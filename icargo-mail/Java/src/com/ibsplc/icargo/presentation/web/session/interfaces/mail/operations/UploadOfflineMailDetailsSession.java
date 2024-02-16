/*
 * UploadOfflineMailDetailsSession.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-6385
 * Added for ICRD-84459
 */
public interface UploadOfflineMailDetailsSession extends ScreenSession {
	
	/**
	 * Returns the collection of ScannedMailDetailsVOs from session
	 * @return
	 */
	public Collection<ScannedMailDetailsVO> getScannedMailDetailsVOs();
	/**
	 * Sets the map of ScannedMailDetailsVOs to session
	 * @param scannedMailDetailsVOs
	 */
	public void setScannedMailDetailsVOs(Collection<ScannedMailDetailsVO> scannedMailDetailsVOs);
	/**
	 * Returns the map of MailUploadVOs from session
	 * @return
	 */
	public HashMap<String, Collection<MailUploadVO>>  getMailMap();
	/**
	 * Sets the map of MailUploadVOs to session
	 * @param mailMap
	 */
	public void setMailMap(HashMap<String, Collection<MailUploadVO>> mailMap);

}
