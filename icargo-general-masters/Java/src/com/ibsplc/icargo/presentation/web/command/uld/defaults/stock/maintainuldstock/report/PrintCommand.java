/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock.report;


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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST049";
	private Log log = LogFactory.getLogger("Maintain ULD Stock");
	private static final String LIST_FAILURE = "list_failure";
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private static final String RESOURCE_BUNDLE_KEY = "maintainuldstock";
	
	private static final String ACTION = "printmaintainuldstock";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
public void execute(InvocationContext invocationContext) throws CommandInvocationException {
	

	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	MaintainULDStockForm maintainuldstockform =
		(MaintainULDStockForm)invocationContext.screenModel;
	ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
	//ULDDefaultsDelegate uldDefaultsDelegate = null;	
	ULDStockConfigFilterVO uldstockconfigfiltervo = new ULDStockConfigFilterVO();
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	String companyCode = logonAttributes.getCompanyCode();
	uldstockconfigfiltervo.setCompanyCode(companyCode);	
	uldstockconfigfiltervo.setStationCode(upper(maintainuldstockform.getStationCode()));	
	//Added as part of ICRD-3639 by A-3767 on 16Aug11
	uldstockconfigfiltervo.setUldGroupCode(maintainuldstockform.getUldGroupCode());
	uldstockconfigfiltervo.setUldNature(maintainuldstockform.getUldNature());
	uldstockconfigfiltervo.setUldTypeCode(maintainuldstockform.getUldTypeCode());
	 
	errors = validateAirline(maintainuldstockform,companyCode,uldstockconfigfiltervo);
	
	if(errors!=null && errors.size() > 0) {
		listULDStockSession.removeULDStockDetails();
		invocationContext.addAllError(errors);
		invocationContext.target=PRINT_UNSUCCESSFUL;
		return;
	}	
	log.log(Log.FINE, "\n\n\n----------Filter Vo sent to server----->",
			uldstockconfigfiltervo);
					ReportSpec reportSpec = getReportSpec();
					reportSpec.setReportId(REPORT_ID);
					reportSpec.setProductCode(maintainuldstockform.getProduct());
					reportSpec.setSubProductCode(maintainuldstockform.getSubProduct());
					reportSpec.setPreview(true);
					reportSpec.setHttpServerBase(invocationContext.httpServerBase);
					reportSpec.addFilterValue(uldstockconfigfiltervo);
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

private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
}
private Collection<ErrorVO> validateAirline(MaintainULDStockForm maintainuldstockform,
		String companyCode,ULDStockConfigFilterVO uldstockconfigfiltervo) {
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!! INSIDE VALIDATE FUN.");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if("".equals(uldstockconfigfiltervo.getStationCode())) {
			Object[] obj = { "ULDStockStockConfigVO" };
			error = new ErrorVO("uld.defaults.stationmandatory", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(maintainuldstockform.getAirlineCode().length()>3) {
			Object[] obj = { "ULDStockStockConfigVO" };
			error = new ErrorVO("uld.defaults.airlinelength", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(maintainuldstockform.getStationCode().length() >3 ) {
			Object[] obj = { "ULDStockStockConfigVO" };
			error = new ErrorVO("uld.defaults.stationlength", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		AirlineValidationVO airlineVO = null;
		if(maintainuldstockform.getAirlineCode() != null
				&& maintainuldstockform.getAirlineCode().trim().length() > 0) {
					//System.out.println("!!!!!!!!!!!!!!!!!!!!!!! INSIDE IF."+companyCode+"   "+maintainuldstockform.getAirlineCode());
					Collection<ErrorVO> errorsAirline = null;
					try {
						airlineVO = new AirlineDelegate().validateAlphaCode(companyCode,(maintainuldstockform.getAirlineCode()).toUpperCase());
						//System.out.println("!!!!!!!!!!!!!!!!!!!!!!! "+airlineVO);
					}
					catch(BusinessDelegateException businessDelegateException) {
						errorsAirline = handleDelegateException(businessDelegateException);
	       			}
					if(errorsAirline != null &&
							errorsAirline.size() > 0) {
						errors.addAll(errorsAirline);
					}
		}

		if(airlineVO != null) {
			int airlineIdentifier = airlineVO.getAirlineIdentifier();
			uldstockconfigfiltervo.setAirlineIdentifier(airlineIdentifier);
		}
		return errors;
	}
}






