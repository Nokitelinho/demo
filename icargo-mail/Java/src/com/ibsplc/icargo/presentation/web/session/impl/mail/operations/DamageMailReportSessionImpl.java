/*
 * DamageMailReportSessionImpl.java Created on FEB 28, 2008
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamageMailReportSession;

/**
 * @author A-3227
 *
 */
public class DamageMailReportSessionImpl extends AbstractScreenSession
        implements DamageMailReportSession {

	private static final String SCREEN_ID = "mailtracking.defaults.damagemailreport";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_ONETIMEDAMAGECODE = "oneTimeDamageCode";
	private static final String KEY_SUBCLASSGROUP="subClassGroup";
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}
	
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEDAMAGECODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode) {
		setAttribute(KEY_ONETIMEDAMAGECODE,(ArrayList<OneTimeVO>)oneTimeDamageCode);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getSubClassGroups() {
		return (Collection<OneTimeVO>)getAttribute(KEY_SUBCLASSGROUP);
	}

	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setSubClassGroups(Collection<OneTimeVO> subClassGroup) {
		setAttribute(KEY_SUBCLASSGROUP,(ArrayList<OneTimeVO>)subClassGroup);
	}
}
