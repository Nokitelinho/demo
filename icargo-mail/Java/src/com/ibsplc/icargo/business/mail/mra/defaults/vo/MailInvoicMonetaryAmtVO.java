/*
 * MailInvoicMonetaryAmtVO.java created on Jul 19, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicMonetaryAmtVO extends AbstractVO {
	
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
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
	 * @return Returns the adjustedLineHaulCharge.
	 */
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
	 * @return Returns the containerChargeAmount.
	 */
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
	 * @return Returns the lineHaulCharge.
	 */
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
	 * @return Returns the kiloChargeAmount.
	 */
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
	 * @return Returns the tripChargeAmount.
	 */
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
	 * @return Returns the terminalHandlingCharge.
	 */
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