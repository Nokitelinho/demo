/*
 * MailResditVO.java Created on Jun 30, 2016
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

public class MailResditVO extends AbstractVO {

 
    private String companyCode;
    private String mailId;
    private String eventCode;
    private String eventAirport;
    private LocalDate eventDate;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String uldNumber;
    private String resditSentFlag;
    private long resditSequenceNum; 
    private String processedStatus;
    private long messageSequenceNumber;
    private String carditKey; 
    
    private LocalDate lastUpdateTime;
    
    private String lastUpdateUser;
    
 private String partyIdentifier;
    
    private String mailboxID;
    private long messageIdentifier; 
    private boolean fromDeviationList;
	private String senderIdentifier;
	private String recipientIdentifier;
    private boolean resditFromCarditEnquiry;
    private String messageAddressSequenceNumber;
    private String containerNumber;
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public boolean isResditFromCarditEnquiry() {
		return resditFromCarditEnquiry;
	}
	public void setResditFromCarditEnquiry(boolean resditFromCarditEnquiry) {
		this.resditFromCarditEnquiry = resditFromCarditEnquiry;
	}
    public boolean isFromDeviationList() {
		return fromDeviationList;
	}
	public void setFromDeviationList(boolean fromDeviationList) {
		this.fromDeviationList = fromDeviationList;
	}
	
	public long getMessageIdentifier() {
		return messageIdentifier;
	}
	public void setMessageIdentifier(long messageIdentifier) {
		this.messageIdentifier = messageIdentifier;
	}
	public String getPartyIdentifier() {
		return partyIdentifier;
	}
	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}
	public String getMailboxID() {
		return mailboxID;
	}
	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
    /*
     * Added By Karthick V..
     * 
     * 
     * 
     */
    private String dependantEventCode;
    
    private String paOrCarrierCode;
	/**
	 * The mailEventSequenceNumber
	 */
	private int mailEventSequenceNumber;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;
	/**
	 * The reconciliationStatus
	 */
	private String reconciliationStatus;
	
	private long mailSequenceNumber;
	/**
	 * @return Returns the reconciliationStatus.
	 */
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}
	/**
	 * @param reconciliationStatus The reconciliationStatus to set.
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}
	/**
	 * @return Returns the interchangeControlReference.
	 */
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}
	/**
	 * @param interchangeControlReference The interchangeControlReference to set.
	 */
	public void setInterchangeControlReference(String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}
	/**
	 * @return Returns the mailEventSequenceNumber.
	 */
    public int getMailEventSequenceNumber() {
		return mailEventSequenceNumber;
	}
    /**
	 * @param mailEventSequenceNumber The mailEventSequenceNumber to set.
	 */
	public void setMailEventSequenceNumber(int mailEventSequenceNumber) {
		this.mailEventSequenceNumber = mailEventSequenceNumber;
	}
    /**
	 * @return Returns the paOrCarrierCode.
	 */
	public String getPaOrCarrierCode() {
		return paOrCarrierCode;
	}
	/**
	 * @param paOrCarrierCode The paOrCarrierCode to set.
	 */
	public void setPaOrCarrierCode(String paOrCarrierCode) {
		this.paOrCarrierCode = paOrCarrierCode;
	}
	/**
	 * @return Returns the messageSequenceNumber.
	 */
	public long getMessageSequenceNumber() {
		return messageSequenceNumber;
	}
	/**
	 * @param messageSequenceNumber The messageSequenceNumber to set.
	 */
	public void setMessageSequenceNumber(long messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}
	/**
	 * @return Returns the processedStatus.
	 */
	public String getProcessedStatus() {
		return processedStatus;
	}
	/**
	 * @param processedStatus The processedStatus to set.
	 */
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}
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
     * @return Returns the mailId.
     */
    public String getMailId() {
        return mailId;
    }
    /**
     * @param mailId The mailId to set.
     */
    public void setMailId(String mailId) {
        this.mailId = mailId;
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
     * @return Returns the resditSentFlag.
     */
    public String getResditSentFlag() {
        return resditSentFlag;
    }
    /**
     * @param resditSentFlag The resditSentFlag to set.
     */
    public void setResditSentFlag(String resditSentFlag) {
        this.resditSentFlag = resditSentFlag;
    }
    /**
     * @return Returns the resditSequenceNum.
     */
    public long getResditSequenceNum() {
        return resditSequenceNum;
    }
    /**
     * @param resditSequenceNum The resditSequenceNum to set.
     */
    public void setResditSequenceNum(long resditSequenceNum) {
        this.resditSequenceNum = resditSequenceNum;
    }
	/**
	 * @return Returns the carditKey.
	 */
	public String getCarditKey() {
		return this.carditKey;
	}
	/**
	 * @param carditKey The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getDependantEventCode() {
		return dependantEventCode;
	}
	public void setDependantEventCode(String dependantEventCode) {
		this.dependantEventCode = dependantEventCode;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public String getSenderIdentifier() {
		return senderIdentifier;
	}
	public void setSenderIdentifier(String senderIdentifier) {
		this.senderIdentifier = senderIdentifier;
	}
	public String getRecipientIdentifier() {
		return recipientIdentifier;
	}
	public void setRecipientIdentifier(String recipientIdentifier) {
		this.recipientIdentifier = recipientIdentifier;
	}
	public String getMessageAddressSequenceNumber() {
		return messageAddressSequenceNumber;
	}
	public void setMessageAddressSequenceNumber(String messageAddressSequenceNumber) {
		this.messageAddressSequenceNumber = messageAddressSequenceNumber;
	}

}
