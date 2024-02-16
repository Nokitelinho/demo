/*
 * MailResditHistoryPK.java Created on June 27, 2016
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
 * TODO Add the purpose of this class
 * 
 * @author A-3109
 * 
 */
@KeyTable(table = "MALRDTHISKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALRDTHISKEY", key = "RDT_SEQNUM")
@Embeddable
public class MailResditHistoryPK implements Serializable {
	/**
	 * The companyCode
	 */

	private String companyCode;

	/**
	 * The mailId
	 */

	private String mailId;

	/**
	 * The eventCode
	 */

	private String eventCode;

	/**
	 * The sequenceNumber
	 */

	private long sequenceNumber;

	/**
	 * @param other
	 * @return
	 */
	public boolean equals(Object other) {
		return (other != null) && ((this.hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode).append(mailId)
				.append(eventCode).append(sequenceNumber).hashCode();
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setMailId(java.lang.String mailId) {
		this.mailId = mailId;
	}

	@KeyCondition(column = "MALIDR")
	public java.lang.String getMailId() {
		return this.mailId;
	}

	public void setEventCode(java.lang.String eventCode) {
		this.eventCode = eventCode;
	}

	@KeyCondition(column = "EVTCOD")
	public java.lang.String getEventCode() {
		return this.eventCode;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MailResditHistoryPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', eventCode '").append(this.eventCode);
		sbul.append("', mailId '").append(this.mailId);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
