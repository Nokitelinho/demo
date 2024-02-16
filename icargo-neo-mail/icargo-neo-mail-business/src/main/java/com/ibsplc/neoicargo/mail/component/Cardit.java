package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


/** 
 * Entity for a Cardit Message
 * @author A-1739
 */
@Setter
@Getter
@Entity
@Slf4j
@IdClass(CarditPK.class)
@Table(name = "MALCDTMST")
@NoArgsConstructor
public class Cardit  extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String MODEOFTRANSPORT_ROAD = "3";

	/** 
	* Interchange reference
	*/
	@Column(name = "INTCTLREF")
	private String interchangeCtrlReference;
	/** 
	* Syntax id of this interchange
	*/
	@Column(name = "STXIDR")
	private String interchangeSyntaxId;
	/** 
	* Syntax version
	*/
	@Column(name = "STXVER")
	private int interchangeSyntaxVer;
	/** 
	* Recipient idr
	*/
	@Column(name = "RCTIDR")
	private String recipientId;
	/** 
	* Sender id
	*/
	@Column(name = "SDRIDR")
	private String senderId;
	/** 
	* Sender id
	*/
	@Column(name = "ACTSDRIDR")
	private String actualSenderId;
	/** 
	* consignment completion date
	*/
	@Column(name = "CSGCMPDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime consignmentDate;
	/** 
	* Interchange preparation date
	*/

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRPDAT")
	private LocalDateTime preparationDate;
	/** 
	* Consignment doc num
	*/
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	/** 
	* message name
	*/
	@Column(name = "MSGNAM")
	private String messageName;
	/** 
	* mail catergory code
	*/
	@Column(name = "MALCTGCOD")
	private String mailCategoryCode;
	/** 
	* Msg reference num
	*/
	@Column(name = "MSGREFNUM")
	private String messageRefNum;
	/** 
	* message version
	*/
	@Column(name = "MSGVERNUM")
	private String messageVersion;
	/** 
	* message segment number
	*/
	@Column(name = "MSGSEGNUM")
	private int messageSegmentNum;
	/** 
	* message release number
	*/
	@Column(name = "MSGRLSNUM")
	private String messageReleaseNum;
	/** 
	* Message type identifier
	*/
	@Column(name = "MSGTYPIDR")
	private String messageTypeId;
	/** 
	* testIndicator
	*/
	@Column(name = "TSTIND")
	private int tstIndicator;
	/** 
	* Controlling agency
	*/
	@Column(name = "CTLAGY")
	private String controlAgency;
	/** 
	* Interchange control count
	*/
	@Column(name = "INTCTLNUM")
	private int interchangeControlCnt;
	/** 
	* cardit
	*/
	@Column(name = "CDTRCVDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime carditReceivedDate;
	@Column(name = "CDTDATUTC")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime utcReceivedTime;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Transient
	private String companyCode;
	/** 
	* The sequenceNum in the MSGMSGMST table
	*/
	@Column(name = "CDTSEQNUM")
	private long messageSequenceNum;
	/** 
	* The STNCOD in MSGMSGMST table for finding this msg again
	*/
	@Column(name = "STNCOD")
	private String stationCode;
	/** 
	* The CDTTYP : CarditType (Message Function) 
	*/
	@Column(name = "CDTTYP")
	private String carditType;


	/**
	* @author A-1739
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public static String findCarditForMailbag(String companyCode, String mailbagId) {
		try {
			return constructDAO().findCarditForMailbag(companyCode, mailbagId);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author A-1739
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("Query DAO not found", exception.getMessage(), exception);
		}
	}

	/**
	* @author A-1936This method is used to find the Cardit Details for the MailBags namely CarditSequenceNumber,ConsignmentNumber,CarditKey. ADDED AS THE PART OF NCA-CR
	* @param companyCode
	* @param mailID
	* @return
	*/
	public static MailbagVO findCarditDetailsForAllMailBags(String companyCode, long mailID) {
		try {
			return constructDAO().findCarditDetailsForAllMailBags(companyCode, mailID);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage(), e);
		}
	}

	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY", insertable = false, updatable = false) })
	private Set<CarditTransportation> transportInformation;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY", insertable = false, updatable = false) })
	private Set<CarditReceptacle> receptacleInformation;
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY",insertable = false, updatable = false) })
	private Set<CarditContainer> containerInformation;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY", insertable = false, updatable = false) })
	private Set<CarditTotal> totalsInformation;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY", insertable = false, updatable = false) })
	private Set<CarditReference> carditReferenceInformation;
	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CDTKEY", referencedColumnName = "CDTKEY", insertable = false, updatable = false) })
	private Set<CarditHandover> carditHandoverInformation;

	/**
	 * @param carditVO
	 * @throws SystemException
	 */
	public Cardit(CarditVO carditVO) {
		log.debug("Persisting Cardit***");
		populatePk(carditVO);
		populateAttributes(carditVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			exception.getErrorCode();
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		populateChildren(carditVO);
		log.debug("Cardit Persisted---");
	}

    public static CarditVO findCarditDetailsForResdit(String companyCode, String consignmentNumber) {
		try {
			return constructDAO().findCarditDetailsForResdit(
					companyCode, consignmentNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
    }

	public static PreAdviceVO findPreAdvice(OperationalFlightVO operationalFlightVO)  throws SystemException{
		Cardit cardit = new Cardit();
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		//Commented by A-5201 for ICRD-95502
		 /*Collection<PreAdviceDetailsVO> preAdviceDetailsVOForULD = cardit
				.findULDInCARDIT(operationalFlightVO);*/
		Collection<PreAdviceDetailsVO> preAdviceDetailsVOForMailbag = cardit
				.findMailbagsInCARDIT(operationalFlightVO);
		Collection<PreAdviceDetailsVO> preAdviceDetailsVOs = new ArrayList<PreAdviceDetailsVO>();
		//preAdviceDetailsVOs.addAll(preAdviceDetailsVOForULD);
		preAdviceDetailsVOs.addAll(preAdviceDetailsVOForMailbag);
		PreAdviceVO preAdviceVO = new PreAdviceVO();
		preAdviceVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		preAdviceVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		preAdviceVO.setCarrierId(operationalFlightVO.getCarrierId());
		preAdviceVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		preAdviceVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		preAdviceVO.setFlightDate(operationalFlightVO.getFlightDate());
		preAdviceVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());

		if(preAdviceDetailsVOs!= null && preAdviceDetailsVOs.size()>0){
			for(PreAdviceDetailsVO details:preAdviceDetailsVOs ){
				if((details.getUldNumbr() == null) ||
						(details.getUldNumbr() != null && details.getUldNumbr().trim().length() > 0)) {
					preAdviceVO.setTotalBags(preAdviceVO.getTotalBags()+details.getTotalbags());

					try {
						if (Objects.nonNull(preAdviceVO.getTotalWeight())) {
						preAdviceVO.setTotalWeight(preAdviceVO.getTotalWeight().add(details.getTotalWeight()));
					}
					else{
							preAdviceVO.setTotalWeight(details.getTotalWeight());
						} }

					finally {
					}
				}
			}
		}
		preAdviceVO.setPreAdviceDetails(preAdviceDetailsVOs);
		return preAdviceVO;
	}

	private Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(OperationalFlightVO operationalFlightVO) {
		try {
			return constructDAO().findMailbagsInCARDIT(operationalFlightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Populates the primaryKeyValues
	 * @param carditVO
	 */
	private void populatePk(CarditVO carditVO) {
		this.setCompanyCode(carditVO.getCompanyCode());
		this.setCarditKey(carditVO.getCarditKey());
	}

	/**
	 * populates the attributes
	 * @param carditVO
	 * @throws SystemException
	 */
	private void populateAttributes(CarditVO carditVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		LocalDate localDateUtil = contextUtil.getBean(LocalDate.class);
		carditVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		setCarditReceivedDate(carditVO.getCarditReceivedDate().toLocalDateTime());
		setUtcReceivedTime(localDateUtil.toUTCTime(carditVO.getCarditReceivedDate()).toLocalDateTime());
		if (carditVO.getConsignmentDate() != null) {
			setConsignmentDate(carditVO.getConsignmentDate().toLocalDateTime());
		}
		setConsignmentNumber(carditVO.getConsignmentNumber());
		setControlAgency(carditVO.getControlAgency());
		setInterchangeCtrlReference(carditVO.getInterchangeCtrlReference());
		setInterchangeSyntaxId(carditVO.getInterchangeSyntaxId());
		setInterchangeSyntaxVer(carditVO.getInterchangeSyntaxVer());
		setInterchangeControlCnt(carditVO.getInterchangeControlCnt());
		setMailCategoryCode(carditVO.getMailCategoryCode());
		setMessageName(carditVO.getMessageName());
		setMessageRefNum(carditVO.getMessageRefNum());
		setMessageReleaseNum(carditVO.getMessageReleaseNum());
		setMessageSegmentNum(carditVO.getMessageSegmentNum());
		setMessageTypeId(carditVO.getMessageTypeId());
		setMessageVersion(carditVO.getMessageVersion());
		if (carditVO.getPreparationDate() != null) {
			setPreparationDate(carditVO.getPreparationDate().toLocalDateTime());
		}
		setRecipientId(carditVO.getRecipientId());
		setSenderId(carditVO.getSenderId());
		setActualSenderId(carditVO.getActualSenderId());
		setMessageSequenceNum(carditVO.getMsgSeqNum());
		setStationCode(carditVO.getStationCode());
		setLastUpdatedUser(carditVO.getLastUpdateUser());
		if (carditVO.getCarditType() != null && carditVO.getCarditType().length() > 0) {
			setCarditType(carditVO.getCarditType());
		} else {
			setCarditType(MailConstantsVO.FLAG_NO);
		}
	}

	/**
	 * @author A-1739
	 * @param carditVO
	 * @throws SystemException
	 */
	private void populateChildren(CarditVO carditVO) {
		Collection<CarditTransportationVO> transportInformationVOs = carditVO.getTransportInformation();
		if (transportInformationVOs != null && transportInformationVOs.size() > 0) {
			removeChildCarditTransportation();
			for (CarditTransportationVO transportInformationVO : transportInformationVOs) {
				log.debug("Cardit Transportation  Not Found  So Persisting");
				if (!(transportInformationVO.getFlightNumber() == null
						&& MODEOFTRANSPORT_ROAD.equalsIgnoreCase(transportInformationVO.getModeOfTransport()))) {
					populateTransportation(transportInformationVO);
				}
			}
		}
		Collection<CarditReceptacleVO> receptacleInformationVOs = carditVO.getReceptacleInformation();
		Collection<CarditReceptacleVO> receptacleInformationToRemoveVOs = new ArrayList<CarditReceptacleVO>();
		boolean isDuplicate = false;
		if (receptacleInformationVOs != null && receptacleInformationVOs.size() > 0) {
			for (CarditReceptacleVO carditReceptacleVO : receptacleInformationVOs) {
				int found = 0;
				String oldCarditKey = "";
				String carditKey = findCarditReceptacle(carditReceptacleVO.getReceptacleId());
				if (carditKey != null) {
					String[] keys = carditKey.split("~");
					oldCarditKey = keys[0];
					found = Integer.parseInt(keys[1]);
				}
				String orgPaCod = null;
				Mailbag mailbag = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(carditVO.getCompanyCode());
				long mailSeqnum = Mailbag.findMailSequenceNumber(carditReceptacleVO.getReceptacleId(),
						carditVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailSeqnum);
				try {
					mailbag = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					mailbag = null;
				}
				if (mailbag != null) {
					MailbagVO mailbagVO = mailbag.retrieveVO();
					String OOE = mailbagVO.getOoe();
					PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					try {
						orgPaCod = ContextUtil.getInstance().getBean(MailController.class).findPAForOfficeOfExchange(mailbagVO.getCompanyCode(), OOE);
					} finally {
					}
					try {
						postalAdministrationVO = ContextUtil.getInstance().getBean(MailController.class).findPACode(mailbagVO.getCompanyCode(), orgPaCod);
					} finally {
					}
					try {if(postalAdministrationVO!=null)
						isDuplicate = checkForDuplicateMailbag(mailbagVO.getCompanyCode(),
								postalAdministrationVO.getPaCode(), mailbag);
					} catch (DuplicateMailBagsException e) {
						e.getMessage();
					}
					if (!isDuplicate) {
						Integer consignmentSeqNumber = 1;
						mailbag.setConsignmentNumber(carditVO.getConsignmentNumber());
						mailbag.setConsignmentSequenceNumber(consignmentSeqNumber.intValue());
						mailbag.setDespatchDate(carditVO.getConsignmentDate());
						mailbag.setOrginOfficeOfExchange(carditReceptacleVO.getOriginExchangeOffice());
						mailbag.setDestinationOfficeOfExchange(carditReceptacleVO.getDestinationExchangeOffice());
						if (carditReceptacleVO.getMailOrigin() != null
								&& carditReceptacleVO.getMailOrigin().length() <= 3) {
							mailbag.setOrigin(carditReceptacleVO.getMailOrigin());
						}
						if (carditReceptacleVO.getMailDestination() != null
								&& carditReceptacleVO.getMailDestination().length() <= 3) {
							mailbag.setDestination(carditReceptacleVO.getMailDestination());
						}
					} else {
						found = 0;
					}
				}
				if (found == 0 || carditVO.isSenderIdChanged()) {
					log.debug("Cardit Receptacle Not Found  So Persisting ");
					log.info("Persisting cardit Receptacle------" + carditReceptacleVO.getReceptacleId());
					populateReceptacleDetails(carditReceptacleVO);
				} else {
					receptacleInformationToRemoveVOs.add(carditReceptacleVO);
					CarditReceptacle carditRcp = new CarditReceptacle();
					if (oldCarditKey != "" || oldCarditKey != null) {
						CarditReceptaclePK carditRcpPK = new CarditReceptaclePK();
						;
						carditRcpPK.setCarditKey(oldCarditKey);
						carditRcpPK.setCompanyCode(carditVO.getCompanyCode());
						carditRcpPK.setReceptacleId(carditReceptacleVO.getReceptacleId());
						try {
							carditRcp = CarditReceptacle.find(carditRcpPK);
						} catch (FinderException e) {
							e.getMessage();
						}
						if (carditRcp != null) {
							carditRcp.remove();
							carditReceptacleVO.setCarditKey(carditVO.getCarditKey());
							populateReceptacleDetails(carditReceptacleVO);
						}
					}
				}
			}
			log.debug("\nRemoving Duplicate Receptacles from cardit VO......\n");
			if (receptacleInformationToRemoveVOs != null && receptacleInformationToRemoveVOs.size() > 0
					&& isDuplicate) {
				receptacleInformationVOs.removeAll(receptacleInformationToRemoveVOs);
			}
		}
		carditVO.setReceptacleInformation(receptacleInformationVOs);
		log.debug("\n Cardit VO after duplicate validation......\n");
		Collection<CarditContainerVO> containerInformationVOs = carditVO.getContainerInformation();
		if (containerInformationVOs != null && containerInformationVOs.size() > 0) {
			for (CarditContainerVO carditContainerVO : containerInformationVOs) {
				log.debug("" + "the Cardit Container Vo" + " " + carditContainerVO);
				CarditContainer carditCont = findCarditContainer(carditContainerVO.getContainerNumber());
				if ((carditCont == null) && (carditContainerVO.getContainerNumber() != null)
						&& (carditContainerVO.getContainerNumber().trim().length() > 0)) {
					log.debug("Cardit Container Not Found  So Persisting");
					populateContainerDetails(carditContainerVO);
				}
			}
		}
		Collection<CarditTotalVO> totalsInformationVOs = carditVO.getTotalsInformation();
		if (totalsInformationVOs != null && totalsInformationVOs.size() > 0) {
			removeChildCarditTotal();
			for (CarditTotalVO carditTotalVO : totalsInformationVOs) {
				log.debug("Cardit Total Persisting");
				populateTotalDetails(carditTotalVO);
			}
		}
		Collection<CarditHandoverInformationVO> carditHandoverInformationVOs = carditVO.getHandoverInformation();
		if (carditHandoverInformationVOs != null && carditHandoverInformationVOs.size() > 0) {
			removeChildCarditHandover();
			for (CarditHandoverInformationVO carditHandoverInfoVO : carditHandoverInformationVOs) {
				log.debug("Cardit Handover Persisting");
				populateHandoverDetails(carditHandoverInfoVO);
			}
		}
		Collection<CarditReferenceInformationVO> carditReferenceInformationVOs = carditVO.getReferenceInformation();
		if (carditReferenceInformationVOs != null && carditReferenceInformationVOs.size() > 0) {
			removeChildCarditReference();
			for (CarditReferenceInformationVO carditReferenceInfoVO : carditReferenceInformationVOs) {
				log.debug("Cardit Reference Persisting");
				populateReferenceDetails(carditReferenceInfoVO);
			}
		}
	}

	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditTransportation() {
		Collection<CarditTransportation> carditTransportationInfo = getTransportInformation();
		if (carditTransportationInfo != null && carditTransportationInfo.size() > 0) {
			log.debug("Cardit Transport Child Removing");
			do {
				CarditTransportation carditTransport = null;
				for (CarditTransportation carditTransportInfo : carditTransportationInfo) {
					carditTransport = carditTransportInfo;
					break;
				}
				carditTransportationInfo.remove(carditTransport);
				carditTransport.remove();
				log.debug("Cardit Transport Child Removed");
			} while (carditTransportationInfo.iterator().hasNext());
		}
	}

	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditTotal() {
		Collection<CarditTotal> totalsInfo = getTotalsInformation();
		if (totalsInfo != null && totalsInfo.size() > 0) {
			log.debug("Cardit Total Child Removing");
			do {
				CarditTotal carditTotl = null;
				for (CarditTotal carditTot : totalsInfo) {
					carditTotl = carditTot;
					break;
				}
				totalsInfo.remove(carditTotl);
				carditTotl.remove();
				log.debug("Cardit Total Child Removed");
			} while (totalsInfo.iterator().hasNext());
		}
	}

	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditHandover() {
		Collection<CarditHandover> carditHandoverInfo = getCarditHandoverInformation();
		if (carditHandoverInfo != null && carditHandoverInfo.size() > 0) {
			log.debug("Cardit Handover Child Removing");
			do {
				CarditHandover carditHndovr = null;
				for (CarditHandover carditHandovr : carditHandoverInfo) {
					carditHndovr = carditHandovr;
					break;
				}
				carditHandoverInfo.remove(carditHndovr);
				carditHndovr.remove();
				log.debug("Cardit Handover Child Removed");
			} while (carditHandoverInfo.iterator().hasNext());
		}
	}

	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	private void removeChildCarditReference() {
		Collection<CarditReference> carditReferenceInfo = getCarditReferenceInformation();
		if (carditReferenceInfo != null && carditReferenceInfo.size() > 0) {
			log.debug("Cardit Reference Child Removing");
			do {
				CarditReference carditRefer = null;
				for (CarditReference carditRef : carditReferenceInfo) {
					carditRefer = carditRef;
					break;
				}
				carditReferenceInfo.remove(carditRefer);
				carditRefer.remove();
				log.debug("Cardit Reference Child Removed");
			} while (carditReferenceInfo.iterator().hasNext());
		}
	}

	/**
	 * @author A-3227
	 * @param transportInformationVO
	 * @throws SystemException
	 */
	private void populateTransportation(CarditTransportationVO transportInformationVO) {

		CarditTransportationPK carditTransportPK = new CarditTransportationPK();
		carditTransportPK.setCompanyCode(this.getCompanyCode());
		carditTransportPK.setCarditKey(this.getCarditKey());
		if (transportInformationVO != null) {
			carditTransportPK.setArrivalPort(transportInformationVO.getArrivalPort());
			if (transportInformation == null) {
				transportInformation = new HashSet<CarditTransportation>();
			}
			transportInformation.add(new CarditTransportation(carditTransportPK, transportInformationVO));
		}
	}

	/**
	 * @author A-3227
	 * @param carditReceptacleVO
	 * @throws SystemException
	 */
	private void populateReceptacleDetails(CarditReceptacleVO carditReceptacleVO) {
		CarditPK carditPK = constructCarditPK();
		if (carditReceptacleVO != null) {
			if (receptacleInformation == null) {
				receptacleInformation = new HashSet<CarditReceptacle>();
			}
			try {
				//CarditReceptacle.find(new CarditReceptacle().createCarditReceptaclePK(carditPK, carditReceptacleVO));

				CarditReceptaclePK carditRcpPK =new CarditReceptaclePK();
				carditRcpPK.setCarditKey(carditPK.getCarditKey());
				carditRcpPK.setCompanyCode(carditPK.getCompanyCode());
				carditRcpPK.setReceptacleId(carditReceptacleVO.getReceptacleId());
				CarditReceptacle.find(carditRcpPK);
			} catch (FinderException e) {
				receptacleInformation.add(new CarditReceptacle(carditPK, carditReceptacleVO));
			}
		}
	}

	/**
	 * Method		:	Cardit.populateCarditDOMReceptacleDetails Added by 	:	A-8061 on 07-Aug-2020 Used for 	: Parameters	:	@param carditReceptacleVO Parameters	:	@throws SystemException  Return type	: 	void
	 */
	private void populateCarditDOMReceptacleDetails(CarditReceptacleVO carditReceptacleVO) {
		CarditPK carditPK = constructCarditPK();
		if (carditReceptacleVO != null) {
			if (receptacleInformation == null) {
				receptacleInformation = new HashSet<CarditReceptacle>();
			}
			try {
				CarditReceptacle carditReceptacle = CarditReceptacle
						.find(new CarditReceptacle().createCarditReceptaclePK(carditPK, carditReceptacleVO));
				carditReceptacle.setCarditType(carditReceptacleVO.getCarditType());
			} catch (FinderException e) {
				log.error("" + "exception raised" + " " + e);
				receptacleInformation.add(new CarditReceptacle(carditPK, carditReceptacleVO));
			}
		}
	}

	/**
	 * @author A-3227
	 * @param carditContainerVO
	 * @throws SystemException
	 */
	private void populateContainerDetails(CarditContainerVO carditContainerVO) {
		CarditPK carditPK = constructCarditPK();
		if (carditContainerVO != null) {
			if (containerInformation == null) {
				containerInformation = new HashSet<CarditContainer>();
			}
			containerInformation.add(new CarditContainer(carditPK, carditContainerVO));
		}
	}

	/**
	 * @author A-3227
	 * @param carditTotalVO
	 * @throws SystemException
	 */
	private void populateTotalDetails(CarditTotalVO carditTotalVO) {
		CarditPK carditPK = constructCarditPK();
		if (carditTotalVO != null) {
			if (totalsInformation == null) {
				totalsInformation = new HashSet<CarditTotal>();
			}
			totalsInformation.add(new CarditTotal(carditPK, carditTotalVO));
		}
	}

	/**
	 * @author A-3227
	 * @param carditHandoverInfoVO
	 * @throws SystemException
	 */
	private void populateHandoverDetails(CarditHandoverInformationVO carditHandoverInfoVO) {
		CarditPK carditPK = constructCarditPK();
		CarditHandoverPK carditHndovrPK = new CarditHandoverPK();
		carditHndovrPK.setCarditKey(carditPK.getCarditKey());
		carditHndovrPK.setCompanyCode(carditPK.getCompanyCode());
		if (carditHandoverInfoVO != null) {
			if (carditHandoverInformation == null) {
				carditHandoverInformation = new HashSet<CarditHandover>();
			}
			carditHandoverInformation.add(new CarditHandover(carditHndovrPK, carditHandoverInfoVO));
		}
	}

	/**
	 * @author A-3227
	 * @param carditReferenceInfoVO
	 * @throws SystemException
	 */
	private void populateReferenceDetails(CarditReferenceInformationVO carditReferenceInfoVO) {
		CarditPK carditPK = constructCarditPK();
		CarditReferencePK carditReferPK = new CarditReferencePK();
		carditReferPK.setCarditKey(carditPK.getCarditKey());
		carditReferPK.setCompanyCode(carditPK.getCompanyCode());
		if (carditReferenceInfoVO != null) {
			if (carditReferenceInformation == null) {
				carditReferenceInformation = new HashSet<CarditReference>();
			}
			carditReferenceInformation.add(new CarditReference(carditReferPK, carditReferenceInfoVO));
		}
	}

	private CarditPK constructCarditPK(){
		CarditPK carditPK = new CarditPK();
		carditPK.setCarditKey(this.getCarditKey());
		carditPK.setCompanyCode(this.getCompanyCode());
		return carditPK;
	}
	/**
	 * TODO Purpose Apr 11, 2007, a-1739
	 * @param carditVO
	 * @throws SystemException
	 */
	public void update(CarditVO carditVO) {
		log.debug("Cardit" + " : " + "update" + " Entering");
		populateChildren(carditVO);
		log.debug("Cardit" + " : " + "update" + " Exiting");
	}

	/**
	 * @author A-8061
	 * @throws SystemException
	 */
	public void remove() {
		removeChildCarditTransportation();
		removeReceptacleDetailsFromCardit(this.receptacleInformation);
		removeChildCarditTotal();
		removeChildCarditHandover();
		removeChildCarditReference();
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/**
	 * @author a-1936This method is used to chekc wether that Cardit Container is already present
	 * @param containerNumber
	 * @return
	 * @throws SystemException
	 */
	private CarditContainer findCarditContainer(String containerNumber) {
		log.debug("Cardit" + " : " + "findCarditContainer" + " Entering");

		CarditContainer carditContainer = null;
		try {
			carditContainer = CarditContainer.find(this.getCompanyCode(), this.getCarditKey(), containerNumber);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION CAUGHT SO CAN CREATE CARDIT CONTAINER");
		}
		return carditContainer;
	}

	/**
	 * @author A-3227
	 * @return
	 * @throws SystemException
	 */
	private String findCarditReceptacle(String mailbagId) {
		log.debug("Cardit" + " : " + "findCarditTotal" + " Entering");
		CarditReceptaclePK carditRcpPK = new CarditReceptaclePK();
		carditRcpPK.setCarditKey(this.getCarditKey());
		carditRcpPK.setCompanyCode(this.getCompanyCode());
		carditRcpPK.setReceptacleId(mailbagId);
		CarditReceptacle carditRcp = null;
		String carditKey = "";
		String oldCarditKey = "";
		int found = 0;
		try {
			carditRcp = CarditReceptacle.find(carditRcpPK);
			found = 1;
			log.info("\n\nMAILBAG IS ALREADY ASSOCIATED WITH THE CARDIT BEING PROCESSED....!!!!\n\n\n");
		} catch (FinderException ex) {
			log.info(
					"\n\nMAILBAG IS ALREADY NOT ASSOCIATED WITH THE CARDIT BEING PROCESSED.... SO CHECKING FOR DUPLICATE IN OLD CARDITS!!!!!\n\n");
			CarditReceptacleVO carditreceptacleVO = new CarditReceptacleVO();
			carditreceptacleVO = CarditReceptacle.findDuplicateMailbagsInCardit(this.getCompanyCode(), mailbagId);
			if (carditreceptacleVO != null) {
				oldCarditKey = carditreceptacleVO.getCarditKey();
				if (oldCarditKey != null && oldCarditKey.trim().length() > 0) {
					if (!(carditreceptacleVO.getCarditType() != null
							&& MailConstantsVO.CDT_TYP_CANCEL.equals(carditreceptacleVO.getCarditType()))) {
						found = 1;
					}
					log.info("\n\n\n\n\t\t MAILBAG IS ALREADY ASSOCIATED WITH THE OLD CARDIT ::" + oldCarditKey);
					log.info("\n\n\n\n\t\t SKIPING DUPLICATE MAILBAG FROM PROCESSING........." + oldCarditKey);
				}
			}
		}
		StringBuilder carditKeys = new StringBuilder().append(oldCarditKey).append("~").append(found);
		carditKey = carditKeys.toString();
		return carditKey;
	}

	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) {
		return CarditReceptacle.findCarditMails(carditEnquiryFilterVO, pageNumber);
	}
	/**
	 * @param carditVO
	 * @throws SystemException
	 * @author A-2572
	 */
	public void updateReceptacleDetailsForUpdation(CarditVO carditVO) {
		Collection<CarditTransportationVO> transportInformationVOs = carditVO.getTransportInformation();
		if (transportInformationVOs != null && transportInformationVOs.size() > 0) {
			removeChildCarditTransportation();
			for (CarditTransportationVO transportInformationVO : transportInformationVOs) {
				log.debug("Cardit Transportation  Not Found  So Persisting");
				if (!(transportInformationVO.getFlightNumber() == null
						&& MODEOFTRANSPORT_ROAD.equalsIgnoreCase(transportInformationVO.getModeOfTransport()))) {
					populateTransportation(transportInformationVO);
				}
			}
		}
		Collection<CarditReceptacleVO> receptacleInformationVOs = carditVO.getReceptacleInformation();
		Collection<CarditReceptacleVO> receptacleInformationToRemoveVOs = new ArrayList<CarditReceptacleVO>();
		if (receptacleInformationVOs != null && receptacleInformationVOs.size() > 0) {
			if (!MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())
					&& !"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())) {
				removeReceptacleDetailsFromCardit(this.getReceptacleInformation());
			}
			if (MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())
					&& this.getReceptacleInformation() != null && !this.getReceptacleInformation().isEmpty()
					&& carditVO.getReceptacleInformation() != null && !carditVO.getReceptacleInformation().isEmpty()
					&& !"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())) {
				Map<String, String> mailInCarditMap = carditVO.getReceptacleInformation().stream().collect(
						Collectors.toMap(CarditReceptacleVO::getReceptacleId, CarditReceptacleVO::getCarditType));
				this.getReceptacleInformation().stream().forEach(rcpInformation -> {
					if (!mailInCarditMap.containsKey(rcpInformation.getReceptacleId())) {
						rcpInformation.setCarditType(MailConstantsVO.CDT_TYP_CANCEL);
					}
				});
			}
			for (CarditReceptacleVO carditReceptacleVO : receptacleInformationVOs) {
				if (!"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())) {
					if (!MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())) {
						populateReceptacleDetails(carditReceptacleVO);
					} else {
						int found = 0;
						found = findCarditReceptacleForUpdation(carditReceptacleVO);
						if (found == 0) {
							log.debug("Cardit Receptacle Not Found  So Persisting ");
							log.info("" + "Persisting cardit Receptacle------" + " "
									+ carditReceptacleVO.getReceptacleId());
							populateReceptacleDetails(carditReceptacleVO);
						} else {
							receptacleInformationToRemoveVOs.add(carditReceptacleVO);
						}
					}
				} else {
					populateCarditDOMReceptacleDetails(carditReceptacleVO);
				}
			}
		}
		log.debug("\n Cardit VO after duplicate validation......\n");
		Collection<CarditContainerVO> containerInformationVOs = carditVO.getContainerInformation();
		if (containerInformationVOs != null && containerInformationVOs.size() > 0) {
			for (CarditContainerVO carditContainerVO : containerInformationVOs) {
				log.debug("" + "the Cardit Container Vo" + " " + carditContainerVO);
				CarditContainer carditCont = findCarditContainer(carditContainerVO.getContainerNumber());
				if ((carditCont == null) && (carditContainerVO.getContainerNumber() != null)
						&& (carditContainerVO.getContainerNumber().trim().length() > 0)) {
					log.debug("Cardit Container Not Found  So Persisting");
					populateContainerDetails(carditContainerVO);
				}
			}
		}
		Collection<CarditTotalVO> totalsInformationVOs = carditVO.getTotalsInformation();
		if (totalsInformationVOs != null && totalsInformationVOs.size() > 0) {
			removeChildCarditTotal();
			for (CarditTotalVO carditTotalVO : totalsInformationVOs) {
				log.debug("Cardit Total Persisting");
				populateTotalDetails(carditTotalVO);
			}
		}
		Collection<CarditHandoverInformationVO> carditHandoverInformationVOs = carditVO.getHandoverInformation();
		if (carditHandoverInformationVOs != null && carditHandoverInformationVOs.size() > 0) {
			removeChildCarditHandover();
			for (CarditHandoverInformationVO carditHandoverInfoVO : carditHandoverInformationVOs) {
				log.debug("Cardit Handover Persisting");
				populateHandoverDetails(carditHandoverInfoVO);
			}
		}
		Collection<CarditReferenceInformationVO> carditReferenceInformationVOs = carditVO.getReferenceInformation();
		if (carditReferenceInformationVOs != null && carditReferenceInformationVOs.size() > 0) {
			removeChildCarditReference();
			for (CarditReferenceInformationVO carditReferenceInfoVO : carditReferenceInformationVOs) {
				log.debug("Cardit Reference Persisting");
				populateReferenceDetails(carditReferenceInfoVO);
			}
		}
	}

	/**
	 * @author A-2572
	 * @return
	 * @throws SystemException
	 */
	private int findCarditReceptacleForUpdation(CarditReceptacleVO carditReceptacleVO) {
		log.debug("Cardit" + " : " + "findCarditTotal" + " Entering");
		CarditReceptaclePK carditRcpPK = new CarditReceptaclePK();
		carditRcpPK.setCarditKey(this.getCarditKey());
		carditRcpPK.setCompanyCode(this.getCompanyCode());
		carditRcpPK.setReceptacleId(carditReceptacleVO.getReceptacleId());
		CarditReceptacle carditRcp = null;
		int found = 0;
		try {
			carditRcp = CarditReceptacle.find(carditRcpPK);
			carditRcp.setCarditType(carditReceptacleVO.getCarditType());
			found = 1;
			log.info("\n\nMAILBAG IS ALREADY ASSOCIATED WITH THE CARDIT BEING PROCESSED....!!!!\n\n\n");
		} catch (FinderException ex) {
			log.debug("FinderException");
		}
		return found;
	}

	/**
	 * <pre> This is to remove the remove Receptacle Details From Cardit </pre>
	 * @author A-3429
	 * @param carditReceptaclesTobeRemoved
	 * @throws SystemException
	 */
	private void removeReceptacleDetailsFromCardit(Collection<CarditReceptacle> carditReceptaclesTobeRemoved) {
		Collection<CarditReceptacle> carditReceptacleDetails = getReceptacleInformation();
		if (carditReceptacleDetails != null && carditReceptacleDetails.size() > 0
				&& carditReceptaclesTobeRemoved != null && carditReceptaclesTobeRemoved.size() > 0) {
			log.debug("Cardit Receptacle Child Removing");
			CarditReceptacle carditReceptacle = null;
			while (carditReceptaclesTobeRemoved.iterator().hasNext()) {
				carditReceptacle = carditReceptaclesTobeRemoved.iterator().next();
				carditReceptacle.remove();
				carditReceptaclesTobeRemoved.remove(carditReceptacle);
			}
		}
	}

	/**
	 * @param carditVO
	 * @throws SystemException
	 * @author A-2572
	 */
	public void updateReceptacleDetailsForConfirmation(CarditVO carditVO) {
		log.debug("Cardit" + " : " + "updateReceptacleDetailsForConfirmation" + " Entering");
		Collection<CarditReceptacleVO> carditReceptacleVOsTobeRemoved = updateReceptacleForHistory(carditVO);
		removeReceptacleDetailsFromCardit(getReceptacleInformation());
		for (CarditReceptacleVO receptacleVO : carditVO.getReceptacleInformation()) {
			populateReceptacleDetails(receptacleVO);
		}
		if (carditReceptacleVOsTobeRemoved != null && carditReceptacleVOsTobeRemoved.size() > 0) {
			carditVO.getReceptacleInformation().addAll(carditReceptacleVOsTobeRemoved);
		}
		log.debug("Cardit" + " : " + "updateReceptacleDetailsForConfirmation" + " Exiting");
	}

	/**
	 * @param carditVO
	 * @return
	 * @throws SystemException
	 * @author A-2572
	 */
	public Collection<CarditReceptacleVO> updateReceptacleForHistory(CarditVO carditVO) {
		log.debug("Cardit" + " : " + "updateReceptacleDetailsForConfirmation" + " Entering");
		Collection<CarditReceptacleVO> carditReceptacleVOsRemoved = new ArrayList<CarditReceptacleVO>();
		CarditReceptacleVO carditReceptacleVOForUpd = null;
		for (CarditReceptacle carditReceptacle : getReceptacleInformation()) {
			carditReceptacleVOForUpd = getReceptacleDetailsForHisUpdation(carditReceptacle,
					carditVO.getReceptacleInformation());
			if (carditReceptacleVOForUpd == null) {
				carditReceptacleVOsRemoved.add(constructVoFromEntity(carditReceptacle));
			}
		}
		log.debug("Cardit" + " : " + "updateReceptacleDetailsForConfirmation" + " Exiting");
		return carditReceptacleVOsRemoved;
	}

	/**
	 * @author A-3429getReceptacleDetailsFromCardit This method will find the Exact Recptacle from the corresponding Consigment and return the same back.
	 */
	private CarditReceptacleVO getReceptacleDetailsForHisUpdation(CarditReceptacle carditReceptacle,
																  Collection<CarditReceptacleVO> receptacleInformations) {
		for (CarditReceptacleVO carditReceptacleVO : receptacleInformations) {
			if (carditReceptacle.getReceptacleId()
					.equals(carditReceptacleVO.getReceptacleId())) {
				return carditReceptacleVO;
			}
		}
		return null;
	}

	/**
	 * @author A-3429getReceptacleDetailsFromCardit This method will find the Exact Recptacle from the corresponding Consigment and return the same back.
	 */
	private CarditReceptacleVO constructVoFromEntity(CarditReceptacle carditReceptacle) {
		CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
		carditReceptacleVO.setReceptacleId(carditReceptacle.getReceptacleId());
		carditReceptacleVO.setReceptacleStatus(MailConstantsVO.CARDIT_STATUS_CONFIRMATION_DELETED);
		return carditReceptacleVO;
	}

	public static Cardit find(CarditPK carditPK)throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(Cardit.class, carditPK);
	}

	/**
	 * @param mailbag
	 * @return
	 * @throws SystemException
	 * @throws DuplicateMailBagsException
	 */
	private boolean checkForDuplicateMailbag(String companyCode, String paCode, Mailbag mailbag)
			throws DuplicateMailBagsException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		PostalAdministrationVO postalAdministrationVO = ContextUtil.getInstance().getBean(MailController.class).findPACode(companyCode, paCode);
		ZonedDateTime currentDate = localDateUtil.getLocalDate(null, true);
		ZonedDateTime dspDate = localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), null);
		//TODO: Code to verify
		long seconds = currentDate.toEpochSecond()-(dspDate.toEpochSecond());
		long days = seconds / 86400000;
		if ((days) <= postalAdministrationVO.getDupMailbagPeriod()) {
			return false;
		}
		return true;
	}
	public static Collection<CarditTransportationVO> findCarditTransportationDetails(
			String companyCode, String carditKey) throws SystemException {
		try {
			return constructDAO().findCarditTransportationDetails(
					companyCode, carditKey);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}


}
