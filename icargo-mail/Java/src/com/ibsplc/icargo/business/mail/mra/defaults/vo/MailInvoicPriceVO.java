/*
 * MailInvoicPriceVO.java created on Jul 19, 2007
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
 * @author A-2408
 *
 */
public class MailInvoicPriceVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private double lineHaulDollarRate;
	
	private double lineHaulSDRRate;
	
	private double terminalHandlingDollarRate;
	
	private double terminalHandlingSDRRate;
	
	private double perKiloDollarRate;
	
	private double perKiloSDRRate;
	
	private double tripChargeDollarRate;
	
	private double tripChargeSDRRate;
	
	private double containerRate;
	
	private double sdrRate;
	
	private LocalDate sdrDate;

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
	 * @return Returns the containerRate.
	 */
	public double getContainerRate() {
		return containerRate;
	}

	/**
	 * @param containerRate The containerRate to set.
	 */
	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}

	/**
	 * @return Returns the invoiceKey.
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey The invoiceKey to set.
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return Returns the lineHaulDollarRate.
	 */
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}

	/**
	 * @param lineHaulDollarRate The lineHaulDollarRate to set.
	 */
	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}

	/**
	 * @return Returns the lineHaulSDRRate.
	 */
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}

	/**
	 * @param lineHaulSDRRate The lineHaulSDRRate to set.
	 */
	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}

	/**
	 * @return Returns the perKiloDollarRate.
	 */
	public double getPerKiloDollarRate() {
		return perKiloDollarRate;
	}

	/**
	 * @param perKiloDollarRate The perKiloDollarRate to set.
	 */
	public void setPerKiloDollarRate(double perKiloDollarRate) {
		this.perKiloDollarRate = perKiloDollarRate;
	}

	/**
	 * @return Returns the perKiloSDRRate.
	 */
	public double getPerKiloSDRRate() {
		return perKiloSDRRate;
	}

	/**
	 * @param perKiloSDRRate The perKiloSDRRate to set.
	 */
	public void setPerKiloSDRRate(double perKiloSDRRate) {
		this.perKiloSDRRate = perKiloSDRRate;
	}

	/**
	 * @return Returns the receptacleIdentifier.
	 */
	public String getReceptacleIdentifier() {
		return receptacleIdentifier;
	}

	/**
	 * @param receptacleIdentifier The receptacleIdentifier to set.
	 */
	public void setReceptacleIdentifier(String receptacleIdentifier) {
		this.receptacleIdentifier = receptacleIdentifier;
	}

	/**
	 * @return Returns the sdrDate.
	 */
	public LocalDate getSdrDate() {
		return sdrDate;
	}

	/**
	 * @param sdrDate The sdrDate to set.
	 */
	public void setSdrDate(LocalDate sdrDate) {
		this.sdrDate = sdrDate;
	}

	/**
	 * @return Returns the sdrRate.
	 */
	public double getSdrRate() {
		return sdrRate;
	}

	/**
	 * @param sdrRate The sdrRate to set.
	 */
	public void setSdrRate(double sdrRate) {
		this.sdrRate = sdrRate;
	}

	/**
	 * @return Returns the sectorDestination.
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return Returns the sectorOrigin.
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}

	/**
	 * @return Returns the terminalHandlingDollarRate.
	 */
	public double getTerminalHandlingDollarRate() {
		return terminalHandlingDollarRate;
	}

	/**
	 * @param terminalHandlingDollarRate The terminalHandlingDollarRate to set.
	 */
	public void setTerminalHandlingDollarRate(double terminalHandlingDollarRate) {
		this.terminalHandlingDollarRate = terminalHandlingDollarRate;
	}

	/**
	 * @return Returns the terminalHandlingSDRRate.
	 */
	public double getTerminalHandlingSDRRate() {
		return terminalHandlingSDRRate;
	}

	/**
	 * @param terminalHandlingSDRRate The terminalHandlingSDRRate to set.
	 */
	public void setTerminalHandlingSDRRate(double terminalHandlingSDRRate) {
		this.terminalHandlingSDRRate = terminalHandlingSDRRate;
	}

	/**
	 * @return Returns the tripChargeDollarRate.
	 */
	public double getTripChargeDollarRate() {
		return tripChargeDollarRate;
	}

	/**
	 * @param tripChargeDollarRate The tripChargeDollarRate to set.
	 */
	public void setTripChargeDollarRate(double tripChargeDollarRate) {
		this.tripChargeDollarRate = tripChargeDollarRate;
	}

	/**
	 * @return Returns the tripChargeSDRRate.
	 */
	public double getTripChargeSDRRate() {
		return tripChargeSDRRate;
	}

	/**
	 * @param tripChargeSDRRate The tripChargeSDRRate to set.
	 */
	public void setTripChargeSDRRate(double tripChargeSDRRate) {
		this.tripChargeSDRRate = tripChargeSDRRate;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	
	
	
	
}