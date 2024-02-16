/*
 * DailyMailStationFilterVO.java Created on JUN 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/*
 * This Class is Used as The FilterVO For Daily Mail Station 
 */
/**
 * @author a-5991
 * 
 */
public class DailyMailStationFilterVO extends AbstractVO{
	private LocalDate filghtDate;
	private String flightNumber;
	private int flightCarrireID;
	private long flightSeqNumber;
	private int segSerialNumber;
	private String companyCode;
	private String origin;
	private String destination;
	private String carrierCode;
	private LocalDate flightFromDate;
	private LocalDate flightToDate;
	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
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
	 * @return the filghtDate
	 */
	public LocalDate getFilghtDate() {
		return filghtDate;
	}
	/**
	 * @param filghtDate the filghtDate to set
	 */
	public void setFilghtDate(LocalDate filghtDate) {
		this.filghtDate = filghtDate;
	}
	/**
	 * @return the flightCarrireID
	 */
	public int getFlightCarrireID() {
		return flightCarrireID;
	}
	/**
	 * @param flightCarrireID the flightCarrireID to set
	 */
	public void setFlightCarrireID(int flightCarrireID) {
		this.flightCarrireID = flightCarrireID;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightSeqNumber
	 */
	public long getFlightSeqNumber() {
		return flightSeqNumber;
	}
	/**
	 * @param flightSeqNumber the flightSeqNumber to set
	 */
	public void setFlightSeqNumber(long flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	/**
	 * @return the segSerialNumber
	 */
	public int getSegSerialNumber() {
		return segSerialNumber;
	}
	/**
	 * @param segSerialNumber the segSerialNumber to set
	 */
	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
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
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the flightFromDate
	 */
	public LocalDate getFlightFromDate() {
		return flightFromDate;
	}
	/**
	 * @param flightFromDate the flightFromDate to set
	 */
	public void setFlightFromDate(LocalDate flightFromDate) {
		this.flightFromDate = flightFromDate;
	}
	/**
	 * @return the flightToDate
	 */
	public LocalDate getFlightToDate() {
		return flightToDate;
	}
	/**
	 * @param flightToDate the flightToDate to set
	 */
	public void setFlightToDate(LocalDate flightToDate) {
		this.flightToDate = flightToDate;
	}
	
	
	
}
