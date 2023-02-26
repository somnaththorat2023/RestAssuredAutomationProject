package com.api.service.request;

import com.api.utils.Config;

public class PetStoreRequest extends BaseRequest {
    public PetStoreRequest(Config config) {
        super(config);
        request = createBaseRequest();
        setDefaultRequestHeader("application/json","application/json");
        //request.header("Authorization", "Token " + config.book_token);
    }
}
