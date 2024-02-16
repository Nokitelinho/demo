/*
 * PrintListOfFlightsCommand.java Created on Feb 28, 2007 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.report;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.report.FlownReportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */
public class PrintListOfFlightsCommand extends AbstractPrintCommand{
	
	private static final String REPORT_ID = "RPTMRA029";
	
	private static final String RESOURCE_BUNDLE_KEY = "flownreportsresources";
	
	private static final String ACTION = "listofflightswithstatus";
	
	private static final String INVALID_DATE_RANGE = 
		"mra.flown.report.msg.err.invaliddaterange";
	
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		Log log = LogFactory.getLogger("CRA Flown");
		
		log.entering("PrintListOfFlightCommand", "execute");
		
		FlownReportsForm form = (FlownReportsForm)invocationContext.screenModel;
		FlownMailFilterVO filterVO = new FlownMailFilterVO(); 
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		
		String companyCode = logonAttributes.getCompanyCode();
		
		
		
		filterVO.setCompanyCode(companyCode);
		log.log(1,"from date from form----->"+form.getFromDateForListOfFlights());
		if(form.getFromDateForListOfFlights() != null && !("").equals(form.getFromDateForListOfFlights())){
			LocalDate flightDate = new LocalDate(NO_STATION,NONE,false);
			flightDate.setDate(form.getFromDateForListOfFlights());
			filterVO.setFromDate(flightDate);
		}
		
		log.log(1,"to date from form----->"+form.getTodateForListOfFlights());
		if(form.getTodateForListOfFlights() != null && !("").equals(form.getTodateForListOfFlights())){
			LocalDate flightDate = new LocalDate(NO_STATION,NONE,false);
			flightDate.setDate(form.getTodateForListOfFlights());
			filterVO.setToDate(flightDate);
		}
		
		if(form.getCarrierCodeForListOfFlights() != null && 
				form.getCarrierCodeForListOfFlights().trim().length() > 0) {
			filterVO.setFlightCarrierCode(form.getCarrierCodeForListOfFlights().trim().toUpperCase());
		}
		
		if(form.getFlightNumberForListOfFlights() != null &&
				form.getFlightNumberForListOfFlights().trim().length() > 0) {
			filterVO.setFlightNumber(form.getFlightNumberForListOfFlights());
		}
		
		if(form.getFlightStatus() != null)
		 {
			filterVO.setFlightStatus(form.getFlightStatus()); //To be added to the VO...
		}
		
		log.log(1,"\n\n FilterVO set---------->"+filterVO);
		
		
		if(filterVO.getFlightCarrierCode() != null && filterVO.getFlightCarrierCode().trim().length() > 0){
			AirlineValidationVO validationVO = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			try{
				validationVO = airlineDelegate.validateAlphaCode(companyCode,filterVO.getFlightCarrierCode().trim().toUpperCase());
			}catch(BusinessDelegateException ex){
				log.log(Log.FINE,"inside busDelegateExc");
				errors =  handleDelegateException(ex);
			}
			log.log(1,"validationVO-------->"+validationVO);	
		}
		
		if(filterVO.getToDate() != null && filterVO.getFromDate() != null){
			if(filterVO.getToDate().before(filterVO.getFromDate())){
				log.log(log.FINE,"To date is earlier than from date");
				ErrorVO errorVO = new ErrorVO(INVALID_DATE_RANGE);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
		}
		
		
		
		if(errors!=null && errors.size() > 0){
			invocationContext.addAllError(errors);
			log.log(Log.INFO,"****************errors"+errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;	
		}
		else{		
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setProductCode(form.getProduct());
			getReportSpec().setSubProductCode(form.getSubProduct());
			getReportSpec().setPreview(true);
			getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
			getReportSpec().addFilterValue(filterVO);
			getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
			getReportSpec().setAction(ACTION);
			generateReport();
			if(getErrors() != null){
				log.log(1,"Exception occured!!");
				invocationContext.addAllError(getErrors());
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}						
		}
		
		log.exiting("PrintListOfFlightsCommand", "execute");
		invocationContext.target = getTargetPage();
		
	}
}
