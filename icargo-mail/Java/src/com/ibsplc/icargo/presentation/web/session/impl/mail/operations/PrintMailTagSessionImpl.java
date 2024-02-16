/*
 * PrintMailTagSessionImpl.java Created on Oct 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PrintMailTagSession;

/**
 * @author A-1876
 *
 */
public class PrintMailTagSessionImpl extends AbstractScreenSession
        implements PrintMailTagSession {

	private static final String SCREEN_ID = "mailtracking.defaults.printmailtag";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ONETIMECAT = "oneTimeCat";
	private static final String KEY_ONETIMERI = "oneTimeRSN";
	private static final String KEY_ONETIMEHNI = "oneTimeHNI";
	private static final String KEY_MAILBAGVOS = "mailbagVOs";
	
		
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
     * @param oneTimeRI - Collection<OneTimeVO>
     */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI) {
		setAttribute(KEY_ONETIMERI,(ArrayList<OneTimeVO>)oneTimeRI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMERI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeRI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMERI);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeHNI - Collection<OneTimeVO>
     */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI) {
		setAttribute(KEY_ONETIMEHNI,(ArrayList<OneTimeVO>)oneTimeHNI);
	}

	/**
     * This method returns the onetime vos
     * @return KEY_ONETIMEHNI - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeHNI() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEHNI);
	}
	
	 /**
     * This method is used to set MailbagVOs values to the session
     * @param mailbagVOs - Collection<MailbagVO>
     */
	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs) {
		setAttribute(KEY_MAILBAGVOS,(ArrayList<MailbagVO>)mailbagVOs);
	}

	/**
     * This method returns the MailbagVOs
     * @return KEY_MAILBAGVOS - Collection<MailbagVO>
     */
	public Collection<MailbagVO> getMailbagVOs() {
		return (Collection<MailbagVO>)getAttribute(KEY_MAILBAGVOS);
	}
    
	

}
