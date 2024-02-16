/*
 * MonitorAllocateNewCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateNewStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;

/**
 * @author A-1927
 *
 * This is the form class that represents the Screen Load Command
 * for Allocate New Stock
 */


public class MonitorAllocateNewCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {


		log.entering("MonitorAllocateNewCommand","execute");
		AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
 		AllocateNewStockSession session = (AllocateNewStockSession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");
 		String chk = allocateNewStockForm.getStockHolderCode();
 		MonitorStockSession monitorSession = getScreenSession("stockcontrol.defaults",
		"stockcontrol.defaults.monitorstock");
		Collection<MonitorStockVO> collmonitorVO = monitorSession
				.getCollectionMonitorStockVO();
		MonitorStockVO selectedVO = new MonitorStockVO();
		if (collmonitorVO != null) {
			for (MonitorStockVO monitorStockVO : collmonitorVO) {
				if (monitorStockVO.getStockHolderCode().equals(chk)) {
					selectedVO = monitorStockVO;
					break;
		
				} else {
					Collection<MonitorStockVO> childVO = monitorStockVO
							.getMonitorStock();
		
					for (MonitorStockVO stkVO : childVO) {
						if (stkVO.getStockHolderCode().equals(chk)) {
							selectedVO = stkVO;
							break;
		
						}
					}
				}
			}
		}

		RangeVO rangeStock =new RangeVO();
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();

		rangeStock.setStartRange("");
		rangeStock.setEndRange("");
		rangeStock.setNumberOfDocuments(0);

		session.removeAllAttributes();

		Collection<String> stockControlFor=new ArrayList<String>();
		stockControlFor.add("ALL");
 		stockControlFor.add("HQPRIV");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		Map<String, Collection<OneTimeVO>> screenLoad =handleScreenLoadDetails(logonAttributesVO.getCompanyCode(),stockControlFor);

	    
		Collection<RangeVO> rangeAllocVOs=new ArrayList<RangeVO>();
 		Collection<RangeVO> rangeAvailVOs=new ArrayList<RangeVO>();
 	
 		session.setAllocatedRangeVos(rangeAllocVOs);
 		session.setAvailableRangeVos(rangeAvailVOs);
 		allocateNewStockForm.setDocType(monitorSession.getDocumentType());
		allocateNewStockForm.setSubType(selectedVO.getDocumentSubType());
		allocateNewStockForm.setStockHolderCode(chk);
		allocateNewStockForm.setStockControlFor(monitorSession.getApproverCode());
		allocateNewStockForm.setStockHolderType(selectedVO.getStockHolderType());
		
		if("Y".equals(monitorSession.getManual())){
			allocateNewStockForm.setManual(true);
		}else{
			allocateNewStockForm.setManual(false);
		}
		HashMap<String,Collection<String>> documentList = null;
		try{
		 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().findAllDocuments(logonAttributesVO.getCompanyCode()));
		 //documentList.put("",null);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
			log.log(Log.FINE, "Error in loadSessionWithPartnerAirlines()");
		}

		session.setDocumentTypes(documentList); //set in session
		log.exiting("MonitorAllocateNewCommand","execute");
		invocationContext.target = "screenload_success";

	}

	/**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @param stockControlFor
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode,Collection<String> stockControlFor) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			try {

				Collection<String> fieldTypes = new ArrayList<String>();
				fieldTypes.add("stockcontrol.defaults.stockholdertypes");

				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldTypes);
				//Setting stock holders priority
				Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
				AllocateNewStockSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.allocatenewstock");
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
				Collection<StockHolderPriorityVO> stockHolderpriorityVos =stockControlDefaultsDelegate.findStockHolderTypes(companyCode);
				Collection<String> stockControlForColl =stockControlDefaultsDelegate.findStockHolderCodes(companyCode,stockControlFor);
				session.setStockControlFor(stockControlForColl);
				populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);


			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE,"BusinessDelegateException caught..........");
			}
			return oneTimes;
		}

		private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
				                    Collection<OneTimeVO> stockHolder,AllocateNewStockSession session){
				if(stockHolderpriorityVos!=null){
					for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
						for(OneTimeVO onetime : stockHolder){
							if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
								priorityVO.setStockHolderDescription(onetime.getFieldDescription());
							}
						}
					}
						session.setPrioritizedStockHolders(stockHolderpriorityVos);
			}

		}
		
		/**
		 * For #102543
		 * 
		 * @author A-2589
		 * @param session
		 * @throws BusinessDelegateException
		 * 
		 */
		private void loadSessionWithPartnerAirlines(AllocateNewStockSession session)
				throws BusinessDelegateException {
			AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
			airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
					.getCompanyCode());
			airlineLovFilterVO.setIsPartnerAirline("Y");
			session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(
					airlineLovFilterVO, 1));
		}
}