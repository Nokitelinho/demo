/*
 * ULDInventory.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.stock;

import java.util.Collection;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
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
@Table(name="ULDINVPLN")
@Entity
public class ULDInventory {
	
	/**
	 * 
	 */
	private ULDInventoryPK inventoryPK;
	
	/**
	 * 
	 */
	private Set<ULDInventoryDetails> uldInventoryDetails; 
	private Log log = LogFactory.getLogger("ULDInventory");
	
	/**
	 * Constructor
	 *
	 */
	public ULDInventory(){
	}

	/**
	 * @author a-2883
	 * @param inventoryULDVO
	 * @throws SystemException
	 */
	public ULDInventory(InventoryULDVO inventoryULDVO) throws SystemException{
		try {
			EntityManager em = PersistenceController.getEntityManager();
			populatePk(inventoryULDVO);
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		if(inventoryULDVO.getUldInventoryDetailsVOs() != null && 
				inventoryULDVO.getUldInventoryDetailsVOs().size() > 0){
			inventoryULDVO.setSequenceNumber(this.getInventoryPK().getSequenceNumber());
			populateChildren(inventoryULDVO);
		}
	}
	
	/** 
	 * @author a-2883
	 * @param inventoryULDVO
	 * @throws SystemException
	 */
	public void populatePk(InventoryULDVO inventoryULDVO) throws SystemException {
		log.entering("ULDInventory", "populatePk");
		ULDInventoryPK pk = new ULDInventoryPK();
		pk.setCompanyCode(inventoryULDVO.getCompanyCode());
		pk.setAirportCode(inventoryULDVO.getAirportCode());
		pk.setUldType(inventoryULDVO.getUldType());
		log.exiting("ULDInventory", "populatePk");
		this.setInventoryPK(pk);
	}
	/**
	 * @author a-2883
	 * @param customerCallHistoryVO
	 * @throws SystemException
	 */

	public void populateChildren(InventoryULDVO inventoryULDVO) throws SystemException {
		log.entering("ULDInventory", "populatePk");
		for(ULDInventoryDetailsVO uldInventoryDetailsVO : inventoryULDVO.getUldInventoryDetailsVOs()){
			uldInventoryDetailsVO.setSequenceNumber(inventoryULDVO.getSequenceNumber());
			new ULDInventoryDetails(uldInventoryDetailsVO);
		}
	}
	/**
	 * @return the uldInventoryDetails
	 */
	@OneToMany
	@JoinColumns( {
	@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	@JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable=false, updatable=false),
	@JoinColumn(name = "ULDTYP", referencedColumnName = "ULDTYP", insertable=false, updatable=false),
	@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable=false, updatable=false)})
	public Set<ULDInventoryDetails> getUldInventoryDetails() {
		return uldInventoryDetails;
	}
	
	/**
	 * @param uldInventoryDetails the uldInventoryDetails to set
	 */
	public void setUldInventoryDetails(Set<ULDInventoryDetails> uldInventoryDetails) {
		this.uldInventoryDetails = uldInventoryDetails;
	}


	/**
	 * @return the inventoryPK
	 */
	@EmbeddedId
	@AttributeOverrides({
 		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
 		@AttributeOverride(name="airportCode", column=@Column(name="ARPCOD")),
 		@AttributeOverride(name="uldType", column=@Column(name="ULDTYP")),
 		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
    )
	public ULDInventoryPK getInventoryPK() {
		return inventoryPK;
	}
	/**
	 * @param inventoryPK the inventoryPK to set
	 */
	public void setInventoryPK(ULDInventoryPK inventoryPK) {
		this.inventoryPK = inventoryPK;
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
            ex.getErrorCode();
            throw new SystemException(ex.getMessage());
        }
    }
    
	/**
	 * 
	 * @param inventoryULDVO
	 * @return InventoryULDVO
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
	 * @param customerCallHistoryVO
	 * @return ULDInventory
	 * @throws SystemException
	 */
	
	public static ULDInventory find(InventoryULDVO inventoryULDVO)
	throws SystemException {
		Log log = LogFactory.getLogger("ULDInventory");
		log.entering("ULDInventory", "find");
		ULDInventoryPK pk = new ULDInventoryPK();
		ULDInventory uldInventory = null;
		pk.setCompanyCode(inventoryULDVO.getCompanyCode());
		pk.setAirportCode(inventoryULDVO.getAirportCode());
		pk.setUldType(inventoryULDVO.getUldType());
		pk.setSequenceNumber(inventoryULDVO.getSequenceNumber());
		EntityManager entityManager = PersistenceController.getEntityManager();
		try {
			uldInventory = entityManager.find(ULDInventory.class, pk);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode());
		}
		log.exiting("ULDInventory", "find");
		return uldInventory;
	}

}
