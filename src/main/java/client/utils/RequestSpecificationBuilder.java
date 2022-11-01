package client.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestSpecificationBuilder {
    private Map<String, ?> queryParams = new HashMap<>();
    private Object payloadObject = null;
    private boolean followRedirection = false;
    private boolean skipStatusCodeValidation = false;
    private boolean urlEncodingEnabled = true;
    private Integer[] expectedStatusCodes = null;
    private Map<String, String> headers;
    private Map<String, String> cookies;

    public Map<String, ?> getQueryParams() {
        return queryParams;
    }

    public RequestSpecificationBuilder setQueryParams(Map<String, ?> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public Object getPayloadObject() {
        return payloadObject;
    }

    public RequestSpecificationBuilder setPayloadObject(Object payloadObject) {
        this.payloadObject = payloadObject;
        return this;
    }

    public boolean isFollowRedirection() {
        return followRedirection;
    }

    public RequestSpecificationBuilder setFollowRedirection(boolean followRedirection) {
        this.followRedirection = followRedirection;
        return this;
    }

    public boolean isSkipStatusCodeValidation() {
        return skipStatusCodeValidation;
    }

    public RequestSpecificationBuilder setSkipStatusCodeValidation(boolean skipStatusCodeValidation) {
        this.skipStatusCodeValidation = skipStatusCodeValidation;
        return this;
    }

    public boolean isUrlEncodingEnabled() {
        return urlEncodingEnabled;
    }

    public RequestSpecificationBuilder setUrlEncodingEnabled(boolean urlEncodingEnabled) {
        this.urlEncodingEnabled = urlEncodingEnabled;
        return this;
    }

    public Integer[] getExpectedStatusCodes() {
        return expectedStatusCodes;
    }

    public RequestSpecificationBuilder setExpectedStatusCodes(int... expectedStatusCodes) {
        this.expectedStatusCodes = Arrays.stream(expectedStatusCodes).boxed().toArray(Integer[]::new);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestSpecificationBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public RequestSpecificationBuilder addCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }
}
