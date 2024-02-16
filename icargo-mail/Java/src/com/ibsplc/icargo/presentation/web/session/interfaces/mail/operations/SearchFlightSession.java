/*
 * SearchFlightSession.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3817
 * 
 */
public interface SearchFlightSession extends ScreenSession {
	
	/**
	* @return Returns the indexMap.
	 */
	public HashMap<String, String> getIndexMap();
	/**
	 * @param indexMap The indexMap to set.
	 */
	public void setIndexMap(HashMap<String, String> indexMap);
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);

	public OperationalFlightVO getOperationalFlightVO();

	public Page<OperationalFlightVO> getOperationalFlightVOs();

	public void setOperationalFlightVOs(
			Page<OperationalFlightVO> operationalFlightVOs);
	public void setScreenFlag(String screenFlag);
	public String getScreenFlag();
}
