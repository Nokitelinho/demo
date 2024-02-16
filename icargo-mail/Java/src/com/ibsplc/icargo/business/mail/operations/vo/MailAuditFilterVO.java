/*
 * MailAuditFilterVO.java Created on JUN 30, 2016
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
 * @author a-5991
 *
 */
public class MailAuditFilterVO extends AbstractVO {
     
     
    private String companyCode;
     
    private LocalDate txnFromDate;
 	private LocalDate txnToDate;
 	
    private String flightCarrierCode;
 	private String flightNumber;
 	private LocalDate flightDate;
 	private String assignPort;
 	private String containerNo;
 	
 	private int carrierId;
    private int legSerialNumber;
    private long flightSequenceNumber;
 	 
 	private String dsn;
	private String ooe;
	private String doe;
	private String category;
	private String subclass;
	private String mailClass;
	private int year;
     

  
	/**
     * @return companyCode
     */
    public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
     * @return Returns the code.
     */
	public String getAssignPort() {
		return assignPort;
	}
	public void setAssignPort(String assignPort) {
		this.assignPort = assignPort;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	public String getOoe() {
		return ooe;
	}
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	public String getSubclass() {
		return subclass;
	}
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public LocalDate getTxnFromDate() {
		return txnFromDate;
	}
	public void setTxnFromDate(LocalDate txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	public LocalDate getTxnToDate() {
		return txnToDate;
	}
	public void setTxnToDate(LocalDate txnToDate) {
		this.txnToDate = txnToDate;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
  }
