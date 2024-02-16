/*
 * ULDValidationVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1347
 *
 */
public class ULDValidationVO extends AbstractVO implements Serializable{
    
    private String companyCode;
    
    private String uldNumber;
    
    private String uldContour;
    
    private Measure tareWeight;
    
    //private double displayTareWeight;
    
    //private String displayTareWeightUnit;
    
    private Measure structuralWeight;
    
    //private double displayStructuralWeight;
    
    //private String displayStructuralWeightUnit;
    
    private Measure baseLength;
    
    //private Measure displayBaseLength;
    
    private Measure baseWidth;
    
    //private double displayBaseWidth;

    private Measure baseHeight;
    
    //private double displayBaseHeight;

    //private String displayDimensionUnit;
    
    private String uldType;
    
    private String operationalAirlineIdentifier;
    
    private int ownerAirlineIdentifier;
    
    private String ownerAirlineCode;
    
    private String currentStation;
    
    private String ownerStation;
    
    private String overallStatus;
    
    private String cleanlinessStatus;
    
    private String damageStatus;
    
    private String location;
    
    private String vendor;
    
    private String manufacturer;
    
    private String uldSerialNumber;
    
    private LocalDate purchaseDate;
    
    private String purchaseInvoiceNumber;
    
    private double uldPrice;
    
    private String uldPriceUnit;
    
    private double iataReplacementCost;
    
    private String iataReplacementCostUnit;

    private double currentValue;
    
    private String currentValueUnit;
    
//Added by AG (A-1496) for ULD Transaction
    
    private int loanReferenceNumber;
    
    private int borrowReferenceNumber;
   
    private LocalDate lastUpdateTime;
    
    private String lastUpdateUser;
    
    private String uldNature;
    
