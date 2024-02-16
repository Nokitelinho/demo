/*
 * ULDPoolOwnerDetailsVO.java Created on aug 11,2006
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
 * @author A-2048
 *
 */
public class ULDPoolOwnerDetailsVO extends AbstractVO implements Serializable{
    
	private String companyCode;
    private int flightCarrierIdentifier;
    private String sequenceNumber;
    private String polSequenceNumber;
    private int polAirlineIdentifier;
    private String polFligthNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String operationFlag;
    private String hiddenOperationFlag;
    
    private String airlineCode;
    private String airport;
    private String remarks;
    
    private String polAirlineCode;
	/**
	 * @return String Returns the polAirlineCode.
	 */
	public String getPolAirlineCode() {
		return this.polAirlineCode;
	}
	/**
	 * @param polAirlineCode The polAirlineCode to set.
	 */
	public void setPolAirlineCode(String polAirlineCode) {
		this.polAirlineCode = polAirlineCode;
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
	 * @return LocalDate Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return this.fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
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
	/**
	 * @return String Returns the polAirlineIdentifier.
	 */
	public int getPolAirlineIdentifier() {
		return this.polAirlineIdentifier;
	}
	/**
	 * @param polAirlineIdentifier The polAirlineIdentifier to set.
	 */
	public void setPolAirlineIdentifier(int polAirlineIdentifier) {
		this.polAirlineIdentifier = polAirlineIdentifier;
	}
	/**
	 * @return String Returns the polFligthNumber.
	 */
	public String getPolFligthNumber() {
		return this.polFligthNumber;
	}
	/**
	 * @param polFligthNumber The polFligthNumber to set.
	 */
	public void setPolFligthNumber(String polFligthNumber) {
		this.polFligthNumber = polFligthNumber;
	}
	/**
	 * @return String Returns the polSequenceNumber.
	 */
	public String getPolSequenceNumber() {
		return this.polSequenceNumber;
	}
	/**
	 * @param polSequenceNumber The polSequenceNumber to set.
	 */
	public void setPolSequenceNumber(String polSequenceNumber) {
		this.polSequenceNumber = polSequenceNumber;
	}
	/**
	 * @return String Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return LocalDate Returns the toDate.
	 */
	public LocalDate getToDate() {
		return this.toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public String getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}
	public void setHiddenOperationFlag(String hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
