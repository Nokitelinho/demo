/*
 * PrintUCRCommand.java Created on Oct 07, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction.report;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 * 
 */
public class PrintUCRCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTOPR053";

	private static final String ACTION = "printUCRLoanBorrowULD";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private Log log = LogFactory.getLogger("Loan Borrow ULD ");

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		log
				.log(
						Log.FINE,
						"PrintUCRCommand ~~~~~~listULDTransactionForm.getUldNumbersSelected()~~~",
						listULDTransactionForm.getUldNumbersSelected());
		log
				.log(
						Log.FINE,
						"PrintUCRCommand ~~~~~~listULDTransactionForm.getUldNumbersSelected()~~~",
						listULDTransactionForm.getTxnNumbersSelected());
		transactionFilterVO = makeFilter(listULDTransactionForm);
		log.log(Log.FINE, "PrintUCRCommand ~~~~~~transactionFilterVO~~~",
				transactionFilterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listULDTransactionForm.getProduct());
		reportSpec.setSubProductCode(listULDTransactionForm.getSubProduct());
		reportSpec.setPreview(false);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(transactionFilterVO);
		reportSpec.setResourceBundle("loanBorrowDetailsEnquiryResources");
		reportSpec.setAction(ACTION);
		
		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("LOANBORROW ULD", "PrintUCRCommand exit");

		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);

	}

	private TransactionFilterVO makeFilter(
			ListULDTransactionForm listULDTransactionForm) {
		log.entering("PrintUCRCommand", "makeFilter");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		
		transactionFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		
		if(listULDTransactionForm.getUldNumbersSelected() != null &&
				listULDTransactionForm.getUldNumbersSelected().length() > 0) {
				String[] uldNumbersSelected = listULDTransactionForm.getUldNumbersSelected().split(",");
				ArrayList<String> uldNumbersForNavigation = new ArrayList<String>();
	    		for(int i = 0; i < uldNumbersSelected.length ; i++) {
	    			uldNumbersForNavigation.add(uldNumbersSelected[i]);
	    		}
    		transactionFilterVO.setUldNumbers(uldNumbersForNavigation);
		}	
		
		if(listULDTransactionForm.getTxnNumbersSelected() != null &&
				listULDTransactionForm.getTxnNumbersSelected().length() > 0) {				
			String[] txnNumbersSelected = listULDTransactionForm.getTxnNumbersSelected().split(",");
			ArrayList<Integer> txnNumbersForNavigation = new ArrayList<Integer>();
    		for(int i = 0; i < txnNumbersSelected.length ; i++) {
    			txnNumbersForNavigation.add(Integer.parseInt(txnNumbersSelected[i]));
    		}
		transactionFilterVO.setTransactionRefNumbers(txnNumbersForNavigation);
		}			
		transactionFilterVO.setCanUseReturnCRNForUCR(TransactionFilterVO.FLAG_YES);
		log.exiting("PrintUCRCommand", "makeFilter");
		return transactionFilterVO;

	}
}
