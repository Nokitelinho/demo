/*
 * StockRangeHistoryPK.java Created on Jan 18,2008.
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

/**
 * @author A-3184 & A-3155
 *
 */

@KeyTable(table = "STKRNGTXNHISKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "STKRNGTXNHISKEY", key = "STKRNGHIS_SEQ")
@Embeddable
public class StockRangeHistoryPK implements Serializable {

	/**
	 * Company Code
	 */

	private String companyCode;

	private int airlineIdentifier;

	private String fromStockHolderCode;

	private String documentType;

	private String documentSubType;

	private String serialNumber;

	public StockRangeHistoryPK() {

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
			.append(airlineIdentifier).append(fromStockHolderCode).append(documentType).append(documentSubType)
				.toString().hashCode();
	}
	/**
	 * @param companyCode The companyCode to set.
	 */

	public void setCompanyCode(String companyCode) {
		this.companyCode=companyCode;
	}
	/**
	 * @return Returns the companyCode.
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber=serialNumber;
	}
	/**
	 * @return Returns the SerialNumber.
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public String getSerialNumber() {
		return this.serialNumber;
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
	 * @return Returns the fromStockHolderCode.
	 */
	@KeyCondition(column = "FRMSTKHLDCOD")
	public String getFromStockHolderCode() {
		return fromStockHolderCode;
	}

	/**
	 * @param fromStockHolderCode The fromStockHolderCode to set.
	 */
	public void setFromStockHolderCode(String fromStockHolderCode) {
		this.fromStockHolderCode = fromStockHolderCode;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(177);
		sbul.append("StockRangeHistoryPK [ ");
		sbul.append("airlineIdentifier '").append(this.airlineIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', documentSubType '").append(this.documentSubType);
		sbul.append("', documentType '").append(this.documentType);
		sbul.append("', fromStockHolderCode '")
				.append(this.fromStockHolderCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
