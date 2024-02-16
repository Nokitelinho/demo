/*
 * ProductRestrictionPaymentTerms.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
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
@Table(name="PRDRSTPMTTRM")
@Entity
@Staleable
public class ProductRestrictionPaymentTerms {
	
    private ProductRestrictionPaymentTermsPK productRestrictionPaymentTermsPK;

    private String isRestricted;
    /**
     * Default Constructor
     *
     */
	public ProductRestrictionPaymentTerms(){
	}
	/**
	 * Constructor for creating Product PaymentTerms
	 * @param companyCode
	 * @param productCode
	 * @param restrictionPaymentTermsVo
	 * @throws SystemException
	 */
	public ProductRestrictionPaymentTerms(String companyCode,String productCode, RestrictionPaymentTermsVO restrictionPaymentTermsVo)
	throws SystemException{
	 	populatePk(companyCode,productCode,restrictionPaymentTermsVo);
	 	populateAttribute(restrictionPaymentTermsVo);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

	 /**
	 * @return Returns the productRestrictionPaymentTermsPK.
	*/
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="paymentTerm", column=@Column(name="PMTTRM"))
		 })
    public ProductRestrictionPaymentTermsPK getProductRestrictionPaymentTermsPK() {
        return productRestrictionPaymentTermsPK;
    }
    /**
     * @param productRestrictionPaymentTermsPK The productRestrictionPaymentTermsPK to set.
     */
    public void setProductRestrictionPaymentTermsPK(
            ProductRestrictionPaymentTermsPK productRestrictionPaymentTermsPK) {
        this.productRestrictionPaymentTermsPK = productRestrictionPaymentTermsPK;
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
	 * Method for retrieving ProductPayment terms
	 * @return RestrictionPaymentTermsVO
	 */
    public RestrictionPaymentTermsVO retrieveVO(){
		RestrictionPaymentTermsVO restrictionPaymentTermsVO = new RestrictionPaymentTermsVO();
		restrictionPaymentTermsVO.setPaymentTerm(this.getProductRestrictionPaymentTermsPK().getPaymentTerm());
		boolean flag=false;
		if(RestrictionPaymentTermsVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionPaymentTermsVO.setIsRestricted(flag);
		return restrictionPaymentTermsVO;
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
	 * Method for populating PK
	 * @param companyCode
	 * @param productCode
	 * @param restrictionPaymentTermsVo
	 */
    private void populatePk(String companyCode,String productCode, RestrictionPaymentTermsVO restrictionPaymentTermsVo){
	 	ProductRestrictionPaymentTermsPK productRestrictionPaymentTermsPk = new ProductRestrictionPaymentTermsPK();
	 	productRestrictionPaymentTermsPk.setCompanyCode(companyCode);
	 	productRestrictionPaymentTermsPk.setProductCode(productCode);
		productRestrictionPaymentTermsPk.setPaymentTerm(restrictionPaymentTermsVo.getPaymentTerm());
	 	this.productRestrictionPaymentTermsPK = productRestrictionPaymentTermsPk;
	}
    /**
     * Method for populating other attributes
     * @param restrictionPaymentTermsVo
     */
	private void populateAttribute( RestrictionPaymentTermsVO restrictionPaymentTermsVo){
		if(restrictionPaymentTermsVo.getIsRestricted())
		{
			this.isRestricted=RestrictionPaymentTermsVO.FLAG_YES;
		}
		else
		{
		    this.isRestricted=RestrictionPaymentTermsVO.FLAG_NO;
		}
	}
}
