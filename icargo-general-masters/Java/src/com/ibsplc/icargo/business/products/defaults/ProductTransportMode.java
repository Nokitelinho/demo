/*
 * ProductTransportMode.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
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
@Table(name="PRDTRAMOD")
@Entity
@Staleable
public class ProductTransportMode {

    private ProductTransportModePK productTransportModePK;

    /**
     * Default Constructor
     *
     */
	public ProductTransportMode(){
	}
	/**
	 * Constructor for creating ProductTransportMode
	 * @param companyCode
	 * @param productCode
	 * @param productTransportModeVo
	 * @throws SystemException
	 */
	public ProductTransportMode(String companyCode,String productCode,ProductTransportModeVO productTransportModeVo)
	throws SystemException{
		try{
		populatePk(companyCode,productCode,productTransportModeVo);
		PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * @return Returns the productTransportModePk.
	 */
@EmbeddedId
	@AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
		 @AttributeOverride(name="transportMode", column=@Column(name="PRDTRAMOD"))
	 })
	public ProductTransportModePK getProductTransportModePk() {
        return productTransportModePK;
    }
    /**
     * @param productTransportModePk The productTransportModePk to set.
     */
    public void setProductTransportModePk(
            ProductTransportModePK productTransportModePk) {
        this.productTransportModePK = productTransportModePk;
    }
	/**
	 * Method for retrieving the ProductTransportMode
	 * @return ProductTransportModeVO
	 */
    public ProductTransportModeVO retrieveVO(){
		ProductTransportModeVO productTransportModeVO = new ProductTransportModeVO();
		productTransportModeVO.setTransportMode(this.getProductTransportModePk().getTransportMode());
		return productTransportModeVO;
	}
    /**
     * Method for removing and object
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
	 * Method for populating the primarykey
	 * @param companyCode
	 * @param productCode
	 * @param productTransportModeVo
	 */
	private void populatePk(String companyCode,String productCode,ProductTransportModeVO productTransportModeVo){
		ProductTransportModePK productTansportModePk = new ProductTransportModePK();
		productTansportModePk.setCompanyCode(companyCode);
		productTansportModePk.setProductCode(productCode);
		productTansportModePk.setTransportMode(productTransportModeVo.getTransportMode());
		this.productTransportModePK = productTansportModePk;
	}
}
