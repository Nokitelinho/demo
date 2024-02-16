/*
 * ScreenloadSearchConsignmentCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadSearchConsignmentCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";	
   
   private static final String ONETIME_CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String ONETIME_CLASS = "mailtracking.defaults.mailclass";
   private static final String ONETIME_RESDITEVENT = "mailtracking.defaults.resditevent";
   private static final String ONETIME_FLIGHTTYPE = "mailtracking.defaults.carditenquiry.flighttype";
   private static final String ONETIME_SEARCHMODE = "mailtracking.defaults.carditenquiry.searchmode";
   private static final String ONETIME_MAILSTATUS = "mailtracking.defaults.mailstatus";
   
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	
    	log.log(Log.FINE,"-*---ScreenloadSearchConsignmentCommand--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
    	log.entering("ScreenloadCarditEnquiryCommand","execute");
    	//Uncommented  by A-7929 for ICRD-253410
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();  
		SearchConsignmentForm carditEnquiryForm = 
    		(SearchConsignmentForm)invocationContext.screenModel;
		SearchConsignmentSession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		carditEnquirySession.setTotalPcs(0);
		carditEnquirySession.setTotalWeight(null);
		
    	carditEnquirySession.setMailbagVOsCollection(null);
    	carditEnquirySession.setMailBagVOsForListing(null);
    	carditEnquiryForm.setFromScreen("Disabled");
    	carditEnquirySession.removeCarditEnquiryVO();
    	carditEnquirySession.setOneTimeVOs(getOneTimeValues());
		carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		carditEnquiryForm.setPol(logonAttributes.getAirportCode());//Added by A-7929 for ICRD-253410
		
		if("POPUP".equals(carditEnquiryForm.getScreenMode())){
		    carditEnquiryForm.setScreenMode("POPUP");
		}else if("POPUP_CARDIT".equals(carditEnquiryForm.getScreenMode())){
			carditEnquiryForm.setScreenMode("POPUP_CARDIT");
		}else{
			carditEnquiryForm.setScreenMode("MAIN");
		}
		
    	invocationContext.target = TARGET;
    	log.exiting("ScreenloadSearchConsignmentCommand","execute");
    	
    }
    
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			log
					.log(
							Log.FINE,
							"****ScreenloadSearchConsignmentCommand**************************",
							getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		updateMilitaryClass(oneTimeValues.get(ONETIME_CLASS));
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(ONETIME_CATEGORY);
    	parameterTypes.add(ONETIME_CLASS);
    	parameterTypes.add(ONETIME_RESDITEVENT);
    	parameterTypes.add(ONETIME_FLIGHTTYPE);
    	parameterTypes.add(ONETIME_SEARCHMODE);
    	parameterTypes.add(ONETIME_MAILSTATUS);
    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
       
	/**
	 * Appends the values of various oneTimeVOs's fieldValues if they all have the
	 * same description 
	 * Feb 7, 2007, A-1739
	 * @param mailClasses
	 */
	private void updateMilitaryClass(Collection<OneTimeVO> mailClasses) {
		log.exiting("ScreenLoadCommand","updateClass");
		if(mailClasses != null && mailClasses.size() > 0) {
			Collection<Collection<OneTimeVO>> mailClassVOs = 
				new ArrayList<Collection<OneTimeVO>>();
			String classDesc = null;
			String fildDes=null;
			Collection<OneTimeVO> militaryVOs = 
				new ArrayList<OneTimeVO>();
			for(OneTimeVO mailClassVO : mailClasses) {
				//finding vos with same description, possibly military mail
				if(mailClassVO.getFieldDescription().equals(classDesc)) {
					militaryVOs.add(mailClassVO);
				} else {
					classDesc = mailClassVO.getFieldDescription();
					militaryVOs = new ArrayList<OneTimeVO>();
					militaryVOs.add(mailClassVO);
					
				}
				if(militaryVOs.size() > 1) {
					String fieldDesc=((ArrayList<OneTimeVO>)militaryVOs).get(0).getFieldDescription();
					if(!fieldDesc.equals(fildDes)){
						mailClassVOs.add(militaryVOs);	
						fildDes=fieldDesc;
					}
				}		
			}
			if(mailClassVOs.size() > 0) {
				for(Collection<OneTimeVO> oneTimeVOs : mailClassVOs) {
					if(oneTimeVOs.size() > 0) {
						mailClasses.removeAll(oneTimeVOs);				
						StringBuilder oneTimeVal = new StringBuilder(); 
						for(OneTimeVO militaryVO : oneTimeVOs) {
							oneTimeVal.append(militaryVO.getFieldValue()).
										append(MailConstantsVO.MALCLS_SEP);
						}
						OneTimeVO mailClassVO = oneTimeVOs.iterator().next();				
						mailClassVO.setFieldValue(
								oneTimeVal.substring(0,oneTimeVal.length()-1).toString());						
						mailClasses.add(mailClassVO);
					}
				}
			}
		}
		log.exiting("ScreenLoadCommand","updateClass");
	}
}
