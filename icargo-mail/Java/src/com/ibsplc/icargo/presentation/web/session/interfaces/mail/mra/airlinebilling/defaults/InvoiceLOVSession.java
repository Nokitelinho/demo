/*
 * InvoiceLOVSession.java Created on Apr 17, 2007
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2518
 * 
 */
public interface InvoiceLOVSession extends ScreenSession {
	/**
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getBillingType();

	/**
	 * 
	 * @param oneTimeVos
	 */
	public void setBillingType(Collection<OneTimeVO> oneTimeVos);

	/**
	 * 
	 * 
	 */
	public void removeBillingType();

}