package com.ibsplc.icargo.business.reco.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
@SequenceKeyGenerator(name="ID_GEN",sequence="RECMSG_SEQ")
@Embeddable
public class RegulatoryComposeMessagePK implements Serializable {

	private int serialNumber;
	private String companyCode;
	
	public RegulatoryComposeMessagePK() {

	}
	
	public RegulatoryComposeMessagePK(String companyCode,int serialNumber) {
		this.companyCode = companyCode;
		this.serialNumber = serialNumber;
	}
	
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumber() {
		return serialNumber;
	}
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	
	public int hashCode() {
		return new StringBuffer(companyCode).append(serialNumber).toString().hashCode();
	}

	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:12 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(73);
		sbul.append("RegulatoryComposeMessagePK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
	
}
