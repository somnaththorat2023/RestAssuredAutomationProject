package com.api.service.request;

import com.api.utils.Config;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;

public abstract class BaseRequest  {
    protected RequestSpecification request;
    protected final Config config;
    private static final int TIMEOUT = 10;
    private String defaultAccept="";
    private String defaultContentType="";

    public BaseRequest(Config config) {
        this.config = config;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setDefaultRequestHeader(final String accept, final String contentType) {
        defaultAccept=accept;
        defaultContentType=contentType;
        setHeader(accept,contentType);
    }
    public void resetHeaderToDefault() {
        setHeader(defaultAccept,defaultContentType);
    }

    public void setNewRequestHeader(final String accept, final String contentType) {
        setHeader(accept,contentType);
    }

    private void setHeader(final String accept, final String contentType) {
        request.header("Content-Type",contentType);
        request.header("Accept","",accept);
    }

    protected RequestSpecification createBaseRequest() {
        RestAssuredConfig restConfig = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT * 2000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT * 2000));

       // return RestAssured.given().proxy(config.getProxyUrl()).config(restConfig).relaxedHTTPSValidation();
        return RestAssured.given().config(restConfig).relaxedHTTPSValidation();
    }

}
