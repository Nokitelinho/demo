/*
 * ULDAirportLocation.java Created on Sept 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name = "ULDSRVMST")
@Entity
public class ULDServiceability {

	private Log log = LogFactory.getLogger("ULD MANAGEMENT");

	private ULDServiceabilityPK serviceabilityPK;

	private String serviceabilityDescription;

	private String serviceabilityCode;

	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;

	// private String partyType;

	/**
	 * @return String Returns the serviceabilityCode.
	 */
	@Column(name = "SRVCOD")
	public String getServiceabilityCode() {
		return this.serviceabilityCode;
	}

	/**
	 * @param serviceabilityCode
	 *            The serviceabilityCode to set.
	 */
	public void setServiceabilityCode(String serviceabilityCode) {
		this.serviceabilityCode = serviceabilityCode;
	}

	/**
	 * @return String Returns the serviceabilityDescription.
	 */
	@Column(name = "SRVDES")
	public String getServiceabilityDescription() {
		return this.serviceabilityDescription;
	}

	/**
	 * @param serviceabilityDescription
	 *            The serviceabilityDescription to set.
	 */
	public void setServiceabilityDescription(String serviceabilityDescription) {
		this.serviceabilityDescription = serviceabilityDescription;
	}

	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	@Column(name = "LSTUPDTIM")
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the partyType
	 */
	/*
	 * @Column(name="PTYTYP") public String getPartyType() { return partyType; }
	 * /** @param partyType the partyType to set
	 */
	/*
	 * public void setPartyType(String partyType) { this.partyType = partyType; }
	 */

	/**
	 * @return ULDServiceabilityPK Returns the serviceabilityPK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "partyType", column = @Column(name = "PTYTYP")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public ULDServiceabilityPK getServiceabilityPK() {
		return this.serviceabilityPK;
	}

	/**
	 * @param serviceabilityPK
	 *            The serviceabilityPK to set.
	 */
	public void setServiceabilityPK(ULDServiceabilityPK serviceabilityPK) {
		this.serviceabilityPK = serviceabilityPK;
	}

	/**
	 * 
	 * 
	 */
	public ULDServiceability() {
	}

	/**
	 * 
	 * @param locationVO
	 * @throws SystemException
	 */
	public ULDServiceability(ULDServiceabilityVO serviceabilityVO)
			throws SystemException {
		log.entering("ULDServiceability", "serviceabilityVO");
		populatePk(serviceabilityVO);
		populateAttributes(serviceabilityVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param serviceabilityVO
	 */
	public void populatePk(ULDServiceabilityVO serviceabilityVO) {
		log.entering("ULDServiceability", "populatePk");
		ULDServiceabilityPK servicePK = new ULDServiceabilityPK();
		servicePK.setCompanyCode(serviceabilityVO.getCompanyCode());
		servicePK.setPartyType(serviceabilityVO.getPartyType());
		setServiceabilityPK(servicePK);
	}

	/**
	 * 
	 * @param serviceabilityVO
	 */
	public void populateAttributes(ULDServiceabilityVO serviceabilityVO) {
		log.entering("ULDServiceability", "populateAttributes");
		setServiceabilityCode(serviceabilityVO.getCode());
		setServiceabilityDescription(serviceabilityVO.getDescription());
		setLastUpdatedUser(serviceabilityVO.getLastUpdatedUser());
	}

	/**
	 * 
	 * @param serviceabilityVO
	 */
	public void update(ULDServiceabilityVO serviceabilityVO) {
		log.entering("ULDServiceabilityVO=============", "\n update");
		this.setLastUpdatedTime(serviceabilityVO.getLastUpdatedTime()
				.toCalendar());
		populateAttributes(serviceabilityVO);
	}

	/**
	 * 
	 * @param serviceabilityVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDServiceability find(ULDServiceabilityVO serviceabilityVO)
			throws SystemException {
		ULDServiceabilityPK servicePK = new ULDServiceabilityPK();
		servicePK.setCompanyCode(serviceabilityVO.getCompanyCode());
		servicePK.setPartyType(serviceabilityVO.getPartyType());
		servicePK.setSequenceNumber(serviceabilityVO.getSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDServiceability.class, servicePK);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode());
		}
	}

	/**
	 * 
	 * @throws SystemException
	 * 
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode());
		} catch (OptimisticConcurrencyException e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param facilityType
	 * @return
	 * @throws SystemException
	 * 
	 */
	public static Collection<ULDServiceabilityVO> listULDServiceability(
			String companyCode, String partyType) throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uLDDefaultsDAO.listULDServiceability(companyCode, partyType);
		} catch (PersistenceException e) {
			e.getErrorCode();
			throw new SystemException(e.getErrorCode());
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param serviceCode
	 * @return
	 * @throws SystemException
	 */
	public static int checkForServiceability(String companyCode,
			String serviceCode, String partyType) throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uLDDefaultsDAO.checkForServiceability(companyCode,
					serviceCode, partyType);
		} catch (PersistenceException e) {
			e.getErrorCode();
			throw new SystemException(e.getErrorCode());
		}
	}

}
