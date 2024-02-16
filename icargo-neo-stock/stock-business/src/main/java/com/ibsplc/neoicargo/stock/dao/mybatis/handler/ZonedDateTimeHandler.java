package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component("zonedDateTimeHandler")
public class ZonedDateTimeHandler implements TypeHandler<ZonedDateTime> {

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, ZonedDateTime zonedDateTime, JdbcType jdbcType) {
    // do nothing
  }

  @Override
  public ZonedDateTime getResult(ResultSet resultSet, String s) throws SQLException {
    return resultSet.getTimestamp(s) != null
        ? BaseMapper.convertToZonedDateTime(resultSet.getTimestamp(s))
        : null;
  }

  @Override
  public ZonedDateTime getResult(ResultSet resultSet, int i) throws SQLException {
    return resultSet.getTimestamp(i) != null
        ? BaseMapper.convertToZonedDateTime(resultSet.getTimestamp(i))
        : null;
  }

  @Override
  public ZonedDateTime getResult(CallableStatement callableStatement, int i) throws SQLException {
    return callableStatement.getTimestamp(i) != null
        ? BaseMapper.convertToZonedDateTime(callableStatement.getTimestamp(i))
        : null;
  }
}
