/*
 * MailBagForFlownSegmentVO.java Created on Aug 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.vo;

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
public class MailBagForFlownSegmentVO extends AbstractVO {
   
	// added for IASCB-22920 by A-9002		 
	private double displayWgt;	 
    private String displayWgtUnit;

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
	//Ends
	/**
     * @return Returns the mailBagWeight.
     */
    public float getMailBagWeight() {
        return mailBagWeight;
    }
    /**
     * @param mailBagWeight The mailBagWeight to set.
     */
    public void setMailBagWeight(float mailBagWeight) {
        this.mailBagWeight = mailBagWeight;
    }
    /**
     * @return Returns the mailIdentifier.
     */
    public String getMailIdentifier() {
        return mailIdentifier;
    }
    /**
     * @param mailIdentifier The mailIdentifier to set.
     */
    public void setMailIdentifier(String mailIdentifier) {
        this.mailIdentifier = mailIdentifier;
    }
    /**
     * @return Returns the destinationOfficeOfExchange.
     */
    public String getDestinationOfficeOfExchange() {
        return destinationOfficeOfExchange;
    }

    /**
     * @param destinationOfficeOfExchange
     *            The destinationOfficeOfExchange to set.
     */
    public void setDestinationOfficeOfExchange(
            String destinationOfficeOfExchange) {
        this.destinationOfficeOfExchange = destinationOfficeOfExchange;
    }

    /**
     * @return Returns the dsnNumber.
     */
    public String getDsnNumber() {
        return dsnNumber;
    }

    /**
     * @param dsnNumber
     *            The dsnNumber to set.
     */
    public void setDsnNumber(String dsnNumber) {
        this.dsnNumber = dsnNumber;
    }

    /**
     * @return Returns the mailBagCount.
     */
    public int getMailBagCount() {
        return mailBagCount;
    }

    /**
     * @param mailBagCount
     *            The mailBagCount to set.
     */
    public void setMailBagCount(int mailBagCount) {
        this.mailBagCount = mailBagCount;
    }

    /**
     * @return Returns the mailCategoryCode.
     */
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }

    /**
     * @param mailCategoryCode
     *            The mailCategoryCode to set.
     */
    public void setMailCategoryCode(String mailCategoryCode) {
        this.mailCategoryCode = mailCategoryCode;
    }

    /**
     * @return Returns the mailSubclass.
     */
    public String getMailSubclass() {
        return mailSubclass;
    }

    /**
     * @param mailSubclass
     *            The mailSubclass to set.
     */
    public void setMailSubclass(String mailSubclass) {
        this.mailSubclass = mailSubclass;
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
     * @return Returns the originOfficeOfExchange.
     */
    public String getOriginOfficeOfExchange() {
        return originOfficeOfExchange;
    }

    /**
     * @param originOfficeOfExchange
     *            The originOfficeOfExchange to set.
     */
    public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
        this.originOfficeOfExchange = originOfficeOfExchange;
    }

    /**
     * @return Returns the serialNumber.
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber
     *            The serialNumber to set.
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return Returns the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year
     *            The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Returns the assignedDate.
     */
    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    /**
     * @param assignedDate
     *            The assignedDate to set.
     */
    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    /**
     * @return Returns the assigneeCode.
     */
    public String getAssigneeCode() {
        return assigneeCode;
    }

    /**
     * @param assigneeCode
     *            The assigneeCode to set.
     */
    public void setAssigneeCode(String assigneeCode) {
        this.assigneeCode = assigneeCode;
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
     * @return Returns the exceptionCode.
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @param exceptionCode
     *            The exceptionCode to set.
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
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
     * @return Returns the resolvedDate.
     */
    public LocalDate getResolvedDate() {
        return resolvedDate;
    }

    /**
     * @param resolvedDate
     *            The resolvedDate to set.
     */
    public void setResolvedDate(LocalDate resolvedDate) {
        this.resolvedDate = resolvedDate;
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
	 * @return Returns the highestReceptacleNumber.
	 */
	public String getHighestReceptacleNumber() {
		return highestReceptacleNumber;
	}
	/**
	 * @param highestReceptacleNumber The highestReceptacleNumber to set.
	 */
	public void setHighestReceptacleNumber(String highestReceptacleNumber) {
		this.highestReceptacleNumber = highestReceptacleNumber;
	}
	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}
	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}
	/**
	 * @return Returns the registeredIndicator.
	 */
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	/**
	 * @param registeredIndicator The registeredIndicator to set.
	 */
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
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
     * Exception Code
     */
    private String exceptionCode;

    /**
     * Segment Origin
     */
    private String segmentOrigin;

    /**
     * Segment Destination
     */
    private String segmentDestination;

    /**
     * Assignee User Code
     */
    private String assigneeCode;

    /**
     * Assigned Date
     */
    private LocalDate assignedDate;

    /**
     * Resolved Date
     */
    private LocalDate resolvedDate;

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

    /**
     * DSN Number
     */
    private String dsnNumber;

    /**
     * Mail Bag Count
     */
    private int mailBagCount;

    /**
     * Exception Serial Number
     */
    private int serialNumber;

    /**
     * <code>Origin Office Of Exchange</code>
     */
    private String originOfficeOfExchange;

    /**
     * <code>Destination Office Of Exchange</code>
     */
    private String destinationOfficeOfExchange;

    /**
     * <code>Mail Category Code</code>
     */
    private String mailCategoryCode;

    /**
     * <code>Mail Sub class</code>
     */
    private String mailSubclass;

    /**
     * <code>year</code>
     */
    private int year;

    /**
     * <code>Mail Identifier</code>
     */
    private String mailIdentifier;

    /**
     * Mail Bag Weight
     */
    private float mailBagWeight;
    /**
     * Receptacle Serial Number
     */
    private String receptacleSerialNumber;
    /**
     * Highest Receptacle Number
     */
    private String highestReceptacleNumber;
    /**
     * Registered Indicator
     */
    private String registeredIndicator;
    
    private String segmentStatus;
    
    private String mailBagSegmentStatus;

	/**
	 * @return Returns the segmentStatus.
	 */
	public String getSegmentStatus() {
		return segmentStatus;
	}
	/**
	 * @param segmentStatus The segmentStatus to set.
	 */
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}
	/**
	 * @return Returns the mailBagSegmentStatus.
	 */
	public String getMailBagSegmentStatus() {
		return mailBagSegmentStatus;
	}
	/**
	 * @param mailBagSegmentStatus The mailBagSegmentStatus to set.
	 */
	public void setMailBagSegmentStatus(String mailBagSegmentStatus) {
		this.mailBagSegmentStatus = mailBagSegmentStatus;
	}
    
	
    


}