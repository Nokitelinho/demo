package com.ibsplc.neoicargo.common.mapper;

import com.ibsplc.neoicargo.common.util.DateTimeUtils;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.mapstruct.Context;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public interface BaseMapper {

    @Named("toCompanyCode")
    default String toCompanyCode(@Context ContextUtil contextUtil) {
        return contextUtil.callerLoginProfile() == null? null : contextUtil.callerLoginProfile().getCompanyCode();
    }

    @Named("eventTimeToUtcLocalDateTime")
    default LocalDateTime eventTimeToUtcLocalDateTime(String item) {
        var zonedDateTime = ZonedDateTime.parse(item, DateTimeUtils.YEAR_STARTED_MILLIS_OFFSET_TIME_FORMATTER);
        return LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneOffset.UTC);
    }

    @Named("eventTimeToLocalDateTime")
    default LocalDateTime eventTimeToLocalDateTime(String item) {
        return LocalDateTime.parse(item, DateTimeUtils.YEAR_STARTED_MILLIS_OFFSET_TIME_FORMATTER);
    }

    @Named("planTimeToLocalDateTime")
    default LocalDateTime planTimeToLocalDateTime(String item) {
        return LocalDateTime.parse(item, DateTimeUtils.DAY_STARTED_FULL_MONTH_TIME_FORMATTER);
    }

    @Named("planDateToLocalDate")
    default LocalDate planDateToLocalDate(String item) {
        return null!=item?LocalDate.parse(item, DateTimeUtils.DAY_STARTED_DATE_FORMATTER):null;
    }

    @Named("shipmentTimeToLocalDateTime")
    default String shipmentTimeToLocalDateTime(LocalDateTime item) {
        return item.format(DateTimeUtils.DAY_STARTED_TIME_FORMATTER);
    }

    @Named("awbTimeToLocalDateTime")
    default LocalDateTime awbTimeToLocalDateTime(String item) {
        return LocalDateTime.parse(item, DateTimeUtils.DAY_STARTED_WITH_MILLISECONDS_TIME_FORMATTER);
    }

    @Named("shipmentActivityTimeToLocalDateTime")
    default String shipmentActivityTimeToLocalDateTime(LocalDateTime item) {
        return item.format(DateTimeUtils.DAY_STARTED_TIME_FORMATTER_WITH_COMMA);
    }
}
