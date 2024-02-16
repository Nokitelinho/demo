/*
 * MRABillingDetailsHistory.java Created on Nov 7, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2554
 *
 */
@Entity
@Table(name = "MALMRABLGDTLHIS")
@Staleable
public class MRABillingDetailsHistory{	
	
	
	private static final String CLASS_NAME = "MRABillingDetailsHistory";
	private static final String MODULE_NAME = "mail.mra.defaults";
	private static final String FLAG_YES = "Y";
	private static final String FLAG_NO = "N";
	private static final String FLAG_FAIL = "F";
	
	private MRABillingDetailsHistoryPK mRABillingDetailsHistoryPK;
	
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
	//private String applyAudit;
	private String prorationType;
	
	
	private String paymentFlag;
	private String blgStatus;
	private String contractCurrCode;
	
	

//	private int updBillToIdr;
//	private double initialRate;
	
	private String acctxnIdr; 
	private double prorationPercentage;
	private double proratedValue;
	private String revFlag;
	private String remarks;
	private Calendar lastUpdateTime;
	 private String lastUpdateUser;
	
	//Added by A-7794 as part of MRA Revamp
	
	 private double grossWeight;
	 private double updatedGrossWt;
	 private double weightChgXDR;
	 private double weightChgCTR;
	 private String joinFlag;
	 private int billTooAirlineId;
	 private String billTooAirlineCode;
	 private String sectorStatus;
	 private String rateIncluServiceTax;
	 private String unitCode;
	 private double valuationChargeCTR;
	 private String rateLIneId;
	 private String billingMatrix;
	 private String rateType;
	 private double otherChgCTR;
	 private double otherChgBAS;
	 private double otherChgUSD;
	 private double otherChgSDR;
	 private String triggerPoint;
	 
	 private String interfaceFlag;
	 private Calendar interfaceTime;
	 
	 @Column(name = "INTFCDFLG")
	 public String getInterfaceFlag() {
		return interfaceFlag;
	}

	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}
	@Column(name = "INTFCDTIM")
	public Calendar getInterfaceTime() {
		return interfaceTime;
	}

	public void setInterfaceTime(Calendar interfaceTime) {
		this.interfaceTime = interfaceTime;
	}


	 
	
