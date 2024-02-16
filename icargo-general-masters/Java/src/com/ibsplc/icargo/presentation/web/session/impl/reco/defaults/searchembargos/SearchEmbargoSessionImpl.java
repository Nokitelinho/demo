/*
 * SearchEmbargoSessionImpl.java Created on May 13, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.searchembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * The Class SearchEmbargoSessionImpl.
 *
 * @author A-
 * SessionImplementation for SearchEmbargo
 */

public class SearchEmbargoSessionImpl extends AbstractScreenSession
	implements SearchEmbargoSession {
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID ="reco.defaults.searchembargo";
	
	/** The Constant ORIGIN_TYPES. */
	private static final String GEOGRAPHICAL_LEVEL_TYPES="geographicleveltypes";
	
	/** The Constant COMPLIANCE_TYPES. */
	private static final String COMPLIANCE_TYPES="compliancetypes";
	
	/** The Constant LEVEL_CODES. */
	private static final String LEVEL_CODES="levelcodes";
	
	/** The Constant GLOBAL_PARAMETERS. */
	private static final String PARAMETER_CODES="parametercodes";
	
	private static final String APPLICABLE_TRANSACTIONS="applicabletransactions";
	
	private static final String CATEGORIES="categories";
	
	private static final String EMB_DETAILS="embargodetails";

	private static final String EMABRGO_FILTER="embargofilter";
	
	private static final String KEY_INDEXMAP="index";

	private static final String KEY_TOTALRECORDS="totalrecords";
	
	private static final String LEFT_PANEL_PARAMETERS="leftPanelParameters";
	
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "dayofoperationapplicableon";
	
	private static final String EMBARGO_SEARCH_VO = "embargoSearchVo";
	
	private static final String REGULATORY_COMPOSE_MESSAGES = "regulatoryCOmposeMessages";
	
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	
	private static final String ULD_POS = "shared.uld.displayuldgroup"; //Added by A-8810 for IASCB-6097 
	
	private static final String RULE_TYPES="ruletypes";
	
	private static final String REGULATORY_COMPLIANCE="regulatoryCompliance";
	
	private static final String GROUP_DETAILS= "reco.defaults.groupingdetails";
	
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass"; //added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	
	@Override
	public String getScreenID() {
		return SCREENID; 
	}
	

	@Override  
	public String getModuleName() { 
		return MODULE_NAME;
	}

	@Override
	public Collection<OneTimeVO> getGeographicLevelTypes() {
		return (Collection<OneTimeVO>)getAttribute(GEOGRAPHICAL_LEVEL_TYPES);
	}
	

	@Override
	public void setGeographicLevelTypes(Collection<OneTimeVO> geographicLevelTypes) {
		setAttribute(GEOGRAPHICAL_LEVEL_TYPES, (ArrayList<OneTimeVO>)geographicLevelTypes);
		
	}

	@Override
	public void removeGeographicLevelTypes() {
		removeAttribute(GEOGRAPHICAL_LEVEL_TYPES);
	}

	@Override
	public Collection<OneTimeVO> getLevelCodes() {
		return (Collection<OneTimeVO>)getAttribute(LEVEL_CODES);
	}

	@Override
	public void setLevelCodes(Collection<OneTimeVO> levelCodes) {
		setAttribute(LEVEL_CODES, (ArrayList<OneTimeVO>)levelCodes);
		
	}
	
	@Override
	public void removeLevelCodes() {
		removeAttribute(LEVEL_CODES);
		
	}

	@Override
	public Collection<OneTimeVO> getComplianceTypes() {
		return (Collection<OneTimeVO>)getAttribute(COMPLIANCE_TYPES);
	}

	@Override
	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes) {
		setAttribute(COMPLIANCE_TYPES, (ArrayList<OneTimeVO>)complianceTypes);
		
	}

	@Override
	public void removeComplianceTypes() {
		removeAttribute(COMPLIANCE_TYPES);
	}
	
	@Override
	public Collection<OneTimeVO> getRuleTypes() {
		return (Collection<OneTimeVO>)getAttribute(RULE_TYPES);
	}

	@Override
	public void setRuleTypes(Collection<OneTimeVO> ruleTypes) {
		setAttribute(RULE_TYPES, (ArrayList<OneTimeVO>)ruleTypes);	
	}

	@Override
	public void removeRuleTypes() {
		removeAttribute(RULE_TYPES);
	}
	@Override
	public Collection<OneTimeVO> getParameterCodes() {
		return (Collection<OneTimeVO>)getAttribute(PARAMETER_CODES);
	}
	
	@Override
	public void setParameterCodes(
			Collection<OneTimeVO> parameterCodes) {
		setAttribute(PARAMETER_CODES, (ArrayList<OneTimeVO>)parameterCodes);
		
	}
	
	@Override
	public void removeParameterCodes() {
		removeAttribute(PARAMETER_CODES);
	}
	
	@Override
	public Collection<OneTimeVO> getApplicableTransactions() {
		return (Collection<OneTimeVO>)getAttribute(APPLICABLE_TRANSACTIONS);
	}
	
	@Override
	public void setApplicableTransactions(
			Collection<OneTimeVO> applicableTransactions) {
		setAttribute(APPLICABLE_TRANSACTIONS, (ArrayList<OneTimeVO>)applicableTransactions);
		
	}
	
	@Override
	public void removeApplicableTransactions() {
		removeAttribute(APPLICABLE_TRANSACTIONS);
	}
	
	@Override
	public Collection<OneTimeVO> getCategories() {
		return (Collection<OneTimeVO>)getAttribute(CATEGORIES);
	}
	
	@Override
	public void setCategories(Collection<OneTimeVO> categories) {
		setAttribute(CATEGORIES, (ArrayList<OneTimeVO>)categories);
		
	}
	
	@Override
	public void removeCategories() {
		removeAttribute(CATEGORIES);
	}
	
	@Override
	public EmbargoFilterVO getFilterVO(){
    	return (EmbargoFilterVO)getAttribute(EMABRGO_FILTER);
    }
	@
	
	Override
	public void setFilterVO(EmbargoFilterVO vo){
		setAttribute(EMABRGO_FILTER, (EmbargoFilterVO)vo);
	}
	
	@Override
	public void removeFilterVO(){
		removeAttribute(EMABRGO_FILTER);
	}
	
	@Override
	public List<EmbargoDetailsVO> getEmabrgoDetailVOs(){
    	return (ArrayList<EmbargoDetailsVO>)getAttribute(EMB_DETAILS);
    }

	@Override
	public void setEmabrgoDetailVOs(List<EmbargoDetailsVO> paramCode){
		setAttribute(EMB_DETAILS, (ArrayList<EmbargoDetailsVO>)paramCode);
	}
	
	@Override
	public void removeEmabrgoDetailVOs(){
		removeAttribute(EMB_DETAILS);
	}
	
	@Override
	public HashMap<String,String>  getIndexMap(){
	    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	@Override
	public void setIndexMap(HashMap<String,String>  indexmap) {
	    setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexmap);
	}
	
	public void removeIndexMap(){
		removeAttribute(KEY_INDEXMAP);
	}
	
	public Integer getTotalRecords() {
			return getAttribute(KEY_TOTALRECORDS);
	}

	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
	
	public void removeTotalRecords(){
		removeAttribute(KEY_TOTALRECORDS);
	}
	
	public Collection<OneTimeVO> getLeftPanelParameters() {
		return (Collection<OneTimeVO>)getAttribute(LEFT_PANEL_PARAMETERS);
	}
	
	public void setLeftPanelParameters(
			Collection<OneTimeVO> parameterCodes) {
		setAttribute(LEFT_PANEL_PARAMETERS, (ArrayList<OneTimeVO>)parameterCodes);
		
	}
	
	public void removeLeftPanelParameters() {
		removeAttribute(LEFT_PANEL_PARAMETERS);
	}
	
	public Collection<OneTimeVO> getDayOfOperationApplicableOn() {
		return (Collection<OneTimeVO>)getAttribute(DAY_OF_OPERATION_APPLICABLE_ON);
	}

	public void setDayOfOperationApplicableOn(Collection<OneTimeVO> dayOfOperationApplicableOn) {
		 setAttribute(DAY_OF_OPERATION_APPLICABLE_ON, (ArrayList<OneTimeVO>)dayOfOperationApplicableOn);
	}
	
	public void removeDayOfOperationApplicableOn() {
		removeAttribute(DAY_OF_OPERATION_APPLICABLE_ON);
	}
	
	public EmbargoSearchVO getEmbargoSearchVO() {
		return (EmbargoSearchVO)getAttribute(EMBARGO_SEARCH_VO);
	}

	public void setEmbargoSearchVO(EmbargoSearchVO embargoSearchVO) {
		 setAttribute(EMBARGO_SEARCH_VO, (EmbargoSearchVO)embargoSearchVO);
	}
	
	public void removeEmbargoSearchVO() {
		removeAttribute(EMBARGO_SEARCH_VO);
	}
	
	public List<RegulatoryMessageVO> getRegulatoryComposeMessages() {
		return (List<RegulatoryMessageVO>)getAttribute(REGULATORY_COMPOSE_MESSAGES);
	}

	public void setRegulatoryComposeMessages(List<RegulatoryMessageVO> regulatoryMessages) {
		 setAttribute(REGULATORY_COMPOSE_MESSAGES, (ArrayList<RegulatoryMessageVO>) regulatoryMessages);
	}
	
	public void removeRegulatoryComposeMessages() {
		removeAttribute(REGULATORY_COMPOSE_MESSAGES);
	}
	
	public Collection<OneTimeVO> getFlightTypes() {
		return (Collection<OneTimeVO>)getAttribute(FLIGHT_TYPE);
	}
	 
	public void setFlightTypes(Collection<OneTimeVO> flightTypes) {
		setAttribute(FLIGHT_TYPE, (ArrayList<OneTimeVO>)flightTypes);
	}
	 
	public void removeFlightTypes() {
		removeAttribute(FLIGHT_TYPE);
	}
	
	public Page<EmbargoDetailsVO> getRegulatoryComplianceRules(){
    	return (Page<EmbargoDetailsVO>)getAttribute(REGULATORY_COMPLIANCE);
    }

	public void setRegulatoryComplianceRules(Page<EmbargoDetailsVO> embargoDetails){
		setAttribute(REGULATORY_COMPLIANCE, (Page<EmbargoDetailsVO>)embargoDetails);
	}

	public void removeRegulatoryComplianceRules(){
		removeAttribute(REGULATORY_COMPLIANCE);
	}
	//added for ICRD-254170
	public Collection<String> getGroupDetails() {
		return (Collection<String>)getAttribute(GROUP_DETAILS);
	}
	public void setGroupDetails(Collection<String> groupingDetails) {
		setAttribute(GROUP_DETAILS, (ArrayList<String>)groupingDetails);
	}

	//Added by A-8810 for IASCB-6097 starts here
	@Override
	public Collection<OneTimeVO> getUldPos() {
		return (Collection<OneTimeVO>)getAttribute(ULD_POS);
	}


	@Override
	public void setUldPos(Collection<OneTimeVO> uldPos) {
		setAttribute(ULD_POS, (ArrayList<OneTimeVO>) uldPos);
	}


	@Override
	public void removeUldPos() {
		removeAttribute(ULD_POS);
	}

	//Added by A-8810 for IASCB-6097 ends here
	
	@Override
	public Collection<OneTimeVO> getServiceCargoClass() {
		return (Collection<OneTimeVO>)getAttribute(SERVICE_CARGO_CLASS);
	}
	@Override
	public void setServiceCargoClass(Collection<OneTimeVO> serviceCargoClass) {
		setAttribute(SERVICE_CARGO_CLASS, (ArrayList<OneTimeVO>)serviceCargoClass);
	}
	@Override
	public void removeServiceCargoClass() {
		removeAttribute(SERVICE_CARGO_CLASS);
	}
	
	
	@Override
	public Collection<OneTimeVO> getShipmentType() {
		return (Collection<OneTimeVO>)getAttribute(SHIPMENT_TYPE);
	}
	@Override
	public void setShipmentType(Collection<OneTimeVO> shipmentType) {
		setAttribute(SHIPMENT_TYPE, (ArrayList<OneTimeVO>)shipmentType);
	}
	@Override
	public void removeShipmentType() {
		removeAttribute(SHIPMENT_TYPE);
	}
	
}
