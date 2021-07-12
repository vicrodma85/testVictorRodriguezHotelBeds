package com.hotelbeds.supplierintegrations.hackertest.detector.utils.parser;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogLine;

public interface LogLineParser {

    LogLine parse(String line);

}
