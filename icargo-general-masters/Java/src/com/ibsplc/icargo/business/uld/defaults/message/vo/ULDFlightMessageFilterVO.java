/*
 * ULDFlightMessageFilterVO.java Created on Jul 12, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.icargo.framework.util.time.GMTDate;

/**
 * @author A-1945
 *
 */
public class ULDFlightMessageFilterVO extends AbstractVO {

    private String companyCode;

    private int legSerialNumber;

    private int flightSequenceNumber;

    private String flightNumber;

    private int flightCarrierId;

    private GMTDate flightDate;

    private String uldNumber;

    private String pointOfUnloading;
   // added for ICRD-2268 author A-5125
    private String airportCode;
    private String uldFlightStatus;
    private Collection<String> uldNOsForMessageReconcile;
    private int sequenceNumber;
    public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getUldFlightStatus() {
		return uldFlightStatus;
	}
	public void setUldFlightStatus(String uldFlightStatus) {
		this.uldFlightStatus = uldFlightStatus;
	}
	public Collection<String> getUldNOsForMessageReconcile() {
		return uldNOsForMessageReconcile;
	}
	public void setUldNOsForMessageReconcile(
			Collection<String> uldNOsForMessageReconcile) {
		this.uldNOsForMessageReconcile = uldNOsForMessageReconcile;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
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
     *
     * @return
     */
    public GMTDate getFlightDate() {
        return flightDate;
    }

    /**
     *
     * @param flightDate
     */
    public void setFlightDate(GMTDate flightDate) {
        this.flightDate = flightDate;
    }

    /**
     *
     * @return
     */
    public String getUldNumber() {
        return uldNumber;
    }

    /**
     *
     * @param uldNumber
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }
}
