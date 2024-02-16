/*
 * VoidCommand.java Created on March 26, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.blackliststock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceChargingAttributeVO;
import com.ibsplc.icargo.business.tariff.others.vo.ServiceConfigurationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.cashiering.defaults.PaymentAdviceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-4803
 *This class is invoked when the user clicks the VOid button from the blacklist Stock Screen. It 
 *validates the range from and range to and also the stock. This method throws 
 *StockControlDefaultsBusinessException with   error code as  
 *stockcontrol.defaults.documentnotinanystock if the document is not in stock range or in transit 
 *range otherwise, will return BlacklistStockVO.
 */
public class VoidCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("STOCK CONTROL BLACKLIST");
	
	private static final String VOID_STOCK_SERVICE=
			"cashiering.defaults.payment.voidstockservice";
	private static final  String CHARGING_DOCNUM_ATTRIBUTES  = 
			"tariff.others.docnumchargingattributes";
	private static final  String CHARGING_AGT_ATTRIBUTES  = 
			"tariff.others.agentcodechargingattribute";
	private static final  String CHARGING_STKDTL_ATTRIBUTES  = 
			"tariff.others.stockdetailchargingattributes";
	private static final  String CHARGING_RMKS_ATTRIBUTES  = 
			"tariff.others.remarkschargingattributes";
	private static final String MODULE_NAME_PAYADV = 
			"cashiering.defaults";
	private static final String SCREEN_ID_PAYADV =
			"cashiering.defaults.generatepaymentadvice";
	private static final String VOID_PAY_SUCCESS="void_success";
	private static final String VOID_PAY_FAILURE="void_failure";
	private static final String SAME_RANGES =
			"stockcontrol.defaults.blackliststock.sameranges";
	private static final String RANGES_TO_MANDAT=
			"stockcontrol.defaults.blackliststock.rangestomandatory";
	private static final String RANGES_FROM_MANDAT=
			"stockcontrol.defaults.blackliststock.rangesfrommandatory";
	private static final  String FLAG  = "Y";
	private static final  String SERVICE  = "Void Stock Service";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws 
			CommandInvocationException {
		log.entering("VoidCommand","execute");
		
		BlackListStockForm form = (BlackListStockForm)invocationContext.
				screenModel;
		StockControlDefaultsDelegate delegate = new 
				StockControlDefaultsDelegate();
		Collection<ErrorVO> errorVOs = null;
		int airlineIdentifier=0;
		/*
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().
					getLogonVO();
		BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(VOID_STOCK_SERVICE);
		try {
				map = new SharedDefaultsDelegate().
				findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findSystemParameterByCodes");
		}
		String voidStockService=map.get(VOID_STOCK_SERVICE);
		 
		ServiceConfigurationFilterVO  serviceConfigurationFilterVO =new 
				ServiceConfigurationFilterVO ();
		
		
		errorVOs=validateForm(form);
		
		if(errorVOs != null && errorVOs.size() > 0){
			invocationContext.addAllError(errorVOs);
			invocationContext.target = VOID_PAY_FAILURE;
			return;	
		} 
		
		if(form.isPartnerAirline()){
			airlineIdentifier= Integer.parseInt(form.getAwbPrefix().split("-")[2]);
		}else{
			airlineIdentifier=logonAttributes.getOwnAirlineIdentifier();
		}
		
		blacklistStockVO.setDocumentType(form.getDocType());
		blacklistStockVO.setRangeFrom(form.getRangeFrom());
		blacklistStockVO.setRangeTo(form.getRangeTo());
		blacklistStockVO.setDocumentSubType(form.getSubType());
		blacklistStockVO.setCompanyCode(logonAttributes.getCompanyCode());
		blacklistStockVO.setAirlineIdentifier(airlineIdentifier);
		blacklistStockVO.setRemarks(form.getRemarks());
		
		try{
			blacklistStockVO= delegate.validateStockForVoiding
					(blacklistStockVO);
		}catch(BusinessDelegateException businessDelegateException){
			errorVOs=handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "BusinessDelegateException caught from validateStockForVoiding");
		}
		if (errorVOs != null && errorVOs.size() > 0) {
			invocationContext.addAllError(errorVOs);
			invocationContext.target = VOID_PAY_FAILURE;
			return;
		}
		
		serviceConfigurationFilterVO = populateServiceConfigurationFilterVO(
				form,blacklistStockVO,logonAttributes,voidStockService,SERVICE,
				CHARGING_DOCNUM_ATTRIBUTES, CHARGING_AGT_ATTRIBUTES,
				CHARGING_STKDTL_ATTRIBUTES,CHARGING_RMKS_ATTRIBUTES);
		PaymentAdviceSession paymentAdviceSession =getScreenSession(
						MODULE_NAME_PAYADV, SCREEN_ID_PAYADV);
		/*
		 * set the chargingAttributesVO to paymentAdviceSession
		 */
		paymentAdviceSession.setServiceConfigurationFilterVO(
						serviceConfigurationFilterVO);
		BlackListStockSession session = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.blackliststock");
		session.setBlacklistStockVO(blacklistStockVO);
		invocationContext.target = VOID_PAY_SUCCESS;
		
		log.exiting("VoidCommand","execute");
	}
	
	/**
	 * 
	 * @param form
	 * @param logonAttributes
	 * @param service
	 * @param serviceName
	 * @param chargingDocAttribute
	 * @param chargingAgentAttribute
	 * @param chargingStockAttribute
	 * @param chargingRemarksAttribute
	 * @return
	 */
	private ServiceConfigurationFilterVO populateServiceConfigurationFilterVO
			(BlackListStockForm form,BlacklistStockVO blacklistStockVO,LogonAttributes logonAttributes,String 
			service,String serviceName,String chargingDocAttribute, String 
			chargingAgentAttribute, String chargingStockAttribute, String 
			chargingRemarksAttribute)  {
		
		ServiceConfigurationFilterVO  serviceConfigurationFilterVO =new 
				ServiceConfigurationFilterVO ();

		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(chargingDocAttribute);
		systemparameterCodes.add(chargingAgentAttribute);
		systemparameterCodes.add(chargingStockAttribute);
		systemparameterCodes.add(chargingRemarksAttribute);
		try {
				map = new SharedDefaultsDelegate().
				findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findSystemParameterByCodes");
		}
		
		serviceConfigurationFilterVO.setCompanyCode(logonAttributes.
						getCompanyCode());
		serviceConfigurationFilterVO.setServiceCode(service);
		serviceConfigurationFilterVO.setServiceName(serviceName);
		
		StringBuilder awbNumber = new StringBuilder();
		
		
		
		if(blacklistStockVO.getRangeFrom()!=null && blacklistStockVO.getRangeTo()!=null &&
				blacklistStockVO.getRangeFrom().trim().length()!=0 && 
				blacklistStockVO.getRangeTo().trim().length()!=0  ){
			 if(blacklistStockVO.getRangeFrom().equals(blacklistStockVO.getRangeTo())){
				String awb=blacklistStockVO.getRangeFrom();
				int checkDigit=Integer.parseInt(awb)%7;
				awbNumber.append(awb).append(checkDigit);
			}
		}
		
		ArrayList<ServiceChargingAttributeVO> chargingAttributes = new 
				ArrayList<ServiceChargingAttributeVO>();
		if(ServiceChargingAttributeVO.DOCNUM.equals(map.get(chargingDocAttribute))){
			ServiceChargingAttributeVO serviceChargingAttributeVO = new 
					ServiceChargingAttributeVO();
			serviceChargingAttributeVO.setChargingAttribute(ServiceChargingAttributeVO.DOCNUM);
			
			log.log(Log.FINE, "serviceConfigurationFilterVO ---> ",
					serviceConfigurationFilterVO);
			StringBuilder attributeValue = new StringBuilder();
			attributeValue.append(logonAttributes.getCompanyCode());
			attributeValue.append("-");
			attributeValue.append(form.isPartnerAirline()? 
					form.getAwbPrefix().split("-")[0]:logonAttributes.getOwnAirlineNumericCode());
			attributeValue.append("-");
			attributeValue.append(awbNumber);
			attributeValue.append("-");
			attributeValue.append(0);
			attributeValue.append("-");
			attributeValue.append(0);
			attributeValue.append("-");
			attributeValue.append(logonAttributes.getOwnAirlineIdentifier());  
			
			serviceChargingAttributeVO.setChargingAttributeValue(
						attributeValue.toString());
			chargingAttributes.add(serviceChargingAttributeVO);
		}
		if(ServiceChargingAttributeVO.AGENTCODE.equals(map.get(chargingAgentAttribute))){
			ServiceChargingAttributeVO serviceChargingAttributeVO = new 
					ServiceChargingAttributeVO();
			serviceChargingAttributeVO.setChargingAttribute(ServiceChargingAttributeVO.AGENTCODE);
			 serviceChargingAttributeVO.setChargingAttributeValue(
					 blacklistStockVO.getAgentCode());      
			chargingAttributes.add(serviceChargingAttributeVO);
		}
		if(ServiceChargingAttributeVO.STKDTL.equals(map.get(chargingStockAttribute))){
			ServiceChargingAttributeVO serviceChargingAttributeVO = new 
					ServiceChargingAttributeVO();
			serviceChargingAttributeVO.setDoNotShowAttribute(FLAG);
			serviceChargingAttributeVO.setChargingAttribute(ServiceChargingAttributeVO.STKDTL);

			StringBuilder attributeValue = new StringBuilder();
			
			attributeValue.append(blacklistStockVO.getDocumentType());
			attributeValue.append("-");
			attributeValue.append(blacklistStockVO.getDocumentSubType());
			attributeValue.append("-");
			attributeValue.append(blacklistStockVO.getStockHolderCode());
								
			serviceChargingAttributeVO.setChargingAttributeValue(
							attributeValue.toString());
			chargingAttributes.add(serviceChargingAttributeVO);
		}
		if(ServiceChargingAttributeVO.REMARKS.equals(map.get(chargingRemarksAttribute))){
			ServiceChargingAttributeVO serviceChargingAttributeVO = new 
					ServiceChargingAttributeVO();
			serviceChargingAttributeVO.setDoNotShowAttribute(FLAG);
			serviceChargingAttributeVO.setChargingAttribute(ServiceChargingAttributeVO.REMARKS);
			serviceChargingAttributeVO.setChargingAttributeValue(
					blacklistStockVO.getRemarks());      
			chargingAttributes.add(serviceChargingAttributeVO);
		}
		
		serviceConfigurationFilterVO.setChargingAttributes(chargingAttributes);
		
		log.log(Log.INFO, "#$$# serviceConfigurationFilterVO>>>>>>>>",
				serviceConfigurationFilterVO);
		return serviceConfigurationFilterVO;
	}
	
	/**
	 * 
	 * @param form
	 * @return
	 */
	  private Collection<ErrorVO> validateForm(BlackListStockForm form){
		log.entering("VoidCommand", "validateForm");
		
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		
		if(form.getRangeFrom()==null || 
					form.getRangeFrom().trim().length()==0){
			errorVO = new ErrorVO(RANGES_FROM_MANDAT);
			errorVOs.add(errorVO);
		}
		if(form.getRangeTo()==null ||
					form.getRangeTo().trim().length()==0){
			errorVO = new ErrorVO(RANGES_TO_MANDAT);
			errorVOs.add(errorVO);
		}
		if((form.getRangeFrom()!=null &&
				form.getRangeFrom().trim().length()!=0) && 
				(form.getRangeTo()!=null && form.getRangeTo().trim().length()!=0)){
			if(!(form.getRangeFrom().equals(form.getRangeTo()))){
				errorVO = new ErrorVO(SAME_RANGES);
				errorVOs.add(errorVO);
			}
		}
		log.exiting("VoidCommand", "validateForm");
		return errorVOs;
	  }
}
