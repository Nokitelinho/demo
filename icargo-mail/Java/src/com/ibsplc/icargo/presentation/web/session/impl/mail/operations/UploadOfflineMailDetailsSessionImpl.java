/*
 * UploadOfflineMailDetailsSessionImpl.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadOfflineMailDetailsSession;

/**
 * @author A-6385
 * Added for ICRD-84459
 */
public class UploadOfflineMailDetailsSessionImpl extends AbstractScreenSession
		implements UploadOfflineMailDetailsSession {

	private static final String KEY_MODULE_NAME = "mail.operations";

	private static final String KEY_SCREEN_ID = "mailtracking.defaults.offlinemailupload";

	private static final String KEY_SCANNED_MAIL_DETAILS = "SCANNED_MAIL_DETAILS";
	
	private static final String KEY_SCANNED_MAIL_MAP = "SCANNED_MAIL_MAP";

	/**
	 * @return Returns the KEY_MODULE_NAME
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * @return Returns the KEY_SCREEN_ID
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}
    /**
     * @return the keyScannedMailDetails
     */
	public Collection<ScannedMailDetailsVO> getScannedMailDetailsVOs() {
		return (Collection<ScannedMailDetailsVO>) getAttribute(KEY_SCANNED_MAIL_DETAILS);
	}

	/**
	 * 
	 * @param scannedMailDetailsVOs
	 */
	public void setScannedMailDetailsVOs(
			Collection<ScannedMailDetailsVO> scannedMailDetailsVOs) {
		setAttribute(KEY_SCANNED_MAIL_DETAILS,
				(ArrayList<ScannedMailDetailsVO>) scannedMailDetailsVOs);
	}	
	/**
	 * @return the keyScannedMailMap
	 */
	public HashMap<String, Collection<MailUploadVO>> getMailMap() {
		return (HashMap<String, Collection<MailUploadVO>>)getAttribute(KEY_SCANNED_MAIL_MAP);
	}
	/**
	 * @param mailMap
	 */
	public void setMailMap(HashMap<String, Collection<MailUploadVO>> mailMap){
		setAttribute(KEY_SCANNED_MAIL_MAP, mailMap);
	}
}
