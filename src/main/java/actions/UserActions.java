package actions;

import actions.common.EndpointsBase;
import client.RestClient;
import client.utils.HttpRequestType;
import client.utils.RequestSpecificationBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import datamodels.gorest.User;

public class UserActions extends EndpointsBase {

    private static final String USERS = "/users";
    private static final String USER_SPECIFIC = USERS + "/%d";

    /**
     * Create a user
     *
     * @param client       - rest session
     * @param user         - user payload
     * @param responseType - <T> Generic response type e.g. {@link User}
     * @param httpStatus   - Optional expected HTTP Status code in the response
     * @return T of the given responseType
     */
    public static <T> T createUser(RestClient client, User user, Class<T> responseType, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = specificationBuilderWithPayload(user, httpStatus);
        return client.sendRequest(getEndpoint(USERS), HttpRequestType.POST, responseType, specificationBuilder);
    }

    /**
     * Get list of users
     *
     * @param client       - rest session
     * @param responseType - response type e.g. {@link TypeReference}&lt;{@link java.util.List}&lt;{@link User}&gt;&gt;
     * @param httpStatus   - Optional expected HTTP Status code in the response
     * @return T of the given responseType
     */
    public static <T> T getUsers(RestClient client, TypeReference<T> responseType, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = specificationBuilderWithoutPayload(httpStatus);
        return client.sendRequest(getEndpoint(USERS), HttpRequestType.GET, responseType, specificationBuilder);
    }


    /**
     * Get user details by user's id
     *
     * @param client       - rest session
     * @param userId       - id of the user whose info you want to retrieve
     * @param responseType - <T> generic response type e.g. {@link User}
     * @param httpStatus   - Optional expected HTTP Status code in the response
     * @return T of the given responseType
     */
    public static <T> T getUserDetails(RestClient client, Long userId, Class<T> responseType, int... httpStatus) {
        RequestSpecificationBuilder specificationBuilder = specificationBuilderWithoutPayload(httpStatus);
        return client.sendRequest(buildEndpoint(USER_SPECIFIC, userId), HttpRequestType.GET, responseType, specificationBuilder);
    }

}
