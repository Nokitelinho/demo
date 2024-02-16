/*
 * StockRequestAudit.java Created on Sep 7, 2005
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

/**
 * @author A-1366
 */
@Table(name="STKREQAUD")
@Entity

public class StockRequestAudit {

	/**
	 * StockRequestAuditPK
	 */
	private StockRequestAuditPK stockRequestAuditPk;

	/**
	 * Stock Holder Code
	 * commented as part of DB Standardisation
	 */
	//private String stockHolderCode;
	/**
	 * Action Code
	 */
	private String actionCode;
	/**
	 *  Document type
	 *  commented as part of DB Standardisation
	 */
	//private String documentType;
	/**
	 *  Document sub type
	 *  commented as part of DB Standardisation
	 */
	//private String documentSubType;
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
	 * @return Returns the documentType.
	 *//*
	@Column(name="DOCTYP")
	public String getDocumentType() {
		return documentType;
	}
	*//**
	 * @param documentType The documentType to set.
	 *//*
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	*//**
	 * @return Returns the documentSubType.
	 *//*
	@Column(name="DOCSUBTYP")
	public String getDocumentSubType() {
		return documentSubType;
	}
	*//**
	 * @param documentSubType The documentSubType to set.
	 *//*
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}*/

	public StockRequestAudit(){
	}
	/**
	 * Default Constructor
	 * @param stockRequestAuditVO
	 * @throws SystemException
	 */
	public StockRequestAudit(StockRequestAuditVO stockRequestAuditVO) throws SystemException {
		StockRequestAuditPK stockRequestAuditPK=new StockRequestAuditPK();
		stockRequestAuditPK.setCompanyCode(stockRequestAuditVO.getCompanyCode());
		stockRequestAuditPK.setRequestRefNumber(stockRequestAuditVO.
				getRequestRefNumber());
		this.stockRequestAuditPk = stockRequestAuditPK;
		this.actionCode = stockRequestAuditVO.getActionCode();
		this.additionalInfo=stockRequestAuditVO.getAdditionalInfo();
		this.auditRemarks = stockRequestAuditVO.getAuditRemarks();
		//this.documentSubType = stockRequestAuditVO.getDocumentSubType();
		//this.documentType = stockRequestAuditVO.getDocumentSubType();
		//this.stockHolderCode = stockRequestAuditVO.getStockHolderCode();
		this.setLastUpdateTime(stockRequestAuditVO.getTxnLocalTime());
    	this.setLastUpdateUser(stockRequestAuditVO.getUserId());
		try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
//printStackTraccee()();
			throw new SystemException(createException.getErrorCode());
		}
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
	 * @return Returns the stockHolderCode.
	 *//*
	@Column(name="STKHLDCOD")
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	*//**
	 * @param stockHolderCode The stockHolderCode to set.
	 *//*
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}*/

	/**
	 * @return Returns the stockRequestAuditPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="requestRefNumber", column=@Column(name="REQREFNUM")),
		@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))}
	)
	public StockRequestAuditPK getStockRequestAuditPk() {
		return stockRequestAuditPk;
	}
	/**
	 * @param stockRequestAuditPk The stockRequestAuditPk to set.
	 */
	public void setStockRequestAuditPk(StockRequestAuditPK stockRequestAuditPk) {
		this.stockRequestAuditPk = stockRequestAuditPk;
	}
}
