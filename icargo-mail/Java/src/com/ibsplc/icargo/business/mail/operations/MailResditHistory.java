/*
 * MailResditHistory.java Created on June 27, 2016
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
import javax.persistence.Version;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-3109
 * 
 */
@Entity
@Table(name = "MALRDTHIS")
public class MailResditHistory {

	private static final String MAIL_OPERATIONS = "mail.operations";

	private Log log = LogFactory.getLogger("MAIL_operations");

	private MailResditHistoryPK mailResditHistoryPK;

	/**
	 * The eventAirport
	 */
	private String eventAirport;
	/**
	 * The eventDate
	 */
	private Calendar eventDate;
	/**
	 * The carrierId
	 */
	private int carrierId;
	/**
	 * The flightNumber
	 */
	private String flightNumber;
	/**
	 * The flightSequenceNumber
	 */
	private long flightSequenceNumber;
	/**
	 * The segmentSerialNumber
	 */
	private int segmentSerialNumber;
	/**
	 * The uldNumber
	 */
	private String uldNumber;
	/**
	 * The resditSent
	 */
	private String resditSent;
	/**
	 * The processedStatus
	 */
	private String processedStatus;
	/**
	 * The messageSequenceNumber
	 */
	private long messageSequenceNumber;
	/**
	 * The carditKey
	 */
	private String carditKey;
	/**
	 * The paOrCarrierCode
	 */
	private String paOrCarrierCode;
	/**
	 * The utcEventDate
	 */
	private Calendar utcEventDate;
	/**
	 * The mailEventSequenceNumber
	 */
	private int mailEventSequenceNumber;
	/**
	 * The lastUpdateTime
	 */
	private Calendar lastUpdateTime;
	/**
	 * The lastUpdateUser
	 */
	private String lastUpdateUser;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;
	/**
	 * The reconciliationStatus
	 */
	private String reconciliationStatus;

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
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
	 * @return Returns the mailEventSequenceNumber.
	 */
	@Column(name = "MALEVTSEQ")
	public int getMailEventSequenceNumber() {
		return mailEventSequenceNumber;
	}

	/**
	 * @param mailEventSequenceNumber
	 *            The mailEventSequenceNumber to set.
	 */
	public void setMailEventSequenceNumber(int mailEventSequenceNumber) {
		this.mailEventSequenceNumber = mailEventSequenceNumber;
	}

	/**
	 * @return Returns the carrierId.
	 */
	@Column(name = "FLTCARIDR")
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the eventAirport.
	 */
	@Column(name = "EVTPRT")
	public String getEventAirport() {
		return eventAirport;
	}

	/**
	 * @param eventAirport
	 *            The eventAirport to set.
	 */
	public void setEventAirport(String eventAirport) {
		this.eventAirport = eventAirport;
	}

