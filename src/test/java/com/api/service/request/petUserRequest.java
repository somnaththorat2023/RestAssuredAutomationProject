package com.api.service.request;

import com.api.utils.Config;

public class petUserRequest extends BaseRequest {
    public petUserRequest(Config config) {
        super(config);
        request = createBaseRequest();
        setDefaultRequestHeader("application/json","application/json");
        //request.header("Authorization", "Token " + config.book_token);
    }
}
