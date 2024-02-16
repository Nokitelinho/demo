/*
 POMailStatementSession.java Created on Feb 01, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.national.POMailStatementVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4777
 *
 */

public interface POMailStatementSession extends ScreenSession {
	
	
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	
	public Page<POMailStatementVO> getSelectedPOMailStatementVOs();
	
	public void setSelectedPOMailStatementVOs(Page<POMailStatementVO> dsnvo);
	
	//Added by A-4810 for icrd-15155
	/**
	 * set index map
	 * @param indexMap
	 */
	public void setIndexMap(HashMap indexMap);
	/**
	 * get indexmap
	 * @return HashMap
	 */
	public HashMap getIndexMap();
	

	

}
