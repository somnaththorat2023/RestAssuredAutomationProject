package com.api.utils;

import org.apache.log4j.Logger;

public class Log {

     private static final Logger logger = Logger.getLogger(Log.class.getName());

    // Need to create these methods, so that they can be called

    public static void info(String message) {

        logger.info(message);

    }

    public static void warn(String message) {

        logger.warn(message);

    }

    public static void error(String message) {

        logger.error(message);

    }

    public static void fatal(String message) {

        logger.fatal(message);

    }

    public static void debug(String message) {

        logger.debug(message);

    }

    public static void startTestCase(String testCaseName){


       logger.info("****************************************************************************************");

        logger.info(" Start pf Test scenario              "+testCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");

        logger.info("****************************************************************************************");


    }

    //This is to print log for the ending of the test case

    public static void endTestCase(String testCaseName){

        logger.info("End of Test scenario              "+testCaseName+"             XXXXXXXXXXXXXXXXXXXXXX");

        logger.info("X");

        logger.info("X");

        logger.info("X");

        logger.info("X");

    }



}

