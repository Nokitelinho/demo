/*
 * ListEmbargoSession.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
* @author A-1747
*
*/

public interface ListEmbargoRulesSession extends ScreenSession {
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
	
	public EmbargoFilterVO getFilterVO();
    
	public void setFilterVO(EmbargoFilterVO vo);
	
	public void removeFilterVO();
	/**
	 * @return Collection<String>
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
	 * @return Page<EmbargoDetailsVO>
	 */
	public Page<EmbargoDetailsVO> getEmabrgoDetailVOs();

	/**
	 * @param paramCode
	 */
	public void setEmabrgoDetailVOs(Page<EmbargoDetailsVO> paramCode);

	/**
	 * Methods for removing embargodetailsVO
	 */
	public void removeEmabrgoDetailVOs();

	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getStatus();
	/**
	 * Methods for setting levelCode
	 */
	public void setStatus(Collection<OneTimeVO> levelCode);
	/**
	 * Methods for removing level
	 */
	public void removeStatus();

	/**
	 * @return HashMap<String,String>
	 */
	public HashMap<String,String>  getIndexMap();

	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String,String>  indexmap);

	/**
	 * Methods for removing indexmap
	 */
	public void removeIndexMap();
	
	
	// added by A-5175 for  CR 21634 starts
	public Integer getTotalRecords();

	public void setTotalRecords(int totalRecords);
	// added by A-5175 for  CR 21634 ends
	
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
	
	public Collection<OneTimeVO> getRuleType();
	
	public void setRuleType(Collection<OneTimeVO> ruleType);
		
	public void removeRuleType();

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
	//added for ICRD-254170
	public Collection<String> getGroupDetails();
	public void setGroupDetails(Collection<String> groupingDetails);
	
	
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
