package com.api.utils;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * wrapper for Response object
 * provides prettified toString override
 */
public class RequestResponse {

    private static final Logger LOG = LoggerFactory.getLogger(RequestResponse.class);
    public Response response;

    public ArrayList<Object> extractObjectFromResponseWithKey(String key) {
        ArrayList<Object> extract_array;

        if(response==null || key==null) {
            return null;
        }

        try {
            JSONParser parser = new JSONParser();

            String responseCode = response.asString();
            JSONObject result = (JSONObject) parser.parse(responseCode);

            extract_array = (ArrayList<Object>)result.get(key);

        } catch (Exception e) {
            LOG.error("Failed extracting json for "+key, e);
            return null;
        }
        return extract_array;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("Response Status: ")
                .append(response==null ?"Null Response" :response.statusLine())
                .append(System.lineSeparator())
                .append("Response Status: ")
                .append(response==null ?"Null Response" :response.asString())
                ;

        return builder.toString();

    }
}
