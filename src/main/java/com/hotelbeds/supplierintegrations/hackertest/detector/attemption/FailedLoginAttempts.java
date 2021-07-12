package com.hotelbeds.supplierintegrations.hackertest.detector.attemption;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FailedLoginAttempts {

    private int attempts = 1;

    private LocalDateTime firstAttemptStoredDateTime;

    public FailedLoginAttempts(LocalDateTime firstAttempt) {
        this.firstAttemptStoredDateTime = firstAttempt;
    }

    public synchronized boolean attemptIsAfterThan(LocalDateTime attemptDate, int maxTimeAllowedBetweenAttempts) {
        return attemptDate.isAfter(firstAttemptStoredDateTime.plusMinutes(maxTimeAllowedBetweenAttempts));
    }

    public synchronized void increaseAttempts(LocalDateTime lastAttempt, int maxTimeAllowedBetweenAttempts) {

        if(attemptIsBeforeThan(lastAttempt, maxTimeAllowedBetweenAttempts)) {
            attempts++;
        } else {
            firstAttemptStoredDateTime = lastAttempt;
            attempts = 1;
        }

    }

    private boolean attemptIsBeforeThan(LocalDateTime attemptDate, int maxTimeAllowedBetweenAttempts) {
        return (firstAttemptStoredDateTime.isBefore(attemptDate) && attemptDate.isBefore(firstAttemptStoredDateTime.plusMinutes(maxTimeAllowedBetweenAttempts)));
    }

}


