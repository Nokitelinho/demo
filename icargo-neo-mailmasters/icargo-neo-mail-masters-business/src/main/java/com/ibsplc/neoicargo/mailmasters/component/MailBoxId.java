package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailBoxIdLovVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailEventVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailboxIdVO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Aug 5, 2016	:	Draft
 */
@Setter
@Getter
@Entity
@IdClass(MailBoxIdPk.class)
@Table(name = "MALMALBOX")
public class MailBoxId  extends BaseEntity implements Serializable {
	private static final String MODULENAME = "mail.masters";
	private static final String PARTIAL = "P";
	@Column(name = "MALBOXDES")
	private String mailboxDescription;
	@Column(name = "MALBOXOWR")
	private String ownerCode;
	@Column(name = "RDTSNDPRD")
	private int resditTriggerPeriod;
	@Column(name = "MSGEVTLOC")
	private String msgEventLocationNeeded;
	@Column(name = "PRTRDT")
	private String partialResdit;
	@Column(name = "RDTVERNUM")
	private String resditversion;
	@Column(name = "MSGENBFLG")
	private String messagingEnabled;
	@Column(name = "MALBOXOWRTYP")
	private String mailboxOwner;
	@Column(name = "MALBOXSTA")
	private String mailboxStatus;
	@Column(name = "RMK")
	private String remarks;

	@Id
	@Column(name = "MALBOXIDR")
	private String mailboxCode;

	/** 
	* Empty Constructor
	*/
	public MailBoxId() {
	}

	/** 
	* @return
	* @throws SystemException
	*/
	public MailBoxId(MailBoxIdLovVO mailBoxIdVO) {
		populatePK(mailBoxIdVO);
		populateAttributes(mailBoxIdVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populateAttributes(MailBoxIdLovVO mailBoxIdVO) {
		this.mailboxDescription = mailBoxIdVO.getMailboxDescription();
	}

	private void populatePK(MailBoxIdLovVO mailBoxIdVO) {
		this.setCompanyCode(mailBoxIdVO.getCompanyCode());
		this.setMailboxCode(mailBoxIdVO.getMailboxCode());
	}

	public MailBoxId(MailboxIdVO mailboxIdVO)  {
		populatePK(mailboxIdVO);
		populateAttributes(mailboxIdVO);
		populateChildAttribute(mailboxIdVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	private void populateChildAttribute(MailboxIdVO mailboxIdVO) {
		if (mailboxIdVO.getMailEventVOs() != null) {
			for (MailEventVO maileventVO : mailboxIdVO.getMailEventVOs())
				new MailEvent(maileventVO);
		}
	}

	private void populateAttributes(MailboxIdVO mailboxIdVO) {
		this.setMailboxDescription(mailboxIdVO.getMailboxName());
		this.setOwnerCode(mailboxIdVO.getOwnerCode());
		this.setPartialResdit(mailboxIdVO.isPartialResdit()?MailConstantsVO.FLAG_YES:MailConstantsVO.FLAG_NO);
		this.setResditTriggerPeriod(mailboxIdVO.getResditTriggerPeriod());
		this.setMsgEventLocationNeeded(mailboxIdVO.isMsgEventLocationNeeded()?MailConstantsVO.FLAG_YES:MailConstantsVO.FLAG_NO);
		this.setResditversion(mailboxIdVO.getResditversion());
		this.setMessagingEnabled(mailboxIdVO.getMessagingEnabled());
		this.setMailboxStatus( mailboxIdVO.getMailboxStatus());
		this.setMailboxOwner(mailboxIdVO.getMailboxOwner());
		this.setLastUpdatedUser(mailboxIdVO.getLastUpdateUser());
		this.setRemarks(mailboxIdVO.getRemarks());
	}

	private void populatePK(MailboxIdVO mailboxIdVO) {
		this.setCompanyCode(mailboxIdVO.getCompanyCode());
		this.setMailboxCode(mailboxIdVO.getMailboxID());
	}

	/** 
	* This methgod is used to find the Instance of the Entity
	* @param
	* @param
	* @param
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailBoxId find(MailBoxIdPk mailBoxIdPk) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailBoxId.class, mailBoxIdPk);
	}

	public static MailBoxId find(String companyCode, String mailboxId)
			throws SystemException, FinderException {
		MailBoxIdPk mailBoxIdPk = new MailBoxIdPk();
		mailBoxIdPk.setCompanyCode(companyCode);
		mailBoxIdPk.setMailboxCode(mailboxId);
		return find(mailBoxIdPk);
	}

	public void update(MailboxIdVO mailboxIdVO) throws FinderException {
		populateAttributes(mailboxIdVO);
		updateMailEvent(mailboxIdVO);
	}

	private void updateMailEvent(MailboxIdVO mailboxIdVO) {
		Collection<MailEventVO> mailEventVOs = mailboxIdVO.getMailEventVOs();
		if ((mailEventVOs != null && !mailEventVOs.isEmpty())|| !StringUtils.equalsIgnoreCase(PARTIAL,mailboxIdVO.getMessagingEnabled())) {
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
			if(StringUtils.equalsIgnoreCase(PARTIAL,mailboxIdVO.getMessagingEnabled())) {
				for (MailEventVO mailEventVO : mailEventVOs) {
					new MailEvent(mailEventVO);
				}
			}

		}
	}


	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
