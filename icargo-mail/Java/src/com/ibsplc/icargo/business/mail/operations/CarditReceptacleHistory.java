/*
 * CarditReceptacleHistory.java Created on Oct 12, 2010
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 *
 * Maintains history for a mailbag. This entity stores all
 * details regarding the mailbag transactions.
 * *
 */
@Entity
@Table(name = "MALCDTRCPHIS")
@Staleable
public class CarditReceptacleHistory {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private CarditReceptacleHistoryPK carditReceptacleHistoryPK;

	private String carditKey;

	private String receptacleStatus;

	private Calendar updatedTime;

	public CarditReceptacleHistory() {

	}

	/**
	 * @author A-1739
	 * @param carditVO
	 * @param carditReceptacleVO
	 * @return CarditReceptacleHistory
	 * @throws SystemException
	 */
	public CarditReceptacleHistory(CarditVO carditVO,CarditReceptacleVO carditReceptacleVO)
			throws SystemException {
		populatePK(carditVO,carditReceptacleVO);
		populateAttributes(carditVO,carditReceptacleVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}

	}

	/**
	 * @author A-3429
	 * @param carditVO
	 * @param carditReceptacleVO
	 * @throws SystemException
	 */
	private void populatePK(CarditVO carditVO,CarditReceptacleVO carditReceptacleVO)
			throws SystemException {
		carditReceptacleHistoryPK = new CarditReceptacleHistoryPK();
		carditReceptacleHistoryPK.setCompanyCode(carditVO.getCompanyCode());
		long mailSeq = carditReceptacleVO.getMailSeqNum() == 0 ? Mailbag.findMailBagSequenceNumberFromMailIdr(carditReceptacleVO.getReceptacleId(),
				carditVO.getCompanyCode()) : carditReceptacleVO.getMailSeqNum();
		carditReceptacleHistoryPK.setMailSeqNum(mailSeq);
		carditReceptacleVO.setMailSeqNum(mailSeq);
	}

	/**
	 *	A-3429
	 *	@param carditVO
	 * 	@param carditReceptacleVO
	 */
	private void populateAttributes(CarditVO carditVO,CarditReceptacleVO carditReceptacleVO) {
		setCarditKey(carditVO.getCarditKey());
		setReceptacleStatus(carditReceptacleVO.getReceptacleStatus());
		setUpdatedTime(carditReceptacleVO.getUpdatedTime());

	}

	/**
	 * @return the carditKey
	 */
	@Column(name = "CDTKEY")
	public String getCarditKey() {
		return carditKey;
	}

	/**
	 * @param carditKey the carditKey to set
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return the receptacleStatus
	 */
	@Column(name = "RCPSTA")
	public String getReceptacleStatus() {
		return receptacleStatus;
	}

	/**
	 * @param receptacleStatus the receptacleStatus to set
	 */
	public void setReceptacleStatus(String receptacleStatus) {
		this.receptacleStatus = receptacleStatus;
	}

	/**
	 * @return the updatedTime
	 */
	@Column(name = "UPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(Calendar updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return Returns the carditReceptacleHistoryPK.
	 *
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailSeqNum", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	/**
	 * @return the carditReceptacleHistoryPK
	 */
	public CarditReceptacleHistoryPK getCarditReceptacleHistoryPK() {
		return carditReceptacleHistoryPK;
	}

	/**
	 * @param carditReceptacleHistoryPK the carditReceptacleHistoryPK to set
	 */
	public void setCarditReceptacleHistoryPK(
			CarditReceptacleHistoryPK carditReceptacleHistoryPK) {
		this.carditReceptacleHistoryPK = carditReceptacleHistoryPK;
	}

	 /**
	   * @author A-3429
	   * This method id used to find the instance of the Entity
	   * @param CarditReceptacleHistoryPK
	   * @return
	   * @throws FinderException
	   * @throws SystemException
	   */
	  public static CarditReceptacleHistory find(CarditReceptacleHistoryPK receptacleHistoryPK)
	      throws FinderException, SystemException {
	      return PersistenceController.getEntityManager().find(CarditReceptacleHistory.class, receptacleHistoryPK);
	  }
}
