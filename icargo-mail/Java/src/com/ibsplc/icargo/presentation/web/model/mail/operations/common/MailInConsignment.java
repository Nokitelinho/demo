package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class MailInConsignment{

	private int statedBags;
	private String statedWeight;
	private String uldNumber;
	private String mailId;
	private String mailCategoryCode;
	private String mailSubclass;
	private String receptacleSerialNumber;
	private String registeredOrInsuredIndicator;
	private String highestNumberedReceptacle;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private long mailSequenceNumber;
	private double declaredValue;
	private LocalDate consignmentDate;
	private String airportCode;
	private int carrierId;
	private Measure volume;
	  private int TotalLetterBags;
	  private int TotalParcelBags;
	  private Measure TotalLetterWeight;
	  private Measure TotalParcelWeight;
	private LocalDate reqDeliveryTime;
	private String displayUnit;
	  private String mailOrigin;
	  private String mailDestination;
	private String currencyCode;
	private String operationFlag;
	private Measure strWeight;
	private LocalDate transWindowEndTime; 
	
	private String mailServiceLevel;
	private String requiredDlvTime;  // to display in normal view
	private String requiredDlvDate; // to display in normal view
	private String operation;   // Outbound or inbound
	private String mailStatus;
	public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getRequiredDlvTime() {
		return requiredDlvTime;
	}
	public void setRequiredDlvTime(String requiredDlvTime) {
		this.requiredDlvTime = requiredDlvTime;
	}
	public String getRequiredDlvDate() {
		return requiredDlvDate;
	}
	public void setRequiredDlvDate(String requiredDlvDate) {
		this.requiredDlvDate = requiredDlvDate;
	}
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	

	

	public LocalDate getTransWindowEndTime() {
		return transWindowEndTime;
	}

	public void setTransWindowEndTime(LocalDate transWindowEndTime) {
		this.transWindowEndTime = transWindowEndTime;
	}
	
	public int getStatedBags() {
		return statedBags;
	}

	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	public String getStatedWeight() {
		return statedWeight;
	}

	public void setStatedWeight(String statedWeight) {
		this.statedWeight = statedWeight;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
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

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}

	public void setRegisteredOrInsuredIndicator(String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
	}

	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}

	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
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

	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public Measure getVolume() {
		return volume;
	}

	public void setVolume(Measure volume) {
		this.volume = volume;
	}
	public int getTotalLetterBags() {
		return TotalLetterBags;
	}
	public void setTotalLetterBags(int totalLetterBags) {
		TotalLetterBags = totalLetterBags;
	}
	public int getTotalParcelBags() {
		return TotalParcelBags;
	}
	public void setTotalParcelBags(int totalParcelBags) {
		TotalParcelBags = totalParcelBags;
	}
	public Measure getTotalLetterWeight() {
		return TotalLetterWeight;
	}
	public void setTotalLetterWeight(Measure totalLetterWeight) {
		TotalLetterWeight = totalLetterWeight;
	}
	public Measure getTotalParcelWeight() {
		return TotalParcelWeight;
	}
	public void setTotalParcelWeight(Measure totalParcelWeight) {
		TotalParcelWeight = totalParcelWeight;
	}
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}

	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	public String getDisplayUnit() {
		return displayUnit;
	}

	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public Measure getStrWeight() {
		return strWeight;
	}

	public void setStrWeight(Measure strWeight) {
		this.strWeight = strWeight;
	}
	
	

}
