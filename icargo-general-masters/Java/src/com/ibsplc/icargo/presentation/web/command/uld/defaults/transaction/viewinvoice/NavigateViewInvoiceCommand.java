/*
 * NavigateViewInvoiceCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.viewinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
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
public class NavigateViewInvoiceCommand extends BaseCommand {
	/**
	 * Logger for View Invoice discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.viewinvoice";
	private static final String NAVIGATION_SUCCESS = "navigation_success";
	private static final String NAVIGATION_FAILURE = "navigation_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ViewInvoiceSession viewInvoiceSession = 
    		(ViewInvoiceSession)getScreenSession(MODULE,SCREENID);
    	ViewInvoiceForm viewInvoiceForm = (ViewInvoiceForm) invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	int displayPage = Integer.parseInt(viewInvoiceForm.getDisplayPage());
    	
    	if(viewInvoiceSession.getInvoiceRefNos() != null &&
    			viewInvoiceSession.getInvoiceRefNos().size() > 0) {
    		ArrayList<String> invoiceRefNos =viewInvoiceSession.getInvoiceRefNos();
        	ArrayList<String> invoiceDates = viewInvoiceSession.getInvoiceDate();
        	ArrayList<String> invoiceToCodes = viewInvoiceSession.getInvoicedTo();
        	ArrayList<String> invoiceToNames = viewInvoiceSession.getInvoiceToName();
	    	String displayRefNo = invoiceRefNos.get(displayPage-1);
	    	ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs = null;
	    	
	    		log.log(Log.FINE,
						"*************************DISPLAY_PAGE========",
						displayPage);
			log.log(Log.FINE, "*************************DISPLAY_RefNo========",
					displayRefNo);
			if(viewInvoiceSession.getUldTransactionMap().get(displayRefNo) == null) {
	    		try {
	    			uldTransactionDetailsVOs = new ArrayList<ULDTransactionDetailsVO>(
	    					new ULDDefaultsDelegate().viewULDChargingInvoiceDetails(
	    					 logonAttributes.getCompanyCode(),
	    					 displayRefNo)) ;
	    		}
	    		catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		
	    	}
	    	else {
	    		uldTransactionDetailsVOs =
		    		viewInvoiceSession.getUldTransactionMap().get(displayRefNo);
	    	}
	    	log.log(Log.FINE,
					"*************************NEWTRANSACTIONVOS========",
					uldTransactionDetailsVOs);
			if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = NAVIGATION_FAILURE;
					return;
			}
	    	loadViewInvoiceForm(viewInvoiceForm,invoiceRefNos,invoiceDates,
    				invoiceToCodes,invoiceToNames,displayPage);
	    	calculateDemerage(viewInvoiceForm,uldTransactionDetailsVOs);
	    	HashMap<String, ArrayList<ULDTransactionDetailsVO>> uldTransactionMap =
	    		viewInvoiceSession.getUldTransactionMap();
	    	viewInvoiceSession.setUldTransactionDetailsVO(uldTransactionDetailsVOs);
	    	uldTransactionMap.put(displayRefNo,uldTransactionDetailsVOs);
	    	
    	}
       	invocationContext.target = NAVIGATION_SUCCESS;
        
    }
    private void calculateDemerage(ViewInvoiceForm viewInvoiceForm, 
    		ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs) {
    	double demerageAccured = 0;
    	double waivedAmount = 0;
    	double demerageAmount = 0;
		for(ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
			demerageAccured = demerageAccured + uldTransactionDetailsVO.getDemurrageAmount();
			waivedAmount = waivedAmount + uldTransactionDetailsVO.getWaived();
			demerageAmount = demerageAmount + uldTransactionDetailsVO.getTotal();
		}
		viewInvoiceForm.setWaivedAmount(Double.toString(waivedAmount));
		viewInvoiceForm.setDemerageAmount(Double.toString(demerageAmount));
		viewInvoiceForm.setDemerageAccured(Double.toString(demerageAccured));
	}
	private void loadViewInvoiceForm(ViewInvoiceForm viewInvoiceForm, 
    		ArrayList<String> invoiceRefNos, ArrayList<String> invoiceDates,
    		ArrayList<String> invoiceToCodes, ArrayList<String> invoiceToNames,
    		int displayPage) {
    	viewInvoiceForm.setInvoiceRefNo(invoiceRefNos.get(displayPage-1));
    	viewInvoiceForm.setInvoicedDate(invoiceDates.get(displayPage-1));
    	viewInvoiceForm.setInvoicedToCode(invoiceToCodes.get(displayPage-1));
    	viewInvoiceForm.setName(invoiceToNames.get(displayPage-1));
    	viewInvoiceForm.setCurrentPage(Integer.toString(displayPage));
    	
	}
	    
}
