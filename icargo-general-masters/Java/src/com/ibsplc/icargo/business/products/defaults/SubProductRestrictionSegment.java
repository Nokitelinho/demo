/*
 * SubProductRestrictionSegment.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
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
@Table(name="PRDSUBRSTSEG")
@Entity
@Staleable
public class SubProductRestrictionSegment {
	 
    private SubProductRestrictionSegmentPK subProductRestrictionSegmentPK;

    private String isRestricted;

    /**
     * @return Returns the subProductRestrictionSegmentPK.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			 @AttributeOverride(name="versionNumber", column=@Column(name="VERNUM")),
		 	 @AttributeOverride(name="origin", column=@Column(name="ORGCOD")),
		 	 @AttributeOverride(name="destination", column=@Column(name="DSTCOD"))
		 		})
    public SubProductRestrictionSegmentPK getSubProductRestrictionSegmentPK() {
        return subProductRestrictionSegmentPK;
    }
    /**
     * @param subProductRestrictionSegmentPK The subProductRestrictionSegmentPK to set.
     */
    public void setSubProductRestrictionSegmentPK(
            SubProductRestrictionSegmentPK subProductRestrictionSegmentPK) {
        this.subProductRestrictionSegmentPK = subProductRestrictionSegmentPK;
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
    public SubProductRestrictionSegment(){
	}
    
    /***
     * This method is used to create SubProductRestrictionSegment
     * @param companyCode
     * @param productCode
     * @param subProductCode
     * @param versonNo
     * @param restrictionSegmentVo
     * @throws SystemException
     */
	public SubProductRestrictionSegment(String companyCode,String productCode,
			String subProductCode,int versonNo,RestrictionSegmentVO
			restrictionSegmentVo)throws SystemException{
		try{
			populatePK(companyCode,productCode,subProductCode,versonNo,
					restrictionSegmentVo);
			populateAttribute(restrictionSegmentVo);
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
	 * Method for retrieving Product segments
	 * @return
	 */
    public RestrictionSegmentVO retrieveVO(){
		RestrictionSegmentVO restrictionSegmentVO = new RestrictionSegmentVO();
		restrictionSegmentVO.setDestination(this.getSubProductRestrictionSegmentPK().getDestination());
		restrictionSegmentVO.setOrigin(this.getSubProductRestrictionSegmentPK().getOrigin());
		boolean flag=false;
		if(RestrictionSegmentVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionSegmentVO.setIsRestricted(flag);
		return restrictionSegmentVO;
	}
	
	/**
	 * This method is used to populate SubProductRestrictionSegmentPK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionSegmentVo
	 */
	private void populatePK(String companyCode,String productCode,
			String subProductCode,int versonNo,
			RestrictionSegmentVO restrictionSegmentVo){
		if(subProductRestrictionSegmentPK == null)
		{
			subProductRestrictionSegmentPK = new 
			SubProductRestrictionSegmentPK(companyCode,productCode,
					subProductCode,versonNo,restrictionSegmentVo.getOrigin()
					.toUpperCase(),
					restrictionSegmentVo.getDestination().toUpperCase());
		}
	}
	
	/**
	 * This method is used to populate the attributes other than PK
	 * @param restrictionSegmentVo
	 */
	private void populateAttribute(RestrictionSegmentVO restrictionSegmentVo){
		if(restrictionSegmentVo.getIsRestricted()){
			this.setIsRestricted(RestrictionSegmentVO.FLAG_YES);
		}else{
			this.setIsRestricted(RestrictionSegmentVO.FLAG_NO);
		}
	}
	
	
	/**
	 * Added by @author a-3351 for bug (92603) fix starts
	 * update the attributes
	 * @param productEventVo
	 */
	public void update(RestrictionSegmentVO restrictionSegmentVo){
		populateAttribute(restrictionSegmentVo);
	}
}
