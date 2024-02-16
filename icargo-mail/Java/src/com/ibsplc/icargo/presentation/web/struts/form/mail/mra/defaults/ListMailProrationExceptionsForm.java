/*

 *

 * ListMailProrationExceptionsForm.java Created on 02 Sep 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
 package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;



	/**
	 * @author A-3108
	 *
	 */
	public class ListMailProrationExceptionsForm extends ScreenModel {

		private static final String PRODUCT = "mail";

		private static final String SUBPRODUCT = "mra";

		private static final String SCREENID = "mailtracking.mra.defaults.listmailprorationexceptions";

		private static final String BUNDLE = "mralistprorationexceptions";
		

		/*
		 * Filter fields
		 */
		private String exceptionCode="";
		
	    private String carrierCode="";
	    
	    private String flightNumber="";
		
		private String flightDate="";
		
		private String origin="";
		
		private String destination="";
		
		private String dispatchNo="";	
		
		private String status="";
		
		private String assignedStatus="";

		private String fromDate="";
		
		private String toDate="";
		//8331
		private String csgDocNum="";
		private String asignee= "";			
		private String parameterValue="";
		private String mailbagID="";
		private String originOfficeOfExchange="";
		private String gpaCode="";

		private String currentDialogOption;
		
		private String currentDialogId;
		
		private String[] exceptionStatus;
		
		public String getGpaCode() {
			return gpaCode;
		}

		public void setGpaCode(String gpaCode) {
			this.gpaCode = gpaCode;
		}
		public String getOriginOfficeOfExchange() {
			return originOfficeOfExchange;
		}

		public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
			this.originOfficeOfExchange = originOfficeOfExchange;
		}

		public String getDestinationOfficeOfExchange() {
			return destinationOfficeOfExchange;
		}

		public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
			this.destinationOfficeOfExchange = destinationOfficeOfExchange;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getSubClass() {
			return subClass;
		}

		public void setSubClass(String subClass) {
			this.subClass = subClass;
		}

		public String getMailCategory() {
			return mailCategory;
		}

		public void setMailCategory(String mailCategory) {
			this.mailCategory = mailCategory;
		}

		public String getHighestNumberIndicator() {
			return highestNumberIndicator;
		}

		public void setHighestNumberIndicator(String highestNumberIndicator) {
			this.highestNumberIndicator = highestNumberIndicator;
		}

		public String getRegisteredIndicator() {
			return registeredIndicator;
		}

		public void setRegisteredIndicator(String registeredIndicator) {
			this.registeredIndicator = registeredIndicator;
		}

		public String getReceptacleSerialNumber() {
			return receptacleSerialNumber;
		}

		public void setReceptacleSerialNumber(String receptacleSerialNumber) {
			this.receptacleSerialNumber = receptacleSerialNumber;
		}

		private String destinationOfficeOfExchange="";
		
		private String year="";
		private String subClass="";
		private String mailCategory="";
		private String highestNumberIndicator="";
		private String registeredIndicator="";
		private String receptacleSerialNumber="";
		/**
		 * @return the mailbagID
		 */
		public String getMailbagID() {
			return mailbagID;
		}

		/**
		 * @param mailbagID the mailbagID to set
		 */
		public void setMailbagID(String mailbagID) {
			this.mailbagID = mailbagID;
		}

		/**
		 * @return the parameterValue
		 */
		public String getParameterValue() {
			return parameterValue;
		}

		/**
		 * @param parameterValue the parameterValue to set
		 */
		public void setParameterValue(String parameterValue) {
			this.parameterValue = parameterValue;
		}
		
		/*
		 * Hidden fields
		 */
		private String displayPage="1";
		
		private String lastPageNum="0";

		private String calendarFormat = "";
		
		private String parentScreenId = "";
		
		private String operationFlag = "";
		
		/**
		 * Details Fields
		 */

		private String[] rowId;
		private String[] assignedUser;
		private String[] assignedTime;
		
		private String clickedButton;
		private String selectedRows;
		
		/**
		 * @author a-3429 for calling from View flight sector revenue screen
		 */
		private String invokingScreen;
		
		private String closeFlag;
		
		/*
		 * For ANZ CR : AirNZ1011 
		 */		
		private String mode;
		
		
		//added by A-5223 for ICRD-21098 starts
		public static final String PAGINATION_MODE_FROM_FILTER = "LIST";
		
		public static final String PAGINATION_MODE_FROM_NAVIGATION_LINK = "LINK";
		
		private String paginationMode;
		
		//added by A-5223 for ICRD-21098 ends
		
		/**
		 * @return the closeFlag
		 */
		public String getCloseFlag() {
			return closeFlag;
		}

		/**
		 * @param closeFlag the closeFlag to set
		 */
		public void setCloseFlag(String closeFlag) {
			this.closeFlag = closeFlag;
		}

		/**
		 * @return Returns the SCREENID.
		 */
		public String getScreenId() {
			return SCREENID;
		}

		/**
		 * @return Returns the PRODUCT.
		 */
		public String getProduct() {
			return PRODUCT;
		}

		/**
		 * @return Returns the SUBPRODUCT.
		 */
		public String getSubProduct() {
			return SUBPRODUCT;
		}

		/**
		 * @return Returns the bundle.
		 */
		public String getBundle() {
			return BUNDLE;
		}

		/**
		 * @return
		 */
		public String getExceptionCode() {
			return exceptionCode;
		}

		/**
		 * @param exceptionCode
		 */
		public void setExceptionCode(String exceptionCode) {
			this.exceptionCode = exceptionCode;
		}

		/**
		 * @return
		 */
		public String getCarrierCode() {
			return carrierCode;
		}

		/**
		 * @param carrierCode
		 */
		public void setCarrierCode(String carrierCode) {
			this.carrierCode = carrierCode;
		}

		/**
		 * @return
		 */
		public String getFlightNumber() {
			return flightNumber;
		}

		/**
		 * @param flightNumber
		 */
		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}

		/**
		 * @return
		 */
		public String getFlightDate() {
			return flightDate;
		}

		/**
		 * @param flightDate
		 */
		public void setFlightDate(String flightDate) {
			this.flightDate = flightDate;
		}

		/**
		 * @return
		 */
		@ DateFieldId(id="AssignExceptionsDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
		public String getFromDate() {
			return fromDate;
		}

		/**
		 * @param fromDate
		 */
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}

		/**
		 * @return
		 */
		@ DateFieldId(id="AssignExceptionsDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
		public String getToDate() {
			return toDate;
		}

		/**
		 * @param toDate
		 */
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}

		/**
		 * @return
		 */
		public String getOrigin() {
			return origin;
		}

		/**
		 * @param origin
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
		}

		/**
		 * @return
		 */
		public String getDestination() {
			return destination;
		}

		/**
		 * @param destination
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}

		/**
		 * @return
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the asignee
		 */
		public String getAsignee() {
			return asignee;
		}

		/**
		 * @param asignee the asignee to set
		 */
		public void setAsignee(String asignee) {
			this.asignee = asignee;
		}

		public String getDispatchNo() {
			return dispatchNo;
		}

		public void setDispatchNo(String dispatchNo) {
			this.dispatchNo = dispatchNo;
		}

		public String getCalendarFormat() {
			return calendarFormat;
		}

		public void setCalendarFormat(String calendarFormat) {
			this.calendarFormat = calendarFormat;
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

		public String getOperationFlag() {
			return operationFlag;
		}

		public void setOperationFlag(String operationFlag) {
			this.operationFlag = operationFlag;
		}

		public String getParentScreenId() {
			return parentScreenId;
		}

		public void setParentScreenId(String parentScreenId) {
			this.parentScreenId = parentScreenId;
		}

		public String[] getAssignedTime() {
			return assignedTime;
		}

		public void setAssignedTime(String[] assignedTime) {
			this.assignedTime = assignedTime;
		}

		public String[] getAssignedUser() {
			return assignedUser;
		}

		public void setAssignedUser(String[] assignedUser) {
			this.assignedUser = assignedUser;
		}

		public String[] getRowId() {
			return rowId;
		}

		public void setRowId(String[] rowId) {
			this.rowId = rowId;
		}

		public String getAssignedStatus() {
			return assignedStatus;
		}

		public void setAssignedStatus(String assignedStatus) {
			this.assignedStatus = assignedStatus;
		}

		public String getClickedButton() {
			return clickedButton;
		}

		public void setClickedButton(String clickedButton) {
			this.clickedButton = clickedButton;
		}

		/**
		 * @return the invokingScreen
		 */
		public String getInvokingScreen() {
			return invokingScreen;
		}

		/**
		 * @param invokingScreen the invokingScreen to set
		 */
		public void setInvokingScreen(String invokingScreen) {
			this.invokingScreen = invokingScreen;
		}

		/**
		 * @return the mode
		 */
		public String getMode() {
			return mode;
		}

		/**
		 * @param mode the mode to set
		 */
		public void setMode(String mode) {
			this.mode = mode;
		}

		/**
		 * 	Getter for paginationMode 
		 *	Added by : a-5223 on 17-Oct-2012
		 * 	Used for : getting Pagination Mode
		 */
		public String getPaginationMode() {
			return paginationMode;
		}

		/**
		 *  @param paginationMode the paginationMode to set
		 * 	Setter for paginationMode 
		 *	Added by : a-5223 on 17-Oct-2012
		 * 	Used for : setting Pagination Mode
		 */
		public void setPaginationMode(String paginationMode) {
			this.paginationMode = paginationMode;
		}
		/**
		 * @return the selectedRows
		 */
		public String getSelectedRows() {
			return selectedRows;
		}
		/**
		 * @param selectedRows the selectedRows to set
		 */
		public void setSelectedRows(String selectedRows) {
			this.selectedRows = selectedRows;
		}

		/**
		 * @return the currentDialogOption
		 */
		public String getCurrentDialogOption() {
			return currentDialogOption;
		}

		/**
		 * @param currentDialogOption the currentDialogOption to set
		 */
		public void setCurrentDialogOption(String currentDialogOption) {
			this.currentDialogOption = currentDialogOption;
		}

		/**
		 * @return the currentDialogId
		 */
		public String getCurrentDialogId() {
			return currentDialogId;
		}

		/**
		 * @param currentDialogId the currentDialogId to set
		 */
		public void setCurrentDialogId(String currentDialogId) {
			this.currentDialogId = currentDialogId;
		}

		/**
		 * @return the exceptionStatus
		 */
		public String[] getExceptionStatus() {
			return exceptionStatus;
		}

		/**
		 * @param exceptionStatus the exceptionStatus to set
		 */
		public void setExceptionStatus(String[] exceptionStatus) {
			this.exceptionStatus = exceptionStatus;
		}

		public String getCsgDocNum() {
			return csgDocNum;
		}

		public void setCsgDocNum(String csgDocNum) {
			this.csgDocNum = csgDocNum;
		}
		
		
}
