/*
 * ULDAtAirportVO.java Created on Jun 30, 2016
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
 * TODO Add the purpose of this class
 * 
 * @author A-3109
 * 
 */

public class ULDAtAirportVO extends AbstractVO {

	private String companyCode;

	private String uldNumber;

	private String airportCode;

	private String finalDestination;

	private LocalDate assignedDate;

	private String assignedUser;

	private int carrierId;

	private String carrierCode;

	private int numberOfBags;

	//private double totalWeight;
	private Measure totalWeight;//added by A-7371

	private String remarks;

	private String warehouseCode;

	private String locationCode;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	private String transferFromCarrier;

	private Collection<DSNInULDAtAirportVO> dsnInULDAtAirportVOs;
	private Collection<MailbagInULDAtAirportVO>mailbagInULDAtAirportVOs;

	private Collection<OnwardRoutingAtAirportVO> onwardRoutingAtAirportVOs;
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
	 * @return Returns the acceptedBags.
	 */
	public int getNumberOfBags() {
		return numberOfBags;
	}

	/**
	 * @param acceptedBags
	 *            The acceptedBags to set.
	 */
	public void setNumberOfBags(int acceptedBags) {
		this.numberOfBags = acceptedBags;
	}

	/**
	 * @return Returns the acceptedWeight.
	 */
	/*public double getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param acceptedWeight
	 *            The acceptedWeight to set.
	 *//*
	public void setTotalWeight(double acceptedWeight) {
		this.totalWeight = acceptedWeight;
	}
*/
	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate
	 *            The assignedDate to set.
	 */
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assignedUser.
	 */
	public String getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser
	 *            The assignedUser to set.
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
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
	 * @return Returns the finalDestination.
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *            The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the dsnInULDAtAirportVOs.
	 */
	public Collection<DSNInULDAtAirportVO> getDsnInULDAtAirportVOs() {
		return dsnInULDAtAirportVOs;
	}

	/**
	 * @param dsnInULDAtAirportVOs
	 *            The dsnInULDAtAirportVOs to set.
	 */
	public void setDsnInULDAtAirportVOs(
			Collection<DSNInULDAtAirportVO> dsnInULDAtAirportVOs) {
		this.dsnInULDAtAirportVOs = dsnInULDAtAirportVOs;
	}

	/**
	 * @return Returns the onwardRoutingAtAirportVOs.
	 */
	public Collection<OnwardRoutingAtAirportVO> getOnwardRoutingAtAirportVOs() {
		return onwardRoutingAtAirportVOs;
	}

	/**
	 * @param onwardRoutingAtAirportVOs
	 *            The onwardRoutingAtAirportVOs to set.
	 */
	public void setOnwardRoutingAtAirportVOs(
			Collection<OnwardRoutingAtAirportVO> onwardRoutingAtAirportVOs) {
		this.onwardRoutingAtAirportVOs = onwardRoutingAtAirportVOs;
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
	 * @return Returns the locationCode.
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode
	 *            The locationCode to set.
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return Returns the warehouseCode.
	 */
	public String getWarehouseCode() {
		return warehouseCode;
	}

	/**
	 * @param warehouseCode
	 *            The warehouseCode to set.
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * @return Returns the fromCarrierCode.
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param fromCarrierCode
	 *            The fromCarrierCode to set.
	 */
	public void setTransferFromCarrier(String fromCarrierCode) {
		this.transferFromCarrier = fromCarrierCode;
	}

	/**
	 * @return the mailbagInULDAtAirportVOs
	 */
	public Collection<MailbagInULDAtAirportVO> getMailbagInULDAtAirportVOs() {
		return mailbagInULDAtAirportVOs;
	}

	/**
	 * @param mailbagInULDAtAirportVOs the mailbagInULDAtAirportVOs to set
	 */
	public void setMailbagInULDAtAirportVOs(
			Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs) {
		this.mailbagInULDAtAirportVOs = mailbagInULDAtAirportVOs;
	}
	
	
	

}
