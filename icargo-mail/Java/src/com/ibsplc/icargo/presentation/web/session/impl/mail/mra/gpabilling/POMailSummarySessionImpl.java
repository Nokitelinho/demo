/**
 *  POMailSummarySessionImpl.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.POMailSummarySession;

/**
 * @author a-4823
 *
 */
public class POMailSummarySessionImpl extends AbstractScreenSession implements POMailSummarySession{

	/**
	 * KEY for MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "mailtracking.mra.gpabilling";

	/**
	 * KEY for SCREEN_ID in session
	 */
	public static final String KEY_SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	
	private static final String KEY_ONETIME_CODES = "onetimecodes";
	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";


	public Collection<OneTimeVO> getOneTimeVo() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ONETIME_CODES);
	}


	public void setOneTimeVo(Collection<OneTimeVO> oneTimeVOs) {
		setAttribute(KEY_ONETIME_CODES, (ArrayList<OneTimeVO>) oneTimeVOs);

	}
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return (HashMap<String, Collection<OneTimeVO>>)
		getAttribute(KEY_ONETIME_VO);
	}


	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
				HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);

	}


	public String getModuleName() {

		return KEY_MODULE_NAME;
	}


	public String getScreenID() {

		return KEY_SCREEN_ID;
	}

}
