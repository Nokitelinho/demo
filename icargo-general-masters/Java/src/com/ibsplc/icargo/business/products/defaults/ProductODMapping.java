/*
 * ProductODMappingPK.java Created on Sep 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

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

import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsPersistenceConstants;
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
 * @author A-4823
 *
 */
@Table(name="PRDODTMPG")
@Entity
@Staleable
public class ProductODMapping {
	/** The log. */
	private Log log = LogFactory.getLogger("ProductODMapping");
	private ProductODMappingPK productODMappingPK;	
	private String lastUpdateUser; 
	private Calendar lastUpdateTime;
	/**
	 * @return the productODMappingPK
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="productName", column=@Column(name="PRDNAM")),
		@AttributeOverride(name="originCode", column=@Column(name="ORGCOD")),
		@AttributeOverride(name="destinationCode", column=@Column(name="DSTCOD"))
		
	})
	public ProductODMappingPK getProductODMappingPK() {
		return productODMappingPK;
	}
	/**
	 * @param productODMappingPK the productODMappingPK to set
	 */
	public void setProductODMappingPK(ProductODMappingPK productODMappingPK) {
		this.productODMappingPK = productODMappingPK;
	}
	/**
	 * @return the lastUpdateUser
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return the lastUpdateTime
	 */
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	/**
	 * Default Constructor.
	 */
	public ProductODMapping(){

	}
	/**
	 * 
	 * @param productODMappingVO
	 * @throws SystemException
	 */
	public ProductODMapping(ProductODMappingVO productODMappingVO)
			throws SystemException{
		log.entering("ProductODMapping", "ProductODMapping");
		try{
			populatePk(productODMappingVO);
			populateAttributes(productODMappingVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("ProductODMapping", "ProductODMapping");
	}
	/**
	 * 
	 * @param productODMappingVO
	 */
	private void populateAttributes(ProductODMappingVO productODMappingVO) {
		log.entering("ProductODMapping", "populateAttributes");		
		setLastUpdateUser(productODMappingVO.getLastUpdateUser());
		log.exiting("ProductODMapping", "populateAttributes");

	}
	/**
	 * 
	 * @param productODMappingVO
	 */
	private void populatePk(ProductODMappingVO productODMappingVO) {
		ProductODMappingPK productODMappingPK = new ProductODMappingPK();
		productODMappingPK.setCompanyCode(productODMappingVO.getCompanyCode());
		productODMappingPK.setProductName(productODMappingVO.getProductName());
		productODMappingPK.setOriginCode(productODMappingVO.getOrigin());
		productODMappingPK.setDestinationCode(productODMappingVO.getDestination());
		setProductODMappingPK(productODMappingPK);

	}

	/**
	 * Removes the.
	 * @param companyCode 
	 *
	 * @throws RemoveException the remove exception
	 * @throws SystemException the system exception
	 */
	public void removeProductODMapping(String companyCode) throws SystemException  {
		log.entering("ProductODMapping",
				"removeProductMapping");
		try {
			 constructDAO().removeProductODMapping(companyCode);
		
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting("ProductODMapping",
				"removeProductODMapping"); 
	}
	public static Collection<ProductODMappingVO> getProductODMapping(
			String companyCode) throws SystemException {
		try{
			return constructDAO().getProductODMapping(companyCode);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	private static ProductDefaultsDAO constructDAO()
			throws SystemException, PersistenceException {
				EntityManager em = PersistenceController.getEntityManager();
				return ProductDefaultsDAO.class.cast
				(em.getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME));
	}



}
