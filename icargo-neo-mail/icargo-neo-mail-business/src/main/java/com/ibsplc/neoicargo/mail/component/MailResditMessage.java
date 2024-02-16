package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.vo.MailResditMessageVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Clob;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailResditMessage.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Jun 4, 2019	:	Draft
 */
@Setter
@Getter
@Slf4j
@IdClass(MailResditMessagePK.class)
@SequenceGenerator(name = "MALRDTMSGREFSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALRDTMSGREF_SEQ")
@Table(name = "MALRDTMSGREF")
@Entity
public class MailResditMessage extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALRDTMSGREFSEQ")
	@Column(name = "MSGIDR")
	private long messageIdentifier;

	@Column(name = "POACOD")
	private String poaCode;
	@Column(name = "MSGVERNUM")
	private String msgVersionNumber;
	@Column(name = "MSGTXT")
	private String messageText;
	@Column(name = "CDTEXT")
	private String carditText;
	@Lob
	@Column(name = "MSGDTL")
	private Clob messageDetail;
	@Column(name = "MSGTYP")
	private String messageType;
	@Column(name = "EVTCOD")
	private String eventCode;
	@Column(name = "EVTPRT")
	private String eventPort;
	@Column(name = "MSGSEQNUM")
	private long msgSequenceNumber;

	public MailResditMessage() {
	}

	public MailResditMessage(MailResditMessageVO mailResditMessageVO) {
		log.debug("MailResditMessage" + " : " + "init" + " Entering");
		populatePK(mailResditMessageVO);
		populateAttributes(mailResditMessageVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("MailResditMessage" + " : " + "init" + " Exiting");
	}

	/** 
	* Method		:	MailResditMessage.populateAttributes Added by 	:	A-4809 on Jun 6, 2019 Used for 	: Parameters	:	@param resditEventVO  Return type	: 	void
	*/
	private void populateAttributes(MailResditMessageVO mailResditMessageVO) {
		log.debug("MailResditMessage" + " : " + "populateAttributes" + " Entering");
		String msgTxt = null;
		String msgDet = null;
		msgTxt = mailResditMessageVO.getMsgText();
		if (mailResditMessageVO.getMsgDetails() != null && mailResditMessageVO.getMsgDetails().trim().length() > 0
				&& mailResditMessageVO.getMsgDetails().indexOf("CNI") > 0
				&& mailResditMessageVO.getMsgDetails().indexOf("UNT") > 0) {
			msgDet = mailResditMessageVO.getMsgDetails().substring(mailResditMessageVO.getMsgDetails().indexOf("CNI"),
					mailResditMessageVO.getMsgDetails().indexOf("UNT"));
		}
		setPoaCode(mailResditMessageVO.getPoaCode());
		setMsgVersionNumber(mailResditMessageVO.getMsgVersionNumber());
		setMessageText(msgTxt);
		setCarditText(mailResditMessageVO.getCarditExist());
		setMessageType(msgDet);
		setMessageType(mailResditMessageVO.getMessageType());
		setEventCode(mailResditMessageVO.getEventCode());
		setEventPort(mailResditMessageVO.getEventPort());
		//TODO: To correct in Neo
//		if (msgDet != null && msgDet.trim().length() > 0) {
//			Clob clob = PersistenceUtils.createClob(msgDet);
//			setMessageDetail(clob);
//		}
		setMsgSequenceNumber(mailResditMessageVO.getMsgSequenceNumber());
		log.debug("MailResditMessage" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* Method		:	MailResditMessage.populatePK Added by 	:	A-4809 on Jun 6, 2019 Used for 	: Parameters	:	@param resditEventVO  Return type	: 	void
	*/
	private void populatePK(MailResditMessageVO mailResditMessageVO) {
		log.debug("MailResditMessage" + " : " + "populatePK" + " Entering");
		this.setCompanyCode(mailResditMessageVO.getCompanyCode());
		log.debug("MailResditMessage" + " : " + "populatePK" + " Exiting");
	}

}
