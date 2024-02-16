/*
 * SaveContainerCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class SaveContainerCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("SaveMailDamageCommand");
	private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
	private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	private static final String ULD_WARNING="mail.operations.warning.uld";
	 /**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("SaveContainerCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
        String showWarning=mailbagEnquiryModel.getShowWarning();
		ContainerDetails selectedContainer = null;
		ContainerVO selectedContainerVO = new ContainerVO();
		FlightValidation flightValidation = new FlightValidation();
		

		List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
		List<ErrorVO> errors = new ArrayList<ErrorVO>();		
		
		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getFlightValidation()!= null) {
			log.log(Log.FINE, "mailbagEnquiryModel.getFlightValidation() not null");
			
			flightValidation = mailbagEnquiryModel.getFlightValidation();
		}
		
		try {
			ULDDelegate uldDelegate = new ULDDelegate();
			selectedContainer = mailbagEnquiryModel.getSelectedContainer();
			if(selectedContainer.getType().equals("U"))
			uldDelegate.validateULD(logonAttributes.getCompanyCode(),selectedContainer.getContainerNumber());
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {      
			actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invaliduldnumber"));
				//ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
				//containerDtlsVO.setContainerType(newContainer.getType());
				//containerDtlsVO.setPou(newContainer.getPou());
				//containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
				//containerDtlsVO.setDestination(newContainer.getDestination());
				//containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
				//containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
				//containerDtlsVO.setPaCode(newContainer.getPaCode());
				return;
		}

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedContainer()!= null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedContainer() not null");
			selectedContainer = mailbagEnquiryModel.getSelectedContainer();
			ContainerVO containerVO = new ContainerVO();
			/*if("Y".equals(mailAcceptanceForm.getOverrideUMSFlag())){
				containerVO.setOverrideUMSFlag(true);
				mailAcceptanceForm.setOverrideUMSFlag("");
			}*/
			containerVO.setCompanyCode(logonAttributes.getCompanyCode());
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setContainerNumber(selectedContainer.getContainerNumber());
			containerVO.setFlightNumber(selectedContainer.getFlightNumber());
			containerVO.setFlightSequenceNumber(selectedContainer.getFlightSequenceNumber());
			containerVO.setCarrierId(selectedContainer.getCarrierId());
			containerVO.setCarrierCode(selectedContainer.getCarrierCode());
			containerVO.setType(selectedContainer.getType());
			containerVO.setPou(selectedContainer.getPou());
			containerVO.setLegSerialNumber(selectedContainer.getLegSerialNumber());
			containerVO.setFinalDestination(selectedContainer.getDestination());
			String pouforCheck= "";
			if(selectedContainer.getType().equals("B")) {
			if(selectedContainer.getPou()!=null && selectedContainer.getPou().trim().length()>0) {
				pouforCheck = selectedContainer.getPou();
			} else if(selectedContainer.getFinalDestination()!=null && selectedContainer.getFinalDestination().trim().length()>0) {
				pouforCheck =  selectedContainer.getFinalDestination();
			}
			} else {
				if(selectedContainer.getFinalDestination()!=null && selectedContainer.getFinalDestination().trim().length()>0) {
					pouforCheck =  selectedContainer.getFinalDestination();
				}
			}
			if(logonAttributes.getAirportCode().equals(pouforCheck)) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
			    return ;
			}
			if (containerVO.getFinalDestination() != null && !containerVO.getFinalDestination().isEmpty()) {
				int errorFlag = 0;
				try {
					new AreaDelegate().validateAirportCode(logonAttributes.getCompanyCode(), containerVO.getFinalDestination().toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errorFlag = 1;
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && !errors.isEmpty() && errorFlag == 1) {
					actionContext.addError(new ErrorVO("Invalid Destination"));
					return;
				}
			}
			if(selectedContainer.getFlightSequenceNumber() > 0) {
				containerVO.setAssignmentFlag("F");
			}
			containerVO.setPaBuiltFlag(selectedContainer.getPaBuiltFlag());
			if("Y".equals(showWarning)){
			try {
				//if(!mailAcceptanceForm.sisCanDiscardUldValidation()){   /*added by A-8149 for ICRD-276070*/
				//mailAcceptanceForm.setWarningStatus("uldvalidation"); /*added by A-8149 for ICRD-276070*/
				containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
				//}
					
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				if (errors != null && errors.size() > 0) {
					for(ErrorVO vo : errors) {
						log.log(Log.FINE,
								"vo.getErrorCode() ----------> ", vo.getErrorCode());
						if("mailtracking.defaults.warn.ulddoesnot.exists".equals(vo.getErrorCode())||
								"mailtracking.defaults.warn.uldisnotinairport".equals(vo.getErrorCode())||
							"mailtracking.defaults.warn.uldisnotoperational".equals(vo.getErrorCode())||
							"mail.defaults.warning.uldisnotinthesystem".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotinairport".equals(vo.getErrorCode())||
							"mail.defaults.warning.uldislost".equals(vo.getErrorCode())||
							"mail.defaults.warning.uldisnotoperational".equals(vo.getErrorCode())||
							"mail.defaults.warning.uldnotinairlinestock".equals(vo.getErrorCode())
							){
			    		  ErrorVO warningError = new ErrorVO(vo.getErrorCode());
			    		  warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			    		  warningErrors.add(warningError);
					     }else{
					    	 warningErrors.add(vo); 
					     }
					     
						
						
					}
					actionContext.addAllError(warningErrors); 
					return;
					
				}                        
			}
			}
			
			selectedContainerVO = MailOperationsModelConverter.constructContainerVO(selectedContainer, logonAttributes);
			
			Collection <ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
			selectedContainerVOs.add(selectedContainerVO);
	    	Collection<LockVO> locks = prepareLocksForSave(selectedContainerVOs,LockConstants.ACTION_ASSIGNCONTAINER);
	    		
				String assignedto = mailbagEnquiryModel.getAssignToFlight();
	        	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
				
    			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	        	if ("FLIGHT".equals(assignedto)) { 
	    			mailAcceptanceVO.setFlightCarrierCode(flightValidation.getCarrierCode());
					mailAcceptanceVO.setFlightNumber(flightValidation.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(flightValidation.getFlightSequenceNumber());
					mailAcceptanceVO.setCarrierId(flightValidation.getFlightCarrierId());
					mailAcceptanceVO.setLegSerialNumber(flightValidation.getLegSerialNumber());
					
					LocalDate localDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
					localDate.setDate(flightValidation.getApplicableDateAtRequestedAirport());
					
					
					mailAcceptanceVO.setFlightDate(localDate);
					Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
					for (ContainerVO vo : selectedContainerVOs) {//Modified for ICRD-103379
						ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
						mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
						mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
						mailAcceptanceVO.setPreassignNeeded(false);
						mailAcceptanceVO.setDestination(vo.getPou());

						containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerDetailsVO.setContainerNumber(vo.getContainerNumber());
						containerDetailsVO.setContainerType(vo.getType());
						if (vo.isReassignFlag()) {
							containerDetailsVO.setReassignFlag(true);
							containerDetailsVO.setCarrierId(vo.getCarrierId());
							containerDetailsVO.setFlightNumber(vo.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
							containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
							containerDetailsVO.setFlightDate(vo.getFlightDate());
							containerDetailsVO.setCarrierCode(vo.getCarrierCode());

						} else {
							containerDetailsVO.setCarrierId(flightValidation.getFlightCarrierId());
							containerDetailsVO.setFlightNumber(flightValidation.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(flightValidation.getFlightSequenceNumber());
							containerDetailsVO.setLegSerialNumber(flightValidation.getLegSerialNumber());
							containerDetailsVO.setFlightDate(localDate);
							containerDetailsVO.setCarrierCode(flightValidation.getCarrierCode());
						}						
						containerDetailsVO.setPol(vo.getAssignedPort());
						containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
						containerDetailsVO.setOwnAirlineCode(vo.getOwnAirlineCode());
						containerDetailsVO.setAcceptedFlag("Y");
						containerDetailsVO.setArrivedStatus("N");
						containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
						containerDetailsVO.setPou(vo.getPou());
						if("Y".equals(vo.getAcceptanceFlag())){
							containerDetailsVO.setOperationFlag("U");
							containerDetailsVO.setContainerOperationFlag("U");
						}
						else{								
							containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							containerDetailsVO.setOperationFlag("I");
							containerDetailsVO.setContainerOperationFlag("I");
						}
						containerDetailsVO.setWareHouse(vo.getWarehouseCode());
						containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
						containerDetailsVO.setDestination(vo.getFinalDestination());
						containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
						containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
						containers.add(containerDetailsVO);							

						vo.setCompanyCode(logonAttributes.getCompanyCode());
						vo.setCarrierCode(flightValidation.getCarrierCode());
						vo.setCarrierId(flightValidation.getFlightCarrierId());
						vo.setFlightNumber(flightValidation.getFlightNumber());
						vo.setFlightSequenceNumber(flightValidation.getFlightSequenceNumber());
						vo.setFlightDate(localDate);
						vo.setLegSerialNumber(flightValidation.getLegSerialNumber());

					}
					//Validate ULD Type- Aircraft Compatibility starts
					if(selectedContainerVO!=null && MailConstantsVO.ULD_TYPE.equals(selectedContainerVO.getType())){
						errors=validateULDIncomatibility(selectedContainerVO,flightValidation);
					
					if (errors != null && errors.size() > 0) {					 
						actionContext.addAllError(errors);
						return;
					} 
					}
					//validateULDIncomatibility
					 mailAcceptanceVO.setContainerDetails(containers);

					
	        	}else{
	        		AirlineValidationVO airlineValidationVO = null;
	        	    String carrierCode = mailbagEnquiryModel.getCarrierCode().trim().toUpperCase();
	            	if (carrierCode != null && !"".equals(carrierCode)) {
	            		try {
	            			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
	            					logonAttributes.getCompanyCode(),carrierCode);
	            		}catch (BusinessDelegateException businessDelegateException) {
	            			handleDelegateException(businessDelegateException);
	            		}
	            	}
	            	 mailAcceptanceVO.setFlightCarrierCode(airlineValidationVO.getAlphaCode());
					 mailAcceptanceVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					 mailAcceptanceVO.setFlightNumber("-1");
					 mailAcceptanceVO.setFlightSequenceNumber(-1);
					 mailAcceptanceVO.setLegSerialNumber(-1);
					 Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();

					 for (ContainerVO vo : selectedContainerVOs) {

						 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						 mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						 mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						 mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
						 mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
						 mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
						 mailAcceptanceVO.setPreassignNeeded(false);

						 containerDetailsVO.setCompanyCode(vo.getCompanyCode());
						 containerDetailsVO.setContainerNumber(vo.getContainerNumber());
						 containerDetailsVO.setContainerType(vo.getType());
							if (vo.isReassignFlag()) {
								containerDetailsVO.setReassignFlag(true);
								containerDetailsVO.setCarrierId(vo.getCarrierId());
								containerDetailsVO.setCarrierCode(vo.getCarrierCode());
								containerDetailsVO.setFlightNumber(vo.getFlightNumber());
								containerDetailsVO.setFlightDate(vo.getFlightDate());
								containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
								containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
								containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
							} else {
								containerDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
								containerDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
								containerDetailsVO.setFlightNumber("-1");
								containerDetailsVO.setFlightSequenceNumber(-1);
								containerDetailsVO.setLegSerialNumber(-1);
								containerDetailsVO.setSegmentSerialNumber(-1);
								containerDetailsVO.setFlightDate(null);
							}						
						 containerDetailsVO.setAcceptedFlag("Y");
						 containerDetailsVO.setArrivedStatus("N");
						 containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
						 if("Y".equals(vo.getAcceptanceFlag())){
							 containerDetailsVO.setOperationFlag("U");
							 containerDetailsVO.setContainerOperationFlag("U");
						 }
						 else{								
							 containerDetailsVO.setOperationFlag("I");
							 containerDetailsVO.setContainerOperationFlag("I");
							 containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						 }
						 containerDetailsVO.setPol(logonAttributes.getAirportCode());
						 containerDetailsVO.setPou(vo.getPou());
						 containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());		 	    			
						 containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
						 containerDetailsVO.setDestination(vo.getFinalDestination());
						 containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
						 containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
						 containerDetailsVO.setWareHouse(vo.getWarehouseCode());
						 containers.add(containerDetailsVO);
						 
	                	 vo.setCompanyCode(logonAttributes.getCompanyCode());
                	     vo.setCarrierCode(airlineValidationVO.getAlphaCode());
                	     vo.setCarrierId(airlineValidationVO.getAirlineIdentifier());
        				 vo.setFlightSequenceNumber(-1);
        				 vo.setSegmentSerialNumber(-1);
        				 vo.setLegSerialNumber(-1);
					 }
	                 mailAcceptanceVO.setContainerDetails(containers);

	        	}
				
				try {	
					new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
		    		
				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
	    	}	    	
		
		
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("SaveContainerCommand", "execute");

	}
	
private Collection<LockVO> prepareLocksForSave(
		Collection<ContainerVO> containerVOs, String lockAction) {
	log.log(Log.FINE, "\n prepareLocksForSave------->>", containerVOs);
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	Collection<LockVO> locks = new ArrayList<LockVO>();

	if (containerVOs != null && containerVOs.size() > 0) {
		for (ContainerVO conVO : containerVOs) {
			ULDLockVO lock = new ULDLockVO();
			lock.setAction(lockAction);
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
/**
 * validateULDIncomatibility
  *@author A-5526 for IASCB-34124 
 * @param selectedContainerVO
 * @param flightValidation
 * @param actionContext
 * @return 
 */
private List<ErrorVO> validateULDIncomatibility(ContainerVO selectedContainerVO, FlightValidation flightValidation) {

	Collection<String> parameterCodes = new ArrayList<String>();
	// ICRD-56719
	List<ErrorVO> errors = new ArrayList<ErrorVO>();
	parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);

	Map<String, String> systemParameters = null;
	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	try {
		systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
	} catch (BusinessDelegateException e) {
		log.log(Log.INFO, "caught BusinessDelegateException ");
		e.getMessageVO().getErrors();
	}

	ArrayList<String> uldTypeCodes = new ArrayList<String>();
	ArrayList<String> uldNumberCodes = new ArrayList<String>();

	
		if (selectedContainerVO.getContainerNumber() != null && selectedContainerVO.getContainerNumber().trim().length() > 0) {
			String uldType = selectedContainerVO.getContainerNumber().substring(0, 3);
			if (!uldTypeCodes.contains(uldType.toUpperCase())) {
				uldTypeCodes.add(uldType.toUpperCase());
			}
			uldNumberCodes.add(selectedContainerVO.getContainerNumber());
		}

		Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
		if (flightValidation != null) {
			Collection<String> aircraftTypes = new ArrayList<String>();
			aircraftTypes.add(flightValidation.getAircraftType());
			ULDPositionFilterVO filterVO = null;
			Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
					systemParameters);
			if (validatedUldTypeCodes != null && validatedUldTypeCodes.size() > 0) {
				for (String uldType : validatedUldTypeCodes) {
					filterVO = new ULDPositionFilterVO();
					filterVO.setAircraftTypes(aircraftTypes);
					filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					filterVO.setUldCode(uldType);
					filterVOs.add(filterVO);
				}
			}
		}
		if (filterVOs != null && filterVOs.size() > 0) {
			try {
				new ULDDelegate().findULDPosition(filterVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errs = handleDelegateException(businessDelegateException);
				for (ErrorVO error : errs) {
					log.log(Log.FINE, "Error code received -->>", error.getErrorCode());
					if (MailConstantsVO.ULD_INCOMPATIBLEAIRCRAFT.equals(error.getErrorCode())) {
						Object[] errorData = error.getErrorData();
						String errorDatum = null;
						if (errorData != null && errorData.length > 0) {
							errorDatum = (String) errorData[0];
						}

						errors.add(new ErrorVO(
								"mailtracking.defaults.mailacceptance.msg.err.uldincompatileforaircrafttype",
								new Object[] { errorDatum }));

						return errors;

					}
				}

			}
		}
		return errors;
	
}
 
 /**
  * @author A-5526 for IASCB-34124
  * validateAirCraftCompatibilityforUldTypes
  * @param uldTypeCodes
  * @param systemParameterMap
  * @return
  */
public Collection<String> validateAirCraftCompatibilityforUldTypes(Collection<String> uldTypeCodes,
		Map<String, String> systemParameterMap) {
	log.entering("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
	ArrayList<String> uldTypeCodesForValidation = null;
	if (systemParameterMap != null && systemParameterMap.size() > 0) {
		String configuredTypes = systemParameterMap.get(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
		if (configuredTypes != null && configuredTypes.length() > 0 && !"N".equals(configuredTypes)) {
			if ("*".equals(configuredTypes)) {
				for (String uldType : uldTypeCodes) {
					if (uldTypeCodesForValidation == null) {
						uldTypeCodesForValidation = new ArrayList<String>();
					}
					uldTypeCodesForValidation.add(uldType);
				}
			} else {
				List<String> configuredTypesList = Arrays.asList(configuredTypes.split(","));
				if (uldTypeCodes != null && uldTypeCodes.size() > 0) {
					for (String uldType : uldTypeCodes) {
						if (configuredTypesList.contains(uldType)) {
							if (uldTypeCodesForValidation == null) {
								uldTypeCodesForValidation = new ArrayList<String>();
							}
							uldTypeCodesForValidation.add(uldType);
						}
					}
				}
			}
		}
	}
	log.exiting("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
	return uldTypeCodesForValidation;
}
}
