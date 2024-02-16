package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ULDDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/** 
 * @author A-3109This class represents each segment of the assigned flight
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(AssignedFlightSegmentPK.class)
@Table(name = "MALFLTSEG")
public class AssignedFlightSegment extends BaseEntity implements Serializable {
	private static final String PRODUCT_NAME = "mail.operations";

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

	@Column(name = "POU")
	private String pou;
	@Column(name = "POL")
	private String pol;
	@Column(name = "MRASTA")
	private String mraStatus;

	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "SEGSERNUM", referencedColumnName = "SEGSERNUM", insertable = false, updatable = false) })
	private Set<ULDForSegment> containersForSegment;
	public AssignedFlightSegment() {
	}

	public AssignedFlightSegment(AssignedFlightSegmentVO assignedFlightSegmentVO) {
		populatePK(assignedFlightSegmentVO);
		populateAttributes(assignedFlightSegmentVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	public static Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
			throws SystemException {
		return constructDAO().generateDailyMailStationReport(filterVO);
		}
	/** 
	* @author a-3109 This method is used to populate the pk
	* @param assignedFlightSegmentVO
	* @throws SystemException
	*/
	private void populatePK(AssignedFlightSegmentVO assignedFlightSegmentVO) {
		log.info("INSIDE THE POPULATE SEGMNET PK");
		this.setCarrierId(assignedFlightSegmentVO.getCarrierId());
		this.setCompanyCode(assignedFlightSegmentVO.getCompanyCode());
		this.setFlightNumber(assignedFlightSegmentVO.getFlightNumber());
		this.setFlightSequenceNumber(assignedFlightSegmentVO.getFlightSequenceNumber());
		this.setSegmentSerialNumber(assignedFlightSegmentVO.getSegmentSerialNumber());
	}

	/** 
	* @author a-3109 This method is used to populate the Attributes other thanpk into the Entity
	* @param assignedFlightSegmentVO
	* @throws SystemException
	*/
	private void populateAttributes(AssignedFlightSegmentVO assignedFlightSegmentVO) {
		log.info("INSIDE THE POPULATE ATTRIBUTES");
		this.setPol(assignedFlightSegmentVO.getPol());
		this.setPou(assignedFlightSegmentVO.getPou());
		if (assignedFlightSegmentVO.getMraStatus() != null) {
			this.setMraStatus(assignedFlightSegmentVO.getMraStatus());
		} else {
			this.setMraStatus(MailConstantsVO.FLAG_NO);
		}
	}

	/** 
	* @author a-3109 This method is used to find the instance of the Entity
	* @param assignedFlightSegmentPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static AssignedFlightSegment find(AssignedFlightSegmentPK assignedFlightSegmentPK) throws FinderException {
		log.info("INSIDE THE FINDER METHOD");
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(AssignedFlightSegment.class, assignedFlightSegmentPK);
	}

	/** 
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
	* @author A-5991
	* @param mailAcceptanceVO
	* @param containerDetailsForSeg
	* @return
	* @throws SystemException
	*/
	public boolean saveFlightAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO,
			Collection<ContainerDetailsVO> containerDetailsForSeg) {
		boolean hasUpdated = false;
		log.debug("AssignedFlightSegment" + " : " + "saveULDForSegments" + " Entering");
		log.debug("" + "Containers of asg flt-> " + " " + containerDetailsForSeg);
		for (ContainerDetailsVO containerDetailsVO : containerDetailsForSeg) {
			if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
				insertContainerAcceptanceDetails(containerDetailsVO, mailAcceptanceVO);
				hasUpdated = true;
			} else if (ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag())) {
				updateContainerAcceptanceDetails(containerDetailsVO, mailAcceptanceVO);
				hasUpdated = true;
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "saveULDForSegments" + " Exiting");
		return hasUpdated;
	}

	/** 
	* @author a-5991
	* @param containerDetailsVO
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	private void insertContainerAcceptanceDetails(ContainerDetailsVO containerDetailsVO,
			MailAcceptanceVO mailAcceptanceVO) {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("AssignedFlightSegment" + " : " + "insertContainerDetails" + " Entering");
		FlightDetailsVO flightDetailsVO = null;
		Collection<ULDInFlightVO> uldInFlightVos = null;
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		uldForSegmentVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		uldForSegmentVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		int bags = 0;
		double weight = 0;
		if (containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
				bags = bags + 1;
				if (mailbagVO.getWeight() != null) {
					weight = weight + mailbagVO.getWeight().getRoundedValue().doubleValue();
				}
			}
		}
		uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		uldForSegmentVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
		uldForSegmentVO.setNoOfBags(bags);
		uldForSegmentVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(weight)));
		ULDForSegment uldForSegment = null;
		boolean isBulkContainer = false;
		if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
			uldForSegmentVO.setUldNumber(containerDetailsVO.getContainerNumber());
			uldForSegmentVO.setRemarks(containerDetailsVO.getRemarks());
			uldForSegmentVO.setWarehouseCode(containerDetailsVO.getWareHouse());
			uldForSegmentVO.setLocationCode(containerDetailsVO.getLocation());
			uldForSegmentVO.setOnwardRoutings(containerDetailsVO.getOnwardRoutingForSegmentVOs());
			uldForSegmentVO.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
			log.debug("COnatiner case finding the ULDSegment");
			try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
				log.debug("FinderException....");
			}
			log.debug("" + "ULDsegment after find" + " " + uldForSegment);
			if (uldForSegment == null) {
				log.debug("Container ULDSEgment not found, creating....");
				uldForSegment = createULDForSegment(uldForSegmentVO);
				boolean isUMSUpdateNeeded = ContextUtil.getInstance().getBean(MailController.class).isULDIntegrationEnabled();
				if (isUMSUpdateNeeded) {
					OperationalFlightVO operationalFlightVo = constructOprFlightFromMailAcp(mailAcceptanceVO);
					ULDInFlightVO uldFltVo = new ULDInFlightVO();
					uldFltVo.setUldNumber(containerDetailsVO.getContainerNumber());
					uldFltVo.setPointOfLading(containerDetailsVO.getPol());
					uldFltVo.setPointOfUnLading(containerDetailsVO.getPou());
					uldFltVo.setRemark("Mail ULD Assigned");
					updateULDForOperationsForContainerAcceptance(operationalFlightVo, uldFltVo);
				}
			} else {
				if (uldForSegment.getRemarks() != null
						&& !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())) {
					uldForSegment.setRemarks(containerDetailsVO.getRemarks());
				}
				if (uldForSegment.getWarehouseCode() != null
						&& !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())) {
					uldForSegment.setWarehouseCode(containerDetailsVO.getWareHouse());
				}
				if (uldForSegment.getLocationCode() != null
						&& !uldForSegment.getLocationCode().equals(containerDetailsVO.getLocation())) {
					uldForSegment.setLocationCode(containerDetailsVO.getLocation());
				}
				if (uldForSegment.getTransferFromCarrier() != null && !uldForSegment.getTransferFromCarrier()
						.equals(containerDetailsVO.getTransferFromCarrier())) {
					uldForSegment.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
				}
			}
		} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
			isBulkContainer = true;
			uldForSegmentVO.setUldNumber(new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(containerDetailsVO.getDestination()).toString());
			try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
			}
			if (uldForSegment == null) {
				log.debug("BULK ULDSEgment not found, creating....");
				uldForSegment = createULDForSegment(uldForSegmentVO);
			}
		}
		uldForSegment.saveAcceptanceDetails(mailAcceptanceVO, containerDetailsVO);
		log.debug("AssignedFlightSegment" + " : " + "insertContainerDetails" + " Exiting");
	}

	/** 
	* @author a-5991
	* @param uldForSegmentPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static ULDForSegment findULDForSegment(ULDForSegmentPK uldForSegmentPK) throws FinderException {
		return ULDForSegment.find(uldForSegmentPK);
	}

	/** 
	* @author A-5991
	* @param uldForSegmentVO
	* @return
	*/
	private ULDForSegmentPK constructULDForSegmentPK(ULDForSegmentVO uldForSegmentVO) {
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldForSegmentPK.setUldNumber(uldForSegmentVO.getUldNumber());
		uldForSegmentPK.setCarrierId(uldForSegmentVO.getCarrierId());
		uldForSegmentPK.setFlightNumber(uldForSegmentVO.getFlightNumber());
		uldForSegmentPK.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
		uldForSegmentPK.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
		return uldForSegmentPK;
	}

	/** 
	* @author a-5991
	* @param uldForSegmentVO
	* @return
	* @throws SystemException
	*/
	public ULDForSegment createULDForSegment(ULDForSegmentVO uldForSegmentVO) {
		if (getContainersForSegment() == null) {
			containersForSegment = new HashSet<ULDForSegment>();
		}
		ULDForSegment uldForSegment = new ULDForSegment(uldForSegmentVO);
		containersForSegment.add(uldForSegment);
		return uldForSegment;
	}

	/** 
	* @author A-5991
	*/
	private String constructBulkULDNumber() {
		return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR).append(getPou())
				.toString();
	}

	/** 
	* @author a-1739
	* @param containerDetailsVO
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	private void updateContainerAcceptanceDetails(ContainerDetailsVO containerDetailsVO,
			MailAcceptanceVO mailAcceptanceVO) {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("AssignedFlightSegment" + " : " + "updateContainerAcceptanceDetails" + " Exiting");
		int bags = 0;
		double weight = 0;
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		uldForSegmentVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		uldForSegmentVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		if (containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
				bags = bags + 1;
				if (mailbagVO.getWeight() != null) {
					weight = weight + mailbagVO.getWeight().getRoundedValue().doubleValue();
				}
			}
		}
		uldForSegmentVO.setNoOfBags(bags);
		uldForSegmentVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(weight)));
		uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		uldForSegmentVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
		ULDForSegment uldForSegment = null;
		boolean isBulkContainer = false;
		if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
			uldForSegmentVO.setUldNumber(containerDetailsVO.getContainerNumber());
			try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
				log.debug("Finder Exception ULDSEgment not found..");
			}
			if (uldForSegment != null) {
				if (uldForSegment.getRemarks() != null
						&& !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())) {
					uldForSegment.setRemarks(containerDetailsVO.getRemarks());
				}
				if (uldForSegment.getWarehouseCode() != null
						&& !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())) {
					uldForSegment.setWarehouseCode(containerDetailsVO.getWareHouse());
				}
				if (uldForSegment.getLocationCode() != null
						&& !uldForSegment.getLocationCode().equals(containerDetailsVO.getLocation())) {
					uldForSegment.setLocationCode(containerDetailsVO.getLocation());
				}
				uldForSegment.updateOnwardRoutes(containerDetailsVO.getOnwardRoutingForSegmentVOs());
				if (mailAcceptanceVO.isScanned() && (containerDetailsVO.getTransferFromCarrier() == null
						|| containerDetailsVO.getTransferFromCarrier().isEmpty())) {
				} else if (uldForSegment.getTransferFromCarrier() != null && !uldForSegment.getTransferFromCarrier()
						.equals(containerDetailsVO.getTransferFromCarrier())) {
					uldForSegment.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
				} else {
				}
			} else {
				throw new SystemException("No such ULD for update");
			}
		} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
			isBulkContainer = true;
			uldForSegmentVO.setUldNumber(constructBulkULDNumber());
			try {
				uldForSegment = findULDForSegment(constructULDForSegmentPK(uldForSegmentVO));
			} catch (FinderException e) {
			}
			if (uldForSegment != null) {
				if (uldForSegment.getWarehouseCode() != null
						&& !uldForSegment.getWarehouseCode().equals(containerDetailsVO.getWareHouse())) {
					uldForSegment.setWarehouseCode(containerDetailsVO.getWareHouse());
				}
			} else {
				log.debug("BULK ULDSEgment not found, creating....");
				throw new SystemException("No such BULK-POU ULD for update");
			}
		}
		uldForSegment.saveAcceptanceDetails(mailAcceptanceVO, containerDetailsVO);
		log.debug("AssignedFlightSegment" + " : " + "updateContainerAcceptanceDetails" + " Exiting");
	}

	/** 
	* TODO Purpose Dec 5, 2006, a-1739
	* @param containerVO
	* @throws SystemException
	*/
	public void updateULDOnwardRoute(ContainerVO containerVO) {
		log.debug("AssignedFlightSegment" + " : " + "updateULDOnwardRoute" + " Entering");
		ULDForSegment uldForSegment = null;
		if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			containerVO.setContainerNumber(new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(containerVO.getPou()).toString());
		}
		try {
			uldForSegment = ULDForSegment.find(constructULDForSegPK(containerVO));
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegs = constructOnwardRoutingForSegments(
				containerVO.getOnwardRoutings());
		uldForSegment.updateOnwardRoutes(onwardRouteForSegs);
		log.debug("AssignedFlightSegment" + " : " + "updateULDOnwardRoute" + " Exiting");
	}

	/** 
	* TODO Purpose Dec 5, 2006, a-1739
	* @param containerVO
	* @return
	*/
	private ULDForSegmentPK constructULDForSegPK(ContainerVO containerVO) {
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
		uldForSegPK.setCompanyCode(containerVO.getCompanyCode());
		uldForSegPK.setCarrierId(containerVO.getCarrierId());
		uldForSegPK.setFlightNumber(containerVO.getFlightNumber());
		uldForSegPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		uldForSegPK.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		uldForSegPK.setUldNumber(containerVO.getContainerNumber());
		return uldForSegPK;
	}

	/** 
	* @author A-5991
	* @param onwardRoutings
	* @return
	*/
	private Collection<OnwardRouteForSegmentVO> constructOnwardRoutingForSegments(
			Collection<OnwardRoutingVO> onwardRoutings) {
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegmentVOs = new ArrayList<OnwardRouteForSegmentVO>();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			for (OnwardRoutingVO onwardRoutingVO : onwardRoutings) {
				OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
				onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO.getOnwardCarrierCode());
				onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
				onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO.getOnwardFlightDate());
				onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO.getOnwardCarrierId());
				onwardRouteForSegmentVO.setPou(onwardRoutingVO.getPou());
				onwardRouteForSegmentVO.setRoutingSerialNumber(onwardRoutingVO.getRoutingSerialNumber());
				onwardRouteForSegmentVO.setOperationFlag(onwardRoutingVO.getOperationFlag());
				onwardRouteForSegmentVOs.add(onwardRouteForSegmentVO);
			}
		}
		return onwardRouteForSegmentVOs;
	}

	/** 
	* @author a-1739
	* @param mailbags
	* @return
	* @throws SystemException
	*/
	public Collection<ContainerDetailsVO> reassignMailFromFlight(Collection<MailbagVO> mailbags) {
		log.debug("AssignedFlightSegment" + " : " + "reassignMailbagsFromFlight" + " Entering");
		Map<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULDSeg = groupMailbagsOfULDSeg(mailbags);
		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();
		ULDForSegment uldForSegment = null;
		boolean isInbound = false;
		for (MailbagVO mailbagVO : mailbags) {
			if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())) {
				isInbound = true;
			}
			break;
		}
		try {
			for (Map.Entry<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULD : mailbagsOfULDSeg.entrySet()) {
				Collection<MailbagVO> mailbagsToRem = mailbagsOfULD.getValue();
				ULDForSegmentPK uldForSegmentPK = mailbagsOfULD.getKey();
				uldForSegment = ULDForSegment.find(uldForSegmentPK);
				uldForSegment.reassignMailFromFlight(mailbagsToRem, isInbound);
				if (isInbound && (uldForSegment.getMailBagInULDForSegments() == null
						|| uldForSegment.getMailBagInULDForSegments().isEmpty())) {
					if (uldForSegment.getUldNumber().startsWith(MailConstantsVO.CONST_BULK)
							&& (uldForSegment.getMailBagInULDForSegments() == null
									|| uldForSegment.getMailBagInULDForSegments().isEmpty())) {
						this.getContainersForSegment().remove(uldForSegment);
						uldForSegment.remove();
						log.debug("uldforseg removed");
					} else if (!(uldForSegment.getUldNumber()
							.startsWith(MailConstantsVO.CONST_BULK))) {
						ContainerDetailsVO containerDetailsVO = constructULDToReturnForMailbag(uldForSegmentPK,
								mailbagsToRem);
						if (checkForFoundULD(containerDetailsVO)) {
							containersToReturn.add(containerDetailsVO);
						}
					}
				} else if (!isInbound && uldForSegment.getNumberOfBags() == 0) {
					if (uldForSegment.getUldNumber().startsWith(MailConstantsVO.CONST_BULK)) {
						log.debug("uldforseg removed");
					} else {
						containersToReturn.add(constructULDToReturnForMailbag(uldForSegmentPK, mailbagsToRem));
					}
				}
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		log.debug("AssignedFlightSegment" + " : " + "reassignMailbagsFromFlight" + " Exiting");
		return containersToReturn;
	}

	/** 
	* Checks if this is a found container, i.e., a ULD added at the destination Dec 6, 2006, a-1739
	* @param containerDetailsVO
	* @return
	* @throws SystemException
	*/
	private boolean checkForFoundULD(ContainerDetailsVO containerDetailsVO) {
		log.debug("AssignedFlightSegment" + " : " + "checkForFoundContainer" + " Entering");
		ContainerVO containerVO = constructContainerVOFromDetails(containerDetailsVO);
		Container container = null;
		try {
			container = Container.find(constructContainerPK(containerVO));
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		return (MailConstantsVO.FLAG_NO.equals(container.getAcceptanceFlag())
				&& MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus()));
	}

	/** 
	* Copies the details from containerdetailsVO and Finds the legserial number of this container too A-1739
	* @param containerDetailsVO
	* @return
	* @throws SystemException
	*/
	private ContainerVO constructContainerVOFromDetails(ContainerDetailsVO containerDetailsVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(this.getCompanyCode());
		containerVO.setCarrierId(this.getCarrierId());
		containerVO.setFlightNumber(this.getFlightNumber());
		containerVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		containerVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setType(containerDetailsVO.getContainerType());
		containerVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		containerVO.setAssignedPort(containerDetailsVO.getPol());
		containerVO.setFinalDestination(containerDetailsVO.getDestination());
		containerVO.setRemarks(containerDetailsVO.getRemarks());
		containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
		containerVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
		containerVO.setDeliveredStatus(containerDetailsVO.getDeliveredStatus());
		containerVO.setAssignedDate(localDateUtil.getLocalDate(containerDetailsVO.getPou(), true));
		containerVO.setPou(containerDetailsVO.getPou());
		containerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		if (containerDetailsVO.getLegSerialNumber() == 0) {
			FlightValidationVO flightValidationVO = validateFlightForContainer(containerDetailsVO);
			containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		} else {
			containerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		}
		containerVO.setContainerJnyID(containerDetailsVO.getContainerJnyId());
		containerVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
		containerVO.setIntact(containerDetailsVO.getIntact());
		if (containerDetailsVO.getLastUpdateTime() != null)
			containerVO.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
		if (containerDetailsVO.getUldLastUpdateTime() != null)
			containerVO.setULDLastUpdateTime(containerDetailsVO.getUldLastUpdateTime());
		return containerVO;
	}

	/** 
	* @author a-1739
	* @param uldForSegmentPK
	* @param mailbags
	* @return
	*/
	private ContainerDetailsVO constructULDToReturnForMailbag(ULDForSegmentPK uldForSegmentPK,
			Collection<MailbagVO> mailbags) {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(uldForSegmentPK.getCompanyCode());
		containerDetailsVO.setCarrierId(uldForSegmentPK.getCarrierId());
		containerDetailsVO.setFlightNumber(uldForSegmentPK.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
		containerDetailsVO.setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
		containerDetailsVO.setContainerNumber(uldForSegmentPK.getUldNumber());
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		for (MailbagVO mailbagVO : mailbags) {
			containerDetailsVO.setDestination(mailbagVO.getFinalDestination());
			containerDetailsVO.setPou(getPou());
			containerDetailsVO.setPol(getPol());
			containerDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
			containerDetailsVO.setFlightDate(mailbagVO.getFlightDate());
			containerDetailsVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			containerDetailsVO.setFlagPAULDResidit(mailbagVO.getFlagPAULDResidit());
			break;
		}
		return containerDetailsVO;
	}

	/** 
	* A-1739
	* @param mailbags
	* @return
	* @throws SystemException
	*/
	private Map<ULDForSegmentPK, Collection<MailbagVO>> groupMailbagsOfULDSeg(Collection<MailbagVO> mailbags) {
		Map<ULDForSegmentPK, Collection<MailbagVO>> mailbagsOfULDMap = new HashMap<ULDForSegmentPK, Collection<MailbagVO>>();
		Collection<MailbagVO> mailbagsOfULD = null;
		for (MailbagVO mailbagVO : mailbags) {
			ULDForSegmentPK uldForSegPK = constructULDForSegPKFromMailbag(mailbagVO);
			mailbagsOfULD = mailbagsOfULDMap.get(uldForSegPK);
			if (mailbagsOfULD == null) {
				mailbagsOfULD = new ArrayList<MailbagVO>();
				mailbagsOfULDMap.put(uldForSegPK, mailbagsOfULD);
			}
			mailbagsOfULD.add(mailbagVO);
		}
		return mailbagsOfULDMap;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	private ULDForSegmentPK constructULDForSegPKFromMailbag(MailbagVO mailbagVO) {
		log.debug("" + "THE MAIL BAG VO IS" + " " + mailbagVO);
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
		uldForSegPK.setCompanyCode(this.getCompanyCode());
		uldForSegPK.setCarrierId(this.getCarrierId());
		uldForSegPK.setFlightNumber(this.getFlightNumber());
		uldForSegPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegPK.setUldNumber(mailbagVO.getContainerNumber()!=null?mailbagVO.getContainerNumber():mailbagVO.getUldNumber());
		if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
			uldForSegPK.setUldNumber(constructBulkULDNumber());
		}
		return uldForSegPK;
	}

	/** 
	* @author a-1739
	* @param containerVO
	* @return
	*/
	private ContainerPK constructContainerPK(ContainerVO containerVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(containerVO.getCompanyCode());
		containerPK.setCarrierId(containerVO.getCarrierId());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		containerPK.setAssignmentPort(containerVO.getAssignedPort());
		containerPK.setFlightNumber(containerVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		return containerPK;
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @return
	* @throws SystemException
	*/
	private FlightValidationVO validateFlightForContainer(ContainerDetailsVO containerDetailsVO) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		Collection<FlightValidationVO> flightValidationVOs = null;
		flightValidationVOs = flightOperationsProxy
				.validateFlightForAirport(constructFlightFilterVOForContainer(containerDetailsVO));
		for (FlightValidationVO flightValidationVO : flightValidationVOs) {
			if (flightValidationVO.getFlightSequenceNumber() == containerDetailsVO.getFlightSequenceNumber()) {
				return flightValidationVO;
			}
		}
		return null;
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @return
	*/
	private FlightFilterVO constructFlightFilterVOForContainer(ContainerDetailsVO containerDetailsVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		//TODO: Neo to update
		//flightFilterVO.setFlightDate(containerDetailsVO.getFlightDate());
		flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		flightFilterVO.setStation(containerDetailsVO.getPou());
		return flightFilterVO;
	}

	/** 
	* @author a-1739
	* @param mailbagsToAdd
	* @param toContainerVO
	* @throws SystemException
	*/
	public void reassignMailToFlight(Collection<MailbagVO> mailbagsToAdd, ContainerVO toContainerVO) {
		log.debug("AssignedFlightSegment" + " : " + "reassignMailToFlight" + " Entering");
		String uldNumber = toContainerVO.getContainerNumber();
		if (MailConstantsVO.BULK_TYPE.equals(toContainerVO.getType())) {
			uldNumber = constructBulkULDNumber();
		}
		ULDForSegment uldForSegment = findULDForSegmentWithULDNumber(uldNumber);
		if (uldForSegment == null) {
			uldForSegment = createULDForSegment(constructULDForSegmentFromCon(toContainerVO, uldNumber));
			boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
			if (isUMSUpdateNeeded && MailConstantsVO.ULD_TYPE.equals(toContainerVO.getType())) {
				updateULDForOperations(toContainerVO);
			}
			if (getContainersForSegment() == null) {
				setContainersForSegment(new HashSet<ULDForSegment>());
			}
			getContainersForSegment().add(uldForSegment);
		}
		uldForSegment.reassignMailToFlight(mailbagsToAdd, toContainerVO);
		log.debug("AssignedFlightSegment" + " : " + "reassignMailToFlight" + " Exiting");
	}

	/** 
	* A-1739
	* @param uldNumber
	* @return
	*/
	private ULDForSegment findULDForSegmentWithULDNumber(String uldNumber) {
		if (getContainersForSegment() != null) {
			for (ULDForSegment uldForSegment : containersForSegment) {
				if (uldForSegment.getUldNumber().equals(uldNumber)) {
					return uldForSegment;
				}
			}
		}
		return null;
	}

	/** 
	* A-1739
	* @param toContainerVO
	* @param uldNumber
	* @return
	*/
	private ULDForSegmentVO constructULDForSegmentFromCon(ContainerVO toContainerVO, String uldNumber) {
		ULDForSegmentVO uldForSegVO = new ULDForSegmentVO();
		uldForSegVO.setUldNumber(uldNumber);
		uldForSegVO.setCompanyCode(this.getCompanyCode());
		uldForSegVO.setCarrierId(this.getCarrierId());
		uldForSegVO.setFlightNumber(this.getFlightNumber());
		uldForSegVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
		return uldForSegVO;
	}

	public static int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO) {
		try {
			return constructDAO().findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
	}

	/** 
	* @author a-1739
	* @param uldSegment
	* @throws SystemException
	*/
	public void removeULDForSegment(ULDForSegment uldSegment) {
		uldSegment.remove();
	}

	/** 
	* @author a-1739
	* @param uldForSegment
	* @param containerNumber
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagInULDForSegmentVO> reassignBulkContainer(ULDForSegment uldForSegment,
			String containerNumber) {
		return uldForSegment.reassignBulkContainer(containerNumber);
	}

	/** 
	* @author a-1739
	* @param toULDForSegment
	* @throws SystemException
	*/
	public void assignBulkContainer(ULDForSegment toULDForSegment,
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs) {
		toULDForSegment.assignBulkContainer(mailbagInULDForSegmentVOs);
	}

	/** 
	* @author a-1883
	* @param containerVOs
	* @param uLDForSegmentVOs
	* @throws SystemException
	*/
	public void saveOutboundDetailsForTransfer(Collection<ContainerVO> containerVOs,
			Collection<ULDForSegmentVO> uLDForSegmentVOs) {
		log.debug("AssignedFlightSegment" + " : " + "saveOutboundDetailsForTransfer" + " Entering");
		for (ULDForSegmentVO uldForSegmentVO : uLDForSegmentVOs) {
			for (ContainerVO containerVO : containerVOs) {
				String bulkNumber = null;
				if (uldForSegmentVO.getUldNumber().equals(containerVO.getContainerNumber())) {
					ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
					uLDForSegmentPK.setCarrierId(this.getCarrierId());
					uLDForSegmentPK.setCompanyCode(this.getCompanyCode());
					uLDForSegmentPK.setFlightNumber(this.getFlightNumber());
					uLDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
					uLDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
					if (containerVO.isUldTobarrow()) {
						bulkNumber = constructBulkULDNumber(containerVO.getFinalDestination(),
								containerVO.getCarrierCode());
						if (bulkNumber != null) {
							uLDForSegmentPK.setUldNumber(bulkNumber);
						}
					} else {
						uLDForSegmentPK.setUldNumber(containerVO.getContainerNumber());
					}
					ULDForSegment uldForSegment = null;
					try {
						uldForSegment = findULDForSegment(uLDForSegmentPK);
					} catch (FinderException e) {
					}
					if (uldForSegment != null) {
						if (containerVO.isUldTobarrow()) {
							uldForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
						} else {
							uldForSegment.saveOutboundDetailsForTransfer(uldForSegmentVO.getDsnInULDForSegmentVOs(),
									containerVO);
						}
						uldForSegment.saveOutboundDetailsForTransfer(uldForSegmentVO.getDsnInULDForSegmentVOs(),
								containerVO);
						if (uldForSegment.getTransferFromCarrier() != null
								&& !uldForSegment.getTransferFromCarrier().equals(containerVO.getCarrierCode())) {
							uldForSegment.setTransferFromCarrier(containerVO.getCarrierCode());
						}
					} else {
						modifyULDForSegmentVO(uldForSegmentVO, containerVO);
						if (containerVO.isUldTobarrow()) {
							bulkNumber = constructBulkULDNumber(containerVO.getFinalDestination(),
									containerVO.getCarrierCode());
							if (bulkNumber != null) {
								uldForSegmentVO.setUldNumber(bulkNumber);
							}
						}
						if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
								&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
							uldForSegmentVO.getMailbagInULDForSegmentVOs()
									.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
											.setGhttim(containerVO.getGHTtime()));
						}
						uldForSegment = new ULDForSegment(uldForSegmentVO);
						if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
							boolean isUMSUpdateNeeded = new MailController().isULDIntegrationEnabled();
							if (isUMSUpdateNeeded && ContainerVO.OPERATION_FLAG_INSERT.equals(containerVO.getOperationFlag())) {
								updateULDForOperations(containerVO);
							}
						}
						if (getContainersForSegment() == null) {
							log.debug("ADDDED  THE CONTAINERS TO THE SEGMENT");
							setContainersForSegment(new HashSet<ULDForSegment>());
						}
						getContainersForSegment().add(uldForSegment);
					}
					break;
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "saveOutboundDetailsForTransfer" + " Exiting");
	}

	/** 
	* @author a-1936 This method is used to reassign the DSN from Flight
	* @param despatchDetailsVOs
	* @return
	* @throws SystemException
	*/
	public Collection<ContainerDetailsVO> reassignDSNsFromFlight(Collection<DespatchDetailsVO> despatchDetailsVOs) {
		log.debug("AssignedFlightSegment" + " : " + "reassignDSNFromFlight" + " Entering");
		Map<ULDForSegmentPK, Collection<DespatchDetailsVO>> uldForSegmentMap = new HashMap<ULDForSegmentPK, Collection<DespatchDetailsVO>>();
		boolean isInbound = false;
		for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
			ULDForSegmentPK uldForSegmentPK = constructULDForSegmentPK(despatchDetailsVO);
			Collection<DespatchDetailsVO> despatchDetailVos = uldForSegmentMap.get(uldForSegmentPK);
			if (despatchDetailVos == null) {
				despatchDetailVos = new ArrayList<DespatchDetailsVO>();
				uldForSegmentMap.put(uldForSegmentPK, despatchDetailVos);
			}
			despatchDetailVos.add(despatchDetailsVO);
			if (MailConstantsVO.OPERATION_INBOUND.equals(despatchDetailsVO.getOperationType())) {
				isInbound = true;
			}
		}
		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();
		if (uldForSegmentMap != null && uldForSegmentMap.size() > 0) {
			for (ULDForSegmentPK uldForSegmentPK : uldForSegmentMap.keySet()) {
				Collection<DespatchDetailsVO> despatchDetailsFromMap = uldForSegmentMap.get(uldForSegmentPK);
				ULDForSegment uldForSegment = null;
				try {
					uldForSegment = ULDForSegment.find(uldForSegmentPK);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				uldForSegment.reassignDSNsFromFlight(despatchDetailsFromMap, isInbound);
				if (uldForSegment.getNumberOfBags() == 0 && !isInbound) {
					if (uldForSegment.getUldNumber().startsWith(MailConstantsVO.CONST_BULK)) {
						uldForSegment.remove();
					} else {
						containersToReturn
								.add(constructULDToReturnForDespatch(uldForSegmentPK, despatchDetailsFromMap));
					}
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "reassignDSNsFromFlight" + " Exiting");
		return containersToReturn;
	}

	/** 
	* @param uldForSegmentVO
	* @param containerVO
	* @throws SystemException
	*/
	private void modifyULDForSegmentVO(ULDForSegmentVO uldForSegmentVO, ContainerVO containerVO) {
		log.debug("AssignedFlightSegment" + " : " + "modifyULDForSegmentVO" + " Entering");
		uldForSegmentVO.setCarrierId(this.getCarrierId());
		uldForSegmentVO.setCompanyCode(this.getCompanyCode());
		uldForSegmentVO.setFlightNumber(this.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegmentVO.setRemarks(containerVO.getRemarks());
		uldForSegmentVO.setPou(this.getPou());
		Collection<OnwardRoutingVO> onwardRoutingVOs = containerVO.getOnwardRoutings();
		if (onwardRoutingVOs != null && onwardRoutingVOs.size() > 0) {
			addNewRoutingDetails(onwardRoutingVOs, uldForSegmentVO);
		}
		if (containerVO.isUldTobarrow()) {
			String bulkNumber = constructBulkULDNumber(containerVO.getFinalDestination(), containerVO.getCarrierCode());
			if (bulkNumber != null) {
				uldForSegmentVO.setUldNumber(bulkNumber);
			}
		} else {
			uldForSegmentVO.setUldNumber(containerVO.getContainerNumber());
		}
	}

	private String constructBulkULDNumber(String airport, String carrierCode) {
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/** 
	* @param onwardRoutingVOs
	* @param uLDForSegmentVO
	* @throws SystemException
	*/
	private void addNewRoutingDetails(Collection<OnwardRoutingVO> onwardRoutingVOs, ULDForSegmentVO uLDForSegmentVO) {
		log.debug("AssignedFlightSegment" + " : " + "addNewRoutingDetails" + " Entering");
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegmentVOs = new ArrayList<OnwardRouteForSegmentVO>();
		if (uLDForSegmentVO.getOnwardRoutings() != null && uLDForSegmentVO.getOnwardRoutings().size() > 0) {
			uLDForSegmentVO.getOnwardRoutings().clear();
		}
		for (OnwardRoutingVO onwardRoutingVO : onwardRoutingVOs) {
			OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
			onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO.getOnwardCarrierCode());
			onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO.getOnwardCarrierId());
			onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO.getOnwardFlightDate());
			onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
			onwardRouteForSegmentVO.setPou(onwardRoutingVO.getPou());
			onwardRouteForSegmentVO.setRoutingSerialNumber(onwardRoutingVO.getRoutingSerialNumber());
			onwardRouteForSegmentVOs.add(onwardRouteForSegmentVO);
		}
		uLDForSegmentVO.setOnwardRoutings(onwardRouteForSegmentVOs);
		log.debug("AssignedFlightSegment" + " : " + "addNewRoutingDetails" + " Exiting");
	}

	/** 
	* This method is used to create the ULDForSegmentPK from the despatchDetailsVO
	* @param despatchDetailsVO
	* @return
	*/
	private ULDForSegmentPK constructULDForSegmentPK(DespatchDetailsVO despatchDetailsVO) {
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
		uldForSegPK.setCompanyCode(this.getCompanyCode());
		uldForSegPK.setCarrierId(this.getCarrierId());
		uldForSegPK.setFlightNumber(this.getFlightNumber());
		uldForSegPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegPK.setUldNumber(despatchDetailsVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(despatchDetailsVO.getContainerType())) {
			uldForSegPK.setUldNumber(constructBulkULDNumber());
		}
		return uldForSegPK;
	}

	/** 
	* @author a-1883
	* @param containerVOs
	* @param toFlightVO TODO
	* @throws SystemException
	* @return Collection<ULDForSegmentVO>
	*/
	public Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO toFlightVO, Map<MailbagPK, MailbagVO> mailMap) {
		log.debug("AssignedFlightSegment" + " : " + "saveArrivalDetailsForTransfer" + " Entering");
		Container container = null;
		Collection<ULDForSegmentVO> uLDForSegmentVOs = new ArrayList<ULDForSegmentVO>();
		for (ContainerVO containerVO : containerVOs) {
			ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
			uLDForSegmentPK.setCarrierId(containerVO.getCarrierId());
			uLDForSegmentPK.setCompanyCode(containerVO.getCompanyCode());
			uLDForSegmentPK.setFlightNumber(containerVO.getFlightNumber());
			uLDForSegmentPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			uLDForSegmentPK.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			uLDForSegmentPK.setUldNumber(containerVO.getContainerNumber());
			ULDForSegment uldForSegment = null;
			try {
				uldForSegment = findULDForSegment(uLDForSegmentPK);
			} catch (FinderException e) {
			}
			if (uldForSegment == null) {
				throw new SystemException("	NO ULD FOR SEGMENT FOUND ");
			}
			uLDForSegmentVOs.add(updateTransferDetailsForULDSegVO(uldForSegment.retrieveVO(), containerVO));
			boolean isArrived = false;
			container = findContainer(containerVO);
			if(Objects.nonNull(containerVO.getLastUpdateTime())) {
				container.setLastUpdatedTime(Timestamp.valueOf(containerVO.getLastUpdateTime().toLocalDateTime()));
			}
			container.setLastUpdatedUser(containerVO.getLastUpdateUser());
			if (!toFlightVO.isTransferOutOperation()) {
				uldForSegment.setTransferToCarrier(toFlightVO.getCarrierCode());
			} else {
				isArrived = true;
			}
			uldForSegment.saveArrivalDetailsForTransfer(mailMap, isArrived, toFlightVO);
			uldForSegment.updateUldAcquittalStatus();
			if (MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())) {
				container.setTransitFlag(MailConstantsVO.FLAG_NO);
				if (toFlightVO.isTransferOutOperation()) {
					container.setArrivedStatus(MailConstantsVO.FLAG_YES);
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "saveArrivalDetailsForTransfer" + " Exiting");
		return uLDForSegmentVOs;
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	private Container findContainer(ContainerVO containerVO) {
		log.debug("MailTransfer" + " : " + "findContainer" + " Entering");
		Container container = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(containerVO.getAssignedPort());
		containerPK.setCarrierId(containerVO.getCarrierId());
		containerPK.setCompanyCode(containerVO.getCompanyCode());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		containerPK.setFlightNumber(containerVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		try {
			container = Container.find(containerPK);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(), finderException.getMessage(), finderException);
		}
		log.debug("MailTransfer" + " : " + "findContainer" + " Exiting");
		return container;
	}

	/** 
	* Oct 27, 2006, a-1739
	* @param containerVO TODO
	* @return
	* @throws SystemException 
	*/
	private ULDForSegmentVO updateTransferDetailsForULDSegVO(ULDForSegmentVO uldForSegmentVO, ContainerVO containerVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		uldForSegmentVO.setTransferFromCarrier(containerVO.getCarrierCode());
		Collection<DSNInULDForSegmentVO> dsnInULDForSegs = uldForSegmentVO.getDsnInULDForSegmentVOs();
		Collection<MailbagInULDForSegmentVO> mailbagInULDForSegments = uldForSegmentVO.getMailbagInULDForSegmentVOs();
		if (dsnInULDForSegs != null && dsnInULDForSegs.size() > 0) {
			for (DSNInULDForSegmentVO dsnInULDForSegmentVO : dsnInULDForSegs) {
				Collection<MailbagInULDForSegmentVO> mailbagsInULD = dsnInULDForSegmentVO.getMailBags();
				if (mailbagsInULD != null && mailbagsInULD.size() > 0) {
					Collection<MailbagInULDForSegmentVO> mailbagsToRem = new ArrayList<MailbagInULDForSegmentVO>();
					for (MailbagInULDForSegmentVO mailbagInULD : mailbagsInULD) {
						if (MailConstantsVO.FLAG_YES.equals(mailbagInULD.getTransferFlag())
								|| MailConstantsVO.FLAG_YES.equals(mailbagInULD.getDeliveredStatus())) {
							mailbagsToRem.add(mailbagInULD);
						} else {
							mailbagInULD.setArrivalFlag(MailConstantsVO.FLAG_NO);
							mailbagInULD.setTransferFlag(MailConstantsVO.FLAG_NO);
							mailbagInULD.setDeliveredStatus(MailConstantsVO.FLAG_NO);
							mailbagInULD.setTransferFromCarrier(containerVO.getCarrierCode());
						}
						mailbagInULD.setScannedPort(containerVO.getPou());
						mailbagInULD.setScannedDate(localDateUtil.getLocalDate(containerVO.getPou(), true));
						log.debug("mailbagInULD.setScannedDate" + mailbagInULD.getScannedDate());
					}
					mailbagsInULD.removeAll(mailbagsToRem);
				}
			}
		}
		if (mailbagInULDForSegments != null && mailbagInULDForSegments.size() > 0) {
			LoginProfile logonVO = contextUtil.callerLoginProfile();
			for (MailbagInULDForSegmentVO mailbagInULDForSegment : mailbagInULDForSegments) {
				mailbagInULDForSegment.setScannedPort(logonVO.getAirportCode());
			}
		}
		log.debug("" + " uldforsgvotosaveob " + " " + uldForSegmentVO);
		return uldForSegmentVO;
	}

	/** 
	* @author a-1739
	* @param uldForSegmentPK
	* @param despatchDetails
	* @return
	*/
	private ContainerDetailsVO constructULDToReturnForDespatch(ULDForSegmentPK uldForSegmentPK,
			Collection<DespatchDetailsVO> despatchDetails) {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(uldForSegmentPK.getCompanyCode());
		containerDetailsVO.setCarrierId(uldForSegmentPK.getCarrierId());
		containerDetailsVO.setFlightNumber(uldForSegmentPK.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(uldForSegmentPK.getFlightSequenceNumber());
		containerDetailsVO.setSegmentSerialNumber(uldForSegmentPK.getSegmentSerialNumber());
		containerDetailsVO.setContainerNumber(uldForSegmentPK.getUldNumber());
		containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		for (DespatchDetailsVO despatchDetailsVO : despatchDetails) {
			containerDetailsVO.setDestination(despatchDetailsVO.getDestination());
			containerDetailsVO.setPou(despatchDetailsVO.getPou());
			containerDetailsVO.setPol(despatchDetailsVO.getAirportCode());
			containerDetailsVO.setCarrierCode(despatchDetailsVO.getCarrierCode());
			containerDetailsVO.setFlightDate(despatchDetailsVO.getFlightDate());
			break;
		}
		return containerDetailsVO;
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveMailArrivalDetailsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("AssignedFlightSegment" + " : " + "saveMailArrivalDetailsForTransfer" + " Entering");
		Map<ULDForSegmentPK, Collection<MailbagVO>> uldMailbagsMap = groupMailbagsOfULDSeg(mailbagVOs);
		for (ULDForSegmentPK uLDForSegmentPK : uldMailbagsMap.keySet()) {
			ULDForSegment uLDForSegment = null;
			Collection<MailbagVO> mailbagsInULD = uldMailbagsMap.get(uLDForSegmentPK);
			try {
				uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode(), finderException.getMessage(),
						finderException);
			}
			uLDForSegment.saveMailArrivalDetailsForTransfer(mailbagsInULD, containerVO);
		}
		log.debug("AssignedFlightSegment" + " : " + "saveMailArrivalDetailsForTransfer" + " Exiting");
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	private ULDForSegmentVO constructULDForSegmentVO(ContainerVO containerVO) {
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCarrierId(this.getCarrierId());
		uldForSegmentVO.setCompanyCode(this.getCompanyCode());
		uldForSegmentVO.setFlightNumber(this.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegmentVO.setUldNumber(containerVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			uldForSegmentVO.setUldNumber(constructBulkULDNumber());
		} else {
			uldForSegmentVO.setRemarks(containerVO.getRemarks());
			uldForSegmentVO.setPou(this.getPou());
			Collection<OnwardRoutingVO> onwardRoutingVOs = containerVO.getOnwardRoutings();
			if (onwardRoutingVOs != null && onwardRoutingVOs.size() > 0) {
				addNewRoutingDetails(onwardRoutingVOs, uldForSegmentVO);
			}
		}
		return uldForSegmentVO;
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveOutboundMailsFlightForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug("AssignedFlightSegment" + " : " + "saveOutboundMailsFlightForTransfer" + " Entering");
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		ULDForSegmentVO uLDForSegmentVO = null;
		containerVO.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uLDForSegmentPK.setCarrierId(this.getCarrierId());
		uLDForSegmentPK.setCompanyCode(this.getCompanyCode());
		uLDForSegmentPK.setFlightNumber(this.getFlightNumber());
		uLDForSegmentPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uLDForSegmentPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uLDForSegmentPK.setUldNumber(containerVO.getContainerNumber());
		if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			uLDForSegmentPK.setUldNumber(constructBulkULDNumber());
		}
		ULDForSegment uLDForSegment = null;
		try {
			uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
		} catch (FinderException finderException) {
			uLDForSegmentVO = constructULDForSegmentVO(containerVO);
			uLDForSegment = new ULDForSegment(uLDForSegmentVO);
			if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				boolean isUMSUpdateNeeded = ContextUtil.getInstance().getBean(MailController.class).isULDIntegrationEnabled();
				if (isUMSUpdateNeeded && ContainerVO.OPERATION_FLAG_INSERT.equals(containerVO.getOperationFlag())) {
					updateULDForOperations(containerVO);
				}
			}
		}
		uLDForSegment.saveOutboundMailDetailsForTransfer(mailbagVOs, containerVO);
		log.debug("AssignedFlightSegment" + " : " + "saveOutboundMailsFlightForTransfer" + " Exiting");
	}

	/** 
	* A-1739
	* @param segmentContainers
	* @param mailArrivalVO
	* @param exceptionMails this collection will be added with the mailbags of the ULDwhich was a new one and caused an assignment exception
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws DuplicateMailBagsException
	*/
	public boolean saveArrivalDetails(Collection<ContainerDetailsVO> segmentContainers, MailArrivalVO mailArrivalVO,
			Collection<MailbagVO> exceptionMails, Map<String, Collection<MailbagVO>> mailBagsMapForInventory,
			Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory)
			throws ContainerAssignmentException, DuplicateMailBagsException {
		log.debug("AssignedFlightSegment" + " : " + "saveArrivalDetails" + " Entering");
		boolean isUpdated = false;
		for (ContainerDetailsVO containerDetailsVO : segmentContainers) {
			if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
				try {
					isUpdated = true;
					insertArrivedContainerDetails(containerDetailsVO, mailArrivalVO, mailBagsMapForInventory,
							despatchesMapForInventory);
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagContainerAuditForArrival(mailArrivalVO);
				} catch (ContainerAssignmentException ex) {
					if (mailArrivalVO.isScanned()) {
						if (containerDetailsVO.getMailDetails() != null) {
							updateMailbagVOsWithErr(containerDetailsVO.getMailDetails(),
									MailConstantsVO.ERR_MSG_NEW_ULD_ASG);
							exceptionMails.addAll(containerDetailsVO.getMailDetails());
						}
					} else {
						throw ex;
					}
				}
			} else if (ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag())) {
				isUpdated = true;
				boolean isSaved = updateContainerArrivalDetails(containerDetailsVO, mailArrivalVO,
						mailBagsMapForInventory, despatchesMapForInventory, mailArrivalVO.isScanned());
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagContainerAuditForArrival(mailArrivalVO);
				if (mailArrivalVO.isScanned() && !isSaved) {
					containerDetailsVO.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					insertArrivedContainerDetails(containerDetailsVO, mailArrivalVO, mailBagsMapForInventory,
							despatchesMapForInventory);
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "saveArrivalDetails" + " Exiting");
		return isUpdated;
	}

	/** 
	* TODO Purpose Oct 12, 2006, a-1739
	* @param mailDetails
	*/
	private void updateMailbagVOsWithErr(Collection<MailbagVO> mailDetails, String errMsg) {
		log.debug("AssignedFlightSegment" + " : " + "updateMailbagVOsWithErr" + " Entering");
		if (mailDetails != null && mailDetails.size() > 0) {
			for (MailbagVO mailbagVO : mailDetails) {
				mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
				mailbagVO.setErrorDescription(errMsg);
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "updateMailbagVOsWithErr" + " Exiting");
	}

	/** 
	* @author a-A-5945
	* @return
	*/
	public void undoArriveContainer(Collection<ContainerDetailsVO> containerDetailsVOs) {
		log.debug("AssignedFlightSegment" + " : " + "undoArriveContainer" + " Entering");
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
			Collection<MailbagVO> mailbagsforUndoArrival = containerDetailsVO.getMailDetails();
			Collection<MailbagVO> mailbagsforDSNUpadation = new ArrayList<MailbagVO>();
			ULDForSegment uldForSegment = null;
			ULDForSegmentPK uldForSegmentPK = constructULDForSegmentPK(containerDetailsVO);
			try {
				uldForSegment = ULDForSegment.find(uldForSegmentPK);
			} catch (FinderException e) {
				log.debug("FinderException....");
			}
			if ((uldForSegment != null && uldForSegment.getNumberOfBags() == 0 && uldForSegment.getWeight() == 0)
					&& "CON".equals(containerDetailsVO.getUndoArrivalFlag())) {
				uldForSegment.remove();
			} else {
				if (uldForSegment != null && !(uldForSegment.getNumberOfBags() == 0 && uldForSegment.getWeight() == 0)
						&& (!MailConstantsVO.FLAG_NO.equals(uldForSegment.getReleasedFlag()))) {
					uldForSegment.setReleasedFlag(MailConstantsVO.FLAG_NO);
				}
				for (MailbagVO mailbag : mailbagsforUndoArrival) {
					if (MailConstantsVO.FLAG_YES.equals(mailbag.getUndoArrivalFlag())) {
						mailbagsforDSNUpadation.add(mailbag);
					}
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "undoArriveContainer" + " Exiting");
	}

	/** 
	* @author a-A-5945
	* @return ULDForSegmentPK
	*/
	private ULDForSegmentPK constructULDForSegmentPK(ContainerDetailsVO containerDetailsVO) {
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldForSegmentPK.setUldNumber(containerDetailsVO.getContainerNumber());
		uldForSegmentPK.setCarrierId(containerDetailsVO.getCarrierId());
		uldForSegmentPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		uldForSegmentPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		uldForSegmentPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		return uldForSegmentPK;
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws DuplicateMailBagsException
	*/
	private void insertArrivedContainerDetails(ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO,
			Map<String, Collection<MailbagVO>> mailBagsMapForInventory,
			Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory)
			throws ContainerAssignmentException, DuplicateMailBagsException {
		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		log.debug("AssignedFlightSegment" + " : " + "insertArrivedContainerDetails" + " Entering");
		if (!containerDetailsVO.getContainerNumber().startsWith(MailConstantsVO.CONST_BULK)) {
			checkContainerAssignmentAtPol(containerDetailsVO);
			createContainerAssignment(containerDetailsVO, mailArrivalVO);
		}
		ULDForSegmentVO uldForSegmentVO = constructULDForSegmentFromDetails(containerDetailsVO);
		uldForSegmentVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
		ULDForSegment uldForSegment = null;
		ULDForSegmentPK uldPK = new ULDForSegmentPK();
		uldPK.setCarrierId(uldForSegmentVO.getCarrierId());
		uldPK.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldPK.setFlightNumber(uldForSegmentVO.getFlightNumber());
		uldPK.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
		uldPK.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
		uldPK.setUldNumber(uldForSegmentVO.getUldNumber());
		try {
			uldForSegment = ULDForSegment.find(uldPK);
		} catch (FinderException exception) {
			uldForSegment = new ULDForSegment(uldForSegmentVO);
		}
		if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			boolean isUMSUpdateNeeded = mailController.isULDIntegrationEnabled();
			if (isUMSUpdateNeeded && ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
				FlightDetailsVO flightDetailsVO = null;
				Collection<ULDInFlightVO> uldInFlightVos = null;
				flightDetailsVO = new FlightDetailsVO();
				flightDetailsVO.setCompanyCode(containerDetailsVO.getCompanyCode());
				flightDetailsVO.setFlightCarrierIdentifier(containerDetailsVO.getCarrierId());
				//TODO: Neo to correct ZOne date
				//flightDetailsVO.setFlightDate(containerDetailsVO.getFlightDate());
				flightDetailsVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				flightDetailsVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				flightDetailsVO.setCarrierCode(containerDetailsVO.getCarrierCode());
				flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
				uldInFlightVos = new ArrayList<ULDInFlightVO>();
				ULDInFlightVO uldFltVo = new ULDInFlightVO();
				uldFltVo.setUldNumber(containerDetailsVO.getContainerNumber());
				uldFltVo.setPointOfLading(containerDetailsVO.getPol());
				uldFltVo.setPointOfUnLading(containerDetailsVO.getPou());
				uldInFlightVos.add(uldFltVo);
				flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
				flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
				flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
				flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
				try {
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				} catch (ULDDefaultsProxyException uldDefaultsException) {
					throw new SystemException(uldDefaultsException.getMessage());
				}
			}
		}
		uldForSegment.saveArrivalDetails(containerDetailsVO, mailArrivalVO, mailBagsMapForInventory,
				despatchesMapForInventory);
		uldForSegment.updateUldAcquittalStatus();
		if (MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())) {
			try {
				Container containerToUpdate = null;
				containerToUpdate = findContainer(constructContainerVOFromDetails(containerDetailsVO));
				containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
				containerToUpdate.setLastUpdatedUser(mailArrivalVO.getArrivedUser());
			} catch (SystemException ex) {
				/* NO NEED TO THROW THIS EXCEPTION TO CLIENT*/
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "insertArrivedContainerDetails" + " Exiting");
	}

	/**
	* A-1739 This method checks if there is already a container assigned at the other port If it accepted If it is in a closed flight
	* @param containerDetailsVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	*/
	private void checkContainerAssignmentAtPol(ContainerDetailsVO containerDetailsVO)
			throws ContainerAssignmentException {
		log.debug("AssignedFlightSegment" + " : " + "checkContainerAssignmentAtPol" + " Entering");
		ContainerAssignmentVO containerAsgVO = Container.findContainerAssignment(
				this.getCompanyCode(), containerDetailsVO.getContainerNumber(),
				containerDetailsVO.getPol());
		if (containerAsgVO != null) {
			if (MailConstantsVO.FLAG_YES.equals(containerAsgVO.getAcceptanceFlag())) {
				if (containerAsgVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					throw new ContainerAssignmentException(ContainerAssignmentException.DESTN_ASSIGNED,
							new Object[] { containerAsgVO.getContainerNumber(), containerDetailsVO.getPol() });
				}
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "checkContainerAssignmentAtPol" + " Exiting");
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @throws SystemException
	*/
	private void createContainerAssignment(ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO) {
		log.debug("AssignedFlightSegment" + " : " + "createContainerAssignment" + " Entering");
		ContainerVO containerVO = constructContainerVOFromDetails(containerDetailsVO);
		containerVO.setAssignedUser(mailArrivalVO.getArrivedUser());
		containerVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
		containerVO.setRemarks(MailConstantsVO.MAIL_ULD_ARRIVED);
		validateAndCreateContainer(containerVO);
		log.debug("AssignedFlightSegment" + " : " + "createContainerAssignment" + " Exiting");
	}

	/** 
	* @author a-1936 This methodo is used to update the Container or create andupdate the Container for OFFload
	* @throws SystemException
	*/
	private void validateAndCreateContainer(ContainerVO containerVO) {
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(containerVO.getCompanyCode());
		containerPk.setContainerNumber(containerVO.getContainerNumber());
		containerPk.setAssignmentPort(containerVO.getAssignedPort());
		containerPk.setFlightNumber(containerVO.getFlightNumber());
		containerPk.setCarrierId(containerVO.getCarrierId());
		containerPk.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVO.getLegSerialNumber());
		try {
			Container container = Container.find(containerPk);
			if (containerVO.getArrivedStatus() != null) {
				container.setArrivedStatus(containerVO.getArrivedStatus());
			}
			container.setRemarks(containerVO.getRemarks());
		} catch (FinderException ex) {
			createContainer(containerVO);
		}
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @return
	*/
	private ULDForSegmentVO constructULDForSegmentFromDetails(ContainerDetailsVO containerDetailsVO) {
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldForSegmentVO.setCarrierId(containerDetailsVO.getCarrierId());
		uldForSegmentVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		uldForSegmentVO.setUldNumber(containerDetailsVO.getContainerNumber());
		uldForSegmentVO.setRemarks(containerDetailsVO.getRemarks());
		return uldForSegmentVO;
	}

	private void createContainer(ContainerVO containerVo) {
		log.debug("AssignedFlightSegment" + " : " + "createContainer" + " Entering");
		Container container = new Container(containerVo);
		//TODO: Neo to correct audit
		/*
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,
				ContainerVO.ENTITY);
		containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(containerAuditVO, container, true);
		containerAuditVO.setActionCode(AuditVO.CREATE_ACTION);
		containerAuditVO.setAssignedPort(containerVo.getAssignedPort());
		containerAuditVO.setContainerNumber(containerVo.getContainerNumber());
		containerAuditVO.setFlightNumber(containerVo.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(containerVo.getLegSerialNumber());
		containerAuditVO.setCarrierId(containerVo.getCarrierId());
		containerAuditVO.setCompanyCode(containerVo.getCompanyCode());
		containerAuditVO.setAuditRemarks(containerVo.getRemarks());
		log.debug("" + "ContainerAuditVO>>>>>>>>>>> " + " " + containerAuditVO);
		AuditUtils.performAudit(containerAuditVO);

		 */
		log.debug("AssignedFlightSegment" + " : " + "createContainer" + " Exiting");
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @return
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	private boolean updateContainerArrivalDetails(ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO,
			Map<String, Collection<MailbagVO>> mailBagsMapForInventory,
			Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory, boolean isScanned)
			throws DuplicateMailBagsException {
		log.debug("AssignedFlightSegment" + " : " + "updateArrivedContainerDetails" + " Entering");
		if (!containerDetailsVO.getContainerNumber().startsWith(MailConstantsVO.CONST_BULK)) {
			try {
				updateContainerDetails(containerDetailsVO, mailArrivalVO);
			} catch (SystemException ex) {
				/* NO NEED TO THROW THIS EXCEPTION TO CLIENT*/
			}
		}
		ULDForSegmentPK uldForSegmentPK = constructULDForSegPKForContainer(containerDetailsVO);
		ULDForSegment uldForSegment = null;
		try {
			uldForSegment = findULDForSegment(uldForSegmentPK);
		} catch (FinderException e) {
		}
		if (uldForSegment == null) {
			if (mailArrivalVO.isScanned()) {
				return false;
			} else {
				throw new SystemException("NO ULD FOR SEGMENT ");
			}
		}
		if (uldForSegment.getRemarks() != null && !uldForSegment.getRemarks().equals(containerDetailsVO.getRemarks())) {
			uldForSegment.setRemarks(containerDetailsVO.getRemarks());
		}
		uldForSegment.saveArrivalDetails(containerDetailsVO, mailArrivalVO, mailBagsMapForInventory,
				despatchesMapForInventory);
		uldForSegment.updateUldAcquittalStatus();
		if (MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag())) {
			try {
				Container containerToUpdate = null;
				containerToUpdate = findContainer(constructContainerVOFromDetails(containerDetailsVO));
				containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
				containerToUpdate.setRemarks(containerDetailsVO.getRemarks());
				containerToUpdate.setLastUpdatedUser(mailArrivalVO.getArrivedUser());
				log.debug("" + "After updatingcontainer------------> " + " " + containerToUpdate);
			} catch (SystemException ex) {
				/* NO NEED TO THROW THIS EXCEPTION TO CLIENT*/
			}
		}
		log.debug("AssignedFlightSegment" + " : " + "updateArrivedContainerDetails" + " Exiting");
		return true;
	}

	/** 
	* @author a-1739
	* @param containerDetailsVO
	* @return
	*/
	private ULDForSegmentPK constructULDForSegPKForContainer(ContainerDetailsVO containerDetailsVO) {
		ULDForSegmentPK uldForSegPK = new ULDForSegmentPK();
		uldForSegPK.setCompanyCode(this.getCompanyCode());
		uldForSegPK.setCarrierId(this.getCarrierId());
		uldForSegPK.setFlightNumber(this.getFlightNumber());
		uldForSegPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		uldForSegPK.setSegmentSerialNumber(this.getSegmentSerialNumber());
		uldForSegPK.setUldNumber(containerDetailsVO.getContainerNumber());
		return uldForSegPK;
	}

	/** 
	* @author a-1739
	* @param containerDetailsVO
	* @param mailArrivalVO
	* @throws SystemException
	*/
	private void updateContainerDetails(ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("AssignedFlightSegment" + " : " + "updateContainerDetails" + " Entering");
		ContainerVO containerVO = constructContainerVOFromDetails(containerDetailsVO);
		log.debug("" + "containerVO " + " " + containerVO);
		containerVO.setAssignedUser(mailArrivalVO.getArrivedUser());
		containerVO.setLastUpdateUser(mailArrivalVO.getArrivedUser());
		Container container = null;
		try {
			container = Container.find(constructContainerPK(containerVO));
		} catch (FinderException exception) {
			try {
				int legSernum = containerVO.getLegSerialNumber();
				containerVO.setLegSerialNumber(0);
				container = Container.find(constructContainerPK(containerVO));
				containerVO.setLegSerialNumber(legSernum);
			} catch (FinderException exception1) {
				throw new SystemException(exception.getMessage(), exception);
			}
		}
		container.setRemarks(containerDetailsVO.getRemarks());
		if (containerVO.getPaBuiltFlag() != null && containerVO.getPaBuiltFlag().length() > 0) {
			container.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		} else {
			container.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
		}
		container.setArrivedStatus(MailConstantsVO.FLAG_YES);
		if (containerDetailsVO.getDeliveredStatus() != null) {
			container.setDeliveredFlag(containerDetailsVO.getDeliveredStatus());
		}
		if (containerDetailsVO.getIntact() != null) {
			container.setIntact(containerDetailsVO.getIntact());
		}
		log.debug("AssignedFlightSegment" + " : " + "updateContainerDetails" + " Exiting");
	}

	/** 
	* @author a-1883
	* @param mailbagVOs
	* @throws SystemException
	*/
	public void saveDamageDetailsForMailbags(Collection<MailbagVO> mailbagVOs) {
		log.debug("AssignedFlightSegment" + " : " + "saveDamageDetailsForMailbags" + " Entering");
		new ULDForSegment().updateDamageDetails(mailbagVOs);
		log.debug("AssignedFlightSegment" + " : " + "saveDamageDetailsForMailbags" + " Exiting");
	}

	/** 
	* @author a-7794
	* @param toContainerVO
	* @throws SystemException
	*/
	public static void updateULDForOperations(ContainerVO toContainerVO) {

		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		FlightDetailsVO flightDetailsVO = null;
		Collection<ULDInFlightVO> uldInFlightVos = null;
		flightDetailsVO = new FlightDetailsVO();
		flightDetailsVO.setCompanyCode(toContainerVO.getCompanyCode());
		flightDetailsVO.setFlightCarrierIdentifier(toContainerVO.getCarrierId());
		flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(toContainerVO.getFlightDate()));
		flightDetailsVO.setFlightNumber(toContainerVO.getFlightNumber());
		flightDetailsVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
		flightDetailsVO.setCarrierCode(toContainerVO.getCarrierCode());
		flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
		uldInFlightVos = new ArrayList<ULDInFlightVO>();
		ULDInFlightVO uldFltVo = new ULDInFlightVO();
		uldFltVo.setUldNumber(toContainerVO.getContainerNumber());
		uldFltVo.setPointOfLading(toContainerVO.getPol());
		uldFltVo.setPointOfUnLading(toContainerVO.getPou());
		uldFltVo.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
		uldInFlightVos.add(uldFltVo);
		flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
		flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
		flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
		try {
			uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
		} catch (ULDDefaultsProxyException uldDefaultsException) {
			throw new SystemException(uldDefaultsException.getMessage());
		}
	}

	/** 
	* @author a-7794
	* @param operationalFlightVo
	* @throws SystemException
	*/
	public static void updateULDForOperationsForContainerAcceptance(OperationalFlightVO operationalFlightVo,
			ULDInFlightVO uldFltVo) {
		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		FlightDetailsVO flightDetailsVO = null;
		Collection<ULDInFlightVO> uldInFlightVos = null;
		flightDetailsVO = new FlightDetailsVO();
		flightDetailsVO.setCompanyCode(operationalFlightVo.getCompanyCode());
		flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVo.getCarrierId());

		flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVo.getFlightDate()));
		flightDetailsVO.setFlightNumber(operationalFlightVo.getFlightNumber());
		flightDetailsVO.setFlightSequenceNumber(operationalFlightVo.getFlightSequenceNumber());
		flightDetailsVO.setCarrierCode(operationalFlightVo.getCarrierCode());
		flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
		uldInFlightVos = new ArrayList<ULDInFlightVO>();
		uldInFlightVos.add(uldFltVo);
		flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
		flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
		flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
		flightDetailsVO.setRemark(uldFltVo.getRemark());
		try {
			uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
		} catch (ULDDefaultsProxyException uldDefaultsException) {
			throw new SystemException(uldDefaultsException.getMessage());
		}
	}

	/** 
	* @author a-7794
	* @param mailAcceptanceVO
	*/
	private OperationalFlightVO constructOprFlightFromMailAcp(MailAcceptanceVO mailAcceptanceVO) {
		OperationalFlightVO opFlightVO = new OperationalFlightVO();
		opFlightVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		opFlightVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		opFlightVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		opFlightVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		opFlightVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
		opFlightVO.setPol(mailAcceptanceVO.getPol());
		opFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		opFlightVO.setFlightDate(mailAcceptanceVO.getFlightDate());
		opFlightVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		opFlightVO.setOwnAirlineCode(mailAcceptanceVO.getOwnAirlineCode());
		opFlightVO.setOwnAirlineId(mailAcceptanceVO.getOwnAirlineId());
		opFlightVO.setOperator(mailAcceptanceVO.getAcceptedUser());
		log.debug("" + "THE accepted User" + " " + mailAcceptanceVO.getAcceptedUser());
		return opFlightVO;
	}
	/**
	 * @author a-1936This methos is used to list  the containers and the associated dsns  present in that..
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 */
	public static MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo) {
		return ULDForSegment.findContainersInFlightForManifest(operationalFlightVo);
	}

	/**
	 * A-1739
	 * @return
	 * @throws SystemException
	 */
	public static MailArrivalVO findArrivalDetails(MailArrivalFilterVO mailArrivalFilterVO) {
		Collection<ContainerDetailsVO> arrivedContainers = ULDForSegment.findArrivedContainers(mailArrivalFilterVO);
		MailArrivalVO arrivalVO = new MailArrivalVO();
		arrivalVO.setCompanyCode(mailArrivalFilterVO.getCompanyCode());
		arrivalVO.setFlightCarrierCode(mailArrivalFilterVO.getCarrierCode());
		arrivalVO.setCarrierId(mailArrivalFilterVO.getCarrierId());
		arrivalVO.setFlightNumber(mailArrivalFilterVO.getFlightNumber());
		arrivalVO.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
		arrivalVO.setArrivalDate(mailArrivalFilterVO.getFlightDate());
		arrivalVO.setAirportCode(mailArrivalFilterVO.getPou());
		arrivalVO.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());
		if (CollectionUtils.isNotEmpty(arrivedContainers)) {
			arrivalVO.setFlightStatus(arrivedContainers.iterator().next().getFlightStatus());
		}
		arrivalVO.setContainerDetails(arrivedContainers);
		return arrivalVO;
	}

	/**
	 * @author A-5166
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode) {
		try {
			return constructDAO().findImportFlghtsForArrival(companyCode);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getErrorCode(), exception.getMessage(), exception);
		}
	}

	/**
	 * @author a-1936This method is used to fetch the MailBags and the Despatches  in the  Container for a Flight.
	 * @param containers
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsVO> containers) {
		return ULDForSegment.findMailbagsInContainerForImportManifest(containers);
	}

	public static MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException {
		return  ULDForSegment.findImportManifestDetails(operationalFlightVo);
	}
}
