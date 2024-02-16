/*
 * MRABillingDetailsTemp.java Created on July 6, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
@Table(name = "MTKMRABLGDTLTMP")
@Entity
public class MRABillingDetailsTemp {	
	
	private static final String CLASS_NAME = "MRABillingDetailsTemp";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private MRABillingDetailsTempPK mRABillingDetailsTempPK;
	
	private String segFrom;
	private String segTo;
	private double aplRate;
	private int updPieces;
	private double updWeight;
	private Calendar flightDate;
	private int flightCarrierID;
	private String flightNumber;
	private int flightSeqNumber;
	private String flightCarrierCode;
	private int segSerialNumber;
	private double wgtCharge;
	private double updWgtCharge;
	private double wgtChargeUsd;
	private double wgtChargeSdr;
	private double wgtChargeBas;
	private double dueAirline;
	private String discrepancy;
	private String applyAudit;
	private String updBillTo;
	private String prorationType;
	private double prorationPercentage;
	private double proratedValue;
	private String paymentFlag;
	private String gpaArlBlgStatus;
	private String blgStatus;
	private String contractCurrCode;
	private String revFlag;
	private String remarks;
	
	
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
	
	@Column(name = "APPAUD")
	public String getApplyAudit() {
		return applyAudit;
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
	 * @return Returns the discrepancy.
	 */
	@Column(name = "DISPCY")
	public String getDiscrepancy() {
		return discrepancy;
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
	@Version
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
	 * @return Returns the gpaArlBlgStatus.
	 */
	@Column(name = "GPAARLBLGFLG")
	public String getGpaArlBlgStatus() {
		return gpaArlBlgStatus;
	}
	/**
	 * @return Returns the paymentFlag.
	 */
	@Column(name = "PAYFLG")
	public String getPaymentFlag() {
		return paymentFlag;
	}
	/**
	 * @return Returns the updPieces.
	 */
	@Column(name = "UPDPCS")
	public int getUpdPieces() {
		return updPieces;
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
	 * @return Returns the updBillTo.
	 */
	@Column(name = "UPDBILLTO")
	public String getUpdBillTo() {
		return updBillTo;
	}
	
	/**
	 * @return Returns the updWgtCharge.
	 */
	@Column(name = "UPDWGTCHG")
	public double getUpdWgtCharge() {
		return updWgtCharge;
	}
	/**
	 * @return Returns the  updWeight.
	 */
	@Column(name = "UPDWGT")
	public double getUpdWeight() {
		return updWeight;
	}
	/**
	 * @return Returns the wgtCharge.
	 */
	@Column(name = "WGTCHG")
	public double getWgtCharge() {
		return wgtCharge;
	}
	/**
	 * @return Returns the wgtChargeUsd.
	 */
	@Column(name = "WGTCHGUSD")
	public double getWgtChargeUsd() {
		return wgtChargeUsd;
	}
	/**
	 * @return Returns the wgtChargeSdr.
	 */
	@Column(name = "WGTCHGSDR")
	public double getWgtChargeSdr() {
		return wgtChargeSdr;
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
	 * @param wgtChargeSdr The wgtChargeSdr to set.
	 */
	public void setWgtChargeSdr(double wgtChargeSdr) {
		this.wgtChargeSdr = wgtChargeSdr;
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
	 * @param applyAudit The applyAudit to set.
	 */
	public void setApplyAudit(String applyAudit) {
		this.applyAudit = applyAudit;
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
	 * @param discrepancy The discrepancy to set.
	 */
	public void setDiscrepancy(String discrepancy) {
		this.discrepancy = discrepancy;
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
	 * @param gpaArlBlgStatus The gpaArlBlgStatus to set.
	 */
	public void setGpaArlBlgStatus(String gpaArlBlgStatus) {
		this.gpaArlBlgStatus = gpaArlBlgStatus;
	}
	/**
	 * @param paymentFlag The paymentFlag to set.
	 */
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	/**
	 * @param updPieces The updPieces to set.
	 */
	public void setUpdPieces(int updPieces) {
		this.updPieces = updPieces;
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
	 * @param updBillTo The updBillTo to set.
	 */
	public void setUpdBillTo(String updBillTo) {
		this.updBillTo = updBillTo;
	}
	
	/**
	 * @param updWgtCharge The updWgtCharge to set.
	 */
	public void setUpdWgtCharge(double updWgtCharge) {
		this.updWgtCharge = updWgtCharge;
	}
	/**
	 * @param  updWeight The  updWeight to set.
	 */
	public void setUpdWeight(double updWeight) {
		this.updWeight = updWeight;
	}
	/**
	 * @param wgtCharge The wgtCharge to set.
	 */
	public void setWgtCharge(double wgtCharge) {
		this.wgtCharge = wgtCharge;
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
	/**
	 * @return Returns the MRABillingDetailsTempPK.
	 */
	public MRABillingDetailsTemp() {

	  }
	
	
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")),
		@AttributeOverride(name = "csgSeqNum", column = @Column(name = "CSGSEQNUM")),
		@AttributeOverride(name = "csgDocumentNumber", column = @Column(name = "CSGDOCNUM")),
		@AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS")),
		@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")) })
		public MRABillingDetailsTempPK getMRABillingDetailsTempPK() {
		return mRABillingDetailsTempPK;
	}
	/**
	 * @param mRABillingDetailsTempPK 
	 * The MRABillingDetailsTempPK to set.
	 */
	public void setMRABillingDetailsTempPK(MRABillingDetailsTempPK mRABillingDetailsTempPK) {
		this.mRABillingDetailsTempPK = mRABillingDetailsTempPK;
	}

	
	/**
	 * 
	 * @param rateAuditDetailsVO
	 * @throws SystemException
	 */
	public MRABillingDetailsTemp(RateAuditDetailsVO rateAuditDetailsVO )throws SystemException{
		returnLogger().entering(CLASS_NAME,"MRABillingDetailsTemp-Constructor");
		populatePK(rateAuditDetailsVO);
		populateAttributes(rateAuditDetailsVO);
		
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
	private void populatePK(RateAuditDetailsVO rateAuditDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populatePK");
		
		MRABillingDetailsTempPK mRABillingDetailTempPK=new MRABillingDetailsTempPK(
				rateAuditDetailsVO.getSerNum(),
				rateAuditDetailsVO.getCsgSeqNum(),
				rateAuditDetailsVO.getCsgDocNum(),
				rateAuditDetailsVO.getBillingBasis(),
				rateAuditDetailsVO.getCompanyCode(),
				rateAuditDetailsVO.getGpaCode()				
		);
		
		this.setMRABillingDetailsTempPK(mRABillingDetailTempPK);
		
		returnLogger().exiting(CLASS_NAME,"populatePK");
	}
	
	
	/**
	 * @param rateAuditDetailsVO
	 */
	private void populateAttributes(RateAuditDetailsVO rateAuditDetailsVO) {
		
		returnLogger().entering(CLASS_NAME,"populateAttributes");	
		
		this.setAplRate(rateAuditDetailsVO.getRate());
		this.setUpdPieces(rateAuditDetailsVO.getNoPieces());
		this.setUpdWeight(rateAuditDetailsVO.getGrsWgt());
		this.setFlightNumber(rateAuditDetailsVO.getFlightno());
		this.setFlightCarrierCode(rateAuditDetailsVO.getCarrierCode());
		this.setWgtCharge(rateAuditDetailsVO.getPrsntWgtCharge().getAmount());
		this.setUpdWgtCharge(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());
		this.setDiscrepancy(rateAuditDetailsVO.getDiscrepancy());
		this.setApplyAudit(rateAuditDetailsVO.getApplyAudt());
		this.setUpdBillTo(rateAuditDetailsVO.getBillTO());
		this.setPaymentFlag(rateAuditDetailsVO.getPayFlag());
			
		
		returnLogger().exiting(CLASS_NAME,"populateAttributes");
		
	}
	
	
	 /**
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()
    throws SystemException{    	
    	try{
    	PersistenceController.getEntityManager().remove(this);
    	}
    	catch(RemoveException removeException){
    		throw new SystemException(removeException.getMessage(),removeException);
    	}
    }
	
	
	
	
}
