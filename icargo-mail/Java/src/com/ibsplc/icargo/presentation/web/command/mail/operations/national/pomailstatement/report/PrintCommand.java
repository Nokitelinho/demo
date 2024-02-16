/*
 * PrintCommand.java Created on Feb 01 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.POMailStatementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4777
 *
 */

public class PrintCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");	
	private static final String REPORT_ID = "RPRMTK073";
	private static final String ACTION = "generatePOMailStatement";	
	private static String PRODUCT_NAME = "mailtracking";
	private static String SUBPRODUCT_NAME = "defaults";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement";
	private static final String MAIL_POSTAL_CODE ="mailtracking.defaults.national.postalcode";
	private static final String RESOURCE_BUNDLE = "pomailStatementResources";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("PrintCommand", "execute");
		POMailStatementForm poMailStatementForm =(POMailStatementForm)invocationContext.screenModel;
		POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		DSNEnquiryFilterVO dsnEnquiryFilterVO = new DSNEnquiryFilterVO();
		String category = poMailStatementForm.getCategory();
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(poMailStatementForm.getFromDate());
		LocalDate toDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(poMailStatementForm.getToDate());
		String consgNo = poMailStatementForm.getConsignmentNo();
		String consgOrigin = poMailStatementForm.getOrigin(); 
		String consgDestination = poMailStatementForm.getDestination();	
		Map<String, String> parMap  = populateSystemParameter();
		dsnEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		dsnEnquiryFilterVO.setPageNumber(Integer.parseInt(poMailStatementForm.getDisplayPage()));
		dsnEnquiryFilterVO.setMailCategoryCode(category);
		dsnEnquiryFilterVO.setFromDate(fromDate);
		dsnEnquiryFilterVO.setToDate(toDate);
		dsnEnquiryFilterVO.setConsignmentNumber(consgNo);
		dsnEnquiryFilterVO.setOriginOfficeOfExchange(consgOrigin);
		dsnEnquiryFilterVO.setDestinationOfficeOfExchange(consgDestination);
		
		
		if(parMap != null && parMap.size() >0){
			dsnEnquiryFilterVO.setPaCode(parMap.get(MAIL_POSTAL_CODE)) ;
		}
		//Added by a-4810 for icrd-17547
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		 Collection<String> fieldTypes = new ArrayList<String>();
		 fieldTypes.add(CATEGORY);
		 try {
				oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			Map<String, Collection<OneTimeVO>> oneTimMap = (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
		//
		
		getReportSpec().setReportId(REPORT_ID);
		
		if(oneTimMap.get(CATEGORY)!=null){
		getReportSpec().addExtraInfo(oneTimMap.get(CATEGORY)); 
		}
		getReportSpec().setProductCode(PRODUCT_NAME);
		getReportSpec().setSubProductCode(SUBPRODUCT_NAME);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE);
		getReportSpec().addFilterValue(dsnEnquiryFilterVO);		
		getReportSpec().addParameter(dsnEnquiryFilterVO);
		getReportSpec().setPreview(true);
		getReportSpec().setAction(ACTION);
		generateReport();
		invocationContext.target = getTargetPage();
		}
	
	private Map<String, String> populateSystemParameter() {
		Map<String, String> parMap  = null;
		Collection<String> codes = new ArrayList<String>();
		codes.add(MAIL_POSTAL_CODE);
		try{
		parMap = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return parMap;
		
	}
}