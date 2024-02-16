/*
 * DamagedDSNSessionImpl.java Created on July 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedDSNSession;

/**
 * @author A-2047
 *
 */
public class DamagedDSNSessionImpl extends AbstractScreenSession implements
		DamagedDSNSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.damageddsn";
	
	private static final String DAMAGED_DSN_VO = "damagedDSNVO";
	private static final String DESP_DET_VO = "despatchDetailsVO";
	
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
     * @return DamagedDSNVO
     */
	public DamagedDSNVO getDamagedDSNVO() {
		return getAttribute(DAMAGED_DSN_VO);
	}
	
	/**
     * @param damagedDSNVO
     */
	public void setDamagedDSNVO(DamagedDSNVO damagedDSNVO) {
		setAttribute(DAMAGED_DSN_VO,damagedDSNVO);
	}
	
	/**
     * @return DespatchDetailsVO
     */
	public DespatchDetailsVO getDespatchDetailsVO() {
		return getAttribute(DESP_DET_VO);
	}
	
	/**
     * @param despatchDetailsVO
     */
	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO) {
		setAttribute(DESP_DET_VO,despatchDetailsVO);
	}
}
