/*
 * MailTrackingDefaultsSqlDAO.java Created on June  29, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.MailEventPK;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.ibsplc.icargo.business.mail.operations.vo.MonitorMailSLAVO.MAILSTATUS_ACCEPTED;
import static com.ibsplc.icargo.business.mail.operations.vo.MonitorMailSLAVO.MAILSTATUS_ARRIVED;

//import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingDetailVO;
//import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFilterVO;
//import com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookedFlightDetailsMapper;
//import com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookingFilterQuery;
//import com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookingMapper;


/**
 * @author a-A5991
 *
 */
public class MailTrackingDefaultsSqlDAO extends AbstractQueryDAO implements
		MailTrackingDefaultsDAO {

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

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
	private static final String FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_CARRIER =
			"mail.operations.findAlreadyAssignedTrolleyNumberForMLDWithCarrier";
	private static final String FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_FLIGHT =
			"mail.operations.findAlreadyAssignedTrolleyNumberForMLDWithFlight";
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
	private static final String FIND_TRANSFERFROMINFO_FROM_CARDIT_FORMAILBAGS="mail.operations.findtransferfrominfo.fromcardit.formailbags";
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
	private static final String  FIND_NUMBER_OF_BARROWS_PRESENT_IN_FLIGHT_OR_CARRIER = "mail.operations.findnumberofbarrowspresentintheflightorcarrier";
	private static final String FIND_PARTNERCARRIERS = "mail.operations.findpartnercarriers";
	private static final String FIND_COTERMINUS_AIRPORTS = "mail.operations.findCoTerminusAirports";
	//private static final String LIST_MAIL_SERVICESTANDARDS = "mail.operations.listServiceStandardDetails";
	private static final String LIST_MAIL_SERVICESTANDARDS = "mail.operations.listServiceStandardDetails";
	private static final String LIST_RDTDETAILS = "mail.operations.findRdtMasterDetails";
	private static final String VALIDATE_COTERMINUS_AIRPORTS = "mail.operations.validateCoTerminusAirports";
	private static final String FIND_MAIL_SEQUENCE_NUMBER = "mail.operations.findMailSequenceNumber";
	private static final String CHECK_CONSIGNMENT_DOCUMENT_EXISTS = "mail.operations.checkConsignmentDocumentExists";
	private static final String FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG = "mail.operations.findconsignmentdetailsformailbag";
	private static final String FIND_CONSIGNMENT_ROUTING_INFOS= "mail.operations.findConsignmentRoutingInfos";
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
	private static final String FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR="mail.operations.findmailsequencenumberfrommailidr";
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
	private static final String FIND_ULDS_INFLIGHT_FOR_MANIFEST="mail.operations.findcontainersinflightformanifest";
	private static final String FIND_MAILBAGS_MANIFEST_ULD="mail.operations.findmanifestedmailsinuld";
	private static final String FIND_MAILBAGS_MANIFEST_BULK="mail.operations.findmanifestedmailsinbulk";
	private static final String FIND_MAILBAGS_IMPORT_MANIFEST_ULD="mail.operations.findimportmanifestedmailsinuld";
	private static final String FIND_MAILBAGS_IMPORT_MANIFEST_BULK="mail.operations.findimportmanifestedmailsinbulk";
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
	private static final String FIND_MAILNOXID_LOV="mail.operations.findmailboxidlov";
	private static final String FIND_MAILTAG = "mail.operations.findmailtag";
	private static final String FIND_MAIL_TRANSFERMANIFEST="mail.operations.findtransfermanifest";
	private static final String FIND_TRANSFER_MANIFEST_DSN_DETAILS = "mail.operations.findtransfermanifestdsndetails";
	private static final String FIND_MAILBAG_DAMAGES = "mail.operations.findmailbagdamages";
	private static final String SEARCHMODE_DEST = "DESTN";
	private static final String FIND_CONTAINERS = "mail.operations.findcontainers";
	private static final String FIND_OFFLOADEDINFO_FORCONTAINER = "mail.operations.findofflodedinfoforcontainer";
	private static final String MAILS_FOR_TRANSHIPMENT_FLIGHT = "mail.operations.mailsfortranshipmentflight";
	private static final String FIND_MAIL_WITHOUT_CARDITS = "mail.operations.findmailswithoutcardits";
	private static final String FIND_MAILSTATUS_CARDIT_NOT_ACCEPTED="mail.operations.mailstatus.carditnotaccepted";
	private static final String FIND_MAIL_ARRIVED_NOT_DELIVERED = "mail.operations.findmailsarrivedandnotdelivered";
	private static final String FIND_MAILSTATUS_MAIL_DELIVERED="mail.operations.mailstatus.mailsdelivered";
	private static final String MAILS_IN_FLIGHT="mail.operations.mailsinflight";
	private static final String MAILS_NOTUPLIFTED_CARRIER="mail.operations.notupliftedmailsincarrier";
	private static final String MAILS_ACCEPTED_NOT_UPLIFTED_FLIGHT="mail.operations.acceptednotupliftedmailsinflight";
	private static final String FIND_MAILSTATUS_MAIL_DELIVERED_WITHOUT_CARDIT ="mail.operations.mailstatus.mailsdeliveredwithoutcardit";
	private static final String MAILS_ACCEPTED_NOT_UPLIFTED_CARRIER="mail.operations.acceptednotupliftedmailsincarrier";
	private static final String FIND_ULDS_FOR_ARRIVAL = "mail.operations.finduldsforarrival";
	private static final String FIND_MAILDETAILS_FORUNSENTRESDITS = "mail.operations.findmaildetailsforunsentresdits";
	private static final String FIND_PARTY_NAME = "mail.operations.findpartyname";
	private static final String FIND_OFFLOADREASON_FOR_MAILBAG = "mail.operations.findoffloadreasonformailbag";
	private static final String FIND_OFFLOADREASON_FOR_CONTAINER = "mail.operations.findoffloadreasonforcontainer";
	private static final String FIND_MAILBAG_DAMAGE_REASON = "mail.operations.finddamagereasonformailbag";
	private static final String FIND_CC_SENDRESDIT = "mail.operations.findCCForSendResdit";
	private static final String FIND_RPT_FOR_XXMAILBAGS= "mail.operations.findrecepientforxxmails";
	private static final String FIND_RPT_FOR_XXULDS = "mail.operations.findrecepientforxxulds";
	private static final String MAILTRACKING_DEFAULTS_GENERATE_DAILYMAILSTATION_REPORT="mail.operations.generatedailymailstationreport";
	private static final String FIND_PALOV = "mail.operations.findpalov";
	private static final String MAILTRACKING_DEFAULTS_FINDDSNMAILBAG = "mail.operations.finddsnmailbagsforflight";
	private static final String FIND_PA_DETAILS = "mail.operations.findPADetails";
	private static final String VALIDATE_POADETAILS = "mail.operations.validatepoadetails";
	private static final String MAILTRACKING_DEFAULTS_FINDALLPOACODES="mail.operations.findallpoacodes";
	private static final String SCANNED_MAIL_HHT_DETAILS_OPERATION = "mail.operations.findScannedMailDetails";
	private static final String PERFORM_UPLOAD_CRCTN = "mail.operations.performuploadcorrection";
	private static final String FIND_MAIL_HANDED_OVER_TOFLIGHT = "mail.operations.findmailhandedovertoflight";
	private static final String FIND_MAIL_HANDED_OVER_TOCARRIER = "mail.operations.findmailhandedovertocarrier";
	private static final String FIND_IMPORT_MANIFEST_DETAILS = "mail.operations.findimportmanifestdetails";
	private static final String FIND_ONLINEFLIGHTS_CONTAINERS="mail.operations.findOnlineFlightsAndConatiners";
	private static final String FIND_FLIGHTS_ARRIVAL="mail.operations.findflightsforArrival";
	private static final String FIND_MAILBAGS_ARRIVAL="mail.operations.findMailbagsforArrival";
	private static final String FIND_FLIGHTSFOR_MAILCLOSURE = "mail.operations.findFlightForMailOperationClosure";
	private static final String FIND_ANYCONTAINER_IN_ASSIGNED_FLIGHT = "mail.operations.findanycontainerinassignedflight";
	private static final String MAILTRACKING_DEFAULTS_FINDIMPORTFLIGHTSFORARRIVAL ="mail.operations.findimportflightsforarrival";
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
	private static final String FIND_OPERATIONFLIGHTS_MRD="mail.operations.findOperationalFlightForMRD";
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
	private static final String FIND_MAILONHANDLISTFLIGHT="mail.operations.findMailOnHandDetailsFlight";
	private static final String FIND_MAILONHANDLIST="mail.operations.findMailOnHandDetails";
	private static final String FIND_MAILONHANDLIST_PARTTWO="mail.operations.findMailOnHandDetailsparttwo";
	private static final String FIND_MAILONHANDLISTCARRIER="mail.operations.findMailOnHandDetailsCarrier";
	//Added as part of CRQ ICRD-204806 starts
	private static final String PROCESS_MAIL_OPERATIONS_FROM_FILE="mail.operations.processMailOperationFromFile";
	private static final String FETCH_DATA_FOR_OFFLOAD_UPLOAD="mail.operations.fetchDataForOfflineUpload";
	private static final String REMOVE_DATA_FROM_TEMPTABLE="mail.operations.removeDataFromTempTable";
	//Added as part of CRQ ICRD-204806 ends
	private static final String FIND_CONAUDIT = "mail.operations.findconaudit";
	private static final String FIND_MAILBAGID_FOR_MAIL_TAG="mail.operations.findmailbagidformailtag";//Added for ICRD-205027
	private static final String MAILTRACKING_DEFAULTS_MAIL_AUDITHISTORY_DETAILS ="mail.operations.findmailaudithistorydetails";
	private static final String MAILBAG_AUDIT_FINDAUDITTRANSACTIONCODES = "mail.operations.findaudittransactioncodes";
	private static final String FIND_MAILBAGCOUNTINCONTAINER = "mail.operations.findmailbagcountincontainer";//added by a-7871 for ICRD-257316
	private static final String FIND_EXCHANGEOFFICE_FOR_PA="mail.operations.findexchangeofficeforpa";//Added for ICRD-212903
	private static final String FIND_AWB_ATTACHED_MAIL_BAG_DETAILS = "mail.operations.findAwbAtachedMailbagDetails";
	private static final String FIND_MAILBAGS_FORREASSIGN="mail.operations.findMailBagsforReassign";//added by A-7371 for ICRD-264253
	private static final String FIND_MAILBOX="mail.operations.findmailboxid";
	private static final String FIND_AGENT_FOR_PA = "mail.operations.findAgentCodeOfPostalAuthority";
	private static final String FIND_MAILBAGS_FOR_DSN="mail.operations.findmailbagsfordsn";
	private static final String FIND_MAILTAG_DETAILS="mail.operations.findmailtagdetails";//Added for ICRD-108366

	private static final String FIND_MAILBAGEVENTS = "mail.operations.findMailbagResditEvents";//a-8061 added for ICRD-248704

	private static final String FIND_GRAND_TOTAL = "mail.operations.findmailbagtotals";//a-8061 added for ICRD-233692

	private static final String FIND_MAIL_SERVICE_LEVEL = "mail.operations.findmailservicelevel";//a-6986 added for ICRD-243469

	private static final String GENERATE_CONSIGNMENT_DETAILS_REPORT_FOR_AV7 = "mail.operations.generateconsignmentdetailsreportforAV7";//added for ICRD-212235

	private static final String FIND_POSTAL_CAL_DETAILS = "mail.operations.finduspspostalcalendardetails";// Added by A-8164 for ICRD-236925
	private static final String FIND_MAIL_HANDOVER_DETAILS = "mail.operations.findMailHandoverDetails";
	private static final String FIND_FLIGHT_STA="mail.operations.findscheduledarrivaltimeforflight";//added by A-7371
	private static final String FIND_HANDOVERTIME="mail.operations.findhandovertime";//added by A-7371
	private static final String FIND_RDTOFFSET="mail.operations.findrdtoffset";//added by A-7371


	private static final String FIND_CONTRACT_DETAILS = "mail.operations.findgpacontractdetails"; //added for ICRD-252821
	private static final String CHECK_RECIEVEFRMTRUCK_ENABLED = "mail.operations.checkReceivedFromTruckEnabled";//Added by a-7871 for ICRD-240184

	private static final String FIND_AWB_PARTIAL_OFL_PCS = "mail.operations.findAwbPartialOflPcs";

	private static final String FIND_CARDIT_DETAILS = "mail.operations.findcarditdetails";

	private static final String  FIND_MAILBAGS_FOR_AWB="mail.operations.findMailbagsForAWB";
	private static final String  FIND_MAIL_BOOKING_COUNT="mail.operations.findMailBookingCount";
	private static final String FIND_AWBATTACHED_MAILS="mail.operations.findAWBAttachedMailbags";
	private static final String FIND_EMPTY_ULDS_IN_MAIL_FLIGHT="mail.operations.findEmptyULDsInMailFlight";
	private static final String FIND_CARDIT_DSN_DETAILS ="mail.operations.findcarditdsndetails";



	private static final String OUTBOUND_FIND_FLIGHT_FOR_PREADVICE="mail.operations.findoutboundflightsdetailsforpreadvice";
	private static final String OUTBOUND_FIND_FLIGHTDETAILS="mail.operations.findoutboundlistflightdetails";
	// private static final String  FIND_OUTBOUND_FLIGHT_CONTAINER_DETAILS="mail.operations.findflightcontainerdetails";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW="mail.operations.findmailbagsinulddsnview";
	private static final String FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW="mail.operations.findmailbagsinbulkdsnview";
	private static final String FIND_MAILBAGS_IN_FLIGHT_ULD="mail.operations.findmailbagsincontaineruld";
	private static final String FIND_MAILBAGS_IN_FLIGHT_BULK="mail.operations.findmailbagsincontainerbulk";
	private static final String FIND_CARDIT_SUMMARY_DETAIL="mail.operations.findcarditssummaryview";
	//  private static final String FIND_CARDIT_GROUP_VIEW="mail.operations.findcarditsgroupview";
	private static final String FIND_CARDIT_GROUP_VIEW_ACCEPTED="mail.operations.findcarditsgroupviewacceptedcount";
	private static final String FIND_CARDIT_GROUP_VIEW_COUNT="mail.operations.findcarditsgroupviewcount";
	private static final String FIND_LYINGLIST_SUMMARY_DETAIL="mail.operations.findlyinglistssummaryview";
	private static final String FIND_CAPTURE_NOTACCEPTED_SUMMARY_DETAIL="mail.operations.findcapturednotaccpetedmailbagssummaryview";
	private static final String OUTBOUND_FIND_CARRIER_FOR_ULD="mail.operations.findcarrierlistdetailsuld";
	private static final String OUTBOUND_FIND_CARRIER_FOR_BULK="mail.operations.findcarrierlistdetailsbulk";
	private static final String OUTBOUND_FIND_CARRIER_FOR_EMPTY="mail.operations.findcarrierlistdetailsempty";
	//carrier containerdetails
	private static final String FIND__OUTBOUND_CARRIER_CONTAINERDETAILS_ULD="mail.operations.findcarriercontainersinuld";
	private static final String FIND_OUTBOUND_CARRIER_CONTAINERDETAILS_BULK="mail.operations.findcarriercontainetsinbulk";
	//carrier mailbagdetails
	private static final String FIND_MAILBAGS_CARRIER_ULD_DSNVIEW="mail.operations.findmailbagsincarrierdsnview";
	private static final String FIND_MAILBAGS_CARRIER_BULK_DSNVIEW="mail.operations.findmailbagsincarrierbulkdsnview";
	private static final String FIND_MAILBAGS_IN_CARRIER_ULD="mail.operations.findmailbagsincarrieruld";
	private static final String FIND_MAILBAGS_IN_CARRIER_BULK="mail.operations.findmailbagsincarrierbulk";
	private static final String FIND_MAILBAGS_INSIDE_FLIGHT_BULK="mail.operations.findmailbagsinbulkuld";

	private static final String FIND_FLIGHTS_FOR_INBOUND1 = "mail.operations.findinboundflightdetailsprefix";//Added by A-8164 for mail inbound starts
	private static final String FIND_FLIGHTS_FOR_INBOUND2 = "mail.operations.findinboundflightdetailssuffix";
	private static final String FIND_FLIGHTS_FOR_INBOUND3 = "mail.operations.findinboundnewflightdetails";
	private static final String FIND_MANIFEST_DETAILS = "mail.operations.findinboundmanifestdetails";
	private static final String FIND_MANIFEST_DETAILS2 = "mail.operations.findinboundmanifestdetailssuffix";
	private static final String FIND_INBOUND_CONTAINER_DETAILS = "mail.operations.findinboundflightcontainerdetails";
	private static final String FIND_INBOUND_MAILBAG_DETAILS = "mail.operations.findinboundmailbagdetails";
	private static final String FIND_INBOUND_DSN_DETAILS = "mail.operations.findinbounddsndetails";//Added by A-8164 for mail inbound ends
	private static final String FIND_DSN_OFFLOAD_DETAILS = "mail.operations.findoffloaddsn";  //Added by A-7929 as part of IASCB-2078


	private static final String FIND_INCENTIVE_CONFIGURATION_DETAILS = "mail.operations.findIncentiveConfigurationDetails";//Added for ICRD-232361
	//Added by A-5526 for ICRD-239811 starts
	private static final String MAIL_OPERATIONS_FIND_OUTBOUNDRUNNERFLIGHTS = "mail.operations.findOutboundRunnerFlights";
	private static final String MAIL_OPERATIONS_FIND_INBOUNDRUNNERFLIGHTS = "mail.operations.findInboundRunnerFlights";
	private static final String MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS = "mail.operations.findRefusedRunnerFlights";
	//Added by A-5526 for ICRD-239811 ends
	private static final String FIND_CLOSEOUT_DATE = "mail.operations.findcloseoutdate";//Added by A-8464 for ICRD-240360
	private static final String FIND_SERVICE_STANDARD = "mail.operations.findservicestandard";//Added by A-8464 for ICRD-240360
	private static final String MAIL_OPERATIONS_FIND_MAILBAGWITHCONSIGNMENTFORTRUCK = "mail.operations.findMailbagsWithConsignmentForTruckFlight"; //Added by A-7929 as part of ICRD-241437
	private static final String MAIL_OPERATIONS_FIND_ALLMAILBAGWITHCONSIGNMENTFORTRUCK = "mail.operations.findAllMailbagsForTruckFlight"; //Added by A-7929 as part of ICRD-241437
	private static final String FIND_ROUTING_INDEX="mail.operations.findRoutingIndex";//Added by a-7531
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_OPS = "mail.operations.findforcemajeureapplicablemailsforops";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_CARDIT = "mail.operations.findforcemajeureapplicablemailsforcardit";
	private static final String MAIL_OPERATIONS_SAVE_FORCE_MAJUERE = "mail.operations.saveForceMajeureRequest";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST = "mail.operations.findforcemjeurerequestdetails";
	private static final String MAIL_OPERATIONS_LIST_FORCE_MAJEURE_LOV = "mail.operations.findforcemjeurerequestids";
	private static final String MAIL_OPERATIONS_UPDATE_FORCE_MAJUERE = "mail.operations.updateForceMajeureRequest";
	private static final String FIND_ALL_CONTAINERS_IN_ASSIGNED_FLIGHT="mail.operations.findAllContainersInAssignedFlight";//Added by A_7540
	//Added as part of ICRD-229584
	private static final String GET_MAIL_MANIFESTINFO="mail.operations.getmanifestinfo";
	private static final String FETCH_MAILDATA_FOR_OFFLINE_UPLOAD="mail.operations.fetchMailDataForOfflineUpload";
	private static final String CHECK_SCAN_WAVED_AIRPORT="mail.operations.checkScanWavedAirport";

	private static final String FIND_PERFORMANCE_MAILBAGS_BASE="mail.operations.findPerformanceMailbags";
	private static final String FIND_PERFORMANCE_MAILBAGS_SERVICE_FAILURE_COUNT="mail.operations.findPerformanceMailbagsServiceFailureCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_ONTIMEMAILBAGS_COUNT="mail.operations.findPerformanceMailbagsOntimeMailbagsCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE_COUNT ="mail.operations.findPerformanceMailbagsForceMejureCount";
	private static final String FIND_PERFORMANCE_MAILBAGS_SELECT="mail.operations.findPerformanceMailbagsSelect";
	private static final String FIND_SERVICE_FAILURE_DETAILS="mail.operations.findServiceFailureDetails";
	private static final String FIND_STATION_ONTIME_DETAILS="mail.operations.findStationOntimeDetails";
	private static final String FIND_FORCE_MAJEURE_DETAILS="mail.operations.findForceMajeureMailbagCountDetails";

	private static final String FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE ="mail.operations.findPerformanceMailbagsForceMejure";
	private static final String FIND_SERVICE_FAILURE_MAILBAGS="mail.operations.findServiceFailureMailbags";
	private static final String FIND_STATION_ONTIME_MAILBAGS="mail.operations.findStationOntimeMailbags";
	private static final String FIND_FORCE_MAJEURE_MAILBAGS="mail.operations.findForceMajeureRequestedMailbags";

	private static final String FIND_MAILBAGS_MAILBAGENQUIRY = "mail.operations.findMailbagDetailsForMailbagEnquiryHHT";// Added by A-8464 for ICRD-273761
	private static final String FIND_INVOIC_PERIOD_DETAILS = "mail.operations.findInvoicPeriodDetails";

	private static final String FIND_DUPLICATEMAILBAG_SEQNUM = "mail.operations.findMailbagDetailsForDuplicate";
	private static final String FIND_SERVICERESPONSIVEINDICATOR = "mail.operations.findServiceResponsiveIndicator";
	// Added as a part of ICRD-318999
	private static final String FIND_MAILBOXID_FROM_CONFIG = "mail.operations.findMailboxIdFromConfig";
	private static final String FIND_MAILBOX_EVENT = "mail.operations.findmailevent";
	private static final String FIND_TMPCARDITMSGS = "mail.operations.findTempCarditMessages";

	private static final String FIND_DEVIATION_LIST_QUERY_MAIN="mail.operations.findDeviationMailbags_query";
	private static final String FIND_DEVIATION_LIST_QUERY_1="mail.operations.findDeviationMailbags_query1";
	private static final String FIND_DEVIATION_LIST_QUERY_2="mail.operations.findDeviationMailbags_query2";


	private static final String FIND_BULKCOUNT_FLIGHT="mail.operations.findbulkcountforoutbound";
	private static final String  FIND__OUTBOUND_FLIGHT_CONTAINERDETAILS_ULD="mail.operations.findflightuldcontainerdetails";
	private static final String  FIND_OUTBOUND_FLIGHT_CONTAINERDETAILS_BULK="mail.operations.findflightbulkcontainerdetails";

	private static final String  FIND_ROUTING_DETAILS_FOR_CONSIGNMENT="mail.operations.findroutingforconsignment";
	private static final String VALID_CONTAINER_FOR_ARRORDLV="mail.operations.isValidContainerForULDlevelArrivalOrDelivery";
	private static final String MAILBAGS_FORPABUILT_CONTAINERSAVE="mail.operations.findMailbagForPABuiltContainer";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_INBOUND = "mail.operations.findflightassignedmailsinuldinboundforreact";
	private static final String FIND_MAILBAGS_STORAGE_UNIT = "mail.operations.findmailsinstorageunit"; ////added by A-9529 for IASCB-44567

	private static final String CONTAINER_INFO_DEVIATED_MAILBAG="mail.operations.findcontainerinfofordeviatedmailbag";
	private static final String FIND_APPROVED_FORCE_MAJEURE_DETAILS_OF_MAILBAG = "mail.operations.findApprovedForceMajeureDetails";


	private static final String FIND_MAILDETAILS = "mail.operations.findmailDetails";
	private static final String FIND_MAILBOX_FORPA = "mail.operations.findMailBoxForPA";

	private static final String FIND_MAIL_BILLING_STATUS = "mail.operations.findMailbagBillingStatus";

	private static final String FIND_TRANSFER_MANIFEST_CONSIGNMENTDTL = "mail.operations.findTransferManifestConsignmentDetails";
	private static final String FIND_NOT_UPLIFTED_MAILBAGS_FOR_DEVIATION = "mail.operations.findNotupliftedMailsInCarrierforDeviationlist";
	private static final String GET_MAIL_MANIFESTINFO_NXT_SEG="mail.operations.getmanifestinfofornextsegment";
	private static final String MAILBAG_IN_CONTAINER_USED_IN_NEXT_SEG="mail.operations.checkMailbagInContainerAlreadyUsedInNextSegment";
	private static final String FIND_MAILDETAILSFORNEW = "mail.operations.findmaildetailsfornewstatus";
	private static final String FIND_TRANSFER_MANIFEST_ID_DETAILS="mail.operations.findtransfermanifestid";
	private static final String GENERATE_CONSIGNMENT_DETAILS_SUMMARY_REPORT = "mail.operations.consignmentDetailsSummaryReport";
	private static final String MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS_PART2 = "mail.operations.findRefusedRunnerFlightsPart2";
	private static final String MAIL_OPERATIONS_SAVE_FORCE_MAJEURE_REQUEST_FOR_UPLOAD = "mail.operations.saveforcemajeurerequestforupload";
	private static final String FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS = "mail.operations.findInboundFlightOperationsDetails";
	private static final String FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS_ONE = "mail.operations.findInboundFlightOperationsDetails_one";
	private static final String OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT = "mail.operations.findOffloadULDDetailsAtAirport";
	private static final String	FIND_EXPORTFLIGHT_OPERATIONS_DETAILS="mail.operations.findExportFlightOperationsDetails";
	private static final String DATE =	"yyyyMMdd" ;
	private static final String MAIL_OPERATIONS_FETCH_CONSIGNMENT_DETAILS_FOR_UPLOAD = "mail.operations.fetchconsignmentdetailsforupload";
	private static final String MAIL_OPERATIONS_FIND_CONTAINER_JOURNEY_ID = "mail.operations.findcontainerjourneyid";
	private static final String GET_MAILBAGS_ARRIVAL = "mail.operations.getMailbagsforArrival";
	private static final String MAIL_OPERATIONS_FIND_FLIGHT_AUDIT_DETAILS = "mail.operations.findflightauditdetails";
	private static final String MAIL_OPERATIONS_FIND_FLIGHT_PURE_CONTAINER_DETAILS = "mail.operations.findflightPureContainerDetails";
	private static final String MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS = "mail.operations.listmailbagSecurityDetails";
	private static final String MAIL_OPERATIONS_FIND_AGENT_CODE_FROM_UPUCODE= "mail.operations.findagentcodefromupucode";
	private static final String MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS_MAIL_SEQUENCE_NUMBER= "mail.operations.findMailSequenceNumberForMailbagSecurity";
	private static final String LIST_MAILBAG_SECURITY_DETAILS= "listMilbagSecurityDetails";
	private static final String MAIL_OPERATIONS_SELECT_COUNT_FROM_MALCSGCSDDTL = "mail.operations.findScreeningDetails";
	private static final String MAIL_OPERATIONS_FIND_ROUTING_DETAILS= "mail.operations.findRoutingDetailsForPrint";
	private static final String MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG="mail.operations.findRegulatedCarrierForMailbag";
	private static final String MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING="mail.operations.findlatestregulatedagentissuing";
	private static final String MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM="mail.operations.findscreeningmethodwithoutagentsernum";
	private static final String MAIL_OPERATIONS_FIND_AWB_ATTACHED_MAIL_DETAILS= "mail.operations.findMailbagsAttachedToAWBNo";

	private static final String FIND_LATEST_CONTAINER_ASSIGNMENT_FOR_ULDDELIVERY = "mail.operations.findLatestContainerAssignmentForUldDelivery";
	private static final String MAIL_OPERATIONS_FIND_MAILBAG_DETAILS= "mail.operations.findMailbagDetails";
	private static final String MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER="mail.operations.findLoadPlanVersionsForContainer";
	private static final String MAIL_OPERATIONS_FIND_LOADPLAN_DETAILS_FOR_FLIGHT= "mail.operations.findLoadPlanDetails";

	private static final String FIND_CONTAINERS_OUTERQUERY = "mail.operations.findcontainerswithsubclassgrpfilter";
	private static final String MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG="mail.operations.findRAacceptingForMailbag";
	private static final String MAIL_OPERATIONS_FIND_ROUTING_DETAILS_FOR_MAILBAG="mail.operations.findRoutingDetailsForMailBag";
	private static final String FIND_MAILBAGS_FLIGHT_ULD_WITHOUTACCEPTANCE = "mail.operations.findflightassignedmailsinuldwithoutacceptance";
	private static final String FIND_MAILBAGS_FLIGHT_BULK_WITHOUTACCEPTANCE = "mail.operations.findflightassignedmailsinbulkwithoutacceptance";
	public static final String MAIL_TRACKING_DEFAULTS_SQLDAO = "MailTrackingDefaultsSQLDAO";
	public static final String FIND_MAILBAGS_IN_CONTAINER = "findMailbagsInContainer";
	private static final String MAIL_OPERATIONS_GET_PA_DETAILS = "mail.operations.getPADetails"; //added by 204082 for IASCB-159276
	private static final String MAIL_OPERATIONS_GET_OFFICE_OF_EXCHANGE_DETAILS = "mail.operations.getOfficeOfExchangeDetails"; //added by 204083 for IASCB-172488
	private static final String MAIL_OPERATIONS_CHECK_FOR_SECURITYSCREENING_VALIDATIONS="mail.operations.checkforsecurityscreeningvalidations";
	private static final String MAIL_OPERATIONS_GET_MAILBAG_DETAILS = "mail.operations.getMailbagDetails"; //added by 204082 for IASCB-159267
	private static final String MAIL_OPERATIONS_GET_MAILBAG_DETAILS_FOR_ORACLE = "mail.operations.getMailbagDetailsForOracle"; //added by 204082 for IASCB-159267
	private static final String MAIL_OPERATIONS_GET_SUBCLASS_DETAILS = "mail.operations.getSubclassDetails"; //added by 204084 for IASCB-172483
	private static final String MAIL_OPERATIONS_GET_EXCHANGE_OFFICE_DETAILS = "mail.operations.getExchangeOfficeDetails"; //added by 204082 for IASCB-164537
	public static final String GET_PLANNED_ROUTING_INDEX_DETAILS = "mail.operations.getPlannedRoutingIndexDetails";
	public static final String LOG_DELIMITER = "=============>>";


	private static final String QUERY_VALIDITY_CHECK="  AND ?  BETWEEN VLDFRMDAT AND VLDTOODAT  ";

	private static final String GENERATE_CN46_CONSIGNMENT_DOCUMENT_DETAILS_REPORT = "mail.operations.generateCN46consignmentdetailsreport";
	private static final String GENERATE_CN46_CONSIGNMENT_DETAILS_SUMMARY_REPORT = "mail.operations.generateCN46consignmentDetailsSummaryReport";
	private static final String FIND_CN46_TRANSFER_MANIFEST_TDTL = "mail.operations.findCN46TransferManifestDetails";

	private static final String FIND_DENSITY_FACTOR_FOR_PA = "mail.operations.finddensityfactorforpostalauthority";	   
    private static final String MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL = "mail.operations.findapplicableregulationflagformailbag";
	private static final String OUTBOUND_FLIGHT_PREADVICE_DETAILS_NEW="mail.operations.preadvicedetailsforflight";
	private static final String FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB="mail.operations.findflightsformailinboundautoattachawb";
	private static final String FIND_MAILBAG_MLDDETAILS="mail.operations.findMailBagMLDDetails";

	private static final String FIND_MAILTRANSIT = "mail.operations.findmailtransitdetails";
	
	private static final String FIND_MAILBAGNOTES = "mail.operations.findmailbagnotes";
	private static final String FIND_MAILBAGS_MAILINBOUNDHHT = "mail.operations.findMailbagDetailsForMailInboundHHT";

	private static final String FIND_MAIL_CONSUMED_DATA = "mail.operations.findMailConsumed";
	private static final String FIND_MAILBAGS_FOR_PAWB_CREATION = "mail.operation.findMailbagsForPAWBCreation";
	
	private static final String FIND_MSTDOCNUM_FOR_AWB_DETAILS = "mail.operations.findMstDocNumForAWBDetails";
	private static final String FIND_MAIL_RESDIT_ADDTNLADDRESS_DETAILS = "mail.operations.findMailResditAddtnlAddressDetails";
	private static final String GENERATE_CONSIGNMENT_DETAILS_SUMMARY_FORCN46_REPORT = "mail.operations.consignmentDetailsSummaryForCN46Report";
	private static final String GENERATE_CONSIGNMENT_DETAILS_FORCN46_NOCONSIGNMENT_REPORT = "mail.operations.consignmentDetailsForCN46Report.noConsignment";
	private static final String FIND_CONSIGNMENT_ROUTING_FOR_MAILBAG_SCREENING = "mail.operations.findRoutingForMailbags";
	 private static final String FIND_CONTAINER_WEIGHT_CAPTURE = "mail.operations.findcontainerweightcapture";

	/**
	 *
	 * @author a-2518
	 *
	 */
	private static class SLAMapper implements Mapper<MailActivityDetailVO> {

		private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

		private static final String CLASS_NAME = "SLAMapper";

		/**
		 *
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public MailActivityDetailVO map(ResultSet rs) throws SQLException {
			log.entering(CLASS_NAME, "map");
			MailActivityDetailVO mailActivityDetailVo = new MailActivityDetailVO();
			mailActivityDetailVo.setSlaIdentifier(rs.getString("SLAIDR"));
			mailActivityDetailVo.setServiceTime(rs.getInt("SRVTIM"));
			return mailActivityDetailVo;
		}
	}



	/**
	 * @author A-1936 This method is used to find the LastAssignedResditForUld
	 * @param containerDetailVo
	 * @param eventCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public UldResditVO findLastAssignedResditForUld(
			ContainerDetailsVO containerDetailVo, String eventCode)
			throws SystemException, PersistenceException {
		int index = 0;
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO,
				"findLastAssignedResditForUld");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_LASTASSIGNEDRESDIT_FORULD);
		qry.setSensitivity(true);
		qry.setParameter(++index, containerDetailVo.getCompanyCode());
		qry.setParameter(++index, containerDetailVo.getPol());
		qry.setParameter(++index, containerDetailVo.getContainerNumber());
		qry.setParameter(++index, eventCode);
		qry.setParameter(++index, containerDetailVo.getCompanyCode());
		qry.setParameter(++index, containerDetailVo.getPol());
		qry.setParameter(++index, containerDetailVo.getContainerNumber());
		qry.setParameter(++index, eventCode);
		UldResditVO uldResditVO = qry
				.getSingleResult(new ULDResditMapper());
		if (uldResditVO != null) {
			uldResditVO.setCompanyCode(containerDetailVo.getCompanyCode());
			uldResditVO.setEventAirport(containerDetailVo.getPol());
			uldResditVO.setUldNumber(containerDetailVo.getContainerNumber());
			uldResditVO.setEventCode(eventCode);
		}
		return uldResditVO;
	}


	/**
	 * Method : findMLDCongfigurations Added by : A-5526 on Dec 17, 2015 for
	 * CRQ-93584 Used for : Parameters : @param mLDConfigurationFilterVO
	 * Parameters : @return Collection<MLDConfigurationVO> Parameters : @throws
	 * SystemException Parameters : @throws PersistenceException
	 */
	public Collection<MLDConfigurationVO> findMLDCongfigurations(
			MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findScannedMailDetails");
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
		return qry.getResultList(new MLDConfigurationMapper());
	}


	/**
	 * @author A-5991
	 * method finds mailsequence number from MailBag table using mailId and
	 * companycode
	 */
	public long findMailSequenceNumber(String mailId, String companyCode)
			throws SystemException {

		log.entering(MODULE, "findMailSequenceNumber");
		long mailSeqNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR);
		int indx = 0;
		// String uldNumber = mailInConsignmentVO.getUldNumber();
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, mailId);
		//query.setParameter(++indx, companyCode); //added by A-8353 for ICRD-230449
		//query.setParameter(++indx, mailId);

		String seqNum = query.getSingleResult(getStringMapper("MALSEQNUM"));
		if (seqNum != null) {
			mailSeqNumber = Long.parseLong(seqNum);
		}
		log.exiting(MODULE, "findMailSequenceNumber");
		return mailSeqNumber;


	}


	/**
	 * @author A-10504
	 * method checks Screening Details from MALCSGCSDDTL table
	 * using mailId and companyCode
	 */
	public int findScreeningDetails(String mailBagId,String companyCode)
			throws SystemException{
		String result=null;
		Query query = getQueryManager().createNamedNativeQuery(
				MAIL_OPERATIONS_SELECT_COUNT_FROM_MALCSGCSDDTL);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailBagId);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailBagId);
		
		result = query.getSingleResult(getStringMapper("CMYCOD"));
		if (result!=null) {
			return 1;
		}else {
			return 0;
		}
	}
	/**
	 * @author a-1936 This method is used to find out the Mail Bags in the
	 *         Containers
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainer(
			Collection<ContainerDetailsVO> containers) throws SystemException,
			PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<ContainerDetailsVO>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (cont.getFlightSequenceNumber() >= 0) {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_FLIGHT_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_FLIGHT_BULK);
				}

				/*qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
				qry
						.setParameter(++idx,
								MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
				qry.setParameter(++idx, cont.getPol());
				qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
				qry
						.setParameter(++idx,
								MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);*/
			} else {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_CARRIER_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_CARRIER_BULK);
				}
			}
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getLegSerialNumber());
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getContainerNumber());
			qry.setSensitivity(true);
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
				containerForReturn.stream().forEach(containerDetails -> {
					if (cont.getPou()!=null) {
						containerDetails.setTransistPort(cont.getPou());
					}
				});
			}
		}
		log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		return containerForReturn;
	}


	public Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(
			Collection<ContainerDetailsVO> containers) throws SystemException,
			PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (cont.getFlightSequenceNumber() >= 0) {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_FLIGHT_ULD_WITHOUTACCEPTANCE);
				} else {
					if(MailConstantsVO.MAILOUTBOUND_SCREEN.equals(cont.getFromScreen())) {
						qry = getQueryManager().createNamedNativeQuery(
								FIND_MAILBAGS_FLIGHT_BULK);
					}else {
						qry = getQueryManager().createNamedNativeQuery(
								FIND_MAILBAGS_FLIGHT_BULK_WITHOUTACCEPTANCE);	}
				}
			} else {
				if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_CARRIER_ULD);
				} else {
					qry = getQueryManager().createNamedNativeQuery(
							FIND_MAILBAGS_CARRIER_BULK);
				}
			}
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getLegSerialNumber());
			if((!MailConstantsVO.MAILOUTBOUND_SCREEN.equals(cont.getContainerType()))&&(!MailConstantsVO.ACCEPTANCE_FLAG.equals(cont.getAcceptedFlag()))&&(!MailConstantsVO.ULD_TYPE.equals(cont.getContainerType()))) {
				qry.setParameter(++idx, cont.getPou());
			}else {
				qry.setParameter(++idx, cont.getPol());}
			qry.setParameter(++idx, cont.getContainerNumber());
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
			}
		}
		log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		return containerForReturn;
	}
	/**
	 * @author A-2521
	 * For fetching already Stamped flight details for Collection of Mail Events
	 */
	public HashMap<String,Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
			Collection<MailResditVO> mailResditVOs)
			throws SystemException, PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbagEvents");

		if(mailResditVOs == null ||  mailResditVOs.size() <= 0)
		{
			return null;
		}

		Query query = getQueryManager().createNamedNativeQuery(FIND_MALRDTEVTS_FLIGHTDETAILS);
		MailResditVO mailResditVO = ((ArrayList<MailResditVO>)mailResditVOs).get(0);

		int idx = 0;
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getEventCode());

		boolean first = true;

		/*query.append(" AND MALIDR IN ( ?");*/
		query.append(" AND (MALIDR = ? ");
		for(MailResditVO mailEventVO: mailResditVOs){
			if(first) {
				first = false;
				query.setParameter(++idx, mailEventVO.getMailId());
			}else {
				/*query.append(",? ");*/
				query.append("OR MALIDR = ? ");
				query.setParameter(++idx, mailEventVO.getMailId());
			}
		}
		query.append(" ) ");
		query.append(" order by MALIDR,EVTPRT,EVTCOD");

		log.exiting("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbagEvents");
		return query.getResultList(new MailResditDetailsMultiMapper()).get(0);
	}


	/**
	 * @author A-1739 This method is used to findFlaggedResditSeqNum Modified By
	 *         Karthick V .. This method returns the ResditSequence Number
	 *         though it is just required to check wether that Resdit Exists for
	 *         that Particular Event since it can serve as a Dual Purpose Check
	 *         Resdit and also Return the Max Sequence Number if any .. Note::-
	 *         As if now the Dependant code takes single event code but Multiple
	 *         Codes can also be specified if required so that the Method
	 *         handles that too,But it has to be comma Separated ...
	 *
	 * @param mailResditVO
	 * @param isSentCheckNeeded
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkResditExists(MailResditVO mailResditVO,
									 boolean isSentCheckNeeded) throws SystemException,
			PersistenceException {
		log.entering("MailTrackkingDefaultsSQLDAO", "checkResditExists");
		int idx = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_RESDIT_EXISTS);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		//For Received Resdit event port need not be considered
		//while checking if the resdit exists
		//Modified as part of ICRD-273013
		if(!MailConstantsVO.RESDIT_RECEIVED.equals(mailResditVO.getEventCode())){
			query.append(" AND  MMR.EVTPRT = ?");
			query.setParameter(++idx, mailResditVO.getEventAirport());
		}
		if (mailResditVO.getDependantEventCode() != null
				&& mailResditVO.getDependantEventCode().trim().length() > 0) {
			log.log(Log.INFO, "THE DEPENDANT EVENT CODES ARE PRESENT");
			query.append(" AND  MMR.EVTCOD IN  ( ?,");
			query.setParameter(++idx, mailResditVO.getEventCode());
			String[] dependantEventCodes = mailResditVO.getDependantEventCode()
					.split(MailConstantsVO.MALCLS_SEP);
			qryBuilder = new StringBuilder();
			for (String dependantCode : dependantEventCodes) {
				qryBuilder.append("?,");
				query.setParameter(++idx, dependantCode);
			}
			qryBuilder.deleteCharAt(qryBuilder.length() - 1);
			qryBuilder.append(" ) ");

			query.append(qryBuilder.toString());
		} else {
			log.log(Log.INFO, "NO DEPENDANT CODES PRESENT");
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		if (isSentCheckNeeded) {
			query.append(" AND MMR.RDTSND = ? ");
			query.setParameter(++idx, mailResditVO.getResditSentFlag());
		}
		log.exiting("MailTrackkingDefaultsSQLDAO", "checkResditExists");
		return (query.getSingleResult(getLongMapper("SEQNUM")) > 0 ? true
				: false);
	}



	/**
	 * @author A-5991
	 * @param mailResditVO
	 * @param isSentCheckNeeded
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkResditExistsFromReassign(MailResditVO mailResditVO,
												 boolean isSentCheckNeeded) throws SystemException,
			PersistenceException {
		log.entering("MailTrackkingDefaultsSQLDAO", "checkResditExistsFromReassign");
		int idx = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_RESDIT_EXISTSFROMREAASIGN);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		query.setParameter(++idx, mailResditVO.getEventAirport());
		query.setParameter(++idx, mailResditVO.getFlightNumber());
		query.setParameter(++idx, mailResditVO.getFlightSequenceNumber());
		if (mailResditVO.getDependantEventCode() != null
				&& mailResditVO.getDependantEventCode().trim().length() > 0) {
			log.log(Log.INFO, "THE DEPENDANT EVENT CODES ARE PRESENT");
			query.append(" AND  MMR.EVTCOD IN  ( ?,");
			query.setParameter(++idx, mailResditVO.getEventCode());
			String[] dependantEventCodes = mailResditVO.getDependantEventCode()
					.split(MailConstantsVO.MALCLS_SEP);
			qryBuilder = new StringBuilder();
			for (String dependantCode : dependantEventCodes) {
				qryBuilder.append("?,");
				query.setParameter(++idx, dependantCode);
			}
			qryBuilder.deleteCharAt(qryBuilder.length() - 1);
			qryBuilder.append(" ) ");
			query.append(qryBuilder.toString());
		} else {
			log.log(Log.INFO, "NO DEPENDANT CODES PRESENT");
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		if (isSentCheckNeeded) {
			query.append(" AND MMR.RDTSND = ? ");
			query.setParameter(++idx, mailResditVO.getResditSentFlag());
		}
		log.exiting("MailTrackkingDefaultsSQLDAO", "checkResditExists");
		return (query.getSingleResult(getLongMapper("SEQNUM")) > 0 ? true
				: false);
	}


	/**
	 * @author A-4072
	 * For Mail 4
	 * Fetching business time configuration for Arrival Airport
	 * //Added for ICRD-63167 moving Cardit Resdit from QF to Base
	 */
	public HashMap<String, String>  findPAForMailbags(
			Collection<MailbagVO> mailbagVOs)
			throws SystemException, PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "findPAForMailbags");

		if(mailbagVOs == null ||  mailbagVOs.size() <= 0)
		{
			return null;
		}
		HashMap<String,String> paDetailMap = new HashMap<String,String>();

		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_FORMAILBAGS);
		query.setSensitivity(true);
		int idx = 0;
		boolean first = true;
		if(mailbagVOs != null &&  mailbagVOs.size() > 0) {
			query.append("AND MALIDR IN ( ?");
			for(MailbagVO mailbagVO: mailbagVOs){
				if(first) {
					first = false;
					query.setParameter(++idx, mailbagVO.getCompanyCode());
					query.setParameter(++idx, mailbagVO.getMailbagId());
				}else {
					query.append(",? ");
					query.setParameter(++idx, mailbagVO.getMailbagId());
				}
			}
			query.append(" ) ");
		}
		paDetailMap = (HashMap<String,String>)query.getResultList(new PADetailsMultiMapper()).get(0);
		log.exiting("MailTrackingDefaultsSqlDAO", "findPAForMailbags");
		return paDetailMap;
	}


	/**
	 * @author Joji
	 * For fetching flight details
	 */
	public Collection<MailResditVO> findResditFlightDetailsForMailbag(
			MailResditVO mailResditVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbag");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILRESDIT_FLIGHTDETAILS);
		int idx = 0;
		qry.setParameter(++idx, mailResditVO.getCompanyCode());
		qry.setParameter(++idx, mailResditVO.getMailId());
		qry.setParameter(++idx, mailResditVO.getEventCode());
		log.exiting("MailTrackingDefaultsSqlDAO", "findResditFlightDetailsForMailbag");
		return qry.getResultList(new MailResditMapper());
	}

	/**
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#isCarditPresentForMailbag(java.lang.String)
	 */
	public String findCarditForMailbag(String companyCode, String mailbagId)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_IS_CARDIT_PRESENT_FOR_MAIL);
		query.setParameter(1, companyCode);
		query.setParameter(2, mailbagId);
		return query.getSingleResult(getStringMapper("CDTKEY"));
	}


	/**
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#isFlightSameForReceptacle(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 */
	public boolean isFlightSameForReceptacle(MailbagVO mailbagVO)
			throws SystemException, PersistenceException {

		int idx = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_IS_FLIGHT_SAME_AS_CARDIT);
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getMailbagId());
		query.setParameter(++idx, mailbagVO.getCarrierId());
		query.setParameter(++idx, mailbagVO.getFlightNumber());
		query.setParameter(++idx, mailbagVO.getFlightSequenceNumber());
		query.setParameter(++idx, mailbagVO.getScannedPort());
		Collection<MailbagVO> mailbags = query
				.getResultList(new MailbagPresentMapper());
		boolean isPresent = false;
		if (mailbags != null && mailbags.size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}


	/**
	 * @author A-2037 This method is used to find the ULDs in CARDIT
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<PreAdviceDetailsVO> findULDInCARDIT(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {


		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDINCARDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry
				.setParameter(++index, operationalFlightVO
						.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		return qry.getResultList(new ULDInCARDITMapper());

	}

	/**
	 * @author A-2037 This method is used to find the Mailbags in CARDIT
	 * Changed the query by A-8164 for ICRD-342608
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {

		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGINCARDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry
				.setParameter(++index, operationalFlightVO
						.getFlightSequenceNumber());
		//qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		//qry.setParameter(++index, operationalFlightVO.getPol());
		qry
				.append(" GROUP BY MALMST.DSTEXGOFC,MALMST.ORGEXGOFC,MALMST.MALCTG,CSGDTL.ULDNUM");
		qry.append(" ORDER BY MALMST.MALCTG,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,CSGDTL.ULDNUM");
		Collection<PreAdviceDetailsVO> coll = qry
				.getResultList(new MailbagsInCARDITMapper());
		for (PreAdviceDetailsVO preAdviceDetailsVO : coll) {
			//String origin = findDescription(preAdviceDetailsVO.getOriginExchangeOffice());//commented for ICRD-342571
			preAdviceDetailsVO.setOriginExchangeOffice(preAdviceDetailsVO
					.getOriginExchangeOffice());
			//String destination = findDescription(preAdviceDetailsVO.getDestinationExchangeOffice());//commented for ICRD-342571

			preAdviceDetailsVO.setDestinationExchangeOffice(preAdviceDetailsVO
					.getDestinationExchangeOffice());


		}
		return coll;
	}


	/**
	 * @author A-2037 This method is to find the desription of
	 *         originOfficeOfExchange and DestinationOfficeOfExchange
	 * @param code
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private String findDescription(String code) throws SystemException,
			PersistenceException {

		log.entering(MODULE, "findDescription");
		Query query = getQueryManager()
				.createNamedNativeQuery(FIND_DESCRIPTION);
		int index = 0;
		Mapper<String> stringMapper = getStringMapper("EXGCODDES");
		query.setParameter(++index, code);
		String description = query.getSingleResult(stringMapper);
		log.log(Log.FINE, "description", description);
		log.exiting(MODULE, "findDescription");
		return description;
	}

	/**
	 * This method is used to check whether the Uld is present in the Cardit
	 *
	 * @author A-1936
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkCarditPresentForUld(String companyCode, String uldNumber)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_IS_CARDIT_PRESENT_FOR_ULD);
		query.setParameter(1, companyCode);
		query.setParameter(2, uldNumber);
		Collection<String> ulds = query
				.getResultList(getStringMapper("ULDNUM"));
		boolean isPresent = false;
		if (ulds != null && ulds.size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}

	/**
	 * @author A-1936 This method is used to check whether the cardit Flight and
	 *         AssignedFlight are Same..
	 * @param containerDetailsVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#isFlightSameForReceptacle(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 */
	public boolean isFlightSameForUld(ContainerDetailsVO containerDetailsVO)
			throws SystemException, PersistenceException {

		int idx = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_IS_FLIGHT_SAME_AS_CARDIT_FORULD);
		query.setParameter(++idx, containerDetailsVO.getCompanyCode());
		query.setParameter(++idx, containerDetailsVO.getContainerNumber());
		query.setParameter(++idx, containerDetailsVO.getCarrierId());
		query.setParameter(++idx, containerDetailsVO.getFlightNumber());
		query.setParameter(++idx, containerDetailsVO.getFlightSequenceNumber());
		query.setParameter(++idx, containerDetailsVO.getSegmentSerialNumber());
		query.setParameter(++idx, containerDetailsVO.getPol());
		Collection<String> ulds = query
				.getResultList(getStringMapper("ULDNUM"));
		boolean isPresent = false;
		if (ulds != null && ulds.size() > 0) {
			isPresent = true;
		}
		return isPresent;
	}


	/**
	 * Finds the cardit details of this resdit Sep 11, 2006, a-1739
	 *
	 * @param companyCode
	 * @param consignmentId
	 * @return the cardit details of this resdit
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#
	 *      findCarditDetailsForResdit(String, java.lang.String)
	 */
	public CarditVO findCarditDetailsForResdit(String companyCode,
											   String consignmentId) throws SystemException, PersistenceException {
		log
				.entering(MAIL_TRACKING_DEFAULTS_SQLDAO,
						"findCarditDetailsForResdit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_FOR_RESDIT);
		query.setParameter(1, companyCode);
		query.setParameter(2, consignmentId);
		return query.getSingleResult(new CarditDetailsMapper());
	}

	/**
	 * Find no of containers in cardit Sep 15, 2006, a-1739
	 *
	 * @param companyCode
	 * @param consignmentNumber
	 * @return container count in cardit
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditContainerCount(java.lang.String,
	 *      java.lang.String)
	 */
	public int findCarditContainerCount(String companyCode,
										String consignmentNumber) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findContainerCountForResdit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CARDITCONTAINER_COUNT);
		query.setParameter(1, companyCode);
		query.setParameter(2, consignmentNumber);
		log
				.exiting("MailTrackingDefaultsSqlDAO",
						"findContainerCountForResdit");
		return query.getSingleResult(getIntMapper("CDTCONCNT"));
	}



	/**
	 * TODO Purpose Sep 15, 2006, a-1739
	 *
	 * @param companyCode
	 * @param consignmentNumber
	 * @return number of receptacles in cardit
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditReceptacleCount(java.lang.String,
	 *      java.lang.String)
	 */
	public int findCarditReceptacleCount(String companyCode,
										 String consignmentNumber) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCarditReceptacleCount");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CARDITRECEPTACLE_COUNT);
		query.setParameter(1, companyCode);
		query.setParameter(2, consignmentNumber);
		log.exiting("MailTrackingDefaultsSqlDAO", "findCarditReceptacleCount");
		return query.getSingleResult(getIntMapper("CDTRCPCNT"));
	}


	/**
	 * TODO Purpose Jan 23, 2007, A-1739
	 *
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditDetailsForMail(com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO)
	 */
	public CarditEnquiryVO findCarditMailDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCarditDetails");
		String flightType = carditEnquiryFilterVO.getFlightType();
		String baseQuery = null;
		if (MailConstantsVO.FLIGHT_TYP_CARDIT.equals(flightType)) {
			if (carditEnquiryFilterVO.getResdit() != null
					&& carditEnquiryFilterVO.getResdit().trim().length() > 0) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_MAILCARDIT_DETAILS);
			} else {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_ALL_MAILCARDIT_DETAILS);
			}
		} else if (MailConstantsVO.FLIGHT_TYP_OPR.equals(flightType)) {

			if (MailConstantsVO.OPERATION_OUTBOUND.equals(carditEnquiryFilterVO
					.getFlighDirection())) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_MAILCARDIT_FLIGHT);
			} else {
				log.log(Log.FINE, "THE OPEARTION TYPE IS INBOUND");
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_MAILCARDIT_FLIGHT_INBOUND);
			}
		}
		Query query = new CarditEnquiryFilterQuery(baseQuery,
				carditEnquiryFilterVO);
		CarditEnquiryVO carditEnquiryVO = new CarditEnquiryVO();
		carditEnquiryVO.setMailbagVos(query
				.getResultList(new CarditMailbagMultiMapper()));
		carditEnquiryVO.setCompanyCode(carditEnquiryFilterVO.getCompanyCode());
		if ((carditEnquiryFilterVO.getResdit() == null || carditEnquiryFilterVO
				.getResdit().trim().length() == 0)
				&& MailConstantsVO.FLIGHT_TYP_CARDIT.equals(flightType)) {
			carditEnquiryVO
					.setContainerVos(findCarditContainerDetails(carditEnquiryFilterVO));
		}
		log.log(Log.FINEST, "enqVO ", carditEnquiryVO);
		log.exiting("MailTrackingDefaultsSqlDAO", "findCarditDetails");
		return carditEnquiryVO;
	}

	/**
	 * @author a-1936 This method is used to find the cardit Container Details.
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<ContainerVO> findCarditContainerDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
		log
				.entering("MailTrackingDefaultsSqlDAO",
						"findCarditContainerDetails");
		Collection<ContainerVO> containerVos = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_CONTAINERDETAILS);
		int index = 0;
		qry.setParameter(++index, carditEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++index, carditEnquiryFilterVO.getCarrierId());
		qry.setParameter(++index, carditEnquiryFilterVO.getFlightNumber());
		qry.setParameter(++index, carditEnquiryFilterVO
				.getFlightSequenceNumber());
		qry.setParameter(++index, carditEnquiryFilterVO.getLegSerialNumber());
		if (carditEnquiryFilterVO.getConsignmentDocument() != null
				&& carditEnquiryFilterVO.getConsignmentDocument().trim()
				.length() > 0) {
			qry.append("AND CDTMST.CSGDOCNUM = ?  ");
			qry.setParameter(++index, carditEnquiryFilterVO
					.getConsignmentDocument());
		}
		containerVos = qry
				.getResultList(new ContainerDetailsForCarditMultiMapper());
		log.exiting("MailTrackingDefaultsSqlDAO", "findCarditContainerDetails");
		return containerVos;
	}


	/**
	 * TODO Purpose Jan 25, 2007, A-1739
	 *
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditDocumentDetails(com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO)
	 */
	public CarditEnquiryVO findCarditDocumentDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCarditDocumentDetails");
		String flightType = carditEnquiryFilterVO.getFlightType();
		String baseQuery = null;
		if (MailConstantsVO.FLIGHT_TYP_CARDIT.equals(flightType)) {
			if (carditEnquiryFilterVO.getResdit() != null
					&& carditEnquiryFilterVO.getResdit().trim().length() > 0) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_DOCCARDIT_DETAILS);
			} else {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_ALL_DOCCARDIT_DETAILS);
			}

		} else if (MailConstantsVO.FLIGHT_TYP_OPR.equals(flightType)) {

			if (MailConstantsVO.OPERATION_OUTBOUND.equals(carditEnquiryFilterVO
					.getFlighDirection())) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_DOCCARDIT_FLIGHT);
			} else {
				log.log(Log.FINE, "The Flight Direction is Inbound");
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_DOCCARDIT_FLIGHT_INBOUND);
			}
		}
		Query query = new CarditEnquiryFilterQuery(baseQuery,
				carditEnquiryFilterVO);
		CarditEnquiryVO carditEnquiryVO = new CarditEnquiryVO();
		carditEnquiryVO.setCompanyCode(carditEnquiryFilterVO.getCompanyCode());
		carditEnquiryVO.setConsignmentDocumentVos(query
				.getResultList(new ConsignmentDocumentExistsMapper()));
		log.log(Log.FINEST, "enqVO ", carditEnquiryVO);
		log.exiting("MailTrackingDefaultsSqlDAO", "findCarditDocumentDetails");
		return carditEnquiryVO;
	}


	/**
	 * TODO Purpose Jan 25, 2007, A-1739
	 *
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditDespatchDetails(com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO)
	 */
	public CarditEnquiryVO findCarditDespatchDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCarditDespatchDetails");
		String flightType = carditEnquiryFilterVO.getFlightType();
		String baseQuery = null;
		if (MailConstantsVO.FLIGHT_TYP_CARDIT.equals(flightType)) {
			if (carditEnquiryFilterVO.getResdit() != null
					&& carditEnquiryFilterVO.getResdit().trim().length() > 0) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_CARDITDESP_DETAILS);
			} else {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_ALL_CARDITDESP_DETAILS);
			}

		}

		else if (MailConstantsVO.FLIGHT_TYP_OPR.equals(flightType)) {
			if (MailConstantsVO.OPERATION_OUTBOUND.equals(carditEnquiryFilterVO
					.getFlighDirection())) {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_CARDITDESP_FLIGHT);
			} else {
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_CARDITDESP_FLIGHT_INBOUND);
			}
		}
		Query query = new CarditEnquiryFilterQuery(baseQuery,
				carditEnquiryFilterVO);
		CarditEnquiryVO carditEnquiryVO = new CarditEnquiryVO();
		carditEnquiryVO.setCompanyCode(carditEnquiryFilterVO.getCompanyCode());
		carditEnquiryVO.setDespatchDetailVos(query
				.getResultList(new OffloadDSNMapper()));
		log.log(Log.FINEST, "enqVO ", carditEnquiryVO);
		log.exiting("MailTrackingDefaultsSqlDAO", "findCarditDespatchDetails");
		return carditEnquiryVO;
	}


	/**
	 * TODO Purpose Feb 5, 2007, A-1739
	 *
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCarditTransportationDetails(java.lang.String,
	 *      java.lang.String)
	 */
	public Collection<CarditTransportationVO> findCarditTransportationDetails(
			String companyCode, String carditKey) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findCarditTransportForMailbag");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_TRTDETAILS);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, carditKey);
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findCarditTransportForMailbag");
		return qry.getResultList(new CarditTransportationMapper());
	}


	/**
	 * @author A-1936 ADDED AS THE PART OF NCA-CR This method is used to find
	 *         the CarditDetails for the MailBags..
	 *
	 * @param companyCode
	 * @param mailID
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailbagVO findCarditDetailsForAllMailBags(String companyCode,
													 long mailID) throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findCarditDetailsForAllMailBags");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CARDITDETAILS_FORALLMAILS);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, mailID);
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findCarditDetailsForAllMailBags");
		return qry.getSingleResult(new CarditDetailsForAllMailBagsMapper());

	}
	/**
	 * @author A-5991
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailbagVO findTransferFromInfoFromCarditForMailbags(
			MailbagVO mailbagVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findTransferFromInfoFromCarditForMailbags");
		MailbagVO updatedMailbagVO = new MailbagVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_TRANSFERFROMINFO_FROM_CARDIT_FORMAILBAGS);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getMailbagId());//Modified by a-7871 for ICRD-240184 (As column name changed to RCPIDR)
		updatedMailbagVO =  query.getSingleResult(new FindTransferFromInfoFromCarditMapper());
		if(updatedMailbagVO!=null){
			mailbagVO.setTransferFromCarrier(updatedMailbagVO.getTransferFromCarrier());
			mailbagVO.setFromFlightDate(updatedMailbagVO.getFromFlightDate());
			mailbagVO.setFromFightNumber(updatedMailbagVO.getFromFightNumber());//Added by a-7871 for ICRD-240184
			mailbagVO.setPou(updatedMailbagVO.getPou());
		}
		return mailbagVO;
	}

	/**
	 *
	 * @param reportVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<CarditPreAdviseReportVO> generateCarditPreAdviceReport (CarditPreAdviseReportVO reportVO)
			throws SystemException{
		log .entering( "MailTrackingDefaultsSqlDAO" , "generateCarditPreAdviceReport" );
		Query query = getQueryManager().createNamedNativeQuery(FIND_CARDIT_PREADVISES);
		int idx = 0;
		//Changes made by A-2882 as part of bug 102009
		query.setParameter(++idx, reportVO.getCompanyCode());
		query.setParameter(++idx, reportVO.getMailDate());
		query.setParameter(++idx, reportVO.getAirportCode());
		//query.setParameter(++idx, reportVO.getAirportCode());



		return query.getResultList(new CarditPreAdviseMultiMapper(reportVO.getAirportCode(), reportVO.getFlightCarrierIdr()));
	}

	/**
	 *
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2572
	 */
	public String findCarditOriginForResditGeneration(String companyCode,long mailbagId)
			throws SystemException,PersistenceException{
		log .entering( "MailTrackingDefaultsSqlDAO" , "findCarditOriginForResditGeneration" );
		Query query = getQueryManager().createNamedNativeQuery(FIND_CARDIT_ORIGIN);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx,mailbagId);
		return query.getSingleResult(getStringMapper("RFFNUM"));

	}


	/**
	 * For converting ULDResdit object qries
	 * Jun 3, 2008, a-1739
	 * @param uldResditVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<UldResditVO> findULDResditStatus (UldResditVO uldResditVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findULDResditStatus");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ULDRESDIT_STATUS);
		int idx=0;
		qry.setParameter(++idx, uldResditVO.getCompanyCode());
		qry.setParameter(++idx, uldResditVO.getUldNumber());
		qry.setParameter(++idx, uldResditVO.getEventCode());
		qry.setParameter(++idx, uldResditVO.getEventAirport());
		qry.setParameter(++idx, uldResditVO.getCarrierId());
		qry.setParameter(++idx, uldResditVO.getFlightNumber());
		qry.setParameter(++idx, uldResditVO.getFlightSequenceNumber());
		qry.setParameter(++idx, uldResditVO.getSegmentSerialNumber());
		log.exiting("MailTrackingDefaultsSqlDAO", "findULDResditStatus");
		return qry.getResultList(new ULDResditMapper());
	}


	/**
	 *
	 */
	public HashMap<String, String> findPAForShipperbuiltULDs(
			Collection<UldResditVO> uldResditVOs, boolean isFromCardit)
			throws SystemException,PersistenceException{

		log .entering( "MailTrackingDefaultsSqlDAO" , "findPAForShipperbuiltULDs" );

		HashMap<String,String> paMap = new HashMap<String,String>();
		if (uldResditVOs == null || uldResditVOs.size() <= 0)
		{
			return paMap;
		}

		int idx = 0;
		boolean first = true;
		UldResditVO firstULDResditVO = ((ArrayList<UldResditVO>)uldResditVOs).get(0);
		Query query = null;

		if (isFromCardit){
			query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_SHIPPERBUILT_ULD_FRMCDT );
			query.setParameter(++idx, firstULDResditVO.getCompanyCode());
			query.append(" AND	CON.CONNUM IN ( ?");

		}else{
			query = getQueryManager().createNamedNativeQuery(FIND_PA_FOR_SHIPPERBUILT_ULD );
			query.setParameter(++idx, firstULDResditVO.getCompanyCode());
			query.setParameter(++idx, firstULDResditVO.getCarrierId());
			query.setParameter(++idx, firstULDResditVO.getFlightNumber());
			query.setParameter(++idx, firstULDResditVO.getFlightSequenceNumber());
			query.setParameter(++idx, firstULDResditVO.getSegmentSerialNumber());

			query.append(" AND	MST.CONNUM IN ( ?");
		}
		query.setSensitivity(true);


		for(UldResditVO uldResditVO: uldResditVOs){
			if(first) {
				first = false;
				query.setParameter(++idx, uldResditVO.getUldNumber());
			}else {
				query.append(",? ");
				query.setParameter(++idx, uldResditVO.getUldNumber());
			}
		}
		query.append(" ) ");

		paMap = (HashMap<String,String>)query.getResultList(new PADetailsMultiMapper()).get(0);
		log .exiting ( "MailTrackingDefaultsSqlDAO" , "findPAForShipperbuiltULDs" );
		return paMap;
	}

	/**
	 * @author a-2037 This method is used to find all the mail subclass codes
	 * @param companyCode
	 * @param officeOfExchange
	 * @param pageNumber
	 * @return Collection<OfficeOfExchangeVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
														 String officeOfExchange, int pageNumber) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findOfficeOfExchange");
		PageableNativeQuery<OfficeOfExchangeVO> pgqry = null;	//Modified for ICRD-101150
		String exchangeOffice = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_OFFICEOFEXCHANGECODES);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(qry);
		pgqry = new PageableNativeQuery<OfficeOfExchangeVO>(0,rankQuery.toString(),
				new OfficeOfExchangeMapper());
		int index = 0;
		pgqry.setParameter(++index, companyCode);
		//Modified the condition by A-8527 for IASCB-30982
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


	/**
	 * Finds the PA corresponding to an exchangeoffice Sep 13, 2006, a-1739
	 *
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findPAForOfficeOfExchange(String,
	 *      java.lang.String)
	 */
	public String findPAForOfficeOfExchange(String companyCode,
											String officeOfExchange) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findPAForOfficeOfExchange");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_PA_FOR_EXCHANGEOFFICE);
		query.setParameter(1, companyCode);
		query.setParameter(2, officeOfExchange);
		log.exiting("MailTrackingDefaultsSqlDAO", "findPAForOfficeOfExchange");
		return query.getSingleResult(getStringMapper("POACOD"));
	}

	/**
	 * @author A-5526
	 * Added for CRQ ICRD-111886 by A-5526
	 * 	Parameters	:	@param companyCode,paCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findPartyIdentifierForPA(String companyCode,
										   String paCode) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findPartyIdentifierForPA");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_PARTYID_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		log.exiting("MailTrackingDefaultsSqlDAO", "findPartyIdentifierForPA");
		return query.getSingleResult(getStringMapper("PTYIDR"));
	}
	/**
	 * TODO Purpose Feb 1, 2007, A-1739
	 *
	 * @param companyCode
	 * @param carrierId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findResditConfurationForAirline(java.lang.String,
	 *      java.lang.String)
	 */
	public ResditConfigurationVO findResditConfurationForAirline(
			String companyCode, int carrierId) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findAirlineResditConfuration");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_RESDIT_CONFIG);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, carrierId);
		Collection<ResditTransactionDetailVO> resditTransactions = query
				.getResultList(new ResditConfigurationMapper());
		ResditConfigurationVO resditConfigVO = new ResditConfigurationVO();
		resditConfigVO.setCompanyCode(companyCode);
		resditConfigVO.setCarrierId(carrierId);
		resditConfigVO.setResditTransactionDetails(resditTransactions);
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findAirlineResditConfuration");
		return resditConfigVO;
	}


	/**
	 * TODO Purpose Feb 5, 2007, A-1739
	 *
	 * @param companyCode
	 * @param carrierId
	 * @param txnId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findResditConfurationForTxn(java.lang.String,
	 *      int, java.lang.String)
	 */
	public ResditTransactionDetailVO findResditConfurationForTxn(
			String companyCode, int carrierId, String txnId)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findResditConfurationForTxn");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_RESDITCONFIG_TXN);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, carrierId);
		qry.setParameter(++idx, txnId);
		log
				.exiting("MailTrackingDefaultsSqlDAO",
						"findResditConfurationForTxn");
		return qry.getSingleResult(new ResditConfigurationMapper());
	}


	/**
	 * @author A-2553
	 * @param companyCode
	 * @param mailbagId
	 * @param opFltVO
	 * @return
	 * @throws SystemException
	 */
	public MailbagVO findExistingMailbags(String companyCode,long mailbagId)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findExistingMailbags");
		MailbagVO mailbagVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_EXISTING_MAILBAGS);
		int index=0;

		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, mailbagId);
		List<MailbagVO> mailbagVOs =qry.getResultList(new ExistingMailbagsMapper());
		if(mailbagVOs!=null && mailbagVOs.size()>0){
			mailbagVO=mailbagVOs.get(0);
		}
		return mailbagVO;
	}


	/**
	 * @author a-2518
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPostalAuthorityCode(String companyCode,
										  String officeOfExchange) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findPostalAuthorityCode");
		Query query = getQueryManager().createNamedNativeQuery(FIND_POA_CODE);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, officeOfExchange);
		Mapper<String> stringMapper = getStringMapper("POACOD");
		log.exiting("MailTrackingDefaultsSqlDAO", "findPostalAuthorityCode");
		return query.getSingleResult(stringMapper);
	}


	/**
	 * @author a-2518
	 * @param companyCode
	 * @param gpaCode
	 * @param origin
	 * @param destination
	 * @param mailCategory
	 * @param activity
	 * @param scanDate
	 * @return MailActivityDetailVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailActivityDetailVO findServiceTimeAndSLAId(String companyCode,
														String gpaCode, String origin, String destination,
														String mailCategory, String activity, LocalDate scanDate)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findServiceTimeAndSLAId");
		Query query = null;
		MailActivityDetailVO mailActivityDetailVo = new MailActivityDetailVO();
		if (MAILSTATUS_ACCEPTED.equals(activity)) {
			query = getQueryManager().createNamedNativeQuery(
					FIND_SLAIDR_ACCPTANCE_TO_DEPARTURE);
		} else if (MAILSTATUS_ARRIVED.equals(activity)) {
			query = getQueryManager().createNamedNativeQuery(
					FIND_SLAIDR_ARRIVAL_TO_DELIVERY);
		}
		int index = 0;
		query.setParameter(++index, scanDate);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, gpaCode);
		query.setParameter(++index, origin);
		query.setParameter(++index, destination);
		query.setParameter(++index, mailCategory);
		Collection<MailActivityDetailVO> mailActivityDetailVos = query
				.getResultList(new SLAMapper());
		for (MailActivityDetailVO slaDetails : mailActivityDetailVos) {
			mailActivityDetailVo.setServiceTime(slaDetails.getServiceTime());
			mailActivityDetailVo
					.setSlaIdentifier(slaDetails.getSlaIdentifier());
			break;
		}
		log.exiting("MailTrackingDefaultsSqlDAO", "findServiceTimeAndSLAId");
		return mailActivityDetailVo;

	}



	/**
	 * @author A-3227
	 * findCityForOfficeOfExchange
	 * @param companyCode
	 * @param officeOfExchanges
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public HashMap<String,String> findCityForOfficeOfExchange(
			String companyCode, Collection<String> officeOfExchanges)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO","findCityForOfficeOfExchange");
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

	/**
	 * CityForOEMapper
	 * @author A-3227
	 *
	 */
	private static class CityForOEMapper implements MultiMapper<HashMap<String,String>> {

		private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
		private static final String CLASS_NAME = "CityForOEMapper";
		private List<HashMap<String,String>> cityForOEMap = new ArrayList<HashMap<String,String>>();

		public List<HashMap<String,String>> map(ResultSet rs) throws SQLException {
			log.entering(CLASS_NAME, "map");
			HashMap<String,String> cityForOE = new HashMap<String, String>();
			while(rs.next()) {
				if(!cityForOE.containsKey(rs.getString("EXGOFCCOD"))) {
					cityForOE.put( rs.getString("EXGOFCCOD"),rs.getString("CTYCOD"));
				}
			}
			cityForOEMap.add(cityForOE);
			return cityForOEMap;
		}
	}


	/**
	 * @author A-3227
	 * This method fetches the latest Container Assignment
	 * irrespective of the PORT to which it is assigned.
	 * This to know the current assignment of the Container.
	 * @param containerNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public ContainerAssignmentVO findLatestContainerAssignment(String companyCode,String containerNumber)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findLatestContainerAssignment");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_LATEST_CONTAINER_ASSIGNMENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setSensitivity(true);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		log.exiting(MODULE, "findLatestContainerAssignment");
		return containerAssignMentVO;
	}


	/**
	 * @author a-1936 This method Checks whether the container is already
	 *         assigned to a flight/destn from the current airport
	 * @param companyCode
	 * @param containerNumber
	 * @param pol
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerAssignmentVO findContainerAssignment(String companyCode,
														 String containerNumber, String pol) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findContainerAssignment");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINER_ASSIGNMENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		qry.setSensitivity(true);

		containerAssignMentVO = qry
				.getSingleResult(new ContainerAssignmentMapper());
		return containerAssignMentVO;
	}

	/**
	 * @author a-1936 This method is used to validate the MailSubclass
	 * @param companyCode
	 * @param mailSubClass
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean validateMailSubClass(String companyCode, String mailSubClass)
			throws SystemException, PersistenceException {
		log.entering("INSIDE SQLDAO", "validateMailSubClass");
		int index = 0;
		boolean isValid = false;
		Query qry = getQueryManager().createNamedNativeQuery(
				IS_MAILSUBCLASSVALID);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, mailSubClass);
		Mapper<String> subClassMapper = getStringMapper("SUBCLSCOD");
		String subClass = qry.getSingleResult(subClassMapper);
		if (subClass != null && subClass.trim().length() > 0) {
			isValid = true;
		}
		return isValid;

	}

	/**
	 * @author a-1883 NCA CR
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailDiscrepancyVO> findMailDiscrepancies(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingSqlDAO", "findMailDiscrepancies");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_DISCREPANCIES);
		query.setParameter(1, operationalFlightVO.getCarrierId());
		query.setParameter(2, operationalFlightVO.getFlightNumber());
		query.setParameter(3, operationalFlightVO.getFlightSequenceNumber());
		query.setParameter(4, operationalFlightVO.getCompanyCode());
		query.setParameter(5, operationalFlightVO.getPou());
		Collection<MailDiscrepancyVO> discrepancies = query
				.getResultList(new MailDiscrepancyMapper());
		log.exiting("MailTrackingSqlDAO", "findMailDiscrepancies");
		log.log(Log.FINE, " Mail Discrepancies ", discrepancies);
		return discrepancies;
	}



	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findULDsInInboundFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findULDsInInboundFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ULDS_IN_INBOUND_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPou());
//ADDED BY a-5945 for ICRD-110029
		qry.setSensitivity(true);
		log.exiting("MailTrackingDefaultsSqlDAO", "findULDsInInboundFlight");
		return qry.getResultList(new ContainerMapper());

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
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(
			String companyCode, String ownCarrierCode, String airportCode)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findAllPartnerCarriers");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_PARTNERCARRIERS);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, ownCarrierCode);
		qry.setParameter(++index, airportCode);

		return qry.getResultList(new PartnerCarrierMapper());

	}

	public boolean validateCoterminusairports(String actualAirport,String eventAirport,String eventCode,String paCode,LocalDate dspDate)
			throws SystemException,PersistenceException{

		boolean isValid=false;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				VALIDATE_COTERMINUS_AIRPORTS);
		qry.setParameter(++index, logonAttributes.getCompanyCode());
		qry.setParameter(++index, actualAirport);
		qry.setParameter(++index, eventAirport);
		qry.setParameter(++index, eventCode);
		qry.setParameter(++index, paCode);
		if(dspDate!= null ){
			qry.append("  AND ?  BETWEEN MTK.VLDFRMDAT AND MTK.VLDTOODAT ");
			qry.setParameter(++index, dspDate.toCalendar());
		}
		log.log(Log.FINE, "validateCoterminusairports query:  ", qry);
		//Mapper<String> coterminusMapper = getStringMapper("Y");
		String coTerminusAirport = qry.getSingleResult(getStringMapper("ARPCOD"));

		if (coTerminusAirport != null ) {
			log.log(Log.FINE, "validateCoterminusairports result:  ", coTerminusAirport);
			isValid = true;
		}
		return isValid;
	}

	public Collection<CoTerminusVO> findAllCoTerminusAirports(
			CoTerminusFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findAllCoTerminusAirports");
		int index = 0;
		String[] airportCodes=null;


		if(filterVO.getAirportCodes()!=null)
			airportCodes=filterVO.getAirportCodes().split(",");

		int size=airportCodes.length;
		int cnt =0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_COTERMINUS_AIRPORTS);
		if("Y".equals(filterVO.getReceivedfromTruck())){
			qry=qry.append("AND TRKFLG=?");
		}
		if(!(("").equals(filterVO.getResditModes()))){
			qry=qry.append(" AND MTK.RSDMOD = ?");
		}
		log.log(Log.FINE, "airportCodes ", airportCodes);
		log.log(Log.FINE, "getResditModes ", filterVO.getResditModes());
		if(airportCodes!=null&&!(("").equals(filterVO.getAirportCodes()))){
			qry=qry.append(" AND(");
			for(String code:airportCodes){
				cnt++;
				qry=qry.append(" INSTR(ARPCOD,?,1)>0");
				if(cnt<size){
					qry=qry.append("AND");
				}
				//	qry=qry.append(")");
			}
			qry=qry.append(")"); //modified by A-7371 as part of ICRD-269592
		}
		log.log(Log.FINE, "Co terminus query ", qry);
		qry.setParameter(++index, filterVO.getCompanyCode());
		qry.setParameter(++index, filterVO.getGpaCode());
		if("Y".equals(filterVO.getReceivedfromTruck())){
			qry.setParameter(++index, filterVO.getReceivedfromTruck());
		}
		if(!(("").equals(filterVO.getResditModes())))
			qry.setParameter(++index, filterVO.getResditModes());

		if(airportCodes!=null&&!(("").equals(filterVO.getAirportCodes()))){
			for(String code:airportCodes){
				qry.setParameter(++index, code);
			}
		}
		log.log(Log.FINE, "Co terminus query ", qry);
		Collection<CoTerminusVO> coTerminusVOs = qry
				.getResultList(new CoterminusAirportsMapper());
		return coTerminusVOs;

	}

	/*modified by A-7540 for ICRD-314176*/
	public Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO,int pageNumber)
			throws SystemException, PersistenceException {

		int pageSize = mailServiceStandardFilterVO.getDefaultPageSize();
		int totalRecords=mailServiceStandardFilterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		Query  baseQry = getQueryManager().createNamedNativeQuery(
				LIST_MAIL_SERVICESTANDARDS);
		rankQuery.append(baseQry);
		PageableNativeQuery<MailServiceStandardVO> qry = new ServiceStandardFilterQuery(pageSize,totalRecords,mailServiceStandardFilterVO, rankQuery.toString(),new MailServiceStandardMapper());
		log.log(Log.INFO, "Query: ", qry);

		qry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return qry.getPage(pageNumber);

	}
	public Collection<MailRdtMasterVO> findRdtMasterDetails(
			RdtMasterFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findRdtMasterDetails");
		int index = 0;
		String[] airportCodes=null;


		Query qry = getQueryManager().createNamedNativeQuery(
				LIST_RDTDETAILS);
		qry.setParameter(++index, filterVO.getCompanyCode());
		/*if(filterVO.getAirportCodes()!=null){
		airportCodes=filterVO.getAirportCodes().split(",");

		int size=airportCodes.length;

		if(airportCodes!=null&&!(("").equals(filterVO.getAirportCodes()))){
			qry=qry.append(" AND ARPCOD IN (");
			for(String code:airportCodes){

				qry=qry.append("?");
				qry.setParameter(++index, code);
					qry=qry.append(",");

			}
			qry=qry.append("AA)");
		}
		}*/

		log.log(Log.FINE, "airportCodes ", airportCodes);


		log.log(Log.FINE, "Co terminus query ", qry);

		/*qry.setParameter(++index, filterVO.getMailType());

		if(airportCodes!=null&&!(("").equals(filterVO.getAirportCodes()))){
			for(String code:airportCodes){
		}*/
		/*if(filterVO.getAirportList()!=null && filterVO.getAirportList().size()>0){
		   qry=qry.append("AND ARPCOD in (");
			    int count = 0;
				for(String airport : filterVO.getAirportList()){
					++count;
					qry.append("?");
					qry.setParameter(++index, airport);
						if (count < filterVO.getAirportList().size()) {
							qry.append(" ,");
					}
				}
			qry.append(" )");
		}*/
		if((filterVO.getAirportCodes()!=null && !(("").equals(filterVO.getAirportCodes()))) || (filterVO.getOriginAirportCode()!=null&&!(("").equals(filterVO.getOriginAirportCode())))){
			qry=qry.append("AND");
		}

		if(filterVO.getAirportCodes()!=null&&!(("").equals(filterVO.getAirportCodes()))){
			qry=qry.append("(DSTARPCOD=?");
			qry.setParameter(++index, filterVO.getAirportCodes());
		}

		//originairportcode has been set only during domestic cardit processing to get thE RDTCFGTYP for oconus airports IASCB-86316

		if(filterVO.getOriginAirportCode()!=null&&!(("").equals(filterVO.getOriginAirportCode()))){
			qry=qry.append("OR ORGARPCOD=?");
			qry.setParameter(++index, filterVO.getOriginAirportCode());
		}

		if((filterVO.getAirportCodes()!=null && !(("").equals(filterVO.getAirportCodes()))) || (filterVO.getOriginAirportCode()!=null&&!(("").equals(filterVO.getOriginAirportCode())))){
			qry=qry.append(")");
		}
		if(filterVO.getMailType() !=null && filterVO.getMailType().trim().length()>0){
			qry=qry.append("AND MALTYP=?");
			qry.setParameter(++index, filterVO.getMailType());
		}
		if(filterVO.getGpaCode() !=null && filterVO.getGpaCode().trim().length()>0){
			qry=qry.append("AND GPACOD=?");
			qry.setParameter(++index, filterVO.getGpaCode());
		}
		if(filterVO.getMailClass()!=null && filterVO.getMailClass().trim().length()>0){
			qry=qry.append("AND MALCLS=?");
			qry.setParameter(++index, filterVO.getMailClass());
		}
		log.log(Log.FINE, "Co terminus query ", qry);
		Collection<MailRdtMasterVO> mailRdtMasterVOs = qry
				.getResultList(new MailRdtMasterMapper());
		return mailRdtMasterVOs;

	}



	/**
	 * @author A-1936 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagsForUpliftedResdit(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findAllPartnerCarriers");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FORUPLIFTEDRESDIT);
		qry.setSensitivity(true);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry.setParameter(++index, operationalFlightVO
				.getFlightSequenceNumber());
		//Added by A-8527 for ICRD-357192 starts
		if(operationalFlightVO.getPol()!=null){
			qry.append(" AND FLTSEG.POL       = ?");
			qry.setParameter(++index, operationalFlightVO.getPol());
		}
		else {
			if(operationalFlightVO.getPou()!=null){
				qry.append(" AND FLTSEG.POU       = ?");
				qry.setParameter(++index, operationalFlightVO.getPou());
			}
		}
		qry.append(" ) SELECT malidr,malseqnum,segsernum,scndat,uldnum,scnprt, contyp, malsta,pou,orgexgofc,dstexgofc,malctg,malsubcls,poacod,pol,orgcod,dstcod FROM  mal ");

		//Added by A-8527 for ICRD-357192 Ends
		// qry.setParameter(++index, operationalFlightVO.getLegSerialNumber()); */
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		//commented for ICRD-ICRD-85769
		/*if (mailBagVos != null && mailBagVos.size() > 0) {
			mailBagVos = validateMailBagsForUpliftedResdit(operationalFlightVO,
					mailBagVos);
		}*/
		if (mailBagVos != null && mailBagVos.size() > 0) {
			for (MailbagVO mailbag : mailBagVos) {
				mailbag.setCompanyCode(operationalFlightVO.getCompanyCode());
				if(mailbag.getCarrierCode()==null){
					mailbag.setCarrierCode(operationalFlightVO.getCarrierCode());
				}
				mailbag.setCarrierId(operationalFlightVO.getCarrierId());
				mailbag.setFlightDate(operationalFlightVO.getFlightDate());
				mailbag.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				mailbag.setFlightNumber(operationalFlightVO.getFlightNumber());
				mailbag
						.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			}
		}
		return mailBagVos;
	}


	/**
	 * This method finds Cardit Details of Maiibag
	 *
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */


	public MailbagHistoryVO findCarditDetailsOfMailbag(String companyCode,String mailBagId,
													   long mailSequenceNumber) throws SystemException, PersistenceException {  /*modified by A-8149 for ICRD-248207*/
		log.entering(MODULE, "findCarditDetailsOfMailbag");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_DETAILS_OF_MAILBAG);
		query.setParameter(1, companyCode);
		if(mailSequenceNumber!=0l){
			query.append(" AND MALMST.MALSEQNUM = ? ORDER BY CDTRCP.RCPSRLNUM DESC");
			query.setParameter(2, mailSequenceNumber);
		}
		else if(mailBagId!=null || !mailBagId.isEmpty()){
			query.append(" AND MALMST.MALIDR = ? ORDER BY CDTRCP.RCPSRLNUM ");
			query.setParameter(2, mailBagId);
		}

		return query.getSingleResult(new CarditDetailsOfMailbagMapper());

	}

	/**
	 * @author A-2037 This method is used to find the History of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagHistoryVO> findMailbagHistories(
			String companyCode, String mailBagId, long mailSequenceNumber, String mldMsgGenerateFlag)
			throws SystemException /* modified by A-8149 for ICRD-248207 */ {

		log.entering(MODULE, "findMailbagHistories");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGHISTORIES);
		int index = 0;
		if (mldMsgGenerateFlag!=null&&"Y".equalsIgnoreCase(mldMsgGenerateFlag)) {
			generateQueryToFetchMailbagHistoryWithMLDetails(query, companyCode, mailBagId, mailSequenceNumber, index);
		} else {
			query.setParameter(++index, companyCode);
			if (mailSequenceNumber != 0l) {
				query.append(" AND MST.MALSEQNUM = ? ORDER BY HIS.UTCSCNDAT ");
				query.setParameter(++index, mailSequenceNumber);
			} else {
				if (mailBagId != null && !mailBagId.isEmpty()) {
				query.append(" AND MST.MALIDR = ? ORDER BY HIS.UTCSCNDAT ");
				query.setParameter(++index, mailBagId);
				}
			}
		}
		return query.getResultList((MailbagHistoryMapper) SpringAdapter.getInstance().getBean("MailbagHistoryMapper"));
	}
	public void generateQueryToFetchMailbagHistoryWithMLDetails(Query query, String companyCode, String mailBagId,
			long mailSequenceNumber, int index) throws SystemException {
		String malseqnum="AND MST.MALSEQNUM = ?";
		String malidr="AND MST.MALIDR = ?";
		Query queryMLD = getQueryManager().createNamedNativeQuery(FIND_MAILBAG_MLDDETAILS);
		boolean malseqExist=false;
		boolean mailBagIdExist=false;
		if(mailSequenceNumber!=0l){
			malseqExist=true;
		}
		if(mailBagId!=null && !mailBagId.isEmpty()){
			mailBagIdExist=true;
		}
		if(malseqExist){
			query.append(malseqnum);
			queryMLD.append(malseqnum);
		}
		if(mailBagIdExist){
			query.append(malidr);
			queryMLD.append(malidr);
		}
		query.append(" UNION ALL ( ");
		queryMLD.append(" ) order by UTCSCNDAT , \r\n" + 
				"    malsta  ");
		query.combine(queryMLD);
		query.setParameter(++index, companyCode);
		if(malseqExist){
			query.setParameter(++index, mailSequenceNumber);
		}
		if(mailBagIdExist){
			query.setParameter(++index, mailBagId);
		}
		query.setParameter(++index, companyCode);
		if(malseqExist){
			query.setParameter(++index, mailSequenceNumber);
		}
		if(mailBagIdExist){
			query.setParameter(++index, mailBagId);
		}


	}
	
	public Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailbagNotes");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGNOTES);
		query.setParameter(++index, mailBagId);
		return query.getResultList(new MailBagNotesMapper());
	}
	/**
	 * findMailbagHistories
	 */
	public Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) throws SystemException,
			PersistenceException {

		log.entering(MODULE, "findMailbagHistories");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGSTATUS);
		query.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		query.setParameter(++index, mailbagEnquiryFilterVO.getOrigin());
		query.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		fromDate.setDate(mailbagEnquiryFilterVO.getFromDate());
		query.setParameter(++index, Integer.parseInt(fromDate.toStringFormat(MailConstantsVO.YYYYMMDD).substring(0, 8)));
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		toDate.setDate(mailbagEnquiryFilterVO.getToDate());
		query.setParameter(++index, Integer.parseInt(toDate.toStringFormat(MailConstantsVO.YYYYMMDD).substring(0, 8)));
		if(mailbagEnquiryFilterVO.getDespatchSerialNumber() !=null && mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
			query.append(" AND MST.DSN = ? ");
			query.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if(mailbagEnquiryFilterVO.getAwbNumber() !=null && mailbagEnquiryFilterVO.getAwbNumber().trim().length()>0){
			String awbNumber = mailbagEnquiryFilterVO.getAwbNumber();
			String[] awbNumbers = awbNumber.split("-");
			String mstdocnum=awbNumbers[1];
			query.append(" AND MST.MSTDOCNUM = ? ");
			query.setParameter(++index, mstdocnum);
		}
		if(mailbagEnquiryFilterVO.getMailbagId() !=null && mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
			query.append(" AND MST.MALIDR = ? ");
			query.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
		}
		query.append("  ORDER BY MST.MALIDR,BKGFLTDTL.SERNUM ");

		return query.getResultList(new MailbagHistoryMapper());

	}


	/**
	 * TODO Purpose Oct 6, 2006, a-1739
	 *
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailbagDetailsForUpload(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 */
	public MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findMailbagDetailsForUpload");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILDETAILS_FOR_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		//query.setParameter(++idx, mailbagVO.getCompanyCode());;//added by A-8353 for ICRD-320888
		//query.setParameter(++idx, mailbagVO.getMailbagId());
		query.setParameter(++idx, mailbagVO.getMailbagId());
		log
				.exiting("MailTrackingDefaultsSqlDAO",
						"findMailbagDetailsForUpload");
		if( mailbagVO.getMailSource()!=null && mailbagVO.getMailSource().startsWith(MailConstantsVO.SCAN) ){
			query.append(" ORDER BY LEGSERNUM DESC ");
		}
		return query.getSingleResult(new MailbagPresentMapper());
	}


	/**
	 * @author A-1885
	 */
	public Collection<MailUploadVO> findMailbagAndContainer(MailUploadVO mailUploadVo)
			throws SystemException,PersistenceException{
		Collection<MailUploadVO> mailvos = null;
		Query qry =null;
		if(!mailUploadVo.isDeliverd()){
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILANDCONTAINERDETAILS);
			int index = 0;
			qry.setParameter(++index, mailUploadVo.getCompanyCode());
			qry.setParameter(++index, mailUploadVo.getCarrierId());
			qry.setParameter(++index, mailUploadVo.getFlightNumber());
			qry.setParameter(++index, mailUploadVo.getFlightSequenceNumber());
			if(mailUploadVo.getMailTag()!=null &&
					mailUploadVo.getMailTag().trim().length()>0){
				qry.append(" AND MALMST.MALIDR = ? ");
				qry.setParameter(++index, mailUploadVo.getMailTag());
			}
			if(mailUploadVo.getContainerNumber()!=null &&
					mailUploadVo.getContainerNumber().trim().length()>0){
				//In case of BULK-SIN(ARR), the uldnum will be stamped in uldnum column, connum coulmn contains the bulkname captured from export
				if(mailUploadVo.getContainerNumber().startsWith("BULK-")){
					qry.append(" AND SEG.ULDNUM = ? ");
					qry.setParameter(++index, mailUploadVo.getContainerNumber());
				}
				else{
					qry.append(" AND SEG.CONNUM = ? ");
					qry.setParameter(++index, mailUploadVo.getContainerNumber());
				}
			}
			/*if(mailUploadVo.getContainerPOU()!=null &&
			mailUploadVo.getContainerPOU().trim().length()>0){
			qry.append(" AND MST.POU = ? ");
			qry.setParameter(++index, mailUploadVo.getContainerPOU());
			}*/
		}
		else{
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILDETAILSFORDELIVERYFORGHA);
			int index = 0;
			qry.setParameter(++index, mailUploadVo.getCompanyCode());
			qry.setParameter(++index, mailUploadVo.getContainerNumber());
			qry.setParameter(++index, mailUploadVo.getToPOU());
		}
		return qry.getResultList(new MailbagAndContainerMapper());
	}



	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findAirportCityForMLD(java.lang.String, java.lang.String)
	 *	Added by 			: A-4803 on 14-Nov-2014
	 * 	Used for 	:	finding airport city for MLD
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param destination
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findAirportCityForMLD(String companyCode, String destination) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findAirportCityForMLD");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_AIRPORTCITYFORMLD);
		query.setParameter(++index, destination);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, destination);
		query.setParameter(++index, companyCode);
		String airportCode = query.getSingleResult(getStringMapper("ARPCOD"));
		log.entering("MailTrackingDefaultsSqlDAO", "findAirportCityForMLD");
		return airportCode;
	}



	/**
	 * @author A-5991
	 * @param flightAssignedContainerVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO) throws SystemException,PersistenceException{
		log.entering(MODULE, "numberOfBarrowsPresent");
		int index =0;
		int numberOfBarrows = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_NUMBER_OF_BARROWS_PRESENT_IN_FLIGHT_OR_CARRIER);
		qry.setParameter(++index,flightAssignedContainerVO.getCarrierId());
		qry.setParameter(++index,flightAssignedContainerVO.getFlightNumber());
		qry.setParameter(++index,flightAssignedContainerVO.getFlightSequenceNumber());
		qry.setParameter(++index,flightAssignedContainerVO.getAssignedPort());
		if(flightAssignedContainerVO.getPou()!= null){
			qry.setParameter(++index,flightAssignedContainerVO.getPou());
		} else
		{
			qry.setParameter(++index,flightAssignedContainerVO.getFinalDestination());
		}
		///qry.setParameter(++index,flightAssignedContainerVO.getSegmentSerialNumber());
		return qry.getSingleResult(getIntMapper("COUNT"));
	}


	@Override
	public Page<OperationalFlightVO> findMailFlightDetails(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * Finds the arrival details of a ULD if any Sep 12, 2007, a-1739
	 *
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivalDetailsForULD(com.ibsplc.icargo.business.mail.operations.vo.ContainerVO)
	 */
	public ContainerAssignmentVO findArrivalDetailsForULD(
			ContainerVO containerVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findArrivalDetailsForULD");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ULD_ARRIVAL_DTLS);
		int idx = 0;
		query.setParameter(++idx, containerVO.getCompanyCode());
		query.setParameter(++idx, containerVO.getContainerNumber());
		query.setParameter(++idx, containerVO.getAssignedPort());
		ContainerAssignmentVO contAsgVO = query
				.getSingleResult(new ContainerAssignmentMapper());
		log.exiting("MailTrackingDefaultsSqlDAO", "findArrivalDetailsForULD");
		return contAsgVO;
	}

	/**
	 *
	 *	Overriding Method	:	@see cargo.business.mail.operations.vo.MLDMasterVO
	 *	Added by 			: A-4803 on 28-Oct-2014
	 * 	Used for 	:	To find whether a container is already presnet for the mail bag
	 *	Parameters	:	@param mldMasterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) throws
			SystemException, PersistenceException {
		MLDDetailVO mLDDetailVO = mldMasterVO.getMldDetailVO();
		Query query = null;
		int index = 0;
		if ("-1".equals(mLDDetailVO.getFlightNumberOub())) {
			query = getQueryManager().createNamedNativeQuery(
					FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_CARRIER);
			query.setParameter(++index, mLDDetailVO.getFlightNumberOub());
			query.setParameter(++index, mldMasterVO.getSenderAirport() );
			query.setParameter(++index, mLDDetailVO.getPouOub());
			query.setParameter(++index, mLDDetailVO.getCarrierCodeOub());
			query.setParameter(++index, mLDDetailVO.getCompanyCode());
			return query.getSingleResult(getStringMapper(MailConstantsVO.CONTAINER_NUMBER));
		} else {
			String flightQuery =null;
			String dynamicquery = null;
			String finalFlightQuery =null;
			flightQuery = getQueryManager().getNamedNativeQueryString(
					FIND_ASSIGNED_TROLLEY_NUMBER_FOR_MLD_FLIGHT);
			if(isOracleDataSource()){
				dynamicquery= "AND ASGFLT.FLTDAT = ?";
			}else{
				dynamicquery= "AND ASGFLT.FLTDAT = cast(? as timestamp)";
			}

			finalFlightQuery = String.format(flightQuery, dynamicquery);
			Query qry = getQueryManager().createNativeQuery(finalFlightQuery);

			qry.setParameter(++index, mLDDetailVO.getCarrierCodeOub());
			qry.setParameter(++index, mLDDetailVO.getFlightNumberOub());
			qry.setParameter(++index, mLDDetailVO.getFlightOperationDateOub().toDisplayDateOnlyFormat());
			qry.setParameter(++index, mldMasterVO.getSenderAirport() );
			qry.setParameter(++index, mLDDetailVO.getPouOub());
			qry.setParameter(++index, mLDDetailVO.getCompanyCode());
			//Added as part of bug IASCB-63591 by A-5526 starts
			qry.append("AND CONMST.CONNUM LIKE ").append(
					new StringBuilder().append("'%").append(mLDDetailVO.getFlight())
							.append("%'").toString());
			//Added as part of bug IASCB-63591 by A-5526 ends
			return qry.getSingleResult(getStringMapper(MailConstantsVO.CONTAINER_NUMBER));
		}
	}


	/**
	 * @author A-1936 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findUldsForUpliftedResdit(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findULDsForUpliftedResdit");
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ULDS_FORUPLIFTEDRESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry
				.setParameter(++index, operationalFlightVO
						.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
		qry.setParameter(++index, operationalFlightVO.getPol());
		qry.setParameter(++index, MailConstantsVO.ULD_TYPE);
		containerDetailsVOs = qry
				.getResultList(new UldsForUpliftedResditMapper());
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			// mailBagVos = validateMailBagsForUpliftedResdit(
			// operationalFlightVO, mailBagVos);
		}
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				containerDetailsVO.setCompanyCode(operationalFlightVO
						.getCompanyCode());
				containerDetailsVO.setCarrierCode(operationalFlightVO
						.getCarrierCode());
				containerDetailsVO.setCarrierId(operationalFlightVO
						.getCarrierId());
				containerDetailsVO.setFlightDate(operationalFlightVO
						.getFlightDate());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO
						.getFlightNumber());
			}
		}
		return containerDetailsVOs;
	}



	/**
	 *
	 * Sep 12, 2007, a-1739
	 *
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivalDetailsForMailbag(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 */
	public MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findArrivalDetailsForMailbag");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_ARRIVAL_DTLS);
		int idx = 0;
		qry.setParameter(++idx, mailbagVO.getCompanyCode());
		qry.setParameter(++idx, mailbagVO.getMailbagId());
		qry.setParameter(++idx, mailbagVO.getScannedPort());

		log.exiting("MailTrackingDefaultsSqlDAO",
				"findArrivalDetailsForMailbag");
		return qry.getSingleResult(new MailbagMapper());
	}


	/**
	 *
	 * @author a-1936 This method is used to validate the OfficeOfExchange
	 * @param companyCode
	 * @param officeOfExchange
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode,
													   String officeOfExchange) throws SystemException,
			PersistenceException {
		log.log(Log.INFO, "INSIDE SQLDAO");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(IS_EXCHANGEVALID);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, officeOfExchange);
		return qry.getSingleResult(new OfficeOfExchangeMapper());

	}



	/**
	 * @author A-2553
	 * @param dsnVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findMailType(MailbagVO mailbagVO)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findPltModeOfDSN");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_TYPE);
		int index =0;
		if(mailbagVO !=null) {
			qry.setParameter(++index, mailbagVO.getCompanyCode());
			qry.setParameter(++index, mailbagVO.getMailSequenceNumber());
		}
		return qry.getSingleResult(getStringMapper("MALTYP"));
	}


	/**
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO checkConsignmentDocumentExists(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "checkConsignmentDocumentExists");
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_CONSIGNMENT_DOCUMENT_EXISTS);
		query.setParameter(1, consignmentDocumentVO.getCompanyCode());
		query.setParameter(2, consignmentDocumentVO.getConsignmentNumber());
		query.setParameter(3, consignmentDocumentVO.getPaCode());
		query.setParameter(4, consignmentDocumentVO.getConsignmentDate()
				.toCalendar());
		return query.getSingleResult(new ConsignmentDocumentExistsMapper());
	}



	/**
	 * This method finds mail sequence number
	 *
	 * @param mailInConsignmentVO
	 * @return int
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailSequenceNumber");
		int mailSeqNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_SEQUENCE_NUMBER);
		int indx = 0;
		// String uldNumber = mailInConsignmentVO.getUldNumber();
		query.setParameter(++indx, mailInConsignmentVO.getCompanyCode());
		query.setParameter(++indx, mailInConsignmentVO.getConsignmentNumber());
		query.setParameter(++indx, mailInConsignmentVO.getPaCode());
		query.setParameter(++indx, mailInConsignmentVO
				.getConsignmentSequenceNumber());
		String seqNum = query.getSingleResult(getStringMapper("MALSEQNUM"));
		if (seqNum != null) {
			mailSeqNumber = Integer.parseInt(seqNum);
		}
		log.exiting(MODULE, "findMailSequenceNumber");
		return mailSeqNumber;
	}

	@Override
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode,
															String subclassCode) throws SystemException, PersistenceException {

		log.entering(MODULE, "findMailSubClassCodes");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILSUBCLASSCODES);
		qry.setParameter(++index, companyCode);
		if (subclassCode != null && subclassCode.trim().length() > 0) {
			subclassCode = subclassCode.replace(MailConstantsVO.STAR,
					MailConstantsVO.PERCENTAGE)
					+ MailConstantsVO.PERCENTAGE;
			qry.append("AND MTK.SUBCLSCOD LIKE  ? ");
			qry.setParameter(++index, subclassCode);
		}
		return qry.getResultList(new MailSubClassCodeMapper());
	}

	/**
	 *
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findLatestFlightDetailsOfMailbag");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_LATEST_FLIGHT_DETAILS_OF_MAILBAG);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getMailSequenceNumber());
		//query.setParameter(++idx, mailbagVO.getDespatchSerialNumber());
//	query.setParameter(++idx, mailbagVO.getOoe());
//	query.setParameter(++idx, mailbagVO.getDoe());
//	query.setParameter(++idx, mailbagVO.getMailSubclass());
//	query.setParameter(++idx, mailbagVO.getMailCategoryCode());
//	query.setParameter(++idx, mailbagVO.getYear());
		query.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
		query.setParameter(++idx, MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findLatestFlightDetailsOfMailbag");
		return query.getSingleResult(new MailbagMapper());
	}

	/**
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean isMailbagAlreadyArrived(
			MailbagVO mailbagVO) throws SystemException,
			PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "isMailbagAlreadyArrived");
		Query qry = getQueryManager().createNamedNativeQuery(
				IS_MAILBAG_ALREADY_ARRIVED);
		qry.setParameter(1, mailbagVO.getCompanyCode());
		qry.setParameter(2, mailbagVO.getMailbagId());
		qry.setParameter(3, mailbagVO.getScannedPort());
		//Added for bug ICRD-98510 by A-5526 starts
		qry.setParameter(4, mailbagVO.getCompanyCode());
		//Added for bug ICRD-98510 by A-5526 ends
		qry.append(" AND MALMST.MALSTA IN ('ARR','ASG') " );
		Mapper<String> stringMapper = getStringMapper("FLG");
		log.exiting("MailTrackingDefaultsSqlDAO", "isMailbagAlreadyArrived");
		if((qry.getSingleResult(stringMapper))!=null){
			return true;
		}
		return false;
	}

	/**
	 * @author A-2037 This method is used to get the consignment details for
	 *         mailbag
	 * @param companyCode
	 * @param mailId
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailInConsignmentVO findConsignmentDetailsForMailbag(
			String companyCode, String mailId, String airportCode)
			throws SystemException, PersistenceException {

		log.entering(MODULE, "findConsignmentDetailsForMailbag");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG);
		//Added for icrd-128081
		query.setSensitivity(true);
		query.setParameter(++index, companyCode);
		query.append(" AND MALMST.MALIDR        = ? ");
		query.setParameter(++index, mailId);
		//Commented for ANZ bug 49056,changes made in query also
		//query.setParameter(++index, airportCode);
		return query.getSingleResult(new ConsignmentDetailsMapper());

	}


	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findConsignmentDocumentDetails");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_ROUTING_INFOS);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(3, consignmentFilterVO.getPaCode());
		List<ConsignmentDocumentVO> consignmentDocumentVOs = query
				.getResultList(new ConsignmentDetailsMultimapper());
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		/*
		 * Mail details not needed for Online HHT.
		 */
		if(!MailConstantsVO.FLAG_YES.equalsIgnoreCase(consignmentFilterVO.getScannedOnline())) {
			String qryString = getQueryManager().getNamedNativeQueryString(
					FIND_CONSIGNMENT_DOCUMENT_DETAILS);
			if(consignmentFilterVO.getPageSize()==0){
				consignmentFilterVO.setPageSize(10);
			}
			PageableNativeQuery<MailInConsignmentVO> pgyquery =
					new PageableNativeQuery<MailInConsignmentVO>(consignmentFilterVO.getPageSize(),consignmentFilterVO.getTotalRecords(),
							qryString , new MaintainConsignmentMapper());

			pgyquery.setParameter(1, consignmentFilterVO.getCompanyCode());
			pgyquery.setParameter(2, consignmentFilterVO.getConsignmentNumber());
			pgyquery.setParameter(3, consignmentFilterVO.getPaCode());
			Page<MailInConsignmentVO> mailInConsignmentVOs=null;
			if(consignmentFilterVO.getPageNumber()>0)
				mailInConsignmentVOs=pgyquery.getPage(consignmentFilterVO.getPageNumber());
			else{
				//fix starts
				List<MailInConsignmentVO> fullmailInConsignmentVOs=pgyquery.getResultList(new MaintainConsignmentMapper());
				mailInConsignmentVOs= new Page<MailInConsignmentVO>(
						fullmailInConsignmentVOs, 0, 0, 0, 0, 0, false);

				//fix ends
			}
			if(consignmentDocumentVO!=null){
				consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
			}
			log.log(Log.INFO, " ###### Query for execution ", qryString);
		}
		log.log(Log.FINE, " <<=== ConsignmentDocumentVO ===>>",
				consignmentDocumentVO);
		return consignmentDocumentVO;
	}


	/**
	 * This method checks whether mail(dsn) accepted
	 *
	 * @author a-1883
	 * @param mailInConsignmentVO
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String checkMailAccepted(MailInConsignmentVO mailInConsignmentVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "checkMailAccepted");
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_MAIL_ACCEPTED);
		int idx = 0;
		query.setParameter(++idx, mailInConsignmentVO.getCompanyCode());
		query.setParameter(++idx, mailInConsignmentVO.getMailId());
		Mapper<String> mailAcceptedMapper = getStringMapper("CMPCOD");
		return query.getSingleResult(mailAcceptedMapper);
	}

	/**
	 * This method collects Mail details for Report
	 *
	 * @author A-1883
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailDetailVO> findMailbagDetailsForReport(
			OperationalFlightVO operationalFlightVO, String consignmentKey)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findMailbagDetailsForReport");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILDETAILS_FOR_REPORT);
		int qryIdx = 0;
		qry.setParameter(++qryIdx, operationalFlightVO.getCompanyCode());
		qry.setParameter(++qryIdx, operationalFlightVO.getCarrierId());
		qry.setParameter(++qryIdx, operationalFlightVO.getFlightNumber());
		qry.setParameter(++qryIdx, operationalFlightVO
				.getFlightSequenceNumber());
		if (consignmentKey != null) {
			appendConsignmentDetails(qry, consignmentKey, qryIdx);

		}
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findCarditDetailsForAllMailBags");
		return qry.getResultList(new MailbagDetailsForReportMapper());
	}


	private void appendConsignmentDetails(Query qry, String consignmentKey,
										  int qryIdx) {
		// consignmentKey is of format AV7 - DOCNUM - ORG - DST - POU/PO
		String[] rptDetails = consignmentKey
				.split(MailConstantsVO.CONSIGN_REPORT_SEP);
		String reportType = rptDetails[0];
		String ctrlDocNum = rptDetails[1];
		String org = rptDetails[2];
		String destn = rptDetails[3];
		String av7PO = null;
		String pou = null;
		if (MailConstantsVO.CONSIGNMENT_TYPE_AV7.equals(reportType)) {
			av7PO = rptDetails[4];
		} else {
			pou = rptDetails[4];
		}
		qry.append(" AND ORG.SRVARPCOD = ? ");
		qry.setParameter(++qryIdx, org);
		qry.append(" AND DST.SRVARPCOD = ? ");
		qry.setParameter(++qryIdx, destn);
		qry.append(" AND CNTDOCNUM = ? ");
		qry.setParameter(++qryIdx, ctrlDocNum);
		if (pou != null) {
			qry.append(" AND ASGFLT.POU = ? ");
			qry.setParameter(++qryIdx, pou);
		}
		if (av7PO != null) {
			qry.append(" AND MALCLS IN ( ");
			StringBuilder malClsBldr = new StringBuilder();
			if (MailConstantsVO.MIL_POST_APO.equals(av7PO)) {
				for (String malcls : MailConstantsVO.APO_CODES) {
					malClsBldr.append(" ?,");
					qry.setParameter(++qryIdx, malcls);
				}
			} else if (MailConstantsVO.MIL_POST_FPO.equals(av7PO)) {
				for (String malcls : MailConstantsVO.FPO_CODES) {
					malClsBldr.append(" ?,");
					qry.setParameter(++qryIdx, malcls);
				}
			}
			malClsBldr.deleteCharAt(malClsBldr.length() - 1);
			malClsBldr.append(" ) ");
			qry.append(malClsBldr.toString());
		}
		log.log(Log.FINEST, "final qry for single print ", qry);
	}


	/**
	 * @author A-3227
	 * @param companyCode
	 * @param despatchDetailsVOs
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public DespatchDetailsVO findConsignmentDetailsForDespatch(
			String companyCode,DespatchDetailsVO despatchDetailsVO) throws SystemException,
			PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO","findConsignmentDetailsForDespatch");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DETAILS_FOR_DESPATCH);
		int idx = 0;
		int consignmentSeqNumber = 1;
		log.log(Log.FINE, "--companyCode --- : ", companyCode);
		log
				.log(Log.FINE, "--consignmentSeqNumber --- : ",
						consignmentSeqNumber);
		log.log(Log.FINE, "--despatchDetailsVO.getConsignmentNumber() --- : ",
				despatchDetailsVO.getConsignmentNumber());
		log.log(Log.FINE, "--despatchDetailsVO.getPaCode() --- : ",
				despatchDetailsVO.getPaCode());
		log.log(Log.FINE, "--despatchDetailsVO.getDsn() --- : ",
				despatchDetailsVO.getDsn());
		log.log(Log.FINE,
				"--despatchDetailsVO.getOriginOfficeOfExchange() --- : ",
				despatchDetailsVO.getOriginOfficeOfExchange());
		log.log(Log.FINE,
				"--despatchDetailsVO.getDestinationOfficeOfExchange() --- : ",
				despatchDetailsVO.getDestinationOfficeOfExchange());
		log.log(Log.FINE, "--despatchDetailsVO.getMailCategoryCode() --- : ",
				despatchDetailsVO.getMailCategoryCode());
		log.log(Log.FINE, "--despatchDetailsVO.getMailSubclass() --- : ",
				despatchDetailsVO.getMailSubclass());
		log.log(Log.FINE, "--despatchDetailsVO.getYear() --- : ",
				despatchDetailsVO.getYear());
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, despatchDetailsVO.getConsignmentNumber());
		query.setParameter(++idx, consignmentSeqNumber);
		query.setParameter(++idx, despatchDetailsVO.getPaCode());
		query.setParameter(++idx, despatchDetailsVO.getDsn());
		query.setParameter(++idx, despatchDetailsVO.getOriginOfficeOfExchange());
		query.setParameter(++idx, despatchDetailsVO.getDestinationOfficeOfExchange());
		query.setParameter(++idx, despatchDetailsVO.getMailCategoryCode());
		query.setParameter(++idx, despatchDetailsVO.getMailSubclass());
		query.setParameter(++idx, despatchDetailsVO.getYear());

		return  query.getSingleResult(new DespatchDetailsMapper());
	}

	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO generateConsignmentReport(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findConsignmentDocumentDetails");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		if(MailConstantsVO.CONSIGNMENT_TYPE_AV7.equals(consignmentFilterVO.getConType())){
			consignmentDocumentVO = new ConsignmentDocumentVO();
			Query query = getQueryManager().createNamedNativeQuery(
					GENERATE_CONSIGNMENT_DETAILS_REPORT_FOR_AV7);
			query.setParameter(1, consignmentFilterVO.getCompanyCode());
			query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
			query.setParameter(3, consignmentFilterVO.getPaCode());
			consignmentDocumentVOs =  query
					.getResultList(new ConsignmentDetailsReportMapper());

		}else{
			Query query = getQueryManager().createNamedNativeQuery(

					GENERATE_CONSIGNMENT_DOCUMENT_DETAILS_REPORT);
			query.setParameter(1, consignmentFilterVO.getCompanyCode());
			query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
			query.setParameter(3, consignmentFilterVO.getPaCode());
			consignmentDocumentVOs = query
					.getResultList(new ConsignmentReportDtlsMultimapper());

		}
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		log.log(Log.FINE, " <<=== ConsignmentDocumentVO ===>>",
				consignmentDocumentVO);
		return consignmentDocumentVO;
	}


	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetailsForHHT(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findConsignmentDocumentDetails");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_ROUTING_INFOS);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(3, consignmentFilterVO.getPaCode());
		query.setSensitivity(true);
		List<ConsignmentDocumentVO> consignmentDocumentVOs = query
				.getResultList(new ConsignmentDetailsMultimapper());
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		return consignmentDocumentVO;
	}


	/**Method	    :	findMLDDetails
	 *	Added by 	:   A-5526 on Dec 20, 2015 for CRQ-93584
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return Collection<MLDMasterVO>
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<MLDMasterVO> findMLDDetails(String companyCode,int recordCount)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findScannedMailDetails");
		int index = 0;
		Collection<MLDMasterVO> mLDMasterVOs = null;
		String query = getQueryManager().getNamedNativeQueryString(
				FIND_MLD_DETAILS);
		String dynamicquery = null;
		if(isOracleDataSource()){
			dynamicquery = "AND ROWNUM <= ? ORDER BY TXNTIMUTC ASC	";
		}else {
			dynamicquery = "ORDER BY TXNTIMUTC ASC LIMIT ? ";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, recordCount);
		return qry.getResultList(new MLDMessageDetailsMapper());
	}


	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MailbagVO> findCarditMails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException,PersistenceException{

		log.entering(MODULE, "findCarditMails");
		//PageableQuery<MailbagVO> pgqry = null;
		Page<MailbagVO> mailbagVos = null;
		String  baseQry = getQueryManager().getNamedNativeQueryString(
				FIND_CARDIT_MAILS);

		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.MALIDR) RANK FROM ( ");
		rankQuery.append(baseQry);
		//Modified by A-5220 for ICRD-21098 starts
		PageableNativeQuery<MailbagVO> qry=null;
		if(carditEnquiryFilterVO.getPageSize()==0) //Modified by A-8164 for ICRD-320122
			qry = new CarditMailsFilterQuery(new CarditMailsMapper(), carditEnquiryFilterVO, rankQuery.toString());
		else
			qry = new CarditMailsFilterQuery(new CarditMailsMapper(), carditEnquiryFilterVO, rankQuery.toString(),"MTK056");
		log.log(Log.INFO, "Query: ", qry);
		//Modified by A-5220 for ICRD-21098 ends
		if(MailConstantsVO.FLAG_YES.equals(carditEnquiryFilterVO.getConsignmentLevelAWbAttachRequired())){
			List<MailbagVO>  mails=qry.getResultList(new CarditMailsMapper());
			return new Page<MailbagVO>(
					mails, 0, 0, 0, 0, 0, false);
		}else{
			return qry.getPage(pageNumber);
		}
	}



	/**
	 * @author A-2107
	 * @param consignmentFilterVO
	 * @throws SystemException
	 */
	public Collection<MailbagVO> findCartIdsMailbags(ConsignmentFilterVO consignmentFilterVO)throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCartIdsMailbags");
		Query qry = getQueryManager().createNamedNativeQuery(GET_CARDIT_MAILBAGDTLS);
		int idx = 0;
		qry.setParameter(++idx, consignmentFilterVO.getCompanyCode());
		qry.setParameter(++idx, consignmentFilterVO.getConsignmentFromDate().toDisplayDateOnlyFormat());
		qry.setParameter(++idx, consignmentFilterVO.getConsignmentToDate().toDisplayDateOnlyFormat());
		if (Objects.nonNull(consignmentFilterVO.getContainerJourneyId()) && !consignmentFilterVO.getContainerJourneyId().isEmpty()) {
			qry.append(" AND CSGDTL.MALJNRIDR = ?");
			qry.setParameter(++idx, consignmentFilterVO.getContainerJourneyId());
		}
		if (Objects.nonNull(consignmentFilterVO.getBellyCartId()) && !consignmentFilterVO.getBellyCartId().isEmpty()) {
			qry.append(" AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++idx, consignmentFilterVO.getBellyCartId());
		}
		if (Objects.nonNull(consignmentFilterVO.getContainerNumber()) && !consignmentFilterVO.getContainerNumber().isEmpty()) {
			qry.append(" AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++idx, consignmentFilterVO.getContainerNumber());
		}
		log.exiting("MailTrackingDefaultsSqlDAO", "findCartIdsMailbags");
		return qry.getResultList(new CarditMailbagDetailsMapper());
	}
	/**
	 * @author A-5991
	 * @param companyCode
	 * @param mailbagID
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public CarditReceptacleVO findDuplicateMailbagsInCardit(String companyCode,
															String mailbagID)throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findDuplicateMailbagsInCardit");
		CarditReceptacleVO carditReceptacleVO  = new CarditReceptacleVO();
		Query query = getQueryManager().createNamedNativeQuery(FIND_DUPLICATE_MAILBAGS_IN_CARDIT);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, mailbagID);
		String oldCarditKey="";
		//	oldCarditKey=query.getSingleResult(getStringMapper("CDTKEY"));
		carditReceptacleVO = query.getSingleResult((new CarditReceptacleMapper()));
		log.log(Log.FINE,"oldCarditKey::"+oldCarditKey);
		log.exiting("MailTrackingDefaultsSqlDAO","findDuplicateMailbagsInCardit");
		return carditReceptacleVO;
	}


	/**
	 * @author a-1936 This method is used to check wether the Transportation
	 *         Details already exist for the Particular Cardit
	 * @param companyCode
	 * @param carditKey
	 * @param carditTransportationVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkCarditTransportExists(String companyCode,
											  String carditKey, CarditTransportationVO carditTransportationVO)
			throws SystemException, PersistenceException {
		log
				.entering("MailTrackingDefaultsSqlDAO",
						"checkCarditTransportExists");
		log.log(Log.FINE,
				" LATEST THE CARDIT TRANSPORTATION VO IN THE SQL DAO ");
		log.log(Log.FINE,
				" LATEST THE CARDIT TRANSPORTATION VO IN THE SQL DAO  IS ",
				carditTransportationVO);
		boolean isFound = false;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSPORT_EXISTS_CARDIT);
		int idx = 0;
		qry.setParameter(++idx, companyCode);
		qry.setParameter(++idx, carditKey);
		qry.setParameter(++idx, carditTransportationVO.getCarrierCode());
		if (carditTransportationVO.getFlightNumber() != null
				&& carditTransportationVO.getFlightNumber().trim().length() > 0) {
			qry.append(" AND FLTNUM = ?  ");
			qry.setParameter(++idx, carditTransportationVO.getFlightNumber());
		}
		if (carditTransportationVO.getDeparturePort() != null
				&& carditTransportationVO.getDeparturePort().trim().length() > 0) {
			qry.append(" AND ORGCOD  = ?  ");
			qry.setParameter(++idx, carditTransportationVO.getDeparturePort());
		}
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findArrivalDetailsForMailbag");
		String carditKeyForTransport = qry
				.getSingleResult(getStringMapper("CDTKEY"));
		if (carditKeyForTransport != null
				&& carditKeyForTransport.trim().length() > 0) {
			isFound = true;
		}
		log.log(Log.FINE, "TRANSPORT EXISTS FOR CARDIT", isFound);
		return isFound;
	}

	/**
	 * @author A-2037 This method is used to find Local PAs
	 * @param companyCode
	 * @param countryCode
	 * @return Collection<PostalAdministrationVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode,
														   String countryCode) throws SystemException, PersistenceException {
		log.entering(MODULE, "findLocalPAs");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_LOCALPAS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, countryCode);
		return query.getResultList(new LocalPAMapper());
	}

	/**
	 * @author A-5991
	 */
	public ContainerAssignmentVO findContainerDetailsForMRD(
			OperationalFlightVO opFltVo, String mailBag)  throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findContainerAssignment");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				"mail.operations.findContainerDetailsForMRD");
		qry.setParameter(++index, opFltVo.getCompanyCode());
		qry.setParameter(++index, opFltVo.getFlightNumber());
		qry.setParameter(++index, opFltVo.getCarrierCode());
		qry.setParameter(++index, opFltVo.getFlightSequenceNumber());
		qry.setParameter(++index, opFltVo.getPou());
		qry.setParameter(++index, mailBag);
		containerAssignMentVO = qry
				.getSingleResult(new ContainerAssignmentMapper());
		return containerAssignMentVO;
	}


	/**
	 * @author A-1936 This method is used to find all the MailBags tat can be
	 *         FlaggedUpliftedResdit
	 * @param operationalFlightVO
	 * @param mailbagVos
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */

	public Collection<MailbagVO> validateMailBagsForUpliftedResdit(
			OperationalFlightVO operationalFlightVO,
			Collection<MailbagVO> mailbagVos) throws SystemException,
			PersistenceException {
		String mailID = null;
		Collection<MailbagVO> validMailBagVos = null;
		for (MailbagVO mailBag : mailbagVos) {
			int index = 0;
			Query query = getQueryManager().createNamedNativeQuery(
					FIND_VALIDMAILBAGS_FORUPLIFTEDRESDIT);
			query.setParameter(++index, operationalFlightVO.getCompanyCode());
			query.append(" AND  MALIDR = ?  ");
			query.setParameter(++index, mailBag.getMailbagId());
			query.append(" AND EVTCOD = ? ");
			query.setParameter(++index, MailConstantsVO.RESDIT_UPLIFTED);
			mailID = query.getSingleResult(getStringMapper("MALIDR"));
			if (mailID == null) {
				if (validMailBagVos == null) {
					validMailBagVos = new ArrayList<MailbagVO>();
				}
				validMailBagVos.add(mailBag);
			}
		}
		log.log(Log.FINE, "THE ValidMailBagsForResdit in ", validMailBagVos);
		return validMailBagVos;
	}



	public Collection<String> validateMailBagsForUPL(
			FlightValidationVO flightValidationVO) throws SystemException,
			PersistenceException {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(VALIDATE_MAILBAG_UPL);
		query.setParameter(++index, flightValidationVO.getCompanyCode());
		query.setParameter(++index, flightValidationVO.getFlightCarrierId());
		query.setParameter(++index, flightValidationVO.getFlightSequenceNumber());
		query.setParameter(++index, flightValidationVO.getFlightNumber());
		return query.getResultList(getStringMapper("MALIDR"));
	}



	/**
	 *
	 * @author a-1936 This method is used to find the MailBags for a DST-CTG +
	 *         CONNUM+CAR+ARP Added as the Part of NCA CR
	 * @param mailInInventoryListVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagsForInventory(
			MailInInventoryListVO mailInInventoryListVo)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailBagsForInventory");
		int index = 0;
		Query qry = null;
		if (MailConstantsVO.ULD_TYPE.equals(mailInInventoryListVo
				.getContainerType())) {
			if (mailInInventoryListVo.getFromFlightNumber() != null
					&& mailInInventoryListVo.getFromFlightNumber().trim()
					.length() > 0) {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_INVENTORYMAILBAG_ARRIVAL_DTLS_ULD);
			} else {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_INVENTORY_MAILBAGSFORREASSIGN_ULD);
			}
		} else {
			if (mailInInventoryListVo.getFromFlightNumber() != null
					&& mailInInventoryListVo.getFromFlightNumber().trim()
					.length() > 0) {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_INVENTORYMAILBAG_ARRIVAL_DTLS_BULK);
			} else {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_INVENTORY_MAILBAGSFORREASSIGN_BULK);
			}
		}
		qry.append("  WHERE  MALMST.CMPCOD =  ? ");
		qry.append("  AND    MALMST.SCNPRT=  ?   ");
		qry.append("  AND    ARPULD.FLTCARIDR=  ? ");
		qry.append("  AND    MALMST.CONNUM=  ? ");
		qry.append("  AND    SUBSTR(MALMST.DSTEXGOFC,3,3) =  ? ");
		qry.append("  AND    ARPULD.DSTCOD IS  NULL ");
		qry.setParameter(++index, mailInInventoryListVo.getCompanyCode());
		qry.setParameter(++index, mailInInventoryListVo.getCurrentAirport());
		qry.setParameter(++index, mailInInventoryListVo.getCarrierID());
		qry.setParameter(++index, mailInInventoryListVo.getUldNumber());
		qry.setParameter(++index, mailInInventoryListVo.getDestinationCity());

		if (mailInInventoryListVo.getOriginPA() != null
				&& mailInInventoryListVo.getOriginPA().trim().length() > 0) {
			qry.append("  AND ORGPA.POACOD = ? ");
			qry.setParameter(++index, mailInInventoryListVo.getOriginPA());
		}
		/*
		 * Append the Incoming Arrival Flight if Present in the Filter .
		 */
		if (mailInInventoryListVo.getFromFlightNumber() != null
				&& mailInInventoryListVo.getFromFlightNumber().trim().length() > 0) {
			qry.append(" AND MALHIS.MALSTA = ?  ");
			qry.append(" AND MALHIS.FLTNUM = ?  ");
			qry.append(" AND MALHIS.FLTCARCOD = ?  ");
			qry.append(" AND MALHIS.FLTDAT = ?  ");
			qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
			qry.setParameter(++index, mailInInventoryListVo
					.getFromFlightNumber());
			qry.setParameter(++index, mailInInventoryListVo
					.getFromFlightCarrierCode());
			qry
					.setParameter(++index, mailInInventoryListVo
							.getFromFlightDate());
		} else {
			/*
			 * Added By Karthick V as the part of the NCA Mail Tracking Bug Fix
			 *
			 */
			qry.append("  AND  NOT EXISTS  ( SELECT  1 FROM   MALHIS  ");
			qry
					.append("  WHERE CMPCOD= MALMST.CMPCOD AND  MALIDR=MALMST.MALIDR and SCNPRT=MALMST.SCNPRT AND MALSTA ='ARR') ");
		}
		if (MailConstantsVO.MIL_MAL_CAT.equals(mailInInventoryListVo
				.getMailCategoryCode())) {
			qry.append(appendMilitaryClasses(qry, index, true));
		} else {
			qry.append(" AND MALMST.MALCTG = ? ");
			qry.setParameter(++index, mailInInventoryListVo
					.getMailCategoryCode());
			qry.append(appendMilitaryClasses(qry, index, false));
		}
		return qry.getResultList(new MailbagForInventoryMapper());
	}


	private String appendMilitaryClasses(Query qry, int index, boolean isMil) {
		StringBuilder milQry = new StringBuilder();
		if (isMil) {
			milQry.append(" AND MALMST.MALCLS IN ( ");
		} else {
			milQry.append(" AND MALMST.MALCLS NOT IN ( ");
		}
		for (String mailClz : MailConstantsVO.MILITARY_CLASS) {
			milQry.append(" ?,");
			qry.setParameter(++index, mailClz);
		}
		milQry.deleteCharAt(milQry.length() - 1);
		milQry.append(" ) ");
		return milQry.toString();
	}


	/**
	 *
	 * @author a-1936 This method is used to find the MailBags for a DST-CTG +
	 *         CONNUM+CAR+ARP Added as the Part of NCA CR
	 * @param mailInInventoryListVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailsForDeliveryFromInventory(
			MailInInventoryListVO mailInInventoryListVo)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailsForDeliveryFromInventory");
		int index = 0;
		Query qry = null;
		qry = getQueryManager().createNamedNativeQuery(
				FIND_ARRIVED_MAILDETAILS_FORINVENTORY);

		qry.setParameter(++index, mailInInventoryListVo.getCompanyCode());
		qry.setParameter(++index, mailInInventoryListVo.getCurrentAirport());
		qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
		qry.setParameter(++index, mailInInventoryListVo.getDestinationCity());
		qry.setParameter(++index, mailInInventoryListVo.getCarrierID());
		qry.setParameter(++index, mailInInventoryListVo.getUldNumber());
		qry.setParameter(++index, mailInInventoryListVo.getContainerType());

		if (mailInInventoryListVo.getOriginPA() != null
				&& mailInInventoryListVo.getOriginPA().trim().length() > 0) {
			qry.append(" AND ORGPA.POACOD = ? ");
			qry.setParameter(++index, mailInInventoryListVo.getOriginPA());
		}
		if (mailInInventoryListVo.getFromFlightCarrierCode() != null) {
			qry.append(" AND HIS.FLTCARCOD = ? ")
					.append(" AND HIS.FLTNUM = ? ").append(
							" AND TRUNC(HIS.FLTDAT) = TRUNC(?) ");
			qry.setParameter(++index, mailInInventoryListVo
					.getFromFlightCarrierCode());
			qry.setParameter(++index, mailInInventoryListVo
					.getFromFlightNumber());
			qry.setParameter(++index, mailInInventoryListVo.getFromFlightDate()
					.toCalendar());
		}
		if (MailConstantsVO.MIL_MAL_CAT.equals(mailInInventoryListVo
				.getMailCategoryCode())) {
			qry.append(appendMilitaryClasses(qry, index, true));
		} else {
			qry.append(" AND HIS.MALCTGCOD = ? ");
			qry.setParameter(++index, mailInInventoryListVo
					.getMailCategoryCode());
			qry.append(appendMilitaryClasses(qry, index, false));
		}
		log.exiting("MailTrackingDefaulstSQLDAO",
				"findMailsForDeliveryFromInventory");
		return qry.getResultList(new MailbagForInventoryMapper());

	}


	/**
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findFlightLegSerialNumber(com.ibsplc.icargo.business.mail.operations.vo.ContainerVO)
	 */
	public int findFlightLegSerialNumber(ContainerVO containerVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findFlightLegSerialNumber");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_FLIGHT_LEGSERNUM_FOR_CONTAINER);
		int idx = 0;
		query.setParameter(++idx, containerVO.getCompanyCode());
		query.setParameter(++idx, containerVO.getCarrierId());
		query.setParameter(++idx, containerVO.getFlightNumber());
		query.setParameter(++idx, containerVO.getFlightSequenceNumber());
		query.setParameter(++idx, containerVO.getAssignedPort());
		query.setParameter(++idx, containerVO.getContainerNumber());
		log.exiting("MailTrackingDefaultsSqlDAO", "findFlightLegSerialNumber");
		return query.getSingleResult(getIntMapper("LEGSERNUM"));
	}

	/**
	 * @author A-5991
	 */
	public long findResditSequenceNumber(MailResditVO mailResditVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackkingDefaultsSQLDAO", "findResditSequenceNumber");
		int idx = 0;
		long sequenceNumber = 0;
		StringBuilder qryBuilder = null;
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_RESDIT_EXISTS);
		query.setParameter(++idx, mailResditVO.getCompanyCode());
		query.setParameter(++idx, mailResditVO.getMailSequenceNumber());
		//Modified by A-7794 as part of ICRD-224613
		//query.setParameter(++idx, mailResditVO.getEventAirport());
		if(mailResditVO.getEventCode()!=null){
			query.append(" AND  MMR.EVTCOD = ? ");
			query.setParameter(++idx, mailResditVO.getEventCode());
		}
		sequenceNumber = query.getSingleResult(getLongMapper("SEQNUM"));
		log.exiting("MailTrackkingDefaultsSQLDAO", "findResditSequenceNumber");
		return sequenceNumber;


	}


	public Collection<MailInConsignmentVO> findConsignmentDetailsForDsn(
			String companyCode,DSNVO dsnVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findMailbagDetailsForSBUldsFromCardit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_DETAILS_FOR_DSN);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, 1);
		query.setParameter(++idx, dsnVO.getPaCode());
		query.setParameter(++idx, dsnVO.getDsn());
		query.setParameter(++idx, dsnVO.getOriginExchangeOffice());
		query.setParameter(++idx, dsnVO.getDestinationExchangeOffice());
		query.setParameter(++idx, dsnVO.getMailCategoryCode());
		query.setParameter(++idx, dsnVO.getMailSubclass());
		query.setParameter(++idx, dsnVO.getYear());
		log.exiting("MailTrackingDefaultsSqlDAO","findMailbagDetailsForSBUldsFromCardit");
		return query.getResultList(new  ConsignmentDetailsMapper());
	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#validateMailFlight
	 *	Added by 			: A-5160 on 26-Nov-2014
	 * 	Used for 	:	validating flight for mail
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findAirportCityForMLD");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(VALIDATE_MAILFLIGHT);
		query.setParameter(++index, flightFilterVO.getCompanyCode());
		query.setParameter(++index, flightFilterVO.getFlightCarrierId());
		query.setParameter(++index, flightFilterVO.getFlightNumber());
		query.setParameter(++index, flightFilterVO.getFlightDate());
		query.setParameter(++index, flightFilterVO.getStation());
		Collection<FlightValidationVO> flightValidationVOs = query.getResultList(new MailFlightMapper());
		log.entering("MailTrackingDefaultsSqlDAO", "findAirportCityForMLD");
		return flightValidationVOs;
	}
	/**
	 *
	 * @author A-5160
	 *
	 */
	private static class MailFlightMapper implements Mapper<FlightValidationVO> {

		public FlightValidationVO map(ResultSet rs) throws SQLException {
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			flightValidationVO.setCompanyCode(rs.getString("CMPCOD"));
			flightValidationVO.setFlightNumber(rs.getString("FLTNUM"));
			flightValidationVO.setAirportCode(rs.getString("ARPCOD"));
			flightValidationVO.setFlightDate(new LocalDate(flightValidationVO.getAirportCode(),Location.ARP,rs.getDate("FLTDAT")));
			flightValidationVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			flightValidationVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			flightValidationVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
			flightValidationVO.setCarrierCode(rs.getString("FLTCARCOD"));
			return flightValidationVO;
		}

	}
	/**
	 * @author A-2037 This method is used to find the accepted ULDs
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findAcceptedULDs(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findAcceptedULDs");
		int index = 0;
		Collection<ContainerDetailsVO> coll = null;
		Query qry = null;
		if (operationalFlightVO.getFlightNumber() != null
				&& operationalFlightVO.getFlightSequenceNumber() > 0) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_FLIGHT_ACCEPTANCEDETAILS);
			qry.setParameter(++index, operationalFlightVO.getCompanyCode());
			qry.setParameter(++index, operationalFlightVO.getCarrierId());
			qry.setParameter(++index, operationalFlightVO.getFlightNumber());
			qry.setParameter(++index, operationalFlightVO
					.getFlightSequenceNumber());
			//qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
			qry.setParameter(++index, operationalFlightVO.getPol());
			qry.setParameter(++index, operationalFlightVO.getCompanyCode());
			qry.setParameter(++index, operationalFlightVO.getCarrierId());
			qry.setParameter(++index, operationalFlightVO.getFlightNumber());
			qry.setParameter(++index, operationalFlightVO
					.getFlightSequenceNumber());

			//qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
			qry.setParameter(++index, operationalFlightVO.getPol());


		} else {
			String acceptedUlds = getQueryManager().getNamedNativeQueryString(
					FIND_CARRIER_ACCEPTANCEDETAILS_ULD);
			String acceptedContainers = getQueryManager()
					.getNamedNativeQueryString(
							FIND_CARRIER_ACCEPTANCEDETAILS_BULK);
			String emptyContainers = getQueryManager()
					.getNamedNativeQueryString(
							FIND_CARRIER_ACCEPTANCEDETAILS_EMPTY);
			qry = new AcceptanceDetailsForCarrierFilterQuery(acceptedUlds,
					acceptedContainers, emptyContainers, operationalFlightVO);
		}

		if (operationalFlightVO.getFlightNumber() != null
				&& operationalFlightVO.getFlightSequenceNumber() > 0) {
			coll = qry.getResultList(new FlightAcceptanceDetailsMultiMapper());
		} else {
			coll = qry.getResultList(new CarrierAcceptanceDetailsMultimapper());
		}
		return coll;
	}
	/**
	 * @author A-3227 - AUG 12, 2008
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkRoutingsForMails(OperationalFlightVO operationalFlightVO,DSNVO dSNVO,String type)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "checkRoutingsForMails");
		boolean routingAvailable=false;
		Query  baseQry = getQueryManager().createNamedNativeQuery(FIND_ROUTINGS_FOR_MAILBAG);
		/*
					routingAvailable= true;
			}*/ // Commented for Bug ICRD-243570

		int index = 0;

		if(operationalFlightVO.getCompanyCode()!=null){
			baseQry.setParameter(++index, operationalFlightVO.getCompanyCode());
		}else{
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, operationalFlightVO.getCarrierId());
		if(operationalFlightVO.getFlightNumber()!=null){
			baseQry.setParameter(++index, operationalFlightVO.getFlightNumber());
		}else{
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		if(dSNVO.getContainerNumber()!=null){
			baseQry.setParameter(++index, dSNVO.getContainerNumber());
		}else{
			baseQry.setParameter(++index, "");
		}
		if(dSNVO.getDsn()!=null){
			baseQry.setParameter(++index, dSNVO.getDsn());
		}else{
			baseQry.setParameter(++index, "");
		}
		if(dSNVO.getOriginExchangeOffice()!=null){
			baseQry.setParameter(++index, dSNVO.getOriginExchangeOffice());
		}else{
			baseQry.setParameter(++index, "");
		}
		if(dSNVO.getDestinationExchangeOffice()!=null){
			baseQry.setParameter(++index, dSNVO.getDestinationExchangeOffice());
		}else{
			baseQry.setParameter(++index, "");
		}
		baseQry.setParameter(++index, dSNVO.getYear());
		if(dSNVO.getMailCategoryCode()!=null){
			baseQry.setParameter(++index, dSNVO.getMailCategoryCode());
		}else{
			baseQry.setParameter(++index, "");
		}
		if(dSNVO.getMailSubclass()!=null){
			baseQry.setParameter(++index, dSNVO.getMailSubclass());
		}else{
			baseQry.setParameter(++index, "");
		}
		if(baseQry.getSingleResult(getIntMapper("ROUTE"))>0){
			routingAvailable= true;
		}
		log.log(Log.INFO, "ROUTE PRESENT---> ", routingAvailable);
		log.exiting("MailTrackingDefaultsSqlDAO", "checkRoutingsForMails");
		return routingAvailable;
	}
	/**
	 * @author A-2037 Method for OfficeOfExchangeLOV containing code and
	 *         description
	 * @param companyCode
	 * @param code
	 * @param description
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,int defaultSize) throws SystemException, PersistenceException {
		log.entering(MODULE, "findOfficeOfExchangeLov");
		// modified the method by A-5103 for ICRD-32647
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		int index = 0;
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_OFFICEOFEXCHANGE_LOV);
		masterQuery.append(queryString);
		if(defaultSize==0){
			defaultSize=25;
		}
		PageableNativeQuery<OfficeOfExchangeVO> pgNativeQuery =
				new PageableNativeQuery<OfficeOfExchangeVO>(defaultSize,-1, masterQuery.toString(),
						new OfficeOfExchangeLovMapper());
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
				pgNativeQuery.append("AND EXGOFCCOD LIKE").append(new StringBuilder().append("'")
						.append(code).append("'").toString());
			}
		}

		if (description != null && description.length() > 0) {
			description = description.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE) +
					MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append(" AND UPPER(EXGCODDES) LIKE ?");
			pgNativeQuery.setParameter(++index, description.toUpperCase());
		}
		if(officeofExchangeVO.getAirportCode() != null && officeofExchangeVO.getAirportCode().length() > 0){
			pgNativeQuery.append(" AND ARPCOD LIKE ?");    //Modified as part of IASCB-53411
			pgNativeQuery.setParameter(++index, officeofExchangeVO.getAirportCode().toUpperCase());
		}
		if(officeofExchangeVO.getPoaCode() != null && officeofExchangeVO.getPoaCode().length() > 0){
			pgNativeQuery.append(" AND POACOD LIKE ?");    //Modified as part of IASCB-53411
			pgNativeQuery.setParameter(++index, officeofExchangeVO.getPoaCode().toUpperCase());
		}
		pgNativeQuery.append(" ORDER BY EXGOFCCOD ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}
	/**
	 *
	 * 	Method		:	MailTrackingDefaultsSqlDAO.getWhereClause
	 *	Added by 	:	A-4803 on 12-May-2015
	 * 	Used for 	:
	 *	Parameters	:	@param sccCodes
	 *	Parameters	:	@return
	 *	Return type	: 	String
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

	private String getEnhancedWhereClause(String[] sccCodes, int offset, String column) {
		StringBuilder buffer = new StringBuilder();
		int startIndex = 0;
		int limit = offset;
		buffer.append(" AND ( ").append(column).append(" IN ('");
		for(int i=startIndex;i<limit;i++) {
			if(i == limit-1) {
				buffer = new StringBuilder(buffer.substring(0, buffer.length() - 3).trim()).append("'");
				buffer.append(") OR ").append(column).append(" IN ('");
				startIndex = limit-1;
				limit = offset + limit;
				if(limit > sccCodes.length) {
					limit = sccCodes.length;
				}
			}
			buffer.append(sccCodes[i]).append("','");
		}
		return buffer.toString().substring(0, buffer.length() - 3).trim() + "')";
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
	public Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode, String code,
														String description, int pageNumber,int defaultSize) throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailSubClassCodeLov");
		// modified the method by A-5103 for ICRD-32647
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		int index = 0;
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAILSUBCLASSCODES);
		masterQuery.append(queryString);
		if(defaultSize==0){
			defaultSize=25;
		}
		PageableNativeQuery<MailSubClassVO> pgNativeQuery = new PageableNativeQuery<MailSubClassVO>(defaultSize,-1,
				masterQuery.toString(), new MailSubClassCodeMapper());
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
			description = description.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE) +
					MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append("AND UPPER(MTK.DES) LIKE ?");
			pgNativeQuery.setParameter(++index, description.toUpperCase());
		}
		pgNativeQuery.append(" ORDER BY SUBCLSCOD ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}


	/**
	 * @author A-2037 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailSubClassCodes");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_PACODE);


		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, paCode);

		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, paCode);

		return qry.getResultList(new PACodeMultiMapper()).get(0);
	}


	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findULDsInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findULDsInAssignedFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ULDS_IN_ASSIGNED_FLIGHT);
		boolean isOracleDataSource = isOracleDataSource();
		if(isOracleDataSource){
			qry.append("AND substr(ULD.ULDNUM, 0, 4) <> 'BULK'");
		} else{
			qry.append("AND substr(ULD.ULDNUM, 0, 5) <> 'BULK'");
		}
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.exiting("MailTrackingDefaultsSqlDAO", "findULDsInAssignedFlight");
		return qry.getResultList(new ContainerMapper());

	}

	/**
	 * @author a-1936
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findContainersInFlightForManifest(
			OperationalFlightVO operationalFlightVo) throws SystemException,
			PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findContainersInFlight");
		MailManifestVO mailManifestVo = new MailManifestVO();
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ULDS_INFLIGHT_FOR_MANIFEST);
		int idx = 0;

		query.setParameter(++idx, operationalFlightVo.getCompanyCode());
		query.setParameter(++idx, operationalFlightVo.getCarrierId());
		query.setParameter(++idx, operationalFlightVo.getFlightNumber());
		query.setParameter(++idx, operationalFlightVo.getFlightSequenceNumber());

		query.setParameter(++idx, operationalFlightVo.getCompanyCode());
		query.setParameter(++idx, operationalFlightVo.getCarrierId());
		query.setParameter(++idx, operationalFlightVo.getFlightNumber());
		query.setParameter(++idx, operationalFlightVo.getFlightSequenceNumber());
		query.setParameter(++idx, operationalFlightVo.getPol());

		mailManifestVo.setContainerDetails(query
				.getResultList(new ContainersForManifestMultiMapper()));
		log.exiting("MailTrackingDefaultsSqlDAO", "findULDsForManifest");
		return mailManifestVo;

	}

	/**
	 * @author a-1936
	 * This  method is used to find the mailbags in the Container for the Manifest
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(Collection<ContainerDetailsVO> containers)
			throws SystemException,PersistenceException{
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<ContainerDetailsVO>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_MAILBAGS_MANIFEST_ULD);
			} else {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_MAILBAGS_MANIFEST_BULK);
			}
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			qry.setParameter(++idx,
					MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			qry
					.setParameter(++idx,
							MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getSegmentSerialNumber());
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getContainerNumber());
			List<ContainerDetailsVO> list = qry
					.getResultList(new AcceptedDSNsForManifestMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
			}
		}
		return containerForReturn;
	}

	/**
	 * findContainersInAssignedFlight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findContainersInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findContainersInAssignedFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINERS_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.exiting("MailTrackingDefaultsSqlDAO", "findContainersInAssignedFlight");
		return qry.getResultList(new ContainerMapper());

	}


	/**
	 * @author a-1883
	 * @param aWBFilterVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findAWBDetails");
		Query query = getQueryManager()
				.createNamedNativeQuery(FIND_AWB_DETAILS);
		query.setParameter(1, aWBFilterVO.getCompanyCode());
		query.setParameter(2, aWBFilterVO.getUldNumber());
		query.setParameter(3, aWBFilterVO.getDocumentOwnerIdentifier());
		query.setParameter(4, aWBFilterVO.getMasterDocumentNumber());
		return query.getSingleResult(new AWBDetailsMapper());
	}

	/**
	 * @author a-1936 This method is used to find out all the MailBags that has
	 *         been Manifested to the Closed Flight .. Required For Monitoring
	 *         the Service Level Activity For the MailBags..
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findMailBagsInClosedFlight(
			OperationalFlightVO operationalFlightVo) throws SystemException,
			PersistenceException {
		log
				.entering("MailTrackingDefaultsSqlDAO",
						"findMailBagsInClosedFlight");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_CLOSEDFLIGHT_MONIITORSLA);
		int index = 0;
		query.setParameter(++index, operationalFlightVo.getCompanyCode());
		query.setParameter(++index, operationalFlightVo.getCarrierId());
		query.setParameter(++index, operationalFlightVo.getFlightNumber());
		query.setParameter(++index, operationalFlightVo
				.getFlightSequenceNumber());
		query.setParameter(++index, operationalFlightVo.getLegSerialNumber());
		query.setParameter(++index, operationalFlightVo.getPol());
		Mapper<String> stringMapper = getStringMapper("MALIDR");
		Collection<String> mailIds = query.getResultList(stringMapper);
		log.exiting("MailTrackingDefaultsSqlDAO", "findMailBagsInClosedFlight");
		return mailIds;
	}
	/**
	 * @author a-1936 This method is used to find the offload details
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findAcceptedContainersForOffload(
			OffloadFilterVO offloadFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "FindAcceptedContainersForOffload");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				FIND_OFFLOAD_DETAILS);
		Query qry = new OffloadContainerFilterQuery(offloadFilterVO, baseQuery);
		return qry.getResultList(new OffloadContainerMultiMapper());
	}


	/**
	 * This method is used to find all the DSNS,that can be Offloaded for a
	 * ParticularFlight
	 *
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<DespatchDetailsVO> findAcceptedDespatchesForOffload(OffloadFilterVO offloadFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findAcceptedDespatchesForOffload");
		if(offloadFilterVO.getDefaultPageSize() == 0){
			String baseQuery = getQueryManager().getNamedNativeQueryString(
					FIND_OFFLOAD_DETAILS);
			Query qry =null;
			PageableQuery<DespatchDetailsVO> pageQuery= null;

			qry =new OffloadDespatchesFilterQuery(offloadFilterVO, baseQuery);
			pageQuery = new PageableQuery<DespatchDetailsVO>(qry, new OffloadDSNMapper());

			Page<DespatchDetailsVO> despatchDetailsPageVOs =pageQuery.getPage(offloadFilterVO.getPageNumber());

			if (despatchDetailsPageVOs != null && despatchDetailsPageVOs.size() > 0) {
				for (DespatchDetailsVO despatchDetailsVO : despatchDetailsPageVOs) {
					despatchDetailsVO
							.setFlightDate(offloadFilterVO.getFlightDate());
					despatchDetailsVO.setLegSerialNumber(offloadFilterVO
							.getLegSerialNumber());
				}
			}
			return despatchDetailsPageVOs;
		}
		else{
			int pageSize = offloadFilterVO.getDefaultPageSize();
			int totalRecords=offloadFilterVO.getTotalRecords();
			StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
			Query offloadDSNquery = getQueryManager().createNamedNativeQuery(FIND_DSN_OFFLOAD_DETAILS);
			rankQuery.append(offloadDSNquery);
			PageableNativeQuery<DespatchDetailsVO> query = new OffloadDespatchesUXFilterQuery(pageSize,totalRecords, new OffloadDSNMapper(), rankQuery.toString(), offloadFilterVO);
			query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
			return query.getPage(offloadFilterVO.getPageNumber());

		}

	}

	/**
	 * This method is used to find all the MailBags that can be Offloaded for a
	 * ParticularFlight
	 *
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<MailbagVO> findAcceptedMailBagsForOffload(
			OffloadFilterVO offloadFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findAcceptedMailBagsForOffload");
		String baseQuery = getQueryManager().getNamedNativeQueryString(OFFLOAD_MAILBAGS);
		if(offloadFilterVO.getDefaultPageSize() == 0){

			Query qry = null;
			PageableQuery<MailbagVO> pageQuery= null;

			qry = new OffloadMailBagsFilterQuery(offloadFilterVO, baseQuery);
			pageQuery = new PageableQuery<MailbagVO>(qry, new OffloadMailBagMapper());


				/*if (mailbagPageVOs != null && mailbagPageVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagPageVOs) {
						mailbagVO.setFlightDate(offloadFilterVO.getFlightDate());
						mailbagVO.setLegSerialNumber(offloadFilterVO
								.getLegSerialNumber());
					}
				}*/
			return pageQuery.getPage(offloadFilterVO.getPageNumber());
		}
		else{
			int pageSize = offloadFilterVO.getDefaultPageSize();
			int totalRecords=offloadFilterVO.getTotalRecords();
			StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
			Query offloadMailquery = getQueryManager().createNamedNativeQuery(OFFLOAD_MAILBAGS);
			rankQuery.append(offloadMailquery);

			PageableNativeQuery<MailbagVO> query = new OffloadMailBagsUXFilterQuery(pageSize,totalRecords, new OffloadMailBagMapper(), rankQuery.toString(), offloadFilterVO);
			query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
			return query.getPage(offloadFilterVO.getPageNumber());

		}


	}

	/**
	 * @author a-1936
	 * This  method is used to find the Transfer Manifest Details
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
			throws SystemException, PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "findTransferManifest");
		Page<TransferManifestVO>  transferManifestVos = null;
		String baseQry = getQueryManager().getNamedNativeQueryString(
				FIND_MAIL_TRANSFERMANIFEST);
		//Added by A-5220 for ICRD-21098 starts

		StringBuilder rankQuery=new StringBuilder();
		rankQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(baseQry);
		PageableNativeQuery<TransferManifestVO> pgqry = new TransferManifestListFilterQuery(
				new TransferManifestListMapper(),
				tranferManifestFilterVo, rankQuery.toString());
		//Added by A-5220 for ICRD-21098 ends
	 /*pgqry = new PageableQuery<TransferManifestVO>(qry,
			new TransferManifestListMapper());*/
		return pgqry.getPage(tranferManifestFilterVo.getPageNumber());
	}

	/**
	 @author a-2553
	  * Added By pAULSON as the  part of  the Air NewZealand CR...
	  * @param companyCode,transferManifestId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public 	TransferManifestVO  generateTransferManifestReport(String companyCode,String transferManifestId)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "generateTransferManifestReport");
		TransferManifestVO transferManifestVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_DSN_DETAILS);
		int index=0;
		qry.setParameter(++index,companyCode);
		qry.setParameter(++index,transferManifestId);
		List<TransferManifestVO> transferManifestVOs =qry.getResultList(new ListTransferManifestDSNMultiMapper());
		if(transferManifestVOs!=null && transferManifestVOs.size()>0){
			transferManifestVO=transferManifestVOs.get(0);
		}
		return transferManifestVO;
	}


	/**
	 * @author a-1936 This method is used to find the MailBags for the MailBag
	 *         enquiry Screen Also finds the MailBags for a InventoryList
	 *         grouped By Destination-Category
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	/** changed by A-5216
	 * to enable last link and total record count
	 * for Jira Id: ICRD-21098 and ScreenId MTK009
	 */
	public Page<MailbagVO> findMailbags(
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailbags");
		log.entering(MODULE, "findMailbags");
		String baseQuery = null;
		boolean acceptedmailbagFilterQuery=false;
		boolean allmailbagfilterQuery=false;

		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		//rankQuery.append("MALIDR,CSGDOCNUM,CSGSEQNUM ) AS RANK FROM (");


		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		/*
		 * Added By karthick V as a Part of NCA CR to include the Functionality
		 * View MailBags in the InventoryList Screen.. Modified on 10-sep-2007
		 * to include the Functionality show the Incoming flight in the
		 * Inventory..
		 */
				/*if (mailbagEnquiryFilterVO.isInventory()) {
					log.log(Log.INFO, "FOR INVENTORY MAIL BAGS");
					baseQuery = getQueryManager().getNamedNativeQueryString(FIND_INVENTORY_MAILBAGS);
					if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
							&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
									.length() > 0) {
						qry.append(appendArrivedFlightToInventory());
					}
					qry.append("  AND MALMST.CMPCOD = ? ");
					qry.append(" AND MALMST.SCNPRT= ?   ");
					qry.append(" AND ARPULD.FLTCARIDR= ?  ");
					qry.append(" AND ARPULD.DSTCOD  IS  NULL ");
					qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
					qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
					qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierId());
					*//*
		 * Append the Incoming Arrival Flight if Present in the Filter .
		 *
		 *//*
					if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
							&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
									.length() > 0) {
						qry.append(" AND MALHIS.MALSTA = ?  ");
						qry.append(" AND MALHIS.FLTNUM = ?  ");
						qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
						qry.setParameter(++index, mailbagEnquiryFilterVO
								.getFromFlightNumber());
					}

					if (mailbagEnquiryFilterVO.getContainerNumber() != null
							&& mailbagEnquiryFilterVO.getContainerNumber().trim()
									.length() > 0) {
			qry.append(" AND MALMST.CONNUM= ?  ");
						qry.setParameter(++index, mailbagEnquiryFilterVO
								.getContainerNumber());
					}

					if (mailbagEnquiryFilterVO.getDestinationCity() != null
							&& mailbagEnquiryFilterVO.getDestinationCity().trim()
									.length() > 0) {
						qry.append(" AND SUBSTR( MALMST.DSTEXGOFC,3,3) IN");
						qry.append(new StringBuilder("(").append(
								mailbagEnquiryFilterVO.getDestinationCity())
								.append(")").toString());
					}
					log.log(Log.FINE, "THE MAIL BAG ENQUIRY FILTER VO",
							mailbagEnquiryFilterVO);
					if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
							&& mailbagEnquiryFilterVO.getMailCategoryCode().trim()
									.length() > 0) {

						if (MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO
								.getMailCategoryCode())) {
							qry.append(appendMilitaryClasses(qry, index, true));
						} else {
							qry.append(" AND MALMST.MALCTG IN ");
							qry.append(new StringBuilder("(").append(
									mailbagEnquiryFilterVO.getMailCategoryCode())
									.append(")").toString());
							qry.append(appendMilitaryClasses(qry, index, false));
						}
					}
				} else {*/

		if("".equals(mailbagEnquiryFilterVO.getCurrentStatus()) || mailbagEnquiryFilterVO.getCurrentStatus()==null){

			rankQuery.append("result_table.cmpcod,result_table.malidr,result_table.fltcarcod,result_table.fltcaridr,result_table.fltnum,result_table.fltseqnum ) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(
					FIND_MAILDETAILS);
			allmailbagfilterQuery=true;

			log.log(Log.INFO,"The Status is <<ALL>> ");



			rankQuery.append(baseQuery);
			if(mailbagEnquiryFilterVO.getPageSize()==0){ //Modified by A-8353 for ICRD-324698
				pageableNativeQuery= new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString());
			}else{
				pageableNativeQuery= new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),"MTK057");
			}
		}else if (MailConstantsVO.MAIL_STATUS_RETURNED
				.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {

			rankQuery.append("result_table.cmpcod,result_table.malidr) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(
					FIND_MAILBAGS_FORRETURN);
			rankQuery.append(baseQuery);
			if(mailbagEnquiryFilterVO.getPageSize()>0) {
				pageableNativeQuery = new ReturnMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,rankQuery.toString(), mailbagEnquiryFilterVO.getPageSize());
			} else {
				pageableNativeQuery = new ReturnMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,rankQuery.toString());
			}
		} else {

			rankQuery.append("result_table.cmpcod,result_table.malidr,result_table.fltcarcod,result_table.fltcaridr,result_table.fltnum,result_table.fltseqnum ) AS RANK FROM (");
			baseQuery = getQueryManager().getNamedNativeQueryString(
					FIND_MAILBAGS);
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
					.equals(mailbagEnquiryFilterVO
							.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_ASSIGNED
					.equals(mailbagEnquiryFilterVO
							.getCurrentStatus())) {
				log
						.log(Log.INFO,
								"The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
				if(MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())){
					log.log(Log.FINE, "Current Status:- ACP from mailExportList");
					//rankQuery.append(baseQuery);
					acceptedmailbagFilterQuery=true;
					if(mailbagEnquiryFilterVO.getPageSize()>0) {
						pageableNativeQuery = new AcceptedMailBagFilterQuery(
								new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery,mailbagEnquiryFilterVO.getPageSize());
					} else {
						pageableNativeQuery = new AcceptedMailBagFilterQuery(
								new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);
					}
					log.log(Log.FINE, "Base query passed,suffix query not required");
				}else{
					if(( mailbagEnquiryFilterVO.getCarrierId() == 0)
							&& (mailbagEnquiryFilterVO.getFlightNumber() == null || mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)){
						acceptedmailbagFilterQuery=true;
						if(mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
								mailbagEnquiryFilterVO.getFlightNumber()==null){
							rankQuery.append(baseQuery);
							if(mailbagEnquiryFilterVO.getPageSize()>0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString());
							}
						}
						else{
							if(mailbagEnquiryFilterVO.getPageSize()>0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery,mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);
							}
						}
						log.log(Log.FINE, "Base query passed,suffix query not required");
					}else{
						if(mailbagEnquiryFilterVO.getCarrierId()>0 && mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
								mailbagEnquiryFilterVO.getFlightNumber()==null){
							log.log(Log.FINE, "Carrier level and from assign continer");
							acceptedmailbagFilterQuery=true;
							rankQuery.append(baseQuery);
							if(mailbagEnquiryFilterVO.getPageSize()>0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString());
							}
						}else if(mailbagEnquiryFilterVO.getCarrierId()>0 && ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))){
							acceptedmailbagFilterQuery=true;

							baseQuery=baseQuery.concat(", ROWNUM AS RANK");
							if(mailbagEnquiryFilterVO.getPageSize()>0) {


								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery,mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);
							}
						}
						else{
							log.log(Log.FINE, "not from mailExportList,rank query used,suffix query to be added");
							rankQuery.append(baseQuery);
							//acceptedmailbagFilterQuery=true;
							if(mailbagEnquiryFilterVO.getPageSize()>0) {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),mailbagEnquiryFilterVO.getPageSize());
							} else {
								pageableNativeQuery = new AcceptedMailBagFilterQuery(
										new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString());
							}
						}
					}
				}
			} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())
					|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED
					.equals(mailbagEnquiryFilterVO
							.getCurrentStatus())) {
				log.log(Log.INFO, "THE STATUS IS ", mailbagEnquiryFilterVO.getCurrentStatus());
				rankQuery.append(baseQuery);
				if(mailbagEnquiryFilterVO.getPageSize()>0) {
					pageableNativeQuery = new OffloadedMailBagFilterQuery(
							new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new OffloadedMailBagFilterQuery(
							new MailbagMapper(), mailbagEnquiryFilterVO, rankQuery.toString());
				}
			} else if(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())){
				int carrierId = mailbagEnquiryFilterVO.getCarrierId();
				LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
				Date flightSqlDate = null;
				if (flightDate != null) {
					flightSqlDate = flightDate.toSqlDate();
				}
				String flightDateString = String.valueOf(flightSqlDate);
				/* Changed by A-5274 for Bug with id: ICRD-29006
				 * In this block qry replaced with baseQuery, instead of createNamedNativeQuery getNamedNativeQueryString used
				 */
							/*qry = getQueryManager().createNamedNativeQuery(
									FIND_CAP_NOT_ACCPETED_MAILBAGS); */
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_CAP_NOT_ACCPETED_MAILBAGS);
				rankQuery.append(baseQuery);
				if(mailbagEnquiryFilterVO.getPageSize()>0) {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getPageSize(), -1,rankQuery.toString(),new MailbagMapper());
				} else {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),rankQuery.toString(),new MailbagMapper());
				}
				if(carrierId > 0) {
					pageableNativeQuery.append(" AND FLTCARIDR = ? ");
					pageableNativeQuery.setParameter(++index, carrierId);
				}
				if(mailbagEnquiryFilterVO.getCarrierCode() != null &&
						mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());

				}
				if(mailbagEnquiryFilterVO.getFlightNumber() != null &&
						mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTNUM = ? " );
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
				}
				if (flightSqlDate != null) {
					pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
					pageableNativeQuery.setParameter(++index, flightDateString);
				}
				pageableNativeQuery.append(" ) WHERE ");
				if(mailbagEnquiryFilterVO.getCompanyCode() != null &&
						mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
					pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
				}
				if(mailbagEnquiryFilterVO.getContainerNumber() != null &&
						mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
				}
				LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
				LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
				if (scanFromDate != null) {
					pageableNativeQuery.append(" AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD')) >= ? ");
					pageableNativeQuery.setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));//A-9092 as a part of IASCB-74752
				}
				if (scanToDate != null) {
					pageableNativeQuery.append("  AND TO_NUMBER(TO_CHAR(CSGMST.CSGDAT,'YYYYMMDD')) <= ? ");
					pageableNativeQuery.setParameter(++index, Integer.parseInt(scanToDate.toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
				}//A-9092
				//Added for ICRD-133967 starts
				if(mailbagEnquiryFilterVO.getConsigmentNumber() != null &&
						mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
				}
				if(mailbagEnquiryFilterVO.getUpuCode() != null &&
						mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
				}
				//Added for ICRD-133967 ends
				//Added for ICRD-214795 starts
				if(mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
					String rqdDlvTime=mailbagEnquiryFilterVO.getReqDeliveryTime().toDisplayFormat("yyyyMMddHHmm");
					if(rqdDlvTime!=null){
						if(rqdDlvTime.contains("0000")){
							pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
						}else{
							pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
						}
						pageableNativeQuery.setParameter(++index,mailbagEnquiryFilterVO.getReqDeliveryTime());
					}
				}
				//Added for ICRD-214795 ends
				//Added for ICRD-214795 starts
				if(mailbagEnquiryFilterVO.getTransportServWindow() != null) {
					String transportServWindow =mailbagEnquiryFilterVO.getTransportServWindow().toDisplayFormat("yyyyMMddHHmm");
					if(transportServWindow!=null){
						if(transportServWindow.contains("0000")){
							pageableNativeQuery.append(" AND TRUNC(MALMST.TRPSRVENDTIM) = ?");
						}else{
							pageableNativeQuery.append(" AND MALMST.TRPSRVENDTIM = ?");
						}
						pageableNativeQuery.setParameter(++index,mailbagEnquiryFilterVO.getTransportServWindow());
					}
				}
				//Added for ICRD-214795 ends
				//Added by A-4809 for ICRD-180189 .. Starts
				if(mailbagEnquiryFilterVO.getDespatchSerialNumber() !=null &&
						mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.DSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
				}
				if(mailbagEnquiryFilterVO.getOoe()!=null && !mailbagEnquiryFilterVO.getOoe().isEmpty()){
					pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
				}
				if(mailbagEnquiryFilterVO.getDoe()!=null && !mailbagEnquiryFilterVO.getDoe().isEmpty()){
					pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
				}
				if(mailbagEnquiryFilterVO.getOriginAirportCode()!=null && !mailbagEnquiryFilterVO.getOriginAirportCode().isEmpty()){
					pageableNativeQuery.append(" AND MALMST.ORGCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
				}
				if(mailbagEnquiryFilterVO.getDestinationAirportCode()!=null && !mailbagEnquiryFilterVO.getDestinationAirportCode().isEmpty()){
					pageableNativeQuery.append(" AND MALMST.DSTCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
				}
				if(mailbagEnquiryFilterVO.getMailCategoryCode()!=null &&
						mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
				}
				if(mailbagEnquiryFilterVO.getMailSubclass()!=null &&
						mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
				}
				if(mailbagEnquiryFilterVO.getYear()!=null && mailbagEnquiryFilterVO.getYear().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.YER = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
				}
				if(mailbagEnquiryFilterVO.getReceptacleSerialNumber()!=null
						&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.RSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
				}
				//Added for ICRD-205027 starts
				if(mailbagEnquiryFilterVO.getMailbagId()!=null
						&& mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
				}
				//Added for ICRD-205027 ends
				//Added by A-8164 starts
				if(mailbagEnquiryFilterVO.getMailBagsToList()!=null
						&& mailbagEnquiryFilterVO.getMailBagsToList().size()>0){
					String mailbagAppend="AND MALMST.MALIDR IN (";
					for(int i=1;i<mailbagEnquiryFilterVO.getMailBagsToList().size();i++){
						mailbagAppend=new StringBuffer().append(mailbagAppend).append("?,").toString();
					}
					mailbagAppend=new StringBuffer().append("?)").toString();
					pageableNativeQuery.append(mailbagAppend);
					for(String mailbag:mailbagEnquiryFilterVO.getMailBagsToList()){
						pageableNativeQuery.setParameter(++index, mailbag);
					}
				}
				//Added by A-8164 ends

				//Added by A-8672 for ICRD-327149 starts
				if(mailbagEnquiryFilterVO.getServiceLevel()!=null
						&& mailbagEnquiryFilterVO.getServiceLevel().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
				}
				//Added by A-8672 for ICRD-327149 ends
				//Added for ICRD-323389 starts
				if(mailbagEnquiryFilterVO.getOnTimeDelivery()!=null
						&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length()>0){
					pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
				}
				//Added for ICRD-323389 ends
				//Added by A-4809 for ICRD-180189 .. Ends
				if(mailbagEnquiryFilterVO.getCarditPresent() != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
					pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
							.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
							.append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
							.append(" ) ");
				}
			}else if(MailConstantsVO.MAIL_STATUS_NEW
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())){

				int carrierId = mailbagEnquiryFilterVO.getCarrierId();
				LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
				Date flightSqlDate = null;
				if (flightDate != null) {
					flightSqlDate = flightDate.toSqlDate();
				}
				String flightDateString = String.valueOf(flightSqlDate);
				baseQuery = getQueryManager().getNamedNativeQueryString(
						FIND_MAILDETAILSFORNEW);
				rankQuery.append(baseQuery);
				if (mailbagEnquiryFilterVO.getPageSize() > 0) {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getPageSize(), -1, rankQuery.toString(), new MailbagMapper());
				}else {
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(), rankQuery.toString(), new MailbagMapper());
				}
				if (carrierId > 0) {
					pageableNativeQuery.append(" AND FLTCARIDR = ? ");
					pageableNativeQuery.setParameter(++index, carrierId);
				}
				if (mailbagEnquiryFilterVO.getCarrierCode() != null &&
						mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());

				}
				if (mailbagEnquiryFilterVO.getFlightNumber() != null &&
						mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND FLTNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
				}
				if (flightSqlDate != null) {
					pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
					pageableNativeQuery.setParameter(++index, flightDateString);
				}
				pageableNativeQuery.append(" ) WHERE ");
				if (mailbagEnquiryFilterVO.getCompanyCode() != null &&
						mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
					pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
				}
				if (mailbagEnquiryFilterVO.getContainerNumber() != null &&
						mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
				}
				LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
				LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
				if (scanFromDate != null && scanToDate != null) {
					pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
					pageableNativeQuery.setParameter(++index, scanFromDate);
					pageableNativeQuery.append(" AND  ?  ");
					pageableNativeQuery.setParameter(++index, scanToDate);
				}
				//Added for ICRD-133967 starts
				if (mailbagEnquiryFilterVO.getConsigmentNumber() != null &&
						mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
				}
				if (mailbagEnquiryFilterVO.getUpuCode() != null &&
						mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
				}
				//Added for ICRD-133967 ends
				//Added for ICRD-214795 starts
				if (mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
					String rqdDlvTime = mailbagEnquiryFilterVO.getReqDeliveryTime().toDisplayFormat("yyyyMMddHHmm");
					if (rqdDlvTime != null) {
						if (rqdDlvTime.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
						}
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReqDeliveryTime());
					}
				}
				//Added for ICRD-214795 ends
				//Added for ICRD-214795 starts
				if (mailbagEnquiryFilterVO.getTransportServWindow() != null) {
					String transportServWindow = mailbagEnquiryFilterVO.getTransportServWindow().toDisplayFormat("yyyyMMddHHmm");
					if (transportServWindow != null) {
						if (transportServWindow.contains("0000")) {
							pageableNativeQuery.append(" AND TRUNC(MALMST.TRPSRVENDTIM) = ?");
						} else {
							pageableNativeQuery.append(" AND MALMST.TRPSRVENDTIM = ?");
						}
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getTransportServWindow());
					}
				}
				//Added for ICRD-214795 ends
				//Added by A-4809 for ICRD-180189 .. Starts
				if (mailbagEnquiryFilterVO.getDespatchSerialNumber() != null &&
						mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.DSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
				}
				if (mailbagEnquiryFilterVO.getOoe() != null && !mailbagEnquiryFilterVO.getOoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
				}
				if (mailbagEnquiryFilterVO.getDoe() != null && !mailbagEnquiryFilterVO.getDoe().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
				}
				if (mailbagEnquiryFilterVO.getOriginAirportCode() != null && !mailbagEnquiryFilterVO.getOriginAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.ORGCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
				}
				if (mailbagEnquiryFilterVO.getDestinationAirportCode() != null && !mailbagEnquiryFilterVO.getDestinationAirportCode().isEmpty()) {
					pageableNativeQuery.append(" AND MALMST.DSTCOD = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
				}
				if (mailbagEnquiryFilterVO.getMailCategoryCode() != null &&
						mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
				}
				if (mailbagEnquiryFilterVO.getMailSubclass() != null &&
						mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
				}
				if (mailbagEnquiryFilterVO.getYear() != null && mailbagEnquiryFilterVO.getYear().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.YER = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
				}
				if (mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null
						&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.RSN = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
				}
				//Added for ICRD-205027 starts
				if (mailbagEnquiryFilterVO.getMailbagId() != null
						&& mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
				}
				//Added for ICRD-205027 ends
				//Added by A-8164 starts
				if (mailbagEnquiryFilterVO.getMailBagsToList() != null
						&& mailbagEnquiryFilterVO.getMailBagsToList().size() > 0) {
					String mailbagAppend = "AND MALMST.MALIDR IN (";
					for (int i = 1; i < mailbagEnquiryFilterVO.getMailBagsToList().size(); i++) {
						mailbagAppend = new StringBuffer().append(mailbagAppend).append("?,").toString();
					}
					mailbagAppend = new StringBuffer().append("?)").toString();
					pageableNativeQuery.append(mailbagAppend);
					for (String mailbag : mailbagEnquiryFilterVO.getMailBagsToList()) {
						pageableNativeQuery.setParameter(++index, mailbag);
					}
				}
				//Added by A-8164 ends

				//Added by A-8672 for ICRD-327149 starts
				if (mailbagEnquiryFilterVO.getServiceLevel() != null
						&& mailbagEnquiryFilterVO.getServiceLevel().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
				}
				//Added by A-8672 for ICRD-327149 ends
				//Added for ICRD-323389 starts
				if (mailbagEnquiryFilterVO.getOnTimeDelivery() != null
						&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length() > 0) {
					pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
					pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
				}
				//Added for ICRD-323389 ends
				//Added by A-4809 for ICRD-180189 .. Ends
				if (mailbagEnquiryFilterVO.getCarditPresent() != null
						&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
					pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
							.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
							.append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
							.append(" ) ");
				}




			}else {
				log.log(Log.INFO, "THE STATUS IS--- ",
						mailbagEnquiryFilterVO.getCurrentStatus());
				rankQuery.append(baseQuery);
				if(mailbagEnquiryFilterVO.getPageSize()>0) {
					pageableNativeQuery = new ArrivalMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,rankQuery.toString(), mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new ArrivalMailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO,rankQuery.toString());
				}
			}
		}
		//}
		//PageableNativeQuery<PermanentBookingVO> query = new PermanentBookingsFilterQuery(new PermanentBookingsMultiMapper(),permanentBookingFilterVo, baseQuery);
		//pageableNativeQuery= new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);

		/**
		 * Commented as booking time is not needed starts
		 */

		log.log(Log.INFO, "Query is", pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if(!acceptedmailbagFilterQuery && !allmailbagfilterQuery)
		{
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		if(allmailbagfilterQuery){
			pageableNativeQuery.append(")MST  where maxutcscndat =  utcscndat");
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}

		return pageableNativeQuery.getPage(pageNumber);
	}


	/**
	 * @author A-1936 Note:- This method will be called to append the Query
	 *         Dynamically in Places where a MailIn InventoryList From the
	 *         Inventory List contains the Flight Number .. Which means its
	 *         showing the Flight From where it got arrived ..
	 *
	 *
	 * @return
	 */
	private String appendArrivedFlightToInventory() {

		return new StringBuilder(" INNER JOIN MALHIS MALHIS ON  ").append(
				"  MALMST.CMPCOD =MALHIS.CMPCOD  AND ").append(
				"  MALMST.MALSEQNUM =MALHIS.MALSEQNUM  AND ").append(
				"  MALMST.SCNPRT = MALHIS.SCNPRT   ").toString();
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailTag(java.util.ArrayList)
	 *	Added by 			: A-4809 on Aug 4, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public ArrayList<MailbagVO> findMailTag(ArrayList<MailbagVO> mailbagVOs)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findMailTag");
		ArrayList<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
		/*String mailId = new StringBuilder().append(mailbagVO.getOoe())
				.append(mailbagVO.getDoe()).append(
						mailbagVO.getMailCategoryCode()).append(
						mailbagVO.getMailSubclass()).append(
						mailbagVO.getYear()).append(
						mailbagVO.getDespatchSerialNumber()).append(
						mailbagVO.getReceptacleSerialNumber()).append(
						mailbagVO.getHighestNumberedReceptacle()).append(
						mailbagVO.getRegisteredOrInsuredIndicator())
				.append(mailbagVO.getStrWeight()).toString();*/
			mailbagVO.setMailbagId(mailbagVO.getMailbagId());//Modified for ICRD-205027
			LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			mailbagVO.setCurrentDateStr(date.toDisplayFormat("ddMMyyyy"));
			log.log(Log.FINE, "mailbagVO.setCurrentDateStr() :", mailbagVO.getCurrentDateStr());
			Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILTAG);
			int idx = 0;
			MailbagVO mailVO = new MailbagVO();
			qry.setParameter(++idx, mailbagVO.getCompanyCode());
			qry.setParameter(++idx, mailbagVO.getMailbagId());
			mailVO = qry.getSingleResult(new PrintMailTagMapper());
			if (mailVO == null) {
				mailVOs.add(mailbagVO);
			} else {
				mailVO.setOoe(mailbagVO.getOoe());
				mailVO.setDoe(mailbagVO.getDoe());
				mailVO.setDespatchSerialNumber(mailbagVO
						.getDespatchSerialNumber());
				mailVO.setReceptacleSerialNumber(mailbagVO
						.getReceptacleSerialNumber());
				mailVO.setYear(mailbagVO.getYear());
				mailVO.setWeight(mailbagVO.getWeight());
				mailVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
				mailVO.setMailSubclass(mailbagVO.getMailSubclass());
				mailVO.setRegisteredOrInsuredIndicator(mailbagVO
						.getRegisteredOrInsuredIndicator());
				mailVO.setHighestNumberedReceptacle(mailbagVO
						.getHighestNumberedReceptacle());
				mailVO.setCurrentDateStr(date.toDisplayFormat("ddMMyyyy"));
				mailVO.setMailbagId(mailbagVO.getMailbagId());
				mailVOs.add(mailVO);
			}
		}
		log.exiting("MailTrackingDefaultsSqlDAO", "findMailTag");
		return mailVOs;
	}
	/**
	 * @author A-2667 Added by Sreekumar S to find the Consignment Details
	 * @param carditEnquiryVO
	 * @return
	 */
	public Page<MailbagVO> findConsignmentDetails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		//Modified as part of bug ICRD-145482 by A-5526 starts-to enable last link and correct total records count
		PageableNativeQuery<MailbagVO> pgqry = null;
		Page<MailbagVO> mailbagVos = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_DETAILS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		rankQuery.append(qry);

		pgqry = new FindConsignmentDetailsFilterQuery(carditEnquiryFilterVO, rankQuery.toString() ,new FindConsignmentDetailsMapper());

		pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(pageNumber);
		//Modified as part of bug ICRD-145482 by A-5526 ends
	}

	/**
	 * @author A-5931 Method for MBI LOV containing mailboxCode and mailboxDescription
	 * @param companyCode
	 * @param mailboxCode
	 * @param mailboxDescription
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode,
												 String mailboxCode, String mailboxDesc, int pageNumber, int defaultSize)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findPALov");

		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);

		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAILNOXID_LOV);
		masterQuery.append(queryString);
		PageableNativeQuery<MailBoxIdLovVO> pgNativeQuery = new PageableNativeQuery<MailBoxIdLovVO>(defaultSize, -1,masterQuery.toString(),new MailBoxIdLovMapper());

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
	 * Jun 6, 2008, 2556
	 * @param dsnVO
	 * @param mode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DespatchDetailsVO> findDespatchesOnDSN (DSNVO dsnVO,String mode)
			throws SystemException, PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "findDespatchesOnDSN");
		Query qry = null;
		int idx=0;
		if("FLIGHT".equals(mode)){
			if("U".equals(dsnVO.getContainerType())){
				qry = getQueryManager().createNamedNativeQuery(FIND_DESPATCHES_IN_FLIGHT_ULDS);
			}
			if("B".equals(dsnVO.getContainerType())){
				qry = getQueryManager().createNamedNativeQuery(FIND_DESPATCHES_IN_FLIGHT_BULK);
			}
		}else{
			if("U".equals(dsnVO.getContainerType())){
				qry = getQueryManager().createNamedNativeQuery(FIND_DESPATCHES_IN_CARRIER_ULDS);
			}
			if("B".equals(dsnVO.getContainerType())){
				qry = getQueryManager().createNamedNativeQuery(FIND_DESPATCHES_IN_CARRIER_BULK);
			}
		}
		qry.setParameter(++idx, dsnVO.getCompanyCode());
		qry.setParameter(++idx, dsnVO.getOriginExchangeOffice());
		qry.setParameter(++idx, dsnVO.getDestinationExchangeOffice());
		qry.setParameter(++idx, dsnVO.getDsn());
		qry.setParameter(++idx, dsnVO.getMailCategoryCode());
		qry.setParameter(++idx, dsnVO.getMailSubclass());
		qry.setParameter(++idx, dsnVO.getContainerNumber());
		log.exiting("MailTrackingDefaultsSqlDAO", "findDespatchesOnDSN");
		return qry.getResultList(new DespatchInDSNMapper());
	}

	/**
	 * @author a-1936 This method is used to return all the containers which are
	 *         already assigned to a particular Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findFlightAssignedContainers(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findFlightAssignedContainers");
		int index = 0;

		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINERS_FORFLIGHT);

		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry
				.setParameter(++index, operationalFlightVO
						.getFlightSequenceNumber());
		/*
		 * The airportCode
		 */
		qry.setParameter(++index, operationalFlightVO.getPol());
		Collection<ContainerVO> containerVOs = qry
				.getResultList(new ContainerMapperForFlight());

		/*
		 * Added For AirNZ CR : Mail Allocation on 05-OCT-2008
		 */
		if(containerVOs!=null && containerVOs.size()>0){
			String bookingBaseQuery = null;
			Query bookingQry = null;
			bookingBaseQuery = getQueryManager().getNamedNativeQueryString(
					FIND_BOOKING_TIME_FOR_DSNS_IN_CONTAINER);
			bookingQry = new BookingDSNInContainerFilterQuery(containerVOs,bookingBaseQuery);
			Collection<ContainerVO> bookedContainerVOs = bookingQry.getResultList(new ContainerBookingMultiMapper());
			if(bookedContainerVOs!=null && bookedContainerVOs.size()>0){
				for(ContainerVO pageContainerVO : containerVOs){
					for(ContainerVO bookedContainerVO : bookedContainerVOs){
						if(pageContainerVO.getContainerNumber().equals(bookedContainerVO.getContainerNumber())){
							pageContainerVO.setBookingTimeVOs(bookedContainerVO.getBookingTimeVOs());
							break;
						}
					}
				}
			}
		}
		//Mail Allocation Ends
		if (containerVOs != null && containerVOs.size() > 0) {
			for (ContainerVO containerVo : containerVOs) {
				if (MailConstantsVO.BULK_TYPE.equals(containerVo.getType())) {
					log.log(Log.INFO, "THE BARROWS PRESENT CALCULATE WEIGHT");
					ContainerVO containerForSum = null;
					int indexForSum = 0;
					qry = getQueryManager().createNamedNativeQuery(
							FIND_BAGCOUNT_FORFLIGHT);
					qry.setParameter(++indexForSum, containerVo
							.getCompanyCode());
					StringBuilder containerNumber=new StringBuilder("BULK-").append(containerVo.getPou());
					qry.setParameter(++indexForSum, containerVo.getContainerNumber());
					qry.setParameter(++indexForSum, containerVo.getCarrierId());
					qry.setParameter(++indexForSum, containerVo
							.getFlightNumber());
					qry.setParameter(++indexForSum, containerVo
							.getFlightSequenceNumber());
					qry.setParameter(++indexForSum, containerVo
							.getSegmentSerialNumber());
					containerForSum = qry.getSingleResult(new BagCountMapper());
					if (containerForSum != null) {
						containerVo.setBags(containerForSum.getBags());
						containerVo.setWeight(containerForSum.getWeight());
						containerVo.setWarehouseCode(containerForSum
								.getWarehouseCode());
						containerVo.setLocationCode(containerForSum
								.getLocationCode());
						log.log(Log.FINE, "Bags >>>>>>>>>>>>>>", containerVo.getBags());
						log.log(Log.FINE, "Weight>>>>>>>>>>>>>>>>>",
								containerVo.getWeight());
					}
				}
			}
		}
		return containerVOs;

	}

	/**
	 * @author A-2037 This method is used to find the Damaged Mailbag Details
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DamagedMailbagVO> findMailbagDamages(String companyCode,
														   String mailbagId) throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailbagDamages");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_DAMAGES);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailbagId);
		return query.getResultList(new DamagedMailBagMapper());

	}
	/**
	 * @author a-1936 This method is used to find the ContainerDetails
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<ContainerVO> findContainers(
			SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
			throws PersistenceException, SystemException {
		PageableNativeQuery<ContainerVO> pgqry = null;
		Page<ContainerVO> containerVos = null;
		String nonSQ= "OTHERS";
		String outerQry = getQueryManager().getNamedNativeQueryString(FIND_CONTAINERS_OUTERQUERY);
		//added by A-5201 for CR ICRD-21098 starts
		String baseQry = getQueryManager().getNamedNativeQueryString(
				FIND_CONTAINERS);
		//Added for icrd-95515
		String baseQry1 = null;
		if("I".equals(searchContainerFilterVO.getOperationType())) {
			baseQry1 = new StringBuilder().append(baseQry).append(",ASGSEG.POL POL ").toString();
		}
		else {
			if (SEARCHMODE_DEST.equals(searchContainerFilterVO.getSearchMode())) {
				baseQry1 = new StringBuilder().append(baseQry).append(",NULL POL ").toString();
			}else {
				baseQry1 = new StringBuilder().append(baseQry).append(",ASG.ARPCOD POL ").toString();
			}
		}
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.CMPCOD , RESULT_TABLE.CONNUM,RESULT_TABLE.ASGPRT,RESULT_TABLE.FLTCARIDR,RESULT_TABLE.FLTNUM,RESULT_TABLE.FLTSEQNUM,RESULT_TABLE.LEGSERNUM) RANK FROM (");
		if (searchContainerFilterVO.getSubclassGroup() != null && !searchContainerFilterVO.getSubclassGroup().equals(nonSQ)) {
			rankQuery.append(outerQry);
			rankQuery.append(baseQry1);
		} else {
			rankQuery.append(baseQry1);
		}

		//added by A-5201 for CR ICRD-21098 end

		log.entering(MODULE, "findContainers");
		// Added to include the Different SearchMode
		/*
		 * When the Search Mode is DESTN only Mapper is Needed When the Search
		 * Mode is FLT or ALL a implementation Of MultiMapper is Required ...
		 */
		boolean isOracleDataSource=isOracleDataSource();
		pgqry = SearchContainerFilterQuery.getInstance(rankQuery.toString(), searchContainerFilterVO, isOracleDataSource);
		containerVos = pgqry.getPage(pageNumber);
		/*
		 * Added For AirNZ CR : Mail Allocation on 05-OCT-2008
		 */
		/**
		 * Commenting the flow in list container as part of performance issue by A-4809
		 * Since integration of module with capacity is not present
		 * fnd booking time and updating vo is not required and it consumes time.
		 */
/*	if(containerVos!=null && containerVos.size()>0){
		String bookingBaseQuery = null;
		Query bookingQry = null;
		bookingBaseQuery = getQueryManager().getNamedNativeQueryString(
				FIND_BOOKING_TIME_FOR_DSNS_IN_CONTAINER);
		Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
		for(ContainerVO containerVO : containerVos) {
			contVOs.add(containerVO);
		}
		bookingQry = new BookingDSNInContainerFilterQuery(contVOs,bookingBaseQuery);
		Collection<ContainerVO> bookedContainerVOs = bookingQry.getResultList(new ContainerBookingMultiMapper());
		if(bookedContainerVOs!=null && bookedContainerVOs.size()>0){
			for(ContainerVO pageContainerVO : containerVos){
				for(ContainerVO bookedContainerVO : bookedContainerVOs){
					if(pageContainerVO.getContainerNumber().equals(bookedContainerVO.getContainerNumber())){
						pageContainerVO.setBookingTimeVOs(bookedContainerVO.getBookingTimeVOs());
						break;
					}
				}
			}
		}
	}*/
		//Mail Allocation Ends
		if (containerVos != null && containerVos.size() > 0) {
			for (ContainerVO containerVO : containerVos) {
				findOffloadedInfoForCOntainer(containerVO);//Added for ICRD-83340
				Query queryForSum = null;
				int index = 0;
				ContainerVO containerForSum = null;
				if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {//Modified for ICRD-217134
					log.log(Log.INFO, "THE BARROWS PRESENT CALCULATE WEIGHT");

					if (containerVO.getFlightSequenceNumber() > 0) {
						queryForSum = getQueryManager().createNamedNativeQuery(
								FIND_BAGCOUNT_FORFLIGHT);
						queryForSum.setParameter(++index, containerVO
								.getCompanyCode());
						queryForSum.setParameter(++index, containerVO
								.getContainerNumber());
						queryForSum.setParameter(++index, containerVO
								.getCarrierId());
						queryForSum.setParameter(++index, containerVO
								.getFlightNumber());
						queryForSum.setParameter(++index, containerVO
								.getFlightSequenceNumber());
						queryForSum.setParameter(++index, containerVO
								.getSegmentSerialNumber());
						containerForSum = queryForSum
								.getSingleResult(new BagCountMapper());
					}
				}
				if(containerVO.getFlightSequenceNumber() <= 0){//Modified for ICRD-217134
					queryForSum = getQueryManager().createNamedNativeQuery(
							FIND_BAGCOUNT_FORDESTINATION);
					queryForSum.setParameter(++index, containerVO
							.getCompanyCode());
					queryForSum.setParameter(++index, containerVO
							.getAssignedPort());
					queryForSum.setParameter(++index, containerVO
							.getCarrierId());
					queryForSum.setParameter(++index, containerVO
							.getContainerNumber());
					containerForSum = queryForSum
							.getSingleResult(new BagCountMapper());
				}
				if (containerForSum != null) {
					if(containerVO.getFlightSequenceNumber() > 0 && MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())){
						containerVO.setBags(containerForSum.getReceivedBags());
						containerVO.setWeight(containerForSum.getReceivedWeight());
					}   else if(containerVO.getFlightSequenceNumber() <0){
						containerVO.setBags(containerForSum.getBags());
						containerVO.setWeight(containerForSum.getWeight());
					}
					containerVO.setWarehouseCode(containerForSum.getWarehouseCode());
					containerVO.setLocationCode(containerForSum.getLocationCode());
					log.log(Log.FINE, "Bags >>>>>>>>>>>>>>", containerVO.getBags());
					log.log(Log.FINE, "Weight>>>>>>>>>>>>>>>>>",
							containerVO.getWeight());


				}
			}
		}
		log.log(Log.FINE, "containerVos>>>>>>>>>>>>>>>>>", containerVos);
		return containerVos;

	}

	/**
	 * @author A-3429 This method is used to find the
	 * offload information for the container
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void findOffloadedInfoForCOntainer(ContainerVO containerVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findOffloadedInfoForCOntainer");
		List<ContainerVO> containerForOffloadDts = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_OFFLOADEDINFO_FORCONTAINER);
		qry.setParameter(++index, containerVO.getCompanyCode());
		qry.setParameter(++index, containerVO.getContainerNumber());
		//Added as part of bug ICRD-153545 by A-5526 starts
		qry.setParameter(++index, containerVO.getAssignedPort());
		//Added as part of bug ICRD-153545 by A-5526 ends
		containerForOffloadDts = qry.getResultList(new OffloadedFlightDetailsMapper());
		Collection<String> offloadDtls = null;
		int offloadCount = 0;
		if(containerForOffloadDts != null){
			offloadDtls = new ArrayList<String> ();
			for(ContainerVO container : containerForOffloadDts){
				offloadDtls.add(container.getOffloadedDescription());
				offloadCount = offloadCount+container.getOffloadCount();
			}
			containerVO.setOffloadedInfo(offloadDtls);
			containerVO.setOffloadCount(offloadCount);
		}
	}


	/**
	 * @author A-2553
	 * @param mailStatusFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO mailStatusFilterVO)
			throws SystemException,PersistenceException {
		log.entering(MODULE, "generateMailStatusReport");
		Query qry = null;
		String baseQry = null;
		List<MailStatusVO> mailStatusVOs = new ArrayList<MailStatusVO>();

		/**
		 * Added by A-1876 Roopak  FOR EXPECTED_MAIL_CARDIT/TRANSHIPS/ALLOCATED
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL)) {

			log.log(Log.FINE,"generateMailStatusReport-->EXPECTED_MAIL_CARDIT");
			qry = new ExpectedMailCarditFilterQuery(mailStatusFilterVO);

			List<MailStatusVO> mailStatusCarditVOs = new ArrayList<MailStatusVO>();
			mailStatusCarditVOs = qry.getResultList(new MailStatusReportMapper());

			if (mailStatusCarditVOs != null && mailStatusCarditVOs.size() > 0) {
				mailStatusVOs.addAll(mailStatusCarditVOs);
			}

			/**Expected mail Through Transhipments*/
			 /*if (mailStatusFilterVO.getCarrierid() > 0
					 && mailStatusFilterVO.getFlightCarrierid() == 0) {
				 baseQry = getQueryManager().getNamedNativeQueryString(MAILS_FOR_TRANSHIPMENT_CARRIER);
			 } else {
				 baseQry = getQueryManager().getNamedNativeQueryString(MAILS_FOR_TRANSHIPMENT_FLIGHT);
			 //}
			 qry = new ExpectedMailTranshipFilterQuery(mailStatusFilterVO,
					 baseQry);

			List<MailStatusVO> mailStatusTranshipVOs = new ArrayList<MailStatusVO>();
			mailStatusTranshipVOs = qry.getResultList(new MailStatusReportMapper());

			if (mailStatusTranshipVOs != null && mailStatusTranshipVOs.size() > 0) {
				mailStatusVOs.addAll(mailStatusTranshipVOs);
			}*/

		}

		/*
		 * Added By RENO K ABRAHAM For Expected Mail Through CARDIT
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_CARDIT)) {
			log.log(Log.FINE,"generateMailStatusReport-->EXPECTED_MAIL_CARDIT");
			qry = new ExpectedMailCarditFilterQuery(mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/*
		 * Expected mails for transhipment
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_TRANSHIPS)) {
			/* if (mailStatusFilterVO.getCarrierid() > 0
					 && mailStatusFilterVO.getFlightCarrierid() == 0) {
				 baseQry = getQueryManager().getNamedNativeQueryString(MAILS_FOR_TRANSHIPMENT_CARRIER);
			 } else {*/
			baseQry = getQueryManager().getNamedNativeQueryString(MAILS_FOR_TRANSHIPMENT_FLIGHT);
			/* }*/
			qry = new ExpectedMailTranshipFilterQuery(mailStatusFilterVO,
					baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/*
		 * Added by Paulson
		 */
		if (MailConstantsVO.MAIL_WITHOUT_CARDIT.equals(mailStatusFilterVO.getCurrentStatus())) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAIL_WITHOUT_CARDITS);
			qry = new MailsWithoutCarditFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}
		/**
		 * If the Mail Accepted but not Uplifted
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals( MailConstantsVO.MAIL_ACCEPTED_NOT_UPLIFTED)) {

			if (mailStatusFilterVO.getCarrierid() > 0
					&& mailStatusFilterVO.getFlightCarrierid() == 0) {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_ACCEPTED_NOT_UPLIFTED_CARRIER);
			} else {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_ACCEPTED_NOT_UPLIFTED_FLIGHT);
			}
			qry = new MailsAcceptedButNotUpliftedFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/**
		 * If the Mail is Not Uplifted
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals( MailConstantsVO.MAIL_NOT_UPLIFTED)) {

			if (mailStatusFilterVO.getCarrierid() > 0
					&& mailStatusFilterVO.getFlightCarrierid() == 0) {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_NOTUPLIFTED_CARRIER);
			} else {
				baseQry = getQueryManager().getNamedNativeQueryString(MAILS_IN_FLIGHT);
			}
			qry = new MailsNotUpliftedFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/**
		 * Uplifted and Not Delivered\No cardit
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_UPLIFTED_WITHOUT_CARDIT)
				|| mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_UPLIFTED_NOT_DELIVERED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(MAILS_IN_FLIGHT);
			qry = new MailsUpliftedtFilterQuery(mailStatusFilterVO, baseQry);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/*
		 * Added by A-3251 SREEJITH P.C. FOR CARDIT_NOT_ACCEPTED
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_CARDIT_NOT_POSSESSED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILSTATUS_CARDIT_NOT_ACCEPTED);
			qry = new CarditNotAcceptedFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		if (MailConstantsVO.MAIL_ARRIVED_NOT_DELIVERED.equals(mailStatusFilterVO.getCurrentStatus())) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAIL_ARRIVED_NOT_DELIVERED);
			qry = new MailsArrivedNotDeliveredFilterQuery(baseQry,
					mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		/*
		 * Added by A-3251 SREEJITH P.C. FOR MAIL DELIVERED
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_DELIVERED)) {
			baseQry = getQueryManager().getNamedNativeQueryString(FIND_MAILSTATUS_MAIL_DELIVERED);
			qry = new MailsDeliveredFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}
		/*
		 * Added by A-3251 SREEJITH P.C. FOR MAIL DELIVERED WITHOUT CARDIT
		 */
		if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.MAIL_DELIVERED_WITHOUT_CARDIT)) {
			baseQry = getQueryManager().getNamedNativeQueryString( FIND_MAILSTATUS_MAIL_DELIVERED_WITHOUT_CARDIT);
			qry = new MailsDeliveredFilterQuery(baseQry, mailStatusFilterVO);
			mailStatusVOs = qry.getResultList(new MailStatusReportMapper());
		}

		log.log(Log.FINE, "####^^^^##### Returning mailStatusVOs : in SQLDAO",
				mailStatusVOs);
		return mailStatusVOs;

	}



	/**
	 * @author a-1936 This method is used to return all the containers which are
	 *         already assigned to a particular destination
	 * @param destinationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findDestinationAssignedContainers(
			DestinationFilterVO destinationFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findDestinationAssignedContainers");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINERS_FORDESTINATION);

		qry.setParameter(++index, destinationFilterVO.getCompanyCode());
		qry.setParameter(++index, destinationFilterVO.getCarrierId());
		qry.setParameter(++index, destinationFilterVO.getAirportCode());
		/*
		 * Added By Karthick V as the part of the NCA Mail Tracking CR... If the
		 * Destination code is mandatory all the Containers for the Inventory
		 * will be fetched..
		 */
		if (destinationFilterVO.getDestination() != null
				&& destinationFilterVO.getDestination().trim().length() > 0) {
			qry.append("  AND  MST.DSTCOD = ? ");
			qry.setParameter(++index, destinationFilterVO.getDestination());
		} else {
			qry.append("  AND MST.DSTCOD IS NULL ");
		}
		Collection<ContainerVO> coll = qry.getResultList(new ContainerMapper());
		if (coll != null && coll.size() > 0) {
			for (ContainerVO containerVo : coll) {
				//if (MailConstantsVO.BULK_TYPE.equals(containerVo.getType())) {
				log.log(Log.INFO, "THE BARROWS PRESENT CALCULATE WEIGHT");
				ContainerVO containerForSum = null;
				int indexForSum = 0;
				qry = getQueryManager().createNamedNativeQuery(
						FIND_BAGCOUNT_FORDESTINATION);
				log.log(Log.FINE, "THE BARROWS PRESENT CALCULATE WEIGHT",
						qry);
				qry.setParameter(++indexForSum, containerVo
						.getCompanyCode());
				qry.setParameter(++indexForSum, containerVo
						.getAssignedPort());
				qry.setParameter(++indexForSum, containerVo.getCarrierId());
				qry.setParameter(++indexForSum, containerVo
						.getContainerNumber());
				containerForSum = qry.getSingleResult(new BagCountMapper());
				log.log(Log.INFO, "THE BARROWS PRESENT CALCULATE WEIGHT");
				if (containerForSum != null) {
					containerVo.setBags(containerForSum.getBags());
					containerVo.setWeight(containerForSum.getWeight());
					containerVo.setWarehouseCode(containerForSum
							.getWarehouseCode());
					containerVo.setLocationCode(containerForSum
							.getLocationCode());
					log.log(Log.FINE, "Bags >>>>>>>>>>>>>>", containerVo.getBags());
					log.log(Log.FINE, "Weight>>>>>>>>>>>>>>>>>",
							containerVo.getWeight());
				}
				//}
			}
		}

		return coll;

	}

	/**
	 * Finds the details for mailbag manifest
	 *
	 * @param opFlightVO
	 */
	public MailManifestVO findMailbagManifestDetails(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_MANIFEST);
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query
				.getResultList(new MailbagManifestMultiMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		return manifests.iterator().next();
	}


	/**
	 *
	 * TODO Purpose Jan 18, 2007, A-1739
	 *
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findMailAWBManifestDetails(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		log.log(Log.FINE, "THE OPERATIONAL FLIGHT VO FROM OPERATIONS",
				opFlightVO);
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_AWB_MANIFEST);
		int idx = 0;

		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query
				.getResultList(new MailAWBManifestMultiMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		return manifests.iterator().next();
	}



	/**
	 *
	 * TODO Purpose Mar 27,2008 , A-2553
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findDSNMailbagManifest(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(this.getClass().getSimpleName(),
				"findDSNMailbagManifest");
		log.log(Log.FINE, "THE OPERATIONAL FLIGHT VO FROM OPERATIONS",
				opFlightVO);
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_DSN_MAILBAG_MANIFEST);
		int idx = 0;

		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query
				.getResultList(new DSNMailbagManifestMultiMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findDSNMailbagManifest");
		return manifests.iterator().next();
	}
	/**
	 *
	 * TODO Purpose Jan 18, 2007, A-1739
	 *
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailManifestVO findManifestbyDestination(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MANIFEST_DESTNCTG);
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		query.setParameter(++idx, opFlightVO.getPol());
		Collection<MailManifestVO> manifests = query
				.getResultList(new DestinationManifestMultiMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findMailbagManifestDetails");
		return manifests.iterator().next();
	}


	/** This method is used to find the Damaged Mailbag Details based on
	 * different search criteria
	 * @author A-3227 RENO K ABRAHAM
	 * @param damageMailFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO
																	 damageMailFilterVO) throws SystemException, PersistenceException {
		log.entering(MODULE, "findDamageMailReport");
		String baseQuery = getQueryManager().getNamedNativeQueryString(FIND_DAMAGE_MAILBAG_REPORT);
		Query query = new DamageMailReportFilterQuery(damageMailFilterVO,baseQuery);
		return query.getResultList(new DamageMailReportMapper());

	}

	/**
	 * @author a-1876 This method is used to view the Summary Details of the
	 *         Inventory List say the Number of Bags and Weight associated with
	 *         the Destination-Category Group ..
	 * @param companyCode
	 * @param currentAirport
	 * @param carrierId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailSummaryVO> findManifestSummaryByDestination(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO,
				"findManifestSummaryByDestination");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_DESTNCATMANIFEST_SUMMARY);
		int index = 0;
		qry.setParameter(++index, opFlightVO.getCompanyCode());
		qry.setParameter(++index, opFlightVO.getCarrierId());
		qry.setParameter(++index, opFlightVO.getFlightNumber());
		qry.setParameter(++index, opFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, opFlightVO.getPol());
		return qry.getResultList(new DestnManifestSummaryMultiMapper());
	}

	/**
	 * TODO Purpose Jan 31, 2007, A-1739
	 *
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findAWBDetailsForFlight(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
	 */
	public Collection<AWBDetailVO> findAWBDetailsForFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findAWBDetailsForFlight");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_AWB_FORFLIGHT);
		int idx = 0;
		query.setParameter(++idx, operationalFlightVO.getCompanyCode());
		query.setParameter(++idx, operationalFlightVO.getCarrierId());
		query.setParameter(++idx, operationalFlightVO.getFlightNumber());
		query
				.setParameter(++idx, operationalFlightVO
						.getFlightSequenceNumber());
		query.setParameter(++idx, operationalFlightVO.getPol());
		log.exiting("MailTrackingDefaultsSqlDAO", "findAWBDetailsForFlight");
		return query.getResultList(new AWBDetailsMapper());
	}


	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "findAWBDetailsForFlight");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FORDESPATCH);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getScannedPort());
		query.setParameter(++idx, mailbagVO.getDespatchId());
		log.exiting("MailTrackingDefaultsSqlDAO", "findAWBDetailsForFlight");
		return query.getResultList(new MailbagForInventoryMapper());
	}


	/**
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedContainers(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
	 */
	public List<ContainerDetailsVO> findArrivedContainers(
			MailArrivalFilterVO mailArrivalFilterVO) throws SystemException,
			PersistenceException {

		String baseQuery = getQueryManager().getNamedNativeQueryString(
				FIND_ULDS_FOR_ARRIVAL);
		Query query = new MailArrivalFilterQuery(mailArrivalFilterVO, baseQuery);
		return query.getResultList(new MailArrivalMultiMapper());
	}


	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR findMailDetail This method is
	 *         used to find out the MailDetais For all MailBags for which
	 *         Resdits are not sent and having the Search Mode as Despatch..
	 * @param despatchDetailVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailDetailsForDespatches(
			Collection<DespatchDetailsVO> despatchDetailVos,
			String unsentResditEvent) throws SystemException,
			PersistenceException {
		Collection<MailbagVO> mailBagVos = null;
		Collection<MailbagVO> mailBagsForUnsentResdits = null;
		Query qry = null;
		for (DespatchDetailsVO despatchVo : despatchDetailVos) {
			int index = 0;
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILDETAILS_FORUNSENTRESDITS);
			if (despatchVo.getCompanyCode() != null
					&& despatchVo.getCompanyCode().trim().length() > 0) {
				qry.append("  AND CDTMST.CMPCOD = ? ");
				qry.setParameter(++index, despatchVo.getCompanyCode());
			}
			if (despatchVo.getPaCode() != null
					&& despatchVo.getPaCode().trim().length() > 0) {
				qry.append("  AND CDTMST.SDRIDR= ? ");
				qry.setParameter(++index, despatchVo.getPaCode());
			}
			if (despatchVo.getConsignmentNumber() != null
					&& despatchVo.getConsignmentNumber().trim().length() > 0) {
				qry.append("  AND CDTMST.CSGDOCNUM=? ");
				qry.setParameter(++index, despatchVo.getConsignmentNumber());
			}
			if (despatchVo.getConsignmentDate() != null) {
				qry.append("  AND TRUNC(CDTMST.CSGCMPDAT) = ? ");
				qry.setParameter(++index, despatchVo.getConsignmentDate()
						.toCalendar());
			}

			if (despatchVo.getOriginOfficeOfExchange() != null
					&& despatchVo.getOriginOfficeOfExchange().trim().length() > 0) {
				qry.append("  AND  MALMST.ORGEXGOFC =? ");
				qry.setParameter(++index, despatchVo
						.getOriginOfficeOfExchange());
			}
			if (despatchVo.getDestinationOfficeOfExchange() != null
					&& despatchVo.getDestinationOfficeOfExchange().trim()
					.length() > 0) {
				qry.append("  AND  MALMST.DSTEXGOFC=?  ");
				qry.setParameter(++index, despatchVo
						.getDestinationOfficeOfExchange());
			}
			if (despatchVo.getMailCategoryCode() != null
					&& despatchVo.getMailCategoryCode().trim().length() > 0) {
				qry.append("  AND MALMST.MALCTG=?  ");
				qry.setParameter(++index, despatchVo.getMailCategoryCode());
			}

			if (despatchVo.getDsn() != null
					&& despatchVo.getDsn().trim().length() > 0) {
				qry.append("  AND MALMST.DSN=? ");
				qry.setParameter(++index, despatchVo.getDsn());
			}

			if (despatchVo.getYear() > 0) {
				qry.append("  AND MALMST.YER=? ");
				qry.setParameter(++index, despatchVo.getYear());
			}
			qry.append("  AND MALRDT.EVTCOD = ? ");
			qry.setParameter(++index, unsentResditEvent);
			qry.append("  AND MALRDT.RDTSND <> 'S' ");
			mailBagsForUnsentResdits = qry
					.getResultList(new MailbagsForUnsentResditMapper());
			if (mailBagsForUnsentResdits != null
					&& mailBagsForUnsentResdits.size() > 0) {
				for (MailbagVO mailRdt : mailBagsForUnsentResdits) {
					mailRdt.setPaCode(despatchVo.getPaCode());
				}
				if (mailBagVos == null) {
					mailBagVos = new ArrayList<MailbagVO>();
				}
				mailBagVos.addAll(mailBagsForUnsentResdits);
			}
		}
		return mailBagVos;
	}

	/**
	 *
	 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to find
	 *         out the MailDetais For all MailBags for which Resdits are not
	 *         sent and having the Search Mode as Document..
	 * @param consignmentDocumentVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findMailDetailsForDocument(
			Collection<ConsignmentDocumentVO> consignmentDocumentVos,
			String unsentResditEvent) throws SystemException,
			PersistenceException {
		Collection<MailbagVO> finalMailBagVos = null;
		Collection<MailbagVO> mailBagsForUnsentResdits = null;
		Query qry = null;
		for (ConsignmentDocumentVO documentVo : consignmentDocumentVos) {
			int index = 0;
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILDETAILS_FORUNSENTRESDITS);
			//A-8061 modified for ICRD-82434 starts
			if (documentVo.getCompanyCode() != null
					&& documentVo.getCompanyCode().trim().length() > 0) {
				qry.append("  AND CSGMST.CMPCOD = ?  ");
				qry.setParameter(++index, documentVo.getCompanyCode());
			}
			if (documentVo.getPaCode() != null
					&& documentVo.getPaCode().trim().length() > 0) {
				qry.append("  AND CSGMST.POACOD = ? ");
				qry.setParameter(++index, documentVo.getPaCode());
			}
			if (documentVo.getConsignmentNumber() != null
					&& documentVo.getConsignmentNumber().trim().length() > 0) {
				qry.append("  AND CSGMST.CSGDOCNUM=? ");
				qry.setParameter(++index, documentVo.getConsignmentNumber());
			}
			if (documentVo.getConsignmentDate() != null) {
				qry.append("  AND TRUNC(CSGMST.CSGDAT) = ? ");
				qry.setParameter(++index, documentVo.getConsignmentDate()
						.toCalendar());
			}

			//qry.append(" AND  MALRDT.EVTCOD = ? ");
			//qry.setParameter(++index, unsentResditEvent);
			//qry.append(" AND  MALRDT.RDTSND <> 'G' ");
			//A-8061 modified for ICRD-82434 ends
			mailBagsForUnsentResdits = qry
					.getResultList(new MailbagsForUnsentResditMapper());
			if (mailBagsForUnsentResdits != null
					&& mailBagsForUnsentResdits.size() > 0) {
				if (finalMailBagVos == null) {
					finalMailBagVos = new ArrayList<MailbagVO>();
				}
				finalMailBagVos.addAll(mailBagsForUnsentResdits);
			}
		}
		return finalMailBagVos;

	}



	/**
	 * @param partyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPartyName(String companyCode,
								String partyCode)throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findPartyName");
		Query query = getQueryManager().createNamedNativeQuery(FIND_PARTY_NAME);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, partyCode);
		query.setParameter(++idx, partyCode);
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, partyCode);
		log.exiting("MailTrackingDefaultsSqlDAO","findPartyName");
		String partyName="";
		return query.getSingleResult(getStringMapper("PTYNAM"));
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 *
	 * @param companyCode
	 * @param receptacleID
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findOffloadReasonForMailbag(java.lang.String)
	 */
	public String findOffloadReasonForMailbag(String companyCode,
											  String receptacleID) throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findOffloadReasonForMailbag");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_OFFLOADREASON_FOR_MAILBAG);
		query.setParameter(1, companyCode);
		query.setParameter(2, receptacleID);
		log
				.exiting("MailTrackingDefaultsSqlDAO",
						"findOffloadReasonForMailbag");
		return query.getSingleResult(getStringMapper("OFLRSNCOD"));
	}

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 *
	 * @param companyCode
	 * @param containerNumber
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findOffloadReasonForULD(java.lang.String)
	 */
	public String findOffloadReasonForULD(String companyCode,
										  String containerNumber) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findOffloadReasonForULD");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_OFFLOADREASON_FOR_CONTAINER);
		query.setParameter(1, companyCode);
		query.setParameter(2, containerNumber);
		log.exiting("MailTrackingDefaultsSqlDAO", "findOffloadReasonForULD");
		return query.getSingleResult(getStringMapper("OFLRSNCOD"));
	}

	/**
	 * Find the damage reason for mailbag Sep 14, 2006, a-1739
	 *
	 * @param companyCode
	 * @param receptacleID
	 * @param airportCode
	 * @return the damage reason
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findDamageReason(java.lang.String,
	 *      java.lang.String, String)
	 */
	public String findDamageReason(String companyCode, String receptacleID,
								   String airportCode) throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findDamageReason");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_DAMAGE_REASON);
		query.setParameter(1, companyCode);
		query.setParameter(2, receptacleID);
		query.setParameter(3, airportCode);
		log.exiting("MailTrackingDefaultsSqlDAO", "findDamageReason");
		return query.getSingleResult(getStringMapper("DMGCOD"));
	}


	/**
	 * Added by A-4072
	 * @param ConsignmentInformationVO
	 * Added for getting orgin-destination level contract ref
	 */
	public Collection<CarditReferenceInformationVO> findCCForSendResdit(ConsignmentInformationVO consgmntInfo)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findCCForSendResdit");
		Collection<CarditReferenceInformationVO> carditRefInfoVos =null;
		if(consgmntInfo == null )
		{
			return null;
		}
		Query query = getQueryManager().createNamedNativeQuery(FIND_CC_SENDRESDIT);
		query.setSensitivity(true);
		int index = 0;
		query.append(" And MST.CSGDOCNUM=?");
		query.setParameter(++index,consgmntInfo.getConsignmentID());
		carditRefInfoVos = query.getResultList(new SendResditMultiMapper());
		log.exiting("MailTrackingDefaultsSqlDAO", "findCCForSendResdit");
		return carditRefInfoVos;
	}


	/**
	 *
	 */
	public HashMap<String, String> findRecepientForXXResdits (
			Collection<ConsignmentInformationVO> consignmentInformationVOsForXX)
			throws SystemException, PersistenceException {

		HashMap<String,String> xxResditRecepientMap = new HashMap<String,String>();
		if (consignmentInformationVOsForXX == null || consignmentInformationVOsForXX.size() <= 0)
		{
			return xxResditRecepientMap;
		}

		int idx1 = 0;
		int idx2 = 0;
		boolean first = true;

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		Query queryMail = null;
		Query  queryUld = null;

		queryMail = getQueryManager().createNamedNativeQuery(FIND_RPT_FOR_XXMAILBAGS );
		queryMail.setParameter(++idx1, logonAttributes.getCompanyCode());
		queryUld = getQueryManager().createNamedNativeQuery (FIND_RPT_FOR_XXULDS );
		queryUld.setParameter(++idx2, logonAttributes.getCompanyCode());

		StringBuilder consignments = new StringBuilder ();
		for(ConsignmentInformationVO consignmentInformationVO: consignmentInformationVOsForXX){
			if(first) {
				first = false;
				consignments.append("AND	rdt.cdtkey IN ( ?");
				queryMail.setParameter(++idx1, consignmentInformationVO.getConsignmentID());
				queryUld.setParameter(++idx2, consignmentInformationVO.getConsignmentID());
			}else {
				consignments.append(",? ");
				queryMail.setParameter(++idx1, consignmentInformationVO.getConsignmentID());
				queryUld.setParameter(++idx2, consignmentInformationVO.getConsignmentID());
			}
		}
		consignments.append(" ) ");

		queryMail.append(consignments.toString());
		queryMail.append("UNION");
		queryUld.append(consignments.toString());
		queryMail.combine(queryUld);


		return ((ArrayList<HashMap<String, String>>) queryMail.getResultList(new XXResditRecepientMultiMapper())).get(0);

	}


	/**
	 * @author A-3251 SREEJITH P.C.
	 * This  method is used to find the details of uld and bulk to generate a report for Daily Mail Station
	 * @param filterVO
	 * @return reportVOList
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
			throws SystemException,ReportGenerationException
	{
		String baseQry = null;
		Query qry=null;
		baseQry = getQueryManager().getNamedNativeQueryString(MAILTRACKING_DEFAULTS_GENERATE_DAILYMAILSTATION_REPORT);
		qry = new DailyMailStationFilterQuery(baseQry,filterVO);
		return qry.getResultList(new DailyMailStationMapper());
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
	public Page<PostalAdministrationVO> findPALov(String companyCode, String paCode, String paName,
												  int pageNumber,int defaultSize) throws SystemException, PersistenceException {
		log.entering(MODULE, "findPALov");
		// modified the method by A-5103 for ICRD-32647
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_PALOV);
		masterQuery.append(queryString);
		if(defaultSize==0){
			defaultSize=25;
		}
		PageableNativeQuery<PostalAdministrationVO> pgNativeQuery =
				new PageableNativeQuery<PostalAdministrationVO>(defaultSize,-1, masterQuery.toString(),
						new PALovMapper());
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
			paName = paName.replace(MailConstantsVO.STAR, MailConstantsVO.PERCENTAGE) +
					MailConstantsVO.PERCENTAGE;
			pgNativeQuery.append("AND UPPER(POANAM) LIKE ?");
			pgNativeQuery.setParameter(++index, paName.toUpperCase());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);
	}


	/**
	 * Added for icrd-110909
	 * @param companyCode,airportCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<String> findOfficeOfExchangesForAirport(
			String companyCode, String airportCode)
			throws SystemException,PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findOfficeOfExchangesForAirport");
		Collection<String> oEToCurrentAirport = new ArrayList<String>();
		int idx = 0;
		Query query = getQueryManager().createNativeQuery(" SELECT EXGOFCCOD FROM MALEXGOFCMST EXGOFC ");
		query.append(" INNER JOIN SHRARPMST ARPMST ")
				.append(" ON ARPMST.CMPCOD = EXGOFC.CMPCOD ")
				.append(" AND ARPMST.CTYCOD = EXGOFC.CTYCOD ")
				.append(" WHERE ARPMST.CMPCOD = ? ")
				.append(" AND ARPMST.ARPCOD  = ? ");
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, airportCode);
		log.log(Log.FINE, "findOfficeOfExchangeNearToAirport--query--->>",
				query.toString());
		oEToCurrentAirport.addAll(query.getResultList(getStringMapper("EXGOFCCOD")));
		log.exiting("MailTrackingDefaultsSqlDAO","findOfficeOfExchangeNearToAirport");
		return oEToCurrentAirport;
	}


	/**
	 * @author A-4809
	 *
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findDSNMailbags");
		Collection<MailbagVO> mailbagVO = new ArrayList<MailbagVO>();
		Query query = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_DEFAULTS_FINDDSNMAILBAG);
		int index = 0;
		query.setParameter(++index, dsnVO.getCompanyCode());
		query.setParameter(++index, dsnVO.getCarrierId());
		query.setParameter(++index, dsnVO.getFlightNumber());
		query.setParameter(++index, dsnVO.getFlightSequenceNumber());
		query.setParameter(++index, dsnVO.getSegmentSerialNumber());
		query.setParameter(++index, dsnVO.getDsn());
		query.setParameter(++index, dsnVO.getOriginExchangeOffice());
		query.setParameter(++index, dsnVO.getDestinationExchangeOffice());
		query.setParameter(++index, dsnVO.getMailClass());
		query.setParameter(++index, dsnVO.getMailSubclass());
		query.setParameter(++index, dsnVO.getMailCategoryCode());
		query.setParameter(++index, dsnVO.getYear());
		return query.getResultList(new MailbagMapper());
	}
	/**
	 *
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public PostalAdministrationVO findPADetails(String companyCode,
												String officeOfExchange) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findPADetails");
		PostalAdministrationVO postalAdministrationVO = null;
		Query query = getQueryManager().createNamedNativeQuery(FIND_PA_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, officeOfExchange);
		postalAdministrationVO = query
				.getSingleResult(new Mapper<PostalAdministrationVO>() {
					public PostalAdministrationVO map(ResultSet rs)
							throws SQLException {
						PostalAdministrationVO postalAdminVO = new PostalAdministrationVO();
						postalAdminVO.setPaCode(rs.getString("POACOD"));
						postalAdminVO.setBasisType(rs.getString("BASTYP"));
						return postalAdminVO;
					}
				});
		log.exiting("MailTrackingDefaultsSqlDAO", "findPADetails");
		return postalAdministrationVO;
	}
	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO
	 * @throws SystemException
	 */
	public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "validatePoaDetails");
		int idx = 0;
		String createQuery =null;
		String dynamicQuery =null;
		String finalCreateQuery=null;
		createQuery =getQueryManager().getNamedNativeQueryString(VALIDATE_POADETAILS);
		if(isOracleDataSource()) {
			dynamicQuery="AND ? BETWEEN VLDFRM AND VLDTOO ";
		}else{
			dynamicQuery= "AND cast(? as timestamp ) BETWEEN VLDFRM AND VLDTOO ";
		}
		finalCreateQuery = String.format(createQuery, dynamicQuery);
		Query qry = getQueryManager().createNativeQuery(finalCreateQuery);
		qry.setParameter(++idx, postalAdministrationDetailsVO.getCompanyCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getPoaCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getParCode());
		qry.setParameter(++idx, postalAdministrationDetailsVO.getValidFrom().toDisplayDateOnlyFormat());
		log.exiting("MailTrackingDefaultsSqlDAO", "validatePoaDetails");
		return qry.getSingleResult(new ValidatePoaDetailsMapper());
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findAllPACodes(com.ibsplc.icargo.business.mailtracking.mra.gpabilling.vo.GenerateInvoiceFilterVO)
	 *	Added by 			: A-4809 on 08-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<PostalAdministrationVO> findAllPACodes(
			GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "FindAllPAcodes");
		Query query = null;
		String  baseQuery=null;
		baseQuery = getQueryManager().getNamedNativeQueryString(MAILTRACKING_DEFAULTS_FINDALLPOACODES);
		query=new FindAllPAFilterQuery(generateInvoiceFilterVO,baseQuery);
		return query.getResultList(new PostalAdministrationMapper());
	}
	public Collection<MailScanDetailVO> findScannedMailDetails(
			String companyCode,int uploadCount) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findScannedMailDetails");
		int index = 0;
		Collection<MailScanDetailVO> sacnnedStrings = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				SCANNED_MAIL_HHT_DETAILS_OPERATION);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, uploadCount);

		return qry.getResultList(new ScannedMailDetailsMapper());
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#performUploadCorrection(java.lang.String)
	 *	Added by 			: A-4809 on Dec 4, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public void performUploadCorrection(String companyCode)
			throws SystemException, PersistenceException {
		log.entering("MailTrackkingDefaultsSQLDAO", "performUploadCorrection");
		int index = 0;
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				PERFORM_UPLOAD_CRCTN);
		procedure.setParameter(++index, companyCode);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.setSensitivity(true);
		procedure.execute();
		log.exiting("MailTrackkingDefaultsSQLDAO", "performUploadCorrection");
	}
	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param mailHandedOverFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailHandedOverVO> generateMailHandedOverReport(MailHandedOverFilterVO
																			 mailHandedOverFilterVO) throws SystemException, PersistenceException{
		log.entering(MODULE, "generateMailHandedOverReport");
		String flightBaseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDED_OVER_TOFLIGHT);
		String carrierBaseQuery = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDED_OVER_TOCARRIER);
		Query query=new MailHandedOverFilterQuery(mailHandedOverFilterVO,flightBaseQuery,carrierBaseQuery);
		return query.getResultList(new MailHandedOverReportMapper());
	}
	/**
	 @author a-1936
	  * Added By Karthick V as the  part of  the Air NewZealand CR...
	  * This method is used to find all  the DSNs and the mail bags Required For the Impport Manifest Report..
	  * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public 	MailManifestVO  findImportManifestDetails(OperationalFlightVO operationalFlightVo)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findDSNsForImportManifestReport");
		MailManifestVO mailManifestV0 = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_IMPORT_MANIFEST_DETAILS);
		int index=0;
		qry.setParameter(++index, operationalFlightVo.getCompanyCode());
		qry.setParameter(++index, operationalFlightVo.getCarrierId());
		qry.setParameter(++index, operationalFlightVo.getFlightNumber());
		qry.setParameter(++index, operationalFlightVo.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVo.getPou());
		List<MailManifestVO> mailManifestVOs =qry.getResultList(new ImportManifestReportMultiMapper());
		if(mailManifestVOs!=null && mailManifestVOs.size()>0){
			mailManifestV0=mailManifestVOs.get(0);
		}
		return mailManifestV0;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findOnlineFlightsAndConatiners(java.lang.String)
	 *	Added by 			: A-4809 on Sep 29, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public List<MailArrivalVO> findOnlineFlightsAndConatiners(
			String companyCode) throws SystemException, PersistenceException {
		log.entering(MODULE, "findOnlineFlightsAndConatiners");
		List<MailArrivalVO> mailArrivalVOs = null;
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_ONLINEFLIGHTS_CONTAINERS);
		String dynamicquery = null;
		if(isOracleDataSource()){
			dynamicquery = " AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <= (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS')) FROM SHRUSEARPPAR ARPPAR "
					+	"WHERE ARPPAR.CMPCOD = LEG.CMPCOD "
					+	"AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset' "
					+	"AND ARPPAR.ARPCOD = ASGFLTSEG.POU "
					+	"AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) >0 )) "
					+	"OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS'))  <= (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))  FROM SHRUSEARPPAR ARPPAR ";
		}else{
			dynamicquery = " AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <= (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS')) FROM SHRUSEARPPAR ARPPAR "
					+	"WHERE ARPPAR.CMPCOD = LEG.CMPCOD "
					+	"AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset' "
					+	"AND ARPPAR.ARPCOD = ASGFLTSEG.POU "
					+	"AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) >0 )) "
					+	"OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS'))  <= (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))  FROM SHRUSEARPPAR ARPPAR ";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		return qry.getResultList(new AcquitContainerFromFlightMultiMapper());
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findFlightsForArrival(java.lang.String)
	 *	Added by 			: A-4809 on Sep 30, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public Collection<OperationalFlightVO> findFlightsForArrival(
			String companyCode) throws SystemException, PersistenceException {
		Collection<OperationalFlightVO> flightVOs = null;
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_FLIGHTS_ARRIVAL);
		String dynamicquery = null;
		if(isOracleDataSource()){
			dynamicquery ="AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))"
					+" FROM SHRUSEARPPAR ARPPAR"
					+" WHERE ARPPAR.CMPCOD = LEG.CMPCOD"
					+" AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset'"
					+" AND ARPPAR.ARPCOD= ASGFLTSEG.POU"
					+" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0))"
					+" OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(SYSDATE -(TO_NUMBER(COALESCE(ARPPAR1.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))";
		}else{
			dynamicquery="AND ( ( TO_NUMBER(TO_CHAR(LEG.ATA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))"
					+" FROM SHRUSEARPPAR ARPPAR"
					+" WHERE ARPPAR.CMPCOD = LEG.CMPCOD"
					+" AND ARPPAR.PARCOD ='mailtracking.defaults.arrivalOffset'"
					+" AND ARPPAR.ARPCOD= ASGFLTSEG.POU"
					+" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0))"
					+" OR ( TO_NUMBER(TO_CHAR(LEG.STA,'YYYYMMDDHH24MISS')) <="
					+ " (SELECT TO_NUMBER(TO_CHAR(now() -  interval '1 day' *(TO_NUMBER(COALESCE(ARPPAR1.PARVAL,'0'))/24),'YYYYMMDDHH24MISS'))";
		}
		query = String.format(query, dynamicquery);
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, companyCode);
		return qry.getResultList(new FlightArrivalMapper());
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findArrivalDetailsForReleasingMails(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO)
	 *	Added by 			: A-4809 on Sep 30, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param flightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public List<ContainerDetailsVO> findArrivalDetailsForReleasingMails(
			OperationalFlightVO flightVO) throws SystemException,PersistenceException {
		List<ContainerDetailsVO> containerDetailsVO =null;
		int index=0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_ARRIVAL);
		qry.setParameter(++index, flightVO.getCompanyCode());
		qry.setParameter(++index, flightVO.getCarrierId());
		qry.setParameter(++index, flightVO.getFlightNumber());
		qry.setParameter(++index, flightVO.getFlightSequenceNumber());
		qry.setParameter(++index, flightVO.getPou());
		return qry.getResultList(new MailbagForArrivalMultiMapper());
	}
	/**
	 * @author A-1885
	 */
	public Collection<OperationalFlightVO> findFlightForMailOperationClosure(
			String companyCode, int time,String airportCode) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findMailOnHandDetails");
		int index = 0;

		LocalDate date = null;
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logon.getAirportCode(),Location.ARP,true);
		GMTDate gmtCovTime = date.toGMTDate();

		Collection<OperationalFlightVO> flights = null;
		String qryString = getQueryManager().getNamedNativeQueryString(
				FIND_FLIGHTSFOR_MAILCLOSURE);

		String modifiedStr1 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "( (LEG.ATDUTC IS NOT NULL AND ? >= (LEG.ATDUTC + to_number(COALESCE(ARPPAR.PARVAL,'0'))/24)) OR LEGSTA = 'CAN')";
		}
		else
		{
			modifiedStr1 = "( (LEG.ATDUTC IS NOT NULL AND ? >= (LEG.ATDUTC +(COALESCE(ARPPAR.PARVAL::interval,'0')))) OR LEGSTA = 'CAN')";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query qry = getQueryManager().createNativeQuery(qryString);
		qry.setParameter(++index, gmtCovTime);
		qry.setParameter(++index, companyCode);
		if (time > 0 && isOracleDataSource()) {
			qry.append(" AND TO_NUMBER(TO_CHAR(MTK.FLTDAT,'YYYYMMDD')) > TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD')) - ? ");
			qry.setParameter(++index, time);
		}
		if (time > 0 && !isOracleDataSource()) {
			qry.append(" AND TO_NUMBER(TO_CHAR(MTK.FLTDAT,'YYYYMMDD::text')) > TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD::text')) - ? ");
			qry.setParameter(++index, time);
		}
		return qry.getResultList(new FlightsForClosureMapper());
	}
	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findAnyContainerInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findAnyContainerInAssignedFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ANYCONTAINER_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		qry.setParameter(6, operationalFlightVO.getLegSerialNumber());
		if (isOracleDataSource())
		{
			qry.append(" AND ROWNUM=1");
		}
		else{
			qry.append(" LIMIT 1");
		}
		Mapper<String> stringMapper = getStringMapper("CON");
		log.exiting("MailTrackingDefaultsSqlDAO", "findAnyContainerInAssignedFlight");
		return qry.getSingleResult(stringMapper);
	}
	/**
	 * @author A-5166
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findImportFlghtsForArrival");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAILTRACKING_DEFAULTS_FINDIMPORTFLIGHTSFORARRIVAL);
		String modifiedStr1 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "AND LEG.STAUTC BETWEEN ( ? + 1 - TO_NUMBER(COALESCE(PERIOD1.PARVAL,'0')) - TO_NUMBER(COALESCE(OFFSET1.PARVAL,'0'))) AND (? + 1 - TO_NUMBER(COALESCE(OFFSET1.PARVAL,'0')))";
		}
		else
		{
			modifiedStr1 = "AND LEG.STAUTC BETWEEN ((cast(? as timestamp)  + INTERVAL '1 day') + make_interval(days =>1) - make_interval(days => (COALESCE(PERIOD1.PARVAL::INT, '0'))) - make_interval(days => to_number(COALESCE(OFFSET1.PARVAL, '0')::INT))) AND ((cast(? as timestamp)  + INTERVAL '1 day') + make_interval(days =>1) - make_interval(days => (COALESCE(OFFSET1.PARVAL::INT, '0'))))";
		}
		qryString = String.format(qryString, modifiedStr1);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index=0;
		//Modified for ICRD-143715 starts
		LocalDate date = null;
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logon.getAirportCode(),Location.ARP,true);
		GMTDate gmtCovTime = date.toGMTDate();
		query.setParameter(++index, companyCode);
		query.setParameter(++index, gmtCovTime);
		query.setParameter(++index, gmtCovTime);
/*		query.setParameter(++index, companyCode);
		 query.setParameter(++index, gmtCovTime);
		 query.setParameter(++index, gmtCovTime);*/
		//Modified for ICRD-143715 ends
		return query.getResultList(new OperationalFlightMapper());
	}
	/**
	 *
	 */
	public Page<DespatchDetailsVO> findDSNs(
			DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
			throws PersistenceException, SystemException {
		log.entering(MODULE, "findCapturedNotAcceptedDSNs");
		PageableNativeQuery<DespatchDetailsVO> pgqry = null;
		Page<DespatchDetailsVO> despatchDetailsVOs = null;
		String baseQuery = null;
		StringBuilder rankQuery = null;
		int index = 0;
		baseQuery = getQueryManager().getNamedNativeQueryString(FIND_DESPATCHES);
		rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY)
				.append(baseQuery);
		pgqry = new PageableNativeQuery<DespatchDetailsVO>(	dSNEnquiryFilterVO.getTotalRecords(), rankQuery.toString(),new AssignedDSNMapper());

		if (dSNEnquiryFilterVO.getCompanyCode() != null
				&& dSNEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
			pgqry.append("  MALMST.CMPCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getCompanyCode()
					.trim());
		}
		if ("true".equals(dSNEnquiryFilterVO.getPltEnabledFlag())) {
			pgqry.append(" AND MALMST.MALTYP = ? ");
			if (FLAG_FALSE.equalsIgnoreCase(dSNEnquiryFilterVO.getPltEnabledFlag())) {

				pgqry.setParameter(++index, "D");
			} else {
				log.log(Log.FINEST, "inside plt$$$$ ");
				pgqry.setParameter(++index, "M");

			}
		}
		if (dSNEnquiryFilterVO.getConsignmentNumber() != null
				&& dSNEnquiryFilterVO.getConsignmentNumber().trim().length() > 0) {
			pgqry.append(" AND  MALMST.CSGDOCNUM = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO
					.getConsignmentNumber().trim());
		}
		if (dSNEnquiryFilterVO.getPaCode() != null
				&& dSNEnquiryFilterVO.getPaCode().trim().length() > 0) {
			pgqry.append(" AND  MALMST.POACOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getPaCode().trim());
		}
		if (dSNEnquiryFilterVO.getMailCategoryCode() != null
				&& dSNEnquiryFilterVO.getMailCategoryCode().trim().length() > 0) {
			pgqry.append(" AND MALMST.MALCTG = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO
					.getMailCategoryCode().trim());
		}
		if (dSNEnquiryFilterVO.getMailSubClass() != null
				&& dSNEnquiryFilterVO.getMailSubClass().trim().length() > 0) {
			pgqry.append(" AND MALMST.MALSUBCLS = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getMailSubClass()
					.trim());
		}
		if (dSNEnquiryFilterVO.getOriginCity() != null
				&& dSNEnquiryFilterVO.getOriginCity().trim().length() > 0) {
			pgqry.append(" AND ORGCTY.CTYCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getOriginCity()
					.trim());
		}
		if (dSNEnquiryFilterVO.getDestinationCity() != null
				&& dSNEnquiryFilterVO.getDestinationCity().trim().length() > 0) {
			pgqry.append(" AND DSTCTY.CTYCOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getDestinationCity()
					.trim());
		}
		if (dSNEnquiryFilterVO.getDsn() != null
				&& dSNEnquiryFilterVO.getDsn().trim().length() > 0) {
			pgqry.append(" AND MALMST.DSN = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getDsn().trim());
		}
		if (!"ALL".equalsIgnoreCase(dSNEnquiryFilterVO.getContainerType()) && dSNEnquiryFilterVO.getContainerType() != null
				&& dSNEnquiryFilterVO.getContainerType().trim().length() > 0) {
			pgqry.append(" AND CONMST.CONTYP = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getContainerType().trim());
		}
		if (dSNEnquiryFilterVO.getTransitFlag() != null
				&& dSNEnquiryFilterVO.getTransitFlag().trim().length() > 0) {
			pgqry.append(" AND CONMST.TRAFLG = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getTransitFlag().trim());
		}
		if ("TRUE".equalsIgnoreCase(dSNEnquiryFilterVO.getCapNotAcpEnabledFlag())) {
			pgqry.append(" AND ULDSEG.ULDNUM IS NULL ");
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(dSNEnquiryFilterVO.getOperationType())){
			pgqry.append(" AND FLT.EXPCLSFLG IN ('C','O') ");
		}else{
			pgqry.append(" AND FLT.IMPCLSFLG IN ('C','O') ");
		}

		if (dSNEnquiryFilterVO.getFlightNumber() != null && dSNEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
			pgqry.append(" AND ULDSEG.FLTNUM = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getFlightNumber());
		}
		if (dSNEnquiryFilterVO.getCarrierId() > 0 ) {
			pgqry.append(" AND ULDSEG.FLTCARIDR = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getCarrierId());
		}
		if (dSNEnquiryFilterVO.getPaCode() != null && dSNEnquiryFilterVO.getPaCode().trim().length() > 0) {
			pgqry.append(" AND ULDSEG.POACOD = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getPaCode());
		}



		if (dSNEnquiryFilterVO.getAirportCode() != null && dSNEnquiryFilterVO.getAirportCode().trim().length() > 0) {

			pgqry.append(" AND FLT.ARPCOD = ?");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getAirportCode());
		}

		String mailClass = dSNEnquiryFilterVO.getMailClass();

		if (dSNEnquiryFilterVO.getMailClass() != null
				&& dSNEnquiryFilterVO.getMailClass().trim().length() > 0) {
			log.log(Log.FINEST, "inside mailClass Check..class is$$$$ ",
					mailClass);
			if (mailClass.contains(MailConstantsVO.MALCLS_SEP)) {

				pgqry.append(" AND MALMST.MALCLS IN ( '1','2','3','4','5','6') ");
			} else {
				pgqry.append(" AND MALMST.MALCLS = ? ");
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getMailClass()
						.trim());
			}
		}
		if ("TRUE".equalsIgnoreCase(dSNEnquiryFilterVO.getCapNotAcpEnabledFlag())) {
			LocalDate fromDate = dSNEnquiryFilterVO.getFromDate();
			LocalDate toDate = dSNEnquiryFilterVO.getToDate();
			if (fromDate != null && toDate != null) {
				pgqry.append(" AND CSGMST.CSGDAT BETWEEN ");
				pgqry.append(" ? AND ? ");
				pgqry.setParameter(++index, fromDate);
				pgqry.setParameter(++index, toDate);
			}
		}else{
			if ( dSNEnquiryFilterVO.getFromDate() != null && dSNEnquiryFilterVO.getToDate()  != null) {

				pgqry.append(" AND TRUNC(ULDSEG.ACPDAT) BETWEEN ? AND ? ");
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getFromDate());
				pgqry.setParameter(++index, dSNEnquiryFilterVO.getToDate());
			}

		}
		if (dSNEnquiryFilterVO.getFlightDate() != null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(flt.FLTDAT,'YYYYMMDD')) = ? ");
			pgqry.setParameter(++index, dSNEnquiryFilterVO.getFlightDate().toSqlDate().toString().replace("-", ""));
		}
		pgqry.append("GROUP BY MALMST.DSNIDR");
		log.log(Log.FINE, "BASEQUERY==&&&>", baseQuery);

		pgqry.append(") RESULT_TABLE");


		despatchDetailsVOs = pgqry.getPage(pageNumber);
		//Commented by A-4809 as part of ICRD-199636 PLT enable not getting updated in jsp
/*		if ((despatchDetailsVOs != null) && (despatchDetailsVOs.size() > 0)) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				despatchDetailsVO.setPltEnabledFlag(dSNEnquiryFilterVO
						.getPltEnabledFlag());
			}
		}*/
		log.exiting(MODULE, "findCapturedNotAcceptedDespatches--");
		return despatchDetailsVOs;
	}

	/**Method	    :	findUpuCodeNameForPA
	 *	Added by 	:   A-5526 on Jun 24, 2016 for CRQ-103713
	 * 	Used for 	:   findUpuCodeNameForPA
	 *	Parameters	:	@param companyCode,paCode
	 *	Parameters	:	@return String
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findUpuCodeNameForPA(String companyCode,
									   String paCode) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findUpuCodeName");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_UPUCODE_NAME_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		log.exiting("MailTrackingDefaultsSqlDAO", "findUpuCodeName");
		return query.getSingleResult(getStringMapper("UPUCODNAME"));
	}

	/**
	 * Find the PA corresponding to a Mailbox ID MAY 23, 2016, a-5526
	 *
	 * @param companyCode
	 * @param mailboxId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPAForMailboxID(String companyCode,
									 String mailboxId,String originOE) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findPAForMailboxID");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_PA_FOR_MAILBOXID);
		query.setParameter(1, companyCode);
		query.setParameter(2, mailboxId);
		query.setParameter(3, originOE);
		log.exiting("MailTrackingDefaultsSqlDAO", "findPAForMailboxID");
		return query.getSingleResult(getStringMapper("POACOD"));
	}

	/**
	 * Invokes the EVT_RCR, EVT_TMR procedure
	 *
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void invokeResditReceiver(String companyCode)
			throws SystemException, PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "invokeResditReceiver");
		LocalDate todaysDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				PROC_RDT_EVT_RCR);
		/*
		 * ICRD-89703 : Irrespective of the logged in station, message need
		 * to be sent after the configured time delay
		 */
		String date = todaysDate.toGMTDate().toDisplayFormat("dd-MM-yy HH:mm:ss");
		procedure.setParameter(1, companyCode);
		procedure.setParameter(2, date);
		procedure.setOutParameter(3, SqlType.STRING);
		procedure.execute();

		log.log(Log.FINEST, "resdit receiver out paramter from EVT_RCR is == ",
				procedure.getParameter(2));
		callResditTimerManager(companyCode);
		log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, "invokeResditReceiver");
	}

	private void callResditTimerManager(String companyCode)
			throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO", "callResditTimerManager");

		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				PROC_RDT_EVT_TMR);
		procedure.setParameter(1, companyCode);
		procedure.setParameter(2, new GMTDate(true)
				.toDisplayFormat("yyMMddHHmm"));
		procedure.setOutParameter(3, SqlType.STRING);
		procedure.execute();

		log.log(Log.FINEST, "resdit receiver out paramter from TMR_MGR is == ",
				procedure.getParameter(3));
		log.exiting("MailTrackingDefaultsSqlDAO", "callResditTimerManager");
	}

	/**
	 * Checks for any flagged resdit events and returns them
	 *
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ResditEventVO> findResditEvents(String companyCode)
			throws SystemException, PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "checkForResditEvents");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_RESDIT_EVENTS);
		query.setParameter(1, companyCode);
		Collection<ResditEventVO> events = query
				.getResultList(new ResditEventMapper());
		if (events != null && events.size() > 0) {
			for (ResditEventVO resditEventVO : events) {
				resditEventVO.setCompanyCode(companyCode);
			}
		}
		return events;
	}

	/**
	 * @author A-5249
	 * method: findMailbagsforFlightSegments
	 * to change the assigned flight status to TBA if mailbag present
	 * CR Id: ICRD-84046
	 * @param operationalFlightVO
	 * @param segments
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean findMailbagsforFlightSegments(OperationalFlightVO operationalFlightVO,
												 Collection<FlightSegmentVO> segments,String cancellation) throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "**findMailbagsforFlightSegments");
		if(segments==null || segments.size()==0){
			return false;
		}
		boolean isMailBagPresent = false;
		Query query = getQueryManager()
				.createNamedNativeQuery(FIND_MAILBAGS_FLIGHTSEGMENTS);
		int idx = 0;
		query.setParameter(++idx, operationalFlightVO.getCompanyCode());
		query.setParameter(++idx, operationalFlightVO.getFlightNumber());
		query.setParameter(++idx, operationalFlightVO.getCarrierId());
		query.setParameter(++idx, operationalFlightVO.getFlightSequenceNumber());
		if(MailConstantsVO.FLAG_NO.equals(cancellation)){
			boolean isFirstPass=true;
			StringBuilder segmentString = null;
			//Modified for bug ICRD-161954 by A-5526
			query.append(" AND seg.pol||'-'||seg.pou IN ( ");
			for(FlightSegmentVO segment:segments){
				//Added if check for bug ICRD-161954 by A-5526

				if(!isFirstPass){
					query.append(" , ");
				}
				query.append(" ? ");
				segmentString = new StringBuilder(segment.getSegmentOrigin()).
						append("-").append(segment.getSegmentDestination());
				query.setParameter(++idx, segmentString.toString());
				isFirstPass=false;
			}
			query.append(" )");
		}
		List<Integer> results=query.getResultList(getIntMapper("1"));
		if(results!=null && results.size()>0) {
			isMailBagPresent=true;
		}
		log.log(Log.INFO, "Mailbags present in deleted segments--->",isMailBagPresent);
		log.exiting("MailTrackingDefaultsSqlDAO", "findMailbagsforFlightSegments");
		return isMailBagPresent;
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findMailBagsForTransportCompletedResdit(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO)
	 *	Added by 			:
	 * 	Used for 	:
	 *	Parameters	:	@param operationalFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<MailbagVO> findMailBagsForTransportCompletedResdit(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE+ LOG_DELIMITER, "flagTransportCompletedResditForMailbags");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FOR_TRANSPORTCOMPLETED_RESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry .setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPou());
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry .setParameter(++index, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(++index, operationalFlightVO.getPou());
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		/*if (mailBagVos != null && mailBagVos.size() > 0) {
			mailBagVos = validateMailBagsForTransportCompletedResdit(operationalFlightVO,
			mailBagVos);
		}*/
		if (mailBagVos != null && mailBagVos.size() > 0) {
			for (MailbagVO mailbag : mailBagVos) {
				mailbag.setCompanyCode(operationalFlightVO.getCompanyCode());
				if(operationalFlightVO.getCarrierCode()!=null){
					mailbag.setCarrierCode(operationalFlightVO.getCarrierCode());
				}
				mailbag.setCarrierId(operationalFlightVO.getCarrierId());
				mailbag.setFlightDate(operationalFlightVO.getFlightDate());
				mailbag.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				mailbag.setFlightNumber(operationalFlightVO.getFlightNumber());
				mailbag
						.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			}
		}
		log.exiting(MODULE+ LOG_DELIMITER, "flagTransportCompletedResditForMailbags");
		return mailBagVos;
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mailtracking.defaults.MailTrackingDefaultsDAO#findUldsForTransportCompletedResdit(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO)
	 *	Added by 			:
	 * 	Used for 	:
	 *	Parameters	:	@param operationalFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE+"=============>>>", "findUldsForTransportCompletedResdit");
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ULDS_FOR_TRANSPORT_COMPLETED_RESDIT);
		qry.setParameter(++index, operationalFlightVO.getCompanyCode());
		qry.setParameter(++index, operationalFlightVO.getCarrierId());
		qry.setParameter(++index, operationalFlightVO.getFlightNumber());
		qry
				.setParameter(++index, operationalFlightVO
						.getFlightSequenceNumber());
		/*qry.setParameter(++index, operationalFlightVO.getPol());
		qry.setParameter(++index, operationalFlightVO.getPou()); */
		containerDetailsVOs = qry
				.getResultList(new UldsForUpliftedResditMapper());
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				containerDetailsVO.setCompanyCode(operationalFlightVO
						.getCompanyCode());
				containerDetailsVO.setCarrierCode(operationalFlightVO
						.getCarrierCode());
				containerDetailsVO.setCarrierId(operationalFlightVO
						.getCarrierId());
				containerDetailsVO.setFlightDate(operationalFlightVO
						.getFlightDate());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO
						.getFlightNumber());
			}
		}
		log.exiting(MODULE+"=============>>>", "findUldsForTransportCompletedResdit");
		return containerDetailsVOs;
	}
	/**
	 *
	 */
	public Collection<OperationalFlightVO> findOperationalFlightForMRD(
			HandoverVO handoverVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findInboundFlightForMailOperation");
		int index = 0;
		boolean legDstExist=false;
		Collection<OperationalFlightVO> flights = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_OPERATIONFLIGHTS_MRD);
		if(handoverVO.getFlightDate() != null){
			qry.append(" AND TRUNC(FLTMST.FLTDAT) = ? ");
			qry.setParameter(++index, handoverVO.getFlightDate().toSqlTimeStamp());
		}else{
			qry.append(" AND FLTLEG.ATA IS NOT NULL AND FLTLEG.ATA = (SELECT MAX(ATA) FROM FLTOPRLEG WHERE ");
			qry.append(" CMPCOD = FLTMST.CMPCOD AND ");
			qry.append(" ATA <= ? ");
			qry.setParameter(++index, handoverVO.getHandOverdate_time().toSqlTimeStamp());
			qry.append(" AND LEGDST = ? ");
			if(handoverVO.getFlightNumber()!=null && handoverVO.getFlightNumber().trim().length()>0 &&
					!"0000".equals(handoverVO.getFlightNumber()) &&
					handoverVO.getCarrierCode()!=null && handoverVO.getCarrierCode().trim().length()>0){
				legDstExist=true;
				qry.append(" AND FLTNUM = FLTMST.FLTNUM AND FLTCARIDR = FLTMST.FLTCARIDR ");
				if(handoverVO.getDestination()!=null && handoverVO.getDestination().trim().length()>0)
				{
					qry.setParameter(++index, handoverVO.getDestination());
				}
				else
					qry.setParameter(++index, handoverVO.getDestAirport());
				if(handoverVO.getFltSeqNum()>0){
					qry.append(" AND FLTSEQNUM = ? ");
					qry.setParameter(++index, handoverVO.getFltSeqNum());
				}
			}else
			{
				qry.setParameter(++index, handoverVO.getDestAirport());
			}
			qry.append(" ) ");
		}
		qry.append(" WHERE FLTMST.CMPCOD = ?  AND FLTLEG.LEGDST = ? ");
		qry.setParameter(++index, handoverVO.getCompanyCode());
		if(MailConstantsVO.FLAG_YES.equals(handoverVO.getTbaFlightNeeded())){
			qry.append(" AND FLTMST.FLTSTA  IN ('ACT','TBA') ");
		}
		else{
			qry.append(" AND  FLTMST.FLTSTA = 'ACT' ");
		}
		if(handoverVO.getDestination()!=null && handoverVO.getDestination().trim().length()>0)
		{
			qry.setParameter(++index, handoverVO.getDestination());
		}
		else
			qry.setParameter(++index, handoverVO.getDestAirport());
		if(handoverVO.getFlightNumber()!=null && handoverVO.getFlightNumber().trim().length()>0 &&
				!"0000".equals(handoverVO.getFlightNumber()) &&
				handoverVO.getCarrierCode()!=null && handoverVO.getCarrierCode().trim().length()>0){
			qry.append(" AND FLTMST.FLTNUM  = ?  AND FLTMST.FLTCARIDR=" +
					" (SELECT ARLIDR  FROM SHRARLMST WHERE CMPCOD  = FLTMST.CMPCOD AND TWOAPHCOD = ? )");
			qry.setParameter(++index, handoverVO.getFlightNumber());
			qry.setParameter(++index, handoverVO.getCarrierCode());
			if(handoverVO.getFltSeqNum()>0){
				qry.append(" AND FLTMST.FLTSEQNUM = ? ");
				qry.setParameter(++index, handoverVO.getFltSeqNum());
			}
		}
		return qry.getResultList(new FlightsForClosureMapper());
	}
	/**
	 *
	 */
	public Collection<CarditVO>  findCarditDetailsForResdit (
			Collection<ResditEventVO> resditEvents) throws SystemException,
			PersistenceException {

		if (resditEvents == null || resditEvents.size() == 0)
		{
			return null;
		}

		ArrayList<ResditEventVO> eventsArrayList = (ArrayList<ResditEventVO>) resditEvents;
		Query query = null;

		int idx = 0;
		query = getQueryManager().createNamedNativeQuery(FIND_CDT_DTL);
		query.setParameter(++idx, eventsArrayList.get(0).getCompanyCode());

		boolean isFirst = true;
		String usConsignment = "US";

		query.append(" AND	MST.CSGDOCNUM IN ( ?");
		for (ResditEventVO resditEventVO : eventsArrayList) {

			if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(resditEventVO.getCarditExist())
					&& resditEventVO.getConsignmentNumber().startsWith(usConsignment)){

				if (isFirst) {
					isFirst = false;
					query.setParameter(++idx, resditEventVO.getConsignmentNumber());
				} else {
					query.append(",? ");
					query.setParameter(++idx, resditEventVO.getConsignmentNumber());
				}
			}


		}
		query.append(" ) ");

		if (!isFirst)
		{
			return query.getResultList(new CarditDetailsMapper());
		}
		else
			return null;

	}
	/**
	 *
	 */
	public PostalAdministrationVO findPartialResditFlagForPA(
			String companyCode, String paCode) throws SystemException,
			PersistenceException {
		log.entering("MailtrackinDefaultsSqlDAO", "findPartialResditFlagForPA");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_PARTIAL_RESDIT_FOR_PA_);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		PostalAdministrationVO postalAdministrationVO = query
				.getSingleResult(new PALovMapper());
		log.log(Log.FINE, "PostalAdministrationVO :", postalAdministrationVO);
		return postalAdministrationVO;
	}

	/**
	 *
	 */
	public Collection<ConsignmentRoutingVO> findConsignmentRoutingDetails(CarditEnquiryFilterVO carditEnqFilterVO)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO","findConsignmentRoutingDetails");
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_ROUTING_DETAILS);
		int idx = 0;
		//int consignmentSeqNumber = 1;
		log.log(Log.FINE, "--companyCode --- : ", carditEnqFilterVO.getCompanyCode());
			/*log.log(Log.FINE, "--consignmentSeqNumber --- : ",
					consignmentSeqNumber);*/
		log.log(Log.FINE,
				"--carditEnqFilterVO.getConsignmentDocument() --- : ",
				carditEnqFilterVO.getConsignmentDocument());
		log.log(Log.FINE, "--carditEnqFilterVO.getPaoCode() --- : ",
				carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		//query.setParameter(++idx, consignmentSeqNumber);
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());

		log.exiting("MailTrackingDefaultsSqlDAO","findConsignmentRoutingDetails");
		return  query.getResultList(new ConsignmentRoutingMapper());
	}

	/**
	 *
	 */
	public String findCityForOfficeOfExchange(String companyCode,String ExchangeOfficeCode )throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findCityForOfficeOfExchange");
		String cityCode="";
		Query query = getQueryManager().createNamedNativeQuery(FIND_CITY_CODE_FOR_OFFICEOFEXCHANGE);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, ExchangeOfficeCode);

		cityCode=query.getSingleResult(getStringMapper("CTYCOD"));
		log.log(Log.FINE,"cityCode::"+cityCode);

		log.exiting("MailTrackingDefaultsSqlDAO","findCityForOfficeOfExchange");
		return cityCode;

	}

	/**
	 *
	 */
	public Collection<TransportInformationVO> findRoutingDetailsFromCardit(CarditEnquiryFilterVO carditEnqFilterVO,String airport)
			throws SystemException,PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO","findRoutingDetailsFromCardit");
		Query query = getQueryManager().createNamedNativeQuery(FIND_ROUTING_DETAILS);
		int idx = 0;
		int consignmentSeqNumber = 1;
		log.log(Log.FINE, "--companyCode --- : ", carditEnqFilterVO.getCompanyCode());
		log.log(Log.FINE, "--consignmentSeqNumber --- : ",
				consignmentSeqNumber);
		log.log(Log.FINE,
				"--carditEnqFilterVO.getConsignmentDocument() --- : ",
				carditEnqFilterVO.getConsignmentDocument());
		log.log(Log.FINE, "--carditEnqFilterVO.getPaoCode() --- : ",
				carditEnqFilterVO.getPaoCode());
		query.setParameter(++idx, carditEnqFilterVO.getCompanyCode());
		query.setParameter(++idx, carditEnqFilterVO.getConsignmentDocument());
		query.setParameter(++idx, carditEnqFilterVO.getPaoCode());
		//query.setParameter(++idx, airport);

		log.exiting("MailTrackingDefaultsSqlDAO","findRoutingDetailsFromCardit");
		return  query.getResultList(new CarditRoutingMapper());
	}

	/**
	 *
	 */
	public Collection<TransportInformationVO> findTransportDetailsForMailbag(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findTransportDetailsForMailbag");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_TRANSPORT_FOR_MAILRESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		if (resditEventVO.getActualResditEvent() != null) {
			query.setParameter(++idx, resditEventVO.getActualResditEvent());
		} else {
			query.setParameter(++idx, resditEventVO.getResditEventCode());
		}
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findTransportDetailsForMailbag");
		return query.getResultList(new TransportInformationMapper());
	}

	/**
	 *
	 */
	public Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findMailbagDetailsForResdit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_FOR_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getConsignmentNumber());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());

		log
				.exiting("MailTrackingDefaultsSqlDAO",
						"findMailbagDetailsForResdit");
		return query.getResultList(new ReceptanceInformationMapper());
	}

	/**
	 *
	 */
	public Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findMailbagDetailsForXXResdit");
		log.log(Log.FINE, "Check----->>resditEventVO---->>>", resditEventVO);
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_FOR_XX_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		if(resditEventVO.getActualResditEvent()!=null && MailConstantsVO.RESDIT_XX.equals(resditEventVO.getActualResditEvent())){
			query.setParameter(++idx, MailConstantsVO.RESDIT_XX);
		}else{
			query.setParameter(++idx, resditEventVO.getResditEventCode());
		}

		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findMailbagDetailsForXXResdit");
		return query.getResultList(new ReceptanceInformationMapper());
	}

	/**
	 *
	 */
	public Collection<TransportInformationVO> findTransportDetailsForULD(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log
				.entering("MailTrackingDefaultsSqlDAO",
						"findTransportDetailsForULD");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_TRANSPORT_FOR_ULDRESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, resditEventVO.getEventPort());
		log.exiting("MailTrackingDefaultsSqlDAO", "findTransportDetailsForULD");
		return query.getResultList(new TransportInformationMapper());
	}

	/**
	 *
	 */
	public Collection<ReceptacleInformationVO> findMailbagDetailsForSBUldsFromCardit(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO","findMailbagDetailsForSBUldsFromCardit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAG_FOR_RESDIT_FROM_CARDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());

		log.exiting("MailTrackingDefaultsSqlDAO","findMailbagDetailsForSBUldsFromCardit");
		return query.getResultList(new ReceptanceInformationMapper());
	}

	/**
	 *
	 */
	public Collection<ContainerInformationVO> findULDDetailsForResdit(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findULDDetailsForResdit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ULD_DETAILS_FOR_RESDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getConsignmentNumber());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());

		log.exiting("MailTrackingDefaultsSqlDAO", "findULDDetailsForResdit");
		return query.getResultList(new ContainerInformationMapper());
	}

	/**
	 *
	 */
	public Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(
			ResditEventVO resditEventVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findULDDetailsForResditWithoutCardit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_ULD_DETAILS_FOR_RESDIT_WITHOUT_CARDIT);
		int idx = 0;
		query.setParameter(++idx, resditEventVO.getCompanyCode());
		query.setParameter(++idx, resditEventVO.getResditEventCode());
		query.setParameter(++idx, MailConstantsVO.FLAG_YES);
		query.setParameter(++idx, resditEventVO.getEventPort());
		query.setParameter(++idx, resditEventVO.getMessageSequenceNumber());

		if (isOracleDataSource())
		{
			query.append(" AND ROWNUM=1");
		}
		else{
			query.append(" LIMIT 1");
		}

		log.exiting("MailTrackingDefaultsSqlDAO", "findULDDetailsForResditWithoutCardit");
		return query.getResultList(new ContainerInformationMapper());
	}

	/**
	 * @author a-1936 Added By Karthick V This method is used to find out wether
	 *  the AWb associated with the DSN is already attached with some
	 *  other DSNs.This method returns the DSN other than the one given
	 *  in the filter if they are attached with that particular AWB ..
	 * @param containerDetailsVO
	 * @param dsnVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<DespatchDetailsVO> findOtherDSNsForSameAWB(DSNVO dsnVo,
																 ContainerDetailsVO containerDetailsVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findOtherDSNsForSameAWB");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_OTHERDSNS_FORSAMEAWB);
		int idx = 0;
		query.setParameter(++idx, containerDetailsVO.getCompanyCode());
		query.setParameter(++idx, dsnVo.getDocumentOwnerIdentifier());
		query.setParameter(++idx, dsnVo.getMasterDocumentNumber());
		query.setParameter(++idx, dsnVo.getDuplicateNumber());
		query.setParameter(++idx, dsnVo.getSequenceNumber());
		query.setParameter(++idx, containerDetailsVO.getCarrierId());
		query.setParameter(++idx, containerDetailsVO.getFlightNumber());
		query.setParameter(++idx, containerDetailsVO.getFlightSequenceNumber());
		query.setParameter(++idx, containerDetailsVO.getSegmentSerialNumber());
		query.setParameter(++idx, dsnVo.getMailSequenceNumber());
		query.setParameter(++idx, containerDetailsVO.getContainerNumber());
		Collection<DespatchDetailsVO> despatchDetails = query
				.getResultList(new OffloadDSNMapper());
		log.exiting("MailTrackingDefaultsSqlDAO", "findOtherDSNsForSameAWB");
		return despatchDetails;
	}
	/*public HashMap<Long, Collection<MailbagHistoryVO>> findCarditDetailsOfMailbagMap(String companyCode,
			long[] malseqnum) throws SystemException, PersistenceException {
		log.entering(MODULE, "findCarditDetailsOfMailbag");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_DETAILS_OF_MAILBAG);
		query.setParameter(1, companyCode);
		StringBuilder mailbagId = new StringBuilder("");
		for(long seqNum : malseqnum){
			mailbagId.append(seqNum).append(",");
		}
		String[] mailbags = mailbagId.toString().split(",");
		String joinQuery = getWhereClause(mailbags);
		query.append(" AND MALMST.MALSEQNUM IN ");
		query.append(joinQuery).append(") ORDER BY CDTRCP.RCPSRLNUM");
		return query.getResultList(new CarditDetailsOfMailbagsMapper()).get(0);
	}*/
	public HashMap<Long, Collection<MailbagHistoryVO>> findMailbagHistoriesMap(String companyCode,
																			   long[] malseqnum) throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailbagHistories;");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGHISTORIES);
		query.setParameter(++index, companyCode);
		StringBuilder mailbagId = new StringBuilder("");
		for(long seqNum : malseqnum){
			mailbagId.append(seqNum).append(",");
		}
		String[] mailbags = mailbagId.toString().split(",");
		String joinQuery = null;
		if(mailbags.length < 999) {
			joinQuery = getWhereClause(mailbags);
			query.append(" AND MST.MALSEQNUM IN ");
		} else {
			joinQuery = getEnhancedWhereClause(mailbags, 999, "MST.MALSEQNUM");
		}
		query.append(joinQuery).append(") ORDER BY HIS.UTCSCNDAT");
		return query
				.getResultList(new MailbagHistoryMultiMapper()).get(0);
	}
	public HashMap<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(String companyCode,
																				  long[] malseqnum) throws SystemException, PersistenceException {
		log.entering(MODULE, "findConsignmentDetailsForMailbag");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CONSIGNMENT_DETAILS_FOR_MAILBAG);
		query.setSensitivity(true);
		query.setParameter(++index, companyCode);
		StringBuilder mailbagId = new StringBuilder("");
		for(long seqNum : malseqnum){
			mailbagId.append(seqNum).append(",");
		}
		String[] mailbags = mailbagId.toString().split(",");
		String joinQuery = null;
		if(mailbags.length < 999) {
			joinQuery = getWhereClause(mailbags);
			query.append(" AND MALMST.MALSEQNUM IN ");
		} else {
			joinQuery = getEnhancedWhereClause(mailbags, 999, "MALMST.MALSEQNUM");
		}
		query.append(joinQuery).append(")");
		return query
				.getResultList(new ConsignmentsOfMailbagsMultiMapper()).get(0);
	}
	/**
	 * @author a-1936
	 * This  method is used to find the mailbags in the Container for the Manifest
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
			throws SystemException,PersistenceException{
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, FIND_MAILBAGS_IN_CONTAINER);
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<ContainerDetailsVO>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_MAILBAGS_IMPORT_MANIFEST_ULD);
			} else {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_MAILBAGS_IMPORT_MANIFEST_BULK);
			}
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
			qry.setParameter(++idx,
					MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
			qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ARRIVED);
			qry.setParameter(++idx, cont.getPol());
//			 qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ACCEPTED);
//			 qry
//			 .setParameter(++idx,
//					 MailConstantsVO.MAIL_STATUS_TRANSFERRED);
//			 qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ASSIGNED);
//			 qry.setParameter(++idx, MailConstantsVO.MAIL_STATUS_ARRIVED);
			qry.setParameter(++idx, cont.getCompanyCode());
			qry.setParameter(++idx, cont.getCarrierId());
			qry.setParameter(++idx, cont.getFlightNumber());
			qry.setParameter(++idx, cont.getFlightSequenceNumber());
			qry.setParameter(++idx, cont.getSegmentSerialNumber());
			qry.setParameter(++idx, cont.getPol());
			qry.setParameter(++idx, cont.getContainerNumber());
			List<ContainerDetailsVO> list = qry
					.getResultList(new AcceptedDSNsForManifestMultiMapper());
			if (list != null && list.size() > 0) {
				containerForReturn.add(list.get(0));
			}
		}
		return containerForReturn;
	}
	/**
	 * @author A-6371
	 * For fetching Mailonhand list details
	 */
	public Page<MailOnHandDetailsVO> findMailOnHandDetails(
			SearchContainerFilterVO searchContainerFilterVO,int pageNumber)
			throws SystemException, PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findMailOnHandDetails");
		PageableNativeQuery<MailOnHandDetailsVO> pgqry = null;
		Page<MailOnHandDetailsVO>  MailOnHandDetailsVOs = null;
		String baseQry="";
		String assignedUser=searchContainerFilterVO.getSearchMode();
		//Added by A-5945 for ICRD-96261
		StringBuilder carrierBuilder= new StringBuilder();
		String carrierValues="";
		if(searchContainerFilterVO.getPartnerCarriers()!=null){
			ArrayList<String> carriervalue =searchContainerFilterVO.getPartnerCarriers();


			for(String s: carriervalue){
				carrierBuilder.append("'");
				carrierBuilder.append(s);
				carrierBuilder.append("'");
				carrierBuilder.append(",");
			}
			int i =	 carrierBuilder.lastIndexOf(",");
			carrierBuilder.deleteCharAt(i);
			carrierValues=	carrierBuilder.toString();
			log.log(Log.FINE, " systemParameterMap ", carrierValues);
		}
		if(SEARCHMODE_FLT.equals(searchContainerFilterVO.getSearchMode()))
		{
			baseQry= getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLISTFLIGHT);
			//Added by A-5945 for ICRD-96261 starts
			if(searchContainerFilterVO.getPartnerCarriers()!=null ){
				StringBuilder queryBuilder = new StringBuilder(baseQry);
				queryBuilder.append("AND MST. FLTCARCOD IN (") ;
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry=queryBuilder.toString();



			}
			//Added by A-5945 ends
		}
		else if(SEARCHMODE_DEST.equals(assignedUser))
		{
			baseQry= getQueryManager().getNamedNativeQueryString(FIND_MAILONHANDLISTCARRIER);
			//Added by A-5945 for ICRD-96261 starts
			if(searchContainerFilterVO.getPartnerCarriers()!=null ){
				StringBuilder queryBuilder = new StringBuilder(baseQry);
				queryBuilder.append("AND MST. FLTCARCOD IN (") ;
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry=queryBuilder.toString();



			}

		}
		else
		{
			baseQry= getQueryManager().getNamedNativeQueryString(
					FIND_MAILONHANDLIST);
			//Added by A-5945 for ICRD-96261 starts
			StringBuilder queryBuilder = new StringBuilder(baseQry);

			if(searchContainerFilterVO.getPartnerCarriers()!=null ){
				queryBuilder.append("AND MST. FLTCARCOD IN (") ;
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry=queryBuilder.toString();



			}
			queryBuilder.append(")UNION ALL (");
			String subQuery=getQueryManager().getNamedNativeQueryString( FIND_MAILONHANDLIST_PARTTWO);
			queryBuilder.append(subQuery);
			baseQry=queryBuilder.toString();
			if(searchContainerFilterVO.getPartnerCarriers()!=null ){
				queryBuilder.append("AND MST. FLTCARCOD IN (") ;
				queryBuilder.append(carrierValues);
				queryBuilder.append(")");
				baseQry=queryBuilder.toString();



			}
		}
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.ASGPRT, RESULT_TABLE.DSTCOD,RESULT_TABLE.SUBCLSGRP) RANK FROM ((");
		rankQuery.append(baseQry);
		rankQuery.append("))GROUP BY CMPCOD,ASGPRT ,DSTCOD ,SUBCLSGRP, SUBSTR(CONNUM, 0,3), CONTYP  )RESULT_TABLE)");
		log.log(Log.INFO, "THE SEARCH MODE IS DESTINATION");
		pgqry = new MailOnHandListFilterQuery(rankQuery.toString(),searchContainerFilterVO,new MailOnHandListMultiMapper());//added by A-6371 for CR ICRD-21098
		MailOnHandDetailsVOs = pgqry.getPage(pageNumber);
		log.log(Log.INFO, "THE DESTINATION IS PRESENT");



		return MailOnHandDetailsVOs;
	}

	/**
	 *
	 */
	public Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO ) throws SystemException{
		log.entering("MailTrackingSQLDAO","findAuditHistoryVOs");
		int index=0;
		Query qry = getQueryManager().createNamedNativeQuery(MAILTRACKING_DEFAULTS_MAIL_AUDITHISTORY_DETAILS);
		qry.setParameter(++index, mailAuditHistoryFilterVO.getCompanyCode());
		qry.setParameter(++index, mailAuditHistoryFilterVO.getMailbagId());
		if(mailAuditHistoryFilterVO.getTransaction()!=null && !("").equals(mailAuditHistoryFilterVO.getTransaction())){
			qry.append(" AND MALAUD.ACTCOD = ?")	;
			qry.setParameter(++index, mailAuditHistoryFilterVO.getTransaction());
		}
		if(mailAuditHistoryFilterVO.getAuditableField()!=null && !("").equals(mailAuditHistoryFilterVO.getAuditableField() )){
			qry.append(" AND  HIS.HISFLDNAM = ?")	;
			qry.setParameter(++index, mailAuditHistoryFilterVO.getAuditableField());
		}
		qry.append(" ORDER BY MALAUD.MALIDR , MALAUD.SERNUM");
		return qry.getResultList(new MailBagAuditHistoryMapper());
	}
	/**
	 *
	 */
	public HashMap<String,String> findAuditTransactionCodes(Collection<String> entities, boolean isForHistory,
															String companyCode)
			throws SystemException{
		HashMap<String,String> txnCodeMap = null;
		log.entering("MailTrackingSQLDAO","findAuditTransactionCodes");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(MAILBAG_AUDIT_FINDAUDITTRANSACTIONCODES);
		qry.setParameter(++index, companyCode);
		if(isForHistory){
			qry.append(" AND CFG.TXNHISFLG = ? ");
			qry.setParameter(++index, MailConstantsVO.FLAG_YES);
		}
		if(entities != null && entities.size() > 0){
			qry.append(" AND CFG.ETYNAM IN ( ");
			int count = 0;
			int numberOfEntity = entities.size();
			for(String entity:entities){
				qry.append(" ? ");
				qry.setParameter(++index,entity);
				if(count < numberOfEntity-1){
					qry.append(", ");
				}
				count++;
			}
			qry.append(")");
		}
		ArrayList<String> txnCodeDetails = (ArrayList<String>)qry.getResultList(new MailbagAuditTransactionCodeMapper());
		if(txnCodeDetails != null && txnCodeDetails.size() > 0){
			txnCodeMap = new HashMap<String,String>();
			for(String txnCodeDtl:txnCodeDetails){
				String[] txnCodDetailArray = txnCodeDtl.split("~");
				txnCodeMap.put(txnCodDetailArray[0],txnCodDetailArray[1]);
			}
		}
		return txnCodeMap;
	}
	/**
	 * @author a-5526 This method Checks whether the container is already
	 *         assigned to a flight/destn from the current airport
	 * @param companyCode
	 * @param containerNumber
	 * @param pol
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerAssignmentVO findContainerAssignmentForArrival(String companyCode,
																   String containerNumber, String pol) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findContainerAssignmentForArrival");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINER_ASSIGNMENT);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, pol);
		containerAssignMentVO = qry
				.getSingleResult(new ContainerAssignmentMapper());
		return containerAssignMentVO;
	}

	/**
	 * @author A-7871
	 * for ICRD-257316
	 * Used to get mailbag count
	 * @param containerDetailsVO
	 * @throws SystemException
	 * @throws BusinessDelegateException
	 */
	public int findMailbagcountInContainer(ContainerVO containerVO) throws SystemException {
		log.entering(MODULE, "findMailbagcountInContainer");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGCOUNTINCONTAINER);
		qry.setParameter(++index, containerVO.getCompanyCode());
		qry.setParameter(++index, containerVO.getCarrierId());
		qry.setParameter(++index, containerVO.getFlightNumber());
		qry.setParameter(++index, containerVO.getFlightSequenceNumber());
		qry.setParameter(++index, containerVO.getSegmentSerialNumber());
		qry.setParameter(++index, containerVO.getContainerNumber());
		//	Mapper<Integer> intMapper = getIntMapper("mailbagcount");
		return qry.getSingleResult(getIntMapper("MAILBAGCOUNT"));
	}



	/** Added as part of CRQ ICRD-204806
	 * @author A-5526
	 * @param fileUploadFilterVO
	 * @return
	 */
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException, PersistenceException
	{
		log.entering(MODULE, "processMailOperationFromFile");
		String processStatus = null;
		if (fileUploadFilterVO != null)
		{
			int index = 0;


			Procedure procedure = getQueryManager().createNamedNativeProcedure(PROCESS_MAIL_OPERATIONS_FROM_FILE);
			index++; procedure.setParameter(index, fileUploadFilterVO.getCompanyCode());
			index++; procedure.setParameter(index, fileUploadFilterVO.getFileType());
			index++; procedure.setParameter(index, "F");
			index++; procedure.setParameter(index,fileUploadFilterVO.getProcessIdentifier());
			index++; procedure.setParameter(index, MailConstantsVO.FLAG_NO); //modified by A-7371 as part of ICRD-249533
			index++; procedure.setOutParameter(index, SqlType.STRING);
			index++; procedure.setOutParameter(index, SqlType.STRING);
			procedure.execute();
			processStatus = (String)procedure.getParameter(index);
		}
		log.log(2, new Object[] {
				"ProcessStatus-->", processStatus });

		return processStatus;
	}
	/** Added as part of CRQ ICRD-204806
	 * fetchDataForOfflineUpload
	 *
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailUploadVO> fetchDataForOfflineUpload(String companyCode, String fileType)
			throws SystemException, PersistenceException
	{
		log.entering(MODULE, "fetchDataForOfflineUpload");
		Collection<MailUploadVO> mailUploadVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FETCH_DATA_FOR_OFFLOAD_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, fileType);

		mailUploadVOs = query
				.getResultList(new OfflineMailUploadMapper
						());
		log.log(Log.INFO, "MailUploadVO is from dao*****", mailUploadVOs);
		return mailUploadVOs;

	}
	/** Added as part of CRQ ICRD-204806
	 * removeDataFromTempTable
	 *
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException, PersistenceException
	{
		log.entering(MODULE, "removeDataFromTempTable");

		String processStatus = null;
		if (fileUploadFilterVO != null)
		{
			int index = 0;


			Procedure procedure = getQueryManager().createNamedNativeProcedure(REMOVE_DATA_FROM_TEMPTABLE);
			index++; procedure.setParameter(index, fileUploadFilterVO.getCompanyCode());
			index++; procedure.setParameter(index, fileUploadFilterVO.getFileType());
			index++; procedure.setParameter(index, "F");
			index++; procedure.setParameter(index,fileUploadFilterVO.getProcessIdentifier());
			index++; procedure.setParameter(index, MailConstantsVO.FLAG_YES);
			index++; procedure.setOutParameter(index, SqlType.STRING);
			index++; procedure.setOutParameter(index, SqlType.STRING);
			procedure.execute();
			processStatus = (String)procedure.getParameter(index);
		}
		log.log(2, new Object[] {
				"ProcessStatus-->", processStatus });


	}
	/**
	 * @author a-7794 This method is used to find ContainerAuditDetails
	 * @param mailAuditFilterVO
	 * @return Collection<PartnerCarrierVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 * ICRD-229934
	 */
	public Collection<AuditDetailsVO> findCONAuditDetails(
			MailAuditFilterVO mailAuditFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findCONAuditDetails");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONAUDIT);
		qry.setParameter(++index, mailAuditFilterVO.getCompanyCode());
		qry.setParameter(++index, mailAuditFilterVO.getTxnFromDate());
		/* .toGMTDate()); Commented by A-4501 to solve ICRD-23202 */
		qry.setParameter(++index, mailAuditFilterVO.getTxnToDate());
		/* .toGMTDate()); Commented by A-4501 to solve ICRD-23202 */

		if (mailAuditFilterVO.getCarrierId() != 0) {
			qry.append("AND FLTCARIDR = ?");
			qry.setParameter(++index, mailAuditFilterVO.getCarrierId());
		}
		if (mailAuditFilterVO.getFlightNumber() != null
				&& mailAuditFilterVO.getFlightNumber().trim().length() > 0) {
			qry.append("AND FLTNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getFlightNumber());
			qry.append("AND FLTSEQNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO
					.getFlightSequenceNumber());
			qry.append("AND LEGSERNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getLegSerialNumber());
		}
		if (mailAuditFilterVO.getAssignPort() != null
				&& mailAuditFilterVO.getAssignPort().trim().length() > 0) {
			qry.append("AND ASGPRT = ?");
			qry.setParameter(++index, mailAuditFilterVO.getAssignPort());
		}
		if (mailAuditFilterVO.getContainerNo() != null
				&& mailAuditFilterVO.getContainerNo().trim().length() > 0) {
			qry.append("AND CONNUM = ?");
			qry.setParameter(++index, mailAuditFilterVO.getContainerNo());
		}

		return qry.getResultList(new MailAuditMapper());

	}

	/**
	 *
	 *	 Method	:	findMailbagIdForMailTag(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 	: a-6245 on 22-Jun-2017
	 * 	Used for 	:ICRD-205027
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 */
	public MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO)
			throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailbagIdForMailTag");
		int idx = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGID_FOR_MAIL_TAG);
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getOoe());
		query.setParameter(++idx, mailbagVO.getDoe());
		query.setParameter(++idx, mailbagVO.getMailCategoryCode());
		query.setParameter(++idx, mailbagVO.getMailSubclass());
		query.setParameter(++idx, mailbagVO.getYear());
		query.setParameter(++idx, mailbagVO.getDespatchSerialNumber());
		query.setParameter(++idx, mailbagVO.getReceptacleSerialNumber());
		query.setParameter(++idx, mailbagVO.getHighestNumberedReceptacle());
		query.setParameter(++idx, mailbagVO.getRegisteredOrInsuredIndicator());
		query.setParameter(++idx, mailbagVO.getWeight().getDisplayValue());
		return query.getSingleResult(new MailTagIDMapper());
	}

	/**
	 *
	 * 	Method		:	MailTrackingDefaultsSqlDAO.findOfficeOfExchangeForPA
	 *	Added by 	:	a-6245 on 20-Jul-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param paCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 *	Return type	: 	HashMap<String,String>
	 */
	public HashMap<String,String> findOfficeOfExchangeForPA(String companyCode,
															String paCode)
			throws SystemException, PersistenceException{
		log.entering(MODULE, "findOfficeOfExchangeForPA");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_EXCHANGEOFFICE_FOR_PA);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, paCode);
		return query
				.getResultList(new ExchangeOfficeMultiMapper()).get(0);
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailBookingAWBs(com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingFlightFilterVO)
	 *	Added by 			: a-7779 on 24-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	/*public Collection<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO) throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailBookingAWBs");
		String query = getQueryManager().getNamedNativeQueryString(FIND_MAILBOOKING_AWBS);
		PageableNativeQuery<MailBookingDetailVO> pgqry = new MailBookingFilterQuery(mailBookingFilterVO.getPageSize(),mailBookingFilterVO.getRowCount(),query,
				new MailBookingMapper(), mailBookingFilterVO);
		return pgqry.getPage(mailBookingFilterVO.getPageNumber());
	}*/
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#fetchBookedFlightDetails(java.lang.String, java.lang.String, java.lang.String)
	 *	Added by 			: a-7779 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	/*public Collection<MailBookingDetailVO> fetchBookedFlightDetails(
			String companyCode, String shipmentPrefix,
			String masterDocumentNumber)throws SystemException, PersistenceException{
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FETCH_BOOKED_FLIGHTS_DETAILS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, shipmentPrefix);
		query.setParameter(++index, masterDocumentNumber);
		return query.getResultList((new MailBookedFlightDetailsMapper()));
	}*/
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findAwbAtachedMailbagDetails(java.lang.String, int, java.lang.String)
	 *	Added by 			: a-7779 on 31-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param ownerId
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 * @throws SystemException
	 */
	public ScannedMailDetailsVO findAwbAtachedMailbagDetails(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{
		int index = 0;

		String flightNumber=mailFlightSummaryVO.getFlightNumber();
		long flightSeqNumber=mailFlightSummaryVO.getFlightSequenceNumber();
		String toFlightNumber=mailFlightSummaryVO.getToFlightNumber();
		long toflightSeqNumber= mailFlightSummaryVO.getToFlightSequenceNumber();
		Query query1 = null;
		Query query2 = null;

		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		query1 = getQueryManager().createNamedNativeQuery(
				FIND_AWB_ATTACHED_MAIL_BAG_DETAILS);

		query2 = getQueryManager().createNamedNativeQuery(
				FIND_AWB_ATTACHED_MAIL_BAG_DETAILS);

		if(shipmentSummaryVO !=null && flightNumber != null && flightNumber.trim().length() >0 && !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query1.append(" AND FLTDTL.FLTCARIDR = ? ");
			query1.append(" AND FLTDTL.FLTNUM = ? ");
			query1.append(" AND FLTDTL.FLTSEQNUM =? ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, flightNumber);
			query1.setParameter(++index, flightSeqNumber);
		}else if(shipmentSummaryVO !=null && toFlightNumber!=null && toFlightNumber.trim().length()>0 && MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query1.append(" AND FLTDTL.FLTCARIDR = ? ");
			query1.append(" AND FLTDTL.FLTNUM = ? ");
			query1.append(" AND FLTDTL.FLTSEQNUM =? ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, toFlightNumber);
			query1.setParameter(++index, toflightSeqNumber);
		}
		query1.append(" WHERE MALMST.CMPCOD = ? ");
		query1.setParameter(++index, mailFlightSummaryVO.getCompanyCode());

		query1.append(" AND BKGMST.ISSSPLBKG ='N' ");

		if(shipmentSummaryVO != null){
			query1.append(" AND MALMST.DOCOWRIDR=? ");
			query1.append(" AND MALMST.MSTDOCNUM=?"  );
			query1.append(" AND MALMST.DUPNUM =? ");
			query1.append(" AND MALMST.SEQNUM = ? ");
			query1.setParameter(++index, shipmentSummaryVO.getOwnerId());
			query1.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
			query1.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());
			query1.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		}else if(mailFlightSummaryVO.getFlightNumber() != null && mailFlightSummaryVO.getFlightNumber().trim().length() >0){
			query1.append(" AND MALMST.FLTCARIDR = ? ");
			query1.append(" AND MALMST.FLTNUM = ? ");
			query1.append(" AND MALMST.FLTSEQNUM =? ");
			query1.append(" AND EXISTS (SELECT 1 FROM OPRSEGSHP ");
			query1.append(" WHERE CMPCOD = MALMST.CMPCOD ");
			query1.append(" AND FLTCARIDR = MALMST.FLTCARIDR ");
			query1.append(" AND FLTNUM = MALMST.FLTNUM ");
			query1.append(" AND FLTSEQNUM = MALMST.FLTSEQNUM ");
			query1.append(" AND SEGSERNUM = MALMST.SEGSERNUM ");
			query1.append(" AND OFLPCS > 0) ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, flightNumber);
			query1.setParameter(++index, flightSeqNumber);
		}


/*
		if(flightNumber!=null && flightNumber.trim().length()>0 && !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){ //modified by A-7371 as part of ICRD-264439
			query.append(" AND FLTDTL.FLTNUM = ? AND FLTDTL.FLTSEQNUM = ? ");
			query.setParameter(++index, flightNumber);
			query.setParameter(++index, flightSeqNumber);
		}else if(toFlightNumber!=null && toFlightNumber.trim().length()>0 && MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query.append(" AND FLTDTL.FLTNUM = ? AND FLTDTL.FLTSEQNUM = ? ");
			query.setParameter(++index, toFlightNumber);
			query.setParameter(++index, toflightSeqNumber);
		}*/


		query1.append(" union all ");


		if(shipmentSummaryVO !=null && flightNumber != null && flightNumber.trim().length() >0 && !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query2.append(" AND FLTDTL.FLTCARIDR = ? ");
			query2.append(" AND FLTDTL.FLTNUM = ? ");
			query2.append(" AND FLTDTL.FLTSEQNUM =? ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, flightNumber);
			query1.setParameter(++index, flightSeqNumber);
		}else if(shipmentSummaryVO !=null && toFlightNumber!=null && toFlightNumber.trim().length()>0 && MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query2.append(" AND FLTDTL.FLTCARIDR = ? ");
			query2.append(" AND FLTDTL.FLTNUM = ? ");
			query2.append(" AND FLTDTL.FLTSEQNUM =? ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, toFlightNumber);
			query1.setParameter(++index, toflightSeqNumber);
		}
		query2.append(" WHERE MALMST.CMPCOD = ? ");
		query1.setParameter(++index, mailFlightSummaryVO.getCompanyCode());

		query2.append(" AND BKGMST.ISSSPLBKG ='Y' ");

		if(shipmentSummaryVO !=null && flightNumber != null && flightNumber.trim().length() >0 && !MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){

			if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailFlightSummaryVO.getEventCode())){
				query2.append(" AND MALMST.FLTCARIDR = ? ");
				query2.append(" AND MALMST.FLTNUM = ? ");
				query2.append(" AND MALMST.FLTSEQNUM =? ");
			}else{
				query2.append(" AND FLTDTL.FLTCARIDR = ? ");
				query2.append(" AND FLTDTL.FLTNUM = ? ");
				query2.append(" AND FLTDTL.FLTSEQNUM =? ");
			}

			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, flightNumber);
			query1.setParameter(++index, flightSeqNumber);
		}else if(shipmentSummaryVO !=null && toFlightNumber!=null && toFlightNumber.trim().length()>0 && MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailFlightSummaryVO.getEventCode())){
			query2.append(" AND FLTDTL.FLTCARIDR = ? ");
			query2.append(" AND FLTDTL.FLTNUM = ? ");
			query2.append(" AND FLTDTL.FLTSEQNUM =? ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, toFlightNumber);
			query1.setParameter(++index, toflightSeqNumber);
		}


		if(shipmentSummaryVO != null){
			query2.append(" AND MALMST.DOCOWRIDR=? ");
			query2.append(" AND MALMST.MSTDOCNUM=?"  );
			query2.append(" AND MALMST.DUPNUM =? ");
			query2.append(" AND MALMST.SEQNUM = ? ");
			query1.setParameter(++index, shipmentSummaryVO.getOwnerId());
			query1.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
			query1.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());
			query1.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		}else if(mailFlightSummaryVO.getFlightNumber() != null && mailFlightSummaryVO.getFlightNumber().trim().length() >0){
			query2.append(" AND MALMST.FLTCARIDR = ? ");
			query2.append(" AND MALMST.FLTNUM = ? ");
			query2.append(" AND MALMST.FLTSEQNUM =? ");
			query2.append(" AND EXISTS (SELECT 1 FROM OPRSEGSHP ");
			query2.append(" WHERE CMPCOD = MALMST.CMPCOD ");
			query2.append(" AND FLTCARIDR = MALMST.FLTCARIDR ");
			query2.append(" AND FLTNUM = MALMST.FLTNUM ");
			query2.append(" AND FLTSEQNUM = MALMST.FLTSEQNUM ");
			query2.append(" AND SEGSERNUM = MALMST.SEGSERNUM ");
			query2.append(" AND OFLPCS > 0) ");
			query1.setParameter(++index, mailFlightSummaryVO.getCarrierId());
			query1.setParameter(++index, flightNumber);
			query1.setParameter(++index, flightSeqNumber);
		}

		query1.combine(query2);
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs =
				query1.getResultList(new AWBAttachedMailbagDetailsMapper());
		//return query.getResultList(new AWBAttachedMailbagDetailsMapper()).get(0);
		if(scannedMailDetailsVOs != null && !scannedMailDetailsVOs.isEmpty()){
			scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs).get(0);
		}
		return scannedMailDetailsVO;
	}
	/**
	 * @author A-7371
	 * for ICRD-264253
	 * @throws SystemException
	 */
	public List<MailbagVO> findMailBagsforReassign(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{
		log.entering(MODULE, "findMailBagsforReassign");
		int index = 0;
		Query query = null;
		query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FORREASSIGN);
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		List<MailbagVO>	mailbag	= query.getResultList(new AWBAttachedReassignMailDetailsMapper());
		return mailbag;

	};

	/**
	 *
	 * @author A-7371
	 * for ICRD-264253
	 *
	 */
	private static class AWBAttachedReassignMailDetailsMapper implements MultiMapper<MailbagVO> {

		public List<MailbagVO> map(ResultSet rs) throws SQLException {
			List<MailbagVO> mailbags=new ArrayList<MailbagVO>();
			while(rs.next()){
				MailbagVO mailbagVO=new MailbagVO();
				mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
				mailbagVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				mailbags.add(mailbagVO);
			}
			return mailbags;
		}


	}
	/**
	 *
	 */
	public String findMailboxId(MailResditVO resditVO) throws
			SystemException, PersistenceException {
		log.entering(MODULE, "findMailboxId");
		String mailbox = "";
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBOX);
		int indx = 0;
		query.setParameter(++indx, resditVO.getCompanyCode());
		query.setParameter(++indx, resditVO.getMailId());
		mailbox = query.getSingleResult(getStringMapper("MALBOX"));
		return mailbox;

	}


	/**
	 *
	 *	Added by 			: a-8061 on 30-Apr-2018
	 * 	Used for 	: ICRD-292562
	 *	Parameters	:	@param shipmentSummaryVO
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@return
	 * @throws SystemException
	 */
	public int findAwbPartialOflPcs(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{
		int index = 0;
		Query query = null;
		int oflpcs=0;
		//ICRD-292562
		query = getQueryManager().createNamedNativeQuery(
				FIND_AWB_PARTIAL_OFL_PCS);

		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index,shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index,shipmentSummaryVO.getDuplicateNumber());
		query.setParameter(++index,shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index,mailFlightSummaryVO.getFlightNumber());
		query.setParameter(++index,mailFlightSummaryVO.getFlightSequenceNumber());

		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index,shipmentSummaryVO.getOwnerId());
		query.setParameter(++index,shipmentSummaryVO.getDuplicateNumber());
		query.setParameter(++index,shipmentSummaryVO.getSequenceNumber());


		oflpcs=query.getSingleResult(getIntMapper("OFLPCS"));

		return oflpcs;

	}
	/**
	 *
	 *	Overriding Method	:@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO
	 *						#findAgentCodeForPA(java.lang.String, java.lang.String)
	 *	Added by 			: U-1267 on Nov 1, 2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param officeOfExchange
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findAgentCodeForPA(String companyCode,
									 String officeOfExchange) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findAgentCodeForPA");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_AGENT_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, officeOfExchange);
		log.exiting("MailTrackingDefaultsSqlDAO", "findAgentCodeForPA");
		return query.getSingleResult(getStringMapper("PARVAL"));
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO
	 *							#findMailbagVOsForDsnVOs(java.util.Collection)
	 *	Added by 			: U-1267 on 08-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param dsnVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<MailbagVO> findMailbagVOsForDsnVOs(
			ContainerDetailsVO containerDetailsVO) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagVOsForDsnVOs");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FOR_DSN);
		if(containerDetailsVO.getFromScreen()!=null && containerDetailsVO.getFromScreen().equals( "Outbound")) {
			query.append("AND ULDSEG.CONNUM = ?");
		}
		else {
			query.append("AND ULDSEG.ULDNUM = ?");
		}
		int index = 0;
		if(!MailConstantsVO.FROMDETACHAWB.equals(containerDetailsVO.getFromDetachAWB())) {
			Collection<DSNVO>dsnVOs=containerDetailsVO.getDsnVOs();
			boolean first = true;
			if (dsnVOs != null && dsnVOs.size() > 0) {
				query.append("AND MST.DSNIDR IN ( ?");
				for (DSNVO dsnVO : dsnVOs) {
					StringBuilder sb = new StringBuilder();
					if (first) {
						first = false;
						query.setParameter(++index, containerDetailsVO.getCompanyCode());
						query.setParameter(++index, containerDetailsVO.getFlightNumber());
						query.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
						query.setParameter(++index, containerDetailsVO.getCarrierId());
						query.setParameter(++index,containerDetailsVO.getContainerNumber());
						sb.append(dsnVO.getOriginExchangeOffice())
								.append(dsnVO.getDestinationExchangeOffice())
								.append(dsnVO.getMailCategoryCode())
								.append(dsnVO.getMailSubclass())
								.append(dsnVO.getYear()).append(dsnVO.getDsn());
						query.setParameter(++index, sb.toString());
					} else {
						query.append(",? ");
						sb.append(dsnVO.getOriginExchangeOffice())
								.append(dsnVO.getDestinationExchangeOffice())
								.append(dsnVO.getMailCategoryCode())
								.append(dsnVO.getMailSubclass())
								.append(dsnVO.getYear()).append(dsnVO.getDsn());
						query.setParameter(++index, sb.toString());
					}
				}
				query.append(" ) ");
			}
		}else {
			Collection<MailbagVO>mailDetails=containerDetailsVO.getMailDetails();
			boolean first = true;
			if (mailDetails != null && mailDetails.size() > 0) {
				query.append("AND MST.MALSEQNUM IN ( ?");
				for (MailbagVO mailDetail : mailDetails) {
					StringBuilder sb = new StringBuilder();
					if (first) {
						first = false;
						query.setParameter(++index, containerDetailsVO.getCompanyCode());
						query.setParameter(++index, containerDetailsVO.getFlightNumber());
						query.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
						query.setParameter(++index, containerDetailsVO.getCarrierId());
						query.setParameter(++index,containerDetailsVO.getContainerNumber());
						query.setParameter(++index, mailDetail.getMailSequenceNumber());
					}
					else {
						query.append(",? ");
						sb.append(mailDetail.getOoe())
								.append(mailDetail.getDoe())
								.append(mailDetail.getMailCategoryCode())
								.append(mailDetail.getMailSubclass())
								.append(mailDetail.getYear()).append(mailDetail.getMailSequenceNumber());
						query.setParameter(++index,mailDetail.getMailSequenceNumber() );
					}
				}
				query.append(" ) ");
			}
		}
		return query.getResultList(new MailbagMapper());

	}
	/**
	 *
	 * 	Method		:	MailTrackingDefaultsSqlDAO.findMailTagDetails
	 *	Added by 	:	a-7871 on
	 * 	Used for 	:	ICRD-108366
	 *	Parameters	:	@param mailbagVOs
	 *	Parameters	:	@return mailVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 *	Return type	: 	ArrayList<MailbagVO>
	 */
	@Override
	public ArrayList<MailbagVO> findMailTagDetails(
			ArrayList<MailbagVO> mailbagVOs) throws SystemException,
			PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "findMailTagDetails");
		ArrayList<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setMailbagId(mailbagVO.getMailbagId());
			LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			mailbagVO.setCurrentDateStr(date.toDisplayFormat("ddMMyyyy"));
			log.log(Log.FINE, "mailbagVO.setCurrentDateStr() :", mailbagVO.getCurrentDateStr());
			Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILTAG_DETAILS);
			int idx = 0;
			MailbagVO mailVO = new MailbagVO();
			qry.setParameter(++idx, mailbagVO.getCompanyCode());
			qry.setParameter(++idx, mailbagVO.getMailbagId());
			mailVO = qry.getSingleResult(new MailTagDetailsMapper());
			if(mailVO !=null)
			{
				mailVOs.add(mailVO);
			}
			else//Added as part of ICRD-274812
			{
				mailVOs.add(mailbagVO);
			}
		}


		log.exiting("MailTrackingDefaultsSqlDAO", "findMailTagDetails");
		return mailVOs;
	}

	/**
	 * @author A-8061
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */

	public Collection<MailbagHistoryVO> findMailbagResditEvents(
			String companyCode, String mailbagId) throws SystemException,
			PersistenceException {

		log.entering(MODULE, "findMailbagResditEvents");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGEVENTS);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailbagId);
		return query.getResultList(new MailbagResditEventMapper());

	}


	/**
	 * @author A-8061
	 * @param carditEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findGrandTotals");

		String grandTotal="";
		String[] grandTotals = {"0","0"};
		String  baseQuery = getQueryManager().getNamedNativeQueryString(
				FIND_GRAND_TOTAL);

		Query query = new GrandTotalFilterQuery(
				carditEnquiryFilterVO,baseQuery);
		grandTotal = query.getSingleResult(getStringMapper("GRDTOT"));
		if(grandTotal!=null){
			if(grandTotal.split("-")[0]!=null){
				grandTotals[0]=grandTotal.split("-")[0];
			}
			if((grandTotal.split("-").length>1) && (grandTotal.split("-")[1]!=null)){
				grandTotals[1]=grandTotal.split("-")[1];
			}
		}
		return grandTotals;
	}

	/**
	 * @author A-6986
	 * @param mailServiceLevelVO
	 * @return
	 */
	public String findMailServiceLevelForIntPA(MailServiceLevelVO mailServiceLevelVO) throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailServiceLevelForInternational");
		int index = 0;
		Query  query = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());

		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? "
				+ "AND (MALCLS = ? AND MALSUBCLS = '-') "
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCTG = ? AND MALSUBCLS = ? AND MALCLS = ?)");//Modified For ICRD-271943
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD  = ? AND MALCTG    = '-' AND MALCLS  = ? AND MALSUBCLS = ? "
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
	public String findMailServiceLevelForDomPA(MailServiceLevelVO mailServiceLevelVO) throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailServiceLevelForDomestic");
		int index = 0;
		Query  query = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_SERVICE_LEVEL);
		query.setParameter(++index, mailServiceLevelVO.getCompanyCode());
		query.setParameter(++index, mailServiceLevelVO.getPoaCode());
		query.setParameter(++index, mailServiceLevelVO.getMailCategory());
		query.setParameter(++index, mailServiceLevelVO.getMailClass());
		query.setParameter(++index, mailServiceLevelVO.getMailSubClass());
		query.append(" UNION ALL ");
		query.append("SELECT MALSRVLVL FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? "
				+ "AND ((MALCTG    = ? AND MALSUBCLS = '-') OR (MALCTG    = '-' AND MALSUBCLS = ?))"
				+ "AND NOT EXISTS (SELECT 1 FROM MALSRVLVLMAP WHERE POACOD = ? AND MALCLS = ? AND MALSUBCLS = ? AND MALCTG    = ?)");//Modified For ICRD-271943
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
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listPostalCalendarDetails(com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO)
	 *	Added by 			: A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param uSPSPostalCalendarFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws SystemException, PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "listPostalCalendarDetails");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_POSTAL_CAL_DETAILS);
		if(uSPSPostalCalendarFilterVO.getCalValidFrom()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))){
			if(USPSPostalCalendarFilterVO.FLAG_NO.equals(uSPSPostalCalendarFilterVO.getListFlag())){
				qry=qry.append(" AND trunc(PRDFRM) <=?");
			}
			else{
				qry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(PRDFRM),'YYYYMMDD')) >= ? ");
			}
		}
		if(uSPSPostalCalendarFilterVO.getCalValidTo()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))){
			if(USPSPostalCalendarFilterVO.FLAG_NO.equals(uSPSPostalCalendarFilterVO.getListFlag())){
				qry=qry.append(" AND trunc(PRDTOO) >=?");
			}
			else{
				qry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(PRDTOO),'YYYYMMDD')) <= ? ");
			}
		}
		int idx = 0;
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getFilterCalender());
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCalPacode());
		if(uSPSPostalCalendarFilterVO.getCalValidFrom()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))){
			qry.setParameter(++idx, Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidFrom().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		if(uSPSPostalCalendarFilterVO.getCalValidTo()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))){
			qry.setParameter(++idx, Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidTo().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		qry.append("ORDER BY PRDFRM, PRDTOO");
		log.exiting("MailTrackingDefaultsSqlDAO", "listPostalCalendarDetails");

		log.log(Log.FINE, "USPS calender query ", qry);
		Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs = qry
				.getResultList(new USPSPostalCalendarMapper());
		return uSPSPostalCalendarVOs;
	}
	/**
	 * @author A-6986
	 * @param mailServiceLevelVO
	 * @return
	 */
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,int pageNumber)
			throws SystemException, PersistenceException{
		log.entering(MODULE, "findMailHandoverDetails");
		StringBuffer masterQuery = new StringBuffer();
		int pagesize= mailHandoverFilterVO.getDefaultPageSize();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_MAIL_HANDOVER_DETAILS);
		masterQuery.append(queryString);
		PageableNativeQuery<MailHandoverVO> pgNativeQuery =
				new PageableNativeQuery<MailHandoverVO>(pagesize,0, masterQuery.toString(),
						new MailHandoverMapper());
		int index = 0;
		pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getCompanyCode());
		if (mailHandoverFilterVO.getGpaCode() != null
				&& !("").equals(mailHandoverFilterVO.getGpaCode())) {
			pgNativeQuery.append(" AND GPACOD=?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getGpaCode());
		}
		if (mailHandoverFilterVO.getAirportCode() != null
				&& !("").equals(mailHandoverFilterVO.getAirportCode())) {
			pgNativeQuery.append(" AND ARPCOD = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getAirportCode());
		}
		if(mailHandoverFilterVO.getMailClass() != null
				&& !("").equals(mailHandoverFilterVO.getMailClass())){
			pgNativeQuery.append(" AND MALCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailClass());
		}
		if(mailHandoverFilterVO.getMailSubClass() != null
				&& !("").equals(mailHandoverFilterVO.getMailSubClass())){
			pgNativeQuery.append(" AND MALSUBCLS = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getMailSubClass());
		}
		if(mailHandoverFilterVO.getExchangeOffice() != null
				&& !("").equals(mailHandoverFilterVO.getExchangeOffice())){
			pgNativeQuery.append(" AND EXGOFC = ?");
			pgNativeQuery.setParameter(++index, mailHandoverFilterVO.getExchangeOffice());
		}
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(pageNumber);


	}


	/**
	 * @author A-7371
	 */
	public Timestamp fetchSegmentSTA(MailbagVO mailbagVO)throws SystemException, PersistenceException {
		Query  query = getQueryManager().createNamedNativeQuery(
				FIND_FLIGHT_STA);
		int indx = 0;
		query.setParameter(++indx, mailbagVO.getCompanyCode());
		query.setParameter(++indx, mailbagVO.getFlightNumber());
		query.setParameter(++indx, mailbagVO.getCarrierId());
		query.setParameter(++indx, mailbagVO.getFlightSequenceNumber());
		query.setParameter(++indx, mailbagVO.getLegSerialNumber());
		Timestamp staDate = query.getSingleResult(getTimestampMapper("STA"));
		return staDate;
	}
	/**
	 * @author A-7371
	 */
	public String fetchHandlingConfiguration(MailbagVO mailbagVO)throws SystemException, PersistenceException {
		Query  query = getQueryManager().createNamedNativeQuery(
				FIND_HANDOVERTIME);
		int indx = 0;
		query.setParameter(++indx, mailbagVO.getCompanyCode());
		query.setParameter(++indx, mailbagVO.getScannedPort());
		query.setParameter(++indx, mailbagVO.getPaCode());
		if(mailbagVO.getConsignmentDate() != null ){
			query.append(QUERY_VALIDITY_CHECK);
			query.setParameter(++indx, mailbagVO.getConsignmentDate().toCalendar());
		}
		String handOverTime = query.getSingleResult(getStringMapper("HNDTIM"));
		return handOverTime;
	}

	/**
	 * @author A-7371
	 */
	public String fetchRDTOffset(MailbagVO mailbagVO,String paCodDom)throws SystemException, PersistenceException{
		Query  query = getQueryManager().createNamedNativeQuery(
				FIND_RDTOFFSET);
		int indx = 0;
		query.setParameter(++indx, mailbagVO.getCompanyCode());
		query.setParameter(++indx, mailbagVO.getScannedPort());
		if(paCodDom.equals(mailbagVO.getPaCode())){
			query.setParameter(++indx, MailConstantsVO.MALTYP_DOMESTIC);
			if(mailbagVO.getMailServiceLevel()!=null && mailbagVO.getMailServiceLevel().trim().length()>0){
				query.append(" AND MALSRVLVL=? ");
				query.setParameter(++indx, mailbagVO.getMailServiceLevel());
			}
		}else{
			query.setParameter(++indx, MailConstantsVO.MALTYP_INTERNATIONAL);
			query.append(" AND GPACOD  =? ");
			query.setParameter(++indx, mailbagVO.getPaCode());


		}
		if(mailbagVO.getConsignmentDate() != null ){
			query.append(QUERY_VALIDITY_CHECK);
			query.setParameter(++indx, mailbagVO.getConsignmentDate().toCalendar());
		}
		String rdtOffset = query
				.getSingleResult(new MailRDTOffsetMapper());

		return rdtOffset;
	}

	private static class MailRDTOffsetMapper implements Mapper<String> {

		public String map(ResultSet rs) throws SQLException {
			int rdtOffsetDay=(rs.getInt("RDTDAY"));
			int rdtOffsetTime=(rs.getInt("RDTOFT"));
			String rdtOffset=new StringBuilder().append(String.valueOf(rdtOffsetDay)).append("-")
					.append(String.valueOf(rdtOffsetTime)).toString();
			return rdtOffset;
		}

	}
	/**
	 * @author A-6986
	 * @param contractFilterVO
	 * @param pageNumber
	 * @return
	 */
	public Collection<GPAContractVO> listContractdetails(GPAContractFilterVO contractFilterVO)
			throws SystemException, PersistenceException{

		log.entering(MODULE, "listContractdetails");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTRACT_DETAILS);

		int index = 0;

		qry.setParameter(++index, contractFilterVO.getCompanyCode());
		qry.setParameter(++index, contractFilterVO.getPaCode());

		if(contractFilterVO.getOrigin() != null && contractFilterVO.getOrigin().trim().length()>0){
			qry.append("AND ORGARP = ? ");
			qry.setParameter(++index, contractFilterVO.getOrigin());
		}
		if(contractFilterVO.getDestination()  != null
				&& contractFilterVO.getDestination().trim().length()>0){
			qry.append("AND DSTARP = ? ");
			qry.setParameter(++index, contractFilterVO.getDestination());
		}
		if(contractFilterVO.getContractID()  != null
				&& contractFilterVO.getContractID().trim().length()>0){
			qry.append("AND CTRIDR LIKE ? ");
			qry.setParameter(++index, contractFilterVO.getContractID());
		}
		if(contractFilterVO.getRegion() != null
				&& contractFilterVO.getRegion().trim().length() > 0){
			qry.append("AND REGCOD = ? ");
			qry.setParameter(++index, contractFilterVO.getRegion());
		}

		Collection<GPAContractVO> contractVOs =  qry
				.getResultList(new GPAContractMapper());

		return contractVOs;


	}

	/**
	 * @author A-6986
	 * @param contractFilterVO
	 * @param pageNumber
	 * @return
	 */
	public Collection<GPAContractVO> listODForContract(GPAContractFilterVO contractFilterVO)
			throws SystemException, PersistenceException{

		log.entering(MODULE, "listODForContract");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_CONTRACT_DETAILS);

		int index = 0;

		qry.setParameter(++index, contractFilterVO.getCompanyCode());
		qry.setParameter(++index, contractFilterVO.getPaCode());

		if(contractFilterVO.getOrigin() != null && contractFilterVO.getOrigin().trim().length()>0){
			qry.append("AND ORGARP = ? ");
			qry.setParameter(++index, contractFilterVO.getOrigin());
		}
		if(contractFilterVO.getDestination()  != null
				&& contractFilterVO.getDestination().trim().length()>0){
			qry.append("AND DSTARP = ? ");
			qry.setParameter(++index, contractFilterVO.getDestination());
		}

		Collection<GPAContractVO> contractVOs =  qry
				.getResultList(new GPAContractMapper());

		return contractVOs;


	}
	/**
	 * @author A-7871
	 *Used for ICRD-240184
	 * @param currentAirport
	 * @param paCode
	 * @return receiveFromTruckEnabled
	 * @throws SystemException
	 */
	public String checkReceivedFromTruckEnabled (String currentAirport,String orginAirport,String paCode,LocalDate dspDate) throws SystemException
	{
		String receiveFromTruckEnabled;

		log.entering(MODULE, "checkReceivedFromTruckEnabled");
		int index = 0;
		Query  query = getQueryManager().createNamedNativeQuery(
				CHECK_RECIEVEFRMTRUCK_ENABLED);
		query.setParameter(++index, currentAirport);
		query.setParameter(++index, orginAirport);// modified by A-8353 for ICRD-336294
		query.setParameter(++index, paCode);
		if(dspDate!= null ){
			query.append(" AND ?  BETWEEN MTK.VLDFRMDAT AND MTK.VLDTOODAT ");
			query.setParameter(++index, dspDate.toCalendar());
		}

		receiveFromTruckEnabled = query.getSingleResult(getStringMapper("ARPCOD"));

		log.exiting(MODULE, "checkReceivedFromTruckEnabled");
		return receiveFromTruckEnabled;

	}

	public HashMap<String,String> findAirportForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges) throws SystemException, PersistenceException
	{
		log.entering("MailTrackingDefaultsSqlDAO","findAirportForOfficeOfExchange");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_CITY_FOR_EXCHANGE_OFFICES);
		int idx = 0;
		boolean first = true;
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

		return (HashMap<String, String>) query.getResultList(new AirportForOEMapper()).get(0);
		//return null;
	}
	public Page<ContainerDetailsVO> findContainerDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
			throws SystemException, PersistenceException{
		int index = 0;
		Page<ContainerDetailsVO> coll = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		PageableNativeQuery<ContainerDetailsVO> pageQuery = null;
		Query qry = null;
		boolean isCarrierList = false;
		if (operationalFlightVO.getFlightNumber() != null
				&& operationalFlightVO.getFlightSequenceNumber() > 0) {
			String acceptedContainersInUld = getQueryManager().getNamedNativeQueryString(FIND__OUTBOUND_FLIGHT_CONTAINERDETAILS_ULD);
			String acceptedContainersInBulk = getQueryManager().getNamedNativeQueryString(FIND_OUTBOUND_FLIGHT_CONTAINERDETAILS_BULK);

			rankQuery.append(acceptedContainersInUld);
			pageQuery = new FindOutboundFlightDetailsFilterQuery(operationalFlightVO,rankQuery,acceptedContainersInBulk,new FindContainersinFlightMapper());
			pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);

			/*qry = getQueryManager().createNamedNativeQuery(
					FIND_OUTBOUND_FLIGHT_CONTAINER_DETAILS);
			rankQuery.append(qry);
			pageQuery = new PageableNativeQuery<ContainerDetailsVO>(operationalFlightVO.getRecordsPerPage(),-1,rankQuery.toString(),new FindContainersinFlightMapper());
			pageQuery.setParameter(++index, operationalFlightVO.getCompanyCode());
			pageQuery.setParameter(++index, operationalFlightVO.getCarrierId());
			pageQuery.setParameter(++index, operationalFlightVO.getFlightNumber());
			pageQuery.setParameter(++index, operationalFlightVO
					.getFlightSequenceNumber());
			//qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
			pageQuery.setParameter(++index, operationalFlightVO.getPol());
			pageQuery.setParameter(++index, operationalFlightVO.getCompanyCode());
			pageQuery.setParameter(++index, operationalFlightVO.getCarrierId());
			pageQuery.setParameter(++index, operationalFlightVO.getFlightNumber());
			pageQuery.setParameter(++index, operationalFlightVO
					.getFlightSequenceNumber());
			//qry.setParameter(++index, operationalFlightVO.getLegSerialNumber());
			pageQuery.setParameter(++index, operationalFlightVO.getPol());
			if(operationalFlightVO.getContainerNumber()!= null) {
				pageQuery.append("CONNUM= ?");
				pageQuery.setParameter(++index, operationalFlightVO
						.getContainerNumber());
			}
			pageQuery.append("group by CMPCOD,FLTCARIDR,FLTCARCOD,FLTNUM,CONNUM,FLTSEQNUM,FLTDAT,LEGSERNUM,SEGSERNUM,CONNUM,POU,ASGPRT,DSTCOD,POAFLG, ACPFLG,ARRSTA,OFLFLG,TRNFLG,CONTYP,CONLSTUPDTIM,CONJRNIDR,POACOD,ACTULDWGT,ASGDATUTC,WHSCOD,LOCCOD,TRFCARCOD,RMK,ULDFRMCARCOD,USRCOD,ULDLSTUPDTIM,LSTUPDUSR,CNTIDR)order by CMPCOD,FLTCARIDR,FLTNUM,CONNUM");
			pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		*/} else {
			isCarrierList = true;
			String acceptedUlds = getQueryManager().getNamedNativeQueryString(
					FIND__OUTBOUND_CARRIER_CONTAINERDETAILS_ULD);
			String acceptedContainers = getQueryManager()
					.getNamedNativeQueryString(
							FIND_OUTBOUND_CARRIER_CONTAINERDETAILS_BULK);
			rankQuery.append(acceptedUlds);
			pageQuery = new FindOutboundCarrierDetailsFilterQuery(operationalFlightVO,rankQuery,acceptedContainers,new FindContainersinCarrierMapper());
			pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
			log.log(Log.INFO, "FINAL query ", pageQuery.toString());
		}
		coll=pageQuery.getPage(pageNumber);
		/*if (operationalFlightVO.getFlightNumber() != null
				&& operationalFlightVO.getFlightSequenceNumber() > 0) {
			coll = qry.getResultList(new FindFlightDetailsMapper());
		} else {
			coll = qry.getResultList(new CarrierAcceptanceDetailsMultimapper());
		}*/
		//added as part of IASCB-51789 by A-7815 starts
		if(isCarrierList && coll!=null) {
			for (ContainerDetailsVO container : coll) {
				if(container.getAssignedPort()!=null && container.getContainerNumber()!=null) {
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCompanyCode(container.getCompanyCode());
					containerVO.setContainerNumber(container.getContainerNumber());
					containerVO.setAssignedPort(container.getAssignedPort());
					findOffloadedInfoForCOntainer(containerVO);
					if(containerVO!=null) {
						container.setOffloadCount(containerVO.getOffloadCount());
						container.setOffloadedInfo(containerVO.getOffloadedInfo());
					}
				}
			}
		}
		//added as part of IASCB-51789 by A-7815 ends
		return coll;
	}
	public Page<MailAcceptanceVO>  findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO,int pageNumber) throws SystemException,
			PersistenceException {
		Page<MailAcceptanceVO> flightvos= null;
		log.entering("MailTrackingDefaultsSqlDAO", "findFlightDetailsforOutbound");
		String baseQry1 = getQueryManager().getNamedNativeQueryString(
				OUTBOUND_FIND_FLIGHT_FOR_PREADVICE);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(
				OUTBOUND_FIND_FLIGHTDETAILS);
		String modifiedStr1 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "LISTAGG(CONNAM,',') within group (order by connam) CONNAM , LISTAGG(CONCNT,',') within group (order by connam) CONCNT , LISTAGG(MALCNT,',') within group (order by connam) MALCNT , LISTAGG(wgt,',') within group (order by connam) wgt";
		}
		else
		{
			modifiedStr1 = "STRING_AGG ( CONNAM,',' ORDER BY CONNAM) CONNAM , STRING_AGG(CAST (CONCNT AS CHARACTER VARYING),',' ORDER BY CONNAM) CONCNT , STRING_AGG(CAST (MALCNT AS CHARACTER VARYING),',' ORDER BY CONNAM) MALCNT , STRING_AGG(CAST (WGT AS CHARACTER VARYING), ',' ORDER BY CONNAM) WGT";
		}
		baseQry2 = String.format(baseQry2, modifiedStr1);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		//added by A-7815 as part of IASCB-29597
		int recordsPerPage = 25;
		if(operationalFlightVO.getRecordsPerPage()!=0) {
			recordsPerPage = operationalFlightVO.getRecordsPerPage();
		}
		boolean isOracleDataSource=isOracleDataSource();
		//  PageableNativeQuery<CollectionListVO> pgqry = new CollectionListEnquiryFilterQuery(collectionListFilterVO, masterQuery.toString(), new CollectionListEnquiryMultiMapper());
		PageableNativeQuery<MailAcceptanceVO> pageQuery =new OutboundFlightFilterQuery(recordsPerPage,operationalFlightVO,rankQuery,baseQry2,isOracleDataSource,new OutboundFlightMapper());
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageQuery.setCacheable(false);
		log.log(Log.INFO, "FINAL query ", pageQuery.toString());
		flightvos = pageQuery.getPage(pageNumber);
		log.entering("MailTrackingDefaultsSqlDAO", "flightvos");
		return flightvos;
	}
	public Page<MailbagVO>  findMailbagsinContainer(ContainerDetailsVO containervo,int pageNumber) throws SystemException,
			PersistenceException {
		Page<MailbagVO> mailbagvos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagsinContainer");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_IN_FLIGHT_ULD);
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_INSIDE_FLIGHT_BULK);
		}
		rankQuery.append(qry);
		PageableNativeQuery<MailbagVO> pageQuery = null;
		if (containervo.getFlightNumber() != null
				&& containervo.getFlightSequenceNumber() > 0) {
			pageQuery = new PageableNativeQuery<MailbagVO>(containervo.getTotalRecordSize(),-1,rankQuery.toString(),new OutboundMailbagMapper());
		}
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getFlightNumber());
		pageQuery.setParameter(++index, containervo.getFlightSequenceNumber());
		//pageQuery.setParameter(++index, containervo.getLegSerialNumber());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if(containervo.getAdditionalFilters()!=null) {
			index = populateAdditionalFiltersForMailbags(pageQuery,index,containervo);
		}
		pageQuery.append(" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG,MALMST.YER ");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		mailbagvos=pageQuery.getPage(pageNumber);
		return mailbagvos;
	}
	private int populateAdditionalFiltersForMailbags(PageableNativeQuery<MailbagVO> pageQuery, int index,
													 ContainerDetailsVO containervo) {
		if(containervo.getAdditionalFilters()!=null) {
			MailbagEnquiryFilterVO mailbagFilters= containervo.getAdditionalFilters() ;
			if(mailbagFilters.getMailbagId()!=null && mailbagFilters.getMailbagId().trim().length()>0) {
				pageQuery.append(" AND MALMST.MALIDR = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailbagId());
			}
			if(mailbagFilters.getOriginAirportCode()!=null && mailbagFilters.getOriginAirportCode().trim().length()>0) {
				pageQuery.append(" AND MALMST.ORGCOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getOriginAirportCode());
			}
			if(mailbagFilters.getDestinationAirportCode()!=null && mailbagFilters.getDestinationAirportCode().trim().length()>0) {
				pageQuery.append(" AND MALMST.DSTCOD = ? ");
				pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
			}
			if(mailbagFilters.getMailCategoryCode()!=null && mailbagFilters.getMailCategoryCode().trim().length()>0) {
				pageQuery.append(" AND MALMST.MALCTG = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());

			}
			if(mailbagFilters.getMailSubclass()!=null && mailbagFilters.getMailSubclass().trim().length()>0) {
				pageQuery.append(" AND MALMST.MALSUBCLS = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());

			}
			if(mailbagFilters.getDespatchSerialNumber()!=null && mailbagFilters.getDespatchSerialNumber().trim().length()>0) {
				pageQuery.append(" AND MALMST.DSN = ?");
				pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());

			}
			if(mailbagFilters.getReceptacleSerialNumber()!=null && mailbagFilters.getReceptacleSerialNumber().trim().length()>0) {
				pageQuery.append(" AND MALMST.RSN = ?");
				pageQuery.setParameter(++index, mailbagFilters.getReceptacleSerialNumber());

			}
			if(mailbagFilters.getPacode()!=null && mailbagFilters.getPacode().trim().length()>0) {
				pageQuery.append(" AND MALMST.POACOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getPacode());

			}
			if(mailbagFilters.getConsigmentNumber()!=null && mailbagFilters.getConsigmentNumber().trim().length()>0) {
				pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
				pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
			}
			if(mailbagFilters.getConsignmentDate()!=null) {
				pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.DSPDAT),'YYYYMMDD')) = ? ");
				pageQuery.setParameter(++index, mailbagFilters.getConsignmentDate().toSqlDate().toString().replace("-", ""));
			}
			if(mailbagFilters.getReqDeliveryTime()!=null) {
				pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
				pageQuery.setParameter(++index, mailbagFilters.getReqDeliveryTime().toSqlDate().toString().replace("-", ""));
			}
			if(mailbagFilters.getMasterDocumentNumber()!=null && mailbagFilters.getMasterDocumentNumber().trim().length()>0) {
				pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
				pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());

			}

			if(mailbagFilters.getShipmentPrefix()!=null && mailbagFilters.getShipmentPrefix().trim().length()>0) {
				pageQuery.append(" AND MALMST.SHPPFX = ?");
				pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());

			}

			if(mailbagFilters.getTransferFromCarrier()!=null && mailbagFilters.getTransferFromCarrier().trim().length()>0) {
				pageQuery.append(" AND MAL.FRMCARCOD = ?");
				pageQuery.setParameter(++index, mailbagFilters.getTransferFromCarrier());
			}
			if(mailbagFilters.getCurrentStatus()!=null && mailbagFilters.getCurrentStatus().trim().length()>0) {
				pageQuery.append(" AND ");
				pageQuery.append(" MALMST.MALSTA = ?");
				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			}

			if("Y".equals(mailbagFilters.getCarditPresent())) {
				pageQuery.append(" AND CSGDTL.MALSEQNUM IS NOT NULL ");
			}

			if("Y".equals(mailbagFilters.getDamageFlag())) {
				pageQuery.append(" AND MALMST.DMGFLG = 'Y' ");
			}
		}
		return index;
	}

	public Page<DSNVO>  findMailbagsinContainerdsnview(ContainerDetailsVO containervo,int pageNumber) throws SystemException,
			PersistenceException {
		Page<DSNVO> dsnVos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagsinContainer");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW);
		}
		rankQuery.append(qry);
		PageableNativeQuery<DSNVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<DSNVO>(containervo.getTotalRecordSize(),-1, rankQuery.toString(), new MailbagDSNMapper());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getFlightNumber());
		pageQuery.setParameter(++index, containervo.getFlightSequenceNumber());
		//pageQuery.setParameter(++index, containervo.getLegSerialNumber());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if(containervo.getAdditionalFilters()!=null) {
			index= populateAdditionalMailbagDSNFilterForFlight(pageQuery,index,containervo);
		}

		pageQuery.append(" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ) MST\r\n" +
				"GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,PLTENBFLG ");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		dsnVos=pageQuery.getPage(pageNumber);
		return dsnVos;
	}
	private int populateAdditionalMailbagDSNFilterForFlight(PageableNativeQuery<DSNVO> pageQuery, int index,
															ContainerDetailsVO containervo) {
		MailbagEnquiryFilterVO  mailbagEnquiryFilterVO = containervo.getAdditionalFilters();
		if(mailbagEnquiryFilterVO.getDespatchSerialNumber()!=null && mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSN = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if(mailbagEnquiryFilterVO.getOoe()!=null && mailbagEnquiryFilterVO.getOoe().trim().length()>0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
		}
		if(mailbagEnquiryFilterVO.getDoe()!=null && mailbagEnquiryFilterVO.getDoe().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
		}
		if(mailbagEnquiryFilterVO.getMailCategoryCode()!=null && mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
		}
		if(mailbagEnquiryFilterVO.getMailSubclass()!=null && mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
		}
		if(mailbagEnquiryFilterVO.getMasterDocumentNumber()!=null && mailbagEnquiryFilterVO.getMasterDocumentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMasterDocumentNumber());
		}
		if(mailbagEnquiryFilterVO.getShipmentPrefix()!=null && mailbagEnquiryFilterVO.getShipmentPrefix().trim().length()>0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getShipmentPrefix());
		}
		if(mailbagEnquiryFilterVO.getPacode()!=null && mailbagEnquiryFilterVO.getPacode().trim().length()>0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getPacode());

		}
		return index;
	}


	public  MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO",
				"findCarditSummaryView");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CARDIT_SUMMARY_DETAIL);
		String companyCode=carditEnquiryFilterVO.getCompanyCode();
		String ooe = carditEnquiryFilterVO.getOoe();
		String doe = carditEnquiryFilterVO.getDoe();
		String mailCategoryCode =carditEnquiryFilterVO.getMailCategoryCode();
		String mailClass = carditEnquiryFilterVO.getMailClass();
		String mailSubclass = carditEnquiryFilterVO.getMailSubclass();
		String year = carditEnquiryFilterVO.getYear();
		String despatchSerialNumber = carditEnquiryFilterVO.getDespatchSerialNumber();
		String receptacleSerialNumber = carditEnquiryFilterVO.getReceptacleSerialNumber();
		String consignmentDocument= carditEnquiryFilterVO.getConsignmentDocument();
		String paoCode = carditEnquiryFilterVO.getPaoCode();
		LocalDate fromDate = carditEnquiryFilterVO.getFromDate();
		LocalDate toDate = carditEnquiryFilterVO.getToDate();
		String carrierCode = carditEnquiryFilterVO.getCarrierCode();
		String flightNumber = carditEnquiryFilterVO.getFlightNumber();
		LocalDate flightDate = carditEnquiryFilterVO.getFlightDate();
		String pol = carditEnquiryFilterVO.getPol();
		String uldNumber = carditEnquiryFilterVO.getUldNumber();
		String mailStatus=carditEnquiryFilterVO.getMailStatus();
		String mailbagId = carditEnquiryFilterVO.getMailbagId();
		LocalDate reqDeliveryTime=carditEnquiryFilterVO.getReqDeliveryTime();
		//Added by A-7531 for icrd-192536
		String shipmentPrefix=carditEnquiryFilterVO.getShipmentPrefix();
		int index = 0;
		//qry.setParameter(++index,carditEnquiryFilterVO.getCompanyCode());
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			qry.append(" INNER JOIN MALFLT ASGFLT ");
			qry.append(" ON ASGFLT.CMPCOD  = MALMST.CMPCOD ");
			qry.append(" AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR ");
			qry.append(" AND ASGFLT.FLTNUM    = MALMST.FLTNUM ");
			qry.append(" AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM ");
		}
		if(carditEnquiryFilterVO.isPendingResditChecked()) {
			qry.append(" INNER JOIN MALRDT MALRDT ");
			qry.append(" ON MALRDT.CMPCOD  = CSGDTL.CMPCOD ");
			qry.append(" AND MALRDT.MALSEQNUM = CSGDTL.MALSEQNUM ");
			qry.append("  AND MALRDT.PROSTA='Y' ");
			qry.append(" AND MALRDT.EVTCOD IN ('74','42','48','6','82','57','24','14','40','23','43','41','21') ");
			qry.append(" AND (MALRDT.RDTSND='Y' OR MALRDT.RDTSND='S') ");
		}
		if(companyCode!=null && companyCode.trim().length() > 0)
		{
			qry.append("WHERE CSGMST.CMPCOD = ?");
			qry.setParameter(++index,companyCode);
		}
		if(carditEnquiryFilterVO.getConsignmentDate()!=null)
		{
			qry.append(" AND TRUNC(CSGMST.CSGDAT) = TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, carditEnquiryFilterVO.getConsignmentDate().toSqlDate().toString());
		}
		//A-8061 Added for ICRD-82434 ends
		//Modified by a-7531 for icrd-192536
		if(ooe!=null && ooe.trim().length() > 0)
		{
			qry.append("AND MALMST.ORGEXGOFC = ? ");
			qry.setParameter(++index,ooe);
		}
		if(doe!=null && doe.trim().length() > 0)
		{
			qry.append("AND MALMST.DSTEXGOFC = ?");
			qry.setParameter(++index,doe);
		}
		if(mailCategoryCode!=null && mailCategoryCode.trim().length() > 0)
		{
			qry.append("AND MALMST.MALCTG = ?");
			qry.setParameter(++index,mailCategoryCode);
		}
		if(year!=null && year.trim().length() > 0)
		{
			qry.append("AND MALMST.YER = ?");
			qry.setParameter(++index,year);
		}
		if(despatchSerialNumber!=null && despatchSerialNumber.trim().length() > 0)
		{
			qry.append("AND  MALMST.DSN = ?");
			qry.setParameter(++index,despatchSerialNumber);
		}
		if(receptacleSerialNumber!=null && receptacleSerialNumber.trim().length() > 0)
		{
			qry.append("AND MALMST.RSN = ?");
			qry.setParameter(++index,receptacleSerialNumber);
		}
		if(consignmentDocument!=null && consignmentDocument.trim().length() > 0)
		{
			qry.append("AND CSGMST.CSGDOCNUM = ?");
			qry.setParameter(++index,consignmentDocument);
		}
		if(mailSubclass != null && mailSubclass.trim().length() > 0) {
			qry.append(" AND MALMST.MALSUBCLS = ? ");
			qry.setParameter(++index, mailSubclass);

		}
		if(fromDate!=null)
		{
			qry.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, fromDate.toSqlDate().toString());
		}
		if(toDate!=null)
		{
			qry.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
			qry.setParameter(++index, toDate.toSqlDate().toString());
		}
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(carditEnquiryFilterVO.getFlightType())) {
			if(carrierCode!=null && carrierCode.trim().length() > 0)
			{
				qry.append("AND ASGFLT.FLTCARCOD = ?");
				qry.setParameter(++index,carrierCode);
			}
			if(flightNumber!=null && flightNumber.trim().length() > 0)
			{
				qry.append("AND ASGFLT.FLTNUM = ?");
				qry.setParameter(++index,flightNumber);
			}
			if(flightDate!=null)
			{
				qry.append("AND TRUNC(ASGFLT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.setParameter(++index,flightDate.toSqlDate().toString());
			}
		}
		else{
			if(carrierCode!=null && carrierCode.trim().length() > 0)
			{
				qry.append("AND TRT.FLTCARCOD = ?");
				qry.setParameter(++index,carrierCode);
			}
			if(flightNumber!=null && flightNumber.trim().length() > 0)
			{
				qry.append("AND TRT.FLTNUM = ?");
				qry.setParameter(++index,flightNumber);
			}
			if(flightDate!=null)
			{
				qry.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.setParameter(++index,flightDate.toSqlDate().toString());
			}
		}
		if(pol!=null && pol.trim().length()>0)
		{
			qry.append("AND TRT.POL = ?");
			qry.setParameter(++index,pol);
		}
		if(uldNumber!=null && uldNumber.trim().length()>0)
		{
			qry.append("AND CSGDTL.ULDNUM = ?");
			qry.setParameter(++index,uldNumber);
		}
		if(mailbagId != null&&mailbagId.trim().length()>0) {
			qry.append(" AND MALMST.MALIDR = ? ");
			qry.setParameter(++index,mailbagId);
		}
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					qry.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					qry.append(" AND MALMST.REQDLVTIM = ?");
				}
				qry.setParameter(++index,reqDeliveryTime);
			}
		}
		if(shipmentPrefix != null&&shipmentPrefix.trim().length()>0)
		{
			qry.append("AND MALMST.SHPPFX = ?");
			qry.setParameter(++index,shipmentPrefix);
		}
		if(carditEnquiryFilterVO.getDocumentNumber()!=null && carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0)
		{
			qry.append("AND MALMST.MSTDOCNUM = ?");
			qry.setParameter(++index,carditEnquiryFilterVO.getDocumentNumber());
		}
		/**
		 * As per new table structure always data present in MALMST
		 */
		if(mailStatus!=null && mailStatus.trim().length()>0){
			if("ACP".equals(mailStatus)){//modified by A-7371 as part of ICRD-251859
				qry.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			}
			else if("CAP".equals(mailStatus)){
				qry.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}//a7531
		//Added by A-8176 for ICRD-228739
		if(carditEnquiryFilterVO.getMailOrigin() !=null && carditEnquiryFilterVO.getMailOrigin().trim().length()>0) {
			qry.append("AND MALMST.ORGCOD= ?");
			qry.setParameter(++index,carditEnquiryFilterVO.getMailOrigin());
		}
		if(carditEnquiryFilterVO.getMaildestination()!=null && carditEnquiryFilterVO.getMaildestination().trim().length()>0) {
			qry.append("AND MALMST.DSTCOD= ?");
			qry.setParameter(++index,carditEnquiryFilterVO.getMaildestination());
		}
		qry.append(" )group by cmpcod");
		log.log(Log.INFO, "Query: ", qry);
		log.exiting("MailTrackingDefaultsSqlDAO",
				"findCarditDetailsForAllMailBags");
		return qry.getSingleResult(new OutboundCarditSummaryMapper());
	}
	public Page<MailbagVO> findGroupedCarditMails(
			CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findGroupedCarditMails");
		//PageableQuery<MailbagVO> pgqry = null;
		Page<MailbagVO> mailbagVos = null;
		String  baseQry1 = getQueryManager().getNamedNativeQueryString(
				FIND_CARDIT_GROUP_VIEW_ACCEPTED);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(
				FIND_CARDIT_GROUP_VIEW_COUNT);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		PageableNativeQuery<MailbagVO> qry = null;
		//Modified by A-5220 for ICRD-21098 starts
		if(carditEnquiryFilterVO.getPageSize() >0) {
			qry = new OutboundCarditGroupFilterQuery(new OutboundCarditGroupMapper(), carditEnquiryFilterVO, rankQuery.toString(),baseQry2.toString(), carditEnquiryFilterVO.getPageSize());
		} else {
			qry = new OutboundCarditGroupFilterQuery(new OutboundCarditGroupMapper(), carditEnquiryFilterVO, rankQuery.toString(),baseQry2.toString());
		}
		log.log(Log.INFO, "Query: ", qry);
		mailbagVos = qry.getPage(pageNumber);
		//Modified by A-5220 for ICRD-21098 ends
		return mailbagVos;
	}
	public  MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailbags");
		log.entering(MODULE, "findMailbags");
		//for Mail Outbound Screen Deviation Tab
		if("DEVIATION_LIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
			return findDeviationMailBagSummaryView(mailbagEnquiryFilterVO);
		}
		String outerQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
		String baseQuery = null;
		boolean acceptedmailbagFilterQuery=false;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		//rankQuery.append("MALIDR,CSGDOCNUM,CSGSEQNUM ) AS RANK FROM (");
		rankQuery.append("RNK ) AS RANK FROM (");
		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		/*
		 * Added By karthick V as a Part of NCA CR to include the Functionality
		 * View MailBags in the InventoryList Screen.. Modified on 10-sep-2007
		 * to include the Functionality show the Incoming flight in the
		 * Inventory..
		 */
		if (mailbagEnquiryFilterVO.isInventory()) {
			log.log(Log.INFO, "FOR INVENTORY MAIL BAGS");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_INVENTORY_MAILBAGS);
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
					.length() > 0) {
				qry.append(appendArrivedFlightToInventory());
			}
			qry.append("  AND MALMST.CMPCOD = ? ");
			qry.append(" AND MALMST.SCNPRT= ?   ");
			qry.append(" AND ARPULD.FLTCARIDR= ?  ");
			qry.append(" AND ARPULD.DSTCOD  IS  NULL ");
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierId());
			/*
			 * Append the Incoming Arrival Flight if Present in the Filter .
			 *
			 */
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
					.length() > 0) {
				qry.append(" AND MALHIS.MALSTA = ?  ");
				qry.append(" AND MALHIS.FLTNUM = ?  ");
				qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
				qry.setParameter(++index, mailbagEnquiryFilterVO
						.getFromFlightNumber());
			}
			if (mailbagEnquiryFilterVO.getContainerNumber() != null
					&& mailbagEnquiryFilterVO.getContainerNumber().trim()
					.length() > 0) {
				qry.append(" AND MALMST.CONNUM= ?  ");
				qry.setParameter(++index, mailbagEnquiryFilterVO
						.getContainerNumber());
			}
			if (mailbagEnquiryFilterVO.getDestinationCity() != null
					&& mailbagEnquiryFilterVO.getDestinationCity().trim()
					.length() > 0) {
				qry.append(" AND SUBSTR( MALMST.DSTEXGOFC,3,3) IN");
				qry.append(new StringBuilder("(").append(
								mailbagEnquiryFilterVO.getDestinationCity())
						.append(")").toString());
			}
			log.log(Log.FINE, "THE MAIL BAG ENQUIRY FILTER VO",
					mailbagEnquiryFilterVO);
			if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
					&& mailbagEnquiryFilterVO.getMailCategoryCode().trim()
					.length() > 0) {
				if (MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO
						.getMailCategoryCode())) {
					qry.append(appendMilitaryClasses(qry, index, true));
				} else {
					qry.append(" AND MALMST.MALCTG IN ");
					qry.append(new StringBuilder("(").append(
									mailbagEnquiryFilterVO.getMailCategoryCode())
							.append(")").toString());
					qry.append(appendMilitaryClasses(qry, index, false));
				}
			}
		} else {
			if("".equals(mailbagEnquiryFilterVO.getCurrentStatus()) || mailbagEnquiryFilterVO.getCurrentStatus()==null){
				baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*)COUNT, sum(WGT)TOTWGT from	(";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
						FIND_MAILBAGS));
				log.log(Log.INFO,"The Status is <<ALL>> ");
				if(!"ACP".equals(mailbagEnquiryFilterVO.getCurrentStatus()))
				{
					baseQuery=baseQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RNK ");
				}
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),true);
			}else if (MailConstantsVO.MAIL_STATUS_RETURNED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT, sum(WGT)TOTWGT from	(";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
						FIND_MAILBAGS_FORRETURN));
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyingListReturnFilterQuery(new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO,rankQuery.toString(),true);
			} else {
				if (MailConstantsVO.MAIL_STATUS_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())) {
					baseQuery = getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS);
					outerQuery=outerQuery.concat(baseQuery);
					// rankQuery.append(outerQuery);
					log
							.log(Log.INFO,
									"The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
					if(MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())){
						log.log(Log.FINE, "Current Status:- ACP from mailExportList");
						//rankQuery.append(baseQuery);
						acceptedmailbagFilterQuery=true;
						pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,outerQuery,true);
						log.log(Log.FINE, "Base query passed,suffix query not required");
					}else{
						if(( mailbagEnquiryFilterVO.getCarrierId() == 0)
								&& (mailbagEnquiryFilterVO.getFlightNumber() == null || mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)){
							acceptedmailbagFilterQuery=true;
							if(mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
									mailbagEnquiryFilterVO.getFlightNumber()==null){
								//rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery, outerQuery,false);
							}
							else{
								if("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))
								{
									outerQuery=outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								}
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO,baseQuery, outerQuery,true);
							}
							log.log(Log.FINE, "Base query passed,suffix query not required");
						}else{
							if(mailbagEnquiryFilterVO.getCarrierId()>0 && mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
									mailbagEnquiryFilterVO.getFlightNumber()==null){
								log.log(Log.FINE, "Carrier level and from assign continer");
								acceptedmailbagFilterQuery=true;
								//rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,outerQuery,true);
							}else if(mailbagEnquiryFilterVO.getCarrierId()>0 && ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))){
								acceptedmailbagFilterQuery=true;
								outerQuery=outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, baseQuery,outerQuery,true);
							}
							else{
								log.log(Log.FINE, "not from mailExportList,rank query used,suffix query to be added");
								//rankQuery.append(outerQuery);
								acceptedmailbagFilterQuery=true;
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO,baseQuery, outerQuery.toString(),true);
							}
						}
					}
				} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())) {
					log.log(Log.INFO, "THE STATUS IS ", mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS));

					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundlyinglistOffloadedFilterQuery(
							new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),true);
				} else if(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())){
					baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT ,sum(WGT) TOTWGT from (";
					int carrierId = mailbagEnquiryFilterVO.getCarrierId();
					LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
					Date flightSqlDate = null;
					if (flightDate != null) {
						flightSqlDate = flightDate.toSqlDate();
					}
					String flightDateString = String.valueOf(flightSqlDate);
					/* Changed by A-5274 for Bug with id: ICRD-29006
					 * In this block qry replaced with baseQuery, instead of createNamedNativeQuery getNamedNativeQueryString used
					 */
						/*qry = getQueryManager().createNamedNativeQuery(
								FIND_CAP_NOT_ACCPETED_MAILBAGS); */
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_CAP_NOT_ACCPETED_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),rankQuery.toString(),new OutboundCarditSummaryMapper());
					if(carrierId > 0) {
						pageableNativeQuery.append(" AND FLTCARIDR = ? ");
						pageableNativeQuery.setParameter(++index, carrierId);
					}
					if(mailbagEnquiryFilterVO.getCarrierCode() != null &&
							mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
					}
					if(mailbagEnquiryFilterVO.getFlightNumber() != null &&
							mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTNUM = ? " );
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
					}
					if (flightSqlDate != null) {
						pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
						pageableNativeQuery.setParameter(++index, flightDateString);
					}
					pageableNativeQuery.append(" ) WHERE ");
					if(mailbagEnquiryFilterVO.getCompanyCode() != null &&
							mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
						pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
					}
					if(mailbagEnquiryFilterVO.getContainerNumber() != null &&
							mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
					}
					LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
					LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
					if(scanFromDate != null && scanToDate != null) {
						pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
						pageableNativeQuery.setParameter(++index, scanFromDate);
						pageableNativeQuery.append(" AND  ?  ");
						pageableNativeQuery.setParameter(++index, scanToDate);
					}
					//Added for ICRD-133967 starts
					if(mailbagEnquiryFilterVO.getConsigmentNumber() != null &&
							mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
					}
					if(mailbagEnquiryFilterVO.getUpuCode() != null &&
							mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
					}
					//Added for ICRD-133967 ends
					//Added for ICRD-214795 starts
					if(mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
						String rqdDlvTime=mailbagEnquiryFilterVO.getReqDeliveryTime().toDisplayFormat("yyyyMMddHHmm");
						if(rqdDlvTime!=null){
							if(rqdDlvTime.contains("0000")){
								pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
							}else{
								pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
							}
							pageableNativeQuery.setParameter(++index,mailbagEnquiryFilterVO.getReqDeliveryTime());
						}
					}
					//Added for ICRD-214795 ends
					//Added by A-4809 for ICRD-180189 .. Starts
					if(mailbagEnquiryFilterVO.getDespatchSerialNumber() !=null &&
							mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.DSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
					}
					if(mailbagEnquiryFilterVO.getOoe()!=null && !mailbagEnquiryFilterVO.getOoe().isEmpty()){
						pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
					}
					if(mailbagEnquiryFilterVO.getDoe()!=null && !mailbagEnquiryFilterVO.getDoe().isEmpty()){
						pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
					}
					if(mailbagEnquiryFilterVO.getMailCategoryCode()!=null &&
							mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
					}
					if(mailbagEnquiryFilterVO.getMailSubclass()!=null &&
							mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
					}
					if(mailbagEnquiryFilterVO.getYear()!=null && mailbagEnquiryFilterVO.getYear().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.YER = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
					}
					if(mailbagEnquiryFilterVO.getReceptacleSerialNumber()!=null
							&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.RSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
					}
					//Added for ICRD-205027 starts
					if(mailbagEnquiryFilterVO.getMailbagId()!=null
							&& mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
					}
					//Added for ICRD-205027 ends
					//Added by A-8672 for ICRD-327149 starts
					if(mailbagEnquiryFilterVO.getServiceLevel()!=null
							&& mailbagEnquiryFilterVO.getServiceLevel().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
					}
					//Added by A-8672 for ICRD-327149 ends
					//Added for ICRD-323389 starts
					if(mailbagEnquiryFilterVO.getOnTimeDelivery()!=null
							&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
					}
					//Added for ICRD-323389 ends
					//Added by A-4809 for ICRD-180189 .. Ends
					if(mailbagEnquiryFilterVO.getCarditPresent() != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
						pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
								.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
								.append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
								.append(" ) ");
					}
					pageableNativeQuery.append(")");
				}else {
					log.log(Log.INFO, "THE STATUS IS--- ",
							mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, count(*) COUNT, sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundLyinglistArrivalFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),true);
				}
			}
		}
		//PageableNativeQuery<PermanentBookingVO> query = new PermanentBookingsFilterQuery(new PermanentBookingsMultiMapper(),permanentBookingFilterVo, baseQuery);
		//pageableNativeQuery= new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);
		/**
		 * Commented as booking time is not needed starts
		 */

		log.log(Log.INFO, "Query is", pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if(!acceptedmailbagFilterQuery)
		{
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pageableNativeQuery.getSingleResult(new OutboundCarditSummaryMapper());
	}

	private MailbagVO findDeviationMailBagSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws SystemException {
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append(" RNK ) AS RANK FROM ( ");
		rankQuery.append(" SELECT ROW_NUMBER() OVER(ORDER BY NULL) AS RNK,");
		rankQuery.append(" COUNT(*) COUNT, SUM(WGT) TOTWGT FROM ( ");
		rankQuery.append("  SELECT  MAX(WGT) WGT, CMPCOD, MALIDR, MALSEQNUM FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new OutboundCarditSummaryMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append("  )reqdata GROUP BY CMPCOD, MALIDR, MALSEQNUM )maldata ");
		pageableNativeQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
		return pageableNativeQuery.getSingleResult(new OutboundCarditSummaryMapper());
	}


	public Page<MailbagVO> findGroupedLyingList(
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException,PersistenceException{
		log.entering(MODULE, "findMailbags");
		log.entering(MODULE, "findMailbags");
		//for Mail Outbound Screen Deviation Tab
		if("DEVIATION_LIST".equals(mailbagEnquiryFilterVO.getFromScreen())) {
			return findDeviationMailBagGroup(mailbagEnquiryFilterVO, pageNumber);
		}
		String baseQuery = null;
		String outerQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT , sum(case acpflg when 'Y' then 1 else 0 end) ACCPCNT,sum(case acpflg when 'Y' then WGT else 0 end) ACCPWGT,sum(WGT) TOTWGT from (";
		boolean acceptedmailbagFilterQuery=false;

		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append("RNK ) AS RANK FROM (");
		//StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		//rankQuery.append("MALIDR,CSGDOCNUM,CSGSEQNUM ) AS RANK FROM (");
		//rankQuery.append("ROW_NUMBER() OVER(ORDER BY 1) ) AS RANK FROM (");
		Query qry = null;
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		int index = 0;
		/*
		 * Added By karthick V as a Part of NCA CR to include the Functionality
		 * View MailBags in the InventoryList Screen.. Modified on 10-sep-2007
		 * to include the Functionality show the Incoming flight in the
		 * Inventory..
		 */
		if (mailbagEnquiryFilterVO.isInventory()) {
			log.log(Log.INFO, "FOR INVENTORY MAIL BAGS");
			baseQuery = getQueryManager().getNamedNativeQueryString(FIND_INVENTORY_MAILBAGS);
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
					.length() > 0) {
				qry.append(appendArrivedFlightToInventory());
			}
			qry.append("  AND MALMST.CMPCOD = ? ");
			qry.append(" AND MALMST.SCNPRT= ?   ");
			qry.append(" AND ARPULD.FLTCARIDR= ?  ");
			qry.append(" AND ARPULD.DSTCOD  IS  NULL ");
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());
			qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierId());
			/*
			 * Append the Incoming Arrival Flight if Present in the Filter .
			 *
			 */
			if (mailbagEnquiryFilterVO.getFromFlightNumber() != null
					&& mailbagEnquiryFilterVO.getFromFlightNumber().trim()
					.length() > 0) {
				qry.append(" AND MALHIS.MALSTA = ?  ");
				qry.append(" AND MALHIS.FLTNUM = ?  ");
				qry.setParameter(++index, MailConstantsVO.MAIL_STATUS_ARRIVED);
				qry.setParameter(++index, mailbagEnquiryFilterVO
						.getFromFlightNumber());
			}
			if (mailbagEnquiryFilterVO.getContainerNumber() != null
					&& mailbagEnquiryFilterVO.getContainerNumber().trim()
					.length() > 0) {
				qry.append(" AND MALMST.CONNUM= ?  ");
				qry.setParameter(++index, mailbagEnquiryFilterVO
						.getContainerNumber());
			}
			if (mailbagEnquiryFilterVO.getDestinationCity() != null
					&& mailbagEnquiryFilterVO.getDestinationCity().trim()
					.length() > 0) {
				qry.append(" AND SUBSTR( MALMST.DSTEXGOFC,3,3) IN");
				qry.append(new StringBuilder("(").append(
								mailbagEnquiryFilterVO.getDestinationCity())
						.append(")").toString());
			}
			log.log(Log.FINE, "THE MAIL BAG ENQUIRY FILTER VO",
					mailbagEnquiryFilterVO);
			if (mailbagEnquiryFilterVO.getMailCategoryCode() != null
					&& mailbagEnquiryFilterVO.getMailCategoryCode().trim()
					.length() > 0) {
				if (MailConstantsVO.MIL_MAL_CAT.equals(mailbagEnquiryFilterVO
						.getMailCategoryCode())) {
					qry.append(appendMilitaryClasses(qry, index, true));
				} else {
					qry.append(" AND MALMST.MALCTG IN ");
					qry.append(new StringBuilder("(").append(
									mailbagEnquiryFilterVO.getMailCategoryCode())
							.append(")").toString());
					qry.append(appendMilitaryClasses(qry, index, false));
				}
			}
		} else {
			if("".equals(mailbagEnquiryFilterVO.getCurrentStatus()) || mailbagEnquiryFilterVO.getCurrentStatus()==null){

				baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK,DSTCOD, count(*) COUNT , sum(case acpflg when 'Y' then 1 else 0 end) ACCPCNT,sum(case acpflg when 'Y' then WGT else 0 end) ACCPWGT,sum(WGT) TOTWGT from (";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
						FIND_MAILBAGS));


				log.log(Log.INFO,"The Status is <<ALL>> ");
					/*if(!"ACP".equals(mailbagEnquiryFilterVO.getCurrentStatus()))
						{
						baseQuery=baseQuery.concat(", ROWNUM AS RNK ");
						}*/
				rankQuery.append(baseQuery);
				if(mailbagEnquiryFilterVO.getPageSize()>0) {
					pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),false,mailbagEnquiryFilterVO.getPageSize());
				} else {
					pageableNativeQuery = new OutboundLyinglistFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),false);
				}
			}else if (MailConstantsVO.MAIL_STATUS_RETURNED
					.equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
				baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
				baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
						FIND_MAILBAGS_FORRETURN));
				rankQuery.append(baseQuery);
				pageableNativeQuery = new OutboundLyingListReturnFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO,rankQuery.toString(),false);
			} else {
				if (MailConstantsVO.MAIL_STATUS_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_ASSIGNED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())) {
					outerQuery="SELECT ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, COUNT(*) COUNT , COUNT(*) ACCPCNT,SUM(WGT) ACCPWGT,SUM(WGT) TOTWGT FROM (";
					baseQuery = getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS);
					outerQuery=outerQuery.concat(baseQuery);
					rankQuery.append(outerQuery);
					log
							.log(Log.INFO,
									"The Status is <<ACCEPTED>><<TRANSFERRED>><<ASSIGNED>> ");
					if(MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getFromExportList())){
						log.log(Log.FINE, "Current Status:- ACP from mailExportList");
						rankQuery.append(baseQuery);
						acceptedmailbagFilterQuery=true;
						pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,rankQuery.toString(),false);
						log.log(Log.FINE, "Base query passed,suffix query not required");
					}else{
						if(( mailbagEnquiryFilterVO.getCarrierId() == 0)
								&& (mailbagEnquiryFilterVO.getFlightNumber() == null || mailbagEnquiryFilterVO.getFlightNumber().trim().length() == 0)){
							acceptedmailbagFilterQuery=true;
							if(mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
									mailbagEnquiryFilterVO.getFlightNumber()==null){
								rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,rankQuery.toString(),false);
							}
							else{
								if("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))
								{
									outerQuery=outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								}
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,rankQuery.toString(),false);
							}
							log.log(Log.FINE, "Base query passed,suffix query not required");
						}else{
							if(mailbagEnquiryFilterVO.getCarrierId()>0 && mailbagEnquiryFilterVO.getFromScreen()!=null && "ASSIGNCONTAINER".equals(mailbagEnquiryFilterVO.getFromScreen()) &&
									mailbagEnquiryFilterVO.getFlightNumber()==null){
								log.log(Log.FINE, "Carrier level and from assign continer");
								acceptedmailbagFilterQuery=true;
								rankQuery.append(outerQuery);
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,rankQuery.toString(),false);
							}else if(mailbagEnquiryFilterVO.getCarrierId()>0 && ("MAILEXPORTLIST".equals(mailbagEnquiryFilterVO.getFromScreen()))){
								acceptedmailbagFilterQuery=true;
								outerQuery=outerQuery.concat(", ROW_NUMBER() OVER(ORDER BY 1) AS RANK");
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,outerQuery,false);
							}
							else{
								log.log(Log.FINE, "not from mailExportList,rank query used,suffix query to be added");
								rankQuery.append(outerQuery);
								acceptedmailbagFilterQuery=true;
								pageableNativeQuery = new OutboundLyingListAcceptedFilterQuery(
										new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery,rankQuery.toString(),false);
							}
						}
					}
				} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())
						|| MailConstantsVO.MAIL_STATUS_NOTUPLIFTED
						.equals(mailbagEnquiryFilterVO
								.getCurrentStatus())) {
					log.log(Log.INFO, "THE STATUS IS ", mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery="select DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS));

					//rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundlyinglistOffloadedFilterQuery(
							new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, baseQuery.toString(),false);
				} else if(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED
						.equals(mailbagEnquiryFilterVO.getCurrentStatus())){
					baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT ,0 ACPCOUNT,0 ACPWGT,sum(WGT) TOTWGT from (";
					int carrierId = mailbagEnquiryFilterVO.getCarrierId();
					LocalDate flightDate = mailbagEnquiryFilterVO.getFlightDate();
					Date flightSqlDate = null;
					if (flightDate != null) {
						flightSqlDate = flightDate.toSqlDate();
					}
					String flightDateString = String.valueOf(flightSqlDate);
					/* Changed by A-5274 for Bug with id: ICRD-29006
					 * In this block qry replaced with baseQuery, instead of createNamedNativeQuery getNamedNativeQueryString used
					 */
						/*qry = getQueryManager().createNamedNativeQuery(
								FIND_CAP_NOT_ACCPETED_MAILBAGS); */
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_CAP_NOT_ACCPETED_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new PageableNativeQuery<MailbagVO>(mailbagEnquiryFilterVO.getTotalRecords(),rankQuery.toString(),new OutboundCarditGroupMapper());
					if(carrierId > 0) {
						pageableNativeQuery.append(" AND FLTCARIDR = ? ");
						pageableNativeQuery.setParameter(++index, carrierId);
					}
					if(mailbagEnquiryFilterVO.getCarrierCode() != null &&
							mailbagEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTCARCOD  = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode());
					}
					if(mailbagEnquiryFilterVO.getFlightNumber() != null &&
							mailbagEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND FLTNUM = ? " );
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
					}
					if (flightSqlDate != null) {
						pageableNativeQuery.append(" AND  TRUNC(FLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
						pageableNativeQuery.setParameter(++index, flightDateString);
					}
					pageableNativeQuery.append(" ) WHERE ");
					if(mailbagEnquiryFilterVO.getCompanyCode() != null &&
							mailbagEnquiryFilterVO.getCompanyCode().trim().length() > 0) {
						pageableNativeQuery.append(" CSGMAL.CMPCOD = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
					}
					if(mailbagEnquiryFilterVO.getContainerNumber() != null &&
							mailbagEnquiryFilterVO.getContainerNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.ULDNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getContainerNumber());
					}
					LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
					LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
					if(scanFromDate != null && scanToDate != null) {
						pageableNativeQuery.append(" AND  CSGMST.CSGDAT BETWEEN  ?  ");
						pageableNativeQuery.setParameter(++index, scanFromDate);
						pageableNativeQuery.append(" AND  ?  ");
						pageableNativeQuery.setParameter(++index, scanToDate);
					}
					//Added for ICRD-133967 starts
					if(mailbagEnquiryFilterVO.getConsigmentNumber() != null &&
							mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0) {
						pageableNativeQuery.append(" AND CSGMAL.CSGDOCNUM = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
					}
					if(mailbagEnquiryFilterVO.getUpuCode() != null &&
							mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0) {
						pageableNativeQuery.append(" AND POADTL.PARVAL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getUpuCode());
					}
					//Added for ICRD-133967 ends
					//Added for ICRD-214795 starts
					if(mailbagEnquiryFilterVO.getReqDeliveryTime() != null) {
						String rqdDlvTime=mailbagEnquiryFilterVO.getReqDeliveryTime().toDisplayFormat("yyyyMMddHHmm");
						if(rqdDlvTime!=null){
							if(rqdDlvTime.contains("0000")){
								pageableNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
							}else{
								pageableNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
							}
							pageableNativeQuery.setParameter(++index,mailbagEnquiryFilterVO.getReqDeliveryTime());
						}
					}
					//Added for ICRD-214795 ends
					//Added by A-4809 for ICRD-180189 .. Starts
					if(mailbagEnquiryFilterVO.getDespatchSerialNumber() !=null &&
							mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.DSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
					}
					if(mailbagEnquiryFilterVO.getOoe()!=null && !mailbagEnquiryFilterVO.getOoe().isEmpty()){
						pageableNativeQuery.append(" AND MALMST.ORGEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
					}
					if(mailbagEnquiryFilterVO.getDoe()!=null && !mailbagEnquiryFilterVO.getDoe().isEmpty()){
						pageableNativeQuery.append(" AND MALMST.DSTEXGOFC = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
					}
					if(mailbagEnquiryFilterVO.getMailCategoryCode()!=null &&
							mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALCTG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
					}
					if(mailbagEnquiryFilterVO.getMailSubclass()!=null &&
							mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALSUBCLS = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
					}
					if(mailbagEnquiryFilterVO.getYear()!=null && mailbagEnquiryFilterVO.getYear().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.YER = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getYear());
					}
					if(mailbagEnquiryFilterVO.getReceptacleSerialNumber()!=null
							&& mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.RSN = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
					}
					//Added for ICRD-205027 starts
					if(mailbagEnquiryFilterVO.getMailbagId()!=null
							&& mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALIDR = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
					}
					//Added for ICRD-205027 ends
					//Added by A-8672 for ICRD-327149 starts
					if(mailbagEnquiryFilterVO.getServiceLevel()!=null
							&& mailbagEnquiryFilterVO.getServiceLevel().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.MALSRVLVL = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getServiceLevel());
					}
					//Added by A-8672 for ICRD-327149 ends
					//Added for ICRD-323389 starts
					if(mailbagEnquiryFilterVO.getOnTimeDelivery()!=null
							&& mailbagEnquiryFilterVO.getOnTimeDelivery().trim().length()>0){
						pageableNativeQuery.append(" AND MALMST.ONNTIMDLVFLG = ? ");
						pageableNativeQuery.setParameter(++index, mailbagEnquiryFilterVO.getOnTimeDelivery());
					}
					//Added for ICRD-323389 ends
					//Added by A-4809 for ICRD-180189 .. Ends
					if(mailbagEnquiryFilterVO.getCarditPresent() != null
							&& MailConstantsVO.FLAG_YES.equals(mailbagEnquiryFilterVO.getCarditPresent())) {
						pageableNativeQuery.append(" AND EXISTS (SELECT 1 FROM MALCDTRCP RCP ")
								.append(" WHERE RCP.CMPCOD = CSGMAL.CMPCOD ")
								.append(" AND RCP.RCPIDR     = MALMST.MALIDR ")
								.append(" ) ");
					}
					pageableNativeQuery.append(")MST group by DSTCOD");
				}else {
					log.log(Log.INFO, "THE STATUS IS--- ",
							mailbagEnquiryFilterVO.getCurrentStatus());
					baseQuery="select ROW_NUMBER() OVER(ORDER BY NULL) AS RNK, DSTCOD, count(*) COUNT, sum(case acpflg when 'Y' then 1 else 0 end) ACPCOUNT,sum(case acpflg when 'Y' then WGT else 0 end) ACPWGT,sum(WGT) TOTWGT from (";
					baseQuery = baseQuery.concat(getQueryManager().getNamedNativeQueryString(
							FIND_MAILBAGS));
					rankQuery.append(baseQuery);
					pageableNativeQuery = new OutboundLyinglistArrivalFilterQuery(new OutboundCarditGroupMapper(), mailbagEnquiryFilterVO, rankQuery.toString(),false);
				}
			}
		}
		//PageableNativeQuery<PermanentBookingVO> query = new PermanentBookingsFilterQuery(new PermanentBookingsMultiMapper(),permanentBookingFilterVo, baseQuery);
		//pageableNativeQuery= new MailBagFilterQuery(new MailbagMapper(), mailbagEnquiryFilterVO, baseQuery);
		/**
		 * Commented as booking time is not needed starts
		 */

		log.log(Log.INFO, "Query is", pageableNativeQuery.toString());
		AcceptedMailBagFilterQuery.setRank(false);
		if(!acceptedmailbagFilterQuery)
		{
			pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}

		return pageableNativeQuery.getPage(pageNumber);
	}
	public Page<MailAcceptanceVO>  findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO,int pageNumber) throws SystemException,
			PersistenceException {
		Page<MailAcceptanceVO> flightvos= null;
		log.entering("MailTrackingDefaultsSqlDAO", "findFlightDetailsforOutbound");
		String baseQryforuld = null;
		String baseQry1 = getQueryManager().getNamedNativeQueryString(
				OUTBOUND_FIND_CARRIER_FOR_ULD);
		String modifiedStr1 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "LISTAGG(CONNAM,',') within GROUP (ORDER BY connam) CONNAM , LISTAGG(CONCNT,',') within GROUP (ORDER BY connam) CONCNT , LISTAGG(MALCNT,',') within GROUP (ORDER BY connam) MALCNT , LISTAGG(WGT,',') within GROUP (ORDER BY connam) WGT";
		}
		else
		{
			modifiedStr1 = "string_agg ( connam,',' ORDER BY  connam) CONNAM, string_agg(CAST (CONCNT as character varying),',' ORDER BY connam)  CONCNT , string_agg(CAST (MALCNT as character varying),',' ORDER BY connam) MALCNT , string_agg(CAST (wgt as character varying), ',' ORDER BY connam) WGT";
		}
		baseQry1 = String.format(baseQry1, modifiedStr1);
		String baseQry2 = getQueryManager().getNamedNativeQueryString(
				OUTBOUND_FIND_CARRIER_FOR_BULK);
		String baseQry3 = getQueryManager().getNamedNativeQueryString(
				OUTBOUND_FIND_CARRIER_FOR_EMPTY);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry1);
		int recordsPerPage = 25;
		if(operationalFlightVO.getRecordsPerPage()!=0) {
			recordsPerPage = operationalFlightVO.getRecordsPerPage();
		}
		PageableNativeQuery<MailAcceptanceVO> pageQuery = new OutboundCarrierFilterQuery(recordsPerPage,operationalFlightVO,rankQuery,baseQry2,baseQry3,new OutboundCarrierMapper());
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		log.log(Log.INFO, "FINAL query ", pageQuery.toString());
		log.log(Log.INFO, "Query: ", pageQuery);
		flightvos = pageQuery.getPage(pageNumber);
		//Modified by A-5220 for ICRD-21098 ends
		log.entering("MailTrackingDefaultsSqlDAO", "flightvos");
		return flightvos;
	}
	public Page<MailbagVO>  getMailbagsinCarrierContainer(ContainerDetailsVO containervo,int pageNumber) throws SystemException,
			PersistenceException {
		Page<MailbagVO> mailbagvos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "getMailbagsinCarrierContainer");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_IN_CARRIER_ULD);
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_IN_CARRIER_BULK);
		}
		rankQuery.append(qry);
		PageableNativeQuery<MailbagVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<MailbagVO>(containervo.getTotalRecordSize(),-1,rankQuery.toString(),new OutboundMailbagMapper());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if(containervo.getDestination()!=null && containervo.getDestination().trim().length()>0) {
			pageQuery.append("AND MST.DSTCOD = ?");
			pageQuery.setParameter(++index, containervo.getDestination());
		}
		index = populateMailagAdditionalFilter(pageQuery,index,containervo);
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			pageQuery.append(" ORDER BY MST.CONNUM,MST.SEGSERNUM,MALMST.DSN,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,MALMST.MALSUBCLS,MALMST.MALCTG,MALMST.YER");
		} else {
			pageQuery.append(" ORDER BY MST.CONNUM,MST.SEGSERNUM,DSNMST.DSN,DSNMST.ORGEXGOFC,DSNMST.DSTEXGOFC,DSNMST.MALSUBCLS,DSNMST.MALCTG,DSNMST.YER");
		}
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		mailbagvos=pageQuery.getPage(pageNumber);
		return mailbagvos;
	}

	private int populateMailagAdditionalFilter(PageableNativeQuery<MailbagVO> pageQuery,int index,
											   ContainerDetailsVO containervo) {
		if(containervo.getAdditionalFilters()!=null) {
			MailbagEnquiryFilterVO mailbagFilter= containervo.getAdditionalFilters();
			if(mailbagFilter.getMailbagId()!=null && mailbagFilter.getMailbagId().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALIDR = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailbagId());
				} else {
					pageQuery.append(" AND DSNMST.MALIDR = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailbagId());
				}

			}
			if(mailbagFilter.getOriginAirportCode()!=null && mailbagFilter.getOriginAirportCode().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.ORGCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getOriginAirportCode());
				} else {
					pageQuery.append(" AND DSNMST.ORGCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getOriginAirportCode());
				}

			}
			if(mailbagFilter.getDestinationAirportCode()!=null && mailbagFilter.getDestinationAirportCode().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DSTCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDestinationAirportCode());
				} else {
					pageQuery.append(" AND DSNMST.DSTCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDestinationAirportCode());
				}

			}
			if(mailbagFilter.getMailCategoryCode()!=null && mailbagFilter.getMailCategoryCode().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALCTG = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailCategoryCode());
				} else {
					pageQuery.append(" AND DSNMST.MALCTG = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailCategoryCode());
				}

			}
			if(mailbagFilter.getMailSubclass()!=null && mailbagFilter.getMailSubclass().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALSUBCLS = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailSubclass());
				} else {
					pageQuery.append(" AND DSNMST.MALSUBCLS = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMailSubclass());
				}

			}
			if(mailbagFilter.getDespatchSerialNumber()!=null && mailbagFilter.getDespatchSerialNumber().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDespatchSerialNumber());
				} else {
					pageQuery.append(" AND DSNMST.DSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getDespatchSerialNumber());
				}

			}
			if(mailbagFilter.getReceptacleSerialNumber()!=null && mailbagFilter.getReceptacleSerialNumber().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.RSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getReceptacleSerialNumber());
				} else {
					pageQuery.append(" AND DSNMST.RSN = ?");
					pageQuery.setParameter(++index, mailbagFilter.getReceptacleSerialNumber());
				}

			}
			if(mailbagFilter.getPacode()!=null && mailbagFilter.getPacode().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.POACOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getPacode());
				} else {
					pageQuery.append(" AND DSNMST.POACOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getPacode());
				}

			}
			if(mailbagFilter.getConsigmentNumber()!=null && mailbagFilter.getConsigmentNumber().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getConsigmentNumber());
				} else {
					pageQuery.append(" AND DSNMST.CSGDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getConsigmentNumber());
				}
			}
			if(mailbagFilter.getConsignmentDate()!=null) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.DSPDAT),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index, mailbagFilter.getConsignmentDate().toSqlDate().toString().replace("-", ""));
				} else {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(DSNMST.DSPDAT),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index, mailbagFilter.getConsignmentDate().toSqlDate().toString().replace("-", ""));
				}
			}
			if(mailbagFilter.getReqDeliveryTime()!=null) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index, mailbagFilter.getReqDeliveryTime().toSqlDate().toString().replace("-", ""));
				} else {
					pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(DSNMST.REQDLVTIM),'YYYYMMDD')) = ? ");
					pageQuery.setParameter(++index, mailbagFilter.getReqDeliveryTime().toSqlDate().toString().replace("-", ""));
				}
			}
			if(mailbagFilter.getMasterDocumentNumber()!=null && mailbagFilter.getMasterDocumentNumber().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMasterDocumentNumber());
				} else {
					pageQuery.append(" AND DSNMST.MSTDOCNUM = ?");
					pageQuery.setParameter(++index, mailbagFilter.getMasterDocumentNumber());
				}

			}

			if(mailbagFilter.getShipmentPrefix()!=null && mailbagFilter.getShipmentPrefix().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.SHPPFX = ?");
					pageQuery.setParameter(++index, mailbagFilter.getShipmentPrefix());
				} else {
					pageQuery.append(" AND DSNMST.SHPPFX = ?");
					pageQuery.setParameter(++index, mailbagFilter.getShipmentPrefix());
				}

			}

			if(mailbagFilter.getTransferFromCarrier()!=null && mailbagFilter.getTransferFromCarrier().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND ARPULD.FRMCARCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getTransferFromCarrier());
				} else {
					pageQuery.append(" AND CONARP.FRMCARCOD = ?");
					pageQuery.setParameter(++index, mailbagFilter.getTransferFromCarrier());
				}

			}
			if(mailbagFilter.getCurrentStatus()!=null && mailbagFilter.getCurrentStatus().trim().length()>0) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.MALSTA = ?");
					pageQuery.setParameter(++index, mailbagFilter.getCurrentStatus());
				} else {
					pageQuery.append(" AND DSNMST.MALSTA = ?");
					pageQuery.setParameter(++index, mailbagFilter.getCurrentStatus());
				}

			}

			if("Y".equals(mailbagFilter.getCarditPresent())) {
				pageQuery.append(" AND CSGDTL.MALSEQNUM IS NOT NULL ");
			}

			if("Y".equals(mailbagFilter.getDamageFlag())) {
				if(MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
					pageQuery.append(" AND MALMST.DMGFLG = 'Y' ");
				} else {
					pageQuery.append(" AND DSNMST.DMGFLG = 'Y' ");
				}
			}

		}
		return index;
	}


	public  Page<DSNVO> getMailbagsinCarrierContainerdsnview(ContainerDetailsVO containervo,int pageNumber) throws SystemException,
			PersistenceException {
		Page<DSNVO> dsnvos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagsinContainer");
		String baseQryforuld = null;
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_CARRIER_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_CARRIER_BULK_DSNVIEW);
		}
		rankQuery.append(qry);
		PageableNativeQuery<DSNVO> pageQuery = null;
		pageQuery = new PageableNativeQuery<DSNVO>(containervo.getTotalRecordSize(),-1, rankQuery.toString(), new MailbagDSNMapper());
		pageQuery.setParameter(++index, containervo.getCompanyCode());
		pageQuery.setParameter(++index, containervo.getCarrierId());
		pageQuery.setParameter(++index, containervo.getPol());
		pageQuery.setParameter(++index, containervo.getContainerNumber());
		if(containervo.getDestination()!=null && containervo.getDestination().trim().length()>0) {
			pageQuery.append("AND MST.DSTCOD = ?");
			pageQuery.setParameter(++index, containervo.getDestination());
		}
		if(containervo.getAdditionalFilters()!=null) {
			index = populateAdditionalMailbagDSNFilter(pageQuery,index,containervo);
		}
		pageQuery.append(" ORDER BY MST.CONNUM,MST.SEGSERNUM,MALMST.DSN,MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,MALMST.MALSUBCLS,MALMST.MALCTG,MALMST.YER) MST GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER");
		pageQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		dsnvos=pageQuery.getPage(pageNumber);
		return dsnvos;
	}
	private int populateAdditionalMailbagDSNFilter(PageableNativeQuery<DSNVO> pageQuery, int index,
												   ContainerDetailsVO containervo) {
		MailbagEnquiryFilterVO  mailbagEnquiryFilterVO = containervo.getAdditionalFilters();
		if(mailbagEnquiryFilterVO.getDespatchSerialNumber()!=null && mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSN = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
		}
		if(mailbagEnquiryFilterVO.getOoe()!=null && mailbagEnquiryFilterVO.getOoe().trim().length()>0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
		}
		if(mailbagEnquiryFilterVO.getDoe()!=null && mailbagEnquiryFilterVO.getDoe().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
		}
		if(mailbagEnquiryFilterVO.getMailCategoryCode()!=null && mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
		}
		if(mailbagEnquiryFilterVO.getMailSubclass()!=null && mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
		}
		if(mailbagEnquiryFilterVO.getMasterDocumentNumber()!=null && mailbagEnquiryFilterVO.getMasterDocumentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getMasterDocumentNumber());
		}
		if(mailbagEnquiryFilterVO.getShipmentPrefix()!=null && mailbagEnquiryFilterVO.getShipmentPrefix().trim().length()>0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getShipmentPrefix());
		}
		if(mailbagEnquiryFilterVO.getPacode()!=null && mailbagEnquiryFilterVO.getPacode().trim().length()>0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagEnquiryFilterVO.getPacode());

		}
		return index;
	}


	public Collection<DSNVO>  getDSNsForContainer(ContainerDetailsVO containervo) throws SystemException,
			PersistenceException {
		List<DSNVO> dsnVos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		//rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagsinContainer");
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_FLIGHT_ULD_DSNVIEW);
			qry.setParameter(++index, containervo.getCompanyCode());
			qry.setParameter(++index, containervo.getCarrierId());
			qry.setParameter(++index, containervo.getFlightNumber());
			qry.setParameter(++index, containervo.getFlightSequenceNumber());
			//pageQuery.setParameter(++index, containervo.getLegSerialNumber());
			qry.setParameter(++index, containervo.getPol());
			qry.setParameter(++index, containervo.getContainerNumber());
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_FLIGHT_BULK_DSNVIEW);
			qry.setParameter(++index, containervo.getCompanyCode());
			qry.setParameter(++index, containervo.getCarrierId());
			//pageQuery.setParameter(++index, containervo.getLegSerialNumber());
			qry.setParameter(++index, containervo.getFlightNumber());
			qry.setParameter(++index, containervo.getFlightSequenceNumber());
			qry.setParameter(++index, containervo.getPol());
			qry.setParameter(++index, containervo.getContainerNumber());
		}
		qry.append(" ORDER BY CON.CONNUM, CON.SEGSERNUM, MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ) MST\r\n" +
				"GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,PLTENBFLG ");
		//qry = new Query( qry.toString(), new MailbagDSNMapper());
		//qry.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		//dsnVos=pageQuery.getPage(pageNumber);
		dsnVos = qry.getResultList(new MailbagDSNMapper());
		return dsnVos;
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listFlightDetails(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO)
	 *	Added by 			: A-8164 on 25-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailArrivalVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO)
			throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO","listFlightDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_FLIGHTS_FOR_INBOUND1);
		Query query2 = getQueryManager().createNamedNativeQuery(
				FIND_FLIGHTS_FOR_INBOUND2);
		Query query3 = getQueryManager().createNamedNativeQuery(
				FIND_FLIGHTS_FOR_INBOUND3);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if(mailArrivalVO.getDefaultPageSize()==0){
			mailArrivalVO.setDefaultPageSize(10);
		}
		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.SEVERE, " logonAttributes exception");
		}
		PageableNativeQuery<MailArrivalVO> pgNativeQuery =
				new PageableNativeQuery<MailArrivalVO>(mailArrivalVO.getDefaultPageSize(),-1, masterQuery.toString(),
						new ListFlightDetailsMultiMapper());
		int index = 0;
		if(null!=mailArrivalVO.getCompanyCode()){
			pgNativeQuery.append("AND FLTMST.CMPCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getCompanyCode());
		}
		if(null!=mailArrivalVO.getAirportCode()){
			pgNativeQuery.append("AND FLTSEG.SEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
			pgNativeQuery.append("AND FLTLEG.LEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if(null!=mailArrivalVO.getFlightNumber()){
			pgNativeQuery.append("AND FLTMST.FLTNUM    = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if(null!=mailArrivalVO.getFlightCarrierCode()){
			pgNativeQuery.append("AND ARLMST.TWOAPHCOD    = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if(null!=mailArrivalVO.getFlightDate()){
			pgNativeQuery.append("AND TO_NUMBER (to_char (fltleg.sta, 'YYYYMMDD')) = ?");
			pgNativeQuery.setParameter(++index,Integer.parseInt(mailArrivalVO.getFlightDate().toSqlDate().toString().replace("-", "")));
		}
		if(null!=mailArrivalVO.getFlightStatus()){
			if("O".equals(mailArrivalVO.getFlightStatus())){
				//pgNativeQuery.append("AND ( IMPCLSFLG IS NULL");
				pgNativeQuery.append("AND (ULDSEG.ULDNUM IS NOT NULL AND COALESCE(MALFLT.IMPCLSFLG,'O') ='O')");
				//pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightStatus());
			}
			else if("N".equals(mailArrivalVO.getFlightStatus())){
				pgNativeQuery.append("AND ULDSEG.ULDNUM IS  NULL");
			}
			else{
				pgNativeQuery.append("AND MALFLT.IMPCLSFLG = ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightStatus());
			}
		}
		if(null!=mailArrivalVO.getArrivalPA()){
			pgNativeQuery.append("AND MALMST.POACOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getArrivalPA());
		}
		if(null!=mailArrivalVO.getTransferCarrier()){
			pgNativeQuery.append("AND SEGDTL.TRFCARCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getTransferCarrier());
		}
		if(null!=mailArrivalVO.getPol() && mailArrivalVO.getPol().trim().length()>0){
			//commented by A-7815 as part of IASCB-52179
			//pgNativeQuery.append("AND SEGDTL.SCNPRT = ?");
			pgNativeQuery.append(" AND  FLTSEG.SEGORG = ? ");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getPol());
		}
		//added by A-7815 as part of ICRD-353361
		if(null!=mailArrivalVO.getFromDate()&& null!=mailArrivalVO.getToDate() && !isFlightFilterPresent(mailArrivalVO)) {
			/*pgNativeQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(FLTLEG.STA),'YYYYMMDD')) BETWEEN ? AND ? ");
			pgNativeQuery.setParameter(++index, Integer.parseInt(mailArrivalVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)));
			pgNativeQuery.setParameter(++index, Integer.parseInt(mailArrivalVO.getToDate().toStringFormat(MailConstantsVO.YYYYMMDD).substring(0, 8)));
		*/
			//added by A-8893 as part of
			pgNativeQuery.append(" AND FLTLEG.STA >= ? ");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFromDate());
			pgNativeQuery.append(" AND FLTLEG.STA <= ? ");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getToDate());
		}
		else {
			if(null!=mailArrivalVO.getFromDate()&& !isFlightFilterPresent(mailArrivalVO)) {
				//commented by A-7815 as part of ICRD-353361
			/*
			pgNativeQuery.append("AND FLTLEG.STA >= ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFromDate());
			pgNativeQuery.append("AND TO_NUMBER(TO_CHAR(FLTLEG.STA,'YYYYMMDD')) >= ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getFromDate().toStringFormat(MailConstantsVO.YYYYMMDD).substring(0, 8)));*/
				pgNativeQuery.append(" AND FLTLEG.STA >= ? ");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFromDate());


			}
			if(null!=mailArrivalVO.getToDate()&& !isFlightFilterPresent(mailArrivalVO)) {
				//commented by A-7815 as part of ICRD-353361
			/*
			pgNativeQuery.append("AND FLTLEG.STA <= ? ");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getToDate());
			pgNativeQuery.append("AND TO_NUMBER(TO_CHAR(FLTLEG.STA,'YYYYMMDD')) <= ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)));*/
				pgNativeQuery.append(" AND FLTLEG.STA <= ? ");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getToDate());


			}
		}

		//added by A-7815 as part of IASCB-36551
		String operatingReference = mailArrivalVO.getOperatingReference();
		if(operatingReference!=null && operatingReference.trim().length()!=0){
			pgNativeQuery.append(" AND coalesce(FLTREF.REFCARCOD,'"+logonAttributes.getOwnAirlineCode()+"') IN ( ");
			String[] operatingReferences = operatingReference.split(",");
			int flightRefIndex=0;
			for(String fltRef:operatingReferences){
				pgNativeQuery.append("?");
				if(++flightRefIndex < operatingReferences.length){
					pgNativeQuery.append(",");
				}
				pgNativeQuery.setParameter(++index, fltRef.trim());
			}
			pgNativeQuery.append(")");
		}
		//pgNativeQuery.combine(query2);
		//query.combine(query2);

		//index = 0;

		/*if(null!=mailArrivalVO.getFlightCarrierCode()){
			pgNativeQuery.append("AND FLTMST.CMPCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if(null!=mailArrivalVO.getAirportCode()){
			pgNativeQuery.append("AND FLTSEG.SEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
			pgNativeQuery.append("AND FLTLEG.LEGDST =  ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if(null!=mailArrivalVO.getFlightNumber()){
			pgNativeQuery.append("AND FLTMST.FLTNUM    = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if(null!=mailArrivalVO.getFlightDate()){
			pgNativeQuery.append("AND TRUNC(FLTLEG.STA) = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightDate().toDisplayDateOnlyFormat());
		}
		if(null!=mailArrivalVO.getFlightStatus()){
			if("O".equals(mailArrivalVO.getFlightStatus()))
				pgNativeQuery.append("AND IMPCLSFLG IS NULL");
			else
			{
			pgNativeQuery.append("AND MALFLT.IMPCLSFLG = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightStatus());
			}
		}
		if(null!=mailArrivalVO.getArrivalPA()){
			pgNativeQuery.append("AND MALMST.POACOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getArrivalPA());
		}
		if(null!=mailArrivalVO.getTransferCarrier()){
			pgNativeQuery.append("AND SEGDTL.TRFCARCOD = ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getTransferCarrier());
		}
		if(null!=mailArrivalVO.getFromDate()){
			pgNativeQuery.append("AND TRUNC(FLTMST.FLTDAT) >= ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getFromDate().toDisplayFormat());
		}
		if(null!=mailArrivalVO.getToDate()){
			pgNativeQuery.append("AND TRUNC(FLTMST.FLTDAT) <= ?");
			pgNativeQuery.setParameter(++index, mailArrivalVO.getToDate().toDisplayFormat());
		}*/

		/*if((null!=mailArrivalVO.getFlightStatus() && mailArrivalVO.getFlightStatus().equals("N"))&&
				(mailArrivalVO.getArrivalPA()==null&&null==mailArrivalVO.getTransferCarrier())){
			pgNativeQuery.combine(query3);

			if(null!=mailArrivalVO.getFlightCarrierCode()){
				pgNativeQuery.append("AND FLTMST.CMPCOD = ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
			}
			if(null!=mailArrivalVO.getAirportCode()){
				pgNativeQuery.append("AND FLTLEG.LEGDST =  ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getAirportCode());
			}
			if(null!=mailArrivalVO.getFlightNumber()){
				pgNativeQuery.append("AND FLTMST.FLTNUM    = ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightNumber());
			}
			if(null!=mailArrivalVO.getFlightDate()){
				pgNativeQuery.append("AND TRUNC(FLTLEG.STA) = ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFlightDate().toDisplayDateOnlyFormat());
			}
			if(null!=mailArrivalVO.getFromDate()){
				pgNativeQuery.append("AND TRUNC(FLTMST.FLTDAT) >= ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getFromDate().toDisplayFormat());
			}
			if(null!=mailArrivalVO.getToDate()){
				pgNativeQuery.append("AND TRUNC(FLTMST.FLTDAT) <= ?");
				pgNativeQuery.setParameter(++index, mailArrivalVO.getToDate().toDisplayFormat());
			}
		}*/
		pgNativeQuery.append(") mst GROUP BY CMPCOD,FLTNUM,FLTDAT,FLTCARIDR,FLTSEQNUM,FLTCARCOD,LEGDST,LEGORG,FLTSTA,FLTROU,"
				+ "STA,ETA,ATA,ACRTYP,ARVGTE,LEGSERNUM,ARRTIM,IMPCLSFLG");

		//Added by A-8464 for ICRD-328502
		if(mailArrivalVO.isMailFlightChecked()){
			pgNativeQuery.append(" HAVING SUM(TOTACPBAG)+SUM(TOTRCVBAG) >= 1 ");
		}
		pgNativeQuery.append(" ORDER BY  fltdat ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.log(Log.INFO, "FINAL query ", pgNativeQuery.toString());
		return pgNativeQuery.getPage(mailArrivalVO.getPageNumber());
	}



	public Collection<MailArrivalVO> listManifestDetails(MailArrivalVO mailArrivalVO)
			throws SystemException {

		log.entering("MailTrackingDefaultsSqlDAO","listManifestDetails");
		Query query = getQueryManager().createNamedNativeQuery(FIND_MANIFEST_DETAILS);
		boolean isFlightFilterPresent = false;
		if (mailArrivalVO.getFlightNumber() != null && mailArrivalVO.getFlightNumber().trim().length() > 0 && mailArrivalVO.getFlightDate() != null) {
			isFlightFilterPresent = true;
		}
		int index = 0;
		if(null!=mailArrivalVO.getFlightCarrierCode()){
			query.append("AND FLTCON.CMPCOD = ?");
			query.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if(null!=mailArrivalVO.getAirportCode()){
			query.append("AND SEG.POU =  ?");
			query.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if(null!=mailArrivalVO.getFlightNumber()){
			query.append("AND MALFLT.FLTNUM    = ?");
			query.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if(null!=mailArrivalVO.getFlightDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) = ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getFlightDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		if(!isFlightFilterPresent && null!=mailArrivalVO.getFromDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) >= ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getFromDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		if(!isFlightFilterPresent && null!=mailArrivalVO.getToDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) <= ? ");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getToDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		Query query2 = getQueryManager().createNamedNativeQuery(FIND_MANIFEST_DETAILS2);
		query.combine(query2);
		if(null!=mailArrivalVO.getFlightCarrierCode()){
			query.append("AND FLTCON.CMPCOD = ?");
			query.setParameter(++index, mailArrivalVO.getFlightCarrierCode());
		}
		if(null!=mailArrivalVO.getAirportCode()){
			query.append("AND SEG.POU =  ?");
			query.setParameter(++index, mailArrivalVO.getAirportCode());
		}
		if(null!=mailArrivalVO.getFlightNumber()){
			query.append("AND MALFLT.FLTNUM    = ?");
			query.setParameter(++index, mailArrivalVO.getFlightNumber());
		}
		if(null!=mailArrivalVO.getFlightDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) = ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getFlightDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		if(!isFlightFilterPresent && null!=mailArrivalVO.getFromDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) >= ?");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getFromDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}
		if(!isFlightFilterPresent && null!=mailArrivalVO.getToDate()){
			query.append("AND TO_NUMBER(TO_CHAR(LEG.STA, 'YYYYMMDD')) <= ? ");
			query.setParameter(++index, Integer.parseInt(mailArrivalVO.getToDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
		}

		query.append(") mst GROUP BY CMPCOD,FLTCARIDR,FLTNUM,FLTSEQNUM,SEGSERNUM,CONNUM");
		return query.getResultList(new ListManifestDetailsMultiMapper());

	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedContainersForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO)
	 *	Added by 			: A-8164 on 29-Dec-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailArrivalFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
			throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO","findArrivedContainersForInbound");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_CONTAINER_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if(mailArrivalFilterVO.getDefaultPageSize()==0){
			mailArrivalFilterVO.setDefaultPageSize(10);
		}
		PageableNativeQuery<ContainerDetailsVO> pgNativeQuery =
				new PageableNativeQuery<ContainerDetailsVO>(mailArrivalFilterVO.getDefaultPageSize(),-1, masterQuery.toString(),
						new InboundListContainerDetailsMultiMapper());
		int index = 0;
		for(int i=0;i<1;i++){
			/*pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());*/
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());

		}

		if(mailArrivalFilterVO.getContainerNumber()!=null && mailArrivalFilterVO.getContainerNumber().trim().length()>0) {
			pgNativeQuery.append(" AND ULD.ULDNUM =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());
		}
		if(mailArrivalFilterVO.getPol()!=null && mailArrivalFilterVO.getPol().trim().length()>0) {
			pgNativeQuery.append(" AND CON.ASGPRT =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if(mailArrivalFilterVO.getDestination()!=null && mailArrivalFilterVO.getDestination().trim().length()>0) {
			pgNativeQuery.append(" AND CON.DSTCOD =?  ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getDestination());
		}
		pgNativeQuery.append(" ) ) MST GROUP BY CMPCOD, FLTCARIDR,FLTNUM,CONNUM,FLTSEQNUM,SEGSERNUM,CONNUM,ULDNUM,POU,ASGPRT,DSTCOD,CONTYP,CONJRNIDR,POACOD,ACTULDWGT,ACTWGTSTA,LOCCOD,ULDFRMCARCOD,LEGSERNUM ) ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.log(Log.INFO, "FINAL query ", pgNativeQuery.toString());
		//return query.getResultList(new InboundListContainerDetailsMultiMapper());
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedMailbagsForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO)
	 *	Added by 			: A-8164 on 29-Dec-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailArrivalFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
			throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO","findArrivedMailbagsForInbound");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_MAILBAG_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if(mailArrivalFilterVO.getDefaultPageSize()==0){
			mailArrivalFilterVO.setDefaultPageSize(10);
		}
		PageableNativeQuery<MailbagVO> pgNativeQuery =
				new PageableNativeQuery<MailbagVO>(mailArrivalFilterVO.getDefaultPageSize(),-1, masterQuery.toString(),
						new InboundListMailbagDetailsMultiMapper());
		int index = 0;
		for(int i=0;i<1;i++){
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, String.valueOf(mailArrivalFilterVO.getFlightNumber()));
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());

		}
		if(mailArrivalFilterVO.getPol()!=null && mailArrivalFilterVO.getPol().trim().length()>0) {
			pgNativeQuery.append(" AND FLT.POL =  ? ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if(mailArrivalFilterVO.getAdditionalFilter()!=null) {
			index = populateAdditionalFilter(pgNativeQuery,index,mailArrivalFilterVO.getAdditionalFilter());
		}
		pgNativeQuery.append(" ORDER BY CONNUM, DSN,ORGEXGOFC,DSTEXGOFC,MALCLS,MALSUBCLS,MALCTGCOD,YER,MALSTA ) ");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);

		//return query.getResultList(new InboundListMailbagDetailsMultiMapper());
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	private int populateAdditionalFilter(PageableNativeQuery<MailbagVO> pageQuery, int index,
										 MailbagEnquiryFilterVO mailbagFilters) {

		if(mailbagFilters.getMailbagId()!=null && mailbagFilters.getMailbagId().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALIDR = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailbagId());
		}
		if(mailbagFilters.getOriginAirportCode()!=null && mailbagFilters.getOriginAirportCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.ORGCOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getOriginAirportCode());
		}
		if(mailbagFilters.getDestinationAirportCode()!=null && mailbagFilters.getDestinationAirportCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSTCOD = ? ");
			pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
		}
		if(mailbagFilters.getMailCategoryCode()!=null && mailbagFilters.getMailCategoryCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());

		}
		if(mailbagFilters.getMailSubclass()!=null && mailbagFilters.getMailSubclass().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());

		}
		if(mailbagFilters.getDespatchSerialNumber()!=null && mailbagFilters.getDespatchSerialNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());

		}
		if(mailbagFilters.getReceptacleSerialNumber()!=null && mailbagFilters.getReceptacleSerialNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.RSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getReceptacleSerialNumber());

		}
		if(mailbagFilters.getPacode()!=null && mailbagFilters.getPacode().trim().length()>0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getPacode());

		}
		if(mailbagFilters.getConsigmentNumber()!=null && mailbagFilters.getConsigmentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
		}
		if(mailbagFilters.getReqDeliveryTime()!=null) {
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALMST.REQDLVTIM),'YYYYMMDD')) = ? ");
			pageQuery.setParameter(++index, mailbagFilters.getReqDeliveryTime().toSqlDate().toString().replace("-", ""));
		}
		if(mailbagFilters.getMasterDocumentNumber()!=null && mailbagFilters.getMasterDocumentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());
		}
		if(mailbagFilters.getShipmentPrefix()!=null && mailbagFilters.getShipmentPrefix().trim().length()>0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());

		}
		if(mailbagFilters.getCurrentStatus()!=null && mailbagFilters.getCurrentStatus().trim().length()>0) {
			if (MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagFilters.getCurrentStatus())) {
				pageQuery.append("AND (MALMST.MALSTA = ? AND (MAL.ARRSTA='N' OR MAL.ARRSTA IS NULL)) ");
				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			}
			else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagFilters.getCurrentStatus())) {
				pageQuery.append("AND MAL.ARRSTA='Y' ");
			}

			else {
				pageQuery.append(" AND MALMST.MALSTA = ?");

				pageQuery.setParameter(++index, mailbagFilters.getCurrentStatus());
			}
		}

		if("Y".equals(mailbagFilters.getCarditPresent())) {
			pageQuery.append(" AND CSGMST.CSGDOCNUM IS NOT NULL ");
		}

		return index;
	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findArrivedDsnsForInbound(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO)
	 *	Added by 			: A-8164 on 29-Dec-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailArrivalFilterVO
	 *	Parameters	:	@return
	 */
	public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
			throws SystemException{
		log.entering("MailTrackingDefaultsSqlDAO","findArrivedDsnsForInbound");
		Query query = getQueryManager().createNamedNativeQuery(FIND_INBOUND_DSN_DETAILS);
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		masterQuery.append(query);
		if(mailArrivalFilterVO.getDefaultPageSize()==0){
			mailArrivalFilterVO.setDefaultPageSize(25);
		}
		PageableNativeQuery<DSNVO> pgNativeQuery =
				new PageableNativeQuery<DSNVO>(mailArrivalFilterVO.getDefaultPageSize(),-1, masterQuery.toString(),
						new InboundListDsnDetailsMultiMapper());
		int index = 0;
		for(int i=0;i<1;i++){
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCompanyCode());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getCarrierId());
			pgNativeQuery.setParameter(++index, String.valueOf(mailArrivalFilterVO.getFlightNumber()));
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getFlightSequenceNumber());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPou());
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerNumber());
			//pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getContainerType());
		}
		if(mailArrivalFilterVO.getPol()!=null && mailArrivalFilterVO.getPol().trim().length()>0) {
			pgNativeQuery.append(" AND FLT.POL =  ? ");
			pgNativeQuery.setParameter(++index, mailArrivalFilterVO.getPol());
		}
		if(mailArrivalFilterVO.getAdditionalFilter()!=null) {
			index = populateMailbagDSNAdditionalFilters(pgNativeQuery,mailArrivalFilterVO.getAdditionalFilter(),index);
		}
		pgNativeQuery.append(" )MST GROUP BY CMPCOD,DSN,ORGEXGOFC, DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER,RMK,CSGDOCNUM )");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		//return query.getResultList(new InboundListDsnDetailsMultiMapper());
		return pgNativeQuery.getPage(mailArrivalFilterVO.getPageNumber());
	}

	private int populateMailbagDSNAdditionalFilters(PageableNativeQuery<DSNVO> pageQuery,
													MailbagEnquiryFilterVO mailbagFilters, int index) {

		if(mailbagFilters.getOoe()!=null && mailbagFilters.getOoe().trim().length()>0) {
			pageQuery.append(" AND MALMST.ORGEXGOFC = ?");
			pageQuery.setParameter(++index, mailbagFilters.getOoe());
		}
		if(mailbagFilters.getDestinationAirportCode()!=null && mailbagFilters.getDestinationAirportCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSTEXGOFC = ? ");
			pageQuery.setParameter(++index, mailbagFilters.getDestinationAirportCode());
		}
		if(mailbagFilters.getMailCategoryCode()!=null && mailbagFilters.getMailCategoryCode().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALCTG = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailCategoryCode());

		}
		if(mailbagFilters.getMailSubclass()!=null && mailbagFilters.getMailSubclass().trim().length()>0) {
			pageQuery.append(" AND MALMST.MALSUBCLS = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMailSubclass());

		}
		if(mailbagFilters.getDespatchSerialNumber()!=null && mailbagFilters.getDespatchSerialNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.DSN = ?");
			pageQuery.setParameter(++index, mailbagFilters.getDespatchSerialNumber());

		}
		if(mailbagFilters.getPacode()!=null && mailbagFilters.getPacode().trim().length()>0) {
			pageQuery.append(" AND MALMST.POACOD = ?");
			pageQuery.setParameter(++index, mailbagFilters.getPacode());

		}
		if(mailbagFilters.getConsigmentNumber()!=null && mailbagFilters.getConsigmentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.CSGDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getConsigmentNumber());
		}
		if(mailbagFilters.getMasterDocumentNumber()!=null && mailbagFilters.getMasterDocumentNumber().trim().length()>0) {
			pageQuery.append(" AND MALMST.MSTDOCNUM = ?");
			pageQuery.setParameter(++index, mailbagFilters.getMasterDocumentNumber());
		}
		if(mailbagFilters.getShipmentPrefix()!=null && mailbagFilters.getShipmentPrefix().trim().length()>0) {
			pageQuery.append(" AND MALMST.SHPPFX = ?");
			pageQuery.setParameter(++index, mailbagFilters.getShipmentPrefix());

		}
		return index;
	}


	/**
	 * @author A-7929
	 */
	public Page<ContainerVO> findAcceptedContainersForOffLoad(
			OffloadFilterVO offloadFilterVO) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findAcceptedContainersForOffLoad");
		int pageSize = offloadFilterVO.getDefaultPageSize();
		int totalRecords=offloadFilterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder().append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query offloadContainerquery = getQueryManager().createNamedNativeQuery(FIND_OFFLOAD_DETAILS);
		rankQuery.append(offloadContainerquery);

		PageableNativeQuery<ContainerVO> query = new OffloadContainerUXFilterQuery(pageSize,totalRecords, new OffloadContainerMultiMapper(), rankQuery.toString(), offloadFilterVO,offloadContainerquery);
		query.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		Page<ContainerVO> containerVOs =query.getPage(offloadFilterVO.getPageNumber());

		if (containerVOs != null && containerVOs.size() > 0) {
			for (ContainerVO containerVO : containerVOs) {
				containerVO
						.setFlightDate(offloadFilterVO.getFlightDate());
			}
		}
		return containerVOs;

	}



	/**
	 * @author A-8061
	 * @param shipmentSummaryVO
	 * @param mailFlightSummaryVO
	 * @return
	 * @throws SystemException
	 */
	public ScannedMailDetailsVO findMailbagsForAWB(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{

		int index = 0;
		String flightNumber=mailFlightSummaryVO.getFlightNumber();
		long flightSeqNumber=mailFlightSummaryVO.getFlightSequenceNumber();
		Query query = null;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();

		query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FOR_AWB);
		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());

		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());

		query.setParameter(++index, mailFlightSummaryVO.getCarrierId());
		query.setParameter(++index, flightNumber);
		query.setParameter(++index, flightSeqNumber);

		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());


		if(mailFlightSummaryVO.getFlightNumber()!=null){
			query.setParameter(++index,
					new StringBuilder("").append(mailFlightSummaryVO.getCarrierId()).append("-")
							.append(mailFlightSummaryVO.getFlightSequenceNumber()).append("-")
							.append(mailFlightSummaryVO.getFlightNumber()).toString());
		}else{
			query.setParameter(++index, " ");
		}
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs =
				query.getResultList(new AWBAttachedMailbagDetailsMapper());

		if(scannedMailDetailsVOs != null && !scannedMailDetailsVOs.isEmpty()){
			scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs).get(0);
		}
		return scannedMailDetailsVO;


	}


	/**
	 * @author A-8061
	 */
	public Boolean isMailAsAwb(MailbagVO mailbagVO) throws SystemException {

		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_BOOKING_COUNT);
		query.setParameter(++index, mailbagVO.getCompanyCode());
		query.setParameter(++index, mailbagVO.getMailSequenceNumber());

		return (query.getSingleResult(getLongMapper("MALBKG")) > 0 ? true
				: false);
	}


	/**
	 * @author A-8061
	 * @param shipmentSummaryVO
	 * @param mailFlightSummaryVO
	 * @return
	 * @throws SystemException
	 */
	public ScannedMailDetailsVO findAWBAttachedMailbags(ShipmentSummaryVO shipmentSummaryVO,
														MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
		int index = 0;
		Query query = null;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		query = getQueryManager().createNamedNativeQuery(
				FIND_AWBATTACHED_MAILS);
		query.setParameter(++index, mailFlightSummaryVO.getCompanyCode());
		query.setParameter(++index, shipmentSummaryVO.getOwnerId());
		query.setParameter(++index, shipmentSummaryVO.getMasterDocumentNumber());
		query.setParameter(++index, shipmentSummaryVO.getSequenceNumber());
		query.setParameter(++index, shipmentSummaryVO.getDuplicateNumber());

		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs =
				query.getResultList(new AWBAttachedMailbagDetailsMapper());

		if(scannedMailDetailsVOs != null && !scannedMailDetailsVOs.isEmpty()){
			scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs).get(0);
		}
		return scannedMailDetailsVO;


	}
	/**
	 * @author A-5526
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 */

	public Collection<ContainerVO> findEmptyULDsInAssignedFlight(OperationalFlightVO operationalFlightVO) throws SystemException{

		log.entering("MailTrackingDefaultsSqlDAO", "findULDsInInboundFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_EMPTY_ULDS_IN_MAIL_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());

		qry.setSensitivity(true);
		log.exiting("MailTrackingDefaultsSqlDAO", "findULDsInInboundFlight");
		return qry.getResultList(new ContainerMapper());
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.
	 *							MailTrackingDefaultsDAO#listCarditDsnDetails(com.ibsplc.
	 *							icargo.business.mail.operations.vo.DSNEnquiryFilterVO)
	 *	Added by 			: 	A-8164 on 04-Sep-2019
	 * 	Used for 			:	List Cardit DSN Details
	 *	Parameters			:	@param dsnEnquiryFilterVO
	 *	Parameters			:	@return
	 * @throws SystemException
	 */
	public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO)
			throws SystemException {
		log.entering("MailTrackingDefaultsSqlDAO", "listCarditDsnDetails");
		StringBuffer masterQuery = new StringBuffer();
		masterQuery.append(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String queryString = getQueryManager().getNamedNativeQueryString(FIND_CARDIT_DSN_DETAILS);
		masterQuery.append(queryString);
		PageableNativeQuery<DSNVO> pgNativeQuery =
				new PageableNativeQuery<DSNVO>(dsnEnquiryFilterVO.getPageSize(),-1, masterQuery.toString(),
						new CarditDsnEnquiryMapper());
		int index = 0;
		if(dsnEnquiryFilterVO.getFlightType()!=null && dsnEnquiryFilterVO.getFlightType().trim().length()>0){
			if(MailConstantsVO.FLIGHT_TYP_OPR.equals(dsnEnquiryFilterVO.getFlightType()))
				pgNativeQuery.append("      INNER JOIN MALFLT ASGFLT "
						+ "ON ASGFLT.CMPCOD  = MALMST.CMPCOD "
						+ "AND ASGFLT.FLTCARIDR = MALMST.FLTCARIDR "
						+ "AND ASGFLT.FLTNUM    = MALMST.FLTNUM "
						+ "AND ASGFLT.FLTSEQNUM = MALMST.FLTSEQNUM");
		}
		pgNativeQuery.append(" WHERE CSGMST.CMPCOD  = ?");
		pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getCompanyCode());
		if(dsnEnquiryFilterVO.getConsignmentNumber()!=null && dsnEnquiryFilterVO.getConsignmentNumber().trim().length()>0){
			pgNativeQuery.append("AND CSGMST.CSGDOCNUM = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getConsignmentNumber());
		}
		if(dsnEnquiryFilterVO.getConsignmentDate()!=null){
			if(isOracleDataSource()){
				
				pgNativeQuery.append("AND TRUNC(CSGMST.CSGDAT) = ?");
			}
			else {
				pgNativeQuery.append("AND TRUNC(CSGMST.CSGDAT) = cast(? as timestamp)");
			}
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getConsignmentDate().toDisplayDateOnlyFormat());
		}
		if(dsnEnquiryFilterVO.getOoe()!=null && dsnEnquiryFilterVO.getOoe().trim().length()>0){
			pgNativeQuery.append("AND MALMST.ORGEXGOFC  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getOoe());
		}
		if(dsnEnquiryFilterVO.getDoe()!=null && dsnEnquiryFilterVO.getDoe().trim().length()>0){
			pgNativeQuery.append("AND MALMST.DSTEXGOFC  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDoe());
		}
		if(dsnEnquiryFilterVO.getMailCategoryCode()!=null && dsnEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
			pgNativeQuery.append("AND MALMST.MALCTG  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailCategoryCode());
		}
		if(dsnEnquiryFilterVO.getYear()!=0){
			pgNativeQuery.append("AND MALMST.YER  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getYear());
		}
		if(dsnEnquiryFilterVO.getDsn()!=null && dsnEnquiryFilterVO.getDsn().trim().length()>0){
			pgNativeQuery.append("AND MALMST.DSN  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDsn());
		}
		if(dsnEnquiryFilterVO.getMailSubClass()!=null && dsnEnquiryFilterVO.getMailSubClass().trim().length()>0){
			pgNativeQuery.append("AND MALMST.MALSUBCLS  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailSubClass());
		}
		if(dsnEnquiryFilterVO.getMailSubClass()!=null && dsnEnquiryFilterVO.getMailSubClass().trim().length()>0){
			pgNativeQuery.append("AND MALMST.MALSUBCLS  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getMailSubClass());
		}
 		/*if(dsnEnquiryFilterVO.getFromDate()!=null && dsnEnquiryFilterVO.getToDate()!=null){
 			pgNativeQuery.append("AND CSGMST.CSGDAT BETWEEN ? AND ?");
 			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFromDate().toDisplayDateOnlyFormat());
 			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getToDate().toDisplayDateOnlyFormat());
 		}*/
		if (dsnEnquiryFilterVO.getFromDate() != null && dsnEnquiryFilterVO.getToDate() != null) {
			pgNativeQuery.append("AND (TO_CHAR(trunc(CSGMST.CSGDAT),'YYYYMMDD')) BETWEEN ? AND ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getToDate().toSqlDate().toString().replace("-", ""));

		}
		if(dsnEnquiryFilterVO.getAwbAttached()!=null && dsnEnquiryFilterVO.getAwbAttached().trim().length()>0){
			if(MailConstantsVO.FLAG_YES.equals(dsnEnquiryFilterVO.getAwbAttached())){
				pgNativeQuery.append("AND MALMST.MSTDOCNUM  IS NOT NULL");
			}
			else{
				pgNativeQuery.append("AND MALMST.MSTDOCNUM  IS NULL");
			}
		}
 		/*if(dsnEnquiryFilterVO.getCarrierCode()!=null && dsnEnquiryFilterVO.getCarrierCode().trim().length()>0){
 			pgNativeQuery.append("AND TRT.FLTCARCOD  = ?");
 			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getCarrierCode());
 		}
 		if(dsnEnquiryFilterVO.getFlightNumber()!=null && dsnEnquiryFilterVO.getFlightNumber().trim().length()>0){
 			pgNativeQuery.append("AND TRT.FLTNUM  = ?");
 			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightNumber());
 		}
 		if(dsnEnquiryFilterVO.getFlightDate() != null){
 			pgNativeQuery.append("AND TRUNC(TRT.FLTDAT)  = ?");
 			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
 		}*/
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(dsnEnquiryFilterVO.getFlightType())) {
			if(dsnEnquiryFilterVO.getCarrierCode()!=null && dsnEnquiryFilterVO.getCarrierCode().trim().length() > 0)
			{
				pgNativeQuery.append("AND ASGFLT.FLTCARCOD = ?");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getCarrierCode());
			}
			if(dsnEnquiryFilterVO.getFlightNumber()!=null && dsnEnquiryFilterVO.getFlightNumber().trim().length() > 0)
			{
				pgNativeQuery.append("AND ASGFLT.FLTNUM = ?");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getFlightNumber());
			}
			if(dsnEnquiryFilterVO.getFlightDate()!=null)
			{
				pgNativeQuery.append("AND TRUNC(ASGFLT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getFlightDate().toSqlDate().toString());
			}
		}
		else{
			if(dsnEnquiryFilterVO.getCarrierCode()!=null && dsnEnquiryFilterVO.getCarrierCode().trim().length() > 0)
			{
				pgNativeQuery.append("AND TRT.FLTCARCOD = ?");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getCarrierCode());
			}
			if(dsnEnquiryFilterVO.getFlightNumber()!=null && dsnEnquiryFilterVO.getFlightNumber().trim().length() > 0)
			{
				pgNativeQuery.append("AND TRT.FLTNUM = ?");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getFlightNumber());
			}
			if(dsnEnquiryFilterVO.getFlightDate()!=null)
			{
				pgNativeQuery.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				pgNativeQuery.setParameter(++index,dsnEnquiryFilterVO.getFlightDate().toSqlDate().toString());
			}
		}
		if(dsnEnquiryFilterVO.getAirportCode()!=null && dsnEnquiryFilterVO.getAirportCode().trim().length()>0){
			pgNativeQuery.append("AND TRT.POL  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getAirportCode());
		}
		if(dsnEnquiryFilterVO.getContainerNumber()!=null && dsnEnquiryFilterVO.getContainerNumber().trim().length()>0){
			pgNativeQuery.append("AND CSGDTL.ULDNUM  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getContainerNumber());
		}
		if(dsnEnquiryFilterVO.getShipmentPrefix()!=null && dsnEnquiryFilterVO.getShipmentPrefix().trim().length()>0){
			pgNativeQuery.append("AND MALMST.SHPPFX  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getShipmentPrefix());
		}
		if(dsnEnquiryFilterVO.getPaCode()!=null && dsnEnquiryFilterVO.getPaCode().trim().length()>0){
			pgNativeQuery.append("AND MALMST.POACOD  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getPaCode());
		}
		if(dsnEnquiryFilterVO.getDocumentNumber()!=null && dsnEnquiryFilterVO.getDocumentNumber().trim().length()>0){
			pgNativeQuery.append("AND MALMST.MSTDOCNUM  = ?");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getDocumentNumber());
		}
		if(dsnEnquiryFilterVO.getRdt()!=null){
			String rqdDlvTime=dsnEnquiryFilterVO.getRdt().toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					pgNativeQuery.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					pgNativeQuery.append(" AND MALMST.REQDLVTIM = ?");
				}
			}
			//pgNativeQuery.append("AND MALMST.REQDLVTIM = to_timestamp(?, 'mm/dd/yyyy hh24:mi:ss.ff3')");
			pgNativeQuery.setParameter(++index, dsnEnquiryFilterVO.getRdt());
		}
		if(dsnEnquiryFilterVO.getStatus()!=null && dsnEnquiryFilterVO.getStatus().trim().length()>0){
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(dsnEnquiryFilterVO.getStatus())){
				pgNativeQuery.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
			}
			else if(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED.equals(dsnEnquiryFilterVO.getStatus())){
				pgNativeQuery.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
			}
		}
		if (isOracleDataSource()) {
			pgNativeQuery.append(
					" )GROUP BY FLTNUM , FLTDAT, CARCOD , ORGEXGOFF, DSTEXGOFF, DSPYER , DSPSRLNUM , MALCTGCOD ,"
							+ "MALSUBCLS , CMPCOD , SHPPFX , MSTDOCNUM , CSGDOCNUM , CSGDAT , POACOD , CONNUM , REQDLVTIM , ACPSTA ");
		} else {
			pgNativeQuery.append(
					" )AS QRY GROUP BY FLTNUM , FLTDAT, CARCOD , ORGEXGOFF, DSTEXGOFF, DSPYER , DSPSRLNUM , MALCTGCOD ,"
							+ "MALSUBCLS , CMPCOD , SHPPFX , MSTDOCNUM , CSGDOCNUM , CSGDAT , POACOD , CONNUM , REQDLVTIM , ACPSTA ");
		}
		pgNativeQuery.append(" ORDER BY CSGDOCNUM, CSGDAT, CONNUM, DSPSRLNUM");
		pgNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgNativeQuery.getPage(dsnEnquiryFilterVO.getPageNumber());

	}

	/**
	 * @author A-6986
	 * @param findIncentiveConfigurationDetails
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails (
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO)
			throws SystemException, PersistenceException{
		log.entering(MODULE, "findIncentiveConfigurationDetails");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_INCENTIVE_CONFIGURATION_DETAILS);
		int index = 0;

		qry.setParameter(++index, incentiveConfigurationFilterVO.getCompanyCode());
		qry.setParameter(++index, incentiveConfigurationFilterVO.getPaCode());

		Collection<IncentiveConfigurationVO>  incentiveVOs = qry
				.getResultList(new MailIncentiveDetailsMultiMapper());

		return incentiveVOs;

	}

	/**
	 * @author A-5526
	 * @param findRunnerFlights
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findOutboundRunnerFlights");
		String qry = null;
		String additionalQuery = null;
		PageableNativeQuery<RunnerFlightVO> pgqry = null;
		String listType = null;
		if (RunnerFlightVO.RUN_DIRECTION_OUTBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_OUTBOUNDRUNNERFLIGHTS);
			listType = RunnerFlightVO.RUN_DIRECTION_OUTBOUND;
		} else if (RunnerFlightVO.RUN_DIRECTION_INBOUND.equals(runnerFlightFilterVO.getRunDirection())) {
			if (RunnerFlightVO.LISTTYPE_INBOUND.equals(runnerFlightFilterVO.getInboundListType())) {
				qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_INBOUNDRUNNERFLIGHTS);
				listType = RunnerFlightVO.LISTTYPE_INBOUND;
			} else if (RunnerFlightVO.LISTTYPE_REFUSAL.equals(runnerFlightFilterVO.getInboundListType())) {
				qry = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS);
				additionalQuery = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_FIND_REFUSALRUNNERFLIGHTS_PART2);
				listType = RunnerFlightVO.LISTTYPE_REFUSAL;
			}
		}
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append("SELECT RESULT_TABLE.* , dense_rank() over( order by FLTDAT,FLTCARIDR,FLTNUM,FLTSEQNUM,FLTSEQNUM,CMPCOD desc) AS RANK FROM ( ");
		rankQuery.append(qry);
		pgqry = new ListRunnerFlightFilterQuery(runnerFlightFilterVO.getPageSize(),
				runnerFlightFilterVO.getTotalRecordCount(), isOracleDataSource(), runnerFlightFilterVO, rankQuery.toString(),additionalQuery,
				new RunnerFlightMultiMapper(listType));

		log.exiting(MODULE, "findOutboundRunnerFlights");
		return pgqry.getPage(runnerFlightFilterVO.getPageNumber());
	}

	/**
	 * @author A-5526
	 * @param findContainerDetails
	 * @return ContainerVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerVO findContainerDetails(RunnerFlightVO runnerFlightVO,
											RunnerFlightULDVO runnerFlightULDVO) throws SystemException, PersistenceException{
		Query qry = null;
		int indexForSum = 0;
		ContainerVO containerVO=null;
		if(runnerFlightVO.getFlightSequenceNumber()>0){
			qry = getQueryManager().createNamedNativeQuery(
					FIND_BAGCOUNT_FORFLIGHT);
			qry.setParameter(++indexForSum, runnerFlightVO
					.getCompanyCode());
			qry.setParameter(++indexForSum, runnerFlightULDVO.getUldNumber());
			qry.setParameter(++indexForSum, runnerFlightVO.getCarrierId());
			qry.setParameter(++indexForSum, runnerFlightVO
					.getFlightNumber());
			qry.setParameter(++indexForSum, runnerFlightVO
					.getFlightSequenceNumber());
			qry.setParameter(++indexForSum, 
					runnerFlightVO.getLegSerialNumber() != null && !runnerFlightVO.getLegSerialNumber().isEmpty() ? 
							Integer.parseInt(runnerFlightVO.getLegSerialNumber()) : 0);

		}else{
			qry = getQueryManager().createNamedNativeQuery(
					FIND_BAGCOUNT_FORDESTINATION);
			qry.setParameter(++indexForSum, runnerFlightVO
					.getCompanyCode());
			qry.setParameter(++indexForSum,
					runnerFlightVO.getAirportCode());
			qry.setParameter(++indexForSum, runnerFlightVO
					.getCarrierId());
			qry.setParameter(++indexForSum, runnerFlightULDVO
					.getUldNumber());
		}
		containerVO = qry.getSingleResult(new BagCountMapper());

		return containerVO;

	}

	/**
	 * @author A-8464
	 * @param findCloseoutDate
	 * @return closeoutdate
	 *
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Timestamp findCloseoutDate(String mailbagId) throws SystemException, PersistenceException {
		log.entering(MODULE, "findCloseoutDate");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_CLOSEOUT_DATE);
		int index = 0;

		qry.setParameter(++index, mailbagId);
		log.exiting(MODULE, "findCloseoutDate");
		return qry.getSingleResult(getTimestampMapper("CLNDAT"));

	}


	/**
	 * @author A-8464
	 * @param findServiceStandard
	 * @return serviceStandard
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public int findServiceStandard(MailbagVO mailbagVo) throws SystemException, PersistenceException {
		log.entering(MODULE, "findServiceStandard");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_SERVICE_STANDARD);
		int index = 0;
		/*if (mailbagVo.getCompanyCode()!=null && mailbagVo.getCompanyCode().trim().length()>0) {
			qry.append(" AND SRVLVL.CMPCOD = ?");
			qry.setParameter(++index, mailbagVo.getCompanyCode());
		}
		if (mailbagVo.getPaCode()!=null && mailbagVo.getPaCode().trim().length()>0) {
			qry.append(" AND SRVLVL.POACOD = ?");
			qry.setParameter(++index, mailbagVo.getPaCode());
		}
		if (mailbagVo.getMailCategoryCode()!=null && mailbagVo.getMailCategoryCode().trim().length()>0) {
			qry.append(" AND SRVLVL.MALCTG = ?");
			qry.setParameter(++index, mailbagVo.getMailCategoryCode());
		}
		if (mailbagVo.getMailClass()!=null && mailbagVo.getMailClass().trim().length()>0) {
			qry.append(" AND SRVLVL.MALCLS = ?");
			qry.setParameter(++index, mailbagVo.getMailClass());
		}
		if (mailbagVo.getMailSubclass()!=null && mailbagVo.getMailSubclass().trim().length()>0) {
			qry.append(" AND SRVLVL.MALSUBCLS = ?");
			qry.setParameter(++index, mailbagVo.getMailSubclass());
		}*/


		if (mailbagVo.getCompanyCode()!=null && mailbagVo.getCompanyCode().trim().length()>0) {

			qry.setParameter(++index, mailbagVo.getCompanyCode());
		}
		if (mailbagVo.getPaCode()!=null && mailbagVo.getPaCode().trim().length()>0) {

			qry.setParameter(++index, mailbagVo.getPaCode());
		}
		if (mailbagVo.getOrigin()!=null && mailbagVo.getOrigin().trim().length()>0) {

			qry.setParameter(++index, mailbagVo.getOrigin());
		}
		if (mailbagVo.getDestination()!=null && mailbagVo.getDestination().trim().length()>0) {

			qry.setParameter(++index, mailbagVo.getDestination());
		}
		if (mailbagVo.getMailServiceLevel()!=null && mailbagVo.getMailServiceLevel().trim().length()>0) {

			qry.setParameter(++index, mailbagVo.getMailServiceLevel());
		}
		if(mailbagVo.getConsignmentDate() != null ){
			qry.append("AND  ?  BETWEEN SRVSTDCFG.VLDFRMDAT AND SRVSTDCFG.VLDTOODAT ");
			qry.setParameter(++index, mailbagVo.getConsignmentDate());
		}

		int serviceStandard = 0;
		String serviceStdString = qry.getSingleResult(getStringMapper("SRVSTD"));
		if(serviceStdString!=null && serviceStdString.trim().length()>0){
			serviceStandard = Integer.parseInt(serviceStdString);
		}
		log.exiting(MODULE, "findServiceStandard");
		return serviceStandard;
	}
	/**
	 * @author A-7929
	 * @param mailbagEnquiryFilterVO,pageNumber
	 * @return Page<MailbagVO>
	 * @throws SystemException
	 */
	public Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException {

		log.entering(MODULE, "findMailbagsForTruckFlight");
		Page<MailbagVO> mailbagVos = null;

		int index = 0;
		int defaultPageSize;
		defaultPageSize=10;
		if(mailbagEnquiryFilterVO.getDefaultPageSize()!=0){
			defaultPageSize=mailbagEnquiryFilterVO.getDefaultPageSize();
		}
		int totalRecords=mailbagEnquiryFilterVO.getTotalRecords();

		String  baseQry = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_FIND_MAILBAGWITHCONSIGNMENTFORTRUCK);

		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry);


		PageableNativeQuery<MailbagVO> qry =new PageableNativeQuery<MailbagVO>(defaultPageSize,totalRecords,rankQuery.toString(),
				new CarditMailsMapper());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getCarrierCode()); //flight carrier code
		qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightDate());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getOrigin());  //pol

		if("C".equals(mailbagEnquiryFilterVO.getFilterType())){

			if(mailbagEnquiryFilterVO.getMailbagId() != null && mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
				qry.append("AND MALMST.MALIDR = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
			}

			if(mailbagEnquiryFilterVO.getOoe() != null && mailbagEnquiryFilterVO.getOoe().trim().length()>0){
				qry.append("AND MALMST.ORGEXGOFC = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
			}

			if(mailbagEnquiryFilterVO.getDoe() != null && mailbagEnquiryFilterVO.getDoe().trim().length()>0){
				qry.append("AND MALMST.DSTEXGOFC = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
			}


			if(mailbagEnquiryFilterVO.getMailCategoryCode() != null && mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
				qry.append("AND MALMST.MALCTG = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
			}

			if(mailbagEnquiryFilterVO.getMailSubclass() != null && mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0){
				qry.append("AND MALMST.MALSUBCLS = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
			}

			if(mailbagEnquiryFilterVO.getYear() != null && mailbagEnquiryFilterVO.getYear().trim().length()>0){
				qry.append("AND MALMST.YER = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getYear());
			}

			if(mailbagEnquiryFilterVO.getDespatchSerialNumber() != null && mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
				qry.append("AND MALMST.DSN = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
			}

			if(mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null && mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
				qry.append("AND MALMST.RSN = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
			}

			if(mailbagEnquiryFilterVO.getConsigmentNumber() != null && mailbagEnquiryFilterVO.getConsigmentNumber().trim().length()>0){
				qry.append("AND CSGMST.CSGDOCNUM = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
			}

			if(mailbagEnquiryFilterVO.getFromDate() != null && mailbagEnquiryFilterVO.getFromDate().trim().length()>0){

				//qry.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
				qry.append("AND TRUNC(CSGMST.CSGDAT) >= ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFromDate());
			}

			if(mailbagEnquiryFilterVO.getToDate() != null && mailbagEnquiryFilterVO.getToDate().trim().length()>0){
				//qry.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
				qry.append("AND TRUNC(CSGMST.CSGDAT) <= ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getToDate());
			}

			if(mailbagEnquiryFilterVO.getPacode() != null && mailbagEnquiryFilterVO.getPacode().trim().length()>0){
				qry.append("AND MALMST.POACOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getPacode());
			}

			if(mailbagEnquiryFilterVO.getFlightNumber() != null && mailbagEnquiryFilterVO.getFlightNumber().trim().length()>0){
				qry.append("AND TRT.FLTNUM = ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
			}

			if(mailbagEnquiryFilterVO.getFlightDate() != null ){
				//qry.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.append("AND TRUNC(TRT.FLTDAT) = ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightDate());
			}



			if(mailbagEnquiryFilterVO.getUpliftAirport() != null && mailbagEnquiryFilterVO.getUpliftAirport().trim().length()>0){
				qry.append("TRT.POL = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			}


			if(mailbagEnquiryFilterVO.getUldNumber() != null && mailbagEnquiryFilterVO.getUldNumber().trim().length()>0){
				qry.append("AND CSGDTL.ULDNUM = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getUldNumber());
			}


			if(mailbagEnquiryFilterVO.getOriginAirportCode() != null && mailbagEnquiryFilterVO.getOriginAirportCode().trim().length()>0){
				qry.append("AND MALMST.ORGCOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
			}


			if(mailbagEnquiryFilterVO.getDestinationAirportCode() != null && mailbagEnquiryFilterVO.getDestinationAirportCode().trim().length()>0){
				qry.append("AND MALMST.DSTCOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
			}
			if(mailbagEnquiryFilterVO.getCurrentStatus()!= null && mailbagEnquiryFilterVO.getCurrentStatus().trim().length()>0){
				if("ACP".equals(mailbagEnquiryFilterVO.getCurrentStatus())){
					qry.append(" AND MALMST.MALSTA  NOT IN('NEW','BKD','CAN')");
				}
				else if("CAP".equals(mailbagEnquiryFilterVO.getCurrentStatus())){
					qry.append(" AND MALMST.MALSTA IN('NEW','BKD','CAN')");
				}

			}

		}
		qry.append("ORDER BY CSGMST.CSGDOCNUM,CSGMST.CSGDAT,CSGDTL.ULDNUM,MALMST.MALIDR");

		qry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.log(Log.INFO, "Query: ", qry);
		return qry.getPage(pageNumber);


	}


	/**
	 * @author A-7929
	 * @param mailbagEnquiryFilterVO,pageNumber
	 * @return Page<MailbagVO>
	 * @throws SystemException
	 */
	public Page<MailbagVO> findAllMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
														 int pageNumber) throws SystemException {

		log.entering(MODULE, "findMailbagsForTruckFlight");
		Page<MailbagVO> mailbagVos = null;
		int index = 0;
		int defaultPageSize;
		defaultPageSize=10;
		if(mailbagEnquiryFilterVO.getDefaultPageSize()!=0){
			defaultPageSize=mailbagEnquiryFilterVO.getDefaultPageSize();
		}
		int totalRecords=mailbagEnquiryFilterVO.getTotalRecords();
		String  baseQry = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_FIND_ALLMAILBAGWITHCONSIGNMENTFORTRUCK);

		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(baseQry);


		PageableNativeQuery<MailbagVO> qry =new PageableNativeQuery<MailbagVO>(defaultPageSize,totalRecords, rankQuery.toString(),
				new CarditMailsMapper());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getScanPort());//scanport
		qry.setParameter(++index, mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
		qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightCarrierIdr()); //flight carrier idr
		qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightSequenceNumber()); //flight sequence number

		if("L".equals(mailbagEnquiryFilterVO.getFilterType())){

			if(mailbagEnquiryFilterVO.getMailbagId() != null && mailbagEnquiryFilterVO.getMailbagId().trim().length()>0){
				qry.append("AND MST.MALIDR = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailbagId());
			}

			if(mailbagEnquiryFilterVO.getOoe() != null && mailbagEnquiryFilterVO.getOoe().trim().length()>0){
				qry.append("AND MST.ORGEXGOFC = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getOoe());
			}


			if(mailbagEnquiryFilterVO.getDoe() != null && mailbagEnquiryFilterVO.getDoe().trim().length()>0){
				qry.append("AND MST.DSTEXGOFC = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDoe());
			}


			if(mailbagEnquiryFilterVO.getMailCategoryCode() != null && mailbagEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
				qry.append("AND MST.MALCTG = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailCategoryCode());
			}

			if(mailbagEnquiryFilterVO.getMailSubclass() != null && mailbagEnquiryFilterVO.getMailSubclass().trim().length()>0){
				qry.append("AND MST.MALSUBCLS = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getMailSubclass());
			}

			if(mailbagEnquiryFilterVO.getYear() != null && mailbagEnquiryFilterVO.getYear().trim().length()>0){
				qry.append("AND MST.YER = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getYear());
			}

			if(mailbagEnquiryFilterVO.getDespatchSerialNumber() != null && mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
				qry.append("AND MST.DSN = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDespatchSerialNumber());
			}

			if(mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null && mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
				qry.append("AND MST.RSN = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getReceptacleSerialNumber());
			}

			if(mailbagEnquiryFilterVO.getConsigmentNumber() != null && mailbagEnquiryFilterVO.getConsigmentNumber().trim().length()>0){
				qry.append(" AND MST.CSGDOCNUM = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getConsigmentNumber());
			}

			if(mailbagEnquiryFilterVO.getFromDate() != null && mailbagEnquiryFilterVO.getFromDate().trim().length()>0){

				//qry.append(" AND TRUNC(CSGMST.CSGDAT) >= TO_DATE(?, 'yyyy-MM-dd') ");
				qry.append("AND TRUNC(MST.SCNDAT) >= ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFromDate());
			}

			if(mailbagEnquiryFilterVO.getToDate() != null && mailbagEnquiryFilterVO.getToDate().trim().length()>0){
				//qry.append(" AND TRUNC(CSGMST.CSGDAT) <= TO_DATE(?, 'yyyy-MM-dd') ");
				qry.append("AND TRUNC(MST.SCNDAT) <= ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getToDate());
			}

			if(mailbagEnquiryFilterVO.getPacode() != null && mailbagEnquiryFilterVO.getPacode().trim().length()>0){
				qry.append("AND MST.POACOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getPacode());
			}

			if(mailbagEnquiryFilterVO.getFlightNumber() != null && mailbagEnquiryFilterVO.getFlightNumber().trim().length()>0){
				qry.append("AND MST.FLTNUM = ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightNumber());
			}

			if(mailbagEnquiryFilterVO.getFlightDate() != null ){
				//qry.append("AND TRUNC(TRT.FLTDAT) = TO_DATE(?, 'yyyy-MM-dd')");
				qry.append("AND TRUNC(FLTMST.FLTDAT) = ?");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getFlightDate());
			}



			if(mailbagEnquiryFilterVO.getUpliftAirport() != null && mailbagEnquiryFilterVO.getUpliftAirport().trim().length()>0){
				qry.append("MST.SCNPRT = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			}


			if(mailbagEnquiryFilterVO.getUldNumber() != null && mailbagEnquiryFilterVO.getUldNumber().trim().length()>0){
				qry.append("AND MST.CONNUM = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getUldNumber());
			}


			if(mailbagEnquiryFilterVO.getOriginAirportCode() != null && mailbagEnquiryFilterVO.getOriginAirportCode().trim().length()>0){
				qry.append("AND MST.ORGCOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getOriginAirportCode());
			}


			if(mailbagEnquiryFilterVO.getDestinationAirportCode() != null && mailbagEnquiryFilterVO.getDestinationAirportCode().trim().length()>0){
				qry.append("AND MST.DSTCOD = ? ");
				qry.setParameter(++index, mailbagEnquiryFilterVO.getDestinationAirportCode());
			}



		}


		qry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		log.log(Log.INFO, "Query: ", qry);
		return qry.getPage(pageNumber);
	}


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findRotingIndex(java.lang.String, java.lang.String)
	 *	Added by 			: A-7531 on 30-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param routeIndex
	 *	Parameters	:	@param companycode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO)
			throws SystemException, PersistenceException {
		int index= 0;
		Collection<RoutingIndexVO> routingIndexVOs=null;

		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_ROUTING_INDEX);
		String modifiedStr1 = null;
		String modifiedStr2 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "AND syspar.parval = arlmst.arlidr";
		}
		else
		{
			modifiedStr1 = "AND arlmst.arlidr :: varchar = syspar.parval";
		}
		if (isOracleDataSource())
		{
			modifiedStr2 = "and ? BETWEEN idx.pldeffdat-1 and idx.plddisdat";

		}
		else
		{
			modifiedStr2 = "and ? BETWEEN date(idx.pldeffdat)-1 and date(idx.plddisdat)";
		}

		qry = String.format(qry,modifiedStr1,modifiedStr2);
		Query qery = getQueryManager().createNativeQuery(qry);
		qery.setParameter(++index, routingIndexVO.getRoutingIndex());
		qery.setParameter(++index,routingIndexVO.getCompanyCode());
		if (routingIndexVO.getScannedDate() == null) {
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			routingIndexVO.setScannedDate(currentDate);
		}
		if (isOracleDataSource())
		{
			qery.setParameter(++index, routingIndexVO.getScannedDate().toDisplayDateOnlyFormat());
		}
		else
		{
			qery.setParameter(++index, routingIndexVO.getScannedDate());
		}
//		qry.setParameter(++index, routingIndexVO.getRoutingIndex());
//		qry.setParameter(++index,routingIndexVO.getCompanyCode());
		routingIndexVOs=qery
				.getResultList(new RoutingIndexDetailsMapper());

		return routingIndexVOs;
	}


	/**
	 *
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO, int pageNumber) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "listForceMajeureApplicableMails");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords=filterVO.getTotalRecords();
		boolean isFltExist = false;
		String sortField = "";
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query qry = null;
		int index = 0;
		if(MailConstantsVO.FLIGHT_TYP_OPR.equals(filterVO.getSource())){
			qry = getQueryManager().createNamedNativeQuery(
					MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_OPS);
			rankQuery.append(qry);
			pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize,totalRecords,rankQuery.toString(),
					new ForceMajeureRequestMapper());
			pgqry.setParameter(++index, filterVO.getCompanyCode());
			if(filterVO.getPoaCode() != null && filterVO.getPoaCode().trim().length() > 0){
				pgqry.append(" AND MST.POACOD = ? ");
				pgqry.setParameter(++index, filterVO.getPoaCode());
			}
			if(filterVO.getOrginAirport() != null && filterVO.getOrginAirport().trim().length() > 0){
				pgqry.append(" AND MST.ORGCOD = ? ");
				pgqry.setParameter(++index, filterVO.getOrginAirport());
			}
			if(filterVO.getDestinationAirport() != null && filterVO.getDestinationAirport().trim().length() > 0 ){
				pgqry.append(" AND MST.DSTCOD = ? ");
				pgqry.setParameter(++index, filterVO.getDestinationAirport());
			}
			if(filterVO.getCarrierID() > 0){
				pgqry.append(" AND HIS.FLTCARIDR = ? ");
				pgqry.setParameter(++index, filterVO.getCarrierID());
			}
			if(filterVO.getFlightNumber() != null && filterVO.getFlightDate() != null
					&& filterVO.getFlightNumber().trim().length() > 0 ){
				isFltExist = true;
				pgqry.append(" AND HIS.FLTNUM = ? ");
				String fltnumber=filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
				pgqry.setParameter(++index, fltnumber);
				pgqry.append(" AND HIS.FLTDAT = ? ");
				pgqry.setParameter(++index, filterVO.getFlightDate());
			}
			if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
				pgqry.append(" AND ( HIS.SCNPRT IN (");
				String[] airports = filterVO.getAffectedAirport()
						.split(",");
				StringBuilder builder = new StringBuilder("'");
				for (String airport : airports) {
					builder.append(airport).append("','");
				}
				pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(")) ");
				if(!isFltExist){
					pgqry.append(" AND Trunc(HIS.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter( ++index,filterVO.getFromDate().toDisplayDateOnlyFormat() );
					pgqry.setParameter( ++index, filterVO.getToDate().toDisplayDateOnlyFormat() );
				}
				pgqry.append(" AND HIS.MALHISIDR = (SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM AND SCNPRT = HIS.SCNPRT ) ");
				if(isFltExist){
					pgqry.append(" ) ) ");
				}else{
					pgqry.append(" AND TRUNC(his.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy')  " );
					pgqry.setParameter( ++index,filterVO.getFromDate().toDisplayDateOnlyFormat());
					pgqry.setParameter( ++index, filterVO.getToDate().toDisplayDateOnlyFormat());
				}
			}
			if(filterVO.getViaPoint() != null && filterVO.getViaPoint().trim().length() > 0){
				pgqry.append(" AND ( ( HIS.SCNPRT = ? ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				if(!isFltExist){
					pgqry.append(" AND TRUNC(his.SCNDAT) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter( ++index,filterVO.getFromDate().toDisplayDateOnlyFormat());
					pgqry.setParameter( ++index,filterVO.getToDate().toDisplayDateOnlyFormat());
				}
				pgqry.append(" AND HIS.MALHISIDR = ( SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM AND SCNPRT = HIS.SCNPRT ");
				if(!isFltExist){
					pgqry.append(" AND TRUNC(HIS.SCNDAT) BETWEEN  to_date(?, 'dd-MON-yyyy') AND  to_date(?, 'dd-MON-yyyy') ");
					pgqry.setParameter( ++index,filterVO.getFromDate().toDisplayDateOnlyFormat());
					pgqry.setParameter( ++index,filterVO.getToDate().toDisplayDateOnlyFormat());
				}else{
					pgqry.append(" ) ");
				}
				pgqry.append(" AND MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? ) ");
				//Added by A-8527 for ICRD-337504 starts
				pgqry.append(" OR ( MST.SCNPRT = ? AND  MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? AND MST.MALSTA IN( 'ARR','OFL') ");
				pgqry.append(" AND HIS.MALHISIDR = (SELECT MAX(MALHISIDR)  FROM MALHIS   WHERE CMPCOD  = MST.CMPCOD   AND MALSEQNUM = MST.MALSEQNUM ");
				pgqry.append("	AND SCNPRT = MST.SCNPRT  AND MALSTA = MST.MALSTA  ))");
				//Added by A-8527 for ICRD-337504 Ends
				pgqry.append(" OR ( INSTR(SUBSTR(FLTMST.FLTROU,INSTR(FLTMST.FLTROU,MST.ORGCOD,1)+length(MST.ORGCOD)-length(FLTMST.FLTROU)),? ) > 0 ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				//Added by A-8527 for ICRD-337504 starts
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				//Added by A-8527 for ICRD-337504 Ends
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.append(" AND HIS.MALHISIDR = ( SELECT MAX(MALHISIDR) FROM MALHIS ");
				pgqry.append(" WHERE CMPCOD = MST.CMPCOD AND MALSEQNUM = MST.MALSEQNUM ) AND MST.DSTCOD  <>  ?   ) ");//modified by A-8527 for ICRD-337502
				pgqry.append(" AND  TRUNC(FLTLEG.STA) BETWEEN to_date(?, 'dd-MON-yyyy') AND to_date(?, 'dd-MON-yyyy') ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter( ++index,filterVO.getFromDate().toDisplayDateOnlyFormat());
				pgqry.setParameter( ++index,filterVO.getToDate().toDisplayDateOnlyFormat());
			}
		}else{
			qry = getQueryManager().createNamedNativeQuery(
					MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST_MAILS_FOR_CARDIT);
			rankQuery.append(qry);
			pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize,totalRecords,rankQuery.toString(),
					new ForceMajeureRequestMapper());
			pgqry.setParameter(++index, filterVO.getCompanyCode());
			if(filterVO.getFlightNumber() != null && filterVO.getFlightNumber().trim().length() > 0 ){
				String fltnumber="";
				if(filterVO.getFlightNumber()!=null&& filterVO.getFlightNumber().length()>0 ){
					fltnumber=filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
				}
				pgqry.append(" AND CSGRTG.FLTNUM = ? ");
				pgqry.setParameter(++index, fltnumber);
			}else{
				if(filterVO.getFromDate()!=null){
					pgqry.append(" AND ( TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDDHHMM') >= ?  ");
					index++; pgqry.setParameter(index, DateUtilities.format(filterVO.getFromDate(), "yyyyMMddHHmm"));
					pgqry.append(" OR  TO_CHAR(TRUNC(CSGRTG.LEGSTA),'YYYYMMDDHHMM') >= ?  )");
					index++; pgqry.setParameter(index, DateUtilities.format(filterVO.getFromDate(), "yyyyMMddHHmm"));
				}
				if(filterVO.getToDate()!=null){
					pgqry.append(" AND ( TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDDHHMM') <= ?  ");
					index++; pgqry.setParameter(index, DateUtilities.format(filterVO.getToDate(), "yyyyMMddHHmm"));
					pgqry.append(" OR  TO_CHAR(TRUNC(CSGRTG.LEGSTA),'YYYYMMDDHHMM') <= ?  )");
					index++; pgqry.setParameter(index, DateUtilities.format(filterVO.getToDate(), "yyyyMMddHHmm"));
				}
			}
			if(filterVO.getCarrierID() > 0){
				pgqry.append(" AND CSGRTG.FLTCARIDR = ? ");
				pgqry.setParameter(++index, filterVO.getCarrierID());
			}
			if(filterVO.getFlightDate() != null){

				pgqry.append("AND TO_NUMBER(TO_CHAR(TRUNC(CSGRTG.FLTDAT),'YYYYMMDD')) = ? ");
				pgqry.setParameter(++index, Integer.parseInt(filterVO.getFlightDate().toStringFormat("yyyyMMdd").substring(0, 8)));
			}
			if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
				pgqry.append(" AND ( CSGRTG.POL IN (");
				String[] airports = filterVO.getAffectedAirport()
						.split(",");
				StringBuilder builder = new StringBuilder("'");
				for (String airport : airports) {
					builder.append(airport).append("','");
				}
				pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");
				pgqry.append(" OR CSGRTG.POU IN (").append(builder.toString().substring(0,builder.length()-2).trim());
				pgqry.append(" ) )");
			}
			if(filterVO.getOrginAirport() != null && filterVO.getOrginAirport().trim().length() > 0){
				pgqry.append(" AND MST.ORGCOD = ? ");
				pgqry.setParameter(++index, filterVO.getOrginAirport());
			}
			if(filterVO.getDestinationAirport() != null && filterVO.getDestinationAirport().trim().length() > 0){
				pgqry.append(" AND MST.DSTCOD = ? ");
				pgqry.setParameter(++index, filterVO.getDestinationAirport());
			}
			if(filterVO.getViaPoint() != null && filterVO.getViaPoint().trim().length() > 0 ){
				pgqry.append(" AND ( MST.ORGCOD  <>  ?  AND  MST.DSTCOD <> ? AND CSGRTG.POU = ? ) ");
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
				pgqry.setParameter(++index, filterVO.getViaPoint());
			}
			if(filterVO.getPoaCode() != null && filterVO.getPoaCode().trim().length() > 0){
				pgqry.append(" AND MST.POACOD = ? ");
				pgqry.setParameter(++index, filterVO.getPoaCode());
			}
			if ((filterVO.getScanType() != null) && (filterVO.getScanType().trim().length() > 0) && (!filterVO.getScanType().contains("ALL"))) {
				if ( filterVO.getScanType().contains("RCV") &&
						(!filterVO.getScanType().contains("DLV")) && (!filterVO.getScanType().contains("LAT"))) {
					pgqry.append(" AND CSGRTG.POL =MST.ORGCOD ");
					if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
						pgqry.append(" AND  MST.ORGCOD IN (");
						String[] airports = filterVO.getAffectedAirport()
								.split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");

					}
				}else if ( filterVO.getScanType().contains("LOD") &&
						(!filterVO.getScanType().contains("DLV")) && (!filterVO.getScanType().contains("LAT"))) {
					pgqry.append(" AND CSGRTG.POL <>MST.DSTCOD ");
					if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
						pgqry.append(" AND  CSGRTG.POL IN (");
						String[] airports = filterVO.getAffectedAirport()
								.split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");

					}
				}
				else if (((filterVO.getScanType().contains("DLV")) || (filterVO.getScanType().contains("LAT"))) &&
						(!filterVO.getScanType().contains("LOD")) && (!filterVO.getScanType().contains("RCV"))) {
					pgqry.append(" AND CSGRTG.POU =MST.DSTCOD ");

					if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
						pgqry.append(" AND  MST.DSTCOD IN (");
						String[] airports = filterVO.getAffectedAirport()
								.split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");

					}

				}
				else {
					pgqry.append(" AND CSGRTG.POL <> MST.ORGCOD ");
					pgqry.append(" AND CSGRTG.POU <> MST.DSTCOD ");
					if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
						pgqry.append(" AND  MST.DSTCOD NOT IN (");
						String[] airports = filterVO.getAffectedAirport()
								.split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");

					}
					if(filterVO.getAffectedAirport() != null && filterVO.getAffectedAirport().trim().length() > 0){
						pgqry.append(" AND  MST.ORGCOD NOT IN (");
						String[] airports = filterVO.getAffectedAirport()
								.split(",");
						StringBuilder builder = new StringBuilder("'");
						for (String airport : airports) {
							builder.append(airport).append("','");
						}
						pgqry.append(builder.toString().substring(0,builder.length()-2).trim()).append(") ");

					}

				}
			}
			pgqry.append(" group by MST.CMPCOD,MST.MALIDR,MST.MALSEQNUM,MST.SCNPRT,ARL.TWOAPHCOD,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.OPRSTA,MST.WGT,MST.ORGCOD,MST.DSTCOD,MST.CSGDOCNUM,MST.SCNUSR");

		}
		if (((filterVO.getSortingField() == null) || ("".equals(filterVO.getSortingField()))) && (
				(filterVO.getSortOrder() == null) || ("".equals(filterVO.getSortOrder())))) {
			pgqry.append( " ORDER BY MALIDR ASC ");//Added for ICRD-338555
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		else {
			if ("mailID".equals(filterVO.getSortingField()))
				sortField = "MALIDR";
			else if ("airportCode".equals(filterVO.getSortingField()))
				sortField = "SCNPRT";
			else if ("flightNumber".equals(filterVO.getSortingField()))
				sortField = "FLTNUM";
			else if ("flightDate".equals(filterVO.getSortingField()))
				sortField = "FLTDAT";
			else if ("type".equals(filterVO.getSortingField()))
				sortField = "OPRSTA";
			else if ("weight".equals(filterVO.getSortingField()))
				sortField = "WGT";
			else if ("originAirport".equals(filterVO.getSortingField()))
				sortField = "ORGARPCOD";
			else if ("destinationAirport".equals(filterVO.getSortingField()))
				sortField = "DSTARPCOD";
			else if ("consignmentDocNumber".equals(filterVO.getSortingField()))
				sortField = "CSGDOCNUM";
			else if ("lastUpdatedUser".equals(filterVO.getSortingField())) {
				sortField = "LSTUPDUSR";
			}
			if ("ASC".equals(filterVO.getSortOrder())){
				pgqry.append( "ORDER BY  " + sortField + " ASC ");
			}else if ("DESC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY  " + sortField + " DESC ");
			}
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pgqry.getPage(pageNumber);
	}


	/**
	 *
	 */
	public String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
			throws  SystemException,PersistenceException{
		log.entering(MODULE, "saveForceMajeureRequest");
		String outPar = "";
		LocalDate dummyDate =new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(MailConstantsVO.DUMMY_DATE_FOR_FMR);
		Procedure burstProcedure = getQueryManager()
				.createNamedNativeProcedure(MAIL_OPERATIONS_SAVE_FORCE_MAJUERE);
		int index = 0;
		burstProcedure.setParameter(++index,filterVO.getCompanyCode());
		burstProcedure.setParameter(++index,filterVO.getOrginAirport());
		burstProcedure.setParameter(++index,filterVO.getDestinationAirport());
		burstProcedure.setParameter(++index,filterVO.getViaPoint());
		burstProcedure.setParameter(++index,filterVO.getAffectedAirport());
		burstProcedure.setParameter(++index,filterVO.getPoaCode());
		burstProcedure.setParameter(++index,filterVO.getCarrierID());
		//Modified by A-8527 for ICRD-325884 starts
		String fltnumber="";
		if(filterVO.getFlightNumber()!=null&& filterVO.getFlightNumber().length()>0 ){
			fltnumber=filterVO.getFlightNumber().substring(3, filterVO.getFlightNumber().length());
		}
		//Modified by A-8527 for ICRD-325884 starts
		burstProcedure.setParameter(++index,fltnumber);
		if(filterVO.getFlightDate() != null)
			burstProcedure.setParameter(++index,filterVO.getFlightDate().toSqlDate());
		else
			burstProcedure.setParameter(++index,dummyDate.toSqlDate());
		if(filterVO.getFromDate() != null)
			burstProcedure.setParameter(++index, DateUtilities.format(filterVO.getFromDate(), "yyyyMMddHHmm"));
		else
			burstProcedure.setParameter(++index,DateUtilities.format(dummyDate, "yyyyMMddHHmm"));
		if(filterVO.getToDate() != null)
			burstProcedure.setParameter(++index, DateUtilities.format(filterVO.getToDate(), "yyyyMMddHHmm"));
		else
			burstProcedure.setParameter(++index,DateUtilities.format(dummyDate, "yyyyMMddHHmm"));
		burstProcedure.setParameter(++index,filterVO.getFilterParameters());
		burstProcedure.setParameter(++index,filterVO.getReqRemarks());
		burstProcedure.setParameter(++index,filterVO.getSource());
		burstProcedure.setParameter(++index,filterVO.getLastUpdatedUser());
		burstProcedure.setParameter(++index,filterVO.getCurrentAirport());
		burstProcedure.setParameter(++index,filterVO.getScanType());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		outPar = (String) burstProcedure.getParameter(index);
		log.log(Log.FINE, "outParameter is ", outPar);
		log.exiting(MODULE, "saveForceMajeureRequest");
		return outPar;
	}


	/**
	 *
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "listForceMajeureDetails");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords=filterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		String sortField="";
		Query qry = null;
		int index = 0;
		qry = getQueryManager().createNamedNativeQuery(
				MAIL_OPERATIONS_LIST_FORCE_MAJEURE_REQUEST);
		rankQuery.append(qry);
		pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize,totalRecords,rankQuery.toString(),
				new ForceMajeureRequestMapper());
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getForceMajeureID());
		if(filterVO.getAirportCode()!=null && filterVO.getAirportCode().trim().length()>0) {
			pgqry.append(" AND FORC.ARPCOD= ? ");
			pgqry.setParameter(++index, filterVO.getAirportCode());
		}
		if(filterVO.getFlightNumber()!=null && filterVO.getFlightNumber().trim().length()>0) {
			pgqry.append(" AND FORC.FLTNUM= ? ");
			pgqry.setParameter(++index, filterVO.getFlightNumber());
		}
		if(filterVO.getCarrierCode()!=null && filterVO.getCarrierCode().trim().length()>0) {
			pgqry.append(" AND FORC.FLTCARCOD= ? ");
			pgqry.setParameter(++index, filterVO.getCarrierCode());
		}
		if(filterVO.getFlightDate()!=null) {
			pgqry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(FORC.FLTDAT),'YYYYMMDD')) = ? ");
			pgqry.setParameter(++index, filterVO.getFlightDate().toSqlDate().toString().replace("-", ""));
		}
		if(filterVO.getConsignmentNo()!=null && filterVO.getConsignmentNo().trim().length()>0) {
			pgqry.append(" AND FORC.CSGDOCNUM = ? ");
			pgqry.setParameter(++index, filterVO.getConsignmentNo());
		}
		if(filterVO.getMailbagId()!=null && filterVO.getMailbagId().trim().length()>0) {
			pgqry.append(" AND FORC.MALIDR = ? ");
			pgqry.setParameter(++index, filterVO.getMailbagId());
		}
		if (((filterVO.getSortingField() == null) || ("".equals(filterVO.getSortingField()))) && (
				(filterVO.getSortOrder() == null) || ("".equals(filterVO.getSortOrder())))) {
			pgqry.append( "ORDER BY MALIDR ASC ");//Added for ICRD-338555
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		else {
			if ("mailID".equals(filterVO.getSortingField()))
				sortField = "MALIDR";
			else if ("airportCode".equals(filterVO.getSortingField()))
				sortField = "SCNPRT";
			else if ("flightNumber".equals(filterVO.getSortingField()))
				sortField = "FLTNUM";
			else if ("flightDate".equals(filterVO.getSortingField()))
				sortField = "FLTDAT";
			else if ("type".equals(filterVO.getSortingField()))
				sortField = "OPRSTA";
			else if ("weight".equals(filterVO.getSortingField()))
				sortField = "WGT";
			else if ("originAirport".equals(filterVO.getSortingField()))
				sortField = "ORGARPCOD";
			else if ("destinationAirport".equals(filterVO.getSortingField()))
				sortField = "DSTARPCOD";
			else if ("consignmentDocNumber".equals(filterVO.getSortingField()))
				sortField = "CSGDOCNUM";
			else if ("Forceid".equals(filterVO.getSortingField()))
				sortField = "FORMJRIDR";
			else if ("status".equals(filterVO.getSortingField()))
				sortField = "FORMJRSTA";
			else if ("lastUpdatedUser".equals(filterVO.getSortingField())) {
				sortField = "LSTUPDUSR";
			}
			if ("ASC".equals(filterVO.getSortOrder())){
				pgqry.append( "ORDER BY " + sortField + " ASC ");
			}else if ("DESC".equals(filterVO.getSortOrder())) {
				pgqry.append("ORDER BY  " + sortField + " DESC ");
			}
			pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		}
		return pgqry.getPage(pageNumber);
	}



	/**
	 *
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "listForceMajeureRequestIds");
		PageableNativeQuery<ForceMajeureRequestVO> pgqry = null;
		int pageSize = filterVO.getDefaultPageSize();
		int totalRecords=filterVO.getTotalRecords();
		StringBuilder rankQuery = new StringBuilder(MAILTRACKING_DEFAULTS_ROWNUM_QUERY);
		Query qry = null;
		int index = 0;
		qry = getQueryManager().createNamedNativeQuery(
				MAIL_OPERATIONS_LIST_FORCE_MAJEURE_LOV);
		rankQuery.append(qry);
		pgqry = new PageableNativeQuery<ForceMajeureRequestVO>(pageSize,totalRecords,rankQuery.toString(),
				new ForceMajeureRequestMapper());
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		if(filterVO.getForceMajeureID() != null && filterVO.getForceMajeureID().trim().length() > 0 ){
			pgqry.append(" AND FORMJRIDR LIKE ?");
			pgqry.setParameter(++index, filterVO.getForceMajeureID());
		}
		if(filterVO.getFromDate() != null){
			pgqry.append(" AND TO_NUMBER(TO_CHAR(REQDAT,'YYYYMMDD')) >= ? ");   //Changed by A-8164 for ICRD-316302
			pgqry.setParameter(++index, Integer.parseInt(filterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		if(filterVO.getToDate() != null){
			pgqry.append(" AND TO_NUMBER(TO_CHAR(REQDAT,'YYYYMMDD')) <= ? ");
			pgqry.setParameter(++index, Integer.parseInt(filterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)));
		}
		pgqry.append("ORDER BY REQDAT DESC");
		pgqry.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		return pgqry.getPage(pageNumber);
	}


	/**
	 *
	 */
	public String updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO)
			throws SystemException, PersistenceException{

		log.entering(MODULE, "updateForceMajeureRequest");
		String outPar = "";
		Procedure burstProcedure = getQueryManager()
				.createNamedNativeProcedure(MAIL_OPERATIONS_UPDATE_FORCE_MAJUERE);
		int index = 0;
		burstProcedure.setParameter(++index,requestVO.getCompanyCode());
		burstProcedure.setParameter(++index,requestVO.getForceMajeureID());
		burstProcedure.setParameter(++index,requestVO.getStatus());
		burstProcedure.setParameter(++index,requestVO.getApprRemarks());
		//Added by A-8527 for ICRD-349945
		burstProcedure.setParameter(++index,requestVO.getLastUpdatedUser());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		outPar = (String) burstProcedure.getParameter(index);
		log.log(Log.FINE, "outParameter is ", outPar);
		log.exiting(MODULE, "updateForceMajeureRequest");
		return outPar;

	}
	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerVO> findAllContainersInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			PersistenceException {

		log.entering("MailTrackingDefaultsSqlDAO", "findAllContainersInAssignedFlight");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_ALL_CONTAINERS_IN_ASSIGNED_FLIGHT);
		qry.setParameter(1, operationalFlightVO.getCompanyCode());
		qry.setParameter(2, operationalFlightVO.getCarrierId());
		qry.setParameter(3, operationalFlightVO.getFlightNumber());
		qry.setParameter(4, operationalFlightVO.getFlightSequenceNumber());
		qry.setParameter(5, operationalFlightVO.getPol());
		log.exiting("MailTrackingDefaultsSqlDAO", "findAllContainersInAssignedFlight");
		return qry.getResultList(new ContainerMapper());
	}
	/**
	 * @author A-8514
	 * Added as part of ICRD-229584 starts
	 *
	 */

	public MailbagInULDForSegmentVO getManifestInfo(ScannedMailDetailsVO scannedMailDetailsVO)throws SystemException, PersistenceException
	{
		log.entering("MailTrackingDefaultsSqlDAO", "getManifestInfo");
		int index=0;
		Collection<MailbagVO> mailBagVO;
		mailBagVO=scannedMailDetailsVO.getMailDetails();
		Query query = null ;
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO=null;
		if (mailBagVO != null && mailBagVO.size() > 0) {
			for (MailbagVO mailbag : mailBagVO) {

				if(scannedMailDetailsVO.getFlightNumber()==null || scannedMailDetailsVO.getFlightNumber().equals(""))
					scannedMailDetailsVO.setFlightNumber(mailbag.getFlightNumber());
				if(scannedMailDetailsVO.getFlightSequenceNumber()==0)
					scannedMailDetailsVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
				if(scannedMailDetailsVO.getSegmentSerialNumber()==0)
					scannedMailDetailsVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
				if(scannedMailDetailsVO.getCarrierId()==0)
					scannedMailDetailsVO.setCarrierId(mailbag.getCarrierId());
				if(mailbag.getMailSequenceNumber()==0)
				{
					Long seqnum=findMailSequenceNumber(mailbag.getMailbagId(), mailbag.getCompanyCode());
					mailbag.setMailSequenceNumber(seqnum);
				}
				query = getQueryManager().createNamedNativeQuery(GET_MAIL_MANIFESTINFO);
				query.setParameter(++index,scannedMailDetailsVO.getCompanyCode());
				query.setParameter(++index,mailbag.getMailSequenceNumber());
				query.setParameter(++index,scannedMailDetailsVO.getAirportCode());
				query.setSensitivity(true);
				mailbagInULDForSegmentVO = query.getSingleResult(new InboundFlightDetailsForAutoArrivalMapper());
				
				return mailbagInULDForSegmentVO;

			}
		}

		return null;
	}
	//Added as part of ICRD-229584 ends */

	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	@Override
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException, PersistenceException {
		log.entering("MailTrackingDefaultsSqlDAO", "processMailDataFromExcel");
		String processStatus = null;
		int index = 0;
		Procedure procedure = getQueryManager().createNamedNativeProcedure(PROCESS_MAIL_OPERATIONS_FROM_FILE);//reusing package call
		procedure.setParameter(++index,fileUploadFilterVO.getCompanyCode());
		procedure.setParameter(++index, fileUploadFilterVO.getFileType());
		procedure.setParameter(++index, "F");
		procedure.setParameter(++index,fileUploadFilterVO.getProcessIdentifier());
		procedure.setParameter(++index, MailConstantsVO.FLAG_NO);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		processStatus = (String)procedure.getParameter(index);
		return processStatus;
	}
	/**
	 * @author A-7794
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 */
	@Override
	public Collection<ConsignmentDocumentVO> fetchMailDataForOfflineUpload(
			String companyCode, String fileType) throws SystemException {
		log.entering(MODULE, "fetchMailDataForOfflineUpload");
		Collection<ConsignmentDocumentVO> documentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FETCH_MAILDATA_FOR_OFFLINE_UPLOAD);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, fileType);
		documentVOs = query
				.getResultList(new FileUploadConsignmentMultiMapper());
		log.log(Log.INFO, "ConsignmentDocumentVO is from dao*****", documentVOs);
		return documentVOs;
	}

	/***
	 * @author A-7794
	 */
	@Override
	public String checkScanningWavedDest(MailbagVO mailbagVO)
			throws SystemException {
		String scanWavedAirport= null;
		Query query = getQueryManager().createNamedNativeQuery(
				CHECK_SCAN_WAVED_AIRPORT);
		int idx = 0;
		query.setParameter(++idx, mailbagVO.getCompanyCode());
		query.setParameter(++idx, mailbagVO.getPaCode());
		if(mailbagVO.getDestination() != null){
			query.setParameter(++idx, mailbagVO.getDestination());
		}else{
			query.setParameter(++idx, mailbagVO.getFinalDestination());
		}
		if(mailbagVO.getConsignmentDate() != null ){
			query.append(" AND ?  BETWEEN VLDFRMDAT AND  VLDTOODAT  ");
			query.setParameter(++idx, mailbagVO.getConsignmentDate().toCalendar());
		}
		scanWavedAirport= query.getSingleResult(getStringMapper("SCNWVDFLG"));
		return scanWavedAirport;
	}

	//Added by A-8464 for ICRD-243079
	public List<MailMonitorSummaryVO> getServiceFailureDetails(MailMonitorFilterVO filterVO)
			throws SystemException, PersistenceException{
		String  baseQry =null;
		String baseQueryCount = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_BASE);
		baseQueryCount = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_SERVICE_FAILURE_COUNT);
		rankQuery.append(baseQueryCount);
		rankQuery.append(baseQry);
		List<MailMonitorSummaryVO> summaryVOs = new ArrayList<MailMonitorSummaryVO>();
		Query query = new MailPerformanceDetailsFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO, "SERVICE_FAILURE", rankQuery);
		summaryVOs = query.getResultList(new ServiceFailureMultiMapper());
		return summaryVOs;

	}

	//Added by A-8464 for ICRD-243079
	public List<MailMonitorSummaryVO> getOnTimePerformanceDetails(MailMonitorFilterVO filterVO)
			throws SystemException, PersistenceException{
		String  baseQry =null;
		String baseQueryCount = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_BASE);
		baseQueryCount = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_ONTIMEMAILBAGS_COUNT);
		rankQuery.append(baseQueryCount);
		rankQuery.append(baseQry);
		List<MailMonitorSummaryVO> summaryVOs = new ArrayList<MailMonitorSummaryVO>();
		Query query = new MailPerformanceDetailsFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO, "ONTIME_MAILBAGS", rankQuery);
		summaryVOs = query.getResultList(new OntimeMailbagsMapper());
		return summaryVOs;
	}

	public Page<MailbagVO> findDeviationMailbags(
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findDeviationMailbags");
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder().append(FIND_CONTAINERS_DENSE_RANK_QUERY);
		rankQuery.append(" MALIDR, MALSEQNUM ) AS RANK FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new DeviationMailbagMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append(MAILTRACKING_DEFAULTS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
		return pageableNativeQuery.getPage(pageNumber);
	}
	public Page<MailbagVO> findDeviationMailBagGroup(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findDeviationGroupedMails");
		StringBuilder baseQuery = null;
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		rankQuery.append(" SELECT  COUNT(*) COUNT,  SUM(WGT) TOTWGT, DSTCOD,  ERRORCOD FROM ( ");
		Query masterQuery = null;
		masterQuery = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_MAIN);
		baseQuery = new StringBuilder(rankQuery).append(" ").append(masterQuery);
		Page<MailbagVO> mailbagVos = null;

		PageableNativeQuery<MailbagVO> pageableNativeQuery = null;
		Query query1 = null;
		query1 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_1);
		Query query2 = null;
		query2 = getQueryManager().createNamedNativeQuery(FIND_DEVIATION_LIST_QUERY_2);
		pageableNativeQuery = new DeviationMailbagFilter(new DeviationMailBagGroupMapper(), mailbagEnquiryFilterVO,
				baseQuery, query1, query2);
		pageableNativeQuery.append("  ) MST GROUP BY ERRORCOD, DSTCOD ");
		pageableNativeQuery.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		pageableNativeQuery.append(" ");
		mailbagVos = pageableNativeQuery.getPage(pageNumber);
		return mailbagVos;
	}
	public static class DeviationMailBagGroupMapper implements Mapper<MailbagVO> {
		private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		public MailbagVO map(ResultSet rs) throws SQLException {
			log.log(Log.INFO, "Entering the DeviationMailBagGroupMapper");
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setDestCityDesc(rs.getString("DSTCOD"));
			mailbagVO.setCount(rs.getInt("COUNT"));
			mailbagVO.setAcceptedBags(rs.getInt("SCACNT"));
			try {
				mailbagVO.setWeight(Measure.addMeasureValues(mailbagVO.getWeight(),
						new Measure(UnitConstants.MAIL_WGT, (rs.getDouble("TOTWGT")))));
				mailbagVO.setAcceptedWeight(Measure.addMeasureValues(mailbagVO.getAcceptedWeight(),
						new Measure(UnitConstants.MAIL_WGT, rs.getDouble("SCAWGT"))));
			} catch (UnitException e1) {
				log.log(Log.SEVERE, "UnitException", e1.getMessage());
			}
			mailbagVO.setErrorCode(rs.getString("ERRORCOD"));
			return mailbagVO;
		}
	}
	public List<MailMonitorSummaryVO> getForceMajeureCountDetails(MailMonitorFilterVO filterVO)
			throws SystemException, PersistenceException{
		String  baseQry =null;
		String baseQueryCount = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE);
		baseQueryCount = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE_COUNT);
		rankQuery.append(baseQueryCount);
		rankQuery.append(baseQry);
		List<MailMonitorSummaryVO> summaryVOs = new ArrayList<MailMonitorSummaryVO>();
		Query query = new MailPerformanceDetailsFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO, "FORCE_MEJURE", rankQuery);
		this.log.log(5, new Object[] { "Query: ", query });
		summaryVOs = query.getResultList(new ForceMajeureCountMapper());
		return summaryVOs;
	}
	/*
		//Added by A-8464 for ICRD-243079
		 public Page<MailbagVO>  getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO,String type,int pageNumber)
				   throws SystemException, PersistenceException{
		Page<MailbagVO> mailbagVos = null;
		String baseQry = null;
		StringBuilder rankQuery = new StringBuilder();

		if (type.equals("MISSING_ORIGIN_SCAN") || type.equals("MISSING_DESTINATION_SCAN") || type.equals("MISSING_BOTH_SCAN")) {

			baseQry = getQueryManager().getNamedNativeQueryString(FIND_SERVICE_FAILURE_MAILBAGS);
			rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
			rankQuery.append(baseQry);

			if (type.equals("MISSING_ORIGIN_SCAN")) {
				rankQuery.append(" AND NOT EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='ACP' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD = HIS2.CMPCOD AND (HIS2.SCNPRT = MAL.ORGCOD OR EXISTS ");
				rankQuery.append(" (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='6' AND MAL.POACOD =CTS.GPACOD AND INSTR(CTS.ARPCOD,MAL.ORGCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
				rankQuery.append(" AND EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='DLV' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD = HIS2.CMPCOD AND (HIS2.SCNPRT= MAL.DSTCOD OR EXISTS ");
				rankQuery.append(" (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='21' AND MAL.POACOD = CTS.GPACOD AND INSTR(CTS.ARPCOD,MAL.DSTCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
			}

			else if (type.equals("MISSING_DESTINATION_SCAN")) {
				rankQuery.append(" AND NOT EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='DLV' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD = HIS2.CMPCOD AND (HIS2.SCNPRT = MAL.DSTCOD OR EXISTS ");
				rankQuery.append(" (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='21' AND MAL.POACOD = CTS.GPACOD AND INSTR(CTS.ARPCOD,MAL.DSTCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
				rankQuery.append(" AND EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='ACP' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD = HIS2.CMPCOD AND (HIS2.SCNPRT = MAL.ORGCOD OR EXISTS ");
				rankQuery.append(" (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='6' AND MAL.POACOD =CTS.GPACOD AND INSTR(CTS.ARPCOD,MAL.ORGCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
			}

			else {
				rankQuery.append(" AND NOT EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='ACP' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD = HIS2.CMPCOD AND (HIS2.SCNPRT = MAL.ORGCOD OR EXISTS ");
				rankQuery.append(" (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='6' AND MAL.POACOD =CTS.GPACOD AND INSTR(CTS.ARPCOD,MAL.ORGCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
				rankQuery.append(" AND NOT EXISTS (SELECT 1 FROM MALHIS HIS2 WHERE MALSTA ='DLV' AND MAL.MALSEQNUM = HIS2.MALSEQNUM AND MAL.CMPCOD    = HIS2.CMPCOD ");
				rankQuery.append(" AND (HIS2.SCNPRT = MAL.DSTCOD OR EXISTS (SELECT ARPCOD FROM MALCTSARP CTS WHERE MAL.CMPCOD =CTS.CMPCOD AND CTS.RSDMOD ='21' AND MAL.POACOD = CTS.GPACOD ");
				rankQuery.append(" AND INSTR(CTS.ARPCOD,MAL.DSTCOD)>0 AND INSTR(CTS.ARPCOD,HIS2.SCNPRT)>0))) ");
			}
		}

		if (type.equals("ON_TIME_MAILBAGS") || type.equals("DELAYED_MAILBAGS")) {

			baseQry = getQueryManager().getNamedNativeQueryString(FIND_STATION_ONTIME_MAILBAGS);
			rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
			rankQuery.append(baseQry);

			if (type.equals("DELAYED_MAILBAGS")) {
				rankQuery.append("AND MAL.ONNTIMDLVFLG = 'N' ");
			}
			else if (type.equals("ON_TIME_MAILBAGS")) {
				rankQuery.append("AND MAL.ONNTIMDLVFLG = 'Y' ");
			}
		}

		if (type.equals("RAISED_MAILBAGS") || type.equals("APPROVED_MAILBAGS") || type.equals("REJECTED_MAILBAGS")) {

			baseQry = getQueryManager().getNamedNativeQueryString(FIND_FORCE_MAJEURE_MAILBAGS);
			rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
			rankQuery.append(baseQry);

			if (type.equals("APPROVED_MAILBAGS")) {
				rankQuery.append(" AND FORCEMJR.FORMJRSTA='APR'");
			}
			else if (type.equals("REJECTED_MAILBAGS")) {
				rankQuery.append(" AND FORCEMJR.FORMJRSTA='REJ' ");
			}
			else{
				rankQuery.append(" AND FORCEMJR.FORMJRSTA='REQ' ");
			}
		}

		PageableNativeQuery<MailbagVO> qry = new MailPerformanceMonitorFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO,rankQuery);
		qry.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		log.log(Log.INFO, "Query: ", qry);
		mailbagVos = qry.getPage(pageNumber);
		return mailbagVos;
			}*/
	public Page<MailbagVO> getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO, String type, int pageNumber)
			throws SystemException, PersistenceException
	{
		Page<MailbagVO> mailbagVos = null;
		String baseQry = null;
		String baseQuerySelect = null;
		StringBuilder rankQuery = new StringBuilder();
		baseQry = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_FORCEMEJURE);
		baseQuerySelect = getQueryManager().getNamedNativeQueryString(FIND_PERFORMANCE_MAILBAGS_SELECT);
		rankQuery.append("SELECT RESULT_TABLE.* , ROW_NUMBER() OVER(ORDER BY NULL) AS RANK FROM ( ");
		rankQuery.append(baseQuerySelect);
		rankQuery.append(baseQry);
		PageableNativeQuery<MailbagVO> qry = new MailPerformanceMonitorFilterQuery(filterVO.getPageSize(), new MailbagMapper(), filterVO, type, rankQuery);
		qry.append(" )RESULT_TABLE");
		this.log.log(5, new Object[] { "Query: ", qry });
		mailbagVos = qry.getPage(pageNumber);
		return mailbagVos;
	}
	/**
	 /**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#listPostalCalendarDetails(com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO)
	 *	Added by 			: A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param uSPSPostalCalendarFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws SystemException, PersistenceException{

		log.entering("MailTrackingDefaultsSqlDAO", "validateFrmToDateRange");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_POSTAL_CAL_DETAILS);
		if(uSPSPostalCalendarFilterVO.getCalValidFrom()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))){
			qry=qry.append(" AND to_number(to_char(PRDFRM,'YYYYMMDD')) IN ( ? )");
		}
		if(uSPSPostalCalendarFilterVO.getCalValidTo()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))){
			qry=qry.append(" AND to_number(to_char (PRDTOO,'YYYYMMDD')) IN (? )");
		}
		int idx = 0;
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx, "I");
		qry.setParameter(++idx, uSPSPostalCalendarFilterVO.getCalPacode());
		if(uSPSPostalCalendarFilterVO.getCalValidFrom()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidFrom()))){
			qry.setParameter(++idx, Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidFrom().toStringFormat(DATE).substring(0, 8)) );
		}
		if(uSPSPostalCalendarFilterVO.getCalValidTo()!=null&&!(("").equals(uSPSPostalCalendarFilterVO.getCalValidTo()))){
			qry.setParameter(++idx,Integer.parseInt(uSPSPostalCalendarFilterVO.getCalValidTo().toStringFormat(DATE).substring(0, 8)));
		}
		log.exiting("MailTrackingDefaultsSqlDAO", "validateFrmToDateRange");
		log.log(Log.FINE, "USPS calender query ", qry);
		Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs = qry
				.getResultList(new USPSPostalCalendarMapper());
		return uSPSPostalCalendarVOs;
	}
	/**
	 *
	 *	Added by 	: A-8464 on 26-Mar-2018
	 * 	Used for 	:	ICRD-273761
	 *	Parameters	:	@param mailbagEnquiryFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws SystemException, PersistenceException{

		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagDetailsForMailbagEnquiryHHT");
		int idx = 0;
		String qryString = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_MAILBAGENQUIRY);
		String modifiedStr1 = null;
		if (isOracleDataSource())
		{
			modifiedStr1 = "LISTAGG(RTGCARCOD || ' ' || RTGFLTNUM || ' ' || RTGFLTDAT,',') within GROUP (ORDER BY RTGFLTDAT) ROUTEINFO";
		}
		else
		{
			modifiedStr1 = "string_agg ( RTGCARCOD || ' '|| RTGFLTNUM|| ' '|| RTGFLTDAT, ','  ORDER BY RTGFLTDAT) ROUTEINFO";
		}
		qryString = String.format(qryString, modifiedStr1);

		Query qry = getQueryManager().createNativeQuery(qryString);
		qry.setParameter(++idx,mailbagEnquiryFilterVO.getCompanyCode());
		qry.setParameter(++idx,mailbagEnquiryFilterVO.getMailbagId());
		qry.setParameter(++idx,mailbagEnquiryFilterVO.getCompanyCode());// added by A-8353 for ICRD-333808
		qry.setParameter(++idx,mailbagEnquiryFilterVO.getMailbagId());

		MailbagVO result = qry.getSingleResult(new MailbagEnquiryMapper());
		return result;

	}
	/**
	 * @author A-7371
	 * @param uspsPostalCalendarFilterVO
	 * @return	USPSPostalCalendarVO
	 */
	public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)throws SystemException, PersistenceException{
		log.entering("MailTrackingDefaultsSqlDAO", "findInvoicPeriodDetails");
		int idx = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_INVOIC_PERIOD_DETAILS);
		qry.setParameter(++idx,uspsPostalCalendarFilterVO.getCompanyCode());
		qry.setParameter(++idx,uspsPostalCalendarFilterVO.getCalPacode());
		qry.setParameter(++idx,uspsPostalCalendarFilterVO.getCalendarType());
		qry.setParameter(++idx,uspsPostalCalendarFilterVO.getInvoiceDate().toSqlDate().toString().replace("-", ""));
		USPSPostalCalendarVO uspsPostalCalendarVO = qry.getSingleResult(new InvoicPeriodDetailsMapper());
		return uspsPostalCalendarVO;

	}


	private static class InvoicPeriodDetailsMapper implements Mapper<USPSPostalCalendarVO> {

		public USPSPostalCalendarVO map(ResultSet rs) throws SQLException {

			USPSPostalCalendarVO uspsPostalCalendarVO = new USPSPostalCalendarVO();
			uspsPostalCalendarVO.setGpacod(rs.getString("GPACOD"));
			uspsPostalCalendarVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PRDFRM")));
			uspsPostalCalendarVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PRDTOO")));
			return uspsPostalCalendarVO;
		}

	}



	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findDuplicateMailbag(java.lang.String, java.lang.String)
	 *	Added by 			: A-7531 on 16-May-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param mailBagId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId)
			throws SystemException {
		Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		ArrayList<MailbagVO> newmailVOs = new ArrayList<MailbagVO>();
		int idx = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_DUPLICATEMAILBAG_SEQNUM);
		qry.setParameter(++idx,companyCode);
		qry.setParameter(++idx,mailBagId);

		mailVOs= qry.getResultList(new DuplicateMailbagMapper());
		newmailVOs=new ArrayList<>(mailVOs);
		return newmailVOs;

	}
	/**
	 *  Added as part of IASCB-91419
	 * @author 215166
	 *
	 */
	private static class DuplicateMailbagMapper implements Mapper<MailbagVO> {

		public MailbagVO map(ResultSet rs) throws SQLException {

			MailbagVO mailbagVO = new MailbagVO();
			String airport = rs.getString("SCNPRT");
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			if (rs.getDate("SCNDAT") != null && airport != null) {
				mailbagVO.setScannedDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("SCNDAT")));
			}

			String mailStatus = rs.getString("MALSTA");
			mailbagVO.setLatestStatus(mailStatus);
			mailbagVO.setScannedPort(rs.getString("SCNPRT"));
			mailbagVO.setContainerNumber(rs.getString("CONNUM"));
			mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			Measure wgt = new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"));
			mailbagVO.setWeight(wgt);
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			if (rs.getString("ACTWGTDSPUNT") != null) {
				mailbagVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT, rs.getDouble("ACTWGT")));
			}
			mailbagVO.setLastUpdateUser(rs.getString("SCNUSR"));
			if (rs.getTimestamp("CSGDAT") != null && airport != null) {
				mailbagVO.setConsignmentDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("CSGDAT")));
			}
			mailbagVO.setMailRemarks(rs.getString("MALRMK"));
			if(rs.getString("DSTEXGOFC") !=null){
				mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			}
			if(rs.getString("ORGEXGOFC") !=null){
				mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			}
			if (rs.getString("DSN") !=null) {
				mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			}
			if (rs.getString("MALCTG") !=null) {
				mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
			}
			if (rs.getInt("YER") !=0) {
				mailbagVO.setYear(rs.getInt("YER"));
			}
			if (rs.getString("MALSUBCLS") !=null) {
				mailbagVO.setMailSubclass(rs.getString("MALSUBCLS").substring(
						0, 1));
			}
			if (rs.getString("MALCLS") !=null) {
				mailbagVO.setMailClass(rs.getString("MALCLS").substring(
						0, 1));
			}
			if (rs.getString("RSN") !=null) {
				mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			}
			mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
			if(rs.getTimestamp("REQDLVTIM") != null){
				mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE,
						rs.getTimestamp("REQDLVTIM")));
			}
			if (rs.getString("MALSRVLVL") !=null) {
				mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			}
			if(rs.getTimestamp("TRPSRVENDTIM") != null){
				mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE,
						rs.getTimestamp("TRPSRVENDTIM")));
			}
			if(rs.getString("POACOD") != null){
				mailbagVO.setPaCode(rs.getString("POACOD"));
			}
			return mailbagVO;
		}
	}

	/**
	 * @author A-8061
	 *
	 */
	public String findServiceResponsiveIndicator(MailbagVO mailbagVO) throws SystemException {
		String serviceResponsiveIndicator=null;
		int idx = 0;
		String qery = getQueryManager().getNamedNativeQueryString(
				FIND_SERVICERESPONSIVEINDICATOR);
		String dynamicQry = null;
		if (isOracleDataSource())
		{
			dynamicQry = "PKG_FRMWRK.FUN_CHECK_STRING_COMMON(DAYOFFWEK,1+TRUNC(?)-TRUNC(?, 'IW'),'') > 0";
		}
		else
		{

			dynamicQry=" strpos(DAYOFFWEK, to_char(extract(isodow from (to_date (?,'yyyy-mm-dd')) ))) > 0";
		}
		qery = String.format(qery,dynamicQry);



		Query qry=getQueryManager().createNativeQuery(qery);
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getMailServiceLevel());
		qry.setParameter(++idx,mailbagVO.getCompanyCode());
		qry.setParameter(++idx,mailbagVO.getPaCode());
		qry.setParameter(++idx,mailbagVO.getOrigin());
		qry.setParameter(++idx,mailbagVO.getDestination());
		qry.setParameter(++idx,mailbagVO.getConsignmentDate());
		qry.setParameter(++idx,mailbagVO.getConsignmentDate());
		if (isOracleDataSource()){
			qry.setParameter(++idx,mailbagVO.getConsignmentDate());
		}

		serviceResponsiveIndicator= qry.getSingleResult(getStringMapper("SRVIND"));
		serviceResponsiveIndicator=serviceResponsiveIndicator==null ? "N":serviceResponsiveIndicator;

		return serviceResponsiveIndicator;
	}

	/**
	 * Added as a part of ICRD-318999.
	 * This method is to pick up mailbox id according to the parameters given.
	 */
	public String findMailboxIdFromConfig(MailbagVO mailbagVO) throws SystemException {
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOXID_FROM_CONFIG);
		query.setParameter(++index, mailbagVO.getOoe());
		query.setParameter(++index, mailbagVO.getDoe());
		query.setParameter(++index, mailbagVO.getOrigin());
		query.setParameter(++index, mailbagVO.getDestination());
		query.setParameter(++index, mailbagVO.getMailCategoryCode());
		query.setParameter(++index, mailbagVO.getMailSubclass());
		String xxResdit="Y";
		if(null!=mailbagVO.getConsignmentNumber()){
			xxResdit = "N";
		}
		query.setParameter(++index, xxResdit);
		if(mailbagVO.getCarrierCode()==null) {
			query.setParameter(++index, mailbagVO.getCompanyCode());
		}
		else {
			query.setParameter(++index, mailbagVO.getCarrierCode());
		}

		String mailboxId = query.getSingleResult(getStringMapper("MALBOXID"));
		return mailboxId;
	}

	private boolean isFlightFilterPresent(MailArrivalVO mailArrivalVO) {
		if (mailArrivalVO.getFlightNumber() != null && mailArrivalVO.getFlightNumber().trim().length() > 0 && mailArrivalVO.getFlightDate() != null) {
			return true;
		}
		return false;
	}


	@Override
	public Collection<MailEventVO> findMailEvent(MailEventPK maileventPK) throws SystemException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMailEvent");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBOX_EVENT);
		query.setParameter(++index, maileventPK.getCompanyCode());
		query.setParameter(++index, maileventPK.getMailboxId());
		List<MailEventVO> mailEventVOs = query.getResultList(new MailboxIdMapper());
		log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMailEvent");
		return mailEventVOs ;
	}


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#getTempCarditMessages(java.lang.String)
	 *	Added by 			: A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 * @throws SystemException
	 */
	@Override
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode,
															 String includeMailBoxIdr,String excludeMailBoxIdr,
															 String includedOrigins,String excludedOrigins,
															 int pageSize,int noOfDays) throws SystemException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "getTempCarditMessages");
		Collection<CarditTempMsgVO> carditTempMsgVOs =null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_TMPCARDITMSGS);
		query.setParameter(++index, companyCode);
		if(includeMailBoxIdr !=null && !includeMailBoxIdr.trim().isEmpty()){
			query.append(" and POSITION(tmp.sndidr in ?) >0 ");
			query.setParameter(++index, includeMailBoxIdr);
			
		}
		if(excludeMailBoxIdr !=null && !excludeMailBoxIdr.trim().isEmpty()){
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,TMP.SNDIDR,?)=0 ");
			query.setParameter(++index, excludeMailBoxIdr);
			query.setParameter(++index, ",");
		}
		if(includedOrigins !=null && !includedOrigins.trim().isEmpty()){
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,substr(TMP.DEPPLC,1,3),?)>0 ");
			query.setParameter(++index, includedOrigins);
			query.setParameter(++index, ";");
		}
		if(excludedOrigins !=null && !excludedOrigins.trim().isEmpty()){
			query.append(" AND  pkg_frmwrk.fun_check_string_contains(?,substr(TMP.DEPPLC,1,3),?)=0 ");
			query.setParameter(++index, excludedOrigins);
			query.setParameter(++index, ";");
		}
		if(noOfDays>0){
			query.append(" and TMP.lstupdtim> current_date- ? ");
			query.setParameter(++index, noOfDays);
		}
		if(pageSize>0){
			query.append(" ORDER BY TMP.SNDIDR,TMP.ICHCTLREF,TMP.MSGSEQNUM) MAL WHERE RNK < ? ");
			query.setParameter(++index, pageSize);
		}else{
			query.append(" ORDER BY TMP.SNDIDR,TMP.ICHCTLREF,TMP.MSGSEQNUM) MAL WHERE RNK <100 ");
		}
		carditTempMsgVOs = query.getResultList(new TempCarditMsgMapper());
		return carditTempMsgVOs;
	}

	//Added by A-8893 for IASCB-34152 starts
	public int findbulkcountInFlight(ContainerDetailsVO containerDetailsVO) throws SystemException {
		log.entering(MODULE, "findbulkcountInFlight");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_BULKCOUNT_FLIGHT);
		qry.setParameter(++index, containerDetailsVO.getCompanyCode());
		qry.setParameter(++index, containerDetailsVO.getCarrierId());
		qry.setParameter(++index, containerDetailsVO.getFlightNumber());
		qry.setParameter(++index, containerDetailsVO.getFlightSequenceNumber());
		qry.setParameter(++index, containerDetailsVO.getDestination());
		qry.setParameter(++index, containerDetailsVO.getAssignedPort());
		return qry.getSingleResult(getIntMapper("BULKCOUNT"));
	}
	//Added by A-8893 for IASCB-34152 ends
	//Added by A-8672 as part of IASCB-42757 starts.............
	public String findRoutingDetailsForConsignment(MailbagVO mailbagVO){
		String routingDetails = null;
		Query query;
		try {
			query = getQueryManager().createNamedNativeQuery(
					FIND_ROUTING_DETAILS_FOR_CONSIGNMENT);
			int index = 0;
			query.setParameter(++index, mailbagVO.getCompanyCode());
			query.setParameter(++index, mailbagVO.getPaCode());
			query.setParameter(++index, mailbagVO.getConsignmentNumber());
			query.setParameter(++index, mailbagVO.getDestination());
			routingDetails = query
					.getSingleResult(new RoutingForConsignmentMapper());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routingDetails;
	}
	private static class RoutingForConsignmentMapper implements Mapper<String> {
		String routingDetail;
		public String map(ResultSet rs) throws SQLException {
			String pol=(rs.getString("POL"));
			String pou =(rs.getString("POU"));
			routingDetail=new StringBuilder().append(String.valueOf(pol)).append("-")
					.append(String.valueOf(pou)).toString();
			return routingDetail;
		}
	}
	//Added by A-8672 as part of IASCB-42757 ends.............

	public Collection<DSNVO>  getDSNsForCarrier(ContainerDetailsVO containervo) throws SystemException,
			PersistenceException {
		List<DSNVO> dsnVos= null;
		Query qry = null;
		StringBuilder rankQuery = new StringBuilder();
		//rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_ROWNUM_QUERY);
		int index=0;
		log.entering("MailTrackingDefaultsSqlDAO", "findMailbagsinContainer");
		if (MailConstantsVO.ULD_TYPE.equals(containervo.getContainerType())) {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_CARRIER_ULD_DSNVIEW);
		} else {
			qry = getQueryManager().createNamedNativeQuery(
					FIND_MAILBAGS_CARRIER_BULK_DSNVIEW);
		}
		qry.setParameter(++index, containervo.getCompanyCode());
		qry.setParameter(++index, containervo.getCarrierId());
		qry.setParameter(++index, containervo.getPol());
		qry.setParameter(++index, containervo.getContainerNumber());
		qry.append(") MST GROUP BY CMPCOD,DSN,ORGEXGOFC,DSTEXGOFC,MALSUBCLS,MALCTGCOD,YER");
		dsnVos = qry.getResultList(new MailbagDSNMapper());
		return dsnVos;
	}


	/**
	 * @author-U-1439
	 * @IASCB-47333
	 * @param containerDetailsVO
	 * @return
	 */
	public String isValidContainerForULDlevelArrivalOrDelivery(ContainerDetailsVO containerDetailsVO) throws SystemException {
		log.entering("MailTrackingDefaultsSqlDao","isValidContainerForULDlevelArrivalOrDelivery");
		String notValidContainer=null;
		Query query  = getQueryManager().createNamedNativeQuery(
				VALID_CONTAINER_FOR_ARRORDLV);
		int idx = 0;
		query.setParameter(++idx, containerDetailsVO.getCompanyCode());
		query.setParameter(++idx, containerDetailsVO.getCarrierId());
		query.setParameter(++idx, containerDetailsVO.getFlightNumber());
		query.setParameter(++idx, containerDetailsVO.getFlightSequenceNumber());
		query.setParameter(++idx, containerDetailsVO.getLegSerialNumber());
		query.setParameter(++idx, containerDetailsVO.getPol());
		query.setParameter(++idx, containerDetailsVO.getContainerNumber());
		notValidContainer =query.getSingleResult(getStringMapper("NOTVALCONFORDLV"));
		log.exiting("MailTrackingDefaultsSqlDao","isValidContainerForULDlevelArrivalOrDelivery");

		return notValidContainer;
	}
	/**
	 * @author A-5526
	 * Added as part of CRQ IASCB-44518
	 * @param containerNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailbagVO> findMailbagsFromOALinResditProcessing(
			String containerNumber,String companyCode) throws SystemException,
			PersistenceException {
		log.entering(MODULE+ LOG_DELIMITER, "findMailbagsFromOALinResditProcessing");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGS_FROMOAL_INRESDITPROCESING);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		if(isOracleDataSource()){
			qry.append(" AND SCNDAT >= SYSDATE-(COALESCE((SELECT to_number(PARVAL) FROM SHRSYSPAR WHERE PARCOD='mail.operations.maxresditdaysforuldmailbagmapping' AND CMPCOD=?),0)) ");
			qry.setParameter(++index, companyCode);
		}
		else{
			qry.append(" AND SCNDAT >= current_date - (COALESCE( (SELECT (PARVAL)::integer FROM SHRSYSPAR WHERE PARCOD='mail.operations.maxresditdaysforuldmailbagmapping' AND CMPCOD=? ),0)) ");
			qry.setParameter(++index, companyCode);
		}
		mailBagVos = qry.getResultList(new MailBagsForUpliftedResditMapper());
		log.exiting(MODULE+ LOG_DELIMITER, "findMailbagsFromOALinResditProcessing");
		return mailBagVos;
	}



	/**
	 * @author a-9529 This method is used to find out the Mail Bags in the
	 *         Containers
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(
			Collection<ContainerDetailsVO> containers) throws SystemException,
			PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMailbagsInContainerFromInboundForReact");
		Collection<ContainerDetailsVO> containerForReturn = new ArrayList<>();
		Query qry = null;
		for (ContainerDetailsVO cont : containers) {
			int idx = 0;
			if (MailConstantsVO.ULD_TYPE.equals(cont.getContainerType())) {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_MAILBAGS_FLIGHT_ULD_INBOUND);
				qry.setParameter(++idx, cont.getCompanyCode());
				qry.setParameter(++idx, cont.getCarrierId());
				qry.setParameter(++idx, cont.getFlightNumber());
				qry.setParameter(++idx, cont.getFlightSequenceNumber());
				qry.setParameter(++idx, cont.getLegSerialNumber());
				qry.setParameter(++idx, cont.getPol());
				qry.setParameter(++idx, cont.getContainerNumber());
			} else {
				qry = getQueryManager().createNamedNativeQuery(
						FIND_INBOUND_MAILBAG_DETAILS);
				qry.setParameter(++idx, cont.getCompanyCode());
				qry.setParameter(++idx, cont.getCarrierId());
				qry.setParameter(++idx, cont.getFlightNumber());
				qry.setParameter(++idx, cont.getFlightSequenceNumber());
				qry.setParameter(++idx, cont.getPou());
				qry.setParameter(++idx, cont.getContainerNumber());
				qry.append(" AND FLT.POL =  ?  ");
				qry.setParameter(++idx,cont.getPol());
				qry.append(" )ORDER BY CONNUM, DSN,ORGEXGOFC,DSTEXGOFC,MALCLS,MALSUBCLS,MALCTGCOD,YER,MALSTA  ");
			}
			List<ContainerDetailsVO> list = qry
					.getResultList(cont.getFlightSequenceNumber() > 0 ? new AcceptedDsnsInFlightMultiMapper()
							: new AcceptedDsnsInCarrierMultiMapper());
			if (list != null && !list.isEmpty()) {
				containerForReturn.add(list.get(0));
			}
		}
		log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMailbagsInContainerFromInboundForReact");
		return containerForReturn;
	}


	/**
	 * @author U-1439
	 * Added as part of CRQ IASCB-48353
	 * @param containerNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailbagVO> findMailbagsForPABuiltContainerSave(String containerNumber, String companyCode,
																	 LocalDate fromDate,LocalDate toDate)
			throws SystemException, PersistenceException {

		log.entering(MODULE+ LOG_DELIMITER, "findMailbagsForPABuiltContainerSave");
		Collection<MailbagVO> mailBagVos = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				MAILBAGS_FORPABUILT_CONTAINERSAVE);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		if(isOracleDataSource()){
			qry.append(" AND CSGMST.CSGDAT BETWEEN TRUNC(?) AND TRUNC(?)  ");
			qry.setParameter(++index, fromDate);
			qry.setParameter(++index, toDate);
		}
		else{

			qry.append("AND to_Date(TO_CHAR(CSGDAT,'yyyy-MM-dd'),'yyyy-MM-dd')  BETWEEN to_date(?,'yyyy-MM-dd') AND to_date(?,'yyyy-MM-dd')");
			qry.setParameter(++index, String.valueOf(fromDate.toSqlDate()));
			qry.setParameter(++index, String.valueOf(toDate.toSqlDate()));
		}
		mailBagVos = qry.getResultList(new MailBagsForPABuiltContainerMapper());



		log.exiting(MODULE+ LOG_DELIMITER, "findMailbagsForPABuiltContainerSave");
		return mailBagVos;

	}

	public String findContainerInfoForDeviatedMailbag(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber){
		String containerInfo = null;
		Query query;
		try {
			query = getQueryManager().createNamedNativeQuery(
					CONTAINER_INFO_DEVIATED_MAILBAG);
			int index = 0;
			query.setParameter(++index, containerDetailsVO.getCompanyCode());
			query.setParameter(++index, mailSequenceNumber);
			query.setParameter(++index, containerDetailsVO.getPou());
			containerInfo = query
					.getSingleResult(new ContainerForDeviatedMailbagMapper());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return containerInfo;
	}
	private static class ContainerForDeviatedMailbagMapper implements Mapper<String> {
		String containerInfo;
		public String map(ResultSet rs) throws SQLException {
			String conNum = (rs.getString("CONNUM"));
			String conType = (rs.getString("CONTYP"));
			if(conNum !=null && conType!=null)
				containerInfo = conNum + "-"+conType;
			return containerInfo;
		}
	}

	/**
	 * @author A-5526 This method is used to find the details of approved Force Meajure info of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(
			String companyCode,String mailBagId, long mailSequenceNumber) throws SystemException,
			PersistenceException {

		log.entering(MODULE, "findApprovedForceMajeureDetails");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_APPROVED_FORCE_MAJEURE_DETAILS_OF_MAILBAG);
		query.setParameter(++index, companyCode);
		if(mailSequenceNumber!=0l){
			query.append(" AND REQMST.MALSEQNUM = ?  ");
			query.setParameter(++index, mailSequenceNumber);

		}
		else if(mailBagId!=null && !mailBagId.isEmpty()){
			query.append(" AND REQMST.MALIDR = ? ");
			query.setParameter(++index, mailBagId);
		}

		return query.getResultList(new ForceMajeureRequestMapper());

	}

	/**
	 * @author A-8672
	 * @param findMailHandoverDetails
	 * @return handovertime
	 */
	public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailHandoverDetails");

		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_HANDOVER_DETAILS);
		int index = 0;
		qry.setParameter(++index, mailHandoverVO.getCompanyCode());
		if (mailHandoverVO.getGpaCode() != null
				&& !("").equals(mailHandoverVO.getGpaCode())) {
			qry.append(" AND GPACOD=?");
			qry.setParameter(++index, mailHandoverVO.getGpaCode());
		}
		if (mailHandoverVO.getHoAirportCodes() != null
				&& !("").equals(mailHandoverVO.getHoAirportCodes())) {
			qry.append(" AND ARPCOD = ?");
			qry.setParameter(++index, mailHandoverVO.getHoAirportCodes());
		}
		if(mailHandoverVO.getMailClass() != null
				&& !("").equals(mailHandoverVO.getMailClass())){
			qry.append(" AND MALCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailClass());
		}
		if(mailHandoverVO.getMailSubClass() != null
				&& !("").equals(mailHandoverVO.getMailSubClass())){
			qry.append(" AND MALSUBCLS = ?");
			qry.setParameter(++index, mailHandoverVO.getMailSubClass());
		}
		if(mailHandoverVO.getExchangeOffice() != null
				&& !("").equals(mailHandoverVO.getExchangeOffice())){
			qry.append(" AND EXGOFC = ?");
			qry.setParameter(++index, mailHandoverVO.getExchangeOffice());
		}

		log.exiting(MODULE, "findMailHandoverDetails");
		return qry.getSingleResult(getStringMapper("HNDTIM"));

	}

	public MailbagVO findNotupliftedMailsInCarrierforDeviationlist(MailbagVO mailbagVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE+ LOG_DELIMITER, "findNotupliftedMailsInCarrierforDeviationlist");
		MailbagVO mailBagVo = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_NOT_UPLIFTED_MAILBAGS_FOR_DEVIATION);
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getPol());
		qry.setParameter(++index, mailbagVO.getMailSequenceNumber());
		mailBagVo = qry.getSingleResult(new NotupliftedMailsInCarrierforDeviationMapper());
		log.exiting(MODULE+ LOG_DELIMITER, "findNotupliftedMailsInCarrierforDeviationlist");
		return mailBagVo;
	}

	private static class NotupliftedMailsInCarrierforDeviationMapper implements Mapper<MailbagVO> {
		public MailbagVO map(ResultSet rs) throws SQLException {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVO.setPol(rs.getString("ARPCOD"));
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
			mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
			mailbagVO.setUldNumber(rs.getString("ULDNUM"));
			mailbagVO.setScannedPort(rs.getString("ARPCOD"));
			return mailbagVO;
		}
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findMailboxIdForPA(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 			: A-8061 on 20-Jul-2020
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findMailboxIdForPA(MailbagVO mailbagVO) throws SystemException, PersistenceException {

		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAILBOX_FORPA);
		int index = 0;
		qry.setParameter(++index, mailbagVO.getCompanyCode());
		qry.setParameter(++index, mailbagVO.getPaCode());

		return qry.getSingleResult(getStringMapper("MALBOXIDR"));
	}

	/**
	 * @author A-8353
	 * Parameters	:	@param mailbagVO
	 *Parameters	:	@return
	 *Parameters	:	@throws SystemException
	 *Parameters	:	@throws PersistenceException
	 */
	public MailbagInULDForSegmentVO getManifestInfoForNextSeg(MailbagVO mailBagVO)throws SystemException, PersistenceException
	{
		log.entering("MailTrackingDefaultsSqlDAO", "getManifestInfoForNextSeg");
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO=null;
		int index = 0;
		if(mailBagVO.getMailSequenceNumber()==0)
		{
			Long seqnum=findMailSequenceNumber(mailBagVO.getMailbagId(), mailBagVO.getCompanyCode());
			mailBagVO.setMailSequenceNumber(seqnum);
		}
		Query query = getQueryManager().createNamedNativeQuery(GET_MAIL_MANIFESTINFO_NXT_SEG);
		query.setParameter(++index,mailBagVO.getCompanyCode());
		query.setParameter(++index,mailBagVO.getMailSequenceNumber());
		query.setParameter(++index,mailBagVO.getPou());
		mailbagInULDForSegmentVO = query.getSingleResult(new InboundFlightDetailsForAutoArrivalMapper());
		return mailbagInULDForSegmentVO;

	}
	/**
	 * @author A-8353
	 * Parameters	:	@param String
	 * Parameters	:	@throws String
	 * Parameters	:	@throws String
	 *Parameters	:	@return
	 *Parameters	:	@throws SystemException
	 */
	public String checkMailInULDExistForNextSeg(String containerNumber,String airpotCode ,String companyCode) throws SystemException {
		log.entering("MailTrackingDefaultsSqlDao","checkMailInULDExistForNextSeg");
		String mailExistInNextSeg=null;
		Query query  = getQueryManager().createNamedNativeQuery(
				MAILBAG_IN_CONTAINER_USED_IN_NEXT_SEG);
		int idx = 0;
		query.setParameter(++idx,airpotCode);
		query.setParameter(++idx, airpotCode);
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, containerNumber);
		mailExistInNextSeg =query.getSingleResult(getStringMapper("MALEXT"));
		log.exiting("MailTrackingDefaultsSqlDao","checkMailInULDExistForNextSeg");
		return mailExistInNextSeg;
	}

	/*
	 *
	 */
	public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO)
			throws SystemException {
		log.entering(MODULE+ LOG_DELIMITER, "findTransferManifestConsignmentDetails");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_CONSIGNMENTDTL);
		qry.setParameter(++index, transferManifestVO.getCompanyCode());
		qry.setParameter(++index, transferManifestVO.getTransferManifestId());
		consignmentDocumentVOs = qry.getResultList(new TransferManifestConsignmentDetail());
		log.exiting(MODULE+ LOG_DELIMITER, "findTransferManifestConsignmentDetails");
		return consignmentDocumentVOs;
	}
	private static class TransferManifestConsignmentDetail implements MultiMapper<ConsignmentDocumentVO> {

		private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
		private static final String CLASS_NAME = "TransferManifestConsignmentDetail";

		public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
			log.entering(CLASS_NAME, "map");
			List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
			ConsignmentDocumentVO consignmentDocumentVO = null;
			while(rs.next()) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
				consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
				consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
				consignmentDocumentVOs.add(consignmentDocumentVO);
			}
			return consignmentDocumentVOs;
		}
	}

	/**
	 * @author A-9084
	 * Parameters	:	@param String
	 * Parameters	:	@throws String
	 *Parameters	:	@return
	 *Parameters	:	@throws SystemException
	 *Parameters	:	@throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,String poaCode)
			throws SystemException, PersistenceException{
		List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
		ConsignmentDocumentVO consignmentVO = new ConsignmentDocumentVO();
		Query query = getQueryManager().createNamedNativeQuery(
				"mail.operations.findConsignmentScreeningDetails");
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, consignmentNumber);
		query.setParameter(++index, poaCode);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, consignmentNumber);
		if(poaCode!=null && !poaCode.isEmpty()) {
			query.append(" AND MST.POACOD = ?");
			query.setParameter(++index, poaCode);
		}
		consignmentDocumentVOs = query.getResultList(new ListConsignmentScreeningMapper());
		if(!consignmentDocumentVOs.isEmpty()){
			consignmentVO = consignmentDocumentVOs.get(0);
			return consignmentVO;
		}
		this.log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, "findConsignmentScreeningDetails");
		return null;
	}

	/**
	 * @author A-9084
	 * Parameters	:	@param String
	 * Parameters	:	@throws String
	 *Parameters	:	@return
	 *Parameters	:	@throws SystemException
	 *Parameters	:	@throws PersistenceException
	 */
	public ConsignmentDocumentVO generateSecurityReport(String consignmentNumber, String paCode)
			throws SystemException
	{
		this.log.entering("MailTrackingDefaultsSqlDAO", "findSecurityScreeningDetails");
		ConsignmentDocumentVO consignmentVO = new ConsignmentDocumentVO();
		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		String airport = logonAttributes.getAirportCode();
		Query query = getQueryManager().createNamedNativeQuery(
				"mail.operations.findConsignmentScreeningDetails");
		int index = 0;
		query.setParameter(++index, logonAttributes.getCompanyCode());
		query.setParameter(++index, consignmentNumber);
		query.setParameter(++index, paCode);
		query.setParameter(++index, logonAttributes.getCompanyCode());
		query.setParameter(++index, consignmentNumber);
		query.append(" AND DTL.ARPCOD =  ").append(
				new StringBuilder().append("'").append(airport)	.append("'").toString());
		List<ConsignmentDocumentVO> consignmentDocumentVOs = query.getResultList(new ListConsignmentScreeningMapper());
		if(!consignmentDocumentVOs.isEmpty()){
			consignmentVO = consignmentDocumentVOs.get(0);
			return consignmentVO;
		}
		this.log.exiting(MAIL_TRACKING_DEFAULTS_SQLDAO, "generateSecurityReport");
		return null;
	}

	public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
			String companyCode,String tranferManifestId)throws SystemException, PersistenceException{
		log.entering(MODULE, "findTransferManifestDetails");
		TransferManifestVO transferManifestVO = null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_DSN_DETAILS);
		int index=0;
		qry.setParameter(++index,companyCode);
		qry.setParameter(++index,tranferManifestId);
		List<TransferManifestVO> transferManifestVOs =qry.getResultList(new ListTransferManifestDSNMultiMapper());

		return transferManifestVOs;
	}
	/**
	 * @author A-8353
	 */
	public String findTransferManifestId(String companyCode,long malSeqNum)throws SystemException, PersistenceException{//partial transfer
		log.entering(MODULE, "findTransferManifestId");
		String transferManifestId=null;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_TRANSFER_MANIFEST_ID_DETAILS);
		int index=0;
		qry.setParameter(++index,companyCode);
		qry.setParameter(++index,malSeqNum);
		transferManifestId =qry.getSingleResult(getStringMapper("TRFMFTIDR"));
		return transferManifestId;
	}

	@Override
	public MailbagVO findMailbagBillingStatus(MailbagVO mailbagVO) throws SystemException {
		log.entering(MODULE, "findMailbagBillingStatus");
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_MAIL_BILLING_STATUS);
		int index=0;
		qry.setParameter(++index,mailbagVO.getCompanyCode());
		qry.setParameter(++index,mailbagVO.getMailSequenceNumber());
		return qry.getSingleResult(new MailbagBillingStatusMapper());
	}

	private static class MailbagBillingStatusMapper implements Mapper<MailbagVO> {
		MailbagVO mailbagVO = null;
		public MailbagVO map(ResultSet rs) throws SQLException {
			mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			mailbagVO.setBillingStatus(rs.getString("BLGSTA"));
			return mailbagVO;
		}
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#generateConsignmentSummaryReport(com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO)
	 *	Added by 			: A-9084 on 12-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public ConsignmentDocumentVO generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.entering(MODULE, "generateConsignmentSummaryReport");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		consignmentDocumentVO = new ConsignmentDocumentVO();
		Query query = getQueryManager().createNamedNativeQuery(
				GENERATE_CONSIGNMENT_DETAILS_SUMMARY_REPORT);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(3, consignmentFilterVO.getPaCode());
		query.setParameter(4, consignmentFilterVO.getCompanyCode());
		query.setParameter(5, consignmentFilterVO.getConsignmentNumber());
		query.setParameter(6, consignmentFilterVO.getPaCode());
		List<ConsignmentDocumentVO> consignmentDocumentVOs =  query.getResultList(new ConsignmentSummaryReportsMultiMapper());
		if (consignmentDocumentVOs!= null && !consignmentDocumentVOs.isEmpty()) {
			consignmentDocumentVO = consignmentDocumentVOs.get(0);
		}
		return consignmentDocumentVO;
	}


	@Override
	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) throws SystemException {
		log.entering(MODULE, "saveUploadedForceMajeureData");
		String processStatus = "OK";
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		int index = 0;
		Procedure procedure = getQueryManager().createNamedNativeProcedure(MAIL_OPERATIONS_SAVE_FORCE_MAJEURE_REQUEST_FOR_UPLOAD);
		procedure.setParameter(++index, fileUploadFilterVO.getCompanyCode());
		procedure.setParameter(++index, fileUploadFilterVO.getProcessIdentifier());
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		processStatus = (String) procedure.getParameter(index);
		log.log(Log.FINE, "ProcessStatus-->", processStatus);
		log.exiting(MODULE, "saveUploadedForceMajeureData");
		return processStatus;
	}



	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																				   Collection<ManifestFilterVO> manifestFilterVOs) throws SystemException, PersistenceException {
		Collection<ImportFlightOperationsVO> importFlightOperationsVOs = null;
		String baseQuery = getQueryManager()
				.getNamedNativeQueryString(FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS);
		String baseQueryOne = getQueryManager()
				.getNamedNativeQueryString(FIND_MAILINBOUND_FLIGHT_OPERATIONS_DETAILS_ONE);
		ImportFlightOperationsFilterQuery importFlightOperationsFilterQuery = new ImportFlightOperationsFilterQuery(filterVO, manifestFilterVOs, baseQuery, baseQueryOne);
		Query qry = importFlightOperationsFilterQuery;
		importFlightOperationsVOs = qry
				.getResultList(new ImportFlightOperationsMultiMapper());
		return importFlightOperationsVOs;
	}
	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO)
			throws SystemException, PersistenceException {
		log.entering(OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT, "findOffloadULDDetailsAtAirport");
		String  baseQuery = getQueryManager().getNamedNativeQueryString(OPR_FLTHANDLING_FIND_OFFLOAD_ULD_DETAILS_AT_AIRPORT);
		Query query = new ULDReceiptOffloadFilterQuery(baseQuery, filterVO);
		return query.getResultList(new OffloadULDDetailsMultiMapper());
	}
	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																	Collection<ManifestFilterVO> manifestFilterVOs) throws SystemException, PersistenceException {
		log.entering(FIND_EXPORTFLIGHT_OPERATIONS_DETAILS, "findExportFlightOperationsDetails");
		Collection<ManifestVO> exportFlightOperationsVOs = null;
		String baseQry = getQueryManager()
				.getNamedNativeQueryString(FIND_EXPORTFLIGHT_OPERATIONS_DETAILS);
		Query qry = new ExportFlightOperationsFilterQuery(filterVO, manifestFilterVOs, baseQry);
		exportFlightOperationsVOs = qry.getResultList(new ExportFlightOperationsMultiMapper());
		return exportFlightOperationsVOs;
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#fetchConsignmentDetailsForUpload(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
	 *	Added by 			: A-6245 on 22-Dec-2020
	 * 	Used for 	:	IASCB-81526
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException {
		log.entering(MODULE, "fetchConsignmentDetailsForUpload");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FETCH_CONSIGNMENT_DETAILS_FOR_UPLOAD);
		int index = 0;
		query.setParameter(++index, fileUploadFilterVO.getCompanyCode());
		query.setParameter(++index, fileUploadFilterVO.getFileType());
		consignmentDocumentVOs = query.getResultList(new ConsignmentDetailsForUploadMultiMapper());
		log.exiting(MODULE, "fetchConsignmentDetailsForUpload");
		return consignmentDocumentVOs;
	}


	@Override
	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.entering(MODULE, "findContainerJourneyID");
		Collection<ContainerDetailsVO> containerJourneyIdDetails = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_CONTAINER_JOURNEY_ID);
		int index = 0;
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index, consignmentFilterVO.getContainerNumber());
		query.setParameter(++index, consignmentFilterVO.getConsignmentFromDate().toDisplayDateOnlyFormat());
		query.setParameter(++index, consignmentFilterVO.getConsignmentToDate().toDisplayDateOnlyFormat());
		containerJourneyIdDetails = query.getResultList(new ContainerJourneyIDMapper());
		log.exiting(MODULE, "findContainerJourneyID");
		return containerJourneyIdDetails;
	}

	@Override
	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO)
			throws SystemException, PersistenceException {
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(GET_MAILBAGS_ARRIVAL);
		qry.setParameter(++index, mailArrivalVO.getCompanyCode());
		qry.setParameter(++index, mailArrivalVO.getCarrierId());
		qry.setParameter(++index, mailArrivalVO.getFlightNumber());
		qry.setParameter(++index, mailArrivalVO.getFlightSequenceNumber());

		StringBuilder mailbagId = new StringBuilder("");
		String joinQuery = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				joinQuery = constructMailbagIdQuery(mailbagId, joinQuery, containerDetailsVO);
			}
		}
		qry.append(" AND MST.MALSEQNUM IN ");
		qry.append(joinQuery);
		qry.append(")");

		return qry.getResultList(new MailbagMapper());
	}


	private String constructMailbagIdQuery(StringBuilder mailbagId, String joinQuery,
										   ContainerDetailsVO containerDetailsVO) {
		if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
			Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
			if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					mailbagId.append(mailbagVO.getMailSequenceNumber()).append(",");
				}
				String[] mailbags = mailbagId.toString().split(",");
				joinQuery = getWhereClause(mailbags);
			}
		}
		return joinQuery;
	}


	@Override
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO)
			throws BusinessDelegateException, SystemException {
		log.entering(MODULE, "findAssignFlightAuditDetails");
		Collection<AuditDetailsVO> auditDetailsVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_FLIGHT_AUDIT_DETAILS);
		int index = 0;
		query.setParameter(++index, mailAuditFilterVO.getFlightNumber());
		query.setParameter(++index, mailAuditFilterVO.getFlightDate());
		if(mailAuditFilterVO.getAssignPort()!=null){
			query.append("AND FLTAUD.ARPCOD = ?");
			query.setParameter(++index, mailAuditFilterVO.getAssignPort());
		}
		auditDetailsVOs = query.getResultList(new AssignFlightAuditMapper());
		log.exiting(MODULE, "findAssignFlightAuditDetails");
		return auditDetailsVOs;
	}

	@Override
	public Collection<MailAcceptanceVO>  findContainerVOs(MailAcceptanceVO mailAcceptanceVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findContainerVOs");
		Collection<MailAcceptanceVO> mailAcceptanceVOs = null;

		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_FLIGHT_PURE_CONTAINER_DETAILS);
		int index = 0;

		query.setParameter(++index, mailAcceptanceVO.getCompanyCode());
		query.setParameter(++index, mailAcceptanceVO.getCarrierId());
		query.setParameter(++index, mailAcceptanceVO.getFlightNumber());
		query.setParameter(++index, mailAcceptanceVO.getFlightSequenceNumber());
		query.setParameter(++index, mailAcceptanceVO.getFlightDestination());
		mailAcceptanceVOs=query.getResultList(new PureTransferContainerMapper());

		return mailAcceptanceVOs;

	}

	@Override
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo) throws SystemException {
		log.entering(MODULE, LIST_MAILBAG_SECURITY_DETAILS);

		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;

		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS);
		int index = 0;

		query.setParameter(++index, mailScreeningFilterVo.getCompanyCode());
		query.setParameter(++index, mailScreeningFilterVo.getMailBagId());
		query.setParameter(++index, mailScreeningFilterVo.getCompanyCode());
		query.setParameter(++index, mailScreeningFilterVo.getMailBagId());
		query.setSensitivity(true);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			this.log.exiting(MODULE, LIST_MAILBAG_SECURITY_DETAILS);
			mailbagVO.setSecurityDetailsPresent(true);
			return mailbagVO;
		} else {
			Query query2 = getQueryManager()
					.createNamedNativeQuery(MAIL_OPERATIONS_LIST_MAILBAG_SECURITY_DETAILS_MAIL_SEQUENCE_NUMBER);
			int indexs = 0;

			query2.setParameter(++indexs, mailScreeningFilterVo.getCompanyCode());
			query2.setParameter(++indexs, mailScreeningFilterVo.getMailBagId());
			mailbagVOs = query2.getResultList(new MailbagSecurityDetailsMapper());
			mailbagVO = mailbagVOs.get(0);
			this.log.exiting(MODULE, LIST_MAILBAG_SECURITY_DETAILS);
			return mailbagVO;
		}

	}
	@Override
	public String findAgentCodeFromUpuCode(String cmpCode, String upuCode) throws SystemException {
		log.entering(MODULE, "findAgentCodeFromUpuCode");
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_AGENT_CODE_FROM_UPUCODE);
		int index = 0;
		query.setParameter(++index,upuCode);
		query.setParameter(++index, cmpCode);
		String agentCode = query.getSingleResult(getStringMapper("AGTCOD"));
		log.exiting(MODULE, "findAgentCodeFromUpuCode");
		return agentCode;

	}
	@Override
	public String findRoutingDetails(String companyCode, long malseqnum) throws SystemException {
		log.entering(MODULE, "findRoutingDetails");

		String pouValues = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_ROUTING_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, malseqnum);
		List<String> pouVal = query.getResultList(new Mapper<String>() {
			public String map(ResultSet rs) throws SQLException {
				log.entering("Mapper", "map");
				return rs.getString("POU");
			}
		});
		StringBuilder sb = new StringBuilder();
		if( pouVal!=null && !pouVal.isEmpty()){
			Iterator<String> pouValue = pouVal.iterator();
			while (pouValue.hasNext()) {
				sb.append(pouValue.next() + ",");
			}
			pouValues = sb.toString();
			pouValues = pouValues.substring(0, pouValues.length() - 1);
		}
		return pouValues;
	}
	@Override
	public Collection<MailbagVO> findAWBAttachedMailbags(MailbagVO mailbag,String consignmentNumber) throws SystemException {
		log.entering(MODULE, "findAWBAttachedMailbags");
		Collection<MailbagVO> mailBags = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_AWB_ATTACHED_MAIL_DETAILS);
		int index = 0;
		query.setParameter(++index, mailbag.getCompanyCode());
		query.setParameter(++index, mailbag.getDuplicateNumber());
		query.setParameter(++index, mailbag.getSequenceNumber());
		query.setParameter(++index, mailbag.getDocumentNumber());
		query.setParameter(++index, consignmentNumber);
		query.setSensitivity(true);
		mailBags=query.getResultList(new CarditPawbShipmentDetailsMapper());
		return mailBags;
	}

	public Collection<MailInConsignmentVO>findMailInConsignment(ConsignmentFilterVO consignmentFilterVO ) throws SystemException{
		Collection<MailInConsignmentVO> mailInConsignmentVOs=null;
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_DOCUMENT_DETAILS);
		int index = 0;
		query.setParameter(++index, consignmentFilterVO.getCompanyCode());
		query.setParameter(++index,  consignmentFilterVO.getConsignmentNumber());
		query.setParameter(++index, consignmentFilterVO.getPaCode());
		mailInConsignmentVOs = query.getResultList(new MaintainConsignmentMapper());
		log.log(Log.INFO, " ###### Query for execution ", query.toString());
		return mailInConsignmentVOs;
	}
	/**
	 * @author A-8353
	 * @param cmpcod
	 * @param malSeqNum
	 * @param agentId
	 * @return
	 * @throws SystemException
	 */
	@Override
	public ConsignmentScreeningVO findRegulatedCarrierForMailbag(String cmpcod, long malSeqNum) throws SystemException {
		log.entering(MODULE,MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG );

		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		ConsignmentScreeningVO consignmentScreeningVO=null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_REGULATED_CARRIER_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, cmpcod);
		query.setParameter(++index, malSeqNum);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			consignmentScreeningVO= mailbagVO.getConsignmentScreeningVO().iterator().next();
		}
		return consignmentScreeningVO;
	}


	public MailbagVO findMailbagDetails(String mailId, String companyCode)
			throws SystemException {

		log.entering(MODULE, "findMailbagDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILSEQUENCE_NUMBER_FROM_MAILIDR);
		int indx = 0;
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, mailId);

		MailbagVO mailbagvo = query.getSingleResult(new MailbagDetailsForOrgAndDstMapper());

		log.exiting(MODULE, "findMailbagDetails");
		return mailbagvo;


	}

	/**
	 * @author A-10647
	 * @param FlightLoadPlanContainerVO
	 * @return FlightLoanPlanContainerVOs
	 * @throws SystemException
	 */
	@Override
	public Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO) throws SystemException{
		log.entering(MODULE,MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER );

		List<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LOADPLAN_VERSIONS_FORCONTAINER);
		int index = 0;
		query.setParameter(++index, loadPlanVO.getCompanyCode());
		query.setParameter(++index, loadPlanVO.getContainerNumber());
		query.setParameter(++index, loadPlanVO.getUldReferenceNo());
		flightLoadPlanContainerVOs = query.getResultList(new LoadPlanDetailsForContainerMapper());
		return flightLoadPlanContainerVOs;
	}

	/**
	 * @author A-9477
	 * @param SearchContainerFilterVO
	 * @return FlightLoanPlanContainerVO
	 * @throws SystemException
	 */
	@Override
	public Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO)
			throws SystemException {
		log.entering(MODULE, "findLoadPlandetails");

		List<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LOADPLAN_DETAILS_FOR_FLIGHT);
		int index = 0;
		query.setParameter(++index, searchContainerFilterVO.getCompanyCode());
		query.setParameter(++index, searchContainerFilterVO.getCarrierId());
		query.setParameter(++index, searchContainerFilterVO.getFlightNumber());
		query.setParameter(++index, Long.parseLong(searchContainerFilterVO.getFlightSeqNumber()));
		if(searchContainerFilterVO.getSegOrigin()!=null) {
			query.append(" AND LODPLNCON.SEGORG    = ? ");
			query.setParameter(++index, searchContainerFilterVO.getSegOrigin());
		}
		if(searchContainerFilterVO.getSegDestination()!=null) {
			query.append(" AND LODPLNCON.SEGDST    = ? ");
			query.setParameter(++index, searchContainerFilterVO.getSegDestination());
		}
		query.append(" AND LODPLNVER =(SELECT MAX(LODPLNVER) FROM MALLODPLNFLTCON LODPLN ");
		query.append(" WHERE LODPLN.CMPCOD = LODPLNCON.CMPCOD ");
		query.append(" AND LODPLN.FLTCARIDR = LODPLNCON.FLTCARIDR ");
		query.append(" AND LODPLN.FLTNUM = LODPLNCON.FLTNUM ");
		query.append(" AND LODPLN.FLTSEQNUM = LODPLNCON.FLTSEQNUM ");
		if(searchContainerFilterVO.getSegOrigin()!=null) {
			query.append(" AND LODPLN.SEGORG = LODPLNCON.SEGORG ");
		}
		if(searchContainerFilterVO.getSegDestination()!=null) {
			query.append(" AND LODPLN.SEGDST = LODPLNCON.SEGDST ");
		}
		query.append(" ) ");
		flightLoadPlanContainerVOs = query.getResultList(new LoadPlanDetailsForContainerMapper());
		return flightLoadPlanContainerVOs;
	}

	/**
	 * @author U-1532
	 */
	public ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(ScannedMailDetailsVO scannedMailDetailsVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findLatestContainerAssignmentForUldDelivery");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_LATEST_CONTAINER_ASSIGNMENT_FOR_ULDDELIVERY);
		qry.setParameter(++index, scannedMailDetailsVO.getCompanyCode());
		qry.setParameter(++index, scannedMailDetailsVO.getContainerNumber());
		qry.setParameter(++index, scannedMailDetailsVO.getAirportCode());

		if (scannedMailDetailsVO.getFlightNumber() != null && !scannedMailDetailsVO.getFlightNumber().isEmpty()) {
			qry.append(" AND MST.FLTNUM = ?");
			qry.setParameter(++index, scannedMailDetailsVO.getFlightNumber());
			if (scannedMailDetailsVO.getFlightDate() != null) {

				
				qry.append(" AND TO_NUMBER(TO_CHAR(TRUNC(FLT.FLTDAT),'YYYYMMDD')) = TO_NUMBER(TO_CHAR(TO_DATE(?), 'YYYYMMDD')) ");
				qry.setParameter(++index, scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
			}
			if (scannedMailDetailsVO.getCarrierCode() != null) {
				qry.append(" AND MST.FLTCARCOD = ? ");
				qry.setParameter(++index, scannedMailDetailsVO.getCarrierCode());
			}

		} else {
			qry.append(
					" AND MST.ASGDATUTC =(SELECT MAX (ASGDATUTC) FROM MALFLTCON WHERE CMPCOD = ? AND CONNUM = ?  AND POU = ? ) ");
			qry.setParameter(++index, scannedMailDetailsVO.getCompanyCode());
			qry.setParameter(++index, scannedMailDetailsVO.getContainerNumber());
			qry.setParameter(++index, scannedMailDetailsVO.getAirportCode());
		}

		qry.setSensitivity(true);
		containerAssignMentVO = qry.getSingleResult(new ContainerAssignmentMapper());
		log.exiting(MODULE, "findLatestContainerAssignmentForUldDelivery");
		return containerAssignMentVO;
	}
	@Override
	public Collection<ConsignmentScreeningVO> findRAacceptingForMailbag(String companyCode, long  mailSequenceNumber)
			throws SystemException {
		log.entering(MODULE, MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG);
		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_RA_ACCEPTING_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailSequenceNumber);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, mailSequenceNumber);
		query.setSensitivity(true);
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		return mailbagVO.getConsignmentScreeningVO();
	}

	@Override
	public String findRoutingDetailsForMailbag(String companyCode, long malseqnum, String airportCode)
			throws SystemException {
		log.entering(MODULE, "findRoutingDetailsForMailbag");
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_ROUTING_DETAILS_FOR_MAILBAG);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, malseqnum);
		query.setParameter(++index, airportCode);
		return query.getSingleResult(getStringMapper("POL"));
	}
	/**
	 * @author A-8353
	 */
	@Override
	public long findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException {
		log.entering(MODULE,MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING );
		long result;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_LATEST_REGULATED_AGENT_ISSUING);
		int index = 0;
		query.setParameter(++index, consignmentScreeningVO.getCompanyCode());
		query.setParameter(++index, consignmentScreeningVO.getScreeningLocation());
		if (MailConstantsVO.SCREEN_LEVEL_VALUE.equals(consignmentScreeningVO.getScreenLevelValue())){
			query.append("AND DTL.MALSEQNUM    = ?");
			query.setParameter(++index, consignmentScreeningVO.getMalseqnum());

		}
		else{
			query.setParameter(++index, consignmentScreeningVO.getConsignmentNumber());
			query.append("AND DTL.CSGDOCNUM    = ?");
		}
		query.setSensitivity(true);
		result=query.getSingleResult(getLongMapper("SERNUM"));
		return result;
	}
	/**
	 * @author A-8353
	 * @throws SystemException
	 *
	 */
	@Override
	public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException {
		log.entering(MODULE,MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM );

		List<MailbagVO> mailbagVOs = null;
		MailbagVO mailbagVO = null;
		Collection<ConsignmentScreeningVO>consignmentScreeningVOs=null;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_SCREENING_METHOD_WITHOUT_AGENT_SERNUM);
		int index = 0;
		query.setParameter(++index, consignmentScreeningVO.getCompanyCode());
		query.setParameter(++index, consignmentScreeningVO.getScreeningLocation());
		if (MailConstantsVO.SCREEN_LEVEL_VALUE.equals(consignmentScreeningVO.getScreenLevelValue())){
			query.append("AND DTL.MALSEQNUM    = ?");
			query.setParameter(++index, consignmentScreeningVO.getMalseqnum());

		}
		else{
			query.setParameter(++index, consignmentScreeningVO.getConsignmentNumber());
			query.append("AND DTL.CSGDOCNUM    = ?");
		}
		mailbagVOs = query.getResultList(new MailbagSecurityDetailsMapper());
		mailbagVO = mailbagVOs.get(0);
		if (mailbagVO != null && !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			consignmentScreeningVOs= mailbagVO.getConsignmentScreeningVO();
		}
		return consignmentScreeningVOs;
	}


	@Override
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) throws SystemException {

		log.entering(MODULE,FETCH_MAIL_INDICATOR );

		Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> operationalFlightVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(FETCH_MAIL_INDICATOR);
		int index = 0;
		int first = 0;

		String companyCode = getCompanyCode(flightListingFilterVOs);
		String airportCode = getAirportCode(flightListingFilterVOs);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airportCode);

		if(Objects.nonNull(flightListingFilterVOs)) {
			query.append("AND (FLTSEG.FLTCARIDR,FLTSEG.FLTNUM,FLTSEG.FLTSEQNUM ) IN ( ");
			for(FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if(first==0) {
					first=1;
				}
				else {
					query.append(" , ");
				}
				query.append(" ( ? , ? , ? ) ");
				query.setParameter(++index, flightListingFilterVO.getCarrierId());
				query.setParameter(++index, flightListingFilterVO.getFlightNumber());
				query.setParameter(++index, flightListingFilterVO.getFlightSequenceNumber());

			}
			query.append(")");
		}

		operationalFlightVOs = query.getResultList(new FetchMailindicatorMapper());


		return operationalFlightVOs;
	}

	private String getAirportCode(Collection<FlightListingFilterVO> flightListingFilterVOs) {
		String getAirportCode = null;
		if(flightListingFilterVOs != null && !flightListingFilterVOs.isEmpty()) {
			for(FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if(flightListingFilterVO.getAirportCode() !=null){
					return 	flightListingFilterVO.getAirportCode();
				}

			}
		}
		return getAirportCode;
	}


	private String getCompanyCode(Collection<FlightListingFilterVO> flightListingFilterVOs) {
		String companyCode = null;
		if(flightListingFilterVOs != null && !flightListingFilterVOs.isEmpty()) {
			for(FlightListingFilterVO flightListingFilterVO : flightListingFilterVOs) {
				if(flightListingFilterVO.getCompanyCode() !=null){
					return 	flightListingFilterVO.getCompanyCode();
				}

			}
		}
		return companyCode;

	}

	/**
	 * @param companyCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159276 on 27-Sep-2022
	 */
	public Collection<PostalAdministrationVO> getPADetails(String companyCode) throws SystemException, PersistenceException {
		log.entering(MODULE, "getPADetails");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_PA_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);

		log.exiting(MODULE, "getPADetails");
		return query.getResultList(new PAMasterDataDetailsMapper());
	}

	@Override
	public Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) throws SystemException, PersistenceException {
		log.entering(MODULE, "getOfficeOfExchangeDetails");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_OFFICE_OF_EXCHANGE_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);

		log.exiting(MODULE, "getOfficeOfExchangeDetails");
		return query.getResultList(new OfficeOfExchangeMapper());
	}

	@Override
	public Collection<SecurityScreeningValidationVO> checkForSecurityScreeningValidation(SecurityScreeningValidationFilterVO
																								 securityScreeningValidationFilterVO) throws SystemException {
		log.entering(MODULE,MAIL_OPERATIONS_CHECK_FOR_SECURITYSCREENING_VALIDATIONS );
		String query = getQueryManager().getNamedNativeQueryString(MAIL_OPERATIONS_CHECK_FOR_SECURITYSCREENING_VALIDATIONS);
		Query qry = null;
		qry = new SecurityScreeningValidationFilterQuery(query, securityScreeningValidationFilterVO);
		return qry.getResultList(new SecurityScreeningValidationsMapper());

	}

	/**
	 * @author 204084
	 * Added as part of CRQ IASCB-164529
	 * @param destinationAirportCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(String destinationAirportCode) throws SystemException {
		log.entering(MODULE+ LOG_DELIMITER, "getPlannedRoutingIndexDetails");
		Collection<RoutingIndexVO> routingIndexVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(GET_PLANNED_ROUTING_INDEX_DETAILS);
		if(isOracleDataSource()){
			qry.append(" where sysdate between pldeffdat and plddisdat and dstcod = ? ");
		}
		else{
			qry.append(" where current_date between pldeffdat and plddisdat and dstcod = ? ");
		}
		qry.setParameter(++index, destinationAirportCode);
		routingIndexVOs = qry.getResultList(new RoutingIndexDetailsMapper());
		log.exiting(MODULE+ LOG_DELIMITER, "getPlannedRoutingIndexDetails");
		return routingIndexVOs;
	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#generateCN46ConsignmentReport(com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO)
	 *	Added by 			: A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "generateCN46ConsignmentReport");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				GENERATE_CN46_CONSIGNMENT_DOCUMENT_DETAILS_REPORT);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getTransferManifestId());
		consignmentDocumentVOs = query.getResultList(new ManifestCN46ReportMultiMapper());
		log.log(Log.FINE,LOG_DELIMITER, consignmentDocumentVO);
		return consignmentDocumentVOs;
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#generateCN46ConsignmentSummaryReport(com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO)
	 *	Added by 			: A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO)
			throws SystemException {
		log.entering(MODULE, "generateCN46ConsignmentSummaryReport");
		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(GENERATE_CN46_CONSIGNMENT_DETAILS_SUMMARY_REPORT);
		query.setParameter(1, consignmentFilterVO.getCompanyCode());
		query.setParameter(2, consignmentFilterVO.getTransferManifestId());
		consignmentDocumentVOs = query
				.getResultList(new ManifestCN46SummaryReportMultiMapper());

		return consignmentDocumentVOs;
	}

	/**
	 * @param mailMasterDataFilterVO
	 * @return MailbagDetailsVo
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159267 on 20-Oct-2022
	 */
	@Override
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) throws SystemException, PersistenceException {
		log.entering(MODULE, "getMailbagDetails");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_MAILBAG_DETAILS);
		int index = 0;
		if (isOracleDataSource()) {
			qryString = getQueryManager().getNamedNativeQueryString(
					MAIL_OPERATIONS_GET_MAILBAG_DETAILS_FOR_ORACLE);
		}
		Query query = getQueryManager().createNativeQuery(qryString);
		LocalDate utcCurrentTimestamp = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		int lastScanTime = mailMasterDataFilterVO.getLastScanTime() / 24;
		query.setParameter(++index, mailMasterDataFilterVO.getNoOfDaysToConsider());
		query.setParameter(++index, utcCurrentTimestamp.toGMTDate().addDays(-lastScanTime));
		query.setParameter(++index, mailMasterDataFilterVO.getRecordSize());
		log.exiting(MODULE, "getMailbagDetails");
		return query.getResultList(new MailbagDetailsForInterfaceMapper());
	}


	/**
	 * @param companyCode
	 * @return MailSubClassVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204084
	 * Added for IASCB-172483 on 15-Oct-2022
	 */
	public Collection<MailSubClassVO> getSubclassDetails(String companyCode) throws SystemException, PersistenceException {
		log.entering(MODULE, "getSubclassDetails");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_SUBCLASS_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);

		log.exiting(MODULE, "getSubclassDetails");
		return query.getResultList(new MailSubClassCodeMapper());
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return OfficeOfExchangeVO
	 * @throws SystemException
	 * @author 204082
	 * Added for IASCB-164537 on 09-Nov-2022
	 */
	public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) throws SystemException {
		log.entering(MODULE, "getExchangeOfficeDetails");
		String qryString = getQueryManager().getNamedNativeQueryString(
				MAIL_OPERATIONS_GET_EXCHANGE_OFFICE_DETAILS);
		Query query = getQueryManager().createNativeQuery(qryString);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airportCode);
		query.setParameter(++index, airportCode);
		query.setParameter(++index, airportCode);

		log.exiting(MODULE, "getExchangeOfficeDetails");
		return query.getResultList(new OfficeOfExchangeMapper());
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO#findCN46TransferManifestDetails(com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO)
	 *	Added by 			: A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param transferManifestVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO)
			throws SystemException {
		log.entering(MODULE, "findCN46TransferManifestDetails");
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_CN46_TRANSFER_MANIFEST_TDTL);
		qry.setParameter(++index, transferManifestVO.getCompanyCode());
		qry.setParameter(++index, transferManifestVO.getTransferManifestId());
		consignmentDocumentVOs = qry.getResultList(new TransferManifestConsignmentDetail());
		log.exiting(MODULE , "findCN46TransferManifestDetails");
		return consignmentDocumentVOs;
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return MailbagVO
	 * @throws SystemException
	 * @author 204084
	 * Added as part of CRQ IASCB-162362
	 */
	public Collection<MailbagVO> getMailbagDetailsForValidation(String companyCode, String airportCode) throws SystemException {
		log.entering(MODULE + LOG_DELIMITER, "getMailbagDetailsForValidation");
		int index = 0;

		Collection<MailbagVO> mailBagVos;

		Query firstQuery = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_DETAILS_FOR_VALIDATION_FIRST_PART);
		Query secondQuery = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_DETAILS_FOR_VALIDATION_SECOND_PART);

		if (isOracleDataSource()) {
			firstQuery.append("AND ((TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD'))) - (TO_NUMBER(TO_CHAR(MST.DSPDAT,'YYYYMMDD')))) <=(CASE WHEN MST.POACOD = 'US001' THEN TO_NUMBER (DOM.PARVAL) ELSE TO_NUMBER (INT.PARVAL) END)");
			secondQuery.append("WHERE ((TO_NUMBER(TO_CHAR(SYSDATE,'YYYYMMDD'))) - (TO_NUMBER(TO_CHAR(MST.DSPDAT,'YYYYMMDD')))) <=(CASE WHEN MST.POACOD = 'US001' THEN TO_NUMBER (DOM.PARVAL) ELSE TO_NUMBER (INT.PARVAL) END)) as x");
		} else {
			firstQuery.append("AND ((TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD'))) - (TO_NUMBER(TO_CHAR(MST.DSPDAT,'YYYYMMDD')))) <=(CASE WHEN MST.POACOD = 'US001' THEN TO_NUMBER (DOM.PARVAL) ELSE TO_NUMBER (INT.PARVAL) END)");
			secondQuery.append("WHERE ((TO_NUMBER(TO_CHAR(current_date,'YYYYMMDD'))) - (TO_NUMBER(TO_CHAR(MST.DSPDAT,'YYYYMMDD')))) <=(CASE WHEN MST.POACOD = 'US001' THEN TO_NUMBER (DOM.PARVAL) ELSE TO_NUMBER (INT.PARVAL) END)) as x");
		}
		firstQuery.combine(secondQuery);

		firstQuery.setParameter(++index, airportCode);
		firstQuery.setParameter(++index, companyCode);
		firstQuery.setParameter(++index, airportCode);
		firstQuery.setParameter(++index, companyCode);
		firstQuery.setParameter(++index, companyCode);
		firstQuery.setParameter(++index, companyCode);
		firstQuery.setParameter(++index, airportCode);
		firstQuery.setParameter(++index, companyCode);
		firstQuery.setParameter(++index, companyCode);

		mailBagVos = firstQuery.getResultList(new MailbagDetailsForValidationMapper());
		log.exiting(MODULE + LOG_DELIMITER, "getMailbagDetailsForValidation");
		return mailBagVos;
	}	
/**
	 * @author U-1532
	 * Added for CRQ ICRD-111886 by A-5526
	 * 	Parameters	:	@param companyCode,paCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public String findDensityfactorForPA(String companyCode,
			String paCode) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findDensityfactorForPA");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_DENSITY_FACTOR_FOR_PA);
		query.setParameter(1, companyCode);
		query.setParameter(2, paCode);
		log.exiting(MODULE, "findDensityfactorForPA");
		return query.getSingleResult(getStringMapper("PARVAL"));
	}
	/**
	 * @throws SystemException 
	 * 
	 */
	public String  findApplicableRegFlagForMailbag(String companyCode, long sequencenum) throws SystemException {
		log.entering(MODULE,MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL);
		String result;
		Query query = getQueryManager().createNamedNativeQuery(MAIL_OPERATIONS_FIND_APPLICABLE_REGULATION_FLAG_FOR_MAIL);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, sequencenum);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, sequencenum);
		result=query.getSingleResult(getStringMapper("APLREGFLG"));
       return result;
		
	}
		public Collection<MailAcceptanceVO>  fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) throws SystemException,
	PersistenceException {
		

		log.entering(MODULE, "fetchFlightPreAdviceDetails");
		Collection<MailAcceptanceVO> preAdviceFlightVOs=  new ArrayList<>();
		int index = 0;
		for(FlightFilterVO flightFilterVO :flightFilterVOs ) {
			Query qry = getQueryManager().createNamedNativeQuery(
					OUTBOUND_FLIGHT_PREADVICE_DETAILS_NEW);
			qry.setParameter(++index, flightFilterVO.getCompanyCode());
			qry.setParameter(++index, flightFilterVO.getFlightCarrierId());
			qry.setParameter(++index,flightFilterVO.getFlightNumber());  
			qry.setParameter(++index,flightFilterVO.getFlightSequenceNumber());
			MailAcceptanceVO preAdviceFlightVO = qry.getSingleResult(new OutboundFlightPreAdviceMapper());
			if (preAdviceFlightVO!=null) {
				preAdviceFlightVOs.add(preAdviceFlightVO);
			}
			index = 0;
		}
		return preAdviceFlightVOs;
	}


		@Override
		public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) throws SystemException {
		int index = 0;
		String query = getQueryManager().getNamedNativeQueryString(FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB);
		StringBuilder sb = new StringBuilder();
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARLMST ARL WHERE ARL.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARL.ARLIDR=ASGFLTSEG.FLTCARIDR AND ARL.TWOAPHCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getCarrierCodes().replace(",", "','")).append("'))");
		}
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARPMST ARP WHERE ARP.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARP.ARPCOD=ASGFLTSEG.POL AND ARP.CNTCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getPointOfLadingCountries().replace(",", "','")).append("'))");
		}
		if (Objects.nonNull(mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries())
				&& !mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries().trim().isEmpty()) {
			sb.append(" AND EXISTS (SELECT 1 FROM SHRARPMST ARP WHERE ARP.CMPCOD = ASGFLTSEG.CMPCOD");
			sb.append(" AND ARP.ARPCOD=ASGFLTSEG.POU AND ARP.CNTCOD IN ('");
			sb.append(mailInboundAutoAttachAWBJobScheduleVO.getPointOfUnladingCountries().replace(",", "','")).append("'))");
		}
		if (isOracleDataSource()) {
			sb.append(" AND ( TO_NUMBER(TO_CHAR(SYS_EXTRACT_UTC(SYSTIMESTAMP), 'YYYYMMDDHH24MISS')) >=")
					.append(" TO_NUMBER(TO_CHAR(LEG.STAUTC -(SELECT TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/1440")
					.append(" FROM SHRUSEARPPAR ARPPAR WHERE ARPPAR.CMPCOD= LEG.CMPCOD")
					.append(" AND ARPPAR.PARCOD='mail.operations.inboundautoattachawboffset' AND ARPPAR.ARPCOD= ASGFLTSEG.POU")
					.append(" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0),'YYYYMMDDHH24MISS')))").toString();
		} else {
			sb.append(" AND ( TO_NUMBER(TO_CHAR(now() at time zone ('utc'), 'YYYYMMDDHH24MISS')) >=")
					.append(" TO_NUMBER(TO_CHAR(LEG.STAUTC - interval '1 day' *(SELECT TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0'))/1440")
					.append(" FROM SHRUSEARPPAR ARPPAR WHERE ARPPAR.CMPCOD= LEG.CMPCOD")
					.append(" AND ARPPAR.PARCOD='mail.operations.inboundautoattachawboffset' AND ARPPAR.ARPCOD= ASGFLTSEG.POU")
					.append(" AND TO_NUMBER(COALESCE(ARPPAR.PARVAL,'0')) > 0),'YYYYMMDDHH24MISS')))").toString();
		}
		sb.append(" AND EXISTS (SELECT 1 FROM MALMST MAL INNER JOIN MALULDSEGDTL SEGDTL ON MAL.CMPCOD = SEGDTL.CMPCOD");
		sb.append(" AND MAL.MALSEQNUM = SEGDTL.MALSEQNUM ");
		sb.append(" WHERE SEGDTL.CMPCOD = ASGFLTSEG.CMPCOD AND SEGDTL.FLTNUM = ASGFLTSEG.FLTNUM");
		sb.append(" AND SEGDTL.FLTCARIDR = ASGFLTSEG.FLTCARIDR AND SEGDTL.FLTSEQNUM = ASGFLTSEG.FLTSEQNUM");
		sb.append(" AND SEGDTL.SEGSERNUM = ASGFLTSEG.SEGSERNUM AND MAL.MSTDOCNUM IS NULL)");
		query = new StringBuilder(query).append(sb).toString();
		Query qry = getQueryManager().createNativeQuery(query);
		qry.setParameter(++index, mailInboundAutoAttachAWBJobScheduleVO.getCompanyCode());
		return qry.getResultList(new MailInboundAutoAttachAWBFlightsMapper());
	}
	@Override
	public Page<MailTransitVO> findMailTransit(MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws SystemException, PersistenceException {
		log.entering(MODULE, "findMailtransit");
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILTRANSIT);
		PageableNativeQuery<MailTransitVO> pgqry = null;
		Page<MailTransitVO> mailTransitVOs = null;
		
		StringBuilder rankQuery = new StringBuilder();
	    rankQuery.append(MailConstantsVO.MAIL_OPERATIONS_DENSE_RANK_QUERY);
	    rankQuery.append("RESULT_TABLE.CMPCOD,RESULT_TABLE.MALDSTCOD) RANK FROM (");
	    rankQuery.append(query);
		
		
		pgqry= new MailTransitFilterQuery(new MailTransitMapper(), mailTransitFilterVO, rankQuery.toString());
		pgqry.setCacheable(false);
	    mailTransitVOs=pgqry.getPage(pageNumber);
		log.exiting(MODULE, "");
		return mailTransitVOs;
	}
		/**
		 *
		 *	Added by 	: A-8464 on 26-Mar-2018
		 * 	Used for 	:	ICRD-273761
		 *	Parameters	:	@param mailbagEnquiryFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws PersistenceException
		 */
		public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
				throws SystemException, PersistenceException{

			log.entering(MODULE, "findMailbagDetailsForMailbagEnquiryHHT");
			int idx = 0;
			String qryString = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGS_MAILINBOUNDHHT);
			String modifiedStr1 = null;
			if (isOracleDataSource())
			{
				modifiedStr1 = "LISTAGG(RTGCARCOD || ' ' || RTGFLTNUM || ' ' || RTGFLTDAT,',') within GROUP (ORDER BY RTGFLTDAT) ROUTEINFO";
			}
			else
			{
				modifiedStr1 = "string_agg ( RTGCARCOD || ' '|| RTGFLTNUM|| ' '|| RTGFLTDAT, ','  ORDER BY RTGFLTDAT) ROUTEINFO";
			}
			qryString = String.format(qryString, modifiedStr1);

			Query qry = getQueryManager().createNativeQuery(qryString);
			qry.setParameter(++idx,mailbagEnquiryFilterVO.getCompanyCode());
			qry.setParameter(++idx,mailbagEnquiryFilterVO.getMailbagId());
			qry.setParameter(++idx,mailbagEnquiryFilterVO.getScanPort());
			qry.setParameter(++idx,mailbagEnquiryFilterVO.getCompanyCode());// added by A-8353 for ICRD-333808
			qry.setParameter(++idx,mailbagEnquiryFilterVO.getMailbagId());

			return qry.getSingleResult(new MailbagEnquiryMapper());

		}	
        @Override
	    public MailbagVO findMailConsumed(MailTransitFilterVO filterVo) throws SystemException {
		log.entering(MODULE, "findMailConsumed");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_MAIL_CONSUMED_DATA);
		int index = 0;
		qry.setParameter(++index, Long.parseLong(filterVo.getFlightFromDate().toDisplayFormat("yyyyMMddHHmmss")));
		qry.setParameter(++index, Long.parseLong(filterVo.getFlightToDate().toDisplayFormat("yyyyMMddHHmmss")));
		qry.setParameter(++index, filterVo.getAirportCode());
		qry.setParameter(++index, filterVo.getAirportCode());
		qry.setParameter(++index, filterVo.getSegmentDestination());
		qry.setParameter(++index, filterVo.getCarrierCode());
		MailbagVO mailbagVO=  qry.getSingleResult(new MailConsumedMapper());
		log.exiting(MODULE, "findMailConsumed");
		return mailbagVO;   
		}
		
		@Override
	public Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(int noOfDays) throws SystemException{
		
		log.entering(MODULE, "findMailbagsForPAWBCreation");
		Collection<CarditPawbDetailsVO> carditPawbDetailsVOs= null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_FOR_PAWB_CREATION);
		query.setParameter(++index, noOfDays);
		query.setParameter(++index, noOfDays);
		carditPawbDetailsVOs = query.getResultList(new MailBagsForPAWBCreationMapper());
		log.exiting(MODULE, "findMailbagsForPAWBCreation");
		return carditPawbDetailsVOs;
		
	}
	
	/**
	 * @author a-9998
	 * @param carditVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO)
			throws SystemException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMstDocNumForAWBDetails");
		Query query = getQueryManager()
				.createNamedNativeQuery(FIND_MSTDOCNUM_FOR_AWB_DETAILS);
		int index = 0;
		query.setParameter(++index, carditVO.getCompanyCode());
		query.setParameter(++index, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getConsignmentNumber());
		query.setParameter(++index, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getConsignmentSequenceNumber());
		query.setParameter(++index, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getPaCode());
		if(carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getMasterDocumentNumber()!= null){
		query.append(" AND MSTDOCNUM =? ");
		query.setParameter(++index, carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getMasterDocumentNumber());
		}
		return query.getSingleResult(new AWBDetailsMapper());
	}
	@Override
	public Collection<MailResditAddressVO> findMailResditAddtnlAddressDetails(String companyCode, List<Long> addtnAddres )
			throws SystemException, PersistenceException {
		log.entering(MAIL_TRACKING_DEFAULTS_SQLDAO, "findMailResditAddtnlAddressDetails");
		Query query = getQueryManager()
				.createNamedNativeQuery(FIND_MAIL_RESDIT_ADDTNLADDRESS_DETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		if(!addtnAddres.isEmpty()) {
			StringBuilder address=new StringBuilder();
			for (Long add : addtnAddres) {
				if (address.length()!=0) {
					address.append(",'" + add.toString() + "'");
				} else {
					address.append("'" + add.toString() + "'");
				}
			}
			query.append("and msgadddtl.msgaddseqnum in ("+address.toString()+")")	;
		}
		return query.getResultList(new MailResditAddressMultiMapper());
	}
	public MailManifestVO findCN46ManifestDetails(
			OperationalFlightVO opFlightVO) throws SystemException,
			PersistenceException {
		log.entering(this.getClass().getSimpleName(),
				"findCN46ManifestDetails");
		Query query;
		if("N".equals(opFlightVO.getConsignemntPresent())) {
			 query = getQueryManager().createNamedNativeQuery(
					GENERATE_CONSIGNMENT_DETAILS_FORCN46_NOCONSIGNMENT_REPORT);
		}else {
		    query = getQueryManager().createNamedNativeQuery(
				GENERATE_CONSIGNMENT_DETAILS_SUMMARY_FORCN46_REPORT);
		}
		if(MailConstantsVO.MAIL_OUTBOUND_SCRIDR.equals(opFlightVO.getFromScreen())) {
			query=query.append(" AND FLT.POL = ? ");
		}
		if(MailConstantsVO.MAIL_INBOUND_SCRIDR.equals(opFlightVO.getFromScreen())) {
			query=query.append(" AND FLT.POU = ? ");
		}
		if(isOracleDataSource()) {
			query=query.append(" )");
		}else {
			query=query.append(" )AS MST");
		}
		int idx = 0;
		query.setParameter(++idx, opFlightVO.getCompanyCode());
		query.setParameter(++idx, opFlightVO.getCarrierId());
		query.setParameter(++idx, opFlightVO.getFlightNumber());
		query.setParameter(++idx, opFlightVO.getFlightSequenceNumber());
		if(MailConstantsVO.MAIL_OUTBOUND_SCRIDR.equals(opFlightVO.getFromScreen())) {
			query.setParameter(++idx, opFlightVO.getPol());
		}
		if(MailConstantsVO.MAIL_INBOUND_SCRIDR.equals(opFlightVO.getFromScreen())) {
			query.setParameter(++idx, opFlightVO.getPou());
		}
		Collection<MailManifestVO> manifests = query
				.getResultList(new CN46MailManifestMultiMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findCN46ManifestDetails");
		return manifests.iterator().next();
	}
	
	public Collection<ConsignmentRoutingVO> findConsignmentRoutingVosForMailbagScreening(
			String companyCode,Long malseqnum) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findConsignmentRoutingVosForMailbagScreening");
		Collection<ConsignmentRoutingVO> consignmentRoutingVos= null;
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_CONSIGNMENT_ROUTING_FOR_MAILBAG_SCREENING);
		query.setParameter(++index, companyCode);
		query.setParameter(++index, malseqnum);
		consignmentRoutingVos = query.getResultList(new ConsignmentRoutingForMailBagScreeningMapper());
		log.exiting(MODULE, "findConsignmentRoutingVosForMailbagScreening");
		return consignmentRoutingVos;
		
	}  
/**
	 * @author a-10383 This method Checks whether the container is already
	 *         assigned to a flight/destn from the current airport
	 * @param companyCode
	 * @param containerNumber
	 * @param pol
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerAssignmentVO findContainerWeightCapture(String companyCode,
														 String containerNumber) throws SystemException,
			PersistenceException {
		log.entering(MODULE, "findContainerWeightCapture");
		ContainerAssignmentVO containerAssignMentVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				FIND_CONTAINER_WEIGHT_CAPTURE);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, containerNumber);
		qry.setSensitivity(true);

		containerAssignMentVO = qry
				.getSingleResult(new ContainerWeightCaptureMapper());
		return containerAssignMentVO;
	}
}