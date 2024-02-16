/*
 * DamageRefNoLovSession.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1617
 *
 */
public interface DamageRefNoLovSession extends ScreenSession {

	 /**
     * This method returns the ClearanceListingVO in session
     * @return Page<ClearanceListingVO>
     */

	public Page<ULDDamageReferenceNumberLovVO> getULDDamageReferenceNumberLovVOs();
	
	/**
     * This method sets the ClearanceListingVO in session
     * @param paramCode
     */
	public void setULDDamageReferenceNumberLovVOs(Page<ULDDamageReferenceNumberLovVO> paramCode);
	
	/**
     * This method removes the embargodetailsVos in session
     */
	public void removeULDDamageReferenceNumberLovVOs();
	
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
	 * The setter for PARENT_SCREENIDR
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId);
	/**
	 * The getter for PARENT_SCREENIDR
	 * @return string parentScreenId
	 */
	public String getParentScreenId();
	/**
	 * Remove method for parentScreenId
	 */
	public void removeParentScreenId();
	
}
