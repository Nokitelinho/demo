package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component("quantityHandler")
public class CcaQuantityHandler implements TypeHandler<Object> {

    public static final int MIN_PARTS_OF_CORRECT_QUANTITY = 2;
    public static final int MAX_PARTS_OF_CORRECT_QUANTITY = 4;
    public static final int UNIT_INDEX = 0;
    public static final int DISPLAY_VALUE_INDEX = 1;
    public static final int SYSTEM_VALUE_INDEX = 2;
    public static final int DISPLAY_UNIT_INDEX = 3;

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) {
        // Do Nothing.
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getString(columnName).split("-"));
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getString(columnIndex).split("-"));
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getString(columnIndex).split("-"));
    }

    public Object valueOf(String... value) {
        if (value.length != MAX_PARTS_OF_CORRECT_QUANTITY && value.length != MIN_PARTS_OF_CORRECT_QUANTITY) {
            return null;
        }
        final var quantities = ContextUtil.getTenantContext().getBean(Quantities.class);
        try {
            return value.length == MAX_PARTS_OF_CORRECT_QUANTITY
                    ? quantities.getQuantity(
                            value[UNIT_INDEX],
                            new BigDecimal(value[DISPLAY_VALUE_INDEX]),
                            new BigDecimal(value[SYSTEM_VALUE_INDEX]),
                            value[DISPLAY_UNIT_INDEX])
                    : quantities.getQuantity(
                            value[UNIT_INDEX],
                            new BigDecimal(value[DISPLAY_VALUE_INDEX]));
        } catch (NumberFormatException e) {
            log.warn("Cannot construct Quantity", e);
            return null;
        }
    }

}
