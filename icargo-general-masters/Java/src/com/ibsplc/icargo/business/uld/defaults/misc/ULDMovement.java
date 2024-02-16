/*
 * ULDMovement.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
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
 * @author A-1347
 * 
 */
@Table(name = "ULDMVTMST")
@Entity
public class ULDMovement {
 
	private ULDMovementPK uldMovementPK;

	private String isUpdateCurrentStation;

	private String currentStation;

	private String remark;

	private Calendar lastUpdatedTime;
	
	//Added by AG
	
	private Calendar movementDate;
	
	

	private String lastUpdatedUser;

	/**
	 * Set<ULDMovementDetails>
	 */
	private Set<ULDMovementDetail> uldMovementDetails;

	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	/**
	 * Default constructor
	 * 
	 */
	public ULDMovement() {

	}

	/**
	 * 
	 * @param uldMovementVO
	 * @throws SystemException
	 */
	public ULDMovement(ULDMovementVO uldMovementVO) throws SystemException {
		log.entering("INSIDE THE ULD", "MOVEMENT");
		log.entering("INSIDE THE ULD", "MOVEMENT");
		log.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldMovementVO.getCompanyCode());
		log.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldMovementVO.getMovementSequenceNumber());
		log
				.log(Log.FINE, "CHECK>>>>>>>>>>>>>>>>", uldMovementVO.getUldNumber());
		populatepk(uldMovementVO.getCompanyCode(),
				uldMovementVO.getUldNumber(), uldMovementVO
						.getMovementSequenceNumber());
		populateAttributes(uldMovementVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param movementSequenceNumber
	 */
	private void populatepk(String companyCode, String uldNumber,
			long movementSequenceNumber) {
		ULDMovementPK pk = new ULDMovementPK();
		pk.setCompanyCode(companyCode);
		pk.setUldNumber(uldNumber);
		pk.setMovementSequenceNumber(movementSequenceNumber);
		this.setUldMovementPK(pk);
	}

	/**
	 * 
	 * @param uldMovementVO
	 * @throws SystemException
	 */
	private void populateAttributes(ULDMovementVO uldMovementVO)
			throws SystemException {
		 if(uldMovementVO.getLastUpdatedTime()!=null){
			this.setMovementDate(uldMovementVO.getLastUpdatedTime());
		 }				
		this.setLastUpdatedUser(uldMovementVO.getLastUpdatedUser());
		log.log(Log.FINE, "uldMovementVO.getUpdateCurrentStation()",
				uldMovementVO.getUpdateCurrentStation());
		this
				.setUpdateCurrentStation(uldMovementVO
						.getUpdateCurrentStation() ? "Y" : "N");
		this.setCurrentStation(uldMovementVO.getCurrentStation());
		this.setRemark(uldMovementVO.getRemark());
	}

	/**
	 * This method is used to find the business Object
	 * @param companyCode
	 * @param uldNumber
	 * @param movementSequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDMovement find(String companyCode, String uldNumber,
			long movementSequenceNumber)throws SystemException { 
		ULDMovement uldMovement=null;
		ULDMovementPK movementPK =new ULDMovementPK();
		movementPK.setCompanyCode(companyCode);
		movementPK.setMovementSequenceNumber(movementSequenceNumber);
		movementPK.setUldNumber(uldNumber);
		EntityManager em = PersistenceController.getEntityManager();
		try{
			uldMovement = em.find(ULDMovement.class, movementPK);
		}catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode(),finderException);
		}
		
		
		return uldMovement;
	}

	/**
	 * This method is used to update the BO
	 * @param uldMovementVO
	 * @throws SystemException
	 */
	public void update(ULDMovementVO uldMovementVO) throws SystemException {
		populateAttributes(uldMovementVO);
	}

	/**
	 * This method is used to remove the BO
	 * @throws SystemException
	 */
	 public void remove() throws SystemException {
		 EntityManager em = PersistenceController.getEntityManager();
    	 log.log(Log.INFO,"!!!!GOING TO REMOVE THIS");
    	 try{
    		em.remove(this);
    	 }catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	 }
	 }
	
	/**
	 * This method retrieves the ULDMovementHistory
	 * 
	 * @param uldMovementFilterVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDMovementDetailVO> findULDMovementHistory(
			ULDMovementFilterVO uldMovementFilterVO, int displayPage)
			throws SystemException {
		Page<ULDMovementDetailVO> uldMovementDetailList = null;
		return constructDAO().findULDMovementHistory(uldMovementFilterVO, displayPage);
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param movementSeqnumber
	 * @return
	 */
	public static HashMap<Long,Integer> findULDCountsForMovement(String companyCode , Collection<Long> seNos)
	throws SystemException {
		try{
			return constructDAO().findULDCountsForMovement(companyCode,seNos);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 
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
	 * This method finds the currentULDDetails
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDValidationVO findCurrentULDDetails(String companyCode,
			String uldNumber) throws SystemException {
		ULDValidationVO uldValidationVO = null;
		return constructDAO().findCurrentULDDetails(companyCode, uldNumber);
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "movementSequenceNumber", column = @Column(name = "MVTSEQNUM")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM"))})
	/**
	 * @return Returns the uldMovementPK.
	 */
	public ULDMovementPK getUldMovementPK() {
		return uldMovementPK;
	}

	/**
	 * @param uldMovementPK
	 *            The uldMovementPK to set.
	 */
	public void setUldMovementPK(ULDMovementPK uldMovementPK) {
		this.uldMovementPK = uldMovementPK;
	}

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
	 * @return Returns the pointOfLading.
	 */

	/**
	 * @return Returns the remark.
	 */
	@Column(name = "RMK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the currentStation.
	 */
	@Column(name = "CURARP")
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation
	 *            The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

	/**
	 * @return Returns the isUpdateCurrentStation.
	 */
	@Column(name = "UPDCURARPFLG")
	public String isUpdateCurrentStation() {
		return isUpdateCurrentStation;
	}

	/**
	 * @param isUpdateCurrentStation
	 *            The isUpdateCurrentStation to set.
	 */
	public void setUpdateCurrentStation(String isUpdateCurrentStation) {
		this.isUpdateCurrentStation = isUpdateCurrentStation;
	}

	/**
	 * @return Returns the movementSequenceNumber.
	 */
	/**
	 * @return Returns the uldMovementDetails.
	 */

	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "MVTSEQNUM", referencedColumnName = "MVTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false) })
	public Set<ULDMovementDetail> getUldMovementDetails() {
		return uldMovementDetails;
	}

	/**
	 * @param uldMovementDetails
	 *            The uldMovementDetails to set.
	 */
	public void setUldMovementDetails(Set<ULDMovementDetail> uldMovementDetails) {
		this.uldMovementDetails = uldMovementDetails;
	}
/**
 * 
 * @return
 */
	@Column(name = "LSTMVTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getMovementDate() {
		return movementDate;
	}
/**
 * 
 * @param movementDate
 */
	public void setMovementDate(Calendar movementDate) {
		this.movementDate = movementDate;
	}
	
	
	
}
