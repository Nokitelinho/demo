/*
 * DsnAuditSessionImpl.java Created on Aug 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnAuditSession;

/**
 * @author A-1876
 *
 */
public class DsnAuditSessionImpl extends AbstractScreenSession
        implements DsnAuditSession {

	private static final String SCREEN_ID = "mailtracking.defaults.dsnaudit";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMEMAILCLASS = "oneTimeMailClass";
	
		
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
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat) {
		setAttribute(KEY_ONETIMECAT,(ArrayList<OneTimeVO>)oneTimeCat);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMECAT - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMECAT);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeMailClass - Collection<OneTimeVO>
     */
	public void setOneTimeMailClass(Collection<OneTimeVO> oneTimeMailClass) {
		setAttribute(KEY_ONETIMEMAILCLASS,(ArrayList<OneTimeVO>)oneTimeMailClass);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEMAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeMailClass() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEMAILCLASS);
	}


}
