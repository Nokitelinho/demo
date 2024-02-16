package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class Mailbag implements Serializable{

	 //this ID field is used to enable search in React table component
	  private long id;
	  private String mailbagId;
	  private String despatchId;
	  private boolean isMailUpdateFlag;
	  private String ooe;
	  private String doe;
	  private String mailCategoryCode;
	  private String mailSubclass;
	  private String mailClass;
	  private String fromCarrier;
	  private int year;
	  private int count;
	  private String despatchSerialNumber;
	  private String receptacleSerialNumber;
	  private String registeredOrInsuredIndicator;
	  private String highestNumberedReceptacle;
	  private Measure weight;
	  private String scannedUser;
	  private String reassignFlag;
	  private int carrierId;
	  private int fromCarrierId;
	  private String fromFightNumber;
	  private long fromFlightSequenceNumber;
	  private int fromSegmentSerialNumber;
	  private LocalDate fromFlightDate;
	  private String fromContainer;
	  private String fromContainerType;
	  private String flightNumber;
	  private long flightSequenceNumber;
	  private int segmentSerialNumber;
	  private String uldNumber;
	  private String currencyCode;
	  private String mailCompanyCode;
	  private boolean isDespatch;
	  private String reqDeliveryDate;
	  private String reqDeliveryTime;
	  public String shipmentPrefix;
	  private String masterDocumentNumber;
	  private int documentOwnerIdr;
	  private int duplicateNumber;
	  private int sequenceNumber;
	  private double declaredValue;
	  private String carrierCode;
	  private String ownAirlineCode;
	  private String flightDate;
	  private String operationalFlag;
	  private String containerType;
	  private String acceptanceFlag;
	  private String offloadedReason;
	  private String offloadedRemarks;
	  private String offloadedDescription;
	  private String returnedReason;
	  private String returnedRemarks;
	  private String returnedDescription;
	  private String containerNumber;
	  private String consignmentNumber;
	  private String consignmentDate;
	  private String accepted;
	  private Measure volume;
	  private String transferFromCarrier;
	  private String sealNumber;
	  private String damageFlag;
	  private String paCode;
	  private String destinationCode;
	  private int acceptedBagCount;
	  private Measure acceptedWeight;
	  
	  private String companyCode;
	  private long mailSequenceNumber;
	  private boolean isoffload;
	  private String scannedDate;
	  private String scannedTime;
      private String scannedPort;
	  private String latestStatus;
	  private String operationalStatus;
	  private int consignmentSequenceNumber;
      private String lastUpdateTime;
	  private String lastUpdateUser;
	  private String mailStatus;
	  private String latestScannedDate;
		
	  private String displayUnit;
	  private String mailRemarks;
	  private String pou;
	  private String pol;
	  private int legSerialNumber;
	  private String upliftAirport;
	  private String mailorigin;
	  private String mailDestination;
	  private Collection<MailbagHistory> mailbagHistories;
	  private String originAirport;
	  private String  destinatonAirport;
	  private String servicelevel;	   
	private String onTimeDelivery;
		
	private Collection<DamagedMailbag> damagedMailbags;
	//Added as the excel view cannot use Measure weight
	private String mailbagWeight;
	private String mailbagVolume;
	private String operationFlag;
	private String bellyCartId;
	  
	private double actualWeight;
	private Measure actualWeightMeasure;
	private String arrivedFlag;
	private String deliveredFlag;
	private double displayWeight;
	private String reqDeliveryDateAndTime;
	private String actDeliveryDateAndTime;
	//Added by A-8893 for ICRD-338285
	private String actualWeightUnit;
	
	private String deliveryRemarks;
	private String carditPresent;
	private String finalDestination;
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	//To incoporate existingMailbag in Mailbag model itself
	private String currentAirport;
	private String  flightStatus;
	private String reassign;
	private String paBuiltFlag;
	private String mraStatus;
	private String routingAvlFlag;
	
	public String getMraStatus() {
		return mraStatus;
	}
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
	}
	private String carrier;
	private String fromPanel;
	private String transportSrvWindow;
	private String deviationErrors;
	private String mailbagDataSource;
	private String acceptancePostalContainerNumber;
	private String acceptancePostalAirportCode;
	private boolean paBuiltFlagUpdate;
	private boolean paContainerNumberUpdate;
	private boolean originUpdate;
	private boolean destinationUpdate;
	private String latValidationNeeded;
	private boolean importMailbag;

	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public String getDespatchId() {
		return despatchId;
	}
	public void setDespatchId(String despatchId) {
		this.despatchId = despatchId;
	}
	public boolean isMailUpdateFlag() {
		return isMailUpdateFlag;
	}
	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}
	public String getOoe() {
		return ooe;
	}
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
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
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public String getFromCarrier() {
		return fromCarrier;
	}
	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
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
	public Measure getWeight() {
		return weight;
	}
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	public String getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public String getScannedUser() {
		return scannedUser;
	}
	public void setScannedUser(String scannedUser) {
		this.scannedUser = scannedUser;
	}
	public String getReassignFlag() {
		return reassignFlag;
	}
	public void setReassignFlag(String reassignFlag) {
		this.reassignFlag = reassignFlag;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public int getFromCarrierId() {
		return fromCarrierId;
	}
	public void setFromCarrierId(int fromCarrierId) {
		this.fromCarrierId = fromCarrierId;
	}
	public String getFromFightNumber() {
		return fromFightNumber;
	}
	public void setFromFightNumber(String fromFightNumber) {
		this.fromFightNumber = fromFightNumber;
	}
	public long getFromFlightSequenceNumber() {
		return fromFlightSequenceNumber;
	}
	public void setFromFlightSequenceNumber(long fromFlightSequenceNumber) {
		this.fromFlightSequenceNumber = fromFlightSequenceNumber;
	}
	public int getFromSegmentSerialNumber() {
		return fromSegmentSerialNumber;
	}
	public void setFromSegmentSerialNumber(int fromSegmentSerialNumber) {
		this.fromSegmentSerialNumber = fromSegmentSerialNumber;
	}
	public LocalDate getFromFlightDate() {
		return fromFlightDate;
	}
	public void setFromFlightDate(LocalDate fromFlightDate) {
		this.fromFlightDate = fromFlightDate;
	}
	public String getFromContainer() {
		return fromContainer;
	}
	public void setFromContainer(String fromContainer) {
		this.fromContainer = fromContainer;
	}
	public String getFromContainerType() {
		return fromContainerType;
	}
	public void setFromContainerType(String fromContainerType) {
		this.fromContainerType = fromContainerType;
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
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	public boolean isDespatch() {
		return isDespatch;
	}
	public void setDespatch(boolean isDespatch) {
		this.isDespatch = isDespatch;
	}
	public String getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	public void setReqDeliveryTime(String reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
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
	public int getDocumentOwnerIdr() {
		return documentOwnerIdr;
	}
	public void setDocumentOwnerIdr(int documentOwnerIdr) {
		this.documentOwnerIdr = documentOwnerIdr;
	}
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getOperationalFlag() {
		return operationalFlag;
	}
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}
	public String getOffloadedReason() {
		return offloadedReason;
	}
	public void setOffloadedReason(String offloadedReason) {
		this.offloadedReason = offloadedReason;
	}
	public String getOffloadedRemarks() {
		return offloadedRemarks;
	}
	public void setOffloadedRemarks(String offloadedRemarks) {
		this.offloadedRemarks = offloadedRemarks;
	}
	public String getOffloadedDescription() {
		return offloadedDescription;
	}
	public void setOffloadedDescription(String offloadedDescription) {
		this.offloadedDescription = offloadedDescription;
	}
	public String getReturnedReason() {
		return returnedReason;
	}
	public void setReturnedReason(String returnedReason) {
		this.returnedReason = returnedReason;
	}
	public String getReturnedRemarks() {
		return returnedRemarks;
	}
	public void setReturnedRemarks(String returnedRemarks) {
		this.returnedRemarks = returnedRemarks;
	}
	public String getReturnedDescription() {
		return returnedDescription;
	}
	public void setReturnedDescription(String returnedDescription) {
		this.returnedDescription = returnedDescription;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public Measure getVolume() {
		return volume;
	}
	public void setVolume(Measure volume) {
		this.volume = volume;
	}
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public String getDamageFlag() {
		return damageFlag;
	}
	public void setDamageFlag(String damageFlag) {
		this.damageFlag = damageFlag;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDestinationCode() {
		return destinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	public int getAcceptedBagCount() {
		return acceptedBagCount;
	}
	public void setAcceptedBagCount(int acceptedBagCount) {
		this.acceptedBagCount = acceptedBagCount;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public boolean isIsoffload() {
		return isoffload;
	}
	public void setIsoffload(boolean isoffload) {
		this.isoffload = isoffload;
	}
	public String getScannedDate() {
		return scannedDate;
	}
	public void setScannedDate(String scannedDate) {
		this.scannedDate = scannedDate;
	}
	public String getScannedTime() {
		return scannedTime;
	}
	public void setScannedTime(String scannedTime) {
		this.scannedTime = scannedTime;
	}
	public String getScannedPort() {
		return scannedPort;
	}
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}
	public String getLatestStatus() {
		return latestStatus;
	}
	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}
	public String getOperationalStatus() {
		return operationalStatus;
	}
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	public String getLatestScannedDate() {
		return latestScannedDate;
	}
	public void setLatestScannedDate(String latestScannedDate) {
		this.latestScannedDate = latestScannedDate;
	}
	public String getDisplayUnit() {
		return displayUnit;
	}
	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}
	public String getMailRemarks() {
		return mailRemarks;
	}
	public void setMailRemarks(String mailRemarks) {
		this.mailRemarks = mailRemarks;
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
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public Collection<DamagedMailbag> getDamagedMailbags() {
		return damagedMailbags;
	}
	public void setDamagedMailbags(Collection<DamagedMailbag> damagedMailbags) {
		this.damagedMailbags = damagedMailbags;
	}
	public String getUpliftAirport() {
		return upliftAirport;
	}
	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	public Collection<MailbagHistory> getMailbagHistories() {
		return mailbagHistories;
	}
	public void setMailbagHistories(Collection<MailbagHistory> mailbagHistories) {
		this.mailbagHistories = mailbagHistories;
	}
	public String getReqDeliveryDate() {
		return reqDeliveryDate;
	}
	public void setReqDeliveryDate(String reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}
	public String getMailorigin() {
		return mailorigin;
	}
	public void setMailorigin(String mailorigin) {
		this.mailorigin = mailorigin;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getMailbagWeight() {
		return mailbagWeight;
	}
	public void setMailbagWeight(String mailbagWeight) {
		this.mailbagWeight = mailbagWeight;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getMailbagVolume() {
		return mailbagVolume;
	}
	public void setMailbagVolume(String mailbagVolume) {
		this.mailbagVolume = mailbagVolume;
	}
	public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
	public String getBellyCartId() {
		return bellyCartId;
	}
	public void setBellyCartId(String bellyCartId) {
		this.bellyCartId = bellyCartId;
	}
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}  
		public String getOriginAirport() {
		return originAirport;
	}
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}
	public String getDestinatonAirport() {
		return destinatonAirport;
	}
	public void setDestinatonAirport(String destinatonAirport) {
		this.destinatonAirport = destinatonAirport;
	}
	public String getServicelevel() {
		return servicelevel;
	}
	public void setServicelevel(String servicelevel) {
		this.servicelevel = servicelevel;
	}
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}
	/**
	 * @return the arrivedFlag
	 */
	public String getArrivedFlag() {
		return arrivedFlag;
	}
	/**
	 * @param arrivedFlag the arrivedFlag to set
	 */
	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}
	/**
	 * @return the deliveredFlag
	 */
	public String getDeliveredFlag() {
		return deliveredFlag;
	}
	/**
	 * @param deliveredFlag the deliveredFlag to set
	 */
	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}
	/**
	 * @return the displayWeight
	 */
	public double getDisplayWeight() {
		return displayWeight;
	}
	/**
	 * @param displayWeight the displayWeight to set
	 */
	public void setDisplayWeight(double displayWeight) {
		this.displayWeight = displayWeight;
	}
	public Measure getActualWeightMeasure() {
		return actualWeightMeasure;
	}
	public void setActualWeightMeasure(Measure actualWeightMeasure) {
		this.actualWeightMeasure = actualWeightMeasure;
	}
	/**
	 * @return the reqDeliveryDateAndTime
	 */
	public String getReqDeliveryDateAndTime() {
		return reqDeliveryDateAndTime;
	}
	/**
	 * @param reqDeliveryDateAndTime the reqDeliveryDateAndTime to set
	 */
	public void setReqDeliveryDateAndTime(String reqDeliveryDateAndTime) {
		this.reqDeliveryDateAndTime = reqDeliveryDateAndTime;
	}
	public String getCarditPresent() {
		return carditPresent;
	}
	public void setCarditPresent(String carditPresent) {
		this.carditPresent = carditPresent;
	}
	public String getActualWeightUnit() {
		return actualWeightUnit;
	}
	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	
	
	  
	/**
	 * @return the actDeliveryDateAndTime
	 */
	public String getActDeliveryDateAndTime() {
		return actDeliveryDateAndTime;
	}
	/**
	 * @param actDeliveryDateAndTime the actDeliveryDateAndTime to set
	 */
	public void setActDeliveryDateAndTime(String actDeliveryDateAndTime) {
		this.actDeliveryDateAndTime = actDeliveryDateAndTime;
	}	  
	public String getFinalDestination() {
		return finalDestination;
	}
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	public String getUbrNumber() {
		return ubrNumber;
	}
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}
	public void setBookingFlightDetailLastUpdTime(LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}
	public String getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(String currentAirport) {
		this.currentAirport = currentAirport;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public String getReassign() {
		return reassign;
	}
	public void setReassign(String reassign) {
		this.reassign = reassign;
	}
	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}
	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	/**
	 * @return the fromPanel
	 */
	public String getFromPanel() {
		return fromPanel;
	}
	/**
	 * @param fromPanel the fromPanel to set
	 */
	public void setFromPanel(String fromPanel) {
		this.fromPanel = fromPanel;
	}
	public String getTransportSrvWindow() {
		return transportSrvWindow;
	}
	public void setTransportSrvWindow(String transportSrvWindow) {
		this.transportSrvWindow = transportSrvWindow;
	}
	public String getDeviationErrors() {
		return deviationErrors;
	}
	public void setDeviationErrors(String deviationErrors) {
		this.deviationErrors = deviationErrors;
	}
	public String getDeliveryRemarks() {
		return deliveryRemarks;
	}
	public void setDeliveryRemarks(String deliveryRemarks) {
		this.deliveryRemarks = deliveryRemarks;
	}
	public String getMailbagDataSource() {
		return mailbagDataSource;
	}
	public void setMailbagDataSource(String mailbagDataSource) {
		this.mailbagDataSource = mailbagDataSource;
	}
	public String getAcceptancePostalContainerNumber() {
		return acceptancePostalContainerNumber;
	}
	public void setAcceptancePostalContainerNumber(String acceptancePostalContainerNumber) {
		this.acceptancePostalContainerNumber = acceptancePostalContainerNumber;
	}
	public boolean isPaBuiltFlagUpdate() {
		return paBuiltFlagUpdate;
	}
	public void setPaBuiltFlagUpdate(boolean paBuiltFlagUpdate) {
		this.paBuiltFlagUpdate = paBuiltFlagUpdate;
	}
	public boolean isPaContainerNumberUpdate() {
		return paContainerNumberUpdate;
	}
	public void setPaContainerNumberUpdate(boolean paContainerNumberUpdate) {
		this.paContainerNumberUpdate = paContainerNumberUpdate;
	}
	public String getAcceptancePostalAirportCode() {
		return acceptancePostalAirportCode;
	}
	public void setAcceptancePostalAirportCode(String acceptancePostalAirportCode) {
		this.acceptancePostalAirportCode = acceptancePostalAirportCode;
	}
	public boolean isOriginUpdate() {
		return originUpdate;
	}
	public void setOriginUpdate(boolean originUpdate) {
		this.originUpdate = originUpdate;
	}
	public boolean isDestinationUpdate() {
		return destinationUpdate;
	}
	public void setDestinationUpdate(boolean destinationUpdate) {
		this.destinationUpdate = destinationUpdate;
	}
	public String getRoutingAvlFlag() {
		return routingAvlFlag;
	}
	public void setRoutingAvlFlag(String routingAvlFlag) {
		this.routingAvlFlag = routingAvlFlag;
	}
	public String getLatValidationNeeded() {
		return latValidationNeeded;
	}
	public void setLatValidationNeeded(String latValidationNeeded) {
		this.latValidationNeeded = latValidationNeeded;
	}
	public boolean isImportMailbag() {
		return importMailbag;
	}
	public void setImportMailbag(boolean importMailbag) {
		this.importMailbag = importMailbag;
	}
	
}
