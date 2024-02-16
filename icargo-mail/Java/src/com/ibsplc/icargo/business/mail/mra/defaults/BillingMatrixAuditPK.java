/*
 * BillingMatrixAuditPK.java Created on Aug 4, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;

/**
 * @author A-5255 
 * @version	0.1, Aug 4, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Aug 4, 2015	     A-5255		First draft
 */
@Embeddable
@KeyTable(table="MALMRABLGMTXAUDKEY", keyColumn="KEYTYP", valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN", table="MALMRABLGMTXAUDKEY", key="SERNUM")
public class BillingMatrixAuditPK implements Serializable{
	
	public BillingMatrixAuditPK(){
		
	}
	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * billingSiteCode
	 */
	private String billingMatrixCode;
	
	/**
	 * serialNumber
	 */
	private int serialNumber;
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column="CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the billingMatrixCode
	 */
	@KeyCondition(column="BLGMTXCOD")
	public String getBillingMatrixCode() {
		return billingMatrixCode;
	}

	/**
	 * @param billingMatrixCode the billingMatrixCode to set
	 */
	public void setBillingMatrixCode(String billingMatrixCode) {
		this.billingMatrixCode = billingMatrixCode;
	}

	/**
	 * @return the serialNumber
	 */
	@Key(generator="ID_GEN", startAt="1")
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


}
