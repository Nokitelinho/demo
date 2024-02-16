/*
 * ReloadListCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
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
	private static final String SCREENID ="uld.defaults.ucmerrorlog";

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
		
		UCMErrorLogForm ucmErrorLogForm = 
			(UCMErrorLogForm) invocationContext.screenModel;
		ucmErrorLogForm.setDuplicateStatus(BLANK);	
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);		
		FlightValidationVO flightValidationVO = duplicateFlightSession
				.getFlightValidationVO();			
		log.log(Log.FINE, "Flight ValidationVo-------------------->",
				flightValidationVO);
		if(logonAttributes.isAirlineUser()){    		
    		ucmErrorLogForm.setUcmDisableStat("airline");
    	}
    	else{
    		ucmErrorLogForm.setUcmerrorlogAirport(logonAttributes.getAirportCode());
    		ucmErrorLogForm.setUcmDisableStat("GHA");
    	}
		
		
		//continuing list after duplicate
		
		UCMErrorLogSession ucmErrorLogSession = 
			(UCMErrorLogSession)getScreenSession(MODULE,SCREENID);
		ucmErrorLogSession.setULDFlightMessageReconcileVOs(null);
		
		
		
		HashMap indexMap = null;
		HashMap finalMap = null;
		if (ucmErrorLogSession.getIndexMap()!=null){
			indexMap = ucmErrorLogSession.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String toDisplayPage = ucmErrorLogForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		FlightFilterMessageVO flightFilterMessageVO=new FlightFilterMessageVO();
		flightFilterMessageVO.setCompanyCode(compCode);
		flightFilterMessageVO.setAirportCode(ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase());
		flightFilterMessageVO.setPageNumber(displayPage);
		flightFilterMessageVO.setAbsoluteIndex(nAbsoluteIndex);
		flightFilterMessageVO.setMessageType(ucmErrorLogForm.getMsgType());
		flightFilterMessageVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		flightFilterMessageVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		flightFilterMessageVO.setFlightNumber(flightValidationVO.getFlightNumber());
		flightFilterMessageVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
		flightFilterMessageVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		flightFilterMessageVO.setCarrierCode(ucmErrorLogForm.getCarrierCode().toUpperCase());
		
       //		 Added For CR Starts
		if((!("ALL").equals(ucmErrorLogForm.getMsgStatus()))){
	   flightFilterMessageVO.setMessageStatus(ucmErrorLogForm.getMsgStatus());
		}
	   if (ucmErrorLogForm.getFromDate()!=null   &&ucmErrorLogForm.getFromDate().trim().length() > 0) {
		   LocalDate frmDate = new LocalDate(ucmErrorLogForm.getFromDate().toUpperCase(),Location.ARP, false);
		   flightFilterMessageVO.setFromDate(frmDate);
	   }
	   if (ucmErrorLogForm.getToDate()!=null   &&ucmErrorLogForm.getToDate().trim().length() > 0) {
		   LocalDate toDate = new LocalDate(ucmErrorLogForm.getToDate().toUpperCase(),Location.ARP, false);
		   flightFilterMessageVO.setToDate(toDate);
	   }		   
     //	 Added For CR ends
	   
	   
		ucmErrorLogSession.setFlightFilterMessageVOSession(flightFilterMessageVO);
		
		log.log(Log.FINE, "flightFilterMessageVO in reload---> ",
				flightFilterMessageVO);
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			uldFlightMessageReconcileVOs= new ULDDefaultsDelegate().listUCMErrors(flightFilterMessageVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "uldFlightMessageReconcileVOs ---> ",
				uldFlightMessageReconcileVOs);
		if(uldFlightMessageReconcileVOs == null || uldFlightMessageReconcileVOs.getActualPageSize()==0) {
			ucmErrorLogForm.setDuplicateStatus("");
			
		    //ucmErrorLogForm.setScreenStatusFlag("SCREENLOAD");
		    ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    ucmErrorLogForm.setScreenStatusValue("SCREENLOAD");
		    invocationContext.addError(new ErrorVO(
                "uld.defaults.messaging.ucmerrorlog.noresults",null));
		    ucmErrorLogSession.setULDFlightMessageReconcileVOs(null);		   
			invocationContext.target = LIST_FAILURE;
			return;
		}
		finalMap = indexMap;
		if(uldFlightMessageReconcileVOs!= null){
		finalMap = buildIndexMap(indexMap, uldFlightMessageReconcileVOs);
		ucmErrorLogSession.setIndexMap(finalMap);
		}
		ucmErrorLogSession.setULDFlightMessageReconcileVOs(uldFlightMessageReconcileVOs);
		//ucmErrorLogForm.setScreenStatusFlag("LISTSUCCESS");
		ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		ucmErrorLogForm.setScreenStatusValue("LISTSUCCESS");
		invocationContext.target = LIST_SUCCESS;
	}

	private HashMap buildIndexMap( HashMap existingMap, Page page) {
		
		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
	
}
