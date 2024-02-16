/*
 * ULDPoolSegmentExceptions.java Created on Aug 11, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.misc;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
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
 * @author A-3429
 *
 */
@Staleable
@Table(name="ULDPOLSEGEXP")
@Entity
public class ULDPoolSegmentExceptions {

	private Log log=LogFactory.getLogger("ULD MANAGEMENT"); 
	
	private ULDPoolSegmentExceptionsPK exceptionsPK ;
	private String origin;
	private String destination;
	private String airport;
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
	/**
	 * @return the destination
	 */
	@Column(name="DSTCOD")
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * @return the origin
	 */
	@Column(name="ORGCOD")
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
		
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="airlineIdentifierOne", column=@Column(name="ARLONE")),
	    @AttributeOverride(name="airlineIdentifierTwo", column=@Column(name="ARLTWO")),
	    @AttributeOverride(name="serialNumber", column=@Column(name="SERNUM")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))})
	    
	/**
	 * @return the exceptionsPK
	 */
	public ULDPoolSegmentExceptionsPK getExceptionsPK() {
		return exceptionsPK;
	}
	/**
	 * @param exceptionsPK the exceptionsPK to set
	 */
	public void setExceptionsPK(ULDPoolSegmentExceptionsPK exceptionsPK) {
		this.exceptionsPK = exceptionsPK;
	}
	/**
	 * 
	 *
	 */
	public ULDPoolSegmentExceptions() {
		
	}
	/**
	 * 
	 * @param detailsVO
	 * @throws SystemException
	 */
	public ULDPoolSegmentExceptions(ULDPoolSegmentExceptionsVO
			 exceptionsVO )throws SystemException{
		log.entering("ULDPoolSegmentException","ULDSegmentException");
		populatePK(exceptionsVO);
		populateAttributes(exceptionsVO);
		log.log(Log.INFO,"After populate Attributes");
		try{
			  PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());	
		}
		log.log(Log.INFO, "%%%%%%%%PKPKPK%%%%%%%%%%%%%%%", this.getExceptionsPK().getSequenceNumber());
	}
	
	/**
	 * 
	 * @param detailsVO
	 */
	public void populatePK(ULDPoolSegmentExceptionsVO
			 exceptionsVO ) throws SystemException{
		log.entering("ULDPoolSegmentException","populatePk");
		
		log.log(Log.INFO, "%%%%%%%%%%%%exceptionsVO%%%%% ", exceptionsVO);
		ULDPoolSegmentExceptionsPK poolSegmentExceptionsPK = new ULDPoolSegmentExceptionsPK();
		poolSegmentExceptionsPK.setCompanyCode(exceptionsVO .getCompanyCode());
		//Added By Deepthi on for 	
		poolSegmentExceptionsPK.setAirlineIdentifierOne(exceptionsVO .getAirlineIdentifierOne());
		poolSegmentExceptionsPK.setAirlineIdentifierTwo(exceptionsVO .getAirlineIdentifierTwo());
		poolSegmentExceptionsPK.setSerialNumber(exceptionsVO.getSerialNumber()); 
		//Deepthi ends	
		//poolSegmentExceptionsPK.setAirport(exceptionsVO .getAirport());
		
		
		
		
		log.log(Log.INFO, "inside populatePk", poolSegmentExceptionsPK);
		this.setExceptionsPK(poolSegmentExceptionsPK);
	}
	/**
	 * 
	 * @param detailsVO
	 */
	public void populateAttributes(ULDPoolSegmentExceptionsVO
			 exceptionsVO ) {
		log.entering("ULDPoolSegmentException","populateAttributes");
		
		this.setOrigin(exceptionsVO.getOrigin());
		this.setDestination(exceptionsVO.getDestination());
		this.setAirport(exceptionsVO.getAirport());
		log.exiting("ULDPoolSegmentException","populateAttributes");
	}
	/**
	 * 
	 * @param exceptionsVO
	 */
	public void update(ULDPoolSegmentExceptionsVO exceptionsVO) {
		log.entering("ULDPoolSegmentException","update");
		populateAttributes(exceptionsVO);
	}
	
	/**
	 * 
	 * @param exceptionsVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDPoolSegmentExceptions find(
			ULDPoolSegmentExceptionsVO exceptionsVO)
	        throws SystemException {
		
		ULDPoolSegmentExceptionsPK poolSegmentExceptionsPK = new 
		ULDPoolSegmentExceptionsPK();
		
		poolSegmentExceptionsPK.setCompanyCode(exceptionsVO.getCompanyCode());
		poolSegmentExceptionsPK.setAirlineIdentifierOne(exceptionsVO.getAirlineIdentifierOne());
		poolSegmentExceptionsPK.setAirlineIdentifierTwo(exceptionsVO.getAirlineIdentifierTwo());
		//poolSegmentExceptionsPK.setAirport(exceptionsVO.getAirport());
		poolSegmentExceptionsPK.setSequenceNumber(exceptionsVO.getSequenceNumber());
		poolSegmentExceptionsPK.setSerialNumber(exceptionsVO.getSerialNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			return entityManager.find(ULDPoolSegmentExceptions.class,
					poolSegmentExceptionsPK);
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
