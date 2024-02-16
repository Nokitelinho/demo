/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOULDAcceptanceVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-7900
 *
 */
public class UpdateULDForOPSOnSaveAcceptanceChannelEvaluator implements Evaluator<CTOAcceptanceVO> {

	private static final String OPERATIONS_FLTHANDLING_UMSCALLREQUIRED = "operations.flthandling.umscallrequired";
	Log log = LogFactory.getLogger("ULD DEFAULTS");

	@Override
	public boolean evaluate(CTOAcceptanceVO ctoAcceptanceVO) {
		try {
			return isUmsCallNeeded(OPERATIONS_FLTHANDLING_UMSCALLREQUIRED);
		} catch (SystemException e) {
			log.log(Log.FINE, "SystemException Exception Caught");
		}
		return false;
	}

	private boolean isUmsCallNeeded(String umsCallRequiredSystemParameter) throws SystemException {
		return CTOULDAcceptanceVO.FLAG_YES.equals(getValueAssignedForSystemParameter(umsCallRequiredSystemParameter));
	}

	/**
	 * To get the value assigned for the SystemParameter
	 * "operations.flthandling.umscallrequired"
	 * 
	 * @param parameterCode
	 * @return
	 * @throws SystemException
	 */
	private String getValueAssignedForSystemParameter(String parameterCode) throws SystemException {
		return ParameterUtil.getInstance().getSystemParameterValue(parameterCode);
	}

}
