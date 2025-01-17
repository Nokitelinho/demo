/**
 *GPABillingSettlementInvoiceDetailPK.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author a-4823
 *
 */

@Embeddable
public class GPABillingSettlementInvoiceDetailPK implements Serializable{
	private String companyCode;
	private String gpaCode;
	private String settlementReferenceNumber;
	private int settlementSequenceNumber;
	private String invoiceNumber;
	private int settlementSerialNumber;
	private long mailSeqNum;//added by a-7871 for ICRD-235799
	/**
	 * @return the mailSeqNum
	 */
	public long getMailSeqNum() {
		return mailSeqNum;
	}

	/**
	 * @param mailSeqNum the mailSeqNum to set
	 */
	public void setMailSeqNum(long mailSeqNum) {
		this.mailSeqNum = mailSeqNum;
	}

	//default constructor
	public GPABillingSettlementInvoiceDetailPK(){

	}
	
	/**
	 * @param companyCode
	 * @param gpaCode
	 * @param settlementReferenceNumber
	 * @param settlementSequenceNumber
	 * @param invoiceNumber
	 * @param settlementSerialNumber
	 */
	public GPABillingSettlementInvoiceDetailPK(String companyCode,
			String gpaCode, String settlementReferenceNumber,
			int settlementSequenceNumber, String invoiceNumber,
			int settlementSerialNumber) {
		super();
		this.companyCode = companyCode;
		this.gpaCode = gpaCode;
		this.settlementReferenceNumber = settlementReferenceNumber;
		this.settlementSequenceNumber = settlementSequenceNumber;
		this.invoiceNumber = invoiceNumber;
		this.settlementSerialNumber = settlementSerialNumber;
	}

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
	/**
	 * @return the gpaCode
	 */
	
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the settlementReferenceNumber
	 */
	
	public String getSettlementReferenceNumber() {
		return settlementReferenceNumber;
	}
	/**
	 * @param settlementReferenceNumber the settlementReferenceNumber to set
	 */
	public void setSettlementReferenceNumber(String settlementReferenceNumber) {
		this.settlementReferenceNumber = settlementReferenceNumber;
	}
	/**
	 * @return the settlementSequenceNumber
	 */
	
	public int getSettlementSequenceNumber() {
		return settlementSequenceNumber;
	}
	/**
	 * @param settlementSequenceNumber the settlementSequenceNumber to set
	 */
	public void setSettlementSequenceNumber(int settlementSequenceNumber) {
		this.settlementSequenceNumber = settlementSequenceNumber;
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
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * 
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).    		
		append(gpaCode).append(settlementReferenceNumber).
		append(settlementSequenceNumber).append(invoiceNumber).
		append(settlementSerialNumber).append(mailSeqNum).
		toString().hashCode();
	}
	/**
	 * @return the settlementSerialNumber
	 */

	public int getSettlementSerialNumber() {
		return settlementSerialNumber;
	}
	/**
	 * @param settlementSerialNumber the settlementSerialNumber to set
	 */
	public void setSettlementSerialNumber(int settlementSerialNumber) {
		this.settlementSerialNumber = settlementSerialNumber;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(209);
		sbul.append("GPABillingSettlementInvoiceDetailPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpaCode '").append(this.gpaCode);
		sbul.append("', invoiceNumber '").append(this.invoiceNumber);
		sbul.append("', settlementReferenceNumber '").append(
				this.settlementReferenceNumber);
		sbul.append("', settlementSequenceNumber '").append(
				this.settlementSequenceNumber);
		sbul.append("', settlementSerialNumber '").append(
				this.settlementSerialNumber);
		sbul.append("', mailSeqNum '").append(
				this.mailSeqNum);
		sbul.append("' ]");
		return sbul.toString();
	}

}
