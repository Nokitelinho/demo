/*
 * ULDListVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1347
 *
 */
public class ULDListVO extends AbstractVO{

    private String companyCode;

    private String uldNumber;

    private String uldGroupCode;

    private String uldTypeCode;

    private String manufacturer;

    private String partyLoaned;

    private String partyBorrowed;

    private String overallStatus;

    private String damageStatus;

    private String cleanlinessStatus;

    private String location;

    private String ownerStation;

    private String currentStation;

    private LocalDate lastMovementDate;

    private String facilityType;

    private String transitStatus;

    private String uldContour;

    private Measure tareWeight;

    private Measure maxGrossWt;

    private String uldNature;

    private String lastUpdateUser;

    private LocalDate lastUpdateTime;

    //Added by Sreekumar S
    private int daysElapsed;

    private String operatingAirline;

    private String facilityCode;
    private String content;
    private String regionCode;
    private String countryCode;
  //  private boolean isOffAirport;
   // private boolean isOccupied;
    private String offAirport;
    private String occupied;

    private String flightInfo;
	private String ownerAirlineCode;
	private String uldTypeNumber;
	//added as part of ICRD-232684 by A-4393 starts 
    private String remainingDayToReturn;
  //added as part of ICRD-232684 by A-4393 ends
	/**
	 * @return the ownerAirlineCode
	 */
	public String getOwnerAirlineCode() {
		return offAirport;
	}
	/**
	 * @param ownerAirlineCode
	 */
	public void setOwnerAirlineCode(String ownerAirlineCode) {
		this.ownerAirlineCode = ownerAirlineCode;
	}

	/**
	 * @return the isOccupied

	public boolean isOccupied() {
		return isOccupied;
	}
	/**
	 * @param isOccupied the isOccupied to set

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	/**
	 * @return the isOffAirport

	public boolean isOffAirport() {
		return isOffAirport;
	}
	/**
	 * @param isOffAirport the isOffAirport to set
		public void setOffAirport(boolean isOffAirport) {
		this.isOffAirport = isOffAirport;
	}
	
	/**
    private String operatingAirline;
    
    private String facilityCode;
    private String content;
    private String regionCode;
    private String countryCode;
  //  private boolean isOffAirport;
   // private boolean isOccupied;
    private String offAirport;
    private String occupied;
    
    private String flightInfo;
    
    /**
	 * @return the occupied
	 */
	public String getOccupied() {
		return occupied;
	}
	/**
	 * @param occupied the occupied to set
	 */
	public void setOccupied(String occupied) {
		this.occupied = occupied;
	}
	/**
	 * @return the offAirport
	 */
	public String getOffAirport() {
		return offAirport;
	}
	/**
	 * @param offAirport the offAirport to set
	 */
	public void setOffAirport(String offAirport) {
		this.offAirport = offAirport;
	}
	/**
	 * @return the isOccupied
	 
	public boolean isOccupied() {
		return isOccupied;
	}
	/**
	 * @param isOccupied the isOccupied to set
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	/**
	 * @return the isOffAirport
	
	public boolean isOffAirport() {
		return isOffAirport;
	}
	/**
	 * @param isOffAirport the isOffAirport to set
		public void setOffAirport(boolean isOffAirport) {
		this.isOffAirport = isOffAirport;
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
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the facilityCode
	 */
	public String getFacilityCode() {
		return facilityCode;
	}
	/**
	 * @param facilityCode the facilityCode to set
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
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
	 * @return the maxGrossWt
	 */
	public Measure getMaxGrossWt() {
		return maxGrossWt;
	}
	/**
	 * @param maxGrossWt the maxGrossWt to set
	 */
	public void setMaxGrossWt(Measure maxGrossWt) {
		this.maxGrossWt = maxGrossWt;
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
	 *
	 * @return
	 */
	public String getTransitStatus() {
		return transitStatus;
	}
	/**
	 *
	 * @param transitStatus
	 */
	public void setTransitStatus(String transitStatus) {
		this.transitStatus = transitStatus;
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
     * @return Returns the partyBorrowed.
     */
    public String getPartyBorrowed() {
        return partyBorrowed;
    }
    /**
     * @param partyBorrowed The partyBorrowed to set.
     */
    public void setPartyBorrowed(String partyBorrowed) {
        this.partyBorrowed = partyBorrowed;
    }
    /**
     * @return Returns the partyLoaned.
     */
    public String getPartyLoaned() {
        return partyLoaned;
    }
    /**
     * @param partyLoaned The partyLoaned to set.
     */
    public void setPartyLoaned(String partyLoaned) {
        this.partyLoaned = partyLoaned;
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
	 * @return Returns the daysElapsed.
	 */
	public int getDaysElapsed() {
		return daysElapsed;
	}
	/**
	 * @param daysElapsed The daysElapsed to set.
	 */
	public void setDaysElapsed(int daysElapsed) {
		this.daysElapsed = daysElapsed;
	}
	/**
	 * @return Returns the operatingAirline.
	 */
	public String getOperatingAirline() {
		return operatingAirline;
	}
	/**
	 * @param operatingAirline The operatingAirline to set.
	 */
	public void setOperatingAirline(String operatingAirline) {
		this.operatingAirline = operatingAirline;
	}
	/**
	 * @return the flightInfo
	 */
	public String getFlightInfo() {
		return flightInfo;
	}
	/**
	 * @param flightInfo the flightInfo to set
	 */
	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}
	public String getUldTypeNumber() {
		return uldTypeNumber;
	}
	public void setUldTypeNumber(String uldTypeNumber) {
		this.uldTypeNumber = uldTypeNumber;
	}
	public String getRemainingDayToReturn() {
		return remainingDayToReturn;
	}
	public void setRemainingDayToReturn(String remainingDayToReturn) {
		this.remainingDayToReturn = remainingDayToReturn;
	}
	
}
