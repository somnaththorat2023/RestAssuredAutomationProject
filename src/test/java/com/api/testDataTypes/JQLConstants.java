package com.api.testDataTypes;

import com.api.utils.Config;

public class JQLConstants {

    private static final Config config = new Config();


    public static final String jquery = "select * from book_user where id=1";

    public static String getString(String qstring) {
        String query = qstring;

        return query;
    }
}