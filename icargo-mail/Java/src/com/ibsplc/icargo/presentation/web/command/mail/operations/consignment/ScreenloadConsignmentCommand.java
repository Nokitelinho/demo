/*
 * ScreenloadConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadConsignmentCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";
   private static final String MODULE_AIRLINE = "mailtracking.mra.airlinebilling";
   private static final String AIRLINE_SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
   private static final String BLANK = "";
   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenloadConsignmentCommand","execute");

    	ConsignmentForm consignmentForm =
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
//    	ListInterlineBillingEntriesSession billingSession = (ListInterlineBillingEntriesSession) getScreenSession(
//    			MODULE_AIRLINE, AIRLINE_SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
		//added by A_8353 for ICRD-274933 starts
		Map systemParameters = null;  
		SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();
		try {
			systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
			consignmentForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
    		}
    		else{
    			consignmentForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}
		//added by A_8353 for ICRD-274933 ends
//		billingSession.setFromScreen(BLANK);
		/*
	    * Getting OneTime values
	    */
       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
	   if(oneTimes!=null){
		   List<String> sortedOnetimes ;
		   Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
		   Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
		   Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
		   Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
		   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
		   //Added as part of CRQ ICRD-103713 by A-5526
		   Collection<OneTimeVO> subTypeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.subtype");
		   log.log(Log.FINE, "*******Getting OneTimeVOs***catVOs***", catVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***rsnVOs***", rsnVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", hniVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", mailClassVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", typeVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", subTypeVOs.size());

		  if(hniVOs!=null && !hniVOs.isEmpty()){
			  sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);


			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(rsnVOs!=null && !rsnVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: rsnVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);


			int i=0;
			for(OneTimeVO riVo: rsnVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
		consignmentSession.setOneTimeCat(catVOs);
		   consignmentSession.setOneTimeRSN(rsnVOs);
		   consignmentSession.setOneTimeHNI(hniVOs);
		   consignmentSession.setOneTimeMailClass(mailClassVOs);
		   consignmentSession.setOneTimeType(typeVOs);
		   //Added as part of CRQ ICRD-103713 by A-5526
		   consignmentSession.setOneTimeSubType(subTypeVOs);
		}
	    consignmentForm.setDirection("O");
		consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);   
		/*if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
			consignmentForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
    		}
    		else{
    			consignmentForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}*/
    	invocationContext.target = TARGET;
    	log.exiting("ScreenloadConsignmentCommand","execute");

    }

    /**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass");
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			  //Added as part of CRQ ICRD-103713 by A-5526
			fieldValues.add("mailtracking.defaults.consignmentdocument.subtype");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	/**
	 * added by A-8353
	 * @return systemParameterCodes
	 */
	private Collection<String> getSystemParameterCodes(){
		  Collection systemParameterCodes = new ArrayList();
		    systemParameterCodes.add("mail.operations.defaultcaptureunit");
		    return systemParameterCodes;
	  }
	 /**
	 * added by A-8353
	 * @return stationParameterCodes
	 */
  private Collection<String> getStationParameterCodes()
  {
    Collection stationParameterCodes = new ArrayList();
    stationParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
    return stationParameterCodes;
  }

}
