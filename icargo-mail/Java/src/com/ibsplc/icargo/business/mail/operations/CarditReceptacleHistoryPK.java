/*
 * CarditReceptacleHistoryPK.java Created on Oct 05, 2010
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
 * @author A-1303
 *
 *
 */

@KeyTable(table = "MALCDTRCPHISKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALCDTRCPHISKEY", key = "HIS_SEQ_NUM")
@Embeddable
public class CarditReceptacleHistoryPK implements Serializable {
	/**
	 * The companyCode
	 */
	private String companyCode;

	/**
	 * The dsn
	 */
	private long mailSeqNum;

	/**
	 * The historySequenceNumber
	 */
	private int sequenceNumber;

	/**
	 * @param other
	 * @return
	 *
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {

		return new StringBuffer(companyCode).append(mailSeqNum).append(
				sequenceNumber).toString().hashCode();
	}

	/**
	 * @param companyCode
	 *            to set
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @return the receptacleId
	 */
	@KeyCondition(column = "MALSEQNUM")
	public long getMailSeqNum() {
		return mailSeqNum;
	}

	/**
	 * @param receptacleId
	 *            the receptacleId to set
	 */
	public void setMailSeqNum(long mailSeqNum) {
		this.mailSeqNum = mailSeqNum;
	}

	/**
	 * @return the sequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1")
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:52 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(98);
		sbul.append("CarditReceptacleHistoryPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', mailSeqNum '").append(this.mailSeqNum);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
