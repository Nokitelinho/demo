/*
 * MailInFlightSummaryVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3109
 * Used for displaying data in MailFlight Summary
 */
public class MailInFlightSummaryVO extends AbstractVO {

	private String companyCode;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategory;
	private String mailSubClass;
	private String originPa;
	private String destinationPa;
	private int bags;
	//private double weight;
	private Measure weight;//added by A-7371
	private String ubrNumber;
	private String controlDocumentNumber;
	
	/*
	 * Addded By Karthick V as the Part of the NCA Mail Tracking CR ..
	 * To include the Origin City and the Destination City ..
	 */
	private String  destinationAirport;
	
	private String originAirport;
	
	private Collection<MailDetailVO> mailDetails;

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
	 * @return Returns the controlDocumentNumber.
	 */
	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}

	/**
	 * @param controlDocumentNumber The controlDocumentNumber to set.
	 */
	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}

	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return Returns the destinationPa.
	 */
	public String getDestinationPa() {
		return destinationPa;
	}

	/**
	 * @param destinationPa The destinationPa to set.
	 */
	public void setDestinationPa(String destinationPa) {
		this.destinationPa = destinationPa;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the mailDetails.
	 */
	public Collection<MailDetailVO> getMailDetails() {
		return mailDetails;
	}

	/**
	 * @param mailDetails The mailDetails to set.
	 */
	public void setMailDetails(Collection<MailDetailVO> mailDetails) {
		this.mailDetails = mailDetails;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return Returns the originPa.
	 */
	public String getOriginPa() {
		return originPa;
	}

	/**
	 * @param originPa The originPa to set.
	 */
	public void setOriginPa(String originPa) {
		this.originPa = originPa;
	}

	/**
	 * @return Returns the ubrNumber.
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}

	/**
	 * @param ubrNumber The ubrNumber to set.
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}

	/**
	 * @return Returns the weight.
	 */
	/*public double getWeight() {
		return weight;
	}

	*//**
	 * @param weight The weight to set.
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/

	/**
	 * 
	 * @return
	 */

public String getDestinationAirport() {
	return destinationAirport;
}
/**
 * 
 * @param destinationAirport
 */
public void setDestinationAirport(String destinationAirport) {
	this.destinationAirport = destinationAirport;
}
/**
 * 
 * @return
 */
public String getOriginAirport() {
	return originAirport;
}
/**
 * 
 * @param originAirport
 */
public void setOriginAirport(String originAirport) {
	this.originAirport = originAirport;
}
}
