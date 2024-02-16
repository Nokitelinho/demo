/*
 * ProductRestrictionCustomerGroup.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
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
@Table(name="PRDRSTCUSGRP")
@Entity
@Staleable
public class ProductRestrictionCustomerGroup {
	 
		
    private ProductRestrictionCustomerGroupPK productRestrictionCustomerGroupPK;

    private String isRestricted;
    /**
     * Default Constructor
     *
     */
   	public ProductRestrictionCustomerGroup(){
	}
   	/**
   	 * Constructor for creating ProductCustomerGroups
   	 * @param companyCode
   	 * @param productCode
   	 * @param restrictionCustomerGroupVo
   	 * @throws SystemException
   	 */
	public ProductRestrictionCustomerGroup(String companyCode,String productCode, RestrictionCustomerGroupVO restrictionCustomerGroupVo)
	throws SystemException{
	  	populatePk(companyCode,productCode,restrictionCustomerGroupVo);
	 	populateAttribute(restrictionCustomerGroupVo);
		try{
		 	PersistenceController.getEntityManager().persist(this);
			}catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());
			}
	}

	 /**
     * @return Returns the productRestrictionCustomerGroupPK.
     */
	@EmbeddedId
		@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="customerGroup", column=@Column(name="CUSGRP"))
			 })
    public ProductRestrictionCustomerGroupPK getProductRestrictionCustomerGroupPK() {
        return productRestrictionCustomerGroupPK;
    }
    /**
     * @param productRestrictionCustomerGroupPK The productRestrictionCustomerGroupPK to set.
     */
    public void setProductRestrictionCustomerGroupPK(
            ProductRestrictionCustomerGroupPK productRestrictionCustomerGroupPK) {
        this.productRestrictionCustomerGroupPK = productRestrictionCustomerGroupPK;
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
	 * Method for retrieving product customer group
	 * @return RestrictionCustomerGroupVO
	 */
    public RestrictionCustomerGroupVO retrieveVO(){
		RestrictionCustomerGroupVO restrictionCustomerGroupVO = new RestrictionCustomerGroupVO();
		restrictionCustomerGroupVO.setCustomerGroup(this.getProductRestrictionCustomerGroupPK().getCustomerGroup());
		boolean flag=false;
		if(RestrictionCustomerGroupVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionCustomerGroupVO.setIsRestricted(flag);
		return restrictionCustomerGroupVO;
	}
	/**
	 * Method for removing an object
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
	 * @param restrictionCustomerGroupVo
	 */
    private void populatePk(String companyCode,String productCode, RestrictionCustomerGroupVO restrictionCustomerGroupVo){
	 	ProductRestrictionCustomerGroupPK productRestrictionCustomerGroupPk = new ProductRestrictionCustomerGroupPK();
	 	productRestrictionCustomerGroupPk.setCompanyCode(companyCode);
	 	productRestrictionCustomerGroupPk.setProductCode(productCode);
	 	productRestrictionCustomerGroupPk.setCustomerGroup(restrictionCustomerGroupVo.getCustomerGroup());
	 	this.productRestrictionCustomerGroupPK = productRestrictionCustomerGroupPk;
	}
    /**
     * Method for populating other attributes
     * @param restrictionCustomerGroupVo
     */
	private void populateAttribute( RestrictionCustomerGroupVO restrictionCustomerGroupVo){
			if(restrictionCustomerGroupVo.getIsRestricted())
			{
				this.isRestricted=RestrictionCustomerGroupVO.FLAG_YES;
			}
				else
				{
		        this.isRestricted=RestrictionCustomerGroupVO.FLAG_NO;
				}
	}
}
