package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class CcaParameterUtilTest {

	private static final String PARAMETER_CODE = "cra.defaults.toleranceweightforautoroute";

	@InjectMocks
	private CcaParameterUtil awbParameterUtil;

	@Mock
	private ParameterService parameterService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldReturnSystemParameterGetSystemParameter() throws BusinessException {
		// Given
		final var parameter = "Y";

		// When
		doReturn(parameter).when(parameterService).getParameter(isA(String.class), isA(ParameterType.class));

		// Then
		assertEquals(parameter, awbParameterUtil.getSystemParameter(PARAMETER_CODE, ParameterType.SYSTEM_PARAMETER));
	}

	@Test
	void shouldReturnNullGetSystemParameterWhenThrowsBusinessException() throws BusinessException {
		// When
		doThrow(new BusinessException("error", "error")).when(parameterService).getParameter(isA(String.class), isA(ParameterType.class));

		// Then
		assertNull(awbParameterUtil.getSystemParameter(PARAMETER_CODE, ParameterType.SYSTEM_PARAMETER));
	}

	@Test
	void shouldReturnNullGetSystemParameterWhenCodeNull() {
		// Then
		assertNull(awbParameterUtil.getSystemParameter(null, ParameterType.SYSTEM_PARAMETER));
	}

	@Test
	void shouldReturnNullGetSystemParameterWhenCodeEmpty() {
		// Then
		assertNull(awbParameterUtil.getSystemParameter("", ParameterType.SYSTEM_PARAMETER));
	}

}
