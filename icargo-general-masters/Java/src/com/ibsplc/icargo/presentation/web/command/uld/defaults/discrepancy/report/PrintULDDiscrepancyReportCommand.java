/*
 * PrintULDDiscrepancyReportCommand.java Created on Mar 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ULDDiscrepancyReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class PrintULDDiscrepancyReportCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTLST210";
	private Log log = LogFactory.getLogger("ULD Discrepancy Report");	
	private static final String BLANK = "";
	private static final String RESOURCE_BUNDLE_KEY = "ulddiscrepancyreport";	
	private static final String ACTION = "generateULDDiscrepancyReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
     */
	public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {
		log.entering("PrintULDDiscrepancyReportCommand","GenerateCommand entry");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		ULDDiscrepancyReportForm uldDiscrepancyReportForm = 
			(ULDDiscrepancyReportForm) invocationContext.screenModel;		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributesVO.getCompanyCode();
		AirlineValidationVO airlineValidationVO = null;
		ULDValidationVO uldValidationVO = null;
		//Commented by Manaf for INT ULD510
		//ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		LocalDate fromDate = new LocalDate(logonAttributesVO.
				getAirportCode(),Location.ARP,false);
		LocalDate toDate = new LocalDate(logonAttributesVO.
				getAirportCode(),Location.ARP,false);
		ULDDiscrepancyFilterVO uldDiscrepancyFilterVO = new ULDDiscrepancyFilterVO();		
		uldDiscrepancyFilterVO.setCompanyCode(logonAttributesVO.
				getCompanyCode());	
		if(uldDiscrepancyReportForm.getFromDate()!= null && !BLANK.equals
						(uldDiscrepancyReportForm.getFromDate())){
			uldDiscrepancyFilterVO.setFromDate(fromDate.
					setDate(uldDiscrepancyReportForm.getFromDate()));
		}
		if(uldDiscrepancyReportForm.getToDate()!= null && !BLANK.equals
						(uldDiscrepancyReportForm.getToDate())){
			uldDiscrepancyFilterVO.setTodate(toDate.
					setDate(uldDiscrepancyReportForm.getToDate()));
		}
		if(uldDiscrepancyReportForm.getUldNumber()!= null && !BLANK.equals
						(uldDiscrepancyReportForm.getUldNumber())){	
			uldDiscrepancyFilterVO.setUldNumber(uldDiscrepancyReportForm.
					getUldNumber().toUpperCase());
		}
		else
		{
			uldDiscrepancyFilterVO.setUldNumber("");
		}		
		if(uldDiscrepancyReportForm.getAirlineCode()!= null && !BLANK.equals
						(uldDiscrepancyReportForm.getAirlineCode())){
			uldDiscrepancyFilterVO.setAirlineCode(uldDiscrepancyReportForm.
					getAirlineCode().toUpperCase());
		}
		else
		{
			uldDiscrepancyFilterVO.setAirlineCode("");
		}
		if(uldDiscrepancyReportForm.getReportingAirportCode()!= null && !BLANK.equals
						(uldDiscrepancyReportForm.getReportingAirportCode())){
			uldDiscrepancyFilterVO.setReportingStation(uldDiscrepancyReportForm.
					getReportingAirportCode().toUpperCase());
		}
		else
		{
			uldDiscrepancyFilterVO.setReportingStation("");
		}
		if (uldDiscrepancyReportForm.getUldNumber() != null
				&& uldDiscrepancyReportForm.getUldNumber().trim().length() > 0) {
			errors = validateULDCode(uldDiscrepancyReportForm, companyCode);
			if (errors != null && errors.size() > 0) {	
				uldDiscrepancyReportForm.setUldNumber("");
				uldDiscrepancyFilterVO.setUldNumber("");
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}
		if (uldDiscrepancyReportForm.getAirlineCode() != null
				&& uldDiscrepancyReportForm.getAirlineCode().trim().length() > 0) {
			String airlineCode = uldDiscrepancyReportForm.getAirlineCode().toUpperCase();
			airlineValidationVO = validateAirlineCode(uldDiscrepancyReportForm, companyCode);
			if (airlineValidationVO == null) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				Object[] obj = { airlineCode };
				error = new ErrorVO(
						"uld.defaults.ulddiscrepancyreport.invalidawbprefix",
						obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				uldDiscrepancyReportForm.setAirlineCode("");
				uldDiscrepancyFilterVO.setAirlineCode("");
			}else{
				uldDiscrepancyFilterVO.setUldOwnerIdentifier(airlineValidationVO.getAirlineIdentifier());
			}
			if (errors != null && errors.size() > 0) {				
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}
		if (uldDiscrepancyReportForm.getReportingAirportCode() != null
				&& uldDiscrepancyReportForm.getReportingAirportCode().trim().length() > 0) {
			errors = validateReportingAirport(uldDiscrepancyReportForm, companyCode);
			if (errors != null && errors.size() > 0) {
				uldDiscrepancyReportForm.setReportingAirportCode("");
				uldDiscrepancyFilterVO.setOwnerStation("");				
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}				
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(uldDiscrepancyReportForm.getProduct());
		reportSpec.setSubProductCode(uldDiscrepancyReportForm.getSubProduct());
		reportSpec.setPreview(false);		
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldDiscrepancyFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);		
		generateReport();	
		log.exiting("PrintULDDiscrepancyReportCommand","GenerateCommand exit");
		invocationContext.target = getTargetPage();
	}
	
	private Collection<ErrorVO> validateReportingAirport(
			ULDDiscrepancyReportForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getReportingAirportCode()
					.toUpperCase());
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}

	private AirlineValidationVO validateAirlineCode(
			ULDDiscrepancyReportForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		try {
			AirlineDelegate delegate = new AirlineDelegate();
			airlineValidationVO = delegate.validateAlphaCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getAirlineCode()
					.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return airlineValidationVO;
	}
	
	private Collection<ErrorVO> validateULDCode(
			ULDDiscrepancyReportForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDValidationVO uldValidationVO = null;
		boolean flag = false;
		try {			
			delegate.validateULDFormat(companyCode,
					form.getUldNumber().toUpperCase());				
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}
}
