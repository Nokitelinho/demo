/*
 * StockHolderPK.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;




@Embeddable
public class StockHolderStockDetailPK implements Serializable{

    private String companyCode;
    
    private String stockHolderCode;
    
    private String documentType;
    
    private String documentSubType;
    
    private Integer txnDateString;
    
    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(stockHolderCode).append(
				documentType).append(documentSubType).append(txnDateString)
				.toString().hashCode();
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the documentSubType
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType the documentSubType to set
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the txnDateString
	 */
	public Integer getTxnDateString() {
		return txnDateString;
	}

	/**
	 * @param txnDateString the txnDateString to set
	 */
	public void setTxnDateString(Integer txnDateString) {
		this.txnDateString = txnDateString;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(150);
		sbul.append("StockHolderStockDetailPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', documentSubType '").append(this.documentSubType);
		sbul.append("', documentType '").append(this.documentType);
		sbul.append("', stockHolderCode '").append(this.stockHolderCode);
		sbul.append("', txnDateString '").append(this.txnDateString);
		sbul.append("' ]");
		return sbul.toString();
	}    
}
