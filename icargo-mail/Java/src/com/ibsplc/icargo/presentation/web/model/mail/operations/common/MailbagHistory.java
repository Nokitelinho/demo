package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class MailbagHistory {

	private String companyCode; 

	    private String dsn;

	    private String originExchangeOffice;

	    private String destinationExchangeOffice;

	    private String mailClass;
	    

	    private int year;

	    private String mailbagId;

	    private String mailStatus;

	    private int historySequenceNumber;

	    private LocalDate scanDate;

	    private String scanUser;

	    private String flightNumber;
	    private int carrierId;
	    private long flightSequenceNumber;
	    private int segmentSerialNumber;

	    private String scannedPort;
	    private String containerNumber;
	    private String containerType;
	    private String carrierCode;
	    private LocalDate flightDate;
	    private String pou;
	    private LocalDate utcScandate;
	   //Added to include the DSN PK 
	     private String mailCategoryCode;
		private String mailSubclass;

		private LocalDate messageTime;
		private String messageTimeDisplay;
		
		private String paBuiltFlag;
		
		private String interchangeControlReference;
		
		private LocalDate messageTimeUTC;
		
		private String mailSource;//Added for ICRD-156218
		
	    private  LocalDate eventDate;


		private  LocalDate utcEventDate;

		private String residitFailReasonCode;
		private String residitSend;
		private String eventCode;
		private String processedStatus;
		private String mailBoxId;
		private String carditKey;
		private String paCarrierCode;
		private int messageSequenceNumber;	
		//Added for ICRD-205027 starts
		private String rsn;
		//private double weight;
		private Measure weight;//added by A-7371
		//Added for ICRD-205027 ends
		//Added for ICRD-214795 starts
		private LocalDate reqDeliveryTime;
		//Added as a part of ICRD-197419 
		private String mailRemarks;
		//Added for ICRD-245211 starts
		private String masterDocumentNumber;
		private String malClass;
		private String malType;
		private String origin;
		private String destination;
		private int pieces;	
		private String airportCode;
		private String deliveryStatus;
		private String firstFlight;
		private String secondFlight;
		//Added for ICRD-245211 ends
		private String onTimeDelivery;//Added for ICRD-243421
		private String additionalInfo;//Added by a-7871 for ICRD-240184
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
		public String getMailbagId() {
			return mailbagId;
		}
		public void setMailbagId(String mailbagId) {
			this.mailbagId = mailbagId;
		}
		public String getMailStatus() {
			return mailStatus;
		}
		public void setMailStatus(String mailStatus) {
			this.mailStatus = mailStatus;
		}
		public int getHistorySequenceNumber() {
			return historySequenceNumber;
		}
		public void setHistorySequenceNumber(int historySequenceNumber) {
			this.historySequenceNumber = historySequenceNumber;
		}
		public LocalDate getScanDate() {
			return scanDate;
		}
		public void setScanDate(LocalDate scanDate) {
			this.scanDate = scanDate;
		}
		public String getScanUser() {
			return scanUser;
		}
		public void setScanUser(String scanUser) {
			this.scanUser = scanUser;
		}
		public String getFlightNumber() {
			return flightNumber;
		}
		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}
		public int getCarrierId() {
			return carrierId;
		}
		public void setCarrierId(int carrierId) {
			this.carrierId = carrierId;
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
		public String getScannedPort() {
			return scannedPort;
		}
		public void setScannedPort(String scannedPort) {
			this.scannedPort = scannedPort;
		}
		public String getContainerNumber() {
			return containerNumber;
		}
		public void setContainerNumber(String containerNumber) {
			this.containerNumber = containerNumber;
		}
		public String getContainerType() {
			return containerType;
		}
		public void setContainerType(String containerType) {
			this.containerType = containerType;
		}
		public String getCarrierCode() {
			return carrierCode;
		}
		public void setCarrierCode(String carrierCode) {
			this.carrierCode = carrierCode;
		}
		public LocalDate getFlightDate() {
			return flightDate;
		}
		public void setFlightDate(LocalDate flightDate) {
			this.flightDate = flightDate;
		}
		public String getPou() {
			return pou;
		}
		public void setPou(String pou) {
			this.pou = pou;
		}
		public LocalDate getUtcScandate() {
			return utcScandate;
		}
		public void setUtcScandate(LocalDate utcScandate) {
			this.utcScandate = utcScandate;
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
		public LocalDate getMessageTime() {
			return messageTime;
		}
		public void setMessageTime(LocalDate messageTime) {
			this.messageTime = messageTime;
		}
		public String getPaBuiltFlag() {
			return paBuiltFlag;
		}
		public void setPaBuiltFlag(String paBuiltFlag) {
			this.paBuiltFlag = paBuiltFlag;
		}
		public String getInterchangeControlReference() {
			return interchangeControlReference;
		}
		public void setInterchangeControlReference(String interchangeControlReference) {
			this.interchangeControlReference = interchangeControlReference;
		}
		public LocalDate getMessageTimeUTC() {
			return messageTimeUTC;
		}
		public void setMessageTimeUTC(LocalDate messageTimeUTC) {
			this.messageTimeUTC = messageTimeUTC;
		}
		public String getMailSource() {
			return mailSource;
		}
		public void setMailSource(String mailSource) {
			this.mailSource = mailSource;
		}
		public LocalDate getEventDate() {
			return eventDate;
		}
		public void setEventDate(LocalDate eventDate) {
			this.eventDate = eventDate;
		}
		public LocalDate getUtcEventDate() {
			return utcEventDate;
		}
		public void setUtcEventDate(LocalDate utcEventDate) {
			this.utcEventDate = utcEventDate;
		}
		public String getResiditFailReasonCode() {
			return residitFailReasonCode;
		}
		public void setResiditFailReasonCode(String residitFailReasonCode) {
			this.residitFailReasonCode = residitFailReasonCode;
		}
		public String getResiditSend() {
			return residitSend;
		}
		public void setResiditSend(String residitSend) {
			this.residitSend = residitSend;
		}
		public String getEventCode() {
			return eventCode;
		}
		public void setEventCode(String eventCode) {
			this.eventCode = eventCode;
		}
		public String getProcessedStatus() {
			return processedStatus;
		}
		public void setProcessedStatus(String processedStatus) {
			this.processedStatus = processedStatus;
		}
		public String getMailBoxId() {
			return mailBoxId;
		}
		public void setMailBoxId(String mailBoxId) {
			this.mailBoxId = mailBoxId;
		}
		public String getCarditKey() {
			return carditKey;
		}
		public void setCarditKey(String carditKey) {
			this.carditKey = carditKey;
		}
		public String getPaCarrierCode() {
			return paCarrierCode;
		}
		public void setPaCarrierCode(String paCarrierCode) {
			this.paCarrierCode = paCarrierCode;
		}
		public int getMessageSequenceNumber() {
			return messageSequenceNumber;
		}
		public void setMessageSequenceNumber(int messageSequenceNumber) {
			this.messageSequenceNumber = messageSequenceNumber;
		}
		public String getRsn() {
			return rsn;
		}
		public void setRsn(String rsn) {
			this.rsn = rsn;
		}
		public Measure getWeight() {
			return weight;
		}
		public void setWeight(Measure weight) {
			this.weight = weight;
		}
		public LocalDate getReqDeliveryTime() {
			return reqDeliveryTime;
		}
		public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
			this.reqDeliveryTime = reqDeliveryTime;
		}
		public String getMailRemarks() {
			return mailRemarks;
		}
		public void setMailRemarks(String mailRemarks) {
			this.mailRemarks = mailRemarks;
		}
		public String getMasterDocumentNumber() {
			return masterDocumentNumber;
		}
		public void setMasterDocumentNumber(String masterDocumentNumber) {
			this.masterDocumentNumber = masterDocumentNumber;
		}
		public String getMalClass() {
			return malClass;
		}
		public void setMalClass(String malClass) {
			this.malClass = malClass;
		}
		public String getMalType() {
			return malType;
		}
		public void setMalType(String malType) {
			this.malType = malType;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public int getPieces() {
			return pieces;
		}
		public void setPieces(int pieces) {
			this.pieces = pieces;
		}
		public String getAirportCode() {
			return airportCode;
		}
		public void setAirportCode(String airportCode) {
			this.airportCode = airportCode;
		}
		public String getDeliveryStatus() {
			return deliveryStatus;
		}
		public void setDeliveryStatus(String deliveryStatus) {
			this.deliveryStatus = deliveryStatus;
		}
		public String getFirstFlight() {
			return firstFlight;
		}
		public void setFirstFlight(String firstFlight) {
			this.firstFlight = firstFlight;
		}
		public String getSecondFlight() {
			return secondFlight;
		}
		public void setSecondFlight(String secondFlight) {
			this.secondFlight = secondFlight;
		}
		public String getOnTimeDelivery() {
			return onTimeDelivery;
		}
		public void setOnTimeDelivery(String onTimeDelivery) {
			this.onTimeDelivery = onTimeDelivery;
		}
		public String getAdditionalInfo() {
			return additionalInfo;
		}
		public void setAdditionalInfo(String additionalInfo) {
			this.additionalInfo = additionalInfo;
		}
		public String getMessageTimeDisplay() {
			return messageTimeDisplay;
		}
		public void setMessageTimeDisplay(String messageTimeDisplay) {
			this.messageTimeDisplay = messageTimeDisplay;
		}
		
}
