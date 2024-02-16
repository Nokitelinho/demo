/*
 * UnaccountedDispatchesSessionImpl.java Created on Aug 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;


/**
 * @author A-2107
 *
 */
public class UnaccountedDispatchesSessionImpl	extends 
		AbstractScreenSession implements UnaccountedDispatchesSession {
   /**
    * Module Name
    */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    /**
     * ScreenID
     */
    private static final String SCREENID = 
    	"mailtracking.mra.defaults.unaccounteddispatches";
    
    /**
     * Session variable for OneTimeVOs 
     */
    private static final String KEY_ONETIME_VOs = "onetimevos";	
    /**
     * Session variable for UnaccountedDispatchesFilterVO 
     */
    private static final String KEY_UNACCTOUNTEDDISPACTCHESFILTERVO = "unaccountedDispatchesFilterVO";
    
    /**
     * Session variable for UnaccountedDispatchesVO 
     */
    private static final String KEY_UNACCTOUNTEDDISPACTCHESVO = "unaccountedDispatchesVO";
    
    private static final String KEY_ONETIME_CODES_MAP = "onetimecodes";
  
    /**
     * Default Constructor
     */
    public UnaccountedDispatchesSessionImpl() {
        super();

    }
    /**
	 * 
	 * @return
	 */	 

	public Collection<OneTimeVO> getOneTimeVOs(){

		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIME_VOs);

	}
	/**
	 * Method to set One time Vo Collection to the session
	 */
	public void setOneTimeVOs(Collection<OneTimeVO> oneTimeVOs){

		setAttribute(KEY_ONETIME_VOs,(ArrayList<OneTimeVO>) oneTimeVOs);

	}
			
    /**
     * @return String
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    } 
    /**
	 * @return
	 */
    @Override
    public String getScreenID() {
    	return SCREENID;
    }
    
	/**
	 * The setter method for SearchContainerFilterVO
	 * @param unaccountedDispatchesFilterVO
	 */
    public void setUnaccountedDispatchesFilterVO(
    		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) {
    	setAttribute(KEY_UNACCTOUNTEDDISPACTCHESFILTERVO,unaccountedDispatchesFilterVO);
    }
    /**
     * The getter method for searchContainerFilterVO
     * @return searchContainerFilterVO
     */
    public UnaccountedDispatchesFilterVO getUnaccountedDispatchesFilterVO() {
    	return getAttribute(KEY_UNACCTOUNTEDDISPACTCHESFILTERVO);
    }
    
    /**
	 * The setter method for SearchContainerFilterVO
	 * @param unaccountedDispatchesVO
	 */
    public void setUnaccountedDispatchesVO(
    		UnaccountedDispatchesVO unaccountedDispatchesVO) {
    	setAttribute(KEY_UNACCTOUNTEDDISPACTCHESVO,unaccountedDispatchesVO);
    }
    /**
     * The getter method for searchContainerFilterVO
     * @return searchContainerFilterVO
     */
    public UnaccountedDispatchesVO getUnaccountedDispatchesVO() {
    	return getAttribute(KEY_UNACCTOUNTEDDISPACTCHESVO);
    }
    

	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
	}

	/**
	 * Method to set the Onetimes map to session
	 * 
	 * @param oneTimeMap
	 * The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
		setAttribute(KEY_ONETIME_CODES_MAP,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
	}

	/**
	 * Method to remove One Time Map from session
	 */
	public void removeOneTimeMap() {
		removeAttribute(KEY_ONETIME_CODES_MAP);

	}

}
