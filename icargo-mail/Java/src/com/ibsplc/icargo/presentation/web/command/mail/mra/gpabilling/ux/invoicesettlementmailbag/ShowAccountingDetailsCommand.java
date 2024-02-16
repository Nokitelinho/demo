/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ShowAccountingDetailsCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	13-Mar-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ShowAccountingDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	13-Mar-2019	:	Draft
 */
public class ShowAccountingDetailsCommand extends BaseCommand {
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

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String GPASETTLEMENT="GS";
	private static final String FROM_GPASETTLEMENT="mailbagsettlementcapture";
	private static final String ACTION_FAILURE="screenload_failure";

	private static final String NO_SETTLEMENT = "mailtracking.mra.gpabilling.nosettlement";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 13-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		InvoiceSettlementMailbagSession session=(InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
		
		InvoiceSettlementFilterVO filterVO=session.getInvoiceSettlementFilterVO();
		filterVO.setActionFlag(form.getActionFlag());
		ListAccountingEntriesSession accountingEntrySession = getScreenSession(	LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		String counter = form.getSelectedMailbag();
		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();  
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		accountingFilterVO
		.setFunctionPoint(GPASETTLEMENT);
		accountingFilterVO.setSubSystem("M");
		accountingFilterVO.setPageNumber(1);	  
		
		if(session.getSelectedGPASettlementVO()!=null && session.getSelectedGPASettlementVO().size()>0){
			Page<InvoiceSettlementVO> selectedInvoiceSettlementVOs =  session.getSelectedGPASettlementVO().iterator().next().getInvoiceSettlementVO();
			
			accountingFilterVO
			.setFunctionPoint(GPASETTLEMENT);
			accountingFilterVO.setSubSystem("M");
			accountingFilterVO.setPageNumber(1);		
			
			accountingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			if(selectedInvoiceSettlementVOs!=null&&selectedInvoiceSettlementVOs.size()>0){
				
				InvoiceSettlementVO settlementVO=selectedInvoiceSettlementVOs.get(Integer.parseInt(counter));
				accountingFilterVO.setMailBillingBasis(settlementVO.getMailbagID());
			}
			else{
				ErrorVO err = new ErrorVO(NO_SETTLEMENT);
				invocationContext.addError(err);
				invocationContext.target = ACTION_FAILURE;
				return;
			}
		}
		accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		accountingEntrySession
		.setParentScreenFlag(FROM_GPASETTLEMENT);
		session.setInvoiceSettlementFilterVO(filterVO);
		invocationContext.target = LIST_ACC_ENTRIES;
	}

}
