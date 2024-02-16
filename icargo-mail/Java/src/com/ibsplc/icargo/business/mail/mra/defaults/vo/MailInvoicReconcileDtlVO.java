/*
 * MailInvoicReconcileDtlVO.java created on Sep 17, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 *
 */
public class MailInvoicReconcileDtlVO extends AbstractVO{
	private String companyCode;
	
	private String recepticleIdentifier;
	
	private String sectorIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private String sectorFlight;
	
	private LocalDate scanDate;
	
	private  String invoicKey;
	
	private LocalDate claimDate;
	
	private String claimCode;
	
	private String claimStatus;
	
	private double claimAmount;
	
	private String claimKey;
	
	private String poaCode;

	

	/**
	 * @return Returns the claimAmount.
	 */
	public double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * @param claimAmount The claimAmount to set.
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * @return Returns the claimCode.
	 */
	public String getClaimCode() {
		return claimCode;
	}

	/**
	 * @param claimCode The claimCode to set.
	 */
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	/**
	 * @return Returns the claimDate.
	 */
	public LocalDate getClaimDate() {
		return claimDate;
	}

	/**
	 * @param claimDate The claimDate to set.
	 */
	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}

	/**
	 * @return Returns the claimKey.
	 */
	public String getClaimKey() {
		return claimKey;
	}

	/**
	 * @param claimKey The claimKey to set.
	 */
	public void setClaimKey(String claimKey) {
		this.claimKey = claimKey;
	}

	/**
	 * @return Returns the claimStatus.
	 */
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * @param claimStatus The claimStatus to set.
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
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
	 * @return Returns the invoicKey.
	 */
	public String getInvoicKey() {
		return invoicKey;
	}

	/**
	 * @param invoicKey The invoicKey to set.
	 */
	public void setInvoicKey(String invoicKey) {
		this.invoicKey = invoicKey;
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

	/**
	 * @return Returns the recepticleIdentifier.
	 */
	public String getRecepticleIdentifier() {
		return recepticleIdentifier;
	}

	/**
	 * @param recepticleIdentifier The recepticleIdentifier to set.
	 */
	public void setRecepticleIdentifier(String recepticleIdentifier) {
		this.recepticleIdentifier = recepticleIdentifier;
	}

	/**
	 * @return Returns the scanDate.
	 */
	public LocalDate getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate The scanDate to set.
	 */
	public void setScanDate(LocalDate scanDate) {
		this.scanDate = scanDate;
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
	 * @return Returns the sectorFlight.
	 */
	public String getSectorFlight() {
		return sectorFlight;
	}

	/**
	 * @param sectorFlight The sectorFlight to set.
	 */
	public void setSectorFlight(String sectorFlight) {
		this.sectorFlight = sectorFlight;
	}

	/**
	 * @return Returns the sectorIdentifier.
	 */
	public String getSectorIdentifier() {
		return sectorIdentifier;
	}

	/**
	 * @param sectorIdentifier The sectorIdentifier to set.
	 */
	public void setSectorIdentifier(String sectorIdentifier) {
		this.sectorIdentifier = sectorIdentifier;
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

	
	
	
}