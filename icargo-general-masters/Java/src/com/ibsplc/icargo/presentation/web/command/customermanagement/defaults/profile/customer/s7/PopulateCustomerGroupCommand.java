package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.s7.PopulateCustomerGroupCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7604	:	Apr 19, 2018	:	Draft
 */

public class PopulateCustomerGroupCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("PopulateCustomerGroupCommand");
	
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	public static final String CLASS_NAME = "PopulateCustomerGroupCommand";
	private static final String GRP_CATEGORY = "GEN";
	public static final String GRP_TYPE = "CUSGRP";
	
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULENAME, CLASS_NAME);
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);

		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();			
		CustomerVO customerVO = session.getCustomerVO();
		Collection<GeneralMasterGroupVO> customerGroups=null;		
		
		if (form.getCustomerCode() != null	&& form.getCustomerCode().trim().length() > 0) {		
			customerGroups=populateCustomerGroupName(companyCode,form.getCustomerCode());
		if(customerGroups!=null)
		{
			for (GeneralMasterGroupVO generalMasterGroupVO :customerGroups)
			{
				log.log(Log.FINE, "S7 populateCustomerGroup-------------->",
						generalMasterGroupVO.getGroupName());
				form.setCustomerGroup(generalMasterGroupVO.getGroupName());	
				customerVO.setCustomerGroup(generalMasterGroupVO.getGroupName());
			}
			log.log(Log.FINE, "S7 populateCustomerGroup Exist-------------->");
		}
	}
		session.setCustomerVO(customerVO);
		log.exiting(MODULENAME, CLASS_NAME);

	 }
	
	/**
	 * 
	 * 	Method		:	PopulateCustomerGroup.populateCustomerGroupName
	 *	Added by 	:	A-7604 on Apr 19, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param agentCusCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GeneralMasterGroupVO>
	 */
	 
	private Collection<GeneralMasterGroupVO> populateCustomerGroupName(String companyCode, String agentCusCode) {
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
