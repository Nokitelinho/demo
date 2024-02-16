/*
 * MailResditFileLog.java Created on Oct 04, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditFileLogVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * TODO Add the purpose of this class
 *
 * @author A-2135
 *
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision 	Date  			Author 		Description
 * ------------------------------------------------------------------------- 
 * 	0.1			Oct 04, 2010 	A-2135  	First Draft
 */

@Entity
@Table(name = "MALRDTFILLOG")
@Staleable
public class MailResditFileLog {

	private static final String MAILTRACKING_DEFAULTS = "mail.operations";

	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private MailResditFileLogPK mailResditFileLogPK;

	private String messageType;
	
	private String resditFileName;
	
	private Calendar sendDate;
	
	private String cCList;
	

	public MailResditFileLog() {
	}
	
	
	/**
	 * @return the messageType
	 */
	@Column(name = "MSGTYP")
	public String getMessageType() {
		return messageType;
	}


	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	/**
	 * @return the resditFileName
	 */
	@Column(name = "FILNAM")
	public String getResditFileName() {
		return resditFileName;
	}


	/**
	 * @param resditFileName the resditFileName to set
	 */
	public void setResditFileName(String resditFileName) {
		this.resditFileName = resditFileName;
	}


	/**
	 * @return the sendDate
	 */
	@Column(name = "SNDDAT")
	public Calendar getSendDate() {
		return sendDate;
	}


	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}


	
	/**
	 * @return the ccList
	 */
	@Column(name = "CCLIST")
	public String getCCList() {
		return cCList;
	}


	/**
	 * @param ccList the ccList to set
	 */
	public void setCCList(String ccList) {
		this.cCList = ccList;
	}


	/**
	 * 
	 * @param resditMessageVO
	 * @throws SystemException
	 */
	public MailResditFileLog(ResditMessageVO resditMessageVO) throws SystemException {
		log.entering("MailResditFileLog", "init");
		
		populatePK(resditMessageVO);
		populateAttributes(resditMessageVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		
		log.exiting("MailResditFileLog", "init");
	}

	/**
	 * A-2135
	 *
	 * @param resditMessageVO
	 */
	private void populatePK(ResditMessageVO resditMessageVO) {
		log.entering("MailResditFileLog", "populatePK");
		
		log.log(Log.FINE, "<<<<<THE RESDITMESSAGE VO>>>>>", resditMessageVO);
		mailResditFileLogPK = new MailResditFileLogPK();		
		
		mailResditFileLogPK.setCompanyCode(resditMessageVO.getCompanyCode());
		mailResditFileLogPK.setInterchangeControlReference
							(resditMessageVO.getInterchangeControlReference());
		mailResditFileLogPK.setRecipientID(resditMessageVO.getRecipientID());
		
		log.exiting("MailResditFileLog", "populatepK");
	}

	/**
	 * A-2135
	 *
	 * @param resditMessageVO
	 * @throws SystemException
	 */
	private void populateAttributes(ResditMessageVO resditMessageVO)
	 throws SystemException {
		log.entering("MailResditFileLog", "populateAttributes");
		
		setMessageType(resditMessageVO.getMessageType());
		setResditFileName(resditMessageVO.getResditFileName());
		setSendDate(resditMessageVO.getSendDate());
		
		setCCList(resditMessageVO.getResditToAirlineCode());
		log.exiting("MailResditFileLog", "populateAttributes");
	}



	
	/**
	 * @return Returns the mailResditFileLogPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "interchangeControlReference", column = @Column(name = "CNTREFNUM")),			
			@AttributeOverride(name = "recipientID", column = @Column(name = "RCPIDR")) })
	public MailResditFileLogPK getMailResditFileLogPK() {
		return mailResditFileLogPK;
	}

	/**
	 * @param mailResditFileLogPK
	 * The mailResditFileLogPK to set.
	 */
	public void setMailResditFileLogPK(MailResditFileLogPK mailResditFileLogPK) {
		this.mailResditFileLogPK = mailResditFileLogPK;
	}

	

	

	/**
	 * @param mailResditFileLogPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailResditFileLog find(MailResditFileLogPK mailResditFileLogPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(MailResditFileLog.class,
				mailResditFileLogPK);

	}

	/**
	 * This method is used to remove the Instance of the Entity
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
	
	/**
	 * This method is used to find the MailResditFileLog Entity as such for updating the sendDate
	 * Added by A-2135 for QF CR 1517
	 * @param mailResditFileLogVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailResditFileLog> findMailResditFileLog
	(MailResditFileLogVO mailResditFileLogVO)throws SystemException {
		
		try {
			return constructObjectDAO().findMailResditFileLog(mailResditFileLogVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	/**
	 * 
	 * Added by A-2135
	 * @return
	 * @throws SystemException
	 */
   private static MailtrackingDefaultsObjectInterface constructObjectDAO() throws SystemException {
	   	try {
	   		return PersistenceController.getEntityManager().getObjectQueryDAO(
	   				MAILTRACKING_DEFAULTS);
	   	} catch(PersistenceException ex) {
	   		throw new SystemException (ex.getMessage(), ex);
	   	}
   }
}
