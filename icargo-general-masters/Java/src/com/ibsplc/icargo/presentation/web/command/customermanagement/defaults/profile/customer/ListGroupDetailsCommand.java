/*
 * Created on : 21-Dec-2021 
 * Name       : ListGroupDetailsCommand.java
 * Created by : A-2569
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListGroupDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListGroupDetailsCommand extends BaseCommand{

	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	private static final String SUCCESS="groupdetails_success";

	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ActivatingCustomer");
		log.entering("ListGroupDetailsCommand","");
		ListGroupDetailsForm form = (ListGroupDetailsForm)invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(MODULENAME,SCREENID);
		CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();  
		generalMasterGroupFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		generalMasterGroupFilterVO.setGroupType(form.getGroupType());
		generalMasterGroupFilterVO.setGroupName(form.getGroupName());
		generalMasterGroupFilterVO.setGroupDescription(form.getGroupDescription());
		generalMasterGroupFilterVO.setGroupCategory(form.getCategory());
		generalMasterGroupFilterVO.setRecordsPerPage(30);
		generalMasterGroupFilterVO.setPageNumber(1);
		Collection<String> groupElements = new ArrayList<>();
		groupElements.add(form.getCustomerCode()); 
		generalMasterGroupFilterVO.setGroupElements(groupElements); 
		Collection<GeneralMasterGroupVO> groupDetails = null;
		try{
			groupDetails =	delegate.listGroupDetailsForCustomer(generalMasterGroupFilterVO);
		}catch(BusinessDelegateException ex){
			log.log(Log.INFO,ex);
    		handleDelegateException(ex);
    	}
		ArrayList<GeneralMasterGroupVO> groupDetailsTemp = new ArrayList<>();
		if(groupDetails!=null && !groupDetails.isEmpty()){
			for(GeneralMasterGroupVO groupVO : groupDetails){
				groupDetailsTemp.add(groupVO);  
			}
		}
		session.setCustomerGroupDetails(groupDetailsTemp); 
		invocationContext.target = SUCCESS;  
		return;
	}
	

}
