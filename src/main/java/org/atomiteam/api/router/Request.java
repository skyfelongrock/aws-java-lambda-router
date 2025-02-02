package org.atomiteam.api.router;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents an HTTP request within an AWS Lambda function, providing utilities
 * for accessing query parameters, headers, cookies, and deserializing the request body.
 * <p>
 * This class is designed to work with API Gateway proxy integrations, where the
 * incoming request is encapsulated in an {@link APIGatewayProxyRequestEvent}.
 * </p>
 */
public class Request {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final APIGatewayProxyRequestEvent event;

    /**
     * Constructs a {@code Request} object with the given API Gateway request event.
     *
     * @param event the API Gateway request event
     */
    public Request(APIGatewayProxyRequestEvent event) {
        this.event = event;
    }

    /**
     * Retrieves the query string parameters as a {@link QueryString} object.
     *
     * @return a {@code QueryString} object containing the query parameters
     */
    public QueryString query() {
        return new QueryString(event.getQueryStringParameters());
    }

    /**
     * Retrieves the headers as a {@link Headers} object.
     *
     * @return a {@code Headers} object containing the request headers
     */
    public Headers headers() {
        return new Headers(event.getHeaders());
    }

    /**
     * Deserializes the request body into an object of the specified type.
     * <p>
     * This method uses the Jackson {@link ObjectMapper} to convert the JSON body
     * into a Java object. It is configured to ignore unknown properties during deserialization.
     * </p>
     *
     * @param <T>   the type of the deserialized object
     * @param clazz the class of the object to deserialize
     * @return the deserialized object
     * @throws RuntimeException if the body cannot be parsed due to invalid JSON or mapping issues
     */
    public <T> T body(Class<T> clazz) {
        try {
            return objectMapper.readValue(event.getBody(), clazz);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error mapping JSON to " + clazz.getName(), e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }
    }

    /**
     * Retrieves the cookies from the "Cookie" header as a {@link RequestCookies} object.
     *
     * @return a {@code RequestCookies} object containing the parsed cookies
     */
    public RequestCookies cookies() {
        return new RequestCookies(headers().getHeader("Cookie"));
    }
}
