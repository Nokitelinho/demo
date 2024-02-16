package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

public class BillingParameterDetails {
	private String parameterName;
	private String parameterValue;
	private String includeExcludeFlag;
	private String parameterCode;
	
	
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getIncludeExcludeFlag() {
		return includeExcludeFlag;
	}
	public void setIncludeExcludeFlag(String includeExcludeFlag) {
		this.includeExcludeFlag = includeExcludeFlag;
	}
	

}
