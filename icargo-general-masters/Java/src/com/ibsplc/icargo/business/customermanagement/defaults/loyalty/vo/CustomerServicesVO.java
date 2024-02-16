/*
 * CustomerServicesVO.java Created on jun 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;




import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2048
 *
 */
public class CustomerServicesVO  extends AbstractVO {
	 
	private String companyCode;
	private String serviceCode;
	private double points;
    private String serviceDescription;
    private String keyContactFlag;
    private LocalDate lastUpdatedTime;
    private String lastUpdatedUser;
    private String operationFlag;
	/**
	 * @return String Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return String Returns the keyContactFlag.
	 */
	public String getKeyContactFlag() {
		return this.keyContactFlag;
	}
	/**
	 * @param keyContactFlag The keyContactFlag to set.
	 */
	public void setKeyContactFlag(String keyContactFlag) {
		this.keyContactFlag = keyContactFlag;
	}
	/**
	 * @return LocalDate Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return double Returns the points.
	 */
	public double getPoints() {
		return this.points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return String Returns the serviceCode.
	 */
	public String getServiceCode() {
		return this.serviceCode;
	}
	/**
	 * @param serviceCode The serviceCode to set.
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * @return String Returns the serviceDescription.
	 */
	public String getServiceDescription() {
		return this.serviceDescription;
	}
	/**
	 * @param serviceDescription The serviceDescription to set.
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
    
}
