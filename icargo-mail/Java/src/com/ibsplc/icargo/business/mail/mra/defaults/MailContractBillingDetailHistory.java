/*
 * MailContractBillingDetailHistory.java Created on March 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "MTKCTRBLGDTLHIS")
@Entity
@Deprecated
public class MailContractBillingDetailHistory {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractBillingDetailHistory";

	private MailContractBillingDetailHistoryPK mailContractBillingDetailHistoryPk;

	/**
	 * @return Returns the mailContractBillingDetailHistoryPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")),
			@AttributeOverride(name = "billingMatrixCode", column = @Column(name = "BLGMTXCOD")),
			@AttributeOverride(name = "versionNumber", column = @Column(name = "VERNUM")) })
	public MailContractBillingDetailHistoryPK getMailContractBillingDetailHistoryPk() {
		return mailContractBillingDetailHistoryPk;
	}

	/**
	 * @param mailContractBillingDetailHistoryPk
	 *            The mailContractBillingDetailHistoryPk to set.
	 */
	public void setMailContractBillingDetailHistoryPk(
			MailContractBillingDetailHistoryPK mailContractBillingDetailHistoryPk) {
		this.mailContractBillingDetailHistoryPk = mailContractBillingDetailHistoryPk;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailContractBillingDetailHistory() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param billingMatrixCode
	 * @param versionNumber
	 * @throws SystemException
	 */
	public MailContractBillingDetailHistory(String companyCode,
			String contractReferenceNumber, String billingMatrixCode,
			String versionNumber) throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractBillingDetailHistoryPK mailContractBillingDetailHistoryPkToPersist = new MailContractBillingDetailHistoryPK(
				companyCode, contractReferenceNumber, billingMatrixCode,
				versionNumber);
		setMailContractBillingDetailHistoryPk(mailContractBillingDetailHistoryPkToPersist);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, CLASS_NAME);
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
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param billingMatrixCode
	 * @param versionNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailContractBillingDetailHistory find(String companyCode,
			String contractReferenceNumber, String billingMatrixCode,
			String versionNumber) throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractBillingDetailHistoryPK mailContractBillingDetailHistoryPkToFind = new MailContractBillingDetailHistoryPK(
				companyCode, contractReferenceNumber, billingMatrixCode,
				versionNumber);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractBillingDetailHistory.class,
				mailContractBillingDetailHistoryPkToFind);
	}

}
