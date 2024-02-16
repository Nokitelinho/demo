/*
 * DailyMailStationForm.java Created on Feb 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3251
 *
 */
public class DailyMailStationForm extends ScreenModel {

	private static final String BUNDLE = "DailyMailStationResources";
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "mailtracking.defaults.DailyMailStation";
	
	//private String filghtDate;
	private String flightDate;
	private String carrierCode;
	private String flightNumber;
	private String destination;	
	
	private String acfilghtDate;
	private String acflightNumber;
	private int acflightCarrireID;
	private long acflightSeqNumber;
	private String accompanyCode;
	private String acorigin;
	private String acdestination;
	private String accarrierCode;
	
	
	private String validreport;
	private String printstatus;
	//Added by A-6991 for ICRD-197259
	private String flightFromDate; 
	private String flightToDate;
	private String acfilghtFromDate;
	private String acfilghtToDate;
	
	/**
	 * @return the printstatus
	 */
	public String getPrintstatus() {
		return printstatus;
	}

	/**
	 * @param printstatus the printstatus to set
	 */
	public void setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
	}

	/**
	 * @return the validreport
	 */
	public String getValidreport() {
		return validreport;
	}

	/**
	 * @param validreport the validreport to set
	 */
	public void setValidreport(String validreport) {
		this.validreport = validreport;
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
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the filghtDate
	 */
	//Commented by A-6991 for CR ICRD-197259
	/*public String getFilghtDate() {
		return filghtDate;
	}

	*//**
	 * @param filghtDate the filghtDate to set
	 *//*
	public void setFilghtDate(String filghtDate) {
		this.filghtDate = filghtDate;
	}*/

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
	 * @return product
	 */
	public  String getProduct() {
		return PRODUCT;
	}
	/**
	 * @return screenId
	 */
	public  String getScreenId() {
		return SCREENID;
	}
	/**
	 * @return subProduct
	 */
	public  String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the accarrierCode
	 */
	public String getAccarrierCode() {
		return accarrierCode;
	}

	/**
	 * @param accarrierCode the accarrierCode to set
	 */
	public void setAccarrierCode(String accarrierCode) {
		this.accarrierCode = accarrierCode;
	}

	/**
	 * @return the accompanyCode
	 */
	public String getAccompanyCode() {
		return accompanyCode;
	}

	/**
	 * @param accompanyCode the accompanyCode to set
	 */
	public void setAccompanyCode(String accompanyCode) {
		this.accompanyCode = accompanyCode;
	}

	/**
	 * @return the acdestination
	 */
	public String getAcdestination() {
		return acdestination;
	}

	/**
	 * @param acdestination the acdestination to set
	 */
	public void setAcdestination(String acdestination) {
		this.acdestination = acdestination;
	}
	

	/**
	 * @return the acfilghtDate
	 */
	public String getAcfilghtDate() {
		return acfilghtDate;
	}

	/**
	 * @param acfilghtDate the acfilghtDate to set
	 */
	public void setAcfilghtDate(String acfilghtDate) {
		this.acfilghtDate = acfilghtDate;
	}

	/**
	 * @return the acflightCarrireID
	 */
	public int getAcflightCarrireID() {
		return acflightCarrireID;
	}

	/**
	 * @param acflightCarrireID the acflightCarrireID to set
	 */
	public void setAcflightCarrireID(int acflightCarrireID) {
		this.acflightCarrireID = acflightCarrireID;
	}

	/**
	 * @return the acflightNumber
	 */
	public String getAcflightNumber() {
		return acflightNumber;
	}

	/**
	 * @param acflightNumber the acflightNumber to set
	 */
	public void setAcflightNumber(String acflightNumber) {
		this.acflightNumber = acflightNumber;
	}

	/**
	 * @return the acflightSeqNumber
	 */
	public long getAcflightSeqNumber() {
		return acflightSeqNumber;
	}

	/**
	 * @param acflightSeqNumber the acflightSeqNumber to set
	 */
	public void setAcflightSeqNumber(long acflightSeqNumber) {
		this.acflightSeqNumber = acflightSeqNumber;
	}

	/**
	 * @return the acorigin
	 */
	public String getAcorigin() {
		return acorigin;
	}

	/**
	 * @param acorigin the acorigin to set
	 */
	public void setAcorigin(String acorigin) {
		this.acorigin = acorigin;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @return the flightFromDate
	 */
	@DateFieldId(id="DailyMailStationDateRange",fieldType="from")//Added By A-6991 for CR ICRD-197259
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
	@DateFieldId(id="DailyMailStationDateRange",fieldType="to")//Added By A-6991 for CR ICRD-197259
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
	 * @return the acfilghtFromDate
	 */;
	public String getAcfilghtFromDate() {
		return acfilghtFromDate;
	}

	/**
	 * @param acfilghtFromDate the acfilghtFromDate to set
	 */
	public void setAcfilghtFromDate(String acfilghtFromDate) {
		this.acfilghtFromDate = acfilghtFromDate;
	}

	/**
	 * @return the acfilghtToDate
	 */
	public String getAcfilghtToDate() {
		return acfilghtToDate;
	}

	/**
	 * @param acfilghtToDate the acfilghtToDate to set
	 */
	public void setAcfilghtToDate(String acfilghtToDate) {
		this.acfilghtToDate = acfilghtToDate;
	}
	
}















		