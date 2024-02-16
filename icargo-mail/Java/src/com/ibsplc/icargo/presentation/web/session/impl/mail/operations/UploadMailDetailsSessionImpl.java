/*
 * UploadMailDetailsSessionImpl.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;

/**
 * @author a-3817
 * 
 */
public class UploadMailDetailsSessionImpl extends AbstractScreenSession
		implements UploadMailDetailsSession {

	private static final String KEY_MODULE_NAME = "mail.operations";

	private static final String KEY_SCREEN_ID = "mailtracking.defaults.batchmailupload";

	private static final String KEY_SCANNED_MAIL_DETAILS = "SCANNED_MAIL_DETAILS";
	private static final String KEY_SCANNED_MAIL_EXCEPTION_DETAILS = "SCANNED_MAIL_EXCEPTION_DETAILS";
	private static final String KEY_SELECTED_SCANNED_MAIL_DETAIL = "SELECTED_SCANNED_MAIL_DETAIL";

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
     * 
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
	 * 
	 * @param scannedMailDetailsVOs
	 */
	public void setMailExceptionDetails(Collection<MailbagVO> mailExceptionVOs){
		setAttribute(KEY_SCANNED_MAIL_EXCEPTION_DETAILS, (ArrayList<MailbagVO>)mailExceptionVOs);
	}
	/**
	 * 
	 * @return
	 */
    public Collection<MailbagVO> getMailExceptionDetails(){
    	return (Collection<MailbagVO>)getAttribute(KEY_SCANNED_MAIL_EXCEPTION_DETAILS);
    }
    /**
     * 
     * @param scannedMailDetailsVO
     */
    public void setSelectedScannedMailDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO){
    	setAttribute(KEY_SELECTED_SCANNED_MAIL_DETAIL, scannedMailDetailsVO);
    }
    /**
     * 
     * @return
     */
    public ScannedMailDetailsVO getSelectedScannedMailDetailsVO(){
    	return (ScannedMailDetailsVO)getAttribute(KEY_SELECTED_SCANNED_MAIL_DETAIL);
    }
}
