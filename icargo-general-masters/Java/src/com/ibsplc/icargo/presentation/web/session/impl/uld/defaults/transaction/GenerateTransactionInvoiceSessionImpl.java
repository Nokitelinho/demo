/*
 * GenerateTransactionInvoiceSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;


import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.GenerateTransactionInvoiceSession;

/**
 * @author A-2001
 *
 */
public class GenerateTransactionInvoiceSessionImpl extends AbstractScreenSession
		implements GenerateTransactionInvoiceSession {

	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.generatetransactioninvoice";	
	
	private static final String KEY_TRANSACTIONDETAILS = "uldTransactionDetailsVO";	
	
	
	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
	

	/**
	 * @return Returns the uldTransactionDetailsVO.
	 */
	public ArrayList<ULDTransactionDetailsVO> getUldTransactionDetailsVO() {
		return getAttribute(KEY_TRANSACTIONDETAILS);
	}

	/**
	 * @param uldTransactionDetailsVOs
	 */
	public void setUldTransactionDetailsVO(
			ArrayList<ULDTransactionDetailsVO> uldTransactionDetailsVOs) {
		setAttribute(KEY_TRANSACTIONDETAILS,uldTransactionDetailsVOs);
	}
	
	
  
}
