/*
 * RateAuditDetailsVO.java created on Mar 06, 2007
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
 * @author A-3251
 *
 */
public class RateAuditDetailsVO extends AbstractVO{
	
	private String companyCode;
	private int noPieces;
	private double grsWgt;
	private String category;
	private String subclass;
	private String uldno;
	private String flightno;
	private String carrierCode;
	private int carrierid;
	private double rate;
	private Money prsntWgtCharge;
	private Money audtdWgtCharge;
	private String discrepancy;
	private String billTO;
	private String applyAudt;
	private String payFlag;
	private int serNum;
	private int csgSeqNum;
	private String csgDocNum;
	private String billingBasis;
	private String gpaCode;
	private String secFrom;
	private String secTo;
	private String gpaarlBillingFlag;
	private String billingStatus;
	private LocalDate recVDate;
	
	
	private double wgtChargeUSD;
	private double wgtChargeSDR;
	private double wgtChargeBAS;
	private String contCurCode;
	private int updBillToIdr;
	
	private LocalDate flightDate; 
	private int flightseqno;
	private int segSerNo;
	private String prorationType;
	private double initialRate;
	
	private String accsta;
	private String acctxnIdr; 
	private double duepoa;
	private double proPercent;
	private double proValue;
	private String revFlg;
	private String remark;
	private double dueAirline;
	
	 private LocalDate lastUpdateTime;
	 private String lastUpdateUser;
	 private String compTotTrigPt;
	 private String sectStatus;
	 private String currency;
	 
	 private double fulChg;
	 private String ratInd;
	 //Added by A-7794 as part of MRA revamp
	 //private int mailSequenceNumber;
	 private String containerNumber;
	 private String containerType;
	 private String paBuiltFlag;
	 private String mailbagId;
		private long mailSequenceNumber;
	 private String processStatus;
	 private String source;
	
	public String getMailbagId() {
			return mailbagId;
		}
		public void setMailbagId(String mailbagId) {
			this.mailbagId = mailbagId;
		}
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	/**
	 * @return Returns the ratInd.
	 */
	public String getRatInd() {
		return ratInd;
	}
	/**
	 * @param ratInd The ratInd to set.
	 */
	public void setRatInd(String ratInd) {
		this.ratInd = ratInd;
	}
	/**
	 * @return Returns the fulChg.
	 */
	public double getFulChg() {
		return fulChg;
	}
	/**
	 * @param fulChg The fulChg to set.
	 */
	public void setFulChg(double fulChg) {
		this.fulChg = fulChg;
	}
	/**
	 * @return the compTotTrigPt
	 */
	public String getCompTotTrigPt() {
		return compTotTrigPt;
	}
	/**
	 * @param compTotTrigPt the compTotTrigPt to set
	 */
	public void setCompTotTrigPt(String compTotTrigPt) {
		this.compTotTrigPt = compTotTrigPt;
	}
	/**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the lastUpdateUser
	 */
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
	 * @return the initialRate
	 */
	public double getInitialRate() {
		return initialRate;
	}
	/**
	 * @param initialRate the initialRate to set
	 */
	public void setInitialRate(double initialRate) {
		this.initialRate = initialRate;
	}
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the applyAudt
	 */
	public String getApplyAudt() {
		return applyAudt;
	}
	/**
	 * @param applyAudt the applyAudt to set
	 */
	public void setApplyAudt(String applyAudt) {
		this.applyAudt = applyAudt;
	}
	
