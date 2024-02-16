/*
 * ScreenLoadCommand.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listblacklistedstock;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1927
 *
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

		log.entering("ScreenLoadCommand","execute");
 		ListBlackListedStockForm listBlackListedStockForm = (ListBlackListedStockForm) invocationContext.screenModel;
 		ListBlackListedStockSession session = (ListBlackListedStockSession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listblacklistedstock");

		session.removeAllAttributes();
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
//printStackTrrace()();
		}

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
	    listBlackListedStockForm.setDocType("");
	    listBlackListedStockForm.setSubType("");
		listBlackListedStockForm.setRangeFrom("");
		listBlackListedStockForm.setRangeTo("");
		listBlackListedStockForm.setFromDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		listBlackListedStockForm.setToDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		LogonAttributes logon = null;
		try {
			logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(Objects.nonNull(logon)){
			listBlackListedStockForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
		}
		Page<BlacklistStockVO> blackListStockVOs=null;
		session.setBlacklistStockVOs(blackListStockVOs);
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
	private void loadSessionWithPartnerAirlines(ListBlackListedStockSession session)
			throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(
				airlineLovFilterVO, 1));
	}

	/** To populate hash map
	 *
	 * @param 
	 * @return HashMap<String,Collection<String>>
	 */
   	/*public HashMap<String,Collection<String>> populateHashMap(){

				 HashMap<String,Collection<String>> map = new  HashMap<String,Collection<String>>();
				 Collection<String> subTypea = new ArrayList<String>();
				 subTypea.add("M");
				 subTypea.add("S");

				 Collection<String> subTypeb = new ArrayList<String>();
				 subTypeb.add("M");
				 subTypeb.add("S");

				 map.put("AWB",subTypea);
				 map.put("HAWB",subTypeb);
				 map.put("",null);

				  return map;

}*/
}