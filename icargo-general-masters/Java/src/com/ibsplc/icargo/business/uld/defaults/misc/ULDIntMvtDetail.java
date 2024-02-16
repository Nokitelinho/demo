/*
 * ULDIntMvtDetail.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2412
 * 
 */

@Table(name = "ULDINTMVTDTL")
@Entity
public class ULDIntMvtDetail {

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	
	private ULDIntMvtDetailPK uldIntMvtDetailPK;
	
	private String agentCode;
	
	private String agentName;
	
	private String content;
	
	private String airport;
	
	private String fromLocation;
	
	private String toLocation;
	
	private String mvtType;
	
	private Calendar mvtDate;
	
	private String remark;
	
	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;

	private String returnStatus;
	
	/**
	 * @return the returnStatus
	 */
	@Column(name = "RTNSTA")
	public String getReturnStatus() {
		return returnStatus;
	}

	/**
	 * @param returnStatus the returnStatus to set
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public ULDIntMvtDetail() {

	}

	public ULDIntMvtDetail(ULDIntMvtDetailVO uldIntMvtDetailVO)
			throws SystemException {
		log.entering("INSIDE THE ULDMOVEMENTDETAIL",
				"INSIDE THE ULDMOVEMENTDETAIL");
		populatePk(uldIntMvtDetailVO);
		populateAttributes(uldIntMvtDetailVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	private void populatePk(ULDIntMvtDetailVO uldIntMvtDetailVO) {
		ULDIntMvtDetailPK pk = new ULDIntMvtDetailPK();
		pk.setCompanyCode(uldIntMvtDetailVO.getCompanyCode());
		pk.setIntSequenceNumber(Objects.nonNull(uldIntMvtDetailVO.getIntSequenceNumber())
				? Integer.parseInt(uldIntMvtDetailVO.getIntSequenceNumber()) : 0);
		pk.setUldNumber(uldIntMvtDetailVO.getUldNumber());
		this.setUldIntMvtDetailPK(pk);

	}

	private void populateAttributes(ULDIntMvtDetailVO uldIntMvtDetailVO)
			throws SystemException {
		this.setAgentCode(uldIntMvtDetailVO.getAgentCode());
		this.setAgentName(uldIntMvtDetailVO.getAgentName());
		this.setContent(uldIntMvtDetailVO.getContent());
		this.setAirport(uldIntMvtDetailVO.getAirport());
		this.setFromLocation(uldIntMvtDetailVO.getFromLocation());
		this.setToLocation(uldIntMvtDetailVO.getToLocation());
		this.setMvtType(uldIntMvtDetailVO.getMvtType());
		this.setMvtDate(uldIntMvtDetailVO.getMvtDate());
		this.setRemark(uldIntMvtDetailVO.getRemark());	
		this.setReturnStatus(uldIntMvtDetailVO.getReturnStatus());
	}

	public static ULDIntMvtDetail find(String companyCode,String uldNumber,
			String intSequenceNumber,long intSerialNumber) throws SystemException {
		ULDIntMvtDetail uldInternalMovementDtl = null;
		ULDIntMvtDetailPK movementPK = new ULDIntMvtDetailPK();
		movementPK.setCompanyCode(companyCode);
		movementPK.setIntSequenceNumber(Objects.nonNull(intSequenceNumber) ? Integer.parseInt(intSequenceNumber) : 0);
		movementPK.setUldNumber(uldNumber);
		movementPK.setIntSerialNumber(intSerialNumber);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			uldInternalMovementDtl = em.find(ULDIntMvtDetail.class, movementPK);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}

		return uldInternalMovementDtl;
	}
	
	/**
	 * @return the uldIntMvtDetailPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "intSequenceNumber", column = @Column(name = "INTSEQNUM")),			
			@AttributeOverride(name = "intSerialNumber", column = @Column(name = "INTSERNUM")) })
	public ULDIntMvtDetailPK getUldIntMvtDetailPK() {
		return uldIntMvtDetailPK;
	}

	/**
	 * @param uldIntMvtDetailPK the uldIntMvtDetailPK to set
	 */
	
	public void setUldIntMvtDetailPK(ULDIntMvtDetailPK uldIntMvtDetailPK) {
		this.uldIntMvtDetailPK = uldIntMvtDetailPK;
	}

	/**
	 * @return the agentCode
	 */
	@Column(name = "AGTCOD")
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the agentName
	 */
	@Column(name = "AGTNAM")
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the airport
	 */
	@Column(name = "ARPCOD")
	public String getAirport() {
		return airport;
	}

	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * @return the content
	 */
	@Column(name = "CNT")
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the fromLocation
	 */
	@Column(name = "FRMLOC")
	public String getFromLocation() {
		return fromLocation;
	}

	/**
	 * @param fromLocation the fromLocation to set
	 */
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	/**
	 * @return the mvtDate
	 */
	@Column(name = "MVTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getMvtDate() {
		return mvtDate;
	}

	/**
	 * @param mvtDate the mvtDate to set
	 */
	public void setMvtDate(Calendar mvtDate) {
		this.mvtDate = mvtDate;
	}

	/**
	 * @return the mvtType
	 */
	@Column(name = "MVTTYP")
	public String getMvtType() {
		return mvtType;
	}

	/**
	 * @param mvtType the mvtType to set
	 */
	public void setMvtType(String mvtType) {
		this.mvtType = mvtType;
	}

	/**
	 * @return the remark
	 */
	@Column(name = "RMK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the toLocation
	 */
	@Column(name = "TOOLOC")
	public String getToLocation() {
		return toLocation;
	}

	/**
	 * @param toLocation the toLocation to set
	 */
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

}
