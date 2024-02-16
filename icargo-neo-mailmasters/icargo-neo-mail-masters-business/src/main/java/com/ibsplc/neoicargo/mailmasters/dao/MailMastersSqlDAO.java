package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.time.format.DateTimeFormatter;
import java.util.*;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import java.time.ZonedDateTime;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.mailmasters.component.MailEventPK;
import com.ibsplc.neoicargo.mailmasters.vo.*;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/** 
 * @author a-A5991
 */
@Slf4j
public class MailMastersSqlDAO extends AbstractQueryDAO implements MailMastersDAO {
	private static final String FIND_MAILBAGS_DETAILS_FOR_VALIDATION_FIRST_PART = "mail.operations.getMailbagDetailsForValidationFirstPart";
	private static final String FIND_MAILBAGS_DETAILS_FOR_VALIDATION_SECOND_PART = "mail.operations.getMailbagDetailsForValidationSecondPart";
	private static final String FIND_MAILBAGS_FROMOAL_INRESDITPROCESING = "mail.operations.findmailbagsfromoalresditprocessing";
	private static final String FIND_MAILBAGS_FORUPLIFTEDRESDIT = "mail.operations.findmailbagsforupliftedresdit";
	private static final String FIND_CARDIT_DETAILS_OF_MAILBAG = "mail.operations.findCarditDetailsOfMailbag";
	private static final String FIND_MAILBAGHISTORIES = "mail.operations.findmailbaghistories";
	private static final String FIND_MAILBAGSTATUS = "mail.operations.findmailstatus";
	private static final String FIND_MAILANDCONTAINERDETAILS = "mail.operations.findmailandcontainerdetails";
	private static final String FIND_MAILDETAILSFORDELIVERYFORGHA = "mail.operations.findmailbagdetailsfordeliveryforgha";
	private static final String FIND_AIRPORTCITYFORMLD = "mail.operations.findairportcityformld";
	private static final String FIND_ULD_ARRIVAL_DTLS = "mail.operations.finduldarrivaldetails";
	private static final String FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_CARRIER = "mail.operations.findAlreadyAssignedTrolleyNumberForMLDWithCarrier";
	private static final String FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_FLIGHT = "mail.operations.findAlreadyAssignedTrolleyNumberForMLDWithFlight";
	private static final String FIND_ULDS_FORUPLIFTEDRESDIT = "mail.operations.finduldsforupliftedresdit";
	private static final String FIND_MAILBAG_ARRIVAL_DTLS = "mail.operations.findmailbagarrivaldetails";
	private static final String IS_EXCHANGEVALID = "mail.operations.isexchangevalid";
	private static final String FIND_MAIL_TYPE = "mail.operations.findmailtype";
	private static final String FETCH_MAIL_INDICATOR = "mail.operations.fetchmailindicatorforprogress";
	private static final String FIND_LATEST_FLIGHT_DETAILS_OF_MAILBAG = "mail.operations.findlatestflightdetailsofmailbag";
	private static final String IS_MAILBAG_ALREADY_ARRIVED = "mail.operations.ismailbagalreadyarrived";
	private static final String FIND_MLD_CONFIGURATIONS = "mail.operations.findmldconfigurations";
	private static final String FIND_MAILBAGS_FLIGHT_ULD = "mail.operations.findflightassignedmailsinuld";
	private static final String FIND_MAILBAGS_FLIGHT_BULK = "mail.operations.findflightassignedmailsinbulk";
	private static final String FIND_MAILBAGS_CARRIER_ULD = "mail.operations.findcarrierassignedmailsinuld";
	private static final String FIND_MAILBAGS_CARRIER_BULK = "mail.operations.findcarrierassignedmailsinbulk";
	private static final String FIND_MALRDTEVTS_FLIGHTDETAILS = "mail.operations.findmailresditeventsflightdetails";
	private static final String CHECK_RESDIT_EXISTS = "mail.operations.checkresditExists";
	private static final String FIND_LASTASSIGNEDRESDIT_FORULD = "mail.operations.findlastassignedresditforuld";
	private static final String CHECK_RESDIT_EXISTSFROMREAASIGN = "mail.operations.checkresditExistsFromReassign";
	private static final String FIND_PA_FORMAILBAGS = "mail.operations.findPAForMailbags";
	private static final String FIND_MAILRESDIT_FLIGHTDETAILS = "mail.operations.findmailresditflightdetails";
	private static final String FIND_IS_CARDIT_PRESENT_FOR_MAIL = "mail.operations.iscarditpresentformail";
	private static final String FIND_IS_FLIGHT_SAME_AS_CARDIT = "mail.operations.isflightsameascardit";
	private static final String FIND_ULDINCARDIT = "mail.operations.finduldincardit";
	private static final String FIND_MAILBAGINCARDIT = "mail.operations.findmailbagsincardit";
	private static final String MODULE = "MailTrackingDefaultsSqlDAO";
	private static final String FIND_DESCRIPTION = "mail.operations.finddecription";
	private static final String FIND_IS_CARDIT_PRESENT_FOR_ULD = "mail.operations.iscarditpresentforuld";
	private static final String FIND_IS_FLIGHT_SAME_AS_CARDIT_FORULD = "mail.operations.isflightsameascarditforuld";
	private static final String FIND_CARDIT_FOR_RESDIT = "mail.operations.findcarditdetailsforresdit";
	private static final String FIND_CARDITCONTAINER_COUNT = "mail.operations.findcarditcontainercount";
	private static final String FIND_CARDITRECEPTACLE_COUNT = "mail.operations.findcarditreceptaclecount";
	private static final String FIND_MAILCARDIT_DETAILS = "mail.operations.findcarditdetailsformailbag";
	private static final String FIND_ALL_MAILCARDIT_DETAILS = "mail.operations.findcarditdetailsforallmailbags";
	private static final String FIND_MAILCARDIT_FLIGHT = "mail.operations.findcarditmaildetailsforopflight";
	private static final String FIND_MAILCARDIT_FLIGHT_INBOUND = "mail.operations.findcarditmaildetailsforinboundopflight";
	private static final String FIND_CARDIT_CONTAINERDETAILS = "mail.operations.findcarditcontainerdetails";
	private static final String FIND_DOCCARDIT_DETAILS = "mail.operations.findcarditdetailsfordocument";
	private static final String FIND_ALL_DOCCARDIT_DETAILS = "mail.operations.findcarditdetailsforalldocuments";
	private static final String FIND_DOCCARDIT_FLIGHT = "mail.operations.findcarditdetailsforopflight";
	private static final String FIND_DOCCARDIT_FLIGHT_INBOUND = "mail.operations.findcarditdetailsforinboundopflight";
	private static final String FIND_CARDITDESP_DETAILS = "mail.operations.findcarditdespatchdetails";
	private static final String FIND_ALL_CARDITDESP_DETAILS = "mail.operations.findcarditdetailsforalldespatches";
	private static final String FIND_CARDITDESP_FLIGHT = "mail.operations.findcarditdespatchdetailsforopflight";
	private static final String FIND_CARDITDESP_FLIGHT_INBOUND = "mail.operations.findcarditdespatchdetailsforinboundopflight";
	private static final String FIND_CARDIT_TRTDETAILS = "mail.operations.findcardittransportation";
	private static final String FIND_CARDITDETAILS_FORALLMAILS = "mail.operations.findcarditdetailsforallmails";
	private static final String FIND_TRANSFERFROMINFO_FROM_CARDIT_FORMAILBAGS = "mail.operations.findtransferfrominfo.fromcardit.formailbags";
	private static final String FIND_CARDIT_PREADVISES = "mail.operations.findcarditpreadvisedetails";
	private static final String FIND_CARDIT_ORIGIN = "mail.operations.getcarditorigin";
	private static final String FIND_ULDRESDIT_STATUS = "mail.operations.finduldresditstatus";
	private static final String FIND_PA_FOR_SHIPPERBUILT_ULD_FRMCDT = "mail.operations.findpaforshipperbuiltuldsfromcardit";
	private static final String FIND_PA_FOR_SHIPPERBUILT_ULD = "mail.operations.findpaforshipperbuiltulds";
	private static final String FIND_OFFICEOFEXCHANGECODES = "mail.operations.findofficeofexchangecodes";
	private static final String MAILTRACKING_DEFAULTS_ROWNUM_QUERY = "SELECT RESULT_TABLE.*,ROW_NUMBER() OVER(ORDER BY NULL) AS RANK FROM (";
	private static final String MAILTRACKING_DEFAULTS_SUFFIX_QUERY = ") RESULT_TABLE";
	private static final String FIND_PA_FOR_EXCHANGEOFFICE = "mail.operations.findpacodeforexchangeoffice";
	private static final String FIND_PARTYID_FOR_PA = "mail.operations.findpartyidforpostalauthority";
	private static final String FIND_RESDIT_CONFIG = "mail.operations.findresditconfiguration";
	private static final String FIND_RESDITCONFIG_TXN = "mail.operations.findresditconfigfortxn";
	private static final String FIND_EXISTING_MAILBAGS = "mail.operations.findexistingmailbags";
	private static final String FIND_POA_CODE = "mail.operations.findpoacode";
	private static final String FIND_SLAIDR_ACCPTANCE_TO_DEPARTURE = "mail.operations.findacceptancetodepartureservicetime";
	private static final String FIND_SLAIDR_ARRIVAL_TO_DELIVERY = "mail.operations.findarrivaltodeliveryservicetime";
	private static final String FIND_CITY_FOR_EXCHANGE_OFFICES = "mail.operations.findcitycodeforexchangeoffices";
	private static final String FIND_LATEST_CONTAINER_ASSIGNMENT = "mail.operations.findLatestContainerAssignment";
	private static final String FIND_CONTAINER_ASSIGNMENT = "mail.operations.findcontainerassignment";
	private static final String IS_MAILSUBCLASSVALID = "mail.operations.ismailsubclassvalid";
	private static final String FIND_MAIL_DISCREPANCIES = "mail.operations.findMailDiscrepancies";
	private static final String FIND_ULDS_IN_INBOUND_FLIGHT = "mail.operations.finduldsininboundflight";
	private static final String FIND_MAILDETAILS_FOR_UPLOAD = "mail.operations.findmailbagdetailsforupload";
	private static final String FIND_NUMBER_OF_BARROWS_PRESENT_IN_FLIGHT_OR_CARRIER = "mail.operations.findnumberofbarrowspresentintheflightorcarrier";
	private static final String FIND_PARTNERCARRIERS = "mail.operations.findpartnercarriers";
	private static final String FIND_COTERMINUS_AIRPORTS = "mail.operations.findCoTerminusAirports";
	private static final String LIST_MAIL_SERVICESTANDARDS = "mail.operations.listServiceStandardDetails";
	private static final String LIST_RDTDETAILS = "mail.operations.findRdtMasterDetails";
	private static final String VALIDATE_COTERMINUS_AIRPORTS = "mail.operations.validateCoTerminusAirports";
	private static final String FIND_MAIL_SEQUENCE_NUMBER = "mail.operations.findMailSequenceNumber";
	private static final String CHECK_CONSIGNMENT_DOCUMENT_EXISTS = "mail.operations.checkConsignmentDocumentExists";
	private static final String FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG = "mail.operations.findconsignmentdetailsformailbag";
	private static final String FIND_CONSIGNMENT_ROUTING_INFOS = "mail.operations.findConsignmentRoutingInfos";
	private static final String FIND_CONSIGNMENT_DOCUMENT_DETAILS = "mail.operations.findConsignmentDocumentDetails";
	private static final String CHECK_MAIL_ACCEPTED = "mail.operations.checkMailAccepted";
	private static final String FIND_MAILDETAILS_FOR_REPORT = "mail.operations.findMailbagDetailsForReport";
	private static final String FIND_CONSIGNMENT_DETAILS_FOR_DESPATCH = "mail.operations.findconsignmentdetailsfordespatchdetails";
	private static final String GENERATE_CONSIGNMENT_DOCUMENT_DETAILS_REPORT = "mail.operations.generateconsignmentdetailsreport";
	private static final String FIND_MLD_DETAILS = "mail.operations.findmlddetails";
	private static final String FIND_CARDIT_MAILS = "mail.operations.findcarditmails";
	private static final String GET_CARDIT_MAILBAGDTLS = "mail.operations.getcarditmailbagdetails";
	private static final String FIND_DUPLICATE_MAILBAGS_IN_CARDIT = "mail.operations.findduplicatemailbagsincardit";
	private static final String FIND_TRANSPORT_EXISTS_CARDIT = "mail.operations.checktransportexistsforcardit";
	private static final String FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR = "mail.operations.findmailsequencenumberfrommailidr";
	private static final String FIND_LOCALPAS = "mail.operations.findlocalpas";
	private static final String FIND_VALIDMAILBAGS_FORUPLIFTEDRESDIT = "mail.operations.findvalidmailbagsforupliftedresdit";
	private static final String VALIDATE_MAILBAG_UPL = "mail.operations.validatemailbagforupl";
	private static final String FIND_INVENTORYMAILBAG_ARRIVAL_DTLS_ULD = "mail.operations.findinventorymailbagsforreassigninuldwithflight";
	private static final String FIND_INVENTORY_MAILBAGSFORREASSIGN_ULD = "mail.operations.findinventorymailbagsforreassigninuld";
	private static final String FIND_INVENTORYMAILBAG_ARRIVAL_DTLS_BULK = "mail.operations.findinventorymailbagsforreassigninbulkwithflight";
	private static final String FIND_INVENTORY_MAILBAGSFORREASSIGN_BULK = "mail.operations.findinventorymailbagsforreassigninbulk";
	private static final String FIND_ARRIVED_MAILDETAILS_FORINVENTORY = "mail.operations.findarrivedmailbagdetailsininventory";
	private static final String FIND_FLIGHT_LEGSERNUM_FOR_CONTAINER = "mail.operations.findflightlegsernumforcontainer";
	private static final String FIND_DELIVERY_BILL_MAILBAGS = "mail.operations.findDeliveryBill";
	private static final String FIND_CONSIGNMENT_DETAILS_FOR_DSN = "mail.operations.findconsignmentdetailsfordsn";
	private static final String VALIDATE_MAILFLIGHT = "mail.operations.validateMailFlight";
	private static final String FIND_CARRIER_ACCEPTANCEDETAILS_ULD = "mail.operations.findcarrieracceptancedetailsinuld";
	private static final String FIND_FLIGHT_ACCEPTANCEDETAILS = "mail.operations.findflightacceptancedetails";
	private static final String FIND_CARRIER_ACCEPTANCEDETAILS_BULK = "mail.operations.findcarrieracceptancedetailsinbulk";
	private static final String FIND_CARRIER_ACCEPTANCEDETAILS_EMPTY = "mail.operations.findcarrieracceptancedetailsinemptycontainers";
	private static final String FIND_ROUTINGS_FOR_MAILBAG = "mail.operations.findroutingsformailbags";
	private static final String FIND_OFFICEOFEXCHANGE_LOV = "mail.operations.findofficeofexchangelov";
	private static final String FIND_MAILSUBCLASSCODES = "mail.operations.findmailsubclasscodes";
	private static final String FIND_PACODE = "mail.operations.findpacode";
	private static final String FIND_ULDS_IN_ASSIGNED_FLIGHT = "mail.operations.finduldsinassignedflight";
	private static final String FIND_ULDS_INFLIGHT_FOR_MANIFEST = "mail.operations.findcontainersinflightformanifest";
	private static final String FIND_MAILBAGS_MANIFEST_ULD = "mail.operations.findmanifestedmailsinuld";
	private static final String FIND_MAILBAGS_MANIFEST_BULK = "mail.operations.findmanifestedmailsinbulk";
	private static final String FIND_MAILBAGS_IMPORT_MANIFEST_ULD = "mail.operations.findimportmanifestedmailsinuld";
	private static final String FIND_MAILBAGS_IMPORT_MANIFEST_BULK = "mail.operations.findimportmanifestedmailsinbulk";
	private static final String FIND_CONTAINERS_IN_ASSIGNED_FLIGHT = "mail.operations.findcontainersinassignedflight";
	private static final String FIND_AWB_DETAILS = "mail.operations.findAWBDetails";
	private static final String FIND_MAILBAGS_CLOSEDFLIGHT_MONIITORSLA = "mail.operations.findmailbagsinclosedflight";
	private static final String FIND_OFFLOAD_DETAILS = "mail.operations.findoffload";
	private static final String OFFLOAD_MAILBAGS = "mail.operations.findoffloadmailbags";
	private static final String FIND_CONTAINERS_DENSE_RANK_QUERY = "SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
	private static final String FIND_CAP_NOT_ACCPETED_MAILBAGS = "mail.operations.findcapturednotaccpetedmailbags";
	private static final String FIND_INVENTORY_MAILBAGS = "mail.operations.findmailbagsforinventory";
	private static final String FIND_MAILBAGS = "mail.operations.findmailbags";
	private static final String FIND_MAILBAGS_FORRETURN = "mail.operations.findmailbagsforreturn";
	private static final String FIND_CONSIGNMENT_DETAILS = "mail.operations.findconsignmentdetails";
	private static final String FIND_DESPATCHES_IN_FLIGHT_ULDS = "mail.operations.finddespatchesinflightulds";
	private static final String FIND_DESPATCHES_IN_FLIGHT_BULK = "mail.operations.finddespatchesinflightbulk";
	private static final String FIND_DESPATCHES_IN_CARRIER_ULDS = "mail.operations.finddespatchesincarrierulds";
	private static final String FIND_DESPATCHES_IN_CARRIER_BULK = "mail.operations.finddespatchesincarrierbulk";
	private static final String FIND_CONTAINERS_FORDESTINATION = "mail.operations.findcontainersfordestination";
	private static final String FIND_BAGCOUNT_FORDESTINATION = "mail.operations.findsumfordestination";
	private static final String FIND_CONTAINERS_FORFLIGHT = "mail.operations.findcontainersforflight";
	private static final String FIND_BOOKING_TIME_FOR_DSNS_IN_CONTAINER = "mail.operations.findbookingtimefordsnsincontainer";
	private static final String FIND_BAGCOUNT_FORFLIGHT = "mail.operations.findsumforflight";
	private static final String FIND_DSN_MAILBAG_MANIFEST = "mail.operations.findDSNMailbagManifest";
	private static final String FIND_AWB_MANIFEST = "mail.operations.findawbmanifestdetails";
	private static final String FIND_MAILBAG_MANIFEST = "mail.operations.findmailbagmanifestdetails";
	private static final String FIND_MANIFEST_DESTNCTG = "mail.operations.finddestnctgmanifestdetails";
	private static final String FIND_DAMAGE_MAILBAG_REPORT = "mail.operations.finddamagemailreport";
	private static final String FIND_DESTNCATMANIFEST_SUMMARY = "mail.operations.finddestnctgmanifestsummarydetails";
	private static final String FIND_AWB_FORFLIGHT = "mail.operations.findawbdetailsforflight";
	private static final String FIND_MAILBAGS_FORDESPATCH = "mail.operations.findmailbagsfordespatch";
	private static final String FIND_MAILNOXID_LOV = "mail.operations.findmailboxidlov";
	private static final String FIND_MAILTAG = "mail.operations.findmailtag";
	private static final String FIND_MAIL_TRANSFERMANIFEST = "mail.operations.findtransfermanifest";
	private static final String FIND_TRANSFER_MANIFEST_DSN_DETAILS = "mail.operations.findtransfermanifestdsndetails";
	private static final String FIND_MAILBAG_DAMAGES = "mail.operations.findmailbagdamages";
	private static final String SEARCHMODE_DEST = "DESTN";
	private static final String FIND_CONTAINERS = "mail.operations.findcontainers";
	private static final String FIND_OFFLOADEDINFO_FORCONTAINER = "mail.operations.findofflodedinfoforcontainer";
	private static final String MAILS_FOR_TRANSHIPMENT_FLIGHT = "mail.operations.mailsfortranshipmentflight";
	private static final String FIND_MAIL_WITHOUT_CARDITS = "mail.operations.findmailswithoutcardits";
	private static final String FIND_MAILSTATUS_CARDIT_NOT_ACCEPTED = "mail.operations.mailstatus.carditnotaccepted";
	private static final String FIND_MAIL_ARRIVED_NOT_DELIVERED = "mail.operations.findmailsarrivedandnotdelivered";
	private static final String FIND_MAILSTATUS_MAIL_DELIVERED = "mail.operations.mailstatus.mailsdelivered";
	private static final String MAILS_IN_FLIGHT = "mail.operations.mailsinflight";
	private static final String MAILS_NOTUPLIFTED_CARRIER = "mail.operations.notupliftedmailsincarrier";
	private static final String MAILS_ACCEPTED_NOT_UPLIFTED_FLIGHT = "mail.operations.acceptednotupliftedmailsinflight";
	private static final String FIND_MAILSTATUS_MAIL_DELIVERED_WITHOUT_CARDIT = "mail.operations.mailstatus.mailsdeliveredwithoutcardit";
	private static final String MAILS_ACCEPTED_NOT_UPLIFTED_CARRIER = "mail.operations.acceptednotupliftedmailsincarrier";
	private static final String FIND_ULDS_FOR_ARRIVAL = "mail.operations.finduldsforarrival";
	private static final String FIND_MAILDETAILS_FORUNSENTRESDITS = "mail.operations.findmaildetailsforunsentresdits";
	private static final String FIND_PARTY_NAME = "mail.operations.findpartyname";
	private static final String FIND_OFFLOADREASON_FOR_MAILBAG = "mail.operations.findoffloadreasonformailbag";
	private static final String FIND_OFFLOADREASON_FOR_CONTAINER = "mail.operations.findoffloadreasonforcontainer";
	private static final String FIND_MAILBAG_DAMAGE_REASON = "mail.operations.finddamagereasonformailbag";
	private static final String FIND_CC_SENDRESDIT = "mail.operations.findCCForSendResdit";
	private static final String FIND_RPT_FOR_XXMAILBAGS = "mail.operations.findrecepientforxxmails";
	private static final String FIND_RPT_FOR_XXULDS = "mail.operations.findrecepientforxxulds";
	private static final String MAILTRACKING_DEFAULTS_GENERATE_DAILYMAILSTATION_REPORT = "mail.operations.generatedailymailstationreport";
	private static final String FIND_PALOV = "mail.operations.findpalov";
	private static final String MAILTRACKING_DEFAULTS_FINDDSNMAILBAG = "mail.operations.finddsnmailbagsforflight";
	private static final String FIND_PA_DETAILS = "mail.operations.findPADetails";
	private static final String VALIDATE_POADETAILS = "mail.operations.validatepoadetails";
	private static final String MAILTRACKING_DEFAULTS_FINDALLPOACODES = "mail.operations.findallpoacodes";
	private static final String SCANNED_MAIL_HHT_DETAILS_OPERATION = "mail.operations.findScannedMailDetails";
	private static final String PERFORM_UPLOAD_CRCTN = "mail.operations.performuploadcorrection";
	private static final String FIND_MAIL_HANDED_OVER_TOFLIGHT = "mail.operations.findmailhandedovertoflight";
	private static final String FIND_MAIL_HANDED_OVER_TOCARRIER = "mail.operations.findmailhandedovertocarrier";
	private static final String FIND_IMPORT_MANIFEST_DETAILS = "mail.operations.findimportmanifestdetails";
	private static final String FIND_ONLINEFLIGHTS_CONTAINERS = "mail.operations.findOnlineFlightsAndConatiners";
	private static final String FIND_FLIGHTS_ARRIVAL = "mail.operations.findflightsforArrival";
	private static final String FIND_MAILBAGS_ARRIVAL = "mail.operations.findMailbagsforArrival";
	private static final String FIND_FLIGHTSFOR_MAILCLOSURE = "mail.operations.findFlightForMailOperationClosure";
	private static final String FIND_ANYCONTAINER_IN_ASSIGNED_FLIGHT = "mail.operations.findanycontainerinassignedflight";
	private static final String MAILTRACKING_DEFAULTS_FINDIMPORTFLIGHTSFORARRIVAL = "mail.operations.findimportflightsforarrival";
	private static final String FIND_DESPATCHES = "mail.operations.finddespatches";
	private static final String FLAG_FALSE = "FALSE";
	private static final String FIND_UPUCODE_NAME_FOR_PA = "mail.operations.findupucodenameforpostalauthority";
	private static final String FIND_PA_FOR_MAILBOXID = "mail.operations.findpacodeformailboxid";
	private static final String PROC_RDT_EVT_RCR = "mail.operations.resditeventreceiver";
	private static final String PROC_RDT_EVT_TMR = "mail.operations.resdittimermanager";
	private static final String FIND_RESDIT_EVENTS = "mail.operations.findresditevents";
	private static final String FIND_MAILBAGS_FLIGHTSEGMENTS = "mail.operations.findMailbagsforFlightSegments";
	private static final String FIND_MAILBAGS_FOR_TRANSPORTCOMPLETED_RESDIT = "mail.operations.findmailbagsfortransportcompletedresdit";
	private static final String FIND_ULDS_FOR_TRANSPORT_COMPLETED_RESDIT = "mail.operations.finduldsfortransportcompletedresdit";
	private static final String FIND_OPERATIONFLIGHTS_MRD = "mail.operations.findOperationalFlightForMRD";
	private static final String FIND_CDT_DTL = "mail.operations.findcarditreferencedetails";
	private static final String FIND_PARTIAL_RESDIT_FOR_PA_ = "mail.operations.findpartialresditflagforpa";
	private static final String FIND_CONSIGNMENT_ROUTING_DETAILS = "mail.operations.findconsignmentroutingdetails";
	private static final String FIND_CITY_CODE_FOR_OFFICEOFEXCHANGE = "mail.operations.findcitycodeforexchangeoffice";
	private static final String FIND_ROUTING_DETAILS = "mail.operations.findroutingdetails";
	private static final String FIND_TRANSPORT_FOR_MAILRESDIT = "mail.operations.findtransportformailresdit";
	private static final String FIND_MAILBAG_FOR_RESDIT = "mail.operations.findmailbagdetailsforresdit";
	private static final String FIND_MAILBAG_FOR_XX_RESDIT = "mail.operations.findmailbagdetailsforxxresdit";
	private static final String FIND_TRANSPORT_FOR_ULDRESDIT = "mail.operations.findtransportforuldresdit";
	private static final String FIND_MAILBAG_FOR_RESDIT_FROM_CARDIT = "mail.operations.findmailbagforresditfromcardit";
	private static final String FIND_ULD_DETAILS_FOR_RESDIT = "mail.operations.findulddetailsforresdit";
	private static final String FIND_ULD_DETAILS_FOR_RESDIT_WITHOUT_CARDIT = "mail.operations.findulddetailsforresditwithoutcardit";
	private static final String FIND_OTHERDSNS_FORSAMEAWB = "mail.operations.findotherdsnsforsameawb";
	private static final String SEARCHMODE_FLT = "FLT";
	private static final String FIND_MAILONHANDLISTFLIGHT = "mail.operations.findMailOnHandDetailsFlight";
	private static final String FIND_MAILONHANDLIST = "mail.operations.findMailOnHandDetails";
	private static final String FIND_MAILONHANDLIST_PARTTWO = "mail.operations.findMailOnHandDetailsparttwo";
	private static final String FIND_MAILONHANDLISTCARRIER = "mail.operations.findMailOnHandDetailsCarrier";
	private static final String PROCESS_MAIL_OPERATIONS_FROM_FILE = "mail.operations.processMailOperationFromFile";
	private static final String FETCH_DATA_FOR_OFFLOAD_UPLOAD = "mail.operations.fetchDataForOfflineUpload";
	private static final String REMOVE_DATA_FROM_TEMPTABLE = "mail.operations.removeDataFromTempTable";
	private static final String FIND_CONAUDIT = "mail.operations.findconaudit";
	private static final String FIND_MAILBAGID_FOR_MAIL_TAG = "mail.operations.findmailbagidformailtag";
	private static final String MAILTRACKING_DEFAULTS_MAIL_AUDITHISTORY_DETAILS = "mail.operations.findmailaudithistorydetails";
	private static final String MAILBAG_AUDIT_FINDAUDITTRANSACTIONCODES = "mail.operations.findaudittransactioncodes";
	private static final String FIND_MAILBAGCOUNTINCONTAINER = "mail.operations.findmailbagcountincontainer";
	private static final String FIND_EXCHANGEOFFICE_FOR_PA = "mail.operations.findexchangeofficeforpa";
	private static final String FIND_AWB_ATTACHED_MAIL_BAG_DETAILS = "mail.operations.findAwbAtachedMailbagDetails";
	private static final String FIND_MAILBAGS_FORREASSIGN = "mail.operations.findMailBagsforReassign";
	private static final String FIND_MAILBOX = "mail.operations.findmailboxid";
	private static final String FIND_AGENT_FOR_PA = "mail.operations.findAgentCodeOfPostalAuthority";
	private static final String FIND_MAILBAGS_FOR_DSN = "mail.operations.findmailbagsfordsn";
	private static final String FIND_MAILTAG_DETAILS = "mail.operations.findmailtagdetails";
	private static final String FIND_MAILBAGEVENTS = "mail.operations.findMailbagResditEvents";
	private static final String FIND_GRAND_TOTAL = "mail.operations.findmailbagtotals";
	private static final String FIND_MAIL_SERVICE_LEVEL = "mail.operations.findmailservicelevel";
	private static final String GENERATE_CONSIGNMENT_DETAILS_REPORT_FOR_AV7 = "mail.operations.generateconsignmentdetailsreportforAV7";
	private static final String FIND_POSTAL_CAL_DETAILS = "mail.operations.finduspspostalcalendardetails";
	private static final String FIND_MAIL_HANDOVER_DETAILS = "mail.operations.findMailHandoverDetails";
	private static final String FIND_FLIGHT_STA = "mail.operations.findscheduledarrivaltimeforflight";
	private static final String FIND_HANDOVERTIME = "mail.operations.findhandovertime";
	private static final String FIND_RDTOFFSET = "mail.operations.findrdtoffset";
	private static final String FIND_CONTRACT_DETAILS = "mail.operations.findgpacontractdetails";
	private static final String CHECK_RECIEVEFRMTRUCK_ENABLED = "mail.operations.checkReceivedFromTruckEnabled";
	private static final String FIND_AWB_PARTIAL_OFL_PCS = "mail.operations.findAwbPartialOflPcs";
	private static final String FIND_CARDIT_DETAILS = "mail.operations.findcarditdetails";
	private static final String FIND_MAILBAGS_FOR_AWB = "mail.operations.findMailbagsForAWB";
	private static final String FIND_MAIL_BOOKING_COUNT = "mail.operations.findMailBookingCount";
	private static final String FIND_AWBATTACHED_MAILS = "mail.operations.findAWBAttachedMailbags";
	private static final String FIND_EMPTY_ULDS_IN_MAIL_FLIGHT = "mail.operations.findEmptyULDsInMailFlight";
	private static final String FIND_CARDIT_DSN_DETAILS = "mail.operations.findcarditdsndetails";
	private static final String OUTBOUND_FIND_FLIGHT_FOR_PREADVICE = "mail.operations.findoutboundflightsdetailsforpreadvice";
	private static final String OUTBOUND_FIND_FLIGHTDETAILS = "mail.operations.findoutboundlistflightdetails";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW = "mail.operations.findmailbagsinulddsnview";
	private static final String FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW = "mail.operations.findmailbagsinbulkdsnview";
	private static final String FIND_MAILBAGS_IN_FLIGHT_ULD = "mail.operations.findmailbagsincontaineruld";
	private static final String FIND_MAILBAGS_IN_FLIGHT_BULK = "mail.operations.findmailbagsincontainerbulk";
	private static final String FIND_CARDIT_SUMMARY_DETAIL = "mail.operations.findcarditssummaryview";
	private static final String FIND_CARDIT_GROUP_VIEW_ACCEPTED = "mail.operations.findcarditsgroupviewacceptedcount";
	private static final String FIND_CARDIT_GROUP_VIEW_COUNT = "mail.operations.findcarditsgroupviewcount";
	private static final String FIND_LYINGLIST_SUMMARY_DETAIL = "mail.operations.findlyinglistssummaryview";
	private static final String FIND_CAPTURE_NOTACCEPTED_SUMMARY_DETAIL = "mail.operations.findcapturednotaccpetedmailbagssummaryview";
	private static final String OUTBOUND_FIND_CARRIER_FOR_ULD = "mail.operations.findcarrierlistdetailsuld";
	private static final String OUTBOUND_FIND_CARRIER_FOR_BULK = "mail.operations.findcarrierlistdetailsbulk";
	private static final String OUTBOUND_FIND_CARRIER_FOR_EMPTY = "mail.operations.findcarrierlistdetailsempty";
	private static final String FIND__OUTBOUND_CARRIER_CONTAINERDETAILS_ULD = "mail.operations.findcarriercontainersinuld";
	private static final String FIND_OUTBOUND_CARRIER_CONTAINERDETAILS_BULK = "mail.operations.findcarriercontainetsinbulk";
	private static final String FIND_MAILBAGS_CARRIER_ULD_DSNVIEW = "mail.operations.findmailbagsincarrierdsnview";
	private static final String FIND_MAILBAGS_CARRIER_BULK_DSNVIEW = "mail.operations.findmailbagsincarrierbulkdsnview";
	private static final String FIND_MAILBAGS_IN_CARRIER_ULD = "mail.operations.findmailbagsincarrieruld";
	private static final String FIND_MAILBAGS_IN_CARRIER_BULK = "mail.operations.findmailbagsincarrierbulk";
	private static final String FIND_MAILBAGS_INSIDE_FLIGHT_BULK = "mail.operations.findmailbagsinbulkuld";
	private static final String FIND_FLIGHTS_FOR_INBOUND1 = "mail.operations.findinboundflightdetailsprefix";
	private static final String FIND_FLIGHTS_FOR_INBOUND2 = "mail.operations.findinboundflightdetailssuffix";
	private static final String FIND_FLIGHTS_FOR_INBOUND3 = "mail.operations.findinboundnewflightdetails";
	private static final String FIND_MANIFEST_DETAILS = "mail.operations.findinboundmanifestdetails";
	private static final String FIND_MANIFEST_DETAILS2 = "mail.operations.findinboundmanifestdetailssuffix";
	private static final String FIND_INBOUND_CONTAINER_DETAILS = "mail.operations.findinboundflightcontainerdetails";
	private static final String FIND_INBOUND_MAILBAG_DETAILS = "mail.operations.findinboundmailbagdetails";
	private static final String FIND_INBOUND_DSN_DETAILS = "mail.operations.findinbounddsndetails";
	private static final String FIND_DSN_OFFLOAD_DETAILS = "mail.operations.findoffloaddsn";
	private static final String FIND_INCENTIVE_CONFIGURATION_DETAILS = "mail.operations.findIncentiveConfigurationDetails";
	private static final String MAIL_OPERATIONS_FIND_OUTBOUNDRUNNERFLIGHTS = "mail.operations.findOutboundRunnerFlights";
	private static final String MAIL_OPERATIONS_FIND_INBOUNDRUNNERFLIGHTS = "mail.operations.findInboundRunnerFlights";
	private static final String MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS = "mail.operations.findRefusedRunnerFlights";
	private static final String FIND_CLOSEOUT_DATE = "mail.operations.findcloseoutdate";
	private static final String FIND_SERVICE_STANDARD = "mail.operations.findservicestandard";
	private static final String MAIL_OPERATIONS_FIND_MAILBAGWITHCONSIGNMENTFORTRUCK = "mail.operations.findMailbagsWithConsignmentForTruckFlight";
	private static final String MAIL_OPERATIONS_FIND_ALLMAILBAGWITHCONSIGNMENTFORTRUCK = "mail.operations.findAllMailbagsForTruckFlight";
	private static final String FIND_ROUTING_INDEX = "mail.operations.findRoutingIndex";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_OPS = "mail.operations.findforcemajeureapplicablemailsforops";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_CARDIT = "mail.operations.findforcemajeureapplicablemailsforcardit";
	private static final String MAIL_OPERATIONS_SAVE_FORCE_MAJUERE = "mail.operations.saveForceMajeureRequest";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST = "mail.operations.findforcemjeurerequestdetails";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_LOV = "mail.operations.findforcemjeurerequestids";
	private static final String MAIL_OPERATIONS_UPDATE_FORCE_MAJUERE = "mail.operations.updateForceMajeureRequest";
	private static final String FIND_ALL_CONTAINERS_IN_ASSIGNED_FLIGHT = "mail.operations.findAllContainersInAssignedFlight";
	private static final String GET_MAIL_MANIFESTINFO = "mail.operations.getmanifestinfo";
	private static final String FETCH_MAILDATA_FOR_OFFLINE_UPLOAD = "mail.operations.fetchMailDataForOfflineUpload";
	private static final String CHECK_SCAN_WAVED_AIRPORT = "mail.operations.checkScanWavedAirport";
	private static final String FIND_PERFORMANCE_MAILBAGS_BASE = "mail.operations.findPerformanceMailbags";
	private static final String FIND_PERFORMANCE_MAILBAGS_SERVICE_FAILURE_COUNT = "mail.operations.findPerformanceMailbagsServiceFailureCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_ONTIMEMAILBAGS_COUNT = "mail.operations.findPerformanceMailbagsOntimeMailbagsCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE_COUNT = "mail.operations.findPerformanceMailbagsForceMejureCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_SELECT = "mail.operations.findPerformanceMailbagsSelect";
	private static final String FIND_SERVICE_FAILURE_DETAILS = "mail.operations.findServiceFailureDetails";
	private static final String FIND_STATION_ONTIME_DETAILS = "mail.operations.findStationOntimeDetails";
	private static final String FIND_FORCE_MAJEURE_DETAILS = "mail.operations.findForceMajeureMailbagCountDetails";
	private static final String FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE = "mail.operations.findPerformanceMailbagsForceMejure";
	private static final String FIND_SERVICE_FAILURE_MAILBAGS = "mail.operations.findServiceFailureMailbags";
	private static final String FIND_STATION_ONTIME_MAILBAGS = "mail.operations.findStationOntimeMailbags";
	private static final String FIND_FORCE_MAJEURE_MAILBAGS = "mail.operations.findForceMajeureRequestedMailbags";
	private static final String FIND_MAILBAGS_MAILBAGENQUIRY = "mail.operations.findMailbagDetailsForMailbagEnquiryHHT";
	private static final String FIND_INVOIC_PERIOD_DETAILS = "mail.operations.findInvoicPeriodDetails";
	private static final String FIND_DUPLICATEMAILBAG_SEQNUM = "mail.operations.findMailbagDetailsForDuplicate";
	private static final String FIND_SERVICERESPONSIVEINDICATOR = "mail.operations.findServiceResponsiveIndicator";
	private static final String FIND_MAILBOXID_FROM_CONFIG = "mail.operations.findMailboxIdFromConfig";
	private static final String FIND_MAILBOX_EVENT = "mail.operations.findmailevent";
	private static final String FIND_TMPCARDITMSGS = "mail.operations.findTempCarditMessages";
	private static final String FIND_DEVIATION_LIST_QUERY_MAIN = "mail.operations.findDeviationMailbags_query";
	private static final String FIND_DEVIATION_LIST_QUERY_1 = "mail.operations.findDeviationMailbags_query1";
	private static final String FIND_DEVIATION_LIST_QUERY_2 = "mail.operations.findDeviationMailbags_query2";
	private static final String FIND_BULKCOUNT_FLIGHT = "mail.operations.findbulkcountforoutbound";
	private static final String FIND__OUTBOUND_FLIGHT_CONTAINERDETAILS_ULD = "mail.operations.findflightuldcontainerdetails";
	private static final String FIND_OUTBOUND_FLIGHT_CONTAINERDETAILS_BULK = "mail.operations.findflightbulkcontainerdetails";
	private static final String FIND_ROUTING_DETAILS_FOR_CONSIGNMENT = "mail.operations.findroutingforconsignment";
	private static final String VALID_CONTAINER_FOR_ARRORDLV = "mail.operations.isValidContainerForULDlevelArrivalOrDelivery";
	private static final String MAILBAGS_FORPABUILT_CONTAINERSAVE = "mail.operations.findMailbagForPABuiltContainer";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_INBOUND = "mail.operations.findflightassignedmailsinuldinboundforreact";
	private static final String FIND_MAILBAGS_STORAGE_UNIT = "mail.operations.findmailsinstorageunit";
	private static final String CONTAINER_INFO_DEVIATED_MAILBAG = "mail.operations.findcontainerinfofordeviatedmailbag";
	private static final String FIND_APPROVED_FORCE_MAJEURE_DETAILS_OF_MAILBAG = "mail.operations.findApprovedForceMajeureDetails";
	private static final String FIND_MAILDETAILS = "mail.operations.findmailDetails";
	private static final String FIND_MAILBOX_FORPA = "mail.operations.findMailBoxForPA";
	private static final String FIND_MAIL_BILLING_STATUS = "mail.operations.findMailbagBillingStatus";
	private static final String FIND_TRANSFER_MANIFEST_CONSIGNMENTDTL = "mail.operations.findTransferManifestConsignmentDetails";
	private static final String FIND_NOT_UPLIFTED_MAILBAGS_FOR_DEVIATION = "mail.operations.findNotupliftedMailsInCarrierforDeviationlist";
	private static final String GET_MAIL_MANIFESTINFO_NXT_SEG = "mail.operations.getmanifestinfofornextsegment";
	private static final String MAILBAG_IN_CONTAINER_USED_IN_NEXT_SEG = "mail.operations.checkMailbagInContainerAlreadyUsedInNextSegment";
	private static final String FIND_MAILDETAILSFORNEW = "mail.operations.findmaildetailsfornewstatus";
	private static final String FIND_TRANSFER_MANIFEST_ID_DETAILS = "mail.operations.findtransfermanifestid";
	private static final String GENERATE_CONSIGNMENT_DETAILS_SUMMARY_REPORT = "mail.operations.consignmentDetailsSummaryReport";
	private static final String MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS_PART2 = "mail.operations.findRefusedRunnerFlightsPart2";
	private static final String MAIL_OPERATIONS_SAVE_FORCE_MAJEURE_REQUEST_FOR_UPLOAD = "mail.operations.saveforcemajeurerequestforupload";
	private static final String FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS = "mail.operations.findInboundFlightOperationsDetails";
	private static final String FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS_ONE = "mail.operations.findInboundFlightOperationsDetails_one";
	private static final String OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT = "mail.operations.findOffloadULDDetailsAtAirport";
	private static final String FIND_EXPORTFLIGHT_OPERATIONS_DETAILS = "mail.operations.findExportFlightOperationsDetails";
	private static final String DATE = "yyyyMMdd";
	private static final String MAIL_OPERATIONS_FETCH_CONSIGNMENT_DETAILS_FOR_UPLOAD = "mail.operations.fetchconsignmentdetailsforupload";
	private static final String MAIL_OPERATIONS_FIND_CONTAINER_JOURNEY_ID = "mail.operations.findcontainerjourneyid";
	private static final String GET_MAILBAGS_ARRIVAL = "mail.operations.getMailbagsforArrival";
	private static final String MAIL_OPERATIONS_FIND_FLIGHT_AUDIT_DETAILS = "mail.operations.findflightauditdetails";
	private static final String MAIL_OPERATIONS_FIND_FLIGHT_PURE_CONTAINER_DETAILS = "mail.operations.findflightPureContainerDetails";
	private static final String MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS = "mail.operations.listmailbagSecurityDetails";
	private static final String MAIL_OPERATIONS_FIND_AGENT_CODE_FROM_UPUCODE = "mail.operations.findagentcodefromupucode";
	private static final String MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS_MAIL_SEQUENCE_NUMBER = "mail.operations.findMailSequenceNumberForMailbagSecurity";
	private static final String LIST_MAILBAG_SECURITY_DETAILS = "listMilbagSecurityDetails";
	private static final String MAIL_OPERATIONS_SELECT_COUNT_FROM_MALCSGCSDDTL = "mail.operations.findScreeningDetails";
	private static final String MAIL_OPERATIONS_FIND_ROUTING_DETAILS = "mail.operations.findRoutingDetailsForPrint";
	private static final String MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG = "mail.operations.findRegulatedCarrierForMailbag";
	private static final String MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING = "mail.operations.findlatestregulatedagentissuing";
	private static final String MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM = "mail.operations.findscreeningmethodwithoutagentsernum";
	private static final String MAIL_OPERATIONS_FIND_AWB_ATTACHED_MAIL_DETAILS = "mail.operations.findMailbagsAttachedToAWBNo";
	private static final String FIND_LATEST_CONTAINER_ASSIGNMENT_FOR_ULDDELIVERY = "mail.operations.findLatestContainerAssignmentForUldDelivery";
	private static final String MAIL_OPERATIONS_FIND_MAILBAG_DETAILS = "mail.operations.findMailbagDetails";
	private static final String MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER = "mail.operations.findLoadPlanVersionsForContainer";
	private static final String MAIL_OPERATIONS_FIND_LOADPLAN_DETAILS_FOR_FLIGHT = "mail.operations.findLoadPlanDetails";
	private static final String FIND_CONTAINERS_OUTERQUERY = "mail.operations.findcontainerswithsubclassgrpfilter";
	private static final String MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG = "mail.operations.findRAacceptingForMailbag";
	private static final String MAIL_OPERATIONS_FIND_ROUTING_DETAILS_FOR_MAILBAG = "mail.operations.findRoutingDetailsForMailBag";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_WITHOUTACCEPTANCE = "mail.operations.findflightassignedmailsinuldwithoutacceptance";
	private static final String FIND_MAILBAGS_FLIGHT_BULK_WITHOUTACCEPTANCE = "mail.operations.findflightassignedmailsinbulkwithoutacceptance";
	public static final String MAIL_TRACKING_DEFAULTS_SQLDAO = "MailTrackingDefaultsSQLDAO";
	public static final String FIND_MAILBAGS_IN_CONTAINER = "findMailbagsInContainer";
	private static final String MAIL_OPERATIONS_GET_PA_DETAILS = "mail.operations.getPADetails";
	private static final String MAIL_OPERATIONS_GET_OFFICE_OF_EXCHANGE_DETAILS = "mail.operations.getOfficeOfExchangeDetails";
	private static final String MAIL_OPERATIONS_CHECK_FOR_SECURITYSCREENING_VALIDATIONS = "mail.operations.checkforsecurityscreeningvalidations";
	private static final String MAIL_OPERATIONS_GET_MAILBAG_DETAILS = "mail.operations.getMailbagDetails";
	private static final String MAIL_OPERATIONS_GET_MAILBAG_DETAILS_FOR_ORACLE = "mail.operations.getMailbagDetailsForOracle";
	private static final String MAIL_OPERATIONS_GET_SUBCLASS_DETAILS = "mail.operations.getSubclassDetails";
	private static final String MAIL_OPERATIONS_GET_EXCHANGE_OFFICE_DETAILS = "mail.operations.getExchangeOfficeDetails";
	public static final String GET_PLANNED_ROUTING_INDEX_DETAILS = "mail.operations.getPlannedRoutingIndexDetails";
	public static final String LOG_DELIMITER = "=============>>";
	private static final String QUERY_VALIDITY_CHECK = "  AND ?  BETWEEN VLDFRMDAT AND VLDTOODAT  ";
	private static final String GENERATE_CN46_CONSIGNMENT_DOCUMENT_DETAILS_REPORT = "mail.operations.generateCN46consignmentdetailsreport";
	private static final String GENERATE_CN46_CONSIGNMENT_DETAILS_SUMMARY_REPORT = "mail.operations.generateCN46consignmentDetailsSummaryReport";
	private static final String FIND_CN46_TRANSFER_MANIFEST_TDTL = "mail.operations.findCN46TransferManifestDetails";
	private static final String FIND_DENSITY_FACTOR_FOR_PA = "mail.operations.finddensityfactorforpostalauthority";
	private static final String MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL = "mail.operations.findapplicableregulationflagformailbag";
	private static final String OUTBOUND_FLIGHT_PREADVICE_DETAILS_NEW = "mail.operations.preadvicedetailsforflight";
	private static final String FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB = "mail.operations.findflightsformailinboundautoattachawb";
	private static final String FIND_MAILBAG_MLDDETAILS = "mail.operations.findMailBagMLDDetails";
	private static final String FIND_MAILTRANSIT = "mail.operations.findmailtransitdetails";
	private static final String FIND_MAILBAGNOTES = "mail.operations.findmailbagnotes";
	private static final String FIND_MAILBAGS_MAILINBOUNDHHT = "mail.operations.findMailbagDetailsForMailInboundHHT";
	private static final String FIND_MAIL_CONSUMED_DATA = "mail.operations.findMailConsumed";
	private static final String FIND_MAILBAGS_FOR_PAWB_CREATION = "mail.operation.findMailbagsForPAWBCreation";
	private static final String FIND_MSTDOCNUM_FOR_AWB_DETAILS = "mail.operations.findMstDocNumForAWBDetails";
	private static final String MAIL_OPERATIONS_FIND_OFFICE_OF_EXCHANGES_FOR_AIRPORT = "mail.operations.findOfficeOfExchangesForAirport";
	private static final DateTimeFormatter DB_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
	private static final DateTimeFormatter YYMMDD_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");



