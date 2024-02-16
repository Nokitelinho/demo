/*
 * CarditEnquiryFilterVO.java Created on Jun 23, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class CarditEnquiryFilterVO extends AbstractVO {
	
    private String companyCode;
    private String carrierCode ;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private LocalDate flightDate;
    private String searchMode;
    private String resdit;
    
    private String consignmentDocument;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String ooe;
    private String doe;
	private String mailCategoryCode;
	private String mailClass;
	private String year;
    private String despatchSerialNumber;
    private String receptacleSerialNumber;
    
    private String flightType;
    private String flighDirection;
    private String pol;
    private String paoCode;
    private String mailbagId;
    
    private int absoluteIndex;
    
    private String mailSubclass;
    
    private String uldNumber;
    
    private LocalDate reqDeliveryTime;//Added as part of ICRD-214795
    
    private int totalRecordsCount;
    private int pageNumber;
    private int pageSize; //Added by A-8164 for ICRD-320122
    
  //A-8061 Added for ICRD-82434 starts
	private boolean isPendingResditChecked;
	private	LocalDate consignmentDate;		
	//A-8061 Added for ICRD-82434 ends
	
    
    //Added by a7531
    public String shipmentPrefix;
	private String documentNumber;
	//Added by A-8176 as part of ICRD-228739
	private String mailOrigin;
	private String maildestination;
	private String consignmentLevelAWbAttachRequired;
	private LocalDate transportServWindow;
    
	//added as part of IASCB-56008
	private String fromScreen;
	private int mailCount;
    
    public int getMailCount() {
		return mailCount;
	}
	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}
    public String getConsignmentLevelAWbAttachRequired() {
		return consignmentLevelAWbAttachRequired;
	}
	public void setConsignmentLevelAWbAttachRequired(String consignmentLevelAWbAttachRequired) {
		this.consignmentLevelAWbAttachRequired = consignmentLevelAWbAttachRequired;
	}
    public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
    public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
  
    /**
     * Flag for notaccepted filter
     */
    private String notAccepted;

	private String mailStatus;
	private String isAWBAttached;

	/**
	 * @return Returns the mailStatus.
	 */
	public String getMailStatus() {
		return mailStatus;
	}

	
	public String getIsAWBAttached() {
		return isAWBAttached;
	}
	public void setIsAWBAttached(String isAWBAttached) {
		this.isAWBAttached = isAWBAttached;
	}
	/**
	 * @param mailStatus The mailStatus to set.
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getPaoCode() {
		return paoCode;
	}

	public void setPaoCode(String paoCode) {
		this.paoCode = paoCode;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
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
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId The carrierId to set.
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
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the consignmentDocument.
	 */
	public String getConsignmentDocument() {
		return consignmentDocument;
	}

	/**
	 * @param consignmentDocument The consignmentDocument to set.
	 */
	public void setConsignmentDocument(String consignmentDocument) {
		this.consignmentDocument = consignmentDocument;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the doe.
	 */
	public String getDoe() {
		return doe;
	}

	/**
	 * @param doe The doe to set.
	 */
	public void setDoe(String doe) {
		this.doe = doe;
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
	 * @return Returns the flightType.
	 */
	public String getFlightType() {
		return flightType;
	}

	/**
	 * @param flightType The flightType to set.
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
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

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailClass(String mailSubclass) {
		this.mailClass = mailSubclass;
	}

	/**
	 * @return Returns the ooe.
	 */
	public String getOoe() {
		return ooe;
	}

	/**
	 * @param ooe The ooe to set.
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the resdit.
	 */
	public String getResdit() {
		return resdit;
	}

	/**
	 * @param resdit The resdit to set.
	 */
	public void setResdit(String resdit) {
		this.resdit = resdit;
	}

	/**
	 * @return Returns the searchMode.
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param searchMode The searchMode to set.
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
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
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public String getFlighDirection() {
		return flighDirection;
	}

	public void setFlighDirection(String flighDirection) {
		this.flighDirection = flighDirection;
	}

	/**
	 * @return Returns the notAccepted.
	 */
	public String getNotAccepted() {
		return notAccepted;
	}

	/**
	 * @param notAccepted The notAccepted to set.
	 */
	public void setNotAccepted(String notAccepted) {
		this.notAccepted = notAccepted;
	}

	public int getAbsoluteIndex() {
		return absoluteIndex;
	}

	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * @return the mailSubclass
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass the mailSubclass to set
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
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
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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
	 * @author A-8061
	 * Getter for isPendingResditChecked
	 * @return
	 */
	public boolean isPendingResditChecked() {
		return isPendingResditChecked;
	}
	/**
	 * @author A-8061 
	 * @param isPendingResditChecked
	 */
	public void setPendingResditChecked(boolean isPendingResditChecked) {
		this.isPendingResditChecked = isPendingResditChecked;
	}
	/**Getter forconsignmentDate
	 * @author A-8061
	 * @return
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	/**
	 * @author A-8061
	 * @param consignmentDate
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMaildestination() {
		return maildestination;
	}
	public void setMaildestination(String maildestination) {
		this.maildestination = maildestination;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public LocalDate getTransportServWindow() {
		return transportServWindow;
	}
	public void setTransportServWindow(LocalDate transportServWindow) {
		this.transportServWindow = transportServWindow;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

}
