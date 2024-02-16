/*
 * MailResditStatusVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-3109
 *
 */

public class MailResditStatusVO extends AbstractVO {

 
    private String companyCode;
    private long serialNumber;
    private String mailIdentifier;
    private String eventCode;
    private String eventAirport;
    private LocalDate eventDate;
    private int carrierId;
    private String flightCarrierCode;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private int legSerialNumber;
    private LocalDate flightDate;
    private int offloadReasonCode;
    private int returnReasonCode;    
    private String uldNumber;
    private String receivedFromCarrier;

	private String transferredToCarrier;
   
   
	/**
     * @return Returns the carrierId.
     */
    public int getCarrierId() {
        return carrierId;
    }
    /**
     * @param carrierId The carrierId to set.
     */
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
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
     * @return Returns the eventAirport.
     */
    public String getEventAirport() {
        return eventAirport;
    }
    /**
     * @param eventAirport The eventAirport to set.
     */
    public void setEventAirport(String eventAirport) {
        this.eventAirport = eventAirport;
    }
    /**
     * @return Returns the eventCode.
     */
    public String getEventCode() {
        return eventCode;
    }
    /**
     * @param eventCode The eventCode to set.
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    /**
     * @return Returns the eventDate.
     */
    public LocalDate getEventDate() {
        return eventDate;
    }
    /**
     * @param eventDate The eventDate to set.
     */
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
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
     * @return Returns the flightSequenceNumber.
     */
    public long getFlightSequenceNumber() {
        return flightSequenceNumber;
    }
    /**
     * @param flightSequenceNumber The flightSequenceNumber to set.
     */
    public void setFlightSequenceNumber(long flightSequenceNumber) {
        this.flightSequenceNumber = flightSequenceNumber;
    }
   
    /**
     * @return Returns the segmentSerialNumber.
     */
    public int getSegmentSerialNumber() {
        return segmentSerialNumber;
    }
    /**
     * @param segmentSerialNumber The segmentSerialNumber to set.
     */
    public void setSegmentSerialNumber(int segmentSerialNumber) {
        this.segmentSerialNumber = segmentSerialNumber;
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
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return the offloadReasonCode
	 */
	public int getOffloadReasonCode() {
		return offloadReasonCode;
	}
	/**
	 * @param offloadReasonCode the offloadReasonCode to set
	 */
	public void setOffloadReasonCode(int offloadReasonCode) {
		this.offloadReasonCode = offloadReasonCode;
	}
	/**
	 * @return the receivedFromCarrier
	 */
	public String getReceivedFromCarrier() {
		return receivedFromCarrier;
	}
	/**
	 * @param receivedFromCarrier the receivedFromCarrier to set
	 */
	public void setReceivedFromCarrier(String receivedFromCarrier) {
		this.receivedFromCarrier = receivedFromCarrier;
	}
	/**
	 * @return the returnReasonCode
	 */
	public int getReturnReasonCode() {
		return returnReasonCode;
	}
	/**
	 * @param returnReasonCode the returnReasonCode to set
	 */
	public void setReturnReasonCode(int returnReasonCode) {
		this.returnReasonCode = returnReasonCode;
	}
	/**
	 * @return the transferredToCarrier
	 */
	public String getTransferredToCarrier() {
		return transferredToCarrier;
	}
	/**
	 * @param transferredToCarrier the transferredToCarrier to set
	 */
	public void setTransferredToCarrier(String transferredToCarrier) {
		this.transferredToCarrier = transferredToCarrier;
	}
	/**
	 * @return the mailIdentifier
	 */
	public String getMailIdentifier() {
		return mailIdentifier;
	}
	/**
	 * @param mailIdentifier the mailIdentifier to set
	 */
	public void setMailIdentifier(String mailIdentifier) {
		this.mailIdentifier = mailIdentifier;
	}
	/**
	 * @return the serialNumber
	 */
	public long getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
   

}
