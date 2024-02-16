/*
 * SaveCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class SaveCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String OUTBOUND = "O";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_ASSIGNMENT_FLAG = "N";
   private static final String CONST_ARRIVED_FLAG = "N";
//   private static final String CONST_FLIGHT_DEPARTED = "DEP";
   private static final String CONST_OVERRIDE_FLAG = "OVERRIDE";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveCommand","execute");

    	AssignContainerForm assignContainerForm =
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	LocalDate currentDate = null;
    	LocalDate flightDeparture = null;
    	LocalDate departureDateForThreshold = null;
    	LocalDate thresholdForNonDeparted = null;
    	int daysbeforeDeparture = 0;
    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    	if (containerVOs != null && containerVOs.size() > 0) {
    	 for (ContainerVO vo : containerVOs) {
    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())) {
    			vo.setAcceptanceFlag(MailConstantsVO.FLAG_YES); 
    			vo.setArrivedStatus(CONST_ARRIVED_FLAG);
    			vo.setTransitFlag("Y");    //Added by A-5945 for ICRD-90046 
    		}
    		vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
    		vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
    	 }
    	}

    	 MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
    	 OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
         operationalFlightVO.setOwnAirlineCode(
             logonAttributes.getOwnAirlineCode());
         operationalFlightVO.setOwnAirlineId(
                 logonAttributes.getOwnAirlineIdentifier());

    	 String assignedto = assignContainerForm.getAssignedto();
    	 log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		/*
    	  * If assigned to flight, do validations for departed and on departed flights
    	 * also set values in the OperationalFlightVO
    	  */
    	 if (CONST_FLIGHT.equals(assignedto)) {
    		FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();

        	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
        	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
        	operationalFlightVO.setDirection(OUTBOUND);
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setPol(logonAttributes.getAirportCode());

        	currentDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);

        	/**
        	 * Added by Karthick.V
        	 */
        	/*
    		 * Validation for non-departed flight -
    		 * The current date should be <= FltDepDate
    		 * The current date should be >= FltDepDate - nondep_configuration
    		 */
			 //Modified by A-1739@NRT On 11-July-2007
    		 LocalDate flightDepTim = new LocalDate(logonAttributes.getAirportCode(),
    		 	ARP,true);
			  flightDepTim.setDate(assignContainerForm.getFlightDate());

        	if(currentDate.isLesserThan(flightDepTim)){

        		log.log(Log.FINE, "STATUS ------------>", assignContainerForm.getStatus());
				if (!CONST_OVERRIDE_FLAG.equals(assignContainerForm.getStatus())) {

        			String nonDepartureConfiguration = assignContainerSession.getNonDepartedConfiguration();
            		daysbeforeDeparture = Integer.parseInt(nonDepartureConfiguration);

           		    if(flightValidationVO.getAtd() != null){
    	    			 flightDeparture=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    		     flightDeparture.setDateAndTime(flightValidationVO.getAtd().toDisplayFormat());
    	    			 departureDateForThreshold=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    			 departureDateForThreshold.setDateAndTime(flightValidationVO.getAtd().toDisplayFormat());
        		    }else if(flightValidationVO.getEtd()!=null){
    	    			 flightDeparture=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    			 flightDeparture.setDateAndTime(flightValidationVO.getEtd().toDisplayFormat());
    	    			 departureDateForThreshold=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    			 departureDateForThreshold.setDateAndTime(flightValidationVO.getEtd().toDisplayFormat());
        		   }else{
    	    			 flightDeparture=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    			 flightDeparture.setDateAndTime(flightValidationVO.getStd().toDisplayFormat());
    	    			 departureDateForThreshold=new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    	    			 departureDateForThreshold.setDateAndTime(flightValidationVO.getStd().toDisplayFormat());
        			}
           		   thresholdForNonDeparted = departureDateForThreshold.addDays(-daysbeforeDeparture);
           		   log.log(Log.FINE, "THE CONGIGURED DAYS  IS ",
						daysbeforeDeparture);
				log.log(Log.FINE, "THE DEPARTURE DATE IS ", flightDeparture);
				log.log(Log.FINE, "THE departureDateForThreshold  IS ",
						departureDateForThreshold);
				log.log(Log.FINE,
						"THE departureDateForThreshold --- after  IS ",
						departureDateForThreshold);
				log.log(Log.FINE, "THE thresholdForNonDeparted  IS ",
						thresholdForNonDeparted);
					/*
           		    * currDate <= FlightDate && currDate >=threshold
           		    * If flight's scheduled departure date is in the future  and the 
           		    * current date is within threshold period before departure  date, 
           		    * then show warning.
           		    * 
           		    *  Otherwise if the departure date is in future but the 
           		    *  currendate is NOT within threshold period before departure,
           		    *  then error.
           		    *  i.e., if assignment should be possible to a flight with future scheduled
           		    *  departure date, then that should be in 'NEAR FUTURE', NOT in 'FAR FUTURE'
           		    *  which is determined by the value of 
           		    *  'mailtracking.defaults.assignment.nondepartedflights' 
           		    */
           		    if((currentDate.isLesserThan(flightDeparture)||
   		    								currentDate.equals(flightDeparture)) && 
   		    				(currentDate.isGreaterThan(thresholdForNonDeparted)||
   		    							currentDate.equals(thresholdForNonDeparted))){
           		    	 log.log(Log.INFO,"NO ERROR MESSAGE REQUIRED ");

           		    	 // WARNING MSG
           		    	 ErrorVO errorVO = new ErrorVO(
    		 					"mailtracking.defaults.assigncontainer.msg.wrn.fltDeparted");
    		 			 errors = new ArrayList<ErrorVO>();
    		 			 errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
    		 			 errors.add(errorVO);
    		 			 invocationContext.addAllError(errors);
    		 			 invocationContext.target = TARGET_FAILURE;
    		 			 return;

           		     }else{
           		    	log.log(Log.INFO," ERROR VO CREATED  ");
           		    	 ErrorVO errorVO = new ErrorVO(
            					"mailtracking.defaults.assigncontainer.msg.err.flightnotdeparted");
            			errors = new ArrayList<ErrorVO>();
            			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
            			errors.add(errorVO);
            			invocationContext.addAllError(errors);
            			invocationContext.target = TARGET_FAILURE;
            			return;
           		     }

        		}

        	}
        	else {
        		log.log(Log.FINE, "flightValidationVO.getAtd() ------------> ",
						flightValidationVO.getAtd());
				log.log(Log.FINE, "CurrentDate ------------> ", currentDate);
				LocalDate depDate = flightValidationVO.getApplicableDateAtRequestedAirport();
        		log.log(Log.FINE, "DepDate ------------> ", depDate);
				/*
        		 * Validation for departed flight -
        		 * The current date shoulfd be >= FltDepDate
        		 * The current date should be <= FltDepDate + dep_configuration
        		 */
        		 //MODIFIED BY JOJI @ NRT ON 10-JULY-2007
        		if (flightValidationVO.getAtd() != null) {
        			String depConfig = assignContainerSession.getDepartedConfiguration();
        			LocalDate newDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
        			newDate.setTimeInMillis(depDate.getTimeInMillis());
        			newDate.addDays(Integer.parseInt(depConfig));
        			log.log(Log.FINE, "DepDate **------------> ", depDate);
					log.log(Log.FINE, "NewDate **------------> ", newDate);
					if (currentDate.isLesserThan(depDate)
        					|| currentDate.isGreaterThan(newDate)) {

        				Object[] obj = {flightValidationVO.getCarrierCode(),
        						flightValidationVO.getFlightNumber(),
        						"departed"};
        				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.assigncontainer.msg.err.departueDateConfigError",obj);
    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors = new ArrayList<ErrorVO>();
    					errors.add(errorVO);
    					invocationContext.addAllError(errors);
    					invocationContext.target = TARGET_FAILURE;
    					return;
        			}
        		}
        	}

          // CHECKING WHETHER THE FLIGHT IS CLOSED FOR MAIL OPERATIONS
        	boolean isFlightClosed = false;
        	try {
        			isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);
        		
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
    			Object[] obj = {flightValidationVO.getCarrierCode(),
    					flightValidationVO.getFlightNumber(),
    					flightValidationVO.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
    			ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj);
    			errors = new ArrayList<ErrorVO>();
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    	}
    	 else {
           AirlineValidationVO airlineValidationVO =
               assignContainerSession.getAirlineValidationVO();

          operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
          operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
          operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
          operationalFlightVO.setFlightNumber("-1");
          operationalFlightVO.setFlightSequenceNumber(-1);
          operationalFlightVO.setLegSerialNumber(-1);
          operationalFlightVO.setPol(logonAttributes.getAirportCode());
          if (containerVOs != null && containerVOs.size() > 0) {
    		 for (ContainerVO vo : containerVOs) {
    			 if (ContainerVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())) {
    				 vo.setFlightSequenceNumber(-1);
    				 vo.setSegmentSerialNumber(-1);
    				 vo.setLegSerialNumber(-1);
    			 }
    		 }
          }
    	 }

    	if (containerVOs != null && containerVOs.size() > 0) {
    		/*
    		 * Construct lock vos for implicit locking
    		 */
    		Collection<LockVO> locks = prepareLocksForSave(containerVOs);
    		log.log(Log.FINE, "LockVO for implicit check", locks);
			try {
	    		log.log(Log.FINE,
						"operationalFlightVO going to save------------> ",
						operationalFlightVO);
				log.log(Log.FINE, "ContainerVOs going to save------------> ",
						containerVOs);
				mailTrackingDefaultsDelegate.saveContainers(
	    				operationalFlightVO,
	    				containerVOs,locks);

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
    	}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

    	invocationContext.target = TARGET_SUCCESS;

    	log.exiting("SaveCommand","execute");

    }
    /*
     * Added by Indu
     */
    private Collection<LockVO> prepareLocksForSave(
    		Collection<ContainerVO> containerVOs) {
    	log.log(Log.FINE, "\n prepareLocksForSave------->>", containerVOs);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();
    
    	if (containerVOs != null && containerVOs.size() > 0) {
    		for (ContainerVO conVO : containerVOs) {
    			ULDLockVO lock = new ULDLockVO();
    			lock.setAction(LockConstants.ACTION_ASSIGNCONTAINER);
    			lock.setClientType(ClientType.WEB);
    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    			lock.setScreenId(SCREEN_ID);
    			lock.setStationCode(logonAttributes.getStationCode());
    			lock.setUldNumber(conVO.getContainerNumber());
    			lock.setDescription(conVO.getContainerNumber());
    			lock.setRemarks(conVO.getContainerNumber());
    			log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
    		}
    	}
    	return locks;
    }

}
