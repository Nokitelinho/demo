/*
 * ULDHistorySession.java Created on Oct 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

/**
 * @author A-2619
 * 
 */
public interface ULDHistorySession extends ScreenSession {

	/**
	 * This method returns the ULDHistoryVO in session
	 * 
	 * @return Page<ULDHistoryVO>
	 */

	public Page<ULDHistoryVO> getUldHistoryVOs();

	/**
	 * This method sets the ClearanceListingVO in session
	 * 
	 * @param uldHistoryVO
	 */
	public void setUldHistoryVOs(Page<ULDHistoryVO> uldHistoryVO);

	/**
	 * Method used to get indexMap
	 * 
	 * @return KEY_INDEXMAP - HashMap<String,String>
	 */
	public HashMap<String, String> getIndexMap();

	/**
	 * Method used to set indexMap to session
	 * 
	 * @param indexmap -
	 *            HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String, String> indexmap);

	/**
	 * This method removes the indexMAp in session
	 */
	public void removeIndexMap();

	/**
	 * The setter for PARENT_SCREENIDR
	 * 
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId);

	/**
	 * The getter for PARENT_SCREENIDR
	 * 
	 * @return string parentScreenId
	 */
	public String getParentScreenId();

	/**
	 * Remove method for parentScreenId
	 */
	public void removeParentScreenId();

	public Collection<OneTimeVO> getOneTimeULDHistoryVO();

	/**
	 * @param oneTimeVOs
	 *            The oneTimeVOs to set.
	 */
	public void setOneTimeULDHistoryVO(Collection<OneTimeVO> oneTimeVOs);

}
