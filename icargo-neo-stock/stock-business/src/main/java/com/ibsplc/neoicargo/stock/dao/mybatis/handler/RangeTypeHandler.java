package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_MANUAL;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_NEUTRAL;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rangeTypeHandler")
public class RangeTypeHandler implements TypeHandler<String> {

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, String rangeType, JdbcType jdbcType) {
    // do nothing
  }

  @Override
  public String getResult(ResultSet resultSet, String s) throws SQLException {
    var rangeType = resultSet.getString(s);
    return getRangeType(rangeType);
  }

  @Override
  public String getResult(ResultSet resultSet, int i) throws SQLException {
    var rangeType = resultSet.getString(i);
    return getRangeType(rangeType);
  }

  @Override
  public String getResult(CallableStatement callableStatement, int i) throws SQLException {
    var rangeType = callableStatement.getString(i);
    return getRangeType(rangeType);
  }

  @Nullable
  private String getRangeType(String rangeType) {
    if (rangeType == null) {
      return null;
    }
    if (MODE_NEUTRAL.equalsIgnoreCase(rangeType)) {
      return MODE_NEUTRAL;
    } else {
      return MODE_MANUAL;
    }
  }
}
