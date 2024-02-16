/*
 * ReturnMailSessionImpl.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnMailSession;

/**
 * @author A-1861
 *
 */
public class ReturnMailSessionImpl extends AbstractScreenSession
        implements ReturnMailSession {

	private static final String SCREEN_ID = "mailtracking.defaults.returnmail";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ONETIMEDAMAGECODE = "oneTimeDamageCode";
	private static final String DAMAGEMAILBAGVOS = "DamageMailBagVOs";
	private static final String POSTALADMINVOS = "PostalAdministrationVOs";
	
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
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs() {
		return (Collection<DamagedMailbagVO>)getAttribute(DAMAGEMAILBAGVOS);
	}
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs) {
		setAttribute(DAMAGEMAILBAGVOS,(ArrayList<DamagedMailbagVO>)damagedMailbagVOs);
	}
	
	/**
     * This method returns the PostalAdministrationVOS
     * @return POSTALADMINVOS - Collection<PostalAdministrationVO>
     */
	public Collection<PostalAdministrationVO> getPostalAdministrationVOs() {
		return (Collection<PostalAdministrationVO>)getAttribute(POSTALADMINVOS);
	}
	
	/**
     * This method is used to set PostalAdministrationVOs to the session
     * @param postalAdministrationVOs - Collection<PostalAdministrationVO>
     */
	public void setPostalAdministrationVOs(Collection<PostalAdministrationVO> postalAdministrationVOs) {
		setAttribute(POSTALADMINVOS,(ArrayList<PostalAdministrationVO>)postalAdministrationVOs);
	}
		
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


}
