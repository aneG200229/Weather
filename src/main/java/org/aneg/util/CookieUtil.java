package org.aneg.util;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {
    public static Optional<Cookie> findCookie(Cookie[] cookies, String cookieName) {
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst();
    }

}
