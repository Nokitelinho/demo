/*
 *  StockAudit.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 * This class handles the persistence of stock holder audit
 */

@Table(name="STKAUD")
@Entity
public class StockAudit {

	private Log log = LogFactory.getLogger("STOCK AUDIT");
	/**
	 * stockholder audit PK
	 */
    private StockAuditPK stockAuditPk;

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

    /**
      * Last update date and time UTC
     */
    private Calendar lastUpdateTimeUTC;
    /**
     * @return Returns the stockHolderAuditPk.
     */

    @EmbeddedId
    @AttributeOverrides( {
    	@AttributeOverride(name="companyCode",
    			column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="airlineIdentifier",
    	    	column=@Column(name="ARLIDR")),
    	@AttributeOverride(name="documentType",
    			column=@Column(name="DOCTYP")),
    	@AttributeOverride(name="documentSubType",
    			column=@Column(name="DOCSUBTYP")),
    	@AttributeOverride(name="sequenceNumber",
    			column=@Column(name="SEQNUM"))}
       	)
   	public StockAuditPK getStockAuditPk() {
        return stockAuditPk;
    }

    /**
     * @param stockAuditPkToSet
     */
    public void setStockAuditPk(StockAuditPK stockAuditPkToSet) {
        this.stockAuditPk = stockAuditPkToSet;
    }


  /**
     * @return Returns the actionCode.
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
   	 * @return Returns the lastUpdateTimeUTC.
   	 */
       @Column(name="UPDTXNTIMUTC")
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
     * @return Returns the lastUpdateUser.
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
     * 
     */
    public StockAudit(){
    	
    }

    public StockAudit(StockAuditVO stockAuditVO)
    throws SystemException{
    	log.log(Log.FINE, ">>>>>>>cmpanycode->", stockAuditVO.
    			getCompanyCode());
		StockAuditPK stockAuditPKToCreate=new StockAuditPK();
    	stockAuditPKToCreate.setCompanyCode(stockAuditVO.getCompanyCode());
    
    	stockAuditPKToCreate.setAirlineIdentifier(stockAuditVO.getAirlineId());
    	stockAuditPKToCreate.setDocumentType(stockAuditVO.getDocType());
    	stockAuditPKToCreate.setDocumentSubType(stockAuditVO.getDocSubType());
    	this.stockAuditPk=stockAuditPKToCreate;
    	this.setActionCode(stockAuditVO.getActionCode());
    	this.setAuditRemarks(stockAuditVO.getAuditRemarks());
    	this.setAdditionalInfo(stockAuditVO.getAdditionalInfo());
    	this.setLastUpdateTime(stockAuditVO.getTxnLocalTime());
    	this.setLastUpdateTimeUTC(stockAuditVO.getTxnTime());
    	this.setLastUpdateUser(stockAuditVO.getUserId());
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    }
}
