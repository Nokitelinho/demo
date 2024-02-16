/*
 * ReloadListCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1862
 * 
 */
public class ReloadListCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE_NAME_FLIGHT = "flight.operation";

	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID ="uld.defaults.ulderrorlog";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UCM ERROR MSG", "RELOAD FLIGHT DETAILS COMMAND");
		
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		log.log(Log.FINE, "flight number--------->", uldErrorLogForm.getUlderrorlogAirport());
		if(logonAttributes.isAirlineUser()){    		
			uldErrorLogForm.setUldDisableStat("airline");
    	}
    	else{
    		uldErrorLogForm.setUlderrorlogAirport(logonAttributes.getAirportCode());
    		uldErrorLogForm.setUldDisableStat("GHA");
    	}
		
//		for finding whether GHA/airline
		
		String isGHA="";
		if(logonAttributes.isAirlineUser()){
			isGHA="N";
		}else
		{
			isGHA="Y";
		}
		AreaDelegate areaDelegate =new AreaDelegate();	
		AirportVO airportVO=null;
		Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
		try {
			airportVO =	areaDelegate.findAirportDetails(compCode,logonAttributes.getAirportCode());
		} catch (BusinessDelegateException e) {
		e.getMessage();
		excep = handleDelegateException(e);
		}
		String isAirlineGHA="";
		if(airportVO!=null)
		{
			if(airportVO.getUsedAirportVO() != null && airportVO.getUsedAirportVO().isUldGHAFlag()){
				isAirlineGHA="Y";
			}else{
				isAirlineGHA="N";
			}
		}			
		log.log(Log.FINE, "isGHA----------------->", isGHA);
		log.log(Log.FINE, "isAirlineGHA-------------------->", isAirlineGHA);
		uldErrorLogForm.setGha(isGHA);
		uldErrorLogForm.setAirlinegha(isAirlineGHA);
		
//		for finding whether GHA/airline
		
		
		uldErrorLogForm.setDupStat(BLANK);	
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);		
		FlightValidationVO flightValidationVO = duplicateFlightSession
				.getFlightValidationVO();			
		log.log(Log.FINE, "Flight ValidationVo-------------------->",
				flightValidationVO);
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID);
		uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);
		
		String toDisplayPage = uldErrorLogForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		FlightFilterMessageVO flightFilterMessageVO=new FlightFilterMessageVO();
		flightFilterMessageVO.setCompanyCode(compCode);
		flightFilterMessageVO.setAirportCode(uldErrorLogForm.getUlderrorlogAirport().toUpperCase());
		if(uldErrorLogForm.getUlderrorlogULDNo()!=null && uldErrorLogForm.getUlderrorlogULDNo().trim().length()>0){
			Collection<String> uldnum=new ArrayList<String>();
			uldnum.add(uldErrorLogForm.getUlderrorlogULDNo().toUpperCase());
			log.log(Log.FINE, "uldnum ---> ", uldnum.size());
			flightFilterMessageVO.setUldNumbers(uldnum);
		}else
		{
			flightFilterMessageVO.setUldNumbers(null);
		}
		flightFilterMessageVO.setPageNumber(displayPage);		
		flightFilterMessageVO.setMessageType(uldErrorLogForm.getMessageType());
		flightFilterMessageVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		flightFilterMessageVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		flightFilterMessageVO.setFlightNumber(flightValidationVO.getFlightNumber());
		flightFilterMessageVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
		flightFilterMessageVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		flightFilterMessageVO.setCarrierCode(uldErrorLogForm.getCarrierCode().toUpperCase());
		//flightFilterMessageVO
		uldErrorLogSession.setFlightFilterMessageVOSession(flightFilterMessageVO);
		log.log(Log.FINE, "flightFilterMessageVO ---> ", flightFilterMessageVO);
		Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			uldFlightMessageReconcileDetailsVOs= new ULDDefaultsDelegate().listUldErrors(flightFilterMessageVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "uldFlightMessageReconcileDetailsVOs ---> ",
				uldFlightMessageReconcileDetailsVOs);
		if(uldFlightMessageReconcileDetailsVOs == null || uldFlightMessageReconcileDetailsVOs.getActualPageSize()==0) {
			//uldErrorLogForm.setScreenStatusFlag("SCREENLOAD");
			uldErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			uldErrorLogForm.setScreenStatusValue("SCREENLOAD");
		    uldErrorLogForm.setDupStat("");
		    invocationContext.addError(new ErrorVO(
                "uld.defaults.messaging.ulderrorlog.noresults",null));
		    uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
		//uldErrorLogForm.setScreenStatusFlag("LISTSUCCESS");
		uldErrorLogForm.setScreenStatusValue("LISTSUCCESS");
		invocationContext.target = LIST_SUCCESS;
	}
	
}
