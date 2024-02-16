/*
 * ExceptionReportDetailPrintCommand.java Created on March 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
* @author Indu
* Command class for ExceptionReportDetailPrintCommand.
*
* Revision History
*
* Version      Date           Author          	 Description
*
*  0.1 	 Mar 1, 2007		Indu   		Initial draft
*
*/
public class ExceptionReportDetailPrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MTK_MRA_AIRLINEBILLING");
	/*
	 * String constants
	 */	
	private static final String REPORT_ID = "RPTMRA021";
	private static final String RESOURCE_BUNDLE_KEY = "airlineExceptions";
	private static final String BLANK = "";
	/*
	 * 
	 * Target mappings for forward action
	 */
	private static final String ACTION = "printExceptionReportDetail";
    private static final String PRINT_FAILURE= "normal-report-error-jsp";
	private static final String CLASS_NAME= "PrintCommand";
	private static final String MEMO_STATUS = "mra.airlinebilling.billingstatus";	
	
	/**
	 * 
	 * A-2391	
	 * The execute method for ExceptionReportDetailPrintCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(
	 * com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
    
		log.entering(CLASS_NAME,"execute");
    	/*
    	 * Obtaining form 
    	 */
		AirlineExceptionsForm airlineExceptionsForm = 
			(AirlineExceptionsForm)invocationContext.screenModel;
		String cmpCod = getApplicationSession().getLogonVO().getCompanyCode();
		AirlineExceptionsFilterVO filterVO = populateFilter(airlineExceptionsForm);
		filterVO.setCompanyCode(cmpCod);
		log.log(Log.FINE, "filterVO", filterVO);
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> memoStatus = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			memoStatus = oneTimes.get(MEMO_STATUS);
			log.log(Log.INFO, "memoStatus++", memoStatus);
		}
	   
       /*
        * 
        * populating ReportSpec attributes
        */
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(airlineExceptionsForm.getProduct());
		getReportSpec().setSubProductCode(airlineExceptionsForm.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addExtraInfo(oneTimes);
		getReportSpec().addFilterValue(filterVO);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().setAction(ACTION);
		log.log(Log.INFO,"before genReport()");
		generateReport();
		if (getErrors()!=null && getErrors().size()>0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_FAILURE;
			return;
		}
		
		invocationContext.target = getTargetPage();
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * populates the filterVO from form fields
	 * 
	 * @param form
	 * @return
	 */
	private AirlineExceptionsFilterVO populateFilter(AirlineExceptionsForm form){
		
		log.entering(CLASS_NAME, "populateFilter");
		AirlineExceptionsFilterVO filterVO = new AirlineExceptionsFilterVO();
		filterVO.setFrmDate(form.getFromDate());
		filterVO.setToDat(form.getToDate());
		filterVO.setFromDate(convertToDate(form.getFromDate()));
		filterVO.setToDate(convertToDate(form.getToDate()));
		filterVO.setAirlineCode(form.getAirlineCode().toUpperCase());
		
		String expCod = form.getExceptionCode();
		if( !BLANK.equals(expCod)){
			
			filterVO.setExceptionCode(expCod);
		}
		
		String dsn = form.getDsn();
		if( !BLANK.equals(dsn)){
			
			filterVO.setDespatchSerNo(dsn);
		}
		
		String invNo = form.getInvoiceRefNo();
		if( !BLANK.equals(invNo)){
			
			filterVO.setInvoiceRefNumber(invNo);
		}
		log.exiting(CLASS_NAME, "populateFilter");
		return filterVO;
	}
	/**
	 * Converts date format string to LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date );
		}
		return null;
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

			fieldValues.add(MEMO_STATUS);
		
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
