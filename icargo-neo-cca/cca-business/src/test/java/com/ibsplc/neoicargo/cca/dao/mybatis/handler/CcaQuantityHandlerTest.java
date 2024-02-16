package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.framework.core.util.ContextUtil.getTenantContext;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class CcaQuantityHandlerTest {

	private final static String WEIGHT = "WGT-100-100-L";

	@Mock
	private ResultSet resultSet;

	@Mock
	private CallableStatement callableStatement;

	@Mock
	private PreparedStatement preparedStatement;

	@InjectMocks
	private CcaQuantityHandler quantityHandler;

	@BeforeEach
	void setUp() {
		final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
		MockitoAnnotations.openMocks(this);
		when(getTenantContext().getBean(Quantities.class)).thenReturn(quantities);
	}

	@Test
	void shouldReturnQuantityWhenDisplayValuePresent() throws SQLException {
		// When
		doReturn(WEIGHT).when(resultSet).getString(any(String.class));

		// Then
		assertDoesNotThrow(() -> quantityHandler.getResult(resultSet, "wgt"));
	}

	@Test
	void shouldReturnQuantity() throws SQLException {
		// When
		doReturn("WGT-100").when(resultSet).getString(any(String.class));

		// Then
		assertDoesNotThrow(() -> quantityHandler.getResult(resultSet, "wgt"));
	}

	@Test
	void shouldReturnNullForQuantity() throws SQLException {
		// When
		doReturn("WGT").when(resultSet).getString(any(String.class));

		// Then
		assertDoesNotThrow(() -> quantityHandler.getResult(resultSet, "wgt"));
	}

	@Test
	void shouldReturnQuantityWithColumnIndex() throws SQLException {
		// When
		doReturn(WEIGHT).when(resultSet).getString(anyInt());

		// Then
		assertDoesNotThrow(() -> quantityHandler.getResult(resultSet, 1));
	}

	@Test
	void shouldReturnQuantityWithCallableStatement() throws SQLException {
		// When
		doReturn(WEIGHT).when(callableStatement).getString(anyInt());

		// Then
		assertDoesNotThrow(() -> quantityHandler.getResult(callableStatement, 1));
	}

	@Test
	void shouldCallSetParameter() {
		// Then
		assertDoesNotThrow(() -> quantityHandler.setParameter(preparedStatement, 0, new Object(), null));
	}

	@Test
	void shouldThrowNumberFormatException() throws SQLException {
		// When
		doReturn("WGT-1oo-1oo-L").when(resultSet).getString(any(String.class));

		// Then
		assertNull(quantityHandler.getResult(resultSet, "wgt"));
	}
}
