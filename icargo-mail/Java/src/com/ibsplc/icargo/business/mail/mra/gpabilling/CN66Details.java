/*
 * CN66Details.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 *  Version History
 *  -----------------------------------------------------------------------------------
 *     Date                 Comment                                 User
 * =====================================================================================    
 *   09-Aug-2007 Modified for implementing optimistic Locking      A-2270
 */


@Entity
@Table(name = "MALMRAGPAC66DTL")
@Staleable
public class CN66Details {

    private static final String MRA_GPABILLING = "mail.mra.gpabilling";
    private static final String CLASS_NAME="CN66Details";
    private Log log = localLogger();
	private CN66DetailsPK cn66DetailsPK;
	
	private Calendar receivedDate;
    private String origin;
    private String destination;
    private String mailCategoryCode;
    private String mailSubclass;
    private int totalPieces;
    private double totalWeight;
    private String unitCode;
    private String rsn;
    private String dsn;
    private String remarks;
    private String billSectorOrigin;
    private String billSectorToo;
    private String flightCarrierCode;
    private int flightCarrierIdr;
    private String flightNumber;
    private Calendar flightDate;
    private double applicableRate;
    private String billingStatus;
    private String mcaRefNumber;
    private String accTransactionIdr;
    private String accountStatus;
    private double billedAmountInBillingCurr;
    private String actualSubclass;
    private double fuelSurcharge;
    private String rateIndicator;
    private String consgPACode;
    private String contrctCurrencyCode;
    private String billingCurrencyCode;
    private double vatAmountBillingCurrency;
    private double totalAmtExcludeVATinBillingCurr;
    private double serviceTaxInBillingCurr;
    private double tdsInBillingCurrency;
    private double netAmountInBillingCurr;
    private double valuationChargeInBillingCurr;
    private String domesticFlag;
    private double totalOtherChgInBillingCurr;
    private double mailChargeInBillingCurr;
    private String interfacedFlag;
    private Calendar interfacedTime;
    private double exchangeRateToSettlmntCurr;
    private double exchangeRateToBaseCurr;
    private String billingMatrixCode;
    private String rateLineIdr;
    /**
	 * @return the settelementStatus
	 */
    @Column(name = "STLSTA")
	public String getSettelementStatus() {
		return settelementStatus;
	}
	/**
	 * @param settelementStatus the settelementStatus to set
	 */
	public void setSettelementStatus(String settelementStatus) {
		this.settelementStatus = settelementStatus;
	}
	/**
	 * @return the settlementAmt
	 */
	  @Column(name = "STLAMT")
	public double getSettlementAmt() {
		return settlementAmt;
	}
	/**
	 * @param settlementAmt the settlementAmt to set
	 */
	public void setSettlementAmt(double settlementAmt) {
		this.settlementAmt = settlementAmt;
	}
	/**
	 * @return the dueAmount
	 */
	 @Column(name = "DUEAMT")
	public double getDueAmount() {
		return dueAmount;
	}
	/**
	 * @param dueAmount the dueAmount to set
	 */
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	


    private String rateType;
    private Calendar lastUpdateTime;
    private String lastUpdatedUser;
    private String settelementStatus;//added by a-7871 for ICRD-235799
    private double settlementAmt;
    private double dueAmount;
    private String caseClose ;

	private static final String SETTLEMENT_STATUS_SETTLED="S";
	private static final String SETTLEMENT_STATUS_DIFFERENCE="D";
	private static final String SETTLEMENT_STATUS_PENDING="E";
	private static final String SETTLEMENT_STATUS_OVERPIAD="O";
	private static final String SETTLEMENT_STATUS_FINALIZED="F";
    
    

	
    /**
     * @return Returns the cn66DetailsPK.
     */

    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="invoiceNumber", column=@Column(name="INVNUM")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
		@AttributeOverride(name="mailsequenceNumber", column=@Column(name="MALSEQNUM")),
		@AttributeOverride(name="invSerialNumber", column=@Column(name="INVSERNUM"))}
	)
    public CN66DetailsPK getCn66DetailsPK() {
        return cn66DetailsPK;
    }
    /**
     * @param cn66DetailsPK The cn66DetailsPK to set.
     */
    public void setCn66DetailsPK(CN66DetailsPK cn66DetailsPK) {
        this.cn66DetailsPK = cn66DetailsPK;
    }
    
	/**
	 * 
	 */
	public CN66Details() {
		super();
	}
	/**
	 * @return the receivedDate
	 */
    @Column(name = "RCVDAT")
	public Calendar getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the origin
	 */
	 @Column(name = "ORGCOD")
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	 @Column(name = "DSTCOD")
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the mailCategoryCode
	 */
	 @Column(name = "MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return the mailSubclass
	 */
	 @Column(name = "SUBCLSGRP")
	public String getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass the mailSubclass to set
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	/**
	 * @return the totalPieces
	 */
	 @Column(name = "TOTPCS")
	public int getTotalPieces() {
		return totalPieces;
	}
	/**
	 * @param totalPieces the totalPieces to set
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	/**
	 * @return the totalWeight
	 */
	 @Column(name = "TOTWGT")
	public double getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
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
	 * @return the rsn
	 */
	 @Column(name = "RSN")
	public String getRsn() {
		return rsn;
	}
	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
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
	 * @return the remarks
	 */
	 @Column(name = "RMK")
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
	 * @return the billSectorOrigin
	 */
	 @Column(name = "BILSECFRM")
	public String getBillSectorOrigin() {
		return billSectorOrigin;
	}
	/**
	 * @param billSectorOrigin the billSectorOrigin to set
	 */
	public void setBillSectorOrigin(String billSectorOrigin) {
		this.billSectorOrigin = billSectorOrigin;
	}
	/**
	 * @return the billSectorToo
	 */
	 @Column(name = "BILSECTOO")
	public String getBillSectorToo() {
		return billSectorToo;
	}
	/**
	 * @param billSectorToo the billSectorToo to set
	 */
	public void setBillSectorToo(String billSectorToo) {
		this.billSectorToo = billSectorToo;
	}
	/**
	 * @return the flightCarrierCode
	 */
	 @Column(name = "FLTCARCOD")
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
	 * @return the flightCarrierIdr
	 */
	 @Column(name = "FLTCARIDR")
	public int getFlightCarrierIdr() {
		return flightCarrierIdr;
	}
	/**
	 * @param flightCarrierIdr the flightCarrierIdr to set
	 */
	public void setFlightCarrierIdr(int flightCarrierIdr) {
		this.flightCarrierIdr = flightCarrierIdr;
	}
	/**
	 * @return the flightNumber
	 */
	 @Column(name = "FLTNUM")
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
	 * @return the flightDate
	 */
	 @Column(name = "FLTDAT")
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
	 * @return the applicableRate
	 */
	 @Column(name = "APLRAT")
	public double getApplicableRate() {
		return applicableRate;
	}
	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}
	/**
	 * @return the billingStatus
	 */
	 @Column(name = "BLGSTA")
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
	 * @return the mcaRefNumber
	 */
	 @Column(name = "MCAREFNUM")
	public String getMcaRefNumber() {
		return mcaRefNumber;
	}
	/**
	 * @param mcaRefNumber the mcaRefNumber to set
	 */
	public void setMcaRefNumber(String mcaRefNumber) {
		this.mcaRefNumber = mcaRefNumber;
	}
	/**
	 * @return the accTransactionIdr
	 */
	 @Column(name = "ACCTXNIDR")
	public String getAccTransactionIdr() {
		return accTransactionIdr;
	}
	/**
	 * @param accTransactionIdr the accTransactionIdr to set
	 */
	public void setAccTransactionIdr(String accTransactionIdr) {
		this.accTransactionIdr = accTransactionIdr;
	}
	/**
	 * @return the accountStatus
	 */
	 @Column(name = "ACCSTA")
	public String getAccountStatus() {
		return accountStatus;
	}
	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * @return the billedAmountInBillingCurr
	 */
	 @Column(name = "BLDAMTBLGCUR")
	public double getBilledAmountInBillingCurr() {
		return billedAmountInBillingCurr;
	}
	/**
	 * @param billedAmountInBillingCurr the billedAmountInBillingCurr to set
	 */
	public void setBilledAmountInBillingCurr(double billedAmountInBillingCurr) {
		this.billedAmountInBillingCurr = billedAmountInBillingCurr;
	}
	/**
	 * @return the actualSubclass
	 */
	 @Column(name = "ACTSUBCLS")
	public String getActualSubclass() {
		return actualSubclass;
	}
	/**
	 * @param actualSubclass the actualSubclass to set
	 */
	public void setActualSubclass(String actualSubclass) {
		this.actualSubclass = actualSubclass;
	}
	/**
	 * @return the fuelSurcharge
	 */
	 @Column(name = "FULCHG")
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
	 * @return the rateIndicator
	 */
	 @Column(name = "RATIND")
	public String getRateIndicator() {
		return rateIndicator;
	}
	/**
	 * @param rateIndicator the rateIndicator to set
	 */
	public void setRateIndicator(String rateIndicator) {
		this.rateIndicator = rateIndicator;
	}
	/**
	 * @return the consgPACode
	 */
	 @Column(name = "CSGPOACOD")
	public String getConsgPACode() {
		return consgPACode;
	}
	/**
	 * @param consgPACode the consgPACode to set
	 */
	public void setConsgPACode(String consgPACode) {
		this.consgPACode = consgPACode;
	}
	/**
	 * @return the billingCurrencyCode
	 */
	 @Column(name = "BLGCURCOD")
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}
	/**
	 * @param billingCurrencyCode the billingCurrencyCode to set
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}
	/**
	 * @return the vatAmountBillingCurrency
	 */
	 @Column(name = "VATAMTBLGCUR")
	public double getVatAmountBillingCurrency() {
		return vatAmountBillingCurrency;
	}
	/**
	 * @param vatAmountBillingCurrency the vatAmountBillingCurrency to set
	 */
	public void setVatAmountBillingCurrency(double vatAmountBillingCurrency) {
		this.vatAmountBillingCurrency = vatAmountBillingCurrency;
	}
	/**
	 * @return the totalAmtExcludeVATinBillingCurr
	 */
	 @Column(name = "TOTAMTEXCVATBLGCUR")
	public double getTotalAmtExcludeVATinBillingCurr() {
		return totalAmtExcludeVATinBillingCurr;
	}
	/**
	 * @param totalAmtExcludeVATinBillingCurr the totalAmtExcludeVATinBillingCurr to set
	 */
	public void setTotalAmtExcludeVATinBillingCurr(
			double totalAmtExcludeVATinBillingCurr) {
		this.totalAmtExcludeVATinBillingCurr = totalAmtExcludeVATinBillingCurr;
	}
	/**
	 * @return the serviceTaxInBillingCurr
	 */
	 @Column(name = "SRVTAXBLGCUR")
	public double getServiceTaxInBillingCurr() {
		return serviceTaxInBillingCurr;
	}
	/**
	 * @param serviceTaxInBillingCurr the serviceTaxInBillingCurr to set
	 */
	public void setServiceTaxInBillingCurr(double serviceTaxInBillingCurr) {
		this.serviceTaxInBillingCurr = serviceTaxInBillingCurr;
	}
	/**
	 * @return the tdsInBillingCurrency
	 */
	 @Column(name = "TDSBLGCUR")
	public double getTdsInBillingCurrency() {
		return tdsInBillingCurrency;
	}
	/**
	 * @param tdsInBillingCurrency the tdsInBillingCurrency to set
	 */
	public void setTdsInBillingCurrency(double tdsInBillingCurrency) {
		this.tdsInBillingCurrency = tdsInBillingCurrency;
	}
	/**
	 * @return the netAmountInBillingCurr
	 */
	 @Column(name = "NETAMTBLGCUR")
	public double getNetAmountInBillingCurr() {
		return netAmountInBillingCurr;
	}
	/**
	 * @param netAmountInBillingCurr the netAmountInBillingCurr to set
	 */
	public void setNetAmountInBillingCurr(double netAmountInBillingCurr) {
		this.netAmountInBillingCurr = netAmountInBillingCurr;
	}
	/**
	 * @return the valuationChargeInBillingCurr
	 */
	 @Column(name = "VALCHGBLGCUR")
	public double getValuationChargeInBillingCurr() {
		return valuationChargeInBillingCurr;
	}
	/**
	 * @param valuationChargeInBillingCurr the valuationChargeInBillingCurr to set
	 */
	public void setValuationChargeInBillingCurr(double valuationChargeInBillingCurr) {
		this.valuationChargeInBillingCurr = valuationChargeInBillingCurr;
	}
	/**
	 * @return the domesticFlag
	 */
	 @Column(name = "DOMFLG")
	public String getDomesticFlag() {
		return domesticFlag;
	}
	/**
	 * @param domesticFlag the domesticFlag to set
	 */
	public void setDomesticFlag(String domesticFlag) {
		this.domesticFlag = domesticFlag;
	}
	/**
	 * @return the totalOtherChgInBillingCurr
	 */
	 @Column(name = "OTHCHGBLGCUR")
	public double getTotalOtherChgInBillingCurr() {
		return totalOtherChgInBillingCurr;
	}
	/**
	 * @param totalOtherChgInBillingCurr the totalOtherChgInBillingCurr to set
	 */
	public void setTotalOtherChgInBillingCurr(double totalOtherChgInBillingCurr) {
		this.totalOtherChgInBillingCurr = totalOtherChgInBillingCurr;
	}
	/**
	 * @return the mailChargeInBillingCurr
	 */
	 @Column(name = "MALCHGBLGCUR")
	public double getMailChargeInBillingCurr() {
		return mailChargeInBillingCurr;
	}
	/**
	 * @param mailChargeInBillingCurr the mailChargeInBillingCurr to set
	 */
	public void setMailChargeInBillingCurr(double mailChargeInBillingCurr) {
		this.mailChargeInBillingCurr = mailChargeInBillingCurr;
	}
	/**
	 * @return the interfacedFlag
	 */
	 @Column(name = "INTFCDFLG")
	public String getInterfacedFlag() {
		return interfacedFlag;
	}
	/**
	 * @param interfacedFlag the interfacedFlag to set
	 */
	public void setInterfacedFlag(String interfacedFlag) {
		this.interfacedFlag = interfacedFlag;
	}
	/**
	 * @return the interfacedTime
	 */
	 @Column(name = "INTFCDTIM")
	public Calendar getInterfacedTime() {
		return interfacedTime;
	}
	/**
	 * @param interfacedTime the interfacedTime to set
	 */
	public void setInterfacedTime(Calendar interfacedTime) {
		this.interfacedTime = interfacedTime;
	}
	/**
	 * @return the exchangeRateToSettlmntCurr
	 */
	 @Column(name = "EXGRATCTR")
	public double getExchangeRateToSettlmntCurr() {
		return exchangeRateToSettlmntCurr;
	}
	/**
	 * @param exchangeRateToSettlmntCurr the exchangeRateToSettlmntCurr to set
	 */
	public void setExchangeRateToSettlmntCurr(double exchangeRateToSettlmntCurr) {
		this.exchangeRateToSettlmntCurr = exchangeRateToSettlmntCurr;
	}
	/**
	 * @return the exchangeRateToBaseCurr
	 */
	 @Column(name = "EXGRATBAS")
	public double getExchangeRateToBaseCurr() {
		return exchangeRateToBaseCurr;
	}
	/**
	 * @param exchangeRateToBaseCurr the exchangeRateToBaseCurr to set
	 */
	public void setExchangeRateToBaseCurr(double exchangeRateToBaseCurr) {
		this.exchangeRateToBaseCurr = exchangeRateToBaseCurr;
	}
	/**
	 * @return the billingMatrixCode
	 */
	 @Column(name = "BLGMTXCOD")
	public String getBillingMatrixCode() {
		return billingMatrixCode;
	}
	/**
	 * @param billingMatrixCode the billingMatrixCode to set
	 */
	public void setBillingMatrixCode(String billingMatrixCode) {
		this.billingMatrixCode = billingMatrixCode;
	}
	/**
	 * @return the rateLineIdr
	 */
	 @Column(name = "RATLINIDR")
	public String getRateLineIdr() {
		return rateLineIdr;
	}
	/**
	 * @param rateLineIdr the rateLineIdr to set
	 */
	public void setRateLineIdr(String rateLineIdr) {
		this.rateLineIdr = rateLineIdr;
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
	 * @return the contrctCurrencyCode
	 */
	 @Column(name = "CTRCURCOD")
	public String getContrctCurrencyCode() {
		return contrctCurrencyCode;
	}
	/**
	 * @param contrctCurrencyCode the contrctCurrencyCode to set
	 */
	public void setContrctCurrencyCode(String contrctCurrencyCode) {
		this.contrctCurrencyCode = contrctCurrencyCode;
	}
	
	@Column(name = "CASCLSFLG")
	public String getCaseClose() {
		return caseClose;
	}
	public void setCaseClose(String caseClose) {
		this.caseClose = caseClose;
	}
	/**
     * Argument Constructor
     * used to persist data
     * @author A-2280
     * @param cn66DetailsVO
     * @throws SystemException
     */
    public CN66Details(CN66DetailsVO cn66DetailsVO)throws  SystemException {
    	log.entering(CLASS_NAME,"CN66Details");
          populatePK(cn66DetailsVO);
          populateAttributes(cn66DetailsVO);
          try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),createException);
		}
          log.exiting(CLASS_NAME,"CN66Details");

    }

    /**
     * This method populates
     * the Pk attributes
     * @param cn66DetailsVO
     */
    private void populatePK(CN66DetailsVO cn66DetailsVO){
    	log.entering(CLASS_NAME,"populatePK");
    	CN66DetailsPK cn66DetailPK=new CN66DetailsPK();
        cn66DetailPK.setCompanyCode( cn66DetailsVO.getCompanyCode());
        cn66DetailPK.setGpaCode( cn66DetailsVO.getGpaCode());
        cn66DetailPK.setInvoiceNumber( cn66DetailsVO.getInvoiceNumber());
        cn66DetailPK.setMailsequenceNumber( cn66DetailsVO.getSequenceNumber());
        this.setCn66DetailsPK(cn66DetailPK);
        log.exiting(CLASS_NAME,"populatePK");
    }
    /**
     * This method populates the
     * attributes.
     * @author A-2280
     * @param cn66DetailsVO
     */
    private void populateAttributes(CN66DetailsVO cn66DetailsVO){
    	log.entering(CLASS_NAME,"populateAttributes");
    	this.setOrigin(cn66DetailsVO.getOrigin());
    	this.setDestination(cn66DetailsVO.getDestination());
    	this.setDsn(cn66DetailsVO.getDsn());
    	this.setMailCategoryCode(cn66DetailsVO.getMailCategoryCode());
    	this.setMailSubclass(cn66DetailsVO.getMailSubclass());
    	this.setRemarks(cn66DetailsVO.getRemarks());
    	this.setTotalPieces(cn66DetailsVO.getTotalPieces());
    	this.setTotalWeight(cn66DetailsVO.getTotalWeight());
    	this.setLastUpdatedUser(cn66DetailsVO.getLastUpdatedUser());
    	this.setLastUpdateTime(cn66DetailsVO.getLastupdatedTime());
    	log.exiting(CLASS_NAME,"populateAttributes");

    }
    /**
     * @author A-2280
     * @param companyCode
     * @param invoiceNumber
     * @param gpaCode
     * @param sequenceNumber
     * @param billingBasis
     * @return
     * @throws SystemException
     * @throws FinderException
     */
    //Modified by A-7794 as part of MRA revamp
    public static CN66Details find(String companyCode, String invoiceNumber,
            String gpaCode,long mailsequenceNumber,int invSerialNumber)throws SystemException,FinderException {
    	Log log = localLogger();
    	log.entering(CLASS_NAME,"find");

    	CN66Details cn66Details=null;
    	CN66DetailsPK cn66DetailsPk=new CN66DetailsPK(companyCode,invoiceNumber,
    			                  gpaCode,mailsequenceNumber,invSerialNumber);
    	cn66Details=PersistenceController.getEntityManager().find(CN66Details.class,cn66DetailsPk);
    	log.exiting(CLASS_NAME,"find");
        return cn66Details;
    }

    public void remove()throws RemoveException,SystemException{
    	log.entering(CLASS_NAME,"remove"); 
    	PersistenceController.getEntityManager().remove(this);
    	log.exiting(CLASS_NAME,"remove");
    }

    public void update() throws SystemException,
	RemoveException,CreateException,FinderException{}

    /**
     * This method is to get the refernec of log
     * @author A-2280
     * @return
     */
    private static Log localLogger() {
		return LogFactory.getLogger("MRA GPABilling");
	}

    /**
     * Finds and returns the CN66 details
     *
     * @param cn51CN66FilterVO
     * @return Collection<CN66DetailsVO>
     * @throws SystemException
     */
//  TODO Collection<CN66DetailsVO>
    public Page<CN66DetailsVO>  findCN66Details(CN51CN66FilterVO cn51CN66FilterVO)
    throws SystemException{
    	Page<CN66DetailsVO> cn66DetailsVOs=null;
    	try {
    		cn66DetailsVOs=constructDAO().findCN66Details(cn51CN66FilterVO);
		} catch (PersistenceException e) {
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}

    	return cn66DetailsVOs;
    }

    /**
     * @author A-2280
     * This method is to get the SqlDao Reference
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */
    private static MRAGPABillingDAO constructDAO()throws PersistenceException, SystemException{


		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRAGPABillingDAO.class.cast(
				entityManager.getQueryDAO(MRA_GPABILLING));
	}

	/**
	 * 	Method		:	CN66Details.findMailDetailsforAudit
	 *	Added by 	:	a-4809 on Apr 2, 2014
	 * 	Used for 	:    to find mail details for stamping audit
	 * when invoice generated
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<MailDetailVO>
	 */
	public static Collection<MailDetailVO> findMailDetailsforAudit(String invoiceNumber,String companyCode)
	throws SystemException{
		Collection<MailDetailVO> mailDetailVOs = null;
		try {
			mailDetailVOs =constructDAO().findMailDetailsforAudit(invoiceNumber, companyCode);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		} 
		return mailDetailVOs;
	}
	
	
	/**
	 * 	Method		:	CN66Details.updateSettlementStatus
	 *	Added by 	:	a-7871 on jun 07, 2018
	 * 	Used for 	:    to update settlement status
	 *	Parameters	:	@param settledAmount
	 */
	public void updateSettlementStatus(double settledAmount) {
		
	/*	if(this.settlementAmt==this.netAmountInBillingCurr || this.settlementAmt<this.netAmountInBillingCurr)
		this.settlementAmt= settledAmount;	
		else
			this.settlementAmt+=this.dueAmount;
		if(this.settlementAmt==this.netAmountInBillingCurr){
			this.settelementStatus=SETTLEMENT_STATUS_SETTLED;			
		}
		else if(this.settlementAmt<this.netAmountInBillingCurr){
			this.settelementStatus=SETTLEMENT_STATUS_DIFFERENCE;	
			}
			else if(this.settlementAmt>this.netAmountInBillingCurr){//added by a-7871 for ICRD-235799 for overpaid invoice
					this.settelementStatus=SETTLEMENT_STATUS_OVERPIAD;
			}	
			
		this.dueAmount=this.netAmountInBillingCurr-this.settlementAmt;*/
		
		
		if(this.netAmountInBillingCurr<settledAmount)
		{
		this.settlementAmt= settledAmount;
		this.settelementStatus=SETTLEMENT_STATUS_OVERPIAD;
		//this.dueAmount=this.dueAmount-settledAmount;//ICRD-349950
		this.dueAmount=this.netAmountInBillingCurr-this.settlementAmt;//ICRD-349950
		
		
		}
		else
		{
			this.settlementAmt=settledAmount;
		if(this.settlementAmt==this.netAmountInBillingCurr){
			this.settelementStatus=SETTLEMENT_STATUS_SETTLED;			
		}
		
			else{
				this.settelementStatus=SETTLEMENT_STATUS_DIFFERENCE;	
				
			}
		this.dueAmount=this.netAmountInBillingCurr-this.settlementAmt;
		}
		
		
		
		
		
		
		
		
	}
	

}
