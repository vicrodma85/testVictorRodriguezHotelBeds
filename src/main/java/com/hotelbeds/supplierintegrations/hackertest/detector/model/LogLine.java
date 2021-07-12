package com.hotelbeds.supplierintegrations.hackertest.detector.model;

import com.hotelbeds.supplierintegrations.hackertest.detector.utils.enums.ActionEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogLine {

    private final String ip;
    private final LocalDateTime date;
    private final ActionEnum action;
    private final String userName;

    public LogLine(String ip, LocalDateTime date, ActionEnum action, String userName) {
        this.ip = ip;
        this.date = date;
        this.action = action;
        this.userName = userName;
    }

}
