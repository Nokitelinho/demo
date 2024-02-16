package com.ibsplc.neoicargo.stock.dao.mybatis.handler;

import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component("MasterDocumentNumberHandler")
public class MasterDocumentNumberHandler implements TypeHandler<List<String>> {

  @Override
  public void setParameter(
      PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) {
    // do nothing
  }

  @Override
  public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
    return BaseMapper.toArray(resultSet.getString(s));
  }

  @Override
  public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
    return BaseMapper.toArray(resultSet.getString(i));
  }

  @Override
  public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
    return BaseMapper.toArray(callableStatement.getString(i));
  }
}
