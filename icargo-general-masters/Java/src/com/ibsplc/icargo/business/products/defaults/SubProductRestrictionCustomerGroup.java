/*
 * SubProductRestrictionCustomerGroup.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import javax.persistence.AttributeOverrides;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.AttributeOverride;
import javax.persistence.EmbeddedId;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
/**
 * @author A-1358
 *
 */
@Table(name="PRDSUBRSTCUSGRP")
@Entity
@Staleable
public class SubProductRestrictionCustomerGroup {
	 
    private SubProductRestrictionCustomerGroupPK subProductRestrictionCustomerGroupPK;

    private String isRestricted;

    /**
     * @return Returns the subProductRestrictionCustomerGroupPK.
     */
      @EmbeddedId
      	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			 @AttributeOverride(name="versionNumber", column=@Column(name="VERNUM")),
			 @AttributeOverride(name="customerGroup", column=@Column(name="CUSGRP"))
			 })
    public SubProductRestrictionCustomerGroupPK getSubProductRestrictionCustomerGroupPK() {
        return subProductRestrictionCustomerGroupPK;
    }
    /**
     * @param subProductRestrictionCustomerGroupPK The subProductRestrictionCustomerGroupPK to set.
     */
    public void setSubProductRestrictionCustomerGroupPK(
            SubProductRestrictionCustomerGroupPK subProductRestrictionCustomerGroupPK) {
        this.subProductRestrictionCustomerGroupPK = subProductRestrictionCustomerGroupPK;
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
     * Default constructor
     *
     */
	public SubProductRestrictionCustomerGroup(){
		 
	 }
	
	/**
	 * This constructor is used to create a SubProductRestrictionCustomerGroup object
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionCustomerGroupVo
	 * @throws SystemException
	 */
	public SubProductRestrictionCustomerGroup(String companyCode,String 
			productCode,String subProductCode,int versonNo,
			RestrictionCustomerGroupVO restrictionCustomerGroupVo)
		throws SystemException {
	   	try{
			populatePK(companyCode,productCode,subProductCode,versonNo,restrictionCustomerGroupVo);
			populateAttribute(restrictionCustomerGroupVo);
			PersistenceController.getEntityManager().persist(this);
			}
			catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());
			}
	}
	
	/**
	 * This method is used to remove the Persistent Object from the database
	 * @throws SystemException
	 */
	public void remove() throws SystemException{
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			entityManager.remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
	 }
	
	/**
	 * Method for retrieving sub product customer group
	 * @return RestrictionCustomerGroupVO
	 */
    public RestrictionCustomerGroupVO retrieveVO(){
		RestrictionCustomerGroupVO restrictionCustomerGroupVO = new RestrictionCustomerGroupVO();
		restrictionCustomerGroupVO.setCustomerGroup(this.getSubProductRestrictionCustomerGroupPK().getCustomerGroup());
		boolean flag=false;
		if(RestrictionCustomerGroupVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionCustomerGroupVO.setIsRestricted(flag);
		return restrictionCustomerGroupVO;
	}

	/**
	 * This method is used to populate RestrictionCustomerGroupPK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionCustomerGroupVo
	 */
	private void populatePK(String companyCode,String productCode,
			String subProductCode,int versonNo,RestrictionCustomerGroupVO 
			restrictionCustomerGroupVo){
		if(subProductRestrictionCustomerGroupPK == null){
			subProductRestrictionCustomerGroupPK = new 
			SubProductRestrictionCustomerGroupPK(companyCode,productCode,
				subProductCode,	versonNo,restrictionCustomerGroupVo.
				getCustomerGroup());
		}
			
	}
	
	/**
	 * This method is used to populate the attributes other than PK
	 * @param restrictionCustomerGroupVo
	 */
	private void populateAttribute(RestrictionCustomerGroupVO restrictionCustomerGroupVo){
		if(restrictionCustomerGroupVo.getIsRestricted()){
			this.setIsRestricted(RestrictionCustomerGroupVO.FLAG_YES);
		}else{
			this.setIsRestricted(RestrictionCustomerGroupVO.FLAG_NO);
		}
	}
}
