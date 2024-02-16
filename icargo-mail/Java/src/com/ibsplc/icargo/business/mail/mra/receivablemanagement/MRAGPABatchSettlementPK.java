package com.ibsplc.icargo.business.mail.mra.receivablemanagement;

import java.io.Serializable;

import javax.persistence.Embeddable;


import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlementPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8331	:	10-Nov-2021	:	Draft
 */
	
	
	
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRAGPABTHSTL_SEQ")
@Embeddable
public class MRAGPABatchSettlementPK implements Serializable{

	
	private String companyCode;
	private String batchId;
	private String poaCode;
	private long batchSequenceNumber;
	
	 public MRAGPABatchSettlementPK(){
	    }
	 
	 
	public MRAGPABatchSettlementPK(String companyCode,String batchId,String poaCode,long batchSequenceNumber) {
		
		this.companyCode = companyCode;
		this.batchSequenceNumber = batchSequenceNumber;
		this.poaCode = poaCode;
		this.batchId = batchId;
	}
	
	/**
	 * @return the sequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1" )
	public long getBatchSequenceNumber() {
		return batchSequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setBatchSequenceNumber(long batchSequenceNumber) {
		this.batchSequenceNumber = batchSequenceNumber;
	}
	/**
	 * @return the comapnyCode
	 */
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	
	/**
	 * @param object
	 * @return boolean
	 */
	   public boolean equals(Object other) {
			return (other != null) && (hashCode() == other.hashCode());
		}
	
	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode)
				.append(batchId).append(poaCode).append(batchSequenceNumber).toString().hashCode();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(199);
		sbul.append("MRAGPABillingDetailsPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', batchId '").append(this.batchId);
		sbul.append("', poaCode '").append(this.poaCode);
		sbul.append("', batchSequenceNumber '").append(this.batchSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

	
}
	
	
	
	
	
	
	
	
	
	
	


