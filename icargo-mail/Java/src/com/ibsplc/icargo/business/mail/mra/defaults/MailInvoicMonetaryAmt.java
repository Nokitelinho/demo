/*
 * MailInvoicMonetaryAmt.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicMonetaryAmtVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVMOA")
@Staleable
@Deprecated
public class MailInvoicMonetaryAmt {
	
	private MailInvoicMonetaryAmtPK mailInvoicMonetaryAmtPK;
	
	private double baseTotalPaymentAmount;
	
	private double adjustedTotalPayment;
	
	private double lineHaulCharge;
	
	private double adjustedLineHaulCharge;
	
	private double terminalHandlingCharge;
	
	private double adjustedTerminalHandlingCharge;
	
	private double tripChargeAmount;
	
	private double kiloChargeAmount;
	
	private double containerChargeAmount;
	
	/**
	 * 
	 */
	public MailInvoicMonetaryAmt(){
		
	}
	/**
	 * @param monetaryVO
	 * @throws SystemException
	 */
	public MailInvoicMonetaryAmt(MailInvoicMonetaryAmtVO monetaryVO)
	throws SystemException{
		MailInvoicMonetaryAmtPK monetaryPK=new MailInvoicMonetaryAmtPK();
		monetaryPK.setCompanyCode(monetaryVO.getCompanyCode());
		monetaryPK.setInvoiceKey(monetaryVO.getInvoiceKey());
		monetaryPK.setPoaCode(monetaryVO.getPoaCode());
		monetaryPK.setReceptacleIdentifier(monetaryVO.getReceptacleIdentifier());
		monetaryPK.setSectorDestination(monetaryVO.getSectorDestination());
		monetaryPK.setSectorOrigin(monetaryVO.getSectorOrigin());
		this.setMailInvoicMonetaryAmtPK(monetaryPK);
		populateAttributes(monetaryVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
		
	}
	/**
	 * @return Returns the adjustedLineHaulCharge.
	 */
	@Column(name="ADJLINHALCHG")
	public double getAdjustedLineHaulCharge() {
		return adjustedLineHaulCharge;
	}

	/**
	 * @param adjustedLineHaulCharge The adjustedLineHaulCharge to set.
	 */
	public void setAdjustedLineHaulCharge(double adjustedLineHaulCharge) {
		this.adjustedLineHaulCharge = adjustedLineHaulCharge;
	}

	/**
	 * @return Returns the adjustedTerminalHandlingCharge.
	 */
	@Column(name="ADJTERHALCHG")
	public double getAdjustedTerminalHandlingCharge() {
		return adjustedTerminalHandlingCharge;
	}

	/**
	 * @param adjustedTerminalHandlingCharge The adjustedTerminalHandlingCharge to set.
	 */
	public void setAdjustedTerminalHandlingCharge(
			double adjustedTerminalHandlingCharge) {
		this.adjustedTerminalHandlingCharge = adjustedTerminalHandlingCharge;
	}

	/**
	 * @return Returns the adjustedTotalPayment.
	 */
	@Column(name="ADJTOTPMT")
	public double getAdjustedTotalPayment() {
		return adjustedTotalPayment;
	}

	/**
	 * @param adjustedTotalPayment The adjustedTotalPayment to set.
	 */
	public void setAdjustedTotalPayment(double adjustedTotalPayment) {
		this.adjustedTotalPayment = adjustedTotalPayment;
	}

	/**
	 * @return Returns the baseTotalPaymentAmount.
	 */
	@Column(name="BASTOTAMT")
	public double getBaseTotalPaymentAmount() {
		return baseTotalPaymentAmount;
	}

	/**
	 * @param baseTotalPaymentAmount The baseTotalPaymentAmount to set.
	 */
	public void setBaseTotalPaymentAmount(double baseTotalPaymentAmount) {
		this.baseTotalPaymentAmount = baseTotalPaymentAmount;
	}

	/**
	 * @return Returns the containerChargeAmount.
	 */
	@Column(name="CNTCHGAMT")
	public double getContainerChargeAmount() {
		return containerChargeAmount;
	}

	/**
	 * @param containerChargeAmount The containerChargeAmount to set.
	 */
	public void setContainerChargeAmount(double containerChargeAmount) {
		this.containerChargeAmount = containerChargeAmount;
	}

	/**
	 * @return Returns the kiloChargeAmount.
	 */
	@Column(name="KLOCHGAMT")
	public double getKiloChargeAmount() {
		return kiloChargeAmount;
	}

	/**
	 * @param kiloChargeAmount The kiloChargeAmount to set.
	 */
	public void setKiloChargeAmount(double kiloChargeAmount) {
		this.kiloChargeAmount = kiloChargeAmount;
	}

	/**
	 * @return Returns the lineHaulCharge.
	 */
	@Column(name="LINHALCHG")
	public double getLineHaulCharge() {
		return lineHaulCharge;
	}

	/**
	 * @param lineHaulCharge The lineHaulCharge to set.
	 */
	public void setLineHaulCharge(double lineHaulCharge) {
		this.lineHaulCharge = lineHaulCharge;
	}

	/**
	 * @return Returns the mailInvoicMonetaryAmtPK.
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
	public MailInvoicMonetaryAmtPK getMailInvoicMonetaryAmtPK() {
		return mailInvoicMonetaryAmtPK;
	}

	/**
	 * @param mailInvoicMonetaryAmtPK The mailInvoicMonetaryAmtPK to set.
	 */
	public void setMailInvoicMonetaryAmtPK(
			MailInvoicMonetaryAmtPK mailInvoicMonetaryAmtPK) {
		this.mailInvoicMonetaryAmtPK = mailInvoicMonetaryAmtPK;
	}

	/**
	 * @return Returns the terminalHandlingCharge.
	 */
	@Column(name="TERHANCHG")
	public double getTerminalHandlingCharge() {
		return terminalHandlingCharge;
	}

	/**
	 * @param terminalHandlingCharge The terminalHandlingCharge to set.
	 */
	public void setTerminalHandlingCharge(double terminalHandlingCharge) {
		this.terminalHandlingCharge = terminalHandlingCharge;
	}

	/**
	 * @return Returns the tripChargeAmount.
	 */
	@Column(name="TRPCHGAMT")
	public double getTripChargeAmount() {
		return tripChargeAmount;
	}

	/**
	 * @param tripChargeAmount The tripChargeAmount to set.
	 */
	public void setTripChargeAmount(double tripChargeAmount) {
		this.tripChargeAmount = tripChargeAmount;
	}
	
	
	/**
	 * @param monetaryVO
	 */
	private void populateAttributes(MailInvoicMonetaryAmtVO monetaryVO){
		this.setBaseTotalPaymentAmount(monetaryVO.getBaseTotalPaymentAmount());
		this.setAdjustedTotalPayment(monetaryVO.getAdjustedTotalPayment());
		this.setLineHaulCharge(monetaryVO.getLineHaulCharge());
		this.setAdjustedLineHaulCharge(monetaryVO.getAdjustedLineHaulCharge());
		this.setTerminalHandlingCharge(monetaryVO.getTerminalHandlingCharge());
		this.setAdjustedTerminalHandlingCharge(monetaryVO.getAdjustedTerminalHandlingCharge());
		this.setTripChargeAmount(monetaryVO.getTripChargeAmount());
		this.setKiloChargeAmount(monetaryVO.getKiloChargeAmount());
		this.setContainerChargeAmount(monetaryVO.getContainerChargeAmount());
	}
	
	
}