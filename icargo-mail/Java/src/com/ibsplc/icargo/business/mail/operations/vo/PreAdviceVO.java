/*
 * PreAdviceVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */
public class PreAdviceVO extends AbstractVO {
	private String companyCode;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int legSerialNumber;

	private LocalDate flightDate;

	private String carrierCode;
  
	/**
	 * Added By karthick  V as the part of NCA Mail Tracking 
	 * to calculate the total Number of mail Bags for that Flight  which
	 * has  hit the system as the part of the Cardit Processing ...
	 */
	private int totalBags;
	
	/**
	 * Added By karthick  V as the part of NCA Mail Tracking 
	 * to calculate the total Number of Weight for all mail Bags for that Flight  which
	 * has  hit the system as the part of the Cardit Processing ...
	 */
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371
	
	
	private Collection<PreAdviceDetailsVO> preAdviceDetails;
    /**
     * 
     * @return totalWeight
     */
	public Measure getTotalWeight() {
		return totalWeight;
	}
   /**
    * 
    * @param totalWeight
    */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	/**
	 * @param legSerialNumber
	 *            The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the preAdviceDetails.
	 */
	public Collection<PreAdviceDetailsVO> getPreAdviceDetails() {
		return preAdviceDetails;
	}

	/**
	 * @param preAdviceDetails
	 *            The preAdviceDetails to set.
	 */
	public void setPreAdviceDetails(
			Collection<PreAdviceDetailsVO> preAdviceDetails) {
		this.preAdviceDetails = preAdviceDetails;
	}

	public int getTotalBags() {
		return totalBags;
	}

	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}

	/*public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/

}
