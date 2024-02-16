/*
 * BillingLineDetailVO.java Created on Jun 12, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5255 
 * @version	0.1, Jun 12, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 12, 2015	     A-5255		First draft
 */

public class BillingLineDetailVO extends AbstractVO{
	public final static  String MAIL_CHARGETYPE="M";
	public final static  String SUR_CHARGETYPE="S";
	
	
	public final static  String FLAT_RATE="FR";
	public final static  String FLAT_CHARGE="FC";
	public final static  String WEIGHT_BREAK="WB";
	public final static  String USPS="US";
	
	public final static  String SECURITY_SURCHARGE="SS";
	public final static  String HANDLING_CHARGE="HC";
	public final static  String FUEL_SURCHARGE="FS";

	private String companycode;
	private String billingMatrixID;
	 private int billingLineSequenceNumber;
	private String chargeType;
	private String chargeTypeDesc;
	private String ratingBasis;
	private String forKeyCheck;
	private Collection<BillingLineChargeVO> billingLineCharges;
	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return the ratingBasis
	 */
	public String getRatingBasis() {
		return ratingBasis;
	}
	/**
	 * @param ratingBasis the ratingBasis to set
	 */
	public void setRatingBasis(String ratingBasis) {
		this.ratingBasis = ratingBasis;
	}
	/**
	 * @return the billingLineCharges
	 */
	public Collection<BillingLineChargeVO> getBillingLineCharges() {
		return billingLineCharges;
	}
	/**
	 * @param billingLineCharges the billingLineCharges to set
	 */
	public void setBillingLineCharges(
			Collection<BillingLineChargeVO> billingLineCharges) {
		this.billingLineCharges = billingLineCharges;
	}
	/**
	 * @return the companycode
	 */
	public String getCompanycode() {
		return companycode;
	}
	/**
	 * @param companycode the companycode to set
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	/**
	 * @return the billingMatrixID
	 */
	public String getBillingMatrixID() {
		return billingMatrixID;
	}
	/**
	 * @param billingMatrixID the billingMatrixID to set
	 */
	public void setBillingMatrixID(String billingMatrixID) {
		this.billingMatrixID = billingMatrixID;
	}
	/**
	 * @return the billingLineSequenceNumber
	 */
	public int getBillingLineSequenceNumber() {
		return billingLineSequenceNumber;
	}
	/**
	 * @param billingLineSequenceNumber the billingLineSequenceNumber to set
	 */
	public void setBillingLineSequenceNumber(int billingLineSequenceNumber) {
		this.billingLineSequenceNumber = billingLineSequenceNumber;
	}
	/**
	 * @return the chargeTypeDesc
	 */
	public String getChargeTypeDesc() {
		return chargeTypeDesc;
	}
	/**
	 * @param chargeTypeDesc the chargeTypeDesc to set
	 */
	public void setChargeTypeDesc(String chargeTypeDesc) {
		this.chargeTypeDesc = chargeTypeDesc;
	}
	/**
	 * 	Getter for forKeyCheck 
	 *	Added by : A-4809 on Jul 27, 2016
	 * 	Used for :
	 */
	public String getForKeyCheck() {
		return forKeyCheck;
	}
	/**
	 *  @param forKeyCheck the forKeyCheck to set
	 * 	Setter for forKeyCheck 
	 *	Added by : A-4809 on Jul 27, 2016
	 * 	Used for :
	 */
	public void setForKeyCheck(String forKeyCheck) {
		this.forKeyCheck = forKeyCheck;
	}
	

}
