/*
 * MaintainULDForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;


/**
 * @author A-2001
 *
 */
public class MaintainULDForm extends ScreenModel {
    
	private static final String BUNDLE = "maintainuldResources";
	
	private String bundle;    

	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";
	
	private static final String SCREENID = "uld.defaults.maintainuld";

	private String uldNumber;
	
	private boolean createMultiple;
	
	private String totalNoofUlds;
	
	private String uldContour;
       
	@MeasureAnnotation(mappedValue="tareWtMeasure",unit="displayTareWeightUnit",unitType="WGT")
	private String displayTareWeight;
	
	private Measure tareWtMeasure;
     
    private String displayTareWeightUnit;
    
    @MeasureAnnotation(mappedValue="structWeightMeasure",unit="displayStructuralWeightUnit",unitType="WGT")
    private String displayStructuralWeight;
    
    /**
	 * @return the structWeightMeasure
	 */
	public Measure getStructWeightMeasure() {
		return structWeightMeasure;
	}
	/**
	 * @param structWeightMeasure the structWeightMeasure to set
	 */
	public void setStructWeightMeasure(Measure structWeightMeasure) {
		this.structWeightMeasure = structWeightMeasure;
	}
	private Measure structWeightMeasure;
    
    private String displayStructuralWeightUnit;
    
    @MeasureAnnotation(mappedValue="baseLengthMeasure",unit="displayDimensionUnit",unitType=UnitConstants.DIMENSION)
    private String displayBaseLength;
    
    private Measure baseLengthMeasure;
    
    @MeasureAnnotation(mappedValue="baseWidthMeasure",unit="displayDimensionUnit",unitType=UnitConstants.DIMENSION)
    private String displayBaseWidth;

    private Measure baseWidthMeasure;
    // mappedValue corrected as part of ICRD-244772
    @MeasureAnnotation(mappedValue="baseHeightMeasure",unit="displayDimensionUnit",unitType=UnitConstants.DIMENSION)
    private String displayBaseHeight;

    private Measure baseHeightMeasure;
    
    /**
	 * @return the tareWtMeasure
	 */
	public Measure getTareWtMeasure() {
		return tareWtMeasure;
	}
	/**
	 * @param tareWtMeasure the tareWtMeasure to set
	 */
	public void setTareWtMeasure(Measure tareWtMeasure) {
		this.tareWtMeasure = tareWtMeasure;
	}
	/**
	 * @return the baseLengthMeasure
	 */
	public Measure getBaseLengthMeasure() {
		return baseLengthMeasure;
	}
	/**
	 * @param baseLengthMeasure the baseLengthMeasure to set
	 */
	public void setBaseLengthMeasure(Measure baseLengthMeasure) {
		this.baseLengthMeasure = baseLengthMeasure;
	}
	/**
	 * @return the baseWidthMeasure
	 */
	public Measure getBaseWidthMeasure() {
		return baseWidthMeasure;
	}
	/**
	 * @param baseWidthMeasure the baseWidthMeasure to set
	 */
	public void setBaseWidthMeasure(Measure baseWidthMeasure) {
		this.baseWidthMeasure = baseWidthMeasure;
	}
	/**
	 * @return the baseHeightMeasure
	 */
	public Measure getBaseHeightMeasure() {
		return baseHeightMeasure;
	}
	/**
	 * @param baseHeightMeasure the baseHeightMeasure to set
	 */
	public void setBaseHeightMeasure(Measure baseHeightMeasure) {
		this.baseHeightMeasure = baseHeightMeasure;
	}
	private String displayDimensionUnit;
    
    private String uldType;
    
    private String currentStation;
    
    private String ownerStation;
    
    private String overallStatus;
    
    private String cleanlinessStatus;
    
    private String damageStatus;
    
    private String location;
    
    private String vendor;
    
    private String manufacturer;
    
    private String uldSerialNumber;
    
    private String purchaseDate = "";
    
    private String purchaseInvoiceNumber;
    
    private String uldPrice;
    
    private String uldPriceUnit;
    
    private String iataReplacementCost;
    
    private String iataReplacementCostUnit;

    private String currentValue;
    
    private String currentValueUnit;
    
    private String remarks;
    
    private String operationalAirlineCode;
    
    private String operationalOwnerAirlineCode;
    private String ownerAirlineCode;
    
