/*
 * SaveAttachRoutingCommand.java Created on Jul 1 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class SaveAttachRoutingCommand.
 *
 * @author A-5991
 */
public class SaveAttachRoutingCommand extends BaseCommand {
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.BaseCommand#breakOnInvocationFailure()
	 */
	@Override
	public boolean breakOnInvocationFailure() {
		
		return true;
	}
	
	/** The log. */
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/** TARGET. */
	private static final String TARGET = "screenload_success";
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mail.operations";	
	
	/** The Constant SCREEN_ID. */
	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	
	/** The Constant SAVE_SUCCESS. */
	private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
	
	/** The Constant HYPHEN. */
	private static final String HYPHEN ="-";
	
	/** Status of flag. */
	private static final String OUTBOUND = "O";
	
	/** Screen id. */
	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
	
	/** Screen id. */
	private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";
	
	/** Flight Module name. */
	private static final String MODULE_NAME_FLIGHT =  "flight.operation";
	
	/** Target string. */
	private static final String DUPLICATE_SUCCESS = "duplicate_success";	
	
	
	/**
	 * This method overrides the executre method of BaseComand class.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("SaveConsignmentCommand","execute");
		
		MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
		MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		errors = validateForm(mailManifestForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}else{
			
			ConsignmentDocumentVO consignmentDocumentVO = mailManifestSession.getConsignmentDocumentVO();
						
			validateConsignment(mailManifestForm,mailManifestSession,invocationContext,
					consignmentDocumentVO,logonAttributes);			
			if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){				
				invocationContext.target = TARGET;
				return;
			}
			
			if (mailManifestSession.getMailVOs()!=null && mailManifestSession.getMailVOs().size()>0){
				Page<MailInConsignmentVO> mailbagVOs = 
					new Page<MailInConsignmentVO>((ArrayList<MailInConsignmentVO>)
							mailManifestSession.getMailVOs(),0,0,0,0,0,false);
				ArrayList<MailInConsignmentVO> newMails = new ArrayList<MailInConsignmentVO>();
				Page<MailInConsignmentVO> newMailbagVOs = new Page<MailInConsignmentVO>(newMails,0,0,0,0,0,false);
				
				for(MailInConsignmentVO mailInConsignment : mailbagVOs){
					if(mailInConsignment.getConsignmentNumber() != null && 
							mailInConsignment.getConsignmentNumber().length() > 0 &&
							mailInConsignment.getPaCode() != null &&
							mailInConsignment.getPaCode().length() > 0){
						/*
						 * Checking whether the Listed Consignment and the
						 * the Consignment in the MailInConsignment VOs are same
						 * else throw error. This case arises for Despatch only,since it 
						 * is accepted with a consignment Doc number 
						 */
						if(!mailInConsignment.getConsignmentNumber().equalsIgnoreCase(
								consignmentDocumentVO.getConsignmentNumber()) &&
								mailInConsignment.getPaCode().equalsIgnoreCase(
										consignmentDocumentVO.getPaCode())){

							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.attachrouting.msg.err.dsnalreadyattachedtoconsignment",
									new Object[]{mailInConsignment.getConsignmentNumber().toUpperCase(),
									mailInConsignment.getPaCode().toUpperCase()});
							invocationContext.addError(errorVO);
							invocationContext.target = TARGET;
							return;
						}
						if(mailInConsignment.getReceptacleSerialNumber()!=null && mailInConsignment.getReceptacleSerialNumber().trim().length()>0){
							newMails.add(mailInConsignment);
						}
					}else {
						/*
						 * If Consignment Doc Num does not exist, then it is not attached to any Consignment.
						 */
						mailInConsignment.setCompanyCode(consignmentDocumentVO.getCompanyCode());
						mailInConsignment.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						mailInConsignment.setPaCode(consignmentDocumentVO.getPaCode());	
						newMails.add(mailInConsignment);
					}				
				}
				consignmentDocumentVO.setMailInConsignmentVOs(newMailbagVOs);
			}
			
			validateRoutingDetails(mailManifestForm,mailManifestSession,invocationContext,
					consignmentDocumentVO,logonAttributes,duplicateFlightSession,maintainFlightSession);
			
			if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0){
				
				invocationContext.target = TARGET;
				return;
			}
			if("Y".equals(mailManifestForm.getDuplicateFlightStatus())){
				mailManifestSession.setConsignmentDocumentVO(consignmentDocumentVO);
				
				invocationContext.target = TARGET;
				return;
			}
			
			
			
			consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentDocumentVO.setAirportCode(logonAttributes.getAirportCode());
			consignmentDocumentVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			log.log(Log.FINE, "Going To Save ...in command",
					consignmentDocumentVO);
			try {
				new MailTrackingDefaultsDelegate().saveConsignmentForManifestedDSN(consignmentDocumentVO);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				
				invocationContext.target = TARGET;
				return;
			}else{
				ConsignmentDocumentVO conDocumentVO = new ConsignmentDocumentVO();
				mailManifestSession.setConsignmentDocumentVO(conDocumentVO);
				mailManifestForm.setConDocNo("");
				mailManifestForm.setPaCode("");
				mailManifestForm.setDirection("");
				mailManifestForm.setDirection("O");
				mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				mailManifestForm.setRoutingStatus("OK");
				invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
			}
			
		}
		invocationContext.target = TARGET;
		log.exiting("SaveConsignmentCommand","execute");
	}
	
	/**
	 * Method to validate form.
	 *
	 * @param mailManifestForm the mail manifest form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(MailManifestForm mailManifestForm) {
		String conDocNo = mailManifestForm.getConDocNo();
		String paCode = mailManifestForm.getPaCode();
		String direction = mailManifestForm.getDirection();
		String conDate = mailManifestForm.getConDate();
		String type = mailManifestForm.getContype();
		String routeOpFlag[] = mailManifestForm.getRouteOpFlag();
		boolean isEmpty=true;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}
		if(direction == null || ("".equals(direction.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.operationtype.empty"));
		}
		if(conDate == null || ("".equals(conDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condate.empty"));
		}
		if(type == null || ("".equals(type.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.type.empty"));
		}
		if(routeOpFlag!=null && routeOpFlag.length>1){
			for(int count=0;count<routeOpFlag.length;count++){				
				if(!"NOOP".equals(routeOpFlag[count])){
					isEmpty=false;
					break;
				}
			}
		}
		if(isEmpty){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.type.routeempty"));
		}
		return errors;
	}
	
	
	
	/**
	 * This method is to validate consignments.
	 *
	 * @param mailManifestForm the mail manifest form
	 * @param mailManifestSession the mail manifest session
	 * @param invocationContext the invocation context
	 * @param consignmentDocumentVO the consignment document vo
	 * @param logonAttributes the logon attributes
	 */
	private void validateConsignment(MailManifestForm mailManifestForm,
			MailManifestSession mailManifestSession,InvocationContext invocationContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes){
		
		Collection<ErrorVO> errors = null;
		
//		validate PA code
		String pacode = mailManifestForm.getPaCode();
		if (pacode != null && pacode.trim().length() > 0) {
			log.log(Log.FINE, "Going To validate PA code ...in command");
			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
			try {
				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
						logonAttributes.getCompanyCode(),pacode.toUpperCase());
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (postalAdministrationVO == null) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
						new Object[]{pacode.toUpperCase()}));
				invocationContext.target = TARGET;
				return;
			}
		}
		
