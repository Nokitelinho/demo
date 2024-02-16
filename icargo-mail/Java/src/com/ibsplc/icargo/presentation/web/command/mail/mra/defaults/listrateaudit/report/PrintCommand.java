/*
 * PrintCommand.java Created on June 9, 2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit.report;




import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 */
public class PrintCommand extends AbstractPrintCommand {
    /**
     * String for CLASS_NAME
     */
    private static final String CLASS_NAME = "printCommand";
    /**
     * Log for EndValidationCommand
     */
    private Log log = LogFactory.getLogger("MRA LISTRATEAUDIT");
    /**
     * Strings for SCREEN_ID and MODULE_NAME
     */
    private static final String REPORT_ID = "02MRA001";
    private static final String RESOURCE_BUNDLE_KEY = "mralistrateaudit";
    private static final String ACTION = "findRateAuditDetailsPrint";
    private static final String DSN_STATUS = "mailtracking.mra.defaults.rateaudit.status";
 
	
    public void execute(InvocationContext invocationContext) throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListRateAuditForm form=(ListRateAuditForm)invocationContext.screenModel;
		
		RateAuditFilterVO rateAuditFilterVO=new RateAuditFilterVO();
		rateAuditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		if(form.getDsn()!=null &&(form.getDsn()).trim().length()>0){

			rateAuditFilterVO.setDsn(form.getDsn());
		}
		if(form.getDsnDate()!=null &&(form.getDsnDate()).trim().length()>0){

			rateAuditFilterVO.setDsnDate(new LocalDate
			(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getDsnDate()));
		}
		if(form.getDsnStatus()!=null &&(form.getDsnStatus()).trim().length()>0){

			rateAuditFilterVO.setDsnStatus(form.getDsnStatus());

		}
		if(form.getGpaCode()!=null &&(form.getGpaCode()).trim().length()>0){

			rateAuditFilterVO.setGpaCode(form.getGpaCode());

		}
		if(form.getSubClass()!=null &&(form.getSubClass()).trim().length()>0){

			rateAuditFilterVO.setSubClass(form.getSubClass());

		}

		if(form.getFlightNo()!=null &&(form.getFlightNo()).trim().length()>0){
			rateAuditFilterVO.setFlightNumber(form.getFlightNo());
		}
		if(form.getFlightDate()!=null &&(form.getFlightDate()).length()>0){
			rateAuditFilterVO.setFlightDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getFlightDate())));
		}
		if(form.getFromDate()!=null &&(form.getFromDate()).trim().length()>0){
			rateAuditFilterVO.setFromDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getFromDate())));
		}
		if(form.getToDate()!=null &&(form.getToDate()).trim().length()>0){
			rateAuditFilterVO.setToDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getToDate())));
		}
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> dsnStatuses = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			dsnStatuses = oneTimes.get(DSN_STATUS);
			log.log(Log.INFO, "dsnStatuses..", dsnStatuses);
		}
			ReportSpec reportSpec = getReportSpec();
	        reportSpec.setReportId(REPORT_ID);
	        reportSpec.setProductCode(form.getProduct());
	        reportSpec.setSubProductCode(form.getSubProduct());
	        reportSpec.setPreview(true);
	        reportSpec.setHttpServerBase(invocationContext.httpServerBase);
	        reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
	        reportSpec.addExtraInfo(dsnStatuses);
	        reportSpec.addFilterValue(rateAuditFilterVO);
	        reportSpec.setAction(ACTION);
	        generateReport();
	        invocationContext.target = getTargetPage();
    }
    
    /**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(DSN_STATUS);
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}

}

