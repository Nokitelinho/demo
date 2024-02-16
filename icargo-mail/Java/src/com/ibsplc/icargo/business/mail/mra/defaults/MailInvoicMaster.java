/*
 * MailInvoicMaster.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicKeyLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicLocationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicMasterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicMonetaryAmtVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicPackageVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicPriceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicProductDtlVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicTotalPaymentVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicTransportationDtlVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */
@Entity
@Table(name = "MTKINVMST")
@Staleable
@Deprecated
public class MailInvoicMaster {

	private MailInvoicMasterPK mailInvoicMasterPK;

	private String messageReferenceNumber;

	private String messageTypeIdentifier;

	private String messageVersionNumber;

	private String messageReleaseNumber;

	private String controllingAgency;

	private String associationAssignedCode;

	private String syntaxIdentifier;

	private int syntaxVersion;

	private String recipientIdentifier;

	private Calendar preparationDate;

	private String interchangeControlReference;

	private int testIndicator;

	private String messageName;

	private String messageDocumentNumber;

	private String messageRevisionNumber;

	private String paymentType;

	private Calendar invoicReceivedDate;

	private Calendar consignmentCompletionDate;

	private Calendar criticalEntryDate;

	private Calendar scheduleInvoiceDate;

	private Calendar enteredDate;

	private String contractType;

	private String contractNumber;

	private String carrierCode;

	private String carrierName;

	private String reconciliationStatus;

	private String senderCodeQualifier;

	private String recipientCodeQualifier;

	private Set<MailInvoicProductDtl> mailInvoicProductDetails;

	private Set<MailInvoicMonetaryAmt> mailInvoicMonetaryDetails;

	private Set<MailInvoicPrice> mailInvoicPriceDetails;

	private Set<MailInvoicPackage> mailInvoicPackageDetails;

	private Set<MailInvoicLocation> mailInvoicLocationDetails;

	private Set<MailInvoicTransportationDtl> mailInvoicTransportationDetails;

	private MailInvoicTotalPayment mailInvoicTotalPayment;

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * default contructor
	 */
	public MailInvoicMaster() {

	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	public MailInvoicMaster(MailInvoicMasterVO masterVO) throws SystemException {
		staticLogger().entering("MailInvoic Master", "Master Contructor----->");
		MailInvoicMasterPK masterPK = new MailInvoicMasterPK();
		masterPK.setCompanyCode(masterVO.getCompanyCode());
		masterPK.setInvoiceKey(masterVO.getInvoiceKey());
		masterPK.setPoaCode(masterVO.getPoaCode());
		this.setMailInvoicMasterPK(masterPK);
		populateAttributes(masterVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		populateProductDetails(masterVO);
		populateMonetaryDetails(masterVO);
		populatePriceDetails(masterVO);
		populatePackageDetails(masterVO);
		populateLocationDetails(masterVO);
		populateTransportationDetails(masterVO);
		populateTotalPayment(masterVO);
		// to do complete constructor
	}

	/**
	 * @return Returns the associationAssignedCode.
	 */
	@Column(name = "ASCASNCOD")
	public String getAssociationAssignedCode() {
		return associationAssignedCode;
	}

	/**
	 * @param associationAssignedCode
	 *            The associationAssignedCode to set.
	 */
	public void setAssociationAssignedCode(String associationAssignedCode) {
		this.associationAssignedCode = associationAssignedCode;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	@Column(name = "CARCOD")
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
	 * @return Returns the carrierName.
	 */
	@Column(name = "CARNAM")
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName
	 *            The carrierName to set.
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return Returns the consignmentCompletionDate.
	 */
	@Column(name = "CSGCMPDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}

	/**
	 * @param consignmentCompletionDate
	 *            The consignmentCompletionDate to set.
	 */
	public void setConsignmentCompletionDate(Calendar consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}

	/**
	 * @return Returns the contractNumber.
	 */
	@Column(name = "CNTNUM")
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber
	 *            The contractNumber to set.
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return Returns the contractType.
	 */
	@Column(name = "CNTTYP")
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType
	 *            The contractType to set.
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return Returns the controllingAgency.
	 */
	@Column(name = "CTLAGC")
	public String getControllingAgency() {
		return controllingAgency;
	}

	/**
	 * @param controllingAgency
	 *            The controllingAgency to set.
	 */
	public void setControllingAgency(String controllingAgency) {
		this.controllingAgency = controllingAgency;
	}

	/**
	 * @return Returns the criticalEntryDate.
	 */
	@Column(name = "CRTENTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getCriticalEntryDate() {
		return criticalEntryDate;
	}

	/**
	 * @param criticalEntryDate
	 *            The criticalEntryDate to set.
	 */
	public void setCriticalEntryDate(Calendar criticalEntryDate) {
		this.criticalEntryDate = criticalEntryDate;
	}

	/**
	 * @return Returns the enteredDate.
	 */
	@Column(name = "ENTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getEnteredDate() {
		return enteredDate;
	}

	/**
	 * @param enteredDate
	 *            The enteredDate to set.
	 */
	public void setEnteredDate(Calendar enteredDate) {
		this.enteredDate = enteredDate;
	}

	/**
	 * @return Returns the interchangeControlReference.
	 */
	@Column(name = "INTCTLREF")
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference
	 *            The interchangeControlReference to set.
	 */
	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/**
	 * @return Returns the invoicReceivedDate.
	 */
	@Column(name = "INVRCVDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getInvoicReceivedDate() {
		return invoicReceivedDate;
	}

	/**
	 * @param invoicReceivedDate
	 *            The invoicReceivedDate to set.
	 */
	public void setInvoicReceivedDate(Calendar invoicReceivedDate) {
		this.invoicReceivedDate = invoicReceivedDate;
	}

	/**
	 * @return Returns the mailInvoicMasterPK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "invoiceKey", column = @Column(name = "INVKEY")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")) })
	public MailInvoicMasterPK getMailInvoicMasterPK() {
		return mailInvoicMasterPK;
	}

	/**
	 * @param mailInvoicMasterPK
	 *            The mailInvoicMasterPK to set.
	 */
	public void setMailInvoicMasterPK(MailInvoicMasterPK mailInvoicMasterPK) {
		this.mailInvoicMasterPK = mailInvoicMasterPK;
	}

	/**
	 * @return Returns the messageDocumentNumber.
	 */
	@Column(name = "MSGDOCNUM")
	public String getMessageDocumentNumber() {
		return messageDocumentNumber;
	}

	/**
	 * @param messageDocumentNumber
	 *            The messageDocumentNumber to set.
	 */
	public void setMessageDocumentNumber(String messageDocumentNumber) {
		this.messageDocumentNumber = messageDocumentNumber;
	}

	/**
	 * @return Returns the messageName.
	 */
	@Column(name = "MSGNAM")
	public String getMessageName() {
		return messageName;
	}

	/**
	 * @param messageName
	 *            The messageName to set.
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	/**
	 * @return Returns the messageReferenceNumber.
	 */
	@Column(name = "MSGREFNUM")
	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	/**
	 * @param messageReferenceNumber
	 *            The messageReferenceNumber to set.
	 */
	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	/**
	 * @return Returns the messageReleaseNumber.
	 */
	@Column(name = "MSGRLSNUM")
	public String getMessageReleaseNumber() {
		return messageReleaseNumber;
	}

	/**
	 * @param messageReleaseNumber
	 *            The messageReleaseNumber to set.
	 */
	public void setMessageReleaseNumber(String messageReleaseNumber) {
		this.messageReleaseNumber = messageReleaseNumber;
	}

	/**
	 * @return Returns the messageRevisionNumber.
	 */
	@Column(name = "MSGREVNUM")
	public String getMessageRevisionNumber() {
		return messageRevisionNumber;
	}

	/**
	 * @param messageRevisionNumber
	 *            The messageRevisionNumber to set.
	 */
	public void setMessageRevisionNumber(String messageRevisionNumber) {
		this.messageRevisionNumber = messageRevisionNumber;
	}

	/**
	 * @return Returns the messageTypeIdentifier.
	 */
	@Column(name = "MSGTYPIDR")
	public String getMessageTypeIdentifier() {
		return messageTypeIdentifier;
	}

	/**
	 * @param messageTypeIdentifier
	 *            The messageTypeIdentifier to set.
	 */
	public void setMessageTypeIdentifier(String messageTypeIdentifier) {
		this.messageTypeIdentifier = messageTypeIdentifier;
	}

	/**
	 * @return Returns the messageVersionNumber.
	 */
	@Column(name = "MSGVERNUM")
	public String getMessageVersionNumber() {
		return messageVersionNumber;
	}

	/**
	 * @param messageVersionNumber
	 *            The messageVersionNumber to set.
	 */
	public void setMessageVersionNumber(String messageVersionNumber) {
		this.messageVersionNumber = messageVersionNumber;
	}

	/**
	 * @return Returns the paymentType.
	 */
	@Column(name = "PAYTYP")
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            The paymentType to set.
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return Returns the preparationDate.
	 */
	@Column(name = "PRPDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getPreparationDate() {
		return preparationDate;
	}

	/**
	 * @param preparationDate
	 *            The preparationDate to set.
	 */
	public void setPreparationDate(Calendar preparationDate) {
		this.preparationDate = preparationDate;
	}

	/**
	 * @return Returns the recipientCodeQualifier.
	 */
	@Column(name = "RCTCODQFR")
	public String getRecipientCodeQualifier() {
		return recipientCodeQualifier;
	}

	/**
	 * @param recipientCodeQualifier
	 *            The recipientCodeQualifier to set.
	 */
	public void setRecipientCodeQualifier(String recipientCodeQualifier) {
		this.recipientCodeQualifier = recipientCodeQualifier;
	}

	/**
	 * @return Returns the recipientIdentifier.
	 */
	@Column(name = "RCTIDR")
	public String getRecipientIdentifier() {
		return recipientIdentifier;
	}

	/**
	 * @param recipientIdentifier
	 *            The recipientIdentifier to set.
	 */
	public void setRecipientIdentifier(String recipientIdentifier) {
		this.recipientIdentifier = recipientIdentifier;
	}

	/**
	 * @return Returns the reconciliationStatus.
	 */
	@Column(name = "INVADVRCLSTA")
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	/**
	 * @param reconciliationStatus
	 *            The reconciliationStatus to set.
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	/**
	 * @return Returns the scheduleInvoiceDate.
	 */
	@Column(name = "SCHINVDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getScheduleInvoiceDate() {
		return scheduleInvoiceDate;
	}

	/**
	 * @param scheduleInvoiceDate
	 *            The scheduleInvoiceDate to set.
	 */
	public void setScheduleInvoiceDate(Calendar scheduleInvoiceDate) {
		this.scheduleInvoiceDate = scheduleInvoiceDate;
	}

	/**
	 * @return Returns the senderCodeQualifier.
	 */
	@Column(name = "SDRCODQFR")
	public String getSenderCodeQualifier() {
		return senderCodeQualifier;
	}

	/**
	 * @param senderCodeQualifier
	 *            The senderCodeQualifier to set.
	 */
	public void setSenderCodeQualifier(String senderCodeQualifier) {
		this.senderCodeQualifier = senderCodeQualifier;
	}

	/**
	 * @return Returns the syntaxIdentifier.
	 */
	@Column(name = "STXIDR")
	public String getSyntaxIdentifier() {
		return syntaxIdentifier;
	}

	/**
	 * @param syntaxIdentifier
	 *            The syntaxIdentifier to set.
	 */
	public void setSyntaxIdentifier(String syntaxIdentifier) {
		this.syntaxIdentifier = syntaxIdentifier;
	}

	/**
	 * @return Returns the syntaxVersion.
	 */
	@Column(name = "STXVER")
	public int getSyntaxVersion() {
		return syntaxVersion;
	}

	/**
	 * @param syntaxVersion
	 *            The syntaxVersion to set.
	 */
	public void setSyntaxVersion(int syntaxVersion) {
		this.syntaxVersion = syntaxVersion;
	}

	/**
	 * @return Returns the testIndicator.
	 */
	@Column(name = "TSTIND")
	public int getTestIndicator() {
		return testIndicator;
	}

	/**
	 * @param testIndicator
	 *            The testIndicator to set.
	 */
	public void setTestIndicator(int testIndicator) {
		this.testIndicator = testIndicator;
	}

	/**
	 * @return Returns the mailInvoicLocationDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicLocation> getMailInvoicLocationDetails() {
		return mailInvoicLocationDetails;
	}

	/**
	 * @param mailInvoicLocationDetails
	 *            The mailInvoicLocationDetails to set.
	 */

	public void setMailInvoicLocationDetails(
			Set<MailInvoicLocation> mailInvoicLocationDetails) {
		this.mailInvoicLocationDetails = mailInvoicLocationDetails;
	}

	/**
	 * @return Returns the mailInvoicMonetaryDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicMonetaryAmt> getMailInvoicMonetaryDetails() {
		return mailInvoicMonetaryDetails;
	}

	/**
	 * @param mailInvoicMonetaryDetails
	 *            The mailInvoicMonetaryDetails to set.
	 */

	public void setMailInvoicMonetaryDetails(
			Set<MailInvoicMonetaryAmt> mailInvoicMonetaryDetails) {
		this.mailInvoicMonetaryDetails = mailInvoicMonetaryDetails;
	}

	/**
	 * @return Returns the mailInvoicPackageDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicPackage> getMailInvoicPackageDetails() {
		return mailInvoicPackageDetails;
	}

	/**
	 * @param mailInvoicPackageDetails
	 *            The mailInvoicPackageDetails to set.
	 */
	public void setMailInvoicPackageDetails(
			Set<MailInvoicPackage> mailInvoicPackageDetails) {
		this.mailInvoicPackageDetails = mailInvoicPackageDetails;
	}

	/**
	 * @return Returns the mailInvoicPriceDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicPrice> getMailInvoicPriceDetails() {
		return mailInvoicPriceDetails;
	}

	/**
	 * @param mailInvoicPriceDetails
	 *            The mailInvoicPriceDetails to set.
	 */
	public void setMailInvoicPriceDetails(
			Set<MailInvoicPrice> mailInvoicPriceDetails) {
		this.mailInvoicPriceDetails = mailInvoicPriceDetails;
	}

	/**
	 * @return Returns the mailInvoicProductDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicProductDtl> getMailInvoicProductDetails() {
		return mailInvoicProductDetails;
	}

	/**
	 * @param mailInvoicProductDetails
	 *            The mailInvoicProductDetails to set.
	 */
	public void setMailInvoicProductDetails(
			Set<MailInvoicProductDtl> mailInvoicProductDetails) {
		this.mailInvoicProductDetails = mailInvoicProductDetails;
	}

	/**
	 * @return Returns the mailInvoicTotalPayment.
	 */		
	@OneToOne(cascade=CascadeType.ALL)	
	@PrimaryKeyJoinColumns({
	        	@PrimaryKeyJoinColumn(name="CMPCOD",referencedColumnName="CMPCOD"),
	        	@PrimaryKeyJoinColumn(name="INVKEY",referencedColumnName="INVKEY"),
	        	@PrimaryKeyJoinColumn(name="POACOD",referencedColumnName="POACOD")
	    })
	public MailInvoicTotalPayment getMailInvoicTotalPayment() {
		return mailInvoicTotalPayment;
	}	

	/**
	 * @param mailInvoicTotalPayment
	 *            The mailInvoicTotalPayment to set.
	 */
	public void setMailInvoicTotalPayment(
			MailInvoicTotalPayment mailInvoicTotalPayment) {
		this.mailInvoicTotalPayment = mailInvoicTotalPayment;
	}

	/**
	 * @return Returns the mailInvoicTransportationDetails.
	 */

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "INVKEY", referencedColumnName = "INVKEY", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
	public Set<MailInvoicTransportationDtl> getMailInvoicTransportationDetails() {
		return mailInvoicTransportationDetails;
	}

	/**
	 * @param mailInvoicTransportationDetails
	 *            The mailInvoicTransportationDetails to set.
	 */
	public void setMailInvoicTransportationDetails(
			Set<MailInvoicTransportationDtl> mailInvoicTransportationDetails) {
		this.mailInvoicTransportationDetails = mailInvoicTransportationDetails;
	}

	/**
	 * @param masterVO
	 */
	private void populateAttributes(MailInvoicMasterVO masterVO) {
		staticLogger().entering("MailInvoic Master",
				"populate Attributes----->");
		this.setMessageReferenceNumber(masterVO.getMessageReferenceNumber());
		this.setMessageTypeIdentifier(masterVO.getMessageTypeIdentifier());
		this.setMessageVersionNumber(masterVO.getMessageVersionNumber());
		this.setMessageReleaseNumber(masterVO.getMessageReleaseNumber());
		this.setControllingAgency(masterVO.getControllingAgency());
		this.setAssociationAssignedCode(masterVO.getAssociationAssignedCode());
		this.setSyntaxIdentifier(masterVO.getSyntaxIdentifier());
		this.setSyntaxVersion(masterVO.getSyntaxVersion());
		this.setRecipientIdentifier(masterVO.getRecipientIdentifier());
		this.setPreparationDate(masterVO.getPreparationDate());
		this.setInterchangeControlReference(masterVO
				.getInterchangeControlReference());
		this.setTestIndicator(masterVO.getTestIndicator());
		this.setMessageName(masterVO.getMessageName());
		this.setMessageDocumentNumber(masterVO.getMessageDocumentNumber());
		this.setMessageRevisionNumber(masterVO.getMessageRevisionNumber());
		this.setPaymentType(masterVO.getPaymentType());
		this.setInvoicReceivedDate(masterVO.getInvoicReceivedDate());
		this.setConsignmentCompletionDate(masterVO
				.getConsignmentCompletionDate());
		this.setCriticalEntryDate(masterVO.getCriticalEntryDate());
		this.setScheduleInvoiceDate(masterVO.getScheduleInvoiceDate());
		this.setEnteredDate(masterVO.getEnteredDate());
		this.setContractType(masterVO.getContractType());
		this.setContractNumber(masterVO.getContractNumber());
		this.setCarrierCode(masterVO.getCarrierCode());
		this.setCarrierName(masterVO.getCarrierName());
		this.setReconciliationStatus(masterVO.getReconciliationStatus());
		this.setSenderCodeQualifier(masterVO.getSenderCodeQualifier());
		this.setRecipientCodeQualifier(masterVO.getRecipientCodeQualifier());
	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populateProductDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Product Details----->");
		Collection<MailInvoicProductDtlVO> productDtls = masterVO
				.getMailInvoicProductDetails();
		if (productDtls != null && productDtls.size() > 0) {
			for (MailInvoicProductDtlVO vo : productDtls) {
				MailInvoicProductDtl mailInvoicProductDtl = new MailInvoicProductDtl(
						vo);
				if (getMailInvoicProductDetails() == null) {
					setMailInvoicProductDetails(new HashSet<MailInvoicProductDtl>());
				}
				getMailInvoicProductDetails().add(mailInvoicProductDtl);
			}
		}

	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populateMonetaryDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Monataryt----->");
		Collection<MailInvoicMonetaryAmtVO> monetaryDtls = masterVO
				.getMailInvoicMonetaryDetails();
		if (monetaryDtls != null && monetaryDtls.size() > 0) {
			for (MailInvoicMonetaryAmtVO vo : monetaryDtls) {
				MailInvoicMonetaryAmt mailInvoicMonetaryAmt = new MailInvoicMonetaryAmt(
						vo);
				if (getMailInvoicMonetaryDetails() == null) {
					setMailInvoicMonetaryDetails(new HashSet<MailInvoicMonetaryAmt>());
				}
				getMailInvoicMonetaryDetails().add(mailInvoicMonetaryAmt);
			}
		}
	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populatePriceDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Price----->");
		Collection<MailInvoicPriceVO> priceDetails = masterVO
				.getMailInvoicPriceDetails();
		if (priceDetails != null && priceDetails.size() > 0) {
			for (MailInvoicPriceVO vo : priceDetails) {
				MailInvoicPrice mailInvoicPrice = new MailInvoicPrice(vo);
				if (getMailInvoicPriceDetails() == null) {
					setMailInvoicPriceDetails(new HashSet<MailInvoicPrice>());
				}
				getMailInvoicPriceDetails().add(mailInvoicPrice);
			}
		}
	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populatePackageDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Package----->");
		Collection<MailInvoicPackageVO> packageDetails = masterVO
				.getMailInvoicPackageDetails();
		if (packageDetails != null && packageDetails.size() > 0) {
			for (MailInvoicPackageVO vo : packageDetails) {
				MailInvoicPackage mailInvoicPackage = new MailInvoicPackage(vo);

				if (getMailInvoicPackageDetails() == null) {
					setMailInvoicPackageDetails(new HashSet<MailInvoicPackage>());
				}
				getMailInvoicPackageDetails().add(mailInvoicPackage);
			}
		}

	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populateLocationDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Location----->");
		Collection<MailInvoicLocationVO> locationDetails = masterVO
				.getMailInvoicLocationDetails();

		if (locationDetails != null && locationDetails.size() > 0) {
			for (MailInvoicLocationVO vo : locationDetails) {
				MailInvoicLocation mailInvoicLocation = new MailInvoicLocation(
						vo);

				if (getMailInvoicLocationDetails() == null) {
					setMailInvoicLocationDetails(new HashSet<MailInvoicLocation>());
				}
				getMailInvoicLocationDetails().add(mailInvoicLocation);
			}
		}
	}

	/**
	 * @param masterVO
	 * @throws SystemException
	 */
	private void populateTransportationDetails(MailInvoicMasterVO masterVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Transport Details----->");
		Collection<MailInvoicTransportationDtlVO> transportDetails = masterVO
				.getMailInvoicTransportationDetails();

		if (transportDetails != null && transportDetails.size() > 0) {
			for (MailInvoicTransportationDtlVO vo : transportDetails) {
				new MailInvoicTransportationDtl(vo);
				/*
				 * if(getMailInvoicTransportationDetails() == null){
				 * 
				 * setMailInvoicTransportationDetails(new HashSet<MailInvoicTransportationDtl>()); }
				 * getMailInvoicTransportationDetails().add(mailInvoicTransportationDtl);
				 */
			}
		}
	}

	/**
	 * @param masterVO
	 */
	private void populateTotalPayment(MailInvoicMasterVO masterVO) {
		staticLogger().entering("MailInvoic Master", "TOTAL Payment----->");
		MailInvoicTotalPaymentVO mailInvoicTotalPaymentVO = masterVO
				.getMailInvoicTotalPayment();

		if (mailInvoicTotalPaymentVO != null) {
			setMailInvoicTotalPayment(new MailInvoicTotalPayment(
					mailInvoicTotalPaymentVO));
		}
	}

	/**
	 * @author a-2270
	 * @param companyCode
	 * @param invoiceNumber
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo)
			throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults"))
					.findInvoicEnquiryDetails(invoiceEnquiryFilterVo);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}

	}

	/**
	 * @return
	 */
	private static Log staticLogger() {
		return LogFactory.getLogger(MODULE_NAME);
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static void importToReconcile(String companyCode)
			throws SystemException {
		try {
			MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults")).importToReconcile(
					companyCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static void reconcileProcess(String companyCode)
			throws SystemException {
		try {
			MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults")).reconcileProcess(
					companyCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * @param companyCode
	 * @param invoicKey
	 * @param poaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey, String poaCode, int pageNumber)
			throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults")).findInvoicKeyLov(
					companyCode, invoicKey, poaCode, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	
	
	
	/**
	 * added by Meenu for USPS accounting while processing Invoic ADV message
	 * @param masterVO
	 * @throws SystemException
	 */
	public void triggerUSPSAccounting(MailInvoicMasterVO masterVO) throws SystemException{
			
			try{//triggering USPS acounting
			
			 MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO("mail.mra.defaults")).triggerUSPSAccounting(masterVO);
			
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		
	}
	
	/**
	 * @param masterVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicMaster find(MailInvoicMasterVO masterVO)
	throws SystemException,FinderException{
		staticLogger().entering("MailInvoic Master", "Master find----->");
		MailInvoicMasterPK mailInvoicMstPK = new MailInvoicMasterPK();
		mailInvoicMstPK.setCompanyCode(masterVO.getCompanyCode());
		mailInvoicMstPK.setInvoiceKey(masterVO.getInvoiceKey());
		mailInvoicMstPK.setPoaCode(masterVO.getPoaCode());
		staticLogger().entering("MailInvoic Master", "Master find----mailInvoicMstPK--->"+mailInvoicMstPK);
		return PersistenceController.getEntityManager().find(
				MailInvoicMaster.class, mailInvoicMstPK);
	}
}