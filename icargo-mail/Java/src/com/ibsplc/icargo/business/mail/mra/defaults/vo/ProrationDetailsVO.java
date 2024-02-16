/*
 * ProrationDetailsVO.java created on Mar 06, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2518
 * 
 */
public class ProrationDetailsVO extends AbstractVO {

	private String companyCode;

	private int serialNumber;

	private String billingBasisNumber;

	private String billingBasis;

	private String originExchangeOffice;

	private String destinationExchangeOffice;

	private String mailCategoryCode;

	private String mailSubclass;

	private int numberOfPieces;

	private double weight;

	private String consigneeDocumentNumber;
	
	private String consigneeSequenceNumber;

	private int flightCarrierIdentifier;

	private String flightNumber;

	private int flightSequenceNumber;

	private int segmentSequenceNumber;

	private String postalAuthorityCode;

	private String postalAuthorityName;

	private String sectorFrom;

	private String sectorTo;

	private String prorationType;

	private double prorationPercentage;

	private String payableFlag;

	private Money prorationAmtInUsd;

	private LocalDate flightDate;

	private Money prorationAmtInSdr;

	private Money prorationAmtInBaseCurr;

	private String carrierCode;
	
	private int carrierId;

	private String operationFlag;
	
	private String gpaarlBillingFlag;

	private double proratedValue;
	
	private double rate;
	
	private String bsaReference;
	
	private LocalDate recVDate;
	/*Added by indu for log his*/
	
	private String lastUpdateUser;
	private String appAud;
	private String billTo;
	private String blgSta;
	private String accSta;
	private String accTxnIdr;
	private double dueArl;
	private double duePoa;
	private String revFlg;
	private int segSerNum;
	private double wgtChg;
	private String rsn;
	private Money surProrationAmtInUsd;
	private Money surProrationAmtInSdr;
	private Money surProrationAmtInBaseCurr;
	private Money surProratedAmtInCtrCur;

	private String displayWeightUnit;
	
	private String codeShareIndicator;
	
	
	//Added by A-7794 as part of MRA revamp
	private long mailSequenceNumber;
	
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	private String dsn;
	
	
	

	/**
	 * Modifed by A-2518 for displaying prorated amount in contract currency
	 */
	private Money proratedAmtInCtrCur;
	
	// added by A-2554
	private int prorationFactor;
	private String sectorStatus;
	
	//added by A-3229
	
	private String totalWeight;
	
	private String ctrCurrencyCode;
	
	//added by A-3434 for bug 40868
	
	private String proratPercentage;
	/**
	 * @return Returns the accSta.
	 */
	public String getAccSta() {
		return accSta;
	}

	/**
	 * @param accSta The accSta to set.
	 */
	public void setAccSta(String accSta) {
		this.accSta = accSta;
	}

	/**
	 * @return Returns the accTxnIdr.
	 */
	public String getAccTxnIdr() {
		return accTxnIdr;
	}

	/**
	 * @param accTxnIdr The accTxnIdr to set.
	 */
	public void setAccTxnIdr(String accTxnIdr) {
		this.accTxnIdr = accTxnIdr;
	}

	/**
	 * @return Returns the appAud.
	 */
	public String getAppAud() {
		return appAud;
	}

	/**
	 * @param appAud The appAud to set.
	 */
	public void setAppAud(String appAud) {
		this.appAud = appAud;
	}

	/**
	 * @return Returns the billTo.
	 */
	public String getBillTo() {
		return billTo;
	}

	/**
	 * @param billTo The billTo to set.
	 */
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	/**
	 * @return Returns the blgSta.
	 */
	public String getBlgSta() {
		return blgSta;
	}

	/**
	 * @param blgSta The blgSta to set.
	 */
	public void setBlgSta(String blgSta) {
		this.blgSta = blgSta;
	}

	/**
	 * @return Returns the dueArl.
	 */
	public double getDueArl() {
		return dueArl;
	}

	/**
	 * @param dueArl The dueArl to set.
	 */
	public void setDueArl(double dueArl) {
		this.dueArl = dueArl;
	}

	/**
	 * @return Returns the duePoa.
	 */
	public double getDuePoa() {
		return duePoa;
	}

	/**
	 * @param duePoa The duePoa to set.
	 */
	public void setDuePoa(double duePoa) {
		this.duePoa = duePoa;
	}

	/**
	 * @return Returns the revFlg.
	 */
	public String getRevFlg() {
		return revFlg;
	}

	/**
	 * @param revFlg The revFlg to set.
	 */
	public void setRevFlg(String revFlg) {
		this.revFlg = revFlg;
	}

	/**
	 * @return Returns the segSerNum.
	 */
	public int getSegSerNum() {
		return segSerNum;
	}

	/**
	 * @param segSerNum The segSerNum to set.
	 */
	public void setSegSerNum(int segSerNum) {
		this.segSerNum = segSerNum;
	}

