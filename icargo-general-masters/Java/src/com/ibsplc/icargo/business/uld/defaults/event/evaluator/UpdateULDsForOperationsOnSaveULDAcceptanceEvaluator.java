/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator.java
 *
 *	Created on	:	30-Mar-2023
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOULDAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ULDAcceptanceVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.evaluator.UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator.java
 *	This class is used for evaluating whether update ULD for operation mapper to be invoked or not on Save ULD Acceptance event
 */
public class UpdateULDsForOperationsOnSaveULDAcceptanceEvaluator implements Evaluator<ULDAcceptanceVO> {
	private static final String OPERATIONS_FLTHANDLING_UMSCALLREQUIRED = "operations.flthandling.umscallrequired";
	private static final Log LOGGER = LogFactory.getLogger("ULD DEFAULTS");
	@Override
	public boolean evaluate(ULDAcceptanceVO arg0) {
		try {
			return isUmsCallNeeded(OPERATIONS_FLTHANDLING_UMSCALLREQUIRED);
		} catch (SystemException e) {
			LOGGER.log(Log.SEVERE, e.getMessage(), e);
		}
		return false;
	}
	private boolean isUmsCallNeeded(String parameterCode) throws SystemException {
		return CTOULDAcceptanceVO.FLAG_YES.equals(ParameterUtil.getInstance().getSystemParameterValue(parameterCode));
	}

}
