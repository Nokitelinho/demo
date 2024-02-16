package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AddContainer;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.AddContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	25-Nov-2018		:	Draft
 */
public class AddContainerCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String INBOUND = "I";
	 private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	 private static final String ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND = "mail.operations.ULDaircraftcompatibilityinMailInbound";
	 private static final String ULD_SYSPAR_NOT_IN_ARP="uld.defaults.errortype.notinairport";
	 private static final String ULD_SYSPAR_NOT_IN_STOCK="uld.defaults.errortype.notinstock";
	 private static final String ULD_SYSPAR_NOT_IN_ARL_STOCK="uld.defaults.errortype.notinairlinestock";
	 private static final String FLIGHT_VALIDATION = "mail.operations.ignoretobeactionedflightvalidation";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS AddContainerCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("AddContainerCommand", "execute");

		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO =null;
		AddContainer addContainer =
				(AddContainer)mailinboundModel.getAddContainer();    
		ResponseVO responseVO = new ResponseVO();
		
		MailTrackingDefaultsDelegate defaultsDelegate =
				new MailTrackingDefaultsDelegate();
		ContainerAssignmentVO containerAssignmentVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ContainerVO> containersToSave=new ArrayList<ContainerVO>();
		String warningShow=mailinboundModel.getShowWarning();
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		
		String containerNum = addContainer.getContainerNo();
		String containerType = null;
		if(true==addContainer.isBarrow()){
			containerType = MailConstantsVO.BULK_TYPE;
		}
		else{
			containerType =MailConstantsVO.ULD_TYPE;
		}
		
		if (containerNum != null && containerType!=null) {

			try {
				containerAssignmentVO = defaultsDelegate.
						findLatestContainerAssignment(containerNum);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
		}
		
		Collection<String> code = new ArrayList<String>();
		code.add(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND);
		code.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
		
		//Validate ULD Type- Aircraft Compatibility starts
		
				Map<String, String> systemParameter = getSystemParameter(code);
				FlightValidationVO flightValidationVO = findFlightValidationVO(operationalFlightVO,logonAttributes);
				if(systemParameter.get(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND)!= null && MailConstantsVO.FLAG_YES.equals(systemParameter.get(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND))){
				if(addContainer!=null && MailConstantsVO.ULD_TYPE.equals(containerType)){
				String errorDatum=validateULDIncomatibility(addContainer,flightValidationVO,code);
				if(errorDatum!=null){
					actionContext.addError(new ErrorVO(
					"mailtracking.defaults.mailinbound.msg.err.uldincompatileforaircrafttype",
					new Object[] { errorDatum }));	
					return;        
				}
				
				}
			}
				//validateULDIncomatibility
				
			  if(flightValidationVO != null){ 
			  boolean isToBeActioned = flightValidationVO.isTBADueRouteChange()||FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus());
			  isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
			  if ((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
			  || FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))) {
			  Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),flightValidationVO.getFlightDate()};
			  ErrorVO errorVO = new ErrorVO(
			  "mailtracking.defaults.err.flightintbcortba",obj);
			  actionContext.addError(errorVO);
			  return;
					}
		List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
		ArrayList<ContainerDetails> containerDetailsCollection=
					mailinboundModel.getContainerDetailsCollection();
		if(containerDetailsCollection != null && !containerDetailsCollection.isEmpty()){
			for(ContainerDetails containerDetail : containerDetailsCollection){
				if(!MailConstantsVO.BULK_TYPE.equals(containerType) && containerDetail.getContainerno() != null && 
							containerDetail.getContainerno().trim().length() > 0 &&
									containerDetail.getPol() != null && containerDetail.getPol().trim().length() > 0){
					if(containerDetail.getContainerno().equals(containerNum) &&
							!(containerDetail.getPol().equals(addContainer.getPol()))){
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containerexistalready"));
						return;
					}
				}
			}
		}
		
		if(Objects.equals(addContainer.getPol(), mailinboundDetails.getPort())){
			actionContext.addError(new ErrorVO("mailtracking.defaults.err.invalidflightsegment"));
			return;
		}
		
		String route =mailinboundDetails.getFlightRoute();
		if("B".equals(containerType)){
			containerNum = new StringBuilder("BULK-").append(mailinboundDetails.getPort()).toString();
			addContainer.setContainerNo(containerNum);
		}
		
		if(containerNum == null || ("".equals(containerNum.trim()))){
			actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containernum.empty"));
	    }
		
		else if(containerAssignmentVO!=null &&
				containerType.equals(containerAssignmentVO.getContainerType()) &&
					MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())&&
						containerAssignmentVO.getFlightDate()!=null){
			if(Objects.equals(containerAssignmentVO.getAirportCode(), mailinboundDetails.getPort())
					|| (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getArrivalFlag()) &&
							!(mailinboundDetails.getPort().equals(containerAssignmentVO.getAirportCode())))){
				Object[] obj = {new StringBuilder(containerAssignmentVO.getCarrierCode())
						.append("").append(containerAssignmentVO.getFlightNumber()).append(" ").toString(),
				containerAssignmentVO.getFlightDate().toDisplayDateOnlyFormat()};
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containernum.isalreadyinanoutboundflight",obj));        
			return;
			}

		}
		
		else{
			int flag = 0;
	    	containerNum = containerNum.trim().toUpperCase();

	    	if("U".equals(containerType)){
	    		
	    		ULDDelegate uldDelegate = new ULDDelegate();
		    	FlightDetailsVO flightDetails = null;
				Collection<ULDInFlightVO> uldInFlightVos = new ArrayList<ULDInFlightVO>();
				ULDInFlightVO uldInFlightVo = new ULDInFlightVO();
				
				flightDetails = new FlightDetailsVO();
				flightDetails.setCompanyCode(logonAttributes.getCompanyCode());
				flightDetails.setCurrentAirport(mailinboundDetails.getPort());
				flightDetails.setDirection(MailConstantsVO.IMPORT);
				flightDetails.setFlightNumber(mailinboundDetails.getFlightNo());
				flightDetails.setCarrierCode(mailinboundDetails.getCarrierCode());
				flightDetails.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
				flightDetails.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				flightDetails.setFlightRoute(operationalFlightVO.getFlightRoute()); 
				
				uldInFlightVo.setUldNumber(containerNum);
				uldInFlightVo.setPointOfLading(addContainer.getPol());
				uldInFlightVo.setPointOfUnLading(mailinboundDetails.getPort());
				uldInFlightVo.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
				uldInFlightVos.add(uldInFlightVo);
				
				flightDetails.setUldInFlightVOs(uldInFlightVos);
				flightDetails.setAction(FlightDetailsVO.ARRIVAL);
				
				try {
					uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);

				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				
				if (errors != null && errors.size() > 0) {
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invaliduldnumber",
			   				new Object[]{containerNum}));
			  		return;
				}
				
				Map<String, String> results = null;
				Collection<String> codes = new ArrayList<String>();
				codes.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
				boolean isUldIntEnable = false;
				try {
					results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				if (results != null && results.size() > 0) {
					if (results.containsKey(MailConstantsVO.ULD_INTEGRATION_ENABLED)) {
						if ("Y".equals(warningShow)&&"Y".equals(results.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
							isUldIntEnable = true;
						}
					}
				}
				if (isUldIntEnable) {
					try {
						defaultsDelegate.validateULDsForOperation(flightDetails);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
						for(ErrorVO vo : errors) {
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
					    		  actionContext.addAllError(warningErrors); 
								  return;   
							}
					}
					}
					
				}
				if (errors != null && errors.size() > 0) {

					actionContext.addAllError((List<ErrorVO>) errors);
					return;
				}
				
	    	}
	    	
	    	
	    	
		}
		
		
		
		Collection<ContainerDetailsVO> containerDetails=
				new ArrayList<ContainerDetailsVO>();
		
		ContainerDetailsVO containerDetailsVO= new ContainerDetailsVO();
		MailArrivalVO mailArrivalVO=new MailArrivalVO();
		
		containerDetailsVO.setContainerNumber(containerNum);
		//containerDetailsVO.setContainerOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		containerDetailsVO.setContainerType(containerType);
		containerDetailsVO.setDestination(addContainer.getDesination());
		containerDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerDetailsVO.setCarrierId(operationalFlightVO.getCarrierId());
		containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
		containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		containerDetailsVO.setFlightStatus(mailinboundDetails.getStatus()!=null?
				Character.toString(mailinboundDetails.getStatus().charAt(0)):"O"); 
		containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_NO); 
		if(addContainer.isIntact()){
			containerDetailsVO.setIntact(MailConstantsVO.FLAG_YES);
		}
		else{
			containerDetailsVO.setIntact(MailConstantsVO.FLAG_NO);
		}
		containerDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		containerDetailsVO.setLastUpdateTime(
				new LocalDate(LocalDate.NO_STATION,
						Location.NONE,true));
		containerDetailsVO.setLocation(addContainer.getPol());
		if(addContainer.isPaBuilt()){
			containerDetailsVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
		}
		else{
			containerDetailsVO.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
		}
		containerDetailsVO.setPol(addContainer.getPol());
		containerDetailsVO.setPou(addContainer.getDesination());
		containerDetailsVO.setSegmentSerialNumber(operationalFlightVO.getSegSerNum());
		containerDetailsVO.setRoute(mailinboundDetails.getFlightRoute());
		containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		containerDetailsVO.setFlightDate((new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate(mailinboundDetails.getFlightDate().split(" ")[0])));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerDetailsVO.setUldLastUpdateTime(
				new LocalDate(LocalDate.NO_STATION,
						Location.NONE,true)); 
		
		mailArrivalVO.setAirportCode(mailinboundDetails.getPort());
		mailArrivalVO.setArrivalDate((new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate(mailinboundDetails.getFlightDate().split(" ")[0])));
		mailArrivalVO.setCarrierId(operationalFlightVO.getCarrierId());
		mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());  
		mailArrivalVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		mailArrivalVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		mailArrivalVO.setFlightStatus(mailinboundDetails.getStatus()!=null?
				Character.toString(mailinboundDetails.getStatus().charAt(0)):"O");
		mailArrivalVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		mailArrivalVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode()); 
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setOwnAirlineCode(operationalFlightVO.getCompanyCode());
		mailArrivalVO.setOwnAirlineId(operationalFlightVO.getCarrierId());
		
		
		containerDetails.add(containerDetailsVO);
		mailArrivalVO.setContainerDetails(containerDetails);
		
		
		try {
			defaultsDelegate.
					saveArrivalDetails(mailArrivalVO,null);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		
		
		
		if (errors != null && errors.size() > 0) {
			actionContext.addError(new ErrorVO("mail.operations.mailinbound.erroroccured"));
			return;
    	}
		
		else{
			ArrayList<MailinboundModel> mailinboundModelsCollection=
					new ArrayList<MailinboundModel>();
			mailinboundModelsCollection.add(mailinboundModel); 
			responseVO.setResults(mailinboundModelsCollection);
	        responseVO.setStatus("success");
	        actionContext.setResponseVO(responseVO);
	    	log.exiting("AddContainerCommand","execute");
		}
		
		}
	}
	
	/**
	 * @author A-5526 for IASCB-34124 
	 * findFlightValidationVO
	 * @param mailAcceptance
	 * @param logonAttributes
	 * @return
	 */
	private FlightValidationVO findFlightValidationVO(OperationalFlightVO operationalFlightVO, LogonAttributes logonAttributes) {
		FlightFilterVO flightFilterVO = null;

		FlightValidationVO flightValidationVO = new FlightValidationVO();
		Collection<ErrorVO> errors = new ArrayList();
		if (operationalFlightVO.getFlightNumber() != null && operationalFlightVO.getFlightNumber().trim().length() > 0) {
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				flightFilterVO = handleFlightFilterVO(operationalFlightVO, logonAttributes);

				//flightFilterVO.setCarrierCode(operationalFlightVO.getFlightCarrierCode());
				flightFilterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
				flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());

				flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (flightValidationVOs.size() == 1) {
				log.log(Log.FINE, "flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO, flightValidVO);
						// carditEnquirySession.setFlightValidationVO(flightValidationVO);
						// break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
			}
			return flightValidationVO;
		}
		return flightValidationVO;
	}
	
	/**
	 * @author A-5526 for IASCB-34124 
	 * handleFlightFilterVO
	 * @param mailAcceptance
	 * @param logonAttributes
	 * @return
	 */
	 private FlightFilterVO handleFlightFilterVO(
			 OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes) {

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		// Modified by A-7794 as part of ICRD-197439
		flightFilterVO.setStation(operationalFlightVO.getPou());
		flightFilterVO.setDirection(INBOUND);
		flightFilterVO.setIncludeACTandTBC(true);
		if (operationalFlightVO.getFlightDate() != null) {
			flightFilterVO.setStringFlightDate(operationalFlightVO.getFlightDate().toString().substring(0, 11));
			if (operationalFlightVO.getFlightDate() != null ) {
				flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
			}

		}
		// flightFilterVO.setFlightDate(date);
		return flightFilterVO;
	}
	
	/**
	 * validateULDIncomatibility
	  *@author A-5526 for IASCB-34124 
	 * @param addContainer
	 * @param flightValidationVO
	 * @param actionContext
	 */
	private String validateULDIncomatibility(AddContainer addContainer, FlightValidationVO flightValidationVO, Collection<String> code) {

//		Collection<String> parameterCodes = new ArrayList<String>();
//		// ICRD-56719
//
//		parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
//
//		Map<String, String> systemParameters = null;
//		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
//		try {
//			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
//		} catch (BusinessDelegateException e) {
//			log.log(Log.INFO, "caught BusinessDelegateException ");
//			e.getMessageVO().getErrors();
//		}
		
		Map<String, String> systemParameter = getSystemParameter(code);

		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();

		
			/*
			 * ULD type compatibility validation
			 */
			if (addContainer.getContainerNo()!= null && addContainer.getContainerNo().trim().length() > 0) {
				String uldType = addContainer.getContainerNo().substring(0, 3);
				if (!uldTypeCodes.contains(uldType.toUpperCase())) {
					uldTypeCodes.add(uldType.toUpperCase());
				}
				uldNumberCodes.add(addContainer.getContainerNo());
			}

			Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
			if (flightValidationVO != null) {
				Collection<String> aircraftTypes = new ArrayList<String>();
				aircraftTypes.add(flightValidationVO.getAircraftType());
				ULDPositionFilterVO filterVO = null;
				Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
						systemParameter);
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



							return errorDatum;

						}
					}

				}
			}
			return null;
		
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
	
	public Map<String, String> getSystemParameter(Collection<String> code) {

		Map<String, String> systemParameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(code);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO, "caught BusinessDelegateException");
			e.getMessageVO().getErrors();
		}
		return systemParameters;
	}
	private boolean canIgnoreToBeActionedCheck() {
		Collection<String> parameterCodes = new ArrayList<>();
		parameterCodes.add(FLIGHT_VALIDATION );
		Map<String, String> systemParameters = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.INFO,  e);
		}
		if(systemParameters!=null && systemParameters.containsKey(FLIGHT_VALIDATION )) {
			return "Y".equals(systemParameters.get(FLIGHT_VALIDATION ));
		}
		return false;
	}
}
