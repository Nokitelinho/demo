/*
 * MLDDetailVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 * 
 */
public class MLDDetailVO extends AbstractVO {

	private String mailModeInb;
	private String carrier;
	private String modeDescriptionInb;

	private String polInb;

	private LocalDate eventTimeInb;
	
	private String carrierCodeInb;
	
	private String flightNumberInb;

	private LocalDate flightOperationDateInb;

	private String postalCodeInb;
	
	private String mailModeOub;

	private String modeDescriptionOub;

	private String pouOub;

	private LocalDate eventTimeOub;
	
	private String carrierCodeOub;
	
	private String flightNumberOub;

	private LocalDate flightOperationDateOub;

	private String postalCodeOub;
	
	private String messageVersion;
	
	private long messageSequence;
	
	private int serialNumber;
	
	private String companyCode;
	
	private String mailIdr;
	
	private long flightSequenceNumberOub;

	private int carrierIdOub;

	private long flightSequenceNumberInb;

	private int carrierIdInb;
	
	private String flightDay;

	private String flight;
	//Added as part of bug IASCB-63591 by A-5526 
	private String airport;
	
	private String containerJourneyId;
	
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return the flightSequenceNumberOub
	 */
	public long getFlightSequenceNumberOub() {
		return flightSequenceNumberOub;
	}

	/**
	 * @param flightSequenceNumberOub the flightSequenceNumberOub to set
	 */
	public void setFlightSequenceNumberOub(long flightSequenceNumberOub) {
		this.flightSequenceNumberOub = flightSequenceNumberOub;
	}

	/**
	 * @return the carrierIdOub
	 */
	public int getCarrierIdOub() {
		return carrierIdOub;
	}

	/**
	 * @param carrierIdOub the carrierIdOub to set
	 */
	public void setCarrierIdOub(int carrierIdOub) {
		this.carrierIdOub = carrierIdOub;
	}

	/**
	 * @return the flightSequenceNumberInb
	 */
	public long getFlightSequenceNumberInb() {
		return flightSequenceNumberInb;
	}

	/**
	 * @param flightSequenceNumberInb the flightSequenceNumberInb to set
	 */
	public void setFlightSequenceNumberInb(long flightSequenceNumberInb) {
		this.flightSequenceNumberInb = flightSequenceNumberInb;
	}

	/**
	 * @return the carrierIdInb
	 */
	public int getCarrierIdInb() {
		return carrierIdInb;
	}

	/**
	 * @param carrierIdInb the carrierIdInb to set
	 */
	public void setCarrierIdInb(int carrierIdInb) {
		this.carrierIdInb = carrierIdInb;
	}

	/**
	 * @return the carrierCodeInb
	 */
	public String getCarrierCodeInb() {
		return carrierCodeInb;
	}

	/**
	 * @param carrierCodeInb the carrierCodeInb to set
	 */
	public void setCarrierCodeInb(String carrierCodeInb) {
		this.carrierCodeInb = carrierCodeInb;
	}

	/**
	 * @return the carrierCodeOub
	 */
	public String getCarrierCodeOub() {
		return carrierCodeOub;
	}

