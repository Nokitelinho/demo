/*
 * SaveUploadCommand.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3817
 *
 */
public class SaveUploadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";
	/*
	 * INVOCATION TARGETS
	 */
	private static final String UPLOAD_SAVE_SUCCESS = "upload_save_success";
	/*
     * Module name and Screen id for duplicate flight
     */
    private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
    private static final String MODULE_NAME_FLIGHT = "flight.operation";
	/*
	 * System Parameter
	 */
	private static final String MAIL_COMMODITY_SYSPAR = "mailtracking.defaults.booking.commodity";
    /*
     * ERROR CODES
     */	
	private static final String RUN_TIME_EXCEPTION = "RTE";
	private static final String OPERATION_FAILED = "OP_FAILED";
	private static final String INVALID_CARRIER = "mailtracking.defaults.msg.err.invalidcarrier";
	private static final String INVALID_AIRPORT = "mailtracking.defaults.invalidairport";
	private static final String FLIGHT_NOT_PRESENT = "mailtracking.defaults.noflightdetails";
	private static final String FLIGHT_CANCELLED = "mailtracking.defaults.uploadmaildetails.flightcancelled";
	private static final String FLIGHT_OUTBOUND_CLOSED = "mailtracking.defaults.err.flightclosed";
	private static final String FLIGHT_INBOUND_CLOSED = "mailtracking.defaults.mailarrival.checkflightclosed";
	private static final String FLIGHT_DUPLICATE = "mailtracking.defaults.uploadmaildetails.flightduplicate";
	private static final String FLIGHT_MANDATORY_FOR_ARRIVAL = "mailtracking.defaults.err.flightdetailsmandatoryforarrival";
	private static final String FLIGHT_POU_INVALID = "mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou";
	private static final String MAILBAG_RETURNED = "mailtracking.defaults.mailbagalreadyreturned";
	public static final String  MAILBAG_DOESNOT_EXISTS = "mailtracking.defaults.mailbagdoesnotexists";
	public static final String  MAILBAG_ALREADY_DELIVERED = "mailtracking.defaults.mailbagalreadydelivered";
	private  static final String INVALID_ULDTYPE= "mailtracking.defaults.invaliduldType";
	public static final String CARRIER_CONTAINER_REASSIGN = "mailtracking.defaults.canreassigned";
	public static final String ULD_ALREADY_IN_USE_AT_ANOTHER_PORT = "mailtracking.defaults.err.uldalreadyinuseatanotherport";
	private static final String ULD_DOES_NOT_EXISTS = "uld.defaults.ulddoesnot.exists";
	private static final String ULD_IS_NOT_IN_AIRPORT = "uld.defaults.uldisnotinairport";
	private static final String ULD_IS_NOT_IN_THE_SYSTEM = "uld.defaults.uldisnotinthesystem";
	private static final String ULD_IS_NOT_OPERATIONAL = "uld.defaults.uldisnotoperational";
	private static final String ULD_DOES_NOT_EXISTS_MAIL = "mailtracking.defaults.warn.ulddoesnot.exists";
	private static final String ULD_IS_NOT_IN_AIRPORT_MAIL = "mailtracking.defaults.warn.uldisnotinairport";
	private static final String ULD_IS_NOT_IN_THE_SYSTEM_MAIL = "mailtracking.defaults.warn.uldisnotinthesystem";
	private static final String ULD_IS_NOT_OPERATIONAL_MAIL = "mailtracking.defaults.warn.uldisnotoperational";
	private static final String ULD_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "uld.defaults.warning.uldisnotinthesystem";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "mail.defaults.error.uldisnotinthesystem";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "mail.defaults.error.uldisnotinairport";
	private static final String MAIL_DEFAULTS_ERROR_ULDISLOST = "mail.defaults.error.uldislost";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "mail.defaults.error.uldisnotoperational";
	private static final String MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "mail.defaults.error.uldnotinairlinestock";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "mail.defaults.warning.uldisnotinthesystem";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "mail.defaults.warning.uldisnotinairport";
	private static final String MAIL_DEFAULTS_WARNING_ULDISLOST = "mail.defaults.warning.uldislost";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "mail.defaults.warning.uldisnotoperational";
	private static final String MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK = "mail.defaults.warning.uldnotinairlinestock";
	private static final String FLIGHT_TO_BE_ACTIONED = "mailtracking.defaults.uploadmaildetails.flighttobeactioned";
	
	/*
	 * Screen Specific Constants
	 */
	private static final String UNSAVED = "U";
	private static final String SAVED = "S";
	private static final String PARTIALLY_SAVED = "P";
	private static final String FLIGHT_STATUS_CANCELLED = "CAN";
	private static final String SEPARATOR = "~";
	private static double densityFactor = 0.0D;
	private static final String ROUTE_DELIMETER = "-";
	private static final String CARRIER_FLT_NUM = "-1";

	public void execute(InvocationContext invocationContext) 
	throws CommandInvocationException {		
		log.entering("SaveUploadCommand","execute");
		//Upload Mail Details Form		
		UploadMailDetailsForm uploadMailDetailsForm = 
			(UploadMailDetailsForm)invocationContext.screenModel;
		//Upload Mail Details session		
		UploadMailDetailsSession uploadMailDetailsSession = 
			(UploadMailDetailsSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		//Duplicate flight session		
		DuplicateFlightSession duplicateFlightSession = getScreenSession(MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String selectedRow = uploadMailDetailsForm.getSelectedScannedVOIndx();
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = uploadMailDetailsSession.getScannedMailDetailsVOs();
		ScannedMailDetailsVO selectedSession = null;
		if(selectedRow != null && selectedRow.trim().length() > 0 && 
				scannedMailDetailsVOs != null && scannedMailDetailsVOs.size() > 0){
			selectedSession = ((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs).get(Integer.parseInt(selectedRow));
			if(selectedSession != null){
				if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(selectedSession.getProcessPoint())){
					saveAcceptanceSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(selectedSession.getProcessPoint())){
					saveArrivalSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(selectedSession.getProcessPoint())){
					saveDeliverSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(selectedSession.getProcessPoint())){
					saveOffloadSession(selectedSession,logonAttributes);
				}else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedSession.getProcessPoint())){
					saveTransferSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(selectedSession.getProcessPoint())){
					saveReassignMailSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_DAMAGED.equals(selectedSession.getProcessPoint())){
					saveDamageSession(selectedSession,logonAttributes,duplicateFlightSession);
				}else if(MailConstantsVO.MAIL_STATUS_RETURNED.equals(selectedSession.getProcessPoint())){
					saveReturnSession(selectedSession,logonAttributes);
				}
			}
		}
		invocationContext.target=UPLOAD_SAVE_SUCCESS;
		log.exiting("SaveUploadCommand","execute");
	}
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 */
	private void saveAcceptanceSession(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveAcceptanceSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<MailAcceptanceVO>();
			MailAcceptanceVO mailAcceptanceVO = null;
			Collection<ErrorVO> fatalErrorsInAcceptance = null;

			//Validating Flight Details & Updating Exception				
			if(!updateExceptionDetails(scannedMailDetailsVO, 
					validateFlightForUploadSession(scannedMailDetailsVO,logonAttributes,duplicateFlightSession),null)){
				//Validating Container Details & Mailbags & Updating Exception					
				if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){			
					try{
						//Constructing MailAcceptanceVO
						if(scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
							mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO,logonAttributes);
						}
						if(mailAcceptanceVO != null){
							mailAcceptanceVOs.add(mailAcceptanceVO);
							//Saving Acceptance Session & Updating Exception
							updateExceptionAfterSave(scannedMailDetailsVO, 
									new MailTrackingDefaultsDelegate().saveScannedOutboundDetails(mailAcceptanceVOs));
						}
					}catch(BusinessDelegateException businessDelegateException){
						fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
						// Updating Exception	
						updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);
					}
				}
			}
		}
		log.exiting("saveAcceptanceSession","execute");	
	}

	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 */
	private void saveArrivalSession(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveArrivalSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
			Collection<MailArrivalVO> mailArrivalVOsToSave = null;
			Collection<ErrorVO> fatalErrorsInAcceptance = null;

			//Validating Flight Details & Updating Exception				
			if(!updateExceptionDetails(scannedMailDetailsVO, 
					validateFlightForUploadSession(scannedMailDetailsVO,logonAttributes,duplicateFlightSession),null)){				
				//Validating Container Details & Mailbags & Updating Exception					
				if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){
					try{
						//Constructing MailArrivalVOs(For Arrival & Delivery based on the Deliver Key)
						if(scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
							mailArrivalVOs = makeMailArrivalVOs(scannedMailDetailsVO,logonAttributes);
						}
						
						if(mailArrivalVOs != null && mailArrivalVOs.size() > 0){
							for(MailArrivalVO mailArrivalVO : mailArrivalVOs){
								//Saving Arrival Session & Updating Exception
								if(mailArrivalVO.isDeliveryNeeded()){
									mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
									mailArrivalVOsToSave.add(mailArrivalVO);
									new MailTrackingDefaultsDelegate().saveScannedDeliverMails(mailArrivalVOsToSave);
									updateExceptionAfterSave(scannedMailDetailsVO,null);
								}else{
									mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
									mailArrivalVOsToSave.add(mailArrivalVO);
									updateExceptionAfterSave(scannedMailDetailsVO, 
											new MailTrackingDefaultsDelegate().saveScannedInboundMails(mailArrivalVOs));
								}
							}
						}
					}catch(BusinessDelegateException businessDelegateException){
						fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
						// Updating Exception	
						updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);
					}
				}
			}
		}
		log.exiting("saveArrivalSession","execute");	
	}
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 */
	private void saveTransferSession(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveTransferSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<ErrorVO> fatalErrorsInAcceptance = null;
			String toPrint = MailConstantsVO.FLAG_NO;
			//Validating Flight Details & Updating Exception				
			if(!updateExceptionDetails(scannedMailDetailsVO, 
					null,null)){	
				
				if(!updateExceptionDetails(scannedMailDetailsVO, 
						validateOutboundFlightForTransferSession(scannedMailDetailsVO, logonAttributes, duplicateFlightSession),null)){
					/**
					 * For transferring to other carrier
					 */
					if(!logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getToCarrierCode())){
						scannedMailDetailsVO.setContainerNumber(null);
						toPrint = MailConstantsVO.FLAG_SCANNED;
					}
					//Validating Container Details & Mailbags & Updating Exception					
					if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){
						try{
							if(scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0 ){
								
								ContainerVO toContainerVO = null;
								//Constructing toContainerVO 
								if(scannedMailDetailsVO.getValidatedContainer()!=null){
									/**
									 *  Constructing the tocontainerVO for the following cases
									 * 1. For transferring mailbags from flight to flight
									 * 2. For transferring mailbags from flight to ownCarrier
									 * 
									 */
								 toContainerVO = constructContainerVO(scannedMailDetailsVO.getValidatedContainer(),logonAttributes);
								}else{
									/**
									 * For constructing the tocontainerVO for the following case
									 * 1. For transferring mailbags from flight to other Airline (OAL) 
									 */
									toContainerVO = constructContainerVO(scannedMailDetailsVO,logonAttributes);
								}
								//Transferring Mailbags									
								new MailTrackingDefaultsDelegate().transferMail(null, scannedMailDetailsVO.getMailDetails(), toContainerVO, toPrint);
								
								updateExceptionAfterSave(scannedMailDetailsVO, null);
							}
						}catch(BusinessDelegateException businessDelegateException){
							fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
							// Updating Exception	
							updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);
						}
					}
				}
			}		 
		}
		log.exiting("saveTransferSession","execute");	
	}
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 */
	private void saveReassignMailSession(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveAcceptanceSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<ErrorVO> fatalErrorsInAcceptance = null;

			//Validating Flight Details & Updating Exception				
			if(!updateExceptionDetails(scannedMailDetailsVO, 
					validateFlightForUploadSession(scannedMailDetailsVO,logonAttributes,duplicateFlightSession),null)){
				
				//Validating Container Details & Mailbags & Updating Exception					
				if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){			
					
					try{
						if(scannedMailDetailsVO.getMailDetails() != null && 
								scannedMailDetailsVO.getMailDetails().size() > 0 &&
								scannedMailDetailsVO.getValidatedContainer()!=null ){
							//Constructing toContainerVO
							ContainerVO toContainerVO = constructContainerVO(scannedMailDetailsVO.getValidatedContainer(),logonAttributes);
							//Reassigning Mailbags	
							new MailTrackingDefaultsDelegate().reassignMailbags(scannedMailDetailsVO.getMailDetails(), toContainerVO);							
						}
						if(scannedMailDetailsVO.getScannedContainerDetails() != null && 
								scannedMailDetailsVO.getScannedContainerDetails().size() > 0){
							//Constructing operationalFlightVO
							OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(scannedMailDetailsVO,logonAttributes);
							//Reassigning Containers
							new MailTrackingDefaultsDelegate().reassignContainers(scannedMailDetailsVO.getScannedContainerDetails(), operationalFlightVO);							
						}
						updateExceptionAfterSave(scannedMailDetailsVO, null);
					}catch(BusinessDelegateException businessDelegateException){
						fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
						// Updating Exception	
						updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);
					}
				}
			}
		}
		log.exiting("saveAcceptanceSession","execute");	
	}
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 */
	private void saveReturnSession(ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes){
		log.entering("saveReturnSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<ErrorVO> fatalErrorsInAcceptance = null;			
			//Validating Mailbags & Updating Exception					
			if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,null)){
				try{
					if(scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
						updateExceptionAfterSave(scannedMailDetailsVO, 
								new MailTrackingDefaultsDelegate().returnScannedMailbags(logonAttributes.getAirportCode(), scannedMailDetailsVO.getMailDetails()));
					}
				}catch(BusinessDelegateException businessDelegateException){
					fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
					updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);			
				}
			}
		}
		log.exiting("saveReturnSession","execute");	
	}
	
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 */
	private void saveOffloadSession(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes){
		log.entering("saveOffloadSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<OffloadVO> offloadVOsForSave = null;
			Collection<ErrorVO> fatalErrorsInAcceptance = null;

			//Validating Mailbags & Updating Exception					
			if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,null)){			
				try{
					//Constructing OffloadVos
					if((scannedMailDetailsVO.getMailDetails() != null && 
							scannedMailDetailsVO.getMailDetails().size() > 0) || 
							(scannedMailDetailsVO.getScannedContainerDetails() != null && 
									scannedMailDetailsVO.getScannedContainerDetails().size() > 0)) {
						offloadVOsForSave = makeOffloadVOs(scannedMailDetailsVO,logonAttributes);
					}
					if(offloadVOsForSave != null && offloadVOsForSave.size() >0){
						//Saving Offload Session
						new MailTrackingDefaultsDelegate().saveScannedOffloadMails(offloadVOsForSave);
						//No Error will be brought from Server, so passign NULL to updateExceptionAfterSave
						updateExceptionAfterSave(scannedMailDetailsVO, null);
					}
				}catch(BusinessDelegateException businessDelegateException){
					fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
					// Updating Exception	
					updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);
				}
			}
		}
		log.exiting("saveOffloadSession","execute");	
	}
	
	/**
	 * This method will validate the Container and Mailbag.
	 * 
	 * If any exception is thrown from the container validation,
	 * the whole session will be suspended from saving.
	 * 
	 * If any exception is thrown from the mailbag validation,
	 * the particular mailbag will be omitted from 
	 * the consecutive upload process cycle
	 *  
	 * @param scannedVO
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private boolean isCurrentUploadSessionValid(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("validateUploadSession","execute");
		boolean isErrorSevere = false;
		Collection<ErrorVO> fatalErrors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errorWarnings = new ArrayList<ErrorVO>();
		Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
		Collection<ErrorVO> containerSpecificErrors = new ArrayList<ErrorVO>();
		Collection<MailbagVO> validatedMailbagVOs = null;
		ScannedMailDetailsVO validatedScannedMailDetailsVO = null;
		if(!scannedMailDetailsVO.isAcknowledged()){
			if(scannedMailDetailsVO.getMailDetails() != null && 
					scannedMailDetailsVO.getMailDetails().size() > 0){
				for(MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()){
					scannedMailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());		
					scannedMailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());	
					scannedMailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
					scannedMailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
					scannedMailbagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
					scannedMailbagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
					scannedMailbagVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
					scannedMailbagVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
				}
			}
			try{
				validatedScannedMailDetailsVO = new MailTrackingDefaultsDelegate().findDetailsForUpload(scannedMailDetailsVO);			
				if(validatedScannedMailDetailsVO != null){
					validatedMailbagVOs = validatedScannedMailDetailsVO.getMailDetails();
					containerSpecificErrors = validatedScannedMailDetailsVO.getContainerSpecificErrors();
				}
				if(containerSpecificErrors != null && containerSpecificErrors.size() > 0){
					ArrayList<String> errorContainersToRemove = new ArrayList<String>();
					ArrayList<ContainerVO> scannedContainersToRemove = new ArrayList<ContainerVO>();
					for(ErrorVO errorVO : containerSpecificErrors){
						
						if(ErrorDisplayType.ERROR.equals(errorVO.getErrorDisplayType())){
							fatalErrors.add(errorVO);
						}else{
							errorWarnings.add(errorVO);
						}
						if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint()) || 
								MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())){
							  Object [] obj = errorVO.getErrorData();
							  if(obj != null && obj.length >0){
								  errorContainersToRemove.add((String)obj[0]);
							  }
						}
					}
					if(errorContainersToRemove.size() > 0 &&
							(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint()) ||
									MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint()))){
						for(String containerNumber : errorContainersToRemove){
							if(validatedScannedMailDetailsVO.getScannedContainerDetails() != null){
								for(ContainerVO scannedContainer : validatedScannedMailDetailsVO.getScannedContainerDetails()){
									if(containerNumber.equals(scannedContainer.getContainerNumber())){
										scannedContainersToRemove.add(scannedContainer);
										break;
									}
								}
							}else{
								break;
							}
						}
						// Removing the Containers with error from the Scanned Container List
						if(validatedScannedMailDetailsVO.getScannedContainerDetails() != null && 
								validatedScannedMailDetailsVO.getScannedContainerDetails().size() > 0 && 
								scannedContainersToRemove != null && scannedContainersToRemove.size() > 0){
							validatedScannedMailDetailsVO.getScannedContainerDetails().removeAll(scannedContainersToRemove);
						}
					}
					// Updating Container Exception	
					if(fatalErrors.size() > 0 || errorWarnings.size() > 0){
						isErrorSevere = updateExceptionDetails(scannedMailDetailsVO,fatalErrors,errorWarnings);
					}
				} if(validatedMailbagVOs != null && validatedMailbagVOs.size() > 0){
					for(MailbagVO mailbagVO : validatedMailbagVOs){
						if(mailbagVO.getErrorType() != null && !"Y".equals(mailbagVO.getAcknowledge())){
							mailbagErrorVOs.add(mailbagVO);
						}
					}
					validatedMailbagVOs.removeAll(mailbagErrorVOs);
					scannedMailDetailsVO.setMailDetails(validatedMailbagVOs);
					scannedMailDetailsVO.setValidatedContainer(validatedScannedMailDetailsVO.getValidatedContainer());
					scannedMailDetailsVO.setScannedContainerDetails(validatedScannedMailDetailsVO.getScannedContainerDetails());
					
					//Updating Mailbag Exception
					updateExceptionAfterMailValidation(scannedMailDetailsVO, mailbagErrorVOs);
					
				} 
				scannedMailDetailsVO.setSegmentSerialNumber(validatedScannedMailDetailsVO.getSegmentSerialNumber());
				scannedMailDetailsVO.setScannedContainerDetails(validatedScannedMailDetailsVO.getScannedContainerDetails());
				scannedMailDetailsVO.setMailDetails(validatedScannedMailDetailsVO.getMailDetails());
				scannedMailDetailsVO.setValidatedContainer(validatedScannedMailDetailsVO.getValidatedContainer());
			}catch (BusinessDelegateException businessDelegateException) {
				// Updating Exception	
				isErrorSevere = updateExceptionDetails(scannedMailDetailsVO,handleDelegateException(businessDelegateException),null);
			}
		}
		
		if((scannedMailDetailsVO.getPol() == null || scannedMailDetailsVO.getPol().trim().length() == 0 ) && 
				MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())){
			FlightValidationVO flightValidationVO = null;
			if(duplicateFlightSession != null) {
				flightValidationVO = duplicateFlightSession.getFlightValidationVO();
			}
			if(flightValidationVO != null){
				String route = flightValidationVO.getFlightRoute();
				String[] routeArr = route.split("-");
				Collection<String> routeColln = new ArrayList<String>();
				for(int index=0;index<routeArr.length;index++){
					if(routeArr[index].equals(logonAttributes.getAirportCode())){
						scannedMailDetailsVO.setPol(routeArr[index-1]);
						break;
					}
					routeColln.add(routeArr[index]);
				}
			}
		}
		log.exiting("validateUploadSession","execute");
		if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())|| 
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())	){
			if((scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() >0)
					|| (scannedMailDetailsVO.getScannedContainerDetails()!= null && scannedMailDetailsVO.getScannedContainerDetails().size() >0)){
				return true;
			}
		}
		/*if(isErrorSevere){
			return false;
		}else{
			return true;
		}*/
		return !isErrorSevere;
	}
	/**
	 * @author A-3227
	 * validateFlightForUploadSession
	 * @param scannedVO
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private Collection<ErrorVO> validateFlightForUploadSession(
			ScannedMailDetailsVO scannedVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession) {
		log.entering("validateFlightForUploadSession","execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
        
		/**
		 * Added for Bug 87047 starts
		 * 
		 */

		//For validating destination starts
		AirportValidationVO airportValidationVO = null;
		String destination = scannedVO.getDestination();        	
		if (destination != null && !"".equals(destination)) { 
			destination = scannedVO.getDestination().trim().toUpperCase(); 
			try {        			
				airportValidationVO = new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),destination);
			}catch (BusinessDelegateException businessDelegateException) {
				//errorVOs = handleDelegateException(businessDelegateException);
			}
			if (airportValidationVO == null) {            			
				Object[] obj = {destination};
				ErrorVO errorVO = new ErrorVO(INVALID_AIRPORT,obj);
				errorVOs.add(errorVO);
				return errorVOs;
			}
		}

//		For validating destination ends

		//For validating POU Starts
		airportValidationVO = null;
		String pou = scannedVO.getPou();        	
		if (pou != null && !"".equals(pou)) { 
			pou = scannedVO.getPou().trim().toUpperCase(); 
			try {        			
				airportValidationVO = new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),pou);
			}catch (BusinessDelegateException businessDelegateException) {
				//errorVOs = handleDelegateException(businessDelegateException);
			}

			if (airportValidationVO == null) {            			
				Object[] obj = {pou};
				ErrorVO errorVO = new ErrorVO(INVALID_AIRPORT,obj);
				errorVOs.add(errorVO);
				return errorVOs;
			}
		}

//		For validating POU Ends
		
//		For validating POL Starts
		airportValidationVO = null;
		String pol = scannedVO.getPol();        	
		if (pol != null && !"".equals(pol)) { 
			pol = scannedVO.getPol().trim().toUpperCase(); 
			try {        			
				airportValidationVO = new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),pol);
			}catch (BusinessDelegateException businessDelegateException) {
				//errorVOs = handleDelegateException(businessDelegateException);
			}

			if (airportValidationVO == null) {            			
				Object[] obj = {pol};
				ErrorVO errorVO = new ErrorVO(INVALID_AIRPORT,obj);
				errorVOs.add(errorVO);
				return errorVOs;
			}
		}

//		For validating POLEnds
		/**
		 * Added for Bug 87047 ends
		 * 
		 */
		
		String alphaCode = scannedVO.getCarrierCode().toUpperCase();
		/*
		 * Airline Validation
		 */
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
				scannedVO.getCompanyCode(), alphaCode, errorVOs);
		if (errorVOs != null && errorVOs.size() > 0) { 
			return errorVOs;
		}
		
		if(airlineValidationVO != null) {
			
			scannedVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			
			if(scannedVO.getFlightNumber() != null 
					&& scannedVO.getFlightNumber().trim().length() > 0
					&& !"-1".equals(scannedVO.getFlightNumber())){
				/*
				 * Validating Flight
				 */
				Collection<FlightValidationVO> flightValidationVOs =
					populateFlightValidationVO(scannedVO,airlineValidationVO,logonAttributes, errorVOs);
				if (errorVOs != null && errorVOs.size() > 0) { 
					return errorVOs;
				}
				/*
				 * If no error and flightValidationVOs is not null
				 */
				if(flightValidationVOs != null && flightValidationVOs.size() > 0){
					/*
					 * If the size of flightValidationVOs is 1
					 * then obtain the flightValidationVO from
					 * the first element of the collection
					 */
					if(flightValidationVOs.size() == 1){					
						duplicateFlightSession.setFlightValidationVO(
								((ArrayList<FlightValidationVO>)flightValidationVOs).get(0));				

						FlightValidationVO flightValidationVO = duplicateFlightSession.getFlightValidationVO();
						if(!MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedVO.getProcessPoint()) && !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedVO.getProcessPoint())){
							if(FLIGHT_STATUS_CANCELLED.equalsIgnoreCase(flightValidationVO.getFlightStatus())){
								StringBuffer canErrorMsg = new StringBuffer("");
								canErrorMsg.append(scannedVO.getCarrierCode()).append("-")
								.append(scannedVO.getFlightNumber()).append(" ").append(scannedVO.getFlightDate().toString().substring(0,11));
								Object[] obj = {canErrorMsg.toString()};
								ErrorVO error = new ErrorVO (FLIGHT_CANCELLED,obj);	
								errorVOs.add(error);
							}
							
							if (errorVOs != null && errorVOs.size() > 0) { 
								return errorVOs;
							}
							/**
							 * ADDED FOR SAA 410 STARTS
							 */
							if(flightValidationVO != null){
				    			if(flightValidationVO.isTBADueRouteChange()){
				    				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),scannedVO.getFlightDate().toString().substring(0,11)};
				    				ErrorVO errorVO = new ErrorVO(FLIGHT_TO_BE_ACTIONED,obj);
				    				if(errorVOs == null){
				    					errorVOs = new ArrayList<ErrorVO>();
				    				}
				    				errorVOs.add(errorVO);
				    				
				    			}
				    		}
							
							if (errorVOs != null && errorVOs.size() > 0) { 
								return errorVOs;
							}
							/**
							 * ADDED FOR SAA 410 ENDS
							 */
						}
						
						
						
						if (errorVOs != null && errorVOs.size() > 0) { 
							return errorVOs;
						}
						MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =	new MailTrackingDefaultsDelegate();

						//Validating Flight Closure
						boolean isFlightClosed = false;			    	
						ErrorVO flightClosureError = null;
						OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
						operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
						operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
						operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
						operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
						operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
						operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
						if((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedVO.getProcessPoint()))
								|| (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedVO.getProcessPoint()))){
							operationalFlightVO.setPou(logonAttributes.getAirportCode());
							operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
							try {
								if(mailTrackingDefaultsDelegate.validateInboundFlight(operationalFlightVO)!= null) {
									isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForInboundOperations(operationalFlightVO);
								}
								if(isFlightClosed) {
									flightClosureError = new ErrorVO (FLIGHT_INBOUND_CLOSED);
								}	
							} catch (BusinessDelegateException businessDelegateException) {
								log.log(Log.SEVERE,
										"BusinessDelegateException---->",
										businessDelegateException);
							}
						}else if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedVO.getProcessPoint()) || 
								MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedVO.getProcessPoint())){
							operationalFlightVO.setPol(logonAttributes.getAirportCode());
							operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
							try {
								isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
								if(isFlightClosed) {
									flightClosureError = new ErrorVO (FLIGHT_OUTBOUND_CLOSED);
								}	
							} catch (BusinessDelegateException businessDelegateException) {
								log.log(Log.SEVERE,
										"BusinessDelegateException---->",
										businessDelegateException);
							}
						}
						log
								.log(Log.FINE, "isFlightClosed---->",
										isFlightClosed);
						if(flightClosureError != null){
							Object[] obj = {operationalFlightVO.getCarrierCode(),
									operationalFlightVO.getFlightNumber(),
									operationalFlightVO.getFlightDate().toString().substring(0,11)};
							ErrorVO error = new ErrorVO (flightClosureError.getErrorCode(),obj);
							errorVOs.add(error);
						}
						if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedVO.getProcessPoint()) || 
								MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedVO.getProcessPoint())){
							Collection<ErrorVO> flightPouExceptions = new ArrayList<ErrorVO>();
							flightPouExceptions = isPouValid(scannedVO, flightValidationVO, logonAttributes);
							if(flightPouExceptions != null && flightPouExceptions.size() >0 ){
								errorVOs.addAll(flightPouExceptions);
							}
							if (errorVOs != null && errorVOs.size() > 0) { 
								return errorVOs;
							}
						}
						if(flightValidationVO.getAtd() != null){
							scannedVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
						}
						scannedVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						scannedVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					}
					/*
					 * If there are more than one element in the collection
					 * then, for time being throw an error instead of  
					 * displaying the duplicate flight popup
					 */
					else if(flightValidationVOs.size() > 1){
						//TODO : Display the duplicate flight popup
						log.log(Log.INFO,"###### Duplicate Exist####");
						duplicateFlightSession.setFlightValidationVOs((
								ArrayList<FlightValidationVO>)flightValidationVOs);
						duplicateFlightSession.setParentScreenId(SCREEN_ID);
						duplicateFlightSession.setFlightFilterVO(getFlightFilterVO(scannedVO,logonAttributes,airlineValidationVO));
						ErrorVO error = new ErrorVO (FLIGHT_DUPLICATE);
						errorVOs.add(error);
						if (errorVOs != null && errorVOs.size() > 0) { 
							return errorVOs;
						}
					}
				}
			}else{
				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedVO.getProcessPoint())){
					ErrorVO errorVO = new ErrorVO(FLIGHT_MANDATORY_FOR_ARRIVAL);
					errorVOs.add(errorVO);
					return errorVOs;
				}
//				//Validating Destination
//				AirportValidationVO airportValidationVO = null;
//				String destination = scannedVO.getDestination();        	
//				if (destination != null && !"".equals(destination)) { 
//					destination = scannedVO.getDestination().trim().toUpperCase(); 
//					try {        			
//						airportValidationVO = new AreaDelegate().validateAirportCode(
//								logonAttributes.getCompanyCode(),destination);
//					}catch (BusinessDelegateException businessDelegateException) {
//						errorVOs = handleDelegateException(businessDelegateException);
//					}
//				}
//				if ((errorVOs != null && errorVOs.size() > 0) || airportValidationVO == null) {            			
//					Object[] obj = {destination};
//					ErrorVO errorVO = new ErrorVO(INVALID_AIRPORT,obj);
//					errorVOs.add(errorVO);
//					return errorVOs;
//				}
				scannedVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
				scannedVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				scannedVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
			}
		}else{
			ErrorVO error = new ErrorVO (INVALID_CARRIER);	
			Object [] obj ={alphaCode};
			error.setErrorData(obj);
			errorVOs.add(error);
		}
		log.exiting("validateFlightForUploadSession","execute");
		return errorVOs;
	}

	/**
	 * @author A-3227
	 * @param selectedSession
	 * @param exceptionDetails
	 */
	private void updateExceptionAfterSave(ScannedMailDetailsVO selectedSession,
			Collection<ScannedMailDetailsVO> exceptionDetailsAfterSave){
		log.entering("updateExceptionAfterSave","execute");
		Collection<ExistingMailbagVO> existingMailDetails = null;
		ScannedMailDetailsVO scannedExceptionVO = null;
		Collection<MailbagVO> errorMailbagsAfterSave = selectedSession.getErrorMailDetails();
		Collection<MailbagVO> errorMailbagsToRemove = null;
		int exceptionCount = 0;
		MailbagVO errorMailbag = null;
		if(exceptionDetailsAfterSave != null && exceptionDetailsAfterSave.size() > 0){
			scannedExceptionVO = ((ArrayList<ScannedMailDetailsVO>)exceptionDetailsAfterSave).get(0);
			existingMailDetails = scannedExceptionVO.getExistingMailbagVOS();
			Collection<MailbagVO> errorMailDetails = scannedExceptionVO.getMailDetails();
			if(errorMailbagsAfterSave == null) {
				errorMailbagsAfterSave = new ArrayList<MailbagVO>();
			}
			if(existingMailDetails != null && existingMailDetails.size() > 0){ 
				for(ExistingMailbagVO existingMailbagVO :existingMailDetails){
					if("C".equals(existingMailbagVO.getFlightStatus())){	         			  		    		  
						errorMailbag = new MailbagVO();
						errorMailbag.setErrorType(MailConstantsVO.EXCEPT_FATAL);
						errorMailbag.setErrorDescription("Flight is Closed");
						errorMailbagsAfterSave.add(errorMailbag);
					} else if(scannedExceptionVO.getPol() != null && !scannedExceptionVO.getPol().equals(existingMailbagVO.getCurrentAirport())){	         			   
						errorMailbag = new MailbagVO(); 			    		  
						errorMailbag.setErrorType(MailConstantsVO.EXCEPT_FATAL);
						errorMailbag.setErrorDescription("Scanned Ports are different");
						errorMailbagsAfterSave.add(errorMailbag);
					}else{
						log.log(Log.INFO,"can reassignmail bags");	         			 
						errorMailbag = new MailbagVO();
						errorMailbag.setCompanyCode(scannedExceptionVO.getCompanyCode());
						errorMailbag.setMailbagId(existingMailbagVO.getMailId()); 

						populateMailPKFields(errorMailbag);

						errorMailbag.setCarrierCode(existingMailbagVO.getCarrierCode());
						errorMailbag.setFlightDate(existingMailbagVO.getFlightDate());
						errorMailbag.setFlightNumber(existingMailbagVO.getFlightNumber());
						errorMailbag.setFlightSequenceNumber(existingMailbagVO.getFlightSequenceNumber());
						errorMailbag.setLegSerialNumber(existingMailbagVO.getLegSerialNumber());
						errorMailbag.setSegmentSerialNumber(existingMailbagVO.getSegmentSerialNumber());
						errorMailbag.setPol(existingMailbagVO.getPol());
						errorMailbag.setPou(existingMailbagVO.getPou());									
						errorMailbag.setCarrierId(existingMailbagVO.getCarrierId());
						errorMailbag.setScannedPort(existingMailbagVO.getCurrentAirport());
						errorMailbag.setContainerNumber(existingMailbagVO.getContainerNumber());
						errorMailbag.setContainerType(existingMailbagVO.getContainerType());
						errorMailbag.setFinalDestination(existingMailbagVO.getFinalDestination());
						errorMailbag.setFromSegmentSerialNumber(existingMailbagVO.getSegmentSerialNumber());
						errorMailbag.setUldNumber(existingMailbagVO.getContainerNumber());
						errorMailbag.setUbrNumber(existingMailbagVO.getUbrNumber());
						errorMailbag.setBookingLastUpdateTime(existingMailbagVO.getBookingLastUpdateTime());
						errorMailbag.setBookingFlightDetailLastUpdTime(existingMailbagVO.getBookingFlightDetailLastUpdTime());

						errorMailbag.setErrorType(MailConstantsVO.EXCEPT_WARN);
						errorMailbag.setErrorDescription("Reassign mailbags");
						errorMailbag.setReassignFlag("Y");
						
						errorMailbag.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
						errorMailbagsAfterSave.add(errorMailbag);

					}
					errorMailbagsToRemove = new ArrayList<MailbagVO>();
					if(selectedSession.getMailDetails()!= null && selectedSession.getMailDetails().size() > 0){
						for(MailbagVO scannedBags : selectedSession.getMailDetails()){
							if(errorMailbag.getMailbagId().equals(scannedBags.getMailbagId())){
								errorMailbagsToRemove.add(scannedBags);
							}
						}
						selectedSession.getMailDetails().removeAll(errorMailbagsToRemove);
					}
				}
			}
			
			if(errorMailDetails != null && errorMailDetails.size() > 0){
				if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(selectedSession.getProcessPoint()) || 
						MailConstantsVO.MAIL_STATUS_ARRIVED.equals(selectedSession.getProcessPoint()) || 
						MailConstantsVO.MAIL_STATUS_RETURNED.equals(selectedSession.getProcessPoint())){
					errorMailbagsAfterSave.addAll(errorMailDetails);
					exceptionCount = errorMailbagsAfterSave.size();	
				}				
			}
		}
		selectedSession.setErrorMailDetails(errorMailbagsAfterSave);
		selectedSession.setMailDetails(new ArrayList<MailbagVO>());
		updateSessionStatus(selectedSession,false);
		log.exiting("updateExceptionAfterSave","execute");
	}
	/**
	 * @author A-3227
	 * @param selectedSession
	 * @param exceptionDetails
	 */
	private void updateExceptionAfterMailValidation(ScannedMailDetailsVO selectedSession,
			Collection<MailbagVO> exceptionMailDetails){
		log.entering("updateExceptionAfterMailValidation","execute");
		if(exceptionMailDetails != null && exceptionMailDetails.size()>0){
			for(MailbagVO errorbagVO : exceptionMailDetails){
				if(errorbagVO.getErrorCode() != null && errorbagVO.getErrorCode().trim().length() > 0){
					ErrorVO errorVO = new ErrorVO(errorbagVO.getErrorCode());
					errorbagVO.setErrorDescription(updateErrorDescription(errorVO));
				}
			}
			Collection<MailbagVO> errorMailbagsVOs = selectedSession.getErrorMailDetails();
			if(errorMailbagsVOs == null) {
				errorMailbagsVOs = new ArrayList<MailbagVO>();
			}
			errorMailbagsVOs.addAll(exceptionMailDetails);
			selectedSession.setErrorMailDetails(errorMailbagsVOs);
			updateSessionStatus(selectedSession,false);
		}
		log.exiting("updateExceptionAfterMailValidation","execute");
	}
	
	/**
	 * 
	 * @param selectedSession
	 * @param errorVOCount This is passed only when error exist in Flight/Container
	 */
	private void updateSessionStatus(
			ScannedMailDetailsVO selectedSession,boolean isErrorVOException){
		log.entering("updateSessionStatus","execute");
		int exceptionCount = 0;
		int savedBags = 0; 
		if(selectedSession.getErrorMailDetails() != null && selectedSession.getErrorMailDetails().size() > 0) {
			exceptionCount = selectedSession.getErrorMailDetails().size();
		}
		if(!isErrorVOException){
			savedBags = selectedSession.getScannedBags() - exceptionCount - selectedSession.getDeletedExceptionBagCout();
		}else{
			savedBags = 0;
		}

		if(selectedSession.getScannedBags() == savedBags){
			selectedSession.setStatus(SAVED);
		}else if(exceptionCount > 0 && savedBags > 0){
			selectedSession.setStatus(PARTIALLY_SAVED);
		}else{
			selectedSession.setStatus(UNSAVED);
		}

		selectedSession.setExceptionBagCout(exceptionCount);
		selectedSession.setSavedBags(savedBags);
		log.exiting("updateSessionStatus","execute");
	}
	/**
	 * @author A-3227
	 * @param selectedSession
	 * @param exceptionDetails
	 */
	private boolean updateExceptionDetails(ScannedMailDetailsVO selectedSession,
			Collection<ErrorVO> fatalErrors,
			Collection<ErrorVO> warnings){
		log.entering("updateExceptionDetails","execute");
		boolean isUpdated = false;
		if((fatalErrors != null && fatalErrors.size() > 0) || (warnings != null && warnings.size() > 0)){
			isUpdated = true;
			MailbagVO errorMailbag = null;   
			Collection<MailbagVO> errorMailbagsAfterSave = null;
			if(errorMailbagsAfterSave == null) {
				errorMailbagsAfterSave = new ArrayList<MailbagVO>();
			}
			if(fatalErrors != null && fatalErrors.size() > 0){
				for(ErrorVO fatalError :  fatalErrors){	         			  		    		  
					errorMailbag = new MailbagVO();
					errorMailbag.setErrorCode(fatalError.getErrorCode());
					errorMailbag.setErrorType(MailConstantsVO.EXCEPT_FATAL);
					if(fatalError != null){
						Object [] containerNum = fatalError.getErrorData();
						if(containerNum != null && containerNum.length ==1){
							errorMailbag.setContainerNumber((String)containerNum[0]);
						}
						
					}
					errorMailbag.setErrorDescription(updateErrorDescription(fatalError));
					errorMailbagsAfterSave.add(errorMailbag);
				}
			}
			if(warnings != null && warnings.size() > 0){
				for(ErrorVO warn :  warnings){	         			  		    		  
					errorMailbag = new MailbagVO();
					errorMailbag.setErrorCode(warn.getErrorCode());
					errorMailbag.setErrorType(MailConstantsVO.EXCEPT_WARN);
					errorMailbag.setErrorDescription(updateErrorDescription(warn));
					errorMailbagsAfterSave.add(errorMailbag);
				}
			}
			selectedSession.setErrorMailDetails(errorMailbagsAfterSave);
			updateSessionStatus(selectedSession,true);
		}
		log.exiting("updateExceptionDetails","execute");
		return isUpdated;
	}

	/**
	 * makeOffloadVOs
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private Collection<OffloadVO> makeOffloadVOs(
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes){
		log.entering("MailController", "makeOffloadVOs");
		Collection<OffloadVO> offloadVOsForSave = new ArrayList<OffloadVO>();
		Collection<OffloadVO> offloadMailVOsForSave = null;
		Collection<OffloadVO> offloadcontainerVOsForSave = null;
		Collection<MailbagVO> mailbagVOsToSave = scannedMailDetailsVO.getMailDetails();
		Collection<ContainerVO> containerVOsToSave = scannedMailDetailsVO.getScannedContainerDetails();
		Map<String,OffloadVO> mailOffloadMap = new HashMap<String, OffloadVO>();
		Map<String,OffloadVO> containerOffloadMap = new HashMap<String, OffloadVO>();
		String mailOffloadMapKey = null;
		String containerOffloadMapKey = null;
		OffloadVO offloadVO = null;
		if(mailbagVOsToSave != null && mailbagVOsToSave.size() > 0){
			for(MailbagVO mailbagVOToSave : mailbagVOsToSave){
				mailOffloadMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
				.append(mailbagVOToSave.getCarrierCode())
				.append(mailbagVOToSave.getFlightNumber())
				.append(mailbagVOToSave.getFlightDate())
				.append(mailbagVOToSave.getCarrierId())
				.append(mailbagVOToSave.getFlightSequenceNumber())
				.append(mailbagVOToSave.getLegSerialNumber())
				.toString();
				if(!mailOffloadMap.containsKey(mailOffloadMapKey)){
					offloadVO = new OffloadVO();
					offloadVO.setDepartureOverride(true);
					offloadVO.setCarrierCode(mailbagVOToSave.getCarrierCode());
					offloadVO.setCarrierId(mailbagVOToSave.getCarrierId());
					offloadVO.setCompanyCode(mailbagVOToSave.getCompanyCode());
					offloadVO.setFlightDate(mailbagVOToSave.getFlightDate());
					offloadVO.setFlightNumber(mailbagVOToSave.getFlightNumber());
					offloadVO.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					offloadVO.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					offloadVO.setPol(mailbagVOToSave.getPol());
					offloadVO.setOffloadMailbags(new Page<MailbagVO>(new ArrayList<MailbagVO>(),0,0,0,0,0,false));
					offloadVO.getOffloadMailbags().add(mailbagVOToSave);
					offloadVO.setUserCode(logonAttributes.getUserId());
					offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_MAILBAG);
					mailOffloadMap.put(mailOffloadMapKey, offloadVO);
				}else{
					offloadVO =  mailOffloadMap.get(mailOffloadMapKey);
					offloadVO.getOffloadMailbags().add(mailbagVOToSave);
				}
			}
		}
		if(containerVOsToSave != null && containerVOsToSave.size() >0){
			for(ContainerVO containerVOTosave : containerVOsToSave){
				
				containerOffloadMapKey = new StringBuilder(containerVOTosave.getCompanyCode())
				.append(containerVOTosave.getCarrierId())
				.append(containerVOTosave.getCarrierCode())
				.append(containerVOTosave.getFlightNumber())
				.append(containerVOTosave.getFlightDate())
				.append(containerVOTosave.getFlightSequenceNumber())
				.append(containerVOTosave.getLegSerialNumber())
				.toString();
				if(!containerOffloadMap.containsKey(containerOffloadMapKey)){
					offloadVO = new OffloadVO();
					offloadVO.setDepartureOverride(true);
					offloadVO.setCarrierCode(containerVOTosave.getCarrierCode());
					offloadVO.setCarrierId(containerVOTosave.getCarrierId());
					offloadVO.setCompanyCode(containerVOTosave.getCompanyCode());
					offloadVO.setFlightDate(containerVOTosave.getFlightDate());
					offloadVO.setFlightNumber(containerVOTosave.getFlightNumber());
					offloadVO.setFlightSequenceNumber(containerVOTosave.getFlightSequenceNumber());
					offloadVO.setLegSerialNumber(containerVOTosave.getLegSerialNumber());
					offloadVO.setPol(containerVOTosave.getAssignedPort());
					offloadVO.setOffloadContainers(new ArrayList<ContainerVO>());
					
					offloadVO.getOffloadContainers().add(containerVOTosave);
					offloadVO.setUserCode(logonAttributes.getUserId());
					offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_CONTAINER);
					containerOffloadMap.put(containerOffloadMapKey, offloadVO);
				}else{
					offloadVO = containerOffloadMap.get(containerOffloadMapKey);
					offloadVO.getOffloadContainers().add(containerVOTosave);
				}
			}
		}
		
		offloadMailVOsForSave = mailOffloadMap.values();
		offloadcontainerVOsForSave=containerOffloadMap.values();
		offloadVOsForSave.addAll(offloadMailVOsForSave);
		offloadVOsForSave.addAll(offloadcontainerVOsForSave);
		
		
		log.exiting("MailController", "makeOffloadVOs");	
		return offloadVOsForSave;
	}
	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 * @return
	 */
	private MailAcceptanceVO makeMailAcceptanceVO(
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes){
		log.entering("makeMailAcceptanceVO","execute");	
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();

		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		mailAcceptanceVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		mailAcceptanceVO.setFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
		mailAcceptanceVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
		mailAcceptanceVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailAcceptanceVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailAcceptanceVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		mailAcceptanceVO.setPol(scannedMailDetailsVO.getPol());
		mailAcceptanceVO.setScanned(true);
		mailAcceptanceVO.setPreassignNeeded(false);

		containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if(containerDetailsVO == null){
			containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		}
		containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerDetailsVO.setAcceptedFlag(scannedMailDetailsVO.getAcceptedFlag());
		containerDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
		containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
		containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
		if(scannedMailDetailsVO.getRemarks() != null && scannedMailDetailsVO.getRemarks().trim().length()>0){
			containerDetailsVO.setRemarks(scannedMailDetailsVO.getRemarks());
		}
		containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());						
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());					
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);

		Collection<MailbagVO> mailVOs = scannedMailDetailsVO.getMailDetails();
		if(mailVOs != null && mailVOs.size() > 0 && (!scannedMailDetailsVO.isAcknowledged())){
			for(MailbagVO mailbagVO : mailVOs){
				mailbagVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
				mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
				mailbagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
				mailbagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
				mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
				mailbagVO.setPou(scannedMailDetailsVO.getPou());
				mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
				mailbagVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
				mailbagVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
				mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setScannedPort(scannedMailDetailsVO.getPol());
				mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_NO);
				if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getDamageFlag())){
					mailbagVO.setDamageFlag(MailConstantsVO.FLAG_YES);
				}else{
					mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
				}
				if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getReassignFlag())){
					mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
					mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				}
			}
		}else if(scannedMailDetailsVO.isAcknowledged()){
			mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
		}
		containerDetailsVO.setMailDetails(mailVOs);

		HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
		if(mailVOs != null && mailVOs.size() > 0){
			for(MailbagVO mailbagVO:mailVOs){
				int numBags = 0;
				double bagWgt = 0;
				String outerpk = mailbagVO.getOoe()
				+mailbagVO.getDoe()
				+mailbagVO.getMailSubclass()
				+mailbagVO.getMailCategoryCode()
				+mailbagVO.getDespatchSerialNumber()
				+mailbagVO.getYear();
				if(dsnMap.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
					dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
					dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					dsnVO.setYear(mailbagVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					for(MailbagVO innerVO:mailVOs){
						String innerpk = innerVO.getOoe()
						+innerVO.getDoe()
						+innerVO.getMailSubclass()
						+innerVO.getMailCategoryCode()
						+innerVO.getDespatchSerialNumber()
						+innerVO.getYear();
						if(outerpk.equals(innerpk)){
							numBags = numBags + 1;
							//bagWgt = bagWgt + innerVO.getWeight();
							bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
						}
					}
					if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getReassignFlag())){
						//number of bags and weight for reassignment will be populated in controller
						numBags = numBags-1;
						//bagWgt = bagWgt-mailbagVO.getWeight();
						bagWgt = bagWgt-mailbagVO.getWeight().getRoundedSystemValue();//added by A-7371
					}
					dsnVO.setBags(numBags);
					//dsnVO.setWeight(bagWgt);
					dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371

					dsnMap.put(outerpk,dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for(String key:dsnMap.keySet()){
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getBags();
			//totWgt = totWgt + dsnVO.getWeight();
			totWgt = totWgt + dsnVO.getWeight().getRoundedSystemValue();//added by A-7371
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setDsnVOs(newDSNVOs);
		containerDetailsVO.setTotalBags(totBags);
		//containerDetailsVO.setTotalWeight(totWgt);
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
		containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		containerDetailsVOs.add(containerDetailsVO);

		mailAcceptanceVO.setContainerDetails(containerDetailsVOs);

		log.exiting("makeMailAcceptanceVO","execute");
		return mailAcceptanceVO;

	}

	/**
	 * makeMailArrivalVOsForDelivery
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private Collection<MailArrivalVO> makeMailArrivalVOsForDelivery(
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes){
		log.entering("MailController", "makeMailArrivalVOsForDelivery");
		Collection<MailbagVO> mailbagVOsToSave = scannedMailDetailsVO.getMailDetails();
		Collection<ScannedMailDetailsVO> scannedMailsForDelivery = new ArrayList<ScannedMailDetailsVO>();
		Collection<MailArrivalVO> mailArrivalVOsForDelivery = null;
		Map<String,ScannedMailDetailsVO> scannedMailsMapForDelivery = new HashMap<String, ScannedMailDetailsVO>();
		String deliverMapKey = null;
		ScannedMailDetailsVO scannedMailDetailsVOForDelivery = null;
		if(mailbagVOsToSave != null && mailbagVOsToSave.size() >0){
			for(MailbagVO mailbagVOToSave : mailbagVOsToSave){
				deliverMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
				.append(mailbagVOToSave.getCarrierCode())
				.append(mailbagVOToSave.getFlightNumber())
				.append(mailbagVOToSave.getFlightDate())
				.append(mailbagVOToSave.getCarrierId())
				.append(mailbagVOToSave.getFlightSequenceNumber())
				.append(mailbagVOToSave.getLegSerialNumber())
				.append(mailbagVOToSave.getPou())
				.append(mailbagVOToSave.getContainerNumber())
				.toString();
				if(!scannedMailsMapForDelivery.containsKey(deliverMapKey)){
					scannedMailDetailsVOForDelivery = new ScannedMailDetailsVO();
					scannedMailDetailsVOForDelivery.setCompanyCode(mailbagVOToSave.getCompanyCode());
					scannedMailDetailsVOForDelivery.setFlightNumber(mailbagVOToSave.getFlightNumber());
					scannedMailDetailsVOForDelivery.setFlightDate(mailbagVOToSave.getFlightDate());
					scannedMailDetailsVOForDelivery.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					scannedMailDetailsVOForDelivery.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					scannedMailDetailsVOForDelivery.setCarrierId(mailbagVOToSave.getCarrierId());
					scannedMailDetailsVOForDelivery.setCarrierCode(mailbagVOToSave.getCarrierCode());
					scannedMailDetailsVOForDelivery.setContainerNumber(mailbagVOToSave.getContainerNumber());
					scannedMailDetailsVOForDelivery.setContainerType(mailbagVOToSave.getContainerType());					
					scannedMailDetailsVOForDelivery.setPol(mailbagVOToSave.getPol());
					scannedMailDetailsVOForDelivery.setPou(mailbagVOToSave.getPou());
					scannedMailDetailsVOForDelivery.setSegmentSerialNumber(mailbagVOToSave.getSegmentSerialNumber());
					scannedMailDetailsVOForDelivery.setMailDetails(new ArrayList<MailbagVO>());
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);

					scannedMailsMapForDelivery.put(deliverMapKey, scannedMailDetailsVOForDelivery);
				}else{
					scannedMailDetailsVOForDelivery = scannedMailsMapForDelivery.get(deliverMapKey);
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);
				}
			}
		}
		scannedMailsForDelivery = scannedMailsMapForDelivery.values();		
		if(scannedMailsForDelivery != null && scannedMailsForDelivery.size() > 0){
			mailArrivalVOsForDelivery = new ArrayList<MailArrivalVO>();
			MailArrivalVO mailArrivalVOForDelivery = null;
			/**
			 * For constructing mailarrivalVo from scanned details
			 */
			for(ScannedMailDetailsVO mailDetailsVO : scannedMailsForDelivery){
				mailArrivalVOForDelivery = makeMailArrivalVO(mailDetailsVO, logonAttributes, true);
				mailArrivalVOsForDelivery.add(mailArrivalVOForDelivery);
			}

		}
		log.exiting("MailController", "makeMailArrivalVOsForDelivery");	
		return mailArrivalVOsForDelivery;
	}

	
	/**
	 * makeMailArrivalVOs
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private Collection<MailArrivalVO> makeMailArrivalVOs(
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes){
		log.entering("MailController", "makeMailArrivalVOs");
		Collection<MailArrivalVO> mailArrivalVOsForSave = new ArrayList<MailArrivalVO>();
		Collection<MailbagVO> mailbagVOsToArrive = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsToDeliver = new ArrayList<MailbagVO>();
				
		Collection<MailbagVO> mailbagVOs = scannedMailDetailsVO.getMailDetails();
		if(mailbagVOs != null && mailbagVOs.size() > 0){
			for(MailbagVO mailbagVO : mailbagVOs){
				if(mailbagVO.isDeliveryNeeded()){
					mailbagVOsToDeliver.add(mailbagVO);
				}else{
					mailbagVOsToArrive.add(mailbagVO);
				}
			}
			if(mailbagVOsToDeliver.size() > 0){
				ScannedMailDetailsVO scannedMailToDeliver = constructScannedMailDetailsVO(scannedMailDetailsVO);				
				scannedMailToDeliver.setMailDetails(mailbagVOsToDeliver);
				MailArrivalVO mailArrivalVOToDeliver = makeMailArrivalVO(scannedMailToDeliver,logonAttributes,true);
				mailArrivalVOToDeliver.setDeliveryNeeded(true);
				mailArrivalVOsForSave.add(mailArrivalVOToDeliver);
			}
			if(mailbagVOsToArrive.size() > 0){		
				ScannedMailDetailsVO scannedMailToArrive = constructScannedMailDetailsVO(scannedMailDetailsVO);					
				scannedMailToArrive.setMailDetails(mailbagVOsToArrive);
				MailArrivalVO mailArrivalVO = makeMailArrivalVO(scannedMailToArrive,logonAttributes,false);
				mailArrivalVO.setDeliveryNeeded(false);
				mailArrivalVOsForSave.add(mailArrivalVO);
			}
		}
		log.exiting("MailController", "makeMailArrivalVOs");
		return mailArrivalVOsForSave;
	}
	
	/**
	 * constructScannedMailDetailsVO
	 * @return
	 */
	private ScannedMailDetailsVO constructScannedMailDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO){
		
		ScannedMailDetailsVO newScannedMailDetailsVO = new ScannedMailDetailsVO();
		newScannedMailDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		newScannedMailDetailsVO.setProcessPoint(scannedMailDetailsVO.getProcessPoint());
		newScannedMailDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		newScannedMailDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		newScannedMailDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		newScannedMailDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		newScannedMailDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		newScannedMailDetailsVO.setFlightStatus(scannedMailDetailsVO.getFlightStatus());
		newScannedMailDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		newScannedMailDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		newScannedMailDetailsVO.setPol(scannedMailDetailsVO.getPol());
		newScannedMailDetailsVO.setPou(scannedMailDetailsVO.getPou());
		newScannedMailDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		newScannedMailDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());

		if(scannedMailDetailsVO.getValidatedContainer() != null){
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getValidatedContainer().getCompanyCode());
			containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getValidatedContainer().getContainerNumber());
			containerDetailsVO.setContainerType(scannedMailDetailsVO.getValidatedContainer().getContainerType());
			containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getValidatedContainer().getCarrierCode());
			containerDetailsVO.setCarrierId(scannedMailDetailsVO.getValidatedContainer().getCarrierId());
			containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getValidatedContainer().getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getValidatedContainer().getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getValidatedContainer().getLegSerialNumber());
			containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getValidatedContainer().getSegmentSerialNumber());
			containerDetailsVO.setOperationFlag(scannedMailDetailsVO.getValidatedContainer().getOperationFlag());
			containerDetailsVO.setContainerOperationFlag(scannedMailDetailsVO.getValidatedContainer().getContainerOperationFlag());
			containerDetailsVO.setPol(scannedMailDetailsVO.getValidatedContainer().getPol());
			containerDetailsVO.setPou(scannedMailDetailsVO.getValidatedContainer().getPou());
			containerDetailsVO.setDestination(scannedMailDetailsVO.getValidatedContainer().getDestination());
			newScannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
		}
		
		return newScannedMailDetailsVO;
	}
	/**
	 * This method constructs the MailArrivalVO for a particular session
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private MailArrivalVO makeMailArrivalVO (
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes,boolean isDeliveryNeeded){
		log.entering("makeMailArrivalVO","execute");
		
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
		mailArrivalVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailArrivalVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailArrivalVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailArrivalVO.setFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
		mailArrivalVO.setArrivalDate(scannedMailDetailsVO.getFlightDate());
		mailArrivalVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setScanDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false));
		mailArrivalVO.setScanned(true);

		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if(containerDetailsVO == null) {
			containerDetailsVO = new ContainerDetailsVO();
		}
		if(containerDetailsVO.getPol() == null){
			containerDetailsVO.setDestination(logonAttributes.getAirportCode());
			containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
		}
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		containerDetailsVO.setPou(logonAttributes.getAirportCode());
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		if(isDeliveryNeeded){
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
			if(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())){
				containerDetailsVO.setContainerNumber(constructBulkULDNumber(logonAttributes.getAirportCode()));
			}
		}
			
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		if(mailbagArriveVOs != null && mailbagArriveVOs.size() > 0){
			for(MailbagVO mailbagToArrive : mailbagArriveVOs){
				mailbagToArrive.setCompanyCode(logonAttributes.getCompanyCode());
				mailbagToArrive.setCarrierCode(containerDetailsVO.getCarrierCode());
				mailbagToArrive.setCarrierId(containerDetailsVO.getCarrierId());
				mailbagToArrive.setFlightNumber(containerDetailsVO.getFlightNumber());
				mailbagToArrive.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				mailbagToArrive.setFlightDate(containerDetailsVO.getFlightDate());
				mailbagToArrive.setContainerNumber(containerDetailsVO.getContainerNumber());
				mailbagToArrive.setContainerType(containerDetailsVO.getContainerType());
				if(MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())){
					mailbagToArrive.setUldNumber(containerDetailsVO.getContainerNumber());
				}else{
					mailbagToArrive.setUldNumber(constructBulkULDNumber(containerDetailsVO.getPou()));					
				}
				mailbagToArrive.setPou(containerDetailsVO.getPou());
				mailbagToArrive.setScannedPort(logonAttributes.getAirportCode());
				mailbagToArrive.setScannedUser(logonAttributes.getUserId().toUpperCase());
				mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_YES);
				mailbagToArrive.setDeliveredFlag(MailConstantsVO.FLAG_NO);
				mailbagToArrive.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
				if(mailbagToArrive.getSegmentSerialNumber() > 0){
					mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					if(isDeliveryNeeded){
						mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
					}else{
						mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
					}
				}else{
					mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					mailbagToArrive.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
				}
			}
		}

		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		if (mailbagArriveVOs != null && mailbagArriveVOs.size() > 0) {
			for (MailbagVO mailbgVO : mailbagArriveVOs) {
				int numBags = 0;
				double bagWgt = 0;
				String outerpk = mailbgVO.getOoe()+mailbgVO.getDoe()
				+(mailbgVO.getMailSubclass())
				+ mailbgVO.getMailCategoryCode()
				+mailbgVO.getDespatchSerialNumber()+mailbgVO.getYear();
				if(dsnMap.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbgVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbgVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbgVO.getDoe());
					dsnVO.setMailClass(mailbgVO.getMailSubclass().substring(0,1));
					dsnVO.setMailSubclass(mailbgVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbgVO.getMailCategoryCode());
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					dsnVO.setYear(mailbgVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					for(MailbagVO innerVO : mailbagArriveVOs){
						String innerpk = innerVO.getOoe()+innerVO.getDoe()
						+(innerVO.getMailSubclass())
						+ innerVO.getMailCategoryCode()
						+innerVO.getDespatchSerialNumber()+innerVO.getYear();
						if(outerpk.equals(innerpk)){
							numBags = numBags + 1;
							//bagWgt = bagWgt + innerVO.getWeight();
							bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
						}
					}
					dsnVO.setReceivedBags(numBags);
					//dsnVO.setReceivedWeight(bagWgt);
					dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
					dsnMap.put(outerpk,dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for(String key:dsnMap.keySet()){
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getReceivedBags();
			//totWgt = totWgt + dsnVO.getReceivedWeight();
			totWgt = totWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();//added by A-7371
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		containerDetailsVO.setDsnVOs(newDSNVOs);
		containerDetailsVO.setReceivedBags(totBags);
		//containerDetailsVO.setReceivedWeight(totWgt);
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
		containerDetailsVOs.add(containerDetailsVO);
        if(isDeliveryNeeded){
        	mailArrivalVO.setPartialDelivery(false);
        	
        }
		mailArrivalVO.setContainerDetails(containerDetailsVOs);

		log.exiting("makeMailArrivalVO","execute");
		return mailArrivalVO;
	}

	/**
	 * @author A-3227
	 * @param scannedMailDetailsVO
	 */
	private void saveDamageSession(ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveDamageSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<ScannedMailDetailsVO> exceptionDetailsAfterSave = null;
			Collection<ErrorVO> fatalErrorsInAcceptance = null;
			 // for validating mailbag details
			if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){
				if(scannedMailDetailsVO.getMailDetails() !=null && scannedMailDetailsVO.getMailDetails().size() >0){
					try{
						new MailTrackingDefaultsDelegate().saveDamageDetailsForMailbag(scannedMailDetailsVO.getMailDetails());
						updateExceptionAfterSave(scannedMailDetailsVO, exceptionDetailsAfterSave);
					}catch(BusinessDelegateException businessDelegateException){
						fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
						updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);			
					}
				}	
			}	
		}
		log.exiting("saveDamageSession","execute");	
	}
	private void saveDeliverSession(ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("saveDeliverSession","execute");
		if(scannedMailDetailsVO != null){
			Collection<ScannedMailDetailsVO> exceptionDetailsAfterSave = null;
			Collection<ErrorVO> fatalErrorsInAcceptance = null;
			Collection<MailArrivalVO> mailArrivalVOsForDeliver = null;
			// for validating mailbag details
			if(isCurrentUploadSessionValid(scannedMailDetailsVO,logonAttributes,duplicateFlightSession)){
				if(scannedMailDetailsVO.getMailDetails() !=null && scannedMailDetailsVO.getMailDetails().size() >0){
					mailArrivalVOsForDeliver =  makeMailArrivalVOsForDelivery(scannedMailDetailsVO, logonAttributes);
					if(mailArrivalVOsForDeliver != null && mailArrivalVOsForDeliver.size() >0){
						try{
							new MailTrackingDefaultsDelegate().saveScannedDeliverMails(mailArrivalVOsForDeliver);
							updateExceptionAfterSave(scannedMailDetailsVO, exceptionDetailsAfterSave);
						}catch(BusinessDelegateException businessDelegateException){
							fatalErrorsInAcceptance = handleDelegateException(businessDelegateException);
							updateExceptionDetails(scannedMailDetailsVO,fatalErrorsInAcceptance,null);			
						}
					}	
				}	
			}	
		}
		log.exiting("saveDeliverSession","execute");	
	}
	/**
	 * 
	 * @param errorCode
	 * @return
	 */
	private String updateErrorDescription(ErrorVO errorVO){
		String errorDescription = "";	
		String errorCode = errorVO.getErrorCode();
		if(RUN_TIME_EXCEPTION.equals(errorCode) || OPERATION_FAILED.equals(errorCode)){
			return "A system service currently unavailable to proceed with the current transaction. Please contact system administrator.";
		}else if(INVALID_CARRIER.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			 StringBuilder errorDesc = new  StringBuilder("Carrier code ");
			 if(obj !=null && obj.length>0){
				 errorDesc.append(obj[0]);
			 }
			 errorDesc.append(" is invalid.");
			return errorDesc.toString();
		}else if (INVALID_AIRPORT.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			StringBuilder errorDesc = new StringBuilder("Airport code ");
			if(obj != null && obj.length>0){
				errorDesc.append(obj[0]);
				errorDesc.append(" is not valid.");
			}
			return errorDesc.toString();
		}else if(FLIGHT_NOT_PRESENT.equals(errorCode)){		
			errorDescription = "Flight not available at this port.";
			 Object [] obj = errorVO.getErrorData();
			  if(obj!=null && obj.length == 3){
				  StringBuilder ErrorDes = new StringBuilder("Flight ")
				  .append(obj[0]).append(" ")
				  .append(obj[1]).append(":").append(obj[2])
				  .append(" is not available at this port");
				  errorDescription=ErrorDes.toString();
			  }
			return errorDescription;
		}else if(FLIGHT_OUTBOUND_CLOSED.equals(errorCode)){
			return "Flight already closed for outbound mail operations.";
		}else if(FLIGHT_INBOUND_CLOSED.equals(errorCode)){
			return "Flight already closed for inbound mail operations.";
		}else if(FLIGHT_CANCELLED.equals(errorCode)){
			return "Flight schedule cancelled.";
		}else if(FLIGHT_DUPLICATE.equals(errorCode)){
			return "Duplicate flight exist in the system.";
		}else if(FLIGHT_MANDATORY_FOR_ARRIVAL.equals(errorCode)){
			return "Flight detail are mandatory during arrival operation.";
		}else if(FLIGHT_POU_INVALID.equals(errorCode)){
			return "The POU(Point Of Unlading) is not in the route of the specified flight.";
		}else if(MAILBAG_RETURNED.equals(errorCode)){
			return "The mailbag is already returned.";
		}else if(MAILBAG_DOESNOT_EXISTS.equals(errorCode)){
			return "The mailbag doesnot exists.";
		}else if(MAILBAG_ALREADY_DELIVERED.equals(errorCode)){
			return "The mailbag is already delivered.";
		}else if(MailConstantsVO.UPLOAD_EXCEPT_ALREADY_OFFLOADED.equals(errorCode)){
			return "The mailbag is already offloaded.";
		}else if(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_ALREADY_TRANSFFERED.equals(errorCode)){
			return "The mailbag is already transffered.";
		}else if(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_NOT_IN_AIRPORT.equals(errorCode)){
			return "The mailbag doesnot exists in this airport.";
		}else if(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_CANNOT_BE_DELIVERED.equals(errorCode)){
			return "The mailbag cannot be delivered.";
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_ACP_TO_FLT.equals(errorCode)){
			errorDescription = "The mailbag(s) cannot be reassigned.";
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length ==4){
				StringBuilder errorDescriptionBuilder = new StringBuilder(" The container ")
				.append((String)obj[0])
				.append(" is not assigned to ")
				.append((String)obj[1])
				.append(" ")
				.append((String)obj[2]).append(":")
				.append((String)obj[3]);
				errorDescription = errorDescriptionBuilder.toString();
			}else if(obj != null && obj.length ==2){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])				
				.append(" is not assigned to ")
				.append((String)obj[1]);
				errorDescription = errorDescriptionBuilder.toString();
			}else if(obj != null && obj.length ==1){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])				
				.append(" is not assigned to Carrier/Flight ");				
				errorDescription = errorDescriptionBuilder.toString();
			}			
			return errorDescription;
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_FLIGHT.equals(errorCode)){
			errorDescription = "The container is not assigned to flight.";
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length ==4){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])
				.append(" is not assigned to ")
				.append((String)obj[1])
				.append(" ")
				.append((String)obj[2]).append(":")
				.append((String)obj[3]);
				errorDescription = errorDescriptionBuilder.toString();
			}else if(obj != null && obj.length ==2){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])				
				.append(" is not assigned to ")
				.append((String)obj[1]);
				errorDescription = errorDescriptionBuilder.toString();
			}
			return errorDescription;
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_ACCEPTED_TO_SYSTEM.equals(errorCode)){
			errorDescription = "";
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length ==5){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])
				.append(" is not assigned to ")
				.append((String)obj[1])
				.append(" ")
				.append((String)obj[2]).append(":")
				.append((String)obj[3])
				.append(" at ").append(obj[4]);
				errorDescription = errorDescriptionBuilder.toString();
			}else if(obj != null && obj.length ==2){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])
				.append(" is not assigned to ")
				.append((String)obj[1])	;
				errorDescription = errorDescriptionBuilder.toString();
			}
			return errorDescription;
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_OFFLOAD_FLT_NOT_CLOSED.equals(errorCode)){
			errorDescription = "Flight not closed for Offloading .";
			Object [] flightArray = errorVO.getErrorData();
			if(flightArray != null && flightArray.length ==4){
				errorDescription = constructFlightNotClosedMessageForUpload(
						(String)flightArray[1], (String)flightArray[2], (LocalDate)flightArray[3]);
			}
			return errorDescription;
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length > 0){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])
				.append(" is currently being used at ")
				.append((String)obj[1])
				.append(" .");
				errorDescription = errorDescriptionBuilder.toString();
			}
			return errorDescription;
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_EXIST_IN_SAME_FLIGHT.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length > 0){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ")
				.append((String)obj[0])
				.append(" is already assign to the same carrier/flight.");
				errorDescription = errorDescriptionBuilder.toString();
			}
			return errorDescription;
		}else if(MailConstantsVO.BULK_IN_FLIGHT_NOT_CLOSED.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj!=null && obj.length ==4){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ");
				errorDescriptionBuilder.append(obj[0].toString())
				.append(" is assigned to flight ")
				.append(obj[1].toString())
				.append("-")
				.append(obj[2].toString())
				.append(" : ").append(((LocalDate)obj[3]).toDisplayDateOnlyFormat())
				.append(" which is not closed");
				return errorDescriptionBuilder.toString();
			}
		}else if(MailConstantsVO.CON_ASSIGNEDTO_DIFFFLT.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj!=null && obj.length ==1){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container is already assigned to flight ");
				errorDescriptionBuilder.append(String.valueOf(obj [0]));
				return errorDescriptionBuilder.toString();
			}
		}else if(MailConstantsVO.BULK_ALREADY_ASSIGNED_TOCARRIER.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj!=null && obj.length ==1){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container is already assigned to carrier ");
				errorDescriptionBuilder.append(String.valueOf(obj [0]));
				return errorDescriptionBuilder.toString();
			}
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_OFFLOADED.equals(errorCode)){
			Object [] obj = errorVO.getErrorData();
			if(obj!=null && obj.length ==1){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ");
				errorDescriptionBuilder.append(String.valueOf(obj [0]))
				.append(" is already offloaded");
				return errorDescriptionBuilder.toString();
			}
		}else if(INVALID_ULDTYPE.equals(errorCode)){		
				StringBuilder errorDescriptionBuilder = new StringBuilder("The specified ULD number is invalid");
				
				return errorDescriptionBuilder.toString();
			
		}else if(CARRIER_CONTAINER_REASSIGN.equals(errorCode)){		

			Object [] obj = errorVO.getErrorData();
			if(obj!=null && obj.length>1){
				StringBuilder errorDescriptionBuilder = new StringBuilder("The container ");	
				errorDescriptionBuilder.append(obj[0]);
				errorDescriptionBuilder.append(" is already assigned to ");
				errorDescriptionBuilder.append(obj[1]);
				return errorDescriptionBuilder.toString();
			}

		}else if("STLDATA".equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("Stale Data");	
			return errorDescriptionBuilder.toString();
		}else if(ULD_ALREADY_IN_USE_AT_ANOTHER_PORT.equals(errorCode)){	
			Object [] obj = errorVO.getErrorData();
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD is already assigned to another carrier/flight at airport ");	
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			return errorDescriptionBuilder.toString();
		}else if(ULD_DOES_NOT_EXISTS_MAIL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist in the system.");
			return errorDescriptionBuilder.toString();
		}else if(ULD_IS_NOT_IN_AIRPORT_MAIL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is not present at current port.");
			return errorDescriptionBuilder.toString();
		}else if(ULD_IS_NOT_IN_THE_SYSTEM_MAIL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist in the system.");
			return errorDescriptionBuilder.toString();
		}else if(ULD_IS_NOT_OPERATIONAL_MAIL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is non-operational.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist in the system.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist for the airport.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_ERROR_ULDISLOST.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is lost.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is non-operational.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist in the airline stock.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" does not exist in the system.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is not present at this airport.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_WARNING_ULDISLOST.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is lost.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is not operational.");
			return errorDescriptionBuilder.toString();
		}else if(MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("ULD ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
			}
			errorDescriptionBuilder.append(" is not available in  the airline stock.");
			return errorDescriptionBuilder.toString();
		}else if(MailConstantsVO.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("Container ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
				errorDescriptionBuilder.append(" is already assigned to the same flight as ");
				if(MailConstantsVO.BULK_TYPE.equals(obj[1])){
					errorDescriptionBuilder.append("BULK");
				}else{
					errorDescriptionBuilder.append("ULD");
				}
				errorDescriptionBuilder.append(".");
			}
			return errorDescriptionBuilder.toString();
		}else if(FLIGHT_TO_BE_ACTIONED.equals(errorCode)){	
			StringBuilder errorDescriptionBuilder = new StringBuilder("The flight ");
			Object [] obj = errorVO.getErrorData();
			if(obj != null && obj.length>0){
				errorDescriptionBuilder.append(obj[0]);
				errorDescriptionBuilder.append(" ");
				errorDescriptionBuilder.append(obj[1]);
				errorDescriptionBuilder.append(" on ");
				errorDescriptionBuilder.append(obj[2]);
				
				errorDescriptionBuilder.append("in TBA status. Only unassignment/offloading of mailbags permitted.");
				
			}
			return errorDescriptionBuilder.toString();
		}
		else{
			errorDescription = "Unreachable error : "+errorCode;
		}		
		return errorDescription;
	}

	/**
	 * @author A-3227
	 * @param mailbagVO
	 * @return
	 */
	private MailbagVO populateMailPKFields(MailbagVO mailbagVO){
		log.entering("UploadMailDetailsCommand","populateMailPKFields");
		String mailBagId = mailbagVO.getMailbagId();	
		double vol = 0.0D;
		if(mailBagId != null && mailBagId.trim().length() > 0){
			mailbagVO.setOoe(mailBagId.substring(0,6));
			mailbagVO.setDoe(mailBagId.substring(6,12));
			mailbagVO.setMailCategoryCode(mailBagId.substring(12,13));
			mailbagVO.setMailSubclass(mailBagId.substring(13,15));
			mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
			mailbagVO.setYear(Integer.parseInt(mailBagId.substring(15,16)));
			mailbagVO.setDespatchSerialNumber(mailBagId.substring(16,20));
			mailbagVO.setReceptacleSerialNumber(mailBagId.substring(20,23));
			mailbagVO.setHighestNumberedReceptacle(mailBagId.substring(23,24));
			mailbagVO.setRegisteredOrInsuredIndicator(mailBagId.substring(24,25));
			/*mailbagVO.setWeight(Double.parseDouble(mailBagId.substring(25,29))/10);
			mailbagVO.setStrWeight(mailBagId.substring(25,29));*/
			mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagId.substring(25,29))/10));
			mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailBagId.substring(25,29))/10));//added by A-7371
			if(densityFactor == 0.0D){
				densityFactor = getDensityFactorForMail(mailbagVO.getCompanyCode());
			}
			/*try{
				vol=UnitFormatter.getRoundedValue(UnitConstants.VOLUME, UnitConstants.VOLUME_UNIT_CUBIC_CENTIMETERS, mailbagVO.getWeight()/densityFactor);
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }*/
			vol= mailbagVO.getWeight().getRoundedSystemValue()/densityFactor;//added by A-7371
			//vol = getScaledValue(mailbagVO.getWeight()/densityFactor,2);
			if(MailConstantsVO.MINIMUM_VOLUME > vol) {
				vol = MailConstantsVO.MINIMUM_VOLUME;
			}
  			//mailbagVO.setVolume(vol);
			mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,vol));//added by A-7371
		}
		log.exiting("UploadMailDetailsCommand","populateMailPKFields");
		return mailbagVO;
	}
	
	/**
	 * @author A-3227
	 * @param companyCode
	 * @return
	 */
	private double getDensityFactorForMail(String companyCode){
		log.entering("UploadMailDetailsCommand","getDensityFactorForMail");
		CommodityDelegate  commodityDelegate = new CommodityDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		Collection<String> commodites = new ArrayList<String>();
		CommodityValidationVO commodityValidationVO = null;
		Map<String,CommodityValidationVO> densityMap = null;

		systemParameterCodes.add(MAIL_COMMODITY_SYSPAR);		
		String commodityCode = findSystemParameterByCodes(systemParameterCodes).get(MAIL_COMMODITY_SYSPAR);

		if(commodityCode != null && commodityCode.trim().length() > 0) {
			commodites.add(commodityCode);
			try {
				densityMap = commodityDelegate.validateCommodityCodes(companyCode, commodites);
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}
			if(densityMap != null && densityMap.size() > 0){
				commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY:::::::::", commodityValidationVO.getDensityFactor());
				if(commodityValidationVO != null) {
					return commodityValidationVO.getDensityFactor();
				}
			}
		}
		log.exiting("UploadMailDetailsCommand","getDensityFactorForMail");
		return 1;

	}




	/**
	 * @author A-3227
	 * This method will be invoked at the time of screen load
	 * @param systemParameterCodes
	 * @return Map<String, String>
	 */
	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.entering("UploadMailDetailsCommand","findSystemParameterByCodes");
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		log.exiting("UploadMailDetailsCommand","findSystemParameterByCodes");
		return results;
	}
	
	
	/**
     * Method is used to find whether the flight POU is there in
     * the flight route
     * @param reassignContainerForm
     * @param flightValidationVO
     * @param logonAttributes
     * @return Collection<ErrorVO>
     */
	 private Collection<ErrorVO> isPouValid(
			 ScannedMailDetailsVO scannedVO,
	    		FlightValidationVO flightValidationVO,
	    		LogonAttributes logonAttributes) {
	    	
	    	log.entering("SaveUploadCommand","isPouValid");
	    	
	    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
	    	if(scannedVO.getPou()== null || scannedVO.getPou().trim().length() == 0){
	    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
				validationerrors.add(errorVO);
				return validationerrors;
	    	}
	    	
	    	/*
			 * Obtain the collection of POUs
			 */
			Collection<String> pointOfUnladings =
				getPointOfUnladings(
						flightValidationVO.getFlightRoute(), logonAttributes);	
			StringBuilder fullroute = new StringBuilder("");
			for (String route : pointOfUnladings) {
				fullroute.append(route).append("-");
			}
			
			if(!pointOfUnladings.contains(scannedVO.getPou().toUpperCase())) {
				Object[] obj = {scannedVO.getPou().toUpperCase(),
						flightValidationVO.getCarrierCode(),
						flightValidationVO.getFlightNumber(),
						fullroute.substring(0,fullroute.length()-1)};
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
				validationerrors.add(errorVO);
			}
	    	
	    	return validationerrors;
	    }
	 
	 
	 /**
		 * The method will obtain the collection of POUs
		 * from the route
		 * @param route
		 * @param logonAttributes
		 * @return pointOfUnladings
		 */
		private Collection<String> getPointOfUnladings(
				String route, LogonAttributes logonAttributes) {
			log.exiting("SaveUploadCommand", "getPOUs");

			Collection<String> pointOfUnladings = new ArrayList<String>();
			/*
			 * Split the route string using the delimeter "-"
			 */
			String[] stations = route.split(ROUTE_DELIMETER);
			if(route != null && route.length() > 0){

				for(int index=1; index < stations.length; index++) {
					/*
					 * the pous should start from the current station
					 */
					pointOfUnladings.add(stations[index]);
				}
			}
			log.log(Log.FINE, "pointOfUnladings ---> ", pointOfUnladings);
			log.exiting("SaveUploadCommand", "getPOUs");
			
			return pointOfUnladings;
		}


	/**
	 * @param pou
	 * @return
	 */
	private String constructBulkULDNumber(String airport) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		String bulkULDNumber = "";
		if(airport != null && airport.trim().length() > 0) {
			bulkULDNumber =  new StringBuilder().append(MailConstantsVO.CONST_BULK).append(
					MailConstantsVO.SEPARATOR).append(airport).toString();
		}
		return bulkULDNumber;
	}

	/**
	 * Method to get the AirlineValidationVO
	 * @param companyCode
	 * @param alphaCode
	 * @param invocationContext
	 * @return airlineValidationVO
	 */
	private AirlineValidationVO populateAirlineValidationVO(String companyCode,
			String alphaCode, Collection<ErrorVO> errors){
		log.entering("UploadMailDetailsCommand","populateAirlineValidationVO");
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;

		try {
			airlineValidationVO =
				airlineDelegate.validateAlphaCode(companyCode, alphaCode);

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessageVO().getErrors();
		}

		if (errors != null && errors.size() > 0){
			ErrorVO error = new ErrorVO(INVALID_CARRIER,new Object[]{alphaCode});
			errors.add(error);
		}
		log.exiting("UploadMailDetailsCommand","populateAirlineValidationVO");
		return airlineValidationVO;
	}
	/**
	 * Validate the flight and if the flight is valid ,
	 * returns the collection of flightvalidation vos
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param airlineValidationVO
	 * @param invocationContext
	 * @param direction
	 * @return
	 */
	private Collection<FlightValidationVO> populateFlightValidationVO(
			ScannedMailDetailsVO scannedVO,
			AirlineValidationVO airlineValidationVO,
			LogonAttributes logonAttributes,
			Collection<ErrorVO> errorVOs){

			log.entering("UploadMailDetailsCommand","populateFlightValidationVO");
			Collection<ErrorVO> errors = null;
			/*
			 * Populate the flightFilterVo
			 */
			FlightFilterVO flightFilterVO =
				getFlightFilterVO(scannedVO,logonAttributes,airlineValidationVO);
			log.log(Log.FINE, "\nflightFilterVO ---> ", flightFilterVO);
			/*
			 * Validate flight - obtain the flightValidationVO
			 */
			MailTrackingDefaultsDelegate delegate =	new MailTrackingDefaultsDelegate();
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				flightValidationVOs = delegate.validateFlight(flightFilterVO);
				log.log(Log.FINE, "flightValidationVOs --> ",
						flightValidationVOs);
			} catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessageVO().getErrors();
			}
			/*
			 * If error is present then set it to the
			 * invocationContext and return
			 */
			if(flightValidationVOs == null || flightValidationVOs.size() == 0){
				if(errors != null && errors.size() > 0) {
					errorVOs.addAll(errors);
				}else {
					Object[] obj = {scannedVO.getCarrierCode(),scannedVO.getFlightNumber(),
							scannedVO.getFlightDate().toString().substring(0,11)};
					ErrorVO error = new ErrorVO (FLIGHT_NOT_PRESENT,obj);
					errorVOs.add(error);
				}
			}
			
			log.exiting("UploadMailDetailsCommand","populateFlightValidationVO");
			return flightValidationVOs;
     }

	/**
	 * Methd to get the Flight FilterVO
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param direction
	 * @param airlineValidationVO
	 * @return
	 */
	private FlightFilterVO getFlightFilterVO(
			ScannedMailDetailsVO scannedVO,
			LogonAttributes logonAttributes,
			AirlineValidationVO airlineValidationVO){
		log.entering("UploadMailDetailsCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setCompanyCode(scannedVO.getCompanyCode());
		flightFilterVO.setActiveAlone(true);
		flightFilterVO.setFlightNumber(scannedVO.getFlightNumber().toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightDate(scannedVO.getFlightDate());
		
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedVO.getProcessPoint()) || 
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedVO.getProcessPoint())){
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedVO.getProcessPoint()) || 
				MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedVO.getProcessPoint())){
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		}
		log.exiting("UploadMailDetailsCommand", "getFlightFilterVO");
		return flightFilterVO;
	}
	
	
	/**
	 * 
	 * @param carrierCode
	 * @param flightNumber
	 * @param fltDat
	 * @return
	 */
	private String 	constructFlightNotClosedMessageForUpload(String carrierCode,String flightNumber,LocalDate fltDat){
		StringBuilder messageString= new StringBuilder("Cannot Offload. Container is currently in an open flight ");
		messageString.append(carrierCode).append("-").append(flightNumber).append(" : ").append(fltDat.toDisplayDateOnlyFormat());
		return messageString.toString();
	}
	/**
	 * 
	 * This method is used for validating outbound flights for transfer and reassign
	 * @param scannedVO
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private Collection<ErrorVO> validateOutboundFlightForTransferSession(
			ScannedMailDetailsVO scannedVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession) {
		log.entering("validateFlightForUploadSession","execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
        Collection<FlightValidationVO> flightValidationVOs = null;
        FlightValidationVO flightValidationVO = null;
		String alphaCode = scannedVO.getToCarrierCode().toUpperCase();
		/*
		 * Airline Validation
		 */
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
				scannedVO.getCompanyCode(), alphaCode, errorVOs);
		if (errorVOs != null && errorVOs.size() > 0) { 
			return errorVOs;
		}
		if(airlineValidationVO != null) {
			if((!CARRIER_FLT_NUM.equals(scannedVO.getToFlightNumber())) &&
					(scannedVO.getToFlightNumber()!=null && scannedVO.getToFlightNumber().trim().length()>0) ){
				/**
				 * For transfering to a flight
				 */
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setStation(logonAttributes.getAirportCode());
				flightFilterVO.setCompanyCode(scannedVO.getCompanyCode());
				flightFilterVO.setActiveAlone(true);
				flightFilterVO.setFlightNumber(scannedVO.getToFlightNumber().toUpperCase());
				flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
				flightFilterVO.setFlightDate(scannedVO.getToFlightDate());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				try{
					flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
				}
				catch(BusinessDelegateException businessDelegateException){
					errorVOs = handleDelegateException(businessDelegateException);
					businessDelegateException.getMessageVO().getErrors();
				}
				if(flightValidationVOs == null || flightValidationVOs.size()==0){
					Object[] obj = {scannedVO.getToCarrierCode(),scannedVO.getToFlightNumber(),
							scannedVO.getToFlightDate().toString().substring(0,11)};
					ErrorVO error = new ErrorVO (FLIGHT_NOT_PRESENT,obj);
					errorVOs.add(error);
					return errorVOs;
				}else if(flightValidationVOs != null && flightValidationVOs.size()==1){
					flightValidationVO = ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
					if(FLIGHT_STATUS_CANCELLED.equalsIgnoreCase(flightValidationVO.getFlightStatus())){
						StringBuffer canErrorMsg = new StringBuffer("");
						canErrorMsg.append(scannedVO.getCarrierCode()).append("-")
						.append(scannedVO.getFlightNumber()).append(" ").append(scannedVO.getFlightDate().toString().substring(0,11));
						Object[] obj = {canErrorMsg.toString()};
						ErrorVO error = new ErrorVO (FLIGHT_CANCELLED,obj);	
						errorVOs.add(error);
						return errorVOs;
					}
					/**
					 * ADDED FOR SAA 410 STARTS
					 */
					if(flightValidationVO != null){
		    			if(flightValidationVO.isTBADueRouteChange()){
		    				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),scannedVO.getFlightDate().toString().substring(0,11)};
		    				ErrorVO errorVO = new ErrorVO(FLIGHT_TO_BE_ACTIONED,obj);
		    				if(errorVOs == null){
		    					errorVOs = new ArrayList<ErrorVO>();
		    				}
		    				errorVOs.add(errorVO);
		    				return errorVOs;
		    			}
		    		}
					/**
					 * ADDED FOR SAA 410 ENDS
					 */
					
					boolean isFlightClosed = false;
					OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
					operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
					operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
					operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
					operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
					operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
					operationalFlightVO.setPol(logonAttributes.getAirportCode());
					operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
					try{
						isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
					}
					catch(BusinessDelegateException businessDelegateException){
						return handleDelegateException(businessDelegateException);
					}
					if(isFlightClosed){
						ErrorVO errorVO = new ErrorVO (FLIGHT_INBOUND_CLOSED);
						errorVOs.add(errorVO);
						return errorVOs;
					}
					scannedVO.setToCarrierid(airlineValidationVO.getAirlineIdentifier());
					scannedVO.setToFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					scannedVO.setToLegSerialNumber(flightValidationVO.getLegSerialNumber());				

				}else if(flightValidationVOs.size() > 1){
					//TODO : Display the duplicate flight popup
					log.log(Log.INFO,"###### Duplicate Exist####");
					duplicateFlightSession.setFlightValidationVOs((
							ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					duplicateFlightSession.setFlightFilterVO(getFlightFilterVO(scannedVO,logonAttributes,airlineValidationVO));
					ErrorVO error = new ErrorVO (FLIGHT_DUPLICATE);
					errorVOs.add(error);

					return errorVOs;

				}
			}else{
				/**
				 * For transfering to a carrier
				 */
				scannedVO.setToCarrierid(airlineValidationVO.getAirlineIdentifier());
				scannedVO.setToFlightNumber(CARRIER_FLT_NUM);
				scannedVO.setToFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				scannedVO.setToLegSerialNumber(MailConstantsVO.DESTN_FLT);
				//scannedVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
				
			}	
		}else{
			ErrorVO error = new ErrorVO (INVALID_CARRIER);	
			Object [] obj ={alphaCode};
			error.setErrorData(obj);
			errorVOs.add(error);
		}
		log.exiting("validateFlightForUploadSession","execute");
		return errorVOs;
	}
	
	/**
	 * constructContainerVO
	 * @param containerDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private ContainerVO constructContainerVO(ContainerDetailsVO containerDetailsVO,LogonAttributes logonAttributes){
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		containerVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerVO.setFlightDate(containerDetailsVO.getFlightDate());
		containerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		containerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerVO.setPou(containerDetailsVO.getPou());
		containerVO.setAssignedPort(logonAttributes.getAirportCode());
		containerVO.setAssignedUser(logonAttributes.getUserId());
		containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());			
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setOperationFlag(containerDetailsVO.getOperationFlag());
		containerVO.setType(containerDetailsVO.getContainerType());
		 
		return containerVO;
	}
	
	/**
	 * constructContainerVO
	 * @param containerDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private ContainerVO constructContainerVO(ScannedMailDetailsVO mailDetailsVO,LogonAttributes logonAttributes){
		ContainerVO containerVO = new ContainerVO();
		containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setCompanyCode(mailDetailsVO.getCompanyCode());
		containerVO.setCarrierCode(mailDetailsVO.getToCarrierCode());
		containerVO.setCarrierId(mailDetailsVO.getToCarrierid());
		containerVO.setAssignedUser(logonAttributes.getUserId());
		containerVO.setAssignedPort(logonAttributes.getAirportCode());
		containerVO.setLastUpdateUser(logonAttributes.getUserId());
		containerVO.setOperationTime(mailDetailsVO.getOperationTime());
		return containerVO;
	}

	/**
	 * constructOperationalFlightVO
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 */
	private OperationalFlightVO constructOperationalFlightVO(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LogonAttributes logonAttributes){
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(logonAttributes.getUserId());    	
    	operationalFlightVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));

		operationalFlightVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(scannedMailDetailsVO.getCarrierId());    	
    	operationalFlightVO.setFlightStatus(scannedMailDetailsVO.getFlightStatus());
    	operationalFlightVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
    	operationalFlightVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
    	operationalFlightVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
    	operationalFlightVO.setPou(scannedMailDetailsVO.getPou());
    	
		return operationalFlightVO;
	}
}
