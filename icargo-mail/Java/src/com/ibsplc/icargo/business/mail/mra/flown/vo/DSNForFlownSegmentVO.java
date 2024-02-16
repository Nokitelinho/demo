/*
 * DSNForFlownSegmentVO.java Created on Aug 12, 2006
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
public class DSNForFlownSegmentVO extends AbstractVO {
    /**
     * @return Returns the acceptedMailBagWeight.
     */
    public float getAcceptedMailBagWeight() {
        return acceptedMailBagWeight;
    }
    /**
     * @param acceptedMailBagWeight The acceptedMailBagWeight to set.
     */
    public void setAcceptedMailBagWeight(float acceptedMailBagWeight) {
        this.acceptedMailBagWeight = acceptedMailBagWeight;
    }
    /**
     * @return Returns the consignmentDocumentNumber.
     */
    public String getConsignmentDocumentNumber() {
        return consignmentDocumentNumber;
    }
    /**
     * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
     */
    public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
        this.consignmentDocumentNumber = consignmentDocumentNumber;
    }
    /**
     * @return Returns the despatchDate.
     */
    public LocalDate getDespatchDate() {
        return despatchDate;
    }
    /**
     * @param despatchDate The despatchDate to set.
     */
    public void setDespatchDate(LocalDate despatchDate) {
        this.despatchDate = despatchDate;
    }
    /**
     * @return Returns the statedMailBagCount.
     */
    public int getStatedMailBagCount() {
        return statedMailBagCount;
    }
    /**
     * @param statedMailBagCount The statedMailBagCount to set.
     */
    public void setStatedMailBagCount(int statedMailBagCount) {
        this.statedMailBagCount = statedMailBagCount;
    }
    /**
     * @return Returns the statedMailBagWeight.
     */
    public float getStatedMailBagWeight() {
        return statedMailBagWeight;
    }
    /**
     * @param statedMailBagWeight The statedMailBagWeight to set.
     */
    public void setStatedMailBagWeight(float statedMailBagWeight) {
        this.statedMailBagWeight = statedMailBagWeight;
    }
    /**
     * @return Returns the destinationOfficeOfExchange.
     */
    public String getDestinationOfficeOfExchange() {
        return destinationOfficeOfExchange;
    }
    /**
     * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
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
     * @param dsnNumber The dsnNumber to set.
     */
    public void setDsnNumber(String dsnNumber) {
        this.dsnNumber = dsnNumber;
    }
    /**
     * @return Returns the mailBagCount.
     */
    public int getAcceptedMailBagCount() {
        return acceptedMailBagCount;
    }
    /**
     * @param mailBagCount The mailBagCount to set.
     */
    public void setAcceptedMailBagCount(int acceptedMailBagCount) {
        this.acceptedMailBagCount = acceptedMailBagCount;
    }
    /**
     * @return Returns the mailCategoryCode.
     */
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }
    /**
     * @param mailCategoryCode The mailCategoryCode to set.
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
     * @param mailSubclass The mailSubclass to set.
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
     * @param operationFlag The operationFlag to set.
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
     * @param originOfficeOfExchange The originOfficeOfExchange to set.
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
     * @param serialNumber The serialNumber to set.
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
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
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
    
    private String dsnSegmentStatus;

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
     * Accepted Mail Bag Weight
     */
    private float acceptedMailBagWeight;
    /**
     * Stated Mail Bag Weight
     */
    private float statedMailBagWeight;
    /**
     * Accepted Mail Bag Count
     */
    private int acceptedMailBagCount;
    /**
     * Stated Mail Bag Count
     */
    private int statedMailBagCount;

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
     * <code>Mail Sub class</code>
     */
    private String consignmentDocumentNumber;

    /**
     * Despatch Date
     */
    private LocalDate despatchDate;
    
    /**
     * 
     */
    private String actualSubClass;
    
    private String segmentStatus;

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
	 * @return Returns the dsnSegmentStatus.
	 */
	public String getDsnSegmentStatus() {
		return dsnSegmentStatus;
	}
	/**
	 * @param dsnSegmentStatus The dsnSegmentStatus to set.
	 */
	public void setDsnSegmentStatus(String dsnSegmentStatus) {
		this.dsnSegmentStatus = dsnSegmentStatus;
	}
	/**
	 * @return the actualSubClass
	 */
	public String getActualSubClass() {
		return actualSubClass;
	}
	/**
	 * @param actualSubClass the actualSubClass to set
	 */
	public void setActualSubClass(String actualSubClass) {
		this.actualSubClass = actualSubClass;
	}
}