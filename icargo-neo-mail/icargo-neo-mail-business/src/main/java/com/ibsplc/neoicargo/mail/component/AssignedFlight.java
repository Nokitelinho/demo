package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//import java.time.LocalDateTime;
//import java.time.ZonedDateTime;

/** 
 * @author A-3109 This class represents the flight to which mail is accepted.This is at a higher level.Segment details are not stored here.
 */
@Getter
@Setter
@Slf4j
@Entity
@IdClass(AssignedFlightPK.class)
@Table(name = "MALFLT")
@Auditable(eventName="mailFlightUpdate",isParent = true)
public class AssignedFlight extends BaseEntity implements Serializable {
	private static final String PRODUCT_NAME = "mail.operations";
	/**
	 * The carrierId
	 */
	@Id
	@Transient
	private String companyCode;
	@Audit(name="carrierId",changeGroupId="mail-flight-update")
	@Id
	@Column(name = "FLTCARIDR")
	private int carrierId;
	/**
	 * The flightNumber
	 */
	@Audit(name="flightNumber",changeGroupId="mail-flight-update")
	@Id
	@Column(name = "FLTNUM")
	private String flightNumber;
	/**
	 * The legSerialNumber
	 */
	@Audit(name="legSerialNumber",changeGroupId="mail-flight-update")
	@Id
	@Column(name = "LEGSERNUM")
	private int legSerialNumber;
	/**
	 * The flightSequenceNumber
	 */
	@Audit(name="flightSequenceNumber",changeGroupId="mail-flight-update")
	@Id
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Audit(name="airportCode",changeGroupId="mail-flight-update")
	@Id
	@Column(name = "ARPCOD")
	private String airportCode;
	/** 
	* Carrier Code
	*/
	@Audit(name="carrierCode",changeGroupId="mail-flight-update")
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	/** 
	* Flight Date
	*/
	@Audit(name="flightDate",changeGroupId="mail-flight-update")
	@Column(name = "FLTDAT")
	//@Temporal(TemporalType.DATE)
	private LocalDateTime flightDate;
	/** 
	* Operationally closed/finalised etc
	*/
	//private String flightStatus;
	@Audit(name="exportClosingFlag",changeGroupId="mail-flight-update")
	@Column(name = "EXPCLSFLG")
	private String exportClosingFlag;
	@Audit(name="importClosingFlag",changeGroupId="mail-flight-update")
	@Column(name = "IMPCLSFLG")
	private String importClosingFlag;
	@Audit(name="flownAccountingStatus",changeGroupId="mail-flight-update")
	@Column(name = "FLNACCSTA")
	private String flownAccountingStatus;
	@Audit(name="gateClearanceStatus",changeGroupId="mail-flight-update")
	@Column(name = "GTECLRSTA")
	@DefaultValue("N")
	private String gateClearanceStatus;
	/**
	* The default constructor
	*/
	public AssignedFlight() {
	}

