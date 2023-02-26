package com.api.service.request;

import com.api.utils.Config;

/**
 * A http request with no header information
 */
public class NoHeaderRequest extends BaseRequest {
    public NoHeaderRequest(Config config) {
        super(config);
        request = createBaseRequest();
        setDefaultRequestHeader("","");
    }
}
