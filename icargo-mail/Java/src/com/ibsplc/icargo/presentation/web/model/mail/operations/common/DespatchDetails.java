package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class DespatchDetails {
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
	  private int shipmentCount;
	  private Measure shipmentWeight;
	  private Measure shipmentVolume;
	  private int prevBagCount;
	  private Measure prevBagWeight;
	  private int statedBags;
	  private Measure statedWeight;
	  private int prevStatedBags;
	  private Measure prevStatedWeight;
	  private Collection<Mailbag> mailbags;
	 // private Collection<DSNAtAirportVO> dsnAtAirports;
	  private String pltEnableFlag;
	  private String operationFlag;
	  private String containerType;
	  private String acceptanceFlag;
	  private int segmentSerialNumber;
	  private String pou;
	  private String destination;
	  private String carrierCode;
	  private int deliveredBags;
	  private Measure deliveredWeight;
	  private int receivedBags;
	  private Measure receivedWeight;
	  private int prevDeliveredBags;
	  private Measure prevDeliveredWeight;
	  private int prevReceivedBags;
	  private Measure prevReceivedWeight;
	  private int documentOwnerIdentifier;
	  private String masterDocumentNumber;
	  private int duplicateNumber;
	  private int sequenceNumber;
	  private String documentOwnerCode;
	  private String shipmentCode;
	  private String shipmentDescription;
	  private String origin;
	  private String transferFlag;
	  private Collection<String> dsnContainers;
	  private LocalDate dsnUldSegLastUpdateTime;
	  private String containerNumber;
	  private String routingAvl;
	  private String pol;
	  private int carrierId;
	  private String flightNumber;
	  private long flightSequenceNumber;
	  private int legSerialNumber;
	  private double mailrate;
	  private String currencyCode;
	  private double chargeInBase;
	  private double chargeInUSD;
	  private String ubrNumber;
	  private LocalDate bookingLastUpdateTime;
	  private LocalDate bookingFlightDetailLastUpdTime;
	  private Measure acceptedVolume;
	  private Measure statedVolume;
	  private String csgDocNum;
	  private String paCode;
	  private int csgSeqNum;
	  private String consignmentNumber;
	  private LocalDate consignmentDate;
	  private LocalDate acceptedDate;
	  private LocalDate receivedDate;
	  private String currentPort;
	  private int transferredPieces;
	  private Measure transferredWeight;
	  private String remarks;
	  private long mailSequenceNumber;
	  private String awb;
	  private String ooe;
	  private String doe;
	  private String category;
	  private String subClass;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
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
	public int getShipmentCount() {
		return shipmentCount;
	}
	public void setShipmentCount(int shipmentCount) {
		this.shipmentCount = shipmentCount;
	}
	public Measure getShipmentWeight() {
		return shipmentWeight;
	}
	public void setShipmentWeight(Measure shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}
	public Measure getShipmentVolume() {
		return shipmentVolume;
	}
	public void setShipmentVolume(Measure shipmentVolume) {
		this.shipmentVolume = shipmentVolume;
	}
	public int getPrevBagCount() {
		return prevBagCount;
	}
	public void setPrevBagCount(int prevBagCount) {
		this.prevBagCount = prevBagCount;
	}
	public Measure getPrevBagWeight() {
		return prevBagWeight;
	}
	public void setPrevBagWeight(Measure prevBagWeight) {
		this.prevBagWeight = prevBagWeight;
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
	public int getPrevStatedBags() {
		return prevStatedBags;
	}
	public void setPrevStatedBags(int prevStatedBags) {
		this.prevStatedBags = prevStatedBags;
	}
	public Measure getPrevStatedWeight() {
		return prevStatedWeight;
	}
	public void setPrevStatedWeight(Measure prevStatedWeight) {
		this.prevStatedWeight = prevStatedWeight;
	}
	public Collection<Mailbag> getMailbags() {
		return mailbags;
	}
	public void setMailbags(Collection<Mailbag> mailbags) {
		this.mailbags = mailbags;
	}
	
	public String getPltEnableFlag() {
		return pltEnableFlag;
	}
	public void setPltEnableFlag(String pltEnableFlag) {
		this.pltEnableFlag = pltEnableFlag;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getDeliveredBags() {
		return deliveredBags;
	}
	public void setDeliveredBags(int deliveredBags) {
		this.deliveredBags = deliveredBags;
	}
	public Measure getDeliveredWeight() {
		return deliveredWeight;
	}
	public void setDeliveredWeight(Measure deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
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
	public int getPrevDeliveredBags() {
		return prevDeliveredBags;
	}
	public void setPrevDeliveredBags(int prevDeliveredBags) {
		this.prevDeliveredBags = prevDeliveredBags;
	}
	public Measure getPrevDeliveredWeight() {
		return prevDeliveredWeight;
	}
	public void setPrevDeliveredWeight(Measure prevDeliveredWeight) {
		this.prevDeliveredWeight = prevDeliveredWeight;
	}
	public int getPrevReceivedBags() {
		return prevReceivedBags;
	}
	public void setPrevReceivedBags(int prevReceivedBags) {
		this.prevReceivedBags = prevReceivedBags;
	}
	public Measure getPrevReceivedWeight() {
		return prevReceivedWeight;
	}
	public void setPrevReceivedWeight(Measure prevReceivedWeight) {
		this.prevReceivedWeight = prevReceivedWeight;
	}
	public int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}
	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
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
	public String getDocumentOwnerCode() {
		return documentOwnerCode;
	}
	public void setDocumentOwnerCode(String documentOwnerCode) {
		this.documentOwnerCode = documentOwnerCode;
	}
	public String getShipmentCode() {
		return shipmentCode;
	}
	public void setShipmentCode(String shipmentCode) {
		this.shipmentCode = shipmentCode;
	}
	public String getShipmentDescription() {
		return shipmentDescription;
	}
	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}
	public Collection<String> getDsnContainers() {
		return dsnContainers;
	}
	public void setDsnContainers(Collection<String> dsnContainers) {
		this.dsnContainers = dsnContainers;
	}
	public LocalDate getDsnUldSegLastUpdateTime() {
		return dsnUldSegLastUpdateTime;
	}
	public void setDsnUldSegLastUpdateTime(LocalDate dsnUldSegLastUpdateTime) {
		this.dsnUldSegLastUpdateTime = dsnUldSegLastUpdateTime;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getRoutingAvl() {
		return routingAvl;
	}
	public void setRoutingAvl(String routingAvl) {
		this.routingAvl = routingAvl;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
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
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public double getMailrate() {
		return mailrate;
	}
	public void setMailrate(double mailrate) {
		this.mailrate = mailrate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public double getChargeInBase() {
		return chargeInBase;
	}
	public void setChargeInBase(double chargeInBase) {
		this.chargeInBase = chargeInBase;
	}
	public double getChargeInUSD() {
		return chargeInUSD;
	}
	public void setChargeInUSD(double chargeInUSD) {
		this.chargeInUSD = chargeInUSD;
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
	public void setBookingFlightDetailLastUpdTime(
			LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}
	public Measure getAcceptedVolume() {
		return acceptedVolume;
	}
	public void setAcceptedVolume(Measure acceptedVolume) {
		this.acceptedVolume = acceptedVolume;
	}
	public Measure getStatedVolume() {
		return statedVolume;
	}
	public void setStatedVolume(Measure statedVolume) {
		this.statedVolume = statedVolume;
	}
	public String getCsgDocNum() {
		return csgDocNum;
	}
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public int getCsgSeqNum() {
		return csgSeqNum;
	}
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
	}
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	public LocalDate getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(LocalDate acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public LocalDate getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getCurrentPort() {
		return currentPort;
	}
	public void setCurrentPort(String currentPort) {
		this.currentPort = currentPort;
	}
	public int getTransferredPieces() {
		return transferredPieces;
	}
	public void setTransferredPieces(int transferredPieces) {
		this.transferredPieces = transferredPieces;
	}
	public Measure getTransferredWeight() {
		return transferredWeight;
	}
	public void setTransferredWeight(Measure transferredWeight) {
		this.transferredWeight = transferredWeight;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	  
}
