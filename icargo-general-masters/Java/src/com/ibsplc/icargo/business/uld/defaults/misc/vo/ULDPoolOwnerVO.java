/*
 * ULDPoolOwnerVO.java Created on Aug 11 ,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2048
 *
 */
public class ULDPoolOwnerVO extends AbstractVO implements Serializable{


    private String companyCode;
    private String airlineOne;
    private String airlineTwo;
    private int airlineIdentifierOne;
    private int airlineIdentifierTwo;
    private String airport;
    private String remarks;

    private String operationFlag;
    private String hiddenOperationFlag;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
    private int flightCarrierIdentifier;
    private String flightNumber;
    private String origin;
    private String destination;
    private long serialNumber;

    private Collection <ULDPoolSegmentExceptionsVO> poolSegmentsExceptionsVOs;

 	private Collection <ULDPoolOwnerDetailsVO> poolDetailVOs;
	/**
	 * @return String Returns the origin.
	 */
	public String getOrigin() {
		return this.origin;
	}


	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return String Returns the destination.
	 */
	public String getDestination() {
		return this.destination;
	}


	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}


	/**
	 * @return int Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return this.flightCarrierIdentifier;
	}


	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}


	/**
	 * @return String Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return this.flightNumber;
	}


	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}


	/**
	 * @return Collection<ULDPoolOwnerDetailsVO> Returns the poolDetailVOs.
	 */
	public Collection<ULDPoolOwnerDetailsVO> getPoolDetailVOs() {
		return this.poolDetailVOs;
	}


	/**
	 * @param poolDetailVOs The poolDetailVOs to set.
	 */
	public void setPoolDetailVOs(Collection<ULDPoolOwnerDetailsVO> poolDetailVOs) {
		this.poolDetailVOs = poolDetailVOs;
	}

	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}


	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



	/**
	 * @return LocalDate Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}


	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}


	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}


	/**
	 * @return String Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}


	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}


	public String getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}


	public void setHiddenOperationFlag(String hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}


	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}


	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}


	/**
	 * @return the airlineOne
	 */
	public String getAirlineOne() {
		return airlineOne;
	}


	/**
	 * @param airlineOne the airlineOne to set
	 */
	public void setAirlineOne(String airlineOne) {
		this.airlineOne = airlineOne;
	}


	/**
	 * @return the airlineTwo
	 */
	public String getAirlineTwo() {
		return airlineTwo;
	}


	/**
	 * @param airlineTwo the airlineTwo to set
	 */
	public void setAirlineTwo(String airlineTwo) {
		this.airlineTwo = airlineTwo;
	}


	/**
	 * @return the poolSegmentsExceptionsVOs
	 */
	public Collection<ULDPoolSegmentExceptionsVO> getPoolSegmentsExceptionsVOs() {
		return this.poolSegmentsExceptionsVOs;
	}


	/**
	 * @param poolSegmentsExceptionsVOs the poolSegmentsExceptionsVOs to set
	 */
	public void setPoolSegmentsExceptionsVOs(
			Collection<ULDPoolSegmentExceptionsVO> poolSegmentsExceptionsVOs) {
		this.poolSegmentsExceptionsVOs = poolSegmentsExceptionsVOs;
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


	/**
	 * @return the airlineIdentifierOne
	 */
	public int getAirlineIdentifierOne() {
		return airlineIdentifierOne;
	}


	/**
	 * @param airlineIdentifierOne the airlineIdentifierOne to set
	 */
	public void setAirlineIdentifierOne(int airlineIdentifierOne) {
		this.airlineIdentifierOne = airlineIdentifierOne;
	}


	/**
	 * @return the airlineIdentifierTwo
	 */
	public int getAirlineIdentifierTwo() {
		return airlineIdentifierTwo;
	}


	/**
	 * @param airlineIdentifierTwo the airlineIdentifierTwo to set
	 */
	public void setAirlineIdentifierTwo(int airlineIdentifierTwo) {
		this.airlineIdentifierTwo = airlineIdentifierTwo;
	}
	
	
	/**
	 * @return the serialNumber
	 */
	public long getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}


}
