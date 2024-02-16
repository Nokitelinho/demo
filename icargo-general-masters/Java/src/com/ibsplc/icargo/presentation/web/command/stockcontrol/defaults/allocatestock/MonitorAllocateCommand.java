/*
 * MonitorAllocateCommand.java Created on Sep 5, 2005
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

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

/**
 * @author A-1366
 *
 */
public class MonitorAllocateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String NULL_STRING="";
	 
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		    AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
			String chk = allocateStockForm.getStockHolderCode();
			MonitorStockSession monitorSession = getScreenSession("stockcontrol.defaults",
			"stockcontrol.defaults.monitorstock");
			monitorSession.setCloseStatus(allocateStockForm.getCloseStatus());
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
		
			
			session.setMode("N");
			session.setPageStockRequestVO(null);
			session.setRangeVO(null);
			
			String fromDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
			String toDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);	
			allocateStockForm.setLevel(NULL_STRING);
			Collection<String> stockControlFor=new ArrayList<String>();
			stockControlFor.add("ALL");
	 		stockControlFor.add("HQPRIV");
	 		Collection<String> stockHolderFor = null;
	 		try{
	 			stockHolderFor=new StockControlDefaultsDelegate().
	 			findStockHolderCodes(logonAttributes.getCompanyCode(),stockControlFor);
	 		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
	 			log.log(Log.FINE,"<---------------------------Inside Business Delegate Exception-------------------->");
	 		}
			Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(logonAttributes.getCompanyCode());
			HashMap<String,Collection<String>> documentList = null;
			try{
			 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().
					 findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
			documentList.put("",null);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}

			session.setMap(documentList); //set in session
		if(oneTimes!=null){
			Collection<OneTimeVO> stockHolderTypes=oneTimes.get("stockcontrol.defaults.stockholdertypes");
			Collection<OneTimeVO> status=oneTimes.get("stockcontrol.defaults.statusforapproval");
			session.setPrioritizedStockHolders(monitorSession.getPrioritizedStockHolders());
			session.setStatus(status);
			session.setStockHolderFor(stockHolderFor);
		}
		
		/**
		 * added now
		 */
		allocateStockForm.setDocSubType(selectedVO.getDocumentSubType());
		allocateStockForm.setDocType(monitorSession.getDocumentType());
		allocateStockForm.setFromDate(fromDateString);
		allocateStockForm.setStockHolderType(selectedVO.getStockHolderType());
		allocateStockForm.setToDate(toDateString);
		if("Y".equals(monitorSession.getManual())){
			allocateStockForm.setManual(true);
		}else{
			allocateStockForm.setManual(false);
		}
	
		allocateStockForm.setStockControlFor(monitorSession.getApproverCode());
		allocateStockForm.setStockHolderCode(chk);
		
		
			invocationContext.target = "list_success";
    	
    }
  
  
	/**
     * This method will be invoked at the time of screen load
     * @param companyCode
     * @return
     */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		try{
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("stockcontrol.defaults.stockholdertypes");
		fieldValues.add("stockcontrol.defaults.statusforapproval");
		

		 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),fieldValues) ;

			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
	        return oneTimes;
	    }
	
	
}
