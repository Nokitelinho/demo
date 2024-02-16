/*
 * AWBManifestPrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest.report;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class AWBManifestPrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTOPR048";
	private Log log = LogFactory.getLogger("Mailbag Manifest");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailManifest";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		MailManifestForm mailManifestForm =
    		(MailManifestForm)invocationContext.screenModel;
		MailManifestSession mailManifestSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
			
		log.log(Log.FINE, "\n\n form mailbag id---------->  ", mailManifestForm.getPrintType());
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);			
		reportSpec.addFilterValue(MailConstantsVO.MANIFEST_AWB);
		reportSpec.addFilterValue(mailManifestSession.getOperationalFlightVO());
		reportSpec.setResourceBundle("mailManifestResources");
		reportSpec.setAction(ACTION);
			
			generateReport();
			invocationContext.target = getTargetPage();
		}

	}







