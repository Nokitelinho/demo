/*
 * ChangeTxnTypeCommand.java Created on Aug 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ChangeTxnTypeCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
	
	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

    
    private static final String AIRLINE = "Ae";
	
	private static final String AGENT = "G";

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		
		
		
		
		// to update filter VO
		
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		String prtyCode="";
		AirlineValidationVO ownerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		log.log(Log.FINE,
				"<<--------listULDTransactionForm.getTxnType()------->>>",
				listULDTransactionForm.getTxnType());
		if(("L").equals(listULDTransactionForm.getTxnType())){
		prtyCode = listULDTransactionForm.getToPartyCode().toUpperCase();
	
    		if (listULDTransactionForm.getToPartyCode() != null && ! ("".equals(listULDTransactionForm.getToPartyCode()))) {
    		log.log(Log.FINE,"<<----------------AirlineDelegate---------------------->>>");
    		try {
				ownerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						listULDTransactionForm.getToPartyCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errors =  handleDelegateException(businessDelegateException);
   			}
    		}
		
		}
		if(("B").equals(listULDTransactionForm.getTxnType())){
		prtyCode = listULDTransactionForm.getFromPartyCode().toUpperCase();
		
		if (listULDTransactionForm.getFromPartyCode() != null && ! ("".equals(listULDTransactionForm.getFromPartyCode()))) {
    		log.log(Log.FINE,"<<----------------AirlineDelegate---------------------->>>");
    		try {
				ownerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						listULDTransactionForm.getFromPartyCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errors =  handleDelegateException(businessDelegateException);
   			}
    		}
		}
		if(("L").equals(listULDTransactionForm.getTxnType()) || ("B").equals(listULDTransactionForm.getTxnType())){
		log.log(Log.FINE, "listULDTransactionForm.getComboFlag()-------->>>",
				listULDTransactionForm.getComboFlag());
		if (AIRLINE.equals(listULDTransactionForm.getPartyType())) {
    		if (prtyCode != null && ! ("".equals(prtyCode))) {
    		log.log(Log.FINE,"<<----------------AirlineDelegate---------------------->>>");
    		try {
				ownerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						prtyCode.toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errors =  handleDelegateException(businessDelegateException);
   			}
    		}
		}
		if (AGENT.equals(listULDTransactionForm.getPartyType())) {
			if (prtyCode != null && ! ("".equals(prtyCode))) {
				log.log(Log.FINE,"<<----------------agentDelegate---------------------->>>");
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agentVO = agentDelegate.findAgentDetails(
						logonAttributes.getCompanyCode(),
						prtyCode.toUpperCase());
			} catch (BusinessDelegateException exception) {
				log.log(Log.FINE,"*****in the exception");
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			if(agentVO == null){
				ErrorVO errorVO = new ErrorVO("uld.defaults.generateinvoice.invalidinvoicedtocode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}

		}
		}}
		/*if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				 invocationContext.target =LIST_SUCCESS;
				 return;
		}*/
		TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
		transactionFilterVO = makeFilter(listULDTransactionForm, listULDTransactionSession,logonAttributes);
		log.log(Log.INFO,
				"ListULDTransactionCommand ~~~~~~transactionFilterVO~~~",
				transactionFilterVO);
		if(("airline").equals(listULDTransactionForm.getEnquiryDisableStatus()) && ("B").equals(listULDTransactionForm.getTxnType())){
			listULDTransactionForm.setToPartyCode(logonAttributes.getOwnAirlineCode());				
			transactionFilterVO.setToPartyCode(logonAttributes.getOwnAirlineCode());				
		}
		if(("airline").equals(listULDTransactionForm.getEnquiryDisableStatus()) && ("L").equals(listULDTransactionForm.getTxnType())){
			listULDTransactionForm.setFromPartyCode(logonAttributes.getOwnAirlineCode());				
			transactionFilterVO.setFromPartyCode(logonAttributes.getOwnAirlineCode());				
		}
	     
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    } 
    
    private TransactionFilterVO makeFilter(ListULDTransactionForm listULDTransactionForm,ListULDTransactionSession listULDTransactionSession,
    		LogonAttributes logonAttributes) {
    	log.entering("ListULDTransactionCommand","makeFilter");
    	
    	TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
    	
    	LocalDate txntodt = null;
    	LocalDate txnfromdt = null;
    	LocalDate retodt = null;
    	LocalDate refromdt = null;
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation()) && listULDTransactionForm.getTxnStation() != null){
    		txntodt = new LocalDate(listULDTransactionForm.getTxnStation().toUpperCase(),Location.ARP,false);
        	txnfromdt = new LocalDate(listULDTransactionForm.getTxnStation().toUpperCase(),Location.ARP,false);
    	}else{
    		txntodt = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
        	txnfromdt = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation()) && listULDTransactionForm.getReturnStation() != null){
    		retodt = new LocalDate(listULDTransactionForm.getReturnStation().toUpperCase(),Location.ARP,false);
        	refromdt = new LocalDate(listULDTransactionForm.getReturnStation().toUpperCase(),Location.ARP,false);
    	}else{
    		retodt = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
        	refromdt = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	}
    	
    	 transactionFilterVO.setCompanyCode(listULDTransactionSession.getCompanyCode());
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getUldNum()) && listULDTransactionForm.getUldNum() != null){
    	         transactionFilterVO.setUldNumber(listULDTransactionForm.getUldNum().trim().toUpperCase());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getUldTypeCode()) && listULDTransactionForm.getUldTypeCode() != null){
    			transactionFilterVO.setUldTypeCode(listULDTransactionForm.getUldTypeCode().trim().toUpperCase());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getAccessoryCode()) && listULDTransactionForm.getAccessoryCode() != null){
				transactionFilterVO.setAccessoryCode(listULDTransactionForm.getAccessoryCode().trim());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnType()) && listULDTransactionForm.getTxnType() != null){
    			transactionFilterVO.setTransactionType(listULDTransactionForm.getTxnType().trim());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStatus()) && listULDTransactionForm.getTxnStatus() != null){
    			transactionFilterVO.setTransactionStatus(listULDTransactionForm.getTxnStatus().trim());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getPartyType()) && listULDTransactionForm.getPartyType() != null){
    			transactionFilterVO.setPartyType(listULDTransactionForm.getPartyType().trim());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getFromPartyCode()) && listULDTransactionForm.getFromPartyCode() != null){
    			transactionFilterVO.setFromPartyCode(listULDTransactionForm.getFromPartyCode().trim().toUpperCase());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getToPartyCode()) && listULDTransactionForm.getToPartyCode() != null){
			transactionFilterVO.setToPartyCode(listULDTransactionForm.getToPartyCode().trim().toUpperCase());
	    }
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation()) && listULDTransactionForm.getTxnStation() != null){
    			transactionFilterVO.setTransactionStationCode(listULDTransactionForm.getTxnStation().trim().toUpperCase());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation()) && listULDTransactionForm.getReturnStation() != null){
    			transactionFilterVO.setReturnedStationCode(listULDTransactionForm.getReturnStation().trim().toUpperCase());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnFromDate()) && listULDTransactionForm.getTxnFromDate() != null){
    		    transactionFilterVO.setTxnFromDate(txntodt.setDate( listULDTransactionForm.getTxnFromDate()));
    			transactionFilterVO.setStrTxnFromDate(listULDTransactionForm.getTxnFromDate());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getTxnToDate()) && listULDTransactionForm.getTxnToDate() != null){
    	    	transactionFilterVO.setTxnToDate(txnfromdt.setDate(listULDTransactionForm.getTxnToDate()));
    			transactionFilterVO.setStrTxnToDate(listULDTransactionForm.getTxnToDate());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnFromDate()) && listULDTransactionForm.getReturnFromDate() != null){
    			transactionFilterVO.setReturnFromDate(retodt.setDate( listULDTransactionForm.getReturnFromDate()));
    			transactionFilterVO.setStrReturnFromDate(listULDTransactionForm.getReturnFromDate());
    	}
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getReturnToDate()) && listULDTransactionForm.getReturnToDate() != null){
    			transactionFilterVO.setReturnToDate(refromdt.setDate(listULDTransactionForm.getReturnToDate()));
    			transactionFilterVO.setStrReturnToDate(listULDTransactionForm.getReturnToDate());
    	}
    	log.exiting("ListULDTransactionCommand","makeFilter");
    	return transactionFilterVO;    	
    }
}
