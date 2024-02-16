/*
 * FlightDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message.vo;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class FlightDetailsVO extends AbstractVO{
	
	public static final String ULDRELOCATION = "UR";
	
	private String companyCode;

	//mandatory
	private String carrierCode;

	// mandatory
	private int flightCarrierIdentifier;
	private String flightNumber;
	// the date on which the flight is scheduled.
	private LocalDate flightDate;
	//the date at which the actual Build up occures.
	private LocalDate transactionDate;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private String direction;//E-Export , I-Import and O-Offload
	private String remark;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser; 
	//Added by Sreekumar S for ANACR 1409
	private String currentAirport;
	//Added By Ashraf on 06APR08 picking up the location of the  build up.
	private String location;
	
	//Added By Asharaf Binu Pto get the destination during flight 
	private String destination;
	
	private Collection<ULDInFlightVO> uldInFlightVOs;

//	Doubt
	private String content;

	//private String pointOfLading;

	//private String pointOfUnLading;
	
	private Collection<String> airports;
	
	private String flightRoute;
	//added for bug 36920 on 19Feb09
	private LocalDate ata;
	private boolean isValidated;
	private boolean isFromFlightFinalization;
	private HashMap<String,LocalDate> atas;
	private String tailNumber;
	//added for bug 36920 on 19Feb09 ends
	

//merge solved by A-7794
	
	//Added by A-6991 for ICRD-77772 Starts
	private String subSystem;
	private boolean isWetleasedFlt;

	//FLTTYP Added by A-8445 for IASCB-22297
	private String flightType;
	
	/**
	 * 	Getter for subSystem 
	 *	Added by : A-6991 on 20-Apr-2017
	 * 	Used for : ICRD-77772 
	 */
	public String getSubSystem() {
		return subSystem;
	}

	/**
	 *  @param subSystem the subSystem to set
	 * 	Setter for subSystem 
	 *	Added by : A-6991 on 20-Apr-2017
	 * 	Used for : ICRD-77772 
	 */
	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	/**
	 * @return the tailNumber
	 */
	public String getTailNumber() {
		return tailNumber;
	}

	/**
	 * @param tailNumber the tailNumber to set
	 */
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	/**
	 * @return the ata
	 */
	public LocalDate getAta() {
		return ata;
	}

	/**
	 * @param ata the ata to set
	 */
	public void setAta(LocalDate ata) {
		this.ata = ata;
	}

	/**
	 * @return the flightRoute
	 */
	public String getFlightRoute() {
		return flightRoute;
	}

	/**
	 * @param flightRoute the flightRoute to set
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}

	//Added by nisha on 11feb08
	//Acceptance-AC,Manifest-M,Arrival-AR.....values should be set
	private String action;




	//added for bug 36920 on 19Feb09 ends
	public static final String ACCEPTANCE = "AC";

	public static final String ARRIVAL = "AR";

	public static final String MANIFEST="M";

	public static final String  BUILDUP="BU";

	public static final String BREAKDOWN="BR";	
	
	public static final String CLOSURE = "CL";
	public static final String FINALISATION = "FL";
	public static final String OFFLOADED = "OF";
	
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCurrentAirport() {
		return currentAirport;
	}

	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
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
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(String direction) {
		this.direction = direction;
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
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the uldInFlightVOs.
	 */
	public Collection<ULDInFlightVO> getUldInFlightVOs() {
		return uldInFlightVOs;
	}

	/**
	 * @param uldInFlightVOs The uldInFlightVOs to set.
	 */
	public void setUldInFlightVOs(Collection<ULDInFlightVO> uldInFlightVOs) {
		this.uldInFlightVOs = uldInFlightVOs;
	}

	//@Column(name = "")
	/**
	 * @return Returns the transactionDate.
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	

	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the airports
	 */
	public Collection<String> getAirports() {
		return airports;
	}

	/**
	 * @param airports the airports to set
	 */
	public void setAirports(Collection<String> airports) {
		this.airports = airports;
	}

	/**
	 * @return the atas
	 */
	public HashMap<String, LocalDate> getAtas() {
		return atas;
	}

	/**
	 * @param atas the atas to set
	 */
	public void setAtas(HashMap<String, LocalDate> atas) {
		this.atas = atas;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public boolean isFromFlightFinalization() {
		return isFromFlightFinalization;
	}

	public void setFromFlightFinalization(boolean isFromFlightFinalization) {
		this.isFromFlightFinalization = isFromFlightFinalization;
	}

	/**
	 * 	Method		:	FlightDetailsVO.setWetleasedFlt
	 *	Added by 	:	a-6830 on Sep 4, 2017
	 * 	Used for 	:	Added as part of CR 192322
	 *	Parameters	:	@param isWetleasedFlt 
	 *	Return type	: 	void
	 */
	public void setWetleasedFlt(boolean isWetleasedFlt) {
		this.isWetleasedFlt = isWetleasedFlt;
		
	}
	
	public boolean isWetleasedFlt() {
		return isWetleasedFlt;
	}

	/**
	 * 	Method		:	FlightDetailsVO.setFlightType
	 *	Added by 	:	A-8445 on Dec 16, 2019
	 * 	Used for 	:	Added as part of CR IASCB-22297
	 *	Parameters	:	@param flightType 
	 *	Return type	: 	void
	 */
	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	
	
}
