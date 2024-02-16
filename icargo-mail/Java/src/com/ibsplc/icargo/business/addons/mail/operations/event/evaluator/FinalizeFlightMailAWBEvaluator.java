package com.ibsplc.icargo.business.addons.mail.operations.event.evaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.event.evaluator.FinalizeFlightMailAWBEvaluator.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N:	28-Sep-2021	:	Draft
 */
public class FinalizeFlightMailAWBEvaluator implements Evaluator<OperationalFlightVO> {
    private static final Log LOGGER= LogFactory.getLogger(FinalizeFlightMailAWBEvaluator.class.getSimpleName());
	private static final String TRIGGERING_POINT_MAIL_ACCEPTANCE="mail.operations.trigerringpointformailacceptance";
	private static final String MAIL_ACCEPTANCE_FLIGHT_FINALIZE="F";
	

	@Override
	public boolean evaluate(OperationalFlightVO operationalFlightVO) {
		return isMailImportRequired();
	}

/**
 * 
 * 	Method		:	FinalizeFlightMailAWBEvaluator.isMailImportRequired
 *	Added by 	:	Ashil M N on 28-Sep-2021
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	boolean
 */
	public boolean isMailImportRequired() {

		Map<String, String> systemParametersMap = null;
		Collection<String> parameterCodes = new ArrayList<>();
		parameterCodes.add(TRIGGERING_POINT_MAIL_ACCEPTANCE);
		boolean isMailImportRequired = false;
		try {
			systemParametersMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
					.findSystemParameterByCodes(parameterCodes);
		} catch (SystemException ex) {
			LOGGER.log(Log.FINE, ex);
		}
		if (systemParametersMap != null && !systemParametersMap.isEmpty() && (MAIL_ACCEPTANCE_FLIGHT_FINALIZE
				.equals(systemParametersMap.get(TRIGGERING_POINT_MAIL_ACCEPTANCE)))) {
			isMailImportRequired = true;
		}
		return isMailImportRequired;
	}

}
