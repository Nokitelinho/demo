/*
 * CarditPreAdviseReportVO.java Created on Jun 30, 2016
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
 * @author A-5991
 * 
 */
public class CarditPreAdviseReportVO extends AbstractVO {

	private String companyCode;
	private String airportCode;
	private LocalDate mailDate;
	private int flightCarrierIdr;
	private String carrierCode;
	private String flightNumber;
	private String destinationAirport;
	private int numBags;
	//private double mailBagWeight;
	private Measure mailBagWeight;//added by A-7371
	private String strMailDate;
	/**
	 * @return the strMailDate
	 */
	public String getStrMailDate() {
		return strMailDate;
	}
	/**
	 * @param strMailDate the strMailDate to set
	 */
	public void setStrMailDate(String strMailDate) {
		this.strMailDate = strMailDate;
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
	 * @return the flightCarrierIdr
	 */
	public int getFlightCarrierIdr() {
		return flightCarrierIdr;
	}
	/**
	 * @param flightCarrierIdr the flightCarrierIdr to set
	 */
	public void setFlightCarrierIdr(int flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
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
	 * 
	 * @return mailBagWeight
	 */
	public Measure getMailBagWeight() {
		return mailBagWeight;
	}
	/**
	 * 
	 * @param mailBagWeight
	 */
	public void setMailBagWeight(Measure mailBagWeight) {
		this.mailBagWeight = mailBagWeight;
	}
	/**
	 * @return the mailBagWeight
	 */
	/*public double getMailBagWeight() {
		return mailBagWeight;
	}
	*//**
	 * @param mailBagWeight the mailBagWeight to set
	 *//*
	public void setMailBagWeight(double mailBagWeight) {
		this.mailBagWeight = mailBagWeight;
	}*/
	/**
	 * @return the mailDate
	 */
	public LocalDate getMailDate() {
		return mailDate;
	}
	/**
	 * @param mailDate the mailDate to set
	 */
	public void setMailDate(LocalDate mailDate) {
		this.mailDate = mailDate;
	}
	/**
	 * @return the numBags
	 */
	public int getNumBags() {
		return numBags;
	}
	/**
	 * @param numBags the numBags to set
	 */
	public void setNumBags(int numBags) {
		this.numBags = numBags;
	}
	

}
