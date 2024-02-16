/*
 * UnaccountedDispatchesFilterVO.java created on Aug 20, 2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2107
 *
 */
public class UnaccountedDispatchesFilterVO extends AbstractVO{


	private LocalDate fromDate;
	private String flightFromDate;
    private LocalDate toDate;
    private String flightToDate;
    private String departurePort;
    private String carrierCode;
    private int carrierId;
    private String flightNumber;
    private String finalDestination;
    private LocalDate effectiveDate; 
    private String unaccountedDisptachEffDate;
    private String reasonCode;
    private String companyCode;
    private int pageNumber;
    private int absoluteIndex; 
    
    // Added for report
    private String noOfDispatches;
    private String currency;
    private String proRatedAmt;
    
    
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
	 * @return the carrierId
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return the departurePort
	 */
	public String getDeparturePort() {
		return departurePort;
	}
	/**
	 * @param departurePort the departurePort to set
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}
	/**
	 * @return the effectiveDate
	 */
	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the finalDestination
	 */
	public String getFinalDestination() {
		return finalDestination;
	}
	/**
	 * @param finalDestination the finalDestination to set
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
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
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
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
	 * @return the unaccountedDisptachEffDate
	 */
	public String getUnaccountedDisptachEffDate() {
		return unaccountedDisptachEffDate;
	}
	/**
	 * @param unaccountedDisptachEffDate the unaccountedDisptachEffDate to set
	 */
	public void setUnaccountedDisptachEffDate(String unaccountedDisptachEffDate) {
		this.unaccountedDisptachEffDate = unaccountedDisptachEffDate;
	}
	/**
	 * @return the flightFromDate
	 */
	public String getFlightFromDate() {
		return flightFromDate;
	}
	/**
	 * @param flightFromDate the flightFromDate to set
	 */
	public void setFlightFromDate(String flightFromDate) {
		this.flightFromDate = flightFromDate;
	}
	/**
	 * @return the flightToDate
	 */
	public String getFlightToDate() {
		return flightToDate;
	}
	/**
	 * @param flightToDate the flightToDate to set
	 */
	public void setFlightToDate(String flightToDate) {
		this.flightToDate = flightToDate;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the noOfDispatches
	 */
	public String getNoOfDispatches() {
		return noOfDispatches;
	}
	/**
	 * @param noOfDispatches the noOfDispatches to set
	 */
	public void setNoOfDispatches(String noOfDispatches) {
		this.noOfDispatches = noOfDispatches;
	}
	/**
	 * @return the proRatedAmt
	 */
	public String getProRatedAmt() {
		return proRatedAmt;
	}
	/**
	 * @param proRatedAmt the proRatedAmt to set
	 */
	public void setProRatedAmt(String proRatedAmt) {
		this.proRatedAmt = proRatedAmt;
	}
	/**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	

}
