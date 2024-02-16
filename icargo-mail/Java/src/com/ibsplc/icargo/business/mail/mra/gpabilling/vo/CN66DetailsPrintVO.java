/*
 * CN66DetailsPrintVO.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class CN66DetailsPrintVO extends AbstractVO {
	
	private String companyCode;
	
	private String gpaCode;
	
	private String gpaName;
	
	private String billingPeriod;
	
	private String mailCategoryCode;
	
	private LocalDate receivedDate;
	
	private String dsn;
	
	private String billedFrom;
	
	private String billingTo;
	
	private String origin;
	
	private String destination;
	
	private String flightNumber;
	
	private String flightCarrierCode;
	
	private double totalLcWeight;
	
	private double totalCpWeight;
	//Added by A-4809 for empty bags and EMS starts
	private double totalSvWeight;

	private double totalEmsWeight;
	//Added by A-4809 for empty bags and EMS ends
	
    private String displayWgtUnitLC;    // added by A-9002 for IASCB-22946
    private String displayWgtUnitCP;   
    private String displayWgtUnitSV;   
    private String displayWgtUnitEMS;
    
    private String mailSubclass;   
	private double weight;
    private String unitCode;
    
    public String getMailSubclass() {
		return mailSubclass;
	}

	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
    
    public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	    
    public String getDisplayWgtUnitLC() {
		return displayWgtUnitLC;
	}

	public void setDisplayWgtUnitLC(String displayWgtUnitLC) {
		this.displayWgtUnitLC = displayWgtUnitLC;
	}

	public String getDisplayWgtUnitCP() {
		return displayWgtUnitCP;
	}

	public void setDisplayWgtUnitCP(String displayWgtUnitCP) {
		this.displayWgtUnitCP = displayWgtUnitCP;
	}

	public String getDisplayWgtUnitSV() {
		return displayWgtUnitSV;
	}

	public void setDisplayWgtUnitSV(String displayWgtUnitSV) {
		this.displayWgtUnitSV = displayWgtUnitSV;
	}

	public String getDisplayWgtUnitEMS() {
		return displayWgtUnitEMS;
	}

	public void setDisplayWgtUnitEMS(String displayWgtUnitEMS) {
		this.displayWgtUnitEMS = displayWgtUnitEMS;
	}
	  

	/**
	 * @return Returns the billedFrom.
	 */
	public String getBilledFrom() {
		return billedFrom;
	}

	/**
	 * @param billedFrom The billedFrom to set.
	 */
	public void setBilledFrom(String billedFrom) {
		this.billedFrom = billedFrom;
	}

	/**
	 * @return Returns the billingPeriod.
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * @param billingPeriod The billingPeriod to set.
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	/**
	 * @return Returns the billingTo.
	 */
	public String getBillingTo() {
		return billingTo;
	}

	/**
	 * @param billingTo The billingTo to set.
	 */
	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
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
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
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
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the receivedDate.
	 */
	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate The receivedDate to set.
	 */
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return Returns the totalCpWeight.
	 */
	public double getTotalCpWeight() {
		return totalCpWeight;
	}

	/**
	 * @param totalCpWeight The totalCpWeight to set.
	 */
	public void setTotalCpWeight(double totalCpWeight) {
		this.totalCpWeight = totalCpWeight;
	}

	/**
	 * @return Returns the totalLcWeight.
	 */
	public double getTotalLcWeight() {
		return totalLcWeight;
	}

	/**
	 * @param totalLcWeight The totalLcWeight to set.
	 */
	public void setTotalLcWeight(double totalLcWeight) {
		this.totalLcWeight = totalLcWeight;
	}
	/**
	 * @return the totalSvWeight
	 */
	public double getTotalSvWeight() {
		return totalSvWeight;
	}
	/**
	 * @return the totalEmsWeight
	 */
	public double getTotalEmsWeight() {
		return totalEmsWeight;
	}
	/**
	 * @param totalSvWeight the totalSvWeight to set
	 */
	public void setTotalSvWeight(double totalSvWeight) {
		this.totalSvWeight = totalSvWeight;
	}
	/**
	 * @param totalEmsWeight the totalEmsWeight to set
	 */
	public void setTotalEmsWeight(double totalEmsWeight) {
		this.totalEmsWeight = totalEmsWeight;
	}

	

	
	
	
	
	
	
	
}