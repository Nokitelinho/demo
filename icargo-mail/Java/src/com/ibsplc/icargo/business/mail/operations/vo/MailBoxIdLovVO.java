package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author a-5991
 *
 */
public class MailBoxIdLovVO extends AbstractVO {

	private String companyCode;
	private String mailboxCode;
	private String mailboxDescription;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMailboxCode() {
		return mailboxCode;
	}

	public void setMailboxCode(String mailboxCode) {
		this.mailboxCode = mailboxCode;
	}

	public String getMailboxDescription() {
		return mailboxDescription;
	}

	public void setMailboxDescription(String mailboxDescription) {
		this.mailboxDescription = mailboxDescription;
	}

}
