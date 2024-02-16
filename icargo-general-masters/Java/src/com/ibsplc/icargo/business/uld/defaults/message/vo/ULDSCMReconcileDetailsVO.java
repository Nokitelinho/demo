/*
 * ULDSCMReconcileDetailsVO.java Created on AUG 01, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2048
 */
public class ULDSCMReconcileDetailsVO extends AbstractVO {


    private String airportCode;
    private String companyCode;

    private String sequenceNumber;
    private String uldNumber;
    
    private int airlineIdentifier;
    
    private String flightCarrierCode;
    private String uldStatus;
    
    private boolean missingDiscrepancyCaptured ;
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
    *Errors captured at the Client Side
    *This errorcode will be set from the client itself
    *ErrorCode:ERR1-------->Missing ULD
    *ErrorCode:ERR2-------->Extra(Found) ULD
    */
	/**
	*Errors captured at the Server Side
	*For the Extra ULD check for whether the ULD is there in the system
	*if it is not in the system then ErrorCode:ERR3-------->ULD not in the system
	*ErrorCode: ERR4------->Return Transaction
	*ErrorCode: ERR5------->Loan Transaction
	*/

	/*
	*Error Priority
	*ERR1
	*ERR3
	*ERR2
	*/
    private String errorCode;
    
    private String facilityType;
    private String location;



    private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;


	private String operationFlag;

	/**
	 * @return String Returns the airportCode.
	 */
	public String getAirportCode() {
		return this.airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
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
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return String Returns the errorCode.
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	 * @return String Returns the uldNumber.
	 */
	public String getUldNumber() {
		return this.uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return int Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return this.airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
/**
 * 
 * @return
 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
/**
 * 
 * @param flightCarrierCode
 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * 
	 * @return
	 */
public String getFacilityType() {
	return facilityType;
}
/**
 * 
 * @param facilityType
 */
public void setFacilityType(String facilityType) {
	this.facilityType = facilityType;
}
/**
 * 
 * @return
 */
public String getLocation() {
	return location;
}
/**
 * 
 * @param location
 */
public void setLocation(String location) {
	this.location = location;
}
public boolean isMissingDiscrepancyCaptured() {
	return missingDiscrepancyCaptured;
}
public void setMissingDiscrepancyCaptured(boolean missingDiscrepancyCaptured) {
	this.missingDiscrepancyCaptured = missingDiscrepancyCaptured;
}






}
