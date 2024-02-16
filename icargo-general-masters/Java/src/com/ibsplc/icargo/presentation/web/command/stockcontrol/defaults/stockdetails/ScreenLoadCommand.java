/*
 * ScreenLoadCommand.java Created on May18, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockdetails;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String AWB = "AWB";

	private static final String S = "S";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("ScreenLoadCommand", "execute"); 
		StockDetailsForm frm=(StockDetailsForm)invocationContext.screenModel;
		StockDetailsSession session=getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.stockdetails");
		String companyCode = getApplicationSession().getLogonVO()
		.getCompanyCode();
		session.removeAllAttributes();
		
		HashMap<String, Collection<String>> documentList = null;
		  
		  frm.setDocType("");
		  frm.setSubType("");
		  frm.setFromDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		  frm.setToDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		Map<String, Collection<OneTimeVO>> map =
	             handleScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
				if(map != null){
				
				session.setStockHolderType(map.get("stockcontrol.defaults.stockholdertypes"));
				
				}
				
				try{
				documentList =new HashMap<String,Collection<String>>(new 
					 DocumentTypeDelegate().findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
				//documentList.put("",null);
				}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				}
			
			    
				session.setDynamicDocType(documentList);
				
				
				invocationContext.target="screenload_success";
				
	}
	
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.stockholdertypes");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);


		Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
		log.log(Log.INFO, "onetime stock holder", stockHolder);
		StockDetailsSession session =
						getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.stockdetails");
		Collection<StockHolderPriorityVO> stockHolderpriorityVos =
			 new StockControlDefaultsDelegate().findStockHolderTypes(companyCode);
		populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);
		log
				.log(
						Log.FINE,
						"\n\n.....................stockHolderpriorityVos...............> ",
						stockHolderpriorityVos);
		
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderTypes");
		}
		return oneTimes;
	}
	private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder,StockDetailsSession session){
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
