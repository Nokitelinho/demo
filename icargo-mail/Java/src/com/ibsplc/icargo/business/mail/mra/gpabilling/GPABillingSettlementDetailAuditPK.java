/*
 * GPABillingSettlementDetailAuditPK.java Created on Jun 6, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-7871 
 * @version	0.1, Jun 6, 2018
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 6, 2018	     A-7871		First draft
 */
@Embeddable
@KeyTable(table="MALMRAGPAINVDTLAUDKEY", keyColumn="KEYTYP", valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN", table="MALMRAGPAINVDTLAUDKEY", key="SERNUM")
public class GPABillingSettlementDetailAuditPK implements Serializable{
	
	private String companyCode;
	
	private long malSeqnum;
	private String serialNumber;
	private String invoiceNumber;
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GPABillingSettlementDetailAuditPK [companyCode=" + companyCode
				+ ", malSeqnum=" + malSeqnum + ", serialNumber=" + serialNumber
				+ ", invoiceNumber=" + invoiceNumber + "]";
	}
	/**
	 * @return the malSeqnum
	 */
	public long getMalSeqnum() {
		return malSeqnum;
	}
	/**
	 * @param malSeqnum the malSeqnum to set
	 */
	public void setMalSeqnum(long malSeqnum) {
		this.malSeqnum = malSeqnum;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	

}
