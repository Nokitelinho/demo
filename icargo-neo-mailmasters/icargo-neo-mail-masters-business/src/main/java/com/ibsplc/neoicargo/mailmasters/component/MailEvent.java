package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailEventVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Entity
@IdClass(MailEventPK.class)
@Table(name = "MALEVT")
public class MailEvent  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";


	@Id
	@Column(name = "MALBOXIDR")
	private String mailboxId;
	/**
	 * The mailCategory
	 */
	@Id
	@Column(name = "MALCTG")
	private String mailCategory;
	/**
	 * The mailSubClass
	 */
	@Id
	@Column(name = "MALSUBCLS")
	private String mailSubClass;
	/** 
	* receivedFlag
	*/
	@Column(name = "RCVFLG")
	private String receivedFlag;
	/** 
	* assignedFlag
	*/
	@Column(name = "ASGFLG")
	private String assignedFlag;
	/** 
	* returnedFlag
	*/
	@Column(name = "RTNFLG")
	private String returnedFlag;
	/** 
	* pendingFlag
	*/
	@Column(name = "PNDFLG")
	private String pendingFlag;
	/** 
	* upliftedFlag
	*/
	@Column(name = "UPLFLG")
	private String upliftedFlag;
	/** 
	* handedOverFlag
	*/
	@Column(name = "HNDOVRFLG")
	private String handedOverFlag;
	/** 
	* deliveredFlag
	*/
	@Column(name = "DLVFLG")
	private String deliveredFlag;
	/** 
	* loadedResditFlag
	*/
	@Column(name = "LODFLG")
	private String loadedResditFlag;
	/** 
	* onlineHandedOverResditFlag
	*/
	@Column(name = "HNDOVRONLFLG")
	private String onlineHandedOverResditFlag;
	/** 
	* handedOverReceivedResditFlag
	*/
	@Column(name = "HNDOVRRCVFLG")
	private String handedOverReceivedResditFlag;
	/** 
	* readyForDeliveryFlag
	*/
	@Column(name = "RDYDLVFLG")
	private String readyForDeliveryFlag;
	/** 
	* transportationCompletedFlag
	*/
	@Column(name = "TRTCPLFLG")
	private String transportationCompletedFlag;
	/** 
	* arrivedFlag
	*/
	@Column(name = "ARRFLG")
	private String arrivedFlag;
	/** 
	* foundFlag
	*/
	@Column(name = "FNDFLG")
	private String foundFlag;
	/** 
	* lostFlag
	*/
	@Column(name = "LSTFLG")
	private String lostFlag;

	/** 
	* Empty Constructor
	*/
	public MailEvent() {
	}

	/**
	* @param mailEventVO
	* @throws SystemException
	*/
	public MailEvent(MailEventVO mailEventVO) {
		populatePK(mailEventVO);
		populateAttributes(mailEventVO);
		try {
			PersistenceController.getEntityManager().persist(this);
			invalidateCache();
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	* @param mailEventVO
	*/
	private void populatePK(MailEventVO mailEventVO) {
		this.setCompanyCode(mailEventVO.getCompanyCode());
		this.setMailboxId(mailEventVO.getMailboxId());
		this.setMailCategory(mailEventVO.getMailCategory());
		this.setMailSubClass(mailEventVO.getMailClass());
	}

	/**
	* @param mailEventVO
	*/
	private void populateAttributes(MailEventVO mailEventVO) {
		if (mailEventVO.isAssigned()) {
			this.setAssignedFlag(MailEventVO.FLAG_YES);
		} else {
			this.setAssignedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isHandedOver()) {
			this.setHandedOverFlag(MailEventVO.FLAG_YES);
		} else {
			this.setHandedOverFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isPending()) {
			this.setPendingFlag(MailEventVO.FLAG_YES);
		} else {
			this.setPendingFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isReceived()) {
			this.setReceivedFlag(MailEventVO.FLAG_YES);
		} else {
			this.setReceivedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isReturned()) {
			this.setReturnedFlag(MailEventVO.FLAG_YES);
		} else {
			this.setReturnedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isUplifted()) {
			this.setUpliftedFlag(MailEventVO.FLAG_YES);
		} else {
			this.setUpliftedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isDelivered()) {
			setDeliveredFlag(MailEventVO.FLAG_YES);
		} else {
			setDeliveredFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isReadyForDelivery()) {
			setReadyForDeliveryFlag(MailEventVO.FLAG_YES);
		} else {
			setReadyForDeliveryFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isTransportationCompleted()) {
			setTransportationCompletedFlag(MailEventVO.FLAG_YES);
		} else {
			setTransportationCompletedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isArrived()) {
			setArrivedFlag(MailEventVO.FLAG_YES);
		} else {
			setArrivedFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isLostFlag()) {
			setLostFlag(MailEventVO.FLAG_YES);
		} else {
			setLostFlag(MailEventVO.FLAG_NO);
		}
		if (mailEventVO.isFoundFlag()) {
			setFoundFlag(MailEventVO.FLAG_YES);
		} else {
			setFoundFlag(MailEventVO.FLAG_NO);
		}
		setLoadedResditFlag(mailEventVO.isLoadedResditFlag() ? MailEventVO.FLAG_YES : MailEventVO.FLAG_NO);
		setOnlineHandedOverResditFlag(
				mailEventVO.isOnlineHandedOverResditFlag() ? MailEventVO.FLAG_YES : MailEventVO.FLAG_NO);
		setHandedOverReceivedResditFlag(
				mailEventVO.isHandedOverReceivedResditFlag() ? MailEventVO.FLAG_YES : MailEventVO.FLAG_NO);
	}

	/** 
	* This methgod is used to find the Instance of the Entity
	* @param companyCode
	* @param mailSubClass
	* @param mailCategory
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailEvent find(String companyCode, String mailboxId, String mailSubClass, String mailCategory)
			throws FinderException {
		MailEventPK mailEventPk = new MailEventPK();
		mailEventPk.setCompanyCode(companyCode);
		mailEventPk.setMailboxId(mailboxId);
		mailEventPk.setMailSubClass(mailSubClass);
		mailEventPk.setMailCategory(mailCategory);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailEvent.class, mailEventPk);
	}

	/** 
	* @param mailEventVO
	*/
	public void update(MailEventVO mailEventVO) {
		populateAttributes(mailEventVO);
		invalidateCache();
	}

	private void invalidateCache() {

		//TODO:Neo correction Cache
		//cache.invalidateForGroup(MailEventCache.MAIL_EVENT_CACHE_GROUP);
	}

	public void remove() throws RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw ex;
		}
	}
	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	public static Collection<MailEventVO> findMailEvent(String companyCode, String mailboxId){
		Collection<MailEventVO>mailEventVOs = new ArrayList<>();
		MailEventPK mailEventPk = new MailEventPK();
		mailEventPk.setCompanyCode(companyCode);
		mailEventPk.setMailboxId(mailboxId);
		mailEventVOs = constructDAO().findMailEvent(mailEventPk);
		return mailEventVOs;
	}
}
