/*
 * MailEventPK.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 * @author a-3109
 * 
 */
@Embeddable
public class MailEventPK implements Serializable {

	/**
	 * The companyCode
	 */

	private String companyCode;

	/**
	 * The paCode
	 */

	private String mailboxId;

	/**
	 * The mailCategory
	 */

	private String mailCategory;

	/**
	 * The mailSubClass
	 */

	private String mailSubClass;

	/**
	 * @param other
	 * @return
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @author A-1936
	 * @return
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(mailboxId)
				.append(mailCategory).append(mailSubClass).toString()
				.hashCode();
	}

	public void setMailboxId(java.lang.String mailboxId) {
		this.mailboxId = mailboxId;
	}

	@KeyCondition(column = "MALBOXIDR")
	public java.lang.String getMailboxId() {
		return this.mailboxId;
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setMailSubClass(java.lang.String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	@KeyCondition(column = "SUBCLSCOD")
	public java.lang.String getMailSubClass() {
		return this.mailSubClass;
	}

	public void setMailCategory(java.lang.String mailCategory) {
		this.mailCategory = mailCategory;
	}

	@KeyCondition(column = "MALCTGCOD")
	public java.lang.String getMailCategory() {
		return this.mailCategory;
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:53 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MailEventPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', mailCategory '").append(this.mailCategory);
		sbul.append("', mailSubClass '").append(this.mailSubClass);
		sbul.append("', mailboxId '").append(this.mailboxId);
		sbul.append("' ]");
		return sbul.toString();
	}
}