/*
 * CustomerListFilterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.profile.vo;




import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class CustomerListFilterVO  extends AbstractVO{

    private String companyCode;
    private String customerCode;
    private String stationCode;
    private String activeStatus;
    private double creditLimit;
    private int creditPeriod;
    private double creditOutstanding;
    private boolean isLoyaltyCustomer;
	/**
	 * @return String Returns the activeStatus.
	 */
	public String getActiveStatus() {
		return this.activeStatus;
	}
	/**
	 * @param activeStatus The activeStatus to set.
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return double Returns the creditLimit.
	 */
	public double getCreditLimit() {
		return this.creditLimit;
	}
	/**
	 * @param creditLimit The creditLimit to set.
	 */
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	/**
	 * @return double Returns the creditOutstanding.
	 */
	public double getCreditOutstanding() {
		return this.creditOutstanding;
	}
	/**
	 * @param creditOutstanding The creditOutstanding to set.
	 */
	public void setCreditOutstanding(double creditOutstanding) {
		this.creditOutstanding = creditOutstanding;
	}
	/**
	 * @return int Returns the creditPeriod.
	 */
	public int getCreditPeriod() {
		return this.creditPeriod;
	}
	/**
	 * @param creditPeriod The creditPeriod to set.
	 */
	public void setCreditPeriod(int creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	/**
	 * @return String Returns the customerCode.
	 */
	public String getCustomerCode() {
		return this.customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return boolean Returns the isLoyaltyCustomer.
	 */
	public boolean isLoyaltyCustomer() {
		return this.isLoyaltyCustomer;
	}
	/**
	 * @param isLoyaltyCustomer The isLoyaltyCustomer to set.
	 */
	public void setLoyaltyCustomer(boolean isLoyaltyCustomer) {
		this.isLoyaltyCustomer = isLoyaltyCustomer;
	}
	/**
	 * @return String Returns the stationCode.
	 */
	public String getStationCode() {
		return this.stationCode;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

}

