package com.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Holds configuration read from system or file properties
 * There is state in here which will be removed
 */
public class Config {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
    private final Properties properties;

    // Browser variables
    public final boolean testOnHeadless;
    //public final boolean testWithProxy;
    public final String httpProxy;
    public final String httpPort;
    public final String noProxy;
    //public final boolean webdriver_log_enabled;

    //  Database
    public final String url;
    public final String driverName;
    public final String username;
    public final String password;;

    // testData
    public final String testDataPath;

    //testBed



    // Book api
    public final String book_url;
    public final String book_endpoint;
    public final String book_username;
    public final String book_password;

// PET STORE API
   public final String  petStore_endpoint;
   public final String petStore_url;
   public final String petsStore_orderId;

    /**
     * It will read the *environment* variable from config.properties file,
     * then read in variables from the config-*environment*.properties file
     * e.g. if i set environment=test, it will read the config-test.properties file.
     */
    public Config() {
        properties = new ConfigProperties().getAllProperties();

        testOnHeadless = Boolean.parseBoolean(getProperty("qaHeadless"));
        LOG.debug("Test running on headless mode {}", testOnHeadless);

        //testWithProxy = Boolean.parseBoolean(getProperty("qaProxy"));
        //webdriver_log_enabled = Boolean.parseBoolean(getProperty("webdriver_log_enabled"));

        // Proxy settings
        httpProxy = getProperty("qaProxyHost");
        httpPort = getProperty("qaProxyPort");
        noProxy = getProperty("qaNoProxy");

        //  Database properties
        url = getProperty("url");
        driverName = getProperty("driverName");
        username = getProperty("username");
        password = getProperty("password");

        // testData
        testDataPath = getProperty("testDataPath");

        //testBed


        //        pull in book API
        book_endpoint=getProperty("book_endpoint");
        book_url=getProperty("book_url");
        book_username=getProperty("book_username");
        book_password=getProperty("book_password");

        
     //pet Store API
        petStore_endpoint=getProperty("petStore_endpoint");
        petStore_url=getProperty("petStore_url");
        petsStore_orderId=getProperty("petsStore_orderId");

    }

    private String[] fromStringArray(final String csvInput) {
        //prechecks
        if (properties == null) {
            //don't want to raise exception or fail here as nothing else does
            LOG.error(String.format("Properties is null trying to populate %s", csvInput));
            return new String[]{};
        }

        //conversion
        String input = getProperty(csvInput);
        String[] output = StringUtils.split(input, ",");   //handles null

        if (output == null || output.length < 1) {
            LOG.error(String.format("Property not found or is empty trying to find %s", csvInput));
            return new String[]{};

        }

        return output;
    }


    public String getProxyUrl() {
        if (this.httpProxy != null && this.httpPort != null) {
            return "http://" + httpProxy + ":" + httpPort;
        }
        return null;
    }

   /* public Boolean getTestWithProxy() {
        return testWithProxy;
    }*/

    public String getHttpProxy() {
        return httpProxy;
    }

    public String getHttpPort() {
        return httpPort;
    }

    private String getProperty(String property) {
        return (System.getProperty(property) != null) ? System.getProperty(property) : properties.getProperty(property);
    }

}
