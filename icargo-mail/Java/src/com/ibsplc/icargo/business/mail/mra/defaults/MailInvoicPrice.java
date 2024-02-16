/*
 * MailInvoicPrice.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicPriceVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVPRI")
@Staleable
@Deprecated
public class MailInvoicPrice {
	
	private MailInvoicPricePK mailInvoicPricePK;
	
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
	
	private Calendar sdrDate;
	
	/**
	 * 
	 */
	public MailInvoicPrice(){
		
	}
	/**
	 * @param priceVO
	 * @throws SystemException
	 */
	public MailInvoicPrice(MailInvoicPriceVO priceVO)
	throws SystemException{
		MailInvoicPricePK pricePK= new MailInvoicPricePK();
		pricePK.setCompanyCode(priceVO.getCompanyCode());
		pricePK.setInvoiceKey(priceVO.getInvoiceKey());
		pricePK.setPoaCode(priceVO.getPoaCode());
		pricePK.setReceptacleIdentifier(priceVO.getReceptacleIdentifier());
		pricePK.setSectorDestination(priceVO.getSectorDestination());
		pricePK.setSectorOrigin(priceVO.getSectorOrigin());
		
		this.setMailInvoicPricePK(pricePK);
		populateAttributes(priceVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}

	/**
	 * @return Returns the containerRate.
	 */
	@Column(name="CNTRAT")
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
	 * @return Returns the lineHaulDollarRate.
	 */
	@Column(name="LINHALDOLRAT")
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
	@Column(name="LINHALSDRRAT")
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
	 * @return Returns the mailInvoicPricePK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD")),
			@AttributeOverride(name="receptacleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicPricePK getMailInvoicPricePK() {
		return mailInvoicPricePK;
	}

	/**
	 * @param mailInvoicPricePK The mailInvoicPricePK to set.
	 */
	public void setMailInvoicPricePK(MailInvoicPricePK mailInvoicPricePK) {
		this.mailInvoicPricePK = mailInvoicPricePK;
	}

	/**
	 * @return Returns the perKiloDollarRate.
	 */
	@Column(name="PERKLODOLRAT")
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
	@Column(name="PERKLOSDRRAT")
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
	 * @return Returns the sdrDate.
	 */
	@Column(name="SDRDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getSdrDate() {
		return sdrDate;
	}

	/**
	 * @param sdrDate The sdrDate to set.
	 */
	public void setSdrDate(Calendar sdrDate) {
		this.sdrDate = sdrDate;
	}

	/**
	 * @return Returns the sdrRate.
	 */
	@Column(name="SDRRAT")
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
	 * @return Returns the terminalHandlingDollarRate.
	 */
	@Column(name="TERHDLDOLRAT")
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
	@Column(name="TERHDLSDRRAT")
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
	@Column(name="TRPCHGDOLRAT")
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
	@Column(name="TRPCHGSDRRAT")
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
	 * @param priceVO
	 */
	private void populateAttributes(MailInvoicPriceVO priceVO){
		this.setLineHaulDollarRate(priceVO.getLineHaulDollarRate());
		this.setLineHaulSDRRate(priceVO.getLineHaulSDRRate());
		this.setTerminalHandlingDollarRate(priceVO.getTerminalHandlingDollarRate());
		this.setTerminalHandlingSDRRate(priceVO.getTerminalHandlingSDRRate());
		this.setPerKiloDollarRate(priceVO.getPerKiloDollarRate());
		this.setPerKiloSDRRate(priceVO.getPerKiloSDRRate());
		this.setTripChargeDollarRate(priceVO.getTripChargeDollarRate());
		this.setTripChargeSDRRate(priceVO.getTripChargeSDRRate());
		this.setContainerRate(priceVO.getContainerRate());
		this.setSdrRate(priceVO.getSdrRate());
		this.setSdrDate(priceVO.getSdrDate());
	}
	
	
	
}