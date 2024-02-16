/*
 * ViewPrdDetailsCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * The command to view product details when the Maintain Product screen is invoked from the List Product Screen
 * @author A-1754
 *
 */
public class ViewPrdDetailsCommand extends BaseCommand {

	//private static final String COMPANY_CODE="AV";
	
	private static final String MODIFY_MODE="modify";
	
	private static final String PRIORITY_ONETIME="products.defaults.priority";
	
	private static final String ZERO = "0";
	
	private static final String TIME_SEPERATOR = ":";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
/**
 * The execute method in BaseCommand
 * @author A-1754
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		session.setButtonStatusFlag(maintainProductForm.getFromListProduct());
		log
				.log(Log.INFO, "\n\n\nFlag--------->", session.getButtonStatusFlag());
		ProductVO productVO =
		findProductDetails(getApplicationSession().getLogonVO().getCompanyCode(),
				maintainProductForm.getProductCode());
		session.setProductVO(productVO);
		handleGetScreenLoadDetails(session);
		maintainProductForm.setMode(MODIFY_MODE);
		setProductDisplayDetails(productVO,
				session,maintainProductForm);
		invocationContext.target = "screenload_success";

	}
	/**
	 * The method to get the onetime values
	 * @param session
	 */

	private void handleGetScreenLoadDetails(MaintainProductSessionInterface session){
		Map<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode()); 
		if(oneTimes!=null){
		Collection<OneTimeVO> restrictedTerms = oneTimes.get("products.defaults.paymentterms");
		Collection<OneTimeVO> weightUnit = oneTimes.get("shared.defaults.weightUnitCodes");
		Collection<OneTimeVO> volumeUnit = oneTimes.get("shared.defaults.volumeUnitCodes");
		Collection<OneTimeVO> statusOnetime = oneTimes.get("products.defaults.status");
		Collection<OneTimeVO> prtyOnetime = oneTimes.get(PRIORITY_ONETIME);
		Collection<OneTimeVO> productCategoryOnetime = oneTimes.get("products.defaults.category");

		Collection<RestrictionPaymentTermsVO> paymentTermsVOs= getAllPaymentRestrictions(restrictedTerms);
		session.setWeightCode(weightUnit);
		session.setVolumeCode(volumeUnit);
		session.setStatusOneTime(statusOnetime);
		session.setRestrictionPaymentTerms(paymentTermsVOs);
		session.setPriorityOneTIme(prtyOnetime);
		session.setProductCategories(productCategoryOnetime);
		}
		//added by A-1944 - to obtain the values of the dynamic doctypes
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		 LinkedHashMap<String,Collection<String>> documentList = null;
		 LinkedHashMap<String,Collection<String>> documentListNew = new LinkedHashMap<String,Collection<String>>();
		// HashMap<String,Collection<String>> documentList = null;
		 try{
			 documentListNew.put("",null);
		  documentList =new LinkedHashMap<String,Collection<String>>(
				  new DocumentTypeDelegate().findAllDocuments(companyCode));
		  //documentList.put("",null);
		  documentListNew.putAll(documentList);
		 }catch(BusinessDelegateException businessDelegateException){
		 	businessDelegateException.getMessage();
		 	log.log(Log.FINE,"\n\n Exception while finding the doctypes");
		 }
		 
		 log.log(Log.FINE, "\n\n the doctypes --------------", documentList);
		session.setDynamicDocType(documentListNew);

	}

    /**
     * Used to fetch the details of a particular product
     * @param companyCode
     * @param productCode
     * @return productVO
     */

    public ProductVO findProductDetails(String companyCode, String productCode) {
		ProductVO productVO = null;
		try{
			 productVO  = new ProductDefaultsDelegate().findProductDetails(companyCode,productCode);

		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
		}
		return productVO;

    }
    /**
     * Method to set the product display details.
     * @param productVO
     * @param session
     * @param request
     * @param mintainProductForm
     */

    private void setProductDisplayDetails(
    				ProductVO productVO,MaintainProductSessionInterface session,
    				MaintainProductForm mintainProductForm){

    		if(productVO.getTransportMode()!=null){
    			if(productVO.getTransportMode().size()==0){
    					session.setProductTransportModeVOs(null);
    			}else{
    				session.setProductTransportModeVOs(productVO.getTransportMode());
    			}
    		}
    		if(productVO.getProductScc()!=null){
    			if(productVO.getProductScc().size()==0){
    				session.setProductSccVOs(null);
    			}else{
    				session.setProductSccVOs(productVO.getProductScc());
    			}
    		}
    		if(productVO.getPriority()!=null){
    			if(productVO.getPriority().size()==0){
    				session.setProductPriorityVOs(null);
    			}else{
    				populatePrtyDisplay(productVO.getPriority(),session);
    				session.setProductPriorityVOs(productVO.getPriority());
    			}
    		}
    		if(productVO.getServices()!=null){
    			if(productVO.getServices().size()==0){
    				session.setProductServiceVOs(null);
    			}else{
    				session.setProductServiceVOs(productVO.getServices());
    			}
    		}

    		if(productVO.getRestrictionCommodity()!=null){
    			if(productVO.getRestrictionCommodity().size()==0){
    				session.setProductCommodityVOs(null);
    			}else{
    				session.setProductCommodityVOs(productVO.getRestrictionCommodity());
    			}
    		}

    		if(productVO.getRestrictionCustomerGroup()!=null){
    			if(productVO.getRestrictionCustomerGroup().size()==0){
    				session.setProductCustGrpVOs(null);
    			}else{
    				session.setProductCustGrpVOs(productVO.getRestrictionCustomerGroup());
    			}
    		}

    		if(productVO.getRestrictionSegment()!=null){
    			if(productVO.getRestrictionSegment().size()==0){
    				session.setProductSegmentVOs(null);
    			}else{
    				session.setProductSegmentVOs(productVO.getRestrictionSegment());
    				session.setSegmentAfterListing(productVO.getRestrictionSegment());
    			}
    		}
    		if(productVO.getRestrictionStation()!=null){
    			if(productVO.getRestrictionStation().size()==0){
    				session.setProductStationVOs(null);
    			}else{
    				session.setProductStationVOs(productVO.getRestrictionStation());
    			}
    		}

    		if(productVO.getProductEvents()!=null){
    			if(productVO.getProductEvents().size()==0){
    				session.setProductEventVOs(null);
    			}else{

    				Collection<ProductEventVO> productEventVos=productVO.getProductEvents();
    				Collection<ProductEventVO> eventsVOs=settingTimeChanger(productEventVos);
    				session.setProductEventVOs(eventsVOs);
    			}
    		}

    		if(productVO.getRestrictionPaymentTerms()!=null){
    			if(productVO.getRestrictionPaymentTerms().size()==0){
    				session.setSelectedRestrictedPaymentTerms(null);
    			}else{
    				session.setSelectedRestrictedPaymentTerms(productVO.getRestrictionPaymentTerms());
    			}
    		}

    		session.setBooleanForProductIcon("N");
				if (productVO.isProductIconPresent()) {
					session.setBooleanForProductIcon("Y");
						log.log(Log.FINE, "inside setting session", session.getBooleanForProductIcon());
			}

    		mintainProductForm.setProductName(productVO.getProductName());
    		log
					.log(
							Log.INFO,
							"\n\n\n!!!!!!!!!!!!!productVO.getProductName()!!!!!!!!!!!!",
							productVO.getProductName());
    		mintainProductForm.setProductCode(productVO.getProductCode());
    		mintainProductForm.setRateDefined(productVO.getIsRateDefined());
    		mintainProductForm.setCoolProduct(productVO.isCoolProduct());
    		if(productVO.getOverrideCapacity()!=null && "Y".equals(productVO.getOverrideCapacity())){
    			mintainProductForm.setOverrideCapacity("on");
    		}
			mintainProductForm.setDisplayInPortal(productVO.getIsDisplayInPortal());//Added for ICRD-352832 
    		//Added for ICRD-352832
    		if(productVO.getPrdPriority()!=null && !"0".equals(productVO.getPrdPriority())){
    			mintainProductForm.setProductPriority(productVO.getPrdPriority());
    		}else
    			mintainProductForm.setProductPriority(""); 
    		String fromDateString=(productVO.getStartDate()).toDisplayFormat(CALENDAR_DATE_FORMAT);
    		String endDateString=(productVO.getEndDate()).toDisplayFormat(CALENDAR_DATE_FORMAT);
    		mintainProductForm.setStartDate(fromDateString);
    		mintainProductForm.setEndDate(endDateString);
    		mintainProductForm.setRestrictedTermsCheck(getCheckedPaymentRestrictions(
    					productVO.getRestrictionPaymentTerms()));
    		mintainProductForm.setProductDesc(
    				productVO.getDescription());
    		mintainProductForm.setAddRestriction(
    				productVO.getAdditionalRestrictions());

    		mintainProductForm.setDetailDesc(
    				productVO.getDetailedDescription());

    		mintainProductForm.setHandlingInfo(
    				productVO.getHandlingInfo());

    		mintainProductForm.setRemarks(
    				productVO.getRemarks());
    		mintainProductForm.setBookingMand(productVO.isBookingMandatory());

    		mintainProductForm.setMaxVolume(
    				Double.toString(productVO.getMaximumVolumeDisplay()));

    		mintainProductForm.setMaxWeight(
    				Double.toString(productVO.getMaximumWeightDisplay()));
    		mintainProductForm.setMinVolume(
    				Double.toString(productVO.getMinimumVolumeDisplay()));
    		mintainProductForm.setMinWeight(
    				Double.toString(productVO.getMinimumWeightDisplay()));
    		mintainProductForm.setWeightUnit(
    				productVO.getDisplayWeightCode());
    		mintainProductForm.setVolumeUnit(
    				productVO.getDisplayVolumeCode());
    		mintainProductForm.setProductStatus(
    					findOneTimeDescription(session.getStatusOneTime(),productVO.getStatus()));
    		mintainProductForm.setProductCategory(productVO.getProductCategory());
    		if(productVO.getDocumentType() != null 
    				&& productVO.getDocumentType().trim().length()>0
    				&& productVO.getDocumentSubType() != null
    				&& productVO.getDocumentSubType().trim().length()>0){
	    		mintainProductForm.setDocType(productVO.getDocumentType());
	    		mintainProductForm.setSubType(productVO.getDocumentSubType());
    		}else{
	    		mintainProductForm.setDocType("");
	    		mintainProductForm.setSubType("");
    		}
    		
log.log(Log.FINE, "productVO.getProactiveMilestoneEnabled()",
					productVO.getProactiveMilestoneEnabled());
			if(ProductVO.FLAG_YES.equalsIgnoreCase(productVO.getProactiveMilestoneEnabled())){
    			mintainProductForm.setProactiveMilestoneEnabled(true);
    		}else{
    			mintainProductForm.setProactiveMilestoneEnabled(false);
    		}
    	}

	/**
	 * Method to get the Payment Terms
	 * This is done to keep the payment terms that are selected checked
	 * The String returned will be set to the paymentTermCheck of the form
	 * @return String[] selectedPaymentTerms
	 * @param paymentTerms
	 *
	 */
	private String[] getCheckedPaymentRestrictions(Collection<RestrictionPaymentTermsVO> paymentTerms){
		String[] selectedPaymentTerms = null;
		if(paymentTerms!=null){
			selectedPaymentTerms = new String[paymentTerms.size()];
			int count = 0;
			for(RestrictionPaymentTermsVO vo : paymentTerms){
				selectedPaymentTerms[count] = vo.getPaymentTerm();

				count++;
			}
		}
			return selectedPaymentTerms;
	}



	 /**
		 * This method will the dstatus escription corresponding to the value from onetime
		 * @param oneTimeVOs
		 * @param status
		 * @return String
		 */
		private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
			return null;
		}

		/**
	     * This method will be invoked at the time of screen load
	     * @param companyCode
	     * @return
	     */
	    public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
	    	Map<String, Collection<OneTimeVO>> oneTimes = null;
			try{

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("products.defaults.paymentterms");
			fieldTypes.add("shared.defaults.weightUnitCodes");
			fieldTypes.add("shared.defaults.volumeUnitCodes");
			fieldTypes.add("products.defaults.status");
			fieldTypes.add("products.defaults.category");
			fieldTypes.add(PRIORITY_ONETIME);
			 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(getApplicationSession().getLogonVO().getCompanyCode(),fieldTypes) ;

			}catch(BusinessDelegateException businessDelegateException){
				businessDelegateException.getMessage();
			}
	        return oneTimes;
	    }
	    /**
		 * This function returns the payment terms to be displayed
		 * this shud be obtained form the shared System
		 * Written of to test function call.
		 * @param restrictedTerms
		 * @return Collection<RestrictionPaymentTermsVO>
		 */
		private Collection<RestrictionPaymentTermsVO> getAllPaymentRestrictions(Collection<OneTimeVO> restrictedTerms){

			 Collection<RestrictionPaymentTermsVO> paymentTerms = new ArrayList<RestrictionPaymentTermsVO>(); ///Till here
			 for(OneTimeVO oneTime : restrictedTerms){
			   RestrictionPaymentTermsVO termsVO = new RestrictionPaymentTermsVO();
			   termsVO.setPaymentTerm(oneTime.getFieldValue());
			   termsVO.setIsRestricted(true);
			   paymentTerms.add(termsVO);
			 }
			   return paymentTerms;
		}
		/**
		 * Function to return the display value set
		 * @param list
		 * @param session
		 */
		private void populatePrtyDisplay(Collection<ProductPriorityVO> list,
				MaintainProductSessionInterface session){
			if(list!=null){
				for(ProductPriorityVO vo : list){
					vo.setPriorityDisplay(findOneTimeDescription(session.getPriorityOneTIme(),vo.getPriority()));

				}
			}
		}
		/**
		 *
		 * @param eventVos
		 * @return Collection<ProductEventVO>
		 */
		private Collection<ProductEventVO> settingTimeChanger(Collection<ProductEventVO> eventVos){
			log
					.log(
							Log.FINE,
							"\n\n\n**********************eventVos after timechange******************",
							eventVos);
			for(ProductEventVO eventVo:eventVos){
				eventVo.setMaximumTimeStr(findTimeString((int)eventVo.getMaximumTime()));
				eventVo.setMinimumTimeStr(findTimeString((int)eventVo.getMinimumTime()));
				
				/*if(eventVo.getMinimumTime()>60){
				minTime=String.valueOf((eventVo.getMinimumTime())/60);
				}else{
				minTime=String.valueOf((eventVo.getMinimumTime())/100);
				}
				if(eventVo.getMaximumTime()>60){
				maxTime=String.valueOf((eventVo.getMaximumTime())/60);
				}else{
				maxTime=String.valueOf((eventVo.getMaximumTime())/100);
				}

				if(minTime.indexOf('.')!=-1 && (minTime.length() - minTime.indexOf('.')) > 2){
					minTime=minTime.substring(0,minTime.indexOf('.')+3);
	                }
	            if(maxTime.indexOf('.')!=-1 && (maxTime.length() - maxTime.indexOf('.')) > 2){
				 maxTime=maxTime.substring(0,maxTime.indexOf('.')+3);
	           }

				
	            minimumTime=minTime.replace('.',':');
				maximumTime=maxTime.replace('.',':');*/
			}
			log
					.log(
							Log.FINE,
							"\n\n\n**********************eventVos after timechange******************",
							eventVos);
			return  eventVos;
		}
		
		/**
		 * Method to return time string getting number of minutes as input
		 * @param minutes
		 * @return
		 */
		private String findTimeString(int minutes){
			StringBuilder timeString = new StringBuilder();
			int hours = 0;
			int mins = 0;
			if(minutes>60){
				hours = minutes/60;
				mins = minutes%60;
				if(hours<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(hours));
				timeString.append(TIME_SEPERATOR);
				if(mins<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(mins));				
			}else{
				timeString.append(ZERO).append(ZERO);
				timeString.append(TIME_SEPERATOR);
				if(minutes<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(minutes));
			}
			return timeString.toString();
		}
}
