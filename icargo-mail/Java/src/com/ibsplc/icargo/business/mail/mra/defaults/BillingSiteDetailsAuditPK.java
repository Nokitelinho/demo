/*
 * BillingSiteAuditPK.java Created on Nov 27, 2013
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
 * @author a-5219
 *
 */
@Embeddable
@KeyTable(table="SHRBLGSITAUDKEY", keyColumn="KEYTYP", valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN", table="SHRBLGSITAUDKEY", key="MTKMRABLGSITAUDKEY")
public class BillingSiteDetailsAuditPK implements Serializable {
	

	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * billingSiteCode
	 */
	private String billingSiteCode;
	
	/**
	 * serialNumber
	 */
	private String serialNumber;
	
	public BillingSiteDetailsAuditPK(){
		
	}
	private BillingSiteDetailsAuditPK(String companyCode, String billingSiteCode,
			String serialNumber) {
		this.companyCode = companyCode;
		this.billingSiteCode = billingSiteCode;
		this.serialNumber=serialNumber;
	}
	
	public boolean equals(Object obj) {
		if (obj != null) {
			return (hashCode() == obj.hashCode());
		}
		return false;
	}

	/**
	 * @return int
	 */

	public int hashCode() {
		return new StringBuffer(companyCode).append(billingSiteCode).append(serialNumber).toString().hashCode();

	}
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
	 * @return the billingSiteCode
	 */
	@KeyCondition(column="BLGSITCOD")
	public String getBillingSiteCode() {
		return billingSiteCode;
	}
	/**
	 * @param billingSiteCode the billingSiteCode to set
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	/**
	 * @return the serialNumber
	 */
	@Key(generator="ID_GEN", startAt="1")
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
