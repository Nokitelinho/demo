/*
 * RepairInvoiceSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2001
 *
 */
public interface RepairInvoiceSession extends ScreenSession {
	/**
	 * @return Returns the invoiceDate.
	 */
	public ArrayList<String> getInvoiceDate(); 
	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(ArrayList<String> invoiceDate);

	/**
	 * @return Returns the invoicedTo.
	 */
	public ArrayList<String> getInvoicedTo(); 

	/**
	 * @param invoicedTo The invoicedTo to set.
	 */
	public void setInvoicedTo(ArrayList<String> invoicedTo); 

	/**
	 * @return Returns the invoiceRefNos.
	 */
	public ArrayList<String> getInvoiceRefNos(); 
	/**
	 * @param invoiceRefNos The invoiceRefNos to set.
	 */
	public void setInvoiceRefNos(ArrayList<String> invoiceRefNos);

	/**
	 * @return Returns the invoiceToName.
	 */
	public ArrayList<String> getInvoiceToName();

	/**
	 * @param invoiceToName The invoiceToName to set.
	 */
	public void setInvoiceToName(ArrayList<String> invoiceToName); 

	/**
	 * @return Returns the uldTransactionMap.
	 */
	public HashMap<String,ULDRepairInvoiceVO> getUldRepairInvoiceVO();

	/**
	 * @param uldTransactionMap The uldTransactionMap to set.
	 */
	public void setUldRepairInvoiceVO(
			HashMap<String,ULDRepairInvoiceVO> uldRepairInvoiceVO); 
	
	/**
	 * @return Returns the uldRepairDetailsVO.
	 */
	public ArrayList<ULDRepairInvoiceDetailsVO> getUldRepairDetailsVO();
	/**
	 * @param uldRepairDetailsVO The uldTransactionDetailsVO to set.
	 */
	public void setUldRepairDetailsVO(
			ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs);

}
