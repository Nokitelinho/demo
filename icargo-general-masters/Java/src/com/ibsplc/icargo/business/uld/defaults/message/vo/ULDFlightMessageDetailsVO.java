/*
 * ULDFlightMessageDetailsVO.java Created on Jul 7, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1945
 */
public class ULDFlightMessageDetailsVO extends AbstractVO {

    private String companyCode;

    private String stationCode;

    private int legSerialNumber;

    private int flightSequenceNumber;

    private String flightNumber;

    private int flightCarrierId;

    private String uldNumber;
    
    private Measure uldWeight;

    private String content;

    private String pointOfUnloading;
    
    private String uldLocation;
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
    public String getContent() {
        return content;
    }
    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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
	/**
	 * @return the uldWeight
	 */
	public Measure getUldWeight() {
		return uldWeight;
	}
	/**
	 * @param uldWeight the uldWeight to set
	 */
	public void setUldWeight(Measure uldWeight) {
		this.uldWeight = uldWeight;
	}
	public String getUldLocation() {
		return uldLocation;
	}
	public void setUldLocation(String uldLocation) {
		this.uldLocation = uldLocation;
	}
	
	
}
