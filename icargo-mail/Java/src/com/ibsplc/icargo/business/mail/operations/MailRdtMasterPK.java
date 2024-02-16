/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMasterPK.java
 *
 *	Created by	:	A-6991
 *	Created on	:	17-Jul-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMasterPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	17-Jul-2018	:	Draft
 */
@SequenceKeyGenerator(name="ID_GEN", sequence = "MALRDTREQDLVTIM_SEQ")
@Embeddable
public class MailRdtMasterPK implements Serializable{

	/**
	 * The CompanyCode
	 */
	
	private String companyCode;    
     
	/**
     * The SERNUM
     */
	private long SERNUM;
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@KeyCondition(column="CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * 	Getter for sERNUM 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Key(generator="ID_GEN", startAt="1")
	public long getSERNUM() {
		return SERNUM;
	}
	/**
	 *  @param sERNUM the sERNUM to set
	 * 	Setter for sERNUM 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setSERNUM(long sERNUM) {
		SERNUM = sERNUM;
	}
	
	@Override
	public String toString() {
		return "MailRdtMasterPK [companyCode=" + companyCode + ",  SERNUM=" + SERNUM + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (SERNUM ^ (SERNUM >>> 32));
		result = prime * result
				+ ((companyCode == null) ? 0 : companyCode.hashCode());
		
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailRdtMasterPK other = (MailRdtMasterPK) obj;
		if (SERNUM != other.SERNUM)
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		
		return true;
	}
}
