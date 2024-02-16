package com.ibsplc.icargo.business.mail.mra.defaults;
/**
 * @author A-9498
 *
 */
import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class BillingParamterMasterPK implements Serializable {
	private String companyCode;
	private String functionPoint;
	private String functionName;
	private Long serialNumber;
	private String parameterCode;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getFunctionPoint() {
		return functionPoint;
	}
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public Long getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	@Override
    public boolean equals(Object other) {
        return (other != null) && (hashCode() == other.hashCode());
    }

    @Override
    public int hashCode() {
        return new StringBuffer(companyCode).append(functionPoint).append(functionName)
                .append(serialNumber).toString().hashCode();
    }
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(350);
		sbul.append("BillingParamterMasterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', functionPoint '").append(this.functionPoint);
		sbul.append("', functionName '").append(this.functionName);
		sbul.append("', serialNumber '").append(
				this.serialNumber);
		sbul.append("', parameterCode '").append(this.parameterCode);
		sbul.append("' ]");
		return sbul.toString();
	}

}
