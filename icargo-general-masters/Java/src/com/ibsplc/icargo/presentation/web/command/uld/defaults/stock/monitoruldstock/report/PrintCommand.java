/*
 * PrintCommand.java Created on Dec 08, 2005
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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 /**
  * 
  * @author A-2122
  *
  */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST036";
	private Log log = LogFactory.getLogger("MonitorULD Stock Report");	   	
	
	private static final String RESOURCE_BUNDLE_KEY = "monitoruldstock";
	
	private static final String ACTION = "printmonitoruldstock";
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

MonitorULDStockSession monitorULDStockSession = (MonitorULDStockSession)getScreenSession("uld.defaults","uld.defaults.monitoruldstock");
Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
ULDStockConfigFilterVO uldStockConfigFilterVO = new ULDStockConfigFilterVO();
uldStockConfigFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		uldStockConfigFilterVO.setAbsoluteIndex(monitorULDStockSession
				.getULDStockListVO() == null ? 1 : monitorULDStockSession
				.getULDStockListVO().getStartIndex());
errors = loadStockFilterFromForm(monitorULDStockForm,uldStockConfigFilterVO);
//monitorULDStockForm.setDisplayPage("1");
//monitorULDStockForm.setLastPageNum("0");	
uldStockConfigFilterVO.setPageNumber(Integer.parseInt(monitorULDStockForm.getDisplayPage()));
uldStockConfigFilterVO.setPrintStatus(monitorULDStockForm.getIsPreview());

	log.log(Log.FINE,
			"MonitorULDStockPrintCommand ~~uldStockConfigFilterVO~~~",
			uldStockConfigFilterVO);
	if(errors != null &&
			errors.size() > 0 ) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
			
	}

	/*Page<ULDStockListVO> uldStockListVOs = new Page<ULDStockListVO>(
			new ArrayList<ULDStockListVO>(), 0, 0, 0, 0, 0, false);
	
	try {
		uldStockListVOs = new ULDDefaultsDelegate().findULDStockList(
			uldStockConfigFilterVO,Integer.parseInt
								(monitorULDStockForm.getDisplayPage())) ;
	}
	catch (BusinessDelegateException businessDelegateException) {
		errors = handleDelegateException(businessDelegateException);
	}
	
     log.log(Log.FINE,"\n\n--Obtained uldStockListVOs from the server->"
    		 + uldStockListVOs);
    	Collection<ULDStockListVO> vos = new 
    									ArrayList<ULDStockListVO>();
    	int pageSize = uldStockListVOs.size();
    	log.log(Log.FINE,"Page Sizeis ------->>"+pageSize);
    	for(int i=0;i<pageSize;i++){
    		vos.add(uldStockListVOs.get(i));
    		
    	}*/
    	/*if(uldStockConfigFilterVO!=null){
    	getReportSpec().setReportId(REPORT_ID);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[]{"airlineCode",
				"stationCode","uldTypeCode"});
		getReportSpec().addParameterMetaData(parameterMetaData);
		getReportSpec().addParameter(uldStockConfigFilterVO);
    	}
    	ReportMetaData reportMetaData = new ReportMetaData();

		reportMetaData.setColumnNames(new String[]{
		"ARLIDR", "STNCOD", "ULDTYPCOD", "AVL","DMG","LON",
		"NONOP","TOT","ALT","MAXQTY","MINQTY"});
		reportMetaData.setFieldNames(new String[] {"airlineCode",
				"stationCode","uldTypeCode", "available",
				"damaged","loaned","nonOperational",
				"total","owned","maxQty",
				"minQty"});*/
		/*getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setReportMetaData(reportMetaData);
		getReportSpec().setData(vos);
		getReportSpec().setPreview(true);
		getReportSpec().setResourceBundle("monitoruldstock");
log.log(Log.FINE,"\n\n\n----------REPORT_ID----->"+REPORT_ID);
log.log(Log.FINE,"\n\n\n----------reportMetaData----->"+reportMetaData);

generateReport();

log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
invocationContext.target = getTargetPage();
log.log(Log.FINE,"\n\n\n----------report----->"+invocationContext.target); */
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

/*
 * Modified as part of ICRD-334152
 */
private Collection<ErrorVO> loadStockFilterFromForm
		(MonitorULDStockForm monitorULDStockForm,
				ULDStockConfigFilterVO uldStockConfigFilterVO) {
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	if(monitorULDStockForm.getAirlineCode() != null 
			&& monitorULDStockForm.getAirlineCode().trim().length() > 0) {
			
		AirlineValidationVO airlineVO= validateAirlineCode(logonAttributes.getCompanyCode(),monitorULDStockForm.getAirlineCode(),errors);
			
				uldStockConfigFilterVO.setAirlineCode(
						monitorULDStockForm.getAirlineCode().toUpperCase());
	
	if(airlineVO != null) {
					uldStockConfigFilterVO.setAirlineIdentifier(airlineVO.getAirlineIdentifier()); 
				}
	}
	/*
	 * Added as part of ICRD-334152
	 */
	if(monitorULDStockForm.getOwnerAirline() != null
			&& monitorULDStockForm.getOwnerAirline().trim().length() > 0) { 
		if(!monitorULDStockForm.getOwnerAirline().equals(monitorULDStockForm.getAirlineCode())) {
			AirlineValidationVO ownerAirlineVO = validateAirlineCode(logonAttributes.getCompanyCode(),monitorULDStockForm.getOwnerAirline(),errors);
			if(ownerAirlineVO != null) {
				uldStockConfigFilterVO.setOwnerAirlineIdentifier(ownerAirlineVO.getAirlineIdentifier());
			}
		}else {
			uldStockConfigFilterVO.setOwnerAirlineIdentifier(uldStockConfigFilterVO.getAirlineIdentifier());
		}
		uldStockConfigFilterVO.setOwnerAirline(monitorULDStockForm.getOwnerAirline().toUpperCase());
	}
	/*
	 * Added as part of ICRD-334152 ends
	 */
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
	//added by a-3045 for bugU544 starts
	if((monitorULDStockForm.getLevelType() != null &&
			monitorULDStockForm.getLevelType().trim().length() > 0)) {
		uldStockConfigFilterVO.setLevelType(monitorULDStockForm.getLevelType().toUpperCase());
		
	}
	if((monitorULDStockForm.getLevelValue() != null &&
			monitorULDStockForm.getLevelValue().trim().length() > 0)) {
		uldStockConfigFilterVO.setLevelValue(monitorULDStockForm.getLevelValue().toUpperCase());
		
	}
	//added by a-3045 for bugU544 ends
	if((monitorULDStockForm.getUldTypeCode() != null &&
			monitorULDStockForm.getUldTypeCode().trim().length() > 0)) {
		uldStockConfigFilterVO.setUldTypeCode(monitorULDStockForm.getUldTypeCode().toUpperCase());
	}
	else{
		uldStockConfigFilterVO.setUldTypeCode("");
	}
	if((monitorULDStockForm.getUldNature() != null &&
			monitorULDStockForm.getUldNature().trim().length() > 0)) {
			if(("ALL").equals(monitorULDStockForm.getUldNature())){
				uldStockConfigFilterVO.setUldNature(null);
			}else{
				uldStockConfigFilterVO.setUldNature(monitorULDStockForm.getUldNature().toUpperCase());
			}
	if((monitorULDStockForm.getSort() != null &&
			monitorULDStockForm.getSort().trim().length() > 0)) {
		uldStockConfigFilterVO.setSort(monitorULDStockForm.getSort());

	}
	}
	return errors;
	
	
	

}
/**
 * 
 * @param companyCode
 * @param airlineCode
 * @param errors
 * @return
 */
private AirlineValidationVO validateAirlineCode(String companyCode, String airlineCode,
		Collection<ErrorVO> errors) {
	Collection<ErrorVO> errorsAirline=null;
	AirlineValidationVO	airlineVO=null;
	try {
		airlineVO= new AirlineDelegate().validateAlphaCode(companyCode,
				airlineCode.toUpperCase());
	}
	catch(BusinessDelegateException businessDelegateException) {
		errorsAirline =
			handleDelegateException(businessDelegateException);
		}
	if(errorsAirline != null &&
			errorsAirline.size() > 0) {
		errors.addAll(errorsAirline);
	}
	return airlineVO;
}
}





