/*
 * ULDHistorySessionImpl.java Created on oct 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHistorySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1617
 *
 */
public class ULDHistorySessionImpl extends AbstractScreenSession implements
		ULDHistorySession {

	private static final String KEY_INDEXMAP = "index";

	private static final String MODULE = "uld.defaults";

	private static final String ULD_HISTORY = "uldhistory";

	private static final String SCREENID = "uld.defaults.uldhistory";

	/*
	 * Key for Parent Screen Id 
	 */
	private static final String KEY_PARENTSCREENID = "parentscreenid";

	private static final String KEY_ONETIME_VO = "uld.defaults.uldhistory";

	/**
	 * @return
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * @return
	 */
	public String getModuleName() {
		return MODULE;
	}

	/**
	 * This method returns the ULDDamageRepairDetailsVO in session
	 * @return Page<ClearanceListingVO>
	 */

	public Page<ULDHistoryVO> getUldHistoryVOs() {
		return (Page<ULDHistoryVO>) getAttribute(ULD_HISTORY);
	}

	/**
	 * This method sets the ULDDamageRepairDetailsVO in session
	 * @param paramCode
	 */
	public void setUldHistoryVOs(Page<ULDHistoryVO> uldHistoryVO) {
		setAttribute(ULD_HISTORY, (Page<ULDHistoryVO>) uldHistoryVO);
	}

	/**
	 * Method used to get indexMap
	 * @return KEY_INDEXMAP - HashMap<String,String>
	 */
	public HashMap<String, String> getIndexMap() {
		return (HashMap<String, String>) getAttribute(KEY_INDEXMAP);
	}

	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String, String> indexmap) {
		setAttribute(KEY_INDEXMAP, (HashMap<String, String>) indexmap);
	}

	/**
	 * This method removes the indexMAp in session
	 */
	public void removeIndexMap() {
		removeAttribute(KEY_INDEXMAP);
	}

	/**
	 * The setter for PARENT_SCREENIDR
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId) {
		setAttribute(KEY_PARENTSCREENID, parentScreenId);
	}

	/**
	 * The getter for PARENT_SCREENIDR
	 * @return string parentScreenId
	 */
	public String getParentScreenId() {
		return getAttribute(KEY_PARENTSCREENID);
	}

	/**
	 * Remove method for parentScreenId
	 */
	public void removeParentScreenId() {
		removeAttribute(KEY_PARENTSCREENID);
	}

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public Collection<OneTimeVO> getOneTimeULDHistoryVO() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ONETIME_VO);
	}

	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeULDHistoryVO(Collection<OneTimeVO> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO, (ArrayList<OneTimeVO>) oneTimeVOs);
	}

}
