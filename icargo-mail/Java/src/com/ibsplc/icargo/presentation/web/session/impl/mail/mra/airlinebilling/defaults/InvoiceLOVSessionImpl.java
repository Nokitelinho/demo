/*
 * InvoiceLOVSessionImpl.java Created on Apr 17, 2007 
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.InvoiceLOVSession;

/**
 * @author A-2518
 * 
 */
public class InvoiceLOVSessionImpl extends AbstractScreenSession implements
		InvoiceLOVSession {

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.invoicelov";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String BILLING_TYPE = "classType";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	 */
	/**
	 * @return screenId
	 */
	public String getScreenID() {

		return SCREENID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	 */
	/**
	 * @return moduleName
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getBillingType() {
		return (Collection<OneTimeVO>) getAttribute(BILLING_TYPE);
	}

	/**
	 * 
	 * @param oneTimeVos
	 */
	public void setBillingType(Collection<OneTimeVO> oneTimeVos) {
		setAttribute(BILLING_TYPE, (ArrayList<OneTimeVO>) oneTimeVos);
	}

	/**
	 * 
	 * 
	 */
	public void removeBillingType() {
		removeAttribute(BILLING_TYPE);
	}

}