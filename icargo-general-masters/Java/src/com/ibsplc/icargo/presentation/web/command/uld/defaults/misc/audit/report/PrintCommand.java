/*
 * PrintCommand.java Created on Apr 24, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.audit.report;


import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDAuditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST229";
	private Log log = LogFactory.getLogger("Audit");
	
	private static final String PRODUCT="uld";
	private static final String SUBPRODUCT="defaults";
	private static final String ACTION="printULDAudit";
	 private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		ULDAuditEnquiryForm uldAuditEnquiryForm = 
			(ULDAuditEnquiryForm) invocationContext.screenModel;
		RelocateULDVO relocateULDVO = new RelocateULDVO();
		relocateULDVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		 if(uldAuditEnquiryForm.getUldNumber() != null && 
				 uldAuditEnquiryForm.getUldNumber().trim().length() > 0){
			 relocateULDVO.setUldNumber(uldAuditEnquiryForm.getUldNumber().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getUldSuffix() != null && 
				 uldAuditEnquiryForm.getUldSuffix().trim().length() > 0){
			 relocateULDVO.setUldSuffix(uldAuditEnquiryForm.getUldSuffix().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getLocation() != null && 
				 uldAuditEnquiryForm.getLocation().trim().length() > 0){
			 relocateULDVO.setLocation(uldAuditEnquiryForm.getLocation().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getUldAirport() != null && 
				 uldAuditEnquiryForm.getUldAirport().trim().length() > 0){
			 relocateULDVO.setCurrentStation(uldAuditEnquiryForm.getUldAirport().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getDefaultComboValue() != null && 
				 uldAuditEnquiryForm.getDefaultComboValue().trim().length() > 0){
			 relocateULDVO.setFacilityType(uldAuditEnquiryForm.getDefaultComboValue().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getTxnFromDate() != null && !("").equals(uldAuditEnquiryForm.getTxnFromDate())) {
		    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		    	relocateULDVO.setTxnFromDate(fromDate.setDate(uldAuditEnquiryForm.getTxnFromDate()));
		    }
		    if(uldAuditEnquiryForm.getTxnToDate() != null && !("").equals(uldAuditEnquiryForm.getTxnToDate())) {
		    	LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		    	relocateULDVO.setTxnToDate(toDate.setDate(uldAuditEnquiryForm.getTxnToDate()));
		    }
	

	
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(PRODUCT);
		getReportSpec().setSubProductCode(SUBPRODUCT);
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(relocateULDVO);
		getReportSpec().setResourceBundle("uldAuditEnquiry");
		getReportSpec().setAction(ACTION);

		log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);
	}
	
}
