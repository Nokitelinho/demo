/*
 * FlightLoanPlanContainerVO.java Created on JUL 01, 2022
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3429
 *
 */
public class FlightLoadPlanContainerVO extends AbstractVO{

	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private String segOrigin;
	private String segDestination;
	private int loadPlanVersion;
	private long uldReferenceNo;
	private String containerType; 
	private int segSerialNumber;
	private int plannedPieces;
	private double plannedWeight;
	private String operationFlag;
	private String planStatus;
	private String uldNumber;
	private String plannedPosition;
	private double plannedVolume;
	private String lastUpdatedUser;
	public static final String OPERATION_FLAG_INSERT = "I";

	public static final String OPERATION_FLAG_DELETE = "D";

	public static final String OPERATION_FLAG_UPDATE = "U";
	private String uldFullIndicator;
	private String subClassGroup;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public String getSegOrigin() {
		return segOrigin;
	}

	public void setSegOrigin(String segOrigin) {
		this.segOrigin = segOrigin;
	}

	public String getSegDestination() {
		return segDestination;
	}

	public void setSegDestination(String segDestination) {
		this.segDestination = segDestination;
	}

	public int getLoadPlanVersion() {
		return loadPlanVersion;
	}

	public void setLoadPlanVersion(int loadPlanVersion) {
		this.loadPlanVersion = loadPlanVersion;
	}

	public long getUldReferenceNo() {
		return uldReferenceNo;
	}

	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public int getSegSerialNumber() {
		return segSerialNumber;
	}

	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
	}

	public int getPlannedPieces() {
		return plannedPieces;
	}

	public void setPlannedPieces(int plannedPieces) {
		this.plannedPieces = plannedPieces;
	}

	public double getPlannedWeight() {
		return plannedWeight;
	}

	public void setPlannedWeight(double plannedWeight) {
		this.plannedWeight = plannedWeight;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getPlannedPosition() {
		return plannedPosition;
	}

	public void setPlannedPosition(String plannedPosition) {
		this.plannedPosition = plannedPosition;
	}

	public double getPlannedVolume() {
		return plannedVolume;
	}

	public void setPlannedVolume(double plannedVolume) {
		this.plannedVolume = plannedVolume;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public String getUldFullIndicator() {
		return uldFullIndicator;
	}
	public void setUldFullIndicator(String uldFullIndicator) {
		this.uldFullIndicator = uldFullIndicator;
	}
	public String getSubClassGroup() {
		return subClassGroup;
	}
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	
	
	
}
