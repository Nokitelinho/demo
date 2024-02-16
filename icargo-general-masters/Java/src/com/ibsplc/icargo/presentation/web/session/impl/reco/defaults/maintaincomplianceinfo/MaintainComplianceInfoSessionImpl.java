
package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.maintaincomplianceinfo;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo.MaintainComplianceInfoSession;

public class MaintainComplianceInfoSessionImpl extends AbstractScreenSession
    implements MaintainComplianceInfoSession {
	
	private static final String APPLICABLE_CODE="applicablecode";
	private static final String EMBARGO_DETAILS="embargodetails";
	private static final String EMBARGO_STATUS="embargostatus";
	private static final String EMBARGOVO="embargovo";
	private static final String DETAILSBUTTON="detailsbutton";
	private static final String ISSAVED="issaved";
	private static final String GEOGRAPHIC_LEVEL_TYPES="reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "dayofoperationapplicableon";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String EMBARGO_PARAMETERS="embargoparameters";

	/**
     * This method returns the SCREEN ID for the Maintain Product screen
     */
    public String getScreenID(){
        return "reco.defaults.maintaincomplianceinfo";
    }

    /**
     * This method returns the MODULE name for the Maintain Product screen
     */
    public String getModuleName(){
        return "reco.defaults";
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
	
	public Collection<EmbargoParameterVO> getEmbargoParameters(){
	    	return (Collection<EmbargoParameterVO>)getAttribute(EMBARGO_PARAMETERS);
	 }
	public void setEmbargoParameters(Collection<EmbargoParameterVO> embargoParameters){
		setAttribute(EMBARGO_PARAMETERS, (ArrayList<EmbargoParameterVO>)embargoParameters);
	}
	public void removeEmbargoParameters() {
		removeAttribute(EMBARGO_PARAMETERS);
	}
	
}