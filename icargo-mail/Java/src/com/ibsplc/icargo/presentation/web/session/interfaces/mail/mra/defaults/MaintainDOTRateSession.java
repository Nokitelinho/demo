/*
 * MaintainDOTRateSession.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2408
 *
 */
public interface MaintainDOTRateSession extends ScreenSession {

	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	/**
	 * @return
	 */
	public ArrayList<MailDOTRateVO> getMailDOTRateVOs();
	
	/**
	 * @param mailDOTRateVOs
	 */
	public void setMailDOTRateVOs(ArrayList<MailDOTRateVO> mailDOTRateVOs);
	
	/**
	 * 
	 */
	public void removeMailDOTRateVOs();
	
	
}