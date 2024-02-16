/*
 * ProductRestrictionCommodity.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
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
@Table(name="PRDRSTCMD")
@Entity
@Staleable
public class ProductRestrictionCommodity {
	 
	
    private ProductRestrictionCommodityPK productRestrictionCommodityPK;

    private String isRestricted;

    /**
     * Default Constructor
     *
     */
	public ProductRestrictionCommodity(){
	}
	/**
	 * Constructor for creating the productRestriction Commodity
	 * @param companyCode
	 * @param productCode
	 * @param restrictionCommodityVo
	 * @throws SystemException
	 */
	public ProductRestrictionCommodity(String companyCode,String productCode,RestrictionCommodityVO restrictionCommodityVo)
	throws SystemException{
	 	populatePk(companyCode,productCode,restrictionCommodityVo);
	 	populateAttribute(restrictionCommodityVo);
	 	try{
	 	PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	* @return Returns the productRestrictionCommodityPK.
	*/
	 @EmbeddedId
	 	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="commodityCode", column=@Column(name="CMDCOD"))
				 })
    public ProductRestrictionCommodityPK getProductRestrictionCommodityPK() {
        return productRestrictionCommodityPK;
    }
    /**
     * @param productRestrictionCommodityPK The productRestrictionCommodityPK to set.
     */
    public void setProductRestrictionCommodityPK(
            ProductRestrictionCommodityPK productRestrictionCommodityPK) {
        this.productRestrictionCommodityPK = productRestrictionCommodityPK;
    }
    /**
     * @return Returns the isRestricted.
     */
    @Column(name="RSTFLG")
    public String getIsRestricted() {
        return isRestricted;
    }
    /**
     * @param isRestricted The isRestricted to set.
     */
    public void setIsRestricted(String isRestricted) {
        this.isRestricted = isRestricted;
    }
	/**
	 * Method for retrieving the ProductCommodities
	 * @return RestrictionCommodityVO
	 */
    public RestrictionCommodityVO retrieveVO(){
		RestrictionCommodityVO restrictionCommodityVO = new RestrictionCommodityVO();
		restrictionCommodityVO.setCommodity(this.getProductRestrictionCommodityPK().getCommodityCode());
		boolean flag=false;
		if(RestrictionCommodityVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionCommodityVO.setIsRestricted(flag);
		return restrictionCommodityVO;
	}
	/**
	 * Method for removing and object
	 * @throws SystemException
	 */
    public void remove()
			throws SystemException{
			EntityManager entityManager = PersistenceController.getEntityManager();
			try{
				entityManager.remove(this);
			}catch(RemoveException removeException){
				throw new SystemException(removeException.getErrorCode());
			}
	}
	
	/**
	 * Method for populating the PK
	 * @param companyCode
	 * @param productCode
	 * @param restrictionCommodityVo
	 */
    private void populatePk(String companyCode,String productCode,RestrictionCommodityVO restrictionCommodityVo){
	 	ProductRestrictionCommodityPK productRestrictionCommodityPk = new ProductRestrictionCommodityPK();
	 	productRestrictionCommodityPk.setCompanyCode(companyCode);
	 	productRestrictionCommodityPk.setProductCode(productCode);
	 	productRestrictionCommodityPk.setCommodityCode(restrictionCommodityVo.getCommodity());
	 	this.productRestrictionCommodityPK = productRestrictionCommodityPk;
	}
    /**
     * Method for populating other attributes
     * @param restrictionCommodityVo
     */
	private void populateAttribute(RestrictionCommodityVO restrictionCommodityVo){
		if(restrictionCommodityVo.getIsRestricted())
		{
		this.isRestricted=RestrictionCommodityVO.FLAG_YES;
		}
		else
		{
		this.isRestricted=RestrictionCommodityVO.FLAG_NO;
		}
	}

}
