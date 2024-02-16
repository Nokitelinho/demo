/*
 * ULDPoolOwner.java Created on Aug 11, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Collection;


import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDPOLOWNMST")
@Entity
public class ULDPoolOwner {
    
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	private static final String MODULE = "uld.defaults";
	
	private ULDPoolOwnerPK poolOwnerPK ;
	private String remarks;
	
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private String airport;
	
	private Set<ULDPoolSegmentExceptions> segmentExceptions;
	
	
	/**
	 * @return the remarks
	 */
	@Column(name="RMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
			
	/**
	 * @return Set<ULDPoolOwnerDetails> Returns the poolOwnerDetails.
	 */
	@OneToMany
    @JoinColumns( {
	  @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	  @JoinColumn(name = "ARLONE", referencedColumnName = "ARLONE", insertable=false, updatable=false),
	  @JoinColumn(name = "ARLTWO", referencedColumnName = "ARLTWO", insertable=false, updatable=false),
	  @JoinColumn(name = "SERNUM", referencedColumnName = "SERNUM", insertable=false, updatable=false)
	  })
	public Set<ULDPoolSegmentExceptions> getSegmentExceptions() {
		return this.segmentExceptions;
	}
	/**
	 * @param segmentExceptions The segmentExceptions to set.
	 */
	public void setSegmentExceptions(Set<ULDPoolSegmentExceptions> segmentExceptions) {
		this.segmentExceptions = segmentExceptions;
	}
	/**
	 * @return ULDPoolOwnerPK Returns the poolOwnerPK.
	 */
   @EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="airlineIdentifierOne", column=@Column(name="ARLONE")),
	    @AttributeOverride(name="airlineIdentifierTwo", column=@Column(name="ARLTWO")),
	    @AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))
	    }) 
	public ULDPoolOwnerPK getPoolOwnerPK() {
		return this.poolOwnerPK;
	}
	/**
	 * @param poolOwnerPK The poolOwnerPK to set.
	 */
	public void setPoolOwnerPK(ULDPoolOwnerPK poolOwnerPK) {
		this.poolOwnerPK = poolOwnerPK;
	}
	
	
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name="LSTUPDTIM")
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
	 * 
	 *
	 */
	public ULDPoolOwner() {
		
	}
	/**
	 * 
	 * @param poolOwnerVO
	 * @throws SystemException
	 */
	public ULDPoolOwner(ULDPoolOwnerVO poolOwnerVO)
	throws SystemException{
		log.entering("ULDPoolOwner","ULDPoolOwner");
		populatePK(poolOwnerVO);
		populateAttributes(poolOwnerVO);
		try{
			  PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());	
		}							
		populateChildren(poolOwnerVO, poolOwnerPK);
	}
	/**
	 * 
	 * @param poolOwnerVO
	 */
	public void populatePK(ULDPoolOwnerVO poolOwnerVO) {
		log.entering("ULDPoolOwner","populatePK");
		ULDPoolOwnerPK poolPK = new ULDPoolOwnerPK();
		poolPK.setCompanyCode(poolOwnerVO.getCompanyCode());
		poolPK.setAirlineIdentifierOne(poolOwnerVO.getAirlineIdentifierOne());
		poolPK.setAirlineIdentifierTwo(poolOwnerVO.getAirlineIdentifierTwo());
		//poolPK.setAirport(poolOwnerVO.getAirport());
		this.setPoolOwnerPK(poolPK);
	}
	/**
	 * 
	 * @param poolOwnerVO
	 */
	public void populateAttributes(ULDPoolOwnerVO poolOwnerVO) throws SystemException
	{
		log.entering("ULDPoolOwner","populateAttributes");
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		this.setRemarks(poolOwnerVO.getRemarks() );
		this.setLastUpdatedUser(logon.getUserId());
		this.setAirport(poolOwnerVO.getAirport());  
		log.exiting("ULDPoolOwner", "populate attributes");
	}
	/**
	 * @param poolOwnerVO 
	 * @throws SystemException 
	 * 
	 *
	 */
	public void populateChildren(ULDPoolOwnerVO poolOwnerVO, ULDPoolOwnerPK poolOwnerPK) 
	throws SystemException{
		log.entering("ULDPoolOwner","PopulateChildren");
		if(poolOwnerVO.getPoolSegmentsExceptionsVOs() != null &&
				poolOwnerVO.getPoolSegmentsExceptionsVOs().size() >0) {
			for(ULDPoolSegmentExceptionsVO exceptionsVO : poolOwnerVO.getPoolSegmentsExceptionsVOs()) {
				exceptionsVO.setSerialNumber(poolOwnerPK.getSerialNumber());
				new ULDPoolSegmentExceptions(exceptionsVO);
			}
		}
	}
	/**
	 * 
	 * @param poolOwnerVO
	 * @throws SystemException 
	 */
	public void update(ULDPoolOwnerVO poolOwnerVO)
	throws SystemException{
		log.entering("ULDPoolOwner","update");
		this.setLastUpdatedTime(poolOwnerVO.getLastUpdatedTime());
		populateAttributes(poolOwnerVO);
		updateChildren(poolOwnerVO);
	}
	
	/**
	 * 
	 * @param poolOwnerVO
	 * @throws SystemException
	 */
	public void updateChildren(ULDPoolOwnerVO poolOwnerVO)throws SystemException {
		log.entering("ULDPoolOwner","updateChildren");
		if(poolOwnerVO.getPoolSegmentsExceptionsVOs() != null &&
				poolOwnerVO.getPoolSegmentsExceptionsVOs().size() >0) {
			for(ULDPoolSegmentExceptionsVO exceptionsVO :poolOwnerVO.getPoolSegmentsExceptionsVOs()) {
				if(ULDPoolSegmentExceptionsVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(exceptionsVO.getOperationFlag())) {
					
					ULDPoolSegmentExceptions uLDPoolSegmentExceptions =null;
					uLDPoolSegmentExceptions = ULDPoolSegmentExceptions.find(exceptionsVO);
					uLDPoolSegmentExceptions.remove();
				}
			}
			for(ULDPoolSegmentExceptionsVO exceptionsVO :poolOwnerVO.getPoolSegmentsExceptionsVOs()) {
				log.log(Log.INFO, "insideULDPoolOwner  insert loop",
						exceptionsVO.getOperationFlag());
				if(ULDPoolSegmentExceptionsVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(exceptionsVO.getOperationFlag())) {
					log.log(Log.INFO, "insideULDPoolOwner  insert loop====>>>");
					exceptionsVO.setSerialNumber(poolOwnerVO.getSerialNumber());  
					new ULDPoolSegmentExceptions(exceptionsVO);
				}else if(ULDPoolSegmentExceptionsVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(exceptionsVO.getOperationFlag())){
					log.log(Log.INFO, "Inside exceptions Update", exceptionsVO);
					ULDPoolSegmentExceptions uLDPoolSegmentExceptions =null;
					exceptionsVO.setSerialNumber(poolOwnerVO.getSerialNumber());
					uLDPoolSegmentExceptions = ULDPoolSegmentExceptions.find(exceptionsVO);
					uLDPoolSegmentExceptions.update(exceptionsVO);
				}
				
			}
		}
	}
	
	/**
	 * 
	 * @param poolOwnerVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ULDPoolOwner find(ULDPoolOwnerVO poolOwnerVO)
	throws SystemException ,FinderException{
		
		ULDPoolOwnerPK uldPoolOwnerPK = new ULDPoolOwnerPK();
		uldPoolOwnerPK.setCompanyCode(poolOwnerVO.getCompanyCode());
		uldPoolOwnerPK.setAirlineIdentifierOne(poolOwnerVO.getAirlineIdentifierOne());
		uldPoolOwnerPK.setAirlineIdentifierTwo(poolOwnerVO.getAirlineIdentifierTwo());
		uldPoolOwnerPK.setSerialNumber(poolOwnerVO.getSerialNumber());
		//uldPoolOwnerPK.setAirport(poolOwnerVO.getAirport());
		EntityManager entityManager = PersistenceController.getEntityManager();
		return entityManager.find(ULDPoolOwner.class,
				uldPoolOwnerPK);
	}
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		 EntityManager em = PersistenceController.getEntityManager();
   	 log.log(Log.INFO,"!!!!GOING TO REMOVE THIS");
   	 try{
   		log.log(Log.INFO,"!!!!GOING TO REMOVE THIS11111111111111");
   		em.remove(this);
   		log.log(Log.INFO,"!!!!AFTER  REMOVE ");
   	 }catch(RemoveException removeException){
   		throw new SystemException(removeException.getErrorCode());
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
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDPoolOwnerVO> listULDPoolOwner(ULDPoolOwnerFilterVO filterVO)
    throws SystemException{
		try {
            return constructDAO().listULDPoolOwner(filterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
	}
	
	/**
	 * @author A-2408
	 * @param uldPoolOwnerFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkforPoolOwner(ULDPoolOwnerFilterVO uldPoolOwnerFilterVO) throws SystemException{
		try {
            return constructDAO().checkforPoolOwner(uldPoolOwnerFilterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
	}
	/**
	 * @return the airport
	 */
	@Column(name="ARPCOD")
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}  
	
	public static boolean findDuplicatePoolOwnerConfig(ULDPoolOwnerVO uldPoolOwnerVO) throws SystemException{
		try {
            return constructDAO().findDuplicatePoolOwnerConfig(uldPoolOwnerVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
	}
	
}
