/*
 * RepairInvoiceSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;


import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.RepairInvoiceSession;

/**
 * @author A-2001
 *
 */
public class RepairInvoiceSessionImpl extends AbstractScreenSession
		implements RepairInvoiceSession {

	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID =
		"uld.defaults.repairinvoice";
	
	
	private static final String KEY_INVOICEREFNO = "invoiceRefNos";
	
	private static final String KEY_INVOICETO = "invoicedTo";
	
	private static final String KEY_INVOICEDATE = "invoiceDate";
	
	private static final String KEY_INVOICETONAME = "invoiceToName";
	
	private static final String KEY_REPAIRDETAILS = "uldRepairDetailsVO";
	
	private static final String KEY_REPAIRMAP = "uldRepairInvoiceVO";
	
	
	/**
	 * @return Returns the uldTransactionMap.
	 */
	public HashMap<String,ULDRepairInvoiceVO> getUldRepairInvoiceVO() {
		return getAttribute(KEY_REPAIRMAP);
	}

	/**
	 * @param uldRepairInvoiceVO
	 */
	public void setUldRepairInvoiceVO(
			HashMap<String,ULDRepairInvoiceVO> uldRepairInvoiceVO) {
		 setAttribute(KEY_REPAIRMAP,uldRepairInvoiceVO);
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
	 * @return Returns the uldRepairDetailsVO.
	 */
	public ArrayList<ULDRepairInvoiceDetailsVO> getUldRepairDetailsVO() {
		return getAttribute(KEY_REPAIRDETAILS);
	}

	/**
	 * @param uldRepairDetailsVOs
	 */
	public void setUldRepairDetailsVO(
			ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs) {
		setAttribute(KEY_REPAIRDETAILS,uldRepairDetailsVOs);
	}
	
	
  
}
