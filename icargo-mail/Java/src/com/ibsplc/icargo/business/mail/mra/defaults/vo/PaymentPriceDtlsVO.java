/*
 * PaymentPriceDtlsVO.java created on Jul 27, 2007
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
 * @author A-2270
 *
 */
public class PaymentPriceDtlsVO extends AbstractVO {
	
	private double baseTotalAmount;
	private double adjustmentTotalAmount;
	private double lineHaulCaharge;
	private double terminalHandlingCharge;
	private double lineHaulDollarRate;
	private double lineHaulSDRRate;
	private double tHDollarRate;
	private double tHSDRRate;
	private double containerRate;
	private double  sDRRate;
	private LocalDate sDRDate;
	/**
	 * @return the adjustmentTotalAmount
	 */
	public double getAdjustmentTotalAmount() {
		return adjustmentTotalAmount;
	}
	/**
	 * @param adjustmentTotalAmount the adjustmentTotalAmount to set
	 */
	public void setAdjustmentTotalAmount(double adjustmentTotalAmount) {
		this.adjustmentTotalAmount = adjustmentTotalAmount;
	}
	/**
	 * @return the baseTotalAmount
	 */
	public double getBaseTotalAmount() {
		return baseTotalAmount;
	}
	/**
	 * @param baseTotalAmount the baseTotalAmount to set
	 */
	public void setBaseTotalAmount(double baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}
	/**
	 * @return the containerRate
	 */
	public double getContainerRate() {
		return containerRate;
	}
	/**
	 * @param containerRate the containerRate to set
	 */
	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}
	/**
	 * @return the lineHaulCaharge
	 */
	public double getLineHaulCaharge() {
		return lineHaulCaharge;
	}
	/**
	 * @param lineHaulCaharge the lineHaulCaharge to set
	 */
	public void setLineHaulCaharge(double lineHaulCaharge) {
		this.lineHaulCaharge = lineHaulCaharge;
	}
	/**
	 * @return the lineHaulDollarRate
	 */
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}
	/**
	 * @param lineHaulDollarRate the lineHaulDollarRate to set
	 */
	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}
	/**
	 * @return the lineHaulSDRRate
	 */
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}
	/**
	 * @param lineHaulSDRRate the lineHaulSDRRate to set
	 */
	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}
	/**
	 * @return the sDRDate
	 */
	public LocalDate getSDRDate() {
		return sDRDate;
	}
	/**
	 * @param date the sDRDate to set
	 */
	public void setSDRDate(LocalDate date) {
		sDRDate = date;
	}
	/**
	 * @return the sDRRate
	 */
	public double getSDRRate() {
		return sDRRate;
	}
	/**
	 * @param rate the sDRRate to set
	 */
	public void setSDRRate(double rate) {
		sDRRate = rate;
	}
	/**
	 * @return the terminalHandlingCharge
	 */
	public double getTerminalHandlingCharge() {
		return terminalHandlingCharge;
	}
	/**
	 * @param terminalHandlingCharge the terminalHandlingCharge to set
	 */
	public void setTerminalHandlingCharge(double terminalHandlingCharge) {
		this.terminalHandlingCharge = terminalHandlingCharge;
	}
	/**
	 * @return the tHDollarRate
	 */
	public double getTHDollarRate() {
		return tHDollarRate;
	}
	/**
	 * @param dollarRate the tHDollarRate to set
	 */
	public void setTHDollarRate(double dollarRate) {
		tHDollarRate = dollarRate;
	}
	/**
	 * @return the tHSDRRate
	 */
	public double getTHSDRRate() {
		return tHSDRRate;
	}
	/**
	 * @param rate the tHSDRRate to set
	 */
	public void setTHSDRRate(double rate) {
		tHSDRRate = rate;
	}
	
	
    
	
		
	
}