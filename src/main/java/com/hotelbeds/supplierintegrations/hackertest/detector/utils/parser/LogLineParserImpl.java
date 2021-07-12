package com.hotelbeds.supplierintegrations.hackertest.detector.utils.parser;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogLine;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.enums.ActionEnum;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Component
public class LogLineParserImpl implements LogLineParser {

    public LogLine parse(String line) {
        String[] splitLine = validateLenghtAndFormat(line);

        String ip = validateIp(splitLine[0]);
        LocalDateTime date = convertEpochTimeToDate(splitLine[1]);
        ActionEnum action = validateAction(splitLine[2]);
        String userName = splitLine[3];

        return new LogLine(ip, date, action, userName);
    }

    private String[] validateLenghtAndFormat(String line) {
        if (line == null || line.isEmpty()) {
            throw new LogLineParserException("Please check the input line as its value is null or is empty");
        }

        String[] result = line.split(",");

        if (result.length == 4) {
            return result;
        }
        throw new LogLineParserException("Line received doesn't have the proper format: ".concat(line));
    }

    private String validateIp(String ip) {
        try {
            if (Inet4Address.getByName(ip).getHostAddress().equals(ip)) {
                return ip;
            } else {
                throw new LogLineParserException("Ip format is not valid. Got: ".concat(ip));
            }
        } catch (UnknownHostException ex) {
            throw new LogLineParserException("Ip format is not valid. Got: ".concat(ex.getMessage()));
        }
    }

    private LocalDateTime convertEpochTimeToDate(String epochInSeconds) {
        try {
            Long epochInMilliseconds = Long.valueOf(epochInSeconds) * 1000;
            return Instant.ofEpochMilli(epochInMilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception ex) {
            throw new LogLineParserException("EpochTime not Valid. Current value is: ".concat(epochInSeconds));
        }

    }

    private ActionEnum validateAction(String action) {
        if (Objects.equals(ActionEnum.SIGNIN_SUCCESS.toString(), action)) {
            return ActionEnum.SIGNIN_SUCCESS;
        } else if (Objects.equals(ActionEnum.SIGNIN_FAILURE.toString(), action)) {
            return ActionEnum.SIGNIN_FAILURE;
        } else {
            throw new LogLineParserException("Action not valid. Current value is: ".concat(action));
        }

    }

}
