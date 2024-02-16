/*
 * CaptureCN51Form.java Created on Feb 12,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author Ruby Abraham
 * Form for Capture CN 51 screen.
 *
 * Revision History
 *
 * Version Date Author Description
 *
 * 0.1 Feb 12, 2007 Ruby Abraham Initial draft
 */

public class CaptureCN51Form extends ScreenModel {

	private static final String BUNDLE = "capturecn51resources";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	/**
	 * Clearance Period
	 */
	private String clearancePeriod;

	private String airlineCode;

	private String cn51Period;

	private String invoiceRefNo;

	private String category;

	private String carriageFrom;

	private String carriageTo;

	private String billingType;

	private String carriagesFrom;

	private String carriagesTo;

	private String categories;

	private String wtLCAO;

	private String rate;

	private String wtCP;

	private String wtSal;

	private String wtUld;

	private String wtSv;

	private String wtEms;

	private String totalAmount;

	private boolean isSelectAll;

	private String[] select;

	private String[] selectedElements;

	private String operationFlag;

	private String sequenceNumber;

	private String linkStatusFlag="disable";

	private String status;

	private String statusFlag;

	// Variable for coming from other screens

	private String screenFlag;

	private String selectedRow;

	// Variable for checking from which screen this Form One screen is coming

	private String closeFlag;

	private String cn51Status;

	private String deleteFlag;

	private String cn66Flag="";


	// Added by cr-AirNZ179
	private Double netWeight=0.00;
	private Money netChargeMoney;
	private String netCharge="0.00";
	private String blgCurCode;
	private Double netLC=0.00;

	private Double netCP=0.00;

	private Double netSal=0.00;

	private Double netUld=0.00;

	private Double netSV=0.00;

	private Double netEMS=0.00;
	private String rowCount;
	
	// Added for BUG : MRA240

	private String lastPageNum="0";
	private String displayPage="1";
	private String totalRecords;
	private String CurrentPageNum="1";
	
	private String lastPageNumber = "0";
	private String displayPageNum = "1";
	private String screenFlg;
	/**
	 * For confirmation messages;
	 */
	private String currentDialogOption;

	private String currentDialogId;
	
	private String popUpCloseFlag;
	
	private Money netSummaryMoney;
	/**
	 * @author a-3447
	 */
	private String netChargeMoneyDisp;
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
	 * @return Returns the netCharge.
	 */
	public String getNetCharge() {
		return netCharge;
	}

	/**
	 * @param netCharge The netCharge to set.
	 */
	public void setNetCharge(String netCharge) {
		this.netCharge = netCharge;
	}

	/**
	 * @return Returns the netChargeMoney.
	 */
	public Money getNetChargeMoney() {
		return netChargeMoney;
	}

	/**
	 * @param netChargeMoney The netChargeMoney to set.
	 */
	public void setNetChargeMoney(Money netChargeMoney) {
		this.netChargeMoney = netChargeMoney;
	}

	/**
	 * @return Returns the cn66Flag.
	 */
	public String getCn66Flag() {
		return cn66Flag;
	}

	/**
	 * @param cn66Flag The cn66Flag to set.
	 */
	public void setCn66Flag(String cn66Flag) {
		this.cn66Flag = cn66Flag;
	}

	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the PRODUCT.
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
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
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * 	Getter for wtEms 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public String getWtEms() {
		return wtEms;
	}
	/**
	 * 	Getter for netEMS 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public Double getNetEMS() {
		return netEMS;
	}
	/**
	 *  @param wtEms the wtEms to set
	 * 	Setter for wtEms 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public void setWtEms(String wtEms) {
		this.wtEms = wtEms;
	}
	/**
	 *  @param netEMS the netEMS to set
	 * 	Setter for netEMS 
	 *	Added by : A-4809 on 17-Feb-2014
	 * 	Used for :
	 */
	public void setNetEMS(Double netEMS) {
		this.netEMS = netEMS;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the cn51Period.
	 */
	public String getCn51Period() {
		return cn51Period;
	}

	/**
	 * @param cn51Period
	 *            The cn51Period to set.
	 */
	public void setCn51Period(String cn51Period) {
		this.cn51Period = cn51Period;
	}

	/**
	 * @return Returns the invoiceRefNo.
	 */
	public String getInvoiceRefNo() {
		return invoiceRefNo;
	}

	/**
	 * @param invoiceRefNo
	 *            The invoiceRefNo to set.
	 */
	public void setInvoiceRefNo(String invoiceRefNo) {
		this.invoiceRefNo = invoiceRefNo;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return Returns the carriagesFrom.
	 */
	public String getCarriagesFrom() {
		return carriagesFrom;
	}

	/**
	 * @param carriagesFrom
	 *            The carriagesFrom to set.
	 */
	public void setCarriagesFrom(String carriagesFrom) {
		this.carriagesFrom = carriagesFrom;
	}

	/**
	 * @return Returns the carriagesTo.
	 */
	public String getCarriagesTo() {
		return carriagesTo;
	}

	/**
	 * @param carriagesTo
	 *            The carriagesTo to set.
	 */
	public void setCarriagesTo(String carriagesTo) {
		this.carriagesTo = carriagesTo;
	}




	/**
	 * @return Returns the categories.
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            The categories to set.
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}



	/**
	 * @return Returns the wtCP.
	 */
	public String getWtCP() {
		return wtCP;
	}

	/**
	 * @param wtCP
	 *            The wtCP to set.
	 */
	public void setWtCP(String wtCP) {
		this.wtCP = wtCP;
	}

	/**
	 * @return Returns the wtLCAO.
	 */
	public String getWtLCAO() {
		return wtLCAO;
	}

	/**
	 * @param wtLCAO
	 *            The wtLCAO to set.
	 */
	public void setWtLCAO(String wtLCAO) {
		this.wtLCAO = wtLCAO;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            The totalAmount to set.
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Returns the isSelectAll.
	 */
	public boolean isSelectAll() {
		return this.isSelectAll;
	}

	/**
	 * @param isSelectAll
	 *            The isSelectAll to set.
	 */
	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return this.select;
	}

	/**
	 * @param select
	 *            The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 *
	 * @return String
	 */
	public String getLinkStatusFlag() {
		return linkStatusFlag;
	}

	/**
	 *
	 * @param linkStatusFlag
	 */
	public void setLinkStatusFlag(String linkStatusFlag) {
		this.linkStatusFlag = linkStatusFlag;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag
	 *            The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	/**
	 * @return Returns the selectedRow.
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow
	 *            The selectedRow to set.
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag
	 *            The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the selectedElements.
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements
	 *            The selectedElements to set.
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag
	 *            The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the billingType.
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType
	 *            The billingType to set.
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
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
	 * @return Returns the cn51Status.
	 */
	public String getCn51Status() {
		return cn51Status;
	}

	/**
	 * @param cn51Status
	 *            The cn51Status to set.
	 */
	public void setCn51Status(String cn51Status) {
		this.cn51Status = cn51Status;
	}

	/**
	 * @return Returns the deleteFlag.
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            The deleteFlag to set.
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the wtSv.
	 */
	public String getWtSv() {
		return wtSv;
	}

	/**
	 * @param wtSv The wtSv to set.
	 */
	public void setWtSv(String wtSv) {
		this.wtSv = wtSv;
	}

	/**
	 * @return Returns the wtSal.
	 */
	public String getWtSal() {
		return wtSal;
	}

	/**
	 * @param wtSal The wtSal to set.
	 */
	public void setWtSal(String wtSal) {
		this.wtSal = wtSal;
	}

	/**
	 * @return Returns the wtUld.
	 */
	public String getWtUld() {
		return wtUld;
	}

	/**
	 * @param wtUld The wtUld to set.
	 */
	public void setWtUld(String wtUld) {
		this.wtUld = wtUld;
	}

	/**
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the netCP.
	 */

	/**
	 * @return the displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return the lastPageNum
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum the lastPageNum to set
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return the totalRecords
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the currentPageNum
	 */
	public String getCurrentPageNum() {
		return CurrentPageNum;
	}

	/**
	 * @param currentPageNum the currentPageNum to set
	 */
	public void setCurrentPageNum(String currentPageNum) {
		CurrentPageNum = currentPageNum;
	}

	/**
	 * @return the popUpCloseFlag
	 */
	public String getPopUpCloseFlag() {
		return popUpCloseFlag;
	}

	/**
	 * @param popUpCloseFlag the popUpCloseFlag to set
	 */
	public void setPopUpCloseFlag(String popUpCloseFlag) {
		this.popUpCloseFlag = popUpCloseFlag;
	}

	public Double getNetCP() {
		return netCP;
	}

	public void setNetCP(Double netCP) {
		this.netCP = netCP;
	}

	public Double getNetLC() {
		return netLC;
	}

	public void setNetLC(Double netLC) {
		this.netLC = netLC;
	}

	public Double getNetSal() {
		return netSal;
	}

	public void setNetSal(Double netSal) {
		this.netSal = netSal;
	}

	public Double getNetSV() {
		return netSV;
	}

	public void setNetSV(Double netSV) {
		this.netSV = netSV;
	}

	public Double getNetUld() {
		return netUld;
	}

	public void setNetUld(Double netUld) {
		this.netUld = netUld;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}  

    /**
	 * @return the displayPageNum
	 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}

	/**
	 * @param displayPageNum the displayPageNum to set
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
	 * @param lastPageNumber the lastPageNumber to set
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return the screenFlg
	 */
	public String getScreenFlg() {
		return screenFlg;
	}

	/**
	 * @param screenFlg the screenFlg to set
	 */
	public void setScreenFlg(String screenFlg) {
		this.screenFlg = screenFlg;
	}

	/**
	 * @return the currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId the currentDialogId to set
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return the netSummaryMoney
	 */
	public Money getNetSummaryMoney() {
		return netSummaryMoney;
	}

	/**
	 * @param netSummaryMoney the netSummaryMoney to set
	 */
	public void setNetSummaryMoney(Money netSummaryMoney) {
		this.netSummaryMoney = netSummaryMoney;
	}
	/**
	 * @return the netChargeMoneyDisp
	 */
	public String getNetChargeMoneyDisp() {
		return netChargeMoneyDisp;
	}

	/**
	 * @param netChargeMoneyDisp the netChargeMoneyDisp to set
	 */
	public void setNetChargeMoneyDisp(String netChargeMoneyDisp) {
		this.netChargeMoneyDisp = netChargeMoneyDisp;
	}
}
