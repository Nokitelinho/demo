package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;
import javax.persistence.Embeddable;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;


@SequenceKeyGenerator(name="ID_GEN",sequence="MALHISNOT_SEQ")
@Embeddable
public class MailHistoryRemarksPK implements Serializable{
	
	
	private long serialNumber;
	private String companyCode;
	
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
	@Key(generator = "ID_GEN", startAt = "1" )
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(205);
		sbul.append("MailHistoryRemarksPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("' ]");
		return sbul.toString();
	}
	
	public int hashCode() {
		return new StringBuffer(companyCode).append(serialNumber).toString().hashCode();
	}
	
	public boolean equals(Object other) {
		return (other != null) && (hashCode() == other.hashCode());
	}
}
