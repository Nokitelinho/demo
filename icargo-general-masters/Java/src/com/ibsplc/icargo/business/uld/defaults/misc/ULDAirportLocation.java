/*
 * ULDAirportLocation.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;


import java.util.ArrayList;
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

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
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
 * @author A-1496
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDARPLOC")
@Entity
public class ULDAirportLocation {
    
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
	private ULDAirportLocationPK locationPK;
	private String facilityDescription;
	private String facilityCode;
	private String defaultFlag;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private String content;
	/**
	 * @return String Returns the facilityCode.
	 */
	 @Column(name="FACCOD")
	public String getFacilityCode() {
		return this.facilityCode;
	}
	/**
	 * @param facilityCode The facilityCode to set.
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * @return String Returns the facilityDescription.
	 */
	 @Column(name="FACDES")
	public String getFacilityDescription() {
		return this.facilityDescription;
	}
	/**
	 * @param facilityDescription The facilityDescription to set.
	 */
	public void setFacilityDescription(String facilityDescription) {
		this.facilityDescription = facilityDescription;
	}
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	 @Column(name="LSTUPDTIM")
	 @Version

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	 @Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return String Returns the defaultFlag.
	 */
	 @Column(name="DEFFLG")
	public String getDefaultFlag() {
		return this.defaultFlag;
	}
	/**
	 * @return the content
	 */
	 @Column(name="CNT")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @param defaultFlag The defaultFlag to set.
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	/**
	 * @return ULDAirportLocationPK Returns the locationPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
	    @AttributeOverride(name="facilityType", column=@Column(name="FACTYP")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))})  
	public ULDAirportLocationPK getLocationPK() {
		return this.locationPK;
	}
	/**
	 * @param locationPK The locationPK to set.
	 */
	public void setLocationPK(ULDAirportLocationPK locationPK) {
		this.locationPK = locationPK;
	}
	/**
	 * 
	 *
	 */
	public ULDAirportLocation() {}
	/**
	 * 
	 * @param locationVO
	 * @throws SystemException
	 */
	public ULDAirportLocation(ULDAirportLocationVO locationVO)
	throws SystemException {	
		log.entering("ULDAgreement","ULDAirportLocation");
		populatePk(locationVO);
		populateAttributes(locationVO);
		try{
		  PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());	
		}
	}
	/**
	 * 
	 * @param locationVO
	 */
	public void populatePk(ULDAirportLocationVO locationVO) {
		log.entering("ULDAgreement","populatePk");
		ULDAirportLocationPK locPK = new ULDAirportLocationPK();
		locPK.setCompanyCode(locationVO.getCompanyCode());
		locPK.setAirportCode(locationVO.getAirportCode());
		locPK.setFacilityType(locationVO.getFacilityType());
		setLocationPK(locPK);
	}
	/**
	 * 
	 * @param locationVO
	 */
	public void populateAttributes(ULDAirportLocationVO locationVO) {
		log.entering("ULDAgreement","populateAttributes");
		setFacilityCode(locationVO.getFacilityCode());
		setFacilityDescription(locationVO.getDescription());
		setDefaultFlag(locationVO.getDefaultFlag());
		setContent(locationVO.getContent());
		setLastUpdatedUser(locationVO.getLastUpdatedUser());
	}
	/**
	 * 
	 * @param locationVO
	 */
	public void update(ULDAirportLocationVO locationVO) {
		log.entering("ULDAirportLocation=============","\n update");
		this.setLastUpdatedTime(locationVO.getLastUpdatedTime().toCalendar());		
		populateAttributes(locationVO);
	}
	/**
	 * 
	 * @param locationVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDAirportLocation find(ULDAirportLocationVO 
			locationVO)throws SystemException{
		ULDAirportLocationPK locPK = new ULDAirportLocationPK();
		locPK.setCompanyCode(locationVO.getCompanyCode());
		locPK.setAirportCode(locationVO.getAirportCode());
		locPK.setFacilityType(locationVO.getFacilityType());
		locPK.setSequenceNumber(locationVO.getSequenceNumber());
		EntityManager entityManager = 
	    	PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDAirportLocation.class,locPK);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * 
	 * @throws SystemException
	
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
	
	//////////
	/**
	 * @param companyCode 
	 * @param airportCode 
	 * @param facilityType 
	 * @return 
	 * @throws SystemException 
	 * 
	 */
	 public static Collection<ULDAirportLocationVO> listULDAirportLocation(String companyCode,
				String airportCode,String facilityType)
				throws SystemException{
		 try {
				EntityManager em = PersistenceController.getEntityManager();
				ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
				(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uLDDefaultsDAO.listULDAirportLocation(companyCode,airportCode,facilityType);
			}catch (PersistenceException e) {
				e.getErrorCode();
				throw new SystemException(e.getErrorCode());
			}
	 }
	 /**
	  * 
	  * @param companyCode
	  * @param airportCode
	  * @return
	  * @throws SystemException
	  * method signature changed by A-2408...0n 05Aug08
	  * this return a collecton of defaults vos for a set of contents in an airport
	  */
	 public static Collection<ULDAirportLocationVO> findDefaultFlag(String companyCode,
				String airportCode,ArrayList<String> contents)
				throws SystemException{
		 try {
				EntityManager em = PersistenceController.getEntityManager();
				ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
				(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uLDDefaultsDAO.findDefaultFlag(companyCode,airportCode,contents);
			}catch (PersistenceException e) {
				e.getErrorCode();
				throw new SystemException(e.getErrorCode());
			}
	 }
	 /**
	  * 
	  * @param companyCode
	  * @param location
	 * @param airportCode 
	  * @return
	  * @throws SystemException
	  */
	 public static int checkForULDLocation(String companyCode,String 
			 location,String airportCode)throws SystemException{
		 try {
				EntityManager em = PersistenceController.getEntityManager();
				ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
				(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uLDDefaultsDAO.checkForULDLocation(companyCode,location,airportCode);
			}catch (PersistenceException e) {
				e.getErrorCode();
				throw new SystemException(e.getErrorCode());
			}
	 }
	 
	 /**
	  * 
	  * @param locationVOs
	  * @return
	  * @throws SystemException
	  */
	 public static Collection<String> checkForDuplicateFacilityCode(String companyCode,String airportCode,String facilityCode,String facilityType,String content)
	 throws SystemException{
		 try {
				EntityManager em = PersistenceController.getEntityManager();
				ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
				(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uLDDefaultsDAO.checkForDuplicateFacilityCode(companyCode,airportCode,facilityCode,facilityType,content);
			}catch (PersistenceException e) {
				e.getErrorCode();
				throw new SystemException(e.getErrorCode());
			}
	 }
	 /**
	 * @param companyCode
	 * @param airportCode
	 * @param facility
	 * @return
	 * @throws SystemException
	 */
	public static ULDAirportLocationVO findLocationforFacility(String companyCode,
				String airportCode,String facility) throws SystemException{
		 try {
				EntityManager em = PersistenceController.getEntityManager();
				ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
				(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
				return uLDDefaultsDAO.findLocationforFacility(companyCode,airportCode,facility);
			}catch (PersistenceException e) {
				e.getErrorCode();
				throw new SystemException(e.getErrorCode());
			}
	 }

	/**
	 *
	 * Added by A-9558 as part of IASCB-55163
	 * @param companyCode
	 * @param airportCode
	 * @param locationCode
	 * @param facilityTypeCode
	 * @return
	 * @throws SystemException
	 *
	 */
	public static boolean validateULDAirportLocation(String companyCode, String airportCode,String locationCode,String facilityTypeCode)
			throws SystemException{
		try {
			EntityManager em = PersistenceController.getEntityManager();
			ULDDefaultsDAO uLDDefaultsDAO = ULDDefaultsDAO.class.cast
					(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uLDDefaultsDAO.validateULDAirportLocation(companyCode,airportCode,locationCode,facilityTypeCode);
		}catch (PersistenceException e) {
			e.getErrorCode();
			throw new SystemException(e.getErrorCode());
		}
	}

}
