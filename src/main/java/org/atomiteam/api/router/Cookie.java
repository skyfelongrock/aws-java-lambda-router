package org.atomiteam.api.router;

/**
 * The {@code Cookie} class represents an HTTP cookie with various attributes.
 * It provides methods to set common cookie properties and build the cookie into
 * a formatted string suitable for HTTP headers.
 */
public class Cookie {
    private final String name;
    private final String value;
    private String path = "/";
    private boolean httpOnly = false;
    private boolean secure = false;
    private Integer maxAge = null;
    private String expires = null;
    private String sameSite = null;

    /**
     * Creates a new {@code Cookie} with the specified name and value.
     *
     * @param name  the name of the cookie
     * @param value the value of the cookie
     */
    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of the cookie.
     *
     * @return the cookie's name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the value of the cookie.
     *
     * @return the cookie's value
     */
    public String value() {
        return value;
    }

    /**
     * Returns the path attribute of the cookie.
     *
     * @return the cookie's path
     */
    public String path() {
        return path;
    }

    /**
     * Returns whether the cookie is marked as HttpOnly.
     *
     * @return {@code true} if the cookie is HttpOnly; {@code false} otherwise
     */
    public boolean httpOnly() {
        return httpOnly;
    }

    /**
     * Returns whether the cookie is marked as Secure.
     *
     * @return {@code true} if the cookie is Secure; {@code false} otherwise
     */
    public boolean secure() {
        return secure;
    }

    /**
     * Returns the Max-Age attribute of the cookie.
     *
     * @return the cookie's max age in seconds, or {@code null} if not set
     */
    public Integer maxAge() {
        return maxAge;
    }

    /**
     * Returns the Expires attribute of the cookie.
     *
     * @return the cookie's expiration date, or {@code null} if not set
     */
    public String expires() {
        return expires;
    }

    /**
     * Returns the SameSite attribute of the cookie.
     *
     * @return the cookie's SameSite policy, or {@code null} if not set
     */
    public String sameSite() {
        return sameSite;
    }

    /**
     * Sets the {@code Path} attribute of the cookie, specifying the URL path
     * for which the cookie is valid.
     *
     * @param path the path for the cookie
     * @return the {@code Cookie} instance
     */
    public Cookie path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Sets the {@code HttpOnly} attribute of the cookie, restricting access from
     * JavaScript.
     *
     * @param httpOnly {@code true} to mark the cookie as HttpOnly; {@code false} otherwise
     * @return the {@code Cookie} instance
     */
    public Cookie httpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    /**
     * Sets the {@code Secure} attribute of the cookie, requiring HTTPS for transmission.
     *
     * @param secure {@code true} to mark the cookie as Secure; {@code false} otherwise
     * @return the {@code Cookie} instance
     */
    public Cookie secure(boolean secure) {
        this.secure = secure;
        return this;
    }

    /**
     * Sets the {@code Max-Age} attribute of the cookie, specifying its lifetime in seconds.
     *
     * @param maxAge the maximum age in seconds
     * @return the {@code Cookie} instance
     */
    public Cookie maxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    /**
     * Sets the {@code Expires} attribute of the cookie, defining its expiration date.
     *
     * @param expires the expiration date in a valid HTTP-date format
     * @return the {@code Cookie} instance
     */
    public Cookie expires(String expires) {
        this.expires = expires;
        return this;
    }

    /**
     * Sets the {@code SameSite} attribute of the cookie, specifying its cross-site policy.
     *
     * @param sameSite the SameSite policy (e.g., "Strict", "Lax", "None")
     * @return the {@code Cookie} instance
     */
    public Cookie sameSite(String sameSite) {
        this.sameSite = sameSite;
        return this;
    }

    /**
     * Builds and returns the cookie as a formatted string suitable for HTTP headers.
     *
     * @return the formatted Set-Cookie string
     */
    public String serialize() {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append("; Path=").append(path);

        if (httpOnly) {
            cookie.append("; HttpOnly");
        }
        if (secure) {
            cookie.append("; Secure");
        }
        if (maxAge != null) {
            cookie.append("; Max-Age=").append(maxAge);
        }
        if (expires != null) {
            cookie.append("; Expires=").append(expires);
        }
        if (sameSite != null) {
            cookie.append("; SameSite=").append(sameSite);
        }

        return cookie.toString();
    }
}
