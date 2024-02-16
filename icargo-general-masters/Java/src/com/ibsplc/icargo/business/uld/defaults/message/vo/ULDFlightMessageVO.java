/*
 * ULDFlightMessageVO.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.icargo.framework.util.time.GMTDate;

/**
 * @author A-1945
 */
public class ULDFlightMessageVO extends AbstractVO {

    private String companyCode;

    private String stationCode;

    private int legSerialNumber;

    private int flightSequenceNumber;

    private String flightNumber;

    private int flightCarrierId;

    private GMTDate flightDate;

    private String legOrigin;

    private String legDestination;

    private String aircraftRegistration;

    private Collection<ULDFlightMessageDetailsVO> uldFlightMessageDetailsVOs;

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
    public String getStationCode() {
        return stationCode;
    }
    /**
     *
     * @param stationCode
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
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
    public String getLegOrigin() {
        return legOrigin;
    }

    /**
     *
     * @return
     */
    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    /**
     * 
     * @param aircraftRegistration
     */
    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    /**
     *
     * @param legOrigin
     */
    public void setLegOrigin(String legOrigin) {
        this.legOrigin = legOrigin;
    }

    /**
     *
     * @return
     */
    public String getLegDestination() {
        return legDestination;
    }

    /**
     * 
     * @param legDestination
     */
    public void setLegDestination(String legDestination) {
        this.legDestination = legDestination;
    }

    /**
     *
     * @return
     */
    public Collection<ULDFlightMessageDetailsVO> getUldFlightMessageDetailsVOs() {
        return uldFlightMessageDetailsVOs;
    }
    /**
     *
     * @param uldFlightMessageDetailsVOs
     */
    public void setUldFlightMessageDetailsVOs(
            Collection<ULDFlightMessageDetailsVO> uldFlightMessageDetailsVOs) {
        this.uldFlightMessageDetailsVOs = uldFlightMessageDetailsVOs;
    }
}
