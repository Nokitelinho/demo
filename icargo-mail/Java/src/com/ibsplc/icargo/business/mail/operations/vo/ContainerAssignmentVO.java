/*
 * ContainerAssignmentVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 * Used to get details reg a container assignment.
 */
public class ContainerAssignmentVO extends AbstractVO {
    private String companyCode;
    private String airportCode;
    
    private String carrierCode;
    private int carrierId;
    private String flightNumber;    
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private LocalDate flightDate;
    private String destination;
    private String flightStatus;
    private int legSerialNumber;
    
    private String containerNumber;
    
    private String acceptanceFlag;
    
    private String pou;
    
    private String containerType;
    
    private String arrivalFlag;
    
    private String transferFlag;
    
    private String journeyID;
    
    private String remark;
 
    private String uldFulIndFlag;
    /**
     * ULD RELEASE FLAG
     * This flag is used to determine whether 
     * the uld is released from a segment.
     * 
     * ULD will be released at the time of 
     * Inbound Flight Closure.
     */
    private String releasedFlag;
    
    private String transactionCode;
    
    /**
     * Transit Flag
     */
    private String transitFlag;
    
    /**
     * shipperBuiltCode - Contains the Shipper Code(PA Code),
     * who build the SB ULD.
     */
    private String shipperBuiltCode;
    
    private String offloadStatus;

    private String poaFlag;
    private Measure actualWeight;
    private  LocalDate assignedDate; 
    private long uldReferenceNo;
    private String actWgtSta;

	/**
	 * @return the journeyID
	 */
	public String getJourneyID() {
		return journeyID;
	}

	/**
	 * @param journeyID the journeyID to set
	 */
	public void setJourneyID(String journeyID) {
		this.journeyID = journeyID;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

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
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the flightStatus.
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus The flightStatus to set.
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the legserailNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legserailNumber The legserailNumber to set.
	 */
	public void setLegSerialNumber(int legserailNumber) {
		this.legSerialNumber = legserailNumber;
	}

    /**
     * @return Returns the acceptanceFlag.
     */
    public String getAcceptanceFlag() {
        return acceptanceFlag;
    }

    /**
     * @param acceptanceFlag The acceptanceFlag to set.
     */
    public void setAcceptanceFlag(String acceptanceFlag) {
        this.acceptanceFlag = acceptanceFlag;
    }

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the arrivalStatus.
	 */
	public String getArrivalFlag() {
		return arrivalFlag;
	}

	/**
	 * @param arrivalStatus The arrivalStatus to set.
	 */
	public void setArrivalFlag(String arrivalStatus) {
		this.arrivalFlag = arrivalStatus;
	}

	/**
	 * @return Returns the transferFlag.
	 */
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param transferFlag The transferFlag to set.
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
	}

	/**
	 * @return the transitFlag
	 */
	public String getTransitFlag() {
		return transitFlag;
	}

	/**
	 * @param transitFlag the transitFlag to set
	 */
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReleasedFlag() {
		return releasedFlag;
	}

	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * @return the offloadStatus
	 */
	public String getOffloadStatus() {
		return offloadStatus;
	}

	/**
	 * @param offloadStatus the offloadStatus to set
	 */
	public void setOffloadStatus(String offloadStatus) {
		this.offloadStatus = offloadStatus;
	}
	/**
	 * 
	 * @return poaFlag
	 */
	public String getPoaFlag() {
		return poaFlag;
	}
	/**
	 * 
	 * @param poaFlag the poaFlag to set
	 */
	public void setPoaFlag(String poaFlag) {
		this.poaFlag = poaFlag;
	}

	public Measure getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Measure actualWeight) {
		this.actualWeight = actualWeight;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
   
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}
	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}

	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
    private Measure netWeight;
    private String unit;
    
	public Measure getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Measure netWeight) {
		this.netWeight = netWeight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	private String weightStatus;
	public String getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(String weightStatus) {
		this.weightStatus = weightStatus;
	}
	private String lastUpdatedUser;
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	private Boolean flagforIA;
	private Boolean flagforReweigh;
	public Boolean getFlagforIA() {
		return flagforIA;
	}
	public void setFlagforIA(Boolean flagforIA) {
		this.flagforIA = flagforIA;
	}
	public Boolean getFlagforReweigh() {
		return flagforReweigh;
	}
	public void setFlagforReweigh(Boolean flagforReweigh) {
		this.flagforReweigh = flagforReweigh;
	}
	private String lastUpdateUser;

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
}
