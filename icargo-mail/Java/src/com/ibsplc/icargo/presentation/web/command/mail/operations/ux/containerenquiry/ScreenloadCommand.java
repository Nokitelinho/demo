/*
 * ScreenloadCommand.java Created on Sep 26, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ScreenloadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	26-Sep-2018	:	Draft
 */
public class ScreenloadCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("Mail Operations List Container ");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.ux.containerenquiry";

	private static final String FLTTYPE_ONETIME = "mailtracking.defaults.operationtype";
	private static final String FLTTYPE_ONETIME_SEARCHMODE = "mailtracking.defaults.containersearchmode";
	private static final String FLTTYPE_ONETIME_SUBCLASSGROUP = "mailtracking.defaults.subclassgroup";
	private static final String CONTENT_ID_ONETIME = "mail.operations.contentid";
	private static final String FLIGHT_STATUS = "flight.operation.flightstatus";
	private static final String FLIGHT_DCS_INFO ="operations.flthandling.dws.dcsreportingstatus";
	private static final String  WEIGHT_SCALE_AVAILABLE="mail.operation.weighscaleformailavailable";
	private static final String SYSPAR_ULDTOBARROW_ALLOW="mail.operations.allowuldasbarrow";
		
	@Override
	public void execute(ActionContext actionContext)
			throws BusinessDelegateException {
		log.entering("ScreenloadCommand", "execute");
		ArrayList results = new ArrayList();
		System.out.println("Inside screenload");
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		System.out.println("Inside screenload1");
		ListContainerModel listContainerModel = (ListContainerModel) actionContext
				.getScreenModel();
		System.out.println("Inside screenload2");
		ContainerFilter containerfilter = listContainerModel
				.getContainerFilter();
		System.out.println("Inside screenload3");
		if (logonAttributes != null) {
			containerfilter.setAirport(logonAttributes.getAirportCode());
			containerfilter.setCarrierCode(logonAttributes.getOwnAirlineCode());
		}
		Map<String, String> paramResults = null;
    	Collection<String> codes = new ArrayList<String>();
    	codes.add(SYSPAR_ULDTOBARROW_ALLOW);
    	//added by A-8672 as part of IASCB-50071
    	codes.add("mail.operations.defaultoperatingcarriers");
    	
    	  try {
	    		paramResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
	    	} catch(BusinessDelegateException businessDelegateException) {
	    		handleDelegateException(businessDelegateException);
	    	}
    	
    	  String allowuldasbarrow =paramResults.get(SYSPAR_ULDTOBARROW_ALLOW);
    	  //added by A-8672 as part of IASCB-50071
    	  String defaultOperatingReference = paramResults.get("mail.operations.defaultoperatingcarriers");
		System.out.println("Inside screenload4");
		containerfilter.setShowEmpty("Y");
		listContainerModel.setContainerFilter(containerfilter);
		HashMap<String, Collection<OneTimeVO>> onetimes = getOneTimeValues(logonAttributes);
		listContainerModel.setOneTimeValues(onetimes);
		 Map airportParameters = null; 
	     AreaDelegate areaDelegate =   new AreaDelegate();
	    	 try {
				airportParameters = areaDelegate.findAirportParametersByCode(logonAttributes.getCompanyCode(), logonAttributes.getAirportCode(), getAirportParameterCodes());
			} catch(BusinessDelegateException e) {
				e.getMessage();
			}//added by A-8353 for ICRD-274933
		if(airportParameters!=null &&airportParameters.size()>0 &&"Y".equals((String)airportParameters.get(WEIGHT_SCALE_AVAILABLE))){
			  listContainerModel.setWeightScaleAvailable(true);
			}
		else{
			  listContainerModel.setWeightScaleAvailable(false);
			}
		
		LocalDate fromDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		fromDate = fromDate.addDays(-2);
		LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		
		listContainerModel.setFromDate(fromDate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		listContainerModel.setToDate(toDate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		listContainerModel.setUldToBarrowAllow(allowuldasbarrow);
		if(!"X".equals(defaultOperatingReference)) {
		listContainerModel.setDefaultOperatingReference(defaultOperatingReference); 
		}
		results.add(listContainerModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListMailbagsCommand","execute");

	}

	private Collection<String> getOneTimeParameterTypes() {
		ArrayList<String> parameterTypes = new ArrayList();
		parameterTypes.add(FLTTYPE_ONETIME);
		parameterTypes.add(FLTTYPE_ONETIME_SEARCHMODE);
		parameterTypes.add(FLTTYPE_ONETIME_SUBCLASSGROUP);
		parameterTypes.add(CONTENT_ID_ONETIME);
		parameterTypes.add(FLIGHT_STATUS);
		parameterTypes.add(FLIGHT_DCS_INFO);
		return parameterTypes;
	}

	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(
			LogonAttributes logonAttributes) {
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map oneTimeValues = null;
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		return ((HashMap) oneTimeValues);
	}
	private Collection<String> getAirportParameterCodes(){
		 Collection<String> parameterTypes = new ArrayList();
		  parameterTypes.add(WEIGHT_SCALE_AVAILABLE);
		  return parameterTypes;
	}

}