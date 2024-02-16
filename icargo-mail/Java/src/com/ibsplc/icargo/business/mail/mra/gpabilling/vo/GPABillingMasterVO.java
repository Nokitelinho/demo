/*
 * GPABillingMasterVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;


import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class GPABillingMasterVO extends AbstractVO {
    private String companyCode;
    private String billingBasis;
    private int totalPiecesReceived;
    private double totalBillableWeight;
    private double totalBilledWeight;
    private double totalBillableAmount;
    private double totalBilledAmount;
    //TODO Collection<gpaBillingDetailsVO>
    private Collection<GPABillingDetailsVO> gpaBillingDetails;
    private String basisType;
    private String currencyCode;

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
     * @return Returns the gpaBillingDetails.
     */
    public Collection<GPABillingDetailsVO> getgpaBillingDetails() {
        return gpaBillingDetails;
    }
    /**
     * @param gpaBillingDetails The gpaBillingDetails to set.
     */
    public void setgpaBillingDetails(Collection<GPABillingDetailsVO> gpaBillingDetails) {
        this.gpaBillingDetails = gpaBillingDetails;
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
	 * @return Returns the basisType.
	 */
	public String getBasisType() {
		return basisType;
	}
	/**
	 * @param basisType The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}
	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
    
	
}
