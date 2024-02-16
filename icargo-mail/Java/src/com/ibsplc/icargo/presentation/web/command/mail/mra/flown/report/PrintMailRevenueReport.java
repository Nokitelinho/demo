/*
 * PrintMailRevenueReport.java Created on Jun 21, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.report;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.report.FlownReportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2270
 * @author Sandeep.T
 * 
 */
public class PrintMailRevenueReport  extends BaseCommand {
	
	private static final String INVALID_DATE_RANGE = 
		"mra.flown.report.msg.err.invaliddaterange";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		FlownReportsForm flownReportForm = (FlownReportsForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if (flownReportForm.getAccountMonth()== null
				|| flownReportForm
						.getAccountMonth()
						.trim().length() < 1) {
			ErrorVO errorVO = new ErrorVO(
					"mra.flown.mailRevenueReport.accountingMonthMandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "mailRevenueReport_success";
			return;
			
		}
		FlownMailFilterVO flownFilterVO = new FlownMailFilterVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		flownFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
				if (flownReportForm.getAccountMonth() != null
				&& flownReportForm
						.getAccountMonth()
						.trim().length() > 0) {
			flownFilterVO.setAccountingMonth(flownReportForm
					.getAccountMonth().substring(0, 3));
			flownFilterVO.setAccountingYear(flownReportForm
					.getAccountMonth()
					.substring(3, 7));
		}
				if(flownReportForm.getFromDateForRevReport() != null && !("").equals(flownReportForm.getFromDateForRevReport())){
					LocalDate flightDate = new LocalDate(NO_STATION,NONE,false);
					flightDate.setDate(flownReportForm.getFromDateForRevReport());
					flownFilterVO.setFromDate(flightDate);
				}
				if(flownReportForm.getToDateForRevReport() != null && !("").equals(flownReportForm.getToDateForRevReport())){
					LocalDate flightDate = new LocalDate(NO_STATION,NONE,false);
					flightDate.setDate(flownReportForm.getToDateForRevReport());
					flownFilterVO.setToDate(flightDate);
				}
				if(flownReportForm.getCarrierCodeForRevReport() != null && 
						flownReportForm.getCarrierCodeForRevReport().trim().length() > 0) {
					flownFilterVO.setFlightCarrierCode(flownReportForm.getCarrierCodeForRevReport().trim().toUpperCase());
				}
				
				if(flownReportForm.getFlightNumberForRevReport() != null &&
						flownReportForm.getFlightNumberForRevReport().trim().length() > 0) {
					flownFilterVO.setFlightNumber(flownReportForm.getFlightNumberForRevReport());
				}
				
				if(flownFilterVO.getToDate() != null && flownFilterVO.getFromDate() != null){
					if(flownFilterVO.getToDate().before(flownFilterVO.getFromDate())){
						
						ErrorVO errorVO = new ErrorVO(INVALID_DATE_RANGE);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
					}
				}
				
				if(errors!=null && errors.size() > 0){
					invocationContext.addAllError(errors);
					invocationContext.target = "printMailRevenueReport_success";
					return;	
				}else{	
					try {
						new MailTrackingMRADelegate().printMailRevenueReport(flownFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
					}
					
					if(errors != null && errors.size() > 0 ){
			    		invocationContext.target = "printMailRevenueReport_success";
			    		invocationContext.addAllError(errors);
			    		return;
			    	}
			    	
			    	else{
			    		ErrorVO error = new ErrorVO("mra.flown.mailRevenueReport.repgensuccess" );
			    		
						errors.add(error);
						
						invocationContext.addAllError(errors);
						
			    	}
	          }
		     invocationContext.target = "printMailRevenueReport_success";

	}

}

