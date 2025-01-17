/*
 * StockRangeUtilisationHistoryPK.java Created on Jan 18,2008.
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

@KeyTable(table = "STKRNGUTLHISKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "STKRNGUTLHISKEY", key = "STKUTLKEY_SEQ")
@Embeddable
/**
 * Author A-3155 & A-3184
 *
 */
public class StockRangeUtilisationHistoryPK implements Serializable{


	private String companyCode;

	private int airlineIdentifier;

	private String stockHolderCode;

	private String documentType;

	private String documentSubType;

	private String serialNumber;


	public StockRangeUtilisationHistoryPK(){

	}

	/**
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}


	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(serialNumber)
				.toString().hashCode();
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	@KeyCondition(column = "ARLIDR")
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the companyCode.
	 */
	@KeyCondition(column = "CMPCOD")
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
	 * @return Returns the documentSubType.
	 */
	@KeyCondition(column = "DOCSUBTYP")
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	@KeyCondition(column = "DOCTYP")
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	@KeyCondition(column = "STKHLDCOD")
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
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(184);
		sbul.append("StockRangeUtilisationHistoryPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', documentSubType '").append(this.documentSubType);
		sbul.append("', documentType '").append(this.documentType);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("', stockHolderCode '").append(this.stockHolderCode);
		sbul.append("' ]");
		return sbul.toString();
	}


}
