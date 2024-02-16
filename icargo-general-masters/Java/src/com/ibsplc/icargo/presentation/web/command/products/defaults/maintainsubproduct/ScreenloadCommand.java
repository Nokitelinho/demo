/*
 * ScreenloadCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1870
 * 
 */
public class ScreenloadCommand extends BaseCommand{
	
	//private static final String COMPANY_CODE = "AV";
	
	private static final String ZERO = "0";
	
	private static final String TIME_SEPERATOR = ":";
	
	private static final String EMPTY = "";
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1870
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		MaintainSubProductSessionInterface maintainSubProductSessionInterface = 
			getScreenSession("product.defaults", "products.defaults.maintainsubproducts");
		MaintainSubProductForm maintainSubProductForm = (MaintainSubProductForm)invocationContext.screenModel;
		SubProductVO subProductVO = new SubProductVO();
		subProductVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		subProductVO.setProductCode(maintainSubProductForm.getProductCode());
		subProductVO.setSubProductCode(maintainSubProductForm.getSubProductCode());
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(!EMPTY.equals(maintainSubProductForm.getVersionNumber())){
			int versionNumber = Integer.parseInt(maintainSubProductForm.getVersionNumber());
			subProductVO.setVersionNumber(versionNumber);
		}
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			SubProductVO retSubProductVO =productDefaultsDelegate.findSubProductDetails(subProductVO);
			Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
			if(oneTimes!=null){
				Collection<OneTimeVO> resultWeight=
					oneTimes.get("shared.defaults.weightUnitCodes");
				Collection<OneTimeVO> resultVolume=oneTimes.get("shared.defaults.volumeUnitCodes");

				Collection<OneTimeVO> resultRestrictedPaymentTerms=oneTimes.get("products.defaults.paymentterms");
				Collection<RestrictionPaymentTermsVO> paymentTermsVOs= getAllPaymentRestrictions(resultRestrictedPaymentTerms);
				Collection<OneTimeVO> statusOnetime =oneTimes.get("products.defaults.status");
				Collection<OneTimeVO> priorityOnetime =oneTimes.get("products.defaults.priority");
			    maintainSubProductSessionInterface.setWeight(resultWeight);
				maintainSubProductSessionInterface.setVolume(resultVolume);
				maintainSubProductSessionInterface.setRestrictedPaymentTerms(paymentTermsVOs);
				maintainSubProductSessionInterface.setStatus(statusOnetime);
				maintainSubProductSessionInterface.setPriority(priorityOnetime);

			}
			settingData(retSubProductVO,maintainSubProductForm,maintainSubProductSessionInterface);
			
		}catch(BusinessDelegateException businessDelegateException){


			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}


	if (errors != null && errors.size() > 0) {
		invocationContext.addAllError(errors);
		invocationContext.target = "screenload_success";
		return;
	}


		invocationContext.target = "screenload_success";

	}
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("shared.defaults.weightUnitCodes");
			fieldValues.add("shared.defaults.volumeUnitCodes");
			fieldValues.add("products.defaults.paymentterms");
			fieldValues.add("products.defaults.status");
			fieldValues.add("products.defaults.priority");

			oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(getApplicationSession().getLogonVO().getCompanyCode(),fieldValues) ;

		}catch(BusinessDelegateException e){
		}
		return oneTimes;
	}


	/**
	 * This function returns the payment terms to be displayed
	 * this shud be obtained form the shared System
	 * Written of to test function call.
	 */
	/**
	 * @param restrictedTerms
	 * @return
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
	 * private method to get the Payment Terms
	 * This is done to keep the payment terms that are selected checked
	 * The String returned will be set to the paymentTermCheck of the form
	 * @return String[] selectedPaymentTerms
	 *
	 */
	/**
	 * @param paymentTerms
	 * @return
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
	 *  @param maintainSubProductForm
	 *  @param maintainSubProductSessionInterface
	 *  @param retSubProductVO
	 */
   private void settingData(SubProductVO retSubProductVO,MaintainSubProductForm maintainSubProductForm
		   ,MaintainSubProductSessionInterface maintainSubProductSessionInterface){
	   
	   maintainSubProductForm.setAddRestriction(retSubProductVO.getAdditionalRestrictions());
		maintainSubProductForm.setProductCode(retSubProductVO.getProductCode());
		maintainSubProductForm.setSubProductCode(retSubProductVO.getSubProductCode());
		maintainSubProductForm.setVersionNumber(String.valueOf(retSubProductVO.getVersionNumber()));
		maintainSubProductForm.setCompanyCode(retSubProductVO.getCompanyCode());
		maintainSubProductForm.setMaxVolume(Double.toString(retSubProductVO.getMaximumVolumeDisplay()));
		maintainSubProductForm.setMaxWeight(Double.toString(retSubProductVO.getMaximumWeightDisplay()));
		maintainSubProductForm.setMinVolume(Double.toString(retSubProductVO.getMinimumVolumeDisplay()));
		maintainSubProductForm.setMinWeight(Double.toString(retSubProductVO.getMinimumWeightDisplay()));
		maintainSubProductForm.setTransportMode(retSubProductVO.getProductTransportMode());
		maintainSubProductForm.setProductScc(retSubProductVO.getProductScc());
		maintainSubProductForm.setHiddenPriority(retSubProductVO.getProductPriority());
		maintainSubProductForm.setPriority(
				findOneTimeDescription(maintainSubProductSessionInterface.getPriority(),retSubProductVO.getProductPriority()));
		maintainSubProductForm.setWeightUnit(retSubProductVO.getWeightUnit());
		maintainSubProductForm.setVolumeUnit(retSubProductVO.getVolumeUnit());
		maintainSubProductForm.setHiddenStatus(retSubProductVO.getStatus());
		maintainSubProductForm.setHandlingInfo(retSubProductVO.getHandlingInfo());
		maintainSubProductForm.setRemarks(retSubProductVO.getRemarks());
		maintainSubProductForm.setStatus(
				findOneTimeDescription(maintainSubProductSessionInterface.getStatus(),retSubProductVO.getStatus()));
		maintainSubProductForm.setRestrictedTermsCheck(getCheckedPaymentRestrictions(
				retSubProductVO.getRestrictionPaymentTerms()));
		maintainSubProductForm.setHandlingInfo(retSubProductVO.getHandlingInfo());
		maintainSubProductForm.setRemarks(retSubProductVO.getRemarks());
		//added now
		/*String endDate = "";
		String startDate = "";
		if(retSubProductVO.getEndDate()!=null ){
			log.log(log.FINE ,"retSubProductVO.getEndDate()"+retSubProductVO.getEndDate());
			endDate = TimeConvertor.toStringFormat(retSubProductVO.getEndDate()
					.toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
		}
		
		if(retSubProductVO.getStartDate()!=null ){
			log.log(log.FINE ,"retSubProductVO.getStartDate()"+retSubProductVO.getStartDate());
			startDate = TimeConvertor.toStringFormat(retSubProductVO.getStartDate()
					.toCalendar(),
			TimeConvertor.CALENDAR_DATE_FORMAT);
		}
		
		maintainSubProductForm.setEndDate(endDate);
		maintainSubProductForm.setStartDate(startDate);*/
	
		if(retSubProductVO!=null){
			maintainSubProductSessionInterface.setSubProductVO(retSubProductVO);
		}
		maintainSubProductSessionInterface.setProductScc(retSubProductVO.getProductScc());
		maintainSubProductSessionInterface.setSelectedRestrictedPaymentTerms(retSubProductVO.getRestrictionPaymentTerms());
		if(retSubProductVO.getRestrictionCommodity()!=null ){
			if(retSubProductVO.getRestrictionCommodity().size()==0){
				maintainSubProductSessionInterface.setCommodityVOs(null);
			}else{

				maintainSubProductSessionInterface.setCommodityVOs(retSubProductVO.getRestrictionCommodity());

			}
		}
		if(retSubProductVO.getRestrictionCustomerGroup()!=null ){
			if(retSubProductVO.getRestrictionCustomerGroup().size()==0){
				maintainSubProductSessionInterface.setCustGroupVOs(null);
			}else{

				maintainSubProductSessionInterface.setCustGroupVOs(retSubProductVO.getRestrictionCustomerGroup());
				setCustomerGroupRestriction(maintainSubProductForm,maintainSubProductSessionInterface);
			}
		}
		if(retSubProductVO.getRestrictionSegment()!=null ){
			if(retSubProductVO.getRestrictionSegment().size()==0){
				maintainSubProductSessionInterface.setSegmentVOs(null);
			}else{
				maintainSubProductSessionInterface.setSegmentVOs(retSubProductVO.getRestrictionSegment());
				maintainSubProductSessionInterface.setSegmentAfterListing(retSubProductVO.getRestrictionSegment());
				setSegmentRestriction(maintainSubProductForm,maintainSubProductSessionInterface);
			}
		}
		if(retSubProductVO.getRestrictionStation()!=null ){
			if(retSubProductVO.getRestrictionStation().size()==0){
				maintainSubProductSessionInterface.setStationVOs(null);
			}else{
				maintainSubProductSessionInterface.setStationVOs(retSubProductVO.getRestrictionStation());
				setStationRestriction(maintainSubProductForm,maintainSubProductSessionInterface);
			}
		}
		if(retSubProductVO.getServices()!=null ){
			if(retSubProductVO.getServices().size()==0){
				maintainSubProductSessionInterface.setProductService(null);
			}else{
				maintainSubProductSessionInterface.setProductService(retSubProductVO.getServices());
			}
		}
		if(retSubProductVO.getEvents()!=null ){
			if(retSubProductVO.getEvents().size()==0){
				
				maintainSubProductSessionInterface.setProductEventVOs(null);
			}else{
				Collection<ProductEventVO> productEventVos=retSubProductVO.getEvents();
				Collection<ProductEventVO> eventsVOs=settingTimeChanger(productEventVos);
				maintainSubProductSessionInterface.setProductEventVOs(eventsVOs);
				//maintainSubProductSessionInterface.setProductEventVOs(retSubProductVO.getEvents());
			}
		}

   }
   
   /**
	 * @param form
	 * @param session
	 */
	private void setCustomerGroupRestriction(MaintainSubProductForm form,MaintainSubProductSessionInterface session){
		if(session.getCustGroupVOs()!=null){
			RestrictionCustomerGroupVO vo = 
				(RestrictionCustomerGroupVO)((ArrayList<RestrictionCustomerGroupVO>)session.getCustGroupVOs()).get(0);
			boolean isRestricted = vo.getIsRestricted();
			if(isRestricted){
				form.setCustGroupStatus("Restrict");
			}else{
				form.setCustGroupStatus("Allow");
			}

		}
		
	}
	/**
	 *  @param form
	 *  @param session
	 */
	private void setSegmentRestriction(MaintainSubProductForm form,MaintainSubProductSessionInterface session){
		if(session.getSegmentVOs()!=null){
			RestrictionSegmentVO vo = (RestrictionSegmentVO)((ArrayList<RestrictionSegmentVO>)session.getSegmentVOs()).get(0);
			boolean isRestricted = vo.getIsRestricted();
			if(isRestricted){
				form.setSegmentStatus("Restrict");
			}else{
				form.setSegmentStatus("Allow");
			}

		}
		
	}
	/**
	 *  @param form
	 *  @param session
	 */
	private void setStationRestriction(MaintainSubProductForm form,MaintainSubProductSessionInterface session){
		if(session.getStationVOs()!=null){
			
			for(RestrictionStationVO vo : session.getStationVOs()){
				if(vo.getIsOrigin()){
					if(vo.getIsRestricted()){
						form.setOriginStatus("Restrict");
					}else{
						form.setOriginStatus("Allow");
					}
					
				}else{
					if(vo.getIsRestricted()){
						form.setDestinationStatus("Restrict");
					}else{
						form.setDestinationStatus("Allow");
					}
				}
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
