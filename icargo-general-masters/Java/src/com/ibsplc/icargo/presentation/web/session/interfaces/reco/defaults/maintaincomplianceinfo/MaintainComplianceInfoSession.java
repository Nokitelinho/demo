
package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo;

import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

public interface MaintainComplianceInfoSession extends ScreenSession {
	
	/**
	 * Methods for getting levelCode
	 */
	public Collection<OneTimeVO> getApplicableCode();
	/**
	 * Methods for setting levelCode
	 */
	public void setApplicableCode(Collection<OneTimeVO> applicableCode);
	/**
	 * Methods for removing level
	 */
	public void removeApplicableCode();

	/**
	 * Methods for getting levelCode
	 */
	public Collection<EmbargoRulesVO> getEmbargoVos();
	/**
	 * Methods for setting levelCode
	 */
	public void setEmbargoVos(Collection<EmbargoRulesVO> applicableCode);
	/**
	 * Methods for removing level
	 */
	public void removeEmbargoVos();
	

	/**
	 * Methods for getting EmbargoStatus
	 */
	public Collection<OneTimeVO> getEmbargoStatus();
	/**
	 * Methods for setting EmbargoStatus
	 */
	public void setEmbargoStatus(Collection<OneTimeVO> embargoStatus);
	/**
	 * Methods for removing EmbargoStatus
	 */
	public void removeEmbargoStatus();
	/**
	 * Methods for getting EmbargoVO
	 */
	public EmbargoRulesVO getEmbargoVo();
	/**
	 * Methods for setting EmbargoVO
	 */
	public void setEmbargoVo(EmbargoRulesVO embargoVO);
	/**
	 * Methods for removing EmbargoVO
	 */
	public void removeEmbargoVo();

	public boolean getDetailsButton();
	 /**
	  * This method sets the embargoVo
	  * @param embargoVo to set
	  */
	 public void setDetailsButton(boolean enable);
	 /**
	  * This method removes EmbargoVo
	  */
	 public void removeDetailsButton();

	 public String getIsSaved();
	 /**
	  * This method sets the embargoVo
	  * @param embargoVo to set
	  */
	 public void setIsSaved(String refNum);
	 /**
	  * This method removes EmbargoVo
	  */
	 public void removeIsSaved();
 
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getGeographicLevelType();
	 
	 /**
	 * @param originTypes
	 */
	public void setGeographicLevelType(Collection<OneTimeVO> geographicLevelType);
	 
	/**
	 * Method to remove Origin Types
	 */
	public void removeGeographicLevelType();
	
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getDayOfOperationApplicableOn();
	 
	 /**
	 * @param Day Of Operation Applicable On
	 */
	public void setDayOfOperationApplicableOn(Collection<OneTimeVO> dayOfOperationApplicableOn);
	 
	/**
	 * Method to remove Day Of Operation Applicable On
	 */
	public void removeDayOfOperationApplicableOn();
	
	
	
	
	public Collection<OneTimeVO> getCategoryTypes();
	 
	 /**
	 * @param categoryTypes
	 */
	public void setCategoryTypes(Collection<OneTimeVO> categoryTypes);
	/**
	 * Gets the embargo parameters.
	 *
	 * @return the embargo parameters
	 */
	public Collection<EmbargoParameterVO> getEmbargoParameters();
	/**
	 * Sets the embargo parameters.
	 *
	 * @param embargoParameters the new embargo parameters
	 */
	public void setEmbargoParameters(Collection<EmbargoParameterVO> embargoParameters);
	/**
	 * Removes the embargo parameters.
	 */
	public void removeEmbargoParameters();
		
	
}