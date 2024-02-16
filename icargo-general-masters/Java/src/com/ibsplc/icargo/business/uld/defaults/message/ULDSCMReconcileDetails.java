/*
 * ULDSCMReconcileDetails.java Created on Aug 01, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-2048
 *
 */
@Staleable
@Table(name = "ULDSCMMSGRECDTL")
@Entity
public class ULDSCMReconcileDetails {
	
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
	private ULDSCMReconcileDetailsPK detailsPK ;
	
    private String errorCode;
    
    private String facilityType;
    
    private String location;
    private String uldStatus;
    
    
	
	/**
	 * @return ULDFlightMessageReconcileDetailsPK Returns the detailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
	    @AttributeOverride(name="uldNumber", column=@Column(name="ULDNUM"))})  
	public ULDSCMReconcileDetailsPK getDetailsPK() {
		return this.detailsPK;
	}
	/**
	 * @param detailsPK The detailsPK to set.
	 */
	public void setDetailsPK(ULDSCMReconcileDetailsPK detailsPK) {
		this.detailsPK = detailsPK;
	}
	/**
	 * @return String Returns the errorCode.
	 */
	 @Column(name="ERRCOD")
	public String getErrorCode() {
		return this.errorCode;
	}
	/**
	 * @param errorCode The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * 
	 *
	 */
	public ULDSCMReconcileDetails() {
		
	}
	
	
	/**
	 * @return the uldStatus
	 */
	@Column(name="ULDSTKSTA")
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * @param uldStatus the uldStatus to set
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	public ULDSCMReconcileDetails(ULDSCMReconcileDetailsVO
			 detailsVO )throws SystemException{
		log.entering("ULDSCMReconcileDetails","ULDSCMReconcileDetails");
		populatePK(detailsVO);
		populateAttributes(detailsVO);
		
		try{
			  PersistenceController.getEntityManager().persist(this);
			}catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());	
			}
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populatePK(ULDSCMReconcileDetailsVO detailsVO) {
		log.entering("ULDSCMReconcileDetails","populatePk");
		ULDSCMReconcileDetailsPK reconcileDetailsPK 
			= new ULDSCMReconcileDetailsPK();
		reconcileDetailsPK.setAirportCode(detailsVO.getAirportCode()); 
		log.log(Log.FINE, " ULDSCMReconcileDetails  ----Company code->>>",
				detailsVO.getCompanyCode());
		reconcileDetailsPK.setCompanyCode(detailsVO.getCompanyCode());
		log.log(Log.FINE, " AirlineIdentifer---%%%%---->>>", detailsVO.getAirlineIdentifier());
		reconcileDetailsPK.setAirlineIdentifier(detailsVO.getAirlineIdentifier());
		
		reconcileDetailsPK.setSequenceNumber(detailsVO.getSequenceNumber());
		reconcileDetailsPK.setUldNumber(detailsVO.getUldNumber());
		setDetailsPK(reconcileDetailsPK);
		log.log(Log.FINE, " Airline identifier-%%%%----->>>", this.getDetailsPK().getAirlineIdentifier());
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populateAttributes(ULDSCMReconcileDetailsVO detailsVO) {
		log.entering("ULDSCMReconcileDetails","populateAttributes");
		this.setErrorCode(detailsVO.getErrorCode());
		this.setFacilityType(detailsVO.getFacilityType());
		this.setLocation(detailsVO.getLocation());
		this.setUldStatus(detailsVO.getUldStatus());
		
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void update(ULDSCMReconcileDetailsVO detailsVO) {
		log.entering("ULDSCMReconcileDetails","update");
		populateAttributes(detailsVO);
	}
	/**
	 * 
	 * @param detailsVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDSCMReconcileDetails find(
			ULDSCMReconcileDetailsVO detailsVO)
	        throws SystemException {
		ULDSCMReconcileDetailsPK reconcileDetailsPK = 
			new ULDSCMReconcileDetailsPK();
		
		reconcileDetailsPK.setAirportCode(detailsVO.getAirportCode());
		reconcileDetailsPK.setCompanyCode(detailsVO.getCompanyCode());
		reconcileDetailsPK.setAirlineIdentifier(detailsVO.getAirlineIdentifier());
		reconcileDetailsPK.setSequenceNumber(detailsVO.getSequenceNumber());
		reconcileDetailsPK.setUldNumber(detailsVO.getUldNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDSCMReconcileDetails.class,
					reconcileDetailsPK);
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
	/**
	 * @return Returns the facilityType.
	 */
	 @Column(name="FACTYP") 
	public String getFacilityType() {
		return facilityType;
	}
	 /**
		 * @param facilityType The facilityType to set.
		 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return Returns the location.
	 */
	 @Column(name="LOC")
	public String getLocation() {
		return location;
	}
	 /**
		 * @param location The location to set.
		 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	
    
}
