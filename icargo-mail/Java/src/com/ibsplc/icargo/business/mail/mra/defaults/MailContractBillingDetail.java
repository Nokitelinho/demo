/*
 * MailContractBillingDetail.java Created on March 30, 2007
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
@Table(name = "MTKCTRBLGDTL")
@Entity
@Deprecated
public class MailContractBillingDetail {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractBillingDetail";

	private MailContractBillingDetailPK mailContractBillingDetailPk;

	/**
	 * @return Returns the mailContractBillingDetailPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")),
			@AttributeOverride(name = "billingMatrixCode", column = @Column(name = "BLGMTXCOD")) })
	public MailContractBillingDetailPK getMailContractBillingDetailPk() {
		return mailContractBillingDetailPk;
	}

	/**
	 * @param mailContractBillingDetailPk
	 *            The mailContractBillingDetailPk to set.
	 */
	public void setMailContractBillingDetailPk(
			MailContractBillingDetailPK mailContractBillingDetailPk) {
		this.mailContractBillingDetailPk = mailContractBillingDetailPk;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailContractBillingDetail() {
	}

	/**
	 * 
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param billingMatrixCode
	 * @throws SystemException
	 */
	public MailContractBillingDetail(String companyCode,
			String contractReferenceNumber, String billingMatrixCode)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractBillingDetailPK mailContractBillingDetailPkToPersist = new MailContractBillingDetailPK(
				companyCode, contractReferenceNumber, billingMatrixCode);
		setMailContractBillingDetailPk(mailContractBillingDetailPkToPersist);
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
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailContractBillingDetail find(String companyCode,
			String contractReferenceNumber, String billingMatrixCode)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractBillingDetailPK mailContractBillingDetailPkToFind = new MailContractBillingDetailPK(
				companyCode, contractReferenceNumber, billingMatrixCode);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractBillingDetail.class,
				mailContractBillingDetailPkToFind);
	}

}
