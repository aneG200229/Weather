package org.aneg.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aneg.exception.NotFoundException;
import org.aneg.model.User;
import org.aneg.service.SessionService;
import org.aneg.util.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<Cookie> sessionCookie = CookieUtil.findCookie(request.getCookies(), "SESSION_ID");


        if (sessionCookie.isEmpty()) {
            response.sendRedirect("/sign-in");
            return false;
        }

        try {
            UUID sessionId = UUID.fromString(sessionCookie.get().getValue());
            User user = service.getUserBySessionId(sessionId);
            request.setAttribute("currentUser", user);
            return true;
        } catch (NotFoundException e) {
            response.sendRedirect("/sign-in");
            return false;
        }
    }
}
