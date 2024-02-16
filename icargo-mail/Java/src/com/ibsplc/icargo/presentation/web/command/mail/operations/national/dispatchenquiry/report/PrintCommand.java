/*
 * PrintCommand.java Created on JUL 09 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry.report;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5137
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");	
	private static final String REPORT_ID = "RPRMTK082";
	private static final String ACTION = "generateDSNEnquiryReport";	
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String RESOURCE_BUNDLE = "nationalDispatchEnquiryResources";
	private static final String MAIL_STATUS = "mailtracking.defaults.national.despatchstatus";
	private static final String DIRECTION = "mailtracking.defaults.operationtype";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("PrintCommand", "execute");
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);		
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationContext.screenModel;		
		dispatchEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);		
		DSNEnquiryFilterVO dsnEnquiryFilterVO = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		dsnEnquiryFilterVO = dispatchEnquirySession.getDSNEnquiryFilterVO();	
		//ADDED AS PART OF BUG FIX ICRD-17548 BY A-4810
		
		
		Map<String, Collection<OneTimeVO>> oneTimMap = dispatchEnquirySession.getOneTimeVOs();
			
		//Setting ReportSpec with filter VO		
		getReportSpec().setReportId(REPORT_ID);
		
		if(oneTimMap.get(CATEGORY)!=null){
			getReportSpec().addExtraInfo(oneTimMap.get(CATEGORY)); 
		   }
		if(oneTimMap.get(MAIL_STATUS)!=null){
			getReportSpec().addExtraInfo(oneTimMap.get(MAIL_STATUS)); 
		   }
		if(oneTimMap.get(DIRECTION)!=null){
			getReportSpec().addExtraInfo(oneTimMap.get(DIRECTION)); 
			}
		
		
		getReportSpec().setProductCode(PRODUCT_NAME);
		getReportSpec().setSubProductCode(SUBPRODUCT_NAME);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE);
		getReportSpec().addFilterValue(dsnEnquiryFilterVO);		
		getReportSpec().addParameter(dsnEnquiryFilterVO);
		getReportSpec().setPreview(true);
		getReportSpec().setAction(ACTION);
		log.log(Log.FINE, "Generating DSNEnquiryReport--------->>");
		generateReport();
		invocationContext.target = getTargetPage();
		}	
	
}