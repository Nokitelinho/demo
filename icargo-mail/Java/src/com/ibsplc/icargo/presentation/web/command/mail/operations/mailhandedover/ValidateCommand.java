/*
 * ValidateCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailhandedover;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailHandedOverReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ValidateCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String SUCCESS = "success";  
	private static final String FAILURE = "failure";   
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailhandedoverreport"; 

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ValidateCommand", "execute");

		MailHandedOverReportForm mailHandedOverReportForm =(MailHandedOverReportForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		AirlineValidationVO airlineValidationVOCarrier = null;
		AirlineValidationVO airlineValidationVOFlight = null;

		AirlineDelegate airlineDelegate = new AirlineDelegate();


		//Validating DATE components
		errors = validateDates(mailHandedOverReportForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}

		//Validating CARRIER Details
		String carrierCode = mailHandedOverReportForm.getCarrierCode().toUpperCase();
		if (carrierCode != null && carrierCode.length()>0) {
			log.log(Log.FINE,"ValidateCarrierDetails");
			Collection<ErrorVO> error = null;
			try {
				airlineValidationVOCarrier = airlineDelegate.validateAlphaCode(companyCode,carrierCode);

			}catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
			if (error != null && error.size() > 0) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailhandedover.msg.err.invalidcarrier",
						new Object[]{carrierCode}));
				invocationContext.target = FAILURE;
				return;
			}

		}

		//Validating FLIGHT CARRIER Details
		String flightCarrierCode = mailHandedOverReportForm.getFlightCarrierCode().toUpperCase();
		if (flightCarrierCode != null && flightCarrierCode.length()>0) {
			log.log(Log.FINE,"ValidateFlightCarrierDetails");
			Collection<ErrorVO> error = null;
			try {
				airlineValidationVOFlight = airlineDelegate.validateAlphaCode(companyCode,flightCarrierCode);

			}catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
			if (error != null && error.size() > 0) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailhandedover.msg.err.invalidflightcarrier",
						new Object[]{flightCarrierCode}));
				invocationContext.target = FAILURE;
				return;
			}

		}

		if(airlineValidationVOCarrier != null){
			int carrierCod=airlineValidationVOCarrier.getAirlineIdentifier();
			int ownCarrierCode=logonAttributes.getOwnAirlineIdentifier();
			
			if(carrierCod != ownCarrierCode){
				mailHandedOverReportForm.setCarrierId(String.valueOf(carrierCod));
			}
		}else{
			mailHandedOverReportForm.setCarrierId(null);
		}

		if(airlineValidationVOFlight != null){
			mailHandedOverReportForm.setFlightCarrierId(String.valueOf(airlineValidationVOFlight.getAirlineIdentifier()));
		}else{
			mailHandedOverReportForm.setFlightCarrierId(null);
		}
		
		mailHandedOverReportForm.setValidFlag(MailConstantsVO.FLAG_YES);
		invocationContext.target = SUCCESS;
		log.exiting("ValidateCommand", "execute");

	}

	private Collection<ErrorVO> validateDates(MailHandedOverReportForm mailHandedOverReportForm){
		log.log(Log.FINE,"ValidateDATEDetails");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String fromDate=mailHandedOverReportForm.getFromDate();
		String toDate=mailHandedOverReportForm.getToDate();
		if((fromDate!=null && fromDate.length() >0) && (toDate !=null && toDate.length() >0)){
			LocalDate frmDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailHandedOverReportForm.getFromDate());
			LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailHandedOverReportForm.getToDate());
			if(frmDat.isGreaterThan(toDat)){
				log.log(Log.FINE,"ValidateGreaterDATEDetails");
				errors.add(new ErrorVO("mailtracking.defaults.mailhandedover.msg.err.fromdatecannotbegreater"));
			}
		}else{
			log.log(Log.FINE,"ValidateEqualDATEDetails");
			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.datefieldcannotbenull"));
		}
		return errors;		
	}
}