	/**
	* @author a-1936 This method is used to validate the MailSubclass
	* @param companyCode
	* @param mailSubClass
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public boolean validateMailSubClass(String companyCode, String mailSubClass) throws PersistenceException {
		log.debug("INSIDE SQLDAO" + " : " + "validateMailSubClass" + " Entering");
		int index = 0;
		boolean isValid = false;
		Query qry = getQueryManager().createNamedNativeQuery(IS_MAILSUBCLASSVALID);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, mailSubClass);
		Mapper<String> subClassMapper = getStringMapper("SUBCLSCOD");
		String subClass = qry.getSingleResult(subClassMapper);
		return StringUtils.isNotBlank(subClass);
	}

	/** 
	* @author a-1876 This method is used to list the PartnerCarriers.
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
			String airportCode) throws PersistenceException {
		log.debug(MODULE + " : " + "findAllPartnerCarriers" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_PARTNERCARRIERS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, ownCarrierCode);
		qry.setParameter(++index, airportCode);
		return qry.getResultList(new PartnerCarrierMapper());
	}

	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) throws PersistenceException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		boolean isValid = false;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(VALIDATE_COTERMINUS_AIRPORTS);
		qry.setParameter(++index, logonAttributes.getCompanyCode());
		qry.setParameter(++index, actualAirport);
		qry.setParameter(++index, eventAirport);
		qry.setParameter(++index, eventCode);
		qry.setParameter(++index, paCode);
		if (dspDate != null) {
			qry.append("  AND ?  BETWEEN MTK.VLDFRMDAT AND MTK.VLDTOODAT ");
			qry.setParameter(++index, dspDate.toLocalDateTime());
		}
		log.debug("" + "validateCoterminusairports query:  " + " " + qry);
		String coTerminusAirport = qry.getSingleResult(getStringMapper("ARPCOD"));
		if (coTerminusAirport != null) {
			log.debug("" + "validateCoterminusairports result:  " + " " + coTerminusAirport);
			isValid = true;
		}
		return isValid;
	}

	public Collection<CoTerminusVO> findAllCoTerminusAirports(CoTerminusFilterVO filterVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findAllCoTerminusAirports" + " Entering");
		int index = 0;
		String[] airportCodes = null;
		if (filterVO.getAirportCodes() != null)
			airportCodes = filterVO.getAirportCodes().split(",");
		int size = airportCodes.length;
		int cnt = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_COTERMINUS_AIRPORTS);
		if ("Y".equals(filterVO.getReceivedfromTruck())) {
			qry = qry.append("AND TRKFLG=?");
		}
		if (!(("").equals(filterVO.getResditModes()))) {
			qry = qry.append(" AND MTK.RSDMOD = ?");
		}
		log.debug("" + "airportCodes " + " " + airportCodes);
		log.debug("" + "getResditModes " + " " + filterVO.getResditModes());
		if (airportCodes != null && !(("").equals(filterVO.getAirportCodes()))) {
			qry = qry.append(" AND(");
			for (String code : airportCodes) {
				cnt++;
				qry = qry.append(" INSTR(ARPCOD,?,1)>0");
				if (cnt < size) {
					qry = qry.append("AND");
				}
			}
			qry = qry.append(")");
		}
		log.debug("" + "Co terminus query " + " " + qry);
		qry.setParameter(++index, filterVO.getCompanyCode());
		qry.setParameter(++index, filterVO.getGpaCode());
		if ("Y".equals(filterVO.getReceivedfromTruck())) {
			qry.setParameter(++index, filterVO.getReceivedfromTruck());
		}
		if (!(("").equals(filterVO.getResditModes())))
			qry.setParameter(++index, filterVO.getResditModes());
		if (airportCodes != null && !(("").equals(filterVO.getAirportCodes()))) {
			for (String code : airportCodes) {
				qry.setParameter(++index, code);
			}
		}
		log.debug("" + "Co terminus query " + " " + qry);
		Collection<CoTerminusVO> coTerminusVOs = qry.getResultList(new CoterminusAirportsMapper());
		return coTerminusVOs;
	}

	public Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO, int pageNumber) throws PersistenceException {
		int pageSize = mailServiceStandardFilterVO.getDefaultPageSize();
		int totalRecords = mailServiceStandardFilterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		Query baseQry = getQueryManager().createNamedNativeQuery(LIST_MAIL_SERVICESTANDARDS);
		rankQuery.append(baseQry);
		PageableNativeQuery<MailServiceStandardVO> qry = new ServiceStandardFilterQuery(pageSize, totalRecords,
				mailServiceStandardFilterVO, rankQuery.toString(), new MailServiceStandardMapper());
		log.info("" + "Query: " + " " + qry);
		qry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return qry.getPage(pageNumber);
	}

	public Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findRdtMasterDetails" + " Entering");
		int index = 0;
		String[] airportCodes = null;
		Query qry = getQueryManager().createNamedNativeQuery(LIST_RDTDETAILS);
		qry.setParameter(++index, filterVO.getCompanyCode());
		log.debug("" + "airportCodes " + " " + airportCodes);
		log.debug("" + "Co terminus query " + " " + qry);
		if ((filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes())))
				|| (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode())))) {
			qry = qry.append("AND");
		}
		if (filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes()))) {
			qry = qry.append("(DSTARPCOD=?");
			qry.setParameter(++index, filterVO.getAirportCodes());
		}
		if (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode()))) {
			qry = qry.append("OR ORGARPCOD=?");
			qry.setParameter(++index, filterVO.getOriginAirportCode());
		}
		if ((filterVO.getAirportCodes() != null && !(("").equals(filterVO.getAirportCodes())))
				|| (filterVO.getOriginAirportCode() != null && !(("").equals(filterVO.getOriginAirportCode())))) {
			qry = qry.append(")");
		}
		if (filterVO.getMailType() != null && filterVO.getMailType().trim().length() > 0) {
			qry = qry.append("AND MALTYP=?");
			qry.setParameter(++index, filterVO.getMailType());
		}
		if (filterVO.getGpaCode() != null && filterVO.getGpaCode().trim().length() > 0) {
			qry = qry.append("AND GPACOD=?");
			qry.setParameter(++index, filterVO.getGpaCode());
		}
		if (filterVO.getMailClass() != null && filterVO.getMailClass().trim().length() > 0) {
			qry = qry.append("AND MALCLS=?");
			qry.setParameter(++index, filterVO.getMailClass());
		}
		log.debug("" + "Co terminus query " + " " + qry);
		Collection<MailRdtMasterVO> mailRdtMasterVOs = qry.getResultList(new MailRdtMasterMapper());
		return mailRdtMasterVOs;
	}

	@Override
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode)
			throws PersistenceException {
		log.debug(MODULE + " : " + "findMailSubClassCodes" + " Entering");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILSUBCLASSCODES);
		qry.setParameter(++index, companyCode);
		if (subclassCode != null && subclassCode.trim().length() > 0) {
			subclassCode = subclassCode.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			qry.append("AND MTK.SUBCLSCOD LIKE  ? ");
			qry.setParameter(++index, subclassCode);
		}
		return qry.getResultList(new MailSubClassCodeMapper());
	}

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode)
			throws PersistenceException {
		log.debug(MODULE + " : " + "findLocalPAs" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_LOCALPAS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, countryCode);
		return query.getResultList(new LocalPAMapper());
	}

	/** 
	* @author A-2037 Method for OfficeOfExchangeLOV containing code anddescription
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,
			int defaultSize) throws PersistenceException {
		log.debug(MODULE + " : " + "findOfficeOfExchangeLov" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		int index = 0;
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_OFFICEOFEXCHANGE_LOV);
		masterQuery.append(queryString);
		if (defaultSize == 0) {
			defaultSize = 25;
		}
		PageableNativeQuery<OfficeOfExchangeVO> pgNativeQuery = new PageableNativeQuery<OfficeOfExchangeVO>(defaultSize,-1,
				 masterQuery.toString(), new OfficeOfExchangeLovMapper(), PersistenceController.getEntityManager().currentSession());

		pgNativeQuery.setParameter(++index, officeofExchangeVO.getCompanyCode());
		String code = officeofExchangeVO.getCode();
		String description = officeofExchangeVO.getCodeDescription();
		if (code != null && code.trim().length() > 0) {
			code = code.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE);
			if (code.contains(",")) {
				String[] codes = code.split(",");
				StringBuilder qstrA = new StringBuilder().append("AND UPPER(EXGOFCCOD) IN ");
				String codeQuery = getWhereClause(codes);
				qstrA.append(codeQuery).append(")");
				pgNativeQuery.append(qstrA.toString());
			} else {
				code = code + MailConstantsVO.PERCENTAGE;
				pgNativeQuery.append("AND EXGOFCCOD LIKE")
						.append(new StringBuilder().append("'").append(code).append("'").toString());
			}
		}
		if (description != null && description.length() > 0) {
			description = description.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append(" AND UPPER(EXGCODDES) LIKE ?");
			pgNativeQuery.setParameter(++index, description.toUpperCase());
		}
		if (officeofExchangeVO.getAirportCode() != null && officeofExchangeVO.getAirportCode().length() > 0) {
			pgNativeQuery.append(" AND ARPCOD LIKE ?");
			pgNativeQuery.setParameter(++index, officeofExchangeVO.getAirportCode().toUpperCase());
		}
		if (officeofExchangeVO.getPoaCode() != null && officeofExchangeVO.getPoaCode().length() > 0) {
			pgNativeQuery.append(" AND POACOD LIKE ?");
			pgNativeQuery.setParameter(++index, officeofExchangeVO.getPoaCode().toUpperCase());
		}
		pgNativeQuery.append(" ORDER BY EXGOFCCOD ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}

	/** 
	* Method		:	MailTrackingDefaultsSqlDAO.getWhereClause Added by 	:	A-4803 on 12-May-2015 Used for 	: Parameters	:	@param sccCodes Parameters	:	@return Return type	: 	String
	*/
	private String getWhereClause(String[] sccCodes) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("('");
		for (String code : sccCodes) {
			buffer.append(code).append("','");
		}
		int len = buffer.length();
		return buffer.toString().substring(0, len - 3).trim() + "'";
	}

	/** 
	* @author A-2037 Method for MailSubClassLOV containing code and description
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode, String code, String description,
			int pageNumber, int defaultSize) throws PersistenceException {
		log.debug(MODULE + " : " + "findMailSubClassCodeLov" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		int index = 0;
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAILSUBCLASSCODES);
		masterQuery.append(queryString);
		if (defaultSize == 0) {
			defaultSize = 25;
		}
		PageableNativeQuery<MailSubClassVO> pgNativeQuery = new PageableNativeQuery<MailSubClassVO>(defaultSize,-1,
				masterQuery.toString(), new MailSubClassCodeMapper(),PersistenceController.getEntityManager().currentSession());
		pgNativeQuery.setParameter(++index, companyCode);
		if (code != null && code.length() > 0) {
			code = code.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE);
			if (code.contains(",")) {
				String[] codes = code.split(",");
				StringBuilder qstrA = new StringBuilder().append("AND UPPER(MTK.SUBCLSCOD) IN ");
				String codeQuery = getWhereClause(codes);
				qstrA.append(codeQuery).append(")");
				pgNativeQuery.append(qstrA.toString());
			} else {
				code = code + MailConstantsVO.PERCENTAGE;
				pgNativeQuery.append("AND MTK.SUBCLSCOD LIKE ?");
				pgNativeQuery.setParameter(++index, code);
			}
		}
		if (description != null && description.length() > 0) {
			description = description.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append("AND UPPER(MTK.DES) LIKE ?");
			pgNativeQuery.setParameter(++index, description.toUpperCase());
		}
		pgNativeQuery.append(" ORDER BY SUBCLSCOD ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}

	/** 
	* @author A-5931 Method for MBI LOV containing mailboxCode and mailboxDescription
	* @param companyCode
	* @param mailboxCode
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode, String mailboxCode, String mailboxDesc,
			int pageNumber, int defaultSize) throws PersistenceException {
		log.debug(MODULE + " : " + "findPALov" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAILNOXID_LOV);
		masterQuery.append(queryString);
		PageableNativeQuery<MailBoxIdLovVO> pgNativeQuery = new PageableNativeQuery<MailBoxIdLovVO>(defaultSize, -1,
				masterQuery.toString(), new MailBoxIdLovMapper(), PersistenceController.getEntityManager().currentSession());

		int index = 0;
		pgNativeQuery.setParameter(++index, companyCode);
		if (mailboxCode != null && mailboxCode.length() > 0) {
			pgNativeQuery.append("AND MALBOX.MALBOXIDR LIKE ?");
			pgNativeQuery.setParameter(++index, mailboxCode);
		}
		if (mailboxDesc != null && mailboxDesc.length() > 0) {
			pgNativeQuery.append("AND UPPER(MALBOX.MALBOXDES) LIKE ?");
			pgNativeQuery.setParameter(++index, mailboxDesc.toUpperCase());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}

	/** 
	* @author A-2037 Method for PALov containing PACode and PADescription
	* @param companyCode
	* @param paCode
	* @param paName
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Page<PostalAdministrationVO> findPALov(String companyCode, String paCode, String paName, int pageNumber,
			int defaultSize) throws PersistenceException {
		log.debug(MODULE + " : " + "findPALov" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_PALOV);
		masterQuery.append(queryString);
		if (defaultSize == 0) {
			defaultSize = 25;
		}
		PageableNativeQuery<PostalAdministrationVO> pgNativeQuery = new PageableNativeQuery<PostalAdministrationVO>(
				defaultSize,-1, masterQuery.toString(), new PALovMapper(),PersistenceController.getEntityManager().currentSession());

		int index = 0;
		pgNativeQuery.setParameter(++index, companyCode);
		if (paCode != null && paCode.length() > 0) {
			paCode = paCode.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE);
			if (paCode.contains(",")) {
				String[] paCodes = paCode.split(",");
				StringBuilder qstrA = new StringBuilder().append("AND UPPER(POACOD) IN ");
				String paQuery = getWhereClause(paCodes);
				qstrA.append(paQuery).append(")");
				pgNativeQuery.append(qstrA.toString());
			} else {
				paCode = paCode + MailConstantsVO.PERCENTAGE;
				pgNativeQuery.append("AND POACOD LIKE ?");
				pgNativeQuery.setParameter(++index, paCode);
			}
		}
		if (paName != null && paName.length() > 0) {
			paName = paName.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE) + MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append("AND UPPER(POANAM) LIKE ?");
			pgNativeQuery.setParameter(++index, paName.toUpperCase());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}
	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public PostalAdministrationVO findPADetails(String companyCode, String officeOfExchange)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPADetails" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, officeOfExchange);
		PostalAdministrationVO postalAdministrationVO = query.getSingleResult(new PAMasterMapper());
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findPADetails" + " Exiting");
		return postalAdministrationVO;
	}

	/** 
	* @author A-3251
	* @param postalAdministrationDetailsVO
	* @throws SystemException
	*/
	public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "validatePoaDetails" + " Entering");
		int idx = 0;
		String createQuery = null;
		String dynamicQuery = null;
		String finalCreateQuery = null;
		createQuery = getQueryManager().getNamedNativeQueryString(VALIDATE_POADETAILS);
		if (isOracleDataSource()) {
			dynamicQuery = "AND ? BETWEEN VLDFRM AND VLDTOO ";
		} else {
			dynamicQuery = "AND cast(? as timestamp ) BETWEEN VLDFRM AND VLDTOO ";
		}
		finalCreateQuery = String.format(createQuery, dynamicQuery);
		Query qry = getQueryManager().createNativeQuery(finalCreateQuery);
		qry.setParameter(++idx, postalAdministrationDetailsVO.getCompanyCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getPoaCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getParCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getValidFrom().format(DB_DATE_TIME_FORMATTER));
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "validatePoaDetails" + " Exiting");
		return qry.getSingleResult(new ValidatePoaDetailsMapper());
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findAllPACodes(com.ibsplc.icargo.business.mailtracking.mra.gpabilling.vo.GenerateInvoiceFilterVO) Added by 			: A-4809 on 08-Jan-2014 Used for 	:	ICRD-42160 Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws PersistenceException {
		log.debug(MODULE + " : " + "FindAllPAcodes" + " Entering");
		Query query = null;
		String baseQuery = null;
		baseQuery = getQueryManager().getNamedNativeQueryString(MAILTRACKING_DEFAULTS_FINDALLPOACODES);
		query = new FindAllPAFilterQuery(generateInvoiceFilterVO, baseQuery);
		return query.getResultList(new PostalAdministrationMapper());
	}
	/**
	* Overriding Method	:@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO #findAgentCodeForPA(java.lang.String, java.lang.String) Added by 			: U-1267 on Nov 1, 2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAgentCodeForPA" + " Entering");
		Query query = getQueryManager().createNamedNativeQuery(FIND_AGENT_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, officeOfExchange);
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findAgentCodeForPA" + " Exiting");
		return query.getSingleResult(getStringMapper("PARVAL"));
	}

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForIntPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findMailServiceLevelForInternational" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? "
				+ "AND (MALCLS = ? AND MALSUBCLS = '-') "
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? AND MALSUBCLS = ? AND MALCLS = ?)");
		query.append(" UNION ALL ");
		query.append(
				"SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD  = ? AND MALCTG    = '-' AND MALCLS  = ? AND MALSUBCLS = ? "
						+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD  = ? AND MALCTG    = ? "
						+ "AND ((MALCLS = ? and MALSUBCLS = ?) OR (MALCLS  = ? AND MALSUBCLS = '-')))");
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		return query.getSingleResult(getStringMapper("MALSRVLVL"));
	}

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForDomPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findMailServiceLevelForDomestic" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? "
				+ "AND ((MALCTG    = ? AND MALSUBCLS = '-') OR (MALCTG    = '-' AND MALSUBCLS = ?))"
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? AND MALSUBCLS = ? AND MALCTG    = ?)");
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? "
				+ "AND MALCTG    = '-' AND MALSUBCLS = '-' "
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? AND ((MALCTG    = ? AND MALSUBCLS = ?) "
				+ "OR (MALCTG    = ? AND MALSUBCLS = '-') OR (MALCTG    = '-' AND MALSUBCLS = ?)) )");
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		return query.getSingleResult(getStringMapper("MALSRVLVL"));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listPostalCalendarDetails(com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO) Added by 			: A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "listPostalCalendarDetails" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_POSTAL_CAL_DETAILS);
		if (uSPSPostalCalendarFilterVO.getCalValidFrom() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))) {
			if (USPSPostalCalendarFilterVO.FLAG_NO.equals(uSPSPostalCalendarFilterVO.getListFlag())) {
				qry = qry.append(" AND trunc(PRDFRM) <=?");
			} else {
				qry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(PRDFRM),'YYYYMMDD')) >= ? ");
			}
		}
		if (uSPSPostalCalendarFilterVO.getCalValidTo() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))) {
			if (USPSPostalCalendarFilterVO.FLAG_NO.equals(uSPSPostalCalendarFilterVO.getListFlag())) {
				qry = qry.append(" AND trunc(PRDTOO) >=?");
			} else {
				qry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(PRDTOO),'YYYYMMDD')) <= ? ");
			}
		}
		int idx = 0;
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getFilterCalender());
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCalPacode());
		//TODO: Neo to correct below code
		if (uSPSPostalCalendarFilterVO.getCalValidFrom() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))) {
			qry.setParameter(++idx, Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidFrom().format(YYMMDD_TIME_FORMATTER)
					));
		}
		if (uSPSPostalCalendarFilterVO.getCalValidTo() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))) {
			qry.setParameter(++idx, Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidTo().format(YYMMDD_TIME_FORMATTER)
			));
		}
		qry.append("ORDER BY PRDFRM, PRDTOO");
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "listPostalCalendarDetails" + " Exiting");
		log.debug("" + "USPS calender query " + " " + qry);
		Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs = qry.getResultList(new USPSPostalCalendarMapper());
		return uSPSPostalCalendarVOs;
	}

	/** 
	* @author A-6986
	* @return
	*/
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(MODULE + " : " + "findMailHandoverDetails" + " Entering");
		StringBuffer masterQuery = new StringBuffer();
		int pagesize = mailHandoverFilterVO.getDefaultPageSize();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDOVER_DETAILS);
		masterQuery.append(queryString);
		PageableNativeQuery<MailHandoverVO> pgNativeQuery = new PageableNativeQuery<MailHandoverVO>(pagesize,0,
				masterQuery.toString(), new MailHandoverMapper(), PersistenceController.getEntityManager().currentSession());
		int index = 0;
		pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getCompanyCode());
		if (mailHandoverFilterVO.getGpaCode() != null && !("").equals(mailHandoverFilterVO.getGpaCode())) {
			pgNativeQuery.append(" AND GPACOD=?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getGpaCode());
		}
		if (mailHandoverFilterVO.getAirportCode() != null && !("").equals(mailHandoverFilterVO.getAirportCode())) {
			pgNativeQuery.append(" AND ARPCOD = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getAirportCode());
		}
		if (mailHandoverFilterVO.getMailClass() != null && !("").equals(mailHandoverFilterVO.getMailClass())) {
			pgNativeQuery.append(" AND MALCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailClass());
		}
		if (mailHandoverFilterVO.getMailSubClass() != null && !("").equals(mailHandoverFilterVO.getMailSubClass())) {
			pgNativeQuery.append(" AND MALSUBCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailSubClass());
		}
		if (mailHandoverFilterVO.getExchangeOffice() != null
				&& !("").equals(mailHandoverFilterVO.getExchangeOffice())) {
			pgNativeQuery.append(" AND EXGOFC = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getExchangeOffice());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}

	/** 
	* @author A-6986
	* @param contractFilterVO
	* @return
	*/
	public Collection<GPAContractVO> listContractdetails(GPAContractFilterVO contractFilterVO)
			throws PersistenceException {
		log.debug(MODULE + " : " + "listContractdetails" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTRACT_DETAILS);
		int index = 0;
		qry.setParameter(++index, contractFilterVO.getCompanyCode());
		qry.setParameter(++index, contractFilterVO.getPaCode());
		if (contractFilterVO.getOrigin() != null && contractFilterVO.getOrigin().trim().length() > 0) {
			qry.append("AND ORGARP = ? ");
			qry.setParameter(++index, contractFilterVO.getOrigin());
		}
		if (contractFilterVO.getDestination() != null && contractFilterVO.getDestination().trim().length() > 0) {
			qry.append("AND DSTARP = ? ");
			qry.setParameter(++index, contractFilterVO.getDestination());
		}
		if (contractFilterVO.getContractID() != null && contractFilterVO.getContractID().trim().length() > 0) {
			qry.append("AND CTRIDR LIKE ? ");
			qry.setParameter(++index, contractFilterVO.getContractID());
		}
		if (contractFilterVO.getRegion() != null && contractFilterVO.getRegion().trim().length() > 0) {
			qry.append("AND REGCOD = ? ");
			qry.setParameter(++index, contractFilterVO.getRegion());
		}
		Collection<GPAContractVO> contractVOs = qry.getResultList(new GPAContractMapper());
		return contractVOs;
	}

	/** 
	* @author A-6986
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findIncentiveConfigurationDetails" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_INCENTIVE_CONFIGURATION_DETAILS);
		int index = 0;
		qry.setParameter(++index, incentiveConfigurationFilterVO.getCompanyCode());
		qry.setParameter(++index, incentiveConfigurationFilterVO.getPaCode());
		Collection<IncentiveConfigurationVO> incentiveVOs = qry.getResultList(new MailIncentiveDetailsMultiMapper());
		return incentiveVOs;
	}

	/** 
	* @author A-8464
	* @return serviceStandard
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findServiceStandard(MailbagVO mailbagVo) throws PersistenceException {
		log.debug(MODULE + " : " + "findServiceStandard" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_SERVICE_STANDARD);
		int index = 0;
		if (mailbagVo.getCompanyCode() != null && mailbagVo.getCompanyCode().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getCompanyCode());
		}
		if (mailbagVo.getPaCode() != null && mailbagVo.getPaCode().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getPaCode());
		}
		if (mailbagVo.getOrigin() != null && mailbagVo.getOrigin().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getOrigin());
		}
		if (mailbagVo.getDestination() != null && mailbagVo.getDestination().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getDestination());
		}
		if (mailbagVo.getMailServiceLevel() != null && mailbagVo.getMailServiceLevel().trim().length() > 0) {
			qry.setParameter(++index, mailbagVo.getMailServiceLevel());
		}
		if (mailbagVo.getConsignmentDate() != null) {
			qry.append("AND  ?  BETWEEN SRVSTDCFG.VLDFRMDAT AND SRVSTDCFG.VLDTOODAT ");
			qry.setParameter(++index, mailbagVo.getConsignmentDate());
		}
		int serviceStandard = 0;
		String serviceStdString = qry.getSingleResult(getStringMapper("SRVSTD"));
		if (serviceStdString != null && serviceStdString.trim().length() > 0) {
			serviceStandard = Integer.parseInt(serviceStdString);
		}
		log.debug(MODULE + " : " + "findServiceStandard" + " Exiting");
		return serviceStandard;
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findRotingIndex(java.lang.String, java.lang.String) Added by 			: A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routeIndex Parameters	:	@param companycode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	@Override
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		int index = 0;
		Collection<RoutingIndexVO> routingIndexVOs = null;
		String qry = getQueryManager().getNamedNativeQueryString(FIND_ROUTING_INDEX);
		String modifiedStr1 = null;
		String modifiedStr2 = null;
		if (isOracleDataSource()) {
			modifiedStr1 = "AND syspar.parval = arlmst.arlidr";
		} else {
			modifiedStr1 = "AND arlmst.arlidr :: varchar = syspar.parval";
		}
		if (isOracleDataSource()) {
			modifiedStr2 = "and ? BETWEEN idx.pldeffdat-1 and idx.plddisdat";
		} else {
			modifiedStr2 = "and ? BETWEEN date(idx.pldeffdat)-1 and date(idx.plddisdat)";
		}
		qry = String.format(qry, modifiedStr1, modifiedStr2);
		Query qery = getQueryManager().createNativeQuery(qry);
		qery.setParameter(++index, routingIndexVO.getRoutingIndex());
		qery.setParameter(++index, routingIndexVO.getCompanyCode());
		if (routingIndexVO.getScannedDate() == null) {
			ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
			routingIndexVO.setScannedDate(currentDate);
		}
		if (isOracleDataSource()) {
			qery.setParameter(++index, routingIndexVO.getScannedDate().toLocalDate());
		} else {
			qery.setParameter(++index, routingIndexVO.getScannedDate().toLocalDateTime());
		}
		routingIndexVOs = qery.getResultList(new RoutingIndexDetailsMapper());
		return routingIndexVOs;
	}
	/** 
	* / Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listPostalCalendarDetails(com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO) Added by 			: A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "validateFrmToDateRange" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_POSTAL_CAL_DETAILS);
		if (uSPSPostalCalendarFilterVO.getCalValidFrom() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))) {
			qry = qry.append(" AND to_number(to_char(PRDFRM,'YYYYMMDD')) IN ( ? )");
		}
		if (uSPSPostalCalendarFilterVO.getCalValidTo() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))) {
			qry = qry.append(" AND to_number(to_char (PRDTOO,'YYYYMMDD')) IN (? )");
		}
		int idx = 0;
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx, "I");
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCalPacode());
		//TODO: Neo to correct below code
		if (uSPSPostalCalendarFilterVO.getCalValidFrom() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))) {
			qry.setParameter(++idx, Integer
					.parseInt(uSPSPostalCalendarFilterVO.getCalValidFrom().format(YYMMDD_TIME_FORMATTER)));
		}
		if (uSPSPostalCalendarFilterVO.getCalValidTo() != null
				&& !(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))) {
			qry.setParameter(++idx,
					Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidTo().format(YYMMDD_TIME_FORMATTER)));
		}
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "validateFrmToDateRange" + " Exiting");
		log.debug("" + "USPS calender query " + " " + qry);
		Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs = qry.getResultList(new USPSPostalCalendarMapper());
		return uSPSPostalCalendarVOs;
	}

	/** 
	* @author A-7371
	* @param uspsPostalCalendarFilterVO
	* @return	USPSPostalCalendarVO
	*/
	public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)
			throws PersistenceException {
		log.debug("MailTrackingDefaultsSqlDAO" + " : " + "findInvoicPeriodDetails" + " Entering");
		int idx = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_INVOIC_PERIOD_DETAILS);
		qry.setParameter(++idx, uspsPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx, uspsPostalCalendarFilterVO.getCalPacode());
		qry.setParameter(++idx, uspsPostalCalendarFilterVO.getCalendarType());
		//TODO: Neo to correct below code
		//qry.setParameter(++idx, uspsPostalCalendarFilterVO.getInvoiceDate().toSqlDate().toString().replace("-", ""));
		USPSPostalCalendarVO uspsPostalCalendarVO = qry.getSingleResult(new InvoicPeriodDetailsMapper());
		return uspsPostalCalendarVO;
	}

	/**
	* Added as a part of ICRD-318999. This method is to pick up mailbox id according to the parameters given.
	*/
	public String findMailboxIdFromConfig(MailbagVO mailbagVO) {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOXID_FROM_CONFIG);
		query.setParameter(++index, mailbagVO.getOoe());
		query.setParameter(++index, mailbagVO.getDoe());
		query.setParameter(++index, mailbagVO.getOrigin());
		query.setParameter(++index, mailbagVO.getDestination());
		query.setParameter(++index, mailbagVO.getMailCategoryCode());
		query.setParameter(++index, mailbagVO.getMailSubclass());
		String xxResdit = "Y";
		if (null != mailbagVO.getConsignmentNumber()) {
			xxResdit = "N";
		}
		query.setParameter(++index, xxResdit);
		if (mailbagVO.getCarrierCode() == null) {
			query.setParameter(++index, mailbagVO.getCompanyCode());
		} else {
			query.setParameter(++index, mailbagVO.getCarrierCode());
		}
		String mailboxId = query.getSingleResult(getStringMapper("MALBOXID"));
		return mailboxId;
	}

	@Override
	public Collection<MailEventVO> findMailEvent(MailEventPK maileventPK) {
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findMailEvent" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOX_EVENT);
		query.setParameter(++index, maileventPK.getCompanyCode());
		query.setParameter(++index, maileventPK.getMailboxId());
		List<MailEventVO> mailEventVOs = query.getResultList(new MailboxIdMapper());
		log.debug(MAIL_TRACKING_DEFAULTS_SQLDAO + " : " + "findMailEvent" + " Exiting");
		return mailEventVOs;
	}

	/** 
	* @author A-8672
	* @return handovertime
	*/
	public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws PersistenceException {
		log.debug(MODULE + " : " + "findMailHandoverDetails" + " Entering");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_HANDOVER_DETAILS);
		int index = 0;
		qry.setParameter(++index, mailHandoverVO.getCompanyCode());
		if (mailHandoverVO.getGpaCode() != null && !("").equals(mailHandoverVO.getGpaCode())) {
			qry.append(" AND GPACOD=?");
			qry.setParameter(++index, mailHandoverVO.getGpaCode());
		}
		if (mailHandoverVO.getHoAirportCodes() != null && !("").equals(mailHandoverVO.getHoAirportCodes())) {
			qry.append(" AND ARPCOD = ?");
			qry.setParameter(++index, mailHandoverVO.getHoAirportCodes());
		}
		if (mailHandoverVO.getMailClass() != null && !("").equals(mailHandoverVO.getMailClass())) {
			qry.append(" AND MALCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailClass());
		}
		if (mailHandoverVO.getMailSubClass() != null && !("").equals(mailHandoverVO.getMailSubClass())) {
			qry.append(" AND MALSUBCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailSubClass());
		}
		if (mailHandoverVO.getExchangeOffice() != null && !("").equals(mailHandoverVO.getExchangeOffice())) {
			qry.append(" AND EXGOFC = ?");
			qry.setParameter(++index, mailHandoverVO.getExchangeOffice());
		}
		log.debug(MODULE + " : " + "findMailHandoverDetails" + " Exiting");
		return qry.getSingleResult(getStringMapper("HNDTIM"));
	}
	/**
	* Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailboxIdForPA(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO) Added by 			: A-8061 on 20-Jul-2020 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	*/
	public String findMailboxIdForPA(MailbagVO mailbagVO) throws PersistenceException {
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBOX_FORPA);
		int index = 0;
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getPaCode());
		return qry.getSingleResult(getStringMapper("MALBOXIDR"));
	}

	/** 
	* @param companyCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	public Collection<PostalAdministrationVO> getPADetails(String companyCode) throws PersistenceException {
		log.debug(MODULE + " : " + "getPADetails" + " Entering");
		String qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_PA_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);
		log.debug(MODULE + " : " + "getPADetails" + " Exiting");
		return query.getResultList(new PAMasterDataDetailsMapper());
	}

	@Override
	public Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) throws PersistenceException {
		log.debug(MODULE + " : " + "getOfficeOfExchangeDetails" + " Entering");
		String qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_OFFICE_OF_EXCHANGE_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);
		log.debug(MODULE + " : " + "getOfficeOfExchangeDetails" + " Exiting");
		return query.getResultList(new OfficeOfExchangeMapper());
	}

	/** 
	* @author 204084Added as part of CRQ IASCB-164529
	* @param destinationAirportCode
	* @return
	* @throws SystemException
	*/
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(String destinationAirportCode) {
		log.debug(MODULE + LOG_DELIMITER + " : " + "getPlannedRoutingIndexDetails" + " Entering");
		Collection<RoutingIndexVO> routingIndexVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(GET_PLANNED_ROUTING_INDEX_DETAILS);
		if (isOracleDataSource()) {
			qry.append(" where sysdate between pldeffdat and plddisdat and dstcod = ? ");
		} else {
			qry.append(" where current_date between pldeffdat and plddisdat and dstcod = ? ");
		}
		qry.setParameter(++index, destinationAirportCode);
		routingIndexVOs = qry.getResultList(new RoutingIndexDetailsMapper());
		log.debug(MODULE + LOG_DELIMITER + " : " + "getPlannedRoutingIndexDetails" + " Exiting");
		return routingIndexVOs;
	}

	/** 
	* @param mailMasterDataFilterVO
	* @return MailbagDetailsVo
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159267 on 20-Oct-2022
	*/
	@Override
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws PersistenceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(MODULE + " : " + "getMailbagDetails" + " Entering");
		String qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_MAILBAG_DETAILS);
		int index = 0;
		if (isOracleDataSource()) {
			qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_MAILBAG_DETAILS_FOR_ORACLE);
		}
		Query query = getQueryManager().createNativeQuery(qryString);
		ZonedDateTime utcCurrentTimestamp = localDateUtil.getLocalDate(null, true);
		int lastScanTime = mailMasterDataFilterVO.getLastScanTime() / 24;
		query.setParameter(++index, mailMasterDataFilterVO.getNoOfDaysToConsider());
		//TODO: Neo to correct below code
		//query.setParameter(++index, localDateUtil.toUTCTime(utcCurrentTimestamp).addDays(-lastScanTime));
		log.debug(MODULE + " : " + "getMailbagDetails" + " Exiting");
		return query.getResultList(new MailbagDetailsForInterfaceMapper());
	}

	/** 
	* @param companyCode
	* @return MailSubClassVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204084Added for IASCB-172483 on 15-Oct-2022
	*/
	public Collection<MailSubClassVO> getSubclassDetails(String companyCode) throws PersistenceException {
		log.debug(MODULE + " : " + "getSubclassDetails" + " Entering");
		String qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_SUBCLASS_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);
		log.debug(MODULE + " : " + "getSubclassDetails" + " Exiting");
		return query.getResultList(new MailSubClassCodeMapper());
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @return OfficeOfExchangeVO
	* @throws SystemException
	* @author 204082Added for IASCB-164537 on 09-Nov-2022
	*/
	public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "getExchangeOfficeDetails" + " Entering");
		String qryString = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_GET_EXCHANGE_OFFICE_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airportCode);
		query.setParameter(++index, airportCode);
		query.setParameter(++index, airportCode);
		log.debug(MODULE + " : " + "getExchangeOfficeDetails" + " Exiting");
		return query.getResultList(new OfficeOfExchangeMapper());
	}
	public PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException {
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_PACODE);


		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, paCode);

		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, paCode);

		return qry.getResultList(new PACodeMultiMapper()).get(0);
	}
	
	public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode, String officeOfExchange, int pageNumber) throws SystemException,
			PersistenceException {

		String exchangeOffice = null;
		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_OFFICEOFEXCHANGECODES);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(qry);
		PageableNativeQuery<OfficeOfExchangeVO> pgqry  = new PageableNativeQuery<OfficeOfExchangeVO>(25,-1,rankQuery.toString(),new OfficeOfExchangeMapper(),PersistenceController.getEntityManager().currentSession());
		int index = 0;
		pgqry.setParameter(++index, companyCode);
		if (officeOfExchange != null && ((officeOfExchange.trim().length() == 6)&& !(officeOfExchange.endsWith("%")))){
			pgqry.append("AND EXGOFCCOD = ?  ");
			pgqry.setParameter(++index, officeOfExchange);
		} else if (officeOfExchange != null
				&& officeOfExchange.trim().length() > 0) {
			exchangeOffice = officeOfExchange.replace(MailConstantsVO.STAR,
					MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			pgqry.append("AND EXGOFCCOD  LIKE ").append(
					new StringBuilder().append("'").append(exchangeOffice)
							.append("'").toString());
		}
		pgqry.append(" ORDER BY EXGOFCCOD ");

		pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(pageNumber);
	}
	public  Collection<String> findOfficeOfExchangesForAirport(
			String companyCode, String airportCode)
			throws SystemException,PersistenceException {
		log.debug(MODULE + " : " + "findOfficeOfExchangesForAirport" + " Entering");
		Collection<String> oEToCurrentAirport = new ArrayList<String>();
		int idx = 0;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_OFFICE_OF_EXCHANGES_FOR_AIRPORT);;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, airportCode);
		log.debug("findOfficeOfExchangeNearToAirport--query--->> {}",query.toString());
		oEToCurrentAirport.addAll(query.getResultList(getStringMapper("EXGOFCCOD")));
		log.debug(MODULE + " : " + "findOfficeOfExchangesForAirport" + " Exiting");
		return oEToCurrentAirport;
	}
	public HashMap<String,String> findOfficeOfExchangeForPA(String companyCode,
															String paCode)
			throws SystemException, PersistenceException{
		log.debug(MODULE + " : " + "findOfficeOfExchangeForPA" + " Entering");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_EXCHANGEOFFICE_FOR_PA);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, paCode);
		return query
				.getResultList(new ExchangeOfficeMultiMapper()).get(0);
	}
	public HashMap<String,String> findCityForOfficeOfExchange(
			String companyCode, Collection<String> officeOfExchanges)
			throws SystemException,PersistenceException{
		log.debug("MailTrackingDefaultsSqlDAO","findCityForOfficeOfExchange");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CITY_FOR_EXCHANGE_OFFICES);
		int idx = 0;
		boolean first = true;
		HashMap<String,String> cityForOE = new HashMap<String, String>();
		query.setParameter(++idx, companyCode);
		if(officeOfExchanges != null &&  officeOfExchanges.size() > 0) {
			query.append(" AND EXGOFCCOD IN ( ?");
			for(String officeOfExchange : officeOfExchanges) {
				if(officeOfExchange!=null)
				{
					if(first) {
						query.setParameter(++idx, officeOfExchange);
						first = false;

					}else {
						query.append(",? ");
						query.setParameter(++idx, officeOfExchange);
					}
				}
			}
			query.append(" ) ");
		}

		return (HashMap<String, String>) query.getResultList(new CityForOEMapper()).get(0);
	}

	@Override
	public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO) {
		log.debug(MODULE + " : " + "findMLDCongfigurations" + " Entering");
		int index = 0;
		Collection<MLDConfigurationVO> mLDConfigurationVOs = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MLD_CONFIGURATIONS);

		qry.setParameter(++index, mLDConfigurationFilterVO.getCompanyCode());
		if (mLDConfigurationFilterVO.getAirportCode() != null
				&& !("").equals(mLDConfigurationFilterVO.getAirportCode())) {
			qry.append(" AND CFG.ARPCOD = ?");
			qry.setParameter(++index, mLDConfigurationFilterVO.getAirportCode());
		}
		if (mLDConfigurationFilterVO.getCarrierIdentifier() > 0) {
			qry.append(" AND CFG.CARIDR = ?");
			qry.setParameter(++index,
					mLDConfigurationFilterVO.getCarrierIdentifier());
		}

		//Added for CRQ ICRD-135130 by A-8061 starts
		if (mLDConfigurationFilterVO.getMldversion() != null
				&& !("").equals(mLDConfigurationFilterVO.getMldversion())) {
			qry.append(" AND CFG.MLDVER = ?");
			qry.setParameter(++index,
					mLDConfigurationFilterVO.getMldversion());
		}
		//Added for CRQ ICRD-135130 by A-8061 end
		log.debug(MODULE + " : " + "findMLDCongfigurations" + " Exiting");
		return qry.getResultList(new MLDConfigurationMapper());
	}


	public String findPAForMailboxID(String companyCode, String mailboxId, String originOE)throws SystemException
	{
		String POACOD = null;
		int index = 0;
		//	log.entering("MailTrackingDefaultsSqlDAO", "findPAForMailboxID");
			Query query = getQueryManager().createNamedNativeQuery(
					FIND_PA_FOR_MAILBOXID);
			query.setParameter(++index, companyCode);
			query.setParameter(++index, mailboxId);
			query.setParameter(++index, originOE);
		//	log.exiting("MailTrackingDefaultsSqlDAO", "findPAForMailboxID");
		  POACOD=query.getSingleResult(getStringMapper("MAX"));
			return POACOD;
		}

  public HashMap<String,String> findAirportForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges) throws SystemException, PersistenceException
	{
		log.debug(MODULE,"findAirportForOfficeOfExchange");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CITY_FOR_EXCHANGE_OFFICES);
		int idx = 0;
		boolean first = true;
		query.setParameter(++idx, companyCode);
		if(officeOfExchanges != null &&  officeOfExchanges.size() > 0) {
			query.append(" AND EXGOFCCOD IN (?");
			for(String officeOfExchange : officeOfExchanges) {
				if(officeOfExchange!=null)
				{
					if(first) {
						query.setParameter(++idx, officeOfExchange);
						first = false;

					}else {
						query.append(",? ");
						query.setParameter(++idx, officeOfExchange);
					}
				}
			}
			query.append(" ) ");
		}

		return (HashMap<String, String>) query.getResultList(new AirportForOEMapper()).get(0);

	}

	public String findUpuCodeNameForPA(String companyCode, String paCode) throws SystemException
	{
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_UPUCODE_NAME_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);

		return query.getSingleResult(getStringMapper("UPUCODNAME"));

	}
	public String findPartyIdentifierForPA(String companyCode,
										   String paCode) throws SystemException{
		log.debug("MailTrackingDefaultsSqlDAO", "findPartyIdentifierForPA");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_PARTYID_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		log.debug("MailTrackingDefaultsSqlDAO", "findPartyIdentifierForPA");
		return query.getSingleResult(getStringMapper("PTYIDR"));
	}
}
