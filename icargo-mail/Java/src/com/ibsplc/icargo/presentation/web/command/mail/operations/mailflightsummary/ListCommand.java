/*
 * ListCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import java.util.ArrayList;
import java.util.Collection;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * @author A-5991
 *
 */
public class ListCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";	
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";

   private static final String OUTBOUND = "O";
   private static final String FLAG_YES = "YES";     

	
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";    
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListCommand","execute");
    	
    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	if(("assignsuccess").equals(mailFlightSummaryForm.getPopupStatus())){
    		if(mailFlightSummarySession.getOperationalFlightVO()!=null){
    			log.log(Log.FINE, "FlightValidationVO --------->>",
						mailFlightSummarySession.getFlightValidationVO());
				mailFlightSummaryForm.setFlightCarrierCode(mailFlightSummarySession.getOperationalFlightVO().getCarrierCode());
    			mailFlightSummaryForm.setFlightNumber(mailFlightSummarySession.getOperationalFlightVO().getFlightNumber());
    			String conDt=TimeConvertor.toStringFormat(((LocalDate)mailFlightSummarySession.getOperationalFlightVO().getFlightDate()).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
    			mailFlightSummaryForm.setFlightDate(conDt);
    			mailFlightSummaryForm.setPopupStatus("");
    		}
    		
    	}
    	
    	// VALIDATING FORM
		errors = validateForm(mailFlightSummaryForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
			mailFlightSummaryForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
    	
    	
    	
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();    	
    	mailFlightSummarySession.setSummaryVOs(null);
    	
    
    	
    	if(!FLAG_YES.equals(mailFlightSummaryForm.getStatus())){
    		
    		mailFlightSummarySession.setFlightValidationVO(null);
    			// VALIDATE FLIGHT CARRIER CODE
        		String flightCarrierCode = mailFlightSummaryForm.getFlightCarrierCode();	
            	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
            		try {        			
            			airlineValidationVO = airlineDelegate.validateAlphaCode(
            					logonAttributes.getCompanyCode(),
            					flightCarrierCode.trim().toUpperCase());

            		}catch (BusinessDelegateException businessDelegateException) {
            			errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) { 
            			mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
            			mailFlightSummaryForm.setScreenStatusFlag(
            					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
            			errors = new ArrayList<ErrorVO>();
            			Object[] obj = {flightCarrierCode.toUpperCase()};
        				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.fltsummary.msg.err.invalidCarrier",obj);
    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors.add(errorVO);
            			invocationContext.addAllError(errors);
            			invocationContext.target = TARGET_FAILURE;
            			return;
            		}
            	}
        		
        		// VALIDATING FLIGHT NUMBER        		
        		FlightFilterVO flightFilterVO = handleFlightFilterVO(
        				mailFlightSummaryForm,
    						logonAttributes);
        		
        		flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
        		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
        		        		
        		Collection<FlightValidationVO> flightValidationVOs = null;

    			try {
    				log.log(Log.FINE, "FlightFilterVO ------------> ",
							flightFilterVO);
					flightValidationVOs =
    					mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

        		}catch (BusinessDelegateException businessDelegateException) {
        			errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			invocationContext.addAllError(errors);
        			mailFlightSummaryForm.setScreenStatusFlag(
        					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
    			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {//IF NO RESULTS RETURNED
    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
    				mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");    				
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.fltsummary.msg.err.noflightDetails");
    				errors = new ArrayList<ErrorVO>();
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
    				invocationContext.addAllError(errors);
    				mailFlightSummaryForm.setScreenStatusFlag(
    						ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    				invocationContext.target = TARGET_FAILURE;
    				return;
    			}
    			else if ( flightValidationVOs.size() == 1) { //IF ONLY 1 VO RETURNED        				
					log.log(Log.FINE, "--------------FlightValidationVOs has one VO");
					for (FlightValidationVO flightValidVO : flightValidationVOs) {						
						
						flightValidVO.setDirection(OUTBOUND);
						mailFlightSummarySession.setFlightValidationVO(flightValidVO);
						break;
					}
				}
    			else if(flightValidationVOs.size() > 1){ //IF MORE VOS RETURNED
    				mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
    				mailFlightSummaryForm.setScreenStatusFlag(
    						ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					log.log(Log.FINE, "--------------Duplicate flight VO");
					duplicateFlightSession.setFlightValidationVOs(
							(ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(flightFilterVO);  
					duplicateFlightSession.setScreenOfParent("mailflightsummary");
					mailFlightSummaryForm.setStatus("showDuplicateFlights");
					invocationContext.target = TARGET_FAILURE;
					return;
				}
        		    			
    	}
    		
    	if(FLAG_YES.equals(mailFlightSummaryForm.getStatus())) {
    		FlightValidationVO flightValidationVO=null;
    		flightValidationVO = duplicateFlightSession.getFlightValidationVO();    		
    		flightValidationVO.setDirection(OUTBOUND);
    		mailFlightSummarySession.setFlightValidationVO(flightValidationVO);
    	}	
    	
    	
log.log(Log.FINE, "FlightValidationVO --------->>",
				mailFlightSummarySession.getFlightValidationVO());
		if(mailFlightSummarySession.getFlightValidationVO()!=null){
    	FlightValidationVO flightValidationVO =mailFlightSummarySession.getFlightValidationVO();
    	// CREATING FILTER VO FOR LISTING
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	//operationalFlightVO.setFlightDate(flightValidationVO.getFlightDate());
    	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
    	operationalFlightVO.setFlightDate(date.setDate(
    			mailFlightSummaryForm.getFlightDate()));
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	//operationalFlightVO.setPou();
    	
    	log.log(Log.FINE, "OperationalFlightVO --------->>",
				operationalFlightVO);
		Collection<MailInFlightSummaryVO> mailInFlightSummaryVOs = null;
    	try {
			
			mailInFlightSummaryVOs =
				mailTrackingDefaultsDelegate.findMailsInFlight(operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "mailInFlightSummaryVOs --------->>",
				mailInFlightSummaryVOs);
		if (errors != null && errors.size() > 0) {
			mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
			mailFlightSummaryForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		if (mailInFlightSummaryVOs == null || mailInFlightSummaryVOs.size() == 0) {
			mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
			mailFlightSummaryForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.fltsummary.msg.err.norecords");
			invocationContext.addError(errorVO);
			invocationContext.target = TARGET_FAILURE;			
			return;
		}
		log.log(Log.FINE, "mailInFlightSummaryVOs --------->>",
				mailInFlightSummaryVOs);
		mailFlightSummaryForm.setScreenStatFlag("LIST");
		mailFlightSummarySession.setOperationalFlightVO(operationalFlightVO);
		mailFlightSummarySession.setSummaryVOs((Collection<MailInFlightSummaryVO>)mailInFlightSummaryVOs);
		
    	mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("ListCommand","execute");
    	
    }}
    
   
    /**
     * Method to create the filter vo for flight validation
     * @param mailFlightSummaryForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		MailFlightSummaryForm mailFlightSummaryForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(mailFlightSummaryForm.getFlightNumber().trim().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(true);
		flightFilterVO.setStringFlightDate(mailFlightSummaryForm.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(
 				mailFlightSummaryForm.getFlightDate()));
		return flightFilterVO;
	}
    
  
	
	/**
	 * This method is used for validating the form for the particular action
	 * @param mailFlightSummaryForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailFlightSummaryForm mailFlightSummaryForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		   		
			if (("").equals(mailFlightSummaryForm.getFlightCarrierCode())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.fltsummary.msg.err.noFlightCarrierCode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(mailFlightSummaryForm.getFlightNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.fltsummary.msg.err.noFlightNumber");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(mailFlightSummaryForm.getFlightDate())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.fltsummary.msg.err.noFlightDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
		
		

		return formErrors;
	}
	
	
       
}
