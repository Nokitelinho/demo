/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxIdPk.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 5, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxIdPk.java
 *	Version		:	Name	:	Date			:	Updation
 * --------------------------------------------------- 0.1 : A-4809 : Aug 5,
 * 2016 : Draft
 */
@Embeddable
public class MailBoxIdPk implements Serializable{
	  /**
	   * The companyCode
	   */

  private String companyCode;
	/**
	 * The mailboxCode
	 */

  private String mailboxCode; 
  
	  /**
	   * @param other
	   * @return
	   */

	  public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

  /**
   *@return 
   * 
   */
	public int hashCode() {
		return new StringBuffer(companyCode).append(mailboxCode).toString().hashCode();
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setMailboxCode(java.lang.String mailboxCode) {
		this.mailboxCode=mailboxCode;
	}

	public java.lang.String getMailboxCode() {
		return this.mailboxCode;
	}


	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(63);
		sbul.append("MailBoxIdPk [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', mailboxCode '").append(this.mailboxCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
