/*
 * AirlineForBillingVO.java Created on Nov 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author Sandeep.T
 * Revision History
 * AirlineForBillingVO 
 * extends AbstractVO
 * Version Date Author Description
 * 
 * 0.1 Nov 07, 2006 Sandeep.T Initial draft
 * 
 */
public class AirlineForBillingVO extends AbstractVO {

	private String companyCode;

	private int airlineIdentifier;

	private String airlineNumber;

	private String clearancePeriod;

	private String airlineCode;

	private String baseCurrency;

	private String billingCurrency;

	private String clearingHouse;

	private LocalDate creationDate;

	private String status;

	private Money cargoBillingTotal;

	private Money miscBillingTotal;

	private String zoneIndicator;

	private Money passengerAmountInBase;

	private Money uatpAmountInBase;

	private Money cargoAmountInBase;

	private Money miscAmountInBase;

	private Money totalAmountInBase;

	private Money creditAmountInBase;

	private Money NetValueInBase;

	private Money passengerAmountInBilling;

	private Money uatpAmountInBilling;

	private Money cargoAmountInBilling;

	private Money miscAmountInBilling;

	private Money totalAmountInBilling;

	private Money creditAmountInBilling;

	private Money netValueInBilling;

	private String operationFlag;

	private String zoneLevel;

	/** added by A-2521 for from three report */

	private String airLineName;

	private Money zoneADebit;

	private Money zoneBDebit;

	private Money zoneCDebit;

	private Money zoneDDebit;

	private String exchangeRateBasis;

	private LocalDate exchangeRateDate;

	/**
	 * variables for flag
	 */

	private boolean isFormTwoGenerated;

	private boolean generatedFormOneFlag;

	private boolean generatedInvoiceFlag;

	private boolean capturedInvoiceFlag;

	private boolean capturedFormOneFlag;

	private boolean isFormThreeCaptured;

	private String formOneStatus;

	/**
	 * @author A-3447
	 * 
	 */
	private double outWardMisAmount;

	private double outWardTotalAmount;

	private double inwardMisAmount;

	private double inwardTotalAmount;

	private double inwardCreditAmount;

	private double inwardNetAmount;

	/**
	 * @author A-3447
	 * 
	 */
	// private Collection<InterlineInvoiceVO>
	// interlineInvoiceVOs=null;
	private Collection<FormOneVO> formOneVOs = null;

	// added by A-2554,Tito for optimistic locking
	private String lastUpdateUser;

	private LocalDate lastUpdateTime;

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
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the baseCurrency.
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * @param baseCurrency
	 *            The baseCurrency to set.
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/**
	 * @return Returns the billingCurrency.
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}

	/**
	 * @param billingCurrency
	 *            The billingCurrency to set.
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	/**
	 * @return Returns the cargoAmountInBase.
	 */
	public Money getCargoAmountInBase() {
		return cargoAmountInBase;
	}

	/**
	 * @param cargoAmountInBase
	 *            The cargoAmountInBase to set.
	 */
	public void setCargoAmountInBase(Money cargoAmountInBase) {
		this.cargoAmountInBase = cargoAmountInBase;
	}

	/**
	 * @return Returns the cargoAmountInBilling.
	 */
	public Money getCargoAmountInBilling() {
		return cargoAmountInBilling;
	}

	/**
	 * @param cargoAmountInBilling
	 *            The cargoAmountInBilling to set.
	 */
	public void setCargoAmountInBilling(Money cargoAmountInBilling) {
		this.cargoAmountInBilling = cargoAmountInBilling;
	}

	/**
	 * @return Returns the cargoBillingTotal.
	 */
	public Money getCargoBillingTotal() {
		return cargoBillingTotal;
	}

