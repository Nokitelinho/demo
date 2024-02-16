/*
 * OffloadForm.java Created on June 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1861
 *
 */
public class OffloadForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.offload";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "offloadResources";

	private String flightCarrierCode;
	private String flightNumber;
	private String date;
	private String departurePort;
	private String type;
	private String containerNumber;
	private String containerType;
	private String despatchSn;
	private String originOE;
	private String destnOE;
	private String mailClass;
	private String year;
	private String mailbagRsn;
	private String mailbagOriginOE;
	private String mailbagDestnOE;
	private String mailbagCategory;
	private String mailbagSubClass;
	private String mailbagYear;
	private String mailbagDsn;

	private String[] dsnOffloadReason;
	private String[] dsnOffloadRemarks;
	private String[] contOffloadReason;
	private String[] contOffloadRemarks;
	private String[] mailbagOffloadReason;
	private String[] mailbagOffloadRemarks;

	private String uldCheckAll;
	private String[] uldSubCheck;
	private String dsnCheckAll;
	private String[] dsnSubCheck;
	private String mailbagCheckAll;
	private String[] mailbagSubCheck;

	private String status;
	private String mode;
	private String flightStatus;
	private String fromScreen;
	private String closeflight;
	private String clearFlag="N";

	private String lastPageNumber = "0";

	private String displayPageNum = "1";
	private String warningFlag;
	private String warningOveride;
	private String reListFlag;
	private String mailbagId;//Added as part of ICRD-205027


	

	public String getReListFlag() {
		return reListFlag;
	}

	public void setReListFlag(String reListFlag) {
		this.reListFlag = reListFlag;
	}

	public String getClearFlag() {
		return clearFlag;
	}

	public void setClearFlag(String clearFlag) {
		this.clearFlag = clearFlag;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the flightStatus.
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus The flightStatus to set.
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the dsnCheckAll.
	 */
	public String getDsnCheckAll() {
		return dsnCheckAll;
	}

	/**
	 * @param dsnCheckAll The dsnCheckAll to set.
	 */
	public void setDsnCheckAll(String dsnCheckAll) {
		this.dsnCheckAll = dsnCheckAll;
	}

	/**
	 * @return Returns the dsnSubCheck.
	 */
	public String[] getDsnSubCheck() {
		return dsnSubCheck;
	}

	/**
	 * @param dsnSubCheck The dsnSubCheck to set.
	 */
	public void setDsnSubCheck(String[] dsnSubCheck) {
		this.dsnSubCheck = dsnSubCheck;
	}

	/**
	 * @return Returns the mailbagCheckAll.
	 */
	public String getMailbagCheckAll() {
		return mailbagCheckAll;
	}

	/**
	 * @param mailbagCheckAll The mailbagCheckAll to set.
	 */
	public void setMailbagCheckAll(String mailbagCheckAll) {
		this.mailbagCheckAll = mailbagCheckAll;
	}

	/**
	 * @return Returns the mailbagSubCheck.
	 */
	public String[] getMailbagSubCheck() {
		return mailbagSubCheck;
	}

	/**
	 * @param mailbagSubCheck The mailbagSubCheck to set.
	 */
	public void setMailbagSubCheck(String[] mailbagSubCheck) {
		this.mailbagSubCheck = mailbagSubCheck;
	}

	/**
	 * @return Returns the uldCheckAll.
	 */
	public String getUldCheckAll() {
		return uldCheckAll;
	}

	/**
	 * @param uldCheckAll The uldCheckAll to set.
	 */
	public void setUldCheckAll(String uldCheckAll) {
		this.uldCheckAll = uldCheckAll;
	}

	/**
	 * @return Returns the uldSubCheck.
	 */
	public String[] getUldSubCheck() {
		return uldSubCheck;
	}

	/**
	 * @param uldSubCheck The uldSubCheck to set.
	 */
	public void setUldSubCheck(String[] uldSubCheck) {
		this.uldSubCheck = uldSubCheck;
	}

	/**
	 * @return Returns the contOffloadReason.
	 */
	public String[] getContOffloadReason() {
		return contOffloadReason;
	}

	/**
	 * @param contOffloadReason The contOffloadReason to set.
	 */
	public void setContOffloadReason(String[] contOffloadReason) {
		this.contOffloadReason = contOffloadReason;
	}

	/**
	 * @return Returns the contOffloadRemarks.
	 */
	public String[] getContOffloadRemarks() {
		return contOffloadRemarks;
	}

	/**
	 * @param contOffloadRemarks The contOffloadRemarks to set.
	 */
	public void setContOffloadRemarks(String[] contOffloadRemarks) {
		this.contOffloadRemarks = contOffloadRemarks;
	}

	/**
	 * @return Returns the dsnOffloadReason.
	 */
	public String[] getDsnOffloadReason() {
		return dsnOffloadReason;
	}

	/**
	 * @param dsnOffloadReason The dsnOffloadReason to set.
	 */
	public void setDsnOffloadReason(String[] dsnOffloadReason) {
		this.dsnOffloadReason = dsnOffloadReason;
	}

	/**
	 * @return Returns the dsnOffloadRemarks.
	 */
	public String[] getDsnOffloadRemarks() {
		return dsnOffloadRemarks;
	}

	/**
	 * @param dsnOffloadRemarks The dsnOffloadRemarks to set.
	 */
	public void setDsnOffloadRemarks(String[] dsnOffloadRemarks) {
		this.dsnOffloadRemarks = dsnOffloadRemarks;
	}

	/**
	 * @return Returns the mailbagOffloadReason.
	 */
	public String[] getMailbagOffloadReason() {
		return mailbagOffloadReason;
	}

	/**
	 * @param mailbagOffloadReason The mailbagOffloadReason to set.
	 */
	public void setMailbagOffloadReason(String[] mailbagOffloadReason) {
		this.mailbagOffloadReason = mailbagOffloadReason;
	}

	/**
	 * @return Returns the mailbagOffloadRemarks.
	 */
	public String[] getMailbagOffloadRemarks() {
		return mailbagOffloadRemarks;
	}

	/**
	 * @param mailbagOffloadRemarks The mailbagOffloadRemarks to set.
	 */
	public void setMailbagOffloadRemarks(String[] mailbagOffloadRemarks) {
		this.mailbagOffloadRemarks = mailbagOffloadRemarks;
	}

	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return Returns the destnOE.
	 */
	public String getDestnOE() {
		return destnOE;
	}

	/**
	 * @param destnOE The destnOE to set.
	 */
	public void setDestnOE(String destnOE) {
		this.destnOE = destnOE;
	}

	/**
	 * @return Returns the despatchSn.
	 */
	public String getDespatchSn() {
		return despatchSn;
	}

	/**
	 * @param despatchSn The despatchSn to set.
	 */
	public void setDespatchSn(String despatchSn) {
		this.despatchSn = despatchSn;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the mailbagCategory.
	 */
	public String getMailbagCategory() {
		return mailbagCategory;
	}

	/**
	 * @param mailbagCategory The mailbagCategory to set.
	 */
	public void setMailbagCategory(String mailbagCategory) {
		this.mailbagCategory = mailbagCategory;
	}

	/**
	 * @return Returns the mailbagDestnOE.
	 */
	public String getMailbagDestnOE() {
		return mailbagDestnOE;
	}

	/**
	 * @param mailbagDestnOE The mailbagDestnOE to set.
	 */
	public void setMailbagDestnOE(String mailbagDestnOE) {
		this.mailbagDestnOE = mailbagDestnOE;
	}

	/**
	 * @return Returns the mailbagDsn.
	 */
	public String getMailbagDsn() {
		return mailbagDsn;
	}

	/**
	 * @param mailbagDsn The mailbagDsn to set.
	 */
	public void setMailbagDsn(String mailbagDsn) {
		this.mailbagDsn = mailbagDsn;
	}

	/**
	 * @return Returns the mailbagOriginOE.
	 */
	public String getMailbagOriginOE() {
		return mailbagOriginOE;
	}

	/**
	 * @param mailbagOriginOE The mailbagOriginOE to set.
	 */
	public void setMailbagOriginOE(String mailbagOriginOE) {
		this.mailbagOriginOE = mailbagOriginOE;
	}

	/**
	 * @return Returns the mailbagRsn.
	 */
	public String getMailbagRsn() {
		return mailbagRsn;
	}

	/**
	 * @param mailbagRsn The mailbagRsn to set.
	 */
	public void setMailbagRsn(String mailbagRsn) {
		this.mailbagRsn = mailbagRsn;
	}

	/**
	 * @return Returns the mailbagSubClass.
	 */
	public String getMailbagSubClass() {
		return mailbagSubClass;
	}

	/**
	 * @param mailbagSubClass The mailbagSubClass to set.
	 */
	public void setMailbagSubClass(String mailbagSubClass) {
		this.mailbagSubClass = mailbagSubClass;
	}

	/**
	 * @return Returns the mailbagYear.
	 */
	public String getMailbagYear() {
		return mailbagYear;
	}

	/**
	 * @param mailbagYear The mailbagYear to set.
	 */
	public void setMailbagYear(String mailbagYear) {
		this.mailbagYear = mailbagYear;
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
	 * @return Returns the originOE.
	 */
	public String getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE The originOE to set.
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return the displayPageNum
	 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}

	/**
	 * @param displayPageNum
	 *            the displayPageNum to set
	 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	/**
	 * @return the lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber
	 *            the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return Returns the closeflight.
	 */
	public String getCloseflight() {
		return this.closeflight;
	}

	/**
	 * @param closeflight The closeflight to set.
	 */
	public void setCloseflight(String closeflight) {
		this.closeflight = closeflight;
	}

	/**
	 * @return the warningFlag
	 */
	public String getWarningFlag() {
		return warningFlag;
	}

	/**
	 * @param warningFlag the warningFlag to set
	 */
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

	/**
	 * @return the warningOveride
	 */
	public String getWarningOveride() {
		return warningOveride;
	}

	/**
	 * @param warningOveride the warningOveride to set
	 */
	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 22-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

}
