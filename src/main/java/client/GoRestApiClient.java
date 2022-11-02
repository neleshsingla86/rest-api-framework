package client;

import client.utils.RequestSpecificationCreator.CustomHeaderBuilder;
import constants.Constants;

import java.util.Collections;

public class GoRestApiClient extends RestClient {

    private static final String HEADER_NAME = "Authorization";
    private static final String HEADER_VALUE_PREFIX = "Bearer ";

    public GoRestApiClient(String authToken) {
        super(new CustomHeaderBuilder().setHeaders(Collections.singletonMap(HEADER_NAME, HEADER_VALUE_PREFIX + authToken)));
    }

    public GoRestApiClient() {
        super(new CustomHeaderBuilder().setHeaders(Collections.singletonMap(HEADER_NAME, HEADER_VALUE_PREFIX + Constants.BEARER_TOKEN)));
    }
}
