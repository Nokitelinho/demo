/*
 * ScreenLoadCommand.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listawbstockhistory; 

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3184 & A-3155
 *
 */

public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK CONTROLLER");

	private static final String EMPTY_STRING = ""; 
 

	/**
	 * The execute method in BaseCommand
	 * @author A-3184 & A-3155
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */ 

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand","execute");
		
		ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm) invocationContext.screenModel;
 		
		ListStockRangeHistorySession session = (ListStockRangeHistorySession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listawbstockhistory");

		session.removeAllAttributes();

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		log
				.log(
						Log.FINE,
						"\n\n\n\n-------------getApplicationSession().getLogonVO().getOwnAirlineNumericCode()-------",
						logonAttributesVO.getOwnAirlineNumericCode());
		Map<String, Collection<OneTimeVO>> map = handleScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
		
		listAwbStockHistoryForm.setRangeFrom(EMPTY_STRING);
		listAwbStockHistoryForm.setRangeTo(EMPTY_STRING);
		listAwbStockHistoryForm.setStartDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		listAwbStockHistoryForm.setEndDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		listAwbStockHistoryForm.setAwb(EMPTY_STRING);
		listAwbStockHistoryForm.setAwp(logonAttributesVO.getOwnAirlineNumericCode());
		listAwbStockHistoryForm.setStockHolderCode("");
	/*	listAwbStockHistoryForm.setStockType(EMPTY_STRING);
		listAwbStockHistoryForm.setStockStatus(EMPTY_STRING);*/
		listAwbStockHistoryForm.setAccountNumber(EMPTY_STRING);
		LogonAttributes logon = null;
		try {
			logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(Objects.nonNull(logon)){
			listAwbStockHistoryForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
		}
		if(map != null){

			 session.setOneTimeStockStatus(map.get("stockcontrol.defaults.stockstatus"));
			 session.setOneTimeAwbType(map.get("stockcontrol.defaults.awbtype"));
			 session.setOneTimeStockUtilizationStatus(map.get("stockcontrol.defaults.stockutilizationstatus"));
			 }
		HashMap<String,Collection<String>> documentList = null;
		ArrayList<String> subDocuments=new ArrayList<String>();
		try{
		 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().findAllDocuments(logonAttributesVO.getCompanyCode()));
		 /* for(Collection<String> Strings:documentList.values()){
			 for(String string:Strings){
				 subDocuments.add(string); 
			 }
		 }*/
		 //documentList.put("",null);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
            log.log(Log.SEVERE, "Business Delegate Exception ");
		}
		//session.setDocumentTypes(subDocuments);
		session.setMap(documentList);
		log.exiting("ScreenLoadCommand","execute"); 
		invocationContext.target = "screenload_success";
		
	} 
	 
	 /**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @return oneTimes 
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.stockstatus");
			fieldTypes.add("stockcontrol.defaults.awbtype");
			fieldTypes.add("stockcontrol.defaults.stockutilizationstatus");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);

		Collection<OneTimeVO> stockstatus = oneTimes.get("stockcontrol.defaults.stockstatus");
		Collection<OneTimeVO> awbtype = oneTimes.get("stockcontrol.defaults.awbtype");
		Collection<OneTimeVO> stockutilizationstatus= oneTimes.get("stockcontrol.defaults.stockutilizationstatus");
		
		ListStockRangeHistorySession session = (ListStockRangeHistorySession)
		getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listawbstockhistory");
		log.log(Log.INFO, "onetime", awbtype);
		log.log(Log.INFO, "onetime", stockutilizationstatus);
		
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			log.log(Log.FINE,"\n\n<------INSIDE EXCEPTION------------>");
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	/**
	 * @author A-2881
	 * @param session
	 * @throws BusinessDelegateException
	 */
	private void loadSessionWithPartnerAirlines(ListStockRangeHistorySession session)
			throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(
				airlineLovFilterVO, 1));
	}
  }