    /**
     * @return Returns the cleanlinessStatus.
     */
    public String getCleanlinessStatus() {
        return cleanlinessStatus;
    }
    /**
     * @param cleanlinessStatus The cleanlinessStatus to set.
     */
    public void setCleanlinessStatus(String cleanlinessStatus) {
        this.cleanlinessStatus = cleanlinessStatus;
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
     * @return Returns the currentStation.
     */
    public String getCurrentStation() {
        return currentStation;
    }
    /**
     * @param currentStation The currentStation to set.
     */
    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }
    /**
     * @return Returns the currentValue.
     */
    public double getCurrentValue() {
        return currentValue;
    }
    /**
     * @param currentValue The currentValue to set.
     */
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
    /**
     * @return Returns the currentValueUnit.
     */
    public String getCurrentValueUnit() {
        return currentValueUnit;
    }
    /**
     * @param currentValueUnit The currentValueUnit to set.
     */
    public void setCurrentValueUnit(String currentValueUnit) {
        this.currentValueUnit = currentValueUnit;
    }
    /**
     * @return Returns the damageStatus.
     */
    public String getDamageStatus() {
        return damageStatus;
    }
    /**
     * @param damageStatus The damageStatus to set.
     */
    public void setDamageStatus(String damageStatus) {
        this.damageStatus = damageStatus;
    }
    /**
     * @return Returns the iataReplacementCost.
     */
    public double getIataReplacementCost() {
        return iataReplacementCost;
    }
    /**
     * @param iataReplacementCost The iataReplacementCost to set.
     */
    public void setIataReplacementCost(double iataReplacementCost) {
        this.iataReplacementCost = iataReplacementCost;
    }
    /**
     * @return Returns the iataReplacementCostUnit.
     */
    public String getIataReplacementCostUnit() {
        return iataReplacementCostUnit;
    }
    /**
     * @param iataReplacementCostUnit The iataReplacementCostUnit to set.
     */
    public void setIataReplacementCostUnit(String iataReplacementCostUnit) {
        this.iataReplacementCostUnit = iataReplacementCostUnit;
    }
    /**
     * @return Returns the location.
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @return Returns the manufacturer.
     */
    public String getManufacturer() {
        return manufacturer;
    }
    /**
     * @param manufacturer The manufacturer to set.
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    /**
     * @return Returns the operationalAirlineIdentifier.
     */
    public String getOperationalAirlineIdentifier() {
        return operationalAirlineIdentifier;
    }
    /**
     * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
     */
    public void setOperationalAirlineIdentifier(
            String operationalAirlineIdentifier) {
        this.operationalAirlineIdentifier = operationalAirlineIdentifier;
    }
    /**
     * @return Returns the overallStatus.
     */
    public String getOverallStatus() {
        return overallStatus;
    }
    /**
     * @param overallStatus The overallStatus to set.
     */
    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }
    /**
     * @return Returns the ownerAirlineIdentifier.
     */
    public int getOwnerAirlineIdentifier() {
        return ownerAirlineIdentifier;
    }
    /**
     * @param ownerAirlineIdentifier The ownerAirlineIdentifier to set.
     */
    public void setOwnerAirlineIdentifier(int ownerAirlineIdentifier) {
        this.ownerAirlineIdentifier = ownerAirlineIdentifier;
    }
    /**
     * @return Returns the ownerStation.
     */
    public String getOwnerStation() {
        return ownerStation;
    }
    /**
     * @param ownerStation The ownerStation to set.
     */
    public void setOwnerStation(String ownerStation) {
        this.ownerStation = ownerStation;
    }
    /**
     * @return Returns the purchaseDate.
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
    /**
     * @param purchaseDate The purchaseDate to set.
     */
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    /**
     * @return Returns the purchaseInvoiceNumber.
     */
    public String getPurchaseInvoiceNumber() {
        return purchaseInvoiceNumber;
    }
    /**
     * @param purchaseInvoiceNumber The purchaseInvoiceNumber to set.
     */
    public void setPurchaseInvoiceNumber(String purchaseInvoiceNumber) {
        this.purchaseInvoiceNumber = purchaseInvoiceNumber;
    }
    /**
     * @return Returns the uldContour.
     */
    public String getUldContour() {
        return uldContour;
    }
    /**
     * @param uldContour The uldContour to set.
     */
    public void setUldContour(String uldContour) {
        this.uldContour = uldContour;
    }
    /**
     * @return Returns the uldNumber.
     */
    public String getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }
    /**
     * @return Returns the uldPrice.
     */
    public double getUldPrice() {
        return uldPrice;
    }
    /**
     * @param uldPrice The uldPrice to set.
     */
    public void setUldPrice(double uldPrice) {
        this.uldPrice = uldPrice;
    }
    /**
     * @return Returns the uldPriceUnit.
     */
    public String getUldPriceUnit() {
        return uldPriceUnit;
    }
    /**
     * @param uldPriceUnit The uldPriceUnit to set.
     */
    public void setUldPriceUnit(String uldPriceUnit) {
        this.uldPriceUnit = uldPriceUnit;
    }
    /**
     * @return Returns the uldSerialNumber.
     */
    public String getUldSerialNumber() {
        return uldSerialNumber;
    }
    /**
     * @param uldSerialNumber The uldSerialNumber to set.
     */
    public void setUldSerialNumber(String uldSerialNumber) {
        this.uldSerialNumber = uldSerialNumber;
    }
    /**
     * @return Returns the vendor.
     */
    public String getVendor() {
        return vendor;
    }
    /**
     * @param vendor The vendor to set.
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
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
     * @return Returns the uldType.
     */
    public String getUldType() {
        return uldType;
    }
    /**
     * @param uldType The uldType to set.
     */
    public void setUldType(String uldType) {
        this.uldType = uldType;
    }
    /**
     * @return Returns the borrowReferenceNumber.
     */
    public int getBorrowReferenceNumber() {
        return borrowReferenceNumber;
    }
    /**
     * @param borrowReferenceNumber The borrowReferenceNumber to set.
     */
    public void setBorrowReferenceNumber(int borrowReferenceNumber) {
        this.borrowReferenceNumber = borrowReferenceNumber;
    }
	/**
	 * @return Returns the loanReferenceNumber.
	 */
	public int getLoanReferenceNumber() {
	    return loanReferenceNumber;
	}
	/**
	 * @param loanReferenceNumber The loanReferenceNumber to set.
	 */
	public void setLoanReferenceNumber(int loanReferenceNumber) {
	    this.loanReferenceNumber = loanReferenceNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwnerAirlineCode() {
		return ownerAirlineCode;
	}
	/**
	 * 
	 * @param ownerAirlineCode
	 */
	public void setOwnerAirlineCode(String ownerAirlineCode) {
		this.ownerAirlineCode = ownerAirlineCode;
	}
	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return this.uldNature;
	}
	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}
}
