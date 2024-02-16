/*
 * OutwardBillingInvoicePrintCommand.java Created on March 19, 200
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;



import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.report.OutwardBillingReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 * Revision History
 * 
 * Version      Date           		Author            
 * 
 *  0.1         March 19, 2007   	A-2270    		
 */
public class OutwardBillingInvoicePrintCommand extends AbstractPrintCommand {
	/**
	 * 
	 * report id
	 */
	private static final String REPORT_ID = "RPTMRA014";
	/**
	 * bundle
	 */
	private static final String RESOURCE_BUNDLE_KEY = "outwardreport";
	/**
	 * 
	 *  
	 *  For setting the Target action
	 */
	private static final String PRINT_FAILURE= "normal-report-error-jsp";

	private static final String ACTION = "findInvoiceDetailsForReport";	

	private static final String CLEARANCEPERIOD_INVALID = "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";

	//private static final String ACTION_FAILURE= "action_failure";

	private static final String BLANK = "";	

	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	/**

	 * @param invocationContext

	 * @throws CommandInvocationException

	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		OutwardBillingReportForm form = (OutwardBillingReportForm)invocationContext.screenModel;
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
			OutwardBillingReportForm form, AirlineCN51FilterVO filterVO){
		MailTrackingMRADelegate mailTrackingMRADelegate =new MailTrackingMRADelegate();		
		Collection<ErrorVO> errors = null;
		AirlineValidationVO airlineValidationVO = null;
		String validationTemp = null;
		log.log(Log.INFO,"inside validate form");
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();

		filterVO.setCompanyCode(companyCode);
		filterVO.setInterlineBillingType(form.getBillingType());
		String invoiceNumber = form.getRpt014InvoiceNum();
		if(!BLANK.equals(invoiceNumber)){
			filterVO.setInvoiceReferenceNumber(form.getRpt014InvoiceNum().trim().toUpperCase());
		}
		validationTemp = form.getRpt014ClrPrd();
		if(!BLANK.equals(validationTemp)&& validationTemp.trim().length() > 0){

			IATACalendarVO iatacalendarvo = null;
			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
			iatacalendarfiltervo.setCompanyCode(companyCode);
			iatacalendarfiltervo.setClearancePeriod(validationTemp);
			if (validationTemp != null && validationTemp.trim().length() > 0) {
				try {

					iatacalendarvo = mailTrackingMRADelegate.validateClearancePeriod(iatacalendarfiltervo);
				}catch(BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);

				}
			}
			if (iatacalendarvo != null ) {

				filterVO.setIataClearancePeriod(form.getRpt014ClrPrd());

			} else {
				errors = new ArrayList<ErrorVO>();
				ErrorVO err = new ErrorVO(CLEARANCEPERIOD_INVALID);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				return errors;
			}
		}
		validationTemp = form.getRpt014AirlineCode();
		if(!BLANK.equals(validationTemp)){

			try {
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						companyCode, validationTemp);
				filterVO.setAirlineCode(validationTemp);
				filterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());

			} catch (BusinessDelegateException e) {

				return handleDelegateException(e);
			}

		}

		validationTemp = form.getRpt014AirlineCode();
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

