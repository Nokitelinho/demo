package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

public class MailScreeningFilter {

	private String mailBagId;
	private String companyCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

}
