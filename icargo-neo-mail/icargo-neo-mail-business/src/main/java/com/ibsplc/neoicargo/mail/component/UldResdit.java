package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.ResditEventVO;
import com.ibsplc.neoicargo.mail.vo.UldResditVO;
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
 * @author A-3109
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(UldResditPK.class)
@SequenceGenerator(name = "MALULDRDTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALULDRDT_SEQ")
@Table(name = "MALULDRDT")
public class UldResdit extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String ULD_EVENT_KEY = "ULD_EVENT_KEY";

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Id
	@Column(name = "EVTCOD")
	private String eventCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALULDRDTSEQ")
	@Column(name = "SEQNUM")
	private long sequenceNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "EVTPRT")
	private String eventAirport;
	/** 
	* totalWeight
	*/
	@Column(name = "EVTDAT")
	private LocalDateTime eventDate;
	/** 
	* totalWeight
	*/
	@Column(name = "FLTCARIDR")
	private int carrierId;
	/** 
	* totalWeight
	*/
	@Column(name = "FLTNUM")
	private String flightNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "RDTSND")
	private String resditSent;
	/** 
	* totalWeight
	*/
	@Column(name = "PROSTA ")
	private String processedStatus;
	/** 
	* totalWeight
	*/
	@Column(name = "MSGSEQNUM ")
	private long messageSequenceNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "ULDEVTSEQ")
	private int uldEventSequenceNumber;
	/** 
	* totalWeight
	*/
	@Column(name = "CDTKEY")
	private String carditKey;
	/** 
	* totalWeight
	*/
	@Column(name = "POACARCOD")
	private String paOrCarrierCode;
	/** 
	* totalWeight
	*/
	@Column(name = "CONJRNIDR")
	private String containerJourneyId;
	/** 
	* shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB ULD.
	*/
	@Column(name = "POACOD")
	private String shipperBuiltCode;
	/** 
	*/
	@Column(name = "EVTDATUTC")
	private LocalDateTime utcEventDate;
	/** 
	* The interchangeControlReference
	*/
	@Column(name = "CNTREFNUM")
	private String interchangeControlReference;
	/**
	* The Default Constructor Required For Hibernate
	*/
	public UldResdit() {
	}

	public UldResdit(UldResditVO uldResditVO) {
		log.debug("UldResdit" + " : " + "UldResdit" + " Entering");
		populatePK(uldResditVO);
		populateAttributes(uldResditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("UldResdit" + " : " + "UldResdit" + " Exiting");
	}

	/** 
	* @author A-3109
	* @param uldResditVO
	*/
	private void populatePK(UldResditVO uldResditVO) {
		log.debug("UldResdit" + " : " + "populatePK" + " Entering");
		log.debug("" + "THE UldResditVO >>>>>>>>>>" + " " + uldResditVO);
		this.setCompanyCode(uldResditVO.getCompanyCode());
		this.setUldNumber(uldResditVO.getUldNumber());
		this.setEventCode(uldResditVO.getEventCode());
		log.info("" + "THE getCompanyCode is" + " " + this.getCompanyCode());
		log.info("" + "THE getEventCode is" + " " + this.getEventCode());
		log.info("" + "THE getSequenceNumber is" + " " + this.getSequenceNumber());
		log.info("" + "THE getUldNumber is" + " " + this.getUldNumber());
		log.debug("UldResdit" + " : " + "populatepK" + " Exiting");
	}

	/** 
	* @author A-3109
	* @param uldResditVO
	* @throws SystemException
	*/
	private void populateAttributes(UldResditVO uldResditVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("UldResdit" + " : " + "populateAttributes" + " Entering");
		setCarrierId(uldResditVO.getCarrierId());
		setFlightNumber(uldResditVO.getFlightNumber());
		setFlightSequenceNumber(uldResditVO.getFlightSequenceNumber());
		setSegmentSerialNumber(uldResditVO.getSegmentSerialNumber());
		setEventAirport(uldResditVO.getEventAirport());
		setEventDate(uldResditVO.getEventDate().toLocalDateTime());
		setUtcEventDate(localDateUtil.toUTCTime(uldResditVO.getEventDate()).toLocalDateTime());
		setResditSent(uldResditVO.getResditSentFlag());
		setProcessedStatus(uldResditVO.getProcessedStatus());
		setMessageSequenceNumber(uldResditVO.getMessageSequenceNumber());
		setCarditKey(uldResditVO.getCarditKey());
		setPaOrCarrierCode(uldResditVO.getPaOrCarrierCode());
		setUldEventSequenceNumber(generateULDEventSeqNumber(uldResditVO.getCompanyCode(), uldResditVO.getUldNumber()));
		setContainerJourneyId(uldResditVO.getContainerJourneyId());
		setShipperBuiltCode(uldResditVO.getShipperBuiltCode());
		log.debug("UldResdit" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @param companyCode
	* @param uldNumber
	* @return
	* @throws SystemException
	*/
	private int generateULDEventSeqNumber(String companyCode, String uldNumber) {
		//TODO: Neo to correct the below code
//		log.debug("UldResdit" + " : " + "generateSerialNumber" + " Entering");
//		criterion.setStartAt("1");
//		return Integer.parseInt(KeyUtils.getKey());
		return 0;
	}

	/** 
	* @author A-3109 This method is used to find the DAO's Instance
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

	/** 
	* This method is used to return the Instance of the Entity
	* @author A-3109
	* @param uldResditPK
	* @throws SystemException
	* @throws FinderException
	*/
	public static UldResdit find(UldResditPK uldResditPK) throws FinderException {
		return PersistenceController.getEntityManager().find(UldResdit.class, uldResditPK);
	}

	/** 
	* @author A-3109 This method is used to remove the Entity
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
	* For finding status of a ULDRESDit event Jun 3, 2008, a-1739
	* @param uldResditVO
	* @return
	* @throws SystemException
	*/
	public static Collection<UldResditVO> findULDResditStatus(UldResditVO uldResditVO) {
		try {
			return constructDAO().findULDResditStatus(uldResditVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @author a-1936Added By Karthick V as the part of the NCA Mail Tracking CR.. This method is used to find the Mail Resdit Entity as such
	* @return
	* @throws SystemException
	*/
	public static List<UldResdit> findUldResdit(UldResditVO uldResditVO, String eventCode) {
		try {
			return constructObjectDAO().findUldResdit(uldResditVO, eventCode);
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
	* @param resditEventVO
	* @return
	* @throws SystemException
	*/
	public static Collection<TransportInformationVO> findTransportDetailsForULD(ResditEventVO resditEventVO) {
		try {
			return constructDAO().findTransportDetailsForULD(resditEventVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
	}

	/** 
	* @param resditEventVO
	* @return
	* @throws SystemException
	*/
	public static Collection<ContainerInformationVO> findULDDetailsForResdit(ResditEventVO resditEventVO) {
		try {
			return constructDAO().findULDDetailsForResdit(resditEventVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
	}

	/** 
	* @param resditEventVO
	* @return
	* @throws SystemException
	*/
	public static Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(ResditEventVO resditEventVO) {
		try {
			return constructDAO().findULDDetailsForResditWithoutCardit(resditEventVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
	}
	public static HashMap<String, String> findPAForShipperbuiltULDs(
			Collection<UldResditVO> uldResditVOs, boolean isFromCardit)	throws SystemException {
		try {
			return constructDAO().findPAForShipperbuiltULDs (uldResditVOs, isFromCardit);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
}
