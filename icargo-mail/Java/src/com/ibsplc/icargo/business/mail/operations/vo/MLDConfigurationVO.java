/*
 * MLDConfigurationVO.java Created on Jun 30, 2016
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
public class MLDConfigurationVO extends AbstractVO {

	private String companyCode;
	private String carrierCode;
	private int carrierIdentifier;
	private String airportCode;
	private String allocatedRequired;
	private String upliftedRequired;
	private String deliveredRequired;
    private String hNDRequired;
	private String receivedRequired;
	private String operationFlag;
	
	//Added for CRQ ICRD-135130 by A-8061 starts
	 private String mldversion; 
	 private String stagedRequired;
	 private String nestedRequired;
	 private String receivedFromFightRequired;
	 private String transferredFromOALRequired;
	 private String receivedFromOALRequired;
	 private String returnedRequired;
	
	 /**
	  * Method is to get  mldversion
	  * @return
	  */
	public String getMldversion() {
		return mldversion;
	}
	/**
	 * Method is to set mldversion
	 * @param mldversion
	 */
	public void setMldversion(String mldversion) {
		this.mldversion = mldversion;
	}
	/**
	 * Method is to get stagedRequired
	 * @return
	 */
	public String getStagedRequired() {
		return stagedRequired;
	}
	/**
	 * Method is to set stagedRequired
	 * @param stagedRequired
	 */
	public void setStagedRequired(String stagedRequired) {
		this.stagedRequired = stagedRequired;
	}
	/**
	 * Method is to get nestedRequired
	 * @return
	 */
	public String getNestedRequired() {
		return nestedRequired;
	}
	/**
	 * Method is to set nestedRequired
	 * @param nestedRequired
	 */
	public void setNestedRequired(String nestedRequired) {
		this.nestedRequired = nestedRequired;
	}
	/**
	 * Method is to get receivedFromFightRequired
	 * @return
	 */
	public String getReceivedFromFightRequired() {
		return receivedFromFightRequired;
	}
	/**
	 * Method is to set receivedFromFightRequired
	 * @param receivedFromFightRequired
	 */
	public void setReceivedFromFightRequired(String receivedFromFightRequired) {
		this.receivedFromFightRequired = receivedFromFightRequired;
	}
	/**
	 * Method is to get transferredFromOALRequired
	 * @return
	 */
	public String getTransferredFromOALRequired() {
		return transferredFromOALRequired;
	}
	/**
	 * Method is to set transferredFromOALRequired
	 * @param transferredFromOALRequired
	 */
	public void setTransferredFromOALRequired(String transferredFromOALRequired) {
		this.transferredFromOALRequired = transferredFromOALRequired;
	}
	/**
	 * Method is to get receivedFromOALRequired
	 * @return
	 */
	public String getReceivedFromOALRequired() {
		return receivedFromOALRequired;
	}
	/**
	 * Method is to set receivedFromOALRequired
	 * @param receivedFromOALRequired
	 */
	public void setReceivedFromOALRequired(String receivedFromOALRequired) {
		this.receivedFromOALRequired = receivedFromOALRequired;
	}
	/**
	 * Method is to get returnedRequired
	 * @return
	 */
	public String getReturnedRequired() {
		return returnedRequired;
	}
	/**
	 * Method is to set returnedRequired
	 * @param returnedRequired
	 */
	public void setReturnedRequired(String returnedRequired) {
		this.returnedRequired = returnedRequired;
	}

	//Added for CRQ ICRD-135130 by A-8061 end
	
	
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * Method is to get companyCode
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * Method is to set companyCode
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * Method is to get carrierCode
	 * @return
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * Method is to set carrierCode
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * Method is to get carrierIdentifier
	 * @return
	 */
	public int getCarrierIdentifier() {
		return carrierIdentifier;
	}
	/**
	 * Method is to set carrierIdentifier
	 * @param carrierIdentifier
	 */
	public void setCarrierIdentifier(int carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}
	/**
	 * Method is to get airportCode
	 * @return
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * Method is to set airportCode
	 * @param airportCode
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * Method is to get allocatedRequired
	 * @return
	 */
	public String getAllocatedRequired() {
		return allocatedRequired;
	}
	/**
	 * Method is to set allocatedRequired
	 * @param allocatedRequired
	 */
	public void setAllocatedRequired(String allocatedRequired) {
		this.allocatedRequired = allocatedRequired;
	}
	/**
	 * Method is to get upliftedRequired
	 * @return
	 */
	public String getUpliftedRequired() {
		return upliftedRequired;
	}
	/**
	 * Method is to set upliftedRequired
	 * @param upliftedRequired
	 */
	public void setUpliftedRequired(String upliftedRequired) {
		this.upliftedRequired = upliftedRequired;
	}
	/**
	 * Method is to get deliveredRequired
	 * @return
	 */
	public String getDeliveredRequired() {
		return deliveredRequired;
	}
	/**
	 * Method is to set deliveredRequired
	 * @param deliveredRequired
	 */
	public void setDeliveredRequired(String deliveredRequired) {
		this.deliveredRequired = deliveredRequired;
	}
	/**
	 * Method is to get hNDRequired
	 * @return
	 */
	public String gethNDRequired() {
		return hNDRequired;
	}
	/**
	 * Method is to set hNDRequired
	 * @param hNDRequired
	 */
	public void sethNDRequired(String hNDRequired) {
		this.hNDRequired = hNDRequired;
	}
	/**
	 * Method is to get receivedRequired
	 * @return
	 */
	public String getReceivedRequired() {
		return receivedRequired;
	}
	/**
	 * Method is to set receivedRequired
	 * @param receivedRequired
	 */
	public void setReceivedRequired(String receivedRequired) {
		this.receivedRequired = receivedRequired;
	}
	
	
}
