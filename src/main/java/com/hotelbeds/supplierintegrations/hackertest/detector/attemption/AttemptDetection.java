package com.hotelbeds.supplierintegrations.hackertest.detector.attemption;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogLine;

public interface AttemptDetection {

    boolean hasExceedFailedAttemptions(LogLine logLine);

}
