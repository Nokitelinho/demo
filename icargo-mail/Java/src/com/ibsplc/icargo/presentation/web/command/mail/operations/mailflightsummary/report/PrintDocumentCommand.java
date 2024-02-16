/*
 * PrintDocumentCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary.report;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class PrintDocumentCommand extends AbstractPrintCommand {
	
	
	private Log log = LogFactory.getLogger("Mailbag Manifest");	
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateControlDocument";
	private static final String BUNDLE = "mailFlightSummaryResources";
	private static final String REPORT_ID_CN38 = "RPTOPR059";
	private static final String REPORT_ID_CN41 = "RPTOPR060";
	private static final String REPORT_ID_AV7 = "RPTOPR061";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    		
		log.log(Log.FINE, "PrintDocType()-->  ", mailFlightSummaryForm.getPrintDocType());
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();
		
		ReportSpec reportSpec = getReportSpec();
		if(mailFlightSummaryForm.getPrintDocType() != null){
			if(mailFlightSummaryForm.getPrintDocType().startsWith("CN38")){
				reportSpec.setReportId(REPORT_ID_CN38);
			}
			if(mailFlightSummaryForm.getPrintDocType().startsWith("CN41")){
				reportSpec.setReportId(REPORT_ID_CN41);
			}
			if(mailFlightSummaryForm.getPrintDocType().startsWith("AV7")){
				reportSpec.setReportId(REPORT_ID_AV7);
			}
		}
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setCarrierId(mailFlightSummaryForm.getCarrierID());
    	operationalFlightVO.setFlightNumber(mailFlightSummaryForm.getFlightNumber());
    	operationalFlightVO.setCarrierCode(mailFlightSummaryForm.getFlightCarrierCode());
    	operationalFlightVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true).setDate(mailFlightSummaryForm.getFlightDate()));
    	operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryForm.getFlightSequenceNumber());
    	
    	String reportKey = new StringBuilder().append(mailFlightSummaryForm.getPrintDocType())
    	 .append(MailConstantsVO.CONSIGN_REPORT_SEP).append(mailFlightSummaryForm.getPrintKey()).toString();
    	
    	log.log(Log.FINE, "operationalFlightVO()-->  ", operationalFlightVO);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);			
		reportSpec.addFilterValue(operationalFlightVO);
		reportSpec.addFilterValue(reportKey);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
			
		generateReport();
		invocationContext.target = getTargetPage();
	}

}

