/**
 *  ShowAccountingDetailsCommand.java Created on 22 May 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author a-4823
 *
 */
public class ShowAccountingDetailsCommand extends BaseCommand  {

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
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.invoicesettlement";
	private static final String GPASETTLEMENT="GS";
	private static final String FROM_GPASETTLEMENT="settlementcapture";
	private static final String ACTION_FAILURE="screenload_failure";

	private static final String NO_SETTLEMENT = "mailtracking.mra.gpabilling.nosettlement";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
		ListAccountingEntriesSession accountingEntrySession = getScreenSession(	LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);

		String counter = form.getSelectedInvoice();

		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		accountingFilterVO
		.setFunctionPoint(GPASETTLEMENT);
		accountingFilterVO.setSubSystem("M");
		accountingFilterVO.setPageNumber(1);	
		if(session.getGPASettlementVO()!=null && session.getGPASettlementVO().size()>0){
		Collection<SettlementDetailsVO> settlementDetailsVOs= session.getGPASettlementVO().iterator().next().getSettlementDetailsVOs();
		ArrayList<SettlementDetailsVO> setDetailsVOs = new ArrayList<SettlementDetailsVO>(
				settlementDetailsVOs);
		
		accountingFilterVO
		.setFunctionPoint(GPASETTLEMENT);
		accountingFilterVO.setSubSystem("M");
		accountingFilterVO.setPageNumber(1);		
		
		accountingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(settlementDetailsVOs!=null && settlementDetailsVOs.size()>0){
			accountingFilterVO.setSettlementReferenceNo(settlementDetailsVOs.iterator().next()
					.getSettlementId());
			if(counter!=null && counter.trim().length()>0){
				String[] selectedRow = counter.split(",");

				
				String[] modeNumber;
				if (selectedRow != null && selectedRow.length > 0) {
					modeNumber = new String[selectedRow.length];
					for (int i = 0; i < selectedRow.length; i++) {
						if (selectedRow[i] != null
								&& selectedRow[i].trim().length() > 0) {
							if (setDetailsVOs != null
									&& setDetailsVOs.size() > 0) {
								if (setDetailsVOs.size() >= Integer.parseInt(selectedRow[i])) {
									modeNumber[i] = (setDetailsVOs.get(Integer.parseInt(selectedRow[i]))
											.getChequeNumber());
									accountingFilterVO.setSettlementSerialNo(setDetailsVOs.get(Integer.parseInt(selectedRow[i]))
											.getSettlementSequenceNumber());
								}

							}
						}
					}

					accountingFilterVO.setSettlementModeNumber(modeNumber);

				}
			}
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


		/**
		 * Success Path for Navigation
		 */
		invocationContext.target = LIST_ACC_ENTRIES;

	}


}
