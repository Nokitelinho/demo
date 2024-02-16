/*
 * ResditEventVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-3109
 * 
 */

public class ResditEventVO extends AbstractVO {

	private String companyCode;

	/**
	 * Code of the Resdit Event
	 */
	private String resditEventCode;

	/**
	 * Status of the Resdit Event
	 */
	private String resditEventStatus;

	/**
	 * ConsignmentId of the receptacles for this resdit event
	 */
	private String consignmentNumber;

	/**
	 * Port at which the resditEvent happened
	 */
	private String eventPort;

	/**
	 * Unique Id for this resditEvent
	 */
	private String uniqueIdForFlag;

	/**
	 * Unique Id for this resditEvent
	 */
	private int messageSequenceNumber;

	private String actualResditEvent;

	private String paCode;

	private Collection<MailbagVO> resditMailbagVOs;

	private String resditVersion;

	private String eventPortName;
	private String carditExist;

	/**
	 * Actual Sender Id
	 */
	private String actualSenderId;
	private String interchangeControlReference;
	/**
	 * Event Date
	 * Added as part of ICRD-181309 
	 */
	private LocalDate eventDate;
	private boolean m49Resdit;
	private String msgText;
	private String msgDetails;
	
	private boolean isMsgEventLocationEnabled;
	private boolean isPartialResdit;
	
	private String partyName;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String reciever;
	/**
	 * For stamping in MALRDT, and to be displayed in Mail History screen
	 */
	private String senderIdentifier;
	private String recipientIdentifier;
	
	private String additionlAddressID;
	/**
	 * @return the carditExist
	 */
	public String getCarditExist() {
		return carditExist;
	}

	/**
	 * @param carditExist
	 *            the carditExist to set
	 */
	public void setCarditExist(String carditExist) {
		this.carditExist = carditExist;
	}

	/**
	 * @return the eventPortName
	 */
	public String getEventPortName() {
		return eventPortName;
	}

	/**
	 * @param eventPortName
	 *            the eventPortName to set
	 */
	public void setEventPortName(String eventPortName) {
		this.eventPortName = eventPortName;
	}

	/**
	 * @return the resditVersion
	 */
	public String getResditVersion() {
		return resditVersion;
	}

	/**
	 * @param resditVersion
	 *            the resditVersion to set
	 */
	public void setResditVersion(String resditVersion) {
		this.resditVersion = resditVersion;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentId) {
		this.consignmentNumber = consignmentId;
	}

	public String getEventPort() {
		return eventPort;
	}

	public void setEventPort(String eventPort) {
		this.eventPort = eventPort;
	}

	public int getMessageSequenceNumber() {
		return messageSequenceNumber;
	}

	public void setMessageSequenceNumber(int messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}

	public String getResditEventCode() {
		return resditEventCode;
	}

	public void setResditEventCode(String resditEventCode) {
		this.resditEventCode = resditEventCode;
	}

	public String getResditEventStatus() {
		return resditEventStatus;
	}

	public void setResditEventStatus(String resditEventStatus) {
		this.resditEventStatus = resditEventStatus;
	}

	public String getUniqueIdForFlag() {
		return uniqueIdForFlag;
	}

	public void setUniqueIdForFlag(String uniqueIdForFlag) {
		this.uniqueIdForFlag = uniqueIdForFlag;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the resditMailbagVOs.
	 */
	public Collection<MailbagVO> getResditMailbagVOs() {
		return resditMailbagVOs;
	}

	/**
	 * @param resditMailbagVOs
	 *            The resditMailbagVOs to set.
	 */
	public void setResditMailbagVOs(Collection<MailbagVO> resditMailbagVOs) {
		this.resditMailbagVOs = resditMailbagVOs;
	}

	/**
	 * @return Returns the actualResditEvent.
	 */
	public String getActualResditEvent() {
		return actualResditEvent;
	}

	/**
	 * @param actualResditEvent
	 *            The actualResditEvent to set.
	 */
	public void setActualResditEvent(String actualResditEvent) {
		this.actualResditEvent = actualResditEvent;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the actualSenderId
	 */
	public String getActualSenderId() {
		return actualSenderId;
	}

	/**
	 * @param actualSenderId
	 *            the actualSenderId to set
	 */
	public void setActualSenderId(String actualSenderId) {
		this.actualSenderId = actualSenderId;
	}

	/**
	 * @return Returns the interchangeControlReference.
	 */
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference
	 *            The interchangeControlReference to set.
	 */
	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/**
	 * @return the eventDate
	 */
	public LocalDate getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	/**
	 * @param m49Resdit the m49Resdit to set
	 */
	public void setM49Resdit(boolean m49Resdit) {
		this.m49Resdit = m49Resdit;
	}
	/**
	 * @return the m49Resdit
	 */
	public boolean isM49Resdit() {
		return m49Resdit;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgDetails() {
		return msgDetails;
	}
	public void setMsgDetails(String msgDetails) {
		this.msgDetails = msgDetails;
	}

	/**
	 * 	Getter for isMsgEventLocationEnabled 
	 *	Added by : A-8061 on 26-Jan-2020
	 * 	Used for :
	 */
	public boolean isMsgEventLocationEnabled() {
		return isMsgEventLocationEnabled;
	}

	/**
	 *  @param isMsgEventLocationEnabled the isMsgEventLocationEnabled to set
	 * 	Setter for isMsgEventLocationEnabled 
	 *	Added by : A-8061 on 26-Jan-2020
	 * 	Used for :
	 */
	public void setMsgEventLocationEnabled(boolean isMsgEventLocationEnabled) {
		this.isMsgEventLocationEnabled = isMsgEventLocationEnabled;
	}

	/**
	 * 	Getter for isPartialResdit 
	 *	Added by : A-8061 on 26-Jan-2020
	 * 	Used for :
	 */
	public boolean isPartialResdit() {
		return isPartialResdit;
	}

	/**
	 *  @param isPartialResdit the isPartialResdit to set
	 * 	Setter for isPartialResdit 
	 *	Added by : A-8061 on 26-Jan-2020
	 * 	Used for :
	 */
	public void setPartialResdit(boolean isPartialResdit) {
		this.isPartialResdit = isPartialResdit;
	}


	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : A-9998 on 15-Mar-2022
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : A-9998 on 15-Mar-2022
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : A-9998 on 15-Mar-2022
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : A-9998 on 15-Mar-2022
	 * 	Used for :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
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
	public String getAdditionlAddressID() {
		return additionlAddressID;
	}
	public void setAdditionlAddressID(String additionlAddressID) {
		this.additionlAddressID = additionlAddressID;
	}
}
