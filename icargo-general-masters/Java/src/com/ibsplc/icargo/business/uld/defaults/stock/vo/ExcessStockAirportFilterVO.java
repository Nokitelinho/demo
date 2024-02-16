/*
 * ExcessStockAirportFilterVO.java Created on Oct 3, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2934
 *
 */
public class ExcessStockAirportFilterVO extends AbstractVO {
	
	private String airport;
	private String uldType;
	
	private String uldTypeCode;
	private String currentAvailability;
	private String ucmUldIn;
	private String ucmUldOut;
	private String projectedULDCount;
	private String minimumQuantity;
	private String stockDeviation;
	
	
    private String companyCode;
    private String airlineCode;
    private int airlineIdentifier;

	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String operationFlag;
	private int absoluteIndex;
	
	private String origin;
	private String destination;
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getUldType() {
		return uldType;
	}
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}
	public String getUldTypeCode() {
		return uldTypeCode;
	}
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
	public String getCurrentAvailability() {
		return currentAvailability;
	}
	public void setCurrentAvailability(String currentAvailability) {
		this.currentAvailability = currentAvailability;
	}
	public String getUcmUldIn() {
		return ucmUldIn;
	}
	public void setUcmUldIn(String ucmUldIn) {
		this.ucmUldIn = ucmUldIn;
	}
	public String getUcmUldOut() {
		return ucmUldOut;
	}
	public void setUcmUldOut(String ucmUldOut) {
		this.ucmUldOut = ucmUldOut;
	}
	public String getProjectedULDCount() {
		return projectedULDCount;
	}
	public void setProjectedULDCount(String projectedULDCount) {
		this.projectedULDCount = projectedULDCount;
	}
	public String getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(String minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	public String getStockDeviation() {
		return stockDeviation;
	}
	public void setStockDeviation(String stockDeviation) {
		this.stockDeviation = stockDeviation;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

}
