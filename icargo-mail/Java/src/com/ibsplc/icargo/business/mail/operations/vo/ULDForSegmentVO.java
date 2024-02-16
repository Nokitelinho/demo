/*
 * ULDForSegmentVO.java Created on Jun 30, 2016
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
public class ULDForSegmentVO extends AbstractVO {
	private String companyCode;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private String carrierCode;

	private LocalDate flightDate;

	/**
	 * For Bulk , String is BULK-POU
	 */
	private String uldNumber;

	private String pou;

	private String operationalFlag;

	private int receivedBags;

	//private double receivedWeight;
	private Measure receivedWeight;//added by A-7371

	/**
	 * No of mailbags in this ULD
	 */
	private int noOfBags;

	/**
	 * total weight of this ULD
	 */
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371

	private String remarks;

	private String warehouseCode;

	private String locationCode;

	private String lastUpdateUser;

	private String transferFromCarrier;

	private String transferToCarrier;

	private String releasedFlag;
	/**
	 * The despatches in this ULD
	 */
	private Collection<DSNInULDForSegmentVO> dsnInULDForSegmentVOs;
	private Collection<MailbagInULDForSegmentVO>mailbagInULDForSegmentVOs;

	/**
	 * The onwardRouting of this ULD
	 */
	private Collection<OnwardRouteForSegmentVO> onwardRoutings;
    /**
     * 
     * @return totalWeight
     */
	public Measure getReceivedWeight() {
		return receivedWeight;
	}
    /**
     * 
     * @param receivedWeight
     */
	public void setReceivedWeight(Measure receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
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
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag
	 *            The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
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
	 * @return Returns the dnsInULDForSegmentVOs.
	 */
	public Collection<DSNInULDForSegmentVO> getDsnInULDForSegmentVOs() {
		return dsnInULDForSegmentVOs;
	}

	/**
	 * @param dnsInULDForSegmentVOs
	 *            The dnsInULDForSegmentVOs to set.
	 */
	public void setDsnInULDForSegmentVOs(
			Collection<DSNInULDForSegmentVO> dnsInULDForSegmentVOs) {
		this.dsnInULDForSegmentVOs = dnsInULDForSegmentVOs;
	}

	/**
	 * @return Returns the noOfBags.
	 */
	public int getNoOfBags() {
		return noOfBags;
	}

	/**
	 * @param noOfBags
	 *            The noOfBags to set.
	 */
	public void setNoOfBags(int noOfBags) {
		this.noOfBags = noOfBags;
	}

	/**
	 * @return Returns the onwardRoutings.
	 */
	public Collection<OnwardRouteForSegmentVO> getOnwardRoutings() {
		return onwardRoutings;
	}

	/**
	 * @param onwardRoutings
	 *            The onwardRoutings to set.
	 */
	public void setOnwardRoutings(
			Collection<OnwardRouteForSegmentVO> onwardRoutings) {
		this.onwardRoutings = onwardRoutings;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	/*public double getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param totalWeight
	 *            The totalWeight to set.
	 *//*
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/

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
	 * @return Returns the receivedBags.
	 */
	public int getReceivedBags() {
		return receivedBags;
	}

	/**
	 * @param receivedBags
	 *            The receivedBags to set.
	 */
	public void setReceivedBags(int receivedBags) {
		this.receivedBags = receivedBags;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	/*public double getReceivedWeight() {
		return receivedWeight;
	}

	*//**
	 * @param receivedWeight
	 *            The receivedWeight to set.
	 *//*
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}
*/
	/**
	 * @return Returns the transferFromCarrier.
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param transferFromCarrier
	 *            The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}

	/**
	 * @return Returns the transferToCarrier.
	 */
	public String getTransferToCarrier() {
		return transferToCarrier;
	}

	/**
	 * @param transferToCarrier
	 *            The transferToCarrier to set.
	 */
	public void setTransferToCarrier(String transferToCarrier) {
		this.transferToCarrier = transferToCarrier;
	}

	/**
	 * @return the releasedFlag
	 */
	public String getReleasedFlag() {
		return releasedFlag;
	}

	/**
	 * @param releasedFlag
	 *            the releasedFlag to set
	 */
	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}

	/**
	 * @return the mailbagInULDForSegmentVOs
	 */
	public Collection<MailbagInULDForSegmentVO> getMailbagInULDForSegmentVOs() {
		return mailbagInULDForSegmentVOs;
	}

	/**
	 * @param mailbagInULDForSegmentVOs the mailbagInULDForSegmentVOs to set
	 */
	public void setMailbagInULDForSegmentVOs(
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs) {
		this.mailbagInULDForSegmentVOs = mailbagInULDForSegmentVOs;
	}
	
}
