/*
 * PrintCommand.java Created on Feb-03,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *  @author A-2458
 */
public class PrintCommand  extends AbstractPrintCommand
{
	private static final String CLASS_NAME = "printCommand";
	/**
	 * Log for EndValidationCommand
	 */
	private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
	/**
	 * Strings for SCREEN_ID and MODULE_NAME
	 */
	private static final String REPORT_ID = "RPTMRA015";
	private static final String INVREFNUM_MANDATORY="mailtracking.mra.airlinebilling.msg.err.invrefnomandatory";
	private static final String CLEARANCEPERIOD_MANDATORY="mailtracking.mra.airlinebilling.msg.err.clearanceperiodmandatory";
	private static final String AIRLINECODE_MANDATORY="mailtracking.mra.airlinebilling.msg.err.airlinecodemandatory";
	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";
	private static final String RESOURCE_BUNDLE_KEY = "capturecn66";
	private static final String PRINT_FAILURE="normal-report-error-jsp";
	private static final String ACTION = "findCN66DetailsPrint";


	/**
	 *
	 * @param invocationContext
	 * @return void
	 * @exception CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		AirlineCN66DetailsFilterVO filterVO=new AirlineCN66DetailsFilterVO();
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		ApplicationSessionImpl applicationSessionImpl =getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error=null;
		String invoiceRefNo=form.getInvoiceRefNo();
		String clearancePeriod=form.getClearancePeriod();
		String airlineCode=form.getAirlineCode();
		String companyCode = logonAttributes.getCompanyCode();
    	filterVO.setCompanyCode(companyCode);
    	if(form.getBillingType()!=null && form.getBillingType().trim().length()>0){
    	filterVO.setInterlineBillingType(form.getBillingType().trim().toUpperCase());
    	}
    	if(invoiceRefNo!=null && invoiceRefNo.length()>0){
			filterVO.setInvoiceRefNumber(invoiceRefNo.toUpperCase());
		}
		else{
			error=new ErrorVO(INVREFNUM_MANDATORY);
 			errors.add(error);
		}
		if(clearancePeriod!=null && clearancePeriod.length()>0){
			/*filterVO.setClearancePeriod(clearancePeriod.toUpperCase());

			UPUCalendarVO upuCalendarVO = new UPUCalendarVO();
			try{

			upuCalendarVO =delegate.validateIataClearancePeriod(companyCode,
						 clearancePeriod);
			 }catch(BusinessDelegateException businessDelegateException){
				 errors=handleDelegateException(businessDelegateException);

			 }
			 log.log(1,"upuCalendarVO-------->"+upuCalendarVO);	*/
			log.log(log.INFO,"inside ClearancePeriod");
			 IATACalendarVO   iatacalendarvo = null;
	            IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
	            iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
	            iatacalendarfiltervo.setClearancePeriod(clearancePeriod);
	            try{

				iatacalendarvo = delegate.validateClearancePeriod(iatacalendarfiltervo);
				  log.log(Log.INFO, "iatacalendarvo obtained", iatacalendarvo);
	            } catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}

	            if(iatacalendarvo!=null ){
	            	filterVO.setClearancePeriod(clearancePeriod);

	            	}
	            else{
            	 	log.log(log.INFO,"iatacalendarvo null");
	            	ErrorVO err=new ErrorVO(CLRPRD_INVALID);
	    			err.setErrorDisplayType(ErrorDisplayType.ERROR);
	    			errors.add(err);
            }
		}
		else{
			error=new ErrorVO(CLEARANCEPERIOD_MANDATORY);
 			errors.add(error);
		}
		if(airlineCode!=null && airlineCode.length()>0){
			filterVO.setAirlineCode(airlineCode);
			try{
			AirlineValidationVO airlineValidationVO=new AirlineDelegate()
			.validateAlphaCode(companyCode,airlineCode.toUpperCase());
			filterVO.setAirlineId(airlineValidationVO.getAirlineIdentifier());
			}
			catch(BusinessDelegateException businessDelegateException){
				errors=handleDelegateException(businessDelegateException);
			}
		}
		else{
			error=new ErrorVO(AIRLINECODE_MANDATORY);
 			errors.add(error);
		}
		if (form.getCategory() != null && form.getCategory().trim().length() > 0) {
				filterVO.setCategory(form.getCategory().toUpperCase());
		}
		if (form.getCarriageFromFilter() != null && form.getCarriageFromFilter().trim().length() > 0) {
				filterVO.setCarriageFrom(form.getCarriageFromFilter().toUpperCase().trim());
		}
		if (form.getCarriageToFilter() != null && form.getCarriageToFilter().trim().length() > 0) {
				filterVO.setCarriageTo(form.getCarriageToFilter().trim().toUpperCase().trim());
		}
		if (form.getDespatchStatusFilter() != null && form.getDespatchStatusFilter().trim().length() > 0) {
				filterVO.setDespatchStatus(form.getDespatchStatusFilter().toUpperCase());
		}

		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target=PRINT_FAILURE;
			log.exiting("MAILTRACKING_LIST","PrintCommand exit");
			return;
		}
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(form.getProduct());
		getReportSpec().setSubProductCode(form.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(filterVO);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().setAction(ACTION);

		generateReport();
		if(getErrors()!= null && getErrors().size()>0 )
		{
			invocationContext.addAllError(getErrors());
			log.log(Log.INFO, "****************errors", getErrors());
			invocationContext.target=PRINT_FAILURE;
		return;
		}
		log.exiting("MRA_AIRLINEBILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
	  }
}









