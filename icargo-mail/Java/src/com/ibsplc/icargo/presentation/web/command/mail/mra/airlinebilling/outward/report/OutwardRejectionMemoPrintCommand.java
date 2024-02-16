/*
 * OutwardRejectionMemoPrintCommand.java Created on Mar 16,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
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
 * @author A-2122
 *
 */
public class OutwardRejectionMemoPrintCommand extends AbstractPrintCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING");

	private static final String REPORT_ID = "RPTMRA040";

	private static final String RESOURCE_BUNDLE_KEY = "outwardreport";
	private static final String EMPTY = "";

	private static final String ACTION = "findOutwardRejectionMemo";

	private static final String CLEARANCEPERIOD_INVALID = "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";


	private static final String CLEARANCEPERIOD_NULL =
		"mailtracking.mra.airlinebilling.outward.outwardrejectionmemo.msg.err.clearanceperiodandairlinecodenull";

	/*private static final String AIRLINECODE_NULL =
		 "mailtracking.mra.airlinebilling.outward.outwardrejectionmemo.msg.err.airlinecodenull";*/



	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	/*
	 *  Execute method to generate Invoice report by airline
	 */

	/**
	 * @param invocationContext	 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("MAILTRACKING MRA AIRLINEBILLING","Print Command entered");
		OutwardBillingReportForm outwardBillingReportForm  = (OutwardBillingReportForm)invocationContext.screenModel;

		Collection<ErrorVO> errors = null;
		ErrorVO errorVO = null;
		boolean canGenerate = true;

		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String airlineCode = outwardBillingReportForm.getRpt040AirlineCode().trim().toUpperCase();	
		String airlineNumber = outwardBillingReportForm.getRpt040AirlineNum().trim().toUpperCase();
		String clearancePeriod = outwardBillingReportForm.getRpt040ClrPrd().trim().toUpperCase();	

		MemoFilterVO  memoFilterVO = new MemoFilterVO();	

		errors = validateForm(companyCode,airlineNumber,airlineCode,memoFilterVO);
		if(errors!= null && errors.size() >0){		
			invocationContext.addAllError(errors);			
		}

		errors = validateClearancePeriod(companyCode,clearancePeriod);		

		if(errors!= null && errors.size() >0){		
			invocationContext.addAllError(errors);			
		}
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size()>0){
			log.log(Log.FINE,"Errors");
			invocationContext.target=PRINT_UNSUCCESSFUL;
			return;

		}
		if((airlineCode!=null && !EMPTY.equals(airlineCode)) && 
				(clearancePeriod != null && !EMPTY.equals(clearancePeriod))){

			memoFilterVO.setCompanyCode(companyCode);
			memoFilterVO.setInterlineBillingType(outwardBillingReportForm.getBillingType());
			memoFilterVO.setAirlineCodeFilter(airlineCode);
			memoFilterVO.setClearancePeriod(clearancePeriod);			

			log.log(Log.FINE, "MemoFilterVO", memoFilterVO);

		}
		else{

			if((clearancePeriod == null || EMPTY.equals(clearancePeriod)) ||
					(airlineCode == null || EMPTY.equals(airlineCode))){
				log.log(Log.FINE,"Enter error due to null clrprd or null airline code");
				errorVO = new ErrorVO(CLEARANCEPERIOD_NULL);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target=PRINT_UNSUCCESSFUL;
				canGenerate = false;
				log.exiting("MAILTRACKING MRA AIRLINEBILLING","PrintCommand exit");
				return;
			}


			/*if(airlineCode == null || EMPTY.equals(airlineCode)){
				log.log(Log.FINE,"Enter error due to null to airlineCode");
				errorVO = new ErrorVO(AIRLINECODE_NULL);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target=PRINT_UNSUCCESSFUL;
				canGenerate = false;
				log.exiting("MAILTRACKING MRA AIRLINEBILLING","PrintCommand exit");
				return;
			}*/
		}
		if(canGenerate){
			log.log(Log.FINE,"no error");
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setProductCode(outwardBillingReportForm.getProduct());
			getReportSpec().setSubProductCode(outwardBillingReportForm.getSubProduct());
			getReportSpec().setPreview(true);
			getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
			getReportSpec().addFilterValue(memoFilterVO);
			getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
			getReportSpec().setAction(ACTION);

			generateReport();
			if(getErrors() != null && getErrors().size() > 0){
				invocationContext.addAllError(getErrors());
				log.log(Log.FINE, "Enter error", getErrors().size());
				invocationContext.target=PRINT_UNSUCCESSFUL;
				return;
			}
			log.exiting("MAILTRACKING MRA AIRLINEBILLING","PrintCommand exit");
			invocationContext.target = getTargetPage();
		}
	}

	private Collection<ErrorVO> validateForm(String companyCode, 
			String airlineNumber,String airlineCode,MemoFilterVO memoFilterVO){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		int airlineIdentifier=0;
		String airlineNo="";
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if(airlineCode != null && airlineCode.trim().length() > 0){ 
			try{
				airlineValidationVO = airlineDelegate.validateAlphaCode(companyCode,airlineCode);
				airlineIdentifier = airlineValidationVO.getAirlineIdentifier();
				airlineNo = airlineValidationVO.getNumericCode();

			}catch(BusinessDelegateException businessDelegateException){
				log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
				handleDelegateException(businessDelegateException);			
			}

			if(airlineValidationVO == null){
				ErrorVO error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.invalidairline");
				error.setErrorDisplayType(ERROR);
				errors.add(error);
			}
			else{	
				memoFilterVO.setAirlineIdentifier(airlineIdentifier);
				memoFilterVO.setAirlineNumber(airlineNo);
			}
		}			
		return errors;
	}

	private Collection<ErrorVO> validateClearancePeriod(String companyCode,
			String clearancePeriod) {
		MailTrackingMRADelegate mailTrackingMRADelegate =new MailTrackingMRADelegate();		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		IATACalendarVO iatacalendarvo = null;
		IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
		iatacalendarfiltervo.setCompanyCode(companyCode);
		iatacalendarfiltervo.setClearancePeriod(clearancePeriod);
		if (clearancePeriod != null && clearancePeriod.trim().length() > 0) {
			try {

				iatacalendarvo = mailTrackingMRADelegate
				.validateClearancePeriod(iatacalendarfiltervo);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
		}
		if (iatacalendarvo != null ) {
			log.log(Log.INFO, "iatacalendarvo not null ", iatacalendarvo);

		} else {
			log.log(log.INFO, "iatacalendarvo null--->");
			errors = new ArrayList<ErrorVO>();
			ErrorVO err = new ErrorVO(CLEARANCEPERIOD_INVALID);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}

		return errors;

	}

}

