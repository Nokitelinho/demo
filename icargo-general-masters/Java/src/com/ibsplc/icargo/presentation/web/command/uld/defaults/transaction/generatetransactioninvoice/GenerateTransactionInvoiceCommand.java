/*
 * GenerateTransactionInvoiceCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generatetransactioninvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.GenerateTransactionInvoiceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateTransactionInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1496
 *
 */
public class GenerateTransactionInvoiceCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("List Invoice ULD");
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.generatetransactioninvoice";	
	private static final String GENERATE_SUCCESS = "generate_success";
	private static final String GENERATE_FAILURE = "generate_failure";
	private static final String AIRLINE = "Airline";
	
	private static final String AGENT = "Agent";
	
	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
	
	
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/*
		 * Obtain the logonAttributes
		 */
    	log.entering("GenerateTransactionInvoiceCommand","execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		GenerateTransactionInvoiceSession generateTransactionInvoiceSession =
    		(GenerateTransactionInvoiceSession)getScreenSession(MODULE,SCREENID);    	
		GenerateTransactionInvoiceForm generateTransactionInvoiceForm = 
			(GenerateTransactionInvoiceForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = validateForm(generateTransactionInvoiceForm,
				logonAttributes.getCompanyCode());
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = GENERATE_FAILURE;
				return;
		}
		String prtyCode = generateTransactionInvoiceForm.getInvoicedToCode().toUpperCase();
		AirlineValidationVO ownerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		log.log(Log.FINE,
				"generateTransactionInvoiceForm.getPartyTypeFlag()-------->>>",
				generateTransactionInvoiceForm.getPartyTypeFlag());
		if (AIRLINE.equals(generateTransactionInvoiceForm.getPartyTypeFlag())) {
    		if (prtyCode != null && ! ("".equals(prtyCode))) {
    		log.log(Log.FINE,"<<----------------AirlineDelegate---------------------->>>");
    		try {
				ownerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						generateTransactionInvoiceForm.getInvoicedToCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errors =  handleDelegateException(businessDelegateException);
   			}
    		}
		}
		if (AGENT.equals(generateTransactionInvoiceForm.getPartyTypeFlag())) {
			if (prtyCode != null && ! ("".equals(prtyCode))) {
				log.log(Log.FINE,"<<----------------agentDelegate---------------------->>>");
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agentVO = agentDelegate.findAgentDetails(
						logonAttributes.getCompanyCode(),
						generateTransactionInvoiceForm.getInvoicedToCode().toUpperCase());
			} catch (BusinessDelegateException exception) {
				log.log(Log.FINE,"*****in the exception");
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			if(agentVO == null){
				ErrorVO errorVO = new ErrorVO("uld.defaults.generatetransactioninvoice.invalidinvoicedtocode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}

		}
		}
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = GENERATE_FAILURE;
				return;
		}
		ULDChargingInvoiceVO uldChargingInvoiceVO = new ULDChargingInvoiceVO();
		loadInvoiceVOFromForm(generateTransactionInvoiceForm,uldChargingInvoiceVO);
		ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs =
			generateTransactionInvoiceSession.getUldTransactionDetailsVO();
			//loadTransactionVO(generateTransactionInvoiceForm, logonAttributes.getCompanyCode());
		if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateTransactionInvoiceForm.getTxnType())) {
			uldChargingInvoiceVO.setRepairInvoice(true);
		}
		
		//added for getting last update time
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		TransactionListVO listTransactionVO = listULDTransactionSession.getTransactionListVO();
		Collection<ULDTransactionDetailsVO> transactionDetailsVOs = listTransactionVO.getTransactionDetailsPage();
    	log.log(Log.INFO, "\n%%%%%transactionDetailsVOs from page\n",
				transactionDetailsVOs);
		String invoiceId = "";
		try {
			for(ULDTransactionDetailsVO vo : transactionDetailsVOs){
				for(ULDTransactionDetailsVO uvo:uldTransactionDetailsVOs){
					log.log(Log.INFO, "\n getTransactionRefNumber", vo.getTransactionRefNumber());
					log.log(Log.INFO, "\n getTransactionRefNumber", uvo.getTransactionRefNumber());
					if(vo.getUldNumber().equals(uvo.getUldNumber()) && vo.getTransactionRefNumber() == uvo.getTransactionRefNumber()){
	    				log.log(Log.INFO, "\n LastUpdateTime", vo.getLastUpdateTime());
						log.log(Log.INFO, "\n LastUpdateTimeULD", vo.getUldNumber());
						uvo.setLastUpdateTime(vo.getLastUpdateTime());
						//Modified by A-7359 for ICRD-248560
						uvo.setOperationalAirlineIdentifier(vo.getOperationalAirlineIdentifier());
						uvo.setCurrency(vo.getCurrency());
	    			}
				}
		}
		log.log(Log.FINE, "\naftersettinglastupdatetime",
				uldTransactionDetailsVOs);
			invoiceId = new ULDDefaultsDelegate().generateInvoiceForReturnedUlds(
					uldChargingInvoiceVO,uldTransactionDetailsVOs) ;
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = GENERATE_FAILURE;
				return;
		}
		generateTransactionInvoiceForm.setInvoiceId(invoiceId);
		generateTransactionInvoiceForm.setStatusFlag("generate");
		invocationContext.target = GENERATE_SUCCESS;
    }
    
    private Collection<ULDTransactionDetailsVO> loadTransactionVO(
    			GenerateTransactionInvoiceForm generateTransactionInvoiceForm,  String companyCode) {
    	Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = 
			new ArrayList<ULDTransactionDetailsVO>();
    	String[] uldNumbers = generateTransactionInvoiceForm.getUldNumbers().split(",");
    	String[] txnRefNos = generateTransactionInvoiceForm.getTxnRefNos().split(",");
    	int uldNumbersSize = uldNumbers.length;    	
    	for(int i = 0; i < uldNumbersSize; i++ ) {
    		ULDTransactionDetailsVO uldTransactionDetailsVO =
    							new ULDTransactionDetailsVO();
    		if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateTransactionInvoiceForm.getTxnType())) { 
    			uldTransactionDetailsVO.setRepairSeqNumber(
        				Integer.parseInt(txnRefNos[i]));
    		}
    		else {
    			uldTransactionDetailsVO.setTransactionRefNumber(
        				Integer.parseInt(txnRefNos[i]));
    		}
    		
    		uldTransactionDetailsVO.setUldNumber(uldNumbers[i]);
    		uldTransactionDetailsVO.setCompanyCode(companyCode);
    		uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
    	}
		return uldTransactionDetailsVOs;
	}

	private void loadInvoiceVOFromForm(GenerateTransactionInvoiceForm generateTransactionInvoiceForm,
    		ULDChargingInvoiceVO uldChargingInvoiceVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	if((generateTransactionInvoiceForm.getInvoicedDate() != null &&
    			generateTransactionInvoiceForm.getInvoicedDate().trim().length() > 0)) {
			LocalDate invoiceDate = null;
			invoiceDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			invoiceDate.setDate(generateTransactionInvoiceForm.getInvoicedDate());
    		uldChargingInvoiceVO.setInvoicedDate(invoiceDate);
    		
    	}
    	
    	if((generateTransactionInvoiceForm.getInvoicedToCode() != null &&
				generateTransactionInvoiceForm.getInvoicedToCode().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoicedToCode(generateTransactionInvoiceForm.getInvoicedToCode().toUpperCase());
    		
    	}
    	
    	if((generateTransactionInvoiceForm.getName() != null &&
				generateTransactionInvoiceForm.getName().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoicedToCodeName(generateTransactionInvoiceForm.getName().toUpperCase());
    	
    	}
    	
    	if((generateTransactionInvoiceForm.getRemarks() != null &&
				generateTransactionInvoiceForm.getRemarks().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoiceRemark(generateTransactionInvoiceForm.getRemarks().toUpperCase());
    	
    	}
    	if((generateTransactionInvoiceForm.getTxnType() != null &&
				generateTransactionInvoiceForm.getTxnType().trim().length() > 0)) {
    		uldChargingInvoiceVO.setTransactionType(generateTransactionInvoiceForm.getTxnType().toUpperCase());
    	
    	}
    	//Added by a-3278 for AirNZ417 begins    	
    	if((generateTransactionInvoiceForm.getPartyTypeFlag() != null &&
				generateTransactionInvoiceForm.getPartyTypeFlag().trim().length() > 0)) {
    		uldChargingInvoiceVO.setPartyType(generateTransactionInvoiceForm.getPartyTypeFlag().toUpperCase());
    	
    	}
    	//Added by a-3278 for AirNZ417 ends
    	/*if((generateTransactionInvoiceForm.getDemAmt() != null &&
				generateTransactionInvoiceForm.getDemAmt().trim().length() > 0)) {
    		uldChargingInvoiceVO.setDemurrageAmount(Double.parseDouble(generateTransactionInvoiceForm.getDemAmt()));
    	
    	}*/
    	/*if((generateTransactionInvoiceForm.getWaiver() != null &&
				generateTransactionInvoiceForm.getWaiver().trim().length() > 0)) {
    		uldChargingInvoiceVO.set(Double.parseDouble(generateTransactionInvoiceForm.getWaiver()));
    	
    	}*/
    	uldChargingInvoiceVO.setLastUpdatedUser(logonAttributes.getUserId());
    	uldChargingInvoiceVO.setLastUpdatedTime(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true));
    	uldChargingInvoiceVO.setCompanyCode(logonAttributes.getCompanyCode());
		
    }

   /**
    * 
    * @param generateTransactionInvoiceForm
    * @param companyCode
    * @return
    */
	private Collection<ErrorVO> validateForm(
			GenerateTransactionInvoiceForm generateTransactionInvoiceForm, String companyCode){
		String txnDates[] = generateTransactionInvoiceForm.getTxndates().split(",");
		log.entering("GenerateTransactionInvoiceCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(generateTransactionInvoiceForm.getInvoicedDate()== null || 
				generateTransactionInvoiceForm.getInvoicedDate().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generatetransactioninvoice.invoicedatemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			
			
			if(!DateUtilities.isValidDate(generateTransactionInvoiceForm.getInvoicedDate(),"dd-MMM-yyyy")) {
				
				error = new ErrorVO("uld.defaults.generatetransactioninvoice.invaliddate",
						new Object[]{generateTransactionInvoiceForm.getInvoicedDate().toUpperCase()});
				errors.add(error);
			}
			else {
				for(int i=0;i< txnDates.length;i++) {
					if(DateUtilities.isValidDate(txnDates[i],"dd-MMM-yyyy")) {
						LocalDate txndate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						txndate.setDate(txnDates[i]);
						LocalDate transactionDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						transactionDate.setDate(generateTransactionInvoiceForm.getInvoicedDate());
						if(txndate.isGreaterThan(transactionDate)) {
							if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateTransactionInvoiceForm.getTxnType())) {
								error = new ErrorVO("uld.defaults.generatetransactioninvoice.invoicedategreaterthanrepairdate");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break;
							}
							else {
								error = new ErrorVO("uld.defaults.generatetransactioninvoice.invoicedategreaterthanreturndate");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break;
							}
						}
					}
				}
			}
		}
		
		if(generateTransactionInvoiceForm.getInvoicedToCode()== null || 
				generateTransactionInvoiceForm.getInvoicedToCode().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generatetransactioninvoice.invoicetocodemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		
		if(generateTransactionInvoiceForm.getName()== null || 
				generateTransactionInvoiceForm.getName().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generatetransactioninvoice.invoicetonamemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		log.exiting("GenerateTransactionInvoiceCommand", "validateForm");
		return errors;
	}

}
