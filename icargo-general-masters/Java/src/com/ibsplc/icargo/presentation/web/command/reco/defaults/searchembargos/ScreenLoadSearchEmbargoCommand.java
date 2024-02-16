/*
 * ScreenLoadSearchEmbargoCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.searchembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * Command class for SearchEmbargos screen load 
 *
 * @author A-5867
 *
 */
public class ScreenLoadSearchEmbargoCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "listscreenload_success";
	
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
	
	private static final String ORIGIN_TYPE = "reco.defaults.geographicleveltype";
	private static final String APPLICABLE_TRANSACTION = "reco.defaults.applicabletransactions";
	private static final String RULE_TYPE = "reco.defaults.ruletype";
	private static final String LEVEL_CODE = "reco.defaults.levelcodes";
	private static final String PARAMETER_CODE = "reco.defaults.embargoparameters";
	private static final String CATEGORY = "reco.defaults.category";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String ULD_POS = "shared.uld.displayuldgroup"; //Added by A-8810 for IASCB-6097
	private static final String LEFT_PANEL_PARAMETERS = "reco.defaults.leftpanelparameters";
	private static final String COMPLIANCE_TYPE = "reco.defaults.compliancetype";
	private static final String SOURCE_SCREEN_PRECHECK = "PRECHECK";
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass"; //added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenloadOneTimeMasterCommand","Execute  ");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		Map hashMap = null;
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		searchEmbargoSession.removeEmbargoSearchVO();
		searchEmbargoSession.removeRegulatoryComplianceRules();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(ORIGIN_TYPE);
		oneTimeList.add(APPLICABLE_TRANSACTION);
		oneTimeList.add(RULE_TYPE);
		oneTimeList.add(LEVEL_CODE);
		oneTimeList.add(PARAMETER_CODE);
		oneTimeList.add(CATEGORY);
		oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
		oneTimeList.add(FLIGHT_TYPE);
		oneTimeList.add(ULD_POS);
		oneTimeList.add(COMPLIANCE_TYPE);
		oneTimeList.add(LEFT_PANEL_PARAMETERS);
		oneTimeList.add(SERVICE_CARGO_CLASS);
		oneTimeList.add(SHIPMENT_TYPE);
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		Collection<OneTimeVO> geographicLevelTypes= (Collection<OneTimeVO>)hashMap.get(ORIGIN_TYPE);
		searchEmbargoSession.setGeographicLevelTypes(geographicLevelTypes);
		Collection<OneTimeVO> applicableTransactions= (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTION);
		searchEmbargoSession.setApplicableTransactions(applicableTransactions);
		Collection<OneTimeVO> ruleTypes= (Collection<OneTimeVO>)hashMap.get(RULE_TYPE);
		searchEmbargoSession.setRuleTypes(ruleTypes);
		Collection<OneTimeVO> levelCOdes= (Collection<OneTimeVO>)hashMap.get(LEVEL_CODE);
		searchEmbargoSession.setLevelCodes(levelCOdes);
		Collection<OneTimeVO> parameterCodes= (Collection<OneTimeVO>)hashMap.get(PARAMETER_CODE);
		searchEmbargoSession.setParameterCodes(parameterCodes);
		Collection<OneTimeVO> categories= (Collection<OneTimeVO>)hashMap.get(CATEGORY);
		searchEmbargoSession.setCategories(categories);
		Collection<OneTimeVO> dayOfOperationApplicableOn= (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
		searchEmbargoSession.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
		Collection<OneTimeVO>  flightTypes = (Collection<OneTimeVO>)hashMap.get(FLIGHT_TYPE);
		searchEmbargoSession.setFlightTypes(flightTypes);
		Collection<OneTimeVO>  uldPos = (Collection<OneTimeVO>)hashMap.get(ULD_POS);
		searchEmbargoSession.setUldPos(uldPos);
		Collection<OneTimeVO> comlianceTypes= (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPE);
		searchEmbargoSession.setComplianceTypes(comlianceTypes);
		Collection<OneTimeVO> leftPanelParameters= (Collection<OneTimeVO>)hashMap.get(LEFT_PANEL_PARAMETERS);
		searchEmbargoSession.setLeftPanelParameters(leftPanelParameters);
		Collection<OneTimeVO>  serviceCargoClass =  (Collection<OneTimeVO>)hashMap.get(SERVICE_CARGO_CLASS);
		Collection<OneTimeVO>  shipmentType =  (Collection<OneTimeVO>)hashMap.get(SHIPMENT_TYPE);
		searchEmbargoSession.setServiceCargoClass(serviceCargoClass);
		searchEmbargoSession.setShipmentType(shipmentType);
		List<RegulatoryMessageVO> regulatoryMessages = null;
		RegulatoryMessageFilterVO filter= new RegulatoryMessageFilterVO();
		filter.setCompanyCode(companyCode);
		filter.setRolGroup(logonAttributes.getDefaultRoleGroupAtStation());
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);	
		filter.setCurrentDate(currentDate);
		LocalDate utcDate = null;
		try {
			EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
			regulatoryMessages = embargoRulesDelegate.findAllRegulatoryMessages(filter);
			if(null !=regulatoryMessages && regulatoryMessages.size()>0){
				for(RegulatoryMessageVO regulatoryMessage:regulatoryMessages){
					if(null !=regulatoryMessage.getUpdatedTransactionTime() ){				
						utcDate = new LocalDate(regulatoryMessage.getUpdatedTransactionTime(),logonAttributes.getStationCode(),Location.STN);
						regulatoryMessage.setUpdatedTransactionTimeView(utcDate);
					}
				}
			}
			searchEmbargoSession.setRegulatoryComposeMessages(regulatoryMessages);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(errors != null && errors.size () > 0){
			   invocationContext.addAllError(errors);
			   invocationContext.target = SCREENLOAD_SUCCESS;
			   return;
		}
		if(!SOURCE_SCREEN_PRECHECK.equals(searchEmbargoForm.getSourceScreen())){
			clearFormValues(searchEmbargoForm, searchEmbargoSession);
		}
		invocationContext.target =SCREENLOAD_SUCCESS;
		log.exiting("ScreenloadOneTimeMasterCommand","Execute");
	}
	private void clearFormValues(SearchEmbargoForm searchEmbargoForm,
			SearchEmbargoSession searchEmbargoSession) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		searchEmbargoForm.setGeographicLevelType(null);
		searchEmbargoForm.setGeographicLevel(null);
		searchEmbargoForm.setDayOfOperationApplicableOn(null);
		searchEmbargoForm.setOriginType(null);
		searchEmbargoForm.setOrigin(null);
		searchEmbargoForm.setApplicableTransaction(null);
		searchEmbargoForm.setCategory(null);
		searchEmbargoForm.setRuleType(null);
		searchEmbargoForm.setDestination(null);
		searchEmbargoForm.setDestinationType(null);
		searchEmbargoForm.setEmbargoDate(null);
		LocalDate fromLocalDate = null;
		fromLocalDate = new LocalDate(logonAttributes.getStationCode(), Location.STN, true);
		searchEmbargoForm.setFromDate(
				fromLocalDate.toDisplayDateOnlyFormat());
		//searchEmbargoForm.setFromDate(null);
		searchEmbargoForm.setLevelCode(null);
		searchEmbargoForm.setParameterCode(null);
		searchEmbargoForm.setParameterValue(null);
		searchEmbargoForm.setToDate(null);
		searchEmbargoForm.setViaPoint(null);
		searchEmbargoForm.setViaPointType(null);
		searchEmbargoSession.removeEmbargoSearchVO();
	}
	
	
}
