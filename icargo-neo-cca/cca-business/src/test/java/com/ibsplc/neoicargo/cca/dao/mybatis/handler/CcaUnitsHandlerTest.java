package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaUnitsHandlerTest {

    private final static String WEIGHT_VOLUME_LENGTH_UNITS_IN_STRING = "K-B-C";
    private final static String WEIGHT_VOLUME_UNITS_IN_STRING = "K-B";

    @Mock
    private ResultSet resultSet;

    @Mock
    private CallableStatement callableStatement;

    @Mock
    private PreparedStatement preparedStatement;

    private final CcaUnitsHandler ccaUnitsHandler = new CcaUnitsHandler();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallSetParameter() {
        //When + Then
        assertDoesNotThrow(() -> ccaUnitsHandler.setParameter(preparedStatement, 0, new Units(), JdbcType.OTHER));
    }

    @Test
    void shouldReturnWeightVolumeLengthUnits() throws SQLException {
        // When
        doReturn(WEIGHT_VOLUME_LENGTH_UNITS_IN_STRING).when(resultSet).getString(any(String.class));

        // Then
        final var actual = assertDoesNotThrow(() -> ccaUnitsHandler.getResult(resultSet, "any"));
        assertNotNull(actual.getWeight());
        assertNotNull(actual.getVolume());
        assertNotNull(actual.getLength());
    }

    @Test
    void shouldReturnWeightVolumeUnits() throws SQLException {
        // When
        doReturn(WEIGHT_VOLUME_UNITS_IN_STRING).when(resultSet).getString(any(String.class));

        // Then
        final var actual = assertDoesNotThrow(() -> ccaUnitsHandler.getResult(resultSet, "any"));
        assertNotNull(actual.getWeight());
        assertNotNull(actual.getVolume());
        assertNull(actual.getLength());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "xxx", "K-BB-CC"})
    void shouldReturnNullForIncorrectString(String columnData) throws SQLException {
        // When
        doReturn(columnData).when(resultSet).getString(any(String.class));

        // Then
        assertNull(ccaUnitsHandler.getResult(resultSet, "any"));
    }

    @Test
    void shouldReturnUnitsWithColumnIndex() throws SQLException {
        // When
        doReturn(WEIGHT_VOLUME_LENGTH_UNITS_IN_STRING).when(resultSet).getString(anyInt());

        // Then
        assertNotNull(ccaUnitsHandler.getResult(resultSet, 1));
    }

    @Test
    void shouldReturnUnitsWithCallableStatement() throws SQLException {
        // When
        doReturn(WEIGHT_VOLUME_LENGTH_UNITS_IN_STRING).when(callableStatement).getString(anyInt());

        // Then
        assertNotNull(ccaUnitsHandler.getResult(callableStatement, 1));
    }
}