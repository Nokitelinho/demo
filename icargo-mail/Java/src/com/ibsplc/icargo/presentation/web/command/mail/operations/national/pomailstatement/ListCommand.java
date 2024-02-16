/*
 * ListCommand.java Created on Feb 01 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.national.POMailStatementVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.POMailStatementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4777
 *
 */

public class ListCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");



	private static final String MODULE_NAME = "mail.operations";	

	private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement";	
	private static final String SCREENLOAD_SUCCESS = "screenload_success"; 	  
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String MAIL_POSTAL_CODE ="mailtracking.defaults.national.postalcode";

	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {

		POMailStatementForm poMailStatementForm =(POMailStatementForm)invocationContext.screenModel;
		POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		poMailStatementSession.setSelectedPOMailStatementVOs(null);
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(poMailStatementForm);
		if (errors != null && errors.size() > 0) { 			
			invocationContext.addAllError(errors);
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		AirportValidationVO airportValidationVO = null;		
		airportValidationVO = validateairport(logonAttributes.getCompanyCode(),poMailStatementForm.getOrigin());
		if(airportValidationVO == null){			
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.notavalidairport",new Object[]{poMailStatementForm.getOrigin()}));			
		}
		
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
			
		}
		airportValidationVO = validateairport(logonAttributes.getCompanyCode(),poMailStatementForm.getDestination());
		if(airportValidationVO == null){			
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.notavalidairport",new Object[]{poMailStatementForm.getDestination()}));
				
		}
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
			
		}

		DSNEnquiryFilterVO dsnEnquiryFilterVO = new DSNEnquiryFilterVO() ;
		
		String category = poMailStatementForm.getCategory();
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(poMailStatementForm.getFromDate());
		LocalDate toDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(poMailStatementForm.getToDate());
		String consgNo = poMailStatementForm.getConsignmentNo();
		String consgOrigin = poMailStatementForm.getOrigin(); 
		String consgDestination = poMailStatementForm.getDestination();	
		Page<POMailStatementVO> pOMailStatementVOs = null;
		Map<String, String> parMap  = populateSystemParameter();
		dsnEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		//dsnEnquiryFilterVO.setPageNumber(Integer.parseInt(poMailStatementForm.getDisplayPage()));
		dsnEnquiryFilterVO.setMailCategoryCode(category);
		dsnEnquiryFilterVO.setFromDate(fromDate);
		dsnEnquiryFilterVO.setToDate(toDate);
		dsnEnquiryFilterVO.setConsignmentNumber(consgNo);
		dsnEnquiryFilterVO.setOriginOfficeOfExchange(consgOrigin);
		dsnEnquiryFilterVO.setDestinationOfficeOfExchange(consgDestination);
		if(parMap != null && parMap.size() >0){
			dsnEnquiryFilterVO.setPaCode(parMap.get(MAIL_POSTAL_CODE)) ;
		}
		
		  //modified by A-4810 for icrd-15155
		
		
		//Added by A-5170 for ICRD-20902 starts
		//HashMap indexMap = null;
		//HashMap finalMap = null;
		if("FIRST".equals(poMailStatementForm.getFirstList())){
			poMailStatementSession.setIndexMap(null);
			poMailStatementForm.setFirstList(null);
		}
		HashMap<String, String> indexMap = getIndexMap(poMailStatementSession.getIndexMap(), invocationContext); 
		HashMap<String, String> finalMap = null;
		
		/*if("FIRST".equals(poMailStatementForm.getFirstList())){
			poMailStatementSession.setIndexMap(null);
		}
		if (poMailStatementSession.getIndexMap() != null) {
			indexMap = poMailStatementSession.getIndexMap();
		}*/
		//Added by A-5170 for ICRD-20902 ends
						
		if (indexMap == null || (indexMap.keySet() != null && indexMap.keySet().isEmpty())) {

			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String dispPage = poMailStatementForm.getDisplayPage();
		int displayPage = Integer.parseInt(dispPage);
		dsnEnquiryFilterVO.setPageNumber(displayPage);

		String strAbsoluteIndex = (String) indexMap.get(dispPage);

		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}

		dsnEnquiryFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		
		//
		
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ",
					dsnEnquiryFilterVO);
			pOMailStatementVOs =
				mailTrackingDefaultsDelegate.findPOMailStatementDetails(dsnEnquiryFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if(pOMailStatementVOs == null ||pOMailStatementVOs.size() ==0 ){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.national.mailstatement.error.norecordsfound"));
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);   

		}else{
			poMailStatementSession.setSelectedPOMailStatementVOs(pOMailStatementVOs);
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);  
			
			//added by a-4810 for icrd-15155
			finalMap = indexMap;
			if (poMailStatementSession.getSelectedPOMailStatementVOs() != null) {
				finalMap = buildIndexMap(indexMap, poMailStatementSession.getSelectedPOMailStatementVOs());
				//poMailStatementSession.setIndexMap(finalMap);							
			}		
		}		
		//Added by A-5170 for ICRD-20902 starts
		poMailStatementSession.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
		//Added by A-5170 for ICRD-20902 ends
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
	
	
	
	private Collection<ErrorVO> validateForm(POMailStatementForm poMailStatementForm) {
		
		String category = poMailStatementForm.getCategory();		
		String fromDate = poMailStatementForm.getFromDate();
		String toDate = poMailStatementForm.getToDate();
		String consgOrigin = poMailStatementForm.getOrigin();
		String consgDestination = poMailStatementForm.getDestination();
		

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(category == null || category.trim().length() ==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.categorymandatory"));
			
		}
		if(fromDate == null || fromDate.length()==0){
			
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.fromDatemandatory"));
		}
		if(toDate == null || toDate.length()==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.toDatemandatory"));
		}
		
		if(consgOrigin == null || consgOrigin.trim().length() ==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.consgOriginmandatory"));
		}
		
		if(consgDestination == null || consgDestination.trim().length() ==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.mailstatement.error.consgDestinationmandatory"));
		}

		return errors;
	}
	
	private AirportValidationVO validateairport(String companyCode,String airportCode){
		AirportValidationVO   airportValidationVO = null;
		try{
			airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return airportValidationVO;
	}
	
	
	private Map<String, String> populateSystemParameter() {
		Map<String, String> parMap  = null;
		Collection<String> codes = new ArrayList<String>();
		codes.add(MAIL_POSTAL_CODE);
		try{
		parMap = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return parMap;
		
	}
	
	
	/**
	 * This method set the final index map value for pagination
	 * 
	 * @param existingMap
	 * @param page
	 * @return
	 */
	private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));

		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}

		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
	
	}
