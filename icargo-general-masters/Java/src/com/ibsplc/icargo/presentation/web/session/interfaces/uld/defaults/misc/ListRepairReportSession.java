/*
 * ListRepairReportSession.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1617
 *
 */
public interface ListRepairReportSession extends ScreenSession {

	 /**
     * This method returns the ClearanceListingVO in session
     * @return Page<ClearanceListingVO>
     */

	public Page<ULDRepairDetailsListVO> getULDDamageRepairDetailsVOs();
	
	/**
     * This method sets the ClearanceListingVO in session
     * @param paramCode
     */
	public void setULDDamageRepairDetailsVOs(Page<ULDRepairDetailsListVO> paramCode);
	
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
	public Collection<OneTimeVO> getRepairStatus();
	/**
	 * Methods for setting status
	 */
	public void setRepairStatus(Collection<OneTimeVO> repairStatus);
	/**
	 * Methods for removing status
	 */
	public void removeRepairStatus();
	
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
	public Collection<OneTimeVO> getRepairHead();
	/**
	 * Methods for setting status
	 */
	public void setRepairHead(Collection<OneTimeVO> repairHead);
	/**
	 * Methods for removing status
	 */
	public void removeRepairHead();
	
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
     * This method returns the ClearanceListingVO in session
     * @return Page<ClearanceListingVO>
     */

	public ULDRepairFilterVO getULDRepairFilterVO();
	
	/**
     * This method sets the ClearanceListingVO in session
     * @param paramCode
     */
	public void setULDRepairFilterVO(ULDRepairFilterVO uldRepairFilterVO);
	
	/**
     * This method removes the embargodetailsVos in session
     */
	public void removeULDRepairFilterVO();
	
	void setTotalRecords(int totalRecords);//Added by A-5214 as part from the ICRD-22824
	
	Integer getTotalRecords();//Added by A-5214 as part from the ICRD-22824	
	
	/**
	 * @author a-3093
	 * @return
	 */
	public UldDmgRprFilterVO getUldDmgRprFilterVO();
	/**
	 * @author a-3093
	 * @param uldRepairFilterVO
	 */
	public void setUldDmgRprFilterVO(UldDmgRprFilterVO uldRepairFilterVO);
	
	
	
}