	/**
	* @author a-3109 This method is used to insert the new record in the Entity
	* @param assignedFlightVO
	* @throws SystemException
	*/
	public AssignedFlight(AssignedFlightVO assignedFlightVO) {
		populatePK(assignedFlightVO);
		populateAttributes(assignedFlightVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author a-3109 This method is used to populate the PK
	* @param assignedFlightVO
	* @throws SystemException
	*/
	private void populatePK(AssignedFlightVO assignedFlightVO) {
		this.setAirportCode(assignedFlightVO.getAirportCode());
		this.setCompanyCode(assignedFlightVO.getCompanyCode());
		this.setCarrierId(assignedFlightVO.getCarrierId());
		this.setFlightNumber(assignedFlightVO.getFlightNumber());
		this.setLegSerialNumber(assignedFlightVO.getLegSerialNumber());
		this.setFlightSequenceNumber(assignedFlightVO.getFlightSequenceNumber());
	}

	/** 
	* @author a-3109 This method is used to populate the Attributes
	* @param assignedFlightVO
	* @throws SystemException
	*/
	private void populateAttributes(AssignedFlightVO assignedFlightVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		this.setCarrierCode(assignedFlightVO.getCarrierCode());
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();

		if (assignedFlightVO.getFlightDate() != null) {
			this.setFlightDate(assignedFlightVO.getFlightDate().toLocalDateTime());
		}
		if (this.getCarrierCode() == null) {
			this.setCarrierCode(logonAttributes.getOwnAirlineCode());
		}
		if (assignedFlightVO.getFlightStatus() != null) {
			this.setExportClosingFlag(assignedFlightVO.getFlightStatus());
		} else {
			this.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_OPEN);
		}
		this.setImportClosingFlag(assignedFlightVO.getImportFlightStatus());
		this.setFlownAccountingStatus("N");
		if (assignedFlightVO.getLastUpdateUser() != null) {
			this.setLastUpdatedUser(assignedFlightVO.getLastUpdateUser());
		} else {
			this.setLastUpdatedUser(logonAttributes.getUserId());
		}
		setGateClearanceStatus(MailConstantsVO.FLAG_NO);
	}

	/** 
	* @author a-3109 This method is used to find the instance of this Entity
	* @param assignedFlightPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static AssignedFlight find(AssignedFlightPK assignedFlightPK) throws FinderException {
		log.debug("airport code" + assignedFlightPK.getAirportCode());
		log.debug("companyCode" + assignedFlightPK.getCompanyCode());
		log.debug("flightNumber" + assignedFlightPK.getFlightNumber());
		log.debug("flightSequenceNumber" + assignedFlightPK.getFlightSequenceNumber());
		log.debug("legSerialNumber" + assignedFlightPK.getLegSerialNumber());
		log.debug("carrierId" + assignedFlightPK.getCarrierId());
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(AssignedFlight.class, assignedFlightPK);
	}

	/** 
	* @author a-1883
	* @param containerVOs
	* @param toFlightVO TODO
	* @return Collection<ULDForSegmentVO>
	* @throws SystemException
	*/
	public Collection<ULDForSegmentVO> saveArrivalDetailsForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO toFlightVO, Map<MailbagPK, MailbagVO> mailMap) {
		Collection<ULDForSegmentVO> allULDForSegmentVOs = new ArrayList<ULDForSegmentVO>();
		Map<AssignedFlightSegmentPK, Collection<ContainerVO>> assignedFlightMap = new HashMap<AssignedFlightSegmentPK, Collection<ContainerVO>>();
		Collection<ContainerVO> containers = null;
		for (ContainerVO containerVO : containerVOs) {
			AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
			assignedFlightSegmentPK.setCarrierId(containerVO.getCarrierId());
			assignedFlightSegmentPK.setCompanyCode(containerVO.getCompanyCode());
			assignedFlightSegmentPK.setFlightNumber(containerVO.getFlightNumber());
			assignedFlightSegmentPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			assignedFlightSegmentPK.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			containers = assignedFlightMap.get(assignedFlightSegmentPK);
			if (containers == null) {
				containers = new ArrayList<ContainerVO>();
				assignedFlightMap.put(assignedFlightSegmentPK, containers);
			}
			containers.add(containerVO);
		}
		for (AssignedFlightSegmentPK assignedFlightSegmentPK : assignedFlightMap.keySet()) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				assignedFlightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode());
			}
			Collection<ULDForSegmentVO> ULDForSegmentVOs = assignedFlightSegment
					.saveArrivalDetailsForTransfer(assignedFlightMap.get(assignedFlightSegmentPK), toFlightVO, mailMap);
			allULDForSegmentVOs.addAll(ULDForSegmentVOs);
		}
		return allULDForSegmentVOs;
	}

	/** 
	* @author a-1883
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void saveMailArrivalDetailsForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		Map<AssignedFlightSegmentPK, Collection<MailbagVO>> assignedFlightMap = new HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>>();
		Collection<MailbagVO> mailbags = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (!MailbagVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
				AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
				assignedFlightSegmentPK.setCarrierId(mailbagVO.getCarrierId());
				assignedFlightSegmentPK.setCompanyCode(mailbagVO.getCompanyCode());
				assignedFlightSegmentPK.setFlightNumber(mailbagVO.getFlightNumber());
				assignedFlightSegmentPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				assignedFlightSegmentPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
				mailbags = assignedFlightMap.get(assignedFlightSegmentPK);
				if (mailbags == null) {
					mailbags = new ArrayList<MailbagVO>();
					assignedFlightMap.put(assignedFlightSegmentPK, mailbags);
				}
				mailbags.add(mailbagVO);
			} else {
				new MailbagInULDForSegment().updateTransferFlagForPreviousSegment(mailbagVO);
			}
		}
		for (AssignedFlightSegmentPK assignedFlightSegmentPK : assignedFlightMap.keySet()) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				assignedFlightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode());
			}
			assignedFlightSegment.saveMailArrivalDetailsForTransfer(assignedFlightMap.get(assignedFlightSegmentPK),
					containerVO);
		}
	}

	/** 
	* @param containerVO
	*/
	public void releaseContainer(ContainerVO containerVO) {
		Container con = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(this.getCompanyCode());
		containerPK.setAssignmentPort(this.getAirportCode());
		containerPK.setCarrierId(this.getCarrierId());
		containerPK.setFlightNumber(this.getFlightNumber());
		containerPK.setFlightSequenceNumber(this.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(this.getLegSerialNumber());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		try {
			con = Container.find(containerPK);
		} catch (FinderException e) {
			log.debug("Exception: while updating transit flag");
		}
		if (con != null) {
			con.setTransitFlag(MailConstantsVO.FLAG_NO);
		}
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
	 * @author A-5160 Added for ICRD-90823
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) {
		try {
			return constructDAO().validateMailFlight(flightFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-3227
	 * @param operationalFlightVO
	 * @return
	 */
	public static boolean checkRoutingsForMails(OperationalFlightVO operationalFlightVO, DSNVO dSNVO, String type) {
		try {
			return constructDAO().checkRoutingsForMails(operationalFlightVO, dSNVO, type);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Finds the ULDs in an Assigned Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findULDsInAssignedFlight(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findULDsInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Finds the Containers in an Assigned Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findContainersInAssignedFlight(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findContainersInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public static Collection<AWBDetailVO> findAWBDetails(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findAWBDetailsForFlight(operationalFlightVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getErrorCode(), exception.getMessage(), exception);
		}
	}

	/**
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailDiscrepancyVO> findMailDiscrepancies(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findMailDiscrepancies(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * Finds if any containers in a Assigned Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static String findAnyContainerInAssignedFlight(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findAnyContainerInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-5166Added for ICRD-36146 on 07-Mar-2013
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode) {
		return AssignedFlightSegment.findImportFlghtsForArrival(companyCode);
	}

	/**
	 * Finds the ULDs in a InboundFlight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findULDsInInboundFlight(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findULDsInInboundFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method		:	AssignedFlight.listFlightDetails Added by 	:	A-8164 on 25-Sep-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	Collection<MailArrivalVO>
	 */
	public static Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO) throws PersistenceException {
		return constructDAO().listFlightDetails(mailArrivalVO);
	}

	public static Collection<MailArrivalVO> listManifestDetails(MailArrivalVO mailArrivalVO)
			throws PersistenceException {
		return constructDAO().listManifestDetails(mailArrivalVO);
	}

	/**
	 * Finds all the containers in an Assigned Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerVO> findAllContainersInAssignedFlight(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findAllContainersInAssignedFlight(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	@Override
	public BaseEntity getParentEntity() {
		return this;
	}
	@Override
	public String getBusinessIdAsString(){
		return this.carrierId+","+this.flightNumber+","+this.flightSequenceNumber+","+this.legSerialNumber;
	}

}


