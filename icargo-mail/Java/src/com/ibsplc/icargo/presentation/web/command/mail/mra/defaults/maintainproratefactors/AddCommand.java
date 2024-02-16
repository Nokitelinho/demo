/*
 * AddCommand.java Created on Nov 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Rani Rose John
 * Command class for loading captureform3
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Oct 31, 2006 Rani Rose John Initial draft
 */
public class AddCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "AddCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String ADD_SUCCESS = "add_success";

	private static final String LINKSTATUS = "enable";

	private static final String STATUS = "N";
	private static final String SOURCE = "M";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
			MaintainMraProrateFactorsForm maintainProrateFactorsForm = 
				(MaintainMraProrateFactorsForm) invocationContext.screenModel;
			MaintainMraProrateFactorsSession maintainProrateFactorsSession = 
				(MaintainMraProrateFactorsSession) getScreenSession(
					MODULE_NAME, SCREEN_ID);
			log.log(Log.INFO, "Session before addition ",
					maintainProrateFactorsSession.getFactors());
			ArrayList<ProrationFactorVO> prorateFactorVOS = null;
			if (maintainProrateFactorsSession.getFactors() == null
					|| maintainProrateFactorsSession.getFactors().size() == 0) {
				log.log(Log.INFO, "entered if block");
				prorateFactorVOS = 
					new ArrayList<ProrationFactorVO>();
			} else {
				log.log(Log.INFO, "entered else block");
				prorateFactorVOS = maintainProrateFactorsSession.getFactors();
			}
			ProrationFactorVO prorateFactorVO = new ProrationFactorVO();
			prorateFactorVO.setCompanyCode(getApplicationSession().getLogonVO()
					.getCompanyCode());
			prorateFactorVO
					.setOperationFlag(ProrationFactorVO.OPERATION_FLAG_INSERT);
			prorateFactorVO.setOriginCityCode(maintainProrateFactorsForm.getOriginCityCode());
			prorateFactorVO.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
			prorateFactorVO.setProrationFactorSource(SOURCE);
			prorateFactorVO.setProrationFactorStatus(STATUS);
			prorateFactorVOS.add(prorateFactorVO);
			maintainProrateFactorsSession.setFactors(prorateFactorVOS);
			log.log(Log.INFO, "Session after addition ",
					maintainProrateFactorsSession.getFactors());
			maintainProrateFactorsForm.setScreenStatusFlag(
													SCREEN_STATUS_DETAIL);
			maintainProrateFactorsForm.setLinkStatusFlag(LINKSTATUS);
		}
		invocationContext.target = ADD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
}
