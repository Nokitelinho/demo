/*
 * MailStatusSessionImpl.java Created on FEB 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailStatusSession;

/**
 * @author A-3227
 *
 */
public class MailStatusSessionImpl extends AbstractScreenSession
        implements MailStatusSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailstatus";
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String ONETIME_CURRENTSTATUS = "current_status";
	
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * This method is used to set current_status values to the session
     * @param currentStatus - Collection<String>
     */
	public void setCurrentStatus(Collection<String> currentStatus) {
		setAttribute(ONETIME_CURRENTSTATUS,(ArrayList<String>)currentStatus);
	}
	
	/**
     * This method returns the current_status vos
     * @return current_status - Collection<String>
     */
	public Collection<String> getCurrentStatus() {
		return (Collection<String>)getAttribute(ONETIME_CURRENTSTATUS);
	}


}
