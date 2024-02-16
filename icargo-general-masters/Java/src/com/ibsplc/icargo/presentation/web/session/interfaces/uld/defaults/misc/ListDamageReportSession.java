/*
 * ListDamageReportSession.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1617
 *
 */
public interface ListDamageReportSession extends ScreenSession {

	 /**
     * This method returns the ClearanceListingVO in session
     * @return Page<ClearanceListingVO>
     */

	public Page<ULDDamageDetailsListVO> getULDDamageRepairDetailsVOs();
	
	/**
     * This method sets the ClearanceListingVO in session
     * @param paramCode
     */
	public void setULDDamageRepairDetailsVOs(Page<ULDDamageDetailsListVO> paramCode);
	
	/**
     * This method removes the embargodetailsVos in session
     */
	public void removeULDDamageRepairDetailsVOs();
	
	 /**
     * Method used to get indexMap
     * @return KEY_INDEXMAP - HashMap<String,String>
     */
	public HashMap<String,String>  getIndexMap();
	
	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String,String>  indexmap);
	
	/**
     * This method removes the indexMAp in session
     */
	public void removeIndexMap();
	
	 /**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getDamageStatus();
	/**
	 * Methods for setting status
	 */
	public void setDamageStatus(Collection<OneTimeVO> damageStatus);
	/**
	 * Methods for removing status
	 */
	public void removeDamageStatus();
	
	 /**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getUldStatus();
	/**
	 * Methods for setting status
	 */
	public void setUldStatus(Collection<OneTimeVO> uldStatus);
	/**
	 * Methods for removing status
	 */
	public void removeUldStatus();
	
	/**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getRepairStatus();
	
	/**
	 * Methods for setting status
	 */
	public void setRepairStatus(Collection<OneTimeVO> repairStatus);
	
	/**
	 * 
	 *
	 */
	public void removeRepairStatus();
	/**
	 * 
	 * @param ULDDamageFilterVO
	 */
	public void setULDDamageFilterVO(ULDDamageFilterVO uldDamageFilterVO);
	/**
	 * 
	 * @return
	 */
	public ULDDamageFilterVO getULDDamageFilterVO();
	/**
	 * 
	 *
	 */
	public void removeULDDamageFilterVO();
	/**
	 * 
	 * @param uldDamagePictureVO
	 */
	public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO);
	/**
	 * 
	 * @return
	 */
	public ULDDamagePictureVO getULDDamagePictureVO();
	/**
	 * 
	 *
	 */
	public void removeULDDamagePictureVO();
	
	/**
	 * The setter for PARENT_SCREENIDR
	 * @param parentScreenId
	 */
	public void setScreenId(String parentScreenId);
	/**
	 * The getter for PARENT_SCREENIDR
	 * @return string parentScreenId
	 */
	public String getScreenId();
	/**
	 * Remove method for parentScreenId
	 */
	public void removeScreenId();
	
	
	 /**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getFacilityType();
	/**
	 * Methods for setting status
	 */
	public void setFacilityType(Collection<OneTimeVO> facilityType);
	
	
	/**
	 * Methods for getting status
	 */
	public Collection<OneTimeVO> getPartyType();
	/**
	 * Methods for setting status
	 */
	public void setPartyType(Collection<OneTimeVO> partyType);
	//added by A-5223 for ICRD-22824 starts
	public Integer getTotalRecords();
	public void setTotalRecords(int totalRecords);
	//added by A-5223 for ICRD-22824 ends
	
	/*Added by A-7636 as part of ICRD-245031*/
	HashMap<String, ArrayList<UploadFileModel>> getDamageImageMap();
	void setDamageImageMap(HashMap<String, ArrayList<UploadFileModel>> damageImageMap);
	
}
