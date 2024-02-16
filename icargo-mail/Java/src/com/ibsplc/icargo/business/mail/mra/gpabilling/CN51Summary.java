/*
 * CN51Summary.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.ibsplc.icargo.business.cra.defaults.vo.tk.AOInvoiceReportDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.ProformaInvoiceDiffReportVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Philip
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jan 8, 2007   Philip 		            Initial draft
 *  0.2         Jan 17,2007   Kiran 					Implemented the entity methods and findAllInvoices
 *              Mar 26,2007	  Prem Kumar.M				Added findSettlementDetails method
 *              Mar 27,2007	 Prem Kumar.M				Added method findSettlementHistory
 *
 *
 */
@Entity
@Table(name = "MALMRAGPAC51SMY")
@Staleable
public class CN51Summary {

	private static final String CLASS_NAME = "CN51Summary";

	private static final Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String MODULE_NAME = "mail.mra.gpabilling";

	private static final String SETTLEMENT_STATUS_SETTLED="S";
	private static final String SETTLEMENT_STATUS_DIFFERENCE="D";
	private static final String SETTLEMENT_STATUS_PENDING="F";
	private static final String SETTLEMENT_STATUS_OVERPIAD="O";//added by a-7871 for ICRD-235799
	/**
	 * the PK for the entity
	 */
	private CN51SummaryPK cn51SummaryPK;
	
	private Set<CN51Details> cn51details;

	private Set<CN66Details> cN66Details;
	private Calendar billingDate;
	private String contractCurrencyCode;
	private String billingCurrencyCode;
	private Calendar billingPeriodFrom;
	private Calendar billingPeriodToo;
	private double settlementAmount;
	private double totalAmountInContractCurr;
	private double totalAmountInBillingCurr;
	private double totalAmountInBaseCurr;
	private double totalAmountForLC;
	private double totalAmountForCP;
	private double totalAmountForEMS;
	private double totalAmountForSacVides;
	private String settlementStatus;
	private String invoiceStatus;
	private String vatNumber;
	private double totalVATinBillingCurr;
	private double totalVATinBaseCurr;
	private double amountExcludVAT;
	private double dueAmount;
	private String emailSentFlag;
	private String invoiceRefNumber;
	private String domesticFlag;
	private double totalValuationChargeINBillingCurr;
	private String gibIdentifier;
	private Calendar invoiceDate;
	private String invoiceGeneratedUser;
	private Calendar invoicePrintedDate;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	
	//Added by A-7929 for ICRD-245605
	private String interfacedFileName;
	private Calendar interfacedTime;
	

	
	
	/* *********************** getters and setters for the attributes of the entity ******************** */

	
	/**
	 * @return Returns the cn51details.
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "INVNUM", referencedColumnName = "INVNUM", insertable=false, updatable=false),
		@JoinColumn(name = "INVSERNUM", referencedColumnName ="INVSERNUM", insertable=false, updatable=false),
		@JoinColumn(name = "GPACOD", referencedColumnName = "GPACOD", insertable=false, updatable=false)})
		public Set<CN51Details> getCn51details() {
		return cn51details;
	}
	/**
	 * @param cn51details The cn51details to set.
	 */
	public void setCn51details(Set<CN51Details> cn51details) {
		this.cn51details = cn51details;
	}
	/**
	 * @return Returns the cn51SummaryPK.
	 */
	//TODO
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="invoiceNumber", column=@Column(name="INVNUM")),
		@AttributeOverride(name="invSerialNumber", column=@Column(name="INVSERNUM")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD"))}
	)
	public CN51SummaryPK getCn51SummaryPK() {
		return cn51SummaryPK;
	}
	/**
	 * @param cn51SummaryPK The cn51SummaryPK to set.
	 */
	public void setCn51SummaryPK(CN51SummaryPK cn51SummaryPK) {
		this.cn51SummaryPK = cn51SummaryPK;
	}   
	

	/**
	 * @return the billingDate
	 */
	@Column(name = "BLGDAT")
	public Calendar getBillingDate() {
		return billingDate;
	}
	/**
	 * @param billingDate the billingDate to set
	 */
	public void setBillingDate(Calendar billingDate) {
		this.billingDate = billingDate;
	}
	/**
	 * @return the contractCurrencyCode
	 */
	@Column(name = "CTRCURCOD")
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
	 * @return the billingPeriodFrom
	 */
	@Column(name = "BLGPRDFRM")
	public Calendar getBillingPeriodFrom() {
		return billingPeriodFrom;
	}
	/**
	 * @param billingPeriodFrom the billingPeriodFrom to set
	 */
	public void setBillingPeriodFrom(Calendar billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}
	/**
	 * @return the billingPeriodToo
	 */
	@Column(name = "BLGPRDTOO")
	public Calendar getBillingPeriodToo() {
		return billingPeriodToo;
	}
	/**
	 * @param billingPeriodToo the billingPeriodToo to set
	 */
	public void setBillingPeriodToo(Calendar billingPeriodToo) {
		this.billingPeriodToo = billingPeriodToo;
	}
	/**
	 * @return the settlementAmount
	 */
	@Column(name = "STLAMT")
	public double getSettlementAmount() {
		return settlementAmount;
	}
	/**
	 * @param settlementAmount the settlementAmount to set
	 */
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	/**
	 * @return the totalAmountInContractCurr
	 */
	@Column(name = "TOTAMTCTRCUR")
	public double getTotalAmountInContractCurr() {
		return totalAmountInContractCurr;
	}
	/**
	 * @param totalAmountInContractCurr the totalAmountInContractCurr to set
	 */
	public void setTotalAmountInContractCurr(double totalAmountInContractCurr) {
		this.totalAmountInContractCurr = totalAmountInContractCurr;
	}
	/**
	 * @return the totalAmountInBillingCurr
	 */
	@Column(name = "TOTAMTBLGCUR")
	public double getTotalAmountInBillingCurr() {
		return totalAmountInBillingCurr;
	}
	/**
	 * @param totalAmountInBillingCurr the totalAmountInBillingCurr to set
	 */
	public void setTotalAmountInBillingCurr(double totalAmountInBillingCurr) {
		this.totalAmountInBillingCurr = totalAmountInBillingCurr;
	}
	/**
	 * @return the totalAmountInBaseCurr
	 */
	@Column(name = "TOTAMTBASCUR")
	public double getTotalAmountInBaseCurr() {
		return totalAmountInBaseCurr;
	}
	/**
	 * @param totalAmountInBaseCurr the totalAmountInBaseCurr to set
	 */
	public void setTotalAmountInBaseCurr(double totalAmountInBaseCurr) {
		this.totalAmountInBaseCurr = totalAmountInBaseCurr;
	}
	/**
	 * @return the totalAmountForLC
	 */
	@Column(name = "TOTAMTLCBLGCUR")
	public double getTotalAmountForLC() {
		return totalAmountForLC;
	}
	/**
	 * @param totalAmountForLC the totalAmountForLC to set
	 */
	public void setTotalAmountForLC(double totalAmountForLC) {
		this.totalAmountForLC = totalAmountForLC;
	}
	/**
	 * @return the totalAmountForCP
	 */
	@Column(name = "TOTAMTCPBLGCUR")
	public double getTotalAmountForCP() {
		return totalAmountForCP;
	}
	/**
	 * @param totalAmountForCP the totalAmountForCP to set
	 */
	public void setTotalAmountForCP(double totalAmountForCP) {
		this.totalAmountForCP = totalAmountForCP;
	}
	/**
	 * @return the totalAmountForEMS
	 */
	@Column(name = "TOTAMTEMSBLGCUR")
	public double getTotalAmountForEMS() {
		return totalAmountForEMS;
	}
	/**
	 * @param totalAmountForEMS the totalAmountForEMS to set
	 */
	public void setTotalAmountForEMS(double totalAmountForEMS) {
		this.totalAmountForEMS = totalAmountForEMS;
	}
	/**
	 * @return the totalAmountForSacVides
	 */
	@Column(name = "TOTAMTSVSBLGCUR")
	public double getTotalAmountForSacVides() {
		return totalAmountForSacVides;
	}
	/**
	 * @param totalAmountForSacVides the totalAmountForSacVides to set
	 */
	public void setTotalAmountForSacVides(double totalAmountForSacVides) {
		this.totalAmountForSacVides = totalAmountForSacVides;
	}
	/**
	 * @return the settlementStatus
	 */
	@Column(name = "STLSTA")
	public String getSettlementStatus() {
		return settlementStatus;
	}
	/**
	 * @param settlementStatus the settlementStatus to set
	 */
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	/**
	 * @return the invoiceStatus
	 */
	@Column(name = "INVSTA")
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	/**
	 * @param invoiceStatus the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	/**
	 * @return the vatNumber
	 */
	@Column(name = "VATNUM")
	public String getVatNumber() {
		return vatNumber;
	}
	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	/**
	 * @return the totalVATinBillingCurr
	 */
	@Column(name = "TOTVATAMTBLGCUR")
	public double getTotalVATinBillingCurr() {
		return totalVATinBillingCurr;
	}
	/**
	 * @param totalVATinBillingCurr the totalVATinBillingCurr to set
	 */
	public void setTotalVATinBillingCurr(double totalVATinBillingCurr) {
		this.totalVATinBillingCurr = totalVATinBillingCurr;
	}
	/**
	 * @return the totalVATinBaseCurr
	 */
	@Column(name = "TOTVATAMTBASCUR")
	public double getTotalVATinBaseCurr() {
		return totalVATinBaseCurr;
	}
	/**
	 * @param totalVATinBaseCurr the totalVATinBaseCurr to set
	 */
	public void setTotalVATinBaseCurr(double totalVATinBaseCurr) {
		this.totalVATinBaseCurr = totalVATinBaseCurr;
	}
	/**
	 * @return the amountExcludVAT
	 */
	@Column(name = "AMTEXCVAT")
	public double getAmountExcludVAT() {
		return amountExcludVAT;
	}
	/**
	 * @param amountExcludVAT the amountExcludVAT to set
	 */
	public void setAmountExcludVAT(double amountExcludVAT) {
		this.amountExcludVAT = amountExcludVAT;
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
	/**
	 * @return the emailSentFlag
	 */
	@Column(name = "EMLSNTFLG")
	public String getEmailSentFlag() {
		return emailSentFlag;
	}
	/**
	 * @param emailSentFlag the emailSentFlag to set
	 */
	public void setEmailSentFlag(String emailSentFlag) {
		this.emailSentFlag = emailSentFlag;
	}
	/**
	 * @return the invoiceRefNumber
	 */
	@Column(name = "INVREFNUM")
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}
	/**
	 * @param invoiceRefNumber the invoiceRefNumber to set
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
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
	 * @return the totalValuationChargeINBillingCurr
	 */
	@Column(name = "NETVALCHGBLGCUR")
	public double getTotalValuationChargeINBillingCurr() {
		return totalValuationChargeINBillingCurr;
	}
	/**
	 * @param totalValuationChargeINBillingCurr the totalValuationChargeINBillingCurr to set
	 */
	public void setTotalValuationChargeINBillingCurr(
			double totalValuationChargeINBillingCurr) {
		this.totalValuationChargeINBillingCurr = totalValuationChargeINBillingCurr;
	}
	/**
	 * @return the gibIdentifier
	 */
	@Column(name = "GIBIDR")
	public String getGibIdentifier() {
		return gibIdentifier;
	}
	/**
	 * @param gibIdentifier the gibIdentifier to set
	 */
	public void setGibIdentifier(String gibIdentifier) {
		this.gibIdentifier = gibIdentifier;
	}
	/**
	 * @return the invoiceDate
	 */
	@Column(name = "INVDAT")
	public Calendar getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(Calendar invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the invoiceGeneratedUser
	 */
	@Column(name = "INVGENUSR")
	public String getInvoiceGeneratedUser() {
		return invoiceGeneratedUser;
	}
	/**
	 * @param invoiceGeneratedUser the invoiceGeneratedUser to set
	 */
	public void setInvoiceGeneratedUser(String invoiceGeneratedUser) {
		this.invoiceGeneratedUser = invoiceGeneratedUser;
	}
	/**
	 * @return the invoicePrintedDate
	 */
	@Column(name = "INVPRTDAT")
	public Calendar getInvoicePrintedDate() {
		return invoicePrintedDate;
	}
	/**
	 * @param invoicePrintedDate the invoicePrintedDate to set
	 */
	public void setInvoicePrintedDate(Calendar invoicePrintedDate) {
		this.invoicePrintedDate = invoicePrintedDate;
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
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
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
	 * @return the interfacedFileName
	 */
	@Column(name = "INTFCDFILNAM")
	public String getInterfacedFileName() {
		return interfacedFileName;
	}
	/**
	 * @param interfacedFileName the interfacedFileName to set
	 */
	
	public void setInterfacedFileName(String interfacedFileName) {
		this.interfacedFileName = interfacedFileName;
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
	 * Finds and returns the CN51s based on the filter criteria
	 * @author a-2049
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws SystemException
	 */
	public static Page<CN51SummaryVO> findAllInvoices
	(CN51SummaryFilterVO cn51SummaryFilterVO)
	throws SystemException{
		constructStaticLog().entering("CN51Summary","findAllInvoices");
		Page<CN51SummaryVO> cn51SummaryVOs = null;
		try {
			cn51SummaryVOs = constructDAO().findAllInvoices(cn51SummaryFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}
		constructStaticLog().exiting("CN51Summary","findAllInvoices");
		return cn51SummaryVOs;
	}

	public static Collection<CN51SummaryVO> findAllInvoicesForPASSFileUpdate
			(CN51SummaryFilterVO cn51SummaryFilterVO)
			throws SystemException{
		Collection<CN51SummaryVO> cn51SummaryVOs = null;

		cn51SummaryVOs = constructDAO().findAllInvoicesForPASSFileUpdate(cn51SummaryFilterVO);

		return cn51SummaryVOs;
	}


	/**
	 * Method to Print InvoicesDetails
	 * @author a-5526
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws SystemException
	 */
	public static Collection<CN51SummaryVO> findAllInvoicesForReport
	(CN51SummaryFilterVO cn51SummaryFilterVO)
	throws SystemException{
		constructStaticLog().entering("CN51Summary","findAllInvoices");
		Collection<CN51SummaryVO> cn51SummaryVOs = null;
		try {
			cn51SummaryVOs = constructDAO().findAllInvoicesForReport(cn51SummaryFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}
		constructStaticLog().exiting("CN51Summary","findAllInvoices");
		return cn51SummaryVOs;
	}

	/**
	 * Finds and returns the  Invoice details
	 * 
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws SystemException
	 */
	public static CN51SummaryVO findGpaBillingInvoiceEnquiryDetails
	(GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
	throws SystemException{

		try {
			return constructDAO().findGpaBillingInvoiceEnquiryDetails(gpaBillingInvoiceEnquiryFilterVO);
		} 
		catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}


	}

	/**
	 * Finds and returns the CN51 and CN66 details
	 * Here splitting of methods will happen for CN51 and CN66
	 * @param cn51CN66FilterVO
	 * @return CN51CN66VO
	 * @throws SystemException
	 */
	public static CN51CN66VO findCN51CN66Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws SystemException{
		log.entering(CLASS_NAME,"findCN51Details");
		CN51CN66VO cn51cn66VO=new CN51CN66VO();
		CN51Details cN51Details=new CN51Details();
		CN66Details cN66Details=new CN66Details();
		/*
		 * find call for CN51Details
		 */
		cn51cn66VO.setCn51DetailsVOs(cN51Details.findCN51Details(cn51CN66FilterVO));
		/*
		 * find call for CN66Details
		 */
		cn51cn66VO.setCn66DetailsVOs(cN66Details.findCN66Details(cn51CN66FilterVO));

		return cn51cn66VO;
	}

	/**
	 * @param  cn51SummaryVO
	 * @return ProformaInvoiceDiffReportVO
	 * @throws SystemException
	 */
	public static Collection<ProformaInvoiceDiffReportVO> generateProformaInvoiceDiffReport(CN51SummaryVO cn51SummaryVO)
	throws SystemException{

		try {
			return constructDAO().generateProformaInvoiceDiffReport(cn51SummaryVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/* ******************************** business methods for the entity ends **************************** */



	/* ************************ common entity methods ****************************** */

	/**
	 * default constructor
	 *
	 */
	public CN51Summary() {

	}

	/**
	 * common constructor
	 * @param cn51SummaryVO
	 * @throws SystemException
	 */
	public CN51Summary( CN51SummaryVO cn51SummaryVO )throws SystemException {
		log.entering(CLASS_NAME,"CN51Summary");
		populateCN51SummaryPK(cn51SummaryVO);
		populateCN51SummaryAttributes(cn51SummaryVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),createException);
		}

		log.exiting(CLASS_NAME,"CN51Summary");
	}

	/**
	 *
	 * @param companyCode
	 * @param invoiceNumber
	 * @param gpaCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static CN51Summary find(String companyCode,
			String invoiceNumber,
			int invSerialNumber,
			String gpaCode)
	throws SystemException ,FinderException {
		CN51SummaryPK entityPK = new CN51SummaryPK(companyCode,invoiceNumber,invSerialNumber,gpaCode);
		return PersistenceController.getEntityManager()
		.find(CN51Summary.class,entityPK);
	}


	/**
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove()
	throws RemoveException,SystemException {
		log.entering(CLASS_NAME,"remove");
		Set<CN51Details> cn51Details = this.getCn51details();
		if(cn51Details != null && cn51Details.size() > 0 ){
			log.log(Log.INFO,"removing all CN51Details first ");
			for(CN51Details cn51Detail : cn51Details){
				cn51Detail.remove();
			}
			log.log(Log.INFO,"removing all CN51Details completed ");
		}
		log.log(Log.INFO," removing the CN51Summary itself ");
		PersistenceController.getEntityManager().remove(this);
		log.exiting(CLASS_NAME,"remove");
	}

	/**
	 * 
	 * @param cn51SummaryVO
	 * @throws SystemException
	 * @throws RemoveException
	 * @throws CreateException
	 * @throws FinderException
	 */
	public void update(CN51SummaryVO cn51SummaryVO)
	throws SystemException,RemoveException,
	CreateException,FinderException {

		log.entering(CLASS_NAME,"update");
		if( OPERATION_FLAG_UPDATE.equals(cn51SummaryVO.getOperationFlag()) ) {
			log.log(Log.INFO," updation of CN51Summary \n ");
			populateCN51SummaryAttributes(cn51SummaryVO);
			log.log(Log.INFO," updation of CN51Summary completed \n");
			if(cn51SummaryVO.getCn51details() != null &&
					cn51SummaryVO.getCn51details().size() > 0 ){
				log.log(Log.FINE," found the CN51DetailsVOs for updating the child details ");
				for(CN51DetailsVO cn51DetailsVO : cn51SummaryVO.getCn51details()){
					if(OPERATION_FLAG_DELETE.equals(cn51DetailsVO.getOperationFlag())){
						CN51Details.find(cn51DetailsVO.getCompanyCode(),
								cn51DetailsVO.getInvoiceNumber(),
								cn51DetailsVO.getGpaCode(),
								cn51DetailsVO.getSequenceNumber(),cn51DetailsVO.getInvSerialNumber())
								.remove();
					}
				}
				for(CN51DetailsVO cn51DetailsVO : cn51SummaryVO.getCn51details()){
					if(OPERATION_FLAG_UPDATE.equals(cn51DetailsVO.getOperationFlag())){
						CN51Details.find(cn51DetailsVO.getCompanyCode(),
								cn51DetailsVO.getInvoiceNumber(),
								cn51DetailsVO.getGpaCode(),
								cn51DetailsVO.getSequenceNumber(),cn51DetailsVO.getInvSerialNumber())
								.update(); // call to be changed
					}
					else if(OPERATION_FLAG_INSERT.equals(cn51DetailsVO.getOperationFlag())){
						new CN51Details(); // call to be changed
					}
				}
				log.log(Log.FINE," updation of CN51Details completed ");
			}

		}

		log.exiting(CLASS_NAME,"update");
	}

	/**
	 *
	 * @param cn51SummaryVO
	 */
	private void populateCN51SummaryPK(CN51SummaryVO cn51SummaryVO){
		log.entering(CLASS_NAME,"populateCN51SummaryPK");
		CN51SummaryPK pkForCreation = new CN51SummaryPK();
		pkForCreation.setCompanyCode(   cn51SummaryVO.getCompanyCode());
		pkForCreation.setGpaCode(   cn51SummaryVO.getGpaCode());
		pkForCreation.setInvoiceNumber(   cn51SummaryVO.getInvoiceNumber());
		this.setCn51SummaryPK(pkForCreation);
		log.exiting(CLASS_NAME,"populateCN51SummaryPK");
	}


	/**
	 *
	 * @param cn51SummaryVO
	 */
	private void populateCN51SummaryAttributes(CN51SummaryVO cn51SummaryVO){
		log.entering(CLASS_NAME,"populateCN51SummaryAttributes");
		//this.setBilledDate(cn51SummaryVO.getBilledDate());
		this.setBillingCurrencyCode(cn51SummaryVO.getBillingCurrencyCode());
		//this.setBillingPeriod(cn51SummaryVO.getBillingPeriod());
		if(cn51SummaryVO.getTotalAmountInBillingCurrency()!=null) {
			this.setTotalAmountInBillingCurr(cn51SummaryVO.getTotalAmountInBillingCurrency().getRoundedAmount());
		}

		if(cn51SummaryVO.getTotalAmountInContractCurrency()!=null) {
			this.setTotalAmountInContractCurr(cn51SummaryVO.getTotalAmountInContractCurrency().getRoundedAmount());
		}

		this.setContractCurrencyCode(cn51SummaryVO.getContractCurrencyCode());
		//this.setSettledAmount(cn51SummaryVO.getSe)
		log.exiting(CLASS_NAME,"populateCN51SummaryAttributes");
	}

	/**
	 * method for calling up the DAO for the submodule
	 * @author a-2049
	 * @return queryDAO
	 * @throws SystemException
	 */
	private static MRAGPABillingDAO constructDAO()
	throws SystemException {
		MRAGPABillingDAO queryDAO =null;
		try {
			queryDAO = (MRAGPABillingDAO)PersistenceController
			.getEntityManager()
			.getQueryDAO(MODULE_NAME);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}

		return queryDAO;
	}

	private static Log constructStaticLog(){
		return LogFactory.getLogger("MRA:GPABILLING");
	}

	/* ************************ common entity methods ends ****************************** */


	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO  invoiceLovVO)
	throws SystemException{

		try{
			return constructDAO().findInvoiceLov(invoiceLovVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN66DetailsVO> generateCN66Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateCN66Report(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}

	}
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN51DetailsVO> generateCN51Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateCN51Report(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}

	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static InvoiceDetailsReportVO generateInvoiceReport(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateInvoiceReport(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	   

	/**
	 * @author A-10383
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static InvoiceDetailsReportVO generateInvoiceReportSQ(CN51CN66FilterVO cN51CN66FilterVO)
			throws SystemException{
				try{
					return constructDAO().generateInvoiceReportSQ(cN51CN66FilterVO);
				}
				catch(PersistenceException persistenceException){
					throw new SystemException(persistenceException.getMessage(),persistenceException);
				}
			}
	/**
	 * @author A-2408
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<BillingSummaryDetailsVO> findBillingSummaryDetailsForPrint(BillingSummaryDetailsFilterVO filterVO)
	throws SystemException{

		try{

			return constructDAO().findBillingSummaryDetailsForPrint(filterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}


	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN51DetailsVO> findCN51DetailsForPrint(CN51DetailsPrintFilterVO filterVO)
	throws SystemException{
		try{

			return constructDAO().findCN51DetailsForPrint(filterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN66DetailsPrintVO> findCN66DetailsForPrint(CN66DetailsPrintFilterVO filterVO)
	throws SystemException{
		try{

			return constructDAO().findCN66DetailsForPrint(filterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 */
	/*public static Collection<InvoiceSettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)throws SystemException{

			try {
				return constructDAO().findSettlementDetails(invoiceSettlementFilterVO);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage());

			} 


	}*/


	/**
	 * @return the cN66Details
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "INVNUM", referencedColumnName = "INVNUM", insertable=false, updatable=false),
		@JoinColumn(name = "INVSERNUM", referencedColumnName = "INVSERNUM", insertable=false, updatable=false),
		@JoinColumn(name = "GPACOD", referencedColumnName = "GPACOD", insertable=false, updatable=false)})
		public Set<CN66Details> getCN66Details() {
		return cN66Details;
	}


	/**
	 * @param details the cN66Details to set
	 */
	public void setCN66Details(Set<CN66Details> details) {
		cN66Details = details;
	}

	/**
	 * Added by A-2565 Meenu for GPA billing accounting
	 * @param summaryVO
	 * @return
	 * @throws SystemException
	 */
	public Object triggerGPABillingAccounting(CN51SummaryVO summaryVO) throws SystemException {
		log.entering("cN51Summary", "triggerGPABillingAccounting");

		try{//triggering GPA Billing acounting

			return constructDAO().triggerGPABillingAccounting(summaryVO);

		}catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * @author a-3447
	 * @param companyCode
	 * @param airlineIdentifier
	 * @return
	 * @throws SystemException
	 */
	public static AirlineVO findAirlineAddress(String companyCode, int airlineIdentifier)  throws SystemException{

		try {
			return constructDAO().findAirlineAddress(companyCode,airlineIdentifier);	
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 


	}

	/**
	 * 
	 * @param settledAmount
	 */
	public void updateSettlementStatus(double settledAmount,InvoiceSettlementVO invoiceSettlementVO,int overrideRounding) {
		double toleranceAmnt=0.0;
		double dueAmt;
		dueAmt=getScaledValue(this.totalAmountInBillingCurr-settledAmount, overrideRounding);
		if(settledAmount!=this.totalAmountInBillingCurr){
			toleranceAmnt=findToleranceAmnt(invoiceSettlementVO, overrideRounding);
		}	
		if((settledAmount==this.totalAmountInBillingCurr)||(Math.abs(dueAmt)<=toleranceAmnt)){
			this.settlementStatus=SETTLEMENT_STATUS_SETTLED;			
			if(dueAmt >=0) {
			settledAmount= settledAmount+toleranceAmnt;}
			else {
			settledAmount= settledAmount-toleranceAmnt;	}
		}
		else {
			if(settledAmount==0){
				this.settlementStatus=SETTLEMENT_STATUS_PENDING;
			//this.invoiceStatus=SETTLEMENT_STATUS_PENDING
			}
			else if(settledAmount>this.totalAmountInBillingCurr){//added by a-7871 for ICRD-235799 for overpaid invoice
					this.settlementStatus=SETTLEMENT_STATUS_OVERPIAD;
			}
		else{			
				this.settlementStatus=SETTLEMENT_STATUS_DIFFERENCE;	
		}					
		}
		this.settlementAmount= settledAmount;
		this.dueAmount=this.totalAmountInBillingCurr-this.settlementAmount;
	}
	private double findToleranceAmnt(InvoiceSettlementVO invoiceSettlementVO,int overrideRounding) {
		double toleranceAmnt=0.0;
		if(invoiceSettlementVO.getSettlementLevel().equals("V")) {
			double setAmnt;
			if(invoiceSettlementVO.getTolerancePercentage()>0) {
				 setAmnt=(totalAmountInBillingCurr*invoiceSettlementVO.getTolerancePercentage())/100; 
				 setAmnt= getScaledValue(setAmnt, overrideRounding);
			}
			else {
				setAmnt=invoiceSettlementVO.getSettlementValue();
			}
			toleranceAmnt=setAmnt>=invoiceSettlementVO.getSettlementMaxValue()?
					invoiceSettlementVO.getSettlementMaxValue():setAmnt;
		}
		return toleranceAmnt;
	}
	private double getScaledValue(double setAmnt, int overrideRounding) {
		 BigDecimal bigDecimal = BigDecimal.valueOf(setAmnt);
		 return bigDecimal.setScale(overrideRounding,
				 RoundingMode.HALF_EVEN).doubleValue();
	}
	



	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static InvoiceDetailsReportVO generateInvoiceReportTK(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateInvoiceReportTK(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	
	/**
	 *  Method for updating the updateInvoiceReference number for THY invoice printing
	 * 	Method		: CN51Summary.updateInvoiceReference
	 *	Added by 	: A-5273 on Mar 20, 2014
	 * 	@param aoInvoiceReportDetailsVO
	 * 	@throws SystemException
	 *  void
	 * @throws FinderException 
	 */
	public void updateInvoiceReference(AOInvoiceReportDetailsVO aoInvoiceReportDetailsVO)
			 throws SystemException, FinderException{
		log.entering(CLASS_NAME, "updateInvoiceReference");
		CN51Summary cn51Summary = find(aoInvoiceReportDetailsVO.getCompanyCode(), 
				aoInvoiceReportDetailsVO.getInvoiceNumber(),
				aoInvoiceReportDetailsVO.getInvoiceSerialNumber(),
				aoInvoiceReportDetailsVO.getGpaCode());
		cn51Summary.setInvoiceRefNumber(aoInvoiceReportDetailsVO.getInvoiceReferenceNumber());
		log.log(Log.FINE, "CN51Summary Entity Update Invoice Reference ---->>>"
				,aoInvoiceReportDetailsVO.getInvoiceReferenceNumber()
				," for Invoice--> ",aoInvoiceReportDetailsVO.getInvoiceNumber());
		//Added by A-5791 for ICRD-68944 -updating the gibIdentifier for Einvoicing starts here 
		cn51Summary.setGibIdentifier(aoInvoiceReportDetailsVO.getGibIdr());
		log.log(Log.FINE, "CN51Summary Entity Update GibIdr ---->>>"
				,aoInvoiceReportDetailsVO.getGibIdr()
				," for Invoice--> ",aoInvoiceReportDetailsVO.getInvoiceNumber());
		//Added by A-5791 for ICRD-68944 -updating the gibIdentifier for Einvoicing ends here
		// Added by A-6782 for ICRD-205428 starts
		cn51Summary.setInvoicePrintedDate(aoInvoiceReportDetailsVO
				.getInvoicePrintDate());
		// Added by A-6782 for ICRD-205428 ends
		log.exiting(CLASS_NAME, "updateInvoiceReference");
	 }

	/**
	 * @author A-7794 
	 * as part of ICRD-234354
	 * @param CN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN66DetailsVO> generateCN66ReportForKE(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateCN66ReportForKE(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}

	}
	
	
	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 */
	public static InvoiceLovVO findInvoiceNumber(InvoiceLovVO  invoiceLovVO)
	throws SystemException{

		try{
			return constructDAO().findInvoiceNumber(invoiceLovVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * 	Method		:	CN51Summary.findInvoicesforPASS
	 *	Added by 	:	A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<GPAInvoiceVO>
	 */
	public static Collection<GPAInvoiceVO> findInvoicesforPASS(GeneratePASSFilterVO passFilterVO)
	throws SystemException{
		log.entering(CLASS_NAME, "findInvoicesforPASS");
		try{
			return constructDAO().findInvoicesforPASS(passFilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * 	Method		:	CN51Summary.getSequenceNumberforPASSFile
	 *	Added by 	:	A-4809 on 16-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	int
	 */
	public static String  getSequenceNumberforPASSFile(GPAInvoiceVO invoiceVO) throws SystemException{
		log.entering(CLASS_NAME, "getSequenceNumberforPASSFile");	
		try{
			return constructDAO().getSequenceNumberforPASSFile(invoiceVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * 
	 * 	Method		:	CN51Summary.updateInterfaceDetails
	 *	Added by 	:	A-8061 on 20-May-2021
	 * 	Used for 	:
	 *	Parameters	:	@param fileGenerateVO
	 *	Parameters	:	@param invoiceList
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public static void updateInterfaceDetails(FileGenerateVO fileGenerateVO,Collection<CN51SummaryVO> invoiceList) throws SystemException {
		
		if(invoiceList!=null && !invoiceList.isEmpty()){
			for(CN51SummaryVO summaryVO : invoiceList){
				try {
					CN51Summary summary =	CN51Summary.find(summaryVO.getCompanyCode(), summaryVO.getInvoiceNumber(), summaryVO.getInvSerialNumber(), summaryVO.getGpaCode());
					if(summary.getInterfacedFileName()!=null){
						summary.setInterfacedFileName(new StringBuilder(summary.getInterfacedFileName()).append(",").append(fileGenerateVO.getFileName()).toString());
					}else{
						summary.setInterfacedFileName(fileGenerateVO.getFileName());
					}
					summary.setInterfacedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).toCalendar());
				} catch (FinderException exception) {
					throw new SystemException(exception.getMessage());
				}
			}
		}
	}

	public static Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws SystemException {
		return constructDAO().findPASSFileNames(fileNameLovVO);
	}
	/**
	 * @author A-10383
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<CN51DetailsVO> generateCN51ReportSQ(CN51CN66FilterVO cN51CN66FilterVO)
			throws SystemException{
				try{
					return constructDAO().generateCN51ReportSQ(cN51CN66FilterVO);
				}
				catch(PersistenceException persistenceException){
					throw new SystemException(persistenceException.getMessage(),persistenceException);
				}
			}



}

