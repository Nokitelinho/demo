/*
 * SearchContainerForm.java Created on Jun 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1876
 *
 */
public class SearchContainerForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "searchContainerResources";

	private String containerNo;
	private String fromDate;
	private String toDate;
	private String departurePort;
	private String assignedBy;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightNo;
	private String flightDate;
	private String carrier;
	private String destination;
	private String[] selectContainer;

	private String status;
	private String reassignFlag;
	private String closeFlag;
	private String fromScreen;
	private String currentAction;
	private String lastPageNum;
	private int currentIndex;
	private String currentDialogOption;
	private String currentDialogId;
	private String displayPage;
	private String assignedto;
	private String operationType="";
	
	private String assignedTo;
	private String operationTypeAll="";
	private String transferable;
	
	private String countTotalFlag=""; //added by A-5201 for CR ICRD-21098

	
	private String reList;
	//added by A-3429 for ICRD-83340
	private String notClosedFlag;
	private String mailAcceptedFlag;
	private String subclassGroup;
	 //Added by A-5945 for ICRD-96261 
	private String showEmptyContainer;
	//Added for icrd-95515
	private String warningFlag;
	private String warningOveride;
	private String[] uldType; /*added by A-8149 for ICRD-270524*/
	
	

	public String getShowEmptyContainer() {
		return showEmptyContainer;
	}

	public void setShowEmptyContainer(String showEmptyContainer) {
		this.showEmptyContainer = showEmptyContainer;
	}
	//Added by A-5945 for ICRD-96261 ends
	/**
	 * @return the notClosedFlag
	 */
	public String getNotClosedFlag() {
		return notClosedFlag;
	}

	/**
	 * @param notClosedFlag the notClosedFlag to set
	 */
	public void setNotClosedFlag(String notClosedFlag) {
		this.notClosedFlag = notClosedFlag;
	}

	/**
	 * @return the mailAcceptedFlag
	 */
	public String getMailAcceptedFlag() {
		return mailAcceptedFlag;
	}

	/**
	 * @param mailAcceptedFlag the mailAcceptedFlag to set
	 */
	public void setMailAcceptedFlag(String mailAcceptedFlag) {
		this.mailAcceptedFlag = mailAcceptedFlag;
	}

	/**
	 * @return the subclassGroup
	 */
	public String getSubclassGroup() {
		return subclassGroup;
	}

	/**
	 * @param subclassGroup the subclassGroup to set
	 */
	public void setSubclassGroup(String subclassGroup) {
		this.subclassGroup = subclassGroup;
	}
	
	public String getReList() {
		return reList;
	}

	public void setReList(String reList) {
		this.reList = reList;
	}

	/**
	 * @return Returns the assignedTo.
	 */
	public String getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo The assignedTo to set.
	 */
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return Returns the operationType.
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType The operationType to set.
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return Returns the assignedto.
	 */
	public String getAssignedto() {
		return assignedto;
	}

	/**
	 * @param assignedto The assignedto to set.
	 */
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return Returns the currentIndex.
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @param currentIndex The currentIndex to set.
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}


	/**
	 * @return Returns the currentAction.
	 */
	public String getCurrentAction() {
		return currentAction;
	}

	/**
	 * @param currentAction The currentAction to set.
	 */
	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}


	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the carrier.
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return Returns the departurePort.
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort The departurePort to set.
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the selectContainer.
	 */
	public String[] getSelectContainer() {
		return selectContainer;
	}

	/**
	 * @param selectContainer The selectContainer to set.
	 */
	public void setSelectContainer(String[] selectContainer) {
		this.selectContainer = selectContainer;
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

	/**
	 * @return Returns the assignedBy.
	 */
	public String getAssignedBy() {
		return this.assignedBy;
	}

	/**
	 * @param assignedBy The assignedBy to set.
	 */
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return this.closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the containerNo.
	 */
	public String getContainerNo() {
		return this.containerNo;
	}

	/**
	 * @param containerNo The containerNo to set.
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="ListContainerDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="ListContainerDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return this.toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return this.displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return this.lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the flightNo.
	 */
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * @param flightNo The flightNo to set.
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return Returns the reassignFlag.
	 */
	public String getReassignFlag() {
		return this.reassignFlag;
	}

	/**
	 * @param reassignFlag The reassignFlag to set.
	 */
	public void setReassignFlag(String reassignFlag) {
		this.reassignFlag = reassignFlag;
	}

	/**
	 * @return Returns the operationTypeAll.
	 */
	public String getOperationTypeAll() {
		return operationTypeAll;
	}

	/**
	 * @param operationTypeAll The operationTypeAll to set.
	 */
	public void setOperationTypeAll(String operationTypeAll) {
		this.operationTypeAll = operationTypeAll;
	}

	/**
	 * @return Returns the transferable.
	 */
	public String getTransferable() {
		return this.transferable;
	}

	/**
	 * @param transferable The transferable to set.
	 */
	public void setTransferable(String transferable) {
		this.transferable = transferable;
	}
	//added by A-5201 for CR ICRD-21098 starts
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	//added by A-5201 for CR ICRD-21098 end
	public String getFromScreen() {
		return fromScreen;
	}

	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWarningFlag() {
		return warningFlag;
	}
	/**
	 * 
	 * @param warningFlag
	 */
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String getWarningOveride() {
		return warningOveride;
	}
	/**
	 * 
	 * @param warningOveride
	 */
	public void setWarningOveride(String warningOveride) {
		this.warningOveride = warningOveride;
	}
	
	/**
	 * @author A-8149
	 * @return uldType
	 */
	
	public String[] getUldType() {
		return uldType;
	}

	/**
	 * @author A-8149
	 * @param uldType
	 */
	public void setUldType(String[] uldType) {
		this.uldType = uldType;
	}
}
