/*
 * BillingLineVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2280
 * 
 */
public class FlightRevenueInterfaceVO extends AbstractVO {

	private String companyCode;
	private int serNumber;
	private long mailSeqNumber;
	private int versionNumber;
	
	
	private String accountDate;
	private String mailNumber;
	private String regionCode;
	private String branchCode;
	private String mailCategory;
	
	private String subClassGroup;
	private String settlement;
	
	private String mailOrigin;
	private String mailDestination;
	private String adjustCode;
	private String mailWeight;
	private String currency;
	private String flightLineCode;
	private String hLNumber;
	private String rateAmount;
	private String RateAmountInUSD;
	private String rSN;
	private String billingBranch;
	private String CarrTypeC;
	private String flightNumber;
	private String flightDate;
	private String flightOrigin;
	private String flightDestination;
	private String firstFlightDate;
	private String serialNumber;
	private String interfaceDate;
	
	private String triggerPoint;
	private String interfaceFlag;
	
	private String blockCheckMailWeight;
	private String blockCheckRateAmount;
	private String blockCheckRateAmountInUSD;
	private long sequenceNumber;
	
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getMailNumber() {
		return mailNumber;
	}
	public void setMailNumber(String mailNumber) {
		this.mailNumber = mailNumber;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getAdjustCode() {
		return adjustCode;
	}
	public void setAdjustCode(String adjustCode) {
		this.adjustCode = adjustCode;
	}
	public String getMailWeight() {
		return mailWeight;
	}
	public void setMailWeight(String mailWeight) {
		this.mailWeight = mailWeight;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String gethLNumber() {
		return hLNumber;
	}
	public void sethLNumber(String hLNumber) {
		this.hLNumber = hLNumber;
	}
	public String getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(String rateAmount) {
		this.rateAmount = rateAmount;
	}


	public String getBillingBranch() {
		return billingBranch;
	}
	public void setBillingBranch(String billingBranch) {
		this.billingBranch = billingBranch;
	}
	public String getCarrTypeC() {
		return CarrTypeC;
	}
	public void setCarrTypeC(String carrTypeC) {
		CarrTypeC = carrTypeC;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightOrigin() {
		return flightOrigin;
	}
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	public String getFlightDestination() {
		return flightDestination;
	}
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	public String getFirstFlightDate() {
		return firstFlightDate;
	}
	public void setFirstFlightDate(String firstFlightDate) {
		this.firstFlightDate = firstFlightDate;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getInterfaceDate() {
		return interfaceDate;
	}
	public void setInterfaceDate(String interfaceDate) {
		this.interfaceDate = interfaceDate;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getSerNumber() {
		return serNumber;
	}
	public void setSerNumber(int serNumber) {
		this.serNumber = serNumber;
	}
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getSubClassGroup() {
		return subClassGroup;
	}
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	public String getFlightLineCode() {
		return flightLineCode;
	}
	public void setFlightLineCode(String flightLineCode) {
		this.flightLineCode = flightLineCode;
	}
	public String getRateAmountInUSD() {
		return RateAmountInUSD;
	}
	public void setRateAmountInUSD(String rateAmountInUSD) {
		RateAmountInUSD = rateAmountInUSD;
	}
	public String getrSN() {
		return rSN;
	}
	public void setrSN(String rSN) {
		this.rSN = rSN;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	/**
	 * 	Getter for triggerPoint 
	 *	Added by : a-8061 on 31-Jul-2018
	 * 	Used for :
	 */
	public String getTriggerPoint() {
		return triggerPoint;
	}
	/**
	 *  @param triggerPoint the triggerPoint to set
	 * 	Setter for triggerPoint 
	 *	Added by : a-8061 on 31-Jul-2018
	 * 	Used for :
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}
	/**
	 * 	Getter for interfaceFlag 
	 *	Added by : a-8061 on 31-Jul-2018
	 * 	Used for :
	 */
	public String getInterfaceFlag() {
		return interfaceFlag;
	}
	/**
	 *  @param interfaceFlag the interfaceFlag to set
	 * 	Setter for interfaceFlag 
	 *	Added by : a-8061 on 31-Jul-2018
	 * 	Used for :
	 */
	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}
	public String getBlockCheckMailWeight() {
		return blockCheckMailWeight;
	}
	public void setBlockCheckMailWeight(String blockCheckMailWeight) {
		this.blockCheckMailWeight = blockCheckMailWeight;
	}
	public String getBlockCheckRateAmount() {
		return blockCheckRateAmount;
	}
	public void setBlockCheckRateAmount(String blockCheckRateAmount) {
		this.blockCheckRateAmount = blockCheckRateAmount;
	}
	public String getBlockCheckRateAmountInUSD() {
		return blockCheckRateAmountInUSD;
	}
	public void setBlockCheckRateAmountInUSD(String blockCheckRateAmountInUSD) {
		this.blockCheckRateAmountInUSD = blockCheckRateAmountInUSD;
	}

	

}
