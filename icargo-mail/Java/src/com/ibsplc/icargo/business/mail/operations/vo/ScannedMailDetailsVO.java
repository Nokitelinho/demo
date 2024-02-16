/*
 * ScannedMailDetailsVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;

/**
 * 
 * @author A-3109
 * 
 */

public class ScannedMailDetailsVO extends AbstractVO {
	private String airportCode;
	private String companyCode;
	private String mailSource;
	private String containerNumber;

	private String pou;

	private String pol;

	private int carrierId;

	private String flightNumber;

	private String flightStatus;

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private String containerType;

	private Collection<MailbagVO> mailDetails;

	private Collection<MailbagVO> errorMailDetails;

	private Collection<DespatchDetailsVO> despatchDetails;

	private LocalDate flightDate;

	private String carrierCode;

	private String destination;

	private String acceptedFlag;

	private String operationFlag;

	private int scannedBags;

	//private double scannedWeight;
	private Measure scannedWeight;//added by A-7371

	private int unsavedBags;

	//private double unsavedWeight;
	private Measure unsavedWeight;//added by A-7371

	private int savedBags;

	//private double savedWeight;
	private Measure savedWeight;//added by A-7371

	private String ownAirlineCode;

	private int legSerialNumber;

	// for client purpose
	private String status;

	private String summaryFlag;

	private String newContainer;

	private String errorType;

	private String errorDescription;

	private String processPoint;

	private int exceptionBagCout;

	private int deletedExceptionBagCout;

	private boolean acknowledged;

	private boolean hasErrors;

	private String inboundCarrierCode;

	private String inboundFlightNumber;

	private LocalDate inboundFlightDate;

	private Collection<ExistingMailbagVO> existingMailbagVOS;

	private ContainerDetailsVO validatedContainer;

	private String containerReassignFlag;

	private Collection<ErrorVO> containerSpecificErrors;

	private Collection<ContainerVO> scannedContainerDetails;

	private String toFlightNumber;

	private int toCarrierid;

	private String toCarrierCode;

	private long toFlightSequenceNumber;

	private int toSegmentSerialNumber;

	private int toLegSerialNumber;

	private LocalDate toFlightDate;

	private LocalDate operationTime;

	private String remarks;

	private String ContainerProcessPoint;
	// Aded for CRQ ICRD-89077
	private String containerArrivalFlag;

	private String containerDeliveryFlag;

	private Collection<MailUploadVO> offlineMailDetails; // Added for ICRD-84459
	// Added for icrd-85213
	private String duplicateMailOverride;
	// Added as part of bug ICRD-144132 by A-5526
	private String transferFromCarrier;
	// Added as part of icrd-125328
	private boolean expReassign;
	// Added for ICRD-122072 by A-4810
	private String scannedUser;
	private boolean fromPolExist;
	private Collection<String> pols;
	private Collection<String> pous;
	
	private Collection<FlightValidationVO> flightValidationVOS;
	private boolean foundArrival;//Added by A-5219 for ICRD-256200 for checking the found arrival cases of mail-awb
	private boolean splitBooking;
	private boolean contOffloadReq;
	private String overrideValidations;//added by a-7871 for ICRD-227884
	
	private String transferFrmFlightNum;//Added by a-7871 for ICRD-240184
	private long transferFrmFlightSeqNum;
	private LocalDate transferFrmFlightDate;
	private String isContainerPabuilt;//Added by a-7871 for ICRD-287127
	
	private String androidFlag;	 
	private String operationType;
	
	private boolean arrivalException;//Added by A-8164 for ICRD-330543
	private String processPointBeforeArrival;
	private boolean flightBypassFlag;
	private String containerAsSuchArrOrDlvFlag;
	private int totalMailbagCount;//Added as part of ICRD-340690
	private Measure totalMailbagWeight;//Added as part of ICRD-340690
	private int receivedMailbagCount;
	private Measure receivedMailbagWeight;
	private int deliveredMailbagCount;   
    private Measure deliveredMailbagWeight;
    private String transactionLevel;
    private boolean notReqSecurityValAtDel;
    private boolean notReqAppReqFlgVal;
    private int transferFrmCarrierId;
	
	public int getReceivedMailbagCount() {
		return receivedMailbagCount;
	}
	public void setReceivedMailbagCount(int receivedMailbagCount) {
		this.receivedMailbagCount = receivedMailbagCount;
	}
	public Measure getReceivedMailbagWeight() {
		return receivedMailbagWeight;
	}
	public void setReceivedMailbagWeight(Measure receivedMailbagWeight) {
		this.receivedMailbagWeight = receivedMailbagWeight;
	}
	public int getDeliveredMailbagCount() {
		return deliveredMailbagCount;
	}
	public void setDeliveredMailbagCount(int deliveredMailbagCount) {
		this.deliveredMailbagCount = deliveredMailbagCount;
	}
	public Measure getDeliveredMailbagWeight() {
		return deliveredMailbagWeight;
	}
	public void setDeliveredMailbagWeight(Measure deliveredMailbagWeight) {
		this.deliveredMailbagWeight = deliveredMailbagWeight;
	}
	private boolean isValidateULDExists;
	private boolean foundTransfer;//Added by A-8164 for IASCB-34167 starts

	//added by A-9529 for IASCB-44567
	private String storageUnit;
	public String getStorageUnit() {
		return storageUnit;
	}
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}

	private String messageVersion;//Added by A-8627 for IASCB-58918	  
    private LocalDate deviceDateAndTime;//added by A-8353 for IASCB-57925
    private int transferFrmFlightLegSerialNumber;
    private boolean isFoundDelivery;
    
    private boolean atdCaptured;
    private boolean forceAcpAfterErr;//added by A-8353
    private boolean mailbagValidationRequired;//added by A-8353 
    private String errorCode;//added by A-8353 
    private boolean forceAccepted;//added by A-8353 
    private boolean notAccepted;//added by A-8353 
    private boolean originScan;//added by A-8353 
    private boolean containerFoundTransfer;

    private boolean rsgnmailbagFromdiffContainer;

    private String containerJourneyId;

    private String uldFulIndFlag;
	private String flightDateString;
	private String transitFlag;
	 private long uldReferenceNo;
	 private Measure actualUldWeight;
	public Measure getActualUldWeight() {
		return actualUldWeight;
	}
	public void setActualUldWeight(Measure actualUldWeight) {
		this.actualUldWeight = actualUldWeight;
	}
	public String getTransitFlag() {
		return transitFlag;
	}
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
	private Collection<ConsignmentScreeningVO> consignmentScreeningVos;
	private boolean isScreeningPresent;
	public boolean isScreeningPresent() {
		return isScreeningPresent;
	}
	public void setScreeningPresent(boolean isScreeningPresent) {
		this.isScreeningPresent = isScreeningPresent;
	}
	public Collection<ConsignmentScreeningVO> getConsignmentScreeningVos() {
		return consignmentScreeningVos;
	}
	public void setConsignmentScreeningVos(Collection<ConsignmentScreeningVO> consignmentScreeningVos) {
		this.consignmentScreeningVos = consignmentScreeningVos;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
	/**
	 * @return the isContainerPabuilt
	 */
	public String getIsContainerPabuilt() {
		return isContainerPabuilt;
	}
	/**
	 * @param isContainerPabuilt the isContainerPabuilt to set
	 */
	public void setIsContainerPabuilt(String isContainerPabuilt) {
		this.isContainerPabuilt = isContainerPabuilt;
	}
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
	 * 
	 * @return
	 */
	public Measure getScannedWeight() {
		return scannedWeight;
	}
	/**
	 * @return the overrideValidations
	 */
	public String getOverrideValidations() {
		return overrideValidations;
	}
	/**
	 * @param overrideValidations the overrideValidations to set
	 */
	public void setOverrideValidations(String overrideValidations) {
		this.overrideValidations = overrideValidations;
	}
	/**
	 * 
	 * @param scannedWeight
	 */
	public void setScannedWeight(Measure scannedWeight) {
		this.scannedWeight = scannedWeight;
	}
	/**
	 * 
	 * @return unsavedWeight
	 */
	public Measure getUnsavedWeight() {
		return unsavedWeight;
	}
	/**
	 * 
	 * @param unsavedWeight
	 */
	public void setUnsavedWeight(Measure unsavedWeight) {
		this.unsavedWeight = unsavedWeight;
	}
	/**
	 * 
	 * @return savedWeight
	 */
	public Measure getSavedWeight() {
		return savedWeight;
	}
	/**
	 * 
	 * @param savedWeight
	 */
	public void setSavedWeight(Measure savedWeight) {
		this.savedWeight = savedWeight;
	}
	/**
	 * 
	 * @return
	 */
	public Collection<FlightValidationVO> getFlightValidationVOS() {
	return flightValidationVOS;
}
public void setFlightValidationVOS(
		Collection<FlightValidationVO> flightValidationVOS) {
	this.flightValidationVOS = flightValidationVOS;
}

	/**
	 * @return the offlineMailDetails
	 */
	public Collection<MailUploadVO> getOfflineMailDetails() {
		return offlineMailDetails;
	}

	/**
	 * @param offlineMailDetails
	 *            the offlineMailDetails to set
	 */
	public void setOfflineMailDetails(
			Collection<MailUploadVO> offlineMailDetails) {
		this.offlineMailDetails = offlineMailDetails;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the acceptedFlag.
	 */
	public String getAcceptedFlag() {
		return acceptedFlag;
	}

	/**
	 * @param acceptedFlag
	 *            The acceptedFlag to set.
	 */
	public void setAcceptedFlag(String acceptedFlag) {
		this.acceptedFlag = acceptedFlag;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
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
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the mailDetails.
	 */
	public Collection<MailbagVO> getMailDetails() {
		return mailDetails;
	}

	/**
	 * @param mailDetails
	 *            The mailDetails to set.
	 */
	public void setMailDetails(Collection<MailbagVO> mailDetails) {
		this.mailDetails = mailDetails;
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
	 * @return Returns the ownAirlineCode.
	 */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	/**
	 * @param ownAirlineCode
	 *            The ownAirlineCode to set.
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the savedBags.
	 */
	public int getSavedBags() {
		return savedBags;
	}

	/**
	 * @param savedBags
	 *            The savedBags to set.
	 */
	public void setSavedBags(int savedBags) {
		this.savedBags = savedBags;
	}

	/**
	 * @return Returns the savedWeight.
	 */
	/*public double getSavedWeight() {
		return savedWeight;
	}

	*//**
	 * @param savedWeight
	 *            The savedWeight to set.
	 *//*
	public void setSavedWeight(double savedWeight) {
		this.savedWeight = savedWeight;
	}*/

	/**
	 * @return Returns the scannedBags.
	 */
	public int getScannedBags() {
		return scannedBags;
	}

	/**
	 * @param scannedBags
	 *            The scannedBags to set.
	 */
	public void setScannedBags(int scannedBags) {
		this.scannedBags = scannedBags;
	}

	/**
	 * @return Returns the scannedWeight.
	 */
	/*public double getScannedWeight() {
		return scannedWeight;
	}

	*//**
	 * @param scannedWeight
	 *            The scannedWeight to set.
	 *//*
	public void setScannedWeight(double scannedWeight) {
		this.scannedWeight = scannedWeight;
	}*/

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
	 * @return Returns the unsavedBags.
	 */
	public int getUnsavedBags() {
		return unsavedBags;
	}

	/**
	 * @param unsavedBags
	 *            The unsavedBags to set.
	 */
	public void setUnsavedBags(int unsavedBags) {
		this.unsavedBags = unsavedBags;
	}

	/**
	 * @return Returns the unsavedWeight.
	 */
	/*public double getUnsavedWeight() {
		return unsavedWeight;
	}

	*//**
	 * @param unsavedWeight
	 *            The unsavedWeight to set.
	 *//*
	public void setUnsavedWeight(double unsavedWeight) {
		this.unsavedWeight = unsavedWeight;
	}*/

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the summaryFlag.
	 */
	public String getSummaryFlag() {
		return this.summaryFlag;
	}

	/**
	 * @param summaryFlag
	 *            The summaryFlag to set.
	 */
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}

	/**
	 * @return Returns the newContainer.
	 */
	public String getNewContainer() {
		return this.newContainer;
	}

	/**
	 * @param newContainer
	 *            The newContainer to set.
	 */
	public void setNewContainer(String newContainer) {
		this.newContainer = newContainer;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the existingMailbagVOS
	 */
	public Collection<ExistingMailbagVO> getExistingMailbagVOS() {
		return existingMailbagVOS;
	}

	/**
	 * @param existingMailbagVOS
	 *            the existingMailbagVOS to set
	 */
	public void setExistingMailbagVOS(
			Collection<ExistingMailbagVO> existingMailbagVOS) {
		this.existingMailbagVOS = existingMailbagVOS;
	}

	public Collection<DespatchDetailsVO> getDespatchDetails() {
		return despatchDetails;
	}

	public void setDespatchDetails(Collection<DespatchDetailsVO> despatchDetails) {
		this.despatchDetails = despatchDetails;
	}

	/**
	 * @return the acknowledged
	 */
	public boolean isAcknowledged() {
		return acknowledged;
	}

	/**
	 * @param acknowledged
	 *            the acknowledged to set
	 */
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	/**
	 * @return the deletedExceptionBagCout
	 */
	public int getDeletedExceptionBagCout() {
		return deletedExceptionBagCout;
	}

	/**
	 * @param deletedExceptionBagCout
	 *            the deletedExceptionBagCout to set
	 */
	public void setDeletedExceptionBagCout(int deletedExceptionBagCout) {
		this.deletedExceptionBagCout = deletedExceptionBagCout;
	}

	/**
	 * @return the errorMailDetails
	 */
	public Collection<MailbagVO> getErrorMailDetails() {
		return errorMailDetails;
	}

	/**
	 * @param errorMailDetails
	 *            the errorMailDetails to set
	 */
	public void setErrorMailDetails(Collection<MailbagVO> errorMailDetails) {
		this.errorMailDetails = errorMailDetails;
	}

	/**
	 * @return the exceptionBagCout
	 */
	public int getExceptionBagCout() {
		return exceptionBagCout;
	}

	/**
	 * @param exceptionBagCout
	 *            the exceptionBagCout to set
	 */
	public void setExceptionBagCout(int exceptionBagCout) {
		this.exceptionBagCout = exceptionBagCout;
	}

	/**
	 * @return the hasErrors
	 */
	public boolean isHasErrors() {
		return hasErrors;
	}

	/**
	 * @param hasErrors
	 *            the hasErrors to set
	 */
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	/**
	 * @return the processPoint
	 */
	public String getProcessPoint() {
		return processPoint;
	}

	/**
	 * @param processPoint
	 *            the processPoint to set
	 */
	public void setProcessPoint(String processPoint) {
		this.processPoint = processPoint;
	}

	/**
	 * @return the inboundCarrierCode
	 */
	public String getInboundCarrierCode() {
		return inboundCarrierCode;
	}

	/**
	 * @param inboundCarrierCode
	 *            the inboundCarrierCode to set
	 */
	public void setInboundCarrierCode(String inboundCarrierCode) {
		this.inboundCarrierCode = inboundCarrierCode;
	}

	/**
	 * @return the inboundFlightDate
	 */
	public LocalDate getInboundFlightDate() {
		return inboundFlightDate;
	}

	/**
	 * @param inboundFlightDate
	 *            the inboundFlightDate to set
	 */
	public void setInboundFlightDate(LocalDate inboundFlightDate) {
		this.inboundFlightDate = inboundFlightDate;
	}

	/**
	 * @return the inboundflightNumber
	 */
	public String getInboundFlightNumber() {
		return inboundFlightNumber;
	}

	/**
	 * @param inboundflightNumber
	 *            the inboundflightNumber to set
	 */
	public void setInboundFlightNumber(String inboundFlightNumber) {
		this.inboundFlightNumber = inboundFlightNumber;
	}

	public String getContainerReassignFlag() {
		return containerReassignFlag;
	}

	public void setContainerReassignFlag(String containerReassignFlag) {
		this.containerReassignFlag = containerReassignFlag;
	}

	/**
	 * @return the containerSpecificErrors
	 */
	public Collection<ErrorVO> getContainerSpecificErrors() {
		return containerSpecificErrors;
	}

	/**
	 * @param containerSpecificErrors
	 *            the containerSpecificErrors to set
	 */
	public void setContainerSpecificErrors(
			Collection<ErrorVO> containerSpecificErrors) {
		this.containerSpecificErrors = containerSpecificErrors;
	}

	/**
	 * @return the validatedContainer
	 */
	public ContainerDetailsVO getValidatedContainer() {
		return validatedContainer;
	}

	/**
	 * @param validatedContainer
	 *            the validatedContainer to set
	 */
	public void setValidatedContainer(ContainerDetailsVO validatedContainer) {
		this.validatedContainer = validatedContainer;
	}

	/**
	 * @return the scannedContainerDetails
	 */
	public Collection<ContainerVO> getScannedContainerDetails() {
		return scannedContainerDetails;
	}

	/**
	 * @param scannedContainerDetails
	 *            the scannedContainerDetails to set
	 */
	public void setScannedContainerDetails(
			Collection<ContainerVO> scannedContainerDetails) {
		this.scannedContainerDetails = scannedContainerDetails;
	}

	/**
	 * @return the toCarrierid
	 */
	public int getToCarrierid() {
		return toCarrierid;
	}

	/**
	 * @param toCarrierid
	 *            the toCarrierid to set
	 */
	public void setToCarrierid(int toCarrierid) {
		this.toCarrierid = toCarrierid;
	}

	/**
	 * @return the toFlightNumber
	 */
	public String getToFlightNumber() {
		return toFlightNumber;
	}

	/**
	 * @param toFlightNumber
	 *            the toFlightNumber to set
	 */
	public void setToFlightNumber(String toFlightNumber) {
		this.toFlightNumber = toFlightNumber;
	}

	/**
	 * @return the toFlightSequenceNumber
	 */
	public long getToFlightSequenceNumber() {
		return toFlightSequenceNumber;
	}

	/**
	 * @param toFlightSequenceNumber
	 *            the toFlightSequenceNumber to set
	 */
	public void setToFlightSequenceNumber(long toFlightSequenceNumber) {
		this.toFlightSequenceNumber = toFlightSequenceNumber;
	}

	/**
	 * @return the toLegSerialNumber
	 */
	public int getToLegSerialNumber() {
		return toLegSerialNumber;
	}

	/**
	 * @param toLegSerialNumber
	 *            the toLegSerialNumber to set
	 */
	public void setToLegSerialNumber(int toLegSerialNumber) {
		this.toLegSerialNumber = toLegSerialNumber;
	}

	/**
	 * @return the toSegmentSerialNumber
	 */
	public int getToSegmentSerialNumber() {
		return toSegmentSerialNumber;
	}

	/**
	 * @param toSegmentSerialNumber
	 *            the toSegmentSerialNumber to set
	 */
	public void setToSegmentSerialNumber(int toSegmentSerialNumber) {
		this.toSegmentSerialNumber = toSegmentSerialNumber;
	}

	/**
	 * @return the toFlightDate
	 */
	public LocalDate getToFlightDate() {
		return toFlightDate;
	}

	/**
	 * @param toFlightDate
	 *            the toFlightDate to set
	 */
	public void setToFlightDate(LocalDate toFlightDate) {
		this.toFlightDate = toFlightDate;
	}

	/**
	 * @return the toCarrierCode
	 */
	public String getToCarrierCode() {
		return toCarrierCode;
	}

	/**
	 * @param toCarrierCode
	 *            the toCarrierCode to set
	 */
	public void setToCarrierCode(String toCarrierCode) {
		this.toCarrierCode = toCarrierCode;
	}

	/**
	 * @return the flightStatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 *            the flightStatus to set
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return the operationTime
	 */
	public LocalDate getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime
	 *            the operationTime to set
	 */
	public void setOperationTime(LocalDate operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * Getter for airportCode Added by : A-4803 on 03-Nov-2014 Used for :
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            the airportCode to set Setter for airportCode Added by :
	 *            A-4803 on 03-Nov-2014 Used for :
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * Getter for mailSource Added by : A-4803 on 03-Nov-2014 Used for :
	 */
	public String getMailSource() {
		return mailSource;
	}

	/**
	 * @param mailSource
	 *            the mailSource to set Setter for mailSource Added by : A-4803
	 *            on 03-Nov-2014 Used for :
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}

	public String getDuplicateMailOverride() {
		return duplicateMailOverride;
	}

	public void setDuplicateMailOverride(String duplicateMailOverride) {
		this.duplicateMailOverride = duplicateMailOverride;
	}

	/**
	 * @return the containerProcessPoint
	 */
	public String getContainerProcessPoint() {
		return ContainerProcessPoint;
	}

	/**
	 * @param containerProcessPoint
	 *            the containerProcessPoint to set
	 */
	public void setContainerProcessPoint(String containerProcessPoint) {
		ContainerProcessPoint = containerProcessPoint;
	}

	/**
	 * Method is to get transferFromCarrier
	 * 
	 * @return
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * Method is to set transferFromCarrier
	 * 
	 * @param transferFromCarrier
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}

	/**
	 * Method to get ContainerArrivalFlag
	 * 
	 * @return ContainerArrivalFlag
	 */
	public String getContainerArrivalFlag() {
		return containerArrivalFlag;
	}

	/**
	 * Method to set containerArrivalFlag
	 * 
	 * @param containerArrivalFlag
	 */
	public void setContainerArrivalFlag(String containerArrivalFlag) {
		this.containerArrivalFlag = containerArrivalFlag;
	}

	/**
	 * Method to get containerDeliveryFlag
	 * 
	 * @return
	 */
	public String getContainerDeliveryFlag() {
		return containerDeliveryFlag;
	}

	/**
	 * Method to set containerDeliveryFlag
	 * 
	 * @param containerDeliveryFlag
	 */
	public void setContainerDeliveryFlag(String containerDeliveryFlag) {
		this.containerDeliveryFlag = containerDeliveryFlag;
	}

	public boolean isExpReassign() {
		return expReassign;
	}

	public void setExpReassign(boolean expReassign) {
		this.expReassign = expReassign;
	}

	public String getScannedUser() {
		return scannedUser;
	}

	public void setScannedUser(String scannedUser) {
		this.scannedUser = scannedUser;

	}
	
	/**
	 * @param fromPolExist the fromPolExist to set
	 */
	public void setFromPolExist(boolean fromPolExist) {
		this.fromPolExist = fromPolExist;
	}

	/**
	 * @return the fromPolExist
	 */
	public boolean isFromPolExist() {
		return fromPolExist;
	}

	/**
	 * @param pols the pols to set
	 */
	public void setPols(Collection<String> pols) {
		this.pols = pols;
	}

	/**
	 * @return the pols
	 */
	public Collection<String> getPols() {
		return pols;
	}
	public boolean isFoundArrival() {
		return foundArrival;
	}
	public void setFoundArrival(boolean foundArrival) {
		this.foundArrival = foundArrival;
	}
	/**
	 *
	 * @return
	 */
	public boolean isSplitBooking() {
		return splitBooking;
	}
	/**
	 *
	 * @param splitBooking
	 */
	public void setSplitBooking(boolean splitBooking) {
		this.splitBooking = splitBooking;
	}
	/**
	 * @return the contOffloadReq
	 */
	public boolean isContOffloadReq() {
		return contOffloadReq;
	}
	/**
	 * @param contOffloadReq the contOffloadReq to set
	 */
	public void setContOffloadReq(boolean contOffloadReq) {
		this.contOffloadReq = contOffloadReq;
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
	public Collection<String> getPous() {
		return pous;
	}
	public void setPous(Collection<String> pous) {
		this.pous = pous;
	}
	public boolean isArrivalException() {
		return arrivalException;
	}
	public void setArrivalException(boolean arrivalException) {
		this.arrivalException = arrivalException;
	}
	public String getProcessPointBeforeArrival() {
		return processPointBeforeArrival;
	}
	public void setProcessPointBeforeArrival(String processPointBeforeArrival) {
		this.processPointBeforeArrival = processPointBeforeArrival;
	}
	public boolean isFlightBypassFlag() {
		return flightBypassFlag;
	}
	public void setFlightBypassFlag(boolean flightBypassFlag) {
		this.flightBypassFlag = flightBypassFlag;
	}
	public String getContainerAsSuchArrOrDlvFlag() {
		return containerAsSuchArrOrDlvFlag;
	}
	public void setContainerAsSuchArrOrDlvFlag(String containerAsSuchArrOrDlvFlag) {
		this.containerAsSuchArrOrDlvFlag = containerAsSuchArrOrDlvFlag;
	}
	public int getTotalMailbagCount() {
		return totalMailbagCount;
	}

	public void setTotalMailbagCount(int totalMailbagCount) {
		this.totalMailbagCount = totalMailbagCount;
	}

	public Measure getTotalMailbagWeight() {
		return totalMailbagWeight;
	}

	public void setTotalMailbagWeight(Measure totalMailbagWeight) {
		this.totalMailbagWeight = totalMailbagWeight;
	}	
	public boolean isValidateULDExists() {
		return isValidateULDExists;
	}
	public void setValidateULDExists(boolean isValidateULDExists) {
		this.isValidateULDExists = isValidateULDExists;
	}
	private boolean isContainerDestChanged;
	public boolean isContainerDestChanged() {
		return isContainerDestChanged;
	}
	public void setContainerDestChanged(boolean isContainerDestChanged) {
		this.isContainerDestChanged = isContainerDestChanged;
	}
	public boolean isFoundTransfer() {
		return foundTransfer;
	}
	public void setFoundTransfer(boolean foundTransfer) {
		this.foundTransfer = foundTransfer;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}  
    public LocalDate getDeviceDateAndTime() {
		return deviceDateAndTime;
	}
	public void setDeviceDateAndTime(LocalDate deviceDateAndTime) {
		this.deviceDateAndTime = deviceDateAndTime;
	}
	public int getTransferFrmFlightLegSerialNumber() {
		return transferFrmFlightLegSerialNumber;
	}
	public void setTransferFrmFlightLegSerialNumber(int transferFrmFlightLegSerialNumber) {
		this.transferFrmFlightLegSerialNumber = transferFrmFlightLegSerialNumber;
	}
	/**
	 * 	Getter for isFoundDelivery 
	 *	Added by : A-8061 on 01-Aug-2020
	 * 	Used for :
	 */
	public boolean isFoundDelivery() {
		return isFoundDelivery;
	}
	/**
	 *  @param isFoundDelivery the isFoundDelivery to set
	 * 	Setter for isFoundDelivery 
	 *	Added by : A-8061 on 01-Aug-2020
	 * 	Used for :
	 */
	public void setFoundDelivery(boolean isFoundDelivery) {
		this.isFoundDelivery = isFoundDelivery;
	}
	public boolean isAtdCaptured() {
		return atdCaptured;
	}
	public void setAtdCaptured(boolean atdCaptured) {
		this.atdCaptured = atdCaptured;
	}
	public boolean isMailbagValidationRequired() {
		return mailbagValidationRequired;
	}
	public void setMailbagValidationRequired(boolean mailbagValidationRequired) {
		this.mailbagValidationRequired = mailbagValidationRequired;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isForceAcpAfterErr() {
		return forceAcpAfterErr;
	}
	public void setForceAcpAfterErr(boolean forceAcpAfterErr) {
		this.forceAcpAfterErr = forceAcpAfterErr;
	}
	public boolean isForceAccepted() {
		return forceAccepted;
	}
	public void setForceAccepted(boolean forceAccepted) {
		this.forceAccepted = forceAccepted;
	}
	public boolean isNotAccepted() {
		return notAccepted;
	}
	public void setNotAccepted(boolean notAccepted) {
		this.notAccepted = notAccepted;
	}
	public boolean isOriginScan() {
		return originScan;
	}
	public void setOriginScan(boolean originScan) {
		this.originScan = originScan;
	}
	public boolean isContainerFoundTransfer() {
		return containerFoundTransfer;
	}
	public void setContainerFoundTransfer(boolean containerFoundTransfer) {
		this.containerFoundTransfer = containerFoundTransfer;
	}

	public boolean isRsgnmailbagFromdiffContainer() {
		return rsgnmailbagFromdiffContainer;
	}
	public void setRsgnmailbagFromdiffContainer(boolean rsgnmailbagFromdiffContainer) {
		this.rsgnmailbagFromdiffContainer = rsgnmailbagFromdiffContainer; 
	}
	public String getFlightDateString() {
		return flightDateString;
	}
	public void setFlightDateString(String flightDateString) {
		this.flightDateString = flightDateString;
	}
	

	public String getContainerJourneyId() {
		return containerJourneyId;
	}
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}
	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}
	
	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	public boolean isNotReqSecurityValAtDel() {
		return notReqSecurityValAtDel;
	}
	public void setNotReqSecurityValAtDel(boolean notReqSecurityValAtDel) {
		this.notReqSecurityValAtDel = notReqSecurityValAtDel;
	}
	public boolean isNotReqAppReqFlgVal() {
		return notReqAppReqFlgVal;
	}
	public void setNotReqAppReqFlgVal(boolean notReqAppReqFlgVal) {
		this.notReqAppReqFlgVal = notReqAppReqFlgVal;
	}  
    public int getTransferFrmCarrierId() {
		return transferFrmCarrierId;
	}
	public void setTransferFrmCarrierId(int transferFrmCarrierId) {
		this.transferFrmCarrierId = transferFrmCarrierId;
	}
	
	private Measure netWeight;
	private String unit;
	
	public Measure getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Measure netWeight) {
		this.netWeight = netWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	private String weightStatus;
	public String getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(String weightStatus) {
		this.weightStatus = weightStatus;
	}
 
	private String lastUpdateUser;
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
