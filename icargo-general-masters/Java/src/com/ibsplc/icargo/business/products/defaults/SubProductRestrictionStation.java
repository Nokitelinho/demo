/*
 * SubProductRestrictionStation.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
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
 @Table(name="PRDSUBRSTSTN")
 @Entity
 @Staleable
public class SubProductRestrictionStation {
	 

    private SubProductRestrictionStationPK subProductRestrictionStationPk;

    private String isRestricted;

    /**
     * @return Returns the subProductRestrictionStationPk.
     */
    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			 @AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			 @AttributeOverride(name="versionNumber", column=@Column(name="VERNUM")),
			 @AttributeOverride(name="stationCode", column=@Column(name="STNCOD")),
		 	 @AttributeOverride(name="isOrigin", column=@Column(name="ORGFLG"))
		 	 })
    public SubProductRestrictionStationPK getSubProductRestrictionStationPk() {
        return subProductRestrictionStationPk;
    }
    /**
     * @param subProductRestrictionStationPk The subProductRestrictionStationPk to set.
     */
    public void setSubProductRestrictionStationPk(
            SubProductRestrictionStationPK subProductRestrictionStationPk) {
        this.subProductRestrictionStationPk = subProductRestrictionStationPk;
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
    public SubProductRestrictionStation(){
	}
    
    /**
     * This method is used to create SubProductRestrictionStation
     * @param companyCode
     * @param productCode
     * @param subProductCode
     * @param versonNo
     * @param restrictionStationVo
     * @throws SystemException
     */
    public SubProductRestrictionStation(String companyCode,String productCode,
    		String subProductCode,int versonNo,RestrictionStationVO
    		restrictionStationVo)throws SystemException{
		try{
			populatePK(companyCode,productCode,subProductCode,versonNo,restrictionStationVo);
			populateAttribute(restrictionStationVo);
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
	 * Method for retrieving the ProductRestricted Station
	 * @return
	 */
    public RestrictionStationVO retrieveVO(){
		RestrictionStationVO restrictionStationVO = new RestrictionStationVO();
		restrictionStationVO.setStation(this.getSubProductRestrictionStationPk().getStationCode());
		boolean originFlag=false;
		if(RestrictionStationVO.FLAG_YES.equals(this.getSubProductRestrictionStationPk().getIsOrigin())){
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
     * This method is used to populate SubProductRestrictionSegmentPK
     * @param companyCode
     * @param productCode
     * @param subProductCode
     * @param versonNo
     * @param restrictionStationVo
     */
    private void populatePK(String companyCode,String productCode,String subProductCode,int versonNo,
    		RestrictionStationVO restrictionStationVo){
		if(this.subProductRestrictionStationPk == null){
			String isOrgin="";
			if(restrictionStationVo.getIsOrigin()){
				isOrgin=RestrictionStationVO.FLAG_YES;
			}else{
				isOrgin=RestrictionStationVO.FLAG_NO;
			}
			subProductRestrictionStationPk = new SubProductRestrictionStationPK
			(companyCode,productCode,subProductCode,versonNo,
					restrictionStationVo.getStation(),isOrgin);
		}
	}
	private void populateAttribute(RestrictionStationVO restrictionStationVo){
		if(restrictionStationVo.getIsRestricted()){
			this.setIsRestricted(RestrictionStationVO.FLAG_YES);
		}else{
			this.setIsRestricted(RestrictionStationVO.FLAG_NO);
		}
	}
	
}
