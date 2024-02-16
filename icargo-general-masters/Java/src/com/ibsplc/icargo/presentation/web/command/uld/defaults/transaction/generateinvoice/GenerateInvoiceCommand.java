/*
 * GenerateInvoiceCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.generateinvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.GenerateInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1496
 *
 */
public class GenerateInvoiceCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("List Invoice ULD");
	
	private static final String GENERATE_SUCCESS = "generate_success";
	private static final String GENERATE_FAILURE = "generate_failure";
	private static final String AIRLINE = "Airline"; 
    private static final String SCREENID = "uld.defaults.listrepairreport";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String EMPTYSTRING = "";
	private static final String AGENT = "Agent";
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
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		GenerateInvoiceForm generateInvoiceForm = (GenerateInvoiceForm)invocationContext.screenModel;
    	
		Collection<ErrorVO> errors = validateForm(generateInvoiceForm,
				logonAttributes.getCompanyCode());
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = GENERATE_FAILURE;
				return;
		}
		String prtyCode = generateInvoiceForm.getInvoicedToCode().toUpperCase();
		AirlineValidationVO ownerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		log.log(Log.FINE, "generateInvoiceForm.getPartyTypeFlag()-------->>>",
				generateInvoiceForm.getPartyTypeFlag());
		//Modified by A-5493 for ICRD-48579
		//As partyTypeFlag value is not set, it is always considered as AIRLINE
		if (AIRLINE.equals(generateInvoiceForm.getPartyTypeFlag()) || generateInvoiceForm.getPartyTypeFlag()==null
				|| EMPTYSTRING.equals(generateInvoiceForm.getPartyTypeFlag())) {
		//Modified by A-5493 ends
    		if (prtyCode != null && ! ("".equals(prtyCode))) {
    		log.log(Log.FINE,"<<----------------AirlineDelegate---------------------->>>");
    		try {
				ownerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						generateInvoiceForm.getInvoicedToCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				
				errors =  handleDelegateException(businessDelegateException);
   			}
    		}
		}
		if (AGENT.equals(generateInvoiceForm.getPartyTypeFlag())) {
			if (prtyCode != null && ! ("".equals(prtyCode))) {
				log.log(Log.FINE,"<<----------------agentDelegate---------------------->>>");
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agentVO = agentDelegate.findAgentDetails(
						logonAttributes.getCompanyCode(),
						generateInvoiceForm.getInvoicedToCode().toUpperCase());
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
		}
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = GENERATE_FAILURE;
				return;
		}
		int airlineIdentifier=0;
		ULDChargingInvoiceVO uldChargingInvoiceVO = new ULDChargingInvoiceVO();
		loadInvoiceVOFromForm(generateInvoiceForm,uldChargingInvoiceVO);
		//Modified by A-7359 for ICRD-248560 starts here
		if(ownerVO!=null&&ownerVO.getAirlineIdentifier()>0){
			airlineIdentifier=ownerVO.getAirlineIdentifier();
		}else{
			airlineIdentifier=logonAttributes.getOwnAirlineIdentifier();
		}
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs =
			loadTransactionVO(generateInvoiceForm, logonAttributes.getCompanyCode(),airlineIdentifier);
		//Modified by A-7359 for ICRD-248560 ends here 
		if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateInvoiceForm.getTxnType())) {
			uldChargingInvoiceVO.setRepairInvoice(true);
		}
		String invoiceId = ""; 
		log.log(Log.FINE,
				"uldTransactionDetailsVOs inside GenerateInvoiceCommand",
				uldTransactionDetailsVOs);
		try {
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
		generateInvoiceForm.setInvoiceId(invoiceId);
		generateInvoiceForm.setStatusFlag("generate");
		invocationContext.target = GENERATE_SUCCESS;
    }
    
    private Collection<ULDTransactionDetailsVO> loadTransactionVO(
    			GenerateInvoiceForm generateInvoiceForm,  String companyCode, int airlineIdentifier) {
    	log.entering(" GenerateInvoiceCommand", "loadTransactionVO");
    	Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = 
			new ArrayList<ULDTransactionDetailsVO>();
    	String[] uldNumbers = generateInvoiceForm.getUldNumbers().split(",");
    	String[] txnRefNos = generateInvoiceForm.getTxnRefNos().split(",");
    	ListRepairReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	Page<ULDRepairDetailsListVO> repairDetailVOs = session.getULDDamageRepairDetailsVOs();
    	//Modified by A-7359 for ICRD-248560 starts here
    	Collection<ULDRepairDetailsListVO> repairDetailTempVOs = new ArrayList<ULDRepairDetailsListVO>();
    	int uldNumbersSize = uldNumbers.length;    	
    	for(int i = 0; i < uldNumbersSize; i++ ) {
    		ULDTransactionDetailsVO uldTransactionDetailsVO =
    							new ULDTransactionDetailsVO();
    		if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateInvoiceForm.getTxnType())) { 
    			uldTransactionDetailsVO.setRepairSeqNumber(
        				Integer.parseInt(txnRefNos[i]));
    		}
    		else {
    			uldTransactionDetailsVO.setTransactionRefNumber(
        				Integer.parseInt(txnRefNos[i]));
    		}
    		
    		uldTransactionDetailsVO.setUldNumber(uldNumbers[i]);
    		uldTransactionDetailsVO.setCompanyCode(companyCode);
    		uldTransactionDetailsVO.setOperationalAirlineIdentifier(airlineIdentifier);
    		for(ULDRepairDetailsListVO uLDRepairDetailsListVO : repairDetailVOs){
    			if(uLDRepairDetailsListVO.getUldNumber().equalsIgnoreCase(uldNumbers[i])){
    				log.log(Log.FINE, "Setting Last Updated time");
    				//added by a-3278 for 17985 on 07Sep08
    				uldTransactionDetailsVO.setDemurrageAmount(uLDRepairDetailsListVO.getRepairAmount());
    				//a-3278 ends
    				uldTransactionDetailsVO.setLastUpdateTime(uLDRepairDetailsListVO.getLastUpdateTime());
    				uldTransactionDetailsVO.setCurrency(uLDRepairDetailsListVO.getCurrency());
    				repairDetailVOs.remove(uLDRepairDetailsListVO);
    				break;
    			}
    		}
    		//Modified by A-7359 for ICRD-248560 ends here
    		uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
    	}
    	log.exiting("GenerateInvoiceCommand", "loadTransactionVO");
		return uldTransactionDetailsVOs;
	}

	private void loadInvoiceVOFromForm(GenerateInvoiceForm generateInvoiceForm,
    		ULDChargingInvoiceVO uldChargingInvoiceVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	if((generateInvoiceForm.getInvoicedDate() != null &&
    			generateInvoiceForm.getInvoicedDate().trim().length() > 0)) {
			LocalDate invoiceDate = null;
			invoiceDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			invoiceDate.setDate(generateInvoiceForm.getInvoicedDate());
    		uldChargingInvoiceVO.setInvoicedDate(invoiceDate);
    		
    	}
    	
    	if((generateInvoiceForm.getInvoicedToCode() != null &&
				generateInvoiceForm.getInvoicedToCode().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoicedToCode(generateInvoiceForm.getInvoicedToCode().toUpperCase());
    		
    	}
    	
    	if((generateInvoiceForm.getName() != null &&
				generateInvoiceForm.getName().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoicedToCodeName(generateInvoiceForm.getName().toUpperCase());
    	
    	}
    	
    	if((generateInvoiceForm.getRemarks() != null &&
				generateInvoiceForm.getRemarks().trim().length() > 0)) {
    		uldChargingInvoiceVO.setInvoiceRemark(generateInvoiceForm.getRemarks().toUpperCase());
    	
    	}
    	if((generateInvoiceForm.getTxnType() != null &&
				generateInvoiceForm.getTxnType().trim().length() > 0)) {
    		uldChargingInvoiceVO.setTransactionType(generateInvoiceForm.getTxnType().toUpperCase());
    	
    	}
    	/*if((generateInvoiceForm.getDemAmt() != null &&
				generateInvoiceForm.getDemAmt().trim().length() > 0)) {
    		uldChargingInvoiceVO.setDemurrageAmount(Double.parseDouble(generateInvoiceForm.getDemAmt()));
    	
    	}*/
    	/*if((generateInvoiceForm.getWaiver() != null &&
				generateInvoiceForm.getWaiver().trim().length() > 0)) {
    		uldChargingInvoiceVO.set(Double.parseDouble(generateInvoiceForm.getWaiver()));
    	
    	}*/
    	uldChargingInvoiceVO.setLastUpdatedUser(logonAttributes.getUserId());
    	uldChargingInvoiceVO.setLastUpdatedTime(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true));
    	uldChargingInvoiceVO.setCompanyCode(logonAttributes.getCompanyCode());
		
    }

   /**
    * 
    * @param generateInvoiceForm
    * @param companyCode
    * @return
    */
	private Collection<ErrorVO> validateForm(
			GenerateInvoiceForm generateInvoiceForm, String companyCode){
		String txnDates[] = generateInvoiceForm.getTxndates().split(",");
		log.entering("GenerateInvoiceCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(generateInvoiceForm.getInvoicedDate()== null || 
				generateInvoiceForm.getInvoicedDate().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generateinvoice.invoicedatemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			
			
			if(!DateUtilities.isValidDate(generateInvoiceForm.getInvoicedDate(),"dd-MMM-yyyy")) {
				
				error = new ErrorVO("uld.defaults.generateinvoice.invaliddate",
						new Object[]{generateInvoiceForm.getInvoicedDate().toUpperCase()});
				errors.add(error);
			}
			else {
				for(int i=0;i< txnDates.length;i++) {
					if(DateUtilities.isValidDate(txnDates[i],"dd-MMM-yyyy")) {
						LocalDate txndate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						txndate.setDate(txnDates[i]);
						LocalDate transactionDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						transactionDate.setDate(generateInvoiceForm.getInvoicedDate());
						if(txndate.isGreaterThan(transactionDate)) {
							if(ULDChargingInvoiceVO.REPAIR_TXN_TYPE.equals(generateInvoiceForm.getTxnType())) {
								error = new ErrorVO("uld.defaults.generateinvoice.invoicedategreaterthanrepairdate");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break;
							}
							else {
								error = new ErrorVO("uld.defaults.generateinvoice.invoicedategreaterthanreturndate");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break;
							}
						}
					}
				}
			}
		}
		
		if(generateInvoiceForm.getInvoicedToCode()== null || 
				generateInvoiceForm.getInvoicedToCode().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generateinvoice.invoicetocodemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		
		if(generateInvoiceForm.getName()== null || 
				generateInvoiceForm.getName().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.generateinvoice.invoicetonamemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		log.exiting("GenerateInvoiceCommand", "validateForm");
		return errors;
	}

}
