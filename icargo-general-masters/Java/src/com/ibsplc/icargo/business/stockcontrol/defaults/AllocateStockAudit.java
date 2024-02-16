/*
 * AllocateStockAudit.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AllocateStockAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

/**
 * @author A-1358
 * This class handles the persistence of stock holder audit
 */

@Table(name="STKRNGAUD")
@Entity
//@Staleable
public class AllocateStockAudit {

    private AllocateStockAuditPK allocateStockAuditPk;


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
    
    private String docType;
    
    private String docSubType;

    /**
     * Last update user code
     */
    private String lastUpdateUser;

    /**
     * Last update date and time
     */
    private Calendar lastUpdateTime;

    /**
     * @return Returns the stockHolderAuditPk.
     */

    @EmbeddedId
    @AttributeOverrides( {
    	@AttributeOverride(name="companyCode",column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="stockHolderCode",column=@Column(name="STKHLDCOD")),
    	@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))}
       	)
   	public AllocateStockAuditPK getAllocateStockAuditPk() {
        return allocateStockAuditPk;
    }

    /**
     * @param stockHolderAuditPk The stockHolderAuditPk to set.
     */
    /**
     * @param allocateStockAuditPk
     */
    public void setAllocateStockAuditPk(AllocateStockAuditPK allocateStockAuditPk) {
        this.allocateStockAuditPk = allocateStockAuditPk;
    }


    /**
     * @return Returns the stockHolderType.
     */
    /*
    @Column(name="STKHLDTYP")
    public String getStockHolderType() {
        return stockHolderType;
    }

    /**
     * @param stockHolderType The stockHolderType to set.
     */
    /*
    public void setStockHolderType(String stockHolderType) {
        this.stockHolderType = stockHolderType;
    }*/

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
    @Column(name="LSTUPDTIM")
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
     */
    @Column(name="LSTUPDUSR")
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
    public AllocateStockAudit(){
    	
    }
    /**
     * @param allocateStockAuditVO
     * @throws SystemException
     */
    public AllocateStockAudit(AllocateStockAuditVO allocateStockAuditVO)
    		throws SystemException{
    	AllocateStockAuditPK allocateStockAuditPK=new AllocateStockAuditPK();
    	allocateStockAuditPK.setCompanyCode(allocateStockAuditVO.getCompanyCode());
    	allocateStockAuditPK.setStockHolderCode(allocateStockAuditVO.getStockHolderCode());
    	this.allocateStockAuditPk=allocateStockAuditPK;
    	this.setDocType(allocateStockAuditVO.getDocType());
    	this.setDocSubType(allocateStockAuditVO.getDocSubType());
    	this.setActionCode(allocateStockAuditVO.getActionCode());
    	this.setAuditRemarks(allocateStockAuditVO.getAuditRemarks());
    	this.setAdditionalInfo(allocateStockAuditVO.getAdditionalInfo());
    	this.setLastUpdateTime(allocateStockAuditVO.getLastUpdateTime());
    	this.setLastUpdateUser(allocateStockAuditVO.getLastUpdateUser());
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    }
    /**
     * @return
     */
    @Column(name="DOCTYP")
	public String getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}
	/**
	 * @return
	 */
	@Column(name="DOCSUBTYP")
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

}
