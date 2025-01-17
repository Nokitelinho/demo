/*
 * SettlementHistoryPK.java created on Mar 26, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-2280
 *
 */
@KeyTable(table="MTKGPASTLHISKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN",table="MTKGPASTLHISKEY",key="MTKGPASTLHIS_SEQ")
@Embeddable
public class SettlementHistoryPK implements Serializable{

	/**
	 * 
	 */

	private String companyCode;
	/**
	 * 
	 */

	private String gpaCode;
	/**
	 * 
	 */

	private String invoiceNumber;
	/**
	 * 
	 */

	private int serialNumber;
	/**
	 * 
	 */
	public SettlementHistoryPK() {
	
	}
	/**
	 * @author A-2280
	 * @param companyCode
	 * @param gpaCode
	 * @param invoiceNumber
	 */
	public SettlementHistoryPK(String companyCode,String gpaCode,String invoiceNumber){
		this.companyCode=companyCode;
		this.gpaCode=gpaCode;
		this.invoiceNumber=invoiceNumber;		
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj!=null){
			return (this.hashCode()==obj.hashCode());
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * @return
	 */
	@Override
     public int hashCode() {		
		return new StringBuilder().append(companyCode).append(gpaCode).
		append(invoiceNumber).append(serialNumber).toString().hashCode();
	}
	
	
	

	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 
	 * @param serialNumber
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber=serialNumber;
	}
	/**
	 * 
	 * @return
	 */
	 @Key(generator="ID_GEN",startAt="1")
	public int getSerialNumber() {
		return this.serialNumber;
	}

	 /**
	  * 
	  * @param gpaCode
	  */
	public void setGpaCode(java.lang.String gpaCode) {
		this.gpaCode=gpaCode;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "GPACOD")
	public java.lang.String getGpaCode() {
		return this.gpaCode;
	}

	/**
	 * 
	 * @param invoiceNumber
	 */
	public void setInvoiceNumber(java.lang.String invoiceNumber) {
		this.invoiceNumber=invoiceNumber;
	}
	/**
	 * 
	 * @return
	 */
	@KeyCondition(column = "INVNUM")
	public java.lang.String getInvoiceNumber() {
		return this.invoiceNumber;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(110);
		sbul.append("SettlementHistoryPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpaCode '").append(this.gpaCode);
		sbul.append("', invoiceNumber '").append(this.invoiceNumber);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
