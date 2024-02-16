/*
 * Customer.java Created on May 20, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.customer.vo.AdditionalContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;

public class OKAdditionalDetailsCommand extends BaseCommand{
	
	private static final String OK_SUCCESS = "ok_success";
	private static final String MODULE = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";
	 
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
	
		MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULE,SCREEN_ID);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	String[] contactMode = form.getContactMode();
    	String[] contactAddress = form.getContactAddress();
    	String[] operationFlag = form.getHiddenOpFlagForAddtContacts();
    	CustomerVO customerVO = session.getCustomerVO();
    	
    	//Added by 203132 for IASCB-177945 starts
    	if(Objects.isNull(session.getCustomerVO()))
    	{
    		customerVO=new CustomerVO();
    		session.setCustomerVO(customerVO);
    	}
    	//Added by 203132 for IASCB-177945 ends
    	
    	ArrayList<AdditionalContactVO> additionalContacts = null;
    	ArrayList<CustomerContactVO> customerContactVOs = null;	
    	int selectedIndex = Integer.parseInt(form.getSelectedContactIndex());
    	if(operationFlag != null) {
    		
			additionalContacts = new ArrayList<AdditionalContactVO>();
			
			for (int i = 0; i < operationFlag.length; i++) {
				if((!CustomerVO.OPERATION_FLAG_DELETE.equals(operationFlag[i])&&
						!"NOOP".equals(operationFlag[i]))
						&& contactAddress[i]!=null 
						&& contactAddress[i].trim().length()>0){
    				AdditionalContactVO additionalContactVO = new AdditionalContactVO();
    				additionalContactVO.setCompanyCode(customerVO.getCompanyCode());
            		additionalContactVO.setCustomerCode(customerVO.getCustomerCode());
            		additionalContactVO.setContactMode(contactMode[i]);
            		additionalContactVO.setContactAddress(contactAddress[i]);
            		additionalContactVO.setLastUpdatedUser(logonAttributes.getUserId());
            		additionalContacts.add(additionalContactVO);
				}
			}
    	}
    	
		CustomerContactVO newContactVO = null;
		// Checks whether atleast one contact is existing for the customer
			if(customerVO.getCustomerContactVOs() != null 
					&& customerVO.getCustomerContactVOs().size()>0) { 
				customerContactVOs = (ArrayList<CustomerContactVO>)customerVO.getCustomerContactVOs();
				// Checks whether the selected contact is a template row
	    		if(selectedIndex>(customerContactVOs.size()-1)){
	    			// Creating new Contact for template row and adding the Additional Contacts
	    			newContactVO = new CustomerContactVO();
					newContactVO.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
					newContactVO.setAdditionalContacts(additionalContacts);
					newContactVO.setAddedFromAdditionalContact(true);
					session.getCustomerVO().getCustomerContactVOs().add(newContactVO);
	    		}else{
	    			// Updating the existing contact with Additional Contacts
	    			customerContactVOs = (ArrayList<CustomerContactVO>) session.getCustomerVO().
	    							getCustomerContactVOs();
		        	customerContactVOs.get(selectedIndex).
		        			setAdditionalContacts(additionalContacts);
	    		}
			}else{
				// If no contact is previously saved for the customer
	    		session.getCustomerVO().setCustomerContactVOs(new ArrayList<CustomerContactVO>());
	    		newContactVO = new CustomerContactVO();
				newContactVO.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
				newContactVO.setAdditionalContacts(additionalContacts);
				newContactVO.setAddedFromAdditionalContact(true);
	    		session.getCustomerVO().getCustomerContactVOs().add(newContactVO);
    	   }
    	
    	form.setReloadParent(Boolean.TRUE.toString());
		invocationContext.target=OK_SUCCESS;
	}

}
