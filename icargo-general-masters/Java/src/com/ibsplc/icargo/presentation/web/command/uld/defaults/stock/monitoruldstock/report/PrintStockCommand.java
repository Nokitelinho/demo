/*
 * PrintStockCommand.java Created on Apr 03, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.monitoruldstock.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class PrintStockCommand extends AbstractPrintCommand{


	private static final String REPORT_ID = "RPTLST213";
	private Log log = LogFactory.getLogger("ULD Station Stock Levels");	   	
	
	private static final String RESOURCE_BUNDLE_KEY = "monitoruldstock";
	
	private static final String ACTION = "printUldStationStock";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {
			
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
			MonitorULDStockForm monitorULDStockForm = 
							(MonitorULDStockForm) invocationContext.screenModel;
			
			
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ULDStockConfigFilterVO uldStockConfigFilterVO = new ULDStockConfigFilterVO();
			uldStockConfigFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
			errors = loadStockFilterFromForm(monitorULDStockForm,uldStockConfigFilterVO);
			monitorULDStockForm.setDisplayPage("1");
			monitorULDStockForm.setLastPageNum("0");	
			uldStockConfigFilterVO.setPageNumber(Integer.parseInt(monitorULDStockForm.getDisplayPage()));
			
				log.log(Log.FINE,
						"PrintStockCommand ~~uldStockConfigFilterVO~~~",
						uldStockConfigFilterVO);
				if(errors != null &&
						errors.size() > 0 ) {
						invocationContext.addAllError(errors);
						invocationContext.target = PRINT_UNSUCCESSFUL;
						return;
						
				}
			
				
				ReportSpec reportSpec = getReportSpec();
				reportSpec.setReportId(REPORT_ID);
				reportSpec.setProductCode(monitorULDStockForm.getProduct());
				reportSpec.setSubProductCode(monitorULDStockForm.getSubProduct());
				reportSpec.setPreview(true);
				reportSpec.setHttpServerBase(invocationContext.httpServerBase);
				reportSpec.addFilterValue(uldStockConfigFilterVO);
				reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
				reportSpec.setAction(ACTION);
				
				generateReport();
				
				if(getErrors() != null && getErrors().size() > 0){
					
					invocationContext.addAllError(getErrors());
					invocationContext.target = PRINT_UNSUCCESSFUL;
					return;
				}
				
				log.exiting("MRA_GPABILLING","PrintCommand exit");
				invocationContext.target = getTargetPage();
			
			
}


/**
 * @param monitorULDStockForm
 * @param uldStockConfigFilterVO
 * @return
 */
private Collection<ErrorVO> loadStockFilterFromForm
		(MonitorULDStockForm monitorULDStockForm,
				ULDStockConfigFilterVO uldStockConfigFilterVO) {
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	AirlineValidationVO airlineVO = null;
	if(monitorULDStockForm.getAirlineCode() != null 
			&& monitorULDStockForm.getAirlineCode().trim().length() > 0) {
			
				Collection<ErrorVO> errorsAirline = null;
				try {
					airlineVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							monitorULDStockForm.getAirlineCode().toUpperCase());
				}
				catch(BusinessDelegateException businessDelegateException) {
					errorsAirline = 
						handleDelegateException(businessDelegateException);
       			}
				if(errorsAirline != null &&
						errorsAirline.size() > 0) {
					errors.addAll(errorsAirline);
				}
				uldStockConfigFilterVO.setAirlineCode(
						monitorULDStockForm.getAirlineCode().toUpperCase());
	}
	
	if(airlineVO != null) {
		int airlineIdentifier = airlineVO.getAirlineIdentifier();
		uldStockConfigFilterVO.setAirlineIdentifier(airlineIdentifier); 
	}
	if((monitorULDStockForm.getStationCode() != null &&
			monitorULDStockForm.getStationCode().trim().length() > 0)) {
		uldStockConfigFilterVO.setStationCode(monitorULDStockForm.getStationCode().toUpperCase());
		
	}
	if((monitorULDStockForm.getAirlineCode() == null 
			|| monitorULDStockForm.getAirlineCode().trim().length()== 0) &&
			(monitorULDStockForm.getStationCode() == null ||
	    			monitorULDStockForm.getStationCode().trim().length() == 0)) {
		ErrorVO error = new ErrorVO("uld.defaults.eitherairlineorstationmandatory");
		errors.add(error);
	}
	if((monitorULDStockForm.getUldGroupCode() != null &&
			monitorULDStockForm.getUldGroupCode().trim().length() > 0)) {
		uldStockConfigFilterVO.setUldGroupCode(monitorULDStockForm.getUldGroupCode().toUpperCase());
		
	}
	
	if((monitorULDStockForm.getUldTypeCode() != null &&
			monitorULDStockForm.getUldTypeCode().trim().length() > 0)) {
		uldStockConfigFilterVO.setUldTypeCode(monitorULDStockForm.getUldTypeCode().toUpperCase());
	}
	else{
		uldStockConfigFilterVO.setUldTypeCode("");
	}
	uldStockConfigFilterVO.setViewByNatureFlag(monitorULDStockForm.getViewByNatureFlag());
	if((monitorULDStockForm.getUldNature() != null &&
			monitorULDStockForm.getUldNature().trim().length() > 0)) {
			if(("ALL").equals(monitorULDStockForm.getUldNature())){
				uldStockConfigFilterVO.setUldNature(null);
			}else{
				uldStockConfigFilterVO.setUldNature(monitorULDStockForm.getUldNature().toUpperCase());
			}
	
	}
	if(monitorULDStockForm.getAgentCode()!=null && monitorULDStockForm.getAgentCode().trim().length()>0){
		uldStockConfigFilterVO.setAgentCode(monitorULDStockForm.getAgentCode().trim());
	}else{
		uldStockConfigFilterVO.setAgentCode("");
	}
	return errors;
	
	
	

}

}
