/*
 * MailSLADetailsVO.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2524
 *
 */
public class MailSLADetailsVO extends AbstractVO {
	
	private String companyCode;
	private String slaId;
	private int serialNumber;
	private String mailCategory;
	private int serviceTime;
	private int alertTime;
	private int chaserTime;
	private int maxNumberOfChasers;
	private int chaserFrequency;
	private double claimRate;
	private String operationFlag;
	
	
	
	
	
	/**
	 * @return Returns the chaserFrequency.
	 */
	public int getChaserFrequency() {
		return chaserFrequency;
	}
	/**
	 * @param chaserFrequency The chaserFrequency to set.
	 */
	public void setChaserFrequency(int chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}
	/**
	 * @return Returns the claimRate.
	 */
	public double getClaimRate() {
		return claimRate;
	}
	/**
	 * @param claimRate The claimRate to set.
	 */
	public void setClaimRate(double claimRate) {
		this.claimRate = claimRate;
	}
	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}
	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	/**
	 * @return Returns the maxNumberOfChasers.
	 */
	public int getMaxNumberOfChasers() {
		return maxNumberOfChasers;
	}
	/**
	 * @param maxNumberOfChasers The maxNumberOfChasers to set.
	 */
	public void setMaxNumberOfChasers(int maxNumberOfChasers) {
		this.maxNumberOfChasers = maxNumberOfChasers;
	}
	
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return Returns the slaId.
	 */
	public String getSlaId() {
		return slaId;
	}
	/**
	 * @param slaId The slaId to set.
	 */
	public void setSlaId(String slaId) {
		this.slaId = slaId;
	}
	/**
	 * @return Returns the alertTime.
	 */
	public int getAlertTime() {
		return alertTime;
	}
	/**
	 * @param alertTime The alertTime to set.
	 */
	public void setAlertTime(int alertTime) {
		this.alertTime = alertTime;
	}
	/**
	 * @return Returns the chaserTime.
	 */
	public int getChaserTime() {
		return chaserTime;
	}
	/**
	 * @param chaserTime The chaserTime to set.
	 */
	public void setChaserTime(int chaserTime) {
		this.chaserTime = chaserTime;
	}
	/**
	 * @return Returns the serviceTime.
	 */
	public int getServiceTime() {
		return serviceTime;
	}
	/**
	 * @param serviceTime The serviceTime to set.
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	

}
