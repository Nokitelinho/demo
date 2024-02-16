/*
 * BillingMatrixAudit.java Created on Aug 4, 2015
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5255 
 * @version	0.1, Aug 4, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Aug 4, 2015	     A-5255		First draft
 */
@Staleable
@Table(name = "MALMRABLGMTXAUD")
@Entity
public class BillingMatrixAudit {
	private Log log = LogFactory.getLogger("BillingMatrixAudit");
	private static final String MODULE = "mail.mra";
	
	private String additionalInformation; 
	private String auditRemarks; 
	private String actionCode; 
	private String updatedUser; 
	private Calendar updatedTransactionTime; 
	private Calendar updatedTransactionTimeUTC; 
	private String stationCode;
	private BillingMatrixAuditPK billingMatrixAuditPK; 
	
	

	
	public BillingMatrixAudit(){
		log.entering("BillingMatrixAudit", "BillingMatrixAudit");
	}
	/**
	 * 
	 * @param billingMatrixAuditVO
	 * @throws SystemException
	 */
	public BillingMatrixAudit(BillingMatrixAuditVO billingMatrixAuditVO) throws SystemException{
		populatePK(billingMatrixAuditVO);
		populateAttributes(billingMatrixAuditVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
		
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingMatrixAuditVO
	 */
		
	private void populatePK(BillingMatrixAuditVO billingMatrixAuditVO ) {
		log.entering("Inside populatePK()", "populatePK");
		BillingMatrixAuditPK billingMatrixAuditPK = new BillingMatrixAuditPK();
		billingMatrixAuditPK.setCompanyCode(billingMatrixAuditVO.getCompanyCode());
		billingMatrixAuditPK.setBillingMatrixCode(billingMatrixAuditVO.getBillingmatrixID());
		this.setBillingMatrixAuditPK(billingMatrixAuditPK);
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingMatrixAuditVO
	 */
	private void populateAttributes(BillingMatrixAuditVO billingMatrixAuditVO ){
		log.entering("populateAttributes()", "Inside populateAttributes()");
		this.setAdditionalInformation(billingMatrixAuditVO.getAdditionalInformation());
		this.setAuditRemarks(billingMatrixAuditVO.getAuditRemarks());
		this.setActionCode(billingMatrixAuditVO.getActionCode());
		if(billingMatrixAuditVO.getSysFlag()!=null && billingMatrixAuditVO.getSysFlag().equals("SYS"))
		{
			this.setUpdatedUser("SYSTEM");
		}
		else{
		this.setUpdatedUser(billingMatrixAuditVO.getUserId());
		}
		this.setUpdatedTransactionTime(billingMatrixAuditVO.getTxnTime());
		this.setUpdatedTransactionTimeUTC(billingMatrixAuditVO.getTxnLocalTime());
		this.setStationCode(billingMatrixAuditVO.getStationCode());
		
	}
	
	/**
	 * @return the additionalInformation
	 */
	@Column(name = "ADLINF")
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	/**
	 * @return the auditRemarks
	 */
	@Column(name = "AUDRMK")
	public String getAuditRemarks() {
		return auditRemarks;
	}
	/**
	 * @param auditRemarks the auditRemarks to set
	 */
	public void setAuditRemarks(String auditRemarks) {
		this.auditRemarks = auditRemarks;
	}
	/**
	 * @return the actionCode
	 */
	@Column(name = "ACTCOD")
	public String getActionCode() {
		return actionCode;
	}
	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	/**
	 * @return the updatedUser
	 */
	@Column(name = "UPDUSR")
	public String getUpdatedUser() {
		return updatedUser;
	}
	/**
	 * @param updatedUser the updatedUser to set
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	/**
	 * @return the updatedTransactionTime
	 */
	@Column(name = "UPDTXNTIM")
	public Calendar getUpdatedTransactionTime() {
		return updatedTransactionTime;
	}
	/**
	 * @param updatedTransactionTime the updatedTransactionTime to set
	 */
	public void setUpdatedTransactionTime(Calendar updatedTransactionTime) {
		this.updatedTransactionTime = updatedTransactionTime;
	}
	/**
	 * @return the updatedTransactionTimeUTC
	 */
	@Column(name = "UPDTXNTIMUTC")
	public Calendar getUpdatedTransactionTimeUTC() {
		return updatedTransactionTimeUTC;
	}
	/**
	 * @param updatedTransactionTimeUTC the updatedTransactionTimeUTC to set
	 */
	public void setUpdatedTransactionTimeUTC(Calendar updatedTransactionTimeUTC) {
		this.updatedTransactionTimeUTC = updatedTransactionTimeUTC;
	}
	/**
	 * @return the billingMatrixAuditPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "billingMatrixCode", column = @Column(name = "BLGMTXCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
	public BillingMatrixAuditPK getBillingMatrixAuditPK() {
		return billingMatrixAuditPK;
	}
	/**
	 * @param billingMatrixAuditPK the billingMatrixAuditPK to set
	 */
	public void setBillingMatrixAuditPK(BillingMatrixAuditPK billingMatrixAuditPK) {
		this.billingMatrixAuditPK = billingMatrixAuditPK;
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
