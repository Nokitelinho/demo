/*
 * ValidateCommand.java Created on Jul 1 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.maildiscrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailDiscrepancyForm;
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

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ValidateMailStatusFilterCommand", "execute");

		MailDiscrepancyForm form = (MailDiscrepancyForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		AirlineValidationVO airlineValidationVOFlight = null;
		AirportValidationVO airportValidationVOCar = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();


		//Validating DATE components
		errors = validateDates(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}



		//Validating FLIGHT CARRIER Details
		if(form.getFlightCarrierCode()!=null){
		String flightCarrierCode = form.getFlightCarrierCode().toUpperCase();
		if (flightCarrierCode != null && flightCarrierCode.length()>0) {
			log.log(Log.FINE,"ValidateFlightCarrierDetails");
			Collection<ErrorVO> error = null;
			try {
				airlineValidationVOFlight = airlineDelegate.validateAlphaCode(companyCode,flightCarrierCode);

			}catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
			if (error != null && error.size() > 0) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.invalidflightcarrier",
						new Object[]{flightCarrierCode}));
				invocationContext.target = FAILURE;
				return;
			}

		}
		}

		//Validate Airport - POL
		String pol=form.getAirport().toUpperCase();
		if (pol != null && pol.length()>0) {
			log.log(Log.FINE,"ValidateAirPortPOLDetails");
			Collection<ErrorVO> error = null;
			try {
				airportValidationVOCar = areaDelegate.validateAirportCode(companyCode,pol);
			}catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
			if (error != null && error.size() > 0) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.invalidairport",
						new Object[]{pol}));
				invocationContext.target = FAILURE;
				return;
			}
		}
		else{
			invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.airportmandatory",
					new Object[]{pol}));
			invocationContext.target = FAILURE;
			return;
		}
		if(airlineValidationVOFlight != null){
			form.setFlightCarrierIdr(String.valueOf(airlineValidationVOFlight.getAirlineIdentifier()));
		}
		else{
			form.setFlightCarrierIdr(null);
		}

		form.setValidFlag(MailConstantsVO.FLAG_YES);
		invocationContext.target = SUCCESS;
		log.exiting("ValidateMailStatusFilterCommand", "execute");

	}

	private Collection<ErrorVO> validateDates(MailDiscrepancyForm mailStatusForm){
		log.log(Log.FINE,"ValidateDATEDetails");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String fromDate=mailStatusForm.getFromDate();
		String toDate=mailStatusForm.getToDate();
		if((fromDate!=null && fromDate.length() >0) && (toDate !=null && toDate.length() >0)){
			LocalDate frmDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailStatusForm.getFromDate());
			LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailStatusForm.getToDate());
			if(frmDat.isGreaterThan(toDat)){
				log.log(Log.FINE,"ValidateGreaterDATEDetails");
				errors.add(new ErrorVO("mailtracking.defaults.msg.err.fromdatecannotbegreater"));
			}
		}else{
			log.log(Log.FINE,"ValidateEqualDATEDetails");
			errors.add(new ErrorVO("mailtracking.defaults.msg.err.datefieldcannotbenull"));
		}
		return errors;
	}

}
