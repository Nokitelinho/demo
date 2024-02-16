/*
 * ULDVO.java Created on Dec 19, 2005
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
public class ULDVO extends AbstractVO implements Serializable{
    /**
     * module
     */
	public static final String MODULE ="uld";
	/**
	 * submodule
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * entity
	 */
	public static final String ENTITY ="uld.defaults.ULD";
	/**
	 * damaged status
	 */
	public static final String DAMAGED_STATUS="N";
	/**
	 * operationalstatus
	 */
	public static final String OPERATIONAL_STATUS="O";

	/**
	 * 
	 */
	public static final String CLEANLINESS_STATUS="C";

	//Added by AG for SCM Message Creation
	/**
	 * 
	 */
	public static final String SCM_SYSTEM_STOCK="S";
	/**
	 * 
	 */
	public static final String SCM_MISSING_STOCK="M";
	
	public static final String SCM_SIGHTED_STOCK="F"; //added by A-6344 for ICRD-55460 
	/**
	 * 
	 */
	public static final String SCM_FOUND_STOCK="F";
	/**
	 * 
	 */
	public static final String NO_LOCATION="NIL";
	
	public static final String OALFLT="OALFLT";
	
	public static final String OWNFLT="OWNFLT";
	public static final String SYS_VAL="S";
	public static final String DIS_VAL="D";
	/*
	 * Added by A-3415 for ICRD-113953.
	 * System parameter which hold the value for all Damage Status that will cause ULD
	 * to become non-operational
	 */
	public static final String NON_OPERATIONAL_DAMAGE_STATUS="uld.defaults.non-operationaldamagestatuses";
	
	

	
	private String scmMessageSendingFlag;
	
    /**
	 * @return the scmMessageSendingFlag
	 */
	public String getScmMessageSendingFlag() {
		return scmMessageSendingFlag;
	}
	/**
	 * @param scmMessageSendingFlag the scmMessageSendingFlag to set
	 */
	public void setScmMessageSendingFlag(String scmMessageSendingFlag) {
		this.scmMessageSendingFlag = scmMessageSendingFlag;
	}
	private String companyCode;

    private String uldNumber;

    private String uldContour;

    private Measure tareWeight;

   // private Measure displayTareWeight;

   // private String displayTareWeightUnit;

    private Measure structuralWeight;

    private String structuralWeightUnit;
   // private Measure displayStructuralWeight;

   // private String displayStructuralWeightUnit;

    public String getStructuralWeightUnit() {
		return structuralWeightUnit;
	}
	public void setStructuralWeightUnit(String structuralWeightUnit) {
		this.structuralWeightUnit = structuralWeightUnit;
	}
    /**
	 * @return the tareWeight
	 */
	public Measure getTareWeight() {
		return tareWeight;
	}
	/**
	 * @param tareWeight the tareWeight to set
	 */
	public void setTareWeight(Measure tareWeight) {
		this.tareWeight = tareWeight;
	}
	/**
	 * @return the structuralWeight
	 */
	public Measure getStructuralWeight() {
		return structuralWeight;
	}
	/**
	 * @param structuralWeight the structuralWeight to set
	 */
	public void setStructuralWeight(Measure structuralWeight) {
		this.structuralWeight = structuralWeight;
	}
	/**
	 * @return the baseLength
	 */
	public Measure getBaseLength() {
		return baseLength;
	}
	/**
	 * @param baseLength the baseLength to set
	 */
	public void setBaseLength(Measure baseLength) {
		this.baseLength = baseLength;
	}
	/**
	 * @return the baseWidth
	 */
	public Measure getBaseWidth() {
		return baseWidth;
	}
	/**
	 * @param baseWidth the baseWidth to set
	 */
	public void setBaseWidth(Measure baseWidth) {
		this.baseWidth = baseWidth;
	}
	/**
	 * @return the baseHeight
	 */
	public Measure getBaseHeight() {
		return baseHeight;
	}
	/**
	 * @param baseHeight the baseHeight to set
	 */
	public void setBaseHeight(Measure baseHeight) {
		this.baseHeight = baseHeight;
	}
	private Measure baseLength;

    //private Measure displayBaseLength;

    private Measure baseWidth;

    //private Measure displayBaseWidth;

    private Measure baseHeight;

   //private Measure displayBaseHeight;

    //private Measure displayDimensionUnit;
	  //added as part of ICRD-258261 by A-4393 for asiana starts 
    private boolean missingDiscrepancyCaptured ;
    public boolean isMissingDiscrepancyCaptured() {
		return missingDiscrepancyCaptured;
	}
	public void setMissingDiscrepancyCaptured(boolean missingDiscrepancyCaptured) {
		this.missingDiscrepancyCaptured = missingDiscrepancyCaptured;
	}
	//added as part of ICRD-258261 by A-4393 for asiana ends
    private String uldType;

    private int operationalAirlineIdentifier;

    private int ownerAirlineIdentifier;

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

    private LocalDate stockCheckDate;
    public LocalDate getStockCheckDate() {
		return stockCheckDate;
	}
	public void setStockCheckDate(LocalDate stockCheckDate) {
		this.stockCheckDate = stockCheckDate;
	}
    private String purchaseInvoiceNumber;

    private double displayUldPrice;

    private double uldPrice;

    private String uldPriceUnit;

    private double  displayIataReplacementCost;

    private double iataReplacementCost;

    private String displayIataReplacementCostUnit;

    private double displayCurrentValue;

    private double currentValue;

    private String currentValueUnit;

