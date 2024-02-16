/*
 * MailInInventoryListVO.java Created on Jun 30, 2016
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
 * @author 
 * This class is used to hold the mailBag related Details necessary for the 
 * Inventory List..
 *
 */
public class MailInInventoryListVO extends AbstractVO {
	    
	    private String companyCode;
	    private String uldNumber;
	    private String destinationCity;
	    private String  mailCategoryCode;
	    private int acceptedBags;
	    //private double acceptedWeight;
	    private Measure acceptedWeight;//added by A-7371 
	    private int carrierID;
	    private String currentAirport; 
	    private String carrierCode; 
	    private String assignedUser;  
	     //Added By Karthick V Need For Validating against the Partner Carriers ... 
	    
	    private String containerType;
	    
	    
	    private String ownAirlineCode; 
	    
	    // Added by Roopak for NCA CR
	    private String originPAdesc;
	    private String originPA;

	    
	    private String fromFlightNumber;
	    
	    private String fromFlightCarrierCode;
	    
	    private LocalDate fromFlightDate; 
	    
	    /**
	     * The Transfer Date if any specified in the Client ..
	     */
	    private LocalDate operationTime; 
	    
	    
	    private String scannedUser;
	    
	    //Added by Paulson for SB uld delivery to be stamped to mailhistory 
	    private String paBuiltFlag;
	    
	    /**
	     * 
	     * @return acceptedWeight
	     */
	    public Measure getAcceptedWeight() {
			return acceptedWeight;
		}
       /**
        * 
        * @param acceptedWeight
        */
		public void setAcceptedWeight(Measure acceptedWeight) {
			this.acceptedWeight = acceptedWeight;
		}
	    
	    public String getScannedUser() {
			return scannedUser;
		}

		public void setScannedUser(String scannedUser) {
			this.scannedUser = scannedUser;
		}

		public LocalDate getOperationTime() {
			return operationTime;
		}

		public void setOperationTime(LocalDate operationTime) {
			this.operationTime = operationTime;
		}

		public String getFromFlightCarrierCode() {
			return fromFlightCarrierCode;
		}

		public void setFromFlightCarrierCode(String fromFlightCarrierCode) {
			this.fromFlightCarrierCode = fromFlightCarrierCode;
		}

		public LocalDate getFromFlightDate() {
			return fromFlightDate;
		}

		public void setFromFlightDate(LocalDate fromFlightDate) {
			this.fromFlightDate = fromFlightDate;
		}

		public String getFromFlightNumber() {
			return fromFlightNumber;
		}

		public void setFromFlightNumber(String fromFlightNumber) {
			this.fromFlightNumber = fromFlightNumber;
		}

		/**
		 * @return Returns the ownAirlineCode.
		 */
		public String getOwnAirlineCode() {
			return ownAirlineCode;
		}

		/**
		 * @param ownAirlineCode The ownAirlineCode to set.
		 */
		public void setOwnAirlineCode(String ownAirlineCode) {
			this.ownAirlineCode = ownAirlineCode;
		}

		/**
		 * @return Returns the assignedUser.
		 */
		public String getAssignedUser() {
			return assignedUser;
		}

		/**
		 * @param assignedUser The assignedUser to set.
		 */
		public void setAssignedUser(String assignedUser) {
			this.assignedUser = assignedUser;
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
		 * @return Returns the carrierID.
		 */
		public int getCarrierID() {
			return carrierID;
		}

		/**
		 * @param carrierID The carrierID to set.
		 */
		public void setCarrierID(int carrierID) {
			this.carrierID = carrierID;
		}

		/**
		 * @return Returns the currentAirport.
		 */
		public String getCurrentAirport() {
			return currentAirport;
		}

		/**
		 * @param currentAirport The currentAirport to set.
		 */
		public void setCurrentAirport(String currentAirport) {
			this.currentAirport = currentAirport;
		}

		/**
		 * @return Returns the acceptedBags.
		 */
		public int getAcceptedBags() {
			return acceptedBags;
		}
		
		/**
		 * @param acceptedBags The acceptedBags to set.
		 */
		public void setAcceptedBags(int acceptedBags) {
			this.acceptedBags = acceptedBags;
		}
		
		/**
		 * @return Returns the acceptedWeight.
		 */
		/*public double getAcceptedWeight() {
			return acceptedWeight;
		}
		*//**
		 * @param acceptedWeight The acceptedWeight to set.
		 *//*
		public void setAcceptedWeight(double acceptedWeight) {
			this.acceptedWeight = acceptedWeight;
		}*/
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
		 * @return Returns the destinationCity.
		 */
		public String getDestinationCity() {
			return destinationCity;
		}
		/**
		 * @param destinationCity The destinationCity to set.
		 */
		public void setDestinationCity(String destinationCity) {
			this.destinationCity = destinationCity;
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

		public String getContainerType() {
			return containerType;
		}

		public void setContainerType(String containerType) {
			this.containerType = containerType;
		}

		public String getOriginPA() {
			return originPA;
		}

		public void setOriginPA(String originPA) {
			this.originPA = originPA;
		}

		public String getOriginPAdesc() {
			return originPAdesc;
		}

		public void setOriginPAdesc(String originPAdesc) {
			this.originPAdesc = originPAdesc;
		}

		public String getPaBuiltFlag() {
			return paBuiltFlag;
		}

		public void setPaBuiltFlag(String paBuiltFlag) {
			this.paBuiltFlag = paBuiltFlag;
		}

		
}
