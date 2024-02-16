/*
 * Customer.java Created on May 20, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OKNotificationPreferencesCommand extends BaseCommand{
	
	private static final String OK_SUCCESS = "ok_success";
	private static final String MODULE = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";
    private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
	 
	@Override 
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		
		log.log(Log.FINE, "OKNotificationPreferencesCommand", "@@--Entry--");
		
		MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULE,SCREEN_ID);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	CustomerVO customerVO = session.getCustomerVO();
    	int selectedIndex = Integer.parseInt(form.getSelectedContactIndex());
    	
    		String[] eventCodes = form.getEventCode();
        	String[] emailFlags = form.getEmailFlag().split("-");
        	String[] mobileFlags = form.getMobileFlag().split("-");
        	String[] faxFlags = form.getFaxFlag().split("-");
        	
        	ArrayList<NotificationPreferenceVO> notificationPreferences = new ArrayList<NotificationPreferenceVO>();
        	ArrayList<CustomerContactVO> customerContactVOs = null;	
        	if(eventCodes != null && eventCodes.length > 0){//ICRD-162691 added null chk condn
        	for (int i = 0; i < eventCodes.length; i++) {
        		//ETR/ETRTOA
        		if(faxFlags.length > 1) {
        			// Checks whether atleast one event mode is selected for an event
            		if(CustomerVO.FLAG_YES.equals(emailFlags[i+1])||
            				CustomerVO.FLAG_YES.equals(mobileFlags[i+1])||
            				CustomerVO.FLAG_YES.equals(faxFlags[i+1])){
            			// constructing VOs from the form values
    	        		NotificationPreferenceVO notificationPreferenceVO = new NotificationPreferenceVO();
    	    			notificationPreferenceVO.setCompanyCode(customerVO.getCompanyCode());
    	    			notificationPreferenceVO.setCustomerCode(customerVO.getCustomerCode());
    	    			notificationPreferenceVO.setEventCode(eventCodes[i]);
        				notificationPreferenceVO.setEmailFlag(emailFlags[i+1]);
        				notificationPreferenceVO.setMobileFlag(mobileFlags[i+1]);
        				notificationPreferenceVO.setFaxFlag(faxFlags[i+1]);
        				notificationPreferenceVO.setLastUpdatedUser(logonAttributes.getUserId());
    	    			
    	    			notificationPreferences.add(notificationPreferenceVO);
        		  }
        		}
        		//EFRT 
        		else {
        			// Checks whether atleast one event mode is selected for an event
            		if((emailFlags!=null && emailFlags.length > 1 && CustomerVO.FLAG_YES.equals(emailFlags[i+1]))||
            				(mobileFlags!=null && mobileFlags.length > 1 &&CustomerVO.FLAG_YES.equals(mobileFlags[i+1]))){
            			// constructing VOs from the form values
    	        		NotificationPreferenceVO notificationPreferenceVO = new NotificationPreferenceVO();
    	    			notificationPreferenceVO.setCompanyCode(customerVO.getCompanyCode());
    	    			notificationPreferenceVO.setCustomerCode(customerVO.getCustomerCode());
    	    			notificationPreferenceVO.setEventCode(eventCodes[i]);
        				notificationPreferenceVO.setEmailFlag(emailFlags!=null && emailFlags.length > 1 ? emailFlags[i+1] : null);
        				notificationPreferenceVO.setMobileFlag(mobileFlags!=null && mobileFlags.length > 1 ? mobileFlags[i+1]: null);
    	    			
    	    			notificationPreferences.add(notificationPreferenceVO);
        		  }
        		}
        	}
        	}//ICRD-162691 added null chk condn
			CustomerContactVO newContactVO = null;
			// Checks whether atleast one contact is existing for the customer
	    	if(customerVO!= null && customerVO.getCustomerContactVOs() != null 
	    			&& customerVO.getCustomerContactVOs().size()>0) { 
	    		customerContactVOs = (ArrayList<CustomerContactVO>)customerVO.getCustomerContactVOs();
	    		// Checks whether the selected contact is a template row
	    		if(selectedIndex>(customerContactVOs.size()-1)){
	    			// Creating new Contact for template row and adding the Notification Prefernces
	    			newContactVO = new CustomerContactVO();
					newContactVO.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
					newContactVO.setNotificationPreferences(notificationPreferences);
					newContactVO.setNotificationLanguageCode(form.getNotificationLanguageCode());
					newContactVO.setNotificationFormat(form.getNotificationFormat());//CRQ ID: ICRD-162691 - A-5127 added
					newContactVO.setAddedFromNotification(true);
					newContactVO.setNotifyShipmentType(form.getNotifyShipmentType());
					session.getCustomerVO().getCustomerContactVOs().add(newContactVO);
	    		}else{
	    			// Updating the existing contact with Notification Preferences
	    			customerContactVOs = (ArrayList<CustomerContactVO>) session.getCustomerVO().
	    			getCustomerContactVOs();
		        	customerContactVOs.get(selectedIndex).
		        				setNotificationPreferences(notificationPreferences);
		        	customerContactVOs.get(selectedIndex).
		        				setNotificationLanguageCode(form.getNotificationLanguageCode());
		        	customerContactVOs.get(selectedIndex).
    				setNotificationFormat(form.getNotificationFormat());//CRQ ID: ICRD-162691 - A-5127 added
		        	customerContactVOs.get(selectedIndex).setNotifyShipmentType(form.getNotifyShipmentType());
	    		}
	    	}else{
	    		// If no contact is previously saved for the customer
	    		if(session.getCustomerVO() == null){
	    			session.setCustomerVO(new CustomerVO());
	    		}
	    		session.getCustomerVO().setCustomerContactVOs(new ArrayList<CustomerContactVO>());
	    		newContactVO = new CustomerContactVO();
				newContactVO.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
				newContactVO.setNotificationPreferences(notificationPreferences);
				newContactVO.setNotificationLanguageCode(form.getNotificationLanguageCode());
				newContactVO.setNotificationFormat(form.getNotificationFormat());//CRQ ID: ICRD-162691 - A-5127 added
				newContactVO.setAddedFromNotification(true);
				newContactVO.setNotifyShipmentType(form.getNotifyShipmentType());
	    		session.getCustomerVO().getCustomerContactVOs().add(newContactVO);
	    	}
    	form.setReloadParent(Boolean.TRUE.toString());
		invocationContext.target=OK_SUCCESS;
		
		log.log(Log.FINE, "OKNotificationPreferencesCommand", "Exit");
	}

}
