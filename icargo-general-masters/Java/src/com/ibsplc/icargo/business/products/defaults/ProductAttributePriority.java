/*
 * ProductAttributePriority.java
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductAttributePriorityVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class ProductAttributePriority.
 * @author A-6843
 */
@Table(name="PRDATRPRY")
@Entity
@Staleable
public class ProductAttributePriority {

	/** The log. */
	private Log log = LogFactory.getLogger("ProductAttributePriority");
	
	 private ProductAttributePriorityPK productAttributePriorityPK; 
	 private String lastUpdateUser; 
	 
 	/** The last update time. */
 	private Calendar lastUpdateTime;
 	
	@EmbeddedId
	@AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="attributeName", column=@Column(name="ATRNAM")),
		 @AttributeOverride(name="priority", column=@Column(name="ATRPRY"))
			})
	public ProductAttributePriorityPK getProductAttributePriorityPK() {
		return productAttributePriorityPK;
	}

	public void setProductAttributePriorityPK(
			ProductAttributePriorityPK productAttributePriorityPK) {
		this.productAttributePriorityPK = productAttributePriorityPK;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * Sets the last update user.
	 *
	 * @param lastUpdateUser the new last update user
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
	 * Sets the last update time.
	 *
	 * @param lastUpdateTime the new last update time
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	 /**
   	 * Default Constructor.
   	 */
	public ProductAttributePriority(){
	}

	/**
	 * Constructor for ProductCommodityGroup for creating Product CommodityGroup
	 * @param productAttributePriorityVO
	 * @throws SystemException
	 */
	public ProductAttributePriority(
			ProductAttributePriorityVO productAttributePriorityVO)
	throws SystemException{
		try{
			populatePk(productAttributePriorityVO);
			populateAttributes(productAttributePriorityVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
	
	/**
	 * Method for populating the primary key
	 * @param productAttributePriorityVO
	 */
    private void populatePk(
    		ProductAttributePriorityVO productAttributePriorityVO) {
    	ProductAttributePriorityPK productAttributePriorityPK =
    			new ProductAttributePriorityPK();
    	productAttributePriorityPK.setCompanyCode(
    			productAttributePriorityVO.getCompanyCode());
    	productAttributePriorityPK.setAttributeName(
    			productAttributePriorityVO.getAttributeName());
    	productAttributePriorityPK.setPriority(
    			productAttributePriorityVO.getPriority());
    	setProductAttributePriorityPK(productAttributePriorityPK);
	}
	/**
	 * @param productAttributePriorityVO
	 */
	public void populateAttributes(
			ProductAttributePriorityVO productAttributePriorityVO) {
		setLastUpdateTime(
				productAttributePriorityVO.getLastUpdateTime());
		setLastUpdateUser(
				productAttributePriorityVO.getLastUpdateUser());
	}
	
	
	/**
	 * Delete Product Attribute Priorities.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void deleteProductAttributePriorities(String companyCode)
			throws SystemException{
		log.entering("ProductAttributePriority",
				"deleteProductAttributePriorities");
		EntityManager entityManager = PersistenceController.getEntityManager();
		ProductDefaultsSqlDAO productDefaultsSqlDAO;
		try {
			productDefaultsSqlDAO = (ProductDefaultsSqlDAO)ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			productDefaultsSqlDAO.deleteProductAttributePriorities(
					companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting("ProductAttributePriority",
				"deleteProductAttributePriorities"); 
	}


}
