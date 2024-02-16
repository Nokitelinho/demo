/*
 * MailDiscrepancyReportVO.java Created on Jun 30, 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author A-3109
 */
public class MailDiscrepancyReportVO extends AbstractVO {

	private String companyCode;
	
	private String airport;
	
	private String flightNum;
	
	private String flightCarCod;
	
	private LocalDate fltDate;
	
	private String consgNum;
	
	private String mailIdentifier;
	
	private int  bags;
	
	//private Double weight;
	private Measure weight;//added by A-7371
	
	private String discrepancyType;
/**
 * 
 * @return weight
 */
	public Measure getWeight() {
		return weight;
	}
/**
 * 
 * @param weight
 */
	public void setWeight(Measure weight) {
		this.weight = weight;
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
	 * @return Returns the discrepancyType.
	 */
	public String getDiscrepancyType() {
		return discrepancyType;
	}

	/**
	 * @param discrepancyType The discrepancyType to set.
	 */
	public void setDiscrepancyType(String discrepancyType) {
		this.discrepancyType = discrepancyType;
	}

	/**
	 * @return Returns the mailIdentifier.
	 */
	public String getMailIdentifier() {
		return mailIdentifier;
	}

	/**
	 * @param mailIdentifier The mailIdentifier to set.
	 */
	public void setMailIdentifier(String mailIdentifier) {
		this.mailIdentifier = mailIdentifier;
	}

	/**
	 * @return Returns the airport.
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport The airport to set.
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return Returns the bags.
	 */
	public int getBags() {
		return bags;
	}

	/**
	 * @param bags The bags to set.
	 */
	public void setBags(int bags) {
		this.bags = bags;
	}

	/**
	 * @return Returns the consgNum.
	 */
	public String getConsgNum() {
		return consgNum;
	}

	/**
	 * @param consgNum The consgNum to set.
	 */
	public void setConsgNum(String consgNum) {
		this.consgNum = consgNum;
	}

	/**
	 * @return Returns the flightNum.
	 */
	public String getFlightNum() {
		return flightNum;
	}

	/**
	 * @param flightNum The flightNum to set.
	 */
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	/**
	 * @return Returns the fltDate.
	 */
	public LocalDate getFltDate() {
		return fltDate;
	}

	/**
	 * @param fltDate The fltDate to set.
	 */
	public void setFltDate(LocalDate fltDate) {
		this.fltDate = fltDate;
	}

	/**
	 * @return Returns the weight.
	 */
	/*public Double getWeight() {
		return weight;
	}

	*//**
	 * @param weight The weight to set.
	 *//*
	public void setWeight(Double weight) {
		this.weight = weight;
	}*/

	/**
	 * @return Returns the flightCarCod.
	 */
	public String getFlightCarCod() {
		return flightCarCod;
	}

	/**
	 * @param flightCarCod The flightCarCod to set.
	 */
	public void setFlightCarCod(String flightCarCod) {
		this.flightCarCod = flightCarCod;
	}

	
	
}
