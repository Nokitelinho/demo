/*
 *  CaptureMailFormOneForm.java Created on June 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-2391
 *
 */
public class CaptureMailFormOneForm extends ScreenModel{

	private static final String BUNDLE = "captureFormOnebundle";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";
	private String clearancePeriod;
	private String airlineCode;
	private String airlineNo;
	private String invoiceStatus;
	private String[] operationFlag;
	private String blgCurCode;
	private String[] invNum;
	private String[] invDate;
	private String[] curList;
	private String[] misAmt;
	private String[] exgRate;
	private String[] amtUsd;
	private String[] invStatus;
	private String[] invF1Status;
	private String[] checkBox;
	
	private Money netMiscAmountMoney;
	private Money netUsdAmountMoney;
	private String netMiscAmount;
	private String netUsdAmount;
	private String rowCount;
	private String formOneOpFlag;
	private String listFlag;
	
	private String selectedRow;
	private String buttonFlag;
	
	private String airlineInvalidFlag;
	private String linkDisable;
	private String processedFlag;
	/**
	 * @return the linkDisable
	 */
	public String getLinkDisable() {
		return linkDisable;
	}

	/**
	 * @param linkDisable the linkDisable to set
	 */
	public void setLinkDisable(String linkDisable) {
		this.linkDisable = linkDisable;
	}

	/**
	 * @return the airlineInvalidFlag
	 */
	public String getAirlineInvalidFlag() {
		return airlineInvalidFlag;
	}

	/**
	 * @param airlineInvalidFlag the airlineInvalidFlag to set
	 */
	public void setAirlineInvalidFlag(String airlineInvalidFlag) {
		this.airlineInvalidFlag = airlineInvalidFlag;
	}

	/**
	 * @return the buttonFlag
	 */
	public String getButtonFlag() {
		return buttonFlag;
	}

	/**
	 * @param buttonFlag the buttonFlag to set
	 */
	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return Returns the formOneOpFlag.
	 */
	public String getFormOneOpFlag() {
		return formOneOpFlag;
	}

	/**
	 * @param formOneOpFlag The formOneOpFlag to set.
	 */
	public void setFormOneOpFlag(String formOneOpFlag) {
		this.formOneOpFlag = formOneOpFlag;
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
	 * @return Returns the checkBox.
	 */
	public String[] getCheckBox() {
		return checkBox;
	}

	/**
	 * @param checkBox The checkBox to set.
	 */
	public void setCheckBox(String[] checkBox) {
		this.checkBox = checkBox;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineNo.
	 */
	public String getAirlineNo() {
		return airlineNo;
	}

	/**
	 * @param airlineNo The airlineNo to set.
	 */
	public void setAirlineNo(String airlineNo) {
		this.airlineNo = airlineNo;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the invoiceStatus.
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus The invoiceStatus to set.
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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
	 * @return Returns the amtUsd.
	 */
	public String[] getAmtUsd() {
		return amtUsd;
	}

	/**
	 * @param amtUsd The amtUsd to set.
	 */
	public void setAmtUsd(String[] amtUsd) {
		this.amtUsd = amtUsd;
	}

	/**
	 * @return Returns the curList.
	 */
	public String[] getCurList() {
		return curList;
	}

	/**
	 * @param curList The curList to set.
	 */
	public void setCurList(String[] curList) {
		this.curList = curList;
	}

	/**
	 * @return Returns the exgRate.
	 */
	public String[] getExgRate() {
		return exgRate;
	}

	/**
	 * @param exgRate The exgRate to set.
	 */
	public void setExgRate(String[] exgRate) {
		this.exgRate = exgRate;
	}

	/**
	 * @return Returns the invDate.
	 */
	public String[] getInvDate() {
		return invDate;
	}

	/**
	 * @param invDate The invDate to set.
	 */
	public void setInvDate(String[] invDate) {
		this.invDate = invDate;
	}

	/**
	 * @return Returns the invF1Status.
	 */
	public String[] getInvF1Status() {
		return invF1Status;
	}

	/**
	 * @param invF1Status The invF1Status to set.
	 */
	public void setInvF1Status(String[] invF1Status) {
		this.invF1Status = invF1Status;
	}

	/**
	 * @return Returns the invNum.
	 */
	public String[] getInvNum() {
		return invNum;
	}

	/**
	 * @param invNum The invNum to set.
	 */
	public void setInvNum(String[] invNum) {
		this.invNum = invNum;
	}

	/**
	 * @return Returns the invStatus.
	 */
	public String[] getInvStatus() {
		return invStatus;
	}

	/**
	 * @param invStatus The invStatus to set.
	 */
	public void setInvStatus(String[] invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * @return Returns the misAmt.
	 */
	public String[] getMisAmt() {
		return misAmt;
	}

	/**
	 * @param misAmt The misAmt to set.
	 */
	public void setMisAmt(String[] misAmt) {
		this.misAmt = misAmt;
	}

	/**
	 * @return Returns the netMiscAmount.
	 */
	public String getNetMiscAmount() {
		return netMiscAmount;
	}

	/**
	 * @param netMiscAmount The netMiscAmount to set.
	 */
	public void setNetMiscAmount(String netMiscAmount) {
		this.netMiscAmount = netMiscAmount;
	}

	/**
	 * @return Returns the netMiscAmountMoney.
	 */
	public Money getNetMiscAmountMoney() {
		return netMiscAmountMoney;
	}

	/**
	 * @param netMiscAmountMoney The netMiscAmountMoney to set.
	 */
	public void setNetMiscAmountMoney(Money netMiscAmountMoney) {
		this.netMiscAmountMoney = netMiscAmountMoney;
	}

	/**
	 * @return Returns the netUsdAmount.
	 */
	public String getNetUsdAmount() {
		return netUsdAmount;
	}

	/**
	 * @param netUsdAmount The netUsdAmount to set.
	 */
	public void setNetUsdAmount(String netUsdAmount) {
		this.netUsdAmount = netUsdAmount;
	}

	/**
	 * @return Returns the netUsdAmountMoney.
	 */
	public Money getNetUsdAmountMoney() {
		return netUsdAmountMoney;
	}

	/**
	 * @param netUsdAmountMoney The netUsdAmountMoney to set.
	 */
	public void setNetUsdAmountMoney(Money netUsdAmountMoney) {
		this.netUsdAmountMoney = netUsdAmountMoney;
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
	 * @return Returns the listFlag.
	 */
	public String getListFlag() {
		return listFlag;
	}

	/**
	 * @param listFlag The listFlag to set.
	 */
	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public String getProcessedFlag() {
		return processedFlag;
	}

	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}
}
