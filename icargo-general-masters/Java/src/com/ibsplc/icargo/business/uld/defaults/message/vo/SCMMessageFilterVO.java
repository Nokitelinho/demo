/*
 * SCMMessageFilterVO.java Created on jul 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.DynamicSearchVO;

/**
 * @author A-2048
 * 
 */
public class SCMMessageFilterVO extends DynamicSearchVO {

	private String companyCode;

	private String airportCode;

	private int flightCarrierIdentifier;

	private LocalDate stockControlDate;

	private int pageNumber;

	private int absoluteIndex;

	private Collection<String> uldNumbers;

	private String sequenceNumber;
	
	//Added By Sreekumar S - AirNZ CR 521 on 28Mar08
	private String errorCode;
	
	//Added By A-6344 for ICRD-55460 starts
	private String uldNumber;
	private String uldStatus;
	//Added By A-6344 for ICRD-55460 end
	
	private String airlineCode;
	
	private String facility;
	
	private String location;
	
	 private int totalRecords;
	private int totalRecordsCount;
	//added as part of  ICRD-270001 startes
	private boolean missingDiscrepancyCaptured ;
	private String fromTab;
	//added as part of  ICRD-270001 startes
/**
 * 
 * @return
 */
	public String getAirlineCode() {
		return airlineCode;
	}
/**
 * 
 * @param airlineCode
 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	

	/**
 * @return the uldNumber
 */
public String getUldNumber() {
	return uldNumber;
}
/**
 * @param uldNumber the uldNumber to set
 */
public void setUldNumber(String uldNumber) {
	this.uldNumber = uldNumber;
}
/**
 * @return the uldStatus
 */
public String getUldStatus() {
	return uldStatus;
}
/**
 * @param uldStatus the uldStatus to set
 */
public void setUldStatus(String uldStatus) {
	this.uldStatus = uldStatus;
}
	/**
	 * @return String Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return int Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return this.absoluteIndex;
	}

	/**
	 * @param absoluteIndex
	 *            The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * @return int Returns the pageNumber.
	 */
	public int getPageNumber() {
		return this.pageNumber;
	}

	/**
	 * @param pageNumber
	 *            The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return String Returns the airportCode.
	 */
	public String getAirportCode() {
		return this.airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
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
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return LocalDate Returns the stockControlDate.
	 */
	public LocalDate getStockControlDate() {
		return this.stockControlDate;
	}

	/**
	 * @param stockControlDate
	 *            The stockControlDate to set.
	 */
	public void setStockControlDate(LocalDate stockControlDate) {
		this.stockControlDate = stockControlDate;
	}

	/**
	 * @return Collection<String> Returns the uldNumbers.
	 */
	public Collection<String> getUldNumbers() {
		return this.uldNumbers;
	}

	/**
	 * @param uldNumbers
	 *            The uldNumbers to set.
	 */
	public void setUldNumbers(Collection<String> uldNumbers) {
		this.uldNumbers = uldNumbers;
	}
	/**
	 * 
	 * @return
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public boolean isMissingDiscrepancyCaptured() {
		return missingDiscrepancyCaptured;
	}
	public void setMissingDiscrepancyCaptured(boolean missingDiscrepancyCaptured) {
		this.missingDiscrepancyCaptured = missingDiscrepancyCaptured;
	}
	public String getFromTab() {
		return fromTab;
	}
	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

}
