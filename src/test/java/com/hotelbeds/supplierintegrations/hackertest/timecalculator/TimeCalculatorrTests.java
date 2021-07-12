package com.hotelbeds.supplierintegrations.hackertest.timecalculator;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeCalculatorrTests {

    @Test
    public void tenMinutesDifference() {

        String timeStamp1 = "Sat, 13 Mar 2010 11:29:05 -0800";
        String timeStamp2 = "Sat, 13 Mar 2010 11:39:05 -0800";

        long result = TimeCalculator.getMinutesBetween2TimeStamps(timeStamp1, timeStamp2);


        assertEquals(10, result);

    }

    @Test
    public void thirtySecondsDifferenceReturnsZero() {

        String timeStamp1 = "Sat, 13 Mar 2010 11:29:05 -0800";
        String timeStamp2 = "Sat, 13 Mar 2010 11:29:35 -0800";

        long result = TimeCalculator.getMinutesBetween2TimeStamps(timeStamp1, timeStamp2);


        assertEquals(0, result);

    }

    @Test
    public void sameTimeReturnsZero() {
        String timeStamp1 = "Sat, 13 Mar 2010 11:29:05 -0800";
        String timeStamp2 = "Sat, 13 Mar 2010 11:29:05 -0800";

        long result = TimeCalculator.getMinutesBetween2TimeStamps(timeStamp1, timeStamp2);


        assertEquals(0, result);
    }
}
