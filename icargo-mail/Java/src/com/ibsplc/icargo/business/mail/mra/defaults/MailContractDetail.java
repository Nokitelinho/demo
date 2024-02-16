/*
 * MailContractDetail.java Created on March 30, 2007
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
@Table(name = "MTKCTRDTL")
@Entity
@Deprecated
public class MailContractDetail {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "MailContractDetail";

	private String originCode;

	private String destinationCode;

	private String serviceLevelActivityOne;

	private String serviceLevelActivityTwo;

	private String remarks;

	private MailContractDetailPK mailContractDetailPk;

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
	 * @return Returns the mailContractDetailPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "contractReferenceNumber", column = @Column(name = "CTRREFNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MailContractDetailPK getMailContractDetailPk() {
		return mailContractDetailPk;
	}

	/**
	 * @param mailContractDetailPk
	 *            The mailContractDetailPk to set.
	 */
	public void setMailContractDetailPk(
			MailContractDetailPK mailContractDetailPk) {
		this.mailContractDetailPk = mailContractDetailPk;
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
	public MailContractDetail() {
	}

	/**
	 * 
	 * @param mailContractDetailsVo
	 * @throws SystemException
	 */
	public MailContractDetail(MailContractDetailsVO mailContractDetailsVo)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailContractDetailPK mailContractDetailPkToPersist = new MailContractDetailPK(
				mailContractDetailsVo.getCompanyCode(), mailContractDetailsVo
						.getContractReferenceNumber(), mailContractDetailsVo
						.getSerialNumber());
		setMailContractDetailPk(mailContractDetailPkToPersist);
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
	 * @return MailContractDetail
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailContractDetail find(
			MailContractDetailsVO mailContractDetailsVo)
			throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MailContractDetailPK mailContractDetailPkToFind = new MailContractDetailPK(
				mailContractDetailsVo.getCompanyCode(), mailContractDetailsVo
						.getContractReferenceNumber(), mailContractDetailsVo
						.getSerialNumber());
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailContractDetail.class, mailContractDetailPkToFind);
	}

}
