/*
 * PopulatePartyNameCommand.java  Created on Aug 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generatetransactioninvoice;

import com.ibsplc.icargo.business.shared.agent.vo.AgentFilterVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3353
 *
 */
public class PopulatePartyNameCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("List Invoice ULD");
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.generatetransactioninvoice";	
	private static final String GENERATE_SUCCESS = "generate_success";
    //private static final String GENERATE_FAILURE = "generate_failure";
	private static final String AIRLINE = "Airline";
	
	private static final String AGENT = "Agent";
	
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    	
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("PopulatePartyNameCommand","execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*GenerateTransactionInvoiceSession generateTransactionInvoiceSession =
    		(GenerateTransactionInvoiceSession)getScreenSession(MODULE,SCREENID); */   	
		GenerateTransactionInvoiceForm generateTransactionInvoiceForm = 
			(GenerateTransactionInvoiceForm)invocationContext.screenModel;
		
		
    	if(generateTransactionInvoiceForm.getInvoicedToCode()!=null && generateTransactionInvoiceForm.getInvoicedToCode().trim().length()>0){
    	
    		log.log(Log.INFO, "partytype flag value---->",
					generateTransactionInvoiceForm.getPartyTypeFlag());
			if(AIRLINE.equals(generateTransactionInvoiceForm.getPartyTypeFlag())||"".equals(generateTransactionInvoiceForm.getPartyTypeFlag()) ){
	    		AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
				airlineLovFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				airlineLovFilterVO.setAirlineCode(generateTransactionInvoiceForm.getInvoicedToCode().trim().toUpperCase());
				airlineLovFilterVO.setDisplayPage(1);	
				Page<AirlineLovVO> page=null;
				try{
				page= new AirlineDelegate().findAirlineLov(airlineLovFilterVO,1);
				
				} catch (BusinessDelegateException businessDelegateException) {	
					 handleDelegateException(businessDelegateException);
				}
				
				if(page!=null && page.size()>0){
					generateTransactionInvoiceForm.setName(page.get(0).getAirlineName());
					generateTransactionInvoiceForm.setInvoiceFlag("N");
				}else{
					generateTransactionInvoiceForm.setInvoiceFlag("Y");
					generateTransactionInvoiceForm.setName("");
				}
    		}else if(AGENT.equals(generateTransactionInvoiceForm.getPartyTypeFlag())){
    			AgentFilterVO agentFilterVO = new AgentFilterVO();
    			agentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    			agentFilterVO.setAgentCode(generateTransactionInvoiceForm.getInvoicedToCode().trim().toUpperCase());
    			agentFilterVO.setPageNumber(1);
    			Page<AgentVO> agentVOs = null;
    			try {
    				agentVOs=	new AgentDelegate().populateAgentLov(agentFilterVO);
    				

    			} catch (BusinessDelegateException businessDelegateException) {
    				handleDelegateException(businessDelegateException);
    			}
    			if(agentVOs!=null && agentVOs.size()>0){
    				generateTransactionInvoiceForm.setName(agentVOs.get(0).getAgentName());
    				generateTransactionInvoiceForm.setInvoiceFlag("N");
    			}else{
    				generateTransactionInvoiceForm.setInvoiceFlag("Y");
    				generateTransactionInvoiceForm.setName("");
    			}
    		}
    	}else{
    		generateTransactionInvoiceForm.setInvoiceFlag("Y");
    		generateTransactionInvoiceForm.setName("");
    	}
    	log.log(Log.INFO, "in populate InvoiceName Command :",
				generateTransactionInvoiceForm.getName());
		invocationContext.target = GENERATE_SUCCESS;
		
    }
}
