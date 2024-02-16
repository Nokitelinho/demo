/*
 * CopyBlgLineSessionImpl.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
/**
 * @author A-2391
 *
 */
public class CopyBlgLineSessionImpl extends AbstractScreenSession
implements CopyBlgLineSession{
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.copyblgline";
	private static final String KEY_BLGMATRIXVO="billingmatrixvo";

	private static final String KEY_SELECTED_BLGLINES="billinglinevos";

	/**
	 *
	 * @param billingMatrixVO
	 */
	public void setBillingMatrixVO(BillingMatrixVO billingMatrixVO) {
		setAttribute(KEY_BLGMATRIXVO,billingMatrixVO);

	}

	/**
	 *
	 * @return
	 */
	public BillingMatrixVO getBillingMatrixVO() {

		return getAttribute(KEY_BLGMATRIXVO);

	}
	/**
	 *
	 *
	 */

	public void removeBillingMatrixVO() {
		removeAttribute(KEY_BLGMATRIXVO);

	}

	/**
	 *
	 * @param billingLineVOs
	 */
	public void setSelectedBlgLines(ArrayList<BillingLineVO> billingLineVOs) {
		setAttribute(KEY_SELECTED_BLGLINES,(ArrayList<BillingLineVO>)billingLineVOs);

	}

	/**
	 *
	 * @return
	 */
	public Collection<BillingLineVO> getSelectedBlgLines() {
		// TODO Auto-generated method stub
		return (Collection<BillingLineVO>)getAttribute(KEY_SELECTED_BLGLINES);
	}

	/**
	 *
	 */
	public void removeSelectedBlgLines() {
		removeAttribute(KEY_SELECTED_BLGLINES);

	}

	/**
	 *
	 * @return
	 */

	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}
	/**
	 *
	 * @return
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}
}
