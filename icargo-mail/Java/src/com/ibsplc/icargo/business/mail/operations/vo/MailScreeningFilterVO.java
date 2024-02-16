package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailScreeningFilterVO extends AbstractVO {

	private String mailBagId;
	private String companyCode;
    private boolean warningFlag;
	
	public boolean isWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(boolean warningFlag) {
		this.warningFlag = warningFlag;
	}

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
