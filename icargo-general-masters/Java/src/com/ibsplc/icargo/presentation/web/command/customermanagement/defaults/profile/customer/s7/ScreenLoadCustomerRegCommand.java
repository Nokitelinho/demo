/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7.ScreenLoadCustomerRegCommand.java
 *
 *	Created by	:	A-7604
 *	Created on	:	28-Feb-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7.ScreenLoadCustomerRegCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7604	:	06-Mar-2018	:	Draft
 */
public class ScreenLoadCustomerRegCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadCustomerRegCommand");
	
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	public static final String CLASS_NAME = "ScreenLoadCustomerRegCommand";
	private static final String GRP_CATEGORY = "GEN";
	public static final String GRP_TYPE = "CUSGRP";
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7604 on 06-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULENAME, CLASS_NAME);
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);

		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String agentCusCode = logonAttributes.getCustomerCode();	
	
		CustomerVO customerVO = session.getCustomerVO();
		Collection<GeneralMasterGroupVO> customerGroups=null;
		
	
		/**
		 * if AGT one is logged in User. then the newly created customer will
		 * added into logged user customer group for example: AGT is mapped with
		 * USER1 and his corresponding Customer Group is GROUP1. then the newly
		 * added customer added into Group1
		 */
		//agentVO = populateCustomerGroup(companyCode, agentCusCode);
		//if (agentVO != null) {
			
			//form.setCustomerGroup(agentVO.getCustomerGroup());

			//customerVO.setCustomerGroup(agentVO.getCustomerGroup());
		//}
		//commenting as part of 1.	ICRD-257976 
		if(agentCusCode!=null)
		{
		customerGroups=populateCustomerGroupName(companyCode,agentCusCode);
		if(customerGroups!=null)
		{
			for (GeneralMasterGroupVO generalMasterGroupVO :customerGroups)
			{
				log.log(Log.FINE, "S7 populateCustomerGroup-------------->",
						generalMasterGroupVO.getGroupName());
				form.setCustomerGroup(generalMasterGroupVO.getGroupName());	
				customerVO.setCustomerGroup(generalMasterGroupVO.getGroupName());
			}
			log.log(Log.FINE, "S7 populateCustomerGroup Exist-------------->"
					);
			
			
		}
		}
	

		session.setCustomerVO(customerVO);
		log.exiting(MODULENAME, CLASS_NAME);

	}

	/**
	 * 	Method		:	ScreenLoadCustomerRegCommand.populateCustomerGroupName
	 *	Added by 	:	A-7604 on Apr 19, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param agentCusCode	
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GeneralMasterGroupVO>
	 */
	private Collection<GeneralMasterGroupVO> populateCustomerGroupName(
			String companyCode, String agentCusCode) {
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO=new GeneralMasterGroupFilterVO();
		generalMasterGroupFilterVO.setCompanyCode(companyCode);
		generalMasterGroupFilterVO.setGroupType(GRP_TYPE);
		generalMasterGroupFilterVO.setGroupEntity(agentCusCode);
		generalMasterGroupFilterVO.setGroupCategory(GRP_CATEGORY);
		Collection<GeneralMasterGroupVO> agentCusgrpVO = null;
		if (agentCusCode != null) {
			try {
					agentCusgrpVO = new GeneralMasterGroupingDelegate().findGroupNamesofGroupEntity(generalMasterGroupFilterVO);
				log.log(Log.FINE, "S7 populateCustomerGroup-------------->",agentCusgrpVO);
			} catch (BusinessDelegateException exception) {
				handleDelegateException(exception);
				log.log(Log.SEVERE, "S7 populateCustomerGroup-------------->",
						exception);
			}
		}
	
		return agentCusgrpVO;
	}
	/**
	 
	 * 	Method		:	ScreenLoadCustomerRegCommand.populateCustomerGroup
	 *	Added by 	:	A-7604 on 06-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param agentCusCode
	 *	Parameters	:	@return 
	 *	Return type	: 	AgentVO
	 */
/*	public AgentVO populateCustomerGroup(String companyCode, String agentCusCode) {
		AgentVO agentVO = null;
		if (agentCusCode != null) {
			try {
				agentVO = new AgentDelegate().findAgentDetails(companyCode,
						agentCusCode);
			} catch (BusinessDelegateException exception) {
				handleDelegateException(exception);
				log.log(Log.SEVERE, "S7 populateCustomerGroup-------------->",
						exception);
			}
		}
		return agentVO;
	}*/
}