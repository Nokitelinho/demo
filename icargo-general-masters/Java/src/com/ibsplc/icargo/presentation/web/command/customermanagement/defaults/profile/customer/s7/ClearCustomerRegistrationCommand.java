/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7.ClearCustomerRegistrationCommand.java
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

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
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
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7.ClearCustomerRegistrationCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7604	:	06-Mar-2018	:	Draft
 */
public class ClearCustomerRegistrationCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ClearCustomerRegistrationCommand");
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	private static final String MODULENAME = "customermanagement.defaults";
	public static final String CLASS_NAME = "ClearCustomerRegistrationCommand";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	private static final String UPDATE = "customermanagement.defaults.customerregn.msg.info.updated";
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
		MaintainCustomerRegistrationSession session = (MaintainCustomerRegistrationSession) getScreenSession(
				MODULENAME, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		CustomerVO customerVO = session.getCustomerVO();
		Collection<GeneralMasterGroupVO> customerGroups=null;
		if(customerVO==null)
		{
			customerVO =new CustomerVO();
			customerVO.setOperationFlag(OPERATION_FLAG_INSERT);	
		}

	
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String agentCusCode = logonAttributes.getCustomerCode();
	/*	agentVO = populateCustomerGroup(companyCode, agentCusCode);
		
		if (agentVO != null) {
			form.setCustomerGroup(agentVO.getCustomerGroup());

			customerVO.setCustomerGroup(agentVO.getCustomerGroup());
		}*/
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
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			if (UPDATE.equals(((ErrorVO) invocationContext.getErrors()
					.iterator().next()).getErrorCode())) {
				invocationContext.target = SAVE_SUCCESS;
			} else {
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		} else {
			invocationContext.target = CLEAR_SUCCESS;
		}
		log.exiting(MODULENAME, CLASS_NAME);

	}

	/**
	 * 
	 * 	Method		:	ClearCustomerRegistrationCommand.populateCustomerGroup
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

}
