package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.admin.report.vo.ReportPublishJobVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.OtherCustomsInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RatingDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RoutingVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentCustomsInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigParameterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralRuleConfigDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.formatter.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.neoicargo.awb.modal.AWBIndexModel;
import com.ibsplc.neoicargo.common.types.Pair;
import com.ibsplc.neoicargo.framework.core.async.AsyncInvoker;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.ICargoDomainTypeSupport;
import com.ibsplc.neoicargo.framework.ebl.SecurityAgent;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.builder.HistoryBuilder;
import com.ibsplc.neoicargo.mail.component.builder.MessageBuilder;
import com.ibsplc.neoicargo.mail.component.events.MailEventsProducer;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.closemailinboundflight.CloseMailInboundFlightFeature;
import com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails.EditScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.SaveArrivalDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.saveloadplandetailsformail.SaveLoadPlanDetailsForMailFeature;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.SaveMailbagHistoryFeature;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.SavePAWBDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.SaveSecurityDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.stampresdit.StampResditFeature;
import com.ibsplc.neoicargo.mail.component.proxy.AdminReportProxy;
import com.ibsplc.neoicargo.mail.component.proxy.CraDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.DocumentRepositoryProxy;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.MailMRAProxy;
import com.ibsplc.neoicargo.mail.component.proxy.MsgBrokerMessageProxy;
import com.ibsplc.neoicargo.mail.component.proxy.OperationsFltHandlingProxy;
import com.ibsplc.neoicargo.mail.component.proxy.OperationsShipmentProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.RecoDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAirlineProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedCommodityProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedCustomerProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedULDProxy;
import com.ibsplc.neoicargo.mail.component.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ULDDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MsgBrokerMessageAsyncEProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.events.*;
import com.ibsplc.neoicargo.mail.exception.AttachAWBException;
import com.ibsplc.neoicargo.mail.exception.CapacityBookingProxyException;
import com.ibsplc.neoicargo.mail.exception.CloseFlightException;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.DuplicateDSNException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.FlightClosedException;
import com.ibsplc.neoicargo.mail.exception.FlightDepartedException;
import com.ibsplc.neoicargo.mail.exception.InvalidFlightSegmentException;
import com.ibsplc.neoicargo.mail.exception.InvalidMailTagFormatException;
import com.ibsplc.neoicargo.mail.exception.InventoryForArrivalFailedException;
import com.ibsplc.neoicargo.mail.exception.MailBookingException;
import com.ibsplc.neoicargo.mail.exception.MailDefaultStorageUnitException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyAcceptedException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyReturnedException;
import com.ibsplc.neoicargo.mail.exception.MailbagIncorrectlyDeliveredException;
import com.ibsplc.neoicargo.mail.exception.ReassignmentException;
import com.ibsplc.neoicargo.mail.exception.ReturnNotPossibleException;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.mapper.ClassicVOConversionMapper;
import com.ibsplc.neoicargo.mail.mapper.MailEventsMapper;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.OperationsShipmentUtil;

import com.ibsplc.neoicargo.mail.vo.*;


import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.neoicargo.mailmasters.model.MailboxIdModel;
import com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeFilterModel;
import com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeModel;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;

import static com.ibsplc.neoicargo.mail.vo.DSNVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.neoicargo.mail.vo.DSNVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.neoicargo.mail.vo.DSNVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.neoicargo.mail.vo.MailActivityDetailVO.SLASTATUS_FAILURE;
import static com.ibsplc.neoicargo.mail.vo.MailActivityDetailVO.SLASTATUS_SUCCESS;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.FLIGHT_CLOSURE_ENABLED;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.MAIL_ARRIVAL_NEEDED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ACCEPTED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ARRIVED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_DELIVERED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_MANIFESTED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_OFFLOADED;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE;
import static com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.SLAACTIVITY_ARRIVAL_TO_DELIVERY;
 import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClients;

import javax.annotation.Resource;


/** 
 * @author a-1303
 */
@Primary
@Component("mailController")
@Qualifier("mailController")
@Slf4j
@RegisterJAXRSClients(value={@RegisterJAXRSClient(clazz = MailTrackingDefaultsBI.class, targetService = "neo-mailmasters-business") })
public class MailController {
	@Autowired
	private MailOperationsMapper mailOperationsMapper;
	@Autowired
	ClassicVOConversionMapper classicVOConversionMapper_;
	@Autowired
	CraDefaultsProxy craDefaultsProxy;
	@Autowired
	private Quantities quantities;
	@Autowired
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy;
	@Autowired
	private DocumentRepositoryProxy documentRepositoryProxy;
	@Autowired
	AdminReportProxy adminReportProxy;
	@Autowired
	private SharedCommodityProxy sharedCommodityProxy;
	@Autowired
	private SharedULDProxy sharedULDProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private MailMRAProxy mailOperationsMRAProxy;
	@Autowired
	private RecoDefaultsProxy recoDefaultsProxy;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	@Autowired
	private OperationsFltHandlingProxy operationsFltHandlingProxy;
	@Autowired
	private ULDDefaultsProxy uLDDefaultsProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private SharedAirlineProxy sharedAirlineProxy;
	@Autowired
	private MailMRAProxy mailtrackingMRAProxy;
	@Autowired
	ProductDefaultsProxy productDefaultsProxy;
	@Autowired
	StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;
	@Autowired
	SharedCustomerProxy sharedCustomerProxy;
	@Autowired
	OperationsShipmentProxy operationsShipmentProxy;
	@Autowired
	MailTrackingDefaultsBI mailMasterApi;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	private KeyUtils keyUtils;
	@Autowired
	private DocumentController documentController;
	@Autowired
	private SecurityAgent securityAgent;
	@Autowired
	private AuditUtils auditUtils;
	@Autowired
	private OperationsShipmentUtil operationsShipmentUtil;

	@Autowired
	private ReassignController reassignController;



	@Autowired
	private AutoAttachAWBDetailsFeature autoAttachAWBDetailsFeature;
	@Autowired
	private SaveSecurityDetailsFeature saveSecurityDetailsFeature;
	@Autowired
	private EditScreeningDetailsFeature editScreeningDetailsFeature;
	@Autowired
	private ResditController resditController;
	@Autowired
	private MailEventsMapper mailEventsMapper;
	@Autowired
	private MailEventsProducer mailEventsProducer;

	@Autowired
	private MailTransfer mailTransfer;

	@Autowired
	private AsyncInvoker asyncInvoker;

	@Autowired
	private HistoryBuilder historyBuilder;
	@Autowired
	private MessageBuilder messageBuilder;
	@Resource
	private MailController mailController;

	private static final String CLASS = "MailController";
	private static final String HYPHEN = "-";
	private static final String CSGDOCNUM_GEN_KEY = "CSGDOCNUM_GEN_KEY";
	private static final String ID_SEP = "~";
	private static final String FINDEREXCEPTIO_STRING = "FINDER EXCEPTION IS THROWN";
	private static final String OFFLOADED_FOR_MLD_UPL = "Flight Departed-MLD";
	private static final String SPACE = " ";
	private static final String MODULENAME = "mail.operations";
	private static final String TRANSFER_MANIFEST_KEY = "TRF_MFT_KEY";
	private static final String TRFMFT_KEYTABLE = "MALTRFMFTKEY";
	private static final String CONTAINER_ASSIGNEDFORFLIGHT = "F";
	private static final String CITY_CACHE = "CITY";
	private static final String MODEOFTRANSPORT_ROAD = "3";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String COUNTRY_ORIGIN = "COUNTRYORIGIN";
	private static final String COUNTRY_DESTINATION = "COUNTRYDESTINATION";
	private static final String COUNTRY_CACHE = "COUNTRY";
	private static final String EXCHANGE_CACHE = "EXCHANGE";
	private static final String ORIGINEXCHANGE = "OOE";
	private static final String DESTINATIONEXCHANGE = "DOE";
	private static final String SUBCLS_CACHE = "MailSubClass";
	private static final String ERROR_CACHE = "ERROR";
	private static final String CITY_ORIGIN = "CITYORIGIN";
	private static final String CITY_DESTINATION = "CITYDESTINATION";
	private static final String PAIR_ORIGIN = "CITYPAIRORIGIN";
	private static final String PAIR_DESTINATION = "CITYPAIRDESTINATION";
	private static final String CITYPAIR_CACHE = "CITYPAIRDESTINATION";
	private static final String PA_CACHE = "PACODE";
	private static final String MODULE = "MailController";
	private static final String MTK_IMP_FLT = "MTK_IMP_FLT";
	private static final String MTK_INB_ONLINEFLT_CLOSURE = "MTK_INB_ONLINEFLT_CLOSURE";
	private static final String DEFAULTCOMMODITYCODE_SYSPARAM = "mailtracking.defaults.booking.commodity";
	private static final String IMPORTMRA_REQUIRED = "mailtracking.defaults.importmailstomra";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String MAILSERVICELEVELS = "mail.operations.mailservicelevels";
	private static final String MAILCLASS = "mailtracking.defaults.mailclass";
	private static final String CONTRACTID_ALREADY_EXISTS = "mailtracking.defaults.ux.mailperformance.msg.err.contractidforodpairalreadyexists";
	private static final String CONTRACTID_ALREADY_EXISTS_FOR_SAME_DATE_SPAN = "mailtracking.defaults.ux.mailperformance.msg.err.contractidforsametimeperiodalreadyexists";
	public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
	public static final String LAT_VIOLATED_WAR = "mailtracking.defaults.war.latvalidation";
	private static final String MAIL_ROUTE_INDEX_ERROR = "mailtracking.operations.msg.err.routeindex";
	private static final String LST_DGT_OF_YEAR_FMT = "y";
	public static final String COUNTRY = "logonattributes.country";
	public static final String MAILAWB_STATUS_DETACH = "mail.operations.malawb.status.det";
	private static final String FORMULA_ALREADY_EXISTS_WITH_SAME_PERCENTAGE_FOR_SAME_PRODUCT = "mailtracking.defaults.ux.mailperformance.msg.err.formulaalreadyexistswithsamepercentforsameproduct";
	private static final String BOTH_CONFIGURATION_NOT_ALLOWED = "mailtracking.defaults.ux.mailperformance.msg.err.bothconfigurationnotallowed";
	private static final String SERVICE_RESPONSIVE = "Y";
	private static final String NON_SERVICE_RESPONSIVE = "N";
	private static final String BOTH = "B";
	private static final String DUPLICATE_RECORD = "mailtracking.defaults.ux.mailperformance.msg.err.duplicaterecord";
	private static final String INCENTIVEPRODUCTPARAMETER = "PRD";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_MARKED = "mailtracking.defaults.mailarrival.readyfordeliverymarkedalready";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED = "mailtracking.defaults.mailarrival.readyfordeliveryresdittrigerred";
	public static final String INVALID_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidDeliveryAirportException";
	private Map<String, String> exchangeOfficeMap;
	public static final String INVALID_READYFOR_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidReadyForDeliveryAirportException";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	private static final String AUTOARRIVALFUNCTIONPOINTS = "mail.operations.autoarrivalfunctionpoints";
	private static final String HAS_PRIVILEGE_FOR_VIEW_ALL_MAIL_TRUCK = "mail.operations.viewalltruckflightsforoperatios";
	private static final String TRANSER_STATUS_REJECT = "TRFREJ";
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String LIST_TRANSFER_MANIFEST_SCREENID = "MTK027";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL = "mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String PA_LIST_FOR_PA_BUILT_RATING = "mailtracking.mra.PAlisttoapplycontainerrate";
	private static final String BLANK = "";
	private static final String CONSIGNMENTCAPTURE = "C";
	private static final String IMPORTTRIGGER_DELIVERY = "D";
	private static final String PROXYEXCEPTION = "ProxyException";
	private static final String MAIL_REASSIGN_FROM_CLOSED_FLIGHT = "mail.operations.offloadonreassignment";
	private static final String TRANSFER_END_FROM_OPS = "EXPFLTFIN_TRAMAL";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	public static final String MAIL_OPERATIONS_TRANSFER_TRANSACTION = "mail.operation.transferoutinonetransaction";
	private static final String MAIL_OPS_TRAEND = "TRAEND";
	private static final String SECURITY_SCREENING_MESSGE_TYPE = "SECANDSCREENING";
	private static final String PUBLISH = "PUBLISH";
	private static final String MAIL_OPERATION_SERVICES = "mailOperationsFlowServices";
	private static final String REGULATED_AGENTACCEPTING_MAIL = "mail.operations.regulatedagentacceptingmail";
	private static final String THIRDPARTYRA_ISSUE_MAIL = "mail.operations.thirdpartyraissuemail";
	private static final String RA_ACCEPTANCE_VALIDATION_OVERRIDE = "mail.operations.raacceptancevalidationoverride";
	private static final String FLT_CLS = "FLTCLS";
	private static final String MAIL_MASTER_DATA_TYPE_MALBAGINF = "MALBAGINF";
	private static final String MAIL_MASTER_DATA_TYPE_PACOD = "PACOD";
	private static final String MAIL_MASTER_DATA_TYPE_SUBCLS = "SUBCLS";
	private static final String MAIL_MASTER_DATA_TYPE_EXCHANGE_OFFICE = "EXGOFC";
	private static final String MACC = "MACC";
	private static final String NO_VALUE = "NO_VALUE";
	private static final String DSTARPCNTGRP = "DSTARPCNTGRP";
	private static final String NO_GROUP = "NO_GRP";

	/** 
	* Saves the scanned outbound mails thru the acceptance. Returns a collection of containers having Oct 6, 2006, a-1739
	* @param scannedItems
	* @return
	* @throws DuplicateMailBagsException
	* @throws FlightClosedException
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws ContainerAssignmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public Collection<ScannedMailDetailsVO> saveScannedOutboundDetails(Collection<MailAcceptanceVO> scannedItems)
			throws DuplicateMailBagsException, FlightClosedException, ContainerAssignmentException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, DuplicateDSNException,
			CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException {
		Collection<ScannedMailDetailsVO> exceptionMails = new ArrayList<ScannedMailDetailsVO>();
		for (MailAcceptanceVO scannedAcpVO : scannedItems) {
			exceptionMails.addAll(saveAcceptanceDetails(scannedAcpVO));
		}
		log.debug("" + "---exceptionMails---" + " " + exceptionMails);
		return exceptionMails;
	}

	public ZonedDateTime getLocalDate(String station, Location location, boolean isTimeRequired) {
		return localDateUtil.getLocalDate(station, isTimeRequired);
	}

	public Collection<GeneralRuleConfigDetailsVO> getTimedetails(GeneralConfigurationMasterVO general) {
		Collection<GeneralRuleConfigDetailsVO> time = general.getTimeDetails();
		return time;
	}

	public MailbagInULDForSegment getMailbagInULDForSegment(MailbagInULDForSegmentPK mailbagInULDForSegmentPK)
			throws FinderException {
		return MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
	}

	/** 
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	* @throws DuplicateMailBagsException
	* @throws FlightClosedException
	* @throws ContainerAssignmentException
	* @throws InvalidFlightSegmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @author a-1739
	*/
	public Collection<ScannedMailDetailsVO> saveAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO)
			throws DuplicateMailBagsException, FlightClosedException, ContainerAssignmentException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, DuplicateDSNException,
			CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException {
		log.debug(CLASS + " : " + "saveAcceptanceDetails" + " Entering");
		log.debug("" + "The MailAcceptanceVO is  >>>>>>>> " + " " + mailAcceptanceVO);
		Collection<ScannedMailDetailsVO> expDetails = new ArrayList<ScannedMailDetailsVO>();
		Map<String, Collection<MailbagVO>> mailbagsToReassignMap = null;
		Map<String, Collection<MailbagVO>> mailbagsToTransferMap = null;
		boolean isScanned = mailAcceptanceVO.isScanned();
		Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		Collection<MailbagVO> mailBagVOsForArrival = new ArrayList<MailbagVO>();
		Collection<MailbagVO> deletedMails = new ArrayList<MailbagVO>();
		if (mailAcceptanceVO.isFromDeviationList() || mailAcceptanceVO.isFromCarditList()) {
			mailAcceptanceVO.setDuplicateMailOverride("Y");
		}
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			if (!MailbagVO.FLAG_YES.equals(mailAcceptanceVO.getDuplicateMailOverride())) {
				checkForDuplicateMailbags(mailAcceptanceVO, isScanned, expDetails);
				log.debug("" + "!!!expDetails!!!" + " " + expDetails);
				if (expDetails == null || expDetails.size() == 0) {
					mailbagsToReassignMap = new HashMap<String, Collection<MailbagVO>>();
					mailAcceptanceVO.setDuplicateMailOverride(MailbagVO.FLAG_YES);
				}
			}
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				Collection<MailbagVO> mailbagsToReassign = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailbagsToTransfer = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailBagVOs = containerDetailsVO.getMailDetails();
				if (mailBagVOs != null && !mailBagVOs.isEmpty()) {
					mailAcceptanceVO.setMailbagPresent(true);
					for (MailbagVO mailbagVO : mailBagVOs) {
						if (mailAcceptanceVO.getMailSource() != null) {
							mailbagVO.setMailbagSource(mailAcceptanceVO.getMailSource());
							mailbagVO.setMessageVersion(mailAcceptanceVO.getMessageVersion());
						}
						if (mailAcceptanceVO.getMailDataSource() != null) {
							mailbagVO.setMailbagDataSource(mailAcceptanceVO.getMailDataSource());
						}
						if (MailbagVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
							mailbagVO.setPaBuiltFlag(MailbagVO.FLAG_YES);
							if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
								mailbagVO.setPaCode(containerDetailsVO.getPaCode());
							}
						} else {
							mailbagVO.setPaBuiltFlag(MailbagVO.FLAG_NO);
						}
						if (MailbagVO.FLAG_YES.equals(mailAcceptanceVO.getDuplicateMailOverride())
								&& containerDetailsVO.getOperationFlag() != null) {
							if (MailbagVO.FLAG_YES.equals(mailbagVO.getReassignFlag())) {
								mailbagVO.setSegmentSerialNumber(mailbagVO.getFromSegmentSerialNumber());
								mailbagVO.setCarrierCode(mailbagVO.getCarrierCode());
								mailbagVO.setOperationalFlag(null);
								mailbagVO.setMailbagHistories(null);
								mailbagVO.setScreen("ACC");
								mailbagsToReassign.add(mailbagVO);
								if (mailbagsToReassignMap == null) {
									mailbagsToReassignMap = new HashMap<String, Collection<MailbagVO>>();
								}
								mailbagsToReassignMap.put(containerDetailsVO.getContainerNumber(), mailbagsToReassign);
							}
						}
						if ((mailAcceptanceVO.isFromDeviationList() || mailAcceptanceVO.isFromCarditList()
								|| mailAcceptanceVO.isFromOutboundScreen()
								|| MailConstantsVO.MLD.equals(mailAcceptanceVO.getMailSource())
								|| MailConstantsVO.EXPFLTFIN_ACPMAL.equals(mailAcceptanceVO.getMailSource()))
								&& ((!"NEW".equals(mailbagVO.getLatestStatus())
										&& !"BKD".equals(mailbagVO.getLatestStatus()))
										|| (mailAcceptanceVO.isFromCarditList()
												|| mailAcceptanceVO.isFromOutboundScreen()
												|| MailConstantsVO.MLD.equals(mailAcceptanceVO.getMailSource())))
								&& mailbagVO.getOrigin() != null
								&& ((!mailbagVO.getOrigin().equals(mailAcceptanceVO.getPol())
										&& !validateCoterminusairports(mailbagVO.getOrigin(), mailAcceptanceVO.getPol(),
												MailConstantsVO.RESDIT_RECEIVED, mailbagVO.getPaCode(),
												mailbagVO.getConsignmentDate())
										&& !MailbagVO.OPERATION_FLAG_DELETE.equals(mailbagVO.getOperationalFlag())
										&& !MailbagVO.OPERATION_FLAG_UPDATE
												.equals(mailbagVO.getOperationalFlag()))
										|| (mailAcceptanceVO.isModifyAfterExportOpr()))) {
							if (!mailAcceptanceVO.isFromDeviationList() && !mailAcceptanceVO.isFromCarditList()) {
								if (MailConstantsVO.MLD.equals(mailAcceptanceVO.getMailSource())
										|| mailAcceptanceVO.isFromOutboundScreen()
										|| MailConstantsVO.EXPFLTFIN_ACPMAL.equals(mailAcceptanceVO.getMailSource())) {
									mailAcceptanceVO.setFoundTransfer(true);
								}
								long seqNum = 0;
								seqNum = Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(),
										mailbagVO.getCompanyCode());
								Mailbag mailBag = null;
								if (seqNum == 0) {
									mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
									Transaction tx = null;
									boolean success = false;
									auditMailDetailUpdates(mailbagVO, MailbagAuditVO.MAILBAG_TRANSFER,"Insert",
											getAdditionalInfoForMailTransfer(mailbagVO,MailbagAuditVO.MAILBAG_TRANSFER));
									try {
										TransactionProvider tm = PersistenceController.__getTransactionProvider();
										tx = tm.getNewTransaction(false);
										mailBag = new Mailbag(mailbagVO);
										success = true;
									} finally {
										if (success) {
											tx.commit();
										}
									}

									mailbagVO.setMailSequenceNumber(mailBag.getMailSequenceNumber());
								}
							}
							mailbagVO.setTransferFlag(MailbagVO.FLAG_YES);
							if (mailAcceptanceVO.isFromDeviationList()) {
								mailbagVO.setFromDeviationList(true);
							}
							mailbagVO.setOperationalFlag(null);
							if (mailbagsToTransferMap == null) {
								mailbagsToTransferMap = new HashMap<String, Collection<MailbagVO>>();
							}
							mailbagsToTransfer.add(mailbagVO);
							mailbagsToTransferMap.put(containerDetailsVO.getContainerNumber(), mailbagsToTransfer);
						}
						if (mailAcceptanceVO.isFromDeviationList()
								&& (mailbagVO.getOrigin().equals(mailAcceptanceVO.getPol())
										|| validateCoterminusairports(mailbagVO.getOrigin(), mailAcceptanceVO.getPol(),
												MailConstantsVO.RESDIT_RECEIVED, mailbagVO.getPaCode(),
												mailbagVO.getConsignmentDate()))) {
							mailBagVOsForArrival.addAll(mailBagVOs);
						}
						mailbagVO.setFinalDestination(containerDetailsVO.getDestination());
						if (mailAcceptanceVO.isInventoryForArrival()) {
							mailbagVO.setUldNumber(mailbagVO.getContainerNumber());
						}
						if (containerDetailsVO.isMailUpdateFlag()) {
							mailbagVO.setMailUpdateFlag(true);
						}
						if (MailbagVO.OPERATION_FLAG_DELETE.equals(mailbagVO.getOperationalFlag())
								&& !"D".equals(mailbagVO.getAcknowledge())) {
							boolean isMailInMRA = false;
							String provisionalRateimportEnabled = findSystemParameterValue(
									MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
							try {
								if (provisionalRateimportEnabled != null
										&& MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
									isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(mailbagVO.getCompanyCode(),
											mailbagVO.getMailSequenceNumber());
								}
							} catch (BusinessException e) {
								log.info("" + "Exception finding mail exists in MRA " + " " + e);
								isMailInMRA = false;
							}
							if (isMailInMRA) {
								throw new MailBookingException(MailOperationsBusinessException.MAIL_IMPORTED_TO_MRA);
							} else {
								deletedMails.add(mailbagVO);
							}
						}
						Collection<DamagedMailbagVO> damagedMailBags = mailbagVO.getDamagedMailbags();
						if (damagedMailBags != null && !damagedMailBags.isEmpty()) {
							for (DamagedMailbagVO damagedMailbagVO : damagedMailBags) {
								if (damagedMailbagVO.getPaCode() == null) {
									if (mailbagVO.getOoe() != null && mailbagVO.getOoe().trim().length() > 0) {
										String paCode = findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
												mailbagVO.getOoe());
										if (paCode != null && !"".equals(paCode)) {
											damagedMailbagVO.setPaCode(paCode);
										}
									}
								}
							}
						}
					}
					if (mailbagsToTransfer != null && !mailbagsToTransfer.isEmpty()) {
						mailBagVOs.removeAll(mailbagsToTransfer);
					}
				}
			}
		}
		if (MailAcceptanceVO.FLAG_YES.equals(mailAcceptanceVO.getDuplicateMailOverride())) {
			if (!mailAcceptanceVO.isPreassignNeeded() && !mailAcceptanceVO.isModifyAfterExportOpr()) {
				saveAcceptedContainers(mailAcceptanceVO);
			}
			if (!mailAcceptanceVO.isConsignmentGenerationNotNeeded()) {
				generateConsignmentDocumentNoForAxp(mailAcceptanceVO);
				updateDocumentDetails(mailAcceptanceVO);
			}
			Collection<MailbagVO> mailBagsForMonitorSLA = null;
			boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
			if (isMonitorSLAEnabled) {
				mailBagsForMonitorSLA = new ArrayList<MailbagVO>();
			}
			if (mailAcceptanceVO.isFromDeviationList()) {
				reassignMailFromDestinationForDeviationMailbags(mailAcceptanceVO);
			}
			ContextUtil.getInstance().getBean(MailAcceptance.class)
					.saveAcceptanceDetails(mailAcceptanceVO, mailBagsForMonitorSLA);
			if (mailAcceptanceVO.isFromDeviationList()) {
				inboundFlightsArrivalForDeviation(mailAcceptanceVO, mailBagVOsForArrival, true);
			}
			calculateAndSaveContentId(mailAcceptanceVO);
			if (mailbagsToReassignMap != null && mailbagsToReassignMap.size() > 0) {
				performMailbagReassignmentFromAcceptance(mailbagsToReassignMap, mailAcceptanceVO);
			}
			if (mailbagsToTransferMap != null && mailbagsToTransferMap.size() > 0) {
				performMailbagTransferFromAcceptance(mailbagsToTransferMap, mailAcceptanceVO);
			}
			if (isMonitorSLAEnabled) {
				performSLAActivityFromAcceptance(mailAcceptanceVO, isMonitorSLAEnabled, mailBagsForMonitorSLA);
			}
			performMailAllocationFromAcceptance(containerDetailsVOs, mailAcceptanceVO);
		}
		log.debug("" + "Expection Details  >>>>>>>> " + " " + expDetails);
		if (deletedMails != null && deletedMails.size() > 0) {
			boolean flightMode = false;
			if (mailAcceptanceVO != null && mailAcceptanceVO.getFlightSequenceNumber() > 0) {
				flightMode = true;
			}
			deleteMailBagDetails(deletedMails, flightMode);
		}
		log.debug(CLASS + " : " + "saveAcceptanceDetails" + " Exiting");
		return expDetails;
	}

	private void reassignMailFromDestinationForDeviationMailbags(MailAcceptanceVO mailAcceptanceVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		Collection<MailbagVO> mailbags = null;
		Collection<MailbagVO> destinationAssignedMailbags = null;
		MailbagVO destMailbag = null;
		if (containerDetailsVOs != null) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailbagVO : mailbags) {
						try {
							destMailbag = new MailbagVO();
							destMailbag = mailOperationsMapper.copyMailbagVO(mailbagVO);
							destMailbag.setPol(mailAcceptanceVO.getPol());
							destMailbag = constructDAO().findNotupliftedMailsInCarrierforDeviationlist(destMailbag);
							if (destMailbag != null) {
								mailbagVO.setFromContainer(destMailbag.getUldNumber());
								mailbagVO.setReassignFlag(MailConstantsVO.FLAG_YES);
								destinationAssignedMailbags = new ArrayList<>();
								destinationAssignedMailbags.add(destMailbag);
								new ReassignController().reassignMailFromDestination(destinationAssignedMailbags);
							}
						} catch (PersistenceException e) {
							log.error(
									"" + "MailController reassignMailFromDestinationForDeviationMailbags:-" + " " + e);
						}
					}
				}
			}
		}
	}

	/** 
	* Added as part of CRQ ICRD-118163 by A-5526 to delete accepted mailbags
	* @param deletedMails
	* @param flightMode
	* @throws SystemException
	*/
	private void deleteMailBagDetails(Collection<MailbagVO> deletedMails, boolean flightMode) {
		log.debug(CLASS + " : " + "deleteMailBagDetails" + " Entering");
		log.debug("" + "The deleteMailBagDetails is " + " " + deletedMails);
		checkDeletedMailbagDetails(deletedMails);
		if (deletedMails != null && !deletedMails.isEmpty()) {
			if (flightMode) {
				new ReassignController().reassignMailFromFlight(deletedMails);
			} else {
				new ReassignController().reassignMailFromDestination(deletedMails);
			}
			deleteMailbags(deletedMails);
		}
		log.debug(CLASS + " : " + "deleteMailBagDetails" + " Exiting");
	}

	/** 
	* Added as part of CRQ ICRD-118163 by A-5526 to delete mailbag infos
	* @param deletedMails
	* @throws SystemException
	*/
	private void deleteMailbags(Collection<MailbagVO> deletedMails) {
		log.debug(CLASS + " : " + "deleteMailbags" + " Entering");
		for (MailbagVO mailbagVO : deletedMails) {
			Mailbag mailBag = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				mailBag = null;
			}
			if (mailBag != null) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				if (!mailbagVO.isMailUpdated()) {
					try {
						if (constructDAO().findConsignmentDetailsForMailbag(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId(), "") != null) {
							if (mailBag.getDamagedMailbags() != null) {
								for (DamagedMailbag damaged : mailBag.getDamagedMailbags()) {
									damaged.remove();
								}
							}
							mailBag.setLatestStatus(MailConstantsVO.MAIL_STATUS_NEW);
							mailBag.setFlightNumber("-1");
							mailBag.setFlightSequenceNumber(-1);
							mailBag.setMasterDocumentNumber("");
							mailBag.setDocumentOwnerId(0);
							mailBag.setDupliacteNumber(0);
							mailBag.setSequenceNumber(0);
							mailBag.setShipmentPrefix("");
						} else {
							mailBag.remove();
						}
					} catch (PersistenceException exception) {
						throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
					}
					mailController.auditMailBagDeletion(mailbagVO, MailbagAuditVO.MAILBAG_DELETED);
				} else {
					mailBag.remove();
					mailController.auditMailBagUpdate(mailbagVO, MailbagAuditVO.MAILBAG_MODIFIED);
				}
			}
			ArrayList<MailResdit> resdits = null;
			resdits = (ArrayList<MailResdit>) MailResdit.findAllResditDetails(mailbagVO.getCompanyCode(),
					mailbagVO.getMailSequenceNumber());
			if (resdits != null && resdits.size() > 0) {
				for (MailResdit mailResdit : resdits) {
					if (MailConstantsVO.FLAG_NO.equals(mailResdit.getProcessedStatus())
							&& MailConstantsVO.FLAG_NO.equals(mailResdit.getResditSent())) {
						mailResdit.remove();
					}
				}
			}
		}
	}

	/** 
	* Method		:	MailController.auditMailBagDeletion Added by 	:	A-5945 on Oct 12, 2015 Used for 	:	Audit at Mailbag deletion Parameters	:	@param mailbagvo Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void auditMailBagDeletion(MailbagVO mailbagvo, String actionCode) {
		log.debug(CLASS + " : " + "auditMailBagDeletion" + " Entering");
		log.debug("" + "Deleted mailbagvo:-" + " " + mailbagvo);
		log.debug(CLASS + " : " + "auditMailBagDeletion" + " Exiting");
	}

	/** 
	* This method checks for any duplicate mailbags in a container. A mailbag is duplicate if it is present in another flight/destination. All the mailbags present in the same flight/destn are added to a hashmap of containers. <p> A-1739
	* @param mailAcceptanceVO The acceptance details
	* @param isScanned        TODO
	* @param expDetails
	* @return mailbagsToReassignMap Amapping between 2 containers key is thecontainer from which mail has to be reassigned and the value contains the to container
	* @throws SystemException
	* @throws DuplicateMailBagsException If any duplciate bag is found in a container
	*/
	private void checkForDuplicateMailbags(MailAcceptanceVO mailAcceptanceVO, boolean isScanned,
			Collection<ScannedMailDetailsVO> expDetails) throws DuplicateMailBagsException {
		log.debug(CLASS + " : " + "checkForDuplicateMailbags" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
			if (containerDetailsVO.getOperationFlag() != null) {
				Collection<ExistingMailbagVO> existingMails = new ArrayList<ExistingMailbagVO>();
				Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagVOs) {
						if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
							Mailbag mailbag = null;
							try {
								mailbag = Mailbag.find(createMailbagPK(mailAcceptanceVO.getCompanyCode(), mailbagVO));
							} catch (FinderException exception) {
								continue;
							}
							if ("NEW".equals(mailbag.getLatestStatus()) || "BKD".equals(mailbag.getLatestStatus())
									|| mailbag.getFlightNumber() == null) {
								if (!mailAcceptanceVO.isFromCarditList()) {
									mailbagVO.setOrigin(mailbag.getOrigin());
									mailbagVO.setDestination(mailbag.getDestination());
								}
								continue;
							}
							if (mailAcceptanceVO.isFromCarditList()
									&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbag.getLatestStatus())
									&& mailbag.getUldNumber() == null) {
								mailbagVO.setReassignFlag(MailConstantsVO.FLAG_YES);
								mailbagVO.setFlightNumber(mailbag.getFlightNumber());
								mailbagVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
								continue;
							}
							if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(mailbag.getLatestStatus())) {
								boolean isAcceptableMailbag = true;
								Collection<MailbagHistoryVO> mailbagHistoryVOs = Mailbag.findMailbagHistories(
										mailbag.getCompanyCode(), "",
										mailbag.getMailSequenceNumber());
								if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
									for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
										if (MailConstantsVO.MAIL_STATUS_ACCEPTED
												.equals(mailbagHistoryVO.getMailStatus())
												|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
														.equals(mailbagHistoryVO.getMailStatus())
												|| MailConstantsVO.MAIL_STATUS_ARRIVED
														.equals(mailbagHistoryVO.getMailStatus())
												|| MailConstantsVO.MAIL_STATUS_DELIVERED
														.equals(mailbagHistoryVO.getMailStatus())
												|| MailConstantsVO.MAIL_STATUS_ASSIGNED
														.equals(mailbagHistoryVO.getMailStatus())) {
											isAcceptableMailbag = false;
											break;
										}
									}
								}
								if (isAcceptableMailbag) {
									continue;
								}
							}
							String flightStatus = "";
							String fltCarCode = "";
							String ubrNumber = "";
							int legsernum = 0;
							ZonedDateTime bookingLastUpdateTime = null;
							ZonedDateTime bookingFlightDetailLastUpdTime = null;
							ZonedDateTime fltDat = null;
							if (mailbag != null) {
								if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbag.getLatestStatus())) {
									throw new DuplicateMailBagsException(
											DuplicateMailBagsException.MAILBAG_ALREADY_RETURNED_EXCEPTION,
											new Object[] { mailbag.getMailIdr() });
								}
								if (mailbag.getFlightSequenceNumber() > 0) {
									long mailSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(
											mailbagVO.getMailbagId(), mailAcceptanceVO.getCompanyCode());
									MailbagVO mail = Mailbag.findExistingMailbags(mailAcceptanceVO.getCompanyCode(),
											mailSequenceNumber);
									if (mail != null && mail.getFlightSequenceNumber() > 0) {
										flightStatus = mail.getFlightStatus();
										fltCarCode = mail.getCarrierCode();
										fltDat = mail.getFlightDate();
										ubrNumber = mail.getUbrNumber();
										legsernum = mail.getLegSerialNumber();
										bookingLastUpdateTime = mail.getBookingLastUpdateTime();
										bookingFlightDetailLastUpdTime = mail.getBookingFlightDetailLastUpdTime();
									}
								} else {
									try {
										AirlineValidationVO airlineValidationVO = sharedAirlineProxy
												.findAirline(mailAcceptanceVO.getCompanyCode(), mailbag.getCarrierId());
										fltCarCode = airlineValidationVO.getAlphaCode();
									} catch (SharedProxyException e) {
										e.getMessage();
									}
								}
							}
							if (!mailAcceptanceVO.isScanned()) {
								if (!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) && (mailbagVO
										.getScannedPort().equals(mailbag.getScannedPort())
										&& MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus()))) {
									log.debug("DuplicateMailBagsException -->");
									throw new DuplicateMailBagsException(
											DuplicateMailBagsException.INVALIDACCEPATNCE_EXCEPTION,
											new Object[] { mailbagVO.getMailbagId() });
								}
							}
							if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbag.getLatestStatus()) || (mailbagVO
									.getScannedPort().equals(mailbag.getScannedPort())
									&& MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus()))) {
								continue;
							}
							String orgPaCod = null;
							String OOE = mailbagVO.getOoe();
							PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
							try {
								orgPaCod = findPAForOfficeOfExchange(mailbagVO.getCompanyCode(), OOE);
							} finally {
							}
							try {
								postalAdministrationVO = findPACode(mailbagVO.getCompanyCode(), orgPaCod);
							} finally {
							}
							boolean isDuplicate = checkForDuplicateMailbag(mailbagVO.getCompanyCode(),
									postalAdministrationVO.getPaCode(), mailbag);
							if (!isDuplicate) {
								ExistingMailbagVO existingMailbagVO = new ExistingMailbagVO();
								existingMailbagVO.setCarrierCode(fltCarCode);
								existingMailbagVO.setCurrentAirport(mailbag.getScannedPort());
								existingMailbagVO.setFlightNumber(mailbag.getFlightNumber());
								existingMailbagVO.setFlightStatus(flightStatus);
								existingMailbagVO.setMailId(mailbagVO.getMailbagId());
								existingMailbagVO.setContainerNumber(mailbag.getUldNumber());
								existingMailbagVO.setPol(mailbagVO.getPol());
								existingMailbagVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
								existingMailbagVO.setLegSerialNumber(legsernum);
								existingMailbagVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
								existingMailbagVO.setContainerType(mailbag.getContainerType());
								existingMailbagVO.setPou(mailbag.getPou());
								existingMailbagVO.setFlightDate(fltDat);
								existingMailbagVO.setFinalDestination(mailbag.getPou());
								existingMailbagVO.setCarrierId(mailbag.getCarrierId());
								existingMailbagVO.setUbrNumber(ubrNumber);
								existingMailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
								existingMailbagVO.setBookingFlightDetailLastUpdTime(bookingFlightDetailLastUpdTime);
								existingMailbagVO.setReassign("Y");
								existingMails.add(existingMailbagVO);
							}
						}
					}
				}
				if (existingMails != null && existingMails.size() > 0) {
					ScannedMailDetailsVO scannedMalDetVO = new ScannedMailDetailsVO();
					scannedMalDetVO.setExistingMailbagVOS(existingMails);
					scannedMalDetVO.setMailDetails(mailbagVOs);
					scannedMalDetVO.setContainerNumber(containerDetailsVO.getContainerNumber());
					scannedMalDetVO.setContainerType(containerDetailsVO.getContainerType());
					scannedMalDetVO.setCompanyCode(containerDetailsVO.getCompanyCode());
					scannedMalDetVO.setCarrierCode(containerDetailsVO.getCarrierCode());
					scannedMalDetVO.setFlightNumber(containerDetailsVO.getFlightNumber());
					scannedMalDetVO.setFlightDate(containerDetailsVO.getFlightDate());
					scannedMalDetVO.setPou(containerDetailsVO.getPou());
					scannedMalDetVO.setDestination(containerDetailsVO.getDestination());
					scannedMalDetVO.setPol(containerDetailsVO.getPol());
					expDetails.add(scannedMalDetVO);
				}
			}
		}
		log.debug(CLASS + " : " + "checkForDuplicateMailbags" + " Exiting");
	}

	/** 
	* @param companyCode
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @author A-5991
	*/
	public MailbagPK createMailbagPK(String companyCode, MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(companyCode);
		mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		return mailbagPK;
	}

	/** 
	* TODO Purpose Feb 6, 2007, A-1739
	* @param mailAcceptanceVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws FlightClosedException
	* @throws ContainerAssignmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public void saveAcceptedContainers(MailAcceptanceVO mailAcceptanceVO) throws ContainerAssignmentException,
			FlightClosedException, InvalidFlightSegmentException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException, MailDefaultStorageUnitException {
    	log.debug(CLASS + " : " + "saveAcceptedContainers" + " Entering");
		Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
		Collection<ContainerVO> containersToSave = new ArrayList<ContainerVO>();
		for (ContainerDetailsVO containerDetailsVO : containerDetails) {
			if (containerDetailsVO.getContainerOperationFlag() != null || containerDetailsVO.isReassignFlag()) {
				ContainerVO containerVOToSave = constructContainerVOFromDetails(containerDetailsVO, mailAcceptanceVO);
				containerVOToSave.setOperationFlag(containerDetailsVO.getContainerOperationFlag());
				containersToSave.add(containerVOToSave);
				if (containerVOToSave.getFlightSequenceNumber() > 0) {
					validateAndReleasePreviousAssignment(containerVOToSave);
				}
			}
		}
		if (canReuseEmptyContainerCheckEnabled()) {
			if (mailAcceptanceVO.getContainerDetails() != null && mailAcceptanceVO.getContainerDetails().size() == 1) {
				ContainerDetailsVO containerDetailsVO = mailAcceptanceVO.getContainerDetails().iterator().next();
				if ("I".equals(containerDetailsVO.getContainerOperationFlag()) && !containerDetailsVO.isReassignFlag()
						&& (containerDetailsVO.getMailDetails() == null
								|| containerDetailsVO.getMailDetails().size() == 0)) {
					String pol = mailAcceptanceVO.getPol() != null ? mailAcceptanceVO.getPol()
							: containerDetailsVO.getPol();
					ContainerVO containerVOToSave = constructContainerVOFromDetails(containerDetailsVO,
							mailAcceptanceVO);
					if (!(mailAcceptanceVO.getMailSource() != null
							&& mailAcceptanceVO.getMailSource().contains("MAN010"))) {
						containerVOToSave.setDeleteEmptyContainer(true);
						validateContainerAssignment(pol, containerVOToSave);
					}
				}
			}
		}
		(ContextUtil.getInstance().getBean(MailController.class))
				.saveContainers(constructOpFlightFromAcp(mailAcceptanceVO), containersToSave);
		updateContainerDetailsVOsForAcp(containersToSave, containerDetails, mailAcceptanceVO);
		log.debug(CLASS + " : " + "saveAcceptedContainers" + " Exiting");
	}

	/** 
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	* @author A-2553
	*/
	private void generateConsignmentDocumentNoForAxp(MailAcceptanceVO mailAcceptanceVO) {
		log.debug(CLASS + " : " + "generateConsignmentDocumentNoForAxp" + " Entering");
		Collection<ContainerDetailsVO> contDetVOs = mailAcceptanceVO.getContainerDetails();
		if (contDetVOs != null && contDetVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : contDetVOs) {
				Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
						if (despatchDetailsVO.getConsignmentNumber() == null
								|| despatchDetailsVO.getConsignmentNumber().length() == 0) {
							log.debug(
									"Consignment Document number not available !!! Going to create an internal Consignment Document number");
							//TODO: Airport service to be used
//							AirportVO airportVO = sharedAreaProxy.findAirportDetails(mailAcceptanceVO.getCompanyCode(),
//									mailAcceptanceVO.getPol());
							AirportVO airportVO = null;
							log.debug("" + "AIRPORT VO" + " " + airportVO);
							String id = new StringBuilder().append(airportVO.getCountryCode())
									.append(airportVO.getCityCode()).toString();
							//TODO:Neo to correct key
							//String key = KeyUtils.getKey();
							String key = "";
							String str = "";
							int count = 0;
							for (int i = 0; i < (7 - key.length()); i++) {
								if (count == 0) {
									str = "0";
									count = 1;
								} else {
									str = new StringBuilder().append(str).append("0").toString();
								}
							}
							String conDocNo = new StringBuilder().append(id).append("S").append(str).append(key)
									.toString();
							if (despatchDetailsVO.getConsignmentDate() == null) {
								if (airportVO.getAirportCode() != null) {
									despatchDetailsVO
											.setConsignmentDate(localDateUtil.getLocalDate(null, true));
								} else {
									despatchDetailsVO
											.setConsignmentDate(localDateUtil.getLocalDate(null, true));
								}
							}
							despatchDetailsVO.setConsignmentNumber(conDocNo);
							log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentNumber-->" + " "
									+ despatchDetailsVO.getConsignmentNumber());
							log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentDate-->" + " "
									+ despatchDetailsVO.getConsignmentDate());
							log.debug(
									"" + "%%%%%despatchDetailsVO%%%%-PaCode-->" + " " + despatchDetailsVO.getPaCode());
						} else {
							log.debug("" + "Consignment Document number already available !!! Not creating new" + " "
									+ despatchDetailsVO.getConsignmentNumber());
						}
					}
				}
			}
		}
	}

	private void updateDocumentDetails(MailAcceptanceVO mailAcceptanceVO) throws DuplicateMailBagsException {
		log.debug(CLASS + " : " + "updateDocumentDetails" + " Entering");
		updateDespatchDocumentDetails(mailAcceptanceVO);
		updateMailbagDocumentDetails(mailAcceptanceVO);
		log.debug(CLASS + " : " + "updateDocumentDetails" + " Exiting");
	}

	/** 
	* @return
	* @throws SystemException
	* @author a-1936 Added By karthick V This method is used to check wetherthe MailBag has to monitored for Service Lvl Activity throughout its Process say From the Acceptance to Delivery .
	*/
	private boolean isMonitorSLAEnabled() {
		boolean isMonitorSLAEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.MAILTRACKING_MONITORSLA);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.MAILTRACKING_MONITORSLA))) {
			isMonitorSLAEnabled = true;
		}
		log.debug("" + " isMonitorSLAEnabled :" + " " + isMonitorSLAEnabled);
		return isMonitorSLAEnabled;
	}

	private void performMailbagReassignmentFromAcceptance(Map<String, Collection<MailbagVO>> mailbagsToReassignMap,
			MailAcceptanceVO mailAcceptanceVO) throws FlightClosedException, InvalidFlightSegmentException,
			CapacityBookingProxyException, MailBookingException {
		if (mailbagsToReassignMap.size() > 0) {
			Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
			for (Map.Entry<String, Collection<MailbagVO>> entrySet : mailbagsToReassignMap.entrySet()) {
				ReassignController reassignController = ContextUtil.getInstance().getBean(ReassignController.class);
				reassignController.reassignMailbags(entrySet.getValue(),
						constructContainerVOForAcp(entrySet.getKey(), containerDetails, mailAcceptanceVO));
			}
		}
	}

	/** 
	* Method		:	MailController.performMailbagTransferFromAcceptance Added by 	:	A-8061 on 21-Apr-2020 Used for 	:	IASCB-48445 Parameters	:	@param mailbagsToTransferMap Parameters	:	@param mailAcceptanceVO Parameters	:	@throws FlightClosedException Parameters	:	@throws InvalidFlightSegmentException Parameters	:	@throws CapacityBookingProxyException Parameters	:	@throws MailBookingException Parameters	:	@throws SystemException Return type	: 	void
	*/
	private void performMailbagTransferFromAcceptance(Map<String, Collection<MailbagVO>> mailbagsToTransferMap,
			MailAcceptanceVO mailAcceptanceVO) throws FlightClosedException, InvalidFlightSegmentException,
			CapacityBookingProxyException, MailBookingException {
		if (mailbagsToTransferMap.size() > 0) {
			Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
			for (Map.Entry<String, Collection<MailbagVO>> entrySet : mailbagsToTransferMap.entrySet()) {
				Collection<MailbagVO> mailbagVos = entrySet.getValue();
				inboundFlightsArrivalForDeviation(mailAcceptanceVO, mailbagVos, false);
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				if (mailAcceptanceVO.isModifyAfterExportOpr()) {
					mailController.flagHandoverReceivedAfterExportOpr(entrySet.getValue(),
							constructContainerVOForAcp(entrySet.getKey(), containerDetails, mailAcceptanceVO));
				} else if (mailAcceptanceVO.isTransferOnModify()) {
					try {
						mailController.transferMailAtExport(entrySet.getValue(),
								constructContainerVOForAcp(entrySet.getKey(), containerDetails, mailAcceptanceVO), "N");
					} catch (MailOperationsBusinessException e) {
						e.getMessage();
					}
				} else {
					try {
						mailController.transferMail(null, entrySet.getValue(),
								constructContainerVOForAcp(entrySet.getKey(), containerDetails, mailAcceptanceVO), "N");
					} catch (MailOperationsBusinessException e) {
						e.getMessage();
					}
				}
				if (mailAcceptanceVO.isFromDeviationList()) {
					inboundFlightsArrivalForDeviation(mailAcceptanceVO, mailbagVos, true);
				}
			}
		}
	}

	private void inboundFlightsArrivalForDeviation(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbagVos,
			boolean isPou) {
		ScannedMailDetailsVO scannedMailDetailsVO;
		Collection<MailbagVO> mailbagVosTemp;
		Collection<MailbagVO> mailbagVosForArrival;
		MailbagVO mailbagVOForArrival;
		MailbagInULDForSegmentVO mailbagInULDForSegmentVO;
		for (MailbagVO mailbagVO : mailbagVos) {
			mailbagVO.setOperationalFlag("U");
			if (isAutoArrivalEnabled(MailConstantsVO.MAIL_STATUS_TRANSFERRED)) {
				scannedMailDetailsVO = new ScannedMailDetailsVO();
				mailbagVosTemp = new ArrayList<MailbagVO>();
				mailbagVOForArrival = new MailbagVO();
				mailbagVosForArrival = new ArrayList<MailbagVO>();
				ContainerVO containerVO = null;
				mailbagInULDForSegmentVO = null;
				mailbagVosTemp.add(mailbagVO);
				scannedMailDetailsVO.setMailDetails(mailbagVosTemp);
				scannedMailDetailsVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				if (mailbagVO.isFromDeviationList() && isPou) {
					scannedMailDetailsVO.setAirportCode(mailbagVO.getPou());
				} else {
					scannedMailDetailsVO.setAirportCode(mailAcceptanceVO.getPol());
				}
				try {
					mailbagInULDForSegmentVO = new MailbagInULDForSegment().getManifestInfo(scannedMailDetailsVO);
				} catch (PersistenceException e) {
					log.error("Not manifested");
				}
				if (mailbagInULDForSegmentVO != null
						&& !MailbagVO.FLAG_YES.equals(mailbagInULDForSegmentVO.getArrivalFlag())) {
					 mailbagVOForArrival= mailOperationsMapper.copyMailbagVO(mailbagVO);
					containerVO = constructContainerVOForAutoArrival(mailbagInULDForSegmentVO, mailbagVOForArrival);
					containerVO.setFromDeviationList(mailAcceptanceVO.isFromDeviationList());
					mailbagVosForArrival.add(mailbagVOForArrival);
					mailTransfer.saveMailbagsInboundDtlsForTransfer(mailbagVosForArrival, containerVO);
				}
			}
		}
	}

	/** 
	* Method		:	MailController.constructContainerVOForAutoArrival Added by 	:	A-8061 on 21-Apr-2020 Used for 	:	IASCB-48445 Parameters	:	@param mailbagInULDForSegmentVO Parameters	:	@param mailbagVO Parameters	:	@return Return type	: 	ContainerVO
	*/
	private ContainerVO constructContainerVOForAutoArrival(MailbagInULDForSegmentVO mailbagInULDForSegmentVO,
			MailbagVO mailbagVO) {
		log.debug(CLASS + " : " + "constructContainerVO" + " Entering");
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(mailbagInULDForSegmentVO.getCompanyCode());
		containerVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
		containerVO.setAssignedPort(mailbagInULDForSegmentVO.getAssignedPort());
		containerVO.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
		containerVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
		containerVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
		containerVO.setLegSerialNumber(mailbagInULDForSegmentVO.getLegSerialNumber());
		mailbagVO.setContainerType(mailbagInULDForSegmentVO.getContainerType());
		mailbagVO.setCarrierId(mailbagInULDForSegmentVO.getCarrierId());
		mailbagVO.setFlightNumber(mailbagInULDForSegmentVO.getFlightNumber());
		mailbagVO.setFlightSequenceNumber(mailbagInULDForSegmentVO.getFlightSequenceNumber());
		mailbagVO.setContainerNumber(mailbagInULDForSegmentVO.getContainerNumber());
		mailbagVO.setSegmentSerialNumber(mailbagInULDForSegmentVO.getSegmentSerialNumber());
		mailbagVO.setLegSerialNumber(mailbagInULDForSegmentVO.getLegSerialNumber());
		return containerVO;
	}

	private void performSLAActivityFromAcceptance(MailAcceptanceVO mailAcceptanceVO, boolean isMonitorSLAEnabled,
			Collection<MailbagVO> mailBagsForMonitorSLA) {
		if (mailAcceptanceVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT && isMonitorSLAEnabled) {
			if (mailBagsForMonitorSLA != null && mailBagsForMonitorSLA.size() > 0) {
				monitorMailSLAActivity(createMonitorSLAVosForAcceptance(mailBagsForMonitorSLA, mailAcceptanceVO));
			}
		}
	}

	private void performMailAllocationFromAcceptance(Collection<ContainerDetailsVO> containerDetailsVOs,
			MailAcceptanceVO mailAcceptanceVO) {
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO contDetVO : containerDetailsVOs) {
				if (contDetVO.getDsnVOs() != null && contDetVO.getDsnVOs().size() > 0) {
					for (DSNVO dsnVO : contDetVO.getDsnVOs()) {
						if (dsnVO.getBags() != 0) {
							dsnVO.setSegmentSerialNumber(contDetVO.getSegmentSerialNumber());
							dsnVO.setContainerType(contDetVO.getContainerType());
							if (dsnVO.getContainerType() != null) {
								if (MailConstantsVO.ULD_TYPE.equals(dsnVO.getContainerType())) {
									dsnVO.setContainerNumber(contDetVO.getContainerNumber());
									dsnVO.setRemarks(contDetVO.getRemarks());
								} else if (MailConstantsVO.BULK_TYPE.equals(dsnVO.getContainerType())) {
									String contNum = new StringBuilder().append(MailConstantsVO.CONST_BULK)
											.append(MailConstantsVO.SEPARATOR).append(contDetVO.getPou()).toString();
									dsnVO.setContainerNumber(contNum);
								}
							}
							dsnVO.setPou(contDetVO.getPou());
							dsnVOs.add(dsnVO);
						}
					}
				}
			}
		}
		OperationalFlightVO operationalFlightVO = constructOpFlightFromAcp(mailAcceptanceVO);
		if (dsnVOs.size() > 0 && operationalFlightVO != null) {
			if (operationalFlightVO.getFlightNumber() != null
					&& !String.valueOf(MailConstantsVO.DESTN_FLT).equals(operationalFlightVO.getFlightNumber())) {
				log.debug("MailController--saveAcceptanceDetails--createBookingForMail");
				log.debug("Created Booking");
			}
		}
	}

	/** 
	* TODO Purpose Feb 6, 2007, A-1739
	* @param mailAcceptanceVO
	* @return
	*/
	public OperationalFlightVO constructOpFlightFromAcp(MailAcceptanceVO mailAcceptanceVO) {
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

	private ContainerVO constructContainerVOFromDetails(ContainerDetailsVO containerDetailsVO,
			MailAcceptanceVO mailAcpVO) {
		ContainerVO newContainerVO = new ContainerVO();
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		newContainerVO.setCompanyCode(mailAcpVO.getCompanyCode());
		if (containerDetailsVO.isReassignFlag()) {
			newContainerVO.setCarrierId(containerDetailsVO.getCarrierId());
			newContainerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			newContainerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			newContainerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
			newContainerVO.setFlightDate(containerDetailsVO.getFlightDate());
			newContainerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		} else {
			newContainerVO.setCarrierId(mailAcpVO.getCarrierId());
			newContainerVO.setFlightNumber(mailAcpVO.getFlightNumber());
			newContainerVO.setFlightSequenceNumber(mailAcpVO.getFlightSequenceNumber());
			newContainerVO.setLegSerialNumber(mailAcpVO.getLegSerialNumber());
			newContainerVO.setFlightDate(mailAcpVO.getFlightDate());
			newContainerVO.setCarrierCode(mailAcpVO.getFlightCarrierCode());
		}
		newContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		newContainerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		newContainerVO.setPreassignNeeded(mailAcpVO.isPreassignNeeded());
		newContainerVO.setAssignedPort(containerDetailsVO.getPol());
		newContainerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		newContainerVO.setContainerJnyID(containerDetailsVO.getContainerJnyId());
		newContainerVO.setContentId(containerDetailsVO.getContentId());
		if (containerDetailsVO.getPaCode() != null) {
			newContainerVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
		}
		newContainerVO.setType(containerDetailsVO.getContainerType());
		newContainerVO.setPou(containerDetailsVO.getPou());
		newContainerVO.setPol(containerDetailsVO.getPol());
		newContainerVO.setFinalDestination(containerDetailsVO.getDestination());
		newContainerVO.setRemarks(containerDetailsVO.getRemarks());
		newContainerVO.setReassignFlag(containerDetailsVO.isReassignFlag());
		newContainerVO.setAssignedUser(containerDetailsVO.getAssignedUser());
		newContainerVO.setAssignedDate(containerDetailsVO.getAssignmentDate());
		newContainerVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		newContainerVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
		newContainerVO.setPaBuiltOpenedFlag(MailConstantsVO.FLAG_NO);
		newContainerVO.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
		newContainerVO.setTransactionCode(containerDetailsVO.getTransactonCode());
		newContainerVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		newContainerVO.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
		newContainerVO.setULDLastUpdateTime(containerDetailsVO.getUldLastUpdateTime());
		newContainerVO.setFromDeviationList(mailAcpVO.isFromDeviationList());
		newContainerVO.setLastUpdateUser(mailAcpVO.getAcceptedUser());
		newContainerVO.setFromCarditList(mailAcpVO.isFromCarditList());
		newContainerVO.setFoundTransfer(mailAcpVO.isFoundTransfer());
		newContainerVO.setMailSource(mailAcpVO.getMailSource());
		newContainerVO.setMailbagPresent(mailAcpVO.isMailbagPresent());
		newContainerVO.setUldFulIndFlag(containerDetailsVO.getUldFulIndFlag());
		newContainerVO.setUldReferenceNo(containerDetailsVO.getUldReferenceNo());
		newContainerVO.setActualWeight(containerDetailsVO.getActualWeight());
		return newContainerVO;
	}

	/** 
	* This method saves the Assignment of containers at a port A-1936
	* @param operationalFlightVO
	* @param containerVos
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws FlightClosedException
	* @throws InvalidFlightSegmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public Collection<ContainerVO> saveContainers(OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> containerVos) throws ContainerAssignmentException, FlightClosedException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, MailDefaultStorageUnitException {
		log.debug(CLASS + " : " + "saveContainers" + " Entering");
		FlightDetailsVO flightDetailsVO = null;
		Collection<ULDInFlightVO> uldInFlightVos = null;
		ULDInFlightVO uldInFlightVO = null;
		Collection<ContainerVO> reassignContainers = null;
		Collection<ContainerVO> assignContainers = null;
		boolean isUld = false;
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		Collection<UldInFlightVO> operationalUlds = new ArrayList<UldInFlightVO>();
		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		boolean isUMSUpdateNeeded = isULDIntegrationEnabled();
		for (ContainerVO containerVo : containerVos) {
			if (MailConstantsVO.ULD_TYPE.equals(containerVo.getType())) {
				isUld = true;
			}
			if (isUMSUpdateNeeded && OPERATION_FLAG_INSERT.equals(containerVo.getOperationFlag())
					&& !MailConstantsVO.BULK_TYPE.equals(containerVo.getType())) {
				flightDetailsVO = new FlightDetailsVO();
				flightDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
				flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getFlightDate()));
				flightDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				flightDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				flightDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
				uldInFlightVos = new ArrayList<ULDInFlightVO>();
				ULDInFlightVO uldFltVo = new ULDInFlightVO();
				uldFltVo.setUldNumber(containerVo.getContainerNumber());
				if (null == containerVo.getPol() || containerVo.getPol().trim().isEmpty()) {
					uldFltVo.setPointOfLading(containerVo.getAssignedPort());
					flightDetailsVO.setCurrentAirport(containerVo.getAssignedPort());
				} else {
					uldFltVo.setPointOfLading(containerVo.getPol());
					flightDetailsVO.setCurrentAirport(containerVo.getPol());
				}
				if (null == containerVo.getPou() || containerVo.getPou().trim().isEmpty()) {
					uldFltVo.setPointOfUnLading(containerVo.getFinalDestination());
				} else {
					uldFltVo.setPointOfUnLading(containerVo.getPou());
				}
				uldFltVo.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
				uldFltVo.setContent(MailConstantsVO.UCM_ULD_SOURCE_MAIL);
				uldInFlightVos.add(uldFltVo);
				if (flightDetailsVO.getCurrentAirport() != null) {
					flightDetailsVO.setTransactionDate(new com.ibsplc.icargo.framework.util.time.LocalDate(
							flightDetailsVO.getCurrentAirport(), Location.ARP, true));
				}
				flightDetailsVO.setUldInFlightVOs(uldInFlightVos);
				flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
				flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
				try {
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				} catch (ULDDefaultsProxyException uldDefaultsException) {
					throw new ContainerAssignmentException(uldDefaultsException);
				}
			}
		}
		for (ContainerVO containerVO : containerVos) {
			if (containerVO.isReassignFlag()) {
				if (reassignContainers == null) {
					reassignContainers = new ArrayList<ContainerVO>();
				}
				reassignContainers.add(containerVO);
			} else {
				if (assignContainers == null) {
					assignContainers = new ArrayList<ContainerVO>();
				}
				assignContainers.add(containerVO);
			}
			if (isUMSUpdateNeeded || isOprUldEnabled) {
				constructDetailsForIntegrationForSaveCotainers(containerVO, isUMSUpdateNeeded, uldInFlightVos,
						isOprUldEnabled, operationalUlds, uldInFlightVO);
			}
		}
		if (assignContainers != null && assignContainers.size() > 0) {
			if (operationalFlightVO == null
					|| operationalFlightVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
				saveContainersForDestination(assignContainers);
			} else {
				saveContainersForFlight(operationalFlightVO, assignContainers);
			}
		}
		if (reassignContainers != null && reassignContainers.size() > 0) {
			performReassignFromSaveContainers(reassignContainers, operationalFlightVO);
		}
		if (assignContainers != null && assignContainers.size() > 0) {
			containersForReturn.addAll(assignContainers);
		}
		if (reassignContainers != null && reassignContainers.size() > 0) {
			containersForReturn.addAll(reassignContainers);
		}
		if (isOprUldEnabled && isUld && operationalUlds != null && operationalUlds.size() > 0) {
			operationsFltHandlingProxy.saveOperationalULDsInFlight(operationalUlds);
		}
		log.debug(CLASS + " : " + "saveContainers" + " Exiting");
		return containersForReturn;
	}

	/** 
	* Updates the ULD details after saving their assignments. Along with this all the reassigned containers can be removed, since everything for them is already done by the reassigncontainers methods Feb 6, 2007, A-1739
	* @param containersToSave
	* @param containerDetails
	* @param mailAcceptanceVO
	*/
	private void updateContainerDetailsVOsForAcp(Collection<ContainerVO> containersToSave,
			Collection<ContainerDetailsVO> containerDetails, MailAcceptanceVO mailAcceptanceVO) {
		for (ContainerDetailsVO containerDetailsVO : containerDetails) {
			if (OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())
					|| containerDetailsVO.isReassignFlag()) {
				for (ContainerVO savedContainerVO : containersToSave) {
					if (containerDetailsVO.getContainerNumber() != null
							&& containerDetailsVO.getContainerNumber().equals(savedContainerVO.getContainerNumber())) {
						containerDetailsVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
						containerDetailsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
						containerDetailsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
						containerDetailsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
						containerDetailsVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
						containerDetailsVO.setFlightDate(mailAcceptanceVO.getFlightDate());
						containerDetailsVO.setSegmentSerialNumber(savedContainerVO.getSegmentSerialNumber());
						containerDetailsVO.setRemarks(savedContainerVO.getRemarks());
						if (containerDetailsVO.getFlightSequenceNumber() > 0) {
							updateFlightDetailsForMailbagVOs(containerDetailsVO);
						}
						if (containerDetailsVO.isReassignFlag()) {
							if (MailConstantsVO.FLAG_YES.equals(savedContainerVO.getAcceptanceFlag())) {
								containerDetailsVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							} else {
								containerDetailsVO.setOperationFlag(OPERATION_FLAG_INSERT);
							}
						}
						break;
					}
				}
			}
		}
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	private void updateDespatchDocumentDetails(MailAcceptanceVO mailAcceptanceVO) throws DuplicateMailBagsException {
		log.debug(CLASS + " : " + "updateDespatchDocumentDetails" + " Entering");
		Collection<ContainerDetailsVO> containerDetails = compareAndCalculateTotalsOfDespatches(
				mailAcceptanceVO.getContainerDetails());
		Map<String, Collection<DespatchDetailsVO>> despatchMap = groupDespatchesForConsignment(containerDetails);
		log.debug("" + "despatch map -->" + " " + despatchMap);
		try {
			for (Map.Entry<String, Collection<DespatchDetailsVO>> despatch : despatchMap.entrySet()) {
				Collection<DespatchDetailsVO> despatches = despatch.getValue();
				ConsignmentDocumentVO consignDocVO = constructConsignmentDocVO(despatch.getKey(), despatches,
						mailAcceptanceVO.getPol());
				consignDocVO.setScanned(mailAcceptanceVO.isScanned());
				log.debug("" + "Consignment Document to save -->" + " " + consignDocVO);
				int consignmentSeqNum = new DocumentController().saveConsignmentForAcceptance(consignDocVO);
				updateDespatchesSequenceNum(consignmentSeqNum, despatches);
			}
		} catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
		}
		log.debug(CLASS + " : " + "updateDespatchDocumentDetails" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	private void updateMailbagDocumentDetails(MailAcceptanceVO mailAcceptanceVO) {
		Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
		log.debug(CLASS + " : " + "updateMailbagDocumentDetails" + " Entering");
		for (ContainerDetailsVO containerDetailsVO : containerDetails) {
			if (containerDetailsVO.getOperationFlag() != null) {
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					DocumentController docController = new DocumentController();
					MailInConsignmentVO mailInConsignmentVO = null;
					for (MailbagVO mailbagVO : mailbags) {
						if (mailbagVO.getOperationalFlag() != null) {
							mailInConsignmentVO = docController.findConsignmentDetailsForMailbag(
									mailbagVO.getCompanyCode(), mailbagVO.getMailbagId(), mailbagVO.getScannedPort());
							if (mailInConsignmentVO != null) {
								mailbagVO.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
								mailbagVO.setConsignmentSequenceNumber(
										mailInConsignmentVO.getConsignmentSequenceNumber());
								if (!"MTK060".equalsIgnoreCase(mailAcceptanceVO.getMailSource())) {
									mailbagVO.setPaCode(mailInConsignmentVO.getPaCode());
								}
							}
						}
					}
				}
			}
		}
		log.debug(CLASS + " : " + "updateMailbagDocumentDetails" + " Exiting");
	}

	private ContainerVO constructContainerVOForAcp(String uldPK, Collection<ContainerDetailsVO> containerDetails,
			MailAcceptanceVO mailAcpVO) {
		for (ContainerDetailsVO containerDetailsVO : containerDetails) {
			if (containerDetailsVO.getContainerNumber().equals(uldPK)) {
				return constructContainerVOFromDetails(containerDetailsVO, mailAcpVO);
			}
		}
		return null;
	}

	/** 
	* @param mailBagsForMonitorSLA
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to construct the MonitorSLAVos Fromthe MailBagVos
	*/
	private Collection<MonitorMailSLAVO> createMonitorSLAVosForAcceptance(Collection<MailbagVO> mailBagsForMonitorSLA,
			MailAcceptanceVO mailAcceptanceVO) {
		log.debug(CLASS + " : " + "createMonitorSLAVosForAcceptance" + " Entering");
		MonitorMailSLAVO monitorSLAVo = null;
		Collection<MonitorMailSLAVO> monitorSLAVos = new ArrayList<MonitorMailSLAVO>();
		for (MailbagVO mailBagForMonitorSLA : mailBagsForMonitorSLA) {
			monitorSLAVo = new MonitorMailSLAVO();
			monitorSLAVo.setCompanyCode(mailAcceptanceVO.getCompanyCode());
			monitorSLAVo.setAirlineCode(mailAcceptanceVO.getOwnAirlineCode());
			monitorSLAVo.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
			monitorSLAVo.setFlightCarrierIdentifier(mailAcceptanceVO.getCarrierId());
			monitorSLAVo.setAirlineIdentifier(mailAcceptanceVO.getOwnAirlineId());
			monitorSLAVo.setFlightNumber(mailAcceptanceVO.getFlightNumber());
			monitorSLAVo.setActivity(MonitorMailSLAVO.MAILSTATUS_ACCEPTED);
			monitorSLAVo.setMailBagNumber(mailBagForMonitorSLA.getMailbagId());
			monitorSLAVo.setOperationFlag(mailBagForMonitorSLA.getOperationalFlag());
			monitorSLAVo.setScanTime(mailBagForMonitorSLA.getScannedDate());
			monitorSLAVos.add(monitorSLAVo);
		}
		log.debug(CLASS + " : " + "createMonitorSLAVosForAcceptance" + " Entering");
		return monitorSLAVos;
	}

	/** 
	* @param monitorMailSlaVos
	* @throws SystemException
	* @author a-2518
	*/
	public void monitorMailSLAActivity(Collection<MonitorMailSLAVO> monitorMailSlaVos) {
		log.debug(CLASS + " : " + "monitorMailSLAActivity" + " Entering");
		HashMap<String, String> cityOEMap = null;
		Collection<String> cities = new ArrayList<String>();
		Collection<String> officeOfExchanges = new ArrayList<String>();
		String companyCode = ((ArrayList<MonitorMailSLAVO>) monitorMailSlaVos).get(0).getCompanyCode();
		for (MonitorMailSLAVO monitorMailSlaVo : monitorMailSlaVos) {
			String originOfficeOfExchange = monitorMailSlaVo.getMailBagNumber().substring(0, 6);
			String destinationOfficeOfExchange = monitorMailSlaVo.getMailBagNumber().substring(6, 12);
			if (!officeOfExchanges.contains(originOfficeOfExchange)) {
				officeOfExchanges.add(originOfficeOfExchange);
			}
			if (!officeOfExchanges.contains(destinationOfficeOfExchange)) {
				officeOfExchanges.add(destinationOfficeOfExchange);
			}
		}
		if (companyCode != null && companyCode.length() > 0 && officeOfExchanges.size() > 0) {
			cityOEMap = findCityForOfficeOfExchange(companyCode, officeOfExchanges);
		}
		for (MonitorMailSLAVO monitorMailSlaVo : monitorMailSlaVos) {
			String origin = null;
			String destination = null;
			String originOfficeOfExchange = monitorMailSlaVo.getMailBagNumber().substring(0, 6);
			String destinationOfficeOfExchange = monitorMailSlaVo.getMailBagNumber().substring(6, 12);
			if (cityOEMap != null && cityOEMap.size() > 0) {
				origin = cityOEMap.get(originOfficeOfExchange);
				destination = cityOEMap.get(destinationOfficeOfExchange);
			}
			String poaCode = MailActivityDetail.findPostalAuthorityCode(monitorMailSlaVo.getCompanyCode(),
					originOfficeOfExchange);
			String mailCategory = monitorMailSlaVo.getMailBagNumber().substring(12, 13);
			log.debug("" + "The Monitor SLA VO" + " " + monitorMailSlaVo);
			if (MAILSTATUS_ACCEPTED.equals(monitorMailSlaVo.getActivity())) {
				if (OPERATION_FLAG_INSERT.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetailVO mailActivityDetailVO = null;
					mailActivityDetailVO = MailActivityDetail.findServiceTimeAndSLAId(monitorMailSlaVo.getCompanyCode(),
							poaCode, origin, destination, mailCategory, SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE,
							monitorMailSlaVo.getScanTime());
					if (mailActivityDetailVO != null) {
						if (mailActivityDetailVO.getSlaIdentifier() != null
								&& !"".equals(mailActivityDetailVO.getSlaIdentifier())) {
							mailActivityDetailVO.setCompanyCode(monitorMailSlaVo.getCompanyCode());
							mailActivityDetailVO.setMailBagNumber(monitorMailSlaVo.getMailBagNumber());
							mailActivityDetailVO.setServiceLevelActivity(SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
							mailActivityDetailVO.setGpaCode(poaCode);
							mailActivityDetailVO.setPlannedTime(
									monitorMailSlaVo.getScanTime().plusHours(mailActivityDetailVO.getServiceTime()));
							mailActivityDetailVO.setActualTime(null);
							mailActivityDetailVO.setAirlineCode(monitorMailSlaVo.getAirlineCode());
							mailActivityDetailVO.setAirlineIdentifier(monitorMailSlaVo.getAirlineIdentifier());
							mailActivityDetailVO.setFlightCarrierCode(monitorMailSlaVo.getFlightCarrierCode());
							mailActivityDetailVO.setFlightCarrierId(monitorMailSlaVo.getFlightCarrierIdentifier());
							mailActivityDetailVO.setFlightNumber(monitorMailSlaVo.getFlightNumber());
							mailActivityDetailVO.setMailCategory(mailCategory);
							mailActivityDetailVO.setSlaStatus(null);
							new MailActivityDetail(mailActivityDetailVO);
						}
					}
				} else if (OPERATION_FLAG_UPDATE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						MailActivityDetailVO mailActivityDetailVO = MailActivityDetail.findServiceTimeAndSLAId(
								monitorMailSlaVo.getCompanyCode(), poaCode, origin, destination, mailCategory,
								SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE, monitorMailSlaVo.getScanTime());
						mailActivityDetail.setPlannedTime(
								monitorMailSlaVo.getScanTime().plusHours(mailActivityDetailVO.getServiceTime()));
						log.info("MailActivityDetail has been updated");
					}
				} else if (OPERATION_FLAG_DELETE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						mailActivityDetail.remove();
						log.info("MailActivityDetail has been removed");
					}
				}
			} else if (MAILSTATUS_MANIFESTED.equals(monitorMailSlaVo.getActivity())) {
				if (OPERATION_FLAG_UPDATE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						log.debug("" + " the Scan time from the Opr" + " " + monitorMailSlaVo.getScanTime());
						mailActivityDetail.setActualTime(monitorMailSlaVo.getScanTime());
						ZonedDateTime ldate = localDateUtil.getLocalDateTime(mailActivityDetail.getPlannedTime(),
								getLogonAirport());
						log.debug("" + " the Scan time Planned " + " " + ldate);
						if (monitorMailSlaVo.getScanTime().isAfter(ldate)) {
							mailActivityDetail.setSlaStatus(SLASTATUS_FAILURE);
						} else {
							mailActivityDetail.setSlaStatus(SLASTATUS_SUCCESS);
						}
						log.info("MailActivityDetail has been updated");
					}
				}
			} else if (MAILSTATUS_OFFLOADED.equals(monitorMailSlaVo.getActivity())) {
				if (OPERATION_FLAG_UPDATE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						//TODO: Validate with PB whether Mail activity code required in Neo
//						mailActivityDetail.setActualTime(null);
//						mailActivityDetail.setPlannedTime(null);
						mailActivityDetail.setSlaStatus(null);
						log.info("MailActivityDetail has been updated");
					}
				} else if (OPERATION_FLAG_DELETE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						mailActivityDetail.remove();
						log.info("MailActivityDetail has been removed");
					}
				}
			} else if (MAILSTATUS_ARRIVED.equals(monitorMailSlaVo.getActivity())) {
				MailActivityDetail mailActivityDetail = null;
				try {
					mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
							monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ACCEPTANCE_TO_DEPARTURE);
				} catch (FinderException finderException) {
					log.error("Finder Exception occurred in finding MailActivityDetail entity");
				}
				log.debug("" + "THE OPERATIONAL FLAG FOR THE ARR " + " " + monitorMailSlaVo.getOperationFlag());
				if (mailActivityDetail != null) {
					log.debug("THE ACCEPTANCE ENTITY GOT  ");
					log.debug("THE ACCEPTANCE ENTITY GOT  ");
					if (OPERATION_FLAG_INSERT.equals(monitorMailSlaVo.getOperationFlag())) {
						log.debug("" + "THE OPERATIONAL FLAG FOR THE DLV " + " " + monitorMailSlaVo.getOperationFlag());
						log.debug("" + "THE OPERATIONAL FLAG FOR THE arr " + " " + monitorMailSlaVo.getOperationFlag());
						MailActivityDetailVO mailActivityDetailVO = null;
						mailActivityDetailVO = MailActivityDetail.findServiceTimeAndSLAId(
								monitorMailSlaVo.getCompanyCode(), poaCode, origin, destination, mailCategory,
								SLAACTIVITY_ARRIVAL_TO_DELIVERY, monitorMailSlaVo.getScanTime());
						log.debug("" + " The Mail Activcity Detailvo" + " " + mailActivityDetailVO);
						if (mailActivityDetailVO != null) {
							if (mailActivityDetailVO.getSlaIdentifier() != null
									&& !"".equals(mailActivityDetailVO.getSlaIdentifier())) {
								mailActivityDetailVO.setCompanyCode(monitorMailSlaVo.getCompanyCode());
								mailActivityDetailVO.setMailBagNumber(monitorMailSlaVo.getMailBagNumber());
								mailActivityDetailVO.setServiceLevelActivity(SLAACTIVITY_ARRIVAL_TO_DELIVERY);
								mailActivityDetailVO.setGpaCode(poaCode);
								mailActivityDetailVO.setPlannedTime(
										monitorMailSlaVo.getScanTime().plusHours(mailActivityDetailVO.getServiceTime()));
								mailActivityDetailVO.setActualTime(null);
								mailActivityDetailVO.setAirlineCode(monitorMailSlaVo.getAirlineCode());
								mailActivityDetailVO.setAirlineIdentifier(monitorMailSlaVo.getAirlineIdentifier());
								mailActivityDetailVO.setFlightCarrierCode(monitorMailSlaVo.getFlightCarrierCode());
								mailActivityDetailVO.setFlightCarrierId(monitorMailSlaVo.getFlightCarrierIdentifier());
								mailActivityDetailVO.setFlightNumber(monitorMailSlaVo.getFlightNumber());
								mailActivityDetailVO.setMailCategory(mailCategory);
								mailActivityDetailVO.setSlaStatus(null);
								new MailActivityDetail(mailActivityDetailVO);
							}
						}
					}
					if (OPERATION_FLAG_UPDATE.equals(monitorMailSlaVo.getOperationFlag())) {
						mailActivityDetail = null;
						try {
							mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
									monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ARRIVAL_TO_DELIVERY);
						} catch (FinderException finderException) {
							log.error("Finder Exception occurred in finding MailActivityDetail entity");
						}
						if (mailActivityDetail != null) {
							mailActivityDetail.setPlannedTime(monitorMailSlaVo.getScanTime());
						} else {
							MailActivityDetailVO mailActivityDetailVO = null;
							mailActivityDetailVO = MailActivityDetail.findServiceTimeAndSLAId(
									monitorMailSlaVo.getCompanyCode(), poaCode, origin, destination, mailCategory,
									SLAACTIVITY_ARRIVAL_TO_DELIVERY, monitorMailSlaVo.getScanTime());
							if (mailActivityDetailVO != null) {
								if (mailActivityDetailVO.getSlaIdentifier() != null
										&& !"".equals(mailActivityDetailVO.getSlaIdentifier())) {
									mailActivityDetailVO.setCompanyCode(monitorMailSlaVo.getCompanyCode());
									mailActivityDetailVO.setMailBagNumber(monitorMailSlaVo.getMailBagNumber());
									mailActivityDetailVO.setServiceLevelActivity(SLAACTIVITY_ARRIVAL_TO_DELIVERY);
									mailActivityDetailVO.setGpaCode(poaCode);
									mailActivityDetailVO.setPlannedTime(monitorMailSlaVo.getScanTime()
											.plusHours(mailActivityDetailVO.getServiceTime()));
									mailActivityDetailVO.setActualTime(null);
									mailActivityDetailVO.setAirlineCode(monitorMailSlaVo.getAirlineCode());
									mailActivityDetailVO.setAirlineIdentifier(monitorMailSlaVo.getAirlineIdentifier());
									mailActivityDetailVO.setFlightCarrierCode(monitorMailSlaVo.getFlightCarrierCode());
									mailActivityDetailVO
											.setFlightCarrierId(monitorMailSlaVo.getFlightCarrierIdentifier());
									mailActivityDetailVO.setFlightNumber(monitorMailSlaVo.getFlightNumber());
									mailActivityDetailVO.setMailCategory(mailCategory);
									mailActivityDetailVO.setSlaStatus(null);
									new MailActivityDetail(mailActivityDetailVO);
								}
							}
						}
					}
				}
			} else if (MAILSTATUS_DELIVERED.equals(monitorMailSlaVo.getActivity())) {
				log.debug("" + "THE OPERATIONAL FLAG FOR THE DLV " + " " + monitorMailSlaVo.getOperationFlag());
				if (OPERATION_FLAG_UPDATE.equals(monitorMailSlaVo.getOperationFlag())) {
					MailActivityDetail mailActivityDetail = null;
					try {
						mailActivityDetail = MailActivityDetail.find(monitorMailSlaVo.getCompanyCode(),
								monitorMailSlaVo.getMailBagNumber(), SLAACTIVITY_ARRIVAL_TO_DELIVERY);
					} catch (FinderException finderException) {
						log.error("Finder Exception occurred in finding MailActivityDetail entity");
					}
					if (mailActivityDetail != null) {
						if (monitorMailSlaVo.getScanTime().isAfter(localDateUtil
								.getLocalDateTime(mailActivityDetail.getPlannedTime(), getLogonAirport()))) {
							mailActivityDetail.setSlaStatus(SLASTATUS_FAILURE);
						} else {
							mailActivityDetail.setSlaStatus(SLASTATUS_SUCCESS);
						}
						mailActivityDetail.setActualTime(monitorMailSlaVo.getScanTime());
						log.info("MailActivityDetail has been updated");
					}
				}
			}
		}
		log.debug(CLASS + " : " + "monitorMailSLAActivity" + " Entering");
	}

	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	public String findSystemParameterValue(String syspar) {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return null;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* This method checks whether ULD integration Enabled or not
	* @return
	* @throws SystemException
	*/
	public boolean isULDIntegrationEnabled() {
		boolean isULDIntegrationEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		Map<String, String> systemParameterMap = null;
		var mastersServiceUtils = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		try {
			systemParameterMap = mastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
			isULDIntegrationEnabled = true;
		}
		log.debug("" + " isULDIntegrationEnabled :" + " " + isULDIntegrationEnabled);
		return isULDIntegrationEnabled;
	}

	private void constructDetailsForIntegrationForSaveCotainers(ContainerVO containerVO, boolean isUMSUpdateNeeded,
			Collection<ULDInFlightVO> uldInFlightVos, boolean isOprUldEnabled,
			Collection<UldInFlightVO> operationalUlds, ULDInFlightVO uldInFlightVO) {
		if (containerVO != null && containerVO.getContainerNumber() != null
				&& containerVO.getContainerNumber().length() >= 3) {
			boolean isULD = false;
			isULD = MailConstantsVO.ULD_TYPE.equals(containerVO.getType());
			log.debug("" + "THE ULD TYPE IS " + " " + isULD);
			if (isULD) {
				if (ContainerVO.OPERATION_FLAG_INSERT.equals(containerVO.getOperationFlag())
						|| ContainerVO.OPERATION_FLAG_UPDATE.equals(containerVO.getOperationFlag())) {
					if (isUMSUpdateNeeded) {
						if (null == uldInFlightVos) {
							uldInFlightVos = new ArrayList<ULDInFlightVO>();
						}
						uldInFlightVO = new ULDInFlightVO();
						uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
						uldInFlightVO.setPointOfUnLading(containerVO.getPou());
						uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
						uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_ASSIGNED);
						uldInFlightVos.add(uldInFlightVO);
					}
					if (!containerVO.isReassignFlag() && isOprUldEnabled
							&& MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
						log.debug("ITS AN ULD SO ADDING TO THE OPERATIONAL ULDS ");
						operationalUlds.add(constructUldInFlightVO(containerVO));
					}
				}
			}
		}
	}

	/** 
	* @param containerVos
	* @throws SystemException
	* @author a-1936 This method is used to save the containers for Destination
	*/
	private void saveContainersForDestination(Collection<ContainerVO> containerVos) {
		log.debug(CLASS + " : " + "saveContainersForDestination" + " Entering");
		log.debug("" + "Containers to Save " + " " + containerVos);
		if (CollectionUtils.isNotEmpty(containerVos)) {
			for (ContainerVO containerVo : containerVos) {
				if (OPERATION_FLAG_INSERT.equals(containerVo.getOperationFlag())) {
					createContainer(containerVo);
				} else if (OPERATION_FLAG_UPDATE.equals(containerVo.getOperationFlag())) {
					modifyContainer(containerVo);
				} else if (OPERATION_FLAG_DELETE.equals(containerVo.getOperationFlag())) {
					deleteContainer(containerVo);
				}
			}
		}
	}

	/** 
	* @param operationalFlightVO
	* @param containerVos
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @author a-1936 This method is used to save the containers for the flightchecks 1.check whether the Fligt is Closed if Exception 2.else find finderthrown create 3.For each ContainerVo call findflightsegments group the containers get segsernum and check findassignedflightsegment if no create
	*/
	private void saveContainersForFlight(OperationalFlightVO operationalFlightVO, Collection<ContainerVO> containerVos)
			throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "saveContainersForFlight" + " Entering");
		validateAndCreateAssignedFlight(operationalFlightVO);
		if (containerVos != null && containerVos.size() > 0) {
			containerVos = validateFlightForSegment(operationalFlightVO, containerVos);
			for (ContainerVO containerVo : containerVos) {
				containerVo.setCarrierId(operationalFlightVO.getCarrierId());
				containerVo.setCompanyCode(operationalFlightVO.getCompanyCode());
				containerVo.setFlightNumber(operationalFlightVO.getFlightNumber());
				containerVo.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				containerVo.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
				validateAssignedFlightSegment(containerVo);
			}
			log.debug("" + "Flight details " + " " + operationalFlightVO);
			log.debug("" + "Containers to Save " + " " + containerVos);
			persistContainerVos(containerVos);
		}
	}

	private void performReassignFromSaveContainers(Collection<ContainerVO> reassignContainers,
			OperationalFlightVO operationalFlightVO) throws FlightClosedException, ContainerAssignmentException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, MailDefaultStorageUnitException {
		log.debug(CLASS + " : " + "performReassignFromSaveContainers" + " Entering");
		Map<String, Collection<ContainerVO>> pouContainersMap = null;
		Collection<String> pous = null;
		pouContainersMap = createPouContainersMap(reassignContainers);
		pous = pouContainersMap.keySet();
		for (String pou : pous) {
			int segsernum = 0;
			operationalFlightVO.setPou(pou);
			log.info("" + "POU : " + " " + pou);
			Collection<ContainerVO> containersReturn = null;
			containersReturn = reassignContainers(pouContainersMap.get(pou), operationalFlightVO);
			if (containersReturn != null && containersReturn.size() > 0) {
				ContainerVO containerForReturn = new ArrayList<ContainerVO>(containersReturn).get(0);
				segsernum = containerForReturn.getSegmentSerialNumber();
			}
			if (segsernum <= 0) {
				segsernum = MailConstantsVO.DESTN_FLT;
			}
			for (ContainerVO reassignedCont : pouContainersMap.get(pou)) {
				reassignedCont.setSegmentSerialNumber(segsernum);
			}
		}
	}

	/** 
	* TODO Purpose Feb 6, 2007, A-1739
	* @param containerDetailsVO
	*/
	private void updateFlightDetailsForMailbagVOs(ContainerDetailsVO containerDetailsVO) {
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			}
		}
	}

	/**
	* @return Collection<ContainerDetailsVO>This function is to check whether any duplicate consignmentno with same unique ie first 20 character of despatch exist with containers or within the container .If such a sitution occurs it will add bags,vol together with details of first DespatchDetailsVO
	* @throws SystemException
	* @author A-2107
	*/
	private Collection<ContainerDetailsVO> compareAndCalculateTotalsOfDespatches(
			Collection<ContainerDetailsVO> containers) {
		log.debug(CLASS + " : " + "compareAndCalculateTotalsOfDespatches" + " Entering");
		DespatchDetailsVO despvo1 = null;
		DespatchDetailsVO despvo2 = null;
		Collection<DespatchDetailsVO> despatchVOs = null;
		List<DespatchDetailsVO> innerDespatchVOList = null;
		List<DespatchDetailsVO> outerDespatchVOList = null;
		if (containers != null && containers.size() > 0) {
			List<ContainerDetailsVO> containerVOList = (List<ContainerDetailsVO>) containers;
			int containerVOSize = containerVOList.size();
			for (int i = 0; i < containerVOSize; i++) {
				despatchVOs = containerVOList.get(i).getDesptachDetailsVOs();
				if (despatchVOs != null && despatchVOs.size() > 1) {
					innerDespatchVOList = (List<DespatchDetailsVO>) despatchVOs;
					int innerDespatchVOSize = innerDespatchVOList.size();
					for (int j = 0; j < innerDespatchVOSize; j++) {
						despvo1 = innerDespatchVOList.get(j);
						for (int k = (j + 1); k < innerDespatchVOSize; k++) {
							despvo2 = innerDespatchVOList.get(k);
							if (compareDespatchDetailsVOs(despvo1, despvo2)) {
								log.debug("Consignment no is same");
								despvo1.setAcceptedBags(despvo1.getAcceptedBags() + despvo2.getAcceptedBags());
								despvo1.setStatedBags(despvo1.getStatedBags() + despvo2.getStatedBags());
								try {
									despvo1.setAcceptedWeight(
											despvo1.getAcceptedWeight().add(despvo2.getAcceptedWeight()));
								} finally {
								}
								try {
									despvo1.setStatedWeight(despvo1.getStatedWeight().add(despvo2.getStatedWeight()));
								} finally {
								}
								try {
									despvo1.setStatedVolume(despvo1.getStatedVolume().add(despvo2.getStatedVolume()));
								} finally {
								}
								try {
									despvo1.setAcceptedVolume(
											despvo1.getAcceptedVolume().add(despvo2.getAcceptedVolume()));
								} finally {
								}
								despvo1.setOperationalFlag(DespatchDetailsVO.OPERATION_FLAG_INSERT);
								innerDespatchVOList.remove(despvo2);
								j--;
								innerDespatchVOSize = innerDespatchVOList.size();
							}
						}
						for (int l = (i + 1); l < containerVOSize; l++) {
							despatchVOs = containerVOList.get(l).getDesptachDetailsVOs();
							if (despatchVOs != null && despatchVOs.size() > 1) {
								outerDespatchVOList = (List<DespatchDetailsVO>) despatchVOs;
								int outerDespatchVOSize = outerDespatchVOList.size();
								for (int m = 0; m < outerDespatchVOSize; m++) {
									despvo2 = outerDespatchVOList.get(m);
									if (compareDespatchDetailsVOs(despvo1, despvo2)) {
										log.debug("Consignment no is same");
										despvo1.setAcceptedBags(despvo1.getAcceptedBags() + despvo2.getAcceptedBags());
										despvo1.setStatedBags(despvo1.getStatedBags() + despvo2.getStatedBags());
										try {
											despvo1.setAcceptedWeight(
													despvo1.getAcceptedWeight().add(despvo2.getAcceptedWeight()));
										} finally {
										}
										try {
											despvo1.setStatedWeight(
													despvo1.getStatedWeight().add(despvo2.getStatedWeight()));
										} finally {
										}
										try {
											despvo1.setStatedVolume(
													despvo1.getStatedVolume().add(despvo2.getStatedVolume()));
										} finally {
										}
										try {
											despvo1.setAcceptedVolume(
													despvo1.getAcceptedVolume().add(despvo2.getAcceptedVolume()));
										} finally {
										}
										despvo1.setOperationalFlag(DespatchDetailsVO.OPERATION_FLAG_INSERT);
										outerDespatchVOList.remove(despvo2);
										m--;
										outerDespatchVOSize = outerDespatchVOList.size();
									}
								}
							}
						}
					}
				}
			}
		}
		return containers;
	}

	/** 
	* A-1739
	* @param containerDetails
	*/
	private Map<String, Collection<DespatchDetailsVO>> groupDespatchesForConsignment(
			Collection<ContainerDetailsVO> containerDetails) {
		Map<String, Collection<DespatchDetailsVO>> despatchMap = new HashMap<String, Collection<DespatchDetailsVO>>();
		if (containerDetails != null && containerDetails.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				if (containerDetailsVO.getOperationFlag() != null) {
					Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
					if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
						for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
							if (despatchDetailsVO.getOperationalFlag() != null
									&& OPERATION_FLAG_INSERT.equals(despatchDetailsVO.getOperationalFlag())) {
								String documentPK = constructDocumentPK(despatchDetailsVO, containerDetailsVO);
								Collection<DespatchDetailsVO> docDespatches = despatchMap.get(documentPK);
								if (docDespatches == null) {
									docDespatches = new ArrayList<DespatchDetailsVO>();
									despatchMap.put(documentPK, docDespatches);
								}
								if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
									despatchDetailsVO.setUldNumber(containerDetailsVO.getContainerNumber());
								} else {
									despatchDetailsVO.setUldNumber(null);
								}
								docDespatches.add(despatchDetailsVO);
							}
						}
					}
				}
			}
		}
		return despatchMap;
	}

	/** 
	* This methods constructs a docPK for consignment master save A-1739
	* @param docPK
	* @param despatches
	* @param airportCode
	* @return
	*/
	private ConsignmentDocumentVO constructConsignmentDocVO(String docPK, Collection<DespatchDetailsVO> despatches,
			String airportCode) {
		String[] tokens = docPK.split(ID_SEP);
		int idx = 0;
		ConsignmentDocumentVO docVO = new ConsignmentDocumentVO();
		docVO.setCompanyCode(tokens[idx++]);
		docVO.setConsignmentNumber(tokens[idx++]);
		docVO.setPaCode(tokens[idx++]);
		docVO.setAirportCode(airportCode);
		constructMailConsignsFromDespatch(despatches, docVO);
		docVO.setOperation(MailConstantsVO.OPERATION_OUTBOUND);
		return docVO;
	}

	/** 
	* A-1739
	* @param consignmentSeqNum
	* @param despatches
	*/
	private void updateDespatchesSequenceNum(int consignmentSeqNum, Collection<DespatchDetailsVO> despatches) {
		for (DespatchDetailsVO despatchDetailsVO : despatches) {
			despatchDetailsVO.setConsignmentSequenceNumber(consignmentSeqNum);
		}
	}

	/** 
	* findCityForOfficeOfExchange
	* @param companyCode
	* @param officeOfExchanges
	* @return MAP<CITY   ,   OFFICE_OF_EXCHANGE>
	* @throws SystemException
	*/
	public HashMap<String, String> findCityForOfficeOfExchange(String companyCode,
			Collection<String> officeOfExchanges) {
		log.debug(CLASS + " : " + "findCityForOfficeOfExchange" + " Entering");
		OfficeOfExchangeFilterModel filterModel = new OfficeOfExchangeFilterModel();
		filterModel.setCompanyCode(companyCode);
		filterModel.setOfficeOfExchanges(officeOfExchanges);
		MailTrackingDefaultsBI mailMasterApi = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
		Map airportCodeMap = mailMasterApi.findCityForOfficeOfExchange(filterModel);
		return (HashMap<String, String>) airportCodeMap;

	}

	/** 
	* @return
	* @throws SystemException
	* @author a-1936 Added By Karthick V This method is used to find the LogonAirport From the Logon Attributes.
	*/
	public String getLogonAirport() {
		log.debug(" Finding the  Airport from the Logon Attributes");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		return logonAttributes.getAirportCode();
	}

	private UldInFlightVO constructUldInFlightVO(ContainerVO containerVO) {
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerVO.getCompanyCode());
		uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
		uldInFlightVO.setPou(containerVO.getPou());
		uldInFlightVO.setAirportCode(containerVO.getAssignedPort());
		uldInFlightVO.setCarrierId(containerVO.getCarrierId());
		if (containerVO.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerVO.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		}
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_OUTBOUND);
		return uldInFlightVO;
	}

	/** 
	* @param containerVo
	* @throws SystemException
	* @author a-1936 This method is used to create a new Container in theSystem
	*/
	private Container createContainer(ContainerVO containerVo) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		ContainerPK containerPk = new ContainerPK();
		Container container = null;
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		try {
			container = Container.find(containerPk);
		} catch (FinderException ex) {
		}
		if (container == null) {
			container = new Container(containerVo);
			if (!"B".equals(containerVo.getType())) {
				mailController.flagMLDForMailOperationsInULD(containerVo, MailConstantsVO.MLD_STG);
			}
		}
		//TODO: Neo to redo audit code
//		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,
//				ContainerVO.ENTITY);
//		containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(containerAuditVO, container, true);
//		collectContainerAuditDetails(container, containerAuditVO);
		return container;
	}

	/** 
	* @param containerVo
	* @throws SystemException
	* @author a-1936 This method is used to modify the container
	*/
	private void modifyContainer(ContainerVO containerVo) {
		ContainerPK containerPk = new ContainerPK();
		Container container = null;
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		try {
			container = Container.find(containerPk);
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (MailConstantsVO.FLAG_YES.equals(container.getAcceptanceFlag())
				&& (containerVo.getOnwardRoutings() != null && !containerVo.getOnwardRoutings().isEmpty())
				|| (container.getOnwardRoutes() != null && !container.getOnwardRoutes().isEmpty())) {
			updateOnwardRoutingForULDSeg(containerVo);
		}
		if (containerVo.isFromDeviationList() && container.getTransitFlag() != null) {
			containerVo.setTransitFlag(container.getTransitFlag());
		}
		if (container.getUldReferenceNo() > 0) {
			containerVo.setUldReferenceNo(container.getUldReferenceNo());
		}
		container.update(containerVo);
		//TODO: Neo to re write audit
//		ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,
//				ContainerVO.ENTITY);
//		collectContainerAuditDetails(container, containerAuditVO);
	}

	/** 
	* @param containerVo
	* @throws SystemException
	* @author a-1936 This method is used to delete the container
	*/
	private void deleteContainer(ContainerVO containerVo) {
		ContainerPK containerPk = new ContainerPK();
		Container container = null;
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		try {
			container = Container.find(containerPk);
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (MailConstantsVO.BULK_TYPE.equals(container.getContainerType())
				&& !("-1").equals(containerVo.getFlightNumber()) && containerVo.getFlightSequenceNumber() > 0) {
			consrtuctAndRemoveBulkForSegment(containerVo);
		}

		container.setLastUpdatedUser(containerVo.getLastUpdateUser());
		container.remove();
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerVo.getCompanyCode());
		uldInFlightVO.setUldNumber(containerVo.getContainerNumber());
		uldInFlightVO.setPou(containerVo.getPou());
		uldInFlightVO.setAirportCode(containerVo.getAssignedPort());
		uldInFlightVO.setCarrierId(containerVo.getCarrierId());
		if (containerVo.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerVo.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerVo.getLegSerialNumber());
		}
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		Collection<UldInFlightVO> operationalUlds = new ArrayList<UldInFlightVO>();
		operationalUlds.add(uldInFlightVO);
		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		if (isOprUldEnabled) {
			if (!MailConstantsVO.BULK_TYPE.equals(containerVo.getType())) {
				if (operationalUlds != null && operationalUlds.size() > 0) {
					operationsFltHandlingProxy.saveOperationalULDsInFlight(operationalUlds);
				}
			}
		}
		ContextUtil.getInstance().getBean(MailController.class).auditContainerUpdates(containerVo,MailConstantsVO.AUD_CON_REM,"DELETED","Container is deleted","");
		//AuditUtils.performAudit(containerAuditVO);
	}

	/** 
	* @param operationalFlightVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @author a-1936 This method is used to validateflight for closed statusand if not exists create
	*/
	private void validateAndCreateAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws ContainerAssignmentException {
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("No Assigned Flight...Creating..");
			createAssignedFlight(operationalFlightVO);
		}
	}

	/** 
	* @param operationalFlightVO
	* @param containerVos
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @author a-1936 This method is used to get the segmentSerialNumber foreacgh POL-POU combination
	*/
	public Collection<ContainerVO> validateFlightForSegment(OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> containerVos) throws ContainerAssignmentException {
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		int segmentSerialNumber = 0;
		log.info("Validate the Container For the Flight");
		segmentSummaryVos = flightOperationsProxy.findFlightSegments(operationalFlightVO.getCompanyCode(),
				operationalFlightVO.getCarrierId(), operationalFlightVO.getFlightNumber(),
				(int) operationalFlightVO.getFlightSequenceNumber());
		if (segmentSummaryVos != null && segmentSummaryVos.size() > 0) {
			for (ContainerVO containerVO : containerVos) {
				for (FlightSegmentSummaryVO segmentVo : segmentSummaryVos) {
					if (segmentVo.getSegmentOrigin() != null && segmentVo.getSegmentDestination() != null) {
						if (segmentVo.getSegmentOrigin().equals(containerVO.getAssignedPort())
								&& segmentVo.getSegmentDestination().equals(containerVO.getPou())) {
							segmentSerialNumber = segmentVo.getSegmentSerialNumber();
							containerVO.setSegmentSerialNumber(segmentSerialNumber);
							log.debug("" + "THE POL IS" + " " + containerVO.getAssignedPort());
							log.debug("" + "THE POU IS" + " " + containerVO.getPou());
							log.debug("" + "THE SEGSERNUM FOR THE POL-POU" + " " + segmentSerialNumber);
							break;
						}
					}
				}
				if (containerVO.getSegmentSerialNumber() == 0) {
					throw new ContainerAssignmentException(ContainerAssignmentException.INVALID_FLIGHT_SEGMENT);
				}
			}
		} else {
			log.error("No Segment Details obtained from Flight module");
		}
		return containerVos;
	}

	/** 
	* @param containerVo
	* @throws SystemException
	* @author a-1936 If the segment for the particularFlight does not existcreate a FlightSegment
	*/
	private void validateAssignedFlightSegment(ContainerVO containerVo) {
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCarrierId(containerVo.getCarrierId());
		assignedFlightSegmentPK.setCompanyCode(containerVo.getCompanyCode());
		assignedFlightSegmentPK.setFlightNumber(containerVo.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
		try {
			AssignedFlightSegment.find(assignedFlightSegmentPK);
		} catch (FinderException ex) {
			log.info("FINDEREXCEPTION THROWN FOR ASSIGNEDFLTSEG");
			AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
			assignedFlightSegmentVO.setCarrierId(containerVo.getCarrierId());
			assignedFlightSegmentVO.setCompanyCode(containerVo.getCompanyCode());
			assignedFlightSegmentVO.setFlightNumber(containerVo.getFlightNumber());
			assignedFlightSegmentVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
			assignedFlightSegmentVO.setPol(containerVo.getAssignedPort());
			assignedFlightSegmentVO.setPou(containerVo.getPou());
			assignedFlightSegmentVO.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
			assignedFlightSegmentVO.setMraStatus(MailConstantsVO.MRA_STATUS_NEW);
			new AssignedFlightSegment(assignedFlightSegmentVO);
		}
	}

	/** 
	* @param containerVos
	* @throws SystemException
	* @author a-1936 This method is used to persist the containers from thecollection
	*/
	private void persistContainerVos(Collection<ContainerVO> containerVos) {
		for (ContainerVO containerVo : containerVos) {
			if (OPERATION_FLAG_INSERT.equals(containerVo.getOperationFlag())) {
				createContainer(containerVo);
			} else if (OPERATION_FLAG_UPDATE.equals(containerVo.getOperationFlag())) {
				if (!containerVo.isFromDeviationList()) {
					modifyContainer(containerVo);
				}
			} else if (OPERATION_FLAG_DELETE.equals(containerVo.getOperationFlag())) {
				deleteContainer(containerVo);
			}
		}
	}

	/** 
	* Mar 13, 2007, a-1739
	* @param reassignContainers
	* @return
	*/
	private Map<String, Collection<ContainerVO>> createPouContainersMap(Collection<ContainerVO> reassignContainers) {
		Map<String, Collection<ContainerVO>> pouContainersMap = new HashMap<String, Collection<ContainerVO>>();
		Collection<ContainerVO> containerVOs = null;
		for (ContainerVO containerVO : reassignContainers) {
			containerVOs = pouContainersMap.get(containerVO.getPou());
			if (containerVOs == null) {
				containerVOs = new ArrayList<ContainerVO>();
				pouContainersMap.put(containerVO.getPou(), containerVOs);
			}
			containerVOs.add(containerVO);
		}
		return pouContainersMap;
	}

	/** 
	* This method reassigns a collection of containers from to another flight or destination A-1739
	* @param containersToReassign
	* @param toFlightVO
	* @return
	* @throws SystemException
	* @throws FlightClosedException
	* @throws ContainerAssignmentException
	* @throws InvalidFlightSegmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public Collection<ContainerVO> reassignContainers(Collection<ContainerVO> containersToReassign,
			OperationalFlightVO toFlightVO) throws FlightClosedException, ContainerAssignmentException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, MailDefaultStorageUnitException {
		log.debug(CLASS + " : " + "reassignContainers" + " Entering");
		log.debug("" + "To Flight details " + " " + toFlightVO);
		log.debug("" + "Containers to Reassign " + " " + containersToReassign);
		return reassignController.reassignContainers(containersToReassign, toFlightVO);
	}

	private boolean compareDespatchDetailsVOs(DespatchDetailsVO despvo1, DespatchDetailsVO despvo2) {
		if (despvo1.getConsignmentNumber().equalsIgnoreCase(despvo2.getConsignmentNumber())
				&& despvo1.getOriginOfficeOfExchange().equals(despvo2.getOriginOfficeOfExchange())
				&& despvo1.getDestinationOfficeOfExchange().equals(despvo2.getDestinationOfficeOfExchange())
				&& despvo1.getMailCategoryCode().equals(despvo2.getMailCategoryCode())
				&& despvo1.getMailSubclass().equals(despvo2.getMailSubclass())
				&& despvo1.getYear() == (despvo2.getYear()) && despvo1.getDsn().equals(despvo2.getDsn())) {
			return true;
		}
		return false;
	}

	private String constructDocumentPK(DespatchDetailsVO despatchDetailsVO, ContainerDetailsVO containerDtlsVO) {
		//TODO: Below code to be corrected in Neo- refer classic
		return new StringBuilder().append(despatchDetailsVO.getCompanyCode()).append(ID_SEP)
				.append(despatchDetailsVO.getConsignmentNumber()).append(ID_SEP).append(despatchDetailsVO.getPaCode())
				.append(ID_SEP).append(despatchDetailsVO.getConsignmentDate()).toString();
	}

	/** 
	* A-1739
	* @param despatches
	* @param docVO
	* @return
	*/
	private void constructMailConsignsFromDespatch(Collection<DespatchDetailsVO> despatches,
			ConsignmentDocumentVO docVO) {
		Page<MailInConsignmentVO> mailInConsigns = new Page<MailInConsignmentVO>(new ArrayList<MailInConsignmentVO>(),
				0, 0, 0, 0, 0, false);
		int statedBagDlt = 0;
		double statedWtDlt = 0;
		for (DespatchDetailsVO despatchDetailsVO : despatches) {
			MailInConsignmentVO mailInConsign = new MailInConsignmentVO();
			mailInConsign.setCompanyCode(despatchDetailsVO.getCompanyCode());
			mailInConsign.setConsignmentNumber(despatchDetailsVO.getConsignmentNumber());
			mailInConsign.setConsignmentSequenceNumber(despatchDetailsVO.getConsignmentSequenceNumber());
			mailInConsign.setPaCode(despatchDetailsVO.getPaCode());
			mailInConsign.setDsn(despatchDetailsVO.getDsn());
			mailInConsign.setOriginExchangeOffice(despatchDetailsVO.getOriginOfficeOfExchange());
			mailInConsign.setDestinationExchangeOffice(despatchDetailsVO.getDestinationOfficeOfExchange());
			mailInConsign.setMailClass(despatchDetailsVO.getMailClass());
			mailInConsign.setMailSubclass(despatchDetailsVO.getMailSubclass());
			mailInConsign.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
			mailInConsign.setYear(despatchDetailsVO.getYear());
			mailInConsign.setStatedBags(despatchDetailsVO.getStatedBags());
			mailInConsign.setStatedWeight(despatchDetailsVO.getStatedWeight());
			if (despatchDetailsVO.getStatedBags() > 0) {
				mailInConsign.setStatedBags(despatchDetailsVO.getStatedBags());
				mailInConsign.setStatedWeight(despatchDetailsVO.getStatedWeight());
			} else {
				mailInConsign.setStatedBags(despatchDetailsVO.getReceivedBags());
				mailInConsign.setStatedWeight(despatchDetailsVO.getReceivedWeight());
			}
			mailInConsigns.add(mailInConsign);
			statedBagDlt += (despatchDetailsVO.getStatedBags() - despatchDetailsVO.getPrevStatedBags());
			//TODO: Neo to verify below code
//			statedWtDlt += (despatchDetailsVO.getStatedWeight().getRoundedSystemValue()
//					- despatchDetailsVO.getPrevStatedWeight().getRoundedSystemValue());
			statedWtDlt += (despatchDetailsVO.getStatedWeight().getRoundedValue().subtract(despatchDetailsVO.getPrevStatedWeight().getRoundedValue())).doubleValue();
			mailInConsign.setUldNumber(despatchDetailsVO.getUldNumber());
		}
		docVO.setMailInConsignmentVOs(mailInConsigns);
	}

	/** 
	* @param container
	* @param containerAuditVO
	* @author a-1936 This method is used to collect the AuditDetails
	*/
	//TODO: Neo to rewrite audit
//	private void collectContainerAuditDetails(Container container, ContainerAuditVO containerAuditVO) {
//		log.debug("---------Setting ContainerAuditVO Details-------");
//		StringBuffer additionalInfo = new StringBuffer();
//		log.info("" + " container.getContainerPK() " + " " + container.getContainerPK());
//		containerAuditVO.setCompanyCode(container.getContainerPK().getCompanyCode());
//		containerAuditVO.setContainerNumber(container.getContainerPK().getContainerNumber());
//		containerAuditVO.setAssignedPort(container.getContainerPK().getAssignmentPort());
//		containerAuditVO.setCarrierId(container.getContainerPK().getCarrierId());
//		containerAuditVO.setFlightNumber(container.getContainerPK().getFlightNumber());
//		containerAuditVO.setFlightSequenceNumber(container.getContainerPK().getFlightSequenceNumber());
//		containerAuditVO.setLegSerialNumber(container.getContainerPK().getLegSerialNumber());
//		containerAuditVO.setUserId(container.getLastUpdateUser());
//		if (containerAuditVO.getAuditFields() != null && containerAuditVO.getAuditFields().size() > 0) {
//			log.info("Going to populate additional info");
//			for (AuditFieldVO auditField : containerAuditVO.getAuditFields()) {
//				if (auditField != null) {
//					additionalInfo.append(" Field Name: ").append(auditField.getFieldName());
//					if (auditField.getOldValue() != null) {
//						additionalInfo.append(" Old Value: ").append(auditField.getOldValue());
//					}
//					additionalInfo.append(" New Value: ").append(auditField.getNewValue());
//				} else {
//					log.warn("auditField is NULL!!!!!!!!!!!!!!!!!!!!!!!");
//				}
//			}
//		}
//		containerAuditVO.setAdditionalInformation(additionalInfo.toString());
//		log.debug("collectContainerAuditDetails" + " : " + "...Finished construction of AuditVO" + " Exiting");
//	}

	/** 
	* TODO Purpose Dec 5, 2006, a-1739
	* @param containerVO
	* @throws SystemException
	*/
	private void updateOnwardRoutingForULDSeg(ContainerVO containerVO) {
		log.debug(CLASS + " : " + "updateOnwardRoutingForULDSeg" + " Entering");
		new AssignedFlightSegment().updateULDOnwardRoute(containerVO);
		log.debug(CLASS + " : " + "updateOnwardRoutingForULDSeg" + " Exiting");
	}

	private void consrtuctAndRemoveBulkForSegment(ContainerVO containerVo) {
		log.debug(CLASS + " : " + "consrtuctAndRemoveBulkForSegment" + " Entering");
		log.debug("" + "The Container Vo is " + " " + containerVo);
		ULDForSegment uldForSegment = null;
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(containerVo.getCompanyCode());
		uldForSegmentPK.setCarrierId(containerVo.getCarrierId());
		uldForSegmentPK.setFlightNumber(containerVo.getFlightNumber());
		uldForSegmentPK.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		uldForSegmentPK.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
		String bulkname = new StringBuilder("BULK").append("-").append(containerVo.getPou()).toString();
		uldForSegmentPK.setUldNumber(bulkname);
		try {
			uldForSegment = ULDForSegment.find(uldForSegmentPK);
		} catch (FinderException ex) {
			uldForSegment = null;
		}
		if (uldForSegment != null) {
			log.debug("" + "The uldForSegment is " + " " + uldForSegment);
			if (uldForSegment.getNumberOfBags() == 0 && uldForSegment.getWeight() == 0.0
					&& uldForSegment.getReceivedBags() == 0 && uldForSegment.getReceivedWeight() == 0.0) {
				uldForSegment.remove();
			}
		}
	}

	private AssignedFlightPK constructAssignedFlightPK(OperationalFlightVO operationalFlightVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		if (operationalFlightVO.isForUCMSendChk()) {
			assignedFlightPk.setAirportCode(operationalFlightVO.getAirportCode());
		} else {
			assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		}
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		return assignedFlightPk;
	}

	/** 
	* @param operationalFlightVO
	* @throws SystemException
	* @author a-1936 This method is used to create the assignedFlight
	*/
	private void createAssignedFlight(OperationalFlightVO operationalFlightVO) {
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
		//TODO ADD add info
		//assignedFlightAuditVO.setAdditionalInformation("Flight Created");
		performAssignedFlightAudit(assignedFlightVO, MailConstantsVO.AUDIT_FLT_CREAT,"","CREATED");
	}

	/** 
	* @param assignedFlightAuditVO
	* @param assignedFlight
	* @param actionCode
	* @throws SystemException
	* @author A-1936
	*/
	//TODO: Neo to rewrite audit
//	private void performAssignedFlightAudit(AssignedFlightAuditVO assignedFlightAuditVO, AssignedFlight assignedFlight,
//			String actionCode) {
//		AssignedFlightPK assignedFlightPK = assignedFlight.getAssignedFlightPk();
//		assignedFlightAuditVO.setCompanyCode(assignedFlightPK.getCompanyCode());
//		assignedFlightAuditVO.setAirportCode(assignedFlightPK.getAirportCode());
//		assignedFlightAuditVO.setFlightNumber(assignedFlightPK.getFlightNumber());
//		assignedFlightAuditVO.setFlightSequenceNumber(assignedFlightPK.getFlightSequenceNumber());
//		assignedFlightAuditVO.setCarrierId(assignedFlightPK.getCarrierId());
//		assignedFlightAuditVO.setLegSerialNumber(assignedFlightPK.getLegSerialNumber());
//		assignedFlightAuditVO.setActionCode(actionCode);
//		AuditUtils.performAudit(assignedFlightAuditVO);
//		log.debug("DSN" + " : " + "performAssignedFlightAudit" + " Exiting");
//	}

	/** 
	* @param containerNumber
	* @return
	* @throws SystemException
	* @author A-3227This method fetches the latest Container Assignment irrespective of the PORT to which it is assigned. This to know the current assignment of the Contianer.
	*/
	public ContainerAssignmentVO findLatestContainerAssignment(String containerNumber) {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (containerNumber != null) {
			LoginProfile logonAttributes = ContextUtil.getInstance().getBean(ContextUtil.class).callerLoginProfile();
			containerAssignmentVO = Container.findLatestContainerAssignment(logonAttributes.getCompanyCode(),
					containerNumber);
		}
		return containerAssignmentVO;
	}

	/** 
	* Added by A-5526
	* @param containerVO
	* @return
	* @throws SystemException
	*/
	public ContainerAssignmentVO findContainerAssignmentForUpload(ContainerVO containerVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (containerVO.getContainerNumber() != null) {
			containerAssignmentVO = Container.findContainerAssignment(containerVO.getCompanyCode(),
					containerVO.getContainerNumber(), containerVO.getAssignedPort());
		}
		return containerAssignmentVO;
	}

	/** 
	* @param flightFilterVO
	* @return
	* @throws SystemException
	* @author a-1936 This a FlightProductProxyCall .This method is used tovalidate the FlightForAirport
	*/
	public Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO) {
		log.debug(CLASS + " : " + "validateFlight" + " Entering");
		var flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		return flightOperationsProxy.validateFlightForAirport(flightFilterVO);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange) {
		OfficeOfExchangeModel officeOfExchangeModel = mailMasterApi.validateOfficeOfExchange(companyCode,officeOfExchange);
		return  mailOperationsMapper.officeOfExchangeModelToOfficeOfExchangeVO(officeOfExchangeModel);
	}

	/**
	* @author A-1936 Added By Karthick v as the part of the NCA Mail TrackingCR -(Auto Create UBrs For the Booking) Validating commodity codes in each Shipment DetailVO and calculating the Volume by using the Density factor of the corrosponding commodity if it is not specified by the user.. Since this is an despacth to the Booking Server Straight away the Booking Client Code is skipped where these calculations afre actually done so its better to do the Volume Calculations in MailTracking System even before the Despacth..
	*/
	public CommodityValidationVO validateCommodity(String companyCode, String commodityCode, String poaCode) {
		log.debug("ValidateBookingCommand" + " : " + "doCommodityCodeValidation" + " Entering");
		CommodityValidationVO commodityValidationVo = null;
		Collection<String> commodityColl = new ArrayList<String>();
		Map<String, CommodityValidationVO> commodityMap = null;
		String densityFactor = null;
		if (poaCode != null && !poaCode.isEmpty()) {
			densityFactor = findDensityfactorForPA(companyCode, poaCode);
		}
		if (densityFactor != null && !densityFactor.isEmpty()) {
			commodityValidationVo = new CommodityValidationVO();
			commodityValidationVo.setCompanyCode(companyCode);
			commodityValidationVo.setDensityFactor(Double.parseDouble(densityFactor));
			return commodityValidationVo;
		}
		commodityColl.add(commodityCode);
		try {
			commodityMap = neoMastersServiceUtils.validateCommodityCodes(companyCode, commodityColl);
		} catch (BusinessException ex) {
			throw new SystemException(ex.getMessage());
		}
		if (commodityMap != null && commodityMap.size() > 0) {
			commodityValidationVo = commodityMap.get(commodityCode);
		}
		return commodityValidationVo;
	}

	/** 
	* Method		:	MailController.validateDestination Added by 	: Used for 	: Parameters	:	@param scannedMailDetailsVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	boolean
	*/
	public boolean validateAirport(String airport, String companyCode) {
		log.debug("MAILCONTROLLER" + " : " + "validateDestination" + " Entering");
		AirportValidationVO airportValidationVO = null;
		if (airport != null && !"".equals(airport)) {
			try {
				airportValidationVO = ContextUtil.getInstance().getBean(SharedAreaProxy.class).validateAirportCode(companyCode, airport.toUpperCase());
			} finally {
			}
			if (airportValidationVO != null) {
				log.info("true");
				return true;
			}
		}
		log.info("false");
		return false;
	}

	/** 
	* @param shipmentDetailsVos
	* @return
	* @throws SystemException
	* @author A-4810
	*/
	public Collection<EmbargoDetailsVO> checkEmbargoForMail(Collection<ShipmentDetailsVO> shipmentDetailsVos) {
		Collection<EmbargoDetailsVO> embargoDetailsVOs = null;
		try {
			embargoDetailsVOs = recoDefaultsProxy.checkForEmbargo(shipmentDetailsVos);
		} finally {
		}
		return embargoDetailsVOs;
	}

	/** 
	* Validates the mailbags for upload Oct 6, 2006, a-1739
	* @param mailbags
	* @return
	* @throws InvalidMailTagFormatException
	* @throws SystemException
	*/
	public Collection<MailbagVO> validateScannedMailDetails(Collection<MailbagVO> mailbags)
			throws InvalidMailTagFormatException {
		boolean isFromHHT = true;
		if (mailbags != null && mailbags.size() > 0
				&& MailConstantsVO.MLD.equals(mailbags.iterator().next().getMailSource())) {
			isFromHHT = false;
		}
		new Mailbag().validateMailBags(mailbags, isFromHHT);
		return mailbags;
	}

	/** 
	* @param companyCode
	* @param subclass
	* @return
	* @throws SystemException
	* @author A-3227 Reno K AbrahamThis method is used to validate the MailSubClass
	*/
	public boolean validateMailSubClass(String companyCode, String subclass) {
		log.debug(CLASS + " : " + "validateMailSubClass" + " Entering");
		return ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class).validateMailSubClass(companyCode, subclass);
	}

	/** 
	* @param containerVO
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to retrieve theContainerassignmentDetails if it is already assigned
	*/
	public ContainerAssignmentVO findContainerAssignment(ContainerVO containerVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		return Container.findContainerAssignment(containerVO.getCompanyCode(), containerVO.getContainerNumber(),
				containerVO.getAssignedPort());
	}

	/** 
	* @param containerVO
	* @return
	* @throws FinderException
	* @throws SystemException
	* @author a-1936 Added By Karthick V as the part of the NCA Mail TrackingBug Fix
	*/
	public Container findContainer(ContainerVO containerVO) throws FinderException {
		log.debug(CLASS + " : " + "findContainer" + " Entering");
		Container container = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setAssignmentPort(containerVO.getAssignedPort());
		containerPK.setCarrierId(containerVO.getCarrierId());
		containerPK.setCompanyCode(containerVO.getCompanyCode());
		containerPK.setContainerNumber(containerVO.getContainerNumber());
		containerPK.setFlightNumber(containerVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
		container = Container.find(containerPK);
		log.debug(CLASS + " : " + "findContainer" + " Exiting");
		return container;
	}

	/** 
	* @param companyCode
	* @param containerNumber
	* @return
	* @throws SystemException
	*/
	public boolean validateUld(String companyCode, String containerNumber) {
		ULDVO uldVO = null;
		log.debug("MAILCONTROLLER" + " : " + "validateUld" + " Entering");
		if (containerNumber != null && !"".equals(containerNumber)) {
			try {
				uldVO = uLDDefaultsProxy.findULDDetails(companyCode, containerNumber);
			} catch (ULDDefaultsProxyException e) {
				return false;
			}
			if (uldVO != null) {
				return true;
			}
		}
		return false;
	}

	/** 
	* @param operationalFlightVO
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to check whether the Flight is closedfor Operations
	*/
	public boolean isFlightClosedForOperations(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "isFlightClosedForOperations" + " Entering");
		boolean isFlightClosedForOperations = false;
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info(FINDEREXCEPTIO_STRING);
		}
		if (assignedFlight != null) {
			isFlightClosedForOperations = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(assignedFlight.getExportClosingFlag());
			log.debug("" + "The Flight Status is found to be " + " " + isFlightClosedForOperations);
		}
		return isFlightClosedForOperations;
	}

	/** 
	* @param operationalFlightVO
	* @return boolean
	* @throws SystemException
	* @author a-1883
	*/
	public boolean isFlightClosedForInboundOperations(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "isFlightClosedForInboundOperations" + " Entering");
		boolean isFlightClosedForInbound = false;
		AssignedFlight inboundFlight = null;
		AssignedFlightPK inboundFlightPk = new AssignedFlightPK();
		if (operationalFlightVO.isForUCMSendChk()) {
			inboundFlightPk.setAirportCode(operationalFlightVO.getAirportCode());
		} else {
			inboundFlightPk.setAirportCode(operationalFlightVO.getPou());
		}
		inboundFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		inboundFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		inboundFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		inboundFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		inboundFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPk);
		} catch (FinderException ex) {
			log.info(FINDEREXCEPTIO_STRING);
		}
		if (inboundFlight != null) {
			isFlightClosedForInbound = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(inboundFlight.getImportClosingFlag());
			log.debug("" + "The Flight Status is found to be " + " " + isFlightClosedForInbound);
		}
		log.debug(CLASS + " : " + "isFlightClosedForInboundOperations" + " Exiting");
		return isFlightClosedForInbound;
	}

	/** 
	* @param companyCode
	* @return
	* @throws SystemException
	* @author A-2037 This method is used to find the History of a Mailbag
	*/
	public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		log.debug(CLASS + " : " + "findMailbagHistories" + " Entering");
		return Mailbag.findMailbagHistories(companyCode, mailBagId, mailSequenceNumber);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) {
		log.debug(CLASS + " : " + "findPAForOfficeOfExchange" + " Entering");
		MailTrackingDefaultsBI mailMasterApi = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
		String paCode = mailMasterApi.findPAForOfficeOfExchange(companyCode, officeOfExchange);

		return paCode;
	}

	/** 
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	* @author a-2037 This method is used to find all the mail subclass codes
	*/
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode) {
		log.debug(CLASS + " : " + "findMailSubClassCodes" + " Entering");
		//TODO: Neo to correct- route to mail master service
		//return MailSubClass.findMailSubClassCodes(companyCode, subclassCode);
		return null;
	}

	/** 
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @author A-3227This method returns Collection<ArrayList<String>> in which, the inner collection contains the values in the order : 1.OFFICE OF EXCHANGE 2.CITY 3.NEAREST AIRPORT
	*/
	public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,
			Collection<String> officeOfExchanges) {
		log.debug(CLASS + " : " + "findCityForOfficeOfExchange" + " Entering");
		HashMap<String, String> cityForOE = null;
		Map<String, CityVO> cityAirtportMap = null;
		Collection<String> cityCodes = new ArrayList<String>();
		ArrayList<String> groupedCodes = new ArrayList<String>();
		Collection<ArrayList<String>> groupedOECityArpCodes = new ArrayList<ArrayList<String>>();
		if (officeOfExchanges != null && officeOfExchanges.size() > 0) {
			cityForOE = findCityForOfficeOfExchange(companyCode, officeOfExchanges);
			if (cityForOE != null && cityForOE.size() > 0) {
				for (String oe : officeOfExchanges) {
					cityCodes.add(cityForOE.get(oe));
				}
				if (cityCodes != null && cityCodes.size() > 0) {
					cityAirtportMap = findCityAirportMap(companyCode, cityCodes);
				}
			}
		}
		if (cityForOE != null && cityForOE.size() > 0 && cityAirtportMap != null && cityAirtportMap.size() > 0) {
			for (String oe : officeOfExchanges) {
				String city = cityForOE.get(oe);
				CityVO cityVO = cityAirtportMap.get(city);
				groupedCodes = new ArrayList<String>();
				groupedCodes.add(oe);
				groupedCodes.add(city);
				groupedCodes.add(cityVO.getNearestAirport());
				groupedOECityArpCodes.add(groupedCodes);
			}
		}
		return groupedOECityArpCodes;
	}

	public boolean checkIfPartnerCarrier(String airportCode, String carrierCode) {
		boolean isPartnerCarrier = true;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<PartnerCarrierVO> partnerCarrierVOs = null;
		ArrayList<String> partnerCarriers = new ArrayList<String>();
		String companyCode = logonAttributes.getCompanyCode();
		String ownCarrierCode = logonAttributes.getOwnAirlineCode();
		partnerCarrierVOs = findAllPartnerCarriers(companyCode, ownCarrierCode, airportCode);
		log.info("" + "partnerCarrierVOs-----------------" + " " + partnerCarrierVOs);
		if (partnerCarrierVOs != null) {
			for (PartnerCarrierVO partner : partnerCarrierVOs) {
				String partnerCarrier = partner.getPartnerCarrierCode();
				partnerCarriers.add(partnerCarrier);
			}
			partnerCarriers.add(ownCarrierCode);
			if (!(partnerCarriers.contains(carrierCode))) {
				isPartnerCarrier = false;
			}
		}
		return isPartnerCarrier;
	}

	/** 
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	private Map<String, CityVO> findCityAirportMap(String companyCode, Collection<String> cities) {
		Collection<String> cityCodes = new ArrayList<String>();
		Map<String, CityVO> cityMap = null;
		for (String cityCode : cities) {
			if (!cityCodes.contains(cityCode)) {
				cityCodes.add(cityCode);
			}
		}
		log.debug("" + "CityCodes->" + " " + cityCodes);
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		try {
			cityMap = sharedAreaProxy.validateCityCodes(companyCode, cityCodes);
		} catch (SharedProxyException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		log.debug("" + "CityMap-->" + " " + cityMap);
		return cityMap;
	}

	/** 
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @author a-1876 This method is used to list the PartnerCarriers..
	*/
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
															   String airportCode) {
		log.debug(CLASS + " : " + "findAllPartnerCarriers" + " Entering");

		return mailOperationsMapper.partnerCarrierModelstoPartnerCarrierVos(mailMasterApi.findAllPartnerCarriers(companyCode,ownCarrierCode,airportCode));
	}

	/** 
	* @param mailInConsignmentVO
	* @return
	* @throws SystemException
	* @author a-1883
	*/
	private Mailbag findMailBag(MailInConsignmentVO mailInConsignmentVO) {
		log.debug(CLASS + " : " + "findMailBag" + " Entering");
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
	* @param mailbagVos
	* @return
	* @throws SystemException
	* @throws InvalidMailTagFormatException
	* @author a-1936 This metrhod is used to validate the mailBag and theMailTagFormat
	*/
	public boolean validateMailBags(Collection<MailbagVO> mailbagVos) throws InvalidMailTagFormatException {
		log.debug(CLASS + " : " + "validateMailBags" + " Entering");
		return new Mailbag().validateMailBags(mailbagVos, false);
	}

	/** 
	* This method updates mailbag consignment details (that is if this mail bag is already accepted)
	* @param mailInConsignmentVO
	* @throws SystemException
	* @throws DuplicateMailBagsException
	* @author a-1883
	*/
	public void updateMailBagConsignmentDetails(MailInConsignmentVO mailInConsignmentVO,
			Collection<RoutingInConsignmentVO> routingInConsignments, Map<String, Long> flightSeqNumMap)
			throws DuplicateMailBagsException {
		log.debug(CLASS + " : " + "updateMailBagConsignmentDetails" + " Entering");
		Mailbag mailbag = findMailBag(mailInConsignmentVO);
		boolean isDuplicate = false;
		LoginProfile logonVO = contextUtil.callerLoginProfile();
		if (mailbag != null) {
			isDuplicate = checkForDuplicateMailbag(mailInConsignmentVO.getCompanyCode(),
					mailInConsignmentVO.getPaCode(), mailbag);
			if (!isDuplicate) {
				mailbag.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
				mailbag.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
				mailbag.setPaCode(mailInConsignmentVO.getPaCode());
			}
			mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
		}
		if (mailbag == null || isDuplicate) {
			if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
				if (mailInConsignmentVO.getReceptacleSerialNumber() != null) {
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
					mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
					mailbagVO.setDoe(mailInConsignmentVO.getDestinationExchangeOffice());
					mailbagVO.setOoe(mailInConsignmentVO.getOriginExchangeOffice());
					mailbagVO.setMailSubclass(mailInConsignmentVO.getMailSubclass());
					mailbagVO.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
					mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
					mailbagVO.setDespatchSerialNumber(mailInConsignmentVO.getDsn());
					mailbagVO.setMailClass(mailInConsignmentVO.getMailClass());
					mailbagVO.setYear(mailInConsignmentVO.getYear());
					mailbagVO.setUldNumber(mailInConsignmentVO.getUldNumber());
					mailbagVO.setHighestNumberedReceptacle(mailInConsignmentVO.getHighestNumberedReceptacle());
					mailbagVO.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
					mailbagVO.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getRegisteredOrInsuredIndicator());
					mailbagVO.setScannedPort(mailInConsignmentVO.getAirportCode());
					mailbagVO.setScannedDate(mailInConsignmentVO.getConsignmentDate());
					mailbagVO.setCarrierId(mailInConsignmentVO.getCarrierId());
					mailbagVO.setWeight(mailInConsignmentVO.getStatedWeight());
					mailbagVO.setConsignmentDate(mailInConsignmentVO.getConsignmentDate());
					mailbagVO.setDespatchId(createDespatchBag(mailInConsignmentVO));
					if (mailbagVO.getWeight().getDisplayValue().doubleValue() > 0) {
						double actualVolume = 0.0;
						double stationVolume = 0.0;
						String commodityCode = "";
						String stationVolumeUnit = "";
						CommodityValidationVO commodityValidationVO = null;
						try {
							commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
							commodityValidationVO = validateCommodity(mailbagVO.getCompanyCode(), commodityCode,
									mailInConsignmentVO.getPaCode());
						} finally {
						}
						if (commodityValidationVO != null && commodityValidationVO.getDensityFactor() != 0) {
							Map stationParameters = null;
							String stationCode = logonVO.getStationCode();
							Collection<String> parameterCodes = new ArrayList<String>();
							parameterCodes.add(STNPAR_DEFUNIT_VOL);
							stationParameters = sharedAreaProxy.findStationParametersByCode(logonVO.getCompanyCode(),
									stationCode, parameterCodes);
							stationVolumeUnit = (String) stationParameters.get(STNPAR_DEFUNIT_VOL);
							if (mailInConsignmentVO.getStatedWeight() != null) {
								//TODO: Neo to verify below code- change as part of compilation in NEO
								double weightInKg = new DocumentController().unitConvertion(UnitConstants.MAIL_WGT,
										mailInConsignmentVO.getStatedWeight().getUnit().getName(),
										UnitConstants.WEIGHT_UNIT_KILOGRAM,
										mailInConsignmentVO.getStatedWeight().getValue().doubleValue());
								actualVolume = (weightInKg / (commodityValidationVO.getDensityFactor()));
								stationVolume = new DocumentController().unitConvertion(UnitConstants.VOLUME,
										UnitConstants.VOLUME_UNIT_CUBIC_METERS, stationVolumeUnit, actualVolume);
								log.info("" + "inside volume calculation for mailbags***:>>>" + " " + actualVolume);
								if (stationVolume < 0.01) {
									stationVolume = 0.01;
								}
							}
						}
						if (stationVolumeUnit != null) {
							mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME,
									BigDecimal.valueOf(stationVolume), BigDecimal.valueOf(0.0), stationVolumeUnit));
						}
					}
					mailbagVO.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
					mailbagVO.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
					mailbagVO.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
					mailbagVO.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
					mailbagVO.setPaCode(mailInConsignmentVO.getPaCode());
					mailbagVO.setLatestStatus("NEW");
					String destination = null;
					try {
						destination = constructDAO().findCityForOfficeOfExchange(mailInConsignmentVO.getCompanyCode(),
								mailInConsignmentVO.getDestinationExchangeOffice());
					} catch (PersistenceException e) {
						e.printStackTrace();
					}
					mailbagVO.setMailbagSource(mailInConsignmentVO.getMailSource());
					mailbagVO.setMailbagDataSource(mailInConsignmentVO.getMailSource());
					mailbagVO.setContractIDNumber(mailInConsignmentVO.getContractIDNumber());
					mailbagVO.setTransWindowEndTime(mailInConsignmentVO.getTransWindowEndTime());
					mailbagVO.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
					if (routingInConsignments != null && routingInConsignments.size() > 0) {
						for (RoutingInConsignmentVO routingVO : routingInConsignments) {
							if (routingVO.getPou().equals(destination)) {
								mailbagVO.setFlightNumber(routingVO.getOnwardFlightNumber());
								mailbagVO.setFlightDate(routingVO.getOnwardFlightDate());
								if (flightSeqNumMap != null && !flightSeqNumMap.isEmpty()
										&& flightSeqNumMap.containsKey(destination)) {
									mailbagVO.setFlightSequenceNumber(flightSeqNumMap.get(destination));
								} else {
									FlightFilterVO flightFilterVO = new FlightFilterVO();
									flightFilterVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
									flightFilterVO.setFlightNumber(routingVO.getOnwardFlightNumber().toUpperCase());
									flightFilterVO.setStation(routingVO.getPol());
									flightFilterVO.setDirection(mailInConsignmentVO.getOperation());
									flightFilterVO.setActiveAlone(false);
									//TODO: Neo to correct below code
									flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(routingVO.getOnwardFlightDate()));
									Collection<FlightValidationVO> flightValidationVOs = null;
									flightValidationVOs = validateFlight(flightFilterVO);
									if (flightValidationVOs != null && flightValidationVOs.size() == 1) {
										for (FlightValidationVO vo : flightValidationVOs) {
											mailbagVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
											flightSeqNumMap.put(destination, vo.getFlightSequenceNumber());
										}
									} else {
										flightSeqNumMap.put(destination, 0L);
									}
								}
								break;
							} else {
								if (routingInConsignments != null && routingInConsignments.size() > 0) {
									RoutingInConsignmentVO routingInConsignmentVO = routingInConsignments.iterator()
											.next();
									mailbagVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
									mailbagVO.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
									mailbagVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
									mailbagVO.setFlightSequenceNumber(routingInConsignmentVO.getOnwardCarrierSeqNum());
									mailbagVO.setPou(routingInConsignmentVO.getPou());
								} else {
									mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
									mailbagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
								}
							}
						}
					}
					mailbagVO = constructOriginDestinationDetailsForConsignment(mailInConsignmentVO, mailbagVO);
					if (mailInConsignmentVO.getMailServiceLevel() != null
							&& !mailInConsignmentVO.getMailServiceLevel().isEmpty()) {
						mailbagVO.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
					}
					String scnaWaved = constructDAO().checkScanningWavedDest(mailbagVO);
					if (scnaWaved != null) {
						mailbagVO.setScanningWavedFlag(scnaWaved);
					}
					if (isUSPSMailbag(mailbagVO)) {
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
					} else {
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
					}
					calculateAndUpdateLatestAcceptanceTime(mailbagVO);
					mailbag = new Mailbag(mailbagVO);
					if (mailbag != null) {
						mailInConsignmentVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
					}
				}
			}
		}
		log.debug(CLASS + " : " + "updateMailBagConsignmentDetails" + " Exiting");
	}

	/** 
	* This method checks whether mail already accepted or not and returns accepted mailbag details A-1883
	* @param mailInConsignmentVOs
	* @return
	* @throws SystemException
	*/
	public Collection<Mailbag> checkMailbagAccepted(Collection<MailInConsignmentVO> mailInConsignmentVOs) {
		log.debug(CLASS + " : " + "checkMailbagAccepted" + " Entering");
		Collection<Mailbag> mailbags = null;
		Mailbag mailbag = null;
		MailbagPK mailbagPk = null;
		for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
			if (MailInConsignmentVO.OPERATION_FLAG_DELETE.equals(mailInConsignmentVO.getOperationFlag())) {
				mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(
						findMailSequenceNumber(mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
				mailbag = findAcceptedMailbag(mailbagPk);
				if (mailbags == null) {
					mailbags = new ArrayList<Mailbag>();
				}
				if (mailbag != null) {
					mailbags.add(mailbag);
				}
			}
		}
		log.debug(CLASS + " : " + "checkMailbagAccepted" + " Exiting");
		return mailbags;
	}

	/** 
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @author A-2037 This method is used to find Local PAs
	*/
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode) {
		log.debug(CLASS + " : " + "findLocalPAs" + " Entering");
		//TODO: Neo to correct below code
		//return PostalAdministration.findLocalPAs(companyCode, countryCode);
		return null;
	}

	/** 
	* @param mailbagPk
	* @return Mailbag
	* @throws SystemException
	* @author a-1883
	*/
	private Mailbag findAcceptedMailbag(MailbagPK mailbagPk) {
		log.debug(CLASS + " : " + "findAcceptedMailbag" + " Entering");
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
			log.debug(" Mail is accepted");
		} catch (FinderException finderException) {
			log.debug(" Mail not accepted");
		}
		log.debug(CLASS + " : " + "findAcceptedMailbag" + " Exiting");
		return mailbag;
	}

	/** 
	* @param mailIdr
	* @param companyCode
	* @return
	* @throws SystemException
	* @author A-5991
	*/
	public long findMailSequenceNumber(String mailIdr, String companyCode) {
		return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
	}

	/** 
	* Method to validate mailbags are assigned in the same container
	* @param mailbagVOs
	* @param toContainerVO
	* @throws ReassignmentException
	* @throws SystemException
	*/
	private void validateMailbagVOs(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO)
			throws ReassignmentException {
		int errorFlag = 0;
		String errorBags = "";
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag mailbag = null;
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
					: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			try {
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException e) {
				log.error("Finder Exception Caught");
				throw new ReassignmentException("mailtracking.defaults.noresultsfound");
			}
			if ((mailbag.getCarrierId() == toContainerVO.getCarrierId()
					&& (mailbag.getFlightNumber() != null
							&& mailbag.getFlightNumber().equals(toContainerVO.getFlightNumber()))
					&& mailbag.getFlightSequenceNumber() == toContainerVO.getFlightSequenceNumber()
					&& mailbag.getSegmentSerialNumber() == toContainerVO.getSegmentSerialNumber()
					&& (mailbag.getUldNumber() != null
							&& mailbag.getUldNumber().equals(toContainerVO.getContainerNumber())))
					&& !toContainerVO.isContainerDestChanged()) {
				errorFlag = 1;
				if ("".equals(errorBags)) {
					errorBags = mailbagVO.getMailbagId();
				} else {
					errorBags = new StringBuilder(errorBags).append(",").append(mailbagVO.getMailbagId()).toString();
				}
			}
			if (mailbag != null && mailbag.getFlightSequenceNumber() < 0
					&& MailConstantsVO.BULK_TYPE.equals(mailbag.getContainerType())) {
				ContainerAssignmentVO containerAssignmentVO = null;
				ContainerVO containerVO = new ContainerVO();
				containerVO.setCompanyCode(mailbagVO.getCompanyCode());
				containerVO.setContainerNumber(mailbagVO.getContainerNumber());
				containerVO.setAssignedPort(mailbagVO.getScannedPort());
				containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
				if (containerAssignmentVO != null) {
					mailbagVO.setFinalDestination(containerAssignmentVO.getDestination());
				}
			}
			mailbagVO.setCarrierId(mailbag.getCarrierId());
			if (mailbag.getFlightNumber() != null) {
				mailbagVO.setFlightNumber(mailbag.getFlightNumber());
			}
			mailbagVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
			mailbagVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
			mailbagVO.setContainerNumber(mailbag.getUldNumber());
		}
		if (errorFlag == 1) {
			throw new ReassignmentException("mailtracking.defaults.reassignmail.reassignsamecontainer",
					new Object[] { errorBags, toContainerVO.getContainerNumber() });
		}
	}

	/** 
	* A-1739
	* @return
	*/
	private OperationalFlightVO constructOpFlightVOFromContainer(ContainerVO containerVo) {
		OperationalFlightVO opFlightVO = new OperationalFlightVO();
		opFlightVO.setCompanyCode(containerVo.getCompanyCode());
		opFlightVO.setCarrierId(containerVo.getCarrierId());
		opFlightVO.setFlightNumber(containerVo.getFlightNumber());
		opFlightVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		opFlightVO.setFlightDate(containerVo.getFlightDate());
		opFlightVO.setPol(containerVo.getAssignedPort());
		opFlightVO.setCarrierCode(containerVo.getCarrierCode());
		opFlightVO.setLegSerialNumber(containerVo.getLegSerialNumber());
		log.debug("" + "THE OPERATIONAL FLIGHT VO FROM MAIL BAG" + " " + opFlightVO);
		return opFlightVO;
	}

	/** 
	* @param mailbagVOs
	* @param mailbagsToReassign
	* @param mailbagsToTsfr
	* @throws SystemException
	* @author A-1739
	*/
	private void groupMailbagsForReassignMailBags(Collection<MailbagVO> mailbagVOs,
			Collection<MailbagVO> mailbagsToReassign, Collection<MailbagVO> mailbagsToTsfr) {
		log.debug("ReassignController" + " : " + "groupMailbagsForReassignOrTsfr" + " Entering");
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mailbagsToReassign.add(mailbagVO);
			} else {
				MailbagVO histMailbagVO = MailbagHistory.findArrivalDetailsForMailbag(mailbagVO);
				log.debug("" + "THE HISTORY MAIL BAG VO" + " " + histMailbagVO);
				if (histMailbagVO == null) {
					mailbagsToReassign.add(mailbagVO);
				} else if (MailbagVO.FLAG_YES.equals(histMailbagVO.getArrivedFlag())
						&& !(MailbagVO.FLAG_YES.equals(histMailbagVO.getDeliveredFlag())
								|| MailbagVO.FLAG_YES.equals(histMailbagVO.getDeliveredFlag()))) {
					MailbagVO mailbagToTsfr = new MailbagVO();
					mailbagToTsfr = mailOperationsMapper.copyMailbagVO(histMailbagVO);
					mailbagToTsfr.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagToTsfr.setScannedDate(localDateUtil.getLocalDate(
							mailbagVO.getScannedPort(), false));
					mailbagToTsfr.setScannedUser(mailbagVO.getScannedUser());
					mailbagToTsfr.setInventoryContainer(mailbagVO.getContainerNumber());
					mailbagToTsfr.setInventoryContainerType(mailbagVO.getContainerType());
					mailbagsToTsfr.add(mailbagToTsfr);
				}
			}
		}
		log.debug("ReassignController" + " : " + "groupMailbagsForReassignOrTsfr" + " Exiting");
	}

	/** 
	* This method is used to reassign the MailBags .. The Reassignmemts Possible are 1.Reassign From Flight To Destination 2.Reassign From Destination To Destination 3.Reassign From Flight To Flight 4.Reassign From Destination To Flight This method also returns any ULDs which becomes empty if all the mailbags in it are moved to another ULD. This is done so that the user can choose to unassign it. At the same time all Barrows which become empty are deassigned automatically by the System. A-1739
	* @param toContainerVO
	* @return the ULDs to be unassigned
	* @throws SystemException
	* @throws FlightClosedException
	* @throws ReassignmentException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @author A-1936 Modified To make the Methods work in a generic manner
	*/
	public Collection<ContainerDetailsVO> reassignMailbags(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO)
			throws FlightClosedException, ReassignmentException, InvalidFlightSegmentException,
			CapacityBookingProxyException, MailBookingException {
		Collection<MailbagVO> mailbagsToReassign = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagsToTsfr = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> conDetVO = null;
		validateMailbagVOs(mailbagVOs, toContainerVO);
		if (toContainerVO.getFlightSequenceNumber() > 0) {
			OperationalFlightVO toFlightVo = constructOpFlightVOFromContainer(toContainerVO);
			log.debug("" + "THE  TO  FLIGHT " + " " + toFlightVo);
			if (toFlightVo.getFlightSequenceNumber() > 0 && toFlightVo.getLegSerialNumber() > 0
					&& isFlightClosedForOperations(toFlightVo)) {
				log.debug("THROW EXCEPTION FOR THE TO FLIGHT");
				throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED,
						constructFltErrorData(toFlightVo));
			}
			groupMailbagsForReassignMailBags(mailbagVOs, mailbagsToReassign, mailbagsToTsfr);
			log.debug("" + "maiblags to transfer " + " " + mailbagsToTsfr);
			log.debug("" + "maiblags to reassign " + " " + mailbagsToReassign);
		} else {
			mailbagsToReassign = mailbagVOs;
		}
		if (mailbagsToReassign != null && mailbagsToReassign.size() > 0) {
			try {
				conDetVO = reassignController.reassignMailbags(mailbagsToReassign, toContainerVO);
				String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagMLDForMailReassignOperations(mailbagsToReassign, toContainerVO, "ALL");
				}
			} finally {
			}
		}
		if (mailbagsToTsfr != null && mailbagsToTsfr.size() > 0) {
			mailTransfer.transferMailbags(mailbagsToTsfr, toContainerVO);
		}
		return conDetVO;
	}

	private MailbagVO populateMailPKFields(MailbagVO mailbagVO) {
		log.debug("UploadMailDetailsCommand" + " : " + "populateMailPKFields" + " Entering");
		String mailBagId = mailbagVO.getMailbagId();
		if (mailBagId != null && mailBagId.trim().length() > 0) {
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
			double displayStrWt = Double.parseDouble(mailBagId.substring(25, 29)) / 10;
			Quantity strWt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(displayStrWt));
			mailbagVO.setWeight(strWt);
			mailbagVO.setStrWeight(strWt);
		}
		log.debug("UploadMailDetailsCommand" + " : " + "populateMailPKFields" + " Exiting");
		return mailbagVO;
	}

	/** 
	* saveScannedOffloadMails
	* @throws MailBookingException
	* @throws CapacityBookingProxyException
	* @throws ULDDefaultsProxyException
	* @throws ReassignmentException
	* @throws FlightDepartedException
	* @throws FlightClosedException
	*/
	public void saveScannedOffloadMails(Collection<OffloadVO> OffloadVosForSave)
			throws FlightClosedException, FlightDepartedException, ReassignmentException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException {
		log.debug(CLASS + " : " + "saveScannedOffloadMails" + " Entering");
		if (OffloadVosForSave != null && OffloadVosForSave.size() > 0) {
			for (OffloadVO offloadVOForSave : OffloadVosForSave) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.offload(offloadVOForSave);
			}
		}
		log.debug(CLASS + " : " + "saveScannedOffloadMails" + " Exiting");
	}

	/** 
	* @param offloadVo
	* @return
	* @throws SystemException
	* @throws FlightClosedException
	* @throws FlightDepartedException
	* @throws ReassignmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @author
	*/
	public Collection<ContainerDetailsVO> offload(OffloadVO offloadVo)
			throws FlightClosedException, FlightDepartedException, ReassignmentException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException {
		Collection<ContainerDetailsVO> emptyULDs = new ArrayList<ContainerDetailsVO>();
		OperationalFlightVO opFlightVO = createOperationalFlightVO(offloadVo);
		if (!offloadVo.isFltClosureChkNotReq() && !isFlightClosedForOperations(opFlightVO)) {
			throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED,
					new Object[] {
							new StringBuilder(offloadVo.getCarrierCode()).append(SPACE)
									.append(offloadVo.getFlightNumber()).toString(),
							offloadVo.getFlightDate().toString().substring(0, 11) });
		}
		if (!offloadVo.isDepartureOverride()) {
			FlightValidationVO flightValidationVO = validateOperationalFlight(opFlightVO, false);
			if (flightValidationVO != null) {
				if (flightValidationVO.getAtd() != null) {
					throw new FlightDepartedException(constructFltErrorData(opFlightVO));
				}
			}
		}
		try {
			if (MailConstantsVO.OFFLOAD_CONTAINER.equals(offloadVo.getOffloadType())) {
				offloadContainers(offloadVo);
			} else if (MailConstantsVO.OFFLOAD_MAILBAG.equals(offloadVo.getOffloadType())) {
				emptyULDs = offloadMails(offloadVo);
			} else if (MailConstantsVO.OFFLOAD_DSN.equals(offloadVo.getOffloadType())) {
				emptyULDs = offloadDSNs(offloadVo);
			}
		} finally {
		}
		return emptyULDs;
	}

	/** 
	* @param offloadVo
	* @return
	* @author This method is used to create the OperationalFlightVO tocheck isflightClosed
	*/
	private OperationalFlightVO createOperationalFlightVO(OffloadVO offloadVo) {
		log.debug(CLASS + " : " + "createOperationalFlightVO" + " Entering");
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode(offloadVo.getCompanyCode());
		operationalFlightVo.setCarrierId(offloadVo.getCarrierId());
		operationalFlightVo.setFlightNumber(offloadVo.getFlightNumber());
		operationalFlightVo.setFlightSequenceNumber(offloadVo.getFlightSequenceNumber());
		operationalFlightVo.setPol(offloadVo.getPol());
		operationalFlightVo.setLegSerialNumber(offloadVo.getLegSerialNumber());
		operationalFlightVo.setFlightDate(offloadVo.getFlightDate());
		operationalFlightVo.setCarrierCode(offloadVo.getCarrierCode());
		return operationalFlightVo;
	}

	public FlightValidationVO validateOperationalFlight(OperationalFlightVO opFlightVO, boolean isInbound) {
		log.debug(CLASS + " : " + "validateOperationalFlight" + " Entering");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(opFlightVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(opFlightVO.getCarrierId());
		flightFilterVO.setFlightNumber(opFlightVO.getFlightNumber());
		if (opFlightVO.getFlightDate() != null) {
			//TODO: To correct in Neo
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(opFlightVO.getFlightDate()));
		}
		if (opFlightVO.getFlightSequenceNumber() > 0) {
			flightFilterVO.setFlightSequenceNumber(opFlightVO.getFlightSequenceNumber());
		}
		if (opFlightVO.getFlightDate() == null && opFlightVO.getFlightSequenceNumber() <= 0) {
			return null;
		}
		if (isInbound) {
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
			flightFilterVO.setStation(opFlightVO.getPou());
		} else {
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
			flightFilterVO.setStation(opFlightVO.getPol());
		}
		Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
		FlightValidationVO toReturnVO = null;
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == opFlightVO.getFlightSequenceNumber()) {
					toReturnVO = flightValidationVO;
				}
			}
		}
		log.debug(CLASS + " : " + "validateOperationalFlight" + " Exiting");
		log.debug("" + "THE FLIGHT FILTER VO IS " + " " + flightFilterVO);
		return toReturnVO;
	}

	/** 
	* @param operationalFlightVO
	* @return
	*/
	private Object[] constructFltErrorData(OperationalFlightVO operationalFlightVO) {
		log.debug("" + SPACE + " " + operationalFlightVO);
		//TODO: TO correct below code- refer classic
		return new String[] {
				new StringBuilder().append(operationalFlightVO.getCarrierCode()).append(SPACE)
						.append(operationalFlightVO.getFlightNumber()).toString(),
				operationalFlightVO.getFlightDate().toString() };
	}

	/** 
	* This method is used to offload the Containers A-1936
	* @param offloadVo
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	private void offloadContainers(OffloadVO offloadVo)
			throws ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException {
		log.debug("Inside the OffloadContainers" + " : " + "offloadContainers" + " Entering");
		Collection<String> mailIds = null;
		boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
		Collection<ContainerDetailsVO> shipperBuiltContainersForResdit = null;
		Collection<ContainerVO> shipperBuiltContainers = null;
		OperationalFlightVO operationalFlightVo = null;
		if (offloadVo.getOffloadContainers() != null && offloadVo.getOffloadContainers().size() > 0) {
			Collection<ContainerVO> containerVos = new ArrayList<ContainerVO>(offloadVo.getOffloadContainers());
			for (ContainerVO containerVo : containerVos) {
				containerVo.setFlightDate(offloadVo.getFlightDate());
				log.debug("" + "The Flight Date in Offload Containers" + " " + containerVo.getFlightDate());
				if (containerVo.getCarrierId() == 0) {
					containerVo.setCarrierId(offloadVo.getCarrierId());
				}
				if (ContainerVO.FLAG_YES.equals(containerVo.getPaBuiltFlag())
						&& MailConstantsVO.ULD_TYPE.equals(containerVo.getType())) {
					if (shipperBuiltContainers == null) {
						shipperBuiltContainers = new ArrayList<ContainerVO>();
					}
					shipperBuiltContainers.add(containerVo);
				}
				if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.INVENTORY_ENABLED_FLAG))) {
					containerVo.setFinalDestination(null);
				}
			}
			operationalFlightVo = constructFlightVoForOffload(offloadVo);
			if (isMonitorSLAEnabled) {
				mailIds = new ArrayList<String>();
			}
			ContextUtil.getInstance().getBean(ReassignController.class).reassignContainerFromFlightToDest(containerVos, operationalFlightVo, mailIds);
			createOffloadDetails(containerVos, offloadVo);
			shipperBuiltContainersForResdit = constructConDetailsVOsForResdit(shipperBuiltContainers);
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			if (!offloadVo.isRemove()) {
				mailController.flagPendingResditForUlds(shipperBuiltContainersForResdit, offloadVo.getPol());
			}
			if (mailIds != null && mailIds.size() > 0) {
				log.debug("" + "The Mail IDS " + " " + mailIds.size());
				log.debug("" + "The Mail IDS " + " " + mailIds);
				monitorMailSLAActivity(createMonitorSLAVosForOffloadContainer(mailIds, offloadVo.getCompanyCode()));
			}
		}
		log.debug("OffloadContainers" + " : " + "offloadContainers" + " Exiting");
	}

	/** 
	* @param offloadVo
	* @return
	* @author This method is used to construct theconstructFlightVoForOffload
	*/
	private OperationalFlightVO constructFlightVoForOffload(OffloadVO offloadVo) {
		log.debug(CLASS + " : " + "constructFlightVoForOffload" + " Entering");
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode(offloadVo.getCompanyCode());
		operationalFlightVo.setCarrierId(offloadVo.getCarrierId());
		operationalFlightVo.setPol(offloadVo.getPol());
		operationalFlightVo.setCarrierCode(offloadVo.getCarrierCode());
		operationalFlightVo.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		operationalFlightVo.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		operationalFlightVo.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		log.debug(CLASS + " : " + "constructFlightVoForOffload" + " Exiting");
		return operationalFlightVo;
	}

	/** 
	* This method is used to construct the ContainerDetailsVo from the ContainerVo A-1936
	* @param containerVos
	* @return
	*/
	private Collection<ContainerDetailsVO> constructConDetailsVOsForResdit(Collection<ContainerVO> containerVos) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		if (containerVos != null && containerVos.size() > 0) {
			containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
			for (ContainerVO containerVo : containerVos) {
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(containerVo.getCompanyCode());
				containerDetailsVO.setPol(containerVo.getAssignedPort());
				containerDetailsVO.setContainerNumber(containerVo.getContainerNumber());
				containerDetailsVO.setCarrierCode(containerVo.getCarrierCode());
				containerDetailsVO.setCarrierId(containerVo.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVo.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
				containerDetailsVO.setOwnAirlineCode(containerVo.getOwnAirlineCode());
				containerDetailsVO.setContainerJnyId(containerVo.getContainerJnyID());
				if (containerVo.getShipperBuiltCode() != null) {
					containerDetailsVO.setPaCode(containerVo.getShipperBuiltCode());
				}
				containerDetailsVOs.add(containerDetailsVO);
			}
		}
		return containerDetailsVOs;
	}

	private void createOffloadDetails(Collection<ContainerVO> containerVos, OffloadVO offloadVo) {
		for (ContainerVO containerVO : containerVos) {
			OffloadDetailVO offloadDetailVo = new OffloadDetailVO();
			offloadDetailVo.setCompanyCode(containerVO.getCompanyCode());
			offloadDetailVo.setContainerNumber(containerVO.getContainerNumber());
			offloadDetailVo.setFlightNumber(containerVO.getFlightNumber());
			offloadDetailVo.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			offloadDetailVo.setAirportCode(containerVO.getAssignedPort());
			offloadDetailVo.setCarrierId(containerVO.getCarrierId());
			offloadDetailVo.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			offloadDetailVo.setOffloadedBags(containerVO.getBags());
			offloadDetailVo.setOffloadedWeight(containerVO.getWeight());
			offloadDetailVo.setCarrierCode(offloadVo.getCarrierCode());
			offloadDetailVo.setOffloadType(MailConstantsVO.OFFLOADTYPE_FULL);
			offloadDetailVo.setOffloadReasonCode(containerVO.getOffloadedReason());
			offloadDetailVo.setOffloadRemarks(containerVO.getOffloadedRemarks());
			offloadDetailVo.setOffloadUser(containerVO.getLastUpdateUser());
			offloadDetailVo.setOffloadDescription(containerVO.getOffloadedDescription());
			new MailOffloadDetail(offloadDetailVo);
		}
	}

	/** 
	* 120507
	* @param mailBagsForMonitorSLA
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to construct the MonitorSLAVos Fromthe MailBagVos
	*/
	private Collection<MonitorMailSLAVO> createMonitorSLAVosForOffloadContainer(
			Collection<String> mailBagsForMonitorSLA, String companyCode) {
		log.debug(CLASS + " : " + "createMonitorSLAVosForOffloadContainer" + " Entering");
		MonitorMailSLAVO monitorSLAVo = null;
		Collection<MonitorMailSLAVO> monitorSLAVos = new ArrayList<MonitorMailSLAVO>();
		for (String mailBagForMonitorSLA : mailBagsForMonitorSLA) {
			monitorSLAVo = new MonitorMailSLAVO();
			monitorSLAVo.setCompanyCode(companyCode);
			monitorSLAVo.setActivity(MonitorMailSLAVO.MAILSTATUS_OFFLOADED);
			monitorSLAVo.setMailBagNumber(mailBagForMonitorSLA);
			monitorSLAVo.setOperationFlag(MonitorMailSLAVO.OPERATION_FLAG_UPDATE);
			monitorSLAVos.add(monitorSLAVo);
		}
		log.debug(CLASS + " : " + "createMonitorSLAVosForOffloadContainer" + " Entering");
		return monitorSLAVos;
	}

	/** 
	* @param offloadVo
	* @return
	* @throws SystemException
	* @throws ReassignmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @author a-1936 This method is used to offload the mailBags.
	*/
	private Collection<ContainerDetailsVO> offloadMails(OffloadVO offloadVo)
			throws ReassignmentException, CapacityBookingProxyException, MailBookingException {
		log.info("OFFLOAD MAILBAGS");
		boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
		log.debug("" + "ISMONITORSLAENABLED " + " " + isMonitorSLAEnabled);
		ZonedDateTime dateOfOffload = null;
		Map<String, Collection<MailbagVO>> offloadMap = null;
		Collection<MailbagVO> mailBags = offloadVo.getOffloadMailbags();
		Collection<ContainerDetailsVO> emptyContainers = new ArrayList<ContainerDetailsVO>();
		if (mailBags != null && mailBags.size() > 0) {
			for (MailbagVO mailbagVO : mailBags) {
				if (mailbagVO.getCarrierId() == 0) {
					mailbagVO.setCarrierId(offloadVo.getCarrierId());
				}
				mailbagVO.setTriggerForReImport(MailConstantsVO.MAIL_STATUS_OFFLOADED);
			}
			if (!offloadVo.isRemove()) {
				checkForPaBuiltContainersForMail(mailBags);
			}
			offloadMap = constructMapForOffload(offloadVo);
			if (offloadMap != null && offloadMap.size() > 0) {
				for (String dummyContainer : offloadMap.keySet()) {
					ContainerVO containerVo = createContainerForOffload(dummyContainer, offloadVo.getPol());
					containerVo.setCompanyCode(offloadVo.getCompanyCode());
					containerVo.setCarrierId(offloadVo.getCarrierId());
					containerVo.setAssignedUser(offloadVo.getUserCode());
					containerVo.setAssignedPort(offloadVo.getPol());
					log.debug("" + "THE ASSIGNED PORT IS " + " " + offloadVo.getPol());
					containerVo.setAssignedUser(offloadVo.getUserCode());
					containerVo.setCarrierCode(offloadVo.getCarrierCode());
					for (MailbagVO mailbagVo : offloadMap.get(dummyContainer)) {
						containerVo.setPou(mailbagVo.getPou());
						if (MailConstantsVO.FLAG_YES
								.equals(findSystemParameterValue(MailConstantsVO.INVENTORY_ENABLED_FLAG))) {
							containerVo.setFinalDestination(null);
						} else {
							containerVo.setFinalDestination(mailbagVo.getPou());
						}
						containerVo.setLastUpdateTime(mailbagVo.getLastUpdateTime());
						break;
					}
					updateContainerForOffload(containerVo);
					Collection<MailbagVO> flightAssignedMailbags = offloadMap.get(dummyContainer);
					emptyContainers.addAll(new ReassignController()
							.reassignMailFromFlightToDestination(offloadMap.get(dummyContainer), containerVo, true));
					createOffloadDetailsForMail(offloadVo);
					if (isMonitorSLAEnabled && dateOfOffload == null) {
						dateOfOffload = localDateUtil.getLocalDate(offloadVo.getPol(), true);
					}
				}
				if (isMonitorSLAEnabled && mailBags != null && mailBags.size() > 0) {
					log.debug("" + "The MailBags" + " " + mailBags.size());
					log.debug("" + "The MailBags" + " " + mailBags);
					monitorMailSLAActivity(createMonitorSLAVosForOffloadMail(mailBags, dateOfOffload));
				}
			}
		}
		return emptyContainers;
	}

	private void checkForPaBuiltContainersForMail(Collection<MailbagVO> mailbags) throws ReassignmentException {
		log.debug(CLASS + " : " + "checkForPaBuiltContainers" + " Entering");
		Collection<ContainerPK> containers = groupContainersForMailbags(mailbags);
		try {
			for (ContainerPK containerPK : containers) {
				Container container = Container.find(containerPK);
				if (MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())) {
					throw new ReassignmentException(ReassignmentException.MAILBAG_REASSIGN_FROM_PABUILT);
				}
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		log.debug(CLASS + " : " + "checkForPaBuiltContainers" + " Exiting");
	}

	/** 
	* @param mailbags
	* @return
	*/
	private Collection<ContainerPK> groupContainersForMailbags(Collection<MailbagVO> mailbags) {
		Collection<ContainerPK> containers = new ArrayList<ContainerPK>();
		for (MailbagVO mailbagVO : mailbags) {
			if (MailConstantsVO.OPERATION_OUTBOUND.equals(mailbagVO.getOperationalStatus())) {
				ContainerPK containerPK = contstructContainerPKFromMail(mailbagVO);
				if (!containers.contains(containerPK)) {
					containers.add(containerPK);
				}
			}
		}
		return containers;
	}

	/** 
	* @param mailbagVO
	* @return
	*/
	private ContainerPK contstructContainerPKFromMail(MailbagVO mailbagVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(mailbagVO.getCompanyCode());
		containerPK.setCarrierId(mailbagVO.getCarrierId());
		containerPK.setFlightNumber(mailbagVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(mailbagVO.getLegSerialNumber());
		containerPK.setAssignmentPort(mailbagVO.getScannedPort());
		containerPK.setContainerNumber(mailbagVO.getContainerNumber());
		return containerPK;
	}

	/** 
	* This method is used to group the mailbags based on the mailbags carrierId and POU .. say OFL-QF-SIN contains a Collection<MailBagVO> OFL-QF-SYD contains a Collection<MailBagVO> A-1936
	* @param offloadVo
	* @return
	*/
	private Map<String, Collection<MailbagVO>> constructMapForOffload(OffloadVO offloadVo) {
		log.debug(CLASS + " : " + "constructMapForOffload" + " Entering");
		Map<String, Collection<MailbagVO>> offloadMap = new HashMap<String, Collection<MailbagVO>>();
		String key = null;
		Collection<MailbagVO> mailbagVos = null;
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>(offloadVo.getOffloadMailbags());
		for (MailbagVO mailbagVo : mailbags) {
			mailbagVo.setFlightDate(offloadVo.getFlightDate());
			mailbagVo.setCarrierCode(offloadVo.getCarrierCode());
			mailbagVo.setScannedUser(offloadVo.getUserCode());
			key = constructContainerForOffload(mailbagVo);
			mailbagVos = offloadMap.get(key);
			if (mailbagVos == null) {
				mailbagVos = new ArrayList<MailbagVO>();
				offloadMap.put(key, mailbagVos);
			}
			mailbagVos.add(mailbagVo);
		}
		return offloadMap;
	}

	/** 
	* @param mailbagVo
	* @return
	* @author a-1936 This method is used to create the DummyContainers in caseof the Offload. Say OFL-QF-SYD OFL-QF-SIN OFL-QF-PER and The Destination of the Container is POU of the Mailbag
	*/
	private String constructContainerForOffload(MailbagVO mailbagVo) {
		return new StringBuilder(MailConstantsVO.MAIL_STATUS_OFFLOADED).append("-").append(mailbagVo.getCarrierCode())
				.append("-").append(mailbagVo.getPou()).toString();
	}

	/** 
	* @param offloadContainer
	* @param scannedPort
	* @return
	* @author a-1936 This method is used to CreateContainerForOffload
	*/
	private ContainerVO createContainerForOffload(String offloadContainer, String scannedPort) {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setContainerNumber(offloadContainer);
		containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		containerVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
		containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		containerVO
				.setAssignedDate(localDateUtil.getLocalDate(scannedPort, true));
		containerVO.setType(MailConstantsVO.BULK_TYPE);
		containerVO.setReassignFlag(true);
		containerVO.setOffloadFlag(MailConstantsVO.FLAG_YES);
		containerVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
		return containerVO;
	}

	/** 
	* @param toDestinationContainerVO
	* @throws SystemException
	* @author a-1936 This methodo is used to update the Container or create andupdate the Container for OFFload
	*/
	private void updateContainerForOffload(ContainerVO toDestinationContainerVO) {
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toDestinationContainerVO.getCompanyCode());
		containerPk.setContainerNumber(toDestinationContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(toDestinationContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toDestinationContainerVO.getFlightNumber());
		containerPk.setCarrierId(toDestinationContainerVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toDestinationContainerVO.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toDestinationContainerVO.getLegSerialNumber());
		toDestinationContainerVO.setAssignedDate(localDateUtil.getLocalDate(
				toDestinationContainerVO.getAssignedPort(), true));
		Container container = null;
		try {
			container = Container.find(containerPk);

			if(toDestinationContainerVO.getLastUpdateTime()!=null){
			container.setLastUpdatedTime(Timestamp.valueOf(toDestinationContainerVO.getLastUpdateTime().toLocalDateTime()));}
		} catch (FinderException ex) {
			container = createContainer(toDestinationContainerVO);
		}
		container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
	}

	/** 
	* This method is used to create the offloadDetails and persist them This is used to used while offloading mailbags A-1739
	* @param offloadVo
	* @throws SystemException
	*/
	private void createOffloadDetailsForMail(OffloadVO offloadVo) {
		log.debug(CLASS + " : " + "createOffloadDetailsForMail" + " Entering");
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>(offloadVo.getOffloadMailbags());
		if (mailbags != null && mailbags.size() > 0) {
			for (MailbagVO mailbagVo : mailbags) {
				OffloadDetailVO offloadDetailVO = new OffloadDetailVO();
				offloadDetailVO.setCompanyCode(mailbagVo.getCompanyCode());
				offloadDetailVO.setContainerNumber(mailbagVo.getContainerNumber());
				offloadDetailVO.setAirportCode(mailbagVo.getScannedPort());
				offloadDetailVO.setCarrierCode(mailbagVo.getCarrierCode());
				offloadDetailVO.setCarrierId(mailbagVo.getCarrierId());
				offloadDetailVO.setFlightNumber(mailbagVo.getFlightNumber());
				offloadDetailVO.setDestinationExchangeOffice(mailbagVo.getDoe());
				offloadDetailVO.setOriginExchangeOffice(mailbagVo.getOoe());
				offloadDetailVO.setDsn(mailbagVo.getDespatchSerialNumber());
				offloadDetailVO.setFlightSequenceNumber(mailbagVo.getFlightSequenceNumber());
				offloadDetailVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
				offloadDetailVO.setMailClass(mailbagVo.getMailClass());
				offloadDetailVO.setMailSubClass(mailbagVo.getMailSubclass());
				offloadDetailVO.setMailCategoryCode(mailbagVo.getMailCategoryCode());
				offloadDetailVO.setMailId(mailbagVo.getMailbagId());
				offloadDetailVO.setOffloadDescription(mailbagVo.getOffloadedDescription());
				offloadDetailVO.setOffloadedDate(localDateUtil.getLocalDate(
						mailbagVo.getScannedPort(), true));
				offloadDetailVO.setOffloadReasonCode(mailbagVo.getOffloadedReason());
				offloadDetailVO.setOffloadRemarks(mailbagVo.getOffloadedRemarks());
				offloadDetailVO.setOffloadType(MailConstantsVO.OFFLOADTYPE_PARTIAL);
				offloadDetailVO.setYear(mailbagVo.getYear());
				offloadDetailVO.setOffloadedBags(MailConstantsVO.ONE);
				offloadDetailVO.setOffloadedWeight(mailbagVo.getWeight());
				offloadDetailVO.setOffloadUser(offloadVo.getUserCode());
				offloadDetailVO.setSegmentSerialNumber(mailbagVo.getSegmentSerialNumber());
				new MailOffloadDetail(offloadDetailVO);
			}
		}
	}

	/** 
	* @param mailBagsForMonitorSLA
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to construct the MonitorSLAVos Fromthe MailBagVos
	*/
	private Collection<MonitorMailSLAVO> createMonitorSLAVosForOffloadMail(Collection<MailbagVO> mailBagsForMonitorSLA,
			ZonedDateTime dateOfOffload) {
		log.debug(CLASS + " : " + "createMonitorSLAVosForOffloadMail" + " Entering");
		MonitorMailSLAVO monitorSLAVo = null;
		Collection<MonitorMailSLAVO> monitorSLAVos = new ArrayList<MonitorMailSLAVO>();
		for (MailbagVO mailBagForMonitorSLA : mailBagsForMonitorSLA) {
			monitorSLAVo = new MonitorMailSLAVO();
			monitorSLAVo.setCompanyCode(mailBagForMonitorSLA.getCompanyCode());
			monitorSLAVo.setActivity(MonitorMailSLAVO.MAILSTATUS_OFFLOADED);
			monitorSLAVo.setMailBagNumber(mailBagForMonitorSLA.getMailbagId());
			monitorSLAVo.setOperationFlag(MonitorMailSLAVO.OPERATION_FLAG_UPDATE);
			monitorSLAVos.add(monitorSLAVo);
		}
		log.debug(CLASS + " : " + "createMonitorSLAVosForOffloadMail" + " Entering");
		return monitorSLAVos;
	}

	/** 
	* This method is used to offload the Dsns Say when the Offload For Dsns Happens all the DSNS are assigned to a POU of the Despatch and into a dummyContainer A-1936
	* @param offloadVo
	* @return
	* @throws SystemException
	* @throws ReassignmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	private Collection<ContainerDetailsVO> offloadDSNs(OffloadVO offloadVo)
			throws ReassignmentException, CapacityBookingProxyException, MailBookingException {
		log.info("OFFLOAD MAILBAGS");
		Map<String, Collection<DespatchDetailsVO>> offloadMap = null;
		Collection<DespatchDetailsVO> despatchDetailsVos = offloadVo.getOffloadDSNs();
		Collection<ContainerDetailsVO> emptyULDs = new ArrayList<ContainerDetailsVO>();
		if (despatchDetailsVos != null && despatchDetailsVos.size() > 0) {
			checkForPaBuiltULDsForDespatch(despatchDetailsVos);
			offloadMap = constructMapForOffloadDSNs(offloadVo);
			if (offloadMap != null && offloadMap.size() > 0) {
				for (String dummyContainer : offloadMap.keySet()) {
					ContainerVO containerVo = createContainerForOffload(dummyContainer, offloadVo.getPol());
					containerVo.setCompanyCode(offloadVo.getCompanyCode());
					containerVo.setCarrierId(offloadVo.getCarrierId());
					containerVo.setAssignedUser(offloadVo.getUserCode());
					containerVo.setAssignedPort(offloadVo.getPol());
					log.debug("" + "THE ASSIGNED PORT IS " + " " + offloadVo.getPol());
					containerVo.setAssignedUser(offloadVo.getUserCode());
					containerVo.setCarrierCode(offloadVo.getCarrierCode());
					for (DespatchDetailsVO despatchDetailsVO : offloadMap.get(dummyContainer)) {
						containerVo.setPou(despatchDetailsVO.getPou());
						containerVo.setFinalDestination(despatchDetailsVO.getPou());
						break;
					}
					updateContainerForOffload(containerVo);
					Collection<DespatchDetailsVO> flightAssignedDSNs = offloadMap.get(dummyContainer);
					emptyULDs.addAll(new ReassignController()
							.reassignDSNsFromFlightToDestination(offloadMap.get(dummyContainer), containerVo));
					createOffloadDetailsForDSNs(offloadVo);
				}
			}
		}
		return emptyULDs;
	}

	/** 
	* This method checks if a despatch is being reassigned from a Pa built ULD. if so it throws exception A-1739
	* @param despatches
	* @throws SystemException
	* @throws ReassignmentException If reassignment is from a PA ULD
	*/
	private void checkForPaBuiltULDsForDespatch(Collection<DespatchDetailsVO> despatches) throws ReassignmentException {
		log.debug(CLASS + " : " + "checkForPaBuiltContainersForDespatch" + " Entering");
		Collection<ContainerPK> containers = groupContainersForDespatches(despatches);
		try {
			for (ContainerPK containerPK : containers) {
				Container container = Container.find(containerPK);
				if (MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())) {
					throw new ReassignmentException(ReassignmentException.DESPATCH_REASSIGN_FROM_PABUILT);
				}
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		log.debug(CLASS + " : " + "checkForPaBuiltContainersForDespatch" + " Exiting");
	}

	/** 
	* A-1739
	* @param despatches
	* @return
	*/
	private Collection<ContainerPK> groupContainersForDespatches(Collection<DespatchDetailsVO> despatches) {
		Collection<ContainerPK> containerPKs = new ArrayList<ContainerPK>();
		for (DespatchDetailsVO despatchDetailsVO : despatches) {
			if (!MailConstantsVO.OPERATION_INBOUND.equals(despatchDetailsVO.getOperationType())) {
				ContainerPK containerPK = constructContainerPKForDespatch(despatchDetailsVO);
				if (!containerPKs.contains(containerPK)) {
					containerPKs.add(containerPK);
				}
			}
		}
		return containerPKs;
	}

	/** 
	* A-1739
	* @param despatchDetailsVO
	* @return
	*/
	private ContainerPK constructContainerPKForDespatch(DespatchDetailsVO despatchDetailsVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(despatchDetailsVO.getCompanyCode());
		containerPK.setCarrierId(despatchDetailsVO.getCarrierId());
		if (despatchDetailsVO.getFlightNumber() != null) {
			containerPK.setFlightNumber(despatchDetailsVO.getFlightNumber());
			containerPK.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
			containerPK.setLegSerialNumber(despatchDetailsVO.getLegSerialNumber());
		} else {
			containerPK.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			containerPK.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			containerPK.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		}
		containerPK.setAssignmentPort(despatchDetailsVO.getAirportCode());
		containerPK.setContainerNumber(despatchDetailsVO.getContainerNumber());
		return containerPK;
	}

	/** 
	* This method is used to group the mailbags based on the mailbags carrierId and POU .. say OFL-QF-SIN contains a Collection<DespatchDetailsVo> OFL-QF-SYD contains a Collection<DespatchDetailsVo>
	* @param offloadVo
	* @return
	*/
	private Map<String, Collection<DespatchDetailsVO>> constructMapForOffloadDSNs(OffloadVO offloadVo) {
		log.debug(CLASS + " : " + "constructMapForOffload" + " Entering");
		Map<String, Collection<DespatchDetailsVO>> offloadMap = new HashMap<String, Collection<DespatchDetailsVO>>();
		String key = null;
		Collection<DespatchDetailsVO> despatchDetailsVOs = null;
		Collection<DespatchDetailsVO> despatchDetails = new ArrayList<DespatchDetailsVO>(offloadVo.getOffloadDSNs());
		for (DespatchDetailsVO despatchDetailsVO : despatchDetails) {
			key = constructContainerForOffloadDSN(despatchDetailsVO);
			despatchDetailsVOs = offloadMap.get(key);
			if (despatchDetailsVOs == null) {
				despatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
				offloadMap.put(key, despatchDetailsVOs);
			}
			despatchDetailsVOs.add(despatchDetailsVO);
		}
		return offloadMap;
	}

	/** 
	* @param despatchDetailsVO
	* @return
	* @author a-1936 This method is used to create the DummyContainers in caseof the Offload. Say OFL-QF-SYD OFL-QF-SIN OFL-QF-PER and The Destination of the Container is POU of the Despatches
	*/
	private String constructContainerForOffloadDSN(DespatchDetailsVO despatchDetailsVO) {
		return new StringBuilder(MailConstantsVO.MAIL_STATUS_OFFLOADED).append("-")
				.append(despatchDetailsVO.getCarrierCode()).append("-").append(despatchDetailsVO.getPou()).toString();
	}

	/** 
	* @param offloadVO
	* @throws SystemException
	* @author a-1936 This method is used to create the Offload Details for theDSNs
	*/
	private void createOffloadDetailsForDSNs(OffloadVO offloadVO) {
		log.debug(CLASS + " : " + "createOffloadDetailsForMail" + " Entering");
		Collection<DespatchDetailsVO> despatchDetails = new ArrayList<DespatchDetailsVO>(offloadVO.getOffloadDSNs());
		if (despatchDetails != null && despatchDetails.size() > 0) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetails) {
				MailbagVO mailbagVO = constructMailbagInULDAtAirportvoFromDespatch(despatchDetailsVO);
				Collection<MailbagVO> mailbagVOs = findMailBagForDespatch(mailbagVO);
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagvo : mailbagVOs) {
						OffloadDetailVO offloadDetailVO = new OffloadDetailVO();
						offloadDetailVO.setCompanyCode(despatchDetailsVO.getCompanyCode());
						offloadDetailVO.setContainerNumber(despatchDetailsVO.getContainerNumber());
						offloadDetailVO.setAirportCode(despatchDetailsVO.getAirportCode());
						offloadDetailVO.setCarrierCode(despatchDetailsVO.getCarrierCode());
						offloadDetailVO.setCarrierId(despatchDetailsVO.getCarrierId());
						offloadDetailVO.setFlightNumber(despatchDetailsVO.getFlightNumber());
						offloadDetailVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
						offloadDetailVO
								.setDestinationExchangeOffice(despatchDetailsVO.getDestinationOfficeOfExchange());
						offloadDetailVO.setOriginExchangeOffice(despatchDetailsVO.getOriginOfficeOfExchange());
						offloadDetailVO.setDsn(despatchDetailsVO.getDsn());
						offloadDetailVO.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
						offloadDetailVO.setMailClass(despatchDetailsVO.getMailClass());
						offloadDetailVO.setMailSubClass(despatchDetailsVO.getMailSubclass());
						offloadDetailVO.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
						offloadDetailVO.setOffloadDescription(despatchDetailsVO.getOffloadedDescription());
						offloadDetailVO.setOffloadedDate(localDateUtil.getLocalDate(
								despatchDetailsVO.getAirportCode(), true));
						offloadDetailVO.setOffloadReasonCode(despatchDetailsVO.getOffloadedReason());
						offloadDetailVO.setOffloadRemarks(despatchDetailsVO.getOffloadedRemarks());
						offloadDetailVO.setOffloadType(MailConstantsVO.OFFLOADTYPE_FULL);
						offloadDetailVO.setYear(despatchDetailsVO.getYear());
						offloadDetailVO.setOffloadedBags(despatchDetailsVO.getAcceptedBags());
						offloadDetailVO.setOffloadedWeight(despatchDetailsVO.getAcceptedWeight());
						offloadDetailVO.setOffloadUser(offloadVO.getUserCode());
						offloadDetailVO.setSegmentSerialNumber(despatchDetailsVO.getSegmentSerialNumber());
						new MailOffloadDetail(offloadDetailVO);
					}
				}
			}
		}
	}

	/**
	* @return
	* @throws SystemException
	* @throws PersistenceException
	* @author A-5991
	*/
	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO) {
		return ULDForSegment.findMailBagForDespatch(mailbagVO);
	}

	/**
	* @return
	* @throws SystemException
	* @author  This method is used to construct theMailbagInULDAtAirportPK
	*/
	private MailbagVO constructMailbagInULDAtAirportvoFromDespatch(DespatchDetailsVO despatchDetailsVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(despatchDetailsVO.getCompanyCode());
		mailbagVO.setUldNumber(despatchDetailsVO.getUldNumber());
		mailbagVO.setScannedPort(despatchDetailsVO.getAirportCode());
		mailbagVO.setCarrierId(despatchDetailsVO.getCarrierId());
		mailbagVO.setDespatchId(createDespatchBag(despatchDetailsVO));
		return mailbagVO;
	}

	public static String createDespatchBag(MailInConsignmentVO mailInConsignmentVO) {
		StringBuilder dsnid = new StringBuilder();
		dsnid.append(mailInConsignmentVO.getOriginExchangeOffice())
				.append(mailInConsignmentVO.getDestinationExchangeOffice())
				.append(mailInConsignmentVO.getMailCategoryCode()).append(mailInConsignmentVO.getMailSubclass())
				.append(mailInConsignmentVO.getYear()).append(mailInConsignmentVO.getDsn());
		return dsnid.toString();
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
	* @param scannedMailbagsToReturn
	* @return Collection<ScannedMailDetailsVO>
	* @throws MailbagAlreadyReturnedException
	* @throws ReturnNotPossibleException
	* @throws SystemException
	* @throws FlightClosedException
	* @throws ReassignmentException           Mailbags are validated and correct mailbags are sent to return.Exceptional mailbags are added with error and sent back.
	* @throws DuplicateMailBagsException
	* @throws InvalidMailTagFormatException
	* @author a-2107
	*/
	public Collection<ScannedMailDetailsVO> returnScannedMailbags(String airportCode,
			Collection<MailbagVO> scannedMailbagsToReturn) throws MailbagAlreadyReturnedException,
			ReturnNotPossibleException, FlightClosedException, ReassignmentException, DuplicateMailBagsException {
		log.debug(CLASS + " : " + "returnscannedMailbags" + " Entering");
		Collection<MailbagVO> exceptionalMailbags = new ArrayList<MailbagVO>();
		Collection<MailbagVO> flightAssignedMails = new ArrayList<MailbagVO>();
		Collection<MailbagVO> destAssignedMails = new ArrayList<MailbagVO>();
		MailbagPK mailbagPK = null;
		Mailbag mailBagTmp = null;
		for (MailbagVO mailbagVO : scannedMailbagsToReturn) {
			mailbagVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
					: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			try {
				mailBagTmp = Mailbag.findMailbag(mailbagPK);
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (mailBagTmp != null) {
				mailbagVO.setFlightSequenceNumber(mailBagTmp.getFlightSequenceNumber());
				mailbagVO.setFlightNumber(mailBagTmp.getFlightNumber());
				mailbagVO.setCarrierId(mailBagTmp.getCarrierId());
				mailbagVO.setSegmentSerialNumber(mailBagTmp.getSegmentSerialNumber());
			}
		}
		if (scannedMailbagsToReturn != null && scannedMailbagsToReturn.size() > 0) {
			if (new ReassignController().isReassignableMailbags(scannedMailbagsToReturn, flightAssignedMails,
					destAssignedMails)) {
				if (flightAssignedMails.size() > 0) {
					new ReassignController().reassignMailFromFlight(flightAssignedMails);
				}
				if (destAssignedMails.size() > 0) {
					new ReassignController().reassignMailFromDestination(destAssignedMails);
				}
			}
			updateMailbagReturnDetails(airportCode, scannedMailbagsToReturn, true);
		}
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (importEnabled != null && importEnabled.contains("D")) {
			Collection<RateAuditVO> rateAuditVOs = createRateAuditVOsForReturn(null, scannedMailbagsToReturn,
					MailConstantsVO.MAIL_STATUS_RETURNED);
			if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (provisionalRateimportEnabled != null && MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			Collection<RateAuditVO> provisionalRateAuditVOs =createRateAuditVOsForReturn(null,
					scannedMailbagsToReturn, MailConstantsVO.MAIL_STATUS_RETURNED);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		scannedMailDetailsVO.setMailDetails(exceptionalMailbags);
		Collection<ScannedMailDetailsVO> exceptionDetails = new ArrayList<ScannedMailDetailsVO>();
		exceptionDetails.add(scannedMailDetailsVO);
		log.debug(CLASS + " : " + "returnscannedMailbags" + " Exiting");
		return exceptionDetails;
	}

	/** 
	* A-1739
	* @param mailbagsToReturn
	* @param isScanned        TODO
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	private void updateMailbagReturnDetails(String airportCode, Collection<MailbagVO> mailbagsToReturn,
			boolean isScanned) throws DuplicateMailBagsException {
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		Mailbag mailbag = new Mailbag();
		mailbag.updateReturnedMailbags(mailbagsToReturn, isScanned);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagResditForMailbags(mailbagsToReturn, airportCode, MailConstantsVO.RESDIT_RECEIVED);
		mailController.flagResditForMailbags(mailbagsToReturn, airportCode, MailConstantsVO.RESDIT_RETURNED);
		mailController.flagHistoryForReturnedMailbags(mailbagsToReturn, getTriggerPoint());
		mailController.flagAuditForReturnedMailbags(mailbagsToReturn);
		String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
		if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
			mailController.flagMLDForMailbagReturn(mailbagsToReturn);
		}
	}

	/** 
	* @param mailbags
	* @throws SystemException
	* @author A-8061
	*/
	public void flagMLDForMailbagReturn(Collection<MailbagVO> mailbags) {
		log.debug(CLASS + " : " + "flagMLDForMailbagReturn" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForMailbagReturn( mailbags));
		log.debug(CLASS + " : " + "flagMLDForMailbagReturn" + " Exiting");
	}

	/**
	* @return
	* @throws SystemException Updating mailbag from db entries with client mailbag
	*/
	public void updateExistingMailBagVO(MailbagVO mailbagvo, MailbagVO mailbagvofromdb, boolean isoffload) {
		log.debug(CLASS + " : " + "updateExistingMailBagVO" + " Entering");
		mailbagvo.setCarrierCode(mailbagvofromdb.getCarrierCode());
		mailbagvo.setCarrierId(mailbagvofromdb.getCarrierId());
		mailbagvo.setPol(mailbagvofromdb.getPol());
		mailbagvo.setPou(mailbagvofromdb.getPou());
		mailbagvo.setFinalDestination(mailbagvofromdb.getFinalDestination());
		mailbagvo.setFlightNumber(mailbagvofromdb.getFlightNumber());
		mailbagvo.setFlightDate(mailbagvofromdb.getFlightDate());
		mailbagvo.setLegSerialNumber(mailbagvofromdb.getLegSerialNumber());
		mailbagvo.setSegmentSerialNumber(mailbagvofromdb.getSegmentSerialNumber());
		mailbagvo.setFlightSequenceNumber(mailbagvofromdb.getFlightSequenceNumber());
		mailbagvo.setContainerNumber(mailbagvofromdb.getContainerNumber());
		mailbagvo.setUldNumber(mailbagvofromdb.getUldNumber());
		mailbagvo.setContainerType(mailbagvofromdb.getContainerType());
		mailbagvo.setLatestStatus(mailbagvofromdb.getLatestStatus());
		mailbagvo.setOperationalStatus(mailbagvofromdb.getOperationalStatus());
		mailbagvo.setConsignmentNumber(mailbagvofromdb.getConsignmentNumber());
		mailbagvo.setConsignmentSequenceNumber(mailbagvofromdb.getConsignmentSequenceNumber());
		mailbagvo.setPaCode(mailbagvofromdb.getPaCode());
		mailbagvo.setDamageFlag(mailbagvofromdb.getDamageFlag());
		mailbagvo.setInventoryContainer(mailbagvofromdb.getInventoryContainer());
		mailbagvo.setInventoryContainerType(mailbagvofromdb.getInventoryContainerType());
		mailbagvo.setArrivedFlag(mailbagvofromdb.getArrivedFlag());
		if (isoffload) {
			mailbagvo.setIsoffload(true);
		}
		if (MailConstantsVO.FLAG_YES.equals(mailbagvo.getReassignFlag())) {
			mailbagvo.setFromSegmentSerialNumber(mailbagvo.getSegmentSerialNumber());
		}
		log.debug(CLASS + " : " + "updateExistingMailBagVO" + " Exiting");
	}

	/** 
	* @param deliverVosForSave
	* @throws SystemException
	* @throws MailBookingException
	* @throws CapacityBookingProxyException
	* @throws ULDDefaultsProxyException
	* @throws FlightClosedException
	* @throws InvalidFlightSegmentException
	* @throws MailbagIncorrectlyDeliveredException
	* @throws DuplicateMailBagsException
	* @throws ContainerAssignmentException
	*/
	public void saveScannedDeliverMails(Collection<MailArrivalVO> deliverVosForSave)
			throws ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			InvalidFlightSegmentException, FlightClosedException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		log.debug(CLASS + " : " + "saveScannedDeliverMails" + " Entering");
		if (deliverVosForSave != null && deliverVosForSave.size() > 0) {
			for (MailArrivalVO deliverVoForSave : deliverVosForSave) {
				deliverMailbags(deliverVoForSave);
			}
		}
		log.debug(CLASS + " : " + "saveScannedDeliverMails" + " Exiting");
	}

	/** 
	* This method is used to create the Monitor SLAVos for all the Inbound Operations say the Arrival\Delivery Operations taking place in the System
	* @param mailbags
	* @param mailArrivalVo
	* @param activity
	* @return
	*/
	private Collection<MonitorMailSLAVO> createMonitorSLAVosForInboundOperations(Collection<MailbagVO> mailbags,
			MailArrivalVO mailArrivalVo, String activity, ZonedDateTime deliveryDate) {
		log.debug(CLASS + " : " + "createMonitorSLAVosForInboundOperations" + " Entering");
		MonitorMailSLAVO monitorSLAVo = null;
		Collection<MonitorMailSLAVO> monitorSLAVos = new ArrayList<MonitorMailSLAVO>();
		for (MailbagVO mailBag : mailbags) {
			monitorSLAVo = new MonitorMailSLAVO();
			monitorSLAVo.setCompanyCode(mailArrivalVo.getCompanyCode());
			monitorSLAVo.setAirlineCode(mailArrivalVo.getOwnAirlineCode());
			monitorSLAVo.setAirlineIdentifier(mailArrivalVo.getOwnAirlineId());
			monitorSLAVo.setFlightCarrierCode(mailArrivalVo.getFlightCarrierCode());
			monitorSLAVo.setFlightCarrierIdentifier(mailArrivalVo.getCarrierId());
			monitorSLAVo.setFlightNumber(mailArrivalVo.getFlightNumber());
			monitorSLAVo.setActivity(activity);
			monitorSLAVo.setMailBagNumber(mailBag.getMailbagId());
			if (MonitorMailSLAVO.MAILSTATUS_ARRIVED.equals(activity)) {
				monitorSLAVo.setScanTime(mailBag.getScannedDate());
			} else {
				monitorSLAVo.setScanTime(deliveryDate);
			}
			if (MonitorMailSLAVO.MAILSTATUS_ARRIVED.equals(activity)) {
				monitorSLAVo.setOperationFlag(mailBag.getOperationalFlag());
			} else {
				monitorSLAVo.setOperationFlag(MonitorMailSLAVO.OPERATION_FLAG_UPDATE);
			}
			monitorSLAVos.add(monitorSLAVo);
		}
		log.debug(CLASS + " : " + "createMonitorSLAVosForInboundOperations" + " Entering");
		return monitorSLAVos;
	}

	/** 
	* @param mailArrivalVO
	* @return void
	* @throws SystemException
	* @author A-2107
	*/
	private void updateMailBagDeliveryInOpertions(MailArrivalVO mailArrivalVO) {

		try {
			operationsFltHandlingProxy.updateOperationalFlightStatus(mailOperationsMapper.
					mailArrivalVOTOOperationalFlightVO(mailArrivalVO));
		}  catch (Exception ex) {
			log.info("" + "EXCEPTION IS THROWN" + " " + ex);
		}
	}

	public void deliverMailbags(MailArrivalVO mailArrivalVO)
			throws ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			InvalidFlightSegmentException, FlightClosedException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		log.debug(CLASS + " : " + "deliveryMailbags" + " Entering");
		Collection<DespatchDetailsVO> despatchesForRemovalFromInventory = new ArrayList<DespatchDetailsVO>();
		Collection<MailbagVO> arrivedMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
		Collection<MailbagVO> deliveredMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
		ZonedDateTime deliveredDate = null;
		boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
		boolean isArrivalOffset = false;
		if (mailArrivalVO.isOfflineJob()) {
			if (mailArrivalVO.isArrivalAndDeliveryMarkedTogether()) {
				isArrivalOffset = true;
			} else {
				if (Objects.nonNull(mailArrivalVO.getActualArrivalTime())) {
					ZonedDateTime actualArrivalTime = mailArrivalVO.getActualArrivalTime();
					ZonedDateTime currentDateTime = localDateUtil.getLocalDate(mailArrivalVO.getLegDestination(), true);
					actualArrivalTime.plusHours(mailArrivalVO.getOffset());
					if (actualArrivalTime.isBefore(currentDateTime)) {
						isArrivalOffset = true;
					}
				}
				if (isArrivalOffset == false) {
					ContextUtil.getInstance().getBean(MailArrival.class).saveArrivalDetails(mailArrivalVO, arrivedMailBagsForMonitorSLA,
							deliveredMailBagsForMonitorSLA);
				}
			}
			if (isArrivalOffset) {
				ContextUtil.getInstance().getBean(MailArrival.class).deliverMailbags(mailArrivalVO, arrivedMailBagsForMonitorSLA,
						deliveredMailBagsForMonitorSLA, despatchesForRemovalFromInventory);
				if (isMonitorSLAEnabled) {
					deliveredDate = localDateUtil.getLocalDate(getLogonAirport(), true);
					if (CollectionUtils.isNotEmpty(arrivedMailBagsForMonitorSLA)) {
						log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA.size());
						log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA);
						monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(arrivedMailBagsForMonitorSLA,
								mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_ARRIVED, null));
					}
					if (CollectionUtils.isNotEmpty(deliveredMailBagsForMonitorSLA)) {
						log.debug("" + "The MailBagVos For SLA DLV" + " " + deliveredMailBagsForMonitorSLA.size());
						log.debug("" + "The MailBagVos For SLA DLV" + " " + deliveredMailBagsForMonitorSLA);
						monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(deliveredMailBagsForMonitorSLA,
								mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_DELIVERED, deliveredDate));
					}
					updateMailBagDeliveryInOpertions(mailArrivalVO);
					log.debug(CLASS + " : " + "deliveryMailbags" + " Exiting");
				}
			}
		} else {
			ContextUtil.getInstance().getBean(MailArrival.class).deliverMailbags(mailArrivalVO, arrivedMailBagsForMonitorSLA,
					deliveredMailBagsForMonitorSLA, despatchesForRemovalFromInventory);
			if (isMonitorSLAEnabled) {
				deliveredDate = localDateUtil.getLocalDate(getLogonAirport(), true);
				if (CollectionUtils.isNotEmpty(arrivedMailBagsForMonitorSLA)) {
					log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA.size());
					log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA);
					monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(arrivedMailBagsForMonitorSLA,
							mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_ARRIVED, null));
				}
				if (CollectionUtils.isNotEmpty(deliveredMailBagsForMonitorSLA)) {
					log.debug("" + "The MailBagVos For SLA DLV" + " " + deliveredMailBagsForMonitorSLA.size());
					log.debug("" + "The MailBagVos For SLA DLV" + " " + deliveredMailBagsForMonitorSLA);
					monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(deliveredMailBagsForMonitorSLA,
							mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_DELIVERED, deliveredDate));
				}
				updateMailBagDeliveryInOpertions(mailArrivalVO);
				log.debug(CLASS + " : " + "deliveryMailbags" + " Exiting");
			}
		}
		Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(mailArrivalVO.getContainerDetails(),
				MailConstantsVO.MAIL_STATUS_DELIVERED, false);
		if (CollectionUtils.isNotEmpty(rateAuditVOs)) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && importEnabled.contains("D")) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
	}

	/** 
	* @param containers
	* @return
	* @throws SystemException
	* @author a-1936
	*/
	public Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers) {
		return MailAcceptance.findMailbagsInContainer(containers);
	}

	/** 
	* This method saves all inbound mails uploaded Each elem in the vo represents the flight and the mails in it TODO Purpose Oct 7, 2006, a-1739
	* @param mailArrivalVOs
	* @return
	* @throws SystemException
	* @throws MailbagIncorrectlyDeliveredException
	* @throws DuplicateMailBagsException
	* @throws ContainerAssignmentException
	* @throws InvalidFlightSegmentException
	* @throws FlightClosedException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public Collection<ScannedMailDetailsVO> saveScannedInboundMails(Collection<MailArrivalVO> mailArrivalVOs)
			throws ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			InvalidFlightSegmentException, FlightClosedException, InventoryForArrivalFailedException,
			ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,
			MailOperationsBusinessException {
		log.debug(CLASS + " : " + "saveScannedInboundMails" + " Entering");
		Collection<MailbagVO> arrivedMailBagsForMonitorSLA = null;
		Collection<MailbagVO> deliveredMailBagsForMonitorSLA = null;
		ZonedDateTime deliveredDate = null;
		boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
		Collection<ScannedMailDetailsVO> scannedDetails = new ArrayList<ScannedMailDetailsVO>();
		for (MailArrivalVO mailArrivalVO : mailArrivalVOs) {
			if (isMonitorSLAEnabled) {
				arrivedMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
				deliveredMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
			}
			ULDInFlightVO uldInFlightVO = null;
			Collection<ULDInFlightVO> uldInFlightVOs = null;
			FlightDetailsVO flightDetailsVO = null;
			boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
			if (isUldIntegrationEnbled) {
				uldInFlightVOs = new ArrayList<ULDInFlightVO>();
				flightDetailsVO = new FlightDetailsVO();
				flightDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
				flightDetailsVO.setFlightCarrierIdentifier(mailArrivalVO.getCarrierId());
				flightDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				flightDetailsVO.setFlightNumber(mailArrivalVO.getFlightNumber());
				if (mailArrivalVO.getArrivalDate() != null) {
					flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getArrivalDate()));

				} else {
					flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getScanDate()));
				}
				flightDetailsVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
				flightDetailsVO.setDirection(MailConstantsVO.IMPORT);
				Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
				if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
					for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
						if (containerDetailsVO.getOperationFlag() != null
								&& !(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType()))) {
							uldInFlightVO = new ULDInFlightVO();
							uldInFlightVO.setUldNumber(containerDetailsVO.getContainerNumber());
							uldInFlightVO.setPointOfLading(containerDetailsVO.getPol());
							uldInFlightVO.setPointOfUnLading(containerDetailsVO.getPou());
							uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
							uldInFlightVO.setContent(MailConstantsVO.UCM_ULD_SOURCE_MAIL);
							uldInFlightVOs.add(uldInFlightVO);
						}
					}
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
					flightDetailsVO.setAction(FlightDetailsVO.ARRIVAL);
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				}
			}
			ScannedMailDetailsVO scanMail = new ScannedMailDetailsVO();
			saveArrivalDetails(mailArrivalVO);
			if (isMonitorSLAEnabled) {
				deliveredDate = localDateUtil.getLocalDate(getLogonAirport(), true);
			}
			if (scanMail != null && scanMail.getMailDetails() != null && scanMail.getMailDetails().size() > 0) {
				scannedDetails.add(scanMail);
			}
			if (isMonitorSLAEnabled) {
				if (arrivedMailBagsForMonitorSLA != null && arrivedMailBagsForMonitorSLA.size() > 0) {
					log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA.size());
					log.debug("" + "The MailBagVos For SLA ARR " + " " + arrivedMailBagsForMonitorSLA);
					monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(arrivedMailBagsForMonitorSLA,
							mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_ARRIVED, null));
				}
				if (deliveredMailBagsForMonitorSLA != null && deliveredMailBagsForMonitorSLA.size() > 0) {
					log.debug("" + "The MailBagVos For SLA DLV " + " " + deliveredMailBagsForMonitorSLA.size());
					log.debug("" + "The MailBagVos For SLA DLV" + " " + deliveredMailBagsForMonitorSLA);
					monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(deliveredMailBagsForMonitorSLA,
							mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_DELIVERED, deliveredDate));
				}
			}
			boolean provisionalRateImport = false;
			Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(mailArrivalVO.getContainerDetails(),
					MailConstantsVO.MAIL_STATUS_ARRIVED, provisionalRateImport);
			log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
			if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
				String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
				if (importEnabled != null && importEnabled.contains("A")) {
					try {
						mailOperationsMRAProxy.importMRAData(rateAuditVOs);
					} catch (BusinessException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
				}
			}
			String provisionalRateimportEnabled = findSystemParameterValue(
					MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
			if (provisionalRateimportEnabled != null && MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
				provisionalRateImport = true;
				Collection<RateAuditVO> provisionalRateAuditVOs = createRateAuditVOs(
						mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_ARRIVED,
						provisionalRateImport);
				if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
					try {
						mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
					} catch (BusinessException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
				}
			}
		}
		log.debug(CLASS + " : " + "saveScannedInboundMails" + " Exiting");
		return scannedDetails;
	}

	/** 
	* This method saves the arrival detail of a flight A-1739
	* @param mailArrivalVO the arrival VO
	* @throws ContainerAssignmentException
	* @throws SystemException
	* @throws DuplicateMailBagsException           If mailbags is arriving in another flight than the currentone
	* @throws MailbagIncorrectlyDeliveredException If mailbags or despatches delivered in ports other that theport of its DOE
	* @throws InvalidFlightSegmentException
	* @throws FlightClosedException
	* @throws ULDDefaultsProxyException
	* @throws InventoryForArrivalFailedException
	* @throws DuplicateDSNException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public void saveArrivalDetailsOld(MailArrivalVO mailArrivalVO) throws ContainerAssignmentException,
			DuplicateMailBagsException, MailbagIncorrectlyDeliveredException, InvalidFlightSegmentException,
			FlightClosedException, InventoryForArrivalFailedException, ULDDefaultsProxyException, DuplicateDSNException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		log.debug(CLASS + " : " + "saveArrivalDetails" + " Entering");
		log.debug("" + "The mailArrivalVO is  >>>>>>>> " + " " + mailArrivalVO);
		Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO contVO : containerDetailsVOs) {
				if (contVO.getMailDetails() != null && !contVO.getMailDetails().isEmpty()) {
					Collection<MailbagVO> mailbagVOs = contVO.getMailDetails();
					if (!mailArrivalVO.isFlightChange()) {
						for (MailbagVO mailbagVO : mailbagVOs) {
							if (mailbagVO.getMailSequenceNumber() > 0 && mailbagVO.getOperationalFlag() != null) {
								mailVOs.add(mailbagVO);
							}
						}
					}
				}
			}
			updateMailbagVOs(mailVOs, mailArrivalVO.isFlightChange());
			mailArrivalVO.setMailVOUpdated(true);
		}
		checkForPostDataCaptureAndSaveDetails(mailArrivalVO);
		Collection<MailbagVO> arrivedMailBagsForMonitorSLA = null;
		Collection<MailbagVO> deliveredMailBagsForMonitorSLA = null;
		ZonedDateTime deliveryDate = null;
		boolean isMonitorSLAEnabled = false;
		boolean undoArriveNeeded = false;
		if (isMonitorSLAEnabled) {
			arrivedMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
			deliveredMailBagsForMonitorSLA = new ArrayList<MailbagVO>();
		}
		mailArrivalVO.setDeliveryCheckNeeded(true);
		Collection<ContainerDetailsVO> arrivedContainers = mailArrivalVO.getContainerDetails();
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		if (isUldIntegrationEnbled) {
			performULDIntegrationOperations(mailArrivalVO);
		}
		generateConsignmentDocumentNoForArrival(mailArrivalVO);
		updateDespatchDocumentDetailsForImport(mailArrivalVO);
		updateMailbagDocumentDetailsForImport(mailArrivalVO);
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				Collection<MailbagVO> mailBagVOs = containerDetailsVO.getMailDetails();
				if (mailBagVOs != null && !mailBagVOs.isEmpty()) {
					for (MailbagVO mailbagVO : mailBagVOs) {
						mailbagVO.setMailSource(mailArrivalVO.getMailSource());
						mailbagVO.setMailbagDataSource(mailArrivalVO.getMailDataSource());
						mailbagVO.setMessageVersion(mailArrivalVO.getMessageVersion());
						if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
							undoArriveNeeded = true;
							if (mailbagVO.getScannedDate() == null) {
								mailbagVO.setScannedDate(localDateUtil.getLocalDate(
										mailbagVO.getScannedPort(),  true));
							}
						}
						Collection<DamagedMailbagVO> damagedMailBags = mailbagVO.getDamagedMailbags();
						if (damagedMailBags != null && !damagedMailBags.isEmpty()) {
							for (DamagedMailbagVO damagedMailbagVO : damagedMailBags) {
								if (damagedMailbagVO.getPaCode() == null) {
									if (mailbagVO.getOoe() != null && mailbagVO.getOoe().trim().length() > 0) {
										String paCode = findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
												mailbagVO.getOoe());
										if (paCode != null && !"".equals(paCode)) {
											damagedMailbagVO.setPaCode(paCode);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		new MailArrival().saveArrivalDetails(mailArrivalVO, arrivedMailBagsForMonitorSLA,
				deliveredMailBagsForMonitorSLA);
		if (isMonitorSLAEnabled) {
			deliveryDate = localDateUtil.getLocalDate(getLogonAirport(), true);
			if (arrivedMailBagsForMonitorSLA != null && arrivedMailBagsForMonitorSLA.size() > 0) {
				monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(arrivedMailBagsForMonitorSLA,
						mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_ARRIVED, null));
			}
			if (deliveredMailBagsForMonitorSLA != null && deliveredMailBagsForMonitorSLA.size() > 0) {
				monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(deliveredMailBagsForMonitorSLA,
						mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_DELIVERED, deliveryDate));
			}
		}
		log.debug(CLASS + " : " + "saveArrivalDetails" + " Entering");
		if (undoArriveNeeded) {
			log.debug("" + "Going To undo arrive ..." + " " + mailArrivalVO);
			undoArriveContainer(mailArrivalVO);
		}
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(mailArrivalVO.getContainerDetails(),
				MailConstantsVO.MAIL_STATUS_ARRIVED, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && importEnabled.contains("A")) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs =createRateAuditVOs(
					mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_ARRIVED, provisionalRateImport);
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
	* @param mailbagVO This method is to perform post data capture only,withouit updating the master details
	* @throws SystemException
	* @author A-5526
	*/
	private void performPostDataCapture(MailbagVO mailbagVO) {
		log.debug(CLASS + " : " + "updatePostCapturedData" + " Entering");
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag = null;
		String mailbagId = mailbagVO.getMailbagId();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException e) {
			log.error("Finder Exception Caught");
		}
		if (mailbag != null) {
			mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())) {
				mailbag.setDamageFlag(mailbagVO.getDamageFlag());
				mailbag.updateDamageDetails(mailbagVO);
			}
		}
		MailbagInULDForSegment mailbagInULDForSegment = null;
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDForSegmentPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDForSegmentPK.setFlightNumber(mailbagVO.getFlightNumber());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setUldNumber(mailbagVO.getUldNumber());
		try {
			mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
		} catch (FinderException e) {
			log.error("Finder Exception Caught");
		}
		if (mailbagInULDForSegment != null) {
			if (mailbagVO.getArrivalSealNumber() != null && mailbagVO.getArrivalSealNumber().trim().length() > 0) {
				mailbagInULDForSegment.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
			}
		}
		MailbagInULDAtAirport mailbagInULDAtAirport = null;
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagInULDAtAirportPK.setCarrierId(mailbagVO.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(mailbagVO.getScannedPort());
		mailbagInULDAtAirportPK.setUldNumber(new StringBuilder().append("BULK").append("-").append("ARR").append("-")
				.append(mailbagVO.getCompanyCode()).toString());
		try {
			mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
		} catch (FinderException e) {
			log.error("Finder Exception Caught");
		}
		if (mailbagInULDAtAirport != null) {
			if (mailbagVO.getSealNumber() != null && mailbagVO.getSealNumber().trim().length() > 0) {
			}
		}
	}

	private void performULDIntegrationOperations(MailArrivalVO mailArrivalVO) throws ULDDefaultsProxyException {
		ULDInFlightVO uldInFlightVO = null;
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		FlightDetailsVO flightDetailsVO = null;
		boolean offlineJobWithAlreadyArrivedMailBag = false;
		uldInFlightVOs = new ArrayList<ULDInFlightVO>();
		flightDetailsVO = new FlightDetailsVO();
		flightDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		flightDetailsVO.setFlightCarrierIdentifier(mailArrivalVO.getCarrierId());
		flightDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
		flightDetailsVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		if (mailArrivalVO.getArrivalDate() != null) {
			flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getArrivalDate()));
		} else {
			flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getScanDate()));
		}
		flightDetailsVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		flightDetailsVO.setDirection(MailConstantsVO.IMPORT);
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (containerDetailsVO.getOperationFlag() != null
						&& !(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType()))) {
					if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getArrivedStatus())
							&& mailArrivalVO.isOfflineJob()) {
						offlineJobWithAlreadyArrivedMailBag = true;
						break;
					}
					uldInFlightVO = new ULDInFlightVO();
					uldInFlightVO.setUldNumber(containerDetailsVO.getContainerNumber());
					uldInFlightVO.setPointOfLading(containerDetailsVO.getPol());
					uldInFlightVO.setPointOfUnLading(containerDetailsVO.getPou());
					uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
					uldInFlightVO.setContent(MailConstantsVO.UCM_ULD_SOURCE_MAIL);
					uldInFlightVOs.add(uldInFlightVO);
				}
			}
			flightDetailsVO.setAction(FlightDetailsVO.ARRIVAL);
			flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
			flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
			if (!offlineJobWithAlreadyArrivedMailBag) {
				uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
			}
		}
	}

	/**
	* @return
	* @throws SystemException
	* @author A-2553
	*/
	private void generateConsignmentDocumentNoForArrival(MailArrivalVO mailArrivalVO) {
		log.debug(CLASS + " : " + "generateConsignmentDocumentNoForArrival" + " Entering");
		Collection<ContainerDetailsVO> contDetVOs = mailArrivalVO.getContainerDetails();
		if (contDetVOs != null && contDetVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : contDetVOs) {
				Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
						if (despatchDetailsVO.getConsignmentNumber() == null
								|| despatchDetailsVO.getConsignmentNumber().length() == 0) {
							//TODO: Master service to be corrected in Neo
//							AirportVO airportVO = sharedAreaProxy.findAirportDetails(mailArrivalVO.getCompanyCode(),
//									mailArrivalVO.getAirportCode());
							AirportVO airportVO = null;
							log.debug("" + "AIRPORT VO" + " " + airportVO);
							String id = new StringBuilder().append(airportVO.getCountryCode())
									.append(airportVO.getCityCode()).toString();
							//TODO: key generation to be correted
							String key = "";//KeyUtils.getKey();
							String str = "";
							int count = 0;
							for (int i = 0; i < (7 - key.length()); i++) {
								if (count == 0) {
									str = "0";
									count = 1;
								} else {
									str = new StringBuilder().append(str).append("0").toString();
								}
							}
							log.debug("" + "222222222%%%%%%%%%%str" + " " + str);
							String conDocNo = new StringBuilder().append(id).append("S").append(str).append(key)
									.toString();
							log.debug("" + "conDocNo" + " " + conDocNo);
							if (despatchDetailsVO.getConsignmentDate() == null) {
								if (airportVO.getAirportCode() != null) {
									despatchDetailsVO
											.setConsignmentDate(localDateUtil.getLocalDate(
													airportVO.getAirportCode(), false));
								} else {
									despatchDetailsVO
											.setConsignmentDate(localDateUtil.getLocalDate(
													null, false));
								}
							}
							despatchDetailsVO.setConsignmentNumber(conDocNo);
							log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentNumber-->" + " "
									+ despatchDetailsVO.getConsignmentNumber());
							log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentDate-->" + " "
									+ despatchDetailsVO.getConsignmentDate());
							log.debug(
									"" + "%%%%%despatchDetailsVO%%%%-PaCode-->" + " " + despatchDetailsVO.getPaCode());
						}
					}
				}
			}
		}
	}

	/** 
	* A-1739
	* @throws SystemException
	* @throws DuplicateMailBagsException
	*/
	private void updateDespatchDocumentDetailsForImport(MailArrivalVO mailArrivalVO) throws DuplicateMailBagsException {
		log.debug(CLASS + " : " + "updateDespatchDocumentDetailsForImport" + " Entering");
		Collection<ContainerDetailsVO> containerDetails = compareAndCalculateTotalsOfDespatches(
				mailArrivalVO.getContainerDetails());
		Map<String, Collection<DespatchDetailsVO>> despatchMap = groupDespatchesForConsignment(containerDetails);
		log.debug("" + "despatch map -->" + " " + despatchMap);
		try {
			for (Map.Entry<String, Collection<DespatchDetailsVO>> despatch : despatchMap.entrySet()) {
				Collection<DespatchDetailsVO> despatches = despatch.getValue();
				ConsignmentDocumentVO consignDocVO = constructConsignmentDocVO(despatch.getKey(), despatches,
						mailArrivalVO.getAirportCode());
				consignDocVO.setScanned(mailArrivalVO.isScanned());
				int consignmentSeqNum = new DocumentController().saveConsignmentForAcceptance(consignDocVO);
				updateDespatchesSequenceNum(consignmentSeqNum, despatches);
			}
		} catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
		}
		log.debug(CLASS + " : " + "updateDespatchDocumentDetailsForImport" + " Exiting");
	}

	/** 
	* A-1739
	* @throws SystemException
	*/
	private void updateMailbagDocumentDetailsForImport(MailArrivalVO mailArrivalVO) {
		Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
		log.debug(CLASS + " : " + "updateMailbagDocumentDetailsForImport" + " Entering");
		if (containerDetails != null && containerDetails.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				if (containerDetailsVO.getOperationFlag() != null) {
					Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
					if (mailbags != null && mailbags.size() > 0) {
						MailInConsignmentVO mailInConsignmentVO = null;
						for (MailbagVO mailbagVO : mailbags) {
							if (mailbagVO.getOperationalFlag() != null) {
								mailInConsignmentVO = mailbagVO.getMailConsignmentVO();
								if (mailInConsignmentVO != null) {
									mailbagVO.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
									mailbagVO.setConsignmentSequenceNumber(
											mailInConsignmentVO.getConsignmentSequenceNumber());
									if (!"MTK064".equalsIgnoreCase(mailArrivalVO.getMailSource())) {
										mailbagVO.setPaCode(mailInConsignmentVO.getPaCode());
									}
								}
							}
						}
					}
				}
			}
		}
		log.debug(CLASS + " : " + "updateMailbagDocumentDetailsForImport" + " Exiting");
	}

	public void undoArriveContainer(MailArrivalVO mailArrivalVO) throws MailOperationsBusinessException {
		log.debug("Entering UNDOARRIVAl >>>>>>>>>>>>>>>>>>");
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		Collection<ContainerDetailsVO> containerDetailsVOforUndoArrival = new ArrayList<ContainerDetailsVO>();
		Collection<DamagedMailbagVO> damagedmailbags = new ArrayList<DamagedMailbagVO>();
		int legserialnum = mailArrivalVO.getLegSerialNumber();
		ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
		String changeFlightFlag = mailArrivalVO.getChangeFlightFlag();
		if (CollectionUtils.isNotEmpty(containerDetailsVOs)) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (MailConstantsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag())) {
					containerDetailsVOforUndoArrival.add(containerDetailsVO);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(containerDetailsVOforUndoArrival)) {
			log.debug("" + "The containerDetailsVOforUndoArrival is >>>>" + " " + containerDetailsVOforUndoArrival);
			new AssignedFlightSegment().undoArriveContainer(containerDetailsVOforUndoArrival);
		}
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOforUndoArrival) {
			Container container = null;
			Container bulkcontainer = null;
			ContainerPK containerpk = null;
			if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
				containerpk = constructContainerPKfromContainerDetailVO(containerDetailsVO);
				try {
					container = Container.find(containerpk);
				} catch (FinderException e) {
					log.error("Finder Exception Caught");
				}
				if (container != null) {
					if (MailConstantsVO.FLAG_YES.equals(container.getAcceptanceFlag()) && changeFlightFlag == null) {
						container.setTransitFlag(MailConstantsVO.FLAG_YES);
					} else {
						if ("CON".equals(containerDetailsVO.getUndoArrivalFlag())) {
							container.remove();
						}
					}
				}
			}
			Collection<MailbagVO> mailBagVOs = containerDetailsVO.getMailDetails();
			int mailbagcount = mailBagVOs.size();
			int mailbagforundoarrival = 0;
			int unarrivedbags = 0;
			if (CollectionUtils.isNotEmpty(mailBagVOs)) {
				for (MailbagVO mailbagvo : mailBagVOs) {
					if (MailConstantsVO.FLAG_NO.equals(mailbagvo.getArrivedFlag())
							&& !MailConstantsVO.FLAG_YES.equals(mailbagvo.getUndoArrivalFlag())) {
						unarrivedbags++;
					}
					if (MailConstantsVO.FLAG_YES.equals(mailbagvo.getUndoArrivalFlag())) {
						Mailbag mailbag = null;
						MailbagPK mailbagpk = createMailbagPK(mailbagvo.getCompanyCode(), mailbagvo);
						try {
							mailbag = Mailbag.find(mailbagpk);
						} catch (FinderException e) {
							log.error("Finder Exception Caught");
						}
						mailhistories = (ArrayList<MailbagHistoryVO>) findMailbagHistories(mailbagvo.getCompanyCode(),
								mailbagvo.getMailbagId(), 0l);
						log.debug("" + "mailbaghistories present (before removing) >>>>" + " " + mailhistories);
						ArrayList<MailbagHistoryVO> mailbaghistories = new ArrayList<MailbagHistoryVO>();
						ArrayList<MailbagHistoryVO> filteredmailbaghistories = new ArrayList<MailbagHistoryVO>();
						ArrayList<MailbagHistoryVO> damagedMailbaghistories = new ArrayList<MailbagHistoryVO>();
						for (MailbagHistoryVO mailhistoryvo : mailhistories) {
							if ("TRA".equals(mailhistoryvo.getMailStatus())
									|| "ARR".equals(mailhistoryvo.getMailStatus())
									|| "ACP".equals(mailhistoryvo.getMailStatus())
									|| "ASG".equals(mailhistoryvo.getMailStatus())
									|| "RTN".equals(mailhistoryvo.getMailStatus())
									|| "OFL".equals(mailhistoryvo.getMailStatus())
									|| "DLV".equals(mailhistoryvo.getMailStatus())) {
								filteredmailbaghistories.add(mailhistoryvo);
							}
							if ("DMG".equals(mailhistoryvo.getMailStatus())) {
								damagedMailbaghistories.add(mailhistoryvo);
							}
						}
						log.debug("" + "mailbaghistories present (after filtering) >>>>" + " " + mailbaghistories);
						log.debug("" + "damagedmailbaghistories present (after removing) >>>>" + " "
								+ damagedMailbaghistories);
						if (filteredmailbaghistories != null && filteredmailbaghistories.size() > 0) {
							MailbagHistoryVO presentHistory = filteredmailbaghistories
									.get(filteredmailbaghistories.size() - 1);
							if (!presentHistory.getScannedPort().equals(mailbagvo.getScannedPort())) {
								if ((presentHistory.getFlightNumber() != null
										&& presentHistory.getFlightNumber().equals(mailbagvo.getFlightNumber()))
										&& (presentHistory.getFlightSequenceNumber() == mailbagvo
												.getFlightSequenceNumber())) {
									throw new MailOperationsBusinessException(
											MailOperationsBusinessException.MAILTRACKING_MAIL_UNARRIVEDBAGS);
								} else {
									throw new MailOperationsBusinessException(
											MailOperationsBusinessException.MAILTRACKING_MAIL_TRANSFERRED_OR_DELIVERED);
								}
							}
							if (presentHistory.getScannedPort().equals(mailbagvo.getScannedPort())) {
								if ("TRA".equals(presentHistory.getMailStatus())
										|| "ASG".equals(presentHistory.getMailStatus())
										|| "DLV".equals(presentHistory.getMailStatus())) {
									throw new MailOperationsBusinessException(
											MailOperationsBusinessException.MAILTRACKING_MAIL_TRANSFERRED_OR_DELIVERED);
								}
								if ("ARR".equals(presentHistory.getMailStatus())
										&& !(presentHistory.getFlightNumber().equals(mailbagvo.getFlightNumber()))
										&& presentHistory.getFlightSequenceNumber() != mailbagvo
												.getFlightSequenceNumber()) {
									throw new MailOperationsBusinessException(
											MailOperationsBusinessException.MAILTRACKING_MAIL_TRANSFERRED_OR_DELIVERED);
								}
							}
							for (MailbagHistoryVO mailhistoryvo : mailhistories) {
								if (mailhistoryvo.getScannedPort().equals(mailbagvo.getScannedPort())
										&& ((("ARR").equals(mailhistoryvo.getMailStatus()))
												|| (("DMG").equals(mailhistoryvo.getMailStatus())))
										&& (mailhistoryvo.getFlightNumber().equals(mailbagvo.getFlightNumber())
												&& mailhistoryvo.getFlightSequenceNumber() == (mailbagvo
														.getFlightSequenceNumber()))) {
									MailbagHistory mailhistory = null;
									MailbagHistoryPK mailbaghistorypk = constructMailbagHistoryPK(mailhistoryvo);
									try {
										mailhistory = MailbagHistory.findMailbagHistory(mailbaghistorypk);
									} catch (FinderException e) {
										log.error("Finder Exception Caught");
									}
									mailhistory.remove();
								} else if ("TRA".equals(mailhistoryvo.getMailStatus())
										|| "ARR".equals(mailhistoryvo.getMailStatus())
										|| "ACP".equals(mailhistoryvo.getMailStatus())
										|| "ASG".equals(mailhistoryvo.getMailStatus())
										|| "RTN".equals(mailhistoryvo.getMailStatus())
										|| "OFL".equals(mailhistoryvo.getMailStatus())
										|| "DLV".equals(mailhistoryvo.getMailStatus())
										|| "DMG".equals(mailhistoryvo.getMailStatus())
										|| "CDT".equals(mailhistoryvo.getMailStatus())) {
									mailbaghistories.add(mailhistoryvo);
								}
							}
						}
						log.debug("" + "mailbaghistories present (after removing) >>>>" + " " + mailbaghistories);
						if (mailbaghistories.size() == 0
								&& !MailConstantsVO.FLAG_YES.equals(mailbagvo.getAcceptanceFlag())) {
							log.debug("" + "Found mailbag : hence removing >>>>" + " " + mailbag);
							mailbag.remove();
						} else {
							MailbagHistoryVO latestHistory = null;
							int historysize = mailbaghistories.size();
							latestHistory = mailbaghistories.get(historysize - 1);
							if (latestHistory != null) {
								if ("TRA".equals(latestHistory.getMailStatus())
										|| "ARR".equals(latestHistory.getMailStatus())
										|| "ACP".equals(latestHistory.getMailStatus())
										|| "ASG".equals(latestHistory.getMailStatus())
										|| "RTN".equals(latestHistory.getMailStatus())
										|| "OFL".equals(latestHistory.getMailStatus())
										|| "DLV".equals(latestHistory.getMailStatus())
										|| "DMG".equals(latestHistory.getMailStatus())
										|| "CDT".equals(latestHistory.getMailStatus())) {
									mailbag.setScannedPort(latestHistory.getScannedPort());
									if ("CDT".equals(latestHistory.getMailStatus())) {
										mailbag.setLatestStatus(MailConstantsVO.MAIL_STATUS_NEW);
									} else {
										mailbag.setLatestStatus(latestHistory.getMailStatus());
									}
									mailbag.setUldNumber(latestHistory.getContainerNumber());
									mailbag.setContainerType(latestHistory.getContainerType());
									mailbag.setFlightNumber(latestHistory.getFlightNumber());
									mailbag.setFlightSequenceNumber(latestHistory.getFlightSequenceNumber());
								}
							}
							mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
							if (MailConstantsVO.BULK_TYPE.equals(mailbagvo.getContainerType())) {
								String bulkcontainernumber = latestHistory.getContainerNumber();
								log.debug("" + "Bulk number at export side  >>>>" + " " + bulkcontainernumber);
								ContainerPK bulkcontainerpk = new ContainerPK();
								bulkcontainerpk.setCarrierId(containerDetailsVO.getCarrierId());
								bulkcontainerpk.setCompanyCode(containerDetailsVO.getCompanyCode());
								bulkcontainerpk.setContainerNumber(bulkcontainernumber);
								bulkcontainerpk.setFlightNumber(containerDetailsVO.getFlightNumber());
								bulkcontainerpk.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
								bulkcontainerpk.setAssignmentPort(containerDetailsVO.getPol());
								if (containerDetailsVO.getLegSerialNumber() == 0) {
									bulkcontainerpk.setLegSerialNumber(legserialnum);
								} else {
									bulkcontainerpk.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
								}
								try {
									bulkcontainer = Container.find(bulkcontainerpk);
								} catch (FinderException e) {
									log.error("Finder Exception Caught");
								}
								mailbag.setUldNumber(bulkcontainernumber);
							}
							if (CollectionUtils.isNotEmpty(mailbagvo.getDamagedMailbags())) {
								log.debug("" + "Damagedmailbags present (before removing) >>>>" + " "
										+ mailbagvo.getDamagedMailbags());
								for (DamagedMailbagVO damagedmailvo : mailbagvo.getDamagedMailbags()) {
									if (mailbagvo.getScannedPort().equals(damagedmailvo.getAirportCode())) {
										DamagedMailbag damagedmailbag = null;
										DamagedMailbagPK damagedmailpk = constructDamagedMailbagPK(damagedmailvo,
												mailbagvo);
										try {
											damagedmailbag = DamagedMailbag.find(damagedmailpk);
										} catch (FinderException e) {
											log.error("Finder Exception Caught");
										}
										mailbagvo.setDamageFlag(MailConstantsVO.FLAG_NO);
										damagedmailbag.remove();
									} else {
										mailbagvo.setDamageFlag(MailConstantsVO.FLAG_YES);
										damagedmailbags.add(damagedmailvo);
									}
								}
								log.debug("" + "Damagedmailbags present (after removing) >>>>" + " " + damagedmailbags);
								if (damagedmailbags != null && damagedmailbags.size() > 0) {
									mailbagvo.setDamagedMailbags(damagedmailbags);
								}
							}
							if (mailbagvo.getDamageFlag() != null) {
								mailbag.setDamageFlag(mailbagvo.getDamageFlag());
							} else {
								mailbag.setDamageFlag(MailbagVO.FLAG_NO);
							}
						}
						MailbagInULDForSegment mailbagInULDForSegment = null;
						MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
						mailbagInULDForSegmentPK.setCompanyCode(mailbagvo.getCompanyCode());
						mailbagInULDForSegmentPK.setCarrierId(mailbagvo.getCarrierId());
						mailbagInULDForSegmentPK.setFlightNumber(mailbagvo.getFlightNumber());
						mailbagInULDForSegmentPK.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
						if (mailbagvo.getUldNumber() != null) {
							mailbagInULDForSegmentPK.setUldNumber(mailbagvo.getUldNumber());
						} else {
							mailbagInULDForSegmentPK.setUldNumber(mailbagvo.getContainerNumber());
						}
						mailbagInULDForSegmentPK.setSegmentSerialNumber(mailbagvo.getSegmentSerialNumber());
						mailbagInULDForSegmentPK.setMailSequenceNumber(
								mailbagvo.getMailSequenceNumber() > 0 ? mailbagvo.getMailSequenceNumber()
										: findMailSequenceNumber(mailbagvo.getMailbagId(), mailbagvo.getCompanyCode()));
						try {
							mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
						} catch (FinderException e) {
							log.error("Finder Exception Caught-mailbagInULDForSegment");
						}
						if (mailbagInULDForSegment != null) {
							mailbagInULDForSegment.setRecievedBags(mailbagInULDForSegment.getRecievedBags() - 1);
							mailbagInULDForSegment.setRecievedWeight(mailbagInULDForSegment.getRecievedWeight()
									- mailbagvo.getWeight().getValue().doubleValue());
							mailbagInULDForSegment.setArrivalFlag(MailConstantsVO.FLAG_NO);
							if (mailbagInULDForSegment.getAcceptedBags() == 0
									&& mailbagInULDForSegment.getRecievedBags() == 0) {
								mailbagInULDForSegment.remove();
							}
						}
						MailbagInULDAtAirport mailbagInULDAtAirport = null;
						try {
							MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
							mailbagInULDAtAirportPK.setCompanyCode(mailbagvo.getCompanyCode());
							mailbagInULDAtAirportPK.setCarrierId(mailbagvo.getCarrierId());
							mailbagInULDAtAirportPK.setAirportCode(mailbagvo.getScannedPort());
							mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
							mailbagInULDAtAirportPK.setUldNumber(mailbagvo.getContainerNumber());
							mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
							mailbagInULDAtAirport.remove();
						} catch (FinderException e) {
							e.printStackTrace();
						} finally {
						}
						MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
						mailbagInULDAtAirportPK.setCompanyCode(mailbagvo.getCompanyCode());
						mailbagInULDAtAirportPK.setCarrierId(mailbagvo.getCarrierId());
						mailbagInULDAtAirportPK.setAirportCode(mailbagvo.getScannedPort());
						if (mailbagvo.getUldNumber() == null) {
							mailbagInULDAtAirportPK.setUldNumber(new StringBuilder().append("BULK").append("-")
									.append("ARR").append("-").append(mailbagvo.getCompanyCode()).toString());
						} else {
							mailbagInULDAtAirportPK.setUldNumber(mailbagvo.getUldNumber());
						}
						mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
						try {
							mailbagInULDAtAirport = MailbagInULDAtAirport.find(mailbagInULDAtAirportPK);
						} catch (FinderException e) {
							log.error("Finder Exception Caught");
						}
						if (mailbagInULDAtAirport != null) {
							mailbagInULDAtAirport.setAcceptedBags(mailbagInULDAtAirport.getAcceptedBags() - 1);
							mailbagInULDAtAirport.setAcceptedWeight(
									mailbagInULDAtAirport.getAcceptedWeight() - mailbagvo.getWeight().getValue().doubleValue());
							if (mailbagInULDAtAirport.getAcceptedBags() == 0
									&& mailbagInULDAtAirport.getAcceptedWeight() == 0) {
								mailbagInULDAtAirport.remove();
							}
						}
						MailResdit mailresdit = null;
						long sequenceNumber = 0;
						MailResditVO mailResditVO = new MailResditVO();
						mailResditVO.setCompanyCode(mailbagvo.getCompanyCode());
						mailResditVO.setEventCode(MailConstantsVO.RESDIT_ARRIVED);
						mailResditVO.setMailId(mailbagvo.getMailbagId());
						mailResditVO.setEventAirport(mailbagvo.getScannedPort());
						mailResditVO.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
						try {
							sequenceNumber = constructDAO().findResditSequenceNumber(mailResditVO);
						} catch (PersistenceException exception) {
							throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
						}
						MailResditPK mailresditPK = new MailResditPK();
						mailresditPK.setCompanyCode(mailbagvo.getCompanyCode());
						mailresditPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
						mailresditPK.setEventCode(MailConstantsVO.RESDIT_ARRIVED);
						mailresditPK.setSequenceNumber(sequenceNumber);
						try {
							mailresdit = MailResdit.find(mailresditPK);
						} catch (FinderException e) {
							log.error("Finder Exception Caught");
						}
						if (mailresdit != null) {
							mailresdit.remove();
						}
						mailbagforundoarrival++;
					}
				}
				if (mailbagforundoarrival + unarrivedbags == mailbagcount) {
					if (container != null && MailConstantsVO.ULD_TYPE.equals(container.getContainerType())) {
						container.setTransactionCode("ASG");
						container.setArrivedStatus(MailConstantsVO.FLAG_NO);
					} else if (bulkcontainer != null
							&& MailConstantsVO.BULK_TYPE.equals(bulkcontainer.getContainerType())) {
						bulkcontainer.setArrivedStatus(MailConstantsVO.FLAG_NO);
					}
				}
			}
		}
	}

	/** 
	* Added A-5945
	* @return ContainerPK
	*/
	private ContainerPK constructContainerPKfromContainerDetailVO(ContainerDetailsVO containerDetailsVO) {
		ContainerPK containerpk = new ContainerPK();
		containerpk.setCarrierId(containerDetailsVO.getCarrierId());
		containerpk.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerpk.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerpk.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerpk.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerpk.setAssignmentPort(containerDetailsVO.getPol());
		containerpk.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		return containerpk;
	}

	/** 
	* Added A-5945
	* @return DamagedMailbagPK
	*/
	private DamagedMailbagPK constructDamagedMailbagPK(DamagedMailbagVO damagedmailvo, MailbagVO mailbagvo) {
		DamagedMailbagPK damagedMailbagPK = new DamagedMailbagPK();
		damagedMailbagPK.setCompanyCode(mailbagvo.getCompanyCode());
		damagedMailbagPK.setAirportCode(damagedmailvo.getAirportCode());
		damagedMailbagPK.setDamageCode(damagedmailvo.getDamageCode());
		damagedMailbagPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		return damagedMailbagPK;
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
	* Added A-5945 * @return MailbagHistoryPK
	* @throws SystemException
	*/
	private MailbagHistoryPK constructMailbagHistoryPK(MailbagHistoryVO mailhistoryvo) {
		MailbagHistoryPK mailbagHistoryPK = new MailbagHistoryPK();
		mailbagHistoryPK.setCompanyCode(mailhistoryvo.getCompanyCode());
		mailbagHistoryPK.setHistorySequenceNumber(mailhistoryvo.getHistorySequenceNumber());
		mailbagHistoryPK
				.setMailSequenceNumber(mailhistoryvo.getMailSequenceNumber() > 0 ? mailhistoryvo.getMailSequenceNumber()
						: findMailSequenceNumber(mailhistoryvo.getMailbagId(), mailhistoryvo.getCompanyCode()));
		return mailbagHistoryPK;
	}

	/** 
	* @param mailArrivalVO This method is to check and update post data capturing like MailCompanyCOde /Seal Number/Arrival Seal number etc to a transferred bag in its arrived flight
	* @throws SystemException
	* @author A-5526
	*/
	private void checkForPostDataCaptureAndSaveDetails(MailArrivalVO mailArrivalVO) {
		log.debug(CLASS + " : " + "checkForPostDataCaptureAndSaveDetails" + " Entering");
		Collection<ContainerDetailsVO> arrivedContainers = mailArrivalVO.getContainerDetails();
		String POST_DATA_CAPTURE = "PDC";
		if (arrivedContainers != null && arrivedContainers.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : arrivedContainers) {
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailbagVO : mailbags) {
						if ("U".equals(mailbagVO.getOperationalFlag())) {
							if (isPostDataCaptureOfMailbag(mailbagVO)) {
								mailbagVO.setOperationalFlag(null);
								mailbagVO.setActionMode(POST_DATA_CAPTURE);
								performPostDataCapture(mailbagVO);
							}
						}
					}
				}
			}
		}
	}

	/** 
	* @param mailbagVO This method is to identify post data capturing
	* @return
	* @throws SystemException
	* @author A-5526
	*/
	private boolean isPostDataCaptureOfMailbag(MailbagVO mailbagVO) {
		log.debug(CLASS + " : " + "findCurrentStatusOfMailbag" + " Entering");
		MailbagPK mailbagPk = new MailbagPK();
		Mailbag mailbag = null;
		boolean postDataCapture = false;
		String mailbagId = mailbagVO.getMailbagId();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
				: findMailSequenceNumber(mailbagId, mailbagVO.getCompanyCode()));
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException e) {
			log.error("Finder Exception Caught");
		}
		if (mailbag != null) {
			if (!mailbag.getScannedPort().equals(mailbagVO.getScannedPort())) {
				if ("O".equals(mailbag.getOperationalStatus())
						&& (mailbag.getFlightNumber() != null
								&& mailbag.getFlightNumber().equals(mailbagVO.getFlightNumber()))
						&& (mailbag.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber())) {
					postDataCapture = false;
				} else {
					Collection<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
					mailhistories = (mailbagVO.getMailbagHistories() != null
							&& !mailbagVO.getMailbagHistories().isEmpty()) ? mailbagVO.getMailbagHistories()
									: (ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistories(
											mailbagVO.getCompanyCode(), mailbagVO.getMailbagId(), 0l);
					if (mailhistories != null && mailhistories.size() > 0) {
						for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
							if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())
									&& mailbaghistoryvo.getScannedPort().equals(mailbagVO.getScannedPort())) {
								if ((mailbaghistoryvo.getFlightNumber() != null
										&& mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber()))
										|| mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO
												.getFlightSequenceNumber()) {
									postDataCapture = true;
								}
							}
						}
					}
				}
			} else if (mailbag.getScannedPort().equals(mailbagVO.getScannedPort())) {
				if (!mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())
						|| mailbagVO.getFlightSequenceNumber() != mailbag.getFlightSequenceNumber()) {
					postDataCapture = true;
				}
			}
		}
		return postDataCapture;
	}

	/** 
	* A-1739
	* @return
	*/
	public String constructBulkULDNumber(String airport, String carrierCode) {
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/** 
	* @param containerDetailsVO
	* @return
	* @author a-1936 This method is used to Create the ContainerVO
	*/
	private ContainerVO constructContainerVO(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "constructContainerVO" + " Entering");
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setAssignedPort(containerDetailsVO.getPol());
		containerVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		return containerVO;
	}

	public void saveDamageDetailsForMailbag(Collection<MailbagVO> mailbags) {
		if (CollectionUtils.isNotEmpty(mailbags)) {
			var flightAssignedMailbagVOs = new ArrayList<MailbagVO>();
			var destAssignedMailbagVOs = new ArrayList<MailbagVO>();

			updateDamagedMailbags(mailbags);
			groupDestFlightMailbags(mailbags, flightAssignedMailbagVOs, destAssignedMailbagVOs);

			if (CollectionUtils.isNotEmpty(flightAssignedMailbagVOs)) {
				log.info("<<<<Calling Save Damages For Flight >>>>>");
				new AssignedFlightSegment().saveDamageDetailsForMailbags(flightAssignedMailbagVOs);
			}
			if (CollectionUtils.isNotEmpty(destAssignedMailbagVOs)) {
				log.info("<<<<Calling Save Damages For Destination >>>>>");
				new ULDAtAirport().saveDamageDetailsForMailbags(destAssignedMailbagVOs);
			}
		}
	}

	public void updateDamagedMailbags(Collection<MailbagVO> damagedMailBags) {
		log.debug(CLASS + " : " + "updateDamagedMailbags" + " Entering");
		for (MailbagVO mailbagVO : damagedMailBags) {
			try {
				var mailbag = Mailbag.findMailbag(createMailbagPK(mailbagVO.getCompanyCode(), mailbagVO));
				if  (Objects.nonNull(mailbagVO.getLastUpdateTime())) {
					mailbag.setLastUpdatedTime(Timestamp.valueOf(mailbagVO.getLastUpdateTime().toLocalDateTime()));
				}
				mailbag.updateDamageDetails(mailbagVO);
			} catch (FinderException exception) {
				mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_NEW);
				Mailbag mailbag = new Mailbag(mailbagVO);
				mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
				mailbag.updateDamageDetails(mailbagVO);
			}
			if (CollectionUtils.isNotEmpty(mailbagVO.getAttachments())) {
				uploadDocumentsToRepository(mailbagVO);
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagHistoryForDamagedMailbags(damagedMailBags, getTriggerPoint());
		mailController.flagAuditForDamagedMailbags(damagedMailBags);
	}

	/** 
	* A-1739
	* @param mailbags
	* @param destAssignedMailbagVOs
	* @param flightAssignedMailbagVOs
	*/
	private void groupDestFlightMailbags(Collection<MailbagVO> mailbags, Collection<MailbagVO> flightAssignedMailbagVOs,
			Collection<MailbagVO> destAssignedMailbagVOs) {
		for (MailbagVO mailbagVO : mailbags) {
			if (mailbagVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
					&& !MailConstantsVO.MAIL_STATUS_NEW.equals(mailbagVO.getLatestStatus())) {
				destAssignedMailbagVOs.add(mailbagVO);
			} else if (!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbagVO.getLatestStatus())) {
				flightAssignedMailbagVOs.add(mailbagVO);
			}
		}
	}

	/** 
	* A-2107
	* @throws SystemException OffloadVO is constructed
	*/
	private OffloadVO updateMailbagOffloadDetails(MailbagVO mailbagsToOffload) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Page<MailbagVO> newMailbagVOs = new Page<MailbagVO>(new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0, false);
		OffloadVO offloadVO = new OffloadVO();
		newMailbagVOs.add(mailbagsToOffload);
		offloadVO.setOffloadMailbags(newMailbagVOs);
		offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_MAILBAG);
		offloadVO.setCarrierCode(mailbagsToOffload.getCarrierCode());
		offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
		offloadVO.setCarrierId(mailbagsToOffload.getCarrierId());
		offloadVO.setFlightNumber(mailbagsToOffload.getFlightNumber());
		offloadVO.setFlightSequenceNumber(mailbagsToOffload.getFlightSequenceNumber());
		offloadVO.setPol(mailbagsToOffload.getPol());
		offloadVO.setLegSerialNumber(mailbagsToOffload.getLegSerialNumber());
		offloadVO.setFlightDate(mailbagsToOffload.getFlightDate());
		offloadVO.setUserCode(logonAttributes.getUserId());
		return offloadVO;
	}

	/** 
	* Need 2 checks: call the findContainerAssignment to retrieve the ContainerAssignmentDetails if 1.ContainerAssignmentVo is null (i.e) Container is not already Assigned to a Flight or Carrier/Destination we can allow the Container For new Assignments <p> else <p> 1.If Flight Assigned Option : Find FlightSegments. From that find segment details for the POL-POU of the container. If current flight details and DB details are same, throw exception saying assigned to same flight. IF currently assigned to a carrier , Throw exception , Show message for Reassign. IF currently assigned to another flight, check the staus of that flight. 1. If it is open throw Exception saying that it can be reassigned.Warning mesasage shown in the client. else if (i).closed and departed(Take the Leg status from Flight Validation VO returned from FlightProduct) check if currentDate>=flightDeparture and currentDate <= flightDeparture+configuredPeriod(SystemParameters) else throw exception(Client shows a Warning asking for a new Ass) <p> (ii).if closed and not departed then check if currentdate>=flightdeparture-configured and currentDate<=flightDeparture else throw exception (check ValidateFlightForStatus method) <p> If Destn Assigned Option is selected: IF currently assigned to a carrier , Throw exception If currently assigned to the Flight 1. If it is open throw Exception saying that it can be reassigned.Warning mesasage shown in the client. else if (i).closed and departed(Take the Leg status from Flight Validation VO returned from ValidateFlight) check if currentDate>=flightDeparture and currentDate <= flightDeparture+configuredPeriod(SystemParameters) else throw exception <p> (ii).if closed and not departed then check if currentdate>=flightdeparture-configured and currentDate<=flightDeparture else throw exception (check ValidateFlightForStatus method) <p> <p> <p> 2.If the ULD final destination does not match with the last POU in onward flight routing then throw exception <p> ************** Changed: Validate all ULDs with OPRULDDTL ******************* Validate all the Bulk Containers with above validation
	* @param containerVO
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws ULDDefaultsProxyException
	*/
	public ContainerVO validateContainer(String airportCode, ContainerVO containerVO)
			throws ContainerAssignmentException, ULDDefaultsProxyException {
		log.debug(CLASS + " : " + "validateContainer" + " Entering");
		FlightDetailsVO flightDetails = null;
		Collection<ULDInFlightVO> uldInFlightVos = null;
		ULDInFlightVO uldInFlightVo = null;
		if (CONTAINER_ASSIGNEDFORFLIGHT.equals(containerVO.getAssignmentFlag())) {
			log.debug("Container is Requested For Flight*------>>>>>>>");
			log.debug("-------Trying to assign the Container to the Flight----");
			try {
				containerVO.setSegmentSerialNumber(findContainerSegment(containerVO));
				//TODO: check why this throw is req
				throw new ContainerAssignmentException(ContainerAssignmentException.INVALID_FLIGHT_SEGMENT);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
			validateContainerAssignment(airportCode, containerVO);
			validateContainerReusability(containerVO);
		} else if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
			validateContainerAssignment(airportCode, containerVO);
		}
		if (!containerVO.isOverrideUMSFlag() && isULDIntegrationEnabled()) {
			if (containerVO.getContainerNumber().length() > 0) {
				boolean isULDType = false;
				isULDType = MailConstantsVO.ULD_TYPE.equals(containerVO.getType());
				if (isULDType) {
					flightDetails = new FlightDetailsVO();
					flightDetails.setCompanyCode(containerVO.getCompanyCode());
					flightDetails.setCurrentAirport(containerVO.getAssignedPort());
					if (containerVO.getFlightSequenceNumber() > 0) {
						flightDetails.setFlightNumber(containerVO.getFlightNumber());
						flightDetails.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
						flightDetails.setFlightCarrierIdentifier(containerVO.getCarrierId());
					}
					uldInFlightVos = new ArrayList<ULDInFlightVO>();
					uldInFlightVo = new ULDInFlightVO();
					uldInFlightVo.setUldNumber(containerVO.getContainerNumber());
					uldInFlightVo.setPointOfLading(containerVO.getAssignedPort());
					uldInFlightVos.add(uldInFlightVo);
					flightDetails.setUldInFlightVOs(uldInFlightVos);
					log.debug("" + "THE Flight Details Vos " + " " + flightDetails);
					Collection<String> uldNumbers = new ArrayList<String>();
					uldNumbers.add(containerVO.getContainerNumber());
					uLDDefaultsProxy.validateULDsForOperation(flightDetails);
				}
			}
		}
		return containerVO;
	}

	private void validateContainerAssignment(String airportCode, ContainerVO containerVO)
			throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "validateContainerAssignment" + " Entering");
		ContainerAssignmentVO latestContainerAssignmentVO = findLatestContainerAssignment(
				containerVO.getContainerNumber());
		if (latestContainerAssignmentVO != null) {
			if ((airportCode.equals(latestContainerAssignmentVO.getAirportCode())
					&& MailConstantsVO.FLAG_NO.equals(latestContainerAssignmentVO.getReleasedFlag()))
					|| (airportCode.equals(latestContainerAssignmentVO.getAirportCode())
					&& MailConstantsVO.DESTN_FLT == latestContainerAssignmentVO.getFlightSequenceNumber()
					&& String.valueOf(MailConstantsVO.DESTN_FLT)
					.equals(latestContainerAssignmentVO.getFlightNumber()))
					|| (MailConstantsVO.BULK_TYPE.equals(containerVO.getType()))
					|| (airportCode.equals(latestContainerAssignmentVO.getAirportCode())
					&& MailConstantsVO.BULK_TYPE.equals(latestContainerAssignmentVO.getContainerType())
					&& MailConstantsVO.FLAG_YES.equals(latestContainerAssignmentVO.getTransitFlag()))) {
				ContainerAssignmentVO containerAssignmentVO = findContainerAssignment(containerVO);
				if (containerAssignmentVO != null) {
					if (containerAssignmentVO.getFlightSequenceNumber() > 0) {
						validateContainerForFlight(containerAssignmentVO, containerVO);
					} else {
						if (CONTAINER_ASSIGNEDFORFLIGHT.equals(containerVO.getAssignmentFlag())) {
							if (containerVO.getFinalDestination()
									.equalsIgnoreCase(containerAssignmentVO.getDestination())) {
								throw new ContainerAssignmentException(
										ContainerAssignmentException.CARRIER_CONTAINER_REASSIGN,
										new Object[] { containerAssignmentVO.getContainerNumber(),
												containerAssignmentVO.getCarrierCode(), containerAssignmentVO });
							} else {
								throw new ContainerAssignmentException(
										ContainerAssignmentException.FINALDESTINATION_POU_INVALID);
							}
						} else {
							throw new ContainerAssignmentException(
									ContainerAssignmentException.CARRIER_ALREADY_ASSIGNED);
						}
					}
				} else {
					if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
						if (latestContainerAssignmentVO.getFlightSequenceNumber() > 0
								&& MailConstantsVO.ULD_TYPE.equals(latestContainerAssignmentVO.getContainerType())
								&& MailConstantsVO.FLAG_NO.equals(latestContainerAssignmentVO.getReleasedFlag())) {
							//TODO: Below code to be corrected in Neo - refer classic
							throw new ContainerAssignmentException(
									ContainerAssignmentException.ULD_NOT_RELEASED_FROM_INB_FLIGHT,
									new Object[] { latestContainerAssignmentVO.getCarrierCode(),
											latestContainerAssignmentVO.getFlightNumber(),
											latestContainerAssignmentVO.getFlightDate() != null ?
													latestContainerAssignmentVO.getFlightDate()
															.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD) : " ",
											latestContainerAssignmentVO.getAirportCode(),
											containerVO.getFlightNumber(),
											latestContainerAssignmentVO});
						}
					}
				}
			} else {
				if (latestContainerAssignmentVO.getFlightSequenceNumber() > 0) {
					if (MailConstantsVO.FLAG_YES.equals(latestContainerAssignmentVO.getReleasedFlag())
							|| MailConstantsVO.FLAG_NO.equals(latestContainerAssignmentVO.getTransitFlag())) {
					} else {
						if (MailConstantsVO.ULD_TYPE.equals(latestContainerAssignmentVO.getContainerType())) {
							if (MailConstantsVO.FLIGHT_STATUS_OPEN
									.equals(latestContainerAssignmentVO.getFlightStatus())) {
								//TODO : Below code to be corrected... refer classic
								throw new ContainerAssignmentException(
										ContainerAssignmentException.ULD_NOT_RELEASED_FROM_INB_FLIGHT,
										new Object[] { latestContainerAssignmentVO.getCarrierCode(),
												latestContainerAssignmentVO.getFlightNumber(),
												latestContainerAssignmentVO.getFlightDate() != null
														? latestContainerAssignmentVO.getFlightDate()
														.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
														: " ",
												latestContainerAssignmentVO.getAirportCode(),
												containerVO.getFlightNumber(), latestContainerAssignmentVO });
							} else {
								//TODO : Below code to be corrected- refer clasic
								throw new ContainerAssignmentException(
										ContainerAssignmentException.ULD_NOT_RELEASED_FROM_INB_FLIGHT,
										new Object[] { latestContainerAssignmentVO.getCarrierCode(),
												latestContainerAssignmentVO.getFlightNumber(),
												latestContainerAssignmentVO.getFlightDate() != null
														? latestContainerAssignmentVO.getFlightDate()
														.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
														: " ",
												latestContainerAssignmentVO.getPou(), containerVO.getFlightNumber(),
												latestContainerAssignmentVO });
							}
						}
					}
				} else {
					boolean canReuseContainer = false;
					if (MailConstantsVO.DESTN_FLT_STR.equals(latestContainerAssignmentVO.getFlightNumber())
							&& MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
						canReuseContainer = canReuseEmptyContainer(latestContainerAssignmentVO);
					}
					if (canReuseContainer) {
						if (containerVO.isDeleteEmptyContainer()) {
							deleteCarrierEmptyContainer(latestContainerAssignmentVO);
						}
						return;
					}
					throw new ContainerAssignmentException(
							ContainerAssignmentException.ULD_ALREADY_IN_USE_AT_ANOTHER_PORT,
							new Object[] { latestContainerAssignmentVO.getAirportCode() });
				}
			}
		}
		log.debug(CLASS + " : " + "validateContainerAssignment" + " Exiting");
	}

	/** 
	* @param containerAssignmentVO
	* @param containerVO
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @author a-1936 This method is used to check whether the Container alreadyassigned to a Fight and we are trying to assign currently are same if same throw exception else return true for other validations like the status of the Flight to be checked.
	*/
	private void validateContainerForFlight(ContainerAssignmentVO containerAssignmentVO, ContainerVO containerVO)
			throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "validateFlightForContainer" + " Entering");
		if (CONTAINER_ASSIGNEDFORFLIGHT.equals(containerVO.getAssignmentFlag())) {
			if (containerAssignmentVO.getFlightNumber().equals(containerVO.getFlightNumber())
					&& containerAssignmentVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
					&& containerAssignmentVO.getCarrierId() == containerVO.getCarrierId()
					&& containerAssignmentVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()) {
				log.debug("The Container is already assigned to the Same Flight");
				log.debug("Container Assigned to SameFlightException is Thrown");
				if (containerAssignmentVO.getContainerNumber().equalsIgnoreCase(containerVO.getContainerNumber())
						&& containerAssignmentVO.getContainerType().equalsIgnoreCase(containerVO.getType())) {
					throw new ContainerAssignmentException(ContainerAssignmentException.ALREADY_ASSIGNED_TOSAMEFLIGHT);
				} else {
					throw new ContainerAssignmentException(ContainerAssignmentException.DIFF_CONTAINER_TYPE);
				}
			} else {
				if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
					if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())) {
						//TODO : Below code to be corrected- refer clasic
						throw new ContainerAssignmentException(ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
								new Object[] { new StringBuilder().append(containerAssignmentVO.getCarrierCode())
										.append(" ").append(containerAssignmentVO.getFlightNumber()).append(" ")
										.append(containerAssignmentVO.getFlightDate() != null
												? containerAssignmentVO.getFlightDate().format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
												: " ")
										.toString(), containerAssignmentVO });
					}
				} else {
					//TODO : Below code to be corrected- refer clasic
					throw new ContainerAssignmentException(ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
							new Object[] { new StringBuilder().append(containerAssignmentVO.getCarrierCode())
									.append(" ").append(containerAssignmentVO.getFlightNumber()).append(" ")
									.append(containerAssignmentVO.getFlightDate() != null
											? containerAssignmentVO.getFlightDate().format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
											: " ")
									.toString(), containerAssignmentVO });
				}
			}
		} else {
			if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
				if (MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getTransitFlag())) {
					//TODO : Below code to be corrected- refer clasic
					throw new ContainerAssignmentException(ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
							new Object[] { new StringBuilder().append(containerAssignmentVO.getCarrierCode())
									.append(" ").append(containerAssignmentVO.getFlightNumber()).append(" ")
									.append(containerAssignmentVO.getFlightDate() != null
											? containerAssignmentVO.getFlightDate().format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
											: " ")
									.toString(), containerAssignmentVO });
				}
			} else {
				//TODO : Below code to be corrected- refer clasic
				throw new ContainerAssignmentException(ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
						new Object[] { new StringBuilder().append(containerAssignmentVO.getCarrierCode()).append(" ")
								.append(containerAssignmentVO.getFlightNumber()).append(" ")
								.append(containerAssignmentVO.getFlightDate() != null
										? containerAssignmentVO.getFlightDate().format(MailConstantsVO.DATE_TIME_FORMATTER_YYYY_MM_DD)
										: " ")
								.toString(), containerAssignmentVO });
			}
		}
	}

	/** 
	* @param containerVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws InvalidFlightSegmentException
	* @author a-1936 This method is used to validate the container if intendedFor Flight
	*/
	private int findContainerSegment(ContainerVO containerVO) throws InvalidFlightSegmentException {
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		int segmentSerialNumber = 0;
		String pol = containerVO.getAssignedPort();
		String pou = containerVO.getPou();
		log.info("Validate the Container For the Flight");
		segmentSummaryVos = flightOperationsProxy.findFlightSegments(containerVO.getCompanyCode(),
				containerVO.getCarrierId(), containerVO.getFlightNumber(), (int) containerVO.getFlightSequenceNumber());
		log.info("" + "segmentSummaryVos" + " " + segmentSummaryVos);
		log.info("" + "pol = " + " " + pol + " " + "pou = " + " " + pou);
		if (segmentSummaryVos != null && segmentSummaryVos.size() > 0) {
			for (FlightSegmentSummaryVO segmentVo : segmentSummaryVos) {
				if (segmentVo.getSegmentOrigin() != null && segmentVo.getSegmentDestination() != null) {
					if (segmentVo.getSegmentOrigin().equals(pol) && segmentVo.getSegmentDestination().equals(pou)) {
						segmentSerialNumber = segmentVo.getSegmentSerialNumber();
						log.debug("" + "THE POL IS" + " " + pol);
						log.debug("" + "THE POU IS" + " " + pou);
						log.debug("" + "THE SEGSERNUM FOR THE POL-POU" + " " + segmentSerialNumber);
						break;
					}
				}
			}
		}
		if (segmentSerialNumber == 0) {
			throw new InvalidFlightSegmentException(
					new String[] { new StringBuilder().append(pou).append("-").append(pol).toString() });
		}
		return segmentSerialNumber;
	}

	/** 
	* @param companyCode
	* @param paCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @author A-2037 This method is used to find Postal Administration CodeDetails
	*/
	public PostalAdministrationVO findPACode(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPACode" + " Entering");
		MailOperationsMapper mailOperationsMapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		MailTrackingDefaultsBI mailMasterApi = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
	    return mailOperationsMapper.postalAdministrationModelToPostalAdministrationVO(
                mailMasterApi.findPACode(companyCode, paCode));

	}

	/** 
	* @param companyCode
	* @param carrierCode
	* @return AirlineValidationVO
	* @author a-2553
	*/
	public AirlineValidationVO findAirlineDescription(String companyCode, String carrierCode)
			throws SharedProxyException {
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		try {
			airlineValidationVO = neoMastersServiceUtils.validateAlphaCode(companyCode, carrierCode);
		} catch (BusinessException e) {
			return airlineValidationVO;
		}

		return airlineValidationVO;
	}

//TODO: Neo to check below code- refer classic
	//	/**
//	* overriding compare method and passing objects by reference Added for ICRD-197379
//	* @author a-7540
//	* @return
//	*/
//	class DestinationExchangeOfficeComparator implements Comparator {
//	}


	public TransferManifestVO transferMail(Collection<DespatchDetailsVO> despatchDetailsVOs,
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO, String toPrintTransferManifest)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException,
			MailOperationsBusinessException {
		log.debug(CLASS + " : " + "transferMail" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<MailbagVO> mailbagVOsToTransfer = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsToTransferInSameCarrier = new ArrayList<MailbagVO>();
		TransferManifestVO transferManifestVO = null;
		Collection<DespatchDetailsVO> despatchDetailsVOsToTransfer = new ArrayList<DespatchDetailsVO>();
		if (CollectionUtils.isNotEmpty(mailbagVOs)) {
			for (MailbagVO mailVO : mailbagVOs) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO = mailOperationsMapper.copyMailbagVO(mailVO);
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.findMailbag(constructMailbagPK(mailbagVO));
				} catch (FinderException e) {
					mailbag = null;
				}
				if (mailbag != null
						&& ("MTK009".equals(mailVO.getMailSource()) || "MAIL ENQ".equals(mailVO.getMailSource()))
						&& mailbag.getScannedPort() != null
						&& !mailbag.getScannedPort().equals(logonAttributes.getAirportCode())) {
					throw new MailOperationsBusinessException(
							MailOperationsBusinessException.MAILTRACKING_MAILBAGNOTAVAILABLE);
				} else {
					if (mailbag != null && (LIST_TRANSFER_MANIFEST_SCREENID.equals(mailVO.getMailSource())
							|| TRANSFER_END_FROM_OPS.equals(mailVO.getMailSource()))) {
						if (mailbagVOsToTransferInSameCarrier.stream()
								.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
							mailbagVOsToTransferInSameCarrier.add(mailbagVO);
						}
					} else if ("TRA_OUT"
							.equals(checkForMailBagTransferStatus(mailbagVO, toContainerVO, logonAttributes))
							&& ("MLD".equals(mailVO.getMailSource())
									|| MailConstantsVO.WS.equals(mailVO.getMailSource())
									|| mailOperationsTransferTransaction())) {
						if (mailbagVOsToTransfer.stream()
								.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
							mailbagVOsToTransfer.add(mailbagVO);
						}
						if (mailbagVOsToTransferInSameCarrier.stream()
								.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
							mailbagVOsToTransferInSameCarrier.add(mailbagVO);
						}
						toContainerVO.setTransStatus(true);
					} else if ("TRA_OUT"
							.equals(checkForMailBagTransferStatus(mailbagVO, toContainerVO, logonAttributes))) {
						if (mailbagVOsToTransfer.stream()
								.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
							mailbagVOsToTransfer.add(mailbagVO);
						}
					} else {
						if (mailbagVOsToTransferInSameCarrier.stream()
								.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
							mailbagVOsToTransferInSameCarrier.add(mailbagVO);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(mailbagVOsToTransferInSameCarrier)) {
				try {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.transferMailbags(mailbagVOsToTransferInSameCarrier, toContainerVO);
				} finally {
				}
			}
		}
		if (CollectionUtils.isNotEmpty(despatchDetailsVOs)) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				DespatchDetailsVO dsespatchDtlsVO = new DespatchDetailsVO();
				dsespatchDtlsVO = mailOperationsMapper.copyDespatchDetailsVO(despatchDetailsVO);
				despatchDetailsVOsToTransfer.add(dsespatchDtlsVO);
			}
		}
		if ((MailConstantsVO.FLAG_YES.equals(toPrintTransferManifest)
				|| MailConstantsVO.FLAG_SCANNED.equals(toPrintTransferManifest))
				&& ((CollectionUtils.isNotEmpty(mailbagVOsToTransfer))
						|| (CollectionUtils.isNotEmpty(despatchDetailsVOsToTransfer)))) {
			Map<String, Collection<MailbagVO>> groupedMailbagDetails = new HashMap<String, Collection<MailbagVO>>();
			Map<String, Collection<DespatchDetailsVO>> groupedDespatchDetailsVO = new HashMap<String, Collection<DespatchDetailsVO>>();
			Set<String> flightKeySet = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(mailbagVOsToTransfer)) {
				String flightKey = null;
				for (MailbagVO mailVO : mailbagVOsToTransfer) {

					flightKey = new StringBuilder().append(mailVO.getCarrierCode()).append(ID_SEP)
							.append(mailVO.getFlightNumber()).append(ID_SEP)
							.append(mailVO.getFlightDate() != null ? mailVO.getFlightDate().toString()
									: "")
							.append(ID_SEP)
							.append(mailVO.getFlightSequenceNumber() > 0 ? mailVO.getFlightSequenceNumber() : "")
							.append(ID_SEP)
							.append(mailVO.getSegmentSerialNumber() > 0 ? mailVO.getSegmentSerialNumber() : "")
							.toString();
					if (!groupedMailbagDetails.containsKey(flightKey)) {
						flightKeySet.add(flightKey);
						Collection<MailbagVO> mailCollection = new ArrayList<MailbagVO>();
						mailCollection.add(mailVO);
						groupedMailbagDetails.put(flightKey, mailCollection);
					} else {
						Collection<MailbagVO> mailbagCollection = groupedMailbagDetails.get(flightKey);
						mailbagCollection.add(mailVO);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(despatchDetailsVOsToTransfer)) {
				String flightKey = null;

				for (DespatchDetailsVO despatchDtlsVO : despatchDetailsVOsToTransfer) {
					flightKey = new StringBuilder().append(despatchDtlsVO.getCarrierCode()).append(ID_SEP)
							.append(despatchDtlsVO.getFlightNumber()).append(ID_SEP)
							.append(despatchDtlsVO.getFlightDate()).toString();
					if (!groupedDespatchDetailsVO.containsKey(flightKey)) {
						flightKeySet.add(flightKey);
						Collection<DespatchDetailsVO> despatchCollection = new ArrayList<DespatchDetailsVO>();
						despatchCollection.add(despatchDtlsVO);
						groupedDespatchDetailsVO.put(flightKey, despatchCollection);
					} else {
						Collection<DespatchDetailsVO> despatchDtlsCollection = groupedDespatchDetailsVO.get(flightKey);
						despatchDtlsCollection.add(despatchDtlsVO);
					}
				}
			}
			if (flightKeySet != null && flightKeySet.size() > 0) {
				for (String flightKey : flightKeySet) {
					String[] keys = flightKey.split(ID_SEP);
					String carrierCode = null;
					String flightNumber = null;
					String flightDate = null;
					String flightSeqNum = null;
					String segSerNum = null;
					if (keys != null && keys.length > 0) {
						carrierCode = keys[0];
						flightNumber = keys[1];
						if (keys.length > 2) {
							flightDate = keys[2];
							flightSeqNum = keys[3];
							if (keys.length > 4) {
								segSerNum = keys[4];
							}
						}
						toContainerVO.setFromCarrier(carrierCode);
						toContainerVO.setFromFltNum(flightNumber);
						toContainerVO.setFrmFltSeqNum(flightSeqNum != null ? Long.parseLong(flightSeqNum) : -1);
						toContainerVO.setFrmSegSerNum(segSerNum != null ? Integer.parseInt(segSerNum) : -1);
						if (flightDate != null && flightDate.trim().length() > 0) {
							//toContainerVO.setFromFltDat(ZonedDateTime.from(localDateUtil.getStationDateTimeFromUTC(flightDate,null,null)));
							try{
							toContainerVO.setFromFltDat(java.time.LocalDate.parse(flightDate,DateTimeFormatter
									.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS_DD_MMM_YYYYY, Locale.ENGLISH)).atStartOfDay(ZoneId.systemDefault()));
							}
							catch(Exception e){
								toContainerVO.setFromFltDat(java.time.LocalDate.parse(flightDate.substring(0,10),DateTimeFormatter
										.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS, Locale.ENGLISH)).atStartOfDay(ZoneId.systemDefault()));
							}

						}
					}
					Collection<MailbagVO> mailbags = null;
					Collection<DespatchDetailsVO> despatchDetails = null;
					if (groupedMailbagDetails != null && groupedMailbagDetails.size() > 0) {
						mailbags = groupedMailbagDetails.get(flightKey);
					}
					if (groupedDespatchDetailsVO != null && groupedDespatchDetailsVO.size() > 0) {
						despatchDetails = groupedDespatchDetailsVO.get(flightKey);
					}
					boolean isPrintingNeeded = true;
					if (MailConstantsVO.FLAG_SCANNED.equals(toPrintTransferManifest)) {
						isPrintingNeeded = false;
					}
					if (mailbags != null && !mailbags.isEmpty()) {
						for (MailbagVO mailbagVO : mailbags) {
							if (mailbagVO.getMailSequenceNumber() > 0) {
								String transferManifestId = null;
								try {
									transferManifestId = constructDAO().findTransferManifestId(
											mailbagVO.getCompanyCode(), mailbagVO.getMailSequenceNumber());
								} catch (PersistenceException e) {
									e.getMessage();
								}
								if (transferManifestId != null && transferManifestId.trim().length() > 0) {
									TransferManifestDSN transferManifestDSN = null;
									TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
									transferManifestDSNPK.setCompanyCode(mailbagVO.getCompanyCode());
									transferManifestDSNPK.setTransferManifestId(transferManifestId);
									transferManifestDSNPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
									try {
										transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
									} catch (FinderException e) {
										e.getMessage();
									}
									if (transferManifestDSN != null) {
										transferManifestDSN.setTransferStatus(TRANSER_STATUS_REJECT);
									}
								}
							}
						}
					}
					transferManifestVO = generateTransferManifest(despatchDetails, mailbags, toContainerVO,
							isPrintingNeeded);
					try {
						PersistenceController.getEntityManager().flush();
					} catch (PersistenceException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					MailOperationsMapper mapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
					if (!mailOperationsTransferTransaction()) {
						mailController.flagHistoryforTransferInitiation(mapper.copyMailbagVOS(mailbags), getTriggerPoint());
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(mailbagVOs)) {
			for (MailbagVO mailVO : mailbagVOs) {
				if ("TRA_OUT".equals(checkForMailBagTransferStatus(mailVO, toContainerVO, logonAttributes))
						&& ("MLD".equals(mailVO.getMailSource()) || MailConstantsVO.WS.equals(mailVO.getMailSource())
								|| TRANSFER_END_FROM_OPS.equals(mailVO.getMailSource())
								|| mailOperationsTransferTransaction())) {
					String transferManifestId = null;
					try {
						transferManifestId = constructDAO().findTransferManifestId(mailVO.getCompanyCode(),
								mailVO.getMailSequenceNumber());
					} catch (PersistenceException e) {
						e.getMessage();
					}
					if (transferManifestId != null && transferManifestId.trim().length() > 0) {
						TransferManifestDSN transferManifestDSN = null;
						TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
						transferManifestDSNPK.setCompanyCode(mailVO.getCompanyCode());
						transferManifestDSNPK.setTransferManifestId(transferManifestId);
						transferManifestDSNPK.setMailSequenceNumber(mailVO.getMailSequenceNumber());
						try {
							transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
						} catch (FinderException e) {
							e.getMessage();
						}
						if (transferManifestDSN != null) {
							transferManifestDSN.setTransferStatus("TRFEND");
						}
					}
					TransferManifestPK transferManifestPK = new TransferManifestPK();
					transferManifestPK.setCompanyCode(mailVO.getCompanyCode());
					transferManifestPK.setTransferManifestId(transferManifestId);
					TransferManifest trasferManifest = new TransferManifest();
					try {
						trasferManifest = TransferManifest.find(transferManifestPK);
					} catch (FinderException e) {
						log.info(e.getMessage());
					}
					trasferManifest.setTransferStatus("TRFEND");
					ZonedDateTime trfDate = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
					trasferManifest.setTransferDate(trfDate);
				}
			}
		}
		log.debug(CLASS + " : " + "transferMail" + " Exiting");
		return transferManifestVO;
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public void transferMailbags(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		log.debug(CLASS + " : " + "transferMailbags" + " Entering");
		mailTransfer.transferMailbags(mailbagVOs, containerVO);
		if (containerVO.getContainerNumber() != null) {
			OperationalFlightVO toFlightVO = constructOperationalFlightVO(containerVO);
			Collection<ContainerVO> containerVOs = new ArrayList<>();
			containerVOs.add(containerVO);
			calculateContentID(containerVOs, toFlightVO);
			updateContainerAcceptance(containerVO, mailbagVOs);
		}
		log.debug(CLASS + " : " + "transferMailbags" + " Exiting");
	}

	private void updateContainerAcceptance(ContainerVO toContainerVO, Collection<MailbagVO> mailbagVOs) throws SystemException{
		log.debug(CLASS + " : " + "updateContainerAcceptance" + " Entering");
		LoginProfile logon = contextUtil.callerLoginProfile();
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toContainerVO.getCompanyCode());
		containerPk.setContainerNumber(toContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(toContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toContainerVO.getFlightNumber());
		containerPk.setCarrierId(toContainerVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toContainerVO.getLegSerialNumber());
		Container container = null;
		try {
			container = Container.find(containerPk);
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			if (MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())
					&& MailConstantsVO.FLAG_NO.equals(container.getAcceptanceFlag())) {
				Collection<ContainerVO> containers = new ArrayList<ContainerVO>();
				containers.add(toContainerVO);
				Collection<ContainerDetailsVO> conts = constructConDetailsVOsForResdit(containers);
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagResditsForULDAcceptance(conts, toContainerVO.getAssignedPort());
			}
		}
		String syncActualWeightToDWS = findSystemParameterValue(
				MailConstantsVO.SYNC_ACTUAL_WEIGHT_TO_DWS_FUNCTION_POINTS);
		if (isNotNullAndEmpty(syncActualWeightToDWS)
				&& syncActualWeightToDWS.contains(MailConstantsVO.MAIL_STATUS_TRANSFERRED)) {
			Quantity actualWeight = getContainerActualWeight(toContainerVO, mailbagVOs, container.getActualWeight());
			if (actualWeight != null) {
				container.setActualWeight(actualWeight.getValue().doubleValue());
				container.setActualWeightDisplayValue(actualWeight.getDisplayValue().doubleValue());
				container.setActualWeightDisplayUnit(actualWeight.getDisplayUnit().getName());
				toContainerVO.setActualWeight(actualWeight);
			}
		}
		container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		if (logon.getOwnAirlineIdentifier() != container.getCarrierId()
				&& MailConstantsVO.DESTN_FLT_STR.equals(container.getFlightNumber())) {
			container.setTransitFlag(MailConstantsVO.FLAG_NO);
		}
		if (container.getFirstMalbagAsgDat() == null) {
			container.setFirstMalbagAsgDat(localDateUtil.getLocalDate(
					toContainerVO.getAssignedPort(), true).toLocalDateTime());
		}
		container.setContentId(toContainerVO.getContentId());
		collectContainerAuditDetails(container,toContainerVO);
		log.debug(CLASS + " : " + "updateContainerAcceptance" + " Exiting");
	}

	/**
	* @param toContainerVO
	* @throws SystemException
	* @author A-3227 RENO K ABRAHAM
	*/
	private TransferManifestVO generateTransferManifest(Collection<DespatchDetailsVO> despatchDetailsVOs,
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO, boolean isPrintingNeeded) {
		log.debug(CLASS + " : " + "generateTransferManifest" + " Entering");
		String id = new StringBuilder().append(toContainerVO.getOwnAirlineCode()).append(toContainerVO.getCarrierCode())
				.toString();
		int transferManifestId = generateTransferManifestSeqNumber(toContainerVO.getCompanyCode(), id);
		log.debug("" + "!!!!!!!!!!!!!!!11id" + " " + id);
		log.debug("" + "!!!!!!!!!!!11transferManifestId" + " " + transferManifestId);
		TransferManifestVO transferManifestVO = new TransferManifestVO();
		transferManifestVO.setCompanyCode(toContainerVO.getCompanyCode());
		transferManifestVO.setAirPort(toContainerVO.getAssignedPort());
		transferManifestVO.setTransferManifestId(
				new StringBuilder().append(id).append(String.valueOf(transferManifestId)).toString());
		log.debug("" + "TransferManifestId--->>>" + " " + transferManifestVO.getTransferManifestId());
		transferManifestVO.setTransferredFromCarCode(toContainerVO.getFromCarrier());
		transferManifestVO.setTransferredFromFltNum(toContainerVO.getFromFltNum());
		transferManifestVO.setToFltDat(toContainerVO.getFlightDate());
		transferManifestVO.setFromFltDat(toContainerVO.getFromFltDat());
		ZonedDateTime trfDate = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
		transferManifestVO.setTransferredDate(trfDate);
		transferManifestVO.setTransferredToCarrierCode(toContainerVO.getCarrierCode());
		transferManifestVO.setTransferredToFltNumber(toContainerVO.getFlightNumber());
		transferManifestVO.setTransferredfrmFltSeqNum(toContainerVO.getFrmFltSeqNum());
		transferManifestVO.setTransferredfrmSegSerNum(toContainerVO.getFrmSegSerNum());
		Collection<DSNVO> dsns = makeDSNVOs(mailbagVOs, despatchDetailsVOs, toContainerVO);
		transferManifestVO.setDsnVOs(dsns);
		log.debug("" + "transferManifestVO--->>>" + " " + transferManifestVO);
		if (toContainerVO.isTransStatus()) {
			transferManifestVO.setStatus(MAIL_OPS_TRAEND);
		}
		new TransferManifest(transferManifestVO);
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = findAirlineDescription(toContainerVO.getCompanyCode(),
					toContainerVO.getCarrierCode());
		} catch (SharedProxyException e) {
			e.getMessage();
		}
		transferManifestVO.setToCarCodeDesc(airlineValidationVO.getAirlineName());
		try {
			airlineValidationVO = findAirlineDescription(toContainerVO.getCompanyCode(),
					toContainerVO.getFromCarrier());
		} catch (SharedProxyException e) {
			e.getMessage();
		}
		transferManifestVO.setFromCarCodeDesc(airlineValidationVO.getAirlineName());
		log.debug(CLASS + " : " + "generateTransferManifest" + " Exiting");
		return transferManifestVO;
	}

	/** 
	* @param mailbagVOs
	* @param despatchDetailsVOs
	* @param toContainerVO
	* @return Collection<ContainerDetailsVO>
	* @author a-2553 Mehtod to make DSN VOs
	*/
	private Collection<DSNVO> makeDSNVOs(Collection<MailbagVO> mailbagVOs,
			Collection<DespatchDetailsVO> despatchDetailsVOs, ContainerVO toContainerVO) {
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
			for (DespatchDetailsVO despatchVO : despatchDetailsVOs) {
				int numBags = 0;
				double bagWgt = 0;
				int stdNumBags = 0;
				double stdBagWgt = 0;
				String outerpk = despatchVO.getOriginOfficeOfExchange() + despatchVO.getDestinationOfficeOfExchange()
						+ despatchVO.getMailCategoryCode() + despatchVO.getMailSubclass() + despatchVO.getDsn()
						+ despatchVO.getYear();
				if (dsnMap.get(outerpk) == null) {
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(toContainerVO.getCompanyCode());
					dsnVO.setDsn(despatchVO.getDsn());
					dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
					dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
					dsnVO.setMailClass(despatchVO.getMailClass());
					dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
					dsnVO.setMailSubclass(despatchVO.getMailSubclass());
					dsnVO.setYear(despatchVO.getYear());
					dsnVO.setPltEnableFlag(DSNVO.FLAG_NO);
					for (DespatchDetailsVO innerVO : despatchDetailsVOs) {
						String innerpk = innerVO.getOriginOfficeOfExchange() + innerVO.getDestinationOfficeOfExchange()
								+ innerVO.getMailCategoryCode() + innerVO.getMailSubclass() + innerVO.getDsn()
								+ innerVO.getYear();
						if (outerpk.equals(innerpk)) {
							//TODO: Neo to check below code- refer classic
							numBags = numBags + innerVO.getAcceptedBags();
							if (innerVO.getAcceptedWeight() != null) {
								bagWgt = bagWgt + innerVO.getAcceptedWeight().getRoundedValue().doubleValue();
							}
							stdNumBags = stdNumBags + innerVO.getStatedBags();
							if (innerVO.getStatedWeight() != null) {
								stdBagWgt = stdBagWgt + innerVO.getStatedWeight().getRoundedValue().doubleValue();
							}
						}
					}
					dsnVO.setBags(numBags);
					dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(bagWgt)));
					dsnVO.setStatedBags(stdNumBags);
					dsnVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(stdBagWgt)));
					dsnMap.put(outerpk, dsnVO);
					numBags = 0;
					bagWgt = 0;
					stdNumBags = 0;
					stdBagWgt = 0;
				}
			}
		}
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				DSNVO dsnVO = new DSNVO();
				dsnVO.setCompanyCode(toContainerVO.getCompanyCode());
				dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
				dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
				dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
				dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0, 1));
				dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
				dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
				dsnVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				dsnVO.setYear(mailbagVO.getYear());
				dsnVO.setContainerNumber(toContainerVO.getContainerNumber());
				dsnVO.setPltEnableFlag(DSNVO.FLAG_YES);
				dsnVO.setBags(1);
				dsnVO.setWeight(mailbagVO.getWeight());
				newDSNVOs.add(dsnVO);
			}
			return newDSNVOs;
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		for (String key : dsnMap.keySet()) {
			DSNVO dsnVO = dsnMap.get(key);
			newDSNVOs.add(dsnVO);
		}
		return newDSNVOs;
	}

	/** 
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	private int generateTransferManifestSeqNumber(String companyCode, String id) {
		log.debug(CLASS + " : " + "generateSerialNumber" + " Entering");

			KeyCondition keyCondition =  new KeyCondition();
			keyCondition.setKey(TRANSFER_MANIFEST_KEY);
			keyCondition.setValue(id);
			Criterion criterion = new CriterionBuilder()
					.withSequence(TRANSFER_MANIFEST_KEY)
					.withKeyCondition(keyCondition)
					.withPrefix("").build();
			KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
		return Integer.parseInt((keyUtils.getKey(criterion)));
	}

	/** 
	* @param containerVOs
	* @param operationalFlightVO
	* @param printFlag
	* @return
	* @throws SystemException
	* @throws ContainerAssignmentException
	* @throws InvalidFlightSegmentException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	*/
	public TransferManifestVO transferContainers(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO, String printFlag)
			throws ContainerAssignmentException, InvalidFlightSegmentException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException {
		log.debug(CLASS + " : " + "transferContainers" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsCollection = null;
		ContainerVO containerVo = null;
		TransferManifestVO transferManifestVO = new TransferManifestVO();
		Map<String, Object> transferOutMap = null;
		Map<String, Object> contTransferMap = null;
		if (CollectionUtils.isNotEmpty(containerVOs)) {
			containerVo = new ArrayList<ContainerVO>(containerVOs).get(0);
		}
		validateContainersForTransfer(containerVOs, operationalFlightVO);
		if (checkForContainerTransferOutStatus(operationalFlightVO.getCarrierCode())
				&& (mailOperationsTransferTransaction())) {
			operationalFlightVO.setTransferStatus(true);
			transferManifestVO.setStatus(MAIL_OPS_TRAEND);
			mailTransfer.saveArrivalBeforeTransferOut(containerVOs, operationalFlightVO);
			transferOutMap = mailTransfer.transferContainers(containerVOs, operationalFlightVO, printFlag);
			contTransferMap = transferOutMap;
		} else if (checkForContainerTransferOutStatus(operationalFlightVO.getCarrierCode())) {
			transferOutMap = mailTransfer.saveArrivalBeforeTransferOut(containerVOs, operationalFlightVO);
		} else {
			contTransferMap = mailTransfer.transferContainers(containerVOs, operationalFlightVO, printFlag);
		}
		if (Objects.nonNull(contTransferMap)) {
			saveScreeningConsginorDetails(contTransferMap);
		}
		AirlineValidationVO fromAirlineValidationVo = null;
		AirlineValidationVO toAirlineValidationVo = null;
		if (transferOutMap != null && transferOutMap.get(MailConstantsVO.CONST_CONTAINER_DETAILS) != null) {
			containerDetailsCollection = (Collection<ContainerDetailsVO>) transferOutMap
					.get(MailConstantsVO.CONST_CONTAINER_DETAILS);
			transferManifestVO.setDsnVOs(new ArrayList<DSNVO>());
			for (ContainerDetailsVO container : containerDetailsCollection) {
				if (container.getDsnVOs() != null && !container.getDsnVOs().isEmpty()) {
					transferManifestVO.getDsnVOs().addAll(container.getDsnVOs());
					for (DSNVO dsnVO : container.getDsnVOs()) {
						if (dsnVO.getMailSequenceNumber() > 0) {
							String transferManifestId = null;
							try {
								transferManifestId = constructDAO().findTransferManifestId(dsnVO.getCompanyCode(),
										dsnVO.getMailSequenceNumber());
							} catch (PersistenceException e) {
								e.getMessage();
							}
							if (transferManifestId != null && transferManifestId.trim().length() > 0) {
								TransferManifestDSN transferManifestDSN = null;
								TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
								transferManifestDSNPK.setCompanyCode(dsnVO.getCompanyCode());
								transferManifestDSNPK.setTransferManifestId(transferManifestId);
								transferManifestDSNPK.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
								try {
									transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
								} catch (FinderException e) {
									e.getMessage();
								}
								if (transferManifestDSN != null) {
									transferManifestDSN.setTransferStatus(TRANSER_STATUS_REJECT);
								}
							}
						}
					}
				}
			}
			transferManifestVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			transferManifestVO.setAirPort(operationalFlightVO.getPol());
			String key = new StringBuilder(operationalFlightVO.getOwnAirlineCode())
					.append(operationalFlightVO.getCarrierCode()).toString();
			int transferManifestId = generateTransferManifestSeqNumber(operationalFlightVO.getCompanyCode(), key);
			transferManifestVO.setTransferManifestId(key + String.valueOf(transferManifestId));
			if (containerVo.getCarrierCode() != null) {
				transferManifestVO.setTransferredFromCarCode(containerVo.getCarrierCode());
			} else {
				if (containerVo.getCarrierId() > 0) {
					AirlineValidationVO airlineValidationVO = null;
					try {
						airlineValidationVO = sharedAirlineProxy.findAirline(containerVo.getCompanyCode(),
								containerVo.getCarrierId());
					} catch (SharedProxyException sharedProxyException) {
						log.info("Exception :", sharedProxyException);
						sharedProxyException.getMessage();
					}
					transferManifestVO.setTransferredFromCarCode(airlineValidationVO.getAlphaCode());
				} else {
					transferManifestVO.setTransferredFromCarCode(operationalFlightVO.getOwnAirlineCode());
				}
			}
			ZonedDateTime date = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
			transferManifestVO.setTransferredDate(date);
			transferManifestVO.setTransferredToCarrierCode(operationalFlightVO.getCarrierCode());
			transferManifestVO.setTransferredToFltNumber(operationalFlightVO.getFlightNumber());
			transferManifestVO.setTransferredFromFltNum(containerVo.getFlightNumber());
			if (containerVo.getFlightNumber() != null && containerVo.getFlightSequenceNumber() > 0) {
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(containerVo.getCompanyCode());
				flightFilterVO.setFlightCarrierId(containerVo.getCarrierId());
				flightFilterVO.setFlightNumber(containerVo.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
				Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
				for (FlightValidationVO flightValidation : flightValidationVOs) {
					containerVo.setFlightDate(LocalDateMapper.toZonedDateTime(flightValidation.getFlightDate()));
				}
			}
			transferManifestVO.setFromFltDat(containerVo.getFlightDate());
			transferManifestVO.setToFltDat(operationalFlightVO.getFlightDate());
			transferManifestVO.setLastUpdateUser(operationalFlightVO.getOperator());
			transferManifestVO.setLastUpdateTime(date);
			transferManifestVO.setTransferredfrmFltSeqNum(containerVo.getFlightSequenceNumber());
			transferManifestVO.setTransferredfrmSegSerNum(containerVo.getSegmentSerialNumber());
			try {
				fromAirlineValidationVo = findAirlineDescription(operationalFlightVO.getCompanyCode(),
						operationalFlightVO.getOwnAirlineCode());
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			transferManifestVO.setFromCarCodeDesc(fromAirlineValidationVo.getAirlineName());
			try {
				toAirlineValidationVo = findAirlineDescription(operationalFlightVO.getCompanyCode(),
						operationalFlightVO.getCarrierCode());
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			transferManifestVO.setToCarCodeDesc(toAirlineValidationVo.getAirlineName());
			new TransferManifest(transferManifestVO);
			for (ContainerDetailsVO container : containerDetailsCollection) {
				Collection<MailbagVO> mailbagVOs = new ArrayList<>();
				for (DSNVO dsnVO : container.getDsnVOs()) {
					MailbagPK mailbagPK = new MailbagPK();
					mailbagPK.setCompanyCode(transferManifestVO.getCompanyCode());
					mailbagPK.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
					Mailbag mailbag = new Mailbag();
					try {
						mailbag = Mailbag.find(mailbagPK);
					} catch (FinderException e) {
						log.info(e.getMessage());
					}
					MailbagVO mailbagvo = populateMailbagVofromMailbag(mailbag, transferManifestVO);
					updateTransferOutDetailsForHistory(mailbagvo, transferManifestVO);
					if (operationalFlightVO.getPou() != null) {
						mailbagvo.setPou(operationalFlightVO.getPou());
					}
					mailbagVOs.add(mailbagvo);
				}
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				MailOperationsMapper mapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
				if (!operationalFlightVO.isTransferStatus()) {
					mailController.flagHistoryforTransferInitiation(mapper.copyMailbagVOS(mailbagVOs), getTriggerPoint());
				}
			}
		}
		return transferManifestVO;
	}

	/** 
	* This method validates the container assignment at this port. Also it removes any destn assigned Containers so tht they'll be moved out of this airport Revision 2 12-Sep-2007 Oct 12, 2006, a-1739
	* @param containerVOs
	* @param operationalFlightVO
	* @throws SystemException
	* @throws ContainerAssignmentException
	*/
	private void validateContainersForTransfer(Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "validateContainersForTransfer" + " Entering");
		Collection<ContainerVO> containersToRemove = new ArrayList<ContainerVO>();
		for (ContainerVO containerVO : containerVOs) {
			ContainerAssignmentVO containerAssignmentVO = Container.findContainerAssignment(
					containerVO.getCompanyCode(), containerVO.getContainerNumber(), operationalFlightVO.getPol());
			if (containerAssignmentVO != null) {
				if (containerAssignmentVO.getFlightSequenceNumber() > 0) {
					if (operationalFlightVO.getFlightSequenceNumber() > 0) {
						if (containerAssignmentVO.getCarrierId() != operationalFlightVO.getCarrierId()
								|| containerAssignmentVO.getFlightSequenceNumber() != operationalFlightVO
										.getFlightSequenceNumber()
								|| !containerAssignmentVO.getFlightNumber()
										.equals(operationalFlightVO.getFlightNumber())) {
							if (containerAssignmentVO.getTransitFlag() != null) {
								if (!MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
									//TODO: Neo to check below code- refer classic
									throw new ContainerAssignmentException(
											ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
											new String[] { new StringBuilder()
													.append(containerAssignmentVO.getCarrierCode()).append(SPACE)
													.append(containerAssignmentVO.getFlightNumber()).append(SPACE)
													.append(containerAssignmentVO.getFlightDate())
													.toString() });
								}
							}
						}
					} else {
						if (containerAssignmentVO.getTransitFlag() != null) {
							if (!MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getTransitFlag())) {
								//TODO: Neo to check below code- refer classic
								throw new ContainerAssignmentException(
										ContainerAssignmentException.CON_ASSIGNEDTO_DIFFFLT,
										new String[] { new StringBuilder()
												.append(containerAssignmentVO.getCarrierCode()).append(SPACE)
												.append(containerAssignmentVO.getFlightNumber()).append(SPACE)
												.append(containerAssignmentVO.getFlightDate())
												.toString() });
							}
						}
					}
				} else {
					ContainerVO containerToRem = new ContainerVO();
					containerToRem = mailOperationsMapper.copyContainerVO( containerVO);
					containerToRem.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					containerToRem.setFlightNumber(containerAssignmentVO.getFlightNumber());
					containerToRem.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					containerToRem.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
					containerToRem.setPou(containerAssignmentVO.getPou());
					containerToRem.setFinalDestination(containerAssignmentVO.getDestination());
					containerToRem.setAssignedPort(containerAssignmentVO.getAirportCode());
					if (containerAssignmentVO.getFlightDate() != null) {
						containerToRem.setFlightDate(containerAssignmentVO.getFlightDate());
					}
					containersToRemove.add(containerToRem);
				}
			}
		}
		if (containersToRemove != null && containersToRemove.size() > 0) {
			log.debug("" + "containers to be removed " + " " + containersToRemove);
			new ReassignController().reassignContainerFromDestToDest(containersToRemove, null);
		}
		log.debug(CLASS + " : " + "validateContainersForTransfer" + " Exiting");
	}

	/** 
	* This method is used to find all the mail subclass codes A-2037
	* @param companyCode
	* @param officeOfExchange
	* @param pageNumber
	* @return Page of officeExchangeVOs
	* @throws SystemException
	*/
	public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode, String officeOfExchange, int pageNumber) {
		log.debug(CLASS + " : " + "findOfficeOfExchange" + " Entering");
		var officeOfEx = ContextUtil.getInstance()
				.getBean(MailTrackingDefaultsBI.class)
				.findOfficeOfExchange(companyCode,officeOfExchange,pageNumber);

        return ContextUtil.getInstance()
				.getBean(MailOperationsMapper.class)
				.officeOfExchangeModelsToOfficeOfExchangeVOs(officeOfEx);
	}

	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) {
		MailTrackingDefaultsBI mailMasterApi = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
		return mailMasterApi
				.validateCoterminusairports(actualAirport, eventAirport, eventCode, paCode, dspDate);
	}

	/** 
	* Method		:	MailController.saveConsignmentDetails Added by 	:	A-5526 on 24 Jun, 2016 for CRQ-ICRD-103713 Used for 	:	saveConsignmentDetails Parameters	:	@param consignmentDocumentVO Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void saveConsignmentDetails(ConsignmentDocumentVO consignmentDocumentVO) {
		if (consignmentDocumentVO.getMailInConsignmentVOs() != null
				&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()) {
			MailInConsignmentVO mailInConsignmentVO = consignmentDocumentVO.getMailInConsignmentVOs().iterator().next();
			consignmentDocumentVO.setOperatorOrigin(
					findUpuCodeName(mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getPaCode()));
			consignmentDocumentVO.setOperatorDestination(
					findUpuCodeName(mailInConsignmentVO.getCompanyCode(), findPAForOfficeOfExchange(
							mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getDestinationExchangeOffice())));
			consignmentDocumentVO.setOoeDescription(findOfficeOfExchangeDescription(
					mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getOriginExchangeOffice()));
			consignmentDocumentVO.setDoeDescription(findOfficeOfExchangeDescription(
					mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getDestinationExchangeOffice()));
			if (MailConstantsVO.MAIL_CATEGORY_AIR.equals(mailInConsignmentVO.getMailCategoryCode())
					|| MailConstantsVO.CN37_CATEGORY_D.equals(mailInConsignmentVO.getMailCategoryCode())) {
				consignmentDocumentVO.setConsignmentPriority(MailConstantsVO.FLAG_YES);
			} else {
				consignmentDocumentVO.setConsignmentPriority(MailConstantsVO.FLAG_NO);
			}
			if (MailConstantsVO.CONSIGNMENT_TYPE_CN38.equals(consignmentDocumentVO.getType())) {
				consignmentDocumentVO.setConsignmentPriority(MailConstantsVO.FLAG_YES);
			} else if (MailConstantsVO.CONSIGNMENT_TYPE_CN41.equals(consignmentDocumentVO.getType())) {
				consignmentDocumentVO.setConsignmentPriority(MailConstantsVO.FLAG_NO);
			}
			MailbagVO mailbagVO = new MailbagVO();
			if (mailInConsignmentVO.getMailOrigin() == null || mailInConsignmentVO.getMailDestination() == null) {
				mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
				mailbagVO.setOoe(mailInConsignmentVO.getOriginExchangeOffice());
				mailbagVO.setDoe(mailInConsignmentVO.getDestinationExchangeOffice());
				constructOriginDestinationDetails(mailbagVO);
			}
			if (mailInConsignmentVO.getMailOrigin() != null) {
				consignmentDocumentVO.setOrgin(mailInConsignmentVO.getMailOrigin());
			} else {
				consignmentDocumentVO.setOrgin(mailbagVO.getOrigin());
			}
			if (mailInConsignmentVO.getMailDestination() != null) {
				consignmentDocumentVO.setDestination(mailInConsignmentVO.getMailDestination());
			} else {
				consignmentDocumentVO.setDestination(mailbagVO.getDestination());
			}
		}
		if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null
				&& !consignmentDocumentVO.getRoutingInConsignmentVOs().isEmpty()) {
			RoutingInConsignmentVO routingInConsignmentVO = consignmentDocumentVO.getRoutingInConsignmentVOs()
					.iterator().next();
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(routingInConsignmentVO.getCompanyCode());
			if (routingInConsignmentVO.getOnwardCarrierId() != 0) {
				flightFilterVO.setFlightCarrierId(routingInConsignmentVO.getOnwardCarrierId());
			} else {
				AirlineValidationVO airlineValidationVO = null;
				if (routingInConsignmentVO.getOnwardCarrierCode() != null
						&& routingInConsignmentVO.getOnwardCarrierCode().trim().length() > 0) {
					try {
						airlineValidationVO = neoMastersServiceUtils.validateAlphaCode(consignmentDocumentVO.getCompanyCode(),
								routingInConsignmentVO.getOnwardCarrierCode());
					} catch (BusinessException e) {
						throw new RuntimeException(e);
					}
				}
				flightFilterVO.setFlightCarrierId(
						airlineValidationVO != null && airlineValidationVO.getAirlineIdentifier() > 0
								? airlineValidationVO.getAirlineIdentifier()
								: 0);
				routingInConsignmentVO.setOnwardCarrierId(flightFilterVO.getFlightCarrierId());
			}
			flightFilterVO.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(routingInConsignmentVO.getOnwardFlightDate()));
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
			flightFilterVO.setStation(routingInConsignmentVO.getPol());
			if (flightFilterVO.getFlightCarrierId() > 0 && flightFilterVO.getFlightDate() != null
					&& flightFilterVO.getFlightNumber() != null) {
				Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
				if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
					FlightValidationVO flightValidationVO = flightValidationVOs.iterator().next();
					consignmentDocumentVO.setTransportationMeans(flightValidationVO.getFlightType());
					consignmentDocumentVO.setFirstFlightDepartureDate(LocalDateMapper.toZonedDateTime(
							flightValidationVO.getDepartureDateAtFirstLeg()));
					consignmentDocumentVO
							.setFlightDetails(new StringBuilder().append(flightValidationVO.getCarrierCode())
									.append("-").append(flightValidationVO.getFlightNumber()).toString());
				}
			}
			String polAirport = findAirportDescription(routingInConsignmentVO.getCompanyCode(),
					routingInConsignmentVO.getPol());
			String pouAirport = findAirportDescription(routingInConsignmentVO.getCompanyCode(),
					routingInConsignmentVO.getPou());
			if (polAirport != null && pouAirport != null) {
				consignmentDocumentVO.setFlightRoute(
						new StringBuilder().append(polAirport).append("-").append(pouAirport).toString());
			}
		}
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		consignmentDocumentVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
		PostalAdministrationVO postalAdministration = null;
		postalAdministration = findPACode(consignmentDocumentVO.getCompanyCode(), consignmentDocumentVO.getPaCode());
		if (postalAdministration != null) {
			consignmentDocumentVO.setPaName(postalAdministration.getPaName());
		}
	}





	/** 
	* Method		:	MailController.findUpuCodeName Added by 	:	A-5526 on 24 Jun, 2016 for CRQ-ICRD-103713 Used for 	:	findUpuCodeName Parameters	:	@param companyCode,paCode Parameters	:	@throws SystemException Return type	: 	String
	*/
	public String findUpuCodeName(String companyCode, String paCode) {
		String upuCodeName = null;
		if (paCode != null) {
			try {
				upuCodeName=	mailMasterApi.findUpuCodeNameForPA(companyCode,paCode);
			} catch (PersistenceException e) {
				throw new RuntimeException(e);
			}
		}
		if (upuCodeName != null) {
			return upuCodeName;
		} else {
			return null;
		}
	}

	/** 
	* Method		:	MailController.findAirportDescription
	 * Added by 	:	A-5526 on 24 Jun, 2016 for CRQ-ICRD-103713 Used for 	:	findAirportDescription Parameters	:	@param companyCode,airportCode Parameters	:	@throws SystemException Return type	: 	String
	*/
	public String findAirportDescription(String companyCode, String airportCode) {
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		try {
			airportValidationVO = neoMastersServiceUtils.validateAirportCode(companyCode, airportCode);
		} catch (AirportBusinessException e) {
			throw new RuntimeException(e);
		}
		if (airportValidationVO != null) {
			return airportValidationVO.getAirportName();
		} else {
			return null;
		}
	}

	/** 
	* Method		:	MailController.findOfficeOfExchangeDescription Added by 	:	A-5526 on 24 Jun, 2016 for CRQ-ICRD-103713 Used for 	:	findOfficeOfExchangeDescription Parameters	:	@param companyCode,exchangeCode Parameters	:	@throws SystemException Return type	: 	String
	*/
	private String findOfficeOfExchangeDescription(String companyCode, String exchangeCode) {
		return mailMasterApi.findOfficeOfExchangeDescription(companyCode,exchangeCode);
	}

	/** 
	* @param mailAcceptanceVO
	* @param hasFlightDeparted
	* @param acceptedMailbags
	* @param acceptedUlds
	* @throws SystemException
	*/
	public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO, boolean hasFlightDeparted,
			Collection<MailbagVO> acceptedMailbags, Collection<ContainerDetailsVO> acceptedUlds) {
		log.debug(CLASS + " : " + "flagResditsForAcceptance" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if(MailConstantsVO.FLAG_YES .equals(resditEnabled)) {
			publishResditForAcceptance(mailAcceptanceVO, hasFlightDeparted, acceptedMailbags, acceptedUlds,resditEnabled);
		}
		log.debug(CLASS + " : " + "flagResditsForAcceptance" + " Exiting");
	}
	private void publishResditForAcceptance(MailAcceptanceVO mailAcceptanceVO, boolean hasFlightDeparted,
											Collection<MailbagVO> acceptedMailbags, Collection<ContainerDetailsVO> acceptedUlds,String resditEnabled){
		ContainerDetailsVO containerDetailsVO = mailAcceptanceVO.getContainerDetails().iterator().next();
		String containerNo =containerDetailsVO.getContainerNumber();
		String mailbagId ="";
		String mailSeqnum ="";
		if(acceptedMailbags!=null && !acceptedMailbags.isEmpty()){
			 MailbagVO mailbagVO = acceptedMailbags.iterator().next();
			mailbagId = mailbagVO.getMailbagId();
			mailSeqnum = String.valueOf(mailbagVO.getMailSequenceNumber());
		}
		String key = String.format("%s-%s",mailAcceptanceVO.getFlightCarrierCode(),containerNo);
		if(!mailbagId.isEmpty()){
			key =	String.format("%s-%s",key,mailbagId,mailSeqnum);
		}
		MailAcceptanceModel mailAcceptanceModel = mailOperationsMapper.mailAcceptanceVOToMailAcceptanceModel(mailAcceptanceVO);
		Collection<MailbagModel>acceptedMailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(acceptedMailbags);
		Collection<ContainerDetailsModel>acceptedUldModels = mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(acceptedUlds);
		ResditAcceptedEvent resditAcceptedEvent = mailEventsMapper.constructResditAcceptedEvent(mailAcceptanceModel,
				hasFlightDeparted,acceptedMailbagModels,acceptedUldModels);
		mailEventsProducer. publishEvent(key,resditAcceptedEvent);
	}
	/**
	* @param mailAcceptanceVO
	* @param mailbags
	* @throws SystemException
	*/
	public void flagMLDForMailAcceptance(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbags) {
		log.debug(CLASS + " : " + "flagMLDForMailAcceptance" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForMailAcceptance( mailAcceptanceVO, mailbags));
		log.debug(CLASS + " : " + "flagMLDForMailAcceptance" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param mode
	* @throws SystemException
	*/
	public void flagMLDForMailOperations(Collection<MailbagVO> mailbagVOs, String mode) {
		log.debug(CLASS + " : " + "flagMLDForMailOperations" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForMailOperations( mailbagVOs,mode));
		log.debug(CLASS + " : " + "flagMLDForMailOperations" + " Exiting");
	}

	/** 
	* @param acceptedUlds
	* @param pol
	* @throws SystemException
	*/
	public void flagPendingResditForUlds(Collection<ContainerDetailsVO> acceptedUlds, String pol) {
		log.debug(CLASS + " : " + "flagPendingResditForUlds" + " Entering");
		log.debug(CLASS + " : " + "flagPendingResditForUlds" + " Exiting");
	}

	/** 
	* @param mailbagsToFlag
	* @param eventAirport
	* @throws SystemException
	*/
	public void flagResditForMailbags(Collection<MailbagVO> mailbagsToFlag, String eventAirport, String eventCode) {
		log.debug(CLASS + " : " + "flagResditForMailbags" + " Entering");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			publishResditForMailbags(mailbagsToFlag,eventAirport,eventCode);
		}
		log.debug(CLASS + " : " + "flagResditForMailbags" + " Exiting");
	}
	public void publishResditForMailbags(Collection<MailbagVO> mailbagsToFlag, String eventAirport, String eventCode){

		String mailbagId ="";
		String mailSeqnum ="";
		if(mailbagsToFlag!=null && !mailbagsToFlag.isEmpty()){
			MailbagVO mailbagVO = mailbagsToFlag.iterator().next();
			mailbagId = mailbagVO.getMailbagId();
			mailSeqnum = String.valueOf(mailbagVO.getMailSequenceNumber());
		}
		String key = String.format("%s-%s",eventCode,eventAirport,mailbagId,mailSeqnum);
		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbagsToFlag);

		MailbagResditEvent mailbagResditEvent = mailEventsMapper.constructMailbagResditEvent(mailbagModels,
				eventAirport,eventCode);
		mailEventsProducer. publishEvent(key,mailbagResditEvent);


	}

	/** 
	* @param containerDetails
	* @param assignedPort
	* @throws SystemException
	*/
	public void flagResditsForULDAcceptance(Collection<ContainerDetailsVO> containerDetails, String assignedPort) {
		log.debug(CLASS + " : " + "flagResditsForULDAcceptance" + " Entering");
		log.debug(CLASS + " : " + "flagResditsForULDAcceptance" + " Exiting");
	}

	/** 
	* @param mailArrivalVO
	* @param arrivedMailbags
	* @param arrivedContainers
	* @throws SystemException
	*/
	public void flagResditsForArrival(MailArrivalVO mailArrivalVO, Collection<MailbagVO> arrivedMailbags,
			Collection<ContainerDetailsVO> arrivedContainers) {
		log.debug(CLASS + " : " + "flagResditsForArrival" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			String mailbagId="";
			String containerNo ="";
			String mailSeqnum ="";
			if(arrivedMailbags!=null && !arrivedMailbags.isEmpty()){
				MailbagVO mailbagVO = arrivedMailbags.iterator().next();
				mailbagId = mailbagVO.getMailbagId();
				mailSeqnum = String.valueOf(mailbagVO.getMailSequenceNumber());
			}
			if(arrivedContainers!=null && !arrivedContainers.isEmpty()){
				ContainerDetailsVO containerDetailsVO = arrivedContainers.iterator().next();
				containerNo = containerDetailsVO.getContainerNumber();
			}
			String key = String.format("%s-%s",containerNo,mailbagId,mailSeqnum);
			Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(arrivedMailbags);
			Collection<ContainerDetailsModel> containerDetailsModels =mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(arrivedContainers);
			MailArrivalModel mailArrivalModel = mailOperationsMapper.mailArrivalVOToMailArrivalModel(mailArrivalVO);
			ArrivalResditEvent arrivalResditEvent = mailEventsMapper.constructArrivalResditEvent(mailArrivalModel,mailbagModels,
					containerDetailsModels);
			mailEventsProducer.publishEvent(key,arrivalResditEvent);
		}
		log.debug(CLASS + " : " + "flagResditsForArrival" + " Exiting");
	}

	/** 
	* @param transferredMails
	* @param containerVOs
	* @param operationalFlightVO
	* @throws SystemException
	*/
	public void flagResditsForContainerTransfer(Collection<MailbagVO> transferredMails,
			Collection<ContainerVO> containerVOs, OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "flagResditsForContainerTransfer" + " Entering");
		String resditEnabled = new MailController().findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			String mailbagId="";
			String containerNo ="";
			String mailSeqnum ="";
			if(transferredMails!=null && !transferredMails.isEmpty()){
				MailbagVO mailbagVO = transferredMails.iterator().next();
				mailbagId = mailbagVO.getMailbagId();
				mailSeqnum = String.valueOf(mailbagVO.getMailSequenceNumber());
			}
			if(containerVOs!=null && !containerVOs.isEmpty()){
				ContainerVO containerVO = containerVOs.iterator().next();
				containerNo = containerVO.getContainerNumber();
			}
			String key = String.format("%s-%s",containerNo,mailbagId);
			Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(transferredMails);
			Collection<ContainerModel> containerModels =mailOperationsMapper.containerVOsToContainerModel(containerVOs);
			OperationalFlightModel operationalFlightModel = mailOperationsMapper.operationalFlightVOToOperationalFlightModel(operationalFlightVO);
			ContainerTransferResditEvent containerTransferResditEvent = mailEventsMapper.constructContainerTransferResditEvent(
					mailbagModels,containerModels,operationalFlightModel);
			mailEventsProducer.publishEvent(key,containerTransferResditEvent);
		}
		log.debug(CLASS + " : " + "flagResditsForContainerTransfer" + " Exiting");
	}

	/** 
	* @param transferredMails
	* @param containerVO
	* @throws SystemException
	*/
	public void flagResditsForMailbagTransfer(Collection<MailbagVO> transferredMails, ContainerVO containerVO) {
		log.debug(CLASS + " : " + "flagResditsForMailbagTransfer" + " Entering");
        String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			publishResditsForMailbagTransfer(transferredMails,
					containerVO);
		}
		log.debug(CLASS + " : " + "flagResditsForMailbagTransfer" + " Exiting");
	}
	public void publishResditsForMailbagTransfer(Collection<MailbagVO> transferredMails, ContainerVO containerVO){
		String mailbagId ="";
		if(transferredMails!=null && !transferredMails.isEmpty()){
			MailbagVO mailbagVO = transferredMails.iterator().next();
			mailbagId = mailbagVO.getMailbagId()+ String.valueOf(mailbagVO.getMailSequenceNumber());
		}
		String key = String.format("%s-%s",containerVO.getContainerNumber(),mailbagId);
		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(transferredMails);
		ContainerModel containerModel =mailOperationsMapper.containerVOToContainerModel(containerVO);
		MailbagTransferResditEvent mailbagTransferResditEvent = mailEventsMapper.constructMailbagTransferResditEvent(mailbagModels,containerModel);
		mailEventsProducer.publishEvent(key,mailbagTransferResditEvent);

	}

	/** 
	* @param eventCode
	* @param eventPort
	* @param mailbagVOs
	* @throws SystemException
	*/
	public void flagResditForMailbagsFromReassign(String eventCode, String eventPort,
			Collection<MailbagVO> mailbagVOs) {
		log.debug(CLASS + " : " + "flagResditForMailbagsFromReassign" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			String mailbagId ="";
			String malSeqnum ="";
			if(mailbagVOs!=null && !mailbagVOs.isEmpty()){
				MailbagVO mailbagVO = mailbagVOs.iterator().next();
				mailbagId = mailbagVO.getMailbagId();
				malSeqnum =String.valueOf(mailbagVO.getMailSequenceNumber());
			}
			String key = String.format("%s-%s",eventPort,mailbagId,malSeqnum);
			Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbagVOs);
			MailbagsFromReassignResditEvent mailbagsFromReassignResditEvent = mailEventsMapper.constructMailbagsFromReassignResditEvent(
					mailbagModels,eventPort,MailConstantsVO.RESDIT_PENDING);
			mailEventsProducer.publishEvent(key,mailbagsFromReassignResditEvent);

		}
		log.debug(CLASS + " : " + "flagResditForMailbagsFromReassign" + " Exiting");
	}

	/** 
	* @param mailbags
	* @param toContainerVO
	* @throws SystemException
	*/
	public void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		log.debug(CLASS + " : " + "flagResditsForMailbagReassign" + " Entering");
		String mailbagId ="";
		String malSeqnum ="";
		if(mailbags!=null && !mailbags.isEmpty()){
			MailbagVO mailbagVO = mailbags.iterator().next();
			mailbagId = mailbagVO.getMailbagId();
			malSeqnum =String.valueOf(mailbagVO.getMailSequenceNumber());
		}
		String key = String.format("%s-%s",toContainerVO.getContainerNumber(),mailbagId,malSeqnum);
		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbags);
		ContainerModel containerModel =mailOperationsMapper.containerVOToContainerModel(toContainerVO);
		MailbagReassignResditEvent mailbagReassignResditEvent = mailEventsMapper.constructMailbagReassignResditEvent(mailbagModels,containerModel);
		mailEventsProducer.publishEvent(key,mailbagReassignResditEvent);
		log.debug(CLASS + " : " + "flagResditsForMailbagReassign" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param ulds
	* @param toFlightVO
	* @param hasFlightDeparted
	* @throws SystemException
	*/
	public void flagResditForContainerReassign(Collection<MailbagVO> mailbagVOs, Collection<ContainerDetailsVO> ulds,
			OperationalFlightVO toFlightVO, boolean hasFlightDeparted) {
		log.debug(CLASS + " : " + "flagResditForContainerReassign" + " Entering");
		String mailbagId ="";
		String malSeqnum ="";
		String containerNo="";
		if(mailbagVOs!=null && !mailbagVOs.isEmpty()){
			MailbagVO mailbagVO = mailbagVOs.iterator().next();
			mailbagId = mailbagVO.getMailbagId();
			malSeqnum =String.valueOf(mailbagVO.getMailSequenceNumber());
		}
		if(ulds!=null && !ulds.isEmpty()){
			ContainerDetailsVO container = ulds.iterator().next();
			containerNo = container.getContainerNumber();
		}
		String key = String.format("%s-%s",containerNo,mailbagId,malSeqnum);
		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbagVOs);
		Collection<ContainerDetailsModel> containerDetails =mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(ulds);
		OperationalFlightModel flightModel = mailOperationsMapper.operationalFlightVOToOperationalFlightModel(toFlightVO);
		ContainerReassignResditEvent containerReassignResditEvent =mailEventsMapper.constructContainerReassignResditEvent(mailbagModels,containerDetails,
				flightModel,hasFlightDeparted);
		mailEventsProducer.publishEvent(key,containerReassignResditEvent);
		log.debug(CLASS + " : " + "flagResditForContainerReassign" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param toContainerVO
	* @param mode
	* @throws SystemException
	*/
	public void flagMLDForMailReassignOperations(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String mode) {
		log.debug(CLASS + " : " + "flagMLDForMailReassignOperations" + " Entering");
		log.debug(CLASS + " : " + "flagMLDForMailReassignOperations" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVOs
	* @param operationalFlightVO
	* @throws SystemException
	*/
	public void flagMLDForContainerTransfer(Collection<MailbagVO> mailbagVOs, Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "flagMLDForContainerTransfer" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForContainerTransfer(mailbagVOs,containerVOs,operationalFlightVO));
		log.debug(CLASS + " : " + "flagMLDForContainerTransfer" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @param containerVO
	* @param operationalFlightVO
	* @throws SystemException
	*/
	public void flagMLDForMailbagTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO,
			OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "flagMLDForMailbagTransfer" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForMailbagTransfer(mailbagVOs,containerVO,operationalFlightVO));
		log.debug(CLASS + " : " + "flagMLDForMailbagTransfer" + " Exiting");
	}

	/** 
	* @param mailbagVO
	* @throws SystemException
	*/
	public void updateResditEventTimes(MailbagVO mailbagVO) {
		log.debug(CLASS + " : " + "updateResditEventTimes" + " Entering");
		log.debug(CLASS + " : " + "updateResditEventTimes" + " Exiting");
	}

	/** 
	* @param mailbagVOs
	* @throws SystemException
	*/
	public static void updateMailbagVOs(Collection<MailbagVO> mailbagVOs, boolean isFlightChange) {
		long[] seqNums = new long[mailbagVOs.size()];
		final Map<Long, Collection<MailbagHistoryVO>> historyMap;
		final Map<Long, MailInConsignmentVO> consignmentsMap;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String companyCode = mailbagVOs.iterator().next().getCompanyCode();
			int i = 0;
			for (MailbagVO mailVO : mailbagVOs) {
				seqNums[i++] = mailVO.getMailSequenceNumber();
			}
			historyMap = Mailbag.findMailbagHistoriesMap(companyCode, seqNums);
			consignmentsMap = Mailbag.findAllConsignmentDetailsForMailbag(companyCode, seqNums);
			for (MailbagVO mailVO : mailbagVOs) {
				if (historyMap != null && !historyMap.isEmpty()) {
					if (historyMap.containsKey(mailVO.getMailSequenceNumber())) {
						mailVO.setMailbagHistories(historyMap.get(mailVO.getMailSequenceNumber()));
					}
				}
				if (consignmentsMap != null && !consignmentsMap.isEmpty()) {
					if (consignmentsMap.containsKey(mailVO.getMailSequenceNumber())) {
						mailVO.setMailConsignmentVO(consignmentsMap.get(mailVO.getMailSequenceNumber()));
					}
				}
			}
		}
	}

	public void insertOrUpdateHistoryDetailsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO> acceptedMailbags) {
		mailAcceptanceVO.setTriggerPoint(getTriggerPoint());
		asyncInvoker.invoke(() -> historyBuilder.insertOrUpdateHistoryDetailsForAcceptance(mailAcceptanceVO,
				acceptedMailbags));
	}

	public String getTriggerPoint() {
		return ContextUtil.getInstance().getTxContext(ContextUtil.TRIGGER_POINT);
	}

	public void flagHistoryDetailsForMailbagsFromReassign(Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO) {
		log.debug(CLASS + " : " + "flagHistoryDetailsForMailbagsFromReassign" + " Entering");
		toDestinationContainerVO.setTriggerPoint(getTriggerPoint());
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryDetailsForMailbagsFromReassign(flightAssignedMailbags,
				toDestinationContainerVO));
		log.debug(CLASS + " : " + "flagHistoryDetailsForMailbagsFromReassign" + " Exiting");
	}

	public void flagHistoryForContainerReassignment(OperationalFlightVO toFlightVO, ContainerVO containerVO,
			Collection<MailbagVO> mailbagVOS, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForContainerReassignment" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForContainerReassignment(toFlightVO, containerVO, mailbagVOS,
				triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForContainerReassignment" + " Exiting");
	}

	public void flagMailbagHistoryForArrival(MailArrivalVO mailArrivalVO, String triggerPoint) {
		log.debug(CLASS + " : " + "flagMailbagHistoryForArrival" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagMailbagHistoryForArrival(mailArrivalVO, triggerPoint));
		log.debug(CLASS + " : " + "flagMailbagHistoryForArrival" + " Exiting");
	}

	public void flagMailbagHistoryForDelivery(Collection<MailbagVO> mailbags, String triggerPoint) {
		log.debug(CLASS + " : " + "flagMailbagHistoryForDelivery" + " Entering");
		asyncInvoker.invoke(()-> historyBuilder.flagMailbagHistoryForDelivery(mailbags, triggerPoint));
		log.debug(CLASS + " : " + "flagMailbagHistoryForDelivery" + " Exiting");
	}

	public void flagHistoryForReturnedMailbags(Collection<MailbagVO> mailbags, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForReturnedMailbags" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForReturnedMailbags(mailbags, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForReturnedMailbags" + " Exiting");
	}

	public void flagHistoryForDamagedMailbags(Collection<MailbagVO> mailbags, String triggeringPoint) {
		log.debug(CLASS + " : " + "flagHistoryForDamagedMailbags" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForDamagedMailbags(mailbags, triggeringPoint));
		log.debug(CLASS + " : " + "flagHistoryForDamagedMailbags" + " Exiting");
	}

	public void flagHistoryForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForTransfer" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForTransfer(mailbagVOs, containerVO,
				triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForTransfer" + " Exiting");
	}

	public void flagHistoryForContainerTransfer(OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForContainerTransfer" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForContainerTransfer(operationalFlightVO, toFlightSegSerNum,
						mailbagVOs, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForContainerTransfer" + " Exiting");
	}

	public void flagHistoryforFlightArrival(MailbagVO mailbagVO, Collection<FlightValidationVO> flightVOs, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryforFlightArrival" + " Entering");
		asyncInvoker.invoke(()-> historyBuilder.flagHistoryforFlightArrival(mailbagVO, flightVOs, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryforFlightArrival" + " Exiting");
	}

	public void insertOrUpdateAuditDetailsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO> acceptedMailbags) {
		log.debug(CLASS + " : " + "insertOrUpdateAuditDetailsForAcceptance" + " Entering");
		for (MailbagVO mailbagVO : acceptedMailbags) {
			if(!mailAcceptanceVO.isAssignedToFlight()){
				//auditMailDetailUpdates(mailbagVO,MailbagAuditVO. MAILBAG_ACCEPTANCE, "Insert");

			}else{
				//auditMailDetailUpdates(mailbagVO,MailbagAuditVO. MAILBAG_ASSIGNED, "Update");

			}
		}

		log.debug(CLASS + " : " + "insertOrUpdateAuditDetailsForAcceptance" + " Exiting");
	}

	public void flagAuditDetailsForMailbagsFromReassign(Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO) {
		log.debug(CLASS + " : " + "flagAuditDetailsForMailbagsFromReassign" + " Entering");
		log.debug(CLASS + " : " + "flagAuditDetailsForMailbagsFromReassign" + " Exiting");
	}

	public void flagMailbagAuditForArrival(MailArrivalVO mailArrivalVO) {
		log.debug(CLASS + " : " + "flagMailbagAuditForArrival" + " Entering");
		log.debug(CLASS + " : " + "flagMailbagAuditForArrival" + " Exiting");
	}

	public void flagAuditForReturnedMailbags(Collection<MailbagVO> mailbags) {
		log.debug(CLASS + " : " + "flagAuditForReturnedMailbags" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForReturnedMailbags" + " Exiting");
	}

	public void flagAuditForDamagedMailbags(Collection<MailbagVO> mailbags) {
		log.debug(CLASS + " : " + "flagAuditForDamagedMailbags" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForDamagedMailbags" + " Exiting");
	}

	public void flagAuditForTransfer(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug(CLASS + " : " + "flagAuditForTransfer" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForTransfer" + " Exiting");
	}

	public void flagAuditforFlightArrival(MailbagVO mailbagVO, Collection<FlightValidationVO> flightVOs) {
		log.debug(CLASS + " : " + "flagAuditforFlightArrival" + " Entering");
		log.debug(CLASS + " : " + "flagAuditforFlightArrival" + " Exiting");
	}

	public void flagAuditForContainerTransfer(OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
			Collection<MailbagVO> mailbagVOs) {
		log.debug(CLASS + " : " + "flagAuditForContainerTransfer" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForContainerTransfer" + " Exiting");
	}

	public void flagAuditForContainerReassignment(OperationalFlightVO toFlightVO, ContainerVO containerVO,
			Collection<MailbagVO> mailbagVOS) {
		log.debug(CLASS + " : " + "flagAuditForContainerReassignment" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForContainerReassignment" + " Exiting");
	}

	public void flagHistoryforTransferInitiation(Collection<MailbagVO> mailbagVOs, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryforTransferInitiation" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryforTransferInitiation(mailbagVOs, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryforTransferInitiation" + " Exiting");
	}

	/** 
	* Method		:	MailController.findOneTimeDescription Added by 	:	A-6991 on 14-Jul-2017 Used for 	:   ICRD-208718 Parameters	:	@param companyCode Parameters	:	@param oneTimeCode Parameters	:	@return Return type	: 	Map<String,Collection<OneTimeVO>>
	*/
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode, String oneTimeCode) {
		log.debug(CLASS + " : " + "findOneTimeDescription" + " Entering");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(oneTimeCode);
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessException e) {
			log.info(e.getMessage());
		}
		log.debug(CLASS + " : " + "findOneTimeDescription" + " Exiting");
		return oneTimes;
	}

	/** 
	* @param mailbagvo
	* @param actionCode
	* @throws SystemException
	*/
	public void auditMailBagUpdate(MailbagVO mailbagvo, String actionCode) {
		log.debug(CLASS + " : " + "auditMailBagUpdate" + " Entering");
		auditMailDetailUpdates(mailbagvo,actionCode,"Update","Updation of origin/destination");
		log.debug(CLASS + " : " + "auditMailBagUpdate" + " Exiting");
	}

	private void checkDeletedMailbagDetails(Collection<MailbagVO> deletedMails) {
		for (MailbagVO mailVO : deletedMails) {
			long mailSeq = 0;
			try {
				mailSeq = findMailSequenceNumber(mailVO.getMailbagId(), mailVO.getCompanyCode());
				if (mailSeq == 0) {
					deletedMails.remove(mailVO);
					if (!deletedMails.isEmpty()) {
						continue;
					} else {
						break;
					}
				} else {
					MailbagPK mailPK = new MailbagPK();
					mailPK.setCompanyCode(mailVO.getCompanyCode());
					mailPK.setMailSequenceNumber(mailSeq);
					Mailbag mail = Mailbag.find(mailPK);
					if (mail != null && MailConstantsVO.MAIL_STATUS_RETURNED.equals(mail.getLatestStatus())) {
						deletedMails.remove(mailVO);
						if (!deletedMails.isEmpty()) {
							continue;
						} else {
							break;
						}
					}
				}
			} catch (FinderException exception) {
				deletedMails.remove(mailVO);
				if (!deletedMails.isEmpty()) {
					continue;
				} else {
					break;
				}
			}
		}
	}

	/** 
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	public void auditContainer(MailAcceptanceVO mailAcceptanceVO) {
		log.debug(CLASS + " : " + "auditContainer" + " Entering");
		log.debug(CLASS + " : " + "auditContainer" + " Exiting");
	}

	/**
	* @throws SystemException
	*/
	public void flagMLDForContainerReassign(Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO) {
		log.debug(CLASS + " : " + "flagMLDForMailReassignOperations" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForContainerReassign( containerVOs,toFlightVO));
		log.debug(CLASS + " : " + "flagMLDForMailReassignOperations" + " Exiting");
	}

	/** 
	* Method		:	MailController.findOfficeOfExchangeForPA Added by 	:	a-6245 on 10-Jul-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	String
	*/
	public Map<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findOfficeOfExchangeForAirports" + " Entering");
        return mailMasterApi.findOfficeOfExchangeForPA(companyCode, paCode);
	}

	/** 
	* Method		:	MailController.performMailAWBTransactions Added by 	:	a-7779 on 13-Sep-2017 Used for 	: Parameters	:	@param mailFlightSummaryVO Parameters	:	@param eventCode Return type	: 	void
	*/
	public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO, String eventCode) {
		log.debug(CLASS + " : " + "performMailAWBTransactions" + " Entering");
		log.debug(CLASS + " : " + "performMailAWBTransactions" + " Exiting");
	}

	public Collection<RateAuditVO> createRateAuditVOs(Collection<ContainerDetailsVO> containerDetailsVOs,
			String triggerPoint, boolean provisionalRateImport) {
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			String triggerForImport = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			LoginProfile logonAttributes = ContextUtil.getInstance().callerLoginProfile();
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailbagVO : mailbags) {
						if (!provisionalRateImport && triggerForImport != null && !triggerForImport.isEmpty()
								&& (triggerForImport.contains("D"))) {
							try {
								Mailbag mailbag = Mailbag.find(createMailbagPK(mailbagVO.getCompanyCode(), mailbagVO));
								if (!(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())
										|| logonAttributes.getOwnAirlineIdentifier() != mailbag.getCarrierId())) {
									continue;
								}
							} catch (FinderException e) {
								log.error("" + "exception raised" + " " + e);
							}
						}
						RateAuditVO rateAuditVO = new RateAuditVO();
						rateAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
						rateAuditVO.setTriggerPoint(triggerPoint);
						rateAuditDetailsVO.setSource(triggerPoint);
						rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbagVO.getScannedDate()));
						rateAuditDetailsVO.setCarrierid(mailbagVO.getCarrierId());
						if (mailbagVO.getCarrierCode() != null) {
							rateAuditDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
						} else {
							rateAuditDetailsVO.setCarrierCode(containerDetailsVO.getCarrierCode());
						}
						if (containerDetailsVO.getFlightNumber() != null) {
							rateAuditDetailsVO.setFlightno(containerDetailsVO.getFlightNumber());
						} else {
							rateAuditDetailsVO.setFlightno(mailbagVO.getFlightNumber());
						}
						if (containerDetailsVO.getFlightSequenceNumber() != 0) {
							rateAuditDetailsVO.setFlightseqno((int) containerDetailsVO.getFlightSequenceNumber());
						} else {
							rateAuditDetailsVO.setFlightseqno((int) mailbagVO.getFlightSequenceNumber());
						}
						if (containerDetailsVO.getSegmentSerialNumber() != 0) {
							rateAuditDetailsVO.setSegSerNo(containerDetailsVO.getSegmentSerialNumber());
						} else {
							rateAuditDetailsVO.setSegSerNo(mailbagVO.getSegmentSerialNumber());
						}
						if (mailbagVO.getFlightDate() != null && mailbagVO.getFlightSequenceNumber() <= 0) {
							rateAuditDetailsVO.setFlightno(containerDetailsVO.getFlightNumber());
							rateAuditDetailsVO.setFlightseqno((int) containerDetailsVO.getFlightSequenceNumber());
							rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerDetailsVO.getFlightDate()));
							rateAuditDetailsVO.setSegSerNo(containerDetailsVO.getSegmentSerialNumber());
						}
						if (containerDetailsVO.getFlightDate() != null) {
							rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerDetailsVO.getFlightDate()));
						} else {
							rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
						}
						if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(triggerPoint)) {
							FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
							flightFilterVO.setFlightCarrierId(rateAuditDetailsVO.getCarrierid());
							flightFilterVO.setFlightNumber(rateAuditDetailsVO.getFlightno());
							flightFilterVO.setDirection(FlightFilterVO.INBOUND);
							flightFilterVO.setStation(containerDetailsVO.getPou());
							flightFilterVO.setFlightSequenceNumber(rateAuditDetailsVO.getFlightseqno());
							Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
							if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
								rateAuditDetailsVO.setFlightDate(flightValidationVOs.iterator().next().getStd());
							}
						}
						rateAuditDetails.add(rateAuditDetailsVO);
						rateAuditVO.setRateAuditDetails(rateAuditDetails);
						rateAuditVOs.add(rateAuditVO);
					}
				}
			}
		}
		return rateAuditVOs;
	}

	public String findNearestAirportOfCity(String companyCode, String exchangeCode) {
		Collection<String> officeOfExchanges = new ArrayList<String>();
		officeOfExchanges.add(exchangeCode);
		Collection<ArrayList<String>> groupedOECityArpCodes = findCityAndAirportForOE(companyCode, officeOfExchanges);
		if (groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
			for (ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
				if (cityAndArpForOE.size() == 3) {
					if (exchangeCode != null && exchangeCode.length() > 0
							&& exchangeCode.equals(cityAndArpForOE.get(0))) {
						return cityAndArpForOE.get(2);
					}
				}
			}
		}
		return null;
	}

	public Collection<RateAuditVO> createRateAuditVOs(OperationalFlightVO toFlightVO, ContainerVO containerVO,
			Collection<MailbagVO> mailbags, String triggerPoint, boolean provisionalRateImport) {
		var loginProfile = ContextUtil.getInstance().getBean(ContextUtil.class).callerLoginProfile();
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
		RateAuditVO rateAuditVO = new RateAuditVO();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		MailbagVO mailbagVO = null;
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (!provisionalRateImport && importEnabled != null
				&& ((importEnabled.contains("D")
						&& !loginProfile.getOwnAirlineCode().equals(toFlightVO.getCarrierCode()))
						|| importEnabled.contains("M"))) {
			if (CollectionUtils.isNotEmpty(mailbags)) {
				for (MailbagVO mailbagVo : mailbags) {
					rateAuditVO = new RateAuditVO();
					rateAuditVO.setCompanyCode(toFlightVO.getCompanyCode());
					rateAuditVO.setTriggerPoint(triggerPoint);
					rateAuditDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
					rateAuditDetailsVO.setCarrierid(toFlightVO.getCarrierId());
					rateAuditDetailsVO.setSource(triggerPoint);
					rateAuditVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
					rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbagVo.getScannedDate()));
					rateAuditDetailsVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
					if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(triggerPoint)
							&& toFlightVO.getSegSerNum() > 0) {
						rateAuditDetailsVO.setSegSerNo(toFlightVO.getSegSerNum());
					} else {
						rateAuditDetailsVO.setSegSerNo(containerVO.getSegmentSerialNumber());
					}
					if (!toFlightVO.getCarrierCode().equals(toFlightVO.getOwnAirlineCode())
							&& toFlightVO.getFlightNumber().equals("-1") && toFlightVO.getFlightSequenceNumber() == -1
							&& containerVO != null && containerVO.getFlightDate() != null) {
						rateAuditDetailsVO.setCarrierCode(toFlightVO.getOwnAirlineCode());
						rateAuditDetailsVO.setFlightno(containerVO.getFlightNumber());
						rateAuditDetailsVO.setFlightseqno((int) containerVO.getFlightSequenceNumber());
						rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerVO.getFlightDate()));
						rateAuditDetailsVO.setSegSerNo(containerVO.getSegmentSerialNumber());
						rateAuditDetailsVO.setCarrierid(toFlightVO.getOwnAirlineId());
					} else {
						rateAuditDetailsVO.setFlightno(toFlightVO.getFlightNumber());
						rateAuditDetailsVO.setFlightseqno((int) toFlightVO.getFlightSequenceNumber());
						rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(toFlightVO.getFlightDate()));
					}
					rateAuditDetails.add(rateAuditDetailsVO);
					rateAuditVO.setRateAuditDetails(rateAuditDetails);
					rateAuditVOs.add(rateAuditVO);
				}
			}
		} else {
			if (CollectionUtils.isNotEmpty(mailbags)) {
				mailbagVO = ((ArrayList<MailbagVO>) mailbags).get(0);
				rateAuditVO.setCompanyCode(toFlightVO.getCompanyCode());
				rateAuditVO.setTriggerPoint(triggerPoint);
				rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				rateAuditDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
				rateAuditDetailsVO.setCarrierid(toFlightVO.getCarrierId());
				rateAuditDetailsVO.setFlightno(toFlightVO.getFlightNumber());
				rateAuditDetailsVO.setFlightseqno((int) toFlightVO.getFlightSequenceNumber());
				rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(toFlightVO.getFlightDate()));
				rateAuditDetailsVO.setSource(triggerPoint);
				rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbagVO.getScannedDate()));
				if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(triggerPoint) && toFlightVO.getSegSerNum() > 0) {
					rateAuditDetailsVO.setSegSerNo(toFlightVO.getSegSerNum());
				} else {
					rateAuditDetailsVO.setSegSerNo(mailbagVO.getSegmentSerialNumber());
				}
				rateAuditDetails.add(rateAuditDetailsVO);
				rateAuditVO.setRateAuditDetails(rateAuditDetails);
				rateAuditVOs.add(rateAuditVO);
			}
		}
		return rateAuditVOs;
	}

	/** 
	* Method		:	MailController.transferMailAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	* @param mailbagVOs
	* @param toContainerVO
	* @param toPrintTransferManifest
	* @return
	* @throws SystemException
	* @throws MailBookingException
	* @throws CapacityBookingProxyException
	* @throws InvalidFlightSegmentException
	* @throws MailOperationsBusinessException
	* @throws FlightClosedException
	*/
	public TransferManifestVO transferMailAtExport(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String toPrintTransferManifest) throws InvalidFlightSegmentException, CapacityBookingProxyException,
			MailBookingException, MailOperationsBusinessException, FlightClosedException {
		log.debug(CLASS + " : " + "transferMailAtExport" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<MailbagVO> mailbagVOsToTransferOutToOtherCarrier = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsToTransfer = new ArrayList<MailbagVO>();
		TransferManifestVO transferManifestVO = null;
		Collection<DespatchDetailsVO> despatchDetailsVOsToTransfer = new ArrayList<DespatchDetailsVO>();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailVO : mailbagVOs) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO = mailOperationsMapper.copyMailbagVO(mailVO);
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.findMailbag(constructMailbagPK(mailbagVO));
				} catch (FinderException e) {
					mailbag = null;
				}
				if (mailbag != null
						&& ("MTK009".equals(mailVO.getMailSource()) || "MAIL ENQ".equals(mailVO.getMailSource()))
						&& mailbag.getScannedPort() != null
						&& !mailbag.getScannedPort().equals(logonAttributes.getAirportCode())) {
					throw new MailOperationsBusinessException(
							MailOperationsBusinessException.MAILTRACKING_MAILBAGNOTAVAILABLE);
				} else if (mailbag != null && (LIST_TRANSFER_MANIFEST_SCREENID.equals(mailVO.getMailSource()))) {
					mailbagVOsToTransfer.add(mailbagVO);
				} else if ("TRA_OUT".equals(checkForMailBagTransferStatus(mailbagVO, toContainerVO, logonAttributes))
						&& mailOperationsTransferTransaction()) {
					if (mailbagVOsToTransfer.stream()
							.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
						mailbagVOsToTransfer.add(mailbagVO);
					}
					if (mailbagVOsToTransferOutToOtherCarrier.stream()
							.noneMatch(mail -> mail.getMailSequenceNumber() == mailVO.getMailSequenceNumber())) {
						mailbagVOsToTransferOutToOtherCarrier.add(mailbagVO);
					}
					toContainerVO.setTransStatus(true);
				} else if ("TRA_OUT".equals(checkForMailBagTransferStatus(mailbagVO, toContainerVO, logonAttributes))) {
					mailbagVOsToTransferOutToOtherCarrier.add(mailbagVO);
				} else {
					if (!"D".equals(mailbagVO.getAcknowledge())) {
						mailbagVOsToTransfer.add(mailbagVO);
					}
				}
			}
			if (!mailbagVOsToTransfer.isEmpty()) {
				try {
					transferMailbagsAtExport(mailbagVOsToTransfer, toContainerVO);
				} catch (SystemException e){
					if ((e.getMessage().contains("No persisted object found"))) {
						throw new MailOperationsBusinessException(MailOperationsBusinessException.MAILTRACKING_MAILBAGNOTAVAILABLE);
					} else {
						throw new SystemException(e.getMessage(), e);
					}
				}
			}
		}
		if (mailbagVOsToTransferOutToOtherCarrier != null && !mailbagVOsToTransferOutToOtherCarrier.isEmpty()) {
			Map<String, Collection<MailbagVO>> groupedMailbagDetails = new HashMap<String, Collection<MailbagVO>>();
			Set<String> flightKeySet = new HashSet<String>();
			if (mailbagVOsToTransferOutToOtherCarrier != null && !mailbagVOsToTransferOutToOtherCarrier.isEmpty()) {
				String flightKey = null;
				for (MailbagVO mailVO : mailbagVOsToTransferOutToOtherCarrier) {


					flightKey = new StringBuilder().append(mailVO.getCarrierCode()).append(ID_SEP)
							.append(mailVO.getFlightNumber()).append(ID_SEP)
							.append(mailVO.getFlightDate() != null ? mailVO.getFlightDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT)
									: "")
							.append(ID_SEP)
							.append(mailVO.getFlightSequenceNumber() > 0 ? mailVO.getFlightSequenceNumber() : "")
							.append(ID_SEP)
							.append(mailVO.getSegmentSerialNumber() > 0 ? mailVO.getSegmentSerialNumber() : "")
							.toString();
					if (!groupedMailbagDetails.containsKey(flightKey)) {
						flightKeySet.add(flightKey);
						Collection<MailbagVO> mailCollection = new ArrayList<MailbagVO>();
						mailCollection.add(mailVO);
						groupedMailbagDetails.put(flightKey, mailCollection);
					} else {
						Collection<MailbagVO> mailbagCollection = groupedMailbagDetails.get(flightKey);
						mailbagCollection.add(mailVO);
					}
				}
			}
			if (flightKeySet != null && flightKeySet.size() > 0) {
				for (String flightKey : flightKeySet) {
					String[] keys = flightKey.split(ID_SEP);
					String carrierCode = null;
					String flightNumber = null;
					String flightDate = null;
					String flightSeqNum = null;
					String segSerNum = null;
					if (keys != null && keys.length > 0) {
						carrierCode = keys[0];
						flightNumber = keys[1];
						if (keys.length > 2) {
							flightDate = keys[2];
							flightSeqNum = keys[3];
							segSerNum = keys[4];
						}
						toContainerVO.setFromCarrier(carrierCode);
						toContainerVO.setFromFltNum(flightNumber);
						toContainerVO.setFrmFltSeqNum(flightSeqNum != null ? Long.parseLong(flightSeqNum) : -1);
						toContainerVO.setFrmSegSerNum(segSerNum != null ? Integer.parseInt(segSerNum) : -1);
						if (flightDate != null && flightDate.trim().length() > 0) {
							toContainerVO.setFromFltDat(java.time.LocalDate.parse(flightDate,DateTimeFormatter
									.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS_DD_MMM_YYYYY, Locale.ENGLISH)).atStartOfDay(ZoneId.systemDefault()));
						}
					}
					Collection<MailbagVO> mailbags = null;
					if (groupedMailbagDetails != null && groupedMailbagDetails.size() > 0) {
						mailbags = groupedMailbagDetails.get(flightKey);
					}
					boolean isPrintingNeeded = false;
					if (MailConstantsVO.FLAG_YES.equals(toPrintTransferManifest)) {
						isPrintingNeeded = true;
					}
					if (mailbags != null && !mailbags.isEmpty()) {
						for (MailbagVO mailbagVO : mailbags) {
							if (mailbagVO.getMailSequenceNumber() > 0) {
								String transferManifestId = null;
								try {
									transferManifestId = constructDAO().findTransferManifestId(
											mailbagVO.getCompanyCode(), mailbagVO.getMailSequenceNumber());
								} catch (PersistenceException e) {
									e.getMessage();
								}
								if (transferManifestId != null && transferManifestId.trim().length() > 0) {
									TransferManifestDSN transferManifestDSN = null;
									TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
									transferManifestDSNPK.setCompanyCode(mailbagVO.getCompanyCode());
									transferManifestDSNPK.setTransferManifestId(transferManifestId);
									transferManifestDSNPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
									try {
										transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
									} catch (FinderException e) {
										e.getMessage();
									}
									if (transferManifestDSN != null) {
										transferManifestDSN.setTransferStatus(TRANSER_STATUS_REJECT);
									}
								}
							}
						}
					}
					transferManifestVO = generateTransferManifestAtExport(mailbags, toContainerVO, isPrintingNeeded);
					if (!toContainerVO.isTransStatus()) {
						MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
						mailController.flagHistoryforTransferInitiation(mailbags, getTriggerPoint());
					}
				}
			}
		}
		log.debug(CLASS + " : " + "transferMailAtExport" + " Exiting");
		return transferManifestVO;
	}

	/** 
	* Method		:	MailController.transferMailbagsAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @throws FlightClosedException
	*/
	public void transferMailbagsAtExport(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException,
			FlightClosedException, SystemException {
		log.debug(CLASS + " : " + "transferMailbagsAtExport" + " Entering");
		mailTransfer.transferMailbagsAtExport(mailbagVOs, containerVO);
		if (containerVO.getContainerNumber() != null) {
			updateContainerAcceptance(containerVO, mailbagVOs);
		}
		log.debug(CLASS + " : " + "transferMailbagsAtExport" + " Exiting");
	}

	/** 
	* Method		:	MailController.flagHistoryForMailTransferAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void flagHistoryForMailTransferAtExport(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForMailTransferAtExport" + " Entering");
        asyncInvoker.invoke(() -> historyBuilder.flagHistoryForMailTransferAtExport(mailbagVOs, containerVO, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForMailTransferAtExport" + " Exiting");
	}

	public void flagAuditForMailTransferAtExport(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		log.debug(CLASS + " : " + "flagAuditForMailTransferAtExport" + " Entering");
		log.debug(CLASS + " : " + "flagAuditForMailTransferAtExport" + " Exiting");
	}

	/**
	* @param toContainerVO
	* @throws SystemException
	* @author A-7371
	*/
	private TransferManifestVO generateTransferManifestAtExport(Collection<MailbagVO> mailbagVOs,
			ContainerVO toContainerVO, boolean isPrintingNeeded) {
		log.debug(CLASS + " : " + "generateTransferManifest" + " Entering");
		String id = new StringBuilder().append(toContainerVO.getOwnAirlineCode()).append(toContainerVO.getCarrierCode())
				.toString();
		int transferManifestId = generateTransferManifestSeqNumber(toContainerVO.getCompanyCode(), id);
		log.debug("" + "!!!!!!!!!!!!!!!11id" + " " + id);
		log.debug("" + "!!!!!!!!!!!11transferManifestId" + " " + transferManifestId);
		TransferManifestVO transferManifestVO = new TransferManifestVO();
		transferManifestVO.setCompanyCode(toContainerVO.getCompanyCode());
		transferManifestVO.setAirPort(toContainerVO.getAssignedPort());
		transferManifestVO.setTransferManifestId(
				new StringBuilder().append(id).append(String.valueOf(transferManifestId)).toString());
		log.debug("" + "TransferManifestId--->>>" + " " + transferManifestVO.getTransferManifestId());
		transferManifestVO.setTransferredFromCarCode(toContainerVO.getFromCarrier());
		transferManifestVO.setTransferredFromFltNum(toContainerVO.getFromFltNum());
		transferManifestVO.setToFltDat(toContainerVO.getFlightDate());
		transferManifestVO.setFromFltDat(toContainerVO.getFromFltDat());
		ZonedDateTime trfDate = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
		transferManifestVO.setTransferredDate(trfDate);
		transferManifestVO.setTransferredToCarrierCode(toContainerVO.getCarrierCode());
		transferManifestVO.setTransferredToFltNumber(toContainerVO.getFlightNumber());
		transferManifestVO.setTransferredfrmFltSeqNum(toContainerVO.getFrmFltSeqNum());
		transferManifestVO.setTransferredfrmSegSerNum(toContainerVO.getFrmSegSerNum());
		Collection<DespatchDetailsVO> despatchDetailsVOs = new ArrayList<>();
		Collection<DSNVO> dsns = makeDSNVOs(mailbagVOs, despatchDetailsVOs, toContainerVO);
		transferManifestVO.setDsnVOs(dsns);
		log.debug("" + "transferManifestVO--->>>" + " " + transferManifestVO);
		if (toContainerVO.isTransStatus()) {
			transferManifestVO.setStatus(MAIL_OPS_TRAEND);
		}
		new TransferManifest(transferManifestVO);
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = findAirlineDescription(toContainerVO.getCompanyCode(),
					toContainerVO.getCarrierCode());
		} catch (SharedProxyException e) {
			e.getMessage();
		}
		transferManifestVO.setToCarCodeDesc(airlineValidationVO.getAirlineName());
		try {
			airlineValidationVO = findAirlineDescription(toContainerVO.getCompanyCode(),
					toContainerVO.getFromCarrier());
		} catch (SharedProxyException e) {
			e.getMessage();
		}
		transferManifestVO.setFromCarCodeDesc(airlineValidationVO.getAirlineName());
		log.debug(CLASS + " : " + "generateTransferManifest" + " Exiting");
		return transferManifestVO;
	}

	/** 
	* @param
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public LoginProfile getLogonAttributes() {
		return contextUtil.callerLoginProfile();
	}

	/**
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public String findMailServiceLevel(MailbagVO mailBagVO) {
		String serviceLevel = null;
		String paCode_int = null;
		String paCode_dom = null;
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		MailServiceLevelVO mailServiceLevelVO = new MailServiceLevelVO();
		mailServiceLevelVO.setCompanyCode(mailBagVO.getCompanyCode());
		mailServiceLevelVO.setPoaCode(mailBagVO.getPaCode());
		if (StringUtils.equalsIgnoreCase(paCode_int, mailBagVO.getPaCode())) {
			mailServiceLevelVO.setMailCategory(mailBagVO.getMailCategoryCode());
			if (mailBagVO.getMailClass() != null && mailBagVO.getMailClass().length() > 0)
				mailServiceLevelVO.setMailClass(mailBagVO.getMailClass());
			else
				mailServiceLevelVO.setMailClass(HYPHEN);
			if (mailBagVO.getMailSubclass() != null && mailBagVO.getMailSubclass().length() > 0)
				mailServiceLevelVO.setMailSubClass(mailBagVO.getMailSubclass());
			else
				mailServiceLevelVO.setMailSubClass(HYPHEN);
		} else if (StringUtils.equalsIgnoreCase(paCode_dom, mailBagVO.getPaCode())) {
			mailServiceLevelVO.setMailClass(mailBagVO.getMailClass());
			if (mailBagVO.getMailCategoryCode() != null)
				mailServiceLevelVO.setMailCategory(mailBagVO.getMailCategoryCode());
			else
				mailServiceLevelVO.setMailCategory(HYPHEN);
			if (mailBagVO.getMailSubclass() != null)
				mailServiceLevelVO.setMailSubClass(mailBagVO.getMailSubclass());
			else
				mailServiceLevelVO.setMailSubClass(HYPHEN);
		} else {
			return serviceLevel;
		}
		try {
			if (paCode_int.equals(mailBagVO.getPaCode())) {
				serviceLevel = constructDAO().findMailServiceLevelForIntPA(mailServiceLevelVO);
			} else if (paCode_dom.equals(mailBagVO.getPaCode())) {
				serviceLevel = constructDAO().findMailServiceLevelForDomPA(mailServiceLevelVO);
			} else {
				return serviceLevel;
			}
		} catch (PersistenceException ex) {
			log.debug(" ++++  Mail Service Level Not Found  +++");
		}
		return serviceLevel;
	}

	/** 
	* @param newMailbgVOs
	* @param isScanned
	* @return
	* @throws MailHHTBusniessException
	* @throws SystemException
	* @author A-7540
	*/
	public ScannedMailDetailsVO doLATValidation(Collection<MailbagVO> newMailbgVOs, boolean isScanned)
			throws MailHHTBusniessException {
		LoginProfile logon = ContextUtil.getInstance().callerLoginProfile();
		boolean isDomesticFlight = false;
		String orgPaCod = null;
		String paCode_int = null;
		String paCode_dom = null;
		boolean coterminusAvalilable = false;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		LoginProfile logonAttributes = ContextUtil.getInstance().callerLoginProfile();
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ZonedDateTime flightdate = localDateUtil.getLocalDate(null, false);
		if (newMailbgVOs != null && newMailbgVOs.size() > 0) {
			for (MailbagVO mailbagVO : newMailbgVOs) {
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
				paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
				paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
				String originPort = findNearestAirportOfCity(mailbagVO.getCompanyCode(), OOE);
				coterminusAvalilable = validateCoterminusairports(originPort, mailbagVO.getScannedPort(),
						MailConstantsVO.RESDIT_RECEIVED, orgPaCod, mailbagVO.getConsignmentDate());
				if ((originPort != null && originPort.equals(mailbagVO.getScannedPort()))
						|| (coterminusAvalilable && !checkReceivedFromTruckEnabled(mailbagVO.getScannedPort(),
								originPort, orgPaCod, mailbagVO.getConsignmentDate()))) {
					if (postalAdministrationVO != null) {
						if (paCode_int.contains(postalAdministrationVO.getPaCode())
								|| paCode_dom.contains(postalAdministrationVO.getPaCode())) {
							if ("E".equals(postalAdministrationVO.getLatValLevel())
									|| "W".equals(postalAdministrationVO.getLatValLevel())) {
								MailbagHistoryVO mailbagHistoryVO = null;
								if ("-1".equals(mailbagVO.getFlightNumber())
										|| "".equals(mailbagVO.getFlightNumber())) {
									Collection<CarditTransportationVO> carditTransportationVOs = null;
									try {
										String carditKey = null;
										carditKey = Cardit.findCarditForMailbag(mailbagVO.getCompanyCode(),
												mailbagVO.getMailbagId());
										if (carditKey != null) {
											carditTransportationVOs = constructDAO().findCarditTransportationDetails(
													mailbagVO.getCompanyCode(), carditKey);
										}
									} catch (PersistenceException e) {
										e.getMessage();
									}
									if (carditTransportationVOs != null && carditTransportationVOs.size() > 0) {
										for (CarditTransportationVO carditTransportationVO : carditTransportationVOs) {
											if (mailbagVO.getScannedPort()
													.equals(carditTransportationVO.getDeparturePort())) {
												mailbagVO.setFlightNumber(carditTransportationVO.getFlightNumber());
												mailbagVO.setFlightSequenceNumber(
														carditTransportationVO.getFlightSequenceNumber());
												mailbagVO.setFlightDate(carditTransportationVO.getDepartureTime());
												mailbagVO.setCarrierId(carditTransportationVO.getCarrierID());
												mailbagVO.setPol(carditTransportationVO.getDeparturePort());
											}
										}
									}
								}
								if (mailbagVO.getFlightNumber() != null && !mailbagVO.getFlightNumber().isEmpty()) {
									Collection<FlightValidationVO> flightValidationVOs = null;
									try {
										flightValidationVOs = flightOperationsProxy
												.validateFlightForAirport(createFlightFilterVO(mailbagVO));
									} finally {
									}
									Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
									AirportValidationVO orgAirportValidationVO = null;
									AirportValidationVO desAirportValidationVO = null;
									Collection<String> airportCodes = new ArrayList<String>();
									String orgcountryCode = "";
									String destcountryCode = "";
									if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
										for (FlightValidationVO flightValidationVO : flightValidationVOs) {
											String flightRoute = flightValidationVO.getFlightRoute();
											String originAirport = flightRoute.substring(0, 3);
											String destAirport = flightRoute.substring(flightRoute.length() - 3);
											flightdate = LocalDateMapper.toZonedDateTime(flightValidationVO.getFlightDate());
											airportCodes.add(originAirport);
											airportCodes.add(destAirport);
                                                try {
                                                    countryCodeMap = neoMastersServiceUtils
                                                            .validateAirportCodes(mailbagVO.getCompanyCode(), airportCodes);
                                                } catch (AirportBusinessException e) {
                                                   log.error(e.getMessage());
                                                }

											if (countryCodeMap != null) {
												orgAirportValidationVO = countryCodeMap.get(originAirport);
												desAirportValidationVO = countryCodeMap.get(destAirport);
												if (orgAirportValidationVO != null) {
													orgcountryCode = orgAirportValidationVO.getCountryCode();
												}
												if (desAirportValidationVO != null) {
													destcountryCode = desAirportValidationVO.getCountryCode();
												}
											}
											flightdate = LocalDateMapper.toZonedDateTime(flightValidationVO.getFlightDate());
											airportCodes.add(originAirport);
											airportCodes.add(destAirport);
											String ownAirlineCountry = "";
											ownAirlineCountry = findSystemParameterValue(COUNTRY);
											if (orgcountryCode.equals(destcountryCode)
													&& orgcountryCode.equals(ownAirlineCountry)
													&& destcountryCode.equals(ownAirlineCountry)) {
												isDomesticFlight = true;
												log.debug("" + "**********Domestic flight***********" + " "
														+ orgcountryCode + " " + destcountryCode);
											}
											GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
											generalTimeMappingFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
											generalTimeMappingFilterVO.setAirportCode(mailbagVO.getScannedPort());
											generalTimeMappingFilterVO.setConfigurationType("MLT");
											try {
												countryCodeMap = neoMastersServiceUtils
														.validateAirportCodes(mailbagVO.getCompanyCode(), airportCodes);
                                            } catch (AirportBusinessException e) {
                                                log.error(e.getMessage());
                                            }
											if (countryCodeMap != null) {
												orgAirportValidationVO = countryCodeMap.get(originAirport);
												desAirportValidationVO = countryCodeMap.get(destAirport);
											}
											if (orgAirportValidationVO != null) {
												orgcountryCode = orgAirportValidationVO.getCountryCode();
											}
											Collection<GeneralConfigParameterVO> parameterDetailsFilterVO = new ArrayList<GeneralConfigParameterVO>();
											GeneralConfigParameterVO generalConfigParameterVO = new GeneralConfigParameterVO();
											if (isDomesticFlight) {
												generalConfigParameterVO.setConfigurationType("MLT");
												generalConfigParameterVO
														.setCompanyCode(generalTimeMappingFilterVO.getCompanyCode());
												generalConfigParameterVO.setParameterCode("DOMFLT");
												generalConfigParameterVO.setParameterValue("yes");
											}
											parameterDetailsFilterVO.add(generalConfigParameterVO);
											generalTimeMappingFilterVO
													.setParameterDetailsFilterVO(parameterDetailsFilterVO);
											if (desAirportValidationVO != null) {
												destcountryCode = desAirportValidationVO.getCountryCode();
											}
											Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = null;
											ZonedDateTime STD = localDateUtil.getLocalDate(logon.getAirportCode(),
													true);
											ZonedDateTime calculatedSTD = null;
											STD = LocalDateMapper.toZonedDateTime(flightValidationVOs.iterator().next().getStd());
											try {
												generalConfigurationMasterVOs = sharedDefaultsProxy
														.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
											} finally {
											}
											String parvalmin = null;
											String parvalhr = null;
											int min = 0;
											int hour = 0;
											int latOffSetValue = 0;
											ZonedDateTime latestAcceptanceTime = null;
											if (mailbagVO.isScanTimeEntered() && mailbagVO.getScannedDate() != null) {
												latestAcceptanceTime = mailbagVO.getScannedDate();
											} else {
												latestAcceptanceTime = localDateUtil
														.getLocalDate(mailbagVO.getScannedPort(), true);
											}
											if (generalConfigurationMasterVOs != null
													&& generalConfigurationMasterVOs.size() > 0) {
												for (GeneralConfigurationMasterVO genaralTimeMappingVO : generalConfigurationMasterVOs) {
													if ((flightdate.isAfter(LocalDateMapper.toZonedDateTime(genaralTimeMappingVO.getStartDate()))
															&& flightdate.isBefore(LocalDateMapper.toZonedDateTime(genaralTimeMappingVO.getEndDate())))
															|| flightdate.isEqual(genaralTimeMappingVO.getStartDate().toZonedDateTime())
															|| flightdate.isEqual(genaralTimeMappingVO.getEndDate().toZonedDateTime())) {
														Collection<GeneralRuleConfigDetailsVO> time = getTimedetails(
																genaralTimeMappingVO);
														if (genaralTimeMappingVO.getParameterDetails() != null
																&& genaralTimeMappingVO.getParameterDetails()
																		.size() > 0) {
															for (GeneralConfigParameterVO parameterDetailsVO : genaralTimeMappingVO
																	.getParameterDetails()) {
																if (isDomesticFlight
																		&& "DOMFLT".equals(
																				parameterDetailsVO.getParameterCode())
																		&& "yes".equals(parameterDetailsVO
																				.getParameterValue())) {
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
																	latOffSetValue = (min + hour * 60);
																	calculatedSTD = STD.plusMinutes(-(latOffSetValue));
																} else if (!isDomesticFlight
																		&& "DOMFLT".equals(
																				parameterDetailsVO.getParameterCode())
																		&& "yes".equals(parameterDetailsVO
																				.getParameterValue())) {
																	calculatedSTD = null;
																} else if (!isDomesticFlight
																		&& !("DOMFLT".equals(
																				parameterDetailsVO.getParameterCode()))
																		&& !("yes".equals(parameterDetailsVO
																				.getParameterValue()))) {
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
																	latOffSetValue = (min + hour * 60);
																	calculatedSTD = STD.plusMinutes(-(latOffSetValue));
																}
															}
														} else {
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
															latOffSetValue = (min + hour * 60);
															calculatedSTD = STD.plusMinutes(-(latOffSetValue));
														}
													}
												}
											}
											if (calculatedSTD != null) {
												if (latestAcceptanceTime.isAfter(STD)) {
													if ((!isScanned || "MTK060".equals(mailbagVO.getMailSource()))
															&& ("Y".equals(mailbagVO.getLatValidationNeeded()))) {
														if ("E".equals(postalAdministrationVO.getLatValLevel())) {
															throw new MailHHTBusniessException(
																	MailHHTBusniessException.LAT_VIOLATED_ERR);
														} else
															throw new MailHHTBusniessException(
																	MailHHTBusniessException.LAT_VIOLATED_WAR);
													} else {
														if ("E".equals(postalAdministrationVO.getLatValLevel())) {
															scannedMailDetailsVO.setErrorDescription(
																	MailHHTBusniessException.LAT_VIOLATED_ERR);
															mailbagVO.setLatestAcceptanceTime(STD);
														} else
															scannedMailDetailsVO.setErrorDescription(
																	MailHHTBusniessException.LAT_VIOLATED_WAR);
														mailbagVO.setLatestAcceptanceTime(STD);
													}
												} else {
													mailbagVO.setLatestAcceptanceTime(STD);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		scannedMailDetailsVO.setMailDetails(newMailbgVOs);
		return scannedMailDetailsVO;
	}

	private FlightFilterVO createFlightFilterVO(MailbagVO mailbagVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
		flightFilterVO.setStation(mailbagVO.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		return flightFilterVO;
	}

	/** 
	* @param mailbagVOs
	* @param toContainerVO
	* @return
	* @throws SystemException
	* @throws FlightClosedException
	* @throws ReassignmentException
	* @throws InvalidFlightSegmentException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @author A-8236
	*/
	public Collection<ContainerDetailsVO> reassignMailbagsfromAndroid(Collection<MailbagVO> mailbagVOs,
			ContainerVO toContainerVO) throws FlightClosedException, ReassignmentException,
			InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException {
		String reassignSystemPar = null;
		try {
			reassignSystemPar = findSystemParameterValue(MAIL_REASSIGN_FROM_CLOSED_FLIGHT);
		} finally {
		}
		Collection<MailbagVO> mailbagsToReassign = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagsToTsfr = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> conDetVO = null;
		validateMailbagVOs(mailbagVOs, toContainerVO);
		if (toContainerVO.getFlightSequenceNumber() > 0) {
			groupMailbagsForReassignMailBags(mailbagVOs, mailbagsToReassign, mailbagsToTsfr);
			log.debug("" + "maiblags to transfer " + " " + mailbagsToTsfr);
			log.debug("" + "maiblags to reassign " + " " + mailbagsToReassign);
		} else {
			mailbagsToReassign = mailbagVOs;
		}
		if (mailbagsToReassign != null && mailbagsToReassign.size() > 0) {
			try {
				ReassignController reassignController = ContextUtil.getInstance().getBean(ReassignController.class);
				conDetVO = reassignController.reassignMailbags(mailbagsToReassign, toContainerVO);
				String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagMLDForMailReassignOperations(mailbagsToReassign, toContainerVO, "ALL");
				}
			} catch (FlightClosedException e) {
				if (MailConstantsVO.FLAG_NO.equals(reassignSystemPar)) {
					throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED);
				} else
					performOffloadAndReassign(mailbagVOs, toContainerVO);
			}
		}
		if (mailbagsToTsfr != null && mailbagsToTsfr.size() > 0) {
			mailTransfer.transferMailbags(mailbagsToTsfr, toContainerVO);
		}
		return conDetVO;
	}

	/** 
	* @param currentAirport
	* @param paCode
	* @return receiveFromTruckEnabled
	* @throws SystemException
	* @author A-7871Used for ICRD-240184
	*/
	public boolean checkReceivedFromTruckEnabled(String currentAirport, String orginAirport, String paCode,
			ZonedDateTime dspDate) {
		String receiveFromTruckEnabled = null;
		boolean receiveFromTruck = false;
		receiveFromTruckEnabled = constructDAO().checkReceivedFromTruckEnabled(currentAirport, orginAirport, paCode,
				dspDate);
		if (receiveFromTruckEnabled != null)
			receiveFromTruck = true;
		return receiveFromTruck;
	}

	/** 
	* Added for ICRD-255189
	* @return
	* @throws SystemException
	*/
	public MailbagVO constructOriginDestinationDetails(MailbagVO mailbagVO) {
		Collection<String> officeOfExchanges = new ArrayList<String>();
		HashMap<String, String> resultSetMap = new HashMap<String, String>();
		officeOfExchanges.add(mailbagVO.getOoe());
		officeOfExchanges.add(mailbagVO.getDoe());
		resultSetMap = findAirportForOfficeOfExchange(mailbagVO.getCompanyCode(), officeOfExchanges);
		if (resultSetMap != null) {
			if (resultSetMap.containsKey(mailbagVO.getOoe()) && resultSetMap.get(mailbagVO.getOoe()) != null) {
				mailbagVO.setOrigin(resultSetMap.get(mailbagVO.getOoe()));
			}
			if (resultSetMap.containsKey(mailbagVO.getDoe()) && resultSetMap.get(mailbagVO.getDoe()) != null) {
				mailbagVO.setDestination(resultSetMap.get(mailbagVO.getDoe()));
			}
		}
		return mailbagVO;
	}

	/** 
	* Method		:	MailController.findRoutingIndex Added by 	:	A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routingIndexVO Parameters	:	@return Return type	: 	Collection<RoutingIndexVO>
	* @throws SystemException
	*/
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) {
		return MailAcceptance.findRoutingIndex(routingIndexVO);
	}

	/**
	* @param mailbag
	* @return
	* @throws SystemException
	* @throws DuplicateMailBagsException
	* @author A-8353
	*/
	public boolean checkForDuplicateMailbag(String companyCode, String paCode, Mailbag mailbag)
			throws DuplicateMailBagsException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		PostalAdministrationVO postalAdministrationVO = findPACode(companyCode, paCode);
		
		ZonedDateTime currentDate = localDateUtil.getLocalDate(null, true);
		ZonedDateTime dspDate = localDateUtil.getLocalDateTime(mailbag.getDespatchDate(), null);
		if (postalAdministrationVO.getDupMailbagPeriod() == 0) {
			return false;
		}
		
		Duration duration = Duration.between(currentDate, dspDate);
		long seconds = duration.getSeconds();
		long days = seconds / 86400000;
		if ((days) <= postalAdministrationVO.getDupMailbagPeriod()) {
			return false;
		}
		return true;
	}

	/** 
	* @author A-7794
	* @param containerDetailsVOs
	* @param mailbagVOs
	* @param triggerPoint
	* @return
	* @throws SystemException
	*/
	public Collection<RateAuditVO> createRateAuditVOsForReturn(Collection<ContainerDetailsVO> containerDetailsVOs,
			Collection<MailbagVO> mailbagVOs, String triggerPoint) {
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO mailbag : mailbags) {
						if (mailbag.getOperationalFlag() != null && !mailbag.getOperationalFlag().isEmpty()) {
							RateAuditVO rateAuditVO = new RateAuditVO();
							rateAuditVO.setMailSequenceNumber(
									mailbag.getMailSequenceNumber() > 0 ? mailbag.getMailSequenceNumber()
											: findMailSequenceNumber(mailbag.getMailbagId(), mailbag.getCompanyCode()));
							rateAuditVO.setCompanyCode(mailbag.getCompanyCode());
							rateAuditVO.setTriggerPoint(triggerPoint);
							rateAuditVO.setBillingBasis(mailbag.getMailbagId());
							rateAuditVO.setMailbagId(mailbag.getMailbagId());
							rateAuditVO.setOperationFlag(mailbag.getOperationalFlag());
							rateAuditVO.setPcs("1");
							OfficeOfExchangeVO officeOfExchangeVOForOOE = validateOfficeOfExchange(
									mailbag.getCompanyCode(), mailbag.getOoe());
							if (mailbag.getPaCode() != null) {
								rateAuditVO.setGpaCode(mailbag.getPaCode());
							} else {
								rateAuditVO.setGpaCode(officeOfExchangeVOForOOE.getPoaCode());
							}
							rateAuditVO.setOriginCityCode(officeOfExchangeVOForOOE.getCityCode());
							OfficeOfExchangeVO officeOfExchangeVOForDOE = validateOfficeOfExchange(
									mailbag.getCompanyCode(), mailbag.getDoe());
							rateAuditVO.setDestinationCityCode(officeOfExchangeVOForDOE.getCityCode());
							rateAuditVO.setOrigin(findNearestAirportOfCity(mailbag.getCompanyCode(), mailbag.getOoe()));
							rateAuditVO.setDestination(
									findNearestAirportOfCity(mailbag.getCompanyCode(), mailbag.getDoe()));
							rateAuditVO.setConDocNum(mailbag.getConsignmentNumber());
							rateAuditVO.setConSerNum(mailbag.getConsignmentSequenceNumber());
							rateAuditVO.setOriginOE(mailbag.getOoe());
							rateAuditVO.setDestinationOE(mailbag.getDoe());
							rateAuditVO.setCategory(mailbag.getMailCategoryCode());
							rateAuditVO.setSubClass(mailbag.getMailSubclass());
							rateAuditVO.setYear(mailbag.getYear());
							rateAuditVO.setDsn(mailbag.getDespatchSerialNumber());
							rateAuditVO.setRsn(mailbag.getReceptacleSerialNumber());
							rateAuditVO.setHsn(mailbag.getHighestNumberedReceptacle());
							rateAuditVO.setRegInd(mailbag.getRegisteredOrInsuredIndicator());
							if (mailbag.getWeight() != null)
								rateAuditVO.setGrossWt(mailbag.getWeight().getValue().doubleValue());
							if (mailbag.getScannedDate() != null) {
								rateAuditVO.setReceivedDate(LocalDateMapper.toLocalDate(mailbag.getScannedDate()));
							} else {
								rateAuditVO.setReceivedDate(new com.ibsplc.icargo.framework.util.time.LocalDate(
										mailbag.getScannedPort(), Location.ARP, true));
							}
							rateAuditVO.setTransfercarcode(mailbag.getTransferFromCarrier());
							rateAuditVO.setMailCompanyCode(mailbag.getMailCompanyCode());
							Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
							RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
							rateAuditDetailsVO.setBillingBasis(rateAuditVO.getMailbagId());
							rateAuditDetailsVO.setMailbagId(rateAuditVO.getMailbagId());
							rateAuditDetailsVO.setMailSequenceNumber(rateAuditVO.getMailSequenceNumber());
							rateAuditDetailsVO.setCarrierid(mailbag.getCarrierId());
							rateAuditDetailsVO.setCarrierCode(mailbag.getCarrierCode());
							rateAuditDetailsVO.setFlightno(mailbag.getFlightNumber());
							rateAuditDetailsVO.setFlightseqno((int) mailbag.getFlightSequenceNumber());
							rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbag.getFlightDate()));
							rateAuditDetailsVO.setSegSerNo(mailbag.getSegmentSerialNumber());
							if (mailbag.getFlightDate() != null && mailbag.getFlightSequenceNumber() <= 0) {
								rateAuditDetailsVO.setFlightno(containerDetailsVO.getFlightNumber());
								rateAuditDetailsVO.setFlightseqno((int) containerDetailsVO.getFlightSequenceNumber());
								rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerDetailsVO.getFlightDate()));
								rateAuditDetailsVO.setSegSerNo(containerDetailsVO.getSegmentSerialNumber());
							}
							rateAuditDetailsVO.setContainerNumber(containerDetailsVO.getContainerNumber());
							rateAuditDetailsVO.setContainerType(containerDetailsVO.getContainerType());
							rateAuditDetailsVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
							rateAuditDetailsVO.setUldno(containerDetailsVO.getContainerNumber());
							rateAuditDetailsVO.setSecFrom(containerDetailsVO.getPol());
							rateAuditDetailsVO.setSecTo(containerDetailsVO.getPou());
							rateAuditDetailsVO.setSource(triggerPoint);
							rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate( mailbag.getScannedDate()));
							rateAuditDetails.add(rateAuditDetailsVO);
							rateAuditVO.setRateAuditDetails(rateAuditDetails);
							if (rateAuditVO.getMailSequenceNumber() != 0) {
								rateAuditVOs.add(rateAuditVO);
							}
						}
					}
				}
			}
		} else {
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbag : mailbagVOs) {
					RateAuditVO rateAuditVO = new RateAuditVO();
					rateAuditVO
							.setMailSequenceNumber(mailbag.getMailSequenceNumber() > 0 ? mailbag.getMailSequenceNumber()
									: findMailSequenceNumber(mailbag.getMailbagId(), mailbag.getCompanyCode()));
					rateAuditVO.setCompanyCode(mailbag.getCompanyCode());
					rateAuditVO.setTriggerPoint(triggerPoint);
					rateAuditVO.setBillingBasis(mailbag.getMailbagId());
					rateAuditVO.setMailbagId(mailbag.getMailbagId());
					rateAuditVO.setOperationFlag(mailbag.getOperationalFlag());
					rateAuditVO.setPcs("1");
					OfficeOfExchangeVO officeOfExchangeVOForOOE = validateOfficeOfExchange(mailbag.getCompanyCode(),
							mailbag.getOoe());
					if (mailbag.getPaCode() != null) {
						rateAuditVO.setGpaCode(mailbag.getPaCode());
					} else {
						rateAuditVO.setGpaCode(officeOfExchangeVOForOOE.getPoaCode());
					}
					rateAuditVO.setOriginCityCode(officeOfExchangeVOForOOE.getCityCode());
					OfficeOfExchangeVO officeOfExchangeVOForDOE = validateOfficeOfExchange(mailbag.getCompanyCode(),
							mailbag.getDoe());
					rateAuditVO.setDestinationCityCode(officeOfExchangeVOForDOE.getCityCode());
					rateAuditVO.setOrigin(findNearestAirportOfCity(mailbag.getCompanyCode(), mailbag.getOoe()));
					rateAuditVO.setDestination(findNearestAirportOfCity(mailbag.getCompanyCode(), mailbag.getDoe()));
					rateAuditVO.setConDocNum(mailbag.getConsignmentNumber());
					rateAuditVO.setConSerNum(mailbag.getConsignmentSequenceNumber());
					rateAuditVO.setOriginOE(mailbag.getOoe());
					rateAuditVO.setDestinationOE(mailbag.getDoe());
					rateAuditVO.setCategory(mailbag.getMailCategoryCode());
					rateAuditVO.setSubClass(mailbag.getMailSubclass());
					rateAuditVO.setYear(mailbag.getYear());
					rateAuditVO.setDsn(mailbag.getDespatchSerialNumber());
					rateAuditVO.setRsn(mailbag.getReceptacleSerialNumber());
					rateAuditVO.setHsn(mailbag.getHighestNumberedReceptacle());
					rateAuditVO.setRegInd(mailbag.getRegisteredOrInsuredIndicator());
					if (mailbag.getWeight() != null)
						rateAuditVO.setGrossWt(mailbag.getWeight().getValue().doubleValue());
					if (mailbag.getScannedDate() != null) {
						rateAuditVO.setReceivedDate(LocalDateMapper.toLocalDate(mailbag.getScannedDate()));
					} else {
						rateAuditVO.setReceivedDate(new com.ibsplc.icargo.framework.util.time.LocalDate(
								mailbag.getScannedPort(), Location.ARP, true));
					}
					rateAuditVO.setTransfercarcode(mailbag.getTransferFromCarrier());
					rateAuditVO.setMailCompanyCode(mailbag.getMailCompanyCode());
					Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
					RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
					rateAuditDetailsVO.setBillingBasis(rateAuditVO.getMailbagId());
					rateAuditDetailsVO.setMailbagId(rateAuditVO.getMailbagId());
					rateAuditDetailsVO.setMailSequenceNumber(rateAuditVO.getMailSequenceNumber());
					rateAuditDetailsVO.setCarrierid(mailbag.getCarrierId());
					rateAuditDetailsVO.setCarrierCode(mailbag.getCarrierCode());
					rateAuditDetailsVO.setFlightno(mailbag.getFlightNumber());
					rateAuditDetailsVO.setFlightseqno((int) mailbag.getFlightSequenceNumber());
					rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbag.getFlightDate()));
					rateAuditDetailsVO.setSegSerNo(mailbag.getSegmentSerialNumber());
					rateAuditDetailsVO.setSource(triggerPoint);
					rateAuditDetails.add(rateAuditDetailsVO);
					rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbag.getScannedDate()));
					rateAuditVO.setRateAuditDetails(rateAuditDetails);
					if (rateAuditVO.getMailSequenceNumber() != 0) {
						rateAuditVOs.add(rateAuditVO);
					}
				}
			}
		}
		return rateAuditVOs;
	}

	private ContainerPK constructContainerPK(ContainerDetailsVO containerDetailsVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerPK.setAssignmentPort(containerDetailsVO.getAssignedPort());
		containerPK.setCarrierId(containerDetailsVO.getCarrierId());
		containerPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		return containerPK;
	}

	private ULDAtAirportPK constructULDAtAirportPK(ContainerDetailsVO containerDetailsVO) {
		ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setAirportCode(containerDetailsVO.getAssignedPort());
		uldAtAirportPK.setCarrierId(containerDetailsVO.getCarrierId());
		uldAtAirportPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		if ("B".equals(containerDetailsVO.getContainerType())) {
			uldAtAirportPK.setUldNumber("BULK-" + (containerDetailsVO.getPou() != null ? containerDetailsVO.getPou()
					: containerDetailsVO.getDestination()));
		} else {
			uldAtAirportPK.setUldNumber(containerDetailsVO.getContainerNumber());
		}
		return uldAtAirportPK;
	}

	/** 
	* Added for /IASCB-32332
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public MailbagVO constructOriginDestinationDetailsForConsignment(MailInConsignmentVO mailInConsignmentVO,
			MailbagVO mailbagVO) {
		Collection<String> officeOfExchanges = new ArrayList<String>();
		HashMap<String, String> resultSetMap = new HashMap<String, String>();
		officeOfExchanges.add(mailbagVO.getOoe());
		officeOfExchanges.add(mailbagVO.getDoe());
		resultSetMap = findAirportForOfficeOfExchange(mailbagVO.getCompanyCode(), officeOfExchanges);
		if (resultSetMap != null) {
			if (resultSetMap.containsKey(mailbagVO.getOoe()) && (mailInConsignmentVO.getMailOrigin() == null
					|| "".equals(mailInConsignmentVO.getMailOrigin()))) {
				mailbagVO.setOrigin(resultSetMap.get(mailbagVO.getOoe()));
			} else {
				mailbagVO.setOrigin(mailInConsignmentVO.getMailOrigin());
			}
			if (resultSetMap.containsKey(mailbagVO.getDoe()) && (mailInConsignmentVO.getMailDestination() == null
					|| "".equals(mailInConsignmentVO.getMailDestination()))) {
				mailbagVO.setDestination(resultSetMap.get(mailbagVO.getDoe()));
			} else {
				mailbagVO.setDestination(mailInConsignmentVO.getMailDestination());
			}
		}
		return mailbagVO;
	}

	/** 
	* Method		:	MailController.getTotalMailWeight Added by 	:	U-1467 on 18-Apr-2020 Used for 	:	Method to get total mail weight from mailbagVOs Parameters	:	@param mailbagVOs Parameters	:	@return Return type	: 	Measure
	*/
	private Quantity getTotalMailWeight(Collection<MailbagVO> mailbagVOs) {
		log.debug(CLASS + " : " + "getTotalMailWeight" + " Entering");
		Quantity totalWeight = null;
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (totalWeight == null) {
					if (mailbagVO.getWeight() != null) {
						totalWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
								BigDecimal.valueOf(0.0), mailbagVO.getWeight().getDisplayUnit().getName());
					} else {
						totalWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0));
					}
				}
				try {
					totalWeight = totalWeight.add(mailbagVO.getWeight());
				} finally {
				}
			}
		}
		log.debug(CLASS + " : " + "getTotalMailWeight" + " Exiting");
		return totalWeight;
	}

	/** 
	* Method		:	MailController.getUldTareWeight Added by 	:	U-1467 on 18-Apr-2020 Used for 	:	Method to get Tare wt for a ULD Parameters	:	@param companyCode Parameters	:	@param uldNumber Parameters	:	@return Return type	: 	Measure
	*/
	public Quantity getUldTareWeight(String companyCode, String uldNumber) {
		log.debug(CLASS + " : " + "getUldTareWeight" + " Entering");
		Quantity tareWeight = null;
		ULDValidationFilterVO uldValidationFilterVO = new ULDValidationFilterVO();
		uldValidationFilterVO.setCompanyCode(companyCode);
		uldValidationFilterVO.setUldTypeCode(uldNumber.substring(0, 3));
		uldValidationFilterVO.setSerialNumber(uldNumber.substring(3, uldNumber.length() - 2));
		uldValidationFilterVO.setUldAirlineCode(uldNumber.substring(uldNumber.length() - 2));
		uldValidationFilterVO.setUldNumber(uldNumber);
		try {
			tareWeight = sharedULDProxy.findULDTareWeight(uldValidationFilterVO);
		} catch (SharedProxyException e) {
			log.error(e.getMessage());
		}
		log.debug(CLASS + " : " + "getUldTareWeight" + " Exiting");
		return tareWeight;
	}

	/** 
	* For transfer mail flow, if the values need to be synced to DWS the container actual weight should be updated in Container. If the container is ULD then Actual Wt = Tare wt + Selected mail Wt + Current container actual wt If the container is Bulk/Barrow then Actual Wt = Total mail Wt + Current container actual wt Transfer flow need not check if ULD has actual weight captured, it needs to be handled as business process as per current requirement Tare wt needs to be added only the first time, the container actual weight will have the latest Tare Wt+ total mail wt
	*/
	private Quantity getContainerActualWeight(ContainerVO toContainerVO, Collection<MailbagVO> mailbagVOs,
			double containerActualWeight) {
		log.debug(CLASS + " : " + "getContainerActualWeight" + " Entering");
		Quantity actualWeight = null;
		Quantity actualTareWeight = null;
		actualWeight = getTotalMailWeight(mailbagVOs);
		if (actualWeight != null) {
			if (MailConstantsVO.ULD_TYPE.equals(toContainerVO.getType()) && containerActualWeight < 0.01) {
				actualTareWeight = getUldTareWeight(toContainerVO.getCompanyCode(), toContainerVO.getContainerNumber());
				if (actualTareWeight != null) {
					actualWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
							BigDecimal.valueOf(containerActualWeight + actualWeight.getValue().doubleValue()),
							actualWeight.getDisplayUnit().getName());
				}
			} else {
				actualWeight = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
						BigDecimal.valueOf(containerActualWeight + actualWeight.getValue().doubleValue()),
						actualWeight.getDisplayUnit().getName());
			}
		}
		log.debug(CLASS + " : " + "getContainerActualWeight" + " Exiting");
		return actualWeight;
	}

	/** 
	* Method		:	MailController.isAutoArrivalEnabled Added by 	:	A-8061 on 21-Apr-2020 Used for 	:	IASCB-48445 Parameters	:	@param functionalPoint Parameters	:	@return Return type	: 	boolean
	*/
	public boolean isAutoArrivalEnabled(String functionalPoint) {
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		boolean enableAutoArrival = false;
		String sysparfunctionpoints = null;
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap =neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return enableAutoArrival;
		}
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
		}
		if (sysparfunctionpoints != null && sysparfunctionpoints.contains(functionalPoint)) {
			enableAutoArrival = true;
		}
		return enableAutoArrival;
	}

	/** 
	* @author A-10383Added for Mail Operations transfer out in one outtransaction
	* @return
	*/
	public boolean mailOperationsTransferTransaction() {
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(MAIL_OPERATIONS_TRANSFER_TRANSACTION);
		boolean mailoperationstransfer = false;
		String sysparfunctionpoints = null;
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(MAIL_OPERATIONS_TRANSFER_TRANSACTION);
		}
		if (sysparfunctionpoints != null && MailConstantsVO.FLAG_YES.equals(sysparfunctionpoints)) {
			mailoperationstransfer = true;
		}
		return mailoperationstransfer;
	}

	/** 
	* @author A-5526Added as part of CRQ IASCB-44518
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findMailbagsFromOALinResditProcessing(String containerNumber, String companyCode) {
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		try {
			mailbags = constructDAO().findMailbagsFromOALinResditProcessing(containerNumber, companyCode);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
		return mailbags;
	}

	/** 
	* Method		:	MailController.constructOpFlightFromContainer Added by 	:	A-8061 on 28-Apr-2020 Used for 	:	IASCB-48967 Parameters	:	@param containerVO Parameters	:	@param isInbound Parameters	:	@return Return type	: 	OperationalFlightVO
	*/
	public OperationalFlightVO constructOpFlightFromContainer(ContainerVO containerVO, boolean isInbound) {
		OperationalFlightVO opFlightVO = new OperationalFlightVO();
		opFlightVO.setCompanyCode(containerVO.getCompanyCode());
		opFlightVO.setCarrierId(containerVO.getCarrierId());
		opFlightVO.setFlightNumber(containerVO.getFlightNumber());
		opFlightVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		opFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		opFlightVO.setPol(containerVO.getPol());
		opFlightVO.setPou(containerVO.getPou());
		if (isInbound) {
			opFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
			opFlightVO.setAirportCode(containerVO.getPou());
		} else {
			opFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
			opFlightVO.setAirportCode(containerVO.getPol());
		}
		opFlightVO.setFlightDate(containerVO.getFlightDate());
		opFlightVO.setOwnAirlineCode(containerVO.getOwnAirlineCode());
		opFlightVO.setOwnAirlineId(containerVO.getOwnAirlineId());
		return opFlightVO;
	}

	/** 
	* Method		:	MailController.calculateAndUpdateLatestAcceptanceTime Added by 	:	U-1467 on 30-Apr-2020 Used for 	:	Calculate and Update Latest acceptance time to Mailbag Parameters	:	@param mailbagVO Parameters	:	@throws SystemException Return type	: 	void
	*/
	//TODO : Static keyword removed
	@SneakyThrows
    public void calculateAndUpdateLatestAcceptanceTime(MailbagVO mailbagVO)  {
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVOForLAT = new MailbagVO();
		MailOperationsMapper mailOperationsMapper = ContextUtil.getInstance().getBean(MailOperationsMapper.class);
		mailbagVOForLAT = mailOperationsMapper.copyMailbagVO(mailbagVO);
		boolean isLATCalculated = false;
		mailbagVOs.add(mailbagVOForLAT);
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		scannedMailDetailsVO = doLATValidation(mailbagVOs, true);
		if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
				&& scannedMailDetailsVO.getMailDetails().size() > 0) {
			String uspsDomesticPA =findSystemParameterValue(MailConstantsVO.USPS_DOMESTIC_PA);
			for (MailbagVO mailbagVOWithLAT : scannedMailDetailsVO.getMailDetails()) {
				if (mailbagVOWithLAT.getLatestAcceptanceTime() != null) {
					mailbagVO.setLatestAcceptanceTime(mailbagVOWithLAT.getLatestAcceptanceTime());
					isLATCalculated = true;
				}
				if (!isLATCalculated && isNotNullAndEmpty(mailbagVO.getPaCode())) {
					if (!(mailbagVO.getPaCode().equals(uspsDomesticPA))) {
						ZonedDateTime statedTimeOfDeparture = null;
						if (mailbagVO.getFlightDate() != null
								&& !(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getLatestStatus())
										|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getLatestStatus()))) {
							//TODO: Neo to correct below code- refer classic
							statedTimeOfDeparture = mailbagVO.getFlightDate();
							GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
							generalTimeMappingFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
							generalTimeMappingFilterVO.setAirportCode(mailbagVO.getScannedPort());
							generalTimeMappingFilterVO
									.setConfigurationType(MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_TYPE);
							if (mailbagVO.getPaCode().equals(uspsDomesticPA)) {
								Collection<GeneralConfigParameterVO> generalConfigParameterVOS = new ArrayList<>();
								GeneralConfigParameterVO generalConfigParameterVO = new GeneralConfigParameterVO();
								generalConfigParameterVO.setCompanyCode(mailbagVO.getCompanyCode());
								generalConfigParameterVO
										.setConfigurationType(MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_TYPE);
								generalConfigParameterVO
										.setParameterCode(MailConstantsVO.MAIL_LAT_OFFSET_PARAMETER_CODE);
								generalConfigParameterVO
										.setParameterValue(MailConstantsVO.MAIL_LAT_OFFSET_PARAMETER_VALUE);
								generalConfigParameterVOS.add(generalConfigParameterVO);
								generalTimeMappingFilterVO.setParameterDetailsFilterVO(generalConfigParameterVOS);
							}
							mailbagVO.setLatestAcceptanceTime(statedTimeOfDeparture.plusMinutes(
									-getLATConfigurationValue(generalTimeMappingFilterVO, statedTimeOfDeparture)));
							isLATCalculated = true;
						} else if (mailbagVO.getScannedDate() != null && mailbagVO.getScannedPort() != null) {
							mailbagVO.setLatestAcceptanceTime(mailbagVO.getScannedDate());
							isLATCalculated = true;
						} else {
							mailbagVO.setLatestAcceptanceTime(getLocalDate(null, "", true));
							isLATCalculated = true;
						}
					}
				}
				if (!isLATCalculated && isNotNullAndEmpty(mailbagVO.getPaCode())) {
					if (mailbagVO.getPaCode().equals(uspsDomesticPA)) {
						mailbagVO.setLatestAcceptanceTime(getLATFromRoutingIndex(mailbagVO));
						isLATCalculated = true;
					}
				}
			}
		}
	}

	/** 
	* Method		:	MailController.getLATFromRoutingIndex Added by 	:	U-1467 on 30-Apr-2020 Used for 	:	IASCB-48892 Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	LocalDate
	*/
	//TODO : Static keyword removed
	private ZonedDateTime getLATFromRoutingIndex(MailbagVO mailbagVO) {
		if (mailbagVO.getMailbagId().length() == 12) {
			String routIndex = mailbagVO.getMailbagId().substring(4, 8);
			Collection<RoutingIndexVO> routingIndexVOs;
			RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
			routingIndexFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
			routingIndexFilterVO.setRoutingIndex(routIndex);
			routingIndexFilterVO.setScannedDate(mailbagVO.getScannedDate());
			routingIndexVOs = findRoutingIndex(routingIndexFilterVO);
			ZonedDateTime statedTimeOfDeparture = null;
			if (routingIndexVOs != null && routingIndexVOs.size() > 0) {
				for (RoutingIndexVO routingIndexVO : routingIndexVOs) {
					Collection<RoutingIndexLegVO> routingIndexLegVOs = routingIndexVO.getRoutingIndexLegVO();
					if (routingIndexLegVOs != null && routingIndexLegVOs.size() > 0) {
						for (RoutingIndexLegVO routingIndexLegVO : routingIndexLegVOs) {
							if (isNotNullAndEmpty(mailbagVO.getOrigin())
									&& mailbagVO.getOrigin().equals(routingIndexLegVO.getLegOrg())) {
								if (isNotNullAndEmpty(routingIndexLegVO.getLegFlight())) {
									FlightFilterVO flightFilterVO = new FlightFilterVO();
									flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
									flightFilterVO.setFlightNumber(routingIndexLegVO.getLegFlight()
											.length() < MailConstantsVO.FLIGHT_NUMBER_LENGTH
													? formatFlightNumber(routingIndexLegVO.getLegFlight())
													: routingIndexLegVO.getLegFlight());
									flightFilterVO.setStation(routingIndexLegVO.getLegOrg());
									flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
									flightFilterVO.setActiveAlone(true);
									flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getScannedDate()));
									flightFilterVO.setCarrierCode(routingIndexLegVO.getLegRoute());
									Collection<FlightValidationVO> flightValidationVOs;
									flightValidationVOs = validateFlight(flightFilterVO);
									if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
										ZonedDateTime std = flightValidationVOs.iterator().next().getStd().toZonedDateTime();
										statedTimeOfDeparture = getLocalDate(null,
												std.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)), false);
									}
								}
								if (statedTimeOfDeparture == null) {
									statedTimeOfDeparture = getLocalDate(null,
											appendDateTime(mailbagVO.getScannedDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)),
													routingIndexLegVO.getLegDepTime()),
											true);
								}
								Collection<GeneralConfigParameterVO> generalConfigParameterVOS = new ArrayList<>();
								GeneralConfigParameterVO generalConfigParameterVO = new GeneralConfigParameterVO();
								GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
								generalTimeMappingFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
								generalTimeMappingFilterVO.setAirportCode(mailbagVO.getScannedPort());
								generalTimeMappingFilterVO
										.setConfigurationType(MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_TYPE);
								generalConfigParameterVO.setCompanyCode(mailbagVO.getCompanyCode());
								generalConfigParameterVO
										.setConfigurationType(MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_TYPE);
								generalConfigParameterVO
										.setParameterCode(MailConstantsVO.MAIL_LAT_OFFSET_PARAMETER_CODE);
								generalConfigParameterVO
										.setParameterValue(MailConstantsVO.MAIL_LAT_OFFSET_PARAMETER_VALUE);
								generalConfigParameterVOS.add(generalConfigParameterVO);
								generalTimeMappingFilterVO.setParameterDetailsFilterVO(generalConfigParameterVOS);
								return statedTimeOfDeparture.plusMinutes(
										-getLATConfigurationValue(generalTimeMappingFilterVO, statedTimeOfDeparture));
							}
						}
					}
				}
			}
		}
		return null;
	}

	public ZonedDateTime getLocalDate(@Nullable String airportCode, String stringDate, boolean isTimePresent) {
		var localDate = new LocalDate();

		if (StringUtils.isNotEmpty(stringDate)) {
			var zonedDateTime = localDate.getLocalDate(airportCode, isTimePresent);
			return isTimePresent ? localDate.withDateAndTime(zonedDateTime, stringDate) : localDate.withDate(zonedDateTime, stringDate);
		}

		return localDate.getLocalDate(null, isTimePresent);
	}

	/** 
	* Method		:	MailController.appendDateTime Added by 	:	U-1467 on 30-Apr-2020 Used for 	:	Generic method to add time and date and return as string Parameters	:	@param date Parameters	:	@param time Parameters	:	@return Return type	: 	String
	*/
	public static String appendDateTime(String date, String time) {
		String dateTime = null;
		if (isNotNullAndEmpty(date) && isNotNullAndEmpty(time)) {
			StringBuilder sb = new StringBuilder();
			sb.append(date).append(" ").append(time, 0, 2).append(":").append(time, 2, 4).append(":00");
			dateTime = sb.toString();
		}
		return dateTime;
	}

	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

	/** 
	* Method		:	MailController.formatFlightNumber Added by 	:	U-1467 on 06-May-2020 Used for 	:	Generic method to format flight number Parameters	:	@param flightNumber Parameters	:	@return Return type	: 	String
	*/
	public static String formatFlightNumber(String flightNumber) {
		int lastCharInAscii = flightNumber.charAt(flightNumber.length() - 1);
		int flightNumberLength = MailConstantsVO.FLIGHT_NUMBER_LENGTH;
		if (!((lastCharInAscii >= 65) && (lastCharInAscii <= 90))) {
			flightNumberLength = flightNumberLength - 1;
		}
		int idx = flightNumberLength - flightNumber.length();
		while (idx != 0) {
			flightNumber = MailConstantsVO.PAD_DIGIT + flightNumber;
			idx--;
		}
		return flightNumber;
	}

	/** 
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findMailbagsForPABuiltContainerSave(String containerNumber, String companyCode,
			ZonedDateTime fromDate, ZonedDateTime toDate) {
		log.debug(CLASS + " : " + "findMailbagsForPABuiltContainerSave" + " Entering");
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		try {
			mailbags = constructDAO().findMailbagsForPABuiltContainerSave(containerNumber, companyCode, fromDate,
					toDate);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}
		return mailbags;
	}

	private FlightFilterVO createFlightFilterVO_atd(ContainerVO containerVo) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerVo.getCompanyCode());
		flightFilterVO.setFlightCarrierId(containerVo.getCarrierId());
		flightFilterVO.setFlightNumber(containerVo.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		flightFilterVO.setStation(containerVo.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		return flightFilterVO;
	}

	private  int getLATConfigurationValue(GeneralConfigurationFilterVO generalTimeMappingFilterVO,
			ZonedDateTime statedTimeOfDeparture) {
		var sharedDefaultsProxy = ContextUtil.getInstance().getBean(SharedDefaultsProxy.class);
		Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = null;
		try {
			generalConfigurationMasterVOs = sharedDefaultsProxy.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
		} catch(SystemException exception) {
			throw new SystemException(exception.getErrorCode());
		}
		int minutes = 0, hours = 0;
		if (CollectionUtils.isNotEmpty(generalConfigurationMasterVOs)) {
			for (GeneralConfigurationMasterVO generalConfigurationMasterVO : generalConfigurationMasterVOs) {
				if ((statedTimeOfDeparture.isAfter(generalConfigurationMasterVO.getStartDate().toZonedDateTime())
						&& statedTimeOfDeparture.isBefore(generalConfigurationMasterVO.getEndDate().toZonedDateTime()))
						|| statedTimeOfDeparture.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
								.equals(generalConfigurationMasterVO.getStartDate().toDisplayDateOnlyFormat())
						|| statedTimeOfDeparture.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
								.equals(generalConfigurationMasterVO.getEndDate().toDisplayDateOnlyFormat())) {
					if (generalConfigurationMasterVO.getParameterDetails() != null
							&& generalConfigurationMasterVO.getParameterDetails().size() > 0
							&& generalTimeMappingFilterVO.getParameterDetailsFilterVO() != null
							&& generalTimeMappingFilterVO.getParameterDetailsFilterVO().size() > 0
							&& MailConstantsVO.MAIL_LAT_OFFSET_PARAMETER_CODE.equals(generalTimeMappingFilterVO
									.getParameterDetailsFilterVO().iterator().next().getParameterCode())) {
						for (GeneralRuleConfigDetailsVO generalRuleConfigDetailsVO : generalConfigurationMasterVO
								.getTimeDetails()) {
							if (MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_RULE_MINUTES
									.equals(generalRuleConfigDetailsVO.getParameterCode())) {
								minutes = Integer.parseInt(generalRuleConfigDetailsVO.getParameterValue());
							}
							if (MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_RULE_HOURS
									.equals(generalRuleConfigDetailsVO.getParameterCode())) {
								hours = Integer.parseInt(generalRuleConfigDetailsVO.getParameterValue());
							}
						}
					}
					if (generalConfigurationMasterVO.getParameterDetails() == null
							|| generalConfigurationMasterVO.getParameterDetails().size() == 0) {
						for (GeneralRuleConfigDetailsVO generalRuleConfigDetailsVO : generalConfigurationMasterVO
								.getTimeDetails()) {
							if (MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_RULE_MINUTES
									.equals(generalRuleConfigDetailsVO.getParameterCode())) {
								minutes = Integer.parseInt(generalRuleConfigDetailsVO.getParameterValue());
							}
							if (MailConstantsVO.MAIL_LAT_OFFSET_CONFIGURATION_RULE_HOURS
									.equals(generalRuleConfigDetailsVO.getParameterCode())) {
								hours = Integer.parseInt(generalRuleConfigDetailsVO.getParameterValue());
							}
						}
					}
				}
			}
		}
		return minutes + hours * 60;
	}

	/** 
	* Method		:	MailController.performOffloadAndReassign Added by 	:	U-1467 on 11-Jun-2020 Used for 	:	IASCB-55954 Parameters	:	@param mailbagVOs Parameters	:	@param toContainerVO Parameters	:	@throws SystemException Parameters	:	@throws InvalidFlightSegmentException Parameters	:	@throws ReassignmentException Parameters	:	@throws CapacityBookingProxyException Parameters	:	@throws MailBookingException Return type	: 	void
	*/
	private void performOffloadAndReassign(Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO)
			throws InvalidFlightSegmentException, ReassignmentException, CapacityBookingProxyException,
			MailBookingException {
		log.debug(CLASS + " : " + "performOffloadAndReassign" + " Entering");
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			Page<MailbagVO> offloadMailbags = new Page<>(new ArrayList<>(), 0, 0, 0, 0, 0, false);
			for (MailbagVO mailbagVO : mailbagVOs) {
				MailbagVO mailbagToOffload = new MailbagVO();
				mailbagToOffload = mailOperationsMapper.copyMailbagVO(mailbagVO);
				mailbagToOffload.setOffloadedReason("PR");
				mailbagToOffload.setIsoffload(true);
				mailbagVO.setFlightClosureCheckNotNeeded(true);
				offloadMailbags.add(mailbagToOffload);
			}
			OffloadVO offloadVO = updateMailbagOffloadDetails(mailbagVOs.iterator().next());
			offloadVO.setDepartureOverride(true);
			offloadVO.setFltClosureChkNotReq(true);
			offloadVO.setOffloadMailbags(offloadMailbags);
			try {
				offload(offloadVO);
			} catch (FlightDepartedException | ULDDefaultsProxyException | FlightClosedException e) {
				throw new SystemException(e.getMessage());
			}
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
				mailbagVO.setFlightSequenceNumber(MailConstantsVO.ZERO);
				mailbagVO.setOffloadAndReassign(true);
				if (MailConstantsVO.ULD_TYPE.equals(mailbagVO.getContainerType())) {
					mailbagVO.setUldNumber(MailConstantsVO.CONST_BULK + MailConstantsVO.SEPARATOR + mailbagVO.getPou());
				}
			}
			try {
				reassignMailbagsfromAndroid(mailbagVOs, toContainerVO);
			} catch (FlightClosedException e) {
				throw new SystemException(e.getMessage());
			}
		}
		log.debug(CLASS + " : " + "performOffloadAndReassign" + " Exiting");
	}

	public ZonedDateTime calculateTransportServiceWindowEndTime(MailbagVO mailbagVO) {
		ZonedDateTime transportServiceWindowEndTime = null;
		MailHandoverVO mailHandoverVO = new MailHandoverVO();
		if (mailbagVO.getConsignmentDate() != null) {
			ZonedDateTime consDate = localDateUtil.getLocalDate(mailbagVO.getDestination(), true);
			//TODO: To verify the date format
			String consignmentDateString =
					mailbagVO.getConsignmentDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
			if (consignmentDateString.trim().length() == 11) {
				consDate = LocalDate.withDate(consDate, consignmentDateString);
			} else {
				consDate = LocalDate.withDateAndTime(consDate, consignmentDateString);
			}
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add("mail.operations.USPSCloseoutoffsettime");
			Map<String, String> systemParameterMap = null;
			try {
				systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
			} catch (BusinessException e) {
				log.error(e.getMessage());
			}
			log.debug("" + " systemParameterMap " + " " + systemParameterMap);
			if (systemParameterMap != null) {
				String closeOutTime = systemParameterMap.get("mail.operations.USPSCloseoutoffsettime");
				int timeInMinutes = Integer.parseInt(closeOutTime);
				timeInMinutes = timeInMinutes * -1;
				consDate.plusMinutes(timeInMinutes);
			}
			transportServiceWindowEndTime = localDateUtil.getLocalDate(mailbagVO.getDestination(), true);
			String consDateString = consDate.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
			transportServiceWindowEndTime = LocalDate.withDate(transportServiceWindowEndTime, consDateString);
			int serviceStandard = 0;
			try {
				if (mailbagVO.getMailServiceLevel() != null)
					serviceStandard = constructDAO().findServiceStandard(mailbagVO);
			} catch (PersistenceException e1) {
			}
			if (serviceStandard != 0) {
				transportServiceWindowEndTime.plusDays(serviceStandard);
			}
			mailHandoverVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailHandoverVO.setGpaCode(mailbagVO.getPaCode());
			mailHandoverVO.setHoAirportCodes(mailbagVO.getDestination());
			boolean mailClassCharCheck = false;
			try {
				Integer.parseInt(mailbagVO.getMailClass());
			} catch (NumberFormatException e) {
				mailClassCharCheck = true;
			}
			if (mailClassCharCheck) {
				mailHandoverVO.setMailClass(mailbagVO.getMailClass());
			} else {
				mailHandoverVO.setMailSubClass(mailbagVO.getMailSubclass());
			}
			if (mailbagVO.getDoe() != null && mailbagVO.getDoe().length() == 6)
				;
			mailHandoverVO.setExchangeOffice(mailbagVO.getDoe().substring(0, 5));
			String mailHandoverTime = null;
			try {
				try {
					mailHandoverTime = constructDAO().findMailHandoverDetails(mailHandoverVO);
				} catch (PersistenceException e) {
				}
			} finally {
			}
			if (mailHandoverTime != null) {
				String handOverTime = mailHandoverTime;
				int timeInMinutes = 0;
				timeInMinutes = (Integer.parseInt(handOverTime.substring(0, 2)) * 60
						+ Integer.parseInt(handOverTime.substring(2, 4)));
				transportServiceWindowEndTime.plusMinutes(timeInMinutes);
			}
		}
		return transportServiceWindowEndTime;
	}

	public boolean canReuseEmptyContainer(ContainerAssignmentVO containerAssignmentVO) {
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mail.operations.reuseEmptyULD");
		boolean reuseEmptyULDEnabled = canReuseEmptyContainerCheckEnabled();
		if (!reuseEmptyULDEnabled) {
			return false;
		}
		Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
		containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
		containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
		containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
		containerDetailsVO.setAssignedPort(containerAssignmentVO.getAirportCode());
		containerDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
		containerDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
		containers.add(containerDetailsVO);
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		try {
			containerDetailsVOs = new MailController().findMailbagsInContainer(containers);
		} finally {
		}
		if (containerDetailsVOs == null || containerDetailsVOs.size() == 0) {
			return false;
		}
		ContainerDetailsVO containerVo = containerDetailsVOs.iterator().next();
		if (containerVo != null && containerVo.getMailDetails() != null && containerVo.getMailDetails().size() > 0) {
			return false;
		}
		return true;
	}

	public boolean canReuseEmptyContainerCheckEnabled() {
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mail.operations.reuseEmptyULD");
		Map<String, String> systemParameterMap = null;
		boolean reuseEmptyULDEnabled = false;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		if (systemParameterMap != null && systemParameterMap.containsKey("mail.operations.reuseEmptyULD")) {
			reuseEmptyULDEnabled = "Y".equals(systemParameterMap.get("mail.operations.reuseEmptyULD"));
		}
		return reuseEmptyULDEnabled;
	}

	/** 
	* This method will delete empty container while reusing from other station
	* @param containerDetailsVO
	*/
	public void deleteCarrierEmptyContainer(ContainerAssignmentVO containerDetailsVO) {
		ULDAtAirport uldAtAirport = null;
		ULDAtAirportPK uldAtAirportPK = constructULDAtAirportPK(containerDetailsVO);
		try {
			uldAtAirport = ULDAtAirport.find(uldAtAirportPK);
			uldAtAirport.remove();
		} catch (FinderException e) {
			log.error("SystemException caught");
		}
		ContainerPK containerPK = constructContainerPK(containerDetailsVO);
		Container container = null;
		try {
			container = Container.find(containerPK);
			//TODO: Neo to rewrite audit
//			ContainerAuditVO containerAuditVO = new ContainerAuditVO(ContainerVO.MODULE, ContainerVO.SUBMODULE,
//					ContainerVO.ENTITY);
//			containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(containerAuditVO, container, false);
//			collectContainerAuditDetails(container, containerAuditVO);
			LoginProfile logonAttributes = null;
			try {
				logonAttributes = contextUtil.callerLoginProfile();
			} finally {
			}
//			containerAuditVO.setCompanyCode(container.getContainerPK().getCompanyCode());
//			containerAuditVO.setContainerNumber(container.getContainerPK().getContainerNumber());
//			containerAuditVO.setAssignedPort(container.getContainerPK().getAssignmentPort());
//			containerAuditVO.setCarrierId(container.getContainerPK().getCarrierId());
//			containerAuditVO.setFlightNumber(container.getContainerPK().getFlightNumber());
//			containerAuditVO.setFlightSequenceNumber(container.getContainerPK().getFlightSequenceNumber());
//			containerAuditVO.setLegSerialNumber(container.getContainerPK().getLegSerialNumber());
//			containerAuditVO.setStationCode(logonAttributes.getAirportCode());
//			containerAuditVO.setUserId(logonAttributes.getUserId());
//			containerAuditVO.setActionCode(AuditVO.DELETE_ACTION);
//			containerAuditVO.setAdditionalInformation("Empty container deleted");
//			containerAuditVO.setAuditRemarks("Empty container deleted");
			container.remove();
//			AuditUtils.performAudit(containerAuditVO);
		} catch (FinderException e) {
			log.error("SystemException caught");
		}
	}

	private ContainerPK constructContainerPK(ContainerAssignmentVO containerAssignmentVO) {
		ContainerPK containerPK = new ContainerPK();
		containerPK.setContainerNumber(containerAssignmentVO.getContainerNumber());
		containerPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
		containerPK.setAssignmentPort(containerAssignmentVO.getAirportCode());
		containerPK.setCarrierId(containerAssignmentVO.getCarrierId());
		containerPK.setFlightNumber(containerAssignmentVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		return containerPK;
	}

	private ULDAtAirportPK constructULDAtAirportPK(ContainerAssignmentVO containerAssignmentVO) {
		ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setAirportCode(containerAssignmentVO.getAirportCode());
		uldAtAirportPK.setCarrierId(containerAssignmentVO.getCarrierId());
		uldAtAirportPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
		if ("B".equals(containerAssignmentVO.getContainerType())) {
			uldAtAirportPK
					.setUldNumber("BULK-" + (containerAssignmentVO.getPou() != null ? containerAssignmentVO.getPou()
							: containerAssignmentVO.getDestination()));
		} else {
			uldAtAirportPK.setUldNumber(containerAssignmentVO.getContainerNumber());
		}
		return uldAtAirportPK;
	}

	public int findFlightSegment(String companyCode, int carrierId, String flightNumber, long flightSequenceNumber,
			String pol, String pou) {
		log.debug(CLASS + " : " + "findFlightSegment" + " Entering");
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		flightSegments = flightOperationsProxy.findFlightSegments(companyCode, carrierId, flightNumber,
				flightSequenceNumber);
		String segment = new StringBuilder().append(pol).append(pou).toString();
		String flightSegment = null;
		int segmentSerNum = 0;
		if (flightSegments != null && !flightSegments.isEmpty()) {
			for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
				flightSegment = new StringBuilder().append(segmentSummaryVO.getSegmentOrigin())
						.append(segmentSummaryVO.getSegmentDestination()).toString();
				if (flightSegment.equals(segment)) {
					segmentSerNum = segmentSummaryVO.getSegmentSerialNumber();
				}
			}
		}
		log.debug(CLASS + " : " + "findFlightSegment" + " Exiting");
		return segmentSerNum;
	}

	/** 
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @author A-5526 Added for CRQ ICRD-233864
	*/
	private static MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		} else {
			mailbagPK.setMailSequenceNumber(
					Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			mailbagVO.setMailSequenceNumber(mailbagPK.getMailSequenceNumber());
		}
		return mailbagPK;
	}

	/** 
	* @author A-7929
	* @param containerVOs
	* @param
	* @return
	* @throws SystemException
	*/
	public void saveContentID(Collection<ContainerVO> containerVOs) {
		log.debug(CLASS + " : " + "saveContentID" + " Entering");
		Container.saveContentID(containerVOs);
	}

	/** 
	* @author A-7540
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException
	*/
	public Collection<RateAuditVO> createRateAuditVOs(ScannedMailDetailsVO scannedMailDetailsVO) {
		RateAuditVO rateAuditVO = new RateAuditVO();
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		MailbagVO mailbagVO = null;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			mailbagVO = scannedMailDetailsVO.getMailDetails().iterator().next();
		}
		rateAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
		rateAuditVO.setAutoArrivalFlag(MailConstantsVO.FLAG_YES);
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<>();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		rateAuditDetailsVO.setSource(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		rateAuditDetails.add(rateAuditDetailsVO);
		rateAuditVO.setRateAuditDetails(rateAuditDetails);
		rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbagVO.getScannedDate()));
		if (mailbagVO.isDeliveryNeeded()) {
			rateAuditVO.setMailStatus(mailbagVO.getMailbagDataSource());
		} else {
			rateAuditVO.setMailStatus(scannedMailDetailsVO.getProcessPoint());
		}
		rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		rateAuditVOs.add(rateAuditVO);
		return rateAuditVOs;
	}

	/** 
	* Method : MailController.isUSPSMailbag Added by : A-8061 on 08-Aug-2019 Used for : ICRD-341146 Parameters : @param mailBagVO Parameters : @return Parameters : @throws SystemException Return type : Boolean
	*/
	public Boolean isUSPSMailbag(MailbagVO mailBagVO) {
		String paCodeInt = null;
		String paCodeDom = null;
		paCodeInt = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCodeDom = findSystemParameterValue(USPS_DOMESTIC_PA);
		return ((paCodeInt != null && paCodeInt.equals(mailBagVO.getPaCode()))
				|| (paCodeDom != null && paCodeDom.equals(mailBagVO.getPaCode())));
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	* @param toContainerVO
	* @param logonAttributes
	* @return
	* @throws SystemException
	*/
	private String checkForMailBagTransferStatus(MailbagVO mailbagVO, ContainerVO toContainerVO,
			LoginProfile logonAttributes) {
		String transferStatus = null;
		if (toContainerVO.getCarrierCode() != null && toContainerVO.getCarrierCode().trim().length() > 0
				&& mailbagVO.getCarrierCode() != null && mailbagVO.getCarrierCode().trim().length() > 0) {
			boolean isFomCarrierCodePartner = checkIfPartnerCarrier(logonAttributes.getAirportCode(),
					mailbagVO.getCarrierCode());
			boolean isToCarrierCodePartner = checkIfPartnerCarrier(logonAttributes.getAirportCode(),
					toContainerVO.getCarrierCode());
			if (isFomCarrierCodePartner && isToCarrierCodePartner) {
				transferStatus = MailConstantsVO.MAIL_STATUS_ASSIGNED;
				mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			} else if (!isFomCarrierCodePartner && isToCarrierCodePartner) {
				transferStatus = MailConstantsVO.TRANSFER_IN;
				mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
			} else {
				transferStatus = MailConstantsVO.TRANSFER_OUT;
				mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
			}
			if (toContainerVO.isFoundTransfer() && mailbagVO.getTransferFromCarrier() != null
					&& mailbagVO.getTransferFromCarrier().trim().length() > 0
					&& !checkIfPartnerCarrier(logonAttributes.getAirportCode(), mailbagVO.getTransferFromCarrier())) {
				transferStatus = MailConstantsVO.TRANSFER_IN;
				mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
			}
		}
		return transferStatus;
	}

	/** 
	* @author A-8353
	* @param toCarrierCode
	* @return
	*/
	private boolean checkForContainerTransferOutStatus(String toCarrierCode) {
		boolean isTransferOutOperation = false;
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		if (toCarrierCode != null && toCarrierCode.trim().length() > 0) {
			boolean isToCarrierCodePartner = true;
			try {
				isToCarrierCodePartner = checkIfPartnerCarrier(logonAttributes.getAirportCode(), toCarrierCode);
			} finally {
			}
			if (!isToCarrierCodePartner) {
				isTransferOutOperation = true;
				return isTransferOutOperation;
			}
		}
		return isTransferOutOperation;
	}

	private void updateTransferOutDetailsForHistory(MailbagVO mailbagVO, TransferManifestVO transferManifestVO) {
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		mailbagVO.setCarrierCode(transferManifestVO.getTransferredToCarrierCode());
		if (mailbagVO.getScannedUser() == null) {
			mailbagVO.setScannedUser(logonAttributes.getUserId());
		}
	}

	private MailbagVO populateMailbagVofromMailbag(Mailbag mailbag, TransferManifestVO transferManifestVO) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailbagId(mailbag.getMailIdr());
		mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
		mailbagVO.setCompanyCode(mailbag.getCompanyCode());
		mailbagVO.setOperationalStatus(mailbag.getOperationalStatus());
		mailbagVO.setPaCode(mailbag.getPaCode());
		ZonedDateTime scanDate = localDateUtil.getLocalDate(mailbag.getScannedPort(), true);
		mailbagVO.setScannedDate(scanDate);
		mailbagVO.setScannedPort(mailbag.getScannedPort());
		mailbagVO.setFlightNumber(mailbag.getFlightNumber());
		mailbagVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
		mailbagVO.setLatestStatus(mailbag.getLatestStatus());
		mailbagVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
		mailbagVO.setMailbagSource(mailbag.getMailbagSource());
		mailbagVO.setMailClass(mailbag.getMailClass());
		mailbagVO.setPaBuiltFlag(mailbag.getPaBuiltFlag());
		mailbagVO.setMailSource(
				transferManifestVO.getTranferSource() != null ? transferManifestVO.getTranferSource() : "MTK027");
		mailbagVO.setCarrierId(mailbag.getCarrierId());
		mailbagVO.setContainerType(mailbag.getContainerType());
		if (mailbagVO.getOperationalStatus().equals("I")) {
			mailbagVO.setArrivedFlag("Y");
		} else if (mailbagVO.getOperationalStatus().equals("O")) {
			if (MailConstantsVO.BULK_TYPE.equalsIgnoreCase(mailbag.getContainerType()) && mailbag.getUldNumber() != null
					&& mailbag.getUldNumber().trim().length() > 0) {
				ContainerAssignmentVO containerAssignmentVO = null;
				try {
					containerAssignmentVO = findLatestContainerAssignment(mailbag.getUldNumber());
				} finally {
				}
				if (containerAssignmentVO != null && containerAssignmentVO.getDestination() != null) {
					mailbagVO.setFinalDestination(containerAssignmentVO.getDestination());
				}
			} else {
				mailbagVO.setUldNumber(mailbag.getUldNumber());
			}
		}
		mailbagVO.setContainerNumber(mailbag.getUldNumber());
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(mailbag.getWeight())));
		mailbagVO.setCarrierCode(transferManifestVO.getTransferredFromCarCode());
		if (transferManifestVO.getTransferredfrmFltSeqNum() > 0) {
			mailbagVO.setFlightNumber(transferManifestVO.getTransferredFromFltNum());
			mailbagVO.setFlightSequenceNumber(transferManifestVO.getTransferredfrmFltSeqNum());
			mailbagVO.setSegmentSerialNumber(transferManifestVO.getTransferredfrmSegSerNum());
		}
		mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		mailbagVO.setDoe(mailbag.getDestinationOfficeOfExchange());
		mailbagVO.setOoe(mailbag.getOrginOfficeOfExchange());
		mailbagVO.setDestination(mailbag.getDestination());
		mailbagVO.setOrigin(mailbag.getOrigin());
		mailbagVO.setMailSubclass(mailbag.getMailSubClass());
		mailbagVO.setMailCategoryCode(mailbag.getMailCategory());
		mailbagVO.setPou(mailbag.getPou());
		return mailbagVO;
	}

	private ContainerVO constructContainerVO(TransferManifestVO transferManifestVO) {
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		ContainerVO containerVO = new ContainerVO();
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineProxy.validateAlphaCode(transferManifestVO.getCompanyCode(),
					transferManifestVO.getTransferredToCarrierCode());
		} catch (SharedProxyException e) {
			e.printStackTrace();
		} finally {
		}
		String airportCode = logonAttributes.getAirportCode();
		containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setCompanyCode(transferManifestVO.getCompanyCode());
		containerVO.setCarrierCode(transferManifestVO.getTransferredToCarrierCode());
		containerVO.setAssignedUser(logonAttributes.getUserId());
		containerVO.setAssignedPort(airportCode);
		containerVO.setLastUpdateUser(logonAttributes.getUserId());
		containerVO.setOperationTime(transferManifestVO.getTransferredDate());
		if (airlineValidationVO != null) {
			containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		}
		return containerVO;
	}

	/** 
	* @author A-8353
	* @param mailbagVOs
	* @param containerVO
	* @throws SystemException
	*/
	public void flagHandoverReceivedAfterExportOpr(Collection<MailbagVO> mailbagVOs, ContainerVO containerVO) {
		LoginProfile logonAttributes = null;
		containerVO.setHandoverReceived(true);
		logonAttributes = contextUtil.callerLoginProfile();
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (containerVO.getFlightSequenceNumber() > -1) {
				new MailbagInULDForSegment().updateHandoverReceivedCarrierForTransfer(mailbagVO, containerVO);
			} else {
				new MailbagInULDAtAirport().updateHandoverReceivedCarrierForTransfer(mailbagVO, containerVO,
						logonAttributes.getAirportCode());
			}
			checkHistoryExistForMailbag(logonAttributes, mailbagVO);
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagHistoryForMailTransferAtExport(mailbagVOs, containerVO, mailController.getTriggerPoint());
		flagResditsForMailbagTransfer(mailbagVOs, containerVO);
		Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(containerVO, mailbagVOs,
				MailConstantsVO.MAIL_STATUS_TRANSFER_MAIL, false);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			if (importEnabled != null && (importEnabled.contains("M") || importEnabled.contains("D"))) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
	}

	/** 
	* @author A-8353
	* @param logonAttributes
	* @param mailbagVO
	*/
	private void checkHistoryExistForMailbag(LoginProfile logonAttributes, MailbagVO mailbagVO) {
		Collection<MailbagHistoryVO> existingMailbagHistories = null;
		Mailbag mailBag = null;
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		try {
			mailBag = Mailbag.find(mailbagPk);
		} catch (FinderException e) {
			log.error("System Exception Caught");
			e.getMessage();
			log.error("System Exception Caught");
		}
		mailbagVO.setLatestStatus(mailBag.getLatestStatus());
		try {
			existingMailbagHistories = Mailbag.findMailbagHistories(mailbagVO.getCompanyCode(), "",
					mailbagVO.getMailSequenceNumber());
		} finally {
		}
		if (existingMailbagHistories != null && !existingMailbagHistories.isEmpty()) {
			for (MailbagHistoryVO mailbagHistory : existingMailbagHistories) {
				if (logonAttributes.getAirportCode().equals(mailbagHistory.getScannedPort())
						&& !MailConstantsVO.FLIGHT_ARRIVAL.equals(mailbagHistory.getMailStatus())) {
					ZonedDateTime scanDate = mailbagHistory.getScanDate();
					scanDate.plusSeconds(-01);
					mailbagVO.setScannedDate(scanDate);
					mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				}
			}
		}
		mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
	}

	/** 
	* @author A-8353
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @throws SystemException
	*/
	public HashMap<String, String> findAirportForOfficeOfExchange(String companyCode,
			Collection<String> officeOfExchanges) {
		OfficeOfExchangeFilterModel filterModel = new OfficeOfExchangeFilterModel();
		filterModel.setCompanyCode(companyCode);
		filterModel.setOfficeOfExchanges(officeOfExchanges);
		MailTrackingDefaultsBI mailMasterApi = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
		Map airportCodeMap = mailMasterApi.findAirportForOfficeOfExchange(filterModel);
		return (HashMap<String, String>) airportCodeMap;
	}

	public boolean isValidDestForCarditMissingDomesticMailbag(String airportCode) {
		boolean isDestForCdtMissingDomMail = false;
		String destForCdtMissingDomMail = null;
		try {
			destForCdtMissingDomMail = findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		} finally {
		}
		if (destForCdtMissingDomMail != null && !"NA".equals(destForCdtMissingDomMail) && airportCode != null
				&& destForCdtMissingDomMail.equals(airportCode)) {
			isDestForCdtMissingDomMail = true;
		}
		return isDestForCdtMissingDomMail;
	}

	public void updatemailperformanceDetails(Mailbag mailbag) {
		MailbagVO mailbagVO = mailbag.retrieveVO();
		MailbagInULDForSegment mailbagInULDForSegment = null;
		try {
			if (isUSPSMailbag(mailbagVO)) {
				Collection<MailbagHistoryVO> mailbagHistoryVOs = mailbag.findMailbagHistories(
						mailbag.getCompanyCode(), null, mailbag.getMailSequenceNumber());
				if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
					for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
						if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagHistoryVO.getMailStatus())) {
							mailbagVO.setScannedPort(mailbagHistoryVO.getScannedPort());
							mailbagVO.setScannedDate(mailbagHistoryVO.getScanDate());
							MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
							mailbagInULDForSegmentPK.setCompanyCode(mailbagHistoryVO.getCompanyCode());
							mailbagInULDForSegmentPK
									.setCarrierId(mailbagHistoryVO.getCarrierId() > 0 ? mailbagHistoryVO.getCarrierId()
											: mailbag.getCarrierId());
							mailbagInULDForSegmentPK.setFlightNumber(mailbagHistoryVO.getFlightNumber());
							mailbagInULDForSegmentPK
									.setFlightSequenceNumber(mailbagHistoryVO.getFlightSequenceNumber());
							mailbagInULDForSegmentPK
									.setSegmentSerialNumber(mailbagHistoryVO.getSegmentSerialNumber() > 0
											? mailbagHistoryVO.getSegmentSerialNumber()
											: mailbag.getSegmentSerialNumber());
							if (MailConstantsVO.BULK_TYPE.equals(mailbagHistoryVO.getContainerType())) {
								mailbagInULDForSegmentPK.setUldNumber("BULK-" + mailbagHistoryVO.getPou());
							} else {
								mailbagInULDForSegmentPK.setUldNumber(mailbagHistoryVO.getContainerNumber());
							}
							mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagHistoryVO.getMailSequenceNumber());
							try {
								mailbagInULDForSegment = getMailbagInULDForSegment(mailbagInULDForSegmentPK);
							} catch (FinderException finderException) {
								log.error("Exception :", finderException);
							}
							break;
						}
					}
				}
				//TODO: Below date to be corrected in neo
//				mailbagVO.setConsignmentDate(localDateUtil.
//						ZonedDateTime.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true));
				mailbagVO.setMailServiceLevel(mailbag.getMailServiceLevel());
				new ULDForSegment().updatemailperformanceDetails(mailbagVO, mailbagInULDForSegment, mailbag);
			}
		} catch (PersistenceException exception) {
			log.error("Exception :", exception);
		}
	}

	/** 
	* Method		:	MailController.flagContainerAuditForUnassignment Added by 	:	A-9084 on 30-Mar-2021 Used for 	:	IASCB-84649 Parameters	:	@param containerDetailsVO Return type	: 	void
	*/
	public void flagContainerAuditForArrival(MailArrivalVO mailArrivalVO) {
		log.debug(CLASS + " : " + "flagContainerAuditForArrival" + " Entering");
		log.debug(CLASS + " : " + "flagContainerAuditForArrival" + " Exiting");
	}

	/** 
	* Method		:	MailController.reImportPABuiltMailbagsToMRA Added by 	:	A-6245 on 12-Mar-2021 Used for 	:	IASCB-96008 Parameters	:	@param mailbagVOs Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	boolean
	*/
	public boolean reImportPABuiltMailbagsToMRA(Collection<MailbagVO> mailbagVOs) {
		boolean isReImported = false;
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			String paListForPABuiltRate = findSystemParameterValue(PA_LIST_FOR_PA_BUILT_RATING);
			for (MailbagVO mailbagVO : mailbagVOs) {
				if ((mailbagVO.isPaBuiltFlagUpdate() || mailbagVO.isPaContainerNumberUpdate())
						&& Objects.nonNull(paListForPABuiltRate)
						&& paListForPABuiltRate.contains(mailbagVO.getPaCode())) {
					//TODO: use mra proxy to classic to mra neo is ready
//					Collection<MailbagVO> mailbagVOsForPABuiltUpdate = mailOperationsMRAProxy
//							.findMailbagsForPABuiltUpdate(mailbagVO);
//					if (Objects.nonNull(mailbagVOsForPABuiltUpdate) && !mailbagVOsForPABuiltUpdate.isEmpty()) {
//						mailOperationsMRAProxy.reImportPABuiltMailbagsToMRA(mailbagVO);
//						isReImported = true;
//					}
				}
			}
		}
		return isReImported;
	}

	public String findOfficeOfExchangeForCarditMissingDomMail(String companyCode, String airportCode) {
		var exchangeOfficeMap = findOfficeOfExchangeForPA(companyCode, findSystemParameterValue(USPS_DOMESTIC_PA));
		if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty() && exchangeOfficeMap.containsKey(airportCode)) {
			return exchangeOfficeMap.get(airportCode);
		}
		return null;
	}

	/** 
	* Method		:	MailController.reImportMailbagsToMRA Added by 	:	A-8061 on 09-Apr-2021 Used for 	: Parameters	:	@param mailbagVOs Parameters	:	@throws SystemException  Return type	: 	Collection<MailbagVO>
	*/
	private Collection<MailbagVO> reImportMailbagsToMRA(Collection<MailbagVO> mailbagVOs) {
		Collection<MailbagVO> mailbagsToReimport = new ArrayList<>();
		Collection<MailbagVO> mailbagsNotImported = new ArrayList<>();
		if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				boolean isMailInMRA = false;
				try {
					isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(mailbagVO.getCompanyCode(),
							mailbagVO.getMailSequenceNumber());
				} catch (BusinessException e) {
					log.debug("Exception finding mail exists in MRA " + e.getMessage());
				}
				if (isMailInMRA) {
					mailbagsToReimport.add(mailbagVO);
				} else {
					mailbagsNotImported.add(mailbagVO);
				}
			}
			ConsignmentDocumentVO consignmentDocumentVO = constructConsignmentDocumentVO(mailbagsToReimport);
			mailtrackingMRAProxy.importConsignmentDataToMra(consignmentDocumentVO);
		}
		return mailbagsNotImported;
	}

	/** 
	* Method		:	MailController.constructConsignmentDocumentVO Added by 	:	A-8061 on 09-Apr-2021 Used for 	: Parameters	:	@param mailbagVOs Parameters	:	@return  Return type	: 	ConsignmentDocumentVO
	*/
	private ConsignmentDocumentVO constructConsignmentDocumentVO(Collection<MailbagVO> mailbagVOs) {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			Collection<MailInConsignmentVO> mailInCondignmentVOs = new ArrayList<>();
			for (MailbagVO mailbagVO : mailbagVOs) {
				MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
				mailInConsignmentVO.setCompanyCode(mailbagVO.getCompanyCode());
				mailInConsignmentVO.setConsignmentNumber(
						mailbagVO.getConsignmentNumber() != null ? mailbagVO.getConsignmentNumber() : BLANK);
				mailInConsignmentVO.setConsignmentSequenceNumber(mailbagVO.getConsignmentSequenceNumber());
				mailInConsignmentVO.setPaCode(mailbagVO.getPaCode());
				mailInConsignmentVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				mailInConsignmentVO.setMailSource(
						isNotNullAndEmpty(mailbagVO.getTriggerForReImport()) ? mailbagVO.getTriggerForReImport()
								: mailbagVO.getMailSource());
				mailInCondignmentVOs.add(mailInConsignmentVO);
			}
			Page<MailInConsignmentVO> mailInConsignmentVOs = new Page<>(
					(ArrayList<MailInConsignmentVO>) mailInCondignmentVOs, 0, 0, 0, 0, 0, false);
			consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		}
		return consignmentDocumentVO;
	}

	public Quantity calculateActualWeightForZeroWeightMailbags(MailbagVO mailbagVO) {
		Quantity actualWeight = null;
		String overwriteValue = findSystemParameterValue("mailtracking.mra.defaults.overwriteweightvalue");
		if (mailbagVO.getWeight() != null && mailbagVO.getMailbagId().length() == 29
				&& Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)) == 0 && overwriteValue != null) {
			//TODO: Unit hard coding to be corrected
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[0].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.WEIGHT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[0].split("=")[1])),
						BigDecimal.valueOf(0.0), "K");
			}
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[1].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.WEIGHT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[1].split("=")[1])),
						BigDecimal.valueOf(0.0), "K");
			}
		}
		if (mailbagVO.getWeight() != null && mailbagVO.getMailbagId().length() == 12
				&& Double.parseDouble(mailbagVO.getMailbagId().substring(10, 12)) == 0 && overwriteValue != null) {
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[0].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[0].split("=")[1])));
			}
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[1].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[1].split("=")[1])));
			}
		}
		return actualWeight;
	}

	/** 
	* If MRA import is enabled, check if incoming mailbags can be PA Built Rated If yes, re-rate the PA Built mailbags If not, check if they exist in MRA, and re-import to MRA  Return mailbags not imported, to be imported if needed 
	* @param mailbagVOs
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> validateAndReImportMailbagsToMRA(Collection<MailbagVO> mailbagVOs) {
		boolean isReImported = false;
		Collection<MailbagVO> mailbagsNotReImported = null;
		if (isImportToMRAEnabled()) {
			isReImported = reImportPABuiltMailbagsToMRA(mailbagVOs);
			if (!isReImported) {
				mailbagsNotReImported = reImportMailbagsToMRA(mailbagVOs);
			}
		}
		return mailbagsNotReImported;
	}

	public static boolean isImportToMRAEnabled() {
		String importToMRAEnabled = ContextUtil.getInstance().getBean(MailController.class)
				.findSystemParameterValue(MailConstantsVO.IMPORTMAILS_TO_MRA_SYSPAR);
		return isNotNullAndEmpty(importToMRAEnabled) && MailConstantsVO.FLAG_YES.equals(importToMRAEnabled);
	}

	/** 
	* Added for CRQ IASCB-74722
	* @author A-8893
	* @param mailbagVO
	* @throws SystemException 
	*/
	public void uploadDocumentsToRepository(MailbagVO mailbagVO) {

		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVos = populatedocumentRepositoryMasterVO(
				mailbagVO);
		try {
			documentRepositoryProxy.uploadMultipleDocumentsToRepository(documentRepositoryMasterVos);
		} catch (BusinessException proxyException) {

		}
	}

	private Collection<DocumentRepositoryMasterVO> populatedocumentRepositoryMasterVO(MailbagVO mailbagVO) {
		DocumentRepositoryMasterVO documentRepositoryMasterVO = new DocumentRepositoryMasterVO();
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVos = new ArrayList<>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String createdUser = mailbagVO.getAttachments().iterator().next().getUploadUser() != null
				? mailbagVO.getAttachments().iterator().next().getUploadUser()
				: logonAttributes.getUserId();
		documentRepositoryMasterVO.setCreatedUser(createdUser);
		documentRepositoryMasterVO
				.setCreatedTime(new com.ibsplc.icargo.framework.util.time.LocalDate("***", Location.NONE, true));
		documentRepositoryMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
		documentRepositoryMasterVO.setAirportCode(logonAttributes.getAirportCode());
		documentRepositoryMasterVO.setPurpose("DMG");
		documentRepositoryMasterVO.setDocumentType("MAL");
		documentRepositoryMasterVO.setDocumentValue(mailbagVO.getMailbagId());
		documentRepositoryMasterVO.setRemarks("Attachments for Damaged Maibag");
		documentRepositoryMasterVO.setOperationFlag("I");
		List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = null;
		documentRepositoryAttachmentVOs = convetImagesToDocumentRepositoryAttachmentVOs(mailbagVO);
		documentRepositoryMasterVO.setAttachments(documentRepositoryAttachmentVOs);
		documentRepositoryMasterVos.add(documentRepositoryMasterVO);
		return documentRepositoryMasterVos;
	}

	/** 
	* Added for CRQ IASCB-74722
	* @author A-8893
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public  List<DocumentRepositoryAttachmentVO> convetImagesToDocumentRepositoryAttachmentVOs(
			MailbagVO mailbagVO) {
		List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = new ArrayList<>();
		if (mailbagVO.getAttachments() != null && !mailbagVO.getAttachments().isEmpty()) {
			for (MailAttachmentVO mailAttachmentVO : mailbagVO.getAttachments()) {
				documentRepositoryAttachmentVOs
						.add(convetImagesToDocumentRepositoryAttachmentVO(mailAttachmentVO, mailbagVO));
			}
		}
		return documentRepositoryAttachmentVOs;
	}

	/** 
	* Added for CRQ IASCB-74722
	* @author A-8893
	* @param mailAttachmentVO
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public DocumentRepositoryAttachmentVO convetImagesToDocumentRepositoryAttachmentVO(
			MailAttachmentVO mailAttachmentVO, MailbagVO mailbagVO) {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO = new DocumentRepositoryAttachmentVO();
		documentRepositoryAttachmentVO.setCompanyCode(mailAttachmentVO.getCompanyCode());
		documentRepositoryAttachmentVO.setFileName(mailAttachmentVO.getFileName());
		documentRepositoryAttachmentVO.setAttachmentData(mailAttachmentVO.getFileData());
		documentRepositoryAttachmentVO.setAttachmentType(mailAttachmentVO.getAttachmentType());
		documentRepositoryAttachmentVO.setAirportCode(logonAttributes.getAirportCode());
		documentRepositoryAttachmentVO.setOperationFlag(mailAttachmentVO.getAttachmentOpFlag());
		documentRepositoryAttachmentVO.setDocumentRepoSerialNumber(mailAttachmentVO.getDocSerialNumber());
		documentRepositoryAttachmentVO.setRemarks(mailAttachmentVO.getRemarks());
		documentRepositoryAttachmentVO.setTxnRefereceOverride(true);
		documentRepositoryAttachmentVO.setReference1("MALIDR");
		documentRepositoryAttachmentVO.setTransactionDataRef1(mailbagVO.getMailbagId());
		documentRepositoryAttachmentVO.setReference2("DMGCOD");
		documentRepositoryAttachmentVO.setTransactionDataRef2(mailAttachmentVO.getReference2());
		return documentRepositoryAttachmentVO;
	}

	public void saveArrivalDetails(MailArrivalVO mailArrivalVO) throws MailOperationsBusinessException {
		SaveArrivalDetailsFeature saveArrivalDetailsFeature = ContextUtil.getInstance().getBean(SaveArrivalDetailsFeature.class);
		saveArrivalDetailsFeature.execute(mailArrivalVO);
		flagFoundArrivalResdits(mailArrivalVO);
	}

	public String findAgentFromUpucode(String cmpCode, String upuCode) {
		return constructDAO().findAgentCodeFromUpuCode(cmpCode, upuCode);
	}

//TB Review by Meera
	public void saveSecurityDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
		saveSecurityDetailsFeature.executeBatch((List<ConsignmentScreeningVO>) consignmentScreeningVOs, false);
		try {
			PersistenceController.getEntityManager().flush();
			PersistenceController.getEntityManager().clear();
		} catch (PersistenceException e) {
			e.getMessage();
		}
		updateRegAgentForScreeningMethods(consignmentScreeningVOs);
	}
	//TB Review by Meera
	public void updateRegAgentForScreeningMethods(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) throws SystemException {
		Optional<ConsignmentScreeningVO> consignmentScreeningVo = consignmentScreeningVOs.stream().filter(screeningVO->screeningVO.getAgentType()!=null
				&&MailConstantsVO.RA_ISSUING.equals(screeningVO.getAgentType())).findFirst();
		if (consignmentScreeningVo.isPresent()){
			ConsignmentScreeningVO consignmentScreeningVO=consignmentScreeningVo.get();
			editScreeningDetailsFeature.execute(consignmentScreeningVO);
		}
	}

	/** 
	* Method		:	MailController.saveScreeningConsginorDetails Added by 	:	A-4809 on 19-May-2022 Used for 	: Parameters	:	@param contTransferMap  Return type	: 	void
	* @throws SystemException 
	* @throws RemoteException 
	*/
	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap) {
		//TODO: correct the belwo code
//		(ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class))
//				.saveScreeningConsginorDetails(contTransferMap);
	}

	/** 
	* @param mailbag
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findAWBAttachedMailbags(MailbagVO mailbag, String consignmentNumber) {
		return constructDAO().findAWBAttachedMailbags(mailbag, consignmentNumber);
	}

	public void findFieldsOfCarditPawb(CarditVO carditVO) {
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		OfficeOfExchangeFilterModel filterModel = new OfficeOfExchangeFilterModel();
		Map<String, String> airPort=new HashMap<>();
		if(carditVO.getCompanyCode()!=null&&carditPawbDetail.getConsignmentDestination()!=null) {
			Collection<String> officeOfExchanges = new ArrayList<>();
			officeOfExchanges.add(carditPawbDetail.getConsignmentDestination());
			filterModel.setCompanyCode(carditVO.getCompanyCode());
			filterModel.setOfficeOfExchanges(officeOfExchanges);
			airPort = mailMasterApi.findAirportForOfficeOfExchange(filterModel);
			if (airPort != null && airPort.size() != 0) {
				carditPawbDetail.setConsignmentDestinationAirport(airPort.get(carditPawbDetail.getConsignmentDestination()));

			} else {
				carditPawbDetail.setConsignmentDestinationAirport(findNearestAirportOfCity(carditVO.getCompanyCode(),
						carditPawbDetail.getConsignmentOrigin()));
			}
		}

		if(carditVO.getCompanyCode()!=null&&carditPawbDetail.getConsignmentOrigin()!=null) {
			Collection<String> officeOfExchangesorigin = new ArrayList<>();
			officeOfExchangesorigin.add(carditPawbDetail.getConsignmentOrigin());
			filterModel.setCompanyCode(carditVO.getCompanyCode());
			filterModel.setOfficeOfExchanges(officeOfExchangesorigin);
			airPort = mailMasterApi.findAirportForOfficeOfExchange(filterModel);
			if (airPort != null && airPort.size() != 0) {
				carditPawbDetail.setConsignmentOriginAirport(airPort.get(carditPawbDetail.getConsignmentOrigin()));
			} else {
				carditPawbDetail.setConsignmentOriginAirport(findNearestAirportOfCity(carditVO.getCompanyCode(),
						carditPawbDetail.getConsignmentOrigin()));
			}
		}
		if (carditPawbDetail.getShipperCode() != null) {
			String agentCode = findAgentFromUpucode(carditVO.getCompanyCode(), carditPawbDetail.getShipperCode());
			carditPawbDetail.setAgentCode(agentCode);
		}
		if (carditPawbDetail.getConsigneeCode() != null) {
			String consigneeCode = findAgentFromUpucode(carditVO.getCompanyCode(), carditPawbDetail.getConsigneeCode());
			carditPawbDetail.setConsigneeAgentCode(consigneeCode);
		}
		carditVO.setCarditPawbDetailsVO(carditPawbDetail);
	}

	public void flagMLDForMailOperationsInULD(ContainerVO containerVo, String mode) {
		log.debug(CLASS + " : " + "flagMLDForMailOperationsInULD" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForMailOperationsInULD( containerVo,mode));
		log.debug(CLASS + " : " + "flagMLDForMailOperationsInULD" + " Exiting");
	}

	/** 
	* @author U-1532findLatestContainerAssignmentForUldDelivery
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException 
	*/
	public ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(
			ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerAssignmentVO containerAssignmentVO = null;
		if (scannedMailDetailsVO.getContainerNumber() != null) {
			containerAssignmentVO = Container.findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
		}
		return containerAssignmentVO;
	}

	public void validateContainerReusability(ContainerVO containerVO) throws ContainerAssignmentException {
		if ("F".equals(containerVO.getAssignmentFlag())) {
			ContainerAssignmentVO latestContainerAssignmentVO = findLatestContainerAssignment(
					containerVO.getContainerNumber());
			if (latestContainerAssignmentVO != null) {
				OperationalFlightVO operationalFlightVO = createOperationalFlightVO(latestContainerAssignmentVO);
				if ((isFlightClosedForOperations(operationalFlightVO)
						&& checkForDepartedFlightAtd(latestContainerAssignmentVO))
						&& MailConstantsVO.FLAG_YES.equals(latestContainerAssignmentVO.getTransitFlag())) {
					throw new ContainerAssignmentException(
							ContainerAssignmentException.ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING,

							new Object[] {
									new StringBuilder().append(latestContainerAssignmentVO.getCarrierCode()).append(" ")
											.append(latestContainerAssignmentVO.getFlightNumber()).append(" ")
											.append(latestContainerAssignmentVO.getFlightDate() != null
													? latestContainerAssignmentVO.getFlightDate().format(
													MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT)
													: " ")
											.toString(),
									new StringBuilder().append(latestContainerAssignmentVO.getAirportCode()).toString(),
									latestContainerAssignmentVO });
				} else {
					FlightFilterVO flightFilterVO = createFlightFilterVO(latestContainerAssignmentVO);
					Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
					if (flightValidationVOs != null && flightValidationVOs.size() == 1) {
						FlightValidationVO flightValidationVO = flightValidationVOs.iterator().next();
						if (FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
								|| FlightValidationVO.FLT_STATUS_CANCELLED
								.equals(flightValidationVO.getFlightStatus())) {
							throw new ContainerAssignmentException(
									ContainerAssignmentException.ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING,

									new Object[] {
											new StringBuilder().append(latestContainerAssignmentVO.getCarrierCode())
													.append(" ").append(latestContainerAssignmentVO.getFlightNumber())
													.append(" ")
													.append(latestContainerAssignmentVO.getFlightDate() != null
															? latestContainerAssignmentVO.getFlightDate()
															.format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT)
															: " ")
													.toString(),
											new StringBuilder().append(latestContainerAssignmentVO.getAirportCode())
													.toString(),
											latestContainerAssignmentVO });
						}
					}
				}
			}
		}
	}

	public OperationalFlightVO createOperationalFlightVO(ContainerAssignmentVO containerVO) {
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode(containerVO.getCompanyCode());
		operationalFlightVo.setCarrierId(containerVO.getCarrierId());
		operationalFlightVo.setFlightNumber(containerVO.getFlightNumber());
		operationalFlightVo.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		operationalFlightVo.setPol(containerVO.getAirportCode());
		operationalFlightVo.setLegSerialNumber(containerVO.getLegSerialNumber());
		operationalFlightVo.setFlightDate(containerVO.getFlightDate());
		operationalFlightVo.setCarrierCode(containerVO.getCarrierCode());
		return operationalFlightVo;
	}

	public FlightFilterVO createFlightFilterVO(ContainerAssignmentVO containerVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerVO.getCompanyCode());
		flightFilterVO.setStation(containerVO.getAirportCode());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(containerVO.getFlightDate()));
		flightFilterVO.setCarrierCode(containerVO.getCarrierCode());
		flightFilterVO.setFlightNumber(containerVO.getFlightNumber());
		log.debug("" + " ****** flightFilterVO***** " + " " + flightFilterVO);
		return flightFilterVO;
	}

	public boolean checkForDepartedFlightAtd(ContainerAssignmentVO containerAssignmentVO) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		if (containerAssignmentVO != null) {
			ContainerVO newContainerVO = new ContainerVO();
			newContainerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
			newContainerVO.setCarrierId(containerAssignmentVO.getCarrierId());
			newContainerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
			newContainerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
			newContainerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
			flightValidationVOs = flightOperationsProxy
					.validateFlightForAirport(createFlightFilterVO_atd(newContainerVO));
			if (flightValidationVOs != null) {
				for (FlightValidationVO flightValidationVO : flightValidationVOs) {
					if (flightValidationVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
							&& flightValidationVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()
							&& flightValidationVO.getAtd() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void releaseContainer(ContainerAssignmentVO containerAssignmentVO) {
		Container con = null;
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
		containerPK.setAssignmentPort(containerAssignmentVO.getAirportCode());
		containerPK.setCarrierId(containerAssignmentVO.getCarrierId());
		containerPK.setFlightNumber(containerAssignmentVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
		containerPK.setContainerNumber(containerAssignmentVO.getContainerNumber());
		try {
			con = Container.find(containerPK);
		} catch (FinderException e) {
			log.info("" + "Finder exception found" + " " + e);
		}
		if (con != null) {
			con.setTransitFlag(MailConstantsVO.FLAG_NO);
		}
	}

	private void validateAndReleasePreviousAssignment(ContainerVO containerVOToSave) {
		containerVOToSave.setAssignmentFlag("F");
		try {
			ContextUtil.getInstance().getBean(MailController.class).validateContainerReusability(containerVOToSave);
		} catch (ContainerAssignmentException e) {
			log.info("" + "ContainerAssignmentException" + " " + e);
			if (e.getMessage() != null && e.getMessage().equals(
					ContainerAssignmentException.ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING)) {
				ContainerAssignmentVO latestContainerAssignmentVO = findLatestContainerAssignment(
						containerVOToSave.getContainerNumber());
				releaseContainer(latestContainerAssignmentVO);
			}
		}
	}


	public boolean findPawbCountryValidation(CarditVO carditVO, ConsignmentDocumentVO consignmentDocumentVO) {
		String countryCodeForPawb = findSystemParameterValue("mail.operations.pawbcountryvalidation");
		boolean pawbCountryValidation = false;
		Collection<RoutingInConsignmentVO> routingVos = null;
		if (consignmentDocumentVO != null && consignmentDocumentVO.getRoutingInConsignmentVOs() != null) {
			routingVos = consignmentDocumentVO.getRoutingInConsignmentVOs();
		}
		Set<String> airportCodes = new HashSet<>();
		Collection<String> transitAirportCodes = new ArrayList<>();
		airportCodes.add(carditVO.getCarditPawbDetailsVO().getConsignmentOriginAirport());
		airportCodes.add(carditVO.getCarditPawbDetailsVO().getConsignmentDestinationAirport());
		if (routingVos != null) {
			routingVos.forEach(routingVO -> transitAirportCodes.add(routingVO.getPou()));
		}
		airportCodes.addAll(transitAirportCodes);
		Set<String> countryCodes = new HashSet<>();
		Set<String> transitCountryCodes = new HashSet<>();
		countryCodes.addAll(transitCountryCodes);
		GeneralMasterGroupFilterVO filterVO = new GeneralMasterGroupFilterVO();
		filterVO.setCompanyCode(carditVO.getCompanyCode());
		filterVO.setGroupType(GeneralMasterGroupFilterVO.COUNTRY_GROUP);
		filterVO.setGroupName(countryCodeForPawb);
		filterVO.setGroupCategory("MAL");
		GeneralMasterGroupVO groupVO;
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = null;
		Set<String> listOfPawbCountries = new HashSet<>();
		groupVO = sharedGeneralMasterGroupingProxy.listGeneralMasterGroup(filterVO);
		if (groupVO != null) {
			detailVOs = (ArrayList<GeneralMasterGroupDetailsVO>) groupVO.getGroupDetailsVOs();
			detailVOs.forEach(detailVO -> listOfPawbCountries.add(detailVO.getGroupedEntity()));
		}
		Map<String, AirportValidationVO> airportValidationVos = sharedAreaProxy
				.validateAirportCodes(carditVO.getCompanyCode(), airportCodes);
		if (airportValidationVos != null && !airportValidationVos.isEmpty()) {
			if (listOfPawbCountries.contains(airportValidationVos
					.get(carditVO.getCarditPawbDetailsVO().getConsignmentOriginAirport()).getCountryCode())) {
				return pawbCountryValidation;
			} else if (listOfPawbCountries.contains(airportValidationVos
					.get(carditVO.getCarditPawbDetailsVO().getConsignmentDestinationAirport()).getCountryCode())) {
				pawbCountryValidation = true;
			} else {
				if (transitAirportCodes.stream().anyMatch(airport -> listOfPawbCountries
						.contains(airportValidationVos.get(airport).getCountryCode()))) {
					pawbCountryValidation = true;
				}
			}
		}
		return pawbCountryValidation;
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	* @return
	* @throws SystemException
	*/

	@Cacheable(value = {"securityScreeningValidationCache"},
            key="#securityScreeningValidationFilterVO.toString()")
	public Collection<SecurityScreeningValidationVO> checkForSecurityScreeningValidation(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) throws BusinessException {

		if (securityScreeningValidationFilterVO.getOriginCountry() != null) {
			findGeneralMasterGroup(securityScreeningValidationFilterVO.getOriginCountry(),
					securityScreeningValidationFilterVO, MailConstantsVO.ORG_COUNTRY);
		}
		if (securityScreeningValidationFilterVO.getDestinationCountry() != null) {
			findGeneralMasterGroup(securityScreeningValidationFilterVO.getDestinationCountry(),
					securityScreeningValidationFilterVO, MailConstantsVO.DEST_COUNTRY);
		}
		if (securityScreeningValidationFilterVO.getTransactionCountry() != null) {
			findGeneralMasterGroup(securityScreeningValidationFilterVO.getTransactionCountry(),
					securityScreeningValidationFilterVO, MailConstantsVO.TXN_COUNTRY);
		}
		if (securityScreeningValidationFilterVO.getTransactionAirport() != null) {
			findGeneralMasterGroup(securityScreeningValidationFilterVO.getTransactionAirport(),
					securityScreeningValidationFilterVO, MailConstantsVO.TXN_ARP);
		}
		if (securityScreeningValidationFilterVO.getSecurityStatusCode() != null) {
			findGeneralMasterGroup(securityScreeningValidationFilterVO.getSecurityStatusCode(),
					securityScreeningValidationFilterVO, MailConstantsVO.SCC_GRP);
		}
        return constructDAO().checkForSecurityScreeningValidation(securityScreeningValidationFilterVO);
	}

	/** 
	* @author A-8353
	* @param groupEntities
	* @param securityScreeningValidationFilterVO
	* @param group
	* @throws SystemException
	*/
	private void findGeneralMasterGroup(String groupEntities,
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO, String group)
			throws BusinessException {
		String groupCategorty = "MALSECSCRN";
		String countryType = "CNTGRP";
		String arpType = "ARPGRP";
		String sccGroup = MailConstantsVO.SCC_GRP;
		Collection<String> groupNames = null;
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		if (MailConstantsVO.ORG_COUNTRY.equals(group) || MailConstantsVO.DEST_COUNTRY.equals(group)
				|| MailConstantsVO.TXN_COUNTRY.equals(group)) {
			populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
					securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, countryType, groupEntities);
			groupNames = sharedGeneralMasterGroupingProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);
			if (groupNames != null) {
				if (MailConstantsVO.ORG_COUNTRY.equals(group)) {
					securityScreeningValidationFilterVO.setOriginAirportCountryGroup(
							groupNames.stream().filter("ORGARPCNTGRP"::equals).findAny().orElse(null));
				} else if (MailConstantsVO.DEST_COUNTRY.equals(group)) {
					securityScreeningValidationFilterVO.setDestAirportCountryGroup(
							groupNames.stream().filter(DSTARPCNTGRP::equals).findAny().orElse(null));
				} else {
					securityScreeningValidationFilterVO.setTxnAirportCountryGroup(
							groupNames.stream().filter("TXNARPCNTGRP"::equals).findAny().orElse(null));
				}
			}
		} else if (MailConstantsVO.TXN_ARP.equals(group)) {
			populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
					securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, arpType, groupEntities);
			groupNames = sharedGeneralMasterGroupingProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);
			if (groupNames != null) {
				securityScreeningValidationFilterVO
						.setTxnAirportGroup(groupNames.stream().filter("TXNARPGRP"::equals).findAny().orElse(null));
			}
		} else {
			populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
					securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, sccGroup, groupEntities);
			groupNames = sharedGeneralMasterGroupingProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);
			if (groupNames != null) {
				securityScreeningValidationFilterVO.setSecurityStatusCodeGroup(
						groupNames.stream().filter("SCCGRP"::equals).findAny().orElse(null));
			}
		}
	}

	/** 
	* @author A-8353
	* @param generalMasterGroupFilterVO
	* @param companyCode
	* @param groupCategory
	* @param groupType
	* @param groupEntity
	*/
	private void populateGeneralMasterGroupFilterVO(GeneralMasterGroupFilterVO generalMasterGroupFilterVO,
			String companyCode, String groupCategory, String groupType, String groupEntity) {
		generalMasterGroupFilterVO.setCompanyCode(companyCode);
		generalMasterGroupFilterVO.setGroupCategory(groupCategory);
		generalMasterGroupFilterVO.setGroupType(groupType);
		generalMasterGroupFilterVO.setGroupEntity(groupEntity);
	}

	public Collection<SecurityScreeningValidationVO> findSecurityScreeningValidations(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) {
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(securityScreeningValidationFilterVO.getCompanyCode());
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = null;
		long sequencenum = Mailbag.findMailBagSequenceNumberFromMailIdr(
				securityScreeningValidationFilterVO.getMailbagId(),
				securityScreeningValidationFilterVO.getCompanyCode());
		if (sequencenum > 0) {
			mailbagPk.setMailSequenceNumber(sequencenum);
		}
		findMailbagForSecurityScreeningVal(securityScreeningValidationFilterVO, mailbagPk);
		findCountryCodesForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		if (securityScreeningValidationFilterVO.isAppRegValReq()) {
			populateApplicableRegulationFlagValidationDetails(securityScreeningValidationFilterVO, sequencenum);
		}
		try {
			securityScreeningValidationVOs = mailController.checkForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		} catch (BusinessException e) {
			log.error("Exception :", e);
		}

		return securityScreeningValidationVOs;
	}

	/** 
	* @param securityScreeningValidationFilterVO
	* @param mailbagPk
	* @throws SystemException
	*/
	void findMailbagForSecurityScreeningVal(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO,
			MailbagPK mailbagPk) {
		Mailbag mailbag = null;
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException e) {
			log.info("Exception :", e);
		}
		if (mailbag != null) {
			if (mailbag.getSecurityStatusCode() != null) {
				securityScreeningValidationFilterVO.setSecurityStatusCode(mailbag.getSecurityStatusCode());
			} else {
				securityScreeningValidationFilterVO.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_NSC);
			}
			if (securityScreeningValidationFilterVO.getOriginAirport() == null) {
				securityScreeningValidationFilterVO.setOriginAirport(mailbag.getOrigin());
			}
			if (securityScreeningValidationFilterVO.getDestinationAirport() == null) {
				securityScreeningValidationFilterVO.setDestinationAirport(mailbag.getDestination());
			}
		} else {
			securityScreeningValidationFilterVO.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_NSC);
		}
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	*/
	public void findCountryCodesForSecurityScreeningValidation(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) {
		Collection<String> airportCodes = new ArrayList<>();
		if (securityScreeningValidationFilterVO.getOriginAirport() != null) {
			airportCodes.add(securityScreeningValidationFilterVO.getOriginAirport());
		}
		if (securityScreeningValidationFilterVO.getDestinationAirport() != null) {
			airportCodes.add(securityScreeningValidationFilterVO.getDestinationAirport());
		}
		if (securityScreeningValidationFilterVO.getTransactionAirport() != null) {
			airportCodes.add(securityScreeningValidationFilterVO.getTransactionAirport());
		}
		if (securityScreeningValidationFilterVO.getTransistAirport() != null) {
			airportCodes.add(securityScreeningValidationFilterVO.getTransistAirport());
		}
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<>();
		AirportValidationVO orgAirportValidationVO = null;
		AirportValidationVO desAirportValidationVO = null;
		AirportValidationVO txnAirportValidationVO = null;
		AirportValidationVO trasistAirportValidationVO = null;
		try {
			countryCodeMap = sharedAreaProxy.validateAirportCodes(securityScreeningValidationFilterVO.getCompanyCode(),
					airportCodes);
		} finally {
		}
		populateSecurityScreeningValidationFilterVoCountries(securityScreeningValidationFilterVO, countryCodeMap,
				orgAirportValidationVO, desAirportValidationVO, txnAirportValidationVO, trasistAirportValidationVO);
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	* @param countryCodeMap
	* @param orgAirportValidationVO
	* @param desAirportValidationVO
	* @param txnAirportValidationVO
	* @param trasistAirportValidationVO 
	*/
	private void populateSecurityScreeningValidationFilterVoCountries(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO,
			Map<String, AirportValidationVO> countryCodeMap, AirportValidationVO orgAirportValidationVO,
			AirportValidationVO desAirportValidationVO, AirportValidationVO txnAirportValidationVO,
			AirportValidationVO trasistAirportValidationVO) {
		if (countryCodeMap != null) {
			if (securityScreeningValidationFilterVO.getOriginAirport() != null) {
				orgAirportValidationVO = countryCodeMap.get(securityScreeningValidationFilterVO.getOriginAirport());
			}
			if (securityScreeningValidationFilterVO.getDestinationAirport() != null) {
				desAirportValidationVO = countryCodeMap
						.get(securityScreeningValidationFilterVO.getDestinationAirport());
			}
			if (securityScreeningValidationFilterVO.getTransactionAirport() != null) {
				txnAirportValidationVO = countryCodeMap
						.get(securityScreeningValidationFilterVO.getTransactionAirport());
			}
			if (securityScreeningValidationFilterVO.getTransistAirport() != null) {
				trasistAirportValidationVO = countryCodeMap
						.get(securityScreeningValidationFilterVO.getTransistAirport());
			}
			if (orgAirportValidationVO != null) {
				securityScreeningValidationFilterVO.setOriginCountry(orgAirportValidationVO.getCountryCode());
			}
			if (desAirportValidationVO != null) {
				securityScreeningValidationFilterVO.setDestinationCountry(desAirportValidationVO.getCountryCode());
			}
			if (txnAirportValidationVO != null) {
				securityScreeningValidationFilterVO.setTransactionCountry(txnAirportValidationVO.getCountryCode());
			}
			if (trasistAirportValidationVO != null) {
				securityScreeningValidationFilterVO
						.setAppRegTransistCountry(trasistAirportValidationVO.getCountryCode());
			}
		}
	}

	/** 
	* @author A-8353
	* @param operationalFlightVO
	* @param selectedContainerVOs
	* @return
	* @throws SystemException
	*/
	public SecurityScreeningValidationVO doSecurityAndScreeningValidationAtContainerLevel(
			OperationalFlightVO operationalFlightVO, Collection<ContainerVO> selectedContainerVOs) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String transactionType = null;
		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		for (ContainerVO containerVO : selectedContainerVOs) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
			containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			containerDetailsVO.setCarrierId(containerVO.getCarrierId());
			containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
			containerDetailsVO.setPol(containerVO.getAssignedPort());
			containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
			containerDetailsVO.setContainerType(containerVO.getType());
			containerDetailsVO.setPou(containerVO.getPou());
			transactionType = containerVO.getTransactionCode();
			containers.add(containerDetailsVO);
		}
		containerDetailsVOs = findMailbagsInContainer(containers);
		if (containerDetailsVOs != null) {
			return populateAndCheckSecuirtyScreeningValidationAtContainerLevel(operationalFlightVO, containerDetailsVOs,
					transactionType);
		}
		return null;
	}

	/** 
	* @author A-8353
	* @param operationalFlightVO
	* @param containerDetailsVOs
	* @param transactionType
	* @return
	* @throws SystemException
	*/
	private SecurityScreeningValidationVO populateAndCheckSecuirtyScreeningValidationAtContainerLevel(
			OperationalFlightVO operationalFlightVO, Collection<ContainerDetailsVO> containerDetailsVOs,
			String transactionType) {
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOsColl = new ArrayList<>();
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
			if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
				for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
					SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = populateSecurityScreeningValidationFilterVoForContainer(
							operationalFlightVO, transactionType, mailbagVO, containerDetailsVO.getTransistPort());
					Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = findSecurityScreeningValidations(
							securityScreeningValidationFilterVO);
					if (securityScreeningValidationVOs != null && !securityScreeningValidationVOs.isEmpty()) {
						Optional<SecurityScreeningValidationVO> securityScreeningValidationVO = securityScreeningValidationVOs
								.stream().filter(securityScreeningValidationVo -> MailConstantsVO.ERROR
										.equals(securityScreeningValidationVo.getErrorType()))
								.findFirst();
						if (securityScreeningValidationVO.isPresent()) {
							securityScreeningValidationVO.get().setMailbagID(mailbagVO.getMailbagId());
							return securityScreeningValidationVO.get();
						}
						securityScreeningValidationVOs
								.forEach(securityScreeningValidationVo -> securityScreeningValidationVo
										.setMailbagID(mailbagVO.getMailbagId()));
						securityScreeningValidationVOsColl.addAll(securityScreeningValidationVOs);
					}
				}
			}
		}
		return checkSecurityScreeningValidationVOsColl(securityScreeningValidationVOsColl);
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationVOsColl
	* @return
	*/
	private SecurityScreeningValidationVO checkSecurityScreeningValidationVOsColl(
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVOsColl) {
		if (!securityScreeningValidationVOsColl.isEmpty()) {
			return securityScreeningValidationVOsColl.iterator().next();
		}
		return null;
	}

	/** 
	* @author A-8353
	* @param operationalFlightVO
	* @param transactionType
	* @param mailbagVO
	* @return
	*/
	private SecurityScreeningValidationFilterVO populateSecurityScreeningValidationFilterVoForContainer(
			OperationalFlightVO operationalFlightVO, String transactionType, MailbagVO mailbagVO, String pou) {
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
		securityScreeningValidationFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(transactionType)) {
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ARRIVED);
			securityScreeningValidationFilterVO.setFlightType(operationalFlightVO.getFlightType());
		} else if (!"-1".equals(operationalFlightVO.getFlightNumber())) {
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ASSIGNED);
			securityScreeningValidationFilterVO.setFlightType(operationalFlightVO.getFlightType());
			securityScreeningValidationFilterVO.setAppRegValReq(true);
			securityScreeningValidationFilterVO.setTransistAirport(pou);
		} else {
			securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			securityScreeningValidationFilterVO.setAppRegValReq(true);
		}
		securityScreeningValidationFilterVO.setTransactionAirport(operationalFlightVO.getPol());
		securityScreeningValidationFilterVO.setOriginAirport(mailbagVO.getOrigin());
		securityScreeningValidationFilterVO.setDestinationAirport(mailbagVO.getDestination());
		securityScreeningValidationFilterVO.setMailbagId(mailbagVO.getMailbagId());
		securityScreeningValidationFilterVO.setSubClass(mailbagVO.getMailSubclass());
		return securityScreeningValidationFilterVO;
	}

	/** 
	* @author U-1532
	* @param companyCode
	* @param paCode
	* @returns
	* @throws SystemException
	*/
	public String findDensityfactorForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPartyIdentifierForPA" + " Entering");
		//TODO: use neo service
//		PostalAdministrationCache cache = postalAdministrationCache;
//		return cache.findDensityfactorForPA(companyCode, paCode);
		return null;
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	* @param sequencenum 
	*/
	public void populateApplicableRegulationFlagValidationDetails(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO, long sequencenum) {
		String applicableRegValue = null;
		if (sequencenum > 0) {
			try {
				applicableRegValue = constructDAO().findApplicableRegFlagForMailbag(
						securityScreeningValidationFilterVO.getCompanyCode(), sequencenum);
			} finally {
			}
		}
		if (applicableRegValue != null) {
			securityScreeningValidationFilterVO.setAppRegFlg(applicableRegValue);
		} else {
			securityScreeningValidationFilterVO.setAppRegFlg(NO_VALUE);
		}
		String groupCategorty = "APLREGFLG";
		String countryType = "CNTGRP";
		boolean isOriginDestEqual = false;
		boolean isOriginTransistEqual = false;
		Collection<String> groupNames = null;
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		String originAirportCountry = null;
		originAirportCountry = securityScreeningValidationFilterVO.getTransactionCountry();
		if (originAirportCountry != null) {
			if (originAirportCountry.equals(securityScreeningValidationFilterVO.getDestinationCountry())) {
				isOriginDestEqual = true;
			}
			if (originAirportCountry.equals(securityScreeningValidationFilterVO.getAppRegTransistCountry())) {
				isOriginTransistEqual = true;
			}
			if ((securityScreeningValidationFilterVO.getAppRegTransistCountry() == null || isOriginTransistEqual)
					&& isOriginDestEqual) {
				securityScreeningValidationFilterVO.setAppRegValReq(false);
				return;
			}
			populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
					securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, countryType,
					originAirportCountry);
			groupNames = sharedGeneralMasterGroupingProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);
			if (groupNames != null && groupNames.stream().findFirst().orElse(null) != null) {
				securityScreeningValidationFilterVO.setAppRegDestCountryGroup(NO_GROUP);
				securityScreeningValidationFilterVO.setAppRegTransistCountryGroup(NO_GROUP);
				return;
			}
		}
		if (!isOriginDestEqual && securityScreeningValidationFilterVO.getDestinationCountry() != null) {
			updateDestCountryGrpForAppRegVal(securityScreeningValidationFilterVO, groupCategorty, countryType,
					groupNames, generalMasterGroupFilterVO);
		}
		if (securityScreeningValidationFilterVO.getDestinationCountry() != null && securityScreeningValidationFilterVO
				.getDestinationCountry().equals(securityScreeningValidationFilterVO.getAppRegTransistCountry())) {
			securityScreeningValidationFilterVO
					.setAppRegTransistCountryGroup(securityScreeningValidationFilterVO.getAppRegDestCountryGroup());
		} else {
			if (!isOriginTransistEqual && securityScreeningValidationFilterVO.getAppRegTransistCountry() != null) {
				updateTransistCountryGrpForAppRegVal(securityScreeningValidationFilterVO, groupCategorty, countryType,
						groupNames, generalMasterGroupFilterVO);
			} else {
				securityScreeningValidationFilterVO.setAppRegTransistCountryGroup(NO_GROUP);
			}
		}
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	* @param groupCategorty
	* @param countryType
	* @param groupNames
	* @param generalMasterGroupFilterVO
	*/
	private void updateDestCountryGrpForAppRegVal(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO, String groupCategorty,
			String countryType, Collection<String> groupNames, GeneralMasterGroupFilterVO generalMasterGroupFilterVO) {
		populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
				securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, countryType,
				securityScreeningValidationFilterVO.getDestinationCountry());
		String destCountryGrp = null;
		destCountryGrp = getGroupName(groupNames, generalMasterGroupFilterVO, destCountryGrp);
		if (destCountryGrp != null) {
			securityScreeningValidationFilterVO.setAppRegDestCountryGroup(destCountryGrp);
		} else {
			securityScreeningValidationFilterVO.setAppRegDestCountryGroup(NO_GROUP);
		}
	}

	/** 
	* @author A-8353
	* @param securityScreeningValidationFilterVO
	* @param groupCategorty
	* @param countryType
	* @param groupNames
	* @param generalMasterGroupFilterVO
	*/
	private void updateTransistCountryGrpForAppRegVal(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO, String groupCategorty,
			String countryType, Collection<String> groupNames, GeneralMasterGroupFilterVO generalMasterGroupFilterVO) {
		populateGeneralMasterGroupFilterVO(generalMasterGroupFilterVO,
				securityScreeningValidationFilterVO.getCompanyCode(), groupCategorty, countryType,
				securityScreeningValidationFilterVO.getAppRegTransistCountry());
		String transistCountryGrp = null;
		transistCountryGrp = getGroupName(groupNames, generalMasterGroupFilterVO, transistCountryGrp);
		if (transistCountryGrp != null) {
			securityScreeningValidationFilterVO.setAppRegTransistCountryGroup(transistCountryGrp);
		} else {
			securityScreeningValidationFilterVO.setAppRegTransistCountryGroup(NO_GROUP);
		}
	}

	/** 
	* @author A-8353
	* @param groupNames
	* @param generalMasterGroupFilterVO
	* @return
	*/
	private String getGroupName(Collection<String> groupNames, GeneralMasterGroupFilterVO generalMasterGroupFilterVO,
			String countryGrp) {

			groupNames = sharedGeneralMasterGroupingProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);

		if (groupNames != null) {
			countryGrp = groupNames.stream().findFirst().orElse(null);
		}
		return countryGrp;
	}

	/** 
	* @author A-8353
	* @param mailbagVO
	* @param securityScreeningValidationFilterVO
	* @return
	*/
	public Collection<SecurityScreeningValidationVO> doApplicableRegulationFlagValidationForPABuidContainer(
			MailbagVO mailbagVO, SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) {
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = null;
		findCountryCodesForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		populateApplicableRegulationFlagValidationDetails(securityScreeningValidationFilterVO,
				mailbagVO.getMailSequenceNumber());
		if (!securityScreeningValidationFilterVO.isAppRegValReq()
				&& securityScreeningValidationFilterVO.isSecurityValNotReq()) {
			return securityScreeningValidationVOs;
		}

		try {
			securityScreeningValidationVOs = mailController.checkForSecurityScreeningValidation(securityScreeningValidationFilterVO);
		} catch (BusinessException e) {
			return null;
		}
		return securityScreeningValidationVOs;
	}

	public OperationalFlightVO constructOperationalFlightVO(ContainerVO containerVO) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(containerVO.getCompanyCode());
		operationalFlightVO.setPol(containerVO.getPol() != null ? containerVO.getPol() : containerVO.getAssignedPort());
		operationalFlightVO.setPou(containerVO.getPou());
		operationalFlightVO.setCarrierId(containerVO.getCarrierId());
		operationalFlightVO.setFlightNumber(containerVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		operationalFlightVO.setFlightDate(containerVO.getFlightDate());
		operationalFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		return operationalFlightVO;
	}

	public void calculateContentID(Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO) {
		if (toFlightVO != null && isNotNullAndEmpty(toFlightVO.getFlightNumber())
				&& toFlightVO.getFlightSequenceNumber() > 0 && containerVOs != null && containerVOs.size() > 0
				&& "AA".equals(toFlightVO.getCompanyCode())) {
			Collection<ContainerDetailsVO> containerDetailsVOs = null;
			Collection<ContainerDetailsVO> containerDetailsVOsPayload = null;
			for (ContainerVO containerVO : containerVOs) {
				if (isNotNullAndEmpty(containerVO.getContainerNumber())) {
					if (containerVO.getFlightSequenceNumber() > 0) {
						containerDetailsVOs = findMailbagsInContainer(
								constructContainerDetailsVO(containerVO, toFlightVO));
					} else {
						Collection<ContainerDetailsVO> containerDetailsVO1s = new ArrayList<>();
						if (containerVO.getContentId() == null || "MT".equals(containerVO.getContentId())) {
							ContainerDetailsVO containerDetailsVO1 = new ContainerDetailsVO();
							containerDetailsVO1.setCompanyCode(containerVO.getCompanyCode());
							containerDetailsVO1.setContainerNumber(containerVO.getContainerNumber());
							containerDetailsVO1.setPol(containerVO.getPol());
							containerDetailsVO1.setCarrierId(containerVO.getCarrierId());
							containerDetailsVO1.setFlightNumber(containerVO.getFlightNumber());
							containerDetailsVO1.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
							containerDetailsVO1.setLegSerialNumber(containerVO.getLegSerialNumber());
							containerDetailsVO1.setContainerType(containerVO.getType());
							containerDetailsVO1.setContentId(containerVO.getContentId());
							containerDetailsVO1.setPou(containerVO.getPou());
							containerDetailsVO1.setAcceptedFlag(MailConstantsVO.FLAG_YES);
							containerDetailsVO1s.add(containerDetailsVO1);
							containerDetailsVOs = findMailbagsInContainerWithoutAcceptance(containerDetailsVO1s);
						}
					}
					if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
						if (toFlightVO.getPou() == null || toFlightVO.getPou().trim().isEmpty()) {
							toFlightVO.setPou(containerVO.getPou());
						}
						for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
							if (containerDetailsVO.getMailDetails() == null
									|| containerDetailsVO.getMailDetails().size() == 0) {
								containerDetailsVOs = constructContainerDetailsVO(containerVO, toFlightVO);
							}
						}
						if (containerDetailsVOsPayload == null) {
							containerDetailsVOsPayload = new ArrayList<>();
						}
						containerDetailsVOsPayload.add(containerDetailsVOs.iterator().next());
					}
				}
			}
			//TODO: Below code to be moved to feature in Neo
//			String contentID = (ContextUtil.getInstance().getBean(AAMailController.class))
//					.calculateULDContentId(containerDetailsVOsPayload, toFlightVO);
//			if (isNotNullAndEmpty(contentID)) {
//				for (ContainerVO containerVO : containerVOs) {
//					containerVO.setContentId(contentID);
//				}
//			}
		}
	}

	public Collection<ContainerDetailsVO> constructContainerDetailsVO(ContainerVO containerVO,
			OperationalFlightVO toFlightVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		if (containerVO.getContentId() == null) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
			containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
			containerDetailsVO.setPol(toFlightVO.getPol());
			containerDetailsVO.setCarrierId(toFlightVO.getCarrierId());
			containerDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerDetailsVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerDetailsVO.setContainerType(containerVO.getType());
			containerDetailsVO.setContentId(containerVO.getContentId());
			containerDetailsVOs.add(containerDetailsVO);
		}
		return containerDetailsVOs;
	}

	public Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(
			Collection<ContainerDetailsVO> containers) {
		return MailAcceptance.findMailbagsInContainerWithoutAcceptance(containers);
	}

	public void calculateAndSaveContentId(MailAcceptanceVO mailAcceptanceVO) {
		if (Objects.nonNull(mailAcceptanceVO) && mailAcceptanceVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
				&& Objects.nonNull(mailAcceptanceVO.getContainerDetails())
				&& !mailAcceptanceVO.getContainerDetails().isEmpty()) {
			Collection<ContainerVO> containerVOs = new ArrayList<>();
			for (ContainerDetailsVO containerDetailsVO : mailAcceptanceVO.getContainerDetails()) {
				containerVOs.add(constructContainerVOFromDetails(containerDetailsVO, mailAcceptanceVO));
			}
			calculateContentID(containerVOs, constructOpFlightFromAcp(mailAcceptanceVO));
			saveContentID(containerVOs);
		}
	}

	public void publishMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) {
		log.debug(MODULE + " : " + "publishMailbagDetails" + " Entering");

		var msgBrokerMessageAsyncEProxy = ContextUtil.getInstance().getBean(MsgBrokerMessageAsyncEProxy.class);
		var mailbagDetailsVOs = getMailbagDetails(mailMasterDataFilterVO);
		var mailbagDetailsMessageVO = new MailbagDetailsMessageVO();
		mailbagDetailsMessageVO.setCompanyCode(mailMasterDataFilterVO.getCompanyCode());
		mailbagDetailsMessageVO.setMessageType(MACC);
		mailbagDetailsMessageVO.setMessageStandard(PUBLISH);
		mailbagDetailsMessageVO.setMailbagDetailsVOs(mailbagDetailsVOs);
		
		try {
			msgBrokerMessageAsyncEProxy.encodeAndSaveMessage(mailbagDetailsMessageVO);
		} catch (Exception ex) {
			log.error("Exception in publishMailbagDetails",ex.getMessage());
		}

		if (CollectionUtils.isNotEmpty(mailbagDetailsVOs)) {
			Collection<MailbagVO> mailBags = new ArrayList<>();
			for (MailbagDetailsVO mailbagDetailsVO : mailbagDetailsVOs) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(mailMasterDataFilterVO.getCompanyCode());
				mailbagVO.setMailSequenceNumber(mailbagDetailsVO.getMailSequenceNumber());
				mailBags.add(mailbagVO);
			}
			updateInterfaceFlag(mailBags, "Y");
		}
	}

	public void updateInterfaceFlag(Collection<MailbagVO> mailBags, String interfaceFlag) throws SystemException {

		for(MailbagVO mailbagVO : mailBags){

			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			Mailbag mailbag=null;
			try {
				mailbag = Mailbag.find(mailbagPk);
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex);
			}
			if(mailbag != null){
				mailbag.setIntFlg(interfaceFlag);
			}
		}
	}

	/**
	 * @param mailMasterDataFilterVO
	 * @return MailbagDetailsVO
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 * @author 204082
	 * Added for IASCB-159276 on 20-Oct-2022
	 */
	Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) throws SystemException {
		return new Mailbag().getMailbagDetails(mailMasterDataFilterVO);
	}

	//TODO: Neo to copy the method from classic
	public String findStockHolderForMail(String companyCode, String airportCode, Boolean isGHA) {
		return null;
	}
	//TODO: Neo to copy the method from classic
	public Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO) {
	return null;
	}

	//TODO: Neo to copy the method from classic
	public HashMap<String, String> findAuditTransactionCodes(Collection<String> entities, boolean b, String companyCode) {
		return null;
	}

	public Page<ContainerDetailsVO> getContainersinFlight(OperationalFlightVO operationalFlightVO, int pageNumber) {
		log.debug("Entering "+CLASS+" getContainersinFlight");
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		Page<ContainerDetailsVO> acceptedULDs = null;
		try {
			acceptedULDs =
					constructDAO().findContainerDetails(operationalFlightVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		if ((acceptedULDs != null) && (acceptedULDs.size() > 0)) {
			mailAcceptanceVO.setContainerDetails(acceptedULDs);
		}
		return acceptedULDs;
	}
	//TODO: Neo to copy the method from classic
	public void updateGateClearStatus(OperationalFlightVO operationalFlightVO, String gateClearanceStatus) {
	}
	public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO) {
		return constructDAO().findTransferManifestConsignmentDetails(transferManifestVO);
	}
	//TODO: Neo to copy the method from classic
	public void attachAWBForMailForAddons(Collection<MailBookingDetailVO> mailBookingDetailVOs, Collection<MailbagVO> mailbagVOs) {
	}
	/**
	 * Update the GHT for mailbags Returns a-7938
	 * @return
	 * @throws DuplicateMailBagsException
	 * @throws SystemException
	 * @throws FinderException
	 */
	public void updateGHTformailbags(Collection<OperationalFlightVO> operationalFlightVOs)
			throws BusinessException, FinderException {
		if (operationalFlightVOs != null && !operationalFlightVOs.isEmpty()) {
			GeneralConfigurationFilterVO generalTimeMappingFilterVO = new GeneralConfigurationFilterVO();
			Collection<GeneralConfigurationMasterVO> generalConfigurationMasterVOs = null;
			Collection<MailbagVO> mailbags = null;
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				generalTimeMappingFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				generalTimeMappingFilterVO.setCarrierId(operationalFlightVO.getCarrierId());
				generalTimeMappingFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				generalTimeMappingFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				if (operationalFlightVO.getPou() != null) {
					generalTimeMappingFilterVO.setPou(operationalFlightVO.getPou());
				}
				generalTimeMappingFilterVO.setPol(operationalFlightVO.getPol());
				generalTimeMappingFilterVO.setAirportCode(operationalFlightVO.getAirportCode());
				generalTimeMappingFilterVO.setConfigurationType("MHT");
				try {
					mailbags = constructDAO().findMailBagsForTransportCompletedResdit(operationalFlightVO);
				} catch (PersistenceException e) {
					e.getMessage();
				}
				if (mailbags != null && mailbags.size() > 0) {
					for (MailbagVO milbagVO : mailbags) {
						ZonedDateTime ghttim = null;
						ZonedDateTime ata = operationalFlightVO.getArrivaltime();
						ZonedDateTime arrtim = ata;
						generalConfigurationMasterVOs = sharedDefaultsProxy.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
						if (generalConfigurationMasterVOs != null && !generalConfigurationMasterVOs.isEmpty()) {
							for (GeneralConfigurationMasterVO general : generalConfigurationMasterVOs) {
								String parvalmin = null;
								String parvalhr = null;
								int min = 0;
								int hour = 0;

								if ((operationalFlightVO.getFlightDate().toLocalDateTime().atZone(ZoneId.of("UTC")).isAfter(general.getStartDate().toInstant().atZone(ZoneId.of("UTC")))
										&& operationalFlightVO.getFlightDate().toLocalDateTime().atZone(ZoneId.of("UTC")).isBefore(general.getEndDate().toInstant().atZone(ZoneId.of("UTC"))))
										|| (operationalFlightVO.getFlightDate().toLocalDateTime().atZone(ZoneId.of("UTC"))).isEqual((general.getStartDate().toInstant().atZone(ZoneId.of("UTC"))))
										|| (operationalFlightVO.getFlightDate().toLocalDateTime().atZone(ZoneId.of("UTC"))).isEqual((general.getEndDate().toInstant().atZone(ZoneId.of("UTC"))))) {
									if (milbagVO.getPou().equals(general.getAirportCode())) {
										Collection<GeneralRuleConfigDetailsVO> time = getTimedetails(general);
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
										arrtim = arrtim.plusHours(hour);
										arrtim = arrtim.plusMinutes(min);
										ghttim = arrtim;
									}
								}
							}
						} else {
							ghttim = arrtim;
						}
						MailbagInULDForSegment mailbagInULDForSegment = null;
						MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
						mailbagInULDForSegmentPK.setCompanyCode(milbagVO.getCompanyCode());
						mailbagInULDForSegmentPK.setCarrierId(milbagVO.getCarrierId());
						mailbagInULDForSegmentPK.setFlightNumber(milbagVO.getFlightNumber());
						mailbagInULDForSegmentPK.setFlightSequenceNumber(milbagVO.getFlightSequenceNumber());
						mailbagInULDForSegmentPK.setSegmentSerialNumber(milbagVO.getSegmentSerialNumber());
						if (MailConstantsVO.BULK_TYPE.equals(milbagVO.getContainerType())) {
							mailbagInULDForSegmentPK.setUldNumber("BULK-" + milbagVO.getPou());
						} else {
							mailbagInULDForSegmentPK.setUldNumber(milbagVO.getContainerNumber());
						}
						mailbagInULDForSegmentPK.setMailSequenceNumber(milbagVO.getMailSequenceNumber());
						mailbagInULDForSegment = getMailbagInULDForSegment(mailbagInULDForSegmentPK);
						if (mailbagInULDForSegment != null && ghttim != null) {
							mailbagInULDForSegment.setGhttim(ghttim.toLocalDateTime());
							removeULDForSegment(mailbagInULDForSegment);
							persistMailbagInULDForSegment(mailbagInULDForSegment);
						}
					}
				}
			}
		}
	}

	public void removeULDForSegment(MailbagInULDForSegment mailbagInULDForSegment) {
		mailbagInULDForSegment.remove();
	}

	public void persistMailbagInULDForSegment(MailbagInULDForSegment mailbagInULDForSegment) {
		new MailbagInULDForSegment(mailbagInULDForSegment);
	}
	/**
	 * Validates the mailbags for upload Oct 6, 2006, a-1739
	 * @param mailbags
	 * @return
	 * @throws InvalidMailTagFormatException
	 * @throws SystemException
	 */
	public Collection<MailbagVO> validateScannedMailbagDetails(
			Collection<MailbagVO> mailbags)
			throws InvalidMailTagFormatException {
		boolean isFromHHT = true;
		if (mailbags != null && mailbags.size() > 0
				&& MailConstantsVO.MLD.equals(mailbags.iterator().next().getMailSource())) {
			isFromHHT = false;
		}
		new Mailbag().validateMailBags(mailbags, isFromHHT);
		return mailbags;
	}

	public Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) {
		log.debug(CLASS + " : " + "findMailbagNotes" + " Entering");
		return Mailbag.findMailbagNotes(mailBagId);
	}

	public Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId,
															long mailSequenceNumber) {
		log.debug(CLASS + " : " + "findMailbagHistoriesFromWebScreen" + " Entering");
		return Mailbag.findMailbagHistoriesFromWebScreen(companyCode, mailBagId, mailSequenceNumber);
	}
	/**
	 * @param operationalFlightVOs
	 * @param mailBagVOs
	 * @return
	 * @throws MailMLDBusniessException
	 * @author A-5991
	 */
	public Collection<MailUploadVO> findAndRetriveUpliftedOperations(
			Collection<OperationalFlightVO> operationalFlightVOs, Collection<MailUploadVO> mailBagVOs)
			throws MailMLDBusniessException {
		Collection<MailUploadVO> mailUploadVosForReturn = new ArrayList<MailUploadVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (operationalFlightVOs != null) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
				Collection<MailbagVO> mailBagsForSentResdit = new ArrayList<MailbagVO>();
				totalMailBags = MailAcceptance.findMailBagsForUpliftedResdit(operationalFlightVO);
				for (MailUploadVO mailUploadVOFromMLD : mailBagVOs) {
					boolean containerValid = false;
					long mailSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(
							mailUploadVOFromMLD.getMailTag(), mailUploadVOFromMLD.getCompanyCode());
					MailbagVO mailbagVO = Mailbag.findExistingMailbags(mailUploadVOFromMLD.getCompanyCode(),
							mailSequenceNumber);
					if (mailbagVO == null
							&& MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())
							&& MailConstantsVO.MLD_STG
							.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())) {
						throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_PRESENT_FOR_STG);
					}
					if (MailConstantsVO.MLD_STG.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())
							&& (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus())
							|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus())
							|| MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
							|| !logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort()))) {
						throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_PRESENT_FOR_STG);
					} else if (mailbagVO != null
							&& (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus())
							|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus())
							|| MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
							|| !logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort()))) {
						throw new MailMLDBusniessException(MailMLDBusniessException.MAILBAG_NOT_PRESENT_UPL);
					}
					if (mailbagVO != null && (!operationalFlightVO.getFlightNumber().equals(mailbagVO.getFlightNumber())
							|| operationalFlightVO.getFlightSequenceNumber() != mailbagVO.getFlightSequenceNumber())) {
						mailbagVO.setMailStatus(MailConstantsVO.MAILBAG_ASSIGNMENT_IN);
						totalMailBags.add(mailbagVO);
					} else if (mailbagVO == null
							&& MailConstantsVO.MLD.equalsIgnoreCase(mailBagVOs.iterator().next().getMailSource())
							&& MailConstantsVO.MAIL_STATUS_UPLIFTED
							.equalsIgnoreCase(mailBagVOs.iterator().next().getProcessPoint())) {
						containerValid = true;
						mailUploadVOFromMLD.setScanType(MailConstantsVO.MAILBAG_ASSIGNMENT_IN);
					}
					for (MailbagVO mailbag : totalMailBags) {
						if (mailUploadVOFromMLD.getContainerNumber() != null
								&& mailUploadVOFromMLD.getContainerNumber().equals(mailbag.getContainerNumber())) {
							containerValid = true;
						}
						if ((mailbag.getMailbagId() != null
								&& mailbag.getMailbagId().equals(mailUploadVOFromMLD.getMailTag()))
								|| (mailUploadVOFromMLD.getContainerNumber() != null && mailUploadVOFromMLD
								.getContainerNumber().equals(mailbag.getContainerNumber()))) {
							mailBagsForSentResdit.add(mailbag);
							if (!MailConstantsVO.MAILBAG_ASSIGNMENT_IN.equals(mailbag.getMailStatus())) {
								mailbag.setMailStatus(MailConstantsVO.RESDIT_SEND_STATUS);
								mailbag.setActionMode(MailConstantsVO.RESDIT_SEND_STATUS);
							}
						}
					}
					if (!MailConstantsVO.MLD_STG.equals(mailUploadVOFromMLD.getProcessPoint())
							&& mailUploadVOFromMLD.getContainerNumber() != null && !containerValid) {
						throw new MailMLDBusniessException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN_IN_FLIGHT);
					}
				}
				mailUploadVosForReturn = checkUpliftedOperations(operationalFlightVO, totalMailBags, mailBagVOs);
				break;
			}
		}
		return mailUploadVosForReturn;
	}
	/**
	 * @param operationalFlightVO
	 * @param totalMailBags
	 * @param mailBagVOs
	 * @return
	 * @throws SystemException
	 * @author A-5991
	 */
	private Collection<MailUploadVO> checkUpliftedOperations(OperationalFlightVO operationalFlightVO,
															 Collection<MailbagVO> totalMailBags, Collection<MailUploadVO> mailBagVOs) {
		boolean isFlightClosed = false;
		if (operationalFlightVO.getFlightSequenceNumber() > 0) {
			isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
		}
		for (MailbagVO mailbag : totalMailBags) {
			if (!MailConstantsVO.RESDIT_SEND_STATUS.equals(mailbag.getMailStatus())
					&& !MailConstantsVO.MAILBAG_ASSIGNMENT_IN.equals(mailbag.getMailStatus())) {
				if (isFlightClosed) {
					mailbag.setActionMode(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				} else {
					mailbag.setActionMode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
				}
			} else if (MailConstantsVO.MAILBAG_ASSIGNMENT_IN.equals(mailbag.getMailStatus())) {
				mailbag.setActionMode(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			}
		}
		for (MailUploadVO mailUploadVo : mailBagVOs) {
			for (MailbagVO mailbag : totalMailBags) {
				if (mailUploadVo.getMailTag().equals(mailbag.getMailbagId())) {
					if (MailConstantsVO.RESDIT_SEND_STATUS.equals(mailbag.getActionMode())) {
						mailUploadVo.setScanType(MailConstantsVO.RESDIT_SEND_STATUS);
					} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbag.getActionMode())) {
						mailUploadVo.setScanType(MailConstantsVO.MAILBAG_ASSIGNMENT_IN);
					}
				}
			}
		}
		return mailBagVOs;
	}

	/**
	 * @param operationalFlightVOs
	 * @param mailBagVOs
	 * @throws SystemException
	 * @author A-5526 This method is used to find the Mailbags Accepted to aFlight and UpliftedResdit to be flagged for the Same .
	 */
	public void flagUpliftedResditForMailbagsForMLD(Collection<OperationalFlightVO> operationalFlightVOs,
													Collection<MailUploadVO> mailBagVOs) {
		log.debug(CLASS + " : " + "flagUpliftedResditForMailbags" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			if (operationalFlightVOs != null) {
				for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
					Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailBagsForSentResdit = new ArrayList<MailbagVO>();
					totalMailBags = MailAcceptance.findMailBagsForUpliftedResdit(operationalFlightVO);
					for (MailUploadVO mailUploadVOFromMLD : mailBagVOs) {
						for (MailbagVO mailbag : totalMailBags) {
							if (mailbag.getMailbagId().equals(mailUploadVOFromMLD.getMailTag())
									|| (mailUploadVOFromMLD.getContainerNumber() != null && mailUploadVOFromMLD
									.getContainerNumber().equals(mailbag.getContainerNumber()))) {
								mailbag.setCarrierCode(mailUploadVOFromMLD.getCarrierCode());
								if (!MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getMailStatus())) {
									mailbag.setFlightDate(mailUploadVOFromMLD.getFlightDate());
								}
								mailbag.setScannedDate(localDateUtil.getLocalDate(
										logonAttributes.getAirportCode(), true));
								mailbag.setScannedUser(logonAttributes.getUserId());
								mailbag.setPou(mailUploadVOFromMLD.getContainerPOU());
								mailbag.setFinalDestination(mailUploadVOFromMLD.getContainerPOU());
								mailBagsForSentResdit.add(mailbag);
								if (!MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getMailStatus())) {
									mailbag.setMailStatus(MailConstantsVO.RESDIT_SEND_STATUS);
								}
							}
						}
					}
					checkAndProceedOffloadorReassignForUPLMessage(operationalFlightVO, totalMailBags);
					Collection<ContainerDetailsVO> containerDetailsVOs = null;
					containerDetailsVOs = MailAcceptance.findUldsForUpliftedResdit(operationalFlightVO);
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagResditsForFlightDeparture(operationalFlightVO.getCompanyCode(),
							operationalFlightVO.getCarrierId(), mailBagsForSentResdit, containerDetailsVOs,
							operationalFlightVO.getPol());
					String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
					if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
						mailController.flagMLDForMailOperations(mailBagsForSentResdit, "UPL");
					}
					break;
				}
			}
		}
		log.debug(CLASS + " : " + "flagUpliftedResditForMailbags" + " Exiting");
	}

	/**
	 * Created as part of CR ICRD-89077 by A-5526 Method is used to offload or reassign the missing mailbags(in UPL message) to carrier w.r.t to flight status
	 * @param operationalFlightVO
	 * @param totalMailBags
	 * @throws SystemException
	 */
	private void checkAndProceedOffloadorReassignForUPLMessage(OperationalFlightVO operationalFlightVO,
															   Collection<MailbagVO> totalMailBags) {
		boolean isFlightClosed = false;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagsForReassign = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailBagsForOffload = new ArrayList<MailbagVO>();
		isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
		for (MailbagVO mailbag : totalMailBags) {
			updateMailbagVOsForUPLOffloadOrReassign(mailbag, operationalFlightVO);
			if (!MailConstantsVO.RESDIT_SEND_STATUS.equals(mailbag.getMailStatus())
					&& !MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getMailStatus())) {
				if (isFlightClosed) {
					mailbag.setActionMode(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					mailbag.setIsoffload(true);
					mailbag.setOffloadedRemarks(OFFLOADED_FOR_MLD_UPL);
					mailbag.setOffloadedDescription(OFFLOADED_FOR_MLD_UPL);
					mailbag.setOffloadedReason("2");
					mailBagsForOffload.add(mailbag);
				} else {
					mailBagsForReassign.add(mailbag);
				}
			}
		}
		if (mailBagsForOffload != null && mailBagsForOffload.size() > 0) {
			scannedMailDetailsVO.setMailDetails(mailBagsForOffload);
			Collection<OffloadVO> offloadMailbagVOs = new MailUploadController().makeOffloadVOs(scannedMailDetailsVO,
					logonAttributes);
			for (OffloadVO offloadVO : offloadMailbagVOs) {
				try {
					offload(offloadVO);
				} catch (FlightClosedException e) {
					log.debug("FlightClosedException-offload");
				} catch (FlightDepartedException e) {
					log.debug("FlightDepartedException-offload");
				} catch (ReassignmentException e) {
					log.debug("ReassignmentException-offload");
				} catch (ULDDefaultsProxyException e) {
					log.debug("ULDDefaultsProxyException-offload");
				} catch (CapacityBookingProxyException e) {
					log.debug("CapacityBookingProxyException-offload");
				} catch (MailBookingException e) {
					log.debug("MailBookingException-offload");
				}
			}
		} else {
			MailbagVO mailbag = null;
			try {
				if (mailBagsForReassign != null && mailBagsForReassign.size() > 0) {
					ContainerVO toContainerVO = new ContainerVO();
					mailbag = mailBagsForReassign.iterator().next();
					createAndUpdateToContainerDetails(mailbag, toContainerVO, null, logonAttributes);
					reassignMailbags(mailBagsForReassign, toContainerVO);
				}
			} catch (FlightClosedException e) {
				log.debug("FlightClosedException-reassignMailbags");
			} catch (ReassignmentException e) {
				log.debug("ReassignmentException-reassignMailbags");
			} catch (InvalidFlightSegmentException e) {
				log.debug("InvalidFlightSegmentException-reassignMailbags");
			} catch (CapacityBookingProxyException e) {
				log.debug("CapacityBookingProxyException-reassignMailbags");
			} catch (MailBookingException e) {
				log.debug("MailBookingException-reassignMailbags");
			}
		}
	}

	/**
	 * Added as part OF CR ICRD-89077 by A-5526 Method to create toContainer info for reassignment to default trolley as part of UPL message processing.
	 * @param mailbag
	 * @param toContainerVO
	 * @param operationalFlightVO
	 * @param logonAttributes
	 * @throws SystemException
	 */
	private void createAndUpdateToContainerDetails(MailbagVO mailbag, ContainerVO toContainerVO,
												   OperationalFlightVO operationalFlightVO, LoginProfile logonAttributes) {
		String serialNumberForcarrier = "";
		String defaultSU = findSystemParameterValue("mailtracking.defaults.defaultstorageunitforMLDairports");
		StringBuilder uldNumberForCarrier = new StringBuilder();
		toContainerVO.setCompanyCode(mailbag.getCompanyCode());
		toContainerVO.setCarrierCode(mailbag.getCarrierCode());
		toContainerVO.setCarrierId(mailbag.getCarrierId());
		toContainerVO.setAssignedPort(mailbag.getPol());
		if (MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbag.getMailStatus())) {
			toContainerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			toContainerVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
			toContainerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			toContainerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			toContainerVO.setFlightDate(mailbag.getFromFlightDate());
			toContainerVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			toContainerVO.setSegmentSerialNumber(operationalFlightVO.getSegSerNum());
			toContainerVO.setPol(mailbag.getPol());
			toContainerVO.setPou(mailbag.getPou());
			toContainerVO.setOwnAirlineCode(mailbag.getCarrierCode());
			toContainerVO.setOwnAirlineId(mailbag.getCarrierId());
			if (mailbag.getInventoryContainer() != null && mailbag.getInventoryContainer().trim().length() > 0) {
				toContainerVO.setContainerNumber(mailbag.getInventoryContainer());
				toContainerVO.setType(mailbag.getInventoryContainerType());
				mailbag.setInventoryContainer(mailbag.getContainerNumber());
				mailbag.setInventoryContainerType(mailbag.getContainerType());
			}
		} else {
			toContainerVO.setFlightNumber("-1");
			toContainerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			toContainerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		}
		toContainerVO.setFinalDestination(mailbag.getPou());
		serialNumberForcarrier = uldNumberForCarrier.append(toContainerVO.getCompanyCode()).append("-")
				.append(defaultSU).append("-").append(toContainerVO.getFinalDestination()).toString();
		if (toContainerVO.getContainerNumber() == null || toContainerVO.getContainerNumber().trim().length() == 0) {
			toContainerVO.setContainerNumber(serialNumberForcarrier);
		}
		toContainerVO.setLastUpdateTime(
				localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true));
	}

	/**
	 * This method is to populateMailPK values and set some required field values in mailbag Vo from operationalFlightVO
	 * @param mailbag
	 * @param operationalFlightVO
	 */
	private void updateMailbagVOsForUPLOffloadOrReassign(MailbagVO mailbag, OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "updateMailbagVOsForUPLOffloadOrReassign" + " Entering");
		mailbag.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		mailbag.setPol(operationalFlightVO.getPol());
		mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		populateMailPKFields(mailbag);
		log.debug(CLASS + " : " + "updateMailbagVOsForUPLOffloadOrReassign" + " Exiting");
	}
	/**
	 * Method		:	MailController.findAlreadyAssignedTrolleyNumberForMLD Added by 	:	A-4803 on 28-Oct-2014 Used for 	:	To find whether a container is already presnet for the mail bag Parameters	:	@param mldMasterVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	String
	 */
	public String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) {
		log.debug(CLASS + " : " + "findAlreadyAssignedTrolleyNumberForMLD" + " Entering");
		String containerNumber = Container.findAlreadyAssignedTrolleyNumberForMLD(mldMasterVO);
		log.debug(CLASS + " : " + "findAlreadyAssignedTrolleyNumberForMLD" + " Exiting");
		return containerNumber;
	}
	/**
	 * @param containerDetailsVOs
	 * @throws SystemException
	 * @author A-1936
	 */
	public void unassignEmptyULDs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		log.debug(CLASS + " : " + "unassignEmptyULDs" + " Entering");
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if ((!ContainerDetailsVO.FLAG_NO.equals(containerDetailsVO.getAcceptedFlag()))
						&& (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType()))) {
					if (containerDetailsVO.getFlightSequenceNumber() > 0) {
						consrtuctAndRemoveULDForSegment(containerDetailsVO);
					} else {
						consrtuctAndRemoveULDForAirport(containerDetailsVO);
					}
				}
				consrtuctAndRemoveContainer(containerDetailsVO);
			}
		}
	}

	/**
	 * @param containerDetailsVO
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find and remove Container.
	 */
	private void consrtuctAndRemoveContainer(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "consrtuctContainerPK" + " Entering");
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerPK.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerPK.setAssignmentPort(containerDetailsVO.getPol());
		containerPK.setCarrierId(containerDetailsVO.getCarrierId());
		containerPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerPK.setLegSerialNumber(Container.findFlightLegSerialNumber(constructContainerVO(containerDetailsVO)));
		Container emptyContainer = null;
		try {
			emptyContainer = Container.find(containerPK);
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerPK.getCompanyCode());
		uldInFlightVO.setUldNumber(containerPK.getContainerNumber());
		uldInFlightVO.setPou(emptyContainer.getPou());
		uldInFlightVO.setAirportCode(containerPK.getAssignmentPort());
		uldInFlightVO.setCarrierId(containerPK.getCarrierId());
		if (containerPK.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerPK.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerPK.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerPK.getLegSerialNumber());
		}
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		emptyContainer.remove();
		Collection<UldInFlightVO> operationalUlds = new ArrayList<>();
		operationalUlds.add(uldInFlightVO);
		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		if (isOprUldEnabled && (!MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType()))) {
			operationsFltHandlingProxy.saveOperationalULDsInFlight(operationalUlds);
		}
		containerDetailsVO.setCarrierCode(emptyContainer.getCarrierCode());
		ZonedDateTime date = localDateUtil.getLocalDate(emptyContainer.getAssignmentPort(), true);
		containerDetailsVO.setAssignedDate(date);
		containerDetailsVO.setAssignedPort(emptyContainer.getAssignmentPort());
		containerDetailsVO.setPou(emptyContainer.getPou());
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagContainerAuditForUnassignment(containerDetailsVO);
	}

	/**
	 * This method is used to consrtuctULDForSegmentPK and remove . A-1936
	 * @param containerDetailsVO
	 * @throws SystemException
	 */
	private void consrtuctAndRemoveULDForSegment(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "consrtuctULDForSegmentPK" + " Entering");
		log.debug("" + "The ContainerDetails Vo is " + " " + containerDetailsVO);
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldForSegmentPK.setCarrierId(containerDetailsVO.getCarrierId());
		uldForSegmentPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		uldForSegmentPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		uldForSegmentPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		uldForSegmentPK.setUldNumber(containerDetailsVO.getContainerNumber());
		try {
			ULDForSegment.find(uldForSegmentPK).remove();
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/**
	 * This method is used to consrtuctAndRemoveULDForAirport and remove . A-2883
	 * @param containerDetailsVO
	 * @throws SystemException
	 */
	private void consrtuctAndRemoveULDForAirport(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "consrtuctULDForSegmentPK" + " Entering");
		log.debug("" + "The ContainerDetails Vo is " + " " + containerDetailsVO);
		ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		uldAtAirportPK.setCarrierId(containerDetailsVO.getCarrierId());
		uldAtAirportPK.setAirportCode(containerDetailsVO.getPol());
		uldAtAirportPK.setUldNumber(containerDetailsVO.getContainerNumber());
		try {
			ULDAtAirport.find(uldAtAirportPK).remove();
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/**
	 * This method checks to see if any of the mailbags being returned were actually returned already. If so it throws MailbagAlreadyReturneException If the mailbag does not belong to a partner carrier, ReturnNotPossible is thrown A-1739
	 * @param mailbagsToReturn
	 * @param isScanned        TODO
	 * @return
	 * @throws MailbagAlreadyReturnedException
	 * @throws SystemException
	 * @throws ReturnNotPossibleException
	 */
	private Collection<MailbagVO> checkForReturnedMailbags(Collection<MailbagVO> mailbagsToReturn, boolean isScanned)
			throws MailbagAlreadyReturnedException, ReturnNotPossibleException {
		Collection<MailbagVO> returnedMails = new ArrayList<MailbagVO>();
		Collection<MailbagVO> exceptionMails = new ArrayList<MailbagVO>();
		Collection<MailbagVO> invalidMails = validatePartnerForReturnMailBags(mailbagsToReturn);
		if (invalidMails != null && invalidMails.size() > 0) {
			if (isScanned) {
				for (MailbagVO invalidMailVO : invalidMails) {
					invalidMailVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
					invalidMailVO.setErrorDescription(MailConstantsVO.ERMSG_HANDOVER_RETURN);
				}
				exceptionMails.addAll(invalidMails);
				mailbagsToReturn.removeAll(invalidMails);
			} else {
				throw new ReturnNotPossibleException(ReturnNotPossibleException.INVALID_MAILBAGS_FORRETURN,
						new Object[] { constructMailbagErrorData(invalidMails) });
			}
		}
		for (MailbagVO mailbagVO : mailbagsToReturn) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagVO.getLatestStatus())) {
				returnedMails.add(mailbagVO);
				if (isScanned) {
					mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
					mailbagVO.setErrorDescription(MailConstantsVO.ERR_MSG_DUPLICATE_RETURN);
				}
			}
		}
		if (returnedMails != null && returnedMails.size() > 0) {
			if (isScanned) {
				exceptionMails.addAll(returnedMails);
				mailbagsToReturn.removeAll(returnedMails);
			} else {
				throw new MailbagAlreadyReturnedException(new Object[] { constructMailbagErrorData(returnedMails) });
			}
		}
		return exceptionMails;
	}

	/**
	 * A-1739
	 * @param mailBags
	 * @return
	 */
	private String constructMailbagErrorData(Collection<MailbagVO> mailBags) {
		StringBuilder errMailbags = new StringBuilder();
		for (MailbagVO duplicateBag : mailBags) {
			errMailbags.append(duplicateBag.getMailbagId()).append(", ");
		}
		return errMailbags.substring(0, errMailbags.length() - 2).toString();
	}

	/**
	 * If the mailbag is not with the ownairline's then it cannot be returned Oct 10, 2006, a-1739
	 * @param mailbagsToReturn
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> validatePartnerForReturnMailBags(Collection<MailbagVO> mailbagsToReturn) {
		Collection<MailbagVO> invalidMails = new ArrayList<MailbagVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String carrierCode = null;
		MailbagVO mailBagVo = new ArrayList<MailbagVO>(mailbagsToReturn).get(0);
		log.debug("" + "The Mailbag Vo is " + " " + mailBagVo);
		
		String companyCode = mailBagVo.getCompanyCode();
		String ownCarrierCode = logonAttributes.getOwnAirlineCode();
		String airportCode = logonAttributes.getAirportCode();
		Collection<PartnerCarrierVO> partnerCarierVos =
				mailOperationsMapper.partnerCarrierModelstoPartnerCarrierVos(
						mailMasterApi.findAllPartnerCarriers(companyCode, ownCarrierCode,
								airportCode));
		for (MailbagVO mailBag : mailbagsToReturn) {
			carrierCode = mailBag.getCarrierCode();
			if (carrierCode != null && !(mailBag.getOwnAirlineCode().equals(carrierCode))
					&& !(validateCarrierCodeFromPartner(partnerCarierVos, carrierCode))) {
				invalidMails.add(mailBag);
			}
		}
		return invalidMails;
	}

	/**
	 * This method is used to Check whether the CarrierCode is Present in one amongst the Partners if Present return true else false A-1936
	 * @param partnerCarierVos
	 * @param carrierCode
	 * @return true, if valid carrier
	 */
	private boolean validateCarrierCodeFromPartner(Collection<PartnerCarrierVO> partnerCarierVos, String carrierCode) {
		boolean isValid = false;
		if (partnerCarierVos != null && partnerCarierVos.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarierVos) {
				log.debug("" + "The Carrier Code is " + " " + carrierCode);
				log.debug("" + "The  Partner Carrier Code is " + " " + partnerCarrierVO.getPartnerCarrierCode());
				if (carrierCode.equals(partnerCarrierVO.getPartnerCarrierCode())) {
					isValid = true;
					break;
				}
			}
		}
		log.debug("" + "<<<<Is PartnerPresent>>>>" + " " + isValid);
		return isValid;
	}
	/**
	 * This method marks all the mailbags as returned. It removes their assignment and removes all flight and container details from its master data, but the mailbags are not removed from the master It also returns the ULDs which becomes empty because of the return A-1739
	 * @param mailbagsToReturn
	 * @return the empty ULDs
	 * @throws MailbagAlreadyReturnedException
	 * @throws FlightClosedException
	 * @throws SystemException
	 * @throws ReturnNotPossibleException
	 * @throws ReassignmentException
	 * @throws CapacityBookingProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 * @throws DuplicateMailBagsException
	 */
	public Collection<ContainerDetailsVO> returnMailbags(Collection<MailbagVO> mailbagsToReturn)
			throws MailbagAlreadyReturnedException, FlightClosedException, ReturnNotPossibleException,
			ReassignmentException, CapacityBookingProxyException, MailBookingException, DuplicateMailBagsException {
		log.debug(CLASS + " : " + "returnMailbags" + " Entering");
		checkForReturnedMailbags(mailbagsToReturn, false);
		String airportCode = getLogonAirport();
		Collection<MailbagVO> flightAssignedMails = new ArrayList<MailbagVO>();
		Collection<MailbagVO> destAssignedMails = new ArrayList<MailbagVO>();
		Collection<ContainerDetailsVO> unassignSBULDs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> returnEmptyULDs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> emptyULDs = null;
		if (reassignController.isReassignableMailbags(mailbagsToReturn, flightAssignedMails, destAssignedMails)) {
			try {
				emptyULDs = new ReassignController().reassignMailFromFlight(flightAssignedMails);
				new ReassignController().reassignMailFromDestination(destAssignedMails);
			} finally {
			}
		}
		if (emptyULDs != null && emptyULDs.size() > 0) {
			for (ContainerDetailsVO containerDetailsVO : emptyULDs) {
				if (ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO.getFlagPAULDResidit())) {
					unassignSBULDs.add(containerDetailsVO);
				} else {
					returnEmptyULDs.add(containerDetailsVO);
				}
			}
		}
		try {
			updateMailbagReturnDetails(airportCode, mailbagsToReturn, false);
		} finally {
		}
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (importEnabled != null && importEnabled.contains("D")) {
			Collection<RateAuditVO> rateAuditVOs = createRateAuditVOsForReturn(emptyULDs, mailbagsToReturn,
					MailConstantsVO.MAIL_STATUS_RETURNED);
			if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			Collection<RateAuditVO> provisionalRateAuditVOs =
					createRateAuditVOsForReturn(emptyULDs, mailbagsToReturn, MailConstantsVO.MAIL_STATUS_RETURNED);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		for (MailbagVO mailbagVO : mailbagsToReturn) {
			if (mailbagVO.getAttachments() != null && !mailbagVO.getAttachments().isEmpty()) {
				uploadDocumentsToRepository(mailbagVO);
			}
		}
		unassignEmptyULDs(unassignSBULDs);
		if (unassignSBULDs != null && unassignSBULDs.size() > 0) {
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagReturnedResditForULDs(unassignSBULDs);
		}
		log.debug(CLASS + " : " + "returnMailbags" + " Exiting");
		return returnEmptyULDs;
	}

	/**
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @author a-5160 This method is used tovalidate the FlightForMail
	 */
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) {
		log.debug(CLASS + " : " + "validateFlight" + " Entering");
		return AssignedFlight.validateMailFlight(flightFilterVO);
	}

	/**
	 * THis method is used to get the warehouse details for the accept mail
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Collection<String>> findWarehouseTransactionLocations(LocationEnquiryFilterVO filterVO) {
		log.debug(CLASS + " : " + "findWarehouseTransactionLocations" + " Entering");
		return MailAcceptance.findWarehouseTransactionLocations(filterVO.getCompanyCode(), filterVO.getAirportCode(),
				filterVO.getWarehouseCode(), filterVO.getTransactionCodes());
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find all the WareHouses for theGiven Airport
	 */
	public Collection<WarehouseVO> findAllWarehouses(String companyCode, String airportCode) {
		return MailAcceptance.findAllWarehouses(companyCode, airportCode);
	}

	/**
	 * This method is used to validate the Location A-1739
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param locationCode
	 * @return the locationvalidationVO
	 * @throws SystemException
	 */
	public LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode,
												 String locationCode) {
		log.debug(CLASS + " : " + "validateLocation" + " Entering");
		return MailAcceptance.validateLocation(companyCode, airportCode, warehouseCode, locationCode);
	}

	/**
	 * @param companyCode
	 * @param despatchDetailsVOs
	 * @return
	 * @throws SystemException
	 * @author A-3227  - FEB 10, 2009
	 */
	public Collection<DespatchDetailsVO> validateConsignmentDetails(String companyCode,
																	Collection<DespatchDetailsVO> despatchDetailsVOs) {
		log.debug(CLASS + " : " + "validateConsignmentDetails" + " Entering");
		return documentController.validateConsignmentDetails(companyCode, despatchDetailsVOs);
	}

	/**
	 * This method is used to validate the DSNs say DOE,OOE A-1936
	 * @param dsnVos
	 * @return true if all dsns are valid
	 * @throws SystemException
	 * @throws InvalidMailTagFormatException
	 */
	public boolean validateDSNs(Collection<DSNVO> dsnVos) throws InvalidMailTagFormatException {
		log.debug(CLASS + " : " + "validateDSNs" + " Entering");
		HashMap<String, Collection<String>> hashMap = null;
		Map<String, Map<String, CityVO>> cityMaps = new HashMap<String, Map<String, CityVO>>();
		String originOfficeExchange = null;
		String destinationOfficeExchange = null;
		if (dsnVos != null && dsnVos.size() > 0) {
			for (DSNVO dsnVo : dsnVos) {
				log.debug("The Operational Flag is " + dsnVo.getOperationFlag());
				if (DSNVO.OPERATION_FLAG_INSERT.equals(dsnVo.getOperationFlag())) {
					if (hashMap == null) {
						hashMap = new HashMap<String, Collection<String>>();
						hashMap.put(COUNTRY_CACHE, new ArrayList<String>());
						hashMap.put(CITY_CACHE, new ArrayList<String>());
						hashMap.put(EXCHANGE_CACHE, new ArrayList<String>());
						hashMap.put(SUBCLS_CACHE, new ArrayList<String>());
						hashMap.put(ERROR_CACHE, new ArrayList<String>());
						hashMap.put(CITYPAIR_CACHE, new ArrayList<String>());
					}
					originOfficeExchange = dsnVo.getOriginExchangeOffice();
					destinationOfficeExchange = dsnVo.getDestinationExchangeOffice();
					log.debug("THE OOE IS " + originOfficeExchange);
					log.debug("THE DOE IS " + destinationOfficeExchange);
					if (originOfficeExchange != null && !hashMap.get(EXCHANGE_CACHE).contains(originOfficeExchange)) {
						log.info("A valid OOE is not there in Map");
						new Mailbag().validateOfficeExchange(dsnVo.getCompanyCode(), originOfficeExchange, hashMap,
								cityMaps, true);
					}
					if (destinationOfficeExchange != null
							&& !hashMap.get(EXCHANGE_CACHE).contains(destinationOfficeExchange)) {
						log.info("A valid DOE is not there in Map");
						new Mailbag().validateOfficeExchange(dsnVo.getCompanyCode(), destinationOfficeExchange, hashMap,
								cityMaps, false);
					}
					String mailSubClass = dsnVo.getMailSubclass();
					if (mailSubClass != null && !mailSubClass.contains(MailConstantsVO.DUMMY_SUBCLS)) {
						validateSubClass(dsnVo.getCompanyCode(), mailSubClass, hashMap);
					}
					if (!(hashMap.get(ERROR_CACHE).size() == 0)) {
						String str = createErrorsForDSN(dsnVo, hashMap.get(ERROR_CACHE));
						throw new InvalidMailTagFormatException(InvalidMailTagFormatException.INVALID_MAILFORMAT,
								new Object[] { str });
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param dsnVo
	 * @param errors
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to collect the erros in case ofValidation of DSNs
	 */
	private String createErrorsForDSN(DSNVO dsnVo, Collection<String> errors) {
		log.debug("INSIDE THE CREATE ERRORS" + " : " + "createErrors" + " Entering");
		StringBuilder builder = null;
		String error = "";
		HashMap<String, String> cityOEMap = null;
		Collection<String> officeOfExchanges = new ArrayList<String>();
		String cityOrigin = null;
		String cityDestination = null;
		if (!officeOfExchanges.contains(dsnVo.getOriginExchangeOffice())) {
			officeOfExchanges.add(dsnVo.getOriginExchangeOffice());
		}
		if (!officeOfExchanges.contains(dsnVo.getDestinationExchangeOffice())) {
			officeOfExchanges.add(dsnVo.getDestinationExchangeOffice());
		}
		cityOEMap = new MailController().findCityForOfficeOfExchange(dsnVo.getCompanyCode(), officeOfExchanges);
		String countryOrigin = dsnVo.getOriginExchangeOffice().substring(0, 2);
		String countryDestination = null;
		if (dsnVo.getDestinationExchangeOffice() != null) {
			countryDestination = dsnVo.getDestinationExchangeOffice().substring(0, 2);
		}
		if (cityOEMap != null && cityOEMap.size() > 0) {
			cityOrigin = cityOEMap.get(dsnVo.getOriginExchangeOffice());
			cityDestination = cityOEMap.get(dsnVo.getDestinationExchangeOffice());
		}
		if (errors != null && errors.size() > 0) {
			builder = new StringBuilder();
			for (String str : errors) {
				if (str.equals(CITY_ORIGIN)) {
					builder.append("City Code ").append(cityOrigin).append("\n");
				} else if (str.equals(CITY_DESTINATION)) {
					builder.append("City Code ").append(cityDestination).append("\n");
				} else if (str.equals(COUNTRY_ORIGIN)) {
					builder.append("Country Code ").append(countryOrigin).append("\n");
				} else if (str.equals(COUNTRY_DESTINATION)) {
					builder.append("Country Code ").append(countryDestination).append("\n");
				} else if (str.equals(ORIGINEXCHANGE)) {
					builder.append(ORIGINEXCHANGE).append(" ").append(dsnVo.getOriginExchangeOffice()).append("\n");
				} else if (str.equals(DESTINATIONEXCHANGE)) {
					builder.append(DESTINATIONEXCHANGE).append(" ").append(dsnVo.getDestinationExchangeOffice())
							.append("\n");
				} else if (str.equals(PAIR_ORIGIN)) {
					builder.append("City-Country Pair").append(" ").append(dsnVo.getOriginExchangeOffice())
							.append("\n");
				} else if (str.equals(PAIR_DESTINATION)) {
					builder.append("City-Country Pair").append(" ").append(dsnVo.getDestinationExchangeOffice())
							.append("\n");
				} else if (str.equals(SUBCLS_CACHE)) {
					builder.append(SUBCLS_CACHE).append(" ").append(dsnVo.getMailSubclass()).append("\n");
				}
			}
			error = builder.append(" is invalid ").append(" for ").append(dsnVo.getCompanyCode()).append("-")
					.append(dsnVo.getOriginExchangeOffice()).append("-").append(dsnVo.getDestinationExchangeOffice())
					.append("-").append(dsnVo.getMailSubclass()).append("-").append(dsnVo.getYear()).toString();
			log.debug("" + "THE ERROR CREATED IS " + " " + error);
		}
		return error;
	}

	/**
	 * @param companyCode
	 * @param subclass
	 * @param hashMap
	 * @throws SystemException
	 * @author a-1936This method is used to validate the MailSubClass
	 */
	private void validateSubClass(String companyCode, String subclass, HashMap<String, Collection<String>> hashMap) {
		log.debug("DSN" + " : " + "validateSubClass" + " Entering");
		boolean isValidSubClass = false;
		if (hashMap.get(SUBCLS_CACHE).contains(subclass)) {
			isValidSubClass = true;
		} else {
			isValidSubClass = validateMailSubClass(companyCode, subclass);
			if (isValidSubClass) {
				if (!hashMap.get(SUBCLS_CACHE).contains(subclass)) {
					hashMap.get(SUBCLS_CACHE).add(subclass);
				}
			} else {
				hashMap.get(ERROR_CACHE).add(SUBCLS_CACHE);
			}
		}
	}

	/**
	 * Method to find all ulds in flight Added as part of ICRD-115893
	 * @param reassignedFlightValidationVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerVO> findAllULDsInAssignedFlight(FlightValidationVO reassignedFlightValidationVO) {
		log.debug("MailTrackingDefaultsServicesEJB" + " : " + "findAllULDsInAssignedFlight" + " Entering");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(reassignedFlightValidationVO.getCompanyCode());
		operationalFlightVO.setFlightNumber(reassignedFlightValidationVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(reassignedFlightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(reassignedFlightValidationVO.getFlightCarrierId());
		operationalFlightVO.setPol(reassignedFlightValidationVO.getAirportCode());
		return AssignedFlight.findULDsInAssignedFlight(operationalFlightVO);
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @author A-2037 This method is used to find Preadvice for outbound mailand it gives the details of the ULDs and the receptacles based on CARDIT
	 */
	public PreAdviceVO findPreAdvice(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findPreAdvice" + " Entering");
		return Cardit.findPreAdvice(operationalFlightVO);
	}

	/**
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @author a-1936This method is used to find the Transfer Manifest for the Different Transactions
	 */
	public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo) {
		return mailTransfer.findTransferManifest(tranferManifestFilterVo);
	}
	/**
	 * findContainersInFlightForManifest
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 */
	public MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo) {
		log.debug(CLASS + " : " + "findContainersInFlightForManifest" + " Entering");
		MailManifestVO mailManifestVO = AssignedFlightSegment.findContainersInFlightForManifest(operationalFlightVo);
		try {
			AssignedFlight assignedFlight = AssignedFlight.find(constructAssignedFlightPK(operationalFlightVo));
			mailManifestVO.setFlightStatus(assignedFlight.getExportClosingFlag());
		} catch (FinderException exception) {
		}
		updateManifestVOWithSortedDSNVos(mailManifestVO);
		log.debug(CLASS + " : " + "findContainersInFlightForManifest" + " Exiting");
		return mailManifestVO;
	}

	/**
	 * method definition for sorting dsn's based on dest offc of exchange Added by ICRD-197379
	 * @param mailManifestVO
	 * @author a-7540
	 */
	private void updateManifestVOWithSortedDSNVos(MailManifestVO mailManifestVO) {
		if (mailManifestVO != null) {
			Collection<ContainerDetailsVO> containerDetails = mailManifestVO.getContainerDetails();
			for (ContainerDetailsVO containerDetailsVO : containerDetails) {
				List<DSNVO> dsnVOs = (List<DSNVO>) containerDetailsVO.getDsnVOs();
				if (dsnVOs != null && dsnVOs.size() > 0) {
                    Comparator<DSNVO> dsnvoComparator
                            = Comparator.comparing(DSNVO::getDestinationExchangeOffice);
                    dsnVOs.sort(dsnvoComparator);
				}
			}
		}
	}

	/**
	 * overriding compare method and passing objects by reference Added for ICRD-197379
	 * @author a-7540
	 * @return
	 */
//	class DestinationExchangeOfficeComparator implements Comparator {
//	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param isGHA
	 * @return
	 * @throws SystemException
	 * @author a-1883
	 */
	public String findStockHolderForMail(String companyCode, String airportCode, boolean isGHA) {
		log.debug(CLASS + " : " + "findStockHolderForMail" + " Entering");
		log.info("" + "isGHAUser :" + " " + isGHA);
		String stockHolder = null;
		Collection<String> parameterCodes = new ArrayList<String>();
		if (isGHA) {
			parameterCodes.add(MailConstantsVO.STOCK_HOLDER_PARAMETER);
			Map<String, String> paramMap = sharedAreaProxy.findAirportParametersByCode(companyCode, airportCode,
					parameterCodes);
			if (paramMap != null) {
				stockHolder = paramMap.get(MailConstantsVO.STOCK_HOLDER_PARAMETER);
			}
		} else {
			parameterCodes.add(MailConstantsVO.STATION_STOCK_HOLDER_PARAMETER);
			Map<String, String> paramMap = sharedAreaProxy.findAirportParametersByCode(companyCode, airportCode,
					parameterCodes);
			if (paramMap != null) {
				stockHolder = paramMap.get(MailConstantsVO.STATION_STOCK_HOLDER_PARAMETER);
			}
			log.info("" + "Stock Holder For Station :" + " " + stockHolder);
			if (stockHolder == null) {
				stockHolder = findSystemParameterValue(MailConstantsVO.DEFAULT_STOCK_HOLDER_PARAMETER);
			}
		}
		log.info("" + "Stock Holder :" + " " + stockHolder);
		return stockHolder;
	}

	/**
	 * @param aWBFilterVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws AttachAWBException
	 * @author a-1883
	 */
	public AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO) throws AttachAWBException {
		log.debug(CLASS + " : " + "findAWBDetails" + " Entering");

		AWBDetailVO awbDetailVO = null;
		Collection<ShipmentVO> shipments = operationsShipmentUtil.findShipments(constructShipmentFilterVO(aWBFilterVO));
		String mailSccCode = findSystemParameterValue(MailConstantsVO.ATTACH_AWB_SCC_CODE);
		boolean hasShipment = false;
		boolean isDiffScc = false;
		boolean isDifferentOrgDst = false;
		if (shipments != null && shipments.size() > 0) {
			hasShipment = true;
			for (ShipmentVO shipmentVO : shipments) {
				if (mailSccCode != null && !((shipmentVO.getScc()).contains(mailSccCode))) {
					isDiffScc = true;
					break;
				}
				if (!(aWBFilterVO.getOrigin().equals(shipmentVO.getOrigin()))
						|| (!aWBFilterVO.getDestination().equals(shipmentVO.getDestination()))) {
					isDifferentOrgDst = true;
					break;
				}

				List<AWBIndexModel> awbIndexModels = operationsShipmentUtil.validateShipmentDetails(
						shipmentVO.getShipmentPrefix().concat("-").concat(shipmentVO.getMasterDocumentNumber()));
				awbDetailVO = constructAWBDetailVO(awbIndexModels, shipmentVO);
			}
		}
		if (isDifferentOrgDst) {
			throw new AttachAWBException(AttachAWBException.AWB_ORG_DST_DIFF);
		}
		if (hasShipment && isDiffScc) {
			throw new AttachAWBException(AttachAWBException.AWB_ATTACHED_DIFF_SCC);
		}
		log.debug("" + "AWBDetailVO  : " + " " + awbDetailVO);
		return awbDetailVO;
	}

	/**
	 * TODO Purpose Mar 23, 2007, A-1739
	 * @param filterVO
	 * @return
	 */
	private ShipmentFilterVO constructShipmentFilterVO(AWBFilterVO filterVO) {
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode(filterVO.getCompanyCode());
		shipmentFilterVO.setOwnerId(filterVO.getDocumentOwnerIdentifier());
		shipmentFilterVO.setMasterDocumentNumber(filterVO.getMasterDocumentNumber());
		shipmentFilterVO.setDocumentNumber(filterVO.getMasterDocumentNumber());
		return shipmentFilterVO;
	}

	/**

	 * @return
	 */
	private AWBDetailVO constructAWBDetailVO(List<AWBIndexModel> awbIndexModels, ShipmentVO shipmentVO) {
		AWBIndexModel awbIndexModel = awbIndexModels.get(0);
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode(shipmentVO.getCompanyCode());
		awbDetailVO.setOwnerId(shipmentVO.getOwnerId());
		awbDetailVO.setMasterDocumentNumber(awbIndexModel.getMasterDocumentNumber());
		awbDetailVO.setDuplicateNumber(shipmentVO.getDuplicateNumber());
		awbDetailVO.setSequenceNumber(shipmentVO.getSequenceNumber());
		awbDetailVO.setDestination(awbIndexModel.getDestination());
		awbDetailVO.setOrigin(awbIndexModel.getOrigin());
		awbDetailVO.setShipmentDescription(awbIndexModel.getShipmentDescription());
		awbDetailVO.setStatedPieces(awbIndexModel.getStatedPieces().intValue());
		awbDetailVO.setStatedWeight(quantities.getQuantity(UnitConstants.MAIL_WGT,BigDecimal.valueOf(awbIndexModel.getStatedWeight())));
		return awbDetailVO;
	}

	/**
	 * @param operationalFlightVO
	 * @param mailManifestVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @author a-3251 SREEJITH P.C.
	 */
	public void closeFlightManifest(OperationalFlightVO operationalFlightVO, MailManifestVO mailManifestVO)
			throws ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeFlightManifest" + " Entering");
		Collection<String> sysParameters = new ArrayList<String>();
		sysParameters.add(MailConstantsVO.CONSIGNMENT_ROUTING_NEEDED_FOR_EXPORT_CLOSEFLIGHT);
		Map<String, String> sysParameterMap = null;
		try {
			sysParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(sysParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + sysParameterMap);
		if (sysParameterMap != null && OperationalFlightVO.FLAG_YES
				.equals(sysParameterMap.get(MailConstantsVO.CONSIGNMENT_ROUTING_NEEDED_FOR_EXPORT_CLOSEFLIGHT))) {
			Collection<ContainerDetailsVO> containerDetails = mailManifestVO.getContainerDetails();
			boolean canCloseFlight = true;
			if (containerDetails != null && containerDetails.size() > 0) {
				for (ContainerDetailsVO containerDtls : containerDetails) {
					if (containerDtls.getDsnVOs() != null && containerDtls.getDsnVOs().size() > 0) {
						for (DSNVO dSNVO : containerDtls.getDsnVOs()) {
							if (DSNVO.FLAG_NO.equals(dSNVO.getRoutingAvl())) {
								canCloseFlight = false;
								break;
							}
						}
					}
					if (!canCloseFlight) {
						break;
					}
				}
			}
			if (!canCloseFlight) {
				throw new CloseFlightException(CloseFlightException.ROUTING_UNAVAILABLE);
			}
		}
		closeFlight(operationalFlightVO);
		MailAlertMessageVO mailAlertMessageVO = new MailAlertMessageVO();
		mailAlertMessageVO.setCompanyCode(mailManifestVO.getCompanyCode());
		mailAlertMessageVO.setCondatails(mailManifestVO.getContainerDetails());
		mailAlertMessageVO.setDepartureDate(mailManifestVO.getDepDate());
		mailAlertMessageVO.setDeptport(mailManifestVO.getDepPort());
		mailAlertMessageVO
				.setFlightnum(mailManifestVO.getFlightCarrierCode() + SPACE + mailManifestVO.getFlightNumber());
		mailAlertMessageVO.setRoute(operationalFlightVO.getFlightRoute());
		mailAlertMessageVO.setAirlinecode(operationalFlightVO.getOwnAirlineCode());
		String st[] = operationalFlightVO.getFlightRoute().split("-");
		Collection<String> stations = new ArrayList<String>();
		for (int i = 0; i < st.length; i++) {
			stations.add(st[i]);
		}
		mailAlertMessageVO.setStations(stations);
		log.debug("" + "\n*******Stations to which the message is to be send are -------->\n" + " " + stations);
		log.debug(CLASS + " : " + "closeFlightManifest" + " Exiting");
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @author a-1936 This method is used to close the Flight
	 */
	public void closeFlight(OperationalFlightVO operationalFlightVO)
			throws ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeFlight" + " Entering");
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<OperationalFlightVO>();
		boolean isSendFWBEnabled = false;
		Collection<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.SEND_FWB_NEEDED);
		boolean canMonitorSLA = false;
		Collection<String> mailIds = null;
		systemParameters.add(MailConstantsVO.MAILTRACKING_MONITORSLA);
		ZonedDateTime manifestedDate = null;
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null
				&& OperationalFlightVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.SEND_FWB_NEEDED))) {
			isSendFWBEnabled = true;
		}
		if (systemParameterMap != null && OperationalFlightVO.FLAG_YES
				.equals(systemParameterMap.get(MailConstantsVO.MAILTRACKING_MONITORSLA))) {
			canMonitorSLA = true;
		}
		log.debug("" + "Can Monitor the SLA For the Closed Flights" + " " + canMonitorSLA);
		AssignedFlight assignedFlight = null;
		AssignedFlightVO assignedFlightVO;
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightVO.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_CLOSED);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		}
		catch (FinderException ex) {
			assignedFlight = new AssignedFlight(assignedFlightVO);
		}
		if (assignedFlight != null) {
			assignedFlight.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			performAssignedFlightAudit(assignedFlightVO,MailConstantsVO.EXPORT_FLIGHT_CLOSED,"MTK060","UPDATED");
			Collection<ContainerVO> bulkContainersInFlight = AssignedFlight
					.findContainersInAssignedFlight(operationalFlightVO);
			if (bulkContainersInFlight != null && bulkContainersInFlight.size() > 0) {
				for (ContainerVO bulkContainerVO : bulkContainersInFlight) {
					try {
						Container bulkContainer = findContainer(bulkContainerVO);
						if (bulkContainer != null) {
							bulkContainer.setTransitFlag(MailConstantsVO.FLAG_NO);
						}
					} catch (FinderException ex) {
						log.error("BULK CONTAINER NOT FOUND !!!!!!");
					}
				}
			}
			if (canMonitorSLA) {
				manifestedDate = localDateUtil.getLocalDate(operationalFlightVO.getPol(), true);
			}
			boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
			if (isUldIntegrationEnbled) {
				Collection<ContainerVO> containerVOs = AssignedFlight.findULDsInAssignedFlight(operationalFlightVO);
				log.debug("" + " ContainerVOs :" + " " + containerVOs);
				if (containerVOs != null && containerVOs.size() > 0) {
					FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
					ULDInFlightVO uldInFlightVO = null;
					Collection<ULDInFlightVO> uldInFlightVOs = new ArrayList<ULDInFlightVO>();
					flightDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
					for (ContainerVO containerVO : containerVOs) {
						uldInFlightVO = new ULDInFlightVO();
						uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
						uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
						uldInFlightVO.setPointOfUnLading(containerVO.getPou());
						uldInFlightVOs.add(uldInFlightVO);
					}
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
					flightDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
					flightDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getFlightDate()));
					flightDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					flightDetailsVO.setCurrentAirport(operationalFlightVO.getPol());
					flightDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
					flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
					flightDetailsVO.setAction(FlightDetailsVO.FINALISATION);
					flightDetailsVO.setSubSystem("MAIL");
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				}
			}
		}
		Collection<ContainerVO> containerVOs = AssignedFlight.findULDsInAssignedFlight(operationalFlightVO);
		Collection<String> mailbagIds = findMailBagsInClosedFlight(operationalFlightVO);
		Collection<ContainerVO> bulkContainers = AssignedFlight.findContainersInAssignedFlight(operationalFlightVO);
		FlightValidationVO flightValidationVO = ContextUtil.getInstance().getBean( MailController.class).validateOperationalFlight(operationalFlightVO,
				false);
		if (flightValidationVO != null && flightValidationVO.getSta() != null) {
			operationalFlightVO.setArrivaltime(localDateUtil.getLocalDate(flightValidationVO.getSta()));
		}
		doSecurityAndScreeningValidations(operationalFlightVO, containerVOs, bulkContainers);
		int finalContainerCount = 0;
		int mailbagCount = 0;
		int uldCount = 0;
		int bulkCount = 0;
		String departureAirport = operationalFlightVO.getAirportCode() != null ? operationalFlightVO.getAirportCode()
				: operationalFlightVO.getPol();
		if (!bulkContainers.isEmpty()) {
			Set<String> bulks = new HashSet<>();
			for (ContainerVO bulk : bulkContainers) {
				bulks.add(bulk.getContainerNumber());
			}
			bulkCount = bulks.size();
		}
		if (!containerVOs.isEmpty()) {
			uldCount = containerVOs.size();
		}
		finalContainerCount = uldCount + bulkCount;
		if (!mailbagIds.isEmpty()) {
			Set<String> mailbags = new HashSet<>();
			for (String mailbag : mailbagIds) {
				mailbags.add(mailbag);
			}
			mailbagCount = mailbags.size();
		}

		if (isSendFWBEnabled) {
			sendFWBForFlight(operationalFlightVO);
		}
		if (canMonitorSLA) {
			mailIds = findMailBagsInClosedFlight(operationalFlightVO);
			if (mailIds != null && mailIds.size() > 0) {
				log.debug("" + "The Mail IDS " + " " + mailIds.size());
				log.debug("" + "The Mail IDS " + " " + mailIds);
				monitorMailSLAActivity(createMonitorSLAVosForManifest(mailIds, operationalFlightVO, manifestedDate));
			}
		}
		if (flightValidationVO != null && flightValidationVO.getAtd() != null) {
			operationalFlightVOs.add(operationalFlightVO);
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagMLDForUpliftedMailbags(operationalFlightVOs);
			}
		}
	}
	private void performAssignedFlightAudit(AssignedFlightVO assignedFlightVO,String action, String trigger,String transaction)
	{

		AuditConfigurationBuilder auditConfigurationBuilder = new AuditConfigurationBuilder();
		auditUtils.performAudit(auditConfigurationBuilder
				.withBusinessObject(assignedFlightVO)
				.withTriggerPoint(trigger)
				.withActionCode(action)
				.withEventName("mailFlightUpdate")
				.withtransaction(transaction).build());
	}
	/**
	 * TODO Purpose Jan 31, 2007, A-1739
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void sendFWBForFlight(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "sendFWBForFlight" + " Entering");
		Collection<AWBDetailVO> awbDetails = AssignedFlight.findAWBDetails(operationalFlightVO);
		if (awbDetails != null && awbDetails.size() > 0) {
			//TODO: neo to correct code
			//operationsShipmentProxy.sendFWB(constructShipmentValidationVOs(awbDetails, operationalFlightVO));
		}
		log.debug(CLASS + " : " + "sendFWBForFlight" + " Exiting");
	}

	/**
	 * TODO Purpose Jan 31, 2007, A-1739
	 * @param awbDetails
	 * @return
	 */
	private Collection<ShipmentValidationVO> constructShipmentValidationVOs(Collection<AWBDetailVO> awbDetails,
																			OperationalFlightVO opFlightVO) {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<ShipmentValidationVO>();
		for (AWBDetailVO awbDetailVO : awbDetails) {
			ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
			shipmentValidationVO.setCompanyCode(opFlightVO.getCompanyCode());
			shipmentValidationVO.setOwnerCode(awbDetailVO.getOwnerCode());
			shipmentValidationVO.setOwnerId(awbDetailVO.getOwnerId());
			shipmentValidationVO.setDocumentNumber(awbDetailVO.getMasterDocumentNumber());
			shipmentValidationVO.setDuplicateNumber(awbDetailVO.getDuplicateNumber());
			shipmentValidationVO.setSequenceNumber(awbDetailVO.getSequenceNumber());
			shipmentValidationVOs.add(shipmentValidationVO);
		}
		return shipmentValidationVOs;
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find the MailBags in the ClosedFlight.. Required For Monitoring the Service Level Activity of the Mail Bags..
	 */
	private Collection<String> findMailBagsInClosedFlight(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findMailBagsInClosedFlight" + " Entering");
		return Mailbag.findMailBagsInClosedFlight(operationalFlightVO);
	}

	/**
	 * 120507
	 * @param mailBagsForMonitorSLA
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to construct the MonitorSLAVos Fromthe MailBagVos
	 */
	private Collection<MonitorMailSLAVO> createMonitorSLAVosForManifest(Collection<String> mailBagsForMonitorSLA,
																		OperationalFlightVO operationalFlightVO, ZonedDateTime manifestedDate) {
		log.debug(CLASS + " : " + "createMonitorSLAVosForManifest" + " Entering");
		MonitorMailSLAVO monitorSLAVo = null;
		Collection<MonitorMailSLAVO> monitorSLAVos = new ArrayList<MonitorMailSLAVO>();
		for (String mailBagForMonitorSLA : mailBagsForMonitorSLA) {
			monitorSLAVo = new MonitorMailSLAVO();
			monitorSLAVo.setCompanyCode(operationalFlightVO.getCompanyCode());
			monitorSLAVo.setActivity(MonitorMailSLAVO.MAILSTATUS_MANIFESTED);
			monitorSLAVo.setMailBagNumber(mailBagForMonitorSLA);
			monitorSLAVo.setOperationFlag(MonitorMailSLAVO.OPERATION_FLAG_UPDATE);
			monitorSLAVo.setScanTime(manifestedDate);
			monitorSLAVos.add(monitorSLAVo);
		}
		log.debug(CLASS + " : " + "createMonitorSLAVosForManifest" + " Entering");
		return monitorSLAVos;
	}

	/**
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find the containers,DSNS,MailBagsthat Can be Offloaded for a ParticularFlight..
	 */
	public OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO) {
		log.debug(CLASS + " : " + "findOffloadDetails" + " Entering");
		return MailAcceptance.findOffloadDetails(offloadFilterVO);
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @author a-1936 This method is used to reopen the Flight
	 */
	public void reopenFlight(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "reopenFlight" + " Entering");
		AssignedFlight assignedFlight = null;
		AssignedFlightVO assignedFlightVO;
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightVO.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_OPEN);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			assignedFlight = new AssignedFlight(assignedFlightVO);
		}
		if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight.getExportClosingFlag())) {
			assignedFlight.setExportClosingFlag(MailConstantsVO.FLIGHT_STATUS_OPEN);
			performAssignedFlightAudit(assignedFlightVO,MailConstantsVO.EXPORT_FLIGHT_REOPEN,"MTK060","UPDATED");

			Collection<ContainerVO> containerVOs = AssignedFlight.findULDsInAssignedFlight(operationalFlightVO);
			Collection<String> mailbagIds = findMailBagsInClosedFlight(operationalFlightVO);
			Collection<ContainerVO> bulkContainers = AssignedFlight.findContainersInAssignedFlight(operationalFlightVO);
			int finalContainerCount = 0;
			int mailbagCount = 0;
			int uldCount = 0;
			int bulkCount = 0;
			if (!bulkContainers.isEmpty()) {
				Set<String> bulks = new HashSet<>();
				for (ContainerVO bulk : bulkContainers) {
					bulks.add(bulk.getContainerNumber());
				}
				bulkCount = bulks.size();
			}
			if (!containerVOs.isEmpty()) {
				uldCount = containerVOs.size();
			}
			finalContainerCount = uldCount + bulkCount;
			if (!mailbagIds.isEmpty()) {
				Set<String> mailbags = new HashSet<>();
				for (String mailbag : mailbagIds) {
					mailbags.add(mailbag);
				}
				mailbagCount = mailbags.size();
			}
		}
	}

	/**
	 * @param operationalFlightVO
	 * @param mailAcceptanceVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @author a-3251 SREEJITH P.C.
	 */
	public void closeFlightAcceptance(OperationalFlightVO operationalFlightVO, MailAcceptanceVO mailAcceptanceVO)
			throws ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeFlightAcceptance" + " Entering");
		Collection<String> sysParameters = new ArrayList<String>();
		sysParameters.add(MailConstantsVO.CONSIGNMENT_ROUTING_NEEDED_FOR_EXPORT_CLOSEFLIGHT);
		Map<String, String> sysParameterMap = null;
		try {
			sysParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(sysParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + sysParameterMap);
		if (sysParameterMap != null && OperationalFlightVO.FLAG_YES
				.equals(sysParameterMap.get(MailConstantsVO.CONSIGNMENT_ROUTING_NEEDED_FOR_EXPORT_CLOSEFLIGHT))) {
			Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
			boolean canCloseFlight = true;
			if (containerDetails != null && containerDetails.size() > 0) {
				for (ContainerDetailsVO containerDtls : containerDetails) {
					if (containerDtls.getDsnVOs() != null && containerDtls.getDsnVOs().size() > 0) {
						for (DSNVO dSNVO : containerDtls.getDsnVOs()) {
							if (DSNVO.FLAG_NO.equals(dSNVO.getRoutingAvl())) {
								canCloseFlight = false;
								break;
							}
						}
					}
					if (!canCloseFlight) {
						break;
					}
				}
			}
			if (!canCloseFlight) {
				throw new CloseFlightException(CloseFlightException.ROUTING_UNAVAILABLE);
			}
		}
		closeFlight(operationalFlightVO);
		MailAlertMessageVO mailAlertMessageVO = new MailAlertMessageVO();
		mailAlertMessageVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		mailAlertMessageVO.setCondatails(mailAcceptanceVO.getContainerDetails());
		mailAlertMessageVO.setDepartureDate(mailAcceptanceVO.getFlightDate());
		mailAlertMessageVO.setDeptport(mailAcceptanceVO.getPol());
		mailAlertMessageVO
				.setFlightnum(mailAcceptanceVO.getFlightCarrierCode() + SPACE + mailAcceptanceVO.getFlightNumber());
		mailAlertMessageVO.setRoute(operationalFlightVO.getFlightRoute());
		mailAlertMessageVO.setAirlinecode(operationalFlightVO.getOwnAirlineCode());
		String st[] = operationalFlightVO.getFlightRoute().split("-");
		Collection<String> stations = new ArrayList<String>();
		for (int i = 0; i < st.length; i++) {
			stations.add(st[i]);
		}
		log.debug("" + "\n*******Stations to which the message is to be send are -------->\n" + " " + stations);
		mailAlertMessageVO.setStations(stations);
		log.debug("" + "\n*******Mail Alert Message VO -------->\n" + " " + mailAlertMessageVO);
		log.debug(CLASS + " : " + "findContainersInFlightForManifest" + " Exiting");
	}

	/**
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find the MailBags
	 */
	public Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findMailbags" + " Entering");
		return Mailbag.findMailbags(mailbagEnquiryFilterVO, pageNumber);
	}

	/**
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @author a-2553
	 */
	public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) throws BusinessException {
		log.debug(CLASS + " : " + "findCarditMails" + " Entering");
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		Page<MailbagVO> carditMails = Cardit.findCarditMails(carditEnquiryFilterVO, pageNumber);
		HashMap<String, MailbagVO> mailBagMap = new HashMap<String, MailbagVO>();
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		String key = null;
		List<Pair> stationParameters = null;
		String stationCode = logonAttributes.getStationCode();
		stationParameters = neoMastersServiceUtils.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode,
				STNPAR_DEFUNIT_VOL);
		String stationVolumeUnit = (String) stationParameters.get(0).getValue();
		if (carditMails != null && carditMails.size() > 0) {
			for (MailbagVO mailbagVO : carditMails) {
				Collection<MailbagHistoryVO> mailbagResditEvents = new ArrayList<MailbagHistoryVO>();
				try {
					mailbagResditEvents.addAll(constructDAO().findMailbagResditEvents(mailbagVO.getCompanyCode(),
							mailbagVO.getMailbagId()));
				} catch (PersistenceException persistenceException) {
					throw new SystemException(persistenceException.getErrorCode());
				}
				mailbagVO.setMailbagHistories(mailbagResditEvents);
				UnitConversionNewVO unitConversionVO = null;
				String fromUnit = stationVolumeUnit;
				if (mailbagVO.getVolUnit() != null) {
					fromUnit = mailbagVO.getVolUnit();
				}
				UnitFormatter unitFormatter = ContextUtil.getInstance().getBean(ICargoDomainTypeSupport.class).unitFormatter();
				try {
					unitConversionVO = unitFormatter.getUnitConversionForToUnit(UnitConstants.VOLUME, fromUnit,
							stationVolumeUnit, mailbagVO.getVol());
				} catch (UnitException e) {
					e.getMessage();
				}
				double convertedValue = Math.round(unitConversionVO.getToValue() * 100.0) / 100.0;
				if (MailConstantsVO.MINIMUM_VOLUME > convertedValue) {
					convertedValue = MailConstantsVO.MINIMUM_VOLUME;
				}
				if (mailbagVO.getVolUnit() != null && mailbagVO.getVol() != 0) {
					mailbagVO.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(convertedValue),
							BigDecimal.valueOf(0.0), stationVolumeUnit));
				}
				key = String.valueOf(mailbagVO.getMailSequenceNumber());
				ZonedDateTime existingDepartureDate = null;
				ZonedDateTime DepartureDate = null;
				if (mailBagMap.containsKey(key)) {
					MailbagVO existingMailbagVO = mailBagMap.get(key);
					FlightFilterVO existingFlightFilterVO = new FlightFilterVO();
					FlightFilterVO flightFilterVO = new FlightFilterVO();
					Collection<FlightValidationVO> existingFlightVOs = null;
					Collection<FlightValidationVO> flightVOs = null;
					existingFlightFilterVO.setCompanyCode(existingMailbagVO.getCompanyCode());
					existingFlightFilterVO.setFlightNumber(existingMailbagVO.getFlightNumber());
					existingFlightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(existingMailbagVO.getFlightDate()));
					existingFlightFilterVO.setFlightSequenceNumber(existingMailbagVO.getFlightSequenceNumber());
					existingFlightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
					existingFlightVOs = validateFlight(existingFlightFilterVO);
					if (existingFlightVOs != null && !existingFlightVOs.isEmpty()) {
						existingDepartureDate = LocalDateMapper.toZonedDateTime(existingFlightVOs.iterator().next().getStd());
					}
					flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
					flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
					flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
					flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
					flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
					flightVOs = validateFlight(flightFilterVO);
					if (flightVOs != null && !flightVOs.isEmpty()) {
						DepartureDate = LocalDateMapper.toZonedDateTime(flightVOs.iterator().next().getStd());
					}
					if (existingDepartureDate != null && DepartureDate != null
							&& existingMailbagVO.getCarrierCode() != null
							&& existingMailbagVO.getCarrierCode().equals(mailbagVO.getCarrierCode())) {
						if (existingMailbagVO.getFlightDate() != null) {
							if (existingDepartureDate.isAfter(DepartureDate)) {
								mailBagMap.put(key, mailbagVO);
							}
						}
					} else if (mailbagVO.getCarrierCode() != null
							&& mailbagVO.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {
						mailBagMap.put(key, mailbagVO);
					} else {
						if (existingMailbagVO.getFlightDate() != null && existingDepartureDate != null
								&& DepartureDate != null && existingDepartureDate.isAfter(DepartureDate)) {
							mailBagMap.put(key, mailbagVO);
						}
					}
				} else {
					mailBagMap.put(key, mailbagVO);
				}
			}
		}
		if (carditMails != null) {
			carditMails.removeAll(carditMails);
			carditMails.addAll(mailBagMap.values());
		}
		return carditMails;
	}
	/**
	 * @param operationalFlightVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 * @author a-1936 This method returns all the ULDs assigned to a particularflight from the given airport are returned
	 */
	public Collection<ContainerVO> findFlightAssignedContainers(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findFlightAssignedContainers" + " Entering");
		return Container.findFlightAssignedContainers(operationalFlightVO);
	}

	/**
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @author A-2037 This method is used to find the Damaged Mailbag Details
	 */
	public Collection<DamagedMailbagVO> findMailbagDamages(String companyCode, String mailbagId) {
		log.debug(CLASS + " : " + "findMailbagDamages" + " Entering");
		Collection<DamagedMailbagVO> damagedMailbagVOs = Mailbag.findMailbagDamages(companyCode, mailbagId);
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<>();
		DocumentRepositoryFilterVO documentRepositoryFilterVO = new DocumentRepositoryFilterVO();
		documentRepositoryFilterVO.setCompanyCode(companyCode);
		documentRepositoryFilterVO.setDocumentType("MAL");
		documentRepositoryFilterVO.setPurpose("DMG");
		documentRepositoryFilterVO.setDocumentValue(mailbagId);
		try {
			documentRepositoryMasterVOs = documentRepositoryProxy
					.getDocumentsfromRepository(documentRepositoryFilterVO);
		} catch (BusinessException proxyException) {
		}
		for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
			damagedMailbagVO.setMailbagId(mailbagId);
			if (!documentRepositoryMasterVOs.isEmpty()) {
				for (DocumentRepositoryMasterVO documentRepositoryMasterVO : documentRepositoryMasterVOs) {
					List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs = documentRepositoryMasterVO
							.getAttachments();
					for (DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO : documentRepositoryAttachmentVOs) {
						populatedamagemailbagvowithfiledata(damagedMailbagVO, documentRepositoryAttachmentVO);
					}
				}
			}
		}
		return damagedMailbagVOs;
	}

	private DamagedMailbagVO populatedamagemailbagvowithfiledata(DamagedMailbagVO damagedMailbagVO,
																 DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO) {
		if (damagedMailbagVO.getDamageCode().equals(documentRepositoryAttachmentVO.getTransactionDataRef2())) {
			if (damagedMailbagVO.getFileName() != null) {
				damagedMailbagVO.setFileName(
						damagedMailbagVO.getFileName() + "-DMG-" + documentRepositoryAttachmentVO.getFileName());
			} else {
				damagedMailbagVO.setFileName(documentRepositoryAttachmentVO.getFileName());
			}
		}
		return damagedMailbagVO;
	}

	/**
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find the containerDetails
	 */
	public Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findContainers" + " Entering");
		LoginProfile logonAttributes = getLogonAttributes();
		Collection<PartnerCarrierVO> partnerCarrierVOs = null;
		ArrayList<String> partnerCarriers = new ArrayList<String>();
		String companyCode = logonAttributes.getCompanyCode();
		String ownCarrierCode = logonAttributes.getOwnAirlineCode();
		String airportCode = logonAttributes.getAirportCode();
		partnerCarrierVOs = findAllPartnerCarriers(companyCode, ownCarrierCode,
					airportCode);
		log.debug("" + " partnerCarriers " + " " + partnerCarrierVOs);
		if (partnerCarrierVOs != null && partnerCarrierVOs.size() > 0) {
			for (PartnerCarrierVO partner : partnerCarrierVOs) {
				String partnerCarrier = partner.getPartnerCarrierCode();
				partnerCarriers.add(partnerCarrier);
			}
			partnerCarriers.add(ownCarrierCode);
			searchContainerFilterVO.setPartnerCarriers(partnerCarriers);
		}
		return Container.findContainers(searchContainerFilterVO, pageNumber);
	}

	/**
	 * @param destinationFilterVO
	 * @return Collection<ContainerVO>
	 * @throws SystemException
	 * @author a-1936 This method returns all the ULDs that are assigned todestination from the given airport are returned
	 */
	public Collection<ContainerVO> findDestinationAssignedContainers(DestinationFilterVO destinationFilterVO) {
		log.debug(CLASS + " : " + "findDestinationAssignedContainers" + " Entering");
		return Container.findDestinationAssignedContainers(destinationFilterVO);
	}

	/**
	 * deletes the assigned Containers A-1739
	 * @param containerVOs
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 */
	public void deleteContainers(Collection<ContainerVO> containerVOs) throws ContainerAssignmentException {
		Collection<String> flightCollection = null;
		OperationalFlightVO operationalFlightVO = null;
		String flightPK = "";
		boolean canDeleted = false;
		if (containerVOs != null && containerVOs.size() > 0) {
			log.debug(CLASS + " : " + "deleteContainers" + " Entering");
			for (ContainerVO containerVO : containerVOs) {
				if (containerVO.isFlightClosureCheckNeeded()) {
					if (containerVO.getLegSerialNumber() > 0 && containerVO.getFlightSequenceNumber() > 0) {
						log.info("THE FLIGHT PRESENT");
						log.info("Calling <<-------getFlightOfContainer ---------->>>>");
						operationalFlightVO = constructFlightVOForContainer(containerVO);
						flightPK = new StringBuilder(operationalFlightVO.getCompanyCode())
								.append(operationalFlightVO.getCarrierId())
								.append(operationalFlightVO.getFlightNumber())
								.append(operationalFlightVO.getFlightSequenceNumber())
								.append(operationalFlightVO.getLegSerialNumber()).append(operationalFlightVO.getPol())
								.toString();
						log.debug("" + "THE FLIGHTPK IS >>>>>>>" + " " + flightPK);
						if (flightCollection != null && flightCollection.size() > 0) {
							canDeleted = flightCollection.contains(flightPK);
							log.info("" + "THE FLAG IS >>>>>>>" + " " + canDeleted);
						}
						if (!canDeleted) {
							if (isFlightClosedForOperations(operationalFlightVO)) {
								String flightDetails = new StringBuilder(containerVO.getCarrierCode()).append(SPACE)
										.append(containerVO.getFlightNumber()).append(" on ")
										.append(containerVO.getFlightDate().toString().substring(0, 11)).toString();
								log.debug("" + "THE flightdetails is " + " " + flightDetails);
								throw new ContainerAssignmentException(
										ContainerAssignmentException.FLIGHT_STATUS_CLOSED,
										new Object[] { containerVO.getContainerNumber(), flightDetails });
							}
							if (flightCollection == null) {
								flightCollection = new ArrayList<String>();
							}
							flightCollection.add(flightPK);
							deleteContainer(containerVO);
						} else {
							deleteContainer(containerVO);
						}
					} else {
						deleteContainer(containerVO);
					}
				} else {
					deleteContainer(containerVO);
				}
			}
		}
	}

	private OperationalFlightVO constructFlightVOForContainer(ContainerVO containerToReassign) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(containerToReassign.getCompanyCode());
		operationalFlightVO.setCarrierCode(containerToReassign.getCarrierCode());
		operationalFlightVO.setCarrierId(containerToReassign.getCarrierId());
		operationalFlightVO.setFlightNumber(containerToReassign.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(containerToReassign.getFlightSequenceNumber());
		operationalFlightVO.setLegSerialNumber(containerToReassign.getLegSerialNumber());
		operationalFlightVO.setPol(containerToReassign.getAssignedPort());
		operationalFlightVO.setFlightDate(containerToReassign.getFlightDate());
		return operationalFlightVO;
	}

	public MailArrivalVO findArrivalDetails(MailArrivalFilterVO mailArrivalFilterVO) {
		MailArrivalVO mailArrivalVO = AssignedFlightSegment.findArrivalDetails(mailArrivalFilterVO);
		try {
			OperationalFlightVO opFlightVO = constructOpFlightVOFromFilter(mailArrivalFilterVO);
			AssignedFlight inboundFlight = AssignedFlight.find(constructInboundFlightPK(opFlightVO));
			mailArrivalVO.setFlightStatus(inboundFlight.getImportClosingFlag());
		} catch (FinderException exception) {
		}
		return mailArrivalVO;
	}

	/**
	 * TODO Purpose Jan 22, 2007, A-1739
	 * @param mailArrivalFilterVO
	 * @return
	 */
	private OperationalFlightVO constructOpFlightVOFromFilter(MailArrivalFilterVO mailArrivalFilterVO) {
		OperationalFlightVO flightVO = new OperationalFlightVO();
		flightVO.setCompanyCode(mailArrivalFilterVO.getCompanyCode());
		flightVO.setCarrierId(mailArrivalFilterVO.getCarrierId());
		flightVO.setFlightNumber(mailArrivalFilterVO.getFlightNumber());
		flightVO.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
		flightVO.setLegSerialNumber(mailArrivalFilterVO.getLegSerialNumber());
		flightVO.setPou(mailArrivalFilterVO.getPou());
		return flightVO;
	}

	/**
	 * @param flightVO
	 * @return
	 * @author A-5991
	 */
	private AssignedFlightPK constructInboundFlightPK(OperationalFlightVO flightVO) {
		AssignedFlightPK inboundFlightPK = new AssignedFlightPK();
		inboundFlightPK.setCompanyCode(flightVO.getCompanyCode());
		inboundFlightPK.setCarrierId(flightVO.getCarrierId());
		inboundFlightPK.setFlightNumber(flightVO.getFlightNumber());
		inboundFlightPK.setFlightSequenceNumber(flightVO.getFlightSequenceNumber());
		inboundFlightPK.setLegSerialNumber(flightVO.getLegSerialNumber());
		inboundFlightPK.setAirportCode(flightVO.getPou());
		return inboundFlightPK;
	}

	public OperationalFlightVO validateInboundFlight(OperationalFlightVO flightVO) {
		log.debug(CLASS + " : " + "validateInboundFlight" + " Entering");
		AssignedFlightPK flightPK = constructInboundFlightPK(flightVO);
		try {
			AssignedFlight.find(flightPK);
			return flightVO;
		} catch (FinderException ex) {
			log.debug("no inboundflight");
		}
		log.debug(CLASS + " : " + "validateInboundFlight" + " Exiting");
		return null;
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws CloseFlightException
	 * @author a-1936 This method is used to close the Ibound Flight
	 */
	public void closeInboundFlight(OperationalFlightVO operationalFlightVO) throws ULDDefaultsProxyException {
		log.debug(CLASS + " : " + "closeInboundFlight" + " Entering");
		LoginProfile logon = contextUtil.callerLoginProfile();
		if (Objects.isNull(operationalFlightVO.getCarrierCode())) {
			AirlineValidationVO airlineValidationVO = findAirlineValidationVO(logon.getCompanyCode(),
					operationalFlightVO.getCarrierId());
			operationalFlightVO.setCarrierCode(Objects.nonNull(airlineValidationVO) ? airlineValidationVO.getAlphaCode()
					: operationalFlightVO.getCarrierCode());
		}
		AssignedFlight inboundFlight = null;
		AssignedFlightVO inboundFlightVO = null;
		AssignedFlightPK inboundFlightPk = new AssignedFlightPK();
		inboundFlightPk.setAirportCode(operationalFlightVO.getPou());
		inboundFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		inboundFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		inboundFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		inboundFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		inboundFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPk);
		} catch (FinderException ex) {
			log.info(FINDEREXCEPTIO_STRING);
			log.info("DATA INCONSISTENT");
			inboundFlightVO = new AssignedFlightVO();
			inboundFlightVO.setAirportCode(operationalFlightVO.getPou());
			inboundFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
			inboundFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			inboundFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			inboundFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
			inboundFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			inboundFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			inboundFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			inboundFlightVO.setLastUpdateTime(localDateUtil.getLocalDate(logon.getAirportCode(), true));
			inboundFlightVO.setLastUpdateUser(logon.getUserId());
			inboundFlightVO.setFlightStatus("N");
			inboundFlight = new AssignedFlight(inboundFlightVO);
		}
		if (inboundFlight != null) {
			inboundFlight.setImportClosingFlag(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			inboundFlight.setCarrierCode(operationalFlightVO.getCarrierCode());
			Collection<ContainerVO> containerVOs = AssignedFlight.findULDsInInboundFlight(operationalFlightVO);
			if (containerVOs != null && containerVOs.size() > 0) {
				int segNo = 0;
				for (ContainerVO container : containerVOs) {
					ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
					ContainerVO containerVO = new ContainerVO();
					uLDForSegmentPK.setCompanyCode(operationalFlightVO.getCompanyCode());
					uLDForSegmentPK.setCarrierId(operationalFlightVO.getCarrierId());
					uLDForSegmentPK.setFlightNumber(operationalFlightVO.getFlightNumber());
					uLDForSegmentPK.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					uLDForSegmentPK.setUldNumber(container.getContainerNumber());
					try {
						containerVO = mailOperationsMapper.copyContainerVO(container);
						containerVO.setCompanyCode(operationalFlightVO.getCompanyCode());
						containerVO.setCarrierId(operationalFlightVO.getCarrierId());
						containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
						containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
						segNo = findContainerSegment(containerVO);
					} catch (InvalidFlightSegmentException e) {
						e.getMessage();
					}
					ContainerPK containerPK = new ContainerPK();
					containerPK.setCompanyCode(containerVO.getCompanyCode());
					containerPK.setCarrierId(containerVO.getCarrierId());
					containerPK.setFlightNumber(containerVO.getFlightNumber());
					containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
					containerPK.setAssignmentPort(containerVO.getAssignedPort());
					containerPK.setContainerNumber(containerVO.getContainerNumber());
					Container containerToUpdate = null;
					try {
						containerToUpdate = Container.find(containerPK);
						if (containerToUpdate != null) {
							if (!containerToUpdate.getRetainFlag().equalsIgnoreCase("Y")) {
								containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
							} else {
								containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_YES);
							}
							containerToUpdate.setArrivedStatus(MailConstantsVO.FLAG_YES);
						}
					} catch (FinderException finderException) {
						log.debug("FinderException ");
					}
					uLDForSegmentPK.setSegmentSerialNumber(segNo);
					try {
						ULDForSegment uLDForSegment = null;
						uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
						if (containerToUpdate != null && !containerToUpdate.getRetainFlag().equalsIgnoreCase("Y")) {
							uLDForSegment.setReleasedFlag(MailConstantsVO.FLAG_YES);
						} else {
							uLDForSegment.setReleasedFlag(MailConstantsVO.FLAG_NO);
						}
					} catch (FinderException e) {
						e.getErrorCode();
					}
				}
			}
			//TODO: Audit to be implemented in Neo
//			AssignedFlightAuditVO assignedFlightAuditVO = new AssignedFlightAuditVO(AssignedFlightVO.MODULE,
//					AssignedFlightVO.SUBMODULE, AssignedFlightVO.ENTITY);
//			assignedFlightAuditVO = (AssignedFlightAuditVO) AuditUtils.populateAuditDetails(assignedFlightAuditVO,
//					inboundFlight);
			Collection<ContainerDetailsVO> containerDetailsVO = findArrivalDetailsForReleasingMails(
					operationalFlightVO);
			int containerCount = 0;
			int mailbagCount = 0;
			HashSet<String> containers = new HashSet<>();
			if (containerDetailsVO != null) {
				for (ContainerDetailsVO containerDetailsVo : containerDetailsVO) {
					for (MailbagVO mailbags : containerDetailsVo.getMailDetails()) {
						if (MailConstantsVO.FLAG_YES.equals(mailbags.getArrivedFlag())
								&& mailbags.getMailbagId() != null) {
							containers.add(containerDetailsVo.getContainerNumber());
							mailbagCount++;
						}
					}
				}
			}
			if (!containers.isEmpty()) {
				containerCount = containers.size();
			}
			//TODO: Neo to implement Audit
//			StringBuilder additInfo = new StringBuilder();
//			additInfo.append("Arrival Airport: " + inboundFlight.getAirportCode());
//			additInfo.append(", No of Containers : " + containerCount + ", No of Mailbags : " + mailbagCount);
//			assignedFlightAuditVO.setAdditionalInformation(additInfo.toString());
//			String triggeringPoint = ContextUtils.getRequestContext().getParameter(MailConstantsVO.REQ_TRIGGERPOINT);
//			assignedFlightAuditVO.setTriggerPoint(triggeringPoint);
//			performAssignedFlightAudit(assignedFlightAuditVO, inboundFlight, MailConstantsVO.IMPORT_FLIGHT_CLOSED);
			boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
			if (isUldIntegrationEnbled) {
				log.debug("" + " ContainerVOs :" + " " + containerVOs);
				if (containerVOs != null && containerVOs.size() > 0) {
					FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
					ULDInFlightVO uldInFlightVO = null;
					Collection<ULDInFlightVO> uldInFlightVOs = new ArrayList<ULDInFlightVO>();
					flightDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
					for (ContainerVO containerVO : containerVOs) {
						uldInFlightVO = new ULDInFlightVO();
						uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
						uldInFlightVO.setPointOfLading(containerVO.getAssignedPort());
						uldInFlightVO.setPointOfUnLading(containerVO.getPou());
						uldInFlightVO.setRemark(MailConstantsVO.FLT_CLOSED);
						uldInFlightVOs.add(uldInFlightVO);
					}
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
					flightDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
					flightDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getFlightDate()));
					flightDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					flightDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
					flightDetailsVO.setDirection(MailConstantsVO.IMPORT);
					flightDetailsVO.setAction(FlightDetailsVO.CLOSURE);
					flightDetailsVO.setSubSystem("MAIL");
					flightDetailsVO.setCurrentAirport(operationalFlightVO.getPou());
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				}
			}
		}
		log.debug(CLASS + " : " + "closeInboundFlight" + " Exiting");
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @author a-1883
	 */
	public Collection<MailDiscrepancyVO> findMailDiscrepancies(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findMailDiscrepancies" + " Entering");
		return AssignedFlight.findMailDiscrepancies(operationalFlightVO);
	}

	/**
	 * @param conatinerstoAcquit
	 * @throws SystemException
	 */
	public void autoAcquitContainers(Collection<ContainerDetailsVO> conatinerstoAcquit) {
		log.debug(CLASS + " : " + "autoAcquitContainers" + " Entering");
		if (CollectionUtils.isNotEmpty(conatinerstoAcquit)) {
			for (ContainerDetailsVO containerDetailsVO : conatinerstoAcquit) {
				if (!"B".equals(containerDetailsVO.getContainerType())) {
					ContainerPK containerPK = new ContainerPK();
					containerPK.setCompanyCode(containerDetailsVO.getCompanyCode());
					containerPK.setCarrierId(containerDetailsVO.getCarrierId());
					containerPK.setFlightNumber(containerDetailsVO.getFlightNumber());
					containerPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
					containerPK.setAssignmentPort(containerDetailsVO.getPol());
					containerPK.setContainerNumber(containerDetailsVO.getContainerNumber());
					containerPK.setLegSerialNumber(
							Container.findFlightLegSerialNumber(constructContainerVO(containerDetailsVO)));
					Container containerToUpdate = null;
					try {
						containerToUpdate = Container.find(containerPK);
					} catch (FinderException finderException) {
						log.debug("FinderException ");
					}
					if ((containerToUpdate != null && containerToUpdate.getRetainFlag().equals(MailConstantsVO.FLAG_NO))
							|| containerDetailsVO.isAquitULDFlag()) {
						containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
						ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
						uldForSegmentPK.setCompanyCode(containerDetailsVO.getCompanyCode());
						uldForSegmentPK.setCarrierId(containerDetailsVO.getCarrierId());
						uldForSegmentPK.setFlightNumber(containerDetailsVO.getFlightNumber());
						uldForSegmentPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
						uldForSegmentPK.setUldNumber(containerDetailsVO.getContainerNumber());
						uldForSegmentPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
						containerDetailsVO.setCarrierCode(containerToUpdate.getCarrierCode());
						ULDForSegment uldForSegment = null;
						try {
							uldForSegment = ULDForSegment.find(uldForSegmentPK);
							if (uldForSegment != null
									&& (!MailConstantsVO.FLAG_YES.equals(uldForSegment.getReleasedFlag()))) {
								uldForSegment.setReleasedFlag(MailConstantsVO.FLAG_YES);
							}
						} catch (FinderException finderException) {
							log.debug("FinderException ");
						}
					}
				}
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagContainerAuditForAcquital(containerDetailsVO);
			}
			boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
			if (isUldIntegrationEnbled) {
				FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
				ULDInFlightVO uldInFlightVO = null;
				Collection<ULDInFlightVO> uldInFlightVOs = new ArrayList<ULDInFlightVO>();
				ContainerDetailsVO containerDetailsVO = (ContainerDetailsVO) ((ArrayList<ContainerDetailsVO>) conatinerstoAcquit)
						.get(0);
				flightDetailsVO.setCompanyCode(containerDetailsVO.getCompanyCode());
				flightDetailsVO.setFlightCarrierIdentifier(containerDetailsVO.getCarrierId());
				flightDetailsVO.setCarrierCode(containerDetailsVO.getCarrierCode());
				flightDetailsVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerDetailsVO.getFlightDate()));
				flightDetailsVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				flightDetailsVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
				flightDetailsVO.setDirection(MailConstantsVO.IMPORT);
				for (ContainerDetailsVO containerVO : conatinerstoAcquit) {
					uldInFlightVO = new ULDInFlightVO();
					uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
					uldInFlightVO.setPointOfLading(containerVO.getPol());
					uldInFlightVO.setPointOfUnLading(containerVO.getPou());
					uldInFlightVO.setRemark(MailConstantsVO.FLT_CLOSED);
					uldInFlightVOs.add(uldInFlightVO);
				}
				flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
				flightDetailsVO.setAction(FlightDetailsVO.CLOSURE);
				try {
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				} catch (ULDDefaultsProxyException ex) {
					log.debug("ULDDefaultsProxyException ");
				}
			}
		}
	}

	public void saveChangeFlightDetails(Collection<MailArrivalVO> mailArrivalVOs) throws MailOperationsBusinessException {
		log.debug(CLASS + " : " + "saveChangeFlightDetails" + " Entering");
		if (CollectionUtils.isNotEmpty(mailArrivalVOs)) {
			for (MailArrivalVO mailArrivalVO : mailArrivalVOs) {
				mailArrivalVO.setFlightChange(true);
				if (MailConstantsVO.FLAG_NO.equals(mailArrivalVO.getChangeFlightFlag())) {
					undoArriveContainer(mailArrivalVO);
				} else {
					saveArrivalDetails(mailArrivalVO);
				}
			}
		}
		log.debug(CLASS + " : " + "saveChangeFlightDetails" + " Exiting");
	}

	/**
	 * This method manually sends the currently unsent event Feb 13, 2007, a-1739
	 * @param carditEnquiryVO
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 */
	public void sendResdit(CarditEnquiryVO carditEnquiryVO) throws ContainerAssignmentException {
		log.debug(CLASS + " : " + "sendResdit" + " Entering");
		String searchMode = carditEnquiryVO.getSearchMode();
		Collection<MailbagVO> mailbagVOs = carditEnquiryVO.getMailbagVos();
		Collection<MailbagVO> mailbagVOsTmp = new ArrayList<MailbagVO>();
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
			ConsignmentDocumentVO consignmentDocumentVO = null;
			HashMap<String, String> consignmentDocuments = new HashMap<String, String>();
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					if (mailbagVO.getConsignmentNumber() != null && !"".equals(mailbagVO.getConsignmentNumber())) {
						if (consignmentDocuments.get(mailbagVO.getConsignmentNumber()) == null) {
							consignmentDocuments.put(mailbagVO.getConsignmentNumber(),
									mailbagVO.getConsignmentNumber());
						}
					}
				}
			}
			if (consignmentDocuments != null && consignmentDocuments.size() > 0) {
				for (Map.Entry<String, String> entry : consignmentDocuments.entrySet()) {
					consignmentDocumentVO = new ConsignmentDocumentVO();
					consignmentDocumentVO.setConsignmentNumber(entry.getKey());
					consignmentDocumentVOs.add(consignmentDocumentVO);
				}
			}
			carditEnquiryVO.setConsignmentDocumentVos(consignmentDocumentVOs);
			mailbagVOs = findMailbagsForUnsentResdit(carditEnquiryVO);
			mailbagVOsTmp = mailbagVOs;
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					ArrayList<MailbagVO> mailbagVOsToSend = new ArrayList<MailbagVO>();
					for (MailbagVO mailbagVOTmp : mailbagVOsTmp) {
						if (mailbagVO.getResditEventString() != null
								&& mailbagVO.getResditEventString().equals(mailbagVOTmp.getResditEventString())
								&& mailbagVO.getResditEventSeqNum() != 0
								&& mailbagVO.getResditEventSeqNum() == mailbagVOTmp.getResditEventSeqNum()) {
							if (!MailConstantsVO.FLAG_YES.equals(mailbagVOTmp.getMailStatus())) {
								if (mailbagVOsToSend.size() == 0) {
									mailbagVOsToSend.add(mailbagVOTmp);
								}
								mailbagVOTmp.setMailStatus(MailConstantsVO.FLAG_YES);
							}
						}
					}
					if (mailbagVOsToSend != null && mailbagVOsToSend.size() > 0) {
						carditEnquiryVO.setUnsendResditEvent(mailbagVOsToSend.get(0).getResditEventString());
						carditEnquiryVO.setMailbagVos(mailbagVOsToSend);
						new ResditController().sendResditMessages(carditEnquiryVO);
					}
				}
			}
		}
		log.debug(CLASS + " : " + "sendResdit" + " Exiting");
	}

	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to findthe Mail Details for all the Unsent Resdits ..
	 */
	private Collection<MailbagVO> findMailbagsForUnsentResdit(CarditEnquiryVO carditEnquiryVo) {
		Collection<MailbagVO> mailBagVos = null;
		if (MailConstantsVO.CARDITENQ_MODE_DOC.equals(carditEnquiryVo.getSearchMode())
				&& carditEnquiryVo.getConsignmentDocumentVos() != null
				&& carditEnquiryVo.getConsignmentDocumentVos().size() > 0) {
			mailBagVos = findMailDetailsForDocument(carditEnquiryVo.getConsignmentDocumentVos(),
					carditEnquiryVo.getUnsendResditEvent());
		} else if (MailConstantsVO.CARDITENQ_MODE_DESP.equals(carditEnquiryVo.getSearchMode())
				&& carditEnquiryVo.getDespatchDetailVos() != null
				&& carditEnquiryVo.getDespatchDetailVos().size() > 0) {
			mailBagVos = findMailDetailsForDespatches(carditEnquiryVo.getDespatchDetailVos(),
					carditEnquiryVo.getUnsendResditEvent());
		}
		return mailBagVos;
	}

	/**
	 * This method is used to find the MailDetails for all UnsentResdits in case of the Search Mode Being Despatch
	 * @param despatchDetailVos
	 * @param unsentResditEvent
	 * @return
	 */
	private Collection<MailbagVO> findMailDetailsForDespatches(Collection<DespatchDetailsVO> despatchDetailVos,
															   String unsentResditEvent) {
		return Mailbag.findMailDetailsForDespatches(despatchDetailVos, unsentResditEvent);
	}

	/**
	 * @param consignmentDocumentVos
	 * @param unsentResditEvent
	 * @return
	 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to findout all the MailDetail for which the Resdits has not been sent Also the Search Mode is For The Documents..
	 */
	private Collection<MailbagVO> findMailDetailsForDocument(Collection<ConsignmentDocumentVO> consignmentDocumentVos,
															 String unsentResditEvent) {
		return Mailbag.findMailDetailsForDocument(consignmentDocumentVos, unsentResditEvent);
	}

	/**
	 * @param consignmentFilterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @author A-2107
	 */
	public Collection<MailbagVO> findCartIds(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().findCartIdsMailbags(consignmentFilterVO);
	}

	/**
	 * Added for icrd-95515
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<String> findOfficeOfExchangesForAirport(String companyCode, String airportCode) {
		log.debug("MailTrackingDefaultsServicesEJB" + " : " + "findCityAndAirportForOE" + " Entering");
        //TODO:Neo to correct
//		OfficeOfExchangeCache cache = officeOfExchangeCache;
//		return cache.findOfficeOfExchangesForAirport(companyCode, airportCode);
        return null;
	}
	/**
	 * This method does the ULD Acquittal at Non Mechanized port This will release the ULD by delivering/transferring the mailbags/despatches at non scannable ports.
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @author A-3227  RENO K ABRAHAM - 09/09/2009
	 */
	public void initiateULDAcquittance(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "initiateULDAcquittance" + " Entering");
		if (operationalFlightVO != null) {
			log.debug("" + "initiateULDAcquittance--operationalFlightVO---" + " " + operationalFlightVO);
			releaseULDFromFlight(operationalFlightVO);
		}
		log.debug(CLASS + " : " + "initiateULDAcquittance" + " Exiting");
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @author A-3227 RENO K ABRAHAM
	 */
	private void releaseULDFromFlight(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "releaseULDFromFlight" + " Entering");
		MailArrivalVO mailArrivalVO = findArrivalDetailsForULDAcquittance(operationalFlightVO);
		try {
			releasingMailsForULDAcquittance(mailArrivalVO, operationalFlightVO);
		} catch (InvalidFlightSegmentException exception) {
			exception.getMessage();
		} catch (CapacityBookingProxyException exception) {
			exception.getMessage();
		} catch (MailBookingException exception) {
			exception.getMessage();
		} catch (ContainerAssignmentException exception) {
			exception.getMessage();
		} catch (DuplicateMailBagsException exception) {
			exception.getMessage();
		} catch (MailbagIncorrectlyDeliveredException exception) {
			exception.getMessage();
		} catch (FlightClosedException exception) {
			exception.getMessage();
		} catch (ULDDefaultsProxyException exception) {
			exception.getMessage();
		} catch (MailOperationsBusinessException exception) {
			exception.getMessage();
		}
		try {
			closeFlightDuringAutoAcquittal(mailArrivalVO, operationalFlightVO);
		} catch (ULDDefaultsProxyException exception) {
			exception.getMessage();
		} catch (CloseFlightException closeFlightException) {
			closeFlightException.getMessage();
		}
		log.debug(CLASS + " : " + "releaseULDFromFlight" + " Exiting");
	}

	/**
	 * @return
	 * @throws SystemException
	 * @author A-3227 RENO K ABRAHAM
	 */
	private MailArrivalVO findArrivalDetailsForULDAcquittance(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findFlightsForULDAcquittance" + " Entering");
		String carrierCode = null;
		try {
			AirlineValidationVO airlineValidationVO = sharedAirlineProxy
					.findAirline(operationalFlightVO.getCompanyCode(), operationalFlightVO.getCarrierId());
			carrierCode = airlineValidationVO.getAlphaCode();
		} catch (SharedProxyException e) {
			e.getMessage();
		}
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		filterVO.setCarrierCode(carrierCode);
		filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		filterVO.setPou(operationalFlightVO.getPou());
		filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		return findArrivalDetails(filterVO);
	}

	/**
	 * @param mailArrivalVO
	 * @throws SystemException
	 * @throws MailBookingException
	 * @throws CapacityBookingProxyException
	 * @throws InvalidFlightSegmentException
	 * @throws ULDDefaultsProxyException
	 * @throws FlightClosedException
	 * @throws MailbagIncorrectlyDeliveredException
	 * @throws DuplicateMailBagsException
	 * @throws ContainerAssignmentException
	 * @throws DuplicateDSNException
	 * @throws InventoryForArrivalFailedException
	 * @author A-3227 RENO K ABRAHAM
	 */
	public void releasingMailsForULDAcquittance(MailArrivalVO mailArrivalVO, OperationalFlightVO operationalFlightVO)
			throws InvalidFlightSegmentException, CapacityBookingProxyException, MailBookingException,
			ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			FlightClosedException, ULDDefaultsProxyException, MailOperationsBusinessException {
		log.debug(CLASS + " : " + "releasingMailsForULDAcquittance" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			Collection<String> nearbyOEToCurrentAirport = (ArrayList<String>) findOfficeOfExchangesForAirport(
					mailArrivalVO.getCompanyCode(), operationalFlightVO.getAirportCode());
			Collection<DespatchDetailsVO> transittingDespatches = new ArrayList<DespatchDetailsVO>();
			Collection<MailbagVO> transittingMailbags = new ArrayList<MailbagVO>();
			HashMap<String, Collection<DespatchDetailsVO>> transittingDespatchesMap = new HashMap<String, Collection<DespatchDetailsVO>>();
			HashMap<String, Collection<MailbagVO>> transittingMailbagsMap = new HashMap<String, Collection<MailbagVO>>();
			Collection<String> transittingOEs = new ArrayList<String>();
			boolean isdeliveryRequired = false;
			boolean isAnyContianerOccupied = false;
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				Collection<MailbagVO> terminatingmailbagVOs = new ArrayList<MailbagVO>();
				if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
					containerDetailsVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
				}
				int totalNumberOfDSNVOs = 0;
				int totalReceivedBags = 0;
				double totalReceivedWgt = 0;
				if (containerDetailsVO.getDsnVOs() != null && containerDetailsVO.getDsnVOs().size() > 0) {
					totalNumberOfDSNVOs = containerDetailsVO.getDsnVOs().size();
					isAnyContianerOccupied = true;
				}
				ArrayList<DSNVO> mainDSNVOs = (ArrayList<DSNVO>) containerDetailsVO.getDsnVOs();
				HashMap<String, DSNVO> terminatingDSNMap = new HashMap<String, DSNVO>();
				HashMap<String, DSNVO> transittingDSNMap = new HashMap<String, DSNVO>();
				for (int dsnIdx = 0; dsnIdx < totalNumberOfDSNVOs; dsnIdx++) {
					DSNVO dsnVO = mainDSNVOs.get(dsnIdx);
					String dsnpk = dsnVO.getOriginExchangeOffice() + ID_SEP + dsnVO.getDestinationExchangeOffice()
							+ ID_SEP + dsnVO.getMailCategoryCode() + ID_SEP + dsnVO.getMailSubclass() + ID_SEP
							+ dsnVO.getDsn() + ID_SEP + dsnVO.getYear();
					if (isTerminating(nearbyOEToCurrentAirport, dsnVO)) {
						terminatingDSNMap.put(dsnpk, dsnVO);
					} else {
						transittingDSNMap.put(dsnpk, dsnVO);
						transittingOEs.add(dsnVO.getDestinationExchangeOffice());
					}
				}
				Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					for (DespatchDetailsVO despatchVO : despatchDetailsVOs) {
						String despatchpk = despatchVO.getOriginOfficeOfExchange() + ID_SEP
								+ despatchVO.getDestinationOfficeOfExchange() + ID_SEP
								+ despatchVO.getMailCategoryCode() + ID_SEP + despatchVO.getMailSubclass() + ID_SEP
								+ despatchVO.getDsn() + ID_SEP + despatchVO.getYear();
						DSNVO terminattingDSNVO = terminatingDSNMap.get(despatchpk);
						DSNVO transittingDSNVO = transittingDSNMap.get(despatchpk);
						if (terminattingDSNVO != null) {
							if (despatchVO.getReceivedBags() == 0) {
								despatchVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								terminattingDSNVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								terminattingDSNVO.setReceivedBags(terminattingDSNVO.getReceivedBags()
										+ (despatchVO.getAcceptedBags() - despatchVO.getReceivedBags()));
								Quantity despAccWt;
								try {
									despAccWt = despatchVO.getAcceptedWeight().subtract(despatchVO.getReceivedWeight());
									try {
										terminattingDSNVO.setReceivedWeight(
												terminattingDSNVO.getReceivedWeight().add(despAccWt));
									} finally {
									}
								} finally {
								}
								despatchVO.setReceivedBags(despatchVO.getAcceptedBags());
								despatchVO.setReceivedWeight(despatchVO.getAcceptedWeight());
								despatchVO.setReceivedDate(localDateUtil.getLocalDate(
										operationalFlightVO.getAirportCode(),true));
							}
							terminatingDSNMap.put(despatchpk, terminattingDSNVO);
						}
						if (transittingDSNVO != null) {
							despatchVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							despatchVO.setCompanyCode(containerDetailsVO.getCompanyCode());
							despatchVO.setCarrierId(containerDetailsVO.getCarrierId());
							despatchVO.setFlightNumber(containerDetailsVO.getFlightNumber());
							despatchVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
							despatchVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
							despatchVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
							despatchVO.setContainerType(containerDetailsVO.getContainerType());
							despatchVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
							despatchVO.setUbrNumber(transittingDSNVO.getUbrNumber());
							despatchVO.setBookingLastUpdateTime(transittingDSNVO.getBookingLastUpdateTime());
							despatchVO.setBookingFlightDetailLastUpdTime(
									transittingDSNVO.getBookingFlightDetailLastUpdTime());
							transittingDSNVO.setReceivedBags(transittingDSNVO.getReceivedBags()
									+ (despatchVO.getAcceptedBags() - despatchVO.getReceivedBags()));
							Quantity despRecWt;
							try {
								despRecWt = despatchVO.getAcceptedWeight().subtract(despatchVO.getReceivedWeight());
								try {
									transittingDSNVO
											.setReceivedWeight(transittingDSNVO.getReceivedWeight().add(despRecWt));
								} finally {
								}
							} finally {
							}
							transittingDespatches.add(despatchVO);
							transittingDSNMap.put(despatchpk, transittingDSNVO);
							if (transittingDespatchesMap.get(despatchpk) != null) {
								transittingDespatchesMap.get(despatchpk).add(despatchVO);
							} else {
								Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
								newDespatchVOs.add(despatchVO);
								transittingDespatchesMap.put(despatchpk, newDespatchVOs);
							}
						}
					}
				}
				Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbagVO : mailbagVOs) {
						String mailDSNPk = mailbagVO.getOoe() + ID_SEP + mailbagVO.getDoe() + ID_SEP
								+ mailbagVO.getMailCategoryCode() + ID_SEP + mailbagVO.getMailSubclass() + ID_SEP
								+ mailbagVO.getDespatchSerialNumber() + ID_SEP + mailbagVO.getYear();
						DSNVO terminattingDSNVO = terminatingDSNMap.get(mailDSNPk);
						DSNVO transittingDSNVO = transittingDSNMap.get(mailDSNPk);
						if (terminattingDSNVO != null) {
							if (!DSNVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())) {
								mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								terminattingDSNVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
								mailbagVO.setScannedPort(operationalFlightVO.getAirportCode());
								mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
								mailbagVO.setScannedDate(localDateUtil.getLocalDate(
										operationalFlightVO.getAirportCode(),true));
								mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
								if (mailArrivalVO.isOfflineJob()) {
									mailbagVO.setMailSource(MTK_IMP_FLT);
								} else {
									mailbagVO.setMailSource(MTK_INB_ONLINEFLT_CLOSURE);
								}
								terminattingDSNVO.setReceivedBags(terminattingDSNVO.getReceivedBags() + 1);
								try {
									terminattingDSNVO.setReceivedWeight(
											terminattingDSNVO.getReceivedWeight().add(mailbagVO.getWeight()));
								} finally {
								}
								terminatingmailbagVOs.add(mailbagVO);
							} else if (DSNVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())
									&& DSNVO.FLAG_NO.equals(mailbagVO.getDeliveredFlag())) {
								mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								terminattingDSNVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
								mailbagVO.setScannedPort(operationalFlightVO.getAirportCode());
								mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
								mailbagVO.setScannedDate(localDateUtil.getLocalDate(
										operationalFlightVO.getAirportCode(),true));
								mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
								if (mailArrivalVO.isOfflineJob()) {
									mailbagVO.setMailSource(MTK_IMP_FLT);
								} else {
									mailbagVO.setMailSource(MTK_INB_ONLINEFLT_CLOSURE);
								}
								terminatingmailbagVOs.add(mailbagVO);
							}
							terminatingDSNMap.put(mailDSNPk, terminattingDSNVO);
						}
						if (transittingDSNVO != null) {
							if (!("TRA".equals(mailbagVO.getMailStatus()) && mailbagVO.getArrivedFlag().equals("Y")
									&& "I".equals(mailbagVO.getMraStatus()))) {
								mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
								mailbagVO.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
								mailbagVO.setScannedPort(operationalFlightVO.getAirportCode());
								mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
								mailbagVO.setScannedDate(localDateUtil.getLocalDate(
										operationalFlightVO.getAirportCode(),true));
								mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
								mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
								mailbagVO.setFlightDate(containerDetailsVO.getFlightDate());
								mailbagVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
								mailbagVO.setUbrNumber(transittingDSNVO.getUbrNumber());
								mailbagVO.setBookingLastUpdateTime(transittingDSNVO.getBookingLastUpdateTime());
								if (mailArrivalVO.isOfflineJob()) {
									mailbagVO.setMailSource(MTK_IMP_FLT);
								} else {
									mailbagVO.setMailSource(MTK_INB_ONLINEFLT_CLOSURE);
								}
								mailbagVO.setFlightDate(operationalFlightVO.getFlightDate());
								double volume = 0.0;
								String commodityCode = "";
								commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
								CommodityValidationVO commodityValidationVO = validateCommodity(
										mailbagVO.getCompanyCode(), commodityCode, mailbagVO.getPaCode());
								if (commodityValidationVO != null && commodityValidationVO.getDensityFactor() != 0) {
									double actualVolume = mailbagVO.getWeight().getRoundedValue().doubleValue()
											/ commodityValidationVO.getDensityFactor();
									if (actualVolume < 0.01) {
										volume = 0.01;
									} else {
										volume = actualVolume;
									}
								}
								log.debug("" + "VOLUME IN MAIL CONTROLLER" + " " + volume);
								mailbagVO.setVolume(
										quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(volume)));
								mailbagVO.setBookingFlightDetailLastUpdTime(
										transittingDSNVO.getBookingFlightDetailLastUpdTime());
								transittingDSNVO.setReceivedBags(transittingDSNVO.getReceivedBags() + 1);
								try {
									transittingDSNVO.setReceivedWeight(
											transittingDSNVO.getReceivedWeight().add(mailbagVO.getWeight()));
								} finally {
								}
								transittingMailbags.add(mailbagVO);
								transittingDSNMap.put(mailDSNPk, transittingDSNVO);
								if (transittingMailbagsMap.get(mailDSNPk) != null) {
									transittingMailbagsMap.get(mailDSNPk).add(mailbagVO);
								} else {
									Collection<MailbagVO> newTransittingMailbags = new ArrayList<MailbagVO>();
									newTransittingMailbags.add(mailbagVO);
									transittingMailbagsMap.put(mailDSNPk, newTransittingMailbags);
								}
							}
						}
					}
				}
				if (terminatingDSNMap != null && terminatingDSNMap.size() > 0) {
					isdeliveryRequired = true;
				}
				for (DSNVO dsnVO : mainDSNVOs) {
					if (dsnVO.getReceivedBags() > 0) {
						totalReceivedBags += dsnVO.getReceivedBags();
						totalReceivedWgt += dsnVO.getReceivedWeight().getRoundedValue().doubleValue();
					}
				}
				containerDetailsVO.setReceivedBags(totalReceivedBags);
				containerDetailsVO.setReceivedWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalReceivedWgt)));
				containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
				containerDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				containerDetailsVO.setMailDetails(terminatingmailbagVOs);
			}
			mailArrivalVO.setPartialDelivery(true);
			mailArrivalVO.setAirportCode(operationalFlightVO.getAirportCode());
			if (transittingOEs.size() > 0) {
				updatebulkDetails(transittingMailbags);
				transferMailForULDAcquittal(transittingOEs, operationalFlightVO, transittingDespatchesMap,
						transittingMailbagsMap);
			}
			if (isdeliveryRequired) {
				deliverMailbags(mailArrivalVO);
			}
			if (!isAnyContianerOccupied) {
				saveArrivalDetails(mailArrivalVO);
			}
		} else {
			saveArrivalDetails(mailArrivalVO);
		}
		try {
			PersistenceController.getEntityManager().flush();
			PersistenceController.getEntityManager().clear();
		} catch (PersistenceException e) {
			e.getMessage();
		}
		log.debug(CLASS + " : " + "releasingMailsForULDAcquittance" + " Exiting");
	}

	/**
	 * @param nearbyOEToCurrentAirport
	 * @return
	 * @author A-3227 RENO K ABRAHAM
	 */
	private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport, DSNVO dsnVO) {
		log.debug(CLASS + " : " + "break;" + " Entering");
		boolean isTerminating = false;
		if (nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0) {
			for (String officeOfExchange : nearbyOEToCurrentAirport) {
				if (dsnVO != null) {
					isTerminating = officeOfExchange.equals(dsnVO.getDestinationExchangeOffice()) ? true : false;
					if (isTerminating) {
						break;
					}
				}
			}
		}
		log.debug(CLASS + " : " + "break;" + " Exiting");
		return isTerminating;
	}

	private void updatebulkDetails(Collection<MailbagVO> mailBagVOinConatiners) {
		log.debug("Inside UpDATEBULK>>>>");
		if (mailBagVOinConatiners != null) {
			for (MailbagVO mailbagvo : mailBagVOinConatiners) {
				if (MailConstantsVO.BULK_TYPE.equals(mailbagvo.getContainerType())) {
					if (mailbagvo.getOperationalFlag() != null
							&& (mailbagvo.getOperationalFlag().equals(MailConstantsVO.OPERATION_FLAG_UPDATE))) {
						Mailbag mailbag = null;
						MailbagPK mailbagPk = new MailbagPK();
						mailbagPk.setCompanyCode(mailbagvo.getCompanyCode());
						mailbagPk.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
						try {
							mailbag = Mailbag.find(mailbagPk);
						} catch (FinderException e) {
							log.error("Finder Exception Caught");
						}
						if (mailbag != null) {
							log.debug("mailbag not null>>>>");
							String containernum = mailbag.getUldNumber();
							Container container = null;
							ContainerPK containerPK = new ContainerPK();
							containerPK.setCompanyCode(mailbagvo.getCompanyCode());
							containerPK.setCarrierId(mailbagvo.getCarrierId());
							containerPK.setContainerNumber(containernum);
							containerPK.setAssignmentPort(mailbagvo.getPol());
							containerPK.setFlightNumber(mailbag.getFlightNumber());
							containerPK.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
							if (mailbagvo.getLegSerialNumber() == 0) {
								FlightValidationVO flightValidationVO = new FlightValidationVO();
								try {
									flightValidationVO = validateFlightForBulk(mailbagvo);
								} finally {
								}
								if (flightValidationVO != null) {
									containerPK.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
								}
							} else {
								containerPK.setLegSerialNumber(mailbagvo.getLegSerialNumber());
							}
							try {
								container = Container.find(containerPK);
							} catch (FinderException e) {
							}
							if (container != null) {
								log.debug("container not null>>>>");
								container.setArrivedStatus(MailConstantsVO.FLAG_YES);
							}
						}
					}
				}
			}
		}
	}

	public FlightValidationVO validateFlightForBulk(MailbagVO mailbagvo) {
		var fltOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		var fltFilterVO = MailOperationsVOConverter.constructFlightFilterVOForContainer(mailbagvo);
		var flightValidationVOs = fltOperationsProxy.validateFlightForAirport(fltFilterVO);
		if (CollectionUtils.isNotEmpty(flightValidationVOs)) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == mailbagvo.getFlightSequenceNumber()) {
					return flightValidationVO;
				}
			}
		}

		return null;
	}

	/**
	 * @param transittingOEs
	 * @param operationalFlightVO
	 * @param transittingDespatchesMap
	 * @param transittingMailbagsMap
	 * @throws SystemException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 * @author A-3227 RENO K ABRAHAM
	 */
	private void transferMailForULDAcquittal(Collection<String> transittingOEs, OperationalFlightVO operationalFlightVO,
											 HashMap<String, Collection<DespatchDetailsVO>> transittingDespatchesMap,
											 HashMap<String, Collection<MailbagVO>> transittingMailbagsMap) throws InvalidFlightSegmentException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		log.debug(CLASS + " : " + "transferMailForULDAcquittal" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<String> finalDSNKeys = new ArrayList<String>();
		if (transittingMailbagsMap != null && transittingMailbagsMap.size() > 0) {
			finalDSNKeys.addAll(transittingMailbagsMap.keySet());
		}
		if (transittingDespatchesMap != null && transittingDespatchesMap.size() > 0) {
			Collection<String> despatchDSNKeys = transittingDespatchesMap.keySet();
			for (String dsnKey : despatchDSNKeys) {
				if (!finalDSNKeys.contains(dsnKey)) {
					finalDSNKeys.add(dsnKey);
				}
			}
		}
		if (finalDSNKeys.size() > 0) {
			for (String key : finalDSNKeys) {
				Collection<DespatchDetailsVO> transittingDespatches = new ArrayList<DespatchDetailsVO>();
				Collection<MailbagVO> transittingMailbags = new ArrayList<MailbagVO>();
				if (transittingMailbagsMap != null && transittingMailbagsMap.size() > 0
						&& transittingMailbagsMap.get(key) != null && transittingMailbagsMap.get(key).size() > 0) {
					transittingMailbags.addAll(transittingMailbagsMap.get(key));
				}
				if (transittingDespatchesMap != null && transittingDespatchesMap.size() > 0
						&& transittingDespatchesMap.get(key) != null && transittingDespatchesMap.get(key).size() > 0) {
					transittingDespatches.addAll(transittingDespatchesMap.get(key));
				}
				ContainerVO toContainerVO = new ContainerVO();
				String[] tokens = key.split(ID_SEP);
				String transittingOE = tokens[1];
				//TODO:Below code to be corrected
				toContainerVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
				toContainerVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
				log.debug("" + "Transferring [ " + " " + key + " " + " ] to Carrier===>" + " "
						+ logonAttributes.getOwnAirlineIdentifier());
				toContainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
				toContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				toContainerVO.setOperationTime(localDateUtil.getLocalDate(
						operationalFlightVO.getAirportCode(),true));
				toContainerVO.setAssignedPort(operationalFlightVO.getAirportCode());
				toContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
				transferMail(transittingDespatches, transittingMailbags, toContainerVO, MailConstantsVO.FLAG_NO);
			}
		}
		log.debug(CLASS + " : " + "transferMailForULDAcquittal" + " Exiting");
	}

	/**
	 * closeFlightDuringAutoAcquittal
	 * @param mailArrivalVO
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws CloseFlightException
	 * @throws ULDDefaultsProxyException
	 */
	private void closeFlightDuringAutoAcquittal(MailArrivalVO mailArrivalVO, OperationalFlightVO operationalFlightVO)
			throws CloseFlightException, ULDDefaultsProxyException {
		log.debug(CLASS + " : " + "closeFlightDuringAutoAcquittal" + " Entering");
		Collection<String> sysParameters = new ArrayList<String>();
		sysParameters.add(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT);
		Map<String, String> sysParameterMap = null;
		try {
			sysParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(sysParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + sysParameterMap);
		if (sysParameterMap != null && OperationalFlightVO.FLAG_YES
				.equals(sysParameterMap.get(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT))) {
			Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
			boolean canCloseFlight = true;
			if (containerDetails != null && containerDetails.size() > 0) {
				for (ContainerDetailsVO containerDtls : containerDetails) {
					if (containerDtls.getDsnVOs() != null && containerDtls.getDsnVOs().size() > 0) {
						for (DSNVO dSNVO : containerDtls.getDsnVOs()) {
							if (DSNVO.FLAG_NO.equals(dSNVO.getRoutingAvl())) {
								canCloseFlight = false;
								break;
							}
						}
					}
					if (!canCloseFlight) {
						break;
					}
				}
			}
			if (!canCloseFlight) {
				throw new CloseFlightException(CloseFlightException.ROUTING_UNAVAILABLE);
			}
		}
		closeInboundFlight(operationalFlightVO);
		log.debug(CLASS + " : " + "closeFlightDuringAutoAcquittal" + " Exiting");
	}

	public void closeInboundFlightForMailOperation(@NonNull String companyCode) {
		log.debug(CLASS + " : " + "closeInboundFlightForMailOperation" + " Entering");
		var systemParameterMap = findSystemParameter(MAIL_ARRIVAL_NEEDED, FLIGHT_CLOSURE_ENABLED);
		boolean mailArrivalNeeded = isMailArrivalNeeded(systemParameterMap);
		boolean flightClosureNeeded = isFlightClosureNeeded(systemParameterMap);
		if (!mailArrivalNeeded) {
			try {
				var mailArrivalVOs = Mailbag.findOnlineFlightsAndConatiners(companyCode);
				if (CollectionUtils.isNotEmpty(mailArrivalVOs)) {
					for (MailArrivalVO mailArrivalVO : mailArrivalVOs) {
						try {
							if (flightClosureNeeded) {
								closeMailInboundFlight(populateOperationalFlightVO(mailArrivalVO));
							}
						} catch (BusinessException e) {
							log.error("Exception in: closeInboundFlightForMailOperation", e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				log.error("Exception in: closeInboundFlightForMailOperation", e.getMessage());
			}
		} else {
			var operationalFlightVOs = Mailbag.findFlightsForArrival(companyCode);
			if (CollectionUtils.isNotEmpty(operationalFlightVOs)) {
				for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
					var containerDetailsVOs = Mailbag.findArrivalDetailsForReleasingMails(operationalFlightVO);
					var arrivalVO = populateMailArrivalVO(operationalFlightVO, containerDetailsVOs);
					try {
						releasingMailsForULDAcquittanceForProxy(arrivalVO, operationalFlightVO);
					} catch (Exception e) {
						log.error("Exception in: closeInboundFlightForMailOperation", e.getMessage());
						continue;
					}
					if (flightClosureNeeded) {
						try {
							closeMailInboundFlight(operationalFlightVO);
						} catch (Exception e) {
							log.error("Exception in: closeInboundFlightForMailOperation", e.getMessage());
						}
					}
				}
			}
		}

		log.debug(CLASS + " : " + "closeInboundFlightForMailOperation" + " Exiting");
	}

	private MailArrivalVO populateMailArrivalVO(@NonNull OperationalFlightVO flightVo,
												@NonNull Collection<ContainerDetailsVO> containerDetailsVOs) {
		var mailArrivalVO = new MailArrivalVO();
		mailArrivalVO.setCompanyCode(flightVo.getCompanyCode());
		mailArrivalVO.setCarrierId(flightVo.getCarrierId());
		mailArrivalVO.setFlightNumber(flightVo.getFlightNumber());
		mailArrivalVO.setFlightSequenceNumber(flightVo.getFlightSequenceNumber());
		mailArrivalVO.setArrivalDate(flightVo.getFlightDate());
		mailArrivalVO.setAirportCode(flightVo.getPou());
		mailArrivalVO.setLegSerialNumber(flightVo.getLegSerialNumber());
		if (CollectionUtils.isNotEmpty(containerDetailsVOs)) {
			mailArrivalVO.setFlightStatus(containerDetailsVOs.iterator().next().getFlightStatus());
			mailArrivalVO.setFlightCarrierCode(containerDetailsVOs.iterator().next().getCarrierCode());
			mailArrivalVO.setContainerDetails(containerDetailsVOs);
		}

		return mailArrivalVO;
	}

	private OperationalFlightVO populateOperationalFlightVO(@NonNull MailArrivalVO mailArrivalVO) {
		OperationalFlightVO flightVO = new OperationalFlightVO();
		flightVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		flightVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		flightVO.setPou(mailArrivalVO.getPou());
		flightVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		flightVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
		flightVO.setCarrierId(mailArrivalVO.getCarrierId());
		flightVO.setFlightDate(mailArrivalVO.getFlightDate());
		return flightVO;
	}

	private boolean isFlightClosureNeeded(@Nullable Map<String, String> systemParameterMap) {
		if (Objects.isNull(systemParameterMap)) {
			return false;
		}

        return ContainerVO.FLAG_YES.equals(systemParameterMap.get(FLIGHT_CLOSURE_ENABLED));
    }

	private boolean isMailArrivalNeeded(@Nullable Map<String, String> systemParameterMap) {
		if (Objects.isNull(systemParameterMap)) {
			return false;
		}

        return ContainerVO.FLAG_YES.equals(systemParameterMap.get(MAIL_ARRIVAL_NEEDED));
    }

	public Map<String, String> findSystemParameter(String MAIL_ARRIVAL_NEEDED, String FLIGHT_CLOSURE_ENABLED) {
		var systemParameters = List.of(MAIL_ARRIVAL_NEEDED, FLIGHT_CLOSURE_ENABLED);
		var proxy= ContextUtil.getInstance().getBean(SharedDefaultsProxy.class);
	    try {
			return proxy.findSystemParameterByCodes(systemParameters);
		} catch (SystemException e) {
			log.error("Exception in: findSystemParameter", e.getMessage());
		}

		return null;
	}

	/**
	 * Method		:	MailController.closeInboundFlightAfterULDAcquital Added by 	:	A-4809 on Oct 6, 2015 Used for 	: Parameters	:	@param operationalFlightVO Return type	: 	void
	 */
	public void closeInboundFlightAfterULDAcquital(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "closeInboundFlight" + " Entering");
		LoginProfile logon = contextUtil.callerLoginProfile();
		AssignedFlight assignedFlight = null;
		AssignedFlightVO assignedFlightVO = null;
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPK.setAirportCode(operationalFlightVO.getPou());
		assignedFlightPK.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPK.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPK.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPK);
		} catch (FinderException e) {
			log.info(FINDEREXCEPTIO_STRING);
			log.info("DATA INCONSISTENT");
			assignedFlightVO = new AssignedFlightVO();
			assignedFlightVO.setAirportCode(operationalFlightVO.getPou());
			assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
			assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
			assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			assignedFlightVO.setLastUpdateTime(
					localDateUtil.getLocalDate(logon.getAirportCode(), true));
			assignedFlightVO.setLastUpdateUser(logon.getUserId());
			assignedFlight = new AssignedFlight(assignedFlightVO);
		}
		if (assignedFlight != null) {
			assignedFlight.setImportClosingFlag(MailConstantsVO.FLIGHT_STATUS_CLOSED);
		}
		Collection<ContainerVO> containerVOs = AssignedFlight.findULDsInInboundFlight(operationalFlightVO);
		if (containerVOs != null && containerVOs.size() > 0) {
			int segNo = 0;
			for (ContainerVO container : containerVOs) {
				ULDForSegmentPK uLDForSegmentPK = new ULDForSegmentPK();
				ContainerVO containerVO = new ContainerVO();
				uLDForSegmentPK.setCompanyCode(operationalFlightVO.getCompanyCode());
				uLDForSegmentPK.setCarrierId(operationalFlightVO.getCarrierId());
				uLDForSegmentPK.setFlightNumber(operationalFlightVO.getFlightNumber());
				uLDForSegmentPK.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				uLDForSegmentPK.setUldNumber(container.getContainerNumber());
				try {
					containerVO = mailOperationsMapper.copyContainerVO(container);
					containerVO.setCompanyCode(operationalFlightVO.getCompanyCode());
					containerVO.setCarrierId(operationalFlightVO.getCarrierId());
					containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
					segNo = findContainerSegment(containerVO);
				} catch (InvalidFlightSegmentException e) {
					e.getMessage();
				}
				uLDForSegmentPK.setSegmentSerialNumber(segNo);
				try {
					ULDForSegment uLDForSegment = null;
					uLDForSegment = ULDForSegment.find(uLDForSegmentPK);
					if (!MailConstantsVO.FLAG_YES.equals(uLDForSegment.getReleasedFlag())) {
						uLDForSegment.setReleasedFlag(MailConstantsVO.FLAG_YES);
					}
				} catch (FinderException e) {
					e.getErrorCode();
				}
				ContainerPK containerPK = new ContainerPK();
				containerPK.setCompanyCode(containerVO.getCompanyCode());
				containerPK.setCarrierId(containerVO.getCarrierId());
				containerPK.setFlightNumber(containerVO.getFlightNumber());
				containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerPK.setLegSerialNumber(containerVO.getLegSerialNumber());
				containerPK.setAssignmentPort(containerVO.getAssignedPort());
				containerPK.setContainerNumber(containerVO.getContainerNumber());
				Container containerToUpdate = null;
				try {
					containerToUpdate = Container.find(containerPK);
					containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
					containerToUpdate.setArrivedStatus(MailConstantsVO.FLAG_YES);
				} catch (FinderException finderException) {
					log.debug("FinderException ");
				}
			}
		}
	}

	/**
	 * @param companyCode
	 * @param time
	 * @throws SystemException
	 * @author A-1885
	 */
	public void closeFlightForMailOperation(String companyCode, int time, String airportCode) {
		Collection<OperationalFlightVO> flights = Mailbag.findFlightForMailOperationClosure(companyCode, time,
				airportCode);
		if (flights != null && flights.size() > 0) {
			for (OperationalFlightVO flightVo : flights) {
				try {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.closeFlightForOperations(flightVo);
				} catch (CloseFlightException e) {
					for (ErrorVO errVo : e.getErrors()) {
						throw new SystemException(errVo.getErrorCode());
					}
				}
			}
		}
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws CloseFlightException
	 * @author a-3251 SREEJITH P.C.
	 */
	public void closeFlightForOperations(OperationalFlightVO operationalFlightVO) throws CloseFlightException {
		log.debug(CLASS + " : " + "closeFlightForOperations" + " Entering");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		boolean isClosed;
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info(FINDEREXCEPTIO_STRING);
		}
		if (assignedFlight != null) {
			log.debug("The Flight is assigned for mail operations... ");
			isClosed = isFlightClosedForOperations(operationalFlightVO);
			if (isClosed) {
				log.debug("The Flight is already closed");
				return;
			} else {
				String containersAssigned = AssignedFlight.findAnyContainerInAssignedFlight(operationalFlightVO);
				if (containersAssigned == null) {
					log.debug("Is flight assigned but not loaded");
					try {
						closeFlightWithoutMails(operationalFlightVO);
					} catch (ULDDefaultsProxyException e) {
						log.debug("ULDDefaultsProxyException");
					}
				} else {
					if (operationalFlightVO.isScanned()) {
						try {
							MailManifestVO mailManifestVO = findContainersInFlightForManifest(operationalFlightVO);
							mailManifestVO.setCompanyCode(operationalFlightVO.getCarrierCode());
							mailManifestVO.setDepDate(operationalFlightVO.getFlightDate());
							mailManifestVO.setDepPort(operationalFlightVO.getPol());
							mailManifestVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
							mailManifestVO.setFlightNumber(operationalFlightVO.getFlightNumber());
							closeFlightManifest(operationalFlightVO, mailManifestVO);
						} catch (ULDDefaultsProxyException e) {
							log.debug("ULDDefaultsProxyException");
						}
					} else {
						if ("1".equals(containersAssigned)) {
							log.debug("Is flight loaded ");
							throw new CloseFlightException(CloseFlightException.CLOSEFLIGHT_EXCEPTION,
									new Object[] { operationalFlightVO.getFlightNumber() });
						}
					}
				}
			}
		} else {
			log.debug("The Flight not assigned for mail operations... ");
			AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
			assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
			assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
			assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
			assignedFlightVO.setFlightStatus("O");
			LoginProfile logonVO = contextUtil.callerLoginProfile();
			assignedFlightVO.setLastUpdateTime(localDateUtil.getLocalDate(logonVO.getAirportCode(),true));
			assignedFlightVO.setLastUpdateUser(logonVO.getUserId());
			AssignedFlight assignedFlightnew = new AssignedFlight(assignedFlightVO);
			try {
				closeFlightWithoutMails(operationalFlightVO);
			} catch (ULDDefaultsProxyException e) {
				log.debug("ULDDefaultsProxyException");
			}
		}
		log.debug(CLASS + " : " + "closeFlightForOperations" + " Exiting");
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @author a-3251 SREEJITH P.C.
	 */
	public void closeFlightWithoutMails(OperationalFlightVO operationalFlightVO)
			throws ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeFlightWithoutMails" + " Entering");
		closeFlight(operationalFlightVO);
		if (operationalFlightVO.getFlightRoute() != null) {
			MailAlertMessageVO mailAlertMessageVO = new MailAlertMessageVO();
			mailAlertMessageVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			mailAlertMessageVO.setCondatails(new ArrayList<ContainerDetailsVO>());
			mailAlertMessageVO.setDepartureDate(operationalFlightVO.getFlightDate());
			mailAlertMessageVO.setDeptport(operationalFlightVO.getPol());
			mailAlertMessageVO
					.setFlightnum(operationalFlightVO.getCarrierCode() + SPACE + operationalFlightVO.getFlightNumber());
			mailAlertMessageVO.setRoute(operationalFlightVO.getFlightRoute());
			mailAlertMessageVO.setAirlinecode(operationalFlightVO.getOwnAirlineCode());
			String st[] = operationalFlightVO.getFlightRoute().split("-");
			Collection<String> stations = new ArrayList<String>();
			for (int i = 0; i < st.length; i++) {
				stations.add(st[i]);
			}
			log.debug("" + "\n*******Stations to which the message is to be send are -------->\n" + " " + stations);
			mailAlertMessageVO.setStations(stations);
			log.debug("" + "\n*******Mail Alert Message VO -------->\n" + " " + mailAlertMessageVO);
			log.debug(CLASS + " : " + "closeFlightWithoutMails" + " Exiting");
		}
	}

	/**
	 * @throws SystemException
	 * @author A-5166Added for ICRD-17262 on 07-Mar-2013
	 */
	public void initiateArrivalForFlights(ArriveAndImportMailVO arriveAndImportMailVO) {
		log.debug(MODULE + " : " + "initiateArrivalForFlights" + " Entering");
		Collection<OperationalFlightVO> operationalFlightVOs = findImportFlghtsForArrival(
				arriveAndImportMailVO.getCompanyCode());
		if (operationalFlightVOs != null && operationalFlightVOs.size() > 0) {
			for (OperationalFlightVO flightVo : operationalFlightVOs) {
				try {
					Collection<ContainerDetailsVO> containerDetailsVO = Mailbag
							.findArrivalDetailsForReleasingMails(flightVo);
					MailArrivalVO arrivalVO = new MailArrivalVO();
					arrivalVO.setCompanyCode(flightVo.getCompanyCode());
					arrivalVO.setFlightCarrierCode(flightVo.getCarrierCode());
					arrivalVO.setCarrierId(flightVo.getCarrierId());
					arrivalVO.setFlightNumber(flightVo.getFlightNumber());
					arrivalVO.setFlightSequenceNumber(flightVo.getFlightSequenceNumber());
					arrivalVO.setArrivalDate(flightVo.getFlightDate());
					arrivalVO.setAirportCode(flightVo.getPou());
					arrivalVO.setLegSerialNumber(flightVo.getLegSerialNumber());
					arrivalVO.setOfflineJob(true);
					if (containerDetailsVO != null && containerDetailsVO.size() > 0) {
						arrivalVO.setFlightStatus(containerDetailsVO.iterator().next().getFlightStatus());
						arrivalVO.setContainerDetails(containerDetailsVO);
					}
					arrivalVO.setOffset(arriveAndImportMailVO.getOffset());
					arrivalVO.setArrivalAndDeliveryMarkedTogether(
							arriveAndImportMailVO.isArrivalAndDeliveryMarkedTogether());
					arrivalVO.setActualArrivalTime(flightVo.getActualArrivalTime());
					arrivalVO.setLegDestination(flightVo.getLegDestination());
					try {
						releasingMailsForULDAcquittance(arrivalVO, flightVo);
					} catch (InvalidFlightSegmentException exception) {
						exception.getMessage();
					} catch (CapacityBookingProxyException exception) {
						exception.getMessage();
					} catch (MailBookingException exception) {
						exception.getMessage();
					} catch (ContainerAssignmentException exception) {
						exception.getMessage();
					} catch (DuplicateMailBagsException exception) {
						exception.getMessage();
					} catch (MailbagIncorrectlyDeliveredException exception) {
						exception.getMessage();
					} catch (FlightClosedException exception) {
						exception.getMessage();
					} catch (ULDDefaultsProxyException exception) {
						exception.getMessage();
					} catch (MailOperationsBusinessException exception) {
						exception.getMessage();
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.closeInboundFlightAfterULDAcquital(flightVo);
				} finally {
				}
				log.debug(MODULE + " : " + "initiateArrivalForFlights" + " Exiting");
			}
		}
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @author A-5166
	 */
	public Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode) {
		log.debug(MODULE + " : " + "findImportFlghtsForArrival" + " Entering");
		return AssignedFlight.findImportFlghtsForArrival(companyCode);
	}

	/**
	 * @param dSNEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findDSNs" + " Entering");
		Page<DespatchDetailsVO> despatchDetailsVOs = null;
		return Mailbag.findDSNs(dSNEnquiryFilterVO, pageNumber);
	}

	/**
	 * This method saves the details of a CARDIT received from a PA This method is invoked from MessageBroker A-1739
	 * @param ediInterchangeVO the VO for edi Interchange which can contain multiple CARDITs
	 * @throws SystemException
	 * @throws DuplicateMailBagsException
	 * @throws IllegalAccessException
	 */
	public Collection<ErrorVO> saveCarditMessages(
			EDIInterchangeVO ediInterchangeVO)
			throws BusinessException, DuplicateMailBagsException, IllegalAccessException,
			InvocationTargetException, SystemException {
		log.debug(CLASS + " : " + "saveCarditMessages" + " Entering");
		Collection<ErrorVO> errors = new ArrayList<>();
		boolean isAutoProcessEnabled = false;
		boolean canFailAttachedAWBs = false;
		ConsignmentDocumentVO consignmentDocumentVO = null;
		ConsignmentDocumentVO existingMailBagsInConsignment = new ConsignmentDocumentVO();
		String autoProcessEnabled = MailConstantsVO.AUTO_PROCESS_NEEDED;
		String failAlreadyAttached = MailConstantsVO.SYSPAR_FAIL_ATTACHEDA_AWB;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(autoProcessEnabled);
		systemParameters.add(failAlreadyAttached);
		Map<String, String> systemParameterMap = null;
		systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		Map<String, String> mailInCarditMap = new HashMap<String, String>();
		log.debug(" systemParameterMap " + systemParameterMap);
		if (systemParameterMap != null
				&& EDIInterchangeVO.FLAG_YES.equals(systemParameterMap.get(autoProcessEnabled))) {
			isAutoProcessEnabled = true;
		}
		if (systemParameterMap != null
				&& EDIInterchangeVO.FLAG_YES.equals(systemParameterMap.get(failAlreadyAttached))) {
			canFailAttachedAWBs = true;
		}
		Collection<CarditVO> cardits = ediInterchangeVO
				.getCarditMessages();
		for (CarditVO carditVO : cardits) {
			updateCarditSender(carditVO);
			setTransportInformation(carditVO);
			if (carditVO.getReceptacleInformation() != null && !carditVO.getReceptacleInformation().isEmpty())
				for (CarditReceptacleVO receptacleVO : carditVO.getReceptacleInformation()) {
					if (receptacleVO.getReceptacleId() != null) {
						Mailbag mailbag = null;
						MailbagPK mailbagPk = null;
						long mailSequenceNumber = findMailSequenceNumber(receptacleVO.getReceptacleId(),
								carditVO.getCompanyCode());
						if (mailSequenceNumber > 0) {
							mailbagPk = new MailbagPK();
							mailbagPk.setCompanyCode(carditVO.getCompanyCode());
							mailbagPk.setMailSequenceNumber(mailSequenceNumber);
							try {
								mailbag = Mailbag.find(mailbagPk);
								receptacleVO.setMasterDocumentNumber(mailbag.getMasterDocumentNumber());
								receptacleVO.setDuplicateNumber(mailbag.getDupliacteNumber());
								receptacleVO.setOwnerId(mailbag.getDocumentOwnerId());
								receptacleVO.setSequenceNumber(mailbag.getSequenceNumber());
							} catch (FinderException finderException) {
								mailbag = null;
							}
						}
						receptacleVO.setCarditType(
								receptacleVO.getCarditType() != null ? receptacleVO.getCarditType() : "N");
						if (canFailAttachedAWBs && mailbag != null && mailbag.getMasterDocumentNumber() != null
								&& !mailbag.getMasterDocumentNumber().isEmpty()) {
							throw new MailOperationsBusinessException(MailConstantsVO.MAILBAG_IS_AWB_ATTACHED);
						}
					}
				}
			if (carditVO.getReceptacleInformation() != null && !carditVO.getReceptacleInformation().isEmpty()) {
				mailInCarditMap = carditVO.getReceptacleInformation().stream().collect(
						Collectors.toMap(CarditReceptacleVO::getReceptacleId, CarditReceptacleVO::getCarditType));
			}
			try {
				CarditVO cdtVO = Cardit.findCarditDetailsForResdit(ediInterchangeVO.getCompanyCode(),
						carditVO.getConsignmentNumber());
				Cardit cardit = null;
				if (cdtVO != null && cdtVO.getSenderId() != null
						&& !cdtVO.getSenderId().equals(carditVO.getSenderId())) {
					carditVO.setSenderIdChanged(true);
					CarditPK carditPK = new CarditPK();
					carditPK.setCompanyCode(ediInterchangeVO.getCompanyCode());
					carditPK.setCarditKey(cdtVO.getCarditKey());
					cardit = Cardit.find(carditPK);
					cardit.remove();
					cardit = new Cardit(carditVO);
				} else {
					cardit = Cardit.find(constructCarditPK(ediInterchangeVO, carditVO));
				}
				log.debug("cardit already exists,  update " + carditVO.getCarditKey());
				if (carditVO.getCarditType() != null && carditVO.getCarditType().trim().length() > 0) {
					if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())
							&& (!"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId()))) {
						cardit.setCarditType(carditVO.getCarditType());
						updateReceptacleDetailsForCancellation(cardit, carditVO);
						populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CANCELLATION);
						if (isAutoProcessEnabled) {
							ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
							consignmentFilterVO.setCompanyCode(carditVO.getCompanyCode());
							consignmentFilterVO.setPaCode(carditVO.getSenderId());
							consignmentFilterVO.setConsignmentNumber(carditVO.getConsignmentNumber());
							if (carditVO.getReceptacleInformation() != null
									&& carditVO.getReceptacleInformation().size() > 0) {
								consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
							} else {
								consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
								consignmentFilterVO.setPageNumber(1);
								consignmentFilterVO.setTotalRecords(-1);
							}
							ConsignmentDocumentVO consignmentDocumentVOTofind = new DocumentController()
									.findConsignmentDocumentDetails(consignmentFilterVO);
							Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
							Collection<MailInConsignmentVO> existingMailInConsignmentVOs = null;
							existingMailInConsignmentVOs = findMailInConsignment(consignmentFilterVO);
							if (consignmentDocumentVOTofind != null) {
								mailInConsignmentVOs = consignmentDocumentVOTofind.getMailInConsignmentVOs();
								existingMailBagsInConsignment = mailOperationsMapper.copyConsignmentDocumentVO(consignmentDocumentVOTofind);
								existingMailBagsInConsignment.setMailInConsignmentcollVOs(existingMailInConsignmentVOs);
								ConsignmentDocument consignmentDocument = ConsignmentDocument
										.find(consignmentDocumentVOTofind);
								consignmentDocument.remove();
							}
							Collection<CarditReceptacleVO> receptacleVOs = carditVO.getReceptacleInformation();
							if (receptacleVOs != null && receptacleVOs.size() > 0) {
								log.debug(" Going to create MailInConsignment ==>> ");
								for (CarditReceptacleVO receptacleVO : receptacleVOs) {
									if (receptacleVO.getReceptacleId() != null) {
										Mailbag mailbag = null;
										MailbagPK mailbagPk = null;
										boolean isMailInMRA = false;
										long mailSequenceNumber = findMailSequenceNumber(receptacleVO.getReceptacleId(),
												carditVO.getCompanyCode());
										if (mailSequenceNumber > 0) {
											mailbagPk = new MailbagPK();
											mailbagPk.setCompanyCode(carditVO.getCompanyCode());
											mailbagPk.setMailSequenceNumber(mailSequenceNumber);
											try {
												mailbag = Mailbag.find(mailbagPk);
												receptacleVO.setMailSeqNum(mailbagPk.getMailSequenceNumber());
												receptacleVO.setMasterDocumentNumber(mailbag.getMasterDocumentNumber());
												receptacleVO.setDuplicateNumber(mailbag.getDupliacteNumber());
												receptacleVO.setOwnerId(mailbag.getDocumentOwnerId());
												receptacleVO.setSequenceNumber(mailbag.getSequenceNumber());
											} catch (FinderException finderException) {
												mailbag = null;
											}
											if (mailbag != null) {
												try {
													isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(
															mailbagPk.getCompanyCode(),
															mailbagPk.getMailSequenceNumber());
												} catch (BusinessException e) {
													log.debug(e.getMessage());
												} finally {
												}
											}
										}
										if (mailbag != null && "NEW".equals(mailbag.getLatestStatus())
												&& !MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag())
												&& !isMailInMRA) {
											mailbag.remove();
										} else if (mailbag != null) {
											mailbag.setConsignmentNumber(null);
											mailbag.setConsignmentSequenceNumber(0);
											Collection<MailbagHistoryVO> mailbagHistoryVOs = Mailbag
													.findMailbagHistories(mailbag.getCompanyCode(), "",
															mailbag.getMailSequenceNumber());
											if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
												for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
													if (!MailConstantsVO.CARDIT_EVENT
															.equals(mailbagHistoryVO.getMailStatus())) {
														mailbag.setDespatchDate(mailbagHistoryVO.getScanDate());
														break;
													}
												}
											}
										}
									}
								}
							} else if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
								log.debug(" Going to create MailInConsignment ==>> ");
								for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
									Mailbag mailbag = null;
									boolean isMailInMRA = false;
									MailbagPK mailbagPk = new MailbagPK();
									mailbagPk.setCompanyCode(carditVO.getCompanyCode());
									mailbagPk.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
									try {
										mailbag = Mailbag.find(mailbagPk);
									} catch (FinderException finderException) {
										mailbag = null;
									}
									if (mailbag != null) {
										try {
											isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(
													mailbagPk.getCompanyCode(), mailbagPk.getMailSequenceNumber());
										} catch (BusinessException e) {
											log.debug(e.getMessage());
										} finally {
										}
									}
									if (mailbag != null && "NEW".equals(mailbag.getLatestStatus())
											&& !MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag())
											&& !isMailInMRA) {
										mailbag.remove();
									} else if (mailbag != null) {
										mailbag.setConsignmentNumber(null);
										mailbag.setConsignmentSequenceNumber(0);
										Collection<MailbagHistoryVO> mailbagHistoryVOs = Mailbag.findMailbagHistories(
												mailbag.getCompanyCode(), "",
												mailbag.getMailSequenceNumber());
										if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
											for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
												if (!MailConstantsVO.CARDIT_EVENT
														.equals(mailbagHistoryVO.getMailStatus())) {
													mailbag.setDespatchDate(mailbagHistoryVO.getScanDate());
													break;
												}
											}
										}
									}
								}
							}
						}
					} else if (MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())
							|| MailConstantsVO.CDT_TYP_FINAL.equalsIgnoreCase(carditVO.getCarditType())
							|| MailConstantsVO.CDT_TYP_CORRECTION.equalsIgnoreCase(carditVO.getCarditType())
							|| (MailConstantsVO.CDT_TYP_ORIGINAL.equalsIgnoreCase(carditVO.getCarditType()))
							|| (MailConstantsVO.CDT_TYP_CONFIRM.equalsIgnoreCase(carditVO.getCarditType()))) {
						if (!carditVO.isSenderIdChanged()) {
							cardit.setCarditType(carditVO.getCarditType());
							cardit.updateReceptacleDetailsForUpdation(carditVO);
						}
						if (isAutoProcessEnabled) {
							if (carditVO.getReceptacleInformation() != null
									&& carditVO.getReceptacleInformation().size() > 0) {
								ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
								consignmentFilterVO.setCompanyCode(carditVO.getCompanyCode());
								if (carditVO.isSenderIdChanged()) {
									consignmentFilterVO.setPaCode(cdtVO.getSenderId());
								} else {
									consignmentFilterVO.setPaCode(carditVO.getSenderId());
								}
								consignmentFilterVO.setConsignmentNumber(carditVO.getConsignmentNumber());
								consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
								ConsignmentDocumentVO consignmentDocumentVOTofind = new DocumentController()
										.findConsignmentDocumentDetails(consignmentFilterVO);
								if (consignmentDocumentVOTofind != null
										&& (!"CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId()))) {
									consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
									consignmentFilterVO.setPageNumber(0);
									consignmentDocumentVOTofind = new DocumentController()
											.findConsignmentDocumentDetails(consignmentFilterVO);
									existingMailBagsInConsignment = mailOperationsMapper.copyConsignmentDocumentVO(
											consignmentDocumentVOTofind);
									Collection<MailInConsignmentVO> existingMailInConsignmentVOs = findMailInConsignment(
											consignmentFilterVO);
									existingMailBagsInConsignment
											.setMailInConsignmentcollVOs(existingMailInConsignmentVOs);
									if (!carditVO.isSenderIdChanged()) {
										cardit.setCarditType(carditVO.getCarditType());
										cardit.updateReceptacleDetailsForUpdation(carditVO);
									}
									ConsignmentDocument consignmentDocument = ConsignmentDocument
											.find(consignmentDocumentVOTofind);
									consignmentDocument.remove();
									Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVOTofind
											.getMailInConsignmentVOs();
									Mailbag mailbag = null;
									boolean isMailInMRA = false;
									MailbagPK mailbagPk = new MailbagPK();
									for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
										mailbagPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
										mailbagPk.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
										try {
											mailbag = Mailbag.find(mailbagPk);
										} catch (FinderException finderException) {
											mailbag = null;
										}
										if (mailbag != null) {
											try {
												isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(
														mailbagPk.getCompanyCode(), mailbagPk.getMailSequenceNumber());
											} catch (BusinessException e) {
												log.debug(e.getMessage());
											} finally {
											}
										}
										if (mailbag != null && "NEW".equals(mailbag.getLatestStatus())
												&& !MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag())
												&& !isMailInMRA) {
											mailbag.remove();
										} else if (mailbag != null
												&& !mailInCarditMap.containsKey(mailbag.getMailIdr())) {
											mailbag.setConsignmentNumber(null);
											mailbag.setConsignmentSequenceNumber(0);
											Collection<MailbagHistoryVO> mailbagHistoryVOs = Mailbag
													.findMailbagHistories(mailbag.getCompanyCode(), "",
															mailbag.getMailSequenceNumber());
											if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
												for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
													if (!MailConstantsVO.CARDIT_EVENT
															.equals(mailbagHistoryVO.getMailStatus())) {
														mailbag.setDespatchDate(mailbagHistoryVO.getScanDate());
														break;
													}
												}
											}
										}
										try {
											PersistenceController.getEntityManager().flush();
											PersistenceController.getEntityManager().clear();
										} catch (PersistenceException e) {
											log.debug(e.getMessage());
										}
									}
									consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
									consignmentFilterVO.setPageNumber(0);
									consignmentDocumentVOTofind = new DocumentController()
											.findConsignmentDocumentDetails(consignmentFilterVO);
									if (Objects.nonNull(consignmentDocumentVOTofind)
											&& consignmentDocumentVOTofind.getMailInConsignmentVOs() != null
											&& !consignmentDocumentVOTofind.getMailInConsignmentVOs().isEmpty()) {
										for (MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVOTofind
												.getMailInConsignmentVOs()) {
											if (mailInConsignmentVO.isAwbAttached()) {
												boolean isMailAsAWB = false;
												MailbagVO mailbagVO = new MailbagVO();
												mailbagVO.setCompanyCode(mailInConsignmentVO.getCompanyCode());
												mailbagVO.setMailSequenceNumber(
														mailInConsignmentVO.getMailSequenceNumber());
												isMailAsAWB = constructDAO().isMailAsAwb(mailbagVO);
												if (isMailAsAWB)
													throw new MailOperationsBusinessException(
															MailConstantsVO.CONSIGNMENT_HAVING_AWB_ATTACHED_MAILBAGS);
											}
										}
									}
								}
								consignmentDocumentVO = createConsignmentDocVO(carditVO);
								documentController=ContextUtil.getInstance().getBean(DocumentController.class);
								try {
									if ("CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId())) {
										consignmentDocumentVO
												.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_UPDATE);
										documentController.updateConsignmentDocument(consignmentDocumentVO);
									} else {
										documentController.saveConsignmentDocument(consignmentDocumentVO);
									}
								} catch (MailbagAlreadyAcceptedException ex) {
								} catch (InvalidMailTagFormatException invalidMailTagFormatException) {
								} catch (DuplicateDSNException duplicateDSNException) {
								}
							} else {
								log.debug("NOT SAVING CONSIGNMENT DETAILS AS NO RECEPTACLE VO IS NULL " + carditVO);
							}
						}
						if (consignmentDocumentVO != null && carditVO != null) {
							updateRecpVOwithMalSeq(consignmentDocumentVO, carditVO);
						}
						if (MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_UPDATE);
						} else if (MailConstantsVO.CARDIT_STATUS_FINAL.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_FINAL);
						} else if (MailConstantsVO.CDT_TYP_ORIGINAL.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_ORIGINAL);
						} else if (MailConstantsVO.CDT_TYP_CONFIRM.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CONFIRMATION);
							cardit.updateReceptacleDetailsForConfirmation(carditVO);
						} else {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CDT_TYP_CORRECTION);
						}
					} else if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())
							&& ("CARDITDOM".equalsIgnoreCase(carditVO.getMessageTypeId()))) {
						if (carditVO.getReceptacleInformation() != null
								&& carditVO.getReceptacleInformation().size() > 0) {
							updateReceptacleDetailsForCancellationDOM(cardit, carditVO);
							ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
							consignmentFilterVO.setCompanyCode(carditVO.getCompanyCode());
							consignmentFilterVO.setPaCode(cdtVO.getSenderId());
							consignmentFilterVO.setConsignmentNumber(carditVO.getConsignmentNumber());
							consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
							ConsignmentDocumentVO consignmentDocumentVOTofind = new DocumentController()
									.findConsignmentDocumentDetails(consignmentFilterVO);
							Collection<MailInConsignmentVO> existingMailInConsignmentVOs = findMailInConsignment(
									consignmentFilterVO);
							if (consignmentDocumentVOTofind != null) {
								consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
								consignmentFilterVO.setPageNumber(0);
								consignmentDocumentVOTofind = new DocumentController()
										.findConsignmentDocumentDetails(consignmentFilterVO);
								existingMailBagsInConsignment = mailOperationsMapper.copyConsignmentDocumentVO(consignmentDocumentVOTofind);
								existingMailBagsInConsignment.setMailInConsignmentcollVOs(existingMailInConsignmentVOs);
								ConsignmentDocument consignmentDocument = ConsignmentDocument
										.find(consignmentDocumentVOTofind);
								Mailbag mailbag = null;
								long tempMailSeq = 0;
								for (CarditReceptacleVO receptacleVO : carditVO.getReceptacleInformation()) {
									if (receptacleVO.getReceptacleId() != null) {
										mailbag = null;
										boolean isMailInMRA = false;
										MailbagPK mailbagPk = new MailbagPK();
										tempMailSeq = findMailSequenceNumber(receptacleVO.getReceptacleId(),
												carditVO.getCompanyCode());
										mailbagPk.setCompanyCode(carditVO.getCompanyCode());
										mailbagPk.setMailSequenceNumber(tempMailSeq);
										if (mailbagPk.getMailSequenceNumber() != 0) {
											try {
												mailbag = Mailbag.find(mailbagPk);
												receptacleVO.setMasterDocumentNumber(mailbag.getMasterDocumentNumber());
												receptacleVO.setDuplicateNumber(mailbag.getDupliacteNumber());
												receptacleVO.setOwnerId(mailbag.getDocumentOwnerId());
												receptacleVO.setSequenceNumber(mailbag.getSequenceNumber());
											} catch (FinderException finderException) {
												mailbag = null;
											}
										}
										if (mailbag != null) {
											try {
												isMailInMRA = mailtrackingMRAProxy.isMailbagInMRA(
														mailbagPk.getCompanyCode(), mailbagPk.getMailSequenceNumber());
											} catch (BusinessException e) {
												log.debug(e.getMessage());
											} finally {
											}
											Collection<MailInConsignment> mailToRemove = new ArrayList<MailInConsignment>();
											if (consignmentDocument != null
													&& consignmentDocument.getMailsInConsignments() != null
													&& consignmentDocument.getMailsInConsignments().size() > 0) {
												consignmentDocument.getMailsInConsignments().stream()
														.filter(csgmail -> (csgmail
																.getMailSequenceNumber() == mailbagPk
																.getMailSequenceNumber()))
														.forEach(mailToRemove::add);
											}
											if ("NEW".equals(mailbag.getLatestStatus())
													&& !MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag())
													&& !isMailInMRA) {
												mailbag.remove();
											} else {
												mailbag.setConsignmentNumber(null);
												mailbag.setConsignmentSequenceNumber(0);
												Collection<MailbagHistoryVO> mailbagHistoryVOs = Mailbag
														.findMailbagHistories(mailbag.getCompanyCode(),
																"", mailbag.getMailSequenceNumber());
												if (mailbagHistoryVOs != null && !mailbagHistoryVOs.isEmpty()) {
													for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
														if (!MailConstantsVO.CARDIT_EVENT
																.equals(mailbagHistoryVO.getMailStatus())) {
															mailbag.setDespatchDate(mailbagHistoryVO.getScanDate());
															break;
														}
													}
												}
											}
											if (mailToRemove != null && mailToRemove.size() > 0) {
												for (MailInConsignment mailInConsignment : mailToRemove) {
													mailInConsignment.remove();
												}
												consignmentDocument.getMailsInConsignments().removeAll(mailToRemove);
											}
										}
									}
								}
							}
						}
					}
				} else {
					cardit.setCarditType("N");
					cardit.update(carditVO);
					populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CARDIT_V20_V11);
				}
				log.debug("cardit -- carditVO **********" + carditVO);
			} catch (FinderException exception) {
				if (carditVO.getCarditType() != null
						&& MailConstantsVO.CDT_TYP_CANCEL.equals(carditVO.getCarditType())) {
					log.debug("CHECK ---->>> \n\t\tCancel cardit uploading method not correct !!!!! +carditVO");
					throw new MailOperationsBusinessException(MailConstantsVO.MAILBAG_CSG_DOESNOT_EXIST);
				} else {
					log.debug("persisting cardit ");
					int initialCount = carditVO.getReceptacleInformation().size();
					Collection<CarditReceptacleVO> receptacleVOsBeforeRemove = new ArrayList(
							carditVO.getReceptacleInformation());
					new Cardit(carditVO);
					log.debug(" isAutoProcessEnabled : " + isAutoProcessEnabled);
					int countAfterRemoval = carditVO.getReceptacleInformation().size();
					if (isAutoProcessEnabled) {
						if (carditVO.getReceptacleInformation() != null
								&& carditVO.getReceptacleInformation().size() > 0) {
							try {
								consignmentDocumentVO = createConsignmentDocVO(carditVO);
							} finally {
							}
							try {
								documentController.saveConsignmentDocument(consignmentDocumentVO);
							} catch (MailbagAlreadyAcceptedException ex) {
							} catch (InvalidMailTagFormatException invalidMailTagFormatException) {
							} catch (DuplicateDSNException duplicateDSNException) {
							}
						} else {
							log.debug("NOT SAVING CONSIGNMENT DETAILS AS NO RECEPTACLE VO IS NULL " + carditVO);
						}
					}
					if (consignmentDocumentVO != null && carditVO != null) {
						updateRecpVOwithMalSeq(consignmentDocumentVO, carditVO);
					}
					if (carditVO.getCarditType() != null && carditVO.getCarditType().trim().length() > 0) {
						if (MailConstantsVO.CDT_TYP_ORIGINAL.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_ORIGINAL);
						} else if (MailConstantsVO.CDT_TYP_UPDATE.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_UPDATE);
						} else if (MailConstantsVO.CDT_TYP_FINAL.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_FINAL);
						} else if (MailConstantsVO.CDT_TYP_CORRECTION.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CORRECTION_ADDED);
						} else if (MailConstantsVO.CDT_TYP_CONFIRM.equalsIgnoreCase(carditVO.getCarditType())) {
							populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CONFIRMATION);
						}
					} else {
						populateCarditReceptacleHistory(carditVO, MailConstantsVO.CARDIT_STATUS_CARDIT_V20_V11);
					}
				}
			}
			errors = savePAWBDetailsFromCardit(carditVO, consignmentDocumentVO, existingMailBagsInConsignment);
		}
		log.debug(CLASS + " : " + "saveCarditMessages" + " Exiting");
		return errors;
	}

	/**
	 * A-3429
	 * @throws SystemException
	 */
	public void populateCarditReceptacleHistory(CarditVO carditVO, String status) {
		log.debug(CLASS + " : " + "populateCarditReceptacleHistory" + " Entering");
		if (carditVO.getReceptacleInformation() != null && carditVO.getReceptacleInformation().size() > 0) {
			for (CarditReceptacleVO carditReceptacleVO : carditVO.getReceptacleInformation()) {
				if (carditReceptacleVO.getReceptacleStatus() == null
						|| (carditReceptacleVO.getReceptacleStatus() != null
						&& carditReceptacleVO.getReceptacleStatus().trim().length() <= 0)) {
					carditReceptacleVO.setReceptacleStatus(status);
				}
				carditReceptacleVO.setUpdatedTime(localDateUtil.getLocalDate(
						null, true));
				new CarditReceptacleHistory(carditVO, carditReceptacleVO);
			}
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			if (!(MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType()))) {
				mailController.insertOrUpdateHistoryDetailsForCardit(carditVO, getTriggerPoint());
				mailController.insertOrUpdateAuditDetailsForCardit(carditVO);
			} else {
				mailController.auditCarditCancellation(carditVO, MailbagAuditVO.MAILBAG_DELETED);
			}
		}
	}

	/**
	 * @param carditVO
	 * @return
	 * @throws SystemException
	 */
	public ConsignmentDocumentVO createConsignmentDocVO(CarditVO carditVO) {
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Page<MailInConsignmentVO> mailInConsignmentPageVOs = new Page<MailInConsignmentVO>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		String serviceLevel = null;
		final String comma = ",";
		String companyCode = getLogonAttributes().getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		boolean paCodeExists = false;
		List<String> uspsPaCodeList = new ArrayList<>();
		String syspar = null;
		String originExchangeOffice = null;
		consignmentDocumentVO.setCompanyCode(carditVO.getCompanyCode());
		consignmentDocumentVO.setConsignmentNumber(carditVO.getConsignmentNumber());
		consignmentDocumentVO.setPaCode(carditVO.getSenderId());
		Collection<CarditTotalVO> totals = carditVO.getTotalsInformation();
		if (carditVO.getConsignmentDate() == null) {
			consignmentDocumentVO.setConsignmentDate(
					localDateUtil.getLocalDate(null,true));
		} else {
			consignmentDocumentVO.setConsignmentDate(carditVO.getConsignmentDate());
		}
		Collection<MailInConsignmentVO> maildetailsFromCardit = collectMailDetailsFromCardit(carditVO);
		syspar = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		if (syspar != null && syspar.length() > 0) {
			String[] sysparList = syspar.split(comma);
			if (sysparList.length > 0) {
				uspsPaCodeList = Arrays.asList(sysparList);
			}
		}
		if (!carditVO.getReceptacleInformation().isEmpty()) {
			originExchangeOffice = ((ArrayList<CarditReceptacleVO>) carditVO.getReceptacleInformation()).get(0)
					.getOriginExchangeOffice();
		}
		String paCode = findPAForOfficeOfExchange(companyCode, originExchangeOffice);
		if (!(null == (consignmentDocumentVO.getPaCode()))) {
			if (uspsPaCodeList.contains(consignmentDocumentVO.getPaCode())) {
				paCodeExists = true;
			}
		} else {
			if (uspsPaCodeList.contains(paCode)) {
				paCodeExists = true;
			}
		}
		for (MailInConsignmentVO mailInConsignmentVO : maildetailsFromCardit) {
			if (paCodeExists) {
				MailbagVO mailBagVO = new MailbagVO();
				mailBagVO.setCompanyCode(carditVO.getCompanyCode());
				mailBagVO.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
				mailBagVO.setMailClass(mailInConsignmentVO.getMailClass());
				mailBagVO.setMailSubclass(mailInConsignmentVO.getMailSubclass());
				mailBagVO.setPaCode(mailInConsignmentVO.getPaCode());
				if (serviceLevel == null) {
					serviceLevel = findMailServiceLevel(mailBagVO);
				} else {
					serviceLevel.concat(",").concat(findMailServiceLevel(mailBagVO));
				}
			}
			mailInConsignmentVO.setMailSource(MailConstantsVO.CARDIT_PROCESS);
			if (carditVO.getReferenceInformation() != null && !carditVO.getReferenceInformation().isEmpty()) {
				ArrayList<CarditReferenceInformationVO> referenceVO = (ArrayList<CarditReferenceInformationVO>) carditVO
						.getReferenceInformation();
				if (referenceVO != null && referenceVO.size() > 0) {
					CarditReferenceInformationVO vo = referenceVO.get(0);
					mailInConsignmentVO
							.setContractIDNumber(vo != null ? vo.getConsignmentContractReferenceNumber() : null);
					if (mailInConsignmentVO.getMailDestination() == null
							|| mailInConsignmentVO.getMailDestination().isEmpty()) {
						updateAirportDetails(mailInConsignmentVO, referenceVO, "AWN");
					}
					if (mailInConsignmentVO.getMailOrigin() == null || mailInConsignmentVO.getMailOrigin().isEmpty()) {
						updateAirportDetails(mailInConsignmentVO, referenceVO, "ERN");
					}
				}
			}
			mailInConsignmentPageVOs.add(mailInConsignmentVO);
		}
		if (paCodeExists && serviceLevel != null
				&& (serviceLevel.contains("MD") || serviceLevel.contains("MP") || serviceLevel.contains("ME"))) {
			consignmentDocumentVO.setType(MailConstantsVO.CONSIGNMENT_TYPE_AV7);
		} else {
			if (MailConstantsVO.CN38_CATEGORY.equals(carditVO.getMailCategoryCode())) {
				consignmentDocumentVO.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN38);
			} else if (MailConstantsVO.CN41_CATEGORY.equals(carditVO.getMailCategoryCode())) {
				consignmentDocumentVO.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN41);
			} else if (MailConstantsVO.CN37_CATEGORY_C.equals(carditVO.getMailCategoryCode())
					|| MailConstantsVO.CN37_CATEGORY_D.equals(carditVO.getMailCategoryCode())) {
				consignmentDocumentVO.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN37);
			}
			if (totals != null && !totals.isEmpty()) {
				CarditTotalVO carditTotalVO = totals.iterator().next();
				if ("T".equals(carditTotalVO.getMailClassCode())) {
					consignmentDocumentVO.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN47);
				}
			}
		}
		String mailOriginFromResdit = carditVO.getReceptacleInformation().iterator().next().getMailOrigin();
		consignmentDocumentVO.setAirportCode(
				(carditVO.getReceptacleInformation() != null && !carditVO.getReceptacleInformation().isEmpty()
						&& mailOriginFromResdit != null && mailOriginFromResdit.length() <= 3)
						? carditVO.getReceptacleInformation().iterator().next().getMailOrigin()
						: getLogonAirport());
		if (consignmentDocumentVO.getAirportCode() == null || consignmentDocumentVO.getAirportCode().trim().isEmpty()) {
			if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null
					&& consignmentDocumentVO.getRoutingInConsignmentVOs().size() > 0) {
				consignmentDocumentVO
						.setAirportCode(consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().getPol());
			} else {
				consignmentDocumentVO.setAirportCode(getLogonAirport());
			}
		}
		consignmentDocumentVO.setOperation(MailConstantsVO.OPERATION_OUTBOUND);
		consignmentDocumentVO.setLastUpdateUser(carditVO.getLastUpdateUser());
		consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentPageVOs);
		consignmentDocumentVO.setRoutingInConsignmentVOs(collectRoutingInfo(carditVO));
		consignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
		consignmentDocumentVO.setSecurityStatusCode(carditVO.getSecurityStatusCode());
		consignmentDocumentVO.setSecurityStatusParty(carditVO.getSecurityStatusParty());
		consignmentDocumentVO.setSecurityStatusDate(carditVO.getSecurityStatusDate());
		consignmentDocumentVO.setApplicableRegTransportDirection(carditVO.getApplicableRegTransportDirection());
		consignmentDocumentVO.setApplicableRegBorderAgencyAuthority(carditVO.getApplicableRegBorderAgencyAuthority());
		consignmentDocumentVO.setApplicableRegReferenceID(carditVO.getApplicableRegReferenceID());
		consignmentDocumentVO.setAdditionalSecurityInfo(carditVO.getAdditionalSecurityInfo());
		consignmentDocumentVO.setConsignementScreeningVOs(carditVO.getConsignementScreeningVOs());
		consignmentDocumentVO.setApplicableRegFlag(carditVO.getApplicableRegFlag());
		consignmentDocumentVO.setScanned(true);
		consignmentDocumentVO.setConsignmentIssuerName(carditVO.getConsignmentIssuerName());
		saveConsignmentDetails(consignmentDocumentVO);
		carditVO.getTransportInformation();
		if (carditVO.getCarditPawbDetailsVO() != null) {
			consignmentDocumentVO.setShipmentPrefix(carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
			consignmentDocumentVO.setMasterDocumentNumber(carditVO.getCarditPawbDetailsVO().getMasterDocumentNumber());
			consignmentDocumentVO.setShipperUpuCode(carditVO.getCarditPawbDetailsVO().getShipperCode());
			consignmentDocumentVO.setConsigneeUpuCode(carditVO.getCarditPawbDetailsVO().getConsigneeCode());
			consignmentDocumentVO.setOriginUpuCode(carditVO.getCarditPawbDetailsVO().getConsignmentOrigin());
			consignmentDocumentVO.setDestinationUpuCode(carditVO.getCarditPawbDetailsVO().getConsignmentDestination());
		}
		return consignmentDocumentVO;
	}

	public void updateReceptacleDetailsForCancellation(Cardit cardit, CarditVO carditVO) {
		log.debug(CLASS + " : " + "updateReceptacleDetailsForCancellation" + " Entering");
		Collection<CarditReceptacle> carditReceptacleDetails = cardit.getReceptacleInformation();
		if (carditReceptacleDetails != null && carditReceptacleDetails.size() > 0) {
			for (CarditReceptacle carditReceptacle : carditReceptacleDetails) {
				carditReceptacle.setCarditType(carditVO.getCarditType());
			}
		}
		log.debug(CLASS + " : " + "updateReceptacleDetailsForCancellation" + " Exiting");
	}

	/**
	 * Method		:	MailController.updateReceptacleDetailsForCancellationDOM Added by 	:	A-8061 on 16-Mar-2020 Used for 	:	IASCB-42209 Parameters	:	@param cardit Parameters	:	@param carditVO Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void updateReceptacleDetailsForCancellationDOM(Cardit cardit, CarditVO carditVO) {
		log.debug(CLASS + " : " + "updateReceptacleDetailsForCancellationDOM" + " Entering");
		Collection<CarditReceptacle> carditReceptacleDetails = cardit.getReceptacleInformation();
		Collection<CarditReceptacleVO> carditReceptacleVOs = carditVO.getReceptacleInformation();
		if (carditReceptacleDetails != null && carditReceptacleVOs != null) {
			Map<String, String> recMap = carditReceptacleVOs.stream()
					.collect(Collectors.toMap(CarditReceptacleVO::getReceptacleId, CarditReceptacleVO::getCarditType));
			carditReceptacleDetails.forEach(rec -> {
				if (recMap.containsKey(rec.getReceptacleId()))
					rec.setCarditType(recMap.get(rec.getReceptacleId()));
			});
		}
		log.debug(CLASS + " : " + "updateReceptacleDetailsForCancellationDOM" + " Exiting");
	}

	private void updateCarditSender(CarditVO carditVO) {
		log.debug(CLASS + " : " + "updateCarditSender" + " Entering");
		if (carditVO.getSenderId() != null && carditVO.getSenderId().trim().length() > 0) {
			log.debug("\n\n\n\tCARDIT SENDER ID EXIST");
			Collection<CarditReceptacleVO> carditReceptacles = (Collection<CarditReceptacleVO>) carditVO
					.getReceptacleInformation();
			if (carditReceptacles != null && carditReceptacles.size() > 0) {
				for (CarditReceptacleVO receptacleVO : carditReceptacles) {
					String originOE = receptacleVO.getOriginExchangeOffice();
					if (originOE != null && originOE.trim().length() > 0) {
						String paCode = null;
						MailTrackingDefaultsBI mailTrackingDefaultsBI = ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
						MailboxIdModel mailboxIdModel = new MailboxIdModel();
						mailboxIdModel.setCompanyCode(carditVO.getCompanyCode());
						mailboxIdModel.setMailboxID(carditVO.getActualSenderId());
						mailTrackingDefaultsBI.findMailboxId(mailboxIdModel);

						mailboxIdModel = mailTrackingDefaultsBI.findMailboxId(mailboxIdModel);
						if (mailboxIdModel != null) {
							if ("ACTIVE".equals(mailboxIdModel.getMailboxStatus())
									&& "PA".equals(mailboxIdModel.getMailboxOwner())) {
								paCode = mailboxIdModel.getOwnerCode();
							}
						}
						if (paCode == null) {
							if (carditVO.getActualSenderId() != null) {
								paCode = findPAForMailboxID(carditVO.getCompanyCode(), carditVO.getActualSenderId(),
										originOE);
							}
							if (paCode == null) {
								paCode = findPAForOfficeOfExchange(carditVO.getCompanyCode(), originOE);
							}
						}
						if (paCode != null) {
							carditVO.setSenderId(paCode);
							break;
						}
					} else {
						log.debug("\n\n\n\t mailbag invalid");
						continue;
					}
				}
			}
		} else {
			log.debug("\n\n\n\tCARDIT SENDER ID is null so setting from receptacle OE PA");
			Collection<CarditReceptacleVO> carditReceptacles = (Collection<CarditReceptacleVO>) carditVO
					.getReceptacleInformation();
			if (carditReceptacles != null && carditReceptacles.size() > 0) {
				for (CarditReceptacleVO receptacleVO : carditReceptacles) {
					String originOE = receptacleVO.getOriginExchangeOffice();
					if (originOE != null) {
						String paCode = findPAForOfficeOfExchange(carditVO.getCompanyCode(), originOE);
						carditVO.setSenderId(paCode);
						if (carditVO.getActualSenderId() == null || carditVO.getActualSenderId().trim().length() < 1) {
							carditVO.setActualSenderId(paCode);
						}
						break;
					} else {
						continue;
					}
				}
			}
		}
		log.debug("\n\n\n\t Final SENDER ID :: " + carditVO.getSenderId());
		ZonedDateTime currDate = localDateUtil.getLocalDate(carditVO.getStationCode(), false);
		String year;
		if (carditVO.getConsignmentDate() != null) {
			year = (carditVO.getConsignmentDate().format(DateTimeFormatter.ofPattern(LST_DGT_OF_YEAR_FMT))).substring(1, 2);
		} else {
			year = (currDate.format(DateTimeFormatter.ofPattern(LST_DGT_OF_YEAR_FMT)).substring(1, 2));
		}
		StringBuilder carditKey = new StringBuilder().append(carditVO.getSenderId())
				.append(carditVO.getConsignmentNumber()).append(year);
		carditVO.setCarditKey(carditKey.toString());
		if (carditVO.getConsignmentNumber() == null) {
			log.error(
					"\n\n\n\tConsignmentNumber null for carditVO.getMessageRefNum():: " + carditVO.getMessageRefNum());
			log.error("\n\n\n\tConsignmentNumber null for carditVO :: " + carditVO);
		}
		log.debug("\n\n\n\t CARDIT KEY Constructed :: " + carditVO.getCarditKey());
		log.debug(CLASS + " : " + "updateCarditSender" + " Exiting");
	}

	/**
	 * @param ediInterchangeVO
	 * @param carditVO
	 * @return
	 */
	private CarditPK constructCarditPK(EDIInterchangeVO ediInterchangeVO,
									   CarditVO carditVO) {
		CarditPK carditPK = new CarditPK();
		carditPK.setCarditKey(carditVO.getCarditKey());
		carditPK.setCompanyCode(ediInterchangeVO.getCompanyCode());
		return carditPK;
	}
	/**
	 * Added for bug ICRD-158989 by A_5526 starts Method to find pacode for mailbox id
	 * @param companyCode
	 * @param mailboxId
	 * @return
	 * @throws SystemException
	 */
	private String findPAForMailboxID(String companyCode, String mailboxId, String originOE) {
		log.debug(CLASS + " : " + "findPAForOfficeOfExchange" + " Entering");
		String paCode = null;
		//MailTrackingDefaultsBI mailTrackingDefaultsBI=ContextUtil.getInstance().getBean(MailTrackingDefaultsBI.class);
		paCode = mailMasterApi.findPAForMailboxID(companyCode,mailboxId, originOE);
		return paCode;
	}

	/**
	 * @param carditVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailInConsignmentVO> collectMailDetailsFromCardit(CarditVO carditVO) {
		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();
		final String comma = ",";
		Collection<MailInConsignmentVO> mailInCondignmentVOs = null;
		MailInConsignmentVO mailInConsignmentVO = null;
		Collection<CarditReceptacleVO> receptacles = carditVO.getReceptacleInformation();
		Collection<CarditContainerVO> carditContainers = carditVO.getContainerInformation();
		String consignmentFilter = findSystemParameterValue(MailConstantsVO.SYSPAR_CARDIT_CONSIGNMENT_FILTER);
		List<String> consignmentFilterList = null;
		if (consignmentFilter != null && consignmentFilter.length() > 0) {
			String[] consignmentFilters = consignmentFilter.split(comma);
			if (consignmentFilters.length > 0) {
				consignmentFilterList = Arrays.asList(consignmentFilters);
			}
		}
		if (receptacles != null && receptacles.size() > 0) {
			mailInCondignmentVOs = new ArrayList<MailInConsignmentVO>();
			for (CarditReceptacleVO receptacleVO : receptacles) {
				if ((consignmentFilterList == null) || (consignmentFilterList != null
						&& consignmentFilterList.size() > 0
						&& (consignmentFilterList.contains(carditVO.getConsignmentNumber().substring(0, 5))
						&& carditVO.getConsignmentNumber().substring(0, 5)
						.equals(receptacleVO.getOriginExchangeOffice().substring(0, 5)))
						|| !(consignmentFilterList.contains(carditVO.getConsignmentNumber().substring(0, 5))))) {
					MailInConsignmentVO assignedVO = null;
					mailInConsignmentVO = createMailInConsignmentVO(receptacleVO);
					mailInConsignmentVO.setConsignmentNumber(carditVO.getConsignmentNumber());
					mailInConsignmentVO.setCompanyCode(carditVO.getCompanyCode());
					mailInConsignmentVO.setPaCode(carditVO.getSenderId());
					checkForExistingMailbag(carditVO, mailInConsignmentVO, receptacleVO);
					if (carditContainers != null && carditContainers.size() > 0) {
						for (CarditContainerVO carditContainerVO : carditContainers) {
							if (carditContainerVO.getContainerNumber() != null
									&& carditContainerVO.getContainerNumber().trim().length() > 0) {
								if (carditContainerVO.getContainerJourneyIdentifier() != null
										&& carditContainerVO.getContainerJourneyIdentifier()
										.equals(mailInConsignmentVO.getMailbagJrnIdr())) {
									mailInConsignmentVO.setUldNumber(carditContainerVO.getContainerNumber());
								}
								mailInConsignmentVO.setSealNumber(carditContainerVO.getSealNumber());
								if (CarditMessageVO.UNIT_LOAD_DEVICE
										.equals(carditContainerVO.getEquipmentQualifier())) {
									Collection<RoutingInConsignmentVO> routingInfoVos = collectRoutingInfo(carditVO);
									if (routingInfoVos != null && !routingInfoVos.isEmpty()) {
										for (RoutingInConsignmentVO routingInConsignmentVO : routingInfoVos) {
											FlightFilterVO flightFilterVO = new FlightFilterVO();
											AirlineValidationVO airlineVO = null;
											try {
												airlineVO = findAirlineDescription(carditVO.getCompanyCode(),
														routingInConsignmentVO.getOnwardCarrierCode());
											} catch (SharedProxyException e) {
												log.error(e.getMessage());
											}
											if (airlineVO != null) {
												flightFilterVO.setFlightCarrierId(airlineVO.getAirlineIdentifier());
											}
											flightFilterVO.setCompanyCode(carditVO.getCompanyCode());
											flightFilterVO
													.setCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
											flightFilterVO
													.setFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
											flightFilterVO.setStation(routingInConsignmentVO.getPol());
											flightFilterVO.setActiveAlone(false);
											flightFilterVO.setDirection("O");
											if (routingInConsignmentVO.getOnwardFlightDate() != null) {
												flightFilterVO.setStringFlightDate(routingInConsignmentVO
														.getOnwardFlightDate().toString().substring(0, 11));
												if (routingInConsignmentVO.getOnwardFlightDate() != null) {
													flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(
															routingInConsignmentVO.getOnwardFlightDate()));
												}
											}
											Collection<FlightValidationVO> flightValidationVOs = validateFlight(
													flightFilterVO);
											FlightValidationVO flightValidationVO = new FlightValidationVO();
											if (flightValidationVOs != null && flightValidationVOs.size() == 1) {
												log.debug("flightValidationVOs has one VO");
												try {
													for (FlightValidationVO flightValidVO : flightValidationVOs) {
														flightValidationVO = classicVOConversionMapper_.copyFlightValidationVO(
																flightValidVO);
													}
												} finally {
												}
											}
										}
									}
								}
							}
						}
					}
					if (mailInConsignmentVO.getMailId() != null) {
						assignedVO = new DocumentController().findConsignmentDetailsForMailbag(
								mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getMailId(), null);
					}
					mailInConsignmentVO.setTransWindowEndTime(receptacleVO.getHandoverTime());
					if (assignedVO == null) {
						mailInCondignmentVOs.add(mailInConsignmentVO);
					} else if (assignedVO.getConsignmentNumber() != null
							&& assignedVO.getConsignmentNumber().equalsIgnoreCase(carditVO.getConsignmentNumber())
							&& assignedVO.getPaCode().equalsIgnoreCase(carditVO.getSenderId())) {
						mailInCondignmentVOs.add(mailInConsignmentVO);
					} else if (assignedVO.getConsignmentNumber() != null
							&& !assignedVO.getConsignmentNumber().equalsIgnoreCase(carditVO.getConsignmentNumber())) {
						assignedVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(
								mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
						MailInConsignment mailInConsignment = null;
						try {
							mailInConsignment = MailInConsignment.find(assignedVO);
						} catch (FinderException e) {
							log.debug(e.getMessage());
						}
						mailInConsignment.remove();
						mailInConsignmentVO.setConsignmentNumber(carditVO.getConsignmentNumber());
						mailInConsignmentVO.setConsignmentSequenceNumber(1);
						mailInConsignmentVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(
								mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
						mailInConsignmentVO.setCompanyCode(carditVO.getCompanyCode());
						mailInConsignmentVO.setPaCode(carditVO.getSenderId());
						mailInConsignmentVO.setOperation(MailConstantsVO.OPERATION_OUTBOUND);
						mailInCondignmentVOs.add(mailInConsignmentVO);
					}
				}
			}
		}
		return mailInCondignmentVOs;
	}

	/**
	 * @param carditVO
	 * @return
	 */
	private Collection<RoutingInConsignmentVO> collectRoutingInfo(CarditVO carditVO) {
		RoutingInConsignmentVO routingInConsignmentVO = null;
		Collection<RoutingInConsignmentVO> routingVOs = null;
		Collection<CarditTransportationVO> transportationVOs = carditVO.getTransportInformation();
		if (transportationVOs != null && transportationVOs.size() > 0) {
			routingVOs = new ArrayList<RoutingInConsignmentVO>();
			for (CarditTransportationVO transportationVO : transportationVOs) {
				if (transportationVO.getFlightNumber() != null) {
					routingInConsignmentVO = new RoutingInConsignmentVO();
					routingInConsignmentVO.setCompanyCode(carditVO.getCompanyCode());
					routingInConsignmentVO.setConsignmentNumber(carditVO.getConsignmentNumber());
					routingInConsignmentVO.setPaCode(carditVO.getSenderId());
					routingInConsignmentVO.setOnwardCarrierCode(transportationVO.getCarrierCode());
					routingInConsignmentVO.setOnwardCarrierId(transportationVO.getCarrierID());
					routingInConsignmentVO.setOnwardFlightDate(transportationVO.getDepartureTime());
					routingInConsignmentVO.setOnwardFlightNumber(transportationVO.getFlightNumber());
					routingInConsignmentVO.setOnwardCarrierSeqNum(transportationVO.getFlightSequenceNumber());
					routingInConsignmentVO.setPol(transportationVO.getDeparturePort());
					routingInConsignmentVO.setOperationFlag(RoutingInConsignmentVO.OPERATION_FLAG_INSERT);
					routingInConsignmentVO.setPou(transportationVO.getArrivalPort());
					routingInConsignmentVO.setScheduledArrivalDate(transportationVO.getArrivalDate());
					if (!MailConstantsVO.CARDITDOM.equalsIgnoreCase(carditVO.getMessageTypeId())) {
						routingInConsignmentVO
								.setTransportStageQualifier(transportationVO.getTransportStageQualifier());
					}
					routingVOs.add(routingInConsignmentVO);
				}
			}
		}
		return routingVOs;
	}

	/**
	 * @param receptacleVO
	 * @return
	 */
	private MailInConsignmentVO createMailInConsignmentVO(CarditReceptacleVO receptacleVO) {
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setDsn(receptacleVO.getDespatchNumber());
		mailInConsignmentVO.setOriginExchangeOffice(receptacleVO.getOriginExchangeOffice());
		mailInConsignmentVO.setDestinationExchangeOffice(receptacleVO.getDestinationExchangeOffice());
		mailInConsignmentVO.setMailClass(receptacleVO.getMailSubClassCode().substring(0, 1));
		mailInConsignmentVO.setMailSubclass(receptacleVO.getMailSubClassCode());
		mailInConsignmentVO.setMailCategoryCode(receptacleVO.getMailCategoryCode());
		mailInConsignmentVO.setYear(receptacleVO.getLastDigitOfYear());
		mailInConsignmentVO.setStatedBags(1);
		mailInConsignmentVO.setSealNumber(receptacleVO.getSealNumber());
		mailInConsignmentVO.setStatedWeight(receptacleVO.getReceptacleWeight());
		mailInConsignmentVO.setReceptacleSerialNumber(receptacleVO.getReceptacleSerialNumber());
		mailInConsignmentVO.setMailId(receptacleVO.getReceptacleId());
		mailInConsignmentVO.setMailbagJrnIdr(receptacleVO.getDespatchIdentification());
		mailInConsignmentVO.setHighestNumberedReceptacle(receptacleVO.getHighestNumberReceptacleIndicator());
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(receptacleVO.getRegdOrInsuredIndicator());
		mailInConsignmentVO.setReqDeliveryTime(receptacleVO.getReqDeliveryTime());
		mailInConsignmentVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);
		String displayUnit = "";
		if (MailConstantsVO.MEASUREUNITQUALIFIER_POUND.equals(receptacleVO.getMeasureUnitQualifier())) {
			displayUnit = MailConstantsVO.WEIGHTCODE_POUND;
		} else if (MailConstantsVO.MEASUREUNITQUALIFIER_KILO.equals(receptacleVO.getMeasureUnitQualifier())) {
			displayUnit = MailConstantsVO.WEIGHTCODE_KILO;
		}
		mailInConsignmentVO.setDisplayUnit(displayUnit);
		if (receptacleVO.getMailOrigin() != null && receptacleVO.getMailOrigin().length() <= 3) {
			mailInConsignmentVO.setMailOrigin(receptacleVO.getMailOrigin());
		}
		if (receptacleVO.getMailDestination() != null && receptacleVO.getMailDestination().length() <= 3) {
			mailInConsignmentVO.setMailDestination(receptacleVO.getMailDestination());
		}
		mailInConsignmentVO.setOperation(MailConstantsVO.OPERATION_OUTBOUND);
		return mailInConsignmentVO;
	}

	/**
	 * Invokes the EVT_RCR proc A-1739
	 * @param companyCode
	 * @throws SystemException
	 */
	public void invokeResditReceiver(String companyCode) {
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.info("" + "Resdit Enabled " + " " + resditEnabled);
			log.debug(CLASS + " : " + "invokeResditReceiver" + " Entering");
			new ResditController().invokeResditReceiver(companyCode);
			log.info(CLASS + " : " + "invokeResditReceiver" + " Exiting");
		}
	}

	/**
	 * @param operationalFlightVOs
	 * @throws SystemException
	 * @author A-1936
	 */
	public void flagUpliftedResditForMailbags(Collection<OperationalFlightVO> operationalFlightVOs) {
		log.debug(CLASS + " : " + "flagUpliftedResditForMailbags" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			if (operationalFlightVOs != null) {
				for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
					Collection<MailbagVO> mailBags = null;
					mailBags = MailAcceptance.findMailBagsForUpliftedResdit(operationalFlightVO);
					if (mailBags != null && mailBags.size() > 0) {
						for (MailbagVO mailbagVO : mailBags) {
							AirlineValidationVO airlineValidationVO = null;
							if (mailbagVO.getCarrierCode() == null) {
								try {
									airlineValidationVO = neoMastersServiceUtils.findAirline(mailbagVO.getCompanyCode(),mailbagVO.getCarrierId());
								} catch (SharedProxyException sharedProxyException) {
									sharedProxyException.getMessage();
								}
								mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
							}
							mailbagVO = constructOriginDestinationDetails(mailbagVO);
						}
					}
					Collection<ContainerDetailsVO> containerDetailsVOs = null;
					containerDetailsVOs = MailAcceptance.findUldsForUpliftedResdit(operationalFlightVO);
					mailController.flagResditsForFlightDeparture(operationalFlightVO.getCompanyCode(),
							operationalFlightVO.getCarrierId(), mailBags, containerDetailsVOs,
							operationalFlightVO.getPol());
				}
			}
		}
		log.debug(CLASS + " : " + "flagUpliftedResditForMailbags" + " Exiting");
	}

	/**
	 * Method		:	MailController.flagTransportCompletedResditForMailbags Added by 	: Used for 	: Parameters	:	@param operationalFlightVOs Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void flagTransportCompletedResditForMailbags(Collection<OperationalFlightVO> operationalFlightVOs) {
		log.debug(CLASS + " : " + "===============>>>flagTransportCompletedResditForMailbags " + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			if (operationalFlightVOs != null && operationalFlightVOs.size() > 0) {
				for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
					Collection<MailbagVO> mailBags = null;
					mailBags = MailAcceptance.findMailBagsForTransportCompletedResdit(operationalFlightVO);
					Collection<ContainerDetailsVO> containerDetailsVOs = null;
					containerDetailsVOs = MailAcceptance.findUldsForTransportCompletedResdit(operationalFlightVO);
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagResditsForTransportCompleted(operationalFlightVO.getCompanyCode(),
							operationalFlightVO.getCarrierId(), mailBags, containerDetailsVOs,
							operationalFlightVO.getPou(), operationalFlightVO.getPou());
					Collection<MailbagVO> mailBagsForArrival = null;
					mailBagsForArrival = MailAcceptance.findMailBagsForUpliftedResdit(operationalFlightVO);
					if (mailBagsForArrival != null && !mailBagsForArrival.isEmpty()) {
						for (MailbagVO mailbagVO : mailBagsForArrival) {
							Collection<FlightValidationVO> flightVOs = null;
							FlightFilterVO flightFilterVO = new FlightFilterVO();
							flightFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
							flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
							flightFilterVO.setPageNumber(1);
							flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
							flightFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
							flightVOs = new MailController().validateFlight(flightFilterVO);
							if (flightVOs != null && !flightVOs.isEmpty() && mailbagVO != null) {
								mailController.flagHistoryforFlightArrival(mailbagVO, flightVOs, getTriggerPoint());
								mailController.flagAuditforFlightArrival(mailbagVO, flightVOs);
							}
						}
					}
				}
			}
		}
		log.debug(CLASS + " : " + "===============>>>flagTransportCompletedResditForMailbags" + " Exiting");
	}

	/**
	 * Find the assignments of colln of containers Mar 28, 2007, a-1739
	 * @param containers
	 * @return
	 * @throws SystemException
	 */
	public Map<String, ContainerAssignmentVO> findContainerAssignments(Collection<ContainerVO> containers) {
		log.debug(CLASS + " : " + "findcontainerassgns" + " Entering");
		Map<String, ContainerAssignmentVO> containerMap = new HashMap<String, ContainerAssignmentVO>();
		if (containers != null && containers.size() > 0) {
			ContainerAssignmentVO containerAsgn = null;
			for (ContainerVO containerVO : containers) {
				containerAsgn = findContainerAssignment(containerVO);
				if (containerAsgn != null) {
					containerMap.put(containerVO.getContainerNumber(), containerAsgn);
				}
			}
		}
		return containerMap;
	}

	/**
	 * @param flightDetails
	 * @author A-2572
	 */
	public void validateULDsForOperation(FlightDetailsVO flightDetails) {
		try {
			uLDDefaultsProxy.validateULDsForOperation(flightDetails);
		} catch (ULDDefaultsProxyException ex) {
			throw new SystemException(ex.getMessage());
		}
	}

	public void resolveTransaction(String companyCode, String txnId, String remarks) {
		//TODO: Neo master service to be implemented
//		try {
//			adminMonitoringProxy.resolveTransaction(companyCode, txnId, remarks);
//		} catch (BusinessException e) {
//			log.info(e.getMessage());
//		}
	}

	/**
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 * @author a-1883
	 */
	public DocumentValidationVO validateDocumentInStock(DocumentFilterVO documentFilterVO) {
		log.debug(CLASS + " : " + "validateDocumentInStock" + " Entering");
		String product = findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs = new ArrayList<ProductValidationVO>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productVOs = neoMastersServiceUtils.findProductsByName(documentFilterVO.getCompanyCode(), product);
		if (productVOs != null) {
			productValidationVO = productVOs.iterator().next();
			productVO = productDefaultsProxy.findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		try {
			return stockcontrolDefaultsProxy.validateDocumentInStock(documentFilterVO);
		} catch (StockcontrolDefaultsProxyException e) {
			e.printStackTrace();
		}
		return new DocumentValidationVO();
	}

	/**
	 * @param awbDetailVO
	 * @param containerDetailsVO
	 * @throws SystemException
	 * @author a-1883
	 * @throws PersistenceException
	 */
	public void attachAWBDetails(AWBDetailVO awbDetailVO, ContainerDetailsVO containerDetailsVO)
			throws PersistenceException {
		log.debug(CLASS + " : " + "attachAWBDetails" + " Entering");
		RatingDetailVO ratingDetailsVO = new RatingDetailVO();
		Collection<MailbagVO> mailbagVOs = null;
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		mailbagVOs = populateMailBagVoForAttachAWB(containerDetailsVO);
		if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
			mailbagVOs = mailbagVOs.stream()
					.filter(containerMails -> containerDetailsVO.getMailDetails().stream().anyMatch(
							selectedMail -> selectedMail.getMailbagId().equals(containerMails.getMailbagId())))
					.collect(Collectors.toList());
		}
		ShipmentDetailVO shipmentDetailVO = constructShipmentFromAWBDetails(awbDetailVO, mailbagVOs);
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String shipper = mailbagVOs.iterator().next().getPaCode();
			String consignee = findPAForOfficeOfExchange(awbDetailVO.getCompanyCode(),
					mailbagVOs.iterator().next().getDoe());
			populateShipmentShipperConsignee(shipmentDetailVO, shipper, consignee, mailbagVOs);
			populateShipmentRoutingDetails(shipmentDetailVO, mailbagVOs, containerDetailsVO, mailManifestDetailsVO);
			if (MailConstantsVO.MAILOUTBOUND_SCREEN.equals(containerDetailsVO.getFromScreen())) {
				validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
			}
			shipmentDetailVO.setAgentCode(awbDetailVO.getAgentCode());
			String agentName = null, iataCode = null;
			Collection<CustomerLovVO> customerLovVOS = null;
			CustomerFilterVO customerFilterVO = new CustomerFilterVO();
			customerFilterVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
			customerFilterVO.setCustomerCode(awbDetailVO.getAgentCode());
			customerFilterVO.setPageNumber(1);
				customerLovVOS = neoMastersServiceUtils.findCustomers(customerFilterVO);
			if (customerLovVOS != null && customerLovVOS.size() > 0) {
				agentName = customerLovVOS.iterator().next().getCustomerName();
				iataCode = customerLovVOS.iterator().next().getIataCode();
			}
			shipmentDetailVO.setAgentName(agentName);
			shipmentDetailVO.setIataCode(iataCode);
			ShipmentValidationVO shipmentValidationVO = operationsShipmentUtil.saveShipmentDetails(shipmentDetailVO);
			mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
			mailManifestDetailsVO.setShipmentDetailVO(shipmentDetailVO);
			mailManifestDetailsVO.setShipmentValidationVO(shipmentValidationVO);
			findManifestDetails(containerDetailsVO, mailManifestDetailsVO);
			if (containerDetailsVO.getMailDetails() == null || containerDetailsVO.getMailDetails().isEmpty()) {
				containerDetailsVO.setMailDetails(mailbagVOs);
			}
			Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
			containerDetailsVOs.add(containerDetailsVO);
			mailManifestDetailsVO.setContainerDetailsVOs(containerDetailsVOs);
			mailManifestDetailsVO.setFromAttachAWB(true);
			autoAttachAWBDetailsFeature.execute(mailManifestDetailsVO);
		}
		ratingDetailsVO.setGrossWeight(shipmentDetailVO.getStatedWeight());
		ratingDetailsVO.setGrossWeightCodeMIP(MailConstantsVO.WEIGHT_CODE);
		log.debug(CLASS + " : " + "attachAWBDetails" + " Exiting");
	}

	/**
	 * @author A-4809
	 * @param containerDetailsVO
	 * @param mailManifestDetailsVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private MailManifestDetailsVO findManifestDetails(ContainerDetailsVO containerDetailsVO,
													  MailManifestDetailsVO mailManifestDetailsVO) throws PersistenceException {
		OperationalFlightVO flightVO = new OperationalFlightVO();
		flightVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		flightVO.setCarrierId(containerDetailsVO.getCarrierId());
		flightVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		flightVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		flightVO.setPol(containerDetailsVO.getPol());
		MailManifestVO mailManifestVO = findContainersInFlightForManifest(flightVO);
		mailManifestDetailsVO.setFromAttachContainerVOs(mailManifestVO.getContainerDetails());
		for (ContainerDetailsVO contvo : mailManifestDetailsVO.getFromAttachContainerVOs()) {
			if (contvo.getDsnVOs() != null && !contvo.getDsnVOs().isEmpty()) {
				Collection<MailbagVO> mailbagVOs = constructDAO().findMailbagVOsForDsnVOs(contvo);
				contvo.setMailDetails(mailbagVOs);
			}
		}
		return mailManifestDetailsVO;
	}

	/**
	 * constructShipmentFromAWBDetails
	 * @param awbDetailVO
	 * @param mailbagVOs
	 * @return
	 * @throws SystemException
	 */
	private ShipmentDetailVO constructShipmentFromAWBDetails(AWBDetailVO awbDetailVO,
															 Collection<MailbagVO> mailbagVOs) {
		MeasureMapper measureMapper =ContextUtil.getInstance().getBean(MeasureMapper.class);
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(awbDetailVO.getCompanyCode());
		shipmentDetailVO.setOwnerId(awbDetailVO.getOwnerId());
		shipmentDetailVO.setOwnerCode(awbDetailVO.getOwnerCode());
		shipmentDetailVO.setMasterDocumentNumber(awbDetailVO.getMasterDocumentNumber());
		shipmentDetailVO.setDuplicateNumber(awbDetailVO.getDuplicateNumber());
		shipmentDetailVO.setSequenceNumber(awbDetailVO.getSequenceNumber());
		shipmentDetailVO.setOperationFlag(awbDetailVO.getOperationFlag());
		shipmentDetailVO.setOrigin(awbDetailVO.getOrigin());
		shipmentDetailVO.setDestination(awbDetailVO.getDestination());
		shipmentDetailVO.setStatedPieces(awbDetailVO.getStatedPieces());
		shipmentDetailVO.setStatedWeight(measureMapper.toMeasure(awbDetailVO.getStatedWeight()));
		shipmentDetailVO.setStatedVolume(measureMapper.toMeasure(awbDetailVO.getStatedVolume()));
		shipmentDetailVO.setTotalVolume(measureMapper.toMeasure(awbDetailVO.getStatedVolume()));
		shipmentDetailVO.setTotalAcceptedPieces(awbDetailVO.getStatedPieces());
		shipmentDetailVO.setTotalAcceptedWeight(measureMapper.toMeasure(awbDetailVO.getStatedWeight()));
		shipmentDetailVO.setDisplayedWeight(measureMapper.toMeasure(awbDetailVO.getStatedWeight()));
		shipmentDetailVO.setGrossStatedVolume(measureMapper.toMeasure(awbDetailVO.getStatedVolume()));
		shipmentDetailVO.setGrossDisplayedVolume(measureMapper.toMeasure(awbDetailVO.getStatedVolume()));
		shipmentDetailVO.setStatedWeightCode(awbDetailVO.getStatedWeightCode());
		shipmentDetailVO.setDateOfJourney(new com.ibsplc.icargo.framework.util.time.LocalDate(
				"***",Location.NONE,true));
		log.debug("" + "Stated weight :" + " " + shipmentDetailVO.getStatedWeight());
		shipmentDetailVO.setShipmentDescription(awbDetailVO.getShipmentDescription());
		shipmentDetailVO.setShipmentPrefix(awbDetailVO.getShipmentPrefix());
		String product = findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
		shipmentDetailVO.setProduct(product);
		shipmentDetailVO.setScc(findSystemParameterValue(MailConstantsVO.ATTACH_AWB_SCC_CODE));
		shipmentDetailVO.setSourceIndicator(MailConstantsVO.MAIL_AWB_SOURCE);
		shipmentDetailVO.setServiceCargoClass(MailConstantsVO.MAIL_SERVICE_CARGO_CLASS);
		Collection<RatingDetailVO> ratingDetails = new ArrayList<RatingDetailVO>();
		RatingDetailVO ratingDetailsVO = new RatingDetailVO();
		String commodityCode = findSystemParameterValue(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		String poaCode = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			poaCode = mailbagVOs.iterator().next().getPaCode();
		}
		log.debug("" + "pacode" + " " + poaCode);
		CommodityValidationVO commodityValidationVo = validateCommodity(shipmentDetailVO.getCompanyCode(),
				commodityCode, poaCode);
		ratingDetailsVO.setCommodityName(commodityCode);
		ratingDetailsVO.setOperationFlag(RatingDetailVO.OPERATION_FLAG_UPDATE);
		ratingDetailsVO.setGrossWeight(measureMapper.toMeasure(awbDetailVO.getStatedWeight()));
		ratingDetailsVO.setGrossVolume(measureMapper.toMeasure(awbDetailVO.getStatedVolume()));
		ratingDetailsVO.setPieces(awbDetailVO.getStatedPieces());
		ratingDetailsVO.setRateLineSerialNumber(1);
		if (commodityValidationVo != null) {
			ratingDetailsVO.setDescription(commodityValidationVo.getCommodityDesc());
		}
		ratingDetails.add(ratingDetailsVO);
		shipmentDetailVO.setRatingDetails(ratingDetails);
		Collection<RoutingVO> routingDetails = new ArrayList<RoutingVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String ownCarrierCode = logonAttributes.getOwnAirlineCode();
		RoutingVO routingVO = new RoutingVO();
		String destination = awbDetailVO.getDestination();
		routingVO.setDestination(destination);
		routingVO.setCarrierCode(ownCarrierCode);
		routingVO.setAirportCode(destination);
		routingDetails.add(routingVO);
		shipmentDetailVO.setRoutingDetails(routingDetails);
		shipmentDetailVO.setOverrideCertificateValidations("N");
		shipmentDetailVO.setSciValidationToBeSkipped(true);
		shipmentDetailVO.setDocumentSubType(awbDetailVO.getDocumentSubType());
		return shipmentDetailVO;
	}

	/**
	 * populateShipmentShipperConsignee
	 * @param shipmentDetailVO
	 * @param shipper
	 * @param consignee
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	private void populateShipmentShipperConsignee(ShipmentDetailVO shipmentDetailVO, String shipper, String consignee,
												  Collection<MailbagVO> mailbagVOs) {
		Collection<CustomerVO> shipperCustomerDetails = null;
		Collection<CustomerVO> consigneeCustomerDetails = null;
		Collection<String> customerPreferences = new ArrayList<>();
		customerPreferences.add(MailConstantsVO.CUSTOMS_POSTAL_AUTHORITY_CODE_PRFCOD);
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
		customerFilterVO.setCustomerType(MailConstantsVO.CUSTOMER_TYPE_GPA);
		customerFilterVO.setStationCode(mailbagVOs.iterator().next().getOrigin());
		customerFilterVO.setPageNumber(1);
		customerFilterVO.setCustomerPreferenceCodes(customerPreferences);
		shipperCustomerDetails = neoMastersServiceUtils.getAllCustomerDetails(customerFilterVO);
		customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
		customerFilterVO.setCustomerType(MailConstantsVO.CUSTOMER_TYPE_GPA);
		customerFilterVO.setStationCode(mailbagVOs.iterator().next().getDestination());
		customerFilterVO.setPageNumber(1);
		customerFilterVO.setCustomerPreferenceCodes(customerPreferences);
		consigneeCustomerDetails = neoMastersServiceUtils.getAllCustomerDetails(customerFilterVO);
		Collection<CustomerVO> shipperDetailsForCustoms = getCustomerForCustoms(shipper, shipperCustomerDetails);
		if (shipperDetailsForCustoms != null && shipperDetailsForCustoms.size() > 0) {
			shipmentDetailVO.setShipperCode(shipperDetailsForCustoms.iterator().next().getCustomerCode());
			shipmentDetailVO.setShipperName(shipperDetailsForCustoms.iterator().next().getCustomerName());
			shipmentDetailVO.setShipperAddress1(shipperDetailsForCustoms.iterator().next().getAddress1());
			shipmentDetailVO.setShipperCity(shipperDetailsForCustoms.iterator().next().getCity());
			shipmentDetailVO.setShipperCountry(shipperDetailsForCustoms.iterator().next().getCountry());
			shipmentDetailVO.setShipperAccountNumber(shipperDetailsForCustoms.iterator().next().getAccountNumber());
			shipmentDetailVO.setConsigneeTelephoneNumber(shipperDetailsForCustoms.iterator().next().getTelephone());
			shipmentDetailVO.setShipperEmailId(shipperDetailsForCustoms.iterator().next().getEmail());
			shipmentDetailVO.setShipperState(shipperDetailsForCustoms.iterator().next().getState());
			shipmentDetailVO.setShipperPostalCode(shipperDetailsForCustoms.iterator().next().getZipCode());
			shipmentDetailVO.setOrigin(mailbagVOs.iterator().next().getMailOrigin());
			shipmentDetailVO.setDestination(mailbagVOs.iterator().next().getMailDestination());
		}
		Collection<CustomerVO> consigneeDetailsForCustoms = getCustomerForCustoms(consignee, consigneeCustomerDetails);
		if (consigneeDetailsForCustoms != null && consigneeDetailsForCustoms.size() > 0) {
			shipmentDetailVO.setConsigneeCode(consigneeDetailsForCustoms.iterator().next().getCustomerCode());
			shipmentDetailVO.setConsigneeName(consigneeDetailsForCustoms.iterator().next().getCustomerName());
			shipmentDetailVO.setConsigneeAddress1(consigneeDetailsForCustoms.iterator().next().getAddress1());
			shipmentDetailVO.setConsigneeCity(consigneeDetailsForCustoms.iterator().next().getCity());
			shipmentDetailVO.setConsigneeCountry(consigneeDetailsForCustoms.iterator().next().getCountry());
			shipmentDetailVO.setConsigneeAccountNumber(consigneeDetailsForCustoms.iterator().next().getAccountNumber());
			shipmentDetailVO.setConsigneeTelephoneNumber(consigneeDetailsForCustoms.iterator().next().getTelephone());
			shipmentDetailVO.setConsigneeEmailId(consigneeDetailsForCustoms.iterator().next().getEmail());
			shipmentDetailVO.setConsigneeState(consigneeDetailsForCustoms.iterator().next().getState());
			shipmentDetailVO.setConsigneePostalCode(consigneeDetailsForCustoms.iterator().next().getZipCode());
		}
		shipmentDetailVO.setAWBDataCaptureDone(true);
	}

	/**
	 * This method deletes document from stock
	 * @param documentFilterVO
	 * @throws SystemException
	 * @author a-1883
	 */
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws StockcontrolDefaultsProxyException, SystemException{
		log.debug(CLASS + " : " + "deleteDocumentFromStock" + " Entering");
		String product = findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs = new ArrayList<ProductValidationVO>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productVOs = neoMastersServiceUtils.findProductsByName(documentFilterVO.getCompanyCode(), product);
		if (productVOs != null) {
			productValidationVO = productVOs.iterator().next();
			productVO = productDefaultsProxy.findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		stockcontrolDefaultsProxy.deleteDocumentFromStock(documentFilterVO);
		log.debug(CLASS + " : " + "deleteDocumentFromStock" + " Exiting");
	}

	/**
	 * @param documentFilterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 * @throws StockcontrolDefaultsProxyException
	 * @author a-1883
	 */
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws StockcontrolDefaultsProxyException {
		log.debug(CLASS + " : " + "findNextDocumentNumber" + " Entering");
		return stockcontrolDefaultsProxy.findNextDocumentNumber(documentFilterVO);
	}

	/**
	 * @return
	 * @throws SystemException
	 * @author A-5183 For ICRD-42572
	 */
	public void updateActualWeightForMailULD(ContainerVO containerVo) {
		ContainerPK containerPk = new ContainerPK();
		Container container = null;
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		try {
			container = Container.find(containerPk);
			if (container != null) {
				container.setActualWeight(containerVo.getActualWeight().getValue().doubleValue());
				container.setActualWeightDisplayValue(containerVo.getActualWeight().getDisplayValue().doubleValue());
				container.setActualWeightDisplayUnit(containerVo.getActualWeight().getDisplayUnit().getName());
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/**
	 * @param companyCode
	 * @param carrierId
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @param eventPort
	 * @throws SystemException
	 */
	public void flagResditsForFlightDeparture(String companyCode, int carrierId, Collection<MailbagVO> mailbagVOs,
											  Collection<ContainerDetailsVO> containerDetailsVOs, String eventPort) {
		log.debug(CLASS + " : " + "flagResditsForFlightDeparture" + " Entering");
		String key = String.format("%s-%s",companyCode,carrierId,eventPort);
		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbagVOs);
		Collection<ContainerDetailsModel>containerDetailsModels = mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(containerDetailsVOs);
		FlightDepartureResditEvent flightDepartureResditEvent =
				mailEventsMapper.constructFlightDepartureResditEvent(
						companyCode,carrierId,mailbagModels,containerDetailsModels,eventPort);
		mailEventsProducer. publishEvent(key,flightDepartureResditEvent);
		log.debug(CLASS + " : " + "flagResditsForFlightDeparture" + " Exiting");
	}
    /**
	 * @param companyCode
	 * @param carrierId
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @param eventPort
	 * @param flightArrivedPort
	 * @throws SystemException
	 */
	public void flagResditsForTransportCompleted(String companyCode, int carrierId, Collection<MailbagVO> mailbagVOs,
												 Collection<ContainerDetailsVO> containerDetailsVOs, String eventPort, String flightArrivedPort) {
		log.debug(CLASS + " : " + "flagResditsForTransportCompleted" + " Entering");
		String key = String.format("%s-%s",companyCode,carrierId,eventPort,flightArrivedPort);

		Collection<MailbagModel>mailbagModels =mailOperationsMapper.mailbagVOsToMailbagModels(mailbagVOs);
		Collection<ContainerDetailsModel>containerDetailsModels = mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(containerDetailsVOs);
		TransportCompletedResditEvent transportEvent =
				mailEventsMapper.constructTransportCompletedResditEvent(
						companyCode,carrierId,mailbagModels,containerDetailsModels,eventPort,flightArrivedPort);
		mailEventsProducer. publishEvent(key,transportEvent);
		log.debug(CLASS + " : " + "flagResditsForTransportCompleted" + " Exiting");
	}
	/**
	 * @param unassignSBULDs
	 * @throws SystemException
	 */
	public void flagReturnedResditForULDs(Collection<ContainerDetailsVO> unassignSBULDs) {
		log.debug(CLASS + " : " + "flagReturnedResditForULDs" + " Entering");
		log.debug(CLASS + " : " + "flagReturnedResditForULDs" + " Exiting");
	}

	/**
	 * @param operationalFlightVOs
	 * @throws SystemException
	 */
	public void flagMLDForUpliftedMailbags(Collection<OperationalFlightVO> operationalFlightVOs) {
		log.debug(CLASS + " : " + "flagMLDForUpliftedMailbags" + " Entering");
		asyncInvoker.invoke(() -> messageBuilder.flagMLDForUpliftedMailbags(operationalFlightVOs));
		log.debug(CLASS + " : " + "flagMLDForUpliftedMailbags" + " Exiting");
	}
	public void flagHistoryforTransferRejection(MailbagVO mailbagVO, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryforTransferRejection" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryforTransferRejection(mailbagVO, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryforTransferRejection" + " Exiting");
	}
	/**
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @author a-1936 This method is used to find out the Mail Bags and theDespacthes in a Container of a Manifested Flight.
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsVO> containers) {
		return AssignedFlightSegment.findMailbagsInContainerForImportManifest(containers);
	}

	/**
	 * This method is used to find all the mail hand list codes A-6371
	 * @param pageNumber
	 * @return Page of officeExchangeVOs
	 * @throws SystemException
	 */
	public Page<MailOnHandDetailsVO> findMailOnHandDetails(SearchContainerFilterVO searchContainerFilterVO,
														   int pageNumber) {
		log.debug(CLASS + " : " + "findMailOnHandDetails" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		Collection<PartnerCarrierVO> partnerCarrierVOs = null;
		ArrayList<String> partnerCarriers = new ArrayList<String>();
		String companyCode = logonAttributes.getCompanyCode();
		String ownCarrierCode = logonAttributes.getOwnAirlineCode();
		String airportCode = logonAttributes.getAirportCode();
		try {
			MailOperationsDAO mailOperationsDAO = MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MODULENAME));
			partnerCarrierVOs = mailOperationsDAO.findAllPartnerCarriers(companyCode, ownCarrierCode,
					airportCode);
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getErrorCode());
		}
		log.debug("" + " partnerCarriers " + " " + partnerCarrierVOs);
		if (partnerCarrierVOs != null && partnerCarrierVOs.size() > 0) {
			for (PartnerCarrierVO partner : partnerCarrierVOs) {
				String partnerCarrier = partner.getPartnerCarrierCode();
				partnerCarriers.add(partnerCarrier);
			}
			partnerCarriers.add(ownCarrierCode);
			searchContainerFilterVO.setPartnerCarriers(partnerCarriers);
		}
		try {
			MailOperationsDAO mailOperationsDAO = MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MODULENAME));
			return mailOperationsDAO.findMailOnHandDetails(searchContainerFilterVO, pageNumber);
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getErrorCode());
		}
	}

	/**
	 * TODO Purpose Jan 30, 2007, A-1739
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public MailManifestVO findMailAWBDetails(OperationalFlightVO operationalFlightVO) {
		log.debug(CLASS + " : " + "findMailAWBDetails" + " Entering");
		return MailAcceptance.findMailAWBManifest(operationalFlightVO);
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws CloseFlightException
	 * @throws ULDDefaultsProxyException
	 * @throws RemoteException
	 */
	public void closeMailExportFlight(OperationalFlightVO operationalFlightVO)
			throws MailOperationsBusinessException, ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeMailExportFlight" + " Entering");
		MailManifestVO mailManifestVO = this.findContainersInFlightForManifest(operationalFlightVO);
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			AssignedFlight.find(assignedFlightPk);
			this.closeFlightManifest(operationalFlightVO, mailManifestVO);
		} catch (FinderException ex) {
			AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
			assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
			assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
			assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
			assignedFlightVO.setFlightStatus("O");
			LoginProfile logonVO = contextUtil.callerLoginProfile();
			assignedFlightVO.setLastUpdateTime(
					localDateUtil.getLocalDate(logonVO.getAirportCode(), true));
			assignedFlightVO.setLastUpdateUser(logonVO.getUserId());
			new AssignedFlight(assignedFlightVO);
			closeFlightWithoutMails(operationalFlightVO);
		}
		log.debug(CLASS + " : " + "closeMailExportFlight" + " Exiting");
	}

	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws CloseFlightException
	 * @throws ULDDefaultsProxyException
	 * @throws RemoteException
	 */
	public void closeMailImportFlight(OperationalFlightVO operationalFlightVO)
			throws MailOperationsBusinessException, ULDDefaultsProxyException, CloseFlightException {
		log.debug(CLASS + " : " + "closeMailImportFlight" + " Entering");
		LoginProfile logon = contextUtil.callerLoginProfile();
		Collection<String> sysParameters = new ArrayList<String>();
		sysParameters.add(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT);
		Map<String, String> sysParameterMap = null;
		try {
			sysParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(sysParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		boolean canCloseFlight = true;
		if (sysParameterMap != null && OperationalFlightVO.FLAG_YES
				.equals(sysParameterMap.get(MailConstantsVO.CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT))) {
			MailArrivalFilterVO filterVO = InboundFlight.constructMailArrivalFilterVO(operationalFlightVO);
			MailArrivalVO mailArrivalVO = this.findArrivalDetails(filterVO);
			Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
			if (containerDetails != null && containerDetails.size() > 0) {
				for (ContainerDetailsVO containerDtls : containerDetails) {
					if (containerDtls.getDsnVOs() != null && containerDtls.getDsnVOs().size() > 0) {
						for (DSNVO dSNVO : containerDtls.getDsnVOs()) {
							if (MailConstantsVO.FLAG_NO.equals(dSNVO.getRoutingAvl())) {
								canCloseFlight = false;
								break;
							}
						}
					}
					if (!canCloseFlight) {
						break;
					}
				}
			}
			if (!canCloseFlight) {
				throw new CloseFlightException(CloseFlightException.ROUTING_UNAVAILABLE);
			}
		}
		AssignedFlight inboundFlight = null;
		AssignedFlightPK inboundFlightPk = new AssignedFlightPK();
		inboundFlightPk.setAirportCode(operationalFlightVO.getPou());
		inboundFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		inboundFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		inboundFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		inboundFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		inboundFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			inboundFlight = AssignedFlight.find(inboundFlightPk);
		} catch (FinderException ex) {
			AssignedFlightVO inboundFlightVO = new AssignedFlightVO();
			inboundFlightVO.setAirportCode(operationalFlightVO.getPou());
			inboundFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
			inboundFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
			inboundFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
			inboundFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
			inboundFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			inboundFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			inboundFlightVO.setLastUpdateTime(
					localDateUtil.getLocalDate(logon.getAirportCode(),true));
			inboundFlightVO.setLastUpdateUser(logon.getUserId());
			inboundFlightVO.setFlightStatus("N");
			inboundFlight = new AssignedFlight(inboundFlightVO);
		}
		closeInboundFlight(operationalFlightVO);
	}
	/**
	 * @param mailAuditFilterVO
	 * @return
	 * @throws SystemException ICRD-229934
	 * @author a-7794 This method is used to list the Audit details
	 */
	public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO) {
		log.debug(CLASS + " : " + "findCONAuditDetails" + " Entering");
		//TODO: To implement audit in Neo
		//return ContainerAudit.findCONAuditDetails(mailAuditFilterVO);
		return null;
	}

	/**
	 * Method		:	MailController.findMailDetailsForMailTag Added by 	:	a-6245 on 07-Jun-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailId Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	MailbagVO
	 */
	public MailbagVO findMailDetailsForMailTag(String companyCode, String mailId) {
		log.debug(CLASS + " : " + "findMailDetailsForMailTag" + " Entering");
		Mailbag mailbag = null;
		MailbagVO mailbagVO = new MailbagVO();
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(companyCode);
		mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailId, companyCode));
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException finderException) {
			log.debug(" ++++  Finder Exception  +++");
			log.debug(" <===  Mailbag is Not accepted ===>");
		}
		if (mailbag != null) {
			mailbagVO.setPaCode(mailbag.getPaCode());
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setMailbagId(mailbag.getMailIdr());
			mailbagVO.setMailSequenceNumber(mailbagPk.getMailSequenceNumber());
			mailbagVO.setOoe(mailbag.getOrginOfficeOfExchange());
			mailbagVO.setDoe(mailbag.getDestinationOfficeOfExchange());
			mailbagVO.setMailCategoryCode(mailbag.getMailCategory());
			mailbagVO.setMailSubclass(mailbag.getMailSubClass());
			mailbagVO.setYear(mailbag.getYear());
			mailbagVO.setDespatchSerialNumber(mailbag.getDespatchSerialNumber());
			mailbagVO.setReceptacleSerialNumber(mailbag.getReceptacleSerialNumber());
			mailbagVO.setHighestNumberedReceptacle(mailbag.getHighestNumberedReceptacle());
			mailbagVO.setRegisteredOrInsuredIndicator(mailbag.getRegisteredOrInsuredIndicator());
			mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(mailbag.getWeight()),
					BigDecimal.valueOf(mailbag.getWeight()), "K"));
			mailbagVO.setVolume(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(mailbag.getVolume()),
					BigDecimal.valueOf(mailbag.getWeight()), "K"));
			mailbagVO.setOrigin(mailbag.getOrigin());
			mailbagVO.setDestination(mailbag.getDestination());
			mailbagVO.setMalseqnum(mailbagPk.getMailSequenceNumber());
		}
		log.debug(CLASS + " : " + "findMailDetailsForMailTag" + " Exiting");
		return mailbagVO;
	}

	/**
	 * Method		:	MailController.findMailbagIdForMailTag Added by 	:	a-6245 on 22-Jun-2017 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	MailbagVO
	 */
	public MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO) {
		return Mailbag.findMailbagIdForMailTag(mailbagVO);
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		return Mailbag.findMailStatusDetails(mailbagEnquiryFilterVO);
	}

	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs) {
		return flightOperationsProxy.validateFlightsForAirport(flightFilterVOs);
	}
	/**
	 * @param consignmentDocumentVO
	 * @param carditVO
	 */
	private void updateRecpVOwithMalSeq(ConsignmentDocumentVO consignmentDocumentVO, CarditVO carditVO) {
		Collection<CarditReceptacleVO> rcptVOs = carditVO.getReceptacleInformation();
		Page<MailInConsignmentVO> mailsInCSG = consignmentDocumentVO.getMailInConsignmentVOs();
		if (rcptVOs != null && mailsInCSG != null && !rcptVOs.isEmpty() && !mailsInCSG.isEmpty()) {
			for (CarditReceptacleVO rcptVO : rcptVOs) {
				String rcptId = rcptVO.getReceptacleId() != null ? rcptVO.getReceptacleId() : "";
				for (MailInConsignmentVO mailCSGVO : mailsInCSG) {
					if (rcptId.equals(mailCSGVO.getMailId())) {
						rcptVO.setMailSeqNum(mailCSGVO.getMailSequenceNumber());
					}
				}
			}
		}
	}
	/**
	 * @author A-7871for ICRD-257316
	 */
	public int findMailbagcountInContainer(ContainerVO containerVO) throws PersistenceException {
		log.debug(CLASS + " : " + "findMailbagcountInContainer" + " Entering");
		return constructDAO().findMailbagcountInContainer(containerVO);
	}
	/**
	 * Method		:	MailController.dettachMailBookingDetails Added by 	:	a-7779 on 29-Aug-2017 Used for 	: Parameters	:	@param mailbagVOs Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs) {
		if (!mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_AWB_CANCELLED);
				Mailbag mailbag = null;
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(),
						mailbagVO.getCompanyCode()));
				try {
					mailbag = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					throw new SystemException(e.getErrorCode(), e.getMessage(), e);
				}
				mailbagVO.setMailSequenceNumber(mailbagPk.getMailSequenceNumber());
				mailbag.detachAwbInMailbag(mailbagVO);
			}
		}
	}

	public void releasingMailsForULDAcquittanceForProxy(MailArrivalVO mailArrivalVO,
														OperationalFlightVO operationalFlightVO) {
//TODO: Neo to correct below
		//		try {
//			mailtrackingDefaultsProxy.releasingMailsForULDAcquittance(mailArrivalVO, operationalFlightVO);
//		} finally {
//		}
	}

	/**
	 * Method		:	MailController.createMailbagsMap Added by 	:	U-1267 on Nov 1, 2017 Used for 	:	ICRD-211205 Parameters	:	@param containerDetailsVOs Parameters	:	@param operationalFlightVO Parameters	:	@return Return type	: 	HashMap<String,Collection<MailbagVO>>
	 */
	private HashMap<String, Collection<MailbagVO>> createMailbagsMap(Collection<ContainerDetailsVO> containerDetailsVOs,
																	 OperationalFlightVO operationalFlightVO) {
		HashMap<String, Collection<MailbagVO>> mailbagsMap = null;
		HashMap<String, Collection<MailbagVO>> newMailbagsMap = new HashMap<>();
		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
			Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
			Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				mailbagsMap = groupMailbagVOsForAutoAttach(mailbagVOs, operationalFlightVO);
				for (Map.Entry<String, Collection<MailbagVO>> entry : mailbagsMap.entrySet()) {
					if (!newMailbagsMap.containsKey(entry.getKey())) {
						newMailbagsMap.put(entry.getKey(), entry.getValue());
					} else {
						newMailbagsMap.get(entry.getKey()).addAll(entry.getValue());
					}
				}
			} else if (dsnVOs != null && dsnVOs.size() > 0) {
				try {
					mailbagVOs = constructDAO().findMailbagVOsForDsnVOs(containerDetailsVO);
				} catch (PersistenceException exception) {
					throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
				}
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					mailbagsMap = groupMailbagVOsForAutoAttach(mailbagVOs, operationalFlightVO);
					for (Map.Entry<String, Collection<MailbagVO>> entry : mailbagsMap.entrySet()) {
						if (!newMailbagsMap.containsKey(entry.getKey())) {
							newMailbagsMap.put(entry.getKey(), entry.getValue());
						} else {
							newMailbagsMap.get(entry.getKey()).addAll(entry.getValue());
						}
					}
				}
				containerDetailsVO.setMailDetails(mailbagVOs);
			}
		}
		return newMailbagsMap;
	}

	/**
	 * Method		:	MailController.groupMailbagVOsForAutoAttach Added by 	:	U-1267 on 08-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param mailbagVOs Parameters	:	@param operationalFlightVO Parameters	:	@return Return type	: 	HashMap<String,Collection<MailbagVO>>
	 */
	private HashMap<String, Collection<MailbagVO>> groupMailbagVOsForAutoAttach(Collection<MailbagVO> mailbagVOs,
																				OperationalFlightVO operationalFlightVO) {
		HashMap<String, Collection<MailbagVO>> mailbagsMap = new HashMap<>();
		Collection<MailbagVO> newMailbagVOs = null;
		String key = null;
		for (MailbagVO mailbagVO : mailbagVOs) {
			StringBuilder sb = new StringBuilder();
			if (!MailConstantsVO.OPERATION_OUTBOUND.equals(operationalFlightVO.getDirection())) {
				key = sb.append(mailbagVO.getOoe()).append(HYPHEN).append(mailbagVO.getDoe()).append(HYPHEN).append(
								isNotNullAndEmpty(mailbagVO.getConsignmentNumber()) ? mailbagVO.getConsignmentNumber() : "")
						.append(HYPHEN).append(mailbagVO.getConsignmentSequenceNumber()).toString().trim();
			} else {
				key = sb.append(mailbagVO.getOoe()).append(HYPHEN).append(mailbagVO.getDoe()).append(HYPHEN)
						.append(mailbagVO.getPou()).append(HYPHEN)
						.append(isNotNullAndEmpty(mailbagVO.getConsignmentNumber()) ? mailbagVO.getConsignmentNumber()
								: "")
						.append(HYPHEN).append(mailbagVO.getConsignmentSequenceNumber()).toString().trim();
			}
			if ((!mailbagsMap.containsKey(key)) && (mailbagVO.getDocumentNumber() == null)) {
				newMailbagVOs = new ArrayList<MailbagVO>();
				newMailbagVOs.add(mailbagVO);
				mailbagsMap.put(key, newMailbagVOs);
			} else if (mailbagVO.getDocumentNumber() == null) {
				mailbagsMap.get(key).add(mailbagVO);
			}
		}
		return mailbagsMap;
	}

	/**
	 * Method		:	MailController.detachAWBDetails Added by 	:	U-1267 on 09-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param containerDetailsVO Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void detachAWBDetails(ContainerDetailsVO containerDetailsVO) {
		MeasureMapper measureMapper =ContextUtil.getInstance().getBean(MeasureMapper.class);
		Collection<MailbagVO> mailbagVOs = null;
		Boolean isMailAsAWB = false;
		if (containerDetailsVO != null) {
			try {
				mailbagVOs = constructDAO().findMailbagVOsForDsnVOs(containerDetailsVO);
			} catch (PersistenceException exception) {
				throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
			}
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				isMailAsAWB = constructDAO().isMailAsAwb(mailbagVOs.iterator().next());
				if (isMailAsAWB) {
					throw new SystemException(MAILAWB_STATUS_DETACH);
				}
				try {
					dettachMailBookingDetails(mailbagVOs);
				} finally {
				}
				Iterator<MailbagVO> iterator = mailbagVOs.iterator();
				while (iterator.hasNext()) {
					MailbagVO mailbagVOsForDetach = iterator.next();
					String documentNumber = mailbagVOsForDetach.getDocumentNumber();
					if (documentNumber == null || documentNumber.trim().isEmpty()) {
						iterator.remove();
					}
				}
			}
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			MailbagVO mailbagVO = ((ArrayList<MailbagVO>) mailbagVOs).get(0);
			ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
			shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
			shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
			shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
			shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
			shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
			ShipmentDetailVO shipmentDetailVO = operationsShipmentUtil.findShipmentDetails(shipmentDetailFilterVO);
			int shipmentPcs = shipmentDetailVO.getStatedPieces();
			int dettachedPcs = mailbagVOs.size();
			int remainingPcs = shipmentPcs - dettachedPcs;
			if (remainingPcs == 0) {
				ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
				shipmentValidationVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
				shipmentValidationVO.setAirportCode(logonAttributes.getAirportCode());
				shipmentValidationVO.setOrigin(shipmentDetailVO.getOrigin());
				shipmentValidationVO.setDocumentNumber(shipmentDetailVO.getMasterDocumentNumber());
				shipmentValidationVO.setDuplicateNumber(shipmentDetailVO.getDuplicateNumber());
				shipmentValidationVO.setMasterDocumentNumber(shipmentDetailVO.getMasterDocumentNumber());
				shipmentValidationVO.setOwnerId(shipmentDetailVO.getOwnerId());
				shipmentValidationVO.setSequenceNumber(shipmentDetailVO.getSequenceNumber());
				shipmentValidationVO.setShipmentPrefix(shipmentDetailVO.getShipmentPrefix());
				shipmentValidationVO.setShipmentStatus(shipmentDetailVO.getShipmentStatus());
				//this.log.log(3, new Object[] { "shipmentValidationVO ---> ", shipmentValidationVO });
				try {
					operationsShipmentUtil.deleteAWB(shipmentValidationVO, "AWB");
				} catch (BusinessException e) {
					log.debug("deleteAWB Exception" + e.getMessage());
				}
			} else {
				shipmentDetailVO.setStatedPieces(remainingPcs);
				double shipmentWeight = shipmentDetailVO.getStatedWeight().getDisplayValue();
				double dettachedWeight = 0;
				for (MailbagVO vo : mailbagVOs) {
					dettachedWeight = dettachedWeight + vo.getWeight().getDisplayValue().doubleValue();
				}
				double remainingWgt = shipmentWeight - dettachedWeight;
				Quantity remainingWgtMeasure = quantities.getQuantity(Quantities.WEIGHT,
						BigDecimal.valueOf(remainingWgt), BigDecimal.valueOf(0),
						shipmentDetailVO.getStatedWeight().getDisplayUnit());
				shipmentDetailVO.setStatedWeight(measureMapper.toMeasure(remainingWgtMeasure));
				shipmentDetailVO.setOperationFlag(ShipmentDetailVO.OPERATION_FLAG_UPDATE);
				operationsShipmentUtil.saveShipmentDetails(shipmentDetailVO);
			}
		}
	}

	/**
	 * Method		:	MailController.transferContainersAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param printFlag
	 * @return
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 * @throws InvalidFlightSegmentException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 * @throws MailDefaultStorageUnitException
	 * @throws FlightClosedException
	 */
	public TransferManifestVO transferContainersAtExport(Collection<ContainerVO> containerVOs,
														 OperationalFlightVO operationalFlightVO, String printFlag) throws ContainerAssignmentException,
			InvalidFlightSegmentException, ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, FlightClosedException, MailDefaultStorageUnitException {
		log.debug(CLASS + " : " + "transferContainers" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsCollection = null;
		ContainerVO containerVo = null;
		TransferManifestVO transferManifestVO = new TransferManifestVO();
		Map<String, Object> transferMap = null;
		if (containerVOs != null && containerVOs.size() > 0) {
			containerVo = new ArrayList<ContainerVO>(containerVOs).get(0);
		}
		Collection<ContainerVO> containerVOsForTransferOut = new ArrayList<>();
		Collection<ContainerVO> containerVOsForTransfer = new ArrayList<>();
		updateContainerForTransfer(containerVOs, operationalFlightVO, containerVOsForTransferOut,
				containerVOsForTransfer);
		if (!containerVOsForTransferOut.isEmpty()) {
			transferMap = new HashMap<>();
			transferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
					createContainerDtlsForTransfermanifest(containerVOsForTransferOut));
		}
		if (checkForContainerTransferOutStatus(operationalFlightVO.getCarrierCode())
				&& (mailOperationsTransferTransaction())) {
			operationalFlightVO.setTransferStatus(true);
			transferManifestVO.setStatus(MAIL_OPS_TRAEND);
			operationalFlightVO.setCstatus(true);
			reassignContainers(containerVOsForTransferOut, operationalFlightVO);
		}
		if (!containerVOsForTransfer.isEmpty()) {
			reassignContainers(containerVOsForTransfer, operationalFlightVO);
		}
		AirlineValidationVO fromAirlineValidationVo = null;
		AirlineValidationVO toAirlineValidationVo = null;
		if (transferMap != null && transferMap.get(MailConstantsVO.CONST_CONTAINER_DETAILS) != null) {
			containerDetailsCollection = (Collection<ContainerDetailsVO>) transferMap
					.get(MailConstantsVO.CONST_CONTAINER_DETAILS);
			transferManifestVO.setDsnVOs(new ArrayList<DSNVO>());
			for (ContainerDetailsVO container : containerDetailsCollection) {
				if (container.getDsnVOs() != null && !container.getDsnVOs().isEmpty()) {
					transferManifestVO.getDsnVOs().addAll(container.getDsnVOs());
					for (DSNVO dsnVO : container.getDsnVOs()) {
						if (dsnVO.getMailSequenceNumber() > 0) {
							String transferManifestId = null;
							try {
								transferManifestId = constructDAO().findTransferManifestId(dsnVO.getCompanyCode(),
										dsnVO.getMailSequenceNumber());
							} catch (PersistenceException e) {
								e.getMessage();
							}
							if (transferManifestId != null && transferManifestId.trim().length() > 0) {
								TransferManifestDSN transferManifestDSN = null;
								TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
								transferManifestDSNPK.setCompanyCode(dsnVO.getCompanyCode());
								transferManifestDSNPK.setTransferManifestId(transferManifestId);
								transferManifestDSNPK.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
								try {
									transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
								} catch (FinderException e) {
									e.getMessage();
								}
								if (transferManifestDSN != null) {
									transferManifestDSN.setTransferStatus(TRANSER_STATUS_REJECT);
								}
							}
						}
					}
				}
			}
			transferManifestVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			transferManifestVO.setAirPort(operationalFlightVO.getPol());
			String key = new StringBuilder(operationalFlightVO.getOwnAirlineCode())
					.append(operationalFlightVO.getCarrierCode()).toString();
			int transferManifestId = generateTransferManifestSeqNumber(operationalFlightVO.getCompanyCode(), key);
			transferManifestVO.setTransferManifestId(key + String.valueOf(transferManifestId));
			transferManifestVO.setTransferredFromCarCode(operationalFlightVO.getOwnAirlineCode());
			ZonedDateTime date = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
			transferManifestVO.setTransferredDate(date);
			transferManifestVO.setTransferredToCarrierCode(operationalFlightVO.getCarrierCode());
			transferManifestVO.setTransferredToFltNumber(operationalFlightVO.getFlightNumber());
			transferManifestVO.setTransferredFromFltNum(containerVo.getFlightNumber());
			if (containerVo.getFlightNumber() != null && containerVo.getFlightSequenceNumber() > 0) {
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(containerVo.getCompanyCode());
				flightFilterVO.setFlightCarrierId(containerVo.getCarrierId());
				flightFilterVO.setFlightNumber(containerVo.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
				Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
				for (FlightValidationVO flightValidation : flightValidationVOs) {
					containerVo.setFlightDate(LocalDateMapper.toZonedDateTime(flightValidation.getFlightDate()));
				}
			}
			transferManifestVO.setFromFltDat(containerVo.getFlightDate());
			transferManifestVO.setTransferredfrmFltSeqNum(containerVo.getFlightSequenceNumber());
			transferManifestVO.setTransferredfrmSegSerNum(containerVo.getSegmentSerialNumber());
			transferManifestVO.setToFltDat(operationalFlightVO.getFlightDate());
			transferManifestVO.setLastUpdateUser(operationalFlightVO.getOperator());
			transferManifestVO.setLastUpdateTime(date);
			try {
				fromAirlineValidationVo = findAirlineDescription(operationalFlightVO.getCompanyCode(),
						operationalFlightVO.getOwnAirlineCode());
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			transferManifestVO.setFromCarCodeDesc(fromAirlineValidationVo.getAirlineName());
			try {
				toAirlineValidationVo = findAirlineDescription(operationalFlightVO.getCompanyCode(),
						operationalFlightVO.getCarrierCode());
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			transferManifestVO.setToCarCodeDesc(toAirlineValidationVo.getAirlineName());
			new TransferManifest(transferManifestVO);
			for (ContainerDetailsVO container : containerDetailsCollection) {
				Collection<MailbagVO> mailbagVOs = new ArrayList<>();
				for (DSNVO dsnVO : container.getDsnVOs()) {
					MailbagPK mailbagPK = new MailbagPK();
					mailbagPK.setCompanyCode(transferManifestVO.getCompanyCode());
					mailbagPK.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
					Mailbag mailbag = new Mailbag();
					try {
						mailbag = Mailbag.find(mailbagPK);
					} catch (FinderException e) {
						log.info(e.getMessage());
					}
					MailbagVO mailbagvo = populateMailbagVofromMailbag(mailbag, transferManifestVO);
					updateTransferOutDetailsForHistory(mailbagvo, transferManifestVO);
					mailbagVOs.add(mailbagvo);
				}
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				if (operationalFlightVO.isTransferStatus()) {
					mailController.flagHistoryForContainerTransferAtExport(operationalFlightVO, 1, mailbagVOs, mailController.getTriggerPoint());
				} else {
					mailController.flagHistoryforTransferInitiation(mailbagVOs, getTriggerPoint());
				}
			}
		}
		return transferManifestVO;
	}

	/**
	 * Method		:	MailController.flagHistoryForContainerTransferAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	 * @param operationalFlightVO
	 * @param toFlightSegSerNum
	 * @param mailbagVOs
	 * @throws SystemException
	 */
	public void flagHistoryForContainerTransferAtExport(OperationalFlightVO operationalFlightVO, int toFlightSegSerNum,
														Collection<MailbagVO> mailbagVOs, String triggerPoint) {
		log.debug(CLASS + " : " + "flagHistoryForContainerTransferAtExport" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.flagHistoryForContainerTransferAtExport(operationalFlightVO,
				toFlightSegSerNum, mailbagVOs, triggerPoint));
		log.debug(CLASS + " : " + "flagHistoryForContainerTransferAtExport" + " Exiting");
	}
	/**
	 * @return
	 * @throws SystemException
	 * @author A-7540
	 */
	public void generateResditPublishReport(String companyCode, String paCode, int days) throws BusinessException {
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		log.debug(CLASS + " : " + "generateResditPublishReport" + " Entering");
		ReportPublishJobVO reportPublishJobVO = new ReportPublishJobVO();
		ResditPublishJobScheduleVO resditPublishJobScheduleVO = new ResditPublishJobScheduleVO();
		reportPublishJobVO.setCompanyCode(companyCode);
		String airportCode = logonAttributes.getAirportCode();
		resditPublishJobScheduleVO.setAirportCode(airportCode);
		reportPublishJobVO.setAirportCode(resditPublishJobScheduleVO.getAirportCode());
		reportPublishJobVO.setReportID(ResditPublishJobScheduleVO.REPORT_ID);
		reportPublishJobVO.setScheduleId(resditPublishJobScheduleVO.getScheduleId());
		reportPublishJobVO.setParameterFive(paCode);
		reportPublishJobVO.setParameterNine(String.valueOf(days));
		reportPublishJobVO.setFileName(ResditPublishJobScheduleVO.REPORT_NAME);
		try {
			adminReportProxy.publishReport(reportPublishJobVO);
		} finally {
		}
	}

	/**
	 * @param carditEnquiryFilterVO
	 * @return
	 * @author A-8061
	 */
	public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO) {
		log.debug(CLASS + " : " + "findGrandTotals" + " Entering");
		return Mailbag.findGrandTotals(carditEnquiryFilterVO);
	}
    /**
	 * @return
	 * @throws SystemException
	 * @author A-7779
	 */
	public MailOperationsDAO getEntityManagerForDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
   	public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO, int pageNumber) {
		try {
			boolean hasPrivilege = hasPrivilege(HAS_PRIVILEGE_FOR_VIEW_ALL_MAIL_TRUCK);
			operationalFlightVO.setIncludeAllMailTrucks(hasPrivilege);
			return constructDAO().findOutboundFlightsDetails(operationalFlightVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public boolean hasPrivilege(String privilege) {
//			return securityAgent.checkPrivilegeForAction(privilege);
		return false;

	}

	public Page<MailbagVO> getMailbagsinContainer(ContainerDetailsVO containerVO, int pageNumber) {
		log.debug(CLASS + " : " + "getMailbagsinContainer" + " Entering");
		log.debug(MODULE + " : " + "getMailbagsinContainer" + " Entering");
		Page<MailbagVO> mailbags = null;
		try {
			mailbags = constructDAO().findMailbagsinContainer(containerVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbags;
	}

	public Page<DSNVO> getMailbagsinContainerdsnview(ContainerDetailsVO containerVO, int pageNumber) {
		log.debug(CLASS + " : " + "getMailbagsinContainerdsnview" + " Entering");
		log.debug(MODULE + " : " + "getMailbagsinContainerdsnview" + " Entering");
		Page<DSNVO> dsnVos = null;
		try {
			dsnVos = constructDAO().findMailbagsinContainerdsnview(containerVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return dsnVos;
	}

	public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO) {
		log.debug(CLASS + " : " + "findCarditSummaryView" + " Entering");
		MailbagVO mailbag = null;
		try {
			mailbag = constructDAO().findCarditSummaryView(carditEnquiryFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbag;
	}

	public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findGroupedCarditMails" + " Entering");
		Page<MailbagVO> mailbag = null;
		try {
			mailbag = constructDAO().findGroupedCarditMails(carditEnquiryFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbag;
	}

	public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		log.debug(CLASS + " : " + "findCarditSummaryView" + " Entering");
		MailbagVO mailbag = null;
		try {
			mailbag = constructDAO().findLyinglistSummaryView(mailbagEnquiryFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbag;
	}

	public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findGroupedCarditMails" + " Entering");
		Page<MailbagVO> mailbag = null;
		try {
			mailbag = constructDAO().findGroupedLyingList(mailbagEnquiryFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbag;
	}

	public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO, int pageNumber) {
		try {
			return constructDAO().findOutboundCarrierDetails(operationalFlightVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containerVO, int pageNumber) {
		log.debug(CLASS + " : " + "getMailbagsinContainer" + " Entering");
		log.debug(MODULE + " : " + "getMailbagsinContainer" + " Entering");
		Page<MailbagVO> mailbags = null;
		try {
			mailbags = constructDAO().getMailbagsinCarrierContainer(containerVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbags;
	}

	public Page<DSNVO> getMailbagsinCarrierdsnview(ContainerDetailsVO containerVO, int pageNumber) {
		log.debug(CLASS + " : " + "getMailbagsinContainerdsnview" + " Entering");
		log.debug(MODULE + " : " + "getMailbagsinContainerdsnview" + " Entering");
		Page<DSNVO> dsnVos = null;
		try {
			dsnVos = constructDAO().getMailbagsinCarrierContainerdsnview(containerVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return dsnVos;
	}

	public Collection<DSNVO> getDSNsForContainer(ContainerDetailsVO containerVO) {
		log.debug(CLASS + " : " + "getMailbagsinContainerdsnview" + " Entering");
		Collection<DSNVO> dsnVos = null;
		try {
			if (containerVO.getFlightSequenceNumber() == -1) {
				dsnVos = constructDAO().getDSNsForCarrier(containerVO);
			} else {
				dsnVos = constructDAO().getDSNsForContainer(containerVO);
			}
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return dsnVos;
	}

	public Collection<DSNVO> getRoutingInfoforDSN(Collection<DSNVO> dsnVos, ContainerDetailsVO containerDetailsVO) {
		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCarrierId(containerDetailsVO.getCarrierId());
		operationalFlightVo.setCompanyCode(containerDetailsVO.getCompanyCode());
		operationalFlightVo.setFlightNumber(containerDetailsVO.getFlightNumber());
		operationalFlightVo.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		for (DSNVO dSNVO : dsnVos) {
			String type = "";
			if (dSNVO != null) {
				if (ContainerDetailsVO.FLAG_YES.equals(dSNVO.getPltEnableFlag())) {
					type = "MAILBAG";
					dSNVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				} else if (((ContainerDetailsVO.FLAG_NO).equals(dSNVO.getPltEnableFlag()))
						&& ((ContainerDetailsVO.OPERATION_FLAG_UPDATE).equals(dSNVO.getContainerType()))) {
					type = "DESPATCH_IN_ULD";
				} else if (((ContainerDetailsVO.FLAG_NO).equals(dSNVO.getPltEnableFlag()))
						&& (("B").equals(dSNVO.getContainerType()))) {
					type = "DESPATCH_IN_MFT_BULK";
					dSNVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				}
				if (dSNVO.getRoutingAvl() == null || dSNVO.getRoutingAvl().trim().length() == 0) {
					if (operationalFlightVo != null && type.trim().length() > 0) {
						if (AssignedFlight.checkRoutingsForMails(operationalFlightVo, dSNVO, type)) {
							dSNVO.setRoutingAvl(ContainerDetailsVO.FLAG_YES);
						} else {
							dSNVO.setRoutingAvl(ContainerDetailsVO.FLAG_NO);
						}
					}
				}
			}
		}
		return dsnVos;
	}

	/**
	 * Method		:	MailController.listFlightDetails Added by 	:	A-8164 on 25-Sep-2018 Used for 	:	Listing flight details for mailInbound Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<MailArrivalVO>
	 */
	public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO) throws PersistenceException {
		Page<MailArrivalVO> flightDetailsCollection = AssignedFlight.listFlightDetails(mailArrivalVO);
		Collection<MailArrivalVO> manifestInfoCollection = AssignedFlight.listManifestDetails(mailArrivalVO);
		String flightCheck = null;
		String manifestCheck = null;
		double totalWeight, totalContainerCount, totalMailCount;
		if (flightDetailsCollection != null && flightDetailsCollection.size() > 0) {
			for (MailArrivalVO flightDetails : flightDetailsCollection) {
				totalWeight = 0;
				totalContainerCount = 0;
				totalMailCount = 0;
				flightCheck = new StringBuffer().append(flightDetails.getFlightCarrierCode())
						.append(flightDetails.getFlightNumber()).append(flightDetails.getFlightSequenceNumber())
						.append(flightDetails.getCarrierId()).toString();
				if (manifestInfoCollection != null && manifestInfoCollection.size() > 0) {
					for (MailArrivalVO manifestDetails : manifestInfoCollection) {
						manifestCheck = new StringBuffer().append(manifestDetails.getFlightCarrierCode())
								.append(manifestDetails.getFlightNumber())
								.append(manifestDetails.getFlightSequenceNumber())
								.append(manifestDetails.getCarrierId()).toString();
						if (flightCheck.equals(manifestCheck)) {
							if (manifestDetails.getTotalWeight() != null)
								totalWeight = totalWeight + manifestDetails.getTotalWeight();
							totalContainerCount = totalContainerCount + manifestDetails.getContainerCount();
							totalMailCount = totalMailCount + manifestDetails.getMailCount();
							if (flightDetails.getManifestInfo() == null) {
								flightDetails.setManifestInfo(manifestDetails.getManifestInfo());
							} else {
								String manifestInfo = new StringBuffer().append(flightDetails.getManifestInfo())
										.append("|").append(manifestDetails.getManifestInfo()).toString();
								flightDetails.setManifestInfo(manifestInfo);
							}
						}
					}
				}
				if (flightDetails.getManifestInfo() == null) {
					flightDetails.setManifestInfo(" -- ");
				} else {
					//TODO: Neo to correct the below commented part
					String manifestInfo = new StringBuffer().append(flightDetails.getManifestInfo()).append("| Total:")
							.append((int) totalMailCount).append('/')
//							.append(new Quantity(UnitConstants.MAIL_WGT, totalWeight).getRoundedDisplayValue()
//									.doubleValue())
							.toString();
					flightDetails.setManifestInfo(manifestInfo);
				}
			}
		}
		return flightDetailsCollection;
	}

	public void closeInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
			throws MailOperationsBusinessException {
		log.debug(CLASS + " : " + "closeInboundFlight" + " Entering");
		for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
			try {
				closeMailInboundFlight(operationalFlightVO);
			} catch (BusinessException e) {
				throw new MailOperationsBusinessException(e);
			}
		}
		log.debug(CLASS + " : " + "closeInboundFlight" + " Exiting");
	}

	/**
	 * Method		:	MailController.reopenInboundFlights Added by 	:	A-8164 on 11-Dec-2018 Used for 	:	reopening multiple flights Parameters	:	@param operationalFlightVOs Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void reopenInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs) {
		log.debug(CLASS + " : " + "reopenInboundFlight" + " Entering");
		for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
			AssignedFlight inboundFlight = null;
			AssignedFlightPK inboundFlightPk = new AssignedFlightPK();
			inboundFlightPk.setAirportCode(operationalFlightVO.getPou());
			inboundFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
			inboundFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
			inboundFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
			inboundFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
			inboundFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
			try {
				inboundFlight = AssignedFlight.find(inboundFlightPk);
			} catch (FinderException ex) {
				log.info(FINDEREXCEPTIO_STRING);
				log.info("DATA INCONSISTENT");
				throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
			}
			if (inboundFlight != null) {
				inboundFlight.setImportClosingFlag(MailConstantsVO.FLIGHT_STATUS_OPEN);
			}
			//TODO: TO implement in Audit
//			AssignedFlightAuditVO assignedFlightAuditVO = new AssignedFlightAuditVO(AssignedFlightVO.MODULE,
//					AssignedFlightVO.SUBMODULE, AssignedFlightVO.ENTITY);
//			assignedFlightAuditVO = (AssignedFlightAuditVO) AuditUtils.populateAuditDetails(assignedFlightAuditVO,
//					inboundFlight);
//			performAuditForInboundFlightReopen(assignedFlightAuditVO, inboundFlight, operationalFlightVO);
		}
	}

	public MailArrivalVO populateMailArrivalVOForInbound(OperationalFlightVO operationalFlightVO) {
		MailArrivalFilterVO mailArrivalFilterVO = new MailArrivalFilterVO();
		mailArrivalFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		mailArrivalFilterVO.setCarrierId(operationalFlightVO.getCarrierId());
		mailArrivalFilterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		mailArrivalFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		mailArrivalFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		mailArrivalFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
		mailArrivalFilterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		mailArrivalFilterVO.setPou(operationalFlightVO.getPou());
		mailArrivalFilterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalVO = findArrivalDetails(mailArrivalFilterVO);
		mailArrivalVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		mailArrivalVO.setAirportCode(operationalFlightVO.getPou());
		mailArrivalVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
		mailArrivalVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		mailArrivalVO.setArrivalDate(operationalFlightVO.getFlightDate());
		mailArrivalVO.setCarrierId(operationalFlightVO.getCarrierId());
		mailArrivalVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		mailArrivalVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		HashMap<String, Collection<DSNVO>> dsnvoMap = new HashMap<String, Collection<DSNVO>>();
		if (mailArrivalVO.getContainerDetails() != null && mailArrivalVO.getContainerDetails().size() > 0) {
			for (ContainerDetailsVO containerdtlsvo : mailArrivalVO.getContainerDetails()) {
				double manifestedWeight = 0;
				int manifestedBags = 0;
				double receivedWeight = 0;
				int receivedBags = 0;
				Collection<DSNVO> dSNVOs = new ArrayList<DSNVO>();
				dSNVOs = containerdtlsvo.getDsnVOs();
				if (dSNVOs != null && dSNVOs.size() > 0) {
					for (DSNVO dsnvo : dSNVOs) {
						manifestedWeight = manifestedWeight + dsnvo.getWeight().getRoundedValue().doubleValue();
						manifestedBags = manifestedBags + dsnvo.getBags();
						receivedWeight = receivedWeight + dsnvo.getReceivedWeight().getRoundedValue().doubleValue();
						receivedBags = receivedBags + dsnvo.getReceivedBags();
						Collection<DSNVO> despatchVOs = null;
						if (dsnvo.getCsgDocNum() != null && !"".equals(dsnvo.getCsgDocNum())) {
							if (dsnvoMap.containsKey(dsnvo.getCsgDocNum())) {
								despatchVOs = dsnvoMap.get(dsnvo.getCsgDocNum());
							} else {
								despatchVOs = new ArrayList<DSNVO>();
							}
							despatchVOs.add(dsnvo);
							dsnvoMap.put(dsnvo.getCsgDocNum(), despatchVOs);
						}
					}
				}
				containerdtlsvo.setTotalBags(manifestedBags);
				containerdtlsvo.setTotalWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(manifestedWeight)));
				containerdtlsvo.setReceivedBags(receivedBags);
				containerdtlsvo.setReceivedWeight(
						quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(receivedWeight)));
			}
		}
		return mailArrivalVO;
	}

	public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO) throws PersistenceException {
		return ULDForSegment.findArrivedContainersForInbound(mailArrivalFilterVO);
	}

	public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO) throws PersistenceException {
		return ULDForSegment.findArrivedMailbagsForInbound(mailArrivalFilterVO);
	}

	public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO) {
		return ULDForSegment.findArrivedDsnsForInbound(mailArrivalFilterVO);
	}

	public OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) {
		log.debug(CLASS + " : " + "findOffLoadDetails" + " Entering");
		return MailAcceptance.findOffLoadDetails(offloadFilterVO);
	}

	public MailInConsignmentVO populatePCIDetailsforUSPS(MailInConsignmentVO mailInConsignment, String airport,
														 String companyCode, String rcpOrg, String rcpDest, String year) {
		mailInConsignment.setMailSubclass(mailInConsignment.getMailId().substring(3, 4) + "X");
		mailInConsignment.setMailClass(mailInConsignment.getMailId().substring(3, 4));
		mailInConsignment.setYear(Integer.parseInt(year));
		exchangeOfficeMap = findOfficeOfExchangeForPA(companyCode, findSystemParameterValue(USPS_DOMESTIC_PA));
		if ((this.exchangeOfficeMap != null) && (!this.exchangeOfficeMap.isEmpty())
				&& (this.exchangeOfficeMap.containsKey(rcpOrg)) && (this.exchangeOfficeMap.containsKey(rcpDest))) {
			mailInConsignment.setOriginExchangeOffice((String) this.exchangeOfficeMap.get(rcpOrg));
			mailInConsignment.setDestinationExchangeOffice((String) this.exchangeOfficeMap.get(rcpDest));
		}
		mailInConsignment.setMailCategoryCode("B");
		mailInConsignment.setDsn(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);
		mailInConsignment.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
		mailInConsignment.setHighestNumberedReceptacle("9");
		mailInConsignment.setRegisteredOrInsuredIndicator("9");
		mailInConsignment.setStatedBags(1);
		return mailInConsignment;
	}

	/**
	 * Method		:	MailController.listCarditDsnDetails Added by 	:	A-8164 on 05-Sep-2019 Used for 	:	List Cardit DSN Details Parameters	:	@param dsnEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Page<DSNVO>
	 */
	public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO) {
		log.debug("listCarditDsnDetails" + " : " + "dsnEnquiryFilterVO" + " Entering");
		Page<DSNVO> dsnvos = null;
		dsnvos = Mailbag.listCarditDsnDetails(dsnEnquiryFilterVO);
		return dsnvos;
	}

	/**
	 * Method		:	MailController.findRunnerFlights Added by 	:	A-5526 on 12-Oct-2018 Used for 	:   ICRD-239811 Parameters	:	@param runnerFlightFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Page<RunnerFlightVO>
	 * @throws PersistenceException
	 */
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			throws PersistenceException {
		log.debug("CTOManifestController" + " : " + "findRunnerFlights" + " Entering");
		MeasureMapper measureMapper =ContextUtil.getInstance().getBean(MeasureMapper.class);
		Page<RunnerFlightVO> runnerFlightVOs = null;
		runnerFlightVOs = constructDAO().findRunnerFlights(runnerFlightFilterVO);
		if (runnerFlightVOs != null && runnerFlightVOs.size() > 0) {
			for (RunnerFlightVO runnerFlightVO : runnerFlightVOs) {
				double manifetsedWgt = 0;
				if (runnerFlightVO.getRunnerFlightULDs() != null && runnerFlightVO.getRunnerFlightULDs().size() > 0) {
					for (RunnerFlightULDVO runnerFlightULDVO : runnerFlightVO.getRunnerFlightULDs()) {
						ContainerVO containerDetailVO = null;
						RunnerFlightVO runnerFlightVOForContainer = null;
						if (RunnerFlightVO.LISTTYPE_REFUSAL.equals(runnerFlightFilterVO.getInboundListType())) {
							runnerFlightVOForContainer = getRunnerFlightVOForContainerFilter(runnerFlightVO);
						}
						if (runnerFlightVOForContainer != null) {
							containerDetailVO = constructDAO().findContainerDetails(runnerFlightVOForContainer,
									runnerFlightULDVO);
						} else {
							containerDetailVO = constructDAO().findContainerDetails(runnerFlightVO, runnerFlightULDVO);
						}
						if (containerDetailVO != null) {
							if (containerDetailVO.getWeight() != null)
								manifetsedWgt = manifetsedWgt + containerDetailVO.getWeight().getRoundedValue().doubleValue();
							runnerFlightULDVO.setManifestedWeight(measureMapper
									.toMeasure(containerDetailVO.getWeight()));
							runnerFlightULDVO.setLocation(containerDetailVO.getLocationCode());
						}
					}
					runnerFlightVO.setManifestedWeight(measureMapper
							.toMeasure(quantities.getQuantity(Quantities.CHG_WGT, BigDecimal.valueOf(manifetsedWgt))));
				}
			}
		}
		return runnerFlightVOs;
	}

	/**
	 * Method		:	MailController.findMailbagsForTruckFlight Added by 	:	A-7929 on 23-Oct-2018 Added for 	:   CRQ ICRD-241437 Parameters	:	@param mailbagEnquiryFilterVO,pageNumber Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Page<MailbagVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		if ("N".equals(mailbagEnquiryFilterVO.getLyingList())) {
			log.debug(CLASS + " : " + "findMailbagsForTruckFlight" + " Entering");
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			CarditEnquiryFilterVO carditfiltervo = new CarditEnquiryFilterVO();
			carditfiltervo.setCarrierCode(mailbagEnquiryFilterVO.getCarrierCode());
			carditfiltervo.setCompanyCode(mailbagEnquiryFilterVO.getCompanyCode());
			carditfiltervo.setCarrierId(mailbagEnquiryFilterVO.getCarrierId());
			carditfiltervo.setConsignmentDocument(mailbagEnquiryFilterVO.getConsigmentNumber());
			carditfiltervo.setMailCategoryCode(mailbagEnquiryFilterVO.getMailCategoryCode());
			carditfiltervo.setMailbagId(mailbagEnquiryFilterVO.getMailbagId());
			carditfiltervo.setMailSubclass(mailbagEnquiryFilterVO.getMailSubclass());
			carditfiltervo.setDoe(mailbagEnquiryFilterVO.getDoe());
			carditfiltervo.setOoe(mailbagEnquiryFilterVO.getOoe());
			carditfiltervo.setYear(mailbagEnquiryFilterVO.getYear());
			carditfiltervo.setDespatchSerialNumber(mailbagEnquiryFilterVO.getDespatchSerialNumber());
			carditfiltervo.setReceptacleSerialNumber(mailbagEnquiryFilterVO.getReceptacleSerialNumber());
			carditfiltervo.setPaoCode(mailbagEnquiryFilterVO.getPacode());
			ZonedDateTime fromDate = localDateUtil.getLocalDate(null, false);
			if (mailbagEnquiryFilterVO.getFromDate() != null) {
				fromDate = LocalDate.withDate(fromDate, mailbagEnquiryFilterVO.getFromDate().toUpperCase());
				carditfiltervo.setFromDate(fromDate);
			} else {
				carditfiltervo.setFromDate(localDateUtil.getLocalDate(null, true));
			}
			ZonedDateTime toDate = localDateUtil.getLocalDate(null, false);
			if (mailbagEnquiryFilterVO.getToDate() != null) {
				toDate = LocalDate.withDate(toDate, mailbagEnquiryFilterVO.getToDate().toUpperCase());
				carditfiltervo.setToDate(toDate);
			} else {
				carditfiltervo.setToDate(localDateUtil.getLocalDate(null, true));
			}
			carditfiltervo.setUldNumber(mailbagEnquiryFilterVO.getUldNumber());
			carditfiltervo.setFlightNumber(mailbagEnquiryFilterVO.getFlightNumber());
			carditfiltervo.setFlightDate(mailbagEnquiryFilterVO.getFlightDate());
			carditfiltervo.setMailOrigin(mailbagEnquiryFilterVO.getOriginAirportCode());
			carditfiltervo.setMaildestination(mailbagEnquiryFilterVO.getDestinationAirportCode());
			carditfiltervo.setMailStatus(mailbagEnquiryFilterVO.getCurrentStatus());
			Page<MailbagVO> carditMails = Cardit.findCarditMails(carditfiltervo, pageNumber);
			HashMap<String, MailbagVO> mailBagMap = new HashMap<String, MailbagVO>();
			String key = null;
			if (carditMails != null && carditMails.size() > 0) {
				for (MailbagVO mailbagVO : carditMails) {
					key = mailbagVO.getMailbagId();
					ZonedDateTime existingDepartureDate = null;
					ZonedDateTime DepartureDate = null;
					if (mailBagMap.containsKey(key)) {
						MailbagVO existingMailbagVO = mailBagMap.get(key);
						FlightFilterVO existingFlightFilterVO = new FlightFilterVO();
						FlightFilterVO flightFilterVO = new FlightFilterVO();
						Collection<FlightValidationVO> existingFlightVOs = null;
						Collection<FlightValidationVO> flightVOs = null;
						existingFlightFilterVO.setCompanyCode(existingMailbagVO.getCompanyCode());
						existingFlightFilterVO.setFlightNumber(existingMailbagVO.getFlightNumber());
						existingFlightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(existingMailbagVO.getFlightDate()));
						existingFlightFilterVO.setFlightSequenceNumber(existingMailbagVO.getFlightSequenceNumber());
						existingFlightVOs = validateFlight(existingFlightFilterVO);
						if (existingFlightVOs != null) {
							existingDepartureDate = localDateUtil.getLocalDate(
									existingFlightVOs.iterator().next().getStd());
						}
						flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
						flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
						flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
						flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
						flightVOs = validateFlight(flightFilterVO);
						if (flightVOs != null) {
							DepartureDate = flightVOs.iterator().next().getStd().toZonedDateTime();
						}
						if (existingMailbagVO.getCarrierCode() != null
								&& existingMailbagVO.getCarrierCode().equals(mailbagVO.getCarrierCode())) {
							if (existingMailbagVO.getFlightDate() != null) {
								if (existingDepartureDate.isAfter(DepartureDate)) {
									mailBagMap.put(key, mailbagVO);
								}
							}
						} else if (mailbagVO.getCarrierCode() != null
								&& mailbagVO.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {
							mailBagMap.put(key, mailbagVO);
						} else if (mailbagVO.getCarrierCode() != null
								&& mailbagVO.getCarrierCode().equals(logonAttributes.getOwnAirlineCode())) {
							mailBagMap.put(key, mailbagVO);
						} else {
							if (existingMailbagVO.getFlightDate() != null) {
								if (existingDepartureDate.isAfter(DepartureDate)) {
									mailBagMap.put(key, mailbagVO);
								}
							}
						}
					} else {
						mailBagMap.put(key, mailbagVO);
					}
				}
				carditMails.removeAll(carditMails);
				carditMails.addAll(mailBagMap.values());
			}
			log.debug(CLASS + " : " + "findMailbagsForTruckFlight" + " Exiting");
			return carditMails;
		} else {
			log.debug(CLASS + " : " + "findAllMailbagsForTruckFlight" + " Entering");
			log.debug(CLASS + " : " + "findAllMailbagsForTruckFlight" + " Exiting");
			return Mailbag.findMailbags(mailbagEnquiryFilterVO, pageNumber);
		}
	}
    /**
	 * Method		:	MailController.findDsnAndRsnForMailbag Added by 	:	A-7531 on 31-Oct-2018 Used for 	: Parameters	:	@param maibagVO Parameters	:	@return Return type	: 	MailbagVO
	 * @throws SystemException
	 */
	public MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO) {
		return MailAcceptance.findDsnAndRsnForMailbag(mailbagVO);
	}

	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO,
																	   int pageNumber) {
		log.debug(CLASS + " : " + "listForceMajeureApplicableMails" + " Entering");
		String source = filterVO.getSource();
		Page<ForceMajeureRequestVO> forceMajeureMails = null;
		try {
			forceMajeureMails = ForceMajeureRequest.listForceMajeureApplicableMails(filterVO, pageNumber);
			if (forceMajeureMails == null || forceMajeureMails.isEmpty()) {
				filterVO.setSource(MailConstantsVO.FLIGHT_TYP_CARDIT);
				forceMajeureMails = ForceMajeureRequest.listForceMajeureApplicableMails(filterVO, pageNumber);
				filterVO.setSource(source);
			}
		} finally {
		}
		log.debug(CLASS + " : " + "listForceMajeureApplicableMails" + " Exiting");
		return forceMajeureMails;
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public void saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) {
		log.debug(CLASS + " : " + "saveForceMajeureRequest" + " Entering");
		String forceMajeureID = "";
		String txnRemarks = "";
		String txnStatus = "";
		try {
			forceMajeureID = ForceMajeureRequest.saveForceMajeureRequest(filterVO);
		} finally {
		}
		InvoiceTransactionLogVO txnLogVO = new InvoiceTransactionLogVO();
		txnLogVO.setCompanyCode(filterVO.getCompanyCode());
		txnLogVO.setInvoiceType(MailConstantsVO.FORCE_MAJEURE_REQUEST);
		txnLogVO.setTransactionCode(filterVO.getTransactionCode());
		txnLogVO.setSerialNumber(filterVO.getTxnSerialNumber());
		if (forceMajeureID != null && forceMajeureID.startsWith(MailConstantsVO.FORCE_MAJEURE)) {
			txnStatus = MailConstantsVO.COMPLETED;
			txnRemarks = new StringBuilder(MailConstantsVO.FORCE_MAJEURE_ID).append(forceMajeureID)
					.append(MailConstantsVO.FORCE_MAJEURE_CREATED).toString();
		} else {
			txnStatus = MailConstantsVO.FAILED;
			txnRemarks = new StringBuilder(MailConstantsVO.FORCE_MAJEURE_CANNOT_CREATE).toString();
		}
		txnLogVO.setInvoiceGenerationStatus(txnStatus);
		txnLogVO.setRemarks(txnRemarks);
		craDefaultsProxy.updateTransactionandRemarks(txnLogVO);
		log.debug(CLASS + " : " + "saveForceMajeureRequest" + " Exiting");
	}

	/**
	 * @param invoiceTransactionLogVO
	 * @return
	 * @throws SystemException
	 */
	public InvoiceTransactionLogVO initTxnForForceMajeure(InvoiceTransactionLogVO invoiceTransactionLogVO) {
		return craDefaultsProxy.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
	}

	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber) {
		log.debug(CLASS + " : " + "listForceMajeureDetails" + " Entering");
		Page<ForceMajeureRequestVO> forceMajeureMails = null;
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		try {
			forceMajeureMails = ForceMajeureRequest.listForceMajeureDetails(filterVO, pageNumber);
		} finally {
		}
		log.debug(CLASS + " : " + "listForceMajeureDetails" + " Exiting");
		return forceMajeureMails;
	}

	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO,
																  int pageNumber) {
		log.debug(CLASS + " : " + "listForceMajeureRequestIds" + " Entering");
		Page<ForceMajeureRequestVO> forceMajeureMails = null;
		try {
			forceMajeureMails = new ForceMajeureRequest().listForceMajeureRequestIds(filterVO, pageNumber);
		} finally {
		}
		log.debug(CLASS + " : " + "listForceMajeureRequestIds" + " Exiting");
		return forceMajeureMails;
	}

	/**
	 * @param requestVOs
	 * @throws SystemException
	 */
	public void deleteForceMajeureRequest(Collection<ForceMajeureRequestVO> requestVOs) {
		log.debug(CLASS + " : " + "deleteForceMajeureRequest" + " Entering");
		for (ForceMajeureRequestVO requestVO : requestVOs) {
			try {
				ForceMajeureRequest request = ForceMajeureRequest.find(requestVO.getCompanyCode(),
						requestVO.getForceMajuereID(), requestVO.getSequenceNumber());
				if (request != null) {
					request.remove();
				}
			} catch (RemoveException exception) {
				log.error(exception.getMessage());
			} catch (FinderException exception) {
				log.error(exception.getMessage());
			}
		}
		log.debug(CLASS + " : " + "deleteForceMajeureRequest" + " Exiting");
	}

	/**
	 * @throws SystemException
	 */
	public void updateForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) {
		log.debug(CLASS + " : " + "updateForceMajeureRequest" + " Entering");
		String txnRemarks = "";
		String txnStatus = "";
		String outPar = "";
		try {
			outPar = ForceMajeureRequest.updateForceMajeureRequest(filterVO);
		} finally {
		}
		InvoiceTransactionLogVO txnLogVO = new InvoiceTransactionLogVO();
		txnLogVO.setCompanyCode(filterVO.getCompanyCode());
		txnLogVO.setInvoiceType(MailConstantsVO.FORCE_MAJEURE_REQUEST);
		txnLogVO.setTransactionCode(filterVO.getTransactionCode());
		txnLogVO.setSerialNumber(filterVO.getTxnSerialNumber());
		if (outPar != null && outPar.startsWith(MailConstantsVO.OK_STATUS)) {
			txnStatus = MailConstantsVO.COMPLETED;
			txnRemarks = new StringBuilder(MailConstantsVO.FORCE_MAJEURE_ID).append(filterVO.getForceMajeureID())
					.append(MailConstantsVO.FORCE_MAJEURE_UPDATED).toString();
		} else {
			txnStatus = MailConstantsVO.FAILED;
			txnRemarks = new StringBuilder(MailConstantsVO.FORCE_MAJEURE_CANNOT_UPDATE).toString();
		}
		txnLogVO.setInvoiceGenerationStatus(txnStatus);
		txnLogVO.setRemarks(txnRemarks);
		craDefaultsProxy.updateTransactionandRemarks(txnLogVO);
		log.debug(CLASS + " : " + "updateForceMajeureRequest" + " Exiting");
	}

	/**
	 * Method to find all containers in flight
	 * @param reassignedFlightValidationVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerVO> findAllContainersInAssignedFlight(FlightValidationVO reassignedFlightValidationVO) {
		log.debug("MailTrackingDefaultsServicesEJB" + " : " + "findAllContainersInAssignedFlight" + " Entering");
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(reassignedFlightValidationVO.getCompanyCode());
		operationalFlightVO.setFlightNumber(reassignedFlightValidationVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(reassignedFlightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(reassignedFlightValidationVO.getFlightCarrierId());
		operationalFlightVO.setPol(reassignedFlightValidationVO.getAirportCode());
		return AssignedFlight.findAllContainersInAssignedFlight(operationalFlightVO);
	}
	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		return constructDAO().processMailDataFromExcel(fileUploadFilterVO);
	}

	/**
	 * @author A-7794
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 */
	public Collection<ConsignmentDocumentVO> fetchMailDataForOfflineUpload(String companyCode, String fileType) {
		return constructDAO().fetchMailDataForOfflineUpload(companyCode, fileType);
	}

	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		constructDAO().removeDataFromTempTable(fileUploadFilterVO);
	}
	/**
	 * Method		:	MailController.findProductsByName Added by 	:	A-8527 on 29-Jan-2020 Used for 	: Parameters	:	@param companyCode Parameters	:	@param product Parameters	:	@return Return type	: 	String
	 * @throws SystemException
	 */
	public String findProductsByName(String companyCode, String product) {
		String documentSybtype = null;
		Collection<ProductValidationVO> productVOs = new ArrayList<ProductValidationVO>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productVOs = neoMastersServiceUtils.findProductsByName(companyCode, product);
		if (productVOs != null) {
			productValidationVO = productVOs.iterator().next();
			productVO = productDefaultsProxy.findProductDetails(companyCode, productValidationVO.getProductCode());
			documentSybtype = productVO.getDocumentSubType();
		}
		return documentSybtype;
	}

	/**
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) {
		return Mailbag.findDeviationMailbags(mailbagEnquiryFilterVO, pageNumber);
	}

	/**
	 * Method		:	MailController.insertOrUpdateHistoryDetailsForCardit Added by 	:	U-1467 on 18-Feb-2020 Used for 	:	IASCB-36803 Parameters	:	@param carditVO Return type	: 	void
	 */
	public void insertOrUpdateHistoryDetailsForCardit(CarditVO carditVO, String triggerPoint) {
		log.debug(CLASS + " : " + "insertOrUpdateHistoryDetailsForCardit" + " Entering");
		asyncInvoker.invoke(() -> historyBuilder.insertOrUpdateHistoryDetailsForCardit(carditVO, triggerPoint));
		log.debug(CLASS + " : " + "insertOrUpdateHistoryDetailsForCardit" + " Exiting");
	}

	/**
	 * Method		:	MailController.auditCarditCancellation Added by 	:	U-1467 on 18-Feb-2020 Used for 	:	IASCB-36803 Parameters	:	@param carditVO Parameters	:	@param actionCode Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void auditCarditCancellation(CarditVO carditVO, String actionCode) {
		log.debug(CLASS + " : " + "auditCarditCancellation" + " Entering");
		log.debug(CLASS + " : " + "auditCarditCancellation" + " Exiting");
	}

	/**
	 * Method		:	MailController.saveCarditMessages Added by 	:	A-6287 on 26-Feb-2020 Used for 	: Parameters	:	@param carditTempMsgVOs Return type	: 	void
	 * @throws SystemException
	 */
	public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs) {
		log.debug(CLASS + " : " + "saveCarditTempMessages" + " Entering");
		if (carditTempMsgVOs != null && carditTempMsgVOs.size() > 0) {
			try {
				new CarditTempDetails(carditTempMsgVOs);
			} finally {
			}
		}
		log.debug(CLASS + " : " + "saveCarditTempMessages" + " Exiting");
	}

	/**
	 * Method		:	MailController.getTempCarditMessages Added by 	:	A-6287 on 01-Mar-2020 Used for 	:savec Parameters	:	@param companyCode Parameters	:	@return Return type	: 	Collection<CarditTempMsgVO>
	 * @throws SystemException
	 */
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode, String includeMailBoxIdr,
															 String excludeMailBoxIdr, String includedOrigins, String excludedOrigins, int pageSize, int noOfDays) {
		log.debug(CLASS + " : " + "getTempCarditMessages" + " Entering");
		return constructDAO().getTempCarditMessages(companyCode, includeMailBoxIdr, excludeMailBoxIdr, includedOrigins,
				excludedOrigins, pageSize, noOfDays);
	}

	/**
	 * Method: MailController.deleteEmptyContainer Added by 	:	A-8893 on 04-Mar-2020 Used for 	:	IASCB-34152 Parameters	:   @param containerDetailsVO Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void deleteEmptyContainer(ContainerDetailsVO containerDetailsVO) {
		Container container = null;
		ULDForSegment uldForSegment = null;
		ContainerPK containerPK = constructContainerPK(containerDetailsVO);
		try {
			container = Container.find(containerPK);
			int bulkCount = constructDAO().findbulkcountInFlight(containerDetailsVO);
			if (!(("B").equals(containerDetailsVO.getContainerType())) || bulkCount == 1) {
				if (!containerDetailsVO.getFlightNumber().equals("-1")
						&& containerDetailsVO.getFlightSequenceNumber() > 0) {
					ULDForSegmentPK uldForSegmentPK = constructULDForSegmentPK(containerDetailsVO);
					uldForSegment = ULDForSegment.find(uldForSegmentPK);
					uldForSegment.remove();
				} else {
					removeUldAtAirport(containerDetailsVO);
				}
			}
			container.remove();
			ZonedDateTime date = localDateUtil.getLocalDate(containerDetailsVO.getAssignedPort(), true);
			containerDetailsVO.setAssignedDate(date);
			containerDetailsVO.setPou(container.getPou());
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagContainerAuditForDeletion(containerDetailsVO);
		} catch (FinderException e) {
			e.printStackTrace();
		} finally {
		}
		log.debug(CLASS + " : " + "deleteEmptyContainer" + " Exiting");
	}

	private ULDForSegmentPK constructULDForSegmentPK(ContainerDetailsVO containerDetailsVO) {
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		if ("B".equals(containerDetailsVO.getContainerType())) {
			uldForSegmentPK.setUldNumber("BULK-" + containerDetailsVO.getPou());
		} else {
			uldForSegmentPK.setUldNumber(containerDetailsVO.getContainerNumber());
		}
		uldForSegmentPK.setCarrierId(containerDetailsVO.getCarrierId());
		uldForSegmentPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		uldForSegmentPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		uldForSegmentPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		return uldForSegmentPK;
	}
	/**
	 * validateULDIncomatibility
	 * @author A-5526 for IASCB-34124
	 * @param flightValidationVO
	 * @throws SystemException
	 */
	private void validateULDIncomatibility(CarditContainerVO carditContainerVO, FlightValidationVO flightValidationVO) throws SharedProxyException {
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = neoMastersServiceUtils.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();
		if (carditContainerVO.getContainerNumber() != null && carditContainerVO.getContainerNumber().trim().length() > 0
				&& CarditMessageVO.UNIT_LOAD_DEVICE.equals(carditContainerVO.getEquipmentQualifier())) {
			String uldType = carditContainerVO.getContainerNumber().substring(0, 3);
			if (!uldTypeCodes.contains(uldType.toUpperCase())) {
				uldTypeCodes.add(uldType.toUpperCase());
			}
			uldNumberCodes.add(carditContainerVO.getContainerNumber());
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
			} finally {
			}
		}
	}

	/**
	 * @author A-5526 for IASCB-34124validateAirCraftCompatibilityforUldTypes
	 * @param uldTypeCodes
	 * @param systemParameterMap
	 * @return
	 */
	public Collection<String> validateAirCraftCompatibilityforUldTypes(Collection<String> uldTypeCodes,
																	   Map<String, String> systemParameterMap) {
		log.debug("SaveAcceptanceCommand" + " : " + "validateAirCraftCompatibilityforUldTypes" + " Entering");
		ArrayList<String> uldTypeCodesForValidation = null;
		if (systemParameterMap != null && systemParameterMap.size() > 0) {
			String configuredTypes = systemParameterMap.get(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
			if (configuredTypes != null && configuredTypes.length() > 0 && !"N".equals(configuredTypes)) {
				if ("*".equals(configuredTypes)) {
					for (String uldType : uldTypeCodes) {
						if (uldTypeCodesForValidation == null) {
							uldTypeCodesForValidation = new ArrayList<String>();
						}
						uldTypeCodesForValidation.add(uldType);
					}
				} else {
					List<String> configuredTypesList = Arrays.asList(configuredTypes.split(","));
					if (uldTypeCodes != null && uldTypeCodes.size() > 0) {
						for (String uldType : uldTypeCodes) {
							if (configuredTypesList.contains(uldType)) {
								if (uldTypeCodesForValidation == null) {
									uldTypeCodesForValidation = new ArrayList<String>();
								}
								uldTypeCodesForValidation.add(uldType);
							}
						}
					}
				}
			}
		}
		log.debug("SaveAcceptanceCommand" + " : " + "validateAirCraftCompatibilityforUldTypes" + " Exiting");
		return uldTypeCodesForValidation;
	}

	/**
	 * Method		:	MailController.updateGateClearStatus Added by 	:	U-1467 on 09-Mar-2020 Parameters	:	@param operationalFlightVO Parameters	:	@param gateClearanceStatus Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void updateGateClearStatus(
			com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO operationalFlightVO,
			String gateClearanceStatus) {
		AssignedFlight flight = null;
		AssignedFlightPK flightPK = new AssignedFlightPK();
		flightPK.setAirportCode(operationalFlightVO.getAirportCode());
		flightPK.setCompanyCode(operationalFlightVO.getCompanyCode());
		flightPK.setFlightNumber(operationalFlightVO.getFlightNumber());
		flightPK.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		flightPK.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		flightPK.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			flight = AssignedFlight.find(flightPK);
		} catch (FinderException ex) {
			log.info(FINDEREXCEPTIO_STRING);
		}
		if (flight != null) {
			if (RunnerFlightVO.RUN_DIRECTION_OUTBOUND.equals(operationalFlightVO.getRunnerFlightType())
					|| RunnerFlightVO.LISTTYPE_REFUSAL.equals(operationalFlightVO.getRunnerFlightType())) {
				String runnerCompletionStatus = flight.getGateClearanceStatus();
				if (runnerCompletionStatus != null && runnerCompletionStatus.trim().length() > 0
						&& !RunnerFlightVO.FLAG_NO.equals(runnerCompletionStatus)) {
					runnerCompletionStatus = runnerCompletionStatus + "," + gateClearanceStatus;
				} else {
					runnerCompletionStatus = gateClearanceStatus;
				}
				flight.setGateClearanceStatus(gateClearanceStatus);
			} else {
				flight.setGateClearanceStatus(gateClearanceStatus);
			}
		} else {
			if (RunnerFlightVO.RUN_DIRECTION_INBOUND.equals(operationalFlightVO.getRunnerFlightType())) {
				AssignedFlightVO flightVO = new AssignedFlightVO();
				flightVO.setAirportCode(operationalFlightVO.getAirportCode());
				flightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				flightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				flightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				flightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
				flightVO.setCarrierId(operationalFlightVO.getCarrierId());
				flightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				flightVO.setImportFlightStatus("O");
				flightVO.setFlightDate(localDateUtil.getLocalDate(operationalFlightVO.getFlightDate()));
				flightVO.setLastUpdateUser(operationalFlightVO.getLastUpdateUser());
				flight = new AssignedFlight(flightVO);
				if (flight != null) {
					flight.setGateClearanceStatus(gateClearanceStatus);
				}
			}
		}
	}

	/**
	 * @param carditVO
	 * @throws SystemException
	 */
	private void setTransportInformation(CarditVO carditVO) {
		ZonedDateTime sta = null;
		ZonedDateTime std = null;
		Collection<FlightValidationVO> flightVOs1 = null;
		Collection<FlightValidationVO> flightVOs2 = null;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		ZonedDateTime firstLegStd = null;
		String paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		if (carditVO.getTransportInformation() != null && !carditVO.getTransportInformation().isEmpty()) {
			for (CarditTransportationVO carditTransportation : carditVO.getTransportInformation()) {
				String transportDate = localDateUtil.getLocalDate(carditTransportation.getDeparturePort(), true)
						.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
				String departureTime = Objects.nonNull(carditTransportation.getDepartureTime())
						? carditTransportation.getDepartureTime().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
						: null;
				flightFilterVO.setCompanyCode(carditVO.getCompanyCode());
				flightFilterVO.setFlightCarrierId(carditTransportation.getCarrierID());
				flightFilterVO.setFlightNumber(carditTransportation.getFlightNumber());
				flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(carditTransportation.getDepartureTime()));
				flightFilterVO.setFlightSequenceNumber(carditTransportation.getFlightSequenceNumber());
				flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
				flightFilterVO.setStation(carditTransportation.getDeparturePort());
				try {
					flightVOs1 = validateFlight(flightFilterVO);
				} finally {
				}
				if (flightVOs1 != null && !flightVOs1.isEmpty()) {
					carditTransportation.setArrivalDate(localDateUtil.getLocalDate(flightVOs1.iterator().next().getSta()));
					if (!transportDate.equals(departureTime)) {
						sta = localDateUtil.getLocalDate(flightVOs1.iterator().next().getSta());
						std = localDateUtil.getLocalDate(flightVOs1.iterator().next().getStd());
					}
				} else {
					if (!transportDate.equals(departureTime))
						firstLegStd = carditTransportation.getDepartureTime();
				}
				if (sta != null) {
					if (transportDate.equals(departureTime)) {
						FlightFilterVO flightFilterVo = new FlightFilterVO();
						flightFilterVo.setCompanyCode(carditVO.getCompanyCode());
						flightFilterVo.setFlightNumber(carditTransportation.getFlightNumber());
						flightFilterVo.setDirection(FlightFilterVO.OUTBOUND);
						flightFilterVo.setStation(carditTransportation.getDeparturePort());
						try {
							flightVOs2 = validateFlight(flightFilterVo);
						} finally {
						}
						if (flightVOs2 != null && !flightVOs2.isEmpty()) {
							for (FlightValidationVO flightVO : flightVOs2) {
								if (flightVO.getStd().isGreaterThan(LocalDateMapper.toLocalDate(sta)) && paCode_dom.equals(carditVO.getSenderId())) {
									carditTransportation.setDepartureTime(localDateUtil.getLocalDate(flightVO.getStd()));
									break;
								}
							}
						}
					}
				} else {
					if (firstLegStd != null && paCode_dom.equals(carditVO.getSenderId())) {
						carditTransportation.setDepartureTime(firstLegStd);
					}
				}
			}
		}
	}
	public void insertHistoryDetailsForExcelUpload(Collection<ConsignmentDocumentVO> consignmentDocumentVOs) {
		log.debug(CLASS + " : " + "insertHistoryDetailsForExcelUpload" + " Entering");
		log.debug(CLASS + " : " + "insertHistoryDetailsForExcelUpload" + " Exiting");
	}
	/**
	 * @param ediInterchangeVO
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @throws DuplicateMailBagsException
	 * @author A-7540
	 */
	public Collection<ErrorVO> saveCDTMessages(EDIInterchangeVO ediInterchangeVO)
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		log.debug(CLASS + " : " + "saveCDTMessages" + " Entering");
		Collection<ErrorVO> errorVO = new ArrayList<>();
		try {
			errorVO = saveCarditMessages(ediInterchangeVO);
		}
		catch(BusinessException exce) {
			if (exce.getErrors() != null && exce.getErrors().size() > 0) {
				for (ErrorVO error : exce.getErrors()) {
					ErrorVO errorVo = new ErrorVO(error.getErrorCode(), error.getErrorData());
					errorVo.setErrorData(error.getErrorData());
					errorVo.setErrorType(ErrorType.WARNING);
					errorVO.add(errorVo);
				}
			}
		}
		return errorVO;
	}

	/**
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @author a-A-9529
	 */
	public Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(
			Collection<ContainerDetailsVO> containers) {
		return MailArrival.findMailbagsInContainerFromInboundForReact(containers);
	}
	/**
	 * Method		:	MailController.getCustomerForCustoms Added by 	:	U-1467 on 10-May-2020 Used for 	:	Get Customer from PA code mapped in Customer Preferences Parameters	:	@param paCode Parameters	:	@param customerDetails Parameters	:	@return Return type	: 	Collection<CustomerVO>
	 */
	private Collection<CustomerVO> getCustomerForCustoms(String paCode, Collection<CustomerVO> customerDetails) {
		Collection<CustomerVO> customerForCustoms = null;
		if (customerDetails != null && customerDetails.size() > 0) {
			for (CustomerVO customer : customerDetails) {
				if (customer.getCustomerPreferences() != null && customer.getCustomerPreferences().size() > 0) {
					for (CustomerPreferenceVO customerPreferenceVO : customer.getCustomerPreferences()) {
						if (isNotNullAndEmpty(paCode) && customerPreferenceVO.getPreferenceValue().contains(paCode)) {
							if (customerForCustoms == null) {
								customerForCustoms = new ArrayList<>();
							}
							customerForCustoms.add(customer);
							return customerForCustoms;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Method		:	MailController.populateShipmentRoutingDetails Added by 	:	U-1467 on 10-May-2020 Used for 	:	IASCB-51778 Parameters	:	@param shipmentDetailVO Parameters	:	@param mailbagVOs Parameters	:	@param containerDetailsVO Parameters	:	@throws SystemException Return type	: 	void
	 */
	private void populateShipmentRoutingDetails(ShipmentDetailVO shipmentDetailVO, Collection<MailbagVO> mailbagVOs,
												ContainerDetailsVO containerDetailsVO, MailManifestDetailsVO mailManifestDetailsVO) {
		Collection<ConsignmentRoutingVO> consignmentRoutingVOs = null;
		Collection<RoutingVO> routingVOs = new ArrayList<>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (isNotNullAndEmpty(mailbagVO.getConsignmentNumber()) && isNotNullAndEmpty(mailbagVO.getPaCode())) {
				CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
				carditEnquiryFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				carditEnquiryFilterVO.setConsignmentDocument(mailbagVO.getConsignmentNumber());
				carditEnquiryFilterVO.setPaoCode(mailbagVO.getPaCode());
				try {
					consignmentRoutingVOs = constructDAO().findConsignmentRoutingDetails(carditEnquiryFilterVO);
				} catch (PersistenceException exp) {
					log.debug(exp.getMessage());
				}
				int sequenceNumber = 1;
				if (consignmentRoutingVOs != null && consignmentRoutingVOs.size() > 0) {
					String consignmentDestination = null;
					for (ConsignmentRoutingVO consignmentRoutingVO : consignmentRoutingVOs) {
						RoutingVO routingVO = new RoutingVO();
						routingVO.setCompanyCode(consignmentRoutingVO.getCompanyCode());
						routingVO.setCarrierCode(consignmentRoutingVO.getFlightCarrierCode());
						routingVO.setCarrierId(consignmentRoutingVO.getFlightCarrierId());
						routingVO.setOrigin(consignmentRoutingVO.getPol());
						routingVO.setDestination(consignmentRoutingVO.getPou());
						routingVO.setAirportCode(consignmentRoutingVO.getPou());
						routingVO.setFlightNumber(consignmentRoutingVO.getFlightNumber());
						routingVO.setFlightDate(LocalDateMapper.toLocalDate(consignmentRoutingVO.getFlightDate()));
						routingVO.setRoutingSequenceNumber(sequenceNumber);
						routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
						routingVOs.add(routingVO);
						sequenceNumber++;
						consignmentDestination = consignmentRoutingVO.getPou();
					}
					shipmentDetailVO.setRoutingDetails(routingVOs);
					shipmentDetailVO.setHandlingInfo(mailbagVO.getConsignmentNumber() + SPACE + mailbagVO.getPaCode());
					if (isNotNullAndEmpty(mailbagVO.getDestination())
							&& !mailbagVO.getDestination().equals(consignmentDestination)) {
						LoginProfile logonAttributes = contextUtil.callerLoginProfile();
						RoutingVO routingVO = new RoutingVO();
						routingVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
						routingVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
						routingVO.setOrigin(consignmentDestination);
						routingVO.setDestination(mailbagVO.getDestination());
						routingVO.setAirportCode(mailbagVO.getDestination());
						routingVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
						routingVO.setRoutingSequenceNumber(sequenceNumber);
						routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
						shipmentDetailVO.getRoutingDetails().add(routingVO);
					}
				}
			} else {
				Collection<FlightValidationVO> flightValidationVOs = null;
				FlightFilterVO flightFilterVO = constructFlightFilterVO(containerDetailsVO);
				try {
					flightValidationVOs = validateFlight(flightFilterVO);
				} finally {
				}
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					int sequenceNumber = 1;
					String flightLegDestination = flightValidationVOs.iterator().next().getLegDestination();
					RoutingVO routingVO = new RoutingVO();
					routingVO.setCompanyCode(flightValidationVOs.iterator().next().getCompanyCode());
					routingVO.setCarrierCode(flightValidationVOs.iterator().next().getCarrierCode());
					routingVO.setCarrierId(flightValidationVOs.iterator().next().getFlightCarrierId());
					routingVO.setOrigin(flightValidationVOs.iterator().next().getLegOrigin());
					routingVO.setDestination(flightLegDestination);
					routingVO.setAirportCode(flightLegDestination);
					routingVO.setFlightNumber(flightValidationVOs.iterator().next().getFlightNumber());
					routingVO.setFlightDate(flightValidationVOs.iterator().next().getFlightDate());
					routingVO.setRoutingSequenceNumber(sequenceNumber);
					routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
					routingVOs.add(routingVO);
					sequenceNumber++;
					shipmentDetailVO.setRoutingDetails(routingVOs);
					if (isNotNullAndEmpty(mailbagVO.getDestination())
							&& !mailbagVO.getDestination().equals(flightLegDestination)) {
						LoginProfile logonAttributes = contextUtil.callerLoginProfile();
						routingVO = new RoutingVO();
						routingVO.setCompanyCode(logonAttributes.getCompanyCode());
						routingVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
						routingVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
						routingVO.setOrigin(flightLegDestination);
						routingVO.setDestination(mailbagVO.getDestination());
						routingVO.setAirportCode(mailbagVO.getDestination());
						routingVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
						routingVO.setRoutingSequenceNumber(sequenceNumber);
						routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
						shipmentDetailVO.getRoutingDetails().add(routingVO);
					}
					mailManifestDetailsVO.setFlightValidationVO(flightValidationVOs.iterator().next());
				}
			}
			break;
		}
	}

	public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsVO containerDetailsVO,
															  long mailSequenceNumber) {
		log.debug(CLASS + " : " + "validateContainerNumberForDeviatedMailbags" + " Entering");
		ErrorVO error = null;
		String containerNumberToValidate = constructDAO().findContainerInfoForDeviatedMailbag(containerDetailsVO,
				mailSequenceNumber);
		if (containerNumberToValidate != null) {
			String conNum = containerNumberToValidate.split("-")[0];
			String conType = containerNumberToValidate.split("-")[1];
			if ("B".equals(conType) && "B".equals(containerDetailsVO.getContainerType())) {
				error = null;
			} else if (!conNum.equals(containerDetailsVO.getContainerNumber())
					|| !conType.equals(containerDetailsVO.getContainerType())) {
				error = new ErrorVO("mailtracking.defaults.containernumbernotmatching");
			}
		}
		return error;
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @author A-5526 This method is used to find the details of approved Force Meajure info of a Mailbag
	 */
	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailBagId,
																			 long mailSequenceNumber) {
		log.debug(CLASS + " : " + "findMailbagHistories" + " Entering");
		return ForceMajeureRequest.findApprovedForceMajeureDetails(companyCode, mailBagId, mailSequenceNumber);
	}

	/**
	 * Method		:	MailController.constructFlightFilterVO Parameters	:	@param containerDetailsVO Parameters	:	@return Return type	: 	FlightFilterVO
	 */
	private FlightFilterVO constructFlightFilterVO(ContainerDetailsVO containerDetailsVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		flightFilterVO.setStation(containerDetailsVO.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		return flightFilterVO;
	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public MailbagInULDForSegmentVO getManifestInfo(MailbagVO mailbagVO) throws PersistenceException {
		return new MailbagInULDForSegment().getManifestInfoForNextSeg(mailbagVO);
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public String checkMailInULDExistForNextSeg(String containerNumber, String airpotCode, String companyCode) {
		log.debug(CLASS + " : " + "isValidContainerForULDlevelTransfer" + " Entering");
		return constructDAO().checkMailInULDExistForNextSeg(containerNumber, airpotCode, companyCode);
	}

	/**
	 * @return
	 * @throws SystemException
	 * @author A-8672 For IASCB-46064
	 */
	public void updateRetainFlagForContainer(ContainerVO containerVo) throws FinderException {
		Container.updateRetainFlag(containerVo);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagContainerAuditForRetaining(containerVo);
	}

	/**
	 * @param mailArrivalVO
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @author A-5526 Added for CRQ ICRD-233864
	 */
	public void onStatustoReadyforDelivery(MailArrivalVO mailArrivalVO) throws MailOperationsBusinessException {
		Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO contVO : containerDetailsVOs) {
				if (contVO.getMailDetails() != null && !contVO.getMailDetails().isEmpty()) {
					mailbagVOs.addAll(contVO.getMailDetails());
				}
			}
		}
		Collection<MailbagVO> tempMailbagVOs = validatemailbagforRFD(mailbagVOs);
		stampautoarrival(mailArrivalVO);
		triggerReadyfordeliveryResdit(mailArrivalVO, tempMailbagVOs, containerDetailsVOs);
		Collection<RateAuditVO> rateAuditVOs = createRateAuditVOs(mailArrivalVO.getContainerDetails(),
				MailConstantsVO.MAIL_STATUS_ARRIVED, false);
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
	}

	/**
	 * @param mailbagVOs
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 * @author A-5526 Added for CRQ ICRD-233864 To validate mailbags for readyFor Delivery
	 */
	private Collection<MailbagVO> validatemailbagforRFD(Collection<MailbagVO> mailbagVOs)
			throws MailOperationsBusinessException {
		Collection<MailbagVO> tempMailbagVOs = new ArrayList<MailbagVO>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO.setAutoArriveMail(MailConstantsVO.FLAG_YES);
			Mailbag mailbag = null;
			Mailbag mailbagToFindPA = null;
			String poaCode = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(
					findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
			try {
				mailbagToFindPA = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				e.getMessage();
			}
			if (mailbagToFindPA != null && mailbagToFindPA.getPaCode() != null) {
				poaCode = mailbagToFindPA.getPaCode();
			} else {
				OfficeOfExchangeVO originOfficeOfExchangeVO;
				//TODO: service to be corrected in Neo
//				originOfficeOfExchangeVO = OfficeOfExchange.validateOfficeOfExchange(mailbagVO.getCompanyCode(),
//						mailbagVO.getOoe());
//				poaCode = originOfficeOfExchangeVO.getPoaCode();
			}
			try {
				new MailArrival().isValidDeliveryAirport(mailbagVO.getDoe(), mailbagVO.getCompanyCode(),
						mailbagVO.getScannedPort(), null, MailConstantsVO.RESDIT_READYFOR_DELIVERY, poaCode,
						mailbagVO.getConsignmentDate());
			} catch (MailOperationsBusinessException e) {
				ErrorVO errorVo = new ErrorVO(INVALID_READYFOR_DELIVERY_AIRPORT);
				errorVo.setErrorType(ErrorType.ERROR);
				e.addError(errorVo);
				throw e;
			}
			try {
				mailbag = Mailbag.findMailbag(constructMailbagPK(mailbagVO));
			} catch (FinderException e) {
				mailbag = null;
			}
			if (mailbag != null) {
				if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())
						|| (!MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getLatestStatus())
						&& mailbag.getScannedPort().equals(mailbagVO.getScannedPort()))) {
					ErrorVO errorVo = new ErrorVO(ERROR_ALREADY_READY_FOR_DELIVED_MARKED);
					errorVo.setErrorType(ErrorType.ERROR);
					MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
					mailOperationsBusinessException.addError(errorVo);
					throw mailOperationsBusinessException;
				}
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getLatestStatus())
						|| MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus())) {
					mailbagVO.setOperationalFlag(null);
				}
				Collection<MailbagHistoryVO> resditEvents = null;
				try {
					resditEvents = constructDAO().findMailbagResditEvents(mailbagVO.getCompanyCode(),
							mailbagVO.getMailbagId());
				} catch (PersistenceException e) {
					throw new SystemException(e.getErrorCode());
				}
				for (MailbagHistoryVO mailbagHistoryVO : resditEvents) {
					if (MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(mailbagHistoryVO.getEventCode())) {
						ErrorVO errorVo = new ErrorVO(ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED);
						errorVo.setErrorType(ErrorType.ERROR);
						MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
						mailOperationsBusinessException.addError(errorVo);
						throw mailOperationsBusinessException;
					}
				}
			}
			mailbagVO.setRfdFlag(MailConstantsVO.FLAG_YES);
			mailbag.setRfdFlag(MailConstantsVO.FLAG_YES);
			MailbagVO tempMailbagVO = new MailbagVO();
			tempMailbagVO = mailOperationsMapper.copyMailbagVO(mailbagVO);
			tempMailbagVOs.add(tempMailbagVO);
		}
		return tempMailbagVOs;
	}

	/**
	 * @param mailArrivalVO
	 * @throws SystemException
	 * @author A-5526 Added for CRQ ICRD-233864 To perform auto-arrival ofmailbags
	 */
	private void stampautoarrival(MailArrivalVO mailArrivalVO) {
		try {
			saveArrivalDetails(mailArrivalVO);
		} catch (MailOperationsBusinessException e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * @param mailArrivalVO
	 * @param mailbagVOs
	 * @param containerDetailsVOs
	 * @throws SystemException
	 * @author A-5526 Added for CRQ ICRD-233864 To stamp ready for deliveryresdits
	 */
	private void triggerReadyfordeliveryResdit(MailArrivalVO mailArrivalVO, Collection<MailbagVO> mailbagVOs,
											   Collection<ContainerDetailsVO> containerDetailsVOs) {
		log.debug(CLASS + " : " + "triggerReadyfordeliveryResdit" + " Entering");
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		log.debug("" + "Resdit Enabled " + " " + resditEnabled);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			new ResditController().triggerReadyfordeliveryResdit(mailArrivalVO, mailbagVOs, containerDetailsVOs);
		}
		log.debug(CLASS + " : " + "triggerReadyfordeliveryResdit" + " Exiting");
	}
	/**
	 * @return
	 * @throws SystemException
	 * @author A-8672 For ICRD-255039
	 */
	public void updateActualWeightForMailbag(MailbagVO mailbagVO) {
		Mailbag.saveActualweight(mailbagVO);
	}

	public Collection<MailMonitorSummaryVO> getPerformanceMonitorDetails(MailMonitorFilterVO filterVO) {
		List<MailMonitorSummaryVO> MailMonitorSummaryVOs = new ArrayList<MailMonitorSummaryVO>();
		List<MailMonitorSummaryVO> serviceFailurVOs = new ArrayList<MailMonitorSummaryVO>();
		List<MailMonitorSummaryVO> ontimeDeliveryVOs = new ArrayList<MailMonitorSummaryVO>();
		List<MailMonitorSummaryVO> forceMajeureVOs = new ArrayList<MailMonitorSummaryVO>();
		try {
			serviceFailurVOs = constructDAO().getServiceFailureDetails(filterVO);
			MailMonitorSummaryVOs.addAll(serviceFailurVOs);
			ontimeDeliveryVOs = constructDAO().getOnTimePerformanceDetails(filterVO);
			MailMonitorSummaryVOs.addAll(ontimeDeliveryVOs);
			forceMajeureVOs = constructDAO().getForceMajeureCountDetails(filterVO);
			MailMonitorSummaryVOs.addAll(forceMajeureVOs);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return MailMonitorSummaryVOs;
	}

	public Page<MailbagVO> getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO, String type, int pageNumber) {
		log.debug(CLASS + " : " + "getPerformanceMonitorMailbags" + " Entering");
		Page<MailbagVO> mailbagVOs = null;
		try {
			mailbagVOs = constructDAO().getPerformanceMonitorMailbags(filterVO, type, pageNumber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		return mailbagVOs;
	}

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @author A-8438 Added for CRQ ICRD-303640
	 */
	public MailManifestVO findMailbagManifest(OperationalFlightVO operationalFlightVO) {
		//this.log.entering(CLASS, "findMailbagManifest");
		return MailAcceptance.findMailbagManifest(operationalFlightVO);
	}
	public MailManifestVO findMailAWBManifest(OperationalFlightVO operationalFlightVO) {
		return MailAcceptance.findMailAWBManifest(operationalFlightVO);
	}
	public MailManifestVO findDSNMailbagManifest(OperationalFlightVO operationalFlightVO) {
		return MailAcceptance.findDSNMailbagManifest(operationalFlightVO);
	}
	public MailManifestVO findDestnCatManifest(OperationalFlightVO operationalFlightVO) {
		return MailAcceptance.findDestnCatManifest(operationalFlightVO);
	}
	/**
	 * @author A-8464
	 * @param mailbagEnquiryFilterVO
	 * @return MailbagVO
	 * @throws SystemException
	 */
	public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		log.debug(CLASS + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		MailbagVO mailbagVO = null;
		try {
			mailbagVO = constructDAO().findMailbagDetailsForMailbagEnquiryHHT(mailbagEnquiryFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
		if (mailbagVO != null && mailbagVO.getLatestStatus() != null
				&& mailbagVO.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
			if (mailbagVO.getFlightNumber() != null && mailbagVO.getFlightDate() != null
					&& mailbagVO.getFlightSequenceNumber() > 0) {
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailbagEnquiryFilterVO.getCompanyCode());
				flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
				flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
				flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
				flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
				Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
				for (FlightValidationVO flightValidation : flightValidationVOs) {
					mailbagVO.setFlightDate(localDateUtil.getLocalDate(flightValidation.getSta()));
				}
			}
		}
		return mailbagVO;
	}

	/**
	 * Update the MALMST if mail sequence number already present Handle Unique Index violation <<< merge needed >>> <<< difference resolved - line(s) deleted >>> <<< merge needed >>> <<< difference resolved - line(s) deleted >>>
	 * @param carditVO
	 * @param mailInConsignmentVO
	 * @param receptacleVO
	 * @throws SystemException
	 */
	private void checkForExistingMailbag(CarditVO carditVO, MailInConsignmentVO mailInConsignmentVO,
										 CarditReceptacleVO receptacleVO) {
		long mailbagSequenceNumber;
		mailbagSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(receptacleVO.getReceptacleId(),
				carditVO.getCompanyCode());
		if (mailbagSequenceNumber > 0) {
			mailInConsignmentVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_UPDATE);
		}
		Mailbag mailbag = null;
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(mailInConsignmentVO.getCompanyCode());
		mailbagPk.setMailSequenceNumber(
				findMailSequenceNumber(mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
		try {
			mailbag = Mailbag.find(mailbagPk);
		} catch (FinderException finderException) {
			finderException.getMessage();
		}
		boolean isDuplicate = false;
		if (mailbag != null) {
			try {
				isDuplicate = checkForDuplicateMailbag(mailInConsignmentVO.getCompanyCode(),
						mailInConsignmentVO.getPaCode(), mailbag);
			} catch (DuplicateMailBagsException e) {
				e.getMessage();
			}
			if (isDuplicate) {
				mailInConsignmentVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);
			}
		}
	}

	/**
	 * Fetch flight capacity details of all segments in the flight Handle Unique Index violation <<< merge needed >>> <<< difference resolved - line(s) deleted >>>
	 * @param flightFilterVOs
	 * @return FlightSegmentCapacitySummaryVO
	 * @throws SystemException
	 */
	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs) {
		Set<String> flightkeySet = new HashSet<String>();
		StringBuilder flightKey = null;
		Collection<FlightSegmentCapacitySummaryVO> segmentCapacityVos = flightOperationsProxy
				.fetchFlightCapacityDetails(flightFilterVOs);
		return segmentCapacityVos;
	}

	/**
	 * Method : MailController.findDuplicateMailbag Added by : A-7531 on 16-May-2019 Used for : Parameters : @param companyCode Parameters : @param mailBagId Parameters : @return Return type : Collection<MailbagHistoryVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId) {
		log.debug(CLASS + " : " + "findDuplicateMailbag" + " Entering");
		return Mailbag.findDuplicateMailbag(companyCode, mailBagId);
	}

	/**
	 * Method : MailController.getRunnerFlightVOForContainerFilter Added by : A-7414 on 01-Jul-2019 Parameters : @param runnerFlightVO Parameters : @return Return type : RunnerFlightVO
	 */
	private RunnerFlightVO getRunnerFlightVOForContainerFilter(RunnerFlightVO runnerFlightVO) {
		log.debug(CLASS + " : " + "getRunnerFlightVOForContainerFilter" + " Entering");
		RunnerFlightVO runnerFlightVoForContainer = new RunnerFlightVO();
		runnerFlightVoForContainer.setCompanyCode(runnerFlightVO.getCompanyCode());
		runnerFlightVoForContainer.setCarrierCode(runnerFlightVO.getCarrierCode());
		runnerFlightVoForContainer.setCarrierId(runnerFlightVO.getCarrierId());
		runnerFlightVoForContainer.setFlightNumber(runnerFlightVO.getFlightNumber());
		runnerFlightVoForContainer.setFlightSequenceNumber(-1);
		runnerFlightVoForContainer.setLegSerialNumber(runnerFlightVO.getLegSerialNumber());
		runnerFlightVoForContainer.setAirportCode(runnerFlightVO.getAirportCode());
		log.debug(CLASS + " : " + "getRunnerFlightVOForContainerFilter" + " Exiting");
		return runnerFlightVoForContainer;
	}
	/**
	 * Method : MailController.findStationParameterByCode Added by : U-1307 on 16-Aug-2019 Used for : Finding station parameters Parameters : @param companyCode Parameters : @param stationCode Parameters : @param parameterCodes Parameters : @return Parameters : @throws SystemException Return type : Map<String,String>
	 */
	public Map<String, String> findStationParametersByCode(String companyCode, String stationCode,
														   Collection<String> parameterCodes) {
		Map<String, String> stationParameters = null;
		try {
			stationParameters = sharedAreaProxy.findStationParametersByCode(companyCode, stationCode, parameterCodes);
		} finally {
		}
		return stationParameters;
	}

	/**
	 * Method		:	MailController.updateMailULDDetailsFromMHS Added by 	:	A-8164 on 15-Feb-2021 Used for 	: Parameters	:	@param storageUnitCheckinVO Parameters	:	@throws SystemException  Return type	: 	void
	 */
	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO) {
		boolean isUpdated = false;
		ContainerAssignmentVO latestContainerAssignmentVO = findLatestContainerAssignment(
				storageUnitCheckinVO.getStorageUnitCode());
		if (Objects.nonNull(latestContainerAssignmentVO)) {
			ContainerPK containerPk = new ContainerPK();
			Container container = null;
			containerPk.setContainerNumber(storageUnitCheckinVO.getStorageUnitCode());
			containerPk.setAssignmentPort(storageUnitCheckinVO.getAirportCode());
			containerPk.setCarrierId(latestContainerAssignmentVO.getCarrierId());
			containerPk.setFlightNumber(latestContainerAssignmentVO.getFlightNumber());
			containerPk.setFlightSequenceNumber(latestContainerAssignmentVO.getFlightSequenceNumber());
			containerPk.setLegSerialNumber(latestContainerAssignmentVO.getLegSerialNumber());
			containerPk.setCompanyCode(storageUnitCheckinVO.getCompanyCode());
			try {
				container = Container.find(containerPk);
				if (container != null) {
					isUpdated = true;
					//TODO: Neo to correct below
//					container.setActualWeight(new Quantity(UnitConstants.WEIGHT, storageUnitCheckinVO.getWeight())
//							.getValue().doubleValue());
					if (Objects.nonNull(storageUnitCheckinVO.getAdditionalInfo())
							&& !storageUnitCheckinVO.getAdditionalInfo().isEmpty()) {
						if (Objects.nonNull(storageUnitCheckinVO.getAdditionalInfo()
								.get(StorageUnitCheckinVO.ADDITIONAL_REMARKS))) {
							container.setRemarks(storageUnitCheckinVO.getAdditionalInfo()
									.get(StorageUnitCheckinVO.ADDITIONAL_REMARKS));
						}
						if (Objects.nonNull(
								storageUnitCheckinVO.getAdditionalInfo().get(StorageUnitCheckinVO.ULD_HEIGHT))) {
							container.setUldHeight(Double.parseDouble(
									storageUnitCheckinVO.getAdditionalInfo().get(StorageUnitCheckinVO.ULD_HEIGHT)));
						}
					}
				}
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
			}
		}
		return isUpdated;
	}

	public void insertOrUpdateAuditDetailsForCardit(CarditVO carditVO) {
		log.debug(CLASS + " : " + "insertOrUpdateAuditDetailsForCardit" + " Entering");
		log.debug(CLASS + " : " + "insertOrUpdateAuditDetailsForCardit" + " Exiting");
	}

	public void flagMailbagAuditForResdit(ResditEventVO resditEventVO, ConsignmentInformationVO consignVO,
										  ReceptacleInformationVO receptacleInformationVO) {
		log.debug(CLASS + " : " + "auditCarditCancellation" + " Entering");
		log.debug(CLASS + " : " + "auditCarditCancellation" + " Exiting");
	}

	/**
	 * Method		:	MailController.createAutoAttachAWBJobSchedule Added by 	:	U-1467 on 23-Sep-2020 Used for 	:	IASCB-72629 Parameters	:	@param autoAttachAWBJobScheduleVO Parameters	:	@throws SystemException Return type	: 	void
	 */
	public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO) {
		if (isNotNullAndEmpty(autoAttachAWBJobScheduleVO.getActualTimeOfDeparture())
				&& !isScheduleExistsForAutoAttachAWB(autoAttachAWBJobScheduleVO)) {
			ZonedDateTime actualTimeOfDeparture = getLocalDate(autoAttachAWBJobScheduleVO.getPol(),
					autoAttachAWBJobScheduleVO.getActualTimeOfDeparture(), true);
			ZonedDateTime startTime = localDateUtil.getLocalDateTime(actualTimeOfDeparture,
					autoAttachAWBJobScheduleVO.getPol());
			String minimumTime = "0";
			Collection<String> parameters = new ArrayList<>();
			parameters.add(MailConstantsVO.AUTO_ATTACH_AWB_MINIMUM_DURATION);
			Map<String, String> parameterMap = sharedAreaProxy.findAirportParametersByCode(
					autoAttachAWBJobScheduleVO.getCompanyCode(), autoAttachAWBJobScheduleVO.getPol(), parameters);
			if (parameterMap != null && parameterMap.size() > 0) {
				minimumTime = parameterMap.get(MailConstantsVO.AUTO_ATTACH_AWB_MINIMUM_DURATION);
			}
			startTime.plusMinutes(Integer.parseInt(minimumTime));
			autoAttachAWBJobScheduleVO.setStartTime(LocalDateMapper.toLocalDate(startTime));
			ZonedDateTime endTime = localDateUtil.getLocalDateTime(actualTimeOfDeparture,
					autoAttachAWBJobScheduleVO.getPol());
			endTime.plusMinutes(Integer.parseInt(minimumTime) + 30);
			autoAttachAWBJobScheduleVO.setEndTime(LocalDateMapper.toLocalDate(endTime));
			autoAttachAWBJobScheduleVO.setJobName(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_AWB_JOB_NAME);
			autoAttachAWBJobScheduleVO.setRetryCount(2);
			autoAttachAWBJobScheduleVO.setRetryDelay(3);
			//TODO: Job to be corrected
//			try {
//				SchedulerAgent.getInstance().createScheduleForJob(autoAttachAWBJobScheduleVO);
//			} finally {
//			}
		}
	}

	/**
	 * Method		:	MailController.isScheduleExistsForAutoAttachAWB Added by 	:	U-1467 on 23-Sep-2020 Used for 	:	IASCB-72629 Parameters	:	@param autoAttachAWBJobScheduleVO Parameters	:	@return Return type	: 	boolean
	 */
	private boolean isScheduleExistsForAutoAttachAWB(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO) {
		//TODO: Job to be corrected in Neo
//		Collection<ScheduleVO> scheduleVOs = null;
//		ScheduleVO scheduleVO = constructScheduleVO(autoAttachAWBJobScheduleVO);
//		try {
//			scheduleVOs = SchedulerAgent.getInstance().findExistingSchedulesForJob(scheduleVO);
//		} finally {
//		}
//		return (scheduleVOs != null && scheduleVOs.size() > 0);
		return false;
	}

	/**
	 * Method		:	MailController.constructScheduleVO Added by 	:	U-1467 on 23-Sep-2020 Used for 	:	IASCB-72629 Parameters	:	@param autoAttachAWBJobScheduleVO Parameters	:	@return Return type	: 	ScheduleVO
	 */
//	private ScheduleVO constructScheduleVO(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO) {
		//TODO: Neo job to be corrected
//		ScheduleVO scheduleVO = new ScheduleVO();
//		scheduleVO.setOwnerId(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_AWB_JOBIDR);
//		scheduleVO.setCompanyCode(autoAttachAWBJobScheduleVO.getCompanyCode());
//		Map<String, String> attributes = new HashMap<>();
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_CMPCOD, autoAttachAWBJobScheduleVO.getCompanyCode());
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_CARIDR,
//				String.valueOf(autoAttachAWBJobScheduleVO.getCarrierId()));
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_CARCOD, autoAttachAWBJobScheduleVO.getCarrierCode());
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_FLTNUM, autoAttachAWBJobScheduleVO.getFlightNumber());
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_FLTSEQNUM,
//				String.valueOf(autoAttachAWBJobScheduleVO.getFlightSequenceNumber()));
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_POL, autoAttachAWBJobScheduleVO.getPol());
//		attributes.put(AutoAttachAWBJobScheduleVO.MAL_AUTO_ATTACH_ATD,
//				autoAttachAWBJobScheduleVO.getActualTimeOfDeparture());
//		scheduleVO.setAttributes(attributes);
//		return scheduleVO;
//		return null;
//	}

	/**
	 * @param consignmentNumber
	 * @param companyCode
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-9084
	 */
	public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,
																 String poaCode) throws FinderException, PersistenceException {
		log.debug(CLASS + " : " + "findConsignmentScreeningDetails" + " Entering");
		return constructDAO().findConsignmentScreeningDetails(consignmentNumber, companyCode, poaCode);
	}

	public void createMailbagAuditForResdit(ResditEventVO resditEventVO, ConsignmentInformationVO consignVO,
											ReceptacleInformationVO receptacleInformationVO) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagMailbagAuditForResdit(resditEventVO, consignVO, receptacleInformationVO);
	}
	/**
	 * @author A-8893
	 * @throws SystemException
	 */
	public void saveTransferFromManifest(TransferManifestVO transferManifestVO) throws InvalidFlightSegmentException,
			CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
		ContainerVO containerVO = constructContainerVO(transferManifestVO);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(transferManifestVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(transferManifestVO.getMailsequenceNumber());
		Mailbag mailbag = new Mailbag();
		try {
			mailbag = Mailbag.find(mailbagPK);
		} catch (FinderException e) {
			log.info(e.getMessage());
		}
		MailbagVO mailbagvo = populateMailbagVofromMailbag(mailbag, transferManifestVO);
		Collection<MailbagVO> mailbagvos = new ArrayList<>();
		mailbagvos.add(mailbagvo);
		if (mailbagvo.getOperationalStatus().equals("O")) {
			try {
				transferMailAtExport(mailbagvos, containerVO, "N");
			} catch (FlightClosedException e) {
				e.getMessage();
			}
		} else {
			transferMail(null, mailbagvos, containerVO, null);
		}
		if (mailbagvo.getMailSequenceNumber() > 0) {
			String transferManifestId = null;
			try {
				transferManifestId = constructDAO().findTransferManifestId(mailbagvo.getCompanyCode(),
						mailbagvo.getMailSequenceNumber());
			} catch (PersistenceException e) {
				e.getMessage();
			}
			if (transferManifestId != null && transferManifestId.trim().length() > 0) {
				TransferManifestDSN transferManifestDSN = null;
				TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
				transferManifestDSNPK.setCompanyCode(mailbagvo.getCompanyCode());
				transferManifestDSNPK.setTransferManifestId(transferManifestId);
				transferManifestDSNPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
				try {
					transferManifestDSN = TransferManifestDSN.find(transferManifestDSNPK);
				} catch (FinderException e) {
					e.getMessage();
				}
				if (transferManifestDSN != null) {
					transferManifestDSN.setTransferStatus("TRFEND");
				}
			}
		}
		TransferManifestPK transferManifestPK = new TransferManifestPK();
		transferManifestPK.setCompanyCode(transferManifestVO.getCompanyCode());
		transferManifestPK.setTransferManifestId(transferManifestVO.getTransferManifestId());
		TransferManifest trasferManifest = new TransferManifest();
		try {
			trasferManifest = TransferManifest.find(transferManifestPK);
		} catch (FinderException e) {
			log.info(e.getMessage());
		}
		trasferManifest.setTransferStatus("TRFEND");
		ZonedDateTime trfDate = localDateUtil.getLocalDate(transferManifestVO.getAirPort(), true);
		trasferManifest.setTransferDate(trfDate);
	}
	/**
	 * @author A-8893
	 * @throws SystemException
	 */
	public void rejectTransferFromManifest(TransferManifestVO transferManifestVO) {
		TransferManifestPK transferManifestPK = new TransferManifestPK();
		transferManifestPK.setCompanyCode(transferManifestVO.getCompanyCode());
		if (transferManifestVO.getTransferManifestId() != null) {
			transferManifestPK.setTransferManifestId(transferManifestVO.getTransferManifestId());
		} else {
			String transferManifestId = null;
			try {
				transferManifestId = constructDAO().findTransferManifestId(transferManifestVO.getCompanyCode(),
						transferManifestVO.getMailsequenceNumber());
				transferManifestPK.setTransferManifestId(transferManifestId);
			} catch (PersistenceException e) {
				log.info("Exception :", e);
			}
		}
		TransferManifest trasferManifest = new TransferManifest();
		try {
			trasferManifest = TransferManifest.find(transferManifestPK);
		} catch (FinderException e) {
			e.printStackTrace();
		} finally {
		}
		trasferManifest.setTransferStatus(TRANSER_STATUS_REJECT);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(transferManifestVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(transferManifestVO.getMailsequenceNumber());
		Mailbag mailbag = new Mailbag();
		try {
			mailbag = Mailbag.find(mailbagPK);
		} catch (FinderException e) {
			log.info(e.getMessage());
		}
		MailbagVO mailbagvo = populateMailbagVofromMailbag(mailbag, transferManifestVO);
		updateTransferOutDetailsForHistory(mailbagvo, transferManifestVO);
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagHistoryforTransferRejection(mailbagvo, getTriggerPoint());
	}

	/**
	 * @author A-8353
	 * @param containerVOs
	 * @param operationalFlightVO
	 * @param containerVOsForTransferOut
	 * @throws SystemException
	 */
	public void updateContainerForTransfer(Collection<ContainerVO> containerVOs,
										   OperationalFlightVO operationalFlightVO, Collection<ContainerVO> containerVOsForTransferOut,
										   Collection<ContainerVO> containerVOsForTransfer) {
		if (containerVOs != null && !containerVOs.isEmpty()) {
			for (ContainerVO containerVO : containerVOs) {
				String transferStatus = null;
				if (operationalFlightVO.getCarrierCode() != null && containerVO.getCarrierCode() != null) {
					transferStatus = checkForContainerTransferStatus(containerVO.getCarrierCode(),
							operationalFlightVO.getCarrierCode());
				}
				if (MailConstantsVO.TRANSFER_OUT.equals(transferStatus)) {
					containerVOsForTransferOut.add(containerVO);
				} else if (MailConstantsVO.TRANSFER_IN.equals(transferStatus)) {
					containerVO.setHandoverReceived(true);
					containerVOsForTransfer.add(containerVO);
				} else {
					containerVOsForTransfer.add(containerVO);
				}
			}
		}
	}

	/**
	 * @author A-8353
	 * @param fromCarrierCode
	 * @param toCarrierCode
	 * @return
	 * @throws SystemException
	 */
	private String checkForContainerTransferStatus(String fromCarrierCode, String toCarrierCode) {
		String transferStatus = null;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (fromCarrierCode != null && fromCarrierCode.trim().length() > 0 && toCarrierCode != null
				&& toCarrierCode.trim().length() > 0) {
			boolean isFomCarrierCodePartner = checkIfPartnerCarrier(logonAttributes.getAirportCode(), fromCarrierCode);
			boolean isToCarrierCodePartner = checkIfPartnerCarrier(logonAttributes.getAirportCode(), toCarrierCode);
			if (isFomCarrierCodePartner && isToCarrierCodePartner) {
				transferStatus = MailConstantsVO.MAIL_STATUS_ASSIGNED;
			} else if (!isFomCarrierCodePartner && isToCarrierCodePartner) {
				transferStatus = MailConstantsVO.TRANSFER_IN;
			} else {
				transferStatus = MailConstantsVO.TRANSFER_OUT;
			}
		}
		return transferStatus;
	}

	/**
	 * @author A-8353
	 * @param containerVOs
	 * @return
	 * @throws SystemException
	 */
	private Collection<ContainerDetailsVO> createContainerDtlsForTransfermanifest(
			Collection<ContainerVO> containerVOs) {
		log.debug("MailTransfer" + " : " + "createContainerDtlsForTransfermanifest" + " Entering");
		Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<>();
		ContainerDetailsVO containerDetails = null;
		if (containerVOs != null & !containerVOs.isEmpty()) {
			for (ContainerVO containerVO : containerVOs) {
				Collection<DSNVO> dsnVOs = new ArrayList<>();
				containerDetails = new ContainerDetailsVO();
				Collection<ContainerDetailsVO> containerDetailVOs = new ArrayList<>();
				containerDetails.setCompanyCode(containerVO.getCompanyCode());
				containerDetails.setCarrierId(containerVO.getCarrierId());
				containerDetails.setFlightNumber(containerVO.getFlightNumber());
				containerDetails.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerDetails.setLegSerialNumber(containerVO.getLegSerialNumber());
				containerDetails.setPol(containerVO.getPol());
				containerDetails.setContainerNumber(containerVO.getContainerNumber());
				containerDetails.setContainerType(containerVO.getType());
				containerDetailVOs.add(containerDetails);
				containerDetailVOs = new MailController().findMailbagsInContainer(containerDetailVOs);
				if (containerDetailVOs != null && !containerDetailVOs.isEmpty()
						&& containerDetailVOs.iterator().next().getMailDetails() != null
						&& !containerDetailVOs.iterator().next().getMailDetails().isEmpty()) {
					DSNVO dsnVo = null;
					for (MailbagVO mailbagVO : containerDetailVOs.iterator().next().getMailDetails()) {
						dsnVo = new DSNVO();
						dsnVo.setCompanyCode(mailbagVO.getCompanyCode());
						dsnVo.setOriginExchangeOffice(mailbagVO.getOoe());
						dsnVo.setDestinationExchangeOffice(mailbagVO.getDoe());
						dsnVo.setMailCategoryCode(mailbagVO.getMailCategoryCode());
						dsnVo.setBags(1);
						dsnVo.setWeight(mailbagVO.getWeight());
						dsnVo.setYear(mailbagVO.getYear());
						dsnVo.setDsn(mailbagVO.getDespatchSerialNumber());
						dsnVo.setMailSubclass(mailbagVO.getMailSubclass());
						dsnVo.setMailClass(mailbagVO.getMailClass());
						dsnVo.setContainerNumber(mailbagVO.getUldNumber());
						dsnVo.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						dsnVOs.add(dsnVo);
					}
				}
				containerDetails.setDsnVOs(dsnVOs);
				containerDetails.setCompanyCode(containerVO.getCompanyCode());
				containerDetails.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsColl.add(containerDetails);
			}
		}
		log.debug("" + "THE CONTAINER DETAILS CONSTRUCTED" + " " + containerDetails);
		return containerDetailsColl;
	}

	public void removeFromInbound(OffloadVO offloadVo) throws MailOperationsBusinessException {
		try {
			offload(offloadVo);
		} catch (FlightClosedException | FlightDepartedException | ReassignmentException | ULDDefaultsProxyException
				| CapacityBookingProxyException | MailBookingException e) {
			throw new MailOperationsBusinessException(e);
		}
	}
	public MailbagVO findMailBillingStatus(MailbagVO mailbagVO) {
		return constructDAO().findMailbagBillingStatus(mailbagVO);
	}
    /**
	 * Method		:	MailController.saveUploadedForceMajeureData Added by 	:	A-6245 on 22-Jan-2021 Used for 	:	IASCB-83866 Parameters	:	@param fileUploadFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	 */
	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) {
		String processStatus = "PE";
		try {
			processStatus = processMailDataFromExcel(fileUploadFilterVO);
		} catch (PersistenceException e) {
			log.error("PersistenceException Caught");
		}
		if (MailConstantsVO.OK_STATUS.equals(processStatus)) {
			return constructDAO().saveUploadedForceMajeureData(fileUploadFilterVO);
		}
		return processStatus;
	}
	/**
	 * Method		:	MailController.fetchConsignmentDetailsForUpload Added by 	:	A-6245 on 22-Dec-2020 Used for 	:	IASCB-81526 Parameters	:	@param fileUploadFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<ConsignmentDocumentVO>
	 */
	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO) {
		return constructDAO().fetchConsignmentDetailsForUpload(fileUploadFilterVO);
	}

	/**
	 * Method		:	MailController.saveMailbagHistory Added by 	:	A-6245 on 22-Dec-2020 Used for 	:	IASCB-81526 Parameters	:	@param mailbagHistoryVOs Parameters	:	@throws SystemException  Return type	: 	void
	 */
	public void saveMailbagHistory(Collection<MailbagHistoryVO> mailbagHistoryVOs) {
		if (Objects.nonNull(mailbagHistoryVOs) && !mailbagHistoryVOs.isEmpty()) {
			for (MailbagHistoryVO mailbagHistoryVO : mailbagHistoryVOs) {
				try {
					saveMailbagHistory(mailbagHistoryVO);
				} catch (BusinessException exception) {
					throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
				}
			}
		}
	}

	public void saveMailbagHistory(MailbagHistoryVO mailbagHistoryVO) throws BusinessException {
		SaveMailbagHistoryFeature saveMailbagHistoryFeature = ContextUtil.getInstance()
				.getBean(SaveMailbagHistoryFeature.class);
		saveMailbagHistoryFeature.execute(mailbagHistoryVO);
	}

	/**
	 * Method		:	MailController.flagContainerAuditForDeletion Added by 	:	A-9084 on 26-Mar-2021 Used for 	:	IASCB-84649 Parameters	:	@param containerDetailsVO Return type	: 	void
	 */
	public void flagContainerAuditForDeletion(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "flagContainerAuditForDeletion" + " Entering");
		log.debug(CLASS + " : " + "flagContainerAuditForDeletion" + " Exiting");
	}

	/**
	 * Method		:	MailController.flagContainerAuditForUnassignment Added by 	:	A-9084 on 29-Mar-2021 Used for 	:	IASCB-84649 Parameters	:	@param containerDetailsVO Return type	: 	void
	 */
	public void flagContainerAuditForUnassignment(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "flagContainerAuditForUnassignment" + " Entering");
		log.debug(CLASS + " : " + "flagContainerAuditForUnassignment" + " Exiting");
	}
	/**
	 * @param documentFilterVO
	 * @return String
	 * @throws SystemException
	 * @throws StockcontrolDefaultsProxyException
	 * @author U-1519
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
			throws StockcontrolDefaultsProxyException, BusinessException {
		log.debug(CLASS + " : " + "findNextDocumentNumber" + " Entering");
		return stockcontrolDefaultsProxy.findAutoPopulateSubtype(documentFilterVO);
	}

	/**
	 * @author A-8353
	 * @param mailbagVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void updateOriginAndDestinationForMailbag(Collection<MailbagVO> mailbagVOs) {
		for (MailbagVO mailbagVO : mailbagVOs) {
			Mailbag.updateOriginAndDestinationForMailbag(mailbagVO);
		}
		if (isImportToMRAEnabled()) {
			reImportMailbagsToMRA(mailbagVOs);
		}
	}
	public long insertMailbagAndHistory(MailbagVO mailbagVO) {
		Mailbag mailbag = new Mailbag(mailbagVO);
		MailbagHistoryVO historyVO = historyBuilder.constructMailHistoryVO(mailbagVO);
		new MailbagHistory( historyVO);
		return mailbag.getMailSequenceNumber();
	}

	public void flagContainerAuditForAcquital(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS + " : " + "flagContainerAuditForAcquital" + " Entering");
		log.debug(CLASS + " : " + "flagContainerAuditForAcquital" + " Exiting");
	}

	public void flagContainerAuditForRetaining(ContainerVO containerVO) {
		log.debug(CLASS + " : " + "flagContainerAuditForRetaining" + " Entering");
		var triggerPoint = "MTK064";
		var transaction = "UPDATED";
		var actionCode = MailConstantsVO.CONTAINER_RETAINED;

		var additionalInfo = "Retained to  ";
		if(!"-1".equals(containerVO.getFlightNumber())){
			additionalInfo += containerVO.getCarrierCode() + " " + containerVO.getFlightNumber() + ", ";
		}else{
			additionalInfo += containerVO.getCarrierCode() + ", ";
		}
		additionalInfo += new LocalDate().getLocalDate(getLogonAttributes().getAirportCode(), true).toString();
		additionalInfo += containerVO.getPol() + " - " + containerVO.getPou() + " ";
		additionalInfo += "in " + getLogonAttributes().getAirportCode();

		auditUtils.performAudit(new AuditConfigurationBuilder()
				.withBusinessObject(containerVO)
				.withTriggerPoint(triggerPoint)
				.withActionCode(actionCode)
				.withEventName("containerUpdate")
				.withAdditionalInfo(additionalInfo)
				.withtransaction(transaction).build());

		log.debug(CLASS + " : " + "flagContainerAuditForRetaining" + " Exiting");
	}

	public void releaseContainers(Collection<ContainerVO> containerVOs) {
		if (containerVOs != null && !containerVOs.isEmpty()) {
			for (ContainerVO containerVO : containerVOs) {
				ContainerPK containerPK = new ContainerPK();
				containerPK.setCompanyCode(containerVO.getCompanyCode());
				containerPK.setCarrierId(containerVO.getCarrierId());
				containerPK.setFlightNumber(containerVO.getFlightNumber());
				containerPK.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerPK.setAssignmentPort(containerVO.getPol());
				containerPK.setContainerNumber(containerVO.getContainerNumber());
				containerPK.setLegSerialNumber(Container.findFlightLegSerialNumber(containerVO));
				Container containerToUpdate = null;
				try {
					containerToUpdate = Container.find(containerPK);
					containerToUpdate.setTransitFlag(MailConstantsVO.FLAG_NO);
				} catch (FinderException finderException) {
					throw new SystemException(finderException.getErrorCode(), finderException.getMessage(),
							finderException);
				}
			}
		}
	}
	/**
	 * Method		:	MailController.findContainerJourneyID Added by 	:	A-6245 on 15-Jun-2021 Parameters	:	@param containerFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<ContainerDetailsVO>
	 */
	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().findContainerJourneyID(consignmentFilterVO);
	}

	public void closeMailInboundFlight(OperationalFlightVO operationalFlightVO) throws BusinessException {
		CloseMailInboundFlightFeature closeMailInboundFlightFeature = ContextUtil.getInstance()
				.getBean(CloseMailInboundFlightFeature.class);
		closeMailInboundFlightFeature.execute(operationalFlightVO);
		flagResditsForCloseMailInboundFlight(operationalFlightVO);
	}

	public void stampResdits(Collection<MailResditVO> mailResditVOs) throws MailOperationsBusinessException {
		if (Objects.nonNull(mailResditVOs) && !mailResditVOs.isEmpty()) {
			for (MailResditVO mailResditVO : mailResditVOs) {
				try {
					stampResdit(mailResditVO);
				} catch (BusinessException exception) {
					throw new MailOperationsBusinessException(exception);
				}
			}
		}
	}

	public void stampResdit(MailResditVO mailResditVO) throws BusinessException {
		StampResditFeature stampResditFeature = ContextUtil.getInstance().getBean(StampResditFeature.class);
		stampResditFeature.execute(mailResditVO);
	}

	public Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO operationalFlightVO) {
		return Mailbag.findArrivalDetailsForReleasingMails(operationalFlightVO);
	}

	/**
	 * Added for CRQ IAN-6113 changed autoAttachAWBDetails method to feature
	 * @author A-8893
	 * @param containerDetailsVOs
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> autoAttachAWBDetails(Collection<ContainerDetailsVO> containerDetailsVOs,
															   OperationalFlightVO operationalFlightVO) {
		log.debug("mailController" + " : " + "autoAttachAWBDetailsFeature" + " Entering");
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		mailManifestDetailsVO.setContainerDetailsVOs(containerDetailsVOs);
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				containerDetailsVO.setFlightDate(operationalFlightVO.getFlightDate());
				if (MailConstantsVO.OPERATION_OUTBOUND.equals(operationalFlightVO.getDirection())
						&& containerDetailsVO.getFromScreen() == null) {
					containerDetailsVO.setFromScreen(MailConstantsVO.MAILOUTBOUND_SCREEN);
				}
			}
		}
		AirlineValidationVO airlineValidationVO = null;
		HashMap<String, Collection<MailbagVO>> mailbagsMap = null;
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			mailbagsMap = createMailbagsMap(containerDetailsVOs, operationalFlightVO);
			try {
				airlineValidationVO = sharedAirlineProxy.validateAlphaCode(operationalFlightVO.getCompanyCode(),
						operationalFlightVO.getCarrierCode());
			} catch (SharedProxyException sharedProxyException) {
				throw new SystemException(sharedProxyException.getMessage());
			}
			mailManifestDetailsVO.setAirlineValidationVO(airlineValidationVO);
			mailManifestDetailsVO.setMailbagsMap(mailbagsMap);
			eventTriggerForAutoAttach(mailManifestDetailsVO);
		}
		return containerDetailsVOs;
	}

	private void eventTriggerForAutoAttach(MailManifestDetailsVO mailManifestDetailsVO) {
		Collection<MailbagVO> mailbagVOs = null;
		if (mailManifestDetailsVO.getMailbagsMap() != null && mailManifestDetailsVO.getMailbagsMap().size() > 0) {
			Iterator<Collection<MailbagVO>> iterator = mailManifestDetailsVO.getMailbagsMap().values().iterator();
			while (iterator.hasNext()) {
				mailbagVOs = iterator.next();
				if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
					mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
					AutoAttachAWBDetailsFeature autoAttachAWBDetailsFeature = ContextUtil.getInstance()
							.getBean(AutoAttachAWBDetailsFeature.class);
					autoAttachAWBDetailsFeature.execute(mailManifestDetailsVO);
				}
			}
		}
	}
	/**
	 * @author A-9084
	 * @param mailAuditFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO) {
		return constructDAO().findAssignFlightAuditDetails(mailAuditFilterVO);
	}

	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO) {
		return Mailbag.getFoundArrivalMailBags(mailArrivalVO);
	}

	private void performAuditForInboundFlightReopen(AssignedFlightAuditVO assignedFlightAuditVO,
													AssignedFlight inboundFlight, OperationalFlightVO operationalFlightVO) {
		Collection<ContainerDetailsVO> containerDetailsVO = findArrivalDetailsForReleasingMails(operationalFlightVO);
		int containerCount = 0;
		int mailbagCount = 0;
		HashSet<String> containers = new HashSet<>();
		if (containerDetailsVO != null) {
			for (ContainerDetailsVO containerDetailsVo : containerDetailsVO) {
				for (MailbagVO mailbags : containerDetailsVo.getMailDetails()) {
					if (MailConstantsVO.FLAG_YES.equals(mailbags.getArrivedFlag()) && mailbags.getMailbagId() != null) {
						containers.add(containerDetailsVo.getContainerNumber());
						mailbagCount++;
					}
				}
			}
		}
		if (!containers.isEmpty()) {
			containerCount = containers.size();
		}
		if (inboundFlight != null) {
			//TODO: Neo to rework on audit
//			StringBuilder additInfo = new StringBuilder();
//			additInfo.append("Arrival Airport: " + inboundFlight.getAirportCode());
//			additInfo.append(", No of Containers: " + containerCount + ", No of Mailbags: " + mailbagCount);
//			assignedFlightAuditVO.setAdditionalInformation(additInfo.toString());
//			String triggeringPoint = ContextUtils.getRequestContext().getParameter(MailConstantsVO.REQ_TRIGGERPOINT);
//			assignedFlightAuditVO.setTriggerPoint(triggeringPoint);
//			performAssignedFlightAudit(assignedFlightAuditVO, inboundFlight, MailConstantsVO.IMPORT_FLIGHT_REOPEN);
		}
	}

	private Collection<MailbagVO> populateMailBagVoForAttachAWB(ContainerDetailsVO containerDetailsVO) {
		Collection<ContainerDetailsVO> containerDetailsVOList = new ArrayList<>();
		containerDetailsVOList.add(containerDetailsVO);
		Collection<MailbagVO> mailbagVOs = null;
		if (containerDetailsVO.getDsnVOs() != null && !containerDetailsVO.getDsnVOs().isEmpty()
				&& ((MailConstantsVO.DSN_VIEW).equals(containerDetailsVO.getActiveTab())
				|| containerDetailsVO.isFromContainerTab())) {
			try {
				mailbagVOs = constructDAO().findMailbagVOsForDsnVOs(containerDetailsVO);
			} catch (PersistenceException exception) {
				throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
			}
		} else {
			try {
				containerDetailsVOList = constructDAO()
						.findMailbagsInContainerWithoutAcceptance(containerDetailsVOList);
			} catch (PersistenceException persistenceException) {
				persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getErrorCode());
			}
			containerDetailsVO = containerDetailsVOList.iterator().next();
			mailbagVOs = containerDetailsVO.getMailDetails();
		}
		return mailbagVOs;
	}

	public void markUnmarkUldIndicator(ContainerVO containerVo) {
		try {
			Container.markUnmarkUldIndicator(containerVo);
		} catch (FinderException e) {
			log.info("Exception :", e);
		}
	}

	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightVO> operationalFlightVOs) {
		log.debug(CLASS + " : " + "triggerEmailForPureTransferContainers" + " Entering");
		Collection<MailAcceptanceVO> mailAcceptanceVOs = null;
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		if (!operationalFlightVOs.isEmpty()) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				mailAcceptanceVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				mailAcceptanceVO.setCarrierId(operationalFlightVO.getCarrierId());
				mailAcceptanceVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				mailAcceptanceVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				mailAcceptanceVO.setFlightDestination(operationalFlightVO.getPou());
			}
			try {
				mailAcceptanceVOs = constructDAO().findContainerVOs(mailAcceptanceVO);
			} catch (PersistenceException exception) {
				throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
			}
			for (MailAcceptanceVO mailAcceptanceVo : mailAcceptanceVOs) {
				if (!mailAcceptanceVo.getContainerDetails().isEmpty()) {
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.triggerMailNotification(mailAcceptanceVo);
				}
			}
		}
	}

	public void triggerMailNotification(MailAcceptanceVO mailAcceptanceVO) {
		log.debug(CLASS + " : " + "triggerMailNotification" + " Entering");
	}

	private Collection<ErrorVO> savePAWBDetailsFromCardit(CarditVO carditVO,
														  ConsignmentDocumentVO consignmentDocumentVO, ConsignmentDocumentVO existingMailBagsInConsignment) throws BusinessException
	{
		Collection<ErrorVO> errorVOs = new ArrayList<>();

		if (checkPAWBParameter(carditVO)) {
			boolean pawbCountryValidation = false;
			if (carditVO.getCarditPawbDetailsVO() != null) {
				findFieldsOfCarditPawb(carditVO);
				if (carditVO.getCarditType().equals(MailConstantsVO.CDT_TYP_CANCEL)) {
					carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingMailBagsInConsignment);
				} else {
					carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
					carditVO.getCarditPawbDetailsVO()
							.setExistingMailBagsInConsignment(existingMailBagsInConsignment);
				}
				pawbCountryValidation = findPawbCountryValidation(carditVO,
						carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
			}
			if (pawbCountryValidation) {
				try {
					SavePAWBDetailsFeature savePAWBDetailsFeature = ContextUtil.getInstance()
							.getBean(SavePAWBDetailsFeature.class);
					savePAWBDetailsFeature.execute(carditVO);
				} catch (Exception exce) {
					log.info(exce.getMessage());
					String[] defaultMessage= exce.getMessage().split(":");
					String errorCode="";
					if(defaultMessage!=null&&defaultMessage.length>1&&
							defaultMessage[1]!=null) {
						errorCode= defaultMessage[1].trim();
						throw new BusinessException(errorCode,"warning");
					}

				}
			}
		}

		return errorVOs;
	}
	private boolean checkPAWBParameter(CarditVO carditVO) {
		try {
			Boolean flag = false;
			String sendeId = carditVO.getSenderId();
			PostalAdministrationVO postalAdministrationVO = null;
			postalAdministrationVO = findPACode(carditVO.getCompanyCode(), sendeId);
			if (!postalAdministrationVO.getPostalAdministrationDetailsVOs().isEmpty()) {
				Collection<PostalAdministrationDetailsVO> paDetails = postalAdministrationVO
						.getPostalAdministrationDetailsVOs().get("INVINFO");
				if (!paDetails.isEmpty()) {
					for (PostalAdministrationDetailsVO paDetail : paDetails) {
						if (paDetail.getParCode().equals("PAWBASSCONENAB")
								&& paDetail.getParameterValue().equalsIgnoreCase("YES")) {
							flag = true;
						}
					}
				}
			}
			return flag;
		} finally {
		}
	}
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo) {
		log.debug(CLASS + " : " + "listmailbagSecurityDetails" + " Entering");
		MailbagVO mailbagVO = new MailbagVO();
		try {
			mailbagVO = constructDAO().listmailbagSecurityDetails(mailScreeningFilterVo);
		} finally {
		}
		return mailbagVO;
	}
	/**
	 * Method		:	MailController.editscreeningDetails Added by 	:	A-10383 on 21-Apr-2022 Used for 	: Parameters	:	@param consignmentScreeningVOs Parameters	:	@throws SystemException  Return type	: 	void
	 */
	public void deletescreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
		if (Objects.nonNull(consignmentScreeningVOs) && !consignmentScreeningVOs.isEmpty()) {
			for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreeningVOs) {
				try {
					ConsignmentScreeningDetailsPK pk = new ConsignmentScreeningDetailsPK();
					pk.setCompanyCode(consignmentScreeningVO.getCompanyCode());
					pk.setSerialNumber(consignmentScreeningVO.getSerialNumber());
					ConsignmentScreeningDetails screening = ConsignmentScreeningDetails
							.find(consignmentScreeningVO.getCompanyCode(), consignmentScreeningVO.getSerialNumber());
					screening.remove();
				} catch (FinderException e) {
					log.error("Exception :", e);
				}
			}
		}
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerVOs
	 * @param bulkContainers
	 * @throws SystemException
	 */
	Collection<ContainerDetailsVO> findContainerDetailsVOs(OperationalFlightVO operationalFlightVO,
														   Collection<ContainerVO> containerVOs, Collection<ContainerVO> bulkContainers) {
		Collection<ContainerDetailsVO> containerDetailsVOs = findContainerDetailsForULD(operationalFlightVO,
				containerVOs);
		Collection<ContainerDetailsVO> containerDetailsVOsBarrow = findContainerDetailsForBarrow(operationalFlightVO,
				bulkContainers);
		containerDetailsVOs.addAll(containerDetailsVOsBarrow);
		return containerDetailsVOs;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerVOs
	 * @throws SystemException
	 */
	private Collection<ContainerDetailsVO> findContainerDetailsForBarrow(OperationalFlightVO operationalFlightVO,
																		 Collection<ContainerVO> containerVOs) {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		if (containerVOs != null && !containerVOs.isEmpty()) {
			Collection<ContainerDetailsVO> containers = new ArrayList<>();
			for (ContainerVO containerVO : containerVOs) {
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				containerDetailsVO.setCarrierId(operationalFlightVO.getCarrierId());
				containerDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				containerDetailsVO.setPol(operationalFlightVO.getPol());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
				containers.add(containerDetailsVO);
			}
			containerDetailsVOs = new MailController().findMailbagsInContainer(containers);
		}
		return containerDetailsVOs;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerVOs
	 * @throws SystemException
	 */
	private Collection<ContainerDetailsVO> findContainerDetailsForULD(OperationalFlightVO operationalFlightVO,
																	  Collection<ContainerVO> containerVOs) {
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<>();
		if (containerVOs != null && !containerVOs.isEmpty()) {
			Collection<ContainerDetailsVO> containers = new ArrayList<>();
			for (ContainerVO containerVO : containerVOs) {
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				containerDetailsVO.setCarrierId(operationalFlightVO.getCarrierId());
				containerDetailsVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
				containerDetailsVO.setFlightNumber(operationalFlightVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				containerDetailsVO.setPol(operationalFlightVO.getPol());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
				containers.add(containerDetailsVO);
			}
			containerDetailsVOs = new MailController().findMailbagsInContainer(containers);
		}
		return containerDetailsVOs;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param pou
	 * @param container
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> sendSecurityScreeningDetailsForContainer(Collection<MailbagVO> mailbagVOs,
																		   OperationalFlightVO operationalFlightVO, String container, String pou) {
		Collection<MailbagVO> mailbagVOsToSend = new ArrayList<>();
		Collection<MailbagVO> mailbagVOsToOps = new ArrayList<>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
			mailScreeningFilterVo.setCompanyCode(operationalFlightVO.getCompanyCode());
			mailScreeningFilterVo.setMailBagId(mailbagVO.getMailbagId());
			MailbagVO mailVo = listmailbagSecurityDetails(mailScreeningFilterVo);
			if (mailVo != null && mailVo.getConsignmentScreeningVO() != null
					&& !mailVo.getConsignmentScreeningVO().isEmpty() && mailVo.isSecurityDetailsPresent()
					&& mailVo.getSecurityStatusCode() != null) {
				MailbagVO mailbagVo = mailVo;
				mailVo.setUpliftAirport(operationalFlightVO.getPol());
				List<ConsignmentScreeningVO> consignmentScreeningVOForOps = mailVo.getConsignmentScreeningVO().stream()
						.filter(value -> (mailVo.getUpliftAirport().equals(value.getScreeningLocation())))
						.collect(Collectors.toList());
				List<ConsignmentScreeningVO> consignmentScreeningVos = mailVo.getConsignmentScreeningVO().stream()
						.filter(value -> ((mailVo.getUpliftAirport().equals(value.getScreeningLocation()))
								&& (MailConstantsVO.REASON_CODE_SM.equals(value.getScreenDetailType())
								|| MailConstantsVO.REASON_CODE_CS.equals(value.getScreenDetailType()))))
						.collect(Collectors.toList());
				if (!consignmentScreeningVos.isEmpty()) {
					mailVo.setConsignmentScreeningVO(consignmentScreeningVos);
					mailbagVOsToSend.add(mailVo);
				}
				if (!consignmentScreeningVOForOps.isEmpty()) {
					mailbagVo.setConsignmentScreeningVO(consignmentScreeningVOForOps);
					mailbagVOsToOps.add(mailbagVo);
				}
			}
		}
		if (mailbagVOsToSend.isEmpty()) {
			if (mailbagVOsToOps.isEmpty()) {
				return Collections.emptyList();
			} else {
				return mailbagVOsToOps;
			}
		}
		SecurityAndScreeningMessageVO securityAndScreeningMessageVO = new SecurityAndScreeningMessageVO();
		securityAndScreeningMessageVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		securityAndScreeningMessageVO.setMessageType(SECURITY_SCREENING_MESSGE_TYPE);
		securityAndScreeningMessageVO.setMessageStandard(PUBLISH);
		securityAndScreeningMessageVO
				.setFlightNumber(operationalFlightVO.getCarrierCode() + operationalFlightVO.getFlightNumber());
		securityAndScreeningMessageVO.setFlightDate(LocalDateMapper.toLocalDate(operationalFlightVO.getArrivaltime()));
		securityAndScreeningMessageVO.setContainer(container);
		securityAndScreeningMessageVO.setPou(pou);
		//TODO: Neo to correct the code by adding mapper
//		securityAndScreeningMessageVO.setMailbagVOs(mailbagVOsToSend);
//		try {
//			msgBrokerMessageProxy.encodeAndSaveMessageAsync(securityAndScreeningMessageVO);
//		} catch (BusinessException proxyException) {
//			log.info("Exception :", proxyException);
//		}
		return mailbagVOsToOps;
	}

	/**
	 * Method		:	MailController.saveMailSecurityStatus Added by 	:	A-4809 on 18-May-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@throws SystemException  Return type	: 	void
	 */
	public void saveMailSecurityStatus(MailbagVO mailbagVO) {
		MailbagPK mailBagPK = new MailbagPK();
		mailBagPK.setCompanyCode(mailbagVO.getCompanyCode());
		mailBagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		Mailbag mailBag = null;
		try {
			mailBag = Mailbag.find(mailBagPK);
			if (mailBag != null) {
				mailBag.setSecurityStatusCode(mailbagVO.getSecurityStatusCode());
			}
		} catch (FinderException e) {
			log.error("Exception :", e);
		}
	}
	public Collection<MailInConsignmentVO> findMailInConsignment(ConsignmentFilterVO consignmentFilterVO) {
		return constructDAO().findMailInConsignment(consignmentFilterVO);
	}

	/**
	 * @author A-8353
	 * @param containerDetailsVOs
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	Collection<MailbagVO> sendSecurityScreeningDetails(Collection<ContainerDetailsVO> containerDetailsVOs,
													   OperationalFlightVO operationalFlightVO) {
		Collection<MailbagVO> mailbagVOsForScreening = new ArrayList<>();
		Collection<MailbagVO> mailbagVOTosend = null;
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
					mailbagVOTosend = sendSecurityScreeningDetailsForContainer(containerDetailsVO.getMailDetails(),
							operationalFlightVO, containerDetailsVO.getContainerNumber(), containerDetailsVO.getPou());
					mailbagVOsForScreening.addAll(mailbagVOTosend);
				}
			}
		}
		return mailbagVOsForScreening;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerVOs
	 * @param bulkContainers
	 * @throws SystemException
	 */
	void doSecurityAndScreeningValidations(OperationalFlightVO operationalFlightVO,
										   Collection<ContainerVO> containerVOs, Collection<ContainerVO> bulkContainers) {
		Collection<ContainerDetailsVO> containerDetailsVOs = findContainerDetailsVOs(operationalFlightVO, containerVOs,
				bulkContainers);
		performEUValidations(containerDetailsVOs, operationalFlightVO);
		Collection<MailbagVO> mailbagVOsForScreening = sendSecurityScreeningDetails(containerDetailsVOs,
				operationalFlightVO);
		updateOciInfo(containerDetailsVOs, mailbagVOsForScreening, operationalFlightVO);
	}

	void updateOciInfo(Collection<ContainerDetailsVO> containerDetailsVOs, Collection<MailbagVO> mailbagVOsForScreening,
					   OperationalFlightVO operationalFlightVO) {
		Collection<MailbagVO> findAWBMailbags = new ArrayList<>();
		findAllMailbagsInCloseFlight(containerDetailsVOs, findAWBMailbags);
		Map<String, MailbagVO> temp = getUniqueAWBMailBags(findAWBMailbags);
		Collection<MailbagVO> uniqueAWBMailBags = new ArrayList<>(temp.values());
		for (MailbagVO uniqueAWBMailBag : uniqueAWBMailBags) {
			Collection<MailbagVO> mailbagsassociatedtoawb = mailbagVOsForScreening.stream()
					.filter(value -> Objects.nonNull(value.getAwbNumber()))
					.filter(value -> value.getAwbNumber().equals(uniqueAWBMailBag.getAwbNumber()))
					.collect(Collectors.toList());
			if (mailbagsassociatedtoawb != null && !mailbagsassociatedtoawb.isEmpty()) {
				MailbagVO mailBagVo = mailbagsassociatedtoawb.iterator().next();
				Collection<ConsignmentScreeningVO> consignmentScreeningVOs = mailBagVo.getConsignmentScreeningVO()
						.stream().filter(value -> Objects.nonNull(value.getAgentType()))
						.filter(value -> value.getAgentType().equals(MailConstantsVO.RA_ACCEPTING))
						.filter(value -> Objects.nonNull(value.getScreeningLocation()))
						.filter(value -> value.getScreeningLocation().equals(operationalFlightVO.getPol()))
						.collect(Collectors.toList());
				if (consignmentScreeningVOs != null && !consignmentScreeningVOs.isEmpty()) {
					ConsignmentScreeningVO consignmentScreeningVO = consignmentScreeningVOs.iterator().next();
					try {
						ShipmentDetailVO shipmentDelVO = operationsShipmentProxy
								.findShipmentDetails(createShipmentFilterVO(uniqueAWBMailBag));
						stampRAAcceptingValuesForOci(consignmentScreeningVO, shipmentDelVO);
						shipmentDelVO.setOperationFlag(ShipmentDetailVO.OPERATION_FLAG_UPDATE);
						operationsShipmentProxy.saveShipmentDetailsAsync(shipmentDelVO);
					} finally {
					}
				}
			}
		}
	}

	private void stampRAAcceptingValuesForOci(ConsignmentScreeningVO consignmentScreeningVO,
											  ShipmentDetailVO shipmentDelVO) {
		if (shipmentDelVO != null && shipmentDelVO.getOtherCustomsInformationVOs() != null
				&& !shipmentDelVO.getOtherCustomsInformationVOs().isEmpty()) {
			OtherCustomsInformationVO otherCustomsInformationVO = new OtherCustomsInformationVO();
			if (consignmentScreeningVO.getIsoCountryCode() != null) {
				otherCustomsInformationVO.setCountryCode(consignmentScreeningVO.getIsoCountryCode());
			}
			otherCustomsInformationVO.setInfoIdentifier("OSS");
			otherCustomsInformationVO.setOtherCusInfoIdentifier("RA");
			if (consignmentScreeningVO.getAgentID() != null) {
				otherCustomsInformationVO.setCustomsInfomation(consignmentScreeningVO.getAgentID());
			}
			shipmentDelVO.getOtherCustomsInformationVOs().add(otherCustomsInformationVO);
		}
	}

	private void findAllMailbagsInCloseFlight(Collection<ContainerDetailsVO> containerDetailsVOs,
											  Collection<MailbagVO> findAWBMailbags) {
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
					for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
						findAWBMailbags.add(mailbagVO);
					}
				}
			}
		}
	}

	private Map<String, MailbagVO> getUniqueAWBMailBags(Collection<MailbagVO> findAWBMailbags) {
		Map<String, MailbagVO> temp = new HashMap<>();
		findAWBMailbags.forEach(x -> {
			if (x.getAwbNumber() != null) {
				MailbagVO mailBagVO = temp.get(x.getAwbNumber());
				if (mailBagVO == null) {
					temp.put(x.getAwbNumber(), x);
				}
			}
		});
		return temp;
	}

	private ShipmentDetailFilterVO createShipmentFilterVO(MailbagVO mailbagVO) {
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentDetailFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		return shipmentDetailFilterVO;
	}

	public MailbagVO findAirportFromMailbag(MailbagVO mailbagvo) throws FinderException {
		MailbagPK mailbagPk = new MailbagPK();
		MailbagVO mailbagVOfromDB = null;
		if (mailbagvo.getMailSequenceNumber() != 0) {
			mailbagPk.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
			mailbagPk.setCompanyCode(mailbagvo.getCompanyCode());
			Mailbag mailbag = Mailbag.find(mailbagPk);
			mailbagvo.setOrigin(mailbag.getOrigin());
			mailbagvo.setDestination(mailbag.getDestination());
		} else {
			mailbagVOfromDB = Mailbag.findMailbagDetails(mailbagvo.getMailbagId(), mailbagvo.getCompanyCode());
			if (mailbagVOfromDB != null) {
				mailbagvo.setOrigin(mailbagVOfromDB.getOrigin());
				mailbagvo.setDestination(mailbagVOfromDB.getDestination());
			} else {
				findAirportForNewlyAddedMailbags(mailbagvo);
			}
		}
		return mailbagvo;
	}

	private void findAirportForNewlyAddedMailbags(MailbagVO mailbagvo) {
		String ooe = mailbagvo.getMailbagId().substring(0, 6);
		String doe = mailbagvo.getMailbagId().substring(6, 12);
		Page<OfficeOfExchangeVO> originOfficeDetails = findOfficeOfExchange(mailbagvo.getCompanyCode(), ooe, 1);
		if (originOfficeDetails != null && !originOfficeDetails.isEmpty()) {
			OfficeOfExchangeVO orgOfficeOfExchangeVO = originOfficeDetails.iterator().next();
			if (orgOfficeOfExchangeVO != null && orgOfficeOfExchangeVO.getAirportCode() != null) {
				mailbagvo.setOrigin(orgOfficeOfExchangeVO.getAirportCode());
			} else {
				if (orgOfficeOfExchangeVO != null) {
					String orgOfficeOfExchange = orgOfficeOfExchangeVO.getCode();
					String originPort = findNearestAirportOfCity(mailbagvo.getCompanyCode(), orgOfficeOfExchange);
					mailbagvo.setOrigin(originPort);
				}
			}
		}
		Page<OfficeOfExchangeVO> destinationOfficeDetails = findOfficeOfExchange(mailbagvo.getCompanyCode(), doe, 1);
		if (destinationOfficeDetails != null && !destinationOfficeDetails.isEmpty()) {
			OfficeOfExchangeVO destOfficeOfExchangeVO = destinationOfficeDetails.iterator().next();
			if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() != null) {
				mailbagvo.setDestination(destOfficeOfExchangeVO.getAirportCode());
			} else {
				if (destOfficeOfExchangeVO != null) {
					String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
					String destinationPort = findNearestAirportOfCity(mailbagvo.getCompanyCode(), destOfficeOfExchange);
					mailbagvo.setDestination(destinationPort);
				}
			}
		}
	}
	private void updateAirportDetails(MailInConsignmentVO mailInConsignmentVO,
									  ArrayList<CarditReferenceInformationVO> referenceVOs, String transportReference) {
		if (referenceVOs != null && !referenceVOs.isEmpty()) {
			for (CarditReferenceInformationVO carditReferenceInformationVO : referenceVOs) {
				if (carditReferenceInformationVO.getTransportContractReferenceQualifier() != null
						&& !carditReferenceInformationVO.getTransportContractReferenceQualifier().isEmpty()
						&& transportReference
						.equals(carditReferenceInformationVO.getTransportContractReferenceQualifier())) {
					String consignmentContractReference = carditReferenceInformationVO
							.getConsignmentContractReferenceNumber();
					if (consignmentContractReference != null && !consignmentContractReference.isEmpty()
							&& (consignmentContractReference.trim().length() == 6 && ("ERN".contentEquals(
							carditReferenceInformationVO.getTransportContractReferenceQualifier())
							|| "AWN".contentEquals(
							carditReferenceInformationVO.getTransportContractReferenceQualifier())))) {
						Collection<String> officeOfExchanges = new ArrayList<>();
						officeOfExchanges.add(carditReferenceInformationVO.getConsignmentContractReferenceNumber());
						HashMap<String, String> resultSetMap = findAirportForOfficeOfExchange(
								mailInConsignmentVO.getCompanyCode(), officeOfExchanges);
						if (resultSetMap != null && resultSetMap
								.containsKey(carditReferenceInformationVO.getConsignmentContractReferenceNumber())) {
							consignmentContractReference = resultSetMap
									.get(carditReferenceInformationVO.getConsignmentContractReferenceNumber());
						}
					}
					if ("ERN".contentEquals(carditReferenceInformationVO.getTransportContractReferenceQualifier())
							&& (mailInConsignmentVO.getMailOrigin() == null
							|| "".equals(mailInConsignmentVO.getMailOrigin()))) {
						mailInConsignmentVO.setMailOrigin(consignmentContractReference);
					} else if ("AWN"
							.contentEquals(carditReferenceInformationVO.getTransportContractReferenceQualifier())
							&& (mailInConsignmentVO.getMailDestination() == null
							|| "".equals(mailInConsignmentVO.getMailDestination()))) {
						mailInConsignmentVO.setMailDestination(consignmentContractReference);
					} else {
					}
				}
			}
		}
	}

	/**
	 * Method		:	MailController.saveFligthLoadPlanForMail Added by 	:	A-3429 on 01-July-2022 Used for 	: Parameters	:	@param flightLoanPlanContainerVOs  Return type	: 	void
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs)
			throws BusinessException {
		for (FlightLoadPlanContainerVO loanPlanContainerVO : flightLoadPlanContainerVOs) {
			SaveLoadPlanDetailsForMailFeature saveLoadPlanDetailsForMailFeature = ContextUtil.getInstance()
					.getBean(SaveLoadPlanDetailsForMailFeature.class);
			saveLoadPlanDetailsForMailFeature.execute(loanPlanContainerVO);
		}
	}

	/**
	 * Method       :      MailController.findLoadPlandetails Added by     :      A-9477 on 13-JUL-2022 Used for     : Parameters   :      @param searchContainerFilterVO Parameters   :      @throws Return type  :      FlightLoanPlanContainerVO
	 */
	public Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO) {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = null;
		try {
			flightLoadPlanContainerVOs = constructDAO().findLoadPlandetails(searchContainerFilterVO);
		} finally {
		}
		return flightLoadPlanContainerVOs;
	}

	/**
	 * @author A-8353
	 * @param containerDetailsVOs
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	void performEUValidations(Collection<ContainerDetailsVO> containerDetailsVOs,
							  OperationalFlightVO operationalFlightVO) {
		if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (containerDetailsVO.getMailDetails() != null && !containerDetailsVO.getMailDetails().isEmpty()) {
					checkAndPerformEUValidation(containerDetailsVO.getMailDetails(), operationalFlightVO,
							containerDetailsVO.getPou());
				}
			}
		}
	}

	/**
	 * @author A-8353
	 * @param mailDetails
	 * @param operationalFlightVO
	 * @param containerPou
	 * @throws SystemException
	 */
	private void checkAndPerformEUValidation(Collection<MailbagVO> mailDetails, OperationalFlightVO operationalFlightVO,
											 String containerPou) {
		if (checkForEuNonEuAirport(operationalFlightVO, containerPou)) {
			String identifyacc3onmftflt = findSystemParameterValue(
					"operations.flthandling.identifyacc3basedonmanifestedflight");
			Collection<FlightValidationVO> flightValidationVOs = null;
			flightValidationVOs = validateFlightAndUpdate(operationalFlightVO, containerPou, true);
			AirlineFilterVO airlineFilterVO = new AirlineFilterVO();
			airlineFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
			airlineFilterVO.setAirportCode(operationalFlightVO.getPol());
			if (!"Y".equals(identifyacc3onmftflt)) {
				FlightValidationVO flightValidationVO = null;
				if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
					flightValidationVO = flightValidationVOs.iterator().next();
				}
				if (flightValidationVO != null) {
					airlineFilterVO.setAirlineIdentifier(flightValidationVO.getFlightCarrierId());
				} else {
					findFlightOwnerIdentifier(operationalFlightVO, containerPou, airlineFilterVO);
				}
			} else {
				airlineFilterVO.setAirlineIdentifier(operationalFlightVO.getCarrierId());
			}
			String parameterValue = null;
			parameterValue = findAirlineParameter(operationalFlightVO, airlineFilterVO);
			saveRcIdentifierForMailbag(mailDetails, operationalFlightVO, parameterValue, airlineFilterVO);
		}
		saveRAIdentifierForMailbag(mailDetails, operationalFlightVO);
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerPou
	 * @return
	 * @throws SystemException
	 */
	private boolean checkForEuNonEuAirport(OperationalFlightVO operationalFlightVO, String containerPou) {
		Collection<String> airportCodes = new ArrayList<>();
		boolean isNonEuAirport = false;
		if (operationalFlightVO != null && operationalFlightVO.getPol() != null
				&& operationalFlightVO.getFlightRoute() != null) {
			String origin = operationalFlightVO.getPol();
			String nextPOU = null;
			nextPOU = findPouFromRoute(operationalFlightVO, containerPou, airportCodes, origin, nextPOU);
			try {
				isNonEuAirport = isEuNonEuStampingRequired(operationalFlightVO, airportCodes, isNonEuAirport, origin,
						nextPOU);
			} catch (SharedProxyException proxyException) {
				log.error("Exception :", proxyException);
				throw new SystemException(proxyException.getMessage());
			}
			if (isNonEuAirport) {
				isNonEuAirport = checkForGreenCountry(operationalFlightVO, origin);
			}
		}
		return isNonEuAirport;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerPou
	 * @return
	 * @throws SystemException
	 */
	private Collection<FlightValidationVO> validateFlightAndUpdate(OperationalFlightVO operationalFlightVO,
																   String containerPou, boolean operationReferenceRequired) {
		Collection<FlightValidationVO> flightValidationVOs;
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
		flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		flightFilterVO.setOrigin(operationalFlightVO.getPol());
		flightFilterVO.setDestination(containerPou);
		//TODO: Neo to correct below
//		String stringFlightDate = TimeConvertor.toStringFormat(operationalFlightVO.getFlightDate(),
//				TimeConvertor.ADVANCED_DATE_FORMAT);
//		stringFlightDate = stringFlightDate.substring(0, 16);
//		flightFilterVO.setStringFlightDate(stringFlightDate);
		if (operationReferenceRequired) {
			flightFilterVO.setOperationReference(true);
			flightValidationVOs = flightOperationsProxy.getNonReferenceFlights(flightFilterVO);
		} else {
			flightFilterVO.setOperationReference(false);
			flightValidationVOs = flightOperationsProxy.validateFlight(flightFilterVO);
		}
		return flightValidationVOs;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerPou
	 * @param airlineFilterVO
	 * @throws SystemException
	 */
	private void findFlightOwnerIdentifier(OperationalFlightVO operationalFlightVO, String containerPou,
										   AirlineFilterVO airlineFilterVO) {
		FlightValidationVO flightValidationVo = new FlightValidationVO();
		if (operationalFlightVO.getFltOwner() != null) {
			flightValidationVo.setFlightOwner(operationalFlightVO.getFltOwner());
		} else {
			Collection<FlightValidationVO> flightValidationVos = null;
			flightValidationVos = validateFlightAndUpdate(operationalFlightVO, containerPou, false);
			if (flightValidationVos != null && !flightValidationVos.isEmpty()) {
				flightValidationVo = flightValidationVos.iterator().next();
			}
		}
		if (flightValidationVo.getFlightOwner() != null) {
			try {
				AirlineValidationVO airlineValidationVO = sharedAirlineProxy
						.validateAlphaCode(operationalFlightVO.getCompanyCode(), flightValidationVo.getFlightOwner());
				airlineFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
			} catch (SharedProxyException e) {
				log.error("Exception :", e);
				throw new SystemException(e.getMessage());
			}
		}
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param airlineFilterVO
	 * @return
	 * @throws SystemException
	 */
	private String findAirlineParameter(OperationalFlightVO operationalFlightVO, AirlineFilterVO airlineFilterVO) {
		String parameterValue = null;
		Collection<String> airlineParameterCode = new ArrayList<>();
		Map<String, String> airlineMap;
		if ("C".equals(operationalFlightVO.getFlightType())) {
			airlineParameterCode.add("shared.airline.ACC3CarrierCodeforCargoonlyAircraft");
			airlineMap = sharedAirlineProxy.findAirlineParametersByCode(operationalFlightVO.getCompanyCode(),
					airlineFilterVO.getAirlineIdentifier(), airlineParameterCode);
			if (airlineMap != null && airlineMap.size() > 0) {
				parameterValue = airlineMap.get("shared.airline.ACC3CarrierCodeforCargoonlyAircraft")
						.toUpperCase(Locale.ENGLISH);
			}
		} else {
			if ("CO".equals(operationalFlightVO.getFlightType())) {
				airlineParameterCode.add("shared.airline.ACC3CarrierCodeforPassengerAircraft");
				airlineMap = sharedAirlineProxy.findAirlineParametersByCode(operationalFlightVO.getCompanyCode(),
						airlineFilterVO.getAirlineIdentifier(), airlineParameterCode);
				if (airlineMap != null && airlineMap.size() > 0) {
					parameterValue = airlineMap.get("shared.airline.ACC3CarrierCodeforPassengerAircraft")
							.toUpperCase(Locale.ENGLISH);
				}
			}
		}
		return parameterValue;
	}

	/**
	 * @author A-8353
	 * @param mailDetails
	 * @param operationalFlightVO
	 * @param parameterValue
	 * @throws SystemException
	 */
	private void saveRcIdentifierForMailbag(Collection<MailbagVO> mailDetails, OperationalFlightVO operationalFlightVO,
											String parameterValue, AirlineFilterVO airlineFilterVO) {
		if (parameterValue != null) {
			AirlineVO airlineVO = null;
			airlineVO = sharedAirlineProxy.findAirlineDetails(operationalFlightVO.getCompanyCode(),
					airlineFilterVO.getAirlineIdentifier());
			StringBuilder certificatNumber = null;
			certificatNumber = new StringBuilder().append(operationalFlightVO.getCountryCode())
					.append(operationalFlightVO.getPol()).append("-").append(parameterValue);
			Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
			Collection<ConsignmentScreeningVO> consignmentScreeningVosEdit = new ArrayList<>();
			for (MailbagVO mailbagVO : mailDetails) {
				ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
				ConsignmentScreeningVO consignmentScreeningVo = null;
				consignmentScreeningVo = constructDAO().findRegulatedCarrierForMailbag(
						operationalFlightVO.getCompanyCode(), mailbagVO.getMailSequenceNumber());
				consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
				consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
				consignmentScreeningVO.setCompanyCode(operationalFlightVO.getCompanyCode());
				consignmentScreeningVO.setSource("FLTCLS");
				consignmentScreeningVO.setAgentType("RC");
				consignmentScreeningVO.setAgentID(certificatNumber.toString().toUpperCase(Locale.ENGLISH));
				consignmentScreeningVO.setIsoCountryCode(airlineVO.getBillingCountry());
				consignmentScreeningVO.setMalseqnum(mailbagVO.getMailSequenceNumber());
				consignmentScreeningVO.setScreeningLocation(operationalFlightVO.getPol());
				if (consignmentScreeningVo != null
						&& !consignmentScreeningVO.getAgentID().equals(consignmentScreeningVo.getAgentID())) {
					consignmentScreeningVO.setSerialNumber(consignmentScreeningVo.getSerialNumber());
					consignmentScreeningVosEdit.add(consignmentScreeningVO);
				} else {
					if (consignmentScreeningVo == null) {
						consignmentScreeningVosSave.add(consignmentScreeningVO);
					}
				}
			}
			if (!consignmentScreeningVosEdit.isEmpty()) {
				editScreeningDetailsFeature.perform(consignmentScreeningVosEdit);
			}
			if (!consignmentScreeningVosSave.isEmpty()) {
				saveSecurityDetails(consignmentScreeningVosSave);
			}
		}
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param containerPou
	 * @param airportCodes
	 * @param origin
	 * @param nextPOU
	 * @return
	 */
	private String findPouFromRoute(OperationalFlightVO operationalFlightVO, String containerPou,
									Collection<String> airportCodes, String origin, String nextPOU) {
		String[] routes = operationalFlightVO.getFlightRoute().split("-");
		for (int i = 0; i < routes.length; i++) {
			if (containerPou.equals(routes[i])) {
				nextPOU = routes[i];
				break;
			}
		}
		airportCodes.add(origin);
		airportCodes.add(nextPOU);
		return nextPOU;
	}

	/**
	 * @author A-8353
	 * @param operationalFlightVO
	 * @param airportCodes
	 * @param isNonEuAirport
	 * @param origin
	 * @param nextPOU
	 * @return
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
	private boolean isEuNonEuStampingRequired(OperationalFlightVO operationalFlightVO, Collection<String> airportCodes,
											  boolean isNonEuAirport, String origin, String nextPOU) throws SharedProxyException {
		Map<String, CityVO> cityMap;
		Set<String> cityCodes = new HashSet<>();
		CityVO originCity = null;
		CityVO nxtPOUCity = null;
		Map<String, AirportValidationVO> validAirportCodes = sharedAreaProxy
				.validateAirportCodes(operationalFlightVO.getCompanyCode(), airportCodes);
		if (validAirportCodes != null && !validAirportCodes.isEmpty() && validAirportCodes.get(origin) != null) {
			cityCodes.add(validAirportCodes.get(origin).getCityCode());
			cityCodes.add(validAirportCodes.get(nextPOU).getCityCode());
		}
		if (!cityCodes.isEmpty()) {
			cityMap = sharedAreaProxy.validateCityCodes(operationalFlightVO.getCompanyCode(), cityCodes);
			if (cityMap != null && cityMap.size() > 0 && validAirportCodes != null
					&& validAirportCodes.get(origin) != null) {
				originCity = cityMap.get(validAirportCodes.get(origin).getCityCode());
				nxtPOUCity = cityMap.get(validAirportCodes.get(nextPOU).getCityCode());
			}
		}
		if (originCity != null && MailConstantsVO.NON_EU_INDICATOR.equals(originCity.getEuNonEuIndicator())
				&& nxtPOUCity != null && MailConstantsVO.EU_INDICATOR.equals(nxtPOUCity.getEuNonEuIndicator())) {
			isNonEuAirport = true;
		}
		return isNonEuAirport;
	}

	/**
	 * @author A-8353
	 * @return
	 * @throws SystemException
	 */
	private boolean checkForGreenCountry(OperationalFlightVO operationalFlightVO, String origin) {
		boolean isNotGreenCountryAirport = true;
		String countryMemberGroup = findCountryMemberGroupforAirport(operationalFlightVO, origin);
		if (countryMemberGroup != null && countryMemberGroup.trim().length() > 0) {
			String[] countryMember = countryMemberGroup.split(",");
			for (String member : countryMember) {
				if (MailConstantsVO.GREEN.equals(member)) {
					isNotGreenCountryAirport = false;
					break;
				}
			}
		}
		return isNotGreenCountryAirport;
	}

	public String findCountryMemberGroupforAirport(OperationalFlightVO operationalFlightVO, String originCode) {
		String countryMemberGroup = null;
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add(originCode);
		Map<String, AirportValidationVO> validAirportCodes = sharedAreaProxy
				.validateAirportCodes(operationalFlightVO.getCompanyCode(), airportCodes);
		if (validAirportCodes != null && validAirportCodes.size() > 0 && validAirportCodes.get(originCode) != null) {
			String cuntryCode = null;
			cuntryCode = validAirportCodes.get(originCode).getCountryCode();
			if (cuntryCode != null) {
				operationalFlightVO.setCountryCode(cuntryCode);
				try {
					countryMemberGroup = validateCountryCode(operationalFlightVO.getCompanyCode(), cuntryCode,
							countryMemberGroup);
				} catch (SharedProxyException e) {
					log.error("Exception :", e);
					throw new SystemException(e.getMessage());
				}
			}
		}
		return countryMemberGroup;
	}

	/**
	 * @author A-8353
	 * @param companyCode
	 * @param countryMemberGroup
	 * @return
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
	private String validateCountryCode(String companyCode, String cuntryCode, String countryMemberGroup)
			throws SharedProxyException {
		CountryVO countryVO;
		ArrayList<String> countries;
		Map<String, CountryVO> country;
		countries = new ArrayList<>();
		countries.add(cuntryCode);
		country = sharedAreaProxy.validateCountryCodes(companyCode, countries);
		if (country != null) {
			countryVO = country.get(cuntryCode);
			countryMemberGroup = countryVO.getMemberGroupCode();
		}
		return countryMemberGroup;
	}

	/**
	 * Method       :      MailController.saveRAIdentifierForMailbag Added by     :      A-9477 on 29-JUL-2022 Used for     :    Stamping the RA accept valueS during the flight closure. Parameters   :      @param mailDetails ,operationalFlightVO  Parameters   :      @throws SystemException Return type  :
	 */
	private void saveRAIdentifierForMailbag(Collection<MailbagVO> mailDetails,
											OperationalFlightVO operationalFlightVO) {
		if (operationalFlightVO != null) {
			Collection<AirlineAirportParameterVO> airportParameterVO = findAirlineAirportParameter(operationalFlightVO);
			String thirdPartyRaIssue = null;
			String countryAgentVal = null;
			String raAcceptanceValidationOverride = null;
			for (AirlineAirportParameterVO parvo : airportParameterVO) {
				if (parvo.getParameterCode().equals(THIRDPARTYRA_ISSUE_MAIL)) {
					thirdPartyRaIssue = parvo.getParameterValue();
				}
				if (parvo.getParameterCode().equals(REGULATED_AGENTACCEPTING_MAIL)) {
					countryAgentVal = parvo.getParameterValue();
				}
				if (parvo.getParameterCode().equals(RA_ACCEPTANCE_VALIDATION_OVERRIDE)) {
					raAcceptanceValidationOverride = parvo.getParameterValue();
				}
			}
			String country = null;
			String raid = null;
			if (countryAgentVal != null) {
				country = countryAgentVal.substring(0, 2);
				raid = countryAgentVal.substring(3);
			}
			Collection<ConsignmentScreeningVO> consignmentScreeningVosSave = new ArrayList<>();
			for (MailbagVO mailbagVO : mailDetails) {
				stampRaValues(operationalFlightVO, thirdPartyRaIssue, raAcceptanceValidationOverride, country, raid,
						consignmentScreeningVosSave, mailbagVO);
			}
			if (!consignmentScreeningVosSave.isEmpty()) {
				saveSecurityDetails(consignmentScreeningVosSave);
			}
		}
	}

	private void stampRaValues(OperationalFlightVO operationalFlightVO, String thirdPartyRaIssue,
							   String raAcceptanceValidationOverride, String country, String raid,
							   Collection<ConsignmentScreeningVO> consignmentScreeningVosSave, MailbagVO mailbagVO) {
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = constructDAO()
				.findRAacceptingForMailbag(operationalFlightVO.getCompanyCode(), mailbagVO.getMailSequenceNumber());
		List<ConsignmentScreeningVO> raAcceptVos = findRAacceptVos(operationalFlightVO, consignmentScreeningVos);
		if (raAcceptVos.isEmpty()) {
			List<String> statusCodes = new ArrayList<>();
			statusCodes.add("SPX");
			statusCodes.add("SPH");
			statusCodes.add("SCO");
			List<ConsignmentScreeningVO> riIssueVos = findRIissueVos(consignmentScreeningVos);
			boolean isContainScc = false;
			if (mailbagVO.getSecurityStatusCode() != null) {
				isContainScc = statusCodes.stream().anyMatch(value -> value.equals(mailbagVO.getSecurityStatusCode()));
			}
			if (!riIssueVos.isEmpty() && isContainScc && MailConstantsVO.FLAG_YES.equals(thirdPartyRaIssue)) {
				constructConsignmentScreeningVo(operationalFlightVO, country, raid, consignmentScreeningVosSave,
						mailbagVO);
			} else {
				String previousAirport = findRoutingDetailsForMailbag(operationalFlightVO.getCompanyCode(),
						mailbagVO.getMailSequenceNumber(), operationalFlightVO.getPol());
				if (MailConstantsVO.FLAG_NO.equals(thirdPartyRaIssue)
						&& (!riIssueVos.isEmpty()
						&& checkRaissueInPreviousairport(riIssueVos, previousAirport, operationalFlightVO))
						&& isContainScc) {
					constructConsignmentScreeningVo(operationalFlightVO, country, raid, consignmentScreeningVosSave,
							mailbagVO);
				} else {
					boolean isNotGreenCountryAirport = checkForGreenCountry(operationalFlightVO, previousAirport);
					if (!isNotGreenCountryAirport && MailConstantsVO.FLAG_YES.equals(raAcceptanceValidationOverride)) {
						constructConsignmentScreeningVo(operationalFlightVO, country, raid, consignmentScreeningVosSave,
								mailbagVO);
					} else {
						if (!isNotGreenCountryAirport && isContainScc) {
							constructConsignmentScreeningVo(operationalFlightVO, country, raid,
									consignmentScreeningVosSave, mailbagVO);
						}
					}
				}
			}
		}
	}

	private void constructConsignmentScreeningVo(OperationalFlightVO operationalFlightVO, String country, String raid,
												 Collection<ConsignmentScreeningVO> consignmentScreeningVosSave, MailbagVO mailbagVO) {
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
		consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
		consignmentScreeningVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		consignmentScreeningVO.setSource(FLT_CLS);
		consignmentScreeningVO.setAgentType(MailConstantsVO.RA_ACCEPTING);
		consignmentScreeningVO.setAgentID(raid);
		consignmentScreeningVO.setIsoCountryCode(country);
		consignmentScreeningVO.setMalseqnum(mailbagVO.getMailSequenceNumber());
		consignmentScreeningVO.setScreeningLocation(operationalFlightVO.getPol());
		consignmentScreeningVosSave.add(consignmentScreeningVO);
	}

	private List<ConsignmentScreeningVO> findRIissueVos(Collection<ConsignmentScreeningVO> consignmentScreeningVos) {
		return consignmentScreeningVos.stream().filter(value -> Objects.nonNull(value.getAgentType()))
				.filter(value -> (value.getAgentType().equals(MailConstantsVO.RA_ISSUING)))
				.collect(Collectors.toList());
	}

	private List<ConsignmentScreeningVO> findRAacceptVos(OperationalFlightVO operationalFlightVO,
														 Collection<ConsignmentScreeningVO> consignmentScreeningVos) {
		return consignmentScreeningVos.stream().filter(value -> Objects.nonNull(value.getAgentType()))
				.filter(value -> value.getAgentType().equals(MailConstantsVO.RA_ACCEPTING))
				.filter(value -> Objects.nonNull(value.getScreeningLocation()))
				.filter(value -> value.getScreeningLocation().equals(operationalFlightVO.getPol()))
				.collect(Collectors.toList());
	}

	private boolean checkRaissueInPreviousairport(Collection<ConsignmentScreeningVO> consignmentScreeningVos,
												  String previousAirport, OperationalFlightVO operationalFlightVO) {
		boolean checkRaIssue = false;
		if (!consignmentScreeningVos.stream().filter(value -> value.getScreeningLocation().equals(previousAirport))
				.collect(Collectors.toList()).isEmpty()
				&& consignmentScreeningVos.stream()
				.filter(value -> value.getScreeningLocation().equals(operationalFlightVO.getPol()))
				.collect(Collectors.toList()).isEmpty()) {
			checkRaIssue = true;
		}
		return checkRaIssue;
	}

	private Collection<AirlineAirportParameterVO> findAirlineAirportParameter(OperationalFlightVO operationalFlightVO) {
		AirlineAirportParameterFilterVO airlineAirportParameterFilterVO = new AirlineAirportParameterFilterVO();
		airlineAirportParameterFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		airlineAirportParameterFilterVO.setAirlineId(operationalFlightVO.getCarrierId());
		airlineAirportParameterFilterVO.setAirportCode(operationalFlightVO.getPol());
		Collection<AirlineAirportParameterVO> airportParameterVO = new ArrayList<>();
		airportParameterVO = sharedAirlineProxy.findAirlineAirportParameters(airlineAirportParameterFilterVO);
		return airportParameterVO;
	}
	private String findRoutingDetailsForMailbag(String companyCode, long malseqnum, String airportCode) {
		String previousAirport = null;
		try {
			previousAirport = constructDAO().findRoutingDetailsForMailbag(companyCode, malseqnum, airportCode);
		} finally {
		}
		return previousAirport;
	}

	/**
	 * @author A-8353
	 * @return
	 */
	public Map<String, ErrorVO> saveSecurityScreeningFromService(
			SecurityAndScreeningMessageVO securityAndScreeningMessageVO) {
		Collection<MailbagVO> mailbagVOs = classicVOConversionMapper_.copyMailBagVOs_classic(
				securityAndScreeningMessageVO.getMailbagVOs());
		Map<String, ErrorVO> errorMap = new HashMap<>();
		long malseqnum = 0;
		for (MailbagVO mailbagVO : mailbagVOs) {
			if ("M".equals(mailbagVO.getType())) {
				try {
					malseqnum = findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				} finally {
				}
				if (malseqnum == 0) {
					errorMap.put(mailbagVO.getMailbagId(), new ErrorVO("Receptacle not present in  the system"));
				} else {
					try {
						mapConsignmentScreeningVoAndSave(malseqnum, mailbagVO);
					} finally {
					}
				}
			}
		}
		return errorMap;
	}

	/**
	 * @author A-8353
	 * @param malseqnum
	 * @param mailbagVO
	 * @throws SystemException
	 * @throws FinderException
	 */
	private void mapConsignmentScreeningVoAndSave(long malseqnum, MailbagVO mailbagVO) {
		mailbagVO.setMailSequenceNumber(malseqnum);
		saveMailSecurityStatus(mailbagVO);
		Map<Integer, List<ConsignmentScreeningVO>> consignmentScreeningVOsGroupedByRaIssuing = mailbagVO
				.getConsignmentScreeningVO().stream()
				.collect(Collectors.groupingBy(ConsignmentScreeningVO::getMappingId));
		for (Map.Entry<Integer, List<ConsignmentScreeningVO>> entry : consignmentScreeningVOsGroupedByRaIssuing
				.entrySet()) {
			List<ConsignmentScreeningVO> consignmentScreeningVOs = entry.getValue();
			consignmentScreeningVOs.forEach(consignmentScreeningVo -> consignmentScreeningVo.setMalseqnum(malseqnum));
			saveSecurityDetails(consignmentScreeningVOs);
		}
	}

	public Map<String, String> findAirportParameterCode(FlightFilterVO flightFilterVO, Collection<String> parCodes) {
		String companyCode = flightFilterVO.getCompanyCode();
		String airport = flightFilterVO.getAirportCode();
		return neoMastersServiceUtils.findAirportParametersByCode(companyCode, airport, parCodes);
	}

	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) {
		Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> operationalFlightVOs = null;
		try {
			operationalFlightVOs = constructDAO().fetchMailIndicatorForProgress(flightListingFilterVOs);
		} finally {
		}
		return operationalFlightVOs;
	}

	public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingVO hbaMarkingVO) {
		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(hbaMarkingVO.getCompanyCode());
		containerDetailsVO.setFlightNumber(hbaMarkingVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(hbaMarkingVO.getFlightSequenceNumber());
		containerDetailsVO.setCarrierId(hbaMarkingVO.getCarrierId());
		containerDetailsVO.setLegSerialNumber(hbaMarkingVO.getLegSerialNumber());
		containerDetailsVO.setPol(hbaMarkingVO.getAssignedPort());
		containerDetailsVO.setContainerNumber(hbaMarkingVO.getContainerNumber());
		containerDetailsVO.setContainerType(hbaMarkingVO.getContainerType());
		containers.add(containerDetailsVO);
		containers = new MailController().findMailbagsInContainer(containers);
		ContainerDetailsVO containerVo = containers.iterator().next();
		if (containerVo != null && containerVo.getMailDetails() != null && containerVo.getMailDetails().size() > 0) {
			Collection<MailbagVO> mailBags = containerVo.getMailDetails();
			updateInterfaceFlag(mailBags, "N");
		}
	}
	private void removeUldAtAirport(ContainerDetailsVO containerDetailsVO) {
		ULDAtAirport uldAtAirport = null;
		ULDAtAirportPK uldAtAirportPK = constructULDAtAirportPK(containerDetailsVO);
		try {
			uldAtAirport = ULDAtAirport.find(uldAtAirportPK);
		} catch (FinderException ex) {
			log.info("Exception :", ex);
		}
		if (uldAtAirport != null) {
			uldAtAirport.remove();
		}
	}

	public String generateAutomaticBarrowId(String cmpcod) {
		java.time.LocalDate date = java.time.LocalDate.now();
		int weekNumber = date.get(WeekFields.ISO.weekOfWeekBasedYear());
		int dayNumber = date.getDayOfWeek().getValue();
		String keyPrefix = String.valueOf(weekNumber).concat(String.valueOf(dayNumber)).concat("M");
		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey("barrowId");
		keyCondition.setValue(keyPrefix);
		Criterion criterion = new CriterionBuilder()
				.withSequence("MAL_BLK_ID_GEN")
				.withKeyCondition(keyCondition)
				.withPrefix(keyPrefix).build();
		return keyUtils.getKey(criterion);
	}

	/**
	 * Method		:	MailController.findCN46TransferManifestDetails Added by 	:	A-10647 on 27-Oct-2022 Used for 	: Parameters	:	@param transferManifestVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<ConsignmentDocumentVO>
	 */
	public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO) {
		return constructDAO().findCN46TransferManifestDetails(transferManifestVO);
	}
	public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) {
		try {
			return constructDAO().fetchFlightPreAdviceDetails(flightFilterVOs);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(), persistenceException.getMessage(),
					persistenceException);
		}
	}

	/**
	 * Method		:	MailController.updateActualWeightForMailContainer Added by 	:	A-6245 on 20-Dec-2022 Added for 	:	IASCB-184276 Parameters	:	@param containerVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	ContainerVO
	 */
	public ContainerVO updateActualWeightForMailContainer(ContainerVO containerVO) {
		if (isNotNullAndEmpty(containerVO.getContainerNumber()) && Objects.nonNull(containerVO.getActualWeight())) {
			ContainerAssignmentVO containerAssignmentVO = findLatestContainerAssignment(
					containerVO.getContainerNumber());
			if (Objects.nonNull(containerAssignmentVO)
					&& ContainerAssignmentVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
					&& MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())) {
				Container container = null;
				ContainerPK containerPK = new ContainerPK();
				containerPK.setCompanyCode(containerAssignmentVO.getCompanyCode());
				containerPK.setContainerNumber(containerAssignmentVO.getContainerNumber());
				containerPK.setAssignmentPort(containerAssignmentVO.getAirportCode());
				containerPK.setCarrierId(containerAssignmentVO.getCarrierId());
				containerPK.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerPK.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerPK.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				try {
					container = Container.find(containerPK);
				} catch (FinderException e) {
					log.debug("" + "FinderException in updateActualWeightForMailContainer" + " " + e);
				}
				if (Objects.nonNull(container)) {
					container.setActualWeight(containerVO.getActualWeight().getValue().doubleValue());
					container.setActualWeightDisplayValue(containerVO.getActualWeight().getDisplayValue().doubleValue());
					container.setActualWeightDisplayUnit(containerVO.getActualWeight().getDisplayUnit().getName());
					containerVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
					containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					containerVO.setCarrierId(containerAssignmentVO.getCarrierId());
					containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					containerVO.setAssignedPort(containerAssignmentVO.getAirportCode());
					containerVO.setFlightDate(containerAssignmentVO.getFlightDate());
					containerVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
					return containerVO;
				}
			}
		}
		return null;
	}

	public void validateAndPopulateShipmentCustomsInformationVOsForACS(ShipmentDetailVO shipmentDetailVO) {
		Collection<String> airportCodes = new ArrayList<>();
		airportCodes.add(shipmentDetailVO.getOrigin());
		airportCodes.add(shipmentDetailVO.getDestination());
		Map<String, AirportValidationVO> countryCodeMap = null;
		try {
			countryCodeMap = sharedAreaProxy.validateAirportCodes(shipmentDetailVO.getCompanyCode(), airportCodes);
		} finally {
		}
		if (Objects.nonNull(countryCodeMap) && Objects.nonNull(countryCodeMap.get(shipmentDetailVO.getOrigin()))
				&& MailConstantsVO.AUSTRALIA.equals(countryCodeMap.get(shipmentDetailVO.getOrigin()).getCountryCode())
				&& Objects.nonNull(countryCodeMap.get(shipmentDetailVO.getDestination())) && !MailConstantsVO.AUSTRALIA
				.equals(countryCodeMap.get(shipmentDetailVO.getDestination()).getCountryCode())) {
			populateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
		}
	}

	private static void populateShipmentCustomsInformationVOsForACS(ShipmentDetailVO shipmentDetailVO) {
		Collection<ShipmentCustomsInformationVO> shipmentCustomsInformationVOs = new ArrayList<>();
		ShipmentCustomsInformationVO shipmentCustomsInformationVO = new ShipmentCustomsInformationVO();
		shipmentCustomsInformationVO.setCustomsAuthority(MailConstantsVO.AUSTRALIAN_CUSTOMS);
		shipmentCustomsInformationVO.setParameter(MailConstantsVO.CAN);
		shipmentCustomsInformationVO.setValue(MailConstantsVO.EXML);
		shipmentCustomsInformationVO.setOperationFlag(ShipmentCustomsInformationVO.OPERATION_FLAG_INSERT);
		shipmentCustomsInformationVOs.add(shipmentCustomsInformationVO);
		shipmentDetailVO.setShipmentCustomsInformationVOs(shipmentCustomsInformationVOs);
	}

	/**
	 * Method		:	MailController.findFlightsForMailInboundAutoAttachAWB Added by 	:	A-6245 on 20-Dec-2022 Used for 	:	Added for IASCB-187122 Parameters	:	@param mailInboundAutoAttachAWBJobScheduleVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<OperationalFlightVO>
	 */
	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) {
		return constructDAO().findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
	}
	public Page<MailTransitVO> findMailTransit(MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws PersistenceException {
		log.debug(CLASS + " : " + "findMailTransit" + " Entering");
		return constructDAO().findMailTransit(mailTransitFilterVO, pageNumber);
	}
	/**
	 * @author U-1519
	 * @param mailbagEnquiryFilterVO
	 * @return MailbagVO
	 * @throws SystemException
	 */
	public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) {
		log.debug(CLASS + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		MailbagVO mailbagVO = null;
		try {
			mailbagVO = constructDAO().findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(), persistenceException.getMessage(),
					persistenceException);
		}
		if (mailbagVO != null && mailbagVO.getLatestStatus() != null
				&& mailbagVO.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)
				&& (mailbagVO.getFlightNumber() != null && mailbagVO.getFlightDate() != null
				&& mailbagVO.getFlightSequenceNumber() > 0)) {
			FlightFilterVO flightFilterVO = new FlightFilterVO();
			flightFilterVO.setCompanyCode(mailbagEnquiryFilterVO.getCompanyCode());
			flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
			flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
			flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
			flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
			Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
			for (FlightValidationVO flightValidation : flightValidationVOs) {
				mailbagVO.setFlightDate(flightValidation.getSta().toZonedDateTime());
			}
		}
		return mailbagVO;
	}

	private AirlineValidationVO findAirlineValidationVO(String companyCode, int carrierId) {
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineProxy.findAirline(companyCode, carrierId);
		} catch (SharedProxyException e) {
			log.error("Exception :", e);
		}
		return airlineValidationVO;
	}
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVo) {
		return flightOperationsProxy.findFlightListings(filterVo);
	}

	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(FlightSegmentCapacityFilterVO filterVo) {
		//TODO: Neo to implement
		//return flightOperationsProxy.findActiveAllotments(filterVo);
		return null;
	}

	public MailbagVO findMailConsumed(MailTransitFilterVO filterVo) {
		log.debug(CLASS + " : " + "findMailConsumed" + " Entering");
		return constructDAO().findMailConsumed(filterVo);
	}
//TODO: To implement in Neo
	public Collection<RateAuditVO> createRateAuditVOs(ContainerVO containerVO, Collection<MailbagVO> mailbagVOs, String mailStatusTransferred, boolean provisionalRateImport) {
		Collection<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		LoginProfile logonAttributes = ContextUtil.getInstance().callerLoginProfile();
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		RateAuditVO rateAuditVO = new RateAuditVO();
		//Added by A-7794 as part of ICRD-232299
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (!provisionalRateImport && importEnabled != null &&(importEnabled.contains("D")  && !logonAttributes.getOwnAirlineCode().equals(containerVO.getCarrierCode())) ) {
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				if (!logonAttributes.getOwnAirlineCode().equals(containerVO.getCarrierCode()) ) {
					for(MailbagVO mailbagVO:mailbagVOs){//IASCB-35658
						rateAuditVO = new RateAuditVO();
						rateAuditVO.setCompanyCode(containerVO.getCompanyCode());
						rateAuditVO.setTriggerPoint(mailStatusTransferred);
						rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
						rateAuditDetailsVO.setCarrierid(mailbagVO.getCarrierId());
						rateAuditDetailsVO.setFlightno(mailbagVO.getFlightNumber());
						rateAuditDetailsVO.setFlightseqno((int) mailbagVO.getFlightSequenceNumber());
						rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
						rateAuditDetailsVO.setSegSerNo(mailbagVO.getSegmentSerialNumber());
						rateAuditDetails.add(rateAuditDetailsVO);
						rateAuditVO.setRateAuditDetails(rateAuditDetails);
						rateAuditVOs.add(rateAuditVO);
					}
				} else if(containerVO.isFromDeviationList()) {
					for(MailbagVO mailbagVO:mailbagVOs){//IASCB-60028
						rateAuditVO = new RateAuditVO();
						rateAuditVO.setCompanyCode(containerVO.getCompanyCode());
						rateAuditVO.setTriggerPoint("DEV");
						rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
						rateAuditDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
						rateAuditDetailsVO.setCarrierid(mailbagVO.getCarrierId());
						rateAuditDetailsVO.setFlightno(mailbagVO.getFlightNumber());
						rateAuditDetailsVO.setFlightseqno((int) mailbagVO.getFlightSequenceNumber());
						rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
						rateAuditDetailsVO.setSegSerNo(mailbagVO.getSegmentSerialNumber());
						rateAuditDetails.add(rateAuditDetailsVO);
						rateAuditVO.setRateAuditDetails(rateAuditDetails);
						rateAuditVOs.add(rateAuditVO);
					}
				}else if(containerVO.isHandoverReceived()){
					for(MailbagVO mailbagVO:mailbagVOs){//IASCB-60028
						if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getLatestStatus())||MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getLatestStatus())){
							rateAuditVO = new RateAuditVO();
							rateAuditVO.setCompanyCode(containerVO.getCompanyCode());
							rateAuditVO.setTriggerPoint("DEV");
							rateAuditVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
							rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
							rateAuditDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
							rateAuditDetailsVO.setCarrierid(mailbagVO.getCarrierId());
							rateAuditDetailsVO.setFlightno(mailbagVO.getFlightNumber());
							rateAuditDetailsVO.setFlightseqno((int) mailbagVO.getFlightSequenceNumber());
							rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailbagVO.getFlightDate()));
							rateAuditDetailsVO.setSegSerNo(mailbagVO.getSegmentSerialNumber());
							rateAuditDetails.add(rateAuditDetailsVO);
							rateAuditVO.setRateAuditDetails(rateAuditDetails);
							rateAuditVOs.add(rateAuditVO);
						}
					}

				}
			}
		}
		else {
			if((provisionalRateImport || (importEnabled != null && importEnabled.contains("M"))) && mailbagVOs != null && mailbagVOs.size() > 0) {


				for (MailbagVO mailbagVo : mailbagVOs) {
					rateAuditVO = new RateAuditVO();
					Collection<RateAuditDetailsVO> rateAuditDetailsVos = new ArrayList<>();
					rateAuditVO.setCompanyCode(containerVO.getCompanyCode());
					rateAuditVO.setTriggerPoint(mailStatusTransferred);
					rateAuditDetailsVO.setSource(mailStatusTransferred);
					rateAuditVO.setScannedDate(LocalDateMapper.toLocalDate(mailbagVo.getScannedDate()));
					rateAuditVO.setMailSequenceNumber(mailbagVo.getMailSequenceNumber());
					rateAuditDetailsVO.setCarrierCode(containerVO.getCarrierCode());
					rateAuditDetailsVO.setCarrierid(containerVO.getCarrierId());
					rateAuditDetailsVO.setFlightno(containerVO.getFlightNumber());
					rateAuditDetailsVO.setFlightseqno((int) containerVO.getFlightSequenceNumber());
					rateAuditDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(containerVO.getFlightDate()));
					rateAuditDetailsVO.setSegSerNo(containerVO.getSegmentSerialNumber());
					rateAuditDetailsVos.add(rateAuditDetailsVO);
					rateAuditVO.setRateAuditDetails(rateAuditDetailsVos);
					rateAuditVOs.add(rateAuditVO);
				}


			}

		}


		return rateAuditVOs;



	}
//TODO: Neo to implement
	public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightVO operationalFlightVO) {
	}

	public void auditMailDetailUpdates(MailbagVO mailbagVO, String action, String transaction, String additionalInfo) {
		AuditConfigurationBuilder auditConfigurationBuilder = new AuditConfigurationBuilder();
		auditUtils.performAudit(auditConfigurationBuilder
				.withBusinessObject(mailbagVO)
				.withTriggerPoint(mailbagVO.getMailbagSource())
				.withActionCode(action)
				.withEventName("maildetailsUpdate")
						.withAdditionalInfo(additionalInfo)
				.withtransaction(transaction).build());
	}


	protected String getAdditionalInfoForAssignOrAcceptance(MailbagVO mailbagVO, String actionCode) {
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(mailbagVO.getScannedDate()!=null){
			additionalInfo.append(" on ").append(mailbagVO.getScannedDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
		}
		if(!("-1".equals(mailbagVO.getFlightNumber()))){
			additionalInfo.append("; to ").append(mailbagVO.getCarrierCode()).append(mailbagVO.getFlightNumber()).append(" ");
			if(mailbagVO.getFlightDate()!=null){
				additionalInfo.append(mailbagVO.getFlightDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
			}
		}else{
			additionalInfo.append("; to ").append(mailbagVO.getCarrierCode());
		}
		additionalInfo.append(" ; at airport ");
		if(mailbagVO.getScannedPort()!=null){
			additionalInfo.append(mailbagVO.getScannedPort());
		}else{
			additionalInfo.append(contextUtil.callerLoginProfile().getAirportCode());
		}
		if(mailbagVO.getUldNumber()!=null || mailbagVO.getContainerNumber()!=null){
			if(mailbagVO.getUldNumber()!=null){
				additionalInfo.append(" ; in container ").append(mailbagVO.getUldNumber());
			}else{
				additionalInfo.append(" ; in container ").append(mailbagVO.getContainerNumber());
			}
		}
		if(mailbagVO.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
		}
		additionalInfo.append(" ; by user ").append(contextUtil.callerLoginProfile().getUserId());
		if(mailbagVO.getMailSource()!=null){
			additionalInfo.append(" ; Source -").append(mailbagVO.getMailSource());
		}
		return additionalInfo.toString();
	}

	private String getAdditionalInfoForMailTransfer(MailbagVO mailbagVO, String actionCode) {
		StringBuffer additionalInfo = new StringBuffer();
		additionalInfo.append(actionCode.toLowerCase());
		if(mailbagVO.getScannedDate()!=null){
			additionalInfo.append(" on ").append(mailbagVO.getScannedDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
		}
		if(!("-1".equals(mailbagVO.getFlightNumber()))){
			additionalInfo.append("; to ").append(mailbagVO.getCarrierCode()).append(mailbagVO.getFlightNumber());
			if(mailbagVO.getFlightDate()!=null){
				additionalInfo.append(" ").append(mailbagVO.getFlightDate().format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT));
			}
		}else{
			additionalInfo.append("; to ").append(mailbagVO.getCarrierCode());
		}
		additionalInfo.append(" ; at airport ");
		if(mailbagVO.getScannedPort()!=null){
			additionalInfo.append(mailbagVO.getScannedPort());
		}else{
			additionalInfo.append(contextUtil.callerLoginProfile().getAirportCode());
		}
		if(mailbagVO.getContainerNumber()!=null){
			additionalInfo.append(" ; in container ").append(mailbagVO.getContainerNumber());
		}
		if(mailbagVO.getPou()!=null){
			additionalInfo.append(" ; POU -").append(mailbagVO.getPou());
		}
		additionalInfo.append(" ; by user ").append(contextUtil.callerLoginProfile().getUserId()).
				append(" ; Source -").append(mailbagVO.getMailSource());
		return additionalInfo.toString();

	}
	public void auditContainerUpdates(ContainerVO containerVO, String action, String transaction, String additionalInfo, String triggerPoint) {
		AuditConfigurationBuilder auditConfigurationBuilder = new AuditConfigurationBuilder();
		auditUtils.performAudit(auditConfigurationBuilder
				.withBusinessObject(containerVO)
				.withTriggerPoint(triggerPoint)
				.withActionCode(action)
				.withEventName("containerUpdate")
				.withAdditionalInfo(additionalInfo)
				.withtransaction(transaction).build());
	}
	public TransferManifestVO generateTransferManifestReportDetails(String companyCode,String transferManifestId ) {
		return TransferManifest.generateTransferManifestReport(companyCode, transferManifestId);
	}
	public Collection<MailbagVO> generateMailTagDetails(Collection<MailbagVO> mailbagVOs) throws SystemException{
		return Mailbag.generateMailTag(mailbagVOs);
	}

	public String findAgentCodeForPA(String companyCode, String officeOfExchange) {
		return mailMasterApi.findAgentCodeForPA(companyCode, officeOfExchange);
	}
	public MailbagVO fetchMailSecurityDetails(MailScreeningFilterVO mailScreeningFilterVO) {
		MailbagVO mailbagVO = listmailbagSecurityDetails(mailScreeningFilterVO);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(mailScreeningFilterVO.getCompanyCode());
		if (mailbagVO != null && mailbagVO.getConsignmentScreeningVO() != null
				&& !mailbagVO.getConsignmentScreeningVO().isEmpty()) {
			Collection<ConsignmentScreeningVO> consignmentScreeningVOs = mailbagVO.getConsignmentScreeningVO();
			for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreeningVOs) {
				if (consignmentScreeningVO.getAgentType() != null && consignmentScreeningVO.getIsoCountryCode() != null && consignmentScreeningVO.getAgentID() != null
						&& MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR.equals(consignmentScreeningVO.getScreenDetailType())
						&& (MailConstantsVO.RA_ACCEPTING.equals(consignmentScreeningVO.getAgentType())
						|| MailConstantsVO.RA_ISSUING.equals(consignmentScreeningVO.getAgentType()))) {
					String agentId;
					boolean isNotGreenOrBlueCountryAirport = true;
					String countryMemberGroups = null;
					String countryMemberGroup = null;
					try {
						countryMemberGroup = validateCountryCode(operationalFlightVO.getCompanyCode(), consignmentScreeningVO.getIsoCountryCode(),
								countryMemberGroups);
					}  catch (SharedProxyException e) {
						log.error("Exception :", e);
						throw new SystemException(e.getMessage());
					}
					if (countryMemberGroup != null && countryMemberGroup.trim().length() > 0) {
						String[] countryMember = countryMemberGroup.split(",");
						for (String member : countryMember) {
							if (MailConstantsVO.GREEN.equals(member) || MailConstantsVO.BLUE.equals(member) || MailConstantsVO.EU.equals(member)) {
								isNotGreenOrBlueCountryAirport = false;
								break;
							}
						}
					}
					if (!isNotGreenOrBlueCountryAirport) {
						agentId = consignmentScreeningVO.getIsoCountryCode().concat("/RA").concat("/".concat(consignmentScreeningVO.getAgentID()))
								.concat(consignmentScreeningVO.getExpiryDate() != null ? "/".concat(consignmentScreeningVO.getExpiryDate()): "");
					} else {
						agentId = consignmentScreeningVO.getIsoCountryCode().concat("/RA3").concat("/".concat(consignmentScreeningVO.getAgentID()))
								.concat(consignmentScreeningVO.getExpiryDate() != null ? "/".concat(consignmentScreeningVO.getExpiryDate()): "");
					}
					consignmentScreeningVO.setAgentID(agentId);
				}
			}
		}
		return mailbagVO;
	}
    public String findRoutingDetails(String companyCode,long malseqnum) {
		String pou = null;
		try {
			pou = constructDAO().findRoutingDetails(companyCode,malseqnum);
		} catch (SystemException e) {
			log.error("Exception :", e);
		}
		return pou;
    }

	public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
			String companyCode,String tranferManifestId)throws SystemException{
		List<TransferManifestVO> transferManifestVO=new ArrayList<>();
		try {
			transferManifestVO= constructDAO().findTransferManifestDetailsForTransfer(companyCode, tranferManifestId);
		} catch (PersistenceException e) {
			e.getMessage();
		}
		return transferManifestVO;
	}
	private void collectContainerAuditDetails(Container container,ContainerVO toContainerVO) throws SystemException {
		StringBuffer additionalInfo = new StringBuffer();
		if(!"-1".equals(container.getFlightNumber())){
			additionalInfo.append(container.getCarrierCode()).append(" ").append(container.getFlightNumber()).append(", ");
		} else {
			additionalInfo.append(container.getCarrierCode()).append(", ");
		}
		additionalInfo.append(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(container.getAssignmentPort(),true).format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT)).append(", ");
		if(!"-1".equals(container.getFlightNumber())){
			additionalInfo.append(container.getAssignmentPort()).append(" - ").append(container.getPou()).append(" ");
		}
		additionalInfo.append("in ").append(container.getAssignmentPort());
		ContextUtil.getInstance().getBean(MailController.class).auditContainerUpdates(toContainerVO,"Transfer",toContainerVO.getTransactionCode(),additionalInfo.toString(),toContainerVO.getTriggerPoint());


	}
public  Collection<MailStatusVO> generateMailStatusRT(MailStatusFilterVO mailStatusFilterVO)
			throws SystemException{
		return Mailbag.generateMailStatusReport(mailStatusFilterVO);

		}


	public Collection<DailyMailStationReportVO> generateDailyMailStationRT(DailyMailStationFilterVO filterVO)
			throws SystemException {
					 return AssignedFlightSegment.generateDailyMailStationReport(filterVO);
	}
	public Collection<MailHandedOverVO> generateMailHandedOverRT(MailHandedOverFilterVO mailHandedOverFilterVO) throws SystemException {
		this.log.debug( "generateMailHandedOverReport");
		return Mailbag.generateMailHandedOverReport(mailHandedOverFilterVO);
	}
  public Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailReportFilterVO)	throws SystemException {

		return Mailbag.findDamageMailReport(damageMailReportFilterVO);
	}

	public MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo){
		return  AssignedFlightSegment.findImportManifestDetails(operationalFlightVo);
	}
	public Collection<RateAuditDetailsVO> createRateAuditVOsFromMailbag(Collection<MailbagVO>mailbagVOs) throws SystemException {
		Collection<RateAuditDetailsVO> rateAuditDetails = new ArrayList<RateAuditDetailsVO>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		String triggerForImport = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			for(MailbagVO mailbagVO : mailbagVOs){
				if(triggerForImport!=null && !triggerForImport.isEmpty()&& triggerForImport.contains("D")){
					try {
						Mailbag mailbag = Mailbag.find(createMailbagPK(mailbagVO.getCompanyCode(), mailbagVO));
						if (!(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus()) || logonAttributes.getOwnAirlineIdentifier() != mailbag.getCarrierId())) {
							continue;
						}
					}
					catch (FinderException e) {
						log.debug("Finder exc");
					}
				}
				RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
				rateAuditDetailsVO.setCompanyCode(mailbagVO.getCompanyCode());
				rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				rateAuditDetailsVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				rateAuditDetailsVO.setCarrierid(mailbagVO.getCarrierId());
				if(mailbagVO.getCarrierCode() != null){
					rateAuditDetailsVO.setCarrierCode(mailbagVO.getCarrierCode());
				}
				if(mailbagVO.getFlightNumber() != null){
					rateAuditDetailsVO.setFlightno(mailbagVO.getFlightNumber());
				}
				if(mailbagVO.getFlightSequenceNumber() != 0){
					rateAuditDetailsVO.setFlightseqno((int)mailbagVO.getFlightSequenceNumber());
				}
				if(mailbagVO.getSegmentSerialNumber() != 0){
					rateAuditDetailsVO.setSegSerNo( mailbagVO.getSegmentSerialNumber());
				}
				if(mailbagVO.getScannedPort()!=null) {
//					LocalDate date = new LocalDate(mailbagVO.getScannedPort(),Location.ARP, true);
					rateAuditDetailsVO.setLastUpdateTime(LocalDateMapper.toLocalDate(mailbagVO.getScannedDate()));
				}
				rateAuditDetailsVO.setLastUpdateUser(mailbagVO.getLastUpdateUser());
				rateAuditDetailsVO.setProcessStatus("D");
				rateAuditDetailsVO.setSource("DIS");
				rateAuditDetails.add(rateAuditDetailsVO);
			}
		}
		return rateAuditDetails;
	}
	public void flagHistoryforFlightDeparture(
			MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs, String triggerPoint) throws SystemException {
		log.debug(CLASS, "flagHistoryforFlightDeparture");
		asyncInvoker.invoke(()-> historyBuilder.flagHistoryforFlightDeparture(mailbagVO,flightVOs, triggerPoint));
		log.debug(CLASS, "flagHistoryforFlightDeparture");
	}
	public void flagAuditforFlightDeparture(
			MailbagVO mailbagVO,Collection<FlightValidationVO> flightVOs) throws SystemException {
		log.debug(CLASS, "flagAuditforFlightDeparture");
		log.debug(CLASS, "flagAuditforFlightDeparture");
	}
	public boolean checkForDepartedFlight_Atd(ContainerVO containerVO)
			throws SystemException {
		Collection<FlightValidationVO> flightValidationVOs = null;
		if(containerVO !=null){
			flightValidationVOs = flightOperationsProxy.validateFlightForAirport(createFlightFilterVO_atd(containerVO));
			if (flightValidationVOs != null) {
				for (FlightValidationVO flightValidationVO : flightValidationVOs) {
					if (flightValidationVO.getFlightSequenceNumber() == containerVO
							.getFlightSequenceNumber()
							&& flightValidationVO.getLegSerialNumber() == containerVO
							.getLegSerialNumber()) {
						if (flightValidationVO.getAtd()!=null) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

    public byte[] findMailbagDamageImages(String companyCode, String id) {
		String[] txnInfo = id.split("-DMG-");
		String fileName=txnInfo[0];
		String damageCode=txnInfo[1];
		String mailbagid=txnInfo[2];
		byte[] imageData=null;
		Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs = new ArrayList<>();
		DocumentRepositoryFilterVO documentRepositoryFilterVO = new DocumentRepositoryFilterVO();
		documentRepositoryFilterVO.setCompanyCode(companyCode);
		documentRepositoryFilterVO.setDocumentType("MAL");
		documentRepositoryFilterVO.setPurpose("DMG");
		documentRepositoryFilterVO.setDocumentValue(mailbagid);
		try {
			documentRepositoryMasterVOs =documentRepositoryProxy.getDocumentsfromRepository(documentRepositoryFilterVO);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		if(!documentRepositoryMasterVOs.isEmpty()){

			for(DocumentRepositoryMasterVO documentRepositoryMasterVO:documentRepositoryMasterVOs){
				List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs =documentRepositoryMasterVO.getAttachments();
				for(DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO:documentRepositoryAttachmentVOs){
					if(damageCode.equals(documentRepositoryAttachmentVO.getTransactionDataRef2())&&fileName.equals(documentRepositoryAttachmentVO.getFileName())){
						imageData=documentRepositoryAttachmentVO.getAttachmentData();
					}
				}
			}
		}
		return imageData;

	}
	public void flagFoundArrivalResdits(MailArrivalVO mailArrivalVO) {
		if (!mailArrivalVO.isFoundResditSent()) {
			String key = String.format("%s-%s", mailArrivalVO.getFlightNumber(), mailArrivalVO.getFlightCarrierCode());
			MailArrivalModel arrivalModel = mailOperationsMapper.mailArrivalVOToMailArrivalModel(mailArrivalVO);
			FoundArrivalResditEvent arrivalevent = mailEventsMapper.constructFoundArrivalResditEvent(arrivalModel);

			mailEventsProducer.publishEvent(key, arrivalevent);
		}

	}

	public void flagResditsForCloseMailInboundFlight(OperationalFlightVO operationalFlightVO){

		String key =String.format("%s-%s",operationalFlightVO.getFlightNumber(),operationalFlightVO.getFlightStatus(),
				operationalFlightVO.getCarrierCode());
		OperationalFlightModel operationalFlightModel = mailOperationsMapper.operationalFlightVOToOperationalFlightModel(operationalFlightVO);
		LostResditEvent lostResditEvent =mailEventsMapper.constructLostResditEvent(operationalFlightModel);
		mailEventsProducer.publishEvent(key,lostResditEvent);
	}
}




