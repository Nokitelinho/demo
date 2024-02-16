/*
 * AirlineCN51SummaryVO.java Created on Feb 16,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1946
 * 
 */
public class AirlineCN51SummaryVO extends AbstractVO {

	private String companycode;

	private int airlineidr;

	private String interlinebillingtype;  //Billing mode

	private String airlineName;

	private String invoicenumber;

	private String clearanceperiod;

	private String airlinecode;

	private String cn51status;

	private String billingcurrencycode;

	private String contractCurrencycode;

	private double totalAmountInBillingCurrency;

	private double totalAmountInContractCurrency;

	private double totalAmount;

	private LocalDate billeddate;

	private String operationFlag;

	private String strAirlineIdr;

	private LocalDate lastUpdatedTime;

	private String lastUpdatedUser;

	private String invSrc;

	// added for AirNZ 166

	private String listingCurrency;

	private Money netAmount;

	private Money exchangeRate;
	private double exgRate;

	private Money amountInusd;

	private LocalDate invRcvdate;

	private double totWt;

	private String invStatus;

	private String invFormstatus;

	private LocalDate invoiceDate;

	private String invoiceSrcFlag;

	private String buttonFlag;
	
	private double c51Amount;
	
	//Added By Deepthi as a part of pagination
	
	private double totalWeight;

	private Money totalCharge;
	
	private String billingType; //  Added by A-7929 as part of ICRD-265471
	
	private String fileName;
	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * this variable stores the amount which is stored against the airline
	 * invoice in the airlineMemoInInvoice table(MTKARLMEMINV)
	 */
	private double differenceAmount;

	private Page<AirlineCN51DetailsVO> cn51DetailsPageVOs;
	
	private Collection<AirlineCN51DetailsVO> cn51DetailsVOs;
	
	private AirlineCN51FilterVO cn51FilterVO;

	/**
	 * @return Returns the c51Amount.
	 */
	public double getC51Amount() {
		return c51Amount;
	}

	/**
	 * @param amount The c51Amount to set.
	 */
	public void setC51Amount(double amount) {
		c51Amount = amount;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the airlinecode.
	 */
	public String getAirlinecode() {
		return airlinecode;
	}

	/**
	 * @param airlinecode
	 *            The airlinecode to set.
	 */
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}

	/**
	 * @return Returns the airlineidr.
	 */
	public int getAirlineidr() {
		return airlineidr;
	}

	/**
	 * @param airlineidr
	 *            The airlineidr to set.
	 */
	public void setAirlineidr(int airlineidr) {
		this.airlineidr = airlineidr;
	}

	/**
	 * @return Returns the billeddate.
	 */
	public LocalDate getBilleddate() {
		return billeddate;
	}

	/**
	 * @param billeddate
	 *            The billeddate to set.
	 */
	public void setBilleddate(LocalDate billeddate) {
		this.billeddate = billeddate;
	}

	/**
	 * @return Returns the billingcurrencycode.
	 */
	public String getBillingcurrencycode() {
		return billingcurrencycode;
	}

	/**
	 * @param billingcurrencycode
	 *            The billingcurrencycode to set.
	 */
	public void setBillingcurrencycode(String billingcurrencycode) {
		this.billingcurrencycode = billingcurrencycode;
	}

	/**
	 * @return Returns the clearanceperiod.
	 */
	public String getClearanceperiod() {
		return clearanceperiod;
	}

	/**
	 * @param clearanceperiod
	 *            The clearanceperiod to set.
	 */
	public void setClearanceperiod(String clearanceperiod) {
		this.clearanceperiod = clearanceperiod;
	}

	/**
	 * @return Returns the cn51status.
	 */
	public String getCn51status() {
		return cn51status;
	}

