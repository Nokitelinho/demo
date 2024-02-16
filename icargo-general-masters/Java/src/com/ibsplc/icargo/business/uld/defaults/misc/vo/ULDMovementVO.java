/*
 * ULDMovementVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDMovementVO extends AbstractVO{
	
	/**
	 * 
	 */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.misc.ULDMovement";
	/**
	 * 
	 */
	public static final String DUMMY ="D";
	/**
	 * 
	 */
	public static final String ACTUAL ="A";
	
	private String carrierCode;
    private String companyCode;
    private String uldNumber;
    private int flightCarrierIdentifier;
    private String flightNumber;
    private LocalDate flightDate;
    private String flightDateString;
    private String pointOfLading;
    private String pointOfUnLading;
    private String content;
    private boolean isDummyMovement;
    private String remark;
    private long movementSequenceNumber;
    private  boolean isUpdateCurrentStation;
    private  String currentStation;
     
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private boolean isDiscrepancyToBeSolved;
	private boolean isHandledCarrierMovement;
	private Collection<String> handledCarrierULDs;

	private String pouLocation;
	private String polLocation;
	private String facilityType;
	private String remarks;
	private LocalDate discrepancyDate;
	private String reportingAirport;
	private String discrepancyCode;
	//Added By Ashraf Binu P
	private String agentCode;
	private String agentName;
//added by nisha for QF1013
	private String scmFlag;
	private LocalDate scmDate;
	
	private String location;
	//Added by A-3415 for ICRD-114051
	private boolean isULDNotExist;
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * 
	 * @return
	 */
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}
	/**
	 * 
	 * @param discrepancyCode
	 */
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}
	public String getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	public boolean isHandledCarrierMovement() {
		return isHandledCarrierMovement;
	}
	public void setHandledCarrierMovement(boolean isHandledCarrierMovement) {
		this.isHandledCarrierMovement = isHandledCarrierMovement;
	}
	/**
	 * @return Returns the movementSequenceNumber.
	 */
	public long getMovementSequenceNumber() {
		return movementSequenceNumber;
	}
	/**
	 * @param movementSequenceNumber The movementSequenceNumber to set.
	 */
	public void setMovementSequenceNumber(long movementSequenceNumber) {
		this.movementSequenceNumber = movementSequenceNumber;
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
	 * @return Returns the updateCurrentStation.
	 */
	public boolean getUpdateCurrentStation() {
		return isUpdateCurrentStation;
	}
	/**
	 * @param isUpdateCurrentStation The updateCurrentStation to set.
	 */
	public void setUpdateCurrentStation(boolean isUpdateCurrentStation) {
		this.isUpdateCurrentStation = isUpdateCurrentStation;
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
     * @return Returns the carrierCode.
     */
    public String getCarrierCode() {
        return carrierCode;
    }
    /**
     * @param carrierCode The carrierCode to set.
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
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
     * @return Returns the content.
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return Returns the flightCarrierIdentifier.
     */
    public int getFlightCarrierIdentifier() {
        return flightCarrierIdentifier;
    }
    /**
     * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
     */
    public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
        this.flightCarrierIdentifier = flightCarrierIdentifier;
    }
    /**
     * @return Returns the flightDate.
     */
    public LocalDate getFlightDate() {
        return flightDate;
    }
    /**
     * @param flightDate The flightDate to set.
     */
    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }
    /**
     * @return Returns the flightNumber.
     */
    public String getFlightNumber() {
        return flightNumber;
    }
    /**
     * @param flightNumber The flightNumber to set.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    /**
     * @return Returns the isDummyMovement.
     */
    public boolean isDummyMovement() {
        return isDummyMovement;
    }
    /**
     * @param isDummyMovement The isDummyMovement to set.
     */
    public void setDummyMovement(boolean isDummyMovement) {
        this.isDummyMovement = isDummyMovement;
    }
    
    /**
     * 
     * @return Returns the DummyMovement
     * 
     */
    public boolean getDummyMovement(){
    	return isDummyMovement;
    }
    /**
     * @return Returns the lastUpdatedTime.
     */
    public LocalDate getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    /**
     * @param lastUpdatedTime The lastUpdatedTime to set.
     */
    public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    /**
     * @return Returns the lastUpdatedUser.
     */
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }
    /**
     * @param lastUpdatedUser The lastUpdatedUser to set.
     */
    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    /**
     * @return Returns the pointOfLading.
     */
    public String getPointOfLading() {
        return pointOfLading;
    }
    /**
     * @param pointOfLading The pointOfLading to set.
     */
    public void setPointOfLading(String pointOfLading) {
        this.pointOfLading = pointOfLading;
    }
    /**
     * @return Returns the pointOfUnLading.
     */
    public String getPointOfUnLading() {
        return pointOfUnLading;
    }
    /**
     * @param pointOfUnLading The pointOfUnLading to set.
     */
    public void setPointOfUnLading(String pointOfUnLading) {
        this.pointOfUnLading = pointOfUnLading;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the uldNumber.
     */
    public String getFlightDateString() {
        return flightDateString;
    }
    /**
     * @param flightDateString The flightDateString to set.
     */
    public void setFlightDateString(String flightDateString) {
        this.flightDateString = flightDateString;
    }
	/**
	 * @return Returns the isDiscrepancyToBeSolved.
	 */
	public boolean isDiscrepancyToBeSolved() {
		return isDiscrepancyToBeSolved;
	}
	/**
	 * @param isDiscrepancyToBeSolved The isDiscrepancyToBeSolved to set.
	 */
	public void setDiscrepancyToBeSolved(boolean isDiscrepancyToBeSolved) {
		this.isDiscrepancyToBeSolved = isDiscrepancyToBeSolved;
	}
	public Collection<String> getHandledCarrierULDs() {
		return handledCarrierULDs;
	}
	public void setHandledCarrierULDs(Collection<String> handledCarrierULDs) {
		this.handledCarrierULDs = handledCarrierULDs;
	}
	public String getPolLocation() {
		return polLocation;
	}
	public void setPolLocation(String polLocation) {
		this.polLocation = polLocation;
	}
	public String getPouLocation() {
		return pouLocation;
	}
	public void setPouLocation(String pouLocation) {
		this.pouLocation = pouLocation;
	}
	public LocalDate getDiscrepancyDate() {
		return discrepancyDate;
	}
	public void setDiscrepancyDate(LocalDate discrepancyDate) {
		this.discrepancyDate = discrepancyDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReportingAirport() {
		return reportingAirport;
	}
	public void setReportingAirport(String reportingAirport) {
		this.reportingAirport = reportingAirport;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	public boolean isULDNotExist() {
		return isULDNotExist;
	}
	public void setULDNotExist(boolean isULDNotExist) {
		this.isULDNotExist = isULDNotExist;
	}

}
