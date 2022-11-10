package client.utils;

import com.google.common.collect.Maps;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestSpecificationCreator {

    private final int connectionTimeout = 60000;
    private final int socketTimeout = 60000;

    private final Integer[] statusCodes = {HttpStatus.SC_OK, HttpStatus.SC_CREATED, HttpStatus.SC_ACCEPTED};
    private CustomHeaderBuilder specificationCreatorBuilder;

    static {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> DefaultObjectMapper.getInstance()));
    }

    public RequestSpecificationCreator(CustomHeaderBuilder builder) {
        this.specificationCreatorBuilder = builder;
    }

    public RequestSpecificationCreator() {
    }

    private RequestSpecBuilder getDefaultRequestSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setConfig(RestAssuredConfig.config().httpClient(
                HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", connectionTimeout)
                    .setParam("http.socket.timeout", socketTimeout)))
            .setContentType(ContentType.JSON);
        if (!Objects.isNull(specificationCreatorBuilder)) {
            if (specificationCreatorBuilder.getHeaders() != null) {
                specificationCreatorBuilder.getHeaders().entrySet().forEach(entry -> requestSpecBuilder.addHeader(entry.getKey(), entry.getValue()));
            }
            if (specificationCreatorBuilder.getCookies() != null) {
                specificationCreatorBuilder.getCookies().forEach(cookie -> requestSpecBuilder.addCookie(cookie));
            }
        }
        return requestSpecBuilder;
    }

    public RequestSpecification getDefaultRequestSpecification() {
        return RestAssured.given()
            .spec(getDefaultRequestSpecBuilder().build())
            .redirects()
            .follow(true)
            .expect()
            .statusCode(Matchers.oneOf(statusCodes))
            .request();
    }

    /**
     * Creates a request specification using the provided {@link RequestSpecificationBuilder} object
     *
     * @param requestSpecificationBuilder {@link RequestSpecificationBuilder}
     * @return {@link RequestSpecification}
     */
    public RequestSpecification createRequestSpecification(RequestSpecification requestSpecification, RequestSpecificationBuilder requestSpecificationBuilder) {
        RequestSpecBuilder requestSpecBuilderLocal = new RequestSpecBuilder();
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;

        requestSpecBuilderLocal.setConfig(filterableRequestSpecification.getConfig())
            .setContentType(filterableRequestSpecification.getContentType())
            .setAuth(filterableRequestSpecification.getAuthenticationScheme())
            .addHeaders(Maps.newHashMap(filterableRequestSpecification.getHeaders().asList()
                .stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue))))
            .addCookies(filterableRequestSpecification.getCookies())
            .addFilters(filterableRequestSpecification.getDefinedFilters());

        if (requestSpecificationBuilder.getQueryParams() != null) {
            requestSpecBuilderLocal.addQueryParams(requestSpecificationBuilder.getQueryParams());
        }

        if (requestSpecificationBuilder.getPayloadObject() != null) {
            requestSpecBuilderLocal.setContentType(ContentType.JSON);
            requestSpecBuilderLocal.setBody(requestSpecificationBuilder.getPayloadObject(), ObjectMapperType.JACKSON_2);
        }

        if (requestSpecificationBuilder.getHeaders() != null) {
            requestSpecBuilderLocal.addHeaders(requestSpecificationBuilder.getHeaders());
        }

        if (requestSpecificationBuilder.getCookies() != null) {
            requestSpecBuilderLocal.addCookies(requestSpecificationBuilder.getCookies());
        }

        RequestSpecification requestSpecificationCustom = RestAssured.given()
            .spec(requestSpecBuilderLocal.build())
            .redirects()
            .follow(requestSpecificationBuilder.isFollowRedirection())
            .urlEncodingEnabled(requestSpecificationBuilder.isUrlEncodingEnabled());
        if (ArrayUtils.isNotEmpty(requestSpecificationBuilder.getExpectedStatusCodes()) && !requestSpecificationBuilder.isSkipStatusCodeValidation()) {
            Integer[] statusCodesToAssert = ArrayUtils.isNotEmpty(requestSpecificationBuilder.getExpectedStatusCodes()) ? requestSpecificationBuilder.getExpectedStatusCodes() : statusCodes;
            requestSpecificationCustom
                .expect()
                .statusCode(Matchers.oneOf(statusCodesToAssert));
        }
        return requestSpecificationCustom.request();
    }


    public static class CustomHeaderBuilder {
        private Map<String, String> headers;
        private List<Cookie> cookies;

        public Map<String, String> getHeaders() {
            return headers;
        }

        public CustomHeaderBuilder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public List<Cookie> getCookies() {
            return cookies;
        }

        public CustomHeaderBuilder setCookies(List<Cookie> cookies) {
            this.cookies = cookies;
            return this;
        }

        public RequestSpecificationCreator build() {
            return new RequestSpecificationCreator(this);
        }
    }
}
