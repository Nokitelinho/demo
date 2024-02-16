/*
 * MRABillingDetailsVO.java Created on July 6, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Calendar;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-2554
 *
 */
public class MRABillingDetailsVO extends AbstractVO{
	
	private int sequenceNumber;
	private int csgSeqNumber;
	private String csgDocumentNumber;
	private String billingBasis;
	private String companyCode;
	private String poaCode;
	
	private String consignmentOrigin;
	private String consignmentDestination;
	private LocalDate consignmentDate;
	private String gpaCountryCode;
	
	private String segFrom;
	private String segTo;
	private String route;
	private double aplRate;
	private int pieces;
	private double weight;
	private Calendar flightDate;
	private int flightCarrierID;
	private String flightNumber;
	private String flightCarrierCode;
	private int flightSeqNumber;
	private int segSerialNumber;
	private double wgtCharge;
	private double surCharge; //Added by a-7871 for ICRD-154005
	private double updWgtCharge;
	private double wgtChargeBas;
	private double wgtChargeSdr;
	private double wgtChargeUsd;
	private double dueAirline;
	
	private String discrepancy;
	private String applyAudit;
	private String billTo;
	private String updMailCtgCode;
	private String updMailSubClsCode;
	private String updBillTo;
	private String prorationType;
	private double prorationPercentage;
	private double proratedAmtUSD;
	private double proratedAmtSplDrawRate;
	private double proratedAmtBaseCurr;
	private double proratedValue;
	private double proratedAmtContractCurr;
	private String paymentFlag;
	private String gpaArlBlgStatus;
	private String blgStatus;
	private String contractCurrCode;
	private String revFlag;
	
	private String operationFlag;
	private int prorationFactor;
	private String sectorStatus;
	private String carrierCode;
	private Boolean isFindFlag;
	
	private double fulChg;
	private String ratInd;
	
	private double serviceTax;
	private double tds;
	
	private double taxPercentage;
	private double tdsPercentage;
	private Money netAmount;
	private double valCharges;
	
	private String mailbagOrigin;
	private String mailbagDestination;	
	
	private long mailSequenceNumber;
	private LocalDate recieveDate;
	private int seqNumInt;
	/**
	 * @return the taxPercentage
	 */
	public double getTaxPercentage() {
		return taxPercentage;
	}
	/**
	 * @param taxPercentage the taxPercentage to set
	 */
	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	/**
	 * @return the tdsPercentage
	 */
	public double getTdsPercentage() {
		return tdsPercentage;
	}
	/**
	 * @param tdsPercentage the tdsPercentage to set
	 */
	public void setTdsPercentage(double tdsPercentage) {
		this.tdsPercentage = tdsPercentage;
	}
	private boolean isTaxIncludedInRateFlag; 
	
	
	
	
	/**
	 * @return the isTaxIncludedInRateFlag
	 */
	public boolean isTaxIncludedInRateFlag() {
		return isTaxIncludedInRateFlag;
	}
	/**
	 * @param isTaxIncludedInRateFlag the isTaxIncludedInRateFlag to set
	 */
	public void setTaxIncludedInRateFlag(boolean isTaxIncludedInRateFlag) {
		this.isTaxIncludedInRateFlag = isTaxIncludedInRateFlag;
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
	 * @return Returns the isFindFlag.
	 */
	public Boolean getIsFindFlag() {
		return isFindFlag;
	}
	/**
	 * @param isFindFlag The isFindFlag to set.
	 */
	public void setIsFindFlag(Boolean isFindFlag) {
		this.isFindFlag = isFindFlag;
	}
	/**
	 * @return Returns the dueAirline.
	 */
	public double getDueAirline() {
		return dueAirline;
	}
	/**
	 * @param dueAirline The dueAirline to set.
	 */
	public void setDueAirline(double dueAirline) {
		this.dueAirline = dueAirline;
	}
	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return Returns the wgtChargeBas.
	 */
	public double getWgtChargeBas() {
		return wgtChargeBas;
	}
	/**
	 * @param wgtChargeBas The wgtChargeBas to set.
	 */
	public void setWgtChargeBas(double wgtChargeBas) {
		this.wgtChargeBas = wgtChargeBas;
	}
	/**
	 * @return Returns the wgtChargeSdr.
	 */
	public double getWgtChargeSdr() {
		return wgtChargeSdr;
	}
	/**
	 * @param wgtChargeSdr The wgtChargeSdr to set.
	 */
	public void setWgtChargeSdr(double wgtChargeSdr) {
		this.wgtChargeSdr = wgtChargeSdr;
	}
	/**
	 * @return Returns the wgtChargeUsd.
	 */
	public double getWgtChargeUsd() {
		return wgtChargeUsd;
	}
	/**
	 * @param wgtChargeUsd The wgtChargeUsd to set.
	 */
	public void setWgtChargeUsd(double wgtChargeUsd) {
		this.wgtChargeUsd = wgtChargeUsd;
	}
	/**
	 * @return Returns the revFlag.
	 */
	public String getRevFlag() {
		return revFlag;
	}
	/**
	 * @param revFlag The revFlag to set.
	 */
	public void setRevFlag(String revFlag) {
		this.revFlag = revFlag;
	}
	/**
	 * @return Returns the poaCode.
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
	 * @return Returns the aplRate.
	 */
	public double getAplRate() {
		return aplRate;
	}
	/**
	 * @param aplRate The aplRate to set.
	 */
	public void setAplRate(double aplRate) {
		this.aplRate = aplRate;
	}
	/**
	 * @return Returns the applyAudit.
	 */
	public String getApplyAudit() {
		return applyAudit;
	}
	/**
	 * @param applyAudit The applyAudit to set.
	 */
	public void setApplyAudit(String applyAudit) {
		this.applyAudit = applyAudit;
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
	 * @return Returns the blgStatus.
	 */
	public String getBlgStatus() {
		return blgStatus;
	}
	/**
	 * @param blgStatus The blgStatus to set.
	 */
	public void setBlgStatus(String blgStatus) {
		this.blgStatus = blgStatus;
	}
	/**
	 * @return Returns the contractCurrCode.
	 */
	public String getContractCurrCode() {
		return contractCurrCode;
	}
	/**
	 * @param contractCurrCode The contractCurrCode to set.
	 */
	public void setContractCurrCode(String contractCurrCode) {
		this.contractCurrCode = contractCurrCode;
	}
	/**
	 * @return Returns the discrepancy.
	 */
	public String getDiscrepancy() {
		return discrepancy;
	}
	/**
	 * @param discrepancy The discrepancy to set.
	 */
	public void setDiscrepancy(String discrepancy) {
		this.discrepancy = discrepancy;
	}
	/**
	 * @return Returns the flightCarrierID.
	 */
	public int getFlightCarrierID() {
		return flightCarrierID;
	}
	/**
	 * @param flightCarrierID The flightCarrierID to set.
	 */
	public void setFlightCarrierID(int flightCarrierID) {
		this.flightCarrierID = flightCarrierID;
	}
	/**
	 * @return Returns the flightDate.
	 */
	public Calendar getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the flightSeqNumber.
	 */
	public int getFlightSeqNumber() {
		return flightSeqNumber;
	}
	/**
	 * @param flightSeqNumber The flightSeqNumber to set.
	 */
	public void setFlightSeqNumber(int flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	/**
	 * @return Returns the gpaArlBlgStatus.
	 */
	public String getGpaArlBlgStatus() {
		return gpaArlBlgStatus;
	}
	/**
	 * @param gpaArlBlgStatus The gpaArlBlgStatus to set.
	 */
	public void setGpaArlBlgStatus(String gpaArlBlgStatus) {
		this.gpaArlBlgStatus = gpaArlBlgStatus;
	}
	/**
	 * @return Returns the paymentFlag.
	 */
	public String getPaymentFlag() {
		return paymentFlag;
	}
	/**
	 * @param paymentFlag The paymentFlag to set.
	 */
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	/**
	 * @return Returns the pieces.
	 */
	public int getPieces() {
		return pieces;
	}
	/**
	 * @param pieces The pieces to set.
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return Returns the proratedAmtBaseCurr.
	 */
	public double getProratedAmtBaseCurr() {
		return proratedAmtBaseCurr;
	}
	/**
	 * @param proratedAmtBaseCurr The proratedAmtBaseCurr to set.
	 */
	public void setProratedAmtBaseCurr(double proratedAmtBaseCurr) {
		this.proratedAmtBaseCurr = proratedAmtBaseCurr;
	}
	/**
	 * @return Returns the proratedAmtContractCurr.
	 */
	public double getProratedAmtContractCurr() {
		return proratedAmtContractCurr;
	}
	/**
	 * @param proratedAmtContractCurr The proratedAmtContractCurr to set.
	 */
	public void setProratedAmtContractCurr(double proratedAmtContractCurr) {
		this.proratedAmtContractCurr = proratedAmtContractCurr;
	}
	/**
	 * @return Returns the proratedAmtSplDrawRate.
	 */
	public double getProratedAmtSplDrawRate() {
		return proratedAmtSplDrawRate;
	}
	/**
	 * @param proratedAmtSplDrawRate The proratedAmtSplDrawRate to set.
	 */
	public void setProratedAmtSplDrawRate(double proratedAmtSplDrawRate) {
		this.proratedAmtSplDrawRate = proratedAmtSplDrawRate;
	}
	/**
	 * @return Returns the proratedAmtUSD.
	 */
	public double getProratedAmtUSD() {
		return proratedAmtUSD;
	}
	/**
	 * @param proratedAmtUSD The proratedAmtUSD to set.
	 */
	public void setProratedAmtUSD(double proratedAmtUSD) {
		this.proratedAmtUSD = proratedAmtUSD;
	}
	/**
	 * @return Returns the proratedValue.
	 */
	public double getProratedValue() {
		return proratedValue;
	}
	/**
	 * @param proratedValue The proratedValue to set.
	 */
	public void setProratedValue(double proratedValue) {
		this.proratedValue = proratedValue;
	}
	/**
	 * @return Returns the prorationPercentage.
	 */
	public double getProrationPercentage() {
		return prorationPercentage;
	}
	/**
	 * @param prorationPercentage The prorationPercentage to set.
	 */
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}
	/**
	 * @return Returns the prorationType.
	 */
	public String getProrationType() {
		return prorationType;
	}
	/**
	 * @param prorationType The prorationType to set.
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}
	
	/**
	 * @return Returns the route.
	 */
	public String getRoute() {
		return route;
	}
	/**
	 * @param route The route to set.
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return Returns the segFrom.
	 */
	public String getSegFrom() {
		return segFrom;
	}
	/**
	 * @param segFrom The segFrom to set.
	 */
	public void setSegFrom(String segFrom) {
		this.segFrom = segFrom;
	}
	/**
	 * @return Returns the segSerialNumber.
	 */
	public int getSegSerialNumber() {
		return segSerialNumber;
	}
	/**
	 * @param segSerialNumber The segSerialNumber to set.
	 */
	public void setSegSerialNumber(int segSerialNumber) {
		this.segSerialNumber = segSerialNumber;
	}
	/**
	 * @return Returns the segTo.
	 */
	public String getSegTo() {
		return segTo;
	}
	/**
	 * @param segTo The segTo to set.
	 */
	public void setSegTo(String segTo) {
		this.segTo = segTo;
	}
	/**
	 * @return Returns the updBillTo.
	 */
	public String getUpdBillTo() {
		return updBillTo;
	}
	/**
	 * @param updBillTo The updBillTo to set.
	 */
	public void setUpdBillTo(String updBillTo) {
		this.updBillTo = updBillTo;
	}
	/**
	 * @return Returns the updMailCtgCode.
	 */
	public String getUpdMailCtgCode() {
		return updMailCtgCode;
	}
	/**
	 * @param updMailCtgCode The updMailCtgCode to set.
	 */
	public void setUpdMailCtgCode(String updMailCtgCode) {
		this.updMailCtgCode = updMailCtgCode;
	}
	/**
	 * @return Returns the updMailSubClsCode.
	 */
	public String getUpdMailSubClsCode() {
		return updMailSubClsCode;
	}
	/**
	 * @param updMailSubClsCode The updMailSubClsCode to set.
	 */
	public void setUpdMailSubClsCode(String updMailSubClsCode) {
		this.updMailSubClsCode = updMailSubClsCode;
	}
	/**
	 * @return Returns the updWgtCharge.
	 */
	public double getUpdWgtCharge() {
		return updWgtCharge;
	}
	/**
	 * @param updWgtCharge The updWgtCharge to set.
	 */
	public void setUpdWgtCharge(double updWgtCharge) {
		this.updWgtCharge = updWgtCharge;
	}
	/**
	 * @return Returns the weight.
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return Returns the wgtCharge.
	 */
	public double getWgtCharge() {
		return wgtCharge;
	}
	/**
	 * @param wgtCharge The wgtCharge to set.
	 */
	public void setWgtCharge(double wgtCharge) {
		this.wgtCharge = wgtCharge;
	}
	/**
	 * @return Returns the billingBasis.
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
	 * @return Returns the csgDocumentNumber.
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber The csgDocumentNumber to set.
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return Returns the csgSeqNumber.
	 */
	public int getCsgSeqNumber() {
		return csgSeqNumber;
	}
	/**
	 * @param csgSeqNumber The csgSeqNumber to set.
	 */
	public void setCsgSeqNumber(int csgSeqNumber) {
		this.csgSeqNumber = csgSeqNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
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
	 * @return Returns the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/** 
	 * @return Returns the prorationFactor
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
	 * @return Returns the sectorStatus
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
	 * @return Returns the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return the serviceTax
	 */
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
	 * @return the tds
	 */
	public double getTds() {
		return tds;
	}
	/**
	 * @param tds the tds to set
	 */
	public void setTds(double tds) {
		this.tds = tds;
	}
	/**
	 * @return the consignmentOrigin
	 */
	public String getConsignmentOrigin() {
		return consignmentOrigin;
	}
	/**
	 * @param consignmentOrigin the consignmentOrigin to set
	 */
	public void setConsignmentOrigin(String consignmentOrigin) {
		this.consignmentOrigin = consignmentOrigin;
	}
	/**
	 * @return the consignmentDestination
	 */
	public String getConsignmentDestination() {
		return consignmentDestination;
	}
	/**
	 * @param consignmentDestination the consignmentDestination to set
	 */
	public void setConsignmentDestination(String consignmentDestination) {
		this.consignmentDestination = consignmentDestination;
	}
	/**
	 * @return the consignmentDate
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}
	/**
	 * @param consignmentDate the consignmentDate to set
	 */
	public void setConsignmentDate(LocalDate consignmentDate) {
		this.consignmentDate = consignmentDate;
	}
	/**
	 * @return the gpaCountryCode
	 */
	public String getGpaCountryCode() {
		return gpaCountryCode;
	}
	/**
	 * @param gpaCountryCode the gpaCountryCode to set
	 */
	public void setGpaCountryCode(String gpaCountryCode) {
		this.gpaCountryCode = gpaCountryCode;
	}
	/**
	 * @return the netAmount
	 */
	public Money getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 *  @param valCharges the valCharges to set
	 * 	Setter for valCharges 
	 *	Added by : A-5219 on 03-Apr-2014
	 * 	Used for :
	 */
	public void setValCharges(double valCharges) {
		this.valCharges = valCharges;
	}
	/**
	 * 	Getter for valCharges 
	 *	Added by : A-5219 on 03-Apr-2014
	 * 	Used for :
	 */
	public double getValCharges() {
		return valCharges;
	}
	/**
	 * @return the mailbagOrigin
	 */
	/**
	 * 	Getter for mailbagOrigin 
	 *	Added by : A-4809 on Jun 15, 2016
	 * 	Used for :ICRD-161909
	 */
	public String getMailbagOrigin() {
		return mailbagOrigin;
	}
	/**
	 *  @param mailbagOrigin the mailbagOrigin to set
	 * 	Setter for mailbagOrigin 
	 *	Added by : A-4809 on Jun 15, 2016
	 * 	Used for :ICRD-161909
	 */
	public void setMailbagOrigin(String mailbagOrigin) {
		this.mailbagOrigin = mailbagOrigin;
	}
	/**
	 * 	Getter for mailbagDestination 
	 *	Added by : A-4809 on Jun 15, 2016
	 * 	Used for :ICRD-161909
	 */
	public String getMailbagDestination() {
		return mailbagDestination;
	}
	/**
	 *  @param mailbagDestination the mailbagDestination to set
	 * 	Setter for mailbagDestination 
	 *	Added by : A-4809 on Jun 15, 2016
	 * 	Used for :ICRD-161909
	 */
	public void setMailbagDestination(String mailbagDestination) {
		this.mailbagDestination = mailbagDestination;
	}
	/**
	 * 	Getter for surCharge 
	 *	Added by : A-7871 on Oct 20, 2017
	 * 	Used for :ICRD-154005
	 */
	public double getSurCharge() {
		return surCharge;
	}
	/**
	 *  @param surCharge the surCharge to set
	 * 	Setter for surCharge 
	 *  Added by : A-7871 on Oct 20, 2017
	 * 	Used for :ICRD-154005
	 */
	public void setSurCharge(double surCharge) {
		this.surCharge = surCharge;
	}
	
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * 	Getter for recieveDate 
	 *	Added by : A-7531 on 14-May-2019
	 * 	Used for :
	 */
	public LocalDate getRecieveDate() {
		return recieveDate;
	}
	/**
	 *  @param recieveDate the recieveDate to set
	 * 	Setter for recieveDate 
	 *	Added by : A-7531 on 14-May-2019
	 * 	Used for :
	 */
	public void setRecieveDate(LocalDate recieveDate) {
		this.recieveDate = recieveDate;
	}
	/**
	 * 	Getter for seqNumInt 
	 *	Added by : A-7531 on 13-Jun-2019
	 * 	Used for :
	 */
	public int getSeqNumInt() {
		return seqNumInt;
	}
	/**
	 *  @param seqNumInt the seqNumInt to set
	 * 	Setter for seqNumInt 
	 *	Added by : A-7531 on 13-Jun-2019
	 * 	Used for :
	 */
	public void setSeqNumInt(int seqNumInt) {
		this.seqNumInt = seqNumInt;
	}
	
	private String orgCountryCode;
	public String getOrgCountryCode() {
		return orgCountryCode;
	}
	public void setOrgCountryCode(String orgCountryCode) {
		this.orgCountryCode = orgCountryCode;
	}
	
}
