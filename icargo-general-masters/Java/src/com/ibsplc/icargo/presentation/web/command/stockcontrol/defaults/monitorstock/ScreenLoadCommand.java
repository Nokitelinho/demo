/*
 * ScreenLoadCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1952
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String AWB = "AWB";
	
	private static final String S = "S";	

	private static final String TRUE="true";
	private static final String FALSE="false";
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "execute");
		MonitorStockForm frm = (MonitorStockForm) invocationContext.screenModel;

		MonitorStockSession session =
			 getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		log.log(Log.FINE, "\n\n\n\n---------modevvv---->", frm.getMode());
		try {
			log.log(Log.FINE, "checking privilege");
			boolean hasDeleteStockPrivilege=SecurityAgent.getInstance()
			.checkBusinessPrivilege("stockcontrol.defaults.deletestock.delete");
			if(hasDeleteStockPrivilege){
				frm.setDeleteButtonPrivilege(TRUE);	
			}else{
				frm.setDeleteButtonPrivilege(FALSE);	
			}
		} catch (SystemException e1) {
			log.log(Log.SEVERE, "EXCeption in checking business privilege");
		}
		if(!"Y".equals(frm.getMode())){
			
			session.removeAllAttributes();
			  frm.setMode("N");//screen invoked from main menu
			
			  log.log(Log.FINE,
					"\n\n\n\n---------screen invoked from main menu---->", frm.getMode());
			frm.setDocType("");
			  frm.setSubType("");
			  
			  
		}
		
		try {
			loadSessionWithPartnerAirlines(session);
		} catch (BusinessDelegateException e) {			
//printStackTrrace()();
		}
		 
		Map<String, Collection<OneTimeVO>> map =
             handleScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
			if(map != null){
			
			session.setOneTimeRequestedBy(map.get("stockcontrol.defaults.stockholdertypes"));
			
			}
			HashMap<String,Collection<String>> documentList = null;
			try{
			documentList =new HashMap<String,Collection<String>>(new 
				 DocumentTypeDelegate().findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
			//documentList.put("",null);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}
		
		    
			session.setDynamicDocType(documentList);
			
        if("Y".equals(frm.getMode())){
			 
        	log
					.log(
							Log.FINE,
							"\n\n\n\n---------screen invoked from list stock menu vvv---->",
							frm.getMode());
			log.log(Log.FINE,"action from list screen ");
				
    
        	
        	String selected = frm.getSelected();
				 	session.setSelected(selected);
				 	log.log(Log.FINE, "\n\n\n\n---------selected---->",
							selected);
					frm.setMode("Y");
					frm.setListInt("Y");
					
		  }
        LogonAttributes logon = null;
		try {
			logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(Objects.nonNull(logon)){
			frm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
		}
        frm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        if("Y".equals(frm.getListInt())){
			    frm.setListInt("N");
			    frm.setDisableButn("Y");
				frm.setMode("Y");
				invocationContext.target ="screenload_MoniterList";
		 }else{

		 log.exiting("ScreenLoadCommand", "execute");
		invocationContext.target = "screenload_success";
		 }
	}


	private void loadSessionWithPartnerAirlines(MonitorStockSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1)); 
	}


	/**
		 * This method will be invoked at the time of screen load
		 *
		 * @param companyCode
		 * @return oneTimes
		 */
		public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			try {

				Collection<String> fieldTypes = new ArrayList<String>();
				fieldTypes.add("stockcontrol.defaults.stockholdertypes");

				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
						companyCode, fieldTypes);


			Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
			MonitorStockSession session =
							getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
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
		/**
		 * This method will be invoked at the time of screen load
		 *
		 * @param stockHolderpriorityVos
		 * @param stockHolder
		 * @param session
		 * @return
		 */
		private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
								Collection<OneTimeVO> stockHolder,MonitorStockSession session){
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
