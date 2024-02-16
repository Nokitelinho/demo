/*
 * ConsignmentDocumentVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author a-5991
 * 
 */
public class ConsignmentDocumentVO extends AbstractVO {

	private String companyCode;

	private String consignmentNumber;

	private String paCode;

	private int consignmentSequenceNumber;

	private LocalDate consignmentDate;

	private String airportCode; 
	/*
	 * The Flag to indicate the Mail Bag is Scaneed or Not ..
	 * 
	 */
	private boolean isScanned;
	

	/*
	 * CN38 or CN41
	 */
	private String type;

	private String subType;

	

	/*
	 * I - Inbound , O -outbound
	 */
	private String operation;

	/*
	 * Collection<RoutingInConsignmentVO>
	 */
	private Collection<RoutingInConsignmentVO> routingInConsignmentVOs;

	/*
	 * Collection<MailInConsignmentVO>
	 */
	private Page<MailInConsignmentVO> mailInConsignmentVOs;

	private String lastUpdateUser;

	private LocalDate lastUpdateTime;

	private String remarks;

	private int statedBags;

	//private double statedWeight;
	private Measure statedWeight=new Measure(UnitConstants.MAIL_WGT, 0.0);//added by A-7371

	private String operationFlag;
	
	//Added by Deepthi for Report
	/*
	 * Collection<MailInConsignmentVO>
	 */
	private Collection<MailInConsignmentVO> mailInConsignmentcollVOs;
	//Added for Generate CN46
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
	
	private String categoryDescription;
	
	/*
	 * Collection<MailInConsignmentVO>
	 */
	private Collection<MailInConsignmentVO> mailInConsignment;
	
	private String destinationOfficeOfExchange;
	
	private String originOfficeOfExchange;
	
	private int year;
	
	private String dsnNumber;
	
	private String uldNumber;









	private String mailSubClass;
	
	private String mailClass;
	
	//Added as part of CRQ ICRD-103713 by A-5526 starts
	private String operatorOrigin ;
	private String operatorDestination ;
	private String ooeDescription ;
	private String doeDescription ;
	private String consignmentPriority ;
	private String transportationMeans ;
	private String flightDetails ;
	private String flightRoute ;
	private LocalDate firstFlightDepartureDate ;
	private String airlineCode ;
	//Added as part of CRQ ICRD-103713 by A-5526 starts

	//added for ICRD_212235 starts
		
		private int totalSacks;  
		private Measure totalWeight;
	private Measure totalLetterWeight;
	private Measure totalParcelWeight;
	//added for ICRD_212235 ends
	private String currency;
	private double rate;
	private String isUspsPerformed;
	private String mcaNumber;
	private Collection<ConsignmentScreeningVO> consignementScreeningVOs;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private LocalDate securityStatusDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String countryCode;
	private String applicableRegFlag;
	private String consignmentOrigin;
	private String consigmentDest;
	private Map<String, Collection<OneTimeVO>> oneTimes;

	private String airlineName;
	private String flightDates;
	private int subClassLC;
	private int subClassCP;
	private int subClassEMS;
	private String airportOftransShipment;
	private String paName;
	private boolean priorityMalExists;
	private boolean airMalExists;
	private boolean priority;
	private boolean nonPriority;
	private boolean byAir;
	private boolean bySal;
	private boolean bySurface;
	private boolean priorityByAir;
	private boolean nonPrioritySurface;
	private boolean parcels;
	private boolean ems;
	private String tranAirportName;
	private String pouName;
	private int lineCount;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String consignmentIssuerName;
	
	private String shipperUpuCode;
	private String consigneeUpuCode;
	private String originUpuCode;
	private String destinationUpuCode;
	private String consignmentMasterDocumentNumber;
	private Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs;
	

	
	public Collection<ConsignmentScreeningVO> getConsignementScreeningVOs() {
		return consignementScreeningVOs;
	}
	public void setConsignementScreeningVOs(Collection<ConsignmentScreeningVO> consignementScreeningVOs) {
		this.consignementScreeningVOs = consignementScreeningVOs;
	}
	public String getSecurityStatusParty() {
		return securityStatusParty;
	}
	public void setSecurityStatusParty(String securityStatusParty) {
		this.securityStatusParty = securityStatusParty;
	}
	public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	public String getAdditionalSecurityInfo() {
		return additionalSecurityInfo;
	}
	public void setAdditionalSecurityInfo(String additionalSecurityInfo) {
		this.additionalSecurityInfo = additionalSecurityInfo;
	}
	public LocalDate getSecurityStatusDate() {
		return securityStatusDate;
	}
	public void setSecurityStatusDate(LocalDate securityStatusDate) {
		this.securityStatusDate = securityStatusDate;
	}
	public String getApplicableRegTransportDirection() {
		return applicableRegTransportDirection;
	}
	public void setApplicableRegTransportDirection(String applicableRegTransportDirection) {
		this.applicableRegTransportDirection = applicableRegTransportDirection;
	}
	public String getApplicableRegBorderAgencyAuthority() {
		return applicableRegBorderAgencyAuthority;
	}
	public void setApplicableRegBorderAgencyAuthority(String applicableRegBorderAgencyAuthority) {
		this.applicableRegBorderAgencyAuthority = applicableRegBorderAgencyAuthority;
	}
	public String getApplicableRegReferenceID() {
		return applicableRegReferenceID;
	}
	public void setApplicableRegReferenceID(String applicableRegReferenceID) {
		this.applicableRegReferenceID = applicableRegReferenceID;
	}
	/**
	 * 
	 * @return operatorOrigin
	 */
	public String getOperatorOrigin() {
		return operatorOrigin;
	}
/**
 * 
 * @param operatorOrigin
 */
	public void setOperatorOrigin(String operatorOrigin) {
		this.operatorOrigin = operatorOrigin;
	}
/**
 * 
 * @return operatorDestination
 */
	public String getOperatorDestination() {
		return operatorDestination;
	}
/**
 * 
 * @param operatorDestination
 */
	public void setOperatorDestination(String operatorDestination) {
		this.operatorDestination = operatorDestination;
	}
/**
 * 
 * @return OOEDescription
 */
	public String getOoeDescription() {
		return ooeDescription;
	}
	
/**
 * 
 * @param oOEDescription
 */
	public void setOoeDescription(String ooeDescription) {
		this.ooeDescription = ooeDescription;
	}
	/**
	 * 
	 * @return firstFlightDepartureDate
	 */
	public LocalDate getFirstFlightDepartureDate() {
		return firstFlightDepartureDate;
	}
/**
 * 
 * @param firstFlightDepartureDate
 */
	public void setFirstFlightDepartureDate(LocalDate firstFlightDepartureDate) {
		this.firstFlightDepartureDate = firstFlightDepartureDate;
	}
	
	/**
	 * 
	 * @return DOEDescription
	 */
	public String getDoeDescription() {
		return doeDescription;
	}
/**
 * 
 * @param dOEDescription
 */

	public void setDoeDescription(String doeDescription) {
		this.doeDescription = doeDescription;
	}
/**
 * 
 * @return consignmentPriority
 */
	public String getConsignmentPriority() {
		return consignmentPriority;
	}
/**
 * 
 * @param consignmentPriority
 */
	public void setConsignmentPriority(String consignmentPriority) {
		this.consignmentPriority = consignmentPriority;
	}
/**
 * 
 * @return transportationMeans
 */
	public String getTransportationMeans() {
		return transportationMeans;
	}
/**
 * 
 * @param transportationMeans
 */
	public void setTransportationMeans(String transportationMeans) {
		this.transportationMeans = transportationMeans;
	}
/**
 * 
 * @return flightDetails
 */
	public String getFlightDetails() {
		return flightDetails;
	}
/**
 * 
 * @param flightDetails
 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}
/**
 * 
 * @return flightRoute
 */
	public String getFlightRoute() {
		return flightRoute;
	}
/**
 * 
 * @param flightRoute
 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}

	
/**
 * 
 * @return airlineCode
 */
	public String getAirlineCode() {
		return airlineCode;
	}
/**
 * 
 * @param airlineCode
 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	private Collection<RoutingInConsignmentVO> acceptanceInfo;
	
	private Collection<RoutingInConsignmentVO> routingInfo;
	
	
	
	
	
	

	public String getMailSubClass() {
		return mailSubClass;
	}

	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public String getDsnNumber() {
		return dsnNumber;
	}

	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	public String getIsUspsPerformed() {
		return isUspsPerformed;
	}
	public void setIsUspsPerformed(String isUspsPerformed) {
		this.isUspsPerformed = isUspsPerformed;
	}
	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the consignmentDate.
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate
	 *            The consignmentDate to set.
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return Returns the consignmentNumber.
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSequenceNumber
	 *            The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the operation.
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            The operation to set.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the statedBags.
	 */
	public int getStatedBags() {
		return statedBags;
	}

	/**
	 * @param statedBags
	 *            The statedBags to set.
	 */
	public void setStatedBags(int statedBags) {
		this.statedBags = statedBags;
	}

	/**
	 * @return Returns the statedWeight.
	 */
	/*public double getStatedWeight() {
		return statedWeight;
	}

	*//**
	 * @param statedWeight
	 *            The statedWeight to set.
	 *//*
	public void setStatedWeight(double statedWeight) {
		this.statedWeight = statedWeight;
	}
*/
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
/**
 * 
 * @return statedWeight
 */
	public Measure getStatedWeight() {
		return statedWeight;
	}
	/**
	 * 
	 * @param statedWeight
	 */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the mailInConsignmentVOs
	 */
	public Page<MailInConsignmentVO> getMailInConsignmentVOs() {
		return mailInConsignmentVOs;
	}

	/**
	 * @param mailInConsignmentVOs the mailInConsignmentVOs to set
	 */
	public void setMailInConsignmentVOs(
			Page<MailInConsignmentVO> mailInConsignmentVOs) {
		this.mailInConsignmentVOs = mailInConsignmentVOs;
	}

	/**
	 * @return Returns the routingInConsignmentVOs.
	 */
	public Collection<RoutingInConsignmentVO> getRoutingInConsignmentVOs() {
		return routingInConsignmentVOs;
	}

