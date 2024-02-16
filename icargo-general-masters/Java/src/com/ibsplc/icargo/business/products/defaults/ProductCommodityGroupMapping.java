/*
 * ProductPriority.java Created on Jul 07, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.products.defaults.vo.ProductCommodityGroupMappingVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ProductCommodityGroupMapping Class is for handling Product CommodityGroup
 * @author A-5642
 *
 */
@Table(name="PRDCOMGRPMPG")
@Entity
@Staleable
public class ProductCommodityGroupMapping {

	/** The log. */
	private Log log = LogFactory.getLogger("ProductCommodityGroupMapping");
	
    private ProductCommodityGroupMappingPK productCommodityGroupMappingPK;
    /** The last update time. */
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name="LSTUPDUSR")
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
	 * @return Returns the lastUpdateTime.
	 */
	
	@Column(name = "LSTUPDTIM")
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
     * Default Constructor
     *
     */
	public ProductCommodityGroupMapping(){
	}

	/**
	 * Constructor for ProductCommodityGroup for creating Product CommodityGroup
	 * @param productCommodityGroupMappingVO
	 * @throws SystemException
	 */
	public ProductCommodityGroupMapping(
			ProductCommodityGroupMappingVO productCommodityGroupMappingVO)
	throws SystemException{
		try{
			populatePk(productCommodityGroupMappingVO);
			populateAttributes(productCommodityGroupMappingVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	 /**
     * @return Returns the productCommodityGroupMappingPK.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productName", column=@Column(name="PRDNAM")),
			 @AttributeOverride(name="commodityGroup", column=@Column(name="COMGRP"))
				})
    public ProductCommodityGroupMappingPK getProductCommodityGroupMappingPK() {
        return productCommodityGroupMappingPK;
    }
    /**
     * @param productCommodityGroupMappingPK The productCommodityGroupMappingPK to set.
     */
    public void setProductCommodityGroupMappingPK(
    		ProductCommodityGroupMappingPK productCommodityGroupMappingPK) {
        this.productCommodityGroupMappingPK = productCommodityGroupMappingPK;
    }
    /**
     * Method for removing object
     * @throws SystemException
     */
    public void remove()
			throws SystemException{
		try{	
	    	EntityManager entityManager = PersistenceController.getEntityManager();
				entityManager.remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
			
	}
	/**
	 * Method for populating the primary key
	 * @param productCommodityGroupMappingVO
	 */
    private void populatePk(
    		ProductCommodityGroupMappingVO productCommodityGroupMappingVO) {
    	ProductCommodityGroupMappingPK productCommodityGroupMappingPK =
    			new ProductCommodityGroupMappingPK();
    	productCommodityGroupMappingPK.setCompanyCode(
    			productCommodityGroupMappingVO.getCompanyCode());
    	productCommodityGroupMappingPK.setProductName(
    			productCommodityGroupMappingVO.getProductName());
    	productCommodityGroupMappingPK.setCommodityGroup(
    			productCommodityGroupMappingVO.getCommodityGroup());
		setProductCommodityGroupMappingPK(productCommodityGroupMappingPK);
	}
	/**
	 * @param productCommodityGroupMappingVO
	 */
	public void populateAttributes(
			ProductCommodityGroupMappingVO productCommodityGroupMappingVO) {
		setLastUpdateTime(
				productCommodityGroupMappingVO.getLastUpdateTime());
		setLastUpdateUser(
				productCommodityGroupMappingVO.getLastUpdateUser());
	}
	/**
	 * Delete product Commodity Group Mappings.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void deleteProductCommodityGroupMappings(String companyCode)
			throws SystemException{
		log.entering("ProductCommodityGroupMapping",
				"deleteProductCommodityGroupMappings");
		EntityManager entityManager = PersistenceController.getEntityManager();
		ProductDefaultsSqlDAO productDefaultsSqlDAO;
		try {
			productDefaultsSqlDAO = (ProductDefaultsSqlDAO)ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			productDefaultsSqlDAO.deleteProductCommodityGroupMappings(
					companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting("ProductCommodityGroupMapping",
				"deleteProductCommodityGroupMappings"); 
	}
}
