package org.atomiteam.api.router;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ResponseCookiesTest {

    @Test
    void testAddSingleCookie() {
        ResponseCookies responseCookies = new ResponseCookies();
        Cookie cookie = responseCookies.withCookie("sessionId", "abc123");

        List<String> cookieHeaders = responseCookies.toCookieHeader();

        assertEquals(1, cookieHeaders.size(), "Expected one cookie header");
        assertTrue(cookieHeaders.get(0).contains("sessionId=abc123"), "Cookie header mismatch");
        assertEquals("sessionId", cookie.name(), "Cookie name mismatch");
        assertEquals("abc123", cookie.value(), "Cookie value mismatch");
    }

    @Test
    void testAddMultipleCookies() {
        ResponseCookies responseCookies = new ResponseCookies();
        responseCookies.withCookie("sessionId", "abc123");
        responseCookies.withCookie("userId", "user456");

        List<String> cookieHeaders = responseCookies.toCookieHeader();

        assertEquals(2, cookieHeaders.size(), "Expected two cookie headers");
        assertTrue(cookieHeaders.stream().anyMatch(header -> header.contains("sessionId=abc123")), "Missing sessionId cookie");
        assertTrue(cookieHeaders.stream().anyMatch(header -> header.contains("userId=user456")), "Missing userId cookie");
    }

    @Test
    void testCookieAttributes() {
        ResponseCookies responseCookies = new ResponseCookies();
        Cookie cookie = responseCookies.withCookie("sessionId", "abc123")
                .path("/app")
                .httpOnly(true)
                .secure(true)
                .maxAge(3600)
                .expires("Wed, 21 Oct 2025 07:28:00 GMT")
                .sameSite("Strict");

        List<String> cookieHeaders = responseCookies.toCookieHeader();

        assertEquals(1, cookieHeaders.size(), "Expected one cookie header");
        String header = cookieHeaders.get(0);
        assertTrue(header.contains("sessionId=abc123"), "Cookie name-value pair mismatch");
        assertTrue(header.contains("Path=/app"), "Path attribute mismatch");
        assertTrue(header.contains("HttpOnly"), "HttpOnly attribute missing");
        assertTrue(header.contains("Secure"), "Secure attribute missing");
        assertTrue(header.contains("Max-Age=3600"), "Max-Age attribute mismatch");
        assertTrue(header.contains("Expires=Wed, 21 Oct 2025 07:28:00 GMT"), "Expires attribute mismatch");
        assertTrue(header.contains("SameSite=Strict"), "SameSite attribute mismatch");

        assertEquals("/app", cookie.path(), "Cookie path mismatch");
        assertTrue(cookie.httpOnly(), "Cookie HttpOnly attribute mismatch");
        assertTrue(cookie.secure(), "Cookie Secure attribute mismatch");
        assertEquals(3600, cookie.maxAge(), "Cookie Max-Age mismatch");
        assertEquals("Wed, 21 Oct 2025 07:28:00 GMT", cookie.expires(), "Cookie Expires mismatch");
        assertEquals("Strict", cookie.sameSite(), "Cookie SameSite mismatch");
    }

    @Test
    void testEmptyResponseCookies() {
        ResponseCookies responseCookies = new ResponseCookies();
        List<String> cookieHeaders = responseCookies.toCookieHeader();

        assertTrue(cookieHeaders.isEmpty(), "Expected no cookie headers");
    }
}
