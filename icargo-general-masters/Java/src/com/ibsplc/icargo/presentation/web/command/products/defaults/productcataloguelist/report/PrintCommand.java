package com.ibsplc.icargo.presentation.web.command.products.defaults.productcataloguelist.report;
import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.PrintProductForm;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
/**
 * Print Command for product brochure
 * @author A-2046
 *
 */
public class PrintCommand extends AbstractPrintCommand{
	/*
	 * report id
	 */
	private static final String REPORT_ID = "RPRPRD001";
	private static final String PREVIEW = "true";
	private static final String PRODUCTCODE="products";
	private static final String SUBPRODUCTCODE = "defaults";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
						throws CommandInvocationException{

	ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
	ProductVO productVO = new ProductVO();
	PrintProductForm printProductForm = (PrintProductForm)invocationContext.screenModel;
	String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
	String productCode=printProductForm.getProductCode();
	try{
	productVO = productDefaultsDelegate.findProductDetails(companyCode,productCode);
	}catch(BusinessDelegateException exception){
		exception.getMessage();
	}
		if(productVO!=null){
	if(productVO.getTransportMode()!=null && productVO.getTransportMode().size()>0){
		getReportSpec().addExtraInfo(productVO.getTransportMode());
		}
		else{
			Collection<ProductTransportModeVO> transDetails = new ArrayList<ProductTransportModeVO>();
			getReportSpec().addExtraInfo(transDetails);
			}
	if(productVO.getProductScc()!=null && productVO.getProductScc().size()>0){
	getReportSpec().addExtraInfo(productVO.getProductScc());
	}
	else{
		Collection<ProductSCCVO> sccDetails = new ArrayList<ProductSCCVO>();
		getReportSpec().addExtraInfo(sccDetails);
	}
	if(productVO.getPriority()!= null && productVO.getPriority().size()>0){
		getReportSpec().addExtraInfo(productVO.getPriority());
	}
	else{
		Collection<ProductPriorityVO> prioDetails = new ArrayList<ProductPriorityVO>();
		getReportSpec().addExtraInfo(prioDetails);
	}

	if(productVO.getServices()!= null && productVO.getServices().size()>0){
		getReportSpec().addSubReportData(productVO.getServices());
	}

	if(productVO.getProductEvents()!= null && productVO.getProductEvents().size()>0){
		getReportSpec().addExtraInfo(productVO.getProductEvents());
	}
	else{
		Collection<ProductEventVO> eventDetails = new ArrayList<ProductEventVO>();
		getReportSpec().addExtraInfo(eventDetails);
	}
	if(productVO.getRestrictionCommodity()!= null &&
			productVO.getRestrictionCommodity().size()>0){
		getReportSpec().addExtraInfo(productVO.getRestrictionCommodity());
	}
	else{
		Collection<RestrictionCommodityVO> commodities = new
		ArrayList<RestrictionCommodityVO>();
		getReportSpec().addExtraInfo(commodities);
	}

	if(productVO.getRestrictionSegment()!= null &&
			productVO.getRestrictionSegment().size()>0){
		getReportSpec().addExtraInfo(productVO.getRestrictionSegment());
	}
	else{
		Collection<RestrictionSegmentVO> segments = new
		ArrayList<RestrictionSegmentVO>();
		getReportSpec().addExtraInfo(segments);
	}
	if(productVO.getRestrictionCustomerGroup()!=null &&
			productVO.getRestrictionCustomerGroup().size()>0){
		getReportSpec().addExtraInfo(productVO.getRestrictionCustomerGroup());
	}
	else{
		Collection<RestrictionCustomerGroupVO> customers = new
		ArrayList<RestrictionCustomerGroupVO>();
		getReportSpec().addExtraInfo(customers);
	}
	if(productVO.getRestrictionPaymentTerms()!= null &&
			productVO.getRestrictionCustomerGroup().size()>0){
		getReportSpec().addExtraInfo(productVO.getRestrictionPaymentTerms());
	}
	else{
		Collection<RestrictionPaymentTermsVO> paymentTerms = new
		ArrayList<RestrictionPaymentTermsVO>();
		getReportSpec().addExtraInfo(paymentTerms);
	}
	if(productVO.getRestrictionStation()!= null &&
			productVO.getRestrictionStation().size()>0){
		getReportSpec().addExtraInfo(productVO.getRestrictionStation());
	}
	else{
		Collection<RestrictionStationVO> stations = new ArrayList<RestrictionStationVO>();
		getReportSpec().addExtraInfo(stations);
	}
	ReportMetaData parameterMetaData = new ReportMetaData();
	parameterMetaData.setFieldNames(new String[]
	     {"productName","description","startDate","endDate","status",
			"detailedDescription","transportMode","productScc","priority",
			"productEvents","restrictionCommodity",
			"restrictionSegment","restrictionCustomerGroup",
			"restrictionPaymentTerms","additionalRestrictions",
			"restrictionStation","minimumWeightDisplay",
			"maximumWeightDisplay","minimumVolumeDisplay","maximumVolumeDisplay"
			});
	ReportMetaData subReportOneMetaData = new ReportMetaData();
	subReportOneMetaData.setColumnNames(new String[]{"PRDCOD","CMPCOD"});
	subReportOneMetaData.setFieldNames(new String[]{"serviceCode","serviceDescription"});
	if(PREVIEW.equals(printProductForm.getHasPreview())) {
		getReportSpec().setPreview(true);
	} else {
		getReportSpec().setPreview(false);
	}
	getReportSpec().setResourceBundle("productCatalogueListResources");
	getReportSpec().setProductCode(PRODUCTCODE);
	getReportSpec().setSubProductCode(SUBPRODUCTCODE);
	getReportSpec().addParameterMetaData(parameterMetaData);
	getReportSpec().setReportId(REPORT_ID);
	getReportSpec().addParameter(productVO);
	getReportSpec().addSubReportMetaData(subReportOneMetaData);
	generateReport();
	invocationContext.target = getTargetPage();
	}
	}
}
