package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class FlightSegmentCapacitySummary {

	/**
	 * @param args
	 */
	private boolean overUtilised;
	  private String processingStatus;
	  private String companyCode;
	  private int flightCarrierIdentifier;
	  private String flightNumber;
	  private long flightSequenceNumber;
	  private String segmentOrigin;
	  private String segmentDestination;
	  private String allotmentId;
	  private String allotmentType;
	  private String allotmentStation;
	  private String allotmentCategory;
	  private String allotmentReference;
	  private String dutyUser;
	  private String oldAllotmentId;
	  private String stationAllotmentId;
	  private String toAllotmentId;
	  private boolean isSegmentCapacityDefined;
	  private String status;
	  private LocalDate actualDepDate;
	  private LocalDate actualArvDate;
	  private String actualDepSchedule;
	  private String actualArvSchedule;
	  private int serviceRecoveryCnt;
	  private String flightGroupName;
	  private String customerName;
	  private int documentOwnerId;
	  private String shipmentPrefix;
	  private String flightStatus;
	  private String cancellationReasonCode;
	  private LocalDate cancelledDate;
	  private String cancelRemarks;
	  private double providedSalesLowerDeckOne;
	  private double providedSalesLowerDeckTwo;
	  private double providedSalesUpperDeckOne;
	  private double providedSalesUpperDeckTwo;
	  private double providedHandlingLowerDeckOne;
	  private double providedHandlingLowerDeckTwo;
	  private double providedHandlingUpperDeckOne;
	  private double providedHandlingUpperDeckTwo;
	  private String utilizationflagalert;
	  private String malperformanceflag;
	  private String shipmentacceptancestatus;
	  private Measure bookedweight;
	  private Measure bookedvolume;
	  private double bookedUpperDeckOne;
	  private double bookedUpperDeckTwo;
	  private double bookedLowerDeckOne;
	  private double bookedLowerDeckTwo;
	  private HashMap<String, String> flightErrors;
	  private String transportOrigin;
	  private String transportDestination;
	  private String categoryCode;
	  private LocalDate scheduleDateOfDeparture;
	  private LocalDate scheduleDateOfArrival;
	  private String segmentString;
	  private LocalDate timeOfDeparture;
	  private LocalDate timeOfArrival;
	  private String aircraftType;
	  private String restrictionFlag;
	  private Measure totalWeight;
	  private Measure totalVolume;
	  private double totalUpperDeckOne;
	  private double totalUpperDeckTwo;
	  private double totalLowerDeckOne;
	  private double totalLowerDeckTwo;
	  private String totalUldString;
	  private int confirmedBookingNumber;
	  private Measure confirmedBookingWeight;
	  private Measure confirmedBookingVolume;
	  private double confirmedLowerDeckOne;
	  private double confirmedLowerDeckTwo;
	  private double confirmedUpperDeckOne;
	  private double confirmedUpperDeckTwo;
	  private String confirmedUldString;
	  private double targetedRevenue;
	  private Measure targetedYield;
	  private double revenue;
	  private double revenueInUSD;
	  private Measure yield;
	  private Measure yieldInUSD;
	  private int queuedBookingNumber;
	  private Measure queuedBookingWeight;
	  private Measure queuedBookingVolume;
	  private double queuedLowerDeckOne;
	  private double queuedLowerDeckTwo;
	  private double queuedUpperDeckOne;
	  private double queuedUpperDeckTwo;
	  private String queuedUldString;
	  private String operationalFlag;
	  private boolean isConfirmed;
	  private boolean hasChangedQueueToConfirm;
	  private String flightCarrrierCode;
	  private String scheduleType;
	  private String flightType;
	  private String isDomestic;
	  private LocalDate flightDate;
	  private String flightRoute;
	  private String flightCarrierCode;
	  private int confirmedPieces;
	  private int queuedPieces;
	  private String legStatus;
	  private String oprStatus;
	  private String bkgStatus;
	  private Measure totalAlotmentAvailableWeight;
	  private Measure totalAlotmentAvailableVolume;
	  private double totalAllotmentAvailableLowerDeckOne;
	  private double totalAllotmentAvailableLowerDeckTwo;
	  private double totalAllotmentAvailableUpperDeckOne;
	  private double totalAllotmentAvailableUpperDeckTwo;
	  private String totalAllotmentAvailableUldString;
	  private Measure availableFreesaleWeight;
	  private Measure availableFreesaleVolume;
	  private double availableFreesaleLowerDeckOne;
	  private double availableFreesaleLowerDeckTwo;
	  private double availableFreesaleUpperDeckOne;
	  private double availableFreesaleUpperDeckTwo;
	  private String category;
	  private String commodity;
	  private int segmentSerialNumber;
	  private int insertOrder;
	  private String segmentStatus;
	  private boolean isAllowBookingOnFreesale;
	  private String daysOfOperation;
	  private Measure tolerenceWeight;
	  private Measure freesaleToleranceVolume;
	  private String stationCode;
	  private boolean isOverrideSegmentCapacity;
	  private Measure segmentTotalWeight;
	  private Measure segmentTotalVolume;
	  private Measure overbookingWeight;
	  private Measure overbookingVolume;
	  private Measure segmentOverbookingWeight;
	  private Measure segmentOverbookingVolume;
	  private boolean releaseFlag;
	  private String customerCode;
	  private String globalCustCode;
	  private String operationFlag;
	  private String allotmentIdentifier;
	  private String carrierCode;
	  private int carrierIdentifier;
	  private int airlineIdentifier;
	  private Measure minimumEntryYield;
	  private String allotmentCurrency;
	  private Measure totalHandlingWeight;
	  private Measure totalHandlingVolume;
	  private double totalHandlingUpperDeckOne;
	  private double totalHandlingUpperDeckTwo;
	  private double totalHandlingLowerDeckOne;
	  private double totalHandlingLowerDeckTwo;
	  private Measure providedSalesWeight;
	  private Measure providedSalesVolume;
	  private Measure providedHandlingWeight;
	  private Measure providedHandlingVolume;
	  private Measure guaranteedWeight;
	  private Measure guaranteedVolume;
	  private double guaranteedUpperDeckOne;
	  private double guaranteedUpperDeckTwo;
	  private double guaranteedLowerDeckOne;
	  private double guaranteedLowerDeckTwo;
	  private Measure limitedWeight;
	  private Measure limitedVolume;
	  private double limitedUpperDeckOne;
	  private double limitedUpperDeckTwo;
	  private double limitedLowerDeckOne;
	  private double limitedLowerDeckTwo;
	  private Measure totalAllotmentWeight;
	  private Measure totalAllotmentVolume;
	  private double totalAllotmentLowerDeckOne;
	  private double totalAllotmentLowerDeckTwo;
	  private double totalAllotmentUpperDeckOne;
	  private double totalAllotmentUpperDeckTwo;
	  private Measure allotmentConfirmedWeight;
	  private Measure allotmentConfirmedVolume;
	  private double allotmentConfirmedLowerDeckOne;
	  private double allotmentConfirmedLowerDeckTwo;
	  private double allotmentConfirmedUpperDeckOne;
	  private double allotmentConfirmedUpperDeckTwo;
	  private Measure freesaleConfirmedWeight;
	  private Measure freesaleConfirmedVolume;
	  private double freesaleConfirmedLowerDeckOne;
	  private double freesaleConfirmedLowerDeckTwo;
	  private double freesaleConfirmedUpperDeckOne;
	  private double freesaleConfirmedUpperDeckTwo;
	  private String allotmentFlag;
	  private LocalDate allotmentDate;
	  private String allotmentSubType;
	  private String toStationCode;
	  private String toSegmentOrigin;
	  private String toSegmentDestination;
	  private LocalDate lastUpdateTime;
	  private String lastUpdateUser;
	  private Measure legWeight;
	  private Measure availableWeight;
	  private Measure availableVolume;
	  private Measure legVolume;
	  private double legLowerDeckOne;
	  private double legLowerDeckTwo;
	  private double legUpperDeckOne;
	  private double legUpperDeckTwo;
	  private String legSegment;
	  private LocalDate scheduleTimeOfDepartureAtfirstLeg;
	  private Measure utilisedOverbookingWeight;
	  private Measure utilisedOverbookingVolume;
	  private String tailNumber;
	  private LocalDate scheduleTimeOfDepartureAtFirstLeg;
	  private Measure stationAllotmentTotalWeight;
	  private Measure stationAllotmentTotalVolume;
	  private double stationAllotmentTotalLD1;
	  private double stationAllotmentTotalLD2;
	  private double stationAllotmentTotalUD1;
	  private double stationAllotmentTotalUD2;
	  private Measure stationAllotmentConfirmedWeight;
	  private Measure stationAllotmentConfirmedVolume;
	  private double stationAllotmentConfirmedLD1;
	  private double stationAllotmentConfirmedLD2;
	  private double stationAllotmentConfirmedUD1;
	  private double stationAllotmentConfirmedUD2;
	  private double mailLowerDeckOne;
	  private double mailLowerDeckTwo;
	  private double mailUpperDeckOne;
	  private double mailUpperDeckTwo;
	  private int utilizedBaggage;
	  private int daysBeforeDeparture;
	  private String ubrNumber;
	  private LocalDate txnTime;
	  private Measure utilizedDryIceWeight;
	  private Measure totalDryIceWeight;
	  private Measure aircraftDryIceWeight;
	  private String releaseAllotmentType;
	  private String capacityId;
	  private double paxLoadFactor;
	  private Measure malWeight;
	  private Measure avaialableSalesWeight;
	  private Measure avaialableSalesVolume;
	  private double avaialableSalesLowerDeckOne;
	  private double avaialableSalesLowerDeckTwo;
	  private double avaialableSalesUpperDeckOne;
	  private double avaialableSalesUpperDeckTwo;
	  private Measure avaialableHandlingWeight;
	  private Measure avaialableHandlingVolume;
	  private double avaialableHandlingLowerDeckOne;
	  private double avaialableHandlingLowerDeckTwo;
	  private double avaialableHandlingUpperDeckOne;
	  private double avaialableHandlingUpperDeckTwo;
	  private String sourceSystem;
	  private double availableLowerDeckOne;
	  private double availableLowerDeckTwo;
	  private double availableUpperDeckOne;
	  private double availableUpperDeckTwo;
	  private double uldWeight;
	  private double manifestWeight;
	  private Measure aircraftWeightCapacity;
	  private String flightMileStoneStatus;
	  private boolean isStationGroupAllotment;
	  private Measure totalChargeableWeight;
	  private Collection<FlightSegmentCapacitySummary> allotments;
	  private int legSerialNumber;
	  private String aircraftClassification;
	  private Measure releasedWeight;
	  private Measure releasedVolume;
	  private double releasedUpperDeckOne;
	  private double releasedUpperDeckTwo;
	  private double releasedLowerDeckOne;
	  private double releasedLowerDeckTwo;
	  private Measure totalFreesaleWeight;
	  private Measure totalFreesaleVolume;
	  private Measure TotalUtilised;
	  private Measure mailCapacity;
	  private Measure mailUtised;
	  private Measure cargoCapacity;
	  private Measure cargoUtilised;
	public boolean isOverUtilised() {
		return overUtilised;
	}
	public void setOverUtilised(boolean overUtilised) {
		this.overUtilised = overUtilised;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
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
	public String getSegmentOrigin() {
		return segmentOrigin;
	}
	public void setSegmentOrigin(String segmentOrigin) {
		this.segmentOrigin = segmentOrigin;
	}
	public String getSegmentDestination() {
		return segmentDestination;
	}
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	public String getAllotmentId() {
		return allotmentId;
	}
	public void setAllotmentId(String allotmentId) {
		this.allotmentId = allotmentId;
	}
	public String getAllotmentType() {
		return allotmentType;
	}
	public void setAllotmentType(String allotmentType) {
		this.allotmentType = allotmentType;
	}
	public String getAllotmentStation() {
		return allotmentStation;
	}
	public void setAllotmentStation(String allotmentStation) {
		this.allotmentStation = allotmentStation;
	}
	public String getAllotmentCategory() {
		return allotmentCategory;
	}
	public void setAllotmentCategory(String allotmentCategory) {
		this.allotmentCategory = allotmentCategory;
	}
	public String getAllotmentReference() {
		return allotmentReference;
	}
	public void setAllotmentReference(String allotmentReference) {
		this.allotmentReference = allotmentReference;
	}
	public String getDutyUser() {
		return dutyUser;
	}
	public void setDutyUser(String dutyUser) {
		this.dutyUser = dutyUser;
	}
	public String getOldAllotmentId() {
		return oldAllotmentId;
	}
	public void setOldAllotmentId(String oldAllotmentId) {
		this.oldAllotmentId = oldAllotmentId;
	}
	public String getStationAllotmentId() {
		return stationAllotmentId;
	}
	public void setStationAllotmentId(String stationAllotmentId) {
		this.stationAllotmentId = stationAllotmentId;
	}
	public String getToAllotmentId() {
		return toAllotmentId;
	}
	public void setToAllotmentId(String toAllotmentId) {
		this.toAllotmentId = toAllotmentId;
	}
	public boolean isSegmentCapacityDefined() {
		return isSegmentCapacityDefined;
	}
	public void setSegmentCapacityDefined(boolean isSegmentCapacityDefined) {
		this.isSegmentCapacityDefined = isSegmentCapacityDefined;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getActualDepDate() {
		return actualDepDate;
	}
	public void setActualDepDate(LocalDate actualDepDate) {
		this.actualDepDate = actualDepDate;
	}
	public LocalDate getActualArvDate() {
		return actualArvDate;
	}
	public void setActualArvDate(LocalDate actualArvDate) {
		this.actualArvDate = actualArvDate;
	}
	public String getActualDepSchedule() {
		return actualDepSchedule;
	}
	public void setActualDepSchedule(String actualDepSchedule) {
		this.actualDepSchedule = actualDepSchedule;
	}
	public String getActualArvSchedule() {
		return actualArvSchedule;
	}
	public void setActualArvSchedule(String actualArvSchedule) {
		this.actualArvSchedule = actualArvSchedule;
	}
	public int getServiceRecoveryCnt() {
		return serviceRecoveryCnt;
	}
	public void setServiceRecoveryCnt(int serviceRecoveryCnt) {
		this.serviceRecoveryCnt = serviceRecoveryCnt;
	}
	public String getFlightGroupName() {
		return flightGroupName;
	}
	public void setFlightGroupName(String flightGroupName) {
		this.flightGroupName = flightGroupName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getDocumentOwnerId() {
		return documentOwnerId;
	}
	public void setDocumentOwnerId(int documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public String getCancellationReasonCode() {
		return cancellationReasonCode;
	}
	public void setCancellationReasonCode(String cancellationReasonCode) {
		this.cancellationReasonCode = cancellationReasonCode;
	}
	public LocalDate getCancelledDate() {
		return cancelledDate;
	}
	public void setCancelledDate(LocalDate cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	public String getCancelRemarks() {
		return cancelRemarks;
	}
	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
	}
	public double getProvidedSalesLowerDeckOne() {
		return providedSalesLowerDeckOne;
	}
	public void setProvidedSalesLowerDeckOne(double providedSalesLowerDeckOne) {
		this.providedSalesLowerDeckOne = providedSalesLowerDeckOne;
	}
	public double getProvidedSalesLowerDeckTwo() {
		return providedSalesLowerDeckTwo;
	}
	public void setProvidedSalesLowerDeckTwo(double providedSalesLowerDeckTwo) {
		this.providedSalesLowerDeckTwo = providedSalesLowerDeckTwo;
	}
	public double getProvidedSalesUpperDeckOne() {
		return providedSalesUpperDeckOne;
	}
	public void setProvidedSalesUpperDeckOne(double providedSalesUpperDeckOne) {
		this.providedSalesUpperDeckOne = providedSalesUpperDeckOne;
	}
	public double getProvidedSalesUpperDeckTwo() {
		return providedSalesUpperDeckTwo;
	}
	public void setProvidedSalesUpperDeckTwo(double providedSalesUpperDeckTwo) {
		this.providedSalesUpperDeckTwo = providedSalesUpperDeckTwo;
	}
	public double getProvidedHandlingLowerDeckOne() {
		return providedHandlingLowerDeckOne;
	}
	public void setProvidedHandlingLowerDeckOne(double providedHandlingLowerDeckOne) {
		this.providedHandlingLowerDeckOne = providedHandlingLowerDeckOne;
	}
	public double getProvidedHandlingLowerDeckTwo() {
		return providedHandlingLowerDeckTwo;
	}
	public void setProvidedHandlingLowerDeckTwo(double providedHandlingLowerDeckTwo) {
		this.providedHandlingLowerDeckTwo = providedHandlingLowerDeckTwo;
	}
	public double getProvidedHandlingUpperDeckOne() {
		return providedHandlingUpperDeckOne;
	}
	public void setProvidedHandlingUpperDeckOne(double providedHandlingUpperDeckOne) {
		this.providedHandlingUpperDeckOne = providedHandlingUpperDeckOne;
	}
	public double getProvidedHandlingUpperDeckTwo() {
		return providedHandlingUpperDeckTwo;
	}
	public void setProvidedHandlingUpperDeckTwo(double providedHandlingUpperDeckTwo) {
		this.providedHandlingUpperDeckTwo = providedHandlingUpperDeckTwo;
	}
	public String getUtilizationflagalert() {
		return utilizationflagalert;
	}
	public void setUtilizationflagalert(String utilizationflagalert) {
		this.utilizationflagalert = utilizationflagalert;
	}
	public String getMalperformanceflag() {
		return malperformanceflag;
	}
	public void setMalperformanceflag(String malperformanceflag) {
		this.malperformanceflag = malperformanceflag;
	}
	public String getShipmentacceptancestatus() {
		return shipmentacceptancestatus;
	}
	public void setShipmentacceptancestatus(String shipmentacceptancestatus) {
		this.shipmentacceptancestatus = shipmentacceptancestatus;
	}
	public Measure getBookedweight() {
		return bookedweight;
	}
	public void setBookedweight(Measure bookedweight) {
		this.bookedweight = bookedweight;
	}
	public Measure getBookedvolume() {
		return bookedvolume;
	}
	public void setBookedvolume(Measure bookedvolume) {
		this.bookedvolume = bookedvolume;
	}
	public double getBookedUpperDeckOne() {
		return bookedUpperDeckOne;
	}
	public void setBookedUpperDeckOne(double bookedUpperDeckOne) {
		this.bookedUpperDeckOne = bookedUpperDeckOne;
	}
	public double getBookedUpperDeckTwo() {
		return bookedUpperDeckTwo;
	}
	public void setBookedUpperDeckTwo(double bookedUpperDeckTwo) {
		this.bookedUpperDeckTwo = bookedUpperDeckTwo;
	}
	public double getBookedLowerDeckOne() {
		return bookedLowerDeckOne;
	}
	public void setBookedLowerDeckOne(double bookedLowerDeckOne) {
		this.bookedLowerDeckOne = bookedLowerDeckOne;
	}
	public double getBookedLowerDeckTwo() {
		return bookedLowerDeckTwo;
	}
	public void setBookedLowerDeckTwo(double bookedLowerDeckTwo) {
		this.bookedLowerDeckTwo = bookedLowerDeckTwo;
	}
	public HashMap<String, String> getFlightErrors() {
		return flightErrors;
	}
	public void setFlightErrors(HashMap<String, String> flightErrors) {
		this.flightErrors = flightErrors;
	}
	public String getTransportOrigin() {
		return transportOrigin;
	}
	public void setTransportOrigin(String transportOrigin) {
		this.transportOrigin = transportOrigin;
	}
	public String getTransportDestination() {
		return transportDestination;
	}
	public void setTransportDestination(String transportDestination) {
		this.transportDestination = transportDestination;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public LocalDate getScheduleDateOfDeparture() {
		return scheduleDateOfDeparture;
	}
	public void setScheduleDateOfDeparture(LocalDate scheduleDateOfDeparture) {
		this.scheduleDateOfDeparture = scheduleDateOfDeparture;
	}
	public LocalDate getScheduleDateOfArrival() {
		return scheduleDateOfArrival;
	}
	public void setScheduleDateOfArrival(LocalDate scheduleDateOfArrival) {
		this.scheduleDateOfArrival = scheduleDateOfArrival;
	}
	public String getSegmentString() {
		return segmentString;
	}
	public void setSegmentString(String segmentString) {
		this.segmentString = segmentString;
	}
	public LocalDate getTimeOfDeparture() {
		return timeOfDeparture;
	}
	public void setTimeOfDeparture(LocalDate timeOfDeparture) {
		this.timeOfDeparture = timeOfDeparture;
	}
	public LocalDate getTimeOfArrival() {
		return timeOfArrival;
	}
	public void setTimeOfArrival(LocalDate timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String getRestrictionFlag() {
		return restrictionFlag;
	}
	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Measure getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(Measure totalVolume) {
		this.totalVolume = totalVolume;
	}
	public double getTotalUpperDeckOne() {
		return totalUpperDeckOne;
	}
	public void setTotalUpperDeckOne(double totalUpperDeckOne) {
		this.totalUpperDeckOne = totalUpperDeckOne;
	}
	public double getTotalUpperDeckTwo() {
		return totalUpperDeckTwo;
	}
	public void setTotalUpperDeckTwo(double totalUpperDeckTwo) {
		this.totalUpperDeckTwo = totalUpperDeckTwo;
	}
	public double getTotalLowerDeckOne() {
		return totalLowerDeckOne;
	}
	public void setTotalLowerDeckOne(double totalLowerDeckOne) {
		this.totalLowerDeckOne = totalLowerDeckOne;
	}
	public double getTotalLowerDeckTwo() {
		return totalLowerDeckTwo;
	}
	public void setTotalLowerDeckTwo(double totalLowerDeckTwo) {
		this.totalLowerDeckTwo = totalLowerDeckTwo;
	}
	public String getTotalUldString() {
		return totalUldString;
	}
	public void setTotalUldString(String totalUldString) {
		this.totalUldString = totalUldString;
	}
	public int getConfirmedBookingNumber() {
		return confirmedBookingNumber;
	}
	public void setConfirmedBookingNumber(int confirmedBookingNumber) {
		this.confirmedBookingNumber = confirmedBookingNumber;
	}
	public Measure getConfirmedBookingWeight() {
		return confirmedBookingWeight;
	}
	public void setConfirmedBookingWeight(Measure confirmedBookingWeight) {
		this.confirmedBookingWeight = confirmedBookingWeight;
	}
	public Measure getConfirmedBookingVolume() {
		return confirmedBookingVolume;
	}
	public void setConfirmedBookingVolume(Measure confirmedBookingVolume) {
		this.confirmedBookingVolume = confirmedBookingVolume;
	}
	public double getConfirmedLowerDeckOne() {
		return confirmedLowerDeckOne;
	}
	public void setConfirmedLowerDeckOne(double confirmedLowerDeckOne) {
		this.confirmedLowerDeckOne = confirmedLowerDeckOne;
	}
	public double getConfirmedLowerDeckTwo() {
		return confirmedLowerDeckTwo;
	}
	public void setConfirmedLowerDeckTwo(double confirmedLowerDeckTwo) {
		this.confirmedLowerDeckTwo = confirmedLowerDeckTwo;
	}
	public double getConfirmedUpperDeckOne() {
		return confirmedUpperDeckOne;
	}
	public void setConfirmedUpperDeckOne(double confirmedUpperDeckOne) {
		this.confirmedUpperDeckOne = confirmedUpperDeckOne;
	}
	public double getConfirmedUpperDeckTwo() {
		return confirmedUpperDeckTwo;
	}
	public void setConfirmedUpperDeckTwo(double confirmedUpperDeckTwo) {
		this.confirmedUpperDeckTwo = confirmedUpperDeckTwo;
	}
	public String getConfirmedUldString() {
		return confirmedUldString;
	}
	public void setConfirmedUldString(String confirmedUldString) {
		this.confirmedUldString = confirmedUldString;
	}
	public double getTargetedRevenue() {
		return targetedRevenue;
	}
	public void setTargetedRevenue(double targetedRevenue) {
		this.targetedRevenue = targetedRevenue;
	}
	public Measure getTargetedYield() {
		return targetedYield;
	}
	public void setTargetedYield(Measure targetedYield) {
		this.targetedYield = targetedYield;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public double getRevenueInUSD() {
		return revenueInUSD;
	}
	public void setRevenueInUSD(double revenueInUSD) {
		this.revenueInUSD = revenueInUSD;
	}
	public Measure getYield() {
		return yield;
	}
	public void setYield(Measure yield) {
		this.yield = yield;
	}
	public Measure getYieldInUSD() {
		return yieldInUSD;
	}
	public void setYieldInUSD(Measure yieldInUSD) {
		this.yieldInUSD = yieldInUSD;
	}
	public int getQueuedBookingNumber() {
		return queuedBookingNumber;
	}
	public void setQueuedBookingNumber(int queuedBookingNumber) {
		this.queuedBookingNumber = queuedBookingNumber;
	}
	public Measure getQueuedBookingWeight() {
		return queuedBookingWeight;
	}
	public void setQueuedBookingWeight(Measure queuedBookingWeight) {
		this.queuedBookingWeight = queuedBookingWeight;
	}
	public Measure getQueuedBookingVolume() {
		return queuedBookingVolume;
	}
	public void setQueuedBookingVolume(Measure queuedBookingVolume) {
		this.queuedBookingVolume = queuedBookingVolume;
	}
	public double getQueuedLowerDeckOne() {
		return queuedLowerDeckOne;
	}
	public void setQueuedLowerDeckOne(double queuedLowerDeckOne) {
		this.queuedLowerDeckOne = queuedLowerDeckOne;
	}
	public double getQueuedLowerDeckTwo() {
		return queuedLowerDeckTwo;
	}
	public void setQueuedLowerDeckTwo(double queuedLowerDeckTwo) {
		this.queuedLowerDeckTwo = queuedLowerDeckTwo;
	}
	public double getQueuedUpperDeckOne() {
		return queuedUpperDeckOne;
	}
	public void setQueuedUpperDeckOne(double queuedUpperDeckOne) {
		this.queuedUpperDeckOne = queuedUpperDeckOne;
	}
	public double getQueuedUpperDeckTwo() {
		return queuedUpperDeckTwo;
	}
	public void setQueuedUpperDeckTwo(double queuedUpperDeckTwo) {
		this.queuedUpperDeckTwo = queuedUpperDeckTwo;
	}
	public String getQueuedUldString() {
		return queuedUldString;
	}
	public void setQueuedUldString(String queuedUldString) {
		this.queuedUldString = queuedUldString;
	}
	public String getOperationalFlag() {
		return operationalFlag;
	}
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	public boolean isConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public boolean isHasChangedQueueToConfirm() {
		return hasChangedQueueToConfirm;
	}
	public void setHasChangedQueueToConfirm(boolean hasChangedQueueToConfirm) {
		this.hasChangedQueueToConfirm = hasChangedQueueToConfirm;
	}
	public String getFlightCarrrierCode() {
		return flightCarrrierCode;
	}
	public void setFlightCarrrierCode(String flightCarrrierCode) {
		this.flightCarrrierCode = flightCarrrierCode;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public String getIsDomestic() {
		return isDomestic;
	}
	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightRoute() {
		return flightRoute;
	}
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	public int getConfirmedPieces() {
		return confirmedPieces;
	}
	public void setConfirmedPieces(int confirmedPieces) {
		this.confirmedPieces = confirmedPieces;
	}
	public int getQueuedPieces() {
		return queuedPieces;
	}
	public void setQueuedPieces(int queuedPieces) {
		this.queuedPieces = queuedPieces;
	}
	public String getLegStatus() {
		return legStatus;
	}
	public void setLegStatus(String legStatus) {
		this.legStatus = legStatus;
	}
	public String getOprStatus() {
		return oprStatus;
	}
	public void setOprStatus(String oprStatus) {
		this.oprStatus = oprStatus;
	}
	public String getBkgStatus() {
		return bkgStatus;
	}
	public void setBkgStatus(String bkgStatus) {
		this.bkgStatus = bkgStatus;
	}
	public Measure getTotalAlotmentAvailableWeight() {
		return totalAlotmentAvailableWeight;
	}
	public void setTotalAlotmentAvailableWeight(Measure totalAlotmentAvailableWeight) {
		this.totalAlotmentAvailableWeight = totalAlotmentAvailableWeight;
	}
	public Measure getTotalAlotmentAvailableVolume() {
		return totalAlotmentAvailableVolume;
	}
	public void setTotalAlotmentAvailableVolume(Measure totalAlotmentAvailableVolume) {
		this.totalAlotmentAvailableVolume = totalAlotmentAvailableVolume;
	}
	public double getTotalAllotmentAvailableLowerDeckOne() {
		return totalAllotmentAvailableLowerDeckOne;
	}
	public void setTotalAllotmentAvailableLowerDeckOne(
			double totalAllotmentAvailableLowerDeckOne) {
		this.totalAllotmentAvailableLowerDeckOne = totalAllotmentAvailableLowerDeckOne;
	}
	public double getTotalAllotmentAvailableLowerDeckTwo() {
		return totalAllotmentAvailableLowerDeckTwo;
	}
	public void setTotalAllotmentAvailableLowerDeckTwo(
			double totalAllotmentAvailableLowerDeckTwo) {
		this.totalAllotmentAvailableLowerDeckTwo = totalAllotmentAvailableLowerDeckTwo;
	}
	public double getTotalAllotmentAvailableUpperDeckOne() {
		return totalAllotmentAvailableUpperDeckOne;
	}
	public void setTotalAllotmentAvailableUpperDeckOne(
			double totalAllotmentAvailableUpperDeckOne) {
		this.totalAllotmentAvailableUpperDeckOne = totalAllotmentAvailableUpperDeckOne;
	}
	public double getTotalAllotmentAvailableUpperDeckTwo() {
		return totalAllotmentAvailableUpperDeckTwo;
	}
	public void setTotalAllotmentAvailableUpperDeckTwo(
			double totalAllotmentAvailableUpperDeckTwo) {
		this.totalAllotmentAvailableUpperDeckTwo = totalAllotmentAvailableUpperDeckTwo;
	}
	public String getTotalAllotmentAvailableUldString() {
		return totalAllotmentAvailableUldString;
	}
	public void setTotalAllotmentAvailableUldString(
			String totalAllotmentAvailableUldString) {
		this.totalAllotmentAvailableUldString = totalAllotmentAvailableUldString;
	}
	public Measure getAvailableFreesaleWeight() {
		return availableFreesaleWeight;
	}
	public void setAvailableFreesaleWeight(Measure availableFreesaleWeight) {
		this.availableFreesaleWeight = availableFreesaleWeight;
	}
	public Measure getAvailableFreesaleVolume() {
		return availableFreesaleVolume;
	}
	public void setAvailableFreesaleVolume(Measure availableFreesaleVolume) {
		this.availableFreesaleVolume = availableFreesaleVolume;
	}
	public double getAvailableFreesaleLowerDeckOne() {
		return availableFreesaleLowerDeckOne;
	}
	public void setAvailableFreesaleLowerDeckOne(
			double availableFreesaleLowerDeckOne) {
		this.availableFreesaleLowerDeckOne = availableFreesaleLowerDeckOne;
	}
	public double getAvailableFreesaleLowerDeckTwo() {
		return availableFreesaleLowerDeckTwo;
	}
	public void setAvailableFreesaleLowerDeckTwo(
			double availableFreesaleLowerDeckTwo) {
		this.availableFreesaleLowerDeckTwo = availableFreesaleLowerDeckTwo;
	}
	public double getAvailableFreesaleUpperDeckOne() {
		return availableFreesaleUpperDeckOne;
	}
	public void setAvailableFreesaleUpperDeckOne(
			double availableFreesaleUpperDeckOne) {
		this.availableFreesaleUpperDeckOne = availableFreesaleUpperDeckOne;
	}
	public double getAvailableFreesaleUpperDeckTwo() {
		return availableFreesaleUpperDeckTwo;
	}
	public void setAvailableFreesaleUpperDeckTwo(
			double availableFreesaleUpperDeckTwo) {
		this.availableFreesaleUpperDeckTwo = availableFreesaleUpperDeckTwo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	public int getInsertOrder() {
		return insertOrder;
	}
	public void setInsertOrder(int insertOrder) {
		this.insertOrder = insertOrder;
	}
	public String getSegmentStatus() {
		return segmentStatus;
	}
	public void setSegmentStatus(String segmentStatus) {
		this.segmentStatus = segmentStatus;
	}
	public boolean isAllowBookingOnFreesale() {
		return isAllowBookingOnFreesale;
	}
	public void setAllowBookingOnFreesale(boolean isAllowBookingOnFreesale) {
		this.isAllowBookingOnFreesale = isAllowBookingOnFreesale;
	}
	public String getDaysOfOperation() {
		return daysOfOperation;
	}
	public void setDaysOfOperation(String daysOfOperation) {
		this.daysOfOperation = daysOfOperation;
	}
	public Measure getTolerenceWeight() {
		return tolerenceWeight;
	}
	public void setTolerenceWeight(Measure tolerenceWeight) {
		this.tolerenceWeight = tolerenceWeight;
	}
	public Measure getFreesaleToleranceVolume() {
		return freesaleToleranceVolume;
	}
	public void setFreesaleToleranceVolume(Measure freesaleToleranceVolume) {
		this.freesaleToleranceVolume = freesaleToleranceVolume;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public boolean isOverrideSegmentCapacity() {
		return isOverrideSegmentCapacity;
	}
	public void setOverrideSegmentCapacity(boolean isOverrideSegmentCapacity) {
		this.isOverrideSegmentCapacity = isOverrideSegmentCapacity;
	}
	public Measure getSegmentTotalWeight() {
		return segmentTotalWeight;
	}
	public void setSegmentTotalWeight(Measure segmentTotalWeight) {
		this.segmentTotalWeight = segmentTotalWeight;
	}
	public Measure getSegmentTotalVolume() {
		return segmentTotalVolume;
	}
	public void setSegmentTotalVolume(Measure segmentTotalVolume) {
		this.segmentTotalVolume = segmentTotalVolume;
	}
	public Measure getOverbookingWeight() {
		return overbookingWeight;
	}
	public void setOverbookingWeight(Measure overbookingWeight) {
		this.overbookingWeight = overbookingWeight;
	}
	public Measure getOverbookingVolume() {
		return overbookingVolume;
	}
	public void setOverbookingVolume(Measure overbookingVolume) {
		this.overbookingVolume = overbookingVolume;
	}
	public Measure getSegmentOverbookingWeight() {
		return segmentOverbookingWeight;
	}
	public void setSegmentOverbookingWeight(Measure segmentOverbookingWeight) {
		this.segmentOverbookingWeight = segmentOverbookingWeight;
	}
	public Measure getSegmentOverbookingVolume() {
		return segmentOverbookingVolume;
	}
	public void setSegmentOverbookingVolume(Measure segmentOverbookingVolume) {
		this.segmentOverbookingVolume = segmentOverbookingVolume;
	}
	public boolean isReleaseFlag() {
		return releaseFlag;
	}
	public void setReleaseFlag(boolean releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getGlobalCustCode() {
		return globalCustCode;
	}
	public void setGlobalCustCode(String globalCustCode) {
		this.globalCustCode = globalCustCode;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getAllotmentIdentifier() {
		return allotmentIdentifier;
	}
	public void setAllotmentIdentifier(String allotmentIdentifier) {
		this.allotmentIdentifier = allotmentIdentifier;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getCarrierIdentifier() {
		return carrierIdentifier;
	}
	public void setCarrierIdentifier(int carrierIdentifier) {
		this.carrierIdentifier = carrierIdentifier;
	}
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	public Measure getMinimumEntryYield() {
		return minimumEntryYield;
	}
	public void setMinimumEntryYield(Measure minimumEntryYield) {
		this.minimumEntryYield = minimumEntryYield;
	}
	public String getAllotmentCurrency() {
		return allotmentCurrency;
	}
	public void setAllotmentCurrency(String allotmentCurrency) {
		this.allotmentCurrency = allotmentCurrency;
	}
	public Measure getTotalHandlingWeight() {
		return totalHandlingWeight;
	}
	public void setTotalHandlingWeight(Measure totalHandlingWeight) {
		this.totalHandlingWeight = totalHandlingWeight;
	}
	public Measure getTotalHandlingVolume() {
		return totalHandlingVolume;
	}
	public void setTotalHandlingVolume(Measure totalHandlingVolume) {
		this.totalHandlingVolume = totalHandlingVolume;
	}
	public double getTotalHandlingUpperDeckOne() {
		return totalHandlingUpperDeckOne;
	}
	public void setTotalHandlingUpperDeckOne(double totalHandlingUpperDeckOne) {
		this.totalHandlingUpperDeckOne = totalHandlingUpperDeckOne;
	}
	public double getTotalHandlingUpperDeckTwo() {
		return totalHandlingUpperDeckTwo;
	}
	public void setTotalHandlingUpperDeckTwo(double totalHandlingUpperDeckTwo) {
		this.totalHandlingUpperDeckTwo = totalHandlingUpperDeckTwo;
	}
	public double getTotalHandlingLowerDeckOne() {
		return totalHandlingLowerDeckOne;
	}
	public void setTotalHandlingLowerDeckOne(double totalHandlingLowerDeckOne) {
		this.totalHandlingLowerDeckOne = totalHandlingLowerDeckOne;
	}
	public double getTotalHandlingLowerDeckTwo() {
		return totalHandlingLowerDeckTwo;
	}
	public void setTotalHandlingLowerDeckTwo(double totalHandlingLowerDeckTwo) {
		this.totalHandlingLowerDeckTwo = totalHandlingLowerDeckTwo;
	}
	public Measure getProvidedSalesWeight() {
		return providedSalesWeight;
	}
	public void setProvidedSalesWeight(Measure providedSalesWeight) {
		this.providedSalesWeight = providedSalesWeight;
	}
	public Measure getProvidedSalesVolume() {
		return providedSalesVolume;
	}
	public void setProvidedSalesVolume(Measure providedSalesVolume) {
		this.providedSalesVolume = providedSalesVolume;
	}
	public Measure getProvidedHandlingWeight() {
		return providedHandlingWeight;
	}
	public void setProvidedHandlingWeight(Measure providedHandlingWeight) {
		this.providedHandlingWeight = providedHandlingWeight;
	}
	public Measure getProvidedHandlingVolume() {
		return providedHandlingVolume;
	}
	public void setProvidedHandlingVolume(Measure providedHandlingVolume) {
		this.providedHandlingVolume = providedHandlingVolume;
	}
	public Measure getGuaranteedWeight() {
		return guaranteedWeight;
	}
	public void setGuaranteedWeight(Measure guaranteedWeight) {
		this.guaranteedWeight = guaranteedWeight;
	}
	public Measure getGuaranteedVolume() {
		return guaranteedVolume;
	}
	public void setGuaranteedVolume(Measure guaranteedVolume) {
		this.guaranteedVolume = guaranteedVolume;
	}
	public double getGuaranteedUpperDeckOne() {
		return guaranteedUpperDeckOne;
	}
	public void setGuaranteedUpperDeckOne(double guaranteedUpperDeckOne) {
		this.guaranteedUpperDeckOne = guaranteedUpperDeckOne;
	}
	public double getGuaranteedUpperDeckTwo() {
		return guaranteedUpperDeckTwo;
	}
	public void setGuaranteedUpperDeckTwo(double guaranteedUpperDeckTwo) {
		this.guaranteedUpperDeckTwo = guaranteedUpperDeckTwo;
	}
	public double getGuaranteedLowerDeckOne() {
		return guaranteedLowerDeckOne;
	}
	public void setGuaranteedLowerDeckOne(double guaranteedLowerDeckOne) {
		this.guaranteedLowerDeckOne = guaranteedLowerDeckOne;
	}
	public double getGuaranteedLowerDeckTwo() {
		return guaranteedLowerDeckTwo;
	}
	public void setGuaranteedLowerDeckTwo(double guaranteedLowerDeckTwo) {
		this.guaranteedLowerDeckTwo = guaranteedLowerDeckTwo;
	}
	public Measure getLimitedWeight() {
		return limitedWeight;
	}
	public void setLimitedWeight(Measure limitedWeight) {
		this.limitedWeight = limitedWeight;
	}
	public Measure getLimitedVolume() {
		return limitedVolume;
	}
	public void setLimitedVolume(Measure limitedVolume) {
		this.limitedVolume = limitedVolume;
	}
	public double getLimitedUpperDeckOne() {
		return limitedUpperDeckOne;
	}
	public void setLimitedUpperDeckOne(double limitedUpperDeckOne) {
		this.limitedUpperDeckOne = limitedUpperDeckOne;
	}
	public double getLimitedUpperDeckTwo() {
		return limitedUpperDeckTwo;
	}
	public void setLimitedUpperDeckTwo(double limitedUpperDeckTwo) {
		this.limitedUpperDeckTwo = limitedUpperDeckTwo;
	}
	public double getLimitedLowerDeckOne() {
		return limitedLowerDeckOne;
	}
	public void setLimitedLowerDeckOne(double limitedLowerDeckOne) {
		this.limitedLowerDeckOne = limitedLowerDeckOne;
	}
	public double getLimitedLowerDeckTwo() {
		return limitedLowerDeckTwo;
	}
	public void setLimitedLowerDeckTwo(double limitedLowerDeckTwo) {
		this.limitedLowerDeckTwo = limitedLowerDeckTwo;
	}
	public Measure getTotalAllotmentWeight() {
		return totalAllotmentWeight;
	}
	public void setTotalAllotmentWeight(Measure totalAllotmentWeight) {
		this.totalAllotmentWeight = totalAllotmentWeight;
	}
	public Measure getTotalAllotmentVolume() {
		return totalAllotmentVolume;
	}
	public void setTotalAllotmentVolume(Measure totalAllotmentVolume) {
		this.totalAllotmentVolume = totalAllotmentVolume;
	}
	public double getTotalAllotmentLowerDeckOne() {
		return totalAllotmentLowerDeckOne;
	}
	public void setTotalAllotmentLowerDeckOne(double totalAllotmentLowerDeckOne) {
		this.totalAllotmentLowerDeckOne = totalAllotmentLowerDeckOne;
	}
	public double getTotalAllotmentLowerDeckTwo() {
		return totalAllotmentLowerDeckTwo;
	}
	public void setTotalAllotmentLowerDeckTwo(double totalAllotmentLowerDeckTwo) {
		this.totalAllotmentLowerDeckTwo = totalAllotmentLowerDeckTwo;
	}
	public double getTotalAllotmentUpperDeckOne() {
		return totalAllotmentUpperDeckOne;
	}
	public void setTotalAllotmentUpperDeckOne(double totalAllotmentUpperDeckOne) {
		this.totalAllotmentUpperDeckOne = totalAllotmentUpperDeckOne;
	}
	public double getTotalAllotmentUpperDeckTwo() {
		return totalAllotmentUpperDeckTwo;
	}
	public void setTotalAllotmentUpperDeckTwo(double totalAllotmentUpperDeckTwo) {
		this.totalAllotmentUpperDeckTwo = totalAllotmentUpperDeckTwo;
	}
	public Measure getAllotmentConfirmedWeight() {
		return allotmentConfirmedWeight;
	}
	public void setAllotmentConfirmedWeight(Measure allotmentConfirmedWeight) {
		this.allotmentConfirmedWeight = allotmentConfirmedWeight;
	}
	public Measure getAllotmentConfirmedVolume() {
		return allotmentConfirmedVolume;
	}
	public void setAllotmentConfirmedVolume(Measure allotmentConfirmedVolume) {
		this.allotmentConfirmedVolume = allotmentConfirmedVolume;
	}
	public double getAllotmentConfirmedLowerDeckOne() {
		return allotmentConfirmedLowerDeckOne;
	}
	public void setAllotmentConfirmedLowerDeckOne(
			double allotmentConfirmedLowerDeckOne) {
		this.allotmentConfirmedLowerDeckOne = allotmentConfirmedLowerDeckOne;
	}
	public double getAllotmentConfirmedLowerDeckTwo() {
		return allotmentConfirmedLowerDeckTwo;
	}
	public void setAllotmentConfirmedLowerDeckTwo(
			double allotmentConfirmedLowerDeckTwo) {
		this.allotmentConfirmedLowerDeckTwo = allotmentConfirmedLowerDeckTwo;
	}
	public double getAllotmentConfirmedUpperDeckOne() {
		return allotmentConfirmedUpperDeckOne;
	}
	public void setAllotmentConfirmedUpperDeckOne(
			double allotmentConfirmedUpperDeckOne) {
		this.allotmentConfirmedUpperDeckOne = allotmentConfirmedUpperDeckOne;
	}
	public double getAllotmentConfirmedUpperDeckTwo() {
		return allotmentConfirmedUpperDeckTwo;
	}
	public void setAllotmentConfirmedUpperDeckTwo(
			double allotmentConfirmedUpperDeckTwo) {
		this.allotmentConfirmedUpperDeckTwo = allotmentConfirmedUpperDeckTwo;
	}
	public Measure getFreesaleConfirmedWeight() {
		return freesaleConfirmedWeight;
	}
	public void setFreesaleConfirmedWeight(Measure freesaleConfirmedWeight) {
		this.freesaleConfirmedWeight = freesaleConfirmedWeight;
	}
	public Measure getFreesaleConfirmedVolume() {
		return freesaleConfirmedVolume;
	}
	public void setFreesaleConfirmedVolume(Measure freesaleConfirmedVolume) {
		this.freesaleConfirmedVolume = freesaleConfirmedVolume;
	}
	public double getFreesaleConfirmedLowerDeckOne() {
		return freesaleConfirmedLowerDeckOne;
	}
	public void setFreesaleConfirmedLowerDeckOne(
			double freesaleConfirmedLowerDeckOne) {
		this.freesaleConfirmedLowerDeckOne = freesaleConfirmedLowerDeckOne;
	}
	public double getFreesaleConfirmedLowerDeckTwo() {
		return freesaleConfirmedLowerDeckTwo;
	}
	public void setFreesaleConfirmedLowerDeckTwo(
			double freesaleConfirmedLowerDeckTwo) {
		this.freesaleConfirmedLowerDeckTwo = freesaleConfirmedLowerDeckTwo;
	}
	public double getFreesaleConfirmedUpperDeckOne() {
		return freesaleConfirmedUpperDeckOne;
	}
	public void setFreesaleConfirmedUpperDeckOne(
			double freesaleConfirmedUpperDeckOne) {
		this.freesaleConfirmedUpperDeckOne = freesaleConfirmedUpperDeckOne;
	}
	public double getFreesaleConfirmedUpperDeckTwo() {
		return freesaleConfirmedUpperDeckTwo;
	}
	public void setFreesaleConfirmedUpperDeckTwo(
			double freesaleConfirmedUpperDeckTwo) {
		this.freesaleConfirmedUpperDeckTwo = freesaleConfirmedUpperDeckTwo;
	}
	public String getAllotmentFlag() {
		return allotmentFlag;
	}
	public void setAllotmentFlag(String allotmentFlag) {
		this.allotmentFlag = allotmentFlag;
	}
	public LocalDate getAllotmentDate() {
		return allotmentDate;
	}
	public void setAllotmentDate(LocalDate allotmentDate) {
		this.allotmentDate = allotmentDate;
	}
	public String getAllotmentSubType() {
		return allotmentSubType;
	}
	public void setAllotmentSubType(String allotmentSubType) {
		this.allotmentSubType = allotmentSubType;
	}
	public String getToStationCode() {
		return toStationCode;
	}
	public void setToStationCode(String toStationCode) {
		this.toStationCode = toStationCode;
	}
	public String getToSegmentOrigin() {
		return toSegmentOrigin;
	}
	public void setToSegmentOrigin(String toSegmentOrigin) {
		this.toSegmentOrigin = toSegmentOrigin;
	}
	public String getToSegmentDestination() {
		return toSegmentDestination;
	}
	public void setToSegmentDestination(String toSegmentDestination) {
		this.toSegmentDestination = toSegmentDestination;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Measure getLegWeight() {
		return legWeight;
	}
	public void setLegWeight(Measure legWeight) {
		this.legWeight = legWeight;
	}
	public Measure getAvailableWeight() {
		return availableWeight;
	}
	public void setAvailableWeight(Measure availableWeight) {
		this.availableWeight = availableWeight;
	}
	public Measure getAvailableVolume() {
		return availableVolume;
	}
	public void setAvailableVolume(Measure availableVolume) {
		this.availableVolume = availableVolume;
	}
	public Measure getLegVolume() {
		return legVolume;
	}
	public void setLegVolume(Measure legVolume) {
		this.legVolume = legVolume;
	}
	public double getLegLowerDeckOne() {
		return legLowerDeckOne;
	}
	public void setLegLowerDeckOne(double legLowerDeckOne) {
		this.legLowerDeckOne = legLowerDeckOne;
	}
	public double getLegLowerDeckTwo() {
		return legLowerDeckTwo;
	}
	public void setLegLowerDeckTwo(double legLowerDeckTwo) {
		this.legLowerDeckTwo = legLowerDeckTwo;
	}
	public double getLegUpperDeckOne() {
		return legUpperDeckOne;
	}
	public void setLegUpperDeckOne(double legUpperDeckOne) {
		this.legUpperDeckOne = legUpperDeckOne;
	}
	public double getLegUpperDeckTwo() {
		return legUpperDeckTwo;
	}
	public void setLegUpperDeckTwo(double legUpperDeckTwo) {
		this.legUpperDeckTwo = legUpperDeckTwo;
	}
	public String getLegSegment() {
		return legSegment;
	}
	public void setLegSegment(String legSegment) {
		this.legSegment = legSegment;
	}
	public LocalDate getScheduleTimeOfDepartureAtfirstLeg() {
		return scheduleTimeOfDepartureAtfirstLeg;
	}
	public void setScheduleTimeOfDepartureAtfirstLeg(
			LocalDate scheduleTimeOfDepartureAtfirstLeg) {
		this.scheduleTimeOfDepartureAtfirstLeg = scheduleTimeOfDepartureAtfirstLeg;
	}
	public Measure getUtilisedOverbookingWeight() {
		return utilisedOverbookingWeight;
	}
	public void setUtilisedOverbookingWeight(Measure utilisedOverbookingWeight) {
		this.utilisedOverbookingWeight = utilisedOverbookingWeight;
	}
	public Measure getUtilisedOverbookingVolume() {
		return utilisedOverbookingVolume;
	}
	public void setUtilisedOverbookingVolume(Measure utilisedOverbookingVolume) {
		this.utilisedOverbookingVolume = utilisedOverbookingVolume;
	}
	public String getTailNumber() {
		return tailNumber;
	}
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
	public LocalDate getScheduleTimeOfDepartureAtFirstLeg() {
		return scheduleTimeOfDepartureAtFirstLeg;
	}
	public void setScheduleTimeOfDepartureAtFirstLeg(
			LocalDate scheduleTimeOfDepartureAtFirstLeg) {
		this.scheduleTimeOfDepartureAtFirstLeg = scheduleTimeOfDepartureAtFirstLeg;
	}
	public Measure getStationAllotmentTotalWeight() {
		return stationAllotmentTotalWeight;
	}
	public void setStationAllotmentTotalWeight(Measure stationAllotmentTotalWeight) {
		this.stationAllotmentTotalWeight = stationAllotmentTotalWeight;
	}
	public Measure getStationAllotmentTotalVolume() {
		return stationAllotmentTotalVolume;
	}
	public void setStationAllotmentTotalVolume(Measure stationAllotmentTotalVolume) {
		this.stationAllotmentTotalVolume = stationAllotmentTotalVolume;
	}
	public double getStationAllotmentTotalLD1() {
		return stationAllotmentTotalLD1;
	}
	public void setStationAllotmentTotalLD1(double stationAllotmentTotalLD1) {
		this.stationAllotmentTotalLD1 = stationAllotmentTotalLD1;
	}
	public double getStationAllotmentTotalLD2() {
		return stationAllotmentTotalLD2;
	}
	public void setStationAllotmentTotalLD2(double stationAllotmentTotalLD2) {
		this.stationAllotmentTotalLD2 = stationAllotmentTotalLD2;
	}
	public double getStationAllotmentTotalUD1() {
		return stationAllotmentTotalUD1;
	}
	public void setStationAllotmentTotalUD1(double stationAllotmentTotalUD1) {
		this.stationAllotmentTotalUD1 = stationAllotmentTotalUD1;
	}
	public double getStationAllotmentTotalUD2() {
		return stationAllotmentTotalUD2;
	}
	public void setStationAllotmentTotalUD2(double stationAllotmentTotalUD2) {
		this.stationAllotmentTotalUD2 = stationAllotmentTotalUD2;
	}
	public Measure getStationAllotmentConfirmedWeight() {
		return stationAllotmentConfirmedWeight;
	}
	public void setStationAllotmentConfirmedWeight(
			Measure stationAllotmentConfirmedWeight) {
		this.stationAllotmentConfirmedWeight = stationAllotmentConfirmedWeight;
	}
	public Measure getStationAllotmentConfirmedVolume() {
		return stationAllotmentConfirmedVolume;
	}
	public void setStationAllotmentConfirmedVolume(
			Measure stationAllotmentConfirmedVolume) {
		this.stationAllotmentConfirmedVolume = stationAllotmentConfirmedVolume;
	}
	public double getStationAllotmentConfirmedLD1() {
		return stationAllotmentConfirmedLD1;
	}
	public void setStationAllotmentConfirmedLD1(double stationAllotmentConfirmedLD1) {
		this.stationAllotmentConfirmedLD1 = stationAllotmentConfirmedLD1;
	}
	public double getStationAllotmentConfirmedLD2() {
		return stationAllotmentConfirmedLD2;
	}
	public void setStationAllotmentConfirmedLD2(double stationAllotmentConfirmedLD2) {
		this.stationAllotmentConfirmedLD2 = stationAllotmentConfirmedLD2;
	}
	public double getStationAllotmentConfirmedUD1() {
		return stationAllotmentConfirmedUD1;
	}
	public void setStationAllotmentConfirmedUD1(double stationAllotmentConfirmedUD1) {
		this.stationAllotmentConfirmedUD1 = stationAllotmentConfirmedUD1;
	}
	public double getStationAllotmentConfirmedUD2() {
		return stationAllotmentConfirmedUD2;
	}
	public void setStationAllotmentConfirmedUD2(double stationAllotmentConfirmedUD2) {
		this.stationAllotmentConfirmedUD2 = stationAllotmentConfirmedUD2;
	}
	public double getMailLowerDeckOne() {
		return mailLowerDeckOne;
	}
	public void setMailLowerDeckOne(double mailLowerDeckOne) {
		this.mailLowerDeckOne = mailLowerDeckOne;
	}
	public double getMailLowerDeckTwo() {
		return mailLowerDeckTwo;
	}
	public void setMailLowerDeckTwo(double mailLowerDeckTwo) {
		this.mailLowerDeckTwo = mailLowerDeckTwo;
	}
	public double getMailUpperDeckOne() {
		return mailUpperDeckOne;
	}
	public void setMailUpperDeckOne(double mailUpperDeckOne) {
		this.mailUpperDeckOne = mailUpperDeckOne;
	}
	public double getMailUpperDeckTwo() {
		return mailUpperDeckTwo;
	}
	public void setMailUpperDeckTwo(double mailUpperDeckTwo) {
		this.mailUpperDeckTwo = mailUpperDeckTwo;
	}
	public int getUtilizedBaggage() {
		return utilizedBaggage;
	}
	public void setUtilizedBaggage(int utilizedBaggage) {
		this.utilizedBaggage = utilizedBaggage;
	}
	public int getDaysBeforeDeparture() {
		return daysBeforeDeparture;
	}
	public void setDaysBeforeDeparture(int daysBeforeDeparture) {
		this.daysBeforeDeparture = daysBeforeDeparture;
	}
	public String getUbrNumber() {
		return ubrNumber;
	}
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}
	public LocalDate getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(LocalDate txnTime) {
		this.txnTime = txnTime;
	}
	public Measure getUtilizedDryIceWeight() {
		return utilizedDryIceWeight;
	}
	public void setUtilizedDryIceWeight(Measure utilizedDryIceWeight) {
		this.utilizedDryIceWeight = utilizedDryIceWeight;
	}
	public Measure getTotalDryIceWeight() {
		return totalDryIceWeight;
	}
	public void setTotalDryIceWeight(Measure totalDryIceWeight) {
		this.totalDryIceWeight = totalDryIceWeight;
	}
	public Measure getAircraftDryIceWeight() {
		return aircraftDryIceWeight;
	}
	public void setAircraftDryIceWeight(Measure aircraftDryIceWeight) {
		this.aircraftDryIceWeight = aircraftDryIceWeight;
	}
	public String getReleaseAllotmentType() {
		return releaseAllotmentType;
	}
	public void setReleaseAllotmentType(String releaseAllotmentType) {
		this.releaseAllotmentType = releaseAllotmentType;
	}
	public String getCapacityId() {
		return capacityId;
	}
	public void setCapacityId(String capacityId) {
		this.capacityId = capacityId;
	}
	public double getPaxLoadFactor() {
		return paxLoadFactor;
	}
	public void setPaxLoadFactor(double paxLoadFactor) {
		this.paxLoadFactor = paxLoadFactor;
	}
	public Measure getMalWeight() {
		return malWeight;
	}
	public void setMalWeight(Measure malWeight) {
		this.malWeight = malWeight;
	}
	public Measure getAvaialableSalesWeight() {
		return avaialableSalesWeight;
	}
	public void setAvaialableSalesWeight(Measure avaialableSalesWeight) {
		this.avaialableSalesWeight = avaialableSalesWeight;
	}
	public Measure getAvaialableSalesVolume() {
		return avaialableSalesVolume;
	}
	public void setAvaialableSalesVolume(Measure avaialableSalesVolume) {
		this.avaialableSalesVolume = avaialableSalesVolume;
	}
	public double getAvaialableSalesLowerDeckOne() {
		return avaialableSalesLowerDeckOne;
	}
	public void setAvaialableSalesLowerDeckOne(double avaialableSalesLowerDeckOne) {
		this.avaialableSalesLowerDeckOne = avaialableSalesLowerDeckOne;
	}
	public double getAvaialableSalesLowerDeckTwo() {
		return avaialableSalesLowerDeckTwo;
	}
	public void setAvaialableSalesLowerDeckTwo(double avaialableSalesLowerDeckTwo) {
		this.avaialableSalesLowerDeckTwo = avaialableSalesLowerDeckTwo;
	}
	public double getAvaialableSalesUpperDeckOne() {
		return avaialableSalesUpperDeckOne;
	}
	public void setAvaialableSalesUpperDeckOne(double avaialableSalesUpperDeckOne) {
		this.avaialableSalesUpperDeckOne = avaialableSalesUpperDeckOne;
	}
	public double getAvaialableSalesUpperDeckTwo() {
		return avaialableSalesUpperDeckTwo;
	}
	public void setAvaialableSalesUpperDeckTwo(double avaialableSalesUpperDeckTwo) {
		this.avaialableSalesUpperDeckTwo = avaialableSalesUpperDeckTwo;
	}
	public Measure getAvaialableHandlingWeight() {
		return avaialableHandlingWeight;
	}
	public void setAvaialableHandlingWeight(Measure avaialableHandlingWeight) {
		this.avaialableHandlingWeight = avaialableHandlingWeight;
	}
	public Measure getAvaialableHandlingVolume() {
		return avaialableHandlingVolume;
	}
	public void setAvaialableHandlingVolume(Measure avaialableHandlingVolume) {
		this.avaialableHandlingVolume = avaialableHandlingVolume;
	}
	public double getAvaialableHandlingLowerDeckOne() {
		return avaialableHandlingLowerDeckOne;
	}
	public void setAvaialableHandlingLowerDeckOne(
			double avaialableHandlingLowerDeckOne) {
		this.avaialableHandlingLowerDeckOne = avaialableHandlingLowerDeckOne;
	}
	public double getAvaialableHandlingLowerDeckTwo() {
		return avaialableHandlingLowerDeckTwo;
	}
	public void setAvaialableHandlingLowerDeckTwo(
			double avaialableHandlingLowerDeckTwo) {
		this.avaialableHandlingLowerDeckTwo = avaialableHandlingLowerDeckTwo;
	}
	public double getAvaialableHandlingUpperDeckOne() {
		return avaialableHandlingUpperDeckOne;
	}
	public void setAvaialableHandlingUpperDeckOne(
			double avaialableHandlingUpperDeckOne) {
		this.avaialableHandlingUpperDeckOne = avaialableHandlingUpperDeckOne;
	}
	public double getAvaialableHandlingUpperDeckTwo() {
		return avaialableHandlingUpperDeckTwo;
	}
	public void setAvaialableHandlingUpperDeckTwo(
			double avaialableHandlingUpperDeckTwo) {
		this.avaialableHandlingUpperDeckTwo = avaialableHandlingUpperDeckTwo;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public double getAvailableLowerDeckOne() {
		return availableLowerDeckOne;
	}
	public void setAvailableLowerDeckOne(double availableLowerDeckOne) {
		this.availableLowerDeckOne = availableLowerDeckOne;
	}
	public double getAvailableLowerDeckTwo() {
		return availableLowerDeckTwo;
	}
	public void setAvailableLowerDeckTwo(double availableLowerDeckTwo) {
		this.availableLowerDeckTwo = availableLowerDeckTwo;
	}
	public double getAvailableUpperDeckOne() {
		return availableUpperDeckOne;
	}
	public void setAvailableUpperDeckOne(double availableUpperDeckOne) {
		this.availableUpperDeckOne = availableUpperDeckOne;
	}
	public double getAvailableUpperDeckTwo() {
		return availableUpperDeckTwo;
	}
	public void setAvailableUpperDeckTwo(double availableUpperDeckTwo) {
		this.availableUpperDeckTwo = availableUpperDeckTwo;
	}
	public double getUldWeight() {
		return uldWeight;
	}
	public void setUldWeight(double uldWeight) {
		this.uldWeight = uldWeight;
	}
	public double getManifestWeight() {
		return manifestWeight;
	}
	public void setManifestWeight(double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	public Measure getAircraftWeightCapacity() {
		return aircraftWeightCapacity;
	}
	public void setAircraftWeightCapacity(Measure aircraftWeightCapacity) {
		this.aircraftWeightCapacity = aircraftWeightCapacity;
	}
	public String getFlightMileStoneStatus() {
		return flightMileStoneStatus;
	}
	public void setFlightMileStoneStatus(String flightMileStoneStatus) {
		this.flightMileStoneStatus = flightMileStoneStatus;
	}
	public boolean isStationGroupAllotment() {
		return isStationGroupAllotment;
	}
	public void setStationGroupAllotment(boolean isStationGroupAllotment) {
		this.isStationGroupAllotment = isStationGroupAllotment;
	}
	public Measure getTotalChargeableWeight() {
		return totalChargeableWeight;
	}
	public void setTotalChargeableWeight(Measure totalChargeableWeight) {
		this.totalChargeableWeight = totalChargeableWeight;
	}
	public Collection<FlightSegmentCapacitySummary> getAllotments() {
		return allotments;
	}
	public void setAllotments(Collection<FlightSegmentCapacitySummary> allotments) {
		this.allotments = allotments;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public String getAircraftClassification() {
		return aircraftClassification;
	}
	public void setAircraftClassification(String aircraftClassification) {
		this.aircraftClassification = aircraftClassification;
	}
	public Measure getReleasedWeight() {
		return releasedWeight;
	}
	public void setReleasedWeight(Measure releasedWeight) {
		this.releasedWeight = releasedWeight;
	}
	public Measure getReleasedVolume() {
		return releasedVolume;
	}
	public void setReleasedVolume(Measure releasedVolume) {
		this.releasedVolume = releasedVolume;
	}
	public double getReleasedUpperDeckOne() {
		return releasedUpperDeckOne;
	}
	public void setReleasedUpperDeckOne(double releasedUpperDeckOne) {
		this.releasedUpperDeckOne = releasedUpperDeckOne;
	}
	public double getReleasedUpperDeckTwo() {
		return releasedUpperDeckTwo;
	}
	public void setReleasedUpperDeckTwo(double releasedUpperDeckTwo) {
		this.releasedUpperDeckTwo = releasedUpperDeckTwo;
	}
	public double getReleasedLowerDeckOne() {
		return releasedLowerDeckOne;
	}
	public void setReleasedLowerDeckOne(double releasedLowerDeckOne) {
		this.releasedLowerDeckOne = releasedLowerDeckOne;
	}
	public double getReleasedLowerDeckTwo() {
		return releasedLowerDeckTwo;
	}
	public void setReleasedLowerDeckTwo(double releasedLowerDeckTwo) {
		this.releasedLowerDeckTwo = releasedLowerDeckTwo;
	}
	public Measure getTotalFreesaleWeight() {
		return totalFreesaleWeight;
	}
	public void setTotalFreesaleWeight(Measure totalFreesaleWeight) {
		this.totalFreesaleWeight = totalFreesaleWeight;
	}
	public Measure getTotalFreesaleVolume() {
		return totalFreesaleVolume;
	}
	public void setTotalFreesaleVolume(Measure totalFreesaleVolume) {
		this.totalFreesaleVolume = totalFreesaleVolume;
	}
	public Measure getTotalUtilised() {
		return TotalUtilised;
	}
	public void setTotalUtilised(Measure totalUtilised) {
		TotalUtilised = totalUtilised;
	}
	public Measure getMailCapacity() {
		return mailCapacity;
	}
	public void setMailCapacity(Measure mailCapacity) {
		this.mailCapacity = mailCapacity;
	}
	public Measure getMailUtised() {
		return mailUtised;
	}
	public void setMailUtised(Measure mailUtised) {
		this.mailUtised = mailUtised;
	}
	public Measure getCargoCapacity() {
		return cargoCapacity;
	}
	public void setCargoCapacity(Measure cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}
	public Measure getCargoUtilised() {
		return cargoUtilised;
	}
	public void setCargoUtilised(Measure cargoUtilised) {
		this.cargoUtilised = cargoUtilised;
	}
	  
      
}
