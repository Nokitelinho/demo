/*
 * CN66DetailsForm.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2408
 * 
 */
public class CN66DetailsForm extends ScreenModel {
	private static final String BUNDLE = "cn66details";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private String carriageFrom;

	private String carriageTo;

	private String cn66Key;

	private String innerRowSelected;

	private String outerRowSelected;

	private String[] mailCategoryCode;

	private String[] origin;

	private String[] destination;

	private String[] flightNumber;

	private String[] carrierCode;

	private String[] flightDate;

	private String[] despatchDate;

	private String[] despatchNumber;

	private String[] receptacleSerialNo;
	private String[] hni;
	private String[] regInd;
	private String[] totalCpWeight;

	private String[] totalLcWeight;

	private String[] check;

	private String screenStatus;

	private String[] totalWeight;

	private String[] mailSubClass;

	private String[] operationFlag;

	private String[] sequenceNumber;
	
	
	
	/*added by indu for cr-164*/
	
	private String[] totalSalWeight;
	private String[] totalUldWeight;
	private String[] totalSvWeight;
	private String[] totalEmsWeight;
	private String[] rate;
	private String[] amount;
	
	private String blgCurCode;
	private String rowCount;
	
	/*added by A-3434 for cr-164*/
	
	private String showDsnPopUp;
	private String[] dsn;
	private Integer count;

	private String tempRowIndicator;
	
	private String focFlag;
	
	private String popupFlag;

	private String[] dsnIdr;
	/**
	 * @return the dsnIdr
	 */
	public String[] getDsnIdr() {
		return dsnIdr;
	}

	/**
	 * @param dsnIdr the dsnIdr to set
	 */
	public void setDsnIdr(String[] dsnIdr) {
		this.dsnIdr = dsnIdr;
	}

	/**
	 * @return the malSeqNum
	 */
	public String[] getMalSeqNum() {
		return malSeqNum;
	}

	/**
	 * @param malSeqNum the malSeqNum to set
	 */
	public void setMalSeqNum(String[] malSeqNum) {
		this.malSeqNum = malSeqNum;
	}

	private String[] malSeqNum;
	
	
	/**
	 * @return the tempRowIndicator
	 */
	public String getTempRowIndicator() {
		return tempRowIndicator;
	}

	/**
	 * @param tempRowIndicator the tempRowIndicator to set
	 */
	public void setTempRowIndicator(String tempRowIndicator) {
		this.tempRowIndicator = tempRowIndicator;
	}

	/**
	 * @return Returns the count.
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count The count to set.
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String[] getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the showDsnPopUp.
	 */
	public String getShowDsnPopUp() {
		return showDsnPopUp;
	}

