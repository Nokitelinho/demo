/*
 * GPABillingSettlementDetailAudit.java Created on Jun 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.MailbagSettlementAuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7871 
 * @version	0.1, Jun 05, 2018
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 05, 2018	     A-7871		First draft
 */
@Staleable
@Table(name = "MALMRAGPAINVDTLAUD")
@Entity
public class GPABillingSettlementDetailAudit {
	private Log log = LogFactory.getLogger("GPABillingSettlementDetailAudit");
	private static final String MODULE = "mail.mra";
	
	
	
	private String additionalInformation; 
	private String auditRemarks; 
	private String actionCode; 
	private String updatedUser; 
	private Calendar updatedTransactionTime; 
	private Calendar updatedTransactionTimeUTC; 
	private String stationCode;
	private String mailbagId;
	/**
	 * @return the mailbagId
	 */
	@Column(name = "MALIDR")
	public String getMailbagId() {
		return mailbagId;
	}
	/**
	 * @param mailbagId the mailbagId to set
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	private GPABillingSettlementDetailAuditPK gpaBillingSettlementDetailAuditPK;
	public GPABillingSettlementDetailAudit() {
		log.entering("GPABillingSettlementDetailAudit", "GPABillingSettlementDetailAudit");
		// TODO Auto-generated constructor stub
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
	/**
	 * @return the gpaBillingSettlementDetailAuditPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "malSeqnum", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM"))})
	public GPABillingSettlementDetailAuditPK getGpaBillingSettlementDetailAuditPK() {
		return gpaBillingSettlementDetailAuditPK;
	}
	/**
	 * @param gpaBillingSettlementDetailAuditPK the gpaBillingSettlementDetailAuditPK to set
	 */
	public void setGpaBillingSettlementDetailAuditPK(
			GPABillingSettlementDetailAuditPK gpaBillingSettlementDetailAuditPK) {
		this.gpaBillingSettlementDetailAuditPK = gpaBillingSettlementDetailAuditPK;
	}
	
	/**
	 * 
	 * @param mailbagSettlementAuditVO
	 * @throws SystemException
	 */
	public GPABillingSettlementDetailAudit(MailbagSettlementAuditVO mailbagSettlementAuditVO) throws SystemException{
		populatePK(mailbagSettlementAuditVO);
		populateAttributes(mailbagSettlementAuditVO);
		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
		
	}
	
	
	/**
	 * 
	 * @author A-7871
	 * @param mailbagSettlementAuditVO
	 */
		
	private void populatePK(MailbagSettlementAuditVO mailbagSettlementAuditVO ) {
		log.entering("Inside populatePK()", "populatePK");
		GPABillingSettlementDetailAuditPK gpaBillingSettlementDetailAuditPK = new GPABillingSettlementDetailAuditPK();
		gpaBillingSettlementDetailAuditPK.setCompanyCode(mailbagSettlementAuditVO.getCompanyCode());
		gpaBillingSettlementDetailAuditPK.setInvoiceNumber(mailbagSettlementAuditVO.getInvoiceNumber());
		gpaBillingSettlementDetailAuditPK.setMalSeqnum(mailbagSettlementAuditVO.getMalSeqnum());
		gpaBillingSettlementDetailAuditPK.setSerialNumber(mailbagSettlementAuditVO.getSerialNumber());
		this.setGpaBillingSettlementDetailAuditPK(gpaBillingSettlementDetailAuditPK);
	}
	/**
	 * 
	 * @author A-7871
	 * @param mailbagSettlementAuditVO
	 */
	private void populateAttributes(MailbagSettlementAuditVO mailbagSettlementAuditVO ){
		log.entering("populateAttributes()", "Inside populateAttributes()");
		this.setAdditionalInformation(mailbagSettlementAuditVO.getAdditionalInformation());
		this.setAuditRemarks(mailbagSettlementAuditVO.getAuditRemarks());
		this.setActionCode(mailbagSettlementAuditVO.getActionCode());
		this.setUpdatedUser(mailbagSettlementAuditVO.getUserId());
		this.setUpdatedTransactionTime(mailbagSettlementAuditVO.getTxnTime());
		this.setUpdatedTransactionTimeUTC(mailbagSettlementAuditVO.getTxnLocalTime());
		this.setMailbagId(mailbagSettlementAuditVO.getMailbagID());
		this.setStationCode(mailbagSettlementAuditVO.getStationCode());
		
	}
	
}
