/*
 * LocationTransportationDtlsVO.java created on Jul 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2270
 *
 */
public class LocationTransportationDtlsVO extends AbstractVO {
	
	private String payCarrier;
	private String originAirport;
	private String destinationAirport;
	private String carrierFinalDest;
	private String carrierAssigned;
	private String scanCarrier;
	private String scanLocation;
	private LocalDate scanDate;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private String possessionCarrier;
	private String possessionLocation;
	private LocalDate possessionDate;
    private String dLVScanCarrier;
    private String dLVScanLocation;
    private LocalDate dLVScanDate;
	/**
	 * @return the arrivalDate
	 */
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @return the carrierAssigned
	 */
	public String getCarrierAssigned() {
		return carrierAssigned;
	}
	/**
	 * @param carrierAssigned the carrierAssigned to set
	 */
	public void setCarrierAssigned(String carrierAssigned) {
		this.carrierAssigned = carrierAssigned;
	}
	/**
	 * @return the carrierFinalDest
	 */
	public String getCarrierFinalDest() {
		return carrierFinalDest;
	}
	/**
	 * @param carrierFinalDest the carrierFinalDest to set
	 */
	public void setCarrierFinalDest(String carrierFinalDest) {
		this.carrierFinalDest = carrierFinalDest;
	}
	/**
	 * @return the departureDate
	 */
	public LocalDate getDepartureDate() {
		return departureDate;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
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
	 * @return the payCarrier
	 */
	public String getPayCarrier() {
		return payCarrier;
	}
	/**
	 * @param payCarrier the payCarrier to set
	 */
	public void setPayCarrier(String payCarrier) {
		this.payCarrier = payCarrier;
	}
	/**
	 * @return the possessionCarrier
	 */
	public String getPossessionCarrier() {
		return possessionCarrier;
	}
	/**
	 * @param possessionCarrier the possessionCarrier to set
	 */
	public void setPossessionCarrier(String possessionCarrier) {
		this.possessionCarrier = possessionCarrier;
	}
	/**
	 * @return the possessionDate
	 */
	public LocalDate getPossessionDate() {
		return possessionDate;
	}
	/**
	 * @param possessionDate the possessionDate to set
	 */
	public void setPossessionDate(LocalDate possessionDate) {
		this.possessionDate = possessionDate;
	}
	/**
	 * @return the possessionLocation
	 */
	public String getPossessionLocation() {
		return possessionLocation;
	}
	/**
	 * @param possessionLocation the possessionLocation to set
	 */
	public void setPossessionLocation(String possessionLocation) {
		this.possessionLocation = possessionLocation;
	}
	/**
	 * @return the scanCarrier
	 */
	public String getScanCarrier() {
		return scanCarrier;
	}
	/**
	 * @param scanCarrier the scanCarrier to set
	 */
	public void setScanCarrier(String scanCarrier) {
		this.scanCarrier = scanCarrier;
	}
	/**
	 * @return the scanDate
	 */
	public LocalDate getScanDate() {
		return scanDate;
	}
	/**
	 * @param scanDate the scanDate to set
	 */
	public void setScanDate(LocalDate scanDate) {
		this.scanDate = scanDate;
	}
	/**
	 * @return the scanLocation
	 */
	public String getScanLocation() {
		return scanLocation;
	}
	/**
	 * @param scanLocation the scanLocation to set
	 */
	public void setScanLocation(String scanLocation) {
		this.scanLocation = scanLocation;
	}
	/**
	 * @return the dLVScanCarrier
	 */
	public String getDLVScanCarrier() {
		return dLVScanCarrier;
	}
	/**
	 * @param scanCarrier the dLVScanCarrier to set
	 */
	public void setDLVScanCarrier(String scanCarrier) {
		dLVScanCarrier = scanCarrier;
	}
	/**
	 * @return the dLVScanDate
	 */
	public LocalDate getDLVScanDate() {
		return dLVScanDate;
	}
	/**
	 * @param scanDate the dLVScanDate to set
	 */
	public void setDLVScanDate(LocalDate scanDate) {
		dLVScanDate = scanDate;
	}
	/**
	 * @return the dLVScanLocation
	 */
	public String getDLVScanLocation() {
		return dLVScanLocation;
	}
	/**
	 * @param scanLocation the dLVScanLocation to set
	 */
	public void setDLVScanLocation(String scanLocation) {
		dLVScanLocation = scanLocation;
	}
    
	
		
	
}