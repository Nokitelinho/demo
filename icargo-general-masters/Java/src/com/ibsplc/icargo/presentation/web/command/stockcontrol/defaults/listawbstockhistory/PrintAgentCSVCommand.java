/*
 * PrintAgentCSVCommand.java Created on May 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listawbstockhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractExportCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2415
 *
 */
public class PrintAgentCSVCommand extends AbstractExportCommand {

	private static final String REPORT_ID = "RPTLST203";

	private static final String ACTION = "printAgentCSVReport";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/**
	 * parameter indicating the time for generation of agent csv report
	 */
	public static final String AGENTCSV_GENTIME = "stockcontrol.defaults.agentcsvgenerationtime";
	/**
	 * @param invocationContext
	 * @return
	 * @exception CommandInvocationException
	 */


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		// Generate filter from the form
		ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = null;
		errors = validateForm(listAwbStockHistoryForm);
		if(errors!=null&&errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		String companyCode = logonAttributesVO.getCompanyCode();
		StockRangeFilterVO stockRangeFilterVO = new StockRangeFilterVO();
		stockRangeFilterVO.setCompanyCode(companyCode);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();  
		Map<String, String> systemParameterValues = new HashMap<String, String>();
		Collection<String> systemParameterCodes = new ArrayList<String>();		
		systemParameterCodes.add(AGENTCSV_GENTIME);
		try {
			systemParameterValues = sharedDefaultsDelegate.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(errors!=null&&errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		if(DateUtilities.getDifferenceInDays(DateUtilities.getCurrentDate("dd-MMM-yyyy"),listAwbStockHistoryForm.getStartDate())==0){
			if(DateUtilities.isLessThan(DateUtilities.getCurrentDate("HH:mm"),
					systemParameterValues.get(AGENTCSV_GENTIME).trim(),"HH:mm")){
				Object[] obj = { systemParameterValues.get(AGENTCSV_GENTIME).trim() };
				ErrorVO error = new ErrorVO("stockcontrol.defaults.reportgentime", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);  
				errors.add(error);
			}
		}		 
		if(errors!=null&&errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		LocalDate start = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true);
		start.setDateAndTime(new StringBuilder().append(listAwbStockHistoryForm.getStartDate().trim())
				.append(" ").append(systemParameterValues.get(AGENTCSV_GENTIME).trim()).toString(),true);
		start.addDays(-1);
		stockRangeFilterVO.setStartDate(start);
		LocalDate end = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true);
		end.setDateAndTime(new StringBuilder().append(listAwbStockHistoryForm.getEndDate().trim())
				.append(" ").append(systemParameterValues.get(AGENTCSV_GENTIME).trim()).toString(),true);
		stockRangeFilterVO.setEndDate(end);
		
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listAwbStockHistoryForm.getProduct());
		reportSpec.setSubProductCode(listAwbStockHistoryForm.getSubProduct());
		reportSpec.setPreview(false);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(stockRangeFilterVO);
		reportSpec.setAction(ACTION);
		reportSpec.setResourceBundle(listAwbStockHistoryForm.getBundle());
		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}


		invocationContext.target = getTargetPage();


	}


	private Collection<ErrorVO> validateForm(ListAwbStockHistoryForm listAwbStockHistoryForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid=true;
		if(listAwbStockHistoryForm.getStartDate()==null || "".equals(listAwbStockHistoryForm.getStartDate().trim())){
			isValid=false;
			Object[] obj = { "StartDate" };
			error = new ErrorVO("stockcontrol.defaults.plsenterfrmdate", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		//Commented by A-2415 as part of CR ANA1666
	/*	if(listAwbStockHistoryForm.getTimeFrom()==null || "".equals(listAwbStockHistoryForm.getTimeFrom().trim())){
			Object[] obj = { "TimeFrom" };
			error = new ErrorVO("stockcontrol.defaults.plsentertimefrom", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/
		if(listAwbStockHistoryForm.getEndDate()==null || "".equals(listAwbStockHistoryForm.getEndDate().trim())){
			isValid=false;
			Object[] obj = { "EndDate" };
			error = new ErrorVO("stockcontrol.defaults.plsentertodate", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		//Commented by A-2415 as part of CR ANA1666
	/*	if(listAwbStockHistoryForm.getTimeTo()==null || "".equals(listAwbStockHistoryForm.getTimeTo().trim())){
			Object[] obj = { "TimeTo" };
			error = new ErrorVO("stockcontrol.defaults.plsentertimeto", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/
		if (isValid && DateUtilities.getDifferenceInDays(listAwbStockHistoryForm.getEndDate(),listAwbStockHistoryForm.getStartDate())!=0){
			isValid = false;
			Object[] obj = { "datesdifferent" };
			error = new ErrorVO("stockcontrol.defaults.datesdifferent", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
	}

}
