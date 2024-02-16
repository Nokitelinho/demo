/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailbagDetailsVo.java
 *
 *	Created by	:	204082
 *	Created on	:	19-Oct-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailbagDetailsVO extends AbstractVO {

	private Long mailSequenceNumber;
	private String mailbagId;
	private Measure weight;
	private String flightCarrierCode;
	private String flightNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private LocalDate std;
	private LocalDate stdutc;
	private LocalDate sta;
	private LocalDate stautc;
	private String inboundResditEvtCode;
	private String inboundSource;
	private String arrivalAirport;
	private String outboundResditEvtCode;
	private String outboundSource;
	private String departureAirport;
	private String containerId;
	private String hbaType;
	private String hbaPosition;
	private String orginAirport;
	private String destAirport;
	private String hndoverRecCarCod;
	private String hndDelCarCod;

	public Long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(Long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public Measure getWeight() {
		return weight;
	}

	public void setWeight(Measure weight) {
		this.weight = weight;
	}

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getSegmentOrigin() {
		return segmentOrigin;
	}

	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}

	public String getSegmentDestination() {
		return segmentDestination;
	}

	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}

	public LocalDate getStd() {
		return std;
	}

	public void setStd(LocalDate std) {
		this.std = std;
	}

	public LocalDate getStdutc() {
		return stdutc;
	}

	public void setStdutc(LocalDate stdutc) {
		this.stdutc = stdutc;
	}

	public LocalDate getSta() {
		return sta;
	}

	public void setSta(LocalDate sta) {
		this.sta = sta;
	}

	public LocalDate getStautc() {
		return stautc;
	}

	public void setStautc(LocalDate stautc) {
		this.stautc = stautc;
	}

	public String getInboundResditEvtCode() {
		return inboundResditEvtCode;
	}

	public void setInboundResditEvtCode(String inboundResditEvtCode) {
		this.inboundResditEvtCode = inboundResditEvtCode;
	}

	public String getInboundSource() {
		return inboundSource;
	}

	public void setInboundSource(String inboundSource) {
		this.inboundSource = inboundSource;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getOutboundResditEvtCode() {
		return outboundResditEvtCode;
	}

	public void setOutboundResditEvtCode(String outboundResditEvtCode) {
		this.outboundResditEvtCode = outboundResditEvtCode;
	}

	public String getOutboundSource() {
		return outboundSource;
	}

	public void setOutboundSource(String outboundSource) {
		this.outboundSource = outboundSource;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getHbaType() {
		return hbaType;
	}

	public void setHbaType(String hbaType) {
		this.hbaType = hbaType;
	}

	public String getHbaPosition() {
		return hbaPosition;
	}

	public void setHbaPosition(String hbaPosition) {
		this.hbaPosition = hbaPosition;
	}
	public String getOrginAirport() {
		return orginAirport;
	}
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}
	public String getDestAirport() {
		return destAirport;
	}
	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}
	public String getHndoverRecCarCod() {
		return hndoverRecCarCod;
	}
	public void setHndoverRecCarCod(String hndoverRecCarCod) {
		this.hndoverRecCarCod = hndoverRecCarCod;
	}
	public String getHndDelCarCod() {
		return hndDelCarCod;
	}
	public void setHndDelCarCod(String hndDelCarCod) {
		this.hndDelCarCod = hndDelCarCod;
	}
}
