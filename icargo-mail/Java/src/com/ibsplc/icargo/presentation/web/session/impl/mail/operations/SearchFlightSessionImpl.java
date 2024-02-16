/*
 * SearchFlightSessionImpl.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3817
 * 
 */
public class SearchFlightSessionImpl extends AbstractScreenSession implements
		SearchFlightSession {

	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MODULENAME = "mail.operations";

	private static final String ONETIME_KEY = "onetimekey";

	private static final String OPR_VO_KEY = "operationalflightvo";
	private static final String OPR_VOS_KEY = "operationalflightvos";
	private static final String SCREEN_KEY = "screen_key";
	
	/*
	 * String KEY_INDEXMAP
	 */
	private static final String KEY_INDEXMAP = "indexMap";

	public String getModuleName() {

		return MODULENAME;
	}

	public String getScreenID() {

		return SCREENID;
	}
	
	/**
	 * @return Returns the indexMap.
	 */
	public HashMap<String, String> getIndexMap() {
		return getAttribute(KEY_INDEXMAP);
	}

	/**
	 * @param indexMap The indexMap to set.
	 */
	public void setIndexMap(HashMap<String, String> indexMap) {
		setAttribute(KEY_INDEXMAP,indexMap);
	}

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(ONETIME_KEY);
	}

	/**
	 * @param oneTimeVOs
	 *            The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(ONETIME_KEY,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeVOs);
	}

	/**
	 * 
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO) {
		setAttribute(OPR_VO_KEY, (OperationalFlightVO) operationalFlightVO);
	}

	/**
	 * 
	 * @return
	 */
	public OperationalFlightVO getOperationalFlightVO() {
		return (OperationalFlightVO) getAttribute(OPR_VO_KEY);
	}
	/**
	 * 
	 * @return
	 */
	public Page<OperationalFlightVO> getOperationalFlightVOs(){
		return (Page<OperationalFlightVO>)getAttribute(OPR_VOS_KEY);
	}
	/**
	 * 
	 * @param operationalFlightVOs
	 */
	public void setOperationalFlightVOs(Page<OperationalFlightVO> operationalFlightVOs){
		setAttribute(OPR_VOS_KEY, (Page<OperationalFlightVO>)operationalFlightVOs);
	}
	/**
	 * 
	 * @param screenFlag
	 */
	public void setScreenFlag(String screenFlag){
		setAttribute(SCREEN_KEY, screenFlag);
		
	}
	/**
	 * 
	 * @return
	 */
	public String getScreenFlag(){
		return getAttribute(SCREEN_KEY);
	}
}
