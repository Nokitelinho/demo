/*
 * RepairInvoiceCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.repairinvoice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.RepairInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2001
 */
public class RepairInvoiceCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	
	private static final String REPAIRINVOICE_SUCCESS = "repairinvoice_success";
	private static final String REPAIRINVOICE_FAILURE = "repairinvoice_failure";
    
	private static final String BASE_CURRENCY = "system.defaults.unit.currency" ;
	//Added by A-7359 for ICRD-248560
    private static final String SYSPAR_ULDINVCURRENCY = "uld.defaults.uldinvoicingcurrency";
  
   
    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("RepairInvoiceCommand", "execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		RepairInvoiceSession repairInvoiceSession = (RepairInvoiceSession)getScreenSession(MODULE,SCREENID);
    	RepairInvoiceForm repairnvoiceForm = (RepairInvoiceForm) invocationContext.screenModel;
    	ArrayList<String> invoiceRefNos = new ArrayList<String>();
    	ArrayList<String> invoiceDates = new ArrayList<String>();
    	ArrayList<String> invoiceToCodes = new ArrayList<String>();
    	ArrayList<String> invoiceToNames = new ArrayList<String>();
    	ULDRepairInvoiceVO uldRepairInvoiceVO = null;
    	ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs = null;
    	repairnvoiceForm.setAirlineBaseCurrency(getBaseCurrency());
    	Collection<ErrorVO> errors = null;
    	HashMap<String,ULDRepairInvoiceVO> uldRepairInvoiceVOs = 
			new HashMap<String,ULDRepairInvoiceVO>();
    	if(repairnvoiceForm.getInvoiceRefNo() != null &&
    			repairnvoiceForm.getInvoiceRefNo().length() > 0) {
    		String[] invoiceRefNosSelected = repairnvoiceForm.getInvoiceRefNo().split(",");
    		String[] invoiceDatesSelected = repairnvoiceForm.getInvoicedDate().split(",");
    		String[] invoiceToCodesSelected = repairnvoiceForm.getInvoicedToCode().split(",");
    		String[] invoiceToNamesSelected = repairnvoiceForm.getName().split(",");
    		for(int i = 0; i < invoiceRefNosSelected.length ; i++) {
    			invoiceRefNos.add(invoiceRefNosSelected[i]);
    		}
    		for(int i = 0; i < invoiceDatesSelected.length ; i++) {
    			invoiceDates.add(invoiceDatesSelected[i]);
    		}
    		for(int i = 0; i < invoiceToCodesSelected.length ; i++) {
    			invoiceToCodes.add(invoiceToCodesSelected[i]);
    		}
    		for(int i = 0; i < invoiceToNamesSelected.length ; i++) {
    			invoiceToNames.add(invoiceToNamesSelected[i]);
    		}
    		try {
    			uldRepairInvoiceVO =
    					new ULDDefaultsDelegate().findRepairInvoiceDetails(
    					 logonAttributes.getCompanyCode(),
    					 invoiceRefNos.get(0)) ;
    		}
    		catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		loadRepairInvoiceForm(repairnvoiceForm,invoiceRefNos,invoiceDates,
    				invoiceToCodes,invoiceToNames,invoiceRefNos.size());
    		
    		
	    	if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = REPAIRINVOICE_FAILURE;
					return;
			}
	    	if( uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs() != null) {
	    		//resetInvoiceAmount(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());  //Commented by Sreekumar for displaying the Actual Invoice amount on jsp Page
	    		uldRepairDetailsVOs = new ArrayList<ULDRepairInvoiceDetailsVO>
	    					(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
	    		//Added by A-7359 for ICRD-248560
	    		calculateInvoicedAmount(uldRepairDetailsVOs);
	    	}
	    	if(invoiceRefNos.size() > 0) {
	    		/*uldRepairMap.put(invoiceRefNos.get(0),
	    				uldRepairDetailsVOs);*/
	    		uldRepairInvoiceVOs.put(invoiceRefNos.get(0),uldRepairInvoiceVO);
	    	}
    	}
    	repairInvoiceSession.setInvoiceRefNos(invoiceRefNos);
    	repairInvoiceSession.setInvoiceDate(invoiceDates);
    	repairInvoiceSession.setInvoicedTo(invoiceToCodes);
    	repairInvoiceSession.setInvoiceToName(invoiceToNames);
    	repairInvoiceSession.setUldRepairDetailsVO(
    			uldRepairDetailsVOs);
    	repairInvoiceSession.setUldRepairInvoiceVO(uldRepairInvoiceVOs);
    	invocationContext.target = REPAIRINVOICE_SUCCESS;
    	log.exiting("RepairInvoiceCommand", "execute");
    }
    /**
	 * 	Method		:	RepairInvoiceCommand.calculateInvoicedAmount
	 *	Added by 	:	A-7359 on 12-Jun-2018
	 * 	Used for 	:	ICRD-248560
	 *	Parameters	:	@param uldRepairDetailsVOs 
	 *	Return type	: 	void
	 */
	private void calculateInvoicedAmount(
			ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {

    	log.entering("RepairInvoiceCommand", "calculateInvoicedAmount");
        DecimalFormat formatter = new DecimalFormat("0.000"); 
    	log.log(Log.INFO, "%%%%% formatter", formatter);
		for(ULDRepairInvoiceDetailsVO uldDetailsVO : uldRepairDetailsVOs) {

			uldDetailsVO.setWaivedAmount(Double.parseDouble(formatter.format(uldDetailsVO.getWaivedAmount())));
			uldDetailsVO.setActualAmount(Double.parseDouble(formatter.format(uldDetailsVO.getActualAmount())));
			uldDetailsVO.setInvoicedAmount(Double.parseDouble(formatter.format(uldDetailsVO.getInvoicedAmount())));
			log.log(Log.INFO, "%%%%% Formatted WaivedAmount", uldDetailsVO.getWaivedAmount());
			log.log(Log.INFO, "%%%%% Formatted ActualAmount", uldDetailsVO.getActualAmount());
			log.log(Log.INFO, "%%%%% Formatted InvoicedAmount", uldDetailsVO.getInvoicedAmount());
		}		
		log.exiting("RepairInvoiceCommand", "calculateInvoicedAmount");
	
		
    }
    private String getBaseCurrency() {
		 String airlineBaseCurrency = "";
		 /*
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			airlineValidationVO = new AirlineDelegate().findAirline(
				logonAttributes.getCompanyCode(),logonAttributes.getOwnAirlineIdentifier());
		} catch (BusinessDelegateException e) {
			error = handleDelegateException(e);
		}
		airlineBaseCurrency = airlineValidationVO.getBaseCurrencyCode();
		return airlineBaseCurrency;
		*/
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		Collection<String> systemparameterCodes = new ArrayList<String>();
		systemparameterCodes.add(SYSPAR_ULDINVCURRENCY);
		Map<String,String> map = null;
		try{
			 map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
		}catch(BusinessDelegateException e) {
			error = handleDelegateException(e);
		}
		if(map != null){
			airlineBaseCurrency = map.get(SYSPAR_ULDINVCURRENCY);
		}
		log.log(Log.INFO, "%%%%% airlineBaseCurrency", airlineBaseCurrency);
		return airlineBaseCurrency;
	}
	
    private void loadRepairInvoiceForm(RepairInvoiceForm repairnvoiceForm, 
    		ArrayList<String> invoiceRefNos, ArrayList<String> invoiceDates,
    		ArrayList<String> invoiceToCodes, ArrayList<String> invoiceToNames,
    		int collectionSize) {
    	repairnvoiceForm.setInvoiceRefNo(invoiceRefNos.get(0));
    	repairnvoiceForm.setInvoicedDate(invoiceDates.get(0));
    	repairnvoiceForm.setInvoicedToCode(invoiceToCodes.get(0));
    	repairnvoiceForm.setName(invoiceToNames.get(0));
    	repairnvoiceForm.setDisplayPage("1");
    	repairnvoiceForm.setCurrentPage("1");
    	repairnvoiceForm.setLastPageNum(Integer.toString(collectionSize));
    	repairnvoiceForm.setTotalRecords(Integer.toString(collectionSize));
	}
    
    private void resetInvoiceAmount(Collection<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
    	for(ULDRepairInvoiceDetailsVO uldRepairInvoiceDetailsVO : uldRepairDetailsVOs) {
    		uldRepairInvoiceDetailsVO.setInvoicedAmount(-1);
    	}
    	
    }
    
}