	/**
	 * @param cargoBillingTotal
	 *            The cargoBillingTotal to set.
	 */
	public void setCargoBillingTotal(Money cargoBillingTotal) {
		this.cargoBillingTotal = cargoBillingTotal;
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the clearingHouse.
	 */
	public String getClearingHouse() {
		return clearingHouse;
	}

	/**
	 * @param clearingHouse
	 *            The clearingHouse to set.
	 */
	public void setClearingHouse(String clearingHouse) {
		this.clearingHouse = clearingHouse;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the creationDate.
	 */
	public LocalDate getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the creditAmountInBase.
	 */
	public Money getCreditAmountInBase() {
		return creditAmountInBase;
	}

	/**
	 * @param creditAmountInBase
	 *            The creditAmountInBase to set.
	 */
	public void setCreditAmountInBase(Money creditAmountInBase) {
		this.creditAmountInBase = creditAmountInBase;
	}

	/**
	 * @return Returns the creditAmountInBilling.
	 */
	public Money getCreditAmountInBilling() {
		return creditAmountInBilling;
	}

	/**
	 * @param creditAmountInBilling
	 *            The creditAmountInBilling to set.
	 */
	public void setCreditAmountInBilling(Money creditAmountInBilling) {
		this.creditAmountInBilling = creditAmountInBilling;
	}

	/**
	 * @return Returns the isFormThreeCaptured.
	 */
	public boolean isFormThreeCaptured() {
		return isFormThreeCaptured;
	}

	/**
	 * @param isFormThreeCaptured
	 *            The isFormThreeCaptured to set.
	 */
	public void setFormThreeCaptured(boolean isFormThreeCaptured) {
		this.isFormThreeCaptured = isFormThreeCaptured;
	}

	/**
	 * @return Returns the isFormTwoGenerated.
	 */
	public boolean isFormTwoGenerated() {
		return isFormTwoGenerated;
	}

	/**
	 * @param isFormTwoGenerated
	 *            The isFormTwoGenerated to set.
	 */
	public void setFormTwoGenerated(boolean isFormTwoGenerated) {
		this.isFormTwoGenerated = isFormTwoGenerated;
	}

	/**
	 * @return Returns the miscAmountInBase.
	 */
	public Money getMiscAmountInBase() {
		return miscAmountInBase;
	}

	/**
	 * @param miscAmountInBase
	 *            The miscAmountInBase to set.
	 */
	public void setMiscAmountInBase(Money miscAmountInBase) {
		this.miscAmountInBase = miscAmountInBase;
	}

	/**
	 * @return Returns the miscAmountInBilling.
	 */
	public Money getMiscAmountInBilling() {
		return miscAmountInBilling;
	}

	/**
	 * @param miscAmountInBilling
	 *            The miscAmountInBilling to set.
	 */
	public void setMiscAmountInBilling(Money miscAmountInBilling) {
		this.miscAmountInBilling = miscAmountInBilling;
	}

	/**
	 * @return Returns the miscBillingTotal.
	 */
	public Money getMiscBillingTotal() {
		return miscBillingTotal;
	}

	/**
	 * @param miscBillingTotal
	 *            The miscBillingTotal to set.
	 */
	public void setMiscBillingTotal(Money miscBillingTotal) {
		this.miscBillingTotal = miscBillingTotal;
	}

	/**
	 * @return Returns the netValueInBase.
	 */
	public Money getNetValueInBase() {
		return NetValueInBase;
	}

	/**
	 * @param netValueInBase
	 *            The netValueInBase to set.
	 */
	public void setNetValueInBase(Money netValueInBase) {
		NetValueInBase = netValueInBase;
	}

	/**
	 * @return Returns the netValueInBilling.
	 */
	public Money getNetValueInBilling() {
		return netValueInBilling;
	}

	/**
	 * @param netValueInBilling
	 *            The netValueInBilling to set.
	 */
	public void setNetValueInBilling(Money netValueInBilling) {
		this.netValueInBilling = netValueInBilling;
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
	 * @return Returns the passengerAmountInBase.
	 */
	public Money getPassengerAmountInBase() {
		return passengerAmountInBase;
	}

	/**
	 * @param passengerAmountInBase
	 *            The passengerAmountInBase to set.
	 */
	public void setPassengerAmountInBase(Money passengerAmountInBase) {
		this.passengerAmountInBase = passengerAmountInBase;
	}

	/**
	 * @return Returns the passengerAmountInBilling.
	 */
	public Money getPassengerAmountInBilling() {
		return passengerAmountInBilling;
	}

	/**
	 * @param passengerAmountInBilling
	 *            The passengerAmountInBilling to set.
	 */
	public void setPassengerAmountInBilling(Money passengerAmountInBilling) {
		this.passengerAmountInBilling = passengerAmountInBilling;
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
	 * @return Returns the totalAmountInBase.
	 */
	public Money getTotalAmountInBase() {
		return totalAmountInBase;
	}

	/**
	 * @param totalAmountInBase
	 *            The totalAmountInBase to set.
	 */
	public void setTotalAmountInBase(Money totalAmountInBase) {
		this.totalAmountInBase = totalAmountInBase;
	}

	/**
	 * @return Returns the totalAmountInBilling.
	 */
	public Money getTotalAmountInBilling() {
		return totalAmountInBilling;
	}

	/**
	 * @param totalAmountInBilling
	 *            The totalAmountInBilling to set.
	 */
	public void setTotalAmountInBilling(Money totalAmountInBilling) {
		this.totalAmountInBilling = totalAmountInBilling;
	}

	/**
	 * @return Returns the uatpAmountInBase.
	 */
	public Money getUatpAmountInBase() {
		return uatpAmountInBase;
	}

	/**
	 * @param uatpAmountInBase
	 *            The uatpAmountInBase to set.
	 */
	public void setUatpAmountInBase(Money uatpAmountInBase) {
		this.uatpAmountInBase = uatpAmountInBase;
	}

	/**
	 * @return Returns the uatpAmountInBilling.
	 */
	public Money getUatpAmountInBilling() {
		return uatpAmountInBilling;
	}

	/**
	 * @param uatpAmountInBilling
	 *            The uatpAmountInBilling to set.
	 */
	public void setUatpAmountInBilling(Money uatpAmountInBilling) {
		this.uatpAmountInBilling = uatpAmountInBilling;
	}

	/**
	 * @return Returns the zoneIndicator.
	 */
	public String getZoneIndicator() {
		return zoneIndicator;
	}

	/**
	 * @param zoneIndicator
	 *            The zoneIndicator to set.
	 */
	public void setZoneIndicator(String zoneIndicator) {
		this.zoneIndicator = zoneIndicator;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the formOneVOs.
	 */
	public Collection<FormOneVO> getFormOneVOs() {
		return formOneVOs;
	}

	/**
	 * @param formOneVOs
	 *            The formOneVOs to set.
	 */
	public void setFormOneVOs(Collection<FormOneVO> formOneVOs) {
		this.formOneVOs = formOneVOs;
	}

	/**
	 * @return Returns the zoneLevel.
	 */
	public String getZoneLevel() {
		return zoneLevel;
	}

	/**
	 * @param zoneLevel
	 *            The zoneLevel to set.
	 */
	public void setZoneLevel(String zoneLevel) {
		this.zoneLevel = zoneLevel;
	}

	/**
	 * @return Returns the airLineName.
	 */
	public String getAirLineName() {
		return airLineName;
	}

	/**
	 * @param airLineName
	 *            The airLineName to set.
	 */
	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}

	/**
	 * @return Returns the zoneADebit.
	 */
	public Money getZoneADebit() {
		return zoneADebit;
	}

	/**
	 * @param zoneADebit
	 *            The zoneADebit to set.
	 */
	public void setZoneADebit(Money zoneADebit) {
		this.zoneADebit = zoneADebit;
	}

	/**
	 * @return Returns the zoneBDebit.
	 */
	public Money getZoneBDebit() {
		return zoneBDebit;
	}

	/**
	 * @param zoneBDebit
	 *            The zoneBDebit to set.
	 */
	public void setZoneBDebit(Money zoneBDebit) {
		this.zoneBDebit = zoneBDebit;
	}

	/**
	 * @return Returns the zoneCDebit.
	 */
	public Money getZoneCDebit() {
		return zoneCDebit;
	}

	/**
	 * @param zoneCDebit
	 *            The zoneCDebit to set.
	 */
	public void setZoneCDebit(Money zoneCDebit) {
		this.zoneCDebit = zoneCDebit;
	}

	/**
	 * @return Returns the zoneDDebit.
	 */
	public Money getZoneDDebit() {
		return zoneDDebit;
	}

	/**
	 * @param zoneDDebit
	 *            The zoneDDebit to set.
	 */
	public void setZoneDDebit(Money zoneDDebit) {
		this.zoneDDebit = zoneDDebit;
	}

	/**
	 * @return Returns the exchangeRateBasis.
	 */
	public String getExchangeRateBasis() {
		return exchangeRateBasis;
	}

	/**
	 * @param exchangeRateBasis
	 *            The exchangeRateBasis to set.
	 */
	public void setExchangeRateBasis(String exchangeRateBasis) {
		this.exchangeRateBasis = exchangeRateBasis;
	}

	/**
	 * @return Returns the exchangeRateDate.
	 */
	public LocalDate getExchangeRateDate() {
		return exchangeRateDate;
	}

	/**
	 * @param exchangeRateDate
	 *            The exchangeRateDate to set.
	 */
	public void setExchangeRateDate(LocalDate exchangeRateDate) {
		this.exchangeRateDate = exchangeRateDate;
	}

	/**
	 * @return Returns the capturedFormOneFlag.
	 */
	public boolean isCapturedFormOneFlag() {
		return capturedFormOneFlag;
	}

	/**
	 * @param capturedFormOneFlag
	 *            The capturedFormOneFlag to set.
	 */
	public void setCapturedFormOneFlag(boolean capturedFormOneFlag) {
		this.capturedFormOneFlag = capturedFormOneFlag;
	}

	/**
	 * @return Returns the capturedInvoiceFlag.
	 */
	public boolean isCapturedInvoiceFlag() {
		return capturedInvoiceFlag;
	}

	/**
	 * @param capturedInvoiceFlag
	 *            The capturedInvoiceFlag to set.
	 */
	public void setCapturedInvoiceFlag(boolean capturedInvoiceFlag) {
		this.capturedInvoiceFlag = capturedInvoiceFlag;
	}

	/**
	 * @return Returns the generatedFormOneFlag.
	 */
	public boolean isGeneratedFormOneFlag() {
		return generatedFormOneFlag;
	}

	/**
	 * @param generatedFormOneFlag
	 *            The generatedFormOneFlag to set.
	 */
	public void setGeneratedFormOneFlag(boolean generatedFormOneFlag) {
		this.generatedFormOneFlag = generatedFormOneFlag;
	}

	/**
	 * @return Returns the generatedInvoiceFlag.
	 */
	public boolean isGeneratedInvoiceFlag() {
		return generatedInvoiceFlag;
	}

	/**
	 * @param generatedInvoiceFlag
	 *            The generatedInvoiceFlag to set.
	 */
	public void setGeneratedInvoiceFlag(boolean generatedInvoiceFlag) {
		this.generatedInvoiceFlag = generatedInvoiceFlag;
	}

	/**
	 * @return Returns the formOneStatus.
	 */
	public String getFormOneStatus() {
		return formOneStatus;
	}

	/**
	 * @param formOneStatus
	 *            The formOneStatus to set.
	 */
	public void setFormOneStatus(String formOneStatus) {
		this.formOneStatus = formOneStatus;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the inwardCreditAmount
	 */
	public double getInwardCreditAmount() {
		return inwardCreditAmount;
	}

	/**
	 * @param inwardCreditAmount
	 *            the inwardCreditAmount to set
	 */
	public void setInwardCreditAmount(double inwardCreditAmount) {
		this.inwardCreditAmount = inwardCreditAmount;
	}

	/**
	 * @return the inwardMisAmount
	 */
	public double getInwardMisAmount() {
		return inwardMisAmount;
	}

	/**
	 * @param inwardMisAmount
	 *            the inwardMisAmount to set
	 */
	public void setInwardMisAmount(double inwardMisAmount) {
		this.inwardMisAmount = inwardMisAmount;
	}

	/**
	 * @return the inwardNetAmount
	 */
	public double getInwardNetAmount() {
		return inwardNetAmount;
	}

	/**
	 * @param inwardNetAmount
	 *            the inwardNetAmount to set
	 */
	public void setInwardNetAmount(double inwardNetAmount) {
		this.inwardNetAmount = inwardNetAmount;
	}

	/**
	 * @return the inwardTotalAmount
	 */
	public double getInwardTotalAmount() {
		return inwardTotalAmount;
	}

	/**
	 * @param inwardTotalAmount
	 *            the inwardTotalAmount to set
	 */
	public void setInwardTotalAmount(double inwardTotalAmount) {
		this.inwardTotalAmount = inwardTotalAmount;
	}

	/**
	 * @return the outWardTotalAmount
	 */
	public double getOutWardTotalAmount() {
		return outWardTotalAmount;
	}

	/**
	 * @param outWardTotalAmount
	 *            the outWardTotalAmount to set
	 */
	public void setOutWardTotalAmount(double outWardTotalAmount) {
		this.outWardTotalAmount = outWardTotalAmount;
	}

	/**
	 * @return the outWardMisAmount
	 */
	public double getOutWardMisAmount() {
		return outWardMisAmount;
	}

	/**
	 * @param outWardMisAmount the outWardMisAmount to set
	 */
	public void setOutWardMisAmount(double outWardMisAmount) {
		this.outWardMisAmount = outWardMisAmount;
	}

}
