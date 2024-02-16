package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

/**
 * @author A-7929
 *
 */
public class OffloadDetails {

	private String containerNo;
	private String pou;
	private String destination;
	private String acceptedWeight;
	private String acceptedBags;
	private String offloadReason;
	private String remarks;

	private String dsn;
	private String ooe;
	private String doe;
	private String mailClass;
	private String subClass;
	private String year;
	private String consignmentNo;

	private String mailbagId;
	private String lastUpdateTime;
	private String lastUpdateUser;

	private int legSerialNumber;
	private long flightSequenceNumber;
	private String pol;
	private String carrierCode;	
	private int carrierId;
	private String flightNumber;
	private String flightDate;
	private String flightCarrierCode;
	private int segmentSerialNumber;
	private String type; 
	private String containerType;
	private String assignedPort;
	private String acceptanceFlag;
	private String finalDestination;
	private String latestStatus;
	private String mailCategoryCode;
    private String operationalStatus;
    private String paBuildFlag;
    private String pacode;
    private String scannedPort;
    private String uldFulIndFlag;
    private String actWgtSta;
    
	
	public String getScannedPort() {
		return scannedPort;
	}

	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}

	public String getPaBuildFlag() {
		return paBuildFlag;
	}

	public void setPaBuildFlag(String paBuildFlag) {
		this.paBuildFlag = paBuildFlag;
	}

	public String getPacode() {
		return pacode;
	}

	public void setPacode(String pacode) {
		this.pacode = pacode;
	}

	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	public String getLatestStatus() {
		return latestStatus;
	}

	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	public String getAssignedPort() {
		return assignedPort;
	}

	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}
	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
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

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}



	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
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

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public String getAcceptedWeight() {
		return acceptedWeight;
	}

	public void setAcceptedWeight(String acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	public String getAcceptedBags() {
		return acceptedBags;
	}

	public void setAcceptedBags(String acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	public String getOffloadReason() {
		return offloadReason;
	}

	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
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
	
	public String getUldFulIndFlag() {
		return uldFulIndFlag;
	}

	public void setUldFulIndFlag(String uldFulIndFlag) {
		this.uldFulIndFlag = uldFulIndFlag;
	}

	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
}
