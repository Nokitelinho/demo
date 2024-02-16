/*
 * MailbagEnquiryFilterVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class MailbagEnquiryFilterVO extends AbstractVO {
    private String ooe;
    private String doe;
    private String mailCategoryCode;
    private String mailSubclass;  
    private String year;
    private String despatchSerialNumber;
    private String receptacleSerialNumber;
    private String currentStatus;
    private String scanPort;
    private LocalDate scanFromDate;
    private LocalDate scanToDate;
    private String damageFlag;
    private String scanUser;
    private String containerNumber;
    private String carrierCode;
    private String flightNumber;
    private LocalDate flightDate; 
    private int carrierId; 
    private String companyCode; 
    //Added to include From Carrier in filter criteria
    private String fromCarrier;
    
    //Added to include the Cardit Present Status in the Filter Criteria 
    private String carditPresent; 
    
    //Added By Karthick V
    //To be used wen the MailBags are to be used for InventoryList Screen.
    private boolean isInventory; 
    //Added to include the Destination City required for ViewInventoryMailBags
    private String  destinationCity;
    // Added by Paulson for AirNZ CR410 
    private String transitFlag;
    /**
     * Added By Karthick V to include the Incoming Flight in the Inventory ..
     */
    
    private String fromFlightNumber;
    
    /* added by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	private int totalRecords;
	
	private int pageNumber;
	
	private String fromScreen;
    
	//Added for ICRD-133967 starts
		private String consigmentNumber;
		private String upuCode;
		//Added for ICRD-133967 ends
    
	private String fromExportList;
	private String mailbagId;//Added for ICRD-205027
	private LocalDate reqDeliveryTime;//Added as part of ICRD-214795
	//Added for ICRD-245211 starts
	private String origin;
	private String awbNumber;
	private String fromDate;
	private String toDate;
	//private String upuCode;
	//Added for ICRD-245211 ends
	
	//Added by A-7929 as part of ICRD-241437 starts....
	private  String lyingList;
	private String filterType;
	private int defaultPageSize;
	private String pacode;
	private String upliftAirport;
	private String uldNumber;
	private String originAirportCode; 
	private String destinationAirportCode;
    private int flightCarrierIdr;
    private int pageSize;//Added by A-8353 for ICRD-324698
    private String serviceLevel;//Added by A-8672 for ICRD-327149
    private String onTimeDelivery;//Added for ICRD-323389
    private ArrayList <String> mailBagsToList;//Added by A-8164 for mailinbound
    private LocalDate transportServWindow;
    private LocalDate consignmentDate;
	private String transferFromCarrier;
	private String shipmentPrefix;
	private String masterDocumentNumber;
    public int getFlightCarrierIdr() {
		return flightCarrierIdr;
	}
	public void setFlightCarrierIdr(int flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
	}
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	private int flightSequenceNumber;
	
    
	
	
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getOriginAirportCode() {
		return originAirportCode;
	}
	public void setOriginAirportCode(String originAirportCode) {
		this.originAirportCode = originAirportCode;
	}
	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}
	public void setDestinationAirportCode(String destinationAirportCode) {
		this.destinationAirportCode = destinationAirportCode;
	}
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getUpliftAirport() {
		return upliftAirport;
	}
	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	public String getPacode() {
		return pacode;
	}
	public void setPacode(String pacode) {
		this.pacode = pacode;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public String getLyingList() {
		return lyingList;
	}
	public void setLyingList(String lyingList) {
		this.lyingList = lyingList;
	}
	//Added by A-7929 as part of ICRD-241437 ends....
    public String getConsigmentNumber() {
		return consigmentNumber;
	}
	public void setConsigmentNumber(String consigmentNumber) {
		this.consigmentNumber = consigmentNumber;
	}
	public String getUpuCode() {
		return upuCode;
	}
	public void setUpuCode(String upuCode) {
		this.upuCode = upuCode;
	}
    public String getFromFlightNumber() {
		return fromFlightNumber;
	}
	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
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
	 * @return Returns the isInventory.
	 */
	public boolean isInventory() {
		return isInventory;
	}
	/**
	 * @param isInventory The isInventory to set.
	 */
	public void setInventory(boolean isInventory) {
		this.isInventory = isInventory;
	}
	/**
	 * @return Returns the carditPresent.
	 */
	public String getCarditPresent() {
		return carditPresent;
	}
	/**
	 * @param carditPresent The carditPresent to set.
	 */
	public void setCarditPresent(String carditPresent) {
		this.carditPresent = carditPresent;
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
     * @return Returns the containerNumber.
     */
    public String getContainerNumber() {
        return containerNumber;
    }
    /**
     * @param containerNumber The containerNumber to set.
     */
    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
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
     * @return Returns the damageFlag.
     */
    public String getDamageFlag() {
        return damageFlag;
    }
    /**
     * @param damageFlag The damageFlag to set.
     */
    public void setDamageFlag(String damageFlag) {
        this.damageFlag = damageFlag;
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
     * @return Returns the mailCategoryCode.
     */
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }
    /**
	 * 	Getter for fromExportList 
	 *	Added by : a-4809 on Sep 2, 2014
	 * 	Used for :
	 */
	public String getFromExportList() {
		return fromExportList;
	}
	/**
	 *  @param fromExportList the fromExportList to set
	 * 	Setter for fromExportList 
	 *	Added by : a-4809 on Sep 2, 2014
	 * 	Used for :
	 */
	public void setFromExportList(String fromExportList) {
		this.fromExportList = fromExportList;
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
    public String getMailSubclass() {
        return mailSubclass;
    }
    /**
     * @param mailSubclass The mailSubclass to set.
     */
    public void setMailSubclass(String mailSubclass) {
        this.mailSubclass = mailSubclass;
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
     * @return Returns the scanFromDate.
     */
    public LocalDate getScanFromDate() {
        return scanFromDate;
    }
    /**
     * @param scanFromDate The scanFromDate to set.
     */
    public void setScanFromDate(LocalDate scanFromDate) {
        this.scanFromDate = scanFromDate;
    }
    /**
     * @return Returns the scanPort.
     */
    public String getScanPort() {
        return scanPort;
    }
    /**
     * @param scanPort The scanPort to set.
     */
    public void setScanPort(String scanPort) {
        this.scanPort = scanPort;
    }
    /**
     * @return Returns the scanToDate.
     */
    public LocalDate getScanToDate() {
        return scanToDate;
    }
    /**
     * @param scanToDate The scanToDate to set.
     */
    public void setScanToDate(LocalDate scanToDate) {
        this.scanToDate = scanToDate;
    }
    /**
     * @return Returns the scanUser.
     */
    public String getScanUser() {
        return scanUser;
    }
    /**
     * @param scanUser The scanUser to set.
     */
    public void setScanUser(String scanUser) {
        this.scanUser = scanUser;
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
	public String getFromCarrier() {
		return fromCarrier;
	}
	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}
	public String getTransitFlag() {
		return transitFlag;
	}
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
	
	//added by A-5216
	/**
	 * @param totalRecords.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * @return Returns the totalRecords.
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	
	//added by A-5216
	/**
	 * @param pageNumber.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
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
	 *	Added by : A-6245 on 27-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 27-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
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
	 * @return the awbNumber
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber the awbNumber to set
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	//Added by A-8672 for ICRD-327149 starts
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	//Added by A-8672 for ICRD-327149 ends	
	/**
	 * @return the onTimeDelivery
	 */
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}
	public ArrayList<String> getMailBagsToList() {
		return mailBagsToList;
	}
	public void setMailBagsToList(ArrayList<String> mailBagsToList) {
		this.mailBagsToList = mailBagsToList;
	}
	public LocalDate getTransportServWindow() {
		return transportServWindow;
	}
	public void setTransportServWindow(LocalDate transportServWindow) {
		this.transportServWindow = transportServWindow;
	}
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	
	
}
