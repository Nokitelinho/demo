/*
 * MailResdit.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-3109
 * 
 */
@Entity
@Table(name = "MALRDT")
public class MailResdit {

	private static final String MAIL_OPERATIONS = "mail.operations";

	private static final String MAIL_EVENT_KEY = "MAIL_EVENT_KEY";

	private static final String RDTEVT_KEYTABLE = "MALRDTEVTKEY";

	private Log log = LogFactory.getLogger("MAIL_operations");

	private MailResditPK mailResditPK;

	/**
	 * eventAirport
	 */
	private String eventAirport;

	/**
	 * eventDate
	 */
	private Calendar eventDate;

	/**
	 * carrierId
	 */
	private int carrierId;

	/**
	 * flightNumber
	 */
	private String flightNumber;

	/**
	 * flightSequenceNumber
	 */
	private long flightSequenceNumber;

	/**
	 * segmentSerialNumber
	 */
	private int segmentSerialNumber;

	/**
	 * uldNumber
	 */
	private String uldNumber;

	/**
	 * resditSent
	 */
	private String resditSent;

	/**
	 * processedStatus
	 */
	private String processedStatus;

	/**
	 * messageSequenceNumber
	 */
	private long messageSequenceNumber;

	/**
	 * carditKey
	 */
	private String carditKey;

	/**
	 * paOrCarrierCode
	 */
	private String paOrCarrierCode;

	/**
	 * utcEventDate
	 */
	private Calendar utcEventDate;

	/**
	 * mailEventSequenceNumber
	 */
	private int mailEventSequenceNumber;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * partyIdentifier
	 */
	private String partyIdentifier;

	/**
	 * mailboxID
	 */
	private String mailboxID;
	/**
	 * The interchangeControlReference
	 */
	private String interchangeControlReference;

	/**
	 * reconciliationStatus
	 */
	private String reconciliationStatus;
	
	private String mailIdr;
	
	//Added by A-7540
	private Calendar resditSenttime;
	
	private long messageIdentifier;

	private String senderIdentifier;
	private String recipientIdentifier;
	private String	messageAddressSequenceNumber;

	/**
	 * @return the mailIdr
	 */
	@Column(name = "MALIDR")
	public String getMailIdr() {
		return mailIdr;
	}

	/**
	 * @param mailIdr the mailIdr to set
	 */
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

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

	@Column(name = "PTYIDR")
	public String getPartyIdentifier() {
		return partyIdentifier;
	}

	public void setPartyIdentifier(String partyIdentifier) {
		this.partyIdentifier = partyIdentifier;
	}

