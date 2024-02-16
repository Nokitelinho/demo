package com.ibsplc.neoicargo.framework.icgsupport;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.neoicargo.framework.icgsupport.utils.audit.AuditVO;

@MappedSuperclass
public abstract class AbstractAudit {
	private String additionalInfo;
	private String actionCode;
	private String auditRemarks;
	private String updateUser;
	private LocalDateTime updateTxnTime;
	private LocalDateTime updateTxnTimeUTC;
	private String stationCode;
	private String triggerPoint;

	@Column(name = "ADLINF")
	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Column(name = "ACTCOD")
	public String getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Column(name = "AUDRMK")
	public String getAuditRemarks() {
		return this.auditRemarks;
	}

	public void setAuditRemarks(String auditRemarks) {
		this.auditRemarks = auditRemarks;
	}

	@Column(name = "UPDUSR")
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "UPDTXNTIM")
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	public LocalDateTime getUpdateTxnTime() {
		return this.updateTxnTime;
	}

	public void setUpdateTxnTime(LocalDateTime updateTxnTime) {
		this.updateTxnTime = updateTxnTime;
	}

	@Column(name = "UPDTXNTIMUTC")
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	public LocalDateTime getUpdateTxnTimeUTC() {
		return this.updateTxnTimeUTC;
	}

	public void setUpdateTxnTimeUTC(LocalDateTime updateTxnTimeUTC) {
		this.updateTxnTimeUTC = updateTxnTimeUTC;
	}

	@Column(name = "STNCOD")
	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	@Column(name = "TRGPNT")
	public String getTriggerPoint() {
		return this.triggerPoint;
	}

	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}

	protected void populateGenericAttributes(AuditVO auditVO) {
		this.actionCode = auditVO.getActionCode();
		this.additionalInfo = auditVO.getAdditionalInformation();
		this.auditRemarks = auditVO.getAuditRemarks();
		this.stationCode = auditVO.getStationCode();
		this.updateTxnTime = auditVO.getTxnLocalTime();
		this.updateTxnTimeUTC = auditVO.getTxnTime();
		this.updateUser = auditVO.getUserId();
		this.triggerPoint = auditVO.getAuditTriggerPoint();
	}
}
