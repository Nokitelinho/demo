

/*
 * RejectionMemoSession.java Created on May 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * @author A-2408
 *
 */
public interface RejectionMemoSession extends ScreenSession{
	
	/**
	 * @return
	 */
	public RejectionMemoVO getRejectionMemoVO();
	
	/**
	 * @param rejectionMemoVO
	 */
	public void setRejectionMemoVO(RejectionMemoVO rejectionMemoVO);
	
	/**
	 * 
	 */
	public void removeRejectionMemoVO();
	
	
	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	
}