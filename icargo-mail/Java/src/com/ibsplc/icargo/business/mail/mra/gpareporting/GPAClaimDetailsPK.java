/**
 * GPAClaimMasterPK.java Created on March 15, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;

import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
/**
 * @author A-8527
 *
 */
@SequenceKeyGenerator(name="ID_GEN", sequence="MALMRAGPACLMDTL_SEQ")
@Embeddable
public class GPAClaimDetailsPK implements Serializable{
	
	private String companyCode;
	
	private String gpaCode;
	
	private long sernum;

	private int seqNumber;
	private long mailSeqNumber;
	/**
	 * @return the companyCode
	 */
	 public GPAClaimDetailsPK(){
	 }
	 public GPAClaimDetailsPK (String companyCode,String gpaCode,long sernum,int seqNumber, long mailSeqNumber){
		 this.companyCode=companyCode;
		 this.gpaCode = gpaCode;
		 this.sernum = sernum;
		 this.seqNumber = seqNumber;
		 this.mailSeqNumber = mailSeqNumber;
	 }
	 @Key(generator = "ID_GEN", startAt = "1" )
		public long getSernum() {
			return sernum;
		}
		public void setSernum(long sernum) {
			this.sernum = sernum;
		}
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
	 * @return the gpaCode
	 */
	
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	
	public int getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}
	/**
     *@param other
     *@return
     * 
     */
    public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
  /**
   * @return
   */
	public int hashCode() {

		return new StringBuffer(companyCode).
				append(gpaCode).
				append(sernum).
				toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("GPAClaimDetailsPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpacod '").append(this.gpaCode);
		sbul.append("', sernum '").append(this.sernum);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	

}
