package org.atomiteam.api.router;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code RequestCookies} class provides a read-only list of parsed cookies from an HTTP request.
 * <p>
 * This class parses the raw HTTP cookie header and extracts individual cookies as {@link Cookie} objects.
 * It offers a method to retrieve cookies by name in a case-insensitive manner.
 * </p>
 */
public class RequestCookies {
    private final List<Cookie> cookies;

    /**
     * Constructs a {@code RequestCookies} instance by parsing the provided HTTP cookie header.
     *
     * @param cookieHeader the raw HTTP cookie header string; can be {@code null} or empty
     */
    public RequestCookies(String cookieHeader) {
        this.cookies = parse(cookieHeader);
    }

    /**
     * Retrieves a cookie by its name, if present.
     *
     * @param name the name of the cookie to retrieve (case-insensitive)
     * @return an {@code Optional} containing the cookie if found, otherwise an empty {@code Optional}
     */
    public Optional<Cookie> getCookie(String name) {
        return cookies.stream()
                .filter(c -> c.name().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Parses the HTTP cookie header and extracts individual cookies.
     *
     * @param cookieHeader the raw HTTP cookie header string
     * @return a list of {@code Cookie} instances extracted from the header; never {@code null}
     */
    private List<Cookie> parse(String cookieHeader) {
        List<Cookie> cookies = new ArrayList<>();
        if (cookieHeader == null || cookieHeader.isEmpty()) {
            return cookies;
        }
        String[] cookiePairs = cookieHeader.split("; ");
        for (String cookiePair : cookiePairs) {
            String[] parts = cookiePair.split("=", 2);
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = URLDecoder.decode(parts[1].trim(), StandardCharsets.UTF_8);
                cookies.add(new Cookie(name, value));
            }
        }
        return cookies;
    }
}
