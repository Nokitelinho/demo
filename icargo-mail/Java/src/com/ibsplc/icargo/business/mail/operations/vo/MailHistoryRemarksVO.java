package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailHistoryRemarksVO extends AbstractVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private long mailSequenceNumber;
	private long remarkSerialNumber;
	private String remark;
	private LocalDate remarkDate;
	private String userName;
	
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public long getRemarkSerialNumber() {
		return remarkSerialNumber;
	}
	public void setRemarkSerialNumber(long remarkSerialNumber) {
		this.remarkSerialNumber = remarkSerialNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public LocalDate getRemarkDate() {
		return remarkDate;
	}
	public void setRemarkDate(LocalDate remarkDate) {
		this.remarkDate =  remarkDate;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
