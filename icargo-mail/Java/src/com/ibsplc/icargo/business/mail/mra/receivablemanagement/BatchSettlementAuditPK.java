
/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController.java
 *
 *	Created by	:	A-10647
 *	Created on	:	7-Jan-2022
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

@KeyTable(table="MALMRABTHSTLAUDKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="AUDSEQ_GEN",table="MALMRABTHSTLAUDKEY",key="MALBTHSTL_SEQ")
@Embeddable
public class BatchSettlementAuditPK implements Serializable{
	private String companyCode;
	private String batchSettlementID;
	private long serialNumber;
	
	
	public BatchSettlementAuditPK(){
		
	}
	
	public BatchSettlementAuditPK(String companyCode,String batchSettlementID,long serialNumber){
		this.companyCode = companyCode;
		this.batchSettlementID = batchSettlementID; 
		this.serialNumber = serialNumber;
	} 
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@KeyCondition(column = "BTHSTLIDR")
	public String getBatchSettlementID() {
		return batchSettlementID;
	}

	public void setBatchSettlementID(String batchSettlementID) {
		this.batchSettlementID = batchSettlementID;
	}
 
	
	/**  
	 * @return the serialNumber
	 */
    @Key(generator="AUDSEQ_GEN",startAt="1")
	public long getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * @param serialNumber the serialNumber to set
	 */

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
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
			return new StringBuffer(companyCode).
					append(batchSettlementID).append(serialNumber).toString().hashCode();
		}
		
		
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder(149);
			sbul.append("BatchSettlementAuditPK [ ");
			sbul.append("', companyCode '").append(this.companyCode);
			sbul.append("', batchSettlementID '").append(this.batchSettlementID);
			sbul.append("', serialNumber '").append(this.serialNumber);
			sbul.append("' ]");
			return sbul.toString();
		}
	
}
