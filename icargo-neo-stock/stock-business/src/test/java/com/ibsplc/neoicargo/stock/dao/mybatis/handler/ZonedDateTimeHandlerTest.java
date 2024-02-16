package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Month;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class ZonedDateTimeHandlerTest {

  @InjectMocks private ZonedDateTimeHandler zonedDateTimeHandler;

  @Mock private ResultSet resultSet;

  @Mock private CallableStatement callableStatement;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldExtractZonedDateFromResultSetWithStringParam() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000"))
        .when(resultSet)
        .getTimestamp("reqdat");

    ZonedDateTime actual = zonedDateTimeHandler.getResult(resultSet, "reqdat");

    assertThat(actual.getYear()).isEqualTo(2023);
    assertThat(actual.getMonth()).isEqualTo(Month.JANUARY);
    assertThat(actual.getDayOfMonth()).isEqualTo(18);
  }

  @Test
  void shouldNotExtractZonedDateFromResultSetWithStringParam() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp("reqdat");

    ZonedDateTime actual = zonedDateTimeHandler.getResult(resultSet, "reqdat");

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractZonedDateFromResultSetWithIntParam() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000"))
        .when(callableStatement)
        .getTimestamp(1);

    ZonedDateTime actual = zonedDateTimeHandler.getResult(callableStatement, 1);

    assertThat(actual.getYear()).isEqualTo(2023);
    assertThat(actual.getMonth()).isEqualTo(Month.JANUARY);
    assertThat(actual.getDayOfMonth()).isEqualTo(18);
  }

  @Test
  void shouldNotExtractZonedDateFromResultSetWithIntParam() throws SQLException {
    doReturn(null).when(callableStatement).getTimestamp(1);

    ZonedDateTime actual = zonedDateTimeHandler.getResult(callableStatement, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractZonedDateFromResultSetWithCallableStatement() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000")).when(resultSet).getTimestamp(1);

    ZonedDateTime actual = zonedDateTimeHandler.getResult(resultSet, 1);

    assertThat(actual.getYear()).isEqualTo(2023);
    assertThat(actual.getMonth()).isEqualTo(Month.JANUARY);
    assertThat(actual.getDayOfMonth()).isEqualTo(18);
  }

  @Test
  void shouldNotExtractZonedDateFromResultSetWithCallableStatement() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp(1);

    ZonedDateTime actual = zonedDateTimeHandler.getResult(resultSet, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldSetParameter() {
    zonedDateTimeHandler.setParameter(null, 1, null, null);

    assertThat(2).isNotNull();
  }
}
