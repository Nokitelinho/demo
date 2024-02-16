/*
 * OffloadDetailVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author A-3109
 *
 */
public class OffloadDetailVO  extends AbstractVO{

	private String companyCode;     
    private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;   
	private int offloadSerialNumber; 
    private LocalDate offloadedDate;
    private String offloadUser;
    private int offloadedBags;
    //private double offloadedWeight;
    private Measure offloadedWeight;//added by A-7371
    private String offloadRemarks;
    private String offloadReasonCode;
    private String carrierCode;
    private String offloadDescription;
    private String offloadType;
    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailClass;
    private int year;
    private String mailId;
    private String containerNumber;
    private String airportCode; 
    //Added to include the DSNPK...
    private String mailSubclass;
    private String  mailCategoryCode;
    private long mailSequenceNumber;
    
    //Added for Irregularity
    
    public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	private String offloadedStation;
    private LocalDate flightDate;
    private String billingBasis;
    private int csgSequenceNumber;
	private String csgDocumentNumber;
	private String poaCode;
    private String irregularityStatus;
	private int rescheduledFlightCarrierId;
	private String rescheduledFlightNo;
	private long rescheduledFlightSeqNo;
	private LocalDate rescheduledFlightDate;
	/**
	 * 
	 * @return offloadedWeight
	 */
	public Measure getOffloadedWeight() {
		return offloadedWeight;
	}
	/**
	 * 
	 * @param offloadedWeight
	 */
	public void setOffloadedWeight(Measure offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}
	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	/**
	 * @param mailSubClass The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	/**
	 * @param destinationExchangeOffice The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	/**
	 * @return Returns the dsn.
	 */
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn The dsn to set.
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return Returns the mailId.
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId The mailId to set.
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return Returns the offloadDescription.
	 */
	public String getOffloadDescription() {
		return offloadDescription;
	}
	/**
	 * @param offloadDescription The offloadDescription to set.
	 */
	public void setOffloadDescription(String offloadDescription) {
		this.offloadDescription = offloadDescription;
	}
	/**
	 * @return Returns the offloadedBags.
	 */
	public int getOffloadedBags() {
		return offloadedBags;
	}
	/**
	 * @param offloadedBags The offloadedBags to set.
	 */
	public void setOffloadedBags(int offloadedBags) {
		this.offloadedBags = offloadedBags;
	}
	/**
	 * @return Returns the offloadedDate.
	 */
	public LocalDate getOffloadedDate() {
		return offloadedDate;
	}
	/**
	 * @param offloadedDate The offloadedDate to set.
	 */
	public void setOffloadedDate(LocalDate offloadedDate) {
		this.offloadedDate = offloadedDate;
	}
	/**
	 * @return Returns the offloadedWeight.
	 */
	/*public double getOffloadedWeight() {
		return offloadedWeight;
	}
	*//**
	 * @param offloadedWeight The offloadedWeight to set.
	 *//*
	public void setOffloadedWeight(double offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}*/
	/**
	 * @return Returns the offloadReasonCode.
	 */
	public String getOffloadReasonCode() {
		return offloadReasonCode;
	}
	/**
	 * @param offloadReasonCode The offloadReasonCode to set.
	 */
	public void setOffloadReasonCode(String offloadReasonCode) {
		this.offloadReasonCode = offloadReasonCode;
	}
	/**
	 * @return Returns the offloadRemarks.
	 */
	public String getOffloadRemarks() {
		return offloadRemarks;
	}
	/**
	 * @param offloadRemarks The offloadRemarks to set.
	 */
	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
	}
	/**
	 * @return Returns the offloadSerialNumber.
	 */
	public int getOffloadSerialNumber() {
		return offloadSerialNumber;
	}
	/**
	 * @param offloadSerialNumber The offloadSerialNumber to set.
	 */
	public void setOffloadSerialNumber(int offloadSerialNumber) {
		this.offloadSerialNumber = offloadSerialNumber;
	}
	/**
	 * @return Returns the offloadType.
	 */
	public String getOffloadType() {
		return offloadType;
	}
	/**
	 * @param offloadType The offloadType to set.
	 */
	public void setOffloadType(String offloadType) {
		this.offloadType = offloadType;
	}
	/**
	 * @return Returns the offloadUser.
	 */
	public String getOffloadUser() {
		return offloadUser;
	}
	/**
	 * @param offloadUser The offloadUser to set.
	 */
	public void setOffloadUser(String offloadUser) {
		this.offloadUser = offloadUser;
	}
	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	/**
	 * @param originExchangeOffice The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the offloadedStation.
	 */
	public String getOffloadedStation() {
		return offloadedStation;
	}
	/**
	 * @param offloadedStation The offloadedStation to set.
	 */
	public void setOffloadedStation(String offloadedStation) {
		this.offloadedStation = offloadedStation;
	}
	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return Returns the csgDocumentNumber.
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber The csgDocumentNumber to set.
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return Returns the csgSequenceNumber.
	 */
	public int getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	/**
	 * @param csgSequenceNumber The csgSequenceNumber to set.
	 */
	public void setCsgSequenceNumber(int csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}
	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	/**
	 * @return Returns the irregularityStatus.
	 */
	public String getIrregularityStatus() {
		return irregularityStatus;
	}
	/**
	 * @param irregularityStatus The irregularityStatus to set.
	 */
	public void setIrregularityStatus(String irregularityStatus) {
		this.irregularityStatus = irregularityStatus;
	}
	public int getRescheduledFlightCarrierId() {
		return rescheduledFlightCarrierId;
	}
	public void setRescheduledFlightCarrierId(int rescheduledFlightCarrierId) {
		this.rescheduledFlightCarrierId = rescheduledFlightCarrierId;
	}
	public String getRescheduledFlightNo() {
		return rescheduledFlightNo;
	}
	public void setRescheduledFlightNo(String rescheduledFlightNo) {
		this.rescheduledFlightNo = rescheduledFlightNo;
	}
	public long getRescheduledFlightSeqNo() {
		return rescheduledFlightSeqNo;
	}
	public void setRescheduledFlightSeqNo(long rescheduledFlightSeqNo) {
		this.rescheduledFlightSeqNo = rescheduledFlightSeqNo;
	}
	public LocalDate getRescheduledFlightDate() {
		return rescheduledFlightDate;
	}
	public void setRescheduledFlightDate(LocalDate rescheduledFlightDate) {
		this.rescheduledFlightDate = rescheduledFlightDate;
	}
}
