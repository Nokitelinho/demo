package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistorynotes.SaveMailbagHistoryNotesFeature;
import com.ibsplc.neoicargo.mail.component.feature.savescreeningdetails.SaveScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeFilterModel;
import com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeModel;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DESTN_FLT;

/** 
 * @author a-5526
 */
@Qualifier("mailuploadController")
@Component("mailuploadController")
@Slf4j
public class MailUploadController extends MailController {

	@Autowired
	private MailOperationsMapper mailOperationsMapper;

	@Autowired
	private Quantities quantities;
	@Autowired
	private DocumentRepositoryProxy documentRepositoryProxy;
	@Autowired
	private SharedAirlineProxy sharedAirlineProxy;
	@Autowired
	private SharedULDProxy sharedULDProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private MailMRAProxy mailOperationsMRAProxy;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private MLDController mLDController;
	@Autowired
	private ValidateMailBagDetailsFeature validateMailBagDetailsFeature;
    @Autowired
	private MailController mailController;
	@Autowired
	private MailTrackingDefaultsBI mailMasterApi;
	@Autowired
	private KeyUtils keyUtils;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;

	@Autowired
	private MailAcceptance mailAcceptance;

	@Autowired
	private SaveScreeningDetailsFeature saveScreeningDetailsFeature;
	private static final String MODULENAME = "mail.operations";
	private static final String DEFAULT_STORAGEUNITFORMAIL = "mailtracking.defaults.defaultstorageunitformail";
	private static final String DEFAULT_AIRPORTCODEFORGHA = "mailtracking.defaults.defaultairportcodeforgha";
	private static final String DEFAULT_OFFLOADCODE = "mailtracking.defaults.defaultOffloadReasonForMail";
	private static final String STATUS_FLAG_UPDATE = "U";
	private static final String STATUS_FLAG_INSERT = "I";
	private static final String BULKARR = "BULK-ARR-SQ";
	private static final String DUMMY_TROLLEY_ID_FORMAT = "mailtracking.defaults.trolleynumberidformat";
	private static final String DEFAULTCOMMODITYCODE_SYSPARAM = "mailtracking.defaults.booking.commodity";
	private static final String OFL_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	private static final String DMG_RET_REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private static final String DEFAULT_OFFLOAREMARKS = "offloaded by GHA";
	private static final String NA = "NA";
	private static final String ULD_AS_BARROW = "mail.operations.allowuldasbarrow";
	private static final String FLAG_YES = "Y";
	private static final String UPL_MESSAGE_EXTRACTOR = "EXTRACT";
	private static final String ALLOWULDASBARROW = "mail.operations.allowuldasbarrow";
	public static final String IS_COTERMINUS_CONFIGURED = "mailtracking.defaults.iscoterminusconfigured";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String AUTOARRIVALFUNCTIONPOINTS = "mail.operations.autoarrivalfunctionpoints";
	private static final String AUTOARRIVALENABLEDPAS = "mail.operations.autoarrivalenabledPAs";
	private static final String AUTOARRIVALOFFSET = "mail.operations.autoarrivaloffset";
	private static final String TBA_VALIDATION_BYPASS = "mail.operations.ignoretobeactionedflightvalidation";
	private static final String SYSTEM_EXCEPTION_ERROR = "System Exception caught";
	ContainerAssignmentVO cntAsgmntVO;
	private Map<String, Long> mailSeqNum;
	private Map<String, String> exchangeOfficeMap;
	private static final String FUNPNTS_RTN = "RTN";
	private static final String FUNPNTS_DLV = "DLV";
	private static final String ULD_SYSPAR_NOTINSTOCK = "uld.defaults.errortype.notinstock";
	private static final String PABUILT_CONTAINERSAVE_ENABLED = "mail.operations.pabuiltsavefromcarditenabled";
	private static final String PERIODFOR_PABUILTMAILS = "mail.operations.noofdaysforpabuiltmailbags";
	private static final String PERIODFOR_PABUILTMAILS_INBOUND = "mail.operations.noofdaysforpabuiltmailbagsinbound";
	private static final String ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND = "mail.operations.ULDaircraftcompatibilityinMailInbound";
	private static final String ULD_SYSPAR_NOT_IN_ARL_STOCK = "uld.defaults.errortype.notinairlinestock";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL = "mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String SEPARATOR_UNDERSCORE = "_";
	private static final String ULD_SYSPAR_TRANFERWITHMAILBAGS = "mail.operations.transferallowedwithmailbags";
	private static final String STATUS_NOTOK = "NOTOK";
	private static final String STATUS_OK = "OK";
	private static final String UNDEFINED_ERROR_WEBSERVICES = "mailtracking.defaults.err.undefinedError";
	private static final String SYSPAR_IMPORT_HANDL_VALIDATION = "operations.flthandling.enableatdcapturevalildationforimporthandling";
	private static final String SHARED_ARPPAR_ONLARP = "operations.flthandling.isonlinehandledairport";
	private static final String MAIL_OPERATION_SERVICES = "mailOperationsFlowServices";
	private static final String FINDER_EXCEPTION_CAUGHT = "Finder Exception Caught";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	private static final String MLD_VERSION0 = "0";
	private static final String DESPATCH_SERIAL_NUMBER="0001";
	private static final String RECEPTACLE_SERIAL_NUMBER="001";
	private static final Pattern MAILBAG_PATTERN = Pattern.compile("\\p{Alnum}{29}", Pattern.UNICODE_CHARACTER_CLASS);

	private void performContainerAsSuchOperations(ScannedMailDetailsVO scannedMailDetailsVO,
			ScannedMailDetailsVO scannedDetailsVOForContainerTxn, MailbagVO mailbagVOToSave,
			ScannedMailDetailsVO scannedMailDetailsVO2, LoginProfile logonAttributes, String scannedPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			ForceAcceptanceException {
		log.info("" + "Perform Container AsSuch Operations :::::::" + " " + scannedMailDetailsVO);
		if (MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())
				|| MailConstantsVO.CONTAINER_STATUS_REASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())
				|| MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			try {
				new MailOperationsValidator().validateScannedMailDetails(scannedMailDetailsVO);
			} finally {
			}
		}
		if (MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			saveAcceptanceFromUpload(scannedDetailsVOForContainerTxn, logonAttributes);
			scannedMailDetailsVO.setContainerProcessPoint(null);
			validateContainerAndUpdateVO(mailbagVOToSave, scannedMailDetailsVO, logonAttributes, scannedPort);
		}
		if (MailConstantsVO.CONTAINER_STATUS_REASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn
					.setScannedContainerDetails(scannedMailDetailsVO.getScannedContainerDetails());
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			savereassignFromUpload(scannedDetailsVOForContainerTxn, logonAttributes);
			if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
				scannedMailDetailsVO.setMailDetails(null);
			}
			scannedMailDetailsVO.setContainerProcessPoint(null);
			validateContainerAndUpdateVO(mailbagVOToSave, scannedMailDetailsVO, logonAttributes, scannedPort);
		}
		if (MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			saveTransferFromUpload(scannedDetailsVOForContainerTxn, logonAttributes);
			if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
				scannedMailDetailsVO.setMailDetails(null);
			}
			if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
				scannedMailDetailsVO.setMailDetails(null);
			}
			scannedMailDetailsVO.setContainerProcessPoint(null);
			validateContainerAndUpdateVO(mailbagVOToSave, scannedMailDetailsVO, logonAttributes, scannedPort);
		}
	}

	private void setDamageAndOffloadDetails(MailbagVO mailbagVOToSave, MailUploadVO uploadedMaibagVO) {
		if (("WS".equals(uploadedMaibagVO.getMailSource())
				|| MailConstantsVO.MLD.equals(uploadedMaibagVO.getMailSource()))
				&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(uploadedMaibagVO.getScanType())) {
			mailbagVOToSave.setFinalDestination(uploadedMaibagVO.getDestination());
		}
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(uploadedMaibagVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(uploadedMaibagVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(uploadedMaibagVO.getScanType())) {
			mailbagVOToSave.setMailCompanyCode(uploadedMaibagVO.getMailCompanyCode());
		}
		if (uploadedMaibagVO.isDeliverd()) {
			mailbagVOToSave.setDeliveredFlag(MailConstantsVO.FLAG_YES);
			mailbagVOToSave.setDeliveryNeeded(true);
		}
		mailbagVOToSave.setOffloadedRemarks(uploadedMaibagVO.getRemarks());
		mailbagVOToSave.setOffloadedReason(uploadedMaibagVO.getOffloadReason());
		mailbagVOToSave.setReturnedReason(uploadedMaibagVO.getDamageCode());
		if (MailConstantsVO.MLD.equals(uploadedMaibagVO.getMailSource())
				&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(uploadedMaibagVO.getScanType())) {
			mailbagVOToSave.setReturnedReason(uploadedMaibagVO.getReturnCode());
		}
		mailbagVOToSave.setReturnedRemarks(uploadedMaibagVO.getDamageRemarks());
		if (!"".equals(uploadedMaibagVO.getDamageCode()) && uploadedMaibagVO.getDamageCode() != null)
			mailbagVOToSave.setDamageFlag("Y");
	}

	private void updateFlightDetailsForDamageCapture(MailbagVO mailBagVOFromUpload, Mailbag mailBag,
			ContainerAssignmentVO containerAssignmentVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		mailBagVOFromUpload.setCarrierId(mailBag.getCarrierId());
		mailBagVOFromUpload.setFlightNumber(mailBag.getFlightNumber());
		mailBagVOFromUpload.setFlightSequenceNumber(mailBag.getFlightSequenceNumber());
		mailBagVOFromUpload.setSegmentSerialNumber(mailBag.getSegmentSerialNumber());
		mailBagVOFromUpload.setUldNumber(mailBag.getUldNumber());
		mailBagVOFromUpload.setOperationalStatus(mailBag.getOperationalStatus());
		if (mailBag.getPou() != null) {
			mailBagVOFromUpload.setPou(mailBag.getPou());
		} else {
			mailBagVOFromUpload.setPou(containerAssignmentVO.getDestination());
			mailBagVOFromUpload.setFinalDestination(containerAssignmentVO.getDestination());
		}
		if (MailConstantsVO.DESTN_FLT == mailBag.getFlightSequenceNumber()) {
			mailBagVOFromUpload.setPou(containerAssignmentVO.getDestination());
			mailBagVOFromUpload.setFinalDestination(containerAssignmentVO.getDestination());
		}
		if (MailConstantsVO.BULK_TYPE.equals(mailBag.getContainerType())
				&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailBag.getLatestStatus())) {
			mailBagVOFromUpload.setPou(mailBag.getScannedPort());
		}
		if (containerAssignmentVO != null) {
			ContainerPK containerPK = populateContainerPK(containerAssignmentVO);
			Container container = null;
			try {
				container = Container.find(containerPK);
			} catch (FinderException e) {
				container = null;
			}
			if (container != null && container.getPaBuiltFlag() != null
					&& MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())) {
				mailBagVOFromUpload.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
			}
		}
	}

	private ContainerPK populateContainerPK(ContainerAssignmentVO containerAssignmentVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(containerAssignmentVO.getAirportCode());
		containerPK.setFlightNumber(containerAssignmentVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		containerPK.setCarrierId(containerAssignmentVO.getCarrierId());
		containerPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
		containerPK.setContainerNumber(containerAssignmentVO.getContainerNumber());
		return containerPK;
	}

	private void validateContainerAndUpdateVO(MailbagVO mailbagVOToSave, ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, String scannedPort)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.MRD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedPort != null) {
				airportCode = scannedPort;
			}
		}
		boolean isReassignforEXP = false;
		ContainerAssignmentVO containerAssignmentVO = null;
		if (scannedMailDetailsVO.getProcessPoint() != null
				&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint()))
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if ((scannedMailDetailsVO.getContainerNumber() == null
					|| scannedMailDetailsVO.getContainerNumber().isEmpty())
					&& (MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()))) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN,
						"Container number is mandatory for acceptance", scannedMailDetailsVO);
			}
			if (scannedMailDetailsVO.getContainerNumber() != null) {
				try {
					containerAssignmentVO = mailController
							.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
					this.cntAsgmntVO = containerAssignmentVO;
					if (containerAssignmentVO != null && (MailConstantsVO.MAIL_STATUS_EXPORT
							.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
							&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
							&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag()))) {
						containerAssignmentVO = null;
					}
					if (containerAssignmentVO != null
							&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
						ContainerVO containerVO = new ContainerVO();
						containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
						containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
						containerVO.setAssignedPort(scannedMailDetailsVO.getAirportCode());
						containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
					}
				} finally {
				}
				if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
						&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						&& containerAssignmentVO != null && !airportCode.equals(containerAssignmentVO.getAirportCode())
						&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
					if (containerAssignmentVO.getPou() != null
							&& (!containerAssignmentVO.getPou().equals(airportCode))) {
						throw new MailMLDBusniessException(MailMLDBusniessException.CONTAINER_NOT_AVAILABLE);
					}
				}
			}
			if ("WS".equals(scannedMailDetailsVO.getMailSource())
					&& MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
					&& "-1".equals(scannedMailDetailsVO.getFlightNumber()) && containerAssignmentVO != null
					&& "U".equals(containerAssignmentVO.getContainerType())
					&& "-1".equals(containerAssignmentVO.getFlightNumber())
					&& scannedMailDetailsVO.getScannedContainerDetails() == null) {
				throw new MailHHTBusniessException(MailHHTBusniessException.CANNOT_ASSIGN_CONTAINER);
			}
			if (!MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
				if (containerAssignmentVO != null
						&& ((scannedMailDetailsVO.getContainerType() != null && !containerAssignmentVO
								.getContainerType().equals(scannedMailDetailsVO.getContainerType())))) {
					if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
						StringBuilder errorString = new StringBuilder();
						errorString.append("This container is already present in the system with containertype as  ");
						if (MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
							errorString.append("Bulk");
						} else {
							errorString.append("ULD");
						}
						String error = errorString.toString();
						constructAndRaiseException(MailMLDBusniessException.WRONG_CONTAINER_TYPE_GIVEN, error,
								scannedMailDetailsVO);
					}
				}
			}
			if (containerAssignmentVO != null && ((scannedMailDetailsVO.getContainerType() != null
					&& containerAssignmentVO.getContainerType().equals(scannedMailDetailsVO.getContainerType()))
					|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
					|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
					|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()))) {
				if (!"WS".equals(scannedMailDetailsVO.getMailSource())) {
					if (!MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
						try {
							new MailOperationsValidator().doContainerDestinationAndPouValidations(
									scannedMailDetailsVO, containerAssignmentVO);
						} finally {
						}
					}
				}
				if (MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())) {
					if (!containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode())
							&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
						constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
								"Container is not available at this airport", scannedMailDetailsVO);
					}
				}
				if ("WS".equals(scannedMailDetailsVO.getMailSource())
						&& (containerAssignmentVO.getFlightNumber() != null && containerAssignmentVO.getFlightNumber()
								.equals(scannedMailDetailsVO.getFlightNumber()))
						&& (MailConstantsVO.DESTN_FLT == containerAssignmentVO.getFlightSequenceNumber())
						&& ("ACP".equals(scannedMailDetailsVO.getProcessPoint()))
						&& containerAssignmentVO.getAirportCode().equals(airportCode)
						&& scannedMailDetailsVO.isExpReassign()) {
					isReassignforEXP = true;
					Collection<ContainerVO> containerVOsToReassign = new ArrayList<ContainerVO>();
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
					containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
					containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
					containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
					containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
					containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
					containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
					containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					containerVO.setPou(scannedMailDetailsVO.getDestination());
					containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
					containerVO.setAssignedPort(airportCode);
					if (mailbagVOToSave != null && mailbagVOToSave.getScannedUser() != null
							&& mailbagVOToSave.getScannedUser().trim().length() > 0) {
						containerVO.setAssignedUser(mailbagVOToSave.getScannedUser());
					} else {
						containerVO.setAssignedUser(logonAttributes.getUserId());
					}
					if (mailbagVOToSave != null && mailbagVOToSave.getScannedDate() != null) {
						containerVO.setScannedDate(mailbagVOToSave.getScannedDate());
					}
					containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
					containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					containerVO.setType(containerAssignmentVO.getContainerType());
					containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
					containerVO.setTypeCodeListResponsibleAgency(scannedMailDetailsVO.getMailSource());
					containerVOsToReassign.add(containerVO);
					scannedMailDetailsVO.setScannedContainerDetails(containerVOsToReassign);
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_REASSIGN);
				}
				if (("-1".equals(scannedMailDetailsVO.getFlightNumber())
						&& "-1".equals(containerAssignmentVO.getFlightNumber()))
						&& (containerAssignmentVO.getDestination() != null && !containerAssignmentVO.getDestination()
								.equals(scannedMailDetailsVO.getDestination()))) {
					isReassignforEXP = true;
				}
				if (((containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
						&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
								.getFlightSequenceNumber()
						&& containerAssignmentVO.getAirportCode().equals(scannedPort))
						|| ((scannedMailDetailsVO.getFlightNumber() != null
								&& scannedMailDetailsVO.getFlightNumber().trim().length() == 0)
								&& (scannedMailDetailsVO.getCarrierCode() != null
										&& scannedMailDetailsVO.getCarrierCode().trim().length() == 0)))
						&& !isReassignforEXP) {
					if (MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
							&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())
							&& scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
								MailHHTBusniessException.CONTAINER_ARRIVED_EXCEPTION, scannedMailDetailsVO);
					} else {
						updateDetailsWithContainerAssignmentInfo(scannedMailDetailsVO, mailbagVOToSave,
								containerAssignmentVO, scannedPort);
					}
				} else if (!containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
						|| containerAssignmentVO.getFlightSequenceNumber() != scannedMailDetailsVO
								.getFlightSequenceNumber()
						|| (containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
								&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
										.getFlightSequenceNumber()
								&& containerAssignmentVO.getSegmentSerialNumber() != scannedMailDetailsVO
										.getSegmentSerialNumber())) {
					if (((MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
							&& containerAssignmentVO.getFlightSequenceNumber() > 0
							&& containerAssignmentVO.getTransitFlag() != null
							&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())))
							|| MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
									&& !containerAssignmentVO.getAirportCode()
											.equals(scannedMailDetailsVO.getAirportCode())) {
						scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
						if (mailbagVOToSave.getPaBuiltFlag() != null
								&& mailbagVOToSave.getPaBuiltFlag().trim().length() != 0
								&& mailbagVOToSave.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
							scannedMailDetailsVO.setNewContainer(MailConstantsVO.FLAG_YES);
						}
					} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
							&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
							&& (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())
									|| MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getArrivalFlag()))
							&& containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode())) {
						scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
					} else if (containerAssignmentVO.getAirportCode().equals(airportCode)
							&& (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_NO
									.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))) {
						Collection<ContainerVO> containerVOsToReassign = new ArrayList<ContainerVO>();
						ContainerVO containerVO = new ContainerVO();
						containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
						containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
						containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
						containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
						containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
						containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
						containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
						containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
						containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
						if (scannedMailDetailsVO.getPou() != null) {
							containerVO.setPou(scannedMailDetailsVO.getPou());
						} else {
							containerVO.setPou(containerAssignmentVO.getPou());
						}
						containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
						if (MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
								&& containerAssignmentVO.getFlightSequenceNumber() <= 0) {
							containerVO.setFinalDestination(containerAssignmentVO.getDestination());
						}
						if ("WS".equals(scannedMailDetailsVO.getMailSource())) {
							if ((MailConstantsVO.MAIL_STATUS_REASSIGNMAIL
									.equals(scannedMailDetailsVO.getProcessPoint()))
									&& containerVO.getFinalDestination() != null) {
								containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
							}
						}
						containerVO.setAssignedPort(airportCode);
						if (mailbagVOToSave != null && mailbagVOToSave.getScannedUser() != null
								&& mailbagVOToSave.getScannedUser().trim().length() > 0) {
							containerVO.setAssignedUser(mailbagVOToSave.getScannedUser());
						} else {
							containerVO.setAssignedUser(logonAttributes.getUserId());
						}
						if (mailbagVOToSave != null && mailbagVOToSave.getScannedDate() != null) {
							containerVO.setScannedDate(mailbagVOToSave.getScannedDate());
						}
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
						containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						containerVO.setType(containerAssignmentVO.getContainerType());
						containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
						containerVO.setTypeCodeListResponsibleAgency(scannedMailDetailsVO.getMailSource());
						containerVO.setMailSource(scannedMailDetailsVO.getMailSource());
						containerVOsToReassign.add(containerVO);
						scannedMailDetailsVO.setScannedContainerDetails(containerVOsToReassign);
						scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_REASSIGN);
					} else if ((containerAssignmentVO.getArrivalFlag() != null
							&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
							&& containerAssignmentVO.getPou().equals(scannedMailDetailsVO.getAirportCode())
							&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
						Collection<ContainerVO> containerVOsToTransfer = new ArrayList<ContainerVO>();
						ContainerVO containerVO = new ContainerVO();
						containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
						containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
						containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
						containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
						containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
						containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
						containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
						containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
						containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
						containerVO.setPou(containerAssignmentVO.getPou());
						containerVO.setFinalDestination(containerAssignmentVO.getDestination());
						containerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
						if (mailbagVOToSave != null && mailbagVOToSave.getScannedUser() != null
								&& mailbagVOToSave.getScannedUser().trim().length() > 0) {
							containerVO.setAssignedUser(mailbagVOToSave.getScannedUser());
						} else {
							containerVO.setAssignedUser(logonAttributes.getUserId());
						}
						if (mailbagVOToSave != null && mailbagVOToSave.getScannedDate() != null) {
							containerVO.setScannedDate(mailbagVOToSave.getScannedDate());
						}
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
						containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						containerVO.setType(containerAssignmentVO.getContainerType());
						containerVO.setAcceptanceFlag("Y");
						containerVOsToTransfer.add(containerVO);
						scannedMailDetailsVO.setScannedContainerDetails(containerVOsToTransfer);
						if ((containerAssignmentVO.getArrivalFlag() != null
								&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
								&& !containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
								|| (("-1").equals(scannedMailDetailsVO.getFlightNumber())
										&& (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_NO
												.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
										&& !containerAssignmentVO.getContainerType()
												.equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
										&& !(containerAssignmentVO.getDestination()
												.equals(scannedMailDetailsVO.getDestination())))) {
							scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_TRANSFER);
							if ("WS".equals(scannedMailDetailsVO.getMailSource())
									&& scannedMailDetailsVO.getPou() != null) {
								containerVO.setPou(scannedMailDetailsVO.getPou());
							}
							containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
							containerVO.setMailSource(scannedMailDetailsVO.getMailSource());
						} else if ((containerAssignmentVO.getArrivalFlag() != null
								&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
								&& !containerAssignmentVO.getPou().equalsIgnoreCase(scannedPort)) {
							scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
						}
					}
				}
				if (containerAssignmentVO.getContainerType().equals(MailConstantsVO.BULK_TYPE)) {
					mailbagVOToSave.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
				}
			} else if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
					|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
					|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_EXPORT)) {
				scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
				if (mailbagVOToSave.getPaBuiltFlag() != null && mailbagVOToSave.getPaBuiltFlag().trim().length() != 0
						&& mailbagVOToSave.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
					scannedMailDetailsVO.setNewContainer(MailConstantsVO.FLAG_YES);
				}
			}
		}
	}

	private void setNonExistedContainerDetails(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerDetailsVO containerDetailsVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		} else if (scannedMailDetailsVO.getProcessPoint() != null
				&& !MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
				&& !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		}
		if (containerAssignmentVO == null
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
				&& scannedMailDetailsVO.getTransferFromCarrier() != null
				&& scannedMailDetailsVO.getTransferFromCarrier().trim().length() > 0
				&& !logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getTransferFromCarrier())
				&& (scannedMailDetailsVO.getTransferFrmFlightNum() == null
						|| scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() == 0)) {
			containerDetailsVO.setFoundTransfer(true);
		}
	}

	private ContainerAssignmentVO setValidatedContainerDetails(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		try {
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
					&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
							|| (scannedMailDetailsVO.getMailDetails() == null
									|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
				containerAssignmentVO = this.cntAsgmntVO == null
						? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
						: this.cntAsgmntVO;
				this.cntAsgmntVO = containerAssignmentVO;
			} else {
				containerAssignmentVO = ((this.cntAsgmntVO == null) || (MailConstantsVO.CONTAINER_STATUS_PREASSIGN
						.equals(scannedMailDetailsVO.getContainerProcessPoint())
						&& MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
						&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()))
						|| !(scannedMailDetailsVO.getContainerNumber() != null && scannedMailDetailsVO
								.getContainerNumber().equals(this.cntAsgmntVO.getContainerNumber())))
										? findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber())
										: this.cntAsgmntVO;
			}
		} finally {
		}
		if (containerAssignmentVO != null
				&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
			ContainerVO containerVO = new ContainerVO();
			containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			containerVO.setAssignedPort(scannedMailDetailsVO.getAirportCode());
			containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
		}
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
		containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		if (containerAssignmentVO != null && containerAssignmentVO.getUldFulIndFlag() != null) {
			containerDetailsVO.setUldFulIndFlag(containerAssignmentVO.getUldFulIndFlag());
		}
		if ("WS".equals(scannedMailDetailsVO.getMailSource())
				&& MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		}
		if (containerAssignmentVO != null) {
			if (scannedMailDetailsVO.getProcessPoint() != null
					&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()))) {
				updateContainerDetailsForAceptnace(scannedMailDetailsVO, containerAssignmentVO, containerDetailsVO);
			} else if (scannedMailDetailsVO.getProcessPoint() != null
					&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
				updateContainerDetailsForArrival(scannedMailDetailsVO, containerAssignmentVO, containerDetailsVO);
			} else if (scannedMailDetailsVO.getProcessPoint() != null
					&& MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
				updateContainerDetailsForTransfer(scannedMailDetailsVO, containerAssignmentVO, containerDetailsVO);
			} else if (scannedMailDetailsVO.getProcessPoint() != null
					&& MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
				updateContainerDetailsForReassign(scannedMailDetailsVO, containerAssignmentVO, containerDetailsVO);
			}
		} else {
			setNonExistedContainerDetails(scannedMailDetailsVO, containerAssignmentVO, containerDetailsVO);
		}
		scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
		return containerAssignmentVO;
	}

	private void updateContainerDetailsForReassign(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerDetailsVO containerDetailsVO) {
		if (scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
				&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
			containerDetailsVO.setPou(containerAssignmentVO.getPou());
			containerDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
			containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
			containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
			containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
			if (containerDetailsVO.getSegmentSerialNumber() < 1) {
				containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
			}
			containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
			containerDetailsVO.setDestination(containerAssignmentVO.getDestination());
		}
	}

	private void updateContainerDetailsForTransfer(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerDetailsVO containerDetailsVO) {
		if (scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
				&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
			containerDetailsVO.setPou(containerAssignmentVO.getPou());
			containerDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
			containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getToFlightNumber());
			containerDetailsVO.setCarrierId(scannedMailDetailsVO.getToCarrierid());
			containerDetailsVO.setFlightDate(scannedMailDetailsVO.getToFlightDate());
			containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getToFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getToLegSerialNumber());
			if (containerDetailsVO.getLegSerialNumber() < 1) {
				containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
			}
			if (containerDetailsVO.getSegmentSerialNumber() < 1) {
				containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
			}
			containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
			containerDetailsVO.setDestination(containerAssignmentVO.getDestination());
		}
	}

	private void updateContainerDetailsForArrival(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerDetailsVO containerDetailsVO) {
		if (scannedMailDetailsVO.getContainerType() == null
				&& MailConstantsVO.MLD.equalsIgnoreCase(scannedMailDetailsVO.getMailSource())) {
			scannedMailDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
		}
		if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
		} else {
			MailbagVO mailbagVO = null;
			if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
				mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
			}
			Mailbag mailBag = null;
			Collection<MailbagVO> mails = scannedMailDetailsVO.getMailDetails();
			if (mails != null && mails.size() > 0) {
				for (MailbagVO mail : mails) {
					if (mail.getMailbagId().trim().length() == 29) {
						MailbagPK mailbagPk = new MailbagPK();
						mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
						long sequencenum = 0;
						try {
							sequencenum = mail.getMailSequenceNumber() == 0
									? Mailbag.findMailBagSequenceNumberFromMailIdr(mail.getMailbagId(),
											mail.getCompanyCode())
									: mail.getMailSequenceNumber();
						} finally {
						}
						mailbagPk.setMailSequenceNumber(sequencenum);
						try {
							mailBag = Mailbag.find(mailbagPk);
						} catch (FinderException e) {
							mailBag = null;
						}
					}
				}
			}
			if (scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
					&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
					&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
					&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
					&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()
					&& scannedMailDetailsVO.getAirportCode().equals(containerAssignmentVO.getPou())) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
				containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
				if (containerDetailsVO.getSegmentSerialNumber() < 1) {
					containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
					scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				}
				containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
				containerDetailsVO.setDestination(containerAssignmentVO.getDestination());
			} else if (mailBag != null && !(MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus()))
					&& mailBag.getUldNumber() != null
					&& mailBag.getUldNumber().equals(containerDetailsVO.getContainerNumber())
					&& !scannedMailDetailsVO.getAirportCode().equals(containerAssignmentVO.getPou())
					&& (!containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
							|| scannedMailDetailsVO.getFlightSequenceNumber() != containerAssignmentVO
									.getFlightSequenceNumber())
					|| (mailBag != null
							&& mailBag.getSegmentSerialNumber() != containerAssignmentVO.getSegmentSerialNumber())) {
				ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
				mailhistories = (ArrayList<MailbagHistoryVO>) Mailbag
						.findMailbagHistories(scannedMailDetailsVO.getCompanyCode(), mailbagVO.getMailbagId(), 0l);
				if (mailhistories != null && mailhistories.size() > 0) {
					for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
						if ((mailbaghistoryvo.getFlightNumber() != null
								&& mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber()))
								&& mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()
								&& !MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())) {
							ContainerVO containerVO = new ContainerVO();
							containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
							containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
							containerVO.setAssignedPort(mailbaghistoryvo.getScannedPort());
							containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
						}
					}
				}
				containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
				containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
				if ((containerAssignmentVO.getFlightNumber() != null
						&& !containerAssignmentVO.getFlightNumber().equals(mailbagVO.getFlightNumber()))
						|| containerAssignmentVO.getFlightSequenceNumber() != containerAssignmentVO
								.getFlightSequenceNumber()) {
					containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
				} else {
					if (containerDetailsVO.getSegmentSerialNumber() < 1) {
						containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
						scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
					}
					containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
					containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					scannedMailDetailsVO.setPol(containerAssignmentVO.getAirportCode());
					scannedMailDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
					scannedMailDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					mailbagVO.setPol(scannedMailDetailsVO.getPol());
					mailbagVO.setPou(scannedMailDetailsVO.getPou());
					mailbagVO.setOperationalFlag(STATUS_FLAG_UPDATE);
					mailbagVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
				}
			} else {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
				containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			}
		}
	}

	private void updateContainerDetailsForAceptnace(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerDetailsVO containerDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
				&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()) {
			if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getAcceptanceFlag())) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			} else {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			}
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setRemarks(containerAssignmentVO.getRemark());
			if (containerDetailsVO.getSegmentSerialNumber() < 1) {
				containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
			}
		} else if (MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
				&& scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
			if (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
				containerDetailsVO.setRemarks(containerAssignmentVO.getRemark());
				ContainerPK containerPK = populateContainerPK(scannedMailDetailsVO);
				Container container = null;
				try {
					container = Container.find(containerPK);
					if (container != null) {
						containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
					}
				} catch (FinderException finderException) {
					containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
				}
				containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
			}
		} else {
			if (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())
					|| MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getReleasedFlag())) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
				containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
			}
		}
	}

	private void updateDetailsWithContainerAssignmentInfo(ScannedMailDetailsVO scannedMailDetailsVO,
			MailbagVO mailbagVOToSave, ContainerAssignmentVO containerAssignmentVO, String scannedPort) {
		scannedMailDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
		scannedMailDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
		scannedMailDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
		scannedMailDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
		scannedMailDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
		scannedMailDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		scannedMailDetailsVO.setPou(containerAssignmentVO.getPou());
		if ("WS".equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getDestination() != null
					&& scannedMailDetailsVO.getDestination().trim().length() > 0
					&& !scannedPort.equals(scannedMailDetailsVO.getDestination())) {
				scannedMailDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
			} else {
				scannedMailDetailsVO.setDestination(containerAssignmentVO.getDestination());
			}
		} else {
			scannedMailDetailsVO.setDestination(containerAssignmentVO.getDestination());
		}
		scannedMailDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
		mailbagVOToSave.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
		mailbagVOToSave.setContainerType(containerAssignmentVO.getContainerType());
		mailbagVOToSave.setFlightNumber(containerAssignmentVO.getFlightNumber());
		mailbagVOToSave.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		mailbagVOToSave.setFlightDate(containerAssignmentVO.getFlightDate());
		mailbagVOToSave.setCarrierId(containerAssignmentVO.getCarrierId());
		mailbagVOToSave.setCarrierCode(containerAssignmentVO.getCarrierCode());
		mailbagVOToSave.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
		mailbagVOToSave.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		mailbagVOToSave.setContainerNumber(containerAssignmentVO.getContainerNumber());
		mailbagVOToSave.setContainerType(containerAssignmentVO.getContainerType());
		mailbagVOToSave.setCompanyCode(containerAssignmentVO.getCompanyCode());
		mailbagVOToSave.setPol(scannedMailDetailsVO.getAirportCode());
	}

	private void updateCarrierAcceptanceDetails(ScannedMailDetailsVO scannedMailDetailsVO, MailbagVO mailbagVOToSave)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getProcessPoint() != null
				&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint()))
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (!MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
					&& (scannedMailDetailsVO.getFlightNumber() == null
							|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0
							|| MailConstantsVO.DESTN_FLT_STR.equals(scannedMailDetailsVO.getFlightNumber()))) {
				String DEST_FLT = "-1";
				scannedMailDetailsVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				scannedMailDetailsVO.setFlightNumber(DEST_FLT);
				scannedMailDetailsVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
				mailbagVOToSave.setFlightNumber(DEST_FLT);
				mailbagVOToSave.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				mailbagVOToSave.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
				AirlineValidationVO airlineValidationVO = null;
				try {
					airlineValidationVO = new MailOperationsValidator().validateCarrierCode(
							scannedMailDetailsVO.getCarrierCode(), scannedMailDetailsVO.getCompanyCode());
					if (airlineValidationVO == null) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
								MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
					} else {
						scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					}
				} finally {
				}
			}
		}
	}

	private FlightFilterVO createFlightFilterVO(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			String airportCode) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		} else {
			if ((scannedMailDetailsVO.getProcessPoint() == null
					|| scannedMailDetailsVO.getProcessPoint().trim().length() == 0)
					&& scannedMailDetailsVO.getOperationType() != null) {
				flightFilterVO.setDirection(scannedMailDetailsVO.getOperationType());
			} else {
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
			}
		}
		flightFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(scannedMailDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		if (scannedMailDetailsVO.getFlightSequenceNumber() > 0 && scannedMailDetailsVO.getOperationType() != null
				&& scannedMailDetailsVO.getOperationType().trim().length() > 0
				&& scannedMailDetailsVO.getOperationType().equals(MailConstantsVO.OPERATION_INBOUND)) {
			flightFilterVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		} else {
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(scannedMailDetailsVO.getFlightDate()));
		}
		flightFilterVO.setAirportCode(airportCode);
		flightFilterVO.setStation(airportCode);
		return flightFilterVO;
	}

	private void setContainerDetails(MailbagVO mailbagVOToSave, MailUploadVO uploadedMaibagVO,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		mailbagVOToSave.setContainerNumber(uploadedMaibagVO.getContainerNumber());
		if (uploadedMaibagVO.getContainerNumber() != null && !uploadedMaibagVO.getContainerNumber().isEmpty()) {
			mailbagVOToSave.setContainerType(uploadedMaibagVO.getContainerType());
		}
		if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			mailbagVOToSave.setContainerNumber(uploadedMaibagVO.getToContainer());
		}
		if (uploadedMaibagVO.getMailTag() != null && uploadedMaibagVO.getMailTag().length() > 0) {
			mailbagVOToSave.setPaBuiltFlag(uploadedMaibagVO.getPaCode());
			scannedMailDetailsVO.setIsContainerPabuilt(uploadedMaibagVO.getPaCode());
		} else {
			scannedMailDetailsVO.setIsContainerPabuilt(uploadedMaibagVO.getPaCode());
		}
	}

	private void setPolPouDestinationDetailsForAndroid(ScannedMailDetailsVO scannedMailDetailsVO,
			MailUploadVO uploadedMaibagVO, String airportCode, MailbagVO mailbagVO) {
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType())
				&& (uploadedMaibagVO.getFlightNumber() == null || "".equals(uploadedMaibagVO.getFlightNumber()))) {
			scannedMailDetailsVO.setPou("");
		} else {
			scannedMailDetailsVO.setPou(uploadedMaibagVO.getToPOU());
		}
		if (!MailConstantsVO.MLD.equalsIgnoreCase(scannedMailDetailsVO.getMailSource())) {
			if ((MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(uploadedMaibagVO.getScanType())
					|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(uploadedMaibagVO.getScanType())
					|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType()))) {
				scannedMailDetailsVO.setPol(airportCode);
			} else if (uploadedMaibagVO.getContainerPol() == null) {
				scannedMailDetailsVO.setPol(mailbagVO.getOrigin());
			} else {
				scannedMailDetailsVO.setPol(uploadedMaibagVO.getContainerPol());
			}
		}
		if (uploadedMaibagVO.getDestination() != null) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getDestination());
		} else {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getContainerPOU());
		}
		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(uploadedMaibagVO.getScanType())) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getToDestination());
		}
	}

	private void setContainerDetails(ScannedMailDetailsVO scannedMailDetailsVO, MailUploadVO uploadedMaibagVO) {
		scannedMailDetailsVO.setContainerNumber(uploadedMaibagVO.getContainerNumber());
		if (uploadedMaibagVO.getContainerNumber() != null && !uploadedMaibagVO.getContainerNumber().isEmpty()) {
			scannedMailDetailsVO.setContainerType(uploadedMaibagVO.getContainerType());
		}
		scannedMailDetailsVO.setScannedUser(uploadedMaibagVO.getScanUser());
		if (uploadedMaibagVO.getToContainer() != null
				&& (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint()))) {
			scannedMailDetailsVO.setContainerNumber(uploadedMaibagVO.getToContainer());
		}
		if (uploadedMaibagVO.getPaCode() != null && !uploadedMaibagVO.getPaCode().isEmpty()) {
			scannedMailDetailsVO.setIsContainerPabuilt(uploadedMaibagVO.getPaCode());
		}
		if (uploadedMaibagVO.getUldActualWeight() != null) {
			scannedMailDetailsVO.setActualUldWeight(uploadedMaibagVO.getUldActualWeight());
		}
		if (uploadedMaibagVO.getTransactionLevel() != null && "U".equals(uploadedMaibagVO.getTransactionLevel())) {
			scannedMailDetailsVO.setTransactionLevel(uploadedMaibagVO.getTransactionLevel());
		}
	}

	private void setFlightDetails(ScannedMailDetailsVO scannedMailDetailsVO, MailUploadVO uploadedMaibagVO) {
		scannedMailDetailsVO.setCarrierCode(uploadedMaibagVO.getCarrierCode());
		scannedMailDetailsVO.setFlightNumber(uploadedMaibagVO.getFlightNumber());
		scannedMailDetailsVO.setFlightDate(uploadedMaibagVO.getFlightDate());
		scannedMailDetailsVO.setFlightSequenceNumber(uploadedMaibagVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setLegSerialNumber(uploadedMaibagVO.getSerialNumber());
		scannedMailDetailsVO.setCarrierId(uploadedMaibagVO.getCarrierId());
		if (uploadedMaibagVO.getTransferFrmFlightNum() != null)
			scannedMailDetailsVO.setTransferFrmFlightNum(uploadedMaibagVO.getTransferFrmFlightNum());
		if (uploadedMaibagVO.getTransferFrmFlightDate() != null)
			scannedMailDetailsVO.setTransferFrmFlightDate(uploadedMaibagVO.getTransferFrmFlightDate());
		scannedMailDetailsVO.setTransferFrmFlightSeqNum(uploadedMaibagVO.getTransferFrmFlightSeqNum());
		if (uploadedMaibagVO.getFromCarrierCode() != null) {
			scannedMailDetailsVO.setTransferFromCarrier(uploadedMaibagVO.getFromCarrierCode());
		}
		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			scannedMailDetailsVO.setFlightDate(uploadedMaibagVO.getToFlightDate());
			scannedMailDetailsVO.setFlightNumber(uploadedMaibagVO.getToFlightNumber());
			scannedMailDetailsVO.setCarrierCode(uploadedMaibagVO.getToCarrierCode());
		}
	}

	/**
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	public String findSystemParameterValue(String syspar) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/**
	* Method : MailUploadController.findAirportCityForMLD Added by : A-4803 on 14-Nov-2014 Used for : finding airport city for mld Parameters : @param companyCode Parameters : @param destination Parameters : @return Parameters : @throws SystemException Return type : String
	*/
	public String findAirportCityForMLD(String companyCode, String destination) {
		log.debug("MailUploadController" + " : " + "findAirportCityForMLD" + " Entering");
		String airportCode = "";
		if (destination != null) {
			try {
				MailOperationsDAO mailOperationsDAO = MailOperationsDAO.class
						.cast(PersistenceController.getEntityManager().getQueryDAO(MODULENAME));
				airportCode = mailOperationsDAO.findAirportCityForMLD(companyCode, destination);
			} catch (PersistenceException e) {
				log.error("PersistenceException caught");
			}
		}
		log.debug("MailUploadController" + " : " + "findAirportCityForMLD" + " Exiting");
		if (airportCode != null) {
			return airportCode;
		} else {
			return destination;
		}
	}

	/**
	* Mthod for create Mail Acceptance VO
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public MailAcceptanceVO makeMailAcceptanceVO(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("makeMailAcceptanceVO" + " : " + "execute" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<FlightValidationVO> flightDetailsVOs = null;
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.MRD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
				|| (scannedMailDetailsVO.getMailSource() != null
						&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN)))
								? scannedMailDetailsVO.getAirportCode()
								: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		mailAcceptanceVO.setFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
		mailAcceptanceVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		mailAcceptanceVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailAcceptanceVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailAcceptanceVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		mailAcceptanceVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
		mailAcceptanceVO.setPol(scannedMailDetailsVO.getPol());
		mailAcceptanceVO.setScanned(true);
		mailAcceptanceVO.setPreassignNeeded(false);
		mailAcceptanceVO.setMailSource(scannedMailDetailsVO.getMailSource());
		mailAcceptanceVO.setMailDataSource(scannedMailDetailsVO.getMailSource());
		mailAcceptanceVO.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
		containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if (containerDetailsVO == null) {
			containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		}
		if (logonAttributes != null && logonAttributes.getDefaultWarehouseCode() != null) {
			containerDetailsVO.setWareHouse(logonAttributes.getDefaultWarehouseCode());
		}
		containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setActualWeight(scannedMailDetailsVO.getActualUldWeight());
		containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerDetailsVO.setAcceptedFlag(scannedMailDetailsVO.getAcceptedFlag());
		containerDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
		containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
		containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
		if (scannedMailDetailsVO.getRemarks() != null && scannedMailDetailsVO.getRemarks().trim().length() > 0) {
			containerDetailsVO.setRemarks(scannedMailDetailsVO.getRemarks());
		}
		containerDetailsVO.setAssignmentDate(localDateUtil.getLocalDate(airportCode, true));
		if (scannedMailDetailsVO.getScannedUser() != null) {
			containerDetailsVO.setAssignedUser(scannedMailDetailsVO.getScannedUser());
		}
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
		containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
		containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		if (scannedMailDetailsVO.getContainerJourneyId() != null) {
			containerDetailsVO.setContainerJnyId(scannedMailDetailsVO.getContainerJourneyId());
		}
		if (scannedMailDetailsVO.getNewContainer() != null
				&& scannedMailDetailsVO.getNewContainer().trim().length() > 0) {
			containerDetailsVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
		}
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerVO.setAssignedPort(scannedMailDetailsVO.getPol());
		ContainerAssignmentVO containerAssignmentVO = null;
		try {
			containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
		} finally {
		}
		ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
		ULDForSegment uLDForSegment = null;
		uLDForSegmentPK.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		uLDForSegmentPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
		uLDForSegmentPK.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		uLDForSegmentPK.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		uLDForSegmentPK.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		uLDForSegmentPK.setUldNumber(scannedMailDetailsVO.getContainerNumber());
		if (uLDForSegmentPK.getFlightSequenceNumber() > 0) {
			try {
				uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
			} catch (FinderException e1) {
				uLDForSegment = null;
			}
		}
		if (containerAssignmentVO != null && uLDForSegment == null) {
			ULDAtAirportPK uLDAtAirportPK = new ULDAtAirportPK();
			uLDAtAirportPK.setAirportCode(containerAssignmentVO.getAirportCode());
			uLDAtAirportPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
			uLDAtAirportPK.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			uLDAtAirportPK.setUldNumber(scannedMailDetailsVO.getContainerNumber());
			ULDAtAirport uLDAtAirport = null;
			try {
				uLDAtAirport = ULDAtAirport.find(uLDAtAirportPK);
			} catch (FinderException e) {
				uLDAtAirport = null;
			}
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			if (containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT && uLDAtAirport != null) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
				uLDAtAirport.setFinalDestination(scannedMailDetailsVO.getDestination());
			}
		} else if (containerAssignmentVO != null && uLDForSegment != null) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
		}
		if (containerAssignmentVO != null && (containerAssignmentVO.getArrivalFlag() != null
				&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		}
		if (containerAssignmentVO != null
				&& (containerAssignmentVO.getArrivalFlag() != null
						&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
				&& (containerAssignmentVO.getTransitFlag() != null
						&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag()))
				&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		}
		if (containerAssignmentVO != null
				&& (containerAssignmentVO.getTransitFlag() != null
						&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag()))
				&& (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())
						|| MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType()))) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setUldFulIndFlag("N");
			if ((containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
					&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO.getFlightSequenceNumber()
					&& containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode()))) {
				containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
				containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
			}
		}
		if ("EXPFLTFIN_ACPMAL".equals(scannedMailDetailsVO.getMailSource())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_INSERT);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_INSERT);
		}
		if (containerAssignmentVO != null
				&& logonAttributes.getOwnAirlineIdentifier() != containerAssignmentVO.getCarrierId()
				&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())
				&& containerAssignmentVO.getCarrierId() == scannedMailDetailsVO.getCarrierId()
				&& containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode())
				&& MailConstantsVO.DESTN_FLT_STR.equals(containerAssignmentVO.getFlightNumber())
				&& containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())) {
			containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
			containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
		}
		if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())) {
			containerDetailsVO.setTransferFromCarrier(scannedMailDetailsVO.getTransferFromCarrier());
		}
		Collection<MailbagVO> mailVOs = scannedMailDetailsVO.getMailDetails();
		OfficeOfExchangeVO officeOfExchangeVO = null;
		if (mailVOs != null && mailVOs.size() > 0 && (!scannedMailDetailsVO.isAcknowledged())) {
			for (MailbagVO mailbagVO : mailVOs) {
				mailbagVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
				mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
				mailbagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
				mailbagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
				mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
				mailbagVO.setPou(scannedMailDetailsVO.getPou());
				mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0, 1));
				if (mailbagVO.getPaBuiltFlag() != null && mailbagVO.getPaBuiltFlag().trim().length() > 0) {
					if (mailbagVO.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
						containerDetailsVO.setPaBuiltFlag(mailbagVO.getPaBuiltFlag());
						try {
							officeOfExchangeVO = validateOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(),
									mailbagVO.getOoe());
						} finally {
						}
						if (officeOfExchangeVO != null) {
							containerDetailsVO.setPaCode(officeOfExchangeVO.getPoaCode());
						}
					}
				}
				String commodityCode = "";
				CommodityValidationVO commodityValidationVO = null;
				try {
					commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
					commodityValidationVO = validateCommodity(scannedMailDetailsVO.getCompanyCode(), commodityCode,
							mailbagVO.getPaCode());
				} finally {
				}
				Mailbag.validateCommodity(commodityValidationVO, mailbagVO);
				mailbagVO.setTransferFromCarrier(mailbagVO.getTransferFromCarrier());
				mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
				mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
				mailbagVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
				mailbagVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
				mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setScannedPort(scannedMailDetailsVO.getPol());
				if (mailbagVO.getScannedUser() == null) {
					mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				}
				mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
				mailbagVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_NO);
				if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getDamageFlag())) {
					mailbagVO.setDamageFlag(MailConstantsVO.FLAG_YES);
				} else {
					mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
				}
				if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getReassignFlag())) {
					mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
					mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				}
				if ("EXPFLTFIN_ACPMAL".equals(mailAcceptanceVO.getMailSource())
						&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus())) {
					mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
				}
			}
		} else if (scannedMailDetailsVO.isAcknowledged()) {
			mailAcceptanceVO.setDuplicateMailOverride(MailConstantsVO.FLAG_YES);
		}
		containerDetailsVO.setMailDetails(mailVOs);
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		if (mailVOs != null && mailVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailVOs) {
				int numBags = 0;
				double bagWgt = 0;
				if (mailbagVO.getDespatchSerialNumber() != null) {
					if (Integer.parseInt(mailbagVO.getDespatchSerialNumber()) != 0) {
						String outerpk = mailbagVO.getOoe() + mailbagVO.getDoe() + mailbagVO.getMailSubclass()
								+ mailbagVO.getMailCategoryCode() + mailbagVO.getDespatchSerialNumber()
								+ mailbagVO.getYear();
						if (dsnMap.get(outerpk) == null) {
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0, 1));
							dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
							dsnVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
							dsnVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
							dsnVO.setCarrierId(mailbagVO.getCarrierId());
							for (MailbagVO innerVO : mailVOs) {
								String innerpk = innerVO.getOoe() + innerVO.getDoe() + innerVO.getMailSubclass()
										+ innerVO.getMailCategoryCode() + innerVO.getDespatchSerialNumber()
										+ innerVO.getYear();
								if (outerpk.equals(innerpk)) {
									numBags = numBags + 1;
									bagWgt = bagWgt + innerVO.getWeight().getValue().doubleValue();
								}
							}
							if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getReassignFlag())) {
								numBags = numBags - 1;
								bagWgt = bagWgt - mailbagVO.getWeight().getValue().doubleValue();
							}
							dsnVO.setBags(numBags);
							dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(bagWgt)));
							dsnMap.put(outerpk, dsnVO);
							numBags = 0;
							bagWgt = 0;
						}
					}
				}
			}
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for (String key : dsnMap.keySet()) {
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getBags();
			totWgt = totWgt + dsnVO.getWeight().getRoundedValue().doubleValue();
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setDsnVOs(newDSNVOs);
		containerDetailsVO.setTotalBags(totBags);
		containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totWgt)));
		if (containerDetailsVO.getAssignedUser() == null) {
			containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		}
		if (scannedMailDetailsVO.getIsContainerPabuilt() != null)
			containerDetailsVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
		containerDetailsVOs.add(containerDetailsVO);
		mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
		mailAcceptanceVO.setDuplicateMailOverride(scannedMailDetailsVO.getDuplicateMailOverride());
		log.debug("makeMailAcceptanceVO" + " : " + "execute" + " Exiting");
		return mailAcceptanceVO;
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void saveDamageCaptureFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveDamageCaptureFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				try {
					saveDamageDetailsForMailbag(scannedMailDetailsVO.getMailDetails());
				} finally {
				}
			}
		}
		log.debug("saveDamageSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param oneTimes
	* @return
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void updateVOForReturn(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			Map<String, Collection<OneTimeVO>> oneTimes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructReturnSession" + " Entering");
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.MRD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		String countryCode = null;
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			airportValidationVO = sharedAreaProxy.validateAirportCode(logonAttributes.getCompanyCode(),
					logonAttributes.getStationCode());
			countryCode = airportValidationVO.getCountryCode();
			if (countryCode != null) {
				postalAdministrationVOs = findLocalPAs(logonAttributes.getCompanyCode(), countryCode);
			}
		} finally {
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO returnMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				if (returnMailbagVO.getMailbagId() != null && returnMailbagVO.getMailbagId().trim().length() > 0) {
					if ("WS".equals(scannedMailDetailsVO.getMailSource())
							&& returnMailbagVO.getReturnedReason() == null) {
						constructAndRaiseException(MailMLDBusniessException.MAIL_RETURN_CODE_IS_MANDATORY,
								MailHHTBusniessException.MAIL_RETURN_CODE_IS_MANDATORY, scannedMailDetailsVO);
					}
					returnMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					returnMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					returnMailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					returnMailbagVO.setScannedPort(airportCode);
					if (returnMailbagVO.getScannedUser() != null) {
						returnMailbagVO.setLastUpdateUser(returnMailbagVO.getScannedUser());
					} else {
						returnMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
						returnMailbagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					}
					returnMailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_RETURNED);
					returnMailbagVO.setFinalDestination(returnMailbagVO.getPou());
					populateMailPKFields(returnMailbagVO);
					if (returnMailbagVO.getDamagedMailbags() != null
							&& returnMailbagVO.getDamagedMailbags().size() > 0) {
						returnMailbagVO.getDamagedMailbags()
								.add(constructDamageDetails(scannedMailDetailsVO.getProcessPoint(),
										returnMailbagVO.getReturnedReason(), returnMailbagVO.getReturnedRemarks(),
										logonAttributes, oneTimes, airportCode, scannedMailDetailsVO));
					} else {
						returnMailbagVO.setDamagedMailbags(new ArrayList<DamagedMailbagVO>());
						returnMailbagVO.getDamagedMailbags()
								.add(constructDamageDetails(scannedMailDetailsVO.getProcessPoint(),
										returnMailbagVO.getReturnedReason(), returnMailbagVO.getReturnedRemarks(),
										logonAttributes, oneTimes, airportCode, scannedMailDetailsVO));
					}
					ArrayList<DamagedMailbagVO> damagedMailbagVOs = (ArrayList<DamagedMailbagVO>) returnMailbagVO
							.getDamagedMailbags();
					if (damagedMailbagVOs != null && damagedMailbagVOs.size() > 0) {
						for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
							if (returnMailbagVO.getScannedUser() != null) {
								damagedMailbagVO.setUserCode(returnMailbagVO.getScannedUser());
							}
							log.info("" + "Setting scanner user id:-" + " " + damagedMailbagVO);
						}
					}
					String paCode = returnMailbagVO.getPaCode() != null
							&& returnMailbagVO.getPaCode().trim().length() > 0 ? returnMailbagVO.getPaCode()
									: mailController.findPAForOfficeOfExchange(returnMailbagVO.getCompanyCode(),
											returnMailbagVO.getOoe());
					if (paCode != null && !"".equals(paCode)) {
						returnMailbagVO.setPaCode(paCode);
					}
					Collection<DamagedMailbagVO> damagedmails = returnMailbagVO.getDamagedMailbags();
					for (DamagedMailbagVO damageVo : damagedmails) {
						damageVo.setPaCode(returnMailbagVO.getPaCode());
						Collection<DamagedMailbagVO> damagedMails = returnMailbagVO.getDamagedMailbags();
						if (damagedMails != null && damagedMails.size() > 0) {
							for (DamagedMailbagVO damagedMail : damagedMails) {
								damagedMail.setPaCode(returnMailbagVO.getPaCode());
								damagedMail.setOperationType(returnMailbagVO.getOperationalStatus());
							}
						}
					}
				}
				MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(returnMailbagVO);
				if (newmailbagVO != null) {
					if (returnMailbagVO.getFlightSequenceNumber() == 0 && returnMailbagVO.getFlightNumber() != null
							&& returnMailbagVO.getFlightNumber().trim().length() == 0) {
						newmailbagVO.setPou(newmailbagVO.getFinalDestination());
					}
					if (MailConstantsVO.DESTN_FLT == returnMailbagVO.getFlightSequenceNumber()) {
						newmailbagVO.setPou(returnMailbagVO.getPou());
						newmailbagVO.setFinalDestination(returnMailbagVO.getFinalDestination());
					}
					if ("WS".equals(returnMailbagVO.getMailSource())) {
						newmailbagVO.setPou(newmailbagVO.getFinalDestination());
					}
					updateExistingMailBagVO(returnMailbagVO, newmailbagVO, false);
					returnMailbagVO.setFinalDestination(returnMailbagVO.getPou());
				}
			}
		}
		scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		log.debug("UploadMailDetailsCommand" + " : " + "constructArrivalSession" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void saveReturnFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveReturnFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
					|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
							? scannedMailDetailsVO.getAirportCode()
							: logonAttributes.getAirportCode();
			if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
				if (scannedMailDetailsVO.getAirportCode() != null) {
					airportCode = scannedMailDetailsVO.getAirportCode();
				}
			}
			try {
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
					scannedMailDetailsVO.getMailDetails().iterator().next()
							.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
					returnScannedMailbags(airportCode, scannedMailDetailsVO.getMailDetails());
				}
			} catch (MailbagAlreadyReturnedException e) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} catch (ReturnNotPossibleException e) {
				constructAndRaiseException(MailMLDBusniessException.RETURN_NOT_POSSIBLE_EXCEPTION,
						MailHHTBusniessException.RETURN_NOT_POSSIBLE_EXCEPTION, scannedMailDetailsVO);
			} catch (FlightClosedException e) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			} catch (ReassignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.REASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.REASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (DuplicateMailBagsException e) {
				e.printStackTrace();
			}
		}
		log.debug("saveReturnSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws SystemException
	*/
	private void updateVOForReassign(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes) {
		log.debug("UploadMailDetailsCommand" + " : " + "constructReassignedMailSession" + " Entering");
		String airportCode = scannedMailDetailsVO.getAirportCode();
		Collection<ContainerVO> reassignContainerVOs = new ArrayList<ContainerVO>();
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			Collection<MailbagVO> mailBags = scannedMailDetailsVO.getMailDetails();
			log.info("" + "mailbagVOs***:>>>" + " " + mailBags);
			for (MailbagVO reassignMailBagVO : mailBags) {
				if (reassignMailBagVO.getMailbagId() != null && reassignMailBagVO.getMailbagId().trim().length() > 0) {
					if (reassignMailBagVO.getMailbagId().trim().length() == 29
							|| isValidMailtag(reassignMailBagVO.getMailbagId().trim().length())) {
						String mailbagId = reassignMailBagVO.getMailbagId();
						reassignMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
						reassignMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						reassignMailBagVO.setMailbagId(mailbagId);
						populateMailPKFields(reassignMailBagVO);
						reassignMailBagVO.setScannedPort(airportCode);
						if (reassignMailBagVO.getScannedUser() != null) {
							reassignMailBagVO.setLastUpdateUser(reassignMailBagVO.getScannedUser());
						} else {
							reassignMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
							reassignMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						reassignMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ACCEPTED);
						reassignMailBagVO.setPol(airportCode);
						if ("EXP".equals(scannedMailDetailsVO.getProcessPoint())) {
							reassignMailBagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
						}
					} else {
						ContainerVO reassignContainerVO = new ContainerVO();
						reassignContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
						reassignContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						reassignContainerVO.setContainerNumber(reassignMailBagVO.getMailbagId());
						reassignContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						reassignContainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						if (reassignMailBagVO.getScannedUser() != null) {
							reassignContainerVO.setAssignedUser(reassignMailBagVO.getScannedUser());
							reassignContainerVO.setLastUpdateUser(reassignMailBagVO.getScannedUser());
						} else {
							reassignContainerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
							reassignContainerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						reassignContainerVO.setAssignedPort(airportCode);
						reassignContainerVO.setScannedDate(reassignMailBagVO.getScannedDate());
						reassignContainerVOs.add(reassignContainerVO);
					}
					if (reassignMailBagVO.getMailbagId().trim().length() == 29
							|| isValidMailtag(reassignMailBagVO.getMailbagId().trim().length())) {
						scannedMailDetailsVO.setContainerNumber(reassignMailBagVO.getContainerNumber());
					} else {
						scannedMailDetailsVO.setContainerNumber(reassignMailBagVO.getMailbagId());
					}
					scannedMailDetailsVO.setPol(airportCode);
					if (reassignMailBagVO.getPou() != null && reassignMailBagVO.getPou().trim().length() > 0) {
						scannedMailDetailsVO.setPou(reassignMailBagVO.getPou());
					}
				}
			}
		}
		scannedMailDetailsVO.setScannedContainerDetails(reassignContainerVOs);
		if (reassignContainerVOs.size() > 0) {
			scannedMailDetailsVO.setMailDetails(null);
		}
		int scanCount = 0;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			scanCount = scannedMailDetailsVO.getMailDetails().size();
		}
		if (scannedMailDetailsVO.getScannedContainerDetails() != null
				&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
			scanCount = scanCount + scannedMailDetailsVO.getScannedContainerDetails().size();
		}
		scannedMailDetailsVO.setScannedBags(scanCount);
		log.debug("UploadMailDetailsCommand" + " : " + "constructReassignedMailSession" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void savereassignFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "savereassignFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
					|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
							? scannedMailDetailsVO.getAirportCode()
							: logonAttributes.getAirportCode();
			if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
				if (scannedMailDetailsVO.getAirportCode() != null) {
					airportCode = scannedMailDetailsVO.getAirportCode();
				}
			}
			try {
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0
						&& scannedMailDetailsVO.getValidatedContainer() != null) {
					ContainerVO toContainerVO = constructContainerVO(airportCode,
							scannedMailDetailsVO.getValidatedContainer(), logonAttributes,
							scannedMailDetailsVO.getMailSource());
					log.info("" + "toContainerVO for reasign" + " " + toContainerVO);
					Collection<MailbagVO> mails = scannedMailDetailsVO.getMailDetails();
					for (MailbagVO mail : mails) {
						MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(mail);
						if (existingMailbagVO != null) {
							updateExistingMailBagVO(mail, existingMailbagVO, false);
						}
						if (mail.getPaBuiltFlag() != null && mail.getPaBuiltFlag().trim().length() > 0) {
							toContainerVO.setPaBuiltFlag(mail.getPaBuiltFlag());
						}
					}
					reassignMailbags(scannedMailDetailsVO.getMailDetails(), toContainerVO);
				}
				if (scannedMailDetailsVO.getScannedContainerDetails() != null
						&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
					OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(airportCode,
							scannedMailDetailsVO, logonAttributes);
					for (ContainerVO container : scannedMailDetailsVO.getScannedContainerDetails()) {
						String carrierCode = container.getCarrierCode();
						container.setSource(scannedMailDetailsVO.getMailSource());
						container.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
						boolean isPartner = new MailOperationsValidator().checkIfPartnerCarrier(airportCode,
								carrierCode);
						log.info(""
								+ "After MailtrackingDefaultsValidator().validateScannedMailDetails :: scannedMailDetailsVO"
								+ " " + scannedMailDetailsVO);
						if (!isPartner) {
							constructAndRaiseException(MailMLDBusniessException.NON_PARTNER_CARRIER,
									MailHHTBusniessException.NON_PARTNER_CARRIER, scannedMailDetailsVO);
						}
					}
					reassignContainers(scannedMailDetailsVO.getScannedContainerDetails(), operationalFlightVO);
				}
			} catch (FlightClosedException e) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			} catch (ContainerAssignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (InvalidFlightSegmentException e) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
						MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
			} catch (ULDDefaultsProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
						MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
			} catch (CapacityBookingProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
						MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (MailBookingException e) {
				constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
						MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (ReassignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.REASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.REASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (MailDefaultStorageUnitException ex) {
				constructAndRaiseException(MailMLDBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
						MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
						scannedMailDetailsVO);
			}
		}
		log.debug("saveAcceptanceSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Method for Construct Accept Session
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	*/
	private void updateVOForAcceptance(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes) {
		log.debug("MailController" + " : " + "constructAcceptDataForHHT" + " Entering");
		String airportCode = scannedMailDetailsVO.getAirportCode();
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			Collection<MailbagVO> uploadedMailbagVOs = scannedMailDetailsVO.getMailDetails();
			log.info("" + "mailbagVOs***:>>>" + " " + uploadedMailbagVOs);
			for (MailbagVO acceptedMailBagVO : uploadedMailbagVOs) {
				acceptedMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
				acceptedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				populateMailPKFields(acceptedMailBagVO);
				acceptedMailBagVO.setScannedPort(airportCode);
				if (acceptedMailBagVO.getScannedUser() != null) {
					acceptedMailBagVO.setScannedUser(acceptedMailBagVO.getScannedUser());
					acceptedMailBagVO.setLastUpdateUser(acceptedMailBagVO.getScannedUser());
				} else {
					acceptedMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					acceptedMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				}
				acceptedMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				acceptedMailBagVO.setPol(airportCode);
				acceptedMailBagVO.setTransferFromCarrier(acceptedMailBagVO.getFromCarrier());
			}
		}
		scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
		scannedMailDetailsVO.setPol(airportCode);
		if (scannedMailDetailsVO.getMailDetails() != null) {
			scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		}
		log.debug("MailController" + " : " + "constructAcceptDataForHHT" + " Exiting");
	}

	/**
	* Method for save Arrival Session
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public void saveArrivalFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveArrivalFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
			Collection<MailArrivalVO> mailArrivalVOsToSave = null;
			if ((scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty())
					|| ((MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerArrivalFlag())
							|| MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag()))
							&& scannedMailDetailsVO.getContainerNumber() != null)) {
				mailArrivalVOs = makeMailArrivalVOs(scannedMailDetailsVO, logonAttributes);
			}
			if (mailArrivalVOs != null && mailArrivalVOs.size() > 0) {
				for (MailArrivalVO mailArrivalVO : mailArrivalVOs) {
					if (mailArrivalVO.isDeliveryNeeded()) {
						scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_DELIVERED);
						mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
						mailArrivalVOsToSave.add(mailArrivalVO);
						try {
							log.info("" + "Going To call saveScannedDeliverMails" + " " + mailArrivalVOsToSave);
							saveScannedDeliverMails(mailArrivalVOsToSave);
						} catch (ContainerAssignmentException e) {
							constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
									MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
						} catch (DuplicateMailBagsException e) {
							constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						} catch (MailbagIncorrectlyDeliveredException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						} catch (InvalidFlightSegmentException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
									MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
						} catch (FlightClosedException e) {
							constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
									MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
						} catch (ULDDefaultsProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
									MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
						} catch (CapacityBookingProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
									MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailBookingException e) {
							constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
									MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailOperationsBusinessException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						}
					} else {
						mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
						mailArrivalVOsToSave.add(mailArrivalVO);
						try {
							log.info("" + "Going To call saveScannedInboundMails" + " " + mailArrivalVOsToSave);
							saveScannedInboundMails(mailArrivalVOsToSave);
						} catch (ContainerAssignmentException e) {
							if (e.getErrors() != null && e.getErrors().size() > 0) {
								for (ErrorVO error : e.getErrors()) {
									if (ContainerAssignmentException.DESTN_ASSIGNED.equals(error.getErrorCode())) {
										constructAndRaiseException(MailMLDBusniessException.DESTN_ASSIGNED,
												MailHHTBusniessException.DESTN_ASSIGNED, scannedMailDetailsVO);
									} else {
										constructAndRaiseException(MailMLDBusniessException.FLIGHT_STATUS_POL_OPEN,
												"Flight is not closed at POL", scannedMailDetailsVO);
									}
								}
							}
						} catch (DuplicateMailBagsException e) {
							constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						} catch (MailbagIncorrectlyDeliveredException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						} catch (InvalidFlightSegmentException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
									constructInvalidArrivalMessege(scannedMailDetailsVO), scannedMailDetailsVO);
						} catch (FlightClosedException e) {
							constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
									MailHHTBusniessException.INB_FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
						} catch (InventoryForArrivalFailedException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_ARRIVAL_EXCEPTION,
									MailHHTBusniessException.INVALID_ARRIVAL_EXCEPTION, scannedMailDetailsVO);
						} catch (ULDDefaultsProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
									MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
						} catch (CapacityBookingProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
									MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailBookingException e) {
							constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
									MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailOperationsBusinessException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						}
					}
				}
			}
		}
		log.debug("saveArrivalSession" + " : " + "execute" + " Exiting");
	}

	/**
	* @author A-5991
	* @param scannedMailDetailsVO
	* @return
	*/
	private String constructInvalidArrivalMessege(ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		String errorMessage = "Invalid flight Segment";
		if (scannedMailDetailsVO.getContainerNumber() != null
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			try {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
						&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
								|| (scannedMailDetailsVO.getMailDetails() == null
										|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
					containerAssignmentVO = this.cntAsgmntVO == null
							? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
							: this.cntAsgmntVO;
					this.cntAsgmntVO = containerAssignmentVO;
				} else {
					containerAssignmentVO = mailController
							.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				}
			} finally {
			}
		}
		if (containerAssignmentVO != null) {
			StringBuilder messageString = new StringBuilder("The container ")
					.append(scannedMailDetailsVO.getContainerNumber()).append(" is assigned in ");
			if (containerAssignmentVO.getFlightSequenceNumber() > 0) {
				messageString.append("flight ").append(containerAssignmentVO.getCarrierCode()).append("-")
						.append(containerAssignmentVO.getFlightNumber()).append(" : ")
						.append((containerAssignmentVO.getFlightDate() != null) ? containerAssignmentVO.getFlightDate()
								.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)) : "XXXX");
			} else {
				messageString.append("carrier ").append(containerAssignmentVO.getCarrierCode()).append("-")
						.append(containerAssignmentVO.getPou());
			}
			errorMessage = messageString.toString();
		}
		return errorMessage;
	}

	/**
	* Method for Mail Arrival VOs
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private Collection<MailArrivalVO> makeMailArrivalVOs(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("MailController" + " : " + "makeMailArrivalVOs" + " Entering");
		Collection<MailArrivalVO> mailArrivalVOsForSave = new ArrayList<MailArrivalVO>();
		Collection<MailbagVO> mailbagVOsToArrive = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsToDeliver = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOs = scannedMailDetailsVO.getMailDetails();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (mailbagVO.isDeliveryNeeded()) {
					mailbagVOsToDeliver.add(mailbagVO);
				} else {
					mailbagVOsToArrive.add(mailbagVO);
				}
				if ("I".equals(mailbagVO.getOperationalFlag())) {
					scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_NO);
				}
			}
			if (mailbagVOsToDeliver.size() > 0) {
				ScannedMailDetailsVO scannedMailToDeliver = constructScannedMailDetailsVO(scannedMailDetailsVO);
				scannedMailToDeliver.setMailDetails(mailbagVOsToDeliver);
				MailArrivalVO mailArrivalVOToDeliver = makeMailArrivalVO(scannedMailToDeliver, logonAttributes, true);
				mailArrivalVOToDeliver.setDeliveryNeeded(true);
				mailArrivalVOsForSave.add(mailArrivalVOToDeliver);
			}
			if (mailbagVOsToArrive.size() > 0) {
				ScannedMailDetailsVO scannedMailToArrive = constructScannedMailDetailsVO(scannedMailDetailsVO);
				scannedMailToArrive.setMailDetails(mailbagVOsToArrive);
				MailArrivalVO mailArrivalVO = makeMailArrivalVO(scannedMailToArrive, logonAttributes, false);
				mailArrivalVO.setDeliveryNeeded(false);
				mailArrivalVOsForSave.add(mailArrivalVO);
			}
		} else {
			if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerArrivalFlag())) {
				ScannedMailDetailsVO scannedMailToArrive = constructScannedMailDetailsVO(scannedMailDetailsVO);
				MailArrivalVO mailArrivalVO = makeMailArrivalVO(scannedMailToArrive, logonAttributes, false);
				mailArrivalVO.setDeliveryNeeded(false);
				mailArrivalVOsForSave.add(mailArrivalVO);
			} else if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())) {
				ScannedMailDetailsVO scannedMailToDeliver = constructScannedMailDetailsVO(scannedMailDetailsVO);
				MailArrivalVO mailArrivalVOToDeliver = makeMailArrivalVO(scannedMailToDeliver, logonAttributes, true);
				mailArrivalVOToDeliver.setDeliveryNeeded(true);
				mailArrivalVOsForSave.add(mailArrivalVOToDeliver);
			}
		}
		log.debug("MailController" + " : " + "makeMailArrivalVOs" + " Exiting");
		return mailArrivalVOsForSave;
	}

	/**
	* Method for Construct Arrival Session
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void updateVOForArrival(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructArrivalSession" + " Entering");
		String airportCode = scannedMailDetailsVO.getAirportCode();
		boolean isAutoArrival = false;
		isAutoArrival = checkAutoArrival(scannedMailDetailsVO);
		ScannedMailDetailsVO scannedMailDetailsVForArrival = new ScannedMailDetailsVO();
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			Collection<MailbagVO> mailBags = scannedMailDetailsVO.getMailDetails();
			log.info("" + "mailbagVOs***:>>>" + " " + mailBags);
			for (MailbagVO arrivedMailBagVO : mailBags) {
				MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
				arrivedMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
				arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				populateMailPKFields(arrivedMailBagVO);
				arrivedMailBagVO.setArrivalSealNumber(arrivedMailBagVO.getSealNumber());
				arrivedMailBagVO.setScannedPort(airportCode);
				if (arrivedMailBagVO.getScannedUser() != null) {
					arrivedMailBagVO.setLastUpdateUser(arrivedMailBagVO.getScannedUser());
				} else {
					arrivedMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					arrivedMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				}
				arrivedMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ARRIVED);
				arrivedMailBagVO.setPou(scannedMailDetailsVO.getPou());
				arrivedMailBagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				arrivedMailBagVO.setContainerType(scannedMailDetailsVO.getContainerType());
				if (MailConstantsVO.FLAG_YES.equals(arrivedMailBagVO.getDeliveredFlag())) {
					arrivedMailBagVO.setDeliveryNeeded(true);
				} else {
					arrivedMailBagVO.setDeliveryNeeded(false);
				}
				if (!MailbagHistory.isMailbagAlreadyArrived(arrivedMailBagVO)) {
					Mailbag mailbag = null;
					try {
						mailbag = Mailbag
								.findMailbag(createMailbagPK(arrivedMailBagVO.getCompanyCode(), arrivedMailBagVO));
					} catch (FinderException exception) {
					}
					log.info("After findMailbag***:>>>" + mailbag);
					String arrivedBulkNumber = null;
					if (MailConstantsVO.BULK_TYPE.equals(arrivedMailBagVO.getContainerType())) {
						arrivedBulkNumber = constructBulkULDNumber(arrivedMailBagVO.getPou(),
								arrivedMailBagVO.getCarrierCode());
					}
					if (arrivedBulkNumber == null) {
						arrivedBulkNumber = arrivedMailBagVO.getContainerNumber();
					}
					log.info("arrivedBulkNumber***:>>>" + arrivedBulkNumber);
					scannedMailDetailsVForArrival = mailOperationsMapper.copyScannedMailDetailsVO(scannedMailDetailsVO);
					try {
						mailbagInULDForSegmentVO = new MailbagInULDForSegment()
								.getManifestInfo(scannedMailDetailsVForArrival);
					} catch (PersistenceException persistenceException) {
						log.info("Exception :", persistenceException);
					}
					if (Objects.nonNull(mailbagInULDForSegmentVO) && StringUtils.equals(MailConstantsVO.FLAG_YES,
							mailbagInULDForSegmentVO.getArrivalFlag())) {
						arrivedMailBagVO.setFoundResditSent(true);
					}
					if (mailbag != null && mailbagInULDForSegmentVO != null
							&& !scannedMailDetailsVO.isFoundDelivery()) {
						if (mailbagInULDForSegmentVO.getCarrierId() == arrivedMailBagVO.getCarrierId()
								&& mailbagInULDForSegmentVO.getFlightNumber().equals(arrivedMailBagVO.getFlightNumber())
								&& mailbagInULDForSegmentVO.getFlightSequenceNumber() == arrivedMailBagVO
										.getFlightSequenceNumber()
								&& ((mailbagInULDForSegmentVO.getContainerNumber().equals(arrivedBulkNumber)
										&& MailConstantsVO.BULK_TYPE
												.equals(mailbagInULDForSegmentVO.getContainerType()))
										|| (mailbagInULDForSegmentVO.getContainerNumber()
												.equals(arrivedMailBagVO.getContainerNumber())
												&& MailConstantsVO.ULD_TYPE
														.equals(mailbagInULDForSegmentVO.getContainerType())))) {
							arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							arrivedMailBagVO.setPol(mailbagInULDForSegmentVO.getAssignedPort());
							arrivedMailBagVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
							arrivedMailBagVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
							arrivedMailBagVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
							if (MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getPaBuiltFlag())
									&& MailConstantsVO.ULD_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())) {
								scannedMailDetailsVO.setIsContainerPabuilt(MailConstantsVO.FLAG_YES);
								arrivedMailBagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
							}
						} else if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(scannedMailDetailsVO.getAndroidFlag())
								&& MailConstantsVO.ULD_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())
								&& !(mailbagInULDForSegmentVO.getContainerNumber().equals(arrivedBulkNumber))) {
							arrivedMailBagVO.setPol(mailbagInULDForSegmentVO.getAssignedPort());
							arrivedMailBagVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
						}
						if (isAutoArrival
								&& MailConstantsVO.BULK_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())
								&& scannedMailDetailsVO.getFlightNumber() != null
								&& scannedMailDetailsVO.getFlightNumber().trim().length() == 0) {
							arrivedMailBagVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
							arrivedMailBagVO
									.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
						}
						if (((mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
								|| (mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED))
								|| (mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)))
								&& arrivedMailBagVO.getDeliveredFlag() != null
								&& arrivedMailBagVO.getDeliveredFlag().equals(MailConstantsVO.FLAG_YES))
								&& (arrivedMailBagVO.getConsignmentNumber() == null
										|| arrivedMailBagVO.getConsignmentNumber().trim().length() == 0)
								&& isAutoArrival) {
							if (mailbagInULDForSegmentVO.getContainerNumber() != null
									&& scannedMailDetailsVO.getContainerNumber() != null
									&& mailbagInULDForSegmentVO.getContainerNumber()
											.equalsIgnoreCase(scannedMailDetailsVO.getContainerNumber())) {
								arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							} else if (mailbagInULDForSegmentVO.getContainerNumber() != null
									&& scannedMailDetailsVO.getContainerNumber() != null
									&& mailbagInULDForSegmentVO.getContainerType() != null
									&& MailConstantsVO.ULD_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())
									&& scannedMailDetailsVO.getContainerType() != null
									&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())
									&& !mailbagInULDForSegmentVO.getContainerNumber()
											.equalsIgnoreCase(scannedMailDetailsVO.getContainerNumber())) {
								arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								scannedMailDetailsVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
								scannedMailDetailsVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
							} else {
								arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
							}
							arrivedMailBagVO.setPol(mailbagInULDForSegmentVO.getAssignedPort());
							arrivedMailBagVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
							arrivedMailBagVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
							arrivedMailBagVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
							arrivedMailBagVO.setFlightNumber(Objects.nonNull(arrivedMailBagVO.getFlightNumber())
									? arrivedMailBagVO.getFlightNumber()
									: mailbagInULDForSegmentVO.getFlightNumber());
							arrivedMailBagVO
									.setFlightSequenceNumber(Objects.nonNull(arrivedMailBagVO.getFlightSequenceNumber())
											? arrivedMailBagVO.getFlightSequenceNumber()
											: mailbagInULDForSegmentVO.getFlightSequenceNumber());
							if (scannedMailDetailsVO.getContainerNumber() == null
									|| scannedMailDetailsVO.getContainerNumber().isEmpty()) {
								scannedMailDetailsVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
							}
							if (scannedMailDetailsVO.getContainerType() == null
									|| scannedMailDetailsVO.getContainerType().isEmpty()) {
								scannedMailDetailsVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
							}
							if (MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getPaBuiltFlag())
									&& MailConstantsVO.ULD_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())) {
								scannedMailDetailsVO.setIsContainerPabuilt(MailConstantsVO.FLAG_YES);
								arrivedMailBagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
							}
							scannedMailDetailsVO.setFlightNumber(Objects.nonNull(scannedMailDetailsVO.getFlightNumber())
									? scannedMailDetailsVO.getFlightNumber()
									: mailbagInULDForSegmentVO.getFlightNumber());
							scannedMailDetailsVO.setFlightSequenceNumber(
									Objects.nonNull(scannedMailDetailsVO.getFlightSequenceNumber())
											? scannedMailDetailsVO.getFlightSequenceNumber()
											: mailbagInULDForSegmentVO.getFlightSequenceNumber());
							scannedMailDetailsVO
									.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
							scannedMailDetailsVO.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
							FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
							flightFilterVO.setFlightCarrierId(mailbagInULDForSegmentVO.getCarrierId());
							flightFilterVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
							flightFilterVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
							Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
							if (flightValidationVOs != null)
								for (FlightValidationVO flightValidation : flightValidationVOs) {
									if (flightValidation.getLegDestination()
											.equals(scannedMailDetailsVO.getAirportCode())) {
										scannedMailDetailsVO.setFlightDate(
												LocalDateMapper.toZonedDateTime(flightValidation.getSta()));
										arrivedMailBagVO.setFlightDate(
												LocalDateMapper.toZonedDateTime(flightValidation.getSta()));
										scannedMailDetailsVO.setLegSerialNumber(flightValidation.getLegSerialNumber());
									}
								}
						}
					} else {
						scannedMailDetailsVO.setPou(arrivedMailBagVO.getScannedPort());
						arrivedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					}
				}
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		}
	}

	/**
	* Method for ScannedMailDetailsVO
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException
	*/
	public ScannedMailDetailsVO constructScannedMailDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO) {
		ScannedMailDetailsVO newScannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		newScannedMailDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		newScannedMailDetailsVO.setProcessPoint(scannedMailDetailsVO.getProcessPoint());
		newScannedMailDetailsVO.setMailSource(scannedMailDetailsVO.getMailSource());
		newScannedMailDetailsVO.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
		if (scannedMailDetailsVO.getTransferFrmFlightNum() == null)
			newScannedMailDetailsVO.setAirportCode(scannedMailDetailsVO.getAirportCode());
		newScannedMailDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		newScannedMailDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		if (scannedMailDetailsVO.getTransferFrmFlightNum() != null
				&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0) {
			newScannedMailDetailsVO.setFlightNumber(scannedMailDetailsVO.getTransferFrmFlightNum());
			newScannedMailDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getTransferFrmFlightSeqNum());
			newScannedMailDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getTransferFrmFlightLegSerialNumber());
			newScannedMailDetailsVO.setCarrierId(scannedMailDetailsVO.getTransferFrmCarrierId());
			newScannedMailDetailsVO.setCarrierCode(scannedMailDetailsVO.getTransferFromCarrier());
		} else {
			newScannedMailDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			newScannedMailDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		}
		newScannedMailDetailsVO.setContainerArrivalFlag(scannedMailDetailsVO.getContainerArrivalFlag());
		newScannedMailDetailsVO.setContainerDeliveryFlag(scannedMailDetailsVO.getContainerDeliveryFlag());
		if (scannedMailDetailsVO.getTransferFrmFlightDate() != null) {
			newScannedMailDetailsVO.setFlightDate(scannedMailDetailsVO.getTransferFrmFlightDate());
			newScannedMailDetailsVO.setAirportCode(scannedMailDetailsVO.getAirportCode());
		} else {
			newScannedMailDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
			newScannedMailDetailsVO.setFlightStatus(scannedMailDetailsVO.getFlightStatus());
			newScannedMailDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
			newScannedMailDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		}
		if (scannedMailDetailsVO.getTransferFrmFlightNum() != null
				&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0
				&& scannedMailDetailsVO.getTransferFrmFlightDate() != null) {
			try {
				flightSegments = flightOperationsProxy.findFlightSegments(scannedMailDetailsVO.getCompanyCode(),
						scannedMailDetailsVO.getTransferFrmCarrierId(), scannedMailDetailsVO.getTransferFrmFlightNum(),
						scannedMailDetailsVO.getTransferFrmFlightSeqNum());
			} finally {
			}
			if (flightSegments != null && !flightSegments.isEmpty()) {
				newScannedMailDetailsVO.setPol(flightSegments.iterator().next().getSegmentOrigin());
				newScannedMailDetailsVO.setPou(flightSegments.iterator().next().getSegmentDestination());
				newScannedMailDetailsVO.setAirportCode(flightSegments.iterator().next().getSegmentDestination());
				newScannedMailDetailsVO
						.setSegmentSerialNumber(flightSegments.iterator().next().getSegmentSerialNumber());
			}
			newScannedMailDetailsVO.setTransferFrmFlightDate(scannedMailDetailsVO.getTransferFrmFlightDate());
			newScannedMailDetailsVO.setTransferFrmFlightNum(scannedMailDetailsVO.getTransferFrmFlightNum());
			newScannedMailDetailsVO.setTransferFrmCarrierId(scannedMailDetailsVO.getTransferFrmCarrierId());
		} else {
			newScannedMailDetailsVO.setPol(scannedMailDetailsVO.getPol());
			newScannedMailDetailsVO.setPou(scannedMailDetailsVO.getPou());
		}
		newScannedMailDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		newScannedMailDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		newScannedMailDetailsVO.setOperationTime(scannedMailDetailsVO.getOperationTime());
		newScannedMailDetailsVO.setAcceptedFlag(scannedMailDetailsVO.getAcceptedFlag());
		newScannedMailDetailsVO.setFromPolExist(scannedMailDetailsVO.isFromPolExist());
		newScannedMailDetailsVO.setFoundArrival(scannedMailDetailsVO.isFoundArrival());
		if (scannedMailDetailsVO.getValidatedContainer() != null) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			ContainerDetailsVO scannedContainerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
			containerDetailsVO.setCompanyCode(scannedContainerDetailsVO.getCompanyCode());
			containerDetailsVO.setContainerNumber(scannedContainerDetailsVO.getContainerNumber());
			containerDetailsVO.setContainerType(scannedContainerDetailsVO.getContainerType());
			containerDetailsVO.setCarrierCode(scannedContainerDetailsVO.getCarrierCode());
			containerDetailsVO.setCarrierId(scannedContainerDetailsVO.getCarrierId());
			containerDetailsVO.setFlightNumber(scannedContainerDetailsVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(scannedContainerDetailsVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(scannedContainerDetailsVO.getLegSerialNumber());
			containerDetailsVO.setSegmentSerialNumber(scannedContainerDetailsVO.getSegmentSerialNumber());
			containerDetailsVO.setOperationFlag(scannedContainerDetailsVO.getOperationFlag());
			containerDetailsVO.setContainerOperationFlag(scannedContainerDetailsVO.getContainerOperationFlag());
			containerDetailsVO.setPol(scannedContainerDetailsVO.getPol());
			containerDetailsVO.setPou(scannedContainerDetailsVO.getPou());
			containerDetailsVO.setDestination(scannedContainerDetailsVO.getDestination());
			containerDetailsVO.setFlightDate(scannedContainerDetailsVO.getFlightDate());
			containerDetailsVO.setLegSerialNumber(scannedContainerDetailsVO.getLegSerialNumber());
			if (scannedMailDetailsVO.getIsContainerPabuilt() != null) {
				containerDetailsVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
			}
			newScannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
		}
		return newScannedMailDetailsVO;
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	public void saveTransferFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			ForceAcceptanceException {
		log.info("" + "saveTransferFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
					|| MailConstantsVO.MRD.equals(scannedMailDetailsVO.getMailSource())
					|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
					|| (scannedMailDetailsVO.getMailSource() != null
							&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN)))
									? scannedMailDetailsVO.getAirportCode()
									: logonAttributes.getAirportCode();
			if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
				if (scannedMailDetailsVO.getAirportCode() != null) {
					airportCode = scannedMailDetailsVO.getAirportCode();
				}
			}
			String toPrint = MailConstantsVO.FLAG_NO;
			if (logonAttributes.getOwnAirlineCode() != null && scannedMailDetailsVO.getToCarrierCode() != null
					&& !logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getToCarrierCode())) {
				toPrint = MailConstantsVO.FLAG_SCANNED;
			}
			try {
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
					ContainerVO toContainerVO = null;
					if (scannedMailDetailsVO.getValidatedContainer() != null) {
						toContainerVO = constructContainerVO(airportCode, scannedMailDetailsVO.getValidatedContainer(),
								logonAttributes, scannedMailDetailsVO.getMailSource());
						log.info("" + "toContainerVO for Container Transfer" + " " + toContainerVO);
					} else {
						toContainerVO = constructContainerVO(scannedMailDetailsVO, logonAttributes);
						log.info("" + "toContainerVO for mailbag Transfer" + " " + toContainerVO);
					}
					if(StringUtils.isBlank(toContainerVO.getType())){
						toContainerVO.setType(scannedMailDetailsVO.getContainerType());
					}
					transferMail(null, scannedMailDetailsVO.getMailDetails(), toContainerVO, toPrint);
				}
				if (scannedMailDetailsVO.getScannedContainerDetails() != null
						&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
					OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(airportCode,
							scannedMailDetailsVO, logonAttributes);
					transferContainers(scannedMailDetailsVO.getScannedContainerDetails(), operationalFlightVO,
							MailConstantsVO.FLAG_YES);
				}
			} catch (InvalidFlightSegmentException e) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
						MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
			} catch (CapacityBookingProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
						MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (MailBookingException e) {
				constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
						MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (ContainerAssignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (ULDDefaultsProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
						MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
			}
		}
		log.debug("saveTransferSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param mailDetailsVO
	* @param logonAttributes
	* @return
	*/
	private ContainerVO constructContainerVO(ScannedMailDetailsVO mailDetailsVO, LoginProfile logonAttributes) {
		ContainerVO containerVO = new ContainerVO();
		String airportCode = (MailConstantsVO.MLD.equals(mailDetailsVO.getMailSource())
				|| MailConstantsVO.MRD.equals(mailDetailsVO.getMailSource())
				|| (MailConstantsVO.WS.equals(mailDetailsVO.getMailSource()))) ? mailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setCompanyCode(mailDetailsVO.getCompanyCode());
		containerVO.setCarrierCode(mailDetailsVO.getToCarrierCode());
		containerVO.setCarrierId(mailDetailsVO.getToCarrierid());
		containerVO.setAssignedUser(logonAttributes.getUserId());
		containerVO.setAssignedPort(airportCode);
		containerVO.setLastUpdateUser(logonAttributes.getUserId());
		containerVO.setOperationTime(mailDetailsVO.getOperationTime());
		containerVO.setFoundTransfer(mailDetailsVO.isFoundTransfer());
		return containerVO;
	}

	/**
	* This method constructs the session for Mail Arrival
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void updateVOForDeliver(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructDeliverSession" + " Entering");
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.RESDIT.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			Collection<MailbagVO> deliverMailBags = scannedMailDetailsVO.getMailDetails();
			log.info("" + "mailbagVOs***:>>>" + " " + deliverMailBags);
			for (MailbagVO deliverMailBagVO : deliverMailBags) {
				if (deliverMailBagVO.getMailbagId() != null && deliverMailBagVO.getMailbagId().trim().length() > 0) {
					deliverMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
					deliverMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					populateMailPKFields(deliverMailBagVO);
					Mailbag mailbag = null;
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(deliverMailBagVO.getCompanyCode());
					long sequencenum = deliverMailBagVO.getMailSequenceNumber() == 0
							? Mailbag.findMailBagSequenceNumberFromMailIdr(deliverMailBagVO.getMailbagId(),
									deliverMailBagVO.getCompanyCode())
							: deliverMailBagVO.getMailSequenceNumber();
					mailbagPk.setMailSequenceNumber(sequencenum);
					try {
						mailbag = Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						log.error(FINDER_EXCEPTION_CAUGHT);
					}
					if (mailbag != null && mailbag.getMailCompanyCode() != null) {
						deliverMailBagVO.setMailCompanyCode(mailbag.getMailCompanyCode());
					}
					deliverMailBagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
					deliverMailBagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
					deliverMailBagVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
					deliverMailBagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
					deliverMailBagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
					deliverMailBagVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
					deliverMailBagVO.setScannedPort(airportCode);
					deliverMailBagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
					deliverMailBagVO.setPou(scannedMailDetailsVO.getPou());
					if (deliverMailBagVO.getScannedUser() != null) {
						deliverMailBagVO.setLastUpdateUser(deliverMailBagVO.getScannedUser());
					} else {
						deliverMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
						deliverMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					}
					deliverMailBagVO.setContainerType(scannedMailDetailsVO.getContainerType());
					deliverMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_DELIVERED);
				}
				MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(deliverMailBagVO);
				if (newmailbagVO != null) {
					updateExistingMailBagVO(deliverMailBagVO, newmailbagVO, false);
				}
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null) {
			scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		}
		log.debug("UploadMailDetailsCommand" + " : " + "constructArrivalSession" + " Exiting");
	}

	/**
	* Method for SaveDeliverySession
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public void saveDeliverFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveDeliverFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<MailArrivalVO> mailArrivalVOsForDeliver = null;
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				mailArrivalVOsForDeliver = makeMailArrivalVOsForDelivery(scannedMailDetailsVO, logonAttributes);
				if (mailArrivalVOsForDeliver != null && mailArrivalVOsForDeliver.size() > 0) {
					try {
						log.info("" + "saveDeliverFromUpload" + " " + mailArrivalVOsForDeliver);
						saveScannedDeliverMails(mailArrivalVOsForDeliver);
					} catch (ContainerAssignmentException e) {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
								MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
					} catch (DuplicateMailBagsException e) {
						constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
								MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
					} catch (MailbagIncorrectlyDeliveredException e) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
								MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
					} catch (InvalidFlightSegmentException e) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
								MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
					} catch (FlightClosedException e) {
						constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
					} catch (ULDDefaultsProxyException e) {
						constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
								MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
					} catch (CapacityBookingProxyException e) {
						constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
								MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
					} catch (MailBookingException e) {
						constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
								MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
					} catch (MailOperationsBusinessException e) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
								MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
					}
				}
			}
		}
		log.debug("saveDeliverSession" + " : " + "execute" + " Exiting");
	}

	/**
	* makeMailArrivalVOsForDelivery
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private Collection<MailArrivalVO> makeMailArrivalVOsForDelivery(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("MailController" + " : " + "makeMailArrivalVOsForDelivery" + " Entering");
		Collection<MailbagVO> mailbagVOsToSave = scannedMailDetailsVO.getMailDetails();
		Collection<ScannedMailDetailsVO> scannedMailsForDelivery = new ArrayList<ScannedMailDetailsVO>();
		Collection<MailArrivalVO> mailArrivalVOsForDelivery = null;
		Map<String, ScannedMailDetailsVO> scannedMailsMapForDelivery = new HashMap<String, ScannedMailDetailsVO>();
		String deliverMapKey = null;
		ScannedMailDetailsVO scannedMailDetailsVOForDelivery = null;
		if (mailbagVOsToSave != null && mailbagVOsToSave.size() > 0) {
			for (MailbagVO mailbagVOToSave : mailbagVOsToSave) {
				deliverMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
						.append(mailbagVOToSave.getCarrierCode()).append(mailbagVOToSave.getFlightNumber())
						.append(mailbagVOToSave.getFlightDate()).append(mailbagVOToSave.getCarrierId())
						.append(mailbagVOToSave.getFlightSequenceNumber()).append(mailbagVOToSave.getLegSerialNumber())
						.append(mailbagVOToSave.getPou()).append(mailbagVOToSave.getContainerNumber()).toString();
				if (!scannedMailsMapForDelivery.containsKey(deliverMapKey)) {
					scannedMailDetailsVOForDelivery = new ScannedMailDetailsVO();
					scannedMailDetailsVOForDelivery.setMailSource(scannedMailDetailsVO.getMailSource());
					scannedMailDetailsVOForDelivery.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
					scannedMailDetailsVOForDelivery.setProcessPoint(scannedMailDetailsVO.getProcessPoint());
					scannedMailDetailsVOForDelivery
							.setContainerDeliveryFlag(scannedMailDetailsVO.getContainerDeliveryFlag());
					scannedMailDetailsVOForDelivery.setOperationTime(scannedMailDetailsVO.getOperationTime());
					scannedMailDetailsVOForDelivery.setCompanyCode(mailbagVOToSave.getCompanyCode());
					scannedMailDetailsVOForDelivery.setAirportCode(scannedMailDetailsVO.getAirportCode());
					scannedMailDetailsVOForDelivery.setFlightNumber(mailbagVOToSave.getFlightNumber());
					scannedMailDetailsVOForDelivery.setFlightDate(mailbagVOToSave.getFlightDate());
					scannedMailDetailsVOForDelivery.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					scannedMailDetailsVOForDelivery.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					scannedMailDetailsVOForDelivery.setCarrierId(mailbagVOToSave.getCarrierId());
					scannedMailDetailsVOForDelivery.setCarrierCode(mailbagVOToSave.getCarrierCode());
					scannedMailDetailsVOForDelivery.setContainerNumber(mailbagVOToSave.getContainerNumber());
					scannedMailDetailsVOForDelivery.setContainerType(mailbagVOToSave.getContainerType());
					scannedMailDetailsVOForDelivery.setPol(mailbagVOToSave.getPol());
					scannedMailDetailsVOForDelivery.setPou(mailbagVOToSave.getPou());
					scannedMailDetailsVOForDelivery.setSegmentSerialNumber(mailbagVOToSave.getSegmentSerialNumber());
					scannedMailDetailsVOForDelivery.setMailDetails(new ArrayList<MailbagVO>());
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);
					scannedMailsMapForDelivery.put(deliverMapKey, scannedMailDetailsVOForDelivery);
				} else {
					scannedMailDetailsVOForDelivery = scannedMailsMapForDelivery.get(deliverMapKey);
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);
				}
			}
		}
		scannedMailsForDelivery = scannedMailsMapForDelivery.values();
		if (scannedMailsForDelivery != null && scannedMailsForDelivery.size() > 0) {
			mailArrivalVOsForDelivery = new ArrayList<MailArrivalVO>();
			MailArrivalVO mailArrivalVOForDelivery = null;
			for (ScannedMailDetailsVO mailDetailsVO : scannedMailsForDelivery) {
				mailArrivalVOForDelivery = makeMailArrivalVO(mailDetailsVO, logonAttributes, true);
				mailArrivalVOsForDelivery.add(mailArrivalVOForDelivery);
			}
		}
		log.debug("MailController" + " : " + "makeMailArrivalVOsForDelivery" + " Exiting");
		return mailArrivalVOsForDelivery;
	}

	/**
	* Added by A-5526
	* @param containerDetailsVO
	* @param logonAttributes
	* @return
	*/
	private ContainerVO constructContainerVO(String airportCode, ContainerDetailsVO containerDetailsVO,
			LoginProfile logonAttributes, String mailSource) {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		containerVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerVO.setFlightDate(containerDetailsVO.getFlightDate());
		containerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		containerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerVO.setPou(containerDetailsVO.getPou());
		containerVO.setAssignedPort(airportCode);
		containerVO.setAssignedUser(logonAttributes.getUserId());
		containerVO.setOflToRsnFlag(containerDetailsVO.isOflToRsnFlag());
		containerVO.setFoundTransfer(containerDetailsVO.isFoundTransfer());
		containerVO.setTransitFlag(containerDetailsVO.getTransitFlag());
		if (logonAttributes.getOwnAirlineCode() != null) {
			containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		} else {
			containerVO.setOwnAirlineCode(containerDetailsVO.getCarrierCode());
		}
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setOperationFlag(containerDetailsVO.getOperationFlag());
		containerVO.setType(containerDetailsVO.getContainerType());
		containerVO.setFinalDestination(containerDetailsVO.getDestination());
		containerVO.setMailSource(mailSource);
		if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
			containerVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		}
		return containerVO;
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	*/
	private OperationalFlightVO constructOperationalFlightVO(String airportCode,
			ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		operationalFlightVO.setPol(airportCode);
		operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		operationalFlightVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		if (scannedMailDetailsVO.getScannedContainerDetails() != null
				&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
			for (ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()) {
				operationalFlightVO.setOperator(containerVO.getAssignedUser());
			}
		} else {
			operationalFlightVO.setOperator(logonAttributes.getUserId());
		}
		operationalFlightVO.setOperationTime(localDateUtil.getLocalDate(airportCode, true));
		operationalFlightVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		operationalFlightVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		operationalFlightVO.setFlightStatus(scannedMailDetailsVO.getFlightStatus());
		operationalFlightVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		operationalFlightVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		operationalFlightVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		if (scannedMailDetailsVO.getPou() != null && !("").equals(scannedMailDetailsVO.getPou())) {
			operationalFlightVO.setPou(scannedMailDetailsVO.getPou());
		} else {
			operationalFlightVO.setPou(scannedMailDetailsVO.getDestination());
		}
		operationalFlightVO.setAtdCaptured(scannedMailDetailsVO.isAtdCaptured());
		return operationalFlightVO;
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	public void updateVOForTransfer(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructTransferSession" + " Entering");
		Collection<ContainerVO> transferContainerVOs = new ArrayList<ContainerVO>();
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			Collection<MailbagVO> mailBags = scannedMailDetailsVO.getMailDetails();
			log.info("" + "mailbagVOs***:>>>" + " " + mailBags);
			for (MailbagVO transferMailBagVO : mailBags) {
				if (transferMailBagVO.getMailbagId() != null && transferMailBagVO.getMailbagId().trim().length() > 0) {
					if (transferMailBagVO.getMailbagId().trim().length() == 29
							|| (isValidMailtag(transferMailBagVO.getMailbagId().length()))) {
						transferMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
						transferMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						populateMailPKFields(transferMailBagVO);
						transferMailBagVO.setScannedPort(airportCode);
						if (transferMailBagVO.getScannedUser() != null) {
							transferMailBagVO.setLastUpdateUser(transferMailBagVO.getScannedUser());
						} else {
							transferMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
							transferMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						transferMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ARRIVED);
						transferMailBagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
						transferMailBagVO.setPou(scannedMailDetailsVO.getPou());
						transferMailBagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
						transferMailBagVO.setContainerType(scannedMailDetailsVO.getContainerType());
						transferMailBagVO.setMailSource(scannedMailDetailsVO.getMailSource());
					} else {
						ContainerVO transferContainerVO = new ContainerVO();
						transferContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
						transferContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						transferContainerVO.setContainerNumber(transferMailBagVO.getMailbagId());
						transferContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						transferContainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						if (transferMailBagVO.getScannedUser() != null) {
							transferContainerVO.setAssignedUser(transferMailBagVO.getScannedUser());
							transferContainerVO.setLastUpdateUser(transferMailBagVO.getScannedUser());
						} else {
							transferContainerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
							transferContainerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						transferContainerVO.setAssignedPort(airportCode);
						transferContainerVO.setScannedDate(transferMailBagVO.getScannedDate());
						transferContainerVOs.add(transferContainerVO);
					}
					if (transferMailBagVO.getMailbagId().trim().length() == 29
							|| (isValidMailtag(transferMailBagVO.getMailbagId().length()))) {
						scannedMailDetailsVO.setContainerNumber(transferMailBagVO.getContainerNumber());
					} else {
						scannedMailDetailsVO.setContainerNumber(transferMailBagVO.getMailbagId());
					}
					scannedMailDetailsVO.setToFlightNumber(transferMailBagVO.getFlightNumber());
					scannedMailDetailsVO.setToCarrierCode(transferMailBagVO.getCarrierCode());
					scannedMailDetailsVO.setToCarrierid(scannedMailDetailsVO.getCarrierId());
					scannedMailDetailsVO.setToFlightSequenceNumber(transferMailBagVO.getFlightSequenceNumber());
					scannedMailDetailsVO.setToLegSerialNumber(transferMailBagVO.getLegSerialNumber());
					scannedMailDetailsVO.setToCarrierCode(transferMailBagVO.getCarrierCode() != null
							&& transferMailBagVO.getCarrierCode().trim().length() > 0
									? transferMailBagVO.getCarrierCode()
									: scannedMailDetailsVO.getCarrierCode());
					if (transferMailBagVO.getFlightDate() != null) {
						scannedMailDetailsVO.setToFlightDate(transferMailBagVO.getFlightDate());
					}
				}
				MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(transferMailBagVO);
				boolean updateExistigVo = true;
				if (existingMailbagVO != null && existingMailbagVO.getUldNumber() != null) {
					if (existingMailbagVO.getUldNumber().equals(existingMailbagVO.getContainerNumber())) {
						String uldFormat = new StringBuilder().append(MailConstantsVO.CONST_BULK)
								.append(MailConstantsVO.SEPARATOR).append(MailConstantsVO.CONST_BULK_ARR_ARP)
								.append(MailConstantsVO.SEPARATOR).append(existingMailbagVO.getCarrierCode())
								.toString();
						if (uldFormat.equals(existingMailbagVO.getUldNumber())) {
							updateExistigVo = false;
						}
					}
				}
				if (existingMailbagVO != null && updateExistigVo) {
					updateExistingMailBagVO(transferMailBagVO, existingMailbagVO, false);
				}
				String commodityCode = "";
				CommodityValidationVO commodityValidationVO = null;
				try {
					commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
					commodityValidationVO = validateCommodity(scannedMailDetailsVO.getCompanyCode(), commodityCode,
							transferMailBagVO.getPaCode());
				} finally {
				}
				Mailbag.validateCommodity(commodityValidationVO, transferMailBagVO);
				if (MailConstantsVO.MLD.equalsIgnoreCase(scannedMailDetailsVO.getMailSource())
						&& MailConstantsVO.MAIL_STATUS_TRANSFERRED
								.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
						&& !logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getCarrierCode())) {
					scannedMailDetailsVO.setContainerNumber(null);
					transferMailBagVO.setContainerNumber(null);
				}
			}
			if (!transferContainerVOs.isEmpty()
					|| !MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getAndroidFlag())) {
				scannedMailDetailsVO.setScannedContainerDetails(transferContainerVOs);
			}
			scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		}
		log.debug("UploadMailDetailsCommand" + " : " + "constructTransferSession" + " Exiting");
	}

	/**
	* This method constructs the MailArrivalVO for a particular session
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public MailArrivalVO makeMailArrivalVO(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			boolean isDeliveryNeeded)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("makeMailArrivalVO" + " : " + "execute" + " Entering");
		String airportCode = scannedMailDetailsVO.getAirportCode();
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALOFFSET);
		systemParameters.add(DEST_FOR_CDT_MISSING_DOM_MAL);
		String sysparfunpnts = null;
		String sysparoffset = null;
		String defDestForCdtMissingMailbag = null;
		Map<String, String> systemParameterMap;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
			if (systemParameterMap != null) {
				sysparfunpnts = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
				sysparoffset = systemParameterMap.get(AUTOARRIVALOFFSET);
				defDestForCdtMissingMailbag = systemParameterMap.get(DEST_FOR_CDT_MISSING_DOM_MAL);
			}
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		} finally {
		}
		Collection<MailbagVO> mailbagVOs = scannedMailDetailsVO.getMailDetails();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (sysparfunpnts != null && sysparoffset != null) {
					if (("N").equals(sysparoffset) && ("Y").equals(mailbagVO.getAutoArriveMail())) {
						mailArrivalVO.setArrivalDate(currentDate);
					} else
						mailArrivalVO.setArrivalDate(scannedMailDetailsVO.getFlightDate());
				}
			}
		}
		mailArrivalVO.setArrivalDate(scannedMailDetailsVO.getFlightDate());
		if (scannedMailDetailsVO.getMailSource() != null) {
			mailArrivalVO.setMailSource(scannedMailDetailsVO.getMailSource());
			mailArrivalVO.setMessageVersion(scannedMailDetailsVO.getMessageVersion());
		} else {
			mailArrivalVO.setMailSource("HHT");
		}
		if (mailArrivalVO.isDeliveryNeeded()) {
			mailArrivalVO.setMailDataSource("DLVSCN");
		} else {
			mailArrivalVO.setMailDataSource("ARRSCN");
		}
		mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailArrivalVO.setAirportCode(airportCode);
		mailArrivalVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailArrivalVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailArrivalVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailArrivalVO.setFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
		mailArrivalVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		if (scannedMailDetailsVO.getTransferFrmFlightDate() != null
				&& scannedMailDetailsVO.getTransferFrmFlightNum() != null)
			mailArrivalVO.setIsFromTruck("Y");
		if (scannedMailDetailsVO.getOperationTime() != null) {
			mailArrivalVO.setScanDate(scannedMailDetailsVO.getOperationTime());
		} else {
			mailArrivalVO.setScanDate(localDateUtil.getLocalDate(null, false));
		}
		mailArrivalVO.setScanned(true);
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if (containerDetailsVO == null) {
			containerDetailsVO = new ContainerDetailsVO();
		}
		if (containerDetailsVO.getPol() == null) {
			containerDetailsVO.setDestination(airportCode);
			containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
		}
		boolean containerFlg = true;
		ContainerAssignmentVO latestContainerAssignmentVO = null;
		String segmentOrgin = null;
		int segmentSerNum = 0;
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		Collection<FlightSegmentSummaryVO> flightSegmentsTemp = new ArrayList<FlightSegmentSummaryVO>();
		if (scannedMailDetailsVO.getFlightSequenceNumber() <= 0 && !isDeliveryNeeded) {
			constructAndRaiseException(MailHHTBusniessException.FLIGHT_MANDATORY_FOR_ARRIVAL_SCAN_CODE,
					MailHHTBusniessException.FLIGHT_MANDATORY_FOR_ARRIVAL_SCAN_CODE, scannedMailDetailsVO);
		}
		try {
			flightSegments = flightOperationsProxy.findFlightSegments(scannedMailDetailsVO.getCompanyCode(),
					scannedMailDetailsVO.getCarrierId(), scannedMailDetailsVO.getFlightNumber(),
					scannedMailDetailsVO.getFlightSequenceNumber());
			flightSegmentsTemp= mailOperationsMapper.copyFlightSegVOs(flightSegments);
		} finally {
		}
		Collection<String> routes = new ArrayList<String>();
		ArrayList<FlightSegmentSummaryVO> segmentsTemp = (ArrayList<FlightSegmentSummaryVO>) flightSegments;
		int segmentsTempLength = segmentsTemp.size();
		if (segmentsTempLength > 0) {
			routes.add(segmentsTemp.get(0).getSegmentOrigin());
			routes.add(segmentsTemp.get(0).getSegmentDestination());
		}
		if (segmentsTempLength > 1) {
			routes.add(segmentsTemp.get(1).getSegmentDestination());
		}
		if (MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			try {
				if (MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
								|| (scannedMailDetailsVO.getMailDetails() == null
										|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
					latestContainerAssignmentVO = this.cntAsgmntVO == null
							? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
							: this.cntAsgmntVO;
					this.cntAsgmntVO = latestContainerAssignmentVO;
				} else {
					latestContainerAssignmentVO = mailController
							.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				}
			} finally {
			}
			if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())
					&& (!scannedMailDetailsVO.getFlightNumber().equals(latestContainerAssignmentVO.getFlightNumber())
							|| scannedMailDetailsVO.getFlightSequenceNumber() != latestContainerAssignmentVO
									.getFlightSequenceNumber()
							|| scannedMailDetailsVO.getSegmentSerialNumber() != latestContainerAssignmentVO
									.getSegmentSerialNumber())) {
				MailbagVO mailbagVO = null;
				if (scannedMailDetailsVO.getMailDetails() != null) {
					mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
				}
				ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
				mailhistories = (ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistories(
						scannedMailDetailsVO.getCompanyCode(), mailbagVO.getMailbagId(),
						mailbagVO.getMailSequenceNumber());
				if (mailhistories != null && mailhistories.size() > 0) {
					for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
						if (mailbaghistoryvo.getFlightNumber() != null
								&& mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber())
								&& mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()
								&& (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbaghistoryvo.getMailStatus())
										|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbaghistoryvo.getMailStatus())
										|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
												.equals(mailbaghistoryvo.getMailStatus()))) {
							ContainerVO containerVO = new ContainerVO();
							containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
							containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
							containerVO.setAssignedPort(mailbaghistoryvo.getScannedPort());
							latestContainerAssignmentVO = findContainerAssignmentForUpload(containerVO);
							containerDetailsVO.setOperationFlag(STATUS_FLAG_UPDATE);
							containerDetailsVO.setContainerOperationFlag(STATUS_FLAG_UPDATE);
							if (containerDetailsVO.getSegmentSerialNumber() < 1) {
								containerDetailsVO
										.setSegmentSerialNumber(latestContainerAssignmentVO.getSegmentSerialNumber());
								scannedMailDetailsVO
										.setSegmentSerialNumber(latestContainerAssignmentVO.getSegmentSerialNumber());
							}
							containerDetailsVO.setPol(latestContainerAssignmentVO.getAirportCode());
							containerDetailsVO.setLegSerialNumber(latestContainerAssignmentVO.getLegSerialNumber());
							scannedMailDetailsVO.setPol(latestContainerAssignmentVO.getAirportCode());
							scannedMailDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
							scannedMailDetailsVO.setLegSerialNumber(latestContainerAssignmentVO.getLegSerialNumber());
							mailbagVO.setPol(scannedMailDetailsVO.getPol());
							mailbagVO.setPou(scannedMailDetailsVO.getPou());
							mailbagVO.setOperationalFlag(STATUS_FLAG_UPDATE);
							mailbagVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
						}
					}
				}
			}
			boolean uldInSameFlight = false;
			if (latestContainerAssignmentVO != null
					&& latestContainerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
					&& latestContainerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
							.getFlightSequenceNumber()
					&& latestContainerAssignmentVO.getSegmentSerialNumber() == latestContainerAssignmentVO
							.getSegmentSerialNumber()) {
				uldInSameFlight = true;
			}
			if (latestContainerAssignmentVO != null && ((latestContainerAssignmentVO.getFlightNumber() != null
					&& !latestContainerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber()))
					|| (latestContainerAssignmentVO.getFlightSequenceNumber() != scannedMailDetailsVO
							.getFlightSequenceNumber())
					|| (latestContainerAssignmentVO.getLegSerialNumber() != scannedMailDetailsVO
							.getLegSerialNumber()))) {
				containerDetailsVO.setContainerOperationFlag(null);
			}
			if (latestContainerAssignmentVO != null && !uldInSameFlight
					&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(latestContainerAssignmentVO.getTransitFlag())
					&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(latestContainerAssignmentVO.getArrivalFlag())
					&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
				latestContainerAssignmentVO = null;
			}
		}
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			if (segmentSummaryVO.getSegmentDestination().equals(airportCode)) {
				for (String route : routes) {
					if (route.equalsIgnoreCase(airportCode)) {
						segmentSerNum = segmentSummaryVO.getSegmentSerialNumber();
						segmentOrgin = segmentSummaryVO.getSegmentOrigin();
					}
				}
			}
		}
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			if (scannedMailDetailsVO.getPol() != null && segmentSummaryVO.getSegmentDestination().equals(airportCode)
					&& segmentSummaryVO.getSegmentOrigin().equals(scannedMailDetailsVO.getPol())) {
				segmentSerNum = segmentSummaryVO.getSegmentSerialNumber();
				segmentOrgin = segmentSummaryVO.getSegmentOrigin();
			}
		}
		if (!"MRD".equals(scannedMailDetailsVO.getMailSource())) {
			scannedMailDetailsVO.setPol(segmentOrgin);
		}
		if (latestContainerAssignmentVO != null) {
			if (latestContainerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
					&& latestContainerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
							.getFlightSequenceNumber()) {
				containerDetailsVO.setFlightNumber(latestContainerAssignmentVO.getFlightNumber());
				if (containerDetailsVO.getFlightDate() == null) {
					containerDetailsVO.setFlightDate(latestContainerAssignmentVO.getFlightDate());
				}
				containerDetailsVO.setFlightSequenceNumber(latestContainerAssignmentVO.getFlightSequenceNumber());
				containerDetailsVO.setLegSerialNumber(latestContainerAssignmentVO.getLegSerialNumber());
				containerDetailsVO.setDestination(latestContainerAssignmentVO.getDestination());
				if (segmentOrgin != null) {
					containerDetailsVO.setPol(segmentOrgin);
					containerDetailsVO.setSegmentSerialNumber(segmentSerNum);
				} else {
					containerDetailsVO.setPol(latestContainerAssignmentVO.getAirportCode());
					containerDetailsVO.setSegmentSerialNumber(latestContainerAssignmentVO.getSegmentSerialNumber());
				}
				scannedMailDetailsVO.setDestination(latestContainerAssignmentVO.getDestination());
				scannedMailDetailsVO.setPol(latestContainerAssignmentVO.getAirportCode());
			}
		} else {
			if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
				containerFlg = true;
				if (!MailConstantsVO.FLAG_NO.equals(scannedMailDetailsVO.getAcceptedFlag())) {
					segmentOrgin = null;
				}
			}
			if (!scannedMailDetailsVO.isFromPolExist() && segmentOrgin != null) {
				if (!"MRD".equals(scannedMailDetailsVO.getMailSource())) {
					containerDetailsVO.setPol(segmentOrgin);
				}
				containerDetailsVO.setSegmentSerialNumber(segmentSerNum);
			} else if (scannedMailDetailsVO.getMailDetails() != null
					&& scannedMailDetailsVO.getMailDetails().size() > 0) {
				MailbagVO mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
				containerDetailsVO.setPol(mailbagVO.getPol());
				containerDetailsVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			} else if (scannedMailDetailsVO.getMailDetails() != null
					&& scannedMailDetailsVO.getMailDetails().size() > 0) {
				MailbagVO mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
				containerDetailsVO.setPol(mailbagVO.getPol());
			}
			containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
			containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
			if (containerDetailsVO.getDestination() == null) {
				containerDetailsVO.setDestination(scannedMailDetailsVO.getAirportCode());
			}
		}
		if (segmentOrgin != null) {
			if (!"MRD".equals(scannedMailDetailsVO.getMailSource())) {
				containerDetailsVO.setPol(segmentOrgin);
			}
			containerDetailsVO.setSegmentSerialNumber(segmentSerNum);
		}
		if (latestContainerAssignmentVO != null && ((latestContainerAssignmentVO.getFlightNumber() != null
				&& latestContainerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber()))
				&& (latestContainerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
						.getFlightSequenceNumber())
				&& (latestContainerAssignmentVO.getLegSerialNumber() != scannedMailDetailsVO.getLegSerialNumber()))) {
			containerDetailsVO.setContainerOperationFlag(null);
			containerDetailsVO.setPol(latestContainerAssignmentVO.getAirportCode());
			containerDetailsVO.setSegmentSerialNumber(latestContainerAssignmentVO.getSegmentSerialNumber());
			containerDetailsVO.setLegSerialNumber(latestContainerAssignmentVO.getLegSerialNumber());
		} else if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			MailbagVO mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
			if (mailbagVO.getPol() != null && mailbagVO.getPol().length() > 0
					&& !"Y".equals(mailArrivalVO.getIsFromTruck())) {
				containerDetailsVO.setPol(mailbagVO.getPol());
			}
		}
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
			containerDetailsVO.setContainerNumber(constructBulkULDNumber(airportCode));
		} else {
			containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		}
		if (isDeliveryNeeded) {
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
		}
		if (scannedMailDetailsVO.isFoundArrival()) {
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		} else {
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
		}
		if (scannedMailDetailsVO.getIsContainerPabuilt() != null
				&& !scannedMailDetailsVO.getIsContainerPabuilt().isEmpty()) {
			containerDetailsVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
			mailArrivalVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
		}
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		if ((MailConstantsVO.MLD.equalsIgnoreCase(scannedMailDetailsVO.getMailSource())
				&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerArrivalFlag())
						|| MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())))
				|| MailConstantsVO.FLAG_YES.equalsIgnoreCase(scannedMailDetailsVO.getAndroidFlag())) {
			Collection<ContainerDetailsVO> conatinerDetailsForArrival = null;
			Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
			containers.add(containerDetailsVO);
			try {
				conatinerDetailsForArrival = mailController.findMailbagsInContainer(containers);
			} finally {
			}
			if (conatinerDetailsForArrival != null && conatinerDetailsForArrival.size() > 0) {
				if (mailbagArriveVOs != null && mailbagArriveVOs.size() > 0) {
					mailbagArriveVOs.addAll(conatinerDetailsForArrival.iterator().next().getMailDetails());
				} else if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(scannedMailDetailsVO.getAndroidFlag())) {
					mailbagArriveVOs = new ArrayList<MailbagVO>();
					mailbagArriveVOs.addAll(conatinerDetailsForArrival.iterator().next().getMailDetails());
				} else
					mailbagArriveVOs = conatinerDetailsForArrival.iterator().next().getMailDetails();
			}
		}
		if (mailbagArriveVOs != null && mailbagArriveVOs.size() > 0) {
			for (MailbagVO mailbagToArrive : mailbagArriveVOs) {
				if (mailbagToArrive.getActionMode() == null || mailbagToArrive.getActionMode().trim().length() == 0) {
					mailbagToArrive.setActionMode(scannedMailDetailsVO.getProcessPoint());
				}
				if (mailbagToArrive.getMailClass() == null || mailbagToArrive.getMailClass().trim().length() == 0) {
					mailbagToArrive.setMailClass(mailbagToArrive.getMailSubclass().substring(0, 1));
				}
				mailbagToArrive.setCompanyCode(logonAttributes.getCompanyCode());
				mailbagToArrive.setCarrierCode(containerDetailsVO.getCarrierCode());
				mailbagToArrive.setCarrierId(containerDetailsVO.getCarrierId());
				mailbagToArrive.setFlightNumber(containerDetailsVO.getFlightNumber());
				mailbagToArrive.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				if (mailbagToArrive.getFlightDate() == null) {
					mailbagToArrive.setFlightDate(containerDetailsVO.getFlightDate());
				}
				mailbagToArrive.setContainerNumber(containerDetailsVO.getContainerNumber());
				mailbagToArrive.setContainerType(containerDetailsVO.getContainerType());
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
					mailbagToArrive.setUldNumber(containerDetailsVO.getContainerNumber());
				} else {
					mailbagToArrive.setUldNumber(constructBulkULDNumber(airportCode));
				}
				mailbagToArrive.setPou(containerDetailsVO.getPou());
				mailbagToArrive.setPol(containerDetailsVO.getPol());
				if (mailbagToArrive.getPaBuiltFlag() != null
						&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagToArrive.getPaBuiltFlag())) {
					containerDetailsVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
					containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
					mailArrivalVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				}
				if (mailbagToArrive.getVolume() == null) {
					String commodityCode = "";
					CommodityValidationVO commodityValidationVO = null;
					try {
						commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
						commodityValidationVO = validateCommodity(scannedMailDetailsVO.getCompanyCode(), commodityCode,
								mailbagToArrive.getPaCode());
					} finally {
					}
					Mailbag.validateCommodity(commodityValidationVO, mailbagToArrive);
				}
				mailbagToArrive.setScannedPort(airportCode);
				if (mailbagToArrive.getScannedUser() == null || mailbagToArrive.getScannedUser().trim().length() == 0) {
					mailbagToArrive.setScannedUser(logonAttributes.getUserId().toUpperCase());
				}
				if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getAndroidFlag())
						&& scannedMailDetailsVO.getOperationTime() != null) {
					mailbagToArrive.setScannedDate(scannedMailDetailsVO.getOperationTime());
				}
				mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_YES);
				mailbagToArrive.setDeliveredFlag(MailConstantsVO.FLAG_NO);
				if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
					mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_NO);
				}
				long mailSequenceNumber = 0;
				mailArrivalVO.setFoundResditSent(mailbagToArrive.isFoundResditSent());
				Mailbag mailBag = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagToArrive.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailbagToArrive.getMailSequenceNumber() == 0
						? findMailSequenceNumber(mailbagToArrive.getMailbagId(), mailbagToArrive.getCompanyCode())
						: mailbagToArrive.getMailSequenceNumber());
				try {
					mailBag = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					log.error(FINDER_EXCEPTION_CAUGHT);
				}
				if (mailBag != null && (mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED))
						|| (mailBag != null && mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)
								&& mailBag.getScannedPort().equals(mailbagToArrive.getScannedPort()))) {
					mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_YES);
				}
				mailbagToArrive.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
				if (mailbagToArrive.getSegmentSerialNumber() > 0) {
					if (scannedMailDetailsVO.isFoundArrival()
							&& (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBag.getLatestStatus())
									|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailBag.getLatestStatus()))) {
						mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					} else if (!scannedMailDetailsVO.isFoundArrival()
							&& (scannedMailDetailsVO.getMailSource() != null
									&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN))
							&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailBag.getLatestStatus())) {
						mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					} else {
						if (scannedMailDetailsVO.getContainerNumber() != null
								&& mailBag.getUldNumber().equalsIgnoreCase(scannedMailDetailsVO.getContainerNumber())) {
							mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
						}
					}
					if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBag.getLatestStatus())) {
						mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					}
					if (isDeliveryNeeded) {
						mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
					} else {
						mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
					}
				} else {
					if (mailBag != null && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailBag.getLatestStatus())
							&& scannedMailDetailsVO.getContainerNumber() != null
							&& mailBag.getUldNumber().equalsIgnoreCase(scannedMailDetailsVO.getContainerNumber()))
						mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					else
						mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					mailbagToArrive.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
				}
				getMailUploadInstance().setStoragUnitForDnataSpecific(mailbagToArrive, scannedMailDetailsVO);
				if (Objects.nonNull(defDestForCdtMissingMailbag)
						&& defDestForCdtMissingMailbag.equals(mailbagToArrive.getDestination())
						&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
						&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())) {
					mailbagToArrive.setDestination(mailbagToArrive.getScannedPort());
					mailbagToArrive.setDoe(findOfficeOfExchangeForCarditMissingDomMail(mailbagToArrive.getCompanyCode(),
							mailbagToArrive.getDestination()));
					mailbagToArrive.setNeedDestUpdOnDlv(true);
				}
				if (scannedMailDetailsVO.getTransactionLevel() != null
						&& "U".equals(scannedMailDetailsVO.getTransactionLevel())) {
					mailbagToArrive.setTransactionLevel(scannedMailDetailsVO.getTransactionLevel());
				}
			}
		}
		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		boolean deliverWithArrival = false;
		if (mailbagArriveVOs != null && mailbagArriveVOs.size() > 0) {
			for (MailbagVO mailbgVO : mailbagArriveVOs) {
				int numBags = 0;
				double bagWgt = 0;
				String outerpk = mailbgVO.getOoe() + mailbgVO.getDoe() + mailbgVO.getMailSubclass()
						+ mailbgVO.getMailCategoryCode() + mailbgVO.getDespatchSerialNumber() + mailbgVO.getYear();
				if (dsnMap.get(outerpk) == null) {
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbgVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbgVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbgVO.getDoe());
					dsnVO.setMailClass(mailbgVO.getMailSubclass().substring(0, 1));
					dsnVO.setMailSubclass(mailbgVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbgVO.getMailCategoryCode());
					if (scannedMailDetailsVO.isFoundArrival()
							&& MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbgVO.getMailStatus())) {
						dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					} else {
						dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					}
					dsnVO.setYear(mailbgVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					for (MailbagVO innerVO : mailbagArriveVOs) {
						String innerpk = innerVO.getOoe() + innerVO.getDoe() + innerVO.getMailSubclass()
								+ innerVO.getMailCategoryCode() + innerVO.getDespatchSerialNumber() + innerVO.getYear();
						if (outerpk.equals(innerpk)) {
							numBags = numBags + 1;
							bagWgt = bagWgt + innerVO.getWeight().getValue().doubleValue();
						}
					}
					if (mailbgVO.getArrivedFlag().equals(MailConstantsVO.FLAG_YES)
							&& mailbgVO.getActionMode().equals(MailConstantsVO.MAIL_STATUS_DELIVERED)) {
						dsnVO.setPrevReceivedBags(numBags);
						dsnVO.setPrevReceivedWeight(mailbgVO.getWeight());
						deliverWithArrival = true;
					}
					dsnVO.setReceivedBags(numBags);
					dsnVO.setReceivedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(bagWgt)));
					dsnMap.put(outerpk, dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for (String key : dsnMap.keySet()) {
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getReceivedBags();
			totWgt = totWgt + dsnVO.getReceivedWeight().getRoundedValue().doubleValue();
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		containerDetailsVO.setDsnVOs(newDSNVOs);
		if (!deliverWithArrival) {
			containerDetailsVO.setReceivedBags(totBags);
			containerDetailsVO
					.setReceivedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totWgt)));
		}
		containerDetailsVOs.add(containerDetailsVO);
		if (isDeliveryNeeded) {
			mailArrivalVO.setPartialDelivery(false);
		}
		mailArrivalVO.setContainerDetails(containerDetailsVOs);
		log.debug("makeMailArrivalVO" + " : " + "execute" + " Exiting");
		if (containerFlg) {
			return mailArrivalVO;
		}
		return null;
	}

	/**
	* Method for Construct Bulk ULD Number
	* @param airport
	* @return
	*/
	private String constructBulkULDNumber(String airport) {
		String bulkULDNumber = "";
		if (airport != null && airport.trim().length() > 0) {
			bulkULDNumber = new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		}
		return bulkULDNumber;
	}

	/**
	* Added by A-5526 This method constructs the session for Offload
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param oneTimes
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void updateVOForOffload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			Map<String, Collection<OneTimeVO>> oneTimes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructOffloadSession" + " Entering");
		Collection<OneTimeVO> offloadOneTimeVOs = new ArrayList<OneTimeVO>();
		Collection<ContainerVO> offloadedContainerVOs = new ArrayList<ContainerVO>();
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
				|| (scannedMailDetailsVO.getMailSource() != null
						&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN)))
								? scannedMailDetailsVO.getAirportCode()
								: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			Collection<MailbagVO> mailBags = scannedMailDetailsVO.getMailDetails();
			for (MailbagVO offloadedMailBagVO : mailBags) {
				if ((MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED))) {
					ContainerAssignmentVO containerAssignmentVO = null;
					if (offloadedMailBagVO.getMailbagId() == null
							|| offloadedMailBagVO.getMailbagId().trim().length() == 0) {
						offloadedMailBagVO.setMailbagId(offloadedMailBagVO.getContainerNumber());
					}
					if (offloadedMailBagVO.getMailbagId().length() != 29
							&& !(isValidMailtag(offloadedMailBagVO.getMailbagId().length()))) {
						scannedMailDetailsVO.setContainerNumber(offloadedMailBagVO.getMailbagId());
						scannedMailDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
						try {
							containerAssignmentVO = mailController
									.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
						} finally {
						}
					}
					if (containerAssignmentVO != null) {
						if ((scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED))) {
							updateDetailsWithContainerAssignmentInfo(scannedMailDetailsVO, offloadedMailBagVO,
									containerAssignmentVO, airportCode);
						}
					}
				}
				if (offloadedMailBagVO.getMailbagId() != null
						&& offloadedMailBagVO.getMailbagId().trim().length() > 0) {
					if (offloadedMailBagVO.getMailbagId().trim().length() == 29
							|| isValidMailtag(offloadedMailBagVO.getMailbagId().trim().length())) {
						offloadedMailBagVO.setCompanyCode(logonAttributes.getCompanyCode());
						offloadedMailBagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						populateMailPKFields(offloadedMailBagVO);
						offloadedMailBagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						offloadedMailBagVO.setScannedPort(airportCode);
						if (offloadedMailBagVO.getScannedUser() != null) {
							offloadedMailBagVO.setLastUpdateUser(offloadedMailBagVO.getScannedUser());
						} else {
							offloadedMailBagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
							offloadedMailBagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						offloadedMailBagVO.setActionMode(MailConstantsVO.MAIL_STATUS_OFFLOADED);
						offloadedMailBagVO.setPol(airportCode);
						if (oneTimes != null) {
							offloadOneTimeVOs = oneTimes.get(OFL_REASONCODE);
						}
						if ("WS".equals(offloadedMailBagVO.getMailSource())) {
							boolean offloadReasonValid = false;
							for (OneTimeVO offloadOneTimeVO : offloadOneTimeVOs) {
								if (offloadOneTimeVO.getFieldValue().equals(offloadedMailBagVO.getOffloadedReason())) {
									offloadedMailBagVO.setOffloadedDescription(offloadOneTimeVO.getFieldDescription());
									offloadedMailBagVO.setOffloadedReason(offloadOneTimeVO.getFieldValue());
									offloadReasonValid = true;
									break;
								}
							}
							if (!offloadReasonValid) {
								constructAndRaiseException(MailMLDBusniessException.INVALID_OFFLOAD_REASONCODE,
										MailHHTBusniessException.INVALID_OFFLOAD_REASON_CODE, scannedMailDetailsVO);
							}
						} else {
							for (OneTimeVO offloadOneTimeVO : offloadOneTimeVOs) {
								if (offloadOneTimeVO.getFieldDescription()
										.equals(offloadedMailBagVO.getOffloadedReason())) {
									offloadedMailBagVO.setOffloadedDescription(offloadOneTimeVO.getFieldDescription());
									offloadedMailBagVO.setOffloadedReason(offloadOneTimeVO.getFieldValue());
								}
							}
						}
						offloadedMailBagVO.setOffloadedRemarks(offloadedMailBagVO.getOffloadedRemarks());
						offloadedMailBagVO.setIsoffload(true);
						if (offloadedMailBagVO.getOffloadedReason() == null
								|| offloadedMailBagVO.getOffloadedReason().trim().length() == 0) {
							offloadedMailBagVO.setOffloadedReason(offloadedMailBagVO.getOffloadedReason());
							offloadedMailBagVO.setOffloadedDescription(offloadedMailBagVO.getOffloadedDescription());
						}
						offloadedMailBagVO.setMailSource(offloadedMailBagVO.getMailSource());
						MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(offloadedMailBagVO);
						if (existingMailbagVO != null) {
							updateExistingMailBagVO(offloadedMailBagVO, existingMailbagVO, true);
						}
					} else {
						if (MailConstantsVO.BULK_TYPE.equals(offloadedMailBagVO.getContainerType())) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_OFFLOAD_ULD_TYPE,
									MailHHTBusniessException.OFFLOAD_OF_ULD_AS_BARROW_NOT_POSSIBLE,
									scannedMailDetailsVO);
						}
						ContainerVO offloadedContainerDetailsVO = new ContainerVO();
						offloadedContainerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
						offloadedContainerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						offloadedContainerDetailsVO.setContainerNumber(offloadedMailBagVO.getMailbagId());
						offloadedContainerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						offloadedContainerDetailsVO.setFlightNumber(offloadedMailBagVO.getFlightNumber());
						offloadedContainerDetailsVO
								.setFlightSequenceNumber(offloadedMailBagVO.getFlightSequenceNumber());
						offloadedContainerDetailsVO.setLegSerialNumber(offloadedMailBagVO.getLegSerialNumber());
						offloadedContainerDetailsVO.setCarrierId(offloadedMailBagVO.getCarrierId());
						offloadedContainerDetailsVO.setSegmentSerialNumber(offloadedMailBagVO.getSegmentSerialNumber());
						offloadedContainerDetailsVO.setAcceptanceFlag(offloadedMailBagVO.getAcceptanceFlag());
						offloadedContainerDetailsVO.setType(offloadedMailBagVO.getContainerType());
						offloadedContainerDetailsVO.setCarrierCode(offloadedMailBagVO.getCarrierCode());
						offloadedContainerDetailsVO.setFlightDate(offloadedMailBagVO.getFlightDate());
						offloadedContainerDetailsVO.setMailSource(scannedMailDetailsVO.getMailSource());
						if (offloadedMailBagVO.getScannedUser() != null) {
							offloadedContainerDetailsVO.setAssignedUser(offloadedMailBagVO.getScannedUser());
							offloadedContainerDetailsVO.setLastUpdateUser(offloadedMailBagVO.getScannedUser());
						} else {
							offloadedContainerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
							offloadedContainerDetailsVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
						}
						offloadedContainerDetailsVO.setAssignedPort(airportCode);
						offloadedContainerDetailsVO.setPou(scannedMailDetailsVO.getPou());
						offloadedContainerDetailsVO.setPol(scannedMailDetailsVO.getAirportCode());
						if (oneTimes != null) {
							offloadOneTimeVOs = oneTimes.get(OFL_REASONCODE);
						}
						if ("WS".equals(offloadedMailBagVO.getMailSource())) {
							for (OneTimeVO offloadOneTimeVO : offloadOneTimeVOs) {
								if (offloadOneTimeVO.getFieldValue().equals(offloadedMailBagVO.getOffloadedReason())) {
									offloadedContainerDetailsVO
											.setOffloadedDescription(offloadOneTimeVO.getFieldDescription());
									offloadedContainerDetailsVO.setOffloadedReason(offloadOneTimeVO.getFieldValue());
									break;
								}
							}
						} else {
							for (OneTimeVO offloadOneTimeVO : offloadOneTimeVOs) {
								if (offloadOneTimeVO.getFieldDescription()
										.equals(offloadedMailBagVO.getOffloadedReason())) {
									offloadedContainerDetailsVO
											.setOffloadedDescription(offloadOneTimeVO.getFieldDescription());
									offloadedContainerDetailsVO.setOffloadedReason(offloadOneTimeVO.getFieldValue());
								}
							}
						}
						offloadedContainerDetailsVO.setOffloadedRemarks(offloadedMailBagVO.getOffloadedRemarks());
						offloadedContainerDetailsVO.setOffload(true);
						offloadedContainerDetailsVO.setScannedDate(offloadedMailBagVO.getScannedDate());
						if (offloadedContainerDetailsVO.getOffloadedReason() == null
								|| offloadedContainerDetailsVO.getOffloadedReason().trim().length() == 0) {
							offloadedContainerDetailsVO.setOffloadedReason(offloadedMailBagVO.getOffloadedReason());
							offloadedContainerDetailsVO
									.setOffloadedDescription(offloadedMailBagVO.getOffloadedDescription());
						}
						offloadedContainerVOs.add(offloadedContainerDetailsVO);
					}
					scannedMailDetailsVO.setScannedContainerDetails(offloadedContainerVOs);
					if (offloadedMailBagVO.getMailbagId().trim().length() == 29) {
						scannedMailDetailsVO.setContainerNumber(offloadedMailBagVO.getContainerNumber());
						scannedMailDetailsVO.setContainerType(offloadedMailBagVO.getContainerType());
					} else {
						scannedMailDetailsVO.setContainerNumber(offloadedMailBagVO.getMailbagId());
					}
					scannedMailDetailsVO.setPol(airportCode);
					scannedMailDetailsVO.setPou(offloadedMailBagVO.getPou());
				}
			}
		}
		int size = 0;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			size = scannedMailDetailsVO.getMailDetails().size();
		}
		if (scannedMailDetailsVO.getScannedContainerDetails() != null
				&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
			size = size + scannedMailDetailsVO.getScannedContainerDetails().size();
		}
		scannedMailDetailsVO.setScannedBags(size);
		log.debug("UploadMailDetailsCommand" + " : " + "constructOffloadSession" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void saveOffloadFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveOffloadFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<OffloadVO> offloadVOsForSave = null;
			try {
				if ((scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0)
						|| (scannedMailDetailsVO.getScannedContainerDetails() != null
								&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0)) {
					if (scannedMailDetailsVO.getScannedContainerDetails() != null
							&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
						if (scannedMailDetailsVO.isContOffloadReq()) {
							scannedMailDetailsVO.setMailDetails(null);
						}
					}
					offloadVOsForSave = makeOffloadVOs(scannedMailDetailsVO, logonAttributes);
				}
				log.info("" + "offloadVOsForSave" + " " + offloadVOsForSave);
				if (offloadVOsForSave != null && offloadVOsForSave.size() > 0) {
					saveScannedOffloadMails(offloadVOsForSave);
				}
			} catch (FlightClosedException e) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			} catch (FlightDepartedException e) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_DEPARTED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_DEPARTED_EXCEPTION, scannedMailDetailsVO);
			} catch (ReassignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.REASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.REASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (ULDDefaultsProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
						MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
			} catch (CapacityBookingProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
						MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (MailBookingException e) {
				constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
						MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
			}
		}
		log.debug("saveOffloadSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Added by A-5526 makeOffloadVOs
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @return
	*/
	public Collection<OffloadVO> makeOffloadVOs(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes) {
		log.debug("MailController" + " : " + "makeOffloadVOs" + " Entering");
		Collection<OffloadVO> offloadVOsForSave = new ArrayList<OffloadVO>();
		Collection<OffloadVO> offloadMailVOsForSave = null;
		Collection<OffloadVO> offloadcontainerVOsForSave = null;
		Collection<MailbagVO> mailbagVOsToSave = scannedMailDetailsVO.getMailDetails();
		Collection<ContainerVO> containerVOsToSave = scannedMailDetailsVO.getScannedContainerDetails();
		Map<String, OffloadVO> mailOffloadMap = new HashMap<String, OffloadVO>();
		Map<String, OffloadVO> containerOffloadMap = new HashMap<String, OffloadVO>();
		String mailOffloadMapKey = null;
		String containerOffloadMapKey = null;
		OffloadVO offloadVO = null;
		boolean fltClosureChkNotReq = "OPSOFL".equals(scannedMailDetailsVO.getMailSource()) ? true : false;
		if (mailbagVOsToSave != null && mailbagVOsToSave.size() > 0
				&& (mailbagVOsToSave.iterator().next().getDespatchId() != null
						&& mailbagVOsToSave.iterator().next().getDespatchId().length() > 0)) {
			for (MailbagVO mailbagVOToSave : mailbagVOsToSave) {
				mailOffloadMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
						.append(mailbagVOToSave.getCarrierCode()).append(mailbagVOToSave.getFlightNumber())
						.append(mailbagVOToSave.getFlightDate()).append(mailbagVOToSave.getCarrierId())
						.append(mailbagVOToSave.getFlightSequenceNumber()).append(mailbagVOToSave.getLegSerialNumber())
						.toString();
				if (!mailOffloadMap.containsKey(mailOffloadMapKey)) {
					offloadVO = new OffloadVO();
					offloadVO.setDepartureOverride(true);
					mailbagVOToSave.setIsoffload(true);
					offloadVO.setCarrierCode(mailbagVOToSave.getCarrierCode());
					offloadVO.setCarrierId(mailbagVOToSave.getCarrierId());
					offloadVO.setCompanyCode(mailbagVOToSave.getCompanyCode());
					offloadVO.setFlightDate(mailbagVOToSave.getFlightDate());
					offloadVO.setFlightNumber(mailbagVOToSave.getFlightNumber());
					offloadVO.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					offloadVO.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					offloadVO.setPol(mailbagVOToSave.getPol());
					offloadVO.setOffloadMailbags(new Page<MailbagVO>(new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0, false));
					offloadVO.getOffloadMailbags().add(mailbagVOToSave);
					if (mailbagVOToSave.getScannedUser() != null) {
						offloadVO.setUserCode(mailbagVOToSave.getScannedUser());
					} else {
						offloadVO.setUserCode(logonAttributes.getUserId());
					}
					offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_MAILBAG);
					offloadVO.setFltClosureChkNotReq(fltClosureChkNotReq);
					if (fltClosureChkNotReq && !scannedMailDetailsVO.isContOffloadReq()) {
						containerVOsToSave = null;
					}
					mailOffloadMap.put(mailOffloadMapKey, offloadVO);
				} else {
					offloadVO = mailOffloadMap.get(mailOffloadMapKey);
					mailbagVOToSave.setIsoffload(true);
					offloadVO.getOffloadMailbags().add(mailbagVOToSave);
				}
			}
		}
		if (containerVOsToSave != null && containerVOsToSave.size() > 0) {
			for (ContainerVO containerVOTosave : containerVOsToSave) {
				containerOffloadMapKey = new StringBuilder(containerVOTosave.getCompanyCode())
						.append(containerVOTosave.getCarrierId()).append(containerVOTosave.getCarrierCode())
						.append(containerVOTosave.getFlightNumber()).append(containerVOTosave.getFlightDate())
						.append(containerVOTosave.getFlightSequenceNumber())
						.append(containerVOTosave.getLegSerialNumber()).toString();
				if (!containerOffloadMap.containsKey(containerOffloadMapKey)) {
					offloadVO = new OffloadVO();
					offloadVO.setDepartureOverride(true);
					offloadVO.setCarrierCode(containerVOTosave.getCarrierCode());
					offloadVO.setCarrierId(containerVOTosave.getCarrierId());
					offloadVO.setCompanyCode(containerVOTosave.getCompanyCode());
					offloadVO.setFlightDate(containerVOTosave.getFlightDate());
					offloadVO.setFlightNumber(containerVOTosave.getFlightNumber());
					offloadVO.setFlightSequenceNumber(containerVOTosave.getFlightSequenceNumber());
					offloadVO.setLegSerialNumber(containerVOTosave.getLegSerialNumber());
					offloadVO.setPol(containerVOTosave.getAssignedPort());
					offloadVO.setFltClosureChkNotReq(fltClosureChkNotReq);
					offloadVO.setOffloadContainers(new ArrayList<ContainerVO>());
					containerVOTosave.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
					offloadVO.getOffloadContainers().add(containerVOTosave);
					if (containerVOTosave.getAssignedUser() != null) {
						offloadVO.setUserCode(containerVOTosave.getAssignedUser());
					} else {
						offloadVO.setUserCode(logonAttributes.getUserId());
					}
					offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_CONTAINER);
					containerOffloadMap.put(containerOffloadMapKey, offloadVO);
				} else {
					offloadVO = containerOffloadMap.get(containerOffloadMapKey);
					offloadVO.getOffloadContainers().add(containerVOTosave);
				}
			}
		}
		offloadMailVOsForSave = mailOffloadMap.values();
		offloadcontainerVOsForSave = containerOffloadMap.values();
		offloadVOsForSave.addAll(offloadMailVOsForSave);
		offloadVOsForSave.addAll(offloadcontainerVOsForSave);
		log.debug("MailController" + " : " + "makeOffloadVOs" + " Exiting");
		return offloadVOsForSave;
	}

	/**
	* Added by A-5526
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param oneTimes
	* @return
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void updateVOForDammageCapture(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			Map<String, Collection<OneTimeVO>> oneTimes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructDamagedMailSession" + " Entering");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		String airportCode = (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (MailConstantsVO.MTKMALUPLJOB.equals(scannedMailDetailsVO.getMailSource())) {
			if (scannedMailDetailsVO.getAirportCode() != null) {
				airportCode = scannedMailDetailsVO.getAirportCode();
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO damageMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				String finalDestination = damageMailbagVO.getPou();
				if (damageMailbagVO.getMailbagId() != null && damageMailbagVO.getMailbagId().trim().length() > 0) {
					if ("WS".equals(scannedMailDetailsVO.getMailSource())
							&& damageMailbagVO.getReturnedReason() == null) {
						constructAndRaiseException(MailMLDBusniessException.MAIL_DAMAGE_CODE_IS_MANDATORY,
								MailHHTBusniessException.MAIL_DAMAGE_CODE_IS_MANDATORY, scannedMailDetailsVO);
					}
					damageMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					damageMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					damageMailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					damageMailbagVO.setScannedPort(airportCode);
					if (damageMailbagVO.getScannedUser() != null) {
						damageMailbagVO.setLastUpdateUser(damageMailbagVO.getScannedUser());
					} else {
						damageMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
						damageMailbagVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					}
					damageMailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_DAMAGED);
					populateMailPKFields(damageMailbagVO);
					if (damageMailbagVO.getDamagedMailbags() != null
							&& damageMailbagVO.getDamagedMailbags().size() > 0) {
						damageMailbagVO.getDamagedMailbags()
								.add(constructDamageDetails(scannedMailDetailsVO.getProcessPoint(),
										damageMailbagVO.getReturnedReason(), damageMailbagVO.getReturnedRemarks(),
										logonAttributes, oneTimes, airportCode, scannedMailDetailsVO));
					} else {
						damageMailbagVO.setDamagedMailbags(new ArrayList<DamagedMailbagVO>());
						damageMailbagVO.getDamagedMailbags()
								.add(constructDamageDetails(scannedMailDetailsVO.getProcessPoint(),
										damageMailbagVO.getReturnedReason(), damageMailbagVO.getReturnedRemarks(),
										logonAttributes, oneTimes, airportCode, scannedMailDetailsVO));
					}
					ArrayList<DamagedMailbagVO> damagedMailbagVOs = (ArrayList<DamagedMailbagVO>) damageMailbagVO
							.getDamagedMailbags();
					if (damagedMailbagVOs != null && damagedMailbagVOs.size() > 0) {
						for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
							if (damageMailbagVO.getScannedUser() != null) {
								damagedMailbagVO.setUserCode(damageMailbagVO.getScannedUser());
							}
							log.info("" + "Setting scanner user id:-" + " " + damagedMailbagVO);
						}
					}
					String country = damageMailbagVO.getMailbagId().substring(0, 2);
					String paCode = damageMailbagVO.getPaCode() != null
							&& damageMailbagVO.getPaCode().trim().length() > 0 ? damageMailbagVO.getPaCode()
									: mailController.findPAForOfficeOfExchange(damageMailbagVO.getCompanyCode(),
											damageMailbagVO.getOoe());
					if (paCode != null && !"".equals(paCode)) {
						damageMailbagVO.setPaCode(paCode);
						Collection<DamagedMailbagVO> damagedMails = damageMailbagVO.getDamagedMailbags();
						if (damagedMails != null && damagedMails.size() > 0) {
							for (DamagedMailbagVO damagedMail : damagedMails) {
								damagedMail.setPaCode(damageMailbagVO.getPaCode());
								damagedMail.setOperationType(damageMailbagVO.getOperationalStatus());
								if (damageMailbagVO.getPaBuiltFlag() != null) {
									if (damageMailbagVO.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
										damageMailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
									}
								}
							}
						}
					}
				}
				boolean paFlag = false;
				String paCode = null;
				if (damageMailbagVO.getPaBuiltFlag() != null
						&& damageMailbagVO.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
					paFlag = true;
					paCode = damageMailbagVO.getPaCode();
				}
				log.info("" + "damageMailbagVO>>>>>>>>" + " " + damageMailbagVO);
				MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(damageMailbagVO);
				log.info("" + "newmailbagVO>>>>>>>>" + " " + newmailbagVO);
				if (newmailbagVO != null) {
					if (paFlag) {
						newmailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
						newmailbagVO.setPaCode(paCode);
					} else if (newmailbagVO.getConsignmentNumber() != null
							&& newmailbagVO.getConsignmentNumber().trim().length() > 0) {
						newmailbagVO.setPaBuiltFlag(null);
						newmailbagVO.setPaCode(null);
					}
					if (damageMailbagVO.getFlightSequenceNumber() == 0 && damageMailbagVO.getFlightNumber() != null
							&& damageMailbagVO.getFlightNumber().trim().length() == 0) {
						newmailbagVO.setPou(newmailbagVO.getFinalDestination());
					}
					if (MailConstantsVO.FLAG_YES.equals(damageMailbagVO.getDamageFlag())) {
						newmailbagVO.setDamageFlag(damageMailbagVO.getDamageFlag());
					}
					updateExistingMailBagVO(damageMailbagVO, newmailbagVO, false);
					if (MailConstantsVO.BULK_TYPE.equals(damageMailbagVO.getContainerType())) {
						damageMailbagVO.setUldNumber(damageMailbagVO.getContainerNumber());
						damageMailbagVO.setFromReturnPopUp(true);
					}
					damageMailbagVO.setFinalDestination(finalDestination);
				}
			}
		}
		scannedMailDetailsVO.setScannedBags(scannedMailDetailsVO.getMailDetails().size());
		log.debug("UploadMailDetailsCommand" + " : " + "constructArrivalSession" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param processPoint
	* @param damageCode
	* @param remarks
	* @param logonAttributes
	* @param oneTimes
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private DamagedMailbagVO constructDamageDetails(String processPoint, String damageCode, String remarks,
			LoginProfile logonAttributes, Map<String, Collection<OneTimeVO>> oneTimes, String airportCode,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("UploadMailDetailsCommand" + " : " + "constructDamageDetails" + " Entering");
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		Collection<OneTimeVO> damageOneTimeVOs = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			damageOneTimeVOs = oneTimes.get(DMG_RET_REASONCODE);
		}
		damagedMailbagVO.setDamageCode(damageCode);
		boolean isDamageCodeValid = false;
		for (OneTimeVO damageOneTimeVO : damageOneTimeVOs) {
			if (damageOneTimeVO.getFieldValue().equals(damageCode)) {
				damagedMailbagVO.setDamageDescription(damageOneTimeVO.getFieldDescription());
				isDamageCodeValid = true;
			}
		}
		if (!isDamageCodeValid && MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
			constructAndRaiseException(MailMLDBusniessException.MAIL_CANNOT_DAMAGED,
					MailHHTBusniessException.INVALID_DAMAGE_REASON_CODE, scannedMailDetailsVO);
		} else if (!isDamageCodeValid
				&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
			constructAndRaiseException(MailMLDBusniessException.MAIL_CANNOT_RETURN,
					MailHHTBusniessException.INVALID_RETURN_REASON_CODE, scannedMailDetailsVO);
		}
		damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		damagedMailbagVO.setAirportCode(airportCode);
		damagedMailbagVO.setDamageDate(localDateUtil.getLocalDate(airportCode, true));
		damagedMailbagVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(processPoint)) {
			damagedMailbagVO.setReturnedFlag(MailConstantsVO.FLAG_YES);
		} else {
			damagedMailbagVO.setReturnedFlag(MailConstantsVO.FLAG_NO);
		}
		damagedMailbagVO.setRemarks(remarks);
		damagedMailbagVO.setUserCode(logonAttributes.getUserId());
		log.debug("UploadMailDetailsCommand" + " : " + "constructDamageDetails" + " Exiting");
		return damagedMailbagVO;
	}

	/**
	* Method to get the AirlineValidationVO
	* @param companyCode
	* @param alphaCode
	* @return airlineValidationVO
	*/
	private AirlineValidationVO populateAirlineValidationVO(String companyCode, String alphaCode) {
		log.debug("UploadMailDetailsCommand" + " : " + "populateAirlineValidationVO" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineProxy.validateAlphaCode(companyCode, alphaCode);
		} catch (SharedProxyException e) {
			log.error(SYSTEM_EXCEPTION_ERROR);
			log.info(e.getMessage());
		}
		log.debug("UploadMailDetailsCommand" + " : " + "populateAirlineValidationVO" + " Exiting");
		return airlineValidationVO;
	}

	/**
	* Method for Populate Mail PK fields
	* @param mailbagVO
	* @return
	*/
	private MailbagVO populateMailPKFields(MailbagVO mailbagVO) {
		log.debug("UploadMailDetailsCommand" + " : " + "populateMailPKFields" + " Entering");
		String mailBagId = mailbagVO.getMailbagId();
		if (mailBagId != null && mailBagId.trim().length() > 0 && !(isValidMailtag(mailBagId.trim().length()))) {
			mailbagVO.setOoe(mailBagId.substring(0, 6));
			mailbagVO.setDoe(mailBagId.substring(6, 12));
			mailbagVO.setMailCategoryCode(mailBagId.substring(12, 13));
			mailbagVO.setMailSubclass(mailBagId.substring(13, 15));
			mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0, 1));
			mailbagVO.setYear(Integer.parseInt(mailBagId.substring(15, 16)));
			mailbagVO.setDespatchSerialNumber(mailBagId.substring(16, 20));
			mailbagVO.setReceptacleSerialNumber(mailBagId.substring(20, 23));
			mailbagVO.setHighestNumberedReceptacle(mailBagId.substring(23, 24));
			mailbagVO.setRegisteredOrInsuredIndicator(mailBagId.substring(24, 25));
			double displayStrWt = Double.parseDouble(mailBagId.substring(25, 29));
			String defaultCaptureUnit = null;
			double displayWeight;
			try {
				defaultCaptureUnit = findSystemParameterValue("mail.operations.defaultcaptureunit");
			} finally {
			}
			if (MailConstantsVO.WEIGHTCODE_KILO.equals(defaultCaptureUnit)) {
				displayWeight = displayStrWt / 10;
			} else {
				displayWeight = displayStrWt;
				defaultCaptureUnit = "H";
			}
			Quantity strWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(displayWeight),
					BigDecimal.valueOf(0.0), defaultCaptureUnit);
			mailbagVO.setWeight(strWt);
			mailbagVO.setStrWeight(strWt);
		}
		log.debug("UploadMailDetailsCommand" + " : " + "populateMailPKFields" + " Exiting");
		return mailbagVO;
	}

	/**
	* @param scannedMailSession
	* @return
	*/
	private ContainerPK populateContainerPK(ScannedMailDetailsVO scannedMailSession) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(scannedMailSession.getPol());
		containerPK.setFlightNumber(scannedMailSession.getFlightNumber());
		containerPK.setFlightSequenceNumber(scannedMailSession.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(scannedMailSession.getLegSerialNumber());
		containerPK.setCarrierId(scannedMailSession.getCarrierId());
		containerPK.setCompanyCode(scannedMailSession.getCompanyCode());
		containerPK.setContainerNumber(scannedMailSession.getContainerNumber());
		return containerPK;
	}

	/**
	* Mthod for Save Mail AcceptanceSession
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws SystemException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	public void saveAcceptanceFromUpload(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveAcceptanceFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<MailAcceptanceVO>();
			MailAcceptanceVO mailAcceptanceVO = null;
			mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
			if (mailAcceptanceVO != null) {
				mailAcceptanceVOs.add(mailAcceptanceVO);
				log.info("" + "MailAcceptacne Vos " + " " + mailAcceptanceVOs);
				try {
					saveScannedOutboundDetails(mailAcceptanceVOs);
				} catch (DuplicateMailBagsException e) {
					constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
							MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
				} catch (FlightClosedException e) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				} catch (ContainerAssignmentException e) {
					constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
							MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
				} catch (InvalidFlightSegmentException e) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT_EXCEPTION,
							MailHHTBusniessException.INVALID_FLIGHT_SEGMENT_EXCEPTION, scannedMailDetailsVO);
				} catch (ULDDefaultsProxyException e) {
					constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
							MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
				} catch (DuplicateDSNException e) {
					constructAndRaiseException(MailMLDBusniessException.DUPLICATE_DSN_EXCEPTION,
							MailHHTBusniessException.DUPLICATE_DSN_EXCEPTION, scannedMailDetailsVO);
				} catch (CapacityBookingProxyException e) {
					constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
							MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
				} catch (MailBookingException e) {
					constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
							MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
				} catch (MailDefaultStorageUnitException ex) {
					constructAndRaiseException(
							MailMLDBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
							MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
							scannedMailDetailsVO);
				}
			}
		}
		log.debug("saveAcceptanceSession" + " : " + "execute" + " Exiting");
	}

	/**
	* Added by A-5526
	* @param companyCode
	* @return
	*/
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		log.debug("UploadMailDetailsCommand" + " : " + "findOneTimeDescription" + " Entering");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(DMG_RET_REASONCODE);
			fieldValues.add(OFL_REASONCODE);
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessException e) {
			log.error("ProxyException Caught");
			log.info(e.getMessage());
		}
		log.debug("UploadMailDetailsCommand" + " : " + "findOneTimeDescription" + " Exiting");
		return oneTimes;
	}

	/**
	* @param errorCode
	* @param errorDescriptionForHHT
	* @param scannedMailDetailsVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	public void constructAndRaiseException(String errorCode, String errorDescriptionForHHT,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (errorDescriptionForHHT != null && errorDescriptionForHHT.length() > 90) {
			errorDescriptionForHHT = errorDescriptionForHHT.substring(0, 90);
		} else if (errorDescriptionForHHT == null || errorDescriptionForHHT.isEmpty()) {
			errorDescriptionForHHT = "Exception";
		}
		if (scannedMailDetailsVO != null && scannedMailDetailsVO.isForceAcpAfterErr()) {
			doAcceptanceAfterErrors(scannedMailDetailsVO);
			throw new ForceAcceptanceException(errorCode, errorDescriptionForHHT);
		}
		if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailSource() != null
				&& MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
			throw new MailMLDBusniessException(errorCode);
		}
		if (scannedMailDetailsVO != null) {
			scannedMailDetailsVO.setErrorDescription(errorDescriptionForHHT);
		}
		throw new MailHHTBusniessException(errorCode, errorDescriptionForHHT);
	}

	/**
	* @author A-5991
	* @param mailIdr
	* @param companyCode
	* @return
	*/
	public long findMailSequenceNumber(String mailIdr, String companyCode) {
		try {
			return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
		} finally {
		}
	}

	/**
	* @author A-8236
	* @return
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public ScannedMailDetailsVO validateMailBagDetails(MailUploadVO mailUploadVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		LoginProfile logonAttributes = null;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<FlightValidationVO> flightDetailsVOs = null;
		MailbagVO mailbagVO = new MailbagVO();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		AirlineValidationVO airlineValidationVO = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		try {
			scannedMailDetailsVO = constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVO, mailbagVO,
					logonAttributes, mailUploadVO.getScannedPort());
			scannedMailDetailsVO = validateMailBagDetailsFeature.execute(scannedMailDetailsVO);
			/*if (scannedMailDetailsVO.getFlightNumber() != null && !"null".equals(scannedMailDetailsVO.getFlightNumber())
					&& scannedMailDetailsVO.getFlightNumber().trim().length() > 0
					&& !MailConstantsVO.DESTN_FLT_STR.equals(scannedMailDetailsVO.getFlightNumber())) {
				flightDetailsVOs = validateFlight(scannedMailDetailsVO, logonAttributes);
				if (flightDetailsVOs.isEmpty()) {
					if (!(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
							&& scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty())) {
						constructAndRaiseException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT,
								MailHHTBusniessException.INVALID_FLIGHT, scannedMailDetailsVO);
					}
				} else {
					String validateImportHandling = findSystemParameterValue(SYSPAR_IMPORT_HANDL_VALIDATION);
					Collection<String> parCodes = new ArrayList<>();
					parCodes.add(SHARED_ARPPAR_ONLARP);
					Map<String, String> arpMap = findAirportParameterValue(logonAttributes.getCompanyCode(),
							flightDetailsVOs.iterator().next().getLegOrigin(), parCodes);
					String onlineArpParamater = arpMap.get(SHARED_ARPPAR_ONLARP);
					if ("Y".equals(validateImportHandling) && ("Y").equals(onlineArpParamater)
							&& flightDetailsVOs.iterator().next().getAtd() == null) {
						constructAndRaiseException(MailConstantsVO.MAIL_OPERATIONS_ATD_MISSING,
								MailHHTBusniessException.MAIL_OPERATIONS_ATD_MISSING, scannedMailDetailsVO);
					}
				}
			}
			if (scannedMailDetailsVO.getContainerNumber() != null
					&& scannedMailDetailsVO.getContainerNumber().trim().length() != 0
					&& scannedMailDetailsVO.getDestination() != null
					&& scannedMailDetailsVO.getDestination().trim().length() != 0) {
				airportValidationVO = sharedAreaProxy.validateAirportCode(logonAttributes.getCompanyCode(),
						scannedMailDetailsVO.getDestination());
				if (airportValidationVO == null) {
					constructAndRaiseException(MailConstantsVO.ANDROID_MSG_ERR_INVALID_DESTINATION,
							MailHHTBusniessException.INVALID_POU_DESTINATION, scannedMailDetailsVO);
				}
			}
			if (scannedMailDetailsVO.getCarrierCode() != null
					&& scannedMailDetailsVO.getCarrierCode().trim().length() != 0) {
				try {
					airlineValidationVO = new MailOperationsValidator().validateCarrierCode(
							scannedMailDetailsVO.getCarrierCode(), scannedMailDetailsVO.getCompanyCode());
					if (airlineValidationVO == null) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
								MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
					} else {
						scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					}
				} finally {
				}
			}
			if (scannedMailDetailsVO.getCarrierCode() == null
					|| scannedMailDetailsVO.getCarrierCode().trim().length() == 0) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
						MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
			}
			if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
				scannedMailDetailsVO
						.setFlightSequenceNumber(flightDetailsVOs.iterator().next().getFlightSequenceNumber());
			}
			if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
				scannedMailDetailsVO = new MailOperationsValidator().validateMailBags(scannedMailDetailsVO);
			} else {
				if (scannedMailDetailsVO.getProcessPoint() != null
						&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						&& !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
						&& scannedMailDetailsVO.getContainerNumber() != null
						&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
					validateContainerForReassign(scannedMailDetailsVO);
				}
			}
		} finally {
		}
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() == null
							|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
				scannedMailDetailsVO.setContainerAsSuchArrOrDlvFlag(MailConstantsVO.FLAG_YES);
				if (mailUploadVO.isDeliverd()) {
					scannedMailDetailsVO.setContainerDeliveryFlag(MailConstantsVO.FLAG_YES);
				}
				scannedMailDetailsVO = checkForULDlevelArrivalOrDelivery(scannedMailDetailsVO, logonAttributes,
						flightDetailsVOs);
				scannedMailDetailsVO.setOverrideValidations(mailUploadVO.getOverrideValidation());
			}
		}
		doSecurityScreeningValidations(scannedMailDetailsVO, flightDetailsVOs);
			 */
		} finally {
		}
		return scannedMailDetailsVO;
	}

	/**
	* @param mailUploadVO
	* @param scanningPort
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @throws RemoteException
	* @throws PersistenceException
	*/
	public ScannedMailDetailsVO saveMailUploadDetailsForAndroid(MailUploadVO mailUploadVO, String scanningPort)
			throws MailMLDBusniessException, MailHHTBusniessException, MailOperationsBusinessException,
			PersistenceException {
		try {
			LoginProfile logonAttributes = null;
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			MailbagVO mailbagVO = new MailbagVO();
			this.cntAsgmntVO = null;
			if (mailUploadVO.getProcessPoint() != null) {
				scannedMailDetailsVO.setProcessPoint(mailUploadVO.getProcessPoint());
			}
			ULDValidationVO uldValidationVO = null;
			try {
				logonAttributes = contextUtil.callerLoginProfile();
			} finally {
			}
			mailUploadVO.setScannedPort(scanningPort);
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailUploadVO.getScanType())
					&& mailUploadVO.getContainerNumber() == null) {
				String bulkname = new StringBuilder("BULK").append("-").append(scanningPort).toString();
				mailUploadVO.setContainerNumber(bulkname);
				mailUploadVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}
			if (mailUploadVO.isForceAccepted() && !mailUploadVO.isFromErrorHandlingForForceAcp()) {
				String errorDescription = "";
				String errorCode = null;
				errorDescription = mailUploadVO.getErrorDescription();
				errorCode = mailUploadVO.getErrorCode();
				constructAndRaiseException(errorCode, errorDescription, scannedMailDetailsVO);
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailUploadVO.getScanType())
					&& !MailConstantsVO.ONLOAD_MESSAGE.equals(mailUploadVO.getMailSource())
					&& (mailUploadVO.getContainerNumber() != null
							&& mailUploadVO.getContainerNumber().trim().length() > 0)
					&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())
					&& (mailUploadVO.getMailTag() == null || "null".equals(mailUploadVO.getMailTag()))
					&& (mailUploadVO.getFlightNumber() == null || "null".equals(mailUploadVO.getFlightNumber())
							|| (mailUploadVO.getFlightNumber() != null && mailUploadVO.getFlightNumber().isEmpty()))
					&& (mailUploadVO.getDestination() == null || "null".equals(mailUploadVO.getDestination())
							|| (mailUploadVO.getDestination() != null && mailUploadVO.getDestination().isEmpty()))
					&& (mailUploadVO.getToPOU() == null || "null".equals(mailUploadVO.getToPOU())
							|| (mailUploadVO.getToPOU() != null && mailUploadVO.getToPOU().isEmpty()))) {
				ContainerAssignmentVO containerAssignmentVO = mailController
						.findLatestContainerAssignment(mailUploadVO.getContainerNumber());
				mailController.releaseContainer(containerAssignmentVO);
				return scannedMailDetailsVO;
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailUploadVO.getScanType())
					&& ((mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() > 0)
							|| mailUploadVO.isFromErrorHandling() || (mailUploadVO.getDestination() == null
									|| mailUploadVO.getDestination().trim().length() == 0))) {
				validateMailbagForReassign(mailUploadVO, logonAttributes);
			}
			if ((MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailUploadVO.getScanType())
					|| MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailUploadVO.getScanType()))
					&& mailUploadVO.getAttachments() != null && !mailUploadVO.getAttachments().isEmpty()) {
				uploadDocumentsToRepository(mailUploadVO);
			}
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty())
					&& (scannedMailDetailsVO.getFlightNumber() == null
							|| scannedMailDetailsVO.getFlightNumber().isEmpty())) {
				MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
				mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
				if (mailbagInULDForSegmentVO != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getAcceptanceFlag())
						&& (mailbagInULDForSegmentVO.getFlightNumber() != null
								&& mailbagInULDForSegmentVO.getFlightSequenceNumber() > 0)) {
					FlightFilterVO flightFilterVO = new FlightFilterVO();
					flightFilterVO.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
					flightFilterVO.setFlightCarrierId(mailbagInULDForSegmentVO.getCarrierId());
					flightFilterVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
					flightFilterVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
					Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
					if (flightValidationVOs != null) {
						for (FlightValidationVO flightValidation : flightValidationVOs) {
							mailUploadVO.setFlightDate(LocalDateMapper.toZonedDateTime(flightValidation.getSta()));
							mailUploadVO.setFlightNumber(flightValidation.getFlightNumber());
							mailUploadVO.setCarrierCode(flightValidation.getCarrierCode());
							mailUploadVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
							mailUploadVO.setFlightSequenceNumber(flightValidation.getFlightSequenceNumber());
							mailUploadVO.setCarrierId(flightValidation.getFlightCarrierId());
							mailUploadVO.setContainerPol(flightValidation.getLegOrigin());
							mailUploadVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
						}
					}
				}
			} else {
			}
			if (mailUploadVO.getOverrideValidation() != null && !("Y").equals(mailUploadVO.getOverrideValidation())) {

				scannedMailDetailsVO = validateMailBagDetails(mailUploadVO);

				if (!MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())
						&& scannedMailDetailsVO.getErrorMailDetails() != null
						&& scannedMailDetailsVO.getErrorMailDetails().size() > 0) {
					return scannedMailDetailsVO;
				}
			} else {
				constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVO, mailbagVO, logonAttributes,
						mailUploadVO.getScannedPort());
			}
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() == null
							|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
				scannedMailDetailsVO.setContainerAsSuchArrOrDlvFlag(MailConstantsVO.FLAG_YES);
				if (scannedMailDetailsVO.getErrorMailDetails() != null
						&& !scannedMailDetailsVO.getErrorMailDetails().isEmpty()) {
					if (MailHHTBusniessException.CONTAINER_INBOUND_OPERATION_SUCESSS
							.equals(scannedMailDetailsVO.getErrorMailDetails().iterator().next().getErrorCode())) {
						scannedMailDetailsVO.setErrorMailDetails(null);
					}
					return scannedMailDetailsVO;
				}
				ContainerAssignmentVO containerAssignmentVO = null;
				if (mailUploadVO.isDeliverd()) {
					scannedMailDetailsVO.setContainerDeliveryFlag(MailConstantsVO.FLAG_YES);
					containerAssignmentVO = this.cntAsgmntVO == null
							? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
							: this.cntAsgmntVO;
					this.cntAsgmntVO = containerAssignmentVO;
				} else {
					containerAssignmentVO = mailController
							.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				}
				if (containerAssignmentVO != null && (scannedMailDetailsVO.getFlightNumber() == null
						|| "".equals(scannedMailDetailsVO.getFlightNumber()))) {
					scannedMailDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
					scannedMailDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					scannedMailDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
					scannedMailDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
				}
			}
			if ((MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND.equals(mailUploadVO.getMailSource())
					|| MailConstantsVO.MAIL_SOURCE_HHT_MAILOUTBOUND.equals(mailUploadVO.getMailSource()))
					&& "Y".equals(mailUploadVO.getOverrideULDVal())) {
				scannedMailDetailsVO.setValidateULDExists(true);
			} else {
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
				if ((scannedMailDetailsVO.getContainerNumber() == null
						|| scannedMailDetailsVO.getContainerNumber().trim().length() == 0)) {
					if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
							&& (checkforCoterminusAirport(scannedMailDetailsVO, MailConstantsVO.RESDIT_RECEIVED,
									getLogonAttributes()))) {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN,
								"Container number is mandatory for acceptance", scannedMailDetailsVO);
					}
				} else if (MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())) {
					uldValidationVO = validateUldForAcceptance(mailUploadVO.getCompanyCode(),
							mailUploadVO.getContainerNumber());
					if (uldValidationVO == null) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_ULD_FORMAT,
								MailHHTBusniessException.INVALID_ULD_FORMAT, scannedMailDetailsVO);
					}
				}
			}
			if (mailUploadVO.isArrivalException() && !mailUploadVO.isFromErrorHandling()) {
				scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
				scannedMailDetailsVO.setFlightDate(null);
				scannedMailDetailsVO.setFlightNumber("");
				scannedMailDetailsVO.setArrivalException(true);
				String errorDescription = "";
				String errorCode = null;
				if (scannedMailDetailsVO.getProcessPointBeforeArrival() != null && scannedMailDetailsVO
						.getProcessPointBeforeArrival().equals(MailConstantsVO.MAIL_STATUS_RETURNED)) {
					errorDescription = new StringBuilder().append("Mailbag returned. ").toString();
					errorCode = MailMLDBusniessException.MALBAG_RET_IMPFLT_MISSING;
				} else if (scannedMailDetailsVO.getProcessPointBeforeArrival() != null && scannedMailDetailsVO
						.getProcessPointBeforeArrival().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED)) {
					errorDescription = new StringBuilder().append("Mailbag Transfered. ").toString();
					errorCode = MailMLDBusniessException.MALBAG_TRF_IMPFLT_MISSING;
				} else {
					errorDescription = new StringBuilder().append("Mailbag delivered. ").toString();
					errorCode = MailMLDBusniessException.MALBAG_DLV_IMPFLT_MISSING;
				}
				errorDescription = new StringBuilder().append(errorDescription)
						.append(MailHHTBusniessException.IMPORT_FLIGHT_MANDATORY).toString();
				constructAndRaiseException(errorCode, errorDescription, scannedMailDetailsVO);
			}
			if (mailUploadVO.isArrivalException() && mailUploadVO.isResolveFromErrorHandling()) {
				scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
				scannedMailDetailsVO.setArrivalException(true);
				mailUploadVO.setResolveFromErrorHandling(false);
				String errorDescription = "";
				errorDescription = new StringBuilder()
						.append("Mailbag is arrived.Import flight detail of previous segment is missing. ").toString();
				errorDescription = new StringBuilder().append(errorDescription).toString();
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_ARRIVED, errorDescription,
						scannedMailDetailsVO);
			}
			String airportCode = (mailUploadVO.getMailSource() != null
					&& (mailUploadVO.getMailSource().startsWith(MailConstantsVO.SCAN)
							|| mailUploadVO.getMailSource().startsWith(MailConstantsVO.WS))) ? scanningPort
									: logonAttributes.getAirportCode();
			scannedMailDetailsVO.setAirportCode(scanningPort);
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()
					&& !MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())) {
				checkForceAcceptanceRequired(scannedMailDetailsVO, logonAttributes);
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& (scannedMailDetailsVO.getDestination() == null
							|| scannedMailDetailsVO.getDestination().trim().length() == 0)
					&& scannedMailDetailsVO.getCarrierCode() != null
					&& scannedMailDetailsVO.getCarrierCode().trim().length() != 0
					&& logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getCarrierCode())) {
				constructAndRaiseException(MailConstantsVO.ANDROID_MSG_ERR_INVALID_DESTINATION,
						MailHHTBusniessException.INVALID_POU_DESTINATION, scannedMailDetailsVO);
			}
			if (mailUploadVO.getOverrideValidation() != null && !("Y").equals(mailUploadVO.getOverrideValidation())
					&& !scannedMailDetailsVO.isValidateULDExists()) {
				validateULDFormat(scannedMailDetailsVO, mailUploadVO, logonAttributes);
				if (!MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())
						&& scannedMailDetailsVO.getErrorMailDetails() != null
						&& !scannedMailDetailsVO.getErrorMailDetails().isEmpty()) {
					return scannedMailDetailsVO;
				}
			}
			if (scannedMailDetailsVO.getFlightNumber() != null && scannedMailDetailsVO.getCarrierCode() != null
					&& scannedMailDetailsVO.getFlightNumber().trim().length() != 0
					&& !MailConstantsVO.DESTN_FLT_STR.equals(scannedMailDetailsVO.getFlightNumber())) {
				validateFlightandUpdateVO(scannedMailDetailsVO, logonAttributes, airportCode);
			} else if ((scannedMailDetailsVO.getFlightNumber() == null
					|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0
					|| MailConstantsVO.DESTN_FLT_STR.equals(scannedMailDetailsVO.getFlightNumber()))
					&& (scannedMailDetailsVO.getCarrierCode() != null
							&& scannedMailDetailsVO.getCarrierCode().trim().length() > 0)) {
				updateCarrierAcceptanceDetails(scannedMailDetailsVO, mailbagVO);
			}
			if (scannedMailDetailsVO.getPou() != null && scannedMailDetailsVO.getPou().length() > 0
					&& scannedMailDetailsVO.getDestination() != null
					&& scannedMailDetailsVO.getDestination().length() > 0)
				validatePOUandDestination(scannedMailDetailsVO, mailUploadVO.getCompanyCode());
			if ((scannedMailDetailsVO.getProcessPoint() != null
					&& MailConstantsVO.MAIL_STATUS_OFFLOADED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint()))
					|| scannedMailDetailsVO.getContainerNumber() != null
							&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
				validateContainer(scannedMailDetailsVO, scanningPort, mailUploadVO);
			}
			if (scannedMailDetailsVO.getMailDetails().size() > 0) {
				if ((scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length() == 29
						|| isValidMailtag(
								scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length()))) {
					validateMailBags(scannedMailDetailsVO);
					updateProcessPointforMailBags(scannedMailDetailsVO, scanningPort);
					if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
							.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
						new MailOperationsValidator().doEmbargoValidationForAndroidMailBag(scannedMailDetailsVO);
					}
					processPointSpecificMailbagValidation(scannedMailDetailsVO);
				}
			}
			if (MailConstantsVO.CONTAINER_STATUS_REASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
				performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
				if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
						&& !scannedMailDetailsVO.isRsgnmailbagFromdiffContainer())
					return scannedMailDetailsVO;
			}
			ContainerAssignmentVO containerAssignmentVO = updateVOForSpecificOperationsforAndroid(scannedMailDetailsVO,
					mailUploadVO, logonAttributes);
			if (MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())
					&& MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
				if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
					reassignOnDestinationChange(scannedMailDetailsVO, logonAttributes);
				}
				if (scannedMailDetailsVO.getTransferFrmFlightNum() != null
						&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0) {
					scannedMailDetailsVO.setTransferFrmFlightNum(null);
				}
				performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
			}
			if (containerAssignmentVO == null
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())
					&& scannedMailDetailsVO.getTransferFromCarrier() != null
					&& scannedMailDetailsVO.getTransferFromCarrier().trim().length() > 0
					&& !logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getTransferFromCarrier())
					&& (scannedMailDetailsVO.getTransferFrmFlightNum() == null
							|| scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() == 0)) {
				Collection<MailbagVO> mailbagVOs = findMailbagsFromOALinResditProcessing(
						scannedMailDetailsVO.getContainerNumber(), scannedMailDetailsVO.getCompanyCode());
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
					performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
					updateMailbagVOForTransfer(mailbagVOs, scannedMailDetailsVO);
					scannedMailDetailsVO.setMailDetails(mailbagVOs);
					saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
					return scannedMailDetailsVO;
				}
			}
			String paBuiltContainerSave = null;
			paBuiltContainerSave = findSystemParameterValue(PABUILT_CONTAINERSAVE_ENABLED);
			if (MailConstantsVO.FLAG_YES.equals(paBuiltContainerSave)
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.FLAG_YES.equals(mailUploadVO.getPaCode())
					&& scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() == null
							|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
				int offsetforPABuilt = 0;
				offsetforPABuilt = Integer.parseInt(findSystemParameterValue(PERIODFOR_PABUILTMAILS));
				ZonedDateTime fromDate = localDateUtil.getLocalDate(null, false);
				ZonedDateTime toDate = localDateUtil.getLocalDate(null, false);
				fromDate = fromDate.plusDays(-offsetforPABuilt);
				toDate = toDate.plusDays(1);
				Collection<MailbagVO> mailbagVOs = findMailbagsForPABuiltContainerSave(
						scannedMailDetailsVO.getContainerNumber(), scannedMailDetailsVO.getCompanyCode(), fromDate,
						toDate);
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
					String origin = null;
					String destination = null;
					origin = mailbagVOs.iterator().next().getOrigin();
					destination = mailbagVOs.iterator().next().getDestination();
					if (mailbagVOs.iterator().next().getContainerJourneyId() != null) {
						scannedMailDetailsVO
								.setContainerJourneyId(mailbagVOs.iterator().next().getContainerJourneyId());
					}
					checkAppRegFlagValidationForPABuildContainer(mailbagVOs, scannedMailDetailsVO);
					if (origin != null && destination != null && !origin.equals(scannedMailDetailsVO.getAirportCode())
							&& !destination.equals(scannedMailDetailsVO.getAirportCode())) {
						updateMailbagVOForTransfer(mailbagVOs, scannedMailDetailsVO);
						scannedMailDetailsVO.getValidatedContainer().setFoundTransfer(true);
						scannedMailDetailsVO.setFoundTransfer(true);
						scannedMailDetailsVO.setMailDetails(mailbagVOs);
						performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
						saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
					} else {
						updateMailbagVOForAcceptance(mailbagVOs, scannedMailDetailsVO);
						scannedMailDetailsVO.setMailDetails(mailbagVOs);
						saveAcceptanceFromUpload(scannedMailDetailsVO, logonAttributes);
					}
					return scannedMailDetailsVO;
				}
			}
			if (MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())
					&& scannedMailDetailsVO.getMailDetails() != null
					&& scannedMailDetailsVO.getMailDetails().size() == 0) {
				saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
				return scannedMailDetailsVO;
			}
			if (MailConstantsVO.FLAG_YES.equals(mailUploadVO.getResolveFlagAndroid())
					&& mailUploadVO.isFromErrorHandling()) {
				if (scannedMailDetailsVO.getFlightNumber() == null || scannedMailDetailsVO.getCarrierCode() == null
						|| scannedMailDetailsVO.getCarrierCode().trim().length() == 0
						|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0) {
					constructAndRaiseException(MailMLDBusniessException.MALBAG_DLV_IMPFLT_MISSING,
							MailHHTBusniessException.IMPORT_FLIGHT_MANDATORY, scannedMailDetailsVO);
				}
				saveAutoArrivalForNonAcceptedMailbags(scannedMailDetailsVO);
				Collection<String> officeOfExchanges = new ArrayList<String>();
				Collection<ArrayList<String>> groupedOECityArpCodes = null;
				String origin = null;
				String ooe = null;
				ooe = scannedMailDetailsVO.getMailDetails().iterator().next().getOoe();
				officeOfExchanges.add(ooe);
				groupedOECityArpCodes = findCityAndAirportForOE(scannedMailDetailsVO.getCompanyCode(),
						officeOfExchanges);
				if (groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
					for (ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
						if (cityAndArpForOE.size() == 3) {
							if (ooe != null && ooe.length() > 0 && ooe.equals(cityAndArpForOE.get(0))) {
								origin = cityAndArpForOE.get(2);
							}
						}
					}
				}
				if (scannedMailDetailsVO.getPol() != null) {
					scannedMailDetailsVO.setAirportCode(scannedMailDetailsVO.getPol());
				}
				MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
				mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
				if (!scannedMailDetailsVO.getPol().equals(origin) && !checkforCoterminusAirport(scannedMailDetailsVO,
						MailConstantsVO.RESDIT_UPLIFTED, logonAttributes)) {
					if (mailbagInULDForSegmentVO != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getArrivalFlag())) {
						scannedMailDetailsVO.setArrivalException(false);
						return scannedMailDetailsVO;
					}
					if (mailbagInULDForSegmentVO != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getAcceptanceFlag())) {
						if (mailbagInULDForSegmentVO.getFlightNumber() != null
								&& mailbagInULDForSegmentVO.getFlightSequenceNumber() > 0) {
							FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
							flightFilterVO.setFlightCarrierId(mailbagInULDForSegmentVO.getCarrierId());
							flightFilterVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
							flightFilterVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
							Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
							if (flightValidationVOs != null) {
								for (FlightValidationVO flightValidation : flightValidationVOs) {
									scannedMailDetailsVO
											.setFlightDate(LocalDateMapper.toZonedDateTime(flightValidation.getSta()));
									scannedMailDetailsVO.setFlightNumber(flightValidation.getFlightNumber());
									scannedMailDetailsVO.setCarrierCode(flightValidation.getCarrierCode());
									scannedMailDetailsVO
											.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
								}
							}
						}
					} else {
						scannedMailDetailsVO.setFlightDate(null);
						scannedMailDetailsVO.setFlightNumber("");
						scannedMailDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
						scannedMailDetailsVO.setContainerNumber("");
					}
					scannedMailDetailsVO.setArrivalException(true);
					return scannedMailDetailsVO;
				}
				return scannedMailDetailsVO;
			}
			if (doAutoArrivalForNonAcceptedMailbags(scannedMailDetailsVO, logonAttributes, airportCode)) {
				if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
					if ((scannedMailDetailsVO.getTransferFromCarrier() != null
							&& scannedMailDetailsVO.getTransferFromCarrier().trim().length() > 0
							&& !logonAttributes.getOwnAirlineCode()
									.equals(scannedMailDetailsVO.getTransferFromCarrier()))
							|| (scannedMailDetailsVO.isContainerFoundTransfer())) {
						scannedMailDetailsVO.setArrivalException(false);
					} else {
						scannedMailDetailsVO.setArrivalException(true);
					}
					importMailProvisionalRateData(scannedMailDetailsVO);
					 saveScreeningDetails(scannedMailDetailsVO);
					return scannedMailDetailsVO;
				}
				Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
				rateAuditVOs = mailController.createRateAuditVOs(scannedMailDetailsVO);
				if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
					String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
					if (importEnabled != null && importEnabled.contains("D")) {
						try {
							mailOperationsMRAProxy.importMRAData(rateAuditVOs);
						} catch (BusinessException e) {
							throw new SystemException(e.getMessage(), e.getMessage(), e);
						}
					}
				}
				scannedMailDetailsVO.setArrivalException(true);
				return scannedMailDetailsVO;
			}
			saveAndProcessMailBagsForAndroid(scannedMailDetailsVO);
			if (scannedMailDetailsVO.getConsignmentScreeningVos() != null) {
				saveScreeningDetails(scannedMailDetailsVO);
			}
			return scannedMailDetailsVO;
		} catch (ForceAcceptanceException exception) {
			Collection<ErrorVO> errors = new ArrayList<>();
			if (exception.getErrors() != null) {
				errors = exception.getErrors();
			}
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			if (errors != null && !errors.isEmpty()) {
				for (ErrorVO vo : errors) {
					scannedMailDetailsVO.setErrorCode(vo.getErrorCode());
					//TODO: to correct as per classic
					//scannedMailDetailsVO.setErrorDescription(vo.getConsoleMessage());
					scannedMailDetailsVO.setErrorDescription(vo.getErrorCode());
					scannedMailDetailsVO.setForceAccepted(true);
				}
			}
			return scannedMailDetailsVO;
		}
	}

	public void saveScreeningDetails(ScannedMailDetailsVO scannedMailDetailsVO) {
		log.info("Entering saveScreeningDetails");
		saveScreeningDetailsFeature.execute(scannedMailDetailsVO);
		log.info("Exiting saveScreeningDetails");
	}

	private ContainerAssignmentVO updateVOForSpecificOperationsforAndroid(ScannedMailDetailsVO scannedMailDetailsVO,
			MailUploadVO mailUploadVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())) {
			log.info("Going to call saveAcceptanceSession ");
			updateVOForAcceptance(scannedMailDetailsVO, logonAttributes);
			if (scannedMailDetailsVO.getMailDetails().size() > 0) {
				if (scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag() != null
						&& scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag().equals("Y"))
					updateVOForDammageCapture(scannedMailDetailsVO, logonAttributes, oneTimes);
			}
		} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
			log.info("Going to call saveOffloadedSession ");
			updateVOForOffload(scannedMailDetailsVO, logonAttributes, oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForArrival(scannedMailDetailsVO, logonAttributes);
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				if (scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag() != null
						&& MailConstantsVO.FLAG_YES
								.equals(scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag())) {
					updateVOForDammageCapture(scannedMailDetailsVO, logonAttributes, oneTimes);
				}
			}
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForDeliver(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForDammageCapture(scannedMailDetailsVO, logonAttributes, oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForReturn(scannedMailDetailsVO, logonAttributes, oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForTransfer(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForReassign(scannedMailDetailsVO, logonAttributes);
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				if (scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag() != null
						&& MailConstantsVO.FLAG_YES
								.equals(scannedMailDetailsVO.getMailDetails().iterator().next().getDamageFlag())) {
					scannedMailDetailsVO.getMailDetails().iterator().next().setActionMode("DMG");
				}
			}
		}
		try {
			containerAssignmentVO = setValidatedContainerDetails(scannedMailDetailsVO);
		} finally {
		}
		return containerAssignmentVO;
	}

	/**
	* @param scannedMailDetailsVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void validateMailBags(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		try {
			new MailOperationsValidator().validateMailDetailsForAndroid(scannedMailDetailsVO);
		} finally {
		}
	}

	/**
	* @param scannedMailVo
	* @param scanningPort
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private void validateContainer(ScannedMailDetailsVO scannedMailVo, String scanningPort, MailUploadVO mailUploadVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		String airportCode = scannedMailVo.getAirportCode();
		boolean airportFlag = false;
		boolean validFlightFlag = false;
		boolean isStatusTBAorTBC = false;
		LoginProfile logonAttributes = null;
		Collection<FlightValidationVO> flightDetailsVOs = null;
		ContainerVO containerVo = new ContainerVO();
		String DEST_FLT = "-1";
		if ((scannedMailVo.getContainerNumber() == null || scannedMailVo.getContainerNumber().isEmpty())
				&& (MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailVo.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailVo.getProcessPoint()))) {
			constructAndRaiseException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN,
					"Container number is mandatory for acceptance", scannedMailVo);
		}
		try {
			if ((MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailVo.getProcessPoint()))
					&& scannedMailVo.getMailDetails().iterator().next().getMailbagId().length() != 0
					&& (scannedMailVo.getMailDetails().iterator().next().getMailbagId().length() != 29
							|| !isValidMailtag(
									scannedMailVo.getMailDetails().iterator().next().getMailbagId().length()))) {
				containerAssignmentVO = mailController
						.findLatestContainerAssignment(scannedMailVo.getMailDetails().iterator().next().getMailbagId());
			} else {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailVo.getProcessPoint())
						&& MailConstantsVO.ULD_TYPE.equals(scannedMailVo.getContainerType())
						&& MailConstantsVO.SCAN.equals(scannedMailVo.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailVo.getContainerDeliveryFlag())
								|| (scannedMailVo.getMailDetails() == null
										|| scannedMailVo.getMailDetails().isEmpty()))) {
					containerAssignmentVO = this.cntAsgmntVO == null
							? findLatestContainerAssignmentForUldDelivery(scannedMailVo)
							: this.cntAsgmntVO;
					this.cntAsgmntVO = containerAssignmentVO;
				} else {
					containerAssignmentVO = (this.cntAsgmntVO == null)
							? findLatestContainerAssignment(scannedMailVo.getContainerNumber())
							: this.cntAsgmntVO;
					this.cntAsgmntVO = containerAssignmentVO;
				}
			}
			if (containerAssignmentVO != null
					&& containerAssignmentVO.getFlightNumber().equals(scannedMailVo.getFlightNumber())
					&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailVo.getFlightSequenceNumber()
					&& containerAssignmentVO.getAirportCode().equals(scannedMailVo.getAirportCode())) {
				scannedMailVo.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
			}
			if (containerAssignmentVO != null
					&& (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailVo.getProcessPoint()))
					&& containerAssignmentVO.getPoaFlag() != null) {
				mailUploadVO.setPaCode(containerAssignmentVO.getPoaFlag());
				scannedMailVo.setIsContainerPabuilt(containerAssignmentVO.getPoaFlag());
			}
			if (containerAssignmentVO != null && scannedMailVo.getCarrierCode() != null
					&& (scannedMailVo.getFlightNumber() == null || scannedMailVo.getFlightNumber().length() == 0)
					&& scannedMailVo.getFlightDate() == null && scannedMailVo.getDestination().length() == 0
					&& scannedMailVo.getCarrierCode().length() == 0
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailVo.getProcessPoint())) {
				try {
					logonAttributes = contextUtil.callerLoginProfile();
				} finally {
				}
				scannedMailVo.setCarrierCode(containerAssignmentVO.getCarrierCode());
				scannedMailVo.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				scannedMailVo.setFlightNumber(containerAssignmentVO.getFlightNumber());
				scannedMailVo.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				scannedMailVo.setPou(containerAssignmentVO.getPou());
				scannedMailVo.setDestination(containerAssignmentVO.getDestination());
				scannedMailVo.setFlightDate(containerAssignmentVO.getFlightDate());
				scannedMailVo.setCarrierId(containerAssignmentVO.getCarrierId());
				ArrayList<MailbagVO> mailbagVOs = (ArrayList<MailbagVO>) scannedMailVo.getMailDetails();
				for (MailbagVO maibagVO : mailbagVOs) {
					maibagVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					maibagVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					maibagVO.setCarrierId(containerAssignmentVO.getCarrierId());
					maibagVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
					maibagVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
					maibagVO.setFlightDate(containerAssignmentVO.getFlightDate());
					maibagVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
					maibagVO.setCarrierId(containerAssignmentVO.getCarrierId());
					FlightFilterVO flightFilterVO = createFlightFilterVO(scannedMailVo, logonAttributes, airportCode);
					flightDetailsVOs = mailController.validateFlight(flightFilterVO);
					if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
						validFlightFlag = true;
						isStatusTBAorTBC = checkIfFlightToBeCancelled(flightDetailsVOs, scannedMailVo, airportCode);
						if (!validFlightFlag) {
							constructAndRaiseException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT,
									MailHHTBusniessException.INVALID_FLIGHT, scannedMailVo);
						}
						if (isStatusTBAorTBC) {
							constructAndRaiseException(MailMLDBusniessException.FLIGHT_TBA_TBC,
									MailHHTBusniessException.CANCELLED_FLIGHT, scannedMailVo);
						}
					}
				}
			}
		} finally {
		}
		if (containerAssignmentVO != null && MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailVo.getProcessPoint())
				&& MailConstantsVO.ULD_TYPE.equals(scannedMailVo.getContainerType())
				&& scannedMailVo.getMailDetails() != null && !scannedMailVo.getMailDetails().isEmpty()
				&& scannedMailVo.getMailDetails().iterator().next().getMailSequenceNumber() > 0
				&& scannedMailVo.getMailDetails().iterator().next().isDeliveryNeeded()
				&& scannedMailVo.getFlightNumber() != null && scannedMailVo.getFlightNumber().trim().length() > 0
				&& !scannedMailVo.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailVo.getFlightSequenceNumber() > 0
				&& scannedMailVo.getFlightSequenceNumber() != containerAssignmentVO.getFlightSequenceNumber()) {
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
			try {
				mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailVo);
			} catch (PersistenceException e) {
				e.getMessage();
			}
			if (mailbagInULDForSegmentVO != null && containerAssignmentVO.getContainerNumber()
					.equals(mailbagInULDForSegmentVO.getContainerNumber())) {
				containerAssignmentVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
				containerAssignmentVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
				containerAssignmentVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
				containerAssignmentVO.setAirportCode(mailbagInULDForSegmentVO.getAssignedPort());
				containerAssignmentVO.setPou(airportCode);
			}
		}
		if (containerAssignmentVO != null
				&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
			containerVo.setCompanyCode(scannedMailVo.getCompanyCode());
			containerVo.setContainerNumber(scannedMailVo.getContainerNumber());
			containerVo.setAssignedPort(scannedMailVo.getAirportCode());
		}
		String allowUldAsBarrow = null;
		allowUldAsBarrow = findSystemParameterValue(ALLOWULDASBARROW);
		if (allowUldAsBarrow != null && MailConstantsVO.FLAG_YES.equals(allowUldAsBarrow)
				&& containerAssignmentVO != null
				&& (scannedMailVo.getContainerType() != null
						&& !containerAssignmentVO.getContainerType().equals(scannedMailVo.getContainerType()))
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailVo.getProcessPoint())) {
			StringBuilder errorString = new StringBuilder();
			if (containerAssignmentVO.getFlightNumber() != null
					&& containerAssignmentVO.getFlightNumber().trim().length() > 0
					&& scannedMailVo.getFlightNumber() != null && scannedMailVo.getFlightNumber().trim().length() > 0
					&& containerAssignmentVO.getFlightSequenceNumber() > 0
					&& containerAssignmentVO.getFlightSequenceNumber() > 0
					&& containerAssignmentVO.getFlightNumber().equals(scannedMailVo.getFlightNumber())
					&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailVo.getFlightSequenceNumber()) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
						MailHHTBusniessException.ULD_AS_BULK_IN_SAME_CONTAINER, scannedMailVo);
			}
			if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
					&& containerAssignmentVO.getFlightNumber().trim().length() > 0
					&& !containerAssignmentVO.getFlightNumber().equals(DEST_FLT)) {
				errorString.append("ULD/BULK is already assigned to flight ");
				errorString.append(containerAssignmentVO.getCarrierCode());
				errorString.append(" ");
				errorString.append(containerAssignmentVO.getFlightNumber());
				errorString.append(" ");
				if (containerAssignmentVO.getFlightDate() != null) {
					errorString.append(containerAssignmentVO.getFlightDate()
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
				}
				constructAndRaiseException(MailMLDBusniessException.ULDBULK_ANOTHER_FLIGHT, errorString.toString(),
						scannedMailVo);
			} else if (containerAssignmentVO.getFlightNumber().trim().length() > 0
					&& containerAssignmentVO.getFlightNumber().equals(DEST_FLT)) {
				boolean canReuseContainer = false;
				if (containerAssignmentVO.getFlightNumber().equals(DEST_FLT)
						&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
					canReuseContainer = canReuseEmptyContainer(containerAssignmentVO);
				}
				if (canReuseContainer) {
					deleteCarrierEmptyContainer(containerAssignmentVO);
				} else {
					errorString.append("ULD/BULK is already assigned to carrier ");
					errorString.append(containerAssignmentVO.getCarrierCode());
					errorString.append(" ");
					constructAndRaiseException(MailMLDBusniessException.ULDBULK_ANOTHER_FLIGHT, errorString.toString(),
							scannedMailVo);
				}
			}
		}
		if (containerAssignmentVO != null
				&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
				&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
				&& (scannedMailVo.getContainerType() != null
						&& !containerAssignmentVO.getContainerType().equals(scannedMailVo.getContainerType()))) {
			StringBuilder errorString = new StringBuilder();
			errorString.append("This container is already present in the system with containertype as  ");
			if (MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
				errorString.append("Bulk");
			} else {
				errorString.append("ULD");
			}
			String error = errorString.toString();
			constructAndRaiseException(MailMLDBusniessException.WRONG_CONTAINER_TYPE_GIVEN, error, scannedMailVo);
		}
		if (containerAssignmentVO != null
				&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
				&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())
				&& scannedMailVo.getFlightNumber() != null
				&& scannedMailVo.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailVo.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailVo.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailVo.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()) {
			constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
					MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER, scannedMailVo);
		}
		if (MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailVo.getProcessPoint())) {
			if (!containerAssignmentVO.getAirportCode().equals(scannedMailVo.getAirportCode())
					&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
						"Container is not available at this airport", scannedMailVo);
			}
		}
		if (containerAssignmentVO != null) {
			if (containerAssignmentVO.getPou() != null) {
				if ((!containerAssignmentVO.getPou().equals(scannedMailVo.getPou())
						|| !containerAssignmentVO.getDestination().equals(scannedMailVo.getDestination()))) {
				}
			}
		}
		if (containerAssignmentVO != null
				&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
				&& (scannedMailVo.getFlightNumber() != null
						&& scannedMailVo.getFlightNumber().equals(containerAssignmentVO.getFlightNumber()))
				&& scannedMailVo.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailVo.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailVo.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
				&& scannedMailVo.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()
				&& !(scannedMailVo.getContainerType().equals(containerAssignmentVO.getContainerType()))) {
			constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE,
					MailHHTBusniessException.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE, scannedMailVo);
		}
		if (containerAssignmentVO != null
				&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
				&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())
				&& scannedMailVo.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
				&& scannedMailVo.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
				&& scannedMailVo.getCarrierId() == containerAssignmentVO.getCarrierId()
				&& scannedMailVo.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()) {
			constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
					MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER, scannedMailVo);
		}
		if (containerAssignmentVO != null
				&& MailConstantsVO.CONTAINER_STATUS_TRANSFER.equalsIgnoreCase(scannedMailVo.getContainerProcessPoint())
				&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
				&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getArrivalFlag())) {
			Collection<ContainerDetailsVO> conatinerDetails = null;
			Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
			containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
			containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
			containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
			containerDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
			containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
			containers.add(containerDetailsVO);
			try {
				conatinerDetails = MailAcceptance.findMailbagsInContainer(containers);
			} finally {
			}
			if (conatinerDetails != null) {
				for (ContainerDetailsVO containerVO : conatinerDetails) {
					int arrivedMails = 0;
					for (MailbagVO mailbagVO : containerVO.getMailDetails()) {
						ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
						mailhistories = (ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistories(
								scannedMailVo.getCompanyCode(), mailbagVO.getMailbagId(),
								mailbagVO.getMailSequenceNumber());
						if (mailhistories != null && mailhistories.size() > 0) {
							for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
								if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())
										&& scannedMailVo.getAirportCode().equals(mailbaghistoryvo.getScannedPort())) {
									arrivedMails++;
								}
							}
						}
					}
					if (arrivedMails > 0 && containerVO.getMailDetails() != null
							&& arrivedMails == containerVO.getMailDetails().size()) {
						scannedMailVo.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
					} else if (arrivedMails > 0 && containerVO.getMailDetails() != null
							&& arrivedMails != containerVO.getMailDetails().size()) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_CONTAINER_REUSE,
								MailHHTBusniessException.INVALID_CONTAINER_REUSE, scannedMailVo);
					}
				}
			}
		}
		boolean coTerminusCheck = false;
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		if (isCoterminusConfigured != null
				&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailVo.getProcessPoint())) {
			String paCode = null;
			OfficeOfExchangeVO originOfficeOfExchangeVO = null;
			String scanPort = null;
			Collection<MailbagVO> mailBagVOs = scannedMailVo.getMailDetails();
			ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailVo.getAirportCode(), true);
			if (mailBagVOs != null && mailBagVOs.size() > 0) {
				for (MailbagVO mailBagvo : mailBagVOs) {
					MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(mailBagvo);
					if (newmailbagVO != null && newmailbagVO.getConsignmentDate() != null) {
						dspDate = localDateUtil.getLocalDateTime(newmailbagVO.getConsignmentDate(), null);
					}
					if (newmailbagVO != null && newmailbagVO.getPaCode() != null) {
						paCode = newmailbagVO.getPaCode();
					} else {
						paCode = mailController.findPAForOfficeOfExchange(scannedMailVo.getCompanyCode(),
								mailBagvo.getOoe());
					}
					originOfficeOfExchangeVO = validateOfficeOfExchange(scannedMailVo.getCompanyCode(),
							mailBagvo.getDoe());
					scanPort = mailBagvo.getScannedPort();
				}
			}
			if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
				Collection<ArrayList<String>> oECityArpCodes = null;
				Collection<String> gpaCode = new ArrayList<String>();
				String airprtCode = null;
				gpaCode.add(originOfficeOfExchangeVO.getCode());
				oECityArpCodes = mailController.findCityAndAirportForOE(scannedMailVo.getCompanyCode(), gpaCode);
				if (oECityArpCodes != null && oECityArpCodes.size() > 0) {
					for (ArrayList<String> cityAndArpForOE : oECityArpCodes) {
						airprtCode = cityAndArpForOE.get(2);
					}
				}
				originOfficeOfExchangeVO.setAirportCode(airprtCode);
			}
			if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() != null) {
				coTerminusCheck = mailController.validateCoterminusairports(scanPort,
						originOfficeOfExchangeVO.getAirportCode(), MailConstantsVO.RESDIT_DELIVERED, paCode, dspDate)
						|| mailController.validateCoterminusairports(scanPort,
								originOfficeOfExchangeVO.getAirportCode(), MailConstantsVO.RESDIT_READYFOR_DELIVERY,
								paCode, dspDate);
			}
		}
		if (MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailVo.getMailSource()) && containerAssignmentVO != null
				&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
				&& containerAssignmentVO.getFlightSequenceNumber() > 0
				&& !airportCode.equals(containerAssignmentVO.getAirportCode())
				&& !(MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getReleasedFlag())
						|| MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag()))) {
			constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
					MailHHTBusniessException.ULD_NOT_RELEASED_FROM_INBFLIHGT, scannedMailVo);
		}
		if (containerAssignmentVO != null && (airportCode.equals(containerAssignmentVO.getAirportCode())
				|| ((airportCode.equals(containerAssignmentVO.getPou())
						|| airportCode.equals(containerAssignmentVO.getDestination()))))
				|| coTerminusCheck) {
			airportFlag = true;
		}
		if (containerAssignmentVO != null && canReuseEmptyContainerCheckEnabled() && airportFlag
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailVo.getProcessPoint())
				&& DEST_FLT.equals(containerAssignmentVO.getFlightNumber())
				&& !airportCode.equals(containerAssignmentVO.getAirportCode())) {
			airportFlag = false;
		}
		if (containerAssignmentVO != null && !airportFlag
				&& containerAssignmentVO.getTransitFlag().equals(MailConstantsVO.FLAG_YES)) {
			boolean canReuseContainer = false;
			if (containerAssignmentVO.getFlightNumber().equals(DEST_FLT)
					&& scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
				canReuseContainer = canReuseEmptyContainer(containerAssignmentVO);
			}
			if (canReuseContainer) {
				deleteCarrierEmptyContainer(containerAssignmentVO);
			} else {
				StringBuilder errorString = new StringBuilder();
				errorString.append("This BULK/ULD is present in another flight/carrier ");
				if (containerAssignmentVO.getFlightNumber().trim().length() > 0
						&& !containerAssignmentVO.getFlightNumber().equals(DEST_FLT)) {
					errorString.append(containerAssignmentVO.getFlightNumber());
				}
				errorString.append(" ");
				if (containerAssignmentVO.getFlightDate() != null) {
					errorString.append(containerAssignmentVO.getFlightDate()
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
				}
				errorString.append(" at ");
				errorString.append(containerAssignmentVO.getAirportCode());
				String error = errorString.toString();
				constructAndRaiseException(MailMLDBusniessException.ULDBULK_ANOTHER_FLIGHT, error, scannedMailVo);
			}
		}
		if (containerAssignmentVO != null && !airportCode.equals(containerAssignmentVO.getAirportCode())
				&& String.valueOf(MailConstantsVO.DESTN_FLT).equals(containerAssignmentVO.getFlightNumber())
				&& MailConstantsVO.DESTN_FLT == (containerAssignmentVO.getFlightSequenceNumber())
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailVo.getProcessPoint())
				&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
			StringBuilder errorString = new StringBuilder();
			errorString.append("This BULK/ULD is present in another flight/carrier ");
			if (containerAssignmentVO.getFlightNumber().trim().length() > 0
					&& !containerAssignmentVO.getFlightNumber().equals(DEST_FLT)) {
				errorString.append(containerAssignmentVO.getFlightNumber());
			}
			errorString.append(" ");
			if (containerAssignmentVO.getFlightDate() != null) {
				errorString.append(containerAssignmentVO.getFlightDate()
						.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
			}
			errorString.append(" at ");
			errorString.append(containerAssignmentVO.getAirportCode());
			String error = errorString.toString();
			constructAndRaiseException(MailMLDBusniessException.ULDBULK_ANOTHER_FLIGHT, error, scannedMailVo);
		}
		if (!checkAutoArrival(scannedMailVo) && containerAssignmentVO != null
				&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailVo.getProcessPoint())
				&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
			if ((!(containerAssignmentVO.getFlightNumber().equals(scannedMailVo.getFlightNumber()))
					&& (!"".equals(scannedMailVo.getFlightNumber())))
					|| (containerAssignmentVO.getFlightSequenceNumber() != scannedMailVo.getFlightSequenceNumber())
							&& (scannedMailVo.getFlightSequenceNumber() != 0)) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_CONTAINER_REUSE,
						MailHHTBusniessException.INVALID_CONTAINER_REUSE, scannedMailVo);
			}
		}
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailVo.getProcessPoint())
				|| scannedMailVo.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
			scannedMailVo.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
			for (MailbagVO mailbagVO : scannedMailVo.getMailDetails()) {
				if (mailbagVO.getPaBuiltFlag() != null && mailbagVO.getPaBuiltFlag().trim().length() != 0
						&& mailbagVO.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
					scannedMailVo.setNewContainer(MailConstantsVO.FLAG_YES);
				}
			}
		}
		if (containerAssignmentVO != null) {
			UpdateContainerProcessPointforAndroid(scannedMailVo, containerAssignmentVO, containerVo, scanningPort);
			if ("".equals(scannedMailVo.getCarrierCode()) && "".equals(scannedMailVo.getFlightNumber())) {
				scannedMailVo.setCarrierCode(containerAssignmentVO.getCarrierCode());
				scannedMailVo.setFlightNumber(containerAssignmentVO.getFlightNumber());
				scannedMailVo.setFlightDate(containerAssignmentVO.getFlightDate());
				scannedMailVo.setPou(containerAssignmentVO.getPou() != null ? containerAssignmentVO.getPou()
						: scannedMailVo.getPou());
				scannedMailVo.setDestination(containerAssignmentVO.getDestination());
				scannedMailVo.setCarrierId(containerAssignmentVO.getCarrierId());
				scannedMailVo.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				scannedMailVo.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				scannedMailVo.setContainerProcessPoint(null);
			}
			containerOffloadandReassignValidations(scannedMailVo, containerAssignmentVO);
		}
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailVo.getProcessPoint())
				&& containerAssignmentVO == null) {
			if (scannedMailVo.getMailDetails().iterator().next().getMailbagId().length() != 29
					&& !isValidMailtag(scannedMailVo.getMailDetails().iterator().next().getMailbagId().length())) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_ACP_TO_FLT,
						"Container is not accepted to any flight", scannedMailVo);
			}
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param airportCode
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private Collection<FlightValidationVO> validateFlightandUpdateVO(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, String airportCode)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		boolean validFlightFlag = false;
		boolean isStatusTBAorTBC = false;
		boolean inValidPol = false;
		boolean isFlightClosed = false;
		String route = null;
		String routeArr[] = null;
		Collection<FlightValidationVO> flightDetailsVOs = null;
		int segSerNum = 0;
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(scannedMailDetailsVO.getCompanyCode(),
				scannedMailDetailsVO.getCarrierCode());
		if (airlineValidationVO != null) {
			scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		} else {
			constructAndRaiseException(MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION,
					MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
		}
		try {
			FlightFilterVO flightFilterVO = createFlightFilterVO(scannedMailDetailsVO, logonAttributes, airportCode);
			flightDetailsVOs = mailController.validateFlight(flightFilterVO);
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()
					&& (flightDetailsVOs == null || flightDetailsVOs.isEmpty())) {
				scannedMailDetailsVO.setFlightNumber("");
				return flightDetailsVOs;
			}
			if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
				validFlightFlag = true;
				isStatusTBAorTBC = checkIfFlightToBeCancelled(flightDetailsVOs, scannedMailDetailsVO, airportCode);
				if (!isStatusTBAorTBC) {
					for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
						if (FlightValidationVO.FLT_STATUS_ACTIVE.equals(flightValidationVO.getFlightStatus())
								|| FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {
							if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
									&& flightValidationVO.getLegDestination().equals(airportCode))
									|| flightValidationVO.getLegOrigin().equals(airportCode)
									|| (MailConstantsVO.MAIL_STATUS_DELIVERED
											.equals(scannedMailDetailsVO.getProcessPoint()))) {
								log.info("" + "VO is" + " " + flightValidationVO);
								updateFlightDetailsForAndroid(scannedMailDetailsVO, flightValidationVO);
								route = flightValidationVO.getFlightRoute();
								routeArr = route.split("-");
								ArrayList<String> pols = new ArrayList<String>();
								for (String airport : routeArr) {
									if (!airport.equals(scannedMailDetailsVO.getAirportCode())) {
										pols.add(airport);
									} else {
										break;
									}
								}
								if (!pols.isEmpty()) {
									Collections.reverse(pols);
								}
								scannedMailDetailsVO.setPols(pols);
								break;
							}
						}
					}
				}
			}
		} finally {
		}
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				&& scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
			try {
				segSerNum = findFlightSegment(scannedMailDetailsVO.getCompanyCode(),
						scannedMailDetailsVO.getCarrierId(), scannedMailDetailsVO.getFlightNumber(),
						scannedMailDetailsVO.getFlightSequenceNumber(), scannedMailDetailsVO.getPol(),
						scannedMailDetailsVO.getPou());
			} finally {
			}
			if (scannedMailDetailsVO.isForceAcpAfterErr() && scannedMailDetailsVO.getMailDetails() != null
					&& !scannedMailDetailsVO.getMailDetails().isEmpty() && flightDetailsVOs != null
					&& !flightDetailsVOs.isEmpty()
					&& !MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())) {
				boolean isFlightClosedforMail = false;
				OperationalFlightVO operationalFlightVO = createOperationalFlightVO(flightDetailsVOs);
				try {
					isFlightClosedforMail = isFlightClosedForOperations(operationalFlightVO);
				} finally {
				}
				if (isFlightClosedforMail) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				}
			}
			scannedMailDetailsVO.setSegmentSerialNumber(segSerNum);
		}
		if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()))) {
			ContainerAssignmentVO containerAssignmentVO = null;
			try {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
						&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
								|| (scannedMailDetailsVO.getMailDetails() == null
										|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
					containerAssignmentVO = this.cntAsgmntVO == null
							? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
							: this.cntAsgmntVO;
					this.cntAsgmntVO = containerAssignmentVO;
				} else {
					containerAssignmentVO = findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				}
				if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()))) {
					if (containerAssignmentVO != null) {
						if (scannedMailDetailsVO.getPol() != null
								&& containerAssignmentVO.getFlightNumber()
										.equals(scannedMailDetailsVO.getFlightNumber())
								&& containerAssignmentVO
										.getFlightSequenceNumber() == (scannedMailDetailsVO.getFlightSequenceNumber())
								&& !containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getPol())) {
							inValidPol = true;
						} else {
							if (scannedMailDetailsVO.getPols() != null && scannedMailDetailsVO.getPols().size() > 0) {
								scannedMailDetailsVO.setPol(scannedMailDetailsVO.getPols().iterator().next());
							}
						}
					} else if (scannedMailDetailsVO.getPol() != null && scannedMailDetailsVO.getPols() != null
							&& scannedMailDetailsVO.getPols().size() > 0
							&& !scannedMailDetailsVO.getPols().contains(scannedMailDetailsVO.getPol())) {
						inValidPol = true;
					} else {
						if ((scannedMailDetailsVO.getPol() == null || scannedMailDetailsVO.getPol().trim().length() < 1)
								&& scannedMailDetailsVO.getPols() != null
								&& scannedMailDetailsVO.getPols().size() > 0) {
							scannedMailDetailsVO.setPol(scannedMailDetailsVO.getPols().iterator().next());
						}
					}
				}
			} finally {
			}
		}
		if (isStatusTBAorTBC && MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
				&& scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()
				&& scannedMailDetailsVO.getMailDetails().iterator().next().isDeliveryNeeded()) {
			scannedMailDetailsVO.setFoundDelivery(true);
			scannedMailDetailsVO.setFlightNumber("");
		} else if (isStatusTBAorTBC
				&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
				&& FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())) {
			scannedMailDetailsVO.setFoundDelivery(true);
		}
		if (!validFlightFlag) {
			constructAndRaiseException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT,
					MailHHTBusniessException.INVALID_FLIGHT, scannedMailDetailsVO);
		}
		if (isStatusTBAorTBC && !scannedMailDetailsVO.isFoundDelivery()) {
			constructAndRaiseException(MailMLDBusniessException.FLIGHT_TBA_TBC,
					MailHHTBusniessException.CANCELLED_FLIGHT, scannedMailDetailsVO);
		}
		if (inValidPol) {
			constructAndRaiseException(MailMLDBusniessException.INVALID_POL, MailHHTBusniessException.INVALID_POL,
					scannedMailDetailsVO);
		}
		return flightDetailsVOs;
	}

	private void updateFlightDetailsForAndroid(ScannedMailDetailsVO scannedMailDetailsVO,
			FlightValidationVO flightValidationVO) {
		scannedMailDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		scannedMailDetailsVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		scannedMailDetailsVO.setCompanyCode(flightValidationVO.getCompanyCode());
		ArrayList<MailbagVO> mailbagVOs = (ArrayList<MailbagVO>) scannedMailDetailsVO.getMailDetails();
		if (mailbagVOs != null) {
			for (MailbagVO maibagVO : mailbagVOs) {
				maibagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				maibagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				maibagVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				maibagVO.setCarrierCode(flightValidationVO.getCarrierCode());
				maibagVO.setFlightNumber(flightValidationVO.getFlightNumber());
				maibagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
				maibagVO.setCompanyCode(flightValidationVO.getCompanyCode());
			}
		}
		if (flightValidationVO.getAtd() != null) {
			scannedMailDetailsVO.setAtdCaptured(true);
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param mailUploadVo
	* @param mailbagVO
	* @param logonAttributes
	* @param scanningPort
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	* @throws SystemException
	*/
	public ScannedMailDetailsVO constructScannedMailDetailVO(ScannedMailDetailsVO scannedMailDetailsVO,
			MailUploadVO mailUploadVo, MailbagVO mailbagVO, LoginProfile logonAttributes, String scanningPort)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		setErrorMailDetails(scannedMailDetailsVO);
		initializeVOforAndroid(scannedMailDetailsVO, mailUploadVo, logonAttributes);
		setMailBags(mailbagVO, mailUploadVo, scannedMailDetailsVO);
		setFlightDetails(scannedMailDetailsVO, mailUploadVo);
		setContainerDetails(scannedMailDetailsVO, mailUploadVo);
		setPolPouDestinationDetailsForAndroid(scannedMailDetailsVO, mailUploadVo, scanningPort, mailbagVO);
		setContainerDetails(mailbagVO, mailUploadVo, scannedMailDetailsVO);
		setScanInformationForAndroid(mailUploadVo, scannedMailDetailsVO, scanningPort);
		setScreeningDetails(mailUploadVo, scannedMailDetailsVO, scanningPort);
		setFlightDetails(scannedMailDetailsVO, logonAttributes);
		setLatestContainerAssignmentVO(mailUploadVo,scannedMailDetailsVO);
		return scannedMailDetailsVO;
	}

	private void setErrorMailDetails(ScannedMailDetailsVO scannedMailDetailsVO) {
		Collection<MailbagVO> errorMailDetails = new ArrayList<MailbagVO>();
		scannedMailDetailsVO.setErrorMailDetails(errorMailDetails);
	}
	private void setFlightDetails(ScannedMailDetailsVO scannedMailDetailsVO,LoginProfile logonAttributes) throws ForceAcceptanceException, MailHHTBusniessException, MailMLDBusniessException {
		if(scannedMailDetailsVO.getFlightValidationVOS() ==null||scannedMailDetailsVO.getFlightValidationVOS().isEmpty())
		scannedMailDetailsVO.setFlightValidationVOS(validateFlight(scannedMailDetailsVO, logonAttributes));
	}

	private void setLatestContainerAssignmentVO(MailUploadVO mailUploadVO,ScannedMailDetailsVO scannedMailDetailsVO){
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() == null
					|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
				scannedMailDetailsVO.setContainerAsSuchArrOrDlvFlag(MailConstantsVO.FLAG_YES);
				if (mailUploadVO.isDeliverd()) {
					scannedMailDetailsVO.setContainerDeliveryFlag(MailConstantsVO.FLAG_YES);
				}
				scannedMailDetailsVO.setOverrideValidations(mailUploadVO.getOverrideValidation());
			}
		}
		if(scannedMailDetailsVO.getContainerNumber()!=null &&scannedMailDetailsVO.getContainerNumber().trim().length()>0){
			try{
				if (MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())) {
					scannedMailDetailsVO.setLatestContainerAssignmentVO(
							mailController.findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO));
				}else{
					scannedMailDetailsVO.setLatestContainerAssignmentVO(
							mailController.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber()));
				}
			}catch(Exception exception){
				log.error("Exception :", exception);
			}
		}

	}

	/**
	* @param mailbagVO
	* @param mailUploadVO
	* @param scannedMailDetailsVO
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void setMailBags(MailbagVO mailbagVO, MailUploadVO mailUploadVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		Collection<MailbagVO> mailbagVOlist = new ArrayList<MailbagVO>();
		try {
			setMailbagDetailsForAndroid(mailbagVO, mailUploadVO, scannedMailDetailsVO);
		} catch (FinderException e) {
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
		setDamageAndOffloadDetails(mailbagVO, mailUploadVO);
		if (mailbagVO.getMailbagId() != null)
			mailbagVOlist.add(mailbagVO);
		else if (scannedMailDetailsVO.getProcessPoint().equals("OFL") && mailbagVO.getMailbagId() == null) {
			mailbagVO.setMailbagId(mailUploadVO.getMailTag());
			mailbagVOlist.add(mailbagVO);
		}
		if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailUploadVO.getScanType())
				&& mailbagVO.getMailbagId().trim().length() == 12) {
			scannedMailDetailsVO.setPou(mailbagVO.getPou());
			scannedMailDetailsVO.setDestination(mailbagVO.getPol());
		}
		scannedMailDetailsVO.setMailDetails(mailbagVOlist);
	}

	/**
	* @param scannedMailDetailsVO
	* @param companyCode
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void validatePOUandDestination(ScannedMailDetailsVO scannedMailDetailsVO, String companyCode)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		boolean isValidAirport = false;
		if (!(MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint()))) {
			try {
				if (scannedMailDetailsVO.getPou() != null)
					isValidAirport = validateAirport(scannedMailDetailsVO.getPou(), companyCode);
				if (isValidAirport) {
					isValidAirport = validateAirport(scannedMailDetailsVO.getDestination(), companyCode);
				}
			} finally {
			}
			if (!isValidAirport) {
				constructAndRaiseException("Invalid POU/Destination", "Invalid POU/Destination", scannedMailDetailsVO);
			}
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private void processPointSpecificMailbagValidation(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		new MailOperationsValidator().doProcessPointSpecificMailbagValidationsForAndroid(scannedMailDetailsVO);
	}

	/**
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @throws ForceAcceptanceException
	*/
	private void performContainerAsSuchOperations(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes) throws MailMLDBusniessException, MailHHTBusniessException,
			MailOperationsBusinessException, ForceAcceptanceException {
		ScannedMailDetailsVO scannedDetailsVOForContainerTxn = new ScannedMailDetailsVO();
		try {
			scannedDetailsVOForContainerTxn = mailOperationsMapper.copyScannedMailDetailsVO( scannedMailDetailsVO);
		} finally {
		}
		performContainerAsSuchOperation(scannedMailDetailsVO, scannedDetailsVOForContainerTxn, logonAttributes);
	}

	/**
	* @param scannedMailDetailsVO
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @throws ForceAcceptanceException
	*/
	private void performContainerAsSuchOperation(ScannedMailDetailsVO scannedMailDetailsVO,
			ScannedMailDetailsVO scannedDetailsVOForContainerTxn, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			ForceAcceptanceException {
		if (MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			saveAcceptanceFromAndroid(scannedDetailsVOForContainerTxn, logonAttributes);
		}
		if (MailConstantsVO.CONTAINER_STATUS_REASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn
					.setScannedContainerDetails(scannedMailDetailsVO.getScannedContainerDetails());
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			savereassignFromAndroid(scannedDetailsVOForContainerTxn, logonAttributes);
			if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
					&& !scannedMailDetailsVO.isRsgnmailbagFromdiffContainer()) {
				scannedMailDetailsVO.setMailDetails(null);
			}
		}
		if (MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
			scannedDetailsVOForContainerTxn.setMailDetails(null);
			saveTransferFromUpload(scannedDetailsVOForContainerTxn, logonAttributes);
			if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
				scannedMailDetailsVO.setMailDetails(null);
			}
			if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
				scannedMailDetailsVO.setMailDetails(null);
			}
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	public void saveAcceptanceFromAndroid(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveAcceptanceFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<MailAcceptanceVO>();
			MailAcceptanceVO mailAcceptanceVO = null;
			mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
			if (mailAcceptanceVO != null) {
				mailAcceptanceVOs.add(mailAcceptanceVO);
				log.info("" + "MailAcceptacne Vos " + " " + mailAcceptanceVOs);
				try {
					saveScannedOutboundDetails(mailAcceptanceVOs);
				} catch (DuplicateMailBagsException e) {
					constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
							MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
				} catch (FlightClosedException e) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				} catch (ContainerAssignmentException e) {
					if (MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource()) && e != null
							&& !e.getErrors().isEmpty() && ContainerAssignmentException.ULD_NOT_RELEASED_FROM_INB_FLIGHT
									.equals(e.getErrors().iterator().next().getErrorCode())) {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
								"ULD is not yet released", scannedMailDetailsVO);
					} else {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
								MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
					}
				} catch (InvalidFlightSegmentException e) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT_EXCEPTION,
							MailHHTBusniessException.INVALID_FLIGHT_SEGMENT_EXCEPTION, scannedMailDetailsVO);
				} catch (ULDDefaultsProxyException e) {
					constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
							MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
				} catch (DuplicateDSNException e) {
					constructAndRaiseException(MailMLDBusniessException.DUPLICATE_DSN_EXCEPTION,
							MailHHTBusniessException.DUPLICATE_DSN_EXCEPTION, scannedMailDetailsVO);
				} catch (CapacityBookingProxyException e) {
					constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
							MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
				} catch (MailBookingException e) {
					constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
							MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
				} catch (MailDefaultStorageUnitException ex) {
					constructAndRaiseException(
							MailMLDBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
							MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
							scannedMailDetailsVO);
				}
			}
		}
		log.debug("saveAcceptanceSession" + " : " + "execute" + " Exiting");
	}

	/**
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void savereassignFromAndroid(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "savereassignFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			String airportCode = ((scannedMailDetailsVO.getMailSource() != null
					&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN))
					|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
							? scannedMailDetailsVO.getAirportCode()
							: logonAttributes.getAirportCode();
			try {
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0
						&& scannedMailDetailsVO.getValidatedContainer() != null) {
					ContainerVO toContainerVO = constructContainerVO(airportCode,
							scannedMailDetailsVO.getValidatedContainer(), logonAttributes,
							scannedMailDetailsVO.getMailSource());
					if (scannedMailDetailsVO.isContainerDestChanged()) {
						toContainerVO.setContainerDestChanged(scannedMailDetailsVO.isContainerDestChanged());
					}
					log.info("" + "toContainerVO for reasign" + " " + toContainerVO);
					Collection<MailbagVO> mails = scannedMailDetailsVO.getMailDetails();
					for (MailbagVO mail : mails) {
						MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(mail);
						if (existingMailbagVO != null) {
							updateExistingMailBagVO(mail, existingMailbagVO, false);
						}
						if (mail.getPaBuiltFlag() != null && mail.getPaBuiltFlag().trim().length() > 0) {
							toContainerVO.setPaBuiltFlag(mail.getPaBuiltFlag());
						}
					}
					reassignMailbagsfromAndroid(scannedMailDetailsVO.getMailDetails(), toContainerVO);
				}
				if (scannedMailDetailsVO.getScannedContainerDetails() != null
						&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
					OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(airportCode,
							scannedMailDetailsVO, logonAttributes);
					for (ContainerVO container : scannedMailDetailsVO.getScannedContainerDetails()) {
						String carrierCode = container.getCarrierCode();
						container.setSource(scannedMailDetailsVO.getMailSource());
						boolean isPartner = new MailOperationsValidator().checkIfPartnerCarrier(airportCode,
								carrierCode);
						log.info(""
								+ "After MailtrackingDefaultsValidator().validateScannedMailDetails :: scannedMailDetailsVO"
								+ " " + scannedMailDetailsVO);
						if (!isPartner) {
							constructAndRaiseException(MailMLDBusniessException.NON_PARTNER_CARRIER,
									MailHHTBusniessException.NON_PARTNER_CARRIER, scannedMailDetailsVO);
						}
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					if (MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())) {
						OperationalFlightVO opFlightVO = constructOpFlightFromContainer(
								scannedMailDetailsVO.getScannedContainerDetails().iterator().next(), false);
						opFlightVO.setPol(scannedMailDetailsVO.getPol());
						if (isFlightClosedForOperations(opFlightVO)) {
							for (ContainerVO container : scannedMailDetailsVO.getScannedContainerDetails()) {
								container.setOffloadedReason(findSystemParameterValue(DEFAULT_OFFLOADCODE));
								container.setLastUpdateUser(scannedMailDetailsVO.getScannedUser());
								container.setOffload(true);
							}
							saveOffloadFromUpload(scannedMailDetailsVO, logonAttributes);
							for (ContainerVO container : scannedMailDetailsVO.getScannedContainerDetails()) {
								container.setFlightSequenceNumber(-1);
								container.setLegSerialNumber(-1);
								container.setFlightNumber("-1");
								container.setOffload(false);
							}
							mailController.reassignContainers(scannedMailDetailsVO.getScannedContainerDetails(),
									operationalFlightVO);
						} else {
							mailController.reassignContainers(scannedMailDetailsVO.getScannedContainerDetails(),
									operationalFlightVO);
						}
					} else {
						mailController.reassignContainers(scannedMailDetailsVO.getScannedContainerDetails(),
								operationalFlightVO);
					}
				}
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
					Map<String, Collection<OneTimeVO>> oneTimes = null;
					oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
					if (scannedMailDetailsVO.getMailDetails().iterator().next().getActionMode() != null
							&& "DMG".equals(scannedMailDetailsVO.getMailDetails().iterator().next().getActionMode())) {
						updateVOForDammageCapture(scannedMailDetailsVO, logonAttributes, oneTimes);
						updateDamagedMailbags(scannedMailDetailsVO.getMailDetails());
					}
				}
			} catch (FlightClosedException e) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			} catch (ContainerAssignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (InvalidFlightSegmentException e) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
						MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
			} catch (ULDDefaultsProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
						MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
			} catch (CapacityBookingProxyException e) {
				constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
						MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (MailBookingException e) {
				constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
						MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
			} catch (ReassignmentException e) {
				constructAndRaiseException(MailMLDBusniessException.REASSIGNMENT_EXCEPTION,
						MailHHTBusniessException.REASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
			} catch (MailDefaultStorageUnitException ex) {
				constructAndRaiseException(MailMLDBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
						MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
						scannedMailDetailsVO);
			}
		}
		log.debug("saveAcceptanceSession" + " : " + "execute" + " Exiting");
	}

	/**
	* @param scannedMailDetailsVO
	* @param containerAssignmentVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void containerOffloadandReassignValidations(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (scannedMailDetailsVO.getScannedContainerDetails() != null
					&& !scannedMailDetailsVO.getScannedContainerDetails().isEmpty()) {
				for (ContainerVO scannedContainerVO : scannedMailDetailsVO.getScannedContainerDetails()) {
					if (containerAssignmentVO != null) {
						if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
							if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getOffloadStatus())
									&& ("-1").equals(containerAssignmentVO.getFlightNumber())
									&& containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
									&& containerAssignmentVO.getLegSerialNumber() == MailConstantsVO.DESTN_FLT
									&& containerAssignmentVO.getSegmentSerialNumber() == MailConstantsVO.DESTN_FLT) {
								constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_OFFLOADED,
										"Container is already offloaded", scannedMailDetailsVO);
							} else if (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getOffloadStatus())
									&& ("-1").equals(containerAssignmentVO.getFlightNumber())
									&& containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
									&& containerAssignmentVO.getLegSerialNumber() == MailConstantsVO.DESTN_FLT
									&& (containerAssignmentVO.getSegmentSerialNumber() == MailConstantsVO.DESTN_FLT
											|| containerAssignmentVO.getSegmentSerialNumber() == 0)) {
								constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_FLIGHT,
										"Only Containers assigned to a flight can be offloaded", scannedMailDetailsVO);
							} else if (!scannedContainerVO.getAssignedPort()
									.equals(containerAssignmentVO.getAirportCode())
									|| containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_YES
											.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())) {
								constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
										"Container is not available at this airport", scannedMailDetailsVO);
							} else if (!MailConstantsVO.FLIGHT_STATUS_CLOSED
									.equals(containerAssignmentVO.getFlightStatus())) {
								constructAndRaiseException(MailMLDBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION,
										MailHHTBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION, scannedMailDetailsVO);
							}
						} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							if (containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
									&& scannedMailDetailsVO.getCarrierCode()
											.equals(containerAssignmentVO.getCarrierCode())
									&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
											.getFlightSequenceNumber()
									&& containerAssignmentVO.getLegSerialNumber() == scannedMailDetailsVO
											.getLegSerialNumber()
									&& scannedContainerVO.getAssignedPort()
											.equals(containerAssignmentVO.getAirportCode())) {
								constructAndRaiseException(
										MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_EXIST_IN_SAME_FLIGHT,
										"Container is already exists in the same flight", scannedMailDetailsVO);
							} else if (MailConstantsVO.FLIGHT_STATUS_CLOSED
									.equals(containerAssignmentVO.getFlightStatus())) {
								constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
										MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
							}
						}
					} else {
						constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_ACP_TO_FLT,
								"Container is not accepted to any flight", scannedMailDetailsVO);
					}
				}
			} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())
					&& containerAssignmentVO != null
					&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
					&& scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length() != 29
					&& !isValidMailtag(scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length())
					&& (!scannedMailDetailsVO.getAirportCode().equals(containerAssignmentVO.getAirportCode())
							|| (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_YES
									.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())))) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
						"Container is not available at this airport", scannedMailDetailsVO);
			}
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param scannedPort
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @throws PersistenceException
	* @throws ForceAcceptanceException
	*/
	private void updateProcessPointforMailBags(ScannedMailDetailsVO scannedMailDetailsVO, String scannedPort)
			throws MailMLDBusniessException, MailHHTBusniessException, PersistenceException, ForceAcceptanceException {
		Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
		String paCode = null;
		if (mailBagVOs != null) {
			for (MailbagVO mailBagVOFromUpload : mailBagVOs) {
				paCode = mailBagVOFromUpload.getPaCode() != null && mailBagVOFromUpload.getPaCode().trim().length() > 0
						? mailBagVOFromUpload.getPaCode()
						: mailController.findPAForOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(),
								mailBagVOFromUpload.getOoe());
				if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
					MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(mailBagVOFromUpload);
					if (existingMailbagVO != null) {
						updateExistingMailBagVO(mailBagVOFromUpload, existingMailbagVO, true);
					}
				}
				if (mailBagVOFromUpload.getMailbagId().trim().length() == 0) {
					scannedMailDetailsVO.setMailDetails(null);
				}
				if (mailBagVOFromUpload.getMailbagId().trim().length() == 29 || (isValidMailtag(
						scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length()))) {
					Mailbag mailBag = null;
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					boolean enableAutoArrival = false;
					enableAutoArrival = checkAutoArrival(scannedMailDetailsVO);
					long sequencenum = 0;
					try {
						sequencenum = mailBagVOFromUpload.getMailSequenceNumber() == 0
								? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagVOFromUpload.getMailbagId(),
										scannedMailDetailsVO.getCompanyCode())
								: mailBagVOFromUpload.getMailSequenceNumber();
					} finally {
					}
					mailbagPk.setMailSequenceNumber(sequencenum);
					try {
						mailBag = Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())
								&& !enableAutoArrival) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
									MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
						} else {
							log.error("Finder Exception Caught");
						}
					}
					if (!enableAutoArrival
							&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())
							&& MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailBag.getLatestStatus())) {
						constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_ALREADY_RETURNED,
								MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
					}
					if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
						scannedMailDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
						mailBagVOFromUpload.setPou(scannedMailDetailsVO.getAirportCode());
					}
					if (((mailBag != null) && (MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus())))) {
						mailBag = null;
					}
					ArrayList<String> systemParameters = new ArrayList<String>();
					systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
					systemParameters.add(AUTOARRIVALENABLEDPAS);
					Map<String, String> systemParameterMap = null;
					try {
						systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
					} catch (BusinessException e) {
						throw new RuntimeException(e);
					}
					String sysparfunctionpoints = null;
					String autoArrEnabledPAs = null;
					boolean isAutoArrival = false;
					if (systemParameterMap != null) {
						sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
						autoArrEnabledPAs = systemParameterMap.get(AUTOARRIVALENABLEDPAS);
					}
					if (sysparfunctionpoints != null
							&& sysparfunctionpoints.contains(MailConstantsVO.MAIL_STATUS_TRANSFERRED)) {
						isAutoArrival = true;
					}
					if (mailBag != null && mailBag.getLatestStatus() != null
							&& !(MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus()))) {
						ContainerAssignmentVO containerAssignmentVO = null;
						String containerNumber = null;
						if (scannedMailDetailsVO.getContainerNumber() != null
								&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
							containerNumber = scannedMailDetailsVO.getContainerNumber();
						} else {
							containerNumber = mailBag.getUldNumber();
						}
						if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
								&& scannedMailDetailsVO.getContainerType() != null
								&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
							containerNumber = mailBag.getUldNumber();
						}
						if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
								&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
								&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
								&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
										|| (scannedMailDetailsVO.getMailDetails() == null
												|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
							containerAssignmentVO = this.cntAsgmntVO == null
									? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
									: this.cntAsgmntVO;
							this.cntAsgmntVO = containerAssignmentVO;
						} else {
							containerAssignmentVO = ((this.cntAsgmntVO == null) || !(containerNumber != null
									&& containerNumber.equals(this.cntAsgmntVO.getContainerNumber())))
											? findLatestContainerAssignment(containerNumber)
											: this.cntAsgmntVO;
							this.cntAsgmntVO = containerAssignmentVO;
						}
						if (MailConstantsVO.MAIL_STATUS_RETURNED
								.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
							mailBagVOFromUpload.setOperationalStatus(mailBag.getOperationalStatus());
						}
						if (MailConstantsVO.MAIL_STATUS_DAMAGED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
								|| MailConstantsVO.MAIL_STATUS_RETURNED
										.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
							updateFlightDetailsForDamageCapture(mailBagVOFromUpload, mailBag, containerAssignmentVO);
						}
						if ((MailConstantsVO.MAIL_STATUS_HND.equals(mailBag.getLatestStatus())
								|| (mailBag.getLatestStatus() != null
										&& mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)))
								&& mailBagVOFromUpload.getDeliveredFlag() != null
								&& mailBagVOFromUpload.getDeliveredFlag().equals(MailConstantsVO.FLAG_YES)) {
							scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_DELIVERED);
						}
						if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
								|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
								|| MailConstantsVO.MAIL_STATUS_ACCEPTED
										.equals(scannedMailDetailsVO.getProcessPoint())) {
							if (isAutoArrival) {
								if (MailConstantsVO.OPERATION_OUTBOUND.equals(mailBag.getOperationalStatus())
										&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailBag.getLatestStatus())
												|| mailBag.getLatestStatus()
														.equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
												|| mailBag.getLatestStatus()
														.equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)
												|| MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_OFFLOADED
														.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
														.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailBag.getLatestStatus()))
										&& mailBag.getScannedPort() != null
										&& scannedMailDetailsVO.getAirportCode() != null
										&& (scannedMailDetailsVO.getAirportCode().equals(mailBag.getScannedPort()))) {
									scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
								}
								if ((mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED) || ((mailBag
										.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
										|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)
										|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)
										|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)
										|| (MailConstantsVO.OPERATION_OUTBOUND.equals(mailBag.getOperationalStatus())
												&& MailConstantsVO.MAIL_STATUS_TRANSFERRED
														.equals(mailBag.getLatestStatus())))
										&& !scannedMailDetailsVO.getAirportCode().equals(mailBag.getScannedPort())))) {
									scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
								} else if ((mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED)
										|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ASSIGNED))
										&& scannedMailDetailsVO.getAirportCode().equals(mailBag.getScannedPort())) {
									scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
								}
							} else {
								if (MailConstantsVO.OPERATION_OUTBOUND.equals(mailBag.getOperationalStatus())
										&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailBag.getLatestStatus())
												|| mailBag.getLatestStatus()
														.equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
												|| mailBag.getLatestStatus()
														.equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)
												|| MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_OFFLOADED
														.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
														.equals(mailBag.getLatestStatus())
												|| MailConstantsVO.MAIL_STATUS_HNDRCV
														.equals(mailBag.getLatestStatus()))) {
									scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
								} else if (MailConstantsVO.OPERATION_INBOUND.equals(mailBag.getOperationalStatus())) {
									if ((mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)
											|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DAMAGED))
											&& mailBag.getScannedPort().equals(scannedPort)) {
										scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
									} else if (mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED)
											|| mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)) {
										scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
									}
								}
							}
							if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL)
									&& scannedMailDetailsVO.getContainerNumber() != null
									&& mailBag.getUldNumber() != null
									&& !scannedMailDetailsVO.getContainerNumber().equals(mailBag.getUldNumber())) {
								scannedMailDetailsVO.setRsgnmailbagFromdiffContainer(true);
							}
						}
					} else {
						String origin = mailBagVOFromUpload.getOrigin();
						boolean checkReceivedFromTruckEnabled;
						String isCoterminusConfigured = findSystemParameterValue(
								MailConstantsVO.IS_COTERMINUS_CONFIGURED);
						if (origin != null && !origin.equals(scannedMailDetailsVO.getAirportCode())
								&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
								&& !scannedMailDetailsVO.isOriginScan()) {
							checkReceivedFromTruckEnabled = checkReceivedFromTruckEnabled(
									scannedMailDetailsVO.getAirportCode(), origin, paCode);
							MailbagVO mailbagvo = new MailbagVO();
							if (isCoterminusConfigured != null && isCoterminusConfigured.equals("Y")
									&& checkReceivedFromTruckEnabled) {
								scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
								for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
									mailbagvo = constructDAO()
											.findTransferFromInfoFromCarditForMailbags(scannedMailbagVO);
									if ((scannedMailDetailsVO.getTransferFrmFlightNum() == null
											|| scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() == 0)) {
										if (mailbagvo != null
												&& scannedMailDetailsVO.getAirportCode().equals(mailbagvo.getPou())) {
											scannedMailDetailsVO
													.setTransferFrmFlightDate(mailbagvo.getFromFlightDate());
											scannedMailDetailsVO
													.setTransferFrmFlightNum(mailbagvo.getFromFightNumber());
											scannedMailDetailsVO
													.setTransferFromCarrier(mailbagvo.getTransferFromCarrier());
										} else {
											scannedMailDetailsVO
													.setProcessPoint(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
											scannedMailDetailsVO.setFoundTransfer(true);
										}
									}
								}
							} else {
								scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
								scannedMailDetailsVO.setFoundTransfer(true);
							}
						}
					}
				}
			}
		}
	}

	public void saveAndProcessMailBagsForAndroid(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			PersistenceException, ForceAcceptanceException {
		log.info("" + "saveAndProcessMailBags" + " " + scannedMailDetailsVO);
		LoginProfile logonAttributes = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALENABLEDPAS);
		Map<String, String> systemParameterMap = null;
			try {
				systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		String sysparfunctionpoints = null;
		String autoArrEnabledPAs = null;
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
			autoArrEnabledPAs = systemParameterMap.get(AUTOARRIVALENABLEDPAS);
		}
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		String paCode = null;
		String orginPort = null;
		String destinationPort = null;
		Collection<FlightValidationVO> flightDetailsVOs = null;
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		if (sysparfunctionpoints != null && sysparfunctionpoints.contains(scannedMailDetailsVO.getProcessPoint())
				&& scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0
				&& !"-1".equals(scannedMailDetailsVO.getMailDetails().iterator().next().getFlightNumber())
				&& checkAutoArrival(scannedMailDetailsVO)) {
			boolean enableAutoArrival = true;
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
			ScannedMailDetailsVO scannedMailDetailsVOUpdated = new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVos = new ArrayList<MailbagVO>();
			ScannedMailDetailsVO scannedMailDetailsVForArrival = new ScannedMailDetailsVO();
			Collection<MailbagVO> mailBagVosForArrival = new ArrayList<MailbagVO>();
			MailbagVO mailBagVoForArrival = new MailbagVO();
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {
					if (FUNPNTS_RTN.equals(scannedMailDetailsVO.getProcessPoint())) {
						MailbagPK mailbagPk = new MailbagPK();
						Mailbag mailBag = null;
						mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
						long sequencenum = mailBagVO.getMailSequenceNumber() == 0
								? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagVO.getMailbagId(),
										mailBagVO.getCompanyCode())
								: mailBagVO.getMailSequenceNumber();
						if (sequencenum > 0) {
							mailbagPk.setMailSequenceNumber(sequencenum);
						}
						try {
							mailBag = Mailbag.find(mailbagPk);
						} catch (FinderException e) {
							log.error(FINDER_EXCEPTION_CAUGHT);
						}
						if (mailBag != null && (mailBag.getScannedPort().equals(scannedMailDetailsVO.getAirportCode())
								|| checkforCoterminusAirport(scannedMailDetailsVO, MailConstantsVO.RESDIT_RECEIVED,
										logonAttributes)
								|| checkforCoterminusAirport(scannedMailDetailsVO, MailConstantsVO.RESDIT_UPLIFTED,
										logonAttributes))) {
							enableAutoArrival = false;
							break;
						}
					}
					if ((FUNPNTS_RTN).contains(scannedMailDetailsVO.getProcessPoint())) {
						if (autoArrEnabledPAs.contains(MailConstantsVO.ALL_RETURN_ENABLED_PA)) {
							enableAutoArrival = true;
						} else {
							if (!autoArrEnabledPAs.contains(mailBagVO.getPaCode())) {
								enableAutoArrival = false;
								break;
							}
						}
					}
					if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
							&& scannedMailDetailsVO.getMailDetails() != null
							&& scannedMailDetailsVO.getMailDetails().size() > 0
							&& scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus() != null
							&& (scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
									.equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_DAMAGED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED))) {
						try {

							scannedMailDetailsVForArrival = mailOperationsMapper.copyScannedMailDetailsVO(scannedMailDetailsVO);
							mailbagInULDForSegmentVO = new MailbagInULDForSegment()
									.getManifestInfo(scannedMailDetailsVForArrival);
						} catch (PersistenceException e) {
							log.error("SystemException caught");
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
						}
					} else {
						try {
							mailbagInULDForSegmentVO = new MailbagInULDForSegment()
									.getManifestInfo(scannedMailDetailsVO);
						} catch (PersistenceException e) {
							log.error("SystemException caught");
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
						}
					}
					if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint()))
							&& Objects.nonNull(mailbagInULDForSegmentVO) && StringUtils.equals(MailConstantsVO.FLAG_YES,
									mailbagInULDForSegmentVO.getArrivalFlag())) {
						mailBagVO.setFoundResditSent(true);
					}
					if (mailbagInULDForSegmentVO != null
							|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
						if (mailbagInULDForSegmentVO == null
								|| MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getArrivalFlag())) {
							enableAutoArrival = false;
							break;
						}
						mailBagVO.setAutoArriveMail(MailConstantsVO.FLAG_YES);
						mailBagVos.add(mailBagVO);
						mailBagVoForArrival = mailOperationsMapper.copyMailbagVO(mailBagVO);
						if (mailbagInULDForSegmentVO != null
								&& MailConstantsVO.MAIL_STATUS_TRANSFERRED
										.equals(scannedMailDetailsVO.getProcessPoint())
								&& MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getPaBuiltFlag())) {
							scannedMailDetailsVForArrival.setIsContainerPabuilt(MailConstantsVO.FLAG_YES);
							mailBagVoForArrival.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
						} else {
							scannedMailDetailsVForArrival.setIsContainerPabuilt(MailConstantsVO.FLAG_NO);
							mailBagVoForArrival.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
						}
						mailBagVosForArrival.add(mailBagVoForArrival);
					} else {
						scannedMailDetailsVO.setProcessPoint("ARR");
						constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_ARRIVED,
								"The mailbag is not arrived", scannedMailDetailsVO);
					}
				}
				if (enableAutoArrival) {
					scannedMailDetailsVO.setMailDetails(mailBagVos);
					scannedMailDetailsVForArrival.setMailDetails(mailBagVosForArrival);
				}
			}
			if (enableAutoArrival && scannedMailDetailsVO.getMailDetails() != null
					&& scannedMailDetailsVO.getMailDetails().size() > 0) {
				for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {
					if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
							&& scannedMailDetailsVO.getMailDetails().size() > 0
							&& scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus() != null
							&& (scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
									.equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_DAMAGED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)
									|| scannedMailDetailsVO.getMailDetails().iterator().next().getLatestStatus()
											.equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED))) {
						scannedMailDetailsVForArrival.setCarrierCode(mailBagVO.getCarrierCode());
						scannedMailDetailsVForArrival.setLegSerialNumber(mailBagVO.getLegSerialNumber());
						scannedMailDetailsVForArrival.setContainerNumber(mailBagVO.getContainerNumber());
						scannedMailDetailsVForArrival.setContainerType(mailBagVO.getContainerType());
						scannedMailDetailsVForArrival.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
						scannedMailDetailsVForArrival.setPou(mailBagVO.getPou());
						scannedMailDetailsVForArrival.setPol(mailBagVO.getPol());
						scannedMailDetailsVForArrival
								.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_TRANSFER);
						scannedMailDetailsVForArrival.setFlightNumber(mailBagVO.getFlightNumber());
						scannedMailDetailsVForArrival.setFlightSequenceNumber(mailBagVO.getFlightSequenceNumber());
						scannedMailDetailsVForArrival.setSegmentSerialNumber(mailBagVO.getSegmentSerialNumber());
						scannedMailDetailsVForArrival.setCarrierId(mailBagVO.getCarrierId());
						scannedMailDetailsVForArrival.setFlightDate(mailBagVO.getFlightDate());
						updateVOForArrival(scannedMailDetailsVForArrival, logonAttributes);
						if (scannedMailDetailsVForArrival != null) {
							scannedMailDetailsVForArrival.getMailDetails().iterator().next()
									.setAutoArriveMail(MailConstantsVO.FLAG_YES);
						}
						updateVOForAutoArrival(scannedMailDetailsVForArrival);
						saveArrivalFromUpload(scannedMailDetailsVForArrival, logonAttributes);
						if (MailConstantsVO.CONTAINER_STATUS_TRANSFER
								.equals(scannedMailDetailsVO.getContainerProcessPoint())) {
							scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setArrivedFlag(MailConstantsVO.FLAG_YES);
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setScannedPort(scannedMailDetailsVO.getAirportCode());
						} else {
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setArrivedFlag(MailConstantsVO.FLAG_YES);
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setLatestStatus(MailConstantsVO.MAIL_STATUS_ARRIVED);
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setScannedPort(scannedMailDetailsVO.getAirportCode());
						}
						ZonedDateTime scanDate = scannedMailDetailsVO.getMailDetails().iterator().next()
								.getScannedDate();
						scanDate.plusSeconds(01);
						scannedMailDetailsVO.getMailDetails().iterator().next().setScannedDate(scanDate);
					} else {
						if (scannedMailDetailsVO.getCarrierCode() == null
								|| scannedMailDetailsVO.getCarrierCode().equals(""))
							scannedMailDetailsVO.setCarrierCode(mailBagVO.getCarrierCode());
						if (scannedMailDetailsVO.getLegSerialNumber() == 0)
							scannedMailDetailsVO.setLegSerialNumber(mailBagVO.getLegSerialNumber());
						if (scannedMailDetailsVO.getContainerNumber() == null
								|| scannedMailDetailsVO.getContainerNumber().equals(""))
							scannedMailDetailsVO.setContainerNumber(mailBagVO.getContainerNumber());
						if (scannedMailDetailsVO.getContainerType() == null
								|| scannedMailDetailsVO.getContainerType().equals(""))
							scannedMailDetailsVO.setContainerType(mailBagVO.getContainerType());
						saveArrivalFromUpload(scannedMailDetailsVO, logonAttributes);
					}
				}
			}
		}
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
				reassignOnDestinationChange(scannedMailDetailsVO, logonAttributes);
			}
			if ("".equals(scannedMailDetailsVO.getCarrierCode())) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
						MailHHTBusniessException.INVALID_FLIGHT, scannedMailDetailsVO);
			}
			if (scannedMailDetailsVO.getMailDetails() != null) {
				if (scannedMailDetailsVO.getMailDetails().size() > 0) {
					for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
						paCode = scannedMailbagVO.getPaCode() != null
								&& scannedMailbagVO.getPaCode().trim().length() > 0 ? scannedMailbagVO.getPaCode()
										: mailController.findPAForOfficeOfExchange(
												scannedMailDetailsVO.getCompanyCode(), scannedMailbagVO.getOoe());
						orginPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
								scannedMailbagVO.getOoe());
						destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
								scannedMailbagVO.getDoe());
						if (scannedMailbagVO.getOrigin() == null) {
							scannedMailbagVO.setOrigin(orginPort);
						}
						if (isCoterminusConfigured != null && isCoterminusConfigured.equals("Y")
								&& checkReceivedFromTruckEnabled(scannedMailDetailsVO.getAirportCode(),
										scannedMailbagVO.getOrigin(), paCode)) {
							MailbagVO mailbagvo = new MailbagVO();
							mailbagvo = constructDAO().findTransferFromInfoFromCarditForMailbags(scannedMailbagVO);
							if ((scannedMailDetailsVO.getTransferFrmFlightNum() == null
									|| scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() == 0)) {
								if (mailbagvo != null
										&& scannedMailDetailsVO.getAirportCode().equals(mailbagvo.getPou())) {
									scannedMailDetailsVO.setTransferFrmFlightDate(mailbagvo.getFromFlightDate());
									scannedMailDetailsVO.setTransferFrmFlightNum(mailbagvo.getFromFightNumber());
									scannedMailDetailsVO.setTransferFromCarrier(mailbagvo.getTransferFromCarrier());
								}
							}
							if (scannedMailDetailsVO.getTransferFrmFlightNum() != null
									&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0
									&& scannedMailDetailsVO.getTransferFrmFlightDate() != null
									&& scannedMailDetailsVO.getAirportCode().equals(mailbagvo.getPou())
									|| scannedMailDetailsVO.getTransferFrmFlightNum() != null
											&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0
											&& scannedMailDetailsVO.getTransferFrmFlightDate() != null
											&& !(orginPort.equals(scannedMailDetailsVO.getAirportCode())
													&& scannedMailDetailsVO.getAirportCode().equals(destinationPort))) {
								flightDetailsVOs = validateTransferFlightandUpdateVO(scannedMailDetailsVO,
										logonAttributes, scannedMailbagVO.getScannedPort());
								scannedMailDetailsVO.setTransferFrmFlightSeqNum(
										flightDetailsVOs.iterator().next().getFlightSequenceNumber());
								scannedMailDetailsVO.setTransferFrmFlightLegSerialNumber(
										flightDetailsVOs.iterator().next().getLegSerialNumber());
								try {
									flightSegments = flightOperationsProxy.findFlightSegments(
											scannedMailDetailsVO.getCompanyCode(),
											scannedMailDetailsVO.getTransferFrmCarrierId(),
											scannedMailDetailsVO.getTransferFrmFlightNum(),
											scannedMailDetailsVO.getTransferFrmFlightSeqNum());
								} finally {
								}
								ContainerDetailsVO containerVO = new ContainerDetailsVO();
								if (scannedMailDetailsVO.getMailDetails() != null) {
									for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
										mailbagVO.setFlightDate((flightDetailsVOs.iterator().next().getSta().toZonedDateTime()));
										mailbagVO.setFlightNumber(scannedMailDetailsVO.getTransferFrmFlightNum());
										mailbagVO.setFlightSequenceNumber(
												scannedMailDetailsVO.getTransferFrmFlightSeqNum());
										mailbagVO.setLegSerialNumber(
												scannedMailDetailsVO.getTransferFrmFlightLegSerialNumber());
										mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
										mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
										mailbagVO.setPou(flightSegments.iterator().next().getSegmentDestination());
										containerVO.setCarrierCode(mailbagVO.getCarrierCode());
										containerVO.setCarrierId(mailbagVO.getCarrierId());
										containerVO.setContainerNumber(mailbagVO.getContainerNumber());
										containerVO.setCompanyCode(mailbagVO.getCompanyCode());
										containerVO.setFlightNumber(mailbagVO.getFlightNumber());
										containerVO.setFlightDate(mailbagVO.getFlightDate());
										containerVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
										containerVO.setSegmentSerialNumber(
												flightSegments.iterator().next().getSegmentSerialNumber());
										containerVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
										if (mailbagVO.getPou() != null) {
											containerVO.setPou(mailbagVO.getPou());
										}
									}
									scannedMailDetailsVO.setValidatedContainer(containerVO);
								}
								saveArrivalFromAndroid(scannedMailDetailsVO, logonAttributes);
								flightDetailsVOs = null;
								if (!"-1".equals(scannedMailDetailsVO.getFlightNumber())
										&& scannedMailDetailsVO.getFlightNumber() != null
										&& scannedMailDetailsVO.getFlightNumber().trim().length() > 0) {
									FlightFilterVO flightFilterVOfortransfer = new FlightFilterVO();
									flightFilterVOfortransfer.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
									flightFilterVOfortransfer.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
									flightFilterVOfortransfer.setFlightCarrierId(scannedMailDetailsVO.getCarrierId());
									flightFilterVOfortransfer
											.setAirportCode(flightSegments.iterator().next().getSegmentDestination());
									flightFilterVOfortransfer.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
									flightFilterVOfortransfer.setFlightDate(LocalDateMapper.toLocalDate(scannedMailDetailsVO.getFlightDate()));
									flightFilterVOfortransfer
											.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
									flightDetailsVOs = mailController.validateFlight(flightFilterVOfortransfer);
								} else {
									scannedMailDetailsVO.getValidatedContainer().setLegSerialNumber(-1);
									scannedMailDetailsVO.getValidatedContainer()
											.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
								}
								if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
									scannedMailDetailsVO.setFlightSequenceNumber(
											flightDetailsVOs.iterator().next().getFlightSequenceNumber());
									scannedMailDetailsVO.setLegSerialNumber(
											flightDetailsVOs.iterator().next().getLegSerialNumber());
									scannedMailDetailsVO.getValidatedContainer().setLegSerialNumber(
											flightDetailsVOs.iterator().next().getLegSerialNumber());
									scannedMailDetailsVO.getValidatedContainer()
											.setCarrierCode(flightDetailsVOs.iterator().next().getCarrierCode());
								}
								scannedMailDetailsVO
										.setAirportCode(flightSegments.iterator().next().getSegmentDestination());
								if (scannedMailDetailsVO.getPou() == null) {
									scannedMailDetailsVO.setPou(scannedMailDetailsVO.getDestination());
								}
								scannedMailDetailsVO.setPol(flightSegments.iterator().next().getSegmentDestination());
								scannedMailDetailsVO.getValidatedContainer()
										.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
								scannedMailDetailsVO.getValidatedContainer()
										.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
								scannedMailDetailsVO.getValidatedContainer()
										.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
								scannedMailDetailsVO.getValidatedContainer()
										.setDestination(scannedMailDetailsVO.getPou());
								scannedMailDetailsVO.getValidatedContainer().setPou(scannedMailDetailsVO.getPou());
								scannedMailDetailsVO.getValidatedContainer().setPol(scannedMailDetailsVO.getPol());
								scannedMailDetailsVO.getValidatedContainer()
										.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
								scannedMailDetailsVO
										.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
								scannedMailDetailsVO.setTransferFrmFlightDate(null);
								scannedMailDetailsVO.setTransferFrmFlightNum(null);
								performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
								scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
								for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
									mailbagVO.setIsFromTruck("Y");
									mailbagVO.setScannedPort(scannedMailDetailsVO.getAirportCode());
								}
								saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
								flagResditForAcceptanceInTruck(scannedMailDetailsVO);
							} else {
								saveAcceptanceFromAndroid(scannedMailDetailsVO, logonAttributes);
							}
						} else if ("Y".equals(scannedMailDetailsVO.getOverrideValidations())) {
							saveAcceptanceFromAndroid(scannedMailDetailsVO, logonAttributes);
						} else {
							saveAcceptanceFromAndroid(scannedMailDetailsVO, logonAttributes);
						}
					}
				} else {
					saveAcceptanceFromAndroid(scannedMailDetailsVO, logonAttributes);
				}
			}
		} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveOffloadFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			boolean checkForAutoarrival = checkAutoArrival(scannedMailDetailsVO);
			if (checkForAutoarrival) {
				if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0)
					scannedMailDetailsVO.getMailDetails().iterator().next().setAutoArriveMail(MailConstantsVO.FLAG_YES);
			}
			saveArrivalFromAndroid(scannedMailDetailsVO, logonAttributes);
		} else if (sysparfunctionpoints != null && !(sysparfunctionpoints.equals(FUNPNTS_DLV))
				&& MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveDeliverFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveDamageCaptureFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveReturnFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (scannedMailDetailsVO.getContainerNumber() != null
					&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
				performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
			}
			saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
			savereassignFromAndroid(scannedMailDetailsVO, logonAttributes);
		} else {
			constructAndRaiseException(MailMLDBusniessException.INVALID_TRANSACTION_EXCEPTION,
					MailHHTBusniessException.INVALID_TRANSACTION_EXCEPTION, scannedMailDetailsVO);
		}
	}

	private void initializeVOforAndroid(ScannedMailDetailsVO scannedMailDetailsVO, MailUploadVO uploadedMaibagVO,
			LoginProfile logonAttributes) {
		final String UNSAVED = "U";
		scannedMailDetailsVO.setAndroidFlag(uploadedMaibagVO.getAndroidFlag());
		scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		scannedMailDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		scannedMailDetailsVO.setStatus(UNSAVED);
		scannedMailDetailsVO.setHasErrors(false);
		scannedMailDetailsVO.setAirportCode(uploadedMaibagVO.getScannedPort());
		scannedMailDetailsVO.setMailSource(uploadedMaibagVO.getMailSource());
		if (MailConstantsVO.CONTAINER_STATUS_ARRIVAL.equals(uploadedMaibagVO.getContaineDescription())) {
			scannedMailDetailsVO.setContainerArrivalFlag(MailConstantsVO.FLAG_YES);
		} else if (MailConstantsVO.CONTAINER_STATUS_DELIVERY.equals(uploadedMaibagVO.getContaineDescription())) {
			scannedMailDetailsVO.setContainerDeliveryFlag(MailConstantsVO.FLAG_YES);
		}
		if (uploadedMaibagVO.getScanType() != null) {
			scannedMailDetailsVO.setProcessPoint(uploadedMaibagVO.getScanType());
		}
		if (uploadedMaibagVO.isExpRsn()) {
			scannedMailDetailsVO.setExpReassign(true);
		}
		scannedMailDetailsVO.setFlightValidationVOS(uploadedMaibagVO.getFlightValidationVOS());
		if (uploadedMaibagVO.getProcessPointBeforeArrival() != null
				&& uploadedMaibagVO.getProcessPointBeforeArrival().trim().length() > 0) {
			scannedMailDetailsVO.setProcessPointBeforeArrival(uploadedMaibagVO.getProcessPointBeforeArrival());
		}
	}

	private void UpdateContainerProcessPointforAndroid(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerVO containerVo, String scanningPort) {
		boolean isReassignforEXP = false;
		LoginProfile logonAttributes = null;
		String systemPar = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		try {
			systemPar = findSystemParameterValue(ULD_SYSPAR_TRANFERWITHMAILBAGS);
		} finally {
		}
		String airportCode = ((scannedMailDetailsVO.getMailSource() != null
				&& scannedMailDetailsVO.getMailSource().startsWith(MailConstantsVO.SCAN))
				|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource()))
						? scannedMailDetailsVO.getAirportCode()
						: logonAttributes.getAirportCode();
		if (!containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
				|| containerAssignmentVO.getFlightSequenceNumber() != scannedMailDetailsVO.getFlightSequenceNumber()
				|| containerAssignmentVO.getCarrierId() != scannedMailDetailsVO.getCarrierId()
				|| (containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
						&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
								.getFlightSequenceNumber()
						&& containerAssignmentVO.getSegmentSerialNumber() != scannedMailDetailsVO
								.getSegmentSerialNumber())) {
			if (((MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
					&& containerAssignmentVO.getFlightSequenceNumber() > 0
					&& containerAssignmentVO.getTransitFlag() != null
					&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())))
					|| MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
							&& !containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode())) {
				scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
				for (MailbagVO mailBagVo : scannedMailDetailsVO.getMailDetails()) {
					if (mailBagVo.getPaBuiltFlag() != null && mailBagVo.getPaBuiltFlag().trim().length() != 0
							&& mailBagVo.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
						scannedMailDetailsVO.setNewContainer(MailConstantsVO.FLAG_YES);
					}
				}
			} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
					&& (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())
							|| MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getArrivalFlag()))
					&& containerAssignmentVO.getAirportCode().equals(scannedMailDetailsVO.getAirportCode())) {
				scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
			} else if (containerAssignmentVO.getAirportCode().equals(airportCode)
					&& (containerAssignmentVO.getArrivalFlag() != null
							&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
					&& !MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())) {
				Collection<ContainerVO> containerVOsToReassign = new ArrayList<ContainerVO>();
				ContainerVO containerVO = new ContainerVO();
				containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
				containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
				containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
				containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
				containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
				containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				containerVO.setUldFulIndFlag(containerAssignmentVO.getUldFulIndFlag());
				containerVO.setUldReferenceNo(containerAssignmentVO.getUldReferenceNo());
				if (scannedMailDetailsVO.getPou() != null && scannedMailDetailsVO.getPou().trim().length() > 0) {
					containerVO.setPou(scannedMailDetailsVO.getPou());
				} else {
					containerVO.setPou(containerAssignmentVO.getPou());
				}
				containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
				if (MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())
						&& containerAssignmentVO.getFlightSequenceNumber() <= 0) {
					containerVO.setFinalDestination(containerAssignmentVO.getDestination());
				}
				if ("WS".equals(scannedMailDetailsVO.getMailSource())) {
					if ((MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint()))
							&& containerVO.getFinalDestination() != null) {
						containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
					}
				}
				getMailUploadInstance().setScreeningUserForDnataSpecific(scannedMailDetailsVO, containerVO);
				containerVO.setAssignedPort(airportCode);
				for (MailbagVO mailbagVo : scannedMailDetailsVO.getMailDetails()) {
					if (mailbagVo != null && mailbagVo.getScannedUser() != null
							&& mailbagVo.getScannedUser().trim().length() > 0) {
						containerVO.setAssignedUser(mailbagVo.getScannedUser());
					} else {
						containerVO.setAssignedUser(logonAttributes.getUserId());
					}
					if (mailbagVo != null && mailbagVo.getScannedDate() != null) {
						containerVO.setScannedDate(mailbagVo.getScannedDate());
					}
				}
				containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
				containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				containerVO.setType(containerAssignmentVO.getContainerType());
				containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
				containerVO.setTypeCodeListResponsibleAgency(scannedMailDetailsVO.getMailSource());
				containerVO.setMailSource(scannedMailDetailsVO.getMailSource());
				containerVOsToReassign.add(containerVO);
				scannedMailDetailsVO.setScannedContainerDetails(containerVOsToReassign);
				if (!scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED))
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_REASSIGN);
			} else if ((containerAssignmentVO.getArrivalFlag() != null
					&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
					&& containerAssignmentVO.getPou() != null
					&& containerAssignmentVO.getPou().equals(scannedMailDetailsVO.getAirportCode())
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& !MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())
					&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())
							|| MailConstantsVO.FLAG_YES.equalsIgnoreCase(systemPar))) {
				Collection<ContainerVO> containerVOsToTransfer = new ArrayList<ContainerVO>();
				ContainerVO containerVO = new ContainerVO();
				containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
				containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
				containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
				containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
				containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
				containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				containerVO.setPou(containerAssignmentVO.getPou());
				containerVO.setFinalDestination(containerAssignmentVO.getDestination());
				containerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
				containerVO.setAssignedUser(logonAttributes.getUserId());
				containerVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
				for (MailbagVO mailbagVo : scannedMailDetailsVO.getMailDetails()) {
					if (mailbagVo != null && mailbagVo.getScannedUser() != null
							&& mailbagVo.getScannedUser().trim().length() > 0) {
						containerVO.setAssignedUser(mailbagVo.getScannedUser());
					} else {
						containerVO.setAssignedUser(logonAttributes.getUserId());
					}
					if (mailbagVo != null && mailbagVo.getScannedDate() != null) {
						containerVO.setScannedDate(mailbagVo.getScannedDate());
					}
				}
				containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
				containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				containerVO.setType(containerAssignmentVO.getContainerType());
				containerVO.setAcceptanceFlag("Y");
				containerVOsToTransfer.add(containerVO);
				scannedMailDetailsVO.setScannedContainerDetails(containerVOsToTransfer);
				if ((containerAssignmentVO.getArrivalFlag() != null
						&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
						&& !containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
						|| (("-1").equals(scannedMailDetailsVO.getFlightNumber())
								&& (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_NO
										.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
								&& !containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
								&& !(containerAssignmentVO.getDestination()
										.equals(scannedMailDetailsVO.getDestination())))) {
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_TRANSFER);
					if (scannedMailDetailsVO.getPou() != null) {
						containerVo.setPou(scannedMailDetailsVO.getPou());
					}
					if (containerAssignmentVO.getActualWeight() != null) {
						containerVO.setActualWeight(containerAssignmentVO.getActualWeight());
					}
					if (containerAssignmentVO.getTransitFlag() != null) {
						containerVO.setTransitFlag(containerAssignmentVO.getTransitFlag());
					}
					if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())) {
						containerVO.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
					}
					containerVo.setFinalDestination(scannedMailDetailsVO.getDestination());
					containerVO.setMailSource(scannedMailDetailsVO.getMailSource());
					containerVO.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
				} else if ((containerAssignmentVO.getArrivalFlag() != null
						&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
						&& !containerAssignmentVO.getPou().equalsIgnoreCase(scanningPort)) {
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
				}
			}
			if ((containerAssignmentVO.getFlightNumber() != null
					&& containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber()))
					&& (MailConstantsVO.DESTN_FLT == containerAssignmentVO.getFlightSequenceNumber())
					&& ("ACP".equals(scannedMailDetailsVO.getProcessPoint()))
					&& containerAssignmentVO.getAirportCode().equals(airportCode)
					&& scannedMailDetailsVO.isExpReassign()) {
				isReassignforEXP = true;
				Collection<ContainerVO> containerVOsToReassign = new ArrayList<ContainerVO>();
				ContainerVO containerVO = new ContainerVO();
				containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
				containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
				containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
				containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
				containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
				containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
				containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				containerVO.setPou(scannedMailDetailsVO.getDestination());
				containerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
				containerVO.setAssignedPort(airportCode);
				for (MailbagVO mailbagVOToSave : scannedMailDetailsVO.getMailDetails()) {
					if (mailbagVOToSave != null && mailbagVOToSave.getScannedUser() != null
							&& mailbagVOToSave.getScannedUser().trim().length() > 0) {
						containerVO.setAssignedUser(mailbagVOToSave.getScannedUser());
					} else {
						containerVO.setAssignedUser(logonAttributes.getUserId());
					}
					if (mailbagVOToSave != null && mailbagVOToSave.getScannedDate() != null) {
						containerVO.setScannedDate(mailbagVOToSave.getScannedDate());
					}
				}
				containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
				containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				containerVO.setType(containerAssignmentVO.getContainerType());
				containerVO.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
				containerVO.setTypeCodeListResponsibleAgency(scannedMailDetailsVO.getMailSource());
				containerVOsToReassign.add(containerVO);
				scannedMailDetailsVO.setScannedContainerDetails(containerVOsToReassign);
				scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_REASSIGN);
			}
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param uploadedMaibagVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private void validateULDFormat(ScannedMailDetailsVO scannedMailDetailsVO, MailUploadVO uploadedMaibagVO,
			LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if ((!MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()))) {
			if ((!MailConstantsVO.BULK_TYPE.equals(uploadedMaibagVO.getContainerType()))) {
				new MailOperationsValidator().doULDValidations(scannedMailDetailsVO, logonAttributes,
						uploadedMaibagVO);
			}
		} else {
			Collection<String> systemParameterCodes = new ArrayList<String>();
			systemParameterCodes.add(ULD_AS_BARROW);
			Map<String, String> mailIDFormatMap = null;

				try {
					mailIDFormatMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameterCodes);
				} catch (BusinessException e) {
					throw new RuntimeException(e);
				}

			if ((MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()))) {
				if (mailIDFormatMap != null && !FLAG_YES.equals(mailIDFormatMap.get(ULD_AS_BARROW))) {
					try {
						if (new MailOperationsValidator().validateContainerNumber(scannedMailDetailsVO)) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_BULK_FORMAT,
									"Bulk format is invalid", scannedMailDetailsVO);
							uploadedMaibagVO.setContainerType(MailConstantsVO.BULK_TYPE);
						}
					} finally {
					}
				}
			}
		}
	}

	public void setScreeningDetails(MailUploadVO uploadedMaibagVO, ScannedMailDetailsVO scannedMailDetailsVO,
			String scannedPort) {
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
		if (uploadedMaibagVO.getSecurityMethods() != null && uploadedMaibagVO.getSecurityMethods().trim().length() > 0
				&& !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			String[] securityMethodArr = uploadedMaibagVO.getSecurityMethods().split(",");
			ZonedDateTime localDate = localDateUtil.getLocalDate(null, false);
			for (String securityMethod : securityMethodArr) {
				scannedMailDetailsVO.setScreeningPresent(true);
				ConsignmentScreeningVO consignmentScreeningVo = new ConsignmentScreeningVO();
				consignmentScreeningVo.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
				consignmentScreeningVo.setScreeningLocation(scannedPort);
				consignmentScreeningVo.setSecurityStatusParty(uploadedMaibagVO.getIssuedBy());
				consignmentScreeningVo.setResult(MailConstantsVO.RESULT);
				consignmentScreeningVo.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_SCREENING);
				ZonedDateTime securityStatusDate = localDateUtil.getLocalDate(null, true);
				consignmentScreeningVo.setSecurityStatusDate(
						LocalDate.withDate(securityStatusDate,String.valueOf(uploadedMaibagVO.getDateTime())));
				consignmentScreeningVo.setScreeningMethodCode(securityMethod);
				consignmentScreeningVo.setSource(MailConstantsVO.SOURCE_HHT);
				consignmentScreeningVo.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
				consignmentScreeningVos.add(consignmentScreeningVo);
			}
		}
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVos);
	}

	/**
	* @param uploadedMaibagVO
	* @param scannedMailDetailsVO
	* @param scannedPort
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	public void setScanInformationForAndroid(MailUploadVO uploadedMaibagVO, ScannedMailDetailsVO scannedMailDetailsVO,
			String scannedPort) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		Collection<MailbagVO> mailBagVos = scannedMailDetailsVO.getMailDetails();
		if (uploadedMaibagVO.getDeviceDateAndTime() != null) {
			scannedMailDetailsVO.setDeviceDateAndTime(uploadedMaibagVO.getDeviceDateAndTime());
		}
		for (MailbagVO mailbagVo : mailBagVos) {
			mailbagVo.setScannedPort(scannedPort);
			if (uploadedMaibagVO.getDateTime() != null && uploadedMaibagVO.getDateTime().trim().length() > 0) {
				ZonedDateTime scanDate = null;
				if (scannedPort != null) {
					scanDate = localDateUtil.getLocalDate(scannedPort, true);
				} else {
					scanDate = localDateUtil.getLocalDate(null, false);
				}
				scanDate = LocalDate.withDateAndTime(scanDate, uploadedMaibagVO.getDateTime());
				mailbagVo.setScannedDate(scanDate);
				if (uploadedMaibagVO.getScanUser() != null) {
					mailbagVo.setScannedUser(uploadedMaibagVO.getScanUser());
				} else {
					mailbagVo.setScannedUser(logonAttributes.getUserId());
				}
				scannedMailDetailsVO.setOperationTime(scanDate);
			}
			updateInboundFlightDetails(logonAttributes, mailbagVo, uploadedMaibagVO, scannedMailDetailsVO);
			if ("WS".equals(uploadedMaibagVO.getMailSource())) {
				mailbagVo.setMailRemarks(uploadedMaibagVO.getRemarks());
			}
		}
		if (mailBagVos != null && mailBagVos.isEmpty()) {
			ZonedDateTime scanDate = null;
			if (scannedPort != null) {
				scanDate = localDateUtil.getLocalDate(scannedPort, true);
			} else {
				scanDate = localDateUtil.getLocalDate(null, false);
			}
			scannedMailDetailsVO.setOperationTime(scanDate);
		}
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType())
				&& (uploadedMaibagVO.getFromCarrierCode() != null && !uploadedMaibagVO.getFromCarrierCode().isEmpty())
				&& (uploadedMaibagVO.getMailTag() == null || uploadedMaibagVO.getMailTag().isEmpty())) {
			scannedMailDetailsVO.setTransferFromCarrier(uploadedMaibagVO.getFromCarrierCode());
		}
		scannedMailDetailsVO.setRemarks(uploadedMaibagVO.getRemarks());
		scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
	}

	public void setMailbagDetailsForAndroid(MailbagVO mailbagVOToSave, MailUploadVO uploadedMaibagVO,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws FinderException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		Page<OfficeOfExchangeVO> origin = null;
		Page<OfficeOfExchangeVO> destination = null;
		OfficeOfExchangeVO originOfficeOfExchangeVO = new OfficeOfExchangeVO();
		OfficeOfExchangeVO destinationOfficeOfExchangeVO = new OfficeOfExchangeVO();
		boolean isValidMailtag = false;
		Mailbag mailbag = null;
		String defDestForCdtMissingMailbag = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		Map<String, String> systemParameterMap;
		Map<String, Object> transactionMap = new HashMap<>();
		systemParameters.add(DEST_FOR_CDT_MISSING_DOM_MAL);
		systemParameters.add(MailConstantsVO.USPS_INTERNATIONAL_PA);
		systemParameters.add(USPS_DOMESTIC_PA);
		systemParameters.add(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		try {
			systemParameterMap = sharedDefaultsProxy.findSystemParameterByCodes(systemParameters);
			if (systemParameterMap != null) {
				defDestForCdtMissingMailbag = systemParameterMap.get(DEST_FOR_CDT_MISSING_DOM_MAL);
			}
		} finally {
		}
		defDestForCdtMissingMailbag = findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		transactionMap.put(DEST_FOR_CDT_MISSING_DOM_MAL, defDestForCdtMissingMailbag);
		transactionMap.put(MailConstantsVO.USPS_INTERNATIONAL_PA, systemParameterMap.get(MailConstantsVO.USPS_INTERNATIONAL_PA));
		transactionMap.put(MailConstantsVO.USPS_DOMESTIC_PA, systemParameterMap.get(MailConstantsVO.USPS_DOMESTIC_PA));
		transactionMap.put(NO_OF_CHAR_ALLOWED_FOR_MAILTAG, systemParameterMap.get(NO_OF_CHAR_ALLOWED_FOR_MAILTAG));
		contextUtil.setTxContext(transactionMap);
		if (uploadedMaibagVO.getMailTag() != null && !uploadedMaibagVO.getMailTag().isEmpty()) {
			isValidMailtag = isValidMailtag(uploadedMaibagVO.getMailTag().length());
		}
		if ((uploadedMaibagVO.getMailTag() != null && !uploadedMaibagVO.getMailTag().isEmpty()
				&& uploadedMaibagVO.getMailTag().length() == 29) || isValidMailtag) {
			mailbag = findExistingMailBag(uploadedMaibagVO);
		}
		if (mailbag == null || MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) {
			mailbagVOToSave.setLatValidationNeeded(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setNotAccepted(true);
			scannedMailDetailsVO.setMailbagValidationRequired(true);
		}
		mailbagVOToSave.setStorageUnit(uploadedMaibagVO.getStorageUnit());
		if (uploadedMaibagVO.getMailTag() != null && isValidMailtag) {
			if (mailbag == null) {
				if (uploadedMaibagVO.getMailTag().length() == 12) {
					if ("Y".equals(uploadedMaibagVO.getResolveFlagAndroid()) && uploadedMaibagVO.getOrgin() != null
							&& uploadedMaibagVO.getDestination() != null) {
						mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
						mailbagVOToSave.setMailbagId(uploadedMaibagVO.getMailTag());
						mailbagVOToSave = constructNewMailbagId(uploadedMaibagVO, mailbagVOToSave);
					} else {
						String routIndex = uploadedMaibagVO.getMailTag().substring(4, 8);
						Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
						RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
						routingIndexFilterVO.setCompanyCode(uploadedMaibagVO.getCompanyCode());
						routingIndexFilterVO.setRoutingIndex(routIndex);
						routingIndexFilterVO.setScannedDate(uploadedMaibagVO.getScannedDate());
						routingIndexVOs = findRoutingIndex(routingIndexFilterVO);
						if (routingIndexVOs.size() == 0) {
							if (defDestForCdtMissingMailbag != null && !"NA".equals(defDestForCdtMissingMailbag)) {
								RoutingIndexVO routingIndexVO = new RoutingIndexVO();
								routingIndexVO.setRoutingIndex("XXXX");
								if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(uploadedMaibagVO.getScanType())) {
									if (uploadedMaibagVO.getFlightNumber() != null
											&& uploadedMaibagVO.getFlightNumber().trim().length() > 0
											&& uploadedMaibagVO.getContainerPol() != null
											&& uploadedMaibagVO.getContainerPol().trim().length() > 0) {
										mailbagVOToSave.setOrigin(uploadedMaibagVO.getContainerPol());
										mailbagVOToSave.setDestination(defDestForCdtMissingMailbag);
									} else {
										mailbagVOToSave.setOrigin(defDestForCdtMissingMailbag);
									}
									if (uploadedMaibagVO.isDeliverd()) {
										mailbagVOToSave.setDestination(uploadedMaibagVO.getScannedPort());
									}
									routingIndexVO.setOrigin(mailbagVOToSave.getOrigin());
									routingIndexVO.setDestination(mailbagVOToSave.getDestination());
								} else {
									mailbagVOToSave.setOrigin(uploadedMaibagVO.getScannedPort());
									mailbagVOToSave.setDestination(defDestForCdtMissingMailbag);
									routingIndexVO.setOrigin(uploadedMaibagVO.getScannedPort());
									routingIndexVO.setDestination(defDestForCdtMissingMailbag);
								}
								routingIndexVOs.add(routingIndexVO);
							} /*else {
								if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(uploadedMaibagVO.getScanType())
										&& uploadedMaibagVO.isDeliverd()) {
									throw new MailHHTBusniessException(
											MailHHTBusniessException.PLAN_ROUTE_MISSING_FOR_DLV,
											MailHHTBusniessException.PLAN_ROUTE_MISSING);
								}
								throw new MailHHTBusniessException(MailHHTBusniessException.PLAN_ROUTE_MISSING,
										MailHHTBusniessException.PLAN_ROUTE_MISSING);
							}*/
						}
						mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
						mailbagVOToSave.setMailbagId(uploadedMaibagVO.getMailTag());
						mailbagVOToSave = constructRouteIndexMailbagID(routingIndexVOs, mailbagVOToSave);
					}
					mailbagVOToSave.setMailSource(uploadedMaibagVO.getMailSource());
					mailbagVOToSave.setAcceptedBags(uploadedMaibagVO.getBags());
					if (uploadedMaibagVO.getScannedDate() == null) {
						mailbagVOToSave
								.setScannedDate(localDateUtil.getLocalDate(uploadedMaibagVO.getScannedPort(), true));
					} else {
						mailbagVOToSave.setScannedDate(uploadedMaibagVO.getScannedDate());
					}
					mailbagVOToSave.setMailCompanyCode(uploadedMaibagVO.getMailCompanyCode());
					mailbagVOToSave.setPol(uploadedMaibagVO.getContainerPol());
					mailbagVOToSave.setPou(uploadedMaibagVO.getToDestination());
					mailbagVOToSave.setContainerNumber(uploadedMaibagVO.getContainerNumber());
					mailbagVOToSave.setContainerType(uploadedMaibagVO.getContainerType());
					mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
					mailbagVOToSave.setFromCarrier(uploadedMaibagVO.getFromCarrierCode());
					String despatchid = mailbagVOToSave.getOoe() + mailbagVOToSave.getDoe()
							+ mailbagVOToSave.getMailCategoryCode() + mailbagVOToSave.getMailSubclass()
							+ mailbagVOToSave.getYear() + mailbagVOToSave.getDespatchSerialNumber();
					mailbagVOToSave.setDespatchId(despatchid);
					mailbagVOToSave.setPaCode(findSystemParameterValue(USPS_DOMESTIC_PA));
				}
			} else {
				mailbagVOToSave.setMailbagId(mailbag.getMailIdr());
				mailbagVOToSave.setMailSource(uploadedMaibagVO.getMailSource());
				mailbagVOToSave.setYear(mailbag.getYear());
				mailbagVOToSave.setOoe(mailbag.getOrginOfficeOfExchange());
				mailbagVOToSave.setDoe(mailbag.getDestinationOfficeOfExchange());
				mailbagVOToSave.setMailCategoryCode(mailbag.getMailCategory());
				mailbagVOToSave.setMailSubclass(mailbag.getMailSubClass());
				mailbagVOToSave.setDespatchSerialNumber(mailbag.getDespatchSerialNumber());
				mailbagVOToSave.setReceptacleSerialNumber(mailbag.getReceptacleSerialNumber());
				mailbagVOToSave.setRegisteredOrInsuredIndicator(mailbag.getRegisteredOrInsuredIndicator());
				mailbagVOToSave.setHighestNumberedReceptacle(mailbag.getHighestNumberedReceptacle());
				mailbagVOToSave.setWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(mailbag.getWeight())));
				mailbagVOToSave.setAcceptedBags(uploadedMaibagVO.getBags());
				if (uploadedMaibagVO.getScannedDate() == null) {
					mailbagVOToSave.setScannedDate(localDateUtil.getLocalDate(mailbag.getScannedPort(), true));
				} else {
					mailbagVOToSave.setScannedDate(uploadedMaibagVO.getScannedDate());
				}
				mailbagVOToSave.setMailCompanyCode(mailbag.getMailCompanyCode());
				mailbagVOToSave.setDespatchId(mailbag.getDsnIdr());
				mailbagVOToSave.setMailSequenceNumber(mailbag.getMailSequenceNumber());
				mailbagVOToSave.setCompanyCode(mailbag.getMailCompanyCode());
				mailbagVOToSave.setPol(MailConstantsVO.MAIL_STATUS_RETURNED.equals(uploadedMaibagVO.getScanType())
						? mailbag.getScannedPort()
						: uploadedMaibagVO.getContainerPol());
				mailbagVOToSave.setPou(
						MailConstantsVO.MAIL_STATUS_RETURNED.equals(uploadedMaibagVO.getScanType()) ? mailbag.getPou()
								: uploadedMaibagVO.getToDestination());
				mailbagVOToSave.setContainerNumber(uploadedMaibagVO.getContainerNumber());
				mailbagVOToSave.setContainerType(uploadedMaibagVO.getContainerType());
				mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
				mailbagVOToSave.setMailClass(mailbag.getMailClass());
				mailbagVOToSave.setFromCarrier(uploadedMaibagVO.getFromCarrierCode());
				mailbagVOToSave.setMailClass(mailbag.getMailClass());
				mailbagVOToSave.setOrigin(mailbag.getOrigin());
				mailbagVOToSave.setDestination(mailbag.getDestination());
				mailbagVOToSave.setConsignmentDate(localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), mailbag.getOrigin()));
				if (defDestForCdtMissingMailbag != null && defDestForCdtMissingMailbag.equals(mailbag.getDestination())
						&& uploadedMaibagVO.isDeliverd()) {
					mailbagVOToSave.setDestination(uploadedMaibagVO.getScannedPort());
					mailbagVOToSave.setDoe(findOfficeOfExchangeForCarditMissingDomMail(mailbagVOToSave.getCompanyCode(),
							mailbagVOToSave.getDestination()));
					mailbagVOToSave.setNeedDestUpdOnDlv(true);
				}
				mailbagVOToSave.setPaCode(mailbag.getPaCode());
				mailbagVOToSave.setSecurityStatusCode(mailbag.getSecurityStatusCode());
				checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO, scannedMailDetailsVO, mailbag);
			}
		} else if (uploadedMaibagVO.getMailTag() != null && !uploadedMaibagVO.getMailTag().isEmpty()
				&& uploadedMaibagVO.getMailTag().length() == 29) {
			mailbagVOToSave.setMailSource(uploadedMaibagVO.getMailSource());
			mailbagVOToSave.setMailbagId(uploadedMaibagVO.getMailTag());
			mailbagVOToSave.setYear(uploadedMaibagVO.getYear());
			uploadedMaibagVO
					.setDestinationOE(uploadedMaibagVO.getDestinationOE() != null ? uploadedMaibagVO.getDestinationOE()
							: uploadedMaibagVO.getMailTag().substring(6, 12));
			uploadedMaibagVO.setOrginOE(uploadedMaibagVO.getOrginOE() != null ? uploadedMaibagVO.getOrginOE()
					: uploadedMaibagVO.getMailTag().substring(0, 6));
			uploadedMaibagVO.setCategory(uploadedMaibagVO.getCategory() != null ? uploadedMaibagVO.getCategory()
					: uploadedMaibagVO.getMailTag().substring(12, 13));
			uploadedMaibagVO.setSubClass(uploadedMaibagVO.getSubClass() != null ? uploadedMaibagVO.getSubClass()
					: uploadedMaibagVO.getMailTag().substring(13, 15));
			mailbagVOToSave.setOoe(uploadedMaibagVO.getOrginOE());
			mailbagVOToSave.setDoe(uploadedMaibagVO.getDestinationOE());
			mailbagVOToSave.setMailCategoryCode(uploadedMaibagVO.getCategory());
			mailbagVOToSave.setMailSubclass(uploadedMaibagVO.getSubClass());
			mailbagVOToSave.setDespatchSerialNumber(String.valueOf(uploadedMaibagVO.getDsn()));
			mailbagVOToSave.setReceptacleSerialNumber(uploadedMaibagVO.getRsn());
			mailbagVOToSave.setRegisteredOrInsuredIndicator(String.valueOf(uploadedMaibagVO.getRegisteredIndicator()));
			mailbagVOToSave.setHighestNumberedReceptacle(String.valueOf(uploadedMaibagVO.getHighestnumberIndicator()));
			mailbagVOToSave.setWeight(uploadedMaibagVO.getWeight());
			mailbagVOToSave.setAcceptedBags(uploadedMaibagVO.getBags());
			if (uploadedMaibagVO.getScannedDate() == null) {
				mailbagVOToSave.setScannedDate(localDateUtil.getLocalDate(uploadedMaibagVO.getScannedPort(), true));
			} else {
				mailbagVOToSave.setScannedDate(uploadedMaibagVO.getScannedDate());
			}
			mailbagVOToSave.setMailCompanyCode(uploadedMaibagVO.getMailCompanyCode());
			mailbagVOToSave.setPol(uploadedMaibagVO.getContainerPol());
			mailbagVOToSave.setPou(uploadedMaibagVO.getToDestination());
			mailbagVOToSave.setContainerNumber(uploadedMaibagVO.getContainerNumber());
			mailbagVOToSave.setContainerType(uploadedMaibagVO.getContainerType());
			mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
			mailbagVOToSave.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			if (uploadedMaibagVO.getMailTag() != null && uploadedMaibagVO.getMailTag().length() == 29) {
				mailbagVOToSave.setDespatchId(uploadedMaibagVO.getMailTag().substring(0, 20));
			}
			if (mailbag != null) {
				mailbagVOToSave.setMailSequenceNumber(mailbag.getMailSequenceNumber());
				mailbagVOToSave.setOrigin(mailbag.getOrigin());
				mailbagVOToSave.setDestination(mailbag.getDestination());
				mailbagVOToSave.setPaCode(mailbag.getPaCode());
				mailbagVOToSave.setSecurityStatusCode(mailbag.getSecurityStatusCode());
				mailbagVOToSave.setConsignmentDate(localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), mailbag.getOrigin()));
				checkForSecValRequiredAtDeliveryAndAppReqFlag(uploadedMaibagVO, scannedMailDetailsVO, mailbag);
			} else {
				try {
					if (uploadedMaibagVO.getOrginOE() != null) {
						origin = mailController.findOfficeOfExchange(uploadedMaibagVO.getCompanyCode(),
								uploadedMaibagVO.getOrginOE(), 1);
					}
				} finally {
				}
				if (origin != null && !origin.isEmpty()) {
					originOfficeOfExchangeVO = origin.iterator().next();
				} /*else if (uploadedMaibagVO.getOrginOE() != null) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
							MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
				} else {
				}*/
				if (originOfficeOfExchangeVO.getPoaCode() != null && !originOfficeOfExchangeVO.getPoaCode().isEmpty()) {
					//TODO: change to neo master service
					PostalAdministrationVO postalAdministrationVO =mailController.findPACode(uploadedMaibagVO.getCompanyCode(),
								originOfficeOfExchangeVO.getPoaCode());
					if(postalAdministrationVO!=null)
					mailbagVOToSave.setPaCode(originOfficeOfExchangeVO.getPoaCode());
				}
				LoginProfile logonAttributes = null;
				try {
					logonAttributes = contextUtil.callerLoginProfile();
				} finally {
				}
				if (originOfficeOfExchangeVO.getAirportCode() != null) {
					mailbagVOToSave.setOrigin(originOfficeOfExchangeVO.getAirportCode());
				} else {
					if (originOfficeOfExchangeVO.getAirportCode() == null) {
						Collection<ArrayList<String>> oECityArpCodes = null;
						Collection<String> originOfficeOfExchange = new ArrayList<>();
						String airportCode = null;
						originOfficeOfExchange.add(originOfficeOfExchangeVO.getCode());
						oECityArpCodes = findCityAndAirportForOE(logonAttributes.getCompanyCode(),
								originOfficeOfExchange);
						if (oECityArpCodes != null && !oECityArpCodes.isEmpty()) {
							for (ArrayList<String> cityAndArpForOE : oECityArpCodes) {
								airportCode = cityAndArpForOE.get(2);
							}
						}
						originOfficeOfExchangeVO.setAirportCode(airportCode);
					}
					if (mailbagVOToSave.getOrigin() == null || mailbagVOToSave.getOrigin().trim().length() == 0) {
						mailbagVOToSave.setOrigin(originOfficeOfExchangeVO.getAirportCode());
					}
				}
				try {
					if (uploadedMaibagVO.getDestinationOE() != null) {
						destination = mailController.findOfficeOfExchange(uploadedMaibagVO.getCompanyCode(),
								uploadedMaibagVO.getDestinationOE(), 1);
					}
				} finally {
				}
				if (destination != null && !destination.isEmpty()) {
					destinationOfficeOfExchangeVO = destination.iterator().next();
				} /*else if (uploadedMaibagVO.getDestinationOE() != null) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
							MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
				} else {
				}*/
				if (destinationOfficeOfExchangeVO.getAirportCode() != null) {
					mailbagVOToSave.setDestination(destinationOfficeOfExchangeVO.getAirportCode());
				} else {
					if (destinationOfficeOfExchangeVO.getAirportCode() == null) {
						Collection<ArrayList<String>> oECityArpCodes = null;
						Collection<String> destinationOfficeOfExchange = new ArrayList<>();
						String airportCode = null;
						destinationOfficeOfExchange.add(destinationOfficeOfExchangeVO.getCode());
						oECityArpCodes = findCityAndAirportForOE(logonAttributes.getCompanyCode(),
								destinationOfficeOfExchange);
						if (oECityArpCodes != null && !oECityArpCodes.isEmpty()) {
							for (ArrayList<String> cityAndArpForOE : oECityArpCodes) {
								airportCode = cityAndArpForOE.get(2);
							}
						}
						destinationOfficeOfExchangeVO.setAirportCode(airportCode);
					}
					if (mailbagVOToSave.getDestination() == null
							|| mailbagVOToSave.getDestination().trim().length() == 0) {
						mailbagVOToSave.setDestination(destinationOfficeOfExchangeVO.getAirportCode());
					}
				}
			}
			mailbagVOToSave.setFromCarrier(uploadedMaibagVO.getFromCarrierCode());
		} else if (uploadedMaibagVO.getMailTag() != null && !isValidMailbagID(uploadedMaibagVO.getMailTag().length())) {
			throw new MailHHTBusniessException(MailHHTBusniessException.INVALID_MAILFORAMT,
					MailHHTBusniessException.INVALID_MAILFORAMT);
		} else {
		}
		if (uploadedMaibagVO.getTransactionLevel() != null) {
			mailbagVOToSave.setTransactionLevel(uploadedMaibagVO.getTransactionLevel());
		}
	}
	public  MailbagVO constructRouteIndexMailbagID( Collection<RoutingIndexVO> routingIndexVOs,MailbagVO mailbagVO) throws SystemException {

		String org=null;
		String dest=null;

		exchangeOfficeMap=new HashMap<String,String>();
		for(RoutingIndexVO routingIndexVO:routingIndexVOs){
			if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){

				org=routingIndexVO.getOrigin();
				dest=routingIndexVO.getDestination();
				exchangeOfficeMap=findOfficeOfExchangeForPA(mailbagVO.getCompanyCode(),findSystemParameterValue(USPS_DOMESTIC_PA));
				if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
					if(exchangeOfficeMap.containsKey(org)){

						mailbagVO.setOoe(exchangeOfficeMap.get(org));
					}
					if(exchangeOfficeMap.containsKey(dest)){
						mailbagVO.setDoe(exchangeOfficeMap.get(dest));
					}
				}

					mailbagVO.setMailCategoryCode("B");
					String mailClass=mailbagVO.getMailbagId().substring(3,4);
					mailbagVO.setMailClass(mailClass);
					mailbagVO.setMailSubclass(mailClass+"X");
					mailbagVO.setOrigin(org);
					mailbagVO.setDestination(dest);

					int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
					String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
					mailbagVO.setYear(Integer.parseInt(lastDigitOfYear));
					mailbagVO.setHighestNumberedReceptacle("9");
					mailbagVO.setRegisteredOrInsuredIndicator("9");
		/*String despacthNumber=generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO);
		String rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);
    	if(rsn.length()>3){

    		generateDespatchSerialNumber(MailConstantsVO.FLAG_NO,mailbagVO);
    		despacthNumber=(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO));
    		rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);

    	}*/

					mailbagVO.setDespatchSerialNumber(DESPATCH_SERIAL_NUMBER);
					mailbagVO.setReceptacleSerialNumber(RECEPTACLE_SERIAL_NUMBER);

					mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(Double.valueOf(mailbagVO.getMailbagId().substring(10,12)))));
					mailbagVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(Double.valueOf(mailbagVO.getMailbagId().substring(10,12)))));
					mailbagVO.setStrWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(Double.valueOf(mailbagVO.getMailbagId().substring(10,12)))));
				}
			}
		}

		return mailbagVO;
	}
	/** 
	* Method		:	MailUploadController.constructNewMailbagId Added by 	:	A-7531 on 19-Nov-2018 Parameters	:	@param uploadedMaibagVO Parameters	:	@param mailbagVOToSave Parameters	:	@return  Return type	: 	MailbagVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	*/
	private MailbagVO constructNewMailbagId(MailUploadVO uploadedMaibagVO, MailbagVO mailbagVOToSave)
			throws MailHHTBusniessException {
		String org = null;
		String dest = null;
		MailbagVO newMailbagVO = new MailbagVO();
		exchangeOfficeMap = new HashMap<String, String>();
		org = uploadedMaibagVO.getOrgin().toUpperCase();
		dest = uploadedMaibagVO.getDestination().toUpperCase();
		exchangeOfficeMap = findOfficeOfExchangeForPA(uploadedMaibagVO.getCompanyCode(),
				findSystemParameterValue(USPS_DOMESTIC_PA));
		if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
			if (exchangeOfficeMap.containsKey(org)) {
				mailbagVOToSave.setOoe(exchangeOfficeMap.get(org));
			}
			if (exchangeOfficeMap.containsKey(dest)) {
				mailbagVOToSave.setDoe(exchangeOfficeMap.get(dest));
			}
		}
		/*if (mailbagVOToSave.getOoe() == null) {
			throw new MailHHTBusniessException(
					MailHHTBusniessException.OOE_NOT_CONFIGURED_FOR_GPA_AGAINST_ORG + " " + org);
		} else if (mailbagVOToSave.getDoe() == null) {
			throw new MailHHTBusniessException(
					MailHHTBusniessException.DOE_NOT_CONFIGURED_FOR_GPA_AGAINST_DEST + " " + dest);
		}*/
		mailbagVOToSave.setMailCategoryCode("B");
		String mailClass = mailbagVOToSave.getMailbagId().substring(3, 4);
		mailbagVOToSave.setMailClass(mailClass);
		mailbagVOToSave.setMailSubclass(mailClass + "X");
		mailbagVOToSave.setOrigin(org);
		mailbagVOToSave.setDestination(dest);
		int lastTwoDigits = localDateUtil.getLocalDate(null, true).getYear() % 100;
		String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1, 2);
		mailbagVOToSave.setYear(Integer.parseInt(lastDigitOfYear));
		mailbagVOToSave.setHighestNumberedReceptacle("9");
		mailbagVOToSave.setRegisteredOrInsuredIndicator("9");
		mailbagVOToSave.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);
		mailbagVOToSave.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
		mailbagVOToSave.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(Double.parseDouble(mailbagVOToSave.getMailbagId().substring(10, 12)))));
		mailbagVOToSave.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(Double.parseDouble(mailbagVOToSave.getMailbagId().substring(10, 12)))));
		mailbagVOToSave.setStrWeight(quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(Double.parseDouble(mailbagVOToSave.getMailbagId().substring(10, 12)))));
		return mailbagVOToSave;
	}

	private Mailbag findExistingMailBag(MailUploadVO mailUploadVO) throws FinderException {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailUploadVO.getCompanyCode());
		Mailbag mailbag = null;
		long sequencenum = 0;
		if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() > 0) {
			sequencenum = mailUploadVO.getMailSequenceNumber() > 0 ? mailUploadVO.getMailSequenceNumber()
					: Mailbag.findMailBagSequenceNumberFromMailIdr(mailUploadVO.getMailTag(),
							mailUploadVO.getCompanyCode());
		}
		if (sequencenum > 0) {
			mailbagPk.setMailSequenceNumber(sequencenum);
			if (mailSeqNum == null) {
				mailSeqNum = new HashMap<String, Long>();
			}
			mailSeqNum.put(mailUploadVO.getMailTag(), sequencenum);
			mailbag = Mailbag.find(mailbagPk);
		}
		return mailbag;
	}

	private boolean checkIfFlightToBeCancelled(Collection<FlightValidationVO> flightDetailsVOs,
			ScannedMailDetailsVO scannedMailDetailsVO, String airportCode) {
		boolean isStatusTBAorTBC = false;
		int count = 0;
		String bypassTBAVal = null;
		try {
			bypassTBAVal = findSystemParameterValue(TBA_VALIDATION_BYPASS);
		} finally {
		}
		for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
			if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& flightValidationVO.getLegDestination().equals(airportCode))
					|| flightValidationVO.getLegOrigin().equals(airportCode)) {
				log.info("" + "VO is" + " " + flightValidationVO);
				if ((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())
						&& MailConstantsVO.FLAG_NO.equals(bypassTBAVal))
						|| FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())) {
					count++;
				}
			}
		}
		if (count == flightDetailsVOs.size()) {
			isStatusTBAorTBC = true;
		}
		return isStatusTBAorTBC;
	}

	/**
	* Method for save Arrival Session
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public void saveArrivalFromAndroid(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.info("" + "saveArrivalFromUpload" + " " + scannedMailDetailsVO);
		if (scannedMailDetailsVO != null) {
			Collection<MailArrivalVO> mailArrivalVOs = new ArrayList<MailArrivalVO>();
			Collection<MailArrivalVO> mailArrivalVOsToSave = null;
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
				mailArrivalVOs = makeMailArrivalVOs(scannedMailDetailsVO, logonAttributes);
			} else {
				MailArrivalVO mailArrivalVO = makeMailArrivalVO(scannedMailDetailsVO, logonAttributes, false);
				if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
						&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())) {
					mailArrivalVO.setDeliveryNeeded(true);
				}
				mailArrivalVOs.add(mailArrivalVO);
			}
			if (mailArrivalVOs != null && mailArrivalVOs.size() > 0) {
				for (MailArrivalVO mailArrivalVO : mailArrivalVOs) {
					if (mailArrivalVO.isDeliveryNeeded()) {
						scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_DELIVERED);
						mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
						mailArrivalVOsToSave.add(mailArrivalVO);
						try {
							log.info("" + "Going To call saveScannedDeliverMails" + " " + mailArrivalVOsToSave);
							saveScannedDeliverMails(mailArrivalVOsToSave);
						} catch (ContainerAssignmentException e) {
							constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
									MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
						} catch (DuplicateMailBagsException e) {
							constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						} catch (MailbagIncorrectlyDeliveredException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						} catch (InvalidFlightSegmentException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
									MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
						} catch (FlightClosedException e) {
							constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
									MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
						} catch (ULDDefaultsProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
									MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
						} catch (CapacityBookingProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
									MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailBookingException e) {
							constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
									MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailOperationsBusinessException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						}
					} else {
						mailArrivalVOsToSave = new ArrayList<MailArrivalVO>();
						mailArrivalVOsToSave.add(mailArrivalVO);
						try {
							log.info("" + "Going To call saveScannedInboundMails" + " " + mailArrivalVOsToSave);
							saveScannedInboundMails(mailArrivalVOsToSave);
						} catch (ContainerAssignmentException e) {
							if (e.getErrors() != null && e.getErrors().size() > 0) {
								for (ErrorVO error : e.getErrors()) {
									if (ContainerAssignmentException.DESTN_ASSIGNED.equals(error.getErrorCode())) {
										constructAndRaiseException(MailMLDBusniessException.DESTN_ASSIGNED,
												MailHHTBusniessException.DESTN_ASSIGNED, scannedMailDetailsVO);
									} else {
										constructAndRaiseException(MailMLDBusniessException.FLIGHT_STATUS_POL_OPEN,
												"Flight is not closed at POL", scannedMailDetailsVO);
									}
								}
							}
						} catch (DuplicateMailBagsException e) {
							constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						} catch (MailbagIncorrectlyDeliveredException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						} catch (InvalidFlightSegmentException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
									constructInvalidArrivalMessege(scannedMailDetailsVO), scannedMailDetailsVO);
						} catch (FlightClosedException e) {
							constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
									MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
						} catch (InventoryForArrivalFailedException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_ARRIVAL_EXCEPTION,
									MailHHTBusniessException.INVALID_ARRIVAL_EXCEPTION, scannedMailDetailsVO);
						} catch (ULDDefaultsProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
									MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
						} catch (CapacityBookingProxyException e) {
							constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
									MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailBookingException e) {
							constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
									MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
						} catch (MailOperationsBusinessException e) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
									MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
						}
					}
				}
			}
		}
		log.debug("saveArrivalSession" + " : " + "execute" + " Exiting");
	}

	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param airportCode
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private Collection<FlightValidationVO> validateTransferFlightandUpdateVO(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, String airportCode)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		boolean validFlightFlag = false;
		boolean isStatusTBAorTBC = false;
		Collection<FlightValidationVO> flightDetailsVOs = null;
		try {
			AirlineValidationVO airlineValidationVO = null;
			try {
				airlineValidationVO = new MailOperationsValidator().validateCarrierCode(
						scannedMailDetailsVO.getTransferFromCarrier(), scannedMailDetailsVO.getCompanyCode());
				if (airlineValidationVO == null) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
							MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
				} else {
					scannedMailDetailsVO.setTransferFrmCarrierId(airlineValidationVO.getAirlineIdentifier());
				}
			} finally {
			}
			FlightFilterVO flightFilterVO = createFlightFilterVOforTransfer(scannedMailDetailsVO, logonAttributes,
					airportCode);
			flightDetailsVOs = mailController.validateFlight(flightFilterVO);
			if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
				validFlightFlag = true;
				isStatusTBAorTBC = checkIfFlightToBeCancelled(flightDetailsVOs, scannedMailDetailsVO, airportCode);
				if (!isStatusTBAorTBC) {
					for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
						if (FlightValidationVO.FLT_STATUS_ACTIVE.equals(flightValidationVO.getFlightStatus())
								|| FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {
							if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
									&& flightValidationVO.getLegDestination().equals(airportCode))
									|| flightValidationVO.getLegOrigin().equals(airportCode)
									|| (MailConstantsVO.MAIL_STATUS_DELIVERED
											.equals(scannedMailDetailsVO.getProcessPoint()))) {
								log.info("" + "VO is" + " " + flightValidationVO);
								String route = flightValidationVO.getFlightRoute();
								String[] routeArr = route.split("-");
								ArrayList<String> pols = new ArrayList<String>();
								for (String airport : routeArr) {
									if (!airport.equals(scannedMailDetailsVO.getAirportCode())) {
										pols.add(airport);
									} else {
										break;
									}
								}
								if (!pols.isEmpty()) {
									Collections.reverse(pols);
								}
								scannedMailDetailsVO.setPols(pols);
								break;
							}
						}
					}
				}
			}
		} finally {
		}
		if (!validFlightFlag) {
			constructAndRaiseException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT,
					MailHHTBusniessException.INVALID_FLIGHT, scannedMailDetailsVO);
		}
		if (isStatusTBAorTBC) {
			constructAndRaiseException(MailMLDBusniessException.FLIGHT_TBA_TBC,
					MailHHTBusniessException.CANCELLED_FLIGHT, scannedMailDetailsVO);
		}
		return flightDetailsVOs;
	}

	private FlightFilterVO createFlightFilterVOforTransfer(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, String airportCode) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| (scannedMailDetailsVO.getTransferFrmFlightNum() != null
						|| scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0)) {
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		} else {
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		}
		flightFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(scannedMailDetailsVO.getTransferFrmCarrierId());
		flightFilterVO.setAirportCode(airportCode);
		flightFilterVO.setFlightNumber(scannedMailDetailsVO.getTransferFrmFlightNum());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(
				scannedMailDetailsVO.getTransferFrmFlightDate()));
		return flightFilterVO;
	}

	/**
	* @param mailtagLength
	* @return valid
	*/
	public boolean isValidMailtag(int mailtagLength) {
		boolean valid = false;
		String systemParameterValue = null;
		try {
			systemParameterValue = findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		} finally {
		}
		if (systemParameterValue != null && !systemParameterValue.equals("NA")) {
			String[] systemParameterVal = systemParameterValue.split(",");
			for (String a : systemParameterVal) {
				if (Integer.valueOf(a) == mailtagLength) {
					valid = true;
					break;
				}
			}
		}
		return valid;
	}

	private boolean isValidMailbagID(int mailtagLength) {
		boolean valid = true;
		String systemParameterValue = null;
		try {
			systemParameterValue = findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		} finally {
		}
		if (systemParameterValue != null && !systemParameterValue.equals("NA")) {
			String[] systemParameterVal = systemParameterValue.split(",");
			List<String> systemParameterValArr = Arrays.asList(systemParameterVal);
			for (String a : systemParameterVal) {
				if (mailtagLength > Integer.valueOf(a) && mailtagLength != 29
						&& !systemParameterValArr.contains(String.valueOf(mailtagLength))) {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}

	/**
	* @author A-7371
	* @param scannedMailDetailsVO
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private Collection<FlightValidationVO> validateFlight(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		FlightFilterVO flightFilterVO = createFlightFilterVO(scannedMailDetailsVO, logonAttributes,
				scannedMailDetailsVO.getAirportCode());
		Collection<FlightValidationVO> flightDetailsVOs = null;
		try {
			flightDetailsVOs = mailController.validateFlight(flightFilterVO);
		} finally {
		}
		return flightDetailsVOs;
	}

	/**
	* Method		:	MailUploadController.createOperationalFlightVO Added by 	:	A-8164 on 20-Feb-2019 Used for 	:	Constructing OperationalFlightVO Parameters	:	@param flightDetailsVOs Parameters	:	@return  Return type	: 	OperationalFlightVO
	*/
	private OperationalFlightVO createOperationalFlightVO(Collection<FlightValidationVO> flightDetailsVOs) {
		log.debug("createOperationalFlightVO" + " : " + "createOperationalFlightVO" + " Entering");
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
			flightValidationVO = ((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0);
			if (FlightValidationVO.FLT_STATUS_ACTIVE.equals(flightValidationVO.getFlightStatus())
					|| FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {
				operationalFlightVo.setCompanyCode(flightValidationVO.getCompanyCode());
				operationalFlightVo.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVo.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVo.setPol(flightValidationVO.getAirportCode());
				operationalFlightVo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				operationalFlightVo.setFlightDate(LocalDateMapper.toZonedDateTime(flightValidationVO.getFlightDate()));
				operationalFlightVo.setCarrierCode(flightValidationVO.getCarrierCode());
				operationalFlightVo.setPou(logonAttributes.getAirportCode());
			}
		}
		return operationalFlightVo;
	}

	/**
	* Method		:	MailUploadController.checkAutoArrival Added by 	:	A-8164 on 24-May-2019 Used for 	:	Checking whether autoarrival is enabled and valid Parameters	:	@param scannedMailDetailsVO Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailHHTBusniessException  Return type	: 	void
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	public boolean checkAutoArrival(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("checkAutoArrival starts ");
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALENABLEDPAS);
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		boolean enableAutoArrival = false;
		Map<String, String> systemParameterMap = null;
		MailbagVO mailbagVO = null;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
		}

			try {
				systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}

		String sysparfunctionpoints = null;
		String autoArrEnabledPAs = null;
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
			autoArrEnabledPAs = systemParameterMap.get(AUTOARRIVALENABLEDPAS);
		}
		if (sysparfunctionpoints != null && (sysparfunctionpoints.contains(scannedMailDetailsVO.getProcessPoint())
				|| (mailbagVO != null && mailbagVO.isDeliveryNeeded()
						&& sysparfunctionpoints.contains(MailConstantsVO.MAIL_STATUS_DELIVERED)))) {
			enableAutoArrival = true;
			if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0)
				for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {
					if (mailBagVO.getPaCode() == null) {
						OfficeOfExchangeVO originOfficeOfExchangeVO;
						originOfficeOfExchangeVO = mailController
								.validateOfficeOfExchange(mailBagVO.getCompanyCode(), mailBagVO.getOoe());
						if (originOfficeOfExchangeVO != null) {
							mailBagVO.setPaCode(originOfficeOfExchangeVO.getPoaCode());
						}
					}
					if (mailBagVO.getPaCode() != null
							&& ((FUNPNTS_RTN).contains(scannedMailDetailsVO.getProcessPoint()))) {
							if (StringUtils.contains(autoArrEnabledPAs,MailConstantsVO.ALL_RETURN_ENABLED_PA)) {
								enableAutoArrival = true;
							} else {
								if (!StringUtils.contains(autoArrEnabledPAs,mailBagVO.getPaCode())) {
									enableAutoArrival = false;
									break;
								}
							}

					}
				}
		}
		if (scannedMailDetailsVO.isFoundTransfer()) {
			enableAutoArrival = true;
		}
		log.debug("checkAutoArrival ends ");
		return enableAutoArrival;
	}

	/**
	* Method		:	MailUploadController.doAutoArrivalForNonAcceptedMailbags Added by 	:	A-8164 on 24-May-2019 Used for 	:	To mark return/delivery when manifest details are absent without arrival Parameters	:	@param scannedMailDetailsVO  Return type	: 	void
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws PersistenceException
	* @throws RemoteException
	* @throws MailOperationsBusinessException
	* @throws ForceAcceptanceException
	*/
	public boolean doAutoArrivalForNonAcceptedMailbags(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, String airportCode) throws MailMLDBusniessException, MailHHTBusniessException,
			PersistenceException, MailOperationsBusinessException, ForceAcceptanceException {
		log.debug("doAutoArrivalForNonAcceptedMailbags starts ");
		boolean autoArrivalRequired = false;
		MailbagVO mailbagVO = null;
		AirlineValidationVO airlineValidationVO = null;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
		}
		if ((FUNPNTS_RTN).equals(scannedMailDetailsVO.getProcessPoint())
				|| ((mailbagVO != null && mailbagVO.isDeliveryNeeded())
						|| (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())))
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			boolean isAutoArrivalRequired = checkAutoArrival(scannedMailDetailsVO);
			if (isAutoArrivalRequired) {
				if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
						&& scannedMailDetailsVO.getMailDetails().size() > 0) {
					Collection<MailbagHistoryVO> mailhistroryVos = null;
					String acceptedPort = null;
					String arrivalPort = null;
					boolean alreadyArrivedAtAirport = false;
					try {
						mailhistroryVos = mailController.findMailbagHistories(
								scannedMailDetailsVO.getCompanyCode(),
								scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
								scannedMailDetailsVO.getMailDetails().iterator().next().getMailSequenceNumber());
					} finally {
					}
					if (mailhistroryVos != null && mailhistroryVos.size() > 0) {
						for (MailbagHistoryVO mailbagHistoryVO : mailhistroryVos) {
							if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagHistoryVO.getMailStatus())) {
								acceptedPort = mailbagHistoryVO.getScannedPort();
							} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagHistoryVO.getMailStatus())) {
								arrivalPort = mailbagHistoryVO.getScannedPort();
								if (airportCode != null && airportCode.equals(arrivalPort)) {
									alreadyArrivedAtAirport = true;
								}
							}
						}
					}
					long sequencenum = 0;
					sequencenum = scannedMailDetailsVO.getMailDetails().iterator().next().getMailSequenceNumber() == 0
							? Mailbag.findMailBagSequenceNumberFromMailIdr(
									scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
									scannedMailDetailsVO.getCompanyCode())
							: scannedMailDetailsVO.getMailDetails().iterator().next().getMailSequenceNumber();
					scannedMailDetailsVO.getMailDetails().iterator().next().setMailSequenceNumber(sequencenum);
					MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
					if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
							&& MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
						reassignOnDestinationChange(scannedMailDetailsVO, logonAttributes);
					}
					if (sequencenum > 0) {
						mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
					}
					if (!alreadyArrivedAtAirport && mailbagInULDForSegmentVO != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getAcceptanceFlag())
							&& mailbagInULDForSegmentVO.getFlightNumber() != null
							&& mailbagInULDForSegmentVO.getFlightSequenceNumber() > 0) {
						if (checkATAisCapturedForTheManifestedFlight(mailbagInULDForSegmentVO, airportCode,
								scannedMailDetailsVO)) {
							autoArrivalRequired = false;
							return autoArrivalRequired;
						} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())
									&& MailConstantsVO.CONTAINER_STATUS_TRANSFER
											.equals(scannedMailDetailsVO.getContainerProcessPoint())
									&& mailbagInULDForSegmentVO.getContainerNumber() != null
									&& scannedMailDetailsVO.getContainerNumber() != null
									&& mailbagInULDForSegmentVO.getContainerNumber()
											.equals(scannedMailDetailsVO.getContainerNumber())
									&& MailConstantsVO.ULD_TYPE.equals(mailbagInULDForSegmentVO.getContainerType())
									&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
								scannedMailDetailsVO.setContainerFoundTransfer(true);
							}
							mailbagInULDForSegmentVO = null;
						} else if (mailbagVO.isDeliveryNeeded()) {
							scannedMailDetailsVO.setFlightNumber("");
						}
					}
					if (acceptedPort == null && arrivalPort == null || ((mailbagVO.isDeliveryNeeded()
							|| (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())))
							&& (scannedMailDetailsVO.getFlightNumber() == null
									|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0))
							|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
						if (scannedMailDetailsVO.getCarrierCode() != null
								&& scannedMailDetailsVO.getCarrierCode().trim().length() != 0) {
							try {
								airlineValidationVO = new MailOperationsValidator().validateCarrierCode(
										scannedMailDetailsVO.getCarrierCode(), scannedMailDetailsVO.getCompanyCode());
								if (airlineValidationVO != null) {
									scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
								}
							} finally {
							}
						}
						if ((FUNPNTS_RTN).equals(scannedMailDetailsVO.getProcessPoint())) {
							scannedMailDetailsVO.getMailDetails().iterator().next()
									.setPou(scannedMailDetailsVO.getAirportCode());
							long seqNUm = 0;
							MailbagInULDForSegmentVO mailbagInULDForNextSegVO = null;
							if (scannedMailDetailsVO.getMailDetails().iterator().next().getMailSequenceNumber() > 0) {
								mailbagInULDForNextSegVO = new MailbagInULDForSegment().getManifestInfoForNextSeg(
										scannedMailDetailsVO.getMailDetails().iterator().next());
							}
							if (mailbagInULDForNextSegVO == null) {
								for (MailbagVO mailbagVo : scannedMailDetailsVO.getMailDetails()) {
									scannedMailDetailsVO.getMailDetails().iterator().next()
											.setPou(scannedMailDetailsVO.getAirportCode());
									mailbagVO.setCarrierId(MailConstantsVO.ZERO);
									mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
									mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
									mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
									mailbagVo.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_RETURNED);
									mailbagVo.setLatestStatus(MailConstantsVO.MAIL_STATUS_RETURNED);
									mailbagVO.setMailSource(scannedMailDetailsVO.getMailSource());
								}
								seqNUm = scannedMailDetailsVO.getMailDetails().iterator().next()
										.getMailSequenceNumber() == 0 ? Mailbag.findMailBagSequenceNumberFromMailIdr(
												scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
												scannedMailDetailsVO.getCompanyCode())
												: scannedMailDetailsVO.getMailDetails().iterator().next()
														.getMailSequenceNumber();
								if (seqNUm > 0) {
									Mailbag mailBag = null;
									MailbagPK mailbagPk = new MailbagPK();
									mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
									mailbagPk.setMailSequenceNumber(scannedMailDetailsVO.getMailDetails().iterator()
											.next().getMailSequenceNumber());
									try {
										mailBag = Mailbag.find(mailbagPk);
									} catch (FinderException e) {
										log.error(FINDER_EXCEPTION_CAUGHT);
									}
									if (mailBag != null) {
										mailBag.setFlightNumber(mailbagVO.getFlightNumber());
										mailBag.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
										mailBag.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
									}
								}
							}
							saveReturnFromUpload(scannedMailDetailsVO, logonAttributes);
							autoArrivalRequired = true;
							PersistenceController.getEntityManager().flush();
							if (seqNUm <= 0) {
								seqNUm = Mailbag.findMailBagSequenceNumberFromMailIdr(
										scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
										scannedMailDetailsVO.getCompanyCode());
							}
							scannedMailDetailsVO.getMailDetails().iterator().next().setMailSequenceNumber(seqNUm);
						} else if (((mailbagVO != null && mailbagVO.isDeliveryNeeded())
								|| (MailConstantsVO.MAIL_STATUS_DELIVERED
										.equals(scannedMailDetailsVO.getProcessPoint())))
								&& (scannedMailDetailsVO.getFlightNumber() == null
										|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0)) {
							mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
							mailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
							mailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_DELIVERED);
							mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
							mailbagVO.setPou(scannedMailDetailsVO.getAirportCode());
							mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
							mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
							mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
							mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
							mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
							mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
							mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
							mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
							if (mailController.isUSPSMailbag(mailbagVO)) {
								mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
							} else {
								mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
							}
							Mailbag mailBag = null;
							if (sequencenum == 0) {
								mailBag = new Mailbag(mailbagVO);
							} else {
								MailbagPK mailbagPk = new MailbagPK();
								mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
								mailbagPk.setMailSequenceNumber(scannedMailDetailsVO.getMailDetails().iterator().next()
										.getMailSequenceNumber());
								try {
									mailBag = Mailbag.find(mailbagPk);
								} catch (FinderException e) {
									log.error(FINDER_EXCEPTION_CAUGHT);
								}
								mailBag.setLatestStatus(mailbagVO.getLatestStatus());
								mailBag.setMailbagSource(mailbagVO.getMailSource());
								if (mailBag.getPou() == null) {
									mailBag.setPou(mailbagVO.getPou());
								}
								mailBag.setFlightNumber(mailbagVO.getFlightNumber());
								mailBag.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
								mailBag.setCarrierId(mailbagVO.getCarrierId());
								mailBag.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
								mailBag.setScannedPort(mailbagVO.getScannedPort());
								mailBag.setScannedUser(mailbagVO.getScannedUser());
								mailBag.setScannedDate(mailbagVO.getScannedDate());
								mailBag.setUldNumber(mailbagVO.getContainerNumber());
								mailBag.setContainerType(mailbagVO.getContainerType());
								if (mailBag.getMailServiceLevel() == null) {
									mailBag.setMailServiceLevel(mailbagVO.getMailServiceLevel());
								}
								if (mailbagVO.isNeedDestUpdOnDlv()) {
									mailBag.setDestination(mailbagVO.getDestination());
									mailBag.setDestinationOfficeOfExchange(mailbagVO.getDoe());
								}
								if (mailBag.getFirstScanDate() == null && mailBag.getFirstScanPort() == null) {
									mailBag.setFirstScanDate(mailbagVO.getScannedDate().toLocalDateTime());
									mailBag.setFirstScanPort(mailbagVO.getScannedPort());
								}
							}
							PersistenceController.getEntityManager().flush();
							if (mailController.isUSPSMailbag(mailbagVO)) {
								try {
									new ULDForSegment().updatemailperformanceDetails(mailbagVO, null, mailBag);
								} catch (PersistenceException e) {
									e.getMessage();
								}
							} else {
								mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
								mailBag.setOnTimeDelivery(mailbagVO.getOnTimeDelivery());
							}
							sequencenum = scannedMailDetailsVO.getMailDetails().iterator().next()
									.getMailSequenceNumber() == 0 ? Mailbag.findMailBagSequenceNumberFromMailIdr(
											scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
											scannedMailDetailsVO.getCompanyCode())
											: scannedMailDetailsVO.getMailDetails().iterator().next()
													.getMailSequenceNumber();
							Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
							mailbagVO.setMailSequenceNumber(sequencenum);
							mailbagVO.setMailSource(scannedMailDetailsVO.getMailSource());
							mailbagVOs.add(mailbagVO);
							new ResditController().flagDeliveredResditForMailbags(mailbagVOs,
									scannedMailDetailsVO.getAirportCode());
							MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
							mailController.flagMailbagHistoryForDelivery(mailbagVOs, ContextUtil.getInstance().getTxContext(ContextUtil.TRIGGER_POINT));
							autoArrivalRequired = true;
							return autoArrivalRequired;
						} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							String origin = scannedMailDetailsVO.getMailDetails() != null
									&& scannedMailDetailsVO.getMailDetails().iterator().next().getOrigin() != null
											? scannedMailDetailsVO.getMailDetails().iterator().next().getOrigin()
											: null;
							String destination = scannedMailDetailsVO.getMailDetails() != null
									&& scannedMailDetailsVO.getMailDetails().iterator().next().getDestination() != null
											? scannedMailDetailsVO.getMailDetails().iterator().next().getDestination()
											: null;
							if (origin != null && !origin.equals(scannedMailDetailsVO.getAirportCode())
									&& !checkforCoterminusAirport(scannedMailDetailsVO, MailConstantsVO.RESDIT_RECEIVED,
											logonAttributes)
									&& mailbagInULDForSegmentVO == null) {
								if (sequencenum == 0) {
									mailbagVO.setOrigin(origin);
									mailbagVO.setDestination(destination);
									mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
									mailbagVO.setMailbagDataSource(scannedMailDetailsVO.getMailSource());
									mailbagVO.setMailSource(scannedMailDetailsVO.getMailSource());
									mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
									mailbagVO.setPou(scannedMailDetailsVO.getPou() != null
											&& scannedMailDetailsVO.getPou().trim().length() > 0
													? scannedMailDetailsVO.getPou()
													: scannedMailDetailsVO.getDestination());
									if (scannedMailDetailsVO.getFlightNumber() != null
											&& scannedMailDetailsVO.getFlightNumber().trim().length() > 0) {
										mailbagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
									} else {
										mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
									}
									if (scannedMailDetailsVO.getFlightSequenceNumber() != 0) {
										mailbagVO.setFlightSequenceNumber(
												scannedMailDetailsVO.getFlightSequenceNumber());
									} else {
										mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
									}
									mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
									if (scannedMailDetailsVO.getContainerNumber() != null
											&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
										mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
										mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
										mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
									}
									mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
									mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
									mailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
									Transaction tx = null;
									boolean success = false;
									try {
										TransactionProvider tm = PersistenceController.__getTransactionProvider();
										tx = tm.getNewTransaction(false);
										new Mailbag(mailbagVO);
										success = true;
									} finally {
										if (success) {
											tx.commit();
										}
									}
								}
								scannedMailDetailsVO.getValidatedContainer().setFoundTransfer(true);
								scannedMailDetailsVO.setFoundTransfer(true);
								scannedMailDetailsVO.setTransferFrmFlightNum(" ");
								scannedMailDetailsVO.setToCarrierCode(scannedMailDetailsVO.getCarrierCode());
								if (scannedMailDetailsVO.getContainerNumber() != null
										&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
									performContainerAsSuchOperations(scannedMailDetailsVO, logonAttributes);
								}
								if (scannedMailDetailsVO.getTransferFromCarrier() != null
										&& scannedMailDetailsVO.getTransferFromCarrier().trim().length() > 0) {
									scannedMailDetailsVO.getMailDetails().iterator().next()
											.setTransferFromCarrier(scannedMailDetailsVO.getTransferFromCarrier());
								}
								if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())
										&& MailConstantsVO.CONTAINER_STATUS_TRANSFER
												.equals(scannedMailDetailsVO.getContainerProcessPoint())
										&& MailConstantsVO.MAIL_STATUS_TRANSFERRED
												.equals(scannedMailDetailsVO.getProcessPoint())
										&& !scannedMailDetailsVO.isContainerFoundTransfer() && mailbagVO != null
										&& scannedMailDetailsVO.getMailDetails() == null) {
									Collection<MailbagVO> mailbagVOs = new ArrayList<>();
									mailbagVOs.add(mailbagVO);
									scannedMailDetailsVO.setMailDetails(mailbagVOs);
									scannedMailDetailsVO.getValidatedContainer()
											.setPaBuiltFlag(scannedMailDetailsVO.getIsContainerPabuilt());
									scannedMailDetailsVO.setScannedContainerDetails(null);
								}
								if (!scannedMailDetailsVO.isContainerFoundTransfer()) {
									saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
								}
								if (scannedMailDetailsVO.isContainerFoundTransfer() && mailbagVO != null
										&& scannedMailDetailsVO.getMailDetails() == null) {
									Collection<MailbagVO> mailbagVOs = new ArrayList<>();
									mailbagVOs.add(mailbagVO);
									scannedMailDetailsVO.setMailDetails(mailbagVOs);
								}
								autoArrivalRequired = true;
								return autoArrivalRequired;
							}
						}
					}
					log.debug("doAutoArrivalForNonAcceptedMailbags ends ");
				}
			}
		}
		return autoArrivalRequired;
	}

	/**
	* Method		:	MailUploadController.saveAutoArrivalForNonAcceptedMailbags Added by 	:	A-8164 on 24-May-2019 Changed 	:	A-8164 for ICRD-333716 Used for 	:	Saving the arrival transaction from error handling screen Parameters	:	@param scannedMailDetailsVO  Return type	: 	void
	* @throws SystemException
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	public void saveAutoArrivalForNonAcceptedMailbags(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		log.debug("saveAutoArrivalForNonAcceptedMailbags starts ");
		if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
				&& scannedMailDetailsVO.getMailDetails().size() > 0) {
			LoginProfile logonAttributes = null;
			Collection<MailbagVO> mailDetails = scannedMailDetailsVO.getMailDetails();
			for (MailbagVO mailbagVO : mailDetails) {
				mailbagVO.setDamagedMailbags(null);
				mailbagVO.setDamageFlag("N");
				mailbagVO.setActionMode(null);
				if (scannedMailDetailsVO.getProcessPointBeforeArrival() != null
						&& scannedMailDetailsVO.getProcessPointBeforeArrival().trim().length() > 0
						&& scannedMailDetailsVO.getProcessPointBeforeArrival()
								.equals(MailConstantsVO.MAIL_STATUS_DELIVERED)) {
					mailbagVO.setDeliveryStatusForAutoArrival(true);
				}
			}
			scannedMailDetailsVO.setMailDetails(mailDetails);
			try {
				logonAttributes = contextUtil.callerLoginProfile();
			} finally {
			}
			saveArrivalFromUpload(scannedMailDetailsVO, logonAttributes);
			MailbagPK mailbagPk = new MailbagPK();
			Mailbag mailBag = null;
			mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			long sequencenum = Mailbag.findMailBagSequenceNumberFromMailIdr(
					scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId(),
					scannedMailDetailsVO.getCompanyCode());
			if (sequencenum > 0) {
				mailbagPk.setMailSequenceNumber(sequencenum);
			}
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				log.error(FINDER_EXCEPTION_CAUGHT);
			}
			if (mailBag != null) {
				if (scannedMailDetailsVO.getProcessPointBeforeArrival() != null
						&& scannedMailDetailsVO.getProcessPointBeforeArrival().trim().length() > 0) {
					mailBag.setLatestStatus(scannedMailDetailsVO.getProcessPointBeforeArrival());
					mailBag.setMailbagSource(scannedMailDetailsVO.getProcessPointBeforeArrival());
				}
			}
			log.debug("saveAutoArrivalForNonAcceptedMailbags ends ");
		}
	}

	/**
	* Method		:	MailUploadController.updateVOForAutoArrival Added by 	:	A-8353 on 12-Jul-2019 Used for 	: Updating  ScannedMailDetailsVO for saving Arrival transaction as a part of transfer in AutoArrival scenario  Parameters	:	@param ScannedMailDetailsVO
	*/
	private void updateVOForAutoArrival(ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		ContainerDetailsVO containerVO = new ContainerDetailsVO();
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {
				containerVO.setCarrierCode(mailBagVO.getCarrierCode());
				containerVO.setCarrierId(mailBagVO.getCarrierId());
				containerVO.setContainerNumber(mailBagVO.getContainerNumber());
				containerVO.setCompanyCode(mailBagVO.getCompanyCode());
				containerVO.setFlightNumber(mailBagVO.getFlightNumber());
				containerVO.setFlightDate(mailBagVO.getFlightDate());
				containerVO.setFlightSequenceNumber(mailBagVO.getFlightSequenceNumber());
				containerVO.setSegmentSerialNumber(mailBagVO.getSegmentSerialNumber());
				containerVO.setLegSerialNumber(mailBagVO.getLegSerialNumber());
				if (mailBagVO.getPou() != null) {
					containerVO.setPou(mailBagVO.getPou());
				}
			}
		}
		scannedMailDetailsVO.setValidatedContainer(containerVO);
		scannedMailDetailsVO.setToCarrierCode(null);
		scannedMailDetailsVO.setToCarrierid(0);
		scannedMailDetailsVO.setToFlightDate(null);
		scannedMailDetailsVO.setToFlightNumber(null);
		scannedMailDetailsVO.setToFlightSequenceNumber(0);
		scannedMailDetailsVO.setToLegSerialNumber(0);
		scannedMailDetailsVO.setToSegmentSerialNumber(0);
		scannedMailDetailsVO.setTransferFrmFlightDate(null);
		scannedMailDetailsVO.setTransferFrmFlightNum(null);
		scannedMailDetailsVO.setTransferFrmFlightSeqNum(0);
		scannedMailDetailsVO.setTransferFromCarrier("");
	}

	/**
	* @author A-7371
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException
	*/
	public boolean checkforCoterminusAirport(ScannedMailDetailsVO scannedMailDetailsVO, String eventCode,
			LoginProfile logonAttributes) {
		boolean coterminus = false;
		String poaCode = null;
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		if (MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured)) {
			OfficeOfExchangeVO originOfficeOfExchangeVO;
			OfficeOfExchangeVO destOfficeOfExchangeVO;
			for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				poaCode = scannedMailbagVO.getPaCode() != null && scannedMailbagVO.getPaCode().trim().length() > 0
						? scannedMailbagVO.getPaCode()
						: mailController.findPAForOfficeOfExchange(scannedMailbagVO.getCompanyCode(),
								scannedMailbagVO.getOoe());
				if (MailConstantsVO.RESDIT_RECEIVED.equals(eventCode)
						|| MailConstantsVO.RESDIT_UPLIFTED.equals(eventCode)) {
					String orginPort;
					if (scannedMailbagVO.getOrigin() != null && scannedMailbagVO.getOrigin().trim().length() > 0) {
						orginPort = scannedMailbagVO.getOrigin();
					} else {
						originOfficeOfExchangeVO = validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(),
								scannedMailbagVO.getOoe());
						if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
							String originOfficeOfExchange = originOfficeOfExchangeVO.getCode();
							orginPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
									originOfficeOfExchange);
							originOfficeOfExchangeVO.setAirportCode(orginPort);
						} else {
							orginPort = originOfficeOfExchangeVO != null ? originOfficeOfExchangeVO.getAirportCode()
									: null;
						}
					}
					if (scannedMailDetailsVO.getAirportCode().equals(orginPort)) {
						coterminus = true;
					} else {
						ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailbagVO.getScannedPort(), true);
						coterminus = mailController.validateCoterminusairports(orginPort,
								scannedMailDetailsVO.getAirportCode(), eventCode, poaCode, dspDate);
					}
				}
				if (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode)
						|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode)) {
					String destinationPort;
					if (scannedMailbagVO.getDestination() != null
							&& scannedMailbagVO.getDestination().trim().length() > 0) {
						destinationPort = scannedMailbagVO.getDestination();
					} else {
						destOfficeOfExchangeVO = validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(),
								scannedMailbagVO.getDoe());
						if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
							String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
							destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
									destOfficeOfExchange);
							destOfficeOfExchangeVO.setAirportCode(destinationPort);
						} else {
							destinationPort = destOfficeOfExchangeVO != null ? destOfficeOfExchangeVO.getAirportCode()
									: null;
						}
					}
					if (scannedMailDetailsVO.getAirportCode().equals(destinationPort)) {
						coterminus = true;
					} else {
						ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailbagVO.getScannedPort(), true);
						coterminus = mailController.validateCoterminusairports(destinationPort,
								scannedMailDetailsVO.getAirportCode(), eventCode, poaCode, dspDate);
					}
				}
			}
		}
		return coterminus;
	}

	/**
	* @author A-7371
	* @param scannedMailDetailsVO
	* @param logonAttributes
	* @param flightDetailsVOs
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private ScannedMailDetailsVO checkForULDlevelArrivalOrDelivery(ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes, Collection<FlightValidationVO> flightDetailsVOs)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
				&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())) {
			containerAssignmentVO = this.cntAsgmntVO == null
					? findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO)
					: this.cntAsgmntVO;
			this.cntAsgmntVO = containerAssignmentVO;
		} else {
			containerAssignmentVO = mailController
					.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
			this.cntAsgmntVO = containerAssignmentVO;
		}
		if (containerAssignmentVO != null) {
			Collection<ContainerDetailsVO> containerDetailsVOs = null;
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			;
			Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
			containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			if (scannedMailDetailsVO.getFlightNumber() != null
					&& scannedMailDetailsVO.getFlightNumber().trim().length() > 0) {
				containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
				containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
				containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
				AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
						scannedMailDetailsVO.getCompanyCode(), scannedMailDetailsVO.getCarrierCode());
				if (airlineValidationVO != null) {
					containerDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
				}
			} else {
				containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
				containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
				containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			}
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
				if ("-1".equals(containerAssignmentVO.getFlightNumber())
						&& MailConstantsVO.DESTN_FLT == containerAssignmentVO.getFlightSequenceNumber()
						&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
						&& (scannedMailDetailsVO.getMailDetails() == null
								|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
					constructAndRaiseException(MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN,
							MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN, scannedMailDetailsVO);
				}
			}
			containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
			containerDetailsVO.setPol(isNotEmpty(scannedMailDetailsVO.getPol()) ? scannedMailDetailsVO.getPol()
					: containerAssignmentVO.getAirportCode());
			containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
			containers.add(containerDetailsVO);
			containerDetailsVOs = mailController.findMailbagsInContainer(containers);
			if (containerDetailsVOs != null) {
				containerDetailsVO = containerDetailsVOs.iterator().next();
				scannedMailDetailsVO.setMailDetails(containerDetailsVO.getMailDetails());
			}
		} else {
			String paBuiltContainerSave = null;
			paBuiltContainerSave = findSystemParameterValue(PABUILT_CONTAINERSAVE_ENABLED);
			if (scannedMailDetailsVO.getFlightNumber() == null || "".equals(scannedMailDetailsVO.getFlightNumber())) {
				//TODO: To correct AA flow
				//				scannedMailDetailsVO = new com.ibsplc.icargo.business.mail.operations.aa.MailUploadController()
//						.constructAndroidException(MailHHTBusniessException.ULD_NOT_FOUND_EXCEPTION,
//								MailHHTBusniessException.ULD_NOT_FOUND_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.FLAG_YES.equals(paBuiltContainerSave)
					&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getIsContainerPabuilt())
					&& scannedMailDetailsVO.getContainerNumber() != null
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& (scannedMailDetailsVO.getMailDetails() == null
							|| scannedMailDetailsVO.getMailDetails().isEmpty())) {
				int offsetforPABuilt = 0;
				offsetforPABuilt = Integer.parseInt(findSystemParameterValue(PERIODFOR_PABUILTMAILS_INBOUND));
				ZonedDateTime fromDate = localDateUtil.getLocalDate(null, false);
				ZonedDateTime toDate = localDateUtil.getLocalDate(null, false);
				fromDate = fromDate.plusDays(-offsetforPABuilt);
				toDate = toDate.plusDays(1);
				Collection<MailbagVO> mailbagVOs = findMailbagsForPABuiltContainerSave(
						scannedMailDetailsVO.getContainerNumber(), scannedMailDetailsVO.getCompanyCode(), fromDate,
						toDate);
				if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
					scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
					if (scannedMailDetailsVO.getContainerDeliveryFlag() != null
							&& MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())) {
						mailbagVOs.forEach(mailbagVO -> mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_YES));
					}
					mailbagVOs.forEach(mailbagVO -> mailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES));
					scannedMailDetailsVO.setMailDetails(mailbagVOs);
					updateVOForArrival(scannedMailDetailsVO, logonAttributes);
					saveArrivalFromAndroid(scannedMailDetailsVO, logonAttributes);
					//TODO: AA flow to correct
//					scannedMailDetailsVO = new com.ibsplc.icargo.business.mail.operations.aa.MailUploadController()
//							.constructAndroidException(MailHHTBusniessException.CONTAINER_INBOUND_OPERATION_SUCESSS,
//									MailHHTBusniessException.CONTAINER_INBOUND_OPERATION_SUCESSS, scannedMailDetailsVO);
				}
			}
		}
		scannedMailDetailsVO.setMailDetails(new ArrayList<MailbagVO>());
		return scannedMailDetailsVO;
	}

	/**
	* Method		:	MailUploadController.constructContainerDetailsVO Added by 	:	U-1307 on 16-Aug-2019 Used for 	:	Constructing ContainerDetailsVO from ScannedMailDetailsVO Parameters	:	@param scannedMailDetailsVO Parameters	:	@return  Return type	: 	ContainerDetailsVO
	*/
	private ContainerDetailsVO constructContainerDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		if (MailConstantsVO.OPERATION_INBOUND.equals(scannedMailDetailsVO.getOperationType())) {
			containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
			containerDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
		} else {
			containerDetailsVO.setPol(scannedMailDetailsVO.getAirportCode());
		}
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerDetailsVO.setFlightNumber(
				isNotEmpty(scannedMailDetailsVO.getFlightNumber()) ? scannedMailDetailsVO.getFlightNumber()
						: MailConstantsVO.DESTN_FLT_STR);
		containerDetailsVO.setFlightSequenceNumber(
				scannedMailDetailsVO.getFlightSequenceNumber() > 0 ? scannedMailDetailsVO.getFlightSequenceNumber()
						: -1);
		containerDetailsVO.setLegSerialNumber(
				scannedMailDetailsVO.getLegSerialNumber() > 0 ? scannedMailDetailsVO.getLegSerialNumber() : -1);
		return containerDetailsVO;
	}

	/**
	* Method		:	MailUploadController.isNotEmpty Added by 	:	U-1307 on 16-Aug-2019 Used for 	:	Check if input string is not empty Parameters	:	@param s Parameters	:	@return  Return type	: 	boolean
	*/
	private static boolean isNotEmpty(String s) {
		return (s != null && !s.trim().equals(""));
	}

	/**
	* @author A-5526Added as part of CRQ IASCB-44518
	* @return
	* @throws SystemException
	*/
	private void updateMailbagVOForTransfer(Collection<MailbagVO> mailbagVOs,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		for (MailbagVO mailbagVOToTransfer : mailbagVOs) {
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVOToTransfer.getCompanyCode());
			mailbagPk.setMailSequenceNumber(mailbagVOToTransfer.getMailSequenceNumber());
			Mailbag mailBag;
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				mailBag = null;
			}
			if (mailBag != null) {
				mailbagVOToTransfer.setMailbagId(mailBag.getMailIdr());
				mailbagVOToTransfer.setWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(mailBag.getWeight())));
				mailbagVOToTransfer.setMailClass(mailBag.getMailClass());
				mailbagVOToTransfer.setOrigin(mailBag.getOrigin());
				mailbagVOToTransfer.setDestination(mailBag.getDestination());
				mailbagVOToTransfer.setOoe(mailBag.getOrginOfficeOfExchange());
				mailbagVOToTransfer.setDoe(mailBag.getDestinationOfficeOfExchange());
				mailbagVOToTransfer.setMailCategoryCode(mailBag.getMailCategory());
				mailbagVOToTransfer.setMailSubclass(mailBag.getMailSubClass());
				mailbagVOToTransfer.setPaCode(mailBag.getPaCode());
			}
			mailbagVOToTransfer.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			mailbagVOToTransfer.setDamageFlag(MailConstantsVO.FLAG_NO);
			mailbagVOToTransfer.setArrivedFlag(MailConstantsVO.FLAG_NO);
			mailbagVOToTransfer.setDeliveredFlag(MailConstantsVO.FLAG_NO);
			mailbagVOToTransfer.setTransferFromCarrier(scannedMailDetailsVO.getTransferFromCarrier());
			mailbagVOToTransfer.setMraStatus(MailConstantsVO.FLAG_NO);
			mailbagVOToTransfer.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			mailbagVOToTransfer.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
			mailbagVOToTransfer.setFlightDate(scannedMailDetailsVO.getFlightDate());
			mailbagVOToTransfer.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
			mailbagVOToTransfer.setCarrierId(scannedMailDetailsVO.getCarrierId());
			mailbagVOToTransfer.setLastUpdateUser(scannedMailDetailsVO.getScannedUser());
			mailbagVOToTransfer.setScannedUser(scannedMailDetailsVO.getScannedUser());
			if (scannedMailDetailsVO.getAirportCode() != null && !scannedMailDetailsVO.getAirportCode().isEmpty()) {
				mailbagVOToTransfer.setScannedPort(scannedMailDetailsVO.getAirportCode());
				mailbagVOToTransfer
						.setScannedDate(localDateUtil.getLocalDate(scannedMailDetailsVO.getAirportCode(), true));
			}
			if (scannedMailDetailsVO.getMailSource() != null) {
				mailbagVOToTransfer.setMailSource(scannedMailDetailsVO.getMailSource());
			}
		}
	}

	/**
	* @author U-1439
	* @param mailbagVOs
	* @param scannedMailDetailsVO
	* @throws SystemException
	*/
	private void updateMailbagVOForAcceptance(Collection<MailbagVO> mailbagVOs,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
			mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
			mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
			mailbagVO.setMraStatus(MailConstantsVO.FLAG_NO);
			mailbagVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			mailbagVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
			mailbagVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
			mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
			mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
			mailbagVO.setLastUpdateUser(scannedMailDetailsVO.getScannedUser());
			mailbagVO.setScannedUser(scannedMailDetailsVO.getScannedUser());
			if (scannedMailDetailsVO.getOperationTime() != null) {
				mailbagVO.setScannedDate(scannedMailDetailsVO.getOperationTime());
			}
			mailbagVO.setTransferFromCarrier(scannedMailDetailsVO.getTransferFromCarrier());
		}
	}

	/**
	* @author A-9619 as part of IASCB-55196
	* @return company specific @MailUploadController
	* @throws SystemException
	*/
	private MailUploadController getMailUploadInstance() {
		return ContextUtil.getInstance().getBean(MailUploadController.class);
	}

	/**
	* @author A-9619 as part of IASCB-55196
	* @param mailbagToArrive
	* @param scannedMailDetailsVO
	*/
	public void setStoragUnitForDnataSpecific(MailbagVO mailbagToArrive, ScannedMailDetailsVO scannedMailDetailsVO) {
	}

	/**
	* @author A-9619 as part of IASCB-55196
	* @param scannedMailDetailsVO
	* @param containerVO
	*/
	public void setScreeningUserForDnataSpecific(ScannedMailDetailsVO scannedMailDetailsVO, ContainerVO containerVO) {
	}

	/**
	* @author A-8353 for IASCB-57925
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	*/
	public void validateContainerForReassign(ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (scannedMailDetailsVO.getContainerNumber() != null
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			try {
				containerAssignmentVO = mailController
						.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				this.cntAsgmntVO = containerAssignmentVO;
			} finally {
			}
		}
		if (containerAssignmentVO != null && MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
				&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getArrivalFlag())
				&& containerAssignmentVO.getAirportCode() != null
				&& containerAssignmentVO.getAirportCode().equalsIgnoreCase(scannedMailDetailsVO.getAirportCode())
				&& containerAssignmentVO.getContainerType() != null && scannedMailDetailsVO.getContainerType() != null
				&& containerAssignmentVO.getContainerType().equals(scannedMailDetailsVO.getContainerType())) {
			if (containerAssignmentVO.getAssignedDate() != null && scannedMailDetailsVO.getDeviceDateAndTime() != null
					&& localDateUtil.toUTCTime(containerAssignmentVO.getAssignedDate())
							.isAfter(localDateUtil.toUTCTime(scannedMailDetailsVO.getDeviceDateAndTime()))) {
				new MailOperationsValidator().constructAndroidException(MailConstantsVO.CONTAINER_REASSIGN_ERROR,
						MailHHTBusniessException.CONTAINER_REASSIGN_ERROR, scannedMailDetailsVO);
			} else {
				if (containerAssignmentVO.getFlightNumber() != null
						&& !MailConstantsVO.DESTN_FLT_STR.equals(containerAssignmentVO.getFlightNumber())
						&& containerAssignmentVO.getFlightSequenceNumber() != -1
						&& (scannedMailDetailsVO.getFlightNumber() == null
								|| scannedMailDetailsVO.getFlightNumber().trim().length() == 0)) {
					String errorDescription = new StringBuilder()
							.append(MailHHTBusniessException.CONTAINER_REASSIGN_FLIGHT)
							.append(containerAssignmentVO.getCarrierCode()).append(" ")
							.append(containerAssignmentVO.getFlightNumber()).toString();
					new MailOperationsValidator().constructAndroidException(
							MailConstantsVO.CONTAINER_REASSIGN_WARNING, errorDescription, scannedMailDetailsVO);
				}
			}
		}
	}

	/**
	* @author A-8353 for IASCB-57925
	* @param mailUploadVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	*/
	public void validateMailbagForReassign(MailUploadVO mailUploadVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (mailUploadVO.getContainerNumber() != null && mailUploadVO.getContainerNumber().trim().length() > 0) {
			try {
				containerAssignmentVO = (this.cntAsgmntVO == null)
						? findLatestContainerAssignment(mailUploadVO.getContainerNumber())
						: this.cntAsgmntVO;
				this.cntAsgmntVO = containerAssignmentVO;
			} finally {
			}
		}
		if (containerAssignmentVO != null && MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
				&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getArrivalFlag())
				&& containerAssignmentVO.getAirportCode() != null
				&& containerAssignmentVO.getAirportCode().equalsIgnoreCase(mailUploadVO.getScannedPort())
				&& containerAssignmentVO.getContainerType() != null && mailUploadVO.getContainerType() != null
				&& containerAssignmentVO.getContainerType().equals(mailUploadVO.getContainerType())) {
			if (containerAssignmentVO.getAssignedDate() != null && mailUploadVO.getDeviceDateAndTime() != null
					&& localDateUtil.toUTCTime(containerAssignmentVO.getAssignedDate())
					.isAfter(localDateUtil.toUTCTime(mailUploadVO.getDeviceDateAndTime()))) {
				if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() > 0) {
					setMailbagDetailForReassign(containerAssignmentVO, mailUploadVO);
				} else {
					throw new MailHHTBusniessException(MailConstantsVO.CONTAINER_REASSIGN_ERROR,
							MailHHTBusniessException.CONTAINER_REASSIGN_ERROR);
				}
			}
			if ((mailUploadVO.getDestination() == null || mailUploadVO.getDestination().trim().length() == 0)
					&& mailUploadVO.getCarrierCode() != null && mailUploadVO.getCarrierCode().trim().length() > 0
					&& containerAssignmentVO.getDestination() != null && logonAttributes.getOwnAirlineCode() != null
					&& logonAttributes.getOwnAirlineCode().equals(mailUploadVO.getCarrierCode())) {
				mailUploadVO.setDestination(containerAssignmentVO.getDestination());
			}
		}
	}

	/**
	* @author A-8353 for IASCB-57925
	* @param containerAssignmentVO
	* @param mailUploadVO
	*/
	private void setMailbagDetailForReassign(ContainerAssignmentVO containerAssignmentVO, MailUploadVO mailUploadVO) {
		if (containerAssignmentVO.getCarrierCode() != null) {
			mailUploadVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
		}
		if (containerAssignmentVO.getFlightNumber() != null
				&& containerAssignmentVO.getFlightNumber().trim().length() > 0
				&& !(MailConstantsVO.DESTN_FLT_STR.equals(containerAssignmentVO.getFlightNumber()))) {
			mailUploadVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
		} else {
			mailUploadVO.setFlightNumber("");
		}
		mailUploadVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		if (containerAssignmentVO.getFlightDate() != null) {
			mailUploadVO.setFlightDate(containerAssignmentVO.getFlightDate());
		}
		if (containerAssignmentVO.getPou() != null && containerAssignmentVO.getPou().trim().length() > 0) {
			mailUploadVO.setContainerPOU(containerAssignmentVO.getPou());
		}
		if (containerAssignmentVO.getDestination() != null
				&& containerAssignmentVO.getDestination().trim().length() > 0) {
			mailUploadVO.setDestination(containerAssignmentVO.getDestination());
		}
		if (containerAssignmentVO.getDestination() != null
				&& containerAssignmentVO.getDestination().trim().length() > 0) {
			mailUploadVO.setDestination(containerAssignmentVO.getDestination());
		}
		if (containerAssignmentVO.getAirportCode() != null
				&& containerAssignmentVO.getAirportCode().trim().length() > 0) {
			mailUploadVO.setContainerPol(containerAssignmentVO.getAirportCode());
		}
		if (mailUploadVO.getDeviceDateAndTime() != null) {
			mailUploadVO.setDateTime(mailUploadVO.getDeviceDateAndTime()
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
		}
	}

	/**
	* @author A-8353 for IASCB-57929
	* @return
	* @throws SystemException
	*/
	public boolean isCommonCityForAirport(String companyCode, String currentPort, String destination) {
		AirportValidationVO airportValidationVoOfDestination = sharedAreaProxy.validateAirportCode(companyCode,
				destination);
		AirportValidationVO airportValidationVoOfCurrentPort = sharedAreaProxy.validateAirportCode(companyCode,
				currentPort);
		boolean isCommonCityForArp = false;
		if (airportValidationVoOfDestination != null && airportValidationVoOfDestination.getCityCode() != null
				&& airportValidationVoOfCurrentPort != null && airportValidationVoOfCurrentPort.getCityCode() != null
				&& airportValidationVoOfDestination.getCityCode()
						.equals(airportValidationVoOfCurrentPort.getCityCode())) {
			isCommonCityForArp = true;
		}
		return isCommonCityForArp;
	}

	/**
	* @author A-8353 for IASCB-65073
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException
	* @throws ForceAcceptanceException
	*/
	private void reassignOnDestinationChange(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		ContainerDetailsVO scannedContainerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if (!(ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(scannedContainerDetailsVO.getContainerOperationFlag()))) {
			ContainerPK containerPK = new ContainerPK();
			containerPK.setAssignmentPort(scannedMailDetailsVO.getAirportCode());
			containerPK.setFlightNumber(scannedContainerDetailsVO.getFlightNumber());
			containerPK.setFlightSequenceNumber(scannedContainerDetailsVO.getFlightSequenceNumber());
			containerPK.setLegSerialNumber(scannedContainerDetailsVO.getLegSerialNumber());
			containerPK.setCarrierId(scannedContainerDetailsVO.getCarrierId());
			containerPK.setCompanyCode(scannedContainerDetailsVO.getCompanyCode());
			containerPK.setContainerNumber(scannedContainerDetailsVO.getContainerNumber());
			Container container = null;
			try {
				container = Container.find(containerPK);
			} catch (FinderException e) {
				log.error("Finder Exception Caught");
			}
			String existingDstCode = null;
			if (container != null) {
				existingDstCode = container.getFinalDestination();
			}
			if (existingDstCode != null && !existingDstCode.equals(scannedMailDetailsVO.getDestination())) {
				ScannedMailDetailsVO copyScannedMailDetailsVO = new ScannedMailDetailsVO();
				copyScannedMailDetailsVO = mailOperationsMapper.copyScannedMailDetailsVO( scannedMailDetailsVO);
				final ScannedMailDetailsVO copyScannedMailDetailsVO1 =copyScannedMailDetailsVO;

				Collection<ContainerDetailsVO> containerDetailsVOsFetched = null;
				Collection<ContainerDetailsVO> currentContainerDetailsVOs = new ArrayList<>();
				try {
					currentContainerDetailsVOs.add(constructContainerDetailsVO(scannedMailDetailsVO));
					containerDetailsVOsFetched = findMailbagsInContainer(currentContainerDetailsVOs);
				} finally {
				}
				if (containerDetailsVOsFetched != null && containerDetailsVOsFetched.size() > 0) {
					ContainerDetailsVO containerDetailsVOFetched = containerDetailsVOsFetched.iterator().next();
					copyScannedMailDetailsVO.setMailDetails(containerDetailsVOFetched.getMailDetails());
				} else {
					copyScannedMailDetailsVO.setMailDetails(null);
				}
				if (copyScannedMailDetailsVO.getValidatedContainer().getDestination() == null
						|| MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
					copyScannedMailDetailsVO.getValidatedContainer()
							.setDestination(copyScannedMailDetailsVO.getDestination());
				}
				if (copyScannedMailDetailsVO.getValidatedContainer().getContainerType() == null) {
					copyScannedMailDetailsVO.getValidatedContainer()
							.setContainerType(copyScannedMailDetailsVO.getContainerType());
				}
				if (copyScannedMailDetailsVO.getMailDetails() != null
						&& !copyScannedMailDetailsVO.getMailDetails().isEmpty()) {
					copyScannedMailDetailsVO.setContainerDestChanged(true);
					if (copyScannedMailDetailsVO.getMailSource() != null) {
						ScannedMailDetailsVO finalCopyScannedMailDetailsVO = copyScannedMailDetailsVO;
						copyScannedMailDetailsVO.getMailDetails().forEach(
								mailbagVO -> mailbagVO.setMailSource(copyScannedMailDetailsVO1.getMailSource()));
					}
					if (copyScannedMailDetailsVO.getScannedUser() != null) {
						copyScannedMailDetailsVO.getMailDetails().forEach(
								mailbagVO -> mailbagVO.setScannedUser(copyScannedMailDetailsVO1.getScannedUser()));
					}
					if (copyScannedMailDetailsVO.getOperationTime() != null) {
						copyScannedMailDetailsVO.getMailDetails().forEach(
								mailbagVO -> mailbagVO.setScannedDate(copyScannedMailDetailsVO1.getOperationTime()));
					}
					savereassignFromAndroid(copyScannedMailDetailsVO, logonAttributes);
				} else {
					OperationalFlightVO operationalFlightVO = constructOperationalFlightVO(
							scannedMailDetailsVO.getAirportCode(), scannedMailDetailsVO, logonAttributes);
					ContainerVO containerVO = constructContainerVO(scannedMailDetailsVO.getAirportCode(),
							scannedMailDetailsVO.getValidatedContainer(), logonAttributes,
							scannedMailDetailsVO.getMailSource());
					containerVO.setAcceptanceFlag("Y");
					containerVO.setContainerDestChanged(true);
					Collection<ContainerVO> containersToReassign = new ArrayList<ContainerVO>();
					containersToReassign.add(containerVO);
					try {
						reassignContainers(containersToReassign, operationalFlightVO);
					} catch (FlightClosedException e) {
						constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
					} catch (ContainerAssignmentException e) {
						constructAndRaiseException(MailMLDBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
								MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION, scannedMailDetailsVO);
					} catch (InvalidFlightSegmentException e) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_FLIGHT_SEGMENT,
								MailHHTBusniessException.INVALID_FLIGHT_SEGMENT, scannedMailDetailsVO);
					} catch (ULDDefaultsProxyException e) {
						constructAndRaiseException(MailMLDBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
								MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION, scannedMailDetailsVO);
					} catch (CapacityBookingProxyException e) {
						constructAndRaiseException(MailMLDBusniessException.CAPACITY_BOOKING_EXCEPTION,
								MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION, scannedMailDetailsVO);
					} catch (MailBookingException e) {
						constructAndRaiseException(MailMLDBusniessException.MAIL_BOOKING_EXCEPTION,
								MailHHTBusniessException.MAIL_BOOKING_EXCEPTION, scannedMailDetailsVO);
					} catch (MailDefaultStorageUnitException ex) {
						constructAndRaiseException(
								MailMLDBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
								MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
								scannedMailDetailsVO);
					}
				}
			}
		}
	}

	/**
	* @author A-5526 Added for bug IASCB-65590
	* @param mailbagInULDForSegmentVO
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	private boolean checkATAisCapturedForTheManifestedFlight(MailbagInULDForSegmentVO mailbagInULDForSegmentVO,
			String airportCode, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ZonedDateTime scanDate = scannedMailDetailsVO.getOperationTime();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagInULDForSegmentVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
		flightFilterVO.setAirportCode(airportCode);
		Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
		String validateImportHandling = findSystemParameterValue(SYSPAR_IMPORT_HANDL_VALIDATION);
		Collection<String> parCodes = new ArrayList<>();
		parCodes.add(SHARED_ARPPAR_ONLARP);
		Map<String, String> arpMap = findAirportParameterValue(scannedMailDetailsVO.getCompanyCode(),
				flightValidationVOs.iterator().next().getLegOrigin(), parCodes);
		String onlineArpParamater = arpMap.get(SHARED_ARPPAR_ONLARP);
		for (FlightValidationVO flightValidation : flightValidationVOs) {
			if (airportCode.equals(flightValidation.getLegDestination())) {
				if ((MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint()))
						&& "Y".equals(validateImportHandling) && ("Y").equals(onlineArpParamater)
						&& flightValidationVOs.iterator().next().getAtd() == null) {
					constructAndRaiseException(MailConstantsVO.MAIL_OPERATIONS_ATD_MISSING,
							MailHHTBusniessException.MAIL_OPERATIONS_ATD_MISSING, scannedMailDetailsVO);
				} else {
					return (flightValidation.getAta() != null && flightValidation.getAta().toZonedDateTime().isBefore(scanDate))
							|| (flightValidation.getAta() == null && flightValidation.getEta() != null
									&& flightValidation.getEta().toZonedDateTime().isBefore(scanDate))
							|| (flightValidation.getAta() == null && flightValidation.getEta() == null
									&& flightValidation.getSta() != null
									&& flightValidation.getSta().toZonedDateTime().isBefore(scanDate));
				}
			}
		}
		return false;
	}

	/**
	* Method		:	MailUploadController.updateInboundFlightDetails Added by 	:	U-1416 on Aug 10, 2020 Used for 	: Parameters	:	@param logonAttributes Parameters	:	@param mailbagVo Parameters	:	@param uploadedMaibagVO Parameters	:	@param scannedMailDetailsVO  Return type	: 	void
	*/
	private void updateInboundFlightDetails(LoginProfile logonAttributes, MailbagVO mailbagVo,
			MailUploadVO uploadedMaibagVO, ScannedMailDetailsVO scannedMailDetailsVO) {
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType())) {
			if (mailbagVo.getOrigin() != null && !mailbagVo.getOrigin().trim().isEmpty()) {
				if (mailbagVo.getOrigin().equals(logonAttributes.getAirportCode())) {
					uploadedMaibagVO.setTransferFrmFlightDate(null);
					uploadedMaibagVO.setTransferFrmFlightNum(null);
					uploadedMaibagVO.setTransferFrmFlightSeqNum(0);
					scannedMailDetailsVO.setTransferFrmFlightDate(null);
					scannedMailDetailsVO.setTransferFrmFlightNum(null);
					scannedMailDetailsVO.setTransferFrmFlightSeqNum(0);
					scannedMailDetailsVO.setTransferFromCarrier(null);
				}
			}
		}
	}

	/**
	* @author A-8353
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	public void doAcceptanceAfterErrors(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		if (scannedMailDetailsVO.isMailbagValidationRequired()) {
			validateMailBags(scannedMailDetailsVO);
		}
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
			mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
			mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			mailbagVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
			mailbagVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
			mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			mailbagVO.setUldNumber(null);
			mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
			mailbagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
			mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			mailbagVO.setMailbagDataSource(scannedMailDetailsVO.getMailSource());
			MailAcceptance.populatePrimaryAcceptanceDetails(mailbagVO);
			populateMailPKFields(mailbagVO);
			mailbagVOs.add(mailbagVO);
			insertOrUpdateMailDetails(mailbagVO);
			try {
				PersistenceController.getEntityManager().flush();
			} catch (PersistenceException e) {
				e.getMessage();
			}
			mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			scannedMailDetailsVO.setFlightNumber(mailbagVO.getFlightNumber());
			scannedMailDetailsVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			scannedMailDetailsVO.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
			MailAcceptanceVO mailAcceptanceVO = null;
			boolean hasFlightDeparted = false;
			LoginProfile logonAttributes = null;
			try {
				logonAttributes = contextUtil.callerLoginProfile();
			} finally {
			}
			try {
				mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
			} finally {
			}
			try {
				mailAcceptance.flagResditsForAcceptance(mailAcceptanceVO, scannedMailDetailsVO.getMailDetails(),
						hasFlightDeparted);
				mailbagVO.setUldNumber(null);
				mailbagVO.setContainerNumber(null);
				mailAcceptance.flagHistoryDetailsOfMailbags(mailAcceptanceVO, mailbagVOs);
			} finally {
			}
			try {
				mLDController.flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_REC);
			} finally {
			}
		}
	}

	/**
	* @author A-8353
	*/
	private void insertOrUpdateMailDetails(MailbagVO mailbagVO) {
		long seqNum = 0;
		try {
			seqNum = mailbagVO.getMailSequenceNumber() == 0
					? Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode())
					: mailbagVO.getMailSequenceNumber();
		} finally {
		}
		Mailbag mailBag = null;
		if (seqNum == 0) {
			mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
			try {
				mailBag = new Mailbag(mailbagVO);
			} finally {
			}
			MailbagPK mailBagPK = null;
			if (mailBag != null) {

				mailbagVO.setMailSequenceNumber(mailBag.getMailSequenceNumber());
			}
		} else {
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(seqNum);
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				e.printStackTrace();
			} finally {
			}
			if (mailBag != null) {
				mailBag.setLatestStatus(mailbagVO.getLatestStatus());
				mailBag.setMailbagSource(mailbagVO.getMailbagDataSource());
				mailBag.setFlightNumber(mailbagVO.getFlightNumber());
				mailBag.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				mailBag.setCarrierId(mailbagVO.getCarrierId());
				mailBag.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
				mailBag.setScannedPort(mailbagVO.getScannedPort());
				mailBag.setScannedUser(mailbagVO.getScannedUser());
				mailBag.setScannedDate(mailbagVO.getScannedDate());
				mailBag.setUldNumber(mailbagVO.getUldNumber());
				mailBag.setContainerType(mailbagVO.getContainerType());
				mailBag.setOperationalStatus(mailbagVO.getOperationalStatus());
				if (mailBag.getMailServiceLevel() == null) {
					mailBag.setMailServiceLevel(mailbagVO.getMailServiceLevel());
				}
				mailBag.updatePrimaryAcceptanceDetails(mailbagVO);
			}
		}
	}

	/**
	* @author A-8353
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException
	*/
	private void checkForceAcceptanceRequired(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes) {
		String origin = scannedMailDetailsVO.getMailDetails().iterator().next().getOrigin();
		if (origin != null && (origin.equals(scannedMailDetailsVO.getAirportCode())
				|| (checkforCoterminusAirport(scannedMailDetailsVO, MailConstantsVO.RESDIT_RECEIVED, logonAttributes)))
				&& !(scannedMailDetailsVO.getTransferFrmFlightNum() != null
						&& scannedMailDetailsVO.getTransferFrmFlightNum().trim().length() > 0
						&& scannedMailDetailsVO.getTransferFromCarrier() != null
						&& scannedMailDetailsVO.getTransferFromCarrier().trim().length() > 0
						&& scannedMailDetailsVO.getTransferFrmFlightDate() != null)) {
			scannedMailDetailsVO.setOriginScan(true);
			if (scannedMailDetailsVO.isNotAccepted()) {
				scannedMailDetailsVO.setForceAcpAfterErr(true);
				scannedMailDetailsVO.setMailbagValidationRequired(true);
			}
		}
	}

	/**
	* @author A-8353
	* @param companyCode
	* @param containerNumber
	* @return
	* @throws SystemException
	*/
	private ULDValidationVO validateUldForAcceptance(String companyCode, String containerNumber) {
		ULDValidationVO uldValidationVO = null;
		try {
			uldValidationVO = sharedULDProxy.validateULD(companyCode, containerNumber);
		} catch (SharedProxyException e) {
			log.error(SYSTEM_EXCEPTION_ERROR);
		}
		return uldValidationVO;
	}

	/**
	* Added for CRQ IASCB-74698
	* @author A-5526
	* @param mailUploadVO
	* @throws SystemException
	*/
	public void uploadDocumentsToRepository(MailUploadVO mailUploadVO) {
		DocumentRepositoryFilterVO documentRepositoryFilterVO = null;
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<>();
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVos = new ArrayList<>();
		documentRepositoryFilterVO = populateDocumentRepositoryFilterVO(mailUploadVO);
		try {
			documentRepositoryMasterVOs = documentRepositoryProxy
					.getDocumentsfromRepository(documentRepositoryFilterVO);
		} catch (BusinessException proxyException) {
		}
		if (mailUploadVO.getAttachments() != null && !mailUploadVO.getAttachments().isEmpty()
				&& documentRepositoryMasterVOs.isEmpty()) {
			DocumentRepositoryMasterVO documentRepositoryMasterVO = new DocumentRepositoryMasterVO();
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			String createdUser = mailUploadVO.getAttachments().iterator().next().getUploadUser() != null
					? mailUploadVO.getAttachments().iterator().next().getUploadUser()
					: logonAttributes.getUserId();
			List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = null;
			documentRepositoryMasterVO.setCompanyCode(mailUploadVO.getCompanyCode());
			documentRepositoryMasterVO.setAirportCode(logonAttributes.getAirportCode());
			documentRepositoryMasterVO.setPurpose("DMG");
			documentRepositoryMasterVO.setDocumentType("MAL");
			documentRepositoryMasterVO.setDocumentValue(mailUploadVO.getMailTag());
			documentRepositoryMasterVO.setRemarks("Attachments for Damaged Maibag");
			documentRepositoryMasterVO.setOperationFlag("I");
			documentRepositoryMasterVO.setCreatedUser(createdUser);
			documentRepositoryMasterVO
					.setCreatedTime(new com.ibsplc.icargo.framework.util.time.LocalDate("***", Location.NONE, true));
			documentRepositoryAttachmentVOs = MailOperationsVOConverter
					.convetToDocumentRepositoryAttachmentVOs(mailUploadVO);
			documentRepositoryMasterVO.setAttachments(documentRepositoryAttachmentVOs);
			documentRepositoryMasterVos.add(documentRepositoryMasterVO);
		} else if (!documentRepositoryMasterVOs.isEmpty() && mailUploadVO.getAttachments() != null
				&& !mailUploadVO.getAttachments().isEmpty()) {
			String operationFlag = MailConstantsVO.OPERATION_FLAG_UPDATE;
			for (DocumentRepositoryMasterVO documentRepositoryMasterVO : documentRepositoryMasterVOs) {
				List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = new ArrayList<>();
				DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO = null;
				for (DocumentRepositoryAttachmentVO documentRepositoryAttachmentVo : documentRepositoryMasterVO
						.getAttachments()) {
					documentRepositoryAttachmentVo.setOperationFlag(MailConstantsVO.OPERATION_FLAG_DELETE);
					documentRepositoryAttachmentVOs.add(documentRepositoryAttachmentVo);
				}
				for (MailAttachmentVO mailAttachmentVO : mailUploadVO.getAttachments()) {
					if ("I".equals(mailAttachmentVO.getAttachmentOpFlag())) {
						documentRepositoryAttachmentVO = MailOperationsVOConverter
								.convetToDocumentRepositoryAttachmentVO(mailAttachmentVO, mailUploadVO);
						documentRepositoryAttachmentVOs.add(documentRepositoryAttachmentVO);
						continue;
					}
				}
				documentRepositoryMasterVO.setAttachments(documentRepositoryAttachmentVOs);
				documentRepositoryMasterVO.setOperationFlag(operationFlag);
				documentRepositoryMasterVos.add(documentRepositoryMasterVO);
			}
		}
		try {
			documentRepositoryProxy.uploadMultipleDocumentsToRepository(documentRepositoryMasterVos);
		} catch (BusinessException proxyException) {

		}

	}

	/**
	* Added for CRQ IASCB-74698
	* @author A-5526
	* @param mailuploadVO
	* @return
	* @throws SystemException
	*/
	private DocumentRepositoryFilterVO populateDocumentRepositoryFilterVO(MailUploadVO mailuploadVO) {
		DocumentRepositoryFilterVO documentRepositoryFilterVO = new DocumentRepositoryFilterVO();
		documentRepositoryFilterVO.setCompanyCode(mailuploadVO.getCompanyCode());
		documentRepositoryFilterVO.setDocumentType("MAL");
		documentRepositoryFilterVO.setPurpose("DMG");
		documentRepositoryFilterVO.setDocumentValue(mailuploadVO.getMailTag());

		return documentRepositoryFilterVO;
	}

	private Map<String, String> findAirportParameterValue(String companyCode, String airport,
			Collection<String> parCodes) {
		return sharedAreaProxy.findAirportParametersByCode(companyCode, airport, parCodes);
	}

	public void flagResditForAcceptanceInTruck(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.getMailDetails().iterator().next().setIsFromTruck(null);
		MailAcceptanceVO mailAcceptanceVO = null;
		mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
		boolean hasFlightDeparted = false;
		if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
			if (mailAcceptanceVO.getFlightStatus() == null) {
				hasFlightDeparted = mailAcceptance.checkForDepartedFlight(mailAcceptanceVO);
			} else if (MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(mailAcceptanceVO.getFlightStatus())) {
				hasFlightDeparted = true;
			}
		}
		mailAcceptance.flagResditsForAcceptance(mailAcceptanceVO, scannedMailDetailsVO.getMailDetails(),
				hasFlightDeparted);
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @return
	* @throws SystemException
	* @throws ForceAcceptanceException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	*/
	ScannedMailDetailsVO doSecurityScreeningValidations(ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightDetailsVOs)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.debug("MailUploadController  " + " : " + "doSecurityScreeningValidations" + " Entering");
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			if (!MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
					&& !scannedMailDetailsVO.isNotReqSecurityValAtDel()) {
				checkSecurityScreeningValidationAtMailbagLevel(scannedMailDetailsVO, flightDetailsVOs);
			}
		} else {
			if (cntAsgmntVO != null && !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
					&& !(MailConstantsVO.FLAG_YES).equals(scannedMailDetailsVO.getOverrideValidations())) {
				checkSecurityScreeningValidationAtContainerLevel(scannedMailDetailsVO, flightDetailsVOs);
			}
		}
		log.debug("MailUploadController  " + " : " + "doSecurityScreeningValidations" + " Exiting");
		return scannedMailDetailsVO;
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void checkSecurityScreeningValidationAtContainerLevel(ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightDetailsVOs)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getProcessPoint() != null
				&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				&& scannedMailDetailsVO.getContainerNumber() != null
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			if ((MailConstantsVO.FLAG_YES.equals(cntAsgmntVO.getTransitFlag())
					&& MailConstantsVO.FLAG_NO.equals(cntAsgmntVO.getArrivalFlag()))
					&& cntAsgmntVO.getContainerType() != null
					&& cntAsgmntVO.getContainerType().equals(scannedMailDetailsVO.getContainerType())) {
				checkForSecurityScreeningForContainerAtExport(scannedMailDetailsVO, flightDetailsVOs);
			}
		} else {
			if ((scannedMailDetailsVO.getProcessPoint() != null
					&& MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.FLAG_YES.equals(cntAsgmntVO.getTransitFlag())
					&& MailConstantsVO.FLAG_NO.equals(cntAsgmntVO.getArrivalFlag())
					&& scannedMailDetailsVO.getAirportCode().equals(cntAsgmntVO.getPou()))) {
				checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO, flightDetailsVOs,
						MailConstantsVO.MAIL_STATUS_ARRIVED);
			}
		}
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void checkForSecurityScreeningForContainerAtExport(ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightDetailsVOs)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getAirportCode().equalsIgnoreCase(cntAsgmntVO.getAirportCode())) {
			checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO, flightDetailsVOs,
					MailConstantsVO.MAIL_STATUS_ACCEPTED);
		} else {
			if ((scannedMailDetailsVO.getAirportCode().equals(cntAsgmntVO.getPou())
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType()))) {
				checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO, flightDetailsVOs,
						MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			}
		}
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @throws SystemException
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void checkSecurityScreeningValidationAtMailbagLevel(ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightDetailsVOs)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = null;
		MailbagVO mailbagVo = scannedMailDetailsVO.getMailDetails().iterator().next();
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = populateSecurityScreeningValidationFilterVO(
				scannedMailDetailsVO, flightDetailsVOs, mailbagVo);
		if (securityScreeningValidationFilterVO.isAppRegValReq()) {
			mailController.populateApplicableRegulationFlagValidationDetails(securityScreeningValidationFilterVO,
					mailbagVo.getMailSequenceNumber());
		}
		if (!securityScreeningValidationFilterVO.isAppRegValReq()
				&& securityScreeningValidationFilterVO.isSecurityValNotReq()) {
			return;
		}
		try {
			securityScreeningValidationVOs = mailController
					.checkForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		} catch (BusinessException e) {
			log.info("Exception :", e);
		}
		if (securityScreeningValidationVOs != null && !securityScreeningValidationVOs.isEmpty()) {
			for (SecurityScreeningValidationVO securityScreeningValidationVO : securityScreeningValidationVOs) {
				securityScreeningValidationVO.setMailbagID(mailbagVo.getMailbagId());
				if (checkForSecurityWarningOrError(scannedMailDetailsVO, securityScreeningValidationVO)) {
					break;
				}
			}
		}
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @param mailbagVo
	* @return
	* @throws SystemException
	*/
	private SecurityScreeningValidationFilterVO populateSecurityScreeningValidationFilterVO(
			ScannedMailDetailsVO scannedMailDetailsVO, Collection<FlightValidationVO> flightDetailsVOs,
			MailbagVO mailbagVo) {
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		securityScreeningValidationFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
		securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
		securityScreeningValidationFilterVO.setTransactionAirport(scannedMailDetailsVO.getAirportCode());
		if (mailbagVo.getSecurityStatusCode() != null) {
			securityScreeningValidationFilterVO.setSecurityStatusCode(mailbagVo.getSecurityStatusCode());
		} else {
			securityScreeningValidationFilterVO.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_NSC);
		}
		if (scannedMailDetailsVO.isScreeningPresent()) {
			securityScreeningValidationFilterVO.setSecurityValNotReq(true);
			securityScreeningValidationFilterVO.setSecurityValNotRequired(MailConstantsVO.FLAG_YES);
		}
		securityScreeningValidationFilterVO.setSubClass(mailbagVo.getMailSubclass());
		if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
			securityScreeningValidationFilterVO.setFlightType(flightDetailsVOs.iterator().next().getFlightType());
		}
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ARRIVED);
		} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
				&& flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			securityScreeningValidationFilterVO.setAppRegValReq(true);
			securityScreeningValidationFilterVO.setTransistAirport(scannedMailDetailsVO.getPou());
		} else {
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
				securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				securityScreeningValidationFilterVO.setAppRegValReq(true);
			}
		}
		if (scannedMailDetailsVO.getTransferFromCarrier() != null
				&& scannedMailDetailsVO.getTransferFrmFlightNum() == null
				&& !checkIfPartnerCarrier(scannedMailDetailsVO.getAirportCode(),
						scannedMailDetailsVO.getTransferFromCarrier())) {
			securityScreeningValidationFilterVO.setTransferInTxn(true);
		}
		findCountryCodesForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		return securityScreeningValidationFilterVO;
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param securityScreeningValidationVO
	* @return
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private boolean checkForSecurityWarningOrError(ScannedMailDetailsVO scannedMailDetailsVO,
			SecurityScreeningValidationVO securityScreeningValidationVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		StringBuilder errorString = new StringBuilder();
		String error = errorString.append("Security screening for the particular mailbag is not done  ")
				.append("MailbagID:").append(securityScreeningValidationVO.getMailbagID()).toString();
		if (MailConstantsVO.WARNING.equals(securityScreeningValidationVO.getErrorType())) {
			new MailOperationsValidator().constructAndroidException(error,
					MailConstantsVO.SECURITY_WARNING_VALIDATION, scannedMailDetailsVO);
			return true;
		}
		if (MailConstantsVO.ERROR.equals(securityScreeningValidationVO.getErrorType())) {
			if ("AR".equals(securityScreeningValidationVO.getValidationType())) {
				constructAndRaiseException(MailConstantsVO.APPLICABLE_REGULATION_ERROR,
						MailHHTBusniessException.APPLICABLE_REGULATION_ERROR_DESC, scannedMailDetailsVO);
			} else {
				constructAndRaiseException(error, MailConstantsVO.SECURITY_ERROR_VALIDATION, scannedMailDetailsVO);
			}
		}
		return false;
	}

	/**
	* @author A-8353
	* @param scannedMailDetailsVO
	* @param flightDetailsVOs
	* @param transactionType
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException
	*/
	private void checkforForSecurityScreeningValidationForContainer(ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightDetailsVOs, String transactionType)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setFlightNumber(cntAsgmntVO.getFlightNumber());
		operationalFlightVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		operationalFlightVO.setPol(scannedMailDetailsVO.getAirportCode());
		if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
			operationalFlightVO.setFlightType(flightDetailsVOs.iterator().next().getFlightType());
			operationalFlightVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		} else {
			operationalFlightVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
		}
		Collection<ContainerVO> selectedContainerVOs = new ArrayList<>();
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		containerVO.setFlightNumber(cntAsgmntVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(cntAsgmntVO.getFlightSequenceNumber());
		containerVO.setCarrierId(cntAsgmntVO.getCarrierId());
		containerVO.setLegSerialNumber(cntAsgmntVO.getLegSerialNumber());
		containerVO.setAssignedPort(cntAsgmntVO.getAirportCode());
		containerVO.setContainerNumber(cntAsgmntVO.getContainerNumber());
		containerVO.setType(cntAsgmntVO.getContainerType());
		containerVO.setTransactionCode(transactionType);
		containerVO.setPou(scannedMailDetailsVO.getPou());
		selectedContainerVOs.add(containerVO);
		SecurityScreeningValidationVO securityScreeningValidationVO = new SecurityScreeningValidationVO();
		try {
			securityScreeningValidationVO = mailController
					.doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO, selectedContainerVOs);
			if (securityScreeningValidationVO != null) {
				checkForSecurityWarningOrError(scannedMailDetailsVO, securityScreeningValidationVO);
			}
		} finally {
		}
	}

	/**
	* @author A-8353
	* @param uploadedMaibagVO
	* @param scannedMailDetailsVO
	* @param mailbag
	*/
	void checkForSecValRequiredAtDeliveryAndAppReqFlag(MailUploadVO uploadedMaibagVO,
			ScannedMailDetailsVO scannedMailDetailsVO, Mailbag mailbag) {
		log.debug("MailUploadController " + " : " + "checkSecValRequiredForDelivery" + " Entering");
		if (uploadedMaibagVO.isDeliverd() && scannedMailDetailsVO.getAirportCode() != null
				&& scannedMailDetailsVO.getAirportCode().equals(mailbag.getScannedPort())
				&& (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getLatestStatus()))) {
			scannedMailDetailsVO.setNotReqSecurityValAtDel(true);
			log.info("" + "scannedDetailsVOs" + " " + scannedMailDetailsVO);
		}
		log.debug("MailUploadController " + " : " + "checkSecValRequiredForDelivery" + " Exiting");
	}

	/**
	* @param currentAirport
	* @param paCode
	* @return receiveFromTruckEnabled
	* @throws SystemException
	* @author A-7871Used for ICRD-240184
	*/
	public boolean checkReceivedFromTruckEnabled(String currentAirport, String orginAirport, String paCode) {
		String receiveFromTruckEnabled = null;
		boolean receiveFromTruck = false;
		ZonedDateTime dspDate = localDateUtil.getLocalDate(currentAirport, true);
		receiveFromTruckEnabled = constructDAO().checkReceivedFromTruckEnabled(currentAirport, orginAirport, paCode,
				dspDate);
		if (receiveFromTruckEnabled != null) {
			return true;
		} else {
			return receiveFromTruck;
		}
	}

	public void importMailProvisionalRateData(ScannedMailDetailsVO scannedMailDetailsVO) {
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (provisionalRateimportEnabled != null && MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController
					.createRateAuditVOs(scannedMailDetailsVO);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
	}

	/**
	* @author A-8353
	* @param mailbagVOs
	* @param scannedMailDetailsVO
	* @throws ForceAcceptanceException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	*/
	void checkAppRegFlagValidationForPABuildContainer(Collection<MailbagVO> mailbagVOs,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		HashMap<String, MailbagVO> mailbagDetailsMap = new HashMap<>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			String mailBagKey = mailbagVO.getConsignmentNumber() + mailbagVO.getMailSubclass();
			if (!mailbagDetailsMap.containsKey(mailBagKey)) {
				SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
				securityScreeningValidationFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
				securityScreeningValidationFilterVO.setOriginAirport(scannedMailDetailsVO.getAirportCode());
				securityScreeningValidationFilterVO.setTransactionAirport(scannedMailDetailsVO.getAirportCode());
				securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				if (scannedMailDetailsVO.getPou() != null && scannedMailDetailsVO.getPou().trim().length() > 0) {
					securityScreeningValidationFilterVO.setDestinationAirport(scannedMailDetailsVO.getPou());
					securityScreeningValidationFilterVO.setAppRegDestArp(scannedMailDetailsVO.getPou());
					securityScreeningValidationFilterVO.setTransistAirport(scannedMailDetailsVO.getPou());
				} else {
					securityScreeningValidationFilterVO.setDestinationAirport(scannedMailDetailsVO.getDestination());
					securityScreeningValidationFilterVO.setAppRegDestArp(scannedMailDetailsVO.getDestination());
				}
				securityScreeningValidationFilterVO.setSecurityValNotReq(true);
				securityScreeningValidationFilterVO.setSecurityValNotRequired(MailConstantsVO.FLAG_YES);
				securityScreeningValidationFilterVO.setAppRegValReq(true);
				Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = mailController
						.doApplicableRegulationFlagValidationForPABuidContainer(mailbagVO,
								securityScreeningValidationFilterVO);
				if (securityScreeningValidationVOs != null && !securityScreeningValidationVOs.isEmpty()) {
					for (SecurityScreeningValidationVO securityScreeningValidationVO : securityScreeningValidationVOs) {
						if (checkForSecurityWarningOrError(scannedMailDetailsVO, securityScreeningValidationVO)) {
							break;
						}
					}
				}
				mailbagDetailsMap.put(mailBagKey, mailbagVO);
			}
		}
	}
	public void saveMailScannedDetails(Collection<MailScanDetailVO> mailScanDetailVOs)
			{
		log.info("MailUploadController -> updateMailOperationalstatus");
		for(MailScanDetailVO mailScanDetailVO:mailScanDetailVOs){
			try {
				new MailScanDetail(mailScanDetailVO);
			} catch (SystemException e) {
				log.error(e.getMessage());
			}
		}
	}

	public ScannedMailDetailsVO saveMailUploadDetails(
			Collection<MailUploadVO> mailBagVOs, String scanningPort)
			throws SystemException, MailHHTBusniessException,
			MailMLDBusniessException, MailOperationsBusinessException, ForceAcceptanceException,
			SystemException,  SystemException {

		ScannedMailDetailsVO scannedMailDetailsVO = null;
		if (mailBagVOs != null && mailBagVOs.size() > 0
				&& mailBagVOs.iterator().next().getCompanyCode() != null) {
			scannedMailDetailsVO = groupAndSaveTransactions(mailBagVOs,
					scanningPort);

		}
		return scannedMailDetailsVO;


	}

	/**
	 * Method for Generate Scanned VOs
	 *
	 * @param mailBagVOs
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws  SystemException
	 * @throws ForceAcceptanceException
	 */
	private ScannedMailDetailsVO groupAndSaveTransactions(
			Collection<MailUploadVO> mailBagVOs, String scannedPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			SystemException, ForceAcceptanceException,  SystemException {

		String processPoint = null;
		String container = null;
		String newSessionKey = null;
		final String SEPARATOR = "~";
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		LinkedHashMap<String, Collection<MailUploadVO>> mailUploadVOmap = new LinkedHashMap<String, Collection<MailUploadVO>>();
		Collection<MailUploadVO> collnToAdd = null;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if(mailSeqNum == null){
			mailSeqNum = new HashMap<String,Long>();
		}
		boolean useMailbag = false;
		boolean mailbagPresent = false;
		boolean mailbagNotPresent = false;
		Mailbag  mailbag=null;
		if (mailBagVOs != null && mailBagVOs.size() > 0) {

			for (MailUploadVO mailUploadVO : mailBagVOs) {
				if(mailUploadVO.getMailTag()!=null && mailUploadVO.getMailTag().trim().length()==29){
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(mailUploadVO.getCompanyCode());

					long sequencenum=Mailbag.findMailBagSequenceNumberFromMailIdr(mailUploadVO.getMailTag(), mailUploadVO.getCompanyCode());
					if(sequencenum >0){
						mailbagPk.setMailSequenceNumber(sequencenum);
						mailSeqNum.put(mailUploadVO.getMailTag(), sequencenum);
						try {
							mailbag=Mailbag.find(mailbagPk);
							if(!"NEW".equalsIgnoreCase(mailbag.getLatestStatus()))
							{
								mailbagPresent = true;
							}
						} catch (SystemException e) {
							//e.printStackTrace();
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
							log.info( SYSTEM_EXCEPTION_ERROR);
						} catch (FinderException e) {
							mailbagNotPresent = true;
							log.info( SYSTEM_EXCEPTION_ERROR);
						}catch(Exception e){
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
						}
					}
					//Added by A-7540 as a part ICRD-212734 starts
					else{
						mailbagNotPresent = true;
					}
					//Added by A-7540 as a part ICRD-212734 ends
					if("WS".equals(mailUploadVO.getMailSource()) && "RSN".equals(mailUploadVO.getScanType())){
						if(mailbagNotPresent){
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
									MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
						}
					}
				}
			}

			//Added as part of CR ICRD-89077 starts
			validateForMLDMessages(mailBagVOs,mailbagPresent,mailbagNotPresent,mailbag);


			//Added as part of CR ICRD-89077 ends
			if(mailbagPresent&&mailbagNotPresent){
				useMailbag = true;
			}

			for (MailUploadVO mailUploadVO : mailBagVOs) {
				processPoint = mailUploadVO.getScanType();

				if (mailUploadVO.getContainerNumber() != null
						&& mailUploadVO.getContainerNumber().trim().length() > 0) {
					container = mailUploadVO.getContainerNumber();
				} else {
					container = mailUploadVO.getToContainer();
				}

				// To be corrected issue comes if one maibag is present and another is not present the process points
				//will be different mail tag to be considered in the key if such a situation coding for that is to be done




				// Copied

				if (useMailbag) {

					newSessionKey = new StringBuilder(processPoint)
							.append(SEPARATOR)
							.append(mailUploadVO.getCarrierCode())
							.append(SEPARATOR)
							.append(mailUploadVO.getFlightNumber())
							.append(SEPARATOR)
							.append(mailUploadVO.getFlightDate())
							.append(SEPARATOR)
							.append(mailUploadVO.getDestination())
							.append(SEPARATOR).append(container)
							.append(SEPARATOR)
							.append(mailUploadVO.getMailTag()).toString();
				}else{
					newSessionKey = new StringBuilder(processPoint)
							.append(SEPARATOR)
							.append(mailUploadVO.getCarrierCode())
							.append(SEPARATOR)
							.append(mailUploadVO.getFlightNumber())
							.append(SEPARATOR)
							.append(mailUploadVO.getFlightDate())
							.append(SEPARATOR)
							.append(mailUploadVO.getDestination())
							.append(SEPARATOR).append(container).toString();
				}

				if (mailUploadVOmap.containsKey(newSessionKey)) {
					collnToAdd = mailUploadVOmap.get(newSessionKey);
					collnToAdd.add(mailUploadVO);
					mailUploadVOmap.put(newSessionKey, collnToAdd);
				} else {
					collnToAdd = new ArrayList<MailUploadVO>();
					if (mailUploadVO.getContainerNumber() == null
							|| mailUploadVO.getContainerNumber().trim()
							.length() == 0) {
						mailUploadVO.setContainerNumber(container);
					}
					collnToAdd.add(mailUploadVO);
					mailUploadVOmap.put(newSessionKey, collnToAdd);
				}
			}

			Collection<Collection<MailUploadVO>> mailuploadVOsCol =
					(Collection<Collection<MailUploadVO>>) mailUploadVOmap.values();
			for (Collection<MailUploadVO> mailuploadvos : mailuploadVOsCol) {
				if (mailuploadvos != null && mailuploadvos.size() > 0) {
					scannedMailDetailsVO = constructVOAndSaveDetails(
							mailuploadvos, logonAttributes, scannedPort);
				}
			}
		}
		return scannedMailDetailsVO;
	}
	private void validateForMLDMessages(Collection<  MailUploadVO> mailBagVOs,
									   boolean mailbagPresent, boolean mailbagNotPresent, Mailbag mailbag) throws MailMLDBusniessException,  SystemException {
		//Added as part of Bug ICRD-143638 by A-5526 starts.Add throws System Exception to this methos and its parent methods
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();

		//Added as part of Bug ICRD-143638 by A-5526 ends


		if (mailbagPresent &&
				  MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())&&
				  MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())) {

			throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_PRESENT);
		}
		//Modified as part of Bug ICRD-143638 by A-5526 for MLD-ALL message

		//Added as part of ICRD-134563 by A-8488 starts
		if (  MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())&&
				mailbagNotPresent &&
				  MailConstantsVO.MLD_TFD.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())) {
			throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_PRESENT_FOR_TFD);
		}
		//Added as part of ICRD-134563 by A-8488 ends
		//Modified by A-8488 as part of ICRD-134563
		if(  MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())&&
				(  MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())
						||   MailConstantsVO.MLD_RCF.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())
						||   MailConstantsVO.MLD_STG.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint()))&&
				(mailBagVOs.iterator().next().getContainerNumber()==null || mailBagVOs.iterator().next().getContainerNumber().trim().length()==0)
		) {
			if(mailbagPresent && mailbag!=null &&
					mailbag.getFlightNumber()!=null && mailbag.getFlightNumber().equals(mailBagVOs.iterator().next().getFlightNumber()) &&
					mailbag.getScannedPort()!=null && mailbag.getScannedPort().equals(mailBagVOs.iterator().next().getContainerPol())
			){
				mailBagVOs.iterator().next().setContainerNumber(mailbag.getUldNumber());
				mailBagVOs.iterator().next().setContainerType(mailbag.getContainerType());

			}else if (mailbagPresent && mailbag!=null &&   MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailBagVOs.iterator().next().getScanType())){
				mailBagVOs.iterator().next().setToContainer(mailbag.getUldNumber());
				mailBagVOs.iterator().next().setContainerType(mailbag.getContainerType());
				mailBagVOs.iterator().next().setDestination(mailbag.getDestination());
			}
			else{
				mailBagVOs.iterator().next().setContainerNumber("BULK-" + mailBagVOs.iterator().next().getContainerPOU());
				mailBagVOs.iterator().next().setContainerType(  MailConstantsVO.BULK_TYPE);

			}
		}
		//Added as part of ICRD-134563 by A-8488 starts
		if(  MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())&&
				  MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())
				&&   MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(mailBagVOs.iterator().next().getScanType())){
			if (mailbag != null && !mailbag.getLatestStatus().equals("NEW")
					&&!  MailConstantsVO.BULK_TYPE.equals(mailbag.getContainerType())
					&& mailBagVOs.iterator().next().getContainerType() == null){
				mailBagVOs.iterator().next().setContainerType(mailbag.getContainerType());
				mailBagVOs.iterator().next().setContainerNumber(mailbag.getUldNumber());
			}
		}
		//Modified as part of ICRD-343603 by A-8488
		if(  MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())&&
				mailbag!=null &&   MailConstantsVO.MLD_TFD.equals(mailBagVOs.iterator().next().getProcessPoint())
				&& !  MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getLatestStatus())){
			boolean isAutoArrivalEnabled = mailController.isAutoArrivalEnabled(  MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			if (!isAutoArrivalEnabled) {
				if (  MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getLatestStatus())) {
					throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION);

				}
				if (mailBagVOs.iterator().next().getFlightNumber() == null) {
					throw new MailMLDBusniessException(MailMLDBusniessException.TRANSFER_WITHOUT_ARRIVAL);
				}
			}

			//Added as part of ICRD-134563 by A-8488 ends
		}

	}
	private ScannedMailDetailsVO constructVOAndSaveDetails(
			Collection<MailUploadVO> uploadedMaibagVos,
			LoginProfile logonAttributes, String scannedPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException, ForceAcceptanceException,  SystemException {
		log.info("uploadedMaibagVos", uploadedMaibagVos);
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailErrors = new ArrayList<MailbagVO>();

		if (scannedMailDetailsVO.getErrorMailDetails() == null
				|| scannedMailDetailsVO.getErrorMailDetails().size() == 0) {
			scannedMailDetailsVO.setErrorMailDetails(mailErrors);
		}
		Collection<MailbagVO> mailbagVosTempForOffload = new ArrayList<MailbagVO>();
		// Iterate the Collection each MailBag data is set in maibagVoDetails
		for (MailUploadVO uploadedMaibagVO : uploadedMaibagVos) {
			MailbagVO mailbagVOToSave = new MailbagVO();
			String airportCode = (MailConstantsVO.MLD.equals(uploadedMaibagVO
					.getMailSource())|| MailConstantsVO.MRD.equals(uploadedMaibagVO
					.getMailSource())|| MailConstantsVO.WS.equals(uploadedMaibagVO
					.getMailSource()))? scannedPort : logonAttributes
					.getAirportCode();
			if ("EXCELUPL".equals(uploadedMaibagVO.getMailSource())){
				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(uploadedMaibagVO.getScanType()))
				{
					airportCode=uploadedMaibagVO.getContainerPOU();
					scannedPort=uploadedMaibagVO.getContainerPOU();
				}else
				{
					airportCode = uploadedMaibagVO.getContainerPol();
					scannedPort = uploadedMaibagVO.getContainerPol();
				}
			}

			/*Added by A-5166 for ISL airport change*/
			if (MailConstantsVO.MTKMALUPLJOB.equals(uploadedMaibagVO.getMailSource())) {
				if (scannedPort != null) {
					airportCode = scannedPort;
				}
			}

			makeScannedMailDetailsVO(scannedMailDetailsVO,mailbagVOToSave, uploadedMaibagVO,
					logonAttributes,scannedPort);

			log.info("After makeScannedMailDetailsVO :: scannedMailDetailsVO", scannedMailDetailsVO);
			try {
				new MailOperationsValidator().doFlightAndCarrierValidations(scannedMailDetailsVO);
			} catch ( SystemException e1) {
				log.info( SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e1.getMessage(), e1.getMessage(), scannedMailDetailsVO);
			} catch (MailHHTBusniessException e){
				log.info( "MailHHTBusniessException Caught");
				//TODO:Neo to correct
				//constructAndRaiseException(e.getErrors().iterator().next().getErrorCode(), e.getErrors().iterator().next().getConsoleMessage(), scannedMailDetailsVO);
				constructAndRaiseException(e.getErrors().iterator().next().getErrorCode(), e.getErrors().iterator().next().getErrorCode(), scannedMailDetailsVO);
			} catch (MailMLDBusniessException e) {
				if (MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT.equals(e.getErrors().iterator().next().getErrorCode())
						&&  MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
						&&  MailConstantsVO.MLD_TFD.equals(uploadedMaibagVO.getProcessPoint())) {
					uploadedMaibagVO.setToFlightDate(null);
					uploadedMaibagVO.setToFlightNumber(null);
				} else {
					constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
				}
			}
			catch(Exception e){
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
			//updating VO with info from existing data if required
			validateFlightAndUpdateVO(mailbagVOToSave, scannedMailDetailsVO,
					logonAttributes, airportCode);
			log.info("After validateFlightAndUpdateVO :: scannedMailDetailsVO", scannedMailDetailsVO);

			updateCarrierAcceptanceDetails(scannedMailDetailsVO,mailbagVOToSave);

			log.info( "After updateCarrierAcceptanceDetails :: scannedMailDetailsVO", scannedMailDetailsVO);
			//Added as part of bug ICRD-136371
			validateULDFormatAndUpdate(scannedMailDetailsVO,uploadedMaibagVO);

			validateContainerAndUpdateVO(mailbagVOToSave, scannedMailDetailsVO,
					logonAttributes, scannedPort);
			log.info( "After validateContainerAndUpdateVO :: scannedMailDetailsVO", scannedMailDetailsVO);
			//Since flight is not validated for Exp event as its flightnum and carrier code are null at the time of above doFlightAndCarrierValidations()
//Added by A-5945 for ICRD-104714
			if ( MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())){
				try {
					new MailOperationsValidator().doFlightAndCarrierValidations(scannedMailDetailsVO);
				} catch (SystemException e1) {
					constructAndRaiseException(e1.getMessage(), "SystemException Exception", scannedMailDetailsVO);

				}
			}
			ScannedMailDetailsVO scannedDetailsVOForContainerTxn = new ScannedMailDetailsVO();
			if (MailConstantsVO.CONTAINER_STATUS_PREASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())||
					MailConstantsVO.CONTAINER_STATUS_REASSIGN.equals(scannedMailDetailsVO.getContainerProcessPoint())||
					MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())){
				try {
					scannedDetailsVOForContainerTxn = mailOperationsMapper.copyScannedMailDetailsVO(scannedMailDetailsVO);
				} catch (SystemException e2) {

					constructAndRaiseException(e2.getMessage(), e2.getMessage(), scannedMailDetailsVO);
				}
				catch(Exception e){
					constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
				}
			}

			/*------------added as part of IASCB-108989-start here---------------------*/
			if (mailbagVOToSave.getOrigin() == null || mailbagVOToSave.getOrigin().isEmpty()) {
				mailbagVOToSave.setOrigin(scannedMailDetailsVO.getPol());
			}

			if (mailbagVOToSave.getDestination() == null || mailbagVOToSave.getDestination().isEmpty()) {
				mailbagVOToSave.setDestination(scannedMailDetailsVO.getDestination());
			}
			/*------------added as part of IASCB-108989-end here-----------------------*/

			if(uploadedMaibagVO.getContainerJourneyId()!=null){
				uploadedMaibagVO.setScannedDate(scannedMailDetailsVO.getOperationTime());
				mailbagVOs = findMailbagsFromCarditsForJourneyId(uploadedMaibagVO);
				scannedMailDetailsVO.setOperationFlag("I");
				scannedMailDetailsVO.setMailDetails(mailbagVOs);
				if( MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())){
					scannedMailDetailsVO.setIsContainerPabuilt( MailConstantsVO.FLAG_YES);
				}
			}else{
				mailbagVOs.add(mailbagVOToSave);
				scannedMailDetailsVO.setOperationFlag("I");
				scannedMailDetailsVO.setMailDetails(mailbagVOs);
			}
			if("WS".equals(scannedMailDetailsVO.getMailSource()) &&  MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())){
				mailbagVosTempForOffload.addAll(scannedMailDetailsVO.getMailDetails());
			}


			try {
				performContainerAsSuchOperations(scannedMailDetailsVO,scannedDetailsVOForContainerTxn,mailbagVOToSave, scannedMailDetailsVO,
						logonAttributes, scannedPort);
			} catch (SystemException e) {
				log.info(SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			} catch (MailHHTBusniessException e){
				log.info( "MailHHTBusniessException Caught");
				//TODO: Neo to check e.getErrors().iterator().next().getConsoleMessage()
				//constructAndRaiseException(e.getErrors().iterator().next().getErrorCode(), e.getErrors().iterator().next().getConsoleMessage(), scannedMailDetailsVO);
				constructAndRaiseException(e.getErrors().iterator().next().getErrorCode(), e.getErrors().iterator().next().getErrorCode(), scannedMailDetailsVO);
			}
			catch(Exception e){
				log.info(SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}

			updateProcessPoint(scannedMailDetailsVO,scannedPort);
			log.info( "After updateProcessPoint :: scannedMailDetailsVO", scannedMailDetailsVO);


			try {
				updateVOForSpecificOperations(scannedMailDetailsVO, mailbagVOToSave,
						uploadedMaibagVO, logonAttributes);
			} catch ( SystemException e) {
				log.info( SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
			catch(Exception e){
				log.info( SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
			log.info( "After updateVOForSpecificOperations :: scannedMailDetailsVO", scannedMailDetailsVO);
			//Code added by Manish for ICRD-184665 AND ICRD-184232 START
			if( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())){
				if(uploadedMaibagVO.getFromPol() != null && uploadedMaibagVO.getFromPol().trim().length() > 0){
					 ContainerAssignmentVO containerAssignmentVO = null;
					if (!"B".equals(scannedMailDetailsVO.getContainerType())
							&& scannedMailDetailsVO.getContainerNumber() != null) {
						try {
							if ( MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
									&&  MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
									&& ( MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
									|| (scannedMailDetailsVO.getMailDetails() == null
									|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {

								containerAssignmentVO = this.cntAsgmntVO == null ?findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO) :this.cntAsgmntVO;
								this.cntAsgmntVO = containerAssignmentVO;
							} else {

								containerAssignmentVO = mailController
										.findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
							}
						} catch (SystemException e) {
							log.info( "System exception",e);
						}
					}
					scannedMailDetailsVO.setPol(uploadedMaibagVO.getFromPol());
					if(scannedMailDetailsVO.getValidatedContainer() != null)
					{
						scannedMailDetailsVO.getValidatedContainer().setPol(uploadedMaibagVO.getFromPol());
					}
					if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
						 MailbagVO mailbagVO=scannedMailDetailsVO.getMailDetails().iterator().next();
						mailbagVO.setPol(uploadedMaibagVO.getFromPol());

					}
					uploadedMaibagVO.setContainerPol(uploadedMaibagVO.getFromPol());
					uploadedMaibagVO.setContainerPOU(scannedMailDetailsVO.getPou());
					scannedMailDetailsVO.setFromPolExist(true);
					try{
						Collection< MailUploadVO> uplVOs = new ArrayList< MailUploadVO>();
						uplVOs.add(uploadedMaibagVO);
						validateFlightAndSegmentsForMLD(uplVOs);
					}catch(Exception exception){
						log.info("Exception Caught");
						constructAndRaiseException(MailHHTBusniessException.INVALID_FLIGHT_POL, MailHHTBusniessException.INVALID_FLIGHT_POL.concat(uploadedMaibagVO.getPols()), scannedMailDetailsVO);
					}
					if( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()) &&
							containerAssignmentVO != null && scannedMailDetailsVO.getPol() != null ){
						if(!(scannedMailDetailsVO.getPol().equals(containerAssignmentVO.getAirportCode()))){
							constructAndRaiseException(
									MailHHTBusniessException.INVALID_POL,
									MailHHTBusniessException.CONTAINER_ALREADY_EXISTS.concat(
											containerAssignmentVO.getAirportCode() != null ? containerAssignmentVO.getAirportCode() : ""),
									scannedMailDetailsVO);
						}
					}
				}else if(scannedMailDetailsVO.getPols() != null && !scannedMailDetailsVO.getPols().isEmpty()){
					ArrayList<String> pols = (ArrayList<String>)scannedMailDetailsVO.getPols();
					scannedMailDetailsVO.setPol(pols.get(0));
					if(scannedMailDetailsVO.getValidatedContainer() != null)
					{
						scannedMailDetailsVO.getValidatedContainer().setPol(pols.get(0));
					}
					uploadedMaibagVO.setContainerPol(pols.get(0));
				}
			}
			//Code added by Manish for ICRD-184665 AND ICRD-184232 END
		}// For loop end

		try {

			new MailOperationsValidator().validateScannedMailDetails(scannedMailDetailsVO);
			log.info( "After MailtrackingDefaultsValidator().validateScannedMailDetails :: scannedMailDetailsVO", scannedMailDetailsVO);

			//Modified for ICRD-211902	by 	A-7540
			// Modified by A-8488 as part of bug ICRD-323461
			if((scannedMailDetailsVO.getMailDetails()!=null
					||(( MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerArrivalFlag())||  MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag()))
					&& scannedMailDetailsVO.getContainerNumber()!=null)) && !"WS".equals(scannedMailDetailsVO.getMailSource())) {



				saveAndProcessMailBags(scannedMailDetailsVO);

			}else if("WS".equals(scannedMailDetailsVO.getMailSource())){
				return scannedMailDetailsVO;
			}


		} catch (RemoteException e) {
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		} catch ( SystemException e) {
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}catch (MailHHTBusniessException mailHHTBusniessException) {
			if (("WS".equals(scannedMailDetailsVO.getMailSource()))&&
					 MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())&&
					MailHHTBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION.equals(mailHHTBusniessException.getMessage())) {

				scannedMailDetailsVO.setMailDetails(mailbagVosTempForOffload);
				scannedMailDetailsVO.setProcessPoint( MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
				try {
					populateContainerDataForAcceptanceGHA(scannedMailDetailsVO,
							logonAttributes);
				} catch ( SystemException e1) {
					log.info( SYSTEM_EXCEPTION_ERROR);
					constructAndRaiseException(e1.getMessage(),e1.getMessage(),
							scannedMailDetailsVO);
				}
				populateFlightDetailsForAcceptanceGHA(scannedMailDetailsVO);
				try {
					updateVOForReassign(scannedMailDetailsVO, logonAttributes);
				} catch (SystemException e) {
					log.info(SYSTEM_EXCEPTION_ERROR);
					constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
				}
				savereassignFromUpload(scannedMailDetailsVO,logonAttributes);
			}
			else {
				throw mailHHTBusniessException;
			}
		}
		catch(Exception e){
			log.info( "Exception Caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}

		return null;
	}
	private void populateFlightDetailsForAcceptanceGHA(
			ScannedMailDetailsVO scannedMailDetailsVO) {

		scannedMailDetailsVO.setFlightNumber("-1");
		scannedMailDetailsVO.setFlightSequenceNumber(-1);
		scannedMailDetailsVO.setLegSerialNumber(-1);
		scannedMailDetailsVO.setFlightStatus(null);
		scannedMailDetailsVO.setFlightDate(null);
		Collection<MailbagVO> mailBagVos = scannedMailDetailsVO
				.getMailDetails();
		if(mailBagVos != null && mailBagVos.size() >0){
			for (MailbagVO mailBagVo : mailBagVos) {
				mailBagVo.setContainerType("B");
				mailBagVo.setLegSerialNumber(-1);
				mailBagVo.setFlightNumber("-1");
				mailBagVo.setFlightSequenceNumber(-1);

			}
		}

	}
	private void populateContainerDataForAcceptanceGHA(
			ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes) throws SystemException {
		String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
		if (scannedMailDetailsVO.getContainerNumber() == null
				|| "".equals(scannedMailDetailsVO.getContainerNumber())) {
			scannedMailDetailsVO.setContainerNumber(storageUnit);
			scannedMailDetailsVO.setContainerType(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.BULK_TYPE);
			scannedMailDetailsVO.setPol(logonAttributes.getAirportCode());
			scannedMailDetailsVO.setPou("SIN");
			// scannedMailDetailsVO.setDestination("SIN");
			Collection<MailbagVO> mailBagVos = scannedMailDetailsVO
					.getMailDetails();
			for (MailbagVO mailBagVo : mailBagVos) {
				mailBagVo.setPol(logonAttributes.getAirportCode());
				mailBagVo.setPou("SIN");
				mailBagVo.setPaBuiltFlag("N");
				mailBagVo.setContainerNumber(storageUnit);
				mailBagVo.setContainerType("B");
				mailBagVo.setLegSerialNumber(-1);
				mailBagVo.setFlightNumber("-1");
				mailBagVo.setFlightSequenceNumber(-1);

			}
			// String mailid =
			// scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId();
			// scannedMailDetailsVO.setSummaryFlag(mailid);
		}
		// scannedMailDetailsVO.setMailDetails(null);

	}
	private void validateFlightAndUpdateVO( MailbagVO mailbagVOToSave,
										    ScannedMailDetailsVO scannedMailDetailsVO,
											LoginProfile logonAttributes, String airportCode) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {



		// Airline validation to set carrier id
		AirlineValidationVO  airlineValidationVO = populateAirlineValidationVO(
				scannedMailDetailsVO.getCompanyCode(),
				scannedMailDetailsVO.getCarrierCode());
		if (airlineValidationVO != null) {
			scannedMailDetailsVO.setCarrierId(airlineValidationVO
					.getAirlineIdentifier());


			if (scannedMailDetailsVO.getCarrierCode() != null
					&& (scannedMailDetailsVO.getFlightNumber() != null && scannedMailDetailsVO
					.getFlightNumber().trim().length() != 0)) {

				if ( MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint()) ||
						 MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()) ||
						 MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()) ||
						( MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint()) && "WS".equals(scannedMailDetailsVO.getMailSource())) ||
						 MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(
								scannedMailDetailsVO.getProcessPoint()) ||
						 MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(
								scannedMailDetailsVO.getProcessPoint()) ||
						 MailConstantsVO.MAIL_STATUS_EXPORT.equals(
								scannedMailDetailsVO.getProcessPoint())) {

					Collection<FlightValidationVO> flightDetailsVOs = null;
					if(scannedMailDetailsVO.getFlightValidationVOS()!=null && scannedMailDetailsVO.getFlightValidationVOS().size()>0){
						flightDetailsVOs=scannedMailDetailsVO.getFlightValidationVOS();
					}else{
						FlightFilterVO flightFilterVO = createFlightFilterVO(scannedMailDetailsVO,
								logonAttributes, airportCode);
						try {
							flightDetailsVOs = mailController.validateFlight(flightFilterVO);
						}
						//Modified as part of code quality work by A-7531
						catch (Exception e) {
							log.info("Exception caught");
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);

						}
					}
					log.info("flightValidationVOs", flightDetailsVOs);
					if (flightDetailsVOs != null) {
						for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
							if(FlightValidationVO.FLT_STATUS_ACTIVE.equals(flightValidationVO.getFlightStatus())) {
								if (( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(
										scannedMailDetailsVO.getProcessPoint()) &&
										flightValidationVO.getLegDestination().equals(airportCode)) ||
										flightValidationVO.getLegOrigin().equals(airportCode) ||
										( MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint()) && "WS".equals(scannedMailDetailsVO.getMailSource()))) {
									log.info( "VO is", flightValidationVO);
									updateFlightDetails(scannedMailDetailsVO,
											mailbagVOToSave, flightValidationVO);
									String route = flightValidationVO.getFlightRoute();
									String[] routeArr = route.split("-");
									ArrayList<String> pols = new ArrayList<String>();
									for(String airport: routeArr){
										if(!airport.equals(scannedMailDetailsVO.getAirportCode())){
											pols.add(airport);
										}else{
											break;
										}
									}
									if(!pols.isEmpty())
									{
										Collections.reverse(pols);
									}
									scannedMailDetailsVO.setPols(pols);
									break;
								}
							}
						}
					}

				}
			}
		}
	}
	private void updateFlightDetails(ScannedMailDetailsVO scannedMailDetailsVO,
									 MailbagVO mailbagVOToSave, FlightValidationVO flightValidationVO) {
		scannedMailDetailsVO.setFlightSequenceNumber(flightValidationVO
				.getFlightSequenceNumber());
		scannedMailDetailsVO.setLegSerialNumber(flightValidationVO
				.getLegSerialNumber());
		scannedMailDetailsVO.setCarrierId(flightValidationVO
				.getFlightCarrierId());
		scannedMailDetailsVO
				.setCompanyCode(flightValidationVO.getCompanyCode());
		mailbagVOToSave.setFlightSequenceNumber(flightValidationVO
				.getFlightSequenceNumber());
		mailbagVOToSave.setLegSerialNumber(flightValidationVO
				.getLegSerialNumber());
		mailbagVOToSave.setCarrierId(flightValidationVO.getFlightCarrierId());
		mailbagVOToSave.setCarrierCode(flightValidationVO.getCarrierCode());
		mailbagVOToSave.setFlightNumber(flightValidationVO.getFlightNumber());
		mailbagVOToSave.setFlightDate(scannedMailDetailsVO.getFlightDate());
		mailbagVOToSave.setCompanyCode(flightValidationVO.getCompanyCode());
	}
	private void makeScannedMailDetailsVO(
			ScannedMailDetailsVO scannedMailDetailsVO,
			MailbagVO mailbagVOToSave, MailUploadVO uploadedMaibagVO,
			LoginProfile logonAttributes, String scannedPort) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {

		// Changed by A-5945 as part of ICRD-85550
		if (uploadedMaibagVO.getScannedPort() == null) {
			uploadedMaibagVO.setScannedPort(scannedPort);
		}
		if("EXCELUPL".equals(uploadedMaibagVO.getMailSource())){
			uploadedMaibagVO.setScannedPort(scannedPort);
		}
		initializeVO(scannedMailDetailsVO, uploadedMaibagVO,
				logonAttributes);
		setFlightDetails(scannedMailDetailsVO, uploadedMaibagVO);
		setContainerDetails(scannedMailDetailsVO, uploadedMaibagVO);
		setPolPouDestinationDetails(scannedMailDetailsVO, uploadedMaibagVO,
				scannedPort);
		setMailbagDetails(mailbagVOToSave, uploadedMaibagVO);
		setCarrierAndFlightDetails(mailbagVOToSave, uploadedMaibagVO,
				scannedMailDetailsVO);
		setContainerDetails(mailbagVOToSave, uploadedMaibagVO,
				scannedMailDetailsVO);
		setPolPouDetails(mailbagVOToSave, uploadedMaibagVO);
		setScanInformation(mailbagVOToSave, uploadedMaibagVO, scannedPort,
				scannedMailDetailsVO);
		setDamageAndOffloadDetails(mailbagVOToSave,uploadedMaibagVO);

	}
	private void setScanInformation(MailbagVO mailbagVOToSave,
									MailUploadVO uploadedMaibagVO, String scannedPort,
									ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		mailbagVOToSave.setScannedPort(scannedPort);
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();

		} catch (SystemException e) {
			log.info("SystemException caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
		catch(Exception e){
			log.info( "SystemException caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
		if (uploadedMaibagVO.getDateTime() != null
				&& uploadedMaibagVO.getDateTime().trim().length() > 0) {
			ZonedDateTime scanDat = null;
			if (scannedPort != null) {
				scanDat = localDateUtil.getLocalDate(scannedPort, true);
			} else {
				scanDat = localDateUtil.getLocalDate(null, false);
			}

			if("MLD".equals(uploadedMaibagVO.getMailSource())){
				scanDat=ZonedDateTime.parse(uploadedMaibagVO.getDateTime().replace('+','T').concat("+00:00"));
				mailbagVOToSave.setScannedDate(scanDat);
			}
			else {
				scanDat = LocalDate.withDateAndTime(scanDat, uploadedMaibagVO.getDateTime());
			String[] time = uploadedMaibagVO.getDateTime().split(" ");
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
			Date date= null;
			try {
				date = sdf.parse(time[0]);
			} catch (ParseException e) {
					log.info(e.getMessage());
			}
			sdf=new SimpleDateFormat("yyyy-MM-dd");
			String scan=sdf.format(date);
			ZonedDateTime scanDateZoned= ZonedDateTime.parse(scan+"T"+time[1]+"+00:00");
			mailbagVOToSave.setScannedDate(scanDateZoned);
			}
			if (uploadedMaibagVO.getScanUser() != null) {
				mailbagVOToSave.setScannedUser(uploadedMaibagVO.getScanUser());
			}else{
				mailbagVOToSave.setScannedUser(logonAttributes.getUserId());
			}
			scannedMailDetailsVO.setOperationTime(scanDat);
		}
		//Added as part of ICRD-144132 by A-5526 starts
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType()) &&
				(uploadedMaibagVO.getFromCarrierCode()!=null && !uploadedMaibagVO.getFromCarrierCode().isEmpty()) &&
				(uploadedMaibagVO.getMailTag()==null || uploadedMaibagVO.getMailTag().isEmpty())){
			scannedMailDetailsVO.setTransferFromCarrier(uploadedMaibagVO.getFromCarrierCode());
		}
		//Added as part of ICRD-144132 by A-5526 ends
		scannedMailDetailsVO.setRemarks(uploadedMaibagVO.getRemarks());

	}
	private void setPolPouDetails(MailbagVO mailbagVOToSave,
								  MailUploadVO uploadedMaibagVO) {
		mailbagVOToSave.setPol(uploadedMaibagVO.getContainerPol());

		mailbagVOToSave.setPou(uploadedMaibagVO.getToPOU());
		if("EXCELUPL".equals(uploadedMaibagVO.getMailSource())){
			mailbagVOToSave.setOrigin(uploadedMaibagVO.getContainerPol());
			mailbagVOToSave.setDestination(uploadedMaibagVO.getToPOU());
		}
		if(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType()) && ( uploadedMaibagVO.getFlightNumber()==null || "".equals(uploadedMaibagVO.getFlightNumber())) ){
			mailbagVOToSave.setPou("");
		}
	}

	private void setCarrierAndFlightDetails(MailbagVO mailbagVOToSave,
											MailUploadVO uploadedMaibagVO,
											ScannedMailDetailsVO scannedMailDetailsVO) {

		if(uploadedMaibagVO.getCompanyCode()!=null && uploadedMaibagVO.getCompanyCode().trim().length()>0){
			mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCompanyCode());
		}
		else
		{
			mailbagVOToSave.setCompanyCode(uploadedMaibagVO.getCarrierCode());
		}
		mailbagVOToSave.setCarrierCode(uploadedMaibagVO.getCarrierCode());
		mailbagVOToSave.setFlightNumber(uploadedMaibagVO.getFlightNumber());
		mailbagVOToSave.setFlightDate(uploadedMaibagVO.getFlightDate());
		mailbagVOToSave.setFlightSequenceNumber(uploadedMaibagVO
				.getFlightSequenceNumber());
		mailbagVOToSave.setCarrierId(uploadedMaibagVO.getCarrierId());
		mailbagVOToSave.setLegSerialNumber(uploadedMaibagVO.getSerialNumber());
		mailbagVOToSave.setTransferFromCarrier(uploadedMaibagVO
				.getFromCarrierCode());
		mailbagVOToSave.setFromCarrier(uploadedMaibagVO.getFromCarrierCode());

		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO
				.getProcessPoint())) {
			mailbagVOToSave.setCarrierCode(uploadedMaibagVO.getToCarrierCode());
			mailbagVOToSave.setFlightNumber(uploadedMaibagVO
					.getToFlightNumber());
			mailbagVOToSave.setFlightDate(uploadedMaibagVO.getToFlightDate());
		}

	}
	private void initializeVO(ScannedMailDetailsVO scannedMailDetailsVO,
							  MailUploadVO uploadedMaibagVO, LoginProfile logonAttributes) {
		final String UNSAVED = "U";
		scannedMailDetailsVO.setExpReassign(false);
		scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		scannedMailDetailsVO.setOwnAirlineCode(logonAttributes
				.getOwnAirlineCode());
		scannedMailDetailsVO.setSavedBags(0);
		scannedMailDetailsVO.setExceptionBagCout(0);
		scannedMailDetailsVO.setDeletedExceptionBagCout(0);
		scannedMailDetailsVO.setStatus(UNSAVED);
		scannedMailDetailsVO.setHasErrors(false);
		scannedMailDetailsVO.setAirportCode(uploadedMaibagVO.getScannedPort());
		scannedMailDetailsVO.setMailSource(uploadedMaibagVO.getMailSource());
		scannedMailDetailsVO.setMessageVersion(uploadedMaibagVO.getMessageVersion());//Added by A-8527 for IASCB-58918
		if(uploadedMaibagVO.getContainerJourneyId()!=null){
			scannedMailDetailsVO.setContainerJourneyId(uploadedMaibagVO.getContainerJourneyId());
		}
		//Added as part of CR ICRD-89077 by A-5526 starts
		if( MailConstantsVO.CONTAINER_STATUS_ARRIVAL.equals(uploadedMaibagVO.getContaineDescription())){
			scannedMailDetailsVO.setContainerArrivalFlag( MailConstantsVO.FLAG_YES);
		}
		else if( MailConstantsVO.CONTAINER_STATUS_DELIVERY.equals(uploadedMaibagVO.getContaineDescription())){
			scannedMailDetailsVO.setContainerDeliveryFlag( MailConstantsVO.FLAG_YES);
		}
		//Added as part of CR ICRD-89077 by A-5526 ends
		if (uploadedMaibagVO.getScanType() != null) {
			scannedMailDetailsVO
					.setProcessPoint(uploadedMaibagVO.getScanType());
		}
		if("WS".equals(uploadedMaibagVO.getMailSource())){
			if(uploadedMaibagVO.isExpRsn()){
				scannedMailDetailsVO.setExpReassign(true);
			}
			scannedMailDetailsVO.setFlightValidationVOS(uploadedMaibagVO.getFlightValidationVOS());

		}
	}

	private void setPolPouDestinationDetails( ScannedMailDetailsVO scannedMailDetailsVO,
											  MailUploadVO uploadedMaibagVO, String airportCode) {


		Page< OfficeOfExchangeVO> pol=null;
		 OfficeOfExchangeVO officeOfExchangeVO=new  OfficeOfExchangeVO();
		try {//added as part of ICRD-293443
			pol=mailController.findOfficeOfExchange(
					uploadedMaibagVO.getCompanyCode(),uploadedMaibagVO.getOrginOE(),1);

		} catch (SystemException e) {
			log.info( SYSTEM_EXCEPTION_ERROR);
		}
		if(pol!=null && !pol.isEmpty()){
			officeOfExchangeVO = pol.iterator().next();
		}

		scannedMailDetailsVO.setPou(uploadedMaibagVO.getToPOU());
		if( MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType()) && ( uploadedMaibagVO.getFlightNumber()==null || "".equals(uploadedMaibagVO.getFlightNumber())) ){
			scannedMailDetailsVO.setPou("");
		}
		if(uploadedMaibagVO.getContainerPol()==null){//modified as part of ICRD-293443
			scannedMailDetailsVO.setPol(officeOfExchangeVO.getAirportCode());
		}else{
			scannedMailDetailsVO.setPol(uploadedMaibagVO.getContainerPol());
		}

		if(! MailConstantsVO.MLD.equalsIgnoreCase(scannedMailDetailsVO.getMailSource())){
			if (( MailConstantsVO.MAIL_STATUS_RECEIVED
					.equalsIgnoreCase(uploadedMaibagVO.getScanType()) ||
					 MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(uploadedMaibagVO.getScanType()) ||
					 MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(uploadedMaibagVO.getScanType()))){
				scannedMailDetailsVO.setPol(airportCode);

			}
		}

		if (uploadedMaibagVO.getDestination() != null) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getDestination());
		} else {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getContainerPOU());
		}

		if ( MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(uploadedMaibagVO.getScanType())) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getToDestination());
		}



		else if(uploadedMaibagVO.getContainerPOU()!=null){
			scannedMailDetailsVO.setPou(uploadedMaibagVO.getContainerPOU());
		}

		if (uploadedMaibagVO.getDestination() != null) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getDestination());
		} else {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getContainerPOU());
		}

		if ( MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(uploadedMaibagVO.getScanType())) {
			scannedMailDetailsVO.setDestination(uploadedMaibagVO.getToDestination());
		}
	}
	private void setMailbagDetails( MailbagVO mailbagVOToSave,
								    MailUploadVO uploadedMaibagVO) throws MailMLDBusniessException  {

		Page< OfficeOfExchangeVO> origin=null;
		Page< OfficeOfExchangeVO> destination=null;
		 OfficeOfExchangeVO originOfficeOfExchangeVO=new  OfficeOfExchangeVO();
		 OfficeOfExchangeVO destinationOfficeOfExchangeVO=new  OfficeOfExchangeVO();
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(uploadedMaibagVO.getCompanyCode());
		Mailbag  mailbag=null;
		long sequencenum=0;
		if(uploadedMaibagVO.getMailTag()!=null&&!uploadedMaibagVO.getMailTag().isEmpty()){
			try {
				sequencenum=uploadedMaibagVO.getMailSequenceNumber() > 0  ? uploadedMaibagVO.getMailSequenceNumber()
						:Mailbag.findMailBagSequenceNumberFromMailIdr(uploadedMaibagVO.getMailTag(), uploadedMaibagVO.getCompanyCode());
			} catch ( SystemException e) {
				log.info( SYSTEM_EXCEPTION_ERROR);
			}
		}
		if(sequencenum >0){
			mailbagPk.setMailSequenceNumber(sequencenum);
			try {
				mailbag=Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				log.info( FINDER_EXCEPTION_CAUGHT);
			} catch ( SystemException e) {
				log.info( SYSTEM_EXCEPTION_ERROR);
			}
		}

		if(mailbag!=null){
			mailbagVOToSave.setOrigin(mailbag.getOrigin());
			mailbagVOToSave.setDestination(mailbag.getDestination());
			mailbagVOToSave.setPaCode(mailbag.getPaCode());
		}else{
			try {
				if(uploadedMaibagVO.getOrginOE()!=null && !uploadedMaibagVO.getOrginOE().isEmpty()){
					origin=mailController.findOfficeOfExchange(
							uploadedMaibagVO.getCompanyCode(),uploadedMaibagVO.getOrginOE(),1);
				}
			} catch ( SystemException e) {
				log.info(SYSTEM_EXCEPTION_ERROR);
			}
			if(origin!=null && !origin.isEmpty()){
				originOfficeOfExchangeVO = origin.iterator().next();
			}else if(uploadedMaibagVO.getOrginOE()!=null && !uploadedMaibagVO.getOrginOE().isEmpty()){
				throw new MailMLDBusniessException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE);
			}
			if(originOfficeOfExchangeVO.getPoaCode()!=null){
				mailbagVOToSave.setPaCode(originOfficeOfExchangeVO.getPoaCode());
			}
			if(originOfficeOfExchangeVO.getAirportCode()!=null){
				mailbagVOToSave.setOrigin(originOfficeOfExchangeVO.getAirportCode());
			}
			try {
				if(uploadedMaibagVO.getDestinationOE()!=null && !uploadedMaibagVO.getDestinationOE().isEmpty()){
					destination=mailController.findOfficeOfExchange(
							uploadedMaibagVO.getCompanyCode(),uploadedMaibagVO.getDestinationOE(),1);
				}
			} catch ( SystemException e) {
				log.info(SYSTEM_EXCEPTION_ERROR);
			}
			if(destination!=null && !destination.isEmpty()){
				destinationOfficeOfExchangeVO = destination.iterator().next();
			}else if(uploadedMaibagVO.getDestinationOE()!=null && !uploadedMaibagVO.getDestinationOE().isEmpty()){

				throw new MailMLDBusniessException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE);
			}
			if(destinationOfficeOfExchangeVO.getAirportCode()!=null){
				mailbagVOToSave.setDestination(destinationOfficeOfExchangeVO.getAirportCode());
			}

		}

		mailbagVOToSave.setMailSource(uploadedMaibagVO.getMailSource());
		mailbagVOToSave.setMessageVersion(uploadedMaibagVO.getMessageVersion());
		mailbagVOToSave.setMailbagId(uploadedMaibagVO.getMailTag());
		mailbagVOToSave.setYear(uploadedMaibagVO.getYear());
		mailbagVOToSave.setOoe(uploadedMaibagVO.getOrginOE());
		mailbagVOToSave.setDoe(uploadedMaibagVO.getDestinationOE());
		mailbagVOToSave.setMailCategoryCode(uploadedMaibagVO.getCategory());
		mailbagVOToSave.setMailSubclass(uploadedMaibagVO.getSubClass());
		mailbagVOToSave.setDespatchSerialNumber(String.valueOf(uploadedMaibagVO
				.getDsn()));
		mailbagVOToSave.setReceptacleSerialNumber(uploadedMaibagVO.getRsn());
		mailbagVOToSave.setRegisteredOrInsuredIndicator(String
				.valueOf(uploadedMaibagVO.getRegisteredIndicator()));
		mailbagVOToSave.setHighestNumberedReceptacle(String
				.valueOf(uploadedMaibagVO.getHighestnumberIndicator()));
		mailbagVOToSave.setWeight(uploadedMaibagVO.getWeight());
		mailbagVOToSave.setAcceptedBags(uploadedMaibagVO.getBags());
		mailbagVOToSave.setScannedDate(uploadedMaibagVO.getScannedDate());//A-5219
		mailbagVOToSave.setMailCompanyCode(uploadedMaibagVO.getMailCompanyCode()); //Added by A-6991 for ICRD-213953
		if(uploadedMaibagVO.getMailTag()!=null && uploadedMaibagVO.getMailTag().length()==29)
		{
			mailbagVOToSave.setDespatchId(uploadedMaibagVO.getMailTag().substring(0, 20));
		}
		if(mailSeqNum != null && !mailSeqNum.isEmpty() && mailSeqNum.containsKey(uploadedMaibagVO.getMailTag()))
		{
			mailbagVOToSave.setMailSequenceNumber(mailSeqNum.get(uploadedMaibagVO.getMailTag()));
		}
	}


	/**
	 * Method to check ULD format validation and treat as bulk if it has any format validation failure
	 * @param uploadedMaibagVO
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws ForceAcceptanceException
	 */
	private void validateULDFormatAndUpdate(
			 ScannedMailDetailsVO scannedMailDetailsVO,  MailUploadVO uploadedMaibagVO) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {

		if((! MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()) &&
				( MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint()) ||  MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())))){
			try {
				if (!new MailOperationsValidator().validateContainerNumber(scannedMailDetailsVO)) {
					/*constructAndRaiseException(
							MailMLDBusniessException.INVALID_ULD_FORMAT,
							MailHHTBusniessException.INVALID_ULD_FORMAT,
							scannedMailDetailsVO);*/

					throw new MailMLDBusniessException(MailMLDBusniessException.INVALID_ULD_FORMAT);

				}
			} catch ( SystemException e) {
				log.info( SYSTEM_EXCEPTION_ERROR);
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
		}

		//Added by A-7540
		else
		{
			Collection<String> systemParameterCodes = new ArrayList<String>();
			systemParameterCodes.add(ULD_AS_BARROW);
			Map<String, String> mailIDFormatMap = null;
			try {
				mailIDFormatMap = neoMastersServiceUtils.findSystemParameterByCodes(
						systemParameterCodes);
			} catch (SystemException | BusinessException e) {
				e.getMessage();
			}
			if(( MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()) &&
					 MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint()) ||
					 MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()))){
				if(mailIDFormatMap != null && !FLAG_YES.equals(mailIDFormatMap.get(ULD_AS_BARROW))) {

					try {
						if (new MailOperationsValidator().validateContainerNumber(scannedMailDetailsVO)) {
						/*constructAndRaiseException(
								MailMLDBusniessException.INVALID_ULD_FORMAT,
								MailHHTBusniessException.INVALID_ULD_FORMAT,
								scannedMailDetailsVO);*/

							uploadedMaibagVO.setContainerType( MailConstantsVO.BULK_TYPE);
							constructAndRaiseException(
									MailMLDBusniessException.INVALID_BULK_FORMAT,
									MailHHTBusniessException.INVALID_BULK_FORMAT,
									scannedMailDetailsVO);
						}
					} catch ( SystemException e) {
						log.info(SYSTEM_EXCEPTION_ERROR);
						constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
					}
				}
			}
		}
	}

	private Collection< MailbagVO> findMailbagsFromCarditsForJourneyId( MailUploadVO uploadedMaibagVO ) throws  SystemException {
		int offsetforPABuilt=0;
		ZonedDateTime scanFromDate = uploadedMaibagVO.getScannedDate()!=null? uploadedMaibagVO.getScannedDate(): localDateUtil.getLocalDate(null, false);
		ZonedDateTime scanToDate = uploadedMaibagVO.getScannedDate()!=null? uploadedMaibagVO.getScannedDate(): localDateUtil.getLocalDate(null, false);
		offsetforPABuilt=Integer.parseInt(findSystemParameterValue(PERIODFOR_PABUILTMAILS));
		ZonedDateTime fromDate=scanFromDate.plusDays(-offsetforPABuilt);
		ZonedDateTime toDate=scanToDate.plusDays(1);
		 ConsignmentFilterVO consignmentFilterVO = new  ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(uploadedMaibagVO.getCompanyCode());
		consignmentFilterVO.setContainerNumber(uploadedMaibagVO.getContainerNumber());
		consignmentFilterVO.setContainerJourneyId(uploadedMaibagVO.getContainerJourneyId());
		consignmentFilterVO.setConsignmentFromDate(fromDate);
		consignmentFilterVO.setConsignmentToDate(toDate);
		return mailController.findCartIds(consignmentFilterVO);
	}
	private void updateProcessPoint(
			 ScannedMailDetailsVO scannedMailDetailsVO, String scannedPort) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		Collection< MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();

		if (mailBagVOs != null) {
			for ( MailbagVO mailBagVOFromUpload : mailBagVOs) {
				// Modified by A-8488 as part of bug ICRD-323461
				if (mailBagVOFromUpload.getMailbagId()==null || mailBagVOFromUpload.getMailbagId().trim().length() == 0) {
					scannedMailDetailsVO.setMailDetails(null);
				}
				else if (mailBagVOFromUpload.getMailbagId().trim().length() == 29) {
					Mailbag mailBag = null;
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(scannedMailDetailsVO
							.getCompanyCode());


					long sequencenum=0;
					try {
						sequencenum = mailBagVOFromUpload.getMailSequenceNumber() == 0 ? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagVOFromUpload.getMailbagId(), scannedMailDetailsVO.getCompanyCode()) : mailBagVOFromUpload.getMailSequenceNumber();
					} catch ( SystemException e1) {
						// TODO Auto-generated catch block
						log.info( SYSTEM_EXCEPTION_ERROR);
					}
					mailbagPk.setMailSequenceNumber(sequencenum);
					try {
						mailBag = Mailbag.find(mailbagPk);
					} catch ( SystemException e) {
						log.info(SYSTEM_EXCEPTION_ERROR);
						constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
						log.info( SYSTEM_EXCEPTION_ERROR);
					} catch (FinderException e) {
						//Modified by A-8488 as part of ICRD-134563
						if (("WS".equals(scannedMailDetailsVO.getMailSource()) ||
								 MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) &&
								 MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
									MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
						}else{
							log.info( FINDER_EXCEPTION_CAUGHT);
						}
					}
					catch(Exception e){
						log.info( SYSTEM_EXCEPTION_ERROR);
						constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
					}
					//Modified by A-8488 as part of ICRD-134563
					if (("WS".equals(scannedMailDetailsVO.getMailSource()) ||
							 MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) &&
							 MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint()) &&
							 MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailBag.getLatestStatus())) {
						constructAndRaiseException( MailConstantsVO.UPLOAD_EXCEPT_ALREADY_RETURNED,
								MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
					}
					if( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()) ||
							 MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint() )){
						scannedMailDetailsVO.setPou(scannedMailDetailsVO.getAirportCode());
						mailBagVOFromUpload.setPou(scannedMailDetailsVO.getAirportCode());
					}
					if (mailBag != null && mailBag.getLatestStatus()!=null && !( MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus()))) {


						 ContainerAssignmentVO containerAssignmentVO = null;
						String containerNumber=null;
						if(scannedMailDetailsVO.getContainerNumber()!=null && scannedMailDetailsVO.getContainerNumber().trim().length()>0){
							containerNumber=scannedMailDetailsVO.getContainerNumber();
						}
						else{
							containerNumber=mailBag.getUldNumber();
						}
						//Added for bug ICRD-95633 by A-5526 starts
						if( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint()) &&
								scannedMailDetailsVO.getContainerType()!=null &&
								 MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
							containerNumber=mailBag.getUldNumber();
						}
						//Added for bug ICRD-95633 by A-5526 ends
						try {
							if ( MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
									&&  MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
									&&  MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
									&& ( MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
									|| (scannedMailDetailsVO.getMailDetails() == null
									|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {

								containerAssignmentVO = this.cntAsgmntVO == null ?findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO) :this.cntAsgmntVO;
								this.cntAsgmntVO = containerAssignmentVO;

							} else {

								containerAssignmentVO = ((this.cntAsgmntVO == null) || !(containerNumber != null && containerNumber.equals(this.cntAsgmntVO.getContainerNumber()))) ?
										findLatestContainerAssignment(containerNumber) : this.cntAsgmntVO;
								this.cntAsgmntVO = containerAssignmentVO;
							}
						}  catch ( SystemException e) {
							log.info( SYSTEM_EXCEPTION_ERROR);

						}
						catch(Exception e){
							log.info( "Exception Caught");
							constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);

						}
						//Added as part of bug ICRD-129281 starts
						if( MailConstantsVO.MAIL_STATUS_RETURNED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())){
							mailBagVOFromUpload.setOperationalStatus(mailBag.getOperationalStatus());
						}
						//Added as part of bug ICRD-129281 ends

						/*Need to be changed this flow as part of one bug fix (ICRD-90140 Arrival+Delivery+damage),
						as similar two transaction is triggered for acceptance plus damage starts */
						if( MailConstantsVO.MAIL_STATUS_DAMAGED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint()) ||  MailConstantsVO.MAIL_STATUS_RETURNED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())){
							updateFlightDetailsForDamageCapture(mailBagVOFromUpload,mailBag,containerAssignmentVO);
						}
						/* Ends */
						/*if(containerAssignmentVO!=null &&
						   MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint()) &&
						   !MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
						}*/

						//Modified by A-8488 as part of ICRD-134563
						if (( MailConstantsVO.MAIL_STATUS_HND.equals(mailBag.getLatestStatus()) ||
								 MailConstantsVO.MLD_RCF.equals(mailBag.getLatestStatus()) ||
								(mailBag.getLatestStatus()!=null && mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_ARRIVED)))&&
								mailBagVOFromUpload.getDeliveredFlag()!=null &&
								mailBagVOFromUpload.getDeliveredFlag().equals( MailConstantsVO.FLAG_YES)) {
							scannedMailDetailsVO.setProcessPoint( MailConstantsVO.MAIL_STATUS_DELIVERED);
						}



						if ( MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())||
								 MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())||
								 MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
							// Outboud flow
							//Changed as part of bug ICRD-98510 by A-5526 (Operational status check)
							if ( MailConstantsVO.OPERATION_OUTBOUND.equals(mailBag.getOperationalStatus())&&
									( MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailBag.getLatestStatus())||
											mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_ACCEPTED) ||
											mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_ASSIGNED) ||
											 MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailBag.getLatestStatus())||
											 MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailBag.getLatestStatus())||
											 MailConstantsVO.MAIL_STATUS_HNDRCV.equals(mailBag.getLatestStatus())||
											 MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailBag.getLatestStatus()))) {
								scannedMailDetailsVO.setProcessPoint( MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
							} else if ( MailConstantsVO.OPERATION_INBOUND.equals(mailBag.getOperationalStatus())) {
								/*
								if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailBag.getLatestStatus())||
									mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)||
									mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ASSIGNED)||
									mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)) {
									scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
								} else */
								if ((mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_ARRIVED) ||mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_DAMAGED)) &&
										mailBag.getScannedPort().equals(scannedPort)) {
									scannedMailDetailsVO.setProcessPoint(
											 MailConstantsVO.MAIL_STATUS_TRANSFERRED);
								} else if (mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_TRANSFERRED)||
										mailBag.getLatestStatus().equals( MailConstantsVO.MAIL_STATUS_ASSIGNED)) {
									scannedMailDetailsVO.setProcessPoint( MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
								}

							}
						}


					}

				}

			}
		}

	}

	private void updateVOForSpecificOperations(
			ScannedMailDetailsVO scannedMailDetailsVO,
			MailbagVO mailbagVOToSave, MailUploadVO uploadedMaibagVO,
			LoginProfile logonAttributes) throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {

		Map<String, Collection<OneTimeVO>> oneTimes = null;
		oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())||
				MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())||
				MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())) {

			updateVOForAcceptance(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			log.info( "Going to call saveOffloadedSession ");
			updateVOForOffload(scannedMailDetailsVO, logonAttributes, oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForArrival(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForDeliver(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForDammageCapture(scannedMailDetailsVO, logonAttributes,oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForReturn(scannedMailDetailsVO, logonAttributes, oneTimes);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForTransfer(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
			updateVOForReassign(scannedMailDetailsVO, logonAttributes);
		}

		try {
			setValidatedContainerDetails(scannedMailDetailsVO);
		} catch (SystemException e) {
			log.info( "SystemException caught");
			log.info( "setValidatedContainerDetails Exception" + e);
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);

		}
		catch(Exception e){
			log.info("Exception caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
	}

	/**
	 *
	 * @throws RemoteException
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws ForceAcceptanceException
	 */
	public void saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO)
			throws RemoteException, SystemException, MailHHTBusniessException,
			MailMLDBusniessException, MailOperationsBusinessException, ForceAcceptanceException {


		LoginProfile logonAttributes = null;

		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} catch (SystemException e) {
			log.info( "SystemException caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
		if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0 &&
				(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint()))){
			for(MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()){
				MailbagInULDForSegmentVO mailbagInULDForSegmentVO = null;
				try {
					mailbagInULDForSegmentVO =new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
				} catch (PersistenceException e) {
					log.info(e.getErrorCode());
				}
				if(Objects.nonNull(mailbagInULDForSegmentVO) && StringUtils.equals(MailConstantsVO.FLAG_YES, mailbagInULDForSegmentVO.getArrivalFlag())){
					mailBagVO.setFoundResditSent(true);
				}
			}
		}

		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO
				.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED
				.equals(scannedMailDetailsVO.getProcessPoint())
				|| MailConstantsVO.MAIL_STATUS_EXPORT
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			if(scannedMailDetailsVO.getMailDetails()!=null)
			{
				saveAcceptanceFromUpload(scannedMailDetailsVO, logonAttributes);
			}
		} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveOffloadFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveArrivalFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveDeliverFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_DAMAGED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveDamageCaptureFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_RETURNED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveReturnFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
		} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL
				.equals(scannedMailDetailsVO.getProcessPoint())) {
			savereassignFromUpload(scannedMailDetailsVO, logonAttributes);
		} else {
			constructAndRaiseException(MailMLDBusniessException.INVALID_TRANSACTION_EXCEPTION,
					MailHHTBusniessException.INVALID_TRANSACTION_EXCEPTION, scannedMailDetailsVO);
		}

	}
	/**
	 *
	 * 	Method		:	MailUploadController.validateFlightAndSegmentsForMLD
	 *	Added by 	:	A-4803 on 08-Dec-2014
	 * 	Used for 	:	validating flight and segments
	 *	Parameters	:	@param mailBagVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailMLDBusniessException
	 *	Return type	: 	Collection<OperationalFlightVO>
	 */
	public Collection<OperationalFlightVO> validateFlightAndSegmentsForMLD(
			Collection<MailUploadVO> mailBagVOs) throws SystemException, MailMLDBusniessException {
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<OperationalFlightVO>();
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} catch (SystemException e) {
			log.info( SYSTEM_EXCEPTION_ERROR);
		}
		String airportCode = logonAttributes.getAirportCode();
		for (MailUploadVO mailUploadVO : mailBagVOs) {
			String pou ="";

			/*Added By A-5166 For ISL airport change*/
			if (mailUploadVO.getScannedPort()!=null && com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MTKMALUPLJOB.equals(mailUploadVO.getMailSource())){
				airportCode = mailUploadVO.getScannedPort();
			}

			pou = mailUploadVO.getContainerPOU();
			if((String.valueOf(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT)).equals(
					mailUploadVO.getFlightNumber()) || mailUploadVO.getFlightNumber()==null
					|| (com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MLD_TFD.equals(mailUploadVO.getProcessPoint())&& mailUploadVO.getToCarrierCode()!=null) ){
				//skipping flight validation for carrier acceptance
				continue;
			}
			AirlineValidationVO airlineValidationVO = null;
			String flightCarrierCode ="";
			if(mailUploadVO.getCarrierCode()!=null){
				flightCarrierCode = mailUploadVO.getCarrierCode().trim().toUpperCase();
			}

			if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
				airlineValidationVO = populateAirlineValidationVO(mailUploadVO.getCompanyCode(),
						flightCarrierCode);
			}
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(mailUploadVO.getCompanyCode());
			flightFilterVO.setStation(mailUploadVO.getScannedPort());
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailUploadVO.getFlightDate()));

			if(airlineValidationVO != null){
				flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
			}
			flightFilterVO.setFlightNumber(mailUploadVO.getFlightNumber());

			if (com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_UPLIFTED.equals(mailUploadVO.getScanType()) ||
					com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailUploadVO.getScanType()) ||
					com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_EXPORT.equals(mailUploadVO.getScanType())) {
				flightFilterVO.setDirection(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.OPERATION_OUTBOUND);
			} else if (com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(mailUploadVO.getScanType())) {
				flightFilterVO.setDirection(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.OPERATION_INBOUND);
			}
			Collection<FlightValidationVO> flightValidationVOs = mailController.validateFlight(
					flightFilterVO);

			if (flightValidationVOs != null && flightValidationVOs.size() > 0) {

				for (FlightValidationVO flightValidationVO : flightValidationVOs) {

					if(FlightValidationVO.FLT_STATUS_ACTIVE.equals(flightValidationVO.getFlightStatus())) {
						Collection<FlightSegmentSummaryVO> flightSegments = null;

						try {
							flightSegments = flightOperationsProxy.findFlightSegments(
									flightValidationVO.getCompanyCode(),
									flightValidationVO.getFlightCarrierId(),
									flightValidationVO.getFlightNumber(),
									flightValidationVO.getFlightSequenceNumber());
						} catch (SystemException e) {
							log.info("SystemException Caught");
						}
						int segmentSerialNumber = 0;

						if (flightSegments != null && flightSegments.size() > 0) {
							Set<String> airports =new HashSet<String>();
							boolean breakRequied = false;
							for (FlightSegmentSummaryVO segmentVo : flightSegments) {
								if((com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(mailUploadVO.getScanType())&&
										com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MLD.equalsIgnoreCase(mailUploadVO.getMailSource())&&
										mailUploadVO.getContainerPol()==null || mailUploadVO.getContainerPol().trim().length()==0) &&
										segmentVo.getSegmentDestination().equals(
												mailUploadVO.getContainerPOU())){
									mailUploadVO.setContainerPol(segmentVo.getSegmentOrigin());
								}
								if(com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(mailUploadVO.getScanType()) &&
										com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.BULK_TYPE.equals(mailUploadVO.getContainerType()) &&
										mailUploadVO.getFromPol() != null && mailUploadVO.getFromPol().trim().length() > 0){
									pou = airportCode;
								}

								if (segmentVo.getSegmentOrigin() != null &&
										segmentVo.getSegmentDestination() != null) {
									if(!breakRequied && !airportCode.equals(segmentVo.getSegmentOrigin()))
									{
										airports.add(segmentVo.getSegmentOrigin());
									}
									else
									{
										breakRequied = true;
									}
									if(!breakRequied && !airportCode.equals(segmentVo.getSegmentDestination()))
									{
										airports.add(segmentVo.getSegmentDestination());
									}
									else
									{
										breakRequied = true;
									}
									if (segmentVo.getSegmentOrigin().equals(mailUploadVO.getContainerPol()) &&
											segmentVo.getSegmentDestination().equals(pou)) {
										segmentSerialNumber = segmentVo.getSegmentSerialNumber();
										break;
									}
								}
							}

							if (segmentSerialNumber == 0) {
								Iterator<String> iterator = airports.iterator();
								StringBuilder airportBuilder = new StringBuilder("");
								while(iterator.hasNext()){
									airportBuilder.append(iterator.next()).append(",");
								}
								//Modified as part of CRQ ICRD-120878 by A-5526 starts
								int value =0;
								if(airportBuilder!=null && airportBuilder.length()>0) {
									airportBuilder.replace(airportBuilder.lastIndexOf(","), airportBuilder.length(), " ");
									value = airportBuilder.lastIndexOf(",");
								}//Modified as part of CRQ ICRD-120878 by A-5526 ends
								if(value > 0)
								{
									airportBuilder.replace(value, value+1, " or ");
								}
								mailUploadVO.setPols(airportBuilder.toString());
								throw new MailMLDBusniessException(
										MailMLDBusniessException.INVALID_FLIGHT_SEGMENT);
							}
						} else {
							log.info("No Segment Details obtained from Flight module");
						}

						if (MailConstantsVO.MAIL_STATUS_UPLIFTED.equals(mailUploadVO.getScanType())) {


							OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
							operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
							operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
							operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
							operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
							operationalFlightVO.setFlightSequenceNumber(
									flightValidationVO.getFlightSequenceNumber());
							operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
							operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
							operationalFlightVO.setSegSerNum(segmentSerialNumber);
							operationalFlightVOs.add(operationalFlightVO);
							Collection<String> mailBags = validateMailBagsForUPL(flightValidationVO);

							if (mailBags != null && mailBags.size() > 0) {
								boolean ismailBagPresent = false;

								for (String mailBag : mailBags) {

									if (mailBag.equals(mailUploadVO.getMailTag())) {
										ismailBagPresent = true;
										break;
									}
								}

							/*if (!ismailBagPresent) {
								throw new MailMLDBusniessException(
										MailMLDBusniessException.MAILBAG_NOT_FOUND_EXCEPTION);
							}*/
							} /*else {
							throw new MailMLDBusniessException(
									MailMLDBusniessException.MAILBAG_NOT_FOUND_EXCEPTION);
						}*/
						}
					}
				}
			} else {
				throw new MailMLDBusniessException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT);
			}
		}

		return operationalFlightVOs;
	}

	/**
	 * Method : MailUploadController.saveMailUploadDetailsFromMLD Added by : A-4803 on 15-Oct-2014 Used for : saving mail details via mld Parameters : @param mldMasterVOs Parameters : @throws SystemException Return type : void
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 * @throws ForceAcceptanceException
	 */
	public Map<String, Collection<MLDMasterVO>> saveMailUploadDetailsFromMLD(Collection<MLDMasterVO> mldMasterVOs)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			ForceAcceptanceException {
		log.debug("MailController" + " : " + "saveMailUploadDetailsFromMLD" + " Entering");
		Map<String, Collection<MLDMasterVO>> mldMasterVOsForReturn = new HashMap<String, Collection<MLDMasterVO>>();
		if (mldMasterVOs != null && mldMasterVOs.size() > 0) {
			Collection<MailUploadVO> mailBagVOs = new ArrayList<MailUploadVO>();
			String scanPort = ((ArrayList<MLDMasterVO>) mldMasterVOs).get(0).getSenderAirport();
			String scanType = "";
			Map<String, String> scanTypeContainerMap = new HashMap<String, String>();
			Map<String, String> dummyTrolleyNumberMap = new HashMap<String, String>();
			String serialNumber = "";
			for (MLDMasterVO mLDMasterVO : mldMasterVOs) {
				if (mLDMasterVO.getBarcodeValue() != null) {
					MLDDetailVO mLDDetailVO = mLDMasterVO.getMldDetailVO();
					MailUploadVO mailUploadVO = new MailUploadVO();
					if (MailConstantsVO.MLD_VERSION_TWO.equals(mLDMasterVO.getMessageVersion())) {
						mailUploadVO = createMailUploadDetailsFromMLD2(mLDMasterVO);
						scanPort = mLDMasterVO.getSenderAirport();
					} else {
						if (MLD_VERSION0.equals(mLDDetailVO.getMessageVersion())) {
							setPouOubForMLD0(mLDMasterVO, mLDDetailVO);
						}
						mailUploadVO = createMailUploadDetailsFromMLD1(mLDMasterVO);
					}
					mailUploadVO.setMessageVersion(mLDMasterVO.getMessageVersion());
					mailUploadVO.setMailSource(mLDMasterVO.getMailSource());
					mailUploadVO.setProcessPoint(mLDMasterVO.getEventCOde());
					try {
						updateULDOrBulkDetailsForMLD(mailUploadVO, mLDMasterVO, mLDDetailVO);
					} catch (SystemException e) {
						throw new SystemException(e.getMessage());}
					if (scanTypeContainerMap.get(mLDMasterVO.getUldNumber()) != null) {
						scanType = scanTypeContainerMap.get(mLDMasterVO.getUldNumber());
					} else {
						scanType = mapEventCodeToScanType(mailUploadVO, mLDMasterVO, mLDDetailVO, scanTypeContainerMap);
					}
					if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(scanType)) {
						scanType = overrideScanTypeForDLVMessage(scanType, mailUploadVO);
					}
					mailUploadVO.setScanType(scanType);
					updateFlightDetailsForMLD(mailUploadVO, mLDMasterVO, mLDDetailVO, scanType);
					updateContainerPOLPOUForMLD(mailUploadVO, mLDMasterVO, mLDDetailVO, scanType);
					updateMailbagDetailsForMLD(mailUploadVO, mLDMasterVO, mLDDetailVO, scanType);
					mailBagVOs.add(mailUploadVO);
				}
			}
			if (mailBagVOs.size() > 0) {
				Collection<OperationalFlightVO> operationalFlightVOs = validateFlightAndSegmentsForMLD(mailBagVOs);
				if (MailConstantsVO.MAIL_STATUS_UPLIFTED.equals(scanType)) {
					Collection<MailUploadVO> mailuploadVOs = new ArrayList<MailUploadVO>();
					if (operationalFlightVOs != null && operationalFlightVOs.size() > 0) {
						if (mldMasterVOs != null && mldMasterVOs.size() > 0
								&& UPL_MESSAGE_EXTRACTOR.equals(mldMasterVOs.iterator().next().getMessagingMode())) {
							mldMasterVOs.iterator().next().setMessagingMode("");
							mailuploadVOs = mailController.findAndRetriveUpliftedOperations(operationalFlightVOs,
									mailBagVOs);
							mldMasterVOsForReturn = createUpliftedOperationsDetails(mldMasterVOs, mailuploadVOs);
						} else {
							mailController.flagUpliftedResditForMailbagsForMLD(operationalFlightVOs, mailBagVOs);
						}
					}
				} else {
					log.debug("Going To call saveMailUploadDetails" + mailBagVOs);
					saveMailUploadDetails(mailBagVOs, scanPort);
				}
			}
		}
		return mldMasterVOsForReturn;
	}

	protected void setPouOubForMLD0(MLDMasterVO mLDMasterVO, MLDDetailVO mLDDetailVO) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mLDMasterVO.getCompanyCode());
		flightFilterVO.setStation(mLDMasterVO.getSenderAirport());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mLDDetailVO.getFlightOperationDateOub()));
		flightFilterVO.setCarrierCode(mLDDetailVO.getCarrierCodeOub());
		flightFilterVO.setFlightNumber(mLDDetailVO.getFlightNumberOub());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		flightValidationVOs = mailController.validateFlight(flightFilterVO);
		if (Objects.nonNull(flightValidationVOs) && flightValidationVOs.size() > 0) {
			FlightValidationVO flight = flightValidationVOs.stream()
					.filter(f -> (f.getCarrierCode().equals(mLDDetailVO.getCarrierCodeOub())
							&& f.getFlightNumber().equals(mLDDetailVO.getFlightNumberOub())))
					.findFirst().orElse(null);
			mLDDetailVO.setPouOub(Objects.nonNull(flight) ? flight.getLegDestination() : mLDDetailVO.getPouOub());
		}
	}

	private Map<String, Collection<MLDMasterVO>> createUpliftedOperationsDetails(Collection<MLDMasterVO> mldMasterVOs,
																				 Collection<MailUploadVO> mailuploadVOs) {
		Map<String, Collection<MLDMasterVO>> mldMasterVOsForReturn = new HashMap<String, Collection<MLDMasterVO>>();
		Collection<MLDMasterVO> mldMasterVOsForResditSend = new ArrayList<MLDMasterVO>();
		Collection<MLDMasterVO> mldMasterVOsForReassignIn = new ArrayList<MLDMasterVO>();
		for (MLDMasterVO mLDMasterVO : mldMasterVOs) {
			MLDDetailVO mLDDetailVO = mLDMasterVO.getMldDetailVO();
			for (MailUploadVO mailUploadVO : mailuploadVOs) {
				if (mLDDetailVO.getMailIdr().equals(mailUploadVO.getMailTag())) {
					if (MailConstantsVO.RESDIT_SEND_STATUS.equals(mailUploadVO.getScanType())) {
						mldMasterVOsForResditSend.add(mLDMasterVO);
					}
					if (MailConstantsVO.MAILBAG_ASSIGNMENT_IN.equals(mailUploadVO.getScanType())) {
						mldMasterVOsForReassignIn.add(mLDMasterVO);
					}
				}
			}
			if (mailuploadVOs == null || mailuploadVOs.isEmpty()) {
				mldMasterVOsForReassignIn.add(mLDMasterVO);
			}
		}
		mldMasterVOsForReturn.put(MailConstantsVO.RESDIT_SEND_STATUS, mldMasterVOsForResditSend);
		mldMasterVOsForReturn.put(MailConstantsVO.MAILBAG_ASSIGNMENT_IN, mldMasterVOsForReassignIn);
		return mldMasterVOsForReturn;
	}
	/**
	 * Method : MailUploadController.updateULDOrBulkDetailsForMLD Added by : A-4803 on 21-Oct-2014 Used for : updating uld details for the mail from mld Parameters : @param mailUploadVO Parameters : @param mLDMasterVO Parameters : @param mLDDetailVO Parameters : @throws SystemException Parameters : @throws GenerationFailedException Return type : void
	 */
	//TODO: Exception handling to be corrected in Neo
	private void updateULDOrBulkDetailsForMLD(MailUploadVO mailUploadVO, MLDMasterVO mLDMasterVO,
											  MLDDetailVO mLDDetailVO) throws SystemException {
		if (mLDMasterVO.getUldNumber() != null && mLDMasterVO.getUldNumber().trim().length() > 0) {
			mailUploadVO.setContainerNumber(mLDMasterVO.getUldNumber());
			mailUploadVO.setContainerType(mLDMasterVO.getContainerType());
		}
	}

	/**
	 * Method : MailUploadController.generateSerialNumber Added by : A-4803 on 21-Oct-2014 Used for : generating the serial number in the trolley number Parameters : @param companyCode Parameters : @param pou Parameters : @param keyCondition Parameters : @return Parameters : @throws GenerationFailedException Parameters : @throws SystemException Return type : String
	 */
	//TODO: Exception handling to be corrected in Neo
	public String generateDummyTrolleyNumberForMLD(MLDMasterVO mldMasterVO, String keyCondition)
			throws SystemException {
		log.debug("MailController" + " : " + "generateDummyTrolleyNumberForMLD" + " Entering");
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		String containerNumber = mailController.findAlreadyAssignedTrolleyNumberForMLD(mldMasterVO);
		if (containerNumber != null) {
			return containerNumber;
		}
		String serialKey = "001";
		KeyCondition keyConditn =  new KeyCondition();
		keyConditn.setKey("barrowId");
		keyConditn.setValue(keyCondition);
		Criterion criterion = new CriterionBuilder()
				.withSequence("MAL_TROLLY_NO_GEN")
				.withKeyCondition(keyConditn)
				.withPrefix(keyCondition).build();
		return keyUtils.getKey(criterion);
	}

	/**
	 * Method : MailUploadController.updateMailbagDetailsForMLD Added by : A-4803 on 21-Oct-2014 Used for : updating mail bag details from MLD Parameters : @param mailUploadVO Parameters : @param mLDMasterVO Parameters : @param mLDDetailVO Parameters : @param scanType Parameters : @param isContainerAssignReassignReq Return type : void
	 * @throws SystemException
	 */
	private void updateMailbagDetailsForMLD(MailUploadVO mailUploadVO, MLDMasterVO mLDMasterVO, MLDDetailVO mLDDetailVO,
											String scanType) {
		mailUploadVO.setMailSource(mLDMasterVO.getMailSource());
		mailUploadVO.setMailTag(mLDDetailVO.getMailIdr());
		mailUploadVO.setContainerJourneyId(mLDDetailVO.getContainerJourneyId());
		mailUploadVO.setDateTime(mLDMasterVO.getScanTime().format( DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm:ss")));
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scanType)) {
			if (mLDDetailVO.getPouOub() != null && mLDDetailVO.getPouOub().trim().length() > 0) {
				String airportCode = findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDDetailVO.getPouOub());
				mailUploadVO.setToPOU(airportCode);
				mailUploadVO.setToDestination(airportCode);
				mailUploadVO.setDestination(airportCode);
			} else {
				String defaultAirport = findSystemParameterValue(DEFAULT_AIRPORTCODEFORGHA);
				mailUploadVO.setToPOU(defaultAirport);
				mailUploadVO.setToDestination(defaultAirport);
				mailUploadVO.setDestination(defaultAirport);
			}
		} else if (MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(scanType)) {
			String airportCode = findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getSenderAirport());
			mailUploadVO.setDestination(airportCode);
			mailUploadVO.setToPOU(airportCode);
			mailUploadVO.setToDestination(airportCode);
		}
		if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
			mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(6, 12));
			mailUploadVO.setCategory(mailUploadVO.getMailTag().substring(12, 13));
			mailUploadVO.setSubClass(mailUploadVO.getMailTag().substring(13, 15));
			mailUploadVO.setYear(Integer.parseInt(mailUploadVO.getMailTag().substring(15, 16)));
			mailUploadVO.setDsn(mailUploadVO.getMailTag().substring(16, 20));
			mailUploadVO.setRsn(mailUploadVO.getMailTag().substring(20, 23));
			mailUploadVO.setHighestnumberIndicator(mailUploadVO.getMailTag().substring(23, 24));
			mailUploadVO.setRegisteredIndicator(mailUploadVO.getMailTag().substring(24, 25));
			Quantity Wgt = quantities.getQuantity(Quantities.MAIL_WGT,
					BigDecimal.valueOf(Double.parseDouble(mailUploadVO.getMailTag().substring(25, 29))));
			mailUploadVO.setTotalWeight(Wgt);
			mailUploadVO.setWeight(Wgt);
		}
		mailUploadVO.setTotalBag(1);
		mailUploadVO.setBags(1);
	}

	/**
	 * Method : MailUploadController.updateContainerPOLPOUForMLD Added by : A-4803 on 21-Oct-2014 Used for : updating container from MLD Parameters : @param mailUploadVO Parameters : @param mLDMasterVO Parameters : @param mLDDetailVO Parameters : @param scanType Return type : void
	 * @throws SystemException
	 * @throws MailMLDBusniessException
	 */
	private void updateContainerPOLPOUForMLD(MailUploadVO mailUploadVO, MLDMasterVO mLDMasterVO,
											 MLDDetailVO mLDDetailVO, String scanType) throws MailMLDBusniessException {
		if (MailConstantsVO.MAIL_STATUS_UPLIFTED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scanType)
				|| (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(scanType)
				&& mLDDetailVO.getFlightNumberInb() != null)) {
			mailUploadVO.setContainerPol(
					findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getSenderAirport()));
			if (mLDMasterVO.getWeight() != null) {
				mailUploadVO.setUldActualWeight(mLDMasterVO.getWeight());
			}
			if (mLDDetailVO.getPouOub() != null && mLDDetailVO.getPouOub().trim().length() > 0) {
				mailUploadVO
						.setContainerPOU(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDDetailVO.getPouOub()));
			} else {
				mailUploadVO.setContainerPOU(findSystemParameterValue(DEFAULT_AIRPORTCODEFORGHA));
			}
		} else if (MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(scanType)) {
			mailUploadVO.setContainerPol(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDDetailVO.getPolInb()));
			mailUploadVO.setContainerPOU(
					findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getSenderAirport()));
		}
		validateULDAircraftComapctibility(mailUploadVO);
	}

	/**
	 * Method : MailUploadController.updateFlightDetailsForMLD Added by : A-4803 on 21-Oct-2014 Used for : updating flight details from MLD Parameters : @param mailUploadVO Parameters : @param mLDMasterVO Parameters : @param mLDDetailVO Parameters : @param scanType Return type : void
	 * @throws SystemException
	 */
	private void updateFlightDetailsForMLD(MailUploadVO mailUploadVO, MLDMasterVO mLDMasterVO, MLDDetailVO mLDDetailVO,
										   String scanType) {
		mailUploadVO.setCompanyCode(mLDMasterVO.getCompanyCode());
		ZonedDateTime flightDate = localDateUtil.getLocalDate(null, false);
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} catch (SystemException e) {
			log.debug(e.getMessage());
		}
		if (MailConstantsVO.MAIL_STATUS_UPLIFTED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scanType)) {
			mailUploadVO.setCarrierCode(mLDDetailVO.getCarrierCodeOub());
			if (mailUploadVO.getCarrierCode() == null || mailUploadVO.getCarrierCode() == "") {
				if (logonAttributes.getOwnAirlineCode() != null
						&& logonAttributes.getOwnAirlineCode().trim().length() > 0) {
					mailUploadVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
				}
			}
			mailUploadVO.setCarrierId(mLDController.findCarrierIdentifier(mLDMasterVO.getCompanyCode(),
					mailUploadVO.getCarrierCode()));
			if ("-1".equalsIgnoreCase(mLDDetailVO.getFlightNumberOub()) || (mLDDetailVO.getFlightNumberOub() == null
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(scanType))) {
				mailUploadVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
				mailUploadVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				mailUploadVO.setSerialNumber(MailConstantsVO.DESTN_FLT);
			} else {
				mailUploadVO.setFlightNumber(mLDDetailVO.getFlightNumberOub());
			}
			if (mLDDetailVO.getFlightOperationDateOub() != null) {
				flightDate = LocalDate.withDate(flightDate, mLDDetailVO.getFlightOperationDateOub()
						.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
			}
		} else if (MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(scanType)
				|| MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(scanType)
				|| (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equalsIgnoreCase(scanType)
				&& mLDDetailVO.getFlightNumberInb() != null)) {
			mailUploadVO.setCarrierCode(mLDDetailVO.getCarrierCodeInb());
			mailUploadVO.setFlightNumber(mLDDetailVO.getFlightNumberInb());
			if (mLDDetailVO.getFlightOperationDateInb() != null) {
				flightDate = LocalDate.withDate(flightDate, mLDDetailVO.getFlightOperationDateInb()
						.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
			}
		}
		mailUploadVO.setFlightDate(flightDate);
		if ((MailConstantsVO.MLD_REC.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde()))
				&& MailConstantsVO.MLD_MODE_CARRIER.equals(mLDDetailVO.getMailModeInb())) {
			mailUploadVO.setFromCarrierCode(mLDDetailVO.getModeDescriptionInb());
		}
		if (MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scanType)
				&& MailConstantsVO.MLD_NST.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			ContainerAssignmentVO containerAssignmentVO = (this.cntAsgmntVO == null)
					? findLatestContainerAssignment(mailUploadVO.getContainerNumber())
					: this.cntAsgmntVO;
			if ((containerAssignmentVO != null
					&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag()))) {
				mailUploadVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
				mailUploadVO.setCarrierId(containerAssignmentVO.getCarrierId());
				mailUploadVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				mailUploadVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				mailUploadVO.setFlightDate(containerAssignmentVO.getFlightDate());
				mailUploadVO.setContainerPOU(containerAssignmentVO.getPou());
				mailUploadVO.setDestination(containerAssignmentVO.getDestination());
				mLDDetailVO.setPouOub(containerAssignmentVO.getPou() != null ? containerAssignmentVO.getPou()
						: containerAssignmentVO.getDestination());
				mLDMasterVO.setDestAirport(containerAssignmentVO.getDestination());
			}
		}
	}

	/**
	 * Method : MailUploadController.mapEventCodeToScanType Added by : A-4803 on 21-Oct-2014 Used for : mapping event code to scan type Parameters : @param mailUploadVO Parameters : @param mLDMasterVO Parameters : @param mLDDetailVO Parameters : @param scanTypeContainerMap Parameters : @param assignReassignContainerPresent Parameters : @return Return type : String
	 * @throws SystemException
	 */
	private String mapEventCodeToScanType(MailUploadVO mailUploadVO, MLDMasterVO mLDMasterVO, MLDDetailVO mLDDetailVO,
										  Map<String, String> scanTypeContainerMap) {
		String scanType = "";
		ContainerAssignmentVO containerAssignmentVO = null;
		try {
			if (MailConstantsVO.BULK_TYPE.equals(mLDMasterVO.getContainerType())) {
				ContainerVO containerVO = new ContainerVO();
				containerVO.setCompanyCode(mLDMasterVO.getCompanyCode());
				containerVO.setContainerNumber(mLDMasterVO.getUldNumber());
				containerVO.setAssignedPort(mLDMasterVO.getSenderAirport());
				containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
			} else {
				containerAssignmentVO = mailController
						.findLatestContainerAssignment(mailUploadVO.getContainerNumber());
			}
		} finally {
		}
		if (MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			if (containerAssignmentVO == null || "U".equals(mLDMasterVO.getBarcodeType())) {
				scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
			} else {
				scanType = MailConstantsVO.MAIL_STATUS_EXPORT;
			}
		} else if (MailConstantsVO.MLD_NST.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			if (containerAssignmentVO == null
					&& ("U".equals(mLDMasterVO.getBarcodeType()) || "R".equals(mLDMasterVO.getBarcodeType())
					|| MailConstantsVO.CONTAINER_JOURNEY_ID.equals(mLDMasterVO.getBarcodeType()))) {
				scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
			} else {
				if (mLDDetailVO.getContainerJourneyId() != null
						&& MailConstantsVO.CONTAINER_JOURNEY_ID.equals(mLDMasterVO.getBarcodeType())) {
					scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
				} else {
					scanType = MailConstantsVO.MAIL_STATUS_EXPORT;
				}
			}
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			scanType = MailConstantsVO.MAIL_STATUS_DELIVERED;
		} else if (MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCF.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			scanType = MailConstantsVO.MAIL_STATUS_ARRIVED;
		} else if (MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
		} else if (MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			scanType = MailConstantsVO.MAIL_STATUS_TRANSFERRED;
		} else if (MailConstantsVO.MLD_RET.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			scanType = MailConstantsVO.MAIL_STATUS_RETURNED;
		} else if (MailConstantsVO.MLD_STG.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			if (containerAssignmentVO == null && (MailConstantsVO.ULD_TYPE.equals(mLDMasterVO.getBarcodeType())
					|| MailConstantsVO.BULK_TYPE.equals(mLDMasterVO.getBarcodeType())
					|| "R".equals(mLDMasterVO.getBarcodeType()))) {
				scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
			} else if (containerAssignmentVO != null && ("R".equals(mLDMasterVO.getBarcodeType())
					|| "U".equals(mLDMasterVO.getBarcodeType()) || "B".equals(mLDMasterVO.getBarcodeType()))) {
				scanType = MailConstantsVO.MAIL_STATUS_ACCEPTED;
			} else
				scanType = MailConstantsVO.MAIL_STATUS_UPLIFTED;
		} else {
			scanType = mLDMasterVO.getEventCOde();
		}
		if (scanTypeContainerMap != null && scanTypeContainerMap.get(mLDMasterVO.getUldNumber()) == null) {
			scanTypeContainerMap.put(mLDMasterVO.getUldNumber(), scanType);
		}
		return scanType;
	}

	/**
	 * @author A-1885
	 * @param webServicesVos
	 * @return
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 */
	public List<MailUploadVO> performMailOperationForGHA(Collection<MailWebserviceVO> webServicesVos,
														 String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, PersistenceException {
		this.cntAsgmntVO = null;
		String errorFromMapping = "";
		StringBuilder errorString = new StringBuilder();
		ArrayList<MailUploadVO> mailscanVos = createMailScanVOS(webServicesVos, scanningPort, errorString,
				errorFromMapping);
		if (mailscanVos.size() > 0) {
			MailUploadVO mailVO = mailscanVos.iterator().next();
			MailWebserviceVO mailWebServiceVO = webServicesVos.iterator().next();
			if (mailVO.getScanType() == null || mailVO.getScanType().trim().length() == 0) {
				mailVO.setScanType(mailWebServiceVO.getScanType());
			}
			mailVO.setOverrideValidation(MailConstantsVO.FLAG_NO);
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			try {
				scannedMailDetailsVO = saveMailUploadDetailForGHA(scanningPort, mailscanVos, scannedMailDetailsVO);
				if (scannedMailDetailsVO.isArrivalException()) {
					return mailscanVos;
				}
			} catch (MailHHTBusniessException e) {
				for (ErrorVO errVo : e.getErrors()) {
					errorFromMapping = "";
					errorFromMapping = MailOperationsBusinessException.returnErrorMapping(errVo.getErrorCode());
					if (errorFromMapping != null && !errorFromMapping.isEmpty()
							&& errorFromMapping.trim().length() > 0) {
						throw new SystemException(errorFromMapping);
					} else if (!(validateAndCorrectErrorFormat(errVo.getErrorCode()).isEmpty())) {
						throw new SystemException(validateAndCorrectErrorFormat(errVo.getErrorCode()));
					} else {
						StringBuilder error = new StringBuilder();
						if (errVo.getErrorCode() != null) {
							error.append(errVo.getErrorCode());
						}
						throw new SystemException(error.toString());
					}
				}
			} catch (MailMLDBusniessException mld) {
				for (ErrorVO errVo : mld.getErrors()) {
					errorFromMapping = "";
					errorFromMapping = MailOperationsBusinessException.returnErrorMapping(errVo.getErrorCode());
					if (errorFromMapping != null && !errorFromMapping.isEmpty()
							&& errorFromMapping.trim().length() > 0) {
						throw new SystemException(errorFromMapping);
					} else {
						StringBuilder error = new StringBuilder();
						if (errVo.getErrorCode() != null) {
							error.append(errVo.getErrorCode());
						}
						throw new SystemException(error.toString());
					}
				}
			}
		}
		return new ArrayList<>();
	}

	public ArrayList<MailUploadVO> createMailScanVOS(Collection<MailWebserviceVO> webServicesVos, String scanningPort,
													 StringBuilder errorString, String errorFromMapping) {
		Collection<MailUploadVO> mailVos = null;
		ArrayList<MailUploadVO> maildeleteVos = new ArrayList<MailUploadVO>();
		ArrayList<MailUploadVO> mailscanVos = new ArrayList<MailUploadVO>();
		try {
			mailVos = generateMailUploadVO(webServicesVos);
		} catch (MailHHTBusniessException e) {
			for (ErrorVO errVo : e.getErrors()) {
				errorFromMapping = "";
				errorFromMapping = MailOperationsBusinessException.returnErrorMapping(errVo.getErrorCode());
				if (errorFromMapping != null && !errorFromMapping.isEmpty() && errorFromMapping.trim().length() > 0) {
					throw new SystemException(errorFromMapping);
				} else if (!validateAndCorrectErrorFormat(errVo.getErrorCode()).isEmpty()) {
					throw new SystemException(validateAndCorrectErrorFormat(errVo.getErrorCode()));
				} else {
					StringBuilder error = new StringBuilder();
					error.append("mail.");
					if (errVo.getErrorCode() != null) {
						error.append(errVo.getErrorCode());
					}
					throw new SystemException(error.toString());
				}
			}
		}
		if (mailVos != null && mailVos.size() > 0) {
			for (MailUploadVO mailVo : mailVos) {
				if (MailConstantsVO.MAIL_STATUS_DELETED.equals(mailVo.getScanType())) {
					maildeleteVos.add(mailVo);
				} else {
					mailscanVos.add(mailVo);
				}
			}
		}
		if (maildeleteVos.size() > 0) {
			deleteMailbag(maildeleteVos);
		}
		return mailscanVos;
	}

	private String validateAndCorrectErrorFormat(String errorCode) {
		String errorString = "";
		if (errorCode != null && !errorCode.isEmpty()) {
			if (errorCode.startsWith("Mailbag is currently at port")) {
				errorString = "mailtracking.defaults.err.mailbagsIsInAnotherPort";
			} else if (errorCode.startsWith("This BULK/ULD is present in another flight")) {
				errorString = "mailtracking.defaults.err.bulkOrULDisPresentInAnotherFlightOrCarrier";
			} else if (errorCode.startsWith("Mailbag is currently in a closed flight")) {
				errorString = "mailtracking.defaults.err.mailbagCurrentlyInClosedFlight";
			}
			if (errorCode.startsWith("Mailbag delivered. Import flight details are mandatory for mailbag arrival")
					|| errorCode.startsWith("Mailbag returned. Import flight details are mandatory for mailbag arrival")
					|| errorCode.startsWith(
					"Mailbag Transfered. Import flight details are mandatory for mailbag arrival")) {
				errorString = errorCode;
			}
		}
		return errorString;
	}

	/**
	 * @author A-1885
	 * @param webServicesVos
	 * @return
	 * @throws MailHHTBusniessException
	 */
	private Collection<MailUploadVO> generateMailUploadVO(Collection<MailWebserviceVO> webServicesVos)
			throws MailHHTBusniessException {
		Collection<MailUploadVO> mailUploadVos = new ArrayList<MailUploadVO>();
		Collection<MailUploadVO> uploadVos = null;
		if (webServicesVos != null && webServicesVos.size() > 0) {
			for (MailWebserviceVO serviceVo : webServicesVos) {
				uploadVos = populateMailUploadVO(serviceVo);
				mailUploadVos.addAll(uploadVos);
			}
		}
		return mailUploadVos;
	}

	/**
	 * @author A-1885
	 * @return
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 */
	private Collection<MailUploadVO> populateMailUploadVO(MailWebserviceVO mailWebserviceVO)
			throws MailHHTBusniessException {
		if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() > 0
				&& (mailWebserviceVO.getMailBagId().length() != 29
				|| !MAILBAG_PATTERN.matcher(mailWebserviceVO.getMailBagId()).matches())) {
			throw new SystemException(MailOperationsBusinessException.MAILTRACKING_INVALIDMAILMAILBAG);
		}
		if (mailWebserviceVO.getCarrierCode() == null || mailWebserviceVO.getCarrierCode().trim().length() == 0) {
			mailWebserviceVO.setCarrierCode(mailWebserviceVO.getCompanyCode());
		}
		Collection<MailUploadVO> finalVos = new ArrayList<MailUploadVO>();
		MailUploadVO mailUploadVO = populateMailVos(mailWebserviceVO);
		if (MailConstantsVO.MAL_FUL_IND.equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType(MailConstantsVO.MAL_FUL_IND);
			if (mailWebserviceVO.getUldFullIndicator() == null || (!"Y".equals(mailWebserviceVO.getUldFullIndicator())
					&& !"N".equals(mailWebserviceVO.getUldFullIndicator()))) {
				throw new SystemException(MailOperationsBusinessException.MAILTRACKING_INVALID_ULD_INDICATOR);
			}
			if (mailWebserviceVO.getContainerNumber() == null) {
				throw new SystemException(MailOperationsBusinessException.MAILTRACKING_CONTAINERID_MISSING);
			}
		}
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailWebserviceVO.getScanType())) {
			acceptMailBagFromPA(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAL_FUL_IND.equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType(MailConstantsVO.MAL_FUL_IND);
			mailUploadVO.setContainerNumber(mailWebserviceVO.getContainerNumber());
			mailUploadVO.setUldFullIndicator(mailWebserviceVO.getUldFullIndicator());
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailWebserviceVO.getScanType())) {
			returnMailBagToPA(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DAMAGED);
			mailUploadVO.setToPOU(mailUploadVO.getScannedPort());
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailWebserviceVO.getScanType())) {
			if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() > 0) {
				deliveryMailbag(mailUploadVO);
				finalVos.add(mailUploadVO);
			} else {
				Collection<MailUploadVO> mailvos = deliverContainer(mailUploadVO);
				if (mailvos != null && mailvos.size() > 0) {
					finalVos.addAll(mailvos);
				}
			}
		}
		if (MailWebserviceVO.MAIL_STATUS_REASSIGN.equals(mailWebserviceVO.getScanType())) {
			removeMailBagFromContainer(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailWebserviceVO.getScanType())) {
			transferMailBagToCarrier(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_DELETED.equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DELETED);
			finalVos.add(mailUploadVO);
		}
		if ("RMK".equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType("RMK");
			finalVos.add(mailUploadVO);
		}
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailWebserviceVO.getScanType())) {
			mailUploadVO.setScanType(mailWebserviceVO.getScanType());
			if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() > 0) {
				arriveMailBagFromFlight(mailUploadVO);
				finalVos.add(mailUploadVO);
			} else {
				Collection<MailUploadVO> mailvos = arriveULDFromFlight(mailUploadVO);
				if (mailvos != null && mailvos.size() > 0) {
					finalVos.addAll(mailvos);
				}
			}
		}
		if (MailWebserviceVO.MAIL_STATUS_EXPORT.equals(mailWebserviceVO.getScanType())) {
			if (mailWebserviceVO.getContainerNumber() != null
					&& mailWebserviceVO.getContainerNumber().trim().length() > 0
					&& !(mailWebserviceVO.getContainerNumber().startsWith("MT"))) {
				String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
				if (storageUnit.equals(mailWebserviceVO.getContainerNumber())) {
					throw new SystemException(
							MailOperationsBusinessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT);
				}
			}
			mailUploadVO.setScanType(mailWebserviceVO.getScanType());
			acceptMailBagFromPA(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		if (MailWebserviceVO.MAIL_STATUS_CANCEL.equals(mailWebserviceVO.getScanType())) {
			removeMailBagFromContainer(mailUploadVO);
			finalVos.add(mailUploadVO);
		}
		boolean validEventCode = false;
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailWebserviceVO.getScanType())
				|| MailWebserviceVO.MAIL_STATUS_EXPORT.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_DELETED.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailWebserviceVO.getScanType())
				|| MailWebserviceVO.MAIL_STATUS_CANCEL.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailWebserviceVO.getScanType())
				|| MailWebserviceVO.MAIL_STATUS_REASSIGN.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailWebserviceVO.getScanType())
				|| MailConstantsVO.MAL_FUL_IND.equals(mailWebserviceVO.getScanType())
				|| "RMK".equals(mailWebserviceVO.getScanType())) {
			validEventCode = true;
		}
		if (!validEventCode) {
			throw new SystemException(MailHHTBusniessException.INVALID_TRANSACTION_EXCEPTION);
		}
		return finalVos;
	}

	/**
	 * @author A-1885
	 * @param mailWebserviceVO
	 * @return
	 * @throws MailHHTBusniessException
	 */
	private MailUploadVO populateMailVos(MailWebserviceVO mailWebserviceVO) throws MailHHTBusniessException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setFromGHAService(true);
		mailUploadVO.setMailSource("WS");
		mailUploadVO.setCompanyCode(mailWebserviceVO.getCompanyCode());
		mailUploadVO.setCarrierCode(mailWebserviceVO.getCarrierCode());
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineProxy.validateAlphaCode(mailWebserviceVO.getCompanyCode(),
					mailWebserviceVO.getCarrierCode());
		} catch (SharedProxyException e) {
			log.error("Exception :", e);
		}
		if (airlineValidationVO != null && airlineValidationVO.getAirlineIdentifier() != 0) {
			mailUploadVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		}
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailWebserviceVO.getScanType())
				&& mailWebserviceVO.getToCarrierCod() != null
				&& mailWebserviceVO.getToCarrierCod().trim().length() > 0) {
			mailUploadVO.setFromCarrierCode(mailWebserviceVO.getCarrierCode());
			mailUploadVO.setCarrierCode(mailWebserviceVO.getToCarrierCod());
		}
		mailUploadVO.setFlightNumber(mailWebserviceVO.getFlightNumber());
		mailUploadVO.setFlightDate(mailWebserviceVO.getFlightDate());
		mailUploadVO.setContainerPOU(mailWebserviceVO.getContainerPou());
		mailUploadVO.setContainerNumber(mailWebserviceVO.getContainerNumber());
		mailUploadVO.setContainerType(mailWebserviceVO.getContainerType());
		mailUploadVO.setDestination(mailWebserviceVO.getContainerDestination());
		mailUploadVO.setContainerPol(mailWebserviceVO.getContainerPol());
		mailUploadVO.setRemarks(mailWebserviceVO.getRemarks());
		mailUploadVO.setMailTag(mailWebserviceVO.getMailBagId());
		mailUploadVO.setDateTime(
				mailWebserviceVO.getScanDateTime().format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
		mailUploadVO.setScannedDate(mailWebserviceVO.getScanDateTime());
		mailUploadVO.setDamageCode(mailWebserviceVO.getDamageCode());
		mailUploadVO.setDamageRemarks(mailWebserviceVO.getDamageRemarks());
		mailUploadVO.setOffloadReason(mailWebserviceVO.getOffloadReason());
		mailUploadVO.setReturnCode(mailWebserviceVO.getReturnCode());
		mailUploadVO.setToContainer(mailWebserviceVO.getToContainer());
		mailUploadVO.setToCarrierCode(mailWebserviceVO.getToCarrierCod());
		mailUploadVO.setToFlightNumber(mailWebserviceVO.getToFlightNumber());
		mailUploadVO.setToFlightDate(mailWebserviceVO.getToFlightDate());
		mailUploadVO.setToPOU(mailWebserviceVO.getToContainerPou());
		mailUploadVO.setToDestination(mailWebserviceVO.getToContainerDestination());
		mailUploadVO.setConsignmentDocumentNumber(mailWebserviceVO.getConsignmentDocNumber());
		mailUploadVO.setScannedPort(mailWebserviceVO.getScanningPort());
		mailUploadVO.setScanUser(mailWebserviceVO.getUserName());
		mailUploadVO.setIntact(mailWebserviceVO.isPAbuilt());
		if (mailWebserviceVO.isPAbuilt()) {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_YES);
		} else {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_NO);
		}
		mailUploadVO.setSerialNumber(mailWebserviceVO.getSerialNumber());
		mailUploadVO.setDeliverd(mailWebserviceVO.isDelivered());
		if (mailWebserviceVO.getFlightValidationVOS() != null && mailWebserviceVO.getFlightValidationVOS().size() > 0) {
			mailUploadVO.setFlightValidationVOS(mailWebserviceVO.getFlightValidationVOS());
		}
		if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() > 0) {
			if (mailWebserviceVO.getMailBagId().trim().length() == 29) {
				splitMailTagId(mailUploadVO);
			} else {
				throw new MailHHTBusniessException(MailHHTBusniessException.INVALID_MAILFORAMT);
			}
		}
		return mailUploadVO;
	}

	private MailUploadVO cloneMailUploadVO(MailUploadVO mailUploadVo) {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setFromGHAService(true);
		mailUploadVO.setMailSource("WS");
		mailUploadVO.setScanType(mailUploadVo.getScanType());
		mailUploadVO.setCompanyCode(mailUploadVo.getCompanyCode());
		mailUploadVO.setCarrierCode(mailUploadVo.getCarrierCode());
		mailUploadVO.setFromCarrierCode(mailUploadVo.getFromCarrierCode());
		mailUploadVO.setFlightNumber(mailUploadVo.getFlightNumber());
		mailUploadVO.setFlightDate(mailUploadVo.getFlightDate());
		mailUploadVO.setFlightSequenceNumber(mailUploadVo.getFlightSequenceNumber());
		mailUploadVO.setContainerPOU(mailUploadVo.getContainerPOU());
		mailUploadVO.setContainerNumber(mailUploadVo.getContainerNumber());
		mailUploadVO.setContainerType(mailUploadVo.getContainerType());
		mailUploadVO.setDestination(mailUploadVo.getDestination());
		mailUploadVO.setContainerPol(mailUploadVo.getContainerPol());
		mailUploadVO.setRemarks(mailUploadVo.getRemarks());
		mailUploadVO.setMailTag(mailUploadVo.getMailTag());
		mailUploadVO.setDateTime(mailUploadVo.getDateTime());
		mailUploadVO.setDamageCode(mailUploadVo.getDamageCode());
		mailUploadVO.setDamageRemarks(mailUploadVo.getDamageRemarks());
		mailUploadVO.setOffloadReason(mailUploadVo.getOffloadReason());
		mailUploadVO.setReturnCode(mailUploadVo.getReturnCode());
		mailUploadVO.setToContainer(mailUploadVo.getToContainer());
		mailUploadVO.setToCarrierCode(mailUploadVo.getToCarrierCode());
		mailUploadVO.setToFlightNumber(mailUploadVo.getToFlightNumber());
		mailUploadVO.setToFlightDate(mailUploadVo.getToFlightDate());
		mailUploadVO.setToPOU(mailUploadVo.getToPOU());
		mailUploadVO.setToDestination(mailUploadVo.getToDestination());
		mailUploadVO.setConsignmentDocumentNumber(mailUploadVo.getConsignmentDocumentNumber());
		mailUploadVO.setScannedPort(mailUploadVo.getScannedPort());
		mailUploadVO.setScanUser(mailUploadVo.getScanUser());
		mailUploadVO.setPaCode(mailUploadVo.getPaCode());
		mailUploadVO.setIntact(mailUploadVo.isIntact());
		mailUploadVO.setSerialNumber(mailUploadVo.getSerialNumber());
		mailUploadVO.setDeliverd(mailUploadVo.isDeliverd());
		return mailUploadVO;
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 */
	private void transferMailBagToCarrier(MailUploadVO mailUploadVo) throws MailHHTBusniessException {
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		if (mailUploadVo.getToDestination() == null || mailUploadVo.getToDestination().trim().length() == 0) {
			mailUploadVo.setToDestination(mailUploadVo.getScannedPort());
		}
		if (mailUploadVo.getToPOU() == null || mailUploadVo.getToPOU().trim().length() == 0) {
			mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
		}
		if (mailUploadVo.getContainerPol() == null || mailUploadVo.getContainerPol().trim().length() == 0) {
			mailUploadVo.setContainerPol(mailUploadVo.getScannedPort());
		}
		if (mailUploadVo.getContainerPOU() == null || mailUploadVo.getContainerPOU().trim().length() == 0) {
			mailUploadVo.setContainerPOU(mailUploadVo.getScannedPort());
		}
		if (mailUploadVo.getDestination() == null || mailUploadVo.getDestination().trim().length() == 0) {
			mailUploadVo.setDestination(mailUploadVo.getScannedPort());
		}
		mailUploadVo.setToCarrierCode(mailUploadVo.getToCarrierCode());
		if (mailUploadVo.getMailTag() != null && mailUploadVo.getMailTag().trim().length() > 0
				&& mailUploadVo.getMailTag().trim().length() == 29) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(mailUploadVo.getCompanyCode());
			mailbagVO.setMailbagId(mailUploadVo.getMailTag());
			mailbagVO.setDespatchSerialNumber(String.valueOf(mailUploadVo.getDsn()));
			mailbagVO.setOoe(mailUploadVo.getOrginOE());
			mailbagVO.setDoe(mailUploadVo.getDestinationOE());
			mailbagVO.setMailSubclass(mailUploadVo.getSubClass());
			mailbagVO.setMailCategoryCode(mailUploadVo.getCategory());
			mailbagVO.setYear(mailUploadVo.getYear());
			MailbagVO mailbagVOfromserver = Mailbag.findMailbagDetailsForUpload(mailbagVO);
			if (mailbagVOfromserver != null) {
				ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
				scannedMailDetailsVO.setProcessPoint(mailUploadVo.getScanType());
				boolean autoArrivalEnabled = false;
				try {
					autoArrivalEnabled = checkAutoArrival(scannedMailDetailsVO);
				} catch (MailMLDBusniessException | ForceAcceptanceException e) {
					log.error("" + SYSTEM_EXCEPTION_ERROR + " " + e);
				}
				if (mailbagVOfromserver.getUldNumber() != null && !" ".equals(mailbagVOfromserver.getUldNumber())
						&& !BULKARR.equals(mailbagVOfromserver.getUldNumber()) && !autoArrivalEnabled) {
					if (!"ARR".equals(mailbagVOfromserver.getLatestStatus())
							&& MailConstantsVO.FLAG_NO.equals(mailbagVOfromserver.getArrivedFlag())) {
						throw new MailHHTBusniessException(MailHHTBusniessException.INVALID_TRANSFER_EXCEPTION);
					}
				} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVOfromserver.getLatestStatus())) {
					throw new MailHHTBusniessException(MailHHTBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION);
				}
			}
		}
	}

	/**
	 * @author A-1885
	 * @return
	 */
	private void acceptMailBagFromPA(MailUploadVO mailUploadVo) throws MailHHTBusniessException {
		if (mailUploadVo.getCarrierCode() == null) {
			mailUploadVo.setCarrierCode(mailUploadVo.getCompanyCode());
		}
		if (!validateCarrierCode(mailUploadVo.getCarrierCode(), mailUploadVo.getCompanyCode())) {
			throw new MailHHTBusniessException(MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION);
		}
		if ("EXP".equals(mailUploadVo.getScanType()) && "".equals(mailUploadVo.getFlightNumber())
				&& mailUploadVo.getContainerNumber() != null && "".equals(mailUploadVo.getMailTag())) {
			mailUploadVo.setContainerType(MailConstantsVO.ULD_TYPE);
			if (mailUploadVo.getContainerPol() == null || mailUploadVo.getContainerPol().trim().length() == 0) {
				mailUploadVo.setContainerPol(mailUploadVo.getScannedPort());
			}
			mailUploadVo.setContainerPOU(mailUploadVo.getDestination());
			mailUploadVo.setToPOU(mailUploadVo.getDestination());
			if (mailUploadVo.getFlightNumber() == null || mailUploadVo.getFlightNumber().trim().length() == 0) {
				ContainerVO containerVo = new ContainerVO();
				containerVo.setCompanyCode(mailUploadVo.getCompanyCode());
				containerVo.setContainerNumber(mailUploadVo.getContainerNumber());
				containerVo.setAssignedPort(mailUploadVo.getScannedPort());
				ContainerAssignmentVO asnVo = mailController
						.findLatestContainerAssignment(mailUploadVo.getContainerNumber());
				if (asnVo != null) {
					if (asnVo.getDestination().equals(mailUploadVo.getDestination())
							&& MailConstantsVO.DESTN_FLT == (asnVo.getFlightSequenceNumber())) {
						throw new SystemException(MailOperationsBusinessException.MAILTRACKING_CONTAINERALREADYASSIGNED);
					}
					if ("Y".equals(asnVo.getArrivalFlag())) {
						if (asnVo.getFlightSequenceNumber() > 0) {
							if (MailConstantsVO.FLAG_YES.equals(asnVo.getReleasedFlag())) {
							} else {
								throw new SystemException(MailOperationsBusinessException.MAILTRACKING_ULD_NOTRELEASED);
							}
						}
					} else if (MailConstantsVO.DESTN_FLT == asnVo.getFlightSequenceNumber()) {
						mailUploadVo.setExpRsn(true);
					}
				}
			}
		} else {
			if (mailUploadVo.getContainerNumber() == null || mailUploadVo.getContainerNumber().trim().length() == 0) {
				String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
				if (storageUnit != null && storageUnit.contains("$")) {
					storageUnit = getKeyCondition(mailUploadVo, storageUnit);
				}
				mailUploadVo.setContainerNumber(storageUnit);
				mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
			} else {
				mailUploadVo.setContainerNumber(mailUploadVo.getContainerNumber());
				if ("BULK".equals(mailUploadVo.getContainerType())
						|| MailConstantsVO.BULK_TYPE.equals(mailUploadVo.getContainerType())) {
					mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
				} else {
					if (mailUploadVo.getContainerNumber().startsWith("MT")) {
						mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
					} else if (mailUploadVo.getContainerNumber().trim().length() >= 3 && "STR".equals(mailUploadVo
							.getContainerNumber().substring(mailUploadVo.getContainerNumber().length() - 3))) {
						mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
					} else {
						mailUploadVo.setContainerType(MailConstantsVO.ULD_TYPE);
					}
				}
				ContainerVO containerVo = new ContainerVO();
				containerVo.setCompanyCode(mailUploadVo.getCompanyCode());
				containerVo.setContainerNumber(mailUploadVo.getContainerNumber());
				containerVo.setAssignedPort(mailUploadVo.getScannedPort());
				if (mailUploadVo.getContainerNumber() != null
						&& MailConstantsVO.ULD_TYPE.equals(mailUploadVo.getContainerType())) {
					ContainerAssignmentVO latestasnVo = mailController
							.findLatestContainerAssignment(mailUploadVo.getContainerNumber());
					if (latestasnVo != null) {
						if ("Y".equals(latestasnVo.getArrivalFlag())) {
							if (latestasnVo.getFlightSequenceNumber() > 0) {
								if (MailConstantsVO.FLAG_YES.equals(latestasnVo.getReleasedFlag())) {
								} else {
									throw new SystemException(
											MailOperationsBusinessException.MAILTRACKING_ULD_NOTRELEASED);
								}
							}
						}
					}
				}
				ContainerAssignmentVO asnVo = mailController.findContainerAssignment(containerVo);
				if (asnVo != null) {
					if (!MailConstantsVO.FLAG_YES.equals(asnVo.getReleasedFlag())
							&& !MailConstantsVO.FLAG_NO.equals(asnVo.getTransitFlag())) {
						if (mailUploadVo.getFlightNumber() == null
								|| mailUploadVo.getFlightNumber().trim().length() == 0) {
							mailUploadVo.setFlightNumber(asnVo.getFlightNumber());
							mailUploadVo.setFlightSequenceNumber(asnVo.getFlightSequenceNumber());
							mailUploadVo.setFlightDate(asnVo.getFlightDate());
						}
						mailUploadVo.setCarrierId(
								mailUploadVo.getCarrierId() != 0 ? mailUploadVo.getCarrierId() : asnVo.getCarrierId());
						mailUploadVo.setCarrierCode(mailUploadVo.getCarrierCode() != null
								&& mailUploadVo.getCarrierCode().trim().length() > 0 ? mailUploadVo.getCarrierCode()
								: asnVo.getCarrierCode());
						mailUploadVo.setDestination(mailUploadVo.getDestination() != null
								&& mailUploadVo.getDestination().trim().length() > 0 ? mailUploadVo.getDestination()
								: asnVo.getDestination());
						mailUploadVo.setContainerPOU(mailUploadVo.getContainerPOU() != null
								&& mailUploadVo.getContainerPOU().trim().length() > 0 ? mailUploadVo.getContainerPOU()
								: asnVo.getPou());
					}
				}
			}
			if (mailUploadVo.getDestination() == null || mailUploadVo.getDestination().trim().length() == 0) {
				mailUploadVo.setDestination(mailUploadVo.getScannedPort());
			}
			if (mailUploadVo.getMailTag() != null && mailUploadVo.getMailTag().trim().length() == 29) {
				mailUploadVo.setContainerPol(mailUploadVo.getMailTag().substring(2, 5));
			} else {
				if (mailUploadVo.getContainerPol() == null || mailUploadVo.getContainerPol().trim().length() == 0) {
					mailUploadVo.setContainerPol(mailUploadVo.getScannedPort());
				}
			}
			if (mailUploadVo.getContainerPOU() == null || mailUploadVo.getContainerPOU().trim().length() == 0) {
				mailUploadVo.setContainerPOU(mailUploadVo.getScannedPort());
			}
			if (mailUploadVo.getToPOU() == null || mailUploadVo.getToPOU().trim().length() == 0) {
				if (mailUploadVo.getFlightNumber() != null && mailUploadVo.getFlightNumber().trim().length() > 0) {
					mailUploadVo.setToPOU(mailUploadVo.getContainerPOU());
				} else {
					mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
				}
			}
		}
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 */
	private void returnMailBagToPA(MailUploadVO mailUploadVo) {
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_RETURNED);
		mailUploadVo.setDamageCode(mailUploadVo.getReturnCode());
		mailUploadVo.setDamageRemarks(mailUploadVo.getRemarks());
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 */
	private void deliveryMailbag(MailUploadVO mailUploadVo) {
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_DELIVERED);
		mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @return
	 */
	private Collection<MailUploadVO> deliverContainer(MailUploadVO mailUploadVo) {
		Collection<MailUploadVO> mailVos = new ArrayList<MailUploadVO>();
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_DELIVERED);
		mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
		mailUploadVo.setDeliverd(true);
		Collection<MailUploadVO> mails = findmailBagAndULDDetails(mailUploadVo);
		if (mails != null && mails.size() > 0) {
			for (MailUploadVO mailVo : mails) {
				MailUploadVO uploadVo = cloneMailUploadVO(mailUploadVo);
				uploadVo.setMailTag(mailVo.getMailTag());
				uploadVo.setContainerNumber(mailVo.getContainerNumber());
				uploadVo.setContainerPOU(mailVo.getContainerPOU());
				if (uploadVo.getMailTag() != null && uploadVo.getMailTag().trim().length() > 0) {
					splitMailTagId(uploadVo);
				}
				if (mailVo.getContainerNumber() != null) {
					uploadVo.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
				}
				mailVos.add(uploadVo);
			}
		} else {
			throw new SystemException(MailOperationsBusinessException.MAILTRACKING_NOMAILBAGSFOUNDFORDELIVERY);
		}
		return mailVos;
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @throws SystemException
	 */
	private void removeMailBagFromContainer(MailUploadVO mailUploadVo) {
		if (mailUploadVo.getMailTag() != null && mailUploadVo.getMailTag().trim().length() > 0) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(mailUploadVo.getCompanyCode());
			mailbagVO.setMailbagId(mailUploadVo.getMailTag());
			mailbagVO.setDespatchSerialNumber(String.valueOf(mailUploadVo.getDsn()));
			mailbagVO.setOoe(mailUploadVo.getOrginOE());
			mailbagVO.setDoe(mailUploadVo.getDestinationOE());
			mailbagVO.setMailSubclass(mailUploadVo.getSubClass());
			mailbagVO.setMailCategoryCode(mailUploadVo.getCategory());
			mailbagVO.setYear(mailUploadVo.getYear());
			MailbagVO mailbagVOfromserver = Mailbag.findMailbagDetailsForUpload(mailbagVO);
			if (mailbagVOfromserver != null) {
				if ("C".equals(mailbagVOfromserver.getFlightStatus())) {
					mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
					if (mailUploadVo.getOffloadReason() == null
							|| mailUploadVo.getOffloadReason().trim().length() == 0) {
						mailUploadVo.setOffloadReason(findSystemParameterValue(DEFAULT_OFFLOADCODE));
					}
					mailUploadVo.setRemarks(DEFAULT_OFFLOAREMARKS);
				} else {
					String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
					if (storageUnit != null && storageUnit.contains("$")) {
						storageUnit = getKeyCondition(mailUploadVo, storageUnit);
					}
					mailUploadVo.setContainerNumber(storageUnit);
					mailUploadVo.setToContainer(mailUploadVo.getContainerNumber());
					mailUploadVo.setToDestination(mailUploadVo.getScannedPort());
					mailUploadVo.setDestination(mailUploadVo.getScannedPort());
					mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
					mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
					mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				}
			}
		} else if (mailUploadVo.getContainerNumber() != null && mailUploadVo.getContainerNumber().trim().length() > 0) {
			ContainerAssignmentVO latestContainerAssignmentVO = null;
			mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
			mailUploadVo.setDestination(mailUploadVo.getScannedPort());
			try {
				latestContainerAssignmentVO = mailController
						.findLatestContainerAssignment(mailUploadVo.getContainerNumber());
			} finally {
			}
			if (latestContainerAssignmentVO != null) {
				OperationalFlightVO opFlightVo = createOperationalFlightVO(latestContainerAssignmentVO);
				if (!isFlightClosedForOperations(opFlightVo)) {
					mailUploadVo.setToDestination(mailUploadVo.getScannedPort());
					mailUploadVo.setToContainer(mailUploadVo.getContainerNumber());
					mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
					if (mailUploadVo.getCarrierCode() == null || "".equals(mailUploadVo.getCarrierCode())) {
						mailUploadVo.setCarrierCode(mailUploadVo.getCompanyCode());
					}
				} else {
					mailUploadVo.setMailTag(mailUploadVo.getContainerNumber());
					mailUploadVo.setContainerNumber(null);
					mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					mailUploadVo.setOffloadReason(findSystemParameterValue(DEFAULT_OFFLOADCODE));
				}
			} else {
				throw new SystemException(MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN);
			}
		}
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 */
	private void arriveMailBagFromFlight(MailUploadVO mailUploadVo) throws MailHHTBusniessException {
		FlightValidationVO flightValidationVO = null;
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
		Collection<FlightValidationVO> flightValidationVOs = null;
		if (mailUploadVo.getFlightValidationVOS() != null && mailUploadVo.getFlightValidationVOS().size() > 0) {
			flightValidationVOs = mailUploadVo.getFlightValidationVOS();
		} else {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(mailUploadVo.getCompanyCode());
			flightFilterVO.setStation(mailUploadVo.getScannedPort());
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailUploadVo.getFlightDate()));
			flightFilterVO.setCarrierCode(mailUploadVo.getCarrierCode());
			flightFilterVO.setFlightNumber(mailUploadVo.getFlightNumber());
			flightValidationVOs = mailController.validateFlight(flightFilterVO);
		}
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		boolean isTobeActionedOrCanceled = checkIfFlightToBeCancelled(flightValidationVOs, scannedMailDetailsVO,
				mailUploadVo.getScannedPort());
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			for (FlightValidationVO vo : flightValidationVOs) {
				if ((FlightValidationVO.FLT_STATUS_ACTIVE.equals(vo.getFlightStatus()) || !isTobeActionedOrCanceled)
						&& vo.getLegDestination().equals(mailUploadVo.getScannedPort())) {
					flightValidationVO = vo;
					break;
				}
			}
			if (flightValidationVO == null) {
				throw new MailHHTBusniessException(MailOperationsBusinessException.MAILTRACKING_INVALIDFLIGHT);
			}
		} else {
			throw new MailHHTBusniessException(MailOperationsBusinessException.MAILTRACKING_INVALIDFLIGHT);
		}
		mailUploadVo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		mailUploadVo.setCarrierId(flightValidationVO.getFlightCarrierId());
		mailUploadVo.setContainerPol(flightValidationVO.getLegOrigin());
		mailUploadVo.setContainerPOU(mailUploadVo.getScannedPort());
		mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
		Collection<MailUploadVO> mailvos = findmailBagAndULDDetails(mailUploadVo);
		MailUploadVO mailVo = null;
		if (mailvos != null && mailvos.size() > 0) {
			for (MailUploadVO uploadVo : mailvos) {
				mailVo = uploadVo;
				break;
			}
		}
		if (mailVo != null) {
			if (mailVo.getContainerNumber().startsWith("MT") || ("MLD".equals(mailVo.getMailSource())
					&& MailConstantsVO.BULK_TYPE.equals(mailVo.getContainerType()))) {
				mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
				StringBuilder containerNo = new StringBuilder().append("BULK-").append(mailUploadVo.getScannedPort());
				mailUploadVo.setContainerNumber(containerNo.toString());
			} else {
				if (mailVo.getContainerType() != null && !"".equals(mailVo.getContainerType())) {
					mailUploadVo.setContainerType(mailVo.getContainerType());
				} else {
					mailUploadVo.setContainerType(MailConstantsVO.ULD_TYPE);
				}
				mailUploadVo.setContainerNumber(mailVo.getContainerNumber());
				mailUploadVo.setDestination(mailVo.getDestination());
			}
		} else {
			mailUploadVo.setContainerType(MailConstantsVO.BULK_TYPE);
			StringBuilder containerNo = new StringBuilder().append("BULK-").append(mailUploadVo.getScannedPort());
			mailUploadVo.setContainerNumber(containerNo.toString());
		}
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @return
	 */
	private Collection<MailUploadVO> arriveULDFromFlight(MailUploadVO mailUploadVo) {
		Collection<MailUploadVO> mailVos = new ArrayList<MailUploadVO>();
		FlightValidationVO flightValidationVO = null;
		mailUploadVo.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
		Collection<FlightValidationVO> flightValidationVOs = null;
		if (mailUploadVo.getFlightValidationVOS() != null && mailUploadVo.getFlightValidationVOS().size() > 0) {
			flightValidationVOs = mailUploadVo.getFlightValidationVOS();
		} else {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(mailUploadVo.getCompanyCode());
			flightFilterVO.setStation(mailUploadVo.getScannedPort());
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailUploadVo.getFlightDate()));
			flightFilterVO.setCarrierCode(mailUploadVo.getCarrierCode());
			flightFilterVO.setFlightNumber(mailUploadVo.getFlightNumber());
			flightValidationVOs = mailController.validateFlight(flightFilterVO);
		}
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO vo : flightValidationVOs) {
				if (FlightValidationVO.FLT_STATUS_ACTIVE.equals(vo.getFlightStatus()) & (vo.getCarrierCode() != null
						&& vo.getCarrierCode().equalsIgnoreCase(mailUploadVo.getCarrierCode()))) {
					flightValidationVO = vo;
					break;
				}
			}
			if (flightValidationVO == null) {
				throw new SystemException(MailOperationsBusinessException.MAILTRACKING_INVALIDFLIGHT);
			}
		} else {
			throw new SystemException(MailOperationsBusinessException.MAILTRACKING_INVALIDFLIGHT);
		}
		mailUploadVo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		mailUploadVo.setCarrierId(flightValidationVO.getFlightCarrierId());
		mailUploadVo.setContainerPol(flightValidationVO.getLegOrigin());
		mailUploadVo.setContainerPOU(mailUploadVo.getScannedPort());
		mailUploadVo.setToPOU(mailUploadVo.getScannedPort());
		Collection<MailUploadVO> mailbagsFromQuery = findmailBagAndULDDetails(mailUploadVo);
		if (mailbagsFromQuery != null && mailbagsFromQuery.size() > 0) {
			for (MailUploadVO vo : mailbagsFromQuery) {
				MailUploadVO uploadVo = cloneMailUploadVO(mailUploadVo);
				uploadVo.setMailTag(vo.getMailTag());
				uploadVo.setContainerNumber(vo.getContainerNumber());
				uploadVo.setContainerType(vo.getContainerType());
				uploadVo.setContainerPol(vo.getContainerPol());
				uploadVo.setContainerPOU(vo.getContainerPOU());
				uploadVo.setDestination(vo.getDestination());
				if (uploadVo.getMailTag() != null && uploadVo.getMailTag().trim().length() > 0) {
					splitMailTagId(uploadVo);
				}
				if (vo.getContainerNumber() != null && MailConstantsVO.ULD_TYPE.equals(vo.getContainerType())) {
					uploadVo.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
				}
				mailVos.add(uploadVo);
			}
		} else {
			MailUploadVO uploadVoclone = cloneMailUploadVO(mailUploadVo);
			mailVos.add(uploadVoclone);
		}
		return mailVos;
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @return
	 */
	private Collection<MailUploadVO> findmailBagAndULDDetails(MailUploadVO mailUploadVo) {
		return Mailbag.findMailBagandContainers(mailUploadVo);
	}

	/**
	 * @author A-1885
	 * @throws SystemException
	 */
	private Collection<MailbagVO> deleteMailbag(Collection<MailUploadVO> mailUploadVos) {
		Collection<MailbagVO> errors = new ArrayList<MailbagVO>();
		for (MailUploadVO mailUploadVo : mailUploadVos) {
			Mailbag mailBag = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailUploadVo.getCompanyCode());
			String dsn1 = String.valueOf(mailUploadVo.getDsn());
			StringBuilder finalDsn = new StringBuilder();
			if (dsn1.length() == 0) {
				finalDsn = finalDsn.append("0000");
			} else if (dsn1.length() == 1) {
				finalDsn = finalDsn.append("000").append(dsn1);
			} else if (dsn1.length() == 2) {
				finalDsn.append("00").append(dsn1);
			} else if (dsn1.length() == 3) {
				finalDsn.append("0").append(dsn1);
			} else if (dsn1.length() == 4) {
				finalDsn.append(dsn1);
			}
			long sequencenum = 0;
			try {
				sequencenum = Mailbag.findMailBagSequenceNumberFromMailIdr(mailUploadVo.getMailTag(),
						mailUploadVo.getCompanyCode());
			} finally {
			}
			mailbagPk.setMailSequenceNumber(sequencenum);
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				log.error(FINDER_EXCEPTION_CAUGHT);
				throw new SystemException(MailOperationsBusinessException.MAILBAG_DOESNOTEXISTS);
			}
			if (mailBag != null) {
				if (mailBag.getFlightSequenceNumber() > 0) {
					throw new SystemException(MailOperationsBusinessException.MAILBAG_CANNOTBEDELETED);
				}
				ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
				mailhistories = (ArrayList<MailbagHistoryVO>) Mailbag
						.findMailbagHistories(mailUploadVo.getCompanyCode(), mailUploadVo.getMailTag(), 0l);
				if (mailhistories != null && mailhistories.size() > 0) {
					for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
						if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())) {
							throw new SystemException(MailOperationsBusinessException.MAILBAG_CANNOTBEDELETED);
						}
					}
				}
				mailBag.remove();
			}
		}
		return errors;
	}

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 */
	private void splitMailTagId(MailUploadVO mailUploadVo) {
		mailUploadVo.setOrginOE(mailUploadVo.getMailTag().substring(0, 6));
		mailUploadVo.setDestinationOE(mailUploadVo.getMailTag().substring(6, 12));
		mailUploadVo.setCategory(mailUploadVo.getMailTag().substring(12, 13));
		mailUploadVo.setSubClass(mailUploadVo.getMailTag().substring(13, 15));
		mailUploadVo.setYear(Integer.parseInt(mailUploadVo.getMailTag().substring(15, 16)));
		mailUploadVo.setDsn(mailUploadVo.getMailTag().substring(16, 20));
		mailUploadVo.setRsn(mailUploadVo.getMailTag().substring(20, 23));
		mailUploadVo.setHighestnumberIndicator(mailUploadVo.getMailTag().substring(23, 24));
		mailUploadVo.setRegisteredIndicator(mailUploadVo.getMailTag().substring(24, 25));
		mailUploadVo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(Double.parseDouble(mailUploadVo.getMailTag().substring(25, 29)))));
	}

	private String overrideScanTypeForDLVMessage(String scanType, MailUploadVO mailUploadVO) {
		if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(scanType)) {
			scanType = MailConstantsVO.MAIL_STATUS_ARRIVED;
			mailUploadVO.setDeliverd(true);
		}
		return scanType;
	}
	/**
	 * Method		:	MLDMessageProcessor.generateTrolleyNumber Added by 	:	A-4803 on 14-Nov-2014 Used for 	:	Generating trolley number Parameters	:	@param mLDMasterVO Parameters	:	@param dummyTrolleyNumberMap Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws GenerationFailedException Parameters	:	@throws ProxyException  Return type	: 	String
	 */
	private String generateTrolleyNumber(MLDMasterVO mLDMasterVO, Map<String, String> dummyTrolleyNumberMap)
			 {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		log.debug("MLDMessageProcessor" + " : " + "generateTrolleyNumber" + " Entering");
		String keyFormat = findKeyFormat();
		String serialNumberForcarrier = "";
		MLDDetailVO mLDDetailVO = mLDMasterVO.getMldDetailVO();
		mLDDetailVO.setCarrier(mLDDetailVO.getCarrierCodeOub());
		if (mLDDetailVO.getFlightOperationDateOub() != null) {
			String day = mLDDetailVO.getFlightOperationDateOub().toString().substring(0, 2);
			mLDDetailVO.setFlightDay(day);
		}
		if (mLDDetailVO.getFlightNumberOub() != null && !"-1".equals(mLDDetailVO.getFlightNumberOub())) {
			if (mLDDetailVO.getFlightNumberOub().length() > 4) {
				mLDDetailVO.setFlight(mLDDetailVO.getFlightNumberOub().substring(0, 4));
			} else {
				mLDDetailVO.setFlight(mLDDetailVO.getFlightNumberOub());
			}
		} else {
			if ((mLDDetailVO.getMailModeOub() == null || mLDDetailVO.getFlightNumberOub() == null
					|| "-1".equals(mLDDetailVO.getFlightNumberOub()))
					&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_STG.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde()))) {
				mLDDetailVO.setFlight("XXXX");
				mLDDetailVO.setFlightDay("XX");
				String defaultSU = findSystemParameterValue("mailtracking.defaults.defaultstorageunitforMLDairports");
				StringBuilder uldNumberForCarrier = new StringBuilder();
				if (defaultSU == null) {
					defaultSU = "STORE";
				}
				serialNumberForcarrier = uldNumberForCarrier.append(logonAttributes.getOwnAirlineCode()).append("-")
						.append(defaultSU).append("-")
						.append(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getDestAirport()))
						.toString();
			}
		}
		if (mLDMasterVO.getSenderAirport() != null) {
			mLDDetailVO.setAirport(mLDMasterVO.getSenderAirport());
		} else if (mLDDetailVO.getPouOub() != null) {
			mLDDetailVO.setAirport(mLDDetailVO.getPouOub());
		}
		String serialNumber = "";
		String keyCondition = getKeyCondition(mLDDetailVO, keyFormat);
		String dummyTrolleyNumberMapKey = mLDDetailVO.getCompanyCode() + mLDDetailVO.getPouOub() + keyCondition;
		if (serialNumberForcarrier != null && serialNumberForcarrier.trim().length() > 0) {
			serialNumber = serialNumberForcarrier;
		} else if (dummyTrolleyNumberMap.get(dummyTrolleyNumberMapKey) != null) {
			serialNumber = dummyTrolleyNumberMap.get(dummyTrolleyNumberMapKey);
		} else {
			serialNumber = generateDummyTrolleyNumberForMLD(mLDMasterVO, keyCondition);
			dummyTrolleyNumberMap.put(dummyTrolleyNumberMapKey, serialNumber);
		}
		log.debug("MLDMessageProcessor" + " : " + "generateTrolleyNumberEnds" + " Exiting");
		return serialNumber;
	}

	/**
	 * Method		:	MLDMessageProcessor.getKeyCondition Added by 	:	A-4803 on 14-Nov-2014 Used for 	:	getting key condition Parameters	:	@param mLDDetailVO Parameters	:	@param keyFormat Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	 */
	public String getKeyCondition(MLDDetailVO mLDDetailVO, String keyFormat) {
		log.debug("MLDMessageProcessor" + " : " + MailConstantsVO.KEYCONDITION + " Entering");
		String displayString = "";
		Map<String, MLDDetailVO> templateObject = new HashMap<String, MLDDetailVO>();
		templateObject.put("mail", mLDDetailVO);
		ZonedDateTime flightDate=mLDDetailVO.getFlightOperationDateOub()!=null?mLDDetailVO.getFlightOperationDateOub():mLDDetailVO.getFlightOperationDateInb();
		if(flightDate!=null){
		displayString="MT"+mLDDetailVO.getCarrier()+ mLDDetailVO.getFlight()+flightDate.toLocalDate().
				format(DateTimeFormatter.ofPattern("dd"));}
		else{

		displayString="MT"+mLDDetailVO.getCarrier()+ mLDDetailVO.getFlight()+mLDDetailVO.getFlightDay();
		}
		log.debug("MLDMessageProcessor;" + " : " + MailConstantsVO.KEYCONDITION + " Exiting");
		return displayString;
	}

	/**
	 * Method		:	MLDMessageProcessor.findKeyFormat Added by 	:	A-4803 on 14-Nov-2014 Used for 	:	finding key format Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws ProxyException  Return type	: 	String
	 */
	private String findKeyFormat() {
		log.debug("MLDMessageProcessor" + " : " + "findKeyFormat" + " Entering");
		String keyFormat = "";
		Collection<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(DUMMY_TROLLEY_ID_FORMAT);
		Map<String, String> mailIDFormatMap = null;
		try {
			mailIDFormatMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		if (mailIDFormatMap != null) {
			keyFormat = mailIDFormatMap.get(DUMMY_TROLLEY_ID_FORMAT);
		}
		log.debug("MLDMessageProcessor" + " : " + "findKeyFormat" + " Exiting");
		return keyFormat;
	}

	/**
	 * Method            :     MailUploadController.validateMailBagsForUPL Added by    :     A-4803 on 24-Nov-2014 Used for    :     validating mail bags for MLD UPL Parameters  :     @param flightValidationVO Parameters  :     @return Parameters  :     @throws SystemException  Return type :     Collection<String>
	 */
	private Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO) {
		return Mailbag.validateMailBagsForUPL(flightValidationVO);
	}

	/**
	 * Added by    :     A-4810 on 29-Nov-2014
	 * @return
	 */
	public boolean validateCarrierCode(String carrierCode, String companyCode) {
		log.debug("MAILCONTROLLER" + " : " + "validateCarrierCode" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		if (carrierCode != null && !"".equals(carrierCode)) {
			try {
				airlineValidationVO = sharedAirlineProxy.validateAlphaCode(companyCode, carrierCode);
			} catch (SharedProxyException e) {
				return false;
			}
			if (airlineValidationVO != null) {
				log.info("true");
				return true;
			}
		}
		log.info("false");
		return false;
	}
	/**
	 * @param opFltVO
	 * @param handover
	 * @param mailBag
	 * @param mailUploadVO
	 */
	private void constructMailUploadVOForMRD(Collection<OperationalFlightVO> opFltVO, HandoverVO handover,
											 String mailBag, MailUploadVO mailUploadVO) {
		mailUploadVO.setMailTag(mailBag.trim());
		ContainerAssignmentVO containerVO = null;
		boolean mailbagPresent = true;
		HandoverVO mailHandover = null;
		Collection<OperationalFlightVO> opFltVOS = null;
		mailHandover = constructMailHandover(handover, mailBag, mailUploadVO);
		try {
			long mailSeq = findMailSequenceNumber(mailBag, mailUploadVO.getCompanyCode());
			if (mailSeq > 0) {
				constructMailHandoverForExisting(mailHandover, mailBag, mailUploadVO);
				opFltVOS = Mailbag.findOperationalFlightForMRD(mailHandover);
			}
		} finally {
		}
		if (handover.getFlightNumber() == null || handover.getFlightNumber().trim().length() == 0
				|| "0000".equals(handover.getFlightNumber())) {
			if (!mailbagPresent) {
				mailUploadVO.setCarrierCode(opFltVO.iterator().next().getCarrierCode());
				mailUploadVO.setFlightNumber(opFltVO.iterator().next().getFlightNumber());
				if (opFltVO.iterator().next().getArrToDate() != null) {
					mailUploadVO.setFlightDate(opFltVO.iterator().next().getArrToDate());
				} else {
					mailUploadVO.setFlightDate(opFltVO.iterator().next().getFlightDate());
				}
				mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
				mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
				mailUploadVO.setContainerNumber("BULK-" + opFltVO.iterator().next().getPou());
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVO.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVO.iterator().next().getPou());
				return;
			} else {
				if (opFltVOS != null && !opFltVOS.isEmpty()) {
					handleExistingMailbags(mailUploadVO, opFltVOS, mailbagPresent, mailBag, mailHandover);
					return;
				}
			}
		}
		if (opFltVO != null && !opFltVO.isEmpty() && opFltVO.iterator().next().getCarrierCode() != null
				&& opFltVO.iterator().next().getFlightNumber() != null
				&& opFltVO.iterator().next().getFlightDate() != null) {
			mailUploadVO.setCarrierCode(opFltVO.iterator().next().getCarrierCode());
			mailUploadVO.setFlightNumber(opFltVO.iterator().next().getFlightNumber());
			if (opFltVO.iterator().next().getArrToDate() != null) {
				mailUploadVO.setFlightDate(opFltVO.iterator().next().getArrToDate());
			} else {
				mailUploadVO.setFlightDate(opFltVO.iterator().next().getFlightDate());
			}
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
			mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
			try {
				containerVO = Mailbag.findContainerDetailsForMRD(opFltVO.iterator().next(), mailBag);
				if (containerVO != null) {
					containerVO = Container.findLatestContainerAssignment(mailUploadVO.getCompanyCode(),
							containerVO.getContainerNumber());
				}
			} finally {
			}
			if (containerVO != null && mailbagPresent && containerVO.getFlightNumber() != null
					&& containerVO.getCarrierCode() != null && containerVO.getFlightDate() != null) {
				if (containerVO.getFlightNumber().equals(handover.getFlightNumber())
						&& containerVO.getCarrierCode().equals(handover.getCarrierCode())) {
					mailUploadVO.setFlightNumber(containerVO.getFlightNumber());
					mailUploadVO.setCarrierCode(containerVO.getCarrierCode());
					mailUploadVO.setFlightDate(containerVO.getFlightDate());
				}
				mailUploadVO.setContainerNumber(containerVO.getContainerNumber());
				mailUploadVO.setContainerType(containerVO.getContainerType());
				mailUploadVO.setContainerPol(containerVO.getAirportCode());
				mailUploadVO.setContainerPOU(containerVO.getPou());
			} else {
				mailUploadVO.setContainerNumber("BULK-" + opFltVO.iterator().next().getPou());
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVO.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVO.iterator().next().getPou());
			}
		} else {
			mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_DELIVERED);
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DELIVERED);
		}
	}

	public HandoverVO constructMailHandover(HandoverVO handover, String mailBag, MailUploadVO mailUploadVO) {
		HandoverVO mailHandover = new HandoverVO();
		mailHandover.setCompanyCode(mailUploadVO.getCompanyCode());
		mailHandover.setDestAirport(handover.getDestAirport());
		mailHandover.setHandOverdate_time(handover.getHandOverdate_time());
		mailUploadVO.setToPOU(
				"POD".equals(handover.getHandOverType()) ? handover.getDestAirport() : handover.getDestination());
		mailUploadVO.setDateTime(handover.getDateTime());
		mailUploadVO.setScannedDate(handover.getHandOverdate_time());
		mailUploadVO.setScannedPort(handover.getDestAirport());
		mailUploadVO.setDsn(mailBag.substring(16, 20));
		mailUploadVO.setOrginOE(mailBag.substring(0, 6));
		mailUploadVO.setDestinationOE(mailBag.substring(6, 12));
		mailUploadVO.setSubClass(mailBag.substring(13, 15));
		mailUploadVO.setCategory(mailBag.substring(12, 13));
		mailUploadVO.setYear(Integer.parseInt(mailBag.substring(15, 16)));
		mailUploadVO.setScanUser("ICOADMIN");
		mailUploadVO.setDeliverd(true);
		mailUploadVO.setMailSource("MRD");
		return mailHandover;
	}

	public void constructMailHandoverForExisting(HandoverVO mailHandover, String mailBag, MailUploadVO mailUploadVO) {
		MailbagVO mailVO = null;
		MailbagVO mailFilterVO = new MailbagVO();
		mailFilterVO.setCompanyCode(mailUploadVO.getCompanyCode());
		mailFilterVO.setMailbagId(mailBag);
		mailFilterVO.setDespatchSerialNumber(mailBag.substring(16, 20));
		mailFilterVO.setOoe(mailBag.substring(0, 6));
		mailFilterVO.setDoe(mailBag.substring(6, 12));
		mailFilterVO.setMailSubclass(mailBag.substring(13, 15));
		mailFilterVO.setMailCategoryCode(mailBag.substring(12, 13));
		mailFilterVO.setYear(Integer.parseInt(mailBag.substring(15, 16)));
		mailVO = Mailbag.findMailbagDetailsForUpload(mailFilterVO);
		mailHandover.setFlightNumber(mailVO.getFlightNumber());
		mailHandover.setCarrierCode(mailVO.getCarrierCode());
		mailHandover.setDestination(mailVO.getPou());
		mailHandover.setFltSeqNum(mailVO.getFlightSequenceNumber());
		mailHandover.setFlightDate(mailVO.getFlightDate());
	}
	/**
	 * Method for Split MailBag details from File
	 * @param data
	 * @return
	 */
	private MailUploadVO constructMailUploadVOFromScanData(String data) {
		String[] splitData = null;
		MailUploadVO mailUploadVO = null;
		splitData = data.split(";");
		mailUploadVO = new MailUploadVO();
		mailUploadVO.setScanType(splitData[0]);
		mailUploadVO.setMailCompanyCode(splitData[1]);
		mailUploadVO.setCarrierCode(splitData[2]);
		mailUploadVO.setFlightNumber(splitData[3]);
		if (splitData[4] != null && splitData[4].trim().length() > 0) {
			ZonedDateTime flightDate = localDateUtil.getLocalDate(null, false);
			flightDate = LocalDate.withDate(flightDate, splitData[4]);
			mailUploadVO.setFlightDate(flightDate);
		}
		if (splitData[5] != null && splitData[5].trim().length() > 0) {
			mailUploadVO.setContainerPOU(splitData[5]);
		} else {
			if (splitData[13].trim().length() == 29) {
				mailUploadVO.setContainerPOU(splitData[13].substring(8, 11));
			}
		}
		mailUploadVO.setContainerNumber(splitData[6]);
		mailUploadVO.setContainerType(splitData[7]);
		mailUploadVO.setCompanyCode(splitData[8]);
		mailUploadVO.setDestination(splitData[9]);
		if (splitData[13].trim().length() == 29) {
			mailUploadVO.setContainerPol(splitData[13].substring(2, 5));
		}
		mailUploadVO.setRemarks(splitData[12]);
		mailUploadVO.setMailTag(splitData[13]);
		mailUploadVO.setDateTime(splitData[14]);
		mailUploadVO.setDamageCode(splitData[15]);
		mailUploadVO.setDamageRemarks(splitData[16]);
		mailUploadVO.setOffloadReason(splitData[17]);
		if (splitData[18] != null && splitData[18].trim().length() > 0) {
			mailUploadVO.setReturnCode(splitData[18]);
		}
		if (mailUploadVO.getScanType().equals(MailConstantsVO.MAIL_STATUS_RETURNED) && "N".equals(splitData[18])) {
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DAMAGED);
		}
		mailUploadVO.setToContainer(splitData[19]);
		mailUploadVO.setToCarrierCode(splitData[20]);
		mailUploadVO.setToFlightNumber(splitData[21]);
		if (splitData[22] != null && splitData[22].trim().length() > 0) {
			ZonedDateTime toFlightDate = localDateUtil.getLocalDate(null, false);
			toFlightDate = LocalDate.withDate(toFlightDate, splitData[22]);
			mailUploadVO.setToFlightDate(toFlightDate);
		}
		if (splitData[5] != null && splitData[5].trim().length() > 0) {
			mailUploadVO.setToPOU(splitData[5]);
			mailUploadVO.setToDestination(splitData[5]);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailUploadVO.getScanType())) {
			mailUploadVO.setToPOU(splitData[23]);
			mailUploadVO.setToDestination(splitData[24]);
		} else {
			if (splitData[13].trim().length() == 29) {
				mailUploadVO.setToPOU(splitData[13].substring(8, 11));
				mailUploadVO.setToDestination(splitData[13].substring(8, 11));
			}
		}
		if (mailUploadVO.getMailTag().trim().length() == 29) {
			mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(6, 12));
			mailUploadVO.setCategory(splitData[13].substring(12, 13));
			mailUploadVO.setSubClass(splitData[13].substring(13, 15));
			mailUploadVO.setYear(Integer.parseInt(splitData[13].substring(15, 16)));
		}
		if (splitData[30] != null && splitData[30].trim().length() > 0) {
			mailUploadVO.setDsn(splitData[30]);
		}
		mailUploadVO.setConsignmentDocumentNumber(splitData[31]);
		mailUploadVO.setPaCode(splitData[32]);
		if (splitData[33] != null && splitData[33].trim().length() > 0) {
			mailUploadVO.setTotalBag(Integer.parseInt(splitData[33]));
		}
		if (splitData[34] != null && splitData[34].trim().length() > 0) {
			mailUploadVO.setTotalWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(Double.parseDouble(splitData[34]))));
		}
		if (splitData[35] != null && splitData[35].trim().length() > 0) {
			mailUploadVO.setBags(Integer.parseInt(splitData[35]));
		}
		if (splitData[36] != null && splitData[36].trim().length() > 0) {
			mailUploadVO.setWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(Double.parseDouble(splitData[36]))));
		}
		if (splitData[37] != null && splitData[37].trim().length() > 0) {
			mailUploadVO.setIntact(Boolean.parseBoolean(splitData[37]));
		}
		if (splitData[38] != null && splitData[38].trim().length() > 0) {
			mailUploadVO.setSerialNumber(Integer.parseInt(splitData[38]));
		}
		mailUploadVO.setCirCode(splitData[39]);
		if (splitData[40] != null && splitData[40].trim().length() > 0) {
			if ("Y".equals(splitData[40]) || splitData[0].equals(MailConstantsVO.MAIL_STATUS_DELIVERED)) {
				mailUploadVO.setDeliverd(true);
			} else {
				mailUploadVO.setDeliverd(false);
			}
		}
		mailUploadVO.setScanUser(splitData[41]);
		mailUploadVO.setFromCarrierCode(splitData[43]);
		if (splitData.length > 45 && splitData[45] != null && splitData[45].trim().length() >= 3) {
			mailUploadVO.setFromPol(splitData[45].substring(0, 3));
		}
		mailUploadVO.setMailSource(MailConstantsVO.MTKMALUPLJOB);
		return mailUploadVO;
	}

	/**
	 * @param mailMRDMessageVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @throws ForceAcceptanceException
	 * @throws MailHHTBusniessException
	 */
	public Collection<ErrorVO> handleMRDMessage(MailMRDVO mailMRDMessageVO)
			throws MailOperationsBusinessException, ForceAcceptanceException {
		Collection<ErrorVO> allErrorVOs = new ArrayList<>();
		Collection<OperationalFlightVO> opFltVO = null;
		if (mailMRDMessageVO.getHandovers() != null && mailMRDMessageVO.getHandovers().get("valid_handovers") != null) {
			for (HandoverVO handover : mailMRDMessageVO.getHandovers().get("valid_handovers")) {
				List<String> mailbags = handover.getMailId();
				if (mailbags != null && !mailbags.isEmpty()) {
					for (String mailBag : mailbags) {
						if ("POD".equals(handover.getHandOverType())) {
							constructAndValidateMailBags(mailMRDMessageVO, handover, mailBag);
							if (mailMRDMessageVO.getHandoverErrors() == null
									|| mailMRDMessageVO.getHandoverErrors().isEmpty()) {
								handover.setCompanyCode(mailMRDMessageVO.getCompanyCode());
								try {
									opFltVO = Mailbag.findOperationalFlightForMRD(handover);
								} finally {
								}
								if (opFltVO == null || opFltVO.isEmpty()) {
									ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.mailnotmanifested");
									Object[] errorData = new Object[2];
									errorData[0] = mailBag;
									errorData[1] = handover.getHandOverID();
									errorVO.setErrorData(errorData);
									allErrorVOs.add(errorVO);
									continue;
								}
								allErrorVOs = handleMessageForMRDPOD(mailMRDMessageVO, opFltVO, handover, mailBag);
							} else {
								allErrorVOs.addAll(mailMRDMessageVO.getHandoverErrors());
							}
						} else {
							allErrorVOs.addAll(handleMessageForMRDPOC(mailMRDMessageVO, handover, mailBag));
						}
					}
				}
			}
		}
		if (mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS) != null
				&& !mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS).isEmpty()) {
			allErrorVOs = handleInvalidHandover(mailMRDMessageVO);
		}
		return allErrorVOs;
	}

	/* * @return
	 */
	public void constructAndValidateMailBags(MailMRDVO mailMRDMessageVO, HandoverVO handoverVO, String mailBag) {
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Collection<EmbargoDetailsVO> embargoDetailVos = null;
		boolean isValidFlight = true;
		boolean isErrorAdded = false;
		boolean isValidFlightCarrier = true;
		boolean isValidFlightSegment = false;
		boolean isValidAirport = validateAirport(handoverVO.getDestAirport(), mailMRDMessageVO.getCompanyCode());
		OfficeOfExchangeVO ooe = "POD".equals(handoverVO.getHandOverType())
				? validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(), handoverVO.getDstExgOffice())
				: validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(), handoverVO.getOrgExgOffice());
		boolean isValidDoe = ooe != null ? true : false;
		if (handoverVO != null && mailBag != null) {
			embargoDetailVos = checkEmbargoForMailMRD(handoverVO, mailBag, mailMRDMessageVO);
		}
		if (handoverVO.getFlightNumber() != null && handoverVO.getFlightNumber().trim().length() > 0
				&& !"0000".equals(handoverVO.getFlightNumber()) && handoverVO.getCarrierCode() != null
				&& handoverVO.getCarrierCode().trim().length() > 0 && handoverVO.getDestination() != null
				&& handoverVO.getDestination().trim().length() > 0 && handoverVO.getOrigin() != null
				&& handoverVO.getOrigin().trim().length() > 0) {
			FlightFilterVO opFlightVO = new FlightFilterVO();
			opFlightVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
			opFlightVO.setCarrierCode(handoverVO.getCarrierCode());
			opFlightVO.setFlightNumber(handoverVO.getFlightNumber());
			opFlightVO.setDirection("I");
			opFlightVO.setDestination(handoverVO.getDestination());
			opFlightVO.setOrigin(handoverVO.getOrigin());
			opFlightVO.setFlightDate(LocalDateMapper.toLocalDate(handoverVO.getFlightDate()));
			Collection<FlightValidationVO> fltValVOs = validateFlight(opFlightVO);
			isValidFlight = (fltValVOs != null && !fltValVOs.isEmpty()) ? true : false;
			if (fltValVOs != null && !fltValVOs.isEmpty()) {
				for (FlightValidationVO fltValVO : fltValVOs) {
					if (fltValVO.getCompanyCode().equals(mailMRDMessageVO.getCompanyCode())
							&& fltValVO.getCarrierCode().equals(handoverVO.getCarrierCode())
							&& fltValVO.getFlightNumber().equals(handoverVO.getFlightNumber())
							&& fltValVO.getLegOrigin().equals(handoverVO.getOrigin())
							&& (fltValVO.getFlightRoute().endsWith("-" + handoverVO.getDestination())
							|| fltValVO.getLegDestination().equals(handoverVO.getDestination()))) {
						MailUploadVO mailUploadVO = new MailUploadVO();
						mailUploadVO.setCompanyCode(fltValVO.getCompanyCode());
						mailUploadVO.setScannedPort(fltValVO.getLegDestination());
						mailUploadVO.setCarrierCode(fltValVO.getCarrierCode());
						mailUploadVO.setFlightNumber(fltValVO.getFlightNumber());
						mailUploadVO.setFlightDate(LocalDateMapper.toZonedDateTime(fltValVO.getFlightDate()));
						mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
						mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
						mailUploadVO.setMailTag(mailBag);
						mailUploadVO.setContainerPol(fltValVO.getLegOrigin());
						mailUploadVO.setContainerPOU(fltValVO.getLegDestination());
						Collection<MailUploadVO> mailUplVOs = new ArrayList<MailUploadVO>();
						Collection<OperationalFlightVO> opfltVO = null;
						mailUplVOs.add(mailUploadVO);
						try {
							validateFlightAndSegmentsForMLD(mailUplVOs);
							isValidFlightSegment = true;
							break;
						} catch (MailMLDBusniessException mldBusinessException) {
							log.info("Exception :", mldBusinessException);
							isValidFlightSegment = false;
						}
					}
				}
			}
			isValidFlightCarrier = validateCarrierCode(handoverVO.getCarrierCode(), mailMRDMessageVO.getCompanyCode());
		} else {
			isValidFlight = true;
			isValidFlightCarrier = true;
			isValidFlightSegment = true;
		}
		boolean isValidHandoverCarrier = true;
		if (handoverVO.getAttributeCarrier() != null) {
			isValidHandoverCarrier = validateCarrierCode(handoverVO.getAttributeCarrier(),
					mailMRDMessageVO.getCompanyCode());
		}
		if (handoverVO.getHandOverCarrierCode() != null) {
			isValidHandoverCarrier = validateCarrierCode(handoverVO.getHandOverCarrierCode(),
					mailMRDMessageVO.getCompanyCode());
		}
		if (isValidFlightCarrier && isValidAirport && isValidDoe && isValidFlight && isValidFlightSegment
				&& isValidHandoverCarrier && embargoDetailVos == null) {
			validateMailData(mailMRDMessageVO, mailBag);
		} else {
			ErrorVO errorVO = null;
			Object[] obj = new Object[2];
			obj[0] = mailBag;
			obj[1] = handoverVO.getHandOverID();
			if (!isValidAirport && !isErrorAdded) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.mrd.invaliddestination");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
			if (!isValidDoe && !isErrorAdded) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidexchangeoffice");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
			if ((!isValidFlightCarrier || !isValidHandoverCarrier) && !isErrorAdded) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidcarriercode");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
			if (!isValidFlight && !isErrorAdded) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidflight");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
			if (!isValidFlightSegment && !isErrorAdded) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.invalidflightsegment");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
			if (embargoDetailVos != null && embargoDetailVos.size() > 0) {
				isErrorAdded = true;
				errorVO = new ErrorVO("mailtracking.defaults.embargoexists");
				errorVO.setErrorData(obj);
				errorVOs.add(errorVO);
			}
		}
		mailMRDMessageVO.setHandoverErrors(errorVOs);
	}

	public Collection<EmbargoDetailsVO> checkEmbargoForMailMRD(HandoverVO handoverVO, String mailBag,
															   MailMRDVO mailMRDMessageVO) {
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = null;
		Collection<EmbargoDetailsVO> embargoDetails = null;
		Set flightNumberOrg = new HashSet();
		Set flightNumberDst = new HashSet();
		Set flightNumberVia = new HashSet();
		Set carrierOrg = new HashSet();
		Set carrierDst = new HashSet();
		Set carrierVia = new HashSet();
		if (handoverVO != null && mailBag != null) {
			ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
			shipmentDetailsVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
			if ("POD".equals(handoverVO.getHandOverType())) {
				shipmentDetailsVO.setOoe(mailBag.substring(0, 6));
				shipmentDetailsVO.setDoe(handoverVO.getDstExgOffice());
			}
			if ("POC".equals(handoverVO.getHandOverType())) {
				shipmentDetailsVO.setOoe(handoverVO.getOrgExgOffice());
				shipmentDetailsVO.setDoe(mailBag.substring(6, 12));
			}
			String orgStation = null;
			String destStation = null;
			try {
				if ("POD".equals(handoverVO.getHandOverType())) {
					orgStation = findNearestAirportOfCity(mailMRDMessageVO.getCompanyCode(), mailBag.substring(0, 6));
					destStation = findNearestAirportOfCity(mailMRDMessageVO.getCompanyCode(),
							handoverVO.getDstExgOffice());
				}
				if ("POC".equals(handoverVO.getHandOverType())) {
					orgStation = findNearestAirportOfCity(mailMRDMessageVO.getCompanyCode(),
							handoverVO.getOrgExgOffice());
					destStation = findNearestAirportOfCity(mailMRDMessageVO.getCompanyCode(), mailBag.substring(6, 12));
				}
			} finally {
			}
			shipmentDetailsVO.setOrgStation(orgStation);
			shipmentDetailsVO.setDstStation(destStation);
			if ("POD".equals(handoverVO.getHandOverType())) {
				shipmentDetailsVO.setOrgCountry(mailBag.substring(0, 6).substring(0, 2));
				shipmentDetailsVO.setDstCountry(handoverVO.getDstExgOffice().substring(0, 2));
			}
			if ("POC".equals(handoverVO.getHandOverType())) {
				shipmentDetailsVO.setOrgCountry(handoverVO.getOrgExgOffice().substring(0, 2));
				shipmentDetailsVO.setDstCountry(mailBag.substring(6, 12).substring(0, 2));
			}
			shipmentDetailsVO.setShipmentID(mailBag);
			shipmentDetailsVO.setShipmentDate(LocalDateMapper.toLocalDate(handoverVO.getHandOverdate_time()));
			String orgPaCod = null;
			String dstPaCod = null;
			try {
				if ("POD".equals(handoverVO.getHandOverType())) {
					orgPaCod = mailController.findPAForOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
							mailBag.substring(0, 6));
				}
				if ("POC".equals(handoverVO.getHandOverType())) {
					orgPaCod = mailController.findPAForOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
							handoverVO.getOrgExgOffice());
				}
			} finally {
			}
			try {
				if ("POD".equals(handoverVO.getHandOverType())) {
					dstPaCod = mailController.findPAForOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
							handoverVO.getDstExgOffice());
				}
				if ("POC".equals(handoverVO.getHandOverType())) {
					dstPaCod = mailController.findPAForOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
							mailBag.substring(6, 12));
				}
			} finally {
			}
			shipmentDetailsVO.setOrgPaCod(orgPaCod);
			shipmentDetailsVO.setDstPaCod(dstPaCod);
			detailsMap = populateDetailsMapforMail(mailBag, mailMRDMessageVO.getCompanyCode());
			StringBuilder flightNumber = new StringBuilder();
			flightNumber.append(handoverVO.getCarrierCode());
			if (handoverVO.getFlightNumber() != null) {
				flightNumber.append("~").append(handoverVO.getFlightNumber());
			}
			flightNumberOrg.add(flightNumber.toString());
			flightNumberVia.add(flightNumber.toString());
			flightNumberDst.add(flightNumber.toString());
			carrierOrg.add(handoverVO.getCarrierCode());
			carrierDst.add(handoverVO.getCarrierCode());
			carrierVia.add(handoverVO.getCarrierCode());
			detailsMap.put("CARORG", carrierOrg);
			detailsMap.put("CARVIA", carrierVia);
			detailsMap.put("CARDST", carrierDst);
			detailsMap.put("FLTNUMORG", flightNumberOrg);
			detailsMap.put("FLTNUMVIA", flightNumberVia);
			detailsMap.put("FLTNUMDST", flightNumberDst);
			if (detailsMap != null && detailsMap.size() > 0) {
				shipmentDetailsVO.setMap(detailsMap);
			}
			shipmentDetailsVO.setUserLocaleNeeded(true);
			shipmentDetailsVO.setApplicableTransaction("MALARR");
			shipmentDetailsVOs.add(shipmentDetailsVO);
			try {
				embargoDetails = checkEmbargoForMail(shipmentDetailsVOs);
			} finally {
			}
		}
		return embargoDetails;
	}

	/**
	 * @author A-4810
	 * @param mailBagId
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<String>> populateDetailsMapforMail(String mailBagId, String companyCode) {
		Map<String, Collection<String>> detailsMap = null;
		Collection<String> mailclass = new ArrayList<String>();
		Collection<String> mailsubclass = new ArrayList<String>();
		Collection<String> mailcat = new ArrayList<String>();
		Collection<MailSubClassVO> mailSubClassVOs = null;
		Collection<String> mailsubclassGrp = new ArrayList<String>();
		String subClassGrp = null;
		if (mailBagId != null && mailBagId.trim().length() > 0) {
			if (mailBagId.length() > 19) {
				detailsMap = new HashMap<String, Collection<String>>();
				mailcat.add(mailBagId.substring(12, 13));
				mailsubclass.add(mailBagId.substring(13, 15));
				mailclass.add(mailBagId.substring(13, 15).substring(0, 1));
				detailsMap.put("MALCLS", mailclass);
				detailsMap.put("MALSUBCLS", mailsubclass);
				detailsMap.put("MALCAT", mailcat);
				try {
					mailSubClassVOs = mailController.findMailSubClassCodes(companyCode,
							mailBagId.substring(13, 15));
				} finally {
				}
				if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
					subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
					if (subClassGrp != null) {
						mailsubclassGrp.add(subClassGrp);
						detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
					}
				}
			}
		}
		return detailsMap;
	}

	/**
	 * @return
	 */
	private void validateMailData(MailMRDVO mailMRDMessageVO, String mailBag) {
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		HashMap<String, String> data = new HashMap<String, String>();
		boolean isValidMailBag = false;
		if (mailBag.trim().length() == 29) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
			mailbagVO.setDoe(mailBag.substring(6, 12));
			mailbagVO.setDespatchSerialNumber(mailBag.substring(16, 20));
			mailbagVO.setMailbagId(mailBag);
			mailbagVO.setMailSubclass(mailBag.substring(13, 15));
			mailbagVO.setMailCategoryCode(mailBag.substring(12, 13));
			mailbagVO.setOoe(mailBag.substring(0, 6));
			mailbagVO.setYear(Integer.parseInt(mailBag.substring(15, 16)));
			mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
			mailBagVOs.add(mailbagVO);
			try {
				isValidMailBag = validateMailBags(mailBagVOs);
				if (isValidMailBag) {
					data.put("valid_mailbag", mailBag);
				} else {
					data.put(MailConstantsVO.INVALID_MAILBAG, mailBag);
				}
			} catch (InvalidMailTagFormatException exception) {
				data.put(MailConstantsVO.INVALID_MAILBAG, mailBag);
			}
		} else {
			data.put(MailConstantsVO.INVALID_MAILBAG, mailBag);
		}
		mailMRDMessageVO.setMailBags(data);
	}

	/**
	 * @param mailMRDMessageVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @throws ForceAcceptanceException
	 */
	public Collection<ErrorVO> handleMRDHO22Message(MailMRDVO mailMRDMessageVO)
			throws MailOperationsBusinessException, ForceAcceptanceException {
		Collection<ErrorVO> allErrorVOs = new ArrayList<ErrorVO>();
		allErrorVOs=handleMRDMessage(mailMRDMessageVO);
		return allErrorVOs;
	}

	public void saveAllValidMailBags(Collection<ScannedMailDetailsVO> validScannedMailVOs)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			ForceAcceptanceException, RemoteException {
		for (ScannedMailDetailsVO scannedMailDetailsVO : validScannedMailVOs) {
			saveAndProcessMailBags(scannedMailDetailsVO);
		}
	}

	public ArrayList<MailUploadVO> createMailScanVOSForErrorStamping(Collection<MailWebserviceVO> webServicesVos,
																	 String scanningPort, StringBuilder errorString, String errorFromMapping) {
		Collection<MailUploadVO> mailVos = null;
		ArrayList<MailUploadVO> maildeleteVos = new ArrayList<MailUploadVO>();
		ArrayList<MailUploadVO> mailscanVos = new ArrayList<MailUploadVO>();
		try {
			mailVos = generateMailUploadVO(webServicesVos);
		} catch (MailHHTBusniessException e) {
			mailVos = generateMailUploadVOForError(webServicesVos);
		}
		if (mailVos != null && mailVos.size() > 0) {
			for (MailUploadVO mailVo : mailVos) {
				if (MailConstantsVO.MAIL_STATUS_DELETED.equals(mailVo.getScanType())) {
					maildeleteVos.add(mailVo);
				} else {
					mailscanVos.add(mailVo);
				}
			}
		}
		return mailscanVos;
	}

	private Collection<MailUploadVO> generateMailUploadVOForError(Collection<MailWebserviceVO> webServicesVos) {
		Collection<MailUploadVO> mailUploadVos = new ArrayList<MailUploadVO>();
		Collection<MailUploadVO> uploadVos = null;
		if (webServicesVos != null && webServicesVos.size() > 0) {
			for (MailWebserviceVO serviceVo : webServicesVos) {
				uploadVos = populateMailUploadVOForError(serviceVo);
				mailUploadVos.addAll(uploadVos);
			}
		}
		return mailUploadVos;
	}

	private Collection<MailUploadVO> populateMailUploadVOForError(MailWebserviceVO mailWebserviceVO) {
		if (mailWebserviceVO.getCarrierCode() == null || mailWebserviceVO.getCarrierCode().trim().length() == 0) {
			mailWebserviceVO.setCarrierCode(mailWebserviceVO.getCompanyCode());
		}
		Collection<MailUploadVO> finalVos = new ArrayList<MailUploadVO>();
		MailUploadVO mailUploadVO = populateMailVosForError(mailWebserviceVO);
		finalVos.add(mailUploadVO);
		return finalVos;
	}

	private MailUploadVO populateMailVosForError(MailWebserviceVO mailWebserviceVO) {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setScanType(mailWebserviceVO.getScanType());
		mailUploadVO.setFromGHAService(true);
		mailUploadVO.setMailSource("WS");
		mailUploadVO.setCompanyCode(mailWebserviceVO.getCompanyCode());
		mailUploadVO.setCarrierCode(mailWebserviceVO.getCarrierCode());
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailWebserviceVO.getScanType())) {
			if (logonAttributes.getOwnAirlineCode() != null
					&& logonAttributes.getOwnAirlineCode().trim().length() > 0) {
				if (!logonAttributes.getOwnAirlineCode().equals(mailWebserviceVO.getCarrierCode())) {
					mailUploadVO.setFromCarrierCode(mailWebserviceVO.getCarrierCode());
				}
			}
		} else {
			mailUploadVO.setFromCarrierCode(mailWebserviceVO.getCarrierCode());
		}
		mailUploadVO.setFlightNumber(mailWebserviceVO.getFlightNumber());
		mailUploadVO.setFlightDate(mailWebserviceVO.getFlightDate());
		mailUploadVO.setContainerPOU(mailWebserviceVO.getContainerPou());
		mailUploadVO.setContainerNumber(mailWebserviceVO.getContainerNumber());
		mailUploadVO.setContainerType(mailWebserviceVO.getContainerType());
		mailUploadVO.setDestination(mailWebserviceVO.getContainerDestination());
		mailUploadVO.setContainerPol(mailWebserviceVO.getContainerPol());
		mailUploadVO.setRemarks(mailWebserviceVO.getRemarks());
		mailUploadVO.setMailTag(mailWebserviceVO.getMailBagId());
		mailUploadVO.setDateTime(
				mailWebserviceVO.getScanDateTime().format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
		mailUploadVO.setDamageCode(mailWebserviceVO.getDamageCode());
		mailUploadVO.setDamageRemarks(mailWebserviceVO.getDamageRemarks());
		mailUploadVO.setOffloadReason(mailWebserviceVO.getOffloadReason());
		mailUploadVO.setReturnCode(mailWebserviceVO.getReturnCode());
		mailUploadVO.setToContainer(mailWebserviceVO.getToContainer());
		mailUploadVO.setToCarrierCode(mailWebserviceVO.getToCarrierCod());
		mailUploadVO.setToFlightNumber(mailWebserviceVO.getToFlightNumber());
		mailUploadVO.setToFlightDate(mailWebserviceVO.getToFlightDate());
		mailUploadVO.setToPOU(mailWebserviceVO.getToContainerPou());
		mailUploadVO.setToDestination(mailWebserviceVO.getToContainerDestination());
		mailUploadVO.setConsignmentDocumentNumber(mailWebserviceVO.getConsignmentDocNumber());
		mailUploadVO.setScannedPort(mailWebserviceVO.getScanningPort());
		mailUploadVO.setScanUser(mailWebserviceVO.getUserName());
		mailUploadVO.setIntact(mailWebserviceVO.isPAbuilt());
		if (mailWebserviceVO.isPAbuilt()) {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_YES);
		} else {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_NO);
		}
		mailUploadVO.setSerialNumber(mailWebserviceVO.getSerialNumber());
		mailUploadVO.setDeliverd(mailWebserviceVO.isDelivered());
		if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() > 0) {
			if (mailWebserviceVO.getMailBagId().trim().length() == 29) {
				splitMailTagId(mailUploadVO);
			}
		}
		return mailUploadVO;
	}

	/**
	 * This method is used to extract data from excel to a temp table SHRGENEXTTAB
	 * @param fileUploadFilterVO
	 * @return
	 * @throws SystemException
	 */
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO) {
		log.debug("MailUploadController" + " : " + "processMailOperationFromFile" + " Entering");
		String processStatus = validateExcelForUpload(fileUploadFilterVO);
		if (!"OK".equals(processStatus)) {
			saveFileUploadError(fileUploadFilterVO);
			return processStatus;
		} else {
			return MailScanDetail.processMailOperationFromFile(fileUploadFilterVO);
		}
	}

	/**
	 * Thsi method wil fetch the date from temp table and perform mail transactions
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailUploadVO> fetchDataForOfflineUpload(String companyCode, String fileType) {
		log.debug("MailUploadController" + " : " + "fetchDataForOfflineUpload" + " Entering");
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		Collection<MailUploadVO> mailUploadVOs = MailScanDetail.fetchDataForOfflineUpload(companyCode, fileType);
		if (mailUploadVOs != null && !mailUploadVOs.isEmpty()) {
			Collection<MailScanDetailVO> mailScanDetailVOs = new ArrayList<MailScanDetailVO>();
			for (MailUploadVO mailUploadVO : mailUploadVOs) {
				Collection<MailUploadVO> mailUploadVOsForSave = new ArrayList<MailUploadVO>();
				String scannedString = constructScannedString(mailUploadVO);
				MailScanDetailVO mailScanDetailVO = constructMailScanDetailVO(scannedString, mailUploadVO);
				if (mailScanDetailVO != null) {
					mailScanDetailVOs.add(mailScanDetailVO);
				}
				mailUploadVOsForSave.add(mailUploadVO);
				//TODO: Neo to correct
//				mailtrackingDefaultsProxy.saveMailDetailsFromJob(mailUploadVOsForSave,
//						logonAttributes.getAirportCode());
			}
			if (mailScanDetailVOs != null && mailScanDetailVOs.size() > 0) {
				saveMailScannedDetails(mailScanDetailVOs);
			}
		}
		return mailUploadVOs;
	}

	/**
	 * This will construct constructMailScanDetailVO
	 * @param scannedString
	 * @param mailUploadVO
	 * @return
	 */
	private MailScanDetailVO constructMailScanDetailVO(String scannedString, MailUploadVO mailUploadVO) {
		log.debug("MailUploadController" + " : " + "constructMailScanDetailVO" + " Entering");
		MailScanDetailVO mailScanDetailVO = new MailScanDetailVO();
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		mailScanDetailVO.setCompanyCode(mailUploadVO.getCompanyCode());
		mailScanDetailVO.setDeviceId("EXCELUPL");
		mailScanDetailVO.setDeviceIpAddress("EXCELUPL");
		mailScanDetailVO.setFileSequence(1);
		mailScanDetailVO.setLastUpdateTime(localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true).toLocalDateTime());
		mailScanDetailVO.setLastUpdateUser(logonAttributes.getUserId());
		mailScanDetailVO.setMailBagId(mailUploadVO.getMailTag());
		mailScanDetailVO.setAirport(logonAttributes.getAirportCode());
		mailScanDetailVO.setScanData(scannedString);
		mailScanDetailVO.setScannedUser(logonAttributes.getUserId());
		//TODO: Neo to correct
//		String nodeName = NodeUtil.getInstance().getNodeName();
//		if (nodeName != null && nodeName.trim().length() > 0) {
//			mailScanDetailVO.setNodeName(nodeName);
//		}
		ZonedDateTime scanDate = localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true);
		mailScanDetailVO.setScanDate(scanDate);
		mailScanDetailVO.setMailSequenceNumber(Integer.parseInt("00001"));
		mailScanDetailVO.setUploadStatus(MailConstantsVO.FLAG_YES);
		if (mailUploadVO.getMailCompanyCode() != null && mailUploadVO.getMailCompanyCode().trim().length() > 0) {
			mailUploadVO.setMailCompanyCode(mailUploadVO.getMailCompanyCode().toUpperCase());
		}
		return mailScanDetailVO;
	}

	/**
	 * This will constrcu ScannedString for MALSCNDTL table
	 * @param mailUploadVO
	 * @return
	 */
	private String constructScannedString(MailUploadVO mailUploadVO) {
		log.debug("MailUploadController" + " : " + "constructScannedString" + " Entering");
		String flightDate = "";
		if (mailUploadVO.getFlightDate() != null) {
			flightDate = mailUploadVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
		}
		return new StringBuilder(mailUploadVO.getScanType()).append(";").append(mailUploadVO.getMailCompanyCode())
				.append(";").append(mailUploadVO.getCarrierCode()).append(";").append(mailUploadVO.getFlightNumber())
				.append(";").append(flightDate).append(";").append(mailUploadVO.getContainerPOU()).append(";")
				.append(mailUploadVO.getContainerNumber()).append(";").append(mailUploadVO.getContainerType())
				.append(";").append(mailUploadVO.getCarrierCode()).append(";").append(mailUploadVO.getDestination())
				.append(";").append(";").append(";").append(";").append(mailUploadVO.getMailTag()).append(";")
				.append(mailUploadVO.getDateTime()).append(";").append(";").append(";").append(";").append(";")
				.append(";").append(";").append(";").append(";").append(";").append(";").append(";").append(";")
				.append(";").append(";").append(";").append(";").append(";").append(MailConstantsVO.FLAG_NO).append(";")
				.append(";").append(";").append(";").append(";").append(MailConstantsVO.FLAG_NO).append(";").append(";")
				.append(";").append(MailConstantsVO.FLAG_NO).append(";").append("EXCELUSER").append(";")
				.append(mailUploadVO.getDateTime()).append(";").append(";").append("00001").append(";").append("*#")
				.toString();
	}

	/**
	 * This method will remove the data from temp table SHRGENEXTTAB
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) {
		log.debug("MailUploadController" + " : " + "removeDataFromTempTable" + " Entering");
		MailScanDetail.removeDataFromTempTable(fileUploadFilterVO);
		log.debug("MailUploadController" + " : " + "removeDataFromTempTable" + " Exiting");
	}

	/**
	 * @author A-5526
	 * @param fileUploadFilterVO
	 */
	private String validateExcelForUpload(FileUploadFilterVO fileUploadFilterVO) {
		log.debug("MailUploadController" + " : " + "validateExcelForUpload" + " Entering");
		String processStatus = "OK";
		String fileName = fileUploadFilterVO.getFileName();
		if (fileName != null && fileName.trim().length() > 0) {
			if (fileName.contains(".csv") || fileName.contains(".CSV")) {
				return "Error";
			}
		}
		log.debug("MailUploadController" + " : " + "validateExcelForUpload" + " Exiting");
		return processStatus;
	}
	/**
	 * @author A-5526This method is used to persist the errors while upload the excel file from SHR118
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 */
	private void saveFileUploadError(FileUploadFilterVO fileUploadFilterVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		FileUploadErrorLogVO errorLogVO = new FileUploadErrorLogVO();
		Collection<FileUploadErrorLogVO> errorLogVOs = new ArrayList<FileUploadErrorLogVO>();
		errorLogVO.setCompanyCode(fileUploadFilterVO.getCompanyCode());
		errorLogVO.setProcessIdentifier(fileUploadFilterVO.getProcessIdentifier());
		errorLogVO.setFileName(fileUploadFilterVO.getFileName());
		errorLogVO.setFileType(fileUploadFilterVO.getFileType());
		errorLogVO.setLineNumber(0);
		errorLogVO.setLineContent(fileUploadFilterVO.getFileName());
		errorLogVO.setErrorCode("Invalid file format");
		errorLogVO.setUpdatedUser(logonAttributes.getUserId());
		ZonedDateTime date = localDateUtil.getLocalDate(null, false);
		errorLogVO.setUpdatedTime(LocalDateMapper.toLocalDate(date));
		errorLogVOs.add(errorLogVO);
		if (!errorLogVOs.isEmpty()) {
			sharedDefaultsProxy.saveFileUploadExceptions(errorLogVOs);
		}
	}
	/**
	 * @author a-7540
	 * @return
	 * @throws SystemException
	 */
	public String validateFromFile(FileUploadFilterVO fileUploadFilterVO) {
		String processStatus = "";
		Collection<MailUploadVO> mailUploadVOs = MailScanDetail
				.fetchDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType());
		HashMap<String, String> validatePA = new HashMap<>();
		for (MailUploadVO mailUploadVO : mailUploadVOs) {
			if (mailUploadVO != null) {
				if (!validatePA.containsKey(mailUploadVO.getContainerNumber())) {
					validatePA.put(mailUploadVO.getContainerNumber(), mailUploadVO.getPaCode());
				} else {
					if (!validatePA.get(mailUploadVO.getContainerNumber()).equalsIgnoreCase(mailUploadVO.getPaCode())) {
						processStatus = STATUS_NOTOK;
						saveFileUploadError(fileUploadFilterVO);
						break;
					}
				}
				if ("MALINBOUND".equals(fileUploadFilterVO.getFileType())) {
					if (mailUploadVO.getMailTag() == null || mailUploadVO.getMailTag().length() > 29
							|| mailUploadVO.getContainerNumber() == null || mailUploadVO.getContainerPol() == null
							|| mailUploadVO.getContainerType() == null || mailUploadVO.getContainerPOU() == null
							|| mailUploadVO.getFlightNumber() == null || mailUploadVO.getFlightDate() == null
							|| mailUploadVO.getDeliverFlag() == null || mailUploadVO.getPaCode() == null) {
						processStatus = STATUS_NOTOK;
						saveFileUploadError(fileUploadFilterVO);
						break;
					} else {
						processStatus = STATUS_OK;
					}
				} else {
					if (mailUploadVO.getMailTag() == null || mailUploadVO.getContainerNumber() == null
							|| mailUploadVO.getContainerPol() == null || mailUploadVO.getContainerType() == null
							|| mailUploadVO.getContainerPOU() == null || mailUploadVO.getFlightNumber() == null
							|| mailUploadVO.getFlightDate() == null || mailUploadVO.getPaCode() == null) {
						processStatus = STATUS_NOTOK;
						saveFileUploadError(fileUploadFilterVO);
						break;
					} else {
						processStatus = STATUS_OK;
					}
				}
			}
		}
		return processStatus;
	}
	/**
	 * Method		:	MailUploadController.validateFlightAndContainer Added by 	:	A-8164 on 20-Feb-2019 Used for 	:	Validating Flight and Container Parameters	:	@param mailUploadVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailHHTBusniessException  Return type	: 	ScannedMailDetailsVO
	 * @throws ForceAcceptanceException
	 */
	public ScannedMailDetailsVO validateFlightAndContainer(MailUploadVO mailUploadVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		ULDValidationVO uldValidationVO = null;
		boolean isValidULD = true;
		boolean isFlightClosedforMail = false;
		boolean atdCaptured = false;
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		LoginProfile logonAttributes = null;
		Collection<FlightValidationVO> flightDetailsVOs = null;
		String allowUldAsBarrow = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		allowUldAsBarrow = findSystemParameterValue(ALLOWULDASBARROW);
		if (mailUploadVO.getContainerNumber() != null || (mailUploadVO.getFlightNumber() != null)) {
			if (mailUploadVO.getContainerNumber() != null) {
				try {
					if (MailConstantsVO.OPERATION_INBOUND.equals(mailUploadVO.getOperationType())
							&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())
							&& (mailUploadVO.getMailTag() == null || "null".equals(mailUploadVO.getMailTag())
							|| mailUploadVO.getMailTag().isEmpty())) {
						scannedMailDetailsVO = new ScannedMailDetailsVO();
						scannedMailDetailsVO.setCompanyCode(mailUploadVO.getCompanyCode());
						scannedMailDetailsVO.setAirportCode(mailUploadVO.getScannedPort());
						scannedMailDetailsVO.setContainerNumber(mailUploadVO.getContainerNumber());
						scannedMailDetailsVO.setFlightNumber(mailUploadVO.getFlightNumber());
						scannedMailDetailsVO.setFlightDate(mailUploadVO.getFlightDate());
						scannedMailDetailsVO.setCarrierCode(mailUploadVO.getCarrierCode());
						containerAssignmentVO = findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
						if (containerAssignmentVO != null
								&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
							containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
						}
					} else {
						containerAssignmentVO = mailController
								.findLatestContainerAssignment(mailUploadVO.getContainerNumber());
						if (MailConstantsVO.OPERATION_INBOUND.equals(mailUploadVO.getOperationType())
								&& (mailUploadVO.getContainerNumber() != null
								&& !mailUploadVO.getContainerNumber().isEmpty())
								&& (mailUploadVO.getMailTag() == null || "null".equals(mailUploadVO.getMailTag())
								|| mailUploadVO.getMailTag().isEmpty())
								&& (mailUploadVO.getFlightNumber() == null
								|| "null".equals(mailUploadVO.getFlightNumber())
								|| mailUploadVO.getFlightNumber().isEmpty())
								&& containerAssignmentVO != null
								&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())
								&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
							containerAssignmentVO.setTransitFlag(MailConstantsVO.FLAG_YES);
						}
					}
				} finally {
				}
			}
			if (containerAssignmentVO != null
					&& MailConstantsVO.OPERATION_OUTBOUND.equals(mailUploadVO.getOperationType())
					&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
				containerAssignmentVO = null;
			}
			if (containerAssignmentVO == null) {
				if (mailUploadVO.getContainerNumber() != null && !"".equals(mailUploadVO.getContainerNumber())
						&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())) {
					try {
						uldValidationVO = sharedULDProxy.validateULD(mailUploadVO.getCompanyCode(),
								mailUploadVO.getContainerNumber());
					} catch (SharedProxyException e) {
						isValidULD = false;
					}
					if (uldValidationVO != null) {
						isValidULD = true;
					} else {
						isValidULD = false;
					}
					if (!isValidULD) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_ULD_FORMAT,
								MailHHTBusniessException.INVALID_ULD_FORMAT, scannedMailDetailsVO);
					}
				}
			}
			scannedMailDetailsVO = populateScannedMailDetails(containerAssignmentVO, mailUploadVO);
			boolean retriggerValidation = false;
			if (containerAssignmentVO != null && "O".equalsIgnoreCase(scannedMailDetailsVO.getOperationType())
					&& scannedMailDetailsVO.getAirportCode() != null
					&& !scannedMailDetailsVO.getAirportCode().equals(containerAssignmentVO.getAirportCode())) {
				retriggerValidation = true;
				if ((containerAssignmentVO.getArrivalFlag() != null
						&& MailConstantsVO.FLAG_NO.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
						&& !containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
						|| (("-1").equals(scannedMailDetailsVO.getFlightNumber())
						&& (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_NO
						.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag()))
						&& !containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.BULK_TYPE)
						&& !(containerAssignmentVO.getDestination()
						.equals(scannedMailDetailsVO.getDestination())))) {
					retriggerValidation = false;
				}
			}
			if ((!MailConstantsVO.FLAG_YES.equals(mailUploadVO.getOverrideULDVal())
					&& (containerAssignmentVO == null || retriggerValidation))
					&& (mailUploadVO.getOverrideValidation() == null || (mailUploadVO.getOverrideValidation() != null
					&& !("Y").equals(mailUploadVO.getOverrideValidation())))) {
				if (mailUploadVO.getContainerType() != null
						&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType()) && isValidULD) {
					boolean isValidUld = new MailOperationsValidator().validateULDonTabOut(scannedMailDetailsVO,
							logonAttributes);
					if (!isValidUld) {
						String sysparValueUldNotInArlStk = null;
						String sysparValueUldNotInStock = null;
						ArrayList<String> systemParameters = new ArrayList<String>();
						systemParameters.add(ULD_SYSPAR_NOT_IN_ARL_STOCK);
						systemParameters.add(ULD_SYSPAR_NOTINSTOCK);
						Map<String, String> systemParameterMap = null;
						try {
							systemParameterMap = neoMastersServiceUtils
									.findSystemParameterByCodes(systemParameters);
						} catch (BusinessException e) {
							throw new RuntimeException(e);
						}
						log.debug("" + " systemParameterMap " + " " + systemParameterMap);
						if (systemParameterMap != null) {
							sysparValueUldNotInArlStk = systemParameterMap.get(ULD_SYSPAR_NOT_IN_ARL_STOCK);
							sysparValueUldNotInStock = systemParameterMap.get(ULD_SYSPAR_NOTINSTOCK);
						}
						if ("W".equals(sysparValueUldNotInArlStk) || "W".equals(sysparValueUldNotInStock)) {
							Collection<MailbagVO> mailErrors = new ArrayList<MailbagVO>();
							if (scannedMailDetailsVO.getErrorMailDetails() == null
									|| scannedMailDetailsVO.getErrorMailDetails().size() == 0) {
								scannedMailDetailsVO.setErrorMailDetails(mailErrors);
							}
							new MailOperationsValidator().constructAndroidException(
									MailMLDBusniessException.ULD_VALID_FAILED, MailHHTBusniessException.ULD_NOT_EXIST,
									scannedMailDetailsVO);
						} else {
							constructAndRaiseException(MailMLDBusniessException.ULD_VALID_FAILED,
									MailHHTBusniessException.ULD_NOT_EXIST, scannedMailDetailsVO);
						}
					}
				}
			}
			if (mailUploadVO.getFlightNumber() != null || mailUploadVO.getContainerNumber() != null
					|| (containerAssignmentVO != null && containerAssignmentVO.getFlightNumber() != null)) {
				if (scannedMailDetailsVO.getFlightDate() != null && scannedMailDetailsVO.getFlightNumber() != null) {
					flightDetailsVOs = validateFlight(scannedMailDetailsVO, logonAttributes);
					if (flightDetailsVOs == null || (flightDetailsVOs != null && flightDetailsVOs.size() == 0)) {
						constructAndRaiseException(MailHHTBusniessException.FLIGHT_NOT_EXIST,
								MailHHTBusniessException.FLIGHT_NOT_EXIST, scannedMailDetailsVO);
					}
				}
				OperationalFlightVO operationalFlightVO = createOperationalFlightVO(flightDetailsVOs);
				if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType()))
					isFlightClosedforMail = isFlightClosedForOperations(operationalFlightVO);
				else {
					isFlightClosedforMail = isFlightClosedForInboundOperations(operationalFlightVO);
					if (flightDetailsVOs != null
							&& ((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0).getSta() != null)
						scannedMailDetailsVO
								.setFlightDate(((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0).getSta().toZonedDateTime());
				}
				if (!MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())
						&& isFlightClosedforMail
						&& !MailConstantsVO.ONLOAD_MESSAGE.equals(mailUploadVO.getMailSource())) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				} else if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())
						&& MailConstantsVO.FLAG_NO.equals(mailUploadVO.getWarningFlag())
						&& !MailConstantsVO.ONLOAD_MESSAGE.equals(mailUploadVO.getMailSource())
						&& containerAssignmentVO != null) {
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCompanyCode(logonAttributes.getCompanyCode());
					containerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
					containerVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
					containerVO.setAssignmentFlag("F");
					try {
						mailController.validateContainerReusability(containerVO);
					} catch (ContainerAssignmentException e) {
						log.info("" + "Finder exception found" + " " + e);
						if (e.getMessage() != null && e.getMessage().equals(
								ContainerAssignmentException.ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING)) {
							StringBuilder stringBuilder = new StringBuilder();
							if (e.getErrors().iterator().hasNext()) {
								Object[] errordata = e.getErrors().iterator().next().getErrorData();
								stringBuilder.append(errordata[0]).append(" at ").append(errordata[1]);
							}
							scannedMailDetailsVO.setErrorMailDetails(new ArrayList<MailbagVO>());
							new MailOperationsValidator().constructAndroidException(
									ContainerAssignmentException.ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING,
									stringBuilder.toString(), scannedMailDetailsVO);
						}
					}
					atdCaptured = mailController.checkForDepartedFlightAtd(containerAssignmentVO);
					if (isFlightClosedforMail && !atdCaptured) {
						scannedMailDetailsVO.setErrorMailDetails(new ArrayList<MailbagVO>());
						new MailOperationsValidator().constructAndroidException(
								MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_WARNING, scannedMailDetailsVO);
					}
				} else {
					if (isFlightClosedforMail) {
						scannedMailDetailsVO.setErrorMailDetails(new ArrayList<MailbagVO>());
						new MailOperationsValidator().constructAndroidException(
								MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_WARNING, scannedMailDetailsVO);
					} else {
					}
				}
			}
			if ((containerAssignmentVO == null || scannedMailDetailsVO.getPol() == null) && flightDetailsVOs != null
					&& MailConstantsVO.OPERATION_INBOUND.equals(mailUploadVO.getOperationType())) {
				scannedMailDetailsVO.setFlightSequenceNumber(
						((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0).getFlightSequenceNumber());
				scannedMailDetailsVO.setLegSerialNumber(
						((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0).getLegSerialNumber());
				scannedMailDetailsVO.setPol(mailUploadVO.getPols());
			}
			if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())
					&& isFlightClosedforMail && MailConstantsVO.FLAG_NO.equals(mailUploadVO.getWarningFlag())
					&& !MailConstantsVO.ONLOAD_MESSAGE.equals(mailUploadVO.getMailSource())
					&& ("SCAN:HHT046").equals(mailUploadVO.getMailSource())) {
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			}
			if (flightDetailsVOs != null && flightDetailsVOs.size() > 0) {
				FlightValidationVO flightValidationVO = new FlightValidationVO();
				flightValidationVO = ((ArrayList<FlightValidationVO>) flightDetailsVOs).get(0);
				String flightRoute = flightValidationVO.getFlightRoute();
				Collection<String> pous = new ArrayList<String>();
				Collection<String> airportCodes = new ArrayList<String>();
				Collection<String> airportCodesBefore = new ArrayList<String>();
				Collection<String> airportCodesAfter = new ArrayList<String>();
				String[] airports = new String[10];
				int i = 0;
				boolean roundRobin = false;
				for (String airportCode : flightRoute.split("-")) {
					airportCodes.add(airportCode);
					airports[i++] = airportCode;
				}
				if (airports != null && airports[0] != null && airports[i - 1] != null
						&& airports[0].equals(airports[i - 1])) {
					roundRobin = true;
					int index = Arrays.asList(airports).indexOf(scannedMailDetailsVO.getAirportCode());
					airportCodesBefore = Arrays.asList(Arrays.copyOf(airports, index));
					airportCodesAfter = Arrays.asList(Arrays.copyOfRange(airports, index + 1, i));
					if (index == 0 && airportCodesBefore.size() == 0) {
						airportCodesBefore = Arrays.asList(Arrays.copyOfRange(airports, index + 1, i - 1));
					}
					if (index == 0 && airportCodesAfter.size() > 0) {
						airportCodesAfter = Arrays.asList(Arrays.copyOfRange(airports, index + 1, i - 1));
					}
				}
				if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())) {
					Collections.reverse((List<String>) airportCodes);
				}
				for (String airport : airportCodes) {
					if (!airport.equals(scannedMailDetailsVO.getAirportCode())) {
						pous.add(airport);
					} else {
						break;
					}
				}
				if (MailConstantsVO.OPERATION_INBOUND.equals(scannedMailDetailsVO.getOperationType())) {
					Collections.reverse((List<String>) pous);
					if (pous.size() == 0) {
						if (null != scannedMailDetailsVO.getAirportCode()) {
							pous.add(scannedMailDetailsVO.getAirportCode());
						}
					}
					scannedMailDetailsVO.setPols(pous);
				} else {
					if (pous.size() == 0) {
						if (null != scannedMailDetailsVO.getAirportCode()) {
							pous.add(scannedMailDetailsVO.getAirportCode());
						}
					}
					scannedMailDetailsVO.setPous(pous);
				}
				if (roundRobin) {
					pous.clear();
					if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())) {
						pous.addAll(airportCodesAfter);
					} else if (MailConstantsVO.OPERATION_INBOUND.equals(scannedMailDetailsVO.getOperationType())) {
						pous.addAll(airportCodesBefore);
					}
				}
				if ((MailConstantsVO.OPERATION_OUTBOUND.equals(mailUploadVO.getOperationType())
						&& mailUploadVO.getContainerNumber() != null
						&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType()))
						|| (MailConstantsVO.OPERATION_INBOUND.equals(mailUploadVO.getOperationType())
						&& mailUploadVO.getContainerNumber() != null
						&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())
						&& containerAssignmentVO == null
						&& (findSystemParameterValue(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND) != null
						&& MailConstantsVO.FLAG_YES.equals(
						findSystemParameterValue(ULD_AIRCRAFT_COMPATIBILITY_FOR_INBOUND))))) {
					try {
						validateULDIncomatibility(mailUploadVO, flightValidationVO);
					} finally {
					}
				}
			}
		}
		if (!Objects.equals(scannedMailDetailsVO.getContainerNumber(), null)
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			findAndUpdateScannedMailDetails(scannedMailDetailsVO);
		}
		if (containerAssignmentVO != null && containerAssignmentVO.getTransitFlag() != null) {
			scannedMailDetailsVO.setTransitFlag(containerAssignmentVO.getTransitFlag());
		}
		return scannedMailDetailsVO;
	}

	/**
	 * Method		:	MailUploadController.populateScannedMailDetails Added by 	:	A-8164 on 20-Feb-2019 Used for 	:	Populating ScannedMailDetailsVO Parameters	:	@param containerAssignmentVO Parameters	:	@param mailUploadVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	ScannedMailDetailsVO
	 */
	public ScannedMailDetailsVO populateScannedMailDetails(ContainerAssignmentVO containerAssignmentVO,
														   MailUploadVO mailUploadVO) {
		LoginProfile logonAttributes = null;
		if (containerAssignmentVO != null && containerAssignmentVO.getFlightDate() == null
				&& containerAssignmentVO.getFlightSequenceNumber() > 0
				&& Objects.equals(mailUploadVO.getOperationType(), "I")) {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
			flightFilterVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
			flightFilterVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
			flightFilterVO.setStation(mailUploadVO.getScannedPort());
			flightFilterVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			flightFilterVO.setDirection("I");
			flightFilterVO.setActiveAlone(false);
			Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
			containerAssignmentVO.setFlightDate(flightValidationVOs.iterator().next().getFlightDate().toZonedDateTime());
		}
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		boolean containerAssignmentCheck = containerAssignmentVO != null
				&& containerAssignmentVO.getFlightDate() != null && containerAssignmentVO.getFlightNumber() != null;
		if ((MailConstantsVO.FLAG_YES.equals(mailUploadVO.getOverrideULDVal())
				&& mailUploadVO.getFlightNumber() != null)) {
			containerAssignmentCheck = false;
		}
		boolean containerNotInPort = false;
		if (Objects.equals(mailUploadVO.getOperationType(), "O") && containerAssignmentVO != null
				&& !Objects.equals(containerAssignmentVO.getAirportCode(), mailUploadVO.getScannedPort())) {
			containerAssignmentCheck = false;
			containerNotInPort = true;
		}
		if (!containerNotInPort) {
			scannedMailDetailsVO.setCarrierCode(mailUploadVO.getCarrierCode());
		}
		if (mailUploadVO.getFlightNumber() != null && !containerAssignmentCheck) {
			scannedMailDetailsVO.setFlightNumber(mailUploadVO.getFlightNumber());
		} else if (containerAssignmentCheck
				&& !MailConstantsVO.DESTN_FLT_STR.equals(containerAssignmentVO.getFlightNumber())) {
			scannedMailDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
		}
		if (containerAssignmentCheck && containerAssignmentVO.getCompanyCode() != null) {
			scannedMailDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
		} else if (mailUploadVO.getCompanyCode() != null) {
			scannedMailDetailsVO.setCompanyCode(mailUploadVO.getCompanyCode());
		}
		if (mailUploadVO.getContainerNumber() != null) {
			scannedMailDetailsVO.setContainerNumber(mailUploadVO.getContainerNumber());
		} else if (containerAssignmentVO != null && containerAssignmentVO.getContainerNumber() != null) {
			scannedMailDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
		}
		if (mailUploadVO.getContainerType() != null) {
			scannedMailDetailsVO.setContainerType(mailUploadVO.getContainerType());
		} else if (containerAssignmentVO != null && containerAssignmentVO.getContainerType() != null) {
			scannedMailDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
		}
		if (mailUploadVO.getFlightDate() != null && mailUploadVO.getFlightNumber() != null
				&& !containerAssignmentCheck) {
			scannedMailDetailsVO.setFlightDate(mailUploadVO.getFlightDate());
		} else if (containerAssignmentCheck) {
			scannedMailDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
		}
		if (mailUploadVO.getCarrierCode() != null && mailUploadVO.getFlightNumber() != null && !containerAssignmentCheck
				&& !containerNotInPort) {
			scannedMailDetailsVO.setCarrierCode(mailUploadVO.getCarrierCode());
		} else if (containerAssignmentCheck && containerAssignmentVO.getCarrierCode() != null && !containerNotInPort) {
			scannedMailDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
		} else if (mailUploadVO.getCarrierCode() != null && !containerAssignmentCheck && containerAssignmentVO != null
				&& containerAssignmentVO.getCarrierCode() != null && scannedMailDetailsVO.getCarrierCode() != null
				&& scannedMailDetailsVO.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())
				&& !containerNotInPort) {
			scannedMailDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
		}
		if (mailUploadVO.getScannedPort() != null) {
			scannedMailDetailsVO.setAirportCode(mailUploadVO.getScannedPort());
		} else if (containerAssignmentCheck && containerAssignmentVO.getAirportCode() != null) {
			scannedMailDetailsVO.setAirportCode(containerAssignmentVO.getAirportCode());
		}
		if (containerAssignmentVO != null && containerAssignmentVO.getCarrierId() != 0
				&& scannedMailDetailsVO.getCarrierCode() != null && containerAssignmentVO.getCarrierCode() != null
				&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())) {
			scannedMailDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
		} else if (StringUtils.isNotBlank(scannedMailDetailsVO.getCarrierCode()) && scannedMailDetailsVO.getCompanyCode() != null) {
			int carrierId = 0;
			try {
				carrierId = findAirlineDescription(scannedMailDetailsVO.getCompanyCode(),
						scannedMailDetailsVO.getCarrierCode()).getAirlineIdentifier();
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			scannedMailDetailsVO.setCarrierId(carrierId);
		}
		if (containerAssignmentVO != null && containerAssignmentVO.getDestination() != null) {
			scannedMailDetailsVO.setDestination(containerAssignmentVO.getDestination());
		}
		if (mailUploadVO.getOperationType() != null) {
			scannedMailDetailsVO.setOperationType(mailUploadVO.getOperationType());
			if (MailConstantsVO.OPERATION_OUTBOUND.equals(mailUploadVO.getOperationType())) {
				scannedMailDetailsVO.setProcessPoint("");
				if (containerAssignmentCheck && containerAssignmentVO.getPou() != null) {
					scannedMailDetailsVO.setPou(containerAssignmentVO.getPou());
				}
			} else {
				scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
				if (containerAssignmentCheck && containerAssignmentVO.getAirportCode() != null) {
					scannedMailDetailsVO.setPol(containerAssignmentVO.getAirportCode());
				}
			}
		}
		if (containerAssignmentVO != null && "-1".equals(containerAssignmentVO.getFlightNumber())
				&& containerAssignmentVO.getDestination() != null) {
			scannedMailDetailsVO.setDestination(containerAssignmentVO.getDestination());
		}
		if (mailUploadVO.getFlightSequenceNumber() != 0 && !containerAssignmentCheck) {
			scannedMailDetailsVO.setFlightSequenceNumber(mailUploadVO.getFlightSequenceNumber());
		} else {
			if (containerAssignmentCheck && containerAssignmentVO != null) {
				scannedMailDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			}
		}
		if (containerAssignmentVO != null) {
			scannedMailDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
			scannedMailDetailsVO.setIsContainerPabuilt(containerAssignmentVO.getPoaFlag());
			scannedMailDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
			mailUploadVO.setContainerType(containerAssignmentVO.getContainerType());
		}
		if (containerAssignmentVO != null && containerAssignmentVO.getUldFulIndFlag() != null) {
			scannedMailDetailsVO.setUldFulIndFlag(containerAssignmentVO.getUldFulIndFlag());
		}
		if (containerAssignmentVO != null && containerAssignmentVO.getUldReferenceNo() > 0) {
			scannedMailDetailsVO.setUldReferenceNo(containerAssignmentVO.getUldReferenceNo());
		}
		return scannedMailDetailsVO;
	}

	/**
	 * @author A-7540
	 * @return
	 * @throws MailMLDBusniessException
	 * @throws MailHHTBusniessException
	 */
	public Collection<ErrorVO> processResditMails(Collection<MailScanDetailVO> mailScanDetailVOs)
			throws MailOperationsBusinessException, MailMLDBusniessException, MailHHTBusniessException {
		boolean mailbagPresent = false;
		boolean coTerminusRdyForDelivery = false;
		//TODO: Master service to be corrected
//		OfficeOfExchange doe = null;
//		OfficeOfExchange ooe = null;
		String paCode_int = null;
		Collection<ErrorVO> allErrorVOs = new ArrayList<ErrorVO>();
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		paCode_int = findSystemParameterValue(MailConstantsVO.USPS_INTERNATIONAL_PA);
		HashMap<String, Mailbag> mailbagDetailsMap = new HashMap<>();
		if (mailScanDetailVOs != null && mailScanDetailVOs.size() > 0) {
			for (MailScanDetailVO mailScanDetailVO : mailScanDetailVOs) {
				String airport = "";
//				try {
//					doe = OfficeOfExchange.find(mailScanDetailVO.getCompanyCode(),
//							mailScanDetailVO.getMailBagId().substring(6, 12));
//					airport = findAirportCityForMLD(mailScanDetailVO.getCompanyCode(), doe.getCityCode());
//				} catch (FinderException e) {
//					e.getMessage();
//				}
				Mailbag mailbagToFindPA = null;
				String poaCode = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailScanDetailVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber() > 0
						? mailScanDetailVO.getMailSequenceNumber()
						: findMailSequenceNumber(mailScanDetailVO.getMailBagId(), mailScanDetailVO.getCompanyCode()));
				try {
					mailbagToFindPA = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					e.getMessage();
				}
				if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
					poaCode = mailbagToFindPA.getPaCode();
				} else {
//					try {
//						ooe = OfficeOfExchange.find(mailScanDetailVO.getCompanyCode(),
//								mailScanDetailVO.getMailBagId().substring(0, 6));
//					} catch (FinderException e) {
//						e.getMessage();
//					}
//					poaCode = ooe.getPoaCode();
				}
				ZonedDateTime dspDate = localDateUtil.getLocalDate(mailScanDetailVO.getAirport(), true);
				if (mailbagToFindPA != null) {
					dspDate = localDateUtil.getLocalDateTime(mailbagToFindPA.getDespatchDate(), null);
				}
				coTerminusRdyForDelivery = mailController.validateCoterminusairports(airport,
						mailScanDetailVO.getAirport(), MailConstantsVO.RESDIT_DELIVERED, poaCode, dspDate);
				if (airport.equals(mailScanDetailVO.getAirport())
						|| (isCoterminusConfigured != null && "Y".equals(isCoterminusConfigured))
						&& coTerminusRdyForDelivery) {
					Mailbag mailbag = null;
					boolean isValid = true;
					MailUploadVO mailUploadVO = new MailUploadVO();
					String mailbagID = "";
					long mailseqnum = 0;
					try {
						MailbagPK mailbagPK = new MailbagPK();
						mailbagPK.setCompanyCode(mailScanDetailVO.getCompanyCode());
						mailbagPK.setMailSequenceNumber(
								mailScanDetailVO.getMailSequenceNumber() > 0 ? mailScanDetailVO.getMailSequenceNumber()
										: findMailSequenceNumber(mailScanDetailVO.getMailBagId(),
										mailScanDetailVO.getCompanyCode()));
						try {
							mailbag = Mailbag.find(mailbagPK);
							mailbagPresent = true;
						} catch (FinderException e) {
							e.getMessage();
						}
					} finally {
					}
					if (!mailbagPresent) {
						mailUploadVO = constructMailUploadVOFromScanData(mailScanDetailVO.getScanData());
						mailUploadVO.setConsignmentDocumentNumber(mailScanDetailVO.getConsignmentNumber());
						mailbagID = mailUploadVO.getMailTag();
						isValid = validateMailbag(mailUploadVO.getCompanyCode(), mailbagID);
					}
					if (isValid) {
						mailUploadVO.setScannedPort(mailScanDetailVO.getAirport());
						if (mailbag != null
								&& MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
							ErrorVO errorVO = new ErrorVO("delivered");
							allErrorVOs.add(errorVO);
						} else
							mailseqnum = updateRdtProcessingMails(mailUploadVO, mailbag, mailbagDetailsMap);
					} else {
						ErrorVO errorVO = new ErrorVO("invalid_mailbag");
						Object[] obj = new Object[1];
						obj[0] = mailbagID;
						errorVO.setErrorData(obj);
						allErrorVOs.add(errorVO);
					}
					validateULDAircraftComapctibility(mailUploadVO);
					String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
					if (importEnabled != null && importEnabled.contains("D")) {
						mailScanDetailVO.setMailSequenceNumber((int) mailseqnum);
						try {
							mailOperationsMRAProxy.importResditDataToMRA(mailScanDetailVO);
						} catch (BusinessException e) {
							throw new SystemException(e.getMessage(), e.getMessage(), e);
						}
					}
				} else {
					ErrorVO errorVO = new ErrorVO("invalid_airport");
					allErrorVOs.add(errorVO);
				}
			}
		}
		return allErrorVOs;
	}

	private long updateRdtProcessingMails(MailUploadVO mailUploadVO, Mailbag mailbag,
										  HashMap<String, Mailbag> mailbagDetailsMap) {
		MailbagVO mailbagVO = new MailbagVO();
		MailbagHistoryVO mailbagHistoryVO = null;
		AirlineValidationVO airlineValidationVO = null;
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (mailUploadVO.getCompanyCode() != null && mailUploadVO.getCarrierCode() != null) {
			try {
				airlineValidationVO = sharedAirlineProxy.validateAlphaCode(mailUploadVO.getCompanyCode(),
						mailUploadVO.getCarrierCode());
			} catch (SharedProxyException e) {
				airlineValidationVO = null;
			}
		}
		if (mailbag != null) {
			if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())
					&& (MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode()))) {
			} else {
				if (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())
						|| MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) {
					mailbag.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
					mailbag.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
					mailbag.setSegmentSerialNumber(MailConstantsVO.ZERO);
					mailbag.setLatestStatus(mailUploadVO.getScanType());
					mailbag.setScannedPort(mailUploadVO.getScannedPort());
					ZonedDateTime localDate = localDateUtil.getLocalDate(mailUploadVO.getScannedPort(), true);
					localDate = LocalDate.withDateAndTime(localDate, String.valueOf(mailUploadVO.getDateTime()));
					mailbag.setScannedDate(localDate);
					mailbag.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
					mailbag.setLastUpdatedUser(logonAttributes.getUserId());
					mailbag.setMailbagSource("RESDIT");
					if (airlineValidationVO != null) {
						mailbag.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					}
				}
			}
			mailbagVO = mailbag.retrieveVO();
			if (MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setLatestStatus(mailUploadVO.getScanType());
				mailbagVO.setScannedPort(mailUploadVO.getScannedPort());
				mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
				mailbagVO.setMailbagSource("RESDIT");
				if (airlineValidationVO != null) {
					mailbagVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
				}
			} else if (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setPou(null);
			}
			constructOriginDestinationDetails(mailbagVO);
			mailbagVO.setMailServiceLevel(mailbag.getMailServiceLevel());
			mailbagVO.setConsignmentDate(
					(localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), mailbagVO.getScannedPort())));
			if (mailUploadVO.getContainerType() != null && mailUploadVO.getContainerNumber() != null
					&& (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())
					|| (!MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())
					&& !MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())))) {
				mailbag.setUldNumber(mailUploadVO.getContainerNumber());
				mailbag.setContainerType(mailUploadVO.getContainerType());
				mailbagVO.setUldNumber(mailUploadVO.getContainerNumber());
				mailbagVO.setContainerNumber(mailUploadVO.getContainerNumber());
				mailbagVO.setContainerType(mailUploadVO.getContainerType());
			}
			mailbagVO.setMailbagSource(MailConstantsVO.MAIL_SRC_RESDIT);
			mailbagVO.setMailSource(MailConstantsVO.MAIL_SRC_RESDIT);
			mailbagVO.setScannedPort(mailUploadVO.getScannedPort());
			if (MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
					|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setCarrierCode(mailUploadVO.getCarrierCode());
				mailbagVO.setFlightNumber(mailUploadVO.getFlightNumber());
				mailbagVO.setFlightDate(mailUploadVO.getFlightDate());
				mailbagVO.setPou(mailUploadVO.getContainerPOU());
			}
			if (MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
					&& !MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) {
				mailbagVO.setOrigin(mailUploadVO.getScannedPort());
				mailbag.setOrigin(mailUploadVO.getScannedPort());
			} else if (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setDestination(mailUploadVO.getScannedPort());
			}
		} else {
			mailbagVO = populateMailbagVO(mailUploadVO);
			constructOriginDestinationDetails(mailbagVO);
			if (MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setOrigin(mailUploadVO.getScannedPort());
			}
			if (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode())) {
				mailbagVO.setDestination(mailUploadVO.getScannedPort());
			}
			if (airlineValidationVO != null) {
				mailbagVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			}
			mailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
			if (!mailbagDetailsMap.containsKey(mailbagVO.getMailbagId())) {
				mailbag = new Mailbag(mailbagVO);
				mailbag.setDespatchDate(localDateUtil.getLocalDate(null, true));
				if (mailbag != null) {
					mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
				}
				mailbagDetailsMap.put(mailbagVO.getMailbagId(), mailbag);
			} else if (mailbagDetailsMap.containsKey(mailbagVO.getMailbagId())) {
				mailbag = mailbagDetailsMap.get(mailbagVO.getMailbagId());
				mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
				if (mailUploadVO.getContainerType() != null && mailUploadVO.getContainerNumber() != null
						&& !MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
					mailbag.setUldNumber(mailUploadVO.getContainerNumber());
					mailbag.setContainerType(mailUploadVO.getContainerType());
				}
			}
		}
		ZonedDateTime localDate = localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true);
		localDate = LocalDate.withDateAndTime(localDate, String.valueOf(mailUploadVO.getDateTime()));
		mailbagVO.setScannedDate(localDate);
		if (MailConstantsVO.RESDIT_DELIVERED.equals(mailUploadVO.getEventCode()) && mailbag != null) {
			if (mailController.isUSPSMailbag(mailbagVO)) {
				try {
					new ULDForSegment().updatemailperformanceDetails(mailbagVO, null, mailbag);
				} catch (PersistenceException e) {
					e.getMessage();
				}
			}
		}
		try {
			mailbagHistoryVO = constructDAO().findCarditDetailsOfMailbag(logonAttributes.getCompanyCode(),
					mailbagVO.getMailbagId(), mailbagVO.getMailSequenceNumber());
			if (mailbagHistoryVO != null) {
				if ((MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
						|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode()))
						&& mailUploadVO.getFlightNumber() == null && airlineValidationVO != null) {
					mailbagVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
					if (mailbagHistoryVO.getFlightNumber() == null) {
						mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
						mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
					}
				} else if ((MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
						|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode()))
						&& mailUploadVO.getFlightNumber() != null && mailUploadVO.getFlightDate() != null) {
					mailbagVO.setFlightNumber(mailUploadVO.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailUploadVO.getFlightSequenceNumber());
					mailbagVO.setFlightDate(mailUploadVO.getFlightDate());
					mailbagVO.setCarrierCode(mailUploadVO.getCarrierCode());
					mailbagVO.setPou(mailUploadVO.getContainerPOU());
				} else {
					mailbagVO.setCarrierId(mailbagHistoryVO.getCarrierId());
					mailbagVO.setFlightNumber(mailbagHistoryVO.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailbagHistoryVO.getFlightSequenceNumber());
					mailbagVO.setFlightDate(mailbagHistoryVO.getFlightDate());
					mailbagVO.setCompanyCode(mailbagHistoryVO.getCompanyCode());
					mailbagVO.setPol(mailbagHistoryVO.getScannedPort());
				}
			}
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
		mailbagVO.setMessageSenderIdentifier(mailUploadVO.getMessageSenderIdentifier());
		mailbagVO.setMessageRecipientIdentifier(mailUploadVO.getMessageRecipientIdentifier());
		mailbagVOs.add(mailbagVO);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagMailbagHistoryForDelivery(mailbagVOs, ContextUtil.getInstance().getTxContext(ContextUtil.TRIGGER_POINT));
		return mailbag.getMailSequenceNumber();
	}

	/**
	 * @param mailUploadVO
	 * @return
	 * @throws SystemException
	 */
	private MailbagVO populateMailbagVO(MailUploadVO mailUploadVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		MailbagVO mailbagVO = new MailbagVO();
		String paCode_int = null;
		String orgPaCod = null;
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagVO.setCarrierId(mailUploadVO.getCarrierId());
		mailbagVO.setCarrierCode(mailUploadVO.getCarrierCode());
		mailbagVO.setMailbagId(mailUploadVO.getMailTag());
		mailbagVO.setLatestStatus(mailUploadVO.getScanType());
		mailbagVO.setCarrierId(mailUploadVO.getCarrierId());
		mailbagVO.setMailbagSource(MailConstantsVO.MAIL_SRC_RESDIT);
		mailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_SRC_RESDIT);
		mailbagVO.setMailSource(MailConstantsVO.MAIL_SRC_RESDIT);
		mailbagVO.setPou(mailUploadVO.getContainerPOU());
		if (mailUploadVO.getContainerType() != null && mailUploadVO.getContainerNumber() != null) {
			mailbagVO.setUldNumber(mailUploadVO.getContainerNumber());
			mailbagVO.setContainerNumber(mailUploadVO.getContainerNumber());
			mailbagVO.setContainerType(mailUploadVO.getContainerType());
		} else if (mailUploadVO.getContainerPOU() != null) {
			mailbagVO.setUldNumber("BULK-" + mailUploadVO.getContainerPOU());
			mailbagVO.setContainerNumber("BULK-" + mailUploadVO.getContainerPOU());
			mailbagVO.setContainerType(MailConstantsVO.BULK_TYPE);
		} else {
			mailbagVO.setUldNumber("BULK-" + mailUploadVO.getDestination());
			mailbagVO.setContainerNumber("BULK-" + mailUploadVO.getDestination());
			mailbagVO.setContainerType(MailConstantsVO.BULK_TYPE);
		}
		if (mailUploadVO.getFlightNumber() != null
				&& MailConstantsVO.RESDIT_RECEIVED.equals(mailUploadVO.getEventCode())
				|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailUploadVO.getEventCode())) {
			mailbagVO.setFlightNumber(mailUploadVO.getFlightNumber());
		} else {
			mailbagVO.setFlightNumber("0000");
		}
		if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailUploadVO.getScanType())) {
			mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
		} else
			mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		mailbagVO.setFlightSequenceNumber(0);
		if (null != mailUploadVO.getFlightDate()) {
			mailbagVO.setFlightDate(mailUploadVO.getFlightDate());
		}
		if (mailUploadVO.getDestination() != null && mailUploadVO.getDestination().trim().length() > 0) {
			mailbagVO.setDestination(mailUploadVO.getDestination());
		} else {
			mailbagVO.setDestination(mailUploadVO.getContainerPOU());
		}
		mailbagVO.setMailStatus(mailUploadVO.getScanType());
		if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailUploadVO.getScanType())) {
			mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);
		}
		mailbagVO.setOoe(mailUploadVO.getOrginOE());
		mailbagVO.setDoe(mailUploadVO.getDestinationOE());
		mailbagVO.setMailCategoryCode(mailUploadVO.getCategory());
		mailbagVO.setRdtProcessing(true);
		String OOE = mailbagVO.getOoe();
		if (mailbagVO != null && mailbagVO.getPaCode() != null) {
			orgPaCod = mailbagVO.getPaCode();
		} else {
			try {
				orgPaCod = findPAForOfficeOfExchange(logonAttributes.getCompanyCode(), OOE);
			} finally {
			}
		}
		try {
			postalAdministrationVO = findPACode(logonAttributes.getCompanyCode(), orgPaCod);
		} finally {
		}
		paCode_int = findSystemParameterValue(MailConstantsVO.USPS_INTERNATIONAL_PA);
		if (paCode_int.equals(postalAdministrationVO.getPaCode())) {
			mailbagVO.setPaCode(orgPaCod);
		} else {
			throw new SystemException(MailOperationsBusinessException.MAILTRACKING_USPS_PA);
		}
		mailbagVO.setDespatchId(mailUploadVO.getMailTag().substring(0, 20));
		mailbagVO.setDespatchSerialNumber(mailUploadVO.getMailTag().substring(16, 20));
		mailbagVO.setReceptacleSerialNumber(mailUploadVO.getMailTag().substring(20, 23));
		mailbagVO.setHighestNumberedReceptacle(mailUploadVO.getMailTag().substring(23, 24));
		mailbagVO.setRegisteredOrInsuredIndicator(mailUploadVO.getMailTag().substring(24, 25));
		mailbagVO.setMailSubclass(mailUploadVO.getSubClass());
		mailbagVO.setMailClass(mailUploadVO.getSubClass().substring(0, 1));
		mailbagVO.setConsignmentNumber(mailUploadVO.getConsignmentDocumentNumber());
		//TODO: hardcoding to be corrected
		Quantity Wgt = quantities.getQuantity(Quantities.WEIGHT,
				BigDecimal.valueOf(Double.parseDouble(mailUploadVO.getMailTag().substring(25, 29)) / 10),
				BigDecimal.valueOf(0.0), "K");
		String commodityCode = "";
		CommodityValidationVO commodityValidationVO = null;
		try {
			commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
			commodityValidationVO = validateCommodity(mailbagVO.getCompanyCode(), commodityCode, mailbagVO.getPaCode());
		} finally {
		}
		Mailbag.validateCommodity(commodityValidationVO, mailbagVO);
		mailbagVO.setWeight(Wgt);
		mailbagVO.setDuplicateNumber(0);
		mailbagVO.setType("M");
		mailbagVO.setDamageFlag(MailConstantsVO.FLAG_NO);
		mailbagVO.setDocumentOwnerIdr(0);
		mailbagVO.setLastUpdateUser(mailUploadVO.getScanUser());
		mailbagVO.setScannedUser(logonAttributes.getUserId());
		mailbagVO.setScannedPort(mailUploadVO.getScannedPort());
		mailbagVO.setSegmentSerialNumber(0);
		ZonedDateTime localDate = localDateUtil.getLocalDate(mailbagVO.getScannedPort(), true);
		localDate = LocalDate.withDateAndTime(localDate, String.valueOf(mailUploadVO.getDateTime()));
		mailbagVO.setScannedDate(localDate);
		mailbagVO.setYear(mailUploadVO.getYear());
		mailbagVO.setOrigin(mailUploadVO.getContainerPol());
		mailbagVO.setMailCompanyCode(logonAttributes.getCompanyCode());
		String scanWaveFlag = null;
		try {
			scanWaveFlag = constructDAO().checkScanningWavedDest(mailbagVO);
		} finally {
		}
		mailbagVO.setScanningWavedFlag(scanWaveFlag);
		if (mailController.isUSPSMailbag(mailbagVO)) {
			mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
		} else {
			mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
		}
		return mailbagVO;
	}

	/**
	 * @param companyCode
	 * @param mailBag
	 */
	private boolean validateMailbag(String companyCode, String mailBag) {
		Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();
		boolean isValidMailBag = false;
		if (mailBag.trim().length() == 29) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setDoe(mailBag.substring(6, 12));
			mailbagVO.setDespatchSerialNumber(mailBag.substring(16, 20));
			mailbagVO.setMailbagId(mailBag);
			mailbagVO.setMailSubclass(mailBag.substring(13, 15));
			mailbagVO.setMailCategoryCode(mailBag.substring(12, 13));
			mailbagVO.setOoe(mailBag.substring(0, 6));
			mailbagVO.setYear(Integer.parseInt(mailBag.substring(15, 16)));
			mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
			mailBagVOs.add(mailbagVO);
			try {
				isValidMailBag = validateMailBags(mailBagVOs);
			} catch (InvalidMailTagFormatException exception) {
				exception.getMessage();
			}
		} else {
			isValidMailBag = false;
		}
		return isValidMailBag;
	}

	/**
	 * Method		:	MailUploadController.findAndUpdateScannedMailDetails Added by 	:	U-1307 on 16-Aug-2019 Used for 	:	Find and update ScannedMailDetailsVO with total mail bag count and weight in container Added for 	:	ICRD-340690 Parameters	:	@param scannedMailDetailsVO  Return type	: 	void
	 */
	private void findAndUpdateScannedMailDetails(ScannedMailDetailsVO scannedMailDetailsVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		Collection<ContainerDetailsVO> currentContainerDetailsVOs = new ArrayList<>();
		if (scannedMailDetailsVO != null) {
			try {
				currentContainerDetailsVOs.add(constructContainerDetailsVO(scannedMailDetailsVO));
				if (MailConstantsVO.OPERATION_INBOUND.equals(scannedMailDetailsVO.getOperationType())) {
					if (scannedMailDetailsVO.getFlightSequenceNumber() > 0
							&& scannedMailDetailsVO.getFlightNumber() != null) {
						containerDetailsVOs = findMailbagsInContainerFromInboundForReact(currentContainerDetailsVOs);
					}
				} else {
					containerDetailsVOs = findMailbagsInContainer(currentContainerDetailsVOs);
				}
			} finally {
			}
			if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
				Map<String, String> stationParameters = null;
				Collection<String> parameterCodes = new ArrayList<>();
				parameterCodes.add(MailConstantsVO.PARAMETER_TYPE_DEFAULT_STATION_UNIT_WEIGHT);
				try {
					stationParameters = findStationParametersByCode(scannedMailDetailsVO.getCompanyCode(),
							scannedMailDetailsVO.getAirportCode(), parameterCodes);
				} finally {
				}
				if (stationParameters != null) {
					for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
						scannedMailDetailsVO.setTotalMailbagCount(containerDetailsVO.getTotalBags());
						scannedMailDetailsVO.setTotalMailbagWeight(convertUnits(containerDetailsVO.getTotalWeight(),
								stationParameters.get(MailConstantsVO.PARAMETER_TYPE_DEFAULT_STATION_UNIT_WEIGHT)));
						scannedMailDetailsVO.setReceivedMailbagCount(containerDetailsVO.getReceivedBags());
						scannedMailDetailsVO.setReceivedMailbagWeight(convertUnits(
								containerDetailsVO.getReceivedWeight(),
								stationParameters.get(MailConstantsVO.PARAMETER_TYPE_DEFAULT_STATION_UNIT_WEIGHT)));
						scannedMailDetailsVO.setDeliveredMailbagCount(containerDetailsVO.getDeliveredBags());
						scannedMailDetailsVO.setDeliveredMailbagWeight(convertUnits(
								containerDetailsVO.getDeliveredWeight(),
								stationParameters.get(MailConstantsVO.PARAMETER_TYPE_DEFAULT_STATION_UNIT_WEIGHT)));
					}
				}
			}
		}
	}

	/**
	 * Method		:	MailUploadController.convertUnits Added by 	:	U-1307 on 16-Aug-2019 Used for 	:	Convert weight to a particular unit  Parameters	:	@param weight Parameters	:	@param unitCode Parameters	:	@return  Return type	: 	Measure
	 */
	private Quantity convertUnits(Quantity weight, String unitCode) {
		Quantity convertedWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0));
		if (weight != null && weight.getValue().doubleValue() > 0 && isNotEmpty(unitCode)) {
			convertedWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
					BigDecimal.valueOf(weight.getValue().doubleValue()), unitCode);
		}
		return convertedWeight;
	}
	/**
	 * validateULDIncomatibility
	 * @author A-5526 for IASCB-34124
	 * @param flightValidationVO
	 * @throws SystemException
	 * @throws MailMLDBusniessException
	 */
	private void validateULDIncomatibility(MailUploadVO mailUploadVO, FlightValidationVO flightValidationVO) {
		Collection<String> parameterCodes = new ArrayList<String>();
		Map<String, String> systemParameters = null;
		parameterCodes.add("operations.flthandling.aircraftcompatibilityrequireduldtypes");
		try {
			systemParameters = neoMastersServiceUtils.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();
		if (mailUploadVO.getContainerNumber() != null && mailUploadVO.getContainerNumber().trim().length() > 0
				&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())) {
			String uldType = mailUploadVO.getContainerNumber().substring(0, 3);
			if (!uldTypeCodes.contains(uldType.toUpperCase())) {
				uldTypeCodes.add(uldType.toUpperCase());
			}
			uldNumberCodes.add(mailUploadVO.getContainerNumber());
		}
		Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
		if (flightValidationVO != null) {
			Collection<String> aircraftTypes = new ArrayList<String>();
			aircraftTypes.add(flightValidationVO.getAircraftType());
			ULDPositionFilterVO filterVO = null;
			Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
					systemParameters);
			if (validatedUldTypeCodes != null && validatedUldTypeCodes.size() > 0) {
				for (String uldType : validatedUldTypeCodes) {
					filterVO = new ULDPositionFilterVO();
					filterVO.setAircraftTypes(aircraftTypes);
					filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
					filterVO.setUldCode(uldType);
					filterVOs.add(filterVO);
				}
			}
		}
		if (filterVOs != null && filterVOs.size() > 0) {
			try {
				sharedULDProxy.findULDPosition(filterVOs);
			} catch (SharedProxyException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	private void validateULDAircraftComapctibility(MailUploadVO mailUploadVO) {
		if ((mailUploadVO.getFlightSequenceNumber() > 0
				|| ("MLD".equals(mailUploadVO.getMailSource()) && mailUploadVO.getFlightNumber() != null
				&& !MailConstantsVO.DESTN_FLT_STR.equals(mailUploadVO.getFlightNumber())))
				&& MailConstantsVO.ULD_TYPE.equals(mailUploadVO.getContainerType())) {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(mailUploadVO.getCompanyCode());
			flightFilterVO.setCarrierCode(mailUploadVO.getCarrierCode());
			flightFilterVO.setFlightNumber(mailUploadVO.getFlightNumber());
			flightFilterVO.setStation(mailUploadVO.getScannedPort());
			if ("MLD".equals(mailUploadVO.getMailSource())) {
				flightFilterVO.setStation(mailUploadVO.getContainerPol());
			}
			flightFilterVO.setDirection("O");
			flightFilterVO.setIncludeACTandTBC(true);
			if (mailUploadVO.getFlightDate() != null) {
				flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailUploadVO.getFlightDate()));
			}
			Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
			if ("MLD".equals(mailUploadVO.getMailSource()) && flightValidationVOs != null
					&& flightValidationVOs.size() == 0) {
				flightFilterVO.setStation(mailUploadVO.getContainerPOU());
				flightValidationVOs = validateFlight(flightFilterVO);
			}
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			if (flightValidationVOs != null && flightValidationVOs.size() == 1) {
				log.debug("flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidationVO = classicVOConversionMapper_.copyFlightValidationVO(flightValidVO);
					}
				} finally {
				}
				validateULDIncomatibility(mailUploadVO, flightValidationVO);
			}
		}
	}

	/**
	 * Method : MailUploadController.createMailUploadDetailsFromMLD2 Added by : A-8488 on 17-Mar-2019 Used for : saving mail details via mld Parameters :
	 * @param mLDMasterVO
	 * @return mailUploadVO
	 * @throws SystemException
	 * @throws MailMLDBusniessException
	 */
	//TODO: Exception handling to be corrected in Neo
	private MailUploadVO createMailUploadDetailsFromMLD2(MLDMasterVO mLDMasterVO)
			throws  MailMLDBusniessException {
		MLDDetailVO mLDDetailVO = mLDMasterVO.getMldDetailVO();
		MailUploadVO mailUploadVO = new MailUploadVO();
		if (MailConstantsVO.MLD_RCF.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& "U".equals(mLDMasterVO.getBarcodeType())) {
			mailUploadVO.setContaineDescription(MailConstantsVO.CONTAINER_STATUS_ARRIVAL);
		} else if (MailConstantsVO.MLD_DLV.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& "U".equals(mLDMasterVO.getBarcodeType())) {
			mailUploadVO.setContaineDescription(MailConstantsVO.CONTAINER_STATUS_DELIVERY);
		}
		if ((MailConstantsVO.MLD_NST.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_STG.equalsIgnoreCase(mLDMasterVO.getEventCOde()))
				&& MailConstantsVO.MLD_MODE_CARRIER.equals(mLDDetailVO.getMailModeOub())) {
			if (mLDDetailVO.getModeDescriptionOub() != null && mLDDetailVO.getCarrierCodeOub() == null) {
				mLDDetailVO.setCarrierCodeOub(mLDDetailVO.getModeDescriptionOub());
			}
			mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
			mLDDetailVO.setFlightSequenceNumberOub(-1);
			mailUploadVO.setToCarrierCode(mLDDetailVO.getCarrierCodeOub());
			if (MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
				mailUploadVO.setContainerPOU(mLDDetailVO.getPouOub());
			}
		}
		if (MailConstantsVO.MLD_REC.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& MailConstantsVO.MLD_MODE_CARRIER.equals(mLDDetailVO.getMailModeOub())) {
			if (mLDDetailVO.getModeDescriptionOub() != null && mLDDetailVO.getCarrierCodeOub() == null) {
				mLDDetailVO.setCarrierCodeOub(mLDDetailVO.getModeDescriptionOub());
			}
			mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
			mLDDetailVO.setFlightSequenceNumberOub(-1);
		}
		if (MailConstantsVO.MLD_RET.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& mLDMasterVO.getReasonCode() != null) {
			mailUploadVO.setReturnCode(mLDMasterVO.getReasonCode());
		}
		if (MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			if (mLDDetailVO.getModeDescriptionOub() != null && mLDDetailVO.getCarrierCodeOub() == null) {
				mLDDetailVO.setCarrierCodeOub(mLDDetailVO.getModeDescriptionOub());
			}
			if (mLDDetailVO.getModeDescriptionInb() != null && mLDDetailVO.getCarrierCodeInb() == null) {
				mLDDetailVO.setCarrierCodeInb(mLDDetailVO.getModeDescriptionInb());
			}
		}
		if (MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL.equals(mLDMasterVO.getBarcodeType()) && mLDDetailVO != null
				&& mLDDetailVO.getMailIdr() != null && mLDDetailVO.getMailIdr().length() != 29) {
			throw new MailMLDBusniessException(MailMLDBusniessException.INVALID_MAILTAG);
		}
		if (MailConstantsVO.MLD_DLV.equals(mLDMasterVO.getEventCOde()) && mLDDetailVO.getMailModeInb() == null) {
			MailbagPK mailbagpk = new MailbagPK();
			mailbagpk.setCompanyCode(mLDMasterVO.getCompanyCode());
			mailbagpk.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mLDDetailVO.getMailIdr(),
					mLDMasterVO.getCompanyCode()));
			Mailbag mailbag;
			try {
				mailbag = Mailbag.findMailbag(mailbagpk);
			} catch (FinderException e) {
				log.error(FINDER_EXCEPTION_CAUGHT);
				throw new SystemException(MailMLDBusniessException.MAILBAG_NOT_ARRIVED_FOR_DLV);
			}
			if (mailbag != null && !mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
				throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_ARRIVED);
			}
		}
		if (MailConstantsVO.MLD_TFD.equals(mLDMasterVO.getEventCOde())) {
			MailbagPK mailbagpk = new MailbagPK();
			mailbagpk.setCompanyCode(mLDMasterVO.getCompanyCode());
			mailbagpk.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mLDDetailVO.getMailIdr(),
					mLDMasterVO.getCompanyCode()));
			Mailbag mailbag;
			try {
				mailbag = Mailbag.findMailbag(mailbagpk);
			} catch (FinderException e) {
				log.error(FINDER_EXCEPTION_CAUGHT);
				throw new SystemException(MailMLDBusniessException.MAILBAG_NOT_PRESENT_FOR_TFD);
			}
			if (mailbag != null && mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)
					&& mLDDetailVO.getPolInb() != null && !mLDDetailVO.getPolInb().equals(mailbag.getScannedPort())
					&& mLDDetailVO.getFlightNumberInb() == null) {
				throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_AT_PORT);
			}
			if (mailbag != null && !mailbag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)
					&& mLDDetailVO.getFlightNumberInb() == null) {
				throw new MailMLDBusniessException(MailMLDBusniessException.TRANSFER_WITHOUT_ARRIVAL);
			}
		}
		if (mLDDetailVO.getFlightNumberOub() != null && " 1".equals(mLDDetailVO.getFlightNumberOub())) {
			mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
		}
		if (MailConstantsVO.MLD_NST.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_REC.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_STG.equalsIgnoreCase(mLDMasterVO.getEventCOde())) {
			if (mLDDetailVO.getPouOub() == null || mLDDetailVO.getPouOub().trim().length() == 0) {
				mLDDetailVO
						.setPouOub(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getDestAirport()));
			} else {
				mLDDetailVO.setPouOub(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDDetailVO.getPouOub()));
			}
			mLDMasterVO.setSenderAirport(
					findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getSenderAirport()));
			if ("-1".equalsIgnoreCase(mLDDetailVO.getFlightNumberOub()) || (mLDDetailVO.getFlightNumberOub() == null
					&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_NST.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_STG.equalsIgnoreCase(mLDMasterVO.getEventCOde())))) {
				mLDMasterVO.setDestAirport(mLDDetailVO.getPouOub());
			}
			if (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0) {
				Map<String, String> dummyTrolleyNumberMap = new HashMap<String, String>();
				String serialNumber = "";
				serialNumber = generateTrolleyNumber(mLDMasterVO, dummyTrolleyNumberMap);
				mLDMasterVO.setUldNumber(serialNumber);
				mLDMasterVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0)) {
			mLDMasterVO.setUldNumber("BULK-" + mLDMasterVO.getSenderAirport());
		} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0)) {
			mLDMasterVO.setUldNumber("");
		}
		return mailUploadVO;
	}

	/**
	 * Method : MailUploadController.createMailUploadDetailsFromMLD2 Added by : A-8488 on 17-Mar-2019 Used for : saving mail details via mld Parameters :
	 * @param mLDMasterVO
	 * @return mailUploadVO
	 * @throws SystemException
	 * @throws MailMLDBusniessException
	 */
	//TODO: Exception handling to be corrected in Neo
	private MailUploadVO createMailUploadDetailsFromMLD1(MLDMasterVO mLDMasterVO)
			throws  MailMLDBusniessException {
		MLDDetailVO mLDDetailVO = mLDMasterVO.getMldDetailVO();
		MailUploadVO mailUploadVO = new MailUploadVO();
		if (MailConstantsVO.MAIL_STATUS_HND.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& "U".equals(mLDMasterVO.getBarcodeType())) {
			mailUploadVO.setContaineDescription(MailConstantsVO.CONTAINER_STATUS_ARRIVAL);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& "U".equals(mLDMasterVO.getBarcodeType())) {
			mailUploadVO.setContaineDescription(MailConstantsVO.CONTAINER_STATUS_DELIVERY);
		}
		if ((MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MLD_REC.equalsIgnoreCase(mLDMasterVO.getEventCOde()))
				&& MailConstantsVO.MLD_MODE_CARRIER.equals(mLDDetailVO.getMailModeOub())
				&& mLDDetailVO.getModeDescriptionOub() != null) {
			if (mLDDetailVO.getCarrierCodeOub() == null) {
				mLDDetailVO.setCarrierCodeOub(mLDDetailVO.getModeDescriptionOub());
			}
			mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
			mLDDetailVO.setFlightSequenceNumberOub(-1);
		}
		if (MailConstantsVO.MLD_TFD.equals(mLDMasterVO.getEventCOde())) {
			if (isNotEmpty(mLDDetailVO.getModeDescriptionOub()) && mLDDetailVO.getCarrierCodeOub() == null) {
				mLDDetailVO.setCarrierCodeOub(mLDDetailVO.getModeDescriptionOub());
			}
			if (!isNotEmpty(mLDDetailVO.getFlightNumberOub())) {
				mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
			}
			mLDDetailVO.setFlightSequenceNumberOub(-1);
			mailUploadVO.setToCarrierCode(mLDDetailVO.getCarrierCodeOub());
			mailUploadVO.setToFlightNumber(mLDDetailVO.getFlightNumberOub());
			mailUploadVO.setToFlightDate(mLDDetailVO.getFlightOperationDateOub());
		}
		if (MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL.equals(mLDMasterVO.getBarcodeType()) && mLDDetailVO != null
				&& mLDDetailVO.getMailIdr() != null && mLDDetailVO.getMailIdr().length() != 29) {
			throw new MailMLDBusniessException(MailMLDBusniessException.INVALID_MAILTAG);
		}
		if (mLDDetailVO.getFlightNumberOub() != null && " 1".equals(mLDDetailVO.getFlightNumberOub())) {
			mLDDetailVO.setFlightNumberOub(String.valueOf(MailConstantsVO.DESTN_FLT));
		}
		if ((MailConstantsVO.ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				|| MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mLDMasterVO.getEventCOde()))
				&& (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0)) {
			if (mLDDetailVO.getPouOub() == null || mLDDetailVO.getPouOub().trim().length() == 0) {
				if ("1".equals(mLDMasterVO.getMessageVersion())) {
					mLDDetailVO.setPouOub(
							findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getDestAirport()));
				} else {
					String defaultAirport = findSystemParameterValue(DEFAULT_AIRPORTCODEFORGHA);
					mLDDetailVO.setPouOub(defaultAirport);
				}
			} else {
				if ("1".equals(mLDMasterVO.getMessageVersion())) {
					mLDDetailVO.setPouOub(findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDDetailVO.getPouOub()));
				}
			}
			mLDMasterVO.setSenderAirport(
					findAirportCityForMLD(mLDMasterVO.getCompanyCode(), mLDMasterVO.getSenderAirport()));
			if ("-1".equalsIgnoreCase(mLDDetailVO.getFlightNumberOub()) || (mLDDetailVO.getFlightNumberOub() == null
					&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					|| MailConstantsVO.MAIL_STATUS_ALL.equalsIgnoreCase(mLDMasterVO.getEventCOde())))) {
				mLDMasterVO.setDestAirport(mLDDetailVO.getPouOub());
			}
			Map<String, String> dummyTrolleyNumberMap = new HashMap<String, String>();
			String serialNumber = "";
			serialNumber = generateTrolleyNumber(mLDMasterVO, dummyTrolleyNumberMap);
			mLDMasterVO.setUldNumber(serialNumber);
		} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0)) {
			mLDMasterVO.setUldNumber("BULK-" + mLDMasterVO.getSenderAirport());
		} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equalsIgnoreCase(mLDMasterVO.getEventCOde())
				&& (mLDMasterVO.getUldNumber() == null || mLDMasterVO.getUldNumber().trim().length() == 0)) {
			mLDMasterVO.setUldNumber("");
		}
		return mailUploadVO;
	}

	/**
	 * @author A-7540
	 * @return
	 * @throws MailMLDBusniessException
	 * @throws MailHHTBusniessException
	 */
	public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadVO> mailScanDetailVOs)
			throws MailOperationsBusinessException, MailMLDBusniessException, MailHHTBusniessException {
		boolean coTerminusRdyForDelivery = false;
		//TODO: Master service to be corrected
//		OfficeOfExchange doe = null;
//		OfficeOfExchange ooe = null;
		String paCode_int = null;
		Collection<ErrorVO> allErrorVOs = new ArrayList<ErrorVO>();
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		paCode_int = findSystemParameterValue(MailConstantsVO.USPS_INTERNATIONAL_PA);
		HashMap<String, Mailbag> mailbagDetailsMap = new HashMap<>();
		if (mailScanDetailVOs != null && mailScanDetailVOs.size() > 0) {
			MailResditMessageVO mailResditMessageVO = new MailResditMessageVO();
			mailResditMessageVO.setCompanyCode(mailScanDetailVOs.iterator().next().getCompanyCode());
			Mailbag mail = null;
			MailbagPK mailPk = new MailbagPK();
			mailPk.setCompanyCode((mailScanDetailVOs.iterator().next().getCompanyCode()));
			mailPk.setMailSequenceNumber((mailScanDetailVOs.iterator().next().getMailSequenceNumber() > 0)
					? (mailScanDetailVOs.iterator().next().getMailSequenceNumber())
					: findMailSequenceNumber((mailScanDetailVOs.iterator().next().getMailTag()),
					(mailScanDetailVOs.iterator().next().getCompanyCode())));
			if (mailPk.getMailSequenceNumber() > 0) {
				try {
					mail = Mailbag.find(mailPk);
				} catch (FinderException e) {
					mail = null;
				}
			}
			if (mail != null && mail.getPaCode() != null) {
				mailResditMessageVO.setPoaCode(mail.getPaCode());
			} else {
//				try {
//					ooe = OfficeOfExchange.find(mailScanDetailVOs.iterator().next().getCompanyCode(),
//							mailScanDetailVOs.iterator().next().getMailTag().substring(0, 6));
//				} catch (FinderException e) {
//					ooe = null;
//				}
//				if (ooe != null) {
//					mailResditMessageVO.setPoaCode(ooe.getPoaCode());
//				}
			}
			if (mailResditMessageVO.getPoaCode() == null) {
				mailResditMessageVO.setPoaCode(mailScanDetailVOs.iterator().next().getPaCode());
			}
			mailResditMessageVO.setMsgVersionNumber("1.1");
			mailResditMessageVO.setMsgDetails(mailScanDetailVOs.iterator().next().getRawMessageBlob());
			mailResditMessageVO.setCarditExist("Y");
			mailResditMessageVO.setEventCode(mailScanDetailVOs.iterator().next().getTotalEventCodes());
			mailResditMessageVO.setEventPort(mailScanDetailVOs.iterator().next().getScannedPort());
			mailResditMessageVO.setMessageType(mailScanDetailVOs.iterator().next().getMessageType());
			mailResditMessageVO.setMsgSequenceNumber(mailScanDetailVOs.iterator().next().getMsgSequenceNumber());
			String msgTxt = mailScanDetailVOs.iterator().next().getRawMessageBlob();
			if (msgTxt != null && msgTxt.length() > 4000) {
				mailResditMessageVO.setMsgText(msgTxt.trim().substring(0, 3999));
			} else {
				mailResditMessageVO.setMsgText(msgTxt);
			}
			MailResditMessage mailResditMessage = new MailResditMessage(mailResditMessageVO);
			for (MailUploadVO mailScanDetailVO : mailScanDetailVOs) {
				boolean mailbagPresent = false;
				String mailbagID = "";
				boolean isValid = true;
				Mailbag mailBag = null;
				long mailseqnum = 0;
				String poaCode = null;
				String airport = "";
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailScanDetailVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber() > 0
						? mailScanDetailVO.getMailSequenceNumber()
						: findMailSequenceNumber(mailScanDetailVO.getMailTag(), mailScanDetailVO.getCompanyCode()));
				if (mailbagPk.getMailSequenceNumber() > 0) {
					try {
						mailBag = Mailbag.find(mailbagPk);
						mailbagPresent = true;
						mailseqnum = mailbagPk.getMailSequenceNumber();
					} catch (FinderException e) {
						e.getMessage();
					}
				}
//				try {
//					doe = OfficeOfExchange.find(mailScanDetailVO.getCompanyCode(),
//							mailScanDetailVO.getMailTag().substring(6, 12));
//					airport = findAirportCityForMLD(mailScanDetailVO.getCompanyCode(), doe.getCityCode());
//					mailScanDetailVO.setDestination(airport);
//				} catch (FinderException e) {
//					e.getMessage();
//				}
//				try {
//					ooe = OfficeOfExchange.find(mailScanDetailVO.getCompanyCode(),
//							mailScanDetailVO.getMailTag().substring(0, 6));
//					if (MailConstantsVO.RESDIT_RECEIVED.equals(mailScanDetailVO.getEventCode())
//							|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailScanDetailVO.getEventCode())) {
//						airport = findAirportCityForMLD(mailScanDetailVO.getCompanyCode(), ooe.getCityCode());
//					}
//				} catch (FinderException e) {
//					e.getMessage();
//				}
//				if (mailBag != null && mailBag.getPaCode() != null) {
//					poaCode = mailBag.getPaCode();
//				} else if (ooe != null) {
//					poaCode = ooe.getPoaCode();
//				}
				mailbagID = mailScanDetailVO.getMailTag();
				isValid = validateMailbag(mailScanDetailVO.getCompanyCode(), mailbagID);
				ZonedDateTime dspDate = localDateUtil.getLocalDate(mailScanDetailVO.getScannedPort(), true);
				if (mailBag != null) {
					dspDate = localDateUtil.getLocalDateTime(mailBag.getDespatchDate(), null);
				}
				coTerminusRdyForDelivery = mailController.validateCoterminusairports(airport,
						mailScanDetailVO.getScannedPort(), mailScanDetailVO.getEventCode(), poaCode, dspDate);
				boolean validateAirportCode = true;
				if (airport.equals(mailScanDetailVO.getScannedPort()) || (isCoterminusConfigured != null
						&& "Y".equals(isCoterminusConfigured))
						&& (coTerminusRdyForDelivery
						&& MailConstantsVO.RESDIT_DELIVERED.equals(mailScanDetailVO.getEventCode())
						|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(mailScanDetailVO.getEventCode()))) {
					validateAirportCode = true;
				} else {
					if (MailConstantsVO.RESDIT_DELIVERED.equals(mailScanDetailVO.getEventCode())
							|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(mailScanDetailVO.getEventCode())) {
						validateAirportCode = false;
					} else {
						validateAirportCode = true;
					}
				}
				if (isValid) {
					if (mailBag != null && MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailBag.getLatestStatus())
							&& !mailScanDetailVO.getScannedPort().equals(mailBag.getDestination())
							&& (MailConstantsVO.RESDIT_DELIVERED.equals(mailScanDetailVO.getEventCode())
							|| MailConstantsVO.RESDIT_READYFOR_DELIVERY
							.equals(mailScanDetailVO.getEventCode()))) {
						ErrorVO errorVO = new ErrorVO("delivered");
						allErrorVOs.add(errorVO);
					} else
						mailseqnum = updateRdtProcessingMails(mailScanDetailVO, mailBag, mailbagDetailsMap);
				} else {
					ErrorVO errorVO = new ErrorVO("invalid_mailbag");
					Object[] obj = new Object[1];
					obj[0] = mailbagID;
					errorVO.setErrorData(obj);
					allErrorVOs.add(errorVO);
				}
				long messageIdentifier = 0;
				if (mailResditMessage != null) {
					messageIdentifier = mailResditMessage.getMessageIdentifier();
				}
				MailbagVO mailVO = new MailbagVO();
				if (mailBag != null) {
					mailVO = mailBag.retrieveVO();
				} else {
					mailVO = populateMailbagVO(mailScanDetailVO);
				}
				new ResditController().populateAndSaveMailResditVO(mailScanDetailVO, mailseqnum, messageIdentifier,
						mailVO);
				String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
				if (importEnabled != null && importEnabled.contains("D")
						&& (MailConstantsVO.RESDIT_DELIVERED.equals(mailScanDetailVO.getEventCode()) || (mailBag != null
						&& MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailBag.getLatestStatus())
						&& (MailConstantsVO.RESDIT_RECEIVED.equals(mailScanDetailVO.getEventCode())
						|| MailConstantsVO.RESDIT_UPLIFTED.equals(mailScanDetailVO.getEventCode()))))) {
					mailScanDetailVO.setMailSequenceNumber((int) mailseqnum);
					Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(mailScanDetailVO);
					try {
						mailOperationsMRAProxy.importMRAData(rateAuditVOs);
					} catch (BusinessException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
				}
			}
		}
		return allErrorVOs;
	}

	private Collection<RateAuditVO> createRateAuditVOs(MailUploadVO mailScanDetailVO) {
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		RateAuditVO rateAuditVO = new RateAuditVO();
		rateAuditVO.setCompanyCode(mailScanDetailVO.getCompanyCode());
		rateAuditVO.setTriggerPoint(MailConstantsVO.MAIL_STATUS_DELIVERED);
		rateAuditVO.setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber());
		if (MailConstantsVO.RESDIT_DELIVERED.equals(mailScanDetailVO.getEventCode())) {
			rateAuditDetailsVO.setFlightno(MailConstantsVO.DESTN_FLT_STR);
			rateAuditDetailsVO.setFlightseqno(MailConstantsVO.DESTN_FLT);
			rateAuditDetailsVO.setSegSerNo(MailConstantsVO.ZERO);
		}
		rateAuditDetailsVO.setMailSequenceNumber(mailScanDetailVO.getMailSequenceNumber());
		rateAuditDetails.add(rateAuditDetailsVO);
		rateAuditVO.setRateAuditDetails(rateAuditDetails);
		rateAuditVOs.add(rateAuditVO);
		return rateAuditVOs;
	}
	/**
	 * Method		:	MailUploadController.saveMailUploadDetailsFromMailOnload Added by 	:	A-8061 on 25-Mar-2021 Used for 	: Parameters	:	@param mailUploadVOs Parameters	:	@return  Return type	: 	Map<String,ErrorVO>
	 * @throws SystemException
	 */
	public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection<MailUploadVO> mailUploadVOs) {
		Map<String, ErrorVO> errorMap = new HashMap<>();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		Set<String> flights = new HashSet<>();
		StringBuilder flightKey = null;
		Set<String> mailBags = new HashSet<>();
		Set<String> containers = new HashSet<>();
		for (MailUploadVO mailUploadVO : mailUploadVOs) {
			try {
				this.cntAsgmntVO = null;
				scannedMailDetailsVO = saveMailUploadDetailsForAndroid(mailUploadVO, mailUploadVO.getContainerPol());
			} catch (MailMLDBusniessException | MailHHTBusniessException | MailOperationsBusinessException exception) {
				if (exception.getErrors() != null && !exception.getErrors().isEmpty()) {
					ErrorVO errorVO = exception.getErrors().iterator().next();
					if (!MailMLDBusniessException.MAILBAG_PRESENT.equals(errorVO.getErrorCode())) {
						errorMap.put(mailUploadVO.getMailTag(), errorVO);
					}
				} else {
					errorMap.put(mailUploadVO.getMailTag(), new ErrorVO(exception.getMessage()));
				}
			} catch (PersistenceException exception) {
				errorMap.put(mailUploadVO.getMailTag(), new ErrorVO(exception.getMessage()));
			} finally {
				mailBags.add(mailUploadVO.getMailTag());
				containers.add(mailUploadVO.getContainerNumber());
				flightKey = new StringBuilder(mailUploadVO.getCompanyCode()).append(SEPARATOR_UNDERSCORE)
						.append(mailUploadVO.getCarrierCode()).append(SEPARATOR_UNDERSCORE)
						.append(mailUploadVO.getFlightNumber()).append(SEPARATOR_UNDERSCORE)
						.append(mailUploadVO.getFlightDate()
								.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)))
						.append(SEPARATOR_UNDERSCORE).append(mailUploadVO.getContainerPol())
						.append(SEPARATOR_UNDERSCORE).append(mailUploadVO.getDestination());
				flights.add(flightKey.toString());
			}
		}
		offloadMailsNotInOnloadMessage(flights, containers, mailBags);
		return errorMap;
	}

	/**
	 * Method		:	MailUploadController.offloadMailsNotInOnloadMessage Added by 	:	A-8061 on 31-Mar-2021 Used for 	: Parameters	:	@param flights Parameters	:	@param containers Parameters	:	@param mailBags  Return type	: 	void
	 * @throws SystemException
	 */
	private void offloadMailsNotInOnloadMessage(Set<String> flights, Set<String> containers, Set<String> mailBags) {
		FlightValidationVO flightValidationVO = null;
		for (String key : flights) {
			flightValidationVO = constructFlightValidationVOForOnloadMessage(key);
			Collection<ContainerVO> containerVOs = findAllContainersInAssignedFlight(flightValidationVO);
			Collection<ContainerDetailsVO> containerMailDetailsVOs = null;
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			if (containerVOs != null && !containerVOs.isEmpty()) {
				validateAndoffloadContainerfromOnloadMessage(containerVOs, containers, containerDetailsVOs);
				containerMailDetailsVOs = findMailbagsInContainer(containerDetailsVOs);
				if (containerMailDetailsVOs != null && !containerMailDetailsVOs.isEmpty()) {
					for (ContainerDetailsVO cntDet : containerMailDetailsVOs) {
						validateAndoffloadMailsfromOnloadMessage(mailBags, cntDet);
					}
				}
			}
		}
	}

	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																				   Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		return constructDAO().findInboundFlightOperationsDetails(filterVO, manifestFilterVOs);
	}

	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO) {
		try {
			return constructDAO().findOffloadULDDetailsAtAirport(filterVO);
		} catch (PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
	}

	public String generateTrolleyNumberForMRD(HandoverVO mailHandover, Map<String, String> dummyTrolleyNumberMap)
			throws BusinessException {
		log.debug("MLDMessageProcessor" + " : " + "generateTrolleyNumber" + " Entering");
		String serialNumberForcarrier = null;
		MLDDetailVO mLDDetailVO = new MLDDetailVO();
		MLDMasterVO mLDMasterVO = new MLDMasterVO();
		mLDDetailVO.setCarrier(mailHandover.getCarrierCode());
		mLDDetailVO.setCarrierCodeOub(mailHandover.getCarrierCode());
		mLDDetailVO.setFlightNumberOub(mailHandover.getFlightNumber());
		mLDDetailVO.setFlightOperationDateOub(mailHandover.getFlightDate());
		mLDDetailVO.setCompanyCode(mailHandover.getCompanyCode());
		mLDDetailVO.setPouOub(mailHandover.getDestination());
		if (mailHandover.getFlightNumber() != null && mailHandover.getFlightNumber().length() > 4) {
			mLDDetailVO.setFlight(mailHandover.getFlightNumber().substring(0, 4));
		} else if (mailHandover.getFlightNumber() != null) {
			mLDDetailVO.setFlight(mailHandover.getFlightNumber());
		}
		mLDMasterVO.setSenderAirport(mailHandover.getOrigin());
		mLDMasterVO.setMldDetailVO(mLDDetailVO);
		if (mailHandover.getFlightDate() != null) {
			String day = mailHandover.getFlightDate().toString().substring(0, 2);
			mLDDetailVO.setFlightDay(day);
		}
		String dummyTrolleyNumberMapKey = null;
		String keyCondition = null;
		if (mailHandover.getFlightNumber() != null && !"0000".equals(mailHandover.getFlightNumber())
				&& mailHandover.getFlightDate() != null) {
			String keyFormat = findKeyFormat();
			keyCondition = getKeyConditionForMRD(mLDDetailVO, keyFormat);
			dummyTrolleyNumberMapKey = mailHandover.getCompanyCode() + keyCondition;
		} else {
			StringBuilder uldNumberForCarrier = new StringBuilder("MT");
			serialNumberForcarrier = uldNumberForCarrier.append(mailHandover.getHandOverCarrierCode())
					.append(mailHandover.getDestination()).append("001").toString();
		}
		String serialNumber = null;
		if (Objects.nonNull(serialNumberForcarrier)) {
			serialNumber = serialNumberForcarrier;
		} else if (dummyTrolleyNumberMap.get(dummyTrolleyNumberMapKey) != null) {
			serialNumber = dummyTrolleyNumberMap.get(dummyTrolleyNumberMapKey);
		} else {
			serialNumber = generateDummyTrolleyNumberForMRD(mLDMasterVO, keyCondition);
			dummyTrolleyNumberMap.put(dummyTrolleyNumberMapKey, serialNumber);
		}
		log.debug("MLDMessageProcessor" + " : " + "generateTrolleyNumber" + " Exiting");
		return serialNumber;
	}

	public String getKeyConditionForMRD(MLDDetailVO mLDDetailVO, String keyFormat) {
		log.debug("MLDMessageProcessor" + " : " + MailConstantsVO.KEYCONDITION + " Entering");
		String displayString = "";
		Map<String, MLDDetailVO> templateObject = new HashMap<>();
		templateObject.put("mail", mLDDetailVO);

		try {
			displayString ="MT"+ mLDDetailVO.getCarrier()+ mLDDetailVO.getFlight()+mLDDetailVO.getFlightOperationDateOub().toLocalDate().
				format(DateTimeFormatter.ofPattern("dd"));
		} finally {
		}
		log.debug("MLDMessageProcessor;" + " : " + MailConstantsVO.KEYCONDITION + " Exiting");
		return displayString;
	}

	public String generateDummyTrolleyNumberForMRD(MLDMasterVO mldMasterVO, String keyCondition) {
		log.debug("MailController" + " : " + "generateSerialNumber" + " Entering");
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		String containerNumber = mailController.findAlreadyAssignedTrolleyNumberForMLD(mldMasterVO);
		if (containerNumber != null) {
			return containerNumber;
		}
		KeyCondition keyConditn =  new KeyCondition();
		keyConditn.setKey("barrowId");
		keyConditn.setValue(keyCondition);
		Criterion criterion = new CriterionBuilder()
				.withSequence("MAL_TROLLY_NO_GEN")
				.withKeyCondition(keyConditn)
				.withPrefix(keyCondition).build();
		return keyUtils.getKey(criterion);
	}

	public Collection<ErrorVO> handleMessageForMRDPOD(MailMRDVO mailMRDMessageVO,
													  Collection<OperationalFlightVO> opFltVO, HandoverVO handover, String mailBag)
			throws MailOperationsBusinessException, ForceAcceptanceException {
		Collection<ErrorVO> allErrorVOs = new ArrayList<>();
		MailUploadVO mailUploadVO = null;
		if (mailMRDMessageVO.getHandoverErrors() != null && !mailMRDMessageVO.getHandoverErrors().isEmpty()) {
			allErrorVOs.add(mailMRDMessageVO.getHandoverErrors().iterator().next());
			return allErrorVOs;
		}
		if (mailMRDMessageVO.getHandoverErrors() == null || mailMRDMessageVO.getHandoverErrors().isEmpty()) {
			mailUploadVO = new MailUploadVO();
			if (mailMRDMessageVO.getHandoverErrors() == null
					|| mailMRDMessageVO.getHandoverErrors().isEmpty() && mailMRDMessageVO.getMailBags() != null
					&& mailMRDMessageVO.getMailBags().get("valid_mailbag") != null) {
				Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
				mailUploadVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
				constructMailUploadVOForMRD(opFltVO, handover, mailBag, mailUploadVO);
				mailBagVOs.add(mailUploadVO);
				try {
					saveMailUploadDetails(mailBagVOs, handover.getDestAirport());
					ErrorVO errorVO = new ErrorVO("Processed successfully");
					allErrorVOs.add(errorVO);
				} catch (MailMLDBusniessException exp) {
					log.debug("exp" + exp.getMessage());
					Collection<ErrorVO> errorVOS = exp.getErrors();
					Object[] errorData = new Object[2];
					errorData[0] = mailBag;
					errorData[1] = handover.getHandOverID();
					errorVOS.iterator().next().setErrorData(errorData);
					allErrorVOs.add(errorVOS.iterator().next());
				} catch (MailHHTBusniessException exp) {
					log.debug("exp" + exp.getMessage());
					Collection<ErrorVO> errorVOS = exp.getErrors();
					Object[] errorData = new Object[2];
					errorData[0] = mailBag;
					errorData[1] = handover.getHandOverID();
					errorVOS.iterator().next().setErrorData(errorData);
					allErrorVOs.add(errorVOS.iterator().next());
				}
			} else {
				if (mailMRDMessageVO.getHandoverErrors() != null && !mailMRDMessageVO.getHandoverErrors().isEmpty()) {
					allErrorVOs.add(mailMRDMessageVO.getHandoverErrors().iterator().next());
				}
				if (mailMRDMessageVO.getMailBags().get(MailConstantsVO.INVALID_MAILBAG) != null
						&& !mailMRDMessageVO.getMailBags().get(MailConstantsVO.INVALID_MAILBAG).isEmpty()) {
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidMailtag");
					Object[] errorData = new Object[2];
					errorData[0] = mailBag;
					errorData[1] = handover.getHandOverID();
					errorVO.setErrorData(errorData);
					allErrorVOs.add(errorVO);
				}
			}
		} else {
			allErrorVOs.addAll(mailMRDMessageVO.getHandoverErrors());
		}
		return allErrorVOs;
	}

	public Collection<ErrorVO> handleMessageForMRDPOC(MailMRDVO mailMRDMessageVO, HandoverVO handover, String mailBag)
			throws MailOperationsBusinessException, ForceAcceptanceException {
		Collection<ErrorVO> allErrorVOs = new ArrayList<>();
		Collection<OperationalFlightVO> opFltVO = null;
		MailUploadVO mailUploadVO = null;
		constructAndValidateMailBagsPOC(mailMRDMessageVO, handover, mailBag);
		if (mailMRDMessageVO.getHandoverErrors() != null && !mailMRDMessageVO.getHandoverErrors().isEmpty()) {
			allErrorVOs.add(mailMRDMessageVO.getHandoverErrors().iterator().next());
			return allErrorVOs;
		}
		if (handover.getFlightNumber() != null && handover.getFlightDate() != null
				&& !("0000".equals(handover.getFlightNumber()))) {
			try {
				opFltVO = Mailbag.findOperationalFlightForMRD(handover);
			} finally {
			}
		}
		mailUploadVO = new MailUploadVO();
		if (mailMRDMessageVO.getHandoverErrors() == null
				|| mailMRDMessageVO.getHandoverErrors().isEmpty() && mailMRDMessageVO.getMailBags() != null
				&& mailMRDMessageVO.getMailBags().get("valid_mailbag") != null) {
			Collection<MailUploadVO> mailBagVOs = new ArrayList<>();
			mailUploadVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
			constructMailUploadVOForMRDPOC(opFltVO, handover, mailBag, mailUploadVO);
			mailBagVOs.add(mailUploadVO);
			try {
				saveMailUploadDetails(mailBagVOs, handover.getDestAirport());
				ErrorVO errorVO = new ErrorVO("Processed successfully");
				allErrorVOs.add(errorVO);
			} catch (MailMLDBusniessException | MailHHTBusniessException exp) {
				log.info("Exception :", exp);
				Collection<ErrorVO> errorVOS = exp.getErrors();
				Object[] errorData = new Object[2];
				errorData[0] = mailBag;
				errorData[1] = handover.getHandOverID();
				errorVOS.iterator().next().setErrorData(errorData);
				allErrorVOs.add(errorVOS.iterator().next());
			}
		} else {
			if (mailMRDMessageVO.getHandoverErrors() != null && !mailMRDMessageVO.getHandoverErrors().isEmpty()) {
				allErrorVOs.add(mailMRDMessageVO.getHandoverErrors().iterator().next());
			}
			if (mailMRDMessageVO.getMailBags().get(MailConstantsVO.INVALID_MAILBAG) != null
					&& !mailMRDMessageVO.getMailBags().get(MailConstantsVO.INVALID_MAILBAG).isEmpty()) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidMailtag");
				Object[] errorData = new Object[2];
				errorData[0] = mailBag;
				errorData[1] = handover.getHandOverID();
				errorVO.setErrorData(errorData);
				allErrorVOs.add(errorVO);
			}
		}
		return allErrorVOs;
	}

	private FlightValidationVO constructFlightValidationVOForOnloadMessage(String key) {
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		Collection<FlightValidationVO> flightDetailsVOs = null;
		String[] det = key.split(SEPARATOR_UNDERSCORE);
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(det[0], det[1]);
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		flightFilterVO.setCompanyCode(det[0]);
		flightFilterVO.setFlightCarrierId(airlineValidationVO != null ? airlineValidationVO.getAirlineIdentifier() : 0);
		flightFilterVO.setFlightNumber(det[2]);
		ZonedDateTime flightDate = localDateUtil.getLocalDate(null, false);
		flightDate = LocalDate.withDate(flightDate, det[3]);
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(flightDate));
		flightFilterVO.setAirportCode(det[4]);
		flightFilterVO.setStation(det[4]);
		flightDetailsVOs = mailController.validateFlight(flightFilterVO);
		if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
			for (FlightValidationVO fltValVO : flightDetailsVOs) {
				if (fltValVO.getLegOrigin().equals(det[4])) {
					flightValidationVO.setCompanyCode(fltValVO.getCompanyCode());
					flightValidationVO.setFlightCarrierId(fltValVO.getFlightCarrierId());
					flightValidationVO.setFlightNumber(fltValVO.getFlightNumber());
					flightValidationVO.setFlightSequenceNumber(fltValVO.getFlightSequenceNumber());
					flightValidationVO.setAirportCode(det[4]);
				}
			}
		}
		return flightValidationVO;
	}

	/**
	 * Method		:	MailUploadController.validateAndoffloadContainerfromOnloadMessage Added by 	:	A-8061 on 16-Apr-2021 Used for 	: Parameters	:	@param containerVOs Parameters	:	@param containers Parameters	:	@param containerDetailsVOs Parameters	:	@throws SystemException  Return type	: 	void
	 */
	private void validateAndoffloadContainerfromOnloadMessage(Collection<ContainerVO> containerVOs,
															  Set<String> containers, Collection<ContainerDetailsVO> containerDetailsVOs) {
		ContainerDetailsVO containerDetailsVO = null;
		for (ContainerVO containerVO : containerVOs) {
			if (!containers.contains(containerVO.getContainerNumber())) {
				MailUploadVO mailUploadVO = constructMailUploadVOForOnloadMessage(null, null, containerVO,
						MailConstantsVO.MAIL_STATUS_OFFLOADED);
				try {
					saveMailUploadDetailsForAndroid(mailUploadVO, mailUploadVO.getScannedPort());
				} catch (MailMLDBusniessException | MailHHTBusniessException | MailOperationsBusinessException
						| PersistenceException exception) {
					log.info("Exception :", exception);
				}
			} else {
				containerDetailsVO = MailOperationsVOConverter.convertToContainerDetails(containerVO);
				containerDetailsVO.setPol(containerVO.getAssignedPort());
				containerDetailsVOs.add(containerDetailsVO);
			}
		}
	}

	/**
	 * Method		:	MailUploadController.validateAndoffloadMailsfromOnloadMessage Added by 	:	A-8061 on 31-Mar-2021 Used for 	: Parameters	:	@param mailBags Parameters	:	@param containerDetailsVO Parameters	:	@throws SystemException  Return type	: 	void
	 * @throws SystemException
	 */
	private void validateAndoffloadMailsfromOnloadMessage(Set<String> mailBags, ContainerDetailsVO containerDetailsVO) {
		if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
			for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
				if (!mailBags.contains(mailbagVO.getMailbagId())) {
					MailUploadVO mailUploadVO = constructMailUploadVOForOnloadMessage(mailbagVO, containerDetailsVO,
							null, MailConstantsVO.MAIL_STATUS_OFFLOADED);
					try {
						saveMailUploadDetailsForAndroid(mailUploadVO, mailUploadVO.getScannedPort());
					} catch (MailMLDBusniessException | MailHHTBusniessException | MailOperationsBusinessException
							| PersistenceException exception) {
						log.info("Exception :", exception);
					}
				}
			}
		}
	}

	/**
	 * Method		:	MailUploadController.constructMailUploadVOForOnloadMessage Added by 	:	A-8061 on 31-Mar-2021 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@param containerDetailsVO Parameters	:	@param scanType Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	MailUploadVO
	 */
	private MailUploadVO constructMailUploadVOForOnloadMessage(MailbagVO mailbagVO,
															   ContainerDetailsVO containerDetailsVO, ContainerVO containerVO, String scanType) {
		MailUploadVO mailUploadVO = new MailUploadVO();
		LoginProfile logonAttributes = null;
		logonAttributes = contextUtil.callerLoginProfile();
		mailUploadVO.setScanType(scanType);
		mailUploadVO.setCompanyCode(logonAttributes.getCompanyCode());
		if (mailbagVO != null) {
			mailUploadVO.setMailTag(mailbagVO.getMailbagId());
		} else if (containerDetailsVO != null) {
			mailUploadVO.setMailTag(containerDetailsVO.getContainerNumber());
		} else if (containerVO != null) {
			mailUploadVO.setMailTag(containerVO.getContainerNumber());
		}
		ZonedDateTime scanDate = localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true);
		mailUploadVO.setDateTime(scanDate.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
		mailUploadVO.setScanUser(logonAttributes.getUserId());
		mailUploadVO.setOverrideValidation(MailConstantsVO.FLAG_YES);
		mailUploadVO.setMailSource(MailConstantsVO.ONLOAD_MESSAGE);
		ZonedDateTime deviceDate = localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true);
		mailUploadVO.setDeviceDateAndTime(deviceDate);
		if (containerDetailsVO != null) {
			mailUploadVO.setScannedPort(containerDetailsVO.getPol());
		} else if (containerVO != null) {
			mailUploadVO.setScannedPort(containerVO.getAssignedPort());
		}
		mailUploadVO.setOffloadReason(findSystemParameterValue(DEFAULT_OFFLOADCODE));
		if (mailbagVO != null) {
			mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(6, 12));
			mailUploadVO.setCategory(mailUploadVO.getMailTag().substring(12, 13));
			mailUploadVO.setSubClass(mailUploadVO.getMailTag().substring(13, 15));
			mailUploadVO.setYear(Integer.parseInt(mailUploadVO.getMailTag().substring(15, 16)));
		}
		return mailUploadVO;
	}
	/**
	 * @param opFltVO
	 * @param handover
	 * @param mailBag
	 * @param mailUploadVO
	 */
	public void constructMailUploadVOForMRDPOC(Collection<OperationalFlightVO> opFltVO, HandoverVO handover,
											   String mailBag, MailUploadVO mailUploadVO) {
		mailUploadVO.setMailTag(mailBag.trim());
		ContainerAssignmentVO containerVO = null;
		boolean mailbagPresent = true;
		Collection<OperationalFlightVO> opFltVOS = null;
		HandoverVO mailHandover = null;
		mailHandover = constructMailHandover(handover, mailBag, mailUploadVO);
		try {
			long mailSeq = findMailSequenceNumber(mailBag, mailUploadVO.getCompanyCode());
			if (mailSeq > 0) {
				constructMailHandoverForExisting(mailHandover, mailBag, mailUploadVO);
				opFltVOS = Mailbag.findOperationalFlightForMRD(mailHandover);
			}
		} finally {
		}
		if ((Objects.isNull(handover.getFlightNumber())
				|| "0000".equals(handover.getFlightNumber()) && opFltVO != null)) {
			if (!mailbagPresent) {
				mailUploadVO.setCarrierCode(opFltVO.iterator().next().getCarrierCode());
				mailUploadVO.setFlightNumber(opFltVO.iterator().next().getFlightNumber());
				if (opFltVO.iterator().next().getArrToDate() != null) {
					mailUploadVO.setFlightDate(opFltVO.iterator().next().getArrToDate());
				} else {
					mailUploadVO.setFlightDate(opFltVO.iterator().next().getFlightDate());
				}
				mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailUploadVO.setContainerNumber("BULK-" + opFltVO.iterator().next().getPou());
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVO.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVO.iterator().next().getPou());
				return;
			} else {
				if (opFltVOS != null && !opFltVOS.isEmpty()) {
					handleExistingMailbagsPOC(mailUploadVO, opFltVOS, mailbagPresent, mailBag, handover);
					return;
				}
			}
		}
		if (opFltVO != null && !opFltVO.isEmpty() && opFltVO.iterator().next().getCarrierCode() != null
				&& opFltVO.iterator().next().getFlightNumber() != null
				&& opFltVO.iterator().next().getFlightDate() != null) {
			mailUploadVO.setCarrierCode(opFltVO.iterator().next().getCarrierCode());
			mailUploadVO.setFlightNumber(opFltVO.iterator().next().getFlightNumber());
			if (opFltVO.iterator().next().getArrToDate() != null) {
				mailUploadVO.setFlightDate(opFltVO.iterator().next().getArrToDate());
			} else {
				mailUploadVO.setFlightDate(opFltVO.iterator().next().getFlightDate());
			}
			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			try {
				containerVO = Mailbag.findContainerDetailsForMRD(opFltVO.iterator().next(), mailBag);
				if (containerVO != null) {
					containerVO = Container.findLatestContainerAssignment(mailUploadVO.getCompanyCode(),
							containerVO.getContainerNumber());
				}
			} finally {
			}
			if (containerVO != null && mailbagPresent && containerVO.getFlightNumber() != null
					&& containerVO.getCarrierCode() != null && containerVO.getFlightDate() != null) {
				if (containerVO.getFlightNumber().equals(handover.getFlightNumber())
						&& containerVO.getCarrierCode().equals(handover.getCarrierCode())) {
					mailUploadVO.setFlightNumber(containerVO.getFlightNumber());
					mailUploadVO.setCarrierCode(containerVO.getCarrierCode());
					mailUploadVO.setFlightDate(containerVO.getFlightDate());
				}
				mailUploadVO.setContainerNumber(containerVO.getContainerNumber());
				mailUploadVO.setContainerType(containerVO.getContainerType());
				mailUploadVO.setContainerPol(containerVO.getAirportCode());
				mailUploadVO.setContainerPOU(containerVO.getPou());
			} else {
				setDetailsOnHandoverType(handover, mailUploadVO, opFltVOS);
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVO.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVO.iterator().next().getPou());
			}
		} else {
			mailUploadVO.setFlightSequenceNumber(-1);
			mailUploadVO.setCarrierCode(handover.getHandOverCarrierCode());
			String airport = null;
			String mailbag = mailUploadVO.getMailTag();
			OfficeOfExchangeFilterModel filterModel = new OfficeOfExchangeFilterModel();
			Collection<String> officeOfExchangeDestination = new ArrayList<>();
			filterModel.setCompanyCode(mailHandover.getCompanyCode());
			officeOfExchangeDestination.add(mailbag.substring(6, 12));
			filterModel.setOfficeOfExchanges(officeOfExchangeDestination);
			Map<String, String> airPort = mailMasterApi.findAirportForOfficeOfExchange(filterModel);
			airport=airPort.get(mailbag.substring(6, 12));
			mailUploadVO.setDestination(airport);
			mailUploadVO.setContainerType("B");
			handover.setDestination(airport);
			setDetailsOnHandoverType(handover, mailUploadVO, opFltVOS);
		}
	}

	public void handleExistingMailbags(MailUploadVO mailUploadVO, Collection<OperationalFlightVO> opFltVOS,
									   boolean mailbagPresent, String mailBag, HandoverVO handover) {
		ContainerAssignmentVO containerVO = null;
		mailUploadVO.setCarrierCode(opFltVOS.iterator().next().getCarrierCode());
		mailUploadVO.setFlightNumber(opFltVOS.iterator().next().getFlightNumber());
		if (opFltVOS.iterator().next().getArrToDate() != null) {
			mailUploadVO.setFlightDate(opFltVOS.iterator().next().getArrToDate());
		} else {
			mailUploadVO.setFlightDate(opFltVOS.iterator().next().getFlightDate());
		}
		mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
		mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
		try {
			containerVO = Mailbag.findContainerDetailsForMRD(opFltVOS.iterator().next(), mailBag);
			if (containerVO != null) {
				containerVO = Container.findLatestContainerAssignment(mailUploadVO.getCompanyCode(),
						containerVO.getContainerNumber());
			}
		} catch(Exception e){
			log.debug(e.getMessage());
		}
		if (containerVO != null && mailbagPresent && (handover.getFlightNumber() == null
				|| handover.getFlightNumber().trim().length() == 0 || "0000".equals(handover.getFlightNumber()))) {
			if (containerVO.getFlightNumber() != null && containerVO.getCarrierCode() != null
					&& containerVO.getFlightDate() != null) {
				mailUploadVO.setContainerNumber(containerVO.getContainerNumber());
				mailUploadVO.setContainerType(containerVO.getContainerType());
				mailUploadVO.setContainerPol(containerVO.getAirportCode());
				mailUploadVO.setContainerPOU(containerVO.getPou());
			} else {
				mailUploadVO.setContainerNumber("BULK-" + opFltVOS.iterator().next().getPou());
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVOS.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVOS.iterator().next().getPou());
			}
		} else {
			mailUploadVO.setContainerNumber("BULK-" + opFltVOS.iterator().next().getPou());
			mailUploadVO.setContainerType("B");
			mailUploadVO.setContainerPol(opFltVOS.iterator().next().getPol());
			mailUploadVO.setContainerPOU(opFltVOS.iterator().next().getPou());
		}
	}

	public void handleExistingMailbagsPOC(MailUploadVO mailUploadVO, Collection<OperationalFlightVO> opFltVOS,
										  boolean mailbagPresent, String mailBag, HandoverVO handover) {
		ContainerAssignmentVO containerVO = null;
		String serialNumber = "";
		Map<String, String> dummyTrolleyNumberMap = new HashMap<>();
		mailUploadVO.setCarrierCode(opFltVOS.iterator().next().getCarrierCode());
		mailUploadVO.setFlightNumber(opFltVOS.iterator().next().getFlightNumber());
		if (opFltVOS.iterator().next().getArrToDate() != null) {
			mailUploadVO.setFlightDate(opFltVOS.iterator().next().getArrToDate());
		} else {
			mailUploadVO.setFlightDate(opFltVOS.iterator().next().getFlightDate());
		}
		mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		try {
			containerVO = Mailbag.findContainerDetailsForMRD(opFltVOS.iterator().next(), mailBag);
			if (containerVO != null) {
				containerVO = Container.findLatestContainerAssignment(mailUploadVO.getCompanyCode(),
						containerVO.getContainerNumber());
			}
		} catch(Exception e){
			log.debug(e.getMessage());
		}
		if (containerVO != null && mailbagPresent && (handover.getFlightNumber() == null
				|| handover.getFlightNumber().trim().length() == 0 || "0000".equals(handover.getFlightNumber()))) {
			if (containerVO.getFlightNumber() != null && containerVO.getCarrierCode() != null
					&& containerVO.getFlightDate() != null) {
				mailUploadVO.setContainerNumber(containerVO.getContainerNumber());
				mailUploadVO.setContainerType(containerVO.getContainerType());
				mailUploadVO.setContainerPol(containerVO.getAirportCode());
				mailUploadVO.setContainerPOU(containerVO.getPou());
			} else {
				mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				try {
					serialNumber = generateTrolleyNumberForMRD(handover, dummyTrolleyNumberMap);
					mailUploadVO.setContainerNumber(serialNumber);
				} catch (BusinessException e) {
					log.warn(MailConstantsVO.TROLLY_EXCEPTION + e);
				}
				mailUploadVO.setContainerType("B");
				mailUploadVO.setContainerPol(opFltVOS.iterator().next().getPol());
				mailUploadVO.setContainerPOU(opFltVOS.iterator().next().getPou());
			}
		} else {
			try {
				serialNumber = generateTrolleyNumberForMRD(handover, dummyTrolleyNumberMap);
				mailUploadVO.setContainerNumber(serialNumber);
			} catch (BusinessException e) {
				log.warn(MailConstantsVO.TROLLY_EXCEPTION + e);
			}
			mailUploadVO.setContainerType("B");
			mailUploadVO.setContainerPol(opFltVOS.iterator().next().getPol());
			mailUploadVO.setContainerPOU(opFltVOS.iterator().next().getPou());
		}
	}

	public void setDetailsOnHandoverType(HandoverVO handover, MailUploadVO mailUploadVO,
										 Collection<OperationalFlightVO> opFltVOS) {
		mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		Map<String, String> dummyTrolleyNumberMap = new HashMap<>();
		String serialNumber = "";
		try {
			serialNumber = generateTrolleyNumberForMRD(handover, dummyTrolleyNumberMap);
			if (Objects.nonNull(serialNumber)) {
				mailUploadVO.setContainerNumber(serialNumber);
			}
		} catch (BusinessException e) {
			log.warn(MailConstantsVO.TROLLY_EXCEPTION + e);
		}
	}

	public Collection<ErrorVO> handleInvalidHandover(MailMRDVO mailMRDMessageVO) {
		Collection<ErrorVO> allErrorVOs = new ArrayList<>();
		if (mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS) != null
				&& !mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS).isEmpty()) {
			for (HandoverVO handoverVO : mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS)) {
				OfficeOfExchangeVO ooe = validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
						"POC".equals(handoverVO.getHandOverType()) ? handoverVO.getOrgExgOffice()
								: handoverVO.getDstExgOffice());
				boolean isValidAirport = validateAirport(handoverVO.getDestAirport(),
						mailMRDMessageVO.getCompanyCode());
				if (Objects.isNull(ooe)) {
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandoverofficeofexchange");
					Object[] errorData = new Object[1];
					errorData[0] = handoverVO.getHandOverID();
					errorVO.setErrorData(errorData);
					allErrorVOs.add(errorVO);
				} else if (!isValidAirport) {
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandoverairport");
					Object[] errorData = new Object[1];
					errorData[0] = handoverVO.getHandOverID();
					errorVO.setErrorData(errorData);
					allErrorVOs.add(errorVO);
				} else if (handoverVO.getHandOverdate_time() != null) {
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandoverdatetime");
					Object[] errorData = new Object[1];
					errorData[0] = handoverVO.getHandOverID();
					errorVO.setErrorData(errorData);
					allErrorVOs.add(errorVO);
				} else {
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandover");
					Object[] errorData = new Object[1];
					errorData[0] = handoverVO.getHandOverID();
					errorVO.setErrorData(errorData);
					allErrorVOs.add(errorVO);
				}
			}
		}
		return allErrorVOs;
	}

	public void constructAndValidateMailBagsPOC(MailMRDVO mailMRDMessageVO, HandoverVO handoverVO, String mailBag) {
		Collection<ErrorVO> errorVOs = null;
		boolean isValidFlight = true;
		boolean isValidFlightCarrier = true;
		boolean isValidFlightSegment = false;
		boolean isValidFlightOrigin = true;
		if (handoverVO.getFlightNumber() != null && !"0000".equals(handoverVO.getFlightNumber())
				&& handoverVO.getOrigin() != null) {
			isValidFlightOrigin = validateAirport(handoverVO.getOrigin(), mailMRDMessageVO.getCompanyCode());
		}
		handoverVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
		if (handoverVO.getFlightNumber() != null && handoverVO.getFlightNumber().trim().length() > 0
				&& !"0000".equals(handoverVO.getFlightNumber()) && handoverVO.getFlightDate() != null
				&& handoverVO.getCarrierCode() != null && handoverVO.getCarrierCode().trim().length() > 0
				&& isValidFlightOrigin) {
			FlightFilterVO opFlightVO = new FlightFilterVO();
			opFlightVO.setCompanyCode(mailMRDMessageVO.getCompanyCode());
			opFlightVO.setCarrierCode(handoverVO.getCarrierCode());
			opFlightVO.setFlightNumber(handoverVO.getFlightNumber());
			opFlightVO.setDirection("O");
			opFlightVO.setDestination(handoverVO.getDestination());
			opFlightVO.setOrigin(handoverVO.getOrigin());
			opFlightVO.setFlightDate(LocalDateMapper.toLocalDate(handoverVO.getFlightDate()));
			isValidFlightCarrier = validateCarrierCode(handoverVO.getCarrierCode(), mailMRDMessageVO.getCompanyCode());
			Collection<FlightValidationVO> fltValVOs = validateFlight(opFlightVO);
			isValidFlight = (fltValVOs != null && !fltValVOs.isEmpty()) ? true : false;
			if (fltValVOs != null && !fltValVOs.isEmpty()) {
				for (FlightValidationVO fltValVO : fltValVOs) {
					if (fltValVO.getCompanyCode().equals(mailMRDMessageVO.getCompanyCode())
							&& fltValVO.getCarrierCode().equals(handoverVO.getCarrierCode())
							&& fltValVO.getFlightNumber().equals(handoverVO.getFlightNumber())
							&& handoverVO.getOrigin() != null && handoverVO.getDestination() != null) {
						MailUploadVO mailUploadVO = new MailUploadVO();
						mailUploadVO.setCompanyCode(fltValVO.getCompanyCode());
						mailUploadVO.setScannedPort(fltValVO.getLegOrigin());
						mailUploadVO.setCarrierCode(fltValVO.getCarrierCode());
						mailUploadVO.setFlightNumber(fltValVO.getFlightNumber());
						mailUploadVO.setFlightDate(LocalDateMapper.toZonedDateTime(fltValVO.getFlightDate()));
						mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
						mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
						mailUploadVO.setMailTag(mailBag);
						mailUploadVO.setContainerPol(fltValVO.getLegOrigin());
						mailUploadVO.setContainerPOU(fltValVO.getLegDestination());
						Collection<MailUploadVO> mailUplVOs = new ArrayList<>();
						Collection<OperationalFlightVO> opfltVO = null;
						mailUplVOs.add(mailUploadVO);
						try {
							opfltVO = Mailbag.findOperationalFlightForMRD(handoverVO);
							if (opfltVO != null && !opfltVO.isEmpty()
									&& opfltVO.iterator().next().getCarrierCode() != null
									&& opfltVO.iterator().next().getFlightNumber() != null
									&& opfltVO.iterator().next().getFlightDate() != null) {
								isValidFlightSegment = true;
							} else {
								isValidFlightSegment = false;
							}
							break;
						} catch (SystemException e) {
							log.debug(e.getMessage());
						}
					} else {
						isValidFlightSegment = false;
					}
				}
			}
		} else {
			isValidFlight = true;
			isValidFlightCarrier = true;
			isValidFlightSegment = true;
		}
		errorVOs = validateDetails(handoverVO, mailMRDMessageVO, isValidFlightCarrier, isValidFlight,
				isValidFlightSegment, mailBag, isValidFlightOrigin);
		mailMRDMessageVO.setHandoverErrors(errorVOs);
	}

	public Collection<ErrorVO> validateDetails(HandoverVO handoverVO, MailMRDVO mailMRDMessageVO,
											   boolean isValidFlightCarrier, boolean isValidFlight, boolean isValidFlightSegment, String mailBag,
											   boolean isValidFlighOrigin) {
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		OfficeOfExchangeVO ooe = "POD".equals(handoverVO.getHandOverType())
				? validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(), handoverVO.getDstExgOffice())
				: validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(), handoverVO.getOrgExgOffice());
		boolean isValidDoe = ooe != null ? true : false;
		boolean isValidHandoverCarrier = true;
		if (handoverVO.getAttributeCarrier() != null) {
			isValidHandoverCarrier = validateCarrierCode(handoverVO.getAttributeCarrier(),
					mailMRDMessageVO.getCompanyCode());
		}
		if (handoverVO.getHandOverCarrierCode() != null) {
			isValidHandoverCarrier = validateCarrierCode(handoverVO.getHandOverCarrierCode(),
					mailMRDMessageVO.getCompanyCode());
		}
		if (isValidFlightCarrier && isValidDoe && isValidFlight && isValidFlightSegment && isValidHandoverCarrier
				&& isValidFlighOrigin) {
			validateMailData(mailMRDMessageVO, mailBag);
			if (mailMRDMessageVO.getMailBags().get("valid_mailbag") != null) {
				Object[] obj = new Object[2];
				obj[0] = mailBag;
				obj[1] = handoverVO.getHandOverID();
				errorVOs = handleInvalidMailbagErrors(mailBag, handoverVO, obj);
			} else {
				ErrorVO errorVO = null;
				Object[] obj = new Object[2];
				obj[0] = handoverVO.getHandOverID();
				obj[1] = mailBag;
				OfficeOfExchangeVO mailOOE = validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
						mailBag.substring(0, 6));
				boolean isValidMailOoe = mailOOE != null ? true : false;
				OfficeOfExchangeVO mailDOE = validateOfficeOfExchange(mailMRDMessageVO.getCompanyCode(),
						mailBag.substring(6, 12));
				boolean isValidMailDoe = mailDOE != null ? true : false;
				if (mailBag.trim().length() != 29) {
					errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidMailtagError");
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				} else if (!isValidMailOoe) {
					errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidmailoriginexchangeoffice");
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				} else if (!isValidMailDoe) {
					errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidmaildestexchangeoffice");
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				} else {
					errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidsubclass");
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
				}
			}
		} else {
			errorVOs = handleInvalidErrors(isValidDoe, isValidFlightCarrier, isValidHandoverCarrier, isValidFlight,
					isValidFlightSegment, handoverVO, isValidFlighOrigin);
		}
		return errorVOs;
	}

	public Collection<ErrorVO> handleInvalidMailbagErrors(String mailBag, HandoverVO handoverVO, Object[] obj) {
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		ErrorVO errorVO = null;
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		oneTimes = findOneTimeDescription(handoverVO.getCompanyCode());
		boolean isRICodeValid = false;
		boolean isMailCategoryValid = false;
		boolean isHNIValid = false;
		if (Objects.nonNull(oneTimes)) {
			Collection<OneTimeVO> riOneTimesVos = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			for (OneTimeVO RIeOneTimeVO : riOneTimesVos) {
				if (RIeOneTimeVO.getFieldValue().equals(mailBag.substring(24, 25))) {
					isRICodeValid = true;
				}
			}
			Collection<OneTimeVO> mailCategoryVos = oneTimes.get("mailtracking.defaults.mailcategory");
			for (OneTimeVO mailCategoryVO : mailCategoryVos) {
				if (mailCategoryVO.getFieldValue().equals(mailBag.substring(12, 13))) {
					isMailCategoryValid = true;
				}
			}
			Collection<OneTimeVO> hniVos = oneTimes.get("mailtracking.defaults.highestnumbermail");
			for (OneTimeVO HNIVO : hniVos) {
				if (HNIVO.getFieldValue().equals(mailBag.substring(23, 24))) {
					isHNIValid = true;
				}
			}
		}
		if (!isRICodeValid) {
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidri");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		} else if (!isMailCategoryValid) {
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidcategory");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		} else if (!isHNIValid) {
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhni");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		return errorVOs;
	}

	public Collection<ErrorVO> handleInvalidErrors(boolean isValidDoe, boolean isValidFlightCarrier,
												   boolean isValidHandoverCarrier, boolean isValidFlight, boolean isValidFlightSegment, HandoverVO handoverVO,
												   boolean isValidFlighOrigin) {
		boolean isErrorAdded = false;
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		Object[] obj = new Object[2];
		obj[0] = handoverVO.getHandOverID();
		obj[1] = handoverVO.getHandOverID();
		ErrorVO errorVO = null;
		boolean isValidFlightDestination = validateAirport(handoverVO.getDestination(), handoverVO.getCompanyCode());
		if (!isValidDoe) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandoverofficeofexchange");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidFlightCarrier && !isErrorAdded) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidcarriercode");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidHandoverCarrier && !isErrorAdded) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidhandovercarier");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidFlight && !isErrorAdded) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidflight");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidFlighOrigin && !isErrorAdded) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidPOL");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidFlightDestination && !isErrorAdded) {
			isErrorAdded = true;
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidPOU");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		if (!isValidFlightSegment && !isErrorAdded) {
			errorVO = new ErrorVO("mailtracking.defaults.mrd.invalidflightPOC");
			errorVO.setErrorData(obj);
			errorVOs.add(errorVO);
		}
		return errorVOs;
	}
	/**
	 * @author A-8353
	 * @param filterVO
	 * @param manifestFilterVOs
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																	Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		return constructDAO().findExportFlightOperationsDetails(filterVO, manifestFilterVOs);
	}
	/**
	 * @author A-8353
	 * @param mailBagVO
	 * @param scanningPort
	 * @throws SystemException
	 */
	public void performErrorStampingForFoundMailWebServices(MailUploadVO mailBagVO, String scanningPort) {
		try {
			stampErrorForFoundMailWebServices(mailBagVO, scanningPort);
		} catch (MailMLDBusniessException | MailHHTBusniessException | MailOperationsBusinessException e) {
			if (!e.getErrors().isEmpty()) {
				for (ErrorVO errVo : e.getErrors()) {
					//TODO: Neo to verify below code wrt classic
					if (!(validateAndCorrectErrorFormat(errVo.getErrorCode()).isEmpty())) {
						throw new SystemException(validateAndCorrectErrorFormat(errVo.getErrorCode()));
					}
				}
			}
			throw new SystemException(UNDEFINED_ERROR_WEBSERVICES);
		}
	}

	/**
	 * @author A-8353
	 * @param mailBagVO
	 * @param scanningPort
	 * @throws MailMLDBusniessException
	 * @throws MailHHTBusniessException
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 */
	private void stampErrorForFoundMailWebServices(MailUploadVO mailBagVO, String scanningPort)
			throws MailMLDBusniessException, MailHHTBusniessException, MailOperationsBusinessException {
		try {
			saveMailUploadDetailsForAndroid(mailBagVO, scanningPort);
		} catch (PersistenceException e) {
			throw new SystemException(UNDEFINED_ERROR_WEBSERVICES);
		}
	}

	/**
	 * @author A-8353
	 * @param scanningPort
	 * @param mailscanVos
	 * @param scannedMailDetailsVO
	 * @return
	 * @throws MailMLDBusniessException
	 * @throws MailHHTBusniessException
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @throws RemoteException
	 * @throws PersistenceException
	 */
	private ScannedMailDetailsVO saveMailUploadDetailForGHA(String scanningPort, ArrayList<MailUploadVO> mailscanVos,
															ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException,
			MailOperationsBusinessException, PersistenceException {
		for (MailUploadVO mailUploadVO : mailscanVos) {
			if (MailConstantsVO.MAL_FUL_IND.equalsIgnoreCase(mailUploadVO.getScanType())) {
				scannedMailDetailsVO = saveMailUploadDetailsForULDFULIndicator(mailUploadVO, scanningPort);
			} else if ("RMK".equalsIgnoreCase(mailUploadVO.getScanType())) {
				scannedMailDetailsVO = saveRemarksForMailTag(mailUploadVO);
			} else {
				scannedMailDetailsVO = saveMailUploadDetailsForAndroid(mailUploadVO, scanningPort);
				if (scannedMailDetailsVO.isArrivalException()) {
					mailUploadVO.setArrivalException(true);
					if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
						mailUploadVO.setProcessPointBeforeArrival(MailConstantsVO.MAIL_STATUS_DELIVERED);
						mailUploadVO.setDeliverd(false);
						mailUploadVO.setDeliverFlag("N");
					} else {
						mailUploadVO.setProcessPointBeforeArrival(scannedMailDetailsVO.getProcessPoint());
					}
					mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
					mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
				}
			}
		}
		return scannedMailDetailsVO;
	}
	public ScannedMailDetailsVO saveMailUploadDetailsForULDFULIndicator(MailUploadVO mailUploadVO, String scanningPort)
			throws MailMLDBusniessException, MailHHTBusniessException {
		try {
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			this.cntAsgmntVO = null;
			mailUploadVO.setScannedPort(scanningPort);
			if (MailConstantsVO.MAL_FUL_IND.equals(mailUploadVO.getScanType())) {
				ContainerAssignmentVO containerAssignmentVOForFUL = (ContextUtil.getInstance()
						.getBean(MailController.class))
						.findLatestContainerAssignment(mailUploadVO.getContainerNumber());
				checkForClosedFlight(scannedMailDetailsVO, containerAssignmentVOForFUL);
				if (containerAssignmentVOForFUL == null) {
					constructAndRaiseException(
							MailMLDBusniessException.MAILTRACKING_DEFAULTS_ERROR_CONTAINERIDNOTAVAILABLE,
							MailHHTBusniessException.MAILTRACKING_DEFAULTS_ERROR_CONTAINERIDNOTAVAILABLE,
							scannedMailDetailsVO);
				} else {
					ContainerVO containerVO = new ContainerVO();
					containerVO.setContainerNumber(containerAssignmentVOForFUL.getContainerNumber());
					containerVO.setAssignedPort(containerAssignmentVOForFUL.getAirportCode());
					containerVO.setCarrierId(containerAssignmentVOForFUL.getCarrierId());
					containerVO.setFlightNumber(containerAssignmentVOForFUL.getFlightNumber());
					containerVO.setFlightSequenceNumber(containerAssignmentVOForFUL.getFlightSequenceNumber());
					containerVO.setLegSerialNumber(containerAssignmentVOForFUL.getLegSerialNumber());
					containerVO.setCompanyCode(containerAssignmentVOForFUL.getCompanyCode());
					containerVO.setUldFulIndFlag(mailUploadVO.getUldFullIndicator());
					mailController.markUnmarkUldIndicator(containerVO);
				}
			}
			return scannedMailDetailsVO;
		} catch (ForceAcceptanceException exception) {
			log.info("Exception :", exception);
			Collection<ErrorVO> errors = new ArrayList<>();
			if (exception.getErrors() != null) {
				errors = exception.getErrors();
			}
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			if (errors != null && !errors.isEmpty()) {
				for(ErrorVO vo : errors) {
					scannedMailDetailsVO.setErrorCode(vo.getErrorCode());
					scannedMailDetailsVO.setErrorDescription(vo.getDefaultMessage());
					scannedMailDetailsVO.setForceAccepted(true);
				}
			}
			return scannedMailDetailsVO;
		}
	}

	public void checkForClosedFlight(ScannedMailDetailsVO scannedMailDetailsVO,
									 ContainerAssignmentVO containerAssignmentVOForFUL)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		MailAcceptance mailAcceptance = new MailAcceptance();
		if (containerAssignmentVOForFUL != null) {
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailAcceptanceVO.setPol(containerAssignmentVOForFUL.getAirportCode());
			mailAcceptanceVO.setCompanyCode(containerAssignmentVOForFUL.getCompanyCode());
			mailAcceptanceVO.setFlightNumber(containerAssignmentVOForFUL.getFlightNumber());
			mailAcceptanceVO.setFlightSequenceNumber(containerAssignmentVOForFUL.getFlightSequenceNumber());
			mailAcceptanceVO.setLegSerialNumber(containerAssignmentVOForFUL.getLegSerialNumber());
			mailAcceptanceVO.setCarrierId(containerAssignmentVOForFUL.getCarrierId());
			mailAcceptanceVO.setFlightDate(containerAssignmentVOForFUL.getFlightDate());
			mailAcceptanceVO.setCarrierCode(containerAssignmentVOForFUL.getCarrierCode());
			try {
				mailAcceptance.checkForClosedFlight(mailAcceptanceVO);
			} catch (FlightClosedException e) {
				log.error("" + MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION + " " + e);
				constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
						MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
			}
		}
	}

	/**
	 * @author A-8353
	 * @param mailUploadVo
	 * @param keyFormat
	 * @return
	 */
	public String getKeyCondition(MailUploadVO mailUploadVo, String keyFormat) {
		log.debug("WebserviceTrolleyFormat" + " : " + MailConstantsVO.KEYCONDITION + " Entering");
		String displayString = "";
		Map<String, MailUploadVO> templateObject = new HashMap<>();
		templateObject.put("mail", mailUploadVo);
		//TODO: Neo to correct
//		try {
//			displayString = TemplateEncoderUtilInstance.getInstance().encode(keyFormat, "mail", templateObject, false);
//		} finally {
//		}
		log.debug("WebserviceTrolleyFormat" + " : " + MailConstantsVO.KEYCONDITION + " Exiting");
		return displayString;
	}
	public ScannedMailDetailsVO saveRemarksForMailTag(MailUploadVO mailUploadVO) {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		long mailSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(mailUploadVO.getMailTag(),
				mailUploadVO.getCompanyCode());
		MailHistoryRemarksVO mailHistoryRemarksVO = new MailHistoryRemarksVO();
		if (mailSequenceNumber != 0) {
			mailHistoryRemarksVO.setCompanyCode(mailUploadVO.getCarrierCode());
			mailHistoryRemarksVO.setMailSequenceNumber(mailSequenceNumber);
			mailHistoryRemarksVO.setRemark(mailUploadVO.getRemarks());
			mailHistoryRemarksVO.setRemarkDate(mailUploadVO.getScannedDate());
			mailHistoryRemarksVO.setRemarkSerialNumber(mailUploadVO.getSerialNumber());
			mailHistoryRemarksVO.setUserName(mailUploadVO.getScanUser());
		}
		SaveMailbagHistoryNotesFeature saveMailbagHistoryNotesFeature = ContextUtil.getInstance()
				.getBean(SaveMailbagHistoryNotesFeature.class);
		saveMailbagHistoryNotesFeature.execute(mailHistoryRemarksVO);
		return scannedMailDetailsVO;
	}
}