	/**
	 * @return Returns the eventDate.
	 */
	@Column(name = "EVTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            The eventDate to set.
	 */
	public void setEventDate(Calendar eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	@Column(name = "FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	@Column(name = "CONNUM")
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the reconciliationStatus
	 */
	@Column(name = "RCNSTA")
	public String getReconciliationStatus() {
		return reconciliationStatus;
	}

	/**
	 * @param reconciliationStatus
	 *            the reconciliationStatus to set
	 */
	public void setReconciliationStatus(String reconciliationStatus) {
		this.reconciliationStatus = reconciliationStatus;
	}

	/**
	 * @return Returns the resditSent.
	 */
	@Column(name = "RDTSND")
	public String getResditSent() {
		return resditSent;
	}

	/**
	 * @param resditSent
	 *            The resditSent to set.
	 */
	public void setResditSent(String resditSent) {
		this.resditSent = resditSent;
	}

	/**
	 * @return Returns the messageSequenceNumber.
	 */
	@Column(name = "MSGSEQNUM ")
	public long getMessageSequenceNumber() {
		return messageSequenceNumber;
	}

	/**
	 * @param messageSequenceNumber
	 *            The messageSequenceNumber to set.
	 */
	public void setMessageSequenceNumber(long messageSequenceNumber) {
		this.messageSequenceNumber = messageSequenceNumber;
	}

	/**
	 * @return Returns the processedStatus.
	 */
	@Column(name = "PROSTA ")
	public String getProcessedStatus() {
		return processedStatus;
	}

	/**
	 * @param processedStatus
	 *            The processedStatus to set.
	 */
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}

	/**
	 * @return Returns the carditKey.
	 */
	@Column(name = "CDTKEY")
	public String getCarditKey() {
		return this.carditKey;
	}

	/**
	 * @param carditKey
	 *            The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return Returns the paOrCarrierCode.
	 */
	@Column(name = "POACARCOD")
	public String getPaOrCarrierCode() {
		return paOrCarrierCode;
	}

	/**
	 * @param paOrCarrierCode
	 *            The paOrCarrierCode to set.
	 */
	public void setPaOrCarrierCode(String paOrCarrierCode) {
		this.paOrCarrierCode = paOrCarrierCode;
	}

	/**
	 * 
	 * @return utcEventDate
	 */
	@Column(name = "EVTDATUTC")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUtcEventDate() {
		return utcEventDate;
	}

	/**
	 * 
	 * @param utcEventDate
	 */
	public void setUtcEventDate(Calendar utcEventDate) {
		this.utcEventDate = utcEventDate;
	}

	/**
	 * @return the interchangeControlReference
	 */
	@Column(name = "CNTREFNUM")
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	/**
	 * @param interchangeControlReference
	 *            the interchangeControlReference to set
	 */
	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	/**
	 * @return Returns the mailResditHistoryPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "eventCode", column = @Column(name = "EVTCOD")),
			@AttributeOverride(name = "mailId", column = @Column(name = "MALIDR")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public MailResditHistoryPK getMailResditHistoryPK() {
		return mailResditHistoryPK;
	}

	/**
	 * @param mailResditHistoryPK
	 *            The mailResditHistoryPK to set.
	 */
	public void setMailResditHistoryPK(MailResditHistoryPK mailResditHistoryPK) {
		this.mailResditHistoryPK = mailResditHistoryPK;
	}

	public MailResditHistory() {
	}

	public MailResditHistory(MailResditVO mailResditVO) throws SystemException {
		log.entering("MailResditHistory", "init");
		populatePK(mailResditVO);
		populateAttributes(mailResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MailResditHistory", "init");
	}

	/**
	 * @author A-3109 This method is used to remove the DAOS Instance
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}

	/**
	 * A-3109
	 * 
	 * @param mailResditVO
	 */
	private void populatePK(MailResditVO mailResditVO) {
		log.entering("MailResditHistory", "populatePK");
		log.log(Log.FINE, "THE MAILRESDIT VO>>>>>>>>>>", mailResditVO);
		mailResditHistoryPK = new MailResditHistoryPK();
		mailResditHistoryPK.setCompanyCode(mailResditVO.getCompanyCode());
		mailResditHistoryPK.setMailId(mailResditVO.getMailId());
		mailResditHistoryPK.setEventCode(mailResditVO.getEventCode());
		log.exiting("MailResditHistory", "populatepK");
	}

	/**
	 * A-3109
	 * 
	 * @param mailResditVO
	 * @throws SystemException
	 */
	private void populateAttributes(MailResditVO mailResditVO)
			throws SystemException {
		log.entering("MailResditHistory", "populateAttributes");
		setCarrierId(mailResditVO.getCarrierId());
		setFlightNumber(mailResditVO.getFlightNumber());
		setFlightSequenceNumber(mailResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(mailResditVO.getSegmentSerialNumber());
		setEventAirport(mailResditVO.getEventAirport());
		if (mailResditVO.getEventDate() != null) {
			setEventDate(mailResditVO.getEventDate());
			setUtcEventDate(mailResditVO.getEventDate().toGMTDate()
					.toCalendar());
		}
		setUldNumber(mailResditVO.getUldNumber());
		setResditSent(mailResditVO.getResditSentFlag());
		setProcessedStatus(mailResditVO.getProcessedStatus());
		setMessageSequenceNumber(mailResditVO.getMessageSequenceNumber());
		setCarditKey(mailResditVO.getCarditKey());
		setPaOrCarrierCode(mailResditVO.getPaOrCarrierCode());
		setMailEventSequenceNumber(mailResditVO.getMailEventSequenceNumber());
		setLastUpdateUser(mailResditVO.getLastUpdateUser());
		setInterchangeControlReference(mailResditVO
				.getInterchangeControlReference());
		setReconciliationStatus(mailResditVO.getReconciliationStatus());
		log.exiting("MailResditHistory", "populateAttributes");
	}

}
