package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.unit.Measure;

public class ConsignmentRouting {

	private String pou;
	private String onwardFlightNumber;
	private String onwardFlightDate;
	private String onwardCarrierCode;
	private int onwardCarrierId;
	private long onwardCarrierSeqNum;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private int routingSerialNumber;
	private String operationFlag;
	private String pol;
	private String isDuplicateFlightChecked;
	private int noOfPieces;
	private Measure weight;
	private int segmentSerialNumber;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private int legSerialNumber;
	private int previousNoOfPieces;
	private Measure previousWeight;
	private boolean invalidFlightFlag;
	private boolean acceptanceFlag;
	private String dsn;
	private String mailClass;
	private boolean offloadFlag;
	private boolean isFlightClosed;
	private String remarks;
	private int RecievedNoOfPieces;
	private Measure RecievedWeight;	
	private String transportStageQualifier;
	
	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
	}

	public String getOnwardFlightDate() {
		return onwardFlightDate;
	}

	public void setOnwardFlightDate(String onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}

	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}

	public void setOnwardCarrierCode(String onwardCarrierCode) {
		this.onwardCarrierCode = onwardCarrierCode;
	}

	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}

	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}

	public long getOnwardCarrierSeqNum() {
		return onwardCarrierSeqNum;
	}

	public void setOnwardCarrierSeqNum(long onwardCarrierSeqNum) {
		this.onwardCarrierSeqNum = onwardCarrierSeqNum;
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

	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}

	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getIsDuplicateFlightChecked() {
		return isDuplicateFlightChecked;
	}

	public void setIsDuplicateFlightChecked(String isDuplicateFlightChecked) {
		this.isDuplicateFlightChecked = isDuplicateFlightChecked;
	}

	public int getNoOfPieces() {
		return noOfPieces;
	}

	public void setNoOfPieces(int noOfPieces) {
		this.noOfPieces = noOfPieces;
	}

	public Measure getWeight() {
		return weight;
	}

	public void setWeight(Measure weight) {
		this.weight = weight;
	}

	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	public String getMailSubClass() {
		return mailSubClass;
	}

	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public int getPreviousNoOfPieces() {
		return previousNoOfPieces;
	}

	public void setPreviousNoOfPieces(int previousNoOfPieces) {
		this.previousNoOfPieces = previousNoOfPieces;
	}

	public Measure getPreviousWeight() {
		return previousWeight;
	}

	public void setPreviousWeight(Measure previousWeight) {
		this.previousWeight = previousWeight;
	}

	public boolean isInvalidFlightFlag() {
		return invalidFlightFlag;
	}

	public void setInvalidFlightFlag(boolean invalidFlightFlag) {
		this.invalidFlightFlag = invalidFlightFlag;
	}

	public boolean isAcceptanceFlag() {
		return acceptanceFlag;
	}

	public void setAcceptanceFlag(boolean acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public boolean isOffloadFlag() {
		return offloadFlag;
	}

	public void setOffloadFlag(boolean offloadFlag) {
		this.offloadFlag = offloadFlag;
	}

	public boolean isFlightClosed() {
		return isFlightClosed;
	}

	public void setFlightClosed(boolean isFlightClosed) {
		this.isFlightClosed = isFlightClosed;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getRecievedNoOfPieces() {
		return RecievedNoOfPieces;
	}

	public void setRecievedNoOfPieces(int recievedNoOfPieces) {
		RecievedNoOfPieces = recievedNoOfPieces;
	}

	public Measure getRecievedWeight() {
		return RecievedWeight;
	}

	public void setRecievedWeight(Measure recievedWeight) {
		RecievedWeight = recievedWeight;
	}

	public String getTransportStageQualifier() {
		return transportStageQualifier;
	}

	public void setTransportStageQualifier(String transportStageQualifier) {
		this.transportStageQualifier = transportStageQualifier;
	}
}
