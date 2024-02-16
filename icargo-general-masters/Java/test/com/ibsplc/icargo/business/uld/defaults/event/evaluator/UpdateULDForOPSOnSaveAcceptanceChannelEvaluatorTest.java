/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.cto.vo.ShipmentInULDAcceptanceVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtilMock;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 * @author A-7900
 *
 */
public class UpdateULDForOPSOnSaveAcceptanceChannelEvaluatorTest extends AbstractFeatureTest {

	UpdateULDForOPSOnSaveAcceptanceChannelEvaluator updateULDForOPSOnSaveAcceptanceChannelEvaluator;
	CTOAcceptanceVO ctoAcceptanceVO;
	ShipmentInULDAcceptanceVO shipmentInULDAcceptanceVO;
	Collection<ShipmentInULDAcceptanceVO> shipmentInULDAcceptanceVOs;
	String umsCallRequiredSystemParameter = "operations.flthandling.umscallrequired";

	@Override
	public void setup() throws Exception {

		updateULDForOPSOnSaveAcceptanceChannelEvaluator = new UpdateULDForOPSOnSaveAcceptanceChannelEvaluator();
		populateCTOAcceptanceVO();
		ParameterUtilMock.mockParameterUtil();

	}
	private void populateCTOAcceptanceVO() {
		ctoAcceptanceVO = new CTOAcceptanceVO();
		ctoAcceptanceVO.setCompanyCode("AV");
	}

	@Test
	public void shouldReturnTrue_When_SystemParameterForUMSCallNeededIsConfiguredAs_Y() throws Exception {
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue(umsCallRequiredSystemParameter);
		boolean returnValue = updateULDForOPSOnSaveAcceptanceChannelEvaluator.evaluate(ctoAcceptanceVO);
		assertTrue(returnValue);
	}

	@Test
	public void shouldReturnFalse_When_SystemParameterForUMSCallNeededIsNotConfigured() throws Exception {
		doReturn("N").when(ParameterUtil.getInstance()).getSystemParameterValue(umsCallRequiredSystemParameter);
		boolean returnValue = updateULDForOPSOnSaveAcceptanceChannelEvaluator.evaluate(ctoAcceptanceVO);
		assertFalse(returnValue);
	}

	@Test(expected = Test.None.class)
	public void shouldSkipUMSCallNeededSystemParameterEvaluation_When_SystemExceptionIsCaughtOnGetSystemParameterValue() throws Exception {
		doThrow(SystemException.class).when(ParameterUtil.getInstance()).getSystemParameterValue(any(String.class));
		updateULDForOPSOnSaveAcceptanceChannelEvaluator.evaluate(ctoAcceptanceVO);
	}
}
