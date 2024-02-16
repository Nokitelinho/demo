/*
 * ULDDiscrepancy.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;


import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.ULDObjectQueryInterface;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */
@Table(name="ULDDIS")
@Entity

public class ULDDiscrepancy {
    
	private Log log = LogFactory.getLogger("ULD");
	
    private ULDDiscrepancyPK uldDiscrepancyPK;
    
    private String discrepancyCode;
    private Calendar discrepancyDate;
    private String reportingStation; 
    private String remark;
    
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	
	private String scmSequenceNumber;
	private String facilityType;
	private String location;
	private String closeStatus;
	private String agentCode;
	

	




	/**
     * Default constructor
     *
     */
	public ULDDiscrepancy() {

	}

	/**
	 * @return String Returns the scmSequenceNumber.
	 */
	@Column(name = "SCMSEQNUM")
	public String getScmSequenceNumber() {
		return this.scmSequenceNumber;
	}

	/**
	 * @param scmSequenceNumber The scmSequenceNumber to set.
	 */
	public void setScmSequenceNumber(String scmSequenceNumber) {
		this.scmSequenceNumber = scmSequenceNumber;
	}

	/**
	 * 
	 * @param uldDiscrepancysVO
	 * @throws SystemException
	 */
	public ULDDiscrepancy(
	        ULDDiscrepancyVO uldDiscrepancysVO)
		throws SystemException {
		log.entering("ULDDiscrepency","ULDDiscrepency");
		populatePk(uldDiscrepancysVO.getCompanyCode() , 
				uldDiscrepancysVO.getUldNumber(),uldDiscrepancysVO.getDiscrepencyCode());
		populateAttributes(uldDiscrepancysVO);
		EntityManager em = PersistenceController.getEntityManager();
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 */ 
	private void populatePk(
	        String companyCode, String uldNumber,String discrepancyCode) {
		log.entering("ULDDiscrepency","populatePk");
		ULDDiscrepancyPK uldDeiscrepencyPK = new ULDDiscrepancyPK();
		uldDeiscrepencyPK.setCompanyCode(companyCode);
		uldDeiscrepencyPK.setUldNumber(uldNumber);
		this.setUldDiscrepancyPK(uldDeiscrepencyPK);
	}

	/**
	 * 
	 * @param uldDiscrepancyVO
	 * @throws SystemException
	 */
	private void populateAttributes(
			ULDDiscrepancyVO uldDiscrepancyVO)
		throws SystemException {
		log.entering("ULDDiscrepency","populateAttributes");
		setDiscrepancyCode(uldDiscrepancyVO.getDiscrepencyCode());
		setDiscrepancyDate(uldDiscrepancyVO.getDiscrepencyDate().toCalendar());
		setReportingStation(uldDiscrepancyVO.getReportingStation());
		setRemark(uldDiscrepancyVO.getRemarks());
		setLastUpdatedUser(uldDiscrepancyVO.getLastUpdatedUser());
		setScmSequenceNumber(uldDiscrepancyVO.getScmSequenceNumber());
		setCloseStatus(uldDiscrepancyVO.getCloseStatus());
		setFacilityType(uldDiscrepancyVO.getFacilityType());
		setLocation(uldDiscrepancyVO.getLocation());
		setAgentCode(uldDiscrepancyVO.getAgentCode());
		
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static ULDDiscrepancy find(
	        String companyCode, String uldNumber ,String seqeunceNumber)
	throws FinderException ,  SystemException{
	    ULDDiscrepancyPK disPK = new ULDDiscrepancyPK();
	    disPK.setCompanyCode(companyCode); 
	    disPK.setUldNumber(uldNumber);
	    disPK.setSequenceNumber(Long.parseLong(seqeunceNumber));
	    EntityManager em  = PersistenceController.getEntityManager() ;
    	return em.find(ULDDiscrepancy.class , disPK);
	}
	

	/**
	 * 
	 * @param uldDiscrepancyVOs
	 * @throws SystemException
	 */
    public void update(ULDDiscrepancyVO uldDiscrepancyVO)
   		throws SystemException {
    	log.entering("ULDDiscrepancy","update");
    	this.setLastUpdatedTime(uldDiscrepancyVO.getLastUpdatedTime());
    	populateAttributes(uldDiscrepancyVO);
    	
    }

	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	log.entering("ULDDiscrepancy","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
    
    /**
     * 
     * @param uldDiscrepancyVO
     * @throws SystemException
     */
     public void saveULDDiscrepancy(ULDDiscrepancyVO uldDiscrepancyVO)   
     throws SystemException {   
     } 
    
    
     /**
      * 
      * @param discrepancyFilterVO
      * @return
      * @throws SystemException
      */
     public static Page<ULDDiscrepancyVO> listULDDiscrepancyDetails(ULDDiscrepancyFilterVO discrepancyFilterVO) 
     throws SystemException {   
    	 EntityManager em = PersistenceController.getEntityManager();
     	try{
     		ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		return uldDefaultsDAO.listULDDiscrepancyDetails(discrepancyFilterVO);
     	}catch(PersistenceException persistenceException){
     		throw new SystemException(persistenceException.getErrorCode());
     	}
     } 
     /**
      * 
      * A-1950
      * @param companyCode
      * @throws SystemException
      */
     public static Collection<String> findMissingULDs(String companyCode , int period)
      throws SystemException {   
        	 EntityManager em = PersistenceController.getEntityManager();
         	try{
         		ULDDefaultsDAO uldDefaultsDAO = 
         			ULDDefaultsDAO.class.cast(
         					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
         		return uldDefaultsDAO.findMissingULDs(companyCode , period);
         	}catch(PersistenceException persistenceException){
         		throw new SystemException(persistenceException.getErrorCode());
         	}
         }  /**
      * @return Returns the uldDiscrepancyPK.
      */
 	
 	@EmbeddedId
	@AttributeOverrides({
 		@AttributeOverride(name = "companyCode",
 				column = @Column(name = "CMPCOD")),
 		@AttributeOverride(name = "uldNumber", 
 				column = @Column(name = "ULDNUM")),
 		@AttributeOverride(name = "discrepancyCode", 
 				column = @Column(name = "DISCOD")),
		@AttributeOverride(name = "sequenceNumber", 
 				column = @Column(name = "SEQNUM"))})
     public ULDDiscrepancyPK getUldDiscrepancyPK() {
         return uldDiscrepancyPK;
     }
     /**
      * @param uldDiscrepancyPK The uldDiscrepancyPK to set.
      */
     public void setUldDiscrepancyPK(ULDDiscrepancyPK uldDiscrepancyPK) {
         this.uldDiscrepancyPK = uldDiscrepancyPK;
     }
    /**
     * @return Returns the discrepancyDate.
     */
     @Column(name = "DISDAT")
     @Audit(name = "DiscrepancyDate")

	@Temporal(TemporalType.DATE)
    public Calendar getDiscrepancyDate() {
        return discrepancyDate;
    }
    /**
     * @param discrepancyDate The discrepancyDate to set.
     */
    public void setDiscrepancyDate(Calendar discrepancyDate) {
        this.discrepancyDate = discrepancyDate;
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
     * @return Returns the remark.
     */
    @Column(name = "RMK")    
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the reportingStation.
     */
    @Column(name = "RPTARP")    
    @Audit(name = "ReportingStation")
    public String getReportingStation() {
        return reportingStation;
    }
    /**
     * @param reportingStation The reportingStation to set.
     */
    public void setReportingStation(String reportingStation) {
        this.reportingStation = reportingStation;
    }

	/*@Column(name = "DISCOD")
	*//**
	 * @return Returns the discrepancyCode.
	 *//*
	@Audit(name = "DiscrepancyCode")
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}

	*//**
	 * @param discrepancyCode The discrepancyCode to set.
	 *//*
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}*/
    
	/**
	 * @return
	 */
    @Column(name = "FACTYP")    
    public String getFacilityType() {
		return facilityType;
	}
	/**
	 * 
	 * @param facilityType
	 */
    public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

    /**
     * 
     * @return
     */
    @Column(name = "LOC")    
	public String getLocation() {
		return location;
	}

	/**
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * 
	 * @return
	 */
	
	@Column(name = "CLSSTA")    
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * 
	 * @param closeStatus
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
	/**
	 * 
	 * @return
	 */
	@Column(name = "DISCOD")    
	public String getDiscrepancyCode() {
		return discrepancyCode;
	}
	/**
	 * 
	 * @param discrepancyCode
	 */
	public void setDiscrepancyCode(String discrepancyCode) {
		this.discrepancyCode = discrepancyCode;
	}
	/**
	 * 
	 * @return
	 */
	@Column(name = "AGTCOD")    
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * 
	 * @param agentCode
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 
	 * @param companyCode
	 * @param uldnos
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDDiscrepancy> findMissingULDObjects(String companyCode , int period)
	throws SystemException{
		try{
			return constructULDObjectDAO().findMissingULDObjects(companyCode , period);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
//	Changed by a-3459 for INT ULD593
	//ULDObjectDAO is changed to ULDObjectQueryInterface
	private static ULDObjectQueryInterface constructULDObjectDAO()
	throws SystemException,PersistenceException{
		return PersistenceController.getEntityManager().getObjectQueryDAO("uld.defaults");
	}
	/**
	 * 
	 * @param uLDDiscrepancyFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDDiscrepancyVO findULDStockStatusForHHT(ULDDiscrepancyFilterVO 
			uLDDiscrepancyFilterVO)throws SystemException{
		EntityManager em = PersistenceController.getEntityManager();
     	try{
     		ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		return uldDefaultsDAO.findULDStockStatusForHHT(uLDDiscrepancyFilterVO);
     	}catch(PersistenceException persistenceException){
     		throw new SystemException(persistenceException.getErrorCode());
     	}
	}
	
	public static Page<String> populateLocationLov(String companyCode, 
			int displayPage, String comboValue,String airportCode)throws SystemException{
		EntityManager em = PersistenceController.getEntityManager();
     	try{
     		ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		return uldDefaultsDAO.populateLocationLov(companyCode,displayPage,comboValue,airportCode);
     	}catch(PersistenceException persistenceException){
     		throw new SystemException(persistenceException.getErrorCode());
     	}
	}
	/**
	 * @author A-2667
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDDiscrepancy
	 * @throws SystemException
	 */
	public static Collection<ULDDiscrepancy> findULDDiscrepanciesObjects(String companyCode,String uldNumber,String reportingStation)
	throws SystemException {
		try {
			return constructULDObjectDAO().findULDDiscrepanciesObjects(companyCode,uldNumber,reportingStation);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
	}
}
	
	/**
	 * @author a-3278
	 * @param companyCode
	 * @param uldnos
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDDiscrepancy> findULDDiscrepancies(String companyCode,
			Collection<String> uldnos) throws SystemException {
		try {
			return constructULDObjectDAO().findULDDiscrepancies(companyCode,
					uldnos);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
     * 
     * @param discrepancyFilterVO
     * @return
     * @throws SystemException
     */
    public static Collection<ULDDiscrepancyVO> listULDDiscrepancy(ULDDiscrepancyFilterVO discrepancyFilterVO) 
    throws SystemException {   
   	 EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(
    					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
    		return uldDefaultsDAO.listULDDiscrepancy(discrepancyFilterVO);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return String
	 * @throws SystemException
	 */
	public static String findpolLocationForULD(String companyCode,String uldNumber)
	throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		return uldDefaultsDAO.findpolLocationForULD(companyCode,uldNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param period
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> sendAlertForDiscrepancy(String companyCode,int period)
	throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		return uldDefaultsDAO.sendAlertForDiscrepancy(companyCode,period);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param airport
	 * @param uldnos
	 * @throws SystemException
	 */
	public static void updateULDDiscrepancy(String companyCode, String airport , Collection<String> uldnos)
	throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		uldDefaultsDAO.updateULDDiscrepancy(companyCode,airport,uldnos);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDDiscrepancy> findULDDiscrepanciesObjectsAtAirport(String companyCode,String airport)
	throws SystemException {
		try {
			return constructULDObjectDAO().findULDDiscrepanciesObjectsAtAirport(companyCode,airport);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	public static Map<String,ULDDiscrepancyVO> findUldDiscrepancyDetails(
			ULDDiscrepancyFilterVO discrepancyFilterVO) throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uldDefaultsDAO
					.findUldDiscrepancyDetails(discrepancyFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	} 
}
