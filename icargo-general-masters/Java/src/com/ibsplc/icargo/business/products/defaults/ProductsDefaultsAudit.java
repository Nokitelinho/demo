/*
 * ProductsDefaultsAudit.java Created on Aug 23, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1366
 **/

 @Table(name="PRDDEFAUD")
 @Entity
 public class ProductsDefaultsAudit {
	 private Log log = LogFactory.getLogger("---ProductsDefaultsAudit---");
    /**
     * ProductsDefaultsAuditPK
     */
    private ProductsDefaultsAuditPK productsDefaultsAuditPk;
        
    /**
     * Action Code
     */
    private String actionCode;

    /**
     * Additional info
     */
    private String additionalInfo;
    
    /**
     * Audit Remarks
     */
    private String auditRemarks;

    /**
     * Last update user code
     */
    private String lastUpdateUser;

    /**
     * Last update date and time
     */
    private Calendar lastUpdateTime;
    
    private Calendar lastUpdateTimeUTC;
    
    private String stationCode;
    
    private String productName;
    
    /**
     * @return Returns the actionCode.
     * 
     */
    @Column(name="ACTCOD")
    public String getActionCode() {
        return actionCode;
    }
    /**
     * @param actionCode The actionCode to set.
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }
    /**
     * @return Returns the additionalInfo.
     * 
     */
    @Column(name="ADLINF")
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    /**
     * @param additionalInfo The additionalInfo to set.
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    /**
     * @return Returns the auditRemarks.
     * 
     */
    @Column(name="AUDRMK")
    public String getAuditRemarks() {
        return auditRemarks;
    }
    /**
     * @param auditRemarks The auditRemarks to set.
     */
    public void setAuditRemarks(String auditRemarks) {
        this.auditRemarks = auditRemarks;
    }
    
    /**
     * @return Returns the lastUpdateTime.
     * 
     */
    @Version
    @Column(name="UPDTXNTIM")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    /**
     * @return Returns the lastUpdateUser.
     * 
     */
    @Column(name="UPDUSR")
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    /**
     * @param lastUpdateUser The lastUpdateUser to set.
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
   
    
    /**
	 * @return Returns the productName.
	 */
    @Column(name="PRDNAM")
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

   
    /**
     * @return Returns the productsDefaultsAuditPk.
     * 
     */
    @EmbeddedId
    	@AttributeOverrides({
	    	@AttributeOverride(name="companyCode",column=@Column(name="CMPCOD")),
	    	@AttributeOverride(name="productCode",column=@Column(name="PRDCOD")),
	    	@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))
    	})
    public ProductsDefaultsAuditPK getProductsDefaultsAuditPk() {
        return productsDefaultsAuditPk;
    }
    /**
     * @param productsDefaultsAuditPk The productsDefaultsAuditPk to set.
     */
    public void setProductsDefaultsAuditPk(
            ProductsDefaultsAuditPK productsDefaultsAuditPk) {
        this.productsDefaultsAuditPk = productsDefaultsAuditPk;
    }
    
    
    /**
	 * @return Returns the lastUpdateTimeUTC.
	 */
    @Column(name = "UPDTXNTIMUTC")
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTimeUTC() {
		return lastUpdateTimeUTC;
	}
	/**
	 * @param lastUpdateTimeUTC The lastUpdateTimeUTC to set.
	 */
	public void setLastUpdateTimeUTC(Calendar lastUpdateTimeUTC) {
		this.lastUpdateTimeUTC = lastUpdateTimeUTC;
	}
	/**
	 * @return Returns the stationCode.
	 */
	@Column(name = "STNCOD")
	public String getStationCode() {
		return stationCode;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
    //Modified By A-1885@TRV on 05-Feb-2007 start
    /*
     * DefaultConstructor
     */
    public ProductsDefaultsAudit(){
    	
    }
    //Modified By A-1885@TRV on 05-Feb-2007 end
    /**
     * 
     * @param productsDefaultsAuditVO
     * @throws SystemException
     */
    public ProductsDefaultsAudit(ProductsDefaultsAuditVO productsDefaultsAuditVO)
    throws SystemException{
    	log.log(Log.FINE,"---------Going for audit ProductsDefaultsAudit-------");
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	ProductsDefaultsAuditPK productsDefaultsAuditPK = new ProductsDefaultsAuditPK();
       	productsDefaultsAuditPK.setCompanyCode(logonAttributes.getCompanyCode());
    	productsDefaultsAuditPK.setProductCode(productsDefaultsAuditVO.getProductCode());
    	this.productsDefaultsAuditPk=productsDefaultsAuditPK;
    	this.setActionCode(productsDefaultsAuditVO.getActionCode());
    	this.setAdditionalInfo(productsDefaultsAuditVO.getAdditionalInfo());
    	this.setAuditRemarks(productsDefaultsAuditVO.getAuditRemarks());
    	this.setActionCode(productsDefaultsAuditVO.getActionCode());
    	this.setLastUpdateTime(productsDefaultsAuditVO.getTxnLocalTime());
    	this.setLastUpdateUser(productsDefaultsAuditVO.getUserId());
    	this.setStationCode(productsDefaultsAuditVO.getStationCode());
    	this.setLastUpdateTimeUTC(productsDefaultsAuditVO.getTxnTime());
    	this.setProductName(productsDefaultsAuditVO.getProductName());
       	try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			createException.getMessage();
			throw new SystemException(createException.getErrorCode());
		}
    }
	
}
