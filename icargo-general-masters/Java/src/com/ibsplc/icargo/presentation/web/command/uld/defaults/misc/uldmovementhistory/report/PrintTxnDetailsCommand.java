/*
 * PrintTxnDetailsCommand.java Created on Jul 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;



import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 *
 * @author A-2553
 *
 */

public class PrintTxnDetailsCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST271";
	private Log log = LogFactory.getLogger("Transaction Details");
	private static final String RESOURCE_BUNDLE_KEY = "listuldmovementResources";
	private static final String ACTION = "generateTransactionDetailsReport";
	private static final String PRODUCTCODE ="uld";
	private static final String SUBPRODUCTCODE="defaults";
	//added by A-4443 for icrd-4490 
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";


    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
										throws	CommandInvocationException {
		
		
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		transactionFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		transactionFilterVO.setUldNumber(listULDMovementForm.getUldNumber());
		transactionFilterVO.setPageNumber(Integer.parseInt(listULDMovementForm.getDisplayPage()));
		if(listULDMovementForm.getFromDate()!=null && listULDMovementForm.getFromDate().trim().length()>0){
			LocalDate fromDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);			
			transactionFilterVO.setTxnFromDate(fromDate.setDate(listULDMovementForm.getFromDate()));
		}
		if(listULDMovementForm.getToDate()!=null && listULDMovementForm.getToDate().trim().length()>0){
			LocalDate toDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
			transactionFilterVO.setTxnToDate(toDate.setDate(listULDMovementForm.getToDate()));
		}
		transactionFilterVO.setTransactionType("ALL");
		log.log(Log.FINE,
				"$$$$$$$PrintTxnDetailsCommand$$$$$$$$transactionFilterVO",
				transactionFilterVO);
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);			
		reportSpec.addFilterValue(transactionFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);			
		generateReport();
		//added by A-4443 for icrd-4490 starts
		if(getErrors() != null && getErrors().size() > 0){

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		//added by A-4443 for icrd-4490 ends
		invocationContext.target = getTargetPage();
		
	}

}





