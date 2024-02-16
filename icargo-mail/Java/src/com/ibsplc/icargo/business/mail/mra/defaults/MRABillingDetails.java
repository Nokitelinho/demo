/*
 * MRABillingDetails.java Created on July 6, 2008
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Calendar;
import java.util.Collection;

/**
 * @author A-2554
 * 
 */
@Entity
@Table(name = "MALMRABLGDTL")
@Staleable
public class MRABillingDetails {
	
	
	
	private static final String CLASS_NAME = "MRABillingDetails";
	private static final String MODULE_NAME = "mail.mra.defaults";
	
	private MRABillingDetailsPK mraBillingDetailsPk;
	
	private String segFrom;
	private String segTo;
	private double aplRate;
	private Calendar flightDate;
	private int flightCarrierID;
	private String flightNumber;
	private int flightSeqNumber;
	private String flightCarrierCode;
	private int segSerialNumber;
	private double wgtChargeUsd;
	private double wgtChargeBas;
	private double dueAirline;
	private String prorationType;
	
	private String paymentFlag;
	private String blgStatus;
	private String contractCurrCode;
	
	
	private String acctxnIdr; 
	private double prorationPercentage;
	private double proratedValue;
	private String revFlag;
	private String remarks;
	private String sectStatus;
	//added for cr ICRD-7370
	//Added as part of CRQ 12578
	private String unitCode;
	private String isTaxIncludedInRateFlag;
	private double valueInBlgCurrency;
	
	//Added by A-7794 as part of MRA Revamp
	
	private double grossWeight;
	private double updatedGrossWeight;
	private double weightChargeXDR;
	private double weightChargeCTR;
	private String joinFlag;
	private int billTooArlnId;
	//private String billTooArlnCode;
	private String rateLineId;
	private String billingMatrix;
	private String rateType;
	private double otherChgCTR;
	private double otherChgBAS;
	private double otherChgUSD;
	private double otherChgSDR;
	private String triggetPnt;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	//Modified by A-7794 as part of ICRD-285773
	private Double netAmount;
	private String billTooPartyCode;
	private String billTooPartyType;
	private double updatedAppliedRate;
	private double serviceTax;
	
	
	
	/**
	 * @return the serviceTax
	 */
	@Column(name="SRVTAX")
	public double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the updatedAppliedRate
	 */
	@Column(name="UPDAPLRAT")
	public double getUpdatedAppliedRate() {
		return updatedAppliedRate;
	}
	/**
	 * @param updatedAppliedRate the updatedAppliedRate to set
	 */
	public void setUpdatedAppliedRate(double updatedAppliedRate) {
		this.updatedAppliedRate = updatedAppliedRate;
	}
	/**
	 * @return the billTooPartyType
	 */
	@Column(name="BILTOOPTYTYP")
	public String getBillTooPartyType() {
		return billTooPartyType;
	}
	/**
	 * @param billTooPartyType the billTooPartyType to set
	 */
	public void setBillTooPartyType(String billTooPartyType) {
		this.billTooPartyType = billTooPartyType;
	}
	/**
	 * @return the billTooPartyCode
	 */
	@Column(name="BILTOOPTYCOD")
	public String getBillTooPartyCode() {
		return billTooPartyCode;
	}
	/**
	 * @param billTooPartyCode the billTooPartyCode to set
	 */
	public void setBillTooPartyCode(String billTooPartyCode) {
		this.billTooPartyCode = billTooPartyCode;
	}
	
	/**
	 * @return the netAmount
	 */
	@Column(name="NETAMT")
	public Double getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @return the grossWeight
	 */
	@Column(name="GRSWGT")
	public double getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight the grossWeight to set
	 */
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * @return the updatedGrossWeight
	 */
	@Column(name="UPDGRSWGT")
	public double getUpdatedGrossWeight() {
		return updatedGrossWeight;
	}
	/**
	 * @param updatedGrossWeight the updatedGrossWeight to set
	 */
	public void setUpdatedGrossWeight(double updatedGrossWeight) {
		this.updatedGrossWeight = updatedGrossWeight;
	}
	/**
	 * @return the weightChargeXDR
	 */
	@Column(name="WGTCHGXDR")
	public double getWeightChargeXDR() {
		return weightChargeXDR;
	}
	/**
	 * @param weightChargeXDR the weightChargeXDR to set
	 */
	public void setWeightChargeXDR(double weightChargeXDR) {
		this.weightChargeXDR = weightChargeXDR;
	}
	/**
	 * @return the weightChargeCTR
	 */
	@Column(name="WGTCHGCTR")
	public double getWeightChargeCTR() {
		return weightChargeCTR;
	}
	/**
	 * @param weightChargeCTR the weightChargeCTR to set
	 */
	public void setWeightChargeCTR(double weightChargeCTR) {
		this.weightChargeCTR = weightChargeCTR;
	}
	/**
	 * @return the joinFlag
	 */
	@Column(name="JONFLG")
	public String getJoinFlag() {
		return joinFlag;
	}
	/**
	 * @param joinFlag the joinFlag to set
	 */
	public void setJoinFlag(String joinFlag) {
		this.joinFlag = joinFlag;
	}
	/**
	 * @return the billTooArlnId
	 */
	@Column(name="BILTOOARLIDR")
	public int getBillTooArlnId() {
		return billTooArlnId;
	}
	/**
	 * @param billTooArlnId the billTooArlnId to set
	 */
	public void setBillTooArlnId(int billTooArlnId) {
		this.billTooArlnId = billTooArlnId;
	}
	
	/**
	 * @return the rateLineId
	 */
	@Column(name="RATLINIDR")
	public String getRateLineId() {
		return rateLineId;
	}
	/**
	 * @param rateLineId the rateLineId to set
	 */
	public void setRateLineId(String rateLineId) {
		this.rateLineId = rateLineId;
	}
	/**
	 * @return the billingMatrix
	 */
	@Column(name="RATCOD")
	public String getBillingMatrix() {
		return billingMatrix;
	}
	/**
	 * @param billingMatrix the billingMatrix to set
	 */
	public void setBillingMatrix(String billingMatrix) {
		this.billingMatrix = billingMatrix;
	}
	/**
	 * @return the rateType
	 */
	@Column(name="RATTYP")
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
	 * @return the otherChgCTR
	 */
	@Column(name="OTHCHGCTR")
	public double getOtherChgCTR() {
		return otherChgCTR;
	}
	/**
	 * @param otherChgCTR the otherChgCTR to set
	 */
	public void setOtherChgCTR(double otherChgCTR) {
		this.otherChgCTR = otherChgCTR;
	}
	/**
	 * @return the otherChgBAS
	 */
	@Column(name="OTHCHGBAS")
	public double getOtherChgBAS() {
		return otherChgBAS;
	}
	/**
	 * @param otherChgBAS the otherChgBAS to set
	 */
	public void setOtherChgBAS(double otherChgBAS) {
		this.otherChgBAS = otherChgBAS;
	}
	/**
	 * @return the otherChgUSD
	 */
	@Column(name="OTHCHGUSD")
	public double getOtherChgUSD() {
		return otherChgUSD;
	}
	/**
	 * @param otherChgUSD the otherChgUSD to set
	 */
	public void setOtherChgUSD(double otherChgUSD) {
		this.otherChgUSD = otherChgUSD;
	}
	/**
	 * @return the otherChgSDR
	 */
	@Column(name="OTHCHGXDR")
	public double getOtherChgSDR() {
		return otherChgSDR;
	}
	/**
	 * @param otherChgSDR the otherChgSDR to set
	 */
	public void setOtherChgSDR(double otherChgSDR) {
		this.otherChgSDR = otherChgSDR;
	}
	/**
	 * @return the triggetPnt
	 */
	@Column(name="TRGPNT")
	public String getTriggetPnt() {
		return triggetPnt;
	}
	/**
	 * @param triggetPnt the triggetPnt to set
	 */
	public void setTriggetPnt(String triggetPnt) {
		this.triggetPnt = triggetPnt;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name="LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	/**
	 * 	Getter for valueInBlgCurrency 
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	@Column(name="VALCHGCTR")
	public double getValueInBlgCurrency() {
		return valueInBlgCurrency;
	}
	/**
	 *  @param valueInBlgCurrency the valueInBlgCurrency to set
	 * 	Setter for valueInBlgCurrency 
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	public void setValueInBlgCurrency(double valueInBlgCurrency) {
		this.valueInBlgCurrency = valueInBlgCurrency;
	}
	
	/**
	 * @return the unitCode
	 */
	@Column(name = "UNTCOD")
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the isTaxIncludedInRateFlag
	 */
	@Column(name = "RATINCSRVTAXFLG")
	public String getIsTaxIncludedInRateFlag() {
		return isTaxIncludedInRateFlag;
	}

	/**
	 * @param isTaxIncludedInRateFlag the isTaxIncludedInRateFlag to set
	 */
	public void setIsTaxIncludedInRateFlag(String isTaxIncludedInRateFlag) {
		this.isTaxIncludedInRateFlag = isTaxIncludedInRateFlag;
	}

	
	
	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * @return Returns the revFlag.
	 */
	@Column(name = "REVFLG")
	public String getRevFlag() {
		return revFlag;
	}
	
	/**
	 * 
	 * @log
	 */
	
	public static Log returnLogger() {
		return LogFactory.getLogger("MRA MAILSLAMASTER");
	}
	/**
	 * @return Returns the aplRate.
	 */
	@Column(name = "APLRAT")
	public double getAplRate() {
		return aplRate;
	}
	
	
	/**
	 * @return Returns the blgStatus.
	 */
	@Column(name = "BLGSTA")
	public String getBlgStatus() {
		return blgStatus;
	}
	
	/**
	 * @return Returns the contractCurrCode.
	 */
	@Column(name = "CTRCURCOD")
	public String getContractCurrCode() {
		return contractCurrCode;
	}
	
	/**
	 * @return Returns the flightCarrierID.
	 */
	@Column(name = "FLTCARIDR")
	public int getFlightCarrierID() {
		return flightCarrierID;
	}
	/**
	 * @return Returns the flightDate.
	 */	
	@Column(name = "FLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightDate() {
		return flightDate;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @return Returns the flightSeqNumber.
	 */
	@Column(name = "FLTSEQNUM")
	public int getFlightSeqNumber() {
		return flightSeqNumber;
	}
	/**
	 * @return Returns the flightCarrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	
	/**
	 * @return Returns the paymentFlag.
	 */
	@Column(name = "PAYFLG")
	public String getPaymentFlag() {
		return paymentFlag;
	}
	
	/**
	 * @return Returns the proratedValue.
	 */
	@Column(name = "PROVAL")
	public double getProratedValue() {
		return proratedValue;
	}
	/**
	 * @return Returns the prorationPercentage.
	 */
	@Column(name = "PROPRC")
	public double getProrationPercentage() {
		return prorationPercentage;
	}
	/**
	 * @return Returns the prorationType.
	 */
	@Column(name = "PROTYP")
	public String getProrationType() {
		return prorationType;
	}
	
	
	/**
	 * @return Returns the segFrom.
	 */
	@Column(name = "SECFRM")
	public String getSegFrom() {
		return segFrom;
	}
	/**
	 * @return Returns the segSerialNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegSerialNumber() {
		return segSerialNumber;
	}
	/**
	 * @return Returns the segTo.
	 */
	@Column(name = "SECTOO")
	public String getSegTo() {
		return segTo;
	}
	
	
	/**
	 * @return Returns the wgtChargeUsd.
	 */
	@Column(name = "WGTCHGUSD")
	public double getWgtChargeUsd() {
		return wgtChargeUsd;
	}
	
	/**
	 * @return Returns the wgtChargeBas.
	 */
	@Column(name = "WGTCHGBAS")
	public double getWgtChargeBas() {
		return wgtChargeBas;
	}
	/**
	 * @return Returns the dueAirline.
	 */
	@Column(name = "DUEARL")
	public double getDueAirline() {
		return dueAirline;
	}
	
	
	/**
	 * @param wgtChargeUsd
	 *            The wgtChargeUsd to set.
	 */
	public void setWgtChargeUsd(double wgtChargeUsd) {
		this.wgtChargeUsd = wgtChargeUsd;
	}
	
	/**
	 * @param wgtChargeBas
	 *            The wgtChargeBas to set.
	 */
	public void setWgtChargeBas(double wgtChargeBas) {
		this.wgtChargeBas = wgtChargeBas;
	}
	/**
	 * @param dueAirline
	 *            The dueAirline to set.
	 */
	public void setDueAirline(double dueAirline) {
		this.dueAirline = dueAirline;
	}
	/**
	 * @param aplRate
	 *            The aplRate to set.
	 */
	public void setAplRate(double aplRate) {
		this.aplRate = aplRate;
	}
	
	/**
	 * @param blgStatus
	 *            The blgStatus to set.
	 */
	public void setBlgStatus(String blgStatus) {
		this.blgStatus = blgStatus;
	}
	
	/**
	 * @param contractCurrCode
	 *            The contractCurrCode to set.
	 */
	public void setContractCurrCode(String contractCurrCode) {
		this.contractCurrCode = contractCurrCode;
	}
	
	/**
	 * @param flightCarrierID
	 *            The flightCarrierID to set.
	 */
	public void setFlightCarrierID(int flightCarrierID) {
		this.flightCarrierID = flightCarrierID;
	}
	
	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @param flightSeqNumber
	 *            The flightSeqNumber to set.
	 */
	public void setFlightSeqNumber(int flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	
	/**
	 * @param paymentFlag
	 *            The paymentFlag to set.
	 */
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	
	/**
	 * @param proratedValue
	 *            The proratedValue to set.
	 */
	public void setProratedValue(double proratedValue) {
		this.proratedValue = proratedValue;
	}
	/**
	 * @param prorationPercentage
	 *            The prorationPercentage to set.
	 */
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}
	/**
	 * @param prorationType
	 *            The prorationType to set.
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}
	
	
	/**
	 * @param segFrom
	 *            The segFrom to set.
	 */
	public void setSegFrom(String segFrom) {
		this.segFrom = segFrom;
	}
	/**
	 * @param segSerialNumber
	 *            The segSerialNumber to set.
	 */
	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
	}
	/**
	 * @param segTo
	 *            The segTo to set.
	 */
	public void setSegTo(String segTo) {
		this.segTo = segTo;
	}
	
	
	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param revFlag
	 *            The revFlag to set.
	 */
	public void setRevFlag(String revFlag) {
		this.revFlag = revFlag;
	}
	/**
	 * @return Returns the mraBillingDetailsPk.
	 */
	public MRABillingDetails() {

	  }
	
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
		@AttributeOverride(name = "mailSeqNum", column = @Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")) })
		public MRABillingDetailsPK getMraBillingDetailsPk() {
		return mraBillingDetailsPk;
	}
	/**
	 * @param mraBillingDetailsPk
	 *            The mraBillingDetailsPk to set.
	 */
	public void setMraBillingDetailsPk(MRABillingDetailsPK mraBillingDetailsPk) {
		this.mraBillingDetailsPk = mraBillingDetailsPk;
	}
	
	
	/**
	 * 
	 * @param rateAuditDetailsVO
	 * @throws SystemException
	 */
	public MRABillingDetails(RateAuditDetailsVO rateAuditDetailsVO )throws SystemException{
		returnLogger().entering(CLASS_NAME,"MRABillingDetailsTemp-Constructor");
		populatePKFromRateAudit(rateAuditDetailsVO);
		populateAttributesFromRateAudit(rateAuditDetailsVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		
		returnLogger().exiting(CLASS_NAME,"MRABillingDetailsTemp-Constructor");
	}
	
	/**
	 * @param rateAuditDetailsVO
	 */
	private void populatePKFromRateAudit(RateAuditDetailsVO rateAuditDetailsVO) {
		//Modified by A-7794 as part of MRA revamp
		returnLogger().entering(CLASS_NAME,"populatePK");
		MRABillingDetailsPK mRABillingDetailPK=new MRABillingDetailsPK(rateAuditDetailsVO.getMailSequenceNumber(),
				rateAuditDetailsVO.getSerNum(),
				rateAuditDetailsVO.getCompanyCode()
		);
		
		this.setMraBillingDetailsPk(mRABillingDetailPK);
		
		returnLogger().exiting(CLASS_NAME,"populatePK");
	}
	
	
	/**
	 * @param rateAuditDetailsVO
	 */
	private void populateAttributesFromRateAudit(RateAuditDetailsVO rateAuditDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes");	
		
		this.setAplRate(rateAuditDetailsVO.getRate());		
		this.setFlightCarrierCode(rateAuditDetailsVO.getCarrierCode());								
		this.setFlightNumber(rateAuditDetailsVO.getFlightno());
		this.setPaymentFlag(rateAuditDetailsVO.getPayFlag());
		this.setSegFrom(rateAuditDetailsVO.getSecFrom());
		this.setSegTo(rateAuditDetailsVO.getSecTo());
		this.setBlgStatus(rateAuditDetailsVO.getBillingStatus());
		this.setFlightCarrierID(rateAuditDetailsVO.getCarrierid());		
		this.setWgtChargeUsd(rateAuditDetailsVO.getWgtChargeUSD());
		this.setWgtChargeBas(rateAuditDetailsVO.getWgtChargeBAS());
		//this.setUpdBillToIdr(rateAuditDetailsVO.getUpdBillToIdr());	
		this.setContractCurrCode(rateAuditDetailsVO.getContCurCode());
		this.setFlightDate(rateAuditDetailsVO.getFlightDate());
		this.setFlightSeqNumber(rateAuditDetailsVO.getFlightseqno());
		this.setProrationType(rateAuditDetailsVO.getProrationType());
		//this.setAccsta(rateAuditDetailsVO.getAccsta());
		this.setAcctxnIdr(rateAuditDetailsVO.getAcctxnIdr());
		this.setDueAirline(rateAuditDetailsVO.getDueAirline());
		//this.setDuepoa(rateAuditDetailsVO.getDuepoa());
		this.setProrationPercentage(rateAuditDetailsVO.getProPercent());
		this.setProratedValue(rateAuditDetailsVO.getProValue());
		this.setRevFlag(rateAuditDetailsVO.getRevFlg());
		this.setRemarks(rateAuditDetailsVO.getRemark());
		this.setSegSerialNumber(rateAuditDetailsVO.getSegSerNo());
		this.setSectStatus(rateAuditDetailsVO.getSectStatus());
		returnLogger().exiting(CLASS_NAME,"populateAttributes");
		
	}
	
	
	/**
	 * 
	 * @param mraBillingDetailsVO
	 * @throws SystemException
	 */
	public MRABillingDetails(MRABillingDetailsVO mraBillingDetailsVO)throws SystemException{
		returnLogger().entering(CLASS_NAME,"MRABillingDetails-Constructor");
		populatePK(mraBillingDetailsVO);
		populateAttributes(mraBillingDetailsVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		
		returnLogger().exiting(CLASS_NAME,"MRABillingDetails-Constructor");
	}
	
	
	 /**
		 * This method constructs
		 * 
		 * @throws PersistenceException
		 * @throws SystemException
		 */
	private static MRADefaultsDAO constructDAO()
	throws PersistenceException, SystemException{
		Log log = LogFactory.getLogger("MRA BILLING");
		log.entering("MRABillingMaster","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRADefaultsDAO.class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}
	/**
	 * @param mraBillingDetailsVO
	 */
	private void populatePK(MRABillingDetailsVO mraBillingDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populatePK");
		
		MRABillingDetailsPK mraBillingDetailsPK=new MRABillingDetailsPK(
				mraBillingDetailsVO.getSequenceNumber(),
				mraBillingDetailsVO.getCompanyCode()
		);
		
		this.setMraBillingDetailsPk(mraBillingDetailsPK);
		
		returnLogger().exiting(CLASS_NAME,"populatePK");
	}
	
	
	/**
	 * @param mraBillingDetailsVO
	 */
	private void populateAttributes(MRABillingDetailsVO mraBillingDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes");
		
		this.setSegFrom(mraBillingDetailsVO.getSegFrom());
		this.setSegTo(mraBillingDetailsVO.getSegTo());
		this.setAplRate(mraBillingDetailsVO.getAplRate());
		this.setFlightDate(mraBillingDetailsVO.getFlightDate());
		this.setFlightCarrierID(mraBillingDetailsVO.getFlightCarrierID());
		this.setFlightNumber(mraBillingDetailsVO.getFlightNumber());
		this.setFlightSeqNumber(mraBillingDetailsVO.getFlightSeqNumber());
		this.setSegSerialNumber(mraBillingDetailsVO.getSegSerialNumber());
		this.setProrationType(mraBillingDetailsVO.getProrationType());
		this.setProrationPercentage(mraBillingDetailsVO.getProrationPercentage());
		this.setProratedValue(mraBillingDetailsVO.getProratedValue());
		this.setPaymentFlag(mraBillingDetailsVO.getPaymentFlag());
		this.setBlgStatus(mraBillingDetailsVO.getBlgStatus());
		this.setContractCurrCode(mraBillingDetailsVO.getContractCurrCode());
		this.setRevFlag(mraBillingDetailsVO.getRevFlag());
		this.setFlightCarrierCode(mraBillingDetailsVO.getFlightCarrierCode());
		this.setWgtChargeBas(mraBillingDetailsVO.getWgtChargeBas());
		this.setWgtChargeUsd(mraBillingDetailsVO.getWgtChargeUsd());
		this.setDueAirline(mraBillingDetailsVO.getDueAirline());
		returnLogger().exiting(CLASS_NAME,"populateAttributes");
		
	}
	
	
	/**
	 * @param seqNumber
	 * @param csgSeqNumber
	 * @param csgDocumentNumber
	 * @param billingBasis
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	//Modified by A-7794 as part of MRA revamp
	public static MRABillingDetails find(String companyCode,long mailSeqNumber, int serialNumber)throws SystemException,FinderException{
		returnLogger().entering(CLASS_NAME,"find");
		MRABillingDetailsPK mraBillingDetailsPK=new MRABillingDetailsPK( mailSeqNumber,serialNumber,companyCode);
		MRABillingDetails mraBillingDetails=null;
		try {
			mraBillingDetails=PersistenceController.getEntityManager().find(
					MRABillingDetails.class,mraBillingDetailsPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"find");
		return mraBillingDetails;
		
	}
	
	
	/**
	 * 
	 * @param mraBillingDetailsVO
	 * @throws SystemException
	 */
	public void update(MRABillingDetailsVO mraBillingDetailsVO)
	throws SystemException{
		returnLogger().entering(CLASS_NAME,"update");
		populateAttributes(mraBillingDetailsVO);		
		returnLogger().exiting(CLASS_NAME,"update");		
	}
	
	/**
	 * 
	 * @param rateAuditDetailsVO
	 * @throws SystemException
	 */
	public void updateFromRateAudit(RateAuditDetailsVO rateAuditDetailsVO )
	throws SystemException{
		returnLogger().entering(CLASS_NAME,"update");
										
		this.setAplRate(rateAuditDetailsVO.getRate());
		// this.setDiscrepancy(rateAuditDetailsVO.getDiscrepancy());
		this.setFlightCarrierCode(rateAuditDetailsVO.getCarrierCode());								
		this.setFlightNumber(rateAuditDetailsVO.getFlightno());
		this.setPaymentFlag(rateAuditDetailsVO.getPayFlag());
		this.setSegFrom(rateAuditDetailsVO.getSecFrom());
		this.setSegTo(rateAuditDetailsVO.getSecTo());
		this.setBlgStatus(rateAuditDetailsVO.getBillingStatus());
		this.setFlightCarrierID(rateAuditDetailsVO.getCarrierid());
		this.setDueAirline(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());
		this.setWgtChargeUsd(rateAuditDetailsVO.getWgtChargeUSD());
		this.setWgtChargeBas(rateAuditDetailsVO.getWgtChargeBAS());
		//this.setUpdBillToIdr(rateAuditDetailsVO.getUpdBillToIdr());
		returnLogger().exiting(CLASS_NAME,"update");		
	}
	
	
	
	
	
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering(CLASS_NAME,"remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"remove");
	}
	
	
	 /**
		 * @author a-3434
		 * @param airlineBillingFilterVO
		 * @return DocumentBillingDetailsVO
		 * @throws SystemException
		 */
	public static Page<DocumentBillingDetailsVO> findInterlineBillingEntries(AirlineBillingFilterVO airlineBillingFilterVO) 
	throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("MRABillingDetails","entity");
			return constructDAO().findInterlineBillingEntries(airlineBillingFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * @author a-3429
	 * @param flightSectorRevenueFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException
	 */
	public static Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) 
	throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("findFlightRevenueForSector","entity");
			return constructDAO().findFlightRevenueForSector(flightSectorRevenueFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * Finds tand returns the GPA Billing entries available This includes
	 * billed, billable and on hold despatches
	 * 
	 * @param gpaBillingEntriesFilterVO
	 * @return Collection<GPABillingDetailsVO>
	 * @throws SystemException
	 */

    public static Page<DocumentBillingDetailsVO> findGPABillingEntries(
            GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
    throws SystemException{
    	Page<DocumentBillingDetailsVO> documentBillingDetailsVOs=null;
    	try{
    		documentBillingDetailsVOs=constructDAO().findGPABillingEntries(gpaBillingEntriesFilterVO);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }

        return documentBillingDetailsVOs;
    }
    /**
	 * @author a-3434
	 * finds the GpaBillingDetails
	 * @param cN66DetailsVO
	 * @return MRABillingDetailsVO
	 * @throws SystemException
	 */
    public static MRABillingDetailsVO findGpaBillingDetails(
    		CN66DetailsVO cN66DetailsVO)
    throws SystemException{
    	MRABillingDetailsVO mraBillingDetailsVO=null;
    	try{
    		mraBillingDetailsVO=constructDAO().findGpaBillingDetails(cN66DetailsVO);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }

        return mraBillingDetailsVO;
    }
	
	/**
	 * @return the acctxnIdr
	 */
	@Column(name = "ACCTXNIDR")
	public String getAcctxnIdr() {
		return acctxnIdr;
	}

	/**
	 * @param acctxnIdr
	 *            the acctxnIdr to set
	 */
	public void setAcctxnIdr(String acctxnIdr) {
		this.acctxnIdr = acctxnIdr;
	}

	
	/**
	 * @return the sectStatus
	 */
	@Column(name = "SECSTA")
	public String getSectStatus() {
		return sectStatus;
	}

	/**
	 * @param sectStatus the sectStatus to set
	 */
	public void setSectStatus(String sectStatus) {
		this.sectStatus = sectStatus;
	}
	
	/**
	 * @author a-8061
	 * @param mRABillingDetailsVO
	 * @return
	 */
	public static Collection<MRABillingDetailsVO> findMRABillingDetails(MRABillingDetailsVO mRABillingDetailsVO)throws SystemException {
		Collection<MRABillingDetailsVO> mRABillingDetailsVOs=null;
    	try{
    		mRABillingDetailsVOs=constructDAO().findMRABillingDetails(mRABillingDetailsVO);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }

        return mRABillingDetailsVOs;
	}
	/**
	 * 	Method		:	MRABillingDetails.listGPABillingEntries
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Page<DocumentBillingDetailsVO>
	 */
    public static Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)throws SystemException{
    	Page<DocumentBillingDetailsVO> documentBillingDetailsVOs=null;
    	try{
    		documentBillingDetailsVOs=constructDAO().listGPABillingEntries(gpaBillingEntriesFilterVO);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }
        return documentBillingDetailsVOs;
    }
/**
 * 	Method		:	MRABillingDetails.listConsignmentDetails
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Page<ConsignmentDocumentVO>
 */
    public static Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO,Collection<CRAParameterVO> craParameterVOs)throws SystemException{
    	Page<ConsignmentDocumentVO> consignmentDocumentVOs=null;
    	try{
    		consignmentDocumentVOs=constructDAO().listConsignmentDetails(gpaBillingEntriesFilterVO,craParameterVOs);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }
        return consignmentDocumentVOs;
    }	
}
