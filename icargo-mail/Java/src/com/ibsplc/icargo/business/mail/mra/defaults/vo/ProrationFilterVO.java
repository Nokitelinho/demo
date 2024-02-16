/*
 * ProrationFilterVO.java created on Mar 06, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2518
 * 
 */
public class ProrationFilterVO extends AbstractVO {

	private String despatchSerialNumber;

	private String consigneeDocumentNumber;

	private int flightCarrierIdentifier;

	private String flightNumber;

	private LocalDate flightDate;

	private String companyCode;
	
	private long flightSeqNumber;
	
	private String baseCurrency;
	
	//For CR882
	
	private String billingBasis;
	
	private int consigneeSequenceNumber;
	
	private int sequenceNumber;
	
	private int serialNumber;
	
	private String poaCode;
	
	private String carrierCode;
	private long mailSquenceNumber;
	 
	
	

	/**
	 * @return the mailSquenceNumber
	 */
	public long getMailSquenceNumber() {
		return mailSquenceNumber;
	}

	/**
	 * @param mailSquenceNumber the mailSquenceNumber to set
	 */
	public void setMailSquenceNumber(long mailSquenceNumber) {
		this.mailSquenceNumber = mailSquenceNumber;
	}

	/**
	 * @return Returns the flightSeqNumber.
	 */
	public long getFlightSeqNumber() {
		return flightSeqNumber;
	}

	/**
	 * @param flightSeqNumber The flightSeqNumber to set.
	 */
	public void setFlightSeqNumber(long flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}

	/**
	 * @return Returns the consigneeDocumentNumber.
	 */
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}

	/**
	 * @param consigneeDocumentNumber
	 *            The consigneeDocumentNumber to set.
	 */
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber
	 *            The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
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
	 * @return Returns the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}
	/**
	 * @param baseCurrency The baseCurrency to set.
	 */

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	/**
	 * @return Returns the billingBasis
	 */
	
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return Returns the consigneeSequenceNumber
	 */

	public int getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}

	/**
	 * @param consigneeSequenceNumber The consigneeSequenceNumber to set.
	 */
	public void setConsigneeSequenceNumber(int consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}

	/**
	 * @return Returns the poaCode
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
	 * @return Returns the sequenceNumber
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
	 * @return Returns the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	

}
