/*
 * DSNAudit.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

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

import com.ibsplc.icargo.business.mail.operations.vo.DSNAuditVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 * @author a-5991
 * 
 */
@Entity
@Table(name = "MALDSNAUD")
public class DSNAudit {
	
	private static final String MODULE = "mail.operations";
	
	private DSNAuditPK dsnAuditPK;

	/**
	 * Additional info
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

	private String action;
	
	/**
	 * Updated Time in UTC 
	 */
	private Calendar updatedUTCTime;
	
	private String stationCode ;
	
	//private String mailClass;
	
	private String auditRemark;


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

	/**
	 * @return Returns the action.
	 */
	@Column(name = "ACTCOD")
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return Returns the additionalInfo.
	 */
	@Column(name = "ADLINF")
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * @param additionalInfo
	 *            The additionalInfo to set.
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return Returns the dsnAuditPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "destinationExchangeOffice", column = @Column(name = "DSTEXGOFC")),
			@AttributeOverride(name = "dsn", column = @Column(name = "DSN")),
			@AttributeOverride(name = "mailCategoryCode", column = @Column(name = "MALCTGCOD")),
			@AttributeOverride(name = "mailSubclass", column = @Column(name = "MALSUBCLS")),
			@AttributeOverride(name = "originExchangeOffice", column = @Column(name = "ORGEXGOFC")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "year", column = @Column(name = "YER")) })
	public DSNAuditPK getDsnAuditPk() {
		return dsnAuditPK;
	}

	/**
	 * @param dsnAuditPk
	 *            The dsnAuditPk to set.
	 */
	public void setDsnAuditPk(DSNAuditPK dsnAuditPk) {
		this.dsnAuditPK = dsnAuditPk;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "UPDTXNTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "UPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
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

	/**
	 * @return Returns the mailClass.
	 */
/*	@Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}*/

	/**
	 * @param mailClass The mailClass to set.
	 */
/*	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}*/
	
	
	public DSNAudit() {

	}

	public DSNAudit(DSNAuditVO dsnAuditVO) throws SystemException {
		populatePK(dsnAuditVO);
		populateAttributes(dsnAuditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
	}

	/**
	 * A-5991
	 * 
	 * @param dsnAuditVO
	 */
	private void populatePK(DSNAuditVO dsnAuditVO) {
		dsnAuditPK = new DSNAuditPK();
		dsnAuditPK.setCompanyCode(   dsnAuditVO.getCompanyCode());
		dsnAuditPK.setDsn( dsnAuditVO.getDsn());
		dsnAuditPK.setOriginExchangeOffice(   dsnAuditVO.getOriginExchangeOffice());
		dsnAuditPK.setDestinationExchangeOffice(   dsnAuditVO
				.getDestinationExchangeOffice());
		dsnAuditPK.setMailSubclass(   dsnAuditVO.getMailSubclass());
		dsnAuditPK.setMailCategoryCode(   
			dsnAuditVO.getMailCategoryCode());
		dsnAuditPK.setYear(   dsnAuditVO.getYear());
	}

	/**
	 * A-5991
	 * 
	 * @param dsnAuditVO
	 */
	private void populateAttributes(DSNAuditVO dsnAuditVO) {
		setAction(dsnAuditVO.getActionCode());
		setAdditionalInfo(dsnAuditVO.getAdditionalInformation());
		setLastUpdateUser(dsnAuditVO.getLastUpdateUser());
		//setLastUpdateTime(dsnAuditVO.getTxnLocalTime());
		setStationCode(dsnAuditVO.getStationCode());
		setUpdatedUTCTime(dsnAuditVO.getTxnTime());
		//setMailClass(dsnAuditVO.getMailClass());
		setAuditRemark (dsnAuditVO.getAuditRemarks());
	}
	
	

	/**
	 * @author a-5991 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}


}
