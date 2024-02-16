/*
 * DocumentStatisticsSessionImpl.java Created on Sep 01, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DocumentStatisticsSession;

/**
 * @author A-3429
 * 
 */
public class DocumentStatisticsSessionImpl extends AbstractScreenSession
		implements DocumentStatisticsSession {

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.documentstatisticsreport";

	/**
	 * For Getting the OneTime values
	 */
	private static final String KEY_ONETIME_VOS = "onetimevalues";

	/**
	 * @return
	 */
	public String getScreenID() {

		return SCREEN_ID;
	}

	/**
	 * @return MODULE_NAME
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * @return
	 */

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return getAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * @param oneTimeVOs
	 */

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}

	/**
	 * remove onetimes
	 */

	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}

}
