package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;

public class ConsignmentDocument {
	  private String companyCode;
	  private String consignmentNumber;
	  private String paCode;
	  private int consignmentSequenceNumber;
	  private LocalDate consignmentDate;
	  private String airportCode;
	  private boolean isScanned;
	  private String type;
	  private String subType;
	  private String operation;
	  private Collection<RoutingInConsignment> routingInConsignments;
	  private PageResult<MailInConsignment> mailInConsignmentPage;
	  private String lastUpdateUser;
	  private LocalDate lastUpdateTime;
	  private String remarks;
	  private int statedBags;
	  private Measure statedWeight = new Measure("MWT", 0.0D);
	  private String operationFlag;
	  private Collection<MailInConsignment> mailInConsignmentcolls;
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
	  private int totalSacks;
	  private Measure totalWeight;
	  private List<RoutingInConsignment> consignmentRouting;
	  private PageResult<MailInConsignment>MailsInConsignmentPage;
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
	public Collection<RoutingInConsignment> getRoutingInConsignments() {
		return routingInConsignments;
	}
	public void setRoutingInConsignmentVOs(
			Collection<RoutingInConsignment> routingInConsignments) {
		this.routingInConsignments = routingInConsignments;
	}
	/*public Page<MailInConsignment> getMailInConsignments() {
		return mailInConsignments;
	}
	public void setMailInConsignmentVOs(Page<MailInConsignment> mailInConsignments) {
		this.mailInConsignments = mailInConsignments;
	}*/
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
	public Collection<MailInConsignment> getMailInConsignmentcolls() {
		return mailInConsignmentcolls;
	}
	public void setMailInConsignmentcollVOs(
			Collection<MailInConsignment> mailInConsignmentcolls) {
		this.mailInConsignmentcolls = mailInConsignmentcolls;
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
	public int getTotalSacks() {
		return totalSacks;
	}
	public void setTotalSacks(int totalSacks) {
		this.totalSacks = totalSacks;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	public PageResult<MailInConsignment> getMailInConsignmentPage() {
		return mailInConsignmentPage;
	}
	public void setMailInConsignmentPage(
			PageResult<MailInConsignment> mailInConsignmentPage) {
		this.mailInConsignmentPage = mailInConsignmentPage;
	}
	public void setRoutingInConsignments(
			Collection<RoutingInConsignment> routingInConsignments) {
		this.routingInConsignments = routingInConsignments;
	}
	public void setMailInConsignmentcolls(
			Collection<MailInConsignment> mailInConsignmentcolls) {
		this.mailInConsignmentcolls = mailInConsignmentcolls;
	}
	public List<RoutingInConsignment> getConsignmentRouting() {
		return consignmentRouting;
	}
	public void setConsignmentRouting(List<RoutingInConsignment> consignmentRouting) {
		this.consignmentRouting = consignmentRouting;
	}
	public PageResult<MailInConsignment> getMailsInConsignmentPage() {
		return MailsInConsignmentPage;
	}
	public void setMailsInConsignmentPage(
			PageResult<MailInConsignment> mailsInConsignmentPage) {
		MailsInConsignmentPage = mailsInConsignmentPage;
	}

	  

}
