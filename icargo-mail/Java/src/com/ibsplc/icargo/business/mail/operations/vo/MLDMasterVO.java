/*
 * MLDMasterVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 * 
 */
public class MLDMasterVO extends AbstractVO {
	
	private String barcodeValue;
	private String containerType;
	private String companyCode;
	private String mailSource;
	private long messageSequence;
	
	private int serialNumber;

	private String barcodeType;

	private String weightCode;

	//private String weight;
	private Measure weight;//added by A-7371

	private String messageVersion;

	private String eventCOde;

	private String reasonCode;

	private LocalDate scanTime;

	private String senderAirport;

	private String receiverAirport;

	private String destAirport;

	private String expectedInd;

	private String uldNumber;

	private String uldWeightCode;

	//private String uldWeight;
	private Measure uldWeight;//added by A-7371

	private String lastUpdatedUser;
	
	private LocalDate lastUpdateTime;

	private String processStatus;

	private String messagingMode;
	
	private String addrCarrier;
	
	private long mailSequenceNumber;
	
	//Boolean values to check the event  types -
	boolean allocationRequired;
	boolean recRequired;
	boolean upliftedRequired ;
	boolean hNdRequired ;
	boolean dLVRequired;
	boolean sTGRequired;
	boolean nSTRequired;
	boolean rCFRequired;
	boolean tFDRequired;
	boolean rCTRequired;
	boolean rETRequired;
	
	private String transactionLevel;
	private LocalDate msgTimUTC;
    /**
     * 
     * @return weight
     */
	public Measure getWeight() {
		return weight;
	}
    /**
     * 
     * @param weight
     */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
    /**
     * 
     * @return uldWeight
     */
	public Measure getUldWeight() {
		return uldWeight;
	}
    /**
     * 
     * @param uldWeight
     */
	public void setUldWeight(Measure uldWeight) {
		this.uldWeight = uldWeight;
	}

	public String getAddrCarrier() {
		return addrCarrier;
	}

	public void setAddrCarrier(String addrCarrier) {
		this.addrCarrier = addrCarrier;
	}

	private MLDDetailVO mldDetailVO;

	/**
	 * @return the barcodeType
	 */
	public String getBarcodeType() {
		return barcodeType;
	}

	/**
	 * @param barcodeType the barcodeType to set
	 */
	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

	/**
	 * @return the barcodeValue
	 */
	public String getBarcodeValue() {
		return barcodeValue;
	}

	/**
	 * @param barcodeValue the barcodeValue to set
	 */
	public void setBarcodeValue(String barcodeValue) {
		this.barcodeValue = barcodeValue;
	}



	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the destAirport
	 */
	public String getDestAirport() {
		return destAirport;
	}

