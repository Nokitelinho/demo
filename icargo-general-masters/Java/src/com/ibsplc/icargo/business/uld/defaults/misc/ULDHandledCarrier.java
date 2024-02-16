/*
 * ULDHandledCarrier.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2883
 *
 */

@Table(name="ULDHDLCAR")
@Entity
@Staleable
public class ULDHandledCarrier { 

	private ULDHandledCarrierPK uldHandledCarrierPK; 
	private String airlineName;
	private Log log = LogFactory.getLogger("ULD");

	/**
     * Default constructor
     *
     */
	public ULDHandledCarrier() {
	}
	
	/**
	 * 
	 * @param uldHandledCarrierVO
	 * @throws SystemException
	 */
	public ULDHandledCarrier(ULDHandledCarrierVO uldHandledCarrierVO) 
		throws SystemException{
			try {
				EntityManager em = PersistenceController.getEntityManager();
				populatePk(uldHandledCarrierVO);
				populateAttributes(uldHandledCarrierVO);
				em.persist(this);
			}catch (CreateException ex) {
				throw new SystemException(ex.getErrorCode(), ex);
			}
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	 private static ULDDefaultsDAO constructDAO() throws SystemException{
		try{
    		EntityManager em = PersistenceController.getEntityManager();
    		return ULDDefaultsDAO.class.cast(em.getQueryDAO(
    				ULDDefaultsPersistenceConstants.MODULE_NAME));
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
	  }
	    
	 /**
     * This method retrieves the uld details of the specified filter condition
     * 
     * @param handledCarrierSetupVO
     * @return Collection<ULDHandledCarrierVO> 
     * @throws SystemException
     */
     public static Collection<ULDHandledCarrierVO> findhandledcarriersetup(
			ULDHandledCarrierVO handledCarrierSetupVO)throws SystemException {   
    	Collection<ULDHandledCarrierVO> carrierList = null;
        try {
			carrierList = constructDAO().findhandledcarriersetup(
					handledCarrierSetupVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
        return carrierList ;
    } 
    
	/**
	 * 
	 * @param uldHandledCarrierVO
	 * @throws SystemException
	 */ 
	public void populatePk(ULDHandledCarrierVO uldHandledCarrierVO) 
    	throws SystemException {
			ULDHandledCarrierPK pk = new ULDHandledCarrierPK();
			pk.setCompanyCode(uldHandledCarrierVO.getCompanyCode());
			pk.setAirlineCode(uldHandledCarrierVO.getAirlineCode());
			pk.setStationCode(uldHandledCarrierVO.getStationCode());
			this.setUldHandledCarrierPK(pk);
	}
  
    /**
     * 
     * @param uldHandledCarrierVO
     * @throws SystemException
     */
    public void populateAttributes(ULDHandledCarrierVO uldHandledCarrierVO)
    	throws SystemException{
    	this.setAirlineName(uldHandledCarrierVO.getAirlineName());
    }
   
	
	/**
	 * 
	 * @return airlineName
	 */
	@Column(name = "ARLNAM")
	public String getAirlineName() {
		return airlineName;
	}
	
	/**
	 * 
	 * @param airlineName
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
		
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "stationCode", column = @Column(name = "STNCOD")),
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineCode", column = @Column(name = "ARLCOD")) })
			
	/**
	 * 
	 * @return uldHandledCarrierPK
	 */
	public ULDHandledCarrierPK getUldHandledCarrierPK() {
		return uldHandledCarrierPK;
	}
	
	/**
	 * 
	 * @param uldHandledCarrierPK
	 */
	public void setUldHandledCarrierPK(ULDHandledCarrierPK uldHandledCarrierPK) {
		this.uldHandledCarrierPK = uldHandledCarrierPK;
	}
    
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
    	log.entering("ULDHandledCarrier","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @param airlineCode
	 * @param stationCode
	 * @return ULDHandledCarrier
	 * @throws SystemException
	 */
	public static final ULDHandledCarrier find(String companyCode,String airlineCode,
			String stationCode)throws SystemException{
		
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDHandledCarrier","find");
		ULDHandledCarrier carrier = null;
		ULDHandledCarrierPK pk = new ULDHandledCarrierPK();
		pk.setCompanyCode(companyCode);
		pk.setAirlineCode(airlineCode);
		pk.setStationCode(stationCode);
		EntityManager em = PersistenceController.getEntityManager();
		try{
			return carrier= em.find(ULDHandledCarrier.class , pk);
		}catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode());
		}
	}
	
    
}
