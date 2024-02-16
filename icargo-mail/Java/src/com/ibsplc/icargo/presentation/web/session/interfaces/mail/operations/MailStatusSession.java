/*
 * MailStatusSession.java Created on FEB 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * @author A-3227
 *
 */
public interface MailStatusSession extends ScreenSession{

	
	/**
     * This method is used to setcurrentStatus  values to the session
     * @param currentStatus - Collection<String>
     */
	public void setCurrentStatus(Collection<String> currentStatus);
	
	/**
     * This method returns the currentStatus vos
     * @return CURRENTSTATUS - Collection<String>
     */
	public Collection<String> getCurrentStatus() ;

}
