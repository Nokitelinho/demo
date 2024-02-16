package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import java.io.Serializable;
import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
/**
 * 
 * @author A-5526
 *
 */
@SequenceKeyGenerator(name = "ID_GEN", sequence = "MALMLDMST_SEQ")
@Embeddable
public class MLDMessageMasterPK implements Serializable {

	private String companyCode;
	private int serialNumber;

	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null) {
			isEqual = hashCode() == other.hashCode();
		}
		return isEqual;
	}

	public int hashCode() {
		return (this.companyCode + this.serialNumber).hashCode();
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("MLDMessageMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}