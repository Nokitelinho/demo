/*
 * FlownMailSegmentVO.java Created on Jan 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author Soncy
 * VO for Segment DSNs.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 13/02/2007 Soncy Initial draft
 */

public class FlownMailSegmentVO extends AbstractVO {
    /**
     * @return Returns the segmentDSNs.
     */
    public Collection<DSNForFlownSegmentVO> getSegmentDSNs() {
        return segmentDSNs;
    }
    /**
     * @param segmentDSNs The segmentDSNs to set.
     */
    public void setSegmentDSNs(Collection<DSNForFlownSegmentVO> segmentDSNs) {
        this.segmentDSNs = segmentDSNs;
    }
    /**
     * @return Returns the segmentMailBags.
     */
    public Collection<MailBagForFlownSegmentVO> getSegmentMailBags() {
        return segmentMailBags;
    }
    /**
     * @param segmentMailBags The segmentMailBags to set.
     */
    public void setSegmentMailBags(Collection<MailBagForFlownSegmentVO> segmentMailBags) {
        this.segmentMailBags = segmentMailBags;
    }
    /**
     * @return Returns the operationFlag.
     */
    public String getOperationFlag() {
        return operationFlag;
    }

    /**
     * @param operationFlag
     *            The operationFlag to set.
     */
    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }

    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * @param companyCode
     *            The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @return Returns the flightCarrierCode.
     */
    public String getFlightCarrierCode() {
        return flightCarrierCode;
    }

    /**
     * @param flightCarrierCode
     *            The flightCarrierCode to set.
     */
    public void setFlightCarrierCode(String flightCarrierCode) {
        this.flightCarrierCode = flightCarrierCode;
    }

    /**
     * @return Returns the flightCarrierId.
     */
    public int getFlightCarrierId() {
        return flightCarrierId;
    }

    /**
     * @param flightCarrierId
     *            The flightCarrierId to set.
     */
    public void setFlightCarrierId(int flightCarrierId) {
        this.flightCarrierId = flightCarrierId;
    }

    /**
     * @return Returns the flightDate.
     */
    public LocalDate getFlightDate() {
        return flightDate;
    }

    /**
     * @param flightDate
     *            The flightDate to set.
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
     * @param flightNumber
     *            The flightNumber to set.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return Returns the flightSegment.
     */
    public String getFlightSegment() {
        return flightSegment;
    }

    /**
     * @param flightSegment
     *            The flightSegment to set.
     */
    public void setFlightSegment(String flightSegment) {
        this.flightSegment = flightSegment;
    }
    
    /**
	 * @return Returns the flightSegmentStatus.
	 */
	public String getFlightSegmentStatus() {
		return flightSegmentStatus;
	}
	/**
	 * @param flightSegmentStatus The flightSegmentStatus to set.
	 */
	public void setFlightSegmentStatus(String flightSegmentStatus) {
		this.flightSegmentStatus = flightSegmentStatus;
	}

    /**
     * @return Returns the flightSequenceNumber.
     */
    public int getFlightSequenceNumber() {
        return flightSequenceNumber;
    }

    /**
     * @param flightSequenceNumber
     *            The flightSequenceNumber to set.
     */
    public void setFlightSequenceNumber(int flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }

    /**
     * @return Returns the segmentDestination.
     */
    public String getSegmentDestination() {
        return segmentDestination;
    }

    /**
     * @param segmentDestination
     *            The segmentDestination to set.
     */
    public void setSegmentDestination(String segmentDestination) {
        this.segmentDestination = segmentDestination;
    }

    /**
     * @return Returns the segmentOrigin.
     */
    public String getSegmentOrigin() {
        return segmentOrigin;
    }

    /**
     * @param segmentOrigin
     *            The segmentOrigin to set.
     */
    public void setSegmentOrigin(String segmentOrigin) {
        this.segmentOrigin = segmentOrigin;
    }

    /**
     * @return Returns the segmentSerialNumber.
     */
    public int getSegmentSerialNumber() {
        return segmentSerialNumber;
    }

    /**
     * @param segmentSerialNumber
     *            The segmentSerialNumber to set.
     */
    public void setSegmentSerialNumber(int segmentSerialNumber) {
        this.segmentSerialNumber = segmentSerialNumber;
    }

    /**
     * @return Returns the stringFlightDate.
     */
    public String getStringFlightDate() {
        return stringFlightDate;
    }

    /**
     * @param stringFlightDate
     *            The stringFlightDate to set.
     */
    public void setStringFlightDate(String stringFlightDate) {
        this.stringFlightDate = stringFlightDate;
    }
    
	/**
	 * @return Returns the flightStatus.
	 */
	public String getFlightStatus() {
		return flightStatus;
	}
	/**
	 * @param flightStatus The flightStatus to set.
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	
	/**
	 * @return Returns the bagcount.
	 */
	public String getBagCount() {
		return bagCount;
	}
	
	
	/**
	 * @param bagCount
	 */
	public void setBagCount(String bagCount) {
		this.bagCount = bagCount;
	}
	
	/**
	 * @return Returns the bagweight.
	 */
	public String getBagWeight() {
		return bagWeight;
	}
	
	
	/**
	 * @param bagWeight
	 */
	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}
	
	/**
	 * @return Returns the despatch.
	 */
	public String getDespatch() {
		return despatch;
	}
	
	
	/**
	 * @param despatch
	 */
	public void setDespatch(String despatch) {
		this.despatch = despatch;
	}
	
	/**
	 * @return Returns the flightDestination.
	 */
	public String getFlightDestination() {
		return flightDestination;
	}
	
	
	/**
	 * @param flightDestination
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	
	/**
	 * @return Returns the flightOrigin.
	 */
	public String getFlightOrigin() {
		return flightOrigin;
	}
	
	
	/**
	 * @param flightOrigin
	 */
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	
	/**
	 * @return Returns the mailDestination.
	 */
	public String getMailDestination() {
		return mailDestination;
	}
	
	
	/**
	 * @param mailDestination
	 */
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	
	/**
	 * @return Returns the mailOrigin.
	 */
	public String getMailOrigin() {
		return mailOrigin;
	}
	
	
	/**
	 * @param mailOrigin
	 */
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	
	/**
	 * @return Returns the segmentStatus.
	 */
	public String getSegmentStatus() {
		return segmentStatus;
	}
	
	
	/**
	 * @param segmentStatus
	 */
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}

    /**
	 * @return the triggerPoint
	 */
	public String getTriggerPoint() {
		return triggerPoint;
	}
	/**
	 * @param triggerPoint the triggerPoint to set
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}

	/**
     * Company Code
     */
    /**
     * <code>companyCode</code>
     */
    private String companyCode;

    /**
     * Flight Number
     */
    private String flightNumber;

    /**
     * Flight carrier identifier
     */
    private int flightCarrierId;

    /**
     * Flight carrier Code
     */
    private String flightCarrierCode;

    /**
     * Flight Sequence Number
     */
    private int flightSequenceNumber;

    /**
     * Flight Date
     */
    private LocalDate flightDate;

    /**
     * Flight Date
     */
    private String stringFlightDate;

    /**
     * Segment Origin
     */
    private String segmentOrigin;

    /**
     * Segment Destination
     */
    private String segmentDestination;

    /**
     * Flight Segment Origin-Destination
     */
    private String flightSegment;

    /**
     * Segment Serial Number
     */
    private int segmentSerialNumber;

    /**
     * Operation Flag
     */
    private String operationFlag;
    
    private String flightSegmentStatus;
    
    private String flightStatus;
    /**
     * DSNs
     */
    private Collection<DSNForFlownSegmentVO> segmentDSNs;

    /**
     * Mail Bags
     */
    private Collection<MailBagForFlownSegmentVO> segmentMailBags;
    
    
    // Added for Flown Reports..
    private String flightOrigin;
    
    private String flightDestination;
    
    private String segmentStatus;
    
    private String mailOrigin;
    
    private String mailDestination;
    
    private String despatch;
    
    private String bagCount;
    
    private String bagWeight;

    private String triggerPoint;

    private String bagWeightUnit;

	public String getBagWeightUnit() {
		return bagWeightUnit;
	}
	public void setBagWeightUnit(String bagWeightUnit) {
		this.bagWeightUnit = bagWeightUnit;
	}

	
	// added for IASCB-22920 by A-9002		 
		private double displayWgt;	
		private String displayWgtUnit;
	//Ends	
	    public double getDisplayWgt() {
			return displayWgt;
		}
		public void setDisplayWgt(double displayWgt) {
			this.displayWgt = displayWgt;
		}
		public String getDisplayWgtUnit() {
			return displayWgtUnit;
		}
		public void setDisplayWgtUnit(String displayWgtUnit) {
			this.displayWgtUnit = displayWgtUnit;
		}		

}