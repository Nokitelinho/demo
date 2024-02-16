package com.ibsplc.neoicargo.cca.dao.mybatis.handler;

import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.security.spring.LoginProfileExtractor;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.config.ConfigProviderImpl;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.currency.vo.MoneyVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class CcaMoneyHandlerTest {

	private static final String RETURNED_MONEY = "USD-10.0";

	@Mock
	private ResultSet resultSet;

	@Mock
	private CallableStatement callableStatement;

	@Mock
	private PreparedStatement preparedStatement;

	private MoneyVO moneyVO;

	@Mock
	private ConfigProviderImpl configProvider;

	@Mock
	private ApplicationContext applicationContext;

	@Mock
	private ObjectProvider<LoginProfileExtractor> loginProfileExtractor;

	@Mock
	private ContextUtil contextUtil;

	@InjectMocks
	private CcaMoneyHandler ccaMoneyHandler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Given
		moneyVO = getMoneyVO();

		// Then
		new ContextUtil(applicationContext, loginProfileExtractor).setContext(new RequestContext());
		when(applicationContext.getBean(ContextUtil.class)).thenReturn(contextUtil);
		when(contextUtil.getBean(ConfigProviderImpl.class)).thenReturn(configProvider);
	}

	@Test
	void shouldReturnMoneyWhenCurrencyAndValuePresent() throws SQLException {
		// When
		doReturn(moneyVO).when(configProvider).getMoney("USD");
		doReturn(RETURNED_MONEY).when(resultSet).getString(any(String.class));

		// Then
		assertEquals(10.0, ccaMoneyHandler.getResult(resultSet, "chg").getRoundedAmount().doubleValue());
		assertEquals("USD", ccaMoneyHandler.getResult(resultSet, "chg").getBaseCurrency().getCurrencyCode());
	}

	@Test
	void shouldReturnNullWhenNothingPresent() throws SQLException {
		// When
		doReturn(null).when(resultSet).getString(any(String.class));

		// Then
		assertNull(ccaMoneyHandler.getResult(resultSet, "chg"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "USD", "10.0"})
	void shouldNotReturnMoney(final String returnedValue) throws SQLException {
		// When
		doReturn(returnedValue).when(resultSet).getString(any(String.class));

		// Then
		assertNull(ccaMoneyHandler.getResult(resultSet, "chg"));
	}

	@Test
	void shouldReturnQuantityWithColumnIndex() throws SQLException {
		// When
		doReturn(moneyVO).when(configProvider).getMoney("USD");
		doReturn(RETURNED_MONEY).when(resultSet).getString(anyInt());

		// Then
		assertDoesNotThrow(() -> ccaMoneyHandler.getResult(resultSet, 1));
	}

	@Test
	void shouldReturnQuantityWithCallableStatement() throws SQLException {
		// When
		doReturn(moneyVO).when(configProvider).getMoney("USD");
		doReturn(moneyVO).when(configProvider).getMoney("USD");
		doReturn(RETURNED_MONEY).when(callableStatement).getString(anyInt());

		// Then
		assertDoesNotThrow(() -> ccaMoneyHandler.getResult(callableStatement, 1));
	}

	@Test
	void shouldCallSetParameter() {
		//Given
		doReturn(moneyVO).when(configProvider).getMoney("USD");

		// Then
		assertDoesNotThrow(() -> ccaMoneyHandler.setParameter(preparedStatement, 0, Money.of("USD"), null));
	}

	private MoneyVO getMoneyVO() {
		moneyVO = new MoneyVO();
		moneyVO.setAlternateCurrencyCode("USD");
		moneyVO.setCurrencyCode("USD");
		moneyVO.setCurrencyName("US DOLLAR");
		moneyVO.setPrecisionType("R");
		moneyVO.setPrecisionValue(2);
		moneyVO.setWeekCurrencyIndicator("Y");
		moneyVO.setRoundingType("H");
		moneyVO.setRoundingUnit(0.05);
		return moneyVO;
	}

}
