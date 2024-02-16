package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/** 
 * @author a-5991
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(MailbagInULDForSegmentPK.class)
@Table(name = "MALULDSEGDTL")
public class MailbagInULDForSegment extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Id
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Id
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Id
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	@Id
	@Column(name = "ULDNUM")
	private String uldNumber;
	@Id
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;

	@Column(name = "CONNUM")
	private String containerNumber;
	@Column(name = "DMGFLG")
	private String damageFlag;
	@Column(name = "SCNPRT")
	private String scannedPort;
	@Column(name = "SCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scannedDate;
	@Column(name = "WGT")
	private double weight;
	@Column(name = "ACPSTA")
	private String acceptanceFlag;
	@Column(name = "ARRSTA")
	private String arrivalFlag;
	@Column(name = "DLVSTA")
	private String deliveredStatus;
	@Column(name = "TRAFLG")
	private String transferFlag;

	/** 
	* Control Document Number;
	*/
	@Column(name = "CNTDOCNUM")
	private String controlDocumentNumber;
	@Column(name = "FRMCARCOD")
	private String transferFromCarrier;
	@Column(name = "TRFCARCOD")
	private String transferToCarrier;
	@Column(name = "MRASTA")
	private String mraStatus;
	@Column(name = "ARRSELNUM")
	private String arrivalsealNumber;
	@Column(name = "RCVBAG")
	private int recievedBags;
	@Column(name = "RCVWGT")
	private double recievedWeight;
	@Column(name = "STDBAG")
	private int statedBags;
	@Column(name = "STDWGT")
	private double statedWeight;
	@Column(name = "DLVBAG")
	private int deliveredBags;
	@Column(name = "DLVWGT")
	private double deliveredWeight;
	@Column(name = "ACPBAG")
	private int acceptedBags;
	@Column(name = "ACPWGT")
	private double acceptedWeight;
	@Column(name = "ACPUSR")
	private String acceptedUser;
	@Column(name = "ACPDAT")
	private LocalDateTime acceptedDate;
	@Column(name = "RCVDAT")
	private LocalDateTime receivedDate;
	@Column(name = "GHTTIM")
	private LocalDateTime ghttim;
	@Column(name = "SRVRSPLN")
	private String serviceResponsive;

	/**
	* @author A-5991
	*/
	public MailbagInULDForSegment() {
	}

	public MailbagInULDForSegment(ULDForSegmentPK uldForSegmentPK, MailbagInULDForSegmentVO mailbagInULDForSegmenVO) {
		populatePK(uldForSegmentPK, mailbagInULDForSegmenVO);
		populateAttributes(mailbagInULDForSegmenVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/**
	* @param mailbagInULDForSegmentVO
	*/
	private void populatePK(ULDForSegmentPK uldForSegmentPK, MailbagInULDForSegmentVO mailbagInULDForSegmentVO) {

		this.setCompanyCode(uldForSegmentPK.getCompanyCode());
		this.setCarrierId(uldForSegmentPK.getCarrierId());
		this.setFlightNumber(uldForSegmentPK.getFlightNumber());
		this.setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
		this.setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
		this.setUldNumber(uldForSegmentPK.getUldNumber());
		this.setMailSequenceNumber(mailbagInULDForSegmentVO.getMailSequenceNumber());
	}

	/** 
	* @param mailbagInULDForSegmentVO
	*/
	private void populateAttributes(MailbagInULDForSegmentVO mailbagInULDForSegmentVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		containerNumber = mailbagInULDForSegmentVO.getContainerNumber();
		damageFlag = mailbagInULDForSegmentVO.getDamageFlag();
		scannedDate = mailbagInULDForSegmentVO.getScannedDate().toLocalDateTime();
		scannedPort = mailbagInULDForSegmentVO.getScannedPort();
		weight = mailbagInULDForSegmentVO.getWeight().getValue().doubleValue();
		if (mailbagInULDForSegmentVO.getAcceptanceFlag() != null) {
			setAcceptanceFlag(mailbagInULDForSegmentVO.getAcceptanceFlag());
		} else {
			setAcceptanceFlag(MailConstantsVO.FLAG_NO);
		}
		if (mailbagInULDForSegmentVO.getArrivalFlag() != null) {
			setArrivalFlag(mailbagInULDForSegmentVO.getArrivalFlag());
		} else {
			setArrivalFlag(MailConstantsVO.FLAG_NO);
		}
		if (mailbagInULDForSegmentVO.getDeliveredStatus() != null) {
			setDeliveredStatus(mailbagInULDForSegmentVO.getDeliveredStatus());
		} else {
			setDeliveredStatus(MailConstantsVO.FLAG_NO);
		}
		if ("Y".equals(mailbagInULDForSegmentVO.getAcceptanceFlag())) {
			setAcceptedBags(1);
			if (mailbagInULDForSegmentVO.getWeight().getValue().doubleValue() == 0.0 & mailbagInULDForSegmentVO.getWeight().getDisplayValue() != null) {
				setAcceptedWeight(mailbagInULDForSegmentVO.getWeight().getDisplayValue().doubleValue());
			} else {
				setAcceptedWeight(mailbagInULDForSegmentVO.getWeight().getValue().doubleValue());
			}

		}
		if ("Y".equals(mailbagInULDForSegmentVO.getArrivalFlag())) {
			setRecievedBags(1);
			setRecievedWeight(mailbagInULDForSegmentVO.getWeight().getValue().doubleValue());
		}
		if ("Y".equalsIgnoreCase(mailbagInULDForSegmentVO.getDeliveredFlag())) {
			setDeliveredBags(1);
			setDeliveredWeight(mailbagInULDForSegmentVO.getWeight().getValue().doubleValue());
		}
		if (mailbagInULDForSegmentVO.getTransferFlag() != null) {
			setTransferFlag(mailbagInULDForSegmentVO.getTransferFlag());
		} else {
			setTransferFlag(MailConstantsVO.FLAG_NO);
		}
		setControlDocumentNumber(mailbagInULDForSegmentVO.getControlDocumentNumber());
		setTransferFromCarrier(mailbagInULDForSegmentVO.getTransferFromCarrier());
		setTransferToCarrier(mailbagInULDForSegmentVO.getTransferToCarrier());
		if (mailbagInULDForSegmentVO.getArrivalSealNumber() != null
				&& mailbagInULDForSegmentVO.getArrivalSealNumber().trim().length() > 0) {
			setArrivalsealNumber(mailbagInULDForSegmentVO.getArrivalSealNumber());
		}
		if (mailbagInULDForSegmentVO.getMraStatus() != null) {
			this.setMraStatus(mailbagInULDForSegmentVO.getMraStatus());
		} else {
			this.setMraStatus(MailConstantsVO.MRA_STATUS_NEW);
		}
		this.setLastUpdatedUser( mailbagInULDForSegmentVO.getLastUpdateUser());
		this.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
		setAcceptedUser(mailbagInULDForSegmentVO.getLastUpdateUser());
		setReceivedDate(mailbagInULDForSegmentVO.getScannedDate().toLocalDateTime());
		setAcceptedDate(localDateUtil
				.getLocalDateTime(mailbagInULDForSegmentVO.getScannedDate(), mailbagInULDForSegmentVO.getScannedPort()).toLocalDateTime());
		setServiceResponsive(MailConstantsVO.FLAG_NO);
		if (mailbagInULDForSegmentVO.getGhttim() != null) {
			setGhttim(mailbagInULDForSegmentVO.getGhttim().toLocalDateTime());
		}
		if (MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getIsFromTruck())) {
			setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		}
	}

	/** 
	* @author A-5991
	* @param mailbagPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailbagInULDForSegment find(MailbagInULDForSegmentPK mailbagPK) throws FinderException {
		return PersistenceController.getEntityManager().find(MailbagInULDForSegment.class, mailbagPK);
	}

	/** 
	* @author A-5991
	* @return
	* @throws SystemException
	*/
	public static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/** 
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* @return
	*/
	public MailbagInULDForSegmentVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagInULDForSegmentVO mailbagInULDForSegVO = new MailbagInULDForSegmentVO();
		mailbagInULDForSegVO.setCompanyCode(this.getCompanyCode());
		mailbagInULDForSegVO.setMailSequenceNumber(this.getMailSequenceNumber());
		mailbagInULDForSegVO.setContainerNumber(getContainerNumber());
		mailbagInULDForSegVO.setDamageFlag(getDamageFlag());
		mailbagInULDForSegVO.setScannedDate(localDateUtil.getLocalDateTime(getScannedDate(), getScannedPort()));
		mailbagInULDForSegVO.setScannedPort(getScannedPort());
		mailbagInULDForSegVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getWeight())));
		mailbagInULDForSegVO.setAcceptanceFlag(getAcceptanceFlag());
		mailbagInULDForSegVO.setArrivalFlag(getArrivalFlag());
		mailbagInULDForSegVO.setTransferFlag(getTransferFlag());
		mailbagInULDForSegVO.setDeliveredStatus(getDeliveredStatus());
		mailbagInULDForSegVO.setTransferFromCarrier(getTransferFromCarrier());
		mailbagInULDForSegVO.setTransferToCarrier(getTransferToCarrier());
		mailbagInULDForSegVO.setMraStatus(getMraStatus());
		return mailbagInULDForSegVO;
	}

	/** 
	* Removes assignment of mailbags from a flight Sep 14, 2006, a-1739
	* @param mailbags
	* @param isInbound TODO
	* @throws SystemException
	*/
	public void reassignMailFromFlight(Collection<MailbagVO> mailbags, boolean isInbound) {
		log.debug("DSNInULDForSegment" + " : " + "reassignMailFromFlight" + " Entering");
		Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> mailbagConMap = removeMailbags(mailbags);
		log.debug("" + "MAILBAGCONMAp" + " " + mailbagConMap);
		if (!isInbound) {
		}
		log.debug("DSNInULDForSegment" + " : " + "reassignMailFromFlight" + " Exiting");
	}

	/** 
	* Removes assignment of mailbags from this DSN. Also returns a map so the the DSNInCOntainer can be updated if mailbags are in bulk
	* @param mailbags
	* @return the map of DSNcontainerPK and VO having the count of mailbags
	* @throws SystemException
	*/
	private Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> removeMailbags(Collection<MailbagVO> mailbags) {
		log.debug("DSNInULDForSegment" + " : " + "removeMailbags" + " Entering");
		Map<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO> mailbagConMap = new HashMap<MailbagInULDForSegmentPK, MailbagInULDForSegmentVO>();
		if (mailbags != null && mailbags.size() > 0) {
			for (MailbagVO mailbagVO : mailbags) {
				MailbagInULDForSegmentPK mailbagInULDPK = constructMailbagPK(mailbagVO);
				MailbagInULDForSegment mailbagInULDToRem = null;
				MailbagInULDForSegment mailbagInULDForSegments = null;
				try {
					mailbagInULDForSegments = mailbagInULDToRem.find(mailbagInULDPK);
				} catch (FinderException ex) {
					log.error("Finder Exception Caught");
				}
				if (mailbagInULDForSegments != null) {
					mailbagInULDForSegments.remove();
				}
			}
		}
		log.debug("DSNInULDForSegment" + " : " + "removeMailbags" + " Exiting");
		return mailbagConMap;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @return
	*/
	private MailbagInULDForSegmentPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagInULDForSegmentPK mailbagPK = new MailbagInULDForSegmentPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagPK.setFlightNumber(mailbagVO.getFlightNumber());
		mailbagPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailbagPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		mailbagPK.setUldNumber(mailbagVO.getUldNumber());
		mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		log.debug("" + "MAILID MAILBAGVO" + " " + mailbagVO.getMailbagId());
		return mailbagPK;
	}

	public MailbagInULDForSegment(MailbagInULDForSegment mailbagInULDForSegment) {
		try {
			PersistenceController.getEntityManager().persist(mailbagInULDForSegment);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @author A-8353
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException 
	*/
	public MailbagInULDForSegmentVO getManifestInfo(ScannedMailDetailsVO scannedMailDetailsVO)
			throws PersistenceException {
		return constructDAO().getManifestInfo(scannedMailDetailsVO);
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	* @return
	* @throws SystemException 
	*/
	public MailbagInULDForSegmentVO getManifestInfoForNextSeg(MailbagVO mailbagVO) throws PersistenceException {
		return constructDAO().getManifestInfoForNextSeg(mailbagVO);
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	* @param containerVO
	*/
	public void updateHandoverReceivedCarrierForTransfer(MailbagVO mailbagVO, ContainerVO containerVO) {
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())
				&& containerVO.getFinalDestination() != null) {
			mailbagVO.setUldNumber(new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(containerVO.getFinalDestination()).toString());
		}
		MailbagInULDForSegment mailbagInULDForSeg = null;
		try {
			mailbagInULDForSeg = find(constructMailbagPK(mailbagVO));
		} catch (FinderException e) {
			e.getMessage();
		}
		if (mailbagInULDForSeg != null) {
			mailbagInULDForSeg.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		}
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	*/
	public void updateTransferFlagForPreviousSegment(MailbagVO mailbagVO) {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
		scannedMailDetailsVO.setAirportCode(mailbagVO.getScannedPort());
		if (mailbagVO.getMailSequenceNumber() == 0) {
			try {
				mailbagVO.setMailSequenceNumber(
						constructDAO().findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			} finally {
			}
		}
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		mailbagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailbagVOs);
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
		try {
			mailbagInULDForSegmentVO = getManifestInfo(scannedMailDetailsVO);
		} catch (PersistenceException e) {
			log.debug("" + "SystemException" + " " + e);
			e.getMessage();
		}
		if (mailbagInULDForSegmentVO != null) {
			MailbagInULDForSegment mailbagInULDForSeg = null;
			MailbagInULDForSegmentPK mailbagPK = new MailbagInULDForSegmentPK();
			mailbagPK.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
			mailbagPK.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
			mailbagPK.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
			mailbagPK.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
			mailbagPK.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
			mailbagPK.setUldNumber(mailbagInULDForSegmentVO.getContainerNumber());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			try {
				mailbagInULDForSeg = find(mailbagPK);
			} catch (FinderException e) {
				log.debug("" + "FinderException" + " " + e);
				e.getMessage();
			}
			if (mailbagInULDForSeg != null) {
				mailbagInULDForSeg.setTransferFlag(MailConstantsVO.FLAG_YES);
			}
		}
	}


}
