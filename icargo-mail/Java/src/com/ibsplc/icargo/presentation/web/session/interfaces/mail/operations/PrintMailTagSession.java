/*
 * PrintMailTagSession.java Created on Oct 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1876
 *
 */
public interface PrintMailTagSession extends ScreenSession {

	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeCat - Collection<OneTimeVO>
     */
	public void setOneTimeCat(Collection<OneTimeVO> oneTimeCat);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeCat - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeCat();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI();
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeRI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI);

	/**
     * This method returns the onetime vos
     * @return ONETIME_oneTimeRI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI();
	
	/**
     * This method is used to set MailbagVOs values to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs);

	/**
     * This method returns the MailbagVO vos
     * @return Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs();
	

}

