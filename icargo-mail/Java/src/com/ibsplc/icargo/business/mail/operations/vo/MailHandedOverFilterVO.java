/*
 * MailHandedOverFilterVO.java Created on Jun 30, 2016
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
public class MailHandedOverFilterVO extends AbstractVO{

	private String companyCode;
	private String carrierCode;
	private String flightNumber;
	private String flightCarrierCode;
	private String scanPort;
	private Integer carrierId;
	private Integer flightCarrierId;
	private Integer ownAirlineId;
	private LocalDate fromDate;
	private LocalDate toDate;
	
	
	
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
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the carrierId
	 */
	public Integer getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(Integer carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the flightCarrierId
	 */
	public Integer getFlightCarrierId() {
		return flightCarrierId;
	}
	/**
	 * @param flightCarrierId the flightCarrierId to set
	 */
	public void setFlightCarrierId(Integer flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}
	/**
	 * @return the scanPort
	 */
	public String getScanPort() {
		return scanPort;
	}
	/**
	 * @param scanPort the scanPort to set
	 */
	public void setScanPort(String scanPort) {
		this.scanPort = scanPort;
	}
	/**
	 * @return the ownAirlineId
	 */
	public Integer getOwnAirlineId() {
		return ownAirlineId;
	}
	/**
	 * @param ownAirlineId the ownAirlineId to set
	 */
	public void setOwnAirlineId(Integer ownAirlineId) {
		this.ownAirlineId = ownAirlineId;
	}
	
	
	
	
	

}
