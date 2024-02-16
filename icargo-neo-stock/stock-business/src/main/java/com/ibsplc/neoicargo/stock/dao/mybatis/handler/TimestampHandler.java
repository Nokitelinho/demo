package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component("timestampHandler")
public class TimestampHandler implements TypeHandler<String> {

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, String timestamp, JdbcType jdbcType) {
    // do nothing
  }

  @Override
  public String getResult(ResultSet resultSet, String s) throws SQLException {
    return toTimeStampFormat(resultSet.getTimestamp(s));
  }

  @Override
  public String getResult(ResultSet resultSet, int i) throws SQLException {
    return toTimeStampFormat(resultSet.getTimestamp(i));
  }

  @Override
  public String getResult(CallableStatement callableStatement, int i) throws SQLException {
    return toTimeStampFormat(callableStatement.getTimestamp(i));
  }

  private String toTimeStampFormat(Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }
    return LocalDateMapper.toLocalDate(timestamp.toLocalDateTime().atZone(ZoneId.of("GMT")))
        .toTimeStampFormat();
  }
}
