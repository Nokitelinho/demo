/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetailsPK.java
 *
 *	Created by	:	A-6287
 *	Created on	:	02-Mar-2020
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

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetailsPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	02-Mar-2020	:	Draft
 */
@Embeddable
public class CarditTempMailBagDetailsPK implements Serializable{
	
	private String companyCode;
    private long  sequenceNumber;
    private String ConsignmentIdentifier;
    private String DRTagNo;

	
    /**
	 * 
	 * 	Method		:	CarditTempMailBagDetailsPK.setCompanyCode
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode 
	 *	Return type	: 	void
	 */
    public void setCompanyCode(String companyCode) {
		this.companyCode=companyCode;
	}
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 
	 * 	Method		:	CarditTempMailBagDetailsPK.setSequenceNumber
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param sequenceNumber 
	 *	Return type	: 	void
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber=sequenceNumber;
	}
	@KeyCondition(column = "SEQNUM")
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
	
	/**
	 * 	Getter for dRTagNo 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	@Column(name="DRTAGNUM")
	public String getDRTagNo() {
		return DRTagNo;
	}

	/**
	 *  @param dRTagNo the dRTagNo to set
	 * 	Setter for dRTagNo 
	 *	Added by : A-6287 on 26-Feb-2020
	 * 	Used for :
	 */
	public void setDRTagNo(String dRTagNo) {
		DRTagNo = dRTagNo;
	}

}
