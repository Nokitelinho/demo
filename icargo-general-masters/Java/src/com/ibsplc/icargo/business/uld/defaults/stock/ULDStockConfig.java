/*
 * ULDStockConfig.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock;


import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDSTKCFG")
@Entity
public class ULDStockConfig {
    
	private static final String MODULE_NAME = "uld.defaults";
	
	private Log log=LogFactory.getLogger("ULD MANAGEMENT");
	
	private ULDStockConfigPK uldStockConfigPK ;
	
    private int minQty;
    private int maxQty;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;  
	// Added by Preet on 1 st Apr for Air NZ 448--starts
	private String uldGroupCode;
	private int dwellTime;
	// Added by Preet on 1 st Apr for Air NZ 448--ends
	
	private String remark;
	
	/**
	 * @return the remark
	 */
	@Column(name="STKRMK")    
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    /**
	 * @return the dwellTime
	 */
	@Column(name="DWLTIM")    
	public int getDwellTime() {
		return dwellTime;
	}
	/**
	 * @param dwellTime the dwellTime to set
	 */
	public void setDwellTime(int dwellTime) {
		this.dwellTime = dwellTime;
	}
	/**
	 * @return the uldGroupCode
	 */
	@Column(name="ULDGRPCOD")    
	public String getUldGroupCode() {
		return uldGroupCode;
	}
	/**
	 * @param uldGroupCode the uldGroupCode to set
	 */
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
	}
	/**
     * @return Returns the lastUpdatedTime.
     */
	@Version
	@Column(name="LSTUPDTIM")    

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
	@Column(name="LSTUPDUSR")    
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
	 * @return Returns the maxQty.
	 */
	@Column(name="MAXQTY")  
	@Audit(name="maxQty")
 	public int getMaxQty() {
		return maxQty;
	}
	/**
	 * @param maxQty The maxQty to set.
	 */
	public void setMaxQty(int maxQty) {
     	this.maxQty = maxQty;
	}
	/**
	 * @return Returns the minQty.
	 */
	@Column(name="MINQTY")   
	@Audit(name="minQty")
	public int getMinQty() {
		return minQty;
  	}
  	/**
  	 * @param minQty The minQty to set.
  	 */
	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}
	
	/**
	 * @return Returns the uldStockConfigPK.
	 */
	 @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
		@AttributeOverride(name="stationCode", column=@Column(name="ARPCOD")),
		@AttributeOverride(name="uldTypeCode", column=@Column(name="ULDTYPCOD")),
		@AttributeOverride(name="uldNature", column=@Column(name="ULDNAT"))})
	    public ULDStockConfigPK getUldStockConfigPK() {
		return uldStockConfigPK;
	}
	/**
	 * @param uldStockConfigPK The uldStockConfigPK to set.
	 */
	public void setUldStockConfigPK(ULDStockConfigPK uldStockConfigPK) {
		this.uldStockConfigPK = uldStockConfigPK;
	}
	
	
	/**
	 * Constructor
	 */
	public ULDStockConfig() {
	}  
	 
	/**
	 * 
	 * @param uldStockConfigVO
	 * @throws SystemException
	 */
	public ULDStockConfig(ULDStockConfigVO uldStockConfigVO) 
		throws SystemException {
		log.entering("ULDStockConfig","constructor");
		populatePk(uldStockConfigVO);
		populateAttributes(uldStockConfigVO);
		EntityManager em = PersistenceController.getEntityManager();
		log.log(Log.INFO,"!!!!GOing to insert");
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
	
	/**
	 * 
	 * @param uldStockConfigVO
	 */
	public void populatePk(ULDStockConfigVO uldStockConfigVO) {
		log.entering("ULDStockConfig","populatePk");
		ULDStockConfigPK stockConfigPK = new ULDStockConfigPK();
		stockConfigPK.setCompanyCode(uldStockConfigVO.getCompanyCode()	);
		stockConfigPK.setAirlineIdentifier(uldStockConfigVO.getAirlineIdentifier());
		stockConfigPK.setStationCode(uldStockConfigVO.getStationCode());
		stockConfigPK.setUldTypeCode(uldStockConfigVO.getUldTypeCode());
		stockConfigPK.setUldNature(uldStockConfigVO.getUldNature());
		this.setUldStockConfigPK(stockConfigPK);
		log.exiting("ULDStockConfig","populatePk");
	}
	
	
	/**
	 * 
	 * @param uldStockConfigVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDStockConfigVO uldStockConfigVO)
		throws SystemException {
		log.entering("ULDStockConfig","populateAttributes");
		this.setMaxQty(uldStockConfigVO.getMaxQty());
		this.setMinQty(uldStockConfigVO.getMinQty());
		this.setLastUpdatedUser(uldStockConfigVO.getLastUpdatedUser());
		// Added by Preet on 1 Apr for Air NZ 448 starts
		this.setUldGroupCode(uldStockConfigVO.getUldGroupCode());
		this.setDwellTime(uldStockConfigVO.getDwellTime());
		// Added by Preet on 1 Apr for Air NZ 448 ends
		this.setRemark(uldStockConfigVO.getRemarks());
		log.exiting("ULDStockConfig","populateAttributes");
	}
	
	  /**
	   * 
	   * @param companyCode
	   * @param airlineIdentifier
	   * @param stationCode
	   * @param uldTypeCode
	   * @return
	   * @throws SystemException
	   */
		public static ULDStockConfig find( String companyCode, int airlineIdentifier,
		        							String stationCode,String uldTypeCode , String uldNature)
			throws SystemException {
			
			Log log = LogFactory.getLogger("uld");
			log.entering("ULDStockConfig","find :companyCode"+companyCode);
			log.entering("ULDStockConfig","find :airlineIdentifier"+airlineIdentifier);
			log.entering("ULDStockConfig","find :stationCode"+stationCode);
			log.entering("ULDStockConfig","find :uldTypeCode"+uldTypeCode);
			log.entering("ULDStockConfig","find :uldNature"+uldNature);
			
			ULDStockConfig uldStockConfig = null;
			ULDStockConfigPK stockConfigPK = new ULDStockConfigPK();
			stockConfigPK.setCompanyCode(companyCode);
			stockConfigPK.setAirlineIdentifier(airlineIdentifier);
			stockConfigPK.setStationCode(stationCode);
			stockConfigPK.setUldTypeCode(uldTypeCode);
			stockConfigPK.setUldNature(uldNature); 
			EntityManager em = PersistenceController.getEntityManager();
			try{
				uldStockConfig = em.find(ULDStockConfig.class,stockConfigPK);
			}catch(FinderException finderException){
				throw new SystemException(finderException.getErrorCode());
			}
			log.entering("ULDStockConfig","find :uldStockConfig"+uldStockConfig);
		    return uldStockConfig;
		}
		
		/**
		 * 
		 * @param ulStockConfigVO
		 * @throws SystemException
		 */
		public void update(ULDStockConfigVO ulStockConfigVO)
			throws SystemException {
			log.entering("ULDStockConfig","update");
			setMaxQty(ulStockConfigVO.getMaxQty());
			setMinQty(ulStockConfigVO.getMinQty());
			setLastUpdatedUser(ulStockConfigVO.getLastUpdatedUser());
			setLastUpdatedTime(ulStockConfigVO.getLastUpdatedTime());
            // Added by Preet on 1 Apr for Air NZ 448 starts
			setDwellTime(ulStockConfigVO.getDwellTime());
			setUldGroupCode(ulStockConfigVO.getUldGroupCode());
            // Added by Preet on 1 Apr for Air NZ 448 ends
			setRemark(ulStockConfigVO.getRemarks());
			log.exiting("ULDStockConfig","update");
		}
		/**
		 * This method is used to remove the business object.
		 * It interally calls the remove method within EntityManager
		 *
		 * @throws SystemException
		 */
		public void remove() throws SystemException {
			log.entering("ULDStockConfig","remove");
			log.log(Log.INFO,"!!!!GOING TO DELETE THIS");
			EntityManager em = PersistenceController.getEntityManager();
			try{
				em.remove(this);
			}catch(RemoveException removeException){
				throw new SystemException(removeException.getErrorCode());
			}		    
		}

		
		
  
		/**
		 * Used to populate the business object with values from VO
		 *
		 * @return ULDStockConfigVO
		 */
		public ULDStockConfigVO retrieveVO() {
			return null;
		}
		
	    
	    /**
	     * 
	     * @param uldStockConfigFilterVO
	     * @return
	     * @throws SystemException
	     */
	    
	    public Collection<ULDStockConfigVO> 
	    	listULDStockConfig(ULDStockConfigFilterVO  uldStockConfigFilterVO)
	    throws SystemException{
	    	log.entering("ULDStockConfig","listULDStockConfig");
	    	EntityManager em = PersistenceController.getEntityManager();
	    	try{
	    		ULDDefaultsDAO uldDefaultsDAO =
	    				ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
	    		return uldDefaultsDAO.listULDStockConfig(uldStockConfigFilterVO);
	    	}catch(PersistenceException persistenceException){
	    		throw new SystemException(persistenceException.getErrorCode());
	    	}     
	    }
}
		
