package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailEvent;

public class MailboxIdModel extends AbstractScreenModel{

	private String mailboxId;
	private String mailboxName;
	private String ownerCode;
	private int resditTriggerPeriod;
	private boolean partialResdit; //listing resdits
	private boolean msgEventLocationNeeded; // Specifying location in Resdits
	private String messagingEnabled;
	//private Collection<OneTimeVO> MailboxOwner;
	private Collection<OneTimeVO> ResditVersion;
	private Map<String, Collection<OneTimeVO>> oneTimeValues;
	private String resditversion;
	private String mailboxOwner;
	private String mailboxStatus;
	private String operationalFlag;
	Collection<MailEvent> mailEvents ;
	private String status;
	private String operationFlag;
	private String AutoEmailReqd;
	private LocalDate lastUpdateTime;
    private String lastUpdateUser;
    private String remarks;
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mail.operations.ux.mailboxIDMaster";
	
	public String getOperationalFlag() {
		return operationalFlag;
	}
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	public String getMailboxId() {
		return mailboxId;
	}
	public void setMailboxId(String mailboxID) {
		this.mailboxId = mailboxID;
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
	public Collection<OneTimeVO> getResditVersion() {
		return ResditVersion;
	}
	public void setResditVersion(Collection<OneTimeVO> resditVersion) {
		ResditVersion = resditVersion;
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
	@Override
	public String getProduct() {
		return PRODUCT;
	}
	@Override
	public String getScreenId() {
		return SUBPRODUCT;
	}
	@Override
	public String getSubProduct() {
		return SCREENID;
	}
	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}
	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}
	public String getResditversion() {
		return resditversion;
	}
	public void setResditversion(String resditversion) {
		this.resditversion = resditversion;
	}
	public Collection<MailEvent> getMailEvents() {
		return mailEvents;
	}
	public void setMailEvents(Collection<MailEvent> mailEvents) {
		this.mailEvents = mailEvents;
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
		return AutoEmailReqd;
	}
	public void setAutoEmailReqd(String autoEmailReqd) {
		AutoEmailReqd = autoEmailReqd;
	}
	public String getMailboxStatus() {
		return mailboxStatus;
	}
	public void setMailboxStatus(String mailboxStatus) {
		this.mailboxStatus = mailboxStatus;
	}
	public String getMailboxOwner() {
		return mailboxOwner;
	}
	public void setMailboxOwner(String mailboxOwner) {
		this.mailboxOwner = mailboxOwner;
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