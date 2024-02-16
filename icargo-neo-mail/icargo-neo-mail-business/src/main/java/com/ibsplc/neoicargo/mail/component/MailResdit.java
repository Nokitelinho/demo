package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ResditEventVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/** 
 * TODO Add the purpose of this class
 * @author A-3109
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailResditPK.class)
@SequenceGenerator(name = "MALRDTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALRDT_SEQ")
@Table(name = "MALRDT")
public class MailResdit extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String MAIL_EVENT_KEY = "MAIL_EVENT_KEY";
	private static final String RDTEVT_KEYTABLE = "MALRDTEVTKEY";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;
	@Id
	@Column(name = "EVTCOD")
	private String eventCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALRDTSEQ")
	@Column(name = "SEQNUM")
	private long sequenceNumber;
	/** 
	* eventAirport
	*/
	@Column(name = "EVTPRT")
	private String eventAirport;
	/** 
	* eventDate
	*/
	@Column(name = "EVTDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime eventDate;
	/** 
	* carrierId
	*/
	@Column(name = "FLTCARIDR")
	private int carrierId;
	/** 
	* flightNumber
	*/
	@Column(name = "FLTNUM")
	private String flightNumber;
	/** 
	* flightSequenceNumber
	*/
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	/** 
	* segmentSerialNumber
	*/
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	/** 
	* uldNumber
	*/
	@Column(name = "CONNUM")
	private String uldNumber;
	/** 
	* resditSent
	*/
	@Column(name = "RDTSND")
	private String resditSent;
	/** 
	* processedStatus
	*/
	@Column(name = "PROSTA ")
	private String processedStatus;
	/** 
	* messageSequenceNumber
	*/
	@Column(name = "MSGSEQNUM ")
	private long messageSequenceNumber;
	/** 
	* carditKey
	*/
	@Column(name = "CDTKEY")
	private String carditKey;
	/** 
	* paOrCarrierCode
	*/
	@Column(name = "POACARCOD")
	private String paOrCarrierCode;
	/** 
	* utcEventDate
	*/
	@Column(name = "EVTDATUTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime utcEventDate;
	/** 
	* mailEventSequenceNumber
	*/
	@Column(name = "MALEVTSEQ")
	private int mailEventSequenceNumber;
	/**
	* partyIdentifier
	*/
	@Column(name = "PTYIDR")
	private String partyIdentifier;
	/** 
	* mailboxID
	*/
	@Column(name = "MALBOXIDR")
	private String mailboxID;
	/** 
	* The interchangeControlReference
	*/
	@Column(name = "CNTREFNUM")
	private String interchangeControlReference;
	/** 
	* reconciliationStatus
	*/
	@Column(name = "RCNSTA")
	private String reconciliationStatus;
	@Column(name = "MALIDR")
	private String mailIdr;
	@Column(name = "SNTTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime resditSenttime;
	@Column(name = "MSGIDR")
	private long messageIdentifier;
	@Column(name = "SNDIDR")
	private String senderIdentifier;
	@Column(name = "RCTIDR ")
	private String recipientIdentifier;

	public MailResdit(){

	}
	/** 
	* @author A-3109 This method is used to remove the DAOS Instance
	* @return
	* @throws SystemException
	*/
	public static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}

	public MailResdit(MailResditVO mailResditVO) {

		populatePK(mailResditVO);
		populateAttributes(mailResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}

	}

	/** 
	* A-3109
	* @param mailResditVO
	* @throws SystemException 
	*/
	private void populatePK(MailResditVO mailResditVO) {

		log.debug("" + "THE MAILRESDIT VO>>>>>>>>>>" + " " + mailResditVO);
		this.setCompanyCode(mailResditVO.getCompanyCode());
		this.setMailSequenceNumber(mailResditVO.getMailSequenceNumber() > 0
				? mailResditVO.getMailSequenceNumber()
				: findMailBagSequenceNumberFromMailIdr(mailResditVO.getMailId(), mailResditVO.getCompanyCode()));
		this.setEventCode(mailResditVO.getEventCode());
		log.debug("MailResdit" + " : " + "populatepK" + " Exiting");
	}

	public static long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailIdr, companyCode);
	}

	/** 
	* A-3109
	* @param mailResditVO
	* @throws SystemException
	*/
	private void populateAttributes(MailResditVO mailResditVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("MailResdit" + " : " + "populateAttributes" + " Entering");
		setCarrierId(mailResditVO.getCarrierId());
		setFlightNumber(mailResditVO.getFlightNumber());
		setFlightSequenceNumber(mailResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(mailResditVO.getSegmentSerialNumber());
		setEventAirport(mailResditVO.getEventAirport());
		if (mailResditVO.getEventDate() != null) {
			setEventDate(mailResditVO.getEventDate().toLocalDateTime());
			setUtcEventDate(localDateUtil.toUTCTime(mailResditVO.getEventDate()).toLocalDateTime());
		}
		setUldNumber(mailResditVO.getUldNumber());
		setResditSent(mailResditVO.getResditSentFlag());
		setProcessedStatus(mailResditVO.getProcessedStatus());
		setMessageSequenceNumber(mailResditVO.getMessageSequenceNumber());
		setCarditKey(mailResditVO.getCarditKey());
		setPaOrCarrierCode(mailResditVO.getPaOrCarrierCode());
		setMailEventSequenceNumber(generateMailEventSeqNumber(mailResditVO.getCompanyCode(), mailResditVO.getMailId()));
		//TODO:Neo to check
		//setLastUpdateUser(mailResditVO.getLastUpdateUser());
		String mailbox = findMailboxFromCardit(mailResditVO);
		if (mailbox != null && mailbox.trim().length() > 0) {
			setMailboxID(mailbox);
		} else {
			setMailboxID(mailResditVO.getMailboxID());
		}
		setPartyIdentifier(mailResditVO.getPartyIdentifier());
		setMailIdr(mailResditVO.getMailId());
		setMessageIdentifier(mailResditVO.getMessageIdentifier());
		log.debug("MailResdit" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @param companyCode
	* @param mailId
	* @return
	* @throws SystemException
	*/
	private int generateMailEventSeqNumber(String companyCode, String mailId) {
		log.debug("MailResdit" + " : " + "generateSerialNumber" + " Entering");
		//TODO: Neo to correct
//		criterion.setStartAt("1");
//		criterion.setName(RDTEVT_KEYTABLE);
//		return Integer.parseInt(KeyUtilInstance.getInstance().getKey());
		return 0;
	}

	/** 
	* @param mailResditPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailResdit find(MailResditPK mailResditPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MailResdit.class, mailResditPK);
	}

	/** 
	* This method is used to remove the Instance of the Entity
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* TODO Purpose May 23, 2007, a-1739
	* @param mailbagVO
	* @param resditEvent
	* @return
	* @throws SystemException
	*/
	public static List<MailResdit> findMailResditsForEvent(MailbagVO mailbagVO, String resditEvent) {
		try {
			return constructObjectDAO().findMailResditForEvent(mailbagVO, resditEvent);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @return
	* @throws SystemException
	*/
	private static MailOperationsObjectInterface constructObjectDAO() {
		try {
			return PersistenceController.getEntityManager().getObjectQueryDAO(MAIL_OPERATIONS);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* @authoer a-2521For find flight details of a resdit Modified for multiple resdit events
	* @return
	* @throws SystemException
	*/
	public static HashMap<String, Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
			Collection<MailResditVO> mailResditVOs) {
		try {
			return constructDAO().findResditFlightDetailsForMailbagEvents(mailResditVOs);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @author A-1739 This method is used to findFlaggedResditSeqNum
	* @param mailResditVO
	* @param isSentCheckNeeded
	* @return
	* @throws SystemException
	*/
	public static boolean checkResditExists(MailResditVO mailResditVO, boolean isSentCheckNeeded) {
		try {
			return constructDAO().checkResditExists(mailResditVO, isSentCheckNeeded);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* Method		:	MailResdit.checkResditExists Added by 	:	A-5201 on 21-Nov-2014 Used for 	: Parameters	:	@param mailResditVO Parameters	:	@param isSentCheckNeeded Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	boolean
	*/
	public static boolean checkResditExistsFromReassign(MailResditVO mailResditVO, boolean isSentCheckNeeded) {
		try {
			return constructDAO().checkResditExistsFromReassign(mailResditVO, isSentCheckNeeded);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @param mailbagVOs
	* @return
	* @throws SystemException
	*/
	public static HashMap<String, String> findPAForMailbags(Collection<MailbagVO> mailbagVOs) {
		try {
			return constructDAO().findPAForMailbags(mailbagVOs);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* Added by A-5526 for CRQ ICRD-118163 Method to fetch all mail resdits
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public static Collection<MailResdit> findAllResditDetails(String companyCode, long mailbagId) {
		try {
			return mailtrackingDefaultsObjectInterface().findAllResditDetails(companyCode, mailbagId);
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
	private static MailOperationsObjectInterface mailtrackingDefaultsObjectInterface()
			throws PersistenceException {
		return PersistenceController.getEntityManager().getObjectQueryDAO("mail.operations");
	}

	/** 
	* @param resditVO
	* @return
	* @throws SystemException
	*/
	private String findMailboxFromCardit(MailResditVO resditVO) {
		try {
			return constructDAO().findMailboxId(resditVO);
		} catch (PersistenceException ex) {
			return "";
		}
	}
	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(ResditEventVO resditEventVO) {
		try {
			return constructDAO().findMailbagDetailsForResdit(resditEventVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(ResditEventVO resditEventVO) {
		try {
			return constructDAO().findMailbagDetailsForXXResdit(resditEventVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public static Collection<MailResditVO> findResditFlightDetailsForMailbag(MailResditVO mailResditVO)
			throws SystemException {
		try {
			return constructDAO().findResditFlightDetailsForMailbag(mailResditVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	public static List<MailResdit> findMailResdits(
			MailResditVO mailResditVO) throws SystemException {
		try {
			return constructObjectDAO().findMailResdits(
					mailResditVO);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
}
