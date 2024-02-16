package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
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
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ValidateContainerForMarkingHbaCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	07-11-2022		:	Draft
 */
public class ValidateContainerForMarkingHbaCommand extends AbstractCommand{
	
	private static final Log LOGGER = LogFactory.getLogger("MAIL");
	private static final String OUTBOUND = "O";
	private static final String FLIGHT_CLOSED_ERR = "mailtracking.defaults.err.flightclosedhbamarking";
	private static final String CLASS_NAME = "ValidateContainerForMarkingHbaCommand";
	private static final String EXECUTE = "execute";
	public void execute(ActionContext actionContext) 
			throws BusinessDelegateException,CommandInvocationException {
		
		LOGGER.entering(CLASS_NAME,EXECUTE); 
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ContainerDetails selectedContainer = selectedContainerData.iterator().next();
	      if(isCarrier(selectedContainer)){
	        	setSuccessResponse(listContainerModel, actionContext);
	        	return;
	        }
		long fltseqNo = selectedContainer.getFlightSequenceNumber();
		int legSerialNo = selectedContainer.getLegSerialNumber();
		String carriercode = selectedContainer.getCarrierCode();
		String fltNo = selectedContainer.getFlightNumber();
		LocalDate fltDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false)
				.setDate(selectedContainer.getFlightDate());
        FlightFilterVO flightFilterVO = constructFlightFilterVO(selectedContainer, logonAttributes);	
 		Collection<FlightValidationVO> flightValidationVOs = null;
 		FlightValidationVO flightValidationVO = new FlightValidationVO();
 		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
 		try {
			flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
 		}catch (BusinessDelegateException businessDelegateException) {
 			handleDelegateException(businessDelegateException);
 		}
 		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			try {
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		}
 		
 		if (flightValidationVOs != null) {
			if (flightValidationVOs.size() == 1 ) { 
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					flightValidationVO = validationVO;
 				}
 			}
 			if(flightValidationVOs.size() > 1) {
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					if (validationVO.getFlightSequenceNumber() == fltseqNo) {
 						flightValidationVO = validationVO;
 						break;
 					}				
 				}
 			}
 		}
 		
 		boolean isFlightClosed = false;
    	OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(flightValidationVO);
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
			handleDelegateException(businessDelegateException);
		}
		if(isFlightClosed){
			Object[] obj = {new StringBuilder(carriercode).append("").append(fltNo).toString(),fltDate.toDisplayDateOnlyFormat()};				
			actionContext.addError(new ErrorVO(FLIGHT_CLOSED_ERR,obj));
			LOGGER.exiting(CLASS_NAME,EXECUTE); 
 	   		return;
		}
		setSuccessResponse(listContainerModel, actionContext) ;
	}

	private OperationalFlightVO constructOperationalFlightVO(FlightValidationVO flightValidationVO) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	return operationalFlightVO;
    	
	}

	private boolean isCarrier(ContainerDetails selectedContainerData) {
		return StringUtils.equals("-1",selectedContainerData.getFlightNumber());
	}

	private void setSuccessResponse(ListContainerModel listContainerModel, ActionContext actionContext) {
		ArrayList<ListContainerModel> results = 
				new ArrayList<>();
		ResponseVO responseVO = new ResponseVO();
		results.add(listContainerModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		LOGGER.exiting(CLASS_NAME,EXECUTE); 
	}

	private FlightFilterVO constructFlightFilterVO(ContainerDetails selectedContainerData, LogonAttributes logonAttributes ) {
		String carriercode = "";
		String fltNo = "";
		String airport = "";
		int carrierid = 0;
		LocalDate fltDate = null;
			if (selectedContainerData.getCarrierCode() != null &&
					selectedContainerData.getCarrierCode().trim().length() > 0) {
				carriercode = selectedContainerData.getCarrierCode();
			}
			fltNo = selectedContainerData.getFlightNumber();
			carrierid = selectedContainerData.getCarrierId();
			fltDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false)
						.setDate(selectedContainerData.getFlightDate());
			airport = selectedContainerData.getAssignedPort();
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
	 	return flightFilterVO;
	}

}