	/**
	 * @param routingInConsignmentVOs
	 *            The routingInConsignmentVOs to set.
	 */
	public void setRoutingInConsignmentVOs(
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
		this.routingInConsignmentVOs = routingInConsignmentVOs;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public boolean isScanned() {
		return isScanned;
	}

	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	/**
	 * @return the mailInConsignmentcollVOs
	 */
	public Collection<MailInConsignmentVO> getMailInConsignmentcollVOs() {
		return mailInConsignmentcollVOs;
	}

	/**
	 * @param mailInConsignmentcollVOs the mailInConsignmentcollVOs to set
	 */
	public void setMailInConsignmentcollVOs(
			Collection<MailInConsignmentVO> mailInConsignmentcollVOs) {
		this.mailInConsignmentcollVOs = mailInConsignmentcollVOs;
	}

	/**
	 * @return Returns the dateOfDept.
	 */
	public String getDateOfDept() {
		return dateOfDept;
	}

	/**
	 * @param dateOfDept The dateOfDept to set.
	 */
	public void setDateOfDept(String dateOfDept) {
		this.dateOfDept = dateOfDept;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public String getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate The despatchDate to set.
	 */
	public void setDespatchDate(String despatchDate) {
		this.despatchDate = despatchDate;
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
	 * @return Returns the flight.
	 */
	public String getFlight() {
		return flight;
	}

	/**
	 * @param flight The flight to set.
	 */
	public void setFlight(String flight) {
		this.flight = flight;
	}

	/**
	 * @return Returns the fltDate.
	 */
	public String getFltDate() {
		return fltDate;
	}

	/**
	 * @param fltDate The fltDate to set.
	 */
	public void setFltDate(String fltDate) {
		this.fltDate = fltDate;
	}

	/**
	 * @return Returns the mailInConsignment.
	 */
	public Collection<MailInConsignmentVO> getMailInConsignment() {
		return mailInConsignment;
	}

	/**
	 * @param mailInConsignment The mailInConsignment to set.
	 */
	public void setMailInConsignment(
			Collection<MailInConsignmentVO> mailInConsignment) {
		this.mailInConsignment = mailInConsignment;
	}

	/**
	 * @return Returns the orgin.
	 */
	public String getOrgin() {
		return orgin;
	}

	/**
	 * @param orgin The orgin to set.
	 */
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	/**
	 * @return Returns the orginAirport.
	 */
	public String getOrginAirport() {
		return orginAirport;
	}

	/**
	 * @param orginAirport The orginAirport to set.
	 */
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}

	/**
	 * @return Returns the orginCity.
	 */
	public String getOrginCity() {
		return orginCity;
	}

	/**
	 * @param orginCity The orginCity to set.
	 */
	public void setOrginCity(String orginCity) {
		this.orginCity = orginCity;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the subsequentFlight.
	 */
	public ArrayList<String> getSubsequentFlight() {
		return subsequentFlight;
	}

	/**
	 * @param subsequentFlight The subsequentFlight to set.
	 */
	public void setSubsequentFlight(ArrayList<String> subsequentFlight) {
		this.subsequentFlight = subsequentFlight;
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
	 * @return Returns the destPort.
	 */
	public String getDestPort() {
		return destPort;
	}

	/**
	 * @param destPort The destPort to set.
	 */
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	/**
	 * @return Returns the reportType.
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType The reportType to set.
	 */
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

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public Collection<RoutingInConsignmentVO> getAcceptanceInfo() {
		return acceptanceInfo;
	}

	public void setAcceptanceInfo(Collection<RoutingInConsignmentVO> acceptanceInfo) {
		this.acceptanceInfo = acceptanceInfo;
	}

	public Collection<RoutingInConsignmentVO> getRoutingInfo() {
		return routingInfo;
	}

	public void setRoutingInfo(Collection<RoutingInConsignmentVO> routingInfo) {
		this.routingInfo = routingInfo;
	}
	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	/**
	 * @return the totalSacks
	 */
	public int getTotalSacks() {
		return totalSacks;
	}
	/**
	 * @param totalSacks the totalSacks to set
	 */
	public void setTotalSacks(int totalSacks) {
		this.totalSacks = totalSacks;
	}
	/**
	 * @return the totalWeight
	 */
	public Measure getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Measure getTotalLetterWeight() {
		return totalLetterWeight;
	}
	public void setTotalLetterWeight(Measure totalLetterWeight) {
		this.totalLetterWeight = totalLetterWeight;
	}
	public Measure getTotalParcelWeight() {
		return totalParcelWeight;
	}
	public void setTotalParcelWeight(Measure totalParcelWeight) {
		this.totalParcelWeight = totalParcelWeight;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the mcaNumber
	 */
	public String getMcaNumber() {
		return mcaNumber;
	}
	/**
	 * @param mcaNumber the mcaNumber to set
	 */
	public void setMcaNumber(String mcaNumber) {
		this.mcaNumber = mcaNumber;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getApplicableRegFlag() {
		return applicableRegFlag;
	}
	public void setApplicableRegFlag(String applicableRegFlag) {
		this.applicableRegFlag = applicableRegFlag;
	}
	public String getConsignmentOrigin() {
		return consignmentOrigin;
	}
	public void setConsignmentOrigin(String consignmentOrigin) {
		this.consignmentOrigin = consignmentOrigin;
	}
	public String getConsigmentDest() {
		return consigmentDest;
	}
	public void setConsigmentDest(String consigmentDest) {
		this.consigmentDest = consigmentDest;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public Map<String, Collection<OneTimeVO>> getOneTimes() {
		return oneTimes;
	}
	public void setOneTimes(Map<String, Collection<OneTimeVO>> oneTimes) {
		this.oneTimes = oneTimes;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getFlightDates() {
		return flightDates;
	}
	public void setFlightDates(String flightDates) {
		this.flightDates = flightDates;
	}
	public int getSubClassLC() {
		return subClassLC;
	}
	public void setSubClassLC(int subClassLC) {
		this.subClassLC = subClassLC;
	}
	public int getSubClassCP() {
		return subClassCP;
	}
	public void setSubClassCP(int subClassCP) {
		this.subClassCP = subClassCP;
	}
	public int getSubClassEMS() {
		return subClassEMS;
	}
	public void setSubClassEMS(int subClassEMS) {
		this.subClassEMS = subClassEMS;
	}
	public String getAirportOftransShipment() {
		return airportOftransShipment;
	}
	public void setAirportOftransShipment(String airportOftransShipment) {
		this.airportOftransShipment = airportOftransShipment;
	}
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
	/**
	 * 	Getter for priorityMalExists 
	 *	Added by : A-5219 on 30-Nov-2020
	 * 	Used for :
	 */
	public boolean isPriorityMalExists() {
		return priorityMalExists;
	}
	/**
	 *  @param priorityMalExists the priorityMalExists to set
	 * 	Setter for priorityMalExists 
	 *	Added by : A-5219 on 30-Nov-2020
	 * 	Used for :
	 */
	public void setPriorityMalExists(boolean priorityMalExists) {
		this.priorityMalExists = priorityMalExists;
	}
	/**
	 * 	Getter for airMalExists 
	 *	Added by : A-5219 on 30-Nov-2020
	 * 	Used for :
	 */
	public boolean isAirMalExists() {
		return airMalExists;
	}
	/**
	 *  @param airMalExists the airMalExists to set
	 * 	Setter for airMalExists 
	 *	Added by : A-5219 on 30-Nov-2020
	 * 	Used for :
	 */
	public void setAirMalExists(boolean airMalExists) {
		this.airMalExists = airMalExists;
	}
	/**
	 * 	Getter for priority 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isPriority() {
		return priority;
	}
	/**
	 *  @param priority the priority to set
	 * 	Setter for priority 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setPriority(boolean priority) {
		this.priority = priority;
	}
	/**
	 * 	Getter for nonPriority 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isNonPriority() {
		return nonPriority;
	}
	/**
	 *  @param nonPriority the nonPriority to set
	 * 	Setter for nonPriority 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setNonPriority(boolean nonPriority) {
		this.nonPriority = nonPriority;
	}
	/**
	 * 	Getter for byAir 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isByAir() {
		return byAir;
	}
	/**
	 *  @param byAir the byAir to set
	 * 	Setter for byAir 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setByAir(boolean byAir) {
		this.byAir = byAir;
	}
	/**
	 * 	Getter for bySal 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isBySal() {
		return bySal;
	}
	/**
	 *  @param bySal the bySal to set
	 * 	Setter for bySal 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setBySal(boolean bySal) {
		this.bySal = bySal;
	}
	/**
	 * 	Getter for bySurface 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isBySurface() {
		return bySurface;
	}
	/**
	 *  @param bySurface the bySurface to set
	 * 	Setter for bySurface 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setBySurface(boolean bySurface) {
		this.bySurface = bySurface;
	}
	/**
	 * 	Getter for priorityByAir 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isPriorityByAir() {
		return priorityByAir;
	}
	/**
	 *  @param priorityByAir the priorityByAir to set
	 * 	Setter for priorityByAir 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setPriorityByAir(boolean priorityByAir) {
		this.priorityByAir = priorityByAir;
	}
	/**
	 * 	Getter for nonPrioritySurface 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isNonPrioritySurface() {
		return nonPrioritySurface;
	}
	/**
	 *  @param nonPrioritySurface the nonPrioritySurface to set
	 * 	Setter for nonPrioritySurface 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setNonPrioritySurface(boolean nonPrioritySurface) {
		this.nonPrioritySurface = nonPrioritySurface;
	}
	/**
	 * 	Getter for parcels 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isParcels() {
		return parcels;
	}
	/**
	 *  @param parcels the parcels to set
	 * 	Setter for parcels 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setParcels(boolean parcels) {
		this.parcels = parcels;
	}
	/**
	 * 	Getter for ems 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public boolean isEms() {
		return ems;
	}
	/**
	 *  @param ems the ems to set
	 * 	Setter for ems 
	 *	Added by : A-5219 on 03-Dec-2020
	 * 	Used for :
	 */
	public void setEms(boolean ems) {
		this.ems = ems;
	}

	/**
	 * 	Getter for tranAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public String getTranAirportName() {
		return tranAirportName;
	}
	/**
	 *  @param tranAirportName the tranAirportName to set
	 * 	Setter for tranAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public void setTranAirportName(String tranAirportName) {
		this.tranAirportName = tranAirportName;
	}
	/**
	 * 	Getter for pouName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public String getPouName() {
		return pouName;
	}
	/**
	 *  @param pouName the pouName to set
	 * 	Setter for pouName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public void setPouName(String pouName) {
		this.pouName = pouName;
	}
	

	public int getLineCount() {
		return lineCount;
	}
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	public Collection<ConsignmentDocumentVO> getExistingConsignmentDocumentVOs() {
		return existingConsignmentDocumentVOs;
	}
	public void setExistingConsignmentDocumentVOs(Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs) {
		this.existingConsignmentDocumentVOs = existingConsignmentDocumentVOs;
	}	
	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : A-9998 on 23-Feb-2022
	 * 	Used for :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	private String securityReasonCode;
	private String agentType;
	private String agentID;
	private String expiryDate;
	private String isoCountryCode;
	private String csgIssuerName;
	private String mstAddionalSecurityInfo;
	private String source;

	public String getSecurityReasonCode() {
		return securityReasonCode;
	}
	public void setSecurityReasonCode(String securityReasonCode) {
		this.securityReasonCode = securityReasonCode;
	}
	public String getMstAddionalSecurityInfo() {
		return mstAddionalSecurityInfo;
	}
	public void setMstAddionalSecurityInfo(String mstAddionalSecurityInfo) {
		this.mstAddionalSecurityInfo = mstAddionalSecurityInfo;
	}
	public String getCsgIssuerName() {
		return csgIssuerName;
	}
	public void setCsgIssuerName(String csgIssuerName) {
		this.csgIssuerName = csgIssuerName;
	}
	public String getAgentType() {
		return agentType;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	 public String getConsignmentIssuerName() {
		return consignmentIssuerName;
	}
	public void setConsignmentIssuerName(String consignmentIssuerName) {
		this.consignmentIssuerName = consignmentIssuerName;
	}
	public String getShipperUpuCode() {
		return shipperUpuCode;
	}
	public void setShipperUpuCode(String shipperUpuCode) {
		this.shipperUpuCode = shipperUpuCode;
	}
	public String getConsigneeUpuCode() {
		return consigneeUpuCode;
	}
	public void setConsigneeUpuCode(String consigneeUpuCode) {
		this.consigneeUpuCode = consigneeUpuCode;
	}
	public String getOriginUpuCode() {
		return originUpuCode;
	}
	public void setOriginUpuCode(String originUpuCode) {
		this.originUpuCode = originUpuCode;
	}
	public String getDestinationUpuCode() {
		return destinationUpuCode;
	}
	public void setDestinationUpuCode(String destinationUpuCode) {
		this.destinationUpuCode = destinationUpuCode;
	}
	public String getConsignmentMasterDocumentNumber() {
		return consignmentMasterDocumentNumber;
	}
	public void setConsignmentMasterDocumentNumber(String consignmentMasterDocumentNumber) {
		this.consignmentMasterDocumentNumber = consignmentMasterDocumentNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	
}
