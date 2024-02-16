/*
 * MailDiscrepancySessionImpl.java Created on mar 25, 2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailDiscrepancySession;

/**
 * @author A-2391
 *
 */
public class MailDiscrepancySessionImpl extends AbstractScreenSession
        implements MailDiscrepancySession {

	private static final String SCREEN_ID = "mailtracking.defaults.maildiscrepancyreport";
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String ONETIME_DISCTYPES = "discTypes";
	
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)
									getAttribute(ONETIME_DISCTYPES);
	}
	
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(ONETIME_DISCTYPES,(
						HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);
	}
	


}