	/**
	 * @param destAirport the destAirport to set
	 */
	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}

	/**
	 * @return the eventCOde
	 */
	public String getEventCOde() {
		return eventCOde;
	}

	/**
	 * @param eventCOde the eventCOde to set
	 */
	public void setEventCOde(String eventCOde) {
		this.eventCOde = eventCOde;
	}

	/**
	 * @return the expectedInd
	 */
	public String getExpectedInd() {
		return expectedInd;
	}

	/**
	 * @param expectedInd the expectedInd to set
	 */
	public void setExpectedInd(String expectedInd) {
		this.expectedInd = expectedInd;
	}

	/**
	 * @return the messageSequence
	 */
	public long getMessageSequence() {
		return messageSequence;
	}

	/**
	 * @param messageSequence the messageSequence to set
	 */
	public void setMessageSequence(long messageSequence) {
		this.messageSequence = messageSequence;
	}

	/**
	 * @return the messageVersion
	 */
	public String getMessageVersion() {
		return messageVersion;
	}

	/**
	 * @param messageVersion the messageVersion to set
	 */
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the receiverAirport
	 */
	public String getReceiverAirport() {
		return receiverAirport;
	}

	/**
	 * @param receiverAirport the receiverAirport to set
	 */
	public void setReceiverAirport(String receiverAirport) {
		this.receiverAirport = receiverAirport;
	}

	/**
	 * @return the scanTime
	 */
	public LocalDate getScanTime() {
		return scanTime;
	}

	/**
	 * @param scanTime the scanTime to set
	 */
	public void setScanTime(LocalDate scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * @return the senderAirport
	 */
	public String getSenderAirport() {
		return senderAirport;
	}

	/**
	 * @param senderAirport the senderAirport to set
	 */
	public void setSenderAirport(String senderAirport) {
		this.senderAirport = senderAirport;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the uldWeight
	 */
	/*public String getUldWeight() {
		return uldWeight;
	}

	*//**
	 * @param uldWeight the uldWeight to set
	 *//*
	public void setUldWeight(String uldWeight) {
		this.uldWeight = uldWeight;
	}*/

	/**
	 * @return the uldWeightCode
	 */
	public String getUldWeightCode() {
		return uldWeightCode;
	}

	/**
	 * @param uldWeightCode the uldWeightCode to set
	 */
	public void setUldWeightCode(String uldWeightCode) {
		this.uldWeightCode = uldWeightCode;
	}

	/**
	 * @return the weight
	 */
	/*public String getWeight() {
		return weight;
	}

	*//**
	 * @param weight the weight to set
	 *//*
	public void setWeight(String weight) {
		this.weight = weight;
	}
*/
	/**
	 * @return the weightCode
	 */
	public String getWeightCode() {
		return weightCode;
	}

	/**
	 * @param weightCode the weightCode to set
	 */
	public void setWeightCode(String weightCode) {
		this.weightCode = weightCode;
	}

	/**
	 * @return the mldDetailVO
	 */
	public MLDDetailVO getMldDetailVO() {
		return mldDetailVO;
	}

	/**
	 * @param mldDetailVO the mldDetailVO to set
	 */
	public void setMldDetailVO(MLDDetailVO mldDetailVO) {
		this.mldDetailVO = mldDetailVO;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the messagingMode
	 */
	public String getMessagingMode() {
		return messagingMode;
	}

	/**
	 * @param messagingMode the messagingMode to set
	 */
	public void setMessagingMode(String messagingMode) {
		this.messagingMode = messagingMode;
	}

	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 	Getter for containerType 
	 *	Added by : A-4803 on 30-Oct-2014
	 * 	Used for :
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 *  @param containerType the containerType to set
	 * 	Setter for containerType 
	 *	Added by : A-4803 on 30-Oct-2014
	 * 	Used for :
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * 	Getter for mailSource 
	 *	Added by : A-4803 on 31-Oct-2014
	 * 	Used for :
	 */
	public String getMailSource() {
		return mailSource;
	}

	/**
	 *  @param mailSource the mailSource to set
	 * 	Setter for mailSource 
	 *	Added by : A-4803 on 31-Oct-2014
	 * 	Used for :
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	/**
	 * 	Getter for mailSequenceNumber 
	 *	Added by : A-8061 on 07-Jun-2021
	 * 	Used for :
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 *  @param mailSequenceNumber the mailSequenceNumber to set
	 * 	Setter for mailSequenceNumber 
	 *	Added by : A-8061 on 07-Jun-2021
	 * 	Used for :
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public boolean isAllocationRequired() {
		return allocationRequired;
	}
	public void setAllocationRequired(boolean allocationRequired) {
		this.allocationRequired = allocationRequired;
	}
	public boolean isRecRequired() {
		return recRequired;
	}
	public void setRecRequired(boolean recRequired) {
		this.recRequired = recRequired;
	}
	public boolean isUpliftedRequired() {
		return upliftedRequired;
	}
	public void setUpliftedRequired(boolean upliftedRequired) {
		this.upliftedRequired = upliftedRequired;
	}
	public boolean ishNdRequired() {
		return hNdRequired;
	}
	public void sethNdRequired(boolean hNdRequired) {
		this.hNdRequired = hNdRequired;
	}
	public boolean isdLVRequired() {
		return dLVRequired;
	}
	public void setdLVRequired(boolean dLVRequired) {
		this.dLVRequired = dLVRequired;
	}
	public boolean issTGRequired() {
		return sTGRequired;
	}
	public void setsTGRequired(boolean sTGRequired) {
		this.sTGRequired = sTGRequired;
	}
	public boolean isnSTRequired() {
		return nSTRequired;
	}
	public void setnSTRequired(boolean nSTRequired) {
		this.nSTRequired = nSTRequired;
	}
	public boolean isrCFRequired() {
		return rCFRequired;
	}
	public void setrCFRequired(boolean rCFRequired) {
		this.rCFRequired = rCFRequired;
	}
	public boolean istFDRequired() {
		return tFDRequired;
	}
	public void settFDRequired(boolean tFDRequired) {
		this.tFDRequired = tFDRequired;
	}
	public boolean isrCTRequired() {
		return rCTRequired;
	}
	public void setrCTRequired(boolean rCTRequired) {
		this.rCTRequired = rCTRequired;
	}
	public boolean isrETRequired() {
		return rETRequired;
	}
	public void setrETRequired(boolean rETRequired) {
		this.rETRequired = rETRequired;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	public LocalDate getMsgTimUTC() {
		return msgTimUTC;
	}
	public void setMsgTimUTC(LocalDate msgTimUTC) {
		this.msgTimUTC = msgTimUTC;
	}

}