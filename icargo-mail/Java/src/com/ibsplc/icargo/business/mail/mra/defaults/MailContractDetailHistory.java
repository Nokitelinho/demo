/*
 * MailContractDetailHistory.java Created on March 30, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
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
@Table(name = "MTKCTRDTLHIS")
@Entity
@Deprecated
public class MailContractDetailHistory {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractDetailHistory";

	private String originCode;

	private String destinationCode;

	private String serviceLevelActivityOne;

	private String serviceLevelActivityTwo;

	private String remarks;

	private MailContractDetailHistoryPK mailContractDetailHistoryPk;

	/**
	 * @return Returns the destinationCode.
	 */
	@Column(name = "DSTCOD")
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode
	 *            The destinationCode to set.
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return Returns the mailContractDetailHistoryPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "versionNumber", column = @Column(name = "VERNUM")) })
	public MailContractDetailHistoryPK getMailContractDetailHistoryPk() {
		return mailContractDetailHistoryPk;
	}

	/**
	 * @param mailContractDetailHistoryPk
	 *            The mailContractDetailHistoryPk to set.
	 */
	public void setMailContractDetailHistoryPk(
			MailContractDetailHistoryPK mailContractDetailHistoryPk) {
		this.mailContractDetailHistoryPk = mailContractDetailHistoryPk;
	}

	/**
	 * @return Returns the originCode.
	 */
	@Column(name = "ORGCOD")
	public String getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode
	 *            The originCode to set.
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the serviceLevelActivityOne.
	 */
	@Column(name = "SLAACTONE")
	public String getServiceLevelActivityOne() {
		return serviceLevelActivityOne;
	}

	/**
	 * @param serviceLevelActivityOne
	 *            The serviceLevelActivityOne to set.
	 */
	public void setServiceLevelActivityOne(String serviceLevelActivityOne) {
		this.serviceLevelActivityOne = serviceLevelActivityOne;
	}

	/**
	 * @return Returns the serviceLevelActivityTwo.
	 */
	@Column(name = "SLAACTTWO")
	public String getServiceLevelActivityTwo() {
		return serviceLevelActivityTwo;
	}

	/**
	 * @param serviceLevelActivityTwo
	 *            The serviceLevelActivityTwo to set.
	 */
	public void setServiceLevelActivityTwo(String serviceLevelActivityTwo) {
		this.serviceLevelActivityTwo = serviceLevelActivityTwo;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailContractDetailHistory() {
	}

	/**
	 * 
	 * @param mailContractDetailsVo
	 * @param versionNumber
	 * @throws SystemException
	 */
	public MailContractDetailHistory(
			MailContractDetailsVO mailContractDetailsVo, String versionNumber)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractDetailHistoryPK mailContractDetailHistoryPkToPersist = new MailContractDetailHistoryPK(
				mailContractDetailsVo.getCompanyCode(), mailContractDetailsVo
						.getContractReferenceNumber(), mailContractDetailsVo
						.getSerialNumber(), versionNumber);
		setMailContractDetailHistoryPk(mailContractDetailHistoryPkToPersist);
		populateAttributes(mailContractDetailsVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, CLASS_NAME);
	}

	/**
	 * 
	 * @param mailContractDetailsVo
	 */
	public void populateAttributes(MailContractDetailsVO mailContractDetailsVo) {
		log.entering(CLASS_NAME, "populateAttributes");
		setDestinationCode(mailContractDetailsVo.getDestinationCode());
		setOriginCode(mailContractDetailsVo.getOriginCode());
		setRemarks(mailContractDetailsVo.getRemarks());
		setServiceLevelActivityOne(mailContractDetailsVo
				.getAcceptanceToDeparture());
		setServiceLevelActivityTwo(mailContractDetailsVo.getArrivalToDelivery());

		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 * 
	 * @param mailContractDetailsVo
	 * @throws SystemException
	 */
	public void update(MailContractDetailsVO mailContractDetailsVo)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(mailContractDetailsVo);
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
	 * @param mailContractDetailsVo
	 * @param versionNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailContractDetailHistory find(
			MailContractDetailsVO mailContractDetailsVo, String versionNumber)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractDetailHistoryPK mailContractDetailHistoryPkToFind = new MailContractDetailHistoryPK(
				mailContractDetailsVo.getCompanyCode(), mailContractDetailsVo
						.getContractReferenceNumber(), mailContractDetailsVo
						.getSerialNumber(), versionNumber);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractDetailHistory.class,
				mailContractDetailHistoryPkToFind);
	}

}
