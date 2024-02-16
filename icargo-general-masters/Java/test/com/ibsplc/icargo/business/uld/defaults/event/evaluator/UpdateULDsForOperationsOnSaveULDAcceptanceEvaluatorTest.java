package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.ShipmentInULDAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ULDAcceptanceVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class UpdateULDsForOperationsOnSaveULDAcceptanceEvaluatorTest extends AbstractFeatureTest {
	UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator evaluator;
	ULDAcceptanceVO uldAcceptanceVO;
	ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVO;
	Collection<ShipmentInULDAcceptanceVO> shipmentInULDAcceptanceVOs;
	String umsCallRequiredSystemParameter = "operations.flthandling.umscallrequired";
	@Override
	public void setup() throws Exception {
		evaluator = new UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator();
		
	}
	@Test
	public void shouldReturnTrue_When_SystemParameterForUMSCallNeededIsConfiguredAs_Y() throws Exception {
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue(umsCallRequiredSystemParameter);
		boolean returnValue = evaluator.evaluate(uldAcceptanceVO);
		assertTrue(returnValue);
	}

	@Test
	public void shouldReturnFalse_When_SystemParameterForUMSCallNeededIsNotConfigured() throws Exception {
		doReturn("N").when(ParameterUtil.getInstance()).getSystemParameterValue(umsCallRequiredSystemParameter);
		boolean returnValue = evaluator.evaluate(uldAcceptanceVO);
		assertFalse(returnValue);
	}

	@Test(expected = Test.None.class)
	public void shouldSkipUMSCallNeededSystemParameterEvaluation_When_SystemExceptionIsCaughtOnGetSystemParameterValue() throws Exception {
		SystemException se = new SystemException(SystemException.UNEXPECTED_SERVER_ERROR);
		doThrow(se).when(ParameterUtil.getInstance()).getSystemParameterValue(any(String.class));
		evaluator.evaluate(uldAcceptanceVO);
	}
	
}
