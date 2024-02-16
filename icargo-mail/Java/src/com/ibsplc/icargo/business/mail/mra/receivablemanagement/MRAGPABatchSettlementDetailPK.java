/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement.java
 *
 *	Created by	:	A-5219
 *	Created on	:	11-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;


	
	
	

@Embeddable
public class MRAGPABatchSettlementDetailPK implements Serializable{
	
    private String companyCode;    
    
    private String batchId;
    
    private long batchSequenceNumber;
    
    private String gpaCode;
    
    private long mailSeqNum;
    
    public MRAGPABatchSettlementDetailPK(){
    	super();
    }
    
    /**
     * 
     *	Constructor	: 	@param companyCode
     *	Constructor	: 	@param batchId
     *	Constructor	: 	@param batchSerialNum
     *	Constructor	: 	@param gpaCode
     *	Constructor	: 	@param mailSeqNum
     *	Created by	:	A-5219
     *	Created on	:	15-Nov-2021
     */
    public MRAGPABatchSettlementDetailPK(String companyCode, String batchId, long batchSequenceNumber,
    		String gpaCode, long mailSeqNum){
    	super();
    	this.companyCode = companyCode;
    	this.batchId = batchId;
    	this.batchSequenceNumber = batchSequenceNumber;
    	this.mailSeqNum = mailSeqNum;
    	this.gpaCode = gpaCode;
    }

    @KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@KeyCondition(column = "BTHSTLIDR")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	
	@KeyCondition(column = "BTHSTLSEQNUM")
	public long getBatchSequenceNumber() {
		return batchSequenceNumber;
	}

	public void setBatchSequenceNumber(long batchSequenceNumber) {
		this.batchSequenceNumber = batchSequenceNumber;
	}

	@KeyCondition(column = "POACOD")
	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	@KeyCondition(column = "MALSEQNUM")
	public long getMailSeqNum() {
		return mailSeqNum;
	}

	public void setMailSeqNum(long mailSeqNum) {
		this.mailSeqNum = mailSeqNum;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null) && (this.hashCode() == other.hashCode());
	}
   
	@Override
	public int hashCode() {

		return new StringBuffer(companyCode).
				append(batchId).
				append(batchSequenceNumber).
				append(gpaCode).
				append(mailSeqNum).
				toString().hashCode();
	}
	
	
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("MRAGPABatchSettlementDetailPK [ ");
		sbul.append("companyCode ").append(this.companyCode);
		sbul.append("batchId ").append(this.batchId);
		sbul.append("batchSequenceNumber '").append(this.batchSequenceNumber);
		sbul.append("gpaCode ").append(this.gpaCode);
		sbul.append("mailSeqNum ").append(this.mailSeqNum);
		sbul.append("' ]");
		return sbul.toString();
	}

}
