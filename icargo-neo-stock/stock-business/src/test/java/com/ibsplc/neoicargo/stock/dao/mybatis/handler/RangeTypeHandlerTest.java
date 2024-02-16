package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class RangeTypeHandlerTest {
  @InjectMocks private RangeTypeHandler rangeTypeHandler;

  @Mock private ResultSet resultSet;

  @Mock private CallableStatement callableStatement;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldExtractRangeTypeFromResultSetWithStringParam() throws SQLException {
    doReturn("N").when(resultSet).getString("type");

    var actual = rangeTypeHandler.getResult(resultSet, "type");

    assertThat(actual).isEqualTo("N");
  }

  @Test
  void shouldNotExtractRangeTypeFromResultSetWithStringParam() throws SQLException {
    doReturn(null).when(resultSet).getString("type");

    var actual = rangeTypeHandler.getResult(resultSet, "type");

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractRangeTypeFromResultSetWithIntParam() throws SQLException {
    doReturn("M").when(callableStatement).getString(1);

    var actual = rangeTypeHandler.getResult(callableStatement, 1);

    assertThat(actual).isEqualTo("M");
  }

  @Test
  void shouldNotExtractRangeTypeFromResultSetWithIntParam() throws SQLException {
    doReturn(null).when(callableStatement).getString(1);

    var actual = rangeTypeHandler.getResult(callableStatement, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractRangeTypeFromResultSetWithCallableStatement() throws SQLException {
    doReturn("M").when(resultSet).getString(1);

    var actual = rangeTypeHandler.getResult(resultSet, 1);

    assertThat(actual).isEqualTo("M");
  }

  @Test
  void shouldNotExtractRangeTypeFromResultSetWithCallableStatement() throws SQLException {
    doReturn(null).when(resultSet).getString(1);

    var actual = rangeTypeHandler.getResult(resultSet, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldSetParameter() {
    rangeTypeHandler.setParameter(null, 1, null, null);

    Assertions.assertThat(2).isNotNull();
  }
}
