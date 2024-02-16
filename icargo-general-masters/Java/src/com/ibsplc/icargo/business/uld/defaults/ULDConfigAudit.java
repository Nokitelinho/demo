/*
 * ULDConfigAudit.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject tos license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */

@Table(name="ULDCFGAUD")
@Entity
@Staleable
public class ULDConfigAudit {
	private static final String MODULE="uld.defaults";
	private Log log = LogFactory.getLogger("<---ULDConfigAudit--->");
	/**
	 * ULDConfigAuditPK
	 */
	private ULDConfigAuditPK uldConfigAuditPK;
	
	private Calendar updatedUTCTime;
	private String stationCode;
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
	 * @return Returns the uldConfigAuditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode",column=@Column(name="CMPCOD")),
		@AttributeOverride(name="sequenceNumber",column=@Column(name="SEQNUM"))}
	)
	public ULDConfigAuditPK getUldConfigAuditPK() {
		return uldConfigAuditPK;
	}
	/**
	 * @param uldConfigAuditPK The uldConfigAuditPK to set.
	 */
	public void setUldConfigAuditPK(ULDConfigAuditPK uldConfigAuditPK) {
		this.uldConfigAuditPK = uldConfigAuditPK;
	}
	/**
	 * Consteuctor
	 */
	public ULDConfigAudit(){

	}

	/**
	 *
	 * @param uldConfigAuditVO
	 * @throws SystemException
	 */
	public ULDConfigAudit(ULDConfigAuditVO uldConfigAuditVO)throws SystemException{
		log.entering("ULDConfigAudit","ULDConfigAudit");
		ULDConfigAuditPK uLDConfigAuditPk = new ULDConfigAuditPK();
		uLDConfigAuditPk.setCompanyCode(uldConfigAuditVO.getCompanyCode());
		this.setUldConfigAuditPK(uLDConfigAuditPk);
		this.setAdditionalInfo(uldConfigAuditVO.getAdditionalInformation());
    	this.setAuditRemarks(uldConfigAuditVO.getAuditRemarks());
    	this.setLastUpdateTime(uldConfigAuditVO.getTxnLocalTime());
    	this.setLastUpdateUser(uldConfigAuditVO.getUserId());
    	this.setAction(uldConfigAuditVO.getActionCode());
    	this.setStationCode(uldConfigAuditVO.getStationCode());
    	this.setUpdatedUTCTime(uldConfigAuditVO.getTxnTime());
       	try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("ULDConfigAudit","ULDConfigAudit");
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
		this.stationCode = stationCode;
	}
	/**
	 * @return Returns the updatedUTCTime.
	 */
	@Column(name="UPDTXNTIMUTC")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedUTCTime() {
		return this.updatedUTCTime;
	}
	/**
	 * @param updatedUTCTime The updatedUTCTime to set.
	 */
	public void setUpdatedUTCTime(Calendar updatedUTCTime) {
		this.updatedUTCTime = updatedUTCTime;
	}
	
	  /**
     * This method retrieves the repair head details for invoicing.
     * @author A-3045
     * @param companyCode
     * @param mucReferenceNumber
     * @param mucDate
     * @param controlReceiptNumber
     * @return Collection<ULDConfigAuditVO>
     * @throws SystemException
     */
	public static Collection<ULDConfigAuditVO> findMUCAuditDetails(ULDTransactionDetailsVO uldTransactionDetailsVO)
    throws SystemException {   
		 Log log =LogFactory.getLogger("ULD_MANAGEMENT");
		 log.entering("ULDConfigAudit","findMUCAuditDetails");
 	    	return constructDAO().findMUCAuditDetails(uldTransactionDetailsVO);
    }
	/**
     * @author A-3045
     * @return ULDDefaultsDAO
     * @throws SystemException
     */
    private static ULDDefaultsDAO constructDAO() throws SystemException {
        try {
            EntityManager em = PersistenceController.getEntityManager();
            return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
        } catch (PersistenceException persistenceException) {
        	persistenceException.getErrorCode();
            throw new SystemException(persistenceException.getMessage());
        }
}
}
