/*
 * PrintCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions.report;



import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class PrintCommand extends AbstractPrintCommand{

	/**
	 * String for CLASS_NAME
	 */
	private static final String CLASS_NAME = "printCommand";
	/**
	 * Log for EndValidationCommand
	 */
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String REPORT_ID = "RPTMRA032";
	private static final String RESOURCE_BUNDLE_KEY = "assignexceptions";	
	private static final String PRINT_FAILURE = "normal-report-error-jsp";	
	private static final String ACTION = "findFlownMailExceptionsforprint";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
    	log.entering(CLASS_NAME,"execute");
    	AssignExceptionsForm assignExceptionsForm = 
			(AssignExceptionsForm)invocationContext.screenModel;
    	FlownMailFilterVO flownMailFilterVO = getFilterDetails(assignExceptionsForm,invocationContext);
    	getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(assignExceptionsForm.getProduct());
		getReportSpec().setSubProductCode(assignExceptionsForm.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(flownMailFilterVO);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().setAction(ACTION);		
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){			
			log.log(Log.INFO, "Errors are returned............", getErrors().size());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_FAILURE;
			log.log(log.FINE,"Errors are returned............sw");
			return;
		}
		log.exiting(CLASS_NAME,"PrintCommand exit");
		invocationContext.target = getTargetPage();
		}
	
	/**
	 * 
	 * @param assignExceptionsForm
	 * 
	 * 
	 */
	public FlownMailFilterVO getFilterDetails(AssignExceptionsForm assignExceptionsForm,
			InvocationContext invocationContext){
		log.entering(CLASS_NAME, "updateFilterDetails");
		/**
		 * 
		 * updating session from form
		 */
		FlownMailFilterVO flownMailFilterVO = 
			new FlownMailFilterVO();
		
		if(assignExceptionsForm.getFlightCarrierCode() != null && 
			assignExceptionsForm.getFlightCarrierCode().trim().length() > 0){
			flownMailFilterVO.setFlightCarrierCode(
					assignExceptionsForm.getFlightCarrierCode().toUpperCase());
	    }
		
		if(assignExceptionsForm.getFlightNumber() != null && 
			assignExceptionsForm.getFlightNumber().trim().length() > 0){
			flownMailFilterVO.setFlightNumber(
					assignExceptionsForm.getFlightNumber().toUpperCase());
	    }
		
		if(assignExceptionsForm.getFromDate() != null && 
				assignExceptionsForm.getFromDate().trim().length() > 0){
			LocalDate fromDate = new LocalDate(
					NO_STATION,NONE,false);
	   		fromDate.setDate(assignExceptionsForm.getFromDate());
	   		flownMailFilterVO.setFromDate(fromDate);
				
		}
		if(assignExceptionsForm.getToDate() != null && 
				assignExceptionsForm.getToDate().trim().length() > 0){
			LocalDate toDate = new LocalDate(
					NO_STATION,NONE,false);
			toDate.setDate(assignExceptionsForm.getToDate());
			flownMailFilterVO.setToDate(toDate);
		}
		log.log(Log.FINE, "**********", assignExceptionsForm.getExceptionCodeForPrint());
		if(assignExceptionsForm.getExceptionCode() != null && 
			assignExceptionsForm.getExceptionCode().trim().length() > 0){
			flownMailFilterVO.setExceptionCode(
					assignExceptionsForm.getExceptionCodeForPrint().toUpperCase());
	    }
		
		if(assignExceptionsForm.getAssignee() != null && 
			assignExceptionsForm.getAssignee().trim().length() > 0){
			flownMailFilterVO.setAssigneeCode(
					assignExceptionsForm.getAssignee().toUpperCase());
	    }
		
		if(assignExceptionsForm.getAssignedDate() != null && 
				assignExceptionsForm.getAssignedDate().trim().length() > 0){
			LocalDate assignedDate = new LocalDate(
					NO_STATION,NONE,false);
			assignedDate.setDate(assignExceptionsForm.getAssignedDate());
			flownMailFilterVO.setAssignedDate(assignedDate);
		}
		if(assignExceptionsForm.getSegmentOrigin() != null && 
				assignExceptionsForm.getSegmentOrigin().trim().length() > 0){
			flownMailFilterVO.setSegmentOrigin(
						assignExceptionsForm.getSegmentOrigin().toUpperCase());
		    }
		if(assignExceptionsForm.getSegmentDestination() != null && 
				assignExceptionsForm.getSegmentDestination().trim().length() > 0){
				flownMailFilterVO.setSegmentDestination(
						assignExceptionsForm.getSegmentDestination().toUpperCase());
		    }
		
				
		flownMailFilterVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		
		
		AirlineValidationVO airlineVO = null;
 		if(assignExceptionsForm.getFlightCarrierCode() != null
 				&& assignExceptionsForm.getFlightCarrierCode().trim().length()
 				> 0) {
 					log.log(Log.FINE, "!!!!!!!!!!!!!!!!!!!!!!! INSIDE IF.",
							assignExceptionsForm.getFlightCarrierCode());
					Collection<ErrorVO> errorsAirline = null;
 					try {
 						airlineVO = new AirlineDelegate().validateAlphaCode(
 								getApplicationSession().getLogonVO().getCompanyCode()
 								,(assignExceptionsForm.getFlightCarrierCode()).toUpperCase());
 					}
 					catch(BusinessDelegateException businessDelegateException){
 						errorsAirline = handleDelegateException(
 								businessDelegateException);
 	       			}
 					if(errorsAirline != null && errorsAirline.size() > 0){
 						invocationContext.addAllError(errorsAirline);
 					}
 					
 		}

 		if(airlineVO != null) {

 			int airlineIdentifier=airlineVO.getAirlineIdentifier();
 			log.log(Log.INFO, "!!!!!!!!!!!!!!!!!airlineIdentifier!!!!!!!!!!",
					airlineIdentifier);
			flownMailFilterVO.setFlightCarrierId(airlineIdentifier);
 			log.log(Log.INFO, "!!!!!!!!!!!!!!!!airlineIdentifier!!!!!!!!!!!!",
					flownMailFilterVO.getFlightCarrierId());
 		}
 		log.log(Log.FINE, "**********", flownMailFilterVO);
		return flownMailFilterVO;
	}
	
	
}
