/*
 * MRAAirlineBillingAudit.java Created on July 13, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 * 
 */
@Table(name = "MALMRAARLAUD")
@Entity
public class MRAAirlineBillingAudit {
	/**
	 * module name
	 */
	public static final String MODULE = "mail.mra.airlinebilling";
	
	private MRAAirlineBillingAuditPK mRAAirlineBillingAuditPK;

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

	/**
	 * Updated Time in UTC
	 */
	private Calendar updatedUTCTime;

	private String action;
	/*
	 * Commented part of DB recommendations
	 */
	//private String airlineCode;

	private String clearancePeriod;
	
	private String stationCode ;
	
	private String auditRemark;

	private Log log = LogFactory.getLogger("MailTracking_Mra_AirlineBilling");

	/**
	 * The Default Constructor
	 * 
	 */
	public MRAAirlineBillingAudit() {

	}

	/**
	 * The constructor for Audit Entity
	 * 
	 * @param airlineBillingAuditVO
	 * @throws SystemException
	 */
	public MRAAirlineBillingAudit(AirlineBillingAuditVO airlineBillingAuditVO)
			throws SystemException {
		log.log(Log.FINE, "The assignedFlightAuditVO is ",
				airlineBillingAuditVO);
		MRAAirlineBillingAuditPK auditPK = new MRAAirlineBillingAuditPK();
		auditPK.setCompanyCode(airlineBillingAuditVO.getCompanyCode());
		auditPK.setAirlineIdentifier(airlineBillingAuditVO
				.getAirlineIdentifier());
		auditPK.setSerialNumber(airlineBillingAuditVO.getSerialNumber());
		this.setMRAAirlineBillingAuditPK(auditPK);
		this.setAction(airlineBillingAuditVO.getActionCode());
		this
				.setAdditionalInfo(airlineBillingAuditVO
						.getAdditionalInformation());
		this.setLastUpdateUser(airlineBillingAuditVO.getUserId());
		this.setLastUpdateTime(airlineBillingAuditVO.getTxnLocalTime());
		this.setUpdatedUTCTime(airlineBillingAuditVO.getTxnTime());
		//this.setAirlineCode(airlineBillingAuditVO.getAirlineCode());
		this.setClearancePeriod(airlineBillingAuditVO.getClearancePeriod());
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}

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
	 * @return Returns the assignedFlightAuditPK.
	 */
	/*@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MRAAirlineBillingAuditPK getmRAAirlineBillingAuditPK() {
		return mraAirlineBillingAuditPK;
	}

	/**
	 * 
	 * @param mraAirlineBillingAuditPK
	 */
	/*
	public void setAirlineBillingAuditPK(
			MRAAirlineBillingAuditPK mraAirlineBillingAuditPK) {
		this.mraAirlineBillingAuditPK = mraAirlineBillingAuditPK;
	}
	*/
	

	/**
	 * @return the mRAAirlineBillingAuditPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public MRAAirlineBillingAuditPK getMRAAirlineBillingAuditPK() {
		return mRAAirlineBillingAuditPK;
	}

	/**
	 * @param airlineBillingAuditPK the mRAAirlineBillingAuditPK to set
	 */
	public void setMRAAirlineBillingAuditPK(
			MRAAirlineBillingAuditPK airlineBillingAuditPK) {
		mRAAirlineBillingAuditPK = airlineBillingAuditPK;
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
	 * @return Returns the updatedUTCTime.
	 */
	@Column(name = "UPDTXNTIMUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedUTCTime() {
		return updatedUTCTime;
	}

	/**
	 * @param updatedUTCTime
	 *            The updatedUTCTime to set.
	 */
	public void setUpdatedUTCTime(Calendar updatedUTCTime) {
		this.updatedUTCTime = updatedUTCTime;
	}

	/**
	 * @return Returns the stationCode.
	 */
	/*@Column(name = "ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}*/

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	/*public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}*/

	/**
	 * @return Returns the stationCode.
	 */
	@Column(name = "CLRPRD")
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}
	/**
	 * @author a-2391 This method is used to list the AuditDetailsVO.
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 */
	public static Collection<AuditDetailsVO> findArlAuditDetails(MRAArlAuditFilterVO mailAuditFilterVO)
			throws SystemException {
		try {
			return constructDAO().findArlAuditDetails(mailAuditFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author a-2391 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MRAAirlineBillingDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MRAAirlineBillingDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
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

	/**
	 * @return the stationCode
	 */
	@Column(name = "STNCOD")
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
}
