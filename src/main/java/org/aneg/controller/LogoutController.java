package org.aneg.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aneg.exception.NotFoundException;
import org.aneg.service.SessionService;
import org.aneg.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    private final SessionService sessionService;


    @PostMapping("/sign-out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> sessionCookie = CookieUtil.findCookie(request.getCookies(), "SESSION_ID");

        if (sessionCookie.isEmpty()) {
            return "redirect:/sign-in";
        }

        UUID sessionId = UUID.fromString(sessionCookie.get().getValue());
        sessionService.deleteSession(sessionId, response);
        return "redirect:/sign-in";
    }
}
