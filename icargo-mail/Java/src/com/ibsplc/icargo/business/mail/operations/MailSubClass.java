/*
 * MailSubClass.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3109
 */
@Entity
@Table(name = "MALSUBCLSMST")
public class MailSubClass {

	private static final String MODULE_NAME = "mail.operations";

	private MailSubClassPK mailSubClassPK;

	/**
	 * The description for the mail subclass code
	 */
	private String description;

	/**
	 * subClassgroup
	 */
	private String subClassgroup;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * @return Returns the description.
	 */
	@Column(name = "DES")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the subClassgroup.
	 */
	@Column(name = "SUBCLSGRP")
	public String getSubClassgroup() {
		return subClassgroup;
	}

	/**
	 * @param subClassgroup
	 *            The subClassgroup to set.
	 */
	public void setSubClassgroup(String subClassgroup) {
		this.subClassgroup = subClassgroup;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the mailSubClassPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "subClassCode", column = @Column(name = "SUBCLSCOD")) })
	public MailSubClassPK getMailSubClassPK() {
		return mailSubClassPK;
	}

	/**
	 * @param mailSubClassPK
	 *            The mailSubClassPK to set.
	 */
	public void setMailSubClassPK(MailSubClassPK mailSubClassPK) {
		this.mailSubClassPK = mailSubClassPK;
	}

	public MailSubClass() {

	}

	public MailSubClass(MailSubClassVO mailSubClassVO) throws SystemException {
		try {
			populatepk(mailSubClassVO);
			populateAttribute(mailSubClassVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populatepk(MailSubClassVO mailSubClassVO) {
		MailSubClassPK mailSubClassPk = new MailSubClassPK();
		mailSubClassPk.setCompanyCode(mailSubClassVO.getCompanyCode());
		mailSubClassPk.setSubClassCode(mailSubClassVO.getCode());
		this.mailSubClassPK = mailSubClassPk;
	}

	private void populateAttribute(MailSubClassVO mailSubClassVO) {
		this.setDescription(mailSubClassVO.getDescription());
		this.setSubClassgroup(mailSubClassVO.getSubClassGroup());
		this.setLastUpdateUser(mailSubClassVO.getLastUpdateUser());
	}

	/**
	 * @param companyCode
	 * @param subClassCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailSubClass find(String companyCode, String subClassCode)
			throws SystemException, FinderException {
		MailSubClassPK mailSubClassPk = new MailSubClassPK();
		mailSubClassPk.setCompanyCode(companyCode);
		mailSubClassPk.setSubClassCode(subClassCode);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailSubClass.class, mailSubClassPk);
	}

	/**
	 * @param mailSubClassVO
	 * @throws SystemException
	 */
	public void update(MailSubClassVO mailSubClassVO) throws SystemException {
		populateAttribute(mailSubClassVO);
		this.setLastUpdateTime(mailSubClassVO.getLastUpdateTime());
	}

	/**
	 * Method to delete a row of MailSubClass
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		PersistenceController.getEntityManager().remove(this);

	}

	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * @author a-2037 This method is used to find all the mail subclass codes
	 * @param companyCode
	 * @param subclassCode
	 * @return Collection<MailSubClassVO>
	 * @throws SystemException
	 */
	public static Collection<MailSubClassVO> findMailSubClassCodes(
			String companyCode, String subclassCode) throws SystemException {
		try {
			return constructDAO().findMailSubClassCodes(companyCode,
					subclassCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}



	/**
	 * @author a-1936 This method is used to validate the MailSubClass
	 * @param companyCode
	 * @param mailSubClass
	 * @return
	 * @throws SystemException
	 */
	public static boolean validateMailSubClass(String companyCode,
			String mailSubClass) throws SystemException {
		Log logger = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		logger.entering("MAILSUBCLASS", "VALIDATEMAILSUBCLASS");
		try {
			return constructDAO().validateMailSubClass(companyCode,
					mailSubClass);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * @author A-2037 Method for MailSubClassLOV containing code and description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailSubClassVO> findMailSubClassCodeLov(
			String companyCode, String code, String description, int pageNumber,int defaultSize)
			throws SystemException {
		try {
			return constructDAO().findMailSubClassCodeLov(companyCode, code,
					description, pageNumber,defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
