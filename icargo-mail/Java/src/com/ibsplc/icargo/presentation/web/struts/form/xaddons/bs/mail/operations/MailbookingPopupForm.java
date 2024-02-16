/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbookingPopupForm.java
 *
 *	Created by	:	A-7531
 *	Created on	:	03-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbookingPopupForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	03-Aug-2017	:	Draft
 */
public class MailbookingPopupForm extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	private static final String PRODUCT_NAME = "bsmail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "searchconsignmentResources";
	
	
	private String masterDocumentNumber;
	 private String bookingFrom;
	 private String bookingTo;
	 private String mailScc;
	 private String mailProduct;
	 private String orginOfBooking;
	 private String destinationOfBooking;
	 private String viaPointOfBooking;
	 private String stationOfBooking;
	 private String shipmentDate;
	 private String bookingCarrierCode;
	 private String bookingFlightNumber;
	 private String bookingFlightFrom;
	 private String bookingFlightTo;
	 private String agentCode;
	 private String customerCode;
	 private String bookingUserId;
	 private String bookingStatus;
	 private String shipmentPrefix;
	 private String displayPage = "1";
	 private String lastPageNum = "0";
	 private String totalViewRecords = "0";
	 private String[] selectedMail;
	 private String[] selectedMailbagId;
	 private String popUpFlag;
	 private String actionName;
	 private String currentDialogOption;
		
		private String currentDialogId;
		
		private String consignmentDocument;
		private int selectedAwbIndex;
	 
	 
	 public int getSelectedAwbIndex() {
			return selectedAwbIndex;
		}
		public void setSelectedAwbIndex(int selectedAwbIndex) {
			this.selectedAwbIndex = selectedAwbIndex;
		}
	 private int bookingFlightSequenceNumber;// a-8061 for ICRD-237844
	
	 private String destMismatchFlag;
	 
	
	public String getDestMismatchFlag() {
		return destMismatchFlag;
	}
	public void setDestMismatchFlag(String destMismatchFlag) {
		this.destMismatchFlag = destMismatchFlag;
	}
	 /**
	  * 
	  * 	Method		:	MailbookingPopupForm.getActionName
	  *	Added by 	:	A-7371 on 02-Nov-2017
	  * 	Used for 	:
	  *	Parameters	:	@return 
	  *	Return type	: 	String
	  */
	 public String getActionName() {
		return actionName;
	}
    /**
     * 
     * 	Method		:	MailbookingPopupForm.setActionName
     *	Added by 	:	A-7371 on 02-Nov-2017
     * 	Used for 	:
     *	Parameters	:	@param actionName 
     *	Return type	: 	void
     */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	/**
	 * 	Getter for bookingFrom 
	 *	Added by : A-7531 on 03-Aug-2017
	 *  @DateFieldId Added by A-8893 for ICRD-332006 
	 * 	Used for :
	 */
	 
	 
	 
	@DateFieldId(id="MailBookingPopupDateRange",fieldType="from")
	public String getBookingFrom() {
		return bookingFrom;
	}

	/**
	 *  @param bookingFrom the bookingFrom to set
	 * 	Setter for bookingFrom 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFrom(String bookingFrom) {
		this.bookingFrom = bookingFrom;
	}

	/**
	 * 	Getter for bookingTo 
	 *	Added by : A-7531 on 03-Aug-2017
	 *  @DateFieldId Added by A-8893 for ICRD-332006
	 * 	Used for :
	 */
	 
	 
	@DateFieldId(id="MailBookingPopupDateRange",fieldType="to")
	public String getBookingTo() {
		return bookingTo;
	}

	/**
	 *  @param bookingTo the bookingTo to set
	 * 	Setter for bookingTo 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingTo(String bookingTo) {
		this.bookingTo = bookingTo;
	}

	/**
	 * 	Getter for mailScc 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getMailScc() {
		return mailScc;
	}

	/**
	 *  @param mailScc the mailScc to set
	 * 	Setter for mailScc 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setMailScc(String mailScc) {
		this.mailScc = mailScc;
	}

	/**
	 * 	Getter for orginOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getOrginOfBooking() {
		return orginOfBooking;
	}

	/**
	 *  @param orginOfBooking the orginOfBooking to set
	 * 	Setter for orginOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setOrginOfBooking(String orginOfBooking) {
		this.orginOfBooking = orginOfBooking;
	}

	/**
	 * 	Getter for destinationOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getDestinationOfBooking() {
		return destinationOfBooking;
	}

	/**
	 *  @param destinationOfBooking the destinationOfBooking to set
	 * 	Setter for destinationOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setDestinationOfBooking(String destinationOfBooking) {
		this.destinationOfBooking = destinationOfBooking;
	}

	/**
	 * 	Getter for viaPointOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getViaPointOfBooking() {
		return viaPointOfBooking;
	}

	/**
	 *  @param viaPointOfBooking the viaPointOfBooking to set
	 * 	Setter for viaPointOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setViaPointOfBooking(String viaPointOfBooking) {
		this.viaPointOfBooking = viaPointOfBooking;
	}

	/**
	 * 	Getter for stationOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getStationOfBooking() {
		return stationOfBooking;
	}

	/**
	 *  @param stationOfBooking the stationOfBooking to set
	 * 	Setter for stationOfBooking 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setStationOfBooking(String stationOfBooking) {
		this.stationOfBooking = stationOfBooking;
	}

	/**
	 * 	Getter for shipmentDate 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getShipmentDate() {
		return shipmentDate;
	}

	/**
	 *  @param shipmentDate the shipmentDate to set
	 * 	Setter for shipmentDate 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	/**
	 * 	Getter for bookingCarrierCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingCarrierCode() {
		return bookingCarrierCode;
	}

	/**
	 *  @param bookingCarrierCode the bookingCarrierCode to set
	 * 	Setter for bookingCarrierCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingCarrierCode(String bookingCarrierCode) {
		this.bookingCarrierCode = bookingCarrierCode;
	}

	/**
	 * 	Getter for bookingFlightNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}

	/**
	 *  @param bookingFlightNumber the bookingFlightNumber to set
	 * 	Setter for bookingFlightNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}

	/**
	 * 	Getter for bookingFlightFrom 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightFrom() {
		return bookingFlightFrom;
	}

	/**
	 *  @param bookingFlightFrom the bookingFlightFrom to set
	 * 	Setter for bookingFlightFrom 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightFrom(String bookingFlightFrom) {
		this.bookingFlightFrom = bookingFlightFrom;
	}

	/**
	 * 	Getter for bookingFlightTo 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingFlightTo() {
		return bookingFlightTo;
	}

	/**
	 *  @param bookingFlightTo the bookingFlightTo to set
	 * 	Setter for bookingFlightTo 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingFlightTo(String bookingFlightTo) {
		this.bookingFlightTo = bookingFlightTo;
	}

	/**
	 * 	Getter for agentCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 *  @param agentCode the agentCode to set
	 * 	Setter for agentCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 	Getter for customerCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 *  @param customerCode the customerCode to set
	 * 	Setter for customerCode 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 	Getter for bookingUserId 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingUserId() {
		return bookingUserId;
	}

	/**
	 *  @param bookingUserId the bookingUserId to set
	 * 	Setter for bookingUserId 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingUserId(String bookingUserId) {
		this.bookingUserId = bookingUserId;
	}

	/**
	 * 	Getter for bookingStatus 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getBookingStatus() {
		return bookingStatus;
	}

	/**
	 *  @param bookingStatus the bookingStatus to set
	 * 	Setter for bookingStatus 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	public String getMailProduct() {
		return mailProduct;
	}

	public void setMailProduct(String mailProduct) {
		this.mailProduct = mailProduct;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getTotalViewRecords() {
		return totalViewRecords;
	}

	public void setTotalViewRecords(String totalViewRecords) {
		this.totalViewRecords = totalViewRecords;
	}

	public String[] getSelectedMail() {
		return selectedMail;
	}

	public void setSelectedMail(String[] selectedMail) {
		this.selectedMail = selectedMail;
	}

	public String[] getSelectedMailbagId() {
		return selectedMailbagId;
	}

	public void setSelectedMailbagId(String[] selectedMailbagId) {
		this.selectedMailbagId = selectedMailbagId;
	}

	public String getPopUpFlag() {
		return popUpFlag;
	}

	public void setPopUpFlag(String popUpFlag) {
		this.popUpFlag = popUpFlag;
	}
	public int getBookingFlightSequenceNumber() {
		return bookingFlightSequenceNumber;
	}
	public void setBookingFlightSequenceNumber(int bookingFlightSequenceNumber) {
		this.bookingFlightSequenceNumber = bookingFlightSequenceNumber;
	}
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}
	public String getCurrentDialogId() {
		return currentDialogId;
	}
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}
	 public String getConsignmentDocument() {
			return consignmentDocument;
		}
		public void setConsignmentDocument(String consignmentDocument) {
			this.consignmentDocument = consignmentDocument;
		}
}
