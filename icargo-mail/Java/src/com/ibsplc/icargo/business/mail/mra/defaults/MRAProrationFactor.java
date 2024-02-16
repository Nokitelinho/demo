/*
 * MRAProrationFactor.java Created on Mar 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 *
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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author a-2518
 *
 */
@Table(name = "MTKPROFCT")
@Entity
public class MRAProrationFactor {
	private static final String MODULE_NAME = "mail.mra.defaults";

	private static final String CLASS_NAME = "MRAProrationFactor";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private MRAProrationFactorPK prorationFactorPk;

	private double prorationFactor;

	private String prorationFactorSource;

	private String prorationFactorStatus;

	private Calendar validFromDate;

	private Calendar validEndDate;

	private String lastUpdatedUser;

	private Calendar lastUpdatedTime;

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the prorationFactor.
	 */
	@Column(name = "PROFCT")
	public double getProrationFactor() {
		return prorationFactor;
	}

	/**
	 * @param prorationFactor
	 *            The prorationFactor to set.
	 */
	public void setProrationFactor(double prorationFactor) {
		this.prorationFactor = prorationFactor;
	}

	/**
	 * @return Returns the prorationFactorPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "fromCity", column = @Column(name = "FRMCTY")),
			@AttributeOverride(name = "toCity", column = @Column(name = "TOOCTY")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public MRAProrationFactorPK getProrationFactorPk() {
		return prorationFactorPk;
	}

	/**
	 * @param prorationFactorPk
	 *            The prorationFactorPk to set.
	 */
	public void setProrationFactorPk(MRAProrationFactorPK prorationFactorPk) {
		this.prorationFactorPk = prorationFactorPk;
	}

	/**
	 * @return Returns the prorationFactorSource.
	 */
	@Column(name = "FCTSRC")
	public String getProrationFactorSource() {
		return prorationFactorSource;
	}

	/**
	 * @param prorationFactorSource
	 *            The prorationFactorSource to set.
	 */
	public void setProrationFactorSource(String prorationFactorSource) {
		this.prorationFactorSource = prorationFactorSource;
	}

	/**
	 * @return Returns the prorationFactorStatus.
	 */
	@Column(name = "FCTSTA")
	public String getProrationFactorStatus() {
		return prorationFactorStatus;
	}

	/**
	 * @param prorationFactorStatus
	 *            The prorationFactorStatus to set.
	 */
	public void setProrationFactorStatus(String prorationFactorStatus) {
		this.prorationFactorStatus = prorationFactorStatus;
	}

	/**
	 * @return Returns the validEndDate.
	 */
	@Column(name = "VLDTOO")

	@Temporal(TemporalType.DATE)
	public Calendar getValidEndDate() {
		return validEndDate;
	}

	/**
	 * @param validEndDate
	 *            The validEndDate to set.
	 */
	public void setValidEndDate(Calendar validEndDate) {
		this.validEndDate = validEndDate;
	}

	/**
	 * @return Returns the validFromDate.
	 */
	@Column(name = "VLDFRM")

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
	 * default constructor
	 */
	public MRAProrationFactor() {

	}

	/**
	 *
	 * @param prorationFactorPk
	 * @param prorationFactorVo
	 * @throws SystemException
	 */
	public MRAProrationFactor(MRAProrationFactorPK prorationFactorPk,
			ProrationFactorVO prorationFactorVo) throws SystemException {
		log.entering(CLASS_NAME, "MRAProrationFactor");
		MRAProrationFactorPK prorationFactorPkToPersist = new MRAProrationFactorPK(
				prorationFactorPk.getCompanyCode(), prorationFactorPk.getFromCity(),
				prorationFactorPk.getToCity(), prorationFactorPk.getSequenceNumber());
		setProrationFactorPk(prorationFactorPkToPersist);
		populateAttributes(prorationFactorVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "MRAProrationFactor");

	}

	/**
	 *
	 * @param prorationFactorVo
	 */
	public void populateAttributes(ProrationFactorVO prorationFactorVo) {
		log.entering(CLASS_NAME, "populateAttributes");
		setProrationFactor(prorationFactorVo.getProrationFactor());
		setProrationFactorSource(prorationFactorVo.getProrationFactorSource());
		setProrationFactorStatus(prorationFactorVo.getProrationFactorStatus());
		setLastUpdatedUser(prorationFactorVo.getLastUpdatedUser());
		//setLastUpdatedTime(new LocalDate(NO_STATION, NONE, true));
		if(prorationFactorVo.getLastUpdatedTime()!=null){
			setLastUpdatedTime(prorationFactorVo.getLastUpdatedTime());
		}
		if (prorationFactorVo.getFromDate() != null) {
			setValidFromDate(prorationFactorVo.getFromDate());
		} else {
			setValidFromDate(null);
		}
		if (prorationFactorVo.getToDate() != null) {
			setValidEndDate(prorationFactorVo.getToDate());
		} else {
			setValidEndDate(null);
		}
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 *
	 * @param prorationFactorVo
	 * @throws SystemException
	 */
	public void update(ProrationFactorVO prorationFactorVo)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(prorationFactorVo);
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
			log.exiting(CLASS_NAME, "remove");
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param fromCity
	 * @param toCity
	 * @param sequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MRAProrationFactor find(String companyCode, String fromCity,
			String toCity, int sequenceNumber) throws SystemException,
			FinderException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "find");
		MRAProrationFactorPK prorationFactorPkToFine = new MRAProrationFactorPK(
				companyCode, fromCity, toCity, sequenceNumber);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MRAProrationFactor.class, prorationFactorPkToFine);
	}

	/**
	 * lists the Proration factors
	 *
	 * @param prorationFactorFilterVo
	 * @return Collection<ProrationFactorVO>
	 * @throws SystemException
	 * @author a-2518
	 */
	public static Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "findProrationFactors");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findProrationFactors");
			return mraDefaultsDao.findProrationFactors(prorationFactorFilterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-5166
	 * Added for ICRD-36146 on 06-Mar-2013
	 * @param companyCode
	 * @throws SystemException
	 */
	public void initiateProration(String companyCode)
	throws SystemException{
		log.entering(CLASS_NAME, "initiateProration");
		MRADefaultsDAO mraDefaultsDao = null;
		try{
			mraDefaultsDao = MRADefaultsDAO.class.cast(
	                   PersistenceController.getEntityManager().getQueryDAO(
	                		   MODULE_NAME));
			mraDefaultsDao.initiateProration(companyCode);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