//	/**
//	 * @return the initialRate
//	 */
//	@Column(name = "BLGRAT")
//	public double getInitialRate() {
//		return initialRate;
//	}
//
//	/**
//	 * @param initialRate the initialRate to set
//	 */
//	public void setInitialRate(double initialRate) {
//		this.initialRate = initialRate;
//	}

	
	/**
	 * @return the grossWeight
	 */
	 @Column(name = "GRSWGT")
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
	 * @return the updatedGrossWt
	 */
	 @Column(name = "UPDGRSWGT")
	public double getUpdatedGrossWt() {
		return updatedGrossWt;
	}

	/**
	 * @param updatedGrossWt the updatedGrossWt to set
	 */
	public void setUpdatedGrossWt(double updatedGrossWt) {
		this.updatedGrossWt = updatedGrossWt;
	}

	/**
	 * @return the weightChgXDR
	 */
	@Column(name = "WGTCHGXDR")
	public double getWeightChgXDR() {
		return weightChgXDR;
	}

	/**
	 * @param weightChgXDR the weightChgXDR to set
	 */
	public void setWeightChgXDR(double weightChgXDR) {
		this.weightChgXDR = weightChgXDR;
	}

	/**
	 * @return the weightChgCTR
	 */
	@Column(name = "WGTCHGCTR")
	public double getWeightChgCTR() {
		return weightChgCTR;
	}

	/**
	 * @param weightChgCTR the weightChgCTR to set
	 */
	public void setWeightChgCTR(double weightChgCTR) {
		this.weightChgCTR = weightChgCTR;
	}

	/**
	 * @return the joinFlag
	 */
	@Column(name = "JONFLG")
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
	 * @return the billTooAirlineId
	 */
	@Column(name = "BILTOOARLIDR")
	public int getBillTooAirlineId() {
		return billTooAirlineId;
	}

	/**
	 * @param billTooAirlineId the billTooAirlineId to set
	 */
	public void setBillTooAirlineId(int billTooAirlineId) {
		this.billTooAirlineId = billTooAirlineId;
	}

	/**
	 * @return the billTooAirlineCode
	 */
	@Column(name = "BILTOOPTYCOD")
	public String getBillTooAirlineCode() {
		return billTooAirlineCode;
	}

	/**
	 * @param billTooAirlineCode the billTooAirlineCode to set
	 */
	public void setBillTooAirlineCode(String billTooAirlineCode) {
		this.billTooAirlineCode = billTooAirlineCode;
	}

	/**
	 * @return the sectorStatus
	 */
	@Column(name = "SECSTA")
	public String getSectorStatus() {
		return sectorStatus;
	}

	/**
	 * @param sectorStatus the sectorStatus to set
	 */
	public void setSectorStatus(String sectorStatus) {
		this.sectorStatus = sectorStatus;
	}

	/**
	 * @return the rateIncluServiceTax
	 */
	@Column(name = "RATINCSRVTAXFLG")
	public String getRateIncluServiceTax() {
		return rateIncluServiceTax;
	}

	/**
	 * @param rateIncluServiceTax the rateIncluServiceTax to set
	 */
	public void setRateIncluServiceTax(String rateIncluServiceTax) {
		this.rateIncluServiceTax = rateIncluServiceTax;
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
	 * @return the valuationChargeCTR
	 */
	@Column(name = "VALCHGCTR")
	public double getValuationChargeCTR() {
		return valuationChargeCTR;
	}

	/**
	 * @param valuationChargeCTR the valuationChargeCTR to set
	 */
	public void setValuationChargeCTR(double valuationChargeCTR) {
		this.valuationChargeCTR = valuationChargeCTR;
	}

	/**
	 * @return the rateLIneId
	 */
	@Column(name = "RATLINIDR")
	public String getRateLIneId() {
		return rateLIneId;
	}

	/**
	 * @param rateLIneId the rateLIneId to set
	 */
	public void setRateLIneId(String rateLIneId) {
		this.rateLIneId = rateLIneId;
	}

	/**
	 * @return the billingMatrix
	 */
	@Column(name = "RATCOD")
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
	@Column(name = "RATTYP")
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
	@Column(name = "OTHCHGCTR")
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
	@Column(name = "OTHCHGBAS")
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
	@Column(name = "OTHCHGUSD")
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
	@Column(name = "OTHCHGXDR")
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
	 * @return the triggerPoint
	 */
	@Column(name = "TRGPNT")
	public String getTriggerPoint() {
		return triggerPoint;
	}

	/**
	 * @param triggerPoint the triggerPoint to set
	 */
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
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
	 * @return Returns the applyAudit.
	 */
	
	
	
	
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
	 * @param wgtChargeUsd The wgtChargeUsd to set.
	 */
	public void setWgtChargeUsd(double wgtChargeUsd) {
		this.wgtChargeUsd = wgtChargeUsd;
	}
	
	/**
	 * @param wgtChargeBas The wgtChargeBas to set.
	 */
	public void setWgtChargeBas(double wgtChargeBas) {
		this.wgtChargeBas = wgtChargeBas;
	}
	/**
	 * @param dueAirline The dueAirline to set.
	 */
	public void setDueAirline(double dueAirline) {
		this.dueAirline = dueAirline;
	}
	/**
	 * @param aplRate The aplRate to set.
	 */
	public void setAplRate(double aplRate) {
		this.aplRate = aplRate;
	}
	
	
	
	/**
	 * @param blgStatus The blgStatus to set.
	 */
	public void setBlgStatus(String blgStatus) {
		this.blgStatus = blgStatus;
	}
	
	/**
	 * @param contractCurrCode The contractCurrCode to set.
	 */
	public void setContractCurrCode(String contractCurrCode) {
		this.contractCurrCode = contractCurrCode;
	}
	
	
	/**
	 * @param flightCarrierID The flightCarrierID to set.
	 */
	public void setFlightCarrierID(int flightCarrierID) {
		this.flightCarrierID = flightCarrierID;
	}
	
	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @param flightSeqNumber The flightSeqNumber to set.
	 */
	public void setFlightSeqNumber(int flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	/**
	 * @param paymentFlag The paymentFlag to set.
	 */
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	
	/**
	 * @param proratedValue The proratedValue to set.
	 */
	public void setProratedValue(double proratedValue) {
		this.proratedValue = proratedValue;
	}
	/**
	 * @param prorationPercentage The prorationPercentage to set.
	 */
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}
	/**
	 * @param prorationType The prorationType to set.
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}
	
	
	/**
	 * @param segFrom The segFrom to set.
	 */
	public void setSegFrom(String segFrom) {
		this.segFrom = segFrom;
	}
	/**
	 * @param segSerialNumber The segSerialNumber to set.
	 */
	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
	}
	/**
	 * @param segTo The segTo to set.
	 */
	public void setSegTo(String segTo) {
		this.segTo = segTo;
	}
	
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @param revFlag The revFlag to set.
	 */
	public void setRevFlag(String revFlag) {
		this.revFlag = revFlag;
	}
	
	
	
