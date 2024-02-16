/*
 * ULDFlightMessageReconcileVO.java Created on Jul 20, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2048
 */
public class ULDFlightMessageReconcileVO extends AbstractVO {
	
	/**
	 * 
	 */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.message.ULDFlightMessageReconcile";
	
    private int flightCarrierIdentifier;
    private String flightNumber;
    private int flightSequenceNumber;
    private int legSerialNumber;
    private String airportCode;
    private String companyCode;
    private String carrierCode;
    
    // OUT/IN
    private String messageType;
    private String sequenceNumber;
    private LocalDate flightDate;
    private String errorCode;
    private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	//FLTTYP Added by A-8445 for IASCB-22297
	private String flightType;
	
	//this is to identify whether the ucm is for blocked flight or not
	
	
	private String operationFlag;
	private boolean hasUldErrors;
	
	private String outSequenceNumber;
	 private boolean isToBeAvoidedFromValidationCheck;
	 
	 private String specialInformation;
	 private String messageSendFlag;
	 
	 private String flightRoute;
	 
	 private boolean toBeForcedSent;
	 
	 private boolean isBlocked;
	 //Added By Manaf for QF CR1021 Starts
	 private String origin;
	 private String destination;
	 private int pageNumber;
	 private LocalDate fromDate;
	 private LocalDate toDate;
	 private String ucmOutMissed;
	 private String ucmInMissed;
	 private String tailNumber;
	 private String rawMessage;

	//FLTTYP Added by A-9111 for IASCB-33977
	/*private String flightType;

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	} */

	public  static final String UCMOUT_MISSED = "N";
	 public  static final String UCMIN_MISSED = "N";
	 public static final String NIL ="NIL"; 
	 
	 private boolean fromReconcileScreen;
	 
	 //Added By Manaf for QF CR1021 ends
	 
	 //Added By a-3459 for bug 29943 starts
	 private LocalDate actualDate;
	 //Added By a-3459 for bug 29943 ends
	 
	 // This flag is used to find the latest Sequence Number for UCM
	 private String toSearchMaxSeqNum;
	 
	 private int totalRecords;
	 private String actionCode; 
	
	  // added A-7359 for ICRD-192413
	 private String messageSource;
	 
	 private int ucmReportedCount;
	 private int totalUldCount;
	 private int manualUldCount;
	private Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetailsVOs;
	//Added by A-6991 for ICRD-177310
	private Collection<MessageDespatchDetailsVO> msgDsptcDetailsVOs;
	
	//Added by A-7131 for ICRD-223404
    private boolean isWetLeasedFlight;

    private Collection<String> pous;
    //Added by A-7359 for ICRD-220123 
    private  Map<String,Collection<String>> errorCollection;
    
    //Added by A-7359 for ICRD-249795
    private boolean isFlightfromFinalization;
	/**
	 * @return the msgDsptcDetailsVOs
	 */
	public Collection<MessageDespatchDetailsVO> getMsgDsptcDetailsVOs() {
		return msgDsptcDetailsVOs;
	}
	/**
	 * @param msgDsptcDetailsVOs the msgDsptcDetailsVOs to set
	 */
	public void setMsgDsptcDetailsVOs(
			Collection<MessageDespatchDetailsVO> msgDsptcDetailsVOs) {
		this.msgDsptcDetailsVOs = msgDsptcDetailsVOs;
	}
	/**
	 * @return Collection<ULDFlightMessageReconcileDetailsVO> Returns the reconcileDetailsVOs.
	 */
	public Collection<ULDFlightMessageReconcileDetailsVO> getReconcileDetailsVOs() {
		return this.reconcileDetailsVOs;
	}
	/**
	 * @param reconcileDetailsVOs The reconcileDetailsVOs to set.
	 */
	public void setReconcileDetailsVOs(
			Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetailsVOs) {
		this.reconcileDetailsVOs = reconcileDetailsVOs;
	}
	/**
	 * @return String Returns the airportCode.
	 */
	public String getAirportCode() {
		return this.airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return String Returns the errorCode.
	 */
	public String getErrorCode() {
		return this.errorCode;
	}
	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return int Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return this.flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	/**
	 * @return LocalDate Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return this.flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return String Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return this.flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return int Returns the flightSequenceNumber.
	 */
	public int getFlightSequenceNumber() {
		return this.flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return LocalDate Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return String Returns the flightType.
	 */
	public String getFlightType() {
		return flightType;
	}
	/**
	 * @param flightType the flightType to set.
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	/**
	 * @return int Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}
	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return String Returns the messageType.
	 */
	public String getMessageType() {
		return this.messageType;
	}
	/**
	 * @param messageType The messageType to set.
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	/**
	 * @return String Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return String Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return boolean Returns the hasUldErrors.
	 */
	public boolean isHasUldErrors() {
		return this.hasUldErrors;
	}
	/**
	 * @param hasUldErrors The hasUldErrors to set.
	 */
	public void setHasUldErrors(boolean hasUldErrors) {
		this.hasUldErrors = hasUldErrors;
	}
	/**
	 * @return String Returns the outSequenceNumber.
	 */
	public String getOutSequenceNumber() {
		return this.outSequenceNumber;
	}
	/**
	 * @param outSequenceNumber The outSequenceNumber to set.
	 */
	public void setOutSequenceNumber(String outSequenceNumber) {
		this.outSequenceNumber = outSequenceNumber;
	}
	//@Column(name = "")
	/**
	 * @return Returns the isToBeAvoidedFromValidationCheck.
	 */
	public boolean isToBeAvoidedFromValidationCheck() {
		return isToBeAvoidedFromValidationCheck;
	}
	/**
	 * @param isToBeAvoidedFromValidationCheck The isToBeAvoidedFromValidationCheck to set.
	 */
	public void setToBeAvoidedFromValidationCheck(
			boolean isToBeAvoidedFromValidationCheck) {
		this.isToBeAvoidedFromValidationCheck = isToBeAvoidedFromValidationCheck;
	}
	/**
	 * @return String Returns the messageSendFlag.
	 */
	public String getMessageSendFlag() {
		return this.messageSendFlag;
	}
	/**
	 * @param messageSendFlag The messageSendFlag to set.
	 */
	public void setMessageSendFlag(String messageSendFlag) {
		this.messageSendFlag = messageSendFlag;
	}
	/**
	 * @return String Returns the specialInformation.
	 */
	public String getSpecialInformation() {
		return this.specialInformation;
	}
	/**
	 * @param specialInformation The specialInformation to set.
	 */
	public void setSpecialInformation(String specialInformation) {
		this.specialInformation = specialInformation;
	}
	/**
	 * @return String Returns the flightRoute.
	 */
	public String getFlightRoute() {
		return this.flightRoute;
	}
	/**
	 * @param flightRoute The flightRoute to set.
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	/**
	 * @return Returns the toBeForcedSent.
	 */
	public boolean isToBeForcedSent() {
		return this.toBeForcedSent;
	}
	/**
	 * @param toBeForcedSent The toBeForcedSent to set.
	 */
	public void setToBeForcedSent(boolean toBeForcedSent) {
		this.toBeForcedSent = toBeForcedSent;
	}    
	/**
	 * @return Returns the isBlocked.
	 */
	public boolean isBlocked() {
		return this.isBlocked;
	}
	/**
	 * @param isBlocked The isBlocked to set.
	 */
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the ucmInMissed
	 */
	public String getUcmInMissed() {
		return ucmInMissed;
	}
	/**
	 * @param ucmInMissed the ucmInMissed to set
	 */
	public void setUcmInMissed(String ucmInMissed) {
		this.ucmInMissed = ucmInMissed;
	}
	/**
	 * @return the ucmOutMissed
	 */
	public String getUcmOutMissed() {
		return ucmOutMissed;
	}
	/**
	 * @param ucmOutMissed the ucmOutMissed to set
	 */
	public void setUcmOutMissed(String ucmOutMissed) {
		this.ucmOutMissed = ucmOutMissed;
	}
	/**
	 * @return the toSearchMaxSeqNum
	 */
	public String getToSearchMaxSeqNum() {
		return toSearchMaxSeqNum;
	}
	/**
	 * @param toSearchMaxSeqNum the toSearchMaxSeqNum to set
	 */
	public void setToSearchMaxSeqNum(String toSearchMaxSeqNum) {
		this.toSearchMaxSeqNum = toSearchMaxSeqNum;
	}
	/**
	 * @return the actualDate
	 */
	public LocalDate getActualDate() {
		return actualDate;
	}
	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(LocalDate actualDate) {
		this.actualDate = actualDate;
	}
	public boolean isFromReconcileScreen() {
		return fromReconcileScreen;
	}
	public void setFromReconcileScreen(boolean fromReconcileScreen) {
		this.fromReconcileScreen = fromReconcileScreen;
	}
	/**
	 * @return the tailNumber
	 */
	public String getTailNumber() {
		return tailNumber;
	}
	/**
	 * @param tailNumber the tailNumber to set
	 */
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
	
	public String getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getMessageSource
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getMessageSource() {
		return messageSource;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.setMessageSource
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param messageSource 
	 *	Return type	: 	void
	 */
	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}
	/**
	 * @return the isWetLeasedFlight
	 */
	public boolean isWetLeasedFlight() {
		return isWetLeasedFlight;
	}
	/**
	 * @param isWetLeasedFlight the isWetLeasedFlight to set
	 */
	public void setWetLeasedFlight(boolean isWetLeasedFlight) {
		this.isWetLeasedFlight = isWetLeasedFlight;
	}

	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getPous
	 *	Added by 	:	A-7130 on 04-Oct-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	public Collection<String> getPous() {
		return pous;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.setPous
	 *	Added by 	:	A-7130 on 04-Oct-2017
	 * 	Used for 	:
	 *	Parameters	:	@param pous 
	 *	Return type	: 	void
	 */
	public void setPous(Collection<String> pous) {
		this.pous = pous;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getErrorCollection
	 *	Added by 	:	A-7359 on 14-Nov-2017
	 * 	Used for 	:	ICRD-220123 
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<String>>
	 */
	public Map<String, Collection<String>> getErrorCollection() {
		return errorCollection;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.setErrorCollection
	 *	Added by 	:	A-7359 on 14-Nov-2017
	 * 	Used for 	:   ICRD-220123 
	 *	Parameters	:	@param errorCollection 
	 *	Return type	: 	void
	 */
	public void setErrorCollection(Map<String, Collection<String>> errorCollection) {
		this.errorCollection = errorCollection;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.isFlightfromFinalization
	 *	Added by 	:	A-7359 on 01-Feb-2018
	 * 	Used for 	:	ICRD-249795
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	public boolean isFlightfromFinalization() {
		return isFlightfromFinalization;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.setFlightfromFinalization
	 *	Added by 	:	A-7359 on 01-Feb-2018
	 * 	Used for 	:	ICRD-249795
	 *	Parameters	:	@param isFlightfromFinalization 
	 *	Return type	: 	void
	 */
	public void setFlightfromFinalization(boolean isFlightfromFinalization) {
		this.isFlightfromFinalization = isFlightfromFinalization;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getUcmReportedCount
	 *	Added on 	:	07-Jul-2022
	 * 	Used for 	:   getting UCM Reported Count
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public int getUcmReportedCount() {
		return ucmReportedCount;
	}
	/**
	 * Setting UCM Reported Count
	 */
	public void setUcmReportedCount(int ucmReportedCount) {
		this.ucmReportedCount = ucmReportedCount;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getUcmReportedCount
	 *	Added on 	:	07-Jul-2022
	 * 	Used for 	:   getting Manually added ULD Count
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public int getManualUldCount() {
		return manualUldCount;
	}
	/**
	 * Setting Manually added ULD Count
	 */
	public void setManualUldCount(int manualUldCount) {
		this.manualUldCount = manualUldCount;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.getTotalUldCount
	 *	Added on 	:	22-Jul-2022
	 * 	Used for 	:	Getting total ULD Count
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public int getTotalUldCount() {
		return totalUldCount;
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcileVO.setTotalUldCount
	 *	Added on 	:	22-Jul-2022
	 * 	Used for 	:	Setting total ULD Count
	 *	Parameters	:	@param totalUldCount 
	 *	Return type	: 	void
	 */
	public void setTotalUldCount(int totalUldCount) {
		this.totalUldCount = totalUldCount;
	}
    
}
