/*
 * ViewInvoiceCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.viewinvoice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ViewInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ViewInvoiceForm;
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
public class ViewInvoiceCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.viewinvoice";
	
	private static final String BASE_CURRENCY = "system.defaults.unit.currency" ;
	
	private static final String VIEWINVOICE_SUCCESS = "viewinvoice_success";
	private static final String VIEWINVOICE_FAILURE = "viewinvoice_failure";
	//Added by A-7359 for ICRD-248560
    private static final String SYSPAR_ULDINVCURRENCY = "uld.defaults.uldinvoicingcurrency";
  
   
    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ViewInvoiceSession viewInvoiceSession = (ViewInvoiceSession)getScreenSession(MODULE,SCREENID);
    	ViewInvoiceForm viewInvoiceForm = (ViewInvoiceForm) invocationContext.screenModel;
    	ArrayList<String> invoiceRefNos = new ArrayList<String>();
    	ArrayList<String> invoiceDates = new ArrayList<String>();
    	ArrayList<String> invoiceToCodes = new ArrayList<String>();
    	ArrayList<String> invoiceToNames = new ArrayList<String>();
    	ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs = null;
    	Collection<ErrorVO> errors = null;
    	HashMap<String, ArrayList<ULDTransactionDetailsVO>> uldTransactionMap = 
			new HashMap<String, ArrayList<ULDTransactionDetailsVO>>();
    	if(viewInvoiceForm.getInvoiceRefNo() != null &&
    			viewInvoiceForm.getInvoiceRefNo().length() > 0) {
    		String[] invoiceRefNosSelected = viewInvoiceForm.getInvoiceRefNo().split(",");
    		String[] invoiceDatesSelected = viewInvoiceForm.getInvoicedDate().split(",");
    		String[] invoiceToCodesSelected = viewInvoiceForm.getInvoicedToCode().split(",");
    		String[] invoiceToNamesSelected = viewInvoiceForm.getName().split(",");
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
    			uldTransactionDetailsVOs = new ArrayList<ULDTransactionDetailsVO>(
    					new ULDDefaultsDelegate().viewULDChargingInvoiceDetails(
    					 logonAttributes.getCompanyCode(),
    					 invoiceRefNos.get(0))) ;
    		}
    		catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		loadViewInvoiceForm(viewInvoiceForm,invoiceRefNos,invoiceDates,
    				invoiceToCodes,invoiceToNames,invoiceRefNos.size());
    		
    		
	    	if(invoiceRefNos.size() > 0) {
	    		uldTransactionMap.put(invoiceRefNos.get(0),uldTransactionDetailsVOs);
	    	}
	    	if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = VIEWINVOICE_FAILURE;
					return;
			}
	    	calculateDemerage(viewInvoiceForm,uldTransactionDetailsVOs);
    	}
    	viewInvoiceSession.setInvoiceRefNos(invoiceRefNos);
    	viewInvoiceSession.setInvoiceDate(invoiceDates);
    	viewInvoiceSession.setInvoicedTo(invoiceToCodes);
    	viewInvoiceSession.setInvoiceToName(invoiceToNames);
    	viewInvoiceSession.setUldTransactionDetailsVO(uldTransactionDetailsVOs);
    	viewInvoiceSession.setUldTransactionMap(uldTransactionMap);
    	invocationContext.target = VIEWINVOICE_SUCCESS;
    }
    private void calculateDemerage(ViewInvoiceForm viewInvoiceForm, 
    		ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs) {
    	log.entering("ViewInvoiceCommand", "calculateDemerage");
    	double demerageAccured = 0;
    	double waivedAmount = 0;
    	double demerageAmount = 0;
    	DecimalFormat formatter = new DecimalFormat("0.000"); 
    	log.log(Log.INFO, "%%%%% formatter", formatter);
		for(ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
			demerageAccured = demerageAccured + uldTransactionDetailsVO.getDemurrageAmount();
			waivedAmount = waivedAmount + uldTransactionDetailsVO.getWaived();
			demerageAmount = demerageAmount + uldTransactionDetailsVO.getTotal();
		}
		//Modifed by A-7359 for ICRD-248560
		viewInvoiceForm.setWaivedAmount(formatter.format(waivedAmount));
		viewInvoiceForm.setDemerageAmount(formatter.format(demerageAmount));
		viewInvoiceForm.setDemerageAccured(formatter.format(demerageAccured));
		log.log(Log.INFO, "%%%%% Formatted WaivedAmount", viewInvoiceForm.getWaivedAmount());
		log.log(Log.INFO, "%%%%% Formatted DemerageAmount", viewInvoiceForm.getDemerageAmount());
		log.log(Log.INFO, "%%%%% Formatted DemerageAccured", viewInvoiceForm.getDemerageAccured());
		log.entering("ViewInvoiceCommand", "calculateDemerage");
	}
    
    private String getBaseCurrency() {
    	
		String airlineBaseCurrency="";
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
    
    
    private void loadViewInvoiceForm(ViewInvoiceForm viewInvoiceForm, 
    		ArrayList<String> invoiceRefNos, ArrayList<String> invoiceDates,
    		ArrayList<String> invoiceToCodes, ArrayList<String> invoiceToNames,
    		int collectionSize) {
    	viewInvoiceForm.setInvoiceRefNo(invoiceRefNos.get(0));
    	viewInvoiceForm.setInvoicedDate(invoiceDates.get(0));
    	viewInvoiceForm.setInvoicedToCode(invoiceToCodes.get(0));
    	viewInvoiceForm.setName(invoiceToNames.get(0));
    	viewInvoiceForm.setDisplayPage("1");
    	viewInvoiceForm.setCurrentPage("1");
    	viewInvoiceForm.setAirlineBaseCurrency(getBaseCurrency());
    	viewInvoiceForm.setLastPageNum(Integer.toString(collectionSize));
    	viewInvoiceForm.setTotalRecords(Integer.toString(collectionSize));
	}

    
}
