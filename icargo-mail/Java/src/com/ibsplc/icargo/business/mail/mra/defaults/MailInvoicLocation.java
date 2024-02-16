/*
 * MailInvoicLocation.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicLocationVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVLOC")
@Staleable
@Deprecated
public class MailInvoicLocation {
	private MailInvoicLocationPK mailInvoicLocationPK;
	
	private String carrierToPay;
	
	private String originPort;
	
	private String destinationPort;
	
	private String offlineOriginPort;
	
	private String truckingLocation;
	
	private String carrierFinalDestination;
	
	private String carrierAssigned;
	
	/**
	 * 
	 */
	public MailInvoicLocation(){
		
	}
	public MailInvoicLocation(MailInvoicLocationVO mailInvoicLocationVO)
	throws SystemException{
		MailInvoicLocationPK locationPK=new MailInvoicLocationPK();
		locationPK.setCompanyCode(mailInvoicLocationVO.getCompanyCode());
		locationPK.setInvoiceKey(mailInvoicLocationVO.getInvoiceKey());
		locationPK.setPoaCode(mailInvoicLocationVO.getPoaCode());
		locationPK.setReceptacleIdentifier(mailInvoicLocationVO.getReceptacleIdentifier());
		locationPK.setSectorDestination(mailInvoicLocationVO.getSectorDestination());
		locationPK.setSectorOrigin(mailInvoicLocationVO.getSectorOrigin());
		this.setMailInvoicLocationPK(locationPK);
		populateAttributes(mailInvoicLocationVO);
		
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}

	}
	/**
	 * @return Returns the carrierAssigned.
	 */
	@Column(name="CARASG")
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
	@Column(name="CARFINDST")
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
	@Column(name="PAYCAR")
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
	 * @return Returns the destinationPort.
	 */
	@Column(name="DSTPRT")
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
	 * @return Returns the mailInvoicLocationPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD")),
			@AttributeOverride(name="receptacleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicLocationPK getMailInvoicLocationPK() {
		return mailInvoicLocationPK;
	}

	/**
	 * @param mailInvoicLocationPK The mailInvoicLocationPK to set.
	 */
	public void setMailInvoicLocationPK(MailInvoicLocationPK mailInvoicLocationPK) {
		this.mailInvoicLocationPK = mailInvoicLocationPK;
	}

	/**
	 * @return Returns the offlineOriginPort.
	 */
	@Column(name="OFLORGPRT")
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
	@Column(name="ORGPRT")
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
	 * @return Returns the truckingLocation.
	 */
	@Column(name="TRKLOC")
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
	 * @param mailInvoicLocationVO
	 */
	private void populateAttributes(MailInvoicLocationVO mailInvoicLocationVO){
		this.setCarrierToPay(mailInvoicLocationVO.getCarrierToPay());
		this.setOriginPort(mailInvoicLocationVO.getOriginPort());
		this.setDestinationPort(mailInvoicLocationVO.getDestinationPort());
		this.setOfflineOriginPort(mailInvoicLocationVO.getOfflineOriginPort());
		this.setTruckingLocation(mailInvoicLocationVO.getTruckingLocation());
		this.setCarrierFinalDestination(mailInvoicLocationVO.getCarrierFinalDestination());
		this.setCarrierAssigned(mailInvoicLocationVO.getCarrierAssigned());
	}
}