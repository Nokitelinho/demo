package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

@Component
public class CcaUnitsHandler implements TypeHandler<Units> {

    public static final int MIN_PARTS_OF_CORRECT_UNITS = 2;
    public static final int MAX_PARTS_OF_CORRECT_UNITS = 3;
    public static final int WEIGHT_UNIT_INDEX = 0;
    public static final int VOLUME_UNIT_INDEX = 1;
    public static final int LENGTH_UNIT_INDEX = 2;

    @Override
    public void setParameter(PreparedStatement ps, int i, Units parameter, JdbcType jdbcType) {
        // Do Nothing.
    }

    @Override
    public Units getResult(ResultSet rs, String columnName) throws SQLException {
        return valueOf(rs.getString(columnName).split("-"));
    }

    @Override
    public Units getResult(ResultSet rs, int columnIndex) throws SQLException {
        return valueOf(rs.getString(columnIndex).split("-"));
    }

    @Override
    public Units getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return valueOf(cs.getString(columnIndex).split("-"));
    }

    private Units valueOf(String... values) {
        if (values.length != MIN_PARTS_OF_CORRECT_UNITS
                && values.length != MAX_PARTS_OF_CORRECT_UNITS || isNotOneSymbolValues(values)) {
            return null;
        }
        final var units = new Units();
        return values.length == MIN_PARTS_OF_CORRECT_UNITS
                ? units.weight(values[WEIGHT_UNIT_INDEX])
                    .volume(values[VOLUME_UNIT_INDEX])
                : units.weight(values[WEIGHT_UNIT_INDEX])
                    .volume(values[VOLUME_UNIT_INDEX])
                    .length(values[LENGTH_UNIT_INDEX]);
    }

    private boolean isNotOneSymbolValues(String[] values) {
        return Stream.of(values)
                .anyMatch(value -> value.length() != 1);
    }

}
