/*
 * BlacklistStockAudit.java Created on Sep 1, 2005
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.
		BlacklistStockAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;


/**
 * @author A-1366
 *
 */
@Table(name="STKBLKLSTAUD")
@Entity
//@Staleable
public class BlacklistStockAudit {


    /**
     * BlacklistStockAuditPK
     */
    private BlacklistStockAuditPK blacklistStockAuditPk;

    /**
     * Action code
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
     * @return Returns the blacklistStockAuditPk.
     */
    @EmbeddedId
    @AttributeOverrides( {
    	@AttributeOverride(name="companyCode",
    			column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="documentType",
    			column=@Column(name="DOCTYP")),
    	@AttributeOverride(name="documentSubType",
    			column=@Column(name="DOCSUBTYP")),
    	@AttributeOverride(name="sequenceNumber",
    			column=@Column(name="SEQNUM"))}
       	)
    public BlacklistStockAuditPK getBlacklistStockAuditPk() {
        return blacklistStockAuditPk;
    }
    /**
     * @param blacklistStockAuditPk The blacklistStockAuditPk to set.
     */
    public void setBlacklistStockAuditPk(
            BlacklistStockAuditPK blacklistStockAuditPk) {
        this.blacklistStockAuditPk = blacklistStockAuditPk;
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

    public BlacklistStockAudit(){

    }

    public BlacklistStockAudit(BlacklistStockAuditVO blacklistStockAuditVO)
    throws SystemException{
    	BlacklistStockAuditPK blacklistStockAuditPK = new
    	BlacklistStockAuditPK();
    	blacklistStockAuditPK.setCompanyCode(blacklistStockAuditVO.
    	getCompanyCode());
    	blacklistStockAuditPK.setDocumentType(blacklistStockAuditVO.
    	getDocumentType());
    	blacklistStockAuditPK.setDocumentSubType(blacklistStockAuditVO.
    	getDocumentSubType());
    	this.blacklistStockAuditPk=blacklistStockAuditPK;
    	this.setActionCode(blacklistStockAuditVO.getActionCode());
    	this.setAdditionalInfo(blacklistStockAuditVO.getAdditionalInfo());
    	this.setAuditRemarks(blacklistStockAuditVO.getAuditRemarks());
    	this.setLastUpdateTime(blacklistStockAuditVO.getTxnLocalTime());
    	this.setLastUpdateUser(blacklistStockAuditVO.getUserId());
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    }
}
