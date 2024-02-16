/*
 * MaintainEmbargoSession.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * Interface for MaintainEmbargoSession
 * @author A-1854
 *
 */
public interface MaintainEmbargoRulesSession extends ScreenSession {
	/**
	 * Methods for getting levelCode
	 */
	public Collection<OneTimeVO> getLevelCode();
	/**
	 * Methods for setting levelCode
	 */
	public void setLevelCode(Collection<OneTimeVO> levelCode);
	/**
	 * Methods for removing level
	 */
	public void removeLevelCode();

	/**
	 * Methods for getting levelCode
	 */
	public Collection<OneTimeVO> getParameterCode();
	/**
	 * Methods for setting levelCode
	 */
	public void setParameterCode(Collection<OneTimeVO> parameterCode);
	/**
	 * Methods for removing level
	 */
	public void removeParameterCode();

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
	public ArrayList<EmbargoParameterVO> getEmbargoParameterVos();
	/**
	 * Methods for setting levelCode
	 */
	public void setEmbargoParameterVos(ArrayList<EmbargoParameterVO> applicableCode);
	/**
	 * Methods for removing level
	 */
	public void removeEmbargoParameterVos();

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
	 * Methods for getting levelCode
	 */
	public Collection<EmbargoGlobalParameterVO> getGlobalParameters();
	/**
	 * Methods for setting levelCode
	 */
	public void setGlobalParameters(Collection<EmbargoGlobalParameterVO> applicableCode);
	/**
	 * Methods for removing level
	 */
	public void removeGlobalParameters();

	/**
	 * Methods for getting levelCode
	 */
	public Collection<String> getValues();
	/**
	 * Methods for setting levelCode
	 */
	public void setValues(Collection<String> applicableCode);
	/**
	 * Methods for removing level
	 */
	public void removeValues();
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
	
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getViaPointTypes();
	 
	 /**
	 * @param Via Point Types
	 */
	public void setViaPointTypes(Collection<OneTimeVO> viaPointTypes);
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeViaPointTypes();
	
	
	public Collection<OneTimeVO> getCategoryTypes();
	 
	 /**
	 * @param categoryTypes
	 */
	public void setCategoryTypes(Collection<OneTimeVO> categoryTypes);
	 
	/**
	 * Method to remove categoryTypes
	 */
	public void removeCategoryTypes();
	
	public Collection<OneTimeVO> getComplianceTypes();
	 
	 /**
	 * @param categoryTypes
	 */
	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes);
	 
	/**
	 * Method to remove categoryTypes
	 */
	public void removeComplianceTypes();
	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getApplicableTransactions();
		
	 
	 /**
	 * @param Via Point Types
	 */
	public void setApplicableTransactions(Collection<OneTimeVO> applicableTransactions) ;
		
	 
	/**
	 * Method to remove Via Point Types
	 */
	public void removeApplicableTransactions();
	
	public Collection<OneTimeVO> getEmbargoParameters();
		
	public void setEmbargoParameters(Collection<OneTimeVO> embargoParameters);
		
	public void removeEmbargoParameters();
		
	public Collection<OneTimeVO> getFlightTypes();
	
	public void setFlightTypes(Collection<OneTimeVO> flightTypes);
		
	public void removeFlightTypes();
	
	//Added by A-8810 for IASCB-6097 starts here
	
	public Collection<OneTimeVO> getUldPos();	
	public void setUldPos(Collection<OneTimeVO> uldPos);	
	public void removeUldPos();
		
	//Added by A-8810 for IASCB-6097 ends here
	
	public Collection<OneTimeVO> getMailClass();
	public void setMailClass(Collection<OneTimeVO> mailClass);
	public void removeMailClass();
	public Collection<OneTimeVO> getMailSubClassGrp();
	public void setMailSubClassGrp(Collection<OneTimeVO> mailSubClassGrp);
	public void removeMailSubClassGrp();
	public Collection<OneTimeVO> getMailCategory();
	public void setMailCategory(Collection<OneTimeVO> mailCategory);
	public void removeMailCategory();
	 /**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getGeographicLevel();
	 /**
	 * @param GeographicLevel
	 */
	public void setGeographicLevel(Collection<OneTimeVO> geographiclevel);
	/**
	 * Method to remove GeographicLevel
	 */
	public void removeGeographicLevel();
	/**
	 * 	Method		:	MaintainEmbargoRulesSession.setAdminUserlanguages
	 *	Added by 	:	a-7815 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param adminUserlanguages 
	 *	Return type	: 	void
	 */
	public void setAdminUserlanguages(Collection<OneTimeVO> adminUserlanguages);
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getAdminUserlanguages
	 *	Added by 	:	a-7815 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getAdminUserlanguages();
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.setLocalLanguageEmbargo
	 *	Added by 	:	a-7815 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param localLanguageEmbargo 
	 *	Return type	: 	void
	 */
	public void setLocalLanguageEmbargo(Map<String, String> localLanguageEmbargo);
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getLocalLanguageEmbargo
	 *	Added by 	:	a-7815 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,String>
	 */
	public Map<String, String>  getLocalLanguageEmbargo();
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getWeightsApplicableOn
	 *	Added by 	:	a-8130 on 06-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getWeightsApplicableOn();
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.setWeightsApplicableOn
	 *	Added by 	:	a-8130 on 06-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	void
	 */
	public void setWeightsApplicableOn(Collection<OneTimeVO> weightsApplicableOn);
		
	/**
	 * 
	 * 	Method		:	MaintainEmbargoRulesSession.getApplicableLevelsForParameters
	 *	Added by 	:	a-7924 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getApplicableLevelsForParameters();
		 
	/**
	 * 	Method		:	MaintainEmbargoRulesSession.setAdminUserlanguages
	 *	Added by 	:	a-7924 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param applicableLevelsForParameters 
	 *	Return type	: 	void
	 */
	public void setApplicableLevelsForParameters(Collection<OneTimeVO> applicableLevelsForParameters);
		 
	/**
	 * Method to remove Applicable Levels For Parameters
	*/
	public void removeApplicableLevelsForParameters();
	
	
	public Collection<OneTimeVO> getServiceCargoClass();
	public void setServiceCargoClass(Collection<OneTimeVO> serviceCargoClass);
	public void removeServiceCargoClass();
	
	public Collection<OneTimeVO> getServiceType();
	public void setServiceType(Collection<OneTimeVO> serviceType);
	public void removeServiceType();
	
	public Collection<OneTimeVO> getShipmentType();
	public void setShipmentType(Collection<OneTimeVO> shipmentType);
	public void removeShipmentType();
	
	public Collection<OneTimeVO> getServiceTypeForTechnicalStop();
	public void setServiceTypeForTechnicalStop(Collection<OneTimeVO> serviceTypeForTechnicalStop);
	public void removeServiceTypeForTechnicalStop();
	
	public Collection<OneTimeVO> getUnPg();
	public void setUnPg(Collection<OneTimeVO> unPg);
	public void removeUnPg();
	
	public Collection<OneTimeVO> getSubRisk();
	public void setSubRisk(Collection<OneTimeVO> subRisk);
	public void removeSubRisk();
}