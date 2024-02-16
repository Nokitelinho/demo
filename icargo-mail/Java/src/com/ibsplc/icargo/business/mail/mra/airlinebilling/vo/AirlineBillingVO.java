/*
 * AirlineBillingVO.java Created on Feb 15,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1946
 *
 */
public class AirlineBillingVO extends AbstractVO {
	
	private String companycode;
	
	private int airlineIdentifier;
	
	private String airlineCode;
	
	private String airlineNumber;
	
	private String billingBasis;
	
	private int totalPiecesReceived;
	
	private double totalWeightReceived;
	
	private double totalBillableWeight;

	private double totalBilledWeight;

	private double totalBillableAmount;

	private double totalBilledAmount;
	
	private Collection<AirlineBillingDetailVO> airlineBillingDetailVOs;

	/**
	 * @return Returns the airlineBillingDetailVOs.
	 */
	public Collection<AirlineBillingDetailVO> getAirlineBillingDetailVOs() {
		return airlineBillingDetailVOs;
	}

	/**
	 * @param airlineBillingDetailVOs The airlineBillingDetailVOs to set.
	 */
	public void setAirlineBillingDetailVOs(
			Collection<AirlineBillingDetailVO> airlineBillingDetailVOs) {
		this.airlineBillingDetailVOs = airlineBillingDetailVOs;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the totalBillableAmount.
	 */
	public double getTotalBillableAmount() {
		return totalBillableAmount;
	}

	/**
	 * @param totalBillableAmount The totalBillableAmount to set.
	 */
	public void setTotalBillableAmount(double totalBillableAmount) {
		this.totalBillableAmount = totalBillableAmount;
	}

	/**
	 * @return Returns the totalBillableWeight.
	 */
	public double getTotalBillableWeight() {
		return totalBillableWeight;
	}

	/**
	 * @param totalBillableWeight The totalBillableWeight to set.
	 */
	public void setTotalBillableWeight(double totalBillableWeight) {
		this.totalBillableWeight = totalBillableWeight;
	}

	/**
	 * @return Returns the totalBilledAmount.
	 */
	public double getTotalBilledAmount() {
		return totalBilledAmount;
	}

	/**
	 * @param totalBilledAmount The totalBilledAmount to set.
	 */
	public void setTotalBilledAmount(double totalBilledAmount) {
		this.totalBilledAmount = totalBilledAmount;
	}

	/**
	 * @return Returns the totalBilledWeight.
	 */
	public double getTotalBilledWeight() {
		return totalBilledWeight;
	}

	/**
	 * @param totalBilledWeight The totalBilledWeight to set.
	 */
	public void setTotalBilledWeight(double totalBilledWeight) {
		this.totalBilledWeight = totalBilledWeight;
	}

	/**
	 * @return Returns the totalPiecesReceived.
	 */
	public int getTotalPiecesReceived() {
		return totalPiecesReceived;
	}

	/**
	 * @param totalPiecesReceived The totalPiecesReceived to set.
	 */
	public void setTotalPiecesReceived(int totalPiecesReceived) {
		this.totalPiecesReceived = totalPiecesReceived;
	}

	/**
	 * @return Returns the totalWeightReceived.
	 */
	public double getTotalWeightReceived() {
		return totalWeightReceived;
	}

	/**
	 * @param totalWeightReceived The totalWeightReceived to set.
	 */
	public void setTotalWeightReceived(double totalWeightReceived) {
		this.totalWeightReceived = totalWeightReceived;
	}
		
}