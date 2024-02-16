/*
 *  CaptureMailFormThreeForm.java Created on June 11, 2008
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
 *
 * @author A-3108
 *
 */

public class CaptureMailFormThreeForm extends ScreenModel{

	private static final String BUNDLE = "captureForm3bundle";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	private String clearancePeriod;

	private String[] hiddenOperationFlag;
	private String[] airlineCode;
	private String[] airlineNumber;
	private int[] airlineIdentifier;
	private String[] passenger;
	private String[] uatp;
	private String[] debit;
	private String[] cargo;
	private String[] zoneA;
	private String[] zoneB;
	private String[] zoneC;
	private String[] zoneD;
	private String[] miscAmountInBilling;
	private String[] totalAmountInBilling;
	private String[] creditAmountInBilling;
	private String[] netValueInBilling;
	private String[] status;
	private String invokingScreen;
	/**
	 * @author A-3447
	 */
	
	private String rowCounter;
	private String linkDisable;
	
	/**
	 * footer Contents
	 */
	private double passengerTotalAmountInBilling;

	private double uatpTotalAmountInBilling;

	private double zoneaTotal;

	private double zonebTotal;

	private double zonecTotal;

	private double zonedTotal;

	private double miscTotalAmountInBilling;

	private double grossTotalAmountInBilling;

	private double creditTotalAmountInBilling;

	private double netTotalValueInBilling;

//For Money Implementation

	private Money passengerTotalAmountInBillingMoney;

	private Money uatpTotalAmountInBillingMoney;

	private Money zoneaTotalMoney;

	private Money zonebTotalMoney;

	private Money zonecTotalMoney;

	private Money zonedTotalMoney;

	private Money miscTotalAmountInBillingMoney;

	private Money grossTotalAmountInBillingMoney;

	private Money creditTotalAmountInBillingMoney;

	private Money netTotalValueInBillingMoney;

	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
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



	public String[] getAirlineCode() {
		return airlineCode;
	}


	public void setAirlineCode(String[] airlineCode) {
		this.airlineCode = airlineCode;
	}


	public String[] getCargo() {
		return cargo;
	}


	public void setCargo(String[] cargo) {
		this.cargo = cargo;
	}


	public String[] getDebit() {
		return debit;
	}


	public void setDebit(String[] debit) {
		this.debit = debit;
	}


	public String[] getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}


	public void setHiddenOperationFlag(String[] hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}


	public String[] getUatp() {
		return uatp;
	}


	public void setUatp(String[] uatp) {
		this.uatp = uatp;
	}


	public String[] getZoneA() {
		return zoneA;
	}


	public void setZoneA(String[] zoneA) {
		this.zoneA = zoneA;
	}


	public String[] getZoneB() {
		return zoneB;
	}


	public void setZoneB(String[] zoneB) {
		this.zoneB = zoneB;
	}


	public String[] getZoneC() {
		return zoneC;
	}


	public void setZoneC(String[] zoneC) {
		this.zoneC = zoneC;
	}


	public String[] getZoneD() {
		return zoneD;
	}


	public void setZoneD(String[] zoneD) {
		this.zoneD = zoneD;
	}


	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}


	public String[] getPassenger() {
		return passenger;
	}


	public void setPassenger(String[] passenger) {
		this.passenger = passenger;
	}





	public String getClearancePeriod() {
		return clearancePeriod;
	}


	public double getCreditTotalAmountInBilling() {
		return creditTotalAmountInBilling;
	}


	public void setCreditTotalAmountInBilling(double creditTotalAmountInBilling) {
		this.creditTotalAmountInBilling = creditTotalAmountInBilling;
	}


	public double getGrossTotalAmountInBilling() {
		return grossTotalAmountInBilling;
	}


	public void setGrossTotalAmountInBilling(double grossTotalAmountInBilling) {
		this.grossTotalAmountInBilling = grossTotalAmountInBilling;
	}


	public double getMiscTotalAmountInBilling() {
		return miscTotalAmountInBilling;
	}


	public void setMiscTotalAmountInBilling(double miscTotalAmountInBilling) {
		this.miscTotalAmountInBilling = miscTotalAmountInBilling;
	}


	public double getNetTotalValueInBilling() {
		return netTotalValueInBilling;
	}


	public void setNetTotalValueInBilling(double netTotalValueInBilling) {
		this.netTotalValueInBilling = netTotalValueInBilling;
	}


	public double getPassengerTotalAmountInBilling() {
		return passengerTotalAmountInBilling;
	}


	public void setPassengerTotalAmountInBilling(
			double passengerTotalAmountInBilling) {
		this.passengerTotalAmountInBilling = passengerTotalAmountInBilling;
	}


	public double getUatpTotalAmountInBilling() {
		return uatpTotalAmountInBilling;
	}


	public void setUatpTotalAmountInBilling(double uatpTotalAmountInBilling) {
		this.uatpTotalAmountInBilling = uatpTotalAmountInBilling;
	}


	public double getZoneaTotal() {
		return zoneaTotal;
	}


	public void setZoneaTotal(double zoneaTotal) {
		this.zoneaTotal = zoneaTotal;
	}


	public double getZonebTotal() {
		return zonebTotal;
	}


	public void setZonebTotal(double zonebTotal) {
		this.zonebTotal = zonebTotal;
	}


	public double getZonecTotal() {
		return zonecTotal;
	}


	public void setZonecTotal(double zonecTotal) {
		this.zonecTotal = zonecTotal;
	}


	public double getZonedTotal() {
		return zonedTotal;
	}


	public void setZonedTotal(double zonedTotal) {
		this.zonedTotal = zonedTotal;
	}


	public Money getCreditTotalAmountInBillingMoney() {
		return creditTotalAmountInBillingMoney;
	}


	public void setCreditTotalAmountInBillingMoney(
			Money creditTotalAmountInBillingMoney) {
		this.creditTotalAmountInBillingMoney = creditTotalAmountInBillingMoney;
	}


	public Money getGrossTotalAmountInBillingMoney() {
		return grossTotalAmountInBillingMoney;
	}


	public void setGrossTotalAmountInBillingMoney(
			Money grossTotalAmountInBillingMoney) {
		this.grossTotalAmountInBillingMoney = grossTotalAmountInBillingMoney;
	}


	public Money getMiscTotalAmountInBillingMoney() {
		return miscTotalAmountInBillingMoney;
	}


	public void setMiscTotalAmountInBillingMoney(Money miscTotalAmountInBillingMoney) {
		this.miscTotalAmountInBillingMoney = miscTotalAmountInBillingMoney;
	}


	public Money getNetTotalValueInBillingMoney() {
		return netTotalValueInBillingMoney;
	}


	public void setNetTotalValueInBillingMoney(Money netTotalValueInBillingMoney) {
		this.netTotalValueInBillingMoney = netTotalValueInBillingMoney;
	}


	public Money getPassengerTotalAmountInBillingMoney() {
		return passengerTotalAmountInBillingMoney;
	}


	public void setPassengerTotalAmountInBillingMoney(
			Money passengerTotalAmountInBillingMoney) {
		this.passengerTotalAmountInBillingMoney = passengerTotalAmountInBillingMoney;
	}


	public Money getUatpTotalAmountInBillingMoney() {
		return uatpTotalAmountInBillingMoney;
	}


	public void setUatpTotalAmountInBillingMoney(Money uatpTotalAmountInBillingMoney) {
		this.uatpTotalAmountInBillingMoney = uatpTotalAmountInBillingMoney;
	}


	public Money getZoneaTotalMoney() {
		return zoneaTotalMoney;
	}


	public void setZoneaTotalMoney(Money zoneaTotalMoney) {
		this.zoneaTotalMoney = zoneaTotalMoney;
	}


	public Money getZonebTotalMoney() {
		return zonebTotalMoney;
	}


	public void setZonebTotalMoney(Money zonebTotalMoney) {
		this.zonebTotalMoney = zonebTotalMoney;
	}


	public Money getZonecTotalMoney() {
		return zonecTotalMoney;
	}


	public void setZonecTotalMoney(Money zonecTotalMoney) {
		this.zonecTotalMoney = zonecTotalMoney;
	}


	public Money getZonedTotalMoney() {
		return zonedTotalMoney;
	}


	public void setZonedTotalMoney(Money zonedTotalMoney) {
		this.zonedTotalMoney = zonedTotalMoney;
	}


	public String[] getAirlineNumber() {
		return airlineNumber;
	}


	public void setAirlineNumber(String[] airlineNumber) {
		this.airlineNumber = airlineNumber;
	}


	public String[] getCreditAmountInBilling() {
		return creditAmountInBilling;
	}


	public void setCreditAmountInBilling(String[] creditAmountInBilling) {
		this.creditAmountInBilling = creditAmountInBilling;
	}


	public String[] getMiscAmountInBilling() {
		return miscAmountInBilling;
	}


	public void setMiscAmountInBilling(String[] miscAmountInBilling) {
		this.miscAmountInBilling = miscAmountInBilling;
	}


	public String[] getNetValueInBilling() {
		return netValueInBilling;
	}


	public void setNetValueInBilling(String[] netValueInBilling) {
		this.netValueInBilling = netValueInBilling;
	}


	public String[] getTotalAmountInBilling() {
		return totalAmountInBilling;
	}


	public void setTotalAmountInBilling(String[] totalAmountInBilling) {
		this.totalAmountInBilling = totalAmountInBilling;
	}


	public int[] getAirlineIdentifier() {
		return airlineIdentifier;
	}


	public void setAirlineIdentifier(int[] airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}


	/**
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}


	/**
	 * @param invokingScreen the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}


	/**
	 * @return the rowCounter
	 */
	public String getRowCounter() {
		return rowCounter;
	}


	/**
	 * @param rowCounter the rowCounter to set
	 */
	public void setRowCounter(String rowCounter) {
		this.rowCounter = rowCounter;
	}


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


}