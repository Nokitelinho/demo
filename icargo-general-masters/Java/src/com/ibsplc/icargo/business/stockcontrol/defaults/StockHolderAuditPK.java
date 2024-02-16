/*
 * StockHolderAuditPK.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;


import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-1358
 *
 */

@KeyTable(table="STKHLDAUDKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="AUDSEQ_GEN",table="STKHLDAUDKEY",key="STKHLDAUD_SEQ")
@Embeddable
public class StockHolderAuditPK implements Serializable {

	/**
	 *
	 */

    private String companyCode;

	/**
	 *
	 */

    private String stockHolderCode;

    /*
     * Audit sequence number
     */
	/**
	 *
	 */

    private long sequenceNumber;

	public StockHolderAuditPK(){

	}
	public StockHolderAuditPK(String companyCode,String stockHolderCode,long sequenceNumber){
		this.companyCode=companyCode;
        this.stockHolderCode=stockHolderCode;
        this.sequenceNumber=sequenceNumber;
	}
    /**
     * @param other
     * @return
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}

	/**
	 * @return
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).append(stockHolderCode).append(sequenceNumber).
			toString().hashCode();
	}
	/**
	 * @return Returns the companyCode.
	 */
	@KeyCondition(column="CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	@Key(generator="AUDSEQ_GEN",startAt="1")
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the stockHolderCode.
	 */
	@KeyCondition(column="STKHLDCOD")
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:15 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(94);
		sbul.append("StockHolderAuditPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', sequenceNumber '").append(this.sequenceNumber);
		sbul.append("', stockHolderCode '").append(this.stockHolderCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}
