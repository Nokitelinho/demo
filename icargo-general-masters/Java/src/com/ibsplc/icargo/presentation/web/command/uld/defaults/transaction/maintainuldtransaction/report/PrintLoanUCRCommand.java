/*
 * PrintLoanUCRCommand.java Created on Oct 07, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction.report;

//import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *.
 */
public class PrintLoanUCRCommand extends AbstractPrintCommand {

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	private static final String REPORT_ID = "RPTOPR053";

	private static final String ACTION = "printUCRLoanBorrowULD";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private Log log = LogFactory.getLogger("Return Loan Borrow ULD ");
	/** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

				
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		MaintainULDTransactionForm maintainULDTransactionForm =
    		(MaintainULDTransactionForm) invocationContext.screenModel;
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		/*if(maintainULDTransactionForm.getPrintUCR() !=null){
			if("N".equals(maintainULDTransactionForm.getPrintUCR())){
				
			}
		}*/
		
		transactionFilterVO = makeFilter(maintainULDTransactionForm,loanBorrowULDSession);
		log.log(Log.FINE, "filter constructed is 123", transactionFilterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(maintainULDTransactionForm.getProduct());
		reportSpec.setSubProductCode(maintainULDTransactionForm.getSubProduct());
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

		log.exiting("LOANBORROW ULD", "PrintUCRCommand exitting");

		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);

	}

	private TransactionFilterVO makeFilter(
			MaintainULDTransactionForm maintainULDTransactionForm,LoanBorrowULDSession loanBorrowULDSession) {
		log.entering("PrintCommand", "makeFilter");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		
		transactionFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		log.log(Log.FINE,
				"listULDTransactionSession.getUldNumbersSelected()---",
				loanBorrowULDSession.getUldNumbersSelected());
		if(loanBorrowULDSession.getUldNumbersSelected() !=null && loanBorrowULDSession.getUldNumbersSelected().size()>0){
			transactionFilterVO.setUldNumbers(loanBorrowULDSession.getUldNumbersSelected());		
			transactionFilterVO.setTransactionRefNumbers(loanBorrowULDSession.getTxnNumbersSelected());			
			transactionFilterVO.setPartyType(maintainULDTransactionForm.getPartyType());
			transactionFilterVO.setToPartyCode(maintainULDTransactionForm.getToPartyCode());
			transactionFilterVO.setToPartyName(maintainULDTransactionForm.getToPartyName());
			transactionFilterVO.setSupportInfo(maintainULDTransactionForm.getAwbNumber());
		}	
		transactionFilterVO.setIsLoanUcrPrint(loanBorrowULDSession.getLoanUcrPrint());		
		log.exiting("PrintCommand", "makeFilter");
		return transactionFilterVO;

	}
}
