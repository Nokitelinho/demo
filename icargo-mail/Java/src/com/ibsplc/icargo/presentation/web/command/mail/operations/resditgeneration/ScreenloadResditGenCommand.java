/*
 * ScreenloadResditGenCommand.java Created on OCT 06 , 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.resditgeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResditGenerationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResditGenerationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 *
 */
public class ScreenloadResditGenCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILTRACKING");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.resditgeneration";

     
   private static final String ONETIME_RESDITEVENTS = "mailtracking.defaults.resditeventcode.screen";
   private static final String ONETIME_OFFLOADREASONCODES = "mailtracking.defaults.offload.reasoncode";
   private static final String ONETIME_RETURNREASONCODES = "mailtracking.defaults.return.reasoncode";
   private static final String ONETIME_CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String ONETIME_HNI = "mailtracking.defaults.highestnumbermail";
   private static final String ONETIME_RI = "mailtracking.defaults.registeredorinsuredcode";
  

	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenloadResditGenCommand","execute");

    	ResditGenerationForm resditGenerationForm =
    		(ResditGenerationForm)invocationContext.screenModel;
    	ResditGenerationSession resditGenerationSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	
    	
		//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Collection<ErrorVO> errors = null;
		resditGenerationSession.setMailbagVO(null);
		resditGenerationSession.setMailbagVOs(null);
		
		
		/*MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate(); 
		String agentCode="";
		try {

    		agentCode = mailTrackingDefaultsDelegate.findStockHolderForMail(logonAttributes.getCompanyCode(),
    				logonAttributes.getAirportCode(),logonAttributes.isGHAUser());		

    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	mailManifestSession.setAgentCode(agentCode);
    	log.log(Log.INFO,"\n\n\n\n  agentCode----->>"+agentCode);*/

		/*
		 * Getting the system parameters for
		 * whether stock request is required and
		 * the value of stock holder
		 */
	//	Collection<String> parameterTypes = new ArrayList<String>();
		//parameterTypes.add(STOCK_REQ_PARAMETER);
		/*
		 * Added By Karthick V as the part of the  Air New Zealand Mail Tracking CR 
		 * 
		 */
		//parameterTypes.add(MailConstantsVO.SHIPMENTDESCRIPTION_FORAWB);
		//parameterTypes.add(STOCK_HOLDER_PARAMETER);
		
		
		Map<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		
		resditGenerationSession.setResditEvents(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_RESDITEVENTS));
		resditGenerationSession.setOffloadReasonCodes(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_OFFLOADREASONCODES));
		resditGenerationSession.setReturnReasonCodes(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_RETURNREASONCODES));		
		resditGenerationSession.setCategory(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_CATEGORY));
		resditGenerationSession.setOneTimeHNI(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_HNI));
		resditGenerationSession.setOneTimeRI(
				(ArrayList<OneTimeVO>) oneTimeValues.get(ONETIME_RI));
		
		
		
		log.log(Log.FINE, "resditEvents", resditGenerationSession.getResditEvents());
		log.log(Log.FINE, "offloadReasonCodes", resditGenerationSession.getOffloadReasonCodes());
		log.log(Log.FINE, "returnReasonCodes", resditGenerationSession.getReturnReasonCodes());
		log.log(Log.FINE, "category", resditGenerationSession.getCategory());
		log.log(Log.FINE, "hnis", resditGenerationSession.getOneTimeHNI());
		log.log(Log.FINE, "ris", resditGenerationSession.getOneTimeRI());
		/*if(!coming from mailengury screen set the collection of mailbagvos to session of this screen.
          * OPERATION_OUTBOUND.equals(mailManifestForm.getFromScreen())){
        	 
         
		 mailManifestVO = new MailManifestVO();
		mailManifestSession.setMailManifestVO(mailManifestVO);
         }
         if(MailConstantsVO.OPERATION_OUTBOUND.equals(mailManifestForm.getFromScreen())){
        	 mailManifestVO=mailManifestSession.getMailManifestVO();
        	 populateForm(mailManifestVO, mailManifestForm);
         }
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailManifestSession.setFlightValidationVO(flightValidationVO);
		mailManifestForm.setInitialFocus(FLAG_YES);*/
		resditGenerationForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		if (("MAILBAG_ENQUIRY").equals(resditGenerationForm.getFromScreen())) {
			if (resditGenerationForm.getMailBagDetails() != null) {
				Collection<MailbagVO> mailbagVOs =new ArrayList<MailbagVO>();
				String mailBags = resditGenerationForm.getMailBagDetails();
				String [] mailBagDetails  = mailBags.split(",");
				for (String mailBagDetail : mailBagDetails) {
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setOoe(mailBagDetail.substring(0, 6));
					mailbagVO.setDoe(mailBagDetail.substring(6, 12));
					mailbagVO.setMailCategoryCode(mailBagDetail.substring(12, 13));
					mailbagVO.setMailSubclass(mailBagDetail.substring(13, 15));
					mailbagVO.setYear(Integer.parseInt(mailBagDetail.substring(15, 16)));
					mailbagVO.setDespatchSerialNumber(mailBagDetail.substring(16, 20));
					mailbagVO.setReceptacleSerialNumber(mailBagDetail.substring(20, 23));
					mailbagVO.setHighestNumberedReceptacle(mailBagDetail.substring(23, 24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBagDetail.substring(24, 25));
					//mailbagVO.setWeight(Double.parseDouble(mailBagDetail.substring(25, 29)));
					mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagDetail.substring(25, 29))));
					//mailbagVO.setStrWeight(mailBagDetail.substring(25, 29));
					mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagDetail.substring(25, 29))));//added by A-7371
					mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
					mailbagVOs.add(mailbagVO);
					resditGenerationSession.setMailbagVOs(mailbagVOs);  
				}
			}
		}else if("MailBagReconciliation".equalsIgnoreCase(resditGenerationForm.getFromScreen())){
			String mailBags = resditGenerationForm.getMailBagDetails();
			String [] mailBagDetails  = mailBags.split(",");
			Collection<MailbagVO> mailbagVOs =new ArrayList<MailbagVO>();
			for (String mailBagDetail : mailBagDetails) {
				String[] mailBagDetailForSetting = mailBagDetail.split(" ");
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setOoe(mailBagDetailForSetting[0]);   
				mailbagVO.setDoe(mailBagDetailForSetting[1]);
				mailbagVO.setMailCategoryCode(mailBagDetailForSetting[2]);
				mailbagVO.setMailSubclass(mailBagDetailForSetting[3]);
				mailbagVO.setYear(Integer.parseInt(mailBagDetailForSetting[4]));
				mailbagVO.setDespatchSerialNumber(mailBagDetailForSetting[5]);
				mailbagVO.setReceptacleSerialNumber(mailBagDetailForSetting[6]);
				mailbagVO.setHighestNumberedReceptacle(mailBagDetailForSetting[7]);
				mailbagVO.setRegisteredOrInsuredIndicator(mailBagDetailForSetting[8]);  
				//mailbagVO.setWeight(Double.parseDouble(mailBagDetailForSetting[9]));
				mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagDetailForSetting[9])));//added by A-7371
				//mailbagVO.setStrWeight(mailBagDetailForSetting[9]);
				mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagDetailForSetting[9])));//added by A-7371
				mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);  
				mailbagVOs.add(mailbagVO);
			}  
			resditGenerationSession.setMailbagVOs(mailbagVOs);  
		}
		//mailManifestForm.setDeparturePort(logonAttributes.getAirportCode());
    	invocationContext.target = TARGET;

    	log.exiting("ScreenloadResditGenCommand","execute");

    }
    /**
     * This method poulates form while coming from MailManifestScreen
     * @param mailManifestVO
     * @param mailManifestForm
     */
  /*  private void populateForm(MailManifestVO mailManifestVO,MailManifestForm mailManifestForm){
    	if(mailManifestVO.getFlightCarrierCode()!=null){
    		mailManifestForm.setFlightCarrierCode(mailManifestVO.getFlightCarrierCode());
    	}
    	if(mailManifestVO.getFlightNumber()!=null){
    		mailManifestForm.setFlightNumber(mailManifestVO.getFlightNumber());
    	}
    	if(mailManifestVO.getDepDate()!=null){
    		mailManifestVO.setStrDepDate(mailManifestVO.getDepDate().toDisplayDateOnlyFormat());
    		mailManifestForm.setDepDate(mailManifestVO.getDepDate().toDisplayDateOnlyFormat());
    	}
    }*/
    
    
    
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(){
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
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			 businessDelegateException.getMessage();
			 handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return oneTimeValues;
	}
	
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(ONETIME_RESDITEVENTS); 
    	parameterTypes.add(ONETIME_OFFLOADREASONCODES); 
    	parameterTypes.add(ONETIME_RETURNREASONCODES); 
    	parameterTypes.add(ONETIME_CATEGORY); 
    	parameterTypes.add(ONETIME_HNI);
    	parameterTypes.add(ONETIME_RI);
    	    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
