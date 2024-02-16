package com.ibsplc.neoicargo.mail.component;

import java.util.Collection;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailResditFileLogVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * TODO Add the purpose of this class
 * @author A-2135
 */
@Setter
@Getter
@Slf4j
@Entity
@Table(name = "MALRDTFILLOG")
public class MailResditFileLog {
	private static final String MAILTRACKING_DEFAULTS = "mail.operations";
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "interchangeControlReference", column = @Column(name = "CNTREFNUM")),
			@AttributeOverride(name = "recipientID", column = @Column(name = "RCPIDR")) })
	private MailResditFileLogPK mailResditFileLogPK;
	@Column(name = "MSGTYP")
	private String messageType;
	@Column(name = "FILNAM")
	private String resditFileName;
	@Column(name = "SNDDAT")
	private LocalDateTime sendDate;
	@Column(name = "CCLIST")
	private String cCList;

	public MailResditFileLog() {
	}

	/** 
	* @return the messageType
	*/
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
	public LocalDateTime getSendDate() {
		return sendDate;
	}

	/** 
	* @param sendDate the sendDate to set
	*/
	public void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate;
	}

	/** 
	* @return the ccList
	*/
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
	* @param resditMessageVO
	* @throws SystemException
	*/
	public MailResditFileLog(ResditMessageVO resditMessageVO) {

		populatePK(resditMessageVO);
		populateAttributes(resditMessageVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}

	}

	/** 
	* A-2135
	* @param resditMessageVO
	*/
	private void populatePK(ResditMessageVO resditMessageVO) {
		mailResditFileLogPK = new MailResditFileLogPK();
		mailResditFileLogPK.setCompanyCode(resditMessageVO.getCompanyCode());
		mailResditFileLogPK.setInterchangeControlReference(resditMessageVO.getInterchangeControlReference());
		mailResditFileLogPK.setRecipientID(resditMessageVO.getRecipientID());

	}

	/** 
	* A-2135
	* @param resditMessageVO
	* @throws SystemException
	*/
	private void populateAttributes(ResditMessageVO resditMessageVO) {

		setMessageType(resditMessageVO.getMessageType());
		setResditFileName(resditMessageVO.getResditFileName());
		//TODO: Neo to correct
		//setSendDate(resditMessageVO.getSendDate());
		setCCList(resditMessageVO.getResditToAirlineCode());

	}

	/** 
	* @return Returns the mailResditFileLogPK.
	*/
	public MailResditFileLogPK getMailResditFileLogPK() {
		return mailResditFileLogPK;
	}

	/** 
	* @param mailResditFileLogPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailResditFileLog find(MailResditFileLogPK mailResditFileLogPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MailResditFileLog.class, mailResditFileLogPK);
	}

	/** 
	* This method is used to find the MailResditFileLog Entity as such for updating the sendDate Added by A-2135 for QF CR 1517
	* @param mailResditFileLogVO
	* @return
	* @throws SystemException
	*/
	public static Collection<MailResditFileLog> findMailResditFileLog(MailResditFileLogVO mailResditFileLogVO) {
		//TODO: Neo to implement -refer classic
//		try {
//			return constructObjectDAO().findMailResditFileLog(mailResditFileLogVO);
//		} catch (PersistenceException exception) {
//			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
//		}
		return null;
	}

	/** 
	* Added by A-2135
	* @return
	* @throws SystemException
	*/
	private static MailOperationsObjectInterface constructObjectDAO() {
		try {
			return PersistenceController.getEntityManager().getObjectQueryDAO(MAILTRACKING_DEFAULTS);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public void setSendDate(ZonedDateTime sendDate) {
		if (Objects.nonNull(sendDate)) {
			this.sendDate = sendDate.toLocalDateTime();
		}
	}
}
