/*
 * ClearUCMINOUTMessagingCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearUCMINOUTMessagingCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Clear Command", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		form.setCarrierCode(BLANK);
		form.setFlightDate(BLANK);
		form.setFlightNo(BLANK);
		form.setOrigin(BLANK);
		form.setDestination(BLANK);
		form.setUcmIn(BLANK);
		form.setUcmOut("OUT");
		form.setArrivalTime(BLANK);
		form.setDepartureTime(BLANK);
		form.setDuplicateFlightStatus(BLANK);
		form.setRoute(BLANK);
		form.setViewUldStatus(BLANK);
		form.setLinkStatus(BLANK);
		form.setOutConfirmStatus(BLANK);
		form.setUcmVOStatus(BLANK);
		form.setMessageTypeStatus(BLANK);
		session.setMessageStatus(BLANK);
		session.setOutDestinations(null);
		form.setPouStatus(BLANK);
		form.setOrgDestStatus(BLANK);
		form.setUcmBlockStatus(BLANK);
		session.setFlightValidationVOSession(null);
		ULDFlightMessageReconcileVO reconcileVO = new ULDFlightMessageReconcileVO();
		reconcileVO
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
		reconcileVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		session.setMessageReconcileVO(reconcileVO);
		form.setOrigin(getApplicationSession().getLogonVO().getAirportCode()
				.toUpperCase());
		form.setIsUcmSent("N");
		invocationContext.target = CLEAR_SUCCESS;

	}

}
