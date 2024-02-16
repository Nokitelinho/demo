/*
 * ClearULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.uldpoolowners;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDPoolOwnersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearULDPoolOwnersCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Clear Command", "ULD Pool Owners");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		form.setAirline(BLANK);
		form.setDestination(BLANK);
		form.setFlightNumber(BLANK);
		form.setOrigin(BLANK);
		session.setFlightValidationVOSession(null);
		ULDPoolOwnerVO poolOwnerVO = new ULDPoolOwnerVO();
		poolOwnerVO.setOperationFlag(ULDPoolOwnerVO.OPERATION_FLAG_INSERT);
		poolOwnerVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		session.setUldPoolOwnerVO(poolOwnerVO);
		form.setLinkStatus(BLANK);
		invocationContext.target = CLEAR_SUCCESS;

	}
}
