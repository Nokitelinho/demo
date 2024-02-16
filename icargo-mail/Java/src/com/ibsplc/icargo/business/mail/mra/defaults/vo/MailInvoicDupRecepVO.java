/*
 * MailInvoicDupRecepVO.java created on Sep 17, 2007
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
public class MailInvoicDupRecepVO extends AbstractVO{
	private String companyCode;
	
	private String recepticleIdentifier;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private String originalInvoicRef;
	
	private String duplicateInvoicRef;

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
	 * @return Returns the duplicateInvoicRef.
	 */
	public String getDuplicateInvoicRef() {
		return duplicateInvoicRef;
	}

	/**
	 * @param duplicateInvoicRef The duplicateInvoicRef to set.
	 */
	public void setDuplicateInvoicRef(String duplicateInvoicRef) {
		this.duplicateInvoicRef = duplicateInvoicRef;
	}

	/**
	 * @return Returns the originalInvoicRef.
	 */
	public String getOriginalInvoicRef() {
		return originalInvoicRef;
	}

	/**
	 * @param originalInvoicRef The originalInvoicRef to set.
	 */
	public void setOriginalInvoicRef(String originalInvoicRef) {
		this.originalInvoicRef = originalInvoicRef;
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


	
}