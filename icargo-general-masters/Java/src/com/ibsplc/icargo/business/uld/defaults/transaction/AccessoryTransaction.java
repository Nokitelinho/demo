/*
 * AccessoryTransaction.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction;

import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
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
 * @author A-1347
 * 
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Staleable
@Table(name = " ULDACCTXNMST")
@Entity
public class AccessoryTransaction {
	/**
	 * 
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * 
	 */
	private AccessoryTransactionPK accessoryTransactionPK;

	// Transaction Type will be L-Loan/B-Borrow/R-Return
	private String transactionType;

	// Transaction Status will be P-Permanent/T-Temporary
	private String transactionNature;

	private String partyType;

	private String partyCode;

	private String partyIdentifier;

	// Current Owner Identifier
	private String currOwnerCode;

	// Station at which Loan/Borrow happens
	private String transactionStationCode;

	private Integer transationPeriod;

	private Calendar transactionDate;

	private String transactionRemark;

	private int quantity;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;

	private Log log = LogFactory.getLogger("ULD_DEFAULTS_MANAGEMENT");

	/**
	 * @return Returns the partyIdentifier.
	 */
	@Column(name = "PTYIDR")
	public String getPartyIdentifier() {
		return partyIdentifier;
	}

	/**
	 * @param partyIdentifier
	 *            The partyIdentifier to set.
	 */
	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}

	/**
	 * @return Returns the currOwnerCode.
	 */
	@Column(name = "CUROWNCOD")
	public String getCurrOwnerCode() {
		return currOwnerCode;
	}

	/**
	 * @param currOwnerCode
	 *            The currOwnerCode to set.
	 */
	public void setCurrOwnerCode(String currOwnerCode) {
		this.currOwnerCode = currOwnerCode;
	}

	/**
	 * @return Returns the partyCode.
	 */
	@Column(name = "PTYCOD")
	public String getPartyCode() {
		return partyCode;
	}

	/**
	 * @param partyCode
	 *            The partyCode to set.
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	/**
	 * @return Returns the partyType.
	 */
	@Column(name = "PTYTYP")
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param partyType
	 *            The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return Returns the accessoryTransactionPK.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "accessoryCode", column = @Column(name = "ACCCOD")),
			@AttributeOverride(name = "transactionRefNumber", column = @Column(name = "TXNREFNUM")) })
	public AccessoryTransactionPK getAccessoryTransactionPK() {
		return accessoryTransactionPK;
	}

	/**
	 * @param accessoryTransactionPK
	 *            The accessoryTransactionPK to set.
	 */
	public void setAccessoryTransactionPK(
			AccessoryTransactionPK accessoryTransactionPK) {
		this.accessoryTransactionPK = accessoryTransactionPK;
	}

	/**
	 * @return Returns the quantity.
	 */
	@Column(name = "ACCQTY")
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	@Column(name = "TXNDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate
	 *            The transactionDate to set.
	 */
	public void setTransactionDate(Calendar transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return Returns the transactionNature.
	 */
	@Column(name = "TXNNAT")
	public String getTransactionNature() {
		return transactionNature;
	}

	/**
	 * @param transactionNature
	 *            The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}

	/**
	 * @return Returns the transactionRemark.
	 */
	@Column(name = "TXNRMK")
	public String getTransactionRemark() {
		return transactionRemark;
	}

	/**
	 * @param transactionRemark
	 *            The transactionRemark to set.
	 */
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}

	/**
	 * @return Returns the transactionStationCode.
	 */
	@Column(name = "TXNARPCOD")
	public String getTransactionStationCode() {
		return transactionStationCode;
	}

	/**
	 * @param transactionStationCode
	 *            The transactionStationCode to set.
	 */
	public void setTransactionStationCode(String transactionStationCode) {
		this.transactionStationCode = transactionStationCode;
	}

	/**
	 * @return Returns the transactionType.
	 */
	@Column(name = "TXNTYP")
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return Returns the transationPeriod.
	 */
	@Column(name = "TXNPRD")
	public Integer getTransationPeriod() {
		return transationPeriod;
	}

	/**
	 * @param transationPeriod
	 *            The transationPeriod to set.
	 */
	public void setTransationPeriod(Integer transationPeriod) {
		this.transationPeriod = transationPeriod;
	}

	/**
	 * Constructor
	 */
	public AccessoryTransaction() {
	}

	/**
	 * 
	 * @param accessoryTransactionVO
	 * @throws SystemException
	 */
	public AccessoryTransaction(AccessoryTransactionVO accessoryTransactionVO)
			throws SystemException {
		populatePk(accessoryTransactionVO);
		populateAttributes(accessoryTransactionVO);
		try {
			log.entering("CREATE METHOD CALLED FOR ",
					"ACCESSORY IN TRANSACTION");
			EntityManager em = PersistenceController.getEntityManager();
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * private method to populate PK
	 * 
	 * @param accessoryTransactionVO
	 */
	public void populatePk(AccessoryTransactionVO accessoryTransactionVO) {
		log.entering("INSIDE THE POPULATE PK", " FOR ACCESSORY TRANSACTION");
		AccessoryTransactionPK pk = new AccessoryTransactionPK();
		pk.setAccessoryCode(accessoryTransactionVO.getAccessoryCode());
		pk.setCompanyCode(accessoryTransactionVO.getCompanyCode());
		// pk.setTransactionRefNumber( accessoryTransactionVO.getTransactionRefNumber());
		this.setAccessoryTransactionPK(pk);
	}

	/**
	 * private method to populate attributes
	 * 
	 * @param accessoryTransactionVO
	 * @throws SystemException
	 */
	public void populateAttributes(AccessoryTransactionVO accessoryTransactionVO)
			throws SystemException {
		log.entering("INSIDE THE POPULATE ATTRIBUTES ",
				" FOR THE ACCESSORY TRANSACTION");
		log.log(Log.INFO, "inside aceessory transaction++++++++++++++++",
				accessoryTransactionVO);
		this.setCurrOwnerCode(String.valueOf(accessoryTransactionVO
				.getCurrOwnerCode()));
		this.setLastUpdateTime(accessoryTransactionVO.getLastUpdateTime());
		this.setLastUpdateUser(accessoryTransactionVO.getLastUpdateUser());
		this.setPartyCode(accessoryTransactionVO.getToPartyCode());
		this.setPartyType(accessoryTransactionVO.getPartyType());
		this.setQuantity(accessoryTransactionVO.getQuantity());
		this.setTransactionDate(accessoryTransactionVO.getTransactionDate()
				.toCalendar());
		this
				.setTransactionNature(accessoryTransactionVO
						.getTransactionNature());
		if(accessoryTransactionVO.getTransationPeriod() != null &&
                !accessoryTransactionVO.getTransationPeriod().isEmpty()){
            this.setTransationPeriod(Integer.parseInt(accessoryTransactionVO.getTransationPeriod()));
        }

		this
				.setTransactionRemark(accessoryTransactionVO
						.getTransactionRemark());
		this.setTransactionType(accessoryTransactionVO.getTransactionType());
		this.setPartyIdentifier(String.valueOf(accessoryTransactionVO
				.getOperationalAirlineIdentifier()));
		this.setTransactionStationCode(accessoryTransactionVO
				.getTransactionStationCode());

	}

	/**
	 * 
	 * @param companyCode
	 * @param accessoryCode
	 * @param transactionRefNumber
	 * @return
	 * @throws SystemException
	 */
	public static AccessoryTransaction find(String companyCode,
			String accessoryCode, int transactionRefNumber)
			throws SystemException {
		Log logger = LogFactory.getLogger("ULD_MANAGEMENT");
		logger.entering("ACCESSORY TRANSACTION",
				"FIND THE ACCESSORY TRANSACTION");
		AccessoryTransaction accessoryTransaction = null;
		AccessoryTransactionPK accessoryPK = new AccessoryTransactionPK();
		accessoryPK.setCompanyCode(companyCode);
		accessoryPK.setTransactionRefNumber(transactionRefNumber);
		accessoryPK.setAccessoryCode(accessoryCode);
		try {
			EntityManager entityManager = PersistenceController
					.getEntityManager();
			accessoryTransaction = entityManager.find(
					AccessoryTransaction.class, accessoryPK);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		logger.exiting("AccessoryTransaction", "find");
		return accessoryTransaction;
	}

	/**
	 * This method updates the BO
	 * 
	 * @param accessoryTransactionVO
	 * @throws SystemException
	 */
	public void update(AccessoryTransactionVO accessoryTransactionVO)
			throws SystemException {

	}

	/**
	 * This method is used to remove the business object. It interally calls the
	 * remove method within EntityManager
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("INSIDE THE ACCESSORY TRANSACTION", "REMOVE");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}

	/**
	 * Used to populate the business object with values from VO
	 * 
	 * @return AccessoryTransactionVO
	 */
	public AccessoryTransactionVO retrieveVO() {
		return null;
	}

	/**
	 * This method is used for listing uld transaction
	 * 
	 * @author A-1883
	 * @param uldTransactionFilterVO
	 * @return TransactionListVO
	 * @throws SystemException
	 */

	public static TransactionListVO listAccessoryTransactionDetails(
			TransactionFilterVO uldTransactionFilterVO) throws SystemException {
		Log loger = LogFactory.getLogger("ULD_DEFAULTS");
		loger.entering("AccessoryTransaction",
				"listAccessoryTransactionDetails");
		return constructDAO().listAccessoryTransactionDetails(
				uldTransactionFilterVO);
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @author A-1883
	 * @return ULDDefaultsDAO
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager entityManager = PersistenceController
					.getEntityManager();
			return ULDDefaultsDAO.class.cast(entityManager.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

}
