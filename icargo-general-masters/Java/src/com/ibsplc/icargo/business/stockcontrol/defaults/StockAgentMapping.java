/*
 * StockAgentMapping.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.util.Calendar;
import java.util.Collection;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
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
 * Represents the mapping entity for the StockHolder and the stock Holder Agent
 * 
 * @author A-1954
 * 
 */
@Table(name = "STKHLDAGT")
@Entity
public class StockAgentMapping {

	private Log log = LogFactory.getLogger("STOCKHOLDER");

	private StockAgentMappingPK stockAgentMappingPK;
	
	private String stockHolderCode;

	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	/**
	 * @return Returns the stockAgentMappingPK.
	 */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="agentCode", column=@Column(name="AGTCOD"))}
	)
	public StockAgentMappingPK getStockAgentMappingPK() {
		return stockAgentMappingPK;
	}

	/**
	 * @param stockAgentMappingPK The stockAgentMappingPK to set.
	 */
	public void setStockAgentMappingPK(StockAgentMappingPK stockAgentMappingPK){
		this.stockAgentMappingPK = stockAgentMappingPK;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
    @Column(name="LSTUPDTIM")
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
    @Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
    @Column(name="STKHLDCOD")
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 *            The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * 
	 */
	public StockAgentMapping(){
		
	}
	
	/**
	 * @param stockAgentVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public StockAgentMapping(StockAgentVO stockAgentVO) 
			throws SystemException, CreateException{
		StockAgentMappingPK pk = new StockAgentMappingPK();
		pk.setCompanyCode(stockAgentVO.getCompanyCode());
		pk.setAgentCode(stockAgentVO.getAgentCode());
		this.setStockAgentMappingPK(pk);
		this.setLastUpdateUser(stockAgentVO.getLastUpdateUser());
		this.setStockHolderCode(stockAgentVO.getStockHolderCode());
		PersistenceController.getEntityManager().persist(this);
	}
	
	/**
	 * @param companyCode
	 * @param agentCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static StockAgentMapping find(String companyCode, String agentCode) 
			throws SystemException, FinderException{
		StockAgentMappingPK pk = new StockAgentMappingPK();
		pk.setCompanyCode(companyCode);
		pk.setAgentCode(agentCode);
		return PersistenceController.getEntityManager().find(
				StockAgentMapping.class, pk);
	}
	
	
	/**
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException{
		PersistenceController.getEntityManager().remove(this);
	}
	
	public void update(StockAgentVO agentVO) throws SystemException {
		this.setLastUpdateTime(agentVO.getLastUpdateTime());
		this.setLastUpdateUser(agentVO.getLastUpdateUser());
		this.setStockHolderCode(agentVO.getStockHolderCode());
	}
	
	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<StockAgentVO> findStockAgentMappings(
			StockAgentFilterVO stockAgentFilterVO)
		throws SystemException{
		try {
			return constructDAO().findStockAgentMappings(stockAgentFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
    private static StockControlDefaultsDAO constructDAO()
    		throws SystemException, PersistenceException {
		EntityManager em = PersistenceController.getEntityManager();
		return StockControlDefaultsDAO.class.cast(
				em.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));
	}

    
    /**
     * @param companyCode
     * @param stockHolderCode
     * @return
     * @throws SystemException
     */
    public static Collection<String> findAgentsForStockHolder(
			String companyCode, String stockHolderCode) throws SystemException{
    	try {
			return constructDAO().findAgentsForStockHolder(
											companyCode, stockHolderCode);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
    }
}
