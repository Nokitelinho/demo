/*
 * ListULDForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-1347
 *
 */
public class ListULDForm extends ScreenModel {
    
	private static final String BUNDLE = "listuldResources";
	
	private String bundle;    
	
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.listuld";

	private String uldNumber;
    
    private String uldGroupCode;
    
    private String uldTypeCode;
    
    private String airlineCode;
    
    private String overallStatus;
    
    private String damageStatus;
    
    private String cleanlinessStatus;
    
    private String manufacturer;
    
    private String location;
    
    private String ownerStation;
    
    private String currentStation;
    
    private String lastMovementDate = "";
    
    private String uldRangeFrom;
    
    private String uldRangeTo;
    
    private String displayPage = "1";
    
    private String lastPageNum = "0";
    
    private String[] selectedRows;
    
    private String[] uldNumbers;
    
    private String[] transitStatus;
    
    private String listStatus;
    
    private String statusFlag;
    
    private String screenLoadStatus;
    
    private String disableStatus;
    
    private String uldNature;
    
    //Added By A-6841 for CRQ ICRD-155382
    private String inTransitFlag;
    //Added By a-2412 for Agent Filter
    private String agentCode;
    private String agentName;
    
    //Added by a-3045 for CR AirNZ415 starts
    private String ownerAirline;
    private String fromDate = "";
    private String toDate = "";
    //Added by a-3045 for CR AirNZ415 ends
    
    private String levelType;
    private String levelValue;
    private String content;
    //added by a-3045 for bug 46783 on 19May09 starts
    private String offairportFlag;
    private String occupiedULDFlag;
    //added by a-3045 for bug 46783 on 19May09 ends
    private String comboFlag;
    //Added by a-3459 for BUG 27324 for implementing sorting for checkbox which uses images in jsp.ie OffAirport
    private String checkOffAirport;
    private String countTotalFlag;
    //Added by A-7359 for ICRD-228547
    private boolean autoCollapse;
    private String filterSummaryDetails;
    // Added by A-7918 for the ICRD-233164
    private String oalUldOnly;
    /**
	 * @return the OalUldOnly
	 */
    public String getOalUldOnly() {
		return oalUldOnly;
	}
	public void setOalUldOnly(String oalUldOnly) {
		this.oalUldOnly = oalUldOnly;
	}
    
    /**
	 * @return the countTotalFlag
	 */
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	/**
	 * @param countTotalFlag the countTotalFlag to set
	 */
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}

	/**
	 * @return the checkOffAirport
	 */
	public String getCheckOffAirport() {
		return checkOffAirport;
	}

	/**
	 * @param checkOffAirport the checkOffAirport to set
	 */
	public void setCheckOffAirport(String checkOffAirport) {
		this.checkOffAirport = checkOffAirport;
	}

	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the levelType
	 */
	public String getLevelType() {
		return levelType;
	}

	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	/**
	 * @return the levelValue
	 */
	public String getLevelValue() {
		return levelValue;
	}

	/**
	 * @param levelValue the levelValue to set
	 */
	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

    public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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
	 * @return Returns the screenLoadStatus.
	 */
	public String getScreenLoadStatus() {
		return screenLoadStatus;
	}

	/**
	 * @param screenLoadStatus The screenLoadStatus to set.
	 */
	public void setScreenLoadStatus(String screenLoadStatus) {
		this.screenLoadStatus = screenLoadStatus;
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
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
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
	 * @return Returns the airlineidentifier.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * 
	 * @param airlineCode
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
	 * @return Returns the lastMovementDate.
	 */
	public String getLastMovementDate() {
		return lastMovementDate;
	}

	/**
	 * @param lastMovementDate The lastMovementDate to set.
	 */
	public void setLastMovementDate(String lastMovementDate) {
		this.lastMovementDate = lastMovementDate;
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
	 * @return Returns the uldGroupCode.
	 */
	public String getUldGroupCode() {
		return uldGroupCode;
	}

	/**
	 * @param uldGroupCode The uldGroupCode to set.
	 */
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
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
	 * @return Returns the uldRangeFrom.
	 */
	public String getUldRangeFrom() {
		return uldRangeFrom;
	}

	/**
	 * @param uldRangeFrom The uldRangeFrom to set.
	 */
	public void setUldRangeFrom(String uldRangeFrom) {
		this.uldRangeFrom = uldRangeFrom;
	}

	/**
	 * @return Returns the uldRangeTo.
	 */
	public String getUldRangeTo() {
		return uldRangeTo;
	}

	/**
	 * @param uldRangeTo The uldRangeTo to set.
	 */
	public void setUldRangeTo(String uldRangeTo) {
		this.uldRangeTo = uldRangeTo;
	}

	/**
	 * @return Returns the uldTypeCode.
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}

	/**
	 * @param uldTypeCode The uldTypeCode to set.
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
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
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return listStatus;
	}

	/**
	 * @param listStatus The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * @return Returns the uldNumbers.
	 */
	public String[] getUldNumbers() {
		return uldNumbers;
	}

	/**
	 * @param uldNumbers The uldNumbers to set.
	 */
	public void setUldNumbers(String[] uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	/**
	 * @return Returns the disableStatus.
	 */
	public String getDisableStatus() {
		return disableStatus;
	}

	/**
	 * @param disableStatus The disableStatus to set.
	 */
	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	/**
	 * @return Returns the transitStatus.
	 */
	public String[] getTransitStatus() {
		return transitStatus;
	}

	/**
	 * @param transitStatus The transitStatus to set.
	 */
	public void setTransitStatus(String[] transitStatus) {
		this.transitStatus = transitStatus;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the ownerAirline
	 */
	public String getOwnerAirline() {
		return ownerAirline;
	}

	/**
	 * @param ownerAirline the ownerAirline to set
	 */
	public void setOwnerAirline(String ownerAirline) {
		this.ownerAirline = ownerAirline;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the occupiedULDFlag
	 */
	public String getOccupiedULDFlag() {
		return occupiedULDFlag;
	}

	/**
	 * @param occupiedULDFlag the occupiedULDFlag to set
	 */
	public void setOccupiedULDFlag(String occupiedULDFlag) {
		this.occupiedULDFlag = occupiedULDFlag;
	}

	/**
	 * @return the offairportFlag
	 */
	public String getOffairportFlag() {
		return offairportFlag;
	}

	/**
	 * @param offairportFlag the offairportFlag to set
	 */
	public void setOffairportFlag(String offairportFlag) {
		this.offairportFlag = offairportFlag;
	}
	/**
	 * @return the inTransitFlag
	 */
	public String getInTransitFlag() {
		return inTransitFlag;
	}
	/**
	 * @param inTransitFlag the inTransitFlag to set
	 */
	public void setInTransitFlag(String inTransitFlag) {
		this.inTransitFlag = inTransitFlag;
	}
    /**
     * 
     * 	Method		:	ListULDForm.isAutoCollapse
     *	Added by 	:	A-7359 on 23-Jan-2018
     * 	Used for 	:	ICRD-228547
     *	Parameters	:	@return 
     *	Return type	: 	boolean
     */

	public boolean isAutoCollapse() {
		return autoCollapse;
	}
	/**
	 * 
	 * 	Method		:	ListULDForm.setAutoCollapse
	 *	Added by 	:	A-7359 on 23-Jan-2018
	 * 	Used for 	:	ICRD-228547
	 *	Parameters	:	@param autoCollapse 
	 *	Return type	: 	void
	 */
	public void setAutoCollapse(boolean autoCollapse) {
		this.autoCollapse = autoCollapse;
	}
	/**
	 * 
	 * 	Method		:	ListULDForm.getFilterSummaryDetails
	 *	Added by 	:	A-7359 on 23-Jan-2018
	 * 	Used for 	:	ICRD-228547
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getFilterSummaryDetails() {
		return filterSummaryDetails;
	}
	/**
	 * 
	 * 	Method		:	ListULDForm.setFilterSummaryDetails
	 *	Added by 	:	A-7359 on 23-Jan-2018
	 * 	Used for 	:	ICRD-228547
	 *	Parameters	:	@param filterSummaryDetails 
	 *	Return type	: 	void
	 */
	public void setFilterSummaryDetails(String filterSummaryDetails) {
		this.filterSummaryDetails = filterSummaryDetails;
	}
 }
