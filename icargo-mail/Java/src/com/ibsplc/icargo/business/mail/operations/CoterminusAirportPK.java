
package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;


@SequenceKeyGenerator(name="ID_GEN", sequence = "MALCTSARP_SEQ")
@Embeddable
public class CoterminusAirportPK implements Serializable {
	
	/**
	 * The CompanyCode
	 */
	
	private String companyCode;    
    /**
     * The GPACode
     */
	private String gpaCode;  
	/**
     * The SERNUM
     */
	private long SERNUM;
    
	@KeyCondition(column="CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	@Key(generator="ID_GEN", startAt="1")
	public long getSERNUM() {
		return SERNUM;
	}
	public void setSERNUM(long sERNUM) {
		SERNUM = sERNUM;
	}
	
	@Override
	public String toString() {
		return "CoterminusAirportPK [companyCode=" + companyCode + ", gpaCode="
				+ gpaCode + ", SERNUM=" + SERNUM + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (SERNUM ^ (SERNUM >>> 32));
		result = prime * result
				+ ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((gpaCode == null) ? 0 : gpaCode.hashCode());
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
		CoterminusAirportPK other = (CoterminusAirportPK) obj;
		if (SERNUM != other.SERNUM)
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (gpaCode == null) {
			if (other.gpaCode != null)
				return false;
		} else if (!gpaCode.equals(other.gpaCode))
			return false;
		return true;
	}
	
 
}
