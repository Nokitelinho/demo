/*
 * FlownMailFilterVO.java Created on Aug 12, 2006
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
 * VO for filtering mails.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 13/02/2007 Soncy Initial draft
 */
public class FlownMailFilterVO extends AbstractVO {
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
     * @return Returns the fromDate.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
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
     * @return Returns the toDate.
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            The toDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    
    /**
	 * @return Returns the FlightDestination
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
	 * @return Returns the FlightOrigin
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
	 * @return Returns the FLightStatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the MailDestination
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
	 * @return Returns the MailOrigin
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
     * Company Code
     */
    /**
     * Comment for <code>companyCode</code>
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
     * Flight date range's from date
     */
    private LocalDate fromDate;

    /**
     * Flight date range's to date
     */
    private LocalDate toDate;

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
   
    
    // added for flown reports
    private String flightStatus;

    private String flightOrigin;
    
    private String flightDestination;
    
    private String mailOrigin;
    
    private String mailDestination;
    
    private String accountingMonth;
    
    private String accountingYear;
    
    private String baseCurrencyCode;
    //

	/**
	 * @return the baseCurrencyCode
	 */
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}

	/**
	 * @param baseCurrencyCode the baseCurrencyCode to set
	 */
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	/**
	 * @return the accountingYear
	 */
	public String getAccountingYear() {
		return accountingYear;
	}

	/**
	 * @param accountingYear the accountingYear to set
	 */
	public void setAccountingYear(String accountingYear) {
		this.accountingYear = accountingYear;
	}

	public String getAccountingMonth() {
		return accountingMonth;
	}

	public void setAccountingMonth(String accountingMonth) {
		this.accountingMonth = accountingMonth;
	}

	
}