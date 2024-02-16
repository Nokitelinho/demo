/*
 * ULDPoolSegmentExceptionsVO.java Created on aug 11,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3429
 *
 */


public class ULDPoolSegmentExceptionsVO extends AbstractVO implements Serializable{
	
	private String companyCode;
	private String sequenceNumber;
	private String airlineOne;
	private String airlineTwo;
	private int airlineIdentifierOne;
	private int airlineIdentifierTwo;
	private String airport;
	private String origin;
	private String destination;
	private String operationFlag;
	private String hiddenOperationFlag;
	private long serialNumber;
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
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the hiddenOperationFlag
	 */
	public String getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}
	/**
	 * @param hiddenOperationFlag the hiddenOperationFlag to set
	 */
	public void setHiddenOperationFlag(String hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
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
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
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
	 * @param airlineIdentifierTwo  airlineIdentifierTwo to set
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