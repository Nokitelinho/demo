/*
 * MLDConfigurationFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-3109
 * 
 */
public class MLDConfigurationFilterVO extends AbstractVO {

	private String companyCode;
	private String carrierCode;
	private String airportCode;
	//Added as part of Bug ICRD-143797 by A-5526
	private int carrierIdentifier;
	
	
	
	 private String mldversion;//Added for CRQ ICRD-135130 by A-8061 
	
	


	/**
	 * Method is to get companyCode
	 * 
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Method is to set companyCode
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Method is to get carrierCode
	 * 
	 * @return
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * Method is to set carrierCode
	 * 
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * Method is to get airportCode
	 * 
	 * @return
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * Method is to set airportCode
	 * 
	 * @param airportCode
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * Method to get carrierIdentifier
	 * @return
	 */
	public int getCarrierIdentifier() {
		return carrierIdentifier;
	}
/**
 * Method to set carrierIdentifier
 * @param carrierIdentifier
 */
	public void setCarrierIdentifier(int carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}
/**
 * method is to get mldversion
 * @return
 */
public String getMldversion() {
	return mldversion;
}
/**
 * this method is to set mldversion
 * @param mldversion
 */
public void setMldversion(String mldversion) {
	this.mldversion = mldversion;
}

}
