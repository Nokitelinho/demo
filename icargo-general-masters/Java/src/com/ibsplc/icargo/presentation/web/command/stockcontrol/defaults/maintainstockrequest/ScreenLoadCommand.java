/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
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
		    MaintainStockRequestForm maintainStockRequestForm = (MaintainStockRequestForm) invocationContext.screenModel;
		    /**
			 * Added by A-4772 for ICRD-9882.Changed the 
			 * Screen id value as per standard for UISKC002
			 */
		    MaintainStockRequestSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockrequest");
		    session.removeAllAttributes();
		    ApplicationSessionImpl applicationSession = getApplicationSession();
		    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		    Map<String, Collection<OneTimeVO>> screenLoad =
		    	handleScreenLoadDetails(logonAttributesVO.getCompanyCode());

		    if(screenLoad != null){

			 session.setOneTimeStatus(screenLoad.get("stockcontrol.defaults.status"));
			 session.setOneTimeStock(screenLoad.get("stockcontrol.defaults.stockholdertypes"));
			}
			Collection<OneTimeVO> statusColl=session.getOneTimeStatus();
			for(OneTimeVO status:statusColl){

				if("N".equals(status.getFieldValue())) {
					maintainStockRequestForm.setStatus(status.getFieldDescription());
				}
			}
		   /* LocalDate date = new LocalDate(getApplicationSession().getLogonVO().getStationCode(), false);
		    String dateString = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy");*/
 		    maintainStockRequestForm.setDateOfReq(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
			//docType and subType set to null part of icrd-4259 by A-5117
 		    maintainStockRequestForm.setDocType("");
			maintainStockRequestForm.setSubType("");
			maintainStockRequestForm.setLevel("");
			maintainStockRequestForm.setStockHolderType("");
			maintainStockRequestForm.setCode("");
			maintainStockRequestForm.setMode("I");
			LogonAttributes logon = null;
			try {
				logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			if(Objects.nonNull(logon)){
				maintainStockRequestForm.setPartnerPrefix(logon.getOwnAirlineNumericCode());
			}
			if("StockRequestListCreate".equals(maintainStockRequestForm.getFromStockRequestList())){
				//commented by A-5117 part of icrd-4259
				//uncommented by A-7364 as part of ICRD-320871
					maintainStockRequestForm.setReqRefNo("");
				
				//Added by A-5117 part of icrd-4259
					//commented by A-7364 as part of ICRD-320871
//					maintainStockRequestForm.getReqRefNo();
			}

			HashMap<String,Collection<String>> documentList = null;
			try{
				 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().findAllDocuments(logonAttributesVO.getCompanyCode()));
				 //commented part of icrd-4259 by A-5117
				// documentList.put("",null);
			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			}

			session.setDocumentTypes(documentList);
			
			try {
				loadSessionWithPartnerAirlines(session);
			} catch (BusinessDelegateException e) {				
//printStackTrrace()();
			}
			
			log.entering("ScreenLoadCommand","execute");
			invocationContext.target = "screenload_success";


    }
    
    /**
     * Added for #102543 base product enhancement
     * @author A-2589
     * @param session
     * @throws BusinessDelegateException
     * 
     */
    private void loadSessionWithPartnerAirlines(MaintainStockRequestSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		session.setPartnerAirlines(new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1)); 
	}

    /**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.status");
			fieldTypes.add("stockcontrol.defaults.stockholdertypes");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldTypes);
			//Setting stock holders priority
			Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
			/**
			 * Added by A-4772 for ICRD-9882.Changed the 
			 * Screen id value as per standard for UISKC002
			 */
			MaintainStockRequestSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.maintainstockrequest");
			Collection<StockHolderPriorityVO> stockHolderpriorityVos =new StockControlDefaultsDelegate().findStockHolderTypes(companyCode);
			populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);
			

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderTypes");
		}
		return oneTimes;
	}

	private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			                    Collection<OneTimeVO> stockHolder,MaintainStockRequestSession session){
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
				Collection<StockHolderPriorityVO> stockHolderVos=session.getPrioritizedStockHolders();
		
	}


}
