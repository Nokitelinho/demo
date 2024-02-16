/*
 * GPARebillRemarkDetails.java Created on Jan 17, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;



import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.util.Calendar;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.RebillRemarksDetailVO;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
//import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5526
 *
 */
 
@Table(name = "MALMRAGPARMKDTL")
@Entity
@Staleable
public class GPARebillRemarkDetails {

	public static final String ENTITY_NAME = "mail.mra.gpabilling.GPARebillRemarkDetails";

	private static final String CLASS_NAME = "GPARebillRemarkDetails";

	private Log log = localLogger();

	private static Log localLogger() {
		return LogFactory.getLogger("MRA REBILLREMARK");
	}

	
	private GPARebillRemarkDetailsPK gPARebillRemarkDetailsPk;

	private String rebillInvoiceNumber;
	
	private String rebillRemark;

	private String rebillStatus; 
	
	 private Calendar lastUpdateTime;
	 private String lastUpdateUser;
	 @Version
	 @Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public GPARebillRemarkDetails() {

	}
    /**
     * 
     * @param rebillRemarksDetailVO
     * @throws SystemException
     */
	public GPARebillRemarkDetails(RebillRemarksDetailVO rebillRemarksDetailVO )
			throws SystemException {
		localLogger().entering(CLASS_NAME, CLASS_NAME);

		try {
			populatePK(rebillRemarksDetailVO);
			populateAttributes(rebillRemarksDetailVO);
			localLogger().log(Log.SEVERE, "Persisting <:BFR:>");
			PersistenceController.getEntityManager().persist(this);
			localLogger().log(Log.SEVERE, "Persisting <:AFR:>");

		} catch (CreateException createException) {
			localLogger().log(Log.SEVERE,
					"CreateException Thrown ::>> SystemException");
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		localLogger().exiting(CLASS_NAME, CLASS_NAME);
	}
	

    /**
     * 
     * @param rebillRemarksDetailVO
     */
	public void populatePK(RebillRemarksDetailVO rebillRemarksDetailVO ) {
		log.entering(CLASS_NAME, "populatePk");
		GPARebillRemarkDetailsPK gPARebillRemarkDetailsPK = new GPARebillRemarkDetailsPK();
		gPARebillRemarkDetailsPK.setCompanyCode(rebillRemarksDetailVO.getCompanyCode());
		gPARebillRemarkDetailsPK.setGpaCode(rebillRemarksDetailVO.getGpaCode());
		gPARebillRemarkDetailsPK.setInvoiceNumber(rebillRemarksDetailVO.getInvoiceNumber());
		gPARebillRemarkDetailsPK.setMailSequenceNumber(rebillRemarksDetailVO.getMailSeqNum());
		gPARebillRemarkDetailsPK.setInvoiceSerialNumber(rebillRemarksDetailVO.getInvoiceSerialNumber());
		gPARebillRemarkDetailsPK.setRebillRound(rebillRemarksDetailVO.getRebillRound());
		this.setgPARebillRemarkDetailsPk(gPARebillRemarkDetailsPK);
		localLogger().exiting(CLASS_NAME, "populatePk");
	}

	/**
	 * This method populates attributes of AgentForBilling Entity
	 *
	 * @param agentForBillingVO
	 * @throws SystemException
	 */
	public void populateAttributes(RebillRemarksDetailVO rebillRemarksDetailVO)
			throws SystemException {
		localLogger().entering(CLASS_NAME, "populateAttributes");
		
			this.setRebillRemark(rebillRemarksDetailVO.getRemark());
			this.setRebillInvoiceNumber(rebillRemarksDetailVO.getInvoiceNumber());
			this.setRebillStatus("P");  
			LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			this.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
			this.setLastUpdateUser(logonAttributes.getUserId());
		localLogger().exiting(CLASS_NAME, "populateAttributes");
	}
	/**
	 * 
	 * @param rebillRemarksDetailVO
	 * @throws SystemException
	 */
	public void update(RebillRemarksDetailVO rebillRemarksDetailVO) throws SystemException{
		log.entering(CLASS_NAME, "update");
		populateAttributes(rebillRemarksDetailVO);
		log.exiting(CLASS_NAME, "update");
		
	}
	/**
	 * 
	 * @param companyCode
	 * @param invoiceNumber
	 * @param invoiceSerialNumber
	 * @param invoiceSequenceNumber
	 * @param rebillRound
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPARebillRemarkDetails find (String companyCode,String gpaCode,String invoiceNumber,int invoiceSerialNumber,long mailSequenceNumber,int rebillRound)
			throws SystemException, FinderException{
		GPARebillRemarkDetailsPK GPARebillRemarkDetailsFind= new GPARebillRemarkDetailsPK(companyCode,gpaCode, invoiceNumber, invoiceSerialNumber, mailSequenceNumber, rebillRound);
		return PersistenceController.getEntityManager().find(
				GPARebillRemarkDetails.class, GPARebillRemarkDetailsFind);
	}
	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		try {
			log.log(Log.INFO,"\n\n***Deleting ");

			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage());
		}
	}
	
	/**
	 * @return the GPARebillRemarkDetailsFindPK
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
		@AttributeOverride(name="invoiceNumber", column=@Column(name="INVNUM")),
		@AttributeOverride(name="invoiceSerialNumber", column=@Column(name="INVSERNUM")),
		@AttributeOverride(name="mailSequenceNumber", column=@Column(name="MALSEQNUM")),
		@AttributeOverride(name="rebillRound", column=@Column(name="RBLRND")) })
	
	public GPARebillRemarkDetailsPK getgPARebillRemarkDetailsPk() {
		return gPARebillRemarkDetailsPk;
	}
	public void setgPARebillRemarkDetailsPk(GPARebillRemarkDetailsPK gPARebillRemarkDetailsPk) {
		this.gPARebillRemarkDetailsPk = gPARebillRemarkDetailsPk;
	}
	

	/**
	 * @return the rebillInvoiceNumber
	 */
	@Column(name = "RBLINVNUM")
	public String getRebillInvoiceNumber() {
		return rebillInvoiceNumber;
	}

	
	/**
	 * @param rebillInvoiceNumber the rebillInvoiceNumber to set
	 */
	public void setRebillInvoiceNumber(String rebillInvoiceNumber) {
		this.rebillInvoiceNumber = rebillInvoiceNumber;
	}

	/**
	 * @return the rebillRemark
	 */
	@Column(name = "RMK")
	public String getRebillRemark() {
		return rebillRemark;
	}

	/**
	 * @param rebillRemark the rebillRemark to set
	 */
	public void setRebillRemark(String rebillRemark) {
		this.rebillRemark = rebillRemark;
	}

	/**
	 * @return the rebillStatus
	 */
	@Column(name = "RBLSTA")
	public String getRebillStatus() {
		return rebillStatus;
	}

	/**
	 * @param rebillStatus the rebillStatus to set
	 */
	public void setRebillStatus(String rebillStatus) {
		this.rebillStatus = rebillStatus;
	}	
	/**
	 * @author A-5991	
	 * @param mailIdr
	 * @param companyCode
	 * @return
	 */
	private long findMailSequenceNumber(String mailIdr,String companyCode){
		MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
		long mailsequenceNumber=0;
		try {
			mailsequenceNumber= mailTrackingDefaultsProxy.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ProxyException Occured!!!!");
		} catch (SystemException e) {
			log.log(Log.SEVERE, "SystemException Occured!!!!");
		}
		
		return mailsequenceNumber;
	}
}

