/*
 * GenerateTransactionInvoiceSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1347
 *
 */
public interface GenerateTransactionInvoiceSession extends ScreenSession {
	
	/**
	 * @return Returns the uldTransactionDetailsVO.
	 */
	public ArrayList<ULDTransactionDetailsVO> getUldTransactionDetailsVO(); 

	/**
	 * @param uldTransactionDetailsVO The uldTransactionDetailsVO to set.
	 */
	public void setUldTransactionDetailsVO(
			ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVO) ;
	
}
