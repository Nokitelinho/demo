package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailRuleConfigVO extends AbstractVO {
	private String mailboxId;

	private Collection<MailRuleConfigParameterVO> mailRuleConfigParameters;
	
	private long messageConfigurationSequenceNumber;
	private String companyCode;
	private LocalDate fromDate;
	private LocalDate toDate;
	
	public String getMailboxId() {
		return mailboxId;
	}
	public void setMailboxId(String mailboxId) {
		this.mailboxId = mailboxId;
	}
		
	
	public Collection<MailRuleConfigParameterVO> getMailRuleConfigParameters() {
		return mailRuleConfigParameters;
	}
	public void setMailRuleConfigParameters(Collection<MailRuleConfigParameterVO> mailRuleConfigParameters) {
		this.mailRuleConfigParameters = mailRuleConfigParameters;
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
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	
	
	
}