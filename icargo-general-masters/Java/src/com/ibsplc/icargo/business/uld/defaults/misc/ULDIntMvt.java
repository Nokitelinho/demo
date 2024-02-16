/*
 * ULDIntMvt.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
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
 * @author A-2412
 *
 */

@Table(name = "ULDINTMVTMST")
@Entity
public class ULDIntMvt {

	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	/**
	 * Default constructor
	 * 
	 */
	public ULDIntMvt() {
	}

	private String airport;
	private ULDIntMvtPK uldIntMvtPK;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;

	private Set<ULDIntMvtDetail> uldIntMvtDetails;

	/**
	 * @param uldIntMovementVO
	 * @throws SystemException
	 */
	public ULDIntMvt(ULDIntMvtVO uldIntMovementVO) throws SystemException {
		log.entering("INSIDE THE ULD", "MOVEMENT");
		log.entering("INSIDE THE ULD", "MOVEMENT");
		log.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldIntMovementVO.getCompanyCode());
		log.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldIntMovementVO.getIntSequenceNumber());
		log.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldIntMovementVO.getUldNumber());
		log.log(Log.FINE, "uldIntMovementVO inside Entity--->>",
				uldIntMovementVO);
		populatepk(uldIntMovementVO.getCompanyCode(), uldIntMovementVO
				.getUldNumber(), uldIntMovementVO.getIntSequenceNumber());
		populateAttributes(uldIntMovementVO);
		if(uldIntMovementVO.getULDIntMvtDetailVOs() != null 
				&& uldIntMovementVO.getULDIntMvtDetailVOs().size() > 0){
				populateChildren(uldIntMovementVO);
		}
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * @param companyCode
	 * @param uldNumber
	 * @param intSequenceNumber
	 */
	private void populatepk(String companyCode, String uldNumber,
			String intSequenceNumber) {
		ULDIntMvtPK pk = new ULDIntMvtPK();
		pk.setCompanyCode(companyCode);
		pk.setUldNumber(uldNumber);
		pk.setIntSequenceNumber(Objects.nonNull(intSequenceNumber) ? Integer.parseInt(intSequenceNumber) : 0);
		this.setUldIntMvtPK(pk);
	}
	/**
	 * @author A-2667
	 * @param uLDIntMovementVO
	 */
	private void populateAttributes(ULDIntMvtVO uLDIntMovementVO ) {
		this.setAirport(uLDIntMovementVO.getAirport());
		this.setLastUpdatedUser(uLDIntMovementVO.getLastUpdatedUser());
	}
	/**
	 * @author A-2667
	 * @param uLDIntMovementVO
	 */
	public void populateChildren(ULDIntMvtVO uLDIntMovementVO)
	throws SystemException {
		Collection<ULDIntMvtDetailVO> uLDIntMvtDetailVOs  = uLDIntMovementVO.getULDIntMvtDetailVOs();
		if(uLDIntMvtDetailVOs != null && uLDIntMvtDetailVOs.size() > 0){
			for(ULDIntMvtDetailVO uLDIntMvtDetailVO : uLDIntMvtDetailVOs){
				uLDIntMvtDetailVO.setIntSequenceNumber(uLDIntMovementVO.getIntSequenceNumber());
				ULDIntMvtDetail uLDIntMvtDetail = new ULDIntMvtDetail(uLDIntMvtDetailVO);
				 try{
					 PersistenceController.getEntityManager().persist(uLDIntMvtDetail);
				 }catch(CreateException createException){
					 throw new SystemException(createException.getErrorCode());
				 }
			}
		}
	}
	
	
	/**
	 * @param companyCode
	 * @param uldNumber
	 * @param intSequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDIntMvt find(String companyCode, String uldNumber,
			String intSequenceNumber) throws SystemException {
		ULDIntMvt uldInternalMovement = null;
		ULDIntMvtPK movementPK = new ULDIntMvtPK();
		movementPK.setCompanyCode(companyCode);
		movementPK.setIntSequenceNumber(Objects.nonNull(intSequenceNumber) ? Integer.parseInt(intSequenceNumber) : 0);
		movementPK.setUldNumber(uldNumber);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			uldInternalMovement = em.find(ULDIntMvt.class, movementPK);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}

		return uldInternalMovement;
	}

	/**
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		log.log(Log.INFO, "!!!!GOING TO REMOVE THIS");
		try {
			em.remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}

	/**
	 * @return the uldIntMvtPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "intSequenceNumber", column = @Column(name = "INTSEQNUM")) })
	public ULDIntMvtPK getUldIntMvtPK() {
		return uldIntMvtPK;
	}

	/**
	 * @param uldIntMvtPK the uldIntMvtPK to set
	 */
	public void setUldIntMvtPK(ULDIntMvtPK uldIntMvtPK) {
		this.uldIntMvtPK = uldIntMvtPK;
	}

	/**
	 * @return the uldIntMvtDetails
	 */

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false),
			@JoinColumn(name = "INTSEQNUM", referencedColumnName = "INTSEQNUM", insertable = false, updatable = false) })
	public Set<ULDIntMvtDetail> getUldIntMvtDetails() {
		return uldIntMvtDetails;
	}

	/**
	 * @param uldIntMvtDetails the uldIntMvtDetails to set
	 */
	public void setUldIntMvtDetails(Set<ULDIntMvtDetail> uldIntMvtDetails) {
		this.uldIntMvtDetails = uldIntMvtDetails;
	}

	/**
	 * @author A-2412
	 * @param uldIntMvtFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDIntMvtDetailVO> findIntULDMovementHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, int displayPage)
			throws SystemException {
		Page<ULDIntMvtDetailVO> uldMovementDetailList = null;
		try {
			uldMovementDetailList = constructDAO().findIntULDMovementHistory(
					uldIntMvtFilterVO, displayPage);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
		return uldMovementDetailList;
	}
 
	/**
	 * @return
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @return the airport
	 */
	@Column(name = "ARPCOD")
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Version
    @Column(name="LSTUPDTIM")    
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	 @Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	/**
	 * @author A-3045
	 * @param uldIntMvtFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDIntMvtDetailVO> findULDIntMvtHistory(
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO)
			throws SystemException{
		Collection<ULDIntMvtDetailVO> uldMovementDetailList = null;
		try {
			uldMovementDetailList = constructDAO().findULDIntMvtHistory(
					uldIntMvtFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		} 
		return uldMovementDetailList;
	}
}
