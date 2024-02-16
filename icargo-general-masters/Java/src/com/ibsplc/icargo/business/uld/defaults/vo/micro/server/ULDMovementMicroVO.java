/*
 * ULDMovementVO.java Created on Dec 21, 2005
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
public class ULDMovementMicroVO extends AbstractVO{

	private String carrierCode;
    private String companyCode;
    private String uldNumber;
    private int flightCarrierIdentifier;
    private String flightNumber;
    private String flightDate;
    private String flightDateString;
    private String pointOfLading;
    private String pointOfUnLading;
    private String content;
    private boolean dummyMovement;
    private String remark;
    private long movementSequenceNumber;
    private  boolean updateCurrentStation;
    private  String currentStation;

	private String lastUpdatedTime;
	private String lastUpdatedUser;
	private boolean discrepancyToBeSolved;
	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the currentStation.
	 */
	public String getCurrentStation() {
		return currentStation;
	}
	/**
	 * @param currentStation The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}
	/**
	 * @return Returns the discrepancyToBeSolved.
	 */
	public boolean isDiscrepancyToBeSolved() {
		return discrepancyToBeSolved;
	}
	/**
	 * @param discrepancyToBeSolved The discrepancyToBeSolved to set.
	 */
	public void setDiscrepancyToBeSolved(boolean discrepancyToBeSolved) {
		this.discrepancyToBeSolved = discrepancyToBeSolved;
	}
	/**
	 * @return Returns the dummyMovement.
	 */
	public boolean isDummyMovement() {
		return dummyMovement;
	}
	/**
	 * @param dummyMovement The dummyMovement to set.
	 */
	public void setDummyMovement(boolean dummyMovement) {
		this.dummyMovement = dummyMovement;
	}
	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the flightDateString.
	 */
	public String getFlightDateString() {
		return flightDateString;
	}
	/**
	 * @param flightDateString The flightDateString to set.
	 */
	public void setFlightDateString(String flightDateString) {
		this.flightDateString = flightDateString;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the movementSequenceNumber.
	 */
	public long getMovementSequenceNumber() {
		return movementSequenceNumber;
	}
	/**
	 * @param movementSequenceNumber The movementSequenceNumber to set.
	 */
	public void setMovementSequenceNumber(long movementSequenceNumber) {
		this.movementSequenceNumber = movementSequenceNumber;
	}
	/**
	 * @return Returns the pointOfLading.
	 */
	public String getPointOfLading() {
		return pointOfLading;
	}
	/**
	 * @param pointOfLading The pointOfLading to set.
	 */
	public void setPointOfLading(String pointOfLading) {
		this.pointOfLading = pointOfLading;
	}
	/**
	 * @return Returns the pointOfUnLading.
	 */
	public String getPointOfUnLading() {
		return pointOfUnLading;
	}
	/**
	 * @param pointOfUnLading The pointOfUnLading to set.
	 */
	public void setPointOfUnLading(String pointOfUnLading) {
		this.pointOfUnLading = pointOfUnLading;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the updateCurrentStation.
	 */
	public boolean isUpdateCurrentStation() {
		return updateCurrentStation;
	}
	/**
	 * @param updateCurrentStation The updateCurrentStation to set.
	 */
	public void setUpdateCurrentStation(boolean updateCurrentStation) {
		this.updateCurrentStation = updateCurrentStation;
	}



}
