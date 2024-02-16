/*
 * ULDVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;





import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1347
 *
 */
public class ULDValidationMicroVO extends AbstractVO {
    
    private String companyCode;
    
    private String uldNumber;
    
    private String uldContour;
    
    private double tareWeight;
    
    private double displayTareWeight;
    
    private String displayTareWeightUnit;
    
    private double structuralWeight;
    
    private double displayStructuralWeight;
    
    private String displayStructuralWeightUnit;
    
    private double baseLength;
    
    private double displayBaseLength;
    
    private double baseWidth;
    
    private double displayBaseWidth;

    private double baseHeight;
    
    private double displayBaseHeight;

    private String displayDimensionUnit;
    
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
    
    private String purchaseDate;
    
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
   
    private String lastUpdateTime;
    
    private String lastUpdateUser;
    
    
    /**
	 * @return Returns the lastUpdateTime.
	 */
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the purchaseDate.
	 */
	public String getPurchaseDate() {
		return purchaseDate;
	}
	/**
	 * @param purchaseDate The purchaseDate to set.
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
     * @return Returns the baseHeight.
     */
    public double getBaseHeight() {
        return baseHeight;
    }
    /**
     * @param baseHeight The baseHeight to set.
     */
    public void setBaseHeight(double baseHeight) {
        this.baseHeight = baseHeight;
    }
    /**
     * @return Returns the baseLength.
     */
    public double getBaseLength() {
        return baseLength;
    }
    /**
     * @param baseLength The baseLength to set.
     */
    public void setBaseLength(double baseLength) {
        this.baseLength = baseLength;
    }
    /**
     * @return Returns the baseWidth.
     */
    public double getBaseWidth() {
        return baseWidth;
    }
    /**
     * @param baseWidth The baseWidth to set.
     */
    public void setBaseWidth(double baseWidth) {
        this.baseWidth = baseWidth;
    }
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
     * @return Returns the displayBaseHeight.
     */
    public double getDisplayBaseHeight() {
        return displayBaseHeight;
    }
    /**
     * @param displayBaseHeight The displayBaseHeight to set.
     */
    public void setDisplayBaseHeight(double displayBaseHeight) {
        this.displayBaseHeight = displayBaseHeight;
    }
    /**
     * @return Returns the displayBaseLength.
     */
    public double getDisplayBaseLength() {
        return displayBaseLength;
    }
    /**
     * @param displayBaseLength The displayBaseLength to set.
     */
    public void setDisplayBaseLength(double displayBaseLength) {
        this.displayBaseLength = displayBaseLength;
    }
    /**
     * @return Returns the displayBaseWidth.
     */
    public double getDisplayBaseWidth() {
        return displayBaseWidth;
    }
    /**
     * @param displayBaseWidth The displayBaseWidth to set.
     */
    public void setDisplayBaseWidth(double displayBaseWidth) {
        this.displayBaseWidth = displayBaseWidth;
    }
    /**
     * @return Returns the displayDimensionUnit.
     */
    public String getDisplayDimensionUnit() {
        return displayDimensionUnit;
    }
    /**
     * @param displayDimensionUnit The displayDimensionUnit to set.
     */
    public void setDisplayDimensionUnit(String displayDimensionUnit) {
        this.displayDimensionUnit = displayDimensionUnit;
    }
    /**
     * @return Returns the displayStructuralWeight.
     */
    public double getDisplayStructuralWeight() {
        return displayStructuralWeight;
    }
    /**
     * @param displayStructuralWeight The displayStructuralWeight to set.
     */
    public void setDisplayStructuralWeight(double displayStructuralWeight) {
        this.displayStructuralWeight = displayStructuralWeight;
    }
    /**
     * @return Returns the displayStructuralWeightUnit.
     */
    public String getDisplayStructuralWeightUnit() {
        return displayStructuralWeightUnit;
    }
    /**
     * @param displayStructuralWeightUnit The displayStructuralWeightUnit to set.
     */
    public void setDisplayStructuralWeightUnit(
            String displayStructuralWeightUnit) {
        this.displayStructuralWeightUnit = displayStructuralWeightUnit;
    }
    /**
     * @return Returns the displayTareWeight.
     */
    public double getDisplayTareWeight() {
        return displayTareWeight;
    }
    /**
     * @param displayTareWeight The displayTareWeight to set.
     */
    public void setDisplayTareWeight(double displayTareWeight) {
        this.displayTareWeight = displayTareWeight;
    }
    /**
     * @return Returns the displayTareWeightUnit.
     */
    public String getDisplayTareWeightUnit() {
        return displayTareWeightUnit;
    }
    /**
     * @param displayTareWeightUnit The displayTareWeightUnit to set.
     */
    public void setDisplayTareWeightUnit(String displayTareWeightUnit) {
        this.displayTareWeightUnit = displayTareWeightUnit;
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
     * @return Returns the structuralWeight.
     */
    public double getStructuralWeight() {
        return structuralWeight;
    }
    /**
     * @param structuralWeight The structuralWeight to set.
     */
    public void setStructuralWeight(double structuralWeight) {
        this.structuralWeight = structuralWeight;
    }
    /**
     * @return Returns the tareWeight.
     */
    public double getTareWeight() {
        return tareWeight;
    }
    /**
     * @param tareWeight The tareWeight to set.
     */
    public void setTareWeight(double tareWeight) {
        this.tareWeight = tareWeight;
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
	public String getOwnerAirlineCode() {
		return ownerAirlineCode;
	}
	public void setOwnerAirlineCode(String ownerAirlineCode) {
		this.ownerAirlineCode = ownerAirlineCode;
	}
}
