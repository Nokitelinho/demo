/*
 * ULDListVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author A-2052
 *
 */
public class ULDListMicroVO extends AbstractVO{

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

    private String facilityType;

    private String transitStatus;

    private String uldContour;

    private double tareWeight;

    private double maxGrossWt;

	private String lastUpdateUser;

	/**
	 * For optimistic locking
	 */
    private String lastUpdateTime;


    /**
	 * @return Returns the maxGrossWt.
	 */
	public double getMaxGrossWt() {
		return maxGrossWt;
	}
	/**
	 * @param maxGrossWt The maxGrossWt to set.
	 */
	public void setMaxGrossWt(double maxGrossWt) {
		this.maxGrossWt = maxGrossWt;
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
}
