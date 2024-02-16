/*
 *  ConfigureResditSessionImpl.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConfigureResditSession;

/**
 * @author A-2001
 *
 */
public class  ConfigureResditSessionImpl extends AbstractScreenSession
        implements  ConfigureResditSession{

	private static final String SCREEN_ID = "mailtracking.defaults.configureresdit";
	private static final String MODULE_NAME = "mail.operations";

	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	/**
	 * Constant for the session variable carditEnquiryVO
	 */
	private static final String KEY_RESDITCONFIGURATIONVO  = "resditConfigurationVO ";
	
		
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)
									getAttribute(KEY_ONETIME_VO);
	}
	
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
						HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);
	}
	
	/**
	 * The setter method for ResditConfigurationVO
	 * @param resditConfigurationVO
	 */
    public void setResditConfigurationVO (ResditConfigurationVO resditConfigurationVO) {
    	setAttribute(KEY_RESDITCONFIGURATIONVO,resditConfigurationVO);
    }
    /**
     * The getter method for ResditConfigurationVO
     * @return resditConfigurationVO
     */
    public ResditConfigurationVO  getResditConfigurationVO() {
    	return getAttribute(KEY_RESDITCONFIGURATIONVO);
    }
     
    public void removeResditConfigurationVO() {
    	removeAttribute(KEY_RESDITCONFIGURATIONVO);
    }

    
	

}