	/**
	 * @param carrierCodeOub the carrierCodeOub to set
	 */
	public void setCarrierCodeOub(String carrierCodeOub) {
		this.carrierCodeOub = carrierCodeOub;
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
	 * @return the eventTimeInb
	 */
	public LocalDate getEventTimeInb() {
		return eventTimeInb;
	}

	/**
	 * @param eventTimeInb the eventTimeInb to set
	 */
	public void setEventTimeInb(LocalDate eventTimeInb) {
		this.eventTimeInb = eventTimeInb;
	}

	/**
	 * @return the eventTimeOub
	 */
	public LocalDate getEventTimeOub() {
		return eventTimeOub;
	}

	/**
	 * @param eventTimeOub the eventTimeOub to set
	 */
	public void setEventTimeOub(LocalDate eventTimeOub) {
		this.eventTimeOub = eventTimeOub;
	}

	/**
	 * @return the flightNumberInb
	 */
	public String getFlightNumberInb() {
		return flightNumberInb;
	}

	/**
	 * @param flightNumberInb the flightNumberInb to set
	 */
	public void setFlightNumberInb(String flightNumberInb) {
		this.flightNumberInb = flightNumberInb;
	}

	/**
	 * @return the flightNumberOub
	 */
	public String getFlightNumberOub() {
		return flightNumberOub;
	}

	/**
	 * @param flightNumberOub the flightNumberOub to set
	 */
	public void setFlightNumberOub(String flightNumberOub) {
		this.flightNumberOub = flightNumberOub;
	}

	/**
	 * @return the flightOperationDateInb
	 */
	public LocalDate getFlightOperationDateInb() {
		return flightOperationDateInb;
	}

	/**
	 * @param flightOperationDateInb the flightOperationDateInb to set
	 */
	public void setFlightOperationDateInb(LocalDate flightOperationDateInb) {
		this.flightOperationDateInb = flightOperationDateInb;
	}

	/**
	 * @return the flightOperationDateOub
	 */
	public LocalDate getFlightOperationDateOub() {
		return flightOperationDateOub;
	}

	/**
	 * @param flightOperationDateOub the flightOperationDateOub to set
	 */
	public void setFlightOperationDateOub(LocalDate flightOperationDateOub) {
		this.flightOperationDateOub = flightOperationDateOub;
	}

	/**
	 * @return the mailIdr
	 */
	public String getMailIdr() {
		return mailIdr;
	}

	/**
	 * @param mailIdr the mailIdr to set
	 */
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	/**
	 * @return the mailModeInb
	 */
	public String getMailModeInb() {
		return mailModeInb;
	}

	/**
	 * @param mailModeInb the mailModeInb to set
	 */
	public void setMailModeInb(String mailModeInb) {
		this.mailModeInb = mailModeInb;
	}

	/**
	 * @return the mailModeOub
	 */
	public String getMailModeOub() {
		return mailModeOub;
	}

	/**
	 * @param mailModeOub the mailModeOub to set
	 */
	public void setMailModeOub(String mailModeOub) {
		this.mailModeOub = mailModeOub;
	}

	/**
	 * @return the messageSequence
	 */
	public long getMessageSequence() {
		return messageSequence;
	}

	/**
	 * @param messageSequence the messageSequence to set
	 */
	public void setMessageSequence(long messageSequence) {
		this.messageSequence = messageSequence;
	}

	/**
	 * @return the messageVersion
	 */
	public String getMessageVersion() {
		return messageVersion;
	}

	/**
	 * @param messageVersion the messageVersion to set
	 */
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	/**
	 * @return the modeDescriptionInb
	 */
	public String getModeDescriptionInb() {
		return modeDescriptionInb;
	}

	/**
	 * @param modeDescriptionInb the modeDescriptionInb to set
	 */
	public void setModeDescriptionInb(String modeDescriptionInb) {
		this.modeDescriptionInb = modeDescriptionInb;
	}

	/**
	 * @return the modeDescriptionOub
	 */
	public String getModeDescriptionOub() {
		return modeDescriptionOub;
	}

	/**
	 * @param modeDescriptionOub the modeDescriptionOub to set
	 */
	public void setModeDescriptionOub(String modeDescriptionOub) {
		this.modeDescriptionOub = modeDescriptionOub;
	}

	/**
	 * @return the polInb
	 */
	public String getPolInb() {
		return polInb;
	}

	/**
	 * @param polInb the polInb to set
	 */
	public void setPolInb(String polInb) {
		this.polInb = polInb;
	}

	/**
	 * @return the postalCodeInb
	 */
	public String getPostalCodeInb() {
		return postalCodeInb;
	}

	/**
	 * @param postalCodeInb the postalCodeInb to set
	 */
	public void setPostalCodeInb(String postalCodeInb) {
		this.postalCodeInb = postalCodeInb;
	}

	/**
	 * @return the postalCodeOub
	 */
	public String getPostalCodeOub() {
		return postalCodeOub;
	}

	/**
	 * @param postalCodeOub the postalCodeOub to set
	 */
	public void setPostalCodeOub(String postalCodeOub) {
		this.postalCodeOub = postalCodeOub;
	}

	/**
	 * @return the pouOub
	 */
	public String getPouOub() {
		return pouOub;
	}

	/**
	 * @param pouOub the pouOub to set
	 */
	public void setPouOub(String pouOub) {
		this.pouOub = pouOub;
	}

	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 	Getter for flightDay 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public String getFlightDay() {
		return flightDay;
	}

	/**
	 *  @param flightDay the flightDay to set
	 * 	Setter for flightDay 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public void setFlightDay(String flightDay) {
		this.flightDay = flightDay;
	}

	/**
	 * 	Getter for flightNumber 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public String getFlight() {
		return flight;
	}

	/**
	 *  @param flightNumber the flightNumber to set
	 * 	Setter for flightNumber 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public void setFlight(String flight) {
		this.flight = flight;
	}

	/**
	 * 	Getter for carrierCode 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 *  @param carrierCode the carrierCode to set
	 * 	Setter for carrierCode 
	 *	Added by : A-4803 on 20-Oct-2014
	 * 	Used for : generating trolley number
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}	
	
	public String getContainerJourneyId() {
		return containerJourneyId;
	}
	
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}	
}