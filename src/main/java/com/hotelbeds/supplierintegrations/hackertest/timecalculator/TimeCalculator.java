package com.hotelbeds.supplierintegrations.hackertest.timecalculator;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeCalculator {

    private static final String PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";

    public static long getMinutesBetween2TimeStamps(String timestamp1, String timestamp2) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN, Locale.ENGLISH);

        ZonedDateTime timeStamp1 = ZonedDateTime.parse( timestamp1, dtf);
        ZonedDateTime timeStamp2 = ZonedDateTime.parse(timestamp2, dtf);

        return Math.abs(Duration.between(timeStamp1, timeStamp2).toMinutes());
    }

}
