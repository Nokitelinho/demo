package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailRuleConfigParameterVO extends AbstractVO {
	private String parameterValue;
	private String parameterCode;
	private long messageConfigurationSequenceNumber;
	private String companyCode;
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	
	public long getMessageConfigurationSequenceNumber() {
		return messageConfigurationSequenceNumber;
	}
	public void setMessageConfigurationSequenceNumber(long messageConfigurationSequenceNumber) {
		this.messageConfigurationSequenceNumber = messageConfigurationSequenceNumber;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
	
	
	
}