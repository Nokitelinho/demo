/*
 POMailStatementVO.java Created on June 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo.national;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */

public class POMailStatementVO extends AbstractVO {

	public static final String MODULE = "mail";

	public static final String SUBMODULE = "operations";

	private String companyCode;
	private String consignmentNumber;
	private LocalDate consignmentDate;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String sector;
	private String remarks;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int segSerNum;
	private int carrierId;
	private String dsnNumber;
	private String originOE;
	private String destinationOE;

	private String mailSubClass;
	private String mailCategoryCode;
	private int year;
	private String uldNumber;
	private String containerNumber;
	private int csgSeqnum;
	private String poaCode;
	// Added by A-4810 as part of bug-icrd:15155
	private String operationFlag;
	private String flightNumberToDisplay;

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	private int pieces;
	private double weight;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the flightSequenceNumber
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return the segSerNum
	 */
	public int getSegSerNum() {
		return segSerNum;
	}

	/**
	 * @param segSerNum
	 *            the segSerNum to set
	 */
	public void setSegSerNum(int segSerNum) {
		this.segSerNum = segSerNum;
	}

	/**
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return the dsnNumber
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber
	 *            the dsnNumber to set
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return the originOE
	 */
	public String getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE
	 *            the originOE to set
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return the destinationOE
	 */
	public String getDestinationOE() {
		return destinationOE;
	}

	/**
	 * @param destinationOE
	 *            the destinationOE to set
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}

	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the containerNumber
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            the containerNumber to set
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the csgSeqnum
	 */
	public int getCsgSeqnum() {
		return csgSeqnum;
	}

	/**
	 * @param csgSeqnum
	 *            the csgSeqnum to set
	 */
	public void setCsgSeqnum(int csgSeqnum) {
		this.csgSeqnum = csgSeqnum;
	}

	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the flightNumberToDisplay
	 */
	public String getFlightNumberToDisplay() {
		return flightNumberToDisplay;
	}

	/**
	 * @param flightNumberToDisplay
	 *            the flightNumberToDisplay to set
	 */
	public void setFlightNumberToDisplay(String flightNumberToDisplay) {
		this.flightNumberToDisplay = flightNumberToDisplay;
	}

}
