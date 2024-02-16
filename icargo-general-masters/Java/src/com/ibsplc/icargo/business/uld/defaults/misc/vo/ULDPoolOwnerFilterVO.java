/*
 * ULDPoolOwnerFilterVO.java Created on Aug 16 ,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.HashMap;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2048
 *
 */
public class ULDPoolOwnerFilterVO extends AbstractVO implements Serializable{


    private String companyCode;
    private String airlineOne;
    private String airlineTwo;
    private int airlineIdentifierOne;
    private int airlineIdentifierTwo;
    private String airport;
    //Added by nisha for QF1018 on 14Jul08 starts
    private String origin;
    private String destination;
    private String flightNumber;
    private int flightCarrierIdentifier;
    //ends
    private HashMap<String,String> odpairs;
	/**
	 * @return the odpairs
	 */
	public HashMap<String, String> getOdpairs() {
		return odpairs;
	}
	/**
	 * @param odpairs the odpairs to set
	 */
	public void setOdpairs(HashMap<String, String> odpairs) {
		this.odpairs = odpairs;
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
	 * @param airlineIdentifierTwo the airlineIdentifierTwo to set
	 */
	public void setAirlineIdentifierTwo(int airlineIdentifierTwo) {
		this.airlineIdentifierTwo = airlineIdentifierTwo;
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
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	 /* @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
}
