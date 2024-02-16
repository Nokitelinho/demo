/*
 * GenerateReportCommand.java Created on MAR 13, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches.report;


import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2107
 *
 */
public class GenerateReportCommand  extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");	

	/**
	 * TARGET
	 */
	private static final String REPORT_ID = "RPTLST290";
	private static final String ACTION = "generateUnaccountedDispachedReport";
	private static final String TARGET_FAILURE = "list_failure";  

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("GenerateReportCommand","execute");
		UnaccountedDispatchesForm form = 
			(UnaccountedDispatchesForm)invocationContext.screenModel;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode();
		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO =
			new UnaccountedDispatchesFilterVO();

		unaccountedDispatchesFilterVO.setCompanyCode(companyCode);
		errors = updateFilterVO(form,unaccountedDispatchesFilterVO,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ReportConstants.NORMAL_REPORT_ERROR_JSP;
			return;
		}
		
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		Collection<OneTimeVO> reasonCodes=new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> mailCategory=new ArrayList<OneTimeVO>();
		if(oneTimes!=null){
			reasonCodes = oneTimes.get("mailtracking.mra.reasoncode");
		}
		if(oneTimes!=null){
			mailCategory = oneTimes.get("mailtracking.defaults.mailcategory");
		}
		ReportSpec reportSpec = getReportSpec();	
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(unaccountedDispatchesFilterVO);
		reportSpec.addExtraInfo(reasonCodes);
		reportSpec.addExtraInfo(mailCategory);
		reportSpec.setResourceBundle(form.getBundle());
		reportSpec.setAction(ACTION);
		
		generateReport();
		invocationContext.target = getTargetPage();
	}
	
	
	/**
	 * Method to update FilterVO
	 * @param unaccountedDispatchesForm
	 * @param unaccountedDispatchesFilterVO
	 * @param logonAttributes
	 * @return Collection<ErrorVO>
	 */
	
	private Collection<ErrorVO> updateFilterVO(
			UnaccountedDispatchesForm unaccountedDispatchesForm,
			UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO,
			LogonAttributes logonAttributes){
		Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
	
		LocalDate afd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		LocalDate atd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		AirlineDelegate airlineDelegate = new AirlineDelegate();
	    AirlineValidationVO airlineValidationVO = null;
		String fromDate = unaccountedDispatchesForm.getFromDate();
		
		if (fromDate != null && fromDate.trim().length() > 0) {
			unaccountedDispatchesFilterVO.setFromDate(afd.setDate(fromDate));
			unaccountedDispatchesFilterVO.setFlightFromDate(fromDate);
		}else{
			 errorsMail.add(new ErrorVO("mailtracking.mra.defaults.nofromdate"));
		}
		
		String toDate = unaccountedDispatchesForm.getToDate();
		if (toDate != null && toDate.trim().length() > 0) {
			unaccountedDispatchesFilterVO.setToDate(atd.setDate(toDate));
			unaccountedDispatchesFilterVO.setFlightToDate(toDate);
		}else{
			 errorsMail.add(new ErrorVO("mailtracking.mra.defaults.notodate"));
		}
		
		if(unaccountedDispatchesFilterVO.getToDate() != null 
				&&	unaccountedDispatchesFilterVO.getFromDate() != null){
			if(afd.after(atd)){
				
 		    	    errorsMail.add(new ErrorVO("mailtracking.mra.defaults.fromdategreatertodate"));
				
		    }
		}
		
//		 VALIDATE FLIGHT CARRIER CODE
		String flightCarrierCode = unaccountedDispatchesForm.getFlightCode();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {   
    		unaccountedDispatchesFilterVO.setCarrierCode(flightCarrierCode);
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					flightCarrierCode.trim().toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
    			handleDelegateException(businessDelegateException);
    			errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidCarrier",
		   				new Object[]{flightCarrierCode.toUpperCase()}));
    		}
    	}else{
			unaccountedDispatchesFilterVO.setCarrierCode("");
		}
    	
    	String fltNo = unaccountedDispatchesForm.getFlightNo();
		if(fltNo != null && fltNo.trim().length()>0){
			unaccountedDispatchesFilterVO.setFlightNumber(fltNo);
		}else{
			unaccountedDispatchesFilterVO.setFlightNumber("");
		}
		
		String originPort = unaccountedDispatchesForm.getOrigin();
		if (originPort != null && originPort.trim().length() > 0) {
			unaccountedDispatchesFilterVO.setDeparturePort(originPort);
			AirportValidationVO airportValidationVO = null;
			try {
    			airportValidationVO = new AreaDelegate().validateAirportCode(
					logonAttributes.getCompanyCode(),originPort.toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidairport",
		   				new Object[]{originPort.toUpperCase()}));
   			}
		}else{
			unaccountedDispatchesFilterVO.setDeparturePort("");
		}
				
		String deptPort = unaccountedDispatchesForm.getDestination();
		if (deptPort != null && deptPort.trim().length() > 0) {
			unaccountedDispatchesFilterVO.setFinalDestination(deptPort);
			AirportValidationVO airportValidationVO = null;
			try {
    			airportValidationVO = new AreaDelegate().validateAirportCode(
					logonAttributes.getCompanyCode(),deptPort.toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidairport",
		   				new Object[]{deptPort.toUpperCase()}));
   			}
			
		}else{
			unaccountedDispatchesFilterVO.setFinalDestination("");
		}
		
		String effectiveDate = unaccountedDispatchesForm.getEffectiveDate();
		if(effectiveDate != null && effectiveDate.trim().length()>0){
			unaccountedDispatchesFilterVO.setEffectiveDate(atd.setDate(effectiveDate));
			unaccountedDispatchesFilterVO.setUnaccountedDisptachEffDate(effectiveDate);
		}else{
			unaccountedDispatchesFilterVO.setUnaccountedDisptachEffDate("");
		}
		
		String reasonCode = unaccountedDispatchesForm.getReasonCode();
		if(reasonCode != null && reasonCode.trim().length()>0){
			unaccountedDispatchesFilterVO.setReasonCode(reasonCode);
		}else{
			unaccountedDispatchesFilterVO.setReasonCode("");
		}
	
		
		return errorsMail;
				
	}
	
	/**
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode){
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;	
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.mra.reasoncode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
