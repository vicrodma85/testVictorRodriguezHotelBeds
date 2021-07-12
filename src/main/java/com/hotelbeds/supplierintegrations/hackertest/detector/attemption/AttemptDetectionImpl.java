package com.hotelbeds.supplierintegrations.hackertest.detector.attemption;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogLine;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.enums.ActionEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AttemptDetectionImpl implements AttemptDetection {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int MAX_TIME_ALLOWED = 5;


    private ConcurrentHashMap<String, FailedLoginAttempts> ipFailedLoginAttempts = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean hasExceedFailedAttemptions(LogLine logLine) {

        if (Objects.equals(logLine.getAction(), ActionEnum.SIGNIN_FAILURE)) {

            int totalFailedAttempts = logAndReturnFailedLoginAttempts(logLine);

            return totalFailedAttempts >= MAX_FAILED_ATTEMPTS;

        } else {
            return false;
        }
    }

    private int logAndReturnFailedLoginAttempts(LogLine line) {

        FailedLoginAttempts failedLoginAttempt = ipFailedLoginAttempts.get(line.getIp());

        if (failedLoginAttempt != null) {
            //Check if the attempt is within 5 minutes. Otherwise remove previous attempts
            attemptWithin5MinutesAndRemoveOldEntries(line);
            //Increase number of attempts
            failedLoginAttempt.increaseAttempts(line.getDate(), MAX_TIME_ALLOWED);

        } else {
            //First time for an IP logging in a failure
            failedLoginAttempt = new FailedLoginAttempts(line.getDate());
            ipFailedLoginAttempts.put(line.getIp(), failedLoginAttempt);
        }

        //Return number of attempts
        return failedLoginAttempt.getAttempts();
    }

    private void attemptWithin5MinutesAndRemoveOldEntries(LogLine logLine) {

        LocalDateTime currentAttempt = logLine.getDate();

        ipFailedLoginAttempts.entrySet().stream().forEach(attempt -> {
            if (attempt.getValue().attemptIsAfterThan(currentAttempt, MAX_TIME_ALLOWED)) {
                ipFailedLoginAttempts.remove(logLine.getIp());
            }
        });
    }
}
