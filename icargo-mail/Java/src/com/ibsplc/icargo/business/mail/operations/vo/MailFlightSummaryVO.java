/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO.java
 *
 *	Created by	:	a-7779
 *	Created on	:	30-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	30-Aug-2017	:	Draft
 */
public class MailFlightSummaryVO extends AbstractVO {

	private String companyCode;
    private String airportCode;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String carrierCode ;
    private LocalDate flightDate ;
	private Collection<ShipmentSummaryVO> shipmentSummaryVOs;
	//added by a-7779 for icrd-192536 CTM out transaction starts
	private String toFlightNumber;
	private int toCarrierId;
	private String toCarrierCode;
	private long toFlightSequenceNumber;
	private int toSegmentSerialNumber;
	private int toLegSerialNumber;
	private LocalDate toFlightDate;
	private String pol;
	private String pou;
	private String toContainerNumber;//added by A-7371 as part of ICRD-231526
	private String finalDestination;
	private String eventCode;//a-8061 added as part of  ICRD-249916
	private String route;//a-8061 added as part of  ICRD-253703
	private Map<String,String> uldAwbMap;//Added by A-5219 for ICRD-256200, to hold container of AWB info when data comes to mail from cargo in case of found arrival
	
	private Map<String,String> shpDetailMap;
	
	private String transferStatus;
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
	public String getToFlightNumber() {
		return toFlightNumber;
	}
	public void setToFlightNumber(String toFlightNumber) {
		this.toFlightNumber = toFlightNumber;
	}
	public int getToCarrierId() {
		return toCarrierId;
	}
	public void setToCarrierId(int toCarrierId) {
		this.toCarrierId = toCarrierId;
	}
	public String getToCarrierCode() {
		return toCarrierCode;
	}
	public void setToCarrierCode(String toCarrierCode) {
		this.toCarrierCode = toCarrierCode;
	}
	public long getToFlightSequenceNumber() {
		return toFlightSequenceNumber;
	}
	public void setToFlightSequenceNumber(long toFlightSequenceNumber) {
		this.toFlightSequenceNumber = toFlightSequenceNumber;
	}
	public int getToSegmentSerialNumber() {
		return toSegmentSerialNumber;
	}
	public void setToSegmentSerialNumber(int toSegmentSerialNumber) {
		this.toSegmentSerialNumber = toSegmentSerialNumber;
	}
	public int getToLegSerialNumber() {
		return toLegSerialNumber;
	}
	public void setToLegSerialNumber(int toLegSerialNumber) {
		this.toLegSerialNumber = toLegSerialNumber;
	}
	public LocalDate getToFlightDate() {
		return toFlightDate;
	}
	public void setToFlightDate(LocalDate toFlightDate) {
		this.toFlightDate = toFlightDate;
	}
	//added by a-7779 for icrd-192536 CTM out transaction ends
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
	public Collection<ShipmentSummaryVO> getShipmentSummaryVOs() {
		return shipmentSummaryVOs;
	}
	public void setShipmentSummaryVOs(
			Collection<ShipmentSummaryVO> shipmentSummaryVOs) {
		this.shipmentSummaryVOs = shipmentSummaryVOs;
	}
	/**
	 * 	Getter for toContainerNumber
	 *	Added by : A-7371 on 04-Dec-2017
	 * 	Used for :
	 */
	public String getToContainerNumber() {
		return toContainerNumber;
	}
	/**
	 *  @param toContainerNumber the toContainerNumber to set
	 * 	Setter for toContainerNumber
	 *	Added by : A-7371 on 04-Dec-2017
	 * 	Used for :
	 */
	public void setToContainerNumber(String toContainerNumber) {
		this.toContainerNumber = toContainerNumber;
	}
	/**
	 * 	Getter for finalDestination
	 *	Added by : A-7371 on 04-Dec-2017
	 * 	Used for :
	 */
	public String getFinalDestination() {
		return finalDestination;
	}
	/**
	 *  @param finalDestination the finalDestination to set
	 * 	Setter for finalDestination
	 *	Added by : A-7371 on 04-Dec-2017
	 * 	Used for :
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Map<String,String> getUldAwbMap() {
		return uldAwbMap;
	}
	public void setUldAwbMap(Map<String,String> uldAwbMap) {
		this.uldAwbMap = uldAwbMap;
	}
	public Map<String,String> getShpDetailMap() {
		return shpDetailMap;
	}
	public void setShpDetailMap(Map<String,String> shpDetailMap) {
		this.shpDetailMap = shpDetailMap;
	}
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

}
