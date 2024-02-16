/*
 * ULDRepairInvoiceVO.java Created on Feb 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class ULDRepairInvoiceVO extends AbstractVO implements Serializable{
	
	private String invoiceRefNumber ;
	private LocalDate invoiceDate;
	private String invoiceToCode;
	private String invoiceToName;
	private String totalwaived;
	private String totalinvocied;
	private String totalamount;
	private Collection<ULDRepairInvoiceDetailsVO> uLDRepairInvoiceDetailsVOs;
	/**
	 * @return Returns the invoiceDate.
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate The invoiceDate to set.
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return Returns the invoiceRefNumber.
	 */
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}
	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}
	/**
	 * @return Returns the invoiceToCode.
	 */
	public String getInvoiceToCode() {
		return invoiceToCode;
	}
	/**
	 * @param invoiceToCode The invoiceToCode to set.
	 */
	public void setInvoiceToCode(String invoiceToCode) {
		this.invoiceToCode = invoiceToCode;
	}
	/**
	 * @return Returns the invoiceToName.
	 */
	public String getInvoiceToName() {
		return invoiceToName;
	}
	/**
	 * @param invoiceToName The invoiceToName to set.
	 */
	public void setInvoiceToName(String invoiceToName) {
		this.invoiceToName = invoiceToName;
	}
	/**
	 * @return Returns the uLDRepairInvoiceDetailsVOs.
	 */
	public Collection<ULDRepairInvoiceDetailsVO> getULDRepairInvoiceDetailsVOs() {
		return uLDRepairInvoiceDetailsVOs;
	}
	/**
	 * @param repairInvoiceDetailsVOs The uLDRepairInvoiceDetailsVOs to set.
	 */
	public void setULDRepairInvoiceDetailsVOs(
			Collection<ULDRepairInvoiceDetailsVO> repairInvoiceDetailsVOs) {
		uLDRepairInvoiceDetailsVOs = repairInvoiceDetailsVOs;
	}
	/**
	 * @return Returns the totalamount.
	 */
	public String getTotalamount() {
		return totalamount;
	}
	/**
	 * @param totalamount The totalamount to set.
	 */
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	/**
	 * @return Returns the totalinvocied.
	 */
	public String getTotalinvocied() {
		return totalinvocied;
	}
	/**
	 * @param totalinvocied The totalinvocied to set.
	 */
	public void setTotalinvocied(String totalinvocied) {
		this.totalinvocied = totalinvocied;
	}
	/**
	 * @return Returns the totalwaived.
	 */
	public String getTotalwaived() {
		return totalwaived;
	}
	/**
	 * @param totalwaived The totalwaived to set.
	 */
	public void setTotalwaived(String totalwaived) {
		this.totalwaived = totalwaived;
	}


}
