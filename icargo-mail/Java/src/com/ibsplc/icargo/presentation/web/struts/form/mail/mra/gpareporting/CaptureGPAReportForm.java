/*
 * CaptureGPAReportForm.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * 
 * @author A-2257 /*
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Feb 13, 2007 Meera Vijayan Initial draft
 * 
 * 
 * 
 */
public class CaptureGPAReportForm extends ScreenModel {

	private static final String BUNDLE = "capturegpareport";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	private String operationFlag;

	private String gpaCode;

	private String gpaName;

	private String country;

	private String frmDate;

	private String toDate;

	private String headerChk;

	private String rowchk;

	private String lastPageNum = "0";

	private String displayPage = "1";

	private String[] rowCount;

	private String fromScreen;

	private String mailStatus;

	private String currencyCode;

	private String showDsnPopUp;

	private String dsnFlag;

	/**
	 * Flag to identify if this popup is being used for adding a new row or
	 * modifying the existing, so that the add/delete link can be enabled
	 * accordingly
	 */
	private String popUpStatusFlag;

	/**
	 * To get rows selected from table
	 */
	private String selectedRows;

	/**
	 * variable for opening different screens and closing main screen
	 */
	private String ScreenFlag;

	/*
	 * For PopUp Screen
	 */

	private String date;

	private String originOE;

	private String destinationOE;

	private String mailBag;

	private String mailCategory;

	private String mailSubClass;

	private String year;

	private String dsn;

	private String noOfMailBag;

	private String weight;

	private String rate;

	private String amount;

	private Money amountMoney;

	private String tax;

	private String total;

	private String status;

	private String basistype;

	/**
	 * RSN
	 */
	private String receptacleSerialNum;

	/**
	 * RI
	 */
	private String registeredOrInsuredInd;

	/**
	 * HN
	 */
	private String highestNumberedRec;

	/*
	 * Fields for flight details
	 */
	private String[] flightCarrierCode;

	private String[] flightNumber;

	private String[] carriageFrom;

	private String[] carriageTo;

	/**
	 * For validating negative double values
	 */
	private String amtForValidation;

	/**
	 * For Sujjest combo
	 */
	private String gpaselect;

	/**
	 * to select next VO from sujjest combo
	 */
	private String displayPopUpPage;

	private String allProcessed;

	private String accEntryFlag;

	/**
	 * @return Returns the gpaselect.
	 */
	public String getGpaselect() {
		return gpaselect;
	}

	/**
	 * @param gpaselect
	 *            The gpaselect to set.
	 */
	public void setGpaselect(String gpaselect) {
		this.gpaselect = gpaselect;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @return SCREENID String
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @return PRODUCT String
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @return SUBPRODUCT String
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
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the frmDate.
	 */
	@DateFieldId(id="CaptureGPAReportDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFrmDate() {
		return frmDate;
	}

	/**
	 * @param frmDate
	 *            The frmDate to set.
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName
	 *            The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="CaptureGPAReportDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return ScreenFlag;
	}

	/**
	 * @param screenFlag
	 *            The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		ScreenFlag = screenFlag;
	}

	/**
	 * @return Returns the popUpStatusFlag.
	 */
	public String getPopUpStatusFlag() {
		return popUpStatusFlag;
	}

	/**
	 * @param popUpStatusFlag
	 *            The popUpStatusFlag to set.
	 */
	public void setPopUpStatusFlag(String popUpStatusFlag) {
		this.popUpStatusFlag = popUpStatusFlag;
	}

	/**
	 * @return Returns the displayPopUpPage.
	 */
	public String getDisplayPopUpPage() {
		return displayPopUpPage;
	}

	/**
	 * @param displayPopUpPage
	 *            The displayPopUpPage to set.
	 */
	public void setDisplayPopUpPage(String displayPopUpPage) {
		this.displayPopUpPage = displayPopUpPage;
	}

	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return Returns the destinationOE.
	 */
	public String getDestinationOE() {
		return destinationOE;
	}

	/**
	 * @param destinationOE
	 *            The destinationOE to set.
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return Returns the mailBag.
	 */
	public String getMailBag() {
		return mailBag;
	}

	/**
	 * @param mailBag
	 *            The mailBag to set.
	 */
	public void setMailBag(String mailBag) {
		this.mailBag = mailBag;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory
	 *            The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the noOfMailBag.
	 */
	public String getNoOfMailBag() {
		return noOfMailBag;
	}

	/**
	 * @param noOfMailBag
	 *            The noOfMailBag to set.
	 */
	public void setNoOfMailBag(String noOfMailBag) {
		this.noOfMailBag = noOfMailBag;
	}

	/**
	 * @return Returns the originOE.
	 */
	public String getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE
	 *            The originOE to set.
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the tax.
	 */
	public String getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            The tax to set.
	 */
	public void setTax(String tax) {
		this.tax = tax;
	}

	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return Returns the weight.
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows
	 *            The selectedRows to set.
	 */
	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the amtForValidation.
	 */
	public String getAmtForValidation() {
		return amtForValidation;
	}

	/**
	 * @param amtForValidation
	 *            The amtForValidation to set.
	 */
	public void setAmtForValidation(String amtForValidation) {
		this.amtForValidation = amtForValidation;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	public String[] getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String[] carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	public String[] getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String[] carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String[] getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String[] flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the headerChk.
	 */
	public String getHeaderChk() {
		return headerChk;
	}

	/**
	 * @param headerChk
	 *            The headerChk to set.
	 */
	public void setHeaderChk(String headerChk) {
		this.headerChk = headerChk;
	}

	/**
	 * @return Returns the rowchk.
	 */
	public String getRowchk() {
		return rowchk;
	}

	/**
	 * @param rowchk
	 *            The rowchk to set.
	 */
	public void setRowchk(String rowchk) {
		this.rowchk = rowchk;
	}

	/**
	 * @return Returns the basistype.
	 */
	public String getBasistype() {
		return basistype;
	}

	/**
	 * @param basistype
	 *            The basistype to set.
	 */
	public void setBasistype(String basistype) {
		this.basistype = basistype;
	}

	/**
	 * @return Returns the highestNumberedRec.
	 */
	public String getHighestNumberedRec() {
		return highestNumberedRec;
	}

	/**
	 * @param highestNumberedRec
	 *            The highestNumberedRec to set.
	 */
	public void setHighestNumberedRec(String highestNumberedRec) {
		this.highestNumberedRec = highestNumberedRec;
	}

	/**
	 * @return Returns the receptacleSerialNum.
	 */
	public String getReceptacleSerialNum() {
		return receptacleSerialNum;
	}

	/**
	 * @param receptacleSerialNum
	 *            The receptacleSerialNum to set.
	 */
	public void setReceptacleSerialNum(String receptacleSerialNum) {
		this.receptacleSerialNum = receptacleSerialNum;
	}

	/**
	 * @return Returns the registeredOrInsuredInd.
	 */
	public String getRegisteredOrInsuredInd() {
		return registeredOrInsuredInd;
	}

	/**
	 * @param registeredOrInsuredInd
	 *            The registeredOrInsuredInd to set.
	 */
	public void setRegisteredOrInsuredInd(String registeredOrInsuredInd) {
		this.registeredOrInsuredInd = registeredOrInsuredInd;
	}

	/**
	 * @return Returns the allProcessed.
	 */
	public String getAllProcessed() {
		return allProcessed;
	}

	/**
	 * @param allProcessed
	 *            The allProcessed to set.
	 */
	public void setAllProcessed(String allProcessed) {
		this.allProcessed = allProcessed;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen
	 *            The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public String[] getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            The rowCount to set.
	 */
	public void setRowCount(String[] rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Returns the mailStatus.
	 */
	public String getMailStatus() {
		return mailStatus;
	}

	/**
	 * @param mailStatus
	 *            The mailStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return Returns the amountMoney.
	 */
	public Money getAmountMoney() {
		return amountMoney;
	}

	/**
	 * @param amountMoney
	 *            The amountMoney to set.
	 */
	public void setAmountMoney(Money amountMoney) {
		this.amountMoney = amountMoney;
	}

	public String getAccEntryFlag() {
		return accEntryFlag;
	}

	public void setAccEntryFlag(String accEntryFlag) {
		this.accEntryFlag = accEntryFlag;
	}

	/**
	 * @return the showDsnPopUp
	 */
	public String getShowDsnPopUp() {
		return showDsnPopUp;
	}

	/**
	 * @param showDsnPopUp
	 *            the showDsnPopUp to set
	 */
	public void setShowDsnPopUp(String showDsnPopUp) {
		this.showDsnPopUp = showDsnPopUp;
	}

	/**
	 * @return the dsnFlag
	 */
	public String getDsnFlag() {
		return dsnFlag;
	}

	/**
	 * @param dsnFlag
	 *            the dsnFlag to set
	 */
	public void setDsnFlag(String dsnFlag) {
		this.dsnFlag = dsnFlag;
	}

}