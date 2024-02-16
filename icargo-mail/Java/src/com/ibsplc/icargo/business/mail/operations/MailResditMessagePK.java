/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessagePK.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Jun 4, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessagePK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Jun 4, 2019	:	Draft
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALRDTMSGREF_SEQ")
@Embeddable
public class MailResditMessagePK implements Serializable{


	private String companyCode;
	private long messageIdentifier;

	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@Key(generator = "ID_GEN", startAt = "1")
	public long getMessageIdentifier() {
		return messageIdentifier;
	}

	public void setMessageIdentifier(long messageIdentifier) {
		this.messageIdentifier = messageIdentifier;
	}

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null) {
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;
    }
    
    public int hashCode() {
		return (this.companyCode + this.messageIdentifier).hashCode();
    } 

	
	

	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MLDMessageMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', messageIdentifier '").append(this.messageIdentifier);
		sbul.append("' ]");
		return sbul.toString();
	}

}
