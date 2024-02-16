/*
 * MailBagEnquiryForm.java Created on Dec 14, 2005
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
 * @author A-1556
 *
 */
public class MailBagEnquiryForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "mailBagEnquiryResources";

	private String originOE;
	private String destnOE;
	private String category;
	private String subClass;
	private String year;
	private String dsn;
	private String rsn;
	private String currentStatus;
	private String port;
	private String fromDate;
	private String toDate;
	private String userId;
	private String containerType;
	private String containerNo;
	private boolean isDamaged;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String carditStatus="";
	private String mailbagId;//Added for ICRD-205027
	
	private String checkAll;
	private String[] subCheck;
	
	private String lastPageNum="0";
	private String displayPage="1";
	
	private String status;	
	private String fromScreen;	
	private String fromCarrier;
	
	// filelds for offload popup
	private String offloadReason;
	private String offloadRemarks;
	
	private String assignToFlight;
	
	private String destination;
	private String destinationCity;
	private String carrierInv;
	private String selCont;
	
	private String reList;
	
	private String successMailFlag;
	
	//added by paulson for AirNZ CR410
	private String transit;
	private String consigmentNumber;
	private String upuCode;
	//Added for ICRD-214795 starts
	private String reqDeliveryDate;
	private String reqDeliveryTime;
	//Added for ICRD-214795 ends
	
	/* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id:  ICRD-21098 and ScreenId MTK009
	 */
	private String countTotalFlag = "";
	private String[] consignmentNumber;
	private String[] uldType; /*added by A-8149 for ICRD-270524*/
	
	

	/**
	 * @return the consigmentNumber
	 */
	public String getConsigmentNumber() {
		return consigmentNumber;
	}

	/**
	 * @param consigmentNumber the consigmentNumber to set
	 */
	public void setConsigmentNumber(String consigmentNumber) {
		this.consigmentNumber = consigmentNumber;
	}

	/**
	 * @return the upuCode
	 */
	public String getUpuCode() {
		return upuCode;
	}

	/**
	 * @param upuCode the upuCode to set
	 */
	public void setUpuCode(String upuCode) {
		this.upuCode = upuCode;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the offloadReason.
	 */
	public String getOffloadReason() {
		return offloadReason;
	}

	/**
	 * @param offloadReason The offloadReason to set.
	 */
	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}

	/**
	 * @return Returns the offloadRemarks.
	 */
	public String getOffloadRemarks() {
		return offloadRemarks;
	}

	/**
	 * @param offloadRemarks The offloadRemarks to set.
	 */
	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
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
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
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
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the checkAll.
	 */
	public String getCheckAll() {
		return checkAll;
	}

	/**
	 * @param checkAll The checkAll to set.
	 */
	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	/**
	 * @return Returns the containerNo.
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * @param containerNo The containerNo to set.
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * @return Returns the currentStatus.
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * @param currentStatus The currentStatus to set.
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * @return Returns the destnOE.
	 */
	public String getDestnOE() {
		return destnOE;
	}

	/**
	 * @param destnOE The destnOE to set.
	 */
	public void setDestnOE(String destnOE) {
		this.destnOE = destnOE;
	}

	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
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
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="MailBagEnquiryDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	/**
	 * @return Returns the isDamaged.
	 */
	public boolean isDamaged() {
		return isDamaged;
	}

	/**
	 * @param isDamaged The isDamaged to set.
	 */
	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	/**
	 * @return Returns the originOE.
	 */
	public String getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE The originOE to set.
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return Returns the port.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port The port to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return Returns the rsn.
	 */
	public String getRsn() {
		return rsn;
	}

	/**
	 * @param rsn The rsn to set.
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return Returns the subClass.
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass The subClass to set.
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return Returns the subCheck.
	 */
	public String[] getSubCheck() {
		return subCheck;
	}

	/**
	 * @param subCheck The subCheck to set.
	 */
	public void setSubCheck(String[] subCheck) {
		this.subCheck = subCheck;
	}

	/**
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="MailBagEnquiryDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * @return Returns the assignToFlight.
	 */
	public String getAssignToFlight() {
		return assignToFlight;
	}

	/**
	 * @param assignToFlight The assignToFlight to set.
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}
	

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
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
	 * @return Returns the carditStatus.
	 */
	public String getCarditStatus() {
		return carditStatus;
	}

	/**
	 * @param carditStatus The carditStatus to set.
	 */
	public void setCarditStatus(String carditStatus) {
		this.carditStatus = carditStatus;
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
	 * @return Returns the carrierInv.
	 */
	public String getCarrierInv() {
		return carrierInv;
	}

	/**
	 * @param carrierInv The carrierInv to set.
	 */
	public void setCarrierInv(String carrierInv) {
		this.carrierInv = carrierInv;
	}

	public String getFromCarrier() {
		return fromCarrier;
	}

	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}

	public String getSelCont() {
		return selCont;
	}

	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}

	public String getReList() {
		return reList;
	}

	public void setReList(String reList) {
		this.reList = reList;
	}

	public String getSuccessMailFlag() {
		return successMailFlag;
	}

	public void setSuccessMailFlag(String successMailFlag) {
		this.successMailFlag = successMailFlag;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	//added by A-5216
	/**
	 * 
	 * @param countTotalFlag
	 */
	public void setCountTotalFlag(String countTotalFlag) {
		this.countTotalFlag = countTotalFlag;
	}
	
	/**
	 * 
	 * @return countTotalFlag
	 */
	public String getCountTotalFlag() {
		return countTotalFlag;
	}

	/**
	 * 	Getter for mailbagId 
	 *	Added by : a-6245 on 14-Jun-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : a-6245 on 14-Jun-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String[] consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return the consignmentNumber
	 */
	public String[] getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * 	Getter for reqDeliveryDate 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}

	/**
	 *  @param reqDeliveryDate the reqDeliveryDate to set
	 * 	Setter for reqDeliveryDate 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}

	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
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
