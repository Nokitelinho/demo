/*
 * CopyBlgLineSession.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * @author A-2391
 *
 */
public interface CopyBlgLineSession extends ScreenSession {
	/**
	 *
	 * @param billingMatrixVO
	 */
	public void setBillingMatrixVO(BillingMatrixVO billingMatrixVO);
	/**
	 *
	 * @return
	 */
	public BillingMatrixVO getBillingMatrixVO();
	/**
	 *
	 *
	 */
	public void removeBillingMatrixVO();
	/**
	 *
	 * @param billingLineVOs
	 */
	public void setSelectedBlgLines(ArrayList<BillingLineVO> billingLineVOs);
	/**
	 *
	 * @return
	 */
	public Collection<BillingLineVO> getSelectedBlgLines();
	/**
	 *
	 *
	 */
	public void removeSelectedBlgLines();

}
