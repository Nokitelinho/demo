/*
 * MaintainEmbargoSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;

/**
 * @author A-1366
 *
 */
public class MaintainEmbargoRulesSessionImpl extends AbstractScreenSession
        implements MaintainEmbargoRulesSession {

	private static final String LEVEL_CODE="levelcodes";
	private static final String PARAMETER_CODE="parametercodes";
	private static final String APPLICABLE_CODE="applicablecode";
	private static final String PARAM_DETAILS="embargoparameters";
	private static final String EMBARGO_DETAILS="embargodetails";
	private static final String GLOBAL_DETAILS="globalparameters";
	private static final String VALUES="parametervalues";
	private static final String EMBARGO_STATUS="embargostatus";
	private static final String EMBARGOVO="embargovo";
	private static final String DETAILSBUTTON="detailsbutton";
	private static final String ISSAVED="issaved";
	private static final String GEOGRAPHIC_LEVEL_TYPES="reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "dayofoperationapplicableon";
	private static final String VIA_POINT_TYPES = "viapointtypes";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String ULD_POS = "shared.uld.displayuldgroup";	//Added by A-8810 for IASCB-6097 
	private static final String MAL_CLS = "mailtracking.defaults.mailclass";
	private static final String MAL_CAT = "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP = "mailtracking.defaults.mailsubclassgroup";
	private static final String GEOGRAPHIC_LEVEL = "geographiclevel";
	private static final String ADMIN_USER_LANGUAGES = "adminUserlanguages";
	private static final String MULTI_LANG_EMBARGO = "localLanguageEmbargo";
	private static final String WEIGHTTYPE_APPLICABLEON = "weighttypeapplicableon";	  
String APPLICABLE_LEVELS_FOR_PARAMETERS = "applicableLevelsForParameters";//added by A-7924 as part of ICRD-299901
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass";
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	private static final String SERVICE_TYPE = "message.ssim.servicetype";
	private static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "message.ssim.serviceTypeForTechnicalStop";
	private static final String UNID_PACKGING_GROUP = "shared.dgr.unid.packaginggroup";
	private static final String UNID_SUB_RISK = "unidsubrisk";
	/**
     * This method returns the SCREEN ID for the Maintain Product screen
     */
    public String getScreenID(){
        return "reco.defaults.maintainembargo";
    }

    /**
     * This method returns the MODULE name for the Maintain Product screen
     */
    public String getModuleName(){
        return "reco.defaults";
    }
    /**
     * This method returns the level code
     * @return  Collection<OneTimeVO>
     */
    public Collection<OneTimeVO> getLevelCode(){
    	return (Collection<OneTimeVO>)getAttribute(LEVEL_CODE);
    }
    /**
     * This method sets the level code
     * @param  levelCode to set
     */
	public void setLevelCode(Collection<OneTimeVO> levelCode){
		setAttribute(LEVEL_CODE, (ArrayList<OneTimeVO>)levelCode);
	}
	/**
     * This method removes the level code
     */
	public void removeLevelCode(){
		removeAttribute(LEVEL_CODE);
	}
	/**
     * This method returns the parameter code
     * @return  Collection<OneTimeVO>
     */
	 public Collection<OneTimeVO> getParameterCode(){
	    	return (Collection<OneTimeVO>)getAttribute(PARAMETER_CODE);
    }
    /**
     * This method sets the parameterCode
     * @param  parameterCode to set
     */
	public void setParameterCode(Collection<OneTimeVO> parameterCode){
		setAttribute(PARAMETER_CODE, (ArrayList<OneTimeVO>)parameterCode);
	}
	/**
     * This method removes the ParameterCode
     */
	public void removeParameterCode(){
		removeAttribute(PARAMETER_CODE);
	}
	/**
     * This method returns the ApplicableCode
     * @return  Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getApplicableCode(){
	    	return (Collection<OneTimeVO>)getAttribute(APPLICABLE_CODE);
    }
	/**
     * This method sets the ApplicableCode
     * @param  applicableCode to set
     */
	public void setApplicableCode(Collection<OneTimeVO> applicableCode){
		setAttribute(APPLICABLE_CODE, (ArrayList<OneTimeVO>)applicableCode);
	}
	/**
     * This method removes the ApplicableCode
     */
	public void removeApplicableCode(){
		removeAttribute(APPLICABLE_CODE);
	}
	/**
     * This method returns the EmbargoParameterVos
     * @return  Collection<EmbargoParameterVO>
     */
	public ArrayList<EmbargoParameterVO> getEmbargoParameterVos(){
	    	return (ArrayList<EmbargoParameterVO>)getAttribute(PARAM_DETAILS);
	 }
	/**
     * This method sets the EmbargoParameterVos
     * @param  embargoParameterVos to set
     */
	public void setEmbargoParameterVos(ArrayList<EmbargoParameterVO> embargoParameterVos){
		setAttribute(PARAM_DETAILS, (ArrayList<EmbargoParameterVO>)embargoParameterVos);
	}
	/**
     * This method removes the EmbargoParameterVos
     */
	public void removeEmbargoParameterVos(){
		removeAttribute(PARAM_DETAILS);
	}
	/**
     * This method returns the EmbargoVos
     * @return  Collection<EmbargoVO>
     */
	public Collection<EmbargoRulesVO> getEmbargoVos(){
	    	return (Collection<EmbargoRulesVO>)getAttribute(EMBARGO_DETAILS);
	 }
	/**
     * This method sets the embargoVos
     * @param  embargoVos to set
     */
	public void setEmbargoVos(Collection<EmbargoRulesVO> embargoVos){
		setAttribute(EMBARGO_DETAILS, (ArrayList<EmbargoRulesVO>)embargoVos);
	}
	/**
     * This method removes the embargoVos
     */
	public void removeEmbargoVos(){
		removeAttribute(EMBARGO_DETAILS);
	}
	/**
     * This method returns the EmbargoGlobalParameterVOs
     * @return  Collection<EmbargoGlobalParameterVO>
     */
	public Collection<EmbargoGlobalParameterVO> getGlobalParameters(){
	    	return (Collection<EmbargoGlobalParameterVO>)getAttribute(GLOBAL_DETAILS);
	 }
	/**
     * This method sets the EmbargoGlobalParameterVOs
     * @param globalParameters to set
     */
	public void setGlobalParameters(Collection<EmbargoGlobalParameterVO> globalParameters){
		setAttribute(GLOBAL_DETAILS, (ArrayList<EmbargoGlobalParameterVO>)globalParameters);
	}
	/**
     * This method removes the EmbargoGlobalParameterVOs
     */
	public void removeGlobalParameters(){
		removeAttribute(GLOBAL_DETAILS);
	}
	/**
     * This method returns the Values
     * @return  Collection<String>
     */
	public Collection<String> getValues(){
	    	return (Collection<String>)getAttribute(VALUES);
	 }
	/**
     * This method sets the Values
     * @param  values to set
     */
	public void setValues(Collection<String> values){
		setAttribute(VALUES, (ArrayList<String>)values);
	}
	/**
     * This method removes the Values
     */
	public void removeValues(){
		removeAttribute(VALUES);
	}
	/**
     * This method returns the EmbargoStatus
     * @return  Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getEmbargoStatus(){
    	return (Collection<OneTimeVO>)getAttribute(EMBARGO_STATUS);
}
	/**
     * This method sets the embargoStatus
     * @param  embargoStatus to set
     */
   public void setEmbargoStatus(Collection<OneTimeVO> embargoStatus){
	  setAttribute(EMBARGO_STATUS, (ArrayList<OneTimeVO>)embargoStatus);
   }
   /**
    * This method removes EmbargoStatus
    */
   public void removeEmbargoStatus(){
	removeAttribute(EMBARGO_STATUS);
   }
   /**
    * This method returns the EmbargoVO
    * @return  EmbargoVO
    */
   public EmbargoRulesVO getEmbargoVo(){
   	return (EmbargoRulesVO)getAttribute(EMBARGOVO);
   }
  /**
   * This method sets the embargoVo
   * @param embargoVo to set
   */
  public void setEmbargoVo(EmbargoRulesVO embargoVo){
	  setAttribute(EMBARGOVO, embargoVo);
  }
  /**
   * This method removes EmbargoVo
   */
  public void removeEmbargoVo(){
	removeAttribute(EMBARGOVO);
  }
  /**
   * This method returns the EmbargoVO
   * @return  EmbargoVO
   */
  public boolean getDetailsButton(){
  	return (Boolean)getAttribute(DETAILSBUTTON);
  }
 /**
  * This method sets the embargoVo
  * @param embargoVo to set
  */
  public void setDetailsButton(boolean enable){
	  setAttribute(DETAILSBUTTON, enable);
  }
 /**
  * This method removes EmbargoVo
  */
  public void removeDetailsButton(){
	removeAttribute(DETAILSBUTTON);
  }
  public String getIsSaved(){
	 return (String)getAttribute(ISSAVED);
  }
 /**
  * This method sets the embargoVo
  * @param embargoVo to set
  */
  public void setIsSaved(String refNum){
	 setAttribute(ISSAVED, refNum);
 }
 /**
  * This method removes EmbargoVo
  */
  public void removeIsSaved(){
	 removeAttribute(ISSAVED);
  }
	 /**
	  * This method returns the EmbargoStatus
	  * @return  Collection<OneTimeVO>
	  */
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
	/**
	 * @return Collection<OneTimeVO>
	 */
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
	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getGeographicLevel() {
		return (Collection<OneTimeVO>)getAttribute(GEOGRAPHIC_LEVEL);
	}
	/**
	 * @param GeographicLevel
	 */
	public void setGeographicLevel(Collection<OneTimeVO> geographiclevel) {
		setAttribute(GEOGRAPHIC_LEVEL, (ArrayList<OneTimeVO>)geographiclevel);
	}
	/**
	 * Method to remove GeographicLevel
	 */
	public void removeGeographicLevel() {
		removeAttribute(GEOGRAPHIC_LEVEL);
	}
	/**
	 * 	Method		:	MaintainEmbargoRulesSession.setAdminUserlanguages
	 *	Added by 	:	a-7815 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param adminUserlanguages 
	 *	Return type	: 	void
	 */
	public void setAdminUserlanguages(Collection<OneTimeVO> adminUserlanguages) {
		setAttribute(ADMIN_USER_LANGUAGES, (ArrayList<OneTimeVO>)adminUserlanguages);
	}
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getAdminUserlanguages
	 *	Added by 	:	a-7815 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getAdminUserlanguages() {
		return (Collection<OneTimeVO>)getAttribute(ADMIN_USER_LANGUAGES);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession#setLocalLanguageEmbargo(java.util.Map)
	 *	Added by 			: a-7815 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param localLanguageEmbargo
	 */
	public void setLocalLanguageEmbargo(Map<String, String> localLanguageEmbargo) {
		setAttribute(MULTI_LANG_EMBARGO, (HashMap<String, String>)localLanguageEmbargo);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession#getLocalLanguageEmbargo()
	 *	Added by 			: a-7815 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public Map<String, String>  getLocalLanguageEmbargo(){
		return (Map<String, String>)getAttribute(MULTI_LANG_EMBARGO);
	}

	//Added by A-8130 for ICRD-232462
	public Collection<OneTimeVO> getWeightsApplicableOn() {
		return (Collection<OneTimeVO>)getAttribute(WEIGHTTYPE_APPLICABLEON);
	}
	public void setWeightsApplicableOn(Collection<OneTimeVO> weightsApplicableOn) {
		setAttribute(WEIGHTTYPE_APPLICABLEON, (ArrayList<OneTimeVO>)weightsApplicableOn);
  }
   /**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getApplicableLevelsForParameters
	 *	Added by 	:	A-7924 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	@Override
	public Collection<OneTimeVO> getApplicableLevelsForParameters() {
		return (Collection<OneTimeVO>)getAttribute(APPLICABLE_LEVELS_FOR_PARAMETERS);
	}
	/**
	 * 	Method		:	MaintainEmbargoRulesSession.setApplicableLevelsForParameters
	 *	Added by 	:	A-7924 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	void
	 */
	@Override
	public void setApplicableLevelsForParameters(Collection<OneTimeVO> applicableLevelsForParameters) {
		setAttribute(APPLICABLE_LEVELS_FOR_PARAMETERS, (ArrayList<OneTimeVO>)applicableLevelsForParameters);
		
	}
	/**
	 * 	Method		:	MaintainEmbargoRulesSession.removeApplicableLevelsForParameters
	 *	Added by 	:	A-7924 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	
	 *	Return type	: 	void
	 */
	@Override
	public void removeApplicableLevelsForParameters() {
		removeAttribute(APPLICABLE_LEVELS_FOR_PARAMETERS);
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
