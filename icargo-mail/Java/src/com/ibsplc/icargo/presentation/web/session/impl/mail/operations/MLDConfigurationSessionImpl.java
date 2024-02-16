/*
 * MLDConfigurationSessionImpl.java Created on December 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MLDConfigurationSession;

/**
 * 
 * @author A-5526
 * 
 */
public class MLDConfigurationSessionImpl extends AbstractScreenSession
		implements MLDConfigurationSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";
	private static final String MODULE_NAME = "mail.operations";
	private static final String MLD_CONFIGURATIONVOS = "mLDConfigurationVOs";

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
	 * This method is used to set Collection<MLDConfigurationVO> to the session
	 * 
	 * @param mLDConfigurationVOs
	 *            - Collection<MLDConfigurationVO>
	 */
	public void setMLDConfigurationVOs(
			Collection<MLDConfigurationVO> mLDConfigurationVOs) {
		setAttribute(MLD_CONFIGURATIONVOS,
				(ArrayList<MLDConfigurationVO>) mLDConfigurationVOs);
	}

	/**
	 * This method returns the Collection<MLDConfigurationVO>
	 * 
	 * @return MLD_CONFIGURATIONVOS - Collection<MLDConfigurationVO>
	 */

	public Collection<MLDConfigurationVO> getMLDConfigurationVOs() {
		return (Collection<MLDConfigurationVO>) getAttribute(MLD_CONFIGURATIONVOS);
	}

}
