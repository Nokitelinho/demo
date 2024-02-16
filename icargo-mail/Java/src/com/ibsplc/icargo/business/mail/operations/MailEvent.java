/*
 * MailEvent.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.cache.MailEventCache;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author a-3109
 */
@Entity
@Table(name = "MALEVT")
@Staleable
public class MailEvent {

	private static final String MAIL_OPERATIONS = "mail.operations";

	private MailEventPK mailEventPK;

	/**
	 * receivedFlag
	 */
	private String receivedFlag;

	/**
	 * assignedFlag
	 */
	private String assignedFlag;

	/**
	 * returnedFlag
	 */
	private String returnedFlag;

	/**
	 * pendingFlag
	 */
	private String pendingFlag;

	/**
	 * upliftedFlag
	 */
	private String upliftedFlag;

	/**
	 * handedOverFlag
	 */
	private String handedOverFlag;

	/**
	 * deliveredFlag
	 */
	private String deliveredFlag;

	/**
	 * loadedResditFlag
	 */
	private String loadedResditFlag;

	/**
	 * onlineHandedOverResditFlag
	 */
	private String onlineHandedOverResditFlag;

	/**
	 * handedOverReceivedResditFlag
	 */
	private String handedOverReceivedResditFlag;

	/**
	 * readyForDeliveryFlag
	 */
	private String readyForDeliveryFlag;

	/**
	 * transportationCompletedFlag
	 */
	private String transportationCompletedFlag;

	/**
	 * arrivedFlag
	 */
	private String arrivedFlag;
	/**
	 * foundFlag
	 */
	private String foundFlag;
	/**
	 * lostFlag
	 */
	private String lostFlag;
	

	/**
	 * @return Returns the assignedFlag.
	 */
	@Column(name = "ASGFLG")
	public String getAssignedFlag() {
		return assignedFlag;
	}

	/**
	 * @param assignedFlag
	 *            The assignedFlag to set.
	 */
	public void setAssignedFlag(String assignedFlag) {
		this.assignedFlag = assignedFlag;
	}

	/**
	 * @return Returns the handedOverFlag.
	 */
	@Column(name = "HNDOVRFLG")
	public String getHandedOverFlag() {
		return handedOverFlag;
	}

	/**
	 * @param handedOverFlag
	 *            The handedOverFlag to set.
	 */
	public void setHandedOverFlag(String handedOverFlag) {
		this.handedOverFlag = handedOverFlag;
	}

	/**
	 * @return Returns the pendingFlag.
	 */
	@Column(name = "PNDFLG")
	public String getPendingFlag() {
		return pendingFlag;
	}

	/**
	 * @param pendingFlag
	 *            The pendingFlag to set.
	 */
	public void setPendingFlag(String pendingFlag) {
		this.pendingFlag = pendingFlag;
	}

	/**
	 * @return Returns the receivedFlag.
	 */
	@Column(name = "RCVFLG")
	public String getReceivedFlag() {
		return receivedFlag;
	}

	/**
	 * @param receivedFlag
	 *            The receivedFlag to set.
	 */
	public void setReceivedFlag(String receivedFlag) {
		this.receivedFlag = receivedFlag;
	}

	/**
	 * @return Returns the returnedFlag.
	 */
	@Column(name = "RTNFLG")
	public String getReturnedFlag() {
		return returnedFlag;
	}

	/**
	 * @param returnedFlag
	 *            The returnedFlag to set.
	 */
	public void setReturnedFlag(String returnedFlag) {
		this.returnedFlag = returnedFlag;
	}

	/**
	 * @return Returns the upliftedFlag.
	 */
	@Column(name = "UPLFLG")
	public String getUpliftedFlag() {
		return upliftedFlag;
	}

	/**
	 * @param upliftedFlag
	 *            The upliftedFlag to set.
	 */
	public void setUpliftedFlag(String upliftedFlag) {
		this.upliftedFlag = upliftedFlag;
	}

	/**
	 * @return Returns the deliveredFlag.
	 */
	@Column(name = "DLVFLG")
	public String getDeliveredFlag() {
		return this.deliveredFlag;
	}

