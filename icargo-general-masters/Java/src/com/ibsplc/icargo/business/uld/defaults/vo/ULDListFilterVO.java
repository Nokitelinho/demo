/*
 * ULDListFilterVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.DynamicSearchVO;

/**
 * @author A-1347
 *
 */
public class ULDListFilterVO extends DynamicSearchVO{
    
    private String companyCode;
    
    private String uldNumber;
    
    private String uldGroupCode;
    
    private String uldTypeCode;
    
    private int airlineidentifier;
    
    private String airlineCode;
        
    private String overallStatus;
    
    private String damageStatus;
    
    private String cleanlinessStatus;
    
    private String manufacturer;
    
    private String location;
    
    private String ownerStation;
    
    private String currentStation;
    
    private LocalDate lastMovementDate;
    
    private int uldRangeFrom;
    
    private int uldRangeTo;
    
    private String facility;
    
    private LocalDate prevDepreciationDate;
    
    private LocalDate currentDepreciationDate;
    private String uldNature;
    
    private int pageNumber;
    
	//Added By A-6841 for CRQ ICRD-155382
    private String inTransitFlag;
    //Added By A-2412 for Agent Filter
    private String agentCode;
    private String agentName;
    
    //added by a-3045 for CR AirNZ415 starts
    private String ownerAirline;
    private int ownerAirlineidentifier;
    private LocalDate fromDate;
    private LocalDate toDate;
    //added by a-3045 for CR AirNZ415 ends
 
    private String levelType;
    private String levelValue;
    private String content;
    //added by a-3045 for bug 46783 on 19May09 starts
    private String offairportFlag;
    private String occupiedULDFlag;
    //added by a-3045 for bug 46783 on 19May09 ends
    //added by a-3045 for bug 26551 starts
    private boolean isFromListULD;
    //added by a-3045 for bug 26551 ends
    // added by a-3459 for QF CR 1186 starts
    private int totalRecords;
    // added by a-3459 for QF CR 1186 ends
    private String printType;
 // Added by A-7918 for the ICRD-233164
    private String oalUldOnly;
    private String airlineID;
    /**
	 * @return the airlineID
	 */
    public String getAirlineID() {
		return airlineID;
	}
	public void setAirlineID(String airlineID) {
		this.airlineID = airlineID;
	}
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
	
	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
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
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
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
    private boolean viewByNatureFlag;
    
    
 
    public boolean isViewByNatureFlag() {
		return viewByNatureFlag;
	}
	public void setViewByNatureFlag(boolean viewByNatureFlag) {
		this.viewByNatureFlag = viewByNatureFlag;
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
     * @return Returns the airlineidentifier.
     */
    public int getAirlineidentifier() {
        return airlineidentifier;
    }
    /**
     * @param airlineidentifier The airlineidentifier to set.
     */
    public void setAirlineidentifier(int airlineidentifier) {
        this.airlineidentifier = airlineidentifier;
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
    public LocalDate getLastMovementDate() {
        return lastMovementDate;
    }
    /**
     * @param lastMovementDate The lastMovementDate to set.
     */
    public void setLastMovementDate(LocalDate lastMovementDate) {
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
    public int getUldRangeFrom() {
        return uldRangeFrom;
    }
    /**
     * @param uldRangeFrom The uldRangeFrom to set.
     */
    public void setUldRangeFrom(int uldRangeFrom) {
        this.uldRangeFrom = uldRangeFrom;
    }
    /**
     * @return Returns the uldRangeTo.
     */
    public int getUldRangeTo() {
        return uldRangeTo;
    }
    /**
     * @param uldRangeTo The uldRangeTo to set.
     */
    public void setUldRangeTo(int uldRangeTo) {
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
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the currentDepreciationDate.
	 */
	public LocalDate getCurrentDepreciationDate() {
		return this.currentDepreciationDate;
	}
	/**
	 * @param currentDepreciationDate The currentDepreciationDate to set.
	 */
	public void setCurrentDepreciationDate(LocalDate currentDepreciationDate) {
		this.currentDepreciationDate = currentDepreciationDate;
	}
	/**
	 * @return Returns the prevDepreciationDate.
	 */
	public LocalDate getPrevDepreciationDate() {
		return this.prevDepreciationDate;
	}
	/**
	 * @param prevDepreciationDate The prevDepreciationDate to set.
	 */
	public void setPrevDepreciationDate(LocalDate prevDepreciationDate) {
		this.prevDepreciationDate = prevDepreciationDate;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the ownerAirlineidentifier
	 */
	public int getOwnerAirlineidentifier() {
		return ownerAirlineidentifier;
	}
	/**
	 * @param ownerAirlineidentifier the ownerAirlineidentifier to set
	 */
	public void setOwnerAirlineidentifier(int ownerAirlineidentifier) {
		this.ownerAirlineidentifier = ownerAirlineidentifier;
	}
	/**
	 * @return the isFromListULD
	 */
	public boolean isFromListULD() {
		return isFromListULD;
	}
	/**
	 * @param isFromListULD the isFromListULD to set
	 */
	public void setFromListULD(boolean isFromListULD) {
		this.isFromListULD = isFromListULD;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}
	/**
	 * @param facility the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}
	/**
	 * @return  inTransitFlag
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
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	
	
	
}
