package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListAcceptMailCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
		private static final String ULD_TYPE = "U";	
		private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
		private static final String ULD_WARNING="mail.operations.warning.uld";
		private static final String ULD_SYSPAR="uld.defaults.errortype.notinairport";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
	this.log.entering("ListAcceptMailCommand", "execute");
	ContainerDetailsVO newContainerVO = null;
	ContainerDetails containerDetails = null;
	ContainerDetailsVO containerDetailsVO=null;
	ContainerDetails newContainer=null;
		
	LogonAttributes logonAttributes = getLogonAttribute();
    List<ErrorVO> errors = new ArrayList<ErrorVO>();
	String cmpcod = logonAttributes.getCompanyCode();
	OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	Map<String, String> warningMap = outboundModel.getWarningMessagesStatus();
	 if ((warningMap == null) || (warningMap.isEmpty()))
	    {
	      warningMap = new HashMap<String, String>();
	      outboundModel.setWarningMessagesStatus(warningMap);
	    }
	Collection<MailbagVO> mailbagVOsInContainer = new ArrayList<MailbagVO>();
	MailAcceptance mailAcceptance = outboundModel.getMailAcceptance();
	MailAcceptanceVO mailAcceptanceVO = MailOutboundModelConverter.constructMailAcceptanceVO(mailAcceptance, logonAttributes);
   // ContainerDetailsVO selContainerDtlsVO = new ContainerDetailsVO();
    if(outboundModel.getNewContainerInfo() !=null ) {
    	 newContainer = outboundModel.getNewContainerInfo();
    	newContainerVO = MailOutboundModelConverter.constructContainerDetailsVO(newContainer,logonAttributes);
    }
		// Putting selected ContainerNo Details VO into session...
	String containerNum = newContainer.getContainerNumber();
	String containerType = newContainer.getType();
	if((containerNum == null || ("".equals(containerNum.trim()))) && ((containerType.equals("U")) || (!getAutomaticBarrowIdSysPar()))){
		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.containernum.empty"));
		
		//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
		containerDtlsVO.setContainerType(newContainer.getType());
		containerDtlsVO.setPou(newContainer.getPou());
		containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
		containerDtlsVO.setDestination(newContainer.getDestination());
		containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
		containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
		containerDtlsVO.setPaCode(newContainer.getPaCode());
		//mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
		
		//invocationContext.target = TARGET;
		return;
	}else if("F".equals(outboundModel.getFlightCarrierflag()) 
				&& (newContainer.getPou() == null || newContainer.getPou().trim().length() == 0)){ 
		actionContext.addError(new ErrorVO("mailtracking.defaults.pou.empty"));
		
		//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
		containerDtlsVO.setContainerType(newContainer.getType());
		containerDtlsVO.setPou(newContainer.getPou());
		containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
		containerDtlsVO.setDestination(newContainer.getDestination());
		containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
		containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
		containerDtlsVO.setPaCode(newContainer.getPaCode());
	
		return;
	}else if((newContainer.getDestination()==null 
				|| newContainer.getDestination().trim().length()==0)){
		actionContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));
		//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
		containerDtlsVO.setContainerType(newContainer.getType());
		containerDtlsVO.setPou(newContainer.getPou());
		containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
		containerDtlsVO.setDestination(newContainer.getDestination());
		containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
		containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
		containerDtlsVO.setPaCode(newContainer.getPaCode());
		return;

	}else if(("F".equals(outboundModel.getFlightCarrierflag())
			&& ("B".equals(newContainer.getType()))
			&& !(newContainer.getPou().equals(newContainer.getDestination())))){				
		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame")); 

				//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
		containerDtlsVO.setContainerType(newContainer.getType());
		containerDtlsVO.setPou(newContainer.getPou());
		containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
		containerDtlsVO.setDestination(newContainer.getDestination());
		containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
		containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
		containerDtlsVO.setPaCode(newContainer.getPaCode());
	return;				
	}else if(newContainer.getDestination()!=null&&     
			newContainer.getDestination().equals(logonAttributes.getAirportCode()))
	{
		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
		//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			//ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
			//containerDtlsVO.setContainerType(newContainer.getType());
			//containerDtlsVO.setPou(newContainer.getPou());
			//containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
			//containerDtlsVO.setDestination(newContainer.getDestination());
			//containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
			//containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
			//containerDtlsVO.setPaCode(newContainer.getPaCode());
		return;
	}else {
		// Validate Destination
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	String destination = newContainer.getDestination();  
    	int errorFlag=0;
    	if (destination != null && !"".equals(destination)) {        		
    		try {        			
    			airportValidationVO = areaDelegate.validateAirportCode(
    					logonAttributes.getCompanyCode(),destination.toUpperCase());
    		}catch (BusinessDelegateException businessDelegateException) {
    			errorFlag=1;
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0
    				&& errorFlag==1) {            			
    			Object[] obj = {destination.toUpperCase()};
    			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
				//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
	    		//	ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
	    		//	containerDtlsVO.setContainerType(newContainer.getType());
	    		//	containerDtlsVO.setPou(newContainer.getPou());
	    		//	containerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
	    		//	containerDtlsVO.setDestination(newContainer.getDestination());
	    		//	containerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
	    		//	containerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
	    		//	containerDtlsVO.setPaCode(newContainer.getPaCode());
				return;
    		}
    	}
		// Putting selected ContainerNo Details VO into session...
		int flag = 0;
		containerNum = newContainer.getContainerNumber().trim().toUpperCase();
		
	
		
		ContainerDetailsVO selContainerDtlsVO = new ContainerDetailsVO();
			ContainerDetails selectedContainer = new ContainerDetails();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		
		
		//int flag=0;
		if ((StringUtils.equals(containerType, "B")) && (StringUtils.isBlank(containerNum)))
	    {
	      String barrowNumber = new MailTrackingDefaultsDelegate().generateAutomaticBarrowId(cmpcod);
	      newContainer.setContainerNumber(barrowNumber);
	    }
		if(flag == 0){
			int nowarn = 0;
			//if(ULD_TYPE.equals(containerType)) { 
			
			selContainerDtlsVO.setOperationFlag("I");
				selectedContainer.setOperationFlag("I");
			selContainerDtlsVO.setCompanyCode(logonAttributes.getCompanyCode());
				selectedContainer.setCompanyCode(logonAttributes.getCompanyCode());
			selContainerDtlsVO.setPol(logonAttributes.getAirportCode());
				selectedContainer.setPol(logonAttributes.getAirportCode());
			selContainerDtlsVO.setPou(newContainer.getPou());
				selectedContainer.setPou(newContainer.getPou());
			selContainerDtlsVO.setDestination(newContainer.getDestination());
				selectedContainer.setDestination(newContainer.getDestination());
			selContainerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
				selectedContainer.setPaBuiltFlag(newContainer.getPaBuiltFlag());
			selContainerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
				selectedContainer.setContainerJnyId(newContainer.getContainerJnyId());
			selContainerDtlsVO.setPaCode(newContainer.getPaCode());
				selectedContainer.setPaCode(newContainer.getPaCode());
			selContainerDtlsVO.setContainerType(newContainer.getType());
				selectedContainer.setType(newContainer.getType());
			selContainerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
				selectedContainer.setContainerNumber(newContainer.getContainerNumber());
			selContainerDtlsVO.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
				selectedContainer.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
			selContainerDtlsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				selectedContainer.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			selContainerDtlsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
				selectedContainer.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			
			
			if(!"Y".equals(outboundModel.getWarningOverride())){
				
				
				ULDDelegate uldDelegate = new ULDDelegate();
				
				boolean isULDType = ULD_TYPE.equals(containerType);
				
				if(isULDType){	    	
					try {
						uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					
					if (errors != null && errors.size() > 0) {      
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invaliduldnumber",
								new Object[]{containerNum}));
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
				}	
				
				
				
				ContainerVO containerVO = new ContainerVO();
				/*if("Y".equals(mailAcceptanceForm.getOverrideUMSFlag())){
					containerVO.setOverrideUMSFlag(true);
					mailAcceptanceForm.setOverrideUMSFlag("");
				}*/
				containerVO.setCompanyCode(logonAttributes.getCompanyCode());
				containerVO.setAssignedPort(logonAttributes.getAirportCode());
				containerVO.setContainerNumber(newContainer.getContainerNumber());
				containerVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
				containerVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				containerVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				containerVO.setType(newContainer.getType());
				containerVO.setPou(newContainer.getPou());
				containerVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
				containerVO.setFinalDestination(newContainer.getDestination());
				if(mailAcceptanceVO.getFlightSequenceNumber() > 0) {
					containerVO.setAssignmentFlag("F");
				}
				containerVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
				List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
				Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();
				String canDiscardUldValidation = ContainerVO.FLAG_NO;
				try {
					//if(!mailAcceptanceForm.isCanDiscardUldValidation()){   /*added by A-8149 for ICRD-276070*/
					//mailAcceptanceForm.setWarningStatus("uldvalidation"); /*added by A-8149 for ICRD-276070*/
					containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
					
						
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
					if (errors != null && errors.size() > 0) {
						for(ErrorVO vo : errors) {
							log.log(Log.FINE,
									"vo.getErrorCode() ----------> ", vo.getErrorCode());
							if(existigWarningMap != null && existigWarningMap.size()>0) {
								canDiscardUldValidation=existigWarningMap.get(vo.getErrorCode());
					    	   }  
							if(canDiscardUldValidation == null){  
								canDiscardUldValidation = ContainerVO.FLAG_NO;	
					    	   }
							if(ContainerVO.FLAG_NO.equals(canDiscardUldValidation)) {
							if("mailtracking.defaults.warn.ulddoesnot.exists".equals(vo.getErrorCode())||
								"mailtracking.defaults.warn.uldisnotinairport".equals(vo.getErrorCode())||
								"mailtracking.defaults.warn.uldisnotinthesystem".equals(vo.getErrorCode())||
								"mailtracking.defaults.warn.uldisnotoperational".equals(vo.getErrorCode())||
										//"mail.defaults.error.uldisnotinthesystem".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotinthesystem".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotinthesystem".equals(vo.getErrorCode())||
										//"mail.defaults.error.uldisnotinairport".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotinairport".equals(vo.getErrorCode())||
										//"mail.defaults.error.uldislost".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldislost".equals(vo.getErrorCode())||
										//"mail.defaults.error.uldisnotoperational".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotoperational".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldisnotoperational".equals(vo.getErrorCode())||
								"mailtracking.defaults.uldalreadyassignedtoanotherClosedflight".equals(vo.getErrorCode())||
										//"mail.defaults.error.uldnotinairlinestock".equals(vo.getErrorCode())||
								"mail.defaults.warning.uldnotinairlinestock".equals(vo.getErrorCode())
								){
									warningMap.put(ULD_WARNING, "N");
									outboundModel.setWarningMessagesStatus(warningMap);
									ErrorVO warningError;
									if("mailtracking.defaults.uldalreadyassignedtoanotherClosedflight".equals(vo.getErrorCode()) && vo.getErrorData()!=null){
										 warningError = new ErrorVO(
												vo.getErrorCode(),
												new Object[] { vo.getErrorData()[0]});          
									}else{    
										 warningError = new ErrorVO(vo.getErrorCode());
									}
									warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
									warningErrors.add(warningError);
								} else if ("mailtracking.defaults.err.uldalreadyinuseatanotherport".equals(vo.getErrorCode())) {
						    	 warningErrors.add(vo); 
							}else{
							    warningErrors.add(vo); 
								}
							}else{
								selContainerDtlsVO = newContainerVO;
								selContainerDtlsVO.setContainerOperationFlag("I");
								outboundModel.setWarningMessagesStatus(null);
								nowarn = 0;
								break;
							}
							if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
								//assignContainerForm.setWarningCode(CON_REASSIGN_WARN_FLIGHT);
								Object[] obj = vo.getErrorData();
								ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
								log
										.log(
												Log.FINE,
												"ContainerAssignmentVO (Flight)------------> ",
												containerAssignmentVO);
								selContainerDtlsVO.setReassignFlag(true);
								selContainerDtlsVO.setCarrierId(containerAssignmentVO.getCarrierId());
								selContainerDtlsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());				
								selContainerDtlsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
								selContainerDtlsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
								selContainerDtlsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
								selContainerDtlsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
								selContainerDtlsVO.setFlightDate(containerAssignmentVO.getFlightDate());
								selContainerDtlsVO.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
									selectedContainer.setReassignFlag(true);
									selectedContainer.setCarrierId(containerAssignmentVO.getCarrierId());
									selectedContainer.setCarrierCode(containerAssignmentVO.getCarrierCode());				
									selectedContainer.setFlightNumber(containerAssignmentVO.getFlightNumber());
									selectedContainer.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
									selectedContainer.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
									selectedContainer.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
									//selectedContainer.setFlightDate(containerAssignmentVO.getFlightDate());
									selectedContainer.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
								break;
							}
							else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {
								//assignContainerForm.setWarningCode(CON_REASSIGN_WARN_DESTN);
								Object[] obj = vo.getErrorData();
								ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
								log
										.log(
												Log.FINE,
												"ContainerAssignmentVO (Destn)------------> ",
												containerAssignmentVO);
								selContainerDtlsVO.setReassignFlag(true);
								selContainerDtlsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
								selContainerDtlsVO.setCarrierId(containerAssignmentVO.getCarrierId());
								selContainerDtlsVO.setFlightNumber("-1");
								selContainerDtlsVO.setFlightSequenceNumber(-1);
								selContainerDtlsVO.setLegSerialNumber(-1);
								selContainerDtlsVO.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
									selectedContainer.setReassignFlag(true);
									selectedContainer.setCarrierCode(containerAssignmentVO.getCarrierCode());
									selectedContainer.setCarrierId(containerAssignmentVO.getCarrierId());
									selectedContainer.setFlightNumber("-1");
									selectedContainer.setFlightSequenceNumber(-1);
									selectedContainer.setLegSerialNumber(-1);
									selectedContainer.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
								break;
							}
							else{
								selContainerDtlsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
								selContainerDtlsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
								selContainerDtlsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
								selContainerDtlsVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());				
								selContainerDtlsVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
								selContainerDtlsVO.setFlightDate(mailAcceptanceVO.getFlightDate());
								
									selectedContainer.setCarrierId(mailAcceptanceVO.getCarrierId());
									selectedContainer.setFlightNumber(mailAcceptanceVO.getFlightNumber());
									selectedContainer.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
									selectedContainer.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());				
									selectedContainer.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
									//selectedContainer.setFlightDate(mailAcceptanceVO.getFlightDate());
							//	mailAcceptanceForm.setOverrideUMSFlag("Y");
								break;
							}
						}
						//mailAcceptanceSession.setContainerDetailsVO(selContainerDtlsVO);
						// actionContext.setAttribute("selectedContainer", selContainerDtlsVO);
						if(ContainerVO.FLAG_NO.equals(canDiscardUldValidation)) {
						actionContext.addAllError(warningErrors); 
						return;
						}
					}                        
				}
			}else{
			//	mailAcceptanceForm.setWarningOveride("");
				selContainerDtlsVO = newContainerVO;
				selContainerDtlsVO.setContainerOperationFlag("I");
				nowarn = 1;
			}
			//}
			
			if(nowarn == 0){
				log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----01------> ");
				selContainerDtlsVO.setOperationFlag("I");
				selContainerDtlsVO.setContainerOperationFlag("I");
				selContainerDtlsVO.setCompanyCode(logonAttributes.getCompanyCode());
				selContainerDtlsVO.setPol(logonAttributes.getAirportCode());
				selContainerDtlsVO.setPou(newContainer.getPou());
				selContainerDtlsVO.setDestination(newContainer.getDestination());
				selContainerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
				selContainerDtlsVO.setContainerJnyId(newContainer.getContainerJnyId());
				selContainerDtlsVO.setPaCode(newContainer.getPaCode());
				selContainerDtlsVO.setContainerType(newContainer.getType());
				selContainerDtlsVO.setContainerNumber(newContainer.getContainerNumber());
				selContainerDtlsVO.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
				selContainerDtlsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				selContainerDtlsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
				selContainerDtlsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				selContainerDtlsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				selContainerDtlsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
					selectedContainer.setOperationFlag("I");
					selectedContainer.setContainerOperationFlag("I");
					selectedContainer.setCompanyCode(logonAttributes.getCompanyCode());
					selectedContainer.setPol(logonAttributes.getAirportCode());
					selectedContainer.setPou(newContainer.getPou());
					selectedContainer.setDestination(newContainer.getDestination());
					selectedContainer.setPaBuiltFlag(newContainer.getPaBuiltFlag());
					selectedContainer.setContainerJnyId(newContainer.getContainerJnyId());
					selectedContainer.setPaCode(newContainer.getPaCode());
					selectedContainer.setType(newContainer.getType());
					selectedContainer.setContainerNumber(newContainer.getContainerNumber());
					selectedContainer.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
					selectedContainer.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					selectedContainer.setAssignedUser(logonAttributes.getUserId().toUpperCase());
					selectedContainer.setCarrierId(mailAcceptanceVO.getCarrierId());
					selectedContainer.setFlightNumber(mailAcceptanceVO.getFlightNumber());
					selectedContainer.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
			}
			
			Collection<ContainerDetailsVO> containerDtlsVOs = new ArrayList<ContainerDetailsVO>();
				outboundModel.setSelectedContainer(selectedContainer);
			actionContext.setAttribute("containerDetails", selContainerDtlsVO);
			actionContext.setAttribute("mailAcceptanceDetails", mailAcceptanceVO);
				
			containerDtlsVOs.add(selContainerDtlsVO);
			/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
				log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----1------> ");
				for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
					log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----2------> ");
					if("N".equals(containerDtlsVO.getContainerOperationFlag())){
						log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----3------> ");
						containerDtlsVOs.add(selContainerDtlsVO);
					}else{
						log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----4------> ");
						containerDtlsVOs.add(containerDtlsVO);
					}
				}
			}*/
			//mailAcceptanceSession.setContainerDetailsVOs(containerDtlsVOs); 
		}
		
		
		/*Collection<ContainerDetailsVO> finalConDtlsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> conDtlsVOs =mailAcceptanceVO.getContainerDetails();
		if(conDtlsVOs != null && conDtlsVOs.size() > 0){
			for(ContainerDetailsVO conDtlsVO:conDtlsVOs){
				if(!"N".equals(conDtlsVO.getContainerOperationFlag())){
					finalConDtlsVOs.add(conDtlsVO);
				}
			}
		}*/
		
		/*Collection<ContainerDetailsVO> contDetailVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> newContainerDetailsVOs = null;
		int n=0;
		if(selContainerDtlsVO.getMailDetails()==null	&& selContainerDtlsVO.getDesptachDetailsVOs()== null){
			
				contDetailVOs.add(selContainerDtlsVO);
				try {					
					newContainerDetailsVOs =new MailTrackingDefaultsDelegate().findMailbagsInContainer(contDetailVOs);
				} catch (BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);
				}
				if(newContainerDetailsVOs!=null && newContainerDetailsVOs.size()>0 &&
						newContainerDetailsVOs.iterator().next().getMailDetails().isEmpty() &&
						newContainerDetailsVOs.iterator().next().getMailDetails().size()==0){//added by A-7371 as part of ICRD-271301
			    	
			    }
				for( ContainerDetailsVO contnrDtlsVO :finalConDtlsVOs){
					for(ContainerDetailsVO newcontrDtlVO :newContainerDetailsVOs ){
						if(contnrDtlsVO.getContainerNumber().equals(newcontrDtlVO.getContainerNumber())){
							contnrDtlsVO.setMailDetails(newcontrDtlVO.getMailDetails());
							contnrDtlsVO.setDesptachDetailsVOs(newcontrDtlVO.getDesptachDetailsVOs());
							
							Collection<MailbagVO> mailbagVOs = contnrDtlsVO.getMailDetails();
							if (mailbagVOs != null && mailbagVOs.size() != 0) {
								for (MailbagVO mailbagVO: mailbagVOs) {
									String mailId = mailbagVO.getMailbagId();
									double displayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
									Measure strWt=new Measure(UnitConstants.WEIGHT,displayWt/10);
									mailbagVO.setStrWeight(strWt);//added by A-7371
									
			    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
			    					 * As a part of performance Upgrade
			    					 * START.
			    					 
			           		       	if(mailbagVO.getOperationalFlag()==null 
			           		       			|| !("I").equals(mailbagVO.getOperationalFlag())){
			           		       		mailbagVO.setDisplayLabel("Y");
			           		       	}      
			           		       	//END
								}
							}
		        			Collection<DespatchDetailsVO> despatchDetailsVOs = contnrDtlsVO.getDesptachDetailsVOs();
		        			if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
			       		       	for (DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs) {	       		       		
			       		       		
			    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
			    					 * As a part of performance Upgrade
			    					 * START.
			    					 
			           		       	if(despatchDetailsVO.getOperationalFlag()==null 
			           		       			|| !("I").equals(despatchDetailsVO.getOperationalFlag())){
			           		       		despatchDetailsVO.setDisplayLabel("Y");
			           		       	}      
			           		       	//END
			       		       	}        				
		        			}
							//mailAcceptanceSession.setContainerDetailsVO(contnrDtlsVO);
							   actionContext.setAttribute("selectedContainer", selContainerDtlsVO);
							   actionContext.setAttribute("mailAcceptanceDetails", mailAcceptanceVO);
							
							n++;
							break;
						}
					}
					if(n!=0){
						break;
					}
				}
				
			//}
		}*/
		
		//mailAcceptanceForm.setDisableFlag("");
	}
		 ResponseVO responseVO = new ResponseVO();
	     List<OutboundModel> results = new ArrayList();
	     results.add(outboundModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
	//mailAcceptanceForm.setScreenStatusFlag("detail");
//	invocationContext.target = TARGET;
	log.exiting("ListAcceptMailCommand","execute");
	}

	private boolean getAutomaticBarrowIdSysPar()
		    throws BusinessDelegateException
		  {
		    ArrayList<String> systemParameters = new ArrayList<>();
		    systemParameters.add("mail.operation.barrowautogenerationrequired");
		    Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
		      .findSystemParameterByCodes(systemParameters);
		    String barrowIdReqSysParValue = systemParameterMap.get("mail.operation.barrowautogenerationrequired");
		    return StringUtils.equals(barrowIdReqSysParValue, "Y");
		  }
}