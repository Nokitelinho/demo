/*
 * ScreenloadCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.productcatalogue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductCatalogueForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ScreenloadCommand extends BaseCommand{
	private static final String PRIORITY_ONETIME="products.defaults.priority";
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
    
    private static final String NIL = "NIL";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenloadCommand","productCatalogue");
		ProductCatalogueForm productCatalogueForm = 
			(ProductCatalogueForm)invocationContext.screenModel;
		String productCode=productCatalogueForm.getCode();
		log
				.log(
						Log.FINE,
						"\n\n\n\n**********************productCode from jsp-----------",
						productCode);
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(PRIORITY_ONETIME);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, Collection<OneTimeVO>> oneTimes =
			findPriorities(getApplicationSession().
					getLogonVO().getCompanyCode(),fieldTypes) ;
		if(oneTimes!=null){
			session.setPriorityLovVOs(oneTimes.get(PRIORITY_ONETIME));
		}
		try{
			ProductDefaultsDelegate productDefaultsDelegate = 
				new ProductDefaultsDelegate();
			ProductVO productVO =productDefaultsDelegate.findProductDetails(
					getApplicationSession().getLogonVO().getCompanyCode(),
					productCode);
			log
					.log(
							Log.FINE,
							"\n\n\n\n**********************ProductVO from server-------",
							productVO);
			ProductVO prdVO=building(session,productVO);
			
			productCatalogueForm.setProductVO(prdVO);
			productCatalogueForm.setProductName(prdVO.getProductName());
			
		}catch(BusinessDelegateException businessDelegateException){
		
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		invocationContext.target="screenload_success";
		log.exiting("ScreenloadCommand","productCatalogue");
	}
	
	/**
	 * The method calls the controller and returns the onetime vos corresponding
	 *  to tramsport mode
	 * @param companyCode
	 * @param fieldTypes
	 * @author A-1754
	 * @return Map<String, Collection<OneTimeVO>> 
	 */
	private Map<String, Collection<OneTimeVO>> 
	findPriorities(String companyCode,Collection<String>fieldTypes) {
		
		Map<String,Collection<OneTimeVO>> tMode  = null;
		try{
			
		 tMode = new SharedDefaultsDelegate().
		 findOneTimeValues(companyCode,fieldTypes);
		
		}catch(BusinessDelegateException e){
			
		}
		
		return tMode;
		
	}
	private ProductVO building(
			MaintainProductSessionInterface session,ProductVO productVO){
		
		StringBuilder build = new StringBuilder();
		String afterBuild = "";
		if(productVO!=null){
			if(productVO.getStartDate()!=null ) {
				/*String startDate=TimeConvertor.toStringFormat(
						productVO.getStartDate(), "dd-MMM-yyyy");
				String endDate=TimeConvertor.toStringFormat(
						productVO.getEndDate(), "dd-MMM-yyyy");*/
				
				//Done to avoid getting one day earlier than the actual date
				String startDate = productVO.getStartDate().toDisplayDateOnlyFormat();
				String endDate = productVO.getEndDate().toDisplayDateOnlyFormat();
				//ends
				
				afterBuild = (build.append(startDate).append("  ").append("to").
						append("  ").append(endDate)).toString();
				productVO.setFinalDate(afterBuild);
				   log.log(Log.FINE,
						"\n\n------------------- ----afterBuild-------->",
						afterBuild);
			   
			   }
		Collection<ProductTransportModeVO> transportModes=productVO.
		getTransportMode();
		StringBuilder transportBuild = new StringBuilder();
		String transportAfterBuild = "";
		int length=0;
		String finalTransportModes="";
		if(transportModes!=null && transportModes.size()>0) {
			for(ProductTransportModeVO vo:transportModes){
				transportAfterBuild=(transportBuild.append(
						vo.getTransportMode()).append(",")).toString();
				
			}
			length=	transportAfterBuild.length();
			finalTransportModes=transportAfterBuild.substring(0,length-1);
			log.log(Log.FINE,
					"\n\n------------------- ----finalTransportModes---->",
					finalTransportModes);
			productVO.setFinalTransportMode(finalTransportModes);
		}
		Collection<ProductPriorityVO> priorities=productVO.getPriority();
		StringBuilder priorityBuild = new StringBuilder();
		String priorityAfterBuild = "";
		int lengthprior=0;
		String finalPriority="";
		Collection<OneTimeVO> oneTimeVOs =session.getPriorityLovVOs();
		if(priorities!=null && priorities.size()>0) {
			for(ProductPriorityVO priorityVo:priorities){
				priorityAfterBuild=(priorityBuild.
						append(findOneTimeDescription(
								oneTimeVOs,priorityVo.getPriority()))
								.append(",")).toString();
			}
			lengthprior=priorityAfterBuild.length();
			finalPriority=priorityAfterBuild.substring(0,lengthprior-1);
			log.log(Log.FINE,
					"\n\n-----------------------finalPriority-------->",
					finalPriority);
			productVO.setFinalPriority(finalPriority);
		}
		Collection<ProductServiceVO> services=productVO.getServices();
		StringBuilder servicesBuild = new StringBuilder();
		String servicesAfterBuild = "";
		int lengthserv=0;
		String finalServices="";
		if(services!=null && services.size()>0) {
			for(ProductServiceVO serviceVo:services){
				servicesAfterBuild=(servicesBuild.append(serviceVo.
						getServiceDescription()).append(",")).toString();
			}
			lengthserv=servicesAfterBuild.length();
			finalServices=servicesAfterBuild.substring(0,lengthserv-1);
			log.log(Log.FINE,
					"\n\n-------------------------finalServices-------->",
					finalServices);
			productVO.setFinalService(finalServices);
		}
		StringBuilder capacityRestricBuild = new StringBuilder();
		String capacityRestricAfterBuild="";
		String maxvol = "";
		String volUnit = "";
		String minvol = "";
		String maxWt = "";
		String wtUnit = "";
		String minWt = "";
		boolean isNil = false;
		capacityRestricBuild.append("Capacity Restrictions :-");
		if(productVO.getMaximumVolume()!=0.0){
			maxvol=String.valueOf(productVO.getMaximumVolume());
			volUnit=productVO.getDisplayVolumeCode();
			capacityRestricBuild.append("Max Volume :").
				append(maxvol).append(volUnit).append(",");
			isNil = true;
		}
		if(productVO.getMinimumVolume()!=0.0){
			minvol=String.valueOf(productVO.getMinimumVolume());
			volUnit=productVO.getDisplayVolumeCode();
			capacityRestricBuild.append("Min Volume :").
				append(minvol).append(volUnit).append(",");
			isNil = true;
		}
		if(productVO.getMaximumWeight()!=0.0){
			maxWt=String.valueOf(productVO.getMaximumWeight());
			wtUnit=productVO.getDisplayWeightCode();
			capacityRestricBuild.append("Max Weight :").
				append(maxWt).append(wtUnit).append(",");
			isNil = true;
		}
		if(productVO.getMinimumWeight()!=0.0){
			minWt=String.valueOf(productVO.getMinimumWeight());
			wtUnit=productVO.getDisplayWeightCode();
			capacityRestricBuild.append("Min Weight :").
				append(minWt).append(wtUnit);
			isNil = true;
		}
		if(!isNil){
			capacityRestricBuild.append(NIL);
		}
		capacityRestricAfterBuild = capacityRestricBuild.toString();
		log.log(Log.FINE,
				"\n\n--------------capacityRestricAfterBuild-------->",
				capacityRestricAfterBuild);
		Collection<RestrictionPaymentTermsVO> paymentTerms=
			productVO.getRestrictionPaymentTerms();
		StringBuilder paymentTermsRestricBuild = new StringBuilder();
		String paymentTermsRestricAfterBuild="";
		int lenthpay=0;
		String finalPayment="";
		if(paymentTerms!=null && paymentTerms.size()>0) {
			for(RestrictionPaymentTermsVO paymentVo:paymentTerms){
					paymentTermsRestricAfterBuild=(paymentTermsRestricBuild.
							append(paymentVo.getPaymentTerm()).append(",")
							).toString();
				
			}
			lenthpay=paymentTermsRestricAfterBuild.length();
			finalPayment=paymentTermsRestricAfterBuild.substring(0,lenthpay-1);
			log.log(Log.FINE,
					"\n\n-------------------------finalPayment-------->",
					finalPayment);
		}
		StringBuilder genInstrucBuild = new StringBuilder();
		String genInstrucAfterBuild="";
		if(!"".equals(finalPayment)){
		genInstrucAfterBuild=(genInstrucBuild.append(capacityRestricAfterBuild).
				append(";").
				append("Payment Restrictions :-").append(finalPayment)).
				toString();
		}else{
		genInstrucAfterBuild=(genInstrucBuild.
					append(capacityRestricAfterBuild)).toString();
		}
		productVO.setFinalGenInstructions(genInstrucAfterBuild);
		log.log(Log.FINE,
				"\n\n------------------------genInstrucAfterBuild---->",
				genInstrucAfterBuild);
		Collection<ProductSCCVO> commodities=
			productVO.getProductScc();
		StringBuilder commodityRestricBuild = new StringBuilder();
		String commodityRestricAfterBuild="";
		int lengthcommodity=0;
		String finalCommodity="";
		if(commodities!=null && commodities.size()>0) {
			for(ProductSCCVO scc:commodities){
				commodityRestricAfterBuild=(commodityRestricBuild.
							append(scc.getScc()).append(",")
							).toString();
			}
			lengthcommodity=commodityRestricAfterBuild.length();
			finalCommodity=commodityRestricAfterBuild.substring(
					0,lengthcommodity-1);
			log.log(Log.FINE,
					"\n\n-------------------------finalCommodity-------->",
					finalCommodity);
			productVO.setFinalCommodities(finalCommodity);
		}
		
		}
		return productVO;
	}
	
	/**
	 * This method will the dstatus escription
	 * corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs,
			String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equals(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}
	
}