	/**
	 * @param cn51status
	 *            The cn51status to set.
	 */
	public void setCn51status(String cn51status) {
		this.cn51status = cn51status;
	}

	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 *            The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the contractCurrencycode.
	 */
	public String getContractCurrencycode() {
		return contractCurrencycode;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setContractCurrencycode(String contractCurrencycode) {
		this.contractCurrencycode = contractCurrencycode;
	}

	/**
	 * @return Returns the interlinebillingtype.
	 */
	public String getInterlinebillingtype() {
		return interlinebillingtype;
	}

	/**
	 * @param interlinebillingtype
	 *            The interlinebillingtype to set.
	 */
	public void setInterlinebillingtype(String interlinebillingtype) {
		this.interlinebillingtype = interlinebillingtype;
	}

	/**
	 * @return Returns the invoicenumber.
	 */
	public String getInvoicenumber() {
		return invoicenumber;
	}

	/**
	 * @param invoicenumber
	 *            The invoicenumber to set.
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
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
	 * @return Returns the differenceAmount.
	 */
	public double getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount
	 *            The differenceAmount to set.
	 */
	public void setDifferenceAmount(double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	/**
	 * @return Returns the airlineName.
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName
	 *            The airlineName to set.
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	/**
	 * @return Returns the strAirlineIdr.
	 */
	public String getStrAirlineIdr() {
		return strAirlineIdr;
	}

	/**
	 * @param strAirlineIdr
	 *            The strAirlineIdr to set.
	 */
	public void setStrAirlineIdr(String strAirlineIdr) {
		this.strAirlineIdr = strAirlineIdr;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Returns the totalAmountInBillingCurrency.
	 */
	public double getTotalAmountInBillingCurrency() {
		return totalAmountInBillingCurrency;
	}

	/**
	 * @param totalAmountInBillingCurrency
	 *            The totalAmountInBillingCurrency to set.
	 */
	public void setTotalAmountInBillingCurrency(
			double totalAmountInBillingCurrency) {
		this.totalAmountInBillingCurrency = totalAmountInBillingCurrency;
	}

	/**
	 * @return Returns the totalAmountInContractCurrency.
	 */
	public double getTotalAmountInContractCurrency() {
		return totalAmountInContractCurrency;
	}

	/**
	 * @param totalAmountInContractCurrency
	 *            The totalAmountInContractCurrency to set.
	 */
	public void setTotalAmountInContractCurrency(
			double totalAmountInContractCurrency) {
		this.totalAmountInContractCurrency = totalAmountInContractCurrency;
	}

	/**
	 * @return Returns the invSrc.
	 */
	public String getInvSrc() {
		return invSrc;
	}

	/**
	 * @param invSrc
	 *            The invSrc to set.
	 */
	public void setInvSrc(String invSrc) {
		this.invSrc = invSrc;
	}

	/**
	 * @return the amountInusd
	 */
	public Money getAmountInusd() {
		return amountInusd;
	}

	/**
	 * @param amountInusd
	 *            the amountInusd to set
	 */
	public void setAmountInusd(Money amountInusd) {
		this.amountInusd = amountInusd;
	}

	/**
	 * @return the buttonFlag
	 */
	public String getButtonFlag() {
		return buttonFlag;
	}

	/**
	 * @param buttonFlag
	 *            the buttonFlag to set
	 */
	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	/**
	 * @return the exchangeRate
	 */
	public Money getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate
	 *            the exchangeRate to set
	 */
	public void setExchangeRate(Money exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * @return the invFormstatus
	 */
	public String getInvFormstatus() {
		return invFormstatus;
	}

	/**
	 * @param invFormstatus
	 *            the invFormstatus to set
	 */
	public void setInvFormstatus(String invFormstatus) {
		this.invFormstatus = invFormstatus;
	}

	/**
	 * @return the invoiceDate
	 */
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the invoiceSrcFlag
	 */
	public String getInvoiceSrcFlag() {
		return invoiceSrcFlag;
	}

	/**
	 * @param invoiceSrcFlag
	 *            the invoiceSrcFlag to set
	 */
	public void setInvoiceSrcFlag(String invoiceSrcFlag) {
		this.invoiceSrcFlag = invoiceSrcFlag;
	}

	/**
	 * @return the invRcvdate
	 */
	public LocalDate getInvRcvdate() {
		return invRcvdate;
	}

	/**
	 * @param invRcvdate
	 *            the invRcvdate to set
	 */
	public void setInvRcvdate(LocalDate invRcvdate) {
		this.invRcvdate = invRcvdate;
	}

	/**
	 * @return the invStatus
	 */
	public String getInvStatus() {
		return invStatus;
	}

	/**
	 * @param invStatus
	 *            the invStatus to set
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * @return the listingCurrency
	 */
	public String getListingCurrency() {
		return listingCurrency;
	}

	/**
	 * @param listingCurrency
	 *            the listingCurrency to set
	 */
	public void setListingCurrency(String listingCurrency) {
		this.listingCurrency = listingCurrency;
	}

	/**
	 * @return the netAmount
	 */
	public Money getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount
	 *            the netAmount to set
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the totWt
	 */
	public double getTotWt() {
		return totWt;
	}

	/**
	 * @param totWt
	 *            the totWt to set
	 */
	public void setTotWt(double totWt) {
		this.totWt = totWt;
	}

	/**
	 * @return the exgRate
	 */
	public double getExgRate() {
		return exgRate;
	}

	/**
	 * @param exgRate the exgRate to set
	 */
	public void setExgRate(double exgRate) {
		this.exgRate = exgRate;
	}
	/**
	 * @return the cn51FilterVO
	 */
	public AirlineCN51FilterVO getCn51FilterVO() {
		return cn51FilterVO;
	}

	/**
	 * @param cn51FilterVO the cn51FilterVO to set
	 */
	public void setCn51FilterVO(AirlineCN51FilterVO cn51FilterVO) {
		this.cn51FilterVO = cn51FilterVO;
	}

	/**
	 * @return the cn51DetailsPageVOs
	 */
	public Page<AirlineCN51DetailsVO> getCn51DetailsPageVOs() {
		return cn51DetailsPageVOs;
	}

	/**
	 * @param cn51DetailsPageVOs the cn51DetailsPageVOs to set
	 */
	public void setCn51DetailsPageVOs(Page<AirlineCN51DetailsVO> cn51DetailsPageVOs) {
		this.cn51DetailsPageVOs = cn51DetailsPageVOs;
	}

	/**
	 * @return the cn51DetailsVOs
	 */
	public Collection<AirlineCN51DetailsVO> getCn51DetailsVOs() {
		return cn51DetailsVOs;
	}

	/**
	 * @param cn51DetailsVOs the cn51DetailsVOs to set
	 */
	public void setCn51DetailsVOs(Collection<AirlineCN51DetailsVO> cn51DetailsVOs) {
		this.cn51DetailsVOs = cn51DetailsVOs;
	}

	/**
	 * @return the totalCharge
	 */
	public Money getTotalCharge() {
		return totalCharge;
	}

	/**
	 * @param totalCharge the totalCharge to set
	 */
	public void setTotalCharge(Money totalCharge) {
		this.totalCharge = totalCharge;
	}

	/**
	 * @return the totalWeight
	 */
	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	/**
	 * 	Getter for fileName 
	 *	Added by : A-5219 on 12-Nov-2020
	 * 	Used for :
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 *  @param fileName the fileName to set
	 * 	Setter for fileName 
	 *	Added by : A-5219 on 12-Nov-2020
	 * 	Used for :
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}