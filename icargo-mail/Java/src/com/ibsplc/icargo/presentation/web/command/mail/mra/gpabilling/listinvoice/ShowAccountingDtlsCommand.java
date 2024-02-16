/*
 * ShowAccountingDtlsCommand.java Created on Nov 14,2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;


/**
 * @author a-3447
 */


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/*
 * @author A-3447
 */


/*
 * Revision 	History 	Revision 	Date 	Author 			Description
 * ========================================================================================================================================================
 * 0.1 		 A-3447 	Coding  14-Nov-2008 Muralee	 To Navigate from ListGpaBilling invoice to List Accounting Screen
 */
/// ========================================================================================================================================================
 


/**
 * This Class is used to Navigate from ListGpaBilling invoice to List Accounting Screen
 */


public class ShowAccountingDtlsCommand extends BaseCommand {

	/***
	 * Logger
	 */

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	/**
	 * CLASS_NAME
	 */
	private static final String CLASS_NAME = "ShowAccountingDtlsCommand";

	/**
	 * List accnt Entry Screen Id.
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";

	/**
	 * Strings for MODULE NAME ListAccountingEntries
	 */

	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";

	/**
	 * Success path For List Accounting Entries
	 */

	private static final String LIST_ACC_ENTRIES = "list_acc_success";
	/**
	 *Parent Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	/**
	 * Parent Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String INVOKE_SCREEN_INVOICEBLG ="listinvoicebilling";
//	/ ========================================================================================================================================================

	
	
	
	/**
	 * @author A-3447
	 * execute Method to implement the Integration With List Accounting Entry Screen	
	 * tion
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
		
	public void execute(InvocationContext invocationContext)  throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;    	
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		ListAccountingEntriesSession accountingEntrySession = getScreenSession(	LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		/***
		 * For getting The Selected Vo
		 */
		String counter = listGPABillingInvoiceForm.getSelectedRow();
		Collection<CN51SummaryVO> cnSummaryVOs = listGPABillingInvoiceSession
		.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cnSummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cnSummaryVOs);
		CN51SummaryVO cnSummaryVO =null;
		log.log(Log.FINE, "counter---->", counter);
		cnSummaryVO = cnSummaryVOArraylist.get(Integer.parseInt(counter));
		log.log(Log.FINE, "ShowAccountingDtlsCommand-/n/n", cnSummaryVO);
		/**
		 * Populating The Filters and setting to accounting Session
		 */
		AccountingFilterVO accountingFilterVO = populateAccountingFilterVO(cnSummaryVO);
		accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		log.log(Log.FINE, "Accountingfiltervo set in session-/n/n",
				accountingEntrySession.getAccountingFilterVO());
		//Added by A-4809 for ICRD-18324 for navigation
		accountingEntrySession.setParentScreenFlag(INVOKE_SCREEN_INVOICEBLG);   
		listGPABillingInvoiceSession.setFromScreen("listGPAInvoices");
		log.log(Log.FINE,
				"Accountingfiltervo set in session ParentScreenFlag-/n/n",
				accountingEntrySession.getParentScreenFlag());
		/**
		 * Success Path for Navigation
		 */
		invocationContext.target = LIST_ACC_ENTRIES;

	}


	/**
	 * Method for Populating Filters to Accounting Filter Vo
	 * @author a-3447
	 * @param cnSummaryVO
	 * @return AccountingFilterVO
	 */
	private AccountingFilterVO populateAccountingFilterVO(
			CN51SummaryVO cnSummaryVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
		accountingFilterVO.setCompanyCode(cnSummaryVO.getCompanyCode());
		accountingFilterVO.setInvoiceNumber(cnSummaryVO
				.getInvoiceNumber());
		accountingFilterVO.setAirlineIdentifier(logonAttributes
				.getOwnAirlineIdentifier());
		/**
		 * Setting Subsystetm and Function Points to The Filter Vo
		 */
		accountingFilterVO.setFunctionPoint(CN51SummaryVO.MRA_FUNCTION_POINT);
		accountingFilterVO.setSubSystem(CN51SummaryVO.MRA_SUB_SYSTEM);
		return accountingFilterVO;
	}






}
