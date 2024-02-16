/*
 * OnlineScannedMailVO.java Created on Jun 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-3109
 * 
 */
public class OnlineScannedMailVO extends AbstractVO {
	
	private String txnMode;
	private String companyCode;
	private String flightNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private int carrierID;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int legSerialNumber;
	private String pol;
	private String pou;
	private String destination;
	private String fromCarrierCode;
	private int fromCarrierID;
	private String containerNumber;
	private String containerType;
	private String intact;
	private String remarks;
	private String offloadReason;
	private String returnReason;
	private int ownAirlineId;
	private String ownAirlineCode;
	private String userId;
	private String airportCode;
	private ContainerVO containerVO;
	private MailbagVO mailbagVO;
	private DespatchDetailsVO despatchDetailsVO;
	private String flightRoute;

	/*
	 * Added by RENO For CR : AirNZ 404 : Mail Allocation
	 */
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	
	/*
	 * Added For Bug MTK636
	 */
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	
	/*
	 * Added For HHT745
	 */
	private String containerReassignFlag;
	
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getCarrierID() {
		return carrierID;
	}
	public void setCarrierID(int carrierID) {
		this.carrierID = carrierID;
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
	public ContainerVO getContainerVO() {
		return containerVO;
	}
	public void setContainerVO(ContainerVO containerVO) {
		this.containerVO = containerVO;
	}
	public DespatchDetailsVO getDespatchDetailsVO() {
		return despatchDetailsVO;
	}
	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO) {
		this.despatchDetailsVO = despatchDetailsVO;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
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
	public String getFromCarrierCode() {
		return fromCarrierCode;
	}
	public void setFromCarrierCode(String fromCarrierCode) {
		this.fromCarrierCode = fromCarrierCode;
	}
	public int getFromCarrierID() {
		return fromCarrierID;
	}
	public void setFromCarrierID(int fromCarrierID) {
		this.fromCarrierID = fromCarrierID;
	}
	public String getIntact() {
		return intact;
	}
	public void setIntact(String intact) {
		this.intact = intact;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public MailbagVO getMailbagVO() {
		return mailbagVO;
	}
	public void setMailbagVO(MailbagVO mailbagVO) {
		this.mailbagVO = mailbagVO;
	}
	public String getOffloadReason() {
		return offloadReason;
	}
	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	public String getTxnMode() {
		return txnMode;
	}
	public void setTxnMode(String txnMode) {
		this.txnMode = txnMode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	public int getOwnAirlineId() {
		return ownAirlineId;
	}
	public void setOwnAirlineId(int ownAirlineId) {
		this.ownAirlineId = ownAirlineId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the flightRoute
	 */
	public String getFlightRoute() {
		return flightRoute;
	}
	/**
	 * @param flightRoute the flightRoute to set
	 */
	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}
	public void setBookingFlightDetailLastUpdTime(
			LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}
	public String getUbrNumber() {
		return ubrNumber;
	}
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
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
	 * @return the containerReassignFlag
	 */
	public String getContainerReassignFlag() {
		return containerReassignFlag;
	}
	/**
	 * @param containerReassignFlag the containerReassignFlag to set
	 */
	public void setContainerReassignFlag(String containerReassignFlag) {
		this.containerReassignFlag = containerReassignFlag;
	}	
}
