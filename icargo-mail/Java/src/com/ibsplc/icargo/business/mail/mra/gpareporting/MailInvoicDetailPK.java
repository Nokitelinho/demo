/*
 * MailInvoicDetailPK.java Created on Nov 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;



/**
 * @author A-8464
 *
 */
@KeyTable(table="MALMRAINVCDTLKEY",keyColumn="KEYTYP",valueColumn="MAXSEQNUM")
@TableKeyGenerator(name="KEY_GEN",table="MALMRAINVCDTLKEY",key="MALMRAINVCDTLKEY_SEQ")
@Embeddable
public class MailInvoicDetailPK implements Serializable{

    private String companyCode;
    private long mailSequenceNumber;


	/**
	 * Default constructor
	 *
	 */
    public MailInvoicDetailPK(){

    }

	/**
	 *
	 * @param companyCode
	 * @param poaCode
	 * @param mailSequenceNumber
	 */
	public MailInvoicDetailPK(String companyCode, long mailSequenceNumber) {
		this.companyCode = companyCode;
		this.mailSequenceNumber = mailSequenceNumber;
	}

    public boolean equals(Object other) {
    	boolean isEqual = false;
		if(other != null){
			isEqual = this.hashCode() == other.hashCode();
		}
		return isEqual;
    }

	public int hashCode() {
		return this.toString().hashCode();
	}



	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}

	@KeyCondition(column =  "CMPCOD")
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}





	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	@KeyCondition(column = "MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}




	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(199);
		sbul.append("MailInvoicDetailPK [ ");
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', mailSequenceNumber '").append(this.mailSequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
