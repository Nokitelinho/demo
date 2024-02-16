package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Calendar;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailScanDetailVO  extends AbstractVO{
	
private String companyCode;
	
	private String mailBagId;
	
	private int serialNumber;
	
	private String scanData;
	
	private String scannedUser;
	
	private String airport;
	
	private String deviceId;
	
	private String deviceIpAddress;
	
	private int fileSequence;
	
	private int mailSequenceNumber;
	
	private String uploadStatus;
	private String poaCode;
	private String totalEventCodes;
	private String messageType;
	private String funtionPoint;
 	private String scanType;
 	private String flightCarrierCode;
 	private String flightNumber;
 	private LocalDate flightDate;
 	private String containerNumber;
 	private String fromCarrierCode;
 	private String fromFlightNumber;
 	private LocalDate fromFlightDate;
 	private String deliveryFlag;
 	private String offloadReason;
 	private String returnFlag;
 	private String damageReason;
 	private String containerDestination;
 	private String containerPOU;
 	private String containerType;
 	private String malComapnyCode;
 	private String screeningUser;
 	private String securityScreeningMethod;
 	private String stgUnit;
	
	public String getTotalEventCodes() {
		return totalEventCodes;
	}
	public void setTotalEventCodes(String totalEventCodes) {
		this.totalEventCodes = totalEventCodes;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	private String lastUpdateUser;
	
 	private Calendar lastUpdateTime;
 	//Added for BUG ICRD-145494 by A-5526 starts
 	private LocalDate scanDate;
 	private String nodeName;
 	//Added for BUG ICRD-145494 by A-5526 ends
 	//Added by A-7540
 	private String consignmentNumber;
 	private boolean rdtProcessing;
 	//Added by U-1317
 	private String eventCode;
 	
 	private String rawMessageBlob;
 	
/**
 * Method to get companyCode
 * @return
 */
	public String getCompanyCode() {
	return companyCode;
}
public String getRawMessageBlob() {
	return rawMessageBlob;
}
public void setRawMessageBlob(String rawMessageBlob) {
	this.rawMessageBlob = rawMessageBlob;
}
/**
 * Method to set companyCode
 * @param companyCode
 */
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}
/**
 * Method to get mailbagId
 * @return
 */
public String getMailBagId() {
	return mailBagId;
}
/**
 * Method to set mailbagId
 * @param mailbagId
 */
public void setMailBagId(String mailBagId) {
	this.mailBagId = mailBagId;
}

/**
 *  Method to get scannedPort
 * @return
 */
public String getAirport() {
	return airport;
}
/**
 * Method to set scannedPort
 * @param scannedPort
 */
public void setAirport(String airport) {
	this.airport = airport;
}
/**
 * Method to get serialNumber
 * @return
 */
public int getSerialNumber() {
	return serialNumber;
}
/**
 * Method to set serialNumber
 * @param serialNumber
 */
public void setSerialNumber(int serialNumber) {
	this.serialNumber = serialNumber;
}
/**
 * Method to get scanData
 * @return
 */
public String getScanData() {
	return scanData;
}
/**
 * Method to set scannedString
 * @param scannedString
 */
public void setScanData(String scanData) {
	this.scanData = scanData;
}
/**
 * Method to get scannedUser
 * @return
 */
public String getScannedUser() {
	return scannedUser;
}
/**
 * Method to set scannedUser
 * @param scannedUser
 */
public void setScannedUser(String scannedUser) {
	this.scannedUser = scannedUser;
}
/**
 * Method to get deviceId
 * @return
 */
public String getDeviceId() {
	return deviceId;
}
/**
 * Method to set deviceId
 * @param deviceId
 */
public void setDeviceId(String deviceId) {
	this.deviceId = deviceId;
}
/**
 * Method to get fileSequence
 * @return
 */
public int getFileSequence() {
	return fileSequence;
}
/**
 * Method to set fileSequence
 * @param fileSequence
 */
public void setFileSequence(int fileSequence) {
	this.fileSequence = fileSequence;
}
/**
 * Method to get  Upload status(N/Y)
 * @return
 */
public String getUploadStatus() {
	return uploadStatus;
}
/**
 * Method to set Upload status(N/Y)
 * @param status
 */
public void setUploadStatus(String uploadStatus) {
	this.uploadStatus = uploadStatus;
}
/**
 * Method to get lastUpdateUser
 * @return
 */
public String getLastUpdateUser() {
	return lastUpdateUser;
}
/**
 * Method to set lastUpdateUser
 * @param lastUpdateUser
 */
public void setLastUpdateUser(String lastUpdateUser) {
	this.lastUpdateUser = lastUpdateUser;
}
/**
 * Method to get lastUpdateTime
 * @return
 */
public Calendar getLastUpdateTime() {
	return lastUpdateTime;
}
/**
 * Method to set lastUpdateTime
 * @param lastUpdateTime
 */
public void setLastUpdateTime(Calendar lastUpdateTime) {
	this.lastUpdateTime = lastUpdateTime;
}
/**
 * Method to get mailSequenceNumber
 * @return
 */
public int getMailSequenceNumber() {
	return mailSequenceNumber;
}
/**
 * Method to set mailSequenceNumber
 * @param mailSequenceNumber
 */
public void setMailSequenceNumber(int mailSequenceNumber) {
	this.mailSequenceNumber = mailSequenceNumber;
}
/**
 * Method to get deviceIpAddress
 * @return
 */
public String getDeviceIpAddress() {
	return deviceIpAddress;
}
/**
 * Method to set deviceIpAddress
 * @param deviceIpAddress
 */
public void setDeviceIpAddress(String deviceIpAddress) {
	this.deviceIpAddress = deviceIpAddress;
}
/**
 * Method to get scanDate
 * @return
 */
public LocalDate getScanDate() {
	return scanDate;
}
/**
 * Method to set scanDate
 * @param scanDate
 */
public void setScanDate(LocalDate scanDate) {
	this.scanDate = scanDate;
}
/**
 * Method to get nodeName
 * @return
 */
public String getNodeName() {
	return nodeName;
}
/**
 * Method to set nodeName
 * @param nodeName
 */
public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
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
/**
 * @return the consignmentNumber
 */
public String getConsignmentNumber() {
	return consignmentNumber;
}
/**
 * @param consignmentNumber the consignmentNumber to set
 */
public void setConsignmentNumber(String consignmentNumber) {
	this.consignmentNumber = consignmentNumber;
}


public String getEventCode() {
	return eventCode;
}
public void setEventCode(String eventCode) {
	this.eventCode = eventCode;
}
public String getFuntionPoint() {
	return funtionPoint;
}
public void setFuntionPoint(String funtionPoint) {
	this.funtionPoint = funtionPoint;
}
public String getScanType() {
	return scanType;
}
public void setScanType(String scanType) {
	this.scanType = scanType;
}
public String getFlightCarrierCode() {
	return flightCarrierCode;
}
public void setFlightCarrierCode(String flightCarrierCode) {
	this.flightCarrierCode = flightCarrierCode;
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
public String getContainerNumber() {
	return containerNumber;
}
public void setContainerNumber(String containerNumber) {
	this.containerNumber = containerNumber;
}
public String getFromCarrierCode() {
	return fromCarrierCode;
}
public void setFromCarrierCode(String fromCarrierCode) {
	this.fromCarrierCode = fromCarrierCode;
}
public String getFromFlightNumber() {
	return fromFlightNumber;
}
public void setFromFlightNumber(String fromFlightNumber) {
	this.fromFlightNumber = fromFlightNumber;
}
public LocalDate getFromFlightDate() {
	return fromFlightDate;
}
public void setFromFlightDate(LocalDate fromFlightDate) {
	this.fromFlightDate = fromFlightDate;
}
public String getDeliveryFlag() {
	return deliveryFlag;
}
public void setDeliveryFlag(String deliveryFlag) {
	this.deliveryFlag = deliveryFlag;
}
public String getOffloadReason() {
	return offloadReason;
}
public void setOffloadReason(String offloadReason) {
	this.offloadReason = offloadReason;
}
public String getReturnFlag() {
	return returnFlag;
}
public void setReturnFlag(String returnFlag) {
	this.returnFlag = returnFlag;
}
public String getDamageReason() {
	return damageReason;
}
public void setDamageReason(String damageReason) {
	this.damageReason = damageReason;
}
public String getContainerDestination() {
	return containerDestination;
}
public void setContainerDestination(String containerDestination) {
	this.containerDestination = containerDestination;
}

public String getContainerPOU() {
	return containerPOU;
}
public void setContainerPOU(String containerPOU) {
	this.containerPOU = containerPOU;
}

public String getContainerType() {
	return containerType;
}
public void setContainerType(String containerType) {
	this.containerType = containerType;
}
public String getMalComapnyCode() {
	return malComapnyCode;
}
public void setMalComapnyCode(String malComapnyCode) {
	this.malComapnyCode = malComapnyCode;
}
public String getScreeningUser() {
	return screeningUser;
}
public void setScreeningUser(String screeningUser) {
	this.screeningUser = screeningUser;
}
public String getSecurityScreeningMethod() {
	return securityScreeningMethod;
}
public void setSecurityScreeningMethod(String securityScreeningMethod) {
	this.securityScreeningMethod = securityScreeningMethod;
}
public String getStgUnit() {
	return stgUnit;
}
public void setStgUnit(String stgUnit) {
	this.stgUnit = stgUnit;
}

}
