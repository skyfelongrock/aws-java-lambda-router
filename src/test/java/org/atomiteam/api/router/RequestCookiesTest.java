package org.atomiteam.api.router;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@code RequestCookies} class.
 */
class RequestCookiesTest {

    @Test
    void testParseValidCookies() {
        String cookieHeader = "sessionId=abc123; theme=dark; user=JohnDoe";
        RequestCookies requestCookies = new RequestCookies(cookieHeader);

        Optional<Cookie> sessionId = requestCookies.getCookie("sessionId");
        Optional<Cookie> theme = requestCookies.getCookie("theme");
        Optional<Cookie> user = requestCookies.getCookie("user");

        assertTrue(sessionId.isPresent());
        assertEquals("abc123", sessionId.get().value());
        
        assertTrue(theme.isPresent());
        assertEquals("dark", theme.get().value());
        
        assertTrue(user.isPresent());
        assertEquals("JohnDoe", user.get().value());
    }

    @Test
    void testParseEmptyCookieHeader() {
        String cookieHeader = "";
        RequestCookies requestCookies = new RequestCookies(cookieHeader);
        assertFalse(requestCookies.getCookie("sessionId").isPresent());
    }

    @Test
    void testParseNullCookieHeader() {
        RequestCookies requestCookies = new RequestCookies(null);
        assertFalse(requestCookies.getCookie("sessionId").isPresent());
    }

    @Test
    void testRetrieveNonExistentCookie() {
        String cookieHeader = "sessionId=abc123";
        RequestCookies requestCookies = new RequestCookies(cookieHeader);
        assertFalse(requestCookies.getCookie("theme").isPresent());
    }

    @Test
    void testCookieCaseInsensitiveRetrieval() {
        String cookieHeader = "sessionId=abc123";
        RequestCookies requestCookies = new RequestCookies(cookieHeader);
        assertTrue(requestCookies.getCookie("SESSIONID").isPresent());
        assertEquals("abc123", requestCookies.getCookie("SESSIONID").get().value());
    }
}
