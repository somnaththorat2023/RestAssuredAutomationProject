package com.api.service.request;

import com.api.utils.Config;

public class GeneratedbRequest extends BaseRequest {
    public GeneratedbRequest(Config config) {
        super(config);
        request = createBaseRequest();
        setDefaultRequestHeader("application/json","application/json");
        //request.header("Authorization", "Token " + config.book_token);
    }


}
