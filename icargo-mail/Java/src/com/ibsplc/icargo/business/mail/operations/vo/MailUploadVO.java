package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.unit.Measure; 

/*
 * This VO represents the data from HHT sent as -- from webservice
 */
public class MailUploadVO extends AbstractVO {
	
	private String mailSource;
	
	private String messageVersion;//added by A-8627 for IASCB-58918
	private String scanType;
	
	private String companyCode;
	private String mailCompanyCode;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String containerPOU;
	private String containerNumber;
	private String containerType;
	private String containeDescription;
	private String containerPol;
	private String fromCarrierCode;
	private String remarks;
	private String mailTag;
	private String dateTime;
	private String damageCode;
	private String damageRemarks;
	private String offloadReason;
	private String returnCode;
	private String toContainer;
	private String toCarrierCode;
	private String toFlightNumber;
	private LocalDate toFlightDate;
	private String toPOU;
	private String toDestination;
	private String orginOE;
	private String destinationOE;
	private String category;
	private String subClass;
	private int year;
	private int carrierId;
	private String processPoint; // Added for ICRD-84459
	private String scanUser;
	private boolean isArrived;
	private boolean isAccepted;
	// Added for ICRD-93567
	private LocalDate scannedDate;
	private String mailKeyforDisplay;
	//Added as part of icrd-125328
	private boolean expRsn;
	private String fromPol;
	private String pols;
	private String overrideValidation;//added by a-7871
	private String transferFrmFlightNum;//Added by a-7871 for ICRD-240184
	private long transferFrmFlightSeqNum;
	private LocalDate transferFrmFlightDate; 
	private String operationType;
//Added by A-7540
	private String deliverFlag;
	private String warningFlag;//Added by A-8164 for ICRD-315657
	
	private String androidFlag;
	private String screeningUser;//Added by A-9498 as part of IASCB-44577
	private Collection<MailAttachmentVO> attachments;
	private String containerJourneyId;
	private String uldFullIndicator;
	/**
	 * Used for stamping sender and recipient of incoming RESDIT
	 */
	private String messageSenderIdentifier;
	private String messageRecipientIdentifier;

	private boolean processMRDWithoutFlight;
	

	

	private String functionPoint;

	public Collection<MailAttachmentVO> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<MailAttachmentVO> attachments) {
		this.attachments = attachments;
	}
	public String getScreeningUser() {
		return screeningUser;
	}
	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}
	public String getRawMessageBlob() {
		return rawMessageBlob;
	}
	public void setRawMessageBlob(String rawMessageBlob) {
		this.rawMessageBlob = rawMessageBlob;
	}
	private boolean arrivalException;//Added by A-8164 for ICRD-330543
	private boolean fromErrorHandling;
	private String processPointBeforeArrival;
	
	private boolean restrictErrorLogging ;
	private String rawMessageBlob;
	
	String overrideULDVal;
	
	
	public String getDeliverFlag() {
		return deliverFlag;
	}
	public void setDeliverFlag(String deliverFlag) {
		this.deliverFlag = deliverFlag;

}
	//added by 7531
	private String resolveFlagAndroid;
	
	private boolean rdtProcessing;
	
	private boolean resolveFromErrorHandling;//added by A-8353 for IASCB-34167
    //added by A-9529 for IASCB-44567
    private String storageUnit;
    public String getStorageUnit() {
        return storageUnit;
    }
    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }
	private LocalDate deviceDateAndTime;//added by A-8353 for IASCB-57925
    private String errorCode;
    private boolean forceAccepted;
    private String errorDescription;
    private boolean fromErrorHandlingForForceAcp;
	/**
	 * @return the transferFrmFlightNum
	 */
	public String getTransferFrmFlightNum() {
		return transferFrmFlightNum;
	}
	/**
	 * @param transferFrmFlightNum the transferFrmFlightNum to set
	 */
	public void setTransferFrmFlightNum(String transferFrmFlightNum) {
		this.transferFrmFlightNum = transferFrmFlightNum;
	}
	/**
	 * @return the transferFrmFlightSeqNum
	 */
	public long getTransferFrmFlightSeqNum() {
		return transferFrmFlightSeqNum;
	}
	/**
	 * @param transferFrmFlightSeqNum the transferFrmFlightSeqNum to set
	 */
	public void setTransferFrmFlightSeqNum(long transferFrmFlightSeqNum) {
		this.transferFrmFlightSeqNum = transferFrmFlightSeqNum;
	}
	/**
	 * @return the transferFrmFlightDate
	 */
	public LocalDate getTransferFrmFlightDate() {
		return transferFrmFlightDate;
	}
	/**
	 * @param transferFrmFlightDate the transferFrmFlightDate to set
	 */
	public void setTransferFrmFlightDate(LocalDate transferFrmFlightDate) {
		this.transferFrmFlightDate = transferFrmFlightDate;
	}
	/**
	 * @return the overrideValidation
	 */
	public String getOverrideValidation() {
		return overrideValidation;
	}
	/**
	 * @param overrideValidation the overrideValidation to set
	 */
	public void setOverrideValidation(String overrideValidation) {
		this.overrideValidation = overrideValidation;
	}
	private Collection<FlightValidationVO> flightValidationVOS;
	
	
		public Collection<FlightValidationVO> getFlightValidationVOS() {
		return flightValidationVOS;
	}
	public void setFlightValidationVOS(
			Collection<FlightValidationVO> flightValidationVOS) {
		this.flightValidationVOS = flightValidationVOS;
	}
		/**
	 * @return the mailKeyforDisplay
	 */
	public String getMailKeyforDisplay() {
		return mailKeyforDisplay;
	}
	/**
	 * @param mailKeyforDisplay the mailKeyforDisplay to set
	 */
	public void setMailKeyforDisplay(String mailKeyforDisplay) {
		this.mailKeyforDisplay = mailKeyforDisplay;
	}
	/**
	 * @return the scannedDate
	 */
	public LocalDate getScannedDate() {
		return scannedDate;
	}
	/**
	 * @param scannedDate the scannedDate to set
	 */
	public void setScannedDate(LocalDate scannedDate) {
		this.scannedDate = scannedDate;
	}
	/**
	 * @return the processPoint
	 */
	public String getProcessPoint() {
		return processPoint;
	}
	public boolean isArrived() {
		return isArrived;
	}
	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	/**
	 * @param processPoint the processPoint to set
	 */
	public void setProcessPoint(String processPoint) {
		this.processPoint = processPoint;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	private String dsn;
	private String rsn;
	private String scannedPort;
	private String destination;
	
	private String transactionLevel; 
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOrgin() {
		return orgin;
	}
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}
	private String orgin;
	public String getScannedPort() {
		return scannedPort;
	}
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}
	/*public int getRsn() {
		return rsn;
	}
	public void setRsn(int rsn) {
		this.rsn = rsn;
	}*/
	/*public int getHighestnumberIndicator() {
		return highestnumberIndicator;
	}
	public void setHighestnumberIndicator(int highestnumberIndicator) {
		this.highestnumberIndicator = highestnumberIndicator;
	}
	public int getRegisteredIndicator() {
		return registeredIndicator;
	}
	public void setRegisteredIndicator(int registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}*/
	private String highestnumberIndicator;
	private String registeredIndicator;
	private String consignmentDocumentNumber;
	private String paCode;
	private int totalBag;
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371
	private int bags;
	public boolean isFromGHAService() {
		return isFromGHAService;
	}
	public void setFromGHAService(boolean isFromGHAService) {
		this.isFromGHAService = isFromGHAService;
	}
	//private double weight;
	private Measure weight;//added by A-7371
	private boolean isIntact;
	private int serialNumber;
	private boolean isFromGHAService;
	private long mailSequenceNumber;
	private String messageType;
	private String eventCode;
	private String totalEventCodes;
	private String nodeName;
	private long msgSequenceNumber;
	private Measure uldActualWeight;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getTotalEventCodes() {
		return totalEventCodes;
	}
	public void setTotalEventCodes(String totalEventCodes) {
		this.totalEventCodes = totalEventCodes;
	}
	/**
	 * 
	 * @return
	 */
	public Measure getTotalWeight() {
		return totalWeight;
	}
	/**
	 * 
	 * @param totalWeight
	 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	/**
	 * 
	 * @return
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
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	private long flightSequenceNumber;
	
	private String cirCode;
	private boolean isDeliverd;
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getContainerPOU() {
		return containerPOU;
	}
	public void setContainerPOU(String containerPOU) {
		this.containerPOU = containerPOU;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getContaineDescription() {
		return containeDescription;
	}
	public void setContaineDescription(String containeDescription) {
		this.containeDescription = containeDescription;
	}
	public String getContainerPol() {
		return containerPol;
	}
	public void setContainerPol(String containerPol) {
		this.containerPol = containerPol;
	}
	public String getFromCarrierCode() {
		return fromCarrierCode;
	}
	public void setFromCarrierCode(String fromCarrierCode) {
		this.fromCarrierCode = fromCarrierCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMailTag() {
		return mailTag;
	}
	public void setMailTag(String mailTag) {
		this.mailTag = mailTag;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getDamageRemarks() {
		return damageRemarks;
	}
	public void setDamageRemarks(String damageRemarks) {
		this.damageRemarks = damageRemarks;
	}
	public String getOffloadReason() {
		return offloadReason;
	}
	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getToContainer() {
		return toContainer;
	}
	public void setToContainer(String toContainer) {
		this.toContainer = toContainer;
	}
	public String getToCarrierCode() {
		return toCarrierCode;
	}
	public void setToCarrierCode(String toCarrierCode) {
		this.toCarrierCode = toCarrierCode;
	}
	public String getToFlightNumber() {
		return toFlightNumber;
	}
	public void setToFlightNumber(String toFlightNumber) {
		this.toFlightNumber = toFlightNumber;
	}
	public LocalDate getToFlightDate() {
		return toFlightDate;
	}
	public void setToFlightDate(LocalDate toFlightDate) {
		this.toFlightDate = toFlightDate;
	}
	public String getToPOU() {
		return toPOU;
	}
	public void setToPOU(String toPOU) {
		this.toPOU = toPOU;
	}
	public String getToDestination() {
		return toDestination;
	}
	public void setToDestination(String toDestination) {
		this.toDestination = toDestination;
	}
	public String getOrginOE() {
		return orginOE;
	}
	public void setOrginOE(String orginOE) {
		this.orginOE = orginOE;
	}
	public String getDestinationOE() {
		return destinationOE;
	}
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
/*	public int getDsn() {
		return dsn;
	}
	public void setDsn(int dsn) {
		this.dsn = dsn;
	}*/
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public int getTotalBag() {
		return totalBag;
	}
	public void setTotalBag(int totalBag) {
		this.totalBag = totalBag;
	}
	/*public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/
	public int getBags() {
		return bags;
	}
	public void setBags(int bags) {
		this.bags = bags;
	}
	/*public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}*/
	public boolean isIntact() {
		return isIntact;
	}
	public void setIntact(boolean isIntact) {
		this.isIntact = isIntact;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCirCode() {
		return cirCode;
	}
	public void setCirCode(String cirCode) {
		this.cirCode = cirCode;
	}
	public boolean isDeliverd() {
		return isDeliverd;
	}
	public void setDeliverd(boolean isDeliverd) {
		this.isDeliverd = isDeliverd;
	}
	/**
	 * 	Getter for mailSource 
	 *	Added by : A-4803 on 31-Oct-2014
	 * 	Used for : knowing the mail source
	 */
	public String getMailSource() {
		return mailSource;
	}
	/**
	 *  @param mailSource the mailSource to set
	 * 	Setter for mailSource 
	 *	Added by : A-4803 on 31-Oct-2014
	 * 	Used for : knowing the mail source
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	public String getScanUser() {
		return scanUser;
	}
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getHighestnumberIndicator() {
		return highestnumberIndicator;
	}
	public void setHighestnumberIndicator(String highestnumberIndicator) {
		this.highestnumberIndicator = highestnumberIndicator;
	}
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}
	public boolean isExpRsn() {
		return expRsn;
	}
	public void setExpRsn(boolean expRsn) {
		this.expRsn = expRsn;
	}
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	
	/**
	 * @param fromPol the fromPol to set
	 */
	public void setFromPol(String fromPol) {
		this.fromPol = fromPol;
	}
	/**
	 * @return the fromPol
	 */
	public String getFromPol() {
		return fromPol;
	}
	/**
	 * @param pols the pols to set
	 */
	public void setPols(String pols) {
		this.pols = pols;
	}
	/**
	 * @return the pols
	 */
	public String getPols() {
		return pols;
	}
	
	
	/**
	 * 
	 * @return androidFlag
	 */
	public String getAndroidFlag() {
		return androidFlag;
	}
	/**
	 * 
	 * @param androidFlag the androidFlag to set
	 */
	public void setAndroidFlag(String androidFlag) {
		this.androidFlag = androidFlag;
	}	 
    public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getWarningFlag() {
		return warningFlag;
	}
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}
	/**
	 * 	Getter for resolveFlagAndroid 
	 *	Added by : A-7531 on 19-Nov-2018
	 * 	Used for :
	 */
	public String getResolveFlagAndroid() {
		return resolveFlagAndroid;
	}
	/**
	 *  @param resolveFlagAndroid the resolveFlagAndroid to set
	 * 	Setter for resolveFlagAndroid 
	 *	Added by : A-7531 on 19-Nov-2018
	 * 	Used for :
	 */
	public void setResolveFlagAndroid(String resolveFlagAndroid) {
		this.resolveFlagAndroid = resolveFlagAndroid;
	}
	/**
	 * @return the rdtProcessing
	 */
	public boolean isRdtProcessing() {
		return rdtProcessing;
	}
	/**
	 * @param rdtProcessing the rdtProcessing to set
	 */
	public void setRdtProcessing(boolean rdtProcessing) {
		this.rdtProcessing = rdtProcessing;
	}
	public boolean isArrivalException() {
		return arrivalException;
	}
	public void setArrivalException(boolean arrivalException) {
		this.arrivalException = arrivalException;
	}
	public boolean isFromErrorHandling() {
		return fromErrorHandling;
	}
	public void setFromErrorHandling(boolean fromErrorHandling) {
		this.fromErrorHandling = fromErrorHandling;
	}
	public String getProcessPointBeforeArrival() {
		return processPointBeforeArrival;
	}
	public void setProcessPointBeforeArrival(String processPointBeforeArrival) {
		this.processPointBeforeArrival = processPointBeforeArrival;
	}
	public boolean isResolveFromErrorHandling() {
		return resolveFromErrorHandling;
	}
	public void setResolveFromErrorHandling(boolean resolveFromErrorHandling) {
		this.resolveFromErrorHandling = resolveFromErrorHandling;
	}
	/**
	 * 	Getter for restrictErrorLogging 
	 *	Added by : A-8061 on 25-Feb-2020
	 * 	Used for :
	 */
	public boolean isRestrictErrorLogging() {
		return restrictErrorLogging;
	}
	/**
	 *  @param restrictErrorLogging the restrictErrorLogging to set
	 * 	Setter for restrictErrorLogging 
	 *	Added by : A-8061 on 25-Feb-2020
	 * 	Used for :
	 */
	public void setRestrictErrorLogging(boolean restrictErrorLogging) {
		this.restrictErrorLogging = restrictErrorLogging;
	}
	
	/**
	 * 	Getter for overrideULDVal 
	 *	Added by : A-8061 on 04-Mar-2020
	 * 	Used for :
	 */
	public String getOverrideULDVal() {
		return overrideULDVal;
	}
	/**
	 *  @param overrideULDVal the overrideULDVal to set
	 * 	Setter for overrideULDVal 
	 *	Added by : A-8061 on 04-Mar-2020
	 * 	Used for :
	 */
	public void setOverrideULDVal(String overrideULDVal) {
		this.overrideULDVal = overrideULDVal;
	}
	public LocalDate getDeviceDateAndTime() {
		return deviceDateAndTime;
	}
	public void setDeviceDateAndTime(LocalDate deviceDateAndTime) {
		this.deviceDateAndTime = deviceDateAndTime;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isForceAccepted() {
		return forceAccepted;
	}
	public void setForceAccepted(boolean forceAccepted) {
		this.forceAccepted = forceAccepted;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public boolean isFromErrorHandlingForForceAcp() {
		return fromErrorHandlingForForceAcp;
	}
	public void setFromErrorHandlingForForceAcp(boolean fromErrorHandlingForForceAcp) {
		this.fromErrorHandlingForForceAcp = fromErrorHandlingForForceAcp;
	}
	public long getMsgSequenceNumber() {
		return msgSequenceNumber;
	}
	public void setMsgSequenceNumber(long msgSequenceNumber) {
		this.msgSequenceNumber = msgSequenceNumber;
	}
	public String getContainerJourneyId() {
		return containerJourneyId;
	}
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}	
	public String getUldFullIndicator() {
		return uldFullIndicator;
	}
	public void setUldFullIndicator(String uldFullIndicator) {
		this.uldFullIndicator = uldFullIndicator;
	}	
	
	private String securityMethods;
	private String issuedBy;
	
	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public String getSecurityMethods() {
		return securityMethods;
	}
	public void setSecurityMethods(String securityMethods) {
		this.securityMethods = securityMethods;
	}
	public Measure getUldActualWeight() {
		return uldActualWeight;
	}
	public void setUldActualWeight(Measure uldActualWeight) {
		this.uldActualWeight = uldActualWeight;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	public String getMessageSenderIdentifier() {
		return messageSenderIdentifier;
	}
	public void setMessageSenderIdentifier(String messageSenderIdentifier) {
		this.messageSenderIdentifier = messageSenderIdentifier;
	}
	public String getMessageRecipientIdentifier() {
		return messageRecipientIdentifier;
	}
	public void setMessageRecipientIdentifier(String messageRecipientIdentifier) {
		this.messageRecipientIdentifier = messageRecipientIdentifier;
	}

	public boolean isProcessMRDWithoutFlight() {
		return processMRDWithoutFlight;
	}
	public void setProcessMRDWithoutFlight(boolean processMRDWithoutFlight) {
		this.processMRDWithoutFlight = processMRDWithoutFlight;
	}

	public String getFunctionPoint() {
		return functionPoint;
	}
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	


}