/**
 * PrintCommand.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.pomailsummary.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.POMailSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.POMailSummaryForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author a-4823
 *
 */
public class PrintCommand extends AbstractPrintCommand{
	private Log log = LogFactory.getLogger("PrintCommand");
	private static final String CLASS_NAME = "PrintCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.pomailsummary";
	private static final String REPORT_ID = "RPRMTK076";
	private static final String ACTION = "findPOMailSummaryDetails";	
	private static final String PRODUCT_NAME = "mailtracking";
	private static final String SUBPRODUCT_NAME = "mra";
	private static final String RESOURCE_BUNDLE = "poMailSummaryResources";
	private static final String PRINT_UNSUCCESSFUL="normal-report-error-jsp";	
	private static final String FROMDATE_MANDATORY="mailtracking.mra.gpabilling.err.fromdatemandatory";
	private static final String TODATE_MANDATORY="mailtracking.mra.gpabilling.err.todatemandatory";
	private static final String DATERANGEEXCEEDS = "mailtracking.mra.gpabilling.err.daterangeexceeds";
	private static final String MAIL_CATEGORY ="mailtracking.defaults.mailcategory";
	private static final String MAIL_CLASS="mailtracking.defaults.mailsubclassgroup";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException { 
		log.entering(CLASS_NAME, "execute");
		POMailSummaryForm form=( POMailSummaryForm)invocationContext.screenModel;
		POMailSummarySession session = (POMailSummarySession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO= new BillingSummaryDetailsFilterVO();
		Map<String, Collection<OneTimeVO>> oneTimMap = session.getOneTimeVOs();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		errorVOs = validateForm(form);
		if(errorVOs!=null && errorVOs.size()>0){
			invocationContext.addAllError(errorVOs);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		billingSummaryDetailsFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		if(form.getLocationType()!=null && form.getLocation().trim().length()>0){
			if(("Country").equals(form.getLocationType())){
				billingSummaryDetailsFilterVO.setCountry(form.getLocation().toUpperCase()) ;
			}
			else{
				billingSummaryDetailsFilterVO.setStationCode(form.getLocation().toUpperCase());
			}
		}		
		billingSummaryDetailsFilterVO.setFromDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false).setDate(form.getFromDate())));
		billingSummaryDetailsFilterVO.setToDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false).setDate(form.getToDate())));
		getReportSpec().addExtraInfo(oneTimMap.get(MAIL_CATEGORY));  
		getReportSpec().addExtraInfo(oneTimMap.get(MAIL_CLASS));
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(PRODUCT_NAME);
		getReportSpec().setSubProductCode(SUBPRODUCT_NAME);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE);
		getReportSpec().addFilterValue(billingSummaryDetailsFilterVO);
		getReportSpec().addParameter(billingSummaryDetailsFilterVO);
		getReportSpec().setPreview(true);
		getReportSpec().setAction(ACTION);
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		invocationContext.target = getTargetPage();

	}
	private Collection<ErrorVO> validateForm(POMailSummaryForm form) {
		Collection<ErrorVO> errorVOs= new ArrayList<ErrorVO>();
		if(form.getFromDate()==null || form.getFromDate().trim().length()<=0){
			errorVOs.add(new ErrorVO(FROMDATE_MANDATORY));
		}
		if(form.getFromDate()==null || form.getFromDate().trim().length()<=0){
			errorVOs.add(new ErrorVO(TODATE_MANDATORY));
		}
		if(form.getFromDate()!=null && form.getFromDate().trim().length()>0 && form.getToDate()!=null && form.getToDate().trim().length()>0){
			if(DateUtilities.getDifferenceInMonths(form.getFromDate(),form.getToDate())> 6){
				errorVOs.add(new ErrorVO(DATERANGEEXCEEDS));
			}
		}
		return errorVOs;
	}

}
