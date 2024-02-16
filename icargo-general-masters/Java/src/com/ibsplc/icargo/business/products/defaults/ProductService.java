/*
 * ProductService.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
/**
 * @author A-1358
 *
 */
@Table(name="PRDSER")
@Entity
@Staleable
public class ProductService {

    private ProductServicePK productServicesPk;
    /**
     * Default Constructor
     *
     */
	public ProductService(){
	}
	/**
	 * Constructor for creating product services
	 * @param companyCode
	 * @param productCode
	 * @param productServiceVo
	 * @throws SystemException
	 */
	public ProductService(String companyCode,String productCode,ProductServiceVO productServiceVo) throws SystemException{
		try{
			populatePk(companyCode,productCode,productServiceVo);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
    * @return Returns the productServicesPk.
	*/
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="serviceCode", column=@Column(name="PRDSERCOD"))
			 })
    public ProductServicePK getProductServicesPk() {
        return productServicesPk;
    }
    /**
     * @param productServicesPk The productServicesPk to set.
     */
    public void setProductServicesPk(ProductServicePK productServicesPk) {
        this.productServicesPk = productServicesPk;
    }
	/**
	 * Method for retrieving the Product Services
	 * @return ProductServiceVO
	 */
    public ProductServiceVO retrieveVO(){
		ProductServiceVO productServiceVO = new ProductServiceVO();
		productServiceVO.setServiceCode(this.getProductServicesPk().getServiceCode());
		return productServiceVO;
	}
	/**
	 * Method for removing an object
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
	 * @param productServiceVo
	 */
    private void populatePk(String companyCode,String productCode,ProductServiceVO productServiceVo){
		ProductServicePK productServicePk = new ProductServicePK();
		productServicePk.setCompanyCode(companyCode);
		productServicePk.setProductCode(productCode);
		productServicePk.setServiceCode(productServiceVo.getServiceCode());
		this.productServicesPk = productServicePk;
	}
}
