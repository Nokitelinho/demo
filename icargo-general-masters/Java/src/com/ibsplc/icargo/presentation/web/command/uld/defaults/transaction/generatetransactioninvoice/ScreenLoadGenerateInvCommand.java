/*
 * ScreenLoadGenerateInvCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generatetransactioninvoice;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.GenerateTransactionInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1496
 *
 */
public class ScreenLoadGenerateInvCommand  extends BaseCommand {
	
	private static final String MODULE = "uld.defaults";
	
	private Log log = LogFactory.getLogger("ScreenLoadGenerateInvCommand");
	
	private static final String BLANK = "";
	
	private static final String SCREENID =
		"uld.defaults.generatetransactioninvoice";	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    private static final String AIRLINE = "A";
    
    private static final String AGENT = "G";
   
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	GenerateTransactionInvoiceSession generateTransactionInvoiceSession =
    		(GenerateTransactionInvoiceSession)getScreenSession(MODULE,SCREENID);
    	GenerateTransactionInvoiceForm generateTransactionInvoiceForm = (GenerateTransactionInvoiceForm)invocationContext.screenModel;
		LocalDate currentdate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		generateTransactionInvoiceForm.setInvoicedDate(
				TimeConvertor.toStringFormat(
						currentdate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs =
			loadTransactionVO(generateTransactionInvoiceForm,
					getApplicationSession().getLogonVO().getCompanyCode());
		generateTransactionInvoiceSession.setUldTransactionDetailsVO(
						uldTransactionDetailsVOs);	
		populateFormWithNewDetails(generateTransactionInvoiceForm,
						uldTransactionDetailsVOs.get(0));
		invocationContext.target = SCREENLOAD_SUCCESS;
    }
    
    private ArrayList<ULDTransactionDetailsVO> loadTransactionVO(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm,  String companyCode) {
    	ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs = 
			new ArrayList<ULDTransactionDetailsVO>();
		String[] uldNumbers = generateTransactionInvoiceForm.getUldNumbers().split(",");
		String[] txnRefNos = generateTransactionInvoiceForm.getTxnRefNos().split(",");
		String[] demAmts = generateTransactionInvoiceForm.getHiddenDmgAmt().split(",");
		String[] waiverAmts = generateTransactionInvoiceForm.getHiddenWaiver().split(",");
		String partNames = generateTransactionInvoiceForm.getHiddenToPtyName();
		log.log(Log.FINE, "HiddenToPartyCode()", generateTransactionInvoiceForm.getHiddenToPartyCode());
		String partyCodes=generateTransactionInvoiceForm.getHiddenToPartyCode();
		int uldNumbersSize = uldNumbers.length;    	
		for(int i = 0; i < uldNumbersSize; i++ ) {
			ULDTransactionDetailsVO uldTransactionDetailsVO =
								new ULDTransactionDetailsVO();			
			uldTransactionDetailsVO.setTransactionRefNumber(
						Integer.parseInt(txnRefNos[i]));
			if(demAmts[i] != null 
					&& demAmts[i].trim().length() > 0) {
				uldTransactionDetailsVO.setDemurrageAmount(
						Double.parseDouble(demAmts[i]));
			}
			if(waiverAmts[i] != null 
					&& waiverAmts[i].trim().length() > 0) {
				uldTransactionDetailsVO.setWaived(
						Double.parseDouble(waiverAmts[i]));
			}
			uldTransactionDetailsVO.setToPartyCode(partyCodes);
			uldTransactionDetailsVO.setUldNumber(uldNumbers[i]);
			uldTransactionDetailsVO.setCompanyCode(companyCode);
			uldTransactionDetailsVO.setToPartyName(partNames);
			uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
		}		
		return uldTransactionDetailsVOs;
    }
  
    private void populateFormWithNewDetails(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm, 
			ULDTransactionDetailsVO uldTransactionDetailsVO) {
    	log.log(Log.FINE, "The ULDTransactionDetailsVO --->",
				uldTransactionDetailsVO);
		log.log(Log.FINE,
				"The generateTransactionInvoiceForm.getPartyTypeFlag() --->",
				generateTransactionInvoiceForm.getPartyTypeFlag());
		if(uldTransactionDetailsVO.getToPartyName()!=null && uldTransactionDetailsVO.getToPartyName().trim().length()>0){
    		generateTransactionInvoiceForm.setName(uldTransactionDetailsVO.getToPartyName());
    	}else{
	    	if(uldTransactionDetailsVO.getToPartyCode()!=null 
	    			&& uldTransactionDetailsVO.getToPartyCode().trim().length()>0 
	    			&& AIRLINE.equals(generateTransactionInvoiceForm.getPartyTypeFlag())){
	    		AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
				airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
				airlineLovFilterVO.setAirlineCode(uldTransactionDetailsVO.getToPartyCode().trim().toUpperCase());
				airlineLovFilterVO.setDisplayPage(1);	
				Page<AirlineLovVO> page=null;
				try{
					
				page= new AirlineDelegate().findAirlineLov(airlineLovFilterVO,1);
				
				} catch (BusinessDelegateException businessDelegateException) {	
					 handleDelegateException(businessDelegateException);
				}
				
				if(page!=null && page.size()>0){
					
					generateTransactionInvoiceForm.setName(page.get(0).getAirlineName());				
				}else{		
					
					generateTransactionInvoiceForm.setName(BLANK);
				}
	    	}else if(uldTransactionDetailsVO.getToPartyCode()!=null 
	    			&& uldTransactionDetailsVO.getToPartyCode().trim().length()>0 
	    			&& AGENT.equals(generateTransactionInvoiceForm.getPartyTypeFlag())){
	    		AgentVO agentVO = null;
	    		try {
					agentVO = new AgentDelegate().findAgentDetails(
							getApplicationSession().getLogonVO().getCompanyCode(),
							uldTransactionDetailsVO.getToPartyCode().trim().toUpperCase());
				} catch (BusinessDelegateException exception) {
					log.log(Log.FINE,"*****in the exception");
					handleDelegateException(exception);
				}
				if(agentVO != null){
					generateTransactionInvoiceForm.setName(agentVO.getAgentName());
				}else{
					generateTransactionInvoiceForm.setName(BLANK);
				}
	    	}else{	
	    		generateTransactionInvoiceForm.setName(BLANK);
	    	}
    	}
    	generateTransactionInvoiceForm.setInvoicedToCode(uldTransactionDetailsVO.getToPartyCode());
    	String[] originalDemAmt = 
    		generateTransactionInvoiceForm.getHiddenDmgAmt().split(",");
    	String[] originalWaived = 
    		generateTransactionInvoiceForm.getHiddenWaiver().split(",");
		generateTransactionInvoiceForm.setUldNumber(
				uldTransactionDetailsVO.getUldNumber());
		generateTransactionInvoiceForm.setDemAmt(Double.toString(
				uldTransactionDetailsVO.getDemurrageAmount()));
		generateTransactionInvoiceForm.setWaiver(Double.toString(
				uldTransactionDetailsVO.getWaived()));
		generateTransactionInvoiceForm.setPrevIndex(0);			
		generateTransactionInvoiceForm.setOriginalDmgAmt(originalDemAmt[0]);
		generateTransactionInvoiceForm.setOriginalWaiver(originalWaived[0]);
	}

}
