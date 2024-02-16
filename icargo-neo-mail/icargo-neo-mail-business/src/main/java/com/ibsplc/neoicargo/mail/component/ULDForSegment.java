package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralRuleConfigDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** 
 * @author a-5991Entity saving info regarding uld/bulk assignments for a segment. Details of containers for BULK  will be saved in a separate entity. AssignedFlight | | AssignedFlightSegment | | +--ULDForSegment | | +--DSNInULDForSegment |               | |               | |               +--MailbagInULDForSegmentVO |               | |               | |               +--DSNInContainerForSegmentVO | +--OnwardRouting
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(ULDForSegmentPK.class)
@Table(name = "MALULDSEG")
public class ULDForSegment extends BaseEntity implements Serializable {

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

	@Column(name = "BAGCNT")
	private int numberOfBags;
	@Column(name = "BAGWGT")
	private double weight;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "RCVBAG")
	private int receivedBags;
	@Column(name = "RCVWGT")
	private double receivedWeight;
	@Column(name = "WHSCOD")
	private String warehouseCode;
	@Column(name = "LOCCOD")
	private String locationCode;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "SEGSERNUM", referencedColumnName = "SEGSERNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false) })
	private Set<OnwardRouteForSegment> onwardRoutes;
	private static final String PRODUCT_NAME = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String CLOSE_OUT_OFFSET = "mail.operations.USPSCloseoutoffsettime";

	@Column(name = "FRMCARCOD")
	private String transferFromCarrier;
	@Column(name = "TRFCARCOD")
	private String transferToCarrier;
	@Column(name = "RELFLG")
	private String releasedFlag;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "SEGSERNUM", referencedColumnName = "SEGSERNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM", insertable = false, updatable = false) })
	private Set<MailbagInULDForSegment> mailBagInULDForSegments;

	/**
	* The Constructor
	*/
	public ULDForSegment() {
	}

	/** 
	* @author A-5991
	* @param uldForSegmentVO
	* @throws SystemException
	*/
	public ULDForSegment(ULDForSegmentVO uldForSegmentVO) {
		populatePK(uldForSegmentVO);
		populateAttributes(uldForSegmentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
		if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null) {
			populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
		}
	}

	public static Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO) throws PersistenceException {
		return constructDAO().findArrivedContainersForInbound(mailArrivalFilterVO);
	}

	public static Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO) throws PersistenceException {
		return constructDAO().findArrivedMailbagsForInbound(mailArrivalFilterVO);
	}
	//TODO: Method to be copied from classic
	public static Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO) {
		return null;
	}

	public static Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
			throws SystemException{
		log.debug("Entering UldForSegment >>>  findContainersInFlight");
		try{
			return constructDAO().findMailbagsInContainerForImportManifest(containers);
		}catch(PersistenceException ex ){
			throw new SystemException(ex.getMessage());
		}
	}

    /**
	* @author A-5991Populate the attribuete vaules
	* @param uldForSegmentVO
	*/
	private void populateAttributes(ULDForSegmentVO uldForSegmentVO) {
		this.numberOfBags = uldForSegmentVO.getNoOfBags();
		if (uldForSegmentVO.getTotalWeight() != null) {
			this.weight = uldForSegmentVO.getTotalWeight().getRoundedValue().doubleValue();
		}
		this.receivedBags = uldForSegmentVO.getReceivedBags();
		if (uldForSegmentVO.getReceivedWeight() != null) {
			this.receivedWeight = uldForSegmentVO.getReceivedWeight().getRoundedValue().doubleValue();
		}
		setNumberOfBags(uldForSegmentVO.getNoOfBags());
		if (uldForSegmentVO.getTotalWeight() != null) {
			setWeight(uldForSegmentVO.getTotalWeight().getValue().doubleValue());
		}
		setWarehouseCode(uldForSegmentVO.getWarehouseCode());
		setLocationCode(uldForSegmentVO.getLocationCode());
		setRemarks(uldForSegmentVO.getRemarks());
		setLastUpdatedUser(uldForSegmentVO.getLastUpdateUser());
		setTransferFromCarrier(uldForSegmentVO.getTransferFromCarrier());
		setTransferToCarrier(uldForSegmentVO.getTransferToCarrier());
		if (uldForSegmentVO.getReleasedFlag() != null) {
			setReleasedFlag(uldForSegmentVO.getReleasedFlag());
		} else {
			setReleasedFlag(MailConstantsVO.FLAG_NO);
		}
	}

	/** 
	* @author A-5991Populates the pK values
	* @param uldForSegmentVO
	*/
	private void populatePK(ULDForSegmentVO uldForSegmentVO) {
		this.setCompanyCode(uldForSegmentVO.getCompanyCode());
		this.setCarrierId(uldForSegmentVO.getCarrierId());
		this.setFlightNumber(uldForSegmentVO.getFlightNumber());
		this.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
		this.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
		this.setUldNumber(uldForSegmentVO.getUldNumber());
	}

	/** 
	* @author A-5991This method is used to find the Instance of the Entity
	* @param uldForSegmentPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static ULDForSegment find(ULDForSegmentPK uldForSegmentPK) throws FinderException {
		return PersistenceController.getEntityManager().find(ULDForSegment.class, uldForSegmentPK);
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO TODO
	* @param containerDetailsVO
	* @throws SystemException
	*/
	public void saveAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO, ContainerDetailsVO containerDetailsVO) {
		log.debug("ULDForSegment" + " : " + "saveAcceptanceDetails" + " Entering");
		boolean isScanned = mailAcceptanceVO.isScanned();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
		OperationalFlightVO operationalFlightVO = null;
		MailController mailController = new MailController();
		ZonedDateTime GHTtime = null;
		operationalFlightVO = mailController.constructOpFlightFromAcp(mailAcceptanceVO);
		operationalFlightVO.setPou(containerDetailsVO.getPou());
		GHTtime = findGHTForMailbags(operationalFlightVO);
		mailAcceptanceVO.setGHTtime(GHTtime);
		if (containerDetailsVO.getMailDetails() != null) {
			mailbagVOs.addAll(containerDetailsVO.getMailDetails());
		}
		if (despatchDetailsVOs != null) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				mailbagVOs.add(MailOperationsVOConverter.convertToMailBagVO(despatchDetailsVO));
			}
		}
		if (mailbagVOs.size() > 0) {
			addMailbags(mailbagVOs, mailAcceptanceVO);
		}
		log.debug("ULDForSegment" + " : " + "saveAcceptanceDetails" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public MailbagInULDForSegmentPK constructMailbagInULDForSegmentPK(MailbagVO mailbagVO) {
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCarrierId(this.getCarrierId());
		mailbagInULDForSegmentPK.setCompanyCode(this.getCompanyCode());
		mailbagInULDForSegmentPK.setFlightNumber(this.getFlightNumber());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setUldNumber(this.getUldNumber());
		mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		return mailbagInULDForSegmentPK;
	}

	/** 
	* @author A-5991
	* @param mailBagId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public long findMailSequenceNumber(String mailBagId, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailBagId, companyCode);
	}

	/** 
	* @author A-5991
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* This method adds mailbags to a DSN during acceptance
	* @param mailbagVOs
	* @throws SystemException
	*/
	public void addMailbags(Collection<MailbagVO> mailbagVOs, MailAcceptanceVO mailAcceptanceVO) {
		log.debug("DSNInULDForSegment" + " : " + "addMailbagsToDSN" + " Entering");
		if (mailbagVOs.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDVOs = new ArrayList<MailbagInULDForSegmentVO>();
			Collection<MailbagVO> mailbagsAdded = new ArrayList<MailbagVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					mailbagVO.setGhttim(mailAcceptanceVO.getGHTtime());
					if (mailAcceptanceVO.isFromDeviationList()) {
						MailbagInULDForSegment mailbagInULDForSeg = null;
						try {
							mailbagInULDForSeg = findMailbagInULD(constructMailbagInULDForSegmentPK(mailbagVO));
						} catch (FinderException e) {
							log.error(e.getMessage());
						}
						if (mailbagInULDForSeg != null) {
							mailbagInULDForSeg.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
							if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())
									&& mailbagVO.getContainerNumber() != null
									&& mailbagVO.getContainerNumber().trim().length() > 0) {
								mailbagInULDForSeg.setContainerNumber(mailbagVO.getContainerNumber());
							}
							mailbagInULDForSeg.setAcceptedDate(mailbagVO.getScannedDate().toLocalDateTime());
							mailbagInULDForSeg.setAcceptedBags(1);
							mailbagInULDForSeg.setAcceptedWeight(
									mailbagVO.getWeight() != null ? mailbagVO.getWeight().getValue().doubleValue()
											: 0.0);
							mailbagInULDForSeg.setAcceptedUser(mailAcceptanceVO.getAcceptedUser());
						} else {
							mailbagInULDVOs.add(constructMailbagForSegment(mailbagVO, false));
						}
					} else {
						mailbagInULDVOs.add(constructMailbagForSegment(mailbagVO, false));
					}
					mailbagsAdded.add(mailbagVO);
				} else if (MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
					MailbagInULDForSegment mailbagInULDForSeg = null;
					try {
						mailbagInULDForSeg = findMailbagInULD(constructMailbagInULDForSegmentPK(mailbagVO));
					} catch (FinderException e) {
						log.debug(e.getMessage());
					}
					if (mailbagInULDForSeg != null) {
						if (mailbagVO.getDamageFlag() != null) {
							mailbagInULDForSeg.setDamageFlag(mailbagVO.getDamageFlag());
						}
						if (mailbagVO.getSealNumber() != null) {
							mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getSealNumber());
						} else {
							mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
						}
						mailbagInULDForSeg.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
						mailbagInULDForSeg.setScannedDate(mailbagVO.getScannedDate().toLocalDateTime());
						if (mailbagVO.getArrivalSealNumber() != null
								&& mailbagVO.getArrivalSealNumber().trim().length() > 0) {
							mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
						}
					} else {
						throw new SystemException("Mailbag not found for update");
					}
				}
			}
			populateMailbagDetails(mailbagInULDVOs);
		}
		log.debug("DSNInULDForSegment" + " : " + "addMailbagsToDSN" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @param isArrival
	* @return
	* @throws SystemException
	*/
	private MailbagInULDForSegmentVO constructMailbagForSegment(MailbagVO mailbagVO, boolean isArrival) {
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO = new MailbagInULDForSegmentVO();
		mailbagInULDForSegmentVO.setMailId(mailbagVO.getMailbagId());
		mailbagInULDForSegmentVO.setDamageFlag(mailbagVO.getDamageFlag());
		mailbagInULDForSegmentVO
				.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		mailbagInULDForSegmentVO.setContainerNumber(mailbagVO.getContainerNumber());
		mailbagInULDForSegmentVO.setScannedDate(mailbagVO.getScannedDate());
		mailbagInULDForSegmentVO.setScannedPort(mailbagVO.getScannedPort());
		mailbagInULDForSegmentVO.setWeight(mailbagVO.getWeight());
		mailbagInULDForSegmentVO.setMailClass(mailbagVO.getMailClass());
		if (!isArrival) {
			mailbagInULDForSegmentVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		}
		if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
			mailbagInULDForSegmentVO.setArrivalFlag(MailConstantsVO.FLAG_YES);
		}
		if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
			mailbagInULDForSegmentVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);
		}
		mailbagInULDForSegmentVO.setArrivalFlag(mailbagVO.getArrivedFlag());
		mailbagInULDForSegmentVO.setDeliveredStatus(mailbagVO.getDeliveredFlag());
		if (mailbagVO.isDeliveryStatusForAutoArrival())
			mailbagInULDForSegmentVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
		mailbagInULDForSegmentVO.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		if (mailbagVO.getSealNumber() != null && mailbagVO.getSealNumber().trim().length() > 0) {
			mailbagInULDForSegmentVO.setSealNumber(mailbagVO.getSealNumber());
		}
		if (mailbagVO.getArrivalSealNumber() != null && mailbagVO.getArrivalSealNumber().trim().length() > 0) {
			mailbagInULDForSegmentVO.setArrivalSealNumber(mailbagVO.getArrivalSealNumber());
		}
		mailbagInULDForSegmentVO.setControlDocumentNumber(mailbagVO.getControlDocumentNumber());
		mailbagInULDForSegmentVO.setMraStatus(MailConstantsVO.MRA_STATUS_NEW);
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagInULDForSegmentVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		}
		if (mailbagVO.getGhttim() != null) {
			mailbagInULDForSegmentVO.setGhttim(mailbagVO.getGhttim());
		}
		if (isArrival && MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
			mailbagInULDForSegmentVO.setIsFromTruck(mailbagVO.getIsFromTruck());
		}
		return mailbagInULDForSegmentVO;
	}

	/** 
	* @author A-5991Finds a mailbag in this ULD
	* @param mailbagPK
	* @return the mailbag entity
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailbagInULDForSegment findMailbagInULD(MailbagInULDForSegmentPK mailbagPK) throws FinderException {
		MailbagInULDForSegment mailInULDSeg = null;
		try {
			mailInULDSeg = MailbagInULDForSegment.find(mailbagPK);
		} catch (Exception exception) {
			if (mailbagPK.getUldNumber() != null && mailbagPK.getUldNumber().contains(MailConstantsVO.CONST_BULK)) {
				mailbagPK.setUldNumber(MailConstantsVO.CONST_BULK);
				mailInULDSeg = MailbagInULDForSegment.find(mailbagPK);
			}
		}
		return mailInULDSeg;
	}

	/** 
	* @author A-5991
	* @param mailbags
	* @throws SystemException
	*/
	public void populateMailbagDetails(Collection<MailbagInULDForSegmentVO> mailbags) {
		ULDForSegmentPK uldForSegmentPk = getUldForSegmentPk();

		if (mailbags != null && mailbags.size() > 0) {
			if (mailBagInULDForSegments == null) {
				mailBagInULDForSegments = new HashSet<MailbagInULDForSegment>();
			}
			if (mailbags != null && !mailbags.isEmpty()) {
				for (MailbagInULDForSegmentVO mailbag : mailbags) {
					mailBagInULDForSegments.add(new MailbagInULDForSegment(uldForSegmentPk, mailbag));
				}
			}
		}
	}

	/** 
	* A-1739
	* @param onwardRoutingForSegmentVOs
	* @throws SystemException
	*/
	public void updateOnwardRoutes(Collection<OnwardRouteForSegmentVO> onwardRoutingForSegmentVOs) {
		if (getOnwardRoutes() == null || onwardRoutes.size() == 0) {
			populateOnwardRouteDetails(onwardRoutingForSegmentVOs);
		} else {
			for (OnwardRouteForSegmentVO onwardRouteForSegmentVO : onwardRoutingForSegmentVOs) {
				if (OnwardRouteForSegmentVO.OPERATION_FLAG_INSERT.equals(onwardRouteForSegmentVO.getOperationFlag())) {
					new OnwardRouteForSegment(getUldForSegmentPk(), onwardRouteForSegmentVO);
				} else {
					OnwardRouteForSegment onwardRouteForSeg = findOnwardRouteForSegment(onwardRoutes,
							onwardRouteForSegmentVO.getRoutingSerialNumber());
					if (OnwardRouteForSegmentVO.OPERATION_FLAG_DELETE.equals(onwardRouteForSegmentVO.getOperationFlag())) {
						onwardRouteForSeg.remove();
					} else {
						onwardRouteForSeg.update(onwardRouteForSegmentVO);
					}
				}
			}
		}
	}
	private ULDForSegmentPK getUldForSegmentPk(){
		ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
		uldForSegmentPk.setCompanyCode(this.getCompanyCode());
		uldForSegmentPk.setCarrierId(this.getCarrierId());
		uldForSegmentPk.setFlightNumber(this.getFlightNumber());
		uldForSegmentPk.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegmentPk.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegmentPk.setUldNumber(this.getUldNumber());
		return uldForSegmentPk;
	}

	/** 
	* @param onwardRoutings
	* @throws SystemException
	*/
	private void populateOnwardRouteDetails(Collection<OnwardRouteForSegmentVO> onwardRoutings) {
		ULDForSegmentPK uldForSegmentPk = getUldForSegmentPk();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			if (onwardRoutes == null) {
				onwardRoutes = new HashSet<OnwardRouteForSegment>();
			}
			for (OnwardRouteForSegmentVO onwardRouteForSegmentVO : onwardRoutings) {
				onwardRoutes.add(new OnwardRouteForSegment(uldForSegmentPk, onwardRouteForSegmentVO));
			}
		}
	}

	/** 
	* @param onwardRoutesForSeg
	* @param routingSerialNumber
	* @return
	*/
	private OnwardRouteForSegment findOnwardRouteForSegment(Set<OnwardRouteForSegment> onwardRoutesForSeg,
			int routingSerialNumber) {
		for (OnwardRouteForSegment onwardRouteForSeg : onwardRoutesForSeg) {
			if (onwardRouteForSeg.getRoutingSerialNumber() == routingSerialNumber) {
				return onwardRouteForSeg;
			}
		}
		return null;
	}

	/** 
	* This method removes the entity
	* @throws SystemException
	*/
	public void remove() {
		removeChildren();
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* This method is used to remove the Instance of the Children
	* @throws SystemException
	*/
	private void removeChildren() {
		Collection<MailbagInULDForSegment> mailbagInULDForSegments = getMailBagInULDForSegments();
		if (mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
			for (MailbagInULDForSegment mailBagSegment : mailbagInULDForSegments) {
				mailBagSegment.remove();
			}
		}
		Collection<OnwardRouteForSegment> onwardRoutings = getOnwardRoutes();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			for (OnwardRouteForSegment onwardRouting : onwardRoutings) {
				onwardRouting.remove();
			}
		}
	}

	/** 
	* @author A-1739This method is used to reassignMailToFlight
	* @param mailbagsToAdd
	* @throws SystemException
	*/
	public void reassignMailToFlight(Collection<MailbagVO> mailbagsToAdd, ContainerVO toContainerVO) {
		log.debug("ULDForSegment" + " : " + "reassignMailToFlight" + " Entering");
		if (mailbagsToAdd != null && mailbagsToAdd.size() > 0) {
			OperationalFlightVO operationalFlightVO = null;
			MailController mailController = new MailController();
			ZonedDateTime GHTtime = null;
			operationalFlightVO = mailController.constructOpFlightFromContainer(toContainerVO, true);
			GHTtime = findGHTForMailbags(operationalFlightVO);
			if (getMailBagInULDForSegments() == null) {
				mailBagInULDForSegments = new HashSet<MailbagInULDForSegment>();
			}
			ULDForSegmentPK uldForSegmentPk = getUldForSegmentPk();
			for (MailbagVO mailbagVO : mailbagsToAdd) {
				mailbagVO.setGhttim(GHTtime);
				mailbagVO.setAcceptanceFlag("Y");
				mailbagVO.setArrivedFlag("N");
				mailbagVO.setDeliveredFlag("N");
				mailbagVO.setMraStatus(MailConstantsVO.FLAG_NO);
				mailBagInULDForSegments.add(
						new MailbagInULDForSegment(uldForSegmentPk, constructMailbagInULD(mailbagVO, toContainerVO)));
			}
		}
		log.debug("ULDForSegment" + " : " + "reassignMailToFlight" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @param toContainerVO
	* @return
	*/
	private MailbagInULDForSegmentVO constructMailbagInULD(MailbagVO mailbagVO, ContainerVO toContainerVO) {
		MailbagInULDForSegmentVO mailbagInULDForSeg = new MailbagInULDForSegmentVO();
		mailbagInULDForSeg.setMailId(mailbagVO.getMailbagId());
		mailbagInULDForSeg.setAcceptanceFlag(mailbagVO.getAcceptanceFlag());
		mailbagInULDForSeg.setContainerNumber(toContainerVO.getContainerNumber());
		mailbagInULDForSeg.setDamageFlag(mailbagVO.getDamageFlag());
		mailbagInULDForSeg.setScannedDate(mailbagVO.getScannedDate());
		mailbagInULDForSeg.setScannedPort(mailbagVO.getScannedPort());
		mailbagInULDForSeg.setWeight(mailbagVO.getWeight());
		mailbagInULDForSeg.setArrivalFlag(mailbagVO.getArrivedFlag());
		mailbagInULDForSeg.setDeliveredStatus(mailbagVO.getDeliveredFlag());
		mailbagInULDForSeg.setMailClass(mailbagVO.getMailClass());
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagInULDForSeg.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		} else if (mailbagVO.getMailbagId() != null) {
			try {
				mailbagInULDForSeg.setMailSequenceNumber(
						findMailSequenceNumber(mailbagVO.getMailbagId(), this.getCompanyCode()));
			} finally {
			}
		}
		mailbagInULDForSeg.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
		mailbagInULDForSeg.setMraStatus(mailbagVO.getMraStatus());
		if (mailbagVO.getGhttim() != null) {
			mailbagInULDForSeg.setGhttim(mailbagVO.getGhttim());
		}
		return mailbagInULDForSeg;
	}

	/** 
	* @return
	*/
	public ULDForSegmentVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		ULDForSegmentPK uldForSegPk = getUldForSegmentPk();
		uldForSegmentVO.setCompanyCode(uldForSegPk.getCompanyCode());
		uldForSegmentVO.setCarrierId(uldForSegPk.getCarrierId());
		uldForSegmentVO.setFlightNumber(uldForSegPk.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(uldForSegPk.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(uldForSegPk.getSegmentSerialNumber());
		uldForSegmentVO.setUldNumber(uldForSegPk.getUldNumber());
		uldForSegmentVO.setNoOfBags(getNumberOfBags());
		uldForSegmentVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getWeight())));
		uldForSegmentVO.setRemarks(getRemarks());
		uldForSegmentVO.setWarehouseCode(getWarehouseCode());
		uldForSegmentVO.setLocationCode(getLocationCode());
		uldForSegmentVO.setTransferFromCarrier(getTransferFromCarrier());
		uldForSegmentVO.setTransferToCarrier(getTransferToCarrier());
		Collection<MailbagInULDForSegment> mailbagInULDForSegments = getMailBagInULDForSegments();
		if (mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = new ArrayList<MailbagInULDForSegmentVO>();
			for (MailbagInULDForSegment mailbagInULDForSegment : mailbagInULDForSegments) {
				mailbagInULDForSegmentVOs.add(mailbagInULDForSegment.retrieveVO());
			}
			uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
		}
		Collection<OnwardRouteForSegment> onwardRoutings = getOnwardRoutes();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			Collection<OnwardRouteForSegmentVO> onwardRouteVOs = new ArrayList<OnwardRouteForSegmentVO>();
			for (OnwardRouteForSegment onwardRouting : onwardRoutings) {
				onwardRouteVOs.add(onwardRouting.retrieveVO());
			}
			uldForSegmentVO.setOnwardRoutings(onwardRouteVOs);
		}
		return uldForSegmentVO;
	}

	/** 
	* This method detaches a DSN from the assigned dest/flight to the dest/flight to which it has to be assined. Since a DSN can span multiple BULK containers, a DSN is completely detached only if it is in a single container only. In the case where a ULD spans multiple containers a replica of this DSN in returned with only that container and the mailbags in them. A-1739
	* @param containerNumber
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagInULDForSegmentVO> reassignBulkContainer(String containerNumber) {
		log.debug("ULDForSEgment" + " : " + "findAndRemoveContainer" + " Entering");
		Collection<MailbagInULDForSegmentVO> dsnsToReassign = new ArrayList<MailbagInULDForSegmentVO>();
		Set<MailbagInULDForSegment> dsnInULDForSegs = getMailBagInULDForSegments();
		Collection<MailbagInULDForSegment> dsnsToDeassign = new ArrayList<MailbagInULDForSegment>();
		for (MailbagInULDForSegment dsnInULDForSeg : dsnInULDForSegs) {
			if (containerNumber.equals(dsnInULDForSeg.getContainerNumber())) {
				dsnsToReassign.add(dsnInULDForSeg.retrieveVO());
				dsnInULDForSeg.remove();
			}
		}
		if (dsnsToDeassign.size() > 0) {
			dsnInULDForSegs.removeAll(dsnsToDeassign);
		}
		log.debug("ULDForSEgment" + " : " + "findAndRemoveContainer" + " Exiting");
		return dsnsToReassign;
	}

	/**
	* @throws SystemException
	*/
	public void assignBulkContainer(Collection<MailbagInULDForSegmentVO> dsnInULDForSegmentVOs) {
		ULDForSegmentPK uldForSegmentPK = getUldForSegmentPk();
		if (getMailBagInULDForSegments() == null) {
			mailBagInULDForSegments = new HashSet<MailbagInULDForSegment>();
		}
		if (dsnInULDForSegmentVOs != null && dsnInULDForSegmentVOs.size() > 0) {
			for (MailbagInULDForSegmentVO dsnInULDForSegmentVO : dsnInULDForSegmentVOs) {
				if (dsnInULDForSegmentVO != null) {
					mailBagInULDForSegments.add(new MailbagInULDForSegment(uldForSegmentPK, dsnInULDForSegmentVO));
				}
			}
		}
	}

	/** 
	* @author a-1936This method is used to reassign the mails From the Flight to Destiantion The Block does the following 1.Group the Mailbags based on differentDSns say D1-U1-F1-seg1 D2-U1-F1-seg1 D3-U1-F1-seg1 2.Group the DSNS to update the DSNS at segment  finally from AssignedFlightSegment say D1-F1-seg1 D2-F1-seg1 D3-F1-seg1 D1-F2-seg1 D2-F1-seg2
	* @param mailbagVOs
	* @param isInbound TODO
	* @throws SystemException
	*/
	public void reassignMailFromFlight(Collection<MailbagVO> mailbagVOs, boolean isInbound) {
		Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> mailbagMap = groupDSNMailbags(mailbagVOs);
		MailbagInULDForSegment mailbagInULDForSegment = null;
		for (Map.Entry<MailbagInULDForSegmentPK, Collection<MailbagVO>> dsnMailbag : mailbagMap.entrySet()) {
			MailbagInULDForSegmentPK mailbagInULDForSegmentPK = dsnMailbag.getKey();
			mailbagInULDForSegment = findDSNInULDForSeg(mailbagInULDForSegmentPK);
			if (mailbagInULDForSegment == null) {
				throw new SystemException("NO mail bags FOR SEG ");
			}
			Collection<MailbagVO> mailbags = dsnMailbag.getValue();
			int noOfbags = mailbags.size();
			double bagWeight = calculateWeightofbags(mailbags);
			if (isInbound) {
				mailbagInULDForSegment.setRecievedBags(mailbagInULDForSegment.getRecievedBags() - noOfbags);
				mailbagInULDForSegment.setRecievedWeight(mailbagInULDForSegment.getRecievedWeight() - bagWeight);
			} else {
				mailbagInULDForSegment.setAcceptedBags(mailbagInULDForSegment.getAcceptedBags() - noOfbags);
				mailbagInULDForSegment.setAcceptedWeight(mailbagInULDForSegment.getAcceptedWeight() - bagWeight);
			}
			mailbagInULDForSegment.reassignMailFromFlight(mailbags, isInbound);
			log.debug(" removing bulk container because wt 0");
			this.mailBagInULDForSegments.remove(mailbagInULDForSegment);
			mailbagInULDForSegment.remove();
		}
	}

	/** 
	* A-1739
	* @param mailbags
	* @return
	*/
	private double calculateWeightofbags(Collection<MailbagVO> mailbags) {
		double totalWeight = 0;
		for (MailbagVO mailbagVO : mailbags) {
			totalWeight += mailbagVO.getWeight().getValue().doubleValue();
		}
		return totalWeight;
	}

	/** 
	* A-1739
	* @param dsnULDPK
	* @return
	*/
	private MailbagInULDForSegment findDSNInULDForSeg(MailbagInULDForSegmentPK dsnULDPK) {
		mailBagInULDForSegments = getMailBagInULDForSegments();
		if (mailBagInULDForSegments != null) {
			log.debug("" + "dsnInULDForSegments.size" + " " + mailBagInULDForSegments.size());
			for (MailbagInULDForSegment mailbagInULDForSegment : mailBagInULDForSegments) {
				if (mailbagInULDForSegment.getCompanyCode().equals(dsnULDPK.getCompanyCode())
					&& (mailbagInULDForSegment.getCarrierId()==dsnULDPK.getCarrierId())
						&&( mailbagInULDForSegment.getFlightNumber().equals(dsnULDPK.getFlightNumber()))
						&& ( mailbagInULDForSegment.getFlightSequenceNumber()==dsnULDPK.getFlightSequenceNumber())
						&& (mailbagInULDForSegment.getSegmentSerialNumber()==dsnULDPK.getSegmentSerialNumber())
						&& (mailbagInULDForSegment.getUldNumber().equals(dsnULDPK.getUldNumber()))
						&& (mailbagInULDForSegment.getMailSequenceNumber()==dsnULDPK.getMailSequenceNumber())) {
					return mailbagInULDForSegment;
				}
			}
		}
		return null;
	}

	/** 
	* A-1739
	* @param mailbagVOs
	* @return
	*/
	private Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> groupDSNMailbags(Collection<MailbagVO> mailbagVOs) {
		Map<MailbagInULDForSegmentPK, Collection<MailbagVO>> dsnForULDSegMap = new HashMap<MailbagInULDForSegmentPK, Collection<MailbagVO>>();
		for (MailbagVO mailbagVo : mailbagVOs) {
			if (mailbagVo.getMailSequenceNumber() == 0 && mailbagVo.getMailbagId() != null) {
				try {
					mailbagVo.setMailSequenceNumber(
							findMailSequenceNumber(mailbagVo.getMailbagId(), mailbagVo.getCompanyCode()));
				} finally {
				}
			}
			MailbagInULDForSegmentPK dsnForULDSegmentPK = constructDSNInULDForSegPKFromMail(mailbagVo);
			Collection<MailbagVO> mailbagsOfDSN = dsnForULDSegMap.get(dsnForULDSegmentPK);
			if (mailbagsOfDSN == null) {
				mailbagsOfDSN = new ArrayList<MailbagVO>();
				dsnForULDSegMap.put(dsnForULDSegmentPK, mailbagsOfDSN);
			}
			mailbagsOfDSN.add(mailbagVo);
		}
		return dsnForULDSegMap;
	}

	/** 
	* @author A-1936This method is used to reassign the DsNs From Flight
	* @param despatchDetailVos
	* @param isInbound TODO
	* @throws SystemException
	*/
	public void reassignDSNsFromFlight(Collection<DespatchDetailsVO> despatchDetailVos, boolean isInbound) {
		log.debug("ULDForSegment" + " : " + "reassignDSNFromFlight" + " Entering");
		if (despatchDetailVos != null && despatchDetailVos.size() > 0) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailVos) {
				MailbagVO mailbagVO = constructMailbagInULDAtAirportvoFromDespatch(despatchDetailsVO);
				Collection<MailbagVO> mailbagVOs = findMailBagForDespatch(mailbagVO);
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagvo : mailbagVOs) {
						MailbagInULDForSegmentPK mailbagInULDForSegmentPK = constructMailbagInULDAtAirportPKFromDespatch(
								mailbagvo);
						MailbagInULDForSegment mailbagInULDForSegment = null;
						try {
							mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
						} catch (FinderException ex) {
							log.debug(ex.getMessage());
						}
						if (mailbagInULDForSegment != null) {
							mailbagInULDForSegment.remove();
						}
					}
				}
			}
		}
		log.debug("ULDForSegment" + " : " + "reassignDSNFromFlight" + " Exiting");
	}

	/** 
	* @author  This method is used to construct theMailbagInULDAtAirportPK
	* @return
	* @throws SystemException
	*/
	private MailbagVO constructMailbagInULDAtAirportvoFromDespatch(DespatchDetailsVO despatchDetailsVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(this.getCompanyCode());
		mailbagVO.setUldNumber(this.getUldNumber());
		mailbagVO.setScannedPort(despatchDetailsVO.getAirportCode());
		mailbagVO.setCarrierId(this.getCarrierId());
		mailbagVO.setDespatchId(createDespatchBag(despatchDetailsVO));
		return mailbagVO;
	}

	public static String createDespatchBag(DespatchDetailsVO despatchDetailsVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(despatchDetailsVO.getOriginOfficeOfExchange())
				.append(despatchDetailsVO.getDestinationOfficeOfExchange())
				.append(despatchDetailsVO.getMailCategoryCode()).append(despatchDetailsVO.getMailSubclass())
				.append(despatchDetailsVO.getYear()).append(despatchDetailsVO.getDsn());
		return dsnid.toString();
	}

	/** 
	* @author A-5991
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public static Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO) {
		Collection<MailbagVO> mailbagVos = null;
		try {
			mailbagVos = constructDAO().findMailBagForDespatch(mailbagVO);
		} catch (PersistenceException e) {
		}
		return mailbagVos;
	}

	/** 
	* @author a-1936This method is used to construct the MailbagInULDAtAirportPK
	* @return
	* @throws SystemException
	*/
	private MailbagInULDForSegmentPK constructMailbagInULDAtAirportPKFromDespatch(MailbagVO mailbagvo) {
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(this.getCompanyCode());
		mailbagInULDForSegmentPK.setUldNumber(this.getUldNumber());
		mailbagInULDForSegmentPK.setFlightNumber(this.getFlightNumber());
		mailbagInULDForSegmentPK.setCarrierId(this.getCarrierId());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		return mailbagInULDForSegmentPK;
	}

	/** 
	* @author a-1936This method is used to construct the DSNInULDForSegmentPK from mailBagVO..
	* @param mailbagVO
	* @return
	*/
	private MailbagInULDForSegmentPK constructDSNInULDForSegPKFromMail(MailbagVO mailbagVO) {
		MailbagInULDForSegmentPK dsnInULDForSegmentPK = new MailbagInULDForSegmentPK();
		dsnInULDForSegmentPK.setCompanyCode(this.getCompanyCode());
		dsnInULDForSegmentPK.setCarrierId(this.getCarrierId());
		dsnInULDForSegmentPK.setFlightNumber(this.getFlightNumber());
		dsnInULDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		dsnInULDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		dsnInULDForSegmentPK.setUldNumber(this.getUldNumber());
		dsnInULDForSegmentPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		return dsnInULDForSegmentPK;
	}

	/** 
	* @param dSNInULDForSegmentVOs
	* @throws SystemException
	*/
	public void saveOutboundDetailsForTransfer(Collection<DSNInULDForSegmentVO> dSNInULDForSegmentVOs,
			ContainerVO containerVO) {
		log.debug("ULDForSegment" + " : " + "saveOutboundDetailsForTransfer" + " Entering");
		int bags = 0;
		double totalWgt = 0;
		ULDForSegmentPK uldForSegmentPk = new ULDForSegmentPK();
		if (dSNInULDForSegmentVOs != null && dSNInULDForSegmentVOs.size() > 0) {
			for (DSNInULDForSegmentVO dSNInULDForSegmentVO : dSNInULDForSegmentVOs) {
				Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = dSNInULDForSegmentVO.getMailBags();
				if (mailbagInULDForSegmentVOs != null && mailbagInULDForSegmentVOs.size() > 0) {
					for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagInULDForSegmentVOs) {
						mailbagInULDForSegmentVO.setGhttim(containerVO.getGHTtime());
						MailbagInULDForSegmentPK mailbagInULDForSegmentPK = constructMailbagInULDPK(uldForSegmentPk,
								mailbagInULDForSegmentVO);
						if (findMailbagInULDForSeg(mailbagInULDForSegmentPK) == null) {
							new MailbagInULDForSegment(getUldForSegmentPk(), mailbagInULDForSegmentVO);
						}
					}
				}
			}
		}
		log.debug("ULDForSegment" + " : " + "saveOutboundDetailsForTransfer" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagInULDForSegmentPK
	* @return
	*/
	private MailbagInULDForSegment findMailbagInULDForSeg(MailbagInULDForSegmentPK mailbagInULDForSegmentPK) {
		if (mailBagInULDForSegments != null && mailBagInULDForSegments.size() > 0) {
			for (MailbagInULDForSegment mailbagInULD : mailBagInULDForSegments) {
				if (mailbagInULD.getCompanyCode().equals(mailbagInULDForSegmentPK.getCompanyCode())
						&& (mailbagInULD.getCarrierId()==mailbagInULDForSegmentPK.getCarrierId())
						&&( mailbagInULD.getFlightNumber().equals(mailbagInULDForSegmentPK.getFlightNumber()))
						&& ( mailbagInULD.getFlightSequenceNumber()==mailbagInULDForSegmentPK.getFlightSequenceNumber())
						&& (mailbagInULD.getSegmentSerialNumber()==mailbagInULDForSegmentPK.getSegmentSerialNumber())
						&& (mailbagInULD.getUldNumber().equals(mailbagInULDForSegmentPK.getUldNumber()))
						&& (mailbagInULD.getMailSequenceNumber()==mailbagInULDForSegmentPK.getMailSequenceNumber())) {
					return mailbagInULD;
				}


			}
		}
		return null;
	}

	/** 
	* @author A-5991
	* @param uldForSegmentPK
	* @param mailbagInULDForSegmentVO
	* @return
	* @throws SystemException
	*/
	private MailbagInULDForSegmentPK constructMailbagInULDPK(ULDForSegmentPK uldForSegmentPK,
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO) {
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(uldForSegmentPK.getCompanyCode());
		mailbagInULDForSegmentPK.setCarrierId(uldForSegmentPK.getCarrierId());
		mailbagInULDForSegmentPK.setFlightNumber(uldForSegmentPK.getFlightNumber());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setUldNumber(uldForSegmentPK.getUldNumber());
		mailbagInULDForSegmentPK.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(
				mailbagInULDForSegmentVO.getMailId(), uldForSegmentPK.getCompanyCode()));
		return mailbagInULDForSegmentPK;
	}

	/** 
	* @author a-1883
	* @param isArrived
	* @param toFlightVO TODO
	* @throws SystemException
	*/
	public void saveArrivalDetailsForTransfer(Map<MailbagPK, MailbagVO> mailMap, boolean isArrived,
			OperationalFlightVO toFlightVO) {
		log.debug("ULDForSegment" + " : " + "saveArrivalDetailsForTransfer" + " Entering");
		Collection<MailbagInULDForSegment> mailbagInULDForSegments = this.getMailBagInULDForSegments();
		if (mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
			for (MailbagInULDForSegment mailbagInULDForSegment : mailbagInULDForSegments) {
				MailbagPK mailbagPK = constructMailbagPKFromSeg(mailbagInULDForSegment);
				MailbagVO mailbagVO = mailMap.get(mailbagPK);
				if (mailbagVO == null) {
					mailbagVO = constructMailBagVOFrmSeg(mailbagInULDForSegment);
					mailMap.put(mailbagPK, mailbagVO);
				}
				mailbagInULDForSegment.setArrivalFlag(MailConstantsVO.FLAG_YES);
				if (!isArrived) {
					mailbagInULDForSegment.setTransferFlag(MailConstantsVO.FLAG_YES);
				}
				mailbagInULDForSegment.setTransferToCarrier(toFlightVO.getCarrierCode());
				mailbagInULDForSegment.setRecievedBags(1);
				mailbagInULDForSegment.setRecievedWeight(mailbagInULDForSegment.getAcceptedWeight());
			}
		}
		log.debug("ULDForSegment" + " : " + "saveArrivalDetailsForTransfer" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagInULDForSegment
	* @return
	*/
	private MailbagPK constructMailbagPKFromSeg(MailbagInULDForSegment mailbagInULDForSegment) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagInULDForSegment.getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailbagInULDForSegment.getMailSequenceNumber());
		return mailbagPK;
	}

	private MailbagVO constructMailBagVOFrmSeg(MailbagInULDForSegment mailbagInULDForSegment) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(mailbagInULDForSegment.getCompanyCode());
		mailbagVO.setMailSequenceNumber(mailbagInULDForSegment.getMailSequenceNumber());
		return mailbagVO;
	}

	/** 
	* updateUldAcquittalStatus
	* @throws SystemException
	*/
	public void updateUldAcquittalStatus() {
		log.debug("ULDForSegment" + " : " + "updateUldAcquittalStatus" + " Entering");
		mailBagInULDForSegments = getMailBagInULDForSegments();
		boolean acquitULD = false;
		if (mailBagInULDForSegments != null && mailBagInULDForSegments.size() > 0) {
			for (MailbagInULDForSegment mailbagInULDForSegment : mailBagInULDForSegments) {
				if (MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegment.getArrivalFlag())) {
					acquitULD = true;
					break;
				}
			}
		}
		if (getNumberOfBags() == 0 && getWeight() == 0.0 && getReceivedBags() == 0 && getReceivedWeight() == 0.0) {
			acquitULD = true;
		}
		if (acquitULD && !MailConstantsVO.FLAG_YES.equals(getReleasedFlag())) {
			setReleasedFlag(MailConstantsVO.FLAG_YES);
			log.debug("ALL DSNs ARRIVED & ULD SUCCESSFULLY RELEASED FROM FLIGHT. !!!!!!!");
		}
		log.debug("ULDForSegment" + " : " + "updateUldAcquittalStatus" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveMailArrivalDetailsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("DSNInULDForSegment" + " : " + "saveMailArrivalDetailsForTransfer" + " Entering");
		MailbagInULDForSegment mailbagInULDForSegment = null;
		try {
			for (MailbagVO mailbagVO : mailbagVOs) {
				MailbagInULDForSegmentPK mailbagInULDForSegmentPK = constructMailbagInULDForSegmentPK(mailbagVO);
				mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
				mailbagInULDForSegment.setArrivalFlag(MailConstantsVO.FLAG_YES);
				mailbagInULDForSegment.setTransferFlag(MailConstantsVO.FLAG_YES);
				mailbagInULDForSegment.setTransferToCarrier(containerVO.getCarrierCode());
				mailbagInULDForSegment.setRecievedBags(1);
				if (mailbagVO.getWeight() != null) {
					mailbagInULDForSegment.setRecievedWeight(mailbagVO.getWeight().getValue().doubleValue());
				}
			}
		} catch (FinderException finderException) {
			throw new SystemException("Mailbag in Dsn not found", finderException.getMessage(), finderException);
		}
		log.debug("DSNInULDForSegment" + " : " + "saveMailArrivalDetailsForTransfer" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveOutboundMailDetailsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		OperationalFlightVO operationalFlightVO = null;
		MailController mailController = new MailController();
		ZonedDateTime GHTtime = null;
		operationalFlightVO = mailController.constructOpFlightFromContainer(containerVO, true);
		GHTtime = findGHTForMailbags(operationalFlightVO);
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setGhttim(GHTtime);
			MailbagInULDForSegmentVO mailbagULDSegVO = constructMailbagInULD(mailbagVO, containerVO);
			mailbagULDSegVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			mailbagULDSegVO.setArrivalFlag(MailConstantsVO.FLAG_NO);
			mailbagULDSegVO.setDeliveredStatus(MailConstantsVO.FLAG_NO);
			mailbagULDSegVO.setTransferFlag(MailConstantsVO.FLAG_NO);
			mailbagULDSegVO.setMraStatus(MailConstantsVO.FLAG_NO);
			if (mailbagVO.getTransferFromCarrier() != null && mailbagVO.getTransferFromCarrier().trim().length() > 0) {
				mailbagULDSegVO.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
			} else {
				if (!containerVO.isFoundTransfer()) {
					mailbagULDSegVO.setTransferFromCarrier(mailbagVO.getCarrierCode());
				}
			}
			boolean uldSegDtlFound = false;
			if (mailbagVO.isFromDeviationList()) {
				MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
				mailbagInULDForSegmentPK.setCompanyCode(this.getCompanyCode());
				mailbagInULDForSegmentPK.setCarrierId(this.getCarrierId());
				mailbagInULDForSegmentPK.setFlightNumber(this.getFlightNumber());
				mailbagInULDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
				mailbagInULDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
				mailbagInULDForSegmentPK.setUldNumber(this.getUldNumber());
				mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagULDSegVO.getMailSequenceNumber());
				try {
					MailbagInULDForSegment foundUldSeg = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
					if (foundUldSeg != null) {
						uldSegDtlFound = true;
						foundUldSeg.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
						foundUldSeg.setAcceptedDate(mailbagVO.getScannedDate().toLocalDateTime());
						foundUldSeg.setAcceptedBags(1);
						foundUldSeg.setAcceptedWeight(
								mailbagVO.getWeight() != null ? mailbagVO.getWeight().getValue().doubleValue() : 0.0);
						foundUldSeg.setAcceptedUser(mailbagVO.getScannedUser());
						if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())
								&& mailbagVO.getContainerNumber() != null
								&& mailbagVO.getContainerNumber().trim().length() > 0) {
							foundUldSeg.setContainerNumber(mailbagVO.getContainerNumber());
						}
					}
				} catch (FinderException e) {
					uldSegDtlFound = false;
				}
			}
			if (!uldSegDtlFound) {
				new MailbagInULDForSegment(getUldForSegmentPk(), mailbagULDSegVO);
			}
		}
		log.debug("DSNInULDForSegment" + " : " + "saveOutboundMailDetailsForTransfer" + " Exiting");
	}

	/** 
	* @author A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO TODO
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	public void saveArrivalDetails(ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO,
			Map<String, Collection<MailbagVO>> mailBagsMapForInventory,
			Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory) throws DuplicateMailBagsException {
		log.debug("ULDForSegment" + " : " + "saveArrivalDetails" + " Entering");
		boolean isScanned = mailArrivalVO.isScanned();
		Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		Collection<MailbagVO> updatedMailbagVOs = new ArrayList<MailbagVO>();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())
						|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
					mailbagVO.setFlightNumber(mailArrivalVO.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
					mailbagVO.setCarrierId(mailArrivalVO.getCarrierId());
					mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
					mailbagVO.setDespatchId(createDespatchBag(mailbagVO));
					mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
					mailbagVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
					if (MailConstantsVO.FLAG_YES.equals(mailArrivalVO.getPaBuiltFlag())) {
						mailbagVO.setPaBuiltFlag(mailArrivalVO.getPaBuiltFlag());
					}
					String paCode = null;
					String companyCode = mailbagVO.getCompanyCode();
					String originOfFiceExchange = mailbagVO.getOoe();
					if (mailbagVO.getPaCode() == null) {
						try {
							paCode = new MailController().findPAForOfficeOfExchange(companyCode, originOfFiceExchange);
						} finally {
						}
						mailbagVO.setPaCode(paCode);
					}
					if (mailArrivalVO.getScanDate() != null) {
						mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
					}
					if (MailConstantsVO.FLAG_YES.equals(mailArrivalVO.getIsFromTruck())) {
						mailbagVO.setIsFromTruck(mailArrivalVO.getIsFromTruck());
					}
					updatedMailbagVOs.add(mailbagVO);
				}
			}
			saveArrivedMailbags(updatedMailbagVOs, mailBagsMapForInventory);
		}
		log.debug("ULDForSegment" + " : " + "saveArrivalDetails" + " Exiting");
	}

	public static String createDespatchBag(MailbagVO mailbagVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(mailbagVO.getOoe()).append(mailbagVO.getDoe()).append(mailbagVO.getMailCategoryCode())
				.append(mailbagVO.getMailSubclass()).append(mailbagVO.getYear())
				.append(mailbagVO.getDespatchSerialNumber());
		return dsnid.toString();
	}

	/** 
	* @author a-1883
	* @param mailbagVOs
	* @throws SystemException
	*/
	public void updateDamageDetails(Collection<MailbagVO> mailbagVOs) {
		log.debug("ULDForSegment" + " : " + "updateDamageDetails" + " Entering");
		for (MailbagVO mailbagVO : mailbagVOs) {
			updateMailbagInULDForSegment(mailbagVO);
		}
		log.debug("ULDForSegment" + " : " + "updateDamageDetails" + " Exiting");
	}

	/** 
	* @author a-1883
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateMailbagInULDForSegment(MailbagVO mailbagVO) {
		log.debug("DSNInULDForSegment" + " : " + "updateMailbagInULDForSegment" + " Entering");
		MailbagInULDForSegmentPK mailbagInULDForSegmentPk = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPk.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDForSegmentPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDForSegmentPk.setFlightNumber(mailbagVO.getFlightNumber());
		mailbagInULDForSegmentPk.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailbagInULDForSegmentPk
				.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		mailbagInULDForSegmentPk.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		mailbagInULDForSegmentPk.setUldNumber(mailbagVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			log.info("THE MAL BAG IS  ASSOCIATED WITH A BARROW");
			if (mailbagVO.isFromReturnPopUp()) {
				String uldNum = new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
						.append(mailbagVO.getPou()).toString();
				mailbagInULDForSegmentPk.setUldNumber(uldNum);
			} else {
				mailbagInULDForSegmentPk.setUldNumber(mailbagVO.getUldNumber());
			}
			log.info("" + "THE MAL BAG IS  ASSOCIATED WITH A BARROW" + " " + mailbagInULDForSegmentPk.getUldNumber());
		}
		try {
			MailbagInULDForSegment mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPk);
			mailbagInULDForSegment.setDamageFlag(MailbagVO.FLAG_YES);
		} catch (FinderException finderException) {
			log.error(" MailbagInULDForSegment Not existing ");
			throw new SystemException(finderException.getMessage(), finderException.getMessage(), finderException);
		}
		log.debug("DSNInULDForSegment" + " : " + "updateMailbagInULDForSegment" + " Exiting");
	}

	/** 
	* @author a-1936Added By Karthick V ..
	* @param mailBagsMapForInventory
	* @param mailBag
	*/
	private void addMailBagToInventoryMap(Map<String, Collection<MailbagVO>> mailBagsMapForInventory,
			MailbagVO mailBag) {
		log.debug("DSNINULD" + " : " + "addMailBagToInventoryMap" + " Entering");
		String containerKey = null;
		if (mailBag.getContainerForInventory() != null) {
			containerKey = new StringBuilder().append(mailBag.getContainerForInventory())
					.append(MailConstantsVO.ARPULD_KEYSEP).append(mailBag.getContainerTypeAtAirport())
					.append(MailConstantsVO.ARPULD_KEYSEP).append(mailBag.getPaBuiltFlag()).toString();
		} else {
			if (mailBag.getContainerType() == null || mailBag.getContainerType().length() == 0) {
				if (getUldForSegmentPk().getUldNumber().startsWith("BULK")) {
					mailBag.setContainerType(MailConstantsVO.BULK_TYPE);
				} else {
					mailBag.setContainerType(MailConstantsVO.ULD_TYPE);
				}
			}
			if (MailConstantsVO.BULK_TYPE.equals(mailBag.getContainerType())) {
				String container = MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR)
						.concat(mailBag.getCarrierCode());
				containerKey = new StringBuilder().append(container).append(MailConstantsVO.ARPULD_KEYSEP)
						.append(mailBag.getContainerType()).toString();
			} else {
				containerKey = new StringBuilder()
						.append(mailBag.getContainerNumber() != null ? mailBag.getContainerNumber()
								: getUldForSegmentPk().getUldNumber())
						.append(MailConstantsVO.ARPULD_KEYSEP).append(mailBag.getContainerType())
						.append(MailConstantsVO.ARPULD_KEYSEP).toString();
			}
		}
		Collection<MailbagVO> mailBags = mailBagsMapForInventory.get(containerKey);
		if (mailBags == null) {
			mailBags = new ArrayList<MailbagVO>();
			mailBagsMapForInventory.put(containerKey, mailBags);
		}
		mailBags.add(mailBag);
		log.debug("DSNINULD" + " : " + "addMailBagToInventoryMap" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagVOs
	* @param mailBagsMapForInventory
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	public void saveArrivedMailbags(Collection<MailbagVO> mailbagVOs,
			Map<String, Collection<MailbagVO>> mailBagsMapForInventory) throws DuplicateMailBagsException {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("DSNInULDForSegment" + " : " + "saveArrivedMailbags" + " Entering");
		if (mailbagVOs.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDVOs = new ArrayList<MailbagInULDForSegmentVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
				if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
						&& !(MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())
								|| MailConstantsVO.FLAG_YES.equals(mailbagVO.getTransferFlag()))) {
					addMailBagToInventoryMap(mailBagsMapForInventory, mailbagVO);
				}
				MailbagPK mailbagPK = constructMailbagPK(mailbagVO);
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.findMailbagDetails(mailbagPK);
				} catch (FinderException e1) {
				}
				MailbagInULDForSegment mailbagInULDForSeg = null;
				try {
					mailbagInULDForSeg = findMailbagInULD(constructMailbagPKFrmMail(mailbagVO));
				} catch (FinderException e) {
				}
				log.debug("" + " To find The Damage Flag is " + " " + mailbagVO);
				if (mailbagInULDForSeg != null && (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())
						|| MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
						|| MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()))) {
					mailbagInULDForSeg.setDamageFlag(mailbagVO.getDamageFlag());
					mailbagInULDForSeg.setArrivalFlag(mailbagVO.getArrivedFlag());
					mailbagInULDForSeg.setDeliveredStatus(mailbagVO.getDeliveredFlag());
					if (mailbagVO.isDeliveryStatusForAutoArrival())
						mailbagInULDForSeg.setDeliveredStatus(MailConstantsVO.FLAG_YES);
					if (!MailConstantsVO.IMPORT.equals(mailbagInULDForSeg.getMraStatus())) {
						mailbagInULDForSeg.setMraStatus(MailConstantsVO.FLAG_NO);
					}
					if (mailbagVO.getScannedDate() != null) {
						mailbagInULDForSeg.setScannedDate(mailbagVO.getScannedDate().toLocalDateTime());
					}
					if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
						mailbagInULDForSeg.setRecievedBags(1);
						if (mailbagVO.getWeight() != null) {
							mailbagInULDForSeg.setRecievedWeight(mailbagVO.getWeight().getValue().doubleValue());
						}
					}
					if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
						mailbagInULDForSeg.setDeliveredBags(1);
						if (mailbagVO.getWeight() != null) {
							mailbagInULDForSeg.setDeliveredWeight(mailbagVO.getWeight().getValue().doubleValue());
						}
					}
					if (mailbagVO.getArrivalSealNumber() != null
							&& mailbagVO.getArrivalSealNumber().trim().length() > 0) {
						mailbagInULDForSeg.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
					}
				} else {
					if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						boolean isDuplicate = false;
						if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
							if (getUldForSegmentPk() != null) {
								mailbagVO.setSegmentSerialNumber(getUldForSegmentPk().getSegmentSerialNumber());
							}
							if (mailbag != null) {
								if (!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()))
										&& MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus())
										&& mailbag.getScannedPort().equals(mailbagVO.getScannedPort())) {
									isDuplicate = new MailController().checkForDuplicateMailbag(
											mailbagVO.getCompanyCode(), mailbagVO.getPaCode(), mailbag);
									if (mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber()) && !isDuplicate) {
										throw new DuplicateMailBagsException(
												DuplicateMailBagsException.DUPLICATEMAILBAGS_EXCEPTION,
												new Object[] { mailbagVO.getMailbagId() });
									}
								} else {
									mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
									mailbagInULDVOs.add(constructMailbagForSegment(mailbagVO, true));
								}
							}
							if (mailbag == null || isDuplicate) {
								String paCode = null;
								String companyCode = mailbagVO.getCompanyCode();
								String originOfFiceExchange = mailbagVO.getOoe();
								if (mailbagVO.getPaCode() == null) {
									try {
										paCode = new MailController().findPAForOfficeOfExchange(companyCode,
												originOfFiceExchange);
									} finally {
									}
									mailbagVO.setPaCode(paCode);
								}
								mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
								String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
								if (scanWaved != null) {
									mailbagVO.setScanningWavedFlag(scanWaved);
								}
								if (new MailController().isUSPSMailbag(mailbagVO)) {
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
								} else {
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
								}
								new MailController().calculateAndUpdateLatestAcceptanceTime(mailbagVO);
								mailbag = new Mailbag().persistMailbag(mailbagVO);
								mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
								if ((mailbagVO.getAcceptanceFlag() != null
										|| MailConstantsVO.FLAG_NO.equals(mailbagVO.getAcceptanceFlag()))
										&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
									OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
									operationalFlightVo.setCompanyCode(mailbagVO.getCompanyCode());
									operationalFlightVo.setCarrierId(mailbagVO.getCarrierId());
									operationalFlightVo.setFlightNumber(mailbagVO.getFlightNumber());
									operationalFlightVo.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
									operationalFlightVo.setPol(mailbagVO.getPol());
									operationalFlightVo.setPou(mailbagVO.getPou());
									mailbagVO.setPou(mailbagVO.getPou());
									operationalFlightVo.setAirportCode(mailbagVO.getScannedPort());
									operationalFlightVo.setLegSerialNumber(mailbagVO.getLegSerialNumber());
									ZonedDateTime flightDate = localDateUtil.getLocalDateTime(mailbagVO.getFlightDate(),
											null);
									operationalFlightVo.setFlightDate(flightDate);
									operationalFlightVo.setCarrierCode(mailbagVO.getCarrierCode());
									Collection<FlightValidationVO> flightValidationVOs = null;
									flightValidationVOs = flightOperationsProxy
											.validateFlightForAirport(MailOperationsVOConverter
													.constructFlightFilterVOForContainer(mailbagVO));
									if (flightValidationVOs != null) {
										for (FlightValidationVO flightValidationVO : flightValidationVOs) {
											if (flightValidationVO.getFlightSequenceNumber() == mailbagVO
													.getFlightSequenceNumber()) {
												if (flightValidationVO != null && flightValidationVO.getAta() != null) {
													operationalFlightVo.setArrivaltime(flightValidationVO.getAta().toZonedDateTime());
													updateGHTForMailbags(operationalFlightVo, mailbagVO);
												}
											}
										}
									}
								}
								MailbagInULDForSegmentVO mailbagInULD = constructMailbagForSegment(mailbagVO, true);
								if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getIsFromTruck())) {
									mailbagInULD.setIsFromTruck(mailbagVO.getIsFromTruck());
								}
								mailbagInULDVOs.add(mailbagInULD);
							}
						}
					} else if (MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
					}
				}
				if (mailbagVO.getLastUpdateTime() != null
						&& !MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					mailbag.setLastUpdatedTime(Timestamp.valueOf(mailbagVO.getLastUpdateTime().toLocalDateTime()));
				}
				if (mailbag != null) {
					try {
						mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
						if (mailbagVO.getMailRemarks() == null || mailbagVO.getMailRemarks().trim().isEmpty()) {
							mailbagVO.setMailRemarks(mailbag.getMailRemarks());
						}
						mailbag.updateArrivalDetails(mailbagVO);
					} catch (Exception exception) {
						log.debug(
								"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with Mailbag "
										+ mailbagVO);
						continue;
					}
					MailInConsignmentVO mailInConsignmentVO = mailbagVO.getMailConsignmentVO();
					if (mailInConsignmentVO != null) {
					}
					mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
					updateFlightDetailsofNewMailbag(mailbagVO);
				}
				if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
					try {
						if (mailbagInULDForSeg != null) {
							if (new MailController().isUSPSMailbag(mailbagVO)) {
								updatemailperformanceDetails(mailbagVO, mailbagInULDForSeg, mailbag);
							}
						}
					} catch (PersistenceException e) {
						log.error(e.getMessage());
					}
				}
				Transaction tx = null;
				boolean success = false;
				try {
					//TODO: Neo to verify
					TransactionProvider tm = PersistenceController.__getTransactionProvider();
					tx = tm.getNewTransaction(false);
					success = true;
				} finally {
					if (success) {
						tx.commit();
					}
				}
			}
			populateMailbagDetails(mailbagInULDVOs);
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())
						&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())
						&& new MailController().isUSPSMailbag(mailbagVO)) {
					MailbagPK mailbagPK = constructMailbagPK(mailbagVO);
					Mailbag mailbag = null;
					MailbagInULDForSegment mailbagInULDForSeg = null;
					try {
						mailbag = Mailbag.find(mailbagPK);
						mailbagInULDForSeg = findMailbagInULD(constructMailbagPKFrmMail(mailbagVO));
						if (mailbag != null && mailbagInULDForSeg != null) {
							updatemailperformanceDetails(mailbagVO, mailbagInULDForSeg, mailbag);
						}
					} catch (Exception e) {
						log.error("" + "exception raised" + " " + e);
					}
				}
			}
		}
		log.debug("DSNInULDForSegment" + " : " + "saveArrivedMailbags" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @return
	*/
	private MailbagInULDForSegmentPK constructMailbagPKFrmMail(MailbagVO mailbagVO) {
		MailbagInULDForSegmentPK mailbagInULDPK = new MailbagInULDForSegmentPK();
		mailbagInULDPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDPK.setFlightNumber(mailbagVO.getFlightNumber());
		mailbagInULDPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailbagInULDPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		if (mailbagVO.getUldNumber() != null) {
			mailbagInULDPK.setUldNumber(mailbagVO.getUldNumber());
		} else {
			mailbagInULDPK.setUldNumber(mailbagVO.getContainerNumber());
		}
		if (mailbagVO.getMailSequenceNumber() < 1) {
			try {
				mailbagInULDPK.setMailSequenceNumber(Mailbag
						.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			} finally {
			}
		} else {
			mailbagInULDPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		}
		return mailbagInULDPK;
	}

	private void updateFlightDetailsofNewMailbag(MailbagVO mailbagVO) {
		mailbagVO.setCompanyCode(this.getCompanyCode());
		mailbagVO.setCarrierId(this.getCarrierId());
		mailbagVO.setFlightNumber(this.getFlightNumber());
		mailbagVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		mailbagVO.setFlightNumber(this.getFlightNumber());
		mailbagVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
	}

	public MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		return mailbagPK;
	}

	private String findSystemParameterValue(String syspar) {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance()
				.getBean(NeoMastersServiceUtils.class);
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* @author A-7371
	* @param mailbag
	* @param mailbagInULDForSeg
	* @throws SystemException 
	* @throws PersistenceException 
	*/
	public void updatemailperformanceDetails(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg,
			Mailbag mailbag) throws PersistenceException {
		updateServiceResponsiveness(mailbagVO, mailbagInULDForSeg);
		String paCode_int = null;
		try {
			paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		} finally {
		}
		if (mailbag.getPaCode().equals(paCode_int)) {
			updateTransportationWindowStatus(mailbag, mailbagVO);
		}
		if (!(MailConstantsVO.FLAG_YES.equals(mailbagVO.getOnTimeDelivery())
				&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getScanningWavedFlag()))) {
			updateOnTimeDelivery(mailbagVO, mailbagInULDForSeg, mailbag);
		}
	}

	private void updateServiceResponsiveness(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg)
			throws PersistenceException {
		String paCode_dom = null;
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		Timestamp staDate = (mailbagVO.getFlightNumber() != null && mailbagVO.getFlightSequenceNumber() > 0)
				? fetchSegmentSTA(mailbagVO)
				: null;
		String serviceResponsiveIndicator = "X";
		if (mailbagVO.getServiceResponsive() != null) {
			serviceResponsiveIndicator = mailbagVO.getServiceResponsive();
		} else {
			try {
				serviceResponsiveIndicator = findServiceResponsiveIndicator(mailbagVO);
			} catch (Exception exception) {
				log.debug("Exception :", exception);
			}
			mailbagVO.setServiceResponsive(serviceResponsiveIndicator);
		}
		if (mailbagInULDForSeg != null && paCode_dom.equals(mailbagVO.getPaCode())) {
			mailbagInULDForSeg.setServiceResponsive(MailConstantsVO.MALTYP_DOMESTIC);
		} else {
			if (mailbagInULDForSeg != null)
				mailbagInULDForSeg.setServiceResponsive(serviceResponsiveIndicator);
		}
	}

	private Timestamp fetchSegmentSTA(MailbagVO mailbagVO) throws PersistenceException {
		return constructDAO().fetchSegmentSTA(mailbagVO);
	}

	private String findServiceResponsiveIndicator(MailbagVO mailbagVO) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagPK mailbagPK = constructMailbagPK(mailbagVO);
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPK);
			mailbagVO.setConsignmentDate(localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), null));
			mailbagVO.setMailServiceLevel(mailbag.getMailServiceLevel());
			mailbagVO.setPaCode(mailbag.getPaCode());
			mailbagVO.setOrigin(mailbag.getOrigin());
			mailbagVO.setDestination(mailbag.getDestination());
		} catch (FinderException e1) {
			log.debug("mailbag not found");
		}
		return constructDAO().findServiceResponsiveIndicator(mailbagVO);
	}

	private String fetchRDTOffset(MailbagVO mailbagVO, String paCode_dom) throws PersistenceException {
		return constructDAO().fetchRDTOffset(mailbagVO, paCode_dom);
	}

	private void updateOnTimeDelivery(MailbagVO mailbagVO, MailbagInULDForSegment mailbagInULDForSeg, Mailbag mailbag)
			throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String domPaCode = null;
		domPaCode = findSystemParameterValue(USPS_DOMESTIC_PA);
		Timestamp staDate = (mailbagVO.getFlightNumber() != null && mailbagVO.getFlightSequenceNumber() > 0)
				? fetchSegmentSTA(mailbagVO)
				: null;
		String serviceResponsiveIndicator = "X";
		if (mailbagInULDForSeg != null && mailbagInULDForSeg.getServiceResponsive() != null) {
			serviceResponsiveIndicator = mailbagInULDForSeg.getServiceResponsive();
		} else {
			serviceResponsiveIndicator = mailbagVO.getServiceResponsive();
		}
		String rdtOffset = fetchRDTOffset(mailbagVO, domPaCode);
		String[] rdtOffsetTime;
		//TODO: Neo to verify below code based on classic
		ZonedDateTime RDTLocal = staDate != null ? localDateUtil.getLocalDate(mailbagVO.getScannedPort(), staDate)
				: mailbagVO.getScannedDate();
		if (mailbag.getReqDeliveryTime() != null) {
			mailbagVO.setReqDeliveryTime(
					(localDateUtil.getLocalDateTime(mailbag.getReqDeliveryTime(), mailbagVO.getScannedPort())));
		}
		if (rdtOffset != null && rdtOffset.trim().length() > 0) {
			rdtOffsetTime = rdtOffset.split("-");
			if (domPaCode.equals(mailbagVO.getPaCode())) {
				RDTLocal.plusDays(Integer.parseInt(rdtOffsetTime[0]));
				String rdt = RDTLocal.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
				RDTLocal = localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true);
				RDTLocal = LocalDate.withDate(RDTLocal, rdt);
				RDTLocal.plusMinutes(Integer.parseInt(rdtOffsetTime[1]));
			} else {
				RDTLocal.plusMinutes(Integer.parseInt(rdtOffsetTime[1]));
			}
		}
		if (serviceResponsiveIndicator != null && serviceResponsiveIndicator.length() > 0
				&& (MailConstantsVO.FLAG_YES.equals(serviceResponsiveIndicator))) {
			if (mailbag.getReqDeliveryTime() != null) {
				if ((mailbagVO.getScannedDate().isBefore(mailbagVO.getReqDeliveryTime())
						|| mailbagVO.getScannedDate().equals(mailbagVO.getReqDeliveryTime()))
						&& ((mailbag.getMetTransWindow() != null) && (mailbag.getMetTransWindow().length() > 0)
								&& MailConstantsVO.FLAG_YES.equals(mailbag.getMetTransWindow()))) {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
				} else {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				}
			} else {
				if ((mailbagVO.getScannedDate().isBefore(RDTLocal) || mailbagVO.getScannedDate().equals(RDTLocal))
						&& ((mailbag.getMetTransWindow() != null) && (mailbag.getMetTransWindow().length() > 0)
								&& MailConstantsVO.FLAG_YES.equals(mailbag.getMetTransWindow()))) {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
				} else {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				}
			}
		} else if (serviceResponsiveIndicator != null && serviceResponsiveIndicator.length() > 0
				&& (MailConstantsVO.MALTYP_DOMESTIC.equals(serviceResponsiveIndicator)
						|| MailConstantsVO.FLAG_NO.equals(serviceResponsiveIndicator))) {
			if (mailbag.getReqDeliveryTime() != null) {
				if (mailbagVO.getScannedDate().isBefore(mailbagVO.getReqDeliveryTime())
						|| mailbagVO.getScannedDate().equals(mailbagVO.getReqDeliveryTime())) {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
				} else {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				}
			} else {
				if (mailbagVO.getScannedDate().isBefore(RDTLocal) || mailbagVO.getScannedDate().equals(RDTLocal)) {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_YES);
				} else {
					mailbag.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				}
			}
		}
	}

	/** 
	* @author A-8464
	* @param mailbag
	* @param mailbagVo
	* @throws SystemException 
	* @throws PersistenceException 
	*/
	private void updateTransportationWindowStatus(Mailbag mailbag, MailbagVO mailbagVo) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("ULDForSegment" + " : " + "updateTransportationWindowStatus" + " Entering");
		mailbagVo.setMailServiceLevel(mailbag.getMailServiceLevel());
		ZonedDateTime transportationSrvWindowEndTime = null;
		if (mailbag.getTransWindowEndTime() == null) {
			transportationSrvWindowEndTime = new MailController().calculateTransportServiceWindowEndTime(mailbagVo);
		} else {
			transportationSrvWindowEndTime = localDateUtil.getLocalDateTime(mailbag.getTransWindowEndTime(),
					mailbag.getDestination());
		}
		mailbag.setTransWindowEndTime(transportationSrvWindowEndTime.toLocalDateTime());
		if (transportationSrvWindowEndTime != null
				&& (mailbagVo.getScannedDate().isBefore(transportationSrvWindowEndTime)
						|| mailbagVo.getScannedDate().equals(transportationSrvWindowEndTime))) {
			mailbag.setMetTransWindow("Y");
			mailbagVo.setMetTransWindow(MailConstantsVO.FLAG_YES);
		} else if (transportationSrvWindowEndTime != null
				&& mailbagVo.getScannedDate().isAfter(transportationSrvWindowEndTime)) {
			mailbag.setMetTransWindow("N");
		} else {
			mailbag.setMetTransWindow("");
		}
		log.debug("ULDForSegment" + " : " + "updateTransportationWindowStatus" + " Exiting");
	}

	/** 
	* Method		:	ULDForSegment.findGHTForMailbags Added by 	:	A-8061 on 28-Apr-2020 Used for 	:	IASCB-48967 Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	LocalDate
	*/
	public ZonedDateTime findGHTForMailbags(OperationalFlightVO operationalFlightVO) {
		FlightValidationVO flightValidationVO = null;
		ZonedDateTime GHTtime = null;
		MailbagVO mailbagVO = new MailbagVO();
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		flightValidationVO = mailController.validateOperationalFlight(operationalFlightVO, true);
		if (flightValidationVO != null) {
			operationalFlightVO.setArrivaltime(
					flightValidationVO.getAta() != null ? flightValidationVO.getAta().toZonedDateTime() : flightValidationVO.getSta().toZonedDateTime());
			mailbagVO.setPou(operationalFlightVO.getPou());
			mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_OUTBOUND);
			updateGHTForMailbags(operationalFlightVO, mailbagVO);
			GHTtime = mailbagVO.getGhttim();
		}
		return GHTtime;
	}

	/** 
	* @author A-7540
	* @param operationalFlightVO
	* @param mailbagVO
	*/
	public void updateGHTForMailbags(OperationalFlightVO operationalFlightVO, MailbagVO mailbagVO) {
		SharedDefaultsProxy sharedDefaultsProxy = ContextUtil.getInstance().getBean(SharedDefaultsProxy.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ZonedDateTime ghttim = null;
		if (operationalFlightVO != null && operationalFlightVO.getFlightDate() != null) {
			GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
			Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = null;
			Collection<MailbagVO> mailbags = null;
			ZonedDateTime arrtime = null;
			ZonedDateTime flightDate = localDateUtil.getLocalDateTime(operationalFlightVO.getFlightDate(), null);
			generalTimeMappingFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			generalTimeMappingFilterVO.setCarrierId(operationalFlightVO.getCarrierId());
			generalTimeMappingFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			generalTimeMappingFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			if (operationalFlightVO.getPou() != null) {
				generalTimeMappingFilterVO.setPou(operationalFlightVO.getPou());
			}
			generalTimeMappingFilterVO.setPol(operationalFlightVO.getPol());
			generalTimeMappingFilterVO
					.setAirportCode(MailConstantsVO.OPERATION_OUTBOUND.equals(mailbagVO.getOperationalFlag())
							? generalTimeMappingFilterVO.getPou()
							: operationalFlightVO.getAirportCode());
			generalTimeMappingFilterVO.setConfigurationType("MHT");
			try {
				generalConfigurationMasterVOs = sharedDefaultsProxy
						.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
			} finally {
			}
			if (operationalFlightVO.getArrivaltime() != null) {
				arrtime = operationalFlightVO.getArrivaltime();
			}
			if (generalConfigurationMasterVOs != null && !generalConfigurationMasterVOs.isEmpty()) {
				for (GeneralConfigurationMasterVO general : generalConfigurationMasterVOs) {
					String parvalmin = null;
					String parvalhr = null;
					int min = 0;
					int hour = 0;
					if ((flightDate.isAfter(LocalDateMapper.toZonedDateTime(general.getStartDate()))
							&& flightDate.isBefore(LocalDateMapper.toZonedDateTime(general.getEndDate())))
							|| flightDate.equals(general.getStartDate()) || flightDate.equals(general.getEndDate())) {
						if (mailbagVO.getPou().equals(general.getAirportCode())) {
							Collection<GeneralRuleConfigDetailsVO> time = general.getTimeDetails();
							for (GeneralRuleConfigDetailsVO offset : time) {
								if (offset.getParameterCode().equals("Min")) {
									parvalmin = offset.getParameterValue();
									min = Integer.parseInt(parvalmin);
								}
								if (offset.getParameterCode().equals("Hrs")) {
									parvalhr = offset.getParameterValue();
									hour = Integer.parseInt(parvalhr);
								}
							}
							arrtime.plusHours(hour);
							arrtime.plusMinutes(min);
							ghttim = arrtime;
						}
					}
				}
			} else {
				ghttim = arrtime;
			}
			mailbagVO.setGhttim(ghttim);
		}
	}
	/**
	 * @author a-1936This method is used to find the Containers and the assocaiated dsns in the Flight..
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 */
	public static MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo) {
		log.debug("UldForSegment" + " : " + "findContainersInFlight" + " Entering");
		try {
			return constructDAO().findContainersInFlightForManifest(operationalFlightVo);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * A-1739
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<ContainerDetailsVO> findArrivedContainers(MailArrivalFilterVO mailArrivalFilterVO) {
		try {
			return constructDAO().findArrivedContainers(mailArrivalFilterVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	public static MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException {
		log.debug("UldForSegment" + " : " + "findImportManifestDetails" + " Entering");
		try{
			return constructDAO().findImportManifestDetails(operationalFlightVo);
		}catch(PersistenceException ex ){
			throw new SystemException(ex.getMessage());
		}
	}

}
