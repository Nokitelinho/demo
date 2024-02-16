/*
 * StockAirlineAudit.java Created on Oct 12, 2006
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAirlineAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1954
 * This class handles the persistence of stock airline audit
 */

@Table(name="STKARLAUD")
@Entity
public class StockAirlineAudit {

	private Log log = LogFactory.getLogger("STOCK-AUDIT");
    private StockAirlineAuditPK stockAirlineAuditPk;

    private String actionCode;
    private String additionalInfo;
    private String auditRemarks;
	private String updatedUser;
	private Calendar updatedTime;
	private String updatedStationCode;
	private Calendar updatedUTCTime;
	
	
	/**
	 * @return Returns the updatedStationCode.
	 */
	@Column(name = "STNCOD")
	public String getUpdatedStationCode() {
		return updatedStationCode;
	}

	/**
	 * @param updatedStationCode The updatedStationCode to set.
	 */
	public void setUpdatedStationCode(String updatedStationCode) {
		this.updatedStationCode = updatedStationCode;
	}

	/**
	 * @return Returns the updatedTime.
	 */
	@Version
	@Column(name = "UPDTXNTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime The updatedTime to set.
	 */
	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return Returns the updatedUser.
	 */
	@Column(name = "UPDUSR")
	public String getUpdatedUser() {
		return updatedUser;
	}

	/**
	 * @param updatedUser The updatedUser to set.
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	/**
	 * @return Returns the updatedUTCTime.
	 */
	@Column(name = "UPDTXNTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedUTCTime() {
		return updatedUTCTime;
	}

	/**
	 * @param updatedUTCTime The updatedUTCTime to set.
	 */
	public void setUpdatedUTCTime(Calendar updatedUTCTime) {
		this.updatedUTCTime = updatedUTCTime;
	}

 
    @EmbeddedId
    @AttributeOverrides( {
    	@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="airlineId", column=@Column(name="ARLIDR")),
    	@AttributeOverride(name="stockHolderCode", column=@Column(name="STKHLDCOD")),
    	@AttributeOverride(name="documentType", column=@Column(name="DOCTYP")),
    	@AttributeOverride(name="documentSubType", column=@Column(name="DOCSUBTYP")),
    	@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))} )
   	public StockAirlineAuditPK getStockAirlineAuditPk() {
        return stockAirlineAuditPk;
    }

    /**
     * @param stockHolderAuditPk The stockHolderAuditPk to set.
     */
    public void setStockAirlineAuditPk(StockAirlineAuditPK stockAirlineAuditPk) {
        this.stockAirlineAuditPk = stockAirlineAuditPk;
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
     * 
     */
    public StockAirlineAudit(){
    	
    }
    /**
     * @param stockAirlineAuditVO
     * @throws SystemException
     */
    public StockAirlineAudit(StockAirlineAuditVO stockAirlineAuditVO)
    	throws SystemException{
    	log.entering("StockAirlineAudit","Constructor");
    	StockAirlineAuditPK pk = new StockAirlineAuditPK();
    	pk.setCompanyCode(stockAirlineAuditVO.getCompanyCode());
    	pk.setAirlineId(stockAirlineAuditVO.getAirlineId());
    	pk.setStockHolderCode(stockAirlineAuditVO.getStockHolderCode());
    	pk.setDocumentSubType(stockAirlineAuditVO.getDocumentSubType());
    	pk.setDocumentType(stockAirlineAuditVO.getDocumentType());
    	
    	this.setStockAirlineAuditPk(pk);
    	
    	this.setActionCode(stockAirlineAuditVO.getActionCode());
    	this.setAuditRemarks(stockAirlineAuditVO.getAuditRemarks());
    	this.setAdditionalInfo(stockAirlineAuditVO.getAdditionalInfo());
    	//this.setStockHolderType(stockAirlineAuditVO.getStockHolderType());
    	this.setUpdatedUTCTime(stockAirlineAuditVO.getTxnTime());
    	this.setUpdatedUser(stockAirlineAuditVO.getUserId());
    	this.setUpdatedStationCode(stockAirlineAuditVO.getStationCode());
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    	log.exiting("StockAirlineAudit","Constructor");
    }
}
