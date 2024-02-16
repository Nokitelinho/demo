/**
 *  POMailSummarySession.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-4823
 *
 */
public interface POMailSummarySession extends ScreenSession{
	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getOneTimeVo();
	/**
	 * 
	 * @param oneTimeVOs
	 */
	public void setOneTimeVo(Collection<OneTimeVO> oneTimeVOs);
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
