/* RejectionMemo.java Created on May18, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

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

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 */
@Entity
@Table(name = "MALMRAARLMEM")

public class RejectionMemo {
	private RejectionMemoPK rejectionMemoPK;
	
	private String airlineCode;
	
	private String inwardInvoiceNumber;
	
	private Calendar inwardInvoiceDate;
	
	private String inwardClearancePeriod;
	
	private String outwardInvoiceNumber;
	
	private Calendar outwardInvoiceDate;
	
	private String outwardClearancePeriod;
	
	private String billingCurrencyCode;
	
	private String contractCurrencyCode;
	
	private String clearanceCurrencyCode;
	
	private Double contractBilledAmount;
	
	private Double contractAcceptedAmount;
	
	private Double contractRejectedAmount;
	
	private Double clearanceBilledAmount;
	
	private Double clearanceAcceptedAmount;
	
	private Double clearanceRejectedAmount;
	
	private Double billingBilledAmount;
	
	private Double billingAcceptedAmount;
	
	private Double billingRejectedAmount;
	
	private Double contractBillingExchangeRate;
	
	private Double billingClearanceExchangeRate;
	
	private String requestAuthorisationIndicator;
	
	private String requestAuthorisationReference;
	
	private Calendar requestAuthorisationDate;
	
	private String duplicateBillingIndicator;
	
	private String duplicateBillingInvoiceNumber;
	
	private Calendar duplicateBillingInvoiceDate;
	
	private String chrgNotCoveredByConIndicator;
	
	private String outTimeLimitsForBillingIndicator;
	
	private String chrgNotConvertedToConIndicator;
	
	private String noApprovalIndicator;
	
	private String noReceiptIndicator;
	
	private String incorrectExchangeRateIndicator;
	
	private String otherIndicator;
	
	private String memoStatus;
	
	private String remarks;
	
	private String acctTxnIdr;
	
	private String lastUpdatedUser;
	
	private Calendar lastUpdatedTime;
	
	private Double provisionalAmt;
	
//	Added By Deepthi
	/*private String billingBasis;
	
	private String consignmentDocumentNumber;
	
	private int consignmentSequenceNumber;
	
	private String poaCode;*/
	
	private String dsn;
	
	private Calendar rejectedDate;
	
	private String revFlag;

	//Added by A-7929 as part of ICRD-265471
	private long mailSeqNumber;

	
	private String  attachmentIndicator;//Added for ICRD-265471 File attachment Attachment 
	
	
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING REJECTIONMEMO");
	 /*
     * Module name
     */
	private static final String MODULE_NAME="mail.mra.airlinebilling";
	 public RejectionMemo() {

	    } 
	/**
	 * @return Returns the exceptionInInvoicePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
		@AttributeOverride(name = "memoCode", column = @Column(name = "MEMCOD"))
		 })
	public RejectionMemoPK getRejectionMemoPK() {
		return rejectionMemoPK;
	}

	/**
	 * @param rejectionMemoPK The rejectionMemoPK to set.
	 */
	public void setRejectionMemoPK(RejectionMemoPK rejectionMemoPK) {
		this.rejectionMemoPK = rejectionMemoPK;
	}
	
