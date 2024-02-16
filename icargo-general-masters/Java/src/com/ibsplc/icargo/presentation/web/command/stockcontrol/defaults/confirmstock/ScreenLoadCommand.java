/*
 * ScreenLoadCommand.java Created on Feb 16, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4443
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
	    ConfirmStockSession session= getScreenSession( 
		    	"stockcontrol.defaults","stockcontrol.defaults.confirmstock");
			ConfirmStockForm confirmStockForm=(
					ConfirmStockForm)invocationContext.screenModel;
			//confirmStockForm.setStockHolderType(stockHolderType)
			session.setTransitStockVOs(null);	
			try {
				loadSessionWithPartnerAirlines(session);
			} catch (BusinessDelegateException e) {				
				log.log(Log.FINE,"BusinessDelegateException");
			} 
			HashMap<String,Collection<String>> documentList = null;
			try{
				 documentList =new HashMap<String,Collection<String>>(
						 new DocumentTypeDelegate().
						 findAllDocuments(
						getApplicationSession().getLogonVO().getCompanyCode()));
				//Commented by A-8445 as part of IASCB-67440
				//documentList.put("",null);
				}catch(BusinessDelegateException businessDelegateException){
					log.log(Log.FINE,"BusinessDelegateException");
				}
			session.setMap(documentList); //set in session
			confirmStockForm.setDocType(NULL_STRING);
			confirmStockForm.setDocSubType(NULL_STRING);
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
	 			log.log(Log.FINE,"BusinessDelegateException");
	 			
	 		}
			Map<String, Collection<OneTimeVO>>  oneTimes=
				getScreenLoadDetails(logonAttributes.getCompanyCode());
			if(oneTimes!=null){
				Collection<OneTimeVO> stockHolderTypes=oneTimes.get(
						"stockcontrol.defaults.stockholdertypes");
				Collection<OneTimeVO> status=oneTimes.get(
						"stockcontrol.defaults.confirmstockstatus");
				Collection<OneTimeVO> operation = oneTimes.get("stockcontrol.defaults.confirmstockoperation");
				populatePriorityStockHolders(
						stockHolderpriorityVos,stockHolderTypes,session);
				confirmStockForm.setStockHolderType("Headquarters");
				session.setStockHolderType(stockHolderTypes);
				
				session.setStatus(status);
				session.setStockHolderFor(stockHolderFor);
				session.setOperation(operation);
				}
			invocationContext.target = "screenload_success";
    	
    }
  
	private void loadSessionWithPartnerAirlines(ConfirmStockSession session) throws BusinessDelegateException {
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
		fieldValues.add("stockcontrol.defaults.confirmstockstatus");
		fieldValues.add("stockcontrol.defaults.confirmstockoperation");

		 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(
						logonAttributes.getCompanyCode(),fieldValues) ;

			}catch(BusinessDelegateException businessDelegateException){
				log.log(Log.FINE,"BusinessDelegateException");
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
            Collection<OneTimeVO> stockHolder,ConfirmStockSession session){
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