//Added by AG (A-1496) for ULD Transaction

    private int loanReferenceNumber;

    private int borrowReferenceNumber;

    private LocalDate lastUpdateTime;

    private String lastUpdateUser;

    private String operationalFlag;

    private String operationalAirlineCode;

    private String operationalOwnerAirlineCode;
    public String getOperationalOwnerAirlineCode() {
		return operationalOwnerAirlineCode;
	}
	public void setOperationalOwnerAirlineCode(String operationalOwnerAirlineCode) {
		this.operationalOwnerAirlineCode = operationalOwnerAirlineCode;
	}
    private String ownerAirlineCode;
	private String uldOwnerCode;
    public String getUldOwnerCode() {
		return uldOwnerCode;
	}
	public void setUldOwnerCode(String uldOwnerCode) {
		this.uldOwnerCode = uldOwnerCode;
	}

    private String remarks;

    private String fromCollection;

    private String locationType;

    private String transitStatus;

    //Added By A-6841 for CRQ ICRD-155382
    private String occupiedULDFlag;
    private String facilityType;

    private String controlReceiptNumber;
    
    private String uldNature;
    
    private LocalDate lostDate;

    //Added By Sreekumar S
    private String uldGroupCode;

    //Added by AG for SCM Message Creation

    private String scmStatusFlag;
    
    private LocalDate manufactureDate;
    private int lifeSpan;
    private String tsoNumber;
    private String releasedTo;
    private String Source;
    //added by A-2408 for QF1013 starts
	private String scmFlag;
	private LocalDate scmDate;
	//ends

	//added by a-3278 for bug 53712
	private String flightInfo;
	//a-3278 ends
	
	//added by a-3278 for CR QF1449
	private LocalDate lastSCMDate;
	//QF1449 ends
	
	private String reconciliationSeqNum; //added by A-6344 for ICRD-55460
	
	
	/**
	 * @return the reconciliationSeqNum
	 */
	public String getReconciliationSeqNum() {
		return reconciliationSeqNum;
	}
	/**
	 * @param reconciliationSeqNum the reconciliationSeqNum to set
	 */
	public void setReconciliationSeqNum(String reconciliationSeqNum) {
		this.reconciliationSeqNum = reconciliationSeqNum;
	}
	/**
	 * @return the releasedTo
	 */
	public String getReleasedTo() {
		return releasedTo;
	}
	/**
	 * @param releasedTo the releasedTo to set
	 */
	public void setReleasedTo(String releasedTo) {
		this.releasedTo = releasedTo;
	}
	/**
	 * @return the lifeSpan
	 */
	public int getLifeSpan() {
		return lifeSpan;
	}
	/**
	 * @param lifeSpan the lifeSpan to set
	 */
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	/**
	 * @return the manufactureDate
	 */
	public LocalDate getManufactureDate() {
		return manufactureDate;
	}
	/**
	 * @param manufactureDate the manufactureDate to set
	 */
	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	/**
	 * @return the tsoNumber
	 */
	public String getTsoNumber() {
		return tsoNumber;
	}
	/**
	 * @param tsoNumber the tsoNumber to set
	 */
	public void setTsoNumber(String tsoNumber) {
		this.tsoNumber = tsoNumber;
	}
	/**
	 * @return Returns the controlReceiptNumber.
	 */
	public String getControlReceiptNumber() {
		return controlReceiptNumber;
	}
	/**
	 * @param controlReceiptNumber The controlReceiptNumber to set.
	 */
	public void setControlReceiptNumber(String controlReceiptNumber) {
		this.controlReceiptNumber = controlReceiptNumber;
	}


	/**
	 * @return Returns the scmStatusFlag.
	 */
	public String getScmStatusFlag() {
		return scmStatusFlag;
	}
	/**
	 * @param scmStatusFlag The scmStatusFlag to set.
	 */
	public void setScmStatusFlag(String scmStatusFlag) {
		this.scmStatusFlag = scmStatusFlag;
	}

	/**
	 * @return String Returns the facilityType.
	 */
	public String getFacilityType() {
		return this.facilityType;
	}
	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return String Returns the locationType.
	 */
	public String getLocationType() {
		return this.locationType;
	}
	/**
	 * @param locationType The locationType to set.
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	/**
	 * @return String Returns the fromCollection.
	 */
	public String getFromCollection() {
		return this.fromCollection;
	}
	/**
	 * @param fromCollection The fromCollection to set.
	 */
	public void setFromCollection(String fromCollection) {
		this.fromCollection = fromCollection;
	}
	/**
	 * @return Returns the displayCurrentValue.
	 */
	public double getDisplayCurrentValue() {
		return displayCurrentValue;
	}
	/**
	 * @param displayCurrentValue The displayCurrentValue to set.
	 */
	public void setDisplayCurrentValue(double displayCurrentValue) {
		this.displayCurrentValue = displayCurrentValue;
	}
	/**
	 * @return Returns the displayiataReplacementCost.
	 */
	public double getDisplayIataReplacementCost() {
		return displayIataReplacementCost;
	}
	/**
	 * 
	 * @param displayIataReplacementCost
	 */
	public void setDisplayIataReplacementCost(double displayIataReplacementCost) {
		this.displayIataReplacementCost = displayIataReplacementCost;
	}
	/**
	 * @return Returns the displayUldPrice.
	 */
	public double getDisplayUldPrice() {
		return displayUldPrice;
	}
	/**
	 * @param displayUldPrice The displayUldPrice to set.
	 */
	public void setDisplayUldPrice(double displayUldPrice) {
		this.displayUldPrice = displayUldPrice;
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
    public String getDisplayIataReplacementCostUnit() {
        return displayIataReplacementCostUnit;
    }
    /**
     * 
     * @param displayIataReplacementCostUnit
     */
    public void setDisplayIataReplacementCostUnit(String displayIataReplacementCostUnit) {
        this.displayIataReplacementCostUnit = displayIataReplacementCostUnit;
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
    public int getOperationalAirlineIdentifier() {
        return operationalAirlineIdentifier;
    }
    /**
     * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
     */
    public void setOperationalAirlineIdentifier(
    		int operationalAirlineIdentifier) {
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
	 * @param operationalFlag The operationalFlag to be set.
	 */
	public void setOperationalFlag(String operationalFlag){
		this.operationalFlag=operationalFlag;
	}
	/**
	 *
	 * @return Returns the OperationalFlag
	 */
	public String getOperationalFlag(){
		return operationalFlag;
	}

	/**
	 *
	 * @param operationalAirlineCode The OperationalAirlineCode to be set
	 */
	public void setOperationalAirlineCode(String operationalAirlineCode){
		this.operationalAirlineCode=operationalAirlineCode;
	}
	/**
	 *
	 * @return Returns the OperationalAirlineCode
	 */
	public String getOperationalAirlineCode(){
		return operationalAirlineCode;
	}
	/**
	 * 
	 * @param ownerAirlineCode
	 */
	public void setOwnerAirlineCode(String ownerAirlineCode){
		this.ownerAirlineCode=ownerAirlineCode;
	}
	/**
	 *
	 * @return Returns the OperationalAirlineCode
	 */
	public String getOwnerAirlineCode(){
		return ownerAirlineCode;
	}
	/**
	 * @return Returns the transitStatus.
	 */
	public String getTransitStatus() {
		return transitStatus;
	}
	/**
	 * @param transitStatus The transitStatus to set.
	 */
	public void setTransitStatus(String transitStatus) {
		this.transitStatus = transitStatus;
	}
	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return uldNature;
	}
	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}
	/**
	 * @return Returns the lostDate.
	 */
	public LocalDate getLostDate() {
		return this.lostDate;
	}
	/**
	 * @param lostDate The lostDate to set.
	 */
	public void setLostDate(LocalDate lostDate) {
		this.lostDate = lostDate;
	}
	public String getUldGroupCode() {
		return uldGroupCode;
	}
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
	}
	/**
	 * @return Returns the scmDate.
	 */
	public LocalDate getScmDate() {
		return scmDate;
	}
	/**
	 * @param scmDate The scmDate to set.
	 */
	public void setScmDate(LocalDate scmDate) {
		this.scmDate = scmDate;
	}
	/**
	 * @return Returns the scmFlag.
	 */
	public String getScmFlag() {
		return scmFlag;
	}
	/**
	 * @param scmFlag The scmFlag to set.
	 */
	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}
	/**
	 * @return the flightInfo
	 */
	public final String getFlightInfo() {
		return flightInfo;
	}
	/**
	 * @param flightInfo the flightInfo to set
	 */
	public final void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}
	/**
	 * @return the lastSCMDate
	 */
	public LocalDate getLastSCMDate() {
		return lastSCMDate;
	}
	/**
	 * @param lastSCMDate the lastSCMDate to set
	 */
	public void setLastSCMDate(LocalDate lastSCMDate) {
		this.lastSCMDate = lastSCMDate;
	}
	public String getOccupiedULDFlag() {
		return occupiedULDFlag;
	}
	public void setOccupiedULDFlag(String occupiedULDFlag) {
		this.occupiedULDFlag = occupiedULDFlag;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	
}
