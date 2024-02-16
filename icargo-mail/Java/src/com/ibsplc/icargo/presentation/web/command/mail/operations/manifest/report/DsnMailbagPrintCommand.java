/*
 * DsnMailbagPrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class DsnMailbagPrintCommand extends AbstractPrintCommand{

	private static final String REPORT_ID = "RPRMTK081";
	private Log log = LogFactory.getLogger("DsnMailbagManifestReport");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailManifest";
	private static final String BUNDLE = "mailManifestResources";
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	
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
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();	
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MAIL_CATEGORY);
		try {
		
		hashMap = new SharedDefaultsDelegate().findOneTimeValues
								(logonAttributes.getCompanyCode(),oneTimeList);
		log.log(Log.FINEST, "\n\n hash map******************", hashMap);
		
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "\n\n message fetch exception..........;;;;");
		}
		
		
		
		
		     
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);			
		reportSpec.addFilterValue(MailConstantsVO.MANIFEST_DSN_MAILBAG);
		reportSpec.addFilterValue(mailManifestSession.getOperationalFlightVO());
		reportSpec.addExtraInfo(hashMap);	
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
			
			generateReport();
			invocationContext.target = getTargetPage();
		}



	
	}
	


