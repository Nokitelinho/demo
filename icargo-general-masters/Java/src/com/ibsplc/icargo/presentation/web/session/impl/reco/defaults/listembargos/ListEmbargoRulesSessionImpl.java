/*
 * ListEmbargoSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.listembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1747
 *SessionImplementation for ListEmbargo
 */

public class ListEmbargoRulesSessionImpl extends AbstractScreenSession
	implements ListEmbargoRulesSession {
	private static final String GLOBAL_DETAILS="globalparameters";
	private static final String LEVEL_CODE="levelcodes";
	private static final String EMB_DETAILS="embargodetails";
	private static final String STATUS_DETAILS="statusdetails";
	private static final String KEY_INDEXMAP="index";
	private static final String EMABRGO_FILTER="embargofilter";
	private static final String GEOGRAPHIC_LEVEL_TYPES="reco.defaults.geographicleveltype";
	private static final String KEY_TOTALRECORDS="totalrecords";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "dayofoperationapplicableon";
	private static final String VIA_POINT_TYPES = "viapointtypes";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	private static final String RULE_TYPE = "reco.defaults.ruletype";
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String ULD_POS = "shared.uld.displayuldgroup"; //Added by A-8810 for IASCB-6097 
	private static final String MAL_CLS = "mailtracking.defaults.mailclass";
	private static final String MAL_CAT = "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP = "mailtracking.defaults.mailsubclassgroup";
	private static final String GROUP_DETAILS= "reco.defaults.groupingdetails";
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass"; //added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	private static final String SERVICE_TYPE = "message.ssim.servicetype";
	private static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "message.ssim.serviceTypeForTechnicalStop";
	private static final String UNID_PACKGING_GROUP = "shared.dgr.unid.packaginggroup";
	private static final String UNID_SUB_RISK = "unidsubrisk";
	/**
     * This method returns the SCREEN ID for the Maintain Product screen
     * @return null
     */
    public String getScreenID(){
        return null;
    }

    /**
     * This method returns the MODULE name for the Maintain Product screen
     * @return null
     */
    public String getModuleName(){
        return null;
    }

    /**MEthod to return Levelcodes
	 * @param
	 * @return Collection<OneTimeVO>
	 */

    public Collection<OneTimeVO> getLevelCode(){
    	return (Collection<OneTimeVO>)getAttribute(LEVEL_CODE);
    }
    /**
     * This method sets the LevelCodes in session
     * @param levelCode
     */
	public void setLevelCode(Collection<OneTimeVO> levelCode){
		setAttribute(LEVEL_CODE, (ArrayList<OneTimeVO>)levelCode);
	}
	/**
     * This method removes the LevelCodes in session
     */
	public void removeLevelCode(){
		removeAttribute(LEVEL_CODE);
	}
	
	 /**MEthod to return Levelcodes
	 * @param
	 * @return Collection<OneTimeVO>
	 */

    public EmbargoFilterVO getFilterVO(){
    	return (EmbargoFilterVO)getAttribute(EMABRGO_FILTER);
    }
    /**
     * This method sets the LevelCodes in session
     * @param levelCode
     */
	public void setFilterVO(EmbargoFilterVO vo){
		setAttribute(EMABRGO_FILTER, (EmbargoFilterVO)vo);
	}
	/**
     * This method removes the LevelCodes in session
     */
	public void removeFilterVO(){
		removeAttribute(EMABRGO_FILTER);
	}
	/**
     * This method returns the globalparameters in session
     * @return Collection<String>
     */
	public Collection<EmbargoGlobalParameterVO> getGlobalParameters(){
    	return (Collection<EmbargoGlobalParameterVO>)getAttribute(GLOBAL_DETAILS);
    }
	/**
     * This method sets the globalparameters in session
     * @param paramCode
     */
	public void setGlobalParameters(Collection<EmbargoGlobalParameterVO> paramCode){
		setAttribute(GLOBAL_DETAILS, (ArrayList<EmbargoGlobalParameterVO>)paramCode);
	}
	/**
     * This method removes the globalparameters in session
     */
	public void removeGlobalParameters(){
		removeAttribute(GLOBAL_DETAILS);
	}
	/**
     * This method returns the embargodetailsvo in session
     * @return Page<EmbargoDetailsVO>
     */

	public Page<EmbargoDetailsVO> getEmabrgoDetailVOs(){
    	return (Page<EmbargoDetailsVO>)getAttribute(EMB_DETAILS);
    }
	/**
     * This method sets the embargodetailsvo in session
     * @param paramCode
     */
	public void setEmabrgoDetailVOs(Page<EmbargoDetailsVO> paramCode){
		setAttribute(EMB_DETAILS, (Page<EmbargoDetailsVO>)paramCode);
	}
	/**
     * This method removes the embargodetailsVos in session
     */
	public void removeEmabrgoDetailVOs(){
		removeAttribute(EMB_DETAILS);
	}
	/**
     * This returns  the status in session
     * @return Collection<OneTimeVO>
     */

	public Collection<OneTimeVO> getStatus(){
    	return (Collection<OneTimeVO>)getAttribute(STATUS_DETAILS);
    }
	/**
     * This method sets the status in session
     * @param paramCode
     */
	public void setStatus(Collection<OneTimeVO> paramCode){
		setAttribute(STATUS_DETAILS, (ArrayList<OneTimeVO>)paramCode);
	}
	/**
     * This method removes the status in session
     */
	public void removeStatus(){
		removeAttribute(STATUS_DETAILS);
	}
	 /**
     * Method used to get indexMap
     * @return KEY_INDEXMAP - HashMap<String,String>
     */
	public HashMap<String,String>  getIndexMap(){
	    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String,String>  indexmap) {
	    setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexmap);
	}
	/**
     * This method removes the indexMAp in session
     */
	public void removeIndexMap(){
		removeAttribute(KEY_INDEXMAP);
	}
	

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoSession#getTotalRecords()
	 *	Added by 			: A-5175 on 30-Oct-2012
	 * 	Used for 	:icrd-21634
	 *	Parameters	:	@return 
	 */

	public Integer getTotalRecords() {
			return getAttribute(KEY_TOTALRECORDS);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoSession#setTotalRecords(int)
	 *	Added by 			: A-5175 on 30-Oct-2012
	 * 	Used for 	:   icrd-21634
	 *	Parameters	:	@param totalRecords 
	 */

	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
	
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getDayOfOperationApplicableOn() {
		return (Collection<OneTimeVO>)getAttribute(DAY_OF_OPERATION_APPLICABLE_ON);
	}
	 
	 /**
	 * @param Day Of Operation Applicable On
	 */
	public void setDayOfOperationApplicableOn(Collection<OneTimeVO> dayOfOperationApplicableOn) {
		 setAttribute(DAY_OF_OPERATION_APPLICABLE_ON, (ArrayList<OneTimeVO>)dayOfOperationApplicableOn);
	}
	 
	/**
	 * Method to remove Day Of Operation Applicable On
	 */
	public void removeDayOfOperationApplicableOn() {
		removeAttribute(DAY_OF_OPERATION_APPLICABLE_ON);
	}
	
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getViaPointTypes() {
		return (Collection<OneTimeVO>)getAttribute(VIA_POINT_TYPES);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setViaPointTypes(Collection<OneTimeVO> viaPointTypes) {
		setAttribute(VIA_POINT_TYPES, (ArrayList<OneTimeVO>)viaPointTypes);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeViaPointTypes() {
		removeAttribute(VIA_POINT_TYPES);
	}

	public Collection<OneTimeVO> getCategoryTypes() {
		return (Collection<OneTimeVO>)getAttribute(CATEGORY_TYPES);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setCategoryTypes(Collection<OneTimeVO> categoryTypes) {
		setAttribute(CATEGORY_TYPES, (ArrayList<OneTimeVO>)categoryTypes);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeCategoryTypes() {
		removeAttribute(CATEGORY_TYPES);
	}
	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getComplianceTypes() {
		return (Collection<OneTimeVO>)getAttribute(COMPLIANCE_TYPES);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes) {
		setAttribute(COMPLIANCE_TYPES, (ArrayList<OneTimeVO>)complianceTypes);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeComplianceTypes() {
		removeAttribute(COMPLIANCE_TYPES);
	}
	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getApplicableTransactions() {
		return (Collection<OneTimeVO>)getAttribute(APPLICABLE_TRANSACTIONS);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setApplicableTransactions(Collection<OneTimeVO> applicableTransactions) {
		setAttribute(APPLICABLE_TRANSACTIONS, (ArrayList<OneTimeVO>)applicableTransactions);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeApplicableTransactions() {
		removeAttribute(APPLICABLE_TRANSACTIONS);
	}
	public Collection<OneTimeVO> getEmbargoParameters() {
		return (Collection<OneTimeVO>)getAttribute(EMBARGO_PARAMETERS);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setEmbargoParameters(Collection<OneTimeVO> embargoParameters) {
		setAttribute(EMBARGO_PARAMETERS, (ArrayList<OneTimeVO>)embargoParameters);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeEmbargoParameters() {
		removeAttribute(EMBARGO_PARAMETERS);
	}
	public Collection<OneTimeVO> getGeographicLevelType(){
	 	return (Collection<OneTimeVO>)getAttribute(GEOGRAPHIC_LEVEL_TYPES);
	}
		/**
	  * This method sets the OriginTypes
	  * @param  OriginTypes to set
	  */
	public void setGeographicLevelType(Collection<OneTimeVO> geographicLevelType){
		  setAttribute(GEOGRAPHIC_LEVEL_TYPES, (ArrayList<OneTimeVO>)geographicLevelType);
	}
	/**
	 * This method removes OriginTypes
	 */
	public void removeGeographicLevelType(){
		removeAttribute(GEOGRAPHIC_LEVEL_TYPES);
	}
	
	public Collection<OneTimeVO> getRuleType(){
	 	return (Collection<OneTimeVO>)getAttribute(RULE_TYPE);
	}
		/**
	  * This method sets the OriginTypes
	  * @param  OriginTypes to set
	  */
	public void setRuleType(Collection<OneTimeVO> ruleType){
		  setAttribute(RULE_TYPE, (ArrayList<OneTimeVO>)ruleType);
	}
	/**
	 * This method removes OriginTypes
	 */
	public void removeRuleType(){
		removeAttribute(RULE_TYPE);
	}
	
	public Collection<OneTimeVO> getFlightTypes() {
		return (Collection<OneTimeVO>)getAttribute(FLIGHT_TYPE);
	}
	 
	 /**
	 * @param Via Point Types
	 */
	public void setFlightTypes(Collection<OneTimeVO> flightTypes) {
		setAttribute(FLIGHT_TYPE, (ArrayList<OneTimeVO>)flightTypes);
	}
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeFlightTypes() {
		removeAttribute(FLIGHT_TYPE);
	}
	@Override
	public Collection<OneTimeVO> getMailClass() {
		// TODO Auto-generated method stub
		return (Collection<OneTimeVO>)getAttribute(MAL_CLS);
	}
	@Override
	public void setMailClass(Collection<OneTimeVO> mailClass) {
		setAttribute(MAL_CLS, (ArrayList<OneTimeVO>)mailClass);
	}
	@Override
	public void removeMailClass() {
		removeAttribute(MAL_CLS);
	}
	@Override
	public Collection<OneTimeVO> getMailSubClassGrp() {
		// TODO Auto-generated method stub
		return (Collection<OneTimeVO>)getAttribute(MAL_SUBCLS_GRP);
	}
	@Override
	public void setMailSubClassGrp(Collection<OneTimeVO> mailSubClassGrp) {
		setAttribute(MAL_SUBCLS_GRP, (ArrayList<OneTimeVO>)mailSubClassGrp);
	}
	@Override
	public void removeMailSubClassGrp() {
		removeAttribute(MAL_SUBCLS_GRP);
	}
	@Override
	public Collection<OneTimeVO> getMailCategory() {
		// TODO Auto-generated method stub
		return (Collection<OneTimeVO>)getAttribute(MAL_CAT);
	}
	@Override
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(MAL_CAT, (ArrayList<OneTimeVO>)mailCategory);
	}
	@Override
	public void removeMailCategory() {
		removeAttribute(MAL_CAT);
	}
	//added for ICRD-254170
	@Override
	public Collection<String> getGroupDetails() {
		return (Collection<String>)getAttribute(GROUP_DETAILS);
	}
	@Override
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
	public Collection<OneTimeVO> getServiceType() {
		return (Collection<OneTimeVO>)getAttribute(SERVICE_TYPE);
	}
	@Override
	public void setServiceType(Collection<OneTimeVO> serviceType) {
		setAttribute(SERVICE_TYPE, (ArrayList<OneTimeVO>)serviceType);
	}
	@Override
	public void removeServiceType() {
		removeAttribute(SERVICE_TYPE);
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
	@Override
	public Collection<OneTimeVO> getServiceTypeForTechnicalStop() {
		return (Collection<OneTimeVO>)getAttribute(SERVICE_TYPE_FOR_TECHNICAL_STOP);
	}
	@Override
	public void setServiceTypeForTechnicalStop(Collection<OneTimeVO> serviceType) {
		setAttribute(SERVICE_TYPE_FOR_TECHNICAL_STOP, (ArrayList<OneTimeVO>)serviceType);
	}
	@Override
	public void removeServiceTypeForTechnicalStop() {
		removeAttribute(SERVICE_TYPE_FOR_TECHNICAL_STOP);
	}
	@Override
	public Collection<OneTimeVO> getUnPg() {
		return (Collection<OneTimeVO>)getAttribute(UNID_PACKGING_GROUP);
	}

	@Override
	public void setUnPg(Collection<OneTimeVO> unPg) {
		setAttribute(UNID_PACKGING_GROUP, (ArrayList<OneTimeVO>)unPg);
	}

	@Override
	public void removeUnPg() {
		removeAttribute(UNID_PACKGING_GROUP);
	}
	
	@Override
	public Collection<OneTimeVO> getSubRisk() {
		return (Collection<OneTimeVO>)getAttribute(UNID_SUB_RISK);
	}

	@Override
	public void setSubRisk(Collection<OneTimeVO> subRisk) {
		setAttribute(UNID_SUB_RISK, (ArrayList<OneTimeVO>)subRisk);
		
	}

	@Override
	public void removeSubRisk() {
		removeAttribute(UNID_SUB_RISK);
	}
}
