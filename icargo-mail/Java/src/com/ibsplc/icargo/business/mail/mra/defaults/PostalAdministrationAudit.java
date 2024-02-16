/*
 * PostalAdministrationAudit.java Created on Jul 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.PostalAdministrationAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2270
 * 
 */

@Table(name = "MTKPOAAUD")
@Entity
public class PostalAdministrationAudit {
	
	private PostalAdministrationAuditPK postalAdministrationAuditPK;
	
	/**
	 * billingPeriodFrom
	 */
	
	/**
	 * poaName
	 */
	//private String poaName;
	/**
	 * actionCode
	 */
	private String actionCode;
	/**
	 * additionalInfo
	 */
	private String additionalInfo;
	/**
	 * Last update user code
	 */
	private String lastUpdateUser;

	/**
	 * Last update date and time
	 */
	private Calendar lastUpdateTime;
	/**
	 * Updated Time in UTC 
	 */
	private Calendar updatedUTCTime; 
	
	private String auditRemark;
	
	private Log log =LogFactory.getLogger("MailTracking_Defaults");
	
	 /**
     * The Default Constructor 
     *
     */
	public PostalAdministrationAudit(){
		
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
	 * @return Returns the postalAdministrationAuditPK.
	 */
	 @EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public PostalAdministrationAuditPK getPostalAdministrationAuditPK() {
		return postalAdministrationAuditPK;
	}

	/**
	 * @param postalAdministrationAuditPK The postalAdministrationAuditPK to set.
	 */
	public void setPostalAdministrationAuditPK(
			PostalAdministrationAuditPK postalAdministrationAuditPK) {
		this.postalAdministrationAuditPK = postalAdministrationAuditPK;
	}

	/**
	 * @return Returns the updatedUTCTime.
	 */
	 @Version
	 @Column(name="UPDTXNTIMUTC")
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
	
	
	/**
	 * @param postalAdministrationAuditVO
	 * @throws SystemException
	 */
	public PostalAdministrationAudit(PostalAdministrationAuditVO  postalAdministrationAuditVO)
			throws SystemException {
		log.log(Log.FINE, "The postalAdministrationAuditVO is ",
				postalAdministrationAuditVO);
		PostalAdministrationAuditPK auditPK = new PostalAdministrationAuditPK();
		auditPK.setCompanyCode(postalAdministrationAuditVO.getCompanyCode());
		auditPK.setPoaCode(postalAdministrationAuditVO.getPoaCode());
 		this.setPostalAdministrationAuditPK(auditPK);
 		
		this.setActionCode(postalAdministrationAuditVO.getActionCode());
		this.setAdditionalInfo(postalAdministrationAuditVO.getAdditionalInformation());
		this.setLastUpdateUser(postalAdministrationAuditVO.getUserId());
		this.setLastUpdateTime(postalAdministrationAuditVO.getTxnLocalTime());
		//this.setBillingPeriodFrom(postalAdministrationAuditVO.getBillingPeriodFrom());
		//this.setBillingPeriodTo(postalAdministrationAuditVO.getBillingPeriodTo());
		//this.poaName(postalAdministrationAuditVO.getPoaCode());
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}

	}

	/**
	 * @return the auditRemark
	 */
	@Column(name = "AUDRMK")
	public String getAuditRemark() {
		return auditRemark;
	}

	/**
	 * @param auditRemark the auditRemark to set
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

}