	/**
	 * @return the lastUpdatedTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")
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


	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
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
    * Constructor
    * @author A-2391
    * @param rejectionMemoVO
    * @throws SystemException
    */
    public RejectionMemo(RejectionMemoVO rejectionMemoVO)
    throws SystemException{
    	
    	RejectionMemoPK memoPK
    						=new RejectionMemoPK(rejectionMemoVO.getCompanycode(),
    								rejectionMemoVO.getAirlineIdentifier(),rejectionMemoVO.getMemoCode());
    	this.setRejectionMemoPK(memoPK);
    	populateAllAttributes(rejectionMemoVO);


    	try{
    	PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException e){
    		throw new SystemException(e.getErrorCode());
    	}
    }
	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "TOOARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	
	/**
	 * @return Returns the billingAcceptedAmount.
	 */
	@Column(name = "BLGAPTAMT")
	public Double getBillingAcceptedAmount() {
		return billingAcceptedAmount;
	}
	/**
	 * @param billingAcceptedAmount The billingAcceptedAmount to set.
	 */
	public void setBillingAcceptedAmount(Double billingAcceptedAmount) {
		this.billingAcceptedAmount = billingAcceptedAmount;
	}
	/**
	 * @return Returns the billingBilledAmount.
	 */
	@Column(name = "BLGBLDAMT")
	public Double getBillingBilledAmount() {
		return billingBilledAmount;
	}
	/**
	 * @param billingBilledAmount The billingBilledAmount to set.
	 */
	public void setBillingBilledAmount(Double billingBilledAmount) {
		this.billingBilledAmount = billingBilledAmount;
	}
	/**
	 * @return Returns the billingClearanceExchangeRate.
	 */
	@Column(name = "BLGCLREXGRAT")
	public Double getBillingClearanceExchangeRate() {
		return billingClearanceExchangeRate;
	}
	/**
	 * @param billingClearanceExchangeRate The billingClearanceExchangeRate to set.
	 */
	public void setBillingClearanceExchangeRate(Double billingClearanceExchangeRate) {
		this.billingClearanceExchangeRate = billingClearanceExchangeRate;
	}
	/**
	 * @return Returns the billingCurrencyCode.
	 */
	@Column(name = "BLGCURCOD")
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}
	/**
	 * @param billingCurrencyCode The billingCurrencyCode to set.
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}
	/**
	 * @return Returns the billingRejectedAmount.
	 */
	@Column(name = "BLGREJAMT")
	public Double getBillingRejectedAmount() {
		return billingRejectedAmount;
	}
	/**
	 * @param billingRejectedAmount The billingRejectedAmount to set.
	 */
	public void setBillingRejectedAmount(Double billingRejectedAmount) {
		this.billingRejectedAmount = billingRejectedAmount;
	}
	/**
	 * @return Returns the chrgNotConvertedToConIndicator.
	 */
	@Column(name = "CHGNOTCONCRTIND")
	public String getChrgNotConvertedToConIndicator() {
		return chrgNotConvertedToConIndicator;
	}
	/**
	 * @param chrgNotConvertedToConIndicator The chrgNotConvertedToConIndicator to set.
	 */
	public void setChrgNotConvertedToConIndicator(
			String chrgNotConvertedToConIndicator) {
		this.chrgNotConvertedToConIndicator = chrgNotConvertedToConIndicator;
	}
	/**
	 * @return Returns the chrgNotCoveredByConIndicator.
	 */
	@Column(name = "CHGNOTCRTIND")
	public String getChrgNotCoveredByConIndicator() {
		return chrgNotCoveredByConIndicator;
	}
	/**
	 * @param chrgNotCoveredByConIndicator The chrgNotCoveredByConIndicator to set.
	 */
	public void setChrgNotCoveredByConIndicator(
			String chrgNotCoveredByConIndicator) {
		this.chrgNotCoveredByConIndicator = chrgNotCoveredByConIndicator;
	}
	/**
	 * @return Returns the clearanceAcceptedAmount.
	 */
	@Column(name = "CLRAPTAMT")
	public Double getClearanceAcceptedAmount() {
		return clearanceAcceptedAmount;
	}
	/**
	 * @param clearanceAcceptedAmount The clearanceAcceptedAmount to set.
	 */
	public void setClearanceAcceptedAmount(Double clearanceAcceptedAmount) {
		this.clearanceAcceptedAmount = clearanceAcceptedAmount;
	}
	/**
	 * @return Returns the clearanceBilledAmount.
	 */
	@Column(name = "CLRBLDAMT")
	public Double getClearanceBilledAmount() {
		return clearanceBilledAmount;
	}
	/**
	 * @param clearanceBilledAmount The clearanceBilledAmount to set.
	 */
	public void setClearanceBilledAmount(Double clearanceBilledAmount) {
		this.clearanceBilledAmount = clearanceBilledAmount;
	}
	/**
	 * @return Returns the clearanceCurrencyCode.
	 */
	@Column(name = "CLRCURCOD")
	public String getClearanceCurrencyCode() {
		return clearanceCurrencyCode;
	}
	/**
	 * @param clearanceCurrencyCode The clearanceCurrencyCode to set.
	 */
	public void setClearanceCurrencyCode(String clearanceCurrencyCode) {
		this.clearanceCurrencyCode = clearanceCurrencyCode;
	}
	/**
	 * @return Returns the clearanceRejectedAmount.
	 */
	@Column(name = "CLRREJAMT")
	public Double getClearanceRejectedAmount() {
		return clearanceRejectedAmount;
	}
	/**
	 * @param clearanceRejectedAmount The clearanceRejectedAmount to set.
	 */
	public void setClearanceRejectedAmount(Double clearanceRejectedAmount) {
		this.clearanceRejectedAmount = clearanceRejectedAmount;
	}
	
	/**
	 * @return Returns the contractAcceptedAmount.
	 */
	@Column(name = "CRTAPTAMT")
	public Double getContractAcceptedAmount() {
		return contractAcceptedAmount;
	}
	/**
	 * @param contractAcceptedAmount The contractAcceptedAmount to set.
	 */
	public void setContractAcceptedAmount(Double contractAcceptedAmount) {
		this.contractAcceptedAmount = contractAcceptedAmount;
	}
	/**
	 * @return Returns the contractBilledAmount.
	 */
	@Column(name = "CRTBLDAMT")
	public Double getContractBilledAmount() {
		return contractBilledAmount;
	}
	/**
	 * @param contractBilledAmount The contractBilledAmount to set.
	 */
	public void setContractBilledAmount(Double contractBilledAmount) {
		this.contractBilledAmount = contractBilledAmount;
	}
	/**
	 * @return Returns the contractBillingExchangeRate.
	 */
	@Column(name = "CRTBLGEXGRAT")
	public Double getContractBillingExchangeRate() {
		return contractBillingExchangeRate;
	}
	/**
	 * @param contractBillingExchangeRate The contractBillingExchangeRate to set.
	 */
	public void setContractBillingExchangeRate(Double contractBillingExchangeRate) {
		this.contractBillingExchangeRate = contractBillingExchangeRate;
	}
	/**
	 * @return Returns the contractRejectedAmount.
	 */
	@Column(name = "CRTREJAMT")
	public Double getContractRejectedAmount() {
		return contractRejectedAmount;
	}
	/**
	 * @param contractRejectedAmount The contractRejectedAmount to set.
	 */
	public void setContractRejectedAmount(Double contractRejectedAmount) {
		this.contractRejectedAmount = contractRejectedAmount;
	}
	/**
	 * @return Returns the duplicateBillingIndicator.
	 */
	@Column(name = "DUPBILIND")
	public String getDuplicateBillingIndicator() {
		return duplicateBillingIndicator;
	}
	/**
	 * @param duplicateBillingIndicator The duplicateBillingIndicator to set.
	 */
	public void setDuplicateBillingIndicator(String duplicateBillingIndicator) {
		this.duplicateBillingIndicator = duplicateBillingIndicator;
	}
	/**
	 * @return Returns the duplicateBillingInvoiceDate.
	 */
	@Column(name = "DUPBILINVDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getDuplicateBillingInvoiceDate() {
		return duplicateBillingInvoiceDate;
	}
	/**
	 * @param duplicateBillingInvoiceDate The duplicateBillingInvoiceDate to set.
	 */
	public void setDuplicateBillingInvoiceDate(Calendar duplicateBillingInvoiceDate) {
		this.duplicateBillingInvoiceDate = duplicateBillingInvoiceDate;
	}
	/**
	 * @return Returns the duplicateBillingInvoiceNumber.
	 */
	@Column(name = "DUPBILINVNUM")
	public String getDuplicateBillingInvoiceNumber() {
		return duplicateBillingInvoiceNumber;
	}
	/**
	 * @param duplicateBillingInvoiceNumber The duplicateBillingInvoiceNumber to set.
	 */
	public void setDuplicateBillingInvoiceNumber(
			String duplicateBillingInvoiceNumber) {
		this.duplicateBillingInvoiceNumber = duplicateBillingInvoiceNumber;
	}
	/**
	 * @return Returns the incorrectExchangeRateIndicator.
	 */
	@Column(name = "INCORTEXGIND")
	public String getIncorrectExchangeRateIndicator() {
		return incorrectExchangeRateIndicator;
	}
	/**
	 * @param incorrectExchangeRateIndicator The incorrectExchangeRateIndicator to set.
	 */
	public void setIncorrectExchangeRateIndicator(
			String incorrectExchangeRateIndicator) {
		this.incorrectExchangeRateIndicator = incorrectExchangeRateIndicator;
	}
	/**
	 * @return Returns the inwardClearancePeriod.
	 */
	@Column(name = "INWCLRPRD")
	public String getInwardClearancePeriod() {
		return inwardClearancePeriod;
	}
	/**
	 * @param inwardClearancePeriod The inwardClearancePeriod to set.
	 */
	public void setInwardClearancePeriod(String inwardClearancePeriod) {
		this.inwardClearancePeriod = inwardClearancePeriod;
	}
	/**
	 * @return Returns the inwardInvoiceDate.
	 */
	@Column(name = "INWINVDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getInwardInvoiceDate() {
		return inwardInvoiceDate;
	}
	/**
	 * @param inwardInvoiceDate The inwardInvoiceDate to set.
	 */
	public void setInwardInvoiceDate(Calendar inwardInvoiceDate) {
		this.inwardInvoiceDate = inwardInvoiceDate;
	}
	/**
	 * @return Returns the inwardInvoiceNumber.
	 */
	@Column(name = "INWINVNUM")
	@Audit(name = "inwardInvoiceNumber")
	public String getInwardInvoiceNumber() {
		return inwardInvoiceNumber;
	}
	/**
	 * @param inwardInvoiceNumber The inwardInvoiceNumber to set.
	 */
	public void setInwardInvoiceNumber(String inwardInvoiceNumber) {
		this.inwardInvoiceNumber = inwardInvoiceNumber;
	}
	
	/**
	 * @return Returns the memoStatus.
	 */
	@Column(name = "MEMSTA")
	public String getMemoStatus() {
		return memoStatus;
	}
	/**
	 * @param memoStatus The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}
	/**
	 * @return Returns the noApprovalIndicator.
	 */
	@Column(name = "NOAPRVIND")
	public String getNoApprovalIndicator() {
		return noApprovalIndicator;
	}
	/**
	 * @param noApprovalIndicator The noApprovalIndicator to set.
	 */
	public void setNoApprovalIndicator(String noApprovalIndicator) {
		this.noApprovalIndicator = noApprovalIndicator;
	}
	/**
	 * @return Returns the noReceiptIndicator.
	 */
	@Column(name = "NORCPTIND")
	public String getNoReceiptIndicator() {
		return noReceiptIndicator;
	}
	/**
	 * @param noReceiptIndicator The noReceiptIndicator to set.
	 */
	public void setNoReceiptIndicator(String noReceiptIndicator) {
		this.noReceiptIndicator = noReceiptIndicator;
	}
	/**
	 * @return Returns the otherIndicator.
	 */
	@Column(name = "OTHIND")
	public String getOtherIndicator() {
		return otherIndicator;
	}
	/**
	 * @param otherIndicator The otherIndicator to set.
	 */
	public void setOtherIndicator(String otherIndicator) {
		this.otherIndicator = otherIndicator;
	}
	/**
	 * @return Returns the outTimeLimitsForBillingIndicator.
	 */
	@Column(name = "OUTTIMLMTIND")
	public String getOutTimeLimitsForBillingIndicator() {
		return outTimeLimitsForBillingIndicator;
	}
	/**
	 * @param outTimeLimitsForBillingIndicator The outTimeLimitsForBillingIndicator to set.
	 */
	public void setOutTimeLimitsForBillingIndicator(
			String outTimeLimitsForBillingIndicator) {
		this.outTimeLimitsForBillingIndicator = outTimeLimitsForBillingIndicator;
	}
	/**
	 * @return Returns the outwardClearancePeriod.
	 */
	@Column(name = "OUTCLRPRD")
	public String getOutwardClearancePeriod() {
		return outwardClearancePeriod;
	}
	/**
	 * @param outwardClearancePeriod The outwardClearancePeriod to set.
	 */
	public void setOutwardClearancePeriod(String outwardClearancePeriod) {
		this.outwardClearancePeriod = outwardClearancePeriod;
	}
	/**
	 * @return Returns the outwardInvoiceDate.
	 */
	@Column(name = "OUTINVDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getOutwardInvoiceDate() {
		return outwardInvoiceDate;
	}
	/**
	 * @param outwardInvoiceDate The outwardInvoiceDate to set.
	 */
	public void setOutwardInvoiceDate(Calendar outwardInvoiceDate) {
		this.outwardInvoiceDate = outwardInvoiceDate;
	}
	/**
	 * @return Returns the outwardInvoiceNumber.
	 */
	@Column(name = "OUTINVNUM")
	public String getOutwardInvoiceNumber() {
		return outwardInvoiceNumber;
	}
	/**
	 * @param outwardInvoiceNumber The outwardInvoiceNumber to set.
	 */
	public void setOutwardInvoiceNumber(String outwardInvoiceNumber) {
		this.outwardInvoiceNumber = outwardInvoiceNumber;
	}
	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the requestAuthorisationDate.
	 */
	@Column(name = "REQAUTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getRequestAuthorisationDate() {
		return requestAuthorisationDate;
	}
	/**
	 * @param requestAuthorisationDate The requestAuthorisationDate to set.
	 */
	public void setRequestAuthorisationDate(Calendar requestAuthorisationDate) {
		this.requestAuthorisationDate = requestAuthorisationDate;
	}
	/**
	 * @return Returns the requestAuthorisationIndicator.
	 */
	@Column(name = "REQAUTIND")
	public String getRequestAuthorisationIndicator() {
		return requestAuthorisationIndicator;
	}
	/**
	 * @param requestAuthorisationIndicator The requestAuthorisationIndicator to set.
	 */
	public void setRequestAuthorisationIndicator(
			String requestAuthorisationIndicator) {
		this.requestAuthorisationIndicator = requestAuthorisationIndicator;
	}
	/**
	 * @return Returns the requestAuthorisationReference.
	 */
	@Column(name = "REQAUTREF")
	public String getRequestAuthorisationReference() {
		return requestAuthorisationReference;
	}
	/**
	 * @param requestAuthorisationReference The requestAuthorisationReference to set.
	 */
	public void setRequestAuthorisationReference(
			String requestAuthorisationReference) {
		this.requestAuthorisationReference = requestAuthorisationReference;
	}
	/**
	 * @return Returns the contractCurrencyCode.
	 */
	@Column(name = "CRTCURCOD")
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}
	/**
	 * @param contractCurrencyCode The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}
	
	
	/**
	 * @return Returns the acctTxnIdr.
	 */
	@Column(name = "ACCTXNIDR")
	public String getAcctTxnIdr() {
		return acctTxnIdr;
	}
	/**
	 * @param acctTxnIdr The acctTxnIdr to set.
	 */
	public void setAcctTxnIdr(String acctTxnIdr) {
		this.acctTxnIdr = acctTxnIdr;
	}
	@Column(name = "MALSEQNUM") 
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}
	/*@Column(name = "MALSEQNUM") 
	public String getMailSeqNumber() {
		return mailSeqNumber;
	}

	public void setMailSeqNumber(String mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}*/
	 /**
     * @author A-2391
     * removes the entity
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()throws RemoveException,SystemException{
    	log.entering(MODULE_NAME,"remove");
		
	    	log.exiting(MODULE_NAME,"remove");
		PersistenceController.getEntityManager().remove(this);
    }
    /**
     * @author A-2391
     *  finds the entity
     * @param companyCode 
     * @param airlineIdentifier
     * @param memoCode
     * @return RejectionMemo
     * @throws SystemException
     * @throws FinderException
     */


    public static RejectionMemo find(String companyCode,
            int airlineIdentifier,String memoCode)
    throws SystemException,FinderException {
    	RejectionMemoPK pk = new RejectionMemoPK();
		pk.setCompanyCode(   companyCode);
		pk.setAirlineIdentifier(   airlineIdentifier);
		pk.setMemoCode(   memoCode);
		return PersistenceController.getEntityManager().find(
				RejectionMemo.class, pk);

    }
    
    /**
	 * 
	 * @param rejectionMemoVO
	 */
	public  void update( RejectionMemoVO rejectionMemoVO ) {
		
			populateAllAttributes(rejectionMemoVO);
		
	}
	/**
	 * 
	 * @param rejectionMemoVO
	 */
	private void populateAllAttributes( RejectionMemoVO rejectionMemoVO ) {
		log.entering(MODULE_NAME,"populateAllAttributes");	
		log.log(Log.INFO, "rejectionvo ", rejectionMemoVO);
		this.setAirlineCode(rejectionMemoVO.getAirlineCode());
		this.setBillingAcceptedAmount(rejectionMemoVO.getBillingAcceptedAmount().getRoundedAmount());
		this.setBillingBilledAmount(rejectionMemoVO.getBillingBilledAmount().getRoundedAmount());
		if(rejectionMemoVO.getBillingClearanceExchangeRate()!=null){
		this.setBillingClearanceExchangeRate(rejectionMemoVO.getBillingClearanceExchangeRate());
		}else{
			this.setBillingClearanceExchangeRate(0.0);
		}
		this.setBillingCurrencyCode(rejectionMemoVO.getBillingCurrencyCode());
		this.setBillingRejectedAmount(rejectionMemoVO.getBillingRejectedAmount().getRoundedAmount());
		if(rejectionMemoVO.getClearanceAcceptedAmount()!=null){
		this.setClearanceAcceptedAmount(rejectionMemoVO.getClearanceAcceptedAmount());
		}else{
			this.setClearanceAcceptedAmount(0.0);
		}		
		if(rejectionMemoVO.getClearanceBilledAmount()!=null){
		this.setClearanceBilledAmount(rejectionMemoVO.getClearanceBilledAmount());
		}else{
			this.setClearanceBilledAmount(0.0);
		}		
		this.setChrgNotConvertedToConIndicator(rejectionMemoVO.getChargeNotConvertedToContractIndicator());
		if(rejectionMemoVO.getClearanceRejectedAmount()!=null){
		this.setClearanceRejectedAmount(rejectionMemoVO.getClearanceRejectedAmount());
		}else{
			this.setClearanceRejectedAmount(0.0);
		}		
		if(rejectionMemoVO.getContractAcceptedAmount()!=null){
		this.setContractAcceptedAmount(rejectionMemoVO.getContractAcceptedAmount());
		}else{
			this.setContractAcceptedAmount(0.0);
		}		
		if(rejectionMemoVO.getContractBilledAmount()!=null){
		this.setContractBilledAmount(rejectionMemoVO.getContractBilledAmount());
		}else{
			this.setContractBilledAmount(0.0);
		}		
		this.setChrgNotCoveredByConIndicator(rejectionMemoVO.getChargeNotCoveredByContractIndicator());
		if(rejectionMemoVO.getContractBillingExchangeRate()!=null){
		this.setContractBillingExchangeRate(rejectionMemoVO.getContractBillingExchangeRate());
		}else{
			this.setContractBillingExchangeRate(0.0);
		}		
		if(rejectionMemoVO.getContractRejectedAmount()!=null){
		this.setContractRejectedAmount(rejectionMemoVO.getContractRejectedAmount());
		}else{
			this.setContractRejectedAmount(0.0);
		}		
		this.setClearanceCurrencyCode(rejectionMemoVO.getClearanceCurrencyCode());
		this.setContractCurrencyCode(rejectionMemoVO.getContractCurrencyCode());
		this.setDuplicateBillingIndicator(rejectionMemoVO.getDuplicateBillingIndicator());
		this.setDuplicateBillingInvoiceNumber(rejectionMemoVO.getDuplicateBillingInvoiceNumber());
		this.setIncorrectExchangeRateIndicator(rejectionMemoVO.getIncorrectExchangeRateIndicator());
		this.setInwardClearancePeriod(rejectionMemoVO.getInwardClearancePeriod());
		this.setInwardInvoiceDate(rejectionMemoVO.getInwardInvoiceDate());
		this.setInwardInvoiceNumber(rejectionMemoVO.getInwardInvoiceNumber());
		this.setMemoStatus(rejectionMemoVO.getMemoStatus());
		this.setNoApprovalIndicator(rejectionMemoVO.getNoApprovalIndicator());
		this.setNoReceiptIndicator(rejectionMemoVO.getNoReceiptIndicator());
		this.setOtherIndicator(rejectionMemoVO.getOtherIndicator());
		this.setOutTimeLimitsForBillingIndicator(rejectionMemoVO.getOutTimeLimitsForBillingIndicator());
		this.setOutwardClearancePeriod(rejectionMemoVO.getOutwardClearancePeriod());
		this.setOutwardInvoiceNumber(rejectionMemoVO.getOutwardInvoiceNumber());
		this.setRemarks(rejectionMemoVO.getRemarks());
		this.setRequestAuthorisationIndicator(rejectionMemoVO.getRequestAuthorisationIndicator());
		this.setRequestAuthorisationReference(rejectionMemoVO.getRequestAuthorisationReference());
		this.setRequestAuthorisationDate(rejectionMemoVO.getRequestAuthorisationDate());
		this.setOutwardInvoiceDate(rejectionMemoVO.getOutwardInvoiceDate());	
		this.setDuplicateBillingInvoiceDate(rejectionMemoVO.getDuplicateBillingInvoiceDate());
		this.setAcctTxnIdr(rejectionMemoVO.getAcctTxnIdr());
		if(rejectionMemoVO.getProvisionalAmount()!=null){
		this.setProvisionalAmt(rejectionMemoVO.getProvisionalAmount());
		}else{
			this.setProvisionalAmt(0.0);
		}
		//this.setBillingBasis(rejectionMemoVO.getBillingBasis());     //Commented As part of ICRD-265471
		//this.setConsignmentDocumentNumber(rejectionMemoVO.getCsgDocNum());
		//this.setConsignmentSequenceNumber(rejectionMemoVO.getCsgSeqNum());
		//this.setPoaCode(rejectionMemoVO.getPoaCode());
		this.setDsn(rejectionMemoVO.getDsn());
		this.setRejectedDate(rejectionMemoVO.getRejectedDate());
		this.setLastUpdatedUser(rejectionMemoVO.getLastUpdatedUser());
		this.setLastUpdatedTime(rejectionMemoVO.getLastUpdatedTime());
		this.setMailSeqNumber(new Long(rejectionMemoVO.getMailSequenceNumber()));
		log.exiting(MODULE_NAME,"populateAllAttributes");
	}
	 

	/**
	 * @author A-2391
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @exception  SystemException
	 */
	public static RejectionMemoVO findRejectionMemo(
			RejectionMemoFilterVO filterVO)
			throws SystemException {
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			RejectionMemoVO rejectionMemoVO=mRAAirlineBillingDAO
			.findRejectionMemo(filterVO);
			if(rejectionMemoVO!=null){
				if(rejectionMemoVO.getInwardClearancePeriod()!=null){
				String invclrprd=rejectionMemoVO.getInwardClearancePeriod().substring(2,4);
				rejectionMemoVO.setMonthOfClearance(invclrprd);
				}
				if(rejectionMemoVO.getOutwardClearancePeriod()!=null){
				String outclrprd=rejectionMemoVO.getOutwardClearancePeriod().substring(2,4);
				rejectionMemoVO.setOutMonthOfClearance(outclrprd);
				}
				}
			return rejectionMemoVO;
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-2391
	 * @param rejectionMemoVO
	 * @return String
	 * @exception  SystemException
	 */
	public static String findBlgCurCode(
			RejectionMemoVO rejectionMemoVO)
			throws SystemException {
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			return mRAAirlineBillingDAO
					.findBlgCurCode(rejectionMemoVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-2391
	 * @param rejectionMemoVO
	 * @return double
	 * @exception  SystemException
	 */
	public static double findExgRate(
			RejectionMemoVO rejectionMemoVO)
			throws SystemException {
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			return mRAAirlineBillingDAO
					.findExgRate(rejectionMemoVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @param filterVO
	 * @param userId
	 * @return
	 * @throws SystemException
	 */
	public static String findAcctTxnIdr(
			RejectionMemoFilterVO filterVO,String userId)
			throws SystemException {
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			return mRAAirlineBillingDAO
					.findAcctTxnIdr(filterVO,userId);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @return the provisionalAmt
	 */
	//@Column(name = "PROVAMT")
	@Column(name = "PROVAMTLSTCUR")     //Modified as part of ICRD-265471
	public Double getProvisionalAmt() {
		return provisionalAmt;
	}
	/**
	 * @param provisionalAmt the provisionalAmt to set
	 */
	public void setProvisionalAmt(Double provisionalAmt) {
		this.provisionalAmt = provisionalAmt;
	}
	/**
	 * @return the billingBasis
	 */
	/*@Column(name = "BLGBAS")
	public String getBillingBasis() {
		return billingBasis;
	}*/
	/**
	 * @param billingBasis the billingBasis to set
	 */
	/*public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}*/
	/**
	 * @return the consignmentDocumentNumber
	 */
	/*@Column(name = "CSGDOCNUM")
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}*/
	/**
	 * @param consignmentDocumentNumber the consignmentDocumentNumber to set
	 */
	/*public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}*/
	/**
	 * @return the poaCode
	 */
	/*@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}*/
	/**
	 * @param poaCode the poaCode to set
	 */
	/*public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}*/
	/**
	 * @return the consignmentSequenceNumber
	 */
	/*@Column(name = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}*/
	/**
	 * @param consignmentSequenceNumber the consignmentSequenceNumber to set
	 */
	/*public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}*/
	/**
	 * @return the dsn
	 */
	@Column(name = "DSN")
	public String getDsn() {
		return dsn;
	}
	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	/**
	 * @return the rejectedDate
	 */
	@Column(name = "MEMDAT")
	public Calendar getRejectedDate() {
		return rejectedDate;
	}
	/**
	 * @param rejectedDate the rejectedDate to set
	 */
	public void setRejectedDate(Calendar rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	/**
	 * @return the revFlag
	 */
	@Column(name = "REVFLG")
	public String getRevFlag() {
		return revFlag;
	}
	/**
	 * @param revFlag the revFlag to set
	 */
	public void setRevFlag(String revFlag) {
		this.revFlag = revFlag;
	}
	
	
	/**
	 * @return the attachmentIndicator
	 */
	@Column(name = "ATHIND")
	public String getAttachmentIndicator() {
		return attachmentIndicator;
	}
	/**
	 * @param attachmentIndicator the attachmentIndicator to set
	 */
	public void setAttachmentIndicator(String attachmentIndicator) {
		this.attachmentIndicator = attachmentIndicator;
	}
	
	
/**
 * to trigger Rejection Memo Accounting at despatch level
 * @author Meenu A-2565
 * @param rejectionVO
 * @throws SystemException
 */
public static void triggerRejectionMemoAccounting(RejectionMemoVO rejectionVO) throws SystemException {
		try{
			MRAAirlineBillingDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO(MODULE_NAME)).triggerRejectionMemoAccounting(rejectionVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	
}
