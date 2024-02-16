package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.MLDDetailVO;
import com.ibsplc.neoicargo.mail.vo.MLDMasterVO;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/** 
 * @author A-5526
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MLDMessageMasterPK.class)
@SequenceGenerator(name = "MALMLDMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMLDMST_SEQ")
@Table(name = "MALMLDMST")
public class MLDMessageMaster extends BaseEntity implements Serializable {
	private static final String mail_operations = "mail.operations";

	@Id
	@Transient
	private String companyCode;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMLDMSTSEQ")
	@Column(name = "SERNUM")
	private int serialNumber;

	@Column(name = "TXNTIMUTC")
	private LocalDateTime transactionTimeUTC;
	@Column(name = "OUBFLTDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime flightOperationDateOub;
	@Column(name = "INBFLTDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime flightOperationDateInb;
	@Column(name = "OUBEVTTIM")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime eventTimeOub;
	@Column(name = "INBEVTTIM")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime eventTimeInb;
	@Column(name = "TXNTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime transactionTime;
	@Column(name = "WGT")
	private double weight;
	@Column(name = "OUBCARIDR")
	private int carrierIdOub;
	@Column(name = "INBFLTSEQNUM")
	private long flightSequenceNumberInb;
	@Column(name = "OUBFLTSEQNUM")
	private long flightSequenceNumberOub;
	@Column(name = "INBCARIDR")
	private int carrierIdInb;

	@Column(name = "MSGVER")
	private String messageVersion;
	@Column(name = "EVTMOD")
	private String eventMode;
	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "WGTCOD")
	private String weightCode;
	@Column(name = "MALIDR")
	private String mailIdr;
	@Column(name = "SNDARPCOD")
	private String senderAirport;
	@Column(name = "RCVARPCOD")
	private String receiverAirport;
	@Column(name = "OUBPOU")
	private String pouOub;
	@Column(name = "INBPOL")
	private String polInb;
	@Column(name = "DSTARPCOD")
	private String destAirport;
	@Column(name = "OUBPOACOD")
	private String postalCodeOub;
	@Column(name = "OUBFLTNUM")
	private String flightNumberOub;
	@Column(name = "INBPOACOD")
	private String postalCodeInb;
	@Column(name = "PROSTA")
	private String processStatus;
	@Column(name = "INBFLTNUM")
	private String flightNumberInb;
	@Column(name = "OUBSTACOD")
	private String statusCodeOub;
	@Column(name = "INBSTACOD")
	private String statusCodeInb;
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;
	@Column(name = "TRALVL")
	private String transactionLevel;
	@Column(name = "MSGTIMUTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime msgTimUTC;

	public MLDMessageMaster() {
	}

	/** 
	* @param mldMasterVO
	* @throws SystemException
	*/
	public MLDMessageMaster(MLDMasterVO mldMasterVO) {
		log.debug("MLDMessageMaster" + " : " + "init" + " Entering");
		populatePK(mldMasterVO);
		populateAttributes(mldMasterVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			log.debug("CreateException-MLDMessageMaster");
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("MLDMessageMaster" + " : " + "init" + " Exiting");
	}

	/** 
	* @param mldMasterVO
	* @throws SystemException
	*/
	private void populateAttributes(MLDMasterVO mldMasterVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		if (mLDDetailVO != null) {
			setCarrierIdInb(mLDDetailVO.getCarrierIdInb());
			setCarrierIdOub(mLDDetailVO.getCarrierIdOub());
			if(mLDDetailVO.getEventTimeInb()!=null){
			setEventTimeInb(mLDDetailVO.getEventTimeInb().toLocalDateTime());
			}
			if(mLDDetailVO.getEventTimeOub()!=null) {
			setEventTimeOub(mLDDetailVO.getEventTimeOub().toLocalDateTime());
			}
			setFlightNumberInb(mLDDetailVO.getFlightNumberInb());
			setFlightNumberOub(mLDDetailVO.getFlightNumberOub());
			if(mLDDetailVO.getFlightOperationDateInb()!=null) {
			setFlightOperationDateInb(mLDDetailVO.getFlightOperationDateInb().toLocalDateTime());
			}
			if(mLDDetailVO.getFlightOperationDateOub()!=null) {
			setFlightOperationDateOub(mLDDetailVO.getFlightOperationDateOub().toLocalDateTime());
			}
			setFlightSequenceNumberInb(mLDDetailVO.getFlightSequenceNumberInb());
			setFlightSequenceNumberOub(mLDDetailVO.getFlightSequenceNumberOub());
			setPolInb(mLDDetailVO.getPolInb());
			setPostalCodeInb(mLDDetailVO.getPostalCodeInb());
			setPostalCodeOub(mLDDetailVO.getPostalCodeOub());
			setPouOub(mLDDetailVO.getPouOub());
			if (mLDDetailVO.getEventTimeOub() != null) {
				setTransactionTime(mLDDetailVO.getEventTimeOub().toLocalDateTime());
			} else {
				setTransactionTime(mLDDetailVO.getEventTimeInb().toLocalDateTime());
			}
			setTransactionTimeUTC(
					localDateUtil.toUTCTime(localDateUtil.getLocalDate(logonAttributes.getAirportCode(), false)).toLocalDateTime());
			setStatusCodeInb(mLDDetailVO.getMailModeInb());
			setStatusCodeOub(mLDDetailVO.getMailModeOub());
		}
		setEventMode(mldMasterVO.getEventCOde());
		//setLastUpdatedTime(localDateUtil.getLocalDate(logonAttributes.getAirportCode(), false).toLocalDateTime());
		setLastUpdatedUser(logonAttributes.getUserId());
		setMailIdr(mldMasterVO.getBarcodeValue());
		setMessageVersion(mldMasterVO.getMessageVersion());
		if(mldMasterVO.getMailSequenceNumber()!=0) {
		setMailSequenceNumber(mldMasterVO.getMailSequenceNumber());
		}
		setProcessStatus(mldMasterVO.getProcessStatus());
		setReceiverAirport(mldMasterVO.getReceiverAirport());
		setSenderAirport(mldMasterVO.getSenderAirport());
		setContainerNumber(mldMasterVO.getUldNumber());
		if (mldMasterVO.getWeight() != null) {
			setWeight(mldMasterVO.getWeight().getValue().doubleValue());
		}
		setWeightCode(mldMasterVO.getWeightCode());
		setDestAirport(mldMasterVO.getDestAirport());
		if (mldMasterVO.getTransactionLevel() != null) {
			setTransactionLevel(mldMasterVO.getTransactionLevel());
		}
	}

	/** 
	* @param mldMasterVO
	*/
	private void populatePK(MLDMasterVO mldMasterVO) {
		log.debug("" + "THE MLDMasterVO VO>>>>>>>>>>" + " " + mldMasterVO);
		this.setCompanyCode(mldMasterVO.getCompanyCode());
		log.debug("MLDMessageMaster" + " : " + "populatepK" + " Exiting");
	}
	/**
	 * @param mLDMessageMasterPK
	 * @return
	 * @throws SystemException
	 */
	public static MLDMessageMaster find(MLDMessageMasterPK mLDMessageMasterPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MLDMessageMaster.class, mLDMessageMasterPK);
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(mail_operations));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception.getMessage(), exception);
		}
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<MLDMasterVO> findMLDDetails(String companyCode, int recordCount) {
		try {
			return constructDAO().findMLDDetails(companyCode, recordCount);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


}
