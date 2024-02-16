package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component("dateHandler")
public class DateHandler implements TypeHandler<String> {

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, String timestamp, JdbcType jdbcType) {
    // do nothing
  }

  @Override
  public String getResult(ResultSet resultSet, String s) throws SQLException {
    return toDisplayDateOnlyFormat(resultSet.getDate(s));
  }

  @Override
  public String getResult(ResultSet resultSet, int i) throws SQLException {
    return toDisplayDateOnlyFormat(resultSet.getDate(i));
  }

  @Override
  public String getResult(CallableStatement callableStatement, int i) throws SQLException {
    return toDisplayDateOnlyFormat(callableStatement.getDate(i));
  }

  private String toDisplayDateOnlyFormat(Date date) {
    if (date == null) {
      return null;
    }
    return new LocalDate(LocalDate.NO_STATION, Location.NONE, date).toDisplayDateOnlyFormat();
  }
}
