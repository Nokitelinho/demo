/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessage.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Jun 4, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.sql.Clob;
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessage.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Jun 4, 2019	:	Draft
 */

@Table(name = "MALRDTMSGREF")
@Entity
@Staleable
public class MailResditMessage {

	 private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	 
	 private MailResditMessagePK mailResditMessagePK;
	 private Calendar lastUpdateTime;
	 private String poaCode;
	 private String msgVersionNumber;
	 private String messageText;
	 private String carditText;
	 private Clob messageDetail;
	 private String messageType;
	 private String eventCode;
	 private String eventPort;
	 private long msgSequenceNumber;
	 
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP) 
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	@Column(name = "MSGVERNUM")
	public String getMsgVersionNumber() {
		return msgVersionNumber;
	}
	public void setMsgVersionNumber(String msgVersionNumber) {
		this.msgVersionNumber = msgVersionNumber;
	}
	@Column(name = "MSGTXT")
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	@Column(name = "CDTEXT")
	public String getCarditText() {
		return carditText;
	}
	public void setCarditText(String carditText) {
		this.carditText = carditText;
	}
	@Lob
	@Column(name = "MSGDTL")
	public Clob getMessageDetail() {
		return messageDetail;
	}
	public void setMessageDetail(Clob messageDetail) {
		this.messageDetail = messageDetail;
	}
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "messageIdentifier", column = @Column(name = "MSGIDR"))
       })	
	public MailResditMessagePK getMailResditMessagePK() {
		return mailResditMessagePK;
	}
	public void setMailResditMessagePK(MailResditMessagePK mailResditMessagePK) {
		this.mailResditMessagePK = mailResditMessagePK;
	}
	
	public MailResditMessage() {
	}
	
	public MailResditMessage(MailResditMessageVO mailResditMessageVO) throws SystemException {
		log.entering("MailResditMessage", "init");
		populatePK(mailResditMessageVO);
		populateAttributes(mailResditMessageVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MailResditMessage", "init");
	}
/**
 * 	Method		:	MailResditMessage.populateAttributes
 *	Added by 	:	A-4809 on Jun 6, 2019
 * 	Used for 	:
 *	Parameters	:	@param resditEventVO 
 *	Return type	: 	void
 */
	private void populateAttributes(MailResditMessageVO mailResditMessageVO) throws SystemException{
		log.entering("MailResditMessage", "populateAttributes");
		String msgTxt=null;
		String msgDet=null;
		msgTxt=mailResditMessageVO.getMsgText();
		if(mailResditMessageVO.getMsgDetails()!=null && mailResditMessageVO.getMsgDetails().trim().length()>0
				&&mailResditMessageVO.getMsgDetails().indexOf("CNI")>0 && mailResditMessageVO.getMsgDetails().indexOf("UNT")>0){ 
		msgDet=mailResditMessageVO.getMsgDetails().substring(mailResditMessageVO.getMsgDetails().indexOf("CNI"), mailResditMessageVO.getMsgDetails().indexOf("UNT"));
		}
		
		setPoaCode(mailResditMessageVO.getPoaCode());
		setMsgVersionNumber(mailResditMessageVO.getMsgVersionNumber());
		
		
		setMessageText(msgTxt);
		
		setCarditText(mailResditMessageVO.getCarditExist());
		setMessageType(msgDet);
		setMessageType(mailResditMessageVO.getMessageType());
		setEventCode(mailResditMessageVO.getEventCode());
		setEventPort(mailResditMessageVO.getEventPort());
		if(msgDet!=null &&msgDet.trim().length()>0){
			 
			Clob clob = PersistenceUtils.createClob(msgDet);
			setMessageDetail(clob);
			} 
		setMsgSequenceNumber(mailResditMessageVO.getMsgSequenceNumber());
		log.exiting("MailResditMessage", "populateAttributes");
	}
/**
 * 	Method		:	MailResditMessage.populatePK
 *	Added by 	:	A-4809 on Jun 6, 2019
 * 	Used for 	:
 *	Parameters	:	@param resditEventVO 
 *	Return type	: 	void
 */
	private void populatePK(MailResditMessageVO mailResditMessageVO) {
		log.entering("MailResditMessage", "populatePK");
		mailResditMessagePK = new MailResditMessagePK();
		mailResditMessagePK.setCompanyCode(mailResditMessageVO.getCompanyCode());
		        
		log.exiting("MailResditMessage", "populatePK");
	}
	
/**
 * 	Method		:	MailResditMessage.find
 *	Added by 	:	A-4809 on Jun 6, 2019
 * 	Used for 	:
 *	Parameters	:	@param mailResditMessagePK
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws FinderException 
 *	Return type	: 	MailResditMessage
 */
	public static MailResditMessage find(MailResditMessagePK mailResditMessagePK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(MailResditMessage.class,
				mailResditMessagePK);

	}

/**
 * 	Method		:	MailResditMessage.remove
 *	Added by 	:	A-4809 on Jun 6, 2019
 * 	Used for 	:
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
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
 * 	Getter for messageType 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
	@Column(name = "MSGTYP")
public String getMessageType() {
	return messageType;
}
/**
 *  @param messageType the messageType to set
 * 	Setter for messageType 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
public void setMessageType(String messageType) {
	this.messageType = messageType;
}
/**
 * 	Getter for eventCode 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
@Column(name = "EVTCOD")
public String getEventCode() {
	return eventCode;
}
/**
 *  @param eventCode the eventCode to set
 * 	Setter for eventCode 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
public void setEventCode(String eventCode) {
	this.eventCode = eventCode;
}
/**
 * 	Getter for eventPort 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
@Column(name = "EVTPRT")
public String getEventPort() {
	return eventPort;
}
/**
 *  @param eventPort the eventPort to set
 * 	Setter for eventPort 
 *	Added by : A-8061 on 28-Jun-2019
 * 	Used for :
 */
public void setEventPort(String eventPort) {
	this.eventPort = eventPort;
}
@Column(name = "MSGSEQNUM")
public long getMsgSequenceNumber() {
	return msgSequenceNumber;
}
public void setMsgSequenceNumber(long msgSequenceNumber) {
	this.msgSequenceNumber = msgSequenceNumber;
}
} 
