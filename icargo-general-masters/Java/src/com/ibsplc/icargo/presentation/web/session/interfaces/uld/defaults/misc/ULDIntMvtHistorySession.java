/*
 * ULDIntMvtHistorySession.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2412
 * 
 */
public interface ULDIntMvtHistorySession extends ScreenSession{
	
	
	
	/**
	 * @param uldIntMvtDetailVOs
	 */
	public void setIntULDMvtDetails(Page<ULDIntMvtDetailVO> 
	uldIntMvtDetailVOs);
	
	
	/**
	 * @return
	 */
	public Page<ULDIntMvtDetailVO> getIntULDMvtDetails();
	
	
	/**
	 * @return
	 */
	public ULDIntMvtHistoryFilterVO getULDIntMvtHistoryFilterVO(); 
	
	
	/**
	 * @param uldIntMvtHistoryFilterVO
	 */
	public void setULDIntMvtHistoryFilterVO(ULDIntMvtHistoryFilterVO
			uldIntMvtHistoryFilterVO);
	
	/**
	 * @return Returns the oneTimeValues.
	 */
		HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
}
