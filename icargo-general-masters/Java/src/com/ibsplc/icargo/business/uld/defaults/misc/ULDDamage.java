/*
 * ULDDamage.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;


import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDDMG")
@Entity

public class ULDDamage {
	
	private Log log = LogFactory.getLogger("ULD");
	
    private ULDDamagePK uldDamagePK;
    private long damageReferenceNumber; 
   // private String damageCode;    
    private String damagePosition;
    private String severity;
    private String reportedStation;
    private Calendar closeDate;
    private String damageRemarks;
    
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;  
	private Calendar reportedDate;
	private String pictureFlag;
	
	//Added by Tarun for CRQ AirNZ418
	private String facilityType;
	private String location;
	private String partyType;
	private String party;
	
	private String section;
	private String damageDescription;
	private String DamagePoints;
	//Added by A-8368 as part of user story - IASCB-35533
	private String damageNoticePoint;
	
	
	/**
     * Default constructor
     *
     */
	public ULDDamage() {

	}
	
	public ULDDamage( String companyCode, String ubrNumber , ULDDamageVO uldDamageVO)
	throws SystemException {
		log.entering("ULDDamage","Constructor");
		log.log(Log.INFO, "!!!!!ubrNumber", ubrNumber);
		populatePk(companyCode , ubrNumber);
		populateAttributes(uldDamageVO);
		EntityManager em = PersistenceController.getEntityManager();
		log.log(Log.INFO,"!!!!GOING TO INSERT ULDDAMAGE");
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		uldDamageVO.setSequenceNumber(this.getUldDamagePK().getDamageSequenceNumber());
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 */
	private void populatePk(String companyCode, String uldNumber) {
		log.entering("ULDDamage","populatePk");
		ULDDamagePK damagePK = new ULDDamagePK();
		damagePK.setCompanyCode(companyCode);
		damagePK.setUldNumber(uldNumber);
		this.setUldDamagePK(damagePK);
		log.log(Log.INFO, "!!!!!damagePK", damagePK);
		log.exiting("ULDDamage","populatePk");
	}
	/**
	 * 
	 * @param uldDamageVO
	 * @throws SystemException
	 */
	private void populateAttributes(ULDDamageVO uldDamageVO)
		throws SystemException {
		log.entering("ULDDamage","populateAttributes");
		log.log(Log.FINE, "The Damage Details VO from populate Attributes",
				uldDamageVO);
		LogonAttributes logonAttributes = 
				 ContextUtils.getSecurityContext().getLogonAttributesVO();
		this.setDamageReferenceNumber(uldDamageVO.getDamageReferenceNumber());
		this.setReportedDate(uldDamageVO.getReportedDate());
		if(uldDamageVO.isClosed()){
    		this.setCloseDate(new LocalDate(logonAttributes.getAirportCode() ,Location.ARP, false));
    	}
    	else
    	{
    		this.setCloseDate(null);
    	}
	//	this.setCloseDate((new LocalDate(logonAttributes.getAirportCode() ,Location.ARP, false)));
	//	this.setDamageCode(uldDamageVO.getDamageCode());
		this.setDamagePosition(uldDamageVO.getPosition());
		this.setDamageRemarks(uldDamageVO.getRemarks());
		this.setLastUpdatedUser(uldDamageVO.getLastUpdateUser());
		this.setSeverity(uldDamageVO.getSeverity());
		this.setReportedStation(uldDamageVO.getReportedStation());
		this.setParty(uldDamageVO.getParty());
		this.setPartyType(uldDamageVO.getPartyType());
		this.setFacilityType(uldDamageVO.getFacilityType());
		this.setLocation(uldDamageVO.getLocation());
		if(uldDamageVO.isPicturePresent()){
			this.setPictureFlag("Y");
		}else{
			this.setPictureFlag("N");
		}
		this.setSection(uldDamageVO.getSection());
		this.setDamageDescription(uldDamageVO.getDamageDescription());
		this.setDamagePoints(uldDamageVO.getDamagePoints());
		//Added by A-8368 as part of user story -    IASCB-35533
		this.setDamageNoticePoint(uldDamageVO.getDamageNoticePoint());
		//this.DamagePoints(uldDamageVO)
		
		log.exiting("ULDDamage","populateAttributes");
	}
	
    /**
     * 
     * @param companyCode
     * @param uldNumber
     * @param damageSequenceNumber
     * @return
     * @throws SystemException
     */
	public static ULDDamage find(String companyCode, String uldNumber, 
			long damageSequenceNumber)
	throws SystemException{
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDDamage","find");
		ULDDamage damage = null;
		ULDDamagePK damagePK = new ULDDamagePK();
		damagePK.setCompanyCode(companyCode);
		damagePK.setUldNumber(uldNumber);
		damagePK.setDamageSequenceNumber(damageSequenceNumber);
		EntityManager em = PersistenceController.getEntityManager();
		try{
			damage = em.find(ULDDamage.class , damagePK);
		}catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode());
		}
	    return damage;
	}
	

	/**
	 * 
	 * @param uldDamageVO
	 * @throws SystemException
	 */
    public void update(ULDDamageVO uldDamageVO)
   		throws SystemException {
    	log.entering("ULDDamage","update");
    	log.log(Log.FINE, "The Damage Details VO from update in entity class",
				uldDamageVO);
		LogonAttributes logonAttributes = 
			 ContextUtils.getSecurityContext().getLogonAttributesVO();
    	 
    	if(uldDamageVO.isClosed()){
    		this.setCloseDate(new LocalDate(logonAttributes.getAirportCode() ,Location.ARP, false));
    	}
    	else
    	{
    		this.setCloseDate(null);
    	}
    	this.setReportedDate(uldDamageVO.getReportedDate());
		//this.setDamageCode(uldDamageVO.getDamageCode());
		this.setDamagePosition(uldDamageVO.getPosition());
		this.setDamageRemarks(uldDamageVO.getRemarks());
		this.setLastUpdatedUser(uldDamageVO.getLastUpdateUser());
		this.setLastUpdatedTime(uldDamageVO.getLastUpdateTime());
		this.setSeverity(uldDamageVO.getSeverity());
		this.setReportedStation(uldDamageVO.getReportedStation());
		this.setParty(uldDamageVO.getParty());
		this.setPartyType(uldDamageVO.getPartyType());
		this.setFacilityType(uldDamageVO.getFacilityType());
		this.setLocation(uldDamageVO.getLocation());
		if(uldDamageVO.isPicturePresent()){
			this.setPictureFlag("Y");
		}else{
			this.setPictureFlag("N");
		}
		this.setSection(uldDamageVO.getSection());
		this.setDamageDescription(uldDamageVO.getDamageDescription());
		this.setDamagePoints(uldDamageVO.getDamagePoints());
		//Added by A-8368 as part of userstory - IASCB-35533
		this.setDamageNoticePoint(uldDamageVO.getDamageNoticePoint());
    }

	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	log.entering("ULDDamage","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	log.log(Log.INFO,"!!!!GOING TO REMOVE THIS");
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
    
    /**
     * 
     * @param uldDamageFilterVO
     * @return
     * @throws SystemException
     */
    public static ULDDamageRepairDetailsVO findULDDamageDetails(ULDDamageFilterVO uldDamageFilterVO)
    throws SystemException { 
    	//log.entering("ULDDamage","findULDDamageDetails");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));	
    		return uldDefaultsDAO.findULDDamageDetails(uldDamageFilterVO);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}	
    }
    /**
     * 
     * @param uldDamageFilterVO
     * @return
     * @throws SystemException
     */
    public static ULDDamageRepairDetailsVO findULDDamageRepairDetails(ULDDamageFilterVO uldDamageFilterVO)
    throws SystemException { 
    	//log.entering("ULDDamage","findULDDamageDetails");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));	
    		return uldDefaultsDAO.findULDDamageRepairDetails(uldDamageFilterVO);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}	
    }
    
    /**
     * 
     * @param uldDamageFilterVO
     * @return
     * @throws SystemException
     */
    public static Page<ULDDamageDetailsListVO> findULDDamageList(ULDDamageFilterVO uldDamageFilterVO)
    throws SystemException{
    	//log.entering("ULDDamage","findULDDamageList");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
    		return uldDefaultsDAO.findULDDamageList(uldDamageFilterVO);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    
    /**
     * 
     * @param companyCode
     * @param uldNumber
     * @param pageNumber
     * @return
     * @throws SystemException
     */
    public static Page<ULDDamageReferenceNumberLovVO> 
    	findULDDamageReferenceNumberLov(String companyCode , String uldNumber , int pageNumber)
    throws SystemException{
    	//log.entering("ULDDamage","findULDDamageList");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
    		return uldDefaultsDAO.findULDDamageReferenceNumberLov(companyCode , uldNumber , pageNumber);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    
    /**
     * @return Returns the uldDamagePK.
     */
	@EmbeddedId
	@AttributeOverrides({
	@AttributeOverride(name = "companyCode",column = @Column(name = "CMPCOD")),
	@AttributeOverride(name = "uldNumber",column = @Column(name = "ULDNUM")),
	@AttributeOverride(name = "damageSequenceNumber",column = @Column(name = "DMGSEQNUM")) })
    public ULDDamagePK getUldDamagePK() {
        return uldDamagePK;
    }
    /**
     * @param uldDamagePK The uldDamagePK to set.
     */
    public void setUldDamagePK(ULDDamagePK uldDamagePK) {
        this.uldDamagePK = uldDamagePK;
    }
 
    /**
     * @return Returns the closeDate.
     */
    @Column(name = "CLSDAT")
    @Audit(name = "CloseDate")

	@Temporal(TemporalType.DATE)
    public Calendar getCloseDate() {
        return closeDate;
    }
    /**
     * @param closeDate The closeDate to set.
     */
    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }
    /**
     * @return Returns the damageCode.
     
    @Column(name = "DMGCOD")
    @Audit(name="DamageCode")
    public String getDamageCode() {
        return damageCode;
    }
    /**
     * @param damageCode The damageCode to set.
    
    public void setDamageCode(String damageCode) {
        this.damageCode = damageCode;
    }
    /**
     * @return Returns the damagePosition.
     */
    @Column(name = "DMGPOS")
    @Audit(name="DamagePosition")
    public String getDamagePosition() {
        return damagePosition;
    }
    /**
     * @param damagePosition The damagePosition to set.
     */
    public void setDamagePosition(String damagePosition) {
        this.damagePosition = damagePosition;
    }
    /**
     * @return Returns the damageReferenceNumber.
     */
    @Column(name = "DMGREFNUM")
    public long getDamageReferenceNumber() {
        return damageReferenceNumber;
    }
    /**
     * @param damageReferenceNumber The damageReferenceNumber to set.
     */
    public void setDamageReferenceNumber(long damageReferenceNumber) {
        this.damageReferenceNumber = damageReferenceNumber;
    }
    /**
     * @return Returns the damageRemarks.
     */
    @Column(name = "RMK")
    public String getDamageRemarks() {
        return damageRemarks;
    }
    /**
     * @param damageRemarks The damageRemarks to set.
     */
    public void setDamageRemarks(String damageRemarks) {
        this.damageRemarks = damageRemarks;
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
     * @param lastUpdatedTime The lastUpdatedTime to set.
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
     * @param lastUpdatedUser The lastUpdatedUser to set.
     */
    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    /**
     * @return Returns the reportedStation.
     */
    @Column(name = "RPTARP")
    @Audit(name = "ReportedStation")
    public String getReportedStation() {
        return reportedStation;
    }
    /**
     * @param reportedStation The reportedStation to set.
     */
    public void setReportedStation(String reportedStation) {
        this.reportedStation = reportedStation;
    }
    /**
     * @return Returns the severity.
     */
    @Column(name = "DMGSVT")
    @Audit(name = "Severity")
    public String getSeverity() {
        return severity;
    }
    /**
     * @param severity The severity to set.
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
	 * @return Returns the reportedDate.
	 */
	@Column(name = "DMGRPTDAT")
	@Audit(name = "ReportedDate")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getReportedDate() {
		return reportedDate;
	}

	/**
	 * @param reportedDate The reportedDate to set.
	 */
	public void setReportedDate(Calendar reportedDate) {
		this.reportedDate = reportedDate;
	}

	/**
	 * @return Returns the pictureFlag.
	 */
	@Column(name = "PICFLG")
	public String getPictureFlag() {
		return this.pictureFlag;
	}

	/**
	 * @param pictureFlag The pictureFlag to set.
	 */
	public void setPictureFlag(String pictureFlag) {
		this.pictureFlag = pictureFlag;
	}

	/**
	 * @return the facilityType
	 */
	@Column(name = "FACTYP")
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	//Added by A-8368 as part of user story - IASCB-35533
	/**
	 * @return the damageNoticePoint
	 */
	@Column(name = "DMGNOTPNT")
	public String getDamageNoticePoint() {
		return damageNoticePoint;
	}
	/**
	 * @param damageNoticePoint the damageNoticePoint to set
	 */
	public void setDamageNoticePoint(String damageNoticePoint) {
		this.damageNoticePoint = damageNoticePoint;
	}

	/**
	 * @return the location
	 */
	@Column(name = "LOC")
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the party
	 */
	@Column(name = "PTYCOD")
	public String getParty() {
		return party;
	}

	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}

	/**
	 * @return the partyType
	 */
	@Column(name = "PTYTYP")
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
	/**
	 * @return the damageDescription
	 */
	@Column(name = "DMGDES")
	public String getDamageDescription() {
		return damageDescription;
	}

	/**
	 * @param damageDescription the damageDescription to set
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}

	/**
	 * @return the damagePoints
	 */
	@Column(name = "DMGPNT")
	public String getDamagePoints() {
		return DamagePoints;
	}

	/**
	 * @param damagePoints the damagePoints to set
	 */
	public void setDamagePoints(String damagePoints) {
		DamagePoints = damagePoints;
	}

	/**
	 * @return the section
	 */
	@Column(name = "DMGSEC")
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * removing all images associated with and damage
	 * at the time of removing a single (or more)
	 * @param uldDamageVO
	 * @throws SystemException 
	 */
	public void removeDamageImages(ULDDamageVO uldDamageVO) throws SystemException{
		log.entering("ULDDamage","removeDamageImages");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));	
    		uldDefaultsDAO.removeDamageImages(uldDamageVO);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    	log.exiting("ULDDamage","removeDamageImages");
	}// end of removeDamageImages
}
