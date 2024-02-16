/*
 * PrintCommand.java Created on Aug 11, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail.report;

import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;

/**
 * @author A-2553
 *
 */

public class PrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTOPS066";
	//private Log log = LogFactory.getLogger("Transfer Mail manifest");
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateTransferManifestReport";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		TransferMailForm transferMailForm = (TransferMailForm)invocationContext.screenModel;
			
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();
		
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(false);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(logonAttributes.getCompanyCode());
		reportSpec.addFilterValue(transferMailForm.getTransferManifestId());
		reportSpec.setResourceBundle("transferMailManifestResources");
		reportSpec.setAction(ACTION);			
			generateReport();
			invocationContext.target = getTargetPage();
		}

	}

