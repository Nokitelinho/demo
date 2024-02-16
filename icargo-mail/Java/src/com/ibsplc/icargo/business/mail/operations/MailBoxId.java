/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 5, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.Collection;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.mail.operations.MailBoxIdPk;
import com.ibsplc.icargo.business.mail.operations.vo.MailBoxIdLovVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 5, 2016	:	Draft
 */
@Entity
@Table(name = "MALMALBOX")
@Staleable
public class MailBoxId {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_NAME);

	private String mailboxDescription;

	//private String mailboxName;
	private String ownerCode;
	private int resditTriggerPeriod;
	private String msgEventLocationNeeded;
	private String partialResdit;
	private String resditversion;
	private String messagingEnabled;
	private String mailboxOwner;
	private String mailboxStatus;
	private String lastUpdateUser;
	private String remarks;
	//private Calendar lastUpdateTime;
	
	private MailBoxIdPk mailboxIdPK;
	/**
	 * @return Returns the postalAdministrationPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailboxCode", column = @Column(name = "MALBOXIDR")) 
	})
			
	public MailBoxIdPk getMailboxIdPK() {
		return mailboxIdPK;
	}

	/**
	 * @param postalAdministrationPK
	 *            The postalAdministrationPK to set.
	 */
	public void setMailboxIdPK (MailBoxIdPk mailboxIdPK) {
		this.mailboxIdPK = mailboxIdPK;
	}

	/**
	 * Empty Constructor
	 *
	 */
	
	
	public MailBoxId() {

	}
	

	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	public MailBoxId(MailBoxIdLovVO mailBoxIdVO)throws SystemException{

		populatePK(mailBoxIdVO);
		populateAttributes(mailBoxIdVO);
		try {

			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}


	}
	
	private void populateAttributes(MailBoxIdLovVO mailBoxIdVO) {
		
			this.mailboxDescription=mailBoxIdVO.getMailboxDescription();
	}
	private void populatePK(MailBoxIdLovVO mailBoxIdVO) {
		
			MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
			mailBoxIdPk.setCompanyCode(mailBoxIdVO.getCompanyCode());
			mailBoxIdPk.setMailboxCode(mailBoxIdVO.getMailboxCode());
			this.mailboxIdPK=mailBoxIdPk;
		
	}
	
	public MailBoxId(MailboxIdVO mailboxIdVO) throws SystemException,
	FinderException{
		populatePK(mailboxIdVO);
		populateAttributes(mailboxIdVO);
		populateChildAttribute(mailboxIdVO);
		try {

			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populateChildAttribute(MailboxIdVO mailboxIdVO) throws SystemException {
		if(mailboxIdVO.getMailEventVOs()!= null){
			for(MailEventVO maileventVO : mailboxIdVO.getMailEventVOs())
				new MailEvent(maileventVO);
		}
		
	}

	private void populateAttributes(MailboxIdVO mailboxIdVO) {
		this.mailboxDescription = mailboxIdVO.getMailboxName();
		this.ownerCode = mailboxIdVO.getOwnerCode();
		this.partialResdit = mailboxIdVO.isPartialResdit()?MailConstantsVO.FLAG_YES:MailConstantsVO.FLAG_NO;
		this.resditTriggerPeriod = mailboxIdVO.getResditTriggerPeriod();
		this.msgEventLocationNeeded = mailboxIdVO.isMsgEventLocationNeeded()?MailConstantsVO.FLAG_YES:MailConstantsVO.FLAG_NO;;
		this.resditversion = mailboxIdVO.getResditversion();
		this.messagingEnabled = mailboxIdVO.getMessagingEnabled();
		this.mailboxStatus = mailboxIdVO.getMailboxStatus();
		this.mailboxOwner = mailboxIdVO.getMailboxOwner();
		this.lastUpdateUser = mailboxIdVO.getLastUpdateUser();
		this.remarks = mailboxIdVO.getRemarks();
	}

	private void populatePK(MailboxIdVO mailboxIdVO) {
		MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
		mailBoxIdPk.setCompanyCode(mailboxIdVO.getCompanyCode());
		mailBoxIdPk.setMailboxCode(mailboxIdVO.getMailboxID());
		this.mailboxIdPK=mailBoxIdPk;
		
	}


	/**
     * This methgod is used to find the Instance of the Entity
     * @param companyCode
     * @param
     * @param
     * @param
     * @return
     * @throws SystemException
     * @throws FinderException
     */
	public static MailBoxId find(MailBoxIdPk mailBoxIdPk) throws SystemException,
			FinderException {
		
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailBoxId.class, mailBoxIdPk);
	}
	
	
	public static MailBoxId find(String companyCode, String mailboxId)
			throws SystemException, FinderException {
		MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
		mailBoxIdPk.setCompanyCode(companyCode);
		mailBoxIdPk.setMailboxCode(mailboxId);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailBoxId.class, mailBoxIdPk);
	}
	
	
	public void update(MailboxIdVO mailboxIdVO)throws SystemException, FinderException {
		populateAttributes(mailboxIdVO);
		updateMailEvent(mailboxIdVO);	
	}


	@Column(name = "MALBOXDES")
	

	public String getMailboxDescription() {
		return mailboxDescription;
	}

	public void setMailboxDescription(String mailboxDescription) {
		this.mailboxDescription = mailboxDescription;
	}	
	
	@Column(name = "MALBOXOWR")
	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	@Column(name = "RDTSNDPRD")
	public int getResditTriggerPeriod() {
		return resditTriggerPeriod;
	}

	public void setResditTriggerPeriod(int resditTriggerPeriod) {
		this.resditTriggerPeriod = resditTriggerPeriod;
	}
	
	@Column(name = "MSGEVTLOC")
	public String getMsgEventLocationNeeded() {
		return msgEventLocationNeeded;
	}

	public void setMsgEventLocationNeeded(String msgEventLocationNeeded) {
		this.msgEventLocationNeeded = msgEventLocationNeeded;
	}


	@Column(name = "PRTRDT")
	public String getPartialResdit() {
		return partialResdit;
	}

	public void setPartialResdit(String partialResdit) {
		this.partialResdit = partialResdit;
	}


	@Column(name = "RDTVERNUM")
	public String getResditversion() {
		return resditversion;
	}

	public void setResditversion(String resditversion) {
		this.resditversion = resditversion;
	}

	@Column(name = "MSGENBFLG")
	public String getMessagingEnabled() {
		return messagingEnabled;
	}

	public void setMessagingEnabled(String messagingEnabled) {
		this.messagingEnabled = messagingEnabled;
	}

	@Column(name = "MALBOXSTA")
	public String getMailboxStatus() {
		return mailboxStatus;
	}

	public void setMailboxStatus(String mailboxStatus) {
		this.mailboxStatus = mailboxStatus;
	}

	@Column(name = "MALBOXOWRTYP")
	public String getMailboxOwner() {
		return mailboxOwner;
	}

	public void setMailboxOwner(String mailboxOwner) {
		this.mailboxOwner = mailboxOwner;
	}

	
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	private void updateMailEvent(MailboxIdVO mailboxIdVO) throws SystemException {
		Collection<MailEventVO> mailEventVOs = mailboxIdVO.getMailEventVOs();
		if ((mailEventVOs != null && !mailEventVOs.isEmpty())|| !StringUtils.equalsIgnoreCase(MailConstantsVO.MESSAGE_ENABLED_PARTIAL,mailboxIdVO.getMessagingEnabled())) {
			MailEventPK mailEventPK = new MailEventPK();
			mailEventPK.setCompanyCode(mailboxIdVO.getCompanyCode());
			mailEventPK.setMailboxId(mailboxIdVO.getMailboxID());
			Collection<MailEventVO> mailEventVOCollection = constructDAO().findMailEvent(mailEventPK);
			if (Objects.nonNull(mailEventVOCollection) && !mailEventVOCollection.isEmpty()) {
				for (MailEventVO mailEventVO : mailEventVOCollection) {
					try {
						MailEvent mailEvent = MailEvent.find(mailEventPK.getCompanyCode(), mailEventPK.getMailboxId(),
									mailEventVO.getMailClass(), mailEventVO.getMailCategory());
						mailEvent.remove();
					} catch (FinderException | RemoveException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
				}
			}
			if(StringUtils.equalsIgnoreCase(MailConstantsVO.MESSAGE_ENABLED_PARTIAL,mailboxIdVO.getMessagingEnabled())) {
				for (MailEventVO mailEventVO : mailEventVOs) {
					new MailEvent(mailEventVO);
				}
			}

		}
	}


	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			LOGGER.log(Log.SEVERE, persistenceException);
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	

}
