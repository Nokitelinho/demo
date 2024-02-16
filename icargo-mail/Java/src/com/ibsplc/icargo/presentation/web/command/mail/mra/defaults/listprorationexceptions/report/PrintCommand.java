/*
 * PrintCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information
 *  of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions.report;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;



/**
 * @author A-3108
 *
 */
public class PrintCommand extends AbstractPrintCommand{
	
	/**
	 * Logger and the file name
	 */
	private  Log log = LogFactory.getLogger("MRA PRORATION");
	private static final String CLASS_NAME = "PrintCommand";	
	private static final String REPORT_ID = "RPTLST292";
	private static final String RESOURCE_BUNDLE_KEY = "mralistprorationexceptions";	
	private static final String PRINT_FAILURE = "normal-report-error-jsp";	
	private static final String ACTION = "printProrationExceptionReport";
	private static final String INVALID_FROMDATE=
		"mra.proration.listexceptions.msg.err.invalidfromdate";
	private static final String INVALID_TODATE=
		"mra.proration.listexceptions.msg.err.invalidtodate";
	private static final String INVALID_DATE=
		"mra.proration.listexceptions.msg.err.invaliddate";
	private static final String FROM_DATEEARLIER= "mra.proration.listexceptions.msg.err.fromdateearliertodate";
	/**
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListMailProrationExceptionsForm listForm = 
			(ListMailProrationExceptionsForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		ProrationExceptionsFilterVO filterVO = new ProrationExceptionsFilterVO();
		
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setExceptionCode(listForm.getExceptionCode());
		filterVO.setCarrierCode(listForm.getCarrierCode());
		filterVO.setFlightNumber(listForm.getFlightNumber());
		filterVO.setSegmentOrigin(listForm.getOrigin());
		filterVO.setSegmentDestination(listForm.getDestination());		
		filterVO.setDispatchNo(listForm.getDispatchNo());		
		filterVO.setStatus(listForm.getStatus());
		filterVO.setAssignedStatus(listForm.getAssignedStatus());
		filterVO.setAsignee(listForm.getAsignee());
		
		
		
		LocalDate localDate=new LocalDate
		(LocalDate.NO_STATION,Location.NONE, false);
		if(listForm.getFromDate().trim().length()!=0){
			if(!DateUtilities.isValidDate(listForm.getFromDate(),
					logonAttributes.getDateFormat())){
				Object[] obj={listForm.getFromDate()};
				ErrorVO error=new ErrorVO(INVALID_FROMDATE,obj);
				errors.add(error);
			}else{
				filterVO.setFromDate(localDate.setDate
						(listForm.getFromDate()));
			}
		}else{
			filterVO.setFromDate(null);
		}
		
		localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		if(listForm.getFlightDate().trim().length()!=0){
			if(!DateUtilities.isValidDate(listForm.getFlightDate(),
					logonAttributes.getDateFormat())){
				Object[] obj={listForm.getFlightDate()};
				ErrorVO error=new ErrorVO(INVALID_DATE,obj);
				errors.add(error);
			}else{
				filterVO.setFlightDate(localDate.setDate
						(listForm.getFlightDate()));
			}
		}else{
			filterVO.setFlightDate(null);
		}
		
		/*
		 * Setting ToDateRange
		 */
		localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		if(listForm.getToDate().trim().length()!=0){
			if(!DateUtilities.isValidDate(listForm.getToDate(),
					logonAttributes.getDateFormat())){
				Object[] obj={listForm.getToDate()};
				ErrorVO error=new ErrorVO(INVALID_TODATE,obj);
				errors.add(error);
			}else{
				filterVO.setToDate(localDate.setDate
						(listForm.getToDate()));
			}
		}else{
			filterVO.setToDate(null);
		}
		
		if ((filterVO.getFromDate() != null) && (!("").equals(filterVO.getFromDate().toString()))&&
				filterVO.getToDate() != null && ((!("").equals(filterVO.getToDate().toString())))){
		if(filterVO.getFromDate().isGreaterThan
				(filterVO.getToDate())){
			ErrorVO errorVO = new ErrorVO
			(FROM_DATEEARLIER);
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);
		}
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			log.log(Log.INFO, "****************errors", errors);
			invocationContext.target=PRINT_FAILURE;
			return;
		}
		
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(listForm.getProduct());
		getReportSpec().setSubProductCode(listForm.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(filterVO);
		log.log(Log.FINE, "FilterVO............", filterVO);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().setAction(ACTION);		
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			
	log.log(Log.FINE, "Errors are returned............", getErrors().size());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_FAILURE;
			log.log(Log.FINE,"Errors are returned............sw");
			return;
		}
		log.exiting("CRA_Proration","PrintCommand exit");
		invocationContext.target = getTargetPage();
		
		
		
		
	}
	
}
