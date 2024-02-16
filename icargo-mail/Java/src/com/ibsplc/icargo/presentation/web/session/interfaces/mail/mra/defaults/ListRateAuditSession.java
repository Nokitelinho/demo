/*
 * ListRateAuditSession.java Created on July 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3108
 *
 */
public interface ListRateAuditSession extends ScreenSession{
	
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * 
	 * @param oneTimeValues
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues);

	/**
	 * 
	 *
	 */
	public void removeOneTimeValues();
	
	public void setRateAuditVOs(Page<RateAuditVO> rateAuditVOs);
	
	public Page<RateAuditVO> getRateAuditVOs();
	public void removeRateAuditVOs();
	/**
	 * 
	 * @return String fromScreen
	 */
	public String getFromScreen();
	
	/**
	 * 
	 * @param fromScreen
	 */
	public void setFromScreen(String fromScreen);
	public void setRateAuditFilterVO(RateAuditFilterVO rateAuditFilterVO);
	
	public RateAuditFilterVO getRateAuditFilterVO();
	public void removeRateAuditFilterVO();
	
}
