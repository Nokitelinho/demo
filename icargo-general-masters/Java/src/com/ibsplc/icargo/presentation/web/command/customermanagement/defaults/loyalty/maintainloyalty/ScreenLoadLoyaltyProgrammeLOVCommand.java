/*
 * ScreenLoadLoyaltyProgrammeLOVCommand.java Created on Apr 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.LoyaltyProgrammeLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1962
 * 
 */
public class ScreenLoadLoyaltyProgrammeLOVCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainloyalty";
	
	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		LoyaltyProgrammeLovForm loyaltyProgrammeLovForm = (LoyaltyProgrammeLovForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = (MaintainLoyaltySession) getScreenSession(
				MODULE, SCREENID);
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO = new LoyaltyProgrammeFilterVO();
		loyaltyProgrammeFilterVO
				.setLoyaltyProgrammeCode(loyaltyProgrammeLovForm
						.getLoyaltyProgramName());
		loyaltyProgrammeFilterVO.setLoyaltyDescription(loyaltyProgrammeLovForm
				.getLoyaltyProgramDescription());
		loyaltyProgrammeFilterVO.setCompanyCode(companyCode);
		
		if(loyaltyProgrammeFilterVO.getLoyaltyProgrammeCode() != null 
				&& loyaltyProgrammeFilterVO.getLoyaltyProgrammeCode().trim().length() > 0){
			loyaltyProgrammeFilterVO.setLoyaltyProgrammeCode(
					new StringBuilder(loyaltyProgrammeFilterVO.getLoyaltyProgrammeCode()).append("*").toString());
		}
		if(loyaltyProgrammeFilterVO.getLoyaltyDescription() != null 
				&& loyaltyProgrammeFilterVO.getLoyaltyDescription().trim().length() > 0){
			loyaltyProgrammeFilterVO.setLoyaltyDescription(
					new StringBuilder(loyaltyProgrammeFilterVO.getLoyaltyDescription()).append("*").toString());
		}
		Page<LoyaltyProgrammeVO> loyaltyProgrammeVOs = null;
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate = new CustomerMgmntDefaultsDelegate();
		log.log(Log.INFO, "loyaltyProgrammeFilterVO", loyaltyProgrammeFilterVO);
		try {
			loyaltyProgrammeVOs = customerMgmntDefaultsDelegate
					.listAllLoyaltyProgrammes(loyaltyProgrammeFilterVO,Integer.parseInt(loyaltyProgrammeLovForm.getDisplayPage()));
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
			handleDelegateException(e);
		}
		if (loyaltyProgrammeVOs != null && loyaltyProgrammeVOs.size() > 0) {
			
			maintainLoyaltySession
					.setLoyaltyProgrammeLovVOs(loyaltyProgrammeVOs);
		} else{
			maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);
		}
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

}
