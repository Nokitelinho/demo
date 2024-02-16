/*
 * ProductSCC.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
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
@Table(name="PRDSCC")
@Entity
@Staleable
public class ProductSCC {

  	private ProductSCCPK productSccPk;
  	/**
  	 * Default Constructor
  	 *
  	 */
	public ProductSCC(){
	}
	/**
	 * Constructor of ProductSCC for handling ProductScc's
	 * @param companyCode
	 * @param productCode
	 * @param productSccVo
	 * @throws SystemException
	 */
	public  ProductSCC(String companyCode,String productCode,ProductSCCVO productSccVo)
	throws SystemException{
		try{
			populatePk(companyCode,productCode,productSccVo);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
	
	/**
	 * @return Returns the productSccPk.
	 */
	@EmbeddedId
		@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="sccCode", column=@Column(name="PRDSCC"))
			 })
    public ProductSCCPK getProductSccPk() {
        return productSccPk;
    }
    /**
     * @param productSccPk The productSccPk to set.
     */
    public void setProductSccPk(ProductSCCPK productSccPk) {
        this.productSccPk = productSccPk;
    }
	
    /**
     * Method for retrieving ProductScc's
     * @return
     */
    public ProductSCCVO retrieveVO(){
		ProductSCCVO productSccVO = new ProductSCCVO();
		productSccVO.setScc(this.getProductSccPk().getSccCode());
		return productSccVO;
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
	 * Method for populating the primaryKey
	 * @param companyCode
	 * @param productCode
	 * @param productSccVo
	 */
    private void populatePk(String companyCode,String productCode,ProductSCCVO productSccVo){
		ProductSCCPK productSccPK = new ProductSCCPK();
		productSccPK.setCompanyCode(companyCode);
		productSccPK.setProductCode(productCode);
		productSccPK.setSccCode(productSccVo.getScc());
		this.productSccPk = productSccPK;
	}
}
