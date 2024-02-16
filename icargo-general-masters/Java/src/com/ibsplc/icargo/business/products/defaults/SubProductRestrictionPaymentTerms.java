/*
 * SubProductRestrictionPaymentTerms.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
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
@Table(name="PRDSUBRSTPMTTRM")
@Entity
@Staleable
public class SubProductRestrictionPaymentTerms {

    private SubProductRestrictionPaymentTermsPK subProductRestrictionPaymentTermsPK;

    private String isRestricted;

    /**
     * @return Returns the subProductRestrictionPaymentTermsPK.
     */
     @EmbeddedId
     	@AttributeOverrides({
				 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
				 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
				 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
				 @AttributeOverride(name="versionNumber", column=@Column(name="VERNUM")),
				 @AttributeOverride(name="paymentTerm", column=@Column(name="PMTTRM"))
				 })
    public SubProductRestrictionPaymentTermsPK getSubProductRestrictionPaymentTermsPK() {
        return subProductRestrictionPaymentTermsPK;
    }
    /**
     * @param subProductRestrictionPaymentTermsPK The subProductRestrictionPaymentTermsPK to set.
     */
    public void setSubProductRestrictionPaymentTermsPK(
            SubProductRestrictionPaymentTermsPK subProductRestrictionPaymentTermsPK) {
        this.subProductRestrictionPaymentTermsPK = subProductRestrictionPaymentTermsPK;
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
	public SubProductRestrictionPaymentTerms(){

	}

	/**
	 * This method is used to create SubProductRestrictionPaymentTerms
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versonNo
	 * @param restrictionPaymentTermsVo
	 * @throws SystemException
	 */
	public SubProductRestrictionPaymentTerms(String companyCode,
			String productCode,String subProductCode,int versonNo,
			RestrictionPaymentTermsVO restrictionPaymentTermsVo)
		throws SystemException{
	   	try{
	   		populatePK(companyCode,productCode,subProductCode,versonNo,
	   				restrictionPaymentTermsVo);
			populateAttribute(restrictionPaymentTermsVo);
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
	 * Method for retrieving SubProductPayment terms
	 * @return RestrictionPaymentTermsVO
	 */
	 public RestrictionPaymentTermsVO retrieveVO(){
		RestrictionPaymentTermsVO restrictionPaymentTermsVO = new RestrictionPaymentTermsVO();
		restrictionPaymentTermsVO.setPaymentTerm(this.getSubProductRestrictionPaymentTermsPK().getPaymentTerm());
		boolean flag=false;
		if(RestrictionPaymentTermsVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionPaymentTermsVO.setIsRestricted(flag);
		return restrictionPaymentTermsVO;
	}

	/**
	 * This method is used to populate SubProductRestrictionPaymentTermPK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @param versionNo
	 * @param restrictionPaymentTermsVo
	 */
	private void populatePK(String companyCode,String productCode,
			String subProductCode,int versionNo,RestrictionPaymentTermsVO
			restrictionPaymentTermsVo){
		if(this.subProductRestrictionPaymentTermsPK == null){
			this.subProductRestrictionPaymentTermsPK = new
			SubProductRestrictionPaymentTermsPK(companyCode,productCode,
					subProductCode,versionNo,restrictionPaymentTermsVo.
					getPaymentTerm());
		}

	}

	/**
	 * This method is used to populate the attributes other than PK
	 * @param restrictionPaymentTermsVo
	 */
	private void populateAttribute(RestrictionPaymentTermsVO restrictionPaymentTermsVo){
		if(restrictionPaymentTermsVo.getIsRestricted()){
			this.setIsRestricted(RestrictionPaymentTermsVO.FLAG_YES);
		}else{
			this.setIsRestricted(RestrictionPaymentTermsVO.FLAG_NO);
		}
	}

}
