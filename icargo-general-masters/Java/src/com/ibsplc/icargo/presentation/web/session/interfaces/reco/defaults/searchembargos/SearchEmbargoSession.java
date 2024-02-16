/*
 * SearchEmbargoSession.java Created on May 13, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * The Interface SearchEmbargoSession.
 *
 * @author
 */

public interface SearchEmbargoSession extends ScreenSession {


	public Collection<OneTimeVO> getGeographicLevelTypes();

	public void setGeographicLevelTypes(Collection<OneTimeVO> geographicLevelTypes);

	public void removeGeographicLevelTypes();

	public Collection<OneTimeVO> getLevelCodes();

	public void setLevelCodes(Collection<OneTimeVO> levelCodes);

	public void removeLevelCodes();

	public Collection<OneTimeVO> getComplianceTypes();

	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes);

	public void removeComplianceTypes();

	public Collection<OneTimeVO> getRuleTypes();

	public void setRuleTypes(Collection<OneTimeVO> ruleTypes);

	public void removeRuleTypes();

	public Collection<OneTimeVO>  getParameterCodes();

	public void setParameterCodes(Collection<OneTimeVO> parameterCodes);

	public void removeParameterCodes();
	
	public Collection<OneTimeVO>  getApplicableTransactions();

	public void setApplicableTransactions(Collection<OneTimeVO> applicableTransactions);

	public void removeApplicableTransactions();
	
	public Collection<OneTimeVO>  getCategories();

	public void setCategories(Collection<OneTimeVO> categories);

	public void removeCategories();
	
	public EmbargoFilterVO getFilterVO();
    
	public void setFilterVO(EmbargoFilterVO vo);
	
	public void removeFilterVO();
	
	public List<EmbargoDetailsVO> getEmabrgoDetailVOs();

	public void setEmabrgoDetailVOs(List<EmbargoDetailsVO> embargoDetails);

	public void removeEmabrgoDetailVOs();
	
	public HashMap<String,String>  getIndexMap();

	public void setIndexMap(HashMap<String,String>  indexmap);

	public void removeIndexMap();
	
	public Integer getTotalRecords();

	public void setTotalRecords(int totalRecords);
	
	public void removeTotalRecords();
	
	public Collection<OneTimeVO>  getLeftPanelParameters();

	public void setLeftPanelParameters(Collection<OneTimeVO> leftPanelParameters);

	public void removeLeftPanelParameters();
	
	public Collection<OneTimeVO> getDayOfOperationApplicableOn();
	 
	public void setDayOfOperationApplicableOn(Collection<OneTimeVO> dayOfOperationApplicableOn);

	public void removeDayOfOperationApplicableOn();
	
	public void setEmbargoSearchVO(EmbargoSearchVO embargoSearchVO);
	
	public EmbargoSearchVO getEmbargoSearchVO();
	
	public void removeEmbargoSearchVO();
	
	public List<RegulatoryMessageVO> getRegulatoryComposeMessages();

	public void setRegulatoryComposeMessages(List<RegulatoryMessageVO> regulatoryMessages);

	public void removeRegulatoryComposeMessages();
	
	public Collection<OneTimeVO> getFlightTypes();
	
	public void setFlightTypes(Collection<OneTimeVO> flightTypes);
		
	public void removeFlightTypes();
	
	//Added by A-8810 for IASCB-6097 starts here
	
	public Collection<OneTimeVO> getUldPos();	
	public void setUldPos(Collection<OneTimeVO> uldPos);	
	public void removeUldPos();
				
	//Added by A-8810 for IASCB-6097 ends here
			
	public Page<EmbargoDetailsVO> getRegulatoryComplianceRules();
	
	public void setRegulatoryComplianceRules(Page<EmbargoDetailsVO> embargoDetails);
	
	public void removeRegulatoryComplianceRules();
	
	//added for ICRD-254170
	public Collection<String> getGroupDetails();
	
	public void setGroupDetails(Collection<String> groupingDetails);
	
	public Collection<OneTimeVO> getServiceCargoClass();
	public void setServiceCargoClass(Collection<OneTimeVO> serviceCargoClass);
	public void removeServiceCargoClass();
	
	public Collection<OneTimeVO> getShipmentType();
	public void setShipmentType(Collection<OneTimeVO> shipmentType);
	public void removeShipmentType();
}
