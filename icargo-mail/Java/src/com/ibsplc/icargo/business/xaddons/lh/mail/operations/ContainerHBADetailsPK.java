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
package com.ibsplc.icargo.business.xaddons.lh.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxIdPk.java
 *	Version		:	Name	:	Date			:	Updation
 * --------------------------------------------------- 0.1 : A-4809 : Aug 5,
 * 2016 : Draft
 */
@Embeddable
public class ContainerHBADetailsPK implements Serializable{
	  /**
	   * The companyCode
	   */

  private String companyCode;
	/**
	 * The uldReferenceNo
	 */

  private long uldReferenceNo; 
  
	  /**
	   * @param other
	   * @return
	   */

	  public boolean equals(Object other) {
		return (other != null) && (hashCode() == other.hashCode());
	}

  /**
   *@return 
   * 
   */
	public int hashCode() {
		return new StringBuffer(companyCode).append(uldReferenceNo).toString().hashCode();
	}

	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo=uldReferenceNo;
	}

	public long getUldReferenceNo() {
		return this.uldReferenceNo;
	}


	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(63);
		sbul.append("ConatinerHBADetailsPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', uldReferenceNo '").append(this.uldReferenceNo);
		sbul.append("' ]");
		return sbul.toString();
	}
}
