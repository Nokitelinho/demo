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
//@SequenceKeyGenerator(name="ID_GEN", sequence="MALMRAGPACLM_SEQ")
@Embeddable
public class GPAClaimMasterPK implements Serializable{
	
	private String companyCode;
	
	private String gpaCode;
	
	private String claimReferenceNumber;

	/**
	 * @return the companyCode
	 */
	 public GPAClaimMasterPK(){
	 }
	 public GPAClaimMasterPK (String companyCode,String gpaCode,String claimReferenceNumber){
		 this.companyCode=companyCode;
		 this.gpaCode = gpaCode;
		 this.claimReferenceNumber = claimReferenceNumber;
	 }


	//@KeyCondition(column = "CMPCOD")
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
				append(claimReferenceNumber).
				toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("GPAClaimMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpacod '").append(this.gpaCode);
		sbul.append("', claimReferenceNumber '").append(this.claimReferenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
	/**
	 * 	Getter for claimReferenceNumber 
	 *	Added by : A-8061 on 27-Jun-2019
	 * 	Used for :
	 */
	public String getClaimReferenceNumber() {
		return claimReferenceNumber;
	}
	/**
	 *  @param claimReferenceNumber the claimReferenceNumber to set
	 * 	Setter for claimReferenceNumber 
	 *	Added by : A-8061 on 27-Jun-2019
	 * 	Used for :
	 */
	public void setClaimReferenceNumber(String claimReferenceNumber) {
		this.claimReferenceNumber = claimReferenceNumber;
	}
	
	

}
