/*
 * DamagedDSNPK.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 * 
 * @author A-3109
 * 
 */
@KeyTable(table = "MALDMGDSNKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@Embeddable
@TableKeyGenerator(name = "ID_GEN", table = "MALDMGDSNKEY", key = "DMGDSN_KEY")
public class DamagedDSNPK implements Serializable {

	/**
	 * The Companycode
	 */

	private String companyCode;

	/**
	 * The dsn
	 */

	private String dsn;

	/**
	 * The originExchangeOffice
	 */

	private String originExchangeOffice;

	/**
	 * The destinationExchangeOffice
	 */

	private String destinationExchangeOffice;

	/**
	 * The mailCategorycode
	 */

	private String mailCategoryCode;

	/**
	 * The mailClass
	 */

	private String mailSubclass;

	/**
	 * The year
	 */

	private int year;

	/**
	 * The consignmentNumber
	 */

	private String consignmentNumber;

	/**
	 * The consignmentSequenceNumber
	 */

	private int consignmentSequenceNumber;

	/**
	 * The paCode
	 */

	private String paCode;

	/**
	 * The airportCode
	 */

	private String airportCode;

	/**
	 * The damageSequenceNumber
	 */

	private int damageSequenceNumber;

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(dsn)
				.append(originExchangeOffice).append(destinationExchangeOffice)
				.append(mailSubclass).append(year).append(consignmentNumber)
				.append(consignmentSequenceNumber).append(paCode)
				.append(damageSequenceNumber).append(airportCode)
				.append(mailCategoryCode).toString().hashCode();
	}

	/**
	 * @param other
	 * @return
	 */
	@Override
	public boolean equals(Object other) {
		return (other != null) && (this.hashCode() == other.hashCode());
	}

	public void setPaCode(java.lang.String paCode) {
		this.paCode = paCode;
	}

	@KeyCondition(column = "POACOD")
	public java.lang.String getPaCode() {
		return this.paCode;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@KeyCondition(column = "YER")
	public int getYear() {
		return this.year;
	}

	public void setDamageSequenceNumber(int damageSequenceNumber) {
		this.damageSequenceNumber = damageSequenceNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public int getDamageSequenceNumber() {
		return this.damageSequenceNumber;
	}

	public void setMailCategoryCode(java.lang.String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	@KeyCondition(column = "MALCTGCOD")
	public java.lang.String getMailCategoryCode() {
		return this.mailCategoryCode;
	}

	public void setAirportCode(java.lang.String airportCode) {
		this.airportCode = airportCode;
	}

	@KeyCondition(column = "ARPCOD")
	public java.lang.String getAirportCode() {
		return this.airportCode;
	}

	public void setConsignmentNumber(java.lang.String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	@KeyCondition(column = "CSGDOCNUM")
	public java.lang.String getConsignmentNumber() {
		return this.consignmentNumber;
	}

	public void setMailSubclass(java.lang.String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	@KeyCondition(column = "MALSUBCLS")
	public java.lang.String getMailSubclass() {
		return this.mailSubclass;
	}

	public void setDestinationExchangeOffice(
			java.lang.String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	@KeyCondition(column = "DSTEXGOFC")
	public java.lang.String getDestinationExchangeOffice() {
		return this.destinationExchangeOffice;
	}

	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	@KeyCondition(column = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return this.consignmentSequenceNumber;
	}

	public void setOriginExchangeOffice(java.lang.String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	@KeyCondition(column = "ORGEXGOFC")
	public java.lang.String getOriginExchangeOffice() {
		return this.originExchangeOffice;
	}

	public void setDsn(java.lang.String dsn) {
		this.dsn = dsn;
	}

	@KeyCondition(column = "DSN")
	public java.lang.String getDsn() {
		return this.dsn;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(326);
		sbul.append("DamagedDSNPK [ ");
		sbul.append("airportCode '").append(this.airportCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', consignmentNumber '").append(this.consignmentNumber);
		sbul.append("', consignmentSequenceNumber '").append(
				this.consignmentSequenceNumber);
		sbul.append("', damageSequenceNumber '").append(
				this.damageSequenceNumber);
		sbul.append("', destinationExchangeOffice '").append(
				this.destinationExchangeOffice);
		sbul.append("', dsn '").append(this.dsn);
		sbul.append("', mailCategoryCode '").append(this.mailCategoryCode);
		sbul.append("', mailSubclass '").append(this.mailSubclass);
		sbul.append("', originExchangeOffice '").append(
				this.originExchangeOffice);
		sbul.append("', paCode '").append(this.paCode);
		sbul.append("', year '").append(this.year);
		sbul.append("' ]");
		return sbul.toString();
	}
}
