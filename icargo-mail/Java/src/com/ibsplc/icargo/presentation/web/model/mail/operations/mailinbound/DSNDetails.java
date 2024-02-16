package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import com.ibsplc.icargo.framework.util.unit.Measure;

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
    
    private Measure weight;
    
    private String remarks;
    
    private String csgDocNum;
    
    private Measure receivedWeight;
    
    private int receivedBags;
    
    private boolean checkBoxSelect;
    
    private String masterDocNumber;
    
    private String shipmentPrefix;
    
    private String origin;
    
    private String Destination;
    
    private String paCode;
    
    private boolean pltEnable;
    
    private boolean routingAvl;
    
    private String readyForDeliveryTime;
	
	private String readyForDeliveryDate;

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

	public Measure getWeight() {
		return weight;
	}

	public void setWeight(Measure weight) {
		this.weight = weight;
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

	public Measure getReceivedWeight() {
		return receivedWeight;
	}

	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}

	public int getReceivedBags() {
		return receivedBags;
	}

	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	public boolean isCheckBoxSelect() {
		return checkBoxSelect;
	}

	public void setCheckBoxSelect(boolean checkBoxSelect) {
		this.checkBoxSelect = checkBoxSelect;
	}

	public String getMasterDocNumber() {
		return masterDocNumber;
	}

	public void setMasterDocNumber(String masterDocNumber) {
		this.masterDocNumber = masterDocNumber;
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

}
