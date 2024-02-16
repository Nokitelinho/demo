/*
 * StockHolderAudit.java Created on Jul 20, 2005
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 * This class handles the persistence of stock holder audit
 */

@Table(name="STKHLDAUD")
@Entity
public class StockHolderAudit {

	private Log log = LogFactory.getLogger("STOCK-AUDIT");
	/**
	 * stockholder audit PK
	 */
    private StockHolderAuditPK stockHolderAuditPk;

    /**
     * stock holder type
     * commented as part of DB standardisation
     */
    //private String stockHolderType;

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
    	@AttributeOverride(name="companyCode",
    			column=@Column(name="CMPCOD")),
    	@AttributeOverride(name="stockHolderCode",
    			column=@Column(name="STKHLDCOD")),
    	@AttributeOverride(name="sequenceNumber",
    			column=@Column(name="SEQNUM"))}
       	)
   	public StockHolderAuditPK getStockHolderAuditPk() {
        return stockHolderAuditPk;
    }

    /**
     * @param stockHolderAuditPk The stockHolderAuditPk to set.
     */
    public void setStockHolderAuditPk(StockHolderAuditPK stockHolderAuditPk) {
        this.stockHolderAuditPk = stockHolderAuditPk;
    }

   
    /**
     * @return Returns the stockHolderType.
     */
    //@Column(name="STKHLDTYP")
    /*public String getStockHolderType() {
        return stockHolderType;
    }

    *//**
     * @param stockHolderType The stockHolderType to set.
     *//*
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
     * 
     */
    public StockHolderAudit(){
    	
    }
    /**
     * @param stockHolderAuditVO
     * @throws SystemException
     */
    public StockHolderAudit(StockHolderAuditVO stockHolderAuditVO)
    	throws SystemException{
    	log.log(Log.FINE, ">>>>>>>cmpanycode->", stockHolderAuditVO.
    			getCompanyCode());
		log.log(Log.FINE, ">>>>>stockholder code->", stockHolderAuditVO.
    			getStockHolderCode());
		StockHolderAuditPK stockHolderAuditPK=new StockHolderAuditPK();
    	stockHolderAuditPK.setCompanyCode(stockHolderAuditVO.getCompanyCode());
    	stockHolderAuditPK.setStockHolderCode(stockHolderAuditVO.
    			getStockHolderCode());
    	this.stockHolderAuditPk=stockHolderAuditPK;
    	this.setActionCode(stockHolderAuditVO.getActionCode());
    	this.setAuditRemarks(stockHolderAuditVO.getAuditRemarks());
    	this.setAdditionalInfo(stockHolderAuditVO.getAdditionalInfo());
    	//this.setStockHolderType(stockHolderAuditVO.getStockHolderType());
    	this.setUpdatedUTCTime(stockHolderAuditVO.getTxnTime());
    	this.setUpdatedUser(stockHolderAuditVO.getUserId());
    	this.setUpdatedStationCode(stockHolderAuditVO.getStationCode());
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    }
}
