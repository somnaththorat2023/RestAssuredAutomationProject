package com.api.service.request;

import com.api.utils.*;

public class RequestFactory {

    private static final Config proxyConfiguration = new Config();
    private static final int TIMEOUT = 10;


    public static BaseRequest makeNoHeaderRequest(final Config config) {
        return new NoHeaderRequest(config);
    }

    public static BaseRequest makeBookRequest(final Config config) {
        return new BookStoreRequest(config);
    }
    
    public static BaseRequest makePetStore(final Config config) {
        return new PetStoreRequest(config);
    }

    public static BaseRequest makeGeneratedbRequest(Config config) {

        return new GeneratedbRequest(config);
    }
    public static BaseRequest makepetUserRequest(Config config) {

        return new petUserRequest(config);
    }

}