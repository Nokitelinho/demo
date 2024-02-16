/*
 * BillingLineChargeVO.java Created on Jun 12, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
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

public class BillingLineChargeVO extends AbstractVO{
	
	private String companycode;
	private String billingMatrixID;
	private int billingLineSequenceNumber;
	private String chargeType;
	private double frmWgt;
	private double applicableRateCharge;
	private String rateType;
	private Money aplRatChg;
	//private double applicableCharge;
	private String operationalFlag="";
	private String forKeyCheck;
	

	//Added as part of ICRD-232319
	private double uspsRateOne;
	private double uspsRateTwo;
	private double uspsRateThr;
	private double uspsRateFour;
	private double uspsTot;
	private double conDiscount;
	
	/**
	 * Flat Rate
	 */
	public static final String FLATRATE="FR";
	/**
	 * Flat Charge
	 */
	public static final String FLATCHARGE="FC";
	/**
	 * Weight Break
	 */
	public static final String WEIGTHBREAK="WB";
	/**
	 * USPS
	 */
	public static final String USPS="US";
	/**
	 * Rate
	 */
	public static final String RATE="R";
	/**
	 * Charge
	 */
	public static final String CHARGE="C";
	/**
	 * @return the frmWgt
	 */
	public double getFrmWgt() {
		return frmWgt;
	}
	/**
	 * @param frmWgt the frmWgt to set
	 */
	public void setFrmWgt(double frmWgt) {
		this.frmWgt = frmWgt;
	}
	
	/**
	 * @return the operationalFlag
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag the operationalFlag to set
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
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
	 * @return the applicableRateCharge
	 */
	public double getApplicableRateCharge() {
		return applicableRateCharge;
	}
	/**
	 * @param applicableRateCharge the applicableRateCharge to set
	 */
	public void setApplicableRateCharge(double applicableRateCharge) {
		this.applicableRateCharge = applicableRateCharge;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	/**
	 * @return the aplRatChg
	 */
	public Money getAplRatChg() {
		return aplRatChg;
	}
	/**
	 * @param aplRatChg the aplRatChg to set
	 */
	public void setAplRatChg(Money aplRatChg) {
		this.aplRatChg = aplRatChg;
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
	public double getUspsRateOne() {
		return uspsRateOne;
	}
	public void setUspsRateOne(double uspsRateOne) {
		this.uspsRateOne = uspsRateOne;
	}
	public double getUspsRateTwo() {
		return uspsRateTwo;
	}
	public void setUspsRateTwo(double uspsRateTwo) {
		this.uspsRateTwo = uspsRateTwo;
	}
	public double getUspsRateThr() {
		return uspsRateThr;
	}
	public void setUspsRateThr(double uspsRateThr) {
		this.uspsRateThr = uspsRateThr;
	}
	public double getUspsRateFour() {
		return uspsRateFour;
	}
	public void setUspsRateFour(double uspsRateFour) {
		this.uspsRateFour = uspsRateFour;
	}
	public double getUspsTot() {
		return uspsTot;
	}
	public void setUspsTot(double uspsTot) {
		this.uspsTot = uspsTot;
	}
	public double getConDiscount() {
		return conDiscount;
	}
	public void setConDiscount(double conDiscount) {
		this.conDiscount = conDiscount;
	}

	
}
