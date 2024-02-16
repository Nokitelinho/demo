/*
 * MLDConfigurationSession.java Created on December 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * 
 * @author A-5526
 * 
 */
public interface MLDConfigurationSession extends ScreenSession {

	/**
	 * This method is used to set Collection<MLDConfigurationVO> to the session
	 * 
	 * @param mLDConfigurationVOs
	 *            - Collection<MLDConfigurationVO>
	 */
	public void setMLDConfigurationVOs(
			Collection<MLDConfigurationVO> mLDConfigurationVOs);

	/**
	 * This method returns the Collection<MLDConfigurationVO>
	 * 
	 * @return SELECTED_MLDConfigurationVOs - Collection<MLDConfigurationVO>
	 */
	public Collection<MLDConfigurationVO> getMLDConfigurationVOs();

}
