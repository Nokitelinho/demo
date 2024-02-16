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
@KeyTable(table="MALMRAMCAAUDKEY", keyColumn="KEYTYP", valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="ID_GEN", table="MALMRAMCAAUDKEY", key="SERNUM")
public class MCADetailsAuditPK implements Serializable {
	

	/**
	 * companyCode
	 */
	private String companyCode;
	private long mailSequenecNumber;
	/**
	 * billingSiteCode
	 */
	private String mcaRefNumber;
	/**
	 * serialNumber
	 */
	//private String sequenceNumber;
	
	private int serialNumber;
	
	
	/**
	 * 	Getter for serialNumber 
	 *	Added by : a-8061 on 10-Aug-2018
	 * 	Used for :
	 */
	@Key(generator="ID_GEN", startAt="1")
	public int getSerialNumber() {
		return serialNumber;
	}


	/**
	 *  @param serialNumber the serialNumber to set
	 * 	Setter for serialNumber 
	 *	Added by : a-8061 on 10-Aug-2018
	 * 	Used for :
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	public MCADetailsAuditPK(){
		
	}
	
	
	/**
	 * @param companyCode
	 * @param mailSequenecNumber
	 * @param mcaRefNumber
	 * @param sequenceNumber
	 */
	public MCADetailsAuditPK(String companyCode, long mailSequenecNumber,
			String mcaRefNumber, String sequenceNumber) {
		super();
		this.companyCode = companyCode;
		this.mailSequenecNumber = mailSequenecNumber;
		this.mcaRefNumber = mcaRefNumber;
		
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
		return new StringBuffer(companyCode).append(mcaRefNumber).append(serialNumber).append(mailSequenecNumber).toString().hashCode();

	}
	/**
	 * @return the companyCode
	 */
	@KeyCondition(column = "CMPCOD")
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
	 * @return the mailSequenecNumber
	 */
	@KeyCondition(column = "MALSEQNUM")
	public long getMailSequenecNumber() {
		return mailSequenecNumber;
	}
	/**
	 * @param mailSequenecNumber the mailSequenecNumber to set
	 */
	public void setMailSequenecNumber(long mailSequenecNumber) {
		this.mailSequenecNumber = mailSequenecNumber;
	}
	/**
	 * @return the mcaRefNumber
	 */
	@KeyCondition(column = "MCAREFNUM")
	public String getMcaRefNumber() {
		return mcaRefNumber;
	}
	/**
	 * @param mcaRefNumber the mcaRefNumber to set
	 */
	public void setMcaRefNumber(String mcaRefNumber) {
		this.mcaRefNumber = mcaRefNumber;
	}
	
/*	*//**
	 * @return the sequenceNumber
	 *//*
	@Key(generator="ID_GEN", startAt="1")
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	*//**
	 * @param sequenceNumber the sequenceNumber to set
	 *//*
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}*/
	
	
}
