package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;

public class Consignment{
	
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private String consignmentDate;
	private String airportCode;
	private boolean isScanned;
	private String type;
	private String subType;
	private String operation;
	private Collection<ConsignmentRouting> consignmentRouting;
	private PageResult<MailInConsignment> mailsInConsignmentPage;
	private String lastUpdateUser;
	private LocalDate lastUpdateTime;
	private String remarks;
	private int statedBags;
	private Measure statedWeight;
	private String operationFlag;
	private Collection<MailInConsignment> mailsInConsignment;
	private String orgin;
	private String destination;
	private String despatchDate;
	private ArrayList<String> subsequentFlight;
	private String pou;
	private String orginCity;
	private String destinationCity;
	private String orginAirport;
	private String fltDate;
	private String dateOfDept;
	private String flight;
	private String carrierCode;
	private String destPort;
	private String reportType;
	private String mailCategory;

	private String destinationOfficeOfExchange;
	private String originOfficeOfExchange;
	private int year;
	private String dsnNumber;
	private String uldNumber;
	private String mailSubClass;
	private String mailClass;
	private String operatorOrigin;
	private String operatorDestination;
	private String OOEDescription;
	private String DOEDescription;
	private String consignmentPriority;
	private String transportationMeans;
	private String flightDetails;
	private String flightRoute;
	private LocalDate firstFlightDepartureDate;
	private String airlineCode;
	private String printMailTag;
	private List<MailBagsInExcelConsignment> excelMailBags;
	private boolean isDomestic;
	private int[] selectedMailbagIndex;
	private String shipmentPrefix;
	 private String masterDocumentNumber;
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
	public String getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public boolean isScanned() {
		return isScanned;
	}
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Collection<ConsignmentRouting> getConsignmentRouting() {
		return consignmentRouting;
	}
	public void setConsignmentRouting(Collection<ConsignmentRouting> consignmentRouting) {
		this.consignmentRouting = consignmentRouting;
	}
	public PageResult<MailInConsignment> getMailsInConsignmentPage() {
		return mailsInConsignmentPage;
	}
	public void setMailsInConsignmentPage(PageResult<MailInConsignment> mailsInConsignmentPage) {
		this.mailsInConsignmentPage = mailsInConsignmentPage;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getStatedBags() {
		return statedBags;
	}
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}
	public Measure getStatedWeight() {
		return statedWeight;
	}
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public Collection<MailInConsignment> getMailsInConsignment() {
		return mailsInConsignment;
	}
	public void setMailsInConsignment(Collection<MailInConsignment> mailsInConsignment) {
		this.mailsInConsignment = mailsInConsignment;
	}
	public String getOrgin() {
		return orgin;
	}
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDespatchDate() {
		return despatchDate;
	}
	public void setDespatchDate(String despatchDate) {
		this.despatchDate = despatchDate;
	}
	public ArrayList<String> getSubsequentFlight() {
		return subsequentFlight;
	}
	public void setSubsequentFlight(ArrayList<String> subsequentFlight) {
		this.subsequentFlight = subsequentFlight;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getOrginCity() {
		return orginCity;
	}
	public void setOrginCity(String orginCity) {
		this.orginCity = orginCity;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getOrginAirport() {
		return orginAirport;
	}
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}
	public String getFltDate() {
		return fltDate;
	}
	public void setFltDate(String fltDate) {
		this.fltDate = fltDate;
	}
	public String getDateOfDept() {
		return dateOfDept;
	}
	public void setDateOfDept(String dateOfDept) {
		this.dateOfDept = dateOfDept;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getDestPort() {
		return destPort;
	}
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDsnNumber() {
		return dsnNumber;
	}
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getMailSubClass() {
		return mailSubClass;
	}
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public String getOperatorOrigin() {
		return operatorOrigin;
	}
	public void setOperatorOrigin(String operatorOrigin) {
		this.operatorOrigin = operatorOrigin;
	}
	public String getOperatorDestination() {
		return operatorDestination;
	}
	public void setOperatorDestination(String operatorDestination) {
		this.operatorDestination = operatorDestination;
	}
	public String getOOEDescription() {
		return OOEDescription;
	}
	public void setOOEDescription(String oOEDescription) {
		OOEDescription = oOEDescription;
	}
	public String getDOEDescription() {
		return DOEDescription;
	}
	public void setDOEDescription(String dOEDescription) {
		DOEDescription = dOEDescription;
	}
	public String getConsignmentPriority() {
		return consignmentPriority;
	}
	public void setConsignmentPriority(String consignmentPriority) {
		this.consignmentPriority = consignmentPriority;
	}
	public String getTransportationMeans() {
		return transportationMeans;
	}
	public void setTransportationMeans(String transportationMeans) {
		this.transportationMeans = transportationMeans;
	}
	public String getFlightDetails() {
		return flightDetails;
	}
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}
	public String getFlightRoute() {
		return flightRoute;
	}
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public LocalDate getFirstFlightDepartureDate() {
		return firstFlightDepartureDate;
	}
	public void setFirstFlightDepartureDate(LocalDate firstFlightDepartureDate) {
		this.firstFlightDepartureDate = firstFlightDepartureDate;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getPrintMailTag() {
		return printMailTag;
	}
	public void setPrintMailTag(String printMailTag) {
		this.printMailTag = printMailTag;
	}
	public List<MailBagsInExcelConsignment> getExcelMailBags() {
		return excelMailBags;
	}
	public void setExcelMailBags(List<MailBagsInExcelConsignment> excelMailBags) {
		this.excelMailBags = excelMailBags;
	}
	public boolean isDomestic() {
		return isDomestic;
	}
	public void setDomestic(boolean isDomestic) {
		this.isDomestic = isDomestic;
	}
	/**
	 * @return the selectedMailbagIndex
	 */
	public int[] getSelectedMailbagIndex() {
		return selectedMailbagIndex;
	}
	/**
	 * @param selectedMailbagIndex the selectedMailbagIndex to set
	 */
	public void setSelectedMailbagIndex(int[] selectedMailbagIndex) {
		this.selectedMailbagIndex = selectedMailbagIndex;
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
	/**
	 * @return the masterDocumentNumber
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 * @param masterDocumentNumber the masterDocumentNumber to set
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	
	}
