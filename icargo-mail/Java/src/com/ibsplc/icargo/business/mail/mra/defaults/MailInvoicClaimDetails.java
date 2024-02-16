/*
 * MailInvoicClaimDetails.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicMessageVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 * 
 */
@Entity
@Table(name = "MTKINVRCLDTL")
@Staleable
@Deprecated
public class MailInvoicClaimDetails {

	private static final String MODULE_NAME = "mail.mra.defaults";

	private static final String CLASS_NAME = "MailInvoicClaimDetails";

	private MailInvoicClaimDetailsPK mailInvoicClaimDetailsPK;

	private String sectorOrigin;

	private String sectorDestination;

	private String sectorFlight;

	private Calendar scanDate;

	private String invoiceKey;

	private Calendar claimDate;

	private String claimStatus;

	private String claimCode;

	private double claimAmount;

	private String poaCode;

	private String claimKey;

	/**
	 * default contructor
	 */
	public MailInvoicClaimDetails() {

	}

	/**
	 * 
	 * @param mailInvoicClaimsEnquiryVO
	 * @throws SystemException
	 */
	public MailInvoicClaimDetails(
			MailInvoicClaimsEnquiryVO mailInvoicClaimsEnquiryVO)
			throws SystemException {
		staticLogger().entering("MailInvoic Master", "Master Contructor----->");
		MailInvoicClaimDetailsPK claimDetailsPK = new MailInvoicClaimDetailsPK();
		claimDetailsPK.setCompanyCode(mailInvoicClaimsEnquiryVO
				.getCompanyCode());
		claimDetailsPK.setReceptacleIdentifier(mailInvoicClaimsEnquiryVO
				.getReceptacleIdentifier());
		claimDetailsPK.setSectorIdentifier(mailInvoicClaimsEnquiryVO
				.getSectorIdentifier());
		this.setMailInvoicClaimDetailsPK(claimDetailsPK);
		populateAttributes(mailInvoicClaimsEnquiryVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * @return the mailInvoicClaimDetailsPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "receptacleIdentifier", column = @Column(name = "RCPIDR")),
			@AttributeOverride(name = "sectorIdentifier", column = @Column(name = "SECIDR")) })
	public MailInvoicClaimDetailsPK getMailInvoicClaimDetailsPK() {
		return mailInvoicClaimDetailsPK;
	}

	/**
	 * @param mailInvoicClaimDetailsPK
	 *            the mailInvoicClaimDetailsPK to set
	 */
	public void setMailInvoicClaimDetailsPK(
			MailInvoicClaimDetailsPK mailInvoicClaimDetailsPK) {
		this.mailInvoicClaimDetailsPK = mailInvoicClaimDetailsPK;
	}

	/**
	 * 
	 * @param mailInvoicClaimsEnquiryVO
	 */
	private void populateAttributes(
			MailInvoicClaimsEnquiryVO mailInvoicClaimsEnquiryVO) {
		staticLogger().entering("mailInvoicClaimsEnquiry",
				"populate Attributes----->");
		this.setClaimAmount(mailInvoicClaimsEnquiryVO.getClaimAmount());
		this.setClaimCode(mailInvoicClaimsEnquiryVO.getClaimCode());
		this.setClaimDate(mailInvoicClaimsEnquiryVO.getClaimDate());
		this.setClaimKey(mailInvoicClaimsEnquiryVO.getClaimKey());
		this.setClaimStatus(mailInvoicClaimsEnquiryVO.getClaimStatus());
		this.setInvoiceKey(mailInvoicClaimsEnquiryVO.getInvoiceKey());
		this.setPoaCode(mailInvoicClaimsEnquiryVO.getPoaCode());
		this.setScanDate(mailInvoicClaimsEnquiryVO.getScanDate());
		this.setSectorDestination(mailInvoicClaimsEnquiryVO
				.getSectorDestination());
		this.setSectorOrigin(mailInvoicClaimsEnquiryVO.getSectorOrigin());
		this.setSectorFlight(mailInvoicClaimsEnquiryVO.getCarrierCode());
	}

	/**
	 * @author a-2270
	 * @param filterVO
	 * @return Page<MailInvoicClaimsEnquiryVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails(
			MailInvoicClaimsFilterVO filterVO) throws SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mailtracking.mra.defaults"))
					.findInvoicClaimsEnquiryDetails(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}

	}

	/**
	 * @author A-2518
	 * @param companyCode
	 * @param receptalceIdentifier
	 * @param sectorIdentifier
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicClaimDetails find(String companyCode,
			String receptalceIdentifier, String sectorIdentifier)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailInvoicClaimDetailsPK mailInvoicClaimDetailsPkToFind = new MailInvoicClaimDetailsPK(
				companyCode, receptalceIdentifier, sectorIdentifier);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailInvoicClaimDetails.class, mailInvoicClaimDetailsPkToFind);
	}

	/**
	 * @return
	 */
	private static Log staticLogger() {
		return LogFactory.getLogger(MODULE_NAME);
	}

	/**
	 * @return the claimAmount
	 */
	@Column(name = "CLMAMT")
	public double getClaimAmount() {
		return claimAmount;
	}

	/**
	 * @param claimAmount
	 *            the claimAmount to set
	 */
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * @return the claimCode
	 */
	@Column(name = "CLMCOD")
	public String getClaimCode() {
		return claimCode;
	}

	/**
	 * @param claimCode
	 *            the claimCode to set
	 */
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	/**
	 * @return the claimDate
	 */
	@Column(name = "CLMDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getClaimDate() {
		return claimDate;
	}

	/**
	 * @param claimDate
	 *            the claimDate to set
	 */
	public void setClaimDate(Calendar claimDate) {
		this.claimDate = claimDate;
	}

	/**
	 * @return the claimKey
	 */
	@Column(name = "CLMKEY")
	public String getClaimKey() {
		return claimKey;
	}

	/**
	 * @param claimKey
	 *            the claimKey to set
	 */
	public void setClaimKey(String claimKey) {
		this.claimKey = claimKey;
	}

	/**
	 * @return the claimStatus
	 */
	@Column(name = "CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}

	/**
	 * @param claimStatus
	 *            the claimStatus to set
	 */
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	/**
	 * @return the invoiceKey
	 */
	@Column(name = "INVKEY")
	public String getInvoiceKey() {
		return invoiceKey;
	}

	/**
	 * @param invoiceKey
	 *            the invoiceKey to set
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}

	/**
	 * @return the poaCode
	 */
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the scanDate
	 */
	@Column(name = "SCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getScanDate() {
		return scanDate;
	}

	/**
	 * @param scanDate
	 *            the scanDate to set
	 */
	public void setScanDate(Calendar scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return the sectorDestination
	 */
	@Column(name = "SECDST")
	public String getSectorDestination() {
		return sectorDestination;
	}

	/**
	 * @param sectorDestination
	 *            the sectorDestination to set
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}

	/**
	 * @return the sectorFlight
	 */
	@Column(name = "SECFLT")
	public String getSectorFlight() {
		return sectorFlight;
	}

	/**
	 * @param sectorFlight
	 *            the sectorFlight to set
	 */
	public void setSectorFlight(String sectorFlight) {
		this.sectorFlight = sectorFlight;
	}

	/**
	 * @return the sectorOrigin
	 */
	@Column(name = "SECORG")
	public String getSectorOrigin() {
		return sectorOrigin;
	}

	/**
	 * @param sectorOrigin
	 *            the sectorOrigin to set
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}

	/**
	 * This method generates INVOIC Claim file
	 * 
	 * @author A-2518
	 * @param companyCode
	 * @throws SystemException
	 * @return Collection<InvoicMessageVO>
	 */
	public static Collection<InvoicMessageVO> generateInvoicClaimFile(
			String companyCode) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "generateInvoicClaimFile");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "generateInvoicClaimFile");
			return mraDefaultsDao.generateInvoicClaimFile(companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}