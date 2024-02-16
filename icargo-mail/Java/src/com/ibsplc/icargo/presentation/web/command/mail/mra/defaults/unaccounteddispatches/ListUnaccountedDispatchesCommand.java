/*
 * ListUnaccountedDispatchesCommand.java Created on Aug 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
/**
 * @author A-2107
 *
 */
public class ListUnaccountedDispatchesCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String LIST_SUCCESS = "list_success";
		
	private static final String CLASS_NAME = "ListUnaccountedDispatchesCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.unaccounteddispatches";
	
	private static final String TARGET_FAILURE = "list_failure";    
	
	private static final String CURRENCY = "NZD";  
	
	
	
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		UnaccountedDispatchesSession session =
			(UnaccountedDispatchesSession) getScreenSession(
				MODULE_NAME, SCREENID);
		
		UnaccountedDispatchesForm unaccountedDispatchesForm = 
			(UnaccountedDispatchesForm)invocationContext.screenModel;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		UnaccountedDispatchesVO unaccountedDispatchesVO = new UnaccountedDispatchesVO();
	
		String companyCode = logonAttributes.getCompanyCode();
		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO =
			new UnaccountedDispatchesFilterVO();

		unaccountedDispatchesFilterVO.setCompanyCode(companyCode);
		log.log(Log.FINE, "*****CloseFlag", unaccountedDispatchesForm.getCloseFlag());
		if("fromdispatchEnquiry".equals(unaccountedDispatchesForm.getCloseFlag())){
			unaccountedDispatchesFilterVO = session.getUnaccountedDispatchesFilterVO();
		}
		else if("frommanualaccounting".equals(unaccountedDispatchesForm.getCloseFlag())){
			unaccountedDispatchesFilterVO = session.getUnaccountedDispatchesFilterVO();
		}else{
			errors = updateFilterVO(unaccountedDispatchesForm,unaccountedDispatchesFilterVO,logonAttributes);
			if (errors != null && errors.size() > 0) {
				unaccountedDispatchesForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}
		String displayPage = unaccountedDispatchesForm.getDisplayPage();
		unaccountedDispatchesFilterVO.setPageNumber(Integer.parseInt(displayPage));
		MailTrackingMRADelegate mailTrackingMRADelegate = 
    		new MailTrackingMRADelegate();
		Page<UnaccountedDispatchesDetailsVO> unaccountedDispatchesPage =null;
		try{
			session.setUnaccountedDispatchesFilterVO(unaccountedDispatchesFilterVO);
			unaccountedDispatchesPage = mailTrackingMRADelegate.listUnaccountedDispatches(unaccountedDispatchesFilterVO);
			if(unaccountedDispatchesPage != null && unaccountedDispatchesPage.size()>0){
				unaccountedDispatchesVO = mailTrackingMRADelegate.getTotalOfDispatches(unaccountedDispatchesFilterVO);
			}
		} catch(BusinessDelegateException businessDelegateException) {			
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
			errors.add(new ErrorVO("mailtracking.mra.defaults.error.noexchangerate"));
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
			
		}
		if(unaccountedDispatchesPage != null && unaccountedDispatchesPage.size()>0){
			unaccountedDispatchesVO.setCurrency(CURRENCY);
			unaccountedDispatchesVO.setUnaccountedDispatchesDetails(unaccountedDispatchesPage);
			session.setUnaccountedDispatchesVO(unaccountedDispatchesVO);
			session.setUnaccountedDispatchesFilterVO(unaccountedDispatchesFilterVO);
			log.log(Log.FINE, "*****in the oneTimeVO", unaccountedDispatchesVO.getPropratedAmt());
			unaccountedDispatchesForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = LIST_SUCCESS;
		}else{
			unaccountedDispatchesForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			session.setUnaccountedDispatchesVO(null);
			errors.add(new ErrorVO("mailtracking.mra.defaults.noresultsfound"));
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

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
			if (!fromDate.equals(toDate)) {
				if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
 		    	    errorsMail.add(new ErrorVO("mailtracking.mra.defaults.fromdategreatertodate"));
				}
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
    			errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidCarrier",
		   				new Object[]{flightCarrierCode.toUpperCase()}));
    			handleDelegateException(businessDelegateException);
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
				errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidairport",
		   				new Object[]{originPort.toUpperCase()}));
				handleDelegateException(businessDelegateException);
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
				errorsMail.add(new ErrorVO("mailtracking.mra.defaults.invalidairport",
		   				new Object[]{deptPort.toUpperCase()}));
				handleDelegateException(businessDelegateException);
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
	
	
	
	

}
