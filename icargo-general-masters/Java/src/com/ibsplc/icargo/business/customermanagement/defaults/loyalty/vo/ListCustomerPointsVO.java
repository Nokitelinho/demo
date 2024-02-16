/*
 * ListCustomerPointsVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ListCustomerPointsVO  extends AbstractVO {
	/**
	 * Attribute Weight
	 */
	public static final String WEIGHT_ATTRIBUTE = "Weight";
	/**
	 * Attribute Volume
	 */
	public static final String VOLUME_ATTRIBUTE = "Volume";
	/**
	 * Attribute Distance
	 */
	public static final String DISTANCE_ATTRIBUTE = "Distance";
	/**
	 * Attribute Revenue
	 */
	public static final String REVENUE_ATTRIBUTE = "Revenue";
	/**
	 * Attribute Yield
	 */
	public static final String YIELD_ATTRIBUTE = "Yield";

	private String companyCode;
	private String customerCode;
	private String masterAwbNumber;
	private int documentOwnerIdentifier;
	private int duplicateNumber;
    private int sequenceNumber;
    private String awbNumber;
    private Collection<String> loyaltyProgrammes;
    private double weight;
    private double yield;
    private double distance;
    private double revenue;
    private double volume;
    private double points;
    private double negativePoints;
    private LocalDate lastUpdateTime;
    
	/**
	 * @return Returns the awbNumber.
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
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
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the distance.
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance The distance to set.
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * @return Returns the documentOwnerIdentifier.
	 */
	public int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}
	/**
	 * @param documentOwnerIdentifier The documentOwnerIdentifier to set.
	 */
	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}
	/**
	 * @return Returns the duplicateNumber.
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	/**
	 * @param duplicateNumber The duplicateNumber to set.
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * @return Returns the masterAwbNumber.
	 */
	public String getMasterAwbNumber() {
		return masterAwbNumber;
	}
	/**
	 * @param masterAwbNumber The masterAwbNumber to set.
	 */
	public void setMasterAwbNumber(String masterAwbNumber) {
		this.masterAwbNumber = masterAwbNumber;
	}
	/**
	 * @return Returns the negativePoints.
	 */
	public double getNegativePoints() {
		return negativePoints;
	}
	/**
	 * @param negativePoints The negativePoints to set.
	 */
	public void setNegativePoints(double negativePoints) {
		this.negativePoints = negativePoints;
	}
	/**
	 * @return Returns the points.
	 */
	public double getPoints() {
		return points;
	}
	/**
	 * @param points The points to set.
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return Returns the revenue.
	 */
	public double getRevenue() {
		return revenue;
	}
	/**
	 * @param revenue The revenue to set.
	 */
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return Returns the yield.
	 */
	public double getYield() {
		return yield;
	}
	/**
	 * @param yield The yield to set.
	 */
	public void setYield(double yield) {
		this.yield = yield;
	}
	/**
	 * @return Returns the loyaltyProgrammes.
	 */
	public Collection<String> getLoyaltyProgrammes() {
		return loyaltyProgrammes;
	}
	/**
	 * @param loyaltyProgrammes The loyaltyProgrammes to set.
	 */
	public void setLoyaltyProgrammes(Collection<String> loyaltyProgrammes) {
		this.loyaltyProgrammes = loyaltyProgrammes;
	}
	/**
	 * @return Returns the volume.
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @param volume The volume to set.
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
