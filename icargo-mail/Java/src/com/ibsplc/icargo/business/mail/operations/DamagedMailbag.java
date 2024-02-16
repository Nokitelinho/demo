/*
 * DamagedMailbag.java Created on Jun 27, 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991
 */
@Entity
@Table(name = "MALDMGDTL")
@Staleable
public class DamagedMailbag {
	private DamagedMailbagPK damagedMailbagPK;



	private String userCode;

	private Calendar damageDate;

	private String remarks;

	

	private String damageDescription;
	private static final String MAIL_OPERATIONS = "mail.operations";
	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	/*
	 * I - inbound , O -outbound
	 */
	private String operationType;

	private String returnFlag;

	private String paCode;

	/**
	 * @return Returns the damageDate.
	 */
	@Column(name = "DMGDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDamageDate() {
		return damageDate;
	}

	/**
	 * @param damageDate
	 *            The damageDate to set.
	 */
	public void setDamageDate(Calendar damageDate) {
		this.damageDate = damageDate;
	}



	/**
	 * @return Returns the damagedMailbagPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "damageCode", column = @Column(name = "DMGCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")) })
	public DamagedMailbagPK getDamagedMailbagPK() {
		return damagedMailbagPK;
	}

	/**
	 * @param damagedMailbagPK
	 *            The damagedMailbagPK to set.
	 */
	public void setDamagedMailbagPK(DamagedMailbagPK damagedMailbagPK) {
		this.damagedMailbagPK = damagedMailbagPK;
	}

	/**
	 * @return the damageDescription
	 */
	@Column(name = "DMGDES")
	public String getDamageDescription() {
		return damageDescription;
	}
	/**
	 * @param damageDescription the damageDescription to set
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}
	/**
	 * @return Returns the operationType.
	 */
	@Column(name = "OPRTYP")
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            The operationType to set.
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the userCode.
	 */
	@Column(name = "LSTUPDUSR")
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            The userCode to set.
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * A-1739
	 * 
	 * @param damagedMailbagVO
	 */
	public void update(DamagedMailbagVO damagedMailbagVO) {
		populateAttributes(damagedMailbagVO);
	}

	/**
	 * @return Returns the paCode.
	 */
	@Column(name = "POACOD")
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the returnFlag.
	 */
	@Column(name = "RTNFLG")
	public String getReturnFlag() {
		return returnFlag;
	}

	/**
	 * @param returnFlag
	 *            The returnFlag to set.
	 */
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	//Added by A-7929 as part of IASCB-35577 starts
	private Calendar lastUpdateTime;
	
	 @Version
	 @Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	

	//Added by A-7929 as part of IASCB-35577 ends


	public DamagedMailbag() {

	}

	/**
	 * @param mailbagPK
	 * @param damagedMailbagVO
	 * @throws SystemException
	 */
	public DamagedMailbag(MailbagPK mailbagPK, DamagedMailbagVO damagedMailbagVO)
			throws SystemException {

		populatePK(mailbagPK, damagedMailbagVO);
		populateAttributes(damagedMailbagVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 * A-1739
	 * 
	 * @param mailbagPK
	 * @param damagedMailbagVO
	 */
	private void populatePK(MailbagPK mailbagPK,
			DamagedMailbagVO damagedMailbagVO) {
		damagedMailbagPK = new DamagedMailbagPK();
		damagedMailbagPK.setCompanyCode(mailbagPK.getCompanyCode());
		damagedMailbagPK.setAirportCode(damagedMailbagVO.getAirportCode());
		damagedMailbagPK.setDamageCode(damagedMailbagVO.getDamageCode());
		damagedMailbagPK.setMailSequenceNumber(mailbagPK
				.getMailSequenceNumber());
	}

	/**
	 * A-1739
	 * 
	 * @param damagedMailbagVO
	 */
	private void populateAttributes(DamagedMailbagVO damagedMailbagVO) {
		setDamageDate(damagedMailbagVO.getDamageDate().toCalendar());
		setOperationType(damagedMailbagVO.getOperationType());
		setRemarks(damagedMailbagVO.getRemarks());
		setDamageDescription(damagedMailbagVO.getDamageDescription());	 
		LogonAttributes logonAttributes=null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE,"SystemException at logonAttributes-ContextUtils.getSecurityContext()");
		} 
		if(damagedMailbagVO.getUserCode()!=null){
		setUserCode(damagedMailbagVO.getUserCode());
		}else{
		setUserCode(logonAttributes.getUserId());
		}
		if (MailConstantsVO.FLAG_YES.equals(damagedMailbagVO.getReturnedFlag())) {
			setReturnFlag(MailConstantsVO.FLAG_YES);
		}
		else{
			setReturnFlag(MailConstantsVO.FLAG_NO);
		}
		setPaCode(damagedMailbagVO.getPaCode());
		setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true)); //Added by A-7929 as part of IASCB-35577
	    
	}

	/**
	 * A-5991
	 * 
	 * @param mailbagPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static DamagedMailbag find(DamagedMailbagPK mailbagPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				DamagedMailbag.class, mailbagPK);
	}

	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}

	/**
	 * @author A-5991
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	/**
	 * TODO Purpose
	 * Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @param airportCode TODO
	 * @return
	 * @throws SystemException 
	 */
	public static String findDamageReason(String companyCode, String receptacleID, 
			String airportCode) throws SystemException {
		try {
			return constructDAO().findDamageReason(
					companyCode, receptacleID, airportCode);
		} catch(PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

}
