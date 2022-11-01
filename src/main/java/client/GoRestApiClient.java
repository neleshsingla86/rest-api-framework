package client;

import client.utils.RequestSpecificationCreator.RequestSpecificationCreatorBuilder;
import constants.Constants;

public class GoRestApiClient extends RestClient {

    public GoRestApiClient(String authToken) {
        super(new RequestSpecificationCreatorBuilder().setAuthToken(authToken));
    }

    public GoRestApiClient() {
        super(new RequestSpecificationCreatorBuilder().setAuthToken(Constants.BEARER_TOKEN));
    }
}
