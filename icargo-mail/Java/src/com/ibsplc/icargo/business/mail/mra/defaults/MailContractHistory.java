/*
 * MailContractHistory.java Created on March 30, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2518
 * 
 */
@Staleable
@Table(name = "MTKCTRMSTHIS")
@Entity
@Deprecated
public class MailContractHistory {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractHistory";

	private int airlineIdentifier;

	private String airlineCode;

	private String gpaCode;

	private String contractDescription;

	private String agreementStatus;

	private String agreementType;

	private Calendar creationDate;

	private Calendar validFromDate;

	private Calendar validToDate;

	private MailContractHistoryPK mailContractHistoryPk;

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
	 * @return Returns the mailContractHistoryPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")),
			@AttributeOverride(name = "versionNumber", column = @Column(name = "VERNUM")) })
	public MailContractHistoryPK getMailContractHistoryPk() {
		return mailContractHistoryPk;
	}

	/**
	 * @param mailContractHistoryPk
	 *            The mailContractHistoryPk to set.
	 */
	public void setMailContractHistoryPk(
			MailContractHistoryPK mailContractHistoryPk) {
		this.mailContractHistoryPk = mailContractHistoryPk;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailContractHistory() {
	}

	/**
	 * 
	 * @param mailContractVo
	 * @throws SystemException
	 */
	public MailContractHistory(MailContractVO mailContractVo)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractHistoryPK mailContractHistoryPkToPersist = new MailContractHistoryPK(
				mailContractVo.getCompanyCode(), mailContractVo
						.getContractReferenceNumber(), mailContractVo
						.getVersionNumber());
		setMailContractHistoryPk(mailContractHistoryPkToPersist);
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
		// Populating Mail Contract history details
		if (mailContractVo.getMailContractDetailsVos() != null
				&& mailContractVo.getMailContractDetailsVos().size() > 0) {
			for (MailContractDetailsVO mailContractDetailsVo : mailContractVo
					.getMailContractDetailsVos()) {
				mailContractDetailsVo.setCompanyCode(mailContractVo
						.getCompanyCode());
				mailContractDetailsVo.setContractReferenceNumber(mailContractVo
						.getContractReferenceNumber());
				new MailContractDetailHistory(mailContractDetailsVo,
						mailContractVo.getVersionNumber());
			}
		}
		// Populating Billing Matrix history details
		if (mailContractVo.getBillingDetails() != null
				&& mailContractVo.getBillingDetails().size() > 0) {
			for (String billingMatrixCode : mailContractVo.getBillingDetails()) {
				if (billingMatrixCode != null) {
					new MailContractBillingDetailHistory(mailContractVo
							.getCompanyCode(), mailContractVo
							.getContractReferenceNumber(), billingMatrixCode,
							mailContractVo.getVersionNumber());
				}
			}
		}
		log.entering(CLASS_NAME, "populateChildren");
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
	public static MailContractHistory find(MailContractVO mailContractVo)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractHistoryPK mailContractHistoryPkToFind = new MailContractHistoryPK(
				mailContractVo.getCompanyCode(), mailContractVo
						.getContractReferenceNumber(), mailContractVo
						.getVersionNumber());
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractHistory.class, mailContractHistoryPkToFind);
	}

}
