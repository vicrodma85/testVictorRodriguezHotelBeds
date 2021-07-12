package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.attemption.AttemptDetection;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogLine;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.parser.LogLineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class HackerDetectorImpl implements HackerDetector {

    private AttemptDetection attemptDetection;

    private LogLineParser logLineParser;

    @Autowired
    public HackerDetectorImpl(AttemptDetection attemptDetection, LogLineParser logLineParser) {
        this.attemptDetection = attemptDetection;
        this.logLineParser = logLineParser;
    }


    public String parseLine(String line) {

        LogLine logLine = logLineParser.parse(line);

        if (attemptDetection.hasExceedFailedAttemptions(logLine)) {
            return logLine.getIp();
        }

        return null;
    }
}