    private String uldOwnerCode;
    private String statusFlag;
    
    public String getUldOwnerCode() {
		return uldOwnerCode;
	}
	public void setUldOwnerCode(String uldOwnerCode) {
		this.uldOwnerCode = uldOwnerCode;
	}
	 public String getOperationalOwnerAirlineCode() {
		return operationalOwnerAirlineCode;
	}
	public void setOperationalOwnerAirlineCode(String operationalOwnerAirlineCode) {
		this.operationalOwnerAirlineCode = operationalOwnerAirlineCode;
	}
    private String screenloadstatus;
    
    private String selectedRows;
    
    private String closeStatus;
    
    private String transitStatus;
    
    private String transitDisable;
    
    private String displayPage = "1";
    
    private String currentPage = "1";
    
    private String lastPageNum = "0";
    
    private String totalRecords = "0";
    
    private String[] uldMaintainNumbers;
    
    private String uldNumbersSelected;
    
    private String structuralFlag;
    
    private String facilityType;
    
    private String uldNature="GEN";

    private String saveIndFlag;
    
    private String currency;
    // Added by Preet for Air NZ 447
    private String manufactureDate;
    private String lifeSpan;
    private String tsoNumber;
    
    
    private String flagVar;
    //Added by A-7530 for ICRD-213974
    private String fromScreen;
    
    
    /**
	 * 	Getter for fromScreen 
	 *	Added by : A-7530 on 26-Sep-2017
	 * 	Used for :
	 */
	public String getFromScreen() {
		return fromScreen;
	}
	/**
	 *  @param fromScreen the fromScreen to set
	 * 	Setter for fromScreen 
	 *	Added by : A-7530 on 26-Sep-2017
	 * 	Used for :
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
    public String getFlagVar() {
		return flagVar;
	}
	public void setFlagVar(String flagVar) {
		this.flagVar = flagVar;
	}
	/**
	 * @return Returns the saveIndFlag.
	 */
	public String getSaveIndFlag() {
		return saveIndFlag;
	}
	/**
	 * @param uldNature The saveIndFlag to set.
	 */
	public void setSaveIndFlag(String saveIndFlag) {
		this.saveIndFlag = saveIndFlag;
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
	 * @return Returns the facilityType.
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
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
     * Method to return the product the screen is associated with
     * 
     * @return String
     */
    public String getProduct() {
        return PRODUCT;
    }
    
    /**
     * Method to return the sub product the screen is associated with
     * 
     * @return String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    
    /**
     * Method to return the id the screen is associated with
     * 
     * @return String
     */
    public String getScreenId() {
        return SCREENID;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
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
	public String getCurrentValue() {
		return currentValue;
	}

	/**
	 * @param currentValue The currentValue to set.
	 */
	public void setCurrentValue(String currentValue) {
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
	public String getDisplayBaseHeight() {
		return displayBaseHeight;
	}

	/**
	 * @param displayBaseHeight The displayBaseHeight to set.
	 */
	public void setDisplayBaseHeight(String displayBaseHeight) {
		this.displayBaseHeight = displayBaseHeight;
	}

	/**
	 * @return Returns the displayBaseLength.
	 */
	public String getDisplayBaseLength() {
		return displayBaseLength;
	}

	/**
	 * @param displayBaseLength The displayBaseLength to set.
	 */
	public void setDisplayBaseLength(String displayBaseLength) {
		this.displayBaseLength = displayBaseLength;
	}

	/**
	 * @return Returns the displayBaseWidth.
	 */
	public String getDisplayBaseWidth() {
		return displayBaseWidth;
	}

	/**
	 * @param displayBaseWidth The displayBaseWidth to set.
	 */
	public void setDisplayBaseWidth(String displayBaseWidth) {
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
	public String getDisplayStructuralWeight() {
		return displayStructuralWeight;
	}

	/**
	 * @param displayStructuralWeight The displayStructuralWeight to set.
	 */
	public void setDisplayStructuralWeight(String displayStructuralWeight) {
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
	public void setDisplayStructuralWeightUnit(String displayStructuralWeightUnit) {
		this.displayStructuralWeightUnit = displayStructuralWeightUnit;
	}

	/**
	 * @return Returns the displayTareWeight.
	 */
	public String getDisplayTareWeight() {
		return displayTareWeight;
	}

	/**
	 * @param displayTareWeight The displayTareWeight to set.
	 */
	public void setDisplayTareWeight(String displayTareWeight) {
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
	public String getIataReplacementCost() {
		return iataReplacementCost;
	}

	/**
	 * @param iataReplacementCost The iataReplacementCost to set.
	 */
	public void setIataReplacementCost(String iataReplacementCost) {
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
	public String getUldPrice() {
		return uldPrice;
	}

	/**
	 * @param uldPrice The uldPrice to set.
	 */
	public void setUldPrice(String uldPrice) {
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
	 * @return Returns the createMultiple.
	 */
	public boolean getCreateMultiple() {
		return createMultiple;
	}

	/**
	 * @param createMultiple The createMultiple to set.
	 */
	public void setCreateMultiple(boolean createMultiple) {
		this.createMultiple = createMultiple;
	}

	/**
	 * @return Returns the totalNoofUlds.
	 */
	public String getTotalNoofUlds() {
		return totalNoofUlds;
	}

	/**
	 * @param totalNoofUlds The totalNoofUlds to set.
	 */
	public void setTotalNoofUlds(String totalNoofUlds) {
		this.totalNoofUlds = totalNoofUlds;
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
	 * @return Returns the operationalAirlineCode.
	 */
	public String getOperationalAirlineCode() {
		return operationalAirlineCode;
	}

	/**
	 * @param operationalAirlineCode The operationalAirlineCode to set.
	 */
	public void setOperationalAirlineCode(String operationalAirlineCode) {
		this.operationalAirlineCode = operationalAirlineCode;
	}

	/**
	 * @return Returns the ownerAirlineCode.
	 */
	public String getOwnerAirlineCode() {
		return ownerAirlineCode;
	}

	/**
	 * @param ownerAirlineCode The ownerAirlineCode to set.
	 */
	public void setOwnerAirlineCode(String ownerAirlineCode) {
		this.ownerAirlineCode = ownerAirlineCode;
	}

	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the selectedRows.
	 */
	public String getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public String getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return Returns the screenloadstatus.
	 */
	public String getScreenloadstatus() {
		return screenloadstatus;
	}

	/**
	 * @param screenloadstatus The screenloadstatus to set.
	 */
	public void setScreenloadstatus(String screenloadstatus) {
		this.screenloadstatus = screenloadstatus;
	}

	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * @param closeStatus The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	/**
	 * @return Returns the uldMaintainNumbers.
	 */
	public String[] getUldMaintainNumbers() {
		return uldMaintainNumbers;
	}

	/**
	 * @param uldMaintainNumbers The uldMaintainNumbers to set.
	 */
	public void setUldMaintainNumbers(String[] uldMaintainNumbers) {
		this.uldMaintainNumbers = uldMaintainNumbers;
	}

	/**
	 * @return Returns the uldNumbersSelected.
	 */
	public String getUldNumbersSelected() {
		return uldNumbersSelected;
	}

	/**
	 * @param uldNumbersSelected The uldNumbersSelected to set.
	 */
	public void setUldNumbersSelected(String uldNumbersSelected) {
		this.uldNumbersSelected = uldNumbersSelected;
	}

	/**
	 * @return Returns the structuralFlag.
	 */
	public String getStructuralFlag() {
		return structuralFlag;
	}

	/**
	 * @param structuralFlag The structuralFlag to set.
	 */
	public void setStructuralFlag(String structuralFlag) {
		this.structuralFlag = structuralFlag;
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
	 * @return Returns the transitDisable.
	 */
	public String getTransitDisable() {
		return transitDisable;
	}

	/**
	 * @param transitDisable The transitDisable to set.
	 */
	public void setTransitDisable(String transitDisable) {
		this.transitDisable = transitDisable;
	}
	/**
	 * @return the lifeSpan
	 */
	public String getLifeSpan() {
		return lifeSpan;
	}
	/**
	 * @param lifeSpan the lifeSpan to set
	 */
	public void setLifeSpan(String lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	/**
	 * @return the manufactureDate
	 */
	public String getManufactureDate() {
		return manufactureDate;
	}
	/**
	 * @param manufactureDate the manufactureDate to set
	 */
	public void setManufactureDate(String manufactureDate) {
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
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	  
    
 }
