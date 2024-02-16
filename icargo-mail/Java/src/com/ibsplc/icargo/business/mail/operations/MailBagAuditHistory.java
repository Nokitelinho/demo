 /*
 * MailBagAuditHistory.java Created on Jun 27 2016 by A-5991 for ICRD-119569
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

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


@Entity
@Table(name = "MALAUDHIS")
@Staleable
public class MailBagAuditHistory  {

private MailBagAuditHistoryPK mailBagAuditHistoryPK;

/**
 * Last update user code
 */
private String lastUpdateUser;

private String auditField;

private String oldValue;

private String newValue;

private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

/**
 * Last update date and time
 */
private Calendar lastUpdateTime;


private String actionCode;

private String mailBagID;
/**
 * @return Returns the lastUpdateTime.
 */
@Column(name="LSTUPDTIM")
public Calendar getLastUpdateTime() {
	return lastUpdateTime;
}
/**
 * @param lastUpdateTime The lastUpdateTime to set.
 */
public void setLastUpdateTime(Calendar lastUpdateTime) {
	this.lastUpdateTime = lastUpdateTime;
}
/**
 * @return Returns the lastUpdateUser.
 */
@Column(name="LSTUPDUSR")
public String getLastUpdateUser() {
	return lastUpdateUser;
}
/**
 * @param lastUpdateUser The lastUpdateUser to set.
 */
public void setLastUpdateUser(String lastUpdateUser) {
	this.lastUpdateUser = lastUpdateUser;
}
/**
 * @return the auditField
 */
@Column(name="HISFLDNAM")
public String getAuditField() {
	return auditField;
}
/**
 * @param auditField the auditField to set
 */
public void setAuditField(String auditField) {
	this.auditField = auditField;
}
/**
 * @return the oldValue
 */
@Column(name="HISFLDOLDVAL")
public String getOldValue() {
	return oldValue;
}
/**
 * @param oldValue the oldValue to set
 */
public void setOldValue(String oldValue) {
	this.oldValue = oldValue;
}
/**
 * @return the newValue
 */
@Column(name="HISFLDNEWVAL")
public String getNewValue() {
	return newValue;
}
/**
 * @param newValue the newValue to set
 */
public void setNewValue(String newValue) {
	this.newValue = newValue;
}
/**
 * @return the shipmentAuditHistoryPK
 */
@EmbeddedId
@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
        @AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
        @AttributeOverride(name = "historySequenceNumber", column = @Column(name = "HISSEQNUM"))})

public MailBagAuditHistoryPK getMailBagAuditHistoryPK() {
	return mailBagAuditHistoryPK;
}
public void setMailBagAuditHistoryPK(MailBagAuditHistoryPK mailBagAuditHistoryPK) {
	this.mailBagAuditHistoryPK = mailBagAuditHistoryPK;
}




/**
 * @return the actionCode
 */
@Column(name="ACTCOD")
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
 * @author A-5991
 * @param mailBagAuditHistoryVO
 * @throws SystemException
 */
public MailBagAuditHistory(MailBagAuditHistoryVO mailBagAuditHistoryVO) throws  SystemException {
	MailBagAuditHistoryPK mailBagAuditHistoryPK = new MailBagAuditHistoryPK(mailBagAuditHistoryVO);
	setMailBagAuditHistoryPK(mailBagAuditHistoryPK);
setAuditField(mailBagAuditHistoryVO.getAuditField());
setOldValue(mailBagAuditHistoryVO.getOldValue());
setNewValue(mailBagAuditHistoryVO.getNewValue());
setActionCode(mailBagAuditHistoryVO.getActionCode());
setMailBagID(mailBagAuditHistoryVO.getMailbagId());

try{
PersistenceController.getEntityManager().persist(this);
}catch(CreateException e){
	log.log(Log.SEVERE, "CreateException Caught");
}
}
/**
 * @param mailBagID the mailBagID to set
 */
public void setMailBagID(String mailBagID) {
	this.mailBagID = mailBagID;
}
/**
 * @return the mailBagID
 */

@Column(name="MALIDR")
public String getMailBagID() {
	return mailBagID;
}


}