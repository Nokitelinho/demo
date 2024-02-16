/*
 * ViewInvoiceSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;


import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ViewInvoiceSession;

/**
 * @author A-2001
 *
 */
public class ViewInvoiceSessionImpl extends AbstractScreenSession
		implements ViewInvoiceSession {

	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.viewinvoice";
	
	
	private static final String KEY_INVOICEREFNO = "invoiceRefNos";
	
	private static final String KEY_INVOICETO = "invoicedTo";
	
	private static final String KEY_INVOICEDATE = "invoiceDate";
	
	private static final String KEY_INVOICETONAME = "invoiceToName";
	
	private static final String KEY_TRANSACTIONDETAILS = "uldTransactionDetailsVO";
	
	private static final String KEY_TRANSACTIONMAP = "uldTransactionMap";
	
	
	/**
	 * @return Returns the uldTransactionMap.
	 */
	public HashMap<String, ArrayList<ULDTransactionDetailsVO>> getUldTransactionMap() {
		return getAttribute(KEY_TRANSACTIONMAP);
	}

	/**
	 * @param uldTransactionMap The uldTransactionMap to set.
	 */
	public void setUldTransactionMap(
			HashMap<String, ArrayList<ULDTransactionDetailsVO>> uldTransactionMap) {
		 setAttribute(KEY_TRANSACTIONMAP,uldTransactionMap);
	}

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
	 * @return Returns the invoiceDate.
	 */
	public ArrayList<String> getInvoiceDate() {
		 return getAttribute(KEY_INVOICEDATE);
	}

	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(ArrayList<String> invoiceDate) {
		 setAttribute(KEY_INVOICEDATE,invoiceDate);
	}

	/**
	 * @return Returns the invoicedTo.
	 */
	public ArrayList<String> getInvoicedTo() {
		 return getAttribute(KEY_INVOICETO);
	}

	/**
	 * @param invoicedTo The invoicedTo to set.
	 */
	public void setInvoicedTo(ArrayList<String> invoicedTo) {
		 setAttribute(KEY_INVOICETO,invoicedTo);
	}

	/**
	 * @return Returns the invoiceRefNos.
	 */
	public ArrayList<String> getInvoiceRefNos() {
		return getAttribute(KEY_INVOICEREFNO);
	}

	/**
	 * @param invoiceRefNos The invoiceRefNos to set.
	 */
	public void setInvoiceRefNos(ArrayList<String> invoiceRefNos) {
		setAttribute(KEY_INVOICEREFNO,invoiceRefNos);
	}

	/**
	 * @return Returns the invoiceToName.
	 */
	public ArrayList<String> getInvoiceToName() {
		return getAttribute(KEY_INVOICETONAME);
	}

	/**
	 * @param invoiceToName The invoiceToName to set.
	 */
	public void setInvoiceToName(ArrayList<String> invoiceToName) {
		setAttribute(KEY_INVOICETONAME,invoiceToName);
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
