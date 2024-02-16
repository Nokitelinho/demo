package com.ibsplc.neoicargo.stock.mapper.common;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MANUAL;
import static com.ibsplc.neoicargo.stock.util.StockConstant.NOT_MANUAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class BaseMapperTest {

  @Test
  void shouldConvertToZonedDateTime() {
    // Given
    var input = new Timestamp(System.currentTimeMillis());
    // When
    var result = BaseMapper.convertToZonedDateTime(input);
    // Then
    assertEquals(input, Timestamp.from(result.toInstant()));
  }

  @Test
  void shouldConvertToTimestamp() {
    // Given
    var input = ZonedDateTime.now();
    // When
    var result = BaseMapper.convertToTimestamp(input);
    // Then
    assertEquals(input, result.toLocalDateTime().atZone(ZoneId.systemDefault()));
  }

  @Test
  void shouldConvertToBoolean() {
    assertTrue(BaseMapper.convertToBoolean(FLAG_YES));
    assertFalse(BaseMapper.convertToBoolean(FLAG_NO));
  }

  @Test
  void shouldConvertToFlag() {
    // When
    var resultForTrue = BaseMapper.convertToFlag(true);
    var resultForFalse = BaseMapper.convertToFlag(false);

    // Then
    assertEquals(FLAG_YES, resultForTrue);
    assertEquals(FLAG_NO, resultForFalse);
  }

  @Test
  void shouldConvertToRangeType() {
    // When
    var resultForTrue = BaseMapper.convertToRangeType(true);
    var resultForFalse = BaseMapper.convertToRangeType(false);

    // Then
    assertEquals(MANUAL, resultForTrue);
    assertEquals(NOT_MANUAL, resultForFalse);
  }

  @Test
  void shouldReturnCurrentZonedDateTime() {
    // When
    var currentZonedDateTime = BaseMapper.createCurrentZonedDateTime();

    // Then
    assertNotNull(currentZonedDateTime);
  }

  @Test
  void shouldConvertToArray() {
    // When
    var result1 = BaseMapper.toArray("HQ, VB");
    var result2 = BaseMapper.toArray("HQ,VB,   F, O,");

    // Then
    assertEquals(List.of("HQ", "VB"), result1);
    assertEquals(List.of("HQ", "VB", "F", "O"), result2);
  }

  @Test
  void shouldConvertToNull() {
    // When
    var result1 = BaseMapper.toArray(null);
    var result2 = BaseMapper.toArray(" , ");

    // Then
    assertThat(result1).isNull();
    assertThat(result2).isNull();
  }

  @Test
  void shouldCreateCurrentZonedDateTime() {
    // When
    var actual = BaseMapper.createCurrentZonedDateTime();

    // Then
    assertThat(actual.getYear()).isEqualTo(ZonedDateTime.now().getYear());
    assertThat(actual.getMonth()).isEqualTo(ZonedDateTime.now().getMonth());
    assertThat(actual.getDayOfMonth()).isEqualTo(ZonedDateTime.now().getDayOfMonth());
  }

  @Test
  void shouldConvertLocalDateToTimestamp() {
    // Given
    LocalDate localDate = null;

    // When
    var actual = BaseMapper.toTimestamp(localDate);

    // Then
    assertThat(actual).isNull();

    // Given
    localDate = new LocalDate("SIN", Location.ARP, false).setDate("26-May-2022");

    // When
    actual = BaseMapper.toTimestamp(localDate);

    // Then
    assertThat(actual.toString()).isEqualTo("2022-05-26 00:00:00.0");
  }
}
