package com.ibsplc.icargo.business.mail.mra.defaults;
/**
 * @author A-9498
 *
 */
import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

@SequenceKeyGenerator(name="ID_GEN",sequence="MALMRABLGSCHMST_SEQ")
@Embeddable
public class BillingScheduleMasterPK implements Serializable {
	private String companyCode;
	private String periodNumber;
	private long serialNumber;
	private String billingType;
	
	@KeyCondition(column = "CMPCOD")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	@Key(generator = "ID_GEN", startAt = "1" )
	public long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	
	@Override
    public boolean equals(Object other) {
        return (other != null) && (hashCode() == other.hashCode());
    }

    @Override
    public int hashCode() {
        return new StringBuffer(companyCode).append(periodNumber).append(billingType)
                .append(serialNumber).toString().hashCode();
    }
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(350);
		sbul.append("BillingScheduleMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', periodNumber '").append(this.periodNumber);
		sbul.append("', billingType '").append(this.billingType);
		sbul.append("', serialNumber '").append(this.serialNumber);
		sbul.append("' ]");
		return sbul.toString();
	}
}
