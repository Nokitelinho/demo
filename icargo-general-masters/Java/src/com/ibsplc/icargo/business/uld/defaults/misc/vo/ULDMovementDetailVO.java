/*
 * ULDMovementDetailVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information 
 *     of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDMovementDetailVO extends AbstractVO
			implements Serializable{
    
    private String companyCode;
    private String uldNumber;
    private int flightCarrierIdentifier;
    private String carrierCode;
    private String flightNumber;
    private LocalDate flightDate;
    private String pointOfLading;
    private String pointOfUnLading;
    private String content;
    private boolean isDummyMovement;
    private String remark;
    private long movementSequenceNumber;
    private long movementSerialNumber;
    
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	//Added by a-3045 for CR AirNZ267 starts
	private String agentCode;
	private String agentName;
    //Added by a-3045 for CR AirNZ267 ends
	private String regdNumber;
	//Added by A-8368 as part of bug
	private LocalDate flightATA;
  
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
    public boolean getIsDummyMovement() {
        return isDummyMovement;
    }
    /**
     * @param isDummyMovement The isDummyMovement to set.
     */
    public void setIsDummyMovement(boolean isDummyMovement) {
        this.isDummyMovement = isDummyMovement;
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
     * 
     * @return
     */
	public long getMovementSequenceNumber() {
		return movementSequenceNumber;
	}
	/**
	 * 
	 * @param movementSequenceNumber
	 */
	public void setMovementSequenceNumber(long movementSequenceNumber) {
		this.movementSequenceNumber = movementSequenceNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * 
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return Returns the movementSerialNumber.
	 */
	public long getMovementSerialNumber() {
		return movementSerialNumber;
	}
	/**
	 * @param movementSerialNumber The movementSerialNumber to set.
	 */
	public void setMovementSerialNumber(long movementSerialNumber) {
		this.movementSerialNumber = movementSerialNumber;
	}
	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return the regdNumber
	 */
	public String getRegdNumber() {
		return regdNumber;
	}
	/**
	 * @param regdNumber the regdNumber to set
	 */
	public void setRegdNumber(String regdNumber) {
		this.regdNumber = regdNumber;
	}
	/**
	 * @param regdNumber the flightATA to set
	 */
	public LocalDate getFlightATA() {
		return flightATA;
	}
	public void setFlightATA(LocalDate flightATA) {
		this.flightATA = flightATA;
	}
}
