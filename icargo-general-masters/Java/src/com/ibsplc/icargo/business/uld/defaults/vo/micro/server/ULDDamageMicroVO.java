/*
 * ULDDamageDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;




/**
 * @author A-1347
 *
 */
public class ULDDamageMicroVO extends AbstractVO {

	public static final String MODULE ="uld";
	public static final String SUBMODULE ="defaults";
	public static final String ENTITY ="uld.defaults.misc.ULDDamage";

    private String damageCode;
    private String position;
    private String severity;
    private String reportedStation;
    private String repairedBy;
    private String repairDate;
    private String remarks;
    private String damageReferenceNumber;
    private String sequenceNumber;
    private String operationFlag;
    private String lastUpdateUser;
    private boolean closed;
    private String reportedDate;
	/**
	 * @return Returns the closed.
	 */
	public boolean isClosed() {
		return closed;
	}
	/**
	 * @param closed The closed to set.
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	/**
	 * @return Returns the damageCode.
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * @return Returns the damageReferenceNumber.
	 */
	public String getDamageReferenceNumber() {
		return damageReferenceNumber;
	}
	/**
	 * @param damageReferenceNumber The damageReferenceNumber to set.
	 */
	public void setDamageReferenceNumber(String damageReferenceNumber) {
		this.damageReferenceNumber = damageReferenceNumber;
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the repairDate.
	 */
	public String getRepairDate() {
		return repairDate;
	}
	/**
	 * @param repairDate The repairDate to set.
	 */
	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}
	/**
	 * @return Returns the repairedBy.
	 */
	public String getRepairedBy() {
		return repairedBy;
	}
	/**
	 * @param repairedBy The repairedBy to set.
	 */
	public void setRepairedBy(String repairedBy) {
		this.repairedBy = repairedBy;
	}
	/**
	 * @return Returns the reportedDate.
	 */
	public String getReportedDate() {
		return reportedDate;
	}
	/**
	 * @param reportedDate The reportedDate to set.
	 */
	public void setReportedDate(String reportedDate) {
		this.reportedDate = reportedDate;
	}
	/**
	 * @return Returns the reportedStation.
	 */
	public String getReportedStation() {
		return reportedStation;
	}
	/**
	 * @param reportedStation The reportedStation to set.
	 */
	public void setReportedStation(String reportedStation) {
		this.reportedStation = reportedStation;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the severity.
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity The severity to set.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

   
}
