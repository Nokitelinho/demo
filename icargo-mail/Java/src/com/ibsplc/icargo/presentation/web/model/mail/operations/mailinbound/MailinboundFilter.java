package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailinboundFilter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	18-Sep-2018		:	Draft
 */

public class MailinboundFilter {
	
	private FlightNumber flightnumber;
	
	private String port;
	
	private String fromDate;
	
	private String toDate;
	
	private String mailstatus;

	private String transfercarrier;
	
	private String pa;
	
	private String pageNumber;
	
	private String pageSize;
	
	private String density;
	
	private String readyforDeliveryRequired;
	
	private boolean mailFlightChecked;//Added by A-8464 for ICRD-328502
	private String defWeightUnit;
	private String  stationVolUnt;//added by A-8353 for ICRD-274933
	private HashMap<String, Collection<OneTimeVO>> oneTimeValues;
	private String fromTime;
	private String toTime;
	private String pol;
	private String valildationforimporthandling;
	private String validationforTBA;
	 public String getValidationforTBA() {
		return validationforTBA;
	}
	public void setValidationforTBA(String validationforTBA) {
		this.validationforTBA = validationforTBA;
	}
	 public String getValildationforimporthandling() {
		return valildationforimporthandling;
	}
	public void setValildationforimporthandling(String valildationforimporthandling) {
		this.valildationforimporthandling = valildationforimporthandling;
	}
	 //added by A-7815 as part of IASCB-36551
    private String operatingReference;
    
	public FlightNumber getFlightnumber() {
		return flightnumber;
	}

	public void setFlightnumber(FlightNumber flightnumber) {
		this.flightnumber = flightnumber;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMailstatus() {
		return mailstatus;
	}

	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}

	public String getTransfercarrier() {
		return transfercarrier;
	}

	public void setTransfercarrier(String transfercarrier) {
		this.transfercarrier = transfercarrier;
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getReadyforDeliveryRequired() {
		return readyforDeliveryRequired;
	}

	public void setReadyforDeliveryRequired(String readyforDeliveryRequired) {
		this.readyforDeliveryRequired = readyforDeliveryRequired;
	}

	public boolean isMailFlightChecked() {
		return mailFlightChecked;
	}

	public void setMailFlightChecked(boolean mailFlightChecked) {
		this.mailFlightChecked = mailFlightChecked;
	}
	
	public String getDefWeightUnit() {
		return defWeightUnit;
	}
	
	public void setDefWeightUnit(String defWeightUnit) {
		this.defWeightUnit = defWeightUnit;
	}
	public String getStationVolUnt() {
		return stationVolUnt;
	}
	public void setStationVolUnt(String stationVolUnt) {
		this.stationVolUnt = stationVolUnt;
	}
	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}
	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}
	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getOperatingReference() {
		return operatingReference;
	}

	public void setOperatingReference(String operatingReference) {
		this.operatingReference = operatingReference;
	}
	
	

}
