package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.OffloadContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	22-Mar-2019		:	Draft
 */
public class OffloadContainerCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("MAIL");
	private static final String OUTBOUND = "O";
	private static final String NOT_FLIGHT_ASSIGNED_ERR = "mailtracking.defaults.searchcontainer.msg.err.notFlightAssigned";
	private static final String DIFF_PORT_ERR = "mailtracking.defaults.searchcontainer.differentport";
	private static final String OFFLOAD_CON_ARR_ERR = "mailtracking.defaults.msg.err.offloadcontainersarrived";
	private static final String OFFLOAD_TRAN_CON_ERR = "mailtracking.defaults.searchcontainer.err.cannnotoffloadtransitcontainer";
	private static final String NOT_SAME_FLIGHT = "mailtracking.defaults.searchcontainer.msg.err.notassignedToSameFlight";
	private static final String NO_BAGS_ACP_ERR = "mailtracking.defaults.searchcontainer.msg.err.notBagsAccepted";
	private static final String FLIGHT_CLOSED_ERR = "mailtracking.defaults.err.flightclosed";
	
	public void execute(ActionContext actionContext) 
			throws BusinessDelegateException,CommandInvocationException {
		
		log.entering("OffloadContainerCommand","execute"); 
		ListContainerModel listContainerModel = 
				(ListContainerModel) actionContext.getScreenModel();
		Collection<ContainerDetails> selectedContainerData =
				listContainerModel.getSelectedContainerData();
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		Collection<ErrorVO> errors = null;
		ResponseVO responseVO = new ResponseVO();
		ArrayList<ListContainerModel> results = 
				new ArrayList<ListContainerModel>();
		
		// Validation to check whether any containers are destination assigned
		for (ContainerDetails selectedvo : selectedContainerData) {
	    	 if (null==selectedvo.getFlightNumber()||
	    			 ("-1").equals(selectedvo.getFlightNumber().trim())) {
				actionContext.addError(new ErrorVO(NOT_FLIGHT_ASSIGNED_ERR));			
				return;
	    	 }
	     }
		
		int errorPort = 0;
	     String contPort = "";
	     for (ContainerDetails selectedvo : selectedContainerData) {
	    	 if(!logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
	    		 errorPort = 1;
   				if("".equals(contPort)){
   					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
					                  .append(",")
					                  .append(selectedvo.getContainerNumber())
					                  .toString();	
	       			}
	    	 }
	     }
	     if(errorPort == 1){
	    	 actionContext.addError(new ErrorVO(DIFF_PORT_ERR,new Object[]{contPort}));
	   	   	 return;
	     }
	     
		// VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
		int errorArr = 0;
		String contArr = "";
		int errorAlreadyManifest = 0;
		String contAlreadyManifest = "";
		for (ContainerDetails selectedvo : selectedContainerData) {
			if (selectedvo.getContainerNumber() != null) {
				if ("Y".equals(selectedvo.getArrivedStatus())) {
					errorArr = 1;
					if ("".equals(contArr)) {
						contArr = selectedvo.getContainerNumber();
					} else {
						contArr = new StringBuilder(contArr).append(",").append(selectedvo.getContainerNumber())
								.toString();
					}
				}
				/*if (MailConstantsVO.BULK_TYPE.equals(selectedvo.getType())
						&& MailConstantsVO.FLAG_NO.equals(selectedvo.getTransitFlag())) {
					errorAlreadyManifest = 1;
					if ("".equals(contAlreadyManifest)) {
						contAlreadyManifest = selectedvo.getContainerNumber();
					} else {
						contAlreadyManifest =
								new StringBuilder(contAlreadyManifest).append(",")
									.append(selectedvo.getContainerNumber()).toString();
					}
				}*/
			}
		}
		
		if(errorArr == 1){
	   		actionContext.addError(new ErrorVO(
	   				OFFLOAD_CON_ARR_ERR, new Object[]{contArr}));
	        log.exiting("OffloadContainerCommand","execute");
			return;
	   	}
		if(errorAlreadyManifest == 1){
			actionContext.addError(new ErrorVO(
					OFFLOAD_TRAN_CON_ERR, new Object[]{contAlreadyManifest}));  	    		    	
	        log.exiting("OffloadContainerCommand","execute");
			return;		
       }
		
		String carriercode = "";
		String fltNo = "";
		String airport = "";
		long fltseqNo = 0;
		int legSerialNo = 0;
		int carrierid = 0;
		LocalDate fltDate = null;
		String containerNumber = "";
		String containerType = "";
		for (ContainerDetails selectedvo : selectedContainerData) {
			if (selectedvo.getCarrierCode() != null &&
					selectedvo.getCarrierCode().trim().length() > 0) {
				carriercode = selectedvo.getCarrierCode();
			}
			fltNo = selectedvo.getFlightNumber();
			fltseqNo = selectedvo.getFlightSequenceNumber();
			legSerialNo = selectedvo.getLegSerialNumber();
			carrierid = selectedvo.getCarrierId();
			fltDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false)
						.setDate(selectedvo.getFlightDate());
			airport = selectedvo.getAssignedPort();
			containerNumber = selectedvo.getContainerNumber();
			containerType = selectedvo.getType();
			break;
		}  
		
		// Validating whether asigned to different flight
        for (ContainerDetails selectedvo : selectedContainerData) {
        	if (!carriercode.equals(selectedvo.getCarrierCode())
        			|| !fltNo.equals(selectedvo.getFlightNumber())
        			|| fltseqNo != selectedvo.getFlightSequenceNumber()
        			|| legSerialNo != selectedvo.getLegSerialNumber()) {
        		
        		ErrorVO errorVO = new ErrorVO(NOT_SAME_FLIGHT);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);			
		        log.exiting("OffloadContainerCommand","execute");
				return;
        	}
        }
        
        // Validating whether any mailbags are accepted in the containers
        StringBuilder errorcode = new StringBuilder("");
        boolean isBagsPresent = true;
        for (ContainerDetails selectedvo : selectedContainerData) {
    		if ("N".equals(selectedvo.getAcceptanceFlag())) {
    			errorcode.append(selectedvo.getContainerNumber()).append(",");
        		isBagsPresent = false;
    		}        		
        }
        if (!isBagsPresent) {
        	Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
        	ErrorVO errorVO = new ErrorVO(NO_BAGS_ACP_ERR,obj);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			actionContext.addAllError((List<ErrorVO>) errors);			
	        log.exiting("OffloadContainerCommand","execute");
			return;
        }
        

        
//      Validating Flight to obtain the Flight Validation VO - START
        FlightFilterVO flightFilterVO = new FlightFilterVO();
  		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
  		flightFilterVO.setFlightNumber(fltNo);
  		flightFilterVO.setStation(airport);
  		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
  		flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
   		flightFilterVO.setFlightDate(fltDate);
   		flightFilterVO.setCarrierCode(carriercode);
 		flightFilterVO.setFlightCarrierId(carrierid); 		
 		Collection<FlightValidationVO> flightValidationVOs = null;
 		FlightValidationVO flightValidationVO = null;
 		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
 		try {
 			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
 				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
 		}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 		}
 		
 		
 		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
 		
 		if (flightValidationVOs != null) {
 			log
					.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
			if (flightValidationVOs.size() == 1 ) { 
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					flightValidationVO = validationVO;
 					break;
 				}
 			}
 			else if(flightValidationVOs.size() > 1) {
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					if (validationVO.getFlightSequenceNumber() == fltseqNo) {
 						flightValidationVO = validationVO;
 						break;
 					}				
 				}
 			}
 		}
 		
 		boolean isFlightClosed = false;
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()))
    		{
    		operationalFlightVO.setLegSerialNumber(legSerialNo);	
    		}	
    	else
    	{
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	}
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	try {	    		
    		isFlightClosed = 
    				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);   		        		
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
    	
    	if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
	        log.exiting("OffloadContainerCommand","execute");
			return;
		}
		log.log(Log.FINE,
				"OffloadContainer--->isFlightClosed----->",
				isFlightClosed);
		if(!isFlightClosed){
			Object[] obj = {new StringBuilder(carriercode).append("").append(fltNo).toString(),fltDate.toDisplayDateOnlyFormat()};				
			actionContext.addError(new ErrorVO(FLIGHT_CLOSED_ERR,obj));
 	        log.exiting("OffloadContainerCommand","execute");
 	   		return;
		}
		
		results.add(listContainerModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("OffloadContainerCommand","execute");
		
		
	}

}
