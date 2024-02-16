/*
 * ULDAudit.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject tos license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDAuditVO;

import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1950
 *
 */

@Table(name="ULDMSTAUD")
@Entity
@Staleable
public class ULDAudit {
	private Log log = LogFactory.getLogger("<---ULDAudit--->");
	/**
	 * ULDAuditPK
	 */
	private ULDAuditPK uldAuditPK;
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
	
	private String stationCode;
	
	private Calendar lastUpdatedUTCTime;
	/**
	 * Action [CREATE,UPDATE,DELETE,MULTIPLE CREATE]
	 */
	private String action;
	/**
	 * @return Returns the action.
	 */
	@Column(name="ACTCOD")
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
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
	 * @return Returns the lastUpdatedUTCTime.
	 */
	@Column(name="UPDTXNTIMUTC")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedUTCTime() {
		return this.lastUpdatedUTCTime;
	}
	/**
	 * @param lastUpdatedUTCTime The lastUpdatedUTCTime to set.
	 */
	public void setLastUpdatedUTCTime(Calendar lastUpdatedUTCTime) {
		this.lastUpdatedUTCTime = lastUpdatedUTCTime;
	}
	/**
	 * @return Returns the stationCode.
	 */
	@Column(name="STNCOD")
	public String getStationCode() {
		return this.stationCode;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode =  stationCode;
	}
	/**
	 * @return Returns the uldAuditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode",column=@Column(name="CMPCOD")),
		@AttributeOverride(name="uldNumber",column=@Column(name="ULDNUM")),
		@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))}
	)
	public ULDAuditPK getUldAuditPK() {
		return uldAuditPK;
	}
	/**
	 * @param uldAuditPK The uldAuditPK to set.
	 */
	public void setUldAuditPK(ULDAuditPK uldAuditPK) {
		this.uldAuditPK = uldAuditPK;
	}
	/**
	 * Consteuctor
	 */
	public ULDAudit(){
		
	}
	
	/**
	 * 
	 * @param uldAuditVO
	 * @throws SystemException
	 */
	public ULDAudit(ULDAuditVO uldAuditVO)throws SystemException{
		log.entering("ULDAudit","ULDAudit");
		ULDAuditPK uLDAuditPk = new ULDAuditPK();
		uLDAuditPk.setCompanyCode(uldAuditVO.getCompanyCode());
		uLDAuditPk.setUldNumber(uldAuditVO.getUldNumber());
		this.setUldAuditPK(uLDAuditPk);
		this.setAdditionalInfo(uldAuditVO.getAdditionalInformation());
    	this.setAuditRemarks(uldAuditVO.getAuditRemarks());
    	this.setLastUpdateTime(uldAuditVO.getTxnLocalTime());
    	this.setLastUpdateUser(uldAuditVO.getUserId());
    	this.setAction(uldAuditVO.getActionCode());
    	this.setStationCode(uldAuditVO.getStationCode());
    	this.setLastUpdatedUTCTime(uldAuditVO.getTxnTime());
       	try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("ULDAudit","ULDAudit");
	}
	 public Page<AuditDetailsVO> findULDActionHistory (ULDMovementFilterVO uldmovementFilterVO)
	    throws SystemException{
	    	EntityManager em = PersistenceController.getEntityManager();
	    	try{
	    		ULDDefaultsDAO uldDefaultsDAO = 
	    			ULDDefaultsDAO.class.cast(
	    					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
	    		return uldDefaultsDAO.findULDActionHistory(uldmovementFilterVO);
	    		}catch(PersistenceException persistenceException){
	    		throw new SystemException(persistenceException.getErrorCode()); 
	    	}
	    }
}
