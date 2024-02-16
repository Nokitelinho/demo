/*
 * UnaccountedDispatchesSession.java Created on Aug 20, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 *  @author A-2107
 */

public interface UnaccountedDispatchesSession extends ScreenSession {

	/**
     * @return
     */
    public Collection<OneTimeVO> getOneTimeVOs();
	
	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(Collection<OneTimeVO> oneTimes);
	
	/**
	 * The setter method for UnaccountedDispatchesFilterVO
	 * @param UnaccountedDispatchesFilterVO
	 */
	public void setUnaccountedDispatchesFilterVO(
			UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO);
    /**
     * The getter method for UnaccountedDispatchesFilterVO
     * @return UnaccountedDispatchesFilterVO
     */
    public UnaccountedDispatchesFilterVO getUnaccountedDispatchesFilterVO();
    
    /**
	 * The setter method for UnaccountedDispatchesFilterVO
	 * @param UnaccountedDispatchesFilterVO
	 */
	public void setUnaccountedDispatchesVO(
			UnaccountedDispatchesVO unaccountedDispatchesVO);
    /**
     * The getter method for UnaccountedDispatchesFilterVO
     * @return UnaccountedDispatchesFilterVO
     */
    public UnaccountedDispatchesVO getUnaccountedDispatchesVO();
    /**
	 *   Method to get the onetime map in the
	 *         session
	 * @return HashMap the onetime map from session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

	/**
	 *  Method to set the Onetimes map to session
	 * @param oneTimeMap
	 *            The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

	/**
	 *  Method to remove One Time Map from
	 *         session
	 */
	public void removeOneTimeMap();
		
}
