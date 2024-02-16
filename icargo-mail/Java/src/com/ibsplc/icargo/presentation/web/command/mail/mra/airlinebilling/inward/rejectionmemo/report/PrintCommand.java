/*
 * PrintCommand.java Created on May 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING REJECTION MEMO");
	
	private static final String REPORT_ID = "RPTMRA042";
	private static final String RESOURCE_BUNDLE_KEY = "rejectionmemobundle";
	
	private static final String ACTION = "printrejectionmemo";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String MEMONO_MANDATORY="mailtracking.mra.airlinebilling.rejectionmemo.err.memonomandatory";
	private static final String BLANK="";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("MRA_AIRLINEBILLING REJECTION MEMO","Print Command entered");
		RejectionMemoForm rejectionMemoForm = 
			(RejectionMemoForm)invocationContext.screenModel;
		String memoNumber=rejectionMemoForm.getMemoCode();
		String invoiceNumber=rejectionMemoForm.getYourInvoiceNumber();
		String airlineIdr=rejectionMemoForm.getAirlineIdentifier();
		String clearancePeriod=rejectionMemoForm.getInwardClearancePeriod();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		ErrorVO errorVO=null;
		RejectionMemoFilterVO filterVO=new RejectionMemoFilterVO();
		
		
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
	    filterVO.setCompanyCode(companyCode);
		if(!BLANK.equals(memoNumber.trim())){
			filterVO.setMemoCode(memoNumber.trim().toUpperCase());
		}else{
			errorVO=new ErrorVO(MEMONO_MANDATORY);
			errors.add(errorVO);
		}
		if(!BLANK.equals(invoiceNumber.trim())){
			filterVO.setInvoiceNumber(invoiceNumber.trim().toUpperCase());
		}
		if(airlineIdr!=null && (!BLANK.equals(airlineIdr.trim()))){
			filterVO.setAirlineIdentifier(Integer.parseInt(airlineIdr.trim()));
		}
		if(clearancePeriod!=null && (!BLANK.equals(clearancePeriod.trim()))){
			filterVO.setClearancePeriod(clearancePeriod.trim());
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.log(Log.INFO, "filterVo", filterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(rejectionMemoForm.getProduct());
		reportSpec.setSubProductCode(rejectionMemoForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_AIRLINEBILLING REJECTION MEMO","PrintCommand exit");
		invocationContext.target = getTargetPage();
	}
}
