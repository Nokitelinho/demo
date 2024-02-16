/*
 * ListConsignmentCommand.java Created on July 1, 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ListConsignmentCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";
  private static final String MODULE_AIRLINE = "mail.mra.airlinebilling";

  private static final String AIRLINE_SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
  private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
	private static final String STATUS_NO_RESULTS = "mailtracking.defaults.consignment.status.noresultsfound";
	 private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
   /**
    * TARGET
    */
   private static final String LIST_SUCCESS = "list_success";
   private static final String BLANK = "";
   /**
    * DUMMY_FLIGHT_NO.
    */
   private static final String DUMMY_FLIGHT_NO = "-1";
  
    /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListConsignmentCommand","execute");    	  
    	ConsignmentForm consignmentForm = 
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 	
    	ListInterlineBillingEntriesSession billingSession = (ListInterlineBillingEntriesSession) getScreenSession(
   			MODULE_AIRLINE, AIRLINE_SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
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
		//added by A_8353 for ICRD-274933 ends
		//added by A_8353 for ICRD-274933 ends
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
			log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***",
					mailClassVOs.size());
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
		   
		   
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	consignmentSession.setConsignmentDocumentVO(null);
    	//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		consignmentSession.setWeightRoundingVO(unitRoundingVO);				
		setUnitComponent(logonAttributes.getStationCode(),consignmentSession);	
    	
    	consignmentForm.setSelectMail(null);
    	if ("fromInterLineBilling".equals(billingSession.getFromScreen())) {
			log.log(Log.FINE, "FromScreen....", billingSession.getFromScreen());
			ConsignmentFilterVO consignmentFilterVO = consignmentSession
					.getConsignmentFilterVO();
			consignmentForm.setConDocNo(consignmentFilterVO
					.getConsignmentNumber());
			consignmentForm.setPaCode(consignmentFilterVO.getPaCode());
			consignmentForm.setFromScreen(billingSession.getFromScreen());
			consignmentForm.setDisplayPage("1");
			consignmentForm.setLastPageNum("0");
			 billingSession.setFromScreen(BLANK);
		}
    	
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
			consignmentForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
    		}
    		else{
    			consignmentForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}
      
    	/*if("carditEnquiry".equals(consignmentForm.getFromScreen())){
    		log.log(Log.FINE, "From carditEnquiry Screen....");
    		consignmentForm.setDisplayPage("1");
    		consignmentForm.setLastPageNum("0");
    	}*/
    	errors = validateForm(consignmentForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		}else{ 
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(consignmentForm.getConDocNo().toUpperCase());
			consignmentFilterVO.setPaCode(consignmentForm.getPaCode().toUpperCase());
			consignmentFilterVO.setPageNumber(Integer.parseInt(consignmentForm
					.getDisplayPage()));
			if("Y".equals(consignmentForm.getCountTotalFlag())&& consignmentSession.getTotalRecords() != 0){
				consignmentFilterVO.setTotalRecords(consignmentSession.getTotalRecords());
			}else{
				consignmentFilterVO.setTotalRecords(-1);
			}
			ConsignmentDocumentVO consignmentDocumentVO = null;
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate().
				        findConsignmentDocumentDetails(consignmentFilterVO);
						
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			
			log.log(Log.FINE, "consignmentDocumentVO ===>>>>",
					consignmentDocumentVO);
			if(consignmentDocumentVO == null) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setOperationFlag("I");
				consignmentForm.setNewRoutingFlag("Y");
				invocationContext.addError(new ErrorVO(STATUS_NO_RESULTS));
				consignmentForm.setDisableListSuccess("N");
				log.log(Log.FINE, "consignmentDocumentVO IS NULL");
			}else {
				consignmentForm.setDisableListSuccess("Y");
				consignmentDocumentVO.setOperationFlag("U");
				log.log(Log.FINE, "consignmentDocumentVO IS not NULL",
						consignmentDocumentVO);
				consignmentForm.setDirection(consignmentDocumentVO.getOperation());
				int totalRecords = 0;
				if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
log.log(Log.FINE, "consignmentDocumentVO ===>>>>",
							consignmentDocumentVO);
					totalRecords = consignmentDocumentVO.getMailInConsignmentVOs().getTotalRecordCount();
					consignmentSession.setTotalRecords(totalRecords);
				}else {
					consignmentSession.setTotalRecords(totalRecords);
				}
			}
			consignmentDocumentVO.setConsignmentNumber(consignmentForm.getConDocNo().toUpperCase());
			consignmentDocumentVO.setPaCode(consignmentForm.getPaCode().toUpperCase());
			if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null) {
				resetFlightNumber(consignmentDocumentVO.getRoutingInConsignmentVOs());
			}
			consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
			consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}

		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListConsignmentCommand","execute");
	}
	

	/**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ConsignmentForm consignmentForm,LogonAttributes logonAttributes) {
		String conDocNo = consignmentForm.getConDocNo();
		String paCode = consignmentForm.getPaCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}else{
//    	validate PA code
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
									logonAttributes.getCompanyCode(),paCode.toUpperCase());	  			
		   			if(postalAdministrationVO == null) {
		  				Object[] obj = {paCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	
	/**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return 
	 */
	private void setUnitComponent(String stationCode,
			ConsignmentSession consignmentSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT);		
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			consignmentSession.setWeightRoundingVO(unitRoundingVO);			
			
		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
	}
	      
	/**    
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		//Collection<ErrorVO> errors = null;
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
			//errors = handleDelegateException(businessDelegateException);			
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}   
	/**
	 * For resetting the flight number.
	 * @param routingInConsignmentVOs
	 */
	private void resetFlightNumber(Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
		for (RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs){
			if (DUMMY_FLIGHT_NO.equals(routingInConsignmentVO.getOnwardFlightNumber())){
				routingInConsignmentVO.setOnwardFlightNumber("");
			}
		}
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
