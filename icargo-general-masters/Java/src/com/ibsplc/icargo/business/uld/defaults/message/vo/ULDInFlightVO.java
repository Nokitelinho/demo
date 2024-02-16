/*
 * ULDInFlightVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDInFlightVO extends AbstractVO{

	private String uldNumber;
    private String pointOfLading;
    private String pointOfUnLading;
    private String content;
    private String remark;
    private int uldOwnerId;
//This flag indicates whether the ULD is assigned to any other flight
    //So that the ULD need not be made INSTATION on arrival close.
	private boolean isULDAssigned;
//	This flag indicates whether the ULD is already recived in a station and mvt need not be marked
	/** The received flag. */
private boolean receivedFlag;
    //added by a-3045 for CR AirNZ267 starts
    private String agentCode;
    private String agentName;
    private boolean baseflag;
    //added by a-3045 for CR AirNZ267 ends
    //Added By Asharaf Binu fon 06Apr08 for capturing the location to which the uld should be moved during acceptance or buildup
    private String location;
    //added by T-1927 for the ICRD-36917
    private String companyCode;
    private String flightNumber;
    private long flightSequenceNumber;
    private int legSerialNumber;
    private String pou;
    private String closedFlag;
    private String facilityType;
    private boolean isThruOrIntact; 
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
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return the closedFlag
	 */
	public String getClosedFlag() {
		return closedFlag;
	}
	/**
	 * @param closedFlag the closedFlag to set
	 */
	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
	}
	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	 * @return Returns the uldOwnerId.
	 */
	public int getUldOwnerId() {
		return this.uldOwnerId;
	}
	/**
	 * @param uldOwnerId The uldOwnerId to set.
	 */
	public void setUldOwnerId(int uldOwnerId) {
		this.uldOwnerId = uldOwnerId;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isBaseflag() {
		return baseflag;
	}
	public void setBaseflag(boolean baseflag) {
		this.baseflag = baseflag;
	}
	/**
	 * @return the isULDAssigned
	 */
	public boolean isULDAssigned() {
		return isULDAssigned;
	}

	/**
	 * @return the receivedFlag
	 */
	public boolean isReceivedFlag() {
		return receivedFlag;
	}
	/**
	 * @param receivedFlag the receivedFlag to set
	 */
	public void setReceivedFlag(boolean receivedFlag) {
		this.receivedFlag = receivedFlag;
	}

	/**
	 * @param isULDAssigned the isULDAssigned to set
	 */
	public void setULDAssigned(boolean isULDAssigned) {
		this.isULDAssigned = isULDAssigned;
	}
	
	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return the isThruOrIntact
	 */
	public boolean isThruOrIntact() {
		return isThruOrIntact;
	}
	/**
	 * @param isThruOrIntact the isThruOrIntact to set
	 */
	public void setThruOrIntact(boolean isThruOrIntact) {
		this.isThruOrIntact = isThruOrIntact;
	}

}
