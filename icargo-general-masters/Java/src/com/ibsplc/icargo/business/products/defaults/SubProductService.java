/*
 * SubProductService.java Created on July 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1358
 *
 */
@Table(name="PRDSUBSER")
@Entity
@Staleable
public class SubProductService {
	
	private Log log = LogFactory.getLogger("SUBPRODUCTSERVICE");
	
    private SubProductServicePK subProductServicePK;

	public SubProductService(){
	}
/**
 * constructor
 * @param companyCode
 * @param productCode
 * @param subProductCode
 * @param versionNumber
 * @param productServiceVo
 * @throws SystemException
 */
	public SubProductService(String companyCode,String productCode,String subProductCode,
			int versionNumber ,ProductServiceVO productServiceVo) throws SystemException{
		log.log(Log.FINE, "subprdser---ENTITY--CMPCOD-->", companyCode);
		log.log(Log.FINE, "subprdser---ENTITY--PRDCOD-->", productCode);
		log.log(Log.FINE, "subprdser---ENTITY--SUBPRDCOD->", subProductCode);
		log.log(Log.FINE, "subprdser---ENTITY--VERNUM-->", versionNumber);
		log.log(Log.FINE, "subprdser---ENTITY--SERVICEVO-->", productServiceVo);
		populatePk(companyCode,productCode,subProductCode,versionNumber,productServiceVo);
		try{
		PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
/**
 * This method is used to set primary key
 * @param companyCode
 * @param productCode
 * @param subProductCode
 * @param versionNumber
 * @param productServiceVo
 */
	private void populatePk(String companyCode,String productCode,String subProductCode,
			int versionNumber,ProductServiceVO productServiceVo){
		if(getSubProductServicesPk() == null){
			subProductServicePK = new SubProductServicePK();
		}
		this.subProductServicePK.setCompanyCode(companyCode);
		this.subProductServicePK.setProductCode(productCode);
		this.subProductServicePK.setSubProductCode(subProductCode);
		this.subProductServicePK.setVersionNumber(versionNumber);
		this.subProductServicePK.setServiceCode(productServiceVo.getServiceCode());

	}
	/**
     * @return Returns the productServicesPk.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			 @AttributeOverride(name="versionNumber",column=@Column(name="VERNUM")),
			 @AttributeOverride(name="serviceCode", column=@Column(name="PRDSERCOD"))
		 })
    public SubProductServicePK getSubProductServicesPk() {
        return subProductServicePK;
    }
    /**
     * @param productServicesPk The productServicesPk to set.
     * @param subProductServicesPk
     */
    public void setSubProductServicesPk(SubProductServicePK subProductServicesPk) {
        this.subProductServicePK = subProductServicesPk;
    }
    /**
     * 
     * @return ProductServiceVO
     */
	public ProductServiceVO retrieveVO(){
		ProductServiceVO productServiceVO = new ProductServiceVO();
		productServiceVO.setServiceCode(this.getSubProductServicesPk().getServiceCode());
		return productServiceVO;
	}
	/**
	 * This method is used to delete SubProduct object
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
}
