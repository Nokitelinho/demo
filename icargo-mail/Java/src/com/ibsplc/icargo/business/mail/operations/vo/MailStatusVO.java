/*
 * MailStatusVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 *
 */


public class MailStatusVO extends AbstractVO  {

	private LocalDate flightDate;
	private String flightCarrierCode;
	private String flightNumber;

	private String incommingFlightCarrierCode;
	private String incommingFlightNumber;
	private LocalDate incommingFlightDate;
	private String pol;
	private String pou;
	private String dsn;
	private String mailBagId;
	//private String weight;
	private Measure weight;//added by A-7371
	private String carditAvailable;
	private String companyCode;
	private LocalDate scheduledDepartureTime;
	private String legStatus;
	private String flightRoute;
	private String containerNumber;
/**
 * 
 * @return weight
 */
	public Measure getWeight() {
		return weight;
	}
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber()   {
		return containerNumber;
	}
	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return Returns the flightRoute.
	 */
	public String getFlightRoute() {
		return flightRoute;
	}
	/**
	 * @param flightRoute The flightRoute to set.
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	/**
	 * @return Returns the legStatus.
	 */
	public String getLegStatus() {
		return legStatus;
	}
	/**
	 * @param legStatus The legStatus to set.
	 */
	public void setLegStatus(String legStatus) {
		this.legStatus = legStatus;
	}
	/**
	 * @return Returns the scheduledDepartureTime.
	 */
	public LocalDate getScheduledDepartureTime() {
		return scheduledDepartureTime;
	}
	/**
	 * @param scheduledDepartureTime The scheduledDepartureTime to set.
	 */
	public void setScheduledDepartureTime(LocalDate scheduledDepartureTime) {
		this.scheduledDepartureTime = scheduledDepartureTime;
	}
	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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
	 * @return the incommingFlightCarrierCode
	 */
	public String getIncommingFlightCarrierCode() {
		return incommingFlightCarrierCode;
	}
	/**
	 * @param incommingFlightCarrierCode the incommingFlightCarrierCode to set
	 */
	public void setIncommingFlightCarrierCode(String incommingFlightCarrierCode) {
		this.incommingFlightCarrierCode = incommingFlightCarrierCode;
	}
	/**
	 * @return the incommingFlightDate
	 */
	public LocalDate getIncommingFlightDate() {
		return incommingFlightDate;
	}
	/**
	 * @param incommingFlightDate the incommingFlightDate to set
	 */
	public void setIncommingFlightDate(LocalDate incommingFlightDate) {
		this.incommingFlightDate = incommingFlightDate;
	}
	/**
	 * @return the incommingFlightNumber
	 */
	public String getIncommingFlightNumber() {
		return incommingFlightNumber;
	}
	/**
	 * @param incommingFlightNumber the incommingFlightNumber to set
	 */
	public void setIncommingFlightNumber(String incommingFlightNumber) {
		this.incommingFlightNumber = incommingFlightNumber;
	}

	/**
	 * @return the mailBagId
	 */
	public String getMailBagId() {
		return mailBagId;
	}
	/**
	 * @param mailBagId the mailBagId to set
	 */
	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}
	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return the weight
	 */
	/*public String getWeight() {
		return weight;
	}
	*//**
	 * @param weight the weight to set
	 *//*
	public void setWeight(String weight) {
		this.weight = weight;
	}*/
	public String getCarditAvailable() {
		return carditAvailable;
	}
	public void setCarditAvailable(String carditAvailable) {
		this.carditAvailable = carditAvailable;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



}
