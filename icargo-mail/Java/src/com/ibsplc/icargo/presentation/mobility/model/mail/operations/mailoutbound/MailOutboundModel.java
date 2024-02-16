/*
 * MailOutboundModel.java Created on Apr 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoutbound;

import java.io.Serializable; 
import com.ibsplc.icargo.framework.util.time.LocalDate;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;

/**
 * Created by A-9498 on 17-03-2020.
 */
public class MailOutboundModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String container;
	private String isContainer;
	private String outboundDestination;
	private String companyCode;
	private String airportCode;
	private String pou;
	private String transferFlag;
	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	private String uldType;
	private String paBuiltFlag;
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String flightNumber;
	private String flightDate;
	private String carrierCode;
	private String containerType;
	private String mailBagID;
	private String truckFlightNum;
	private LocalDate truckFlightDate;
	private String truckFlighCarrierCode;
	private String screeningUser;
	
	public String getScreeningUser() {
		return screeningUser;
	}

	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}

	public String getTruckFlightNum() {
		return truckFlightNum;
	}

	public void setTruckFlightNum(String truckFlightNum) {
		this.truckFlightNum = truckFlightNum;
	}

	public LocalDate getTruckFlightDate() {
		return truckFlightDate;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public void setTruckFlightDate(LocalDate truckFlightDate) {
		this.truckFlightDate = truckFlightDate;
	}

	public String getTruckFlighCarrierCode() {
		return truckFlighCarrierCode;
	}

	public void setTruckFlighCarrierCode(String truckFlighCarrierCode) {
		this.truckFlighCarrierCode = truckFlighCarrierCode;
	}

	public String getMailBagID() {
		return mailBagID;
	}

	public void setMailBagID(String mailBagID) {
		this.mailBagID = mailBagID;
	}

	public String getContainerType() {

		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getUldType() {
		return uldType;
	}

	public void setUldType(String uldType) {
		this.uldType = uldType;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	private Collection<MailbagVO> mailDetails;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getIsContainer() {
		return isContainer;
	}

	public void setIsContainer(String isContainer) {
		this.isContainer = isContainer;
	}

	public String getOutboundDestination() {
		return outboundDestination;
	}

	public void setOutboundDestination(String outboundDestination) {
		this.outboundDestination = outboundDestination;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public Collection<MailbagVO> getMailDetails() {
		return mailDetails;
	}

	public void setMailDetails(Collection<MailbagVO> mailDetails) {
		this.mailDetails = mailDetails;
	}
}