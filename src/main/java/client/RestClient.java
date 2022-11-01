package client;

import client.utils.HttpRequestType;
import client.utils.RequestSpecificationBuilder;
import client.utils.RequestSpecificationCreator;
import client.utils.RequestSpecificationCreator.CustomHeaderBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Rest Assured Client class to create sessions and make API requests
 */
public class RestClient {

    private RequestSpecification requestSpecification;

    public RestClient() {
        requestSpecification = new RequestSpecificationCreator().getDefaultRequestSpecification();
    }

    public RestClient(CustomHeaderBuilder requestSpecificationCreatorBuilder) {
        requestSpecification = new RequestSpecificationCreator(requestSpecificationCreatorBuilder).getDefaultRequestSpecification();
    }

    public <T> T sendRequest(String endpoint, HttpRequestType requestType, Class<T> returnType, RequestSpecificationBuilder... requestSpecificationBuilder) {
        return resultOf(sendRequestAndGetResponse(endpoint, requestType, requestSpecificationBuilder), returnType);
    }

    public <T> T sendRequest(String endpoint, HttpRequestType requestType, TypeReference<T> typeReference, RequestSpecificationBuilder... requestSpecificationBuilder) {
        return resultOf(sendRequestAndGetResponse(endpoint, requestType, requestSpecificationBuilder), typeReference);
    }

    private <T> T resultOf(Response response, Class<T> returnType) {
        return returnType != null ? response.as(returnType) : null;
    }

    private <T> T resultOf(Response response, TypeReference<T> returnType) {
        return returnType != null ? response.as(returnType.getType()) : null;
    }

    public Response sendRequestAndGetResponse(String endpoint, HttpRequestType requestType, RequestSpecificationBuilder... requestSpecificationBuilders) {
        if (ArrayUtils.isNotEmpty(requestSpecificationBuilders)) {
            requestSpecification = new RequestSpecificationCreator().createRequestSpecification(requestSpecification, requestSpecificationBuilders[0]);
        }

        Response response;
        switch (requestType) {
            case POST:
                response = requestSpecification.when().post(endpoint).andReturn();
                break;
            case DELETE:
                response = requestSpecification.when().delete(endpoint).andReturn();
                break;
            case PUT:
                response = requestSpecification.when().put(endpoint).andReturn();
                break;
            case PATCH:
                response = requestSpecification.when().patch(endpoint).andReturn();
                break;
            case GET:
            default:
                response = requestSpecification.when().get(endpoint).andReturn();
        }

        return response;
    }
}
