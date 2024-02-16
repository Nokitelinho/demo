/*
 * ProductRestrictionSegment.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
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
@Table(name="PRDRSTSEG")
@Entity
@Staleable
public class ProductRestrictionSegment {
	 
	
    private ProductRestrictionSegmentPK productRestrictionSegmentPK;

    private String isRestricted;

    /**
     * Default Constructor
     *
     */
	public ProductRestrictionSegment(){
	}
	/**
	 * Constructor for Handling Product restricted Segments
	 * @param companyCode
	 * @param productCode
	 * @param restrictionSegmentVo
	 * @throws SystemException
	 */
	public ProductRestrictionSegment(String companyCode,String productCode,RestrictionSegmentVO restrictionSegmentVo)
	throws SystemException{
	 	populatePk(companyCode,productCode,restrictionSegmentVo);
	 	populateAttribute(restrictionSegmentVo);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
   /**
	* @return Returns the productRestrictionSegmentPK.
	*/
@EmbeddedId
	@AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
		 @AttributeOverride(name="origin", column=@Column(name="ORGCOD")),
		 @AttributeOverride(name="destination", column=@Column(name="DSTCOD"))
	 })
    public ProductRestrictionSegmentPK getProductRestrictionSegmentPK() {
        return productRestrictionSegmentPK;
    }
    /**
     * @param productRestrictionSegmentPK The productRestrictionSegmentPK to set.
     */
    public void setProductRestrictionSegmentPK(
            ProductRestrictionSegmentPK productRestrictionSegmentPK) {
      		 this.productRestrictionSegmentPK = productRestrictionSegmentPK;
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
	 * Method for retrieving Product segments
	 * @return
	 */
    public RestrictionSegmentVO retrieveVO(){
		RestrictionSegmentVO restrictionSegmentVO = new RestrictionSegmentVO();
		restrictionSegmentVO.setDestination(this.getProductRestrictionSegmentPK().getDestination());
		restrictionSegmentVO.setOrigin(this.getProductRestrictionSegmentPK().getOrigin());
		boolean flag=false;
		if(RestrictionSegmentVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionSegmentVO.setIsRestricted(flag);
		return restrictionSegmentVO;
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
	 * @param restrictionSegmentVo
	 */
    private void populatePk(String companyCode,String productCode,RestrictionSegmentVO restrictionSegmentVo){
	 	ProductRestrictionSegmentPK productRestrictionSegmentPk = new ProductRestrictionSegmentPK();
	 	productRestrictionSegmentPk.setCompanyCode(companyCode);
	 	productRestrictionSegmentPk.setProductCode(productCode);
	 	productRestrictionSegmentPk.setOrigin(restrictionSegmentVo.getOrigin().toUpperCase());
	 	productRestrictionSegmentPk.setDestination(restrictionSegmentVo.getDestination().toUpperCase());
	 	this.productRestrictionSegmentPK = productRestrictionSegmentPk;
	}

	/**
	 * Method for populating other attributes
	 * @param restrictionSegmentVo
	 */
    private void populateAttribute(RestrictionSegmentVO restrictionSegmentVo){
		if(restrictionSegmentVo.getIsRestricted())
		{
			this.isRestricted=RestrictionSegmentVO.FLAG_YES;
		}
		else
		{
		    this.isRestricted=RestrictionSegmentVO.FLAG_NO;
		}
	}
    
   
    /**
     * Added by @author a-3351 for bug (92603) fix starts
     * @author A-3072
	 * Method for update
	 * @param restrictionSegmentVo
	 */
    public void update(RestrictionSegmentVO restrictionSegmentVo){
    	populateAttribute(restrictionSegmentVo);
	}
}