//	/**
//	 * @return the updBillToIdr
//	 */
//	@Column(name = "UPDBILTOOIDR")
//	public int getUpdBillToIdr() {
//		return updBillToIdr;
//	}
//
//	/**
//	 * @param updBillToIdr the updBillToIdr to set
//	 */
//	public void setUpdBillToIdr(int updBillToIdr) {
//		this.updBillToIdr = updBillToIdr;
//	}

	
	/**
	 * @return the acctxnIdr
	 */
	@Column(name = "ACCTXNIDR")
	public String getAcctxnIdr() {
		return acctxnIdr;
	}

	/**
	 * @param acctxnIdr the acctxnIdr to set
	 */
	public void setAcctxnIdr(String acctxnIdr) {
		this.acctxnIdr = acctxnIdr;
	}

	/**
	
	
	/**
	 * @return Returns the mraBillingDetailsPk.
	 */
	public MRABillingDetailsHistory() {

	  }

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column =@Column(name = "CMPCOD")),
		@AttributeOverride(name = "mailSeqNumber", column =@Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "serNumber", column =@Column(name = "SERNUM")),
		@AttributeOverride(name = "versionNumber", column =@Column(name = "VERNUM"))})
		public MRABillingDetailsHistoryPK getMRABillingDetailsHistoryPK() {
		return mRABillingDetailsHistoryPK;
	}
	/**
	 * @param mraBillingDetailsPk The mraBillingDetailsPk to set.
	 */
	public void setMRABillingDetailsHistoryPK(MRABillingDetailsHistoryPK mRABillingDetailsHistoryPK) {
		this.mRABillingDetailsHistoryPK = mRABillingDetailsHistoryPK;
	}
	
	
	/**
	 * 
	 * @param rateAuditDetailsVO
	 * @throws SystemException
	 */
	public MRABillingDetailsHistory(RateAuditDetailsVO rateAuditDetailsVO )throws SystemException{
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
		
		int seqnum =0;
		
		try {
			seqnum=findMaxSeqnumFromMRABillingDetailsHistory(rateAuditDetailsVO);
		} catch (SystemException e) {			
			e.getMessage();
		}
		
		MRABillingDetailsHistoryPK mraBillingDetailsHistoryPK=new MRABillingDetailsHistoryPK(
				rateAuditDetailsVO.getCompanyCode(),
				rateAuditDetailsVO.getSerNum(),rateAuditDetailsVO.getMailSequenceNumber(),(seqnum+1)			
		);
		this.setMRABillingDetailsHistoryPK(mraBillingDetailsHistoryPK);
		
		
	}
	
	private int findMaxSeqnumFromMRABillingDetailsHistory(RateAuditDetailsVO rateAuditDetailsVO)throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("MRABillingDetails","entity");
			return constructDAO().findMaxSeqnumFromMRABillingDetailsHistory(rateAuditDetailsVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	
	 /**
	 * This method constructs
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
	 * @param rateAuditDetailsVO
	 */
	private void populateAttributesFromRateAudit(RateAuditDetailsVO rateAuditDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes");	
		
		this.setAplRate(rateAuditDetailsVO.getRate());
		//this.setApplyAudit(rateAuditDetailsVO.getApplyAudt());		
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
//		this.setInitialRate(rateAuditDetailsVO.getInitialRate());
		this.setAcctxnIdr(rateAuditDetailsVO.getAcctxnIdr());
		this.setDueAirline(rateAuditDetailsVO.getDueAirline());
		this.setProrationPercentage(rateAuditDetailsVO.getProPercent());
		this.setProratedValue(rateAuditDetailsVO.getProValue());
		this.setRevFlag(rateAuditDetailsVO.getRevFlg());
		this.setRemarks(rateAuditDetailsVO.getRemark());
		this.setSegSerialNumber(rateAuditDetailsVO.getSegSerNo());
		this.setTriggerPoint(rateAuditDetailsVO.getCompTotTrigPt());
		this.setLastUpdateTime(rateAuditDetailsVO.getLastUpdateTime());
		this.setLastUpdateUser(rateAuditDetailsVO.getLastUpdateUser());
		this.setInterfaceFlag(FLAG_NO);
		returnLogger().exiting(CLASS_NAME,"populateAttributes");
		
		
	}

	

	/**
	 * @return the lastUpdateTime
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	/**
	 * @author A-2391
	 * @param prorationDetailsVO
	 * @throws SystemException
	 */
	public MRABillingDetailsHistory(ProrationDetailsVO prorationDetailsVO )throws SystemException{
		returnLogger().entering(CLASS_NAME,"MRABillingDetailsTemp-Constructor for manual proration");
		populatePKFromProration(prorationDetailsVO);
		populateAttributesFromProration(prorationDetailsVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		
		returnLogger().exiting(CLASS_NAME,"MRABillingDetailsTemp-Constructor for manual proration");
	}
	/**
	 * @param rateAuditDetailsVO
	 */
	private void populateAttributesFromProration(ProrationDetailsVO prorationDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes for proration");	
		
		this.setAplRate(prorationDetailsVO.getRate());
		//this.setApplyAudit(prorationDetailsVO.getAppAud());		
		this.setFlightCarrierCode(prorationDetailsVO.getCarrierCode());								
		this.setFlightNumber(prorationDetailsVO.getFlightNumber());	
		this.setPaymentFlag(prorationDetailsVO.getPayableFlag());
		this.setSegFrom(prorationDetailsVO.getSectorFrom());
		this.setSegTo(prorationDetailsVO.getSectorTo());
		this.setBlgStatus(prorationDetailsVO.getBlgSta());
		this.setFlightCarrierID(prorationDetailsVO.getFlightCarrierIdentifier());		
		this.setWgtChargeUsd(prorationDetailsVO.getProrationAmtInUsd().getRoundedAmount());
		this.setWgtChargeBas(prorationDetailsVO.getProrationAmtInBaseCurr().getRoundedAmount());
		this.setContractCurrCode(prorationDetailsVO.getCtrCurrencyCode());
		this.setFlightDate(prorationDetailsVO.getFlightDate());
		this.setFlightSeqNumber(prorationDetailsVO.getFlightSequenceNumber());
		this.setProrationType(prorationDetailsVO.getProrationType());
		this.setAcctxnIdr(prorationDetailsVO.getAccTxnIdr());
		this.setDueAirline(prorationDetailsVO.getDueArl());
		this.setProrationPercentage(prorationDetailsVO.getProrationPercentage());
		this.setProratedValue(prorationDetailsVO.getProratedValue());
		this.setRevFlag(prorationDetailsVO.getRevFlg());
		this.setRemarks("");
		this.setSegSerialNumber(prorationDetailsVO.getSegSerNum());
		this.setTriggerPoint("MP");
		LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	
		this.setLastUpdateTime(fromDate);
		this.setLastUpdateUser(prorationDetailsVO.getLastUpdateUser());
		returnLogger().exiting(CLASS_NAME,"populateAttributes for proration");
		
	}
	/**
	 * @param rateAuditDetailsVO
	 */
	private void populatePKFromProration(ProrationDetailsVO prorationDetailsVO) {
		
		int seqnum =0;
		
		try {
			seqnum=findMaxSeqnumFromMRABillingDetailsHistoryForProration(prorationDetailsVO);
		} catch (SystemException e) {			
			e.getMessage();
		}
		
		MRABillingDetailsHistoryPK mraBillingDetailsHistoryPK=new MRABillingDetailsHistoryPK(			
				prorationDetailsVO.getCompanyCode(),
				prorationDetailsVO.getSerialNumber(),prorationDetailsVO.getMailSequenceNumber(),(seqnum+1)			
		);
		this.setMRABillingDetailsHistoryPK(mraBillingDetailsHistoryPK);
		
		
	}
	private int findMaxSeqnumFromMRABillingDetailsHistoryForProration(ProrationDetailsVO prorationDetailsVO)throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("findMaxSeqnumFromMRABillingDetailsHistoryForProration","entity");
			RateAuditDetailsVO rateVO=new RateAuditDetailsVO();
			rateVO.setCompanyCode(prorationDetailsVO.getCompanyCode());
			rateVO.setBillingBasis(prorationDetailsVO.getBillingBasis());
			rateVO.setCsgDocNum(prorationDetailsVO.getConsigneeDocumentNumber());
			rateVO.setCsgSeqNum(Integer.parseInt(prorationDetailsVO.getConsigneeSequenceNumber()));
			rateVO.setGpaCode(prorationDetailsVO.getPostalAuthorityCode());
			return constructDAO().findMaxSeqnumFromMRABillingDetailsHistory(rateVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * 
	 * 	Method		:	MRABillingDetailsHistory.findFlightrevenueDetails
	 *	Added by 	:	a-8061 on 29-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param companycode
	 *	Parameters	:	@param rowCount
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<FlightRevenueInterfaceVO>
	 */
	public Collection<FlightRevenueInterfaceVO> findFlightrevenueDetails(String companycode, boolean isFromRetrigger)throws SystemException {
		
		 try {
			return  constructDAO().findFlightrevenueDetails(companycode,isFromRetrigger);
		} catch (PersistenceException e) {
			
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	
	
	/**
	 * 
	 * 	Method		:	MRABillingDetailsHistory.find
	 *	Added by 	:	a-8061 on 29-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param mRABillingDetailsHistoryPK
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws FinderException 
	 *	Return type	: 	MRABillingDetailsHistory
	 */
	public static MRABillingDetailsHistory find(MRABillingDetailsHistoryPK mRABillingDetailsHistoryPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(
				MRABillingDetailsHistory.class, mRABillingDetailsHistoryPK);
	}
	
	/**
	 * 
	 * 	Method		:	MRABillingDetailsHistory.updateInterfaceStatus
	 *	Added by 	:	a-8061 on 29-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param flightRevenueInterfaceVOs 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws FinderException 
	 */
	public void updateInterfaceStatus(Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs,String statusFLag) throws SystemException {
		// TODO Auto-generated method stub
		LocalDate currTime=null;
		int versionNumber=0;
		try{
		if(flightRevenueInterfaceVOs!=null && !flightRevenueInterfaceVOs.isEmpty()){
		for(FlightRevenueInterfaceVO flightRevenueInterfaceVO :flightRevenueInterfaceVOs ){
			versionNumber=flightRevenueInterfaceVO.getVersionNumber();
			for(int tmpverno=versionNumber;tmpverno<=versionNumber&&tmpverno>0;tmpverno--){
			MRABillingDetailsHistoryPK mraBillingDetailsHistoryPK=new MRABillingDetailsHistoryPK(			
					flightRevenueInterfaceVO.getCompanyCode(),
					flightRevenueInterfaceVO.getSerNumber(),flightRevenueInterfaceVO.getMailSeqNumber(),tmpverno			
			);

			MRABillingDetailsHistory mRABillingDetailsHistory=null;
			try {
				mRABillingDetailsHistory = MRABillingDetailsHistory.find(mraBillingDetailsHistoryPK);
			} catch (FinderException e) {
				e.getErrorCode();
			}
			if(mRABillingDetailsHistory!=null){
			currTime=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
//Commented for monitoring the duplication on interfacing for bug ICRD-338009 by A-5526.Temporary change
			//if(FLAG_NO.equals(mRABillingDetailsHistory.getInterfaceFlag())||FLAG_FAIL.equals(mRABillingDetailsHistory.getInterfaceFlag())){
			mRABillingDetailsHistory.setInterfaceTime(currTime);
			mRABillingDetailsHistory.setInterfaceFlag(statusFLag);
			//}
			
			}
			}
		}
		}
		}catch( Throwable e){
			e.getMessage();
		//If exception occurred for one data,the transaction shouldn't get roll back.	
		}

	}
}
