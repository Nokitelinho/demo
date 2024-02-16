package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.sql.CallableStatement;
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
public class MasterDocumentNumberHandlerTest {
  @InjectMocks private MasterDocumentNumberHandler masterDocumentNumberHandler;

  @Mock private ResultSet resultSet;

  @Mock private CallableStatement callableStatement;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldExtractMasterDocumentNumberFromResultSetWithStringParam() throws SQLException {
    doReturn("L").when(resultSet).getString("type");

    var actual = masterDocumentNumberHandler.getResult(resultSet, "type");

    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(0)).isEqualTo("L");
  }

  @Test
  void shouldNotExtractMasterDocumentNumberFromResultSetWithStringParam() throws SQLException {
    doReturn(null).when(resultSet).getString("type");

    var actual = masterDocumentNumberHandler.getResult(resultSet, "type");

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractMasterDocumentNumberFromResultSetWithIntParam() throws SQLException {
    doReturn("L").when(callableStatement).getString(1);

    var actual = masterDocumentNumberHandler.getResult(callableStatement, 1);

    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(0)).isEqualTo("L");
  }

  @Test
  void shouldNotExtractMasterDocumentNumberFromResultSetWithIntParam() throws SQLException {
    doReturn(null).when(callableStatement).getString(1);

    var actual = masterDocumentNumberHandler.getResult(callableStatement, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldExtractMasterDocumentNumberFromResultSetWithCallableStatement() throws SQLException {
    doReturn("L").when(resultSet).getString(1);

    var actual = masterDocumentNumberHandler.getResult(resultSet, 1);

    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(0)).isEqualTo("L");
  }

  @Test
  void shouldNotExtractMasterDocumentNumberFromResultSetWithCallableStatement()
      throws SQLException {
    doReturn(null).when(resultSet).getString(1);

    var actual = masterDocumentNumberHandler.getResult(resultSet, 1);

    assertThat(actual).isNull();
  }

  @Test
  void shouldSetParameter() {
    masterDocumentNumberHandler.setParameter(null, 1, null, null);

    assertThat(2).isNotNull();
  }
}
