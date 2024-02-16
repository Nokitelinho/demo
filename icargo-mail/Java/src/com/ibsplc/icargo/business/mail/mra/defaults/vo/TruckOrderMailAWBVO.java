package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailAWBVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	17-Jul-2018	:	Draft
 */
public class TruckOrderMailAWBVO extends AbstractVO{

	
	private String segmentOrgin;
	private String segmentDestination;
	private int segmentSerialNumber;
	
	private Money totalTruckCharge;
	private Money totalOtherCharge;
	private Money otherChargeVAT;
	private Money truckChargeVAT;
	
	
	
	private int documentOwnerId;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	
	/**
	 * 	Getter for segmentOrgin 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getSegmentOrgin() {
		return segmentOrgin;
	}
	/**
	 *  @param segmentOrgin the segmentOrgin to set
	 * 	Setter for segmentOrgin 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setSegmentOrgin(String segmentOrgin) {
		this.segmentOrgin = segmentOrgin;
	}
	/**
	 * 	Getter for segmentDestination 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getSegmentDestination() {
		return segmentDestination;
	}
	/**
	 *  @param segmentDestination the segmentDestination to set
	 * 	Setter for segmentDestination 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setSegmentDestination(String segmentDestination) {
		this.segmentDestination = segmentDestination;
	}
	/**
	 * 	Getter for segmentSerialNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 *  @param segmentSerialNumber the segmentSerialNumber to set
	 * 	Setter for segmentSerialNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * 	Getter for totalTruckCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Money getTotalTruckCharge() {
		return totalTruckCharge;
	}
	/**
	 *  @param totalTruckCharges the totalTruckCharges to set
	 * 	Setter for totalTruckCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTotalTruckCharge(Money totalTruckCharge) {
		this.totalTruckCharge = totalTruckCharge;
	}
	/**
	 * 	Getter for totalOtherCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Money getTotalOtherCharge() {
		return totalOtherCharge;
	}
	/**
	 *  @param totalOtherCharges the totalOtherCharges to set
	 * 	Setter for totalOtherCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTotalOtherCharge(Money totalOtherCharge) {
		this.totalOtherCharge = totalOtherCharge;
	}
	/**
	 * 	Getter for documentOwnerId 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getDocumentOwnerId() {
		return documentOwnerId;
	}
	/**
	 *  @param documentOwnerId the documentOwnerId to set
	 * 	Setter for documentOwnerId 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setDocumentOwnerId(int documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for masterDocumentNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 *  @param masterDocumentNumber the masterDocumentNumber to set
	 * 	Setter for masterDocumentNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	/**
	 * 	Getter for duplicateNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	/**
	 *  @param duplicateNumber the duplicateNumber to set
	 * 	Setter for duplicateNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * 	Getter for sequenceNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 *  @param sequenceNumber the sequenceNumber to set
	 * 	Setter for sequenceNumber 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	/**
	 * 	Getter for otherChargeVAT 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Money getOtherChargeVAT() {
		return otherChargeVAT;
	}
	/**
	 *  @param otherChargeVAT the otherChargeVAT to set
	 * 	Setter for otherChargeVAT 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setOtherChargeVAT(Money otherChargeVAT) {
		this.otherChargeVAT = otherChargeVAT;
	}

	/**
	 * 	Getter for truckChargeVAT 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Money getTruckChargeVAT() {
		return truckChargeVAT;
	}
	/**
	 *  @param truckChargeVAT the truckChargeVAT to set
	 * 	Setter for truckChargeVAT 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTruckChargeVAT(Money truckChargeVAT) {
		this.truckChargeVAT = truckChargeVAT;
	}
	
}
