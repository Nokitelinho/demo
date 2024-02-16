/*
 * ULDInventoryDetails.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.stock;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDInventoryDetailsVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2883
 *
 */

@Staleable
@Table(name="ULDINVPLNDTL")
@Entity
public class ULDInventoryDetails {
	
	
	private Calendar date;
	private String remarks;
	private int requiredQuantity;
	private ULDInventoryDetailsPK inventoryDetailsPK;
	private Log log = LogFactory.getLogger("ULDInventory");
	
	/**
	 * 
	 *Constructor
	 */
	public ULDInventoryDetails(){
	}
	
	/**
	 * 
	 * @param inventoryDetailsVO
	 * @return ULDInventoryDetails
	 * @throws SystemException
	 */
	public ULDInventoryDetails(ULDInventoryDetailsVO inventoryDetailsVO)
	throws SystemException{
		log.entering("ULDInventoryDetails", "ULDInventoryDetails");
		populatePk(inventoryDetailsVO);
		populateAttributes(inventoryDetailsVO);
		try {
			EntityManager em = PersistenceController.getEntityManager();
            em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 * @throws SystemException
	 */
	public void populatePk(ULDInventoryDetailsVO inventoryDetailsVO) 
	throws SystemException {
		log.entering("ULDInventory", "populatePk");
		ULDInventoryDetailsPK pk = new ULDInventoryDetailsPK();
		pk.setCompanyCode(inventoryDetailsVO.getCompanyCode());
		pk.setAirportCode(inventoryDetailsVO.getAirportCode());
		pk.setUldType(inventoryDetailsVO.getUldType());
		pk.setSequenceNumber(inventoryDetailsVO.getSequenceNumber());
		log.exiting("ULDInventory", "populatePk");
		this.setInventoryDetailsPK(pk);
	}
	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 */
	public void populateAttributes(ULDInventoryDetailsVO inventoryULDVO){
		log.entering("ULDInventory", "populateAttributes");
		this.setRequiredQuantity(inventoryULDVO.getRequiredULD());
		this.setRemarks(inventoryULDVO.getRemarks());
		this.setDate(inventoryULDVO.getInventoryDate());
		log.exiting("ULDInventory", "populateAttributes");
	}
	
	/**
	 * @return the inventoryPK
	 */
	@EmbeddedId
	@AttributeOverrides({
 		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
 		@AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
 		@AttributeOverride(name="uldType", column=@Column(name="ULDTYP")),
 		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM")),
 		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))}
    )
	/**
	 * @return the inventoryDetailsPK
	 */
	public ULDInventoryDetailsPK getInventoryDetailsPK() {
		return inventoryDetailsPK;
	}

	/**
	 * @param inventoryDetailsPK the inventoryDetailsPK to set
	 */
	public void setInventoryDetailsPK(ULDInventoryDetailsPK inventoryDetailsPK) {
		this.inventoryDetailsPK = inventoryDetailsPK;
	}
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
	 * @return the date
	 */
	@Column(name="DAT")
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/**
	 * @return the requiredQuantity
	 */
	@Column(name="REQQTY")
	public int getRequiredQuantity() {
		return requiredQuantity;
	}
	/**
	 * @param requiredQuantity the requiredQuantity to set
	 */
	public void setRequiredQuantity(int requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}
	/**
	 * @author a-2883
	 * @return ULDDefaultsDAO
	 * @throws SystemException
	 */
    private static ULDDefaultsDAO constructDAO() throws SystemException {
    try {
        EntityManager em = PersistenceController.getEntityManager();
            return ULDDefaultsDAO.class.cast(em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
        } catch (PersistenceException ex) {
            throw new SystemException(ex.getMessage());
        }
    }
	/**
	 * 
	 * @param inventoryULDVO
	 * @return Collection<InventoryULDVO>
	 * @throws SystemException
	 */
	 public static Collection<InventoryULDVO> findULDInventoryDetails(InventoryULDVO inventoryULDVO)
	 throws SystemException{
	    	try{
	    		return constructDAO().findULDInventoryDetails(inventoryULDVO);
	    	}catch(PersistenceException persistenceException){
	    		throw new SystemException(persistenceException.getErrorCode());
	    	}
	    }

	/**
	 * @author a-2883
	 * @param vo
	 * @throws SystemException
	 */
	public void update(ULDInventoryDetailsVO vo)
 	throws SystemException {
		log.entering("ULDInventoryDetails","update");
		this.setRequiredQuantity(vo.getRequiredULD());
		this.setRemarks(vo.getRemarks());
		log.exiting("ULDInventoryDetails", "update");
	  }
	  
	  /**
	 * @author a-2883
	 * @param customerCallHistoryVO
	 * @return ULDInventoryDetails
	 * @throws SystemException
	 */
	public static ULDInventoryDetails find(ULDInventoryDetailsVO inventoryULDVO)
	throws SystemException{
		Log log = LogFactory.getLogger("ULDInventoryDeatils");
		log.entering("ULDInventoryDeatils", "find");
		ULDInventoryDetails uldInventoryDetails = null;
		ULDInventoryDetailsPK pk = new ULDInventoryDetailsPK();
		pk.setCompanyCode(inventoryULDVO.getCompanyCode());
		pk.setAirportCode(inventoryULDVO.getAirportCode());
		pk.setUldType(inventoryULDVO.getUldType());
		pk.setSequenceNumber(inventoryULDVO.getSequenceNumber());
		pk.setSerialNumber(inventoryULDVO.getSerialNumber());
		log.exiting("ULDInventory", "find"+pk);
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			uldInventoryDetails = entityManager.find(ULDInventoryDetails.class, pk);
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return uldInventoryDetails;
	}
	
}
