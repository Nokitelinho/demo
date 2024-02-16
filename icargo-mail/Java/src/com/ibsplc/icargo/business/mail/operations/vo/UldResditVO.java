/*
 * UldResditVO.java Created on Jun 30, 2016
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
 * 
 * 
 * @author A-3109
 * 
 */

public class UldResditVO extends AbstractVO {

	private String companyCode;

	private String eventCode;

	private String eventAirport;

	private LocalDate eventDate;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private String uldNumber;

	private String resditSentFlag;

	private long resditSequenceNum;

	private String processedStatus;

	private long messageSequenceNumber;

	private String carditKey;

	private String paOrCarrierCode;

	private String containerJourneyId;

	private int uldEventSequenceNumber;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;

	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/**
	 * @return Returns the uldEventSequenceNumber.
	 */
	public int getUldEventSequenceNumber() {
		return uldEventSequenceNumber;
	}

	/**
	 * @param uldEventSequenceNumber
	 *            The uldEventSequenceNumber to set.
	 */
	public void setUldEventSequenceNumber(int uldEventSequenceNumber) {
		this.uldEventSequenceNumber = uldEventSequenceNumber;
	}

	/**
	 * shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB
	 * ULD.
	 */
	private String shipperBuiltCode;

	/**
	 * @return Returns the paOrCarrierCode.
	 */
	public String getPaOrCarrierCode() {
		return paOrCarrierCode;
	}

	/**
	 * @param paOrCarrierCode
	 *            The paOrCarrierCode to set.
	 */
	public void setPaOrCarrierCode(String paOrCarrierCode) {
		this.paOrCarrierCode = paOrCarrierCode;
	}

	/**
	 * @return Returns the messageSequenceNumber.
	 */
	public long getMessageSequenceNumber() {
		return messageSequenceNumber;
	}

	/**
	 * @param messageSequenceNumber
	 *            The messageSequenceNumber to set.
	 */
	public void setMessageSequenceNumber(long messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}

	/**
	 * @return Returns the processedStatus.
	 */
	public String getProcessedStatus() {
		return processedStatus;
	}

	/**
	 * @param processedStatus
	 *            The processedStatus to set.
	 */
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the eventAirport.
	 */
	public String getEventAirport() {
		return eventAirport;
	}

	/**
	 * @param eventAirport
	 *            The eventAirport to set.
	 */
	public void setEventAirport(String eventAirport) {
		this.eventAirport = eventAirport;
	}

	/**
	 * @return Returns the eventCode.
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode
	 *            The eventCode to set.
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return Returns the eventDate.
	 */
	public LocalDate getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            The eventDate to set.
	 */
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
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
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the resditSentFlag.
	 */
	public String getResditSentFlag() {
		return resditSentFlag;
	}

	/**
	 * @param resditSentFlag
	 *            The resditSentFlag to set.
	 */
	public void setResditSentFlag(String resditSentFlag) {
		this.resditSentFlag = resditSentFlag;
	}

	/**
	 * @return Returns the resditSequenceNum.
	 */
	public long getResditSequenceNum() {
		return resditSequenceNum;
	}

	/**
	 * @param resditSequenceNum
	 *            The resditSequenceNum to set.
	 */
	public void setResditSequenceNum(long resditSequenceNum) {
		this.resditSequenceNum = resditSequenceNum;
	}

	/**
	 * @return Returns the carditKey.
	 */
	public String getCarditKey() {
		return this.carditKey;
	}

	/**
	 * @param carditKey
	 *            The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return the containerJourneyId
	 */
	public String getContainerJourneyId() {
		return containerJourneyId;
	}

	/**
	 * @param containerJourneyId
	 *            the containerJourneyId to set
	 */
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode
	 *            the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
	}

}
