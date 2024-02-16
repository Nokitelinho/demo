package com.ibsplc.icargo.presentation.web.command.products.defaults.productperformance.report;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class PrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPRPRD002";
	private static final String EMPTY = "";
	private static final String ALL = "ALL";
	private static final String ERROR_FWD = "normal-report-error-jsp";
	
	private Log log = LogFactory.getLogger("PRODUCTPERFORMANCEREPORT PRINTCOMMAND");
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		ProductDefaultsDelegate employeeDelegate = new ProductDefaultsDelegate();
		ProductPerformanceFilterVO productPerformanceFilterVO = new ProductPerformanceFilterVO();
		Collection<ProductPerformanceVO> vos = new ArrayList<ProductPerformanceVO>();
		ProductPerformanceForm productPerformanceForm = (ProductPerformanceForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();	   	
		String printOrViewMode = productPerformanceForm.getIsView();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(productPerformanceForm);
		if(errors != null && errors.size()>0 ) {
			invocationContext.addAllError(errors);
			invocationContext.target = ERROR_FWD;			
		}else {
		try{
			productPerformanceFilterVO = getFilterVO(productPerformanceForm,logonAttributes);
				log.log(Log.FINE,
						"productPerformanceFilterVOproductPerformanceFilterVO",
						"productPerformanceFilterVO",
						productPerformanceFilterVO);
			vos = employeeDelegate.getProductPerformanceDetailsForReport(productPerformanceFilterVO);
			 
			}catch(BusinessDelegateException e){
				e.getMessage();
//printStackTrrace()();
				
			}
		
		if(vos == null || vos.size()==0){
			ErrorVO error = new ErrorVO("opeartions.shipment.nocolperformanceexists");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = ERROR_FWD;
			
		}else{
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().addParameter(productPerformanceFilterVO);
			if(vos!= null && vos.size()>0){
				getReportSpec().setData(vos);
			}
			if("true".equalsIgnoreCase(printOrViewMode)){
				  getReportSpec().setPreview(true);
			}else if("false".equalsIgnoreCase(printOrViewMode)){
				  getReportSpec().setPreview(false);
			}			
			ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[]{"productCode","productName","sccCode",
			"transMode", "fromDate","toDate", "origin","destination"});		
			
			ReportMetaData reportMetaData = new ReportMetaData();
			
			reportMetaData.setColumnNames(new String[]{"PRDCOD","PRDNAM","PRDDES","SCCCOD",
			"TRNMOD","PRTCOD","ORGCOD","DESCOD","WGTVAL","REVVAL"});
			reportMetaData.setFieldNames(new String[]{"productCode","productName","prodDescription",
			"sccCode", "transMode","prodPriority", "origin", 
			"destination","weight","revenue"});	
			
			getReportSpec().addParameterMetaData(parameterMetaData);
			getReportSpec().setReportMetaData(reportMetaData);	
			getReportSpec().setResourceBundle(productPerformanceForm.getBundle());
			getReportSpec().setProductCode("products");
			getReportSpec().setSubProductCode("defaults");	
			generateReport();
			invocationContext.target = getTargetPage();
		}
	}
	}
	/**
	 * 
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	public Collection<ErrorVO> validateForm (ProductPerformanceForm form){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		if(form.getFromDate()== null || form.getFromDate().trim().length()==0 ){
			ErrorVO error = new ErrorVO("products.performance.fromdateempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);			
		}		
		if(form.getToDate()== null || form.getToDate().trim().length()==0 ){
			ErrorVO error = new ErrorVO("products.performance.todateempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);			
		}			
		return errors;
	}
	
	public ProductPerformanceFilterVO getFilterVO (ProductPerformanceForm productPerformanceForm,
			LogonAttributes logonAttributes){
		ProductPerformanceFilterVO productPerformanceFilterVO = new ProductPerformanceFilterVO();
		LocalDate ldfrom= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate ldto= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);		
		productPerformanceFilterVO.setCompanyCode(logonAttributes.getCompanyCode());	
				
		if(productPerformanceForm.getDestination()!=null &&
					productPerformanceForm.getDestination().trim().length()>0) {
			productPerformanceFilterVO.setDestination(productPerformanceForm.getDestination());
		}
		else {
			productPerformanceFilterVO.setDestination(EMPTY);
		}
		if(productPerformanceForm.getScc()!=null && 
				productPerformanceForm.getScc().trim().length()>0 ) {
			productPerformanceFilterVO.setSccCode(productPerformanceForm.getScc());
		}
		else {
			productPerformanceFilterVO.setSccCode(EMPTY);
		}
		if(productPerformanceForm.getOrigin()!=null &&
				productPerformanceForm.getOrigin().trim().length()>0 ) {
			productPerformanceFilterVO.setOrigin(productPerformanceForm.getOrigin());
		}
		else {
			productPerformanceFilterVO.setOrigin(EMPTY);
		}
		if(productPerformanceForm.getPriority()!=null &&
				productPerformanceForm.getPriority().trim().length()>0 ){
			if(!ALL.equals(productPerformanceForm.getPriority())) {
				productPerformanceFilterVO.setPriority(productPerformanceForm.getPriority());
			}
			else {
				productPerformanceFilterVO.setPriority(EMPTY);
			}
		}
		if(productPerformanceForm.getProductCode()!=null && 
				productPerformanceForm.getProductCode().trim().length()>0 ) {			
			productPerformanceFilterVO.setProductCode(productPerformanceForm.getProductCode());			
		}
		else {
			productPerformanceFilterVO.setProductCode(EMPTY);
		}
		if(productPerformanceForm.getProductName()!=null && 
				productPerformanceForm.getProductName().trim().length()>0 ) {
			productPerformanceFilterVO.setProductName(productPerformanceForm.getProductName());
		}
		else {
			productPerformanceFilterVO.setProductName(EMPTY);
		}
		if(productPerformanceForm.getTransMode()!=null && 
				productPerformanceForm.getTransMode().trim().length()>0 ){
			if(!ALL.equals(productPerformanceForm.getTransMode())) {
					productPerformanceFilterVO.setTransMode(productPerformanceForm.getTransMode());
			}
			else {
				productPerformanceFilterVO.setTransMode(EMPTY);
			}
		}
		if(productPerformanceForm.getFromDate()!= null && 
				productPerformanceForm.getFromDate().trim().length()>0) {
			productPerformanceFilterVO.setFromDate(ldfrom.setDate(productPerformanceForm.getFromDate()));
		}
		
		if(productPerformanceForm.getToDate()!= null && 
				productPerformanceForm.getToDate().trim().length()>0) {
				productPerformanceFilterVO.setToDate(ldto.setDate(productPerformanceForm.getToDate()));
		}
		return productPerformanceFilterVO;
	}
	
	
	
}
