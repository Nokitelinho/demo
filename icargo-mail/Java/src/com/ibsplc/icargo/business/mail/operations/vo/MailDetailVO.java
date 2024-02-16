/*
 * MailDetailVO.java Created on Jun 30, 2016
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
 */
public class MailDetailVO extends AbstractVO {

	private String companyCode;
	private String mailId;
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubclass;
	private int year;
	
	private int carrierId;
	
	private String flightNumber;
	
	private long flightSequenceNumber;
	
	private int segmentSerialNumber;
	/*
	 * ULDNumber for ULD and barrow number for Bulk
	 */
	private String containerNumber;
	private String scannedPort;
	//private double weight;
	private Measure weight;//added by A-7371
	private String mailClass;
	private String uldNumber;
	private String ubrNumber;
	private String controlDocumentNumber;
	private String originPACode ;
	private String destinationPACode ;
	private String originCity;
	private String destinationCity;
	/* Added for Report generation */
	private String originAirport;
	private String destinationAirport;
	private String pou; 
	
	private String destnAirportName;
	private LocalDate rcvDate;
	/*
	 * Added By Karthick V as the part of the Optimistic Locking Mechanism
	 * 
	 * 
	 */
	
	private LocalDate malUldSegLastUpdateTime;
	
	private String lastUpdateUser;
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
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public LocalDate getMalUldSegLastUpdateTime() {
		return malUldSegLastUpdateTime;
	}
	public void setMalUldSegLastUpdateTime(LocalDate malUldSegLastUpdateTime) {
		this.malUldSegLastUpdateTime = malUldSegLastUpdateTime;
	}
	/**
	 * @return Returns the destinationAirport.
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}
	/**
	 * @param destinationAirport The destinationAirport to set.
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	/**
	 * @return Returns the originAirport.
	 */
	public String getOriginAirport() {
		return originAirport;
	}
	/**
	 * @param originAirport The originAirport to set.
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}
	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}
	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @return Returns the controlDocumentNumber.
	 */
	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}
	/**
	 * @param controlDocumentNumber The controlDocumentNumber to set.
	 */
	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}
	/**
	 * @return Returns the ubrNumber.
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}
	/**
	 * @param ubrNumber The ubrNumber to set.
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
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
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	/**
	 * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	 * 	Getter for rcvDate 
	 *	Added by : a-4809 on Apr 2, 2014
	 * 	Used for :
	 */
	public LocalDate getRcvDate() {
		return rcvDate;
	}
	/**
	 *  @param rcvDate the rcvDate to set
	 * 	Setter for rcvDate 
	 *	Added by : a-4809 on Apr 2, 2014
	 * 	Used for :
	 */
	public void setRcvDate(LocalDate rcvDate) {
		this.rcvDate = rcvDate;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return Returns the mailId.
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId The mailId to set.
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}
	/**
	 * @param originOfficeOfExchange The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
	/**
	 * @return Returns the scannedPort.
	 */
	public String getScannedPort() {
		return scannedPort;
	}
	/**
	 * @param scannedPort The scannedPort to set.
	 */
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}
	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * @return Returns the weight.
	 */
	/*public double getWeight() {
		return weight;
	}
	*//**
	 * @param weight The weight to set.
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/
	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the destinationPACode.
	 */
	public String getDestinationPACode() {
		return destinationPACode;
	}
	/**
	 * @param destinationPACode The destinationPACode to set.
	 */
	public void setDestinationPACode(String destinationPACode) {
		this.destinationPACode = destinationPACode;
	}
	/**
	 * @return Returns the originPACode.
	 */
	public String getOriginPACode() {
		return originPACode;
	}
	/**
	 * @param originPACode The originPACode to set.
	 */
	public void setOriginPACode(String originPACode) {
		this.originPACode = originPACode;
	}
	/**
	 * @return Returns the destinationCity.
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity The destinationCity to set.
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	/**
	 * @return Returns the originCity.
	 */
	public String getOriginCity() {
		return originCity;
	}
	/**
	 * @param originCity The originCity to set.
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	/**
	 * @return Returns the destnAirportName.
	 */
	public String getDestnAirportName() {
		return destnAirportName;
	}
	/**
	 * @param destnAirportName The destnAirportName to set.
	 */
	public void setDestnAirportName(String destnAirportName) {
		this.destnAirportName = destnAirportName;
	}
	        
}
