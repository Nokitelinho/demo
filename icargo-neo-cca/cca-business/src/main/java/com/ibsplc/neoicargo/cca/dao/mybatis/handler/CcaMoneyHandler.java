package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.util.currency.Money;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CcaMoneyHandler implements TypeHandler<Money> {

    public static final int PARTS_OF_CORRECT_MONEY_DATA = 2;
    public static final int AMOUNT_INDEX = 1;
    public static final int CURRENCY_INDEX = 0;

    @Override
    public void setParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) {
        // Do nothing
    }

    @Override
    public Money getResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) != null) {
            return valueOf(rs.getString(columnName).split("-"));
        }
        return null;
    }

    @Override
    public Money getResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getString(columnIndex).split("-"));
    }

    @Override
    public Money getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getString(columnIndex).split("-"));
    }

    private Money valueOf(String... value) {
        if (value.length == PARTS_OF_CORRECT_MONEY_DATA) {
            return Money.of(new BigDecimal(value[AMOUNT_INDEX]), value[CURRENCY_INDEX]);
        }
        return null;
    }

}