	/**
	 * @param deliveredFlag
	 *            The deliveredFlag to set.
	 */
	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}

	/**
	 * @return Returns the handedOverReceivedResditFlag.
	 */
	@Column(name = "HNDOVRRCVFLG")
	public String getHandedOverReceivedResditFlag() {
		return handedOverReceivedResditFlag;
	}

	/**
	 * @param handedOverReceivedResditFlag
	 *            The handedOverReceivedResditFlag to set.
	 */
	public void setHandedOverReceivedResditFlag(
			String handedOverReceivedResditFlag) {
		this.handedOverReceivedResditFlag = handedOverReceivedResditFlag;
	}

	/**
	 * @return Returns the 4.
	 */
	@Column(name = "LODFLG")
	public String getLoadedResditFlag() {
		return loadedResditFlag;
	}

	/**
	 * @param loadedResditFlag
	 *            The loadedResditFlag to set.
	 */
	public void setLoadedResditFlag(String loadedResditFlag) {
		this.loadedResditFlag = loadedResditFlag;
	}

	/**
	 * @return Returns the onlineHandedOverResditFlag.
	 */
	@Column(name = "HNDOVRONLFLG")
	public String getOnlineHandedOverResditFlag() {
		return onlineHandedOverResditFlag;
	}

	/**
	 * @param onlineHandedOverResditFlag
	 *            The onlineHandedOverResditFlag to set.
	 */
	public void setOnlineHandedOverResditFlag(String onlineHandedOverResditFlag) {
		this.onlineHandedOverResditFlag = onlineHandedOverResditFlag;
	}

	/**
	 * Getter for readyForDeliveryFlag Used for :
	 */
	@Column(name = "RDYDLVFLG")
	public String getReadyForDeliveryFlag() {
		return readyForDeliveryFlag;
	}

	/**
	 * @param readyForDeliveryFlag
	 *            the readyForDeliveryFlag to set Setter for
	 *            readyForDeliveryFlag Used for :
	 */
	public void setReadyForDeliveryFlag(String readyForDeliveryFlag) {
		this.readyForDeliveryFlag = readyForDeliveryFlag;
	}

	/**
	 * Getter for transportationCompletedFlag Used for :
	 */
	@Column(name = "TRTCPLFLG")
	public String getTransportationCompletedFlag() {
		return transportationCompletedFlag;
	}

	/**
	 * @param transportationCompletedFlag
	 *            the transportationCompletedFlag to set Setter for
	 *            transportationCompletedFlag Used for :
	 */
	public void setTransportationCompletedFlag(
			String transportationCompletedFlag) {
		this.transportationCompletedFlag = transportationCompletedFlag;
	}

	/**
	 * Getter for arrivedFlag Used for :
	 */
	@Column(name = "ARRFLG")
	public String getArrivedFlag() {
		return arrivedFlag;
	}

	/**
	 * @param arrivedFlag
	 *            the arrivedFlag to set Setter for arrivedFlag Used for :
	 */
	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}

		@Column(name = "FNDFLG")
	public String getFoundFlag() {
		return foundFlag;
	}

	public void setFoundFlag(String foundFlag) {
		this.foundFlag = foundFlag;
	}

	@Column(name = "LSTFLG")
	public String getLostFlag() {
		return lostFlag;
	}

	public void setLostFlag(String lostFlag) {
		this.lostFlag = lostFlag;
	}
	/**
	 * Empty Constructor
	 * 
	 */
	public MailEvent() {

	}

	/**
	 * @return Returns the mailEventPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailboxId", column = @Column(name = "MALBOXIDR")),
			@AttributeOverride(name = "mailCategory", column = @Column(name = "MALCTG")),
			@AttributeOverride(name = "mailSubClass", column = @Column(name = "MALSUBCLS")) })
	public MailEventPK getMailEventPK() {
		return mailEventPK;
	}

	/**
	 * @param mailEventPK
	 *            The mailEventPK to set.
	 */
	public void setMailEventPK(MailEventPK mailEventPK) {
		this.mailEventPK = mailEventPK;
	}

	/**
	 * 
	 * @param postalAdministrationPK
	 * @param mailEventVO
	 * @throws SystemException
	 */
	public MailEvent(MailEventVO mailEventVO) throws SystemException {
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
	 * 
	 * @param postalAdministrationPK
	 * @param mailEventVO
	 */
	private void populatePK(MailEventVO mailEventVO) {
		MailEventPK mailEventPk = new MailEventPK();
		mailEventPk.setCompanyCode(mailEventVO.getCompanyCode());
		mailEventPk.setMailboxId(mailEventVO.getMailboxId());
		mailEventPk.setMailCategory(mailEventVO.getMailCategory());
		mailEventPk.setMailSubClass(mailEventVO.getMailClass());
		this.setMailEventPK(mailEventPk);
	}

	/**
	 * 
	 * @param postalAdministrationPK
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
		
		setLoadedResditFlag(mailEventVO.isLoadedResditFlag() ? MailEventVO.FLAG_YES
				: MailEventVO.FLAG_NO);
		setOnlineHandedOverResditFlag(mailEventVO
				.isOnlineHandedOverResditFlag() ? MailEventVO.FLAG_YES
				: MailEventVO.FLAG_NO);
		setHandedOverReceivedResditFlag(mailEventVO
				.isHandedOverReceivedResditFlag() ? MailEventVO.FLAG_YES
				: MailEventVO.FLAG_NO);
	}

	/**
	 * 
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw ex;
		}
		invalidateCache();
	}

	/**
	 * This methgod is used to find the Instance of the Entity
	 * 
	 * @param companyCode
	 * @param paCode
	 * @param mailSubClass
	 * @param mailCategory
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailEvent find(String companyCode, String mailboxId,
			String mailSubClass, String mailCategory) throws SystemException,
			FinderException {
		MailEventPK mailEventPk = new MailEventPK();
		mailEventPk.setCompanyCode(companyCode);
		mailEventPk.setMailboxId(mailboxId);
		mailEventPk.setMailSubClass(mailSubClass);
		mailEventPk.setMailCategory(mailCategory);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailEvent.class, mailEventPk);
	}

	/**
	 * 
	 * @param mailEventVO
	 * @throws CacheException 
	 */
	public void update(MailEventVO mailEventVO) throws CacheException {
		populateAttributes(mailEventVO);
		invalidateCache();
	}

	private void invalidateCache() throws CacheException {
		CacheFactory factory = CacheFactory.getInstance();
		MailEventCache cache = factory.getCache(MailEventCache.ENTITY_NAME);
		cache.invalidateForGroup(MailEventCache.MAIL_EVENT_CACHE_GROUP);
	}

	private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static Collection<MailEventVO> findMailEvent(MailEventPK maileventPK) throws SystemException {
		return constructDAO().findMailEvent(maileventPK);
	}

}
