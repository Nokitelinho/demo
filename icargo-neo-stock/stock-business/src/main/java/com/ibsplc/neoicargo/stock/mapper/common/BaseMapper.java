package com.ibsplc.neoicargo.stock.mapper.common;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MANUAL;
import static com.ibsplc.neoicargo.stock.util.StockConstant.NOT_MANUAL;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.lang.Nullable;

/** A common mapper interface that provides helper methods for other mappers */
@Mapper(
    config = CentralConfig.class,
    imports = {LocalDateMapper.class})
public interface BaseMapper {

  DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(StockConstant.STRING_DATE_FORMAT);
  DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Named("convertToZonedDateTime")
  static ZonedDateTime convertToZonedDateTime(@NotNull Timestamp timestamp) {
    return ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.systemDefault());
  }

  @Named("convertToTimestamp")
  static Timestamp convertToTimestamp(ZonedDateTime zonedDateTime) {
    return zonedDateTime == null ? null : Timestamp.valueOf(zonedDateTime.toLocalDateTime());
  }

  @Named("convertToBoolean")
  static boolean convertToBoolean(String flag) {
    return FLAG_YES.equals(flag);
  }

  @Named("convertToFlag")
  static String convertToFlag(boolean value) {
    return value ? FLAG_YES : FLAG_NO;
  }

  @Named("convertZonedDateTimeToInt")
  static int convertZonedDateTimeToInt(ZonedDateTime zonedDateTime) {
    return Integer.parseInt(zonedDateTime.format(FORMATTER));
  }

  static LocalDate toLocalDate(String input) {
    return LocalDateMapper.toLocalDate(
        java.time.LocalDate.parse(input, DATE_FORMATTER).atStartOfDay(ZoneOffset.UTC));
  }

  @Named("convertIntToZonedDateTime")
  static ZonedDateTime convertIntToZonedDateTime(Integer date) {
    return java.time.LocalDate.parse(String.valueOf(date), FORMATTER).atStartOfDay(ZoneOffset.UTC);
  }

  @Named("convertToRangeType")
  static String convertToRangeType(boolean value) {
    return value ? MANUAL : NOT_MANUAL;
  }

  @Named("createCurrentZonedDateTime")
  static ZonedDateTime createCurrentZonedDateTime() {
    return ZonedDateTime.now()
        .withZoneSameInstant(ZoneId.of("GMT"))
        .truncatedTo(ChronoUnit.MINUTES);
  }

  @Named("toArray")
  static List<String> toArray(@Nullable String input) {
    if (Objects.nonNull(input)) {
      var result =
          Arrays.stream(input.split(DELIMITER))
              .map(String::trim)
              .filter(StringUtils::isNotEmpty)
              .collect(Collectors.toList());
      return CollectionUtils.isNotEmpty(result) ? result : null;
    }

    return null;
  }

  static LocalDate toLocalDate(ZonedDateTime zonedDateTime) {
    return LocalDateMapper.toLocalDate(zonedDateTime);
  }

  @Named("convertLocalDateToTimestamp")
  static Timestamp toTimestamp(LocalDate localDate) {
    return localDate == null
        ? null
        : Timestamp.valueOf(LocalDateMapper.toZonedDateTime(localDate).toLocalDateTime());
  }

  @Named("convertZonedDateTimeToCalendar")
  static Calendar convertZonedDateTimeToCalendar(ZonedDateTime zonedDateTime) {
    return zonedDateTime == null ? null : GregorianCalendar.from(zonedDateTime);
  }

  static GMTDate toGMTDate(ZonedDateTime zonedDateTime) {
    return LocalDateMapper.toGMTDate(zonedDateTime);
  }

  static ZonedDateTime toZonedDateTime(GMTDate gmtDate) {
    return LocalDateMapper.toZonedDateTime(gmtDate);
  }
}
