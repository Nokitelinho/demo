/*
 * OfficeOfExchangeMasterSessionImpl.java Created on June 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2047
 *
 */
public class OfficeOfExchangeMasterSessionImpl extends AbstractScreenSession
		implements OfficeOfExchangeMasterSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.officeofexchange";
	
	private static final String OE_VOS = "officeOfExchangeVOs";
	private static final String OE_VO = "officeOfExchangeVO";
	
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return Page<OfficeOfExchangeVO>
     */
	public Page<OfficeOfExchangeVO> getOfficeOfExchangeVOs() {
		return (Page<OfficeOfExchangeVO>)getAttribute(OE_VOS);
	}
	
	/**
     * @param officeOfExchangeVOs
     */
	public void setOfficeOfExchangeVOs(Page<OfficeOfExchangeVO> officeOfExchangeVOs) {
		setAttribute(OE_VOS,officeOfExchangeVOs);
	}
	
	/**
     * @return OfficeOfExchangeVO
     */
	public OfficeOfExchangeVO getOfficeOfExchangeVO() {
		return getAttribute(OE_VO);
	}
	
	/**
     * @param officeOfExchangeVO
     */
	public void setOfficeOfExchangeVO(OfficeOfExchangeVO officeOfExchangeVO) {
		setAttribute(OE_VO,officeOfExchangeVO);
	}

}