	/**
	 * @param showDsnPopUp The showDsnPopUp to set.
	 */
	public void setShowDsnPopUp(String showDsnPopUp) {
		this.showDsnPopUp = showDsnPopUp;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public String getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount The rowCount to set.
	 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Returns the blgCurCode.
	 */
	public String getBlgCurCode() {
		return blgCurCode;
	}

	/**
	 * @param blgCurCode The blgCurCode to set.
	 */
	public void setBlgCurCode(String blgCurCode) {
		this.blgCurCode = blgCurCode;
	}

	/**
	 * @return Returns the screenid.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the product.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the subproduct.
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle
	 *            The bundle to set.
	 */
	public void setBundle(String bundle) {
		// this.bundle = bundle;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String carriageFrom) {
		this.carriageFrom = carriageFrom;
	}
	/**
	 * 	Getter for totalEmsWeight 
	 *	Added by : A-4809 on 21-Feb-2014
	 * 	Used for :
	 */
	public String[] getTotalEmsWeight() {
		return totalEmsWeight;
	}
	/**
	 *  @param totalEmsWeight the totalEmsWeight to set
	 * 	Setter for totalEmsWeight 
	 *	Added by : A-4809 on 21-Feb-2014
	 * 	Used for :
	 */
	public void setTotalEmsWeight(String[] totalEmsWeight) {
		this.totalEmsWeight = totalEmsWeight;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	public String getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the cn66Key.
	 */
	public String getCn66Key() {
		return cn66Key;
	}

	/**
	 * @param cn66Key
	 *            The cn66Key to set.
	 */
	public void setCn66Key(String cn66Key) {
		this.cn66Key = cn66Key;
	}

	/**
	 * @return Returns the innerRowSelected.
	 */
	public String getInnerRowSelected() {
		return innerRowSelected;
	}

	/**
	 * @param innerRowSelected
	 *            The innerRowSelected to set.
	 */
	public void setInnerRowSelected(String innerRowSelected) {
		this.innerRowSelected = innerRowSelected;
	}

	/**
	 * @return Returns the outerRowSelected.
	 */
	public String getOuterRowSelected() {
		return outerRowSelected;
	}

	/**
	 * @param outerRowSelected
	 *            The outerRowSelected to set.
	 */
	public void setOuterRowSelected(String outerRowSelected) {
		this.outerRowSelected = outerRowSelected;
	}

	/**
	 * @return Returns the category.
	 */
	public String[] getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String[] mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public String[] getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate
	 *            The despatchDate to set.
	 */
	public void setDespatchDate(String[] despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the despatchNumber.
	 */
	public String[] getDespatchNumber() {
		return despatchNumber;
	}

	/**
	 * @param despatchNumber
	 *            The despatchNumber to set.
	 */
	public void setDespatchNumber(String[] despatchNumber) {
		this.despatchNumber = despatchNumber;
	}
	/**
	 * @return the receptacleSerialNo
	 */
	public String[] getReceptacleSerialNo() {
		return receptacleSerialNo;
	}
	/**
	 * @param receptacleSerialNo the receptacleSerialNo to set
	 */
	public void setReceptacleSerialNo(String[] receptacleSerialNo) {
		this.receptacleSerialNo = receptacleSerialNo;
	}
	/**
	 * @return the hni
	 */
	public String[] getHni() {
		return hni;
	}
	/**
	 * @param hni the hni to set
	 */
	public void setHni(String[] hni) {
		this.hni = hni;
	}
	/**
	 * @return the regInd
	 */
	public String[] getRegInd() {
		return regInd;
	}
	/**
	 * @param regInd the regInd to set
	 */
	public void setRegInd(String[] regInd) {
		this.regInd = regInd;
	}

	/**
	 * @return Returns the destination.
	 */
	public String[] getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String[] getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the origin.
	 */
	public String[] getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the totalCpWeight.
	 */
	public String[] getTotalCpWeight() {
		return totalCpWeight;
	}

	/**
	 * @param totalCpWeight
	 *            The totalCpWeight to set.
	 */
	public void setTotalCpWeight(String[] totalCpWeight) {
		this.totalCpWeight = totalCpWeight;
	}

	/**
	 * @return Returns the totalLcWeight.
	 */
	public String[] getTotalLcWeight() {
		return totalLcWeight;
	}

	/**
	 * @param totalLcWeight
	 *            The totalLcWeight to set.
	 */
	public void setTotalLcWeight(String[] totalLcWeight) {
		this.totalLcWeight = totalLcWeight;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check
	 *            The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}

	/**
	 * @param screenStatus
	 *            The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	public String[] getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *            The totalWeight to set.
	 */
	public void setTotalWeight(String[] totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String[] getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String[] mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String[] getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String[] carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String[] getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public String[] getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(String[] sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the totalSalWeight.
	 */
	public String[] getTotalSalWeight() {
		return totalSalWeight;
	}

	/**
	 * @param totalSalWeight The totalSalWeight to set.
	 */
	public void setTotalSalWeight(String[] totalSalWeight) {
		this.totalSalWeight = totalSalWeight;
	}

	/**
	 * @return Returns the totalSvWeight.
	 */
	public String[] getTotalSvWeight() {
		return totalSvWeight;
	}

	/**
	 * @param totalSvWeight The totalSvWeight to set.
	 */
	public void setTotalSvWeight(String[] totalSvWeight) {
		this.totalSvWeight = totalSvWeight;
	}

	/**
	 * @return Returns the totalUldWeight.
	 */
	public String[] getTotalUldWeight() {
		return totalUldWeight;
	}

	/**
	 * @param totalUldWeight The totalUldWeight to set.
	 */
	public void setTotalUldWeight(String[] totalUldWeight) {
		this.totalUldWeight = totalUldWeight;
	}

	/**
	 * @return Returns the amount.
	 */
	public String[] getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the rate.
	 */
	public String[] getRate() {
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String[] rate) {
		this.rate = rate;
	}

	/**
	 * @return the focFlag
	 */
	public String getFocFlag() {
		return focFlag;
	}

	/**
	 * @param focFlag the focFlag to set
	 */
	public void setFocFlag(String focFlag) {
		this.focFlag = focFlag;
	}

	/**
	 * @return the popupFlag
	 */
	public String getPopupFlag() {
		return popupFlag;
	}

	/**
	 * @param popupFlag the popupFlag to set
	 */
	public void setPopupFlag(String popupFlag) {
		this.popupFlag = popupFlag;
	}

}
