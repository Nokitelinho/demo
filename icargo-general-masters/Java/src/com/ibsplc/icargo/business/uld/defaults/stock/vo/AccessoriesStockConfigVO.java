/*
 * AccessoriesStockConfigVO.java Created on Oct 3, 2005
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
 * @author A-1347
 *
 */
public class AccessoriesStockConfigVO extends AbstractVO {

    private String operationFlag;

    private String companyCode;
    
    private String accessoryCode;

    private String accessoryDescription;
    
    private String airlineCode;
    
    private int airlineIdentifier;

    private String stationCode;

    private int available;

    private int loaned;

	private String remarks;
	
	//added by a-3045 for CR AirNZ450 starts
	private int minimumQuantity;
    //added by a-3045 for CR AirNZ450 starts

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;





    /**
     * @return Returns the accessoryCode.
     */
    public String getAccessoryCode() {
        return accessoryCode;
    }
    /**
     * @param accessoryCode The accessoryCode to set.
     */
    public void setAccessoryCode(String accessoryCode) {
        this.accessoryCode = accessoryCode;
    }
    /**
     * @return Returns the accessoryDescription.
     */
    public String getAccessoryDescription() {
        return accessoryDescription;
    }
    /**
     * @param accessoryDescription The accessoryDescription to set.
     */
    public void setAccessoryDescription(String accessoryDescription) {
        this.accessoryDescription = accessoryDescription;
    }
    /**
     * @return Returns the available.
     */
    public int getAvailable() {
        return available;
    }
    /**
     * @param available The available to set.
     */
    public void setAvailable(int available) {
        this.available = available;
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
     * @return Returns the lastUpdateTime.
     */
    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateTime The lastUpdateTime to set.
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
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    /**
     * @return Returns the loaned.
     */
    public int getLoaned() {
        return loaned;
    }
    /**
     * @param loaned The loaned to set.
     */
    public void setLoaned(int loaned) {
        this.loaned = loaned;
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
     * @return Returns the remarks.
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return Returns the stationCode.
     */
    public String getStationCode() {
        return stationCode;
    }
    /**
     * @param stationCode The stationCode to set.
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
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
    /**
     * @return String
     */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return the minimumQuantity
	 */
	public int getMinimumQuantity() {
		return minimumQuantity;
	}
	/**
	 * @param minimumQuantity the minimumQuantity to set
	 */
	public void setMinimumQuantity(int minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
}