	/**
	 * @return Returns the consigneeSequenceNumber.
	 */
	public String getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}

	/**
	 * @param consigneeSequenceNumber The consigneeSequenceNumber to set.
	 */
	public void setConsigneeSequenceNumber(String consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}

	/**
	 * @return Returns the prorationFactor.
	 */
	public int getProrationFactor() {
		return prorationFactor;
	}

	/**
	 * @param prorationFactor The prorationFactor to set.
	 */
	public void setProrationFactor(int prorationFactor) {
		this.prorationFactor = prorationFactor;
	}

	/**
	 * @return Returns the sectorStatus.
	 */
	public String getSectorStatus() {
		return sectorStatus;
	}

	/**
	 * @param sectorStatus The sectorStatus to set.
	 */
	public void setSectorStatus(String sectorStatus) {
		this.sectorStatus = sectorStatus;
	}

	/**
	 * @return Returns the billingBasisNumber.
	 */
	public String getBillingBasisNumber() {
		return billingBasisNumber;
	}

	/**
	 * @param billingBasisNumber
	 *            The billingBasisNumber to set.
	 */
	public void setBillingBasisNumber(String billingBasisNumber) {
		this.billingBasisNumber = billingBasisNumber;
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
	 * @return Returns the destinationExchangeOffice.
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
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
	 * @return Returns the flightSequenceNumber.
	 */
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass
	 *            The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return Returns the numberOfPieces.
	 */
	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	/**
	 * @param numberOfPieces
	 *            The numberOfPieces to set.
	 */
	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the originExchangeOffice.
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice
	 *            The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return Returns the postalAuthorityCode.
	 */
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode
	 *            The postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	
	/**
	 * @return Returns the prorationType.
	 */
	public String getProrationType() {
		return prorationType;
	}

	/**
	 * @param prorationType
	 *            The prorationType to set.
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}

	/**
	 * @return Returns the prorationPercentage.
	 */
	public double getProrationPercentage() {
		return prorationPercentage;
	}

	/**
	 * @param prorationPercentage
	 *            The prorationPercentage to set.
	 */
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}

	/**
	 * @return Returns the sectorFrom.
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom
	 *            The sectorFrom to set.
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return Returns the sectorTo.
	 */
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo
	 *            The sectorTo to set.
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	/**
	 * @return Returns the segmentSequenceNumber.
	 */
	public int getSegmentSequenceNumber() {
		return segmentSequenceNumber;
	}

	/**
	 * @param segmentSequenceNumber
	 *            The segmentSequenceNumber to set.
	 */
	public void setSegmentSequenceNumber(int segmentSequenceNumber) {
		this.segmentSequenceNumber = segmentSequenceNumber;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the postalAuthorityName.
	 */
	public String getPostalAuthorityName() {
		return postalAuthorityName;
	}

	/**
	 * @param postalAuthorityName
	 *            The postalAuthorityName to set.
	 */
	public void setPostalAuthorityName(String postalAuthorityName) {
		this.postalAuthorityName = postalAuthorityName;
	}

	/**
	 * @return Returns the billingBasis.
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            The billingBasis to set.
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return Returns the payableFlag.
	 */
	public String getPayableFlag() {
		return payableFlag;
	}

	/**
	 * @param payableFlag
	 *            The payableFlag to set.
	 */
	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the proratedValue.
	 */
	public double getProratedValue() {
		return proratedValue;
	}

	/**
	 * @param proratedValue
	 *            The proratedValue to set.
	 */
	public void setProratedValue(double proratedValue) {
		this.proratedValue = proratedValue;
	}

	/**
	 * @return Returns the proratedAmtInCtrCur.
	 */
	public Money getProratedAmtInCtrCur() {
		return proratedAmtInCtrCur;
	}

	/**
	 * @param proratedAmtInCtrCur The proratedAmtInCtrCur to set.
	 */
	public void setProratedAmtInCtrCur(Money proratedAmtInCtrCur) {
		this.proratedAmtInCtrCur = proratedAmtInCtrCur;
	}

	/**
	 * @return Returns the prorationAmtInBaseCurr.
	 */
	public Money getProrationAmtInBaseCurr() {
		return prorationAmtInBaseCurr;
	}

	/**
	 * @param prorationAmtInBaseCurr The prorationAmtInBaseCurr to set.
	 */
	public void setProrationAmtInBaseCurr(Money prorationAmtInBaseCurr) {
		this.prorationAmtInBaseCurr = prorationAmtInBaseCurr;
	}

	/**
	 * @return Returns the prorationAmtInSdr.
	 */
	public Money getProrationAmtInSdr() {
		return prorationAmtInSdr;
	}

	/**
	 * @param prorationAmtInSdr The prorationAmtInSdr to set.
	 */
	public void setProrationAmtInSdr(Money prorationAmtInSdr) {
		this.prorationAmtInSdr = prorationAmtInSdr;
	}

	/**
	 * @return Returns the prorationAmtInUsd.
	 */
	public Money getProrationAmtInUsd() {
		return prorationAmtInUsd;
	}

	/**
	 * @param prorationAmtInUsd The prorationAmtInUsd to set.
	 */
	public void setProrationAmtInUsd(Money prorationAmtInUsd) {
		this.prorationAmtInUsd = prorationAmtInUsd;
	}
	/**
	 * @return Returns the totalWeight.
	 */
	public String getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight The totalWeight to set.
	 */
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}


	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return Returns the ctrCurrencyCode.
	 */

	public String getCtrCurrencyCode() {
		return ctrCurrencyCode;
	}

	/**
	 * @param ctrCurrencyCode The ctrCurrencyCode to set.
	 */
	public void setCtrCurrencyCode(String ctrCurrencyCode) {
		this.ctrCurrencyCode = ctrCurrencyCode;
	}

	public String getGpaarlBillingFlag() {
		return gpaarlBillingFlag;
	}

	public void setGpaarlBillingFlag(String gpaarlBillingFlag) {
		this.gpaarlBillingFlag = gpaarlBillingFlag;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the recVDate
	 */
	public LocalDate getRecVDate() {
		return recVDate;
	}

	/**
	 * @param recVDate the recVDate to set
	 */
	public void setRecVDate(LocalDate recVDate) {
		this.recVDate = recVDate;
	}

	/**
	 * @return the proratPercentage
	 */
	public String getProratPercentage() {
		return proratPercentage;
	}

	/**
	 * @param proratPercentage the proratPercentage to set
	 */
	public void setProratPercentage(String proratPercentage) {
		this.proratPercentage = proratPercentage;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the wgtChg.
	 */
	public double getWgtChg() {
		return wgtChg;
	}

	/**
	 * @param wgtChg The wgtChg to set.
	 */
	public void setWgtChg(double wgtChg) {
		this.wgtChg = wgtChg;
	}

	/**
	 * @return the surProrationAmtInUsd
	 */
	public Money getSurProrationAmtInUsd() {
		return surProrationAmtInUsd;
	}

	/**
	 * @param surProrationAmtInUsd the surProrationAmtInUsd to set
	 */
	public void setSurProrationAmtInUsd(Money surProrationAmtInUsd) {
		this.surProrationAmtInUsd = surProrationAmtInUsd;
	}

	/**
	 * @return the surProrationAmtInSdr
	 */
	public Money getSurProrationAmtInSdr() {
		return surProrationAmtInSdr;
	}

	/**
	 * @param surProrationAmtInSdr the surProrationAmtInSdr to set
	 */
	public void setSurProrationAmtInSdr(Money surProrationAmtInSdr) {
		this.surProrationAmtInSdr = surProrationAmtInSdr;
	}

	/**
	 * @return the surProrationAmtInBaseCurr
	 */
	public Money getSurProrationAmtInBaseCurr() {
		return surProrationAmtInBaseCurr;
	}

	/**
	 * @param surProrationAmtInBaseCurr the surProrationAmtInBaseCurr to set
	 */
	public void setSurProrationAmtInBaseCurr(Money surProrationAmtInBaseCurr) {
		this.surProrationAmtInBaseCurr = surProrationAmtInBaseCurr;
	}

	/**
	 * @return the surProratedAmtInCtrCur
	 */
	public Money getSurProratedAmtInCtrCur() {
		return surProratedAmtInCtrCur;
	}

	/**
	 * @param surProratedAmtInCtrCur the surProratedAmtInCtrCur to set
	 */
	public void setSurProratedAmtInCtrCur(Money surProratedAmtInCtrCur) {
		this.surProratedAmtInCtrCur = surProratedAmtInCtrCur;
	}
	/**
	 * 
	 * @return bsaReference
	 */
	public String getBsaReference() {
		return bsaReference;
	}
	/**
	 * 
	 * @param bsaReference
	 */
	public void setBsaReference(String bsaReference) {
		this.bsaReference = bsaReference;
	}

	/**
	 * @return the surProrationAmtInUsd
	 */
	

	/**
	 * Modifed by A-2518 for displaying prorated amount in contract currency
	 */
	/**
	 * 	Getter for displayWeightUnit 
	 *	Added by : A-8061 on 03-Dec-2019
	 * 	Used for :
	 */
	public String getDisplayWeightUnit() {
		return displayWeightUnit;
	}

	/**
	 *  @param displayWeightUnit the displayWeightUnit to set
	 * 	Setter for displayWeightUnit 
	 *	Added by : A-8061 on 03-Dec-2019
	 * 	Used for :
	 */
	public void setDisplayWeightUnit(String displayWeightUnit) {
		this.displayWeightUnit = displayWeightUnit;
	}

	/**
	 * 	Getter for codeShareIndicator 
	 *	Added by : A-8061 on 27-Sep-2020
	 * 	Used for :
	 */
	public String getCodeShareIndicator() {
		return codeShareIndicator;
	}

	/**
	 *  @param codeShareIndicator the codeShareIndicator to set
	 * 	Setter for codeShareIndicator 
	 *	Added by : A-8061 on 27-Sep-2020
	 * 	Used for :
	 */
	public void setCodeShareIndicator(String codeShareIndicator) {
		this.codeShareIndicator = codeShareIndicator;
	}

}
