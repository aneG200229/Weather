package org.aneg.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aneg.dao.SessionDao;
import org.aneg.exception.NotFoundException;
import org.aneg.model.SessionModel;
import org.aneg.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {
    private final SessionDao dao;
    @Value("${session.duration.hours}")
    private int sessionDurationHours;

    @Transactional
    public void createSession(User user, HttpServletResponse resp) {
        SessionModel sessionModel = new SessionModel();
        sessionModel.setUser(user);
        sessionModel.setExpiresAt(LocalDateTime.now().plusHours(sessionDurationHours));
        dao.save(sessionModel);
        log.info("Session created for User: {}", user.getId());
        Cookie cookie = new Cookie("SESSION_ID", sessionModel.getId().toString());
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    @Transactional
    public User getUserBySessionId(UUID sessionId) {
        SessionModel sessionModel = dao.findById(sessionId)
                .orElseThrow(() -> {
                    log.warn("Session not found: {}", sessionId);
                    return new NotFoundException("Session not found");
                });

        if (sessionModel.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Session is over: {}", sessionId);
            dao.deleteById(sessionId);
            throw new NotFoundException("Session is over");
        }
        return sessionModel.getUser();
    }

    @Transactional
    public void deleteSession(UUID sessionId, HttpServletResponse resp) {
        dao.deleteById(sessionId);
        log.info("Session delete: {}", sessionId);
        Cookie cookie = new Cookie("SESSION_ID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
