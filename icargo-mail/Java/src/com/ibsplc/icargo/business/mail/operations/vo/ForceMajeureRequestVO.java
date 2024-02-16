/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Calendar;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5219
 *
 */
public class ForceMajeureRequestVO  extends AbstractVO{


	private String companyCode;

	private String forceMajuereID;

	private long sequenceNumber;

	private long mailSeqNumber;

	private String mailID;

	private String airportCode;

	private String carrierCode;

	private String flightNumber;

	private LocalDate flightDate;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String source;

	private Measure weight;

	private String originAirport;

	private String destinationAirport;

	private String consignmentDocNumber;

	private String status;

	private String requestRemarks;

	private String approvalRemarks;

	private String filterParameters;

	private String lastUpdatedUser;

	private String type;

	private int carrierID;
	
	private int flightSeqNum;
	
	private LocalDate requestDate;
	
	private String loadScan;
	public String getLoadScan() {
		return loadScan;
	}

	public void setLoadScan(String loadScan) {
		this.loadScan = loadScan;
	}

	public String getRecieveScan() {
		return recieveScan;
	}

	public void setRecieveScan(String recieveScan) {
		this.recieveScan = recieveScan;
	}

	public String getDeliveryScan() {
		return deliveryScan;
	}

	public void setDeliveryScan(String deliveryScan) {
		this.deliveryScan = deliveryScan;
	}

	public String getLateDeliveryScan() {
		return lateDeliveryScan;
	}

	public void setLateDeliveryScan(String lateDeliveryScan) {
		this.lateDeliveryScan = lateDeliveryScan;
	}

	public String getAllScan() {
		return allScan;
	}

	public void setAllScan(String allScan) {
		this.allScan = allScan;
	}

	private String recieveScan;
	private String deliveryScan;
	private String lateDeliveryScan;
	private String allScan;
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the forceMajuereID
	 */
	public String getForceMajuereID() {
		return forceMajuereID;
	}

	/**
	 * @param forceMajuereID the forceMajuereID to set
	 */
	public void setForceMajuereID(String forceMajuereID) {
		this.forceMajuereID = forceMajuereID;
	}

	/**
	 * @return the sequenceNumber
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the mailSeqNumber
	 */
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}

	/**
	 * @param mailSeqNumber the mailSeqNumber to set
	 */
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}

	/**
	 * @return the mailID
	 */
	public String getMailID() {
		return mailID;
	}

	/**
	 * @param mailID the mailID to set
	 */
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightDate
	 */
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Calendar getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the weight
	 */
	public Measure getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}

	/**
	 * @return the originAirport
	 */
	public String getOriginAirport() {
		return originAirport;
	}

	/**
	 * @param originAirport the originAirport to set
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * @return the consignmentDocNumber
	 */
	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}

	/**
	 * @param consignmentDocNumber the consignmentDocNumber to set
	 */
	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the requestRemarks
	 */
	public String getRequestRemarks() {
		return requestRemarks;
	}

	/**
	 * @param requestRemarks the requestRemarks to set
	 */
	public void setRequestRemarks(String requestRemarks) {
		this.requestRemarks = requestRemarks;
	}

	/**
	 * @return the approvalRemarks
	 */
	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	/**
	 * @param approvalRemarks the approvalRemarks to set
	 */
	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	/**
	 * @return the filterParameters
	 */
	public String getFilterParameters() {
		return filterParameters;
	}

	/**
	 * @param filterParameters the filterParameters to set
	 */
	public void setFilterParameters(String filterParameters) {
		this.filterParameters = filterParameters;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 *
	 * @return
	 */
	public String getType(){
		return type;
	}

	/**
	 *
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * @return the carrierID
	 */
	public int getCarrierID() {
		return carrierID;
	}
	/**
	 * @param carrierID the carrierID to set
	 */
	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
	}
	/**
	 * @return the flightSeqNum
	 */
	public int getFlightSeqNum() {
		return flightSeqNum;
	}
	/**
	 * @param flightSeqNum the flightSeqNum to set
	 */
	public void setFlightSeqNum(int flightSeqNum) {
		this.flightSeqNum = flightSeqNum;
	}

	/**
	 * @return the requestDate
	 */
	public LocalDate getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

}
