package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
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
@Table(name = "MALCSGMST")
@Entity
@IdClass(ConsignmentDocumentPK.class)
@SequenceGenerator(name = "MALCSGMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCSGMST_SEQ")
public class ConsignmentDocument extends BaseEntity implements Serializable {
	private static final String MODULE = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String PASS = "P";
	private static final String FAIL = "F";
	private static final String SCREENING_RESULT_PASS = "SPX,SCO";
	private static final String SCREENING_RESULT_FAIL = "NSC";
	private static final String COMMA = ",";
	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	@Id
	@Column(name = "POACOD")
	private String paCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCSGMSTSEQ")
	@Column(name = "CSGSEQNUM")
	private int consignmentSequenceNumber;
	@Column(name = "CSGDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime consignmentDate;
	@Column(name = "CSGTYP")
	private String type;
	@Column(name = "SUBTYP")
	private String subType;
	@Column(name = "OPRTYP")
	private String operation;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	private Set<RoutingInConsignment> routingsInConsignments;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	private Set<MailInConsignment> mailsInConsignments;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false) })
	private Set<ConsignmentScreeningDetails> consignmentScreeningDetails;
	@Column(name = "SECSTAPTY")
	private String securityStatusParty;
	@Column(name = "SECSTACOD")
	private String securityStatusCode;
	@Column(name = "ADLSECINF")
	private String additionalSecurityInfo;
	@Column(name = "SECSTADAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime securityStatusDate;
	@Column(name = "RMK")
	private String remarks;
	@Column(name = "STDBAG")
	private int statedBags;
	@Column(name = "STDWGT")
	private double statedWeight;
	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "CSGORG")
	private String consignmentOrigin;
	@Column(name = "CSGDST")
	private String consignmentDestination;
	@Column(name = "OPRORG")
	private String operatorOrigin;
	@Column(name = "OPRDST")
	private String operatorDestination;
	@Column(name = "ORGEXGOFCDES")
	private String OOEDescription;
	@Column(name = "DSTEXGOFCDES")
	private String DOEDescription;
	@Column(name = "CSGPRI")
	private String consignmentPriority;
	@Column(name = "TRPMNS")
	private String transportationMeans;
	@Column(name = "FLTDTL")
	private String flightDetails;
	@Column(name = "FLTRUT")
	private String flightRoute;
	@Column(name = "FSTFLTDEPDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime firstFlightDepartureDate;
	@Column(name = "ARLCOD")
	private String airlineCode;
	@Column(name = "POANAM")
	private String paName;
	@Column(name = "CSGISRNAM")
	private String consignmentIssuerName;
	@Column(name = "SHPPFX")
	private String shipmentPrefix;
	@Column(name = "MSTDOCNUM")
	private String masterDocumentNumber;
	@Column(name = "SHPUPUCOD")
	private String shipperUpuCode;
	@Column(name = "CNSUPUCOD")
	private String consigneeUpuCode;
	@Column(name = "CSGORGEXGOFCCOD")
	private String originUpuCode;
	@Column(name = "CSGDSTEXGOFCCOD")
	private String destinationUpuCode;

	/**
	* Default Constructor
	*/
	public ConsignmentDocument() {
	}

	/** 
	* @author a-5991
	* @param consignmentDocumentVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	public ConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO) throws DuplicateMailBagsException {
		log.debug("ConsignmentDocument" + " : " + "ConsignmentDocument" + " Entering");
		populatePk(consignmentDocumentVO);
		populateAttributes(consignmentDocumentVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		consignmentDocumentVO.setConsignmentSequenceNumber(this.getConsignmentSequenceNumber());
		populateChildren(consignmentDocumentVO);
		log.debug("ConsignmentDocument" + " : " + "ConsignmentDocument" + " Exiting");
	}

	/** 
	* @author a-5991
	* @param consignmentDocumentVO
	*/
	private void populatePk(ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("ConsignmentDocument" + " : " + "populatePk" + " Entering");
		this.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		this.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		this.setPaCode(consignmentDocumentVO.getPaCode());
		log.debug("ConsignmentDocument" + " : " + "populatePk" + " Exiting");
	}

	/** 
	* @author a-1883
	* @return MailTrackingDefaultsDAO
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		MailOperationsDAO mailOperationsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailOperationsDAO = MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailOperationsDAO;
	}

	/** 
	* @author a-1883
	* @param consignmentDocumentVO
	* @return ConsignmentDocumentVO
	* @throws SystemException
	*/
	public ConsignmentDocumentVO checkConsignmentDocumentExists(ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("ConsignmentDocument" + " : " + "checkConsignmentDocumentExists" + " Entering");
		try {
			return constructDAO().checkConsignmentDocumentExists(consignmentDocumentVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/** 
	* @author  a-5991
	* @param consignmentDocumentVO
	*/
	private void populateAttributes(ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("ConsignmentDocument" + " : " + "populateAttributes" + " Entering");
		if (consignmentDocumentVO.getConsignmentDate() != null) {
			this.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toLocalDateTime());
		}
		if (consignmentDocumentVO.getConsignmentPriority() == null) {
			consignmentDocumentVO.setConsignmentPriority(ConsignmentDocumentVO.FLAG_NO);
		}
		this.setOperation(consignmentDocumentVO.getOperation());
		this.setRemarks(consignmentDocumentVO.getRemarks());
		this.setStatedBags(consignmentDocumentVO.getStatedBags());
		if(consignmentDocumentVO.getStatedWeight()!=null) {
		this.setStatedWeight(consignmentDocumentVO.getStatedWeight().getValue().doubleValue());
		}
		this.setType(consignmentDocumentVO.getType());
		this.setSubType(consignmentDocumentVO.getSubType());
		this.setAirportCode(consignmentDocumentVO.getAirportCode());
		if (consignmentDocumentVO.getLastUpdateTime() != null) {
			this.setLastUpdatedTime(Timestamp.valueOf(consignmentDocumentVO.getLastUpdateTime().toLocalDateTime()));
		}
		this.setLastUpdatedUser(consignmentDocumentVO.getLastUpdateUser());
		this.setConsignmentOrigin(consignmentDocumentVO.getOrgin());
		this.setConsignmentDestination(consignmentDocumentVO.getDestination());
		this.setOperatorOrigin(consignmentDocumentVO.getOperatorOrigin());
		this.setOperatorDestination(consignmentDocumentVO.getOperatorDestination());
		this.setOOEDescription(consignmentDocumentVO.getOoeDescription());
		this.setDOEDescription(consignmentDocumentVO.getDoeDescription());
		this.setConsignmentPriority(consignmentDocumentVO.getConsignmentPriority());
		this.setTransportationMeans(consignmentDocumentVO.getTransportationMeans());
		this.setFlightDetails(consignmentDocumentVO.getFlightDetails());
		this.setFlightRoute(consignmentDocumentVO.getFlightRoute());
		if(consignmentDocumentVO.getFirstFlightDepartureDate()!=null) {
			this.setFirstFlightDepartureDate(consignmentDocumentVO.getFirstFlightDepartureDate().toLocalDateTime());
		}
		this.setAirlineCode(consignmentDocumentVO.getAirlineCode());
		this.setSecurityStatusParty(consignmentDocumentVO.getSecurityStatusParty());
		this.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
		if (consignmentDocumentVO.getSecurityStatusDate() != null) {
			this.setSecurityStatusDate(consignmentDocumentVO.getSecurityStatusDate().toLocalDateTime());
		}
		this.setAdditionalSecurityInfo(consignmentDocumentVO.getAdditionalSecurityInfo());
		this.setPaName(consignmentDocumentVO.getPaName());
		this.setConsignmentIssuerName(consignmentDocumentVO.getConsignmentIssuerName());
		this.setShipmentPrefix(consignmentDocumentVO.getShipmentPrefix());
		this.setMasterDocumentNumber(consignmentDocumentVO.getMasterDocumentNumber());
		this.setShipperUpuCode(consignmentDocumentVO.getShipperUpuCode());
		this.setConsigneeUpuCode(consignmentDocumentVO.getConsigneeUpuCode());
		this.setOriginUpuCode(consignmentDocumentVO.getOriginUpuCode());
		this.setDestinationUpuCode(consignmentDocumentVO.getDestinationUpuCode());
		log.debug("ConsignmentDocument" + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @author  a-5991
	* @param consignmentDocumentVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	private void populateChildren(ConsignmentDocumentVO consignmentDocumentVO) throws DuplicateMailBagsException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("ConsignmentDocument" + " : " + "populateChildren" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
		int carrierId = 0;
		if (routingInConsignmentVOs != null && routingInConsignmentVOs.size() > 0) {
			log.debug(" Going to create RoutingInConsignment ==>> ");
			Set<RoutingInConsignment> routingInConsignments = new HashSet<RoutingInConsignment>();
			for (RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs) {
				if (routingInConsignmentVO.getOnwardFlightDate() != null) {
					routingInConsignmentVO
							.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
					RoutingInConsignment routingInConsignment = new RoutingInConsignment(routingInConsignmentVO);
					carrierId = routingInConsignmentVO.getOnwardCarrierId();
					routingInConsignments.add(routingInConsignment);
				}
			}
			this.setRoutingsInConsignments(routingInConsignments);
		}
		if (consignmentDocumentVO.getConsignementScreeningVOs() != null
				&& !consignmentDocumentVO.getConsignementScreeningVOs().isEmpty()) {
			int bags = 0;
			double weight = 0;
			String airportCode = null;
			Collection<MailInConsignmentVO> mailInConsignment = consignmentDocumentVO.getMailInConsignmentVOs();
			for (MailInConsignmentVO mailInConsign : mailInConsignment) {
				bags += mailInConsign.getStatedBags();
				weight += mailInConsign.getStatedWeight().getValue().doubleValue();
				airportCode = mailInConsign.getMailOrigin();
			}
			Set<ConsignmentScreeningDetails> consignmentScreeningDetails = new HashSet<>();
			for (ConsignmentScreeningVO screeningVO : consignmentDocumentVO.getConsignementScreeningVOs()) {
				screeningVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
				screeningVO.setPaCode(consignmentDocumentVO.getPaCode());
				if ("CARDIT".equals(screeningVO.getSource()) || "FILUPL".equals(screeningVO.getSource())) {
					screeningVO.setStatedBags(bags);
					screeningVO
							.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(weight)));
					screeningVO.setAirportCode(airportCode);
					if (airportCode != null) {
						screeningVO.setScreeningLocation(airportCode);
					} else {
						screeningVO.setScreeningLocation(consignmentDocumentVO.getOrgin());
					}
					screeningVO.setResult(getScreeningResult(consignmentDocumentVO.getSecurityStatusCode()));
				}
			}
			mailController.saveSecurityDetails(consignmentDocumentVO.getConsignementScreeningVOs());
		}
		Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			log.debug(" Going to create MailInConsignment ==>> ");
			MailInConsignment mailInConsignment = null;
			int consignmentSeqNumber = consignmentDocumentVO.getConsignmentSequenceNumber();
			Set<MailInConsignment> mailInConsignments = new HashSet<MailInConsignment>();
			HashMap<String, Long> flightSeqNumMap = new HashMap<>();
			HashMap<String, String> destOfficeExchangeMap = new HashMap<>();
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (mailInConsignmentVO.getMailId() != null) {
					mailInConsignmentVO.setCarrierId(carrierId);
					Mailbag mailbag = findMailBag(mailInConsignmentVO);
					boolean isDuplicate = false;
					if (mailbag != null) {
						isDuplicate = mailController.checkForDuplicateMailbag(
								mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getPaCode(), mailbag);
						if (!isDuplicate) {
							mailbag.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
							mailbag.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
							mailbag.setPaCode(consignmentDocumentVO.getPaCode());
							if(mailInConsignmentVO.getReqDeliveryTime()!=null) {
								mailbag.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime().toLocalDateTime());
							}
							mailbag.setDespatchDate(consignmentDocumentVO.getConsignmentDate());
							if (mailInConsignmentVO.getMailDestination() != null) {
								mailbag.setDestination(mailInConsignmentVO.getMailDestination());
							}
							setSecurityStatusCode(consignmentDocumentVO, mailbag);
							mailbag.setDestinationOfficeOfExchange(mailInConsignmentVO.getDestinationExchangeOffice());
							if (mailInConsignmentVO.getStatedWeight() != null
									&& mailInConsignmentVO.getStatedWeight().getValue().doubleValue() != mailbag
											.getWeight()
									&& MailConstantsVO.CARDIT_PROCESS.equals(mailInConsignmentVO.getMailSource())) {
								String paCode = mailController.findSystemParameterValue(USPS_DOMESTIC_PA);
								if (paCode != null && paCode.equals(mailbag.getPaCode())) {
									mailbag.setWeight(mailInConsignmentVO.getStatedWeight().getValue().doubleValue());
									mailbag.setDisplayValue(
											mailInConsignmentVO.getStatedWeight().getDisplayValue().doubleValue());
									mailbag.setDisplayUnit(
											mailInConsignmentVO.getStatedWeight().getDisplayUnit().getName());
								}
							}
							mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
							if (MailInConsignmentVO.FLAG_YES.equals(mailInConsignmentVO.getPaBuiltFlag())) {
								mailbag.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
							}
						} else {
							isDuplicate = true;
						}
						new DocumentController().modifyExistingMailInConsignment(mailInConsignmentVO);
						if (mailInConsignmentVO.getContractIDNumber() != null
								&& mailInConsignmentVO.getContractIDNumber().trim().length() > 0) {
							mailbag.setContractIDNumber(mailInConsignmentVO.getContractIDNumber());
						}
						if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
							mailController.updatemailperformanceDetails(mailbag);
						}
					}
					if (mailbag == null || isDuplicate) {
						if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
							if (mailInConsignmentVO.getMailId() != null) {
								MailbagVO mailbagVO = new MailbagVO();
								mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
								mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
								mailbagVO.setDoe(mailInConsignmentVO.getDestinationExchangeOffice());
								mailbagVO.setOoe(mailInConsignmentVO.getOriginExchangeOffice());
								mailbagVO.setMailSubclass(mailInConsignmentVO.getMailSubclass());
								mailbagVO.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
								mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
								mailbagVO.setDespatchSerialNumber(mailInConsignmentVO.getDsn());
								mailbagVO.setDespatchId(createDespatchBag(mailInConsignmentVO));
								mailbagVO.setMailClass(mailInConsignmentVO.getMailClass());
								mailbagVO.setYear(mailInConsignmentVO.getYear());
								mailbagVO.setUldNumber(mailInConsignmentVO.getUldNumber());
								mailbagVO.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
								if (mailInConsignmentVO.getConsignmentDate() != null) {
									mailbagVO.setConsignmentDate(mailInConsignmentVO.getConsignmentDate());
								} else {
									mailbagVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
								}
								mailbagVO.setHighestNumberedReceptacle(
										mailInConsignmentVO.getHighestNumberedReceptacle());
								mailbagVO.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
								mailbagVO.setRegisteredOrInsuredIndicator(
										mailInConsignmentVO.getRegisteredOrInsuredIndicator());
								mailbagVO.setScannedPort(consignmentDocumentVO.getAirportCode());
								mailbagVO.setScannedDate(consignmentDocumentVO.getConsignmentDate());
								mailbagVO.setCarrierId(mailInConsignmentVO.getCarrierId());
								mailbagVO.setWeight(mailInConsignmentVO.getStatedWeight());
								mailbagVO.setVolume(mailInConsignmentVO.getVolume());
								mailbagVO.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
								mailbagVO.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
								mailbagVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
								mailbagVO.setConsignmentSequenceNumber(
										consignmentDocumentVO.getConsignmentSequenceNumber());
								mailbagVO.setPaCode(consignmentDocumentVO.getPaCode());
								mailbagVO.setLatestStatus("NEW");
								mailbagVO.setLastUpdateUser(consignmentDocumentVO.getLastUpdateUser());
								mailbagVO.setScannedUser(consignmentDocumentVO.getLastUpdateUser());
								mailbagVO.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
								mailbagVO.setDisplayUnit(mailInConsignmentVO.getDisplayUnit());
								if (mailInConsignmentVO.getMailServiceLevel() != null
										&& !mailInConsignmentVO.getMailServiceLevel().isEmpty()) {
									mailbagVO.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
								}
								String destination = null;
								if (!destOfficeExchangeMap.isEmpty() && destOfficeExchangeMap
										.containsKey(mailInConsignmentVO.getDestinationExchangeOffice())) {
									destination = destOfficeExchangeMap
											.get(mailInConsignmentVO.getDestinationExchangeOffice());
								} else {
									try {
										destination = constructDAO().findCityForOfficeOfExchange(
												mailInConsignmentVO.getCompanyCode(),
												mailInConsignmentVO.getDestinationExchangeOffice());
									} catch (PersistenceException e) {
									}
									destOfficeExchangeMap.put(mailInConsignmentVO.getDestinationExchangeOffice(),
											destination);
								}
								if (routingInConsignmentVOs != null && routingInConsignmentVOs.size() > 0) {
									for (RoutingInConsignmentVO routingVO : routingInConsignmentVOs) {
										if (routingVO.getPou() != null) {
											if (routingVO.getPou().equals(destination)) {
												mailbagVO.setFlightNumber(routingVO.getOnwardFlightNumber());
												mailbagVO.setFlightDate(routingVO.getOnwardFlightDate());
												if (flightSeqNumMap != null && !flightSeqNumMap.isEmpty()
														&& flightSeqNumMap.containsKey(destination)) {
													mailbagVO.setFlightSequenceNumber(flightSeqNumMap.get(destination));
												} else {
													FlightFilterVO flightFilterVO = new FlightFilterVO();
													flightFilterVO.setCompanyCode(routingVO.getCompanyCode());
													flightFilterVO.setFlightNumber(
															routingVO.getOnwardFlightNumber().toUpperCase());
													flightFilterVO.setStation(routingVO.getPol());
													flightFilterVO.setDirection(mailInConsignmentVO.getOperation());
													flightFilterVO.setActiveAlone(false);
													flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(routingVO.getOnwardFlightDate()));
													Collection<FlightValidationVO> flightValidationVOs = null;
													flightValidationVOs =
															 mailController.validateFlight(flightFilterVO);
													if (flightValidationVOs != null
															&& flightValidationVOs.size() == 1) {
														for (FlightValidationVO vo : flightValidationVOs) {
															mailbagVO.setFlightSequenceNumber(
																	vo.getFlightSequenceNumber());
															flightSeqNumMap.put(destination,
																	vo.getFlightSequenceNumber());
														}
													} else {
														flightSeqNumMap.put(destination, 0L);
													}
												}
												break;
											}
										} else {
											if (routingInConsignmentVOs != null && routingInConsignmentVOs.size() > 0) {
												RoutingInConsignmentVO routingInConsignmentVO = routingInConsignmentVOs
														.iterator().next();
												mailbagVO.setFlightNumber(
														routingInConsignmentVO.getOnwardFlightNumber());
												mailbagVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
												mailbagVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
												mailbagVO.setFlightSequenceNumber(
														routingInConsignmentVO.getOnwardCarrierSeqNum());
												mailbagVO.setPou(routingInConsignmentVO.getPou());
												break;
											} else {
												mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
												mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
											}
										}
									}
								}
								if (mailInConsignmentVO.getMailOrigin() == null
										|| mailInConsignmentVO.getMailOrigin().isEmpty()
										|| mailInConsignmentVO.getMailDestination() == null
										|| mailInConsignmentVO.getMailDestination().isEmpty()) {
									mailController.constructOriginDestinationDetailsForConsignment(
											mailInConsignmentVO, mailbagVO);
								} else {
									mailbagVO.setOrigin(mailInConsignmentVO.getMailOrigin());
									mailbagVO.setDestination(mailInConsignmentVO.getMailDestination());
								}
								if ((mailbagVO.getDestination() == null || mailbagVO.getDestination().isEmpty())
										&& mailbagVO.getFinalDestination() == null) {
									String dest = findAirportCity(mailbagVO);
									mailbagVO.setDestination(dest);
								}
								mailbagVO.setMailbagSource(mailInConsignmentVO.getMailSource());
								mailbagVO.setMailbagDataSource(mailInConsignmentVO.getMailSource());
								String scanWavedDestn = constructDAO().checkScanningWavedDest(mailbagVO);
								if (scanWavedDestn != null) {
									mailbagVO.setScanningWavedFlag(scanWavedDestn);
								}
								if (mailController.isUSPSMailbag(mailbagVO)) {
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
								} else {
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
								}
								mailbagVO.setContractIDNumber(mailInConsignmentVO.getContractIDNumber());
								mailbagVO.setTransWindowEndTime(mailInConsignmentVO.getTransWindowEndTime());
								mailbagVO.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
								mailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
								mailbag = new Mailbag(mailbagVO);
								if (mailbag != null) {
									mailInConsignmentVO
											.setMailSequenceNumber(mailbag.getMailSequenceNumber());
								}
							}
						}
					}
					mailInConsignmentVO.setConsignmentSequenceNumber(consignmentSeqNumber);
					mailInConsignment = new MailInConsignment(mailInConsignmentVO);
					mailInConsignments.add(mailInConsignment);
					this.statedBags += mailInConsignmentVO.getStatedBags();
					this.statedWeight += mailInConsignmentVO.getStatedWeight().getValue().doubleValue();
				}
				this.setMailsInConsignments(mailInConsignments);
			}
			log.debug("ConsignmentDocument" + " : " + "populateChildren" + " Exiting");
		}
	}

	public static String createDespatchBag(MailInConsignmentVO mailInConsignmentVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(mailInConsignmentVO.getOriginExchangeOffice())
				.append(mailInConsignmentVO.getDestinationExchangeOffice())
				.append(mailInConsignmentVO.getMailCategoryCode()).append(mailInConsignmentVO.getMailSubclass())
				.append(mailInConsignmentVO.getYear()).append(mailInConsignmentVO.getDsn());
		return dsnid.toString();
	}

	/** 
	* @author a-1883
	* @param mailInConsignmentVO
	* @return
	* @throws SystemException
	*/
	private Mailbag findMailBag(MailInConsignmentVO mailInConsignmentVO) {
		Mailbag mailbag = null;
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(
				findMailSequenceNumber(mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException finderException) {
			log.debug(" ++++  Finder Exception  +++");
			log.debug(" <===  Mailbag is Not accepted ===>");
		}
		return mailbag;
	}

	/** 
	* @author A-5991	
	* @param mailIdr
	* @param companyCode
	* @return
	* @throws SystemException 
	*/
	private long findMailSequenceNumber(String mailIdr, String companyCode) {
		return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
	}

	/** 
	* @author  a-5991
	* @param consignmentDocumentVO
	* @throws SystemException
	*/
	public void update(ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("ConsignmentDocument" + " : " + "update" + " Entering");
		populateAttributes(consignmentDocumentVO);
		if (consignmentDocumentVO.getConsignementScreeningVOs() != null
				&& !consignmentDocumentVO.getConsignementScreeningVOs().isEmpty()) {
			Set<ConsignmentScreeningDetails> consignmentScreeningDetails = new HashSet<>();
			for (ConsignmentScreeningVO screeningVO : consignmentDocumentVO.getConsignementScreeningVOs()) {
				screeningVO.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
				consignmentScreeningDetails.add(new ConsignmentScreeningDetails(screeningVO));
			}
			this.setConsignmentScreeningDetails(consignmentScreeningDetails);
		}
		log.debug("ConsignmentDocument" + " : " + "update" + " Exiting");
	}

	/** 
	* @author  a-5991
	* @throws SystemException
	*/
	public void remove() {
		log.debug("RoutingInConsignment" + " : " + "remove" + " Entering");
		try {
			Collection<RoutingInConsignment> routingInConsignments = this.getRoutingsInConsignments();
			Collection<MailInConsignment> mailInConsignments = this.getMailsInConsignments();
			Collection<ConsignmentScreeningDetails> screeningDetails = this.getConsignmentScreeningDetails();
			if (routingInConsignments != null && routingInConsignments.size() > 0) {
				for (RoutingInConsignment routingInConsignment : routingInConsignments) {
					routingInConsignment.remove();
				}
				this.routingsInConsignments.removeAll(routingInConsignments);
			}
			if (mailInConsignments != null && mailInConsignments.size() > 0) {
				for (MailInConsignment mailInConsignment : mailInConsignments) {
					mailInConsignment.remove();
				}
				this.mailsInConsignments.removeAll(mailInConsignments);
			}
			if (screeningDetails != null && screeningDetails.size() > 0) {
				for (ConsignmentScreeningDetails screeningDetail : screeningDetails) {
					screeningDetail.remove();
				}
				this.consignmentScreeningDetails.removeAll(screeningDetails);
			}
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			removeException.getErrorCode();
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
		log.debug("RoutingInConsignment" + " : " + "remove" + " Exiting");
	}

	/** 
	* @author  a-5991
	* @param consignmentDocumentVO
	* @return
	* @throws SystemException
	*/
	public static ConsignmentDocument find(ConsignmentDocumentVO consignmentDocumentVO) {
		log.debug("ConsignmentDocument" + " : " + "find" + " Entering");
		ConsignmentDocument consignmentDocument = null;
		ConsignmentDocumentPK consignmentDocumentPk = new ConsignmentDocumentPK();
		consignmentDocumentPk.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		consignmentDocumentPk.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		consignmentDocumentPk.setPaCode(consignmentDocumentVO.getPaCode());
		consignmentDocumentPk.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
		try {
			consignmentDocument = PersistenceController.getEntityManager().find(ConsignmentDocument.class,
					consignmentDocumentPk);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(), finderException.getMessage(), finderException);
		}
		return consignmentDocument;
	}

	/** 
	* This method finds mail sequence number
	* @param mailInConsignmentVO
	* @return int
	* @throws SystemException
	*/
	public static int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO) {
		log.debug("ConsignmentDocument" + " : " + "findMailSequenceNumber" + " Entering");
		return MailInConsignment.findMailSequenceNumber(mailInConsignmentVO);
	}

	/** 
	* @author A-2037
	* @param companyCode
	* @param mailId
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	public static MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode, String mailId,
			String airportCode) {
		log.debug("ConsignmentDocument" + " : " + "findConsignmentDetailsForMailbag" + " Entering");
		return MailInConsignment.findConsignmentDetailsForMailbag(companyCode, mailId, airportCode);
	}

	/** 
	* @author A-7540
	* @param mailbagVO
	* @return
	*/
	private String findAirportCity(MailbagVO mailbagVO) {
		String dest = mailbagVO.getDoe().substring(2, 5);
		String city = null;
		try {
			city = constructDAO().findAirportCityForMLD(mailbagVO.getCompanyCode(), dest);
		} catch (PersistenceException e) {
			e.getMessage();
		}
		return city;
	}

	/** 
	* Method returns screening result as pass or fail Screening result value depends on the security status code
	* @return String
	*/
	private String getScreeningResult(String securityStatusCode) {
		String screenResultPass = SCREENING_RESULT_PASS;
		String screenResultFail = SCREENING_RESULT_FAIL;
		List<String> screenResultPassList = Arrays.asList(screenResultPass.split(COMMA));
		List<String> screenResultFailList = Arrays.asList(screenResultFail.split(COMMA));
		if (Objects.nonNull(securityStatusCode) && screenResultPassList.stream().anyMatch(securityStatusCode::equals)) {
			return PASS;
		}
		if (Objects.nonNull(securityStatusCode) && screenResultFailList.stream().anyMatch(securityStatusCode::equals)) {
			return FAIL;
		}
		return null;
	}

	/** 
	* @author A-8353
	* @param consignmentDocumentVO
	* @param mailbag
	*/
	private void setSecurityStatusCode(ConsignmentDocumentVO consignmentDocumentVO, Mailbag mailbag) {
		if (consignmentDocumentVO.getSecurityStatusCode() != null) {
			mailbag.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
		}
	}
	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static ConsignmentDocumentVO findConsignmentDocumentDetails(ConsignmentFilterVO consignmentFilterVO) {
		try {
			return constructDAO().findConsignmentDocumentDetails(consignmentFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * @author A-3227  - FEB 10, 2009
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static DespatchDetailsVO findConsignmentDetailsForDespatch(String companyCode,
																	  DespatchDetailsVO despatchDetailsVO) {
		try {
			return constructDAO().findConsignmentDetailsForDespatch(companyCode, despatchDetailsVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	public static ConsignmentDocumentVO generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().generateConsignmentSummaryReport(consignmentFilterVO);
	}

	public static ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO filterVO) {
		return constructDAO().generateConsignmentSecurityReportDtls(filterVO);
	}
	public static ConsignmentDocumentVO generateConsignmentReport(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().generateConsignmentReport(consignmentFilterVO);
	}

    public void updateMasterDocumentNumber(ConsignmentDocumentVO consignmentDocumentVO) {
		this.setMasterDocumentNumber(consignmentDocumentVO.getMasterDocumentNumber());
    }
}
