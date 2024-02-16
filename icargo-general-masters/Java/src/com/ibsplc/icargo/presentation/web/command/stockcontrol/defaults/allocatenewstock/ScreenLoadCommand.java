/*
 * ScreenLoadCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateNewStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 * This is the form class that represents the Screen Load Command
 * for Allocate New Stock
 */


public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String AWB = "AWB";

	private static final String S = "S";

	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {


		log.entering("ScreenloadCommand","execute");
		AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
 		AllocateNewStockSession session = (AllocateNewStockSession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");

		RangeVO rangeStock =new RangeVO();
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();

		rangeStock.setOperationFlag(OPERATION_FLAG_INSERT);
		rangeStock.setStartRange("");
		rangeStock.setEndRange("");
		rangeStock.setNumberOfDocuments(0);

		session.removeAllAttributes();
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
//printStackTrrace()();
		}

		Collection<String> stockControlFor=new ArrayList<String>();
		stockControlFor.add("ALL");
 		stockControlFor.add("HQPRIV");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		Map<String, Collection<OneTimeVO>> screenLoad =handleScreenLoadDetails(logonAttributesVO.getCompanyCode(),stockControlFor);

	    if(screenLoad != null){

				// session.setOneTimeStock(screenLoad.get("stockcontrol.defaults.stockholdertypes"));
		}


		Collection<RangeVO> rangeAllocVOs=new ArrayList<RangeVO>();
 		Collection<RangeVO> rangeAvailVOs=new ArrayList<RangeVO>();
 		rangeAllocVOs.add(rangeStock);
 		session.setAllocatedRangeVos(rangeAllocVOs);
 		session.setAvailableRangeVos(rangeAvailVOs);
		allocateNewStockForm.setDocType("");
		allocateNewStockForm.setSubType("");
		LogonAttributes logon = null;
		try {
			logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(Objects.nonNull(logon)){
			allocateNewStockForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
		}

		HashMap<String,Collection<String>> documentList = null;
		try{
		 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().findAllDocuments(logonAttributesVO.getCompanyCode()));
		 //documentList.put("",null);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}

		session.setDocumentTypes(documentList); //set in session
		log.exiting("ScreenLoadCommand","execute");
		invocationContext.target = "screenload_success";

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
				log.log(Log.SEVERE, "BusinessDelegateException caught!");
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
}