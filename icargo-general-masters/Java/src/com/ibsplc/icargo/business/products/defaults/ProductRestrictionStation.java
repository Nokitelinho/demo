/*
 * ProductRestrictionStation.java Created on Jun 27, 2005
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

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
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
@Table(name="PRDRSTSTN")
@Entity
@Staleable
public class ProductRestrictionStation {
	
	
    private ProductRestrictionStationPK productRestrictionStationPk;

    private String isRestricted;

    /**
     * Default Constructor
     *
     */
	public ProductRestrictionStation(){
	}
	/**
	 * Constructor for handling the Product Restricted Station
	 * @param companyCode
	 * @param productCode
	 * @param restrictionStationVo
	 * @throws SystemException
	 */
	public ProductRestrictionStation(String companyCode,String productCode,RestrictionStationVO restrictionStationVo)
	throws SystemException{
	 	populatePk(companyCode,productCode,restrictionStationVo);
	 	populateAttribute(restrictionStationVo);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}

/**
 * @return Returns the productRestrictionStationPk.
 */
 @EmbeddedId
 	@AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
		 @AttributeOverride(name="stationCode", column=@Column(name="STNCOD")),
		 @AttributeOverride(name="isOrigin", column=@Column(name="ORGFLG"))
	 })
    public ProductRestrictionStationPK getProductRestrictionStationPk() {
        return productRestrictionStationPk;
    }
    /**
     * @param productRestrictionStationPk The productRestrictionStationPk to set.
     */
    public void setProductRestrictionStationPk(
            ProductRestrictionStationPK productRestrictionStationPk) {
        this.productRestrictionStationPk = productRestrictionStationPk;
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
	 * Method for retrieving the ProductRestricted Station
	 * @return
	 */
    public RestrictionStationVO retrieveVO(){
		RestrictionStationVO restrictionStationVO = new RestrictionStationVO();
		restrictionStationVO.setStation(this.getProductRestrictionStationPk().getStationCode());
		boolean originFlag=false;
		if(RestrictionStationVO.FLAG_YES.equals(this.getProductRestrictionStationPk().getIsOrigin())){
			originFlag=true;
		}
		restrictionStationVO.setIsOrigin(originFlag);
		boolean flag=false;
		if(RestrictionStationVO.FLAG_YES.equals(this.getIsRestricted())){
			flag=true;
		}
		restrictionStationVO.setIsRestricted(flag);
		return restrictionStationVO;
	}
    /**
     * Method for removing an object
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
	 * Method for populating the Pk
	 * @param companyCode
	 * @param productCode
	 * @param restrictionStationVo
	 */
	private void populatePk(String companyCode,String productCode,RestrictionStationVO restrictionStationVo){
	 	ProductRestrictionStationPK productRestrictionStationPK = new ProductRestrictionStationPK();
	 	productRestrictionStationPK.setCompanyCode(companyCode);
	 	productRestrictionStationPK.setProductCode(productCode);
	 	if(restrictionStationVo.getIsOrigin())
	 	{
	 		productRestrictionStationPK.setIsOrigin(RestrictionStationVO.FLAG_YES);
	 	}
	 	else
	 	{
	 		productRestrictionStationPK.setIsOrigin(RestrictionStationVO.FLAG_NO);
	 	}

	 	productRestrictionStationPK.setStationCode(restrictionStationVo.getStation());
	 	this.productRestrictionStationPk = productRestrictionStationPK;
	}
	/**
	 * Method for populating other attributes
	 * @param restrictionStationVo
	 */
	private void populateAttribute(RestrictionStationVO restrictionStationVo){
		if(restrictionStationVo.getIsRestricted())
		{
			this.isRestricted=RestrictionStationVO.FLAG_YES;
		}
		else
		{
		    this.isRestricted=RestrictionStationVO.FLAG_NO;
		}
	}
}
