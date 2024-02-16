/*
 * AccessoriesStockConfig.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.stock;

import java.util.Calendar;
import java.util.Collection;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
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

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;
/**
 * @author A-1347
 *
 */

@Table(name="ULDACCSTKCFG")
@Entity
public class AccessoriesStockConfig { 

	private AccessoriesStockConfigPK accessoriesStockConfigPK;
	
    private String accessoryDescription;

    private int available; 

    private int loaned;
    
    //added by a-3045 for CR AirNZ450 starts
	private int minimumQuantity;
    //added by a-3045 for CR AirNZ450 starts

	private String remarks;
	
    private Calendar lastUpdateTime;

    private String lastUpdateUser;

   private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

	/**
     * Default constructor
     *
     */
	public AccessoriesStockConfig() {

	}

	/**
     * constructor for the BO
     *
     * @param version
     * @param accessoriesStockConfigVO
     * @param companyCode
     * @param startYear
     * @param stationCode
     * @throws SystemException
     */
	public AccessoriesStockConfig(
		AccessoriesStockConfigVO accessoriesStockConfigVO)
		throws SystemException {
		log.entering("AccessoriesStockConfig","AccessoriesStockConfig");
		try{
			populatePk(accessoriesStockConfigVO.getCompanyCode(),
					accessoriesStockConfigVO.getAccessoryCode(),
					accessoriesStockConfigVO.getStationCode(),
					accessoriesStockConfigVO.getAirlineIdentifier());
			populateAttributes(accessoriesStockConfigVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
		log.log(Log.SEVERE,"CreateException caught, SystemException thrown");
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("AccessoriesStockConfig","AccessoriesStockConfig");
	}

	/**
	 * 
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @param accessoryCode
	 */	
	private void populatePk(
	        String companyCode, String accessoryCode,
	        String stationCode, int airlineIdentifier) {
		log.entering("AccessoriesStockConfig","populatePk");
		AccessoriesStockConfigPK accessoriesStockConfigPk = new
			AccessoriesStockConfigPK(
					companyCode,accessoryCode,
					stationCode,airlineIdentifier);
		setAccessoriesStockConfigPK(accessoriesStockConfigPk);
		log.exiting("AccessoriesStockConfig","populatePk");
	}

	/**
     * private method to populate attributes
     *
     * @param accessoriesStockConfigVO
     * @throws GenerationFailedException
     * @throws SystemException
     */
	private void populateAttributes(
		AccessoriesStockConfigVO accessoriesStockConfigVO)
		throws SystemException {
		log.entering("AccessoriesStockConfig","populateAttributes");
		setAccessoryDescription(accessoriesStockConfigVO.
									getAccessoryDescription());
		setAvailable(accessoriesStockConfigVO.getAvailable());
		setLoaned(accessoriesStockConfigVO.getLoaned());
		setMinimumQuantity(accessoriesStockConfigVO.getMinimumQuantity());
		setRemarks(accessoriesStockConfigVO.getRemarks());
		setLastUpdateUser(accessoriesStockConfigVO.getLastUpdateUser());
		setLastUpdateTime(accessoriesStockConfigVO.getLastUpdateTime());
		log.exiting("AccessoriesStockConfig","populateAttributes");
	}
	
	/**
	 * This methos finds the business object
	 * 
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @param accessoryCode
	 * @return AccessoriesStockConfig
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static AccessoriesStockConfig find(
	        String companyCode, String accessoryCode,
	        String stationCode, int airlineIdentifier)
			throws SystemException,FinderException {
		//log.entering("AccessoriesStockConfig","find");
		AccessoriesStockConfigPK accessoriesStockConfigPk = new
			AccessoriesStockConfigPK();
		accessoriesStockConfigPk.setCompanyCode(companyCode);
		accessoriesStockConfigPk.setAccessoryCode(accessoryCode);
		accessoriesStockConfigPk.setStationCode(stationCode);
		accessoriesStockConfigPk.setAirlineIdentifier(airlineIdentifier);
		EntityManager em = PersistenceController.getEntityManager();
		AccessoriesStockConfig accessoriesStockConfig = null;
		return em.find(AccessoriesStockConfig.class, accessoriesStockConfigPk);
	}
	

   /**
    * method to update the BO
    *
    * @param accessoriesStockConfigVO
    * @throws SystemException
    */
    public void update(AccessoriesStockConfigVO accessoriesStockConfigVO)
   		throws SystemException {
    	log.entering("AccessoriesStockConfig","update");
//    	this.accessoryDescription = accessoriesStockConfigVO.
    	  //getAccessoryDescription();
//    	this.available = accessoriesStockConfigVO.getAvailable();
//    	this.loaned = accessoriesStockConfigVO.getLoaned();
//    	this.remarks = accessoriesStockConfigVO.getRemarks();
//    	this.lastUpdateTime = accessoriesStockConfigVO.getLastUpdateTime();
//    	this.lastUpdateUser = accessoriesStockConfigVO.getLastUpdateUser();
    	populateAttributes(accessoriesStockConfigVO);
    	//setLastUpdateTime(accessoriesStockConfigVO.getLastUpdateTime());
    	
    	log.exiting("AccessoriesStockConfig","update");
    }

	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}
    
    
    /**
     * This method retrieves the stock details of the specified accessory code
     * 
     * @param companyCode
     * @param accessoryCode
     * @param stationCode
     * @param airlineIdentifier
     * @return AccessoriesStockConfigVO
     * @throws SystemException
     */
    public static AccessoriesStockConfigVO findAccessoriesStockDetails(
	        String companyCode, String accessoryCode,
	        String stationCode, int airlineIdentifier)  
    throws SystemException {
    	return constructDAO().findAccessoriesStockDetails(companyCode,
        		accessoryCode,stationCode,airlineIdentifier);
    }
    
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
     * @param accessoriesStockConfigFilterVO
     * @param displayPage
     * @return Page<AccessoriesStockConfigListVO> 
     * @throws SystemException
     */
    public static Page<AccessoriesStockConfigVO> findAccessoryStockList(
    		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
    		int displayPage)throws SystemException {   
        Page<AccessoriesStockConfigVO> accessoryStockList = null;
    	return constructDAO().findAccessoryStockList(accessoriesStockConfigFilterVO, displayPage) ;
    } 
    
    
    
    /**
     * @return Returns the accessoriesStockConfigPK.
     */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode",
				column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "airlineIdentifier", 
				column = @Column(name = "ARLIDR")),
		@AttributeOverride(name = "stationCode", 
				column = @Column(name = "ARPCOD")),
		@AttributeOverride(name = "accessoryCode", 
				column = @Column(name = "ACCCOD")) 
	})    
    public AccessoriesStockConfigPK getAccessoriesStockConfigPK() {
        return accessoriesStockConfigPK;
    }
    /**
     * @param accessoriesStockConfigPK The accessoriesStockConfigPK to set.
     */
    public void setAccessoriesStockConfigPK(
            AccessoriesStockConfigPK accessoriesStockConfigPK) {
        this.accessoriesStockConfigPK = accessoriesStockConfigPK;
    }
    /**
     * @return Returns the accessoryDescription.
     */
  @Column(name = "ACCDES")
	public String getAccessoryDescription() {
        return accessoryDescription;
    }
    /**
     * @param accessoryDescription The accessoryDescription to set.
     */
    public void setAccessoryDescription(String accessoryDescription) {
        this.accessoryDescription = accessoryDescription;
    }
    /**
     * @return Returns the available.
     */
  @Column(name = "AVLACC")
	public int getAvailable() {
        return available;
    }
    /**
     * @param available The available to set.
     */
    public void setAvailable(int available) {
        this.available = available;
    }
    /**
     * @return Returns the lastUpdateTime.
     */
    @Version
    @Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    /**
     * @return Returns the lastUpdateUser.
     */
    @Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    /**
     * @return Returns the loaned. 
     */
  @Column(name = "LNDACC")
	public int getLoaned() {
        return loaned;
    }
    /**
     * @param loaned The loaned to set.
     */
    public void setLoaned(int loaned) {
        this.loaned = loaned;
    }
    /**
     * @return Returns the remarks.
     */
  @Column(name = "RMK")
	public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    

    /**
     * This method is used to list Accessories at a purticular station 
     * 
     * @param  companyCode
     * @param  stationCode   
     * @param  airlineIdentifier
     * @return Collection<String>
     * @throws SystemException
     * 
     */
 
    public static Collection<String> findStationAccessories(String companyCode,
            				String stationCode,
            				int airlineIdentifier)
    throws SystemException{
        return null;
    }

	/**
	 * @return the minimumQuantity
	 */
    @Column(name = "MINQTY")
	public int getMinimumQuantity() {
		return minimumQuantity;
	}

	/**
	 * @param minimumQuantity the minimumQuantity to set
	 */
	public void setMinimumQuantity(int minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
    
    
    /**
     
    public void Operation1() {
    }*/
	/**
	 * 
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<AccessoriesStockConfigVO> sendAlertForULDAccStockDepletion(String companyCode)
	throws SystemException{
		try{
			return constructDAO().sendAlertForULDAccStockDepletion(companyCode);
		}catch(PersistenceException e){
			throw new SystemException(e.getErrorCode() , e);
		}
	}
}
