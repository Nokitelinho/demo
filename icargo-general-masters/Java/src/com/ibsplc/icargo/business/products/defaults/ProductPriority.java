/*
 * ProductPriority.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * ProductPrioirty Class is for handling ProductPriorities
 * @author A-1358
 *
 */
@Table(name="PRDPRY")
@Entity
@Staleable
public class ProductPriority {

    private ProductPriorityPK productPriorityPK;

    /**
     * Default Constructor
     *
     */
	public ProductPriority(){
	}

	/**
	 * Constructor for ProductPriority for creating product priorities
	 * @param companyCode
	 * @param productCode
	 * @param productPriorityVo
	 * @throws SystemException
	 */
	public ProductPriority(String companyCode,String productCode,ProductPriorityVO productPriorityVo)
	throws SystemException{
		try{
			populatePk(companyCode,productCode,productPriorityVo);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	 /**
     * @return Returns the productPriorityPK.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="priority", column=@Column(name="PRDPRY"))
				})
    public ProductPriorityPK getProductPriorityPK() {
        return productPriorityPK;
    }
    /**
     * @param productPriorityPK The productPriorityPK to set.
     */
    public void setProductPriorityPK(ProductPriorityPK productPriorityPK) {
        this.productPriorityPK = productPriorityPK;
    }
	/**
	 * Method for retrieving ProductPriority
	 * @return ProductPriorityVO
	 */
    public ProductPriorityVO retrieveVO(){
		ProductPriorityVO productPriorityVO = new ProductPriorityVO();
		productPriorityVO.setPriority(this.getProductPriorityPK().getPriority());
		return productPriorityVO;
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
	 * @param companyCode
	 * @param productCode
	 * @param productPriorityVo
	 */
    private void populatePk(String companyCode,String productCode,ProductPriorityVO productPriorityVo){
		ProductPriorityPK productPriorityPk = new ProductPriorityPK();
		productPriorityPk.setCompanyCode(companyCode);
		productPriorityPk.setProductCode(productCode);
		productPriorityPk.setPriority(productPriorityVo.getPriority());
		this.productPriorityPK = productPriorityPk;
	}

}
