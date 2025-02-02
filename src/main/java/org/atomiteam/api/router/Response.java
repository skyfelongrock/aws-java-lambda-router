package org.atomiteam.api.router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents an HTTP response in an AWS Lambda function, providing utilities
 * for setting status codes, content types, and body content.
 */
public class Response {

    private int httpCode;
    private Object body;
    private String contentType = "application/json";
    private ResponseCookies cookies = new ResponseCookies();

    /**
     * Constructs a Response object with the specified HTTP status code and body.
     *
     * @param httpCode the HTTP status code (e.g., 200, 404, 500).
     * @param body     the response body, which can be a string or an object.
     */
    public Response(int httpCode, Object body) {
        this.httpCode = httpCode;
        this.body = body;
    }

    /**
     * Sets the content type of the response.
     *
     * @param contentType the MIME type of the response (e.g., "application/json", "text/html").
     * @return the current Response instance for method chaining.
     */
    public Response contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Retrieves the content type of the response.
     *
     * @return the MIME type of the response.
     */
    public String contentType() {
        return contentType;
    }

    /**
     * Sets the HTTP status code of the response.
     *
     * @param httpCode the HTTP status code (e.g., 200, 404, 500).
     * @return the current Response instance for method chaining.
     */
    public Response httpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    /**
     * Retrieves the HTTP status code of the response.
     *
     * @return the HTTP status code.
     */
    public int httpCode() {
        return httpCode;
    }

    /**
     * Retrieves the body of the response as a string.
     * If the body is an object, it is serialized to JSON.
     *
     * @return the response body as a string.
     * @throws RuntimeException if the body cannot be serialized to JSON.
     */
    public String body() {
        if (this.body == null) {
            return "";
        }
        if (this.body instanceof String) {
            return (String) this.body;
        }
        try {
            return new ObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing response body", e);
        }
    }

    /**
     * Retrieves the cookies associated with the response.
     *
     * @return the {@link ResponseCookies} object containing the cookies.
     */
    public ResponseCookies cookies() {
        return cookies;
    }

    /**
     * Constructs and returns a map of HTTP headers.
     *
     * The map includes:
     * <ul>
     *   <li>"Content-Type" header, if set.</li>
     *   <li>"Set-Cookie" headers, if there are cookies present.</li>
     * </ul>
     *
     * @return a map containing the HTTP headers.
     */
    public Map<String, List<String>> getHeaders() {
        Map<String, List<String>> headers = new HashMap<>();
        if (contentType != null && !contentType.isEmpty()) {
            headers.put("Content-Type", List.of(contentType));
        }
        List<String> cookieHeaders = cookies.toCookieHeader();
        if (!cookieHeaders.isEmpty()) {
            headers.put("Set-Cookie", cookieHeaders);
        }
        return headers;
    }
}