/*
 * ULDPoolOwnerDetails.java Created on Aug 11, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
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



import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerDetailsVO;
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
@Table(name = "ULDPOLOWNDTL")
@Entity
public class ULDPoolOwnerDetails {
	
	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
	private ULDPoolOwnerDetailsPK detailsPK ;
	
	private int polAirlineIdentifier;
    private String polFlightNumber;
    private Calendar fromDate;
    private Calendar toDate;
	
	/**
	 * @return Calendar Returns the fromDate.
	 */
    @Column(name="FRMDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return this.fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return int Returns the polAirlineIdentifier.
	 */
	@Column(name="POLARLIDR")
	public int getPolAirlineIdentifier() {
		return this.polAirlineIdentifier;
	}
	/**
	 * @param polAirlineIdentifier The polAirlineIdentifier to set.
	 */
	public void setPolAirlineIdentifier(int polAirlineIdentifier) {
		this.polAirlineIdentifier = polAirlineIdentifier;
	}
	/**
	 * @return String Returns the polFlightNumber.
	 */
	@Column(name="POLFLTNUM")
	public String getPolFlightNumber() {
		return this.polFlightNumber;
	}
	/**
	 * @param polFlightNumber The polFlightNumber to set.
	 */
	public void setPolFlightNumber(String polFlightNumber) {
		this.polFlightNumber = polFlightNumber;
	}
	/**
	 * @return Calendar Returns the toDate.
	 */
	@Column(name="TOODAT")

	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return this.toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return ULDFlightMessageReconcileDetailsPK Returns the detailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="flightCarrierIdentifier", column=@Column(name="FLTCARIDR")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
	    @AttributeOverride(name="polSequenceNumber", column=@Column(name="POLSEQNUM"))})  
	public ULDPoolOwnerDetailsPK getDetailsPK() {
		return this.detailsPK;
	}
	/**
	 * @param detailsPK The detailsPK to set.
	 */
	public void setDetailsPK(ULDPoolOwnerDetailsPK detailsPK) {
		this.detailsPK = detailsPK;
	}
	
	
	/**
	 * 
	 *
	 */
	public ULDPoolOwnerDetails() {
		
	}
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	public ULDPoolOwnerDetails(ULDPoolOwnerDetailsVO
			 detailsVO )throws SystemException{
		log.entering("ULDPoolOwnerDetails","ULDPoolOwnerDetails");
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
	public void populatePK(ULDPoolOwnerDetailsVO detailsVO) {
		log.entering("ULDPoolOwnerDetails","populatePk");
		ULDPoolOwnerDetailsPK poolOwnerDetailsPK = new 
		ULDPoolOwnerDetailsPK();
		poolOwnerDetailsPK.setCompanyCode(detailsVO.getCompanyCode());
		poolOwnerDetailsPK.setFlightCarrierIdentifier(detailsVO.getFlightCarrierIdentifier());
		poolOwnerDetailsPK.setSequenceNumber(detailsVO.getSequenceNumber());
		setDetailsPK(poolOwnerDetailsPK);
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populateAttributes(ULDPoolOwnerDetailsVO detailsVO) {
		log.entering("ULDPoolOwnerDetails","populateAttributes");
		
		this.setPolAirlineIdentifier(detailsVO.getPolAirlineIdentifier());
		this.setPolFlightNumber(detailsVO.getPolFligthNumber());
		if(detailsVO.getFromDate() != null){
			this.setFromDate(detailsVO.getFromDate().toCalendar());	
		}
		if(detailsVO.getToDate() != null){
			this.setToDate(detailsVO.getToDate().toCalendar());	
		}
	
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void update(ULDPoolOwnerDetailsVO detailsVO) {
		log.entering("ULDPoolOwnerDetails","update");
		populateAttributes(detailsVO);
	}
	/**
	 * 
	 * @param detailsVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDPoolOwnerDetails find(
			ULDPoolOwnerDetailsVO detailsVO)
	        throws SystemException {
		
		ULDPoolOwnerDetailsPK poolOwnerDetailsPK = new 
		 ULDPoolOwnerDetailsPK();
		
		poolOwnerDetailsPK.setCompanyCode(detailsVO.getCompanyCode());
		poolOwnerDetailsPK.setFlightCarrierIdentifier(detailsVO.getFlightCarrierIdentifier());
		poolOwnerDetailsPK.setSequenceNumber(detailsVO.getSequenceNumber());
		poolOwnerDetailsPK.setPolSequenceNumber(detailsVO.getPolSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDPoolOwnerDetails.class,
					poolOwnerDetailsPK);
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
	
	
    
}
