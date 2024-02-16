/*
 * SubProductRestrictionCommodity.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
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
 @Table(name="PRDSUBRSTCMD")
 @Entity
 @Staleable
public class SubProductRestrictionCommodity {
	
	private SubProductRestrictionCommodityPK subProductRestrictionCommodityPK;
    private String isRestricted;

    /**
     * @return Returns the subProductRestrictionCommodityPK.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			 @AttributeOverride(name="versionNumber", column=@Column(name="VERNUM")),
			 @AttributeOverride(name="commodityCode", column=@Column(name="CMDCOD"))
			 })
    public SubProductRestrictionCommodityPK getSubProductRestrictionCommodityPK() {
        return subProductRestrictionCommodityPK;
    }
    /**
     * @param subProductRestrictionCommodityPK The subProductRestrictionCommodityPK to set.
     */
    public void setSubProductRestrictionCommodityPK(
            SubProductRestrictionCommodityPK subProductRestrictionCommodityPK) {
        this.subProductRestrictionCommodityPK = subProductRestrictionCommodityPK;
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
     * Default Constructor
     *
     */
	public SubProductRestrictionCommodity(){
	}
    
	/**
	 * This constructor is used to create a SubProductRestrictionCommodity
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionCommodityVo
	 * @throws SystemException
	 */
	public SubProductRestrictionCommodity(String companyCode,String productCode,
			String subProductCode,int versonNo,RestrictionCommodityVO 
			restrictionCommodityVo)throws SystemException{
    	try{
				populatePK(companyCode,productCode,subProductCode,versonNo,
						restrictionCommodityVo);
				populateAttribute(restrictionCommodityVo);
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
	public void remove()throws SystemException{
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			entityManager.remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
	}
	
	/**
	 * 
	 * @return RestrictionCommodityVO
	 */
	public RestrictionCommodityVO retrieveVO(){
		RestrictionCommodityVO restrictionCommodityVO = new RestrictionCommodityVO();
		restrictionCommodityVO.setCommodity(this.getSubProductRestrictionCommodityPK().getCommodityCode());
		if(RestrictionCommodityVO.FLAG_YES.equals(this.getIsRestricted())){
			restrictionCommodityVO.setIsRestricted(true);
		}else{
			restrictionCommodityVO.setIsRestricted(false);
		}
		return restrictionCommodityVO;
	}
	
	/**
	 * This method is used to populate SubProductRestrictionCommodityPK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionCommodityVo
	 */
	private void populatePK(String companyCode,String productCode,
			String subProductCode,int versonNo,RestrictionCommodityVO 
			restrictionCommodityVo){
			if(this.subProductRestrictionCommodityPK == null)
			{
			this.subProductRestrictionCommodityPK = new SubProductRestrictionCommodityPK(companyCode,productCode,
					subProductCode,versonNo,restrictionCommodityVo.getCommodity());
			}
		
	}
	/**
	 * This method is used to populate the attributes other than PK
	 * @param restrictionCommodityVo
	 */
	private void populateAttribute(RestrictionCommodityVO restrictionCommodityVo){
		if(restrictionCommodityVo.getIsRestricted()){
			this.setIsRestricted(RestrictionCommodityVO.FLAG_YES);
		}else{
			this.setIsRestricted(RestrictionCommodityVO.FLAG_NO);
		}
	}
}
