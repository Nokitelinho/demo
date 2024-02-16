/**
 * ListCommand.java Created on February 20, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ListCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		dispatchEnquirySession.setDespatchDetailsVO(null);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(dispatchEnquiryForm);
		if (errors != null && errors.size() > 0) { 
			invocationcontext.addAllError(errors);
			dispatchEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
		}
		//Added by A-4810 as part of bug-fix icrd-15125
		dispatchEnquiryForm.setTransferFlag("");
		
		//		VALIDATING FLIGHT CARRIER
		String carrier = dispatchEnquiryForm.getFlightCarrierCode();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		errors = null;
		if (carrier != null && (carrier.trim().length() > 0)) {

			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(),
						carrier.trim().toUpperCase());

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, " errors.size() --------->>", errors.size());
				for(ErrorVO error:errors){
					error.setErrorCode("mailtracking.defaults.national.dispatchenquiry.error.invalidcarrier");
				}
				invocationcontext.addAllError(errors);
				invocationcontext.target = SCREENLOAD_FAILURE;
				return;
			}
		}
		//VALIDATING PORT
		String port = dispatchEnquiryForm.getAirport();
		errors = null;
		if (port != null && !"".equals(port)) {

			try {
				new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),port.toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				errors = new ArrayList<ErrorVO>();
				log.log(Log.FINE, " errors.size() --------->>", errors.size());
				errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.invalidairport",
						new Object[]{port.toUpperCase()}));
				invocationcontext.addAllError(errors);
				invocationcontext.target = SCREENLOAD_FAILURE;
				return;
			}
		}		
		Collection<ErrorVO> errorVOS = new ArrayList<ErrorVO>();
		if(dispatchEnquiryForm.getOrigin().trim().length()>0){
			AirportValidationVO airportValidationVO = null;		
			airportValidationVO = validateAirport(logonAttributes.getCompanyCode(),dispatchEnquiryForm.getOrigin().toUpperCase());
			if(airportValidationVO == null){			
				errorVOS.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.invalidstation",new Object[]{dispatchEnquiryForm.getOrigin().toUpperCase()}));			
			}

			if(errorVOS != null && errorVOS.size()>0){
				invocationcontext.addAllError(errorVOS);
				invocationcontext.target = SCREENLOAD_SUCCESS;
				return;
			}
		}
		if(dispatchEnquiryForm.getDestination().trim().length()>0){
			AirportValidationVO airportValidationVO = null;		
			airportValidationVO = validateAirport(logonAttributes.getCompanyCode(),dispatchEnquiryForm.getDestination().toUpperCase());
			if(airportValidationVO == null){			
				errorVOS.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.invalidstation",new Object[]{dispatchEnquiryForm.getDestination().toUpperCase()}));			
			}

			if(errorVOS != null && errorVOS.size()>0){
				invocationcontext.addAllError(errorVOS);
				invocationcontext.target = SCREENLOAD_SUCCESS;
				return;
			}
		}
		if("I".equals(dispatchEnquiryForm.getOperationType())){
			/*if(dispatchEnquiryForm.getFlightNo() == null ||dispatchEnquiryForm.getFlightNo().trim().length() == 0 || dispatchEnquiryForm.getFlightDate() == null || dispatchEnquiryForm.getFlightDate().trim().length() == 0){
				invocationcontext.addError(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.flightnumbermandatory"));
				invocationcontext.target = SCREENLOAD_FAILURE;
				return;
			}*/
		}
		Page<DespatchDetailsVO> despatchDetailsVOPage = null;
		DSNEnquiryFilterVO dsnEnquiryFilterVO =populatedsnEnquiryFilterVO(dispatchEnquiryForm , logonAttributes ,airlineValidationVO);
		
		//Added by A-5214 as part from the ICRD-21098 starts
		if(!"YES".equals(dispatchEnquiryForm.getCountTotalFlag()) && dispatchEnquirySession.getTotalRecords().intValue() != 0){
			dsnEnquiryFilterVO.setTotalRecords(dispatchEnquirySession.getTotalRecords().intValue());
	    }else
	    	{
	    	dsnEnquiryFilterVO.setTotalRecords(-1);
	    	}
		//Added by A-5214 as part from the ICRD-21098 ends
		
		dispatchEnquirySession.setDSNEnquiryFilterVO(dsnEnquiryFilterVO);
		try {
			despatchDetailsVOPage = new MailTrackingDefaultsDelegate().findDSNsForNational(dsnEnquiryFilterVO, Integer.parseInt(dispatchEnquiryForm.getDisplayPage()));
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		} 
		
		if (despatchDetailsVOPage != null && despatchDetailsVOPage.size() > 0) {
			log.log(Log.FINE, "Page --------->>", despatchDetailsVOPage);
			dispatchEnquirySession.setDespatchDetailsVO(despatchDetailsVOPage);
			dispatchEnquirySession.setTotalRecords(despatchDetailsVOPage.getTotalRecordCount());//Added by A-5214 as part from the ICRD-21098 
			dispatchEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}
		else {
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.national.dispatchenquiry.err.nodetails");
			dispatchEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationcontext.addAllError(errors);
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
		}
		/*if (errors != null && errors.size() > 0) {
			invocationcontext.addAllError(errors);
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
		}*/

		invocationcontext.target = SCREENLOAD_SUCCESS;
		log.exiting("ListCommand","execute");

	}

	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @return airportValidationVO
	 */
	private AirportValidationVO validateAirport(String companyCode,
			String airportCode) {
		AirportValidationVO   airportValidationVO = null;
		try{
			airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
		return airportValidationVO;
	}

	/**
	 * 
	 * @param dispatchEnquiryForm
	 * @param logonAttributes
	 * @param airlineValidationVO 
	 * @return dsnFilterVO - DSNEnquiryFilterVO
	 */
	private DSNEnquiryFilterVO populatedsnEnquiryFilterVO(
			DispatchEnquiryForm dispatchEnquiryForm,
			LogonAttributes logonAttributes, AirlineValidationVO airlineValidationVO) {

		DSNEnquiryFilterVO dsnFilterVO = new DSNEnquiryFilterVO();
		dsnFilterVO.setCarrierCode(dispatchEnquiryForm.getFlightCarrierCode().toUpperCase());
		dsnFilterVO.setPltEnabledFlag("FALSE");
		dsnFilterVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
		dsnFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		dsnFilterVO.setAirportCode(dispatchEnquiryForm.getAirport().toUpperCase());
		dsnFilterVO.setConsignmentNumber(dispatchEnquiryForm.getConsignmentNo());
		dsnFilterVO.setOriginCity(dispatchEnquiryForm.getOrigin().toUpperCase());		
		dsnFilterVO.setDestinationCity(dispatchEnquiryForm.getDestination().toUpperCase());		
		//dsnFilterVO.setOperationType(dispatchEnquiryForm.getOperationType());
		//modified for icrd-17657 by a-4810
		dsnFilterVO.setUserIdentifier(dispatchEnquiryForm.getUserId());		
		
		
		String status = dispatchEnquiryForm.getStatus();
		if(status!=null && status.trim().length()>0) {
			
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_OUTBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_INBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_INBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_NOTARRIVED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_INBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_NOTDELIVERED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_INBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_OUTBOUND);
			}else if("RET".equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_OUTBOUND);
			}else if(MailConstantsVO.MAIL_STATUS_NOTUPLIFTED.equalsIgnoreCase(status)) {
				dsnFilterVO.setOperationType(MailConstantsVO.OPERATION_OUTBOUND);
			}			
		}
		
		//Added by A-4810 for bug-icrd-15125 for disabling transfer button when listing with out bound operation.
		if("O".equals(dispatchEnquiryForm.getOperationType()))
		 {
		    dispatchEnquiryForm.setTransferFlag("N");
		 }
		 /*if("I".equals(dispatchEnquiryForm.getOperationType())){
			 dispatchEnquiryForm.setTransferFlag("");
		  }*/
		if(dispatchEnquiryForm.getFlightDate() != null && dispatchEnquiryForm.getFlightDate().trim().length() >0){
			LocalDate fltdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			dsnFilterVO.setFlightDate(fltdate.setDate(dispatchEnquiryForm.getFlightDate()));
		}
		if(dispatchEnquiryForm.getFlightNo() != null && dispatchEnquiryForm.getFlightNo().trim().length() > 0){
			dsnFilterVO.setFlightNumber(dispatchEnquiryForm.getFlightNo());
		}
		dsnFilterVO.setContainerType("B");
		dsnFilterVO.setFromDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false).setDate(dispatchEnquiryForm.getFromDate())));
		dsnFilterVO.setMailCategoryCode(dispatchEnquiryForm.getCategory());
		dsnFilterVO.setToDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false).setDate(dispatchEnquiryForm.getToDate())));	
		dsnFilterVO.setStatus(dispatchEnquiryForm.getStatus());
		return dsnFilterVO;

	}
	/**
	 * 
	 * @param dispatchEnquiryForm
	 * @return errors-Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			DispatchEnquiryForm dispatchEnquiryForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(dispatchEnquiryForm.getAirport() == null || dispatchEnquiryForm.getAirport().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.portmandatory"));
		}
		if(dispatchEnquiryForm.getFromDate() == null || dispatchEnquiryForm.getFromDate().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.fromdatemandatory"));
		}
		if(dispatchEnquiryForm.getToDate() == null || dispatchEnquiryForm.getToDate().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.todatemandatory"));
		}
		if(dispatchEnquiryForm.getOperationType() == null || dispatchEnquiryForm.getOperationType().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.optypemandatory"));
		}
		if(dispatchEnquiryForm.getFlightCarrierCode() == null || dispatchEnquiryForm.getFlightCarrierCode().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.dispatchenquiry.error.carriercodemandatory"));
		}
		return errors;
	}

}
