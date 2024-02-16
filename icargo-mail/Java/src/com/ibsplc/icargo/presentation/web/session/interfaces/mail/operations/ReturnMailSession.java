/*
 * ReturnMailSession.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1861
 *
 */
public interface ReturnMailSession extends ScreenSession {

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode);
	
	/**
     * This method returns the DamagedMailbagVOS
     * @return DAMAGEMAILBAGVOS - Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs();
	
	/**
     * This method is used to set DamagedMailbagVOs to the session
     * @param damagedMailbagVOs - Collection<DamagedMailbagVO>
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs);
	
	/**
     * This method returns the PostalAdministrationVOS
     * @return POSTALADMINVOS - Collection<PostalAdministrationVO>
     */
	public Collection<PostalAdministrationVO> getPostalAdministrationVOs();
	
	/**
     * This method is used to set PostalAdministrationVOs to the session
     * @param postalAdministrationVOs - Collection<PostalAdministrationVO>
     */
	public void setPostalAdministrationVOs(Collection<PostalAdministrationVO> postalAdministrationVOs);
	
}

