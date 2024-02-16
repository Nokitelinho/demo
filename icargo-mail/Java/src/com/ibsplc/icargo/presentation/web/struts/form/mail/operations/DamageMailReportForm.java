/*
 * DamageMailReportForm.java Created on FEB 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3227
 *
 */
public class DamageMailReportForm  extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.damagemailreport";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "damageMailReportResources";
	
	private String fromDate;
	private String toDate;
	private String airport;
	private String damageCode;
	private String airline;
	private String airlineId;
	private String validFlag;
	private String status;	  
	//added by A-5844 for ICRD-67196
	private String flightCarrierCode; 
	
	private String flightNumber; 
	private String flightDate; 
	private String flightOrigin; 
	private String flightDestination; 
	private String gpaCode; 
	private String originOE; 
	private String destinationOE; 
	private String subClassGroup; 
	private String subClassCode; 
	private String reportFlag;
	
	/**
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;	}
	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	public String getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the flightOrigin
	 */
	public String getFlightOrigin() {
		return flightOrigin;
	}
	/**
	 * @param flightOrigin the flightOrigin to set
	 */
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}
	/**
	 * @return the flightDestination
	 */
	public String getFlightDestination() {
		return flightDestination;
	}
	/**
	 * @param flightDestination the flightDestination to set
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the originOE
	 */
	public String getOriginOE() {
		return originOE;
	}
	/**
	 * @param originOE the originOE to set
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	/**
	 * @return the destinationOE
	 */
	public String getDestinationOE() {
		return destinationOE;
	}
	/**
	 * @param destinationOE the destinationOE to set
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	/**
	 * @return the subClassGroup
	 */
	public String getSubClassGroup() {
		return subClassGroup;
	}
	/**
	 * @param subClassGroup the subClassGroup to set
	 */
	public void setSubClassGroup(String subClassGroup) {
		this.subClassGroup = subClassGroup;
	}
	/**
	 * @return the subClassCode
	 */
	public String getSubClassCode() {
		return subClassCode;
	}
	/**
	 * @param subClassCode the subClassCode to set
	 */
	public void setSubClassCode(String subClassCode) {
		this.subClassCode = subClassCode;
	}
	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	
	/**
	 * @return the damageCode
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCodes the damageCode to set
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * @return the fromDate
	 */
	@DateFieldId(id="DamageReportMailReportDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	@DateFieldId(id="DamageReportMailReportDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getProduct() {
		return PRODUCT_NAME;
	}
	public String getScreenId() {
		return SCREEN_ID;
	}
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}
	public String getBundle(){
		return BUNDLE;
	}
	/**
	 * @return the airlineId
	 */
	public String getAirlineId() {
		return airlineId;
	}
	/**
	 * @param airlineId the airlineId to set
	 */
	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
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
	 * @return the validFlag
	 */
	public String getValidFlag() {
		return validFlag;
	}
	/**
	 * @param validFlag the validFlag to set
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	/**
	 * @param reportFlag the reportFlag to set
	 */
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}
	/**
	 * @return the reportFlag
	 */
	public String getReportFlag() {
		return reportFlag;
	}
}
