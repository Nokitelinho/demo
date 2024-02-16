package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** 
 * @author a-3109 This class represents a Container (ULD/Barrow) assigned to aflight or carrier.
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(ContainerPK.class)
@Table(name = "MALFLTCON")
@Auditable(eventName="containerUpdate",isParent = true)
public class Container extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	private static final String ENTITY = "Container";
	private static final String CONTAINER_TYPE_BULK = "B";
	private static final int SEQUENCE_NUMBERS = -1;
	private static final String FLIGHT_NUMBER = "-1";
	private static final String CONTAINER_ID = "CONTAINER_ID";

	@Id
	@Transient
	private String companyCode;
    @Audit(name="containerNumber",changeGroupId="generic")
	@Id
	@Column(name = "CONNUM")
	private String containerNumber;
	@Id
	@Column(name = "ASGPRT")
	private String assignmentPort;
    @Audit(name="carrierId",changeGroupId="generic")
    @Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
    @Audit(name="flightNumber",changeGroupId="generic")
	@Id
	@Column(name = "FLTNUM")
	private String flightNumber;
    @Audit(name="flightSequenceNumber",changeGroupId="generic")
	@Id
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Id
	@Column(name = "LEGSERNUM")
	private int legSerialNumber;

	private static final String MAL_FLTCON_ULDREFNUM = "MAL_FLTCON_ULDREFNUM";
	/** 
	* containerType
	*/
    @Audit(name="containerType",changeGroupId="generic")
	@Column(name = "CONTYP")
	private String containerType;
	/** 
	* final Destination
	*/
	@Column(name = "DSTCOD")
	private String finalDestination;
	/** 
	* remarks
	*/
	@Column(name = "RMK")
	private String remarks;
	/** 
	* paBuiltFlag
	*/
	@Column(name = "POAFLG")
	private String paBuiltFlag;
	/** 
	* onward Routes
	*/
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CONNUM", referencedColumnName = "CONNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ASGPRT", referencedColumnName = "ASGPRT", insertable = false, updatable = false),
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "LEGSERNUM", referencedColumnName = "LEGSERNUM", insertable = false, updatable = false) })
	private Set<OnwardRouting> onwardRoutes;
	/** 
	* pou
	*/
	@Column(name = "POU")
	private String pou;
	/** 
	* segment SerialNumber
	*/
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	/** 
	* assignedOn
	*/
	@Column(name = "ASGDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime assignedOn;
	/** 
	* assignedDateUTC
	*/
	@Column(name = "ASGDATUTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime assignedDateUTC;
	/** 
	* assignedBy
	*/
	@Column(name = "USRCOD")
	private String assignedBy;
	/** 
	* acceptanceFlag
	*/
    @Audit(name="acceptanceFlag",changeGroupId="generic")
	@Column(name = "ACPFLG")
	private String acceptanceFlag;

	/** 
	* carrierCode
	*/
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	/** 
	* offload Flag
	*/
    @Audit(name="offloadFlag",changeGroupId="generic")
	@Column(name = "OFLFLG")
	private String offloadFlag;
	/** 
	* Arrival flag
	*/
	@Column(name = "ARRSTA")
	private String arrivedStatus;
	/** 
	* transfer Flag
	*/
	@Column(name = "TRAFLG")
	private String transferFlag;
	/** 
	* paBuiltOpened Flag
	*/
	@Column(name = "POAOPN")
	private String paBuiltOpenedFlag;
	/** 
	* delivered Flag
	*/
	@Column(name = "DLVFLG")
	private String deliveredFlag;
	/** 
	* actualWeight
	*/
    @Audit(name="actualWeight",changeGroupId="generic")
	@Column(name = "ACTULDWGT")
	private double actualWeight;
	/** 
	* intact
	*/
	@Column(name = "INTFLG")
	private String intact;
	/** 
	* transaction Code
	*/
	@Column(name = "TXNCOD")
	private String transactionCode;
	/** 
	* containerJnyIdr
	*/
	@Column(name = "CONJRNIDR")
	private String containerJnyIdr;
	/** 
	* shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB ULD.
	*/
	@Column(name = "POACOD")
	private String shipperBuiltCode;
	/** 
	* Transit Flag
	*/
	@Column(name = "TRNFLG")
	private String transitFlag;
	@Column(name = "CNTIDR")
	private String contentId;
	@Column(name = "ULDHGT")
	private double uldHeight;
	@Column(name = "ACTULDWGTDSPUNT")
	private String actualWeightDisplayUnit;
	@Column(name = "ACTULDWGTDSP")
	private double actualWeightDisplayValue;
	@Column(name = "RETFLG")
	private String retainFlag;
	@Column(name = "FSTMALASGDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime firstMalbagAsgDat;
	@Column(name = "ULDFULIND")
	private String uldFulIndFlag;
	@Column(name = "ULDREFNUM")
	private long uldReferenceNo;

	/**
	* The defaultConstructor
	*/
	public Container() {
	}

	/** 
	* @author a-3109 The constructor called when the instance is to bepersisted
	* @param containerVO
	* @throws SystemException
	*/
	public Container(ContainerVO containerVO) {
		populatePK(containerVO);
		populateAttributes(containerVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		populateChildren(containerVO);
	}

	/** 
	* This method is used to populate the Routing Details
	* @author a-3109
	* @param containerVO
	* @throws SystemException
	*/
	public void populateChildren(ContainerVO containerVO) {
		log.info("INSIDE THE POPULATE CHILDREN");
		Collection<OnwardRoutingVO> onwardRoutinVos = null;
		if (containerVO.getOnwardRoutings() != null && containerVO.getOnwardRoutings().size() > 0) {
			log.info("populateRoutingDetails called");
			onwardRoutinVos = containerVO.getOnwardRoutings();
			populateRoutingDetails(onwardRoutinVos);
		}
	}

	/** 
	* @author a-3109 This method is used to populate the RoutingDetails
	* @param onwardRoutingVos
	* @throws SystemException
	*/
	private void populateRoutingDetails(Collection<OnwardRoutingVO> onwardRoutingVos) {
		for (OnwardRoutingVO onwardRoutingVo : onwardRoutingVos) {
			if (OnwardRoutingVO.OPERATION_FLAG_INSERT.equals(onwardRoutingVo.getOperationFlag())) {
				log.info("CREATE ROUTING DETAILS CALLED");
				createRoutingDetails(onwardRoutingVo);
			}
			if (OnwardRoutingVO.OPERATION_FLAG_UPDATE.equals(onwardRoutingVo.getOperationFlag())) {
				log.info("MODIFY  ROUTING DETAILS CALLED");
			}
			if (OnwardRoutingVO.OPERATION_FLAG_DELETE.equals(onwardRoutingVo.getOperationFlag())) {
				log.info("MODIFY  ROUTING  DETAILS CALLED");
			}
		}
	}

	/** 
	* @author a-3109 1.This method is used to append the Prefix say 00 toCriterion generated Key if the key generated is from 0 to 9 2.else if the key is from 10 to 99 append the prefix 0 . so it will formulate a string of always size 3. can be reused to get theprefix of any length.
	* @param key
	*/
	private String formulatePrefix(String key) {
		int size = 3;
		int difference = size - key.length();
		StringBuilder prefixBuilder = new StringBuilder();
		for (int i = 0; i < difference; i++) {
			prefixBuilder.append(0);
		}
		return prefixBuilder.append(key).toString();
	}

	/** 
	* @author a-3109 This method is used to create the RoutingDetail
	* @param onwardRoutingVo
	* @throws SystemException
	*/
	private void createRoutingDetails(OnwardRoutingVO onwardRoutingVo) {
		onwardRoutingVo.setContainerNumber(this.getContainerNumber());
		onwardRoutingVo.setCompanyCode(this.getCompanyCode());
		onwardRoutingVo.setAssignmenrPort(this.getAssignmentPort());
		onwardRoutingVo.setCarrierId(this.getCarrierId());
		onwardRoutingVo.setFlightNumber(this.getFlightNumber());
		onwardRoutingVo.setFlightSequenceNumber(this.getFlightSequenceNumber());
		onwardRoutingVo.setLegSerialNumber(this.getLegSerialNumber());
		OnwardRouting onwardRouting = new OnwardRouting(onwardRoutingVo);
		if (getOnwardRoutes() == null) {
			setOnwardRoutes(new HashSet<OnwardRouting>());
		}
		getOnwardRoutes().add(onwardRouting);
		log.debug("" + "THE SIZE OF THE CONTAINERDETAILS IN THE SET IS" + " " + getOnwardRoutes().size());
	}

	/** 
	* @author a-1936 This method is used to remove all the children instanceswhen the Parent is requested for delete ..
	* @throws SystemException
	*/
	private void removeChildren() {
		log.info("INSIDE THE REMOVE METHOD FOR THE CHILDREN");
		if (getOnwardRoutes() != null && getOnwardRoutes().size() > 0) {
			log.info("" + "THE SIZE OF THE CONTAINERDETAILS" + " " + getOnwardRoutes().size());
			for (OnwardRouting onwardRouting : getOnwardRoutes()) {
				onwardRouting.remove();
			}
			log.info("CHILD REMOVED");
		}
	}

	/** 
	* This method is used to populate the pk for the container
	* @param containerVO
	* @throws SystemException
	*/
	private void populatePK(ContainerVO containerVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		this.setCompanyCode(containerVO.getCompanyCode());
		if ((containerVO.getContainerNumber() == null || containerVO.getContainerNumber().trim().length() == 0)
				&& !containerVO.isReassignFlag()) {
			String assignedPort = containerVO.getAssignedPort();
			ZonedDateTime localDate = localDateUtil.getLocalDate(assignedPort, false);
			String date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
			StringBuilder keyBuilder = new StringBuilder(CONTAINER_TYPE_BULK);
			keyBuilder.append(assignedPort);
			StringTokenizer tokenizer = new StringTokenizer(date, "-");
			while (tokenizer.hasMoreTokens()) {
				keyBuilder.append(tokenizer.nextToken());
			}
			String keyPrefix = keyBuilder.toString();
			KeyCondition keyCondition =  new KeyCondition();
			keyCondition.setKey("barrowId");
			keyCondition.setValue(keyPrefix);
			Criterion criterion = new CriterionBuilder()
					.withKeyCondition(keyCondition)
					.withSequence(CONTAINER_ID)
					.withPrefix("").withNumberFormat("%04d").build();
			KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
			String key = keyUtils.getKey(criterion);
			if (key.length() < 3) {
				key = formulatePrefix(key);
			}
		setContainerNumber(new StringBuilder(keyPrefix).append(key).toString());
		} else {
			this.setContainerNumber(containerVO.getContainerNumber());
		}
		this.setAssignmentPort(containerVO.getAssignedPort());
		if (containerVO.getFlightNumber() == null || containerVO.getFlightNumber().trim().length() == 0) {
			this.setFlightNumber(FLIGHT_NUMBER);
			this.setFlightSequenceNumber(SEQUENCE_NUMBERS);
			this.setLegSerialNumber(SEQUENCE_NUMBERS);
		} else {
			this.setFlightNumber(containerVO.getFlightNumber());
			this.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			this.setLegSerialNumber(containerVO.getLegSerialNumber());
		}
		this.setCarrierId(containerVO.getCarrierId());
	}

	/** 
	* @author a-1936 This method is used
	* @param containerVO
	*/
	private void populateAttributes(ContainerVO containerVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Inside the Contailner" + " : " + "populateAttributes" + " Entering");
		log.debug("" + "The Container Vo in populateAttributes" + " " + containerVO);
		this.setAssignedBy(containerVO.getAssignedUser());
		if (containerVO.getAssignedDate() != null) {
			this.setAssignedOn(
					localDateUtil.getLocalDateTime(containerVO.getAssignedDate(), containerVO.getAssignedPort()).toLocalDateTime());
			this.setAssignedDateUTC(localDateUtil.toUTCTime(containerVO.getAssignedDate()).toLocalDateTime());
		}
		this.setContainerType(containerVO.getType());
		this.setFinalDestination(containerVO.getFinalDestination());
		this.setLastUpdatedUser(containerVO.getLastUpdateUser());
		this.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		this.setDeliveredFlag(MailConstantsVO.FLAG_NO);
		if (containerVO.getTransferFlag() != null) {
			this.setTransferFlag(containerVO.getTransferFlag());
		} else {
			this.setTransferFlag(MailConstantsVO.FLAG_NO);
		}
		if (containerVO.getPaBuiltFlag() != null && containerVO.getPaBuiltFlag().length() > 0) {
			this.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		} else {
			this.setPaBuiltFlag(ContainerVO.FLAG_NO);
		}
		this.setPou(containerVO.getPou());
		this.setRemarks(containerVO.getRemarks());
		this.setCarrierCode(containerVO.getCarrierCode());
		if (containerVO.getAcceptanceFlag() != null) {
			this.setAcceptanceFlag(containerVO.getAcceptanceFlag());
		} else {
			this.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
		}
		if (containerVO.getOffloadFlag() != null) {
			setOffloadFlag(containerVO.getOffloadFlag());
		} else {
			setOffloadFlag(MailConstantsVO.FLAG_NO);
		}
		if (containerVO.getArrivedStatus() != null) {
			setArrivedStatus(containerVO.getArrivedStatus());
		} else {
			setArrivedStatus(MailConstantsVO.FLAG_NO);
		}
		setTransferFlag(MailConstantsVO.FLAG_NO);
		if (containerVO.getPaBuiltOpenedFlag() != null) {
			setPaBuiltOpenedFlag(containerVO.getPaBuiltOpenedFlag());
		} else {
			setPaBuiltOpenedFlag(MailConstantsVO.FLAG_NO);
		}
		if(Objects.nonNull(containerVO.getLastUpdateTime())){
		setLastUpdatedTime(Timestamp.valueOf(containerVO.getLastUpdateTime().toLocalDateTime()));
		}
		setLastUpdatedUser(containerVO.getLastUpdateUser());
		setTransactionCode(containerVO.getTransactionCode());
		setContainerJnyIdr(containerVO.getContainerJnyID());
		setShipperBuiltCode(containerVO.getShipperBuiltCode());
		if (containerVO.getIntact() == null) {
			setIntact(MailConstantsVO.FLAG_NO);
		} else {
			setIntact(containerVO.getIntact());
		}
		this.setDeliveredFlag(MailConstantsVO.FLAG_NO);
		if (containerVO.getActualWeight() != null) {
			this.setActualWeight(containerVO.getActualWeight().getValue().doubleValue());
			this.setActualWeightDisplayValue(containerVO.getActualWeight().getDisplayValue().doubleValue());
			this.setActualWeightDisplayUnit(containerVO.getActualWeight().getDisplayUnit().getName());
		}
		if (containerVO.getTransitFlag() != null) {
			setTransitFlag(containerVO.getTransitFlag());
		} else {
			setTransitFlag(MailConstantsVO.FLAG_NO);
		}
		if (containerVO.getUldFulIndFlag() != null) {
			setUldFulIndFlag(containerVO.getUldFulIndFlag());
		} else {
			setUldFulIndFlag(MailConstantsVO.FLAG_NO);
		}
		setContentId(containerVO.getContentId());
		setRetainFlag(MailConstantsVO.FLAG_NO);
		if (getFirstMalbagAsgDat() == null && containerVO.isMailbagPresent()) {
			if (containerVO.getAssignedDate() != null && containerVO.getFirstAssignDate() == null) {
				setFirstMalbagAsgDat(
						localDateUtil.getLocalDateTime(containerVO.getAssignedDate(), containerVO.getAssignedPort()).toLocalDateTime());
			} else if (containerVO.getFirstAssignDate() != null) {
				setFirstMalbagAsgDat(localDateUtil.getLocalDateTime(containerVO.getFirstAssignDate(),
						containerVO.getAssignedPort()).toLocalDateTime());
			} else {
				setFirstMalbagAsgDat(localDateUtil.getLocalDate(containerVO.getAssignedPort(), true).toLocalDateTime());
			}
		}
		if (containerVO.getUldReferenceNo() > 0) {
			this.setUldReferenceNo(containerVO.getUldReferenceNo());
		} else {
			this.setUldReferenceNo(generateULDReferenceNumber(containerVO));
		}
	}

	/** 
	* @author a-1936 This method is used to update the BusinessObject
	* @param containerVO
	* @throws SystemException
	*/
	public void update(ContainerVO containerVO) {
		log.debug("Inside the update method" + " : " + "For Container" + " Entering");
		populateAttributes(containerVO);
		populateChildren(containerVO);
	}

	/** 
	* @author a-1936 methods the DAO instance ..
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
	* @throws SystemException
	*/
	public void remove() {
		log.debug("CONTAINER" + " : " + "REMOVE METHOD CALLED" + " Entering");
		try {
			removeChildren();
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException.getMessage(), removeException);
		}
		log.debug("CONTAINER" + " : " + "REMOVE" + " Exiting");
	}

	/** 
	* @author a-1936
	* @param containerPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static Container find(ContainerPK containerPK) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(Container.class, containerPK);
	}

	/** 
	* @author A-3227 This method fetches the latest Container Assignmentirrespective of the PORT to which it is assigned. This to know the current assignment of the Container.
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public static ContainerAssignmentVO findLatestContainerAssignment(String companyCode, String containerNumber) {
		try {
			return constructDAO().findLatestContainerAssignment(companyCode, containerNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author a-1936 This method is used to retrieve thecurrentContainerDetails.If the ContainerAssigmentVo is null Assignments can be made
	* @param companyCode
	* @param containerNumber
	* @param pol
	* @return
	* @throws SystemException
	*/
	public static ContainerAssignmentVO findContainerAssignment(String companyCode, String containerNumber,
			String pol) {
		try {
			return constructDAO().findContainerAssignment(companyCode, containerNumber, pol);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public static ContainerAssignmentVO findArrivalDetailsForULD(ContainerVO containerVO) {
		try {
			return constructDAO().findArrivalDetailsForULD(containerVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
	}

	/** 
	* A-1739
	* @return
	*/
	public ContainerVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(this.getCompanyCode());
		containerVO.setContainerNumber(this.getContainerNumber());
		containerVO.setCarrierId(this.getCarrierId());
		containerVO.setFlightNumber(this.getFlightNumber());
		containerVO.setFlightSequenceNumber(this.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(this.getLegSerialNumber());
		containerVO.setAssignedPort(this.getAssignmentPort());
		containerVO.setAcceptanceFlag(getAcceptanceFlag());
		containerVO.setAssignedUser(getAssignedBy());
		containerVO.setAssignedDate(localDateUtil.getLocalDateTime(getAssignedOn(), this.getAssignmentPort()));
		containerVO.setType(getContainerType());
		containerVO.setFinalDestination(getFinalDestination());
		containerVO.setPaBuiltFlag(getPaBuiltFlag());
		containerVO.setPou(getPou());
		containerVO.setRemarks(getRemarks());
		containerVO.setSegmentSerialNumber(getSegmentSerialNumber());
		containerVO.setOffloadFlag(getOffloadFlag());
		containerVO.setArrivedStatus(getArrivedStatus());
		containerVO.setPaBuiltOpenedFlag(getPaBuiltOpenedFlag());
		containerVO.setTransactionCode(getTransactionCode());
		containerVO.setContainerJnyID(getContainerJnyIdr());
		containerVO.setShipperBuiltCode(getShipperBuiltCode());
		containerVO.setContentId(getContentId());
		containerVO.setUldFulIndFlag(getUldFulIndFlag());
		if (getUldReferenceNo() != 0) {
			containerVO.setUldReferenceNo(getUldReferenceNo());
		}
		onwardRoutes = getOnwardRoutes();
		if (onwardRoutes != null && onwardRoutes.size() > 0) {
			Collection<OnwardRoutingVO> onwardRouteVOs = new ArrayList<OnwardRoutingVO>();
			for (OnwardRouting onwardRouting : onwardRoutes) {
				OnwardRoutingVO onwardRoutingVO = onwardRouting.retrieveVO();
				onwardRouteVOs.add(onwardRoutingVO);
			}
			containerVO.setOnwardRoutings(onwardRouteVOs);
		}
		containerVO.setActualWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getActualWeight())));
		if (getFirstMalbagAsgDat() != null) {
			containerVO.setFirstAssignDate(
					localDateUtil.getLocalDateTime(getFirstMalbagAsgDat(), this.getAssignmentPort()));
			containerVO.setMailbagPresent(true);
		}
		return containerVO;
	}

	public static ContainerAssignmentVO findContainerAssignmentForArrival(String companyCode, String containerNumber,
			String pol) {
		try {
			return constructDAO().findContainerAssignment(companyCode, containerNumber, pol);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	private static ContainerPK constructContainerPK(ContainerVO containerDetails) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(containerDetails.getCompanyCode());
		containerPK.setAssignmentPort(containerDetails.getAssignedPort());
		containerPK.setCarrierId(containerDetails.getCarrierId());
		containerPK.setContainerNumber(containerDetails.getContainerNumber());
		containerPK.setFlightNumber(containerDetails.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerDetails.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerDetails.getLegSerialNumber());
		return containerPK;
	}

	/** 
	* @author a-7929 
	* @param containerVOs
	* @param 
	* @return
	* @throws SystemException
	*/
	public static void saveContentID(Collection<ContainerVO> containerVOs) {
		Container container = null;
		if (containerVOs != null && !containerVOs.isEmpty()) {
			for (ContainerVO containerVO : containerVOs) {
				try {
					container = Container.find(constructContainerPK(containerVO));
					container.setContentId(containerVO.getContentId());
				} catch (FinderException e) {
					throw new SystemException(e.getErrorCode(), e.getMessage(), e);
				}
			}
		}
	}

	/** 
	* @author A-7929
	* @param dwsMasterVO
	* @param assignedAirport 
	* @param containerNumber 
	* @return 
	* @throws SystemException 
	* @throws FinderException 
	*/
	public static String fetchMailContentIDs(DWSMasterVO dwsMasterVO, String containerNumber, String assignedAirport) {
		Container container = null;
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(dwsMasterVO.getCompanyCode());
		containerVO.setCarrierId(dwsMasterVO.getCarrierId());
		containerVO.setAssignedPort(assignedAirport);
		containerVO.setContainerNumber(containerNumber);
		containerVO.setFlightNumber(dwsMasterVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(dwsMasterVO.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(dwsMasterVO.getLegSerialNumber());
		try {
			container = Container.find(constructContainerPK(containerVO));
		} catch (FinderException e) {
			log.info("" + "System exception" + " " + e);
			return null;
		}
		return container.getContentId();
	}

	/** 
	* @author U-1532findLatestContainerAssignmentForUldDelivery
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException 
	*/
	public static ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(
			ScannedMailDetailsVO scannedMailDetailsVO) {
		try {
			return constructDAO().findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	public long generateULDReferenceNumber(ContainerVO containerVO) {
		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey(MAL_FLTCON_ULDREFNUM);
		keyCondition.setValue(MAL_FLTCON_ULDREFNUM);
		Criterion criterion = new CriterionBuilder()
				.withSequence(MAL_FLTCON_ULDREFNUM)
				.withKeyCondition(keyCondition)
				.withPrefix("").build();
		KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
		return Long.parseLong((keyUtils.getKey(criterion)));

	}
	/**
	 * Method : Container.findAlreadyAssignedTrolleyNumberForMLD Added by : A-4803 on 28-Oct-2014 Used for : To find whether a container is already presnet for the mail bag Parameters : @param mldMasterVO Parameters : @return Parameters : @throws SystemException Return type : String
	 */
	public static String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) {
		try {
			return constructDAO().findAlreadyAssignedTrolleyNumberForMLD(mldMasterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
	}

	/**
	 * @author A-1739
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 */
	public static int findFlightLegSerialNumber(ContainerVO containerVO) {
		try {
			return constructDAO().findFlightLegSerialNumber(containerVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author a-1936 This method returns all the ULDs assigned to a particularflight from the given airport are returned
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findFlightAssignedContainers(OperationalFlightVO operationalFlightVO) {
		log.debug(ENTITY + " : " + "findFlightAssignedContainers" + " Entering");
		try {
			return constructDAO().findFlightAssignedContainers(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author a-1936 This method returns all the ULDs that are assigned todestination from the given airport are returned
	 * @param destinationFilterVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findDestinationAssignedContainers(DestinationFilterVO destinationFilterVO) {
		try {
			return constructDAO().findDestinationAssignedContainers(destinationFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author a-1936 This method is used to find the conrainerDetails
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, int pageNumber) {
		if(StringUtils.equals("REASSIGN_CONTAINER", searchContainerFilterVO.getSource())){
			return constructDAO().findContainerDetailsForReassignment(searchContainerFilterVO, pageNumber);
		}
		try {
			return constructDAO().findContainers(searchContainerFilterVO, pageNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}

	}


	/**
	 * @author A-8672
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static void updateRetainFlag(ContainerVO containerVo) throws FinderException {
		Container container = null;
		container = Container.find(constructContainerPK(containerVo));
		if (container != null) {
			container.setRetainFlag(containerVo.getRetainFlag());
			if (container.getTransitFlag().equals(MailConstantsVO.FLAG_NO)) {
				container.setTransitFlag(MailConstantsVO.FLAG_YES);
			}
		}
	}

	public static void markUnmarkUldIndicator(ContainerVO containerVo) throws FinderException {
		Container container = null;
		container = Container.find(constructContainerPK(containerVo));
		if (container != null) {
			container.setUldFulIndFlag(containerVo.getUldFulIndFlag());
		}
	}
	@Override
	public BaseEntity getParentEntity() {
		return this;
	}
	@Override
	public String getBusinessIdAsString(){
		return this.containerNumber;
	}
}
