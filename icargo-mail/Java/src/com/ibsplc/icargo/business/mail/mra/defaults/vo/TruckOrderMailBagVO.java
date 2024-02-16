package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailBagVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	17-Jul-2018	:	Draft
 */
public class TruckOrderMailBagVO extends AbstractVO{

	
	private String segmentOrgin;
	private String segmentDestination;
	private int segmentSerialNumber;
	
	private Money totalTruckCharges;
	private Money totalOtherCharges;
	
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
	public Money getTotalTruckCharges() {
		return totalTruckCharges;
	}
	/**
	 *  @param totalTruckCharges the totalTruckCharges to set
	 * 	Setter for totalTruckCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTotalTruckCharges(Money totalTruckCharges) {
		this.totalTruckCharges = totalTruckCharges;
	}
	/**
	 * 	Getter for totalOtherCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public Money getTotalOtherCharges() {
		return totalOtherCharges;
	}
	/**
	 *  @param totalOtherCharges the totalOtherCharges to set
	 * 	Setter for totalOtherCharges 
	 *	Added by : a-8061 on 17-Jul-2018
	 * 	Used for :
	 */
	public void setTotalOtherCharges(Money totalOtherCharges) {
		this.totalOtherCharges = totalOtherCharges;
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

	
	
	
	
}
