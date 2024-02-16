/*
 * GPAContractPK.java Created on JUL 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
/**
 * @author A-6986
 *
 */
@SequenceKeyGenerator(name="ID_GEN", sequence="MALGPACTR_SEQ")
@Embeddable
public class GPAContractPK implements Serializable{
	
	private String companyCode;
	
	private String gpaCode;
	
	//code commented and added by A-8527 for BUG ICRD-308683
	//private String contractID;
	 private int sernum;

	/**
	 * @return the companyCode
	 */
	 public GPAContractPK(){
	 }
	 public GPAContractPK (String companyCode,String gpaCode,int sernum){
		 this.companyCode=companyCode;
		 this.gpaCode = gpaCode;
		 this.sernum = sernum;
	 }
	 @Key(generator = "ID_GEN", startAt = "1" )
		public int getSernum() {
			return sernum;
		}
		public void setSernum(int sernum) {
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

	/**
	 * @return the contractID
	 */
	//code commented and added by A-8527 for BUG ICRD-308683
	/*public String getContractID() {
		return contractID;
	}*/

	/**
	 * @param contractID the contractID to set
	 */
	/*public void setContractID(String contractID) {
		this.contractID = contractID;
	}*/
	
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
				//code commented and added by A-8527 for BUG ICRD-308683
				//append(contractID).
				append(sernum).
				toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(106);
		sbul.append("GPAContractPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', gpacod '").append(this.gpaCode);
		//code commented and added by A-8527 for BUG ICRD-308683
		//sbul.append("', contractId '").append(this.contractID);
		sbul.append("', sernum '").append(this.sernum);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	

}
