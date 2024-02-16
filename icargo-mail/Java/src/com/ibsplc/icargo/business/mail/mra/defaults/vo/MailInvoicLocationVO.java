/*
 * MailInvoicLocationVO.java created on Jul 19, 2007
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
public class MailInvoicLocationVO extends AbstractVO {
	private String companyCode;
	
	private String invoiceKey;
	
	private String poaCode;
	
	private String receptacleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private String carrierToPay;
	
	private String originPort;
	
	private String destinationPort;
	
	private String offlineOriginPort;
	
	private String truckingLocation;
	
	private String carrierFinalDestination;
	
	private String carrierAssigned;

	/**
	 * @return Returns the carrierAssigned.
	 */
	public String getCarrierAssigned() {
		return carrierAssigned;
	}

	/**
	 * @param carrierAssigned The carrierAssigned to set.
	 */
	public void setCarrierAssigned(String carrierAssigned) {
		this.carrierAssigned = carrierAssigned;
	}

	/**
	 * @return Returns the carrierFinalDestination.
	 */
	public String getCarrierFinalDestination() {
		return carrierFinalDestination;
	}

	/**
	 * @param carrierFinalDestination The carrierFinalDestination to set.
	 */
	public void setCarrierFinalDestination(String carrierFinalDestination) {
		this.carrierFinalDestination = carrierFinalDestination;
	}

	/**
	 * @return Returns the carrierToPay.
	 */
	public String getCarrierToPay() {
		return carrierToPay;
	}

	/**
	 * @param carrierToPay The carrierToPay to set.
	 */
	public void setCarrierToPay(String carrierToPay) {
		this.carrierToPay = carrierToPay;
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
	 * @return Returns the destinationPort.
	 */
	public String getDestinationPort() {
		return destinationPort;
	}

	/**
	 * @param destinationPort The destinationPort to set.
	 */
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
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
	 * @return Returns the offlineOriginPort.
	 */
	public String getOfflineOriginPort() {
		return offlineOriginPort;
	}

	/**
	 * @param offlineOriginPort The offlineOriginPort to set.
	 */
	public void setOfflineOriginPort(String offlineOriginPort) {
		this.offlineOriginPort = offlineOriginPort;
	}

	/**
	 * @return Returns the originPort.
	 */
	public String getOriginPort() {
		return originPort;
	}

	/**
	 * @param originPort The originPort to set.
	 */
	public void setOriginPort(String originPort) {
		this.originPort = originPort;
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
	 * @return Returns the truckingLocation.
	 */
	public String getTruckingLocation() {
		return truckingLocation;
	}

	/**
	 * @param truckingLocation The truckingLocation to set.
	 */
	public void setTruckingLocation(String truckingLocation) {
		this.truckingLocation = truckingLocation;
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