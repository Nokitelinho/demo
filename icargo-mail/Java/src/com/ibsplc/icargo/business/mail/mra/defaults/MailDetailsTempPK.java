/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.MailDetailsTempPK.java
 *
 *	Created by	:	A-7531
 *	Created on	:	30-Nov-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;
import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.MailDetailsTempPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	30-Nov-2018	:	Draft
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRAINT_SEQ")
@Embeddable
public class MailDetailsTempPK implements Serializable{
	
	
	private String companyCode;
	
	private int seqNum;
	
	
	
	
	
	
	  public boolean equals(Object other) {
			return (other != null) && ((hashCode() == other.hashCode()));
		}
	    /**
	     * @return
	     */
		public int hashCode() {

			return new StringBuffer(companyCode).
					append(seqNum).
					toString().hashCode();
		}

		
		
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 	Getter for seqNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	@Key(generator = "ID_GEN", startAt = "1" )
	public int getSeqNum() {
		return seqNum;
	}

	/**
	 *  @param seqNum the seqNum to set
	 * 	Setter for seqNum 
	 *	Added by : A-7531 on 03-Dec-2018
	 * 	Used for :
	 */
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("MailDetailsTempPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("seqNum '").append(this.seqNum);
		sbul.append("' ]");
		return sbul.toString();
	}
}
