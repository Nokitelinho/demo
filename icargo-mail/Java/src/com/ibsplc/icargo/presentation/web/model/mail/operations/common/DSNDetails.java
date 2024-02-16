package com.ibsplc.icargo.presentation.web.model.mail.operations.common;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.DSNDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Sep-2019		:	Draft
 */
public class DSNDetails {
	
	private String companyCode; 
	
    private String dsn;
    
    private String originExchangeOffice;
    
    private String destinationExchangeOffice;
    
    private String mailClass;
    
    private int year;
    
    private String mailCategoryCode;
    
    private String mailSubclass;
    
    private int bags;
    
    private String weight;
    
    private String remarks;
    
    private String csgDocNum;
    
    private int receivedBags;
    
    private String masterDocumentNumber;
    
    private String shipmentPrefix;
    
    private String origin;
    
    private String Destination;
    
    private String paCode;
    
    private boolean pltEnable;
    
    private boolean routingAvl;
    
    private String readyForDeliveryTime;
	
	private String readyForDeliveryDate;
	
	private String requiredDeliveryTime;
	
	private String acceptanceStatus;
	
	private String consignmentDate;
	
	private String carrierCode;
	
	private String flightNumber;
	
	private String flightDate;
	
	private String uldNumber;
	
	

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	public String getMailSubclass() {
		return mailSubclass;
	}

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCsgDocNum() {
		return csgDocNum;
	}

	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	public int getReceivedBags() {
		return receivedBags;
	}

	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public boolean isPltEnable() {
		return pltEnable;
	}

	public void setPltEnable(boolean pltEnable) {
		this.pltEnable = pltEnable;
	}

	public boolean isRoutingAvl() {
		return routingAvl;
	}

	public void setRoutingAvl(boolean routingAvl) {
		this.routingAvl = routingAvl;
	}

	public String getReadyForDeliveryTime() {
		return readyForDeliveryTime;
	}

	public void setReadyForDeliveryTime(String readyForDeliveryTime) {
		this.readyForDeliveryTime = readyForDeliveryTime;
	}

	public String getReadyForDeliveryDate() {
		return readyForDeliveryDate;
	}

	public void setReadyForDeliveryDate(String readyForDeliveryDate) {
		this.readyForDeliveryDate = readyForDeliveryDate;
	}
    
	/**
	 * @return the shipmentPrefix
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix the shipmentPrefix to set
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRequiredDeliveryTime() {
		return requiredDeliveryTime;
	}

	public void setRequiredDeliveryTime(String requiredDeliveryTime) {
		this.requiredDeliveryTime = requiredDeliveryTime;
	}

	public String getAcceptanceStatus() {
		return acceptanceStatus;
	}

	public void setAcceptanceStatus(String acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}

	public String getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

		

}