	public MailResdit() {
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

	/*
	 * Added by A-2135 for QF CR 1517
	 */

	/**
	 * @return the interchangeControlReference
	 */
	@Column(name = "CNTREFNUM")
	public String getInterchangeControlReference() {
		return interchangeControlReference;
	}

	@Column(name = "MALBOXIDR")
	public String getMailboxID() {
		return mailboxID;
	}

	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
	
	//Added by A-7540
    @Column(name = "SNTTIM")
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getResditSenttime() {
		return resditSenttime;
	}

	public void setResditSenttime(Calendar resditSenttime) {
		this.resditSenttime = resditSenttime;
	}
	/**
	 * 	Getter for messageIdentifier 
	 *	Added by : A-8061 on 28-Jun-2019
	 * 	Used for :
	 */
	@Column(name = "MSGIDR")
	public long getMessageIdentifier() {
		return messageIdentifier;
	}

	public void setMessageIdentifier(long messageIdentifier) {
		this.messageIdentifier = messageIdentifier;
	}
	/**
	 * @return Returns the mailResditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "eventCode", column = @Column(name = "EVTCOD")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public MailResditPK getMailResditPK() {
		return mailResditPK;
	}

	/**
	 * @param mailResditPK
	 *            The mailResditPK to set.
	 */
	public void setMailResditPK(MailResditPK mailResditPK) {
		this.mailResditPK = mailResditPK;
	}

	@Column(name = "SNDIDR")
	public String getSenderIdentifier() {
		return senderIdentifier;
	}

	public void setSenderIdentifier(String senderIdentifier) {
		this.senderIdentifier = senderIdentifier;
	}

	@Column(name = "RCTIDR ")
	public String getRecipientIdentifier() {
		return recipientIdentifier;
	}

	public void setRecipientIdentifier(String recipientIdentifier) {
		this.recipientIdentifier = recipientIdentifier;
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
	 * @param interchangeControlReference
	 *            the interchangeControlReference to set
	 */
	public void setInterchangeControlReference(
			String interchangeControlReference) {
		this.interchangeControlReference = interchangeControlReference;
	}

	public MailResdit(MailResditVO mailResditVO) throws SystemException {
		log.entering("MailResdit", "init");
		populatePK(mailResditVO);
		populateAttributes(mailResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MailResdit", "init");
	}

	/**
	 * A-3109
	 * 
	 * @param mailResditVO
	 * @throws SystemException 
	 */
	private void populatePK(MailResditVO mailResditVO) throws SystemException {
		log.entering("MailResdit", "populatePK");
		log.log(Log.FINE, "THE MAILRESDIT VO>>>>>>>>>>", mailResditVO);
		mailResditPK = new MailResditPK();
		mailResditPK.setCompanyCode(mailResditVO.getCompanyCode());
	//	mailResditPK.setMailSequenceNumber(findMailBagSequenceNumberFromMailIdr(mailResditVO.getMailId(),mailResditVO.getCompanyCode()));
		mailResditPK.setMailSequenceNumber(mailResditVO.getMailSequenceNumber() > 0 ?
				mailResditVO.getMailSequenceNumber(): findMailBagSequenceNumberFromMailIdr(mailResditVO.getMailId(),mailResditVO.getCompanyCode()) );   
	
		mailResditPK.setEventCode(mailResditVO.getEventCode());
		log.exiting("MailResdit", "populatepK");
	}
    public static long findMailBagSequenceNumberFromMailIdr(String mailIdr,String companyCode) throws SystemException{
    	return constructDAO().findMailSequenceNumber(mailIdr, companyCode);
    	
    }

	/**
	 * A-3109
	 * 
	 * @param mailResditVO
	 * @throws SystemException
	 */
	private void populateAttributes(MailResditVO mailResditVO)
			throws SystemException { 
		log.entering("MailResdit", "populateAttributes");
		setCarrierId(mailResditVO.getCarrierId());
		setFlightNumber(mailResditVO.getFlightNumber());
		setFlightSequenceNumber(mailResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(mailResditVO.getSegmentSerialNumber());
		setEventAirport(mailResditVO.getEventAirport());
		/*if (mailResditVO.getEventDate() != null) {
			//currently mailResditVO.getEventDate() is not coming based on the airport 
			LocalDate localEventDate = new LocalDate(eventAirport, Location.ARP, true);
			localEventDate.setDate(mailResditVO.getEventDate().toDisplayFormat());
			localEventDate.setTime(mailResditVO.getEventDate().toDisplayTimeOnlyFormat());
			setEventDate(localEventDate);
			setUtcEventDate(localEventDate.toGMTDate());
		}
 		setUldNumber(mailResditVO.getUldNumber());*/
 		
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
		setMailEventSequenceNumber(generateMailEventSeqNumber(
				mailResditVO.getCompanyCode(), mailResditVO.getMailId()));
		setLastUpdateUser(mailResditVO.getLastUpdateUser());
			String mailbox = findMailboxFromCardit(mailResditVO);
			if(mailbox != null && mailbox.trim().length() >0){
				setMailboxID(mailbox);
			}else{
		setMailboxID(mailResditVO.getMailboxID());
			}
		setPartyIdentifier(mailResditVO.getPartyIdentifier());
		setMailIdr(mailResditVO.getMailId());
		
          setMessageIdentifier(mailResditVO.getMessageIdentifier());

          if(mailResditVO.getMessageAddressSequenceNumber()!=null) {
        	  setMessageAddressSequenceNumber(mailResditVO.getMessageAddressSequenceNumber());
          }
		log.exiting("MailResdit", "populateAttributes");
	}

	/**
	 * @param companyCode
	 * @param mailId
	 * @return
	 * @throws SystemException
	 */
	private int generateMailEventSeqNumber(String companyCode, String mailId)
			throws SystemException {
		log.entering("MailResdit", "generateSerialNumber");
		Criterion criterion = KeyUtils.getCriterion(companyCode,
				MAIL_EVENT_KEY, mailId);
		criterion.setStartAt("1");
		criterion.setName(RDTEVT_KEYTABLE);
		return Integer.parseInt(KeyUtilInstance.getInstance().getKey(criterion));
	}

	/**
	 * @param mailResditPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailResdit find(MailResditPK mailResditPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(MailResdit.class,
				mailResditPK);

	}

	/**
	 * This method is used to remove the Instance of the Entity
	 * 
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
	
	
	/**
	 * TODO Purpose
	 * May 23, 2007, a-1739
	 * @param mailbagVO
	 * @param resditEvent
	 * @return
	 * @throws SystemException
	 */
	public static List<MailResdit> findMailResditsForEvent(MailbagVO mailbagVO,
			String resditEvent) throws SystemException {
		try {
			return constructObjectDAO().findMailResditForEvent(mailbagVO, resditEvent);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	
	/**
	 *
	 * @return
	 * @throws SystemException
	 */
   private static MailtrackingDefaultsObjectInterface constructObjectDAO() throws SystemException {
   	try {
   		return PersistenceController.getEntityManager().getObjectQueryDAO(
   				MAIL_OPERATIONS);
   	} catch(PersistenceException ex) {
   		throw new SystemException (ex.getMessage(), ex);
   	}
   }
   
   
	/**
	 * @authoer a-2521
	 * For find flight details of a resdit
	 * Modified for multiple resdit events
	 * @param Collection<mailResditVO>
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String,Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
			Collection<MailResditVO> mailResditVOs)	throws SystemException {
		try {
		return constructDAO().findResditFlightDetailsForMailbagEvents(mailResditVOs);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	
	/**
	 * @author A-1739 This method is used to findFlaggedResditSeqNum
	 * @param mailResditVO
	 * @param isSentCheckNeeded
	 * @return
	 * @throws SystemException
	 */

	/*
	 * Added By Karthick V a-1936
	 *
	 *  Note:
	 *   When the event is  RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   When the event is  HANDED OVER RECEIVED
	 *   Flag the Resit Event RECEIVED Only when neither  of tbe (Received or HandOver Received) is already Flagged.
	 *
	 *   The Method checks wether the Resdit is already flagged for the Particular Event ..If the
	 *   DependantEventCode in MailResditVo is also being  present then  this method checks wether there is any Resdit
	 *   flagged for either the Resdit Event or the Dependant Event and Returns True if flagged for either of these or all
	 *   Else
	 *   False
	 */
	public static boolean  checkResditExists(MailResditVO mailResditVO,
			boolean isSentCheckNeeded) throws SystemException {
		try {
			return constructDAO().checkResditExists(mailResditVO,
					isSentCheckNeeded);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

	}

	
	/**
	 * 
	 * 	Method		:	MailResdit.checkResditExists
	 *	Added by 	:	A-5201 on 21-Nov-2014
	 * 	Used for 	:
	 *	Parameters	:	@param mailResditVO
	 *	Parameters	:	@param isSentCheckNeeded
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	boolean
	 */
	public static boolean  checkResditExistsFromReassign(MailResditVO mailResditVO,
			boolean isSentCheckNeeded) throws SystemException {
		try {
			return constructDAO().checkResditExistsFromReassign(mailResditVO,
					isSentCheckNeeded);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
	/**
	 * 
	 * @param mailbagVOs
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String, String> findPAForMailbags(
			Collection<MailbagVO> mailbagVOs)	throws SystemException {
		try {
			return constructDAO().findPAForMailbags(mailbagVOs);
			} catch(PersistenceException exception) {
				throw new SystemException(exception.getMessage(), exception);
			}		
	}
	
	/**
	 * @authoer a-1739
	 * For find flight details of a resdit
	 * @param mailResditVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailResditVO> findResditFlightDetailsForMailbag(MailResditVO mailResditVO)
	throws SystemException {
		try {
		return constructDAO().findResditFlightDetailsForMailbag(mailResditVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	
    /**
    *
    * @param mailResditVO
    * @return
    * @throws SystemException
    */
	public static List<MailResdit> findMailResdits(
			MailResditVO mailResditVO) throws SystemException {
		try {
			return constructObjectDAO().findMailResdits(
					mailResditVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	 /** Added by A-5526 for CRQ ICRD-118163
		 * Method to fetch all mail resdits
		 * @param companyCode
		 * @param mailbagId
		 * @return
		 * @throws SystemException
		 */
		public static Collection<MailResdit> findAllResditDetails(String companyCode,
				long mailbagId) throws SystemException {
			try {
				return mailtrackingDefaultsObjectInterface()
						.findAllResditDetails(companyCode,mailbagId);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage());
			}
		}
		/**
		 * @author Added by A-5526 for CRQ ICRD-118163
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		private static MailtrackingDefaultsObjectInterface mailtrackingDefaultsObjectInterface()
		throws SystemException, PersistenceException {
			return PersistenceController.getEntityManager().getObjectQueryDAO("mail.operations");
		}
		/**
		 * 
		 * @param companyCode
		 * @param controlReferenceNumber
		 * @return
		 * @throws SystemException
		 * @author A-2572
		 */
		public static Collection<MailResdit> findMailResditForResditStatusUpd(
				String companyCode, String controlReferenceNumber)
				throws SystemException {
			try {
				return constructObjectDAO().findMailResditForResditStatusUpd(companyCode,controlReferenceNumber);
			} catch(PersistenceException exception) {
				throw new SystemException(exception.getMessage(), exception);
			}
		}
		  /**
		  * For converting mailResdit to mailResditVO
		  * @param mailResdit
		  * @return mailResditVO
		  */
		 public static MailResditVO covertToMailResditVo(
				 MailResdit mailResdit) {
			MailResditVO mailResditVO = new MailResditVO();
			mailResditVO.setCompanyCode(
					mailResdit.getMailResditPK().getCompanyCode());
	/*		mailResditVO.setMailId(
					mailResdit.getMailResditPK().getMailId());*/
			mailResditVO.setEventCode(
					mailResdit.getMailResditPK().getEventCode());
			mailResditVO.setEventAirport(mailResdit.getEventAirport());
			mailResditVO.setCarrierId(mailResdit.getCarrierId());
			mailResditVO.setFlightNumber(mailResdit.getFlightNumber());
			mailResditVO.setFlightSequenceNumber(mailResdit
					.getFlightSequenceNumber());
			mailResditVO.setSegmentSerialNumber(mailResdit
					.getSegmentSerialNumber());
			mailResditVO.setUldNumber(mailResdit.getUldNumber());
			mailResditVO.setResditSentFlag(mailResdit.getResditSent());
			mailResditVO.setEventDate(
					new LocalDate(mailResdit.getEventAirport(),
							Location.ARP, mailResdit.getEventDate(), true));
			mailResditVO.setMessageSequenceNumber(mailResdit
					.getMessageSequenceNumber());
			mailResditVO.setProcessedStatus(mailResdit.getProcessedStatus());
			mailResditVO.setCarditKey(mailResdit.getCarditKey());
			mailResditVO.setPaOrCarrierCode(mailResdit.getPaOrCarrierCode());
			mailResditVO.setMailEventSequenceNumber(mailResdit
					.getMailEventSequenceNumber());
			mailResditVO.setLastUpdateUser(mailResdit.getLastUpdateUser());
			mailResditVO.setInterchangeControlReference(
					mailResdit.getInterchangeControlReference());
			mailResditVO.setReconciliationStatus(
					mailResdit.getReconciliationStatus());
			return mailResditVO;
		 }
		 
		 /**
		  * 
		  * @param resditEventVO
		  * @return
		  * @throws SystemException
		  */
		 public static Collection<TransportInformationVO> findTransportDetailsForMailbag(
					ResditEventVO resditEventVO) throws SystemException {
				try {
					return constructDAO().findTransportDetailsForMailbag(resditEventVO);
				} catch (PersistenceException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
			}
		 
		 /**
		  * 
		  * @param resditEventVO
		  * @return
		  * @throws SystemException
		  */
		 public static Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(
					ResditEventVO resditEventVO) throws SystemException {
				try {
					return constructDAO().findMailbagDetailsForResdit(resditEventVO);
				} catch (PersistenceException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
			}
		 
		 /**
		  * 
		  * @param resditEventVO
		  * @return
		  * @throws SystemException
		  */
		 public static Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(
					ResditEventVO resditEventVO) throws SystemException {
				try {
					return constructDAO().findMailbagDetailsForXXResdit(resditEventVO);
				} catch (PersistenceException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
			}
		 /**
		  * 
		  * @param resditVO
		  * @return
		  * @throws SystemException
		  */
		 private String findMailboxFromCardit(MailResditVO resditVO) throws SystemException{
			 	try{
					return constructDAO().findMailboxId(resditVO);
				} catch (PersistenceException ex) {
					return "";
				}
			}
		@Column(name = "MSGADDSEQNUM")
       public String getMessageAddressSequenceNumber() {
			return messageAddressSequenceNumber;
		}
		public void setMessageAddressSequenceNumber(String messageAddressSequenceNumber) {
			this.messageAddressSequenceNumber = messageAddressSequenceNumber;
			}


}
