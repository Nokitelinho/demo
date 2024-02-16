/*
 * PopulatePartyNameCommand.java  Created on Jan 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class PopulatePartyNameCommand extends BaseCommand {
	
	  /**
	 * Logger for PopulateParyNameCommand
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	
	/**
	 * target String if success
	 */
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDTransactionForm maintainULDTransactionForm =
    		(MaintainULDTransactionForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	if(maintainULDTransactionForm.getPartyCode()!=null && maintainULDTransactionForm.getPartyCode().trim().length()>0){
    		/*AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
			airlineLovFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			airlineLovFilterVO.setAirlineCode(maintainULDTransactionForm.getPartyCode().trim().toUpperCase());
			airlineLovFilterVO.setDisplayPage(1);*/	
			Page<AirlineVO> page=null;
			AirlineFilterVO filterVO=new AirlineFilterVO();
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setAirlineCode(maintainULDTransactionForm.getPartyCode().trim().toUpperCase());
			filterVO.setStatus("A");
			try{
				if(filterVO.getAirlineCode().length()<4){
					page= new AirlineDelegate().findAirlines(filterVO, 1);
				}
			} catch (BusinessDelegateException businessDelegateException) {	
				handleDelegateException(businessDelegateException);
			}    		
			
			if(page!=null && page.size()>0){
				maintainULDTransactionForm.setPartyName(page.get(0).getAirlineName());
				maintainULDTransactionForm.setErrorStatusFlag("N");
			}else{
				maintainULDTransactionForm.setErrorStatusFlag("Y");
	    		maintainULDTransactionForm.setPartyName("");
			}
    	}else{
    		maintainULDTransactionForm.setErrorStatusFlag("Y");
    		maintainULDTransactionForm.setPartyName("");
    	}
    	log.log(Log.INFO, "in populate PartyName Command",
				maintainULDTransactionForm.getPartyName());
		invocationContext.target = SCREENLOAD_SUCCESS;
		
    }
}
