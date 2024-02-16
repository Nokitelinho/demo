/*
 * UldResdit.java Created on June 27, 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3109
 * 
 */
@Entity
@Table(name = "MALULDRDTHIS")
@Staleable
public class UldResditHistory {
	private static final String MAIL_OPERATIONS = "mail.operations";

	private Log log = LogFactory.getLogger("MAIL_operations");

	private UldResditHistoryPK uldResditHistoryPK;
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
	 * The uldEventSequenceNumber
	 */
	private int uldEventSequenceNumber;
	/**
	 * The carditKey
	 */
	private String carditKey;
	/**
	 * The paOrCarrierCode
	 */
	private String paOrCarrierCode;
	/**
	 * The containerJourneyId
	 */
	private String containerJourneyId;
	/**
	 * shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB
	 * ULD.
	 */
	private String shipperBuiltCode;
	/**
	 * The utcEventDate
	 */
	private Calendar utcEventDate;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;

	/**
	 * @return Returns the uldEventSequenceNumber.
	 */
	@Column(name = "ULDEVTSEQ")
	public int getUldEventSequenceNumber() {
		return uldEventSequenceNumber;
	}

	/**
	 * @param uldEventSequenceNumber
	 *            The uldEventSequenceNumber to set.
	 */
	public void setUldEventSequenceNumber(int uldEventSequenceNumber) {
		this.uldEventSequenceNumber = uldEventSequenceNumber;
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
	 * @return the containerJourneyId
	 */
	@Column(name = "CONJRNIDR")
	public String getContainerJourneyId() {
		return containerJourneyId;
	}

	/**
	 * @param containerJourneyId
	 *            the containerJourneyId to set
	 */
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}

	/**
	 * @return the shipperBuiltCode
	 */
	@Column(name = "POACOD")
	public String getShipperBuiltCode() {
		return shipperBuiltCode;
	}

	/**
	 * @param shipperBuiltCode
	 *            the shipperBuiltCode to set
	 */
	public void setShipperBuiltCode(String shipperBuiltCode) {
		this.shipperBuiltCode = shipperBuiltCode;
	}

	/**
	 * @return Returns the uldResditHistoryPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "eventCode", column = @Column(name = "EVTCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public UldResditHistoryPK getUldResditHistoryPK() {
		return uldResditHistoryPK;
	}

	/**
	 * @param uldResditHistoryPK
	 *            The uldResditHistoryPK to set.
	 */
	public void setUldResditHistoryPK(UldResditHistoryPK uldResditHistoryPK) {
		this.uldResditHistoryPK = uldResditHistoryPK;
	}

	/**
	 * The Default Constructor Required For Hibernate
	 * 
	 */
	public UldResditHistory() {
	}

	public UldResditHistory(UldResditVO uldResditVO) throws SystemException {
		log.entering("UldResdit", "UldResdit");
		populatePK(uldResditVO);
		populateAttributes(uldResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("UldResdit", "UldResdit");
	}

	/**
	 * @author A-3109
	 * @param uldResditVO
	 */
	private void populatePK(UldResditVO uldResditVO) {
		log.entering("UldResditHistory", "populatePK");
		uldResditHistoryPK = new UldResditHistoryPK();
		uldResditHistoryPK.setCompanyCode(uldResditVO.getCompanyCode());
		uldResditHistoryPK.setUldNumber(uldResditVO.getUldNumber());
		uldResditHistoryPK.setEventCode(uldResditVO.getEventCode());
		log.exiting("UldResditHistory", "populatepK");
	}

	/**
	 * @author A-3109
	 * @param uldResditVO
	 * @throws SystemException
	 */
	private void populateAttributes(UldResditVO uldResditVO)
			throws SystemException {
		log.entering("UldResditHistory", "populateAttributes");
		setEventAirport(uldResditVO.getEventAirport());
		setCarrierId(uldResditVO.getCarrierId());
		setFlightNumber(uldResditVO.getFlightNumber());
		setFlightSequenceNumber(uldResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(uldResditVO.getSegmentSerialNumber());
		setResditSent(uldResditVO.getResditSentFlag());
		setEventDate(uldResditVO.getEventDate().toCalendar());
		setUtcEventDate(uldResditVO.getEventDate().toGMTDate().toCalendar());
		setMessageSequenceNumber(uldResditVO.getMessageSequenceNumber());
		setProcessedStatus(uldResditVO.getProcessedStatus());
		setCarditKey(uldResditVO.getCarditKey());
		setPaOrCarrierCode(uldResditVO.getPaOrCarrierCode());
		setUldEventSequenceNumber(uldResditVO.getUldEventSequenceNumber());
		setContainerJourneyId(uldResditVO.getContainerJourneyId());
		setShipperBuiltCode(uldResditVO.getShipperBuiltCode());
		setInterchangeControlReference(uldResditVO
				.getInterchangeControlReference());
		log.exiting("UldResditHistory", "populateAttributes");
	}

	/**
	 * @author A-3109 This method is used to find the DAO's Instance
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
	 * This method is used to return the Instance of the Entity
	 * 
	 * @author A-3109
	 * @param uldResditHistoryPK
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static UldResdit find(UldResditHistoryPK uldResditHistoryPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(UldResdit.class,
				uldResditHistoryPK);

	}

	/**
	 * @author A-3109 This method is used to remove the Entity
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}

}
