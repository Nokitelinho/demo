/*
 * InterlineFilterVO.java Created on July 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3456
 * 
 */
public class InterlineFilterVO extends AbstractVO {
	private String airlineNumber;
	private int airlineIdentifier;	
	private String airlineCode;
	
	private String clearancePeriod;
	
	private String companyCode;
	private Boolean isFormTwo;
	private String interlineBillingType;

	/**
	 * @return the interlineBillingType
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}
	/**
	 * @param interlineBillingType the interlineBillingType to set
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}
	/**
	 * @return Returns the isFormTwo.
	 */
	public Boolean getIsFormTwo() {
		return isFormTwo;
	}
	/**
	 * @param isFormTwo The isFormTwo to set.
	 */
	public void setIsFormTwo(Boolean isFormTwo) {
		this.isFormTwo = isFormTwo;
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
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}
	/**
	 * @param airlineNumber The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
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
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

}