//		VALIDATING Type with Mail Category Code
		int invalidCategory = 0;
		Collection<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentcollVOs();
		String type = consignmentDocumentVO.getType();
		if(mailVOs != null && mailVOs.size() > 0) {
			for(MailInConsignmentVO mailbagVO:mailVOs) {
				if(!"D".equals(mailbagVO.getOperationFlag())){
					String category = mailbagVO.getMailCategoryCode();
					if("CN41".equals(type)){
						if(!"B".equals(category)){
							invalidCategory = 1;
						}
					}
					if("CN38".equals(type)){
						if("B".equals(category)){
							invalidCategory = 2;
						}
					}
				}
			}
		}
		if(invalidCategory == 1){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchsal"));
			invocationContext.target = TARGET;
			return;
		}
		if(invalidCategory == 2){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchnosal"));
			invocationContext.target = TARGET;
			return;
		}
		
		
	}
	
	
	/**
	 * This method is to validate consignments RoutingDetails.
	 *
	 * @param mailManifestForm the mail manifest form
	 * @param mailManifestSession the mail manifest session
	 * @param invocationContext the invocation context
	 * @param consignmentDocumentVO the consignment document vo
	 * @param logonAttributes the logon attributes
	 * @param duplicateFlightSession the duplicate flight session
	 * @param maintainFlightSession the maintain flight session
	 */
	private void validateRoutingDetails(MailManifestForm mailManifestForm,
			MailManifestSession mailManifestSession,InvocationContext invocationContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes,DuplicateFlightSession duplicateFlightSession
			,MaintainFlightSession maintainFlightSession){
		
		Collection<ErrorVO> errors = null;
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		
//		VALIDATING CarrierCode
		String invalidCarrierCode = "";
		Collection<RoutingInConsignmentVO> routingVOs =  consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						routingVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber().toUpperCase());
						routingVO.setPaCode(consignmentDocumentVO.getPaCode().toUpperCase());
						if(routingVO.getOnwardCarrierCode()!=null && routingVO.getOnwardCarrierCode().trim().length()>0){
						String carrierCode = routingVO.getOnwardCarrierCode().toUpperCase();
						AirlineValidationVO airlineValidationVO = null;
						
						airlineValidationVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),carrierCode);
						
						if (airlineValidationVO == null) {
							if("".equals(invalidCarrierCode)){
								invalidCarrierCode = carrierCode;
							}else{
								invalidCarrierCode = new StringBuilder(invalidCarrierCode).append(",").append(carrierCode).toString();
							}
						}else{
							routingVO.setOnwardCarrierId(airlineValidationVO.getAirlineIdentifier());
						}
					}
					}
					String flightNumber = (routingVO.getOnwardFlightNumber());
					routingVO.setOnwardFlightNumber(flightNumber);
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		
		if(!"".equals(invalidCarrierCode)){
			formErrors.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
					new Object[]{invalidCarrierCode.toUpperCase()}));
			invocationContext.addAllError(formErrors);
			invocationContext.target = TARGET;
			return;
		}
		Collection<RoutingInConsignmentVO> validRoutingVos=new ArrayList<RoutingInConsignmentVO>();
		for(RoutingInConsignmentVO routing:routingVOs){
			if((routing.getOnwardCarrierCode()!=null && routing.getOnwardCarrierCode().trim().length()>0) &&
					(routing.getOnwardFlightNumber()!=null && routing.getOnwardFlightNumber().trim().length()>0)){
				validRoutingVos.add(routing);        
			}
		}
		
		consignmentDocumentVO.setRoutingInConsignmentVOs(validRoutingVos);
		mailManifestSession.setConsignmentDocumentVO(consignmentDocumentVO);
		errors = null;
//		VALIDATING POL
		String invalidPOL = "";
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						if(routingVO.getPol()!=null && routingVO.getPol().trim().length()>0){
						String pol = routingVO.getPol().toUpperCase();
						AirportValidationVO airportValidationVO = null;
						
						airportValidationVO = new AreaDelegate().validateAirportCode(
								logonAttributes.getCompanyCode(),pol);
						
						if (airportValidationVO == null) {
							if("".equals(invalidPOL)){
								invalidPOL = pol;
							}else{
								invalidPOL = new StringBuilder(invalidPOL).append(",").append(pol).toString();
							}
						}
					}
				}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
		}
		if(!"".equals(invalidPOL)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpol",
					new Object[]{invalidPOL.toUpperCase()}));
			invocationContext.addAllError(formErrors);
			invocationContext.target = TARGET;
			return;
		}
		errors = null;
//		VALIDATING POU
		String invalidPOU = "";
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
					if(!"D".equals(routingVO.getOperationFlag())){
						if(routingVO.getPou()!=null && routingVO.getPou().trim().length()>0){
						String pou = routingVO.getPou().toUpperCase();
						AirportValidationVO airportValidationVO = null;
						
						airportValidationVO = new AreaDelegate().validateAirportCode(
								logonAttributes.getCompanyCode(),pou);
						
						if (airportValidationVO == null) {
							if("".equals(invalidPOU)){
								invalidPOU = pou;
							}else{
								invalidPOU = new StringBuilder(invalidPOU).append(",").append(pou).toString();
							}
						}
					}
				}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
		}
		if(!"".equals(invalidPOU)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpou",
					new Object[]{invalidPOU.toUpperCase()}));
			invocationContext.addAllError(formErrors);
			invocationContext.target = TARGET;
			return;
		}
		
		
//		Check for same POL and POU
		int sameAP = 0;
		if(routingVOs != null && routingVOs.size() > 0) {
			StringBuilder errFlightNumbers = null;
			for(RoutingInConsignmentVO routingVO:routingVOs) {
				log.log(Log.FINE, " routingVO ", routingVO);
				if(!"D".equals(routingVO.getOperationFlag())){
					log.log(Log.FINE, " D.equals(routingVO.getOperationFlag()");
					String pol="";
					String pou="";
					if(routingVO.getPol()!=null && routingVO.getPol().trim().length()>0){
					pol = routingVO.getPol().toUpperCase();           
					pou = routingVO.getPou().toUpperCase();
					if (pol.equals(pou)) {
						sameAP = 1;
						formErrors.add(new ErrorVO("mailtracking.defaults.consignment.sameairport",
								new Object[]{new StringBuilder(routingVO.getOnwardCarrierCode())
								.append("").append(routingVO.getOnwardFlightNumber()).toString()}));
					}
					}
//					VALIDATE FLIGHT CARRIER CODE
					AirlineDelegate airlineDelegate = new AirlineDelegate();
					AirlineValidationVO airlineValidationVO = null;
					String flightCarrierCode = routingVO.getOnwardCarrierCode();
					errors = null;
					if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
						try {
							airlineValidationVO = airlineDelegate.validateAlphaCode(
									logonAttributes.getCompanyCode(),
									flightCarrierCode.trim().toUpperCase());
							
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {flightCarrierCode.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidCarrier"
									,obj));
						}
					}
					
					//Checking Flight number  validation
					
					if(flightCarrierCode!=null && flightCarrierCode.equals(logonAttributes.getOwnAirlineCode())){
						Collection<FlightValidationVO> flightValidationVOs = new ArrayList<FlightValidationVO>();
						FlightFilterVO flightFilterVO = handleFlightFilterVO(
								routingVO,logonAttributes);
						flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
						log.log(Log.FINE,
								"consignmentForm.getDuplicateFlightStatus() ",
								mailManifestForm.getDuplicateFlightStatus());
						log.log(Log.FINE,
								"routingVO.getIsDuplicateFlightChecked() ",
								routingVO.getIsDuplicateFlightChecked());
							//	if("".equals(mailManifestForm.getDuplicateFlightStatus()) && 
					//			("".equals(routingVO.getIsDuplicateFlightChecked()) || routingVO.getIsDuplicateFlightChecked() == null )){
							try {
								flightValidationVOs =
									new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
								log.log(Log.FINE, "flightValidationVOs ",
										flightValidationVOs);
							}catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
							}
					/*	}else{
							flightValidationVOs.add(duplicateFlightSession.getFlightValidationVO());
							mailManifestForm.setDuplicateFlightStatus("");
						}*/
						if (errors != null && errors.size() > 0) {
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {flightCarrierCode.toUpperCase(),
									routingVO.getOnwardFlightNumber(),
									routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
									,obj));
						}
						if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
							Object[] obj = {flightCarrierCode.toUpperCase(),
									routingVO.getOnwardFlightNumber(),
									routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
									,obj));
						}else if (flightValidationVOs.size()== 1) {
							log
									.log(
											Log.FINE,
											"consignmentForm.getDuplicateFlightStatus() ",
											mailManifestForm.getDuplicateFlightStatus());
							List<FlightValidationVO> validflightvos = (List<FlightValidationVO>)flightValidationVOs;
							FlightValidationVO flightValidationVO = validflightvos.get(0);
							
							//Validating whether POU present in the Flight Route
							String route ="";
							if(flightValidationVO!=null){
							route = flightValidationVO.getFlightRoute();          
							}
							if(route != null && route.length() >0) {
								StringTokenizer stationTokens = new StringTokenizer(route,"-");
								boolean isPouInRoute = false;
								while(stationTokens.hasMoreTokens()) {
									if(pou.equals(stationTokens.nextToken()) && formErrors.size() == 0 ) {
										isPouInRoute = true;
										break;
									}        				
								}
								if(!isPouInRoute) {
									Object[] obj = {pou,
											flightCarrierCode.toUpperCase(),
											routingVO.getOnwardFlightNumber(),
											route};
									formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflightRoute",obj));
								}        				
							}
							//A-5249 from ICRD-84046
							if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
				                    FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
				                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
								if(errFlightNumbers==null){
									errFlightNumbers = new StringBuilder();
								}else{
									errFlightNumbers.append(", ");
								}
								errFlightNumbers.append(flightCarrierCode.toUpperCase()).append(flightValidationVO.getFlightNumber());
							}
							routingVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
						}else {
							duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)(flightValidationVOs));
							duplicateFlightSession.setParentScreenId(SCREEN_ID);
							duplicateFlightSession.setFlightFilterVO(flightFilterVO);
							maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
							mailManifestForm.setDuplicateFlightStatus(FLAG_YES);
							routingVO.setIsDuplicateFlightChecked(FLAG_YES);
							break;
						}
						
					}else{
						routingVO.setOnwardCarrierSeqNum(MailConstantsVO.DESTN_FLT);
					}
					
					
//					VALIDATE POL & POU
					Collection<ErrorVO> polErrors = new ArrayList<ErrorVO>();
					Collection<ErrorVO> pouErrors = new ArrayList<ErrorVO>();
					String org = routingVO.getPol();
					String dest = routingVO.getPou();
					AreaDelegate areaDelegate = new AreaDelegate();
					AirportValidationVO airportValidationVO = null;
					errors = null;
					if (org != null && !"".equals(org)) {
						try {
							airportValidationVO = areaDelegate.validateAirportCode(
									logonAttributes.getCompanyCode(),
									org.toUpperCase());
							
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							polErrors.addAll(errors);
						}
						if (polErrors != null && polErrors.size() > 0) {
							Object[] obj = {org.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOL"
									,obj));
						}}
					errors = null;
					if (dest != null && !"".equals(dest)) {
						try {
							airportValidationVO = areaDelegate.validateAirportCode(
									logonAttributes.getCompanyCode(),
									dest.toUpperCase());
							
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && errors.size() > 0) {
							pouErrors.addAll(errors);
						}
						if (pouErrors != null && pouErrors.size() > 0) {
							Object[] obj = {dest.toUpperCase()};
							sameAP = 1;
							formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOU"
									,obj));
						}
					}
					
					
					
				}
			}
			if(errFlightNumbers!=null){
				Object[] obj = {errFlightNumbers};
				ErrorVO err = new ErrorVO("mailtracking.defaults.err.flightintbcortba",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = TARGET;				
				return;
			}
		}
		log.log(Log.FINE, " out formErrors ", formErrors);
		if(formErrors.size()>0){
			invocationContext.addAllError(formErrors);
		}
		
		if(sameAP == 1){
			invocationContext.target = TARGET;
			return;
		}
		
		/**
		 * Added to check whether connection flights are continous
		 */
		
		if(routingVOs != null && routingVOs.size() > 1) {
			List<RoutingInConsignmentVO>	routingVOList = (List<RoutingInConsignmentVO>)routingVOs;
			int routingVOSize = routingVOList.size();
			log.log(Log.FINE, " routingVOList.size() = ", routingVOSize);
			for(int i=0;i<routingVOSize;i++) {
				int nextvo = (i+1);
				
				if(nextvo < routingVOSize){
					log.log(Log.FINE, "nextvo = ", nextvo);
					RoutingInConsignmentVO routingInConsignmentVOOne = (RoutingInConsignmentVO)routingVOList.get(i);
					RoutingInConsignmentVO routingInConsignmentVOTwo = (RoutingInConsignmentVO)routingVOList.get(i+1);
					if(routingInConsignmentVOOne.getOperationFlag()!=null && "D".equals(routingInConsignmentVOOne.getOperationFlag())){
						continue;
					}
					if(!routingInConsignmentVOOne.getPou().equals(routingInConsignmentVOTwo.getPol())){
						invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.mismatchinconnectionflights"));
						invocationContext.target = TARGET;
						return;
					}
				}
			}
		}
		
		
	}
	
	
	/**
	 * Method to create the filter vo for flight validation.
	 *
	 * @param routingVO the routing vo
	 * @param logonAttributes the logon attributes
	 * @return FlightFilterVO
	 */
	private FlightFilterVO handleFlightFilterVO(
			RoutingInConsignmentVO routingVO,
			LogonAttributes logonAttributes){
		
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		flightFilterVO.setStation(routingVO.getPol());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate(routingVO.getOnwardFlightDate());
		flightFilterVO.setCarrierCode(routingVO.getOnwardCarrierCode());
		flightFilterVO.setFlightNumber(routingVO.getOnwardFlightNumber());
		log.log(Log.FINE, " ****** flightFilterVO***** ", flightFilterVO);
		return flightFilterVO;
	}
	
}
