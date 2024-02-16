/*
 * CarditEnquirySession.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2001
 *
 */
public interface PeakFileUploadSession extends ScreenSession {

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
						HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	

}

