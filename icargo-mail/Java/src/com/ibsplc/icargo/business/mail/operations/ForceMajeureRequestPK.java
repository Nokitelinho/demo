/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * @author A-5219
 *
 */
@SequenceKeyGenerator(name="ID_GEN",sequence="MALFORMJRREQMST_SEQ")
@Embeddable
public class ForceMajeureRequestPK implements Serializable {
	
	private String companyCode;
	
	private String forceMajuereID;
	
	private long sequenceNumber;

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
	 * @return the forceMajuereID
	 */
	@KeyCondition(column = "FORMJRIDR")
	public String getForceMajuereID() {
		return forceMajuereID;
	}

	/**
	 * @param forceMajuereID the forceMajuereID to set
	 */
	public void setForceMajuereID(String forceMajuereID) {
		this.forceMajuereID = forceMajuereID;
	}

	/**
	 * @return the sequenceNumber
	 */
	@Key(generator = "ID_GEN", startAt = "1" )
	public long getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	/**
	 * 
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	
	/**
	 * 
	 */
	public int hashCode() {

		return new StringBuffer(companyCode).
				
				append(forceMajuereID).
				append(sequenceNumber).
				toString().hashCode();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("ForceMajeureRequestPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("forceMajuereID '").append(this.forceMajuereID);
		sbul.append("sequenceNumber '").append(this.sequenceNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}
