/*
 * ProformaInvoiceDiffReportSession.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


	/**
	 * @author A-3271
	 *
	 */
	public interface ProformaInvoiceDiffReportSession extends ScreenSession {

	
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(ArrayList<OneTimeVO> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();

	
}
