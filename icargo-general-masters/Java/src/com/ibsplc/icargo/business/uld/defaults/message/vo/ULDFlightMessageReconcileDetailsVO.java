/*
 * ULDFlightMessageReconcileDetailsVO.java Created on Jul 20, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2048
 */
public class ULDFlightMessageReconcileDetailsVO extends AbstractVO {

	    
		private int flightCarrierIdentifier;
	    private String flightNumber;
	    private int flightSequenceNumber;
	    private int legSerialNumber;
	    private String airportCode;
	    private String companyCode;
	    private String messageType;
	    private String sequenceNumber;
	    private String uldNumber;
	    private String pou;
	    private String content;
	    private String errorCode;
	    private String operationFlag;
	    private String carrierCode;
	    private LocalDate flightDate;
	    private String damageStatus;
	    private boolean isToBeAvoidedFromValidationCheck;
	    private String lastUpdateUser;
	    private LocalDate lastUpdateTime;
	    
	    private String ucmErrorCode;
	    private boolean fromReconcileScreen;
	    
	    // added A-5125 for ICRD-2268
	    private String uldFlightStatus;
	    
	    // added A-7359 for ICRD-192413
	    private String uldSource;
		private String uldStatus;
	    private String pol;
	    
	    //Added by A-7131 for ICRD-223404
	    private boolean isWetLeasedFlight;
	    
	    public static final String NIL = "NIL";
	    /**
	     * 
	     * @return String returns the UldFlightStatus specialInformation
	     */
		public String getUldFlightStatus() {
			return uldFlightStatus;
		}
		public void setUldFlightStatus(String uldFlightStatus) {
			this.uldFlightStatus = uldFlightStatus;
		}
		public boolean isFromReconcileScreen() {
			return fromReconcileScreen;
		}
		public void setFromReconcileScreen(boolean fromReconcileScreen) {
			this.fromReconcileScreen = fromReconcileScreen;
		}
		/**
		 * @return String Returns the ucmErrorCode.
		 */
		public String getUcmErrorCode() {
			return this.ucmErrorCode;
		}
		/**
		 * @param ucmErrorCode The ucmErrorCode to set.
		 */
		public void setUcmErrorCode(String ucmErrorCode) {
			this.ucmErrorCode = ucmErrorCode;
		}
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
		 * @return String Returns the damageStatus.
		 */
		public String getDamageStatus() {
			return this.damageStatus;
		}
		/**
		 * @param damageStatus The damageStatus to set.
		 */
		public void setDamageStatus(String damageStatus) {
			this.damageStatus = damageStatus;
		}
		/**
		 * @return Returns the flightDate.
		 */
		public LocalDate getFlightDate() {
			return flightDate;
		}
		/**
		 * @param flightDate The flightDate to set.
		 */
		public void setFlightDate(LocalDate flightDate) {
			this.flightDate = flightDate;
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
		 * @return String Returns the content.
		 */
		public String getContent() {
			return this.content;
		}
		/**
		 * @param content The content to set.
		 */
		public void setContent(String content) {
			this.content = content;
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
		 * @return String Returns the pou.
		 */
		public String getPou() {
			return this.pou;
		}
		/**
		 * @param pou The pou to set.
		 */
		public void setPou(String pou) {
			this.pou = pou;
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
		 * @return String Returns the uldNumber.
		 */
		public String getUldNumber() {
			return this.uldNumber;
		}
		/**
		 * @param uldNumber The uldNumber to set.
		 */
		public void setUldNumber(String uldNumber) {
			this.uldNumber = uldNumber;
		}
		/**
		 * @return Returns the lastUpdateTime.
		 */
		public LocalDate getLastUpdateTime() {
			return lastUpdateTime;
		}
		/**
		 * @param lastUpdateTime The lastUpdateTime to set.
		 */
		public void setLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
		/**
		 * @return Returns the lastUpdateUser.
		 */
		public String getLastUpdateUser() {
			return lastUpdateUser;
		}
		/**
		 * @param lastUpdateUser The lastUpdateUser to set.
		 */
		public void setLastUpdateUser(String lastUpdateUser) {
			this.lastUpdateUser = lastUpdateUser;
		}
		/**
		 * @return the pol
		 */
		public String getPol() {
			return pol;
		}
		/**
		 * @param pol the pol to set
		 */
		public void setPol(String pol) {
			this.pol = pol;
		}
		/**
		 * 
		 * 	Method		:	ULDFlightMessageReconcileDetailsVO.getUldSource
		 *	Added by 	:	A-7359 on 24-Aug-2017
		 * 	Used for 	:	ICRD-192413
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		 public String getUldSource() {
				return uldSource;
		}
		/**
		 * 
		 * 	Method		:	ULDFlightMessageReconcileDetailsVO.setUldSource
		 *	Added by 	:	A-7359 on 24-Aug-2017
		 * 	Used for 	:	ICRD-192413
		 *	Parameters	:	@param uldSource 
		 *	Return type	: 	void
		 */
		 public void setUldSource(String uldSource) {
				this.uldSource = uldSource;
	 	}
		 /**
		  * 
		  * Method		:	ULDFlightMessageReconcileDetailsVO.getUldStatus
		  *	Added by 	:	A-7359 on 24-Aug-2017
		  * Used for 	:   ICRD-192413
		  *	Parameters	:	@return 
		  *	Return type	: 	String
		  */
		 public String getUldStatus() {
				return uldStatus;
		 }
		 /**
		  * 
		  * Method		:	ULDFlightMessageReconcileDetailsVO.setUldStatus
		  *	Added by 	:	A-7359 on 24-Aug-2017
		  * Used for 	:	ICRD-192413
		  *	Parameters	:	@param uldStatus 
		  *	Return type	: 	void
		  */
		 public void setUldStatus(String uldStatus) {
				this.uldStatus = uldStatus;
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

}
