/*
 * FlightFilterMessageVO.java Created on Jul 21, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2048
 *
 */
public class FlightFilterMessageVO extends AbstractVO {

    private String companyCode;

    private int legSerialNumber;

    private int flightSequenceNumber;

    private String flightNumber;

    private int flightCarrierId;

    private LocalDate flightDate;

    private String carrierCode;

    private String pointOfUnloading;
    
    private String airportCode;
    
    private Collection<String> uldNumbers;
    
    private int pageNumber;
    
    private int absoluteIndex;
    
    private Collection<String> ucmSequenceNumbers;
    
    private String messageType;
    /*
     * MessageStatus can be P - Pending, S - Sent, R - Resent
     * if it s NULL then it is an ALL condition
     */
    private String messageStatus;
    
    
    private LocalDate fromDate;
    
    private LocalDate toDate;
    
    private String fromMarkFlightMvt;
    
    private Collection<String> destinations;

    private Collection<String> flightOrigins;
	/**
	 * @return the destinations
	 */
	public Collection<String> getDestinations() {
		return destinations;
	}

	/**
	 * @param destinations the destinations to set
	 */
	public void setDestinations(Collection<String> destinations) {
		this.destinations = destinations;
	}

	/**
	 * @return Returns the fromDate.
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
	 * @return Returns the toDate.
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

	/**
	 * @return String Returns the messageType.
	 */
	public String getMessageType() {
		return this.messageType;
	}

	/**
	 * @param messageType The messageType to set.
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	

	/**
	 * @return Collection<String> Returns the ucmSequenceNumbers.
	 */
	public Collection<String> getUcmSequenceNumbers() {
		return this.ucmSequenceNumbers;
	}

	/**
	 * @param ucmSequenceNumbers The ucmSequenceNumbers to set.
	 */
	public void setUcmSequenceNumbers(Collection<String> ucmSequenceNumbers) {
		this.ucmSequenceNumbers = ucmSequenceNumbers;
	}

	/**
	 * @return int Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return this.absoluteIndex;
	}

	/**
	 * @param absoluteIndex The absoluteIndex to set.
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
	 * @param pageNumber The pageNumber to set.
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
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
     *
     * @return
     */
    public String getPointOfUnloading() {
        return pointOfUnloading;
    }

    /**
     *
     * @param pointOfUnloading
     */
    public void setPointOfUnloading(String pointOfUnloading) {
        this.pointOfUnloading = pointOfUnloading;
    }

    /**
     *
     * @return
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     *
     * @param companyCode
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     *
     * @return
     */
    public int getLegSerialNumber() {
        return legSerialNumber;
    }

    /**
     *
     * @param legSerialNumber
     */
    public void setLegSerialNumber(int legSerialNumber) {
        this.legSerialNumber = legSerialNumber;
    }

    /**
     *
     * @return
     */
    public int getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    /**
     *
     * @param flightSequenceNumber
     */
    public void setFlightSequenceNumber(int flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }

    /**
     *
     * @return
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     *
     * @param flightNumber
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     *
     * @return
     */
    public int getFlightCarrierId() {
        return flightCarrierId;
    }

    /**
     *
     * @param flightCarrierId
     */
    public void setFlightCarrierId(int flightCarrierId) {
        this.flightCarrierId = flightCarrierId;
    }

	/**
	 * @return Collection<String> Returns the uldNumbers.
	 */
	public Collection<String> getUldNumbers() {
		return this.uldNumbers;
	}

	/**
	 * @param uldNumbers The uldNumbers to set.
	 */
	public void setUldNumbers(Collection<String> uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	/**
	 * @return LocalDate Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return this.flightDate;
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
	 * @return Returns the messageStatus.
	 */
	public String getMessageStatus() {
		return this.messageStatus;
	}

	/**
	 * @param messageStatus The messageStatus to set.
	 */
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
	 * @return the fromMarkFlightMvt
	 */
	public String getFromMarkFlightMvt() {
		return fromMarkFlightMvt;
	}

	/**
	 * @param fromMarkFlightMvt the fromMarkFlightMvt to set
	 */
	public void setFromMarkFlightMvt(String fromMarkFlightMvt) {
		this.fromMarkFlightMvt = fromMarkFlightMvt;
	}

	/**
	 * @return the flightOrigins
	 */
	public Collection<String> getFlightOrigins() {
		return flightOrigins;
	}

	/**
	 * @param flightOrigins the flightOrigins to set
	 */
	public void setFlightOrigins(Collection<String> flightOrigins) {
		this.flightOrigins = flightOrigins;
	}
   
}
