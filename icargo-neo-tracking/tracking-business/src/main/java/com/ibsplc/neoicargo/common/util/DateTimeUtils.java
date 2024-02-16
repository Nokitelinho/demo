package com.ibsplc.neoicargo.common.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {

    public static final DateTimeFormatter DAY_STARTED_WITH_MILLISECONDS_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
    public static final DateTimeFormatter DAY_STARTED_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter DAY_STARTED_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    public static final DateTimeFormatter DAY_STARTED_FULL_MONTH_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    public static final DateTimeFormatter YEAR_STARTED_MILLIS_OFFSET_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
    public static final DateTimeFormatter DAY_STARTED_TIME_FORMATTER_WITH_COMMA = DateTimeFormatter.ofPattern("dd MMM yyyy',' HH:mm");

}
