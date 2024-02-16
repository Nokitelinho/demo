/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails.java Created on	:	08-Jun-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-2257 :
 * 08-Jun-2018 : Draft
 */
public class ContainerDetails {

	private String containerNumber;
	private String type;
	private String remarks;
	private String paBuiltFlag;
	private String operationFlag;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int legSerialNumber;
	private String companyCode;
	private String assignedPort;
	private String assignmentFlag;
	private String lastUpdateUser;
	private String acceptanceFlag;
	private boolean isReassign;
	private String carrierCode;
	private boolean isFlightClosureCheckNeeded;
	private String pou;
	private String pol;
	private String offloadFlag;
	private String ownAirlineCode;
	private int ownAirlineId;
	private String arrivedStatus;
	private String transferFlag;
	private boolean isPreassignNeeded;
	private String paBuiltOpenedFlag;
	private String containerSealNumber;
	private String fromCarrier;
	private String fromFltNum;
	private String flightStatus;
	private String deliveredStatus;
	private String containerJnyID;
	private String intact;
	private String transitFlag;
	private String releasedFlag;
	private String scannedDate;
	private String mailSource;
	private String source;
	private String finalDestination;
	private int bags;
	private double weight;
	
	//Added by A-7929 as part of ICRD-269984 starts....
    private String assignedOn;
    private String assignedBy;
    private String onwadRoute;
    private String flightDate;
    private String airportCode;
    private String destination;
    private double actualWeight;
    private Measure actualWeightMeasure;
    private LocalDate lastUpdateTime;
    private LocalDate uldLastUpdateTime;	
//Added by A-8176 for mail outbound
    private String acceptedFlag; 
	private int receivedBags;
	private Measure receivedWeight;
	private String containerOperationFlag;
	private boolean isReassignFlag;
	private boolean isMailUpdateFlag;
	private Measure receptacleWeight;
	private int receptacleCount;
	private String containerJnyId;
	private String paCode;
	private boolean isPreassignFlag;
	private String assignedUser;
	private String onwardFlights;
	private String bellyCarditId;
	private String location;
	private int totalBags;
	private Measure totalWeight;
	private LocalDate assignmentDate;
	private String wareHouse;
	private String locationCode;
	private String transferFromCarrier;
	private Collection<Mailbag> mailDetails;
	private String Containergroup;
	private int containercount;
	private int mailbagcount;
	private Measure mailbagweight;
	private PageResult<Mailbag> mailbagpagelist;
	private PageResult<DespatchDetails> dsnviewpagelist;
	private List<Mailbag> mailBagDetailsCollection;
	private List<DespatchDetails> desptachDetailsCollection;
	
	//Added by A-8672 for ICRD-269973 starts here
	private Collection<OnwardRoutingVO> onwardRoutings;
	private Collection<String> offloadedInfo;
	private int offloadCount;
	private String flightDetail;
	private String actualWeightUnit;
	private boolean isActualWeightUpdated;
	private String minReqDelveryTime;
	
	
	 private boolean uldTobarrow;
	 private boolean barrowToUld;
	 
	 private long assignedOnInMilliSec;
	 
	 private String containerPureTransfer;//Added by A-8464 for ICRD-328502
	 private boolean containerDestChanged;//added by A-8353 for ICRD-328502
	 
	 private int noOfDaysInCurrentLoc;
	 private String subclassGroup;
	 private boolean fromContainerTab; 
	 private String uldFulIndFlag;
	 private String transactionCode;
	 private String plannedFlightAndDate;
	 private double expectedOrProvisionalCharge;
	 private String position;
	 //added by 203168
	 private long uldReferenceNo;
	 private String expClsFlg;
	 private String hbaMarked;
	 private double  provosionalCharge;
	   private String baseCurrency;
	   private String rateAvailforallMailbags;
	   private String acceptedPort;
	   	private String actWgtSta;

