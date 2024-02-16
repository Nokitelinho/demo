/*
 * ReservationAudit.java Created on Mar 3, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation;

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

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1954
 * 
 */
@Table(name = "STKRESAWBAUD")
@Entity
public class ReservationAudit {

	private Log log = LogFactory.getLogger("--RESERVATION AUDIT--");

	private ReservationAuditPK reservationAuditPK;

	private String shipmentPrefix;

	private String actionCode;

	private String additionalInfo;

	private String auditRemark;

	private String lastUpdatedUser;

	private Calendar lastUpdatedTime;

	/**
	 * @return Returns the actionCode.
	 */
	@Column(name = "ACTCOD")
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode
	 *            The actionCode to set.
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
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
	 * @return Returns the auditRemark.
	 */
	@Column(name = "AUDRMK")
	public String getAuditRemark() {
		return auditRemark;
	}

	/**
	 * @param auditRemark
	 *            The auditRemark to set.
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the reservationAuditPK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "documentNumber", column = @Column(name = "DOCNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public ReservationAuditPK getReservationAuditPK() {
		return reservationAuditPK;
	}

	/**
	 * @param reservationAuditPK
	 *            The reservationAuditPK to set.
	 */
	public void setReservationAuditPK(ReservationAuditPK reservationAuditPK) {
		this.reservationAuditPK = reservationAuditPK;
	}

	/**
	 * @return Returns the shipmentPrefix.
	 */
	@Column(name = "SHPPFX")
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix
	 *            The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	
	/**
	 * 
	 */
	public ReservationAudit(){
		
	}

	/**
	 * @param reservationAuditVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public ReservationAudit(ReservationAuditVO reservationAuditVO) 
			throws SystemException, CreateException {
		log.log(Log.FINER,"ENTRY----inside the audit of reservation");
		ReservationAuditPK auditPk = new ReservationAuditPK();
		auditPk.setCompanyCode(reservationAuditVO.getCompanyCode());
		auditPk.setAirportCode(reservationAuditVO.getAirportCode());
		auditPk.setAirlineIdentifier(reservationAuditVO.getAirlineIdentifier());
		auditPk.setDocumentNumber(reservationAuditVO.getDocumentNumber());
		this.setReservationAuditPK(auditPk);
		this.setActionCode(reservationAuditVO.getActionCode());
		this.setAdditionalInfo(reservationAuditVO.getAdditionalInformation());
		this.setAuditRemark(reservationAuditVO.getAuditRemarks());
		this.setLastUpdatedUser(reservationAuditVO.getLastUpdateUser());
		this.setShipmentPrefix(reservationAuditVO.getShipmentPrefix());
		
		PersistenceController.getEntityManager().persist(this);

		log.log(Log.FINER,"RETURN----outside the audit of reservation");
	}

}
