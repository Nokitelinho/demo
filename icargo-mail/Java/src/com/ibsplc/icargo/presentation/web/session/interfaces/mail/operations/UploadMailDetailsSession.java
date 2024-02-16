/*
 * UploadMailDetailsSession.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-3817
 * 
 */
public interface UploadMailDetailsSession extends ScreenSession {
	/**
	 * 
	 * @return
	 */
	public Collection<ScannedMailDetailsVO> getScannedMailDetailsVOs();

	/**
	 * 
	 * @param scannedMailDetailsVOs
	 */
	public void setScannedMailDetailsVOs(
			Collection<ScannedMailDetailsVO> scannedMailDetailsVOs);

	public void setMailExceptionDetails(
			Collection<MailbagVO> mailExceptionVOs);

	public Collection<MailbagVO> getMailExceptionDetails();

	public void setSelectedScannedMailDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO);
	public ScannedMailDetailsVO getSelectedScannedMailDetailsVO();

}
