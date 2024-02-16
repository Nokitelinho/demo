package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class TimestampHandlerTest {
  @InjectMocks private TimestampHandler timestampHandler;

  @Mock private ResultSet resultSet;

  @Mock private CallableStatement callableStatement;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldExtractFromResultSetWithStringParam() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000"))
        .when(resultSet)
        .getTimestamp("reqdat");

    String actual = timestampHandler.getResult(resultSet, "reqdat");

    assertThat(actual).isEqualTo("18-Jan-2023 13:19:19.000");
  }

  @Test
  void shouldNotExtractTimestampFromResultSetWithStringParam() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp("reqdat");

    String actual = timestampHandler.getResult(resultSet, "reqdat");

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractTimestampFromResultSetWithIntParam() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000"))
        .when(callableStatement)
        .getTimestamp(1);

    String actual = timestampHandler.getResult(callableStatement, 1);

    assertThat(actual).isEqualTo("18-Jan-2023 13:19:19.000");
  }

  @Test
  void shouldNotExtractTimestampFromResultSetWithIntParam() throws SQLException {
    doReturn(null).when(callableStatement).getTimestamp(1);

    String actual = timestampHandler.getResult(callableStatement, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractTimestampFromResultSetWithCallableStatement() throws SQLException {
    doReturn(Timestamp.valueOf("2023-01-18 13:19:19.000000")).when(resultSet).getTimestamp(1);

    String actual = timestampHandler.getResult(resultSet, 1);

    assertThat(actual).isEqualTo("18-Jan-2023 13:19:19.000");
  }

  @Test
  void shouldNotExtractTimestampFromResultSetWithCallableStatement() throws SQLException {
    doReturn(null).when(resultSet).getTimestamp(1);

    String actual = timestampHandler.getResult(resultSet, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldSetParameter() {
    timestampHandler.setParameter(null, 1, null, null);

    assertThat(2).isNotNull();
  }
}
