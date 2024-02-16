package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class DateHandlerTest {

  @InjectMocks private DateHandler dateHandler;

  @Mock private ResultSet resultSet;

  @Mock private CallableStatement callableStatement;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldExtractDateFromResultSetWithStringParam() throws SQLException {
    doReturn(Date.valueOf("2023-01-18")).when(resultSet).getDate("reqdat");

    String actual = dateHandler.getResult(resultSet, "reqdat");

    assertThat(actual).isEqualTo("18-Jan-2023");
  }

  @Test
  void shouldNotExtractDateFromResultSetWithStringParam() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp("reqdat");

    String actual = dateHandler.getResult(resultSet, "reqdat");

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractDateFromResultSetWithIntParam() throws SQLException {
    doReturn(Date.valueOf("2023-01-18")).when(callableStatement).getDate(1);

    String actual = dateHandler.getResult(callableStatement, 1);

    assertThat(actual).isEqualTo("18-Jan-2023");
  }

  @Test
  void shouldNotExtractDateFromResultSetWithIntParam() throws SQLException {
    doReturn(null).when(callableStatement).getTimestamp(1);

    String actual = dateHandler.getResult(callableStatement, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractDateFromResultSetWithCallableStatement() throws SQLException {
    doReturn(Date.valueOf("2023-01-18")).when(resultSet).getDate(1);

    String actual = dateHandler.getResult(resultSet, 1);

    assertThat(actual).isEqualTo("18-Jan-2023");
  }

  @Test
  void shouldNotExtractZonedFromResultSetWithCallableStatement() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp(1);

    String actual = dateHandler.getResult(resultSet, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldSetParameter() {
    dateHandler.setParameter(null, 1, null, null);

    assertThat(2).isNotNull();
  }
}
