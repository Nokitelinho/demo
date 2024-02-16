package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.InvalidMailTagFormatException;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DESTN_FLT;

/** 
 * @author A-5991Entity storing mailbag details. 
 */
@Setter
@Getter
@Slf4j
@Entity
@Table(name = "MALMST_VW_BASE")
@IdClass(MailbagPK.class)
@SequenceGenerator(name = "MALMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALMST_SEQ")
@Auditable(eventName="maildetailsUpdate",isParent = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Mailbag  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String ASSIGNED_FROM_DEV_PANEL = "Asg. from Dev.Panel";
	private static final String ATTACHED_TO_AWB = " was attached with AWB ";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	private static final String MLDMSGSENDINGREQRD = "mail.operations.mldsendingrequired";

	private static final String COUNTRY_CACHE = "COUNTRY";
	private static final String CITY_CACHE = "CITY";
	private static final String EXCHANGE_CACHE = "EXCHANGE";
	private static final String ERROR_CACHE = "ERROR";
	private static final String SUBCLS_CACHE = "MailSubClass";
	private static final String CITYPAIR_CACHE = "CITYPAIRDESTINATION";
	private static final String ORIGINEXCHANGE_ERR = "OOE";
	private static final String DESTINATIONEXCHANGE_ERR = "DOE";
	private static final String MTK_INB_ONLINEFLT_CLOSURE = "MTK_INB_ONLINEFLT_CLOSURE";
	private static final String ORIGIN_COUNTRY_ERR = "COUNTRYORIGIN";
	private static final String DESTN_COUNTRY_ERR = "COUNTRYDESTINATION";
	private static final String ORIGIN_CITY_ERR = "CITYORIGIN";
	private static final String DESTN_CITY_ERR = "CITYDESTINATION";
	private static final String ORIGIN_PAIR_ERR = "CITYPAIRORIGIN";
	private static final String DESTN_PAIR_ERR = "CITYPAIRDESTINATION";
	private static final String ORIGIN_EXG_PA_ERR = "ORGPANOTPRESENT";
	private static final String MTK_IMP_FLT = "MTK_IMP_FLT";

	@Transient
	private StringBuilder errorString = new StringBuilder();


	@Column(name = "INTFLG")
	private String intFlg;
	@Id
	@Transient
	private String companyCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALMSTSEQ")
	@Column(name = "MALSEQNUM")
	private long mailSequenceNumber;
	@Column(name = "RSN")
	private String receptacleSerialNumber;
	@Column(name = "REGIND")
	private String registeredOrInsuredIndicator;
	@Column(name = "HSN")
	private String highestNumberedReceptacle;
	@Column(name = "WGT")
	@Audit(name="weight",changeGroupId="mailbag-general")
	private double weight;
	@Column(name = "DSPDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime despatchDate;
	@Column(name = "SCNUSR")
	private String scannedUser;
	@Audit(name="flightCarrierIdr",changeGroupId="mailbag-general")
	@Column(name = "FLTCARIDR")
	private int carrierId;
	@Audit(name="flightNum",changeGroupId="mailbag-general")
	@Column(name = "FLTNUM")
	private String flightNumber;
	@Audit(name="flightSequenceNumber",changeGroupId="mailbag-general")
	@Column(name = "FLTSEQNUM")
	private long flightSequenceNumber;
	@Audit(name="segmentSerialNumber",changeGroupId="mailbag-general")
	@Column(name = "SEGSERNUM")
	private int segmentSerialNumber;
	@Audit(name="containerNumber",changeGroupId="mailbag-general")
	@Column(name = "CONNUM")
	private String uldNumber;
	@Column(name = "POU")
	private String pou;

	@Column(name = "SCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime scannedDate;

	@Column(name = "SCNPRT")
	private String scannedPort;
	@Audit(name="mailStatus",changeGroupId="mailbag-general")
	@Column(name = "MALSTA")
	private String latestStatus;
	@Column(name = "ORGEXGOFC")
	private String orginOfficeOfExchange;
	@Column(name = "DSTEXGOFC")
	private String destinationOfficeOfExchange;
	@Column(name = "MALCTG")
	private String mailCategory;
	@Column(name = "MALSUBCLS")
	private String mailSubClass;
	@Column(name = "YER")
	private int year;
	@Column(name = "DSN")
	private String despatchSerialNumber;
	@Audit(name="operationalStatus",changeGroupId="mailbag-general")
	@Column(name = "OPRSTA")
	private String operationalStatus;
	@Column(name = "CONTYP")
	private String containerType;
	@Column(name = "CSGDOCNUM")
	private String consignmentNumber;
	@Column(name = "PRGCLSDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime purgingCloseDate;
	@Column(name = "POACOD")
	private String paCode;
	@Column(name = "VOL")
	private double volume;
	@Column(name = "CSGSEQNUM")
	private int consignmentSequenceNumber;
	@Column(name = "DMGFLG")
	private String damageFlag;
	@Column(name = "MALCLS")
	private String mailClass;
	@Column(name = "MALCMPCOD")
	private String mailCompanyCode;
	@Column(name = "MALIDR")
	private String mailIdr;
	@Column(name = "DSNIDR")
	private String dsnIdr;
	@Column(name = "MALTYP")
	private String mailType;

	@Column(name = "REQDLVTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime reqDeliveryTime;
	@Column(name = "MALRMK")
	private String mailRemarks;
	@Column(name = "DOCOWRIDR")
	private int documentOwnerId;
	@Column(name = "MSTDOCNUM")
	@Audit(name="masterDocumentNumber",changeGroupId="mailbag-awb")
	private String masterDocumentNumber;
	@Column(name = "DUPNUM")
	private int dupliacteNumber;
	@Column(name = "SEQNUM")
	private int sequenceNumber;
	@Column(name = "SHPPFX")
	private String shipmentPrefix;
	@Column(name = "DSPWGTUNT")
	private String displayUnit;
	@Column(name = "DSPWGT")
	private double displayValue;
	@Column(name = "MALSRVLVL")
	private String mailServiceLevel;
	@Column(name = "SELNUM")
	private String selnum;
	@Column(name = "ONNTIMDLVFLG")
	private String onTimeDelivery;
	@Column(name = "ORGCOD")
	private String origin;
	@Column(name = "DSTCOD")
	private String destination;
	@Column(name = "METTRPSRVFLG")
	private String metTransWindow;
	@Column(name = "TRPSRVENDTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime transWindowEndTime;
	@Column(name = "MALSRC")
	private String mailbagSource;
	@Column(name = "SCNWVDFLG")
	private String scanWavedFlag;
	@Column(name = "ACTWGT")
	@Audit(name="actualWeight",changeGroupId="mailbag-general")
	private double actualWeight;
	@Column(name = "VOLUNT")
	private String displayVolUnit;
	@Column(name = "RFDFLG")
	private String rfdFlag;
	@Column(name = "ACTWGTDSPUNT")
	private String actualWeightDisplayUnit;
	@Column(name = "ACTWGTDSP")
	private double actualWeightDisplayValue;
	@Column(name = "CTRNUM")
	private String contractIDNumber;
	@Column(name = "POAFLG")
	private String paBuiltFlag;
	@Column(name = "LATACTTIM")
	//@Temporal(TemporalType.TIMESTAMP)

	private LocalDateTime latestAcceptanceTime;
	@Column(name = "ACPARPCOD")

	private String acceptanceAirportCode;
	@Column(name = "ACPSCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)

	private LocalDateTime acceptanceScanDate;
	@Column(name = "ACPPOACONNUM")

	private String acceptancePostalContainerNumber;
	@Column(name = "FSTSCNDAT")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime firstScanDate;
	@Column(name = "FSTSCNARP")
	private String firstScanPort;
	@Column(name = "SECSTACOD")
	@Audit(name="securityStatusCode",changeGroupId="mailbag-general")
	private String securityStatusCode;

	@OneToMany
	@JoinColumns({ @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable = false, updatable = false) })
	private Set<DamagedMailbag> damagedMailbags;

	/** 
	* @author A-5991This method id used to find the instance of the Entity
	* @param mailbagPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static Mailbag find(MailbagPK mailbagPK) throws FinderException {
		return PersistenceController.getEntityManager().find(Mailbag.class, mailbagPK);
	}

public static Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO mailStatusFilterVO)
			throws SystemException{
		try{
			return constructDAO().generateMailStatusReport(mailStatusFilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());

		}
	}
public static Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailFilterVO) throws SystemException {
		try{
			return constructDAO().findDamageMailReport(damageMailFilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/** 
	* @author A-5991
	* @throws SystemException
	*/
	public void remove() {
		if (this.getDamagedMailbags() != null) {
			for (DamagedMailbag damaged : this.getDamagedMailbags()) {
				damaged.remove();
			}
		}
		Collection<MailbagHistoryVO> mailbagHistoryVOs = findMailbagHistories(this.getCompanyCode(), "",
				this.getMailSequenceNumber());
		if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
			for (MailbagHistoryVO mailbagHistory : mailbagHistoryVOs) {
				MailbagHistoryPK mailbaghistorypk = MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
				MailbagHistory mailhistory = null;
				try {
					mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
					mailhistory.remove();
				} catch (FinderException e) {
					log.error("Finder Exception Caught");
				}
			}
		}
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	public Mailbag() {
	}

	public Mailbag(MailbagVO mailbagVO) {
		populateMailbag(mailbagVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			log.error("Mailbag - SystemException" + createException);
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
		populateChildren(mailbagVO);
	}

	/** 
	* @author A-9619 for resolving sonarlint issue Constructors should only call non-overridable methods (squid:S1699)
	* @param mailbagVO
	* @throws SystemException
	*/
	private void populateMailbag(MailbagVO mailbagVO) {
		populatePk(mailbagVO);
		populateAttributes(mailbagVO);
	}

	public Mailbag persistMailbag(MailbagVO mailbagVO) throws SystemException {
		populatePk(mailbagVO);
		populateAttributes(mailbagVO);/*A-9619 as part of IASCB-55196*/
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch(CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		populateChildren(mailbagVO);
		return this;
	}

	public void updateULDTransferDetails(OperationalFlightVO operationalFlightVO, int toFlightSegSerNum) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("Mailbag" + " : " + "updateULDTransferDetails" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String currScannedPort = getScannedPort();
		setScannedPort(operationalFlightVO.getPol());
		setScannedUser(operationalFlightVO.getOperator());
		if (operationalFlightVO.getOperationTime() != null) {
			setScannedDate(localDateUtil.getLocalDateTime(operationalFlightVO.getOperationTime().toLocalDateTime(),
					getScannedPort()));
		} else {
			setScannedDate(localDateUtil.getLocalDate(getScannedPort(), true));
		}
		boolean[] eventStats = checkIfHistoryExists(null, MailConstantsVO.MAIL_STATUS_ARRIVED,
				MailConstantsVO.MAIL_STATUS_DELIVERED);
		boolean isArrived = eventStats[0];
		boolean isDelivered = eventStats[1];
		if (!isArrived && !isDelivered) {
		}
		if (!isDelivered) {
			if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(getLatestStatus())
					&& operationalFlightVO.getPol().equals(currScannedPort)) {
				setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				setScannedUser(this.getLastUpdatedUser());
				setMailbagSource(getLatestStatus());
			} else {
				if (operationalFlightVO.isTransferStatus()) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				} else {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				}
				String triggeringPoint = contextUtil.getTxContext(ContextUtil.TRIGGER_POINT);
				if ("MTK064".equals(triggeringPoint)) {
					setMailbagSource(triggeringPoint);
				} else {
					setMailbagSource(getLatestStatus());
				}
				MailbagVO newMailbagVO = new MailbagVO();
				newMailbagVO.setCompanyCode(this.getCompanyCode());
				newMailbagVO.setPaCode(getPaCode());
				if (new MailController().isUSPSMailbag(newMailbagVO)) {
					setOnTimeDelivery(MailConstantsVO.FLAG_NO);
				} else {
					setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
				}
			}
			setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			setCarrierId(operationalFlightVO.getCarrierId());
			setFlightNumber(operationalFlightVO.getFlightNumber());
			setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			setSegmentSerialNumber(toFlightSegSerNum);
			setPou(operationalFlightVO.getPou());
			String routingDetails = null;
			MailbagVO mailbagVO = new MailbagVO();
			if (getConsignmentNumber() != null) {
				mailbagVO.setCompanyCode(this.getCompanyCode());
				mailbagVO.setPaCode(getPaCode());
				mailbagVO.setConsignmentNumber(getConsignmentNumber());
				mailbagVO.setDestination(getDestination());
				routingDetails = constructDAO().findRoutingDetailsForConsignment(mailbagVO);
			}
			if (routingDetails != null && getReqDeliveryTime() != null) {
				//TODO: neo to verify and fix the below code
				setReqDeliveryTime(getReqDeliveryTime());
			} else {
				ZonedDateTime reqDlvTim = null;
					MailbagVO mailbagTransferVO = new MailbagVO();
					mailbagTransferVO.setDestination(getDestination());
					mailbagTransferVO.setPaCode(getPaCode());
					mailbagTransferVO.setCompanyCode(operationalFlightVO.getCompanyCode());
					mailbagTransferVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					mailbagTransferVO.setFlightDate(operationalFlightVO.getFlightDate());
					mailbagTransferVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					reqDlvTim = calculateRDT(mailbagTransferVO);
					if(Objects.nonNull(reqDlvTim)) {
						setReqDeliveryTime(reqDlvTim.toLocalDateTime());
					}
			}
		}
		log.debug("Mailbag" + " : " + "updateULDTransferDetails" + " Entering");
	}

	/** 
	* This method checks whether History entry exists or not. If exists for Arrived/Delivered status updates it's Scan date.
	* @param mailbagVO
	* @return
	* @throws SystemException 
	*/
	private boolean[] checkIfHistoryExists(MailbagVO mailbagVO, String... events) {
		Collection<MailbagHistoryVO> existingMailbagHistories = findMailbagHistories(this.getCompanyCode(),
				"", this.getMailSequenceNumber());
		boolean[] evtStats = new boolean[events.length];
		int idx = 0;
		String airport = getScannedPort();
		if (mailbagVO != null) {
			airport = mailbagVO.getScannedPort();
		}
		if (existingMailbagHistories != null && existingMailbagHistories.size() > 0) {
			for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
				for (String mailEvent : events) {
					if (mailbagHistory.getScannedPort().equals(airport)
							&& mailbagHistory.getMailStatus().equals(mailEvent)) {
						evtStats[idx++] = true;
						break;
					}
				}
			}
		}
		return evtStats;
	}

	/** 
	* @param mailbagVO
	* @throws SystemException
	*/
	private void populateChildren(MailbagVO mailbagVO) {
		Collection<DamagedMailbagVO> damageVOs = mailbagVO.getDamagedMailbags();
		if (!MailConstantsVO.FLAG_NO.equals(mailbagVO.getAcceptanceFlag())) {
			if (damageVOs != null && damageVOs.size() > 0) {
				populateDamageDetails(damageVOs);
			}
		}
	}

	/** 
	* Method		:	Mailbag.constructDamagedMailbagPK Added by 	:	A-4803 on 21-May-2015 Used for 	:	constructing damage mail bag pk Parameters	:	@param damageMailbagVO Parameters	:	@return Return type	: 	DamagedMailbagPK
	*/
	private DamagedMailbagPK constructDamagedMailbagPK(DamagedMailbagVO damageMailbagVO) {
		DamagedMailbagPK damageMailbagPK = new DamagedMailbagPK();
		damageMailbagPK.setCompanyCode(this.getCompanyCode());
		damageMailbagPK.setMailSequenceNumber(this.getMailSequenceNumber());
		damageMailbagPK.setAirportCode(damageMailbagVO.getAirportCode());
		damageMailbagPK.setDamageCode(damageMailbagVO.getDamageCode());
		return damageMailbagPK;
	}

	/** 
	* A-1739
	* @param damageVOs
	* @throws SystemException
	*/
	private void populateDamageDetails(Collection<DamagedMailbagVO> damageVOs) {
		if (getDamagedMailbags() == null) {
			damagedMailbags = new HashSet<DamagedMailbag>();
		}

		for (DamagedMailbagVO damagedMailbagVO : damageVOs) {
			damagedMailbagVO.setMailClass(getMailClass());
			DamagedMailbagPK damagedMailbagPK = constructDamagedMailbagPK(damagedMailbagVO);
			try {

				DamagedMailbag.find(damagedMailbagPK);
				log.info("Damage details already saved");
			} catch (FinderException exception) {
				damagedMailbags.add(new DamagedMailbag(damagedMailbagPK, damagedMailbagVO));
			}
		}
	}

	/** 
	* A-5991
	* @param mailbagVO
	*/
	private void populatePk(MailbagVO mailbagVO) {

		this.setCompanyCode(mailbagVO.getCompanyCode());
	}

	/** 
	* A-5991
	* @param mailbagVO
	*/
	public void populateAttributes(MailbagVO mailbagVO) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		setCarrierId(mailbagVO.getCarrierId());
		if (mailbagVO.getConsignmentDate() != null) {
			setDespatchDate(mailbagVO.getConsignmentDate());
		}
		setFlightNumber(mailbagVO.getFlightNumber());
		setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		setHighestNumberedReceptacle(mailbagVO.getHighestNumberedReceptacle());
		setLatestStatus(mailbagVO.getLatestStatus());
		setMailCompanyCode(mailbagVO.getMailCompanyCode());
		setPou(mailbagVO.getPou());
		setReceptacleSerialNumber(mailbagVO.getReceptacleSerialNumber());
		setRegisteredOrInsuredIndicator(mailbagVO.getRegisteredOrInsuredIndicator());
		setScannedDate(mailbagVO.getScannedDate());
		setScannedPort(mailbagVO.getScannedPort());
		setScannedUser(mailbagVO.getScannedUser());
		setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		setMailIdr(mailbagVO.getMailbagId());
		setDsnIdr(mailbagVO.getDespatchId());
		setUldNumber(mailbagVO.getUldNumber());
		setContainerType(mailbagVO.getContainerType());
		if(mailbagVO.getWeight().getValue().compareTo(BigDecimal.ZERO)==0){
			setWeight(mailbagVO.getWeight().getDisplayValue().doubleValue());
		} else{
			setWeight(mailbagVO.getWeight().getValue().doubleValue());
		}

		setOperationalStatus(mailbagVO.getOperationalStatus());
		setConsignmentNumber(mailbagVO.getConsignmentNumber());
		setConsignmentSequenceNumber(mailbagVO.getConsignmentSequenceNumber());
		setPaCode(mailbagVO.getPaCode());
		if (mailbagVO.getPaBuiltFlag() != null && mailbagVO.getPaBuiltFlag().trim().length() > 0) {
			setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
		} else {
			setPaBuiltFlag("N");
		}
		if (mailbagVO.getDamageFlag() != null) {
			setDamageFlag(mailbagVO.getDamageFlag());
		} else {
			setDamageFlag(MailbagVO.FLAG_NO);
		}
		setMailClass(mailbagVO.getMailClass());
		setMailCategory(mailbagVO.getMailCategoryCode());
		setMailSubClass(mailbagVO.getMailSubclass());
		setOrginOfficeOfExchange(mailbagVO.getOoe());
		setDestinationOfficeOfExchange(mailbagVO.getDoe());
		setDespatchSerialNumber(mailbagVO.getDespatchSerialNumber());
		setYear(mailbagVO.getYear());
		setMailRemarks(mailbagVO.getMailRemarks());
		if (mailbagVO.isDespatch()) {
			setMailType("D");
		} else {
			setMailType("M");
		}
		if (mailbagVO.getDamageFlag() != null && mailbagVO.getDamageFlag().equals(MailConstantsVO.FLAG_YES)) {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
		}
		if (mailbagVO.getFlightSequenceNumber() > 0
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())) {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		}
		setLastUpdatedUser(mailbagVO.getScannedUser());
		log.debug("" + "VOLUME" + " " + mailbagVO.getVolume());
		try {
			calculateMailbagVolume(mailbagVO);
		} finally {
		}
		if (mailbagVO.getVolume() != null) {
			setVolume(mailbagVO.getVolume().getDisplayValue().doubleValue());
		}
		if (mailbagVO.getReqDeliveryTime() != null) {
			setReqDeliveryTime(mailbagVO.getReqDeliveryTime().toLocalDateTime());
		} else {
			ZonedDateTime reqDlvTim = null;
			reqDlvTim = calculateRDT(mailbagVO);
			if(Objects.nonNull(reqDlvTim)) {
				setReqDeliveryTime(reqDlvTim.toLocalDateTime());
			}
		}
		setDisplayValue(mailbagVO.getWeight().getDisplayValue().doubleValue());
		setDisplayUnit(mailbagVO.getWeight().getDisplayUnit().getName());
		if (mailbagVO.getMailServiceLevel() != null && !mailbagVO.getMailServiceLevel().isEmpty()
				&& getMailServiceLevel() == null) {
			setMailServiceLevel(mailbagVO.getMailServiceLevel());
		} else {
			String serviceLevel = null;
			try {
				serviceLevel = ContextUtil.getInstance().getBean(MailController.class).findMailServiceLevel(mailbagVO);
			} finally {
			}
			if (serviceLevel != null && !serviceLevel.isEmpty()) {
				mailbagVO.setMailServiceLevel(serviceLevel);
				setMailServiceLevel(serviceLevel);
			}
		}
		setSelnum(mailbagVO.getSealNumber());
		setOnTimeDelivery(MailConstantsVO.FLAG_NO);
		setOrigin(mailbagVO.getOrigin());
		setDestination(mailbagVO.getDestination());
		if (mailbagVO.getOnTimeDelivery() != null) {
			setOnTimeDelivery(mailbagVO.getOnTimeDelivery());
		} else {
			setOnTimeDelivery("NA");
		}
		setOrigin(mailbagVO.getOrigin());
		setDestination(mailbagVO.getDestination());
		setMailbagSource(mailbagVO.getMailbagDataSource());
		setScanWavedFlag(mailbagVO.getScanningWavedFlag());
		if (mailbagVO.getVolume() != null) {
			//TODO:Neo to verify this value
			setDisplayVolUnit(mailbagVO.getVolume().getDisplayUnit().getName());
		}
		if (mailbagVO.getRfdFlag() != null) {
			setRfdFlag(mailbagVO.getRfdFlag());
		} else {
			setRfdFlag(MailConstantsVO.FLAG_NO);
		}
		setContractIDNumber(mailbagVO.getContractIDNumber());
		if (mailbagVO.getTransWindowEndTime() == null) {
			String paCode_int = null;
			try {
				paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
			} finally {
			}
			if (mailbagVO.getPaCode() != null) {
				if (mailbagVO.getPaCode().equals(paCode_int)) {
					ZonedDateTime transportServiceWindowEndTime = null;
					try {
						transportServiceWindowEndTime = ContextUtil.getInstance().getBean(MailController.class)
								.calculateTransportServiceWindowEndTime(mailbagVO);
					} finally {
					}
					if (transportServiceWindowEndTime != null) {
						setTransWindowEndTime(transportServiceWindowEndTime.toLocalDateTime());
					}
				}
			}
		} else {
			setTransWindowEndTime(mailbagVO.getTransWindowEndTime().toLocalDateTime());
		}
		setLatestAcceptanceTime(
				mailbagVO.getLatestAcceptanceTime() != null ? mailbagVO.getLatestAcceptanceTime().toLocalDateTime()
						: null);
		setAcceptanceAirportCode(mailbagVO.getAcceptanceAirportCode());
		setAcceptanceScanDate(
				mailbagVO.getAcceptanceScanDate() != null ? mailbagVO.getAcceptanceScanDate().toLocalDateTime() : null);
		setAcceptancePostalContainerNumber(mailbagVO.getAcceptancePostalContainerNumber());
		//TODO: Neo to verify below code
		if (mailbagVO.getWeight().getValue().doubleValue()== 0) {
			try {
				Quantity actualWeight = mailController.calculateActualWeightForZeroWeightMailbags(mailbagVO);
				if (actualWeight != null) {
					setActualWeight(actualWeight.getValue().doubleValue());
					setActualWeightDisplayValue(actualWeight.getDisplayValue().doubleValue());
					setActualWeightDisplayUnit(actualWeight.getDisplayUnit().getName());
				}
			} finally {
			}
		}
		if (!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbagVO.getLatestStatus())) {
			setFirstScanDate(mailbagVO.getScannedDate().toLocalDateTime());
			setFirstScanPort(mailbagVO.getScannedPort());
		}
		setSecurityStatusCode(mailbagVO.getSecurityStatusCode());
	}

	/** 
	* @author A-5991This method returns the Instance of the DAO
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-5991
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public static long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailIdr, companyCode);
	}

	/** 
	* @author A-10504
	* @param mailBagId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public static int findScreeningDetails(String mailBagId, String companyCode) {
		return constructDAO().findScreeningDetails(mailBagId, companyCode);
	}

	/** 
	* @author A-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateAcceptanceFlightDetails(MailbagVO mailbagVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("Mailbag" + " : " + "updateAcceptanceFlightDetails" + " Entering");
		if (mailbagVO.isFromDeviationList()) {
			return;
		}
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (mailbagVO.isMailUpdateFlag()) {
			setScannedDate(mailbagVO.getScannedDate());
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			setPou(mailbagVO.getPou());
			setUldNumber(mailbagVO.getContainerNumber());
			setContainerType(mailbagVO.getContainerType());
			setOperationalStatus(mailbagVO.getOperationalStatus());
			setScannedPort(mailbagVO.getScannedPort());
			if(Objects.nonNull(mailbagVO.getLastUpdateTime())) {
				setLastUpdatedTime(Timestamp.valueOf(mailbagVO.getLastUpdateTime().toLocalDateTime()));
			}
			setLastUpdatedUser(logonAttributes.getUserId());
			setScannedDate(mailbagVO.getScannedDate());
			calculateMailbagVolume(mailbagVO);
			if (mailbagVO.getVolume() != null) {
				setVolume(mailbagVO.getVolume().getDisplayValue().doubleValue());
			}
			setMailRemarks(mailbagVO.getMailRemarks());
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
				setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
			}
			setPaCode(mailbagVO.getPaCode());
			if (mailbagVO.getMailbagSource() != null) {
				setMailbagSource(mailbagVO.getMailSource());
			}
			if (getFirstScanDate() == null && getFirstScanPort() == null) {
				setFirstScanDate(mailbagVO.getScannedDate().toLocalDateTime());
				setFirstScanPort(mailbagVO.getScannedPort());
			}
			if (mailbagVO.getMailCompanyCode() != null && !mailbagVO.getMailCompanyCode().equals(getMailCompanyCode())
					&& mailbagVO.getMailCompanyCode().trim().length() > 0) {
				//TODO: Neo to implement audit

				setMailCompanyCode(mailbagVO.getMailCompanyCode());

				StringBuffer additionalInfo = new StringBuffer();
				additionalInfo.append("Company code ").append("updated for mailbag ").append(mailbagVO.getMailbagId());
				ContextUtil.getInstance().getBean(MailController.class).auditMailDetailUpdates(mailbagVO,"UPDATEMAL","Update",additionalInfo.toString());
			} else {
				setMailCompanyCode(mailbagVO.getMailCompanyCode());
			}
			if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
				Collection<MailbagHistoryVO> mailbagHistoryVOs = mailbagVO.getMailbagHistories();
				if (mailbagVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
						&& "CARDIT".equals(mailbagVO.getFromPanel())) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				} else if (getLatestStatus() != null) {
					if (getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
						mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
						setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
					} else if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(getLatestStatus())
							|| getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED)
							|| getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
						mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					} else if (MailConstantsVO.MAIL_STATUS_NEW.equals(getLatestStatus())
							|| MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(getLatestStatus())
							|| MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(getLatestStatus())) {
						if (mailbagVO.getFlightSequenceNumber() != DESTN_FLT
								&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())) {
							setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						} else {
							setLatestStatus(mailbagVO.getLatestStatus());
						}
					}
				} else {
					setLatestStatus(mailbagVO.getLatestStatus());
				}
				setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				} else if (MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
				MailbagHistory mailbagHistory = findMailbagHistoryForEvent(mailbagVO);
				if (mailbagHistory != null) {
					mailbagHistory.setScanDate(mailbagVO.getScannedDate().toLocalDateTime());
				}
				updateAxpResditEventTimes(mailbagVO);
			}
		}
		log.debug("Mailbag" + " : " + "updateAcceptanceFlightDetails" + " Exiting");
	}

	/** 
	* May 23, 2007, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException 
	*/
	public MailbagHistory findMailbagHistoryForEvent(MailbagVO mailbagVO) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = findMailbagHistories(mailbagVO.getCompanyCode(),
				mailbagVO.getMailbagId(), mailbagVO.getMailSequenceNumber());
		if (mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0) {
			for (MailbagHistoryVO mailbagHistory : mailbagHistoryVOs) {
				if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailbagHistory.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistory.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagHistory.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagHistory.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagHistory.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagHistory.getMailStatus())) {
					if (mailbagHistory.getFlightNumber() != null && mailbagHistory.getContainerNumber() != null) {
						if (mailbagHistory.getScannedPort().equals(mailbagVO.getScannedPort())
								&& mailbagHistory.getCarrierId() == mailbagVO.getCarrierId()
								&& mailbagHistory.getFlightNumber().equals(mailbagVO.getFlightNumber())
								&& mailbagHistory.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()
								&& mailbagHistory.getSegmentSerialNumber() == mailbagVO.getSegmentSerialNumber()
								&& mailbagHistory.getContainerNumber().equals(mailbagVO.getContainerNumber())) {
							MailbagHistoryPK mailbaghistorypk = MailbagHistory
									.constructMailbagHistoryPK(mailbagHistory);
							MailbagHistory mailhistory = null;
							try {
								mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
							} catch (FinderException e) {
								log.error("Finder Exception Caught");
							}
							return mailhistory;
						}
					}
				}
			}
		}
		return null;
	}

	/** 
	* May 23, 2007, a-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateAxpResditEventTimes(MailbagVO mailbagVO) {
		Collection<MailbagVO> mail = new ArrayList<MailbagVO>();
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			ContextUtil.getInstance().getBean(ResditController.class).updateResditEventTimes(mailbagVO, MailConstantsVO.TXN_ACP);
		}
	}

	private static String findSystemParameterValue(String syspar) {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return sysparValue;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateAcceptanceDamage(MailbagVO mailbagVO) {
		log.debug("Mailbag" + " : " + "updateAcceptanceDamage" + " Entering");
		if (!MailbagVO.FLAG_YES.equals(this.getDamageFlag())) {
			if (mailbagVO.getDamageFlag() != null) {
				setDamageFlag(mailbagVO.getDamageFlag());
			}
			if (mailbagVO.getDamageFlag() != null && (MailConstantsVO.FLAG_YES).equals(mailbagVO.getDamageFlag())) {
				setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
			}
		}
		setScannedUser(mailbagVO.getScannedUser());
		Collection<DamagedMailbagVO> damageMails = mailbagVO.getDamagedMailbags();
		if (damageMails != null && damageMails.size() > 0) {
			updateMailbagDamage(mailbagVO);
		}
		log.debug("Mailbag" + " : " + "updateAcceptanceDamage" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateMailbagDamage(MailbagVO mailbagVO) {
		Collection<DamagedMailbagVO> damagedMailbagVOs = mailbagVO.getDamagedMailbags();
		if (getDamagedMailbags() == null) {
			damagedMailbags = new HashSet<DamagedMailbag>();
		}
		for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
			log.info("" + "DAMAGE MAIL-updateMailbagDamage" + " " + damagedMailbagVO);
			DamagedMailbag dmgMail = findDamagedMailbagInSet(damagedMailbagVO);
			damagedMailbagVO.setMailClass(getMailClass());
			DamagedMailbagPK damagedMailbagPk = constructDamagedMailbagPK(damagedMailbagVO);
			if (dmgMail == null) {
				setDamageFlag(MailConstantsVO.FLAG_YES);
				setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
				setScannedUser(mailbagVO.getScannedUser());
				damagedMailbags.add(new DamagedMailbag(damagedMailbagPk, damagedMailbagVO));
			} else {
				dmgMail.update(damagedMailbagVO);
			}
		}
	}

	/** 
	* A-1739
	* @param damagedMailbagVO
	* @return
	*/
	public DamagedMailbag findDamagedMailbagInSet(DamagedMailbagVO damagedMailbagVO) {
		for (DamagedMailbag damagedMailbag : damagedMailbags) {
			if (damagedMailbag.getAirportCode().equals(damagedMailbagVO.getAirportCode())
					&& damagedMailbag.getDamageCode().equals(damagedMailbagVO.getDamageCode())) {
				return damagedMailbag;
			}
		}
		return null;
	}

	/** 
	* @author A-5991
	* @param mailBagId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public static long findMailSequenceNumber(String mailBagId, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailBagId, companyCode);
	}

	/** 
	* @author A-2553
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public static MailbagVO findExistingMailbags(String companyCode, long mailbagId) {
		try {
			return constructDAO().findExistingMailbags(companyCode, mailbagId);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* This method is used to validate the MailTagFormat
	* @param mailbagVos
	* @param isUploadHHT will not throw exp if true
	* @return
	* @throws SystemException
	* @throws InvalidMailTagFormatException
	*/
	public boolean validateMailBags(Collection<MailbagVO> mailbagVos, boolean isUploadHHT)
			throws InvalidMailTagFormatException {
		HashMap<String, Collection<String>> hashMap = null;
		Map<String, Map<String, CityVO>> cityMaps = new HashMap<String, Map<String, CityVO>>();
		String originOfficeExchange = null;
		String destinationOfficeExchange = null;
		String mailSubClass = null;
		log.debug("MailBag" + " : " + "ValidateMailBag" + " Entering");
		if (mailbagVos != null && mailbagVos.size() > 0) {
			for (MailbagVO mailbagVo : mailbagVos) {
				log.debug("" + "The Operational Flag From MailBagVo" + " " + mailbagVo.getOperationalFlag());
				if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVo.getOperationalFlag())) {
					if (hashMap == null) {
						log.info("Will be entered only once");
						hashMap = new HashMap<String, Collection<String>>();
						hashMap.put(COUNTRY_CACHE, new ArrayList<String>());
						hashMap.put(CITY_CACHE, new ArrayList<String>());
						hashMap.put(EXCHANGE_CACHE, new ArrayList<String>());
						hashMap.put(ERROR_CACHE, new ArrayList<String>());
						hashMap.put(SUBCLS_CACHE, new ArrayList<String>());
						hashMap.put(CITYPAIR_CACHE, new ArrayList<String>());
					}
					if (isUploadHHT && hashMap.get(ERROR_CACHE) != null && hashMap.get(ERROR_CACHE).size() > 0) {
						hashMap.put(ERROR_CACHE, new ArrayList<String>());
					}
					originOfficeExchange = mailbagVo.getOoe();
					destinationOfficeExchange = mailbagVo.getDoe();
					mailSubClass = mailbagVo.getMailSubclass();
					if (originOfficeExchange != null && !hashMap.get(EXCHANGE_CACHE).contains(originOfficeExchange)) {
						log.info("A valid Origin OE is not there in Map");
						validateOfficeExchange(mailbagVo.getCompanyCode(), originOfficeExchange, hashMap, cityMaps,
								true);
					}
					if (!(hashMap.get(ERROR_CACHE).size() == 0)) {
						errorString.append(" The mailbag id : ");
						errorString.append(mailbagVo.getMailbagId());
						errorString.append(" : ");
						errorString.append(mailbagVo.getActionMode());
						String finalerrorString = errorString.toString();
						//TODO: Neo to correct
						//errPgExceptionLogger.info(finalerrorString);
					}
					if (destinationOfficeExchange != null
							&& !hashMap.get(EXCHANGE_CACHE).contains(destinationOfficeExchange)) {
						log.info("A valid Destination OE is not there in Map");
						validateOfficeExchange(mailbagVo.getCompanyCode(), destinationOfficeExchange, hashMap, cityMaps,
								false);
					}
					if (!(hashMap.get(ERROR_CACHE).size() == 0)) {
						errorString.append(" The mailbag id : ");
						errorString.append(mailbagVo.getMailbagId());
						errorString.append(" : ");
						errorString.append(mailbagVo.getActionMode());
						String finalerrorString = errorString.toString();
						//TODO: Neo to correct
						// errPgExceptionLogger.info(finalerrorString);
					}
					if (mailSubClass != null) {
						validateSubClass(mailbagVo.getCompanyCode(), mailSubClass, hashMap);
					}
					if (!(hashMap.get(ERROR_CACHE).size() == 0)) {
						errorString.append(" The mailbag id : ");
						errorString.append(mailbagVo.getMailbagId());
						errorString.append(" : ");
						errorString.append(mailbagVo.getActionMode());
						String finalerrorString = errorString.toString();
						//TODO: Neo to correct
						//errPgExceptionLogger.info(finalerrorString);
						String str = createErrors(mailbagVo, hashMap.get(ERROR_CACHE));
						if (isUploadHHT) {
							mailbagVo.setErrorType(MailConstantsVO.EXCEPT_FATAL);
							mailbagVo.setErrorDescription(str);
						} else {
							throw new InvalidMailTagFormatException(InvalidMailTagFormatException.INVALID_MAILFORMAT,
									new Object[] { str });
						}
					}
				}
			}
		}
		return true;
	}

	/** 
	* @author A-1936This method is used to validateOfficeExchange
	* @param companyCode
	* @param officeExchange
	* @param hashMap
	* @param cityMaps
	* @param isOrigin
	* @throws SystemException
	*/
	public void validateOfficeExchange(String companyCode, String officeExchange,
			HashMap<String, Collection<String>> hashMap, Map<String, Map<String, CityVO>> cityMaps, boolean isOrigin) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		HashMap<String, String> cityOEMap = null;
		errorString = new StringBuilder();
		Collection<String> officeOfExchanges = new ArrayList<String>();
		officeOfExchanges.add(officeExchange);
		cityOEMap = mailController.findCityForOfficeOfExchange(companyCode, officeOfExchanges);
		String country = officeExchange.substring(0, 2);
		String city = null;
		if (cityOEMap != null) {
			city = cityOEMap.get(officeExchange);
		}
		Map<String, CityVO> cityMap = null;
		boolean isValidCountry = true;
		OfficeOfExchangeVO officeOfExchangeVO = null;
		boolean isValidCity = true;
		boolean isValidExchange = true;
		boolean isValidCityCountryPair = true;
		boolean isValidPoaCode = true;
		if (!hashMap.get(EXCHANGE_CACHE).contains(officeExchange) && officeExchange.trim().length() == 6) {
			log.debug("MailBag" + " : " + "validateOfficeExchange" + " Entering");
			officeOfExchangeVO = mailController
					.validateOfficeOfExchange(companyCode, officeExchange);
			if (officeOfExchangeVO != null) {
				isValidExchange = true;
				if (officeOfExchangeVO.getPoaCode() == null) {
					isValidPoaCode = false;
				}
			} else {
				isValidExchange = false;
			}
		}
		if (!isValidExchange) {
			if (!hashMap.get(COUNTRY_CACHE).contains(country)) {
				isValidCountry = validateCountry(companyCode, country);
			}
			if (!hashMap.get(CITY_CACHE).contains(city)) {
				cityMap = validateCity(companyCode, city, cityMap);
				if (cityMap == null) {
					log.info("CITY MAP NULL");
					isValidCity = false;
				}
			} else if (officeExchange.trim().length() == 5) {
				cityMap = cityMaps.get(city);
				log.debug("" + "The Constructed City Map is " + " " + cityMap);
			}
			if (!hashMap.get(CITYPAIR_CACHE).contains(officeExchange) && officeExchange.trim().length() == 5) {
				if (isValidCity) {
					String countryCode = cityMap.get(city).getCountryCode();
					if (!country.equals(countryCode)) {
						log.debug("" + "The Country Code" + " " + countryCode);
						isValidCityCountryPair = false;
					}
				} else {
					isValidCityCountryPair = false;
				}
			}
			if (isValidCountry) {
				if (!hashMap.get(COUNTRY_CACHE).contains(country)) {
					hashMap.get(COUNTRY_CACHE).add(country);
				}
			} else {
				if (isOrigin) {
					hashMap.get(ERROR_CACHE).add(ORIGIN_COUNTRY_ERR);
					errorString.append(officeExchange);
					errorString.append(" is an invalid Orgin Office Of exchage ");
				} else {
					errorString.append(officeExchange);
					errorString.append(" is an invalid Destination Office Of exchage ");
					hashMap.get(ERROR_CACHE).add(DESTN_COUNTRY_ERR);
				}
			}
			if (isValidCity) {
				if (!hashMap.get(CITY_CACHE).contains(city)) {
					hashMap.get(CITY_CACHE).add(city);
				}
				if (cityMaps.get(city) == null) {
					cityMaps.put(city, cityMap);
				}
			} else {
				if (isOrigin) {
					errorString.append(officeExchange);
					errorString.append(" - Invalid Orgin Office Of exchage ");
					hashMap.get(ERROR_CACHE).add(ORIGIN_CITY_ERR);
				} else {
					errorString.append(officeExchange);
					errorString.append("- Invalid Destination Office Of exchage ");
					hashMap.get(ERROR_CACHE).add(DESTN_CITY_ERR);
				}
			}
		}
		if (officeExchange.trim().length() == 5) {
			if (isValidCityCountryPair) {
				if (!hashMap.get(CITYPAIR_CACHE).contains(officeExchange)) {
					hashMap.get(CITYPAIR_CACHE).add(officeExchange);
				}
			} else {
				if (isOrigin) {
					errorString.append(officeExchange);
					errorString.append(" - Invalid Office Of exchage ");
					hashMap.get(ERROR_CACHE).add(ORIGIN_PAIR_ERR);
				} else {
					errorString.append(officeExchange);
					errorString.append(" - Invalid Office Of exchage  ");
					hashMap.get(ERROR_CACHE).add(DESTN_PAIR_ERR);
				}
			}
		} else {
			if (isValidExchange) {
				if (!hashMap.get(EXCHANGE_CACHE).contains(officeExchange)) {
					hashMap.get(EXCHANGE_CACHE).add(officeExchange);
				}
			} else {
				if (isOrigin) {
					hashMap.get(ERROR_CACHE).add(ORIGINEXCHANGE_ERR);
				} else {
					hashMap.get(ERROR_CACHE).add(DESTINATIONEXCHANGE_ERR);
				}
			}
		}
		if (!isValidPoaCode) {
			if (isOrigin) {
				hashMap.get(ERROR_CACHE).add(ORIGIN_EXG_PA_ERR);
			}
		}
	}

	/** 
	* @author a-1936This method is used to validate the MailSubClass
	* @param companyCode
	* @param subclass
	* @param hashMap
	* @throws SystemException
	*/
	private void validateSubClass(String companyCode, String subclass, HashMap<String, Collection<String>> hashMap) {
		log.debug("MailBag" + " : " + "validateSubClass" + " Entering");
		boolean isValidSubClass = true;
		errorString = new StringBuilder();
		if (!hashMap.get(SUBCLS_CACHE).contains(subclass)) {

			isValidSubClass = ContextUtil.getInstance().getBean(MailController.class)
					.validateMailSubClass(companyCode, subclass);
		}
		if (isValidSubClass) {
			if (!hashMap.get(SUBCLS_CACHE).contains(subclass)) {
				hashMap.get(SUBCLS_CACHE).add(subclass);
			}
		} else {
			hashMap.get(ERROR_CACHE).add(SUBCLS_CACHE);
			errorString.append(subclass);
			errorString.append(" is an Invalid subclass");
		}
	}

	/** 
	* @author a-1936
	* @author A-3227This method is used to create an appended String tat has be sent to the Client along with the Rxception when the MailTagValidation Fails
	* @param mailbagVo
	* @param errors
	* @return
	*/
	public String createErrors(MailbagVO mailbagVo, Collection<String> errors) {
		log.debug("INSIDE THE CREATE ERRORS" + " : " + "createErrors" + " Entering");
		StringBuilder builder = null;
		String error = "";
		HashMap<String, String> cityOEMap = null;
		Collection<String> officeOfExchanges = new ArrayList<String>();
		String cityOrigin = null;
		String cityDestination = null;
		String countryOrigin = StringUtils.substring(mailbagVo.getOoe(),0, 2);
		String countryDestination = StringUtils.substring(mailbagVo.getDoe(),0, 2);
		if (mailbagVo.getOoe() != null && mailbagVo.getOoe().length() > 0) {
			officeOfExchanges.add(mailbagVo.getOoe());
		}
		if (mailbagVo.getDoe() != null && mailbagVo.getDoe().length() > 0) {
			officeOfExchanges.add(mailbagVo.getDoe());
		}
		try {
			cityOEMap = ContextUtil.getInstance().getBean(MailController.class).findCityForOfficeOfExchange(mailbagVo.getCompanyCode(), officeOfExchanges);
		} finally {
		}
		if (cityOEMap != null && cityOEMap.size() > 0) {
			cityOrigin = cityOEMap.get(mailbagVo.getOoe());
			cityDestination = cityOEMap.get(mailbagVo.getDoe());
		}
		log.debug("" + "------cityOrigin-------" + " " + cityOrigin);
		log.debug("" + "--------cityDestination-----" + " " + cityDestination);
		if (errors != null && errors.size() > 0) {
			builder = new StringBuilder();
			for (String errorStr : errors) {
				if (errorStr.equals(ORIGIN_CITY_ERR)) {
					if (cityOrigin != null) {
						builder.append("City code ").append(cityOrigin).append("\n");
					}
				} else if (errorStr.equals(DESTN_CITY_ERR)) {
					if (cityDestination != null) {
						builder.append("City code ").append(cityDestination).append("\n");
					}
				} else if (errorStr.equals(ORIGIN_COUNTRY_ERR)) {
					builder.append("Country code ").append(countryOrigin).append("\n");
				} else if (errorStr.equals(DESTN_COUNTRY_ERR)) {
					builder.append("Country code ").append(countryDestination).append("\n");
				} else if (errorStr.equals(ORIGINEXCHANGE_ERR)) {
					builder.append("Origin OE ").append(mailbagVo.getOoe()).append("\n");
				} else if (errorStr.equals(DESTINATIONEXCHANGE_ERR)) {
					builder.append("Destination OE ").append(mailbagVo.getDoe()).append("\n");
				} else if (errorStr.equals(ORIGIN_PAIR_ERR)) {
					builder.append("City-Country pair").append(" ").append(mailbagVo.getOoe()).append("\n");
				} else if (errorStr.equals(DESTN_PAIR_ERR)) {
					builder.append("City-Country pair").append(" ").append(mailbagVo.getDoe()).append("\n");
				} else if (errorStr.equals(SUBCLS_CACHE)) {
					builder.append(" Subclass code ").append(mailbagVo.getMailSubclass()).append("\n");
				} else if (errorStr.equals(ORIGIN_EXG_PA_ERR)) {
					builder.append(" Origin PA Code ").append("\n");
				}
			}
			builder.append(" is invalid for ").append(mailbagVo.getMailbagId());
			if (mailbagVo.getUldNumber() != null && mailbagVo.getUldNumber().trim().length() > 0) {
				builder.append(" in ").append(mailbagVo.getUldNumber());
			}
			error = builder.toString();
		}
		log.debug("" + "The Error created is " + " " + error);
		return error;
	}

	/** 
	* @author a-1936This method is used to validate the Country
	* @param companyCode
	* @param country
	* @return
	* @throws SystemException
	*/
	private boolean validateCountry(String companyCode, String country) {
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		log.debug("MailBag" + " : " + "validateCountry" + " Entering");
		boolean isValidCountry = true;
		Collection<String> countryCodes = new ArrayList<String>();
		countryCodes.add(country);
		try {
			sharedAreaProxy.validateCountryCodes(companyCode, countryCodes);
		} catch (SharedProxyException ex) {
			log.info("<<<<<<<<<<<<THE PROXY EXCEPTION CAUGHT >>>>>>>>>>>>>");
			isValidCountry = false;
		}
		return isValidCountry;
	}

	/** 
	* @author A-1936This method is used to validate the City
	* @param companyCode
	* @param city
	* @param cityMap
	* @return
	* @throws SystemException
	*/
	private Map<String, CityVO> validateCity(String companyCode, String city, Map<String, CityVO> cityMap) {
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		log.debug("MailBag" + " : " + "validateCity" + " Entering");
		Collection<String> cityCodes = new ArrayList<String>();
		Map<String, CityVO> cityMapFromProxy = null;
		cityCodes.add(city);
		try {
			cityMapFromProxy = sharedAreaProxy.validateCityCodes(companyCode, cityCodes);
		} catch (SharedProxyException ex) {
			log.info("<<<<<<<<<<<<THE PROXY EXCEPTION CAUGHT >>>>>>>>>>>>>");
		}
		return cityMapFromProxy;
	}

	/** 
	* @author A-2037This method is used to find the History of a Mailbag
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public static Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
		try {
			String mldMsgGenerateFlag = findSystemParameterValue(MLDMSGSENDINGREQRD);
			mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode, mailBagId, mailSequenceNumber,
					mldMsgGenerateFlag));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagHistoryVOs;
	}




	/** 
	* TODO Purpose Oct 6, 2006, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public static MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO) {
		try {
			return constructDAO().findMailbagDetailsForUpload(mailbagVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	/** 
	* @author a-1936 This method is used to construct the MailkBagPK
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	private static MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		} else {
			mailbagPK.setMailSequenceNumber(
					findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		}
		return mailbagPK;
	}

	/** 
	* A-1739
	* @param toFlightVO
	* @param containerVO
	* @param toFlightSegmentSerialNum
	* @throws SystemException
	*/
	public void updateFlightForReassign(OperationalFlightVO toFlightVO, ContainerVO containerVO,
			int toFlightSegmentSerialNum, boolean isRDTUpdateReq) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		setCarrierId(toFlightVO.getCarrierId());
		setFlightNumber(toFlightVO.getFlightNumber());
		setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
		setSegmentSerialNumber(toFlightSegmentSerialNum);
		setScannedPort(containerVO.getAssignedPort());
		if (toFlightVO.getFlightSequenceNumber() > 0) {
			setPou(toFlightVO.getPou());
		} else {
			setPou(containerVO.getFinalDestination());
		}
		if (containerVO.getScannedDate() != null) {
			setScannedDate(containerVO.getScannedDate());
		} else {
			setScannedDate(localDateUtil.getLocalDate(containerVO.getAssignedPort(), true));
		}

		setLastUpdatedUser(logonAttributes.getUserId());
		if (containerVO.isOffload()) {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);
		} else {
			if (containerVO.isHandoverReceived()) {
				setLatestStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
			} else {
				if (!containerVO.isRemove()) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				}
			}
		}
		setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		if (containerVO.getAssignedUser() != null) {
			setScannedUser(containerVO.getAssignedUser());
		} else {
			setScannedUser(logonAttributes.getUserId());
		}
		if (containerVO.getMailSource() != null) {
			setMailbagSource(containerVO.getMailSource());
		}
		setMailRemarks(containerVO.getRemarks());
		if (containerVO.isUldTobarrow()) {
			setContainerType("B");
		}
		if (containerVO.isBarrowToUld()) {
			setContainerType("U");
		}
		if (!isRDTUpdateReq && getReqDeliveryTime() != null) {
			setReqDeliveryTime(getReqDeliveryTime());
		} else {
			ZonedDateTime reqDlvTim = null;
			try {
				MailbagVO mailbagTransferVO = new MailbagVO();
				mailbagTransferVO.setDestination(getDestination());
				mailbagTransferVO.setPaCode(getPaCode());
				mailbagTransferVO.setCompanyCode(getCompanyCode());
				mailbagTransferVO.setFlightNumber(toFlightVO.getFlightNumber());
				mailbagTransferVO.setFlightDate(toFlightVO.getFlightDate());
				mailbagTransferVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
				reqDlvTim = calculateRDT(mailbagTransferVO);
			} finally {
			}
			if(reqDlvTim!=null){
			setReqDeliveryTime(reqDlvTim.toLocalDateTime());
			}
		}
	}

	/** 
	* Method		:	Mailbag.findRoutingDetailsForConsignment Added by 	:	A-8061 on 22-May-2020 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	*/
	public static String findRoutingDetailsForConsignment(MailbagVO mailbagVO) {
		return constructDAO().findRoutingDetailsForConsignment(mailbagVO);
	}

	/** 
	* @author A-2553
	* @return
	* @throws SystemException
	*/
	public static String findMailType(MailbagVO mailbagVO) {
		try {
			return constructDAO().findMailType(mailbagVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public MailbagVO retrieveVO() {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(this.getCompanyCode());
		mailbagVO.setMailSequenceNumber(this.getMailSequenceNumber());
		mailbagVO.setMailbagId(getMailIdr());
		mailbagVO.setDespatchId(getDsnIdr());
		mailbagVO.setDespatchSerialNumber(getDespatchSerialNumber());
		mailbagVO.setOoe(getOrginOfficeOfExchange());
		mailbagVO.setDoe(getDestinationOfficeOfExchange());
		mailbagVO.setMailSubclass(getMailSubClass());
		mailbagVO.setMailCategoryCode(getMailCategory());
		mailbagVO.setYear(getYear());
		mailbagVO.setCarrierId(getCarrierId());
		mailbagVO.setFlightNumber(getFlightNumber());
		mailbagVO.setFlightSequenceNumber(getFlightSequenceNumber());
		mailbagVO.setHighestNumberedReceptacle(getHighestNumberedReceptacle());
		mailbagVO.setLatestStatus(getLatestStatus());
		mailbagVO.setMailRemarks(getMailRemarks());
		mailbagVO.setPou(getPou());
		mailbagVO.setReceptacleSerialNumber(getReceptacleSerialNumber());
		mailbagVO.setRegisteredOrInsuredIndicator(getRegisteredOrInsuredIndicator());
		mailbagVO.setScannedDate(localDateUtil.getLocalDate(getScannedPort(), true));
		mailbagVO.setScannedPort(getScannedPort());
		mailbagVO.setScannedUser(getScannedUser());
		mailbagVO.setSegmentSerialNumber(getSegmentSerialNumber());
		Quantity strWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(getWeight()));
		mailbagVO.setWeight(strWt);
		mailbagVO.setOperationalStatus(getOperationalStatus());
		mailbagVO.setPaCode(getPaCode());
		mailbagVO.setDamageFlag(getDamageFlag());
		mailbagVO.setMailClass(getMailClass());
		mailbagVO.setOrigin(getOrigin());
		mailbagVO.setDestination(getDestination());

		if (getLastUpdatedTime() != null) {
			mailbagVO.setLastUpdateTime(localDateUtil.getLocalDate(null, getLastUpdatedTime()));
		}
		mailbagVO.setLastUpdateUser(this.getLastUpdatedUser());
		return mailbagVO;
	}

	/** 
	* This method is used to update the mailbag details in case of reassigning from Flight to Destination Set MalSta as OFL in case of OFFLOAD
	* @param mailbagVo
	* @param toDestinationVo
	* @throws SystemException
	*/
	public void updateDestinationReassignDetails(MailbagVO mailbagVo, ContainerVO toDestinationVo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailBag" + " : " + "updateDestinationDetails" + " Entering");
		log.debug("" + "The MAIL BAG VO IS FOUND TO BE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + " " + mailbagVo);
		log.debug("" + "The MAIL BAG VO IS FOUND TO BE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + " "
				+ mailbagVo.getLatestStatus());
		MailbagHistoryVO mailbagHistoryVO = constructMailbagHistoryForDestn(mailbagVo, toDestinationVo);
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		setLatestStatus((mailbagVo.isIsoffload()) ? MailConstantsVO.MAIL_STATUS_OFFLOADED
				: MailConstantsVO.MAIL_STATUS_ASSIGNED);
		if (toDestinationVo.isFromDeviationList()) {
			return;
		}
		if (mailbagVo.isIsoffload() && mailbagVo.isRemove()) {
			return;
		}
		if (mailbagVo.getMailCompanyCode() != null && mailbagVo.getMailCompanyCode().trim().length() > 0) {
			setMailCompanyCode(mailbagVo.getMailCompanyCode());
		}
		if (mailbagVo.getMailCompanyCode() != null && mailbagVo.getMailCompanyCode().trim().length() > 0) {
			if (mailbagVo.getMailCompanyCode() != null
					&& !mailbagVo.getMailCompanyCode().equals(getMailCompanyCode())) {
				//TODO: Audit to be implemented in Neo
//				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
//						MailbagAuditVO.SUB_MOD_OPERATIONS, MailbagAuditVO.ENTITY_MAILBAG);
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
				setMailCompanyCode(mailbagVo.getMailCompanyCode());
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
//				mailbagAuditVO.setActionCode("UPDATEMAL");
//				mailbagAuditVO.setCompanyCode(mailbagVo.getCompanyCode());
//				mailbagAuditVO.setMailbagId(mailbagVo.getMailbagId());
//				mailbagAuditVO.setDsn(mailbagVo.getDespatchSerialNumber());
//				mailbagAuditVO.setOriginExchangeOffice(mailbagVo.getOoe());
//				mailbagAuditVO.setDestinationExchangeOffice(mailbagVo.getDoe());
//				mailbagAuditVO.setMailSubclass(mailbagVo.getMailSubclass());
//				mailbagAuditVO.setMailCategoryCode(mailbagVo.getMailCategoryCode());
//				mailbagAuditVO.setYear(mailbagVo.getYear());
//				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
//				StringBuffer additionalInfo = new StringBuffer();
//				additionalInfo.append("Company code ").append("updated for mailbag	").append(mailbagVo.getMailbagId());
//				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
//				AuditUtils.performAudit(mailbagAuditVO);
			} else {
				setMailCompanyCode(mailbagVo.getMailCompanyCode());
			}
		}
		setCarrierId(toDestinationVo.getCarrierId());
		setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		setUldNumber(toDestinationVo.getContainerNumber());
		log.debug("" + "THE CONTAINER TYPE FROM DESTINATION VO IS " + " " + toDestinationVo.getType());
		setContainerType(toDestinationVo.getType());
		setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
		setScannedDate(localDateUtil.getLocalDate(toDestinationVo.getAssignedPort(), true));
		setPou(toDestinationVo.getFinalDestination());
		setScannedUser(mailbagVo.getScannedUser());
		setLastUpdatedUser(logonAttributes.getUserId());
	}
	/**
	 * @author A-1936
	 * This methodis used to construct the MailBagHistoryVo
	 * @param mailbagVo
	 * @param toDestinationVo
	 * @return
	 */
	public MailbagHistoryVO constructMailbagHistoryForDestn(MailbagVO mailbagVo, ContainerVO toDestinationVo)
			throws SystemException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Mailbag mailbag=null;
		try {
			mailbag=find(createMailbagPK(mailbagVo.getCompanyCode(), mailbagVo));
		} catch (FinderException e) {
			mailbag=null;
		}
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(mailbag.getCompanyCode());
		mailbagHistoryVO.setPaBuiltFlag(toDestinationVo.getPaBuiltFlag());

		mailbagHistoryVO.setMailStatus(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ASSIGNED);
		mailbagHistoryVO.setCarrierId(toDestinationVo.getCarrierId());
		mailbagHistoryVO.setFlightNumber(String.valueOf(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT));
		mailbagHistoryVO.setFlightSequenceNumber(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT);
		mailbagHistoryVO.setContainerNumber(toDestinationVo.getContainerNumber());
		mailbagHistoryVO.setSegmentSerialNumber(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT);
		if(mailbagVo.getScannedDate() != null ){
			mailbagHistoryVO.setScanDate(mailbagVo.getScannedDate());
		}
		else {
			mailbagHistoryVO.setScanDate(localDateUtil.getLocalDate(toDestinationVo.getAssignedPort(),true));
		}
		mailbagHistoryVO.setScannedPort(mailbagVo.getScannedPort());
		mailbagHistoryVO.setScanUser(mailbagVo.getScannedUser());
		mailbagHistoryVO.setCarrierCode(toDestinationVo.getCarrierCode());
		mailbagHistoryVO.setContainerType(toDestinationVo.getType());
		if(mailbagHistoryVO.getFlightSequenceNumber()>0){
			mailbagHistoryVO.setPou(toDestinationVo.getFinalDestination());
		}
		mailbagHistoryVO.setMailSource(mailbagVo.getMailSource());//Added for ICRD-156218
		mailbagHistoryVO.setMessageVersion(mailbagVo.getMessageVersion());
		if(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.FLAG_YES.equals(toDestinationVo.getPaBuiltFlag())){
			mailbagHistoryVO.setAdditionalInfo(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.PA_BUILT_ADD_INFO);
		}
		return mailbagHistoryVO;
	}

	public MailbagPK createMailbagPK(String companyCode, MailbagVO mailbagVO) throws SystemException {
		MailbagPK mailbagPK = new MailbagPK();
		long mailsequenceNumber;
		if(mailbagVO.getMailSequenceNumber()>0) {
			mailsequenceNumber = mailbagVO.getMailSequenceNumber();
		} else {

			mailsequenceNumber = findMailSequenceNumber(mailbagVO.getMailbagId(),companyCode);
		}

		mailbagPK.setCompanyCode(companyCode);
		mailbagPK.setMailSequenceNumber(mailsequenceNumber);
		return mailbagPK;
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @param toContainerVO
	* @throws SystemException
	*/
	public void updateFlightReassignDetails(MailbagVO mailbagVO, ContainerVO toContainerVO, boolean isRDTUpdateReq) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("Mailbag" + " : " + "updateFlightReassignDetails" + " Entering");
		log.debug("" + "getSts" + " " + getLatestStatus());
		log.debug("" + "mailbagVO.getBellyCartId()" + " " + mailbagVO.getBellyCartId());
		if (toContainerVO.isFromDeviationList()) {
			return;
		}
		setScannedDate(mailbagVO.getScannedDate());
		setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		setScannedUser((mailbagVO.getScannedUser()));
		if (mailbagVO.getMailCompanyCode() != null && mailbagVO.getMailCompanyCode().trim().length() > 0) {
			if (mailbagVO.getMailCompanyCode() != null
					&& !mailbagVO.getMailCompanyCode().equals(getMailCompanyCode())) {
				//TODO: Audit to be implemented in Neo
//				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
//						MailbagAuditVO.SUB_MOD_OPERATIONS, MailbagAuditVO.ENTITY_MAILBAG);
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
				setMailCompanyCode(mailbagVO.getMailCompanyCode());
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
//				mailbagAuditVO.setActionCode("UPDATEMAL");
//				mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
//				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
//				mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
//				mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
//				mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
//				mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
//				mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
//				mailbagAuditVO.setYear(mailbagVO.getYear());
//				LoginProfile logonAttributes = contextUtil.callerLoginProfile();
//				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
//				StringBuffer additionalInfo = new StringBuffer();
//				additionalInfo.append("Company code ").append("updated for mailbag	").append(mailbagVO.getMailbagId());
//				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
//				AuditUtils.performAudit(mailbagAuditVO);
			} else {
				setMailCompanyCode(mailbagVO.getMailCompanyCode());
			}
		}
		if (mailbagVO.getMailCompanyCode() != null && mailbagVO.getMailCompanyCode().trim().length() > 0) {
			setMailCompanyCode(mailbagVO.getMailCompanyCode());
		}
		if (toContainerVO == null) {
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			setPou(mailbagVO.getPou());
			setUldNumber(mailbagVO.getContainerNumber());
			setContainerType(mailbagVO.getContainerType());
		} else {
			setCarrierId(toContainerVO.getCarrierId());
			setFlightNumber(toContainerVO.getFlightNumber());
			setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			setPou(toContainerVO.getPou());
			setUldNumber(toContainerVO.getContainerNumber());
			setContainerType(toContainerVO.getType());
		}
		if (!isRDTUpdateReq) {
			//TODO: Neo to verify below code
			//setReqDeliveryTime(getReqDeliveryTime());
		} else {
			ZonedDateTime reqDlvTim = null;
			try {
				MailbagVO mailbagTransferVO = new MailbagVO();
				mailbagTransferVO.setDestination(mailbagVO.getDestination());
				mailbagTransferVO.setPaCode(mailbagVO.getPaCode());
				mailbagTransferVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagTransferVO.setFlightNumber(toContainerVO.getFlightNumber());
				mailbagTransferVO.setFlightDate(toContainerVO.getFlightDate());
				mailbagTransferVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
				reqDlvTim = calculateRDT(mailbagTransferVO);
			} finally {
			}
			if(Objects.nonNull(reqDlvTim)) {
				setReqDeliveryTime(reqDlvTim.toLocalDateTime());
			}
		}
	}

	/** 
	* @author A-1936This method is used to updateMailbags Details
	* @param flightAssignedMails
	* @param toContainerVO
	* @throws SystemException
	*/
	public void updateReassignedMailbags(Collection<MailbagVO> flightAssignedMails, ContainerVO toContainerVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		if (flightAssignedMails != null && flightAssignedMails.size() > 0) {
			Mailbag mailbag = null;
			String routingDetails = null;
			HashMap<String, String> routingCache = new HashMap<String, String>();
			boolean isRDTUpdateReq = false;
			StringBuilder routingKey = null;
			for (MailbagVO mailbagVo : flightAssignedMails) {
				try {
					log.info("updateMailBags Called");
					mailbag = Mailbag.findMailbagDetails(constructMailbagPK(mailbagVo));

				//	mailbag.setLastUpdatedTime(Timestamp.valueOf(localDateUtil.getLocalDate(null,  true).toLocalDateTime()));
					mailbag.setScreeningUser(mailbagVo);
					mailbag.setMailbagSource(mailbagVo.getMailSource());
				} catch (FinderException ex) {
					log.info("DATA INCONSISTENT");
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					log.info(" calling >>>>>>>>>>>>>>updateDestinationDetails");
					mailbag.updateDestinationReassignDetails(mailbagVo, toContainerVO);
					mailbag.updatePrimaryAcceptanceDetails(mailbagVo);
				} else {
					log.info(" calling >>>>>>>>>>>>>>updateFlightDetails");
					if (mailbagVo.getDestination() == null) {
						mailbagVo.setDestination(mailbag.getDestination());
					}
					if (mailbagVo.getPaCode() == null) {
						mailbagVo.setPaCode(mailbag.getPaCode());
					}
					isRDTUpdateReq = true;
					if (mailbagVo.getConsignmentNumber() != null) {
						routingKey = new StringBuilder();
						routingKey.append(mailbagVo.getPaCode()).append(mailbagVo.getConsignmentNumber())
								.append(mailbagVo.getDestination());
						if (!routingCache.containsKey(routingKey.toString())) {
							routingDetails = findRoutingDetailsForConsignment(mailbagVo);
							if (routingDetails != null) {
								routingCache.put(routingKey.toString(), routingDetails);
								isRDTUpdateReq = false;
							}
						} else {
							isRDTUpdateReq = false;
						}
					}
					mailbag.updateFlightReassignDetails(mailbagVo, toContainerVO, isRDTUpdateReq);
					mailbag.updatePrimaryAcceptanceDetails(mailbagVo);
				}
				String additionalinfo=additionalinfoForupdateReassignedMailbags(mailbagVo,toContainerVO);
				ContextUtil.getInstance().getBean(MailController.class).auditMailDetailUpdates(mailbagVo,mailbagVo.isIsoffload()?MailbagAuditVO.MAILBAG_OFFLOAD:MailbagAuditVO.MAILBAG_REASSIGN,"Update",additionalinfo);

			}
		}
	}

	/** 
	* @author A-1739
	* @param mailbagPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static Mailbag findMailbag(MailbagPK mailbagPK) throws FinderException {
		return Mailbag.find(mailbagPK);
	}

	/** 
	* Updates the returndetails in mailbag master and DSNAtAirport A-1739
	* @param isScanned 
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	public void updateReturnedMailbags(Collection<MailbagVO> mailbagsToReturn, boolean isScanned)
			throws DuplicateMailBagsException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		int totalbags = 0;
		int flightAcpbags = 0;
		double flightAcpWt = 0;
		int destAcpbags = 0;
		double destAcpWt = 0;
		double totalWeight = 0;
		String airportCode = null;
		boolean isInbound = false;
		for (MailbagVO mailbagVO : mailbagsToReturn) {
			totalbags = 0;
			totalWeight = 0;
			mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
			if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())) {
				isInbound = true;
			}
			Mailbag mailbag = null;
			boolean isNew = false;
			try {
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbag = findMailbagDetails(mailbagPk);
			} catch (FinderException ex) {
				if (isScanned) {
					isNew = true;
					Collection<DamagedMailbagVO> dmgMails = mailbagVO.getDamagedMailbags();
					mailbagVO.setDamagedMailbags(null);
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
					mailbagVO.setMailbagDataSource(mailbagVO.getLatestStatus());
					new MailController().calculateAndUpdateLatestAcceptanceTime(mailbagVO);
					mailbag = new Mailbag(mailbagVO);
					mailbagVO.setDamagedMailbags(dmgMails);
				} else {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
			}
			if (mailbag != null) {
				//mailbag.setLastUpdatedTime(localDateUtil.getLocalDate(null, true));
				if (isNew) {
					mailbag.updateAcceptanceFlightDetails(mailbagVO);
				} else {
					totalWeight += mailbagVO.getWeight().getValue().doubleValue();
					totalbags++;
					if (mailbagVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
						destAcpbags++;
						destAcpWt += mailbagVO.getWeight().getValue().doubleValue();
					} else {
						flightAcpbags++;
						flightAcpWt += mailbagVO.getWeight().getValue().doubleValue();
					}
				}
				mailbag.updateReturnDetails(mailbagVO);
			}
			airportCode = mailbagVO.getScannedPort();
			MailbagInULDAtAirportVO mailbagInULDAtAirportVO = new MailbagInULDAtAirportVO();
			mailbagInULDAtAirportVO.setAirportCode(airportCode);
			mailbagInULDAtAirportVO.setComapnyCode(mailbagVO.getCompanyCode());
			mailbagInULDAtAirportVO.setCarrierId(mailbagVO.getCarrierId());
			if (MailConstantsVO.BULK_TYPE.equals(mailbagVO.getContainerType())) {
				mailbagInULDAtAirportVO.setUldNumber(
						new StringBuilder().append("BULK").append("-").append(mailbagVO.getPou()).toString());
			} else {
				mailbagInULDAtAirportVO.setUldNumber(mailbagVO.getUldNumber());
			}
			mailbagInULDAtAirportVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mailbagInULDAtAirportVO.setAcceptedBags(totalbags);
			mailbagInULDAtAirportVO
					.setAcceptedWgt(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalWeight)));
			updateMailbagInULDAtAirportReturnDetails(mailbagInULDAtAirportVO);
			log.debug("DSN" + " : " + "updateReturnedMailbags" + " Exiting");
		}
	}

	private static void updateMailbagInULDAtAirportReturnDetails(MailbagInULDAtAirportVO dsnAtAirportVO) {
		try {
			MailbagInULDAtAirport mailbagInULDAtAirport = MailbagInULDAtAirport
					.find(constructMailbagInULDAtAirportPK(dsnAtAirportVO));
			mailbagInULDAtAirport.remove();
		} catch (FinderException e) {
		}
	}

	private static MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(MailbagInULDAtAirportVO dsnAtAirportVO) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(dsnAtAirportVO.getComapnyCode());
		mailbagInULDAtAirportPK.setCarrierId(dsnAtAirportVO.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(dsnAtAirportVO.getAirportCode());
		mailbagInULDAtAirportPK.setMailSequenceNumber(dsnAtAirportVO.getMailSequenceNumber());
		mailbagInULDAtAirportPK.setUldNumber(dsnAtAirportVO.getUldNumber());
		return mailbagInULDAtAirportPK;
	}

	/** 
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateReturnDetails(MailbagVO mailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Mailbag" + " : " + "updateReturnDetails" + " Entering");
		Collection<DamagedMailbagVO> damagedMailbagVOs = mailbagVO.getDamagedMailbags();
		if (damagedMailbagVOs != null && damagedMailbagVOs.size() > 0) {
			setDamageFlag(MailConstantsVO.FLAG_YES);
			updateMailbagDamage(mailbagVO);
		}
		setCarrierId(MailConstantsVO.ZERO);
		setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
		setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		setSegmentSerialNumber(MailConstantsVO.ZERO);
		setPou(null);
		setLatestStatus(MailConstantsVO.MAIL_STATUS_RETURNED);
		setScannedPort(mailbagVO.getScannedPort());
		setScannedUser(mailbagVO.getScannedUser());
		setScannedDate(localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true));
		if (mailbagVO.getMailSource() != null) {
			setMailbagSource(mailbagVO.getMailSource());
		} else {
			setMailbagSource(MailConstantsVO.MAIL_STATUS_RETURNED);
		}
		log.debug("Mailbag" + " : " + "updateReturnDetails" + " Exiting");
	}

	/** 
	* TODO Purpose Oct 11, 2006, a-1739
	* @param mailbagVOs
	* @param toContainerVO
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> updateMailbagsForTransfer(Collection<MailbagVO> mailbagVOs,
			ContainerVO toContainerVO) {
		log.debug("DSN" + " : " + "updateMailbagsForTransfer" + " Entering");
		Collection<MailbagVO> mailbagsInDSN = new ArrayList<MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			try {
				MailbagPK mailbagPK = new MailbagPK();
				mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException exception) {
				log.debug("Exception in MailController at closeInboundFlightForMailOperation for Online *Flight* ");
				continue;
			} catch (Exception exception) {
				log.debug("Exception in MailController at closeInboundFlightForMailOperation for Online *Flight* ");
				continue;
			}
			if (mailbagVO.getDestination() == null) {
				mailbagVO.setDestination(mailbag.getDestination());
			}
			if (mailbagVO.getPaCode() == null) {
				mailbagVO.setPaCode(mailbag.getPaCode());
			}
			if (toContainerVO.getSegmentSerialNumber() != 0) {
				mailbagVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			}
			mailbag.updateDetailsForTransfer(toContainerVO, mailbagVO);
			MailbagVO mailbagAfterTransfer = mailbag.retrieveVO();
			mailbagAfterTransfer.setCarrierCode(toContainerVO.getCarrierCode());
			mailbagAfterTransfer.setVolume(mailbagVO.getVolume());
			mailbagsInDSN.add(mailbagAfterTransfer);
			mailbagVO.setPaBuiltFlag(toContainerVO.getPaBuiltFlag());
		}
		log.debug("DSN" + " : " + "updateMailbagsForTransfer" + " Exiting");
		return mailbagsInDSN;
	}

	/** 
	* TODO Purpose Oct 11, 2006, a-1739
	* @param toContainerVO
	* @throws SystemException
	*/
	public void updateDetailsForTransfer(ContainerVO toContainerVO, MailbagVO mailbagToUpdate) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("Mailbag" + " : " + "updateDetailsForTransfer" + " Entering");
		LoginProfile logonAttributes = null;
		if (mailbagToUpdate.isFromDeviationList()) {
			return;
		}
		setScannedPort(toContainerVO.getAssignedPort());
		logonAttributes = contextUtil.callerLoginProfile();
		setScannedUser(mailbagToUpdate.getScannedUser());
		if ("WS".equals(mailbagToUpdate.getMailSource()) && mailbagToUpdate.getScannedDate() != null) {
			setScannedDate(mailbagToUpdate.getScannedDate());
		} else {
			setScannedDate(localDateUtil.getLocalDate(getScannedPort(), true));
		}
		if (mailbagToUpdate.getMailCompanyCode() != null && mailbagToUpdate.getMailCompanyCode().trim().length() > 0) {
			if (!mailbagToUpdate.getMailCompanyCode().equals(getMailCompanyCode())) {
				setMailCompanyCode(mailbagToUpdate.getMailCompanyCode());


			} else {
				setMailCompanyCode(mailbagToUpdate.getMailCompanyCode());
			}
		}
		boolean[] eventStats = checkIfHistoryExists(null, MailConstantsVO.MAIL_STATUS_ARRIVED,
				MailConstantsVO.MAIL_STATUS_DELIVERED);
		boolean isArrived = eventStats[0];
		boolean isDelivered = eventStats[1];
		if (!isArrived && !isDelivered) {
		}
		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagToUpdate.getMailStatus())) {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		} else if (MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagToUpdate.getMailStatus())) {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
		} else {
			setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		}
		if (mailbagToUpdate.getMailSource() != null) {
			setMailbagSource(mailbagToUpdate.getMailSource());
		} else {
			setMailbagSource(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		}
		if (toContainerVO.getCarrierId() == 0) {
			setCarrierId(mailbagToUpdate.getCarrierId());
		} else {
			setCarrierId(toContainerVO.getCarrierId());
		}
		if (toContainerVO.getFlightNumber() == null) {
			setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
			setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			setSegmentSerialNumber(MailConstantsVO.ZERO);
		} else {
			setFlightNumber(toContainerVO.getFlightNumber());
			setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
		}
		setPou(toContainerVO.getPou() == null ? toContainerVO.getFinalDestination() : toContainerVO.getPou());
		setUldNumber(toContainerVO.getContainerNumber());
		setContainerType(toContainerVO.getType());
		setMailRemarks(mailbagToUpdate.getMailRemarks());
		calculateMailbagVolume(mailbagToUpdate);
		if (mailbagToUpdate.getVolume() != null) {
			setVolume(mailbagToUpdate.getVolume().getDisplayValue().doubleValue());
		}
		setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		if (new MailController().isUSPSMailbag(mailbagToUpdate)) {
			setOnTimeDelivery(MailConstantsVO.FLAG_NO);
		} else {
			setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
		}
		String routingDetails = null;
		if (mailbagToUpdate.getConsignmentNumber() != null)
			routingDetails = constructDAO().findRoutingDetailsForConsignment(mailbagToUpdate);
		if (routingDetails != null && getReqDeliveryTime() != null) {

			setReqDeliveryTime(getReqDeliveryTime());
		} else if (getReqDeliveryTime() == null && toContainerVO.getFlightSequenceNumber() > 0) {
			ZonedDateTime reqDlvTim = null;
			try {
				MailbagVO mailbagTransferVO = new MailbagVO();
				mailbagTransferVO.setDestination(mailbagToUpdate.getDestination());
				mailbagTransferVO.setPaCode(mailbagToUpdate.getPaCode());
				mailbagTransferVO.setCompanyCode(mailbagToUpdate.getCompanyCode());
				mailbagTransferVO.setFlightNumber(toContainerVO.getFlightNumber());
				mailbagTransferVO.setFlightDate(toContainerVO.getFlightDate());
				mailbagTransferVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
				mailbagTransferVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
				reqDlvTim = calculateRDT(mailbagTransferVO);
			} finally {
			}
			if(Objects.nonNull(reqDlvTim)) {
				setReqDeliveryTime(reqDlvTim.toLocalDateTime());
			}
		}
		setPaCode(mailbagToUpdate.getPaCode());
		updatePrimaryAcceptanceDetails(mailbagToUpdate);
		if (getFirstScanDate() == null && getFirstScanPort() == null) {
			setFirstScanDate(getScannedDate());
			setFirstScanPort(getScannedPort());
		}
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(MailbagAuditVO.MAILBAG_TRANSFER.toLowerCase());
		if(mailbagToUpdate.getScannedDate()!=null){
			additionalInfo.append(" on ").append(mailbagToUpdate.getScannedDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
		}
		if(!("-1".equals(toContainerVO.getFlightNumber()))){
			additionalInfo.append("; to ").append(toContainerVO.getCarrierCode()).append(toContainerVO.getFlightNumber());
			if(mailbagToUpdate.getFlightDate()!=null){
				additionalInfo.append(" ").append(toContainerVO.getFlightDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
			}
		}else{
			additionalInfo.append("; to ").append(toContainerVO.getCarrierCode());
		}
		additionalInfo.append(" ; at airport ");
		if(mailbagToUpdate.getScannedPort()!=null){
			additionalInfo.append(mailbagToUpdate.getScannedPort());
		}else{
			additionalInfo.append(logonAttributes.getAirportCode());
		}
		if(toContainerVO.getContainerNumber()!=null){
			additionalInfo.append(" ; in container ").append(toContainerVO.getContainerNumber());
		}
		if(mailbagToUpdate.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagToUpdate.getPou());
		}
		additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
				append(" ; Source -").append(mailbagToUpdate.getMailSource());

		ContextUtil.getInstance().getBean(MailController.class).auditMailDetailUpdates(mailbagToUpdate,
				MailbagAuditVO.MAILBAG_TRANSFER,"UPDATED", additionalInfo.toString());
		log.debug("Mailbag" + " : " + "updateDetailsForTransfer" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateArrivalDetails(MailbagVO mailbagVO) {
		log.debug("Mailbag" + " : " + "updateArrivalDetails" + " Entering");
		if (mailbagVO.isFromDeviationList()) {
			return;
		}
		if (getCarrierId() == 0) {
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			if (MailConstantsVO.MAIL_STATUS_NEW.equals(getLatestStatus())) {
				setUldNumber(mailbagVO.getContainerNumber());
				setContainerType(mailbagVO.getContainerType());
			}
			if (null == getPou()) {
				setPou(mailbagVO.getPou());
			}
		} else {
			log.debug("if carrierID !=0 then update flight details");
			setCarrierId(mailbagVO.getCarrierId());
			setFlightNumber(mailbagVO.getFlightNumber());
			setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			setUldNumber(mailbagVO.getContainerNumber());
			setContainerType(mailbagVO.getContainerType());
			if (null == getPou()) {
				setPou(mailbagVO.getPou());
			}
		}
		boolean[] arrDlv = updateIfHistoryExists(mailbagVO);
		boolean isAlreadyArrived = arrDlv[0];
		boolean isAlreadyDelivered = arrDlv[1];
		boolean isAlreadyDamaged = arrDlv[2];
		if (!isAlreadyArrived || !isAlreadyDelivered) {
			boolean canUpdate = false;
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
				if (!isAlreadyArrived && !isAlreadyDelivered) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
					if (mailbagVO.getMailSource() != null) {
						setMailbagSource(mailbagVO.getMailSource());
					} else {
						setMailbagSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
					}
					setMailCompanyCode(mailbagVO.getMailCompanyCode());
					canUpdate = true;
				}
			}
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
				if (!isAlreadyDelivered) {
					setMailCompanyCode(mailbagVO.getMailCompanyCode());
					setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
					if (mailbagVO.getMailSource() != null) {
						setMailbagSource(mailbagVO.getMailSource());
					} else {
						setMailbagSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
					}
					if (mailbagVO.isNeedDestUpdOnDlv()) {
						setDestination(mailbagVO.getDestination());
						setDestinationOfficeOfExchange(mailbagVO.getDoe());
					}
					canUpdate = true;
				}
			}
			if (canUpdate || MailConstantsVO.OPERATION_INBOUND.equals(getOperationalStatus())) {
				if (!MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())) {
					if (mailbagVO.getScannedDate() != null) {
						setScannedDate(mailbagVO.getScannedDate());
					}
				} else {
					performChangeScannedTimeAudit(mailbagVO);
				}
				if (mailbagVO.getScannedUser() != null) {
					setScannedUser(mailbagVO.getScannedUser());
				}
				if (mailbagVO.getScannedPort() != null) {
					setScannedPort(mailbagVO.getScannedPort());
				}
				setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
			} else {
			}
			mailbagVO.setLatestStatus(getLatestStatus());
			setMailRemarks(mailbagVO.getMailRemarks());
			if (mailbagVO.getDamageFlag() != null && mailbagVO.getDamageFlag().equals(MailConstantsVO.FLAG_YES)
					&& !isAlreadyDelivered) {
				boolean newDamage = false;
				Collection<DamagedMailbagVO> damageVOs = mailbagVO.getDamagedMailbags();
				if (damageVOs != null && damageVOs.size() > 0) {
					for (DamagedMailbagVO damagedMailbagVO : damageVOs) {
						if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO.getOperationFlag())) {
							populateDamageDetails(damageVOs);
							newDamage = true;
						}
					}
				}
				if (newDamage) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
					if (mailbagVO.getDamageFlag() != null) {
						setDamageFlag(mailbagVO.getDamageFlag());
					} else {
						setDamageFlag(MailbagVO.FLAG_NO);
					}
				}
			}
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag()) && !isAlreadyDelivered) {
				setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
				if (mailbagVO.getMailSource() != null) {
					setMailbagSource(mailbagVO.getMailSource());
				} else {
					setMailbagSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
				}
			}
			if (MailbagVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
				if (mailbagVO.isResditRequired()) {
					updateArrResditEventTimes(mailbagVO);
				}
			}
		} else if (isAlreadyDelivered || isAlreadyArrived) {
			if (mailbagVO.getDamageFlag() != null && mailbagVO.getDamageFlag().equals(MailConstantsVO.FLAG_YES)) {
				boolean newDamage = false;
				Collection<DamagedMailbagVO> damageVOs = mailbagVO.getDamagedMailbags();
				if (damageVOs != null && damageVOs.size() > 0) {
					for (DamagedMailbagVO damagedMailbagVO : damageVOs) {
						if (MailConstantsVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO.getOperationFlag())) {
							populateDamageDetails(damageVOs);
							newDamage = true;
						}
					}
				}
				if (newDamage) {
					setLatestStatus(MailConstantsVO.MAIL_STATUS_DAMAGED);
					setDamageFlag(mailbagVO.getDamageFlag());
				}
			}
			if (mailbagVO.isResditRequired()) {
				updateArrResditEventTimes(mailbagVO);
			}
			if (!MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())) {
				if (mailbagVO.getScannedDate() != null) {
					setScannedDate(mailbagVO.getScannedDate());
				}
			} else {
				performChangeScannedTimeAudit(mailbagVO);
			}
		}
		if (getFirstScanDate() == null && getFirstScanPort() == null && mailbagVO.getScannedPort() != null
				&& mailbagVO.getScannedDate() != null) {
			//TODO: Neo to handle below code
			//setFirstScanDate(getScannedDate());
			setFirstScanPort(getScannedPort());
		}
		if (mailbagVO.getDeliveredFlag() != null && mailbagVO.getDeliveredFlag().equals("Y") && getIntFlg() != null
				&& getIntFlg().equals("Y")) {
			setIntFlg("N");
		}
		log.debug("Mailbag" + " : " + "updateArrivalDetails" + " Exiting");
	}

	/** 
	* This method checks whether History entry exists or not. If exists for Arrived/Delivered status updates it's Scan date.
	* @param mailbagVO
	* @return
	* @throws SystemException 
	*/
	public boolean[] updateIfHistoryExists(MailbagVO mailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Collection<MailbagHistoryVO> existingMailbagHistories = findMailbagHistories(this.getCompanyCode(),
				"", this.getMailSequenceNumber());
		boolean[] arrDlv = new boolean[3];
		if (existingMailbagHistories != null && existingMailbagHistories.size() > 0) {
			for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
				if (mailbagHistory.getScannedPort().equals(mailbagVO.getScannedPort())) {
					if (mailbagHistory.getMailStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
						if (MailConstantsVO.CHANGE_SCAN_TIME.equals(mailbagVO.getScreen())
								&& !MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
							MailbagHistoryPK mailbaghistorypk = MailbagHistory
									.constructMailbagHistoryPK(mailbagHistory);
							MailbagHistory mailhistory = null;
							try {
								mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
								mailhistory.setScanDate(mailbagVO.getScannedDate().toLocalDateTime());
								ZonedDateTime utcDate = localDateUtil.getLocalDate(null, true);
								ZonedDateTime gmt = localDateUtil.toUTCTime(utcDate);
								mailhistory.setUtcScandate(gmt.toLocalDateTime());
								mailhistory.setLastUpdatedUser(mailbagVO.getLastUpdateUser());
								//TODO: Neo to correct below
//								mailhistory.setLastUpdatedTime(new com.ibsplc.icargo.framework.util.time.LocalDate(
//										ZonedDateTime.NO_STATION, Location.NONE, true));
							} catch (FinderException e) {
								log.error("Finder Exception Caught");
							}
						}
						arrDlv[0] = true;
					}
					if (mailbagHistory.getMailStatus().equals(MailConstantsVO.MAIL_STATUS_DELIVERED)) {
						MailbagHistoryPK mailbaghistorypk = MailbagHistory.constructMailbagHistoryPK(mailbagHistory);
						MailbagHistory mailhistory = null;
						try {
							mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
							mailhistory.setScanDate(mailbagVO.getScannedDate().toLocalDateTime());
							//TODO:To correct in Neo
							///mailhistory.setUtcScandate(mailbagVO.getScannedDate().toGMTDate());
							mailhistory.setLastUpdatedUser(mailbagVO.getLastUpdateUser());
							//TODO:To correct in Neo
//							mailhistory.setLastUpdatedTime(new LocalDate(
//									ZonedDateTime.NO_STATION, Location.NONE, true));
						} catch (FinderException e) {
							log.error("Finder Exception Caught");
						}
						arrDlv[1] = true;
					}
				}
				if (mailbagHistory.getMailStatus().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)) {
					arrDlv[2] = true;
				}
			}
		}
		return arrDlv;
	}

	/** 
	* @param mailbagVO
	* @throws SystemException
	*/
	private void updateArrResditEventTimes(MailbagVO mailbagVO) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.updateResditEventTimes(mailbagVO);
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateDamageDetails(MailbagVO mailbagVO) {
		updateMailbagDamage(mailbagVO);
	}

	/** 
	* @author A-1739
	* @param dsnVO
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	public void saveArrivalDetails(DSNVO dsnVO) throws DuplicateMailBagsException {
		log.debug("DSN" + " : " + "saveArrivalDetails" + " Entering");
		Collection<MailbagVO> mailbagVOs = dsnVO.getMailbags();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
				if (mailbagVO.getOperationalFlag() != null) {
					boolean isNew = false;
					Mailbag mailbag = null;
					try {
						mailbag = findMailbag(constructMailbagPK(mailbagVO));
					} catch (FinderException e) {
						log.error("Finder Exception Caught");
					}
					if (mailbag == null) {
						isNew = true;
					}
					boolean isDuplicate = false;
					if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						if (!isNew) {
							if (!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()))
									&& MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus())
									&& mailbag.getScannedPort().equals(mailbagVO.getScannedPort())) {
								isDuplicate = new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),
										mailbagVO.getPaCode(), mailbag);
							}
						}
						if (isNew || isDuplicate) {
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
							mailbag = new Mailbag(mailbagVO);
						}
					}
					if (mailbagVO.getLastUpdateTime() != null
							&& !MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						mailbag.setLastUpdatedTime(Timestamp.valueOf(mailbagVO.getLastUpdateTime().toLocalDateTime()));
					}
					if (mailbag != null) {
						try {
							mailbag.updateArrivalDetails(mailbagVO);
						} catch (Exception exception) {
							log.debug(
									"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with Mailbag "
											+ mailbagVO);
							continue;
						}
						DocumentController docController = new DocumentController();
						MailInConsignmentVO mailInConsignmentVO = null;
						mailInConsignmentVO = docController.findConsignmentDetailsForMailbag(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId(), null);
						if (mailInConsignmentVO != null) {
						}
					}
				}
			}
		}
		log.debug("DSN" + " : " + "saveArrivalDetails" + " Exiting");
	}

	public static Map<Long, Collection<MailbagHistoryVO>> findMailbagHistoriesMap(String companyCode,
			long[] malseqnum) {
		Map<Long, Collection<MailbagHistoryVO>> mailHistories = null;
		try {
			mailHistories = constructDAO().findMailbagHistoriesMap(companyCode, malseqnum);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailHistories;
	}

	public static Map<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(String companyCode,
			long[] malseqnum) {
		Map<Long, MailInConsignmentVO> consignments = null;
		try {
			consignments = constructDAO().findAllConsignmentDetailsForMailbag(companyCode, malseqnum);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return consignments;
	}

	/** 
	* Method to perform audit during change scan time Added for ICRD-140584
	* @param mailbagVO
	* @throws SystemException
	*/
	private void performChangeScannedTimeAudit(MailbagVO mailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContextUtil contextUtil = ContextUtil.getInstance();
		if (mailbagVO.getScannedDate() != null) {
			if (!mailbagVO.getScannedDate().equals(getScannedDate())) {
				ZonedDateTime oldDate = localDateUtil.getLocalDateTime(getScannedDate(), getScannedPort());
				//TODO: Audit to be corrected in NEO
//				MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,
//						MailbagAuditVO.SUB_MOD_OPERATIONS, MailbagAuditVO.ENTITY_MAILBAG);
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
//				setScannedDate(mailbagVO.getScannedDate());
//				mailbagAuditVO = (MailbagAuditVO) AuditUtils.populateAuditDetails(mailbagAuditVO, this, false);
//				mailbagAuditVO.setActionCode(MailbagAuditVO.MAL_SCNDATTIM_UPDATED);
//				mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
//				mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
//				mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
//				mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
//				mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
//				mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
//				mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
//				mailbagAuditVO.setYear(mailbagVO.getYear());
//				LoginProfile logonAttributes = contextUtil.callerLoginProfile();
//				mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
//				StringBuffer additionalInfo = new StringBuffer();
//				additionalInfo.append("Scan date and time of the mail bag ").append(mailbagVO.getMailbagId())
//						.append(" was changed from ")
//						.append(oldDate.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT))).append(" to ")
//						.append(mailbagVO.getScannedDate().toDisplayFormat());
//				mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
//				AuditUtils.performAudit(mailbagAuditVO);
			}
		}
	}

	/** 
	* Method to fetch mailbag transaction histories only exclude cardit info
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public static Collection<MailbagHistoryVO> findMailbagHistoriesWithoutCarditDetails(String companyCode,
			String mailbagId) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<>();
		try {
			String mldMsgGenerateFlag = findSystemParameterValue(MLDMSGSENDINGREQRD);
			mailbagHistoryVOs
					.addAll(constructDAO().findMailbagHistories(companyCode, mailbagId, 0l, mldMsgGenerateFlag));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagHistoryVOs;
	}

	/** 
	* Method		:	Mailbag.performAttachAWBDetailsForMailbag Added by 	:	U-1267 on Nov 2, 2017 Used for 	:	ICRD-211205 Parameters	:	@param mailbagVO Parameters	:	@param shipmentValidationVO  Return type	: 	void
	*/
	public void performAttachAWBDetailsForMailbag(MailbagVO mailbagVO, ShipmentValidationVO shipmentValidationVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		this.setMasterDocumentNumber(shipmentValidationVO.getMasterDocumentNumber());
		this.setDocumentOwnerId(shipmentValidationVO.getOwnerId());
		this.setSequenceNumber(shipmentValidationVO.getSequenceNumber());
		this.setDupliacteNumber(shipmentValidationVO.getDuplicateNumber());
		this.setShipmentPrefix(shipmentValidationVO.getShipmentPrefix());
		//TODO: Audit to be reworked in Neo
				StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("The mailbag ").append(this.getMailIdr()).append(ATTACHED_TO_AWB)
				.append(this.getShipmentPrefix()).append("-").append(this.getMasterDocumentNumber());
		ContextUtil.getInstance().getBean(MailController.class)
				.auditMailDetailUpdates(mailbagVO,MailbagAuditVO.MAL_AWB_ATTACHED, "Update", additionalInfo.toString());

	}

	public String getActualWeightDisplayUnit() {
		return actualWeightDisplayUnit;
	}

	public void setActualWeightDisplayUnit(String actualWeightDisplayUnit) {
		this.actualWeightDisplayUnit = actualWeightDisplayUnit;
	}

	public double getActualWeightDisplayValue() {
		return actualWeightDisplayValue;
	}

	public void setActualWeightDisplayValue(double actualWeightDisplayValue) {
		this.actualWeightDisplayValue = actualWeightDisplayValue;
	}

	/** 
	* @author A-7540
	* @return
	* @throws SystemException
	*/
	private ZonedDateTime calculateRDT(MailbagVO mailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String rcpDest = mailbagVO.getDestination();
		ZonedDateTime reqDeliveryDate = null;
		String paCode_int = null;
		String paCode_dom = null;
		Boolean isDestinationPresent = false;
		String[] flightRoutes = null;
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		if (paCode_dom.equals(mailbagVO.getPaCode())) {
			mailbagVO.setReqDeliveryTime(null);
			return null;
		}
		FlightValidationVO flightValidationVO = null;
		flightValidationVO = validateFlight(mailbagVO);
		if (flightValidationVO != null) {
			flightRoutes = flightValidationVO.getFlightRoute().split("-");
			for (int i = 0; i < flightRoutes.length; i++) {
				if (flightRoutes[i].equals(rcpDest)) {
					isDestinationPresent = true;
					break;
				}
			}
		}
		if (!isDestinationPresent) {
			mailbagVO.setReqDeliveryTime(null);
			return null;
		}
		if (rcpDest != null)
			reqDeliveryDate = localDateUtil.getLocalDate(rcpDest, true);
		RdtMasterFilterVO filterVO = new RdtMasterFilterVO();
		filterVO.setAirportCodes(rcpDest);
		filterVO.setCompanyCode(mailbagVO.getCompanyCode());
		filterVO.setGpaCode(mailbagVO.getPaCode());
		Collection<MailRdtMasterVO> mailRdtMasterVOs = null;
		try {
			mailRdtMasterVOs = constructDAO().findRdtMasterDetails(filterVO);
		} catch (PersistenceException e) {
			e.getMessage();
		}
		ZonedDateTime scheduledTimeOfArrival = null;
		ZonedDateTime scheduledTimeOfArrivalCopy = null;
		if (flightValidationVO != null) {
			scheduledTimeOfArrival = LocalDateMapper.toZonedDateTime(flightValidationVO.getSta());
		}
		if (mailRdtMasterVOs != null && mailRdtMasterVOs.size() > 0) {
			for (MailRdtMasterVO mailRdtMasterVO : mailRdtMasterVOs) {
				if (paCode_int.equals(mailbagVO.getPaCode())) {
					if (scheduledTimeOfArrival != null) {
						if (rcpDest != null)
							scheduledTimeOfArrivalCopy = localDateUtil.getLocalDateTime(scheduledTimeOfArrival,
									rcpDest);
						if (scheduledTimeOfArrivalCopy != null) {
							scheduledTimeOfArrivalCopy = scheduledTimeOfArrivalCopy.plusDays(mailRdtMasterVO.getRdtDay());
							scheduledTimeOfArrivalCopy = scheduledTimeOfArrivalCopy.plusMinutes(mailRdtMasterVO.getRdtOffset());
							reqDeliveryDate = scheduledTimeOfArrivalCopy;
						}
					}
				}
			}
		} else {
			reqDeliveryDate = scheduledTimeOfArrival;
		}
		return reqDeliveryDate;
	}

	/** 
	* @author A-7540
	* @return
	* @throws SystemException
	*/
	private FlightValidationVO validateFlight(MailbagVO mailbagVO) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		flightValidationVOs = flightOperationsProxy.validateFlightForAirport(flightFilterVO);
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()) {
					return flightValidationVO;
				}
			}
		}
		return null;
	}

	/** 
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateMailbagForConsignment(MailbagVO mailbagVO) {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if (mailbag != null) {
				if(mailbagVO.getReqDeliveryTime()!=null) {
					mailbag.setReqDeliveryTime(mailbagVO.getReqDeliveryTime().toLocalDateTime());
				}
				mailbag.setOrigin(mailbagVO.getOrigin());
				mailbag.setDestination(mailbagVO.getDestination());
				mailbag.setMailServiceLevel(mailbagVO.getMailServiceLevel());
				if (mailbagVO.getConsignmentNumber() != null && !mailbagVO.getConsignmentNumber().isEmpty()
						&& !mailbagVO.getConsignmentNumber().equals(mailbag.getConsignmentNumber())) {
					mailbag.setConsignmentNumber(mailbagVO.getConsignmentNumber());
					mailbag.setConsignmentSequenceNumber(mailbagVO.getConsignmentSequenceNumber());
				}
				if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
					mailbag.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
				}
				if (mailbagVO.getWeight() != null && mailbagVO.getWeight().getValue().doubleValue() != mailbag.getWeight()
						&& mailbag.getPaCode().equals(findSystemParameterValue(USPS_DOMESTIC_PA))
						&& MailConstantsVO.CARDIT_PROCESS.equals(mailbagVO.getMailSource())) {
					mailbag.setWeight(mailbagVO.getWeight().getValue().doubleValue());
					mailbag.setDisplayValue(mailbagVO.getWeight().getDisplayValue().doubleValue());
					mailbag.setDisplayUnit(mailbagVO.getWeight().getDisplayUnit().getName());
				}
				if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
					new MailController().updatemailperformanceDetails(mailbag);
				}
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* Method		:	Mailbag.updatePrimaryAcceptanceDetails Added by 	:	A-6245 on 26-Feb-2021 Used for 	:	IASCB-96538 Parameters	:	@param mailbagVO  Return type	: 	void
	*/
	public void updatePrimaryAcceptanceDetails(MailbagVO mailbagVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		if ((MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus()))
				&& (Objects.isNull(getAcceptanceAirportCode())
						|| getAcceptanceAirportCode().equals(mailbagVO.getScannedPort()))
				&& (Objects.isNull(getAcceptancePostalContainerNumber()))) {
			if (Objects.nonNull(mailbagVO.getScannedPort())) {
				setAcceptanceAirportCode(mailbagVO.getScannedPort());
				if (Objects.isNull(getAcceptanceScanDate())) {
					if (Objects.nonNull(mailbagVO.getScannedDate())) {
						setAcceptanceScanDate(mailbagVO.getScannedDate().toLocalDateTime());
					} else if (Objects.nonNull(mailbagVO.getFlightDate())) {
						setAcceptanceScanDate(mailbagVO.getFlightDate().toLocalDateTime());
					} else {
						setAcceptanceScanDate(
								localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true).toLocalDateTime());
					}
				}
			} else {
				setAcceptanceAirportCode(getScannedPort());
				//TODO: Neo to correct below code
				//setAcceptanceScanDate(getScannedDate());
			}
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
				setAcceptancePostalContainerNumber(mailbagVO.getContainerNumber());
				setPaBuiltFlag(MailConstantsVO.FLAG_YES);
			}
		}
		if ((mailbagVO.isPaContainerNumberUpdate() || mailbagVO.isPaBuiltFlagUpdate())
				&& (getAcceptanceAirportCode().equals(mailbagVO.getScannedPort())
						|| MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getTriggerForReImport())
						|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailbagVO.getTriggerForReImport()))) {
			mailbagVO.setPreviousPostalContainerNumber(getAcceptancePostalContainerNumber());
			if (Objects.isNull(mailbagVO.getAcceptancePostalContainerNumber())
					|| mailbagVO.getAcceptancePostalContainerNumber().isEmpty()) {
				setPaBuiltFlag(MailConstantsVO.FLAG_NO);
				setAcceptancePostalContainerNumber(null);
			} else {
				setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				setAcceptancePostalContainerNumber(mailbagVO.getAcceptancePostalContainerNumber());
			}
		}
		if (Objects.nonNull(getAcceptanceScanDate())) {
			//TODO: Neo to correct below code
//			mailbagVO.setAcceptanceScanDate(new com.ibsplc.icargo.framework.util.time.LocalDate(
//					ZonedDateTime.NO_STATION, Location.NONE, getAcceptanceScanDate(), true));
		}
	}

	/** 
	* @author A-9619
	* @return company specific mail controller
	* @throws SystemException IASCB-55196
	*/
	public static Mailbag getInstance() {
		return ContextUtil.getInstance().getBean(Mailbag.class);
	}

	/** 
	* @author A-9619
	* @param mailbagPK
	* @return
	* @throws FinderException
	* @throws SystemException implemented for xaddons IASCB-55196
	*/
	public static Mailbag findMailbagDetails(MailbagPK mailbagPK) throws FinderException {
		return PersistenceController.getEntityManager().find(Mailbag.class, mailbagPK);
	}

	public void setScreeningUser(MailbagVO mailbagVo) throws FinderException {
	}

	public static MailbagVO findMailbagDetails(String mailBagId, String companyCode) {
		return constructDAO().findMailbagDetails(mailBagId, companyCode);
	}

	public String getIntFlg() {
		return intFlg;
	}

	public void setIntFlg(String intFlg) {
		this.intFlg = intFlg;
	}

	public void calculateMailbagVolume(MailbagVO mailbagVO) {
		String commodityCode = "";
		CommodityValidationVO commodityValidationVO = null;
		try {
			commodityCode = new MailController().findSystemParameterValue("mailtracking.defaults.booking.commodity");
			commodityValidationVO = new MailController().validateCommodity(mailbagVO.getCompanyCode(), commodityCode,
					mailbagVO.getPaCode());
		} catch (Exception e) {
			log.debug("" + "Exception" + " " + e);
		}
		validateCommodity(commodityValidationVO, mailbagVO);
	}

	public static void validateCommodity(CommodityValidationVO commodityValidationVO, MailbagVO mailbagVO) {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		if (commodityValidationVO != null && commodityValidationVO.getDensityFactor() > 0) {
			if (mailbagVO.getWeight() != null) {
				//TODO: neo to remove unit hard coding
				double mailbagWgt=mailbagVO.getWeight().getValue().doubleValue()!=0.0?mailbagVO.getWeight().getValue().doubleValue():
						mailbagVO.getWeight().getDisplayValue().doubleValue();
				double weightInKg = ContextUtil.getInstance().getBean(DocumentController.class).unitConvertion(Quantities.MAIL_WGT,
						mailbagVO.getWeight().getUnit().getName(), "K",
						mailbagWgt);
				double actualVolume = (weightInKg / (commodityValidationVO.getDensityFactor()));
				mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(actualVolume),
						BigDecimal.valueOf(0.0), "B"));
			}
		}
	}

	public void setDespatchDate(ZonedDateTime despatchDate) {
		if (Objects.nonNull(despatchDate)) {
			this.despatchDate = despatchDate.toLocalDateTime();
		}
	}

	public void setScannedDate(ZonedDateTime scannedDate) {
		if (Objects.nonNull(scannedDate)) {
			this.scannedDate = scannedDate.toLocalDateTime();
		}
	}

	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws SystemException {
		try {
			return constructDAO().getMailbagDetails(mailMasterDataFilterVO);
		} catch (PersistenceException e) {
			log.error("Exception in: getMailbagDetails", e.getMessage());
		}

		return null;
	}
	public static Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) {
		Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs = new ArrayList<>();
		try {
			mailHistoryRemarksVOs.addAll(constructDAO().findMailbagNotes(mailBagId));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
		return mailHistoryRemarksVOs;
	}

	public static Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId,
																				 long mailSequenceNumber) throws SystemException {
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		try{
			String mldMsgGenerateFlag = findSystemParameterValue(MLDMSGSENDINGREQRD);
			if(mldMsgGenerateFlag== null){
				mldMsgGenerateFlag="Y";
			}
			mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode, mailBagId, mailSequenceNumber,mldMsgGenerateFlag));
		}
		catch(PersistenceException persistenceException){
			//LOGGER.log(Log.SEVERE,"findMailbagHistoriesFromWebScreen - PersistenceException: "+persistenceException);
			log.debug( " : " + "findMailbagHistoriesFromWebScreen - PersistenceException:" + persistenceException);
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagHistoryVOs;
	}


	public static Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
		MailbagHistoryVO mailbagHistoryVO = null;
		try {
			mailbagHistoryVOs.addAll(constructDAO().findMailStatusDetails(mailbagEnquiryFilterVO));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagHistoryVOs;
	}
	/**
	 * @author A-1885
	 * @param mailUploadVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailUploadVO> findMailBagandContainers(MailUploadVO mailUploadVO) {
		try {
			return constructDAO().findMailbagAndContainer(mailUploadVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @param opFltVo
	 * @return
	 * @throws SystemException
	 */
	public static ContainerAssignmentVO findContainerDetailsForMRD(OperationalFlightVO opFltVo, String mailBag) {
		try {
			return constructDAO().findContainerDetailsForMRD(opFltVo, mailBag);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method		:	Mailbag.validateMailBagsForUPL Added by 	:	A-4803 on 24-Nov-2014 Used for 	:	validating mail bags for MLD UPL Parameters	:	@param flightValidationVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<String>
	 */
	public static Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO) {
		try {
			return constructDAO().validateMailBagsForUPL(flightValidationVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author a-1936This method is used to find all the MailBags from the closed Flight
	 * @param operationalFlightVo
	 * @return
	 */
	public static Collection<String> findMailBagsInClosedFlight(OperationalFlightVO operationalFlightVo) {
		log.debug("Mailbag" + " : " + "findMailBagsInClosedFlight" + " Entering");
		try {
			return constructDAO().findMailBagsInClosedFlight(operationalFlightVo);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author a-1936This method is used to find the mailbags
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		if (mailbagEnquiryFilterVO.isInventory() && mailbagEnquiryFilterVO.getDestinationCity() != null
				&& mailbagEnquiryFilterVO.getDestinationCity().trim().length() > 0
				&& mailbagEnquiryFilterVO.getMailCategoryCode() != null
				&& mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			createDestintaionCategoryForInventory(mailbagEnquiryFilterVO);
		}
		try {
			return constructDAO().findMailbags(mailbagEnquiryFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author a-1936This method is used to create the appended String of Destination's and Category's tat could be used as the Part of the (IN) parameter in the Query of the Find MailBags .. Say From the Client the Destination Citys may be SIN,SIN,TRV should be converted to the Form Of 'SIN','TRV' Note:Duplicates also should be avoided Say From the Client the Categorys  may be A,A,U should be converted to the Form Of 'A','U' Note:Duplicates also should be avoided
	 * @param mailbagEnquiryFilterVO
	 */
	private static void createDestintaionCategoryForInventory(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		String[] destinationCity = mailbagEnquiryFilterVO.getDestinationCity().split(",");
		String[] category = mailbagEnquiryFilterVO.getMailCategoryCode().split(",");
		String createdDestination = null;
		String createdCategory = null;
		StringBuilder destinationBuilder = null;
		StringBuilder categoryBuilder = null;
		Collection<String> destinationCollection = null;
		Collection<String> categoryCollection = null;
		if (destinationCity != null && destinationCity.length > 0) {
			destinationBuilder = new StringBuilder();
			if (destinationCity.length == 1) {
				createdDestination = destinationBuilder.append("'").append(destinationCity[0]).append("'").toString();
			} else {
				destinationCollection = new ArrayList<String>();
				for (String dest : destinationCity) {
					if (!destinationCollection.contains(dest)) {
						destinationBuilder.append("'").append(dest).append("'").append(",");
						destinationCollection.add(dest);
					}
				}
				createdDestination = destinationBuilder.substring(0, destinationBuilder.length() - 1);
			}
		}
		if (category != null && category.length > 0) {
			categoryBuilder = new StringBuilder();
			if (category.length == 1) {
				if (!MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO.getMailCategoryCode())) {
					createdCategory = categoryBuilder.append("'").append(category[0]).append("'").toString();
				} else {
					createdCategory = categoryBuilder.append(category[0]).toString();
				}
			} else {
				categoryCollection = new ArrayList<String>();
				for (String ctg : category) {
					if (!categoryCollection.contains(ctg)) {
						categoryBuilder.append("'").append(ctg).append("'").append(",");
						categoryCollection.add(ctg);
					}
				}
				createdCategory = categoryBuilder.substring(0, categoryBuilder.length() - 1);
			}
		}
		mailbagEnquiryFilterVO.setDestinationCity(createdDestination);
		mailbagEnquiryFilterVO.setMailCategoryCode(createdCategory);
	}

	/**
	 * @author a-1936This  method is used to find out the MailDetais  For  all MailBags for which  Resdits are  not sent  and  having  the Search Mode as Despatch..
	 * @param despatchDetailVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailbagVO> findMailDetailsForDespatches(Collection<DespatchDetailsVO> despatchDetailVos,
																	 String unsentResditEvent) {
		try {
			return constructDAO().findMailDetailsForDespatches(despatchDetailVos, unsentResditEvent);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author a-1936This  method is used to find out the MailDetais  For  all MailBags for which  Resdits are not sent and having the Search Mode as Document..
	 * @param consignmentDocumentVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailbagVO> findMailDetailsForDocument(
			Collection<ConsignmentDocumentVO> consignmentDocumentVos, String unsentResditEvent) {
		try {
			return constructDAO().findMailDetailsForDocument(consignmentDocumentVos, unsentResditEvent);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author A-2037This method is used to find the Damaged Mailbag Details
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 */
	public static Collection<DamagedMailbagVO> findMailbagDamages(String companyCode, String mailbagId) {
		try {
			return constructDAO().findMailbagDamages(companyCode, mailbagId);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * TODO Purpose Sep 26, 2006, a-1739
	 * @param resditEventVO
	 * @param receptacleVO
	 * @return
	 * @throws SystemException
	 */
	private MailbagPK constructMailbagPKForResdit(ResditEventVO resditEventVO, ReceptacleInformationVO receptacleVO) {
		MailbagPK mailPK = new MailbagPK();
		mailPK.setCompanyCode(resditEventVO.getCompanyCode());
		if (receptacleVO.getMailSequenceNumber() > 0) {
			mailPK.setMailSequenceNumber(receptacleVO.getMailSequenceNumber());
		} else {
			String mailbagId = receptacleVO.getReceptacleID();
			mailPK.setMailSequenceNumber(
					findMailBagSequenceNumberFromMailIdr(mailbagId, resditEventVO.getCompanyCode()));
		}
		return mailPK;
	}

	/**
	 * Create history for all mailbags which had RESDITs flagged Sep 26, 2006, a-1739
	 * @param resditEventVO
	 * @param consignVO
	 * @throws SystemException
	 */
	public void insertMailbagHistoryForResdit(ResditEventVO resditEventVO, ConsignmentInformationVO consignVO,
											  ReceptacleInformationVO receptacleVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.info("Mailbag" + " : " + "insertMailbagHistoryForResdit" + " Entering");
		ContextUtil contextUtil = ContextUtil.getInstance();
		ZonedDateTime eventTime = consignVO.getEventDate().toZonedDateTime();
		Collection<TransportInformationVO> transportInfoVOs = consignVO.getTransportInformationVOs();
		TransportInformationVO transportVO = null;
		String mailbagId = receptacleVO.getReceptacleID();
		String triggeringPoint = contextUtil.getTxContext(ContextUtil.TRIGGER_POINT);
		if (transportInfoVOs != null && transportInfoVOs.size() > 0) {
			transportVO = ((ArrayList<TransportInformationVO>) transportInfoVOs).get(0);
		}
		if (receptacleVO.getReceptacleID() != null && receptacleVO.getReceptacleID().trim().length() > 0) {
			MailbagPK mailPK = constructMailbagPKForResdit(resditEventVO, receptacleVO);
			MailbagHistoryVO historyVO = constructMailbagHistoryVOForResdit(resditEventVO, receptacleVO, transportVO);
			historyVO.setCompanyCode(resditEventVO.getCompanyCode());
			if (receptacleVO.getMailSequenceNumber() > 0) {
				historyVO.setMailSequenceNumber(receptacleVO.getMailSequenceNumber());
			} else {
				historyVO.setMailSequenceNumber(
						findMailBagSequenceNumberFromMailIdr(mailbagId, resditEventVO.getCompanyCode()));
			}
			historyVO.setMailSource("JOB");
			historyVO.setMessageTime(localDateUtil.getLocalDate(resditEventVO.getEventPort(),true));
			if (eventTime != null && consignVO.getTransferLocation() != null
					&& consignVO.getTransferLocation().length() > 0) {
				historyVO.setScanDate(localDateUtil.getLocalDateTime(eventTime,consignVO.getTransferLocation()));
			} else {
				historyVO.setScanDate(localDateUtil.getLocalDate(resditEventVO.getEventPort(), true));
			}
			StringBuilder sb = new StringBuilder();
			sb.append(MailConstantsVO.SENDER_ID_LEADING_VALUE).append(resditEventVO.getSenderIdentifier())
					.append(MailConstantsVO.SENDER_RECIPIENT_SEPERATOR)
					.append(MailConstantsVO.RECIPIENT_ID_LEADING_VALUE).append(resditEventVO.getRecipientIdentifier());
			historyVO.setAdditionalInfo(sb.toString());
			new MailbagHistory(historyVO);
		}
		log.debug("Mailbag" + " : " + "insertMailbagHistoryForResdit" + " Exiting");
	}

	/**
	 * TODO Purpose Sep 26, 2006, a-1739
	 * @param resditEventVO
	 * @param receptacleVO
	 * @param transportVO
	 */
	private MailbagHistoryVO constructMailbagHistoryVOForResdit(ResditEventVO resditEventVO,
																ReceptacleInformationVO receptacleVO, TransportInformationVO transportVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setMailClass(getMailClass());
		mailbagHistoryVO.setMailStatus(resditEventVO.getResditEventCode());
		mailbagHistoryVO.setScannedPort(resditEventVO.getEventPort());
		if (transportVO != null) {
			mailbagHistoryVO.setCarrierCode(transportVO.getCarrierCode());
			mailbagHistoryVO.setCarrierId(transportVO.getCarrierID());
			mailbagHistoryVO.setFlightNumber(transportVO.getFlightNumber());
			mailbagHistoryVO.setFlightSequenceNumber(transportVO.getFlightSequenceNumber());
			mailbagHistoryVO.setSegmentSerialNumber(transportVO.getSegmentSerialNumber());
			if (((MailConstantsVO.RESDIT_TRANSPORT_COMPLETED.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_DELIVERED.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_ARRIVED.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEventVO.getResditEventCode()))
					|| (MailConstantsVO.RESDIT_FOUND.equals(resditEventVO.getResditEventCode())))
					&& (transportVO.getArrivalTime() != null)) {
				mailbagHistoryVO.setFlightDate(localDateUtil.getLocalDate(transportVO.getArrivalTime()));
			} else if(transportVO.getDepartureDate()!=null) {
				mailbagHistoryVO.setFlightDate(localDateUtil.getLocalDate(transportVO.getDepartureDate()));
			}else{
				mailbagHistoryVO.setFlightDate(null);
			}
		}
		return mailbagHistoryVO;
	}

	/**
	 * Method		:	Mailbag.findFlightsForArrival Added by 	:	A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemExcepion Return type	: 	Collection<OperationalFlightVO>
	 */
	public static Collection<OperationalFlightVO> findFlightsForArrival(String companyCode) {
		try {
			return constructDAO().findFlightsForArrival(companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method		:	Mailbag.findFlightsForArrival Added by 	:	A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemExcepion Return type	: 	Collection<OperationalFlightVO>
	 */
	public static Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO flightVO) {
		try {
			return constructDAO().findArrivalDetailsForReleasingMails(flightVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method		:	Mailbag.findOnlineFlightsAndConatiners Added by 	:	A-4809 on Sep 29, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<ContainerDetailsVO>
	 */
	public static Collection<MailArrivalVO> findOnlineFlightsAndConatiners(String companyCode) {
		try {
			return constructDAO().findOnlineFlightsAndConatiners(companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1885
	 * @param companyCode
	 * @param time
	 * @return
	 * @throws SystemException
	 */
	public static Collection<OperationalFlightVO> findFlightForMailOperationClosure(String companyCode, int time,
																					String airportCode) {
		try {
			return constructDAO().findFlightForMailOperationClosure(companyCode, time, airportCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method is ued to  find DSN
	 * @param dSNEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber) {
		try {
			return constructDAO().findDSNs(dSNEnquiryFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @param handoverVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<OperationalFlightVO> findOperationalFlightForMRD(HandoverVO handoverVO) {
		try {
			return constructDAO().findOperationalFlightForMRD(handoverVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * Method		:	Mailbag.findMailbagIdForMailTag Added by 	:	a-6245 on 22-Jun-2017 Used for 	:	Finding mailbagid from mail details Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MailbagVO
	 */
	public static MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO) {
		try {
			mailbagVO = constructDAO().findMailbagIdForMailTag(mailbagVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagVO;
	}
	/**
	 * Method		:	Mailbag.detachAwbInMailbag Added by 	:	a-7779 on 29-Aug-2017 Used for 	: Parameters	:	  Return type	: 	void
	 * @throws SystemException
	 */
	public void detachAwbInMailbag(MailbagVO mailbagVO) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("Mailbag" + " : " + "detachAwbInMailbag" + " Entering");
		if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(this.getLatestStatus())) {
			this.setLatestStatus(MailConstantsVO.MAIL_STATUS_AWB_CANCELLED);
		}
		this.setMasterDocumentNumber(null);
		this.setDocumentOwnerId(0);
		this.setSequenceNumber(0);
		this.setDupliacteNumber(0);
		this.setShipmentPrefix(null);
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append("The mailbag ").append(this.getMailIdr()).append(" was detached with AWB ")
				.append(mailbagVO.getShipmentPrefix()).append("-").append(mailbagVO.getDocumentNumber());
		ContextUtil.getInstance().getBean(MailController.class)
				.auditMailDetailUpdates(mailbagVO,MailbagAuditVO.MAL_AWB_DEATTACHED, "Update", additionalInfo.toString());
		log.debug("Mailbag" + " : " + "detachAwbInMailbag" + " Exiting");
	}
	/**
	 * @author A-8061
	 * @param carditEnquiryFilterVO
	 * @return
	 */
	public static String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO) {
		try {
			return constructDAO().findGrandTotals(carditEnquiryFilterVO);
		} catch (PersistenceException exception) {
			throw new SystemException("findGrandTotals");
		}
	}

	/**
	 * @author A-8672
	 * @return
	 * @throws SystemException
	 */
	public static void saveActualweight(MailbagVO mailbagVO) {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if (mailbag != null) {
				mailbag.setActualWeight(mailbagVO.getActualWeight().getValue().doubleValue());
				mailbag.setActualWeightDisplayValue(mailbagVO.getActualWeight().getDisplayValue().doubleValue());
				mailbag.setActualWeightDisplayUnit(mailbagVO.getActualWeight().getDisplayUnit().getName());
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/**
	 * Method		:	Mailbag.findDuplicateMailbag Added by 	:	A-7531 on 16-May-2019 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailBagId Parameters	:	@returMailbagHistoryMappern Return type	: 	ArrayList<MailbagVO>
	 * @throws PersistenceException
	 */
	public static ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId) {
		try {
			return constructDAO().findDuplicateMailbag(companyCode, mailBagId);
		} finally {
		}
	}
	/**
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		try {
			return constructDAO().findDeviationMailbags(mailbagEnquiryFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * Method		:	Mailbag.updateDespatchDate Added by 	:	A-8061 on 08-Apr-2020 Used for 	:	IASCB-45762 Parameters	:	@param mailbagVO Parameters	:	@param duplicatePeriod Parameters	:	@throws SystemException  Return type	: 	void
	 */
	public void updateDespatchDate(MailbagVO mailbagVO, int duplicatePeriod) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			ZonedDateTime currentDate = localDateUtil.getLocalDate(null, true);
			ZonedDateTime dspDate = localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), null);
			long seconds = ChronoUnit.SECONDS.between(currentDate, dspDate);
			long days = seconds / 86400000;
			if (((days) <= duplicatePeriod) || duplicatePeriod == 0) {
				mailbag.setDespatchDate(mailbagVO.getConsignmentDate());
				mailbag.setLastUpdatedTime(Timestamp.valueOf(localDateUtil.getLocalDate(null,  true).toLocalDateTime()));
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}
	/**
	 * @author A-8353
	 * @throws SystemException
	 */
	public static void updateOriginAndDestinationForMailbag(MailbagVO mailbagVO) {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			if (mailbag != null) {
				if (mailbagVO.isOriginUpdate()) {
					updateOriginOfMailbag(mailbagVO, mailbag);
				}
				if (mailbagVO.isDestinationUpdate()) {
					updateDestinationOfMailbag(mailbagVO, mailbag);
				}
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.auditMailBagUpdate(mailbagVO, MailbagAuditVO.MAILBAG_ORG_DEST_MODIFIED);
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @throws SystemException
	 */
	private static void updateDestinationOfMailbag(MailbagVO mailbagVO, Mailbag mailbag) {
		String officeOfExchange = null;
		boolean isDummyDest = false;
		isDummyDest = new MailController().isValidDestForCarditMissingDomesticMailbag(mailbag.getDestination());
		mailbagVO.setDestination(mailbag.getDestination());
		mailbag.setDestination(mailbagVO.getMailDestination());
		if (isDummyDest) {
			officeOfExchange = new MailController().findOfficeOfExchangeForCarditMissingDomMail(
					mailbagVO.getCompanyCode(), mailbagVO.getMailDestination());
			if (officeOfExchange != null) {
				mailbag.setDestinationOfficeOfExchange(officeOfExchange);
			}
		}
	}

	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param mailbag
	 * @throws SystemException
	 */
	private static void updateOriginOfMailbag(MailbagVO mailbagVO, Mailbag mailbag) {
		String officeOfExchange = null;
		boolean isDummyOrg = false;
		isDummyOrg = new MailController().isValidDestForCarditMissingDomesticMailbag(mailbag.getOrigin());
		mailbagVO.setOrigin(mailbag.getOrigin());
		mailbag.setOrigin(mailbagVO.getMailOrigin());
		if (isDummyOrg) {
			officeOfExchange = new MailController()
					.findOfficeOfExchangeForCarditMissingDomMail(mailbagVO.getCompanyCode(), mailbagVO.getMailOrigin());
			if (officeOfExchange != null) {
				mailbag.setOrginOfficeOfExchange(officeOfExchange);
			}
		}
	}

	/**
	 * Method		:	Mailbag.listCarditDsnDetails Added by 	:	A-8164 on 04-Sep-2019 Used for 	:	List Cardit DSN Details Parameters	:	@param dsnEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Page<DSNVO>
	 */
	public static Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO) {
		try {
			return constructDAO().listCarditDsnDetails(dsnEnquiryFilterVO);
		} finally {
		}
	}
	public static Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO) {
		try {
			return constructDAO().getFoundArrivalMailBags(mailArrivalVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	public void insertMailbagHistoryDetails(MailbagHistoryVO mailbagHistoryVO, Mailbag mailbag, String triggeringPoint ) throws SystemException {

		  mailbagHistoryVO.setCompanyCode(mailbag.getCompanyCode());
		  mailbagHistoryVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
		if(mailbagHistoryVO.getMailSource()!=null && mailbagHistoryVO.getMailSource().startsWith(MailConstantsVO.SCAN+":")){
			mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(MailConstantsVO.SCAN+":", ""));
		}else if(mailbagHistoryVO.getMailSource()!=null && MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagHistoryVO.getMailSource())){
			mailbagHistoryVO.setMailSource(MailConstantsVO.MAIL_SRC_RESDIT);
		}
		//Added by A-8527 for IASCB-58918 starts
		else if(mailbagHistoryVO.getMailSource()!=null && MailConstantsVO.MLD.equals(mailbagHistoryVO.getMailSource())){
			mailbagHistoryVO.setMailSource(MailConstantsVO.MLD+" "+mailbagHistoryVO.getMessageVersion());
		}
		//Added by A-8527 for IASCB-58918 ends
		else{
			mailbagHistoryVO.setMailSource(triggeringPoint);
			if(mailbagHistoryVO.getMailSource()!=null && mailbagHistoryVO.getMailSource().startsWith(MailConstantsVO.SCAN+":")) {
				mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(MailConstantsVO.SCAN + ":", ""));
			}
		}
		if (mailbagHistoryVO.isFomDeviationList()
				&& (MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagHistoryVO.getMailStatus())|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistoryVO.getMailStatus()))) {
			if (Objects.nonNull(mailbagHistoryVO.getAdditionalInfo())) {
				StringBuilder sb = new StringBuilder(mailbagHistoryVO.getAdditionalInfo());
				sb.append(",").append(ASSIGNED_FROM_DEV_PANEL);
				mailbagHistoryVO.setAdditionalInfo(sb.toString());
			} else {
				mailbagHistoryVO.setAdditionalInfo(ASSIGNED_FROM_DEV_PANEL);
			}
		}

		new MailbagHistory().persistMailbagHistory(mailbagHistoryVO);//A-9619 as part of IASCB-55196

	}

	@Override
	public BaseEntity getParentEntity() {
		return this;
	}
	@Override
	public String getBusinessIdAsString(){
		return this.mailIdr;
	}
	public static String additionalinfoForupdateReassignedMailbags(MailbagVO mailbagVO,ContainerVO toContainerVO){
		StringBuffer additionalInfo = new StringBuffer();
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();

		if(mailbagVO.isIsoffload()) {
			additionalInfo.append("Offloaded");
			if (mailbagVO.getScannedDate() != null) {
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
			}
			additionalInfo.append(" ").append(mailbagVO.getCarrierCode());
			additionalInfo.append(" ; at airport ");
			if (mailbagVO.getScannedPort() != null) {
				additionalInfo.append(mailbagVO.getScannedPort());
			} else {
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if (toContainerVO.getPou() != null) {
				additionalInfo.append(" ; POU - ").append(toContainerVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
					append(" ; Source -").append(mailbagVO.getMailSource());
			if (mailbagVO.isRemove() && mailbagVO.getOffloadedRemarks() != null) {
				additionalInfo.append(" ; Remarks - ").append(mailbagVO.getOffloadedRemarks());
			}
		} else{
			additionalInfo.append(MailbagAuditVO.MAILBAG_REASSIGN.toLowerCase());
			if(mailbagVO.getScannedDate()!=null){
				additionalInfo.append(" on ").append(mailbagVO.getScannedDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
			}
			if(!("-1".equals(toContainerVO.getFlightNumber()))){
				additionalInfo.append("; to ").append(toContainerVO.getCarrierCode()).append(toContainerVO.getFlightNumber());
				if(toContainerVO.getFlightDate()!=null){
					additionalInfo.append(" ").append(toContainerVO.getFlightDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
				}
			}else{
				additionalInfo.append(toContainerVO.getCarrierCode());
			}
			if(toContainerVO.getContainerNumber()!=null){
				additionalInfo.append(" ; to container ").append(toContainerVO.getContainerNumber());
			}
			additionalInfo.append(" ; at airport ");
			if(mailbagVO.getScannedPort()!=null){
				additionalInfo.append(mailbagVO.getScannedPort());
			}else{
				additionalInfo.append(logonAttributes.getAirportCode());
			}
			if(toContainerVO.getPou()!=null){
				additionalInfo.append(" ; POU -").append(toContainerVO.getPou());
			}
			additionalInfo.append(" ; by user ").append(logonAttributes.getUserId()).
					append(" ; Source -").append(mailbagVO.getMailSource());

		}
		return	additionalInfo.toString();

	}
	public static Collection<MailbagVO> generateMailTag(Collection<MailbagVO> mailbagVOs)throws SystemException{
		try{
			return constructDAO().findMailTagDetails(mailbagVOs);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getMessage(),ex);
		}
	}
	public static Collection<MailHandedOverVO> generateMailHandedOverReport(
			MailHandedOverFilterVO mailHandedOverFilterVO)
			throws SystemException{
		try{
			return constructDAO().generateMailHandedOverReport(mailHandedOverFilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public   MailbagHistoryVO constructFltReassignHistoryVO(
			MailbagVO mailbagVO,
			ContainerVO toDestinationVo) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(
				"THE CONTAINER VO in constructMailbagHistoryVOForFlight  IS >>>>>>>>>>>> {}",
				toDestinationVo);
		log.debug("THE MailBagVO VO  in constructMailbagHistoryVOForFlight IS >>>>>>>>>>>> {}",
						toDestinationVo);
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		// mailbagHistoryVO.setMailStatus(getLatestStatus());
		mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		mailbagHistoryVO.setScannedPort(mailbagVO.getScannedPort());
		mailbagHistoryVO.setScanUser(mailbagVO.getScannedUser());
		mailbagHistoryVO.setMailClass(mailbagVO.getMailClass());
		if (toDestinationVo != null) {
			mailbagHistoryVO.setPaBuiltFlag(toDestinationVo.getPaBuiltFlag());
			mailbagHistoryVO.setCarrierId(toDestinationVo.getCarrierId());
			mailbagHistoryVO.setFlightNumber(toDestinationVo.getFlightNumber());
			mailbagHistoryVO.setFlightSequenceNumber(toDestinationVo.getFlightSequenceNumber());
			mailbagHistoryVO.setContainerNumber(toDestinationVo.getContainerNumber());
			mailbagHistoryVO.setSegmentSerialNumber(toDestinationVo.getSegmentSerialNumber());
			if (mailbagVO.getScreeningUser() != null) {
				mailbagHistoryVO.setScreeningUser(mailbagVO.getScreeningUser());
			}
			if (mailbagVO.getScannedDate() != null) {
				mailbagHistoryVO.setScanDate(mailbagVO.getScannedDate());

			} else {

				mailbagHistoryVO.setScanDate(localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true));   //Chganged by A-5945 for ICRD-112229
			}
			mailbagHistoryVO.setCarrierCode(toDestinationVo.getCarrierCode());
			mailbagHistoryVO.setContainerType(toDestinationVo.getType());
			mailbagHistoryVO.setPou(toDestinationVo.getPou());
			mailbagHistoryVO.setFlightDate(toDestinationVo.getFlightDate());
			mailbagHistoryVO.setMailSource(toDestinationVo.getMailSource());////Added for ICRD-156218
			mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());

		} else {
			mailbagHistoryVO.setCarrierId(mailbagVO.getCarrierId());
			mailbagHistoryVO.setFlightNumber(mailbagVO.getFlightNumber());
			mailbagHistoryVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			mailbagHistoryVO.setContainerNumber(mailbagVO.getUldNumber());
			mailbagHistoryVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			if (mailbagVO.getScannedDate() != null) {
				mailbagHistoryVO.setScanDate(
						localDateUtil.getLocalDateTime(mailbagVO.getScannedDate(), mailbagVO.getScannedPort()));
			} else {
				mailbagHistoryVO.setScanDate(localDateUtil.getLocalDate(mailbagVO.getScannedPort(),true));
			}
			mailbagHistoryVO.setContainerType(mailbagVO.getContainerType());
			mailbagHistoryVO.setPou(mailbagVO.getPou());
			if (mailbagVO != null) {
				mailbagHistoryVO.setFlightDate(mailbagVO.getFlightDate());
				mailbagHistoryVO.setCarrierCode(mailbagVO.getCarrierCode());
				mailbagHistoryVO.setMailSource(mailbagVO.getMailSource());//Added for ICRD-156218
				mailbagHistoryVO.setMessageVersion(mailbagVO.getMessageVersion());
			}
    /*else {
				mailbagHistoryVO.setFlightDate(null);
				mailbagHistoryVO.setCarrierCode(null);
    }*/
		}
//mailbagHistoryVO.setScanDate(mailbagVO.getScannedDate());
		if (MailConstantsVO.FLAG_YES.equals(toDestinationVo.getPaBuiltFlag())) {
			mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
		}
		return mailbagHistoryVO;
	}

	public MailbagHistoryVO constructMailHistVOForReassign(
			OperationalFlightVO toFlightVO, ContainerVO containerVO, MailbagVO mailbag) {  MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		mailbagHistoryVO.setMailClass(mailbag.getMailClass());
		LoginProfile logonAttributes = ContextUtil.getInstance().
					callerLoginProfile();

		if(containerVO.isOffload()) {
			mailbagHistoryVO.setCarrierId(containerVO.getCarrierId());
			//mailbagHistoryVO.setFlightNumber(containerVO.getFlightNumber()); Commented by A-6991 for ICRD-209424
			//mailbagHistoryVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			//added as part of IASCB-65084
			mailbagHistoryVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			mailbagHistoryVO.setCarrierCode(containerVO.getCarrierCode());
			mailbagHistoryVO.setFlightDate(containerVO.getFlightDate());
			mailbagHistoryVO.setPou(containerVO.getPou());
			mailbagHistoryVO.setFlightNumber(containerVO.getFlightNumber());
		} else {
			mailbagHistoryVO.setCarrierId(toFlightVO.getCarrierId());
			mailbagHistoryVO.setFlightNumber(toFlightVO.getFlightNumber());
			mailbagHistoryVO.setFlightSequenceNumber(
					toFlightVO.getFlightSequenceNumber());
			mailbagHistoryVO.setSegmentSerialNumber(getSegmentSerialNumber());
			mailbagHistoryVO.setCarrierCode(toFlightVO.getCarrierCode());
			mailbagHistoryVO.setFlightDate(toFlightVO.getFlightDate());
			if(toFlightVO.getFlightSequenceNumber()>0){
				mailbagHistoryVO.setPou(toFlightVO.getPou());
			}

		}
		mailbagHistoryVO.setContainerType(containerVO.getType());
		if(containerVO.isUldTobarrow()){
			mailbagHistoryVO.setContainerType("B");
		}
		if(containerVO.isBarrowToUld()){
			mailbagHistoryVO.setContainerType("U");
		}
		mailbagHistoryVO.setScannedPort(containerVO.getAssignedPort());
		mailbagHistoryVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		//Added for icrd-94800 by A-4810
		if(containerVO.getScannedDate() != null) {
			mailbagHistoryVO.setScanDate(containerVO.getScannedDate());
		}
		else
		{
			mailbagHistoryVO.setScanDate(
					localDateUtil.getLocalDate(containerVO.getAssignedPort(), true));
		}
		mailbagHistoryVO.setContainerNumber(containerVO.getContainerNumber());

		if(containerVO.isOffload()) {
			mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_OFFLOADED);

		} else {
			if(containerVO.isHandoverReceived()){
				mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
				mailbagHistoryVO
						.setAdditionalInfo(MailConstantsVO.HNDOVR_CARRIER+containerVO.getCarrierCode());
			}
			else{
				mailbagHistoryVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			}
		}

		//mailbagHistoryVO.setPou(containerVO.getPou());    //Commented by A-6991 for ICRD-
		if(containerVO.getAssignedUser() != null){
			mailbagHistoryVO.setScanUser(containerVO.getAssignedUser());
		}else{

			mailbagHistoryVO.setScanUser(logonAttributes.getUserId());
		}

		mailbagHistoryVO.setMailSource(containerVO.getMailSource());//Added for ICRD-156218
		mailbagHistoryVO.setMessageVersion(containerVO.getMessageVersion());
		if(MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())){
			mailbagHistoryVO.setAdditionalInfo(MailConstantsVO.PA_BUILT_ADD_INFO);
		}
		mailbagHistoryVO.setScreeningUser(containerVO.getScreeningUser());//Added by A-9498 for IASCB-44577
		return mailbagHistoryVO;
	}




}
