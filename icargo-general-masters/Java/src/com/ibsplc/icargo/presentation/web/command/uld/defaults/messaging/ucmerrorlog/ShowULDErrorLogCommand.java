/*
 * ShowULDErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ShowULDErrorLogCommand screen
 * 
 * @author A-1862
 */

public class ShowULDErrorLogCommand extends BaseCommand {
    
	/**
	 * Logger for UCM Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID =
		"uld.defaults.ucmerrorlog";
	private static final String SCREENID_ULDERRORLOG =
		"uld.defaults.ulderrorlog";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String SCREENLOAD_FAILURE="screenload_failure";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Commented by Manaf for INT ULD510
		//String  compCode = logonAttributes.getCompanyCode();
		UCMErrorLogSession ucmErrorLogSession = 
			(UCMErrorLogSession)getScreenSession(MODULE,SCREENID);
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
		UCMErrorLogForm ucmErrorLogForm = 
			(UCMErrorLogForm) invocationContext.screenModel;
		log.log(Log.FINE, "ucmErrorLogForm.getRowindex()-------------------->",
				ucmErrorLogForm.getRowindex());
		if(("fromviewuldsbutton").equals(ucmErrorLogForm.getRowindex()))
	    {
	    	String[] checked = ucmErrorLogForm.getSelectedUCMErrorLog();
	    	log.log(Log.FINE, "checked[0]------------>", checked);
			ucmErrorLogForm.setRowindex(checked[0]);
	    }
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs=ucmErrorLogSession.getULDFlightMessageReconcileVOs();
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=uldFlightMessageReconcileVOs.get(Integer.parseInt(ucmErrorLogForm.getRowindex()));
		log.log(Log.FINE, "uldFlightMessageReconcileVO-------------------->",
				uldFlightMessageReconcileVO);
		FlightFilterMessageVO flightFilterMessageVO=new FlightFilterMessageVO();
		flightFilterMessageVO.setAirportCode(uldFlightMessageReconcileVO.getAirportCode());
		flightFilterMessageVO.setCompanyCode(uldFlightMessageReconcileVO.getCompanyCode());
		flightFilterMessageVO.setCarrierCode(uldFlightMessageReconcileVO.getCarrierCode());
		flightFilterMessageVO.setFlightCarrierId(uldFlightMessageReconcileVO.getFlightCarrierIdentifier());
		flightFilterMessageVO.setFlightDate(uldFlightMessageReconcileVO.getFlightDate());
		flightFilterMessageVO.setFlightNumber(uldFlightMessageReconcileVO.getFlightNumber());
		flightFilterMessageVO.setFlightSequenceNumber(uldFlightMessageReconcileVO.getFlightSequenceNumber());
		flightFilterMessageVO.setLegSerialNumber(uldFlightMessageReconcileVO.getLegSerialNumber());
		flightFilterMessageVO.setMessageType(uldFlightMessageReconcileVO.getMessageType());
		//flightFilterMessageVO.setPointOfUnloading(uldFlightMessageReconcileVO.getp);
		Collection<String> ucmnum=new ArrayList<String>();
		ucmnum.add(uldFlightMessageReconcileVO.getSequenceNumber());
				
		flightFilterMessageVO.setUcmSequenceNumbers(ucmnum);
log.log(Log.INFO, "uldFlightVO", uldFlightMessageReconcileVO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineValidationVO airlineValidationVO = null;
		if (uldFlightMessageReconcileVO.getCarrierCode() != null
				&& uldFlightMessageReconcileVO.getCarrierCode().trim().length() > 0) {
			try {
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(), uldFlightMessageReconcileVO.getCarrierCode()
								.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
			
				errors = new ArrayList<ErrorVO>();
				Object[] obj = { uldFlightMessageReconcileVO.getCarrierCode().toUpperCase() };
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.ulderrormsg.msg.err.invalidcarrier", obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				//uldErrorLogForm.setDupStat("");
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO flightValidationVO = new FlightValidationVO();

		/*
		 * get flight validation vos
		 */
		FlightFilterVO flightFilterVO = null;
		flightFilterVO = getFlightFilterVO(logonAttributes,
				uldFlightMessageReconcileVO, airlineValidationVO);
		flightValidationVOs = getFlightDetails(flightFilterVO);
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.ulderrormsg.msg.err.noflightdetails");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		} else if (flightValidationVOs != null
				&& flightValidationVOs.size() == 1) {
			log.log(Log.FINE,
					"\n\n\n********* No Duplicate flights**************");
			flightValidationVO = ((ArrayList<FlightValidationVO>) flightValidationVOs)
					.get(0);

		} /*else {
			log.log(Log.FINE, "\n\n\n*********Duplicate flights**************");
			log.log(Log.FINE,"flight number--------->"+uldErrorLogForm.getUlderrorlogAirport());
			duplicateFlightSession
					.setFlightValidationVOs((ArrayList<FlightValidationVO>) flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREENID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			uldErrorLogForm.setDupStat(FLAG_YES);
			invocationContext.target = DUPLICATE_SUCCESS;
			return;
		}*/		
		//newly added starts
		ArrayList<String> stationsFromRoute = new ArrayList<String>();
		if("OUT".equals(uldFlightMessageReconcileVO.getMessageType())){
		String[] stations = flightValidationVO.getFlightRoute().split(
				uldFlightMessageReconcileVO.getAirportCode().toUpperCase());
		
		if (stations.length > 1) {
			String[] selectedDestns = stations[1].split("-");

			for (int i = 0; i < selectedDestns.length; i++) {

				if (selectedDestns[i] != null
						&& selectedDestns[i].trim().length() > 0) {
					stationsFromRoute.add(selectedDestns[i]);
				}

			}

			log.log(Log.FINE,
					"\n\n\nStations to be set in combo------------->",
					stationsFromRoute);

		}
		
		}else{
			stationsFromRoute.add(uldFlightMessageReconcileVO.getAirportCode().toUpperCase());
		}
		
		uldErrorLogSession.setPouValues(stationsFromRoute);
		//newly added
		
		log.log(Log.FINE, "flightFilterMessageVO-------------------->",
				flightFilterMessageVO);
		//Added by A-7359 for ICRD-225848 starts here	
		if(!(("fromSendUCMInOut").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
				.getFlightFilterMessageVOSession() != null)){
		uldErrorLogSession.setForPic("fromucmerrorlog");
		uldErrorLogSession.setPageURL("fromucmerrorlog");
		}else{
			uldErrorLogSession.setPageURL("fromSendUCMInOut");
			uldErrorLogSession.setForPic("fromSendUCMInOut");
		}
		//Added by A-7359 for ICRD-225848 ends here
		uldErrorLogSession.setFlightFilterMessageVOSession(flightFilterMessageVO);
		
		ucmErrorLogForm.setRowindex("");
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
 
    /**
	 * 
	 * @param flightFilterVO
	 * @return
	 */
	private Collection<FlightValidationVO> getFlightDetails(
			FlightFilterVO flightFilterVO) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			flightValidationVOs = delegate.fetchFlightDetails(flightFilterVO);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return flightValidationVOs;
	}
	/**
	 * Methd to get the Flight FilterVO
	 * 
	 * @param companyCode
	 * @param stationCode
	 * @param form
	 * @param airlineValidationVO
	 * @return flightFilterVO
	 */
	private FlightFilterVO getFlightFilterVO(LogonAttributes logonAttributes ,
			ULDFlightMessageReconcileVO vo, AirlineValidationVO airlineValidationVO) {
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		//flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setStation(vo.getAirportCode());
		flightFilterVO.setFlightNumber(vo.getFlightNumber());
		flightFilterVO.setCarrierCode(vo.getCarrierCode());
		flightFilterVO.setFlightCarrierId(airlineValidationVO
				.getAirlineIdentifier());
		flightFilterVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
		log.log(Log.FINE, "getMsgType----------------->", vo.getMessageType());
		if("OUT".equals(vo.getMessageType())){
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		}else{
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		}

		
			flightFilterVO.setFlightDate(vo.getFlightDate());
		
		// flightFilterVO.setDirection(direction);

		log.log(Log.FINE, "Flight filter vo to server-------------->",
				flightFilterVO);
		log.exiting("ListCommand", "getFlightFilterVO");
		return flightFilterVO;
	}
}
