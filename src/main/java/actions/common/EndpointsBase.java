package actions.common;

import client.utils.RequestSpecificationBuilder;

import constants.Constants;
import java.util.HashMap;

public class EndpointsBase {

    public static String buildEndpoint(String path, Object... variables) {
        return Constants.BASE_URL + String.format(path, variables);
    }

    public static String getEndpoint(String path) {
        return Constants.BASE_URL + path;
    }

    /**
     * Creates a custom {@link RequestSpecificationBuilder} without any payload
     *
     * @param httpStatus Expected status code (optional)
     * @return {@link RequestSpecificationBuilder}
     */
    protected static RequestSpecificationBuilder specificationBuilderWithoutPayload(int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = new RequestSpecificationBuilder();
        return setExpectedStatusCodes(specificationBuilder, httpStatus);
    }

    /**
     * Creates a custom {@link RequestSpecificationBuilder} object with specific payload to be sent with API request
     *
     * @param payload    Payload object
     * @param httpStatus Expected status code (optional)
     * @return {@link RequestSpecificationBuilder}
     */
    protected static RequestSpecificationBuilder specificationBuilderWithPayload(Object payload, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = new RequestSpecificationBuilder().setPayloadObject(payload);
        return setExpectedStatusCodes(specificationBuilder, httpStatus);
    }

    /**
     * Creates a custom {@link RequestSpecificationBuilder} object with query params to be sent with API request
     *
     * @param queryParams  queryParams object
     * @param httpStatus Expected status code (optional)
     * @return {@link RequestSpecificationBuilder}
     */
    protected static RequestSpecificationBuilder specificationBuilderWithQueryParams(HashMap queryParams, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = new RequestSpecificationBuilder().setQueryParams(queryParams);
        return setExpectedStatusCodes(specificationBuilder, httpStatus);
    }

    /**
     * Creates a custom {@link RequestSpecificationBuilder} object with specific payload and query params to be sent with API request
     *
     * @param payload    Payload object
     * @param queryParams  HashMap object
     * @param httpStatus Expected status code (optional)
     * @return {@link RequestSpecificationBuilder}
     */
    protected static RequestSpecificationBuilder specificationBuilderWithPayloadAndQueryParams(Object payload, HashMap queryParams, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = new RequestSpecificationBuilder().setPayloadObject(payload).setQueryParams(queryParams);
        return setExpectedStatusCodes(specificationBuilder, httpStatus);
    }

    /**
     * Sets the expected status code to  {@link RequestSpecificationBuilder}
     * and an optional expected Http status code.
     *
     * @param specificationBuilder {@link RequestSpecificationBuilder} specification builder
     * @param httpStatus           Expected status code (optional)
     * @return {@link RequestSpecificationBuilder}
     */
    private static RequestSpecificationBuilder setExpectedStatusCodes(RequestSpecificationBuilder specificationBuilder,
                                                                      int... httpStatus) {
        if (httpStatus.length > 0) {
            specificationBuilder = specificationBuilder.setExpectedStatusCodes(httpStatus);
        }
        return specificationBuilder;
    }
}