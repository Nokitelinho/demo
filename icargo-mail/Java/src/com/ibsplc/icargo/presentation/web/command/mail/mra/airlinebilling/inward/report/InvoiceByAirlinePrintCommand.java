/*
 * InvoiceByAirlinePrintCommand.java Created on March 16, 200
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.report;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.report.InwardBillingReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-2521
 * Revision History
 * 
 * Version      Date           		Author            
 * 
 *  0.1         March 16, 2007   	A-2521    		
 */
public class InvoiceByAirlinePrintCommand extends AbstractPrintCommand {
	/**
	 * 
	 * report id
	 */
	private static final String REPORT_ID = "RPTMRA020";
	/**
	 * bundle
	 */
	private static final String RESOURCE_BUNDLE_KEY = "inwardreport";
	/**
	 * 
	 *  
	 *  For setting the Target action
	 */
	private static final String PRINT_FAILURE= "normal-report-error-jsp";
	
	private static final String ACTION = "generateInvoiceByAirline";	
	
	private static final String ACTION_FAILURE= "action_failure";
	
	private static final String BLANK = "";	
//	private static final String DATEFORMAT = "dd-MMM-yyyy";
//	private static final String MANDATORY_DATE = "cra.flown.msg.err.mandatoryfromdateortodate";
//	private static final String ORG_DST_SAME = "cra.flown.msg.err.originanddestnsame";
//	private static final String INVALID_DATE = "cra.flown.msg.err.invaliddate";
	/**
	 
	 * @param invocationContext
	 
	 * @throws CommandInvocationException
	 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		InwardBillingReportForm form = (InwardBillingReportForm)invocationContext.screenModel;
		AirlineCN51FilterVO filterVO = new AirlineCN51FilterVO();
		Collection<ErrorVO> errors = null;
				
		errors = validateForm(form, filterVO);
		
		if(errors != null && errors.size() > 0){
			
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_FAILURE;
			return;
		}
			
		ReportSpec reportSpec = getReportSpec();
		
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setAction(ACTION);
		generateReport();
		
		if(getErrors()!= null && getErrors().size() > 0) {
		
			invocationContext.addAllError(getErrors());			
			invocationContext.target = PRINT_FAILURE;
			return;
		}
		
		invocationContext.target = getTargetPage();
	}
	
	/**
	 * 
	 * @param form
	 * @param filterVO
	 * @return
	 */
	private Collection<ErrorVO> validateForm(
			InwardBillingReportForm form, AirlineCN51FilterVO filterVO){
		
		Collection<ErrorVO> errors = null;
		AirlineValidationVO airlineValidationVO = null;
		String validationTemp = null;
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		
		filterVO.setCompanyCode(companyCode);
		filterVO.setInterlineBillingType(form.getBillingType());
		
		validationTemp = form.getRpt020FromDate();
		if(!BLANK.equals(validationTemp)){
			
			filterVO.setFromDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
					setDate( validationTemp ));
		}
		
		validationTemp = form.getRpt020ToDate();
		if(!BLANK.equals(validationTemp)){
			
			filterVO.setToDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
					setDate( validationTemp ));
		}
		
		validationTemp = form.getRpt020AirlineCode();
		if(!BLANK.equals(validationTemp)){
			
			try {
				validationTemp = validationTemp.toUpperCase();
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						companyCode, validationTemp);
				filterVO.setAirlineCode(validationTemp);
				filterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
				
			} catch (BusinessDelegateException e) {
				return handleDelegateException(e);
			}
			
		}
		
		validationTemp = form.getRpt020AirlineNum();
		if(!BLANK.equals(validationTemp)){
			
			try {
				
				airlineValidationVO = new AirlineDelegate().validateNumericCode(
						companyCode, validationTemp);
				filterVO.setAirlineNumber(Integer.parseInt(validationTemp));
				filterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
				
			} catch (BusinessDelegateException e) {
				
				return handleDelegateException(e);
			}
			
		}
		
		return null;
	}
	
}
