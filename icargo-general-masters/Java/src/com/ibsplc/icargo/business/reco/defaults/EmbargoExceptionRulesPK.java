package com.ibsplc.icargo.business.reco.defaults;

import java.io.Serializable;
import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;
/**
 * @author A-6843
 *
 */
@Embeddable
@SequenceKeyGenerator(name="ID_GEN",sequence="RECEXPMST_SEQ")
public class EmbargoExceptionRulesPK implements Serializable{
	
	public EmbargoExceptionRulesPK() {

	}
	

	private String companyCode;
	private int serialNumbers;
	
	
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
	@Key(generator = "ID_GEN", startAt = "1")
	public int getSerialNumbers() {
		return serialNumbers;
	}
	public void setSerialNumbers(int serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	
	public int hashCode() {
		return new StringBuffer(companyCode).append(serialNumbers).toString().hashCode();
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(73);
		sbul.append("EmbargoExceptionRulesPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("serialNumbers '").append(this.serialNumbers);
		sbul.append("' ]");
		return sbul.toString();
	}
	
}
