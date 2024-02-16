package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/** 
 * @author A-5991Maintains history for a mailbag. This entity stores all details regarding the mailbag transactions. 
 */
@Setter
@Getter
@Entity
@Slf4j
@IdClass(MailbagHistoryPK.class)
@SequenceGenerator(name = "MALHISSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALHIS_SEQ")
@Table(name = "MALHIS_VW_BASE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MailbagHistory extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";

	@Id
	@Transient
	private String companyCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALHISSEQ")
	@Column(name = "MALHISIDR")
	private int historySequenceNumber;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;
	@Column(name = "SCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scanDate;
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	@Column(name = "SCNPRT")
	private String scannedPort;
	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "CONTYP")
	private String containerType;
	@Column(name = "SCNUSR")
	private String scanUser;
	@Column(name = "FLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime flightDate;
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	@Column(name = "POU")
	private String pou;
	@Column(name = "UTCSCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime utcScandate;
	@Column(name = "MSGTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime messageTime;
	@Column(name = "MSGTIMUTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime messageTimeUTC;
//	private String paBuiltUldFlag;
	@Version
	@Column(name = "CRTTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime creationTime;
	@Column(name = "MALSRC")
	private String mailSource;
	/** 
	* The mailStatus
	*/
	@Column(name = "MALSTA")
	private String mailStatus;
	/** 
	* Added by A-2135 for QFCR 1517
	*/
	@Column(name = "CNTREFNUM")
	private String interchangeControlReference;
	@Column(name = "ADDINF")
	private String additionalInfo;
		/**
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @author A-5991
	* @param mailbaghistorypk
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static MailbagHistory findMailbagHistory(MailbagHistoryPK mailbaghistorypk) throws FinderException {
		return PersistenceController.getEntityManager().find(MailbagHistory.class, mailbaghistorypk);
	}

	/** 
	* Find the arrival history of a mailbag Sep 12, 2007, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public static MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO) {
		try {
			return constructDAO().findArrivalDetailsForMailbag(mailbagVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
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
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public static boolean isMailbagAlreadyArrived(MailbagVO mailbagVO) {
		try {
			return constructDAO().isMailbagAlreadyArrived(mailbagVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* Method		:	MailbagHistory.constructMailbagHistoryPK Added by 	:	A-8061 on 07-Apr-2020 Used for 	: Parameters	:	@param mailhistoryvo Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MailbagHistoryPK
	*/
	public static MailbagHistoryPK constructMailbagHistoryPK(MailbagHistoryVO mailhistoryvo) {
		MailbagHistoryPK mailbagHistoryPK = new MailbagHistoryPK();
		mailbagHistoryPK.setCompanyCode(mailhistoryvo.getCompanyCode());
		mailbagHistoryPK.setHistorySequenceNumber(mailhistoryvo.getHistorySequenceNumber());
		mailbagHistoryPK
				.setMailSequenceNumber(mailhistoryvo.getMailSequenceNumber() > 0 ? mailhistoryvo.getMailSequenceNumber()
						: Mailbag.findMailSequenceNumber(mailhistoryvo.getMailbagId(), mailhistoryvo.getCompanyCode()));
		return mailbagHistoryPK;
	}

	/** 
	* @author A-9619
	* @return company specific mail controller
	* @throws SystemException IASCB-55196
	*/
	public static MailbagHistory getInstance() {
		return ContextUtil.getInstance().getBean(MailbagHistory.class);
	}

	public MailbagHistory(MailbagHistoryVO mailbagHistoryVO)
			throws SystemException {
		populatePK(mailbagHistoryVO);
		populateAttributes(mailbagHistoryVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}

	}

	private void populatePK(MailbagHistoryVO mailbagHistoryVO) throws SystemException {
			this.setCompanyCode(mailbagHistoryVO.getCompanyCode());
			this.setMailSequenceNumber(mailbagHistoryVO.getMailSequenceNumber());
	}

	/**
	 * A-5991
	 *
	 * @param mailbagHistoryVO
	 */
	public void populateAttributes(MailbagHistoryVO mailbagHistoryVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		setCarrierId(mailbagHistoryVO.getCarrierId());
		setContainerNumber(mailbagHistoryVO.getContainerNumber());
		setContainerType(mailbagHistoryVO.getContainerType());
		setFlightNumber(mailbagHistoryVO.getFlightNumber());
		setFlightSequenceNumber(mailbagHistoryVO.getFlightSequenceNumber());
		setScanDate(mailbagHistoryVO.getScanDate().toLocalDateTime());
		//UTC scan should be populated from scan time, review by Shambu
		if(mailbagHistoryVO.getScanDate()!=null){//added by A-8353 for IASCB-52564
			setUtcScandate(localDateUtil.toUTCTime(mailbagHistoryVO.getScanDate()).toLocalDateTime());
		}
		else{
			setUtcScandate(LocalDateTime.now(ZoneOffset.UTC));
		}

		setScannedPort(mailbagHistoryVO.getScannedPort());
		setSegmentSerialNumber(mailbagHistoryVO.getSegmentSerialNumber());
		setMailStatus(mailbagHistoryVO.getMailStatus());
		setScanUser(mailbagHistoryVO.getScanUser());
		setCarrierCode(mailbagHistoryVO.getCarrierCode());
		setPou(mailbagHistoryVO.getPou());
		// setPaBuiltUldFlag(mailbagHistoryVO.getPaBuiltFlag());
		if (mailbagHistoryVO.getFlightDate() != null) {
			setFlightDate(mailbagHistoryVO.getFlightDate().toLocalDateTime());
		}

		if (mailbagHistoryVO.getMessageTime() != null) {
			setMessageTime(mailbagHistoryVO.getMessageTime().toLocalDateTime());

			setMessageTimeUTC(localDateUtil.toUTCTime(mailbagHistoryVO.getMessageTime()
					).toLocalDateTime());
		}
		setInterchangeControlReference(mailbagHistoryVO
				.getInterchangeControlReference());

		setCreationTime(LocalDateTime.now(ZoneOffset.UTC));// Added
		// for
		// ICRD-156218
		if(mailbagHistoryVO.getMailSource()!=null){
			setMailSource(mailbagHistoryVO.getMailSource());// Added for ICRD-156218
		}else{
			setMailSource(MailConstantsVO.FRM_JOB);
		}

		setAdditionalInfo(mailbagHistoryVO.getAdditionalInfo());//Added by A-7871 for ICRD-240184
		LoginProfile logonAttributes = ContextUtil.getInstance().callerLoginProfile();
		setLastUpdatedUser(logonAttributes.getUserId());
		setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));//Added by A-7929 as part of IASCB-35577

	}
	public MailbagHistory() {
	}
	/**
	 * @param mailbagHistoryVO
	 * @throws SystemException
	 */
	private void setMailbagHistoryAttributes(MailbagHistoryVO mailbagHistoryVO) {
		populatePK(mailbagHistoryVO);
		populateAttributes(mailbagHistoryVO);
	}

	public MailbagHistory persistMailbagHistory(MailbagHistoryVO mailbagHistoryVO)
			throws SystemException {

		populatePK(mailbagHistoryVO);
		populateAttributes(mailbagHistoryVO);
		try {
			PersistenceController.getEntityManager().persist(this);

		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		return this;
	}

	public static MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO) {
			return constructDAO().findLatestFlightDetailsOfMailbag(mailbagVO);
	}

}
