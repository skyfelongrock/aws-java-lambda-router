package org.atomiteam.api.router;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ResponseCookies} class allows managing and serializing cookies for an HTTP response.
 */
public class ResponseCookies {
    private final List<Cookie> cookies;

    /**
     * Constructs an empty {@code ResponseCookies} manager.
     */
    public ResponseCookies() {
        this.cookies = new ArrayList<>();
    }

    /**
     * Adds a new cookie to the response.
     *
     * @param name  the name of the cookie
     * @param value the value of the cookie
     * @return the created {@code Cookie} instance
     */
    public Cookie withCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookies.add(cookie);
        return cookie;
    }

    /**
     * Returns a list of cookies formatted as HTTP header strings.
     *
     * @return a list of formatted cookie header strings
     */
    public List<String> toCookieHeader() {
        return cookies.stream().map(Cookie::serialize).toList();
    }
}