	/**
	 * @return the billTO
	 */
	public String getBillTO() {
		return billTO;
	}
	/**
	 * @param billTO the billTO to set
	 */
	public void setBillTO(String billTO) {
		this.billTO = billTO;
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
	/**
	 * @return the carrierid
	 */
	public int getCarrierid() {
		return carrierid;
	}
	/**
	 * @param carrierid the carrierid to set
	 */
	public void setCarrierid(int carrierid) {
		this.carrierid = carrierid;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return the flightno
	 */
	public String getFlightno() {
		return flightno;
	}
	/**
	 * @param flightno the flightno to set
	 */
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	/**
	 * @return the grsWgt
	 */
	public double getGrsWgt() {
		return grsWgt;
	}
	/**
	 * @param grsWgt the grsWgt to set
	 */
	public void setGrsWgt(double grsWgt) {
		this.grsWgt = grsWgt;
	}	
	/**
	 * @return the payFlag
	 */
	public String getPayFlag() {
		return payFlag;
	}
	/**
	 * @param payFlag the payFlag to set
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
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
	 * @return the subclass
	 */
	public String getSubclass() {
		return subclass;
	}
	/**
	 * @param subclass the subclass to set
	 */
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	/**
	 * @return the uldno
	 */
	public String getUldno() {
		return uldno;
	}
	/**
	 * @param uldno the uldno to set
	 */
	public void setUldno(String uldno) {
		this.uldno = uldno;
	}
	/**
	 * @return the serNum
	 */
	public int getSerNum() {
		return serNum;
	}
	/**
	 * @param serNum the serNum to set
	 */
	public void setSerNum(int serNum) {
		this.serNum = serNum;
	}
	/**
	 * @return the noPieces
	 */
	public int getNoPieces() {
		return noPieces;
	}
	/**
	 * @param noPieces the noPieces to set
	 */
	public void setNoPieces(int noPieces) {
		this.noPieces = noPieces;
	}
	/**
	 * @return the discrepancy
	 */
	public String getDiscrepancy() {
		return discrepancy;
	}
	/**
	 * @param discrepancy the discrepancy to set
	 */
	public void setDiscrepancy(String discrepancy) {
		this.discrepancy = discrepancy;
	}
	/**
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}
	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
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
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}
	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}
	/**
	 * @return the secTo
	 */
	public String getSecTo() {
		return secTo;
	}
	/**
	 * @param secTo the secTo to set
	 */
	public void setSecTo(String secTo) {
		this.secTo = secTo;
	}
	/**
	 * @return the secFrom
	 */
	public String getSecFrom() {
		return secFrom;
	}
	/**
	 * @param secFrom the secFrom to set
	 */
	public void setSecFrom(String secFrom) {
		this.secFrom = secFrom;
	}
	/**
	 * @return the gpaarlBillingFlag
	 */
	public String getGpaarlBillingFlag() {
		return gpaarlBillingFlag;
	}
	/**
	 * @param gpaarlBillingFlag the gpaarlBillingFlag to set
	 */
	public void setGpaarlBillingFlag(String gpaarlBillingFlag) {
		this.gpaarlBillingFlag = gpaarlBillingFlag;
	}
	/**
	 * @return the billingStatus
	 */
	public String getBillingStatus() {
		return billingStatus;
	}
	/**
	 * @param billingStatus the billingStatus to set
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
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
	 * @return the contCurCode
	 */
	public String getContCurCode() {
		return contCurCode;
	}
	/**
	 * @param contCurCode the contCurCode to set
	 */
	public void setContCurCode(String contCurCode) {
		this.contCurCode = contCurCode;
	}
	/**
	 * @return the wgtChargeBAS
	 */
	public double getWgtChargeBAS() {
		return wgtChargeBAS;
	}
	/**
	 * @param wgtChargeBAS the wgtChargeBAS to set
	 */
	public void setWgtChargeBAS(double wgtChargeBAS) {
		this.wgtChargeBAS = wgtChargeBAS;
	}
	/**
	 * @return the wgtChargeSDR
	 */
	public double getWgtChargeSDR() {
		return wgtChargeSDR;
	}
	/**
	 * @param wgtChargeSDR the wgtChargeSDR to set
	 */
	public void setWgtChargeSDR(double wgtChargeSDR) {
		this.wgtChargeSDR = wgtChargeSDR;
	}
	/**
	 * @return the wgtChargeUSD
	 */
	public double getWgtChargeUSD() {
		return wgtChargeUSD;
	}
	/**
	 * @param wgtChargeUSD the wgtChargeUSD to set
	 */
	public void setWgtChargeUSD(double wgtChargeUSD) {
		this.wgtChargeUSD = wgtChargeUSD;
	}
	/**
	 * @return the updBillToIdr
	 */
	public int getUpdBillToIdr() {
		return updBillToIdr;
	}
	/**
	 * @param updBillToIdr the updBillToIdr to set
	 */
	public void setUpdBillToIdr(int updBillToIdr) {
		this.updBillToIdr = updBillToIdr;
	}
	/**
	 * @return the prsntWgtCharge
	 */
	public Money getPrsntWgtCharge() {
		return prsntWgtCharge;
	}
	/**
	 * @param prsntWgtCharge the prsntWgtCharge to set
	 */
	public void setPrsntWgtCharge(Money prsntWgtCharge) {
		this.prsntWgtCharge = prsntWgtCharge;
	}
	/**
	 * @return the audtdWgtCharge
	 */
	public Money getAudtdWgtCharge() {
		return audtdWgtCharge;
	}
	/**
	 * @param audtdWgtCharge the audtdWgtCharge to set
	 */
	public void setAudtdWgtCharge(Money audtdWgtCharge) {
		this.audtdWgtCharge = audtdWgtCharge;
	}
	/**
	 * @return the flightseqno
	 */
	public int getFlightseqno() {
		return flightseqno;
	}
	/**
	 * @param flightseqno the flightseqno to set
	 */
	public void setFlightseqno(int flightseqno) {
		this.flightseqno = flightseqno;
	}
	/**
	 * @return the prorationType
	 */
	public String getProrationType() {
		return prorationType;
	}
	/**
	 * @param prorationType the prorationType to set
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}
	/**
	 * @return the accsta
	 */
	public String getAccsta() {
		return accsta;
	}
	/**
	 * @param accsta the accsta to set
	 */
	public void setAccsta(String accsta) {
		this.accsta = accsta;
	}
	/**
	 * @return the acctxnIdr
	 */
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
	 * @return the duepoa
	 */
	public double getDuepoa() {
		return duepoa;
	}
	/**
	 * @param duepoa the duepoa to set
	 */
	public void setDuepoa(double duepoa) {
		this.duepoa = duepoa;
	}
	/**
	 * @return the proPercent
	 */
	public double getProPercent() {
		return proPercent;
	}
	/**
	 * @param proPercent the proPercent to set
	 */
	public void setProPercent(double proPercent) {
		this.proPercent = proPercent;
	}
	/**
	 * @return the proValue
	 */
	public double getProValue() {
		return proValue;
	}
	/**
	 * @param proValue the proValue to set
	 */
	public void setProValue(double proValue) {
		this.proValue = proValue;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the revFlg
	 */
	public String getRevFlg() {
		return revFlg;
	}
	/**
	 * @param revFlg the revFlg to set
	 */
	public void setRevFlg(String revFlg) {
		this.revFlg = revFlg;
	}
	/**
	 * @return the dueAirline
	 */
	public double getDueAirline() {
		return dueAirline;
	}
	/**
	 * @param dueAirline the dueAirline to set
	 */
	public void setDueAirline(double dueAirline) {
		this.dueAirline = dueAirline;
	}
	/**
	 * @return the segSerNo
	 */
	public int getSegSerNo() {
		return segSerNo;
	}
	/**
	 * @param segSerNo the segSerNo to set
	 */
	public void setSegSerNo(int segSerNo) {
		this.segSerNo = segSerNo;
	}
	/**
	 * @return the sectStatus
	 */
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
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
