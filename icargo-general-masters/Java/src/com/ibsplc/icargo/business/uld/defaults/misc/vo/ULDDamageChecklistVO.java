/*
 * ULDDamageChecklistVO.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3459
 *
 */
public class ULDDamageChecklistVO extends AbstractVO implements Serializable {
	
	private String companyCode;
	private String sequenceNumber;
	private String section;
	//Added by A-5374
	private String sectionDescription;
	private String operationFlag;
	
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String description;
	private int noOfPoints;
	private int totalPoints;
	
	private boolean checkInOut;

	/**
	 * 
	 * @return
	 */
	public String getSectionDescription() {
		return sectionDescription;
	}
	/**
	 * 
	 * @param sectionDescription
	 */
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
	/**
	 * @return the checkInOut
	 */
	public boolean isCheckInOut() {
		return checkInOut;
	}
	/**
	 * @param checkInOut the checkInOut to set
	 */
	public void setCheckInOut(boolean checkInOut) {
		this.checkInOut = checkInOut;
	}
	/**
	 * @return the totalPoints
	 */
	public int getTotalPoints() {
		return totalPoints;
	}
	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the noOfPoints
	 */
	public int getNoOfPoints() {
		return noOfPoints;
	}
	/**
	 * @param noOfPoints the noOfPoints to set
	 */
	public void setNoOfPoints(int noOfPoints) {
		this.noOfPoints = noOfPoints;
	}
	

}
