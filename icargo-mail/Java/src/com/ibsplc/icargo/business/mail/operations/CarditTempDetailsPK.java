/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetailsPK.java
 *
 *	Created by	:	A-6287
 *	Created on	:	26-Feb-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetailsPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	26-Feb-2020	:	Draft
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALCDTMSGTMP_SEQ")
@Embeddable
public class CarditTempDetailsPK implements Serializable{
	
	private String companyCode;
    /**
     *The  sequence number
     */
    private long  sequenceNumber;
    
    private String ConsignmentIdentifier;

	/**
	 * 	Getter for consignmentIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	
    public void setCompanyCode(String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}

	

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
    @Key(generator = "ID_GEN", startAt = "1" )
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}
    
    @Column(name="CNSMNTIDR")
	public String getConsignmentIdentifier() {
		return ConsignmentIdentifier;
	}

	/**
	 *  @param consignmentIdentifier the consignmentIdentifier to set
	 * 	Setter for consignmentIdentifier 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setConsignmentIdentifier(String consignmentIdentifier) {
		ConsignmentIdentifier = consignmentIdentifier;
	}

}
