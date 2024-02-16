/*
 * MailTrolleyKey.java Created on 27 June, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Strategy;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailTrolleyKey.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5991	:	27-Jun-2016	:	Draft
 */
@KeyTable(table = "MALTRLKEY", keyColumn = "KEYTYP", valueColumn = "MAXSEQNUM")
@TableKeyGenerator(name = "ID_GEN", table = "MALTRLKEY", key = "MALTRL_KEY")
@Embeddable
public class MailTrolleyKey implements Serializable {

	/**
	 * Field 	: serialVersionUID	of type : long
	 * Used for :
	 */
	private static final long serialVersionUID = 1L;

	private String companyCode;

	private String keyCondition;
	
	private String sequenceIdentifier;

	/**
	 * 
	 *	Overriding Method	:	@see java.lang.Object#equals(java.lang.Object)
	 *	Added by 			: A-4803 on 17-Oct-2014
	 * 	Used for 	:   equals method
	 *	Parameters	:	@param other
	 *	Parameters	:	@return
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	
	/**
	 * 
	 *	Constructor	: 	MailTrolleyKey
	 *	Created by	:	A-4803
	 *	Created on	:	17-Oct-2014
	 */
	public MailTrolleyKey() {

	}
	
	/**
	 * 
	 *	Overriding Method	:	@see java.lang.Object#hashCode()
	 *	Added by 			: A-4803 on 17-Oct-2014
	 * 	Used for 	:	hashCode method
	 *	Parameters	:	@return int
	 */
	public int hashCode() {
		return new StringBuilder(companyCode).append(keyCondition).append(sequenceIdentifier).toString().hashCode();
	}

	/**
	 * 
	 * 	Method		:	MailTrolleyKey.setCompanyCode
	 *	Added by 	:	A-4803 on 17-Oct-2014
	 * 	Used for 	:   setting company code
	 *	Parameters	:	@param companyCode 
	 *	Return type	: 	void
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	@KeyCondition(column = "KEYCDN")
	public String getKeyCondition() {
		return keyCondition;
	}
	
	/**
	 * 
	 * 	Method		:	MailTrolleyKey.setKeyCondition
	 *	Added by 	:	A-4803 on 17-Oct-2014
	 * 	Used for 	:   setting key condition
	 *	Parameters	:	@param keyCondition 
	 *	Return type	: 	void
	 */
	public void setKeyCondition(String keyCondition) {
		this.keyCondition = keyCondition;
	}

	/**
	 * 
	 * 	Method		:	MailTrolleyKey.getSequenceIdentifier
	 *	Added by 	:	A-4803 on 17-Oct-2014
	 * 	Used for 	:   generating sequence id
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	

	@Key(generator = "ID_GEN", startAt = "1")
	public String getSequenceIdentifier() {
		return sequenceIdentifier;
	}

	/**
	 * 
	 * 	Method		:	MailTrolleyKey.setSequenceIdentifier
	 *	Added by 	:	A-4803 on 17-Oct-2014
	 * 	Used for 	:   setting the sequence identifier
	 *	Parameters	:	@param sequenceIdentifier 
	 *	Return type	: 	void
	 */
	public void setSequenceIdentifier(String sequenceIdentifier) {
		this.sequenceIdentifier = sequenceIdentifier;
	}

	/**
	 * 
	 *	Overriding Method	:	@see java.lang.Object#toString()
	 *	Added by 			: A-4803 on 17-Oct-2014
	 * 	Used for 	:   toString()
	 *	Parameters	:	@return String
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(98);
		sbul.append("MailTrolleyKey [ ");
		sbul.append("sequenceIdentifier '").append(this.sequenceIdentifier);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', keyCondition '").append(this.keyCondition);
		sbul.append("' ]");
		return sbul.toString();
	}
	
}