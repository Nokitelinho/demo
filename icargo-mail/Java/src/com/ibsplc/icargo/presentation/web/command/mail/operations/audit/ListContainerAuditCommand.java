/*
 * ListContainerAuditCommand.java Created on Jul 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.audit;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAuditFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ContainerAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListContainerAuditCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String SCREENID_AUD = "shared.audit.auditenquiry";

	private static final String MODULE_NAME_AUD = "shared.audit";
	
	
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.entering("ListContainerAuditCommand","execute");  
    	ContainerAuditForm containerAuditForm =
				(ContainerAuditForm)invocationContext.screenModel;
    	AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME_AUD,
				SCREENID_AUD);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	/**
    	 * Validate Mandatory fields
    	 */
    	errors = validateForm(containerAuditForm);
    	if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}

    	MailAuditFilterVO mailAuditFilterVO = new MailAuditFilterVO();
 	    mailAuditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
 	    if(containerAuditForm.getFlightCarrierCode() != null && !("").equals(containerAuditForm.getFlightCarrierCode())) {
 	    	mailAuditFilterVO.setFlightCarrierCode(containerAuditForm.getFlightCarrierCode().toUpperCase());
 	    	AirlineValidationVO airlineValidationVO = null;
 	    	try {        			
    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					mailAuditFilterVO.getFlightCarrierCode());

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {mailAuditFilterVO.getFlightCarrierCode()};
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
    			invocationContext.target = FAILURE;
    			return;
    		}
    		mailAuditFilterVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
 	    }
 	    if(containerAuditForm.getFlightNumber() != null && !("").equals(containerAuditForm.getFlightNumber())) {
 	    	mailAuditFilterVO.setFlightNumber(containerAuditForm.getFlightNumber().toUpperCase());
 	    }
 	    if(containerAuditForm.getFlightDate() != null && !("").equals(containerAuditForm.getFlightDate())) {
	    	LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			mailAuditFilterVO.setFlightDate(flightDate.setDate(containerAuditForm.getFlightDate()));
	    }
 	    if(containerAuditForm.getAssignPort() != null && !("").equals(containerAuditForm.getAssignPort())) {
 	    	mailAuditFilterVO.setAssignPort(containerAuditForm.getAssignPort().toUpperCase());
 	    }
 	    if(containerAuditForm.getContainerNumber() != null && !("").equals(containerAuditForm.getContainerNumber())) {
 	    	mailAuditFilterVO.setContainerNo(containerAuditForm.getContainerNumber().toUpperCase());
 	    }
 	    
 	    if(containerAuditForm.getTxnFromDate() != null && !("").equals(containerAuditForm.getTxnFromDate())) {
	    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			mailAuditFilterVO.setTxnFromDate(fromDate.setDate(containerAuditForm.getTxnFromDate()));
	    }
	    
	    if(containerAuditForm.getTxnToDate() != null && !("").equals(containerAuditForm.getTxnToDate())) {
	    	LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			mailAuditFilterVO.setTxnToDate(toDate.setDate(containerAuditForm.getTxnToDate()));
	    }
	    
	    
	    if(containerAuditForm.getFlightCarrierCode() != null 
    		&& !("").equals(containerAuditForm.getFlightCarrierCode())
    		&& containerAuditForm.getFlightNumber() != null 
    		&& !("").equals(containerAuditForm.getFlightNumber())
    		&& containerAuditForm.getFlightDate() != null 
    		&& !("").equals(containerAuditForm.getFlightDate())) {
	    
		    FlightFilterVO flightFilterVO = handleFlightFilterVO(containerAuditForm,
		    									mailAuditFilterVO,logonAttributes);
		    
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ",
						flightFilterVO);
				flightValidationVOs =
					new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				log.log(Log.FINE, "flightValidationVOs is NULL");
				Object[] obj = {mailAuditFilterVO.getFlightCarrierCode(),
						mailAuditFilterVO.getFlightNumber(),
						mailAuditFilterVO.getFlightDate().toString().substring(0,11)};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
				invocationContext.target = FAILURE;
				return;
			} 
			
			try {
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO,flightValidVO);
						break;
					}
				}
			} catch (SystemException systemException) {
				systemException.getMessage();
			}
			
			mailAuditFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			mailAuditFilterVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		
	    }else{
	    	if(containerAuditForm.getFlightNumber() != null 
	        		&& !("").equals(containerAuditForm.getFlightNumber())) {
	    		if(containerAuditForm.getFlightCarrierCode() == null 
		        		|| ("").equals(containerAuditForm.getFlightCarrierCode())
		        		|| containerAuditForm.getFlightDate() == null 
		        		|| ("").equals(containerAuditForm.getFlightDate())) {
	    			invocationContext.addError(new ErrorVO("mailtracking.defaults.audit.incompleteflight"));
					invocationContext.target = FAILURE;
					return;
		    	}
	    	}
	    	if(containerAuditForm.getFlightDate() != null 
	        		&& !("").equals(containerAuditForm.getFlightDate())) {
	    		if(containerAuditForm.getFlightCarrierCode() == null 
		        		|| ("").equals(containerAuditForm.getFlightCarrierCode())
		        		|| containerAuditForm.getFlightNumber() == null 
		        		|| ("").equals(containerAuditForm.getFlightNumber())) {
	    			invocationContext.addError(new ErrorVO("mailtracking.defaults.audit.incompleteflight"));
					invocationContext.target = FAILURE;
					return;
		    	}
	    	}
	    }
		
 	    
 	    Collection<AuditDetailsVO> auditDetailsVOs = new ArrayList<AuditDetailsVO>();
 	    try {
 	    	auditDetailsVOs = new MailTrackingDefaultsDelegate().findCONAuditDetails(mailAuditFilterVO);
 	    	log.log(Log.FINE, "auditDetailsVOs CON:", auditDetailsVOs);
 	    }catch(BusinessDelegateException businessDelegateException) {
 	    	handleDelegateException(businessDelegateException);
 	    }
 	    
 	    if (auditDetailsVOs == null || auditDetailsVOs.size() == 0) {
 	    	auditDetailsVOs = new ArrayList<AuditDetailsVO>();
 	    	invocationContext.addError(new ErrorVO("shared.audit.auditEnquiry.msg.err.noflightDetails"));
 	    }
 	    
 	    auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
	    invocationContext.target = SUCCESS;
	    
	    log.exiting("ListContainerAuditCommand","execute");  

	}
	
	
	 /**
     * Method to create the filter vo for flight validation
     * @param mailAcceptanceForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		ContainerAuditForm containerAuditForm,
    		MailAuditFilterVO mailAuditFilterVO,
			LogonAttributes logonAttributes){
 
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setCarrierCode(mailAuditFilterVO.getFlightCarrierCode());
		flightFilterVO.setFlightCarrierId(mailAuditFilterVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailAuditFilterVO.getFlightNumber());
		flightFilterVO.setDirection("O");
		flightFilterVO.setStringFlightDate(containerAuditForm.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(containerAuditForm.getFlightDate()));
		return flightFilterVO;
	}
    
    
	/**
	 * Used to validate for mandatory checks
	 * 
	 * @param containerAuditForm ContainerAuditForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ContainerAuditForm containerAuditForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		log.log(Log.FINE, "Dates  ---> from date", containerAuditForm.getTxnFromDate());
		log.log(Log.FINE, "Dates >>>>>>> to date", containerAuditForm.getTxnToDate());
		if((containerAuditForm.getTxnFromDate() != null && 
				containerAuditForm.getTxnFromDate().trim().length()>0) &&
			(containerAuditForm.getTxnToDate() != null && 
					containerAuditForm.getTxnToDate().trim().length()>0)){
			
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			LocalDate frmDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);	
			frmDate.setDate(containerAuditForm.getTxnFromDate());
			toDate.setDate(containerAuditForm.getTxnToDate());
			if(toDate.isLesserThan(frmDate)){
				ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.todatelessthanfrmdate");
				errors.add(error);						
			}	
		}else{
				if (containerAuditForm.getTxnFromDate() == null
						|| containerAuditForm.getTxnFromDate().trim().length() == 0) {

					ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txnfrmdateerror");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);

				}
				if (containerAuditForm.getTxnToDate() == null
						|| containerAuditForm.getTxnToDate().trim().length() == 0) {
					ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txntodateerror");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
		}

		return errors;

	}
    
}
