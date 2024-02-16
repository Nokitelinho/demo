/*
 * ULDIntMvtHistorySessionImpl.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2412
 * 
 */
public class ULDIntMvtHistorySessionImpl extends AbstractScreenSession
	implements ULDIntMvtHistorySession{
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.misc.uldintmvthistory";
	
	private static final String KEY_INTULDMVTDETAILS = "uldIntMvtDetailVOs";
	private static final String KEY_INTULDFILTERVO = "uldIntMvtHistoryFilterVO";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	
	public String getModuleName() {		
		return MODULE;
	}

	
	public String getScreenID() {		
		return SCREENID;
	}

	public Page<ULDIntMvtDetailVO> getIntULDMvtDetails() {	
		return getAttribute(KEY_INTULDMVTDETAILS);
	}

	public void setIntULDMvtDetails(Page<ULDIntMvtDetailVO> uldIntMvtDetailVOs) {
		setAttribute(KEY_INTULDMVTDETAILS,uldIntMvtDetailVOs);		
	}


	public ULDIntMvtHistoryFilterVO getULDIntMvtHistoryFilterVO() {
		return getAttribute(KEY_INTULDFILTERVO);
	}


	public void setULDIntMvtHistoryFilterVO(ULDIntMvtHistoryFilterVO uldIntMvtHistoryFilterVO) {
		setAttribute(KEY_INTULDFILTERVO,uldIntMvtHistoryFilterVO);	
		
	}
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> 
 									oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
}