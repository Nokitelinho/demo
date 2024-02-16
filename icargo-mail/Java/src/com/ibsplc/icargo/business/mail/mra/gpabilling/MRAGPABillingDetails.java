/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7794
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Dec 01,2017    Remya I R 		        Initial draft
 *
 */



@Entity
@Table(name = "MALMRAGPABLGDTL")

public class MRAGPABillingDetails {
	
	private static final String CLASS_NAME = "MRAGPABillingDetails";	
	 private static final Log log = LogFactory.getLogger("MRA BILLING");

	private MRAGPABillingDetailsPK mraGPABillingDetailsPK;
	private String sectorFrom;
	private String sectorToo;
	private Calendar flightDate;
	private int flightCarrierIdentifier;
	private String flightNumber;
	private String flightCarrierCode;
	private int flightSeqNumber;
	private int segmentSerialNumber;
	private double appliedRate;
	private double grossWeightt;
	private double updatedGrossWeight;
	private String billToPOA;
	private String updatedBillTooPOA;
	private double wieghtChargeInContractCurrency;
	private double weightChargeInUSD;
	private double weightChargeInXDR;
	private double weightChargeInBaseCurrency;
	private double updatedWtChargeInContractCurrency;
	private double updatedWeightChargeInUSD;
	private double updatedWeightChargeInXDR;
	private double updatedWeightChargeInBaseCurrency;
	private String billingStatus;
	private String contractCurrencyCode;
	private String remarks;
	private double dueAmountToPostalAuthority;
	private String accountingTrxnIdr;
	private double fuelSurcharge;
	private String rateIndicatorFuelSurcharge;
	private double serviceTax;
	private double taxDeductedAtSource;
	private double netAmount;
	private String rateInclusiveServiceTaxFlag;
	private String unitCode;
	private double valuationChrageInContractCurr;
	private String rateLineSeqNumber;
	private String billingMatrix;
	private double otherChargeInContractCurrency;
	private double surChargeInBaseCurrency;
	private double surChargeInUSD;
	private double surChargeInXDR;
	private double updatedOtherChargeInContractCurrency;
	private double updatedSurChargeInBaseCurrency;
	private double updatedSurChargeInUSD;
	private double updatedSurChargeInXDR;
	private String rateType;
	private String lastUpdatedUSer;
	private Calendar lastUpdatedTime;
	private String updatedContractCurrency;
	private String incrmks; //Added by A-8484 for ICRD-232401 
	
	
	/**
	 * @return the sectorFrom
	 */
	@Column(name="SECFRM")
	public String getSectorFrom() {
		return sectorFrom;
	}
	/**
	 * @param sectorFrom the sectorFrom to set
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}
	/**
	 * @return the sectorToo
	 */
	@Column(name="SECTOO")
	public String getSectorToo() {
		return sectorToo;
	}
	/**
	 * @param sectorToo the sectorToo to set
	 */
	public void setSectorToo(String sectorToo) {
		this.sectorToo = sectorToo;
	}
	/**
	 * @return the flightDate
	 */
	@Column(name="FLTDAT")
	public Calendar getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the flightCarrierIdentifier
	 */
	@Column(name="FLTCARIDR")
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier the flightCarrierIdentifier to set
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	/**
	 * @return the flightNumber
	 */
	@Column(name="FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the flightCarrierCode
	 */
	@Column(name="FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @return the flightSeqNumber
	 */
	@Column(name="FLTSEQNUM")
	public int getFlightSeqNumber() {
		return flightSeqNumber;
	}
	/**
	 * @param flightSeqNumber the flightSeqNumber to set
	 */
	public void setFlightSeqNumber(int flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	/**
	 * @return the segmentSerialNumber
	 */
	@Column(name="SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber the segmentSerialNumber to set
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	/**
	 * @return the appliedRate
	 */
	@Column(name="APLRAT")
	public double getAppliedRate() {
		return appliedRate;
	}
	/**
	 * @param appliedRate the appliedRate to set
	 */
	public void setAppliedRate(double appliedRate) {
		this.appliedRate = appliedRate;
	}
	/**
	 * @return the grossWeightt
	 */
	@Column(name="GRSWGT")
	public double getGrossWeightt() {
		return grossWeightt;
	}
	/**
	 * @param grossWeightt the grossWeightt to set
	 */
	public void setGrossWeightt(double grossWeightt) {
		this.grossWeightt = grossWeightt;
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
	 * @return the billToPOA
	 */
	@Column(name="BILTOOPOA")
	public String getBillToPOA() {
		return billToPOA;
	}
	/**
	 * @param billToPOA the billToPOA to set
	 */
	public void setBillToPOA(String billToPOA) {
		this.billToPOA = billToPOA;
	}
	/**
	 * @return the updatedBillTooPOA
	 */
	@Column(name="UPDBILTOOPOA")
	public String getUpdatedBillTooPOA() {
		return updatedBillTooPOA;
	}
	/**
	 * @param updatedBillTooPOA the updatedBillTooPOA to set
	 */
	public void setUpdatedBillTooPOA(String updatedBillTooPOA) {
		this.updatedBillTooPOA = updatedBillTooPOA;
	}
	/**
	 * @return the wieghtChargeInContractCurrency
	 */
	@Column(name="WGTCHGCTR")
	public double getWieghtChargeInContractCurrency() {
		return wieghtChargeInContractCurrency;
	}
	/**
	 * @param wieghtChargeInContractCurrency the wieghtChargeInContractCurrency to set
	 */
	public void setWieghtChargeInContractCurrency(
			double wieghtChargeInContractCurrency) {
		this.wieghtChargeInContractCurrency = wieghtChargeInContractCurrency;
	}
	/**
	 * @return the weightChargeInUSD
	 */
	@Column(name="WGTCHGUSD")
	public double getWeightChargeInUSD() {
		return weightChargeInUSD;
	}
	/**
	 * @param weightChargeInUSD the weightChargeInUSD to set
	 */
	public void setWeightChargeInUSD(double weightChargeInUSD) {
		this.weightChargeInUSD = weightChargeInUSD;
	}
	/**
	 * @return the weightChargeInXDR
	 */
	@Column(name="WGTCHGXDR")
	public double getWeightChargeInXDR() {
		return weightChargeInXDR;
	}
	/**
	 * @param weightChargeInXDR the weightChargeInXDR to set
	 */
	public void setWeightChargeInXDR(double weightChargeInXDR) {
		this.weightChargeInXDR = weightChargeInXDR;
	}
	/**
	 * @return the weightChargeInBaseCurrency
	 */
	@Column(name="WGTCHGBAS")
	public double getWeightChargeInBaseCurrency() {
		return weightChargeInBaseCurrency;
	}
	/**
	 * @param weightChargeInBaseCurrency the weightChargeInBaseCurrency to set
	 */
	public void setWeightChargeInBaseCurrency(double weightChargeInBaseCurrency) {
		this.weightChargeInBaseCurrency = weightChargeInBaseCurrency;
	}
	/**
	 * @return the updatedWtChargeInContractCurrency
	 */
	@Column(name="UPDWGTCHGCTR")
	public double getUpdatedWtChargeInContractCurrency() {
		return updatedWtChargeInContractCurrency;
	}
	/**
	 * @param updatedWtChargeInContractCurrency the updatedWtChargeInContractCurrency to set
	 */
	public void setUpdatedWtChargeInContractCurrency(
			double updatedWtChargeInContractCurrency) {
		this.updatedWtChargeInContractCurrency = updatedWtChargeInContractCurrency;
	}
	/**
	 * @return the updatedWeightChargeInUSD
	 */
	@Column(name="UPDWGTCHGUSD")
	public double getUpdatedWeightChargeInUSD() {
		return updatedWeightChargeInUSD;
	}
	/**
	 * @param updatedWeightChargeInUSD the updatedWeightChargeInUSD to set
	 */
	public void setUpdatedWeightChargeInUSD(double updatedWeightChargeInUSD) {
		this.updatedWeightChargeInUSD = updatedWeightChargeInUSD;
	}
	/**
	 * @return the updatedWeightChargeInXDR
	 */
	@Column(name="UPDWGTCHGXDR")
	public double getUpdatedWeightChargeInXDR() {
		return updatedWeightChargeInXDR;
	}
	/**
	 * @param updatedWeightChargeInXDR the updatedWeightChargeInXDR to set
	 */
	public void setUpdatedWeightChargeInXDR(double updatedWeightChargeInXDR) {
		this.updatedWeightChargeInXDR = updatedWeightChargeInXDR;
	}
	/**
	 * @return the updatedWeightChargeInBaseCurrency
	 */
	@Column(name="UPDWGTCHGBAS")
	public double getUpdatedWeightChargeInBaseCurrency() {
		return updatedWeightChargeInBaseCurrency;
	}
	/**
	 * @param updatedWeightChargeInBaseCurrency the updatedWeightChargeInBaseCurrency to set
	 */
	public void setUpdatedWeightChargeInBaseCurrency(
			double updatedWeightChargeInBaseCurrency) {
		this.updatedWeightChargeInBaseCurrency = updatedWeightChargeInBaseCurrency;
	}
	/**
	 * @return the billingStatus
	 */
	@Column(name="BLGSTA")
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
	 * @return the contractCurrencyCode
	 */
	@Column(name="CTRCURCOD")
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}
	/**
	 * @param contractCurrencyCode the contractCurrencyCode to set
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}
	/**
	 * @return the remarks
	 */
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the dueAmountToPostalAuthority
	 */
	
	/**
	 * @return the accountingTrxnIdr
	 */
	@Column(name="ACCTXNIDR")
	public String getAccountingTrxnIdr() {
		return accountingTrxnIdr;
	}
	/**
	 * @param accountingTrxnIdr the accountingTrxnIdr to set
	 */
	public void setAccountingTrxnIdr(String accountingTrxnIdr) {
		this.accountingTrxnIdr = accountingTrxnIdr;
	}
	/**
	 * @return the fuelSurcharge
	 */
	@Column(name="FULCHG")
	public double getFuelSurcharge() {
		return fuelSurcharge;
	}
	/**
	 * @param fuelSurcharge the fuelSurcharge to set
	 */
	public void setFuelSurcharge(double fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	/**
	 * @return the rateIndicatorFuelSurcharge
	 */
	@Column(name="RATIND")
	public String getRateIndicatorFuelSurcharge() {
		return rateIndicatorFuelSurcharge;
	}
	/**
	 * @param rateIndicatorFuelSurcharge the rateIndicatorFuelSurcharge to set
	 */
	public void setRateIndicatorFuelSurcharge(String rateIndicatorFuelSurcharge) {
		this.rateIndicatorFuelSurcharge = rateIndicatorFuelSurcharge;
	}
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
	 * @return the taxDeductedAtSource
	 */
	@Column(name="TDS")
	public double getTaxDeductedAtSource() {
		return taxDeductedAtSource;
	}
	/**
	 * @param taxDeductedAtSource the taxDeductedAtSource to set
	 */
	public void setTaxDeductedAtSource(double taxDeductedAtSource) {
		this.taxDeductedAtSource = taxDeductedAtSource;
	}
	/**
	 * @return the netAmount
	 */
	@Column(name="NETAMT")
	public double getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @return the rateInclusiveServiceTaxFlag
	 */
	@Column(name="RATINCSRVTAXFLG")
	public String getRateInclusiveServiceTaxFlag() {
		return rateInclusiveServiceTaxFlag;
	}
	/**
	 * @param rateInclusiveServiceTaxFlag the rateInclusiveServiceTaxFlag to set
	 */
	public void setRateInclusiveServiceTaxFlag(String rateInclusiveServiceTaxFlag) {
		this.rateInclusiveServiceTaxFlag = rateInclusiveServiceTaxFlag;
	}
	/**
	 * @return the unitCode
	 */
	@Column(name="UNTCOD")
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
	 * @return the valuationChrageInContractCurr
	 */
	@Column(name="VALCHGCTR")
	public double getValuationChrageInContractCurr() {
		return valuationChrageInContractCurr;
	}
	/**
	 * @param valuationChrageInContractCurr the valuationChrageInContractCurr to set
	 */
	public void setValuationChrageInContractCurr(
			double valuationChrageInContractCurr) {
		this.valuationChrageInContractCurr = valuationChrageInContractCurr;
	}
	/**
	 * @return the rateLineSeqNumber
	 */
	@Column(name="RATLINIDR")
	public String getRateLineSeqNumber() {
		return rateLineSeqNumber;
	}
	/**
	 * @param rateLineSeqNumber the rateLineSeqNumber to set
	 */
	public void setRateLineSeqNumber(String rateLineSeqNumber) {
		this.rateLineSeqNumber = rateLineSeqNumber;
	}
	/**
	 * @return the billingMatrix
	 */
	@Column(name="BLGMTXCOD")
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
	 * @return the otherChargeInContractCurrency
	 */
	@Column(name="OTHCHGCTR")
	public double getOtherChargeInContractCurrency() {
		return otherChargeInContractCurrency;
	}
	/**
	 * @param otherChargeInContractCurrency the otherChargeInContractCurrency to set
	 */
	public void setOtherChargeInContractCurrency(
			double otherChargeInContractCurrency) {
		this.otherChargeInContractCurrency = otherChargeInContractCurrency;
	}
	/**
	 * @return the surChargeInBaseCurrency
	 */
	@Column(name="OTHCHGBAS")
	public double getSurChargeInBaseCurrency() {
		return surChargeInBaseCurrency;
	}
	/**
	 * @param surChargeInBaseCurrency the surChargeInBaseCurrency to set
	 */
	public void setSurChargeInBaseCurrency(double surChargeInBaseCurrency) {
		this.surChargeInBaseCurrency = surChargeInBaseCurrency;
	}
	/**
	 * @return the surChargeInUSD
	 */
	@Column(name="OTHCHGUSD")
	public double getSurChargeInUSD() {
		return surChargeInUSD;
	}
	/**
	 * @param surChargeInUSD the surChargeInUSD to set
	 */
	public void setSurChargeInUSD(double surChargeInUSD) {
		this.surChargeInUSD = surChargeInUSD;
	}
	/**
	 * @return the surChargeInXDR
	 */
	@Column(name="OTHCHGXDR")
	public double getSurChargeInXDR() {
		return surChargeInXDR;
	}
	/**
	 * @param surChargeInXDR the surChargeInXDR to set
	 */
	public void setSurChargeInXDR(double surChargeInXDR) {
		this.surChargeInXDR = surChargeInXDR;
	}
	/**
	 * @return the updatedOtherChargeInContractCurrency
	 */
	@Column(name="UPDOTHCHGCTR")
	public double getUpdatedOtherChargeInContractCurrency() {
		return updatedOtherChargeInContractCurrency;
	}
	/**
	 * @param updatedOtherChargeInContractCurrency the updatedOtherChargeInContractCurrency to set
	 */
	public void setUpdatedOtherChargeInContractCurrency(
			double updatedOtherChargeInContractCurrency) {
		this.updatedOtherChargeInContractCurrency = updatedOtherChargeInContractCurrency;
	}
	/**
	 * @return the updatedSurChargeInBaseCurrency
	 */
	@Column(name="UPDOTHCHGBAS")
	public double getUpdatedSurChargeInBaseCurrency() {
		return updatedSurChargeInBaseCurrency;
	}
	/**
	 * @param updatedSurChargeInBaseCurrency the updatedSurChargeInBaseCurrency to set
	 */
	public void setUpdatedSurChargeInBaseCurrency(
			double updatedSurChargeInBaseCurrency) {
		this.updatedSurChargeInBaseCurrency = updatedSurChargeInBaseCurrency;
	}
	/**
	 * @return the updatedSurChargeInUSD
	 */
	@Column(name="UPDOTHCHGUSD")
	public double getUpdatedSurChargeInUSD() {
		return updatedSurChargeInUSD;
	}
	/**
	 * @param updatedSurChargeInUSD the updatedSurChargeInUSD to set
	 */
	public void setUpdatedSurChargeInUSD(double updatedSurChargeInUSD) {
		this.updatedSurChargeInUSD = updatedSurChargeInUSD;
	}
	/**
	 * @return the updatedSurChargeInXDR
	 */
	@Column(name="UPDOTHCHGXDR")
	public double getUpdatedSurChargeInXDR() {
		return updatedSurChargeInXDR;
	}
	/**
	 * @param updatedSurChargeInXDR the updatedSurChargeInXDR to set
	 */
	public void setUpdatedSurChargeInXDR(double updatedSurChargeInXDR) {
		this.updatedSurChargeInXDR = updatedSurChargeInXDR;
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
	 * @return the lastUpdatedUSer
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUSer() {
		return lastUpdatedUSer;
	}
	/**
	 * @param lastUpdatedUSer the lastUpdatedUSer to set
	 */
	public void setLastUpdatedUSer(String lastUpdatedUSer) {
		this.lastUpdatedUSer = lastUpdatedUSer;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Version
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
	public MRAGPABillingDetailsPK getMraGPABillingDetailsPK() {
		return mraGPABillingDetailsPK;
	}
	/**
	 * @param mraGPABillingDetailsPK the mraGPABillingDetailsPK to set
	 */
	public void setMraGPABillingDetailsPK(
			MRAGPABillingDetailsPK mraGPABillingDetailsPK) {
		this.mraGPABillingDetailsPK = mraGPABillingDetailsPK;
	}
	/**
	 * @return the updatedContractCurrency
	 */
	@Column(name="UPDCTRCURCOD")
	public String getUpdatedContractCurrency() {
		return updatedContractCurrency;
	}
	/**
	 * @param updatedContractCurrency the updatedContractCurrency to set
	 */
	public void setUpdatedContractCurrency(String updatedContractCurrency) {
		this.updatedContractCurrency = updatedContractCurrency;
	}
	@Column(name="DUEPOA")
	public double getDueAmountToPostalAuthority() {
		return dueAmountToPostalAuthority;
	}
	public void setDueAmountToPostalAuthority(double dueAmountToPostalAuthority) {
		this.dueAmountToPostalAuthority = dueAmountToPostalAuthority;
	}
	
	 //Added by A-8484 for ICRD-232401 
	@Column(name="INCRMK")
	public String getIncrmks() {
		return incrmks;
	}
	
	public void setIncrmks(String incrmks) {
		this.incrmks = incrmks;
	}
	
	public static MRAGPABillingDetails find(String companyCode,long mailSeqNumber, int serialNumber)throws SystemException,FinderException{
		
		log.entering(CLASS_NAME,"find");
		MRAGPABillingDetailsPK mraGPABillingDetailsPK = 
				new MRAGPABillingDetailsPK( companyCode,mailSeqNumber,serialNumber);
		MRAGPABillingDetails mraGPABillingDetails=null;
		try {
			mraGPABillingDetails=PersistenceController.getEntityManager().find(
					MRAGPABillingDetails.class,mraGPABillingDetailsPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		log.exiting(CLASS_NAME,"find");
		return mraGPABillingDetails;
		
	}
	

	
}
