/*
 * SurchargeCCAdetailsVO.java Created on Jul 9, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5255 
 * @version	0.1, Jul 9, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 9, 2015	     A-5255		First draft
 */

public class SurchargeCCAdetailsVO  extends AbstractVO{
	private String companyCode;
	private String billingBasis;
	private String ccaRefNumber;	
	private int csgSequenceNumber;	
	private String csgDocumentNumber;	
	private String poaCode;
	private String chargeHeadName;
	private Money revSurCharge;
	private Money orgSurCharge;
	//a-8061 added for ICRD-254294
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	//added by A-7371 as part of ICRD-257661
	private long mailSequenceNumber;
	
	//Added by A-7540 
	private double surchareOrgRate;
	private double surchargeRevRate;
	
	
	
	public double getSurchareOrgRate() {
		return surchareOrgRate;
	}
	public void setSurchareOrgRate(double surchareOrgRate) {
		this.surchareOrgRate = surchareOrgRate;
	}
	public double getSurchargeRevRate() {
		return surchargeRevRate;
	}
	public void setSurchargeRevRate(double surchargeRevRate) {
		this.surchargeRevRate = surchargeRevRate;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return the ccaRefNumber
	 */
	public String getCcaRefNumber() {
		return ccaRefNumber;
	}
	/**
	 * @param ccaRefNumber the ccaRefNumber to set
	 */
	public void setCcaRefNumber(String ccaRefNumber) {
		this.ccaRefNumber = ccaRefNumber;
	}
	/**
	 * @return the csgSequenceNumber
	 */
	public int getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	/**
	 * @param csgSequenceNumber the csgSequenceNumber to set
	 */
	public void setCsgSequenceNumber(int csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}
	/**
	 * @return the csgDocumentNumber
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber the csgDocumentNumber to set
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	/**
	 * @return the chargeHeadName
	 */
	public String getChargeHeadName() {
		return chargeHeadName;
	}
	/**
	 * @param chargeHeadName the chargeHeadName to set
	 */
	public void setChargeHeadName(String chargeHeadName) {
		this.chargeHeadName = chargeHeadName;
	}
	/**
	 * @return the revSurCharge
	 */
	public Money getRevSurCharge() {
		return revSurCharge;
	}
	/**
	 * @param revSurCharge the revSurCharge to set
	 */
	public void setRevSurCharge(Money revSurCharge) {
		this.revSurCharge = revSurCharge;
	}
	/**
	 * @return the orgSurCharge
	 */
	public Money getOrgSurCharge() {
		return orgSurCharge;
	}
	/**
	 * @param orgSurCharge the orgSurCharge to set
	 */
	public void setOrgSurCharge(Money orgSurCharge) {
		this.orgSurCharge = orgSurCharge;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * 
	 * @return mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * 
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	
	
}
