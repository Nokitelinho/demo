/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */
public class RoutingInConsignmentVO extends AbstractVO {

	private String pou;
	private String onwardFlightNumber;
	private LocalDate onwardFlightDate;
	private String onwardCarrierCode;
	private int onwardCarrierId;
	private long onwardCarrierSeqNum;
	// private long flightSequenceNumber;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private int routingSerialNumber;
	private String operationFlag;
	private String pol;
	private String isDuplicateFlightChecked;
	// Added for Indigo CR
	private int noOfPieces;
	//private double weight;
	private Measure weight;//added by A-7371
	private int segmentSerialNumber;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private int legSerialNumber;
	private int previousNoOfPieces;
	//private double previousWeight;
	private Measure previousWeight;//added by A-7371
	private boolean invalidFlightFlag;
	private boolean acceptanceFlag;
	private String dsn;
	private String mailClass;
	private boolean offloadFlag;
	private boolean isFlightClosed;
	private String remarks;
	// Added by A-4810 for bug:icrd-15420
	private int recievedNoOfPieces;
	//private double RecievedWeight;
	private Measure recievedWeight;//added by A-7371
	private LocalDate scheduledArrivalDate;
	private String flightCarrierCode;
	private String polAirportName;
	private String pouAirportName;
	private String transportStageQualifier;
   
   /**
    * 
    * @return weight
    */
	public Measure getWeight() {
		return weight;
	}
   /**
    * 
    * @param weight
    */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
   /**
    * 
    * @return previousWeight
    */
	public Measure getPreviousWeight() {
		return previousWeight;
	}
   /**
    * 
    * @param previousWeight
    */
	public void setPreviousWeight(Measure previousWeight) {
		this.previousWeight = previousWeight;
	}
    /**
     * 
     * @return recievedWeight
     */
	public Measure getRecievedWeight() {
		return recievedWeight;
	}
    /**
     * 
     * @param recievedWeight
     */
	public void setRecievedWeight(Measure recievedWeight) {
		this.recievedWeight = recievedWeight;
	}

	public int getPreviousNoOfPieces() {
		return previousNoOfPieces;
	}

	public void setPreviousNoOfPieces(int previousNoOfPieces) {
		this.previousNoOfPieces = previousNoOfPieces;
	}

	/*public double getPreviousWeight() {
		return previousWeight;
	}

	public void setPreviousWeight(double previousWeight) {
		this.previousWeight = previousWeight;
	}*/

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
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

	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	public void setDestinationOfficeOfExchange(
			String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * @return Returns the onwardCarrierCode.
	 */
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}

	/**
	 * @param onwardCarrierCode
	 *            The onwardCarrierCode to set.
	 */
	public void setOnwardCarrierCode(String onwardCarrierCode) {
		this.onwardCarrierCode = onwardCarrierCode;
	}

	/**
	 * @return Returns the onwardCarrierId.
	 */
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}

	/**
	 * @param onwardCarrierId
	 *            The onwardCarrierId to set.
	 */
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}

	/**
	 * @return Returns the onwardFlightDate.
	 */
	public LocalDate getOnwardFlightDate() {
		return onwardFlightDate;
	}

	/**
	 * @param onwardFlightDate
	 *            The onwardFlightDate to set.
	 */
	public void setOnwardFlightDate(LocalDate onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}

	/**
	 * @return Returns the onwardFlightNumber.
	 */
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	/**
	 * @param onwardFlightNumber
	 *            The onwardFlightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
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
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the routingSerialNumber.
	 */
	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}

	/**
	 * @param routingSerialNumber
	 *            The routingSerialNumber to set.
	 */
	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	public long getOnwardCarrierSeqNum() {
		return onwardCarrierSeqNum;
	}

	public void setOnwardCarrierSeqNum(long onwardCarrierSeqNum) {
		this.onwardCarrierSeqNum = onwardCarrierSeqNum;
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

	/*public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
*/
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

	public boolean isInvalidFlightFlag() {
		return invalidFlightFlag;
	}

	public void setInvalidFlightFlag(boolean invalidFlightFlag) {
		this.invalidFlightFlag = invalidFlightFlag;
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

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the recievedNoOfPieces
	 */
	public int getRecievedNoOfPieces() {
		return recievedNoOfPieces;
	}

	/**
	 * @param recievedNoOfPieces
	 *            the recievedNoOfPieces to set
	 */
	public void setRecievedNoOfPieces(int recievedNoOfPieces) {
		this.recievedNoOfPieces = recievedNoOfPieces;
	}

	/**
	 * @return the recievedWeight
	 */
	/*public double getRecievedWeight() {
		return RecievedWeight;
	}

	*//**
	 * @param recievedWeight
	 *            the recievedWeight to set
	 *//*
	public void setRecievedWeight(double recievedWeight) {
		RecievedWeight = recievedWeight;
	}*/
	
	public LocalDate getScheduledArrivalDate() {
		return scheduledArrivalDate;
	}
	public void setScheduledArrivalDate(LocalDate scheduledArrivalDate) {
		this.scheduledArrivalDate = scheduledArrivalDate;
	}
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * 	Getter for polAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public String getPolAirportName() {
		return polAirportName;
	}
	/**
	 *  @param polAirportName the polAirportName to set
	 * 	Setter for polAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public void setPolAirportName(String polAirportName) {
		this.polAirportName = polAirportName;
	}
	/**
	 * 	Getter for pouAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public String getPouAirportName() {
		return pouAirportName;
	}
	/**
	 *  @param pouAirportName the pouAirportName to set
	 * 	Setter for pouAirportName 
	 *	Added by : A-5219 on 23-Dec-2020
	 * 	Used for :
	 */
	public void setPouAirportName(String pouAirportName) {
		this.pouAirportName = pouAirportName;
	}
	
	public String getTransportStageQualifier() {
		return transportStageQualifier;
	}
	
	public void setTransportStageQualifier(String transportStageQualifier) {
		this.transportStageQualifier = transportStageQualifier;
	}
}
