package com.api.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final String DATE_OUTPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DB_TIME_STAMP_FORMAT = "MM/dd/yy HH:mm";
    private static final String DB_SHIPPING_TIME_STAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";
    private static final String ESTIMATED_DELIVERY_TIME_STAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public static void main(String[] args)
    {

        startofDay();

        endoffDay();


        LocalDate localDate = LocalDate.now();

        LocalDateTime startOfDay = localDate.atTime(LocalTime.MIN);
        System.out.println(startOfDay);

        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        System.out.println(endOfDay);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(startOfDay));
        System.out.println(dtf.format(endOfDay));



        // LocalDateTime cur = convertStringToTimeStamp(endOfDate2.toString());

        //  System.out.println("Date represented is-7 "+ cur );

        // System.out.println(startOfTomorrow.atTime(LocalTime.MAX);  //23:59:59.999999999;



    }

//	 public static LocalDateTime convertStringToTimeStamp(String str) {
//
//
//		 if (!StringUtils.isBlank(str) && str.length() < 19) {
//
//				str += ":00";
//			}
//
//		 System.out.println(str);
//
//			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSX");
//		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX");
//
//			String strLocalDate = "2012-02-22 02:06:58.147Z";
//
//			LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);
//			String replace = localDate.toString().replace("T", " ");
//		    System.out.println(replace);
//
//			//System.out.println(localDate);
//			System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate));
//			System.out.println(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd").format(localDate));
//
//			String strDateTime = "2012-02-22 02:06:58.147Z";
//			System.out.println(ZonedDateTime.parse(strDateTime, formatter));
//				return LocalDateTime.now().withNano(0);
//		//	}
//
//		}
//

    public static LocalDateTime startofDay() {

        LocalDateTime startofDate = LocalDateTime.now().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());


        System.out.println("Date represented is-1 "+ startofDate );
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String startDate = startofDate.toString().replace("T", " ");
        System.out.println("start of today"+ startDate);

        //  System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX").format(LocalDateTime.now().atOffset(ZoneOffset.MAX)));
        return startofDate;
    }


    public static LocalDateTime endoffDay() {

        LocalDateTime endOfDate = LocalDateTime.now().with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());


        System.out.println("Date represented is-2 "+ endOfDate );
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        String endDate = endOfDate.toString().replace("T", " ");
        System.out.println("end of today"+endDate);

        // System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX").format(LocalDateTime.now().atOffset(ZoneOffset.MAX)));
        return endOfDate;
    }


    //

    public static LocalDateTime convertStringDateToLocaleDate(String dateValue) {
        LocalDateTime dt = null;
        try {
            if (StringUtils.isNotEmpty(dateValue)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT);
                dt = LocalDateTime.parse(dateValue, formatter);
            }
        } catch (Exception e) {
            Log.info("Exception"+e);

        }
        return dt;
    }

    public static long calculateDaysfromToday(String dateString) {
        long days = 0;

        try {
            LocalDate currentDate = LocalDate.now(Clock.systemUTC());
            LocalDate purchaseDate = LocalDate.parse(dateString);
            Period period = Period.between(purchaseDate, currentDate);
            days = ChronoUnit.DAYS.between(purchaseDate, currentDate);
        } catch (Exception e) {
            Log.info("Exception"+e);
            throw e;

        }
        return days;

    }




}
