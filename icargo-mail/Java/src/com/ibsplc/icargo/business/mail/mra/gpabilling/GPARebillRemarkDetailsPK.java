/*
 * GPARebillRemarkDetailsPK.java Created on Jan 17, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

// TODO: Auto-generated Javadoc
/**
 * The Class GPARebillRemarkDetailsPK.
 *
 * @author a-5526
 */
@Embeddable
public class GPARebillRemarkDetailsPK implements Serializable {

	/** The company code. */
	private String companyCode;
	
	/** The GPA code. */
	private String gpaCode;
	
    /** The invoice number. */
    private String invoiceNumber;
    
    /** The invoice serial number. */
    private int invoiceSerialNumber;
	
      
    /** The mail sequence number. */
    private long mailSequenceNumber;
    
	/** The rebill round. */
	private int rebillRound;
	
    /**
     * Default constructor.
     */
    public GPARebillRemarkDetailsPK(){
    	
    }
    
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new StringBuffer(companyCode).append(gpaCode).append(
				invoiceNumber).append(invoiceSerialNumber).append(mailSequenceNumber).append(rebillRound).toString().hashCode();
	}


	public boolean equals(Object other) {
		return (other != null) && ((this.hashCode() == other.hashCode()));  
	}    




	/**
	 * Instantiates a new MRA GPA rebill remark pk.
	 *
	 * @param companyCode the company code
	 * @param invoiceNumber the invoice number
	 * @param invoiceSerialNumber the invoice serial number
	 * @param invoiceSequenceNumber the invoice sequence number
	 * @param rebillRound the rebill round
	 */
	public GPARebillRemarkDetailsPK(String companyCode,String gpaCode, String invoiceNumber,
			int invoiceSerialNumber, long mailSequenceNumber, int rebillRound) {
		this.companyCode = companyCode;
		this.gpaCode=gpaCode;
		this.invoiceNumber = invoiceNumber;
		this.invoiceSerialNumber = invoiceSerialNumber;
		this.mailSequenceNumber = mailSequenceNumber;
		this.rebillRound = rebillRound;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GPARebillRemarkDetailsPK [companyCode=");
		builder.append(companyCode);
		builder.append(", gpaCode=");
		builder.append(gpaCode);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", invoiceSerialNumber=");
		builder.append(invoiceSerialNumber);
		builder.append(", mailSequenceNumber=");
		builder.append(mailSequenceNumber);
		builder.append(", rebillRound=");
		builder.append(rebillRound);
		builder.append("]");
		return builder.toString();
	}
	


	/**
	 * Gets the company code.
	 *
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Gets the invoice number.
	 *
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * Sets the invoice number.
	 *
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * Gets the invoice serial number.
	 *
	 * @return the invoiceSerialNumber
	 */
	public int getInvoiceSerialNumber() {
		return invoiceSerialNumber;
	}

	/**
	 * Sets the invoice serial number.
	 *
	 * @param invoiceSerialNumber the invoiceSerialNumber to set
	 */
	public void setInvoiceSerialNumber(int invoiceSerialNumber) {
		this.invoiceSerialNumber = invoiceSerialNumber;
	}



	public String getGpaCode() {
		return gpaCode;
	}


	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}


	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}


	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}


	/**
	 * Gets the rebill round.
	 *
	 * @return the rebillRound
	 */
	public int getRebillRound() {
		return rebillRound;
	}

	/**
	 * Sets the rebill round.
	 *
	 * @param rebillRound the rebillRound to set
	 */
	public void setRebillRound(int rebillRound) {
		this.rebillRound = rebillRound;
	}

}
