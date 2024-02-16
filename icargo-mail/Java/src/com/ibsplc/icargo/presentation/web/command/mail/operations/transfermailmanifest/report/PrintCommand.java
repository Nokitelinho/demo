/*
 * PrintCommand.java Created on Apr 03, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2553
 *
 */

public class PrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTOPS066";
	private Log log = LogFactory.getLogger("Transfer Mail manifest");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateTransferManifestReport";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		TransferMailManifestForm transferMailManifestForm = 
    		(TransferMailManifestForm)invocationContext.screenModel;
			
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();
		
		TransferMailManifestSession transferMailManifestSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		Collection<TransferManifestVO> transferMftVOs = new ArrayList<TransferManifestVO>();
		Collection<TransferManifestVO> transferManifestVOs  = transferMailManifestSession.getTransferManifestVOs();
		for(TransferManifestVO trfManifestVO : transferManifestVOs){
			transferMftVOs.add(trfManifestVO);
		}
		String select = transferMailManifestForm.getSelectMail();
		log.log(Log.FINE, "&&&select&", select);
		TransferManifestVO transferManifestVO = ((ArrayList<TransferManifestVO>)(transferMftVOs)).get(Integer.parseInt(select));
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(transferManifestVO.getCompanyCode());
		reportSpec.addFilterValue(transferManifestVO.getTransferManifestId());
		reportSpec.setResourceBundle("transferMailManifestResources");
		reportSpec.setAction(ACTION);			
			generateReport();
			invocationContext.target = getTargetPage();
		}

	}

