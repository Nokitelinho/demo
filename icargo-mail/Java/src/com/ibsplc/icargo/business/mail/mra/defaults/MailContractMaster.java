/*
 * MailContractMaster.java Created on March 30, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVersionLOVVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2518
 * 
 */
@Staleable
@Table(name = "MTKCTRMST")
@Entity
@Deprecated
public class MailContractMaster {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractMaster";

	private static final String MODULE_NAME = "mail.mra.defaults";

	private String versionNumber;

	private int airlineIdentifier;

	private String airlineCode;

	private String gpaCode;

	private String contractDescription;

	private String agreementStatus;

	private String agreementType;

	private Calendar creationDate;

	private Calendar validFromDate;

	private Calendar validToDate;

	private MailContractMasterPK mailContractMasterPk;

	/**
	 * @return Returns the agreementStatus.
	 */
	@Column(name = "AGRSTA")
	public String getAgreementStatus() {
		return agreementStatus;
	}

	/**
	 * @param agreementStatus
	 *            The agreementStatus to set.
	 */
	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	/**
	 * @return Returns the agreementType.
	 */
	@Column(name = "AGRTYP")
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param agreementType
	 *            The agreementType to set.
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	@Column(name = "ARLIDR")
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the contractDescription.
	 */
	@Column(name = "CTRDSC")
	public String getContractDescription() {
		return contractDescription;
	}

	/**
	 * @param contractDescription
	 *            The contractDescription to set.
	 */
	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	/**
	 * @return Returns the creationDate.
	 */
	@Column(name = "CREDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	@Column(name = "GPACOD")
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the validFromDate.
	 */
	@Column(name = "VLDFRMDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getValidFromDate() {
		return validFromDate;
	}

	/**
	 * @param validFromDate
	 *            The validFromDate to set.
	 */
	public void setValidFromDate(Calendar validFromDate) {
		this.validFromDate = validFromDate;
	}

	/**
	 * @return Returns the validToDate.
	 */
	@Column(name = "VLDTOODAT")

	@Temporal(TemporalType.DATE)
	public Calendar getValidToDate() {
		return validToDate;
	}

	/**
	 * @param validToDate
	 *            The validToDate to set.
	 */
	public void setValidToDate(Calendar validToDate) {
		this.validToDate = validToDate;
	}

	/**
	 * @return Returns the versionNumber.
	 */
	@Column(name = "VERNUM")
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber
	 *            The versionNumber to set.
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return Returns the mailContractMasterPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")) })
	public MailContractMasterPK getMailContractMasterPk() {
		return mailContractMasterPk;
	}

	/**
	 * @param mailContractMasterPk
	 *            The mailContractMasterPk to set.
	 */
	public void setMailContractMasterPk(
			MailContractMasterPK mailContractMasterPk) {
		this.mailContractMasterPk = mailContractMasterPk;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailContractMaster() {
	}

	/**
	 * 
	 * @param mailContractVo
	 * @throws SystemException
	 */
	public MailContractMaster(MailContractVO mailContractVo)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractMasterPK mailContractMasterPkToPersist = new MailContractMasterPK(
				mailContractVo.getCompanyCode(), mailContractVo
						.getContractReferenceNumber());
		setMailContractMasterPk(mailContractMasterPkToPersist);
		populateChildren(mailContractVo);
		populateAttributes(mailContractVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, CLASS_NAME);
	}

	/**
	 * 
	 * @param mailContractVo
	 * @throws SystemException
	 */
	public void populateChildren(MailContractVO mailContractVo)
			throws SystemException {
		log.entering(CLASS_NAME, "populateChildren");
		// Populating Mail Contract details
		if (mailContractVo.getMailContractDetailsVos() != null
				&& mailContractVo.getMailContractDetailsVos().size() > 0) {
			for (MailContractDetailsVO mailContractDetailsVo : mailContractVo
					.getMailContractDetailsVos()) {
				mailContractDetailsVo.setCompanyCode(mailContractVo
						.getCompanyCode());
				mailContractDetailsVo.setContractReferenceNumber(mailContractVo
						.getContractReferenceNumber());
				new MailContractDetail(mailContractDetailsVo);
			}
		}
		// Populating Billing Matrix details
		if (mailContractVo.getBillingDetails() != null
				&& mailContractVo.getBillingDetails().size() > 0) {
			for (String billingMatrixCode : mailContractVo.getBillingDetails()) {
				if (billingMatrixCode != null) {
					new MailContractBillingDetail(mailContractVo
							.getCompanyCode(), mailContractVo
							.getContractReferenceNumber(), billingMatrixCode);
				}
			}
		}
		log.exiting(CLASS_NAME, "populateChildren");
	}

	/**
	 * 
	 * @param mailContractVo
	 */
	public void populateAttributes(MailContractVO mailContractVo) {
		log.entering(CLASS_NAME, "populateAttributes");
		setAgreementStatus(mailContractVo.getAgreementStatus());
		setAgreementType(mailContractVo.getAgreementType());
		setAirlineCode(mailContractVo.getAirlineCode());
		setAirlineIdentifier(mailContractVo.getAirlineIdentifier());
		setContractDescription(mailContractVo.getContractDescription());
		setCreationDate(mailContractVo.getCreationDate());
		setGpaCode(mailContractVo.getGpaCode());
		setValidFromDate(mailContractVo.getValidFromDate());
		setValidToDate(mailContractVo.getValidToDate());
		setVersionNumber(mailContractVo.getVersionNumber());
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 * 
	 * @param mailContractVo
	 * @throws SystemException
	 */
	public void update(MailContractVO mailContractVo) throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(mailContractVo);
		log.exiting(CLASS_NAME, "update");
	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering(CLASS_NAME, "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "remove");
	}

	/**
	 * 
	 * @param mailContractVo
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailContractMaster find(MailContractVO mailContractVo)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractMasterPK mailContractMasterPkToFind = new MailContractMasterPK(
				mailContractVo.getCompanyCode(), mailContractVo
						.getContractReferenceNumber());
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractMaster.class, mailContractMasterPkToFind);
	}

	/**
	 * Finds mail contract details
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return MailContractVO
	 * @throws SystemException
	 * @author A-2518
	 */
	public static MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
			throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "viewMailContract");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "viewMailContract");
			return mraDefaultsDao.viewMailContract(companyCode,
					contractReferenceNumber, versionNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static int findMaximumVersionNumber(String companyCode,
			String contractReferenceNumber) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "findMaximumVersionNumber");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findMaximumVersionNumber");
			return mraDefaultsDao.findMaximumVersionNumber(companyCode,
					contractReferenceNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Displays Version numbers for Version LOV
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return
	 * @throws SystemException
	 * @author A-2518
	 */
	public static Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "displayVersionLov");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findMaximumVersionNumber");
			return mraDefaultsDao.displayVersionLov(companyCode,
					contractReferenceNumber, versionNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method to construct corresponding DAO
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO() throws SystemException {

		MRADefaultsDAO queryDAO = null;
		try {
			queryDAO = (MRADefaultsDAO) PersistenceController
					.getEntityManager().getQueryDAO(MODULE_NAME);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}

		return queryDAO;
	}

	/**
	 * @author A-2521
	 * @param contractFilterVO
	 * @return Collection<ContractDetailsVO>
	 * @throws SystemException
	 */
	public static Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO) throws SystemException {

		return constructDAO().displayContractDetails(contractFilterVO);

	}

	/**
	 * Finds mail contract details for duplicate contract check
	 * 
	 * @param companyCode
	 * @param gpaCode
	 * @param airlineCode
	 * @return Collection<MailContractVO>
	 * @throws SystemException
	 * @author A-2518
	 */
	public static Collection<MailContractVO> viewDuplicateMailContract(
			String companyCode, String gpaCode, String airlineCode)
			throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "viewDuplicateMailContract");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "viewDuplicateMailContract");
			return mraDefaultsDao.viewDuplicateMailContract(companyCode,
					gpaCode, airlineCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1946
	 * @param mailContractFilterVO
	 * @return Collection<ContractDetailsVO>
	 * @throws SystemException
	 */
	public static Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO) throws SystemException {

		return constructDAO().findMailContracts(mailContractFilterVO);
	}

}
