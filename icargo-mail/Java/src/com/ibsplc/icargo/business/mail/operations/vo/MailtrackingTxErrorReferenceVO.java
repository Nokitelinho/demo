package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.xibase.server.framework.tx.audit.vo.TxErrorReferenceVO;
public class MailtrackingTxErrorReferenceVO extends TxErrorReferenceVO {

	
	private String mailbagId;
	private String function;
	private String container;
	private String carrierCode;
	private String flightnumber;
	private String flightDate;
	private String remarks;
	private String resolutionStatus;
	private String scannedDateAndTime;
	private String lastUpdateDateAndTime;
	//Added by A-5945 for ICRD-85550 starts
	private String airportCode;
	//Added by A-5945 for ICRD-85550 ends
	//Added by A-5945 for ICRd-113473 starts
	private String transferCarrier;
	//Added by A-5945 for ICRD-113473 ends
	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getFlightnumber() {
		return flightnumber;
	}

	public void setFlightnumber(String flightnumber) {
		this.flightnumber = flightnumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResolutionStatus() {
		return resolutionStatus;
	}

	public void setResolutionStatus(String resolutionStatus) {
		this.resolutionStatus = resolutionStatus;
	}



	public String getScannedDateAndTime() {
		return scannedDateAndTime;
	}

	public void setScannedDateAndTime(String scannedDateAndTime) {
		this.scannedDateAndTime = scannedDateAndTime;
	}

	public String getLastUpdateDateAndTime() {
		return lastUpdateDateAndTime;
	}

	public void setLastUpdateDateAndTime(String lastUpdateDateAndTime) {
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	
	
	
	public String getReferenceOne() {
		return mailbagId;
	}

	public String getReferenceTwo() {
		return function;
	}

	public String getReferenceThree() {
		return container;
	}

	public String getReferenceFour() {
		return carrierCode;
	}

	public String getReferenceFive() {
		return flightnumber;
	}

	public String getReferenceSix() {
		return flightDate;
	}

	public String getReferenceSeven() {
		return remarks;
	}

	public String getReferenceEight() {
		return airportCode;
	}

	public String getReferenceNine() {
		return scannedDateAndTime;
	}
//Changed by A-5945 for ICRD-113473
	public String getReferenceTen() {
		return transferCarrier;
	}
	//Added by A-5945 for ICRD-85550 starts
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportCode() {
		return airportCode;
	}
	//Added by A-5945 for ICRD-85550 ends
//Added by A-5945 for ICRD-113473 starts
	/**
	 * @return the transferCarrier
	 */
	public String getTransferCarrier() {
		return transferCarrier;
	}
	/**
	 * @param transferCarrier the transferCarrier to set
	 */
	public void setTransferCarrier(String transferCarrier) {
		this.transferCarrier = transferCarrier;
	}
	//Added by A-5945 for ICRd-113473 ends
	
}
