/**MaintainUldDiscrepancySession.java Created on Dec 01, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public interface MaintainUldDiscrepancySession extends ScreenSession {
	/**
	 * 
	 * @return
	 */
	public String getScreenID();
	/**
	 * 
	 * @return
	 */
	public String getModuleName();
	/**
	 * 
	 * @return
	 */  
	public Page<ULDDiscrepancyVO> getULDDiscrepancyVODetails();
	/**
	 * 
	 * @param productDetails
	 */
	public void setULDDiscrepancyVODetails(Page<ULDDiscrepancyVO> productDetails);
	/**
	 * 
	 *
	 */
	public void removeULDDiscrepancyVODetails() ;
	/**
	 * 
	 * @return
	 */
	public ULDDiscrepancyFilterVO getULDDiscrepancyFilterVODetails();
	/**
	 * 
	 * @param productDetails
	 */
	public void setULDDiscrepancyFilterVODetails(ULDDiscrepancyFilterVO productDetails);
	/**
	 * 
	 *
	 */
	public void removeULDDiscrepancyFilterVODetails();		
	/**
	 * 
	 * @return
	 */
	public String getPageURL();
	/**
	 * 
	 * @param pageUrl
	 */  
	public void setPageURL(String pageUrl);
	/**
	 * 
	 * @return
	 */
	public ArrayList<ULDDiscrepancyVO> getULDDiscrepancyVOs();
	/**
	 * 
	 * @param uldDiscrepancyVOs
	 */
	public void setULDDiscrepancyVOs(ArrayList<ULDDiscrepancyVO> uldDiscrepancyVOs);
	/**
	 * 
	 * @return
	 */
	public ULDDiscrepancyVO getDiscrepancyDetails();
	/**
	 * 
	 * @param productDetails
	 */     
	public void setDiscrepancyDetails(ULDDiscrepancyVO productDetails);
	/**
	 * 
	 *
	 */  
	public void removeDiscrepancyDetails();	
	/**
	 * 
	 * @param discrepancyCode
	 */
	public void setDiscrepancyCode(Collection<OneTimeVO> discrepancyCode);
	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getDiscrepancyCode();
	/**
	 * 
	 * @return
	 */
	public String getCloseFlag();
	/**
	 * 
	 * @param pageUrl
	 */
	public void setCloseFlag(String pageUrl);
	/**
	 * 
	 * @param scmDetailsVO
	 */
	public void setSCMULDReconcileDetailsVO(ULDSCMReconcileDetailsVO scmDetailsVO);
	/**
	 * 
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getSCMULDReconcileDetailsVO();

	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	/***
	 * 
	 * @return
	 */
	public String getPolLocation();
	/**
	 * 
	 * @param polLocation
	 */
	public void setPolLocation(String polLocation);
	/***
	 * 
	 * @return
	 */
	public String getPouLocation();
	/**
	 * 
	 * @param polLocation
	 */
	public void setPouLocation(String pouLocation);

	/**
	 * 
	 * @return
	 */
	public String getFacilityType();
	/**
	 * 
	 * @param facilityType
	 */
	public void setFacilityType(String facilityType);
	/**
	 * 
	 * @return
	 */
	public Page<ULDDiscrepancyVO> getFacilityTypes();

	/**
	 * 
	 * @param facilityTypes
	 */
	public void setFacilityTypes(Page<ULDDiscrepancyVO> facilityTypes);
	/**
	 * 
	 * @param uldNumber
	 */
	public void setUldNumber(String uldNumber);
	/**
	 * 
	 * @return
	 */
	public String getUldNumber();

	public String getMode();

	public void setMode(String mode);
}
