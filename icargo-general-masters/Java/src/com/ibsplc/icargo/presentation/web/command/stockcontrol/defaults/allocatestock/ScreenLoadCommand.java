/*
 * ScreenLoadCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;

import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

/**
 * @author A-1366
 *
 */
public class ScreenLoadCommand extends BaseCommand {
    /**
     * log
     */
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * NULL_STRING
	 */
	private static final String NULL_STRING="";
	
	private static final String ZERO ="0";
	
	private static final String AWB = "AWB";
	
	private static final String S = "S";	
	
	 
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		    AllocateStockSession session= getScreenSession( 
		    	"stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(
					AllocateStockForm)invocationContext.screenModel;
			allocateStockForm.setStockHolderCode(NULL_STRING);
//			allocateStockForm.setStartRange(ZERO); Added by A-7764 for ICRD-243078
			session.setMode("N");
			session.setPageStockRequestVO(null);
			session.setRangeVO(null);
			LogonAttributes logon = null;
			try {
				logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			if(Objects.nonNull(logon)){
				allocateStockForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
			}
			
			try {
				loadSessionWithPartnerAirlines(session);
			} catch (BusinessDelegateException e) {				
//printStackTrrace()();
			}   
			
			allocateStockForm.setFromDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
			allocateStockForm.setToDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
			allocateStockForm.setManual(false);
			allocateStockForm.setLevel(NULL_STRING);
			Collection<String> stockControlFor=new ArrayList<String>();
			stockControlFor.add("ALL");
	 		stockControlFor.add("HQPRIV");
	 		Collection<String> stockHolderFor = null;
	 		Collection<StockHolderPriorityVO> stockHolderpriorityVos=null;
	 		try{
	 			stockHolderFor=new StockControlDefaultsDelegate().
	 			findStockHolderCodes(
	 					logonAttributes.getCompanyCode(),stockControlFor);
	 			stockHolderpriorityVos =new StockControlDefaultsDelegate().
	 			findStockHolderTypes(logonAttributes.getCompanyCode());
	 			log
						.log(
								Log.FINE,
								"\n\n\n\n-------stockHolderpriorityVos from server------>",
								stockHolderpriorityVos);
	 			
	 		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
	 			
	 		}
			Map<String, Collection<OneTimeVO>>  oneTimes=
				getScreenLoadDetails(logonAttributes.getCompanyCode());
			HashMap<String,Collection<String>> documentList = null;
			try{
			 documentList =new HashMap<String,Collection<String>>(
					 new DocumentTypeDelegate().
					 findAllDocuments(
					getApplicationSession().getLogonVO().getCompanyCode()));
			//documentList.put("",null);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
			allocateStockForm.setDocType("");
			allocateStockForm.setDocSubType("");
			session.setMap(documentList); //set in session
		if(oneTimes!=null){
			Collection<OneTimeVO> stockHolderTypes=oneTimes.get(
					"stockcontrol.defaults.stockholdertypes");
			Collection<OneTimeVO> status=oneTimes.get(
					"stockcontrol.defaults.statusforapproval");
			populatePriorityStockHolders(
					stockHolderpriorityVos,stockHolderTypes,session);
			
			session.setStatus(status);
			session.setStockHolderFor(stockHolderFor);
			}
			invocationContext.target = "screenload_success";
    	
    }
    
    /**
	 * For #102543
	 * @author A-2589
	 * @param session
	 * @throws BusinessDelegateException
	 * 
	 */
	private void loadSessionWithPartnerAirlines(AllocateStockSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1)); 
	}
  
    
    /**
     * This method will be invoked at the time of screen load
     * @param companyCode
     * @return
     */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		try{
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("stockcontrol.defaults.stockholdertypes");
		fieldValues.add("stockcontrol.defaults.statusforapproval");
		

		 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(
						logonAttributes.getCompanyCode(),fieldValues) ;

			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
	        return oneTimes;
	    }
	
	/**
	 * 
	 * @param stockHolderpriorityVos
	 * @param stockHolder
	 * @param session
	 */
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
            Collection<OneTimeVO> stockHolder,AllocateStockSession session){
		if(stockHolderpriorityVos!=null){
		for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
		for(OneTimeVO onetime : stockHolder){
			if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
				priorityVO.setStockHolderDescription(onetime.getFieldDescription());
			}
		}
		}
		log
				.log(
						Log.FINE,
						"\n\n\n\n-------stockHolderpriorityVos setting in session---->",
						stockHolderpriorityVos);
		session.setPrioritizedStockHolders(stockHolderpriorityVos);
		}

}
}
