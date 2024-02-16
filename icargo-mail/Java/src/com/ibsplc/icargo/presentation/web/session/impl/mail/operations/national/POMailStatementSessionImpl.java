/*
 POMailStatementSessionImpl.java Created on Feb 01, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.national.POMailStatementVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4777
 *
 */

public class POMailStatementSessionImpl extends AbstractScreenSession
implements POMailStatementSession{
	
	private static String SCREEN_ID = "mailtracking.defaults.national.mailstatement";
	private static String MODULE_NAME = "mail.operations";
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	private static final String KEY_SELECTED_POMAILVO = "SelectedPOMailStatementVOs";
	private static final String KEY_INDEXMAP = "indexMap";
	
	public String getScreenId() {
		return SCREEN_ID;
	}
	public void setScreenId(String screenId) {
		SCREEN_ID = screenId;
	}
	public String getModuleName() {
		return MODULE_NAME;
	}
	public void setModuleName(String moduleName) {
		MODULE_NAME = moduleName;
	}
	
	public String getScreenID() {
		
		return null;
	}
	
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return (HashMap<String, Collection<OneTimeVO>>)
		getAttribute(KEY_ONETIME_VO);
	}


	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
				HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);

	}

	public Page<POMailStatementVO> getSelectedPOMailStatementVOs() {
		
		return (Page<POMailStatementVO>)getAttribute(KEY_SELECTED_POMAILVO);
	}


	public void setSelectedPOMailStatementVOs(Page<POMailStatementVO> pOMailStatementVO) {
	
		setAttribute(KEY_SELECTED_POMAILVO,(Page<POMailStatementVO>) pOMailStatementVO);
	}

//Added by A-4810 for icrd-15155
	/**
	 * set index map
	 * @param indexMap
	 */
	 public void setIndexMap(HashMap indexMap){
		setAttribute( KEY_INDEXMAP,(HashMap<String,String>)indexMap);
	 }
	/**
	 * get indexmap
	 * @return HashMap
	 */
	public HashMap getIndexMap(){
		return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	
	

}
