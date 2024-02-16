/*
 * PopulateAirlineCommand.java Created on Jul 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generatetransactioninvoice;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2408
 *
 */
public class PopulateAirlineCommand  extends BaseCommand {
	
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    private static final String BLANK = "";
    
    private static final String AIRLINE = "A";
    
    private static final String AGENT = "G";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	GenerateTransactionInvoiceForm generateInvoiceForm = (GenerateTransactionInvoiceForm)invocationContext.screenModel;
    	if(generateInvoiceForm.getInvoicedToCode()!=null && generateInvoiceForm.getInvoicedToCode().trim().length()>0){
    		if(AIRLINE.equals(generateInvoiceForm.getPartyTypeFlag())){
	    		AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
				airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
				airlineLovFilterVO.setAirlineCode(generateInvoiceForm.getInvoicedToCode().trim().toUpperCase());
				airlineLovFilterVO.setDisplayPage(1);	
				Page<AirlineLovVO> page=null;
				try{
				page= new AirlineDelegate().findAirlineLov(airlineLovFilterVO,1);
				
				} catch (BusinessDelegateException businessDelegateException) {	
					 handleDelegateException(businessDelegateException);
				}
				
				if(page!=null && page.size()>0){
					generateInvoiceForm.setName(page.get(0).getAirlineName());				
				}else{				
					generateInvoiceForm.setName(BLANK);
				}
    		}else if(AGENT.equals(generateInvoiceForm.getPartyTypeFlag())){
    			AgentVO agentVO = null;
	    		try {
					agentVO = new AgentDelegate().findAgentDetails(
							getApplicationSession().getLogonVO().getCompanyCode(),
							generateInvoiceForm.getInvoicedToCode().toUpperCase());
				} catch (BusinessDelegateException exception) {
						handleDelegateException(exception);
				}
				if(agentVO != null){
					generateInvoiceForm.setName(agentVO.getAgentName());
				}else{
					generateInvoiceForm.setName(BLANK);
				}
    		}else{
    			generateInvoiceForm.setName(BLANK);
    		}
    	}else{    	
    		generateInvoiceForm.setName(BLANK);
    	}
		invocationContext.target = SCREENLOAD_SUCCESS;
    }
  
}
