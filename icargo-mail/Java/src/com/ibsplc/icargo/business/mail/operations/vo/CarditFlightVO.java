/*
 * CarditFlightVO.java Created on Jun 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-5991
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * JUN 30, 2016 A-5991 First Draft
 */
public class CarditFlightVO extends AbstractVO {

	private String flightNumber;
	private String companyCode;
	private String carrierCode;
	private LocalDate flightDate;
	private Collection<ConsignmentDocumentVO> carditDetails;
	private String hasMailbags;
	private String hasUlds;
	private LocalDate eventTime;
	private String eventCode;
	private String assignedPort;
	private String handedOverPartner;
	private String paCode;
	private String pou;
	private String flightno;
	private String carrCode;
	private String legserialNo;
	private String segSerialNo;
	private String fltCarrierId;
	private String fltSeqNo;
	private String departurePort;
	private LocalDate flightDepartureTime;
	
	
	private String prevCarrier;

	private String nextCarrier;

	private String finalDestn;

	public void setPrevCarrier(String prevCarrier) {
		this.prevCarrier = prevCarrier;
	}

	public String getPrevCarrier() {
		return prevCarrier;
	}

	public void setNextCarrier(String nextCarrier) {
		this.nextCarrier = nextCarrier;
	}

	public String getNextCarrier() {
		return nextCarrier;
	}

	public void setFinalDestn(String finalDestn) {
		this.finalDestn = finalDestn;
	}

	public String getFinalDestn() {
		return finalDestn;
	}
	
	private LocalDate estArrTime;
	private String arrivalPort;

	public String getArrivalPort() {
		return arrivalPort;
	}

	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	public LocalDate getEstArrTime() {
		return estArrTime;
	}

	public void setEstArrTime(LocalDate estArrTime) {
		this.estArrTime = estArrTime;
	}
	
	public Collection<ConsignmentDocumentVO> getCarditDetails() {
		return carditDetails;
	}
	public void setCarditDetails(Collection<ConsignmentDocumentVO> carditDetails) {
		this.carditDetails = carditDetails;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getHasMailbags() {
		return hasMailbags;
	}
	public void setHasMailbags(String hasMailbags) {
		this.hasMailbags = hasMailbags;
	}
	public String getHasUlds() {
		return hasUlds;
	}
	public void setHasUlds(String hasUlds) {
		this.hasUlds = hasUlds;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAssignedPort() {
		return assignedPort;
	}
	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public LocalDate getEventTime() {
		return eventTime;
	}
	public void setEventTime(LocalDate eventTime) {
		this.eventTime = eventTime;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getFltCarrierId() {
		return fltCarrierId;
	}
	public void setFltCarrierId(String fltCarrierId) {
		this.fltCarrierId = fltCarrierId;
	}
	public String getHandedOverPartner() {
		return handedOverPartner;
	}
	public void setHandedOverPartner(String handedOverPartner) {
		this.handedOverPartner = handedOverPartner;
	}
	public String getLegserialNo() {
		return legserialNo;
	}
	public void setLegserialNo(String legserialNo) {
		this.legserialNo = legserialNo;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getSegSerialNo() {
		return segSerialNo;
	}
	public void setSegSerialNo(String segSerialNo) {
		this.segSerialNo = segSerialNo;
	}
	public String getCarrCode() {
		return carrCode;
	}
	public void setCarrCode(String carrCode) {
		this.carrCode = carrCode;
	}
	public String getFltSeqNo() {
		return fltSeqNo;
	}
	public void setFltSeqNo(String fltSeqNo) {
		this.fltSeqNo = fltSeqNo;
	}
	public String getDeparturePort() {
		return departurePort;
	}
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}
	public LocalDate getFlightDepartureTime() {
		return flightDepartureTime;
	}
	public void setFlightDepartureTime(LocalDate flightDepartureTime) {
		this.flightDepartureTime = flightDepartureTime;
	}
	

}
