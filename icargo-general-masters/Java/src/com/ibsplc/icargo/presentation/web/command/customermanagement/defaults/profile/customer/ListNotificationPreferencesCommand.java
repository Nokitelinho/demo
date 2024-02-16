/*
 * Customer.java Created on May 20, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListNotificationPreferencesCommand extends BaseCommand{
	
		private static final String MODULE = "customermanagement.defaults";
	    private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";
	    private static final String LIST_SUCCESS = "list_success";
	    private static final String MASTER_TYPE = "NTFCFG";
	    private static final String NOTIFICATION_TYPE_ETR = "ETR";
	    //CRQ ID: ICRD-162691 - A-5127	added - start
	    private static final String NOTIFICATION_TYPE_QA = "QAE";
	    private static final String NOTIFICATION_TYPE_EBK = "EBK";
	    private static final String ONETIME_LANGUAGE = "customermanagement.defaults.customercontact.language";
	    private static final String PREFERRED_LANGUAGE_GERMAN = "de_DE";
	    private static final String PREFERRED_LANGUAGE_ENGLISH = "en_US";
	    //CRQ ID: ICRD-162691 - A-5127	added - end
	    private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
	    
	    // Added by A-7137 as part of the BUG ICRD-230980
	    private static final String NOTIFICATION_TYPE_EFRT = "EFRT";
	
	@Override
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  companyCode = logonAttributes.getCompanyCode();
		MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULE,SCREEN_ID);
    	boolean isNotificationPreferenceVOsExist = false;
		int selectedIndex = Integer.parseInt(form.getSelectedContactIndex());
    	String contactType = form.getContactType()/*.split("-")[selectedIndex]*/;//ICRD-162691
    	
    		//Populating Notification Preferences Configurations
    		GeneralParameterConfigurationVO generalParameterConfigurationVO = new GeneralParameterConfigurationVO();
			generalParameterConfigurationVO.setMasterType(MASTER_TYPE);
			generalParameterConfigurationVO.setConfigurationReferenceTwo(GeneralParameterConfigurationVO.FLAG_YES);
			generalParameterConfigurationVO.setCompanyCode(companyCode);
			generalParameterConfigurationVO.setConfigurationReferenceOne(contactType);
			
			Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs = null;
			 try {
				 generalParameterConfigurationVOs = new SharedDefaultsDelegate().
				             findGeneralParameterConfiguration(generalParameterConfigurationVO);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
    		
			
			
			//Populating Notification Preferences for the contact
			NotificationPreferenceVO notificationPreferenceVODisplay = null;
    		ArrayList<NotificationPreferenceVO> preferenceVOs = new ArrayList<NotificationPreferenceVO>();
    		Collection<NotificationPreferenceVO> notificationPreferenceVOs = null;
    		
			log.log(Log.FINE, "\n.........selectedIndex  ", selectedIndex);
			
			ArrayList<CustomerContactVO> customerContactVOs = null;
			
			// If the customer is already having contacts
			if(session.getCustomerVO() != null && session.getCustomerVO()
					.getCustomerContactVOs()!=null){
				customerContactVOs = (ArrayList<CustomerContactVO>)session.getCustomerVO()
				.getCustomerContactVOs();
	    		if(customerContactVOs.size()>0 && selectedIndex<=(customerContactVOs.size()-1)) {
	    			String notificationLanguageCode = customerContactVOs.get(selectedIndex).getNotificationLanguageCode();
	    			if(notificationLanguageCode!=null && notificationLanguageCode.trim().length()>0) {
	    				form.setNotificationLanguageCode(notificationLanguageCode);
	    			}
	    			//CRQ ID: ICRD-162691 - A-5127 added - start
	    			String notificationFormat = customerContactVOs.get(selectedIndex).getNotificationFormat();
	    			if(notificationFormat!=null && notificationFormat.trim().length()>0) {
	    				form.setNotificationFormat(notificationFormat);
	    			}
	    			String notificationFilterType = customerContactVOs.get(selectedIndex).getNotifyShipmentType();
	    			if(notificationFilterType!=null && notificationFilterType.trim().length()>0) {
	    				form.setNotifyShipmentType(notificationFilterType);
	    			}
	    			//CRQ ID: ICRD-162691 - A-5127 added - end
	    			notificationPreferenceVOs = customerContactVOs.get(selectedIndex).getNotificationPreferences();
	    			// If Notification Prefernces for the contact is already available in the session.
	        		if(notificationPreferenceVOs != null && notificationPreferenceVOs.size()>0) {
	        			isNotificationPreferenceVOsExist = true;
	        		}
	    		}
			}
    		log.log(Log.FINE, "  \n.........cust++++", generalParameterConfigurationVOs);
    		if(generalParameterConfigurationVOs != null && generalParameterConfigurationVOs.size()>0) {
    			// Modified by A-7137 as part of the BUG ICRD-230980 
				if(contactType.equals(NOTIFICATION_TYPE_ETR) || contactType.equals(NOTIFICATION_TYPE_EFRT)) {
					generalParameterConfigurationVOs = sort(generalParameterConfigurationVOs);
				}
				
				for (GeneralParameterConfigurationVO parameterConfigurationVO : generalParameterConfigurationVOs) {
					// Constructing new Notification Prefernce from the configuration
					notificationPreferenceVODisplay = new NotificationPreferenceVO();
					notificationPreferenceVODisplay.setEventCode(parameterConfigurationVO.getParmeterCode());
					notificationPreferenceVODisplay.setEventDescription(parameterConfigurationVO.getParameterDescription());	
					notificationPreferenceVODisplay.setEmailFlag(NotificationPreferenceVO.FLAG_NO);
					notificationPreferenceVODisplay.setMobileFlag(NotificationPreferenceVO.FLAG_NO);
					notificationPreferenceVODisplay.setFaxFlag(NotificationPreferenceVO.FLAG_NO);
					// Populating Email, Fax, SMS values from db, if exists
					if(isNotificationPreferenceVOsExist) {
						for (NotificationPreferenceVO notificationPreferenceVO : notificationPreferenceVOs) {
							if(parameterConfigurationVO.getParmeterCode().equals(notificationPreferenceVO.getEventCode())) {								
								notificationPreferenceVODisplay.setEmailFlag(notificationPreferenceVO.getEmailFlag());
								notificationPreferenceVODisplay.setMobileFlag(notificationPreferenceVO.getMobileFlag());
								notificationPreferenceVODisplay.setFaxFlag(notificationPreferenceVO.getFaxFlag());								
							}							
						}
					} 
					preferenceVOs.add(notificationPreferenceVODisplay);
				}
			}
    		//CRQ ID: ICRD-162691 - A-5127 added to include only languages English and German for contacttypes QA and ebooking- start
			if((session.getLanguagesForEbkAndQa() == null || session.getLanguagesForEbkAndQa().isEmpty())
					&& (contactType.equals(NOTIFICATION_TYPE_QA) || contactType.equals(NOTIFICATION_TYPE_EBK))) {
    			HashMap<String, Collection<OneTimeVO>> onetimeValues = session.getOneTimeValues();
    			Collection<OneTimeVO> languages = onetimeValues.get(ONETIME_LANGUAGE);
    			ArrayList<OneTimeVO> preferredLanguages = null;
    			if(languages != null && !languages.isEmpty()){
    				for(OneTimeVO oneTimeVO: languages){
    					if(PREFERRED_LANGUAGE_GERMAN.equals(oneTimeVO.getFieldValue()) || PREFERRED_LANGUAGE_ENGLISH.equals(oneTimeVO.getFieldValue())){
    						if(preferredLanguages == null){
    							preferredLanguages = new ArrayList<OneTimeVO>();
    						}
    						preferredLanguages.add(oneTimeVO);
    					}
    				}
    			}
    			// Added by A-7137 as part of the BUG ICRD-229549
    			session.setLanguagesForEbkAndQa(preferredLanguages);
			}
    		//CRQ ID: ICRD-162691 - A-5127 added to include only languages English and German for contacttypes QA and ebooking - end
    		session.setNotificationPreferences(preferenceVOs);
    		
		invocationContext.target=LIST_SUCCESS;
		
	}
	/**
	* @author a-7137
	* 
	* 	Method		:	ListNotificationPr	eferencesCommand.sort
	*	Added by 	:	a-7137 on 23-May-2016
	* 	Used for 	:	ICRD-152555. Sorting the Event Codes based on 'Customer Master Display Flag'
	*	Parameters	:	@param generalParameterConfigurationVOs
	*	Return type	: 	List<GeneralParameterConfigurationVO>
	 */
	private List<GeneralParameterConfigurationVO> sort(
			Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs) {
		List<GeneralParameterConfigurationVO> list = new ArrayList<GeneralParameterConfigurationVO>(generalParameterConfigurationVOs);
		Collections.sort(list, new Comparator<GeneralParameterConfigurationVO>(){
			   public int compare(GeneralParameterConfigurationVO generalParameterConfigurationVO1, 
					   GeneralParameterConfigurationVO generalParameterConfigurationVO2){
				   if(generalParameterConfigurationVO1.getConfigurationReferenceFive() != null && 
						   generalParameterConfigurationVO1.getConfigurationReferenceFive().length() > 0 && 
						   generalParameterConfigurationVO2.getConfigurationReferenceFive() != null && 
						   generalParameterConfigurationVO2.getConfigurationReferenceFive().length() > 0) {
					   return Integer.parseInt(generalParameterConfigurationVO1.getConfigurationReferenceFive()) - Integer.parseInt(generalParameterConfigurationVO2.getConfigurationReferenceFive());
				   }
				   else {
					   return 0;
				   }
			   }
			});
		return list;
	}
	
	
	
}