	public double getProvosionalCharge() {
		return provosionalCharge;
	}
	public void setProvosionalCharge(double provosionalCharge) {
		this.provosionalCharge = provosionalCharge;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getRateAvailforallMailbags() {
		return rateAvailforallMailbags;
	}
	public void setRateAvailforallMailbags(String rateAvailforallMailbags) {
		this.rateAvailforallMailbags = rateAvailforallMailbags;
	}
	public long getUldReferenceNo() {
		return uldReferenceNo;
	}
	public void setUldReferenceNo(long uldReferenceNo) {
		this.uldReferenceNo = uldReferenceNo;
	}
	public double getExpectedOrProvisionalCharge() {
		return expectedOrProvisionalCharge;
	}

	public void setExpectedOrProvisionalCharge(double expectedOrProvisionalCharge) {
		this.expectedOrProvisionalCharge = expectedOrProvisionalCharge;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Collection<OnwardRoutingVO> getOnwardRoutings() {
		return onwardRoutings;
	}

	public void setOnwardRoutings(Collection<OnwardRoutingVO> onwardRoutings) {
		this.onwardRoutings = onwardRoutings;
	}
	//Added by A-8672 for ICRD-269973 ends here

	private String contentId;   //Added by A-7929 as part of ICRD-219699
	
    public LocalDate getUldLastUpdateTime() {
		return uldLastUpdateTime;
	}

	public void setUldLastUpdateTime(LocalDate uldLastUpdateTime) {
		this.uldLastUpdateTime = uldLastUpdateTime;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getOnwadRoute() {
		return onwadRoute;
	}

	public void setOnwadRoute(String onwadRoute) {
		this.onwadRoute = onwadRoute;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	//Added by A-7929 as part of ICRD-269984 ends....   
	
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getAssignedPort() {
		return assignedPort;
	}

	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}

	public String getAssignmentFlag() {
		return assignmentFlag;
	}

	public void setAssignmentFlag(String assignmentFlag) {
		this.assignmentFlag = assignmentFlag;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	public boolean isReassign() {
		return isReassign;
	}

	public void setReassign(boolean isReassign) {
		this.isReassign = isReassign;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public boolean isFlightClosureCheckNeeded() {
		return isFlightClosureCheckNeeded;
	}

	public void setFlightClosureCheckNeeded(boolean isFlightClosureCheckNeeded) {
		this.isFlightClosureCheckNeeded = isFlightClosureCheckNeeded;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getOffloadFlag() {
		return offloadFlag;
	}

	public void setOffloadFlag(String offloadFlag) {
		this.offloadFlag = offloadFlag;
	}

	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}

	public int getOwnAirlineId() {
		return ownAirlineId;
	}

	public void setOwnAirlineId(int ownAirlineId) {
		this.ownAirlineId = ownAirlineId;
	}

	public String getArrivedStatus() {
		return arrivedStatus;
	}

	public void setArrivedStatus(String arrivedStatus) {
		this.arrivedStatus = arrivedStatus;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public boolean isPreassignNeeded() {
		return isPreassignNeeded;
	}

	public void setPreassignNeeded(boolean isPreassignNeeded) {
		this.isPreassignNeeded = isPreassignNeeded;
	}

	public String getPaBuiltOpenedFlag() {
		return paBuiltOpenedFlag;
	}

	public void setPaBuiltOpenedFlag(String paBuiltOpenedFlag) {
		this.paBuiltOpenedFlag = paBuiltOpenedFlag;
	}

	public String getContainerSealNumber() {
		return containerSealNumber;
	}

	public void setContainerSealNumber(String containerSealNumber) {
		this.containerSealNumber = containerSealNumber;
	}

	public String getFromCarrier() {
		return fromCarrier;
	}

	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}

	public String getFromFltNum() {
		return fromFltNum;
	}

	public void setFromFltNum(String fromFltNum) {
		this.fromFltNum = fromFltNum;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	public String getDeliveredStatus() {
		return deliveredStatus;
	}

	public void setDeliveredStatus(String deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
	}

	public String getContainerJnyID() {
		return containerJnyID;
	}

	public void setContainerJnyID(String containerJnyID) {
		this.containerJnyID = containerJnyID;
	}

	public String getIntact() {
		return intact;
	}

	public void setIntact(String intact) {
		this.intact = intact;
	}

	public String getTransitFlag() {
		return transitFlag;
	}

	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	public String getReleasedFlag() {
		return releasedFlag;
	}

	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}

	public String getScannedDate() {
		return scannedDate;
	}

	public void setScannedDate(String scannedDate) {
		this.scannedDate = scannedDate;
	}

	public String getMailSource() {
		return mailSource;
	}

	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getAcceptedFlag() {
		return acceptedFlag;
	}
	public void setAcceptedFlag(String acceptedFlag) {
		this.acceptedFlag = acceptedFlag;
	}
	public int getReceivedBags() {
		return receivedBags;
	}
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
	public String getContainerOperationFlag() {
		return containerOperationFlag;
	}
	public void setContainerOperationFlag(String containerOperationFlag) {
		this.containerOperationFlag = containerOperationFlag;
	}
	public boolean isReassignFlag() {
		return isReassignFlag;
	}
	public void setReassignFlag(boolean isReassignFlag) {
		this.isReassignFlag = isReassignFlag;
	}
	public boolean isMailUpdateFlag() {
		return isMailUpdateFlag;
	}
	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}
	public Measure getReceptacleWeight() {
		return receptacleWeight;
	}
	public void setReceptacleWeight(Measure receptacleWeight) {
		this.receptacleWeight = receptacleWeight;
	}
	public int getReceptacleCount() {
		return receptacleCount;
	}
	public void setReceptacleCount(int receptacleCount) {
		this.receptacleCount = receptacleCount;
	}
	public String getContainerJnyId() {
		return containerJnyId;
	}
	public void setContainerJnyId(String containerJnyId) {
		this.containerJnyId = containerJnyId;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public boolean isPreassignFlag() {
		return isPreassignFlag;
	}
	public void setPreassignFlag(boolean isPreassignFlag) {
		this.isPreassignFlag = isPreassignFlag;
	}
	public String getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	public String getOnwardFlights() {
		return onwardFlights;
	}
	public void setOnwardFlights(String onwardFlights) {
		this.onwardFlights = onwardFlights;
	}
	public String getBellyCarditId() {
		return bellyCarditId;
	}
	public void setBellyCarditId(String bellyCarditId) {
		this.bellyCarditId = bellyCarditId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTotalBags() {
		return totalBags;
	}
	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}
	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}
	public Collection<Mailbag> getMailDetails() {
		return mailDetails;
	}
	public void setMailDetails(Collection<Mailbag> mailDetails) {
		this.mailDetails = mailDetails;
	}
	public String getContainergroup() {
		return Containergroup;
	}
	public void setContainergroup(String containergroup) {
		Containergroup = containergroup;
	}
	public int getContainercount() {
		return containercount;
	}
	public void setContainercount(int containercount) {
		this.containercount = containercount;
	}
	public int getMailbagcount() {
		return mailbagcount;
	}
	public void setMailbagcount(int mailbagcount) {
		this.mailbagcount = mailbagcount;
	}
	public Measure getMailbagweight() {
		return mailbagweight;
	}
	public void setMailbagweight(Measure mailbagweight) {
		this.mailbagweight = mailbagweight;
	}
	public PageResult<Mailbag> getMailbagpagelist() {
		return mailbagpagelist;
	}
	public void setMailbagpagelist(PageResult<Mailbag> mailbagpagelist) {
		this.mailbagpagelist = mailbagpagelist;
	}
	
	public PageResult<DespatchDetails> getDsnviewpagelist() {
		return dsnviewpagelist;
	}

	public void setDsnviewpagelist(PageResult<DespatchDetails> dsnviewpagelist) {
		this.dsnviewpagelist = dsnviewpagelist;
	}
	public List<Mailbag> getMailBagDetailsCollection() {
		return mailBagDetailsCollection;
	}
	public void setMailBagDetailsCollection(
			List<Mailbag> mailBagDetailsCollection) {
		this.mailBagDetailsCollection = mailBagDetailsCollection;
	}
	public List<DespatchDetails> getDesptachDetailsCollection() {
		return desptachDetailsCollection;
	}
	public void setDesptachDetailsCollection(
			List<DespatchDetails> desptachDetailsCollection) {
		this.desptachDetailsCollection = desptachDetailsCollection;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Collection<String> getOffloadedInfo() {
		return offloadedInfo;
	}

	public void setOffloadedInfo(Collection<String> offloadedInfo) {
		this.offloadedInfo = offloadedInfo;
	}

	public int getOffloadCount() {
		return offloadCount;
	}

	public void setOffloadCount(int offloadCount) {
		this.offloadCount = offloadCount;
	}

	public String getFlightDetail() {
		return flightDetail;
	}

	public void setFlightDetail(String flightDetail) {
		this.flightDetail = flightDetail;
	}

	public String getActualWeightUnit() {
		return actualWeightUnit;
	}

	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	/**
	 * @return the actualWeightMeasure
	 */
	public Measure getActualWeightMeasure() {
		return actualWeightMeasure;
	}
	/**
	 * @param actualWeightMeasure the actualWeightMeasure to set
	 */
	public void setActualWeightMeasure(Measure actualWeightMeasure) {
		this.actualWeightMeasure = actualWeightMeasure;
	}

	public boolean isActualWeightUpdated() {
		return isActualWeightUpdated;
	}

	public void setActualWeightUpdated(boolean isActualWeightUpdated) {
		this.isActualWeightUpdated = isActualWeightUpdated;
	}
	public String getMinReqDelveryTime() {
		return minReqDelveryTime;
	}
	public void setMinReqDelveryTime(String minReqDelveryTime) {
		this.minReqDelveryTime = minReqDelveryTime;
	}
	public boolean isUldTobarrow() {
		return uldTobarrow;
	}
	public void setUldTobarrow(boolean uldTobarrow) {
		this.uldTobarrow = uldTobarrow;
	}

	public boolean isBarrowToUld() {
		return barrowToUld;
	}

	public void setBarrowToUld(boolean barrowToUld) {
		this.barrowToUld = barrowToUld;
	}

	public long getAssignedOnInMilliSec() {
		return assignedOnInMilliSec;
	}

	public void setAssignedOnInMilliSec(long assignedOnInMilliSec) {
		this.assignedOnInMilliSec = assignedOnInMilliSec;
	}

	public String getContainerPureTransfer() {
		return containerPureTransfer;
	}

	public void setContainerPureTransfer(String containerPureTransfer) {
		this.containerPureTransfer = containerPureTransfer;
	}
	public boolean isContainerDestChanged() {
		return containerDestChanged;
	}
	public void setContainerDestChanged(boolean containerDestChanged) {
		this.containerDestChanged = containerDestChanged;
	}
	public int getNoOfDaysInCurrentLoc() {
		return noOfDaysInCurrentLoc;
	}
	public void setNoOfDaysInCurrentLoc(int noOfDaysInCurrentLoc) {
		this.noOfDaysInCurrentLoc = noOfDaysInCurrentLoc;
	}
	public String getSubclassGroup() {
		return subclassGroup;
	}
	public void setSubclassGroup(String subclassGroup) {
		this.subclassGroup = subclassGroup;
	}

	public boolean isFromContainerTab() {
		return fromContainerTab;
	}

	public void setFromContainerTab(boolean fromContainerTab) {
		this.fromContainerTab = fromContainerTab;
	}

    public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}

	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}
	public String getTransactionCode() { 
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getPlannedFlightAndDate() {
		return plannedFlightAndDate;
	}
	public void setPlannedFlightAndDate(String plannedFlightAndDate) {
		this.plannedFlightAndDate = plannedFlightAndDate;
	}
	public String getExpClsFlg() {
		return expClsFlg;
	}
	public void setExpClsflg(String expClsFlg) {
		this.expClsFlg = expClsFlg;
	}
	public String getHbaMarked() {
		return hbaMarked;
	}
	public void setHbaMarked(String hbaMarked) {
		this.hbaMarked = hbaMarked;
	}
	public String getAcceptedPort() {
		return acceptedPort;
	}
	public void setAcceptedPort(String acceptedPort) {
		this.acceptedPort = acceptedPort;
	}
		   
	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
}