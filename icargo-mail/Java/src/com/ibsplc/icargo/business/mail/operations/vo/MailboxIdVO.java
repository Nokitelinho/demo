package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailboxIdVO extends AbstractVO {

	private String mailboxID;
	private String mailboxName;
	private String ownerCode;
	private int resditTriggerPeriod;
	private boolean partialResdit; 
	private boolean msgEventLocationNeeded; 
	private String messagingEnabled;
	private String residtversion;
	private String mailboxOwner;
	private String resditversion;
	private String companyCode;
	private String mailboxStatus;
	private Collection<MailEventVO> mailEventVOs;
	private LocalDate lastUpdateTime;
    private String lastUpdateUser;
	private String status;
	private String operationFlag;
	private String autoEmailReqd;
	private String remarks;
	
	public String getResditversion() {
		return resditversion;
	}
	public void setResditversion(String resditversion) {
		this.resditversion = resditversion;
	}
	public String getMailboxID() {
		return mailboxID;
	}
	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
	public String getMailboxName() {
		return mailboxName;
	}
	public void setMailboxName(String mailboxName) {
		this.mailboxName = mailboxName;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	public int getResditTriggerPeriod() {
		return resditTriggerPeriod;
	}
	public void setResditTriggerPeriod(int resditTriggerPeriod) {
		this.resditTriggerPeriod = resditTriggerPeriod;
	}
	public boolean isPartialResdit() {
		return partialResdit;
	}
	public void setPartialResdit(boolean partialResdit) {
		this.partialResdit = partialResdit;
	}
	public boolean isMsgEventLocationNeeded() {
		return msgEventLocationNeeded;
	}
	public void setMsgEventLocationNeeded(boolean msgEventLocationNeeded) {
		this.msgEventLocationNeeded = msgEventLocationNeeded;
	}
	public String getMessagingEnabled() {
		return messagingEnabled;
	}
	public void setMessagingEnabled(String messagingEnabled) {
		this.messagingEnabled = messagingEnabled;
	}
	public String getResidtversion() {
		return residtversion;
	}
	public void setResidtversion(String residtversion) {
		this.residtversion = residtversion;
	}
	public String getMailboxOwner() {
		return mailboxOwner;
	}
	public void setMailboxOwner(String mailboxOwner) {
		this.mailboxOwner = mailboxOwner;
	}
	public Collection<MailEventVO> getMailEventVOs() {
		return mailEventVOs;
	}
	public void setMailEventVOs(Collection<MailEventVO> mailEventVOs) {
		this.mailEventVOs = mailEventVOs;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getAutoEmailReqd() {
		return autoEmailReqd;
	}
	public void setAutoEmailReqd(String autoEmailReqd) {
		this.autoEmailReqd = autoEmailReqd;
	}
	public String getMailboxStatus() {
		return mailboxStatus;
	}
	public void setMailboxStatus(String mailboxStatus) {
		this.mailboxStatus = mailboxStatus;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
