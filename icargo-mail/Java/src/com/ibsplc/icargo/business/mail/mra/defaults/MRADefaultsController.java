/*
 * MRADefaultsController.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import static com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO.BILLING_STATUS_ACTIVE;
import static com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO.BILLING_STATUS_CANCELLED;
import static com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO.BILLING_STATUS_INACTIVE;
import static com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO.BILLING_STATUS_NEW;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAFlightFinaliseVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.cra.miscbilling.blockspace.flight.utilization.vo.BlockSpaceFlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.AuditHelper;
import com.ibsplc.icargo.business.mail.mra.MailMRAHelper;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.SharedProxyException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.RejectionMemo;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteDetailsAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicKeyLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MCAAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailBillingInterfaceFileJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVersionLOVVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailExceptionReportsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineErrorVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailAWBVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.MRAGPABillingDetails;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.CRAMiscbillingProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingMRAProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCitypairProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.TariffTaxProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.WorkflowDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicProductMessageVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.citypair.CityPairBusinessException;
import com.ibsplc.icargo.business.shared.citypair.vo.CityPairVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateConfigVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateFilterVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.tariff.tax.vo.ChargeDetailsVO;
import com.ibsplc.icargo.business.tariff.tax.vo.TaxFilterVO;
import com.ibsplc.icargo.business.tariff.tax.vo.TaxVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.jobscheduler.SchedulerAgent;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Advices;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;
import com.ibsplc.xibase.server.jobscheduler.business.schedule.vo.ScheduleVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;

/**
 * The Class MRADefaultsController.
 *
 * @author A-1556
 */
@Module("mail")
@SubModule("mra")
public class MRADefaultsController {

	/** The log. */
	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/** The Constant CLASS_NAME. */
	private static final String CLASS_NAME = "MRADefaultsController";

	/** The Constant STATUS_ACTIVE. */
	private static final String STATUS_ACTIVE = "A";

	/** The Constant STATUS_INACTIVE. */
	private static final String STATUS_INACTIVE = "I";

	/** The Constant STATUS_EXPIRED. */
	private static final String STATUS_EXPIRED = "E";

	/** The Constant STATUS_CANCELLED. */
	private static final String STATUS_CANCELLED = "C";

	/** The Constant STATUS_NEW. */
	private static final String STATUS_NEW = "N";

	/** The Constant STATUS_NEW. */
	private static final String STATUS_APPROVE = "A";

	/** The Constant STATUS_DRAFT. */
	private static final String STATUS_DRAFT = "D";

	/** The Constant VERSION_LATEST. */
	private static final String VERSION_LATEST = "LATEST";

	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mail.mra.defaults";

	/** The Constant ACTIVATE. */
	private static final String ACTIVATE = "A";

	/** The Constant CANCEL. */
	private static final String CANCEL = "C";

	/** The Constant INACTIVATE. */
	private static final String INACTIVATE = "I";

	/** The Constant NEW. */
	private static final String NEW = "N";

	/** The Constant KEY_MESSAGE_REFERENCE. */
	private static final String KEY_MESSAGE_REFERENCE = "INVOICCLAIM_MESSAGEREF";

	/** The Constant SYS_PARA_ACCOUNTING_ENABLED. */
	private static final String SYS_PARA_ACCOUNTING_ENABLED="cra.accounting.isaccountingenabled";

	/** The Constant ERROR_SEARCH. */
	private static final String ERROR_SEARCH = "mailtracking.mra.defaults.irregularity.msg.err.noirregularitylistexists";

	/** The Constant IRPSTATUS. */
	private static final String IRPSTATUS="mra.defaults.irpstatus";

	/** The Constant FROM_RATEAUDIT. */
	private static final String FROM_RATEAUDIT="fromrateaudit";

	/** The Constant PAYABLE. */
	private static final String PAYABLE = "P";

	private static final Log LOGGER = LogFactory.getLogger("MRA:DEFAULTS");
	/** The Constant RECEIVABLE. */
	private static final String RECEIVABLE = "R";

	/** The Constant RETENSION. */
	private static final String RETENSION = "T";

	/** The Constant AIRLINEFLG. */
	private static final String AIRLINEFLG = "A";

	/** The Constant GPAFLG. */
	private static final String GPAFLG = "G";

	/** The Constant MRA_DEFAULTS_RETURN_ERROR. */
	private static final String MRA_DEFAULTS_RETURN_ERROR = "ERROR";

	/** The Constant APPROVED. */
	private static final String APPROVED = "A";

	/** The Constant REJECTED. */
	private static final String REJECTED = "R";
	

	/** The Constant BILLABLE. */
	private static final String BILLABLE = "BB";

	private static final String COMPLETED = "C";
	
	private static final String PARTIALLY_COMPLETED="P";
	
	private static final String FAILED="F";

	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String SYS_PARAM_DAYS_FOR_SAFT="mailtracking.mra.DaysforSAFTinterfaceJob";
	private static final String SYS_PARAM_FRMINVDAT_FOR_SAFT="mailtracking.mra.FromInvoiceGenerationDateforSAFTInterface";
	private static final String MRA_INTERFACE_FILTYP="MRAINTRPT";
	private static final String MRA_INTERFACE_FROMDATE_FILTER_CODE="FRMDAT";
	private static final String MRA_INTERFACE_TODATE_FILTER_CODE="TOODAT";
	private static final String INTERFACED_FILTER_CODE="INTFCDFLG";
	private static final String INTERFACED_FILTER_VALUE="N";
	private static final String MRA_CONFIGURATION="MRACFG";
	private static final String TAX_GROUP="Tax Group";
	private static final String TAX_PARAMETER="taxParameter";
	private static final String MRA_TAX_CONFIGURATION="MRATAXVALUE";
	private static final String TAX_VALUE="taxValue";
	private static final String TAX_COUNTRY="taxCountry";
	private static final String MRA_COUNTRY_CONFIGURATION="MRATAXCOUNTRY";
	private static final String SYS_PARA_TRIGGER_FOR_MRAIMPORT="mailtracking.mra.triggerforimport";
	private static final String SYS_PARA_AGENTTYPE_RESTRICT_IMP="mailtracking.mra.defaults.AgentTypeToRestrictMailImport";
	
	private static final String UPU ="UPU";//added by a-7871 for ICRD-223130
	private static final String F ="F";//added by a-7871 for ICRD-223130
	private static final String C ="C";//added by a-7871 for ICRD-223130
	private static final String REMARK_FAILED ="UPU Rate Card Activation failed for the selected rate lines of rate card ";//added by a-7871 for ICRD-223130
	private static final String REMARK_SUCCESS ="UPU Rate Card Activation completed successfully for the selected rate lines of rate card ";//added by a-7871 for ICRD-223130
	private static final String ORIGIN =" for Origin ";//added by a-7871 for ICRD-223130
	private static final String DESTINATION =" Destination ";//added by a-7871 for ICRD-223130
	private static final String VALIDITY =" having validity from ";//added by a-7871 for ICRD-223130
	private static final String TO =" to ";//added by a-7871 for ICRD-223130
	private static final String CCA_TYPE_ACTUAL = "A";
	private static final String SYS_PARA_TRUCKCOSTFORWEIGHTCHARGE="mail.mra.truckcostforweightchargeonly";
	private static final String FLAG_YES="Y";
	private static final String CRA_PAR_INVGRP="INVGRP";
	private static final String SYSPAR_BASECURRENCY = "shared.airline.basecurrency";
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373
	private static final String ACCEPTMCA = "ACPMCA";//Added for IASCB-2373
	public static final String IMPORT_DATA_TO_MRA = "I";
	public static final String ACT = "Actual";
    public static final String M = " MCA ";
    
    public static final String CHANGE_DATE_FLAG = "ChangeEndDate ";
    private static final String REMARKS="The Billing Matrix ID ";
    private static final String REMARKS_STATUS=" status update to ";
    private static final String STATUS_SUCSESS=" Successfully .";
    private static final String STATUS_FAILED=" Failed .";
	
   
	
	private static final String STA_ACT = "Active";
		
	private static final String STA_INA = "Inactive";
	
	private static final String STA_EXP = "Expired";
	
	private static final String STA_CAN = "Cancelled";
	
    private static final String PARTIALLY_COMPLETED_REMARKS="Billing lines with identical parameters and Status 'Active' is already present in the system for Rate line ID ";

	
    /** The Constant RATE_LINE_BACK_DATE_REQUIRED. */
	private static final String RATE_LINE_BACK_DATE_REQUIRED="mailtracking.mra.ratelinebackdaterequired";
	
	private static final String MBG = "MBG";
	
	static StringBuilder checkOverlapBillinglinetotal=new StringBuilder("");
	/**
	 * Instantiates a new mRA defaults controller.
	 */
	public MRADefaultsController() {
	}

	/**
	 * Saves the rate card and assosciated rate lines Used for the
	 * creation,updation of rate cards and creation,updation and deletion of
	 * rate lines based on the operation flag set in argument.
	 *
	 * @param rateCardVO the rate card vo
	 * @throws SystemException the system exception
	 * @throws RateCardException the rate card exception
	 * @throws RateLineException the rate line exception
	 * @author A-2408
	 */
	@Advice(name = "mail.mra.auditRateCardDetails" , phase=Phase.POST_INVOKE)
	public void saveRateCard(RateCardVO rateCardVO) throws SystemException,
	RateCardException, RateLineException {
		try {
			// Collection<RateLineVO> rateLineVOs=new ArrayList<RateLineVO>();
			// this collection should call another method

			ArrayList<RateLineVO> rates = null;
			int numberOfRateLines = 0;

			if (OPERATION_FLAG_UPDATE.equals(rateCardVO.getOperationFlag())) {
				// rates=new
				// ArrayList<RateLineVO>(RateCard.findAllRateLines(filterVO));
				// numberOfRateLines=rates.size();
				// Collection<RateLineVO>
				// rateLinesVOs=RateCard.findAllRateLines(filterVO);
				RateCard entity = RateCard.find(rateCardVO.getCompanyCode(),
						rateCardVO.getRateCardID());
				String previoueRateCardStatus = entity.getRateCardStatus()
				.trim();
				RateCardVO updateVO = new RateCardVO();
				String newRateCardStatus = rateCardVO.getRateCardStatus()
				.trim();
				if (previoueRateCardStatus != null
						&& previoueRateCardStatus.length() > 0
						&& newRateCardStatus != null
						&& newRateCardStatus.length() > 0) {
					if (previoueRateCardStatus.equals(newRateCardStatus)) {
						updateVO = rateCardVO;
					} else {
						updateVO = changeRateLineStatus(rateCardVO);
					}

				}

				RateCard.find(rateCardVO.getCompanyCode(),
						rateCardVO.getRateCardID()).update(updateVO);
			} else if (OPERATION_FLAG_INSERT.equals(rateCardVO
					.getOperationFlag())) {
				rateCardVO.setRateCardStatus(STATUS_NEW);
				if (rateCardVO.getRateLineVOss() != null
						&& rateCardVO.getRateLineVOss().size() > 0) {
					rates = new ArrayList<RateLineVO>(rateCardVO
							.getRateLineVOss());
					numberOfRateLines = rates.size();
					if (rates != null && numberOfRateLines > 0) {
						// Iterator
						// iterator=rateCardVO.getRateLineVOss().iterator();
						for (RateLineVO rateLineVO : rates) {

							rateLineVO.setRatelineStatus(STATUS_NEW);
						}
						rateCardVO.setRateLineVOss(new Page<RateLineVO>(rates,
								1, 0, rates.size(), 0, 0, false));
					}

				}
				//Added by A-5166 for ICRD-17262 on 08-Feb-2013
				else if(rateCardVO.getRateLineVOs() !=null
						&& rateCardVO.getRateLineVOs().size() > 0){
					rates = new ArrayList<RateLineVO>(rateCardVO
							.getRateLineVOs()
							);
					rateCardVO.setRateCardStatus(STATUS_NEW);
					numberOfRateLines = rates.size();
					if (rates != null && numberOfRateLines > 0) {
						for (RateLineVO rateLineVO : rates) {
							rateLineVO.setRatelineStatus(STATUS_NEW);
						}
						rateCardVO.setRateLineVOs(rates);
					}
				}
				//Added by A-5166 for ICRD-17262 on 08-Feb-2013
				new RateCard(rateCardVO);

			} else if (OPERATION_FLAG_DELETE.equals(rateCardVO
					.getOperationFlag())) {
				RateCard rateCard = RateCard.find(rateCardVO.getCompanyCode(),
						rateCardVO.getRateCardID());
				rateCard.remove();
			}

		} catch (FinderException e) {
			throw new SystemException(e.getMessage(), e);
		}

	}

	
	/**
	 * Finds the rate card and assosciated rate lines.
	 *
	 * @param companyCode the company code
	 * @param rateCardID the rate card id
	 * @return RateCardVO
	 * @throws SystemException the system exception
	 * @author A-2408
	 */
	public RateCardVO findRateCardDetails(String companyCode, String rateCardID,int pagenum)
	throws SystemException {
		return RateCard.findRateCardDetails(companyCode, rateCardID,pagenum);
	}

	/**
	 * Returns the ratelines based on the filter criteria.
	 *
	 * @param rateLineFilterVO the rate line filter vo
	 * @return Page<RateLineVO>
	 * @throws SystemException the system exception
	 * @author A-2408
	 */
	public Page<RateLineVO> findRateLineDetails(
			RateLineFilterVO rateLineFilterVO) throws SystemException {

		return RateCard.findRateLineDetails(rateLineFilterVO);

	}

	/**
	 * Saves the rateLine Status.
	 *
	 * @param rateLineVOs the rate line v os
	 * @throws SystemException the system exception
	 * @throws RateLineException the rate line exception
	 * @author A-2270
	 */

	public void saveRatelineStatus(Collection<RateLineVO> rateLineVOs)
	throws SystemException, RateLineException {
		// for checking if the rateLine given for saving with status 'N' are
		// overlapping,in that case throw
		// rateline Exception

		HashMap<String, RateLineVO> rateLineVoforChk = null;
		rateLineVoforChk = new HashMap<String, RateLineVO>();
		for (RateLineVO rateLineVoforCompare : rateLineVOs) {
			log.log(Log.INFO,
			"INSIDE FOR FOR CHK IN CONTROLLER>>>>>>>>>>>>>>>>>>>>. ");
			String rateID = rateLineVoforCompare.getRateCardID();
			String origin = rateLineVoforCompare.getOrigin();
			String destination = rateLineVoforCompare.getDestination();
			LocalDate fromDate = rateLineVoforCompare.getValidityStartDate();
			LocalDate toDate = rateLineVoforCompare.getValidityEndDate();
			String keyForCompare = origin.concat(destination);
			boolean isPresent = rateLineVoforChk.containsKey(keyForCompare);
			if (isPresent) {
				log.log(Log.INFO, "INSIDE PRESENT>>>>>>>>>>>>>>>>>>>>. ");
				RateLineVO rateLineVoInMap = rateLineVoforChk
				.get(keyForCompare);

				if (origin.equals(rateLineVoInMap.getOrigin())
						&& destination.equals(rateLineVoInMap.getDestination())
						&& (((rateLineVoInMap.getValidityEndDate()
								.isGreaterThan(fromDate) || (rateLineVoInMap
										.getValidityEndDate().compareTo(fromDate)) == 0))
										&& ((rateLineVoInMap.getValidityEndDate()
												.isGreaterThan(fromDate) || (rateLineVoInMap
														.getValidityEndDate()
														.compareTo(fromDate)) == 0)) || ((fromDate
																.isGreaterThan(rateLineVoInMap
																		.getValidityEndDate()) || (fromDate
																				.compareTo(rateLineVoInMap.getValidityEndDate())) == 0))
																				&& ((fromDate.isGreaterThan(rateLineVoInMap
																						.getValidityEndDate()) || (fromDate
																								.compareTo(rateLineVoInMap
																										.getValidityEndDate())) == 0)))) {
					log
					.log(Log.INFO,
					"INSIDE IF BEFORE THROWING EXCEPTION>>>>>>>>>>>>>>>>>>>>. ");
					// if(? <= LIN.VLDENDDAT AND ? >= LIN.VLDSTRDAT )))
					// Collection<RateLineVO> collnExists =
					// rateLineVoforChk.get(keyForCompare);
					// collnExists.add(rateLineVoforCompare);
					RateLineErrorVO rateLineErrorvo = new RateLineErrorVO();
					Collection<RateLineErrorVO> rateLineErrorvos = new ArrayList<RateLineErrorVO>();
					rateLineErrorvo.setOrigin(rateLineVoInMap.getOrigin());
					rateLineErrorvo.setDestination(rateLineVoInMap
							.getDestination());
					rateLineErrorvo.setCurrentRateCardID(rateLineVoInMap
							.getRateCardID());
					rateLineErrorvo.setCurrentValidityStartDate(rateLineVoInMap
							.getValidityStartDate());
					rateLineErrorvo.setCurrentValidityEndDate(rateLineVoInMap
							.getValidityEndDate());
					rateLineErrorvo.setNewRateCardID(rateID);
					rateLineErrorvo.setNewValidityStartDate(fromDate);
					rateLineErrorvo.setNewValidityEndDate(toDate);
					rateLineVoInMap.setErrorVO(rateLineErrorvo);
					// setting
					rateLineErrorvos.add(rateLineErrorvo);
					throw new RateLineException(
							RateLineException.RATELINE_EXIST, rateLineErrorvos
							.toArray());
				}
			} else {
				// Collection<RateLineVO> collnToAdd = new
				// ArrayList<RateLineVO>();
				// collnToAdd = rateLineVoforChk.values();
				// collnToAdd.add(rateLineVoforCompare);
				log.log(Log.INFO, "PUTTING IN MAP>>>>>>>>>>>>>>>>>>>>. ");
				rateLineVoforChk.put(keyForCompare, rateLineVoforCompare);
			}
		}

		//
		Collection<RateLineErrorVO> rateLineErrorVos = null;
		for (RateLineVO rateLineVo : rateLineVOs) {
			if (RateLineVO.ACTIVE.equals(rateLineVo.getRatelineStatus())) {
				rateLineErrorVos = MRARateLine.validateRatelines(rateLineVOs);
				break;
			}
		}

		log.log(Log.INFO, " inside saveRatelineStatus>>>rateLineErrorVos ",
				rateLineErrorVos);
		if (rateLineErrorVos != null && rateLineErrorVos.size() > 0) {
			log.log(Log.INFO,
			" ratelineExp found as errorvos != null>>>>>>>>>>>>>>>> ");
			rateLineVOs.iterator().next().setErrorVO(rateLineErrorVos.iterator().next());//added by a-7871 for ICRD-231043
			throw new RateLineException(RateLineException.RATELINE_EXIST,
					rateLineErrorVos.toArray());

		} else {
			log.log(Log.INFO, " ratelineErrorVos is null>>>>>>>> ");
			for (RateLineVO rateLineVo : rateLineVOs) {
				try {
					MRARateLine rateLine = MRARateLine.find(rateLineVo
							.getCompanyCode(), rateLineVo.getRateCardID(),
							rateLineVo.getRatelineSequenceNumber());
					if (rateLine != null) {
						/*
						 * Added by Sandeep as part of Optimistic Locking
						 * Mechanism..
						 */
						rateLine.setLastUpdatedTime(rateLineVo
								.getLastUpdateTime());
						rateLine.setLastUpdatedUser(rateLineVo
								.getLastUpdateUser());
						rateLine.setRatelineStatus(rateLineVo
								.getRatelineStatus());
					}
				} catch (FinderException e) {
					e.getErrorCode();
				}
			}

		}

	}

	/**
	 * Finds the rate cards based on the filter.
	 *
	 * @param rateCardFilterVO the rate card filter vo
	 * @return Page<RateCardVO>
	 * @throws SystemException the system exception
	 * @author a-2049
	 */
	public Page<RateCardVO> findAllRateCards(RateCardFilterVO rateCardFilterVO)
	throws SystemException {
		log.entering(CLASS_NAME, "findAllRateCards");
		return RateCard.findAllRateCards(rateCardFilterVO);
	}

	/**
	 * Find rate card lov.
	 *
	 * @param companyCode the company code
	 * @param rateCardId the rate card id
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-2408
	 */
	public Page<RateCardLovVO> findRateCardLov(String companyCode,
			String rateCardId, int pageNumber) throws SystemException {
		log.entering(CLASS_NAME, "findRateCardLov");
		return RateCard.findRateCardLov(companyCode, rateCardId, pageNumber);
	}

	/**
	 * Change rate line status.
	 *
	 * @param rateCardVO the rate card vo
	 * @return RateCardVO
	 * @throws RateLineException the rate line exception
	 * @throws SystemException the system exception
	 * @author A-2408
	 */
	private RateCardVO changeRateLineStatus(RateCardVO rateCardVO)
	throws RateLineException, SystemException {
		// this collection is for those ratelines converted from 'New' to
		// 'Active' status
		Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
		RateLineFilterVO filterVO = new RateLineFilterVO();
		filterVO.setCompanyCode(rateCardVO.getCompanyCode());
		filterVO.setRateCardID(rateCardVO.getRateCardID());
		log
				.log(Log.INFO,
						" going to find all the rate Lines for the rateCard ",
						filterVO);
		Collection<RateLineVO> rateVos = RateCard.findAllRateLines(filterVO);
		ArrayList<RateLineVO> rates = null;
		if (rateVos != null && rateVos.size() > 0) {
			rates = new ArrayList<RateLineVO>(RateCard
					.findAllRateLines(filterVO));
		} else {
			rates = new ArrayList<RateLineVO>();
		}
		// this is to check whether any new ratelines are added
		if (rateCardVO.getRateLineVOss() != null
				&& rateCardVO.getRateLineVOss().size() > 0) {
			ArrayList<RateLineVO> ratelines = new ArrayList<RateLineVO>(
					rateCardVO.getRateLineVOss());
			if (ratelines != null && ratelines.size() > 0) {

				for (RateLineVO rate : ratelines) {
					if (OPERATION_FLAG_INSERT.equals(rate.getOperationFlag())) {

						rates.add(rate);
					}

				}
			}

		}
		RateCard entity = null;
		try {
			entity = RateCard.find(rateCardVO.getCompanyCode(), rateCardVO
					.getRateCardID());
		} catch (FinderException finderException) {
			log.log(Log.SEVERE, " could nt locate the RateCard entity ");
			throw new SystemException(finderException.getMessage(),
					finderException);
		}

		String previoueRateCardStatus = entity.getRateCardStatus().trim();

		String newStatus = rateCardVO.getRateCardStatus().trim();

		if (!(STATUS_ACTIVE.equals(previoueRateCardStatus))) {

			if (STATUS_ACTIVE.equals(newStatus)) {
				// System.out.println("inside active"+rates.size());
				int endIndex = rates.size();
				for (int i = 0; i < endIndex; i++) {
					RateLineVO rateLineVO = rates.get(i);
					if (rateLineVO.getRatelineStatus() != null
							&& rateLineVO.getRatelineStatus().trim().length() > 0) {
						if (STATUS_NEW.equals(rateLineVO.getRatelineStatus()
								.trim())) {
							// System.out.println("inside new");
							// System.out.println("inside new"+i);
							rateLineVO.setRatelineStatus(STATUS_ACTIVE);
							if (!(OPERATION_FLAG_INSERT.equals(rateLineVO
									.getOperationFlag()))) {
								rateLineVO
								.setOperationFlag(OPERATION_FLAG_UPDATE);
							}

							if (rateLineVO.getValidityEndDate() != null
									&& rateLineVO.getValidityStartDate() != null
									&& rateLineVO.getOrigin() != null
									&& rateLineVO.getDestination() != null
									&& rateLineVO.getOrigin().trim().length() > 0
									&& rateLineVO.getDestination().trim()
									.length() > 0) {
								rateLineVOs.add(rateLineVO);
								// rates.remove(rateLineVO);
							}

						} else if (STATUS_INACTIVE.equals(rateLineVO
								.getRatelineStatus().trim())) {
							rateLineVO.setRatelineStatus(STATUS_ACTIVE);
							if (!(OPERATION_FLAG_INSERT.equals(rateLineVO
									.getOperationFlag()))) {
								rateLineVO
								.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}

					}
				}

			}
		}

		if (!(STATUS_INACTIVE.equals(previoueRateCardStatus))) {
			if (STATUS_INACTIVE.equals(newStatus)) {
				if (rates != null && rates.size() > 0) {
					// Iterator
					// iterator=rateCardVO.getRateLineVOss().iterator();
					for (RateLineVO rateLineVO : rates) {

						if (rateLineVO.getRatelineStatus() != null
								&& rateLineVO.getRatelineStatus().trim()
								.length() > 0) {
							if (!(STATUS_EXPIRED.equals(rateLineVO
									.getRatelineStatus().trim()) || STATUS_CANCELLED
									.equals(rateLineVO.getRatelineStatus()
											.trim()))) {
								rateLineVO.setRatelineStatus(STATUS_INACTIVE);
								if (!(OPERATION_FLAG_INSERT.equals(rateLineVO
										.getOperationFlag()))) {
									rateLineVO
									.setOperationFlag(OPERATION_FLAG_UPDATE);
								}
							}
						}
					}
				}
			}
		}
		if (!(STATUS_CANCELLED.equals(previoueRateCardStatus))) {
			if (STATUS_CANCELLED.equals(newStatus)) {
				// System.out.println("inside cancell");
				if (rates != null && rates.size() > 0) {
					// Iterator
					// iterator=rateCardVO.getRateLineVOss().iterator();
					for (RateLineVO rateLineVO : rates) {

						rateLineVO.setRatelineStatus(STATUS_CANCELLED);
						if (!(OPERATION_FLAG_INSERT.equals(rateLineVO
								.getOperationFlag()))) {
							// System.out.println("inside oprflag");
							rateLineVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						}
					}
				}
			}
		}

		if (!(STATUS_EXPIRED.equals(previoueRateCardStatus))) {
			if (STATUS_EXPIRED.equals(newStatus)) {
				if (rates != null && rates.size() > 0) {
					// Iterator
					// iterator=rateCardVO.getRateLineVOss().iterator();
					for (RateLineVO rateLineVO : rates) {

						rateLineVO.setRatelineStatus(STATUS_EXPIRED);
						if (!(OPERATION_FLAG_INSERT.equals(rateLineVO
								.getOperationFlag()))) {
							rateLineVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						}
					}
				}
			}
		}
		if (rateLineVOs != null && rateLineVOs.size() > 0) {
			rates.removeAll(rateLineVOs);
			log.log(Log.INFO,
					" inside rateline to b converted from new to active ",
					rateLineVOs.size());
			saveRatelineStatus(rateLineVOs);
		}
		// System.out.println("size of rates"+rates.size());

		rateCardVO.setRateLineVOss(new Page<RateLineVO>(rates, 0, 0, rates
				.size(), 0, 0, false));

		return rateCardVO;
	}

	/**
	 * method which will call up the changeRateLineStatus() internally for each
	 * iteration of the Collection of RateCardVOs passed from client for chaning
	 * rateCard status.
	 *
	 * @param rateCardVOs Collection of RateCardVOs
	 * @throws SystemException the system exception
	 * @throws RateLineException the rate line exception
	 * @author a-2049
	 */
	public void changeRateCardStatus(Collection<RateCardVO> rateCardVOs)
	throws SystemException, RateLineException {
		log.entering(CLASS_NAME, "changeRateCardStatus");
		if (rateCardVOs != null && rateCardVOs.size() > 0) {

			RateLineFilterVO filterVO = null;
			for (RateCardVO rateCardVO : rateCardVOs) {
				log.log(Log.INFO, "<-- change rate card status for -->",
						rateCardVO);
				filterVO = new RateLineFilterVO();
				filterVO.setCompanyCode(rateCardVO.getCompanyCode());
				filterVO.setRateCardID(rateCardVO.getRateCardID());
				// finding the associated rateLines
				Collection<RateLineVO> rates = RateCard
				.findAllRateLines(filterVO);
				if (rates != null && rates.size() > 0) {
					log.log(Log.INFO,
					" going for the method changeRateLineStatus ");
					rateCardVO = changeRateLineStatus(rateCardVO);
					rateCardVO
					.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
				}

				try {
					RateCard rateCardEntity = null;
					rateCardEntity = RateCard.find(rateCardVO.getCompanyCode(),
							rateCardVO.getRateCardID());
					rateCardEntity.update(rateCardVO);
				} catch (FinderException finderException) {
					throw new SystemException(finderException.getMessage(),
							finderException);
				}
			}
		}
		log.exiting(CLASS_NAME, "changeRateCardStatus");
	}

	/**
	 * Find billing matrix details.
	 *
	 * @param blgMtxFilterVO the blg mtx filter vo
	 * @return the billing matrix vo
	 * @throws SystemException the system exception
	 * @author A-2398
	 */
	public BillingMatrixVO findBillingMatrixDetails(
			BillingMatrixFilterVO blgMtxFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "listBillingMatrixDetails");
		BillingMatrixVO blgMtxVO = BillingMatrix
		.findBillingMatrixDetails(blgMtxFilterVO);
		log.exiting(CLASS_NAME, "listBillingMatrixDetails");
		return blgMtxVO;
	}

	/**
	 * Find billing line details.
	 *
	 * @param blgLineFilterVO the blg line filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-2398
	 */
	public Page<BillingLineVO> findBillingLineDetails(
			BillingLineFilterVO blgLineFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findBillingLineDetails");
		log.exiting(CLASS_NAME, "findBillingLineDetails");
		return BillingMatrix.findBillingLineDetails(blgLineFilterVO);
	}

	/**
	 * Find billing line values.
	 *
	 * @param blgLineFilterVO the blg line filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<BillingLineVO> findBillingLineValues(
			BillingLineFilterVO blgLineFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findBillingLineValues");

		Page<BillingLineVO> billingLineDetails = null;
		if (blgLineFilterVO.getUldType() == null
				&& blgLineFilterVO.getMailCategoryCode() == null &&

				blgLineFilterVO.getMailClass() == null &&

				blgLineFilterVO.getMailSubclass() == null &&

				blgLineFilterVO.getOrigin() == null &&
				blgLineFilterVO.getDestination() == null &&
				blgLineFilterVO.getUplift()==null &&
				blgLineFilterVO.getDischarge() == null
				&& blgLineFilterVO.getValidityEndDate()== null
				&& blgLineFilterVO.getValidityStartDate()==null && blgLineFilterVO.getBillingMatrixId()==null&&
				blgLineFilterVO.getPoaCode()==null&&blgLineFilterVO.getBillingLineId()==0) {
			billingLineDetails = BillingMatrix
			.findBillingLineDetails(blgLineFilterVO);
		} else {
			billingLineDetails = BillingMatrix
			.findBillingLineValues(blgLineFilterVO);
		}
		log.exiting(CLASS_NAME, "findBillingLineValues");
		return billingLineDetails;
	}

	/**
	 * Save billing matrix.
	 *
	 * @param billingMatrixVO the billing matrix vo
	 * @return true, if successful
	 * @throws CreateException the create exception
	 * @throws FinderException the finder exception
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	@Advice(name = "mail.mra.saveBillingMatrix" , phase=Phase.POST_INVOKE)
	public boolean saveBillingMatrix(BillingMatrixVO billingMatrixVO)
	throws CreateException, FinderException, SystemException,
	MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveBillingMatrix");

		if (OPERATION_FLAG_INSERT.equals(billingMatrixVO.getOperationFlag())) {
			//Added by A-5526 as part of Bug ICRD-69451 starts
			try
			{
				BillingMatrix billingMatrix=BillingMatrix.find(billingMatrixVO.getCompanyCode(), billingMatrixVO.getBillingMatrixId());
				billingMatrix.update(billingMatrixVO);
			}catch (SystemException e) {
			new BillingMatrix(billingMatrixVO);
			}
			//Added by A-5526 as part of Bug ICRD-69451 ends

			// log.log(1, "The matrix vo is :" + billingMatrixVO + "***"
			// + billingMatrixVO.getBillingLineVOs());
		} else if (OPERATION_FLAG_UPDATE.equals(billingMatrixVO
				.getOperationFlag())) {
			BillingMatrix billingMatrix = BillingMatrix.find(billingMatrixVO
					.getCompanyCode(), billingMatrixVO.getBillingMatrixId());
			// if(billingMatrixVO.getBillingMatrixStatus() !=
			// billingMatrix.getBillingMatrixStatus()
			// && billingMatrixVO.getStatusChanged()){
			// Collection<BillingMatrixVO> vos = new
			// ArrayList<BillingMatrixVO>();
			// vos.add(billingMatrixVO);
			// log.log(1,"calling saveBillingLineStatus..........&& vo
			// "+billingMatrixVO);
			// saveBillingLineStatus(vos,null);
			// }
			billingMatrix.update(billingMatrixVO);
		}


		else if (OPERATION_FLAG_DELETE.equals(billingMatrixVO
				.getOperationFlag())) {
			log.log(log.FINE, "Inside Remove");
			BillingMatrix billingMatrix = BillingMatrix.find(billingMatrixVO
					.getCompanyCode(), billingMatrixVO.getBillingMatrixId());
			
			Set<BillingLine> billingLine = billingMatrix.getBillingLines();
			for (BillingLine billingLines : billingLine)
			{
				
				billingLines.deleteParameters(); 
				billingLines.remove(); 
			}
			billingMatrix.remove();
		}


		log.exiting(CLASS_NAME, "saveBillingMatrix");
		return true;

	}

	/**
	 * Find all billing matrix.
	 *
	 * @param billingMatrixFilterVO the billing matrix filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-2280
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findAllBillingMatrix");

		return BillingMatrix.findAllBillingMatrix(billingMatrixFilterVO);

	}

	/**
	 * Method for changing the billing matrix and billing line status. This is
	 * the common method that is being used by the MaintainBillingMatrix,
	 * ListBillingMatrix, ListBillingLines, for changing the billing status *
	 *
	 * @param billingMatrixVOs * *
	 * @param billingLineVOs * *
	 * @throws SystemException * *
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-1872 * Mar 5, 2007 * *
	 */
	public void saveBillingLineStatus(
			Collection<BillingMatrixVO> billingMatrixVOs,
			Collection<BillingLineVO> billingLineVOs) throws SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveBillingLineStatus");
		if (billingMatrixVOs != null && billingMatrixVOs.size() > 0) {
			/*
			 * change billing status of billing matrix and all billing lines in
			 * that BillingMatrix. Validate if same billing line exists under
			 * any Billing Matrix, if not exists then find the corresponding
			 * billing matrix and billing line under that and change the status
			 * of that billing line
			 */
			changeBillingMatrixStatus(billingMatrixVOs);
		}
		if (billingLineVOs != null && billingLineVOs.size() > 0) {
			/*
			 * change the billing status of a single billing line Validate if
			 * same billing line exists under any Billing Matrix, if not exists
			 * then find the corresponding billing matrix and billing line under
			 * that and change the status of that billing line
			 */
			for (BillingLineVO billingLineVO : billingLineVOs) {
				validateBillingLines(findOverlappingBillingLines(billingLineVO,
						billingLineVO.getBillingLineStatus()));
				BillingMatrix billingMatrix = null;
				billingMatrix = BillingMatrix.find(billingLineVO
						.getCompanyCode(), billingLineVO.getBillingMatrixId());
				if (billingMatrix != null) {
					changeBillingLineStatus(billingLineVO, billingMatrix,
							billingLineVO.getBillingLineStatus());
				}
			}
		}
		log.exiting(CLASS_NAME, "saveBillingLineStatus");
	}

	/**
	 * This method is for changing the status of Billing Matrix and all the
	 * billing lines under that coming from either Maintain Billing Matrix or
	 * from List Billing Matrix screens
	 *
	 * TODO Purpose *.
	 *
	 * @param billingMatrixVOs * *
	 * @throws SystemException the system exception
	 * @author A-1872 * Mar 5, 2007 * *
	 */
	private void changeBillingMatrixStatus(
			Collection<BillingMatrixVO> billingMatrixVOs)
	throws SystemException {
		log.entering("saveBillingLineStatus", "changeBillingMatrixStatus");
		log
				.log(Log.INFO, "<:billingMatrixVOs(SIZE):>", billingMatrixVOs.size());
		BillingMatrix billingMatrix = null;
		Collection<BillingLineVO> billingLines = null;
		for (BillingMatrixVO billingMatrixVO : billingMatrixVOs) {
			if (billingMatrixVO.getOperationFlag() != null
					&& !OPERATION_FLAG_DELETE.equals(billingMatrixVO
							.getOperationFlag())) {
				billingMatrix = BillingMatrix
				.find(billingMatrixVO.getCompanyCode(), billingMatrixVO
						.getBillingMatrixId());
			}
			if (billingMatrix != null) {
				// Setting the Billing Matrix Status
				String statusToSet = billingStatusToSet(billingMatrixVO
						.getBillingMatrixStatus(), billingMatrix
						.getBillingMatrixStatus());
				log.log(Log.INFO, "<:statusToSet:>", statusToSet);
				// billingMatrix.setBillingMatrixStatus(statusToSet);
				/*
				 * Setting the status of Billing Lines i. find the billing lines
				 * under this billing matrix with the needed status
				 */
				billingLines = findBillingLines(populateBillingLineFilterVO(billingMatrixVO));
				if (billingLines != null && billingLines.size() > 0) {
					log.log(Log.INFO, "<:billingLines(SIZE):>", billingLines.size());
					log.log(Log.INFO, "<:billingLines:>", billingLines);
					for (BillingLineVO billingLineVO : billingLines) {
						validateBillingLines(findOverlappingBillingLines(
								billingLineVO, billingMatrixVO
								.getBillingMatrixStatus()));
						changeBillingLineStatus(billingLineVO, billingMatrix,
								statusToSet);
					}

				}
				billingMatrix.setBillingMatrixStatus(statusToSet);
				/**
				 * for Optimistic Locking
				 */
				billingMatrix.setLastUpdatedTime(billingMatrixVO
						.getLastUpdatedTime());
				billingMatrix.setLastUpdatedUser(billingMatrixVO
						.getLastUpdatedUser());
			}
		}
		log.exiting("saveBillingLineStatus", "changeBillingMatrixStatus");
	}

	/**
	 * Method for populating the billing line filter vo TODO Purpose *.
	 *
	 * @param billingMatrixVO * *
	 * @return billingLineFilterVO
	 * @author A-1872 * Mar 6, 2007 * *
	 */
	private BillingLineFilterVO populateBillingLineFilterVO(
			BillingMatrixVO billingMatrixVO) {
		log.entering(CLASS_NAME, "populateBillingLineFilterVO");
		BillingLineFilterVO billingLineFilterVO = new BillingLineFilterVO();
		StringBuffer statusToFind = null;
		billingLineFilterVO.setCompanyCode(billingMatrixVO.getCompanyCode());
		billingLineFilterVO.setBillingMatrixId(billingMatrixVO
				.getBillingMatrixId());
		if (BILLING_STATUS_ACTIVE.equals(billingMatrixVO
				.getBillingMatrixStatus())) {
			statusToFind = new StringBuffer();
			statusToFind.append(BILLING_STATUS_NEW);
		} else if (BILLING_STATUS_INACTIVE.equals(billingMatrixVO
				.getBillingMatrixStatus())) {
			statusToFind = new StringBuffer();
			statusToFind.append(BILLING_STATUS_NEW).append(",").append(
					BILLING_STATUS_ACTIVE);
		}
		if (statusToFind != null) {
			billingLineFilterVO.setBillingLineStatus(statusToFind.toString());
			log.log(Log.INFO, "<:status:>", statusToFind.toString());
		}
		log.log(Log.INFO, "<:billingLineFilterVO:>", billingLineFilterVO);
		log.exiting(CLASS_NAME, "populateBillingLineFilterVO");
		return billingLineFilterVO;
	}

	/**
	 * Billing status changing method * BILLING MATRIX STATUS * ACTIVE -->
	 * changes the status of all billing lines with NEW status to ACTIVE *
	 * INACTIVE --> changes the status of all billing lines except with
	 * CANCELLED to INACTIVE * CANCELLED --> changes the status of all billing
	 * lines under that matrix to CANCELLED.
	 *
	 * @param newStatus the new status
	 * @param oldStatus the old status
	 * @return billingStatusToSet
	 * @author A-1872 Mar 6, 2007
	 */
	private String billingStatusToSet(String newStatus, String oldStatus) {
		log.entering("saveBillingLineStatus", "billingStatusToSet");
		String billingMatrixStatusNew = newStatus;
		String billingMatrixStatusOld = oldStatus;
		String billingStatusToSet = null;
		log.log(Log.INFO, "<:billingMatrixStatusNew:>", billingMatrixStatusNew);
		log.log(Log.INFO, "<:billingMatrixStatusOld:>", billingMatrixStatusOld);
		if (BILLING_STATUS_NEW.equals(billingMatrixStatusOld)
				&& (BILLING_STATUS_ACTIVE.equals(billingMatrixStatusNew))) {
			billingStatusToSet = BILLING_STATUS_ACTIVE;
		} else if (!BILLING_STATUS_CANCELLED.equals(billingMatrixStatusOld)
				&& (BILLING_STATUS_INACTIVE.equals(billingMatrixStatusNew))) {
			billingStatusToSet = BILLING_STATUS_INACTIVE;
		} else if (BILLING_STATUS_CANCELLED.equals(billingMatrixStatusNew)) {
			billingStatusToSet = BILLING_STATUS_CANCELLED;
		}
		log.log(Log.INFO, "<:billingStatusToSet:>", billingStatusToSet);
		log.exiting("saveBillingLineStatus", "billingStatusToSet");
		if (billingStatusToSet == null) {
			billingStatusToSet = billingMatrixStatusNew;
		}
		return billingStatusToSet;
	}

	/**
	 * Method for changing the status of a single billing line under a billing
	 * matrix, this is used from ListRateLines screen *.
	 *
	 * @param billingLineVO * *
	 * @param billingMatrix * *
	 * @param status * *
	 * @throws SystemException the system exception
	 * @author A-1872 * Mar 5, 2007 * *
	 */
	private void changeBillingLineStatus(BillingLineVO billingLineVO,
			BillingMatrix billingMatrix, String status) throws SystemException {
		log.entering("saveBillingLineStatus", "changeSingleBillingLineStatus");
		for (BillingLine billingLine : billingMatrix.getBillingLines()) {
			if ((billingLine.getBillingLinePK().getBillingMatrixID()
					.equals(billingLineVO.getBillingMatrixId()))
					&& (billingLine.getBillingLinePK().getCompanyCode().equals(
							billingLineVO.getCompanyCode()) && (billingLine
									.getBillingLinePK().getBillingLineSequenceNumber() == billingLineVO
									.getBillingLineSequenceNumber()))) {
				log.log(Log.INFO, "<:Setting Billing Line Status :>", status);
				billingLine.setBillingLineStatus(status);
				/**
				 * for optimistic locking
				 *
				 */
				billingLine.setLastUpdatedTime(billingLineVO
						.getLastUpdatedTime());
				billingLine.setLastUpdatedUser(billingLineVO
						.getLastUpdatedUser());
			}
		}
		log.exiting("saveBillingLineStatus", "changeSingleBillingLineStatus");
	}

	/**
	 * Method returns the billing lines with the status provided *.
	 *
	 * @param billingLineVO * *
	 * @param billingLineStatus * *
	 * @return billingLineVOs * *
	 * @throws SystemException the system exception
	 * @author A-1872 * Mar 5, 2007 * *
	 */
	Collection<BillingLineVO> findOverlappingBillingLines(
			BillingLineVO billingLineVO, String billingLineStatus)
			throws SystemException {
		log.entering(CLASS_NAME, "findOverlappingBillingLines");
		int flag = 0;
		String changeEnddate = billingLineVO.getOperationFlag();
		Collection<BillingLineVO> billingLineVOs = null;
		billingLineVOs = BillingMatrix.findOverlappingBillingLines(
				billingLineVO, billingLineStatus);
		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		try {
			systemParameters = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			log.log(Log.INFO, e);
			throw new SystemException(e.getMessage());
		}
		String rateLineRequired = (systemParameters.get(RATE_LINE_BACK_DATE_REQUIRED));
		if (rateLineRequired.equals("Y") && "A".equals(billingLineStatus)&&!CHANGE_DATE_FLAG.equalsIgnoreCase(changeEnddate))
		{
			StringBuilder adInfo=new StringBuilder("");
			for (BillingLineVO linevo : billingLineVOs) {
				if (billingLineVO.getValidityStartDate().isGreaterThan(linevo.getValidityStartDate()))
				{
					linevo.setValidityEndDate(billingLineVO.getValidityStartDate().addDays(-1));	
					linevo.setLastUpdatedUser("SYSTEM");
					updateBillingLine(linevo);	
					String updatedDate = linevo.getValidityEndDate().toDisplayDateOnlyFormat();
                         //IASCB-125216
					adInfo=adInfo.append("Valid to date of Rate line ID :").append(linevo.getBillingLineSequenceNumber()).append(" auto updated to ").
							 append(updatedDate).append(" as part of activating :").
							 append(billingLineVO.getBillingMatrixId()).append("-").append(billingLineVO.getBillingLineSequenceNumber());
					String actionCode = "Auto Modified Rate line";
					auditLog(linevo.getBillingMatrixId(),billingLineVO.getCompanyCode(),actionCode,adInfo );
				}
				else
				{
					flag++;
				}
			}
		}
		else
		{
		/* change added by Indu to incooperate date range check */
		for (BillingLineVO linevo : billingLineVOs) {
			if(CHANGE_DATE_FLAG.equalsIgnoreCase(changeEnddate)&&(linevo.getBillingMatrixId()).equals(billingLineVO.getBillingMatrixId())
						&& (billingLineVO.getBillingLineSequenceNumber() == linevo.getBillingLineSequenceNumber())) {
				continue;
			}
			
			log.log(Log.INFO, "overlapping lines from query ", linevo);
			if (((billingLineVO.getValidityStartDate().isGreaterThan(
					linevo.getValidityStartDate()) || billingLineVO
					.getValidityStartDate().equals(
							linevo.getValidityStartDate())
							|| (linevo.getValidityStartDate().isGreaterThan(billingLineVO.getValidityStartDate())
									&& linevo.getValidityStartDate().isLesserThan(billingLineVO.getValidityEndDate())))
									&& (billingLineVO.getValidityEndDate().isLesserThan(
											linevo.getValidityEndDate()) || billingLineVO
											.getValidityEndDate().equals(
													linevo.getValidityEndDate())||
													(linevo.getValidityEndDate().isGreaterThan(billingLineVO.getValidityStartDate())
															&& linevo.getValidityEndDate().isLesserThan(billingLineVO.getValidityEndDate()))))||(billingLineVO.getValidityStartDate().equals(linevo.getValidityEndDate()))||
					(billingLineVO.getValidityStartDate().isLesserThan(linevo.getValidityEndDate()))) {
				if(linevo.getBillingBasis().equals(billingLineVO.getBillingBasis()))
				{
				flag++;
			}
			}
		}
		}
		log.log(Log.INFO, "value of flag after date range check", flag);
		if (flag == 0) {
			log.log(Log.INFO, "no date range overlap!!!!!!!!!", flag);
			billingLineVOs = null;
		}
		/* change added by Indu to incooperate date range check */
		log.exiting(CLASS_NAME, "findOverlappingBillingLines");
		return billingLineVOs;
	}

		public void auditLog(String matrixId,String companyCode,String actionCode, StringBuilder adInfo) throws SystemException {
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME, BillingMatrixAuditVO.AUDIT_ENTITY);	
		billingMatrixAuditVO.setBillingmatrixID(matrixId);	
		billingMatrixAuditVO.setCompanyCode(companyCode);
		billingMatrixAuditVO.setAdditionalInformation(adInfo.toString());
		billingMatrixAuditVO.setActionCode(actionCode);
		billingMatrixAuditVO.setSysFlag("SYS");
		AuditUtils.performAudit(billingMatrixAuditVO);

	}

	/**
	 * Method for finding the billing lines under a particular billing matrix *.
	 *
	 * @param billingLineFilterVO * *
	 * @return billingLines * *
	 * @throws SystemException the system exception
	 * @author A-1872 * Mar 6, 2007 * *
	 */
	private Collection<BillingLineVO> findBillingLines(
			BillingLineFilterVO billingLineFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findBillingLineDetails");
		Collection<BillingLineVO> billingLines = BillingMatrix
		.findBillingLines(billingLineFilterVO);
		log.exiting(CLASS_NAME, "findBillingLineDetails");
		return billingLines;
	}

	/**
	 * Method for checking the billing lines TODO Purpose *.
	 *
	 * @param billingLineVOs * *
	 * @throws SystemException the system exception
	 * @author A-1872 * Mar 6, 2007 * *
	 */
	private void validateBillingLines(Collection<BillingLineVO> billingLineVOs)
	throws SystemException {
		log.entering(CLASS_NAME, "validateBillingLines");

		if (billingLineVOs != null && billingLineVOs.size() > 0) {
			StringBuilder rateLines = new StringBuilder("");
			for (BillingLineVO billingLineVo : billingLineVOs) {
				rateLines.append(billingLineVo.getBillingMatrixId() + "-" + billingLineVo.getBillingLineSequenceNumber());
				
			}

			throw new SystemException(DuplicateBillingLineException.DUPLICATE_BILLING_LINE_EXIST,
					new DuplicateBillingLineException(rateLines.toString()));
		}
		log.exiting(CLASS_NAME, "enclosing_method");
	}

	/**
	 * Find billing matrix lov.
	 *
	 * @param companyCode the company code
	 * @param billingMatrixCode the billing matrix code
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-2408
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,
			String billingMatrixCode, int pageNumber) throws SystemException {
		log.entering(CLASS_NAME, "findBillingMatrixLov");
		return BillingMatrix.findBillingMatrixLov(companyCode,
				billingMatrixCode, pageNumber);
	}

	/**
	 * This method displays Mail Proration Details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "displayProrationDetails");
		return MRAProrationDetails.displayProrationDetails(prorationFilterVO);
	}

	/**
	 * lists the Proration factors.
	 *
	 * @param prorationFactorFilterVo the proration factor filter vo
	 * @return Collection<ProrationFactorVO>
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws SystemException {
		log.entering(CLASS_NAME, "findProrationFactors");
		return MRAProrationFactor.findProrationFactors(prorationFactorFilterVo);
	}

	/**
	 * Saves the Proration factors.
	 *
	 * @param prorationFactorVos the proration factor vos
	 * @return void
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public void saveProrationFactors(
			Collection<ProrationFactorVO> prorationFactorVos)
	throws SystemException {
		log.entering(CLASS_NAME, "saveProrationFactors");
		for (ProrationFactorVO prorationFactorVo : prorationFactorVos) {
			MRAProrationFactor prorationFactor = null;
			if (OPERATION_FLAG_INSERT.equals(prorationFactorVo
					.getOperationFlag())) {
				MRAProrationFactorPK prorationFactorPk = new MRAProrationFactorPK(
						prorationFactorVo.getCompanyCode(), prorationFactorVo
						.getOriginCityCode(), prorationFactorVo
						.getDestinationCityCode(), prorationFactorVo
						.getSequenceNumber());
				new MRAProrationFactor(prorationFactorPk, prorationFactorVo);
			} else if (OPERATION_FLAG_UPDATE.equals(prorationFactorVo
					.getOperationFlag())) {
				try {
					prorationFactor = MRAProrationFactor.find(prorationFactorVo
							.getCompanyCode(), prorationFactorVo
							.getOriginCityCode(), prorationFactorVo
							.getDestinationCityCode(), prorationFactorVo
							.getSequenceNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING ProrationFactor Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (prorationFactor != null) {
					prorationFactor.update(prorationFactorVo);
				}
			} else if (OPERATION_FLAG_DELETE.equals(prorationFactorVo
					.getOperationFlag())) {
				try {
					prorationFactor = MRAProrationFactor.find(prorationFactorVo
							.getCompanyCode(), prorationFactorVo
							.getOriginCityCode(), prorationFactorVo
							.getDestinationCityCode(), prorationFactorVo
							.getSequenceNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING ProrationFactor Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (prorationFactor != null) {
					prorationFactor.remove();
				}
			}
		}
		log.exiting(CLASS_NAME, "saveProrationFactors");
	}

	/**
	 * This method changes the status. Possible values of status are "New,
	 * Active, Inactive and Cancelled"
	 *
	 * @param prorationFactorVo the proration factor vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author a-2518
	 */
	public void changeProrationFactorStatus(ProrationFactorVO prorationFactorVo)
	throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "changeProrationFactorStatus");
		if (OPERATION_FLAG_UPDATE.equals(prorationFactorVo.getOperationFlag())) {
			MRAProrationFactor prorationFactor = null;
			try {
				prorationFactor = MRAProrationFactor.find(prorationFactorVo
						.getCompanyCode(), prorationFactorVo
						.getOriginCityCode(), prorationFactorVo
						.getDestinationCityCode(), prorationFactorVo
						.getSequenceNumber());
			} catch (FinderException finderException) {
				log
				.log(Log.SEVERE,
				" @@@@@@@@@ FINDER EXCEPTION OCCURED IN FINDING ProrationFactor Entity ");
				throw new SystemException(finderException.getErrorCode());
			}
			if (prorationFactor != null) {
				if (STATUS_ACTIVE.equals(prorationFactorVo
						.getProrationFactorStatus())) {
					ProrationFactorFilterVO prorationFactorFilterVo = new ProrationFactorFilterVO();
					prorationFactorFilterVo.setCompanyCode(prorationFactorVo
							.getCompanyCode());
					prorationFactorFilterVo.setOriginCityCode(prorationFactorVo
							.getOriginCityCode());
					prorationFactorFilterVo
					.setProrationFactorStatus(STATUS_ACTIVE);
					Collection<ProrationFactorVO> activeProrationFactorVos = MRAProrationFactor
					.findProrationFactors(prorationFactorFilterVo);
					// Checking duplicate records
					if (checkDuplicateActiveProrationFactors(
							activeProrationFactorVos, prorationFactorVo)) {
						String[] err = new String[2];
						err[0] = prorationFactorVo.getOriginCityCode();
						err[1] = prorationFactorVo.getDestinationCityCode();
						throw new MailTrackingMRABusinessException(
								DuplicateCityPairException.DUPLICATE_CITY_PAIR,
								err);
					} else {
						if (prorationFactorVo.getFromDate() == null) {
							prorationFactorVo.setFromDate(new LocalDate(
									NO_STATION, NONE, false));
						}
						if (prorationFactorVo.getToDate() == null) {
							prorationFactorVo.setToDate(new LocalDate(
									NO_STATION, NONE, false));
						}
					}
				} /*
				 * else if (STATUS_INACTIVE.equals(prorationFactorVo
				 * .getProrationFactorStatus())) { if
				 * (prorationFactorVo.getToDate() == null) {
				 * prorationFactorVo.setToDate(new LocalDate(NO_STATION,
				 * NONE, false)); } }
				 */
				prorationFactor.update(prorationFactorVo);
				log.log(Log.INFO, "ProrationFactor entity has been updated");
			}
		}
		log.exiting(CLASS_NAME, "changeProrationFactorStatus");
	}

	/**
	 * This method checks duplicate active proration factor.
	 *
	 * @param activeProrationFactorVos the active proration factor vos
	 * @param prorationFactorVo the proration factor vo
	 * @return boolean
	 * @throws SystemException the system exception
	 */
	private boolean checkDuplicateActiveProrationFactors(
			Collection<ProrationFactorVO> activeProrationFactorVos,
			ProrationFactorVO prorationFactorVo) throws SystemException {
		log.entering(CLASS_NAME, "checkDuplicateProrationFactors");
		for (ProrationFactorVO activeProrationFactorVo : activeProrationFactorVos) {
			if (prorationFactorVo.getDestinationCityCode().equals(
					activeProrationFactorVo.getDestinationCityCode())) {
				// Checking Valid from and Valid to date range
				if ((prorationFactorVo.getFromDate().isGreaterThan(
						activeProrationFactorVo.getFromDate()) || (prorationFactorVo
								.getFromDate().equals(activeProrationFactorVo
										.getFromDate())))
										&& (prorationFactorVo.getToDate().isLesserThan(
												activeProrationFactorVo.getToDate()) || (prorationFactorVo
														.getToDate().equals(activeProrationFactorVo
																.getToDate())))) {
					log.exiting(CLASS_NAME, "checkDuplicateParameters");
					return true;
				}
			}
		}
		log.exiting(CLASS_NAME, "checkDuplicateProrationFactors");
		return false;
	}

	/**
	 * Finds mail contract details.
	 *
	 * @param companyCode the company code
	 * @param contractReferenceNumber the contract reference number
	 * @param versionNumber the version number
	 * @return MailContractVO
	 * @throws SystemException the system exception
	 * @author A-2518
	 */
	public MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
	throws SystemException {
		log.entering(CLASS_NAME, "viewMailContract");
		return MailContractMaster.viewMailContract(companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 * Save mail sla.
	 *
	 * @param mailSLAVo the mail sla vo
	 * @throws SystemException the system exception
	 * @author a-2524
	 */
	public void saveMailSla(MailSLAVO mailSLAVo) throws SystemException {
		MailSLAMaster mailSLAMaster = null;
		int serialNumber = 0;
		log.entering("MRADefaultsController", "saveMailSla");
		if (mailSLAVo != null) {
			serialNumber = MailSLAMaster.findMaxSerialNumber(mailSLAVo
					.getCompanyCode(), mailSLAVo.getSlaId());
			log.log(Log.FINE, "<------serialNumber---->>>>>>>", serialNumber);
			if (MailSLAVO.OPERATION_FLAG_INSERT.equals(mailSLAVo
					.getOperationFlag())) {
				log.log(Log.FINE, "Inserting A New Mail SLA Template  ");
				new MailSLAMaster(mailSLAVo);
				for (MailSLADetailsVO mailSLADetailsVo : mailSLAVo
						.getMailSLADetailsVos()) {
					mailSLADetailsVo.setSerialNumber(++serialNumber);
					new MailSLADetail(mailSLADetailsVo);
				}
			} else if (MailSLAVO.OPERATION_FLAG_UPDATE.equals(mailSLAVo
					.getOperationFlag())) {
				log.log(Log.FINE, "Updatedating An Existing Mail SLA Record  ");
				Collection<MailSLADetailsVO> mailSLADetailsVOs = mailSLAVo
				.getMailSLADetailsVos();
				if (mailSLADetailsVOs != null && mailSLADetailsVOs.size() > 0) {
					for (MailSLADetailsVO mailSLADetailsVo : mailSLADetailsVOs) {
						if (MailSLADetailsVO.OPERATION_FLAG_INSERT
								.equals(mailSLADetailsVo.getOperationFlag())) {
							mailSLADetailsVo.setSerialNumber(++serialNumber);
							new MailSLADetail(mailSLADetailsVo);
						} else if (MailSLADetailsVO.OPERATION_FLAG_UPDATE
								.equals(mailSLADetailsVo.getOperationFlag())) {
							MailSLADetail mailSLADetail = MailSLADetail.find(
									mailSLADetailsVo.getCompanyCode(),
									mailSLADetailsVo.getSlaId(),
									mailSLADetailsVo.getSerialNumber());
							mailSLADetail.update(mailSLADetailsVo);
						} else if (MailSLADetailsVO.OPERATION_FLAG_DELETE
								.equals(mailSLADetailsVo.getOperationFlag())) {
							MailSLADetail mailSLADetail = MailSLADetail.find(
									mailSLADetailsVo.getCompanyCode(),
									mailSLADetailsVo.getSlaId(),
									mailSLADetailsVo.getSerialNumber());
							mailSLADetail.remove();

						}
					}
				}
				mailSLAMaster = MailSLAMaster.find(mailSLAVo.getCompanyCode(),
						mailSLAVo.getSlaId());
				mailSLAMaster.update(mailSLAVo);
			} else if (MailSLAVO.OPERATION_FLAG_DELETE.equals(mailSLAVo
					.getOperationFlag())) {
				log.log(Log.FINE, "Delete An Existing Mail SLA Record  ");
				mailSLAMaster = MailSLAMaster.find(mailSLAVo.getCompanyCode(),
						mailSLAVo.getSlaId());
				for (MailSLADetailsVO mailSLADetailsVo : mailSLAVo
						.getMailSLADetailsVos()) {
					MailSLADetail mailSLADetail = MailSLADetail.find(
							mailSLADetailsVo.getCompanyCode(), mailSLADetailsVo
							.getSlaId(), mailSLADetailsVo
							.getSerialNumber());
					mailSLADetail.remove();
				}
				mailSLAMaster.update(mailSLAVo);
				mailSLAMaster.remove();
				log.log(Log.FINE, " Deleted An Existing Mail SLA Record  ");
			}
		}
		log.exiting("MRADefaultsController", "saveMailSla");
	}

	/**
	 * Find mail sla.
	 *
	 * @param companyCode the company code
	 * @param slaId the sla id
	 * @return MailSLAVO
	 * @throws SystemException the system exception
	 * @author a-2524
	 */
	public MailSLAVO findMailSla(String companyCode, String slaId)
	throws SystemException {
		log.entering("MRADefaultsController", "findMailSla");
		return MailSLAMaster.findMailSla(companyCode, slaId);
	}

	/**
	 * Saves mail contract details.
	 *
	 * @param mailContractVo the mail contract vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-2518
	 */
	public void saveMailContract(MailContractVO mailContractVo)
	throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveMailContract");
		if (mailContractVo != null) {
			if (OPERATION_FLAG_INSERT.equals(mailContractVo.getOperationFlag())) {
				// Checking duplicate mail contract
				Collection<MailContractVO> mailContractVOs = MailContractMaster
				.viewDuplicateMailContract(mailContractVo
						.getCompanyCode(), mailContractVo.getGpaCode(),
						mailContractVo.getAirlineCode());
				MailContractVO mailContractDuplicateVo = checkDuplicateParameters(
						mailContractVOs, mailContractVo);
				if (mailContractDuplicateVo.getMailContractDetailsVos() != null
						&& mailContractDuplicateVo.getMailContractDetailsVos()
						.size() > 0) {
					Object[] errorData = new Object[] { mailContractDuplicateVo };
					throw new MailTrackingMRABusinessException(
							DuplicateContractException.DUPLICATE_MAILCONTRACT,
							errorData);
				} else {
					// If no duplicate mail contract exists, insert the new mail
					// contract
					mailContractVo.setVersionNumber("1");
					/*
					 * If atleast one billing matrix code is present, mail
					 * contract status will be 'Active', else it will be in
					 * 'Draft' status
					 */
					if (mailContractVo.getBillingDetails() != null
							&& mailContractVo.getBillingDetails().size() > 0) {
						mailContractVo.setAgreementStatus(STATUS_ACTIVE);
					} else {
						mailContractVo.setAgreementStatus(STATUS_DRAFT);
					}
					new MailContractMaster(mailContractVo);
					log
					.log(Log.INFO,
					"@@@@@ MailContractMaster entity has been persisted @@@@@");
				}
			}
			if (OPERATION_FLAG_UPDATE.equals(mailContractVo.getOperationFlag())) {
				// Move the latest mail contract to history
				MailContractVO mailContractVoForHistory = MailContractMaster
				.viewMailContract(mailContractVo.getCompanyCode(),
						mailContractVo.getContractReferenceNumber(),
						VERSION_LATEST);
				new MailContractHistory(mailContractVoForHistory);
				log
				.log(Log.INFO,
				"@@@@@ MailContractHistory entity has been persisted @@@@@");
				// Update the latest mail contract
				int maximumVersionNumber = MailContractMaster
				.findMaximumVersionNumber(mailContractVo
						.getCompanyCode(), mailContractVo
						.getContractReferenceNumber());
				mailContractVo.setVersionNumber(Integer
						.toString(++maximumVersionNumber));
				// Updating mail contract details
				if (mailContractVo.getMailContractDetailsVos() != null
						&& mailContractVo.getMailContractDetailsVos().size() > 0) {
					for (MailContractDetailsVO mailContractDetailsVo : mailContractVo
							.getMailContractDetailsVos()) {
						if (OPERATION_FLAG_INSERT.equals(mailContractDetailsVo
								.getOperationFlag())) {
							mailContractDetailsVo.setCompanyCode(mailContractVo
									.getCompanyCode());
							mailContractDetailsVo
							.setContractReferenceNumber(mailContractVo
									.getContractReferenceNumber());
							new MailContractDetail(mailContractDetailsVo);
							log
							.log(Log.INFO,
							"@@@@@ MailContractDetail entity has been persisted @@@@@");
						} else if (OPERATION_FLAG_UPDATE
								.equals(mailContractDetailsVo
										.getOperationFlag())) {
							MailContractDetail mailContractDetail = null;
							try {
								mailContractDetail = MailContractDetail
								.find(mailContractDetailsVo);
							} catch (FinderException finderException) {
								log
								.log(Log.SEVERE,
								"@@@@@ MailContractDetail entity not found @@@@@");
							}
							if (mailContractDetail != null) {
								mailContractDetail
								.update(mailContractDetailsVo);
								log
								.log(Log.INFO,
								"@@@@@ MailContractDetail entity has been updated @@@@@");
							}
						} else if (OPERATION_FLAG_DELETE
								.equals(mailContractDetailsVo
										.getOperationFlag())) {
							MailContractDetail mailContractDetail = null;
							try {
								mailContractDetail = MailContractDetail
								.find(mailContractDetailsVo);
							} catch (FinderException finderException) {
								log
								.log(Log.SEVERE,
								"@@@@@ MailContractDetail entity not found @@@@@");
							}
							if (mailContractDetail != null) {
								mailContractDetail.remove();
								log
								.log(Log.INFO,
								"@@@@@ MailContractDetail entity has been removed @@@@@");
							}
						}
					}
				}
				// If billing matrix details have been modified, delete them and
				// insert again
				if (mailContractVo.isBillingMatrixModified()) {
					// Deleting billing matrix details
					MailContractVO mailContractVoForBillingMatrix = MailContractMaster
					.viewMailContract(
							mailContractVo.getCompanyCode(),
							mailContractVo.getContractReferenceNumber(),
							VERSION_LATEST);
					if (mailContractVoForBillingMatrix.getBillingDetails() != null
							&& mailContractVoForBillingMatrix
							.getBillingDetails().size() > 0) {
						for (String billingMatrixCode : mailContractVoForBillingMatrix
								.getBillingDetails()) {
							MailContractBillingDetail mailContractBillingDetail = null;
							try {
								mailContractBillingDetail = MailContractBillingDetail
								.find(
										mailContractVoForBillingMatrix
										.getCompanyCode(),
										mailContractVoForBillingMatrix
										.getContractReferenceNumber(),
										billingMatrixCode);
							} catch (FinderException finderException) {
								log
								.log(Log.SEVERE,
								"@@@@@ MailContractBillingDetail entity not found @@@@@");
							}
							if (mailContractBillingDetail != null) {
								mailContractBillingDetail.remove();
								log
								.log(Log.INFO,
								"@@@@@ MailContractBillingDetail entity has been removed @@@@@");
							}
						}
					}
					// Inserting billing matrix details
					if (mailContractVo.getBillingDetails() != null
							&& mailContractVo.getBillingDetails().size() > 0) {
						for (String billingMatrixCode : mailContractVo
								.getBillingDetails()) {
							new MailContractBillingDetail(mailContractVo
									.getCompanyCode(), mailContractVo
									.getContractReferenceNumber(),
									billingMatrixCode);
							log
							.log(Log.INFO,
							"@@@@@ MailContractBillingDetail entity has been persisted @@@@@");
						}
					}
				}
				// Updating latest mail contract
				MailContractMaster mailContractMaster = null;
				try {
					mailContractMaster = MailContractMaster
					.find(mailContractVo);
				} catch (FinderException finderException) {
					log.log(Log.SEVERE,
					"@@@@@ MailContractMaster entity not found @@@@@");
				}
				if (mailContractMaster != null) {
					mailContractMaster.update(mailContractVo);
					log
					.log(Log.INFO,
					"@@@@@ MailContractMaster entity has been updated @@@@@");
				}
			}
		}
		log.exiting(CLASS_NAME, "saveMailContract");
	}

	/**
	 * Changes the agreement status - Possible values for status can be -
	 * <li>A - Active, C - Cancelled</li>.
	 *
	 * @param mailContractVOs the mail contract v os
	 * @throws SystemException the system exception
	 * @author A-2518
	 */
	public void changeMailContractStatus(
			Collection<MailContractVO> mailContractVOs) throws SystemException {
		log.entering(CLASS_NAME, "changeMailContractStatus");
		MailContractMaster mailContractMaster = null;
		try {
			for (MailContractVO mailContractVO : mailContractVOs) {
				mailContractMaster = MailContractMaster.find(mailContractVO);
				mailContractMaster.setAgreementStatus(mailContractVO
						.getAgreementStatus());
			}

		} catch (FinderException finderException) {
			log.log(Log.SEVERE,
			"@@@@@ MailContractMaster entity not found @@@@@");
		}
		log.log(Log.INFO,
		"@@@@@ MailContractMaster entity has been updated @@@@@");
		log.exiting(CLASS_NAME, "changeMailContractStatus");
	}

	/**
	 * Displays Version numbers for Version LOV.
	 *
	 * @param companyCode the company code
	 * @param contractReferenceNumber the contract reference number
	 * @param versionNumber the version number
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-2518
	 */
	public Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws SystemException {
		log.entering(CLASS_NAME, "displayVersionLov");
		return MailContractMaster.displayVersionLov(companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 * Display contract details.
	 *
	 * @param contractFilterVO the contract filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO) throws SystemException {

		log.exiting(CLASS_NAME, "displayContractDetails");
		return MailContractMaster.displayContractDetails(contractFilterVO);
	}

	/**
	 * Added by A-2521 for SLAId Lov.
	 *
	 * @param slaFilterVO the sla filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<SLADetailsVO> displaySLADetails(SLAFilterVO slaFilterVO)
	throws SystemException {

		log.exiting(CLASS_NAME, "displaySLADetails");
		return MailSLAMaster.displaySLADetails(slaFilterVO);
	}

	/**
	 * Checks duplicate parameters and returns true if duplicate parameters
	 * exist and false otherwise.
	 *
	 * @param mailContractVos the mail contract vos
	 * @param mailContractVo the mail contract vo
	 * @return MailContractVO
	 * @throws SystemException the system exception
	 * @author A-2518
	 */
	private MailContractVO checkDuplicateParameters(
			Collection<MailContractVO> mailContractVos,
			MailContractVO mailContractVo) throws SystemException {
		log.entering(CLASS_NAME, "checkDuplicateParameters");
		MailContractVO mailContractVoToReturn = new MailContractVO();
		Collection<MailContractDetailsVO> mailContractDetailVos = new ArrayList<MailContractDetailsVO>();
		Map<String, MailContractDetailsVO> mailContractDetailsMap = new HashMap<String, MailContractDetailsVO>();
		String stationKey = null;
		if (mailContractVo.getMailContractDetailsVos() != null
				&& mailContractVo.getMailContractDetailsVos().size() > 0) {
			for (MailContractDetailsVO mailContractDetailsVo : mailContractVo
					.getMailContractDetailsVos()) {
				stationKey = new StringBuilder().append(
						mailContractDetailsVo.getOriginCode()).append(
								mailContractDetailsVo.getDestinationCode()).toString();
				if (!mailContractDetailsMap.containsKey(stationKey)) {
					mailContractDetailsMap.put(stationKey,
							mailContractDetailsVo);
				}
			}
		}
		if (mailContractVos != null && mailContractVos.size() > 0) {
			for (MailContractVO duplicateMailContractVo : mailContractVos) {
				for (MailContractDetailsVO mailContractDetailsVo : duplicateMailContractVo
						.getMailContractDetailsVos()) {
					stationKey = new StringBuilder().append(
							mailContractDetailsVo.getOriginCode()).append(
									mailContractDetailsVo.getDestinationCode())
									.toString();
					// If Duplicate origin and destination codes exist
					if (mailContractDetailsMap.containsKey(stationKey)) {
						if (mailContractVo.getValidFromDate() != null
								&& mailContractVo.getValidToDate() != null) {
							// Overlapping valid from date and valid to date -
							// check
							if ((mailContractVo.getValidFromDate()
									.isGreaterThan(
											duplicateMailContractVo
											.getValidFromDate()) || mailContractVo
											.getValidFromDate().equals(
													duplicateMailContractVo
													.getValidFromDate()))
													&& (mailContractVo.getValidFromDate()
															.isLesserThan(
																	duplicateMailContractVo
																	.getValidToDate()) || mailContractVo
																	.getValidFromDate().equals(
																			duplicateMailContractVo
																			.getValidToDate()))
																			|| (mailContractVo
																					.getValidToDate()
																					.isGreaterThan(
																							duplicateMailContractVo
																							.getValidFromDate()) || mailContractVo
																							.getValidToDate()
																							.equals(
																									duplicateMailContractVo
																									.getValidFromDate()))
																									&& (mailContractVo.getValidToDate()
																											.isLesserThan(
																													duplicateMailContractVo
																													.getValidToDate()) || mailContractVo
																													.getValidToDate().equals(
																															duplicateMailContractVo
																															.getValidToDate()))) {
								mailContractDetailVos
								.add(mailContractDetailsVo);
								mailContractVoToReturn
								.setContractReferenceNumber(duplicateMailContractVo
										.getContractReferenceNumber());
							}
						}
					}
				}
			}
		}
		mailContractVoToReturn.setMailContractDetailsVos(mailContractDetailVos);
		log.exiting(CLASS_NAME, "checkDuplicateParameters");
		return mailContractVoToReturn;
	}

	/**
	 * Added by A-1946 for findMailContracts.
	 *
	 * @param mailContractFilterVO the mail contract filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO) throws SystemException {

		log.exiting(CLASS_NAME, "displaySLADetails");
		return MailContractMaster.findMailContracts(mailContractFilterVO);
	}

	/**
	 * This method validates billing matrix codes.
	 *
	 * @param companyCode the company code
	 * @param billingMatrixCodes the billing matrix codes
	 * @throws SystemException the system exception
	 * @throws InvalidBillingMatrixCodeException the invalid billing matrix code exception
	 * @author A-2518
	 */
	public void validateBillingMatrixCodes(String companyCode,
			Collection<String> billingMatrixCodes) throws SystemException,
			InvalidBillingMatrixCodeException {
		log.entering(CLASS_NAME, "validateBillingMatrixCodes");
		Collection<String> billingMatrixCodesCollection = BillingMatrix
		.validateBillingMatrixCodes(companyCode, billingMatrixCodes);
		Collection<String> billingMatrixCodesToRemove = new ArrayList<String>();
		for (String billingMatrixCode : billingMatrixCodesCollection) {
			billingMatrixCodesToRemove.add(billingMatrixCode);
		}
		billingMatrixCodes.removeAll(billingMatrixCodesToRemove);
		if (billingMatrixCodes != null && billingMatrixCodes.size() > 0) {
			String[] errorData = new String[billingMatrixCodes.size()];
			int index = 0;
			for (String billingMatrixCode : billingMatrixCodes) {
				errorData[index++] = billingMatrixCode;
			}
			throw new InvalidBillingMatrixCodeException(
					InvalidBillingMatrixCodeException.INVALID_BILLING_MATRIX_CODE,
					errorData);
		}
		log.exiting(CLASS_NAME, "validateBillingMatrixCodes");
	}

	/**
	 * This method validates Service Level Activity(SLA) codes.
	 *
	 * @param companyCode the company code
	 * @param slaCodes the sla codes
	 * @throws SystemException the system exception
	 * @throws InvalidSLACodeException the invalid sla code exception
	 * @author A-2518
	 */
	public void validateSLACodes(String companyCode, Collection<String> slaCodes)
	throws SystemException, InvalidSLACodeException {
		log.entering(CLASS_NAME, "validateSLACodes");
		Collection<String> slaCodesCollection = MailSLAMaster.validateSLACodes(
				companyCode, slaCodes);
		Collection<String> slaCodesToRemove = new ArrayList<String>();
		for (String slaCode : slaCodesCollection) {
			slaCodesToRemove.add(slaCode);
		}
		slaCodes.removeAll(slaCodesToRemove);
		if (slaCodes != null && slaCodes.size() > 0) {
			String[] errorData = new String[slaCodes.size()];
			int index = 0;
			for (String billingMatrixCode : slaCodes) {
				errorData[index++] = billingMatrixCode;
			}
			throw new InvalidSLACodeException(
					InvalidSLACodeException.INVALID_SLA_CODE, errorData);
		}
		log.exiting(CLASS_NAME, "validateSLACodes");
	}

	/**
	 * Find despatch lov.
	 *
	 * @param companyCode the company code
	 * @param dsn the dsn
	 * @param despatch the despatch
	 * @param gpaCode the gpa code
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode, String dsn,
			String despatch, String gpaCode, int pageNumber)
			throws SystemException {
		log.entering(CLASS_NAME, "findDespatchLov");
		return MRAProrationDetails.findDespatchLov(companyCode, dsn, despatch,
				gpaCode, pageNumber);

	}

	/**
	 * Validate reporting period.
	 *
	 * @param reportingPeriodFilterVo the reporting period filter vo
	 * @return the boolean
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public Boolean validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
	throws SystemException {
		log.entering(CLASS_NAME, "validateReportingPeriod");
		boolean isValid = false;
		Collection<ReportingPeriodVO> reportingPeriodVos = findReportingPeriod(reportingPeriodFilterVo);
		/*for (ReportingPeriodVO reportingPeriodVo : reportingPeriodVos) {
			if (reportingPeriodFilterVo.getFromDate().equals(
					reportingPeriodVo.getFromDate())
					&& reportingPeriodFilterVo.getToDate().equals(
							reportingPeriodVo.getToDate())) {*/
		if(reportingPeriodVos.size()!=0){
			log.log(Log.INFO, "reportingPeriodVos", reportingPeriodVos);
			isValid = true;

		}else{

			log.log(log.FINE, "size ==0");

		}
		log.exiting(CLASS_NAME, "validateReportingPeriod");
		return isValid;
	}

	/**
	 * Find reporting period.
	 *
	 * @param reportingPeriodFilterVo the reporting period filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	private Collection<ReportingPeriodVO> findReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "validateReportingPeriod");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "validateReportingPeriod");
			return mraDefaultsDao
			.validateReportingPeriod(reportingPeriodFilterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Import flown mails.
	 *
	 * @param flightValidationVO the flight validation vo
	 * @param flownMailSegmentVOs the flown mail segment v os
	 * @throws SystemException the system exception
	 */
	public void importFlownMails(FlightValidationVO flightValidationVO,
			Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO,String txnlogInfo)
	throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "importFlownMails");
		MRADefaultsDAO mraDefaultsDao = null;
		String processStatus=null;
		StringBuilder remarks = null;
		String txnCod = null;
		int serNum =0;
		String[ ]  txnInfo =null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "importFlownMails");
			
			if (documentBillingVO != null && documentBillingVO.getBillingBasis() != null && !documentBillingVO.getBillingBasis().isEmpty()) {
				documentBillingVO
						.setMailSequenceNumber(Proxy.getInstance().get(MailTrackingDefaultsProxy.class).findMailBagSequenceNumberFromMailIdr(
								documentBillingVO.getBillingBasis(), documentBillingVO.getCompanyCode()));
			}
			
			 mraDefaultsDao.importFlownMails(flightValidationVO,
					flownMailSegmentVOs,documentBillingVO); 
			
			
			if(documentBillingVO!=null &&MRAConstantsVO.TRGPNT_PROCESS_MANAGER.equals(documentBillingVO.getTriggerPoint())){
			
				if(txnlogInfo!=null && !txnlogInfo.isEmpty()){
					txnInfo = txnlogInfo.split("-");
					txnCod = txnInfo[0];
					serNum = Integer.parseInt(txnInfo[1]);
				}
					processStatus =COMPLETED;
					remarks=new StringBuilder("Process completed");
					InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
					LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
					invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.IMPORT_DATA_TO_MRA);
					invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
					invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());	
					invoiceTransactionLogVO.setRemarks(remarks.toString());	
					invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
					invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
				    invoiceTransactionLogVO.setSerialNumber(serNum);
				    invoiceTransactionLogVO.setTransactionCode(txnCod);	
				
				    Proxy.getInstance().get(CRADefaultsProxy.class).updateTransactionandRemarks(invoiceTransactionLogVO);
				
			
			}

		} catch (PersistenceException  | ProxyException exception) {
			throw new SystemException(exception.getMessage());
		}
		if(isTaxRequired())
			{
			updateTax(flightValidationVO);
			}

	}

	/**
	 * Update tax.
	 *
	 * @param flightValidationVO the flight validation vo
	 * @throws SystemException the system exception
	 */
	private void updateTax(FlightValidationVO flightValidationVO) throws SystemException{
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<MRABillingDetailsVO> mraBillingDetails = null;
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "updateTax");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "updateTax");
			mraBillingDetails = mraDefaultsDao.findBillingEntriesForFlight(flightValidationVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		String taxValues= taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		
		if(mraBillingDetails!=null && !mraBillingDetails.isEmpty()){
			if(null!=taxValues )
			{
				double tax=Double.parseDouble(taxValues);
			taxMraIntUpdate(mraBillingDetails, tax);
			}
			else
			{
			HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails
			= findTaxDetails(mraBillingDetails);
			log.log(Log.FINE, "Tax details obtained",taxDetails);
			for(MRABillingDetailsVO billingDetailsVO :mraBillingDetails ){
				/*
				 * update billing details vo with taxvo. Use key from the taxDetails and billing basis +payment flag from billingDetailsVO
				 *
				 */

				/*
				 * Find the tax corresponding to the despatch. Check with Raiz whether tax needs to be calculated for
				 * each billing basis.
				 * iterate tax details and find and update MRABillingDetailsEntity with tax
				 */
				updateBillingEntryWithTax(billingDetailsVO,taxDetails);

			}
		}
		}
	}
	public void taxMraIntUpdate(Collection<MRABillingDetailsVO> mraBillingDetails, double tax)
			throws SystemException {
		String taxCountry= taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
		for(MRABillingDetailsVO billingDetailsVO :mraBillingDetails )
		{
			if(null!=taxCountry && taxCountry.equals(billingDetailsVO.getOrgCountryCode()))
			{
				billingDetailsVO.setTaxPercentage(tax);
				try {
					updateBillingDetail(billingDetailsVO);
				} catch (FinderException e1) {
					log.log(Log.INFO,e1 );
				}
				MailDetailsTemp mailDetailsTemp=null;
				try {
					 mailDetailsTemp=MailDetailsTemp.find(billingDetailsVO);
					 mailDetailsTemp.setTaxUpdationFlag("Y");
				} catch (FinderException e) {
					log.log(Log.INFO,e );
				}
			}
		}
	}

	/**
	 * Find tax details.
	 *
	 * @param billingDetails the billing details
	 * @return the hash map
	 * @throws SystemException the system exception
	 */
	private HashMap<String ,HashMap<String ,Collection<TaxVO>>>
	findTaxDetails(Collection<MRABillingDetailsVO> billingDetails)throws SystemException{
		HashMap<String,TaxFilterVO> taxFilterMap = constructTaxFilterMap(billingDetails);
		HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails = null;
		// taxDetails - String Will be Key(billing basis), Secodn String will be type.. ie TAX,TDS, then Collection<TaxVO>

		try {
			taxDetails =Proxy.getInstance().get(TariffTaxProxy.class).computeTax(taxFilterMap);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}

		return taxDetails;

	}

	/**
	 * Construct tax filter map.
	 *
	 * @param billingDetails the billing details
	 * @return the hash map
	 * @throws SystemException the system exception
	 */
	private HashMap<String,TaxFilterVO> constructTaxFilterMap(Collection<MRABillingDetailsVO> billingDetails)
	throws SystemException{
		HashMap<String,TaxFilterVO> taxFilterMap = new HashMap<String, TaxFilterVO>();
		TaxFilterVO taxFilterVO = null;
		HashMap<String , Collection<String> > taxConfigurationDetails = null;
		HashMap<String ,Collection<String> >  parameterMap =  null;
		HashMap<String , ChargeDetailsVO> chargedetailsMap = null;
		ChargeDetailsVO chargeDetailsVO = null;
		Collection<String> configurationCodeWtCharge = null;
		Collection<String> configurationCodeValchg = null;
		Collection<String> configurationCodeSrvTax = null;
		Collection<String> configurationCodesTDS = null;
		Collection<String> configurationCodeSurCharge = null;//Added by a-7871 for ICRD-154005
		Collection<String> agentPatameters = null;
		Money weightCharge = null;
		Money valCharge = null;
		Money surCharge = null;//Added by a-7871 for ICRD-154005

		//Collection<String> stationPatameters = new ArrayList<String>();

		for(MRABillingDetailsVO billingDeatilsVO: billingDetails){
			taxFilterVO = new TaxFilterVO();
			/**
			 * TODO to check . Whether this is billing currency code itself
			 */
			//#1
			taxFilterVO.setCompanyCode(billingDeatilsVO.getCompanyCode());
			taxFilterVO.setCountryCode(billingDeatilsVO.getGpaCountryCode());
			taxFilterVO.setOrigin(billingDeatilsVO.getMailbagOrigin());
			taxFilterVO.setDestination(billingDeatilsVO.getMailbagDestination());
			if(billingDeatilsVO.getConsignmentDate()!=null){
			taxFilterVO.setDateOfJourney(billingDeatilsVO.getConsignmentDate());
			}else{
				taxFilterVO.setDateOfJourney(billingDeatilsVO.getRecieveDate());
			}
			taxFilterVO.setCurrencyCode(billingDeatilsVO.getContractCurrCode());
			taxFilterVO.setCargoType(TaxFilterVO.CARGOTYPE_MAIL);
			if("R".equals(billingDeatilsVO.getPaymentFlag())){
				if(billingDeatilsVO.getNetAmount()!=null && billingDeatilsVO.getNetAmount().getAmount()==0){
					taxFilterVO.setExemptionBalanceToBeUpdated(true);
				}
			}
			else{
				taxFilterVO.setExemptionBalanceToBeUpdated(false);
			}
			//#2
			taxConfigurationDetails=new HashMap<String, Collection<String>>();


			configurationCodeWtCharge=new ArrayList<String>();
			configurationCodeWtCharge.add(TaxFilterVO.CONFIGURATIONCODE_STWTCHG);
			taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TAX, configurationCodeWtCharge);

			//5219
			configurationCodeValchg=new ArrayList<String>();
			configurationCodeValchg.add(TaxFilterVO.CONFIGURATIONCODE_STVALCHG);
			taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TAX, configurationCodeValchg);

			configurationCodeSurCharge=new ArrayList<String>(); //Added by a-7871 for ICRD-154005
			configurationCodeSurCharge.add(TaxFilterVO.CONFIGURATIONCODE_STSURCHG);
			taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TAX, configurationCodeSurCharge);
			configurationCodeSrvTax =  new ArrayList<String>();
			configurationCodeSrvTax.add(TaxFilterVO.CONFIGURATIONCODE_ST);
			taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TAX, configurationCodeSrvTax);

			configurationCodesTDS=new ArrayList<String>();
			configurationCodesTDS.add(TaxFilterVO.CONFIGURATIONCODE_TDSDUEGPA);
			taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TDS, configurationCodesTDS);
			/*
			 * TODO check if this is only  TDS DUE GPA only
			 */
			//configurationCodesTDS.add(TaxFilterVO.CONFIGURATIONCODE_TDSDUECAR);

			//tds
			taxFilterVO.setTaxConfigurationDetails(taxConfigurationDetails);
			//Added by A 4823 for including billing sector configuration of tax
			/*taxFilterVO.setBilledFrom(billingDeatilsVO.getSegFrom());
			taxFilterVO.setBilledTo(billingDeatilsVO.getSegTo());*/
			//#3 to check with Shibin
			agentPatameters=new ArrayList<String>();
			agentPatameters.add(billingDeatilsVO.getUpdBillTo());
			parameterMap=new HashMap<String, Collection<String>>();
			parameterMap.put(TaxFilterVO.PARAMETERCODE_GPA, agentPatameters);
			//stationPatameters.add();
			//parameterMap.put(TaxFilterVO.PARAMETERCODE_STNCOD, stationPatameters);
			taxFilterVO.setParameterMap(parameterMap);

			//#4
			//Creating ChargeDetailsVO for each component
			try {
				weightCharge = CurrencyHelper.getMoney(billingDeatilsVO.getContractCurrCode());
				valCharge = CurrencyHelper.getMoney(billingDeatilsVO.getContractCurrCode());
				surCharge= CurrencyHelper.getMoney(billingDeatilsVO.getContractCurrCode()); //Added by a-7871 for ICRD-154005
			} catch (CurrencyException e) {
				log.log(Log.FINE,  "CurrencyException");
			}
			weightCharge.plusEquals(billingDeatilsVO.getWgtCharge());
			valCharge.plusEquals(billingDeatilsVO.getValCharges());//5219
			surCharge.plusEquals(billingDeatilsVO.getSurCharge());//Added by a-7871 for ICRD-154005
			chargeDetailsVO =  new ChargeDetailsVO();
			chargedetailsMap=new HashMap<String, ChargeDetailsVO>();
			chargeDetailsVO.setBasis(MRAConstantsVO.BASISCODE_WTCHG);
			chargeDetailsVO.setBasisTotalAmount(weightCharge);
			chargedetailsMap.put(MRAConstantsVO.BASISCODE_WTCHG,chargeDetailsVO);
			//5219
			ChargeDetailsVO chargeDetailsVO1 =  new ChargeDetailsVO();
			chargeDetailsVO1.setBasis(MRAConstantsVO.BASISCODE_VALCHG);
			chargeDetailsVO1.setBasisTotalAmount(valCharge);
			chargedetailsMap.put(MRAConstantsVO.BASISCODE_VALCHG,chargeDetailsVO1);
			ChargeDetailsVO chargeDetailsVO2 =  new ChargeDetailsVO();//Added by a-7871 for ICRD-154005
			chargeDetailsVO2.setBasis(MRAConstantsVO.BASISCODE_SURCHG);
			chargeDetailsVO2.setBasisTotalAmount(surCharge);
			chargedetailsMap.put(MRAConstantsVO.BASISCODE_SURCHG,chargeDetailsVO2);
			taxFilterVO.setChargedetailsMap(chargedetailsMap);
			//modified key for bug ICRD-19263
			taxFilterMap.put(billingDeatilsVO.getBillingBasis(), taxFilterVO);

		}


		return taxFilterMap;

	}

	/**
	 * Update billing entry with tax.
	 *
	 * @param billingDetailsVO the billing details vo
	 * @param taxDetailsMap the tax details map
	 */
	private void updateBillingEntryWithTax(
			MRABillingDetailsVO billingDetailsVO,
			HashMap<String, HashMap<String, Collection<TaxVO>>> taxDetailsMap) {
		HashMap<String ,Collection<TaxVO>>  taxDetails = null;
		Collection<TaxVO> taxVOs = null;
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "updateBillingEntryWithTax");
		double taxPercentage=0.0;
		double tdsPercentage=0.0;
		if(taxDetailsMap!=null && taxDetailsMap.size() > 0){
			for(String key : taxDetailsMap.keySet() ){
				if(key != null){
				if(key.equals(billingDetailsVO.getBillingBasis())){
					taxDetails = taxDetailsMap.get(key);
					for(String configType : taxDetails.keySet()){
						if(TaxFilterVO.CONFIGURATIONTYPE_TAX.equals(configType)){
							taxVOs = taxDetails.get(configType);
							if(taxVOs!=null){
								for(TaxVO taxVO : taxVOs){
									if(TaxFilterVO.CONFIGURATIONCODE_STWTCHG.equals(taxVO.getConfigurationCode()) ){
										billingDetailsVO.setServiceTax(
												taxVO.getTotalTaxAmount().getRoundedAmount());
										if(taxVO.getTaxConfigurationVO().getTaxDetailsVo().getPercentageValue()!=null){
											taxPercentage = Double.parseDouble(taxVO.getTaxConfigurationVO().
													getTaxDetailsVo().getPercentageValue());

											billingDetailsVO.setTaxPercentage(taxPercentage);
										}

									}
									if(TaxFilterVO.CONFIGURATIONCODE_STSURCHG.equals(taxVO.getConfigurationCode()) ){ //Added by a-7871 for ICRD-154005
										billingDetailsVO.setServiceTax(taxVO.getTotalTaxAmount().getRoundedAmount());
									}
									if(TaxFilterVO.CONFIGURATIONCODE_STVALCHG.equals(taxVO.getConfigurationCode()) ){
										billingDetailsVO.setServiceTax(
												taxVO.getTotalTaxAmount().getRoundedAmount());

									}
									if(TaxFilterVO.CONFIGURATIONCODE_ST.equals(taxVO.getConfigurationCode()) ){
										billingDetailsVO.setServiceTax(
												taxVO.getTotalTaxAmount().getRoundedAmount());

									}
								}
							}
						}else if(TaxFilterVO.CONFIGURATIONTYPE_TDS.equals(configType)){
							taxVOs = taxDetails.get(configType);
							if(taxVOs!=null){
								for(TaxVO taxVO : taxVOs){
									if(TaxFilterVO.CONFIGURATIONCODE_TDSDUEGPA.equals(taxVO.getConfigurationCode()) ){
										billingDetailsVO.setTds(taxVO.getTotalTaxAmount().getRoundedAmount());
										if(taxVO.getTaxConfigurationVO().getTaxDetailsVo().getPercentageValue()!=null){
											tdsPercentage = Double.parseDouble(taxVO.getTaxConfigurationVO().
													getTaxDetailsVo().getPercentageValue());
											billingDetailsVO.setTdsPercentage(tdsPercentage);
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

		log.log(Log.FINE, "billing details VO",billingDetailsVO);
		updateBillingDetailEntity(billingDetailsVO);
	}

	/**
	 * Update billing detail entity.
	 *
	 * @param billingDetailsVO the billing details vo
	 */
	private void updateBillingDetailEntity(MRABillingDetailsVO billingDetailsVO) {
		double netAmount = 0.0;
		Money netAmnt = null;
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "updateBillingDetailEntity");
		try {
			MRAGPABillingDetails mraGPABillingDetails = MRAGPABillingDetails.
			find(billingDetailsVO.getCompanyCode(),billingDetailsVO.getMailSequenceNumber(),billingDetailsVO.getSequenceNumber());
			log.log(Log.FINE, "Billing details find",mraGPABillingDetails);
			String CURRENCY_CODE = mraGPABillingDetails.getContractCurrencyCode();
			//If rate is Inclusive of service tax
				log.log(Log.FINE, "tax included in rate flag");
				double actualRate=0.0;
				double actualTds=0.0;
				double actualServiceTax=0.0;
				double rateInclOfSTax=mraGPABillingDetails.getAppliedRate();
				double weightMailChgIncl=mraGPABillingDetails.getWieghtChargeInContractCurrency();
				double weightSurChgIncl=mraGPABillingDetails.getOtherChargeInContractCurrency();
				double actualMailWtChg=0.0;
				double actualSurWtChg=0.0;
				//Added by A-4809 for BUG ICRD-18417
				//Money rate = null;
				Money tds = null;
				Money serviceTax = null;
				Money inclOfSTax = null;
				Money chgMailIncl = null;
				Money chgSurIncl = null;
				Money wtChg = null;
				Money wtChgAndSurChg=null;
				Money surChg=null;
				try{
					//Modified by A-4809 for BUG ICRD-18417 ..Starts
					//Values should be in money rounded to 2 decimal

					inclOfSTax =CurrencyHelper.getMoney(CURRENCY_CODE);
					inclOfSTax.setAmount(rateInclOfSTax);
					chgMailIncl =CurrencyHelper.getMoney(CURRENCY_CODE);
					chgMailIncl.setAmount(weightMailChgIncl);

					chgSurIncl =CurrencyHelper.getMoney(CURRENCY_CODE);
					chgSurIncl.setAmount(weightSurChgIncl);

					netAmnt = CurrencyHelper.getMoney(CURRENCY_CODE);
					netAmnt.setAmount(netAmount);

					//rate without tax component

					actualRate=rateInclOfSTax/(100+billingDetailsVO.getTaxPercentage())*100;
					//rate =CurrencyHelper.getMoney(CURRENCY_CODE);
					//rate.setAmount(actualRate);

					actualMailWtChg=chgMailIncl.getRoundedAmount()/(100+billingDetailsVO.getTaxPercentage())*100;
					wtChg = CurrencyHelper.getMoney(CURRENCY_CODE);
					wtChg.setAmount(actualMailWtChg);

					actualSurWtChg=chgSurIncl.getRoundedAmount()/(100+billingDetailsVO.getTaxPercentage())*100;
					surChg= CurrencyHelper.getMoney(CURRENCY_CODE);
					surChg.setAmount(actualSurWtChg);

					wtChgAndSurChg=CurrencyHelper.getMoney(CURRENCY_CODE);
					wtChgAndSurChg.setAmount(wtChg.getAmount()+surChg.getAmount());


					if(wtChgAndSurChg!=null){
						actualTds=(wtChgAndSurChg.getRoundedAmount()+
								(wtChgAndSurChg.getRoundedAmount()*billingDetailsVO.getTaxPercentage()/100))*billingDetailsVO.getTdsPercentage()/100;
					}

					tds =CurrencyHelper.getMoney(CURRENCY_CODE);
					tds.setAmount(actualTds);
					if(wtChgAndSurChg!=null){
						actualServiceTax=billingDetailsVO.getServiceTax();
					}

					serviceTax = CurrencyHelper.getMoney(CURRENCY_CODE);
					serviceTax.setAmount(actualServiceTax);

					if(wtChgAndSurChg!=null && serviceTax!=null && tds!=null ){
						netAmount=(wtChgAndSurChg.getRoundedAmount())+ serviceTax.getRoundedAmount()-tds.getRoundedAmount();
					}

					log.log(Log.FINE, "service tax calculated",serviceTax.getRoundedAmount());
					log.log(Log.FINE, "tds tax calculated",tds.getRoundedAmount());
					//setting money values to vo
					billingDetailsVO.setAplRate(getScaledValue(actualRate, 4));
					billingDetailsVO.setTds(tds.getRoundedAmount());
					billingDetailsVO.setServiceTax(serviceTax.getRoundedAmount());
					mraGPABillingDetails.setUpdatedWeightChargeInBaseCurrency(wtChg.getRoundedAmount());
					mraGPABillingDetails.setUpdatedSurChargeInBaseCurrency(surChg.getRoundedAmount());
					mraGPABillingDetails.setAppliedRate(getScaledValue(actualRate, 4));
					
				}catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
					e.getErrorCode();
				}
				//Modified by A-4809 for BUG ICRD-18417 ..Ends
			

			////mraBillingDetails.setUpdWgtCharge(billingDetailsVO.getAplRate()*mraBillingDetails.getUpdWeight());
			mraGPABillingDetails.setServiceTax(billingDetailsVO.getServiceTax());
			mraGPABillingDetails.setTaxDeductedAtSource(billingDetailsVO.getTds());
			
			//Added by A-4809 for BUG ICRD-18417 ...Starts
			log.log(Log.FINE, "MRA billing details after service tax and tds",mraGPABillingDetails);
			try{
				netAmnt = CurrencyHelper.getMoney(CURRENCY_CODE);
				netAmnt.setAmount(netAmount);
				mraGPABillingDetails.setNetAmount(netAmnt.getRoundedAmount());
			}catch(CurrencyException e){
				log.log(Log.SEVERE, "#####Currency Exception:###");
				e.getErrorCode();
			}
			log.log(Log.FINE, "MRA billing details updating tax",mraGPABillingDetails);
			//Modified by A-4809 for BUG ICRD-18417 ...Ends
		} catch (FinderException e) {
			//ignore;
		} catch (SystemException e) {
			//ignore
		}
	}

	/**
	 * Activate rate lines.
	 *
	 * @param rateLineVOs the rate line v os
	 * @throws SystemException the system exception
	 * @throws RateLineException the rate line exception
	 * @throws FinderException 
	 * @throws NumberFormatException 
	 */
	@Advice(name = "mail.mra.saveRateLineDetails" , phase=Phase.POST_INVOKE) 
	public void activateRateLines(Collection<RateLineVO> rateLineVOs , boolean isBulkActivation)
	throws SystemException, RateLineException, NumberFormatException, FinderException {	log.entering(CLASS_NAME, "activateRateLines");
		//Added for ICRD-143975 starts
		LocalDate date = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		
			Collection<RateLineErrorVO> errors = new ArrayList<RateLineErrorVO>();
			RateLineErrorVO errorVO= new RateLineErrorVO(); 
	
	
	if(isBulkActivation)//procedure call for bulk activation
	{
		
		
		String ratecardID;
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			ratecardID=	mraDefaultsDao.activateBulkRatelines(rateLineVOs.iterator().next());
			
			if(!ratecardID.equals("OK"))
			{
			MRARateLine rateLine=MRARateLine.find(rateLineVOs.iterator().next().getCompanyCode(), ratecardID.split("-")[0], Integer.parseInt(ratecardID.split("-")[1]));
			RateLineErrorVO rateLineErrorvo = new RateLineErrorVO();
			Collection<RateLineErrorVO> rateLineErrorvos = new ArrayList<RateLineErrorVO>();
			rateLineErrorvo.setOrigin(rateLine.getOrigin());
			rateLineErrorvo.setDestination(rateLine
					.getDestination());
			rateLineErrorvo.setCurrentRateCardID(ratecardID.split("-")[0]);
			rateLineErrorvo.setCurrentValidityStartDate(rateLineVOs.iterator().next().getValidityStartDate());
			rateLineErrorvo.setCurrentValidityEndDate(rateLineVOs.iterator().next().getValidityEndDate());
			//rateLineErrorvo.setNewRateCardID(ratecardID.split("-")[0]);
			//rateLineErrorvo.setNewValidityStartDate(fromDate);
			//rateLineErrorvo.setNewValidityEndDate(toDate);
			//rateLineVoInMap.setErrorVO(rateLineErrorvo);
			// setting
			rateLineErrorvos.add(rateLineErrorvo);//failure  case
			rateLineVOs.iterator().next().setErrorVO(rateLineErrorvos.iterator().next());
				InvoiceTransactionLogVO invoiceTransactionLogVO= new InvoiceTransactionLogVO();//added by a-7871
				invoiceTransactionLogVO.setTransactionCode(rateLineVOs.iterator().next().getTransactionCode());
				invoiceTransactionLogVO.setSerialNumber(rateLineVOs.iterator().next().getTxnLogSerialNum());
				invoiceTransactionLogVO.setCompanyCode(rateLineVOs.iterator().next().getCompanyCode());
				invoiceTransactionLogVO.setInvoiceType(UPU);
				invoiceTransactionLogVO.setInvoiceGenerationStatus(F);
				
				for(RateLineVO rateLineVo : rateLineVOs){
					if(rateLineVo.getErrorVO() != null){
						errorVO = rateLineVo.getErrorVO();
						invoiceTransactionLogVO.setRemarks(REMARK_FAILED+rateLineVo.getRateCardID()+ORIGIN+rateLine.getOrigin()+DESTINATION+rateLine.getDestination()+VALIDITY+rateLineVo.getValidityStartDate().toDisplayDateOnlyFormat()+TO+rateLineVo.getValidityEndDate().toDisplayDateOnlyFormat());
						break;
					}
				}
				errors.add(errorVO);
				
	     
 		   try {
				new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				log			.log(Log.INFO, "ratelinestatus ", e.getMessage());
				 
			}
 		   
			
		/*	throw new RateLineException(
					RateLineException.RATELINE_EXIST, rateLineErrorvos
					.toArray());*/
			
			}	
			else
		{
		ArrayList<RateLineVO> vos = new ArrayList<RateLineVO>(rateLineVOs);


			InvoiceTransactionLogVO invoiceTransactionLogVO= new InvoiceTransactionLogVO();//added by a-7871
			boolean firstTxn = false;
			for (RateLineVO rateLineVo : vos) {
				if(rateLineVo.getTransactionCode()!=null && rateLineVo.getTxnLogSerialNum()!=0)	{
					if(!firstTxn){
					   invoiceTransactionLogVO.setTransactionCode(rateLineVo.getTransactionCode());
					   invoiceTransactionLogVO.setSerialNumber(rateLineVo.getTxnLogSerialNum());
					   invoiceTransactionLogVO.setCompanyCode(rateLineVo.getCompanyCode());
					   invoiceTransactionLogVO.setInvoiceType(UPU);
					   invoiceTransactionLogVO.setInvoiceGenerationStatus(C);
					   invoiceTransactionLogVO.setRemarks(REMARK_SUCCESS+rateLineVo.getRateCardID());
					   try {
							new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
						} catch (ProxyException e) {
							// TODO Auto-generated catch block
							log
							.log(Log.INFO, "ratelinestatus ", e.getMessage());
						}
					   firstTxn = true;
					}
				}
				}
				
				
			}
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		
		
		
	}
	
	else
	{
	try{
		//Added for ICRD-143975 ends
		for (RateLineVO rateLineVo : rateLineVOs) {
			rateLineVo.setRatelineStatus(ACTIVATE);
			rateLineVo.setLastUpdateTime(date);//Added for ICRD-143975
			rateLineVo.setLastUpdateUser(logonAttributes.getUserId());//Added for ICRD-143975
		}
		saveRatelineStatus(rateLineVOs);
	}catch(RateLineException exception){
		log			.log(Log.INFO, "ratelinestatus ", exception.getMessage());
		/*Collection<RateLineErrorVO> errors = new ArrayList<RateLineErrorVO>();
		RateLineErrorVO errorVO= new RateLineErrorVO(); */
		//failure scenerio
	
			
			for(RateLineVO rateLineVo : rateLineVOs){
				if(rateLineVo.getErrorVO() != null){
					errorVO = rateLineVo.getErrorVO();
					errors.add(errorVO);
					break;
				}
			}
			throw new RateLineException(
					RateLineException.RATELINE_EXIST, errors.toArray());
		
	}
	}
	/* for testing */
	/*for (RateLineVO rateLineVo : vos) {//commented for performance enhancement
			log
					.log(Log.INFO, "ratelinestatus ", rateLineVo.getRatelineStatus());
	}*/
		/** *************** */
		updateRateCardStatus(rateLineVOs);
	//Success scenerio
	
			
		log.exiting(CLASS_NAME, "activateRateLines");
	}

	/**
	 * In activate rate lines.
	 *
	 * @param rateLineVOs the rate line v os
	 * @throws SystemException the system exception
	 */
	@Advice(name = "mail.mra.updateRateLineDetails" , phase=Phase.POST_INVOKE) 
	public void inActivateRateLines(Collection<RateLineVO> rateLineVOs)
	throws SystemException,MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "inActivateRateLines");
		//Added for ICRD-143975 starts
		LocalDate date = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		//Added for ICRD-143975 ends
		for (RateLineVO rateLineVO : rateLineVOs) {
			rateLineVO.setRatelineStatus(INACTIVATE);
			rateLineVO.setLastUpdateTime(date);//Added for ICRD-143975
			rateLineVO.setLastUpdateUser(logonAttributes.getUserId());//Added for ICRD-143975
			updateRateLine(rateLineVO);
		}
		updateRateCardStatus(rateLineVOs);
		log.exiting(CLASS_NAME, "inActivateRateLines");
	}

	/**
	 * Cancel rate lines.
	 *
	 * @param rateLineVOs the rate line v os
	 * @throws SystemException the system exception
	 */
	@Advice(name = "mail.mra.updateRateLineDetails" , phase=Phase.POST_INVOKE) 
	public void cancelRateLines(Collection<RateLineVO> rateLineVOs)
	throws SystemException {
		log.entering(CLASS_NAME, "cancelRateLines");
		//Added for ICRD-143975 starts
		LocalDate date = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		//Added for ICRD-143975 starts
		Collection<RateLineVO> lineVOs = new ArrayList<RateLineVO>();
		for (RateLineVO rateLineVO : rateLineVOs) {
			log.log(Log.INFO, "before cancelling ", rateLineVO);
			rateLineVO.setRatelineStatus(CANCEL);
			rateLineVO.setLastUpdateTime(date);//Added for ICRD-143975
			rateLineVO.setLastUpdateUser(logonAttributes.getUserId());//Added for ICRD-143975
			updateRateLine(rateLineVO);
			log.log(Log.INFO, "after update rateline ", rateLineVO);
		}
		for (RateLineVO rateLineVO : rateLineVOs) {
			log.log(Log.INFO, "after complete setting ", rateLineVO);
			lineVOs.add(rateLineVO);
		}
		updateRateCardStatus(lineVOs);
		log.exiting(CLASS_NAME, "cancelRateLines");
	}

	/**
	 * Update rate line.
	 *
	 * @param rateLineVO the rate line vo
	 * @throws SystemException the system exception
	 */
	private void updateRateLine(RateLineVO rateLineVO) throws SystemException {
		log.entering(CLASS_NAME, "updateRateLine");
		MRARateLine rateLine = null;
		try {
			rateLine = MRARateLine.find(rateLineVO.getCompanyCode(), rateLineVO
					.getRateCardID(), rateLineVO.getRatelineSequenceNumber());
			if (rateLine != null) {
				rateLine.update(rateLineVO);
				log.log(Log.WARNING, "Rateline Entity updated", rateLineVO);
			} else {
				log.log(Log.WARNING, "Rateline Entity not found");
			}
		} catch (FinderException finderException) {
			log.log(Log.FINE,  "FinderException");
		}
		log.exiting(CLASS_NAME, "updateRateLine");
	}

	/**
	 * Update rate card status.
	 *
	 * @param rateLineVOs the rate line v os
	 * @throws SystemException the system exception
	 */
	private void updateRateCardStatus(Collection<RateLineVO> rateLineVOs)
	throws SystemException {
		log.entering(CLASS_NAME, "updateRateCardStatus");
		Map<String, String> rateCardIdMap = new HashMap<String, String>();
		int cancelled = 0;
		boolean isActive = false;
		boolean isNew = false;
		boolean isInactive = false;
		LocalDate currentTime = new LocalDate
		(LocalDate.NO_STATION,Location.NONE,true);
		try {
			for (RateLineVO rateLineVO : rateLineVOs) {
				if (!rateCardIdMap.containsKey(rateLineVO.getRateCardID())) {
					RateLineFilterVO rateLineFilterVO = new RateLineFilterVO();
					rateLineFilterVO
					.setCompanyCode(rateLineVO.getCompanyCode());
					rateLineFilterVO.setRateCardID(rateLineVO.getRateCardID());
					Collection<RateLineVO> rateLines = RateCard
					.findAllRateLines(rateLineFilterVO);
					RateCard ratecard = RateCard.find(rateLineVO
							.getCompanyCode(), rateLineVO.getRateCardID());
					log.log(Log.INFO, "after finding rate card");
					if (ratecard != null) {
						log.log(Log.INFO, "ratecard not equal to null");
						ratecard.setLastUpdatedUser(rateLineVO
								.getLastUpdateUser());
						ratecard.setLastUpdateTime(rateLineVO
								.getLastUpdateTime());
						if (rateLines != null && rateLines.size() > 0) {
							log.log(Log.INFO, "ratelines not equal to null");
							for (RateLineVO rateLinevo : rateLines) {
								log.log(Log.INFO, "ratelinestatus ", rateLinevo.getRatelineStatus());
								if (ACTIVATE.equals(rateLinevo
										.getRatelineStatus())) {
									isActive = true;
								} else if (CANCEL.equals(rateLinevo
										.getRatelineStatus())) {
									++cancelled;
								} else if (INACTIVATE.equals(rateLinevo
										.getRatelineStatus())) {
									ratecard.setRateCardStatus(INACTIVATE);
									ratecard.setLastUpdateTime(currentTime);
									isInactive = true;
								} else if (NEW.equals(rateLinevo
										.getRatelineStatus())) {
									isNew = true;
								}
							}
							if (isActive) {
								ratecard.setRateCardStatus(ACTIVATE);
								ratecard.setLastUpdateTime(currentTime);
							} else if (cancelled>0) {//Modified for icrd-260016,since a single rate line in a rate card can be made to status cancelled. 
								ratecard.setRateCardStatus(CANCEL);
								ratecard.setLastUpdateTime(currentTime);
							} else if (!isInactive) {
								if (isNew) {
									ratecard.setRateCardStatus(NEW);
								}
							}
						}
					}
					rateCardIdMap.put(rateLineVO.getRateCardID(), rateLineVO
							.getRateCardID());
				}
			}
		} catch (FinderException finderException) {
			log.log(Log.FINE,  "FinderException");
		}
		log.exiting(CLASS_NAME, "updateRateCardStatus");
	}

	/**
	 * Inactivates the billing lines.
	 *
	 * @param billingLineVos the billing line vos
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public void inActivateBillingLines(Collection<BillingLineVO> billingLineVos)
	throws SystemException {
		log.entering(CLASS_NAME, "inActivateBillingLines");
		//Added for ICRD-143975 starts
		LocalDate date = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		//Added for ICRD-143975 ends
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(
				BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME,
				BillingMatrixAuditVO.AUDIT_ENTITY);
		StringBuilder addInActivateInfo = null;
		if(billingLineVos!=null && billingLineVos.size()>0){
			/**
			 * populating Audit details
			 */
			billingMatrixAuditVO.setCompanyCode(billingLineVos.iterator().next().getCompanyCode());
			billingMatrixAuditVO.setBillingmatrixID(billingLineVos.iterator().next().getBillingMatrixId());
			isBillingLineParameterSame(billingLineVos);
			addInActivateInfo=new StringBuilder("Rate lines with following IDs are Inactivated:  ");
			/**
			 * populating Audit details ENDS
			 */
		}
		for (BillingLineVO billingLineVo : billingLineVos) {
			billingLineVo.setBillingLineStatus(INACTIVATE);
			billingLineVo.setLastUpdatedTime(date);//Added for ICRD-143975
			billingLineVo.setLastUpdatedUser(logonAttributes.getUserId());//Added for ICRD-143975
			addInActivateInfo.append(billingLineVo.getBillingLineSequenceNumber()).append(" ");
			updateBillingLine(billingLineVo);
			/**
			 *  Audit AdditionalInformation
			 */
			addInActivateInfo.append(billingLineVo.getBillingLineSequenceNumber());
		}
		updateBillingMatrixStatus(billingLineVos);
		/**
		 * Audit
		 */
		if(addInActivateInfo!=null){
			billingMatrixAuditVO.setAdditionalInformation(addInActivateInfo.toString());
			billingMatrixAuditVO.setActionCode("INACTIVATED the rateline");
			AuditUtils.performAudit(billingMatrixAuditVO);
		}
		log.exiting(CLASS_NAME, "inActivateBillingLines");
	}

	/**
	 * Cancels the billing lines.
	 *
	 * @param billingLineVos the billing line vos
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public void cancelBillingLines(Collection<BillingLineVO> billingLineVos)
	throws SystemException {
		log.entering(CLASS_NAME, "cancelBillingLines");
		//Added for ICRD-143975 starts
		LocalDate date = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		//Added for ICRD-143975 ends
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(
				BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME,
				BillingMatrixAuditVO.AUDIT_ENTITY);
		StringBuilder addCancelInfo = null;
		if(billingLineVos!=null && billingLineVos.size()>0){
			/**
			 * populating Audit details
			 */
			billingMatrixAuditVO.setCompanyCode(billingLineVos.iterator().next().getCompanyCode());
			billingMatrixAuditVO.setBillingmatrixID(billingLineVos.iterator().next().getBillingMatrixId());
			isBillingLineParameterSame(billingLineVos);
			addCancelInfo=new StringBuilder("Rate lines with following IDs are Cancelled: ");
			/**
			 * populating Audit details ENDS
			 */
		}
		for (BillingLineVO billingLineVo : billingLineVos) {
			billingLineVo.setBillingLineStatus(CANCEL);
			billingLineVo.setLastUpdatedTime(date);//Added for ICRD-143975
			billingLineVo.setLastUpdatedUser(logonAttributes.getUserId());//Added for ICRD-143975
			addCancelInfo.append(billingLineVo.getBillingLineSequenceNumber()).append(" ");
			updateBillingLine(billingLineVo);
			/**
			 *  Audit AdditionalInformation
			 */
			addCancelInfo.append(billingLineVo.getBillingLineSequenceNumber());
		}
		updateBillingMatrixStatus(billingLineVos);
		/**
		 * Audit
		 */
		if(addCancelInfo!=null){
			billingMatrixAuditVO.setAdditionalInformation(addCancelInfo.toString());
			billingMatrixAuditVO.setActionCode("CANCELLED the rateline");
			AuditUtils.performAudit(billingMatrixAuditVO);
		}
		log.exiting(CLASS_NAME, "cancelBillingLines");
	}

	/**
	 * Activate billing lines.
	 *
	 * @param billingLineVos the billing line vos
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	public void activateBillingLines(Collection<BillingLineVO> billingLineVos)
	throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "activateBillingLines");
		BillingMatrixAuditVO billingMatrixAuditVO = new BillingMatrixAuditVO(
				BillingMatrixAuditVO.AUDIT_MODULENAME,
				BillingMatrixAuditVO.AUDIT_SUBMODULENAME,
				BillingMatrixAuditVO.AUDIT_ENTITY);
		StringBuilder addActivationInfo = null;
		/*
		 * if (checkDuplicateParameters(billingLineVos)) { throw new
		 * SystemException(
		 * DuplicateBillingLineException.DUPLICATE_BILLING_LINE_EXIST, new
		 * DuplicateBillingLineException()); } else { for (BillingLineVO
		 * billingLineVo : billingLineVos) {
		 * billingLineVo.setBillingLineStatus(ACTIVATE);
		 * updateBillingLine(billingLineVo); } }
		 */
		/*
		 * Change done by indu change the billing status of a single billing
		 * line Validate if same billing line exists under any Billing Matrix,
		 * if not exists then find the corresponding billing matrix and billing
		 * line under that and change the status of that billing line
		 */

		if (billingLineVos != null && billingLineVos.size() > 0) {
			/**
			 * populating Audit details
			 */
			billingMatrixAuditVO.setCompanyCode(billingLineVos.iterator().next().getCompanyCode());
			billingMatrixAuditVO.setBillingmatrixID(billingLineVos.iterator().next().getBillingMatrixId());
			isBillingLineParameterSame(billingLineVos);
			addActivationInfo=new StringBuilder("Rate lines with following IDs are Activated:");
			/**
			 * populating Audit details ENDS
			 */
			//Added for ICRD-143975 starts
			LocalDate date = null;
			LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
			date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			//Added for ICRD-143975 ends
			//Checks whether the Billing line contains same parameter
			if(!isBillingLineParameterSame(billingLineVos)){
				for (BillingLineVO billingLineVO : billingLineVos) {

					validateBillingLines(findOverlappingBillingLines(billingLineVO,
					"A"));
					billingLineVO.setBillingLineStatus("A");
					billingLineVO.setLastUpdatedTime(date);//Added for ICRD-143975
					billingLineVO.setLastUpdatedUser(logonAttributes.getUserId());//Added for ICRD-143975
					updateBillingLine(billingLineVO);
					/**
					 *  Audit AdditionalInformation
					 */
					addActivationInfo.append(billingLineVO.getBillingLineSequenceNumber());
				}

			}
		}
		updateBillingMatrixStatus(billingLineVos);
		/**
		 * Audit
		 */
		if(addActivationInfo!=null){
			billingMatrixAuditVO.setAdditionalInformation(addActivationInfo.toString());
			billingMatrixAuditVO.setActionCode("ACTIVATED the rate line");
			AuditUtils.performAudit(billingMatrixAuditVO);
		}
		log.exiting(CLASS_NAME, "activateBillingLines");
	}

	/**
	 * Checks whether the Billing line contains same parameter in same date range.
	 *
	 * @param billingLineVos the billing line vos
	 * @return the boolean
	 * @throws SystemException the system exception
	 * @author A-3434
	 */
	public Boolean isBillingLineParameterSame(Collection<BillingLineVO> billingLineVos)
	throws SystemException {
		log.entering(CLASS_NAME, "isBillingLineParameterSame");

		HashMap<String, BillingLineVO> billingLineVoforChk = null;
		billingLineVoforChk = new HashMap<String, BillingLineVO>();
		Boolean isParameterSame=false;
		String firstParameter="";
		String RevenueExpenditureFlag ="";
		for (BillingLineVO billingLineVoforCompare : billingLineVos) {
			log.log(Log.INFO, "billingLineVoforCompare.. ",
					billingLineVoforCompare);
			String parameter="";
			
			String revenueExpenditureFlagCheck =billingLineVoforCompare.getRevenueExpenditureFlag();
			LocalDate fromDate = billingLineVoforCompare.getValidityStartDate();
			LocalDate toDate = billingLineVoforCompare.getValidityEndDate();
			Collection<BillingLineParameterVO> billingLineParameterVOs=billingLineVoforCompare.getBillingLineParameters();

			for (BillingLineParameterVO billingLineParameterVO : billingLineParameterVOs) {
				String parameterValue = billingLineParameterVO.getParameterValue();
				parameter = parameter.concat(parameterValue);
			}
			parameter=parameter.concat(billingLineVoforCompare.getBillingBasis());
			boolean isPresent = billingLineVoforChk.containsKey(firstParameter);
			if (isPresent) {
				log.log(Log.INFO, "INSIDE PRESENT>>>>>>>>>>>>>>>>>>>>. ");
				BillingLineVO billingLineVoInMap = billingLineVoforChk
				.get(firstParameter);
				LocalDate startDate = billingLineVoInMap.getValidityStartDate();
				LocalDate endDate = billingLineVoInMap.getValidityEndDate();
				Boolean isdateOverlap=true;
				log.log(Log.INFO, "parameter.. ", parameter);
				log.log(Log.INFO, "firstParameter.. ", firstParameter);
				log.log(Log.INFO, "billingLineVoInMap.. ", billingLineVoInMap);
				//Checking whether the dates are overlapping
				isdateOverlap=(((startDate.isGreaterThan(toDate)||startDate.compareTo(toDate) == 0)&&
						(fromDate.isLesserThan(startDate)||fromDate.compareTo(startDate) == 0))||
						((toDate.isGreaterThan(startDate)||startDate.compareTo(toDate) == 0)&&
								(endDate.isLesserThan(fromDate)||fromDate.compareTo(endDate) == 0)));
                
				if (parameter.equals(firstParameter)&& !isdateOverlap && RevenueExpenditureFlag.equals(revenueExpenditureFlagCheck) ){
					log
					.log(Log.INFO,
					"INSIDE IF BEFORE THROWING EXCEPTION>>>>>>>>>>>>>>>>>>>>. ");
					isParameterSame=true;
					throw new SystemException(
							DuplicateParameterException.DUPLICATE_PARAMETER_EXIST,
							new DuplicateParameterException());
				}

			}
			else {
				// Collection<BillingLineVO>
				//
				log.log(Log.INFO, "PUTTING IN MAP>>>>>>>>>>>>>>>>>>>>. ");
				firstParameter=parameter;
				RevenueExpenditureFlag = revenueExpenditureFlagCheck;
				billingLineVoforChk.put(firstParameter, billingLineVoforCompare);
			}

			log.exiting(CLASS_NAME, "isBillingLineParameterSame");
		}
		return isParameterSame;

	}


	/**
	 * Updates billing line status.
	 *
	 * @param billingLineVo the billing line vo
	 * @throws SystemException the system exception
	 */
	private void updateBillingLine(BillingLineVO billingLineVo)
	throws SystemException {
		log.entering(CLASS_NAME, "updateBillingLine");

		log.log(Log.FINE, "billingLineVo>>>>", billingLineVo);
		BillingLine billingLine = null;
		billingLine = BillingLine.find(billingLineVo.getCompanyCode(),
				billingLineVo.getBillingMatrixId(), billingLineVo
				.getBillingLineSequenceNumber());
		if (billingLine != null) {
			if(!"E".equalsIgnoreCase(billingLineVo
					.getBillingLineStatus())){
			billingLine.setBillingLineStatus(billingLineVo
					.getBillingLineStatus());
			}
			billingLine.setLastUpdatedTime(billingLineVo.getLastUpdatedTime());
			billingLine.setLastUpdatedUser(billingLineVo.getLastUpdatedUser());
			billingLine.setValidityEndDate(billingLineVo.getValidityEndDate());
			log.log(Log.WARNING, "BillingLine Entity updated");
		} else {
			log.log(Log.WARNING, "BillingLine Entity not found");
		}
		log.exiting(CLASS_NAME, "updateBillingLine");
	}

	/**
	 * Updates billing matrix status.
	 *
	 * @param billingLineVos the billing line vos
	 * @throws SystemException the system exception
	 */
	private void updateBillingMatrixStatus(
			Collection<BillingLineVO> billingLineVos) throws SystemException {
		log.entering(CLASS_NAME, "updateBillingMatrixStatus");
		Map<String, String> billingMatrixIdMap = new HashMap<String, String>();
		int cancelled = 0;
		boolean isActive = false;
		boolean isNew = false;
		boolean isInactive = false;
		for (BillingLineVO billingLineVo : billingLineVos) {
			if (!billingMatrixIdMap.containsKey(billingLineVo
					.getBillingMatrixId())) {
				BillingLineFilterVO billingLineFilterVo = new BillingLineFilterVO();
				billingLineFilterVo.setCompanyCode(billingLineVo
						.getCompanyCode());
				billingLineFilterVo.setBillingMatrixId(billingLineVo
						.getBillingMatrixId());
				Collection<BillingLineVO> billingLines = BillingMatrix
				.findBillingLines(billingLineFilterVo);
				BillingMatrix billingMatrix = BillingMatrix.find(billingLineVo
						.getCompanyCode(), billingLineVo.getBillingMatrixId());
				if (billingMatrix != null) {
					if (billingLines != null && billingLines.size() > 0) {
						for (BillingLineVO billingLineVO : billingLines) {
							if (ACTIVATE.equals(billingLineVO
									.getBillingLineStatus())) {
								isActive = true;
							} else if (CANCEL.equals(billingLineVO
									.getBillingLineStatus())) {
								++cancelled;
							} else if (INACTIVATE.equals(billingLineVO
									.getBillingLineStatus())) {
								billingMatrix
								.setBillingMatrixStatus(INACTIVATE);
								isInactive = true;
							} else if (NEW.equals(billingLineVO
									.getBillingLineStatus())) {
								isNew = true;
							}
						}
						if (isActive) {
							billingMatrix.setBillingMatrixStatus(ACTIVATE);
						} else if (cancelled == billingLines.size()) {
							billingMatrix.setBillingMatrixStatus(CANCEL);
						} else if (!isInactive) {
							if (isNew) {
								billingMatrix.setBillingMatrixStatus(NEW);
							}
						}

						/**
						 * Added  as part of optimistic Locking
						 * Mechanism
						 */
						billingMatrix.setLastUpdatedTime(billingMatrix.getLastUpdatedTime());
						billingMatrix.setLastUpdatedUser(billingLineVo.getLastUpdatedUser());
					}
				}
				billingMatrixIdMap.put(billingLineVo.getBillingMatrixId(),
						billingLineVo.getBillingMatrixId());
			}
		}
		log.exiting(CLASS_NAME, "updateBillingMatrixStatus");
	}

	/**
	 * The Class SortPriority.
	 *
	 */
	/**
	 * @param billingLineVos
	 * @return
	 * @throws SystemException
	 */
	// commented since this method is not used...............
	// private boolean checkDuplicateParameters(
	// Collection<BillingLineVO> billingLineVos) throws SystemException {
	// log.entering(CLASS_NAME, "checkDuplicateParameters");
	// StringBuilder billingLinesKey = null;
	// Map<String, String> billingMatrixIdMap = new HashMap<String, String>();
	// HashMap<String, BillingLineVO> billingLinesMap = new HashMap<String,
	// BillingLineVO>();
	// Collection<BillingLineParameterVO> parameterVos = null;
	// for (BillingLineVO billingLineVo : billingLineVos) {
	// if (!billingMatrixIdMap.containsKey(billingLineVo
	// .getBillingMatrixId())) {
	// BillingLineFilterVO billingLineFilterVo = new BillingLineFilterVO();
	// billingLineFilterVo.setCompanyCode(billingLineVo
	// .getCompanyCode());
	// billingLineFilterVo.setBillingMatrixId(billingLineVo
	// .getBillingMatrixId());
	// Collection<BillingLineVO> billingLines = BillingMatrix
	// .findBillingLines(billingLineFilterVo);
	// parameterVos = billingLineVo.getBillingLineParameters();
	// Collections.sort(
	// (ArrayList<BillingLineParameterVO>) parameterVos,
	// new SortPriority());
	// log.log(Log.INFO,
	// "after sorting parameters for the first time --->"
	// + parameterVos);
	// billingLinesKey = new StringBuilder();
	// for (BillingLineParameterVO parameterVo : parameterVos) {
	// billingLinesKey.append(parameterVo.getParameterCode());
	// billingLinesKey.append(parameterVo.getParameterValue());
	// billingLinesKey.append(parameterVo.getExcludeFlag());
	// }
	// log.log(Log.INFO,
	// "billingLinesKey created for the first time-->"
	// + billingLinesKey);
	// billingLinesMap.put(billingLinesKey.toString(), billingLineVo);
	// parameterVos = null;
	// if (billingLines != null && billingLines.size() > 0) {
	// for (BillingLineVO billingLineVO : billingLines) {
	// if (ACTIVATE.equals(billingLineVO
	// .getBillingLineStatus())) {
	// parameterVos = billingLineVO
	// .getBillingLineParameters();
	// Collections
	// .sort(
	// (ArrayList<BillingLineParameterVO>) parameterVos,
	// new SortPriority());
	// log.log(Log.INFO, "after sorting parameters --->"
	// + parameterVos);
	// billingLinesKey = new StringBuilder();
	// for (BillingLineParameterVO parameterVo : parameterVos) {
	// billingLinesKey.append(parameterVo
	// .getParameterCode());
	// billingLinesKey.append(parameterVo
	// .getParameterValue());
	// billingLinesKey.append(parameterVo
	// .getExcludeFlag());
	// }
	// log.log(Log.INFO, "billingLinesKey created -->"
	// + billingLinesKey);
	// parameterVos = null;
	// if (billingLinesMap.containsKey(billingLinesKey
	// .toString())) {
	// /*
	// * Checking Effective from and effective to date
	// * range
	// */
	// if ((billingLineVO
	// .getValidityStartDate()
	// .isGreaterThan(
	// billingLinesMap.get(
	// billingLinesKey
	// .toString())
	// .getValidityStartDate()) || (billingLineVO
	// .getValidityStartDate()
	// .equals(billingLinesMap.get(
	// billingLinesKey.toString())
	// .getValidityStartDate())))
	// && (billingLineVO
	// .getValidityEndDate()
	// .isLesserThan(
	// billingLinesMap
	// .get(
	// billingLinesKey
	// .toString())
	// .getValidityEndDate()) || (billingLineVO
	// .getValidityEndDate()
	// .equals(billingLinesMap.get(
	// billingLinesKey
	// .toString())
	// .getValidityEndDate())))) {
	// log.exiting(CLASS_NAME,
	// "checkDuplicateParameters");
	// return true;
	// }
	// } else {
	// billingLinesMap.put(billingLinesKey.toString(),
	// billingLineVO);
	// }
	// }
	// }
	// }
	// billingMatrixIdMap.put(billingLineVo.getBillingMatrixId(),
	// billingLineVo.getBillingMatrixId());
	// }
	// }
	// log.exiting(CLASS_NAME, "checkDuplicateParameters");
	// return false;
	// }
	/**
	 *
	 * @author a-2518
	 *
	 */
//	private static class SortPriority implements Comparator {
//
//		/**
//		 * Compare.
//		 *
//		 * @param object the object
//		 * @param obj the obj
//		 * @return the int
//		 */
//		public int compare(Object object, Object obj) {
//			if (object instanceof BillingLineParameterVO
//					&& obj instanceof BillingLineParameterVO) {
//				BillingLineParameterVO code = (BillingLineParameterVO) object;
//				BillingLineParameterVO parameterCode = (BillingLineParameterVO) obj;
//				return (code.getParameterCode()).compareTo(parameterCode
//						.getParameterCode());
//			}
//			return 0;
//		}
//	}

	/**
	 * Save mail invoic adv.
	 *
	 * @param masterVO the master vo
	 * @throws SystemException the system exception
	 */
	/* Commented the method as part of ICRD-153078
	public void saveMailInvoicAdv(MailInvoicMasterVO masterVO)
	throws SystemException {
		log.entering(CLASS_NAME, "save mailinvoic--->" + masterVO);
		if (masterVO != null) {

			// ArrayList<MailInvoicDupRecepVO> dupRecepticlesDetails= new
			// ArrayList<MailInvoicDupRecepVO>();
			ArrayList<MailInvoicProductDtlVO> saveProductDetails = new ArrayList<MailInvoicProductDtlVO>();

			ArrayList<MailInvoicPackageVO> savePackageDetails = new ArrayList<MailInvoicPackageVO>();
			ArrayList<MailInvoicMonetaryAmtVO> saveMonetaryDetails = new ArrayList<MailInvoicMonetaryAmtVO>();
			ArrayList<MailInvoicPriceVO> savePriceDetails = new ArrayList<MailInvoicPriceVO>();
			ArrayList<MailInvoicLocationVO> saveLocationDetails = new ArrayList<MailInvoicLocationVO>();
			ArrayList<MailInvoicTransportationDtlVO> saveTranDetails = new ArrayList<MailInvoicTransportationDtlVO>();

			Collection<MailInvoicProductDtlVO> productDetails = masterVO
			.getMailInvoicProductDetails();
			Collection<MailInvoicPackageVO> packageDetails = masterVO
			.getMailInvoicPackageDetails();
			Collection<MailInvoicMonetaryAmtVO> monetaryDetails = masterVO
			.getMailInvoicMonetaryDetails();
			Collection<MailInvoicPriceVO> priceDetails = masterVO
			.getMailInvoicPriceDetails();
			Collection<MailInvoicLocationVO> locationDetails = masterVO
			.getMailInvoicLocationDetails();
			Collection<MailInvoicTransportationDtlVO> tranDetails = masterVO
			.getMailInvoicTransportationDetails();

			Collection<String> keyvals = new ArrayList<String>();

			if (productDetails != null && productDetails.size() > 0) {
				for (MailInvoicProductDtlVO vo : productDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(keyvals.contains(key))) {

						keyvals.add(key);
						saveProductDetails.add(vo);
					}

				}
			}
			Collection<String> packkeyvals = new ArrayList<String>();
			if (packageDetails != null && packageDetails.size() > 0) {
				for (MailInvoicPackageVO vo : packageDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(packkeyvals.contains(key))) {

						packkeyvals.add(key);
						savePackageDetails.add(vo);
					}

				}
			}
			Collection<String> monkeyvals = new ArrayList<String>();
			if (monetaryDetails != null && monetaryDetails.size() > 0) {
				for (MailInvoicMonetaryAmtVO vo : monetaryDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(monkeyvals.contains(key))) {

						monkeyvals.add(key);
						saveMonetaryDetails.add(vo);
					}

				}
			}
			Collection<String> prikeyvals = new ArrayList<String>();
			if (priceDetails != null && priceDetails.size() > 0) {
				for (MailInvoicPriceVO vo : priceDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(prikeyvals.contains(key))) {

						prikeyvals.add(key);
						savePriceDetails.add(vo);
					}

				}
			}
			Collection<String> lockeyvals = new ArrayList<String>();
			if (locationDetails != null && locationDetails.size() > 0) {
				for (MailInvoicLocationVO vo : locationDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(lockeyvals.contains(key))) {

						lockeyvals.add(key);
						saveLocationDetails.add(vo);
					}

				}
			}
			Collection<String> trankeyvals = new ArrayList<String>();
			if (tranDetails != null && tranDetails.size() > 0) {
				for (MailInvoicTransportationDtlVO vo : tranDetails) {
					String key = new StringBuilder()
					.append(vo.getCompanyCode()).append(
							vo.getInvoiceKey()).append(vo.getPoaCode())
							.append(vo.getReceptacleIdentifier()).append(
									vo.getSectorOrigin()).append(
											vo.getSectorDestination()).toString();
					if (!(trankeyvals.contains(key))) {

						trankeyvals.add(key);
						saveTranDetails.add(vo);
					}

				}
			}

			if (saveProductDetails != null && saveProductDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicProductDtlVO vo = new MailInvoicProductDtlVO();
				for (int i = saveProductDetails.size() - 1; i > -1; i--) {
					vo = saveProductDetails.get(i);

					log.log(Log.INFO, "vo for dup check--", vo);
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					log.log(Log.INFO, "duplicate counr-->", dupCount);
					if (dupCount != null && dupCount.trim().length() > 0) {
						MailInvoicDupRecepVO dupVO = new MailInvoicDupRecepVO();
						dupVO.setCompanyCode(vo.getCompanyCode());
						dupVO.setRecepticleIdentifier(vo
								.getReceptacleIdentifier());
						dupVO.setSectorDestination(vo.getSectorDestination());
						dupVO.setSectorOrigin(vo.getSectorOrigin());
						MailInvoicProductDtlVO orginalVO = new MailInvoicProductDtlVO();
						orginalVO.setCompanyCode(vo.getCompanyCode());
						orginalVO.setInvoiceKey(dupCount);
						orginalVO.setPoaCode(vo.getPoaCode());
						orginalVO.setReceptacleIdentifier(vo
								.getReceptacleIdentifier());
						orginalVO.setSectorOrigin(vo.getSectorOrigin());
						orginalVO.setSectorDestination(vo
								.getSectorDestination());
						try {
							MailInvoicProductDtl mailInvoicProductDtl = MailInvoicProductDtl
							.find(orginalVO);
							if (mailInvoicProductDtl != null) {
								dupVO.setOriginalInvoicRef(mailInvoicProductDtl
										.getMailInvoicProductDtlPK()
										.getInvoiceKey());
							}
						} catch (FinderException e) {
							e.getErrorCode();
						}
						dupVO.setDuplicateInvoicRef(vo.getInvoiceKey());
						// dupRecepticlesDetails.add(dupVO);//for save to dup
						// table

						if ("8".equals(masterVO.getPaymentType())) {
							Collection<String> reconciles = MailInvoicReconcileDtl
							.findReconcileSectorIdrs(dupVO);
							log
									.log(Log.INFO, "reconciles counr-->",
											reconciles);
							if (reconciles != null && reconciles.size() > 0) {
								try {
									for (String recon : reconciles) {
										MailInvoicReconcileDtlVO reconvo = new MailInvoicReconcileDtlVO();
										reconvo.setCompanyCode(vo
												.getCompanyCode());
										reconvo.setRecepticleIdentifier(vo
												.getReceptacleIdentifier());
										reconvo.setSectorIdentifier(recon);

										MailInvoicReconcileDtl mailInvoicReconcileDtl = MailInvoicReconcileDtl
										.find(reconvo);
										if (mailInvoicReconcileDtl != null) {
											mailInvoicReconcileDtl
											.updateClaimStatus(reconvo);
										}
									}
								} catch (FinderException e) {
									e.getErrorCode();
								}
							}
						} else {
							/*
							 * Find and Insert for MailInvoicDupRecep(MTKINVDUPRCP)
							 
							MailInvoicDupRecep  mailInvoicDupRecep = null;
							try {
								mailInvoicDupRecep = MailInvoicDupRecep.find(dupVO);
							} catch (FinderException finderException) {
								new MailInvoicDupRecep(dupVO);
							}
						}
						saveProductDetails.remove(vo);
					}
				}
				/*
				 * if(!("8".equals(masterVO.getPaymentType()))){
				 * if(dupRecepticlesDetails!=null &&
				 * dupRecepticlesDetails.size()>0){
				 *
				 * for(MailInvoicDupRecepVO dupvo:dupRecepticlesDetails){ new
				 * MailInvoicDupRecep(dupvo); } //save in duplicate table } }
				 

			}
			// package details
			if (savePackageDetails != null && savePackageDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicPackageVO vo = new MailInvoicPackageVO();
				log.log(Log.INFO, "savepacvke-->", savePackageDetails);
				for (int i = savePackageDetails.size() - 1; i > -1; i--) {
					vo = savePackageDetails.get(i);
					log.log(Log.INFO, "savepacvke-->", savePackageDetails);
					// callthe method
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					log.log(Log.INFO, "duplicate counr-->", dupCount);
					if (dupCount != null && dupCount.trim().length() > 0) {

						savePackageDetails.remove(vo);
					}
				}

			}
			// monetary amts
			if (saveMonetaryDetails != null && saveMonetaryDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicMonetaryAmtVO vo = new MailInvoicMonetaryAmtVO();
				for (int i = saveMonetaryDetails.size() - 1; i > -1; i--) {
					vo = saveMonetaryDetails.get(i);

					// callthe method
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					if (dupCount != null && dupCount.trim().length() > 0) {

						saveMonetaryDetails.remove(vo);
					}
				}

			}
			// price details
			if (savePriceDetails != null && savePriceDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicPriceVO vo = new MailInvoicPriceVO();
				for (int i = savePriceDetails.size() - 1; i > -1; i--) {
					vo = savePriceDetails.get(i);

					// callthe method
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					if (dupCount != null && dupCount.trim().length() > 0) {

						savePriceDetails.remove(vo);
					}
				}

			}
			// location details
			if (saveLocationDetails != null && saveLocationDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicLocationVO vo = new MailInvoicLocationVO();
				for (int i = saveLocationDetails.size() - 1; i > -1; i--) {
					vo = saveLocationDetails.get(i);

					// callthe method
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					if (dupCount != null && dupCount.trim().length() > 0) {

						saveLocationDetails.remove(vo);
					}
				}

			}

			if (saveTranDetails != null && saveTranDetails.size() > 0) {
				// CALL THR METHOD THAT RETURNS DUPLICTES RECORDS
				MailInvoicTransportationDtlVO vo = new MailInvoicTransportationDtlVO();
				for (int i = saveTranDetails.size() - 1; i > -1; i--) {
					vo = saveTranDetails.get(i);

					// callthe method
					String dupCount = MailInvoicPackage.findDuplicateRecticles(
							vo.getCompanyCode(), vo.getSectorOrigin(), vo
							.getSectorDestination(), masterVO
							.getPaymentType(), vo
							.getReceptacleIdentifier());
					if (dupCount != null && dupCount.trim().length() > 0) {

						saveTranDetails.remove(vo);
					}
				}

			}
			masterVO.setMailInvoicProductDetails(saveProductDetails);
			masterVO.setMailInvoicPackageDetails(savePackageDetails);
			masterVO.setMailInvoicPriceDetails(savePriceDetails);
			masterVO.setMailInvoicMonetaryDetails(saveMonetaryDetails);
			masterVO.setMailInvoicLocationDetails(saveLocationDetails);
			masterVO.setMailInvoicTransportationDetails(saveTranDetails);

			// duplicates should be saved to duplicat table
			log.entering(CLASS_NAME, "save mailinvoic after modification--->"
					+ masterVO);
			/*
			 * Find and Insert for MailInvoicMaster(MTKINVMST)
			
			MailInvoicMaster mailInvoicMst = null;
			try {
				mailInvoicMst = MailInvoicMaster.find(masterVO);
			} catch (FinderException ex) {
				new MailInvoicMaster(masterVO);
			}
		}
		/**
		 * added by A-2565 Meenu for triggering USPS accounting while processing Invoic ADV message
		 
		String sysparValue=null;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		//system parameter for accounting
		systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		if (systemParameters != null) {
			String accountingEnabled = (systemParameters.get(SYS_PARA_ACCOUNTING_ENABLED));
			log.log(Log.FINE, " accountingEnabled==================> ",
					accountingEnabled);
			//if  accounting enabled
			if( "Y".equals(accountingEnabled)){
				MailInvoicMaster mailInvoicMaster = new MailInvoicMaster() ;

				mailInvoicMaster.triggerUSPSAccounting(masterVO);

			}
		}
		log.exiting(CLASS_NAME, "save mailinvoic");
	}*/

	/**
	 * Find invoic enquiry details.
	 *
	 * @param invoiceEnquiryFilterVo the invoice enquiry filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author a-2270
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo)
			throws SystemException {
		return MailInvoicMaster.findInvoicEnquiryDetails(invoiceEnquiryFilterVo);
	}

	/**
	 * Find invoic claims enquiry details.
	 *
	 * @param filterVO the filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author a-2270
	 */
	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails(
			MailInvoicClaimsFilterVO filterVO) throws SystemException {
		return MailInvoicClaimDetails.findInvoicClaimsEnquiryDetails(filterVO);
	}

	/**
	 * Find dot rate details.
	 *
	 * @param filterVO the filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(
			MailDOTRateFilterVO filterVO) throws SystemException {
		return MailDOTRate.findDOTRateDetails(filterVO);
	}

	/**
	 * Save dot rate details.
	 *
	 * @param mailDOTRateVOs the mail dot rate v os
	 * @throws SystemException the system exception
	 */
	public void saveDOTRateDetails(Collection<MailDOTRateVO> mailDOTRateVOs)
	throws SystemException {
		if (mailDOTRateVOs != null && mailDOTRateVOs.size() > 0) {
			for (MailDOTRateVO vo : mailDOTRateVOs) {
				if (OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())) {
					new MailDOTRate(vo);
				} else if (OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())) {
					MailDOTRatePK mailDOTRatePK = new MailDOTRatePK();
					mailDOTRatePK.setCompanyCode(vo.getCompanyCode());
					mailDOTRatePK.setCircleMiles(vo.getCircleMiles());
					mailDOTRatePK.setRateCode(vo.getRateCode());
					mailDOTRatePK.setSectorDestination(vo.getDestinationCode());
					mailDOTRatePK.setSectorOrigin(vo.getOriginCode());

					MailDOTRate mailDOTRate = MailDOTRate.find(vo);
					mailDOTRate.remove();

				} else if (OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag())) {
					MailDOTRatePK mailDOTRatePK = new MailDOTRatePK();
					mailDOTRatePK.setCompanyCode(vo.getCompanyCode());
					mailDOTRatePK.setCircleMiles(vo.getCircleMiles());
					mailDOTRatePK.setRateCode(vo.getRateCode());
					mailDOTRatePK.setSectorDestination(vo.getDestinationCode());
					mailDOTRatePK.setSectorOrigin(vo.getOriginCode());

					MailDOTRate mailDOTRate = MailDOTRate.find(vo);
					mailDOTRate.update(vo);

				}
			}
		}
	}

	/**
	 * Import to reconcile.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void importToReconcile(String companyCode) throws SystemException {
		log.entering(CLASS_NAME, "save importToReconcile");
		MailInvoicMaster.importToReconcile(companyCode);
		log.exiting(CLASS_NAME, "save importToReconcile");
	}

	/**
	 * Reconcile process.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void reconcileProcess(String companyCode) throws SystemException {
		log.entering(CLASS_NAME, "save importToReconcile");
		MailInvoicMaster.reconcileProcess(companyCode);
		log.exiting(CLASS_NAME, "save importToReconcile");
	}

	/**
	 * This method generates INVOIC Claim file.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 * @author A-2518
	 */
	public void generateInvoicClaimFile(String companyCode)
	throws SystemException {
		log.entering(CLASS_NAME, "generateInvoicClaimFile");
		Collection<InvoicMessageVO> invoicMessageVos = MailInvoicClaimDetails
		.generateInvoicClaimFile(companyCode);
		if (invoicMessageVos != null) {
			for (InvoicMessageVO invoicMessageVo : invoicMessageVos) {

				//for generating file name
				StringBuffer fileName = new StringBuffer();
				String tmpfileName="";
				String tfileName="";
				tmpfileName=new LocalDate(LocalDate.NO_STATION,Location.NONE, true).toDisplayFormat("yyyy-MM-dd HH:mm:ss");
				tfileName=  tmpfileName.replaceAll(":","");
				tmpfileName=tfileName.replaceAll("-","");
				tfileName=tmpfileName.replaceAll(" ","");
				(((fileName.append("INVOICCLM")).append(tfileName)).append(invoicMessageVo.getRecipientID())).append(".txt");
				log.log(Log.FINE, "File name--->>", fileName.toString());
				invoicMessageVo.setFileName(fileName.toString());
				invoicMessageVo.setMessageType(InvoicMessageVO.INVOICCLAIM);
				invoicMessageVo.setMessageStandard(InvoicMessageVO.EDIFACT);
				LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
				invoicMessageVo
				.setStationCode(logonAttributes.getStationCode());
				Criterion mesrefCriterion = KeyUtils
				.getCriterion(invoicMessageVo.getCompanyCode(),
						KEY_MESSAGE_REFERENCE, invoicMessageVo
						.getRecipientID());

				invoicMessageVo.setMessageReferenceNumber(KeyUtils
						.getKey(mesrefCriterion));
				invoicMessageVo.setNumberOfSegments(invoicMessageVo
						.getProductDetails().size());
				try {
					new MsgBrokerMessageProxy()
					.encodeAndSaveMessage(invoicMessageVo);
				} catch (SystemException systemException) {
					log
					.log(Log.SEVERE,
					"Exception occurred when encoding INVOIC Claim file");
					return;
				}
				for (InvoicProductMessageVO productMessageVo : invoicMessageVo
						.getProductDetails()) {
					MailInvoicClaimDetails claimDetail = null;
					try {
						claimDetail = MailInvoicClaimDetails.find(
								invoicMessageVo.getCompanyCode(),
								productMessageVo.getReceptacleIdentifier(),
								productMessageVo.getSectorIdentifier());
					} catch (FinderException finderException) {
						log.log(Log.WARNING,
						"MailInvoicClaimDetails Entity not found");
					}
					if (claimDetail != null) {
						// Updating claim status as 'Sent' and claim date as
						// current date
						claimDetail
						.setClaimStatus(MailInvoicClaimsEnquiryVO.SUCCESS);
						claimDetail.setClaimDate(new LocalDate(NO_STATION,
								NONE, true));
						log.log(Log.FINE,
						"Claim status has been updated to 'Sent'");
					}
				}
			}
		}
		log.exiting(CLASS_NAME, "generateInvoicClaimFile");
	}

	/**
	 * Find invoic key lov.
	 *
	 * @param companyCode the company code
	 * @param invoicKey the invoic key
	 * @param poaCode the poa code
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey, String poaCode, int pageNumber)
			throws SystemException {
		return MailInvoicMaster.findInvoicKeyLov(companyCode, invoicKey,
				poaCode, pageNumber);
	}

	/**
	 * Method to find the billingperiod of the gpacode/billing periods of all
	 * the gpacodes of the country specified
	 * Method is called on clickig the suggestbutton.
	 *
	 * @param generateInvoiceFilterVO the generate invoice filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * A-3108
	 */
	public Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {
		log.log(Log.INFO,"into controller");
		return  MRABillingMaster.findBillingPeriods(generateInvoiceFilterVO);
	}

	/**
	 * generateInvoice.
	 *
	 * @param generateInvoiceFilterVO the generate invoice filter vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	/* Commented the method as part of ICRD-153078
	public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException,MailTrackingMRABusinessException{
		MRABillingMaster.generateInvoice(generateInvoiceFilterVO);
	}*/

	/**
	 * Find despatch details.
	 *
	 * @param popUpVO the pop up vo
	 * @return DespatchEnquiryVO
	 * @throws SystemException the system exception
	 */
	/* Commented the method as part of ICRD-153078
	public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException{
		return MRABillingMaster.findDespatchDetails(popUpVO);
	}*/

	/**
	 * Find gpa billing details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<GPABillingDetailsVO>
	 * @throws SystemException the system exception
	 */
	public Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)throws SystemException{
		return MRABillingMaster.findGPABillingDetails(popUpVO);
	}

	/**
	 * Find dsn select lov.
	 *
	 * @param companyCode the company code
	 * @param dsnNum the dsn num
	 * @param dsnDate the dsn date
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
			String dsnNum,String dsnDate,int pageNumber) throws SystemException {
		log.entering(CLASS_NAME, "findRateCardLov");
		return MRABillingMaster.findDsnSelectLov(companyCode, dsnNum,dsnDate, pageNumber);
	}

	/**
	 * Find rate audit details.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return Collection<RateAuditVO>
	 * @throws SystemException the system exception
	 * @author  a-3108
	 */
	public Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findRateAuditDetails");
		return MRABillingMaster.findRateAuditDetails(rateAuditFilterVO);
	}

	

	/**
	 * Find list rate audit details.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @author a-2391
	 */
	public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws SystemException{
		return MRABillingMaster.findListRateAuditDetails(rateAuditFilterVO);
	}

	/**
	 * Change rate audit dsn status.
	 *
	 * @param rateAuditVOs the rate audit v os
	 * @param rateAuditVOsForaplyAudit the rate audit v os foraply audit
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author a-3108
	 */
	public void changeRateAuditDsnStatus(Collection<RateAuditVO> rateAuditVOs,Collection<RateAuditVO> rateAuditVOsForaplyAudit) throws SystemException,MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "changeDsnStatus");
		//String  DNSSTATUS_TOBERATEAUDITED="T";
		String  DNSSTATUS_RATEAUDITED="F";
		String RECIEVEABLE = "R";
		String CURRENCY_CODE = "NZD";
		String BILLABLE = "BB";
		String OUTWARD_BILLABLE = "OB";
		String INSERT = "I";
		String AIRLINE = "A";
		String GPA = "G";
		String ACTUAL = "A";

		Double presentWtCharge=0.0;
		Double updWt=0.0;
		Boolean isCCAIssued=false;

		Map<String, String> systemParameters = null;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		String accountingEnabled = (systemParameters.get(SYS_PARA_ACCOUNTING_ENABLED));

		for(RateAuditVO rateAuditVO:rateAuditVOs){

			rateAuditVO.setDsnStatus(DNSSTATUS_RATEAUDITED);
			saveHistoryForLog(rateAuditVO);
			LogonAttributes logonAttributes =
				(LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
			//log.log(Log.FINE,"rateAuditVObefore saving"+rateAuditVO);
			MRABillingMaster mRABillingMaster = null;
			Collection<MRABillingDetails> mraBillingDetails = null;
			try {

				mRABillingMaster = MRABillingMaster.find(rateAuditVO.getCompanyCode(),rateAuditVO.getMailSequenceNumber());

			} catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
			}
			if(mRABillingMaster!=null){
				//mRABillingMaster.setRateStatus(rateAuditVO.getDsnStatus());
				if(rateAuditVO.getLastUpdateTime()!=null){
					mRABillingMaster.setLastUpdateTime(rateAuditVO.getLastUpdateTime().toCalendar());
				}
				mraBillingDetails = mRABillingMaster.getBillingDetails();
				int a = mraBillingDetails.size();
				for(MRABillingDetails mraBillingDtl : mraBillingDetails){
					if("P".equals(mraBillingDtl.getPaymentFlag())){
						mraBillingDtl.setBlgStatus("IU");
					}else if("R".equals(mraBillingDtl.getPaymentFlag())){
						//Modified by A-7794 as part of MRA revamp
						if("G".equals(mraBillingDtl.getBillTooPartyType())){
							PostalAdministrationDetailsVO postalAdministrationDetailsVO = new PostalAdministrationDetailsVO();

							postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							postalAdministrationDetailsVO.setPoaCode(mraBillingDtl.getBillTooPartyCode());
							postalAdministrationDetailsVO.setParCode("BLGINFO");
							postalAdministrationDetailsVO.setValidFrom(rateAuditVO.getDsnDate());
							PostalAdministrationDetailsVO pADetailsVO = null;
							pADetailsVO = validatePoaDetailsForBilling(postalAdministrationDetailsVO);

							if(pADetailsVO==null){
								log.log(Log.SEVERE, "ERROR --->>> GPA NOT CONFIGURED !!!!!!!! Check DB!!!!!MTKPOADTL");
							}else if(pADetailsVO!=null){
								if(pADetailsVO.getBillingSource()!=null &&  "R".equals(pADetailsVO.getBillingSource())){
									mraBillingDtl.setBlgStatus("TR");
								}else if(pADetailsVO.getBillingSource()!=null &&  "B".equals(pADetailsVO.getBillingSource())){
									mraBillingDtl.setBlgStatus("BB");
								}
							}
						}else{
							mraBillingDtl.setBlgStatus("OB");
						}

					}
				}
			}
			/* Added for BUG : MRA 209
			 * If Accounting is Enabled, Flown And Interline Prov Accounting should be triggered
			 * START
			 */
			if("Y".equals(accountingEnabled)){
				log.log(Log.FINE, "Accounting is Enabled");
				mRABillingMaster.triggerFlownAndInterlineProvAccounting(rateAuditVO);
				log.log(Log.FINE, "Accounting Done");
			}
			//END MRA 209
			if(rateAuditVO.getPresentWtCharge()!=null){
				presentWtCharge=rateAuditVO.getPresentWtCharge().getRoundedAmount();
			}
			if(rateAuditVO.getAuditedWtCharge()!=null){
				updWt=rateAuditVO.getAuditedWtCharge().getRoundedAmount();
			}
			log
					.log(Log.FINE, "presentWtCharge-updWt..", presentWtCharge,
							updWt);
			if(((presentWtCharge-updWt)!=0)&&
					((rateAuditVO.getApplyAutd()!=null)&&("Y".equals(rateAuditVO.getApplyAutd())))){
				isCCAIssued=true;
			}

			if(isCCAIssued){
				//save an actual entry in ccadtl table ie issue an actual cca
				raiseActualCCA(rateAuditVO);
			}
			else{
				isCCAIssued=false;
			}
			log.log(Log.FINE, "isCCAIssued..", isCCAIssued);
		}
		//if apply audit is checked update those...
		if(rateAuditVOsForaplyAudit!=null){
			changeApplyAudit(rateAuditVOsForaplyAudit);
		}

	}



	/**
	 * Raise actual cca.
	 *
	 * @param rateAuditVO the rate audit vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3251
	 */
	private void raiseActualCCA(RateAuditVO rateAuditVO)throws SystemException,MailTrackingMRABusinessException{

		String RECIEVEABLE = "R";
		String CURRENCY_CODE = "NZD";
		String BILLABLE = "BB";
		String OUTWARD_BILLABLE = "OB";
		String INSERT = "I";
		String AIRLINE = "A";
		String GPA = "G";
		String ACTUAL = "A";

		Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		CCAdetailsVO ccaDetailsVO = new CCAdetailsVO();
		ccaDetailsVO.setOperationFlag(INSERT);
		ccaDetailsVO.setBillingBasis(rateAuditVO.getBillingBasis());
		ccaDetailsVO.setDsnNo(rateAuditVO.getDsn());
		ccaDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		ccaDetailsVO.setCsgDocumentNumber(rateAuditVO.getConDocNum());
		ccaDetailsVO.setCsgSequenceNumber(rateAuditVO.getConSerNum());
		ccaDetailsVO.setPoaCode(rateAuditVO.getGpaCode());
		ccaDetailsVO.setDsnDate(rateAuditVO.getDsnDate().toDisplayDateOnlyFormat());
		ccaDetailsVO.setPayFlag(RECIEVEABLE);
		//Added by Deepthi to control the accounting call while coming from rate audit acreen
		ccaDetailsVO.setFromRateAudit(rateAuditVO.getFromRateAudit());
		rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();

		for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
			if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){
				ccaDetailsVO.setContCurCode(rateAuditDetailsVO.getContCurCode());
			}
		}

		ccaDetailsVO.setGpaArlIndicator(AIRLINE);
		ccaDetailsVO.setIssuingParty(AIRLINE);
		ccaDetailsVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
		ccaDetailsVO.setCcaType(ACTUAL);
		ccaDetailsVO.setGrossWeight(rateAuditVO.getGrossWt());
		ccaDetailsVO.setRevGrossWeight(Double.parseDouble(rateAuditVO.getUpdWt()));
		ccaDetailsVO.setIssueDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,false).toDisplayDateOnlyFormat());
		log.log(Log.SEVERE, "\n\nCCA IssueDate !!!!\n\n", ccaDetailsVO.getIssueDate());
		for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
			if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){

				Money revChgGrossWeight = null;
				Money chgGrossWeight = null;
				Money dueArl = null;
				Money revDueArl = null;
				Money duePostDbt = null;
				try {
					revChgGrossWeight = CurrencyHelper.getMoney(CURRENCY_CODE);
					revChgGrossWeight.setAmount(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());
					chgGrossWeight = CurrencyHelper.getMoney(CURRENCY_CODE);
					chgGrossWeight.setAmount(rateAuditDetailsVO.getPrsntWgtCharge().getAmount());
					ccaDetailsVO.setChgGrossWeight(chgGrossWeight);
					ccaDetailsVO.setRevChgGrossWeight(revChgGrossWeight);
					dueArl = CurrencyHelper.getMoney(CURRENCY_CODE);
					dueArl.setAmount(rateAuditDetailsVO.getPrsntWgtCharge().getAmount());
					revDueArl = CurrencyHelper.getMoney(CURRENCY_CODE);
					revDueArl.setAmount(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());
					ccaDetailsVO.setDueArl(dueArl);
					ccaDetailsVO.setRevDueArl(revDueArl);
					duePostDbt = CurrencyHelper.getMoney(CURRENCY_CODE);
					duePostDbt.setAmount(0);
					ccaDetailsVO.setDuePostDbt(duePostDbt);
					ccaDetailsVO.setRevDuePostDbt(duePostDbt.getAmount());
					// Added by A-3434 for bug 47253
					ccaDetailsVO.setSectFrom(rateAuditDetailsVO.getSecFrom());
					ccaDetailsVO.setSectTo(rateAuditDetailsVO.getSecTo());
					ccaDetailsVO.setUpdBillTo(rateAuditDetailsVO.getBillTO());

				} catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
					e.getErrorCode();
				}

				if(GPA.equals(rateAuditDetailsVO.getGpaarlBillingFlag())){
					ccaDetailsVO.setGpaCode(rateAuditDetailsVO.getBillTO());
					ccaDetailsVO.setCcaStatus(BILLABLE);
				}else{
					ccaDetailsVO.setCcaStatus(OUTWARD_BILLABLE);
				}
			}
		}
		ccaDetailsVO.setOriginCode(rateAuditVO.getOrigin().substring(2, 5));
		ccaDetailsVO.setDestnCode(rateAuditVO.getDestination().substring(2, 5));

		saveCCAdetails(ccaDetailsVO);


	}



	/**
	 * Change apply audit.
	 *
	 * @param rateAuditVOs the rate audit v os
	 * @throws SystemException the system exception
	 * @author a-3434
	 */
	public void changeApplyAudit(Collection<RateAuditVO> rateAuditVOs) throws SystemException {
		for(RateAuditVO rateAuditVO:rateAuditVOs){
			MRABillingDetails mRABillingDetails = null;
			try{
				mRABillingDetails = MRABillingDetails.find(rateAuditVO.getCompanyCode(),rateAuditVO.getMailSequenceNumber(),rateAuditVO.getSerialNumber());
			}catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
			}
			log.log(Log.FINE, "ApplyAudit from vo : ", rateAuditVO.getApplyAutd());
//			if(mRABillingDetails!=null){
//				if("Y".equals(rateAuditVO.getApplyAutd())){
//
//					mRABillingDetails.setApplyAudit("Y");
//
//				}
//
//				else if("N".equals(rateAuditVO.getApplyAutd())){
//
//					mRABillingDetails.setApplyAudit("N");
//
//				}
//			}
		}
	}

	/**
	 * Find cca.
	 *
	 * @param maintainCCAFilterVO the maintain cca filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-3447
	 */
	public Collection<CCAdetailsVO> findCCA(MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException{

		return CCADetail.findCCA(maintainCCAFilterVO);

	}


	/**
	 * Save mc adetails.
	 *
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @throws BusinessDelegateException the business delegate exception
	 * @throws RemoteException the remote exception
	 * @author a-4823
	 */
	
	//@Advice(name = "mail.mra.saveMCADetails" , phase=Phase.POST_INVOKE)
	@Advices({
		@Advice(name = "mail.mra.saveMCADetails" , phase=Phase.POST_INVOKE),
		@Advice(name = "mail.operations.invokejobForRevInterface" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	})
	
	public String saveMCAdetails(CCAdetailsVO detailsVO)throws SystemException,MailTrackingMRABusinessException, BusinessDelegateException, RemoteException{
		CCADetail cCADetails = null;
		String ccaRefNo = "INVALID";

		//save,update or delete -->>
		if (OPERATION_FLAG_INSERT.equals(detailsVO.getOperationFlag())) {
			if(APPROVED.equals(detailsVO.getCcaStatus())){
				if(!"OH".equals(detailsVO.getBillingStatus()))
				{
				detailsVO.setBillingStatus(BILLABLE);
				}


				saveHistoryDetails(detailsVO);
				double netAmount = 0.0;
				//tax calculation
				//computeTax(detailsVO);
				//update CCADetail entity
				netAmount=detailsVO.getRevChgGrossWeight().getAmount()+detailsVO.getOtherRevChgGrossWgt().getAmount()+ detailsVO.getRevTax() - detailsVO.getRevTds();


				ccaRefNo = detailsVO.getCcaRefNumber();



			}
			else{
				/**
				 * Case 2: If the Revised currency is different from original currency and the revised weight charge is also diff from original
				 * Find new exchange rate and update the weight charge
				 * Commented as per the business, suggested by Riaz,
				 */

				//			if(!detailsVO.getContCurCode().equals(detailsVO.getRevContCurCode()) &&
				//					detailsVO.getRevChgGrossWeight().equals(detailsVO.getChgGrossWeight())){
				//				exchangeRate=getExChangeRate(detailsVO.getCompanyCode(), detailsVO.getContCurCode(), detailsVO.getRevContCurCode());
				//				if(exchangeRate!=null){
				//					detailsVO.getRevChgGrossWeight().setAmount(exchangeRate*detailsVO.getRevChgGrossWeight().getAmount());
				//				}
				//
				//			}
				/**
				 * Case 3: If revised weight charge  different from original
				 * computTax , Tds for the revised and update NetAmount in CCADetail entity
				 */

				//Added by A-7929 for ICRD-237091 starts...
				
				// commented below code block as part of ICRD-282761 
				/*double rate=(detailsVO.getRevChgGrossWeight().getAmount())/detailsVO.getGrossWeight();
				double netAmountValue = 0.0;
				Money netAmt;
				try {
					netAmt = CurrencyHelper.getMoney(detailsVO.getContCurCode());
					
				    netAmountValue = (detailsVO.getRevGrossWeight()*rate ); 
				    netAmt.setAmount(netAmountValue);
				    
				    detailsVO.setRevChgGrossWeight(netAmt);
				} catch (CurrencyException e) {
					log.log(Log.FINE,"Inside CurrencyException.. ");
				}*/
				// Added by A-7929 for ICRD-237091 ends...
				if(detailsVO.getChgGrossWeight().getAmount()!=(detailsVO.getRevChgGrossWeight().getAmount()) ){
					double netAmount = 0.0;
					if(detailsVO.getValChgUpdAmount()!=null && detailsVO.getValChgUpdAmount().getAmount()>0)
						{
						detailsVO.getValChgUpdAmount().setAmount(detailsVO.getRevChgGrossWeight().getAmount()+detailsVO.getValChgUpdAmount().getAmount());
						}
					//tax calculation
					computeTax(detailsVO);
					//update CCADetail entity
					netAmount=detailsVO.getRevChgGrossWeight().getRoundedAmount()+detailsVO.getOtherRevChgGrossWgt().getRoundedAmount()+ detailsVO.getRevTax() - detailsVO.getRevTds();
					//Commented by A-4809 for bug ICRD-18489
					/*if(detailsVO.getRevNetAmount()!=null){
						detailsVO.getRevNetAmount().setAmount(netAmount);
					}
					 */
					cCADetails=new CCADetail(detailsVO);
					//To set revised net amount
					cCADetails.setRevNetAmount(netAmount);

				}

				else{
					cCADetails = new CCADetail(detailsVO);
				}
			}


		}
		else{
			try {
				cCADetails = CCADetail.find(detailsVO);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				cCADetails = new CCADetail(detailsVO);
			}
			if (OPERATION_FLAG_UPDATE.equals(detailsVO.getOperationFlag())){
				computeTax(detailsVO);//Added by A-6991 for ICRD-215015
				cCADetails.update(detailsVO);
			}else if (OPERATION_FLAG_DELETE.equals(detailsVO.getOperationFlag())){
				cCADetails.remove();
			}
		}
		
		//Modified by A-7794 as part of MRA revamp
		if(cCADetails!=null){
			if(cCADetails.getCCADetailsPK().getMcaRefNumber()!=null){
				ccaRefNo = cCADetails.getCCADetailsPK().getMcaRefNumber();
			}
		}

		detailsVO.setCcaRefNumber(ccaRefNo);
		return ccaRefNo;
	}

	/**
	 * method for calculating tax and tds.
	 *
	 * @param detailsVO the details vo
	 * @throws SystemException the system exception
	 */
	private void computeTax(CCAdetailsVO detailsVO) throws SystemException {
		HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails=findTaxDetailForMCA(detailsVO);
		updateBillingEntryWithTaxForMCA(detailsVO,taxDetails);

	}

	/**
	 * Trigger proration.
	 *
	 * @param detailsVO the details vo
	 * @throws SystemException the system exception
	 * @author a-4823
	 */
	private void triggerProration(CCAdetailsVO detailsVO) throws SystemException {
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "triggerProration");
			mraDefaultsDao.triggerProration(detailsVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

	}

	/**
	 * Update billing entry with tax for mca.
	 *
	 * @param detailsVO the details vo
	 * @param taxDetailsMap the tax details map
	 * @throws SystemException the system exception
	 */
	 //A-6991
	/*private void updateBillingEntryWithTaxForMCA(CCAdetailsVO detailsVO,
			HashMap<String, HashMap<String, Collection<TaxVO>>> taxDetailsMap) throws SystemException {
		HashMap<String ,Collection<TaxVO>>  taxDetails = null;
		Collection<TaxVO> taxVOs = null;
		if(taxDetailsMap!=null && taxDetailsMap.size() > 0){
			for(String key : taxDetailsMap.keySet() ){
				if(key.equals(detailsVO.getBillingBasis())){
					taxDetails = taxDetailsMap.get(key);
					for(String configType : taxDetails.keySet()){
						if(TaxFilterVO.CONFIGURATIONTYPE_TAX.equals(configType)){
							taxVOs = taxDetails.get(configType);
							if(taxVOs!=null){
								for(TaxVO taxVO : taxVOs){
									if(TaxFilterVO.CONFIGURATIONCODE_STWTCHG.equals(taxVO.getConfigurationCode()) ){
										if(detailsVO.getContCurCode().equals(detailsVO.getRevContCurCode()) && !"N".equalsIgnoreCase(detailsVO.getOverrideRounding())){
										detailsVO.setRevTax(
												taxVO.getTotalTaxAmount().getAmount()); //Modified by A-6991 for Bug ICRD-213897
									}
										else{
										if(detailsVO.getContCurCode().equals(detailsVO.getRevContCurCode()) ){
										detailsVO.setRevTax(
													taxVO.getTotalTaxAmount().getRoundedAmount());
											}
									}
									}
								}
							}
						}else if(TaxFilterVO.CONFIGURATIONTYPE_TDS.equals(configType)){
							taxVOs = taxDetails.get(configType);
							if(taxVOs!=null){
								for(TaxVO taxVO : taxVOs){
									if(TaxFilterVO.CONFIGURATIONCODE_TDSDUEGPA.equals(taxVO.getConfigurationCode()) ){
										detailsVO.setRevTds(taxVO.getTotalTaxAmount().getRoundedAmount());
									}
								}
							}
						}
					}
				}
			}
		}
		if(APPROVED.equals(detailsVO.getCcaStatus())){
			updateCCADetailEntity(detailsVO);
		}

	}*/
	//Commented and Added by A-6991 for ICRD-213422
	//modified by a-7871 for ICRD-255961
	private void updateBillingEntryWithTaxForMCA(CCAdetailsVO detailsVO,HashMap<String, HashMap<String, Collection<TaxVO>>> taxDetailsMap) throws SystemException {
		log.entering(CLASS_NAME, "updateBillingEntryWithTaxForMCA");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		double taxPercentage=0.0;
		double tdsPercentage=0.0;
		/*String value=null;
		double revTax;
		GeneralParameterConfigurationVO configVO = new GeneralParameterConfigurationVO();
		configVO.setCompanyCode(logonAttributes.getCompanyCode());
		configVO.setMasterType(MRA_CONFIGURATION);
		configVO.setConfigurationReferenceOne(TAX_GROUP);
		configVO.setParmeterCode(TAX_VALUE);
		SharedDefaultsProxy proxy = new SharedDefaultsProxy();
		Map<String, HashMap<String, String>> configParams = null;
		try{
			configParams = proxy.findGeneralParameterConfigurationDetails(configVO);
		}catch(Exception exception){
			log.log(Log.FINE,exception.getMessage());
		}
		if(configParams!=null && !configParams.isEmpty() && configParams.containsKey(TAX_GROUP)){
			Map<String, String> map = configParams.get(TAX_GROUP);
			if(map!=null && !map.isEmpty() && map.containsKey(TAX_VALUE)){
				//if(detailsVO.getDomesticFlag().equalsIgnoreCase(DOMESTIC))
				//{//added by a-7871 for --TK bug
				 value = map.get(TAX_VALUE);
				 revTax=(detailsVO.getRevChgGrossWeight().getAmount())*(Double.parseDouble(value)/100);
				//}
				//else
				//{
					// revTax=0;
				//}
				double revChgGrosswt=(detailsVO.getRevChgGrossWeight().getAmount())*(Double.parseDouble(value)/100);//modified by a-7871 for ICRD-214766
				double revTax=getScaledValue(revChgGrosswt,Integer.parseInt(detailsVO.getOverrideRounding()));
				detailsVO.setRevTax(revTax);
				
			}
		}*/
		
		//added by a-7871 for ICRD-255961 starts-----
		HashMap<String ,Collection<TaxVO>>  taxDetails = null;
		Collection<TaxVO> taxVOs = null;
		if(taxDetailsMap!=null && taxDetailsMap.size() > 0){
			for(String key : taxDetailsMap.keySet() ){
				if(key.equals(detailsVO.getBillingBasis())){
					taxDetails = taxDetailsMap.get(key);
					for(String configType : taxDetails.keySet()){
						if(TaxFilterVO.CONFIGURATIONTYPE_TAX.equals(configType)){
							taxVOs = taxDetails.get(configType);
							if(taxVOs!=null){
								for(TaxVO taxVO : taxVOs){
									if(TaxFilterVO.CONFIGURATIONCODE_STWTCHG.equals(taxVO.getConfigurationCode()) ){
										if(detailsVO.getContCurCode().equals(detailsVO.getRevContCurCode()) ){
											if(taxVO.getTaxConfigurationVO().getTaxDetailsVo().getPercentageValue()!=null){
												taxPercentage = Double.parseDouble(taxVO.getTaxConfigurationVO().
														getTaxDetailsVo().getPercentageValue())/100;
												detailsVO.setRevTax(
														detailsVO.getRevChgGrossWeight().getAmount()*taxPercentage);
											}

										}
										if(TaxFilterVO.CONFIGURATIONCODE_STVALCHG.equals(taxVO.getConfigurationCode()) ){
											detailsVO.setRevTax(
													detailsVO.getRevChgGrossWeight().getAmount()*taxPercentage);

										}
										if(TaxFilterVO.CONFIGURATIONCODE_ST.equals(taxVO.getConfigurationCode()) ){
											detailsVO.setRevTax(
													detailsVO.getRevChgGrossWeight().getAmount()*taxPercentage);
										}
									}
								}
							}else if(TaxFilterVO.CONFIGURATIONTYPE_TDS.equals(configType)){
								taxVOs = taxDetails.get(configType);
								if(taxVOs!=null){
									for(TaxVO taxVO : taxVOs){
										if(TaxFilterVO.CONFIGURATIONCODE_TDSDUEGPA.equals(taxVO.getConfigurationCode()) ){
											detailsVO.setTds(taxVO.getTotalTaxAmount().getRoundedAmount());
											if(taxVO.getTaxConfigurationVO().getTaxDetailsVo().getPercentageValue()!=null){
												tdsPercentage = Double.parseDouble(taxVO.getTaxConfigurationVO().
														getTaxDetailsVo().getPercentageValue());
												detailsVO.setRevTds(detailsVO.getRevChgGrossWeight().getAmount()*tdsPercentage);
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
		//added by a-7871 for ICRD-255961 ends-----		
		log.exiting(CLASS_NAME, "updateBillingEntryWithTaxForMCA");
	
		if(APPROVED.equals(detailsVO.getCcaStatus())){
			updateCCADetailEntity(detailsVO);
		}

	}

	/**
	 * Update cca detail entity.
	 *
	 * @param detailsVO the details vo
	 * @throws SystemException the system exception
	 */
	private void updateCCADetailEntity(CCAdetailsVO detailsVO) throws SystemException {
		double netAmount = 0.0;
		CCADetail ccaDetail=null;
		//Added by a-4823 for bug ICRD-20500
		//Implementing rounding
		Money tds = null;
		Money serviceTax = null;
		Money wtChg = null;
		Money surChg = null;

		try {
			ccaDetail = CCADetail.find(detailsVO);
			
			String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
			Map<String, String> systemParameters = null;
			Collection<String> systemParameterCodes = new ArrayList<String>();
			SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
			systemParameterCodes.add(SYS_PAR_OVERRIDE_ROUNDING);
			try {
				systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
			} catch (ProxyException e) {
				e.getMessage();
			}
			if (systemParameters != null) {
				detailsVO.setOverrideRounding(systemParameters.get(SYS_PAR_OVERRIDE_ROUNDING));
			}
			
			//ccaDetail.update(detailsVO);
			if(ccaDetail.getRevContCurCod()!=null && ccaDetail.getRevContCurCod().trim().length()>0){
				try {
					wtChg=CurrencyHelper.getMoney(ccaDetail.getRevContCurCod());
					serviceTax=CurrencyHelper.getMoney(ccaDetail.getRevContCurCod());
					tds=CurrencyHelper.getMoney(ccaDetail.getRevContCurCod());
					surChg=CurrencyHelper.getMoney(ccaDetail.getRevContCurCod());
					wtChg.setAmount(ccaDetail.getRevWtChargeCTR());
					serviceTax.setAmount(detailsVO.getRevTax());
					tds.setAmount(detailsVO.getRevTds());
					surChg.setAmount(ccaDetail.getRevSurChargeCTR());

				} catch (CurrencyException e) {
					// TODO Auto-generated catch block

				}
			}
			if(wtChg!=null && serviceTax!=null && tds!=null){
				if(!"N".equalsIgnoreCase(detailsVO.getOverrideRounding())){
					netAmount = wtChg.getAmount()+ serviceTax.getAmount()+surChg.getAmount() - tds.getAmount();
					if(detailsVO.getValChgUpdAmount()!=null && detailsVO.getValChgUpdAmount().getAmount()>0){
						netAmount = netAmount+detailsVO.getValChgUpdAmount().getAmount()+surChg.getAmount()-wtChg.getAmount();
					}
					ccaDetail.setRevWtChargeCTR(getScaledValue(wtChg.getAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));//modified by a-7871 for ICRD-214766
					ccaDetail.setRevNetAmount(netAmount);
					if(ccaDetail.getContCurCode().equals(ccaDetail.getRevContCurCod())){
					ccaDetail.setRevSrvTax(getScaledValue(serviceTax.getAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));//Modified by A-6991 for Bug ICRD-213897 //modified by a-7871 for ICRD-214766
					}
					ccaDetail.setRevTds(getScaledValue(tds.getAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));//modified by a-7871 for ICRD-214766
				}
				else{
				netAmount = wtChg.getRoundedAmount()+ serviceTax.getRoundedAmount()+surChg.getRoundedAmount() - tds.getRoundedAmount();
				if(detailsVO.getValChgUpdAmount()!=null && detailsVO.getValChgUpdAmount().getAmount()>0){
					netAmount = netAmount+detailsVO.getValChgUpdAmount().getRoundedAmount()+surChg.getRoundedAmount()-wtChg.getRoundedAmount();
				}
				ccaDetail.setRevNetAmount(netAmount);
				if(!"N".equalsIgnoreCase(detailsVO.getOverrideRounding())){
				ccaDetail.setRevWtChargeCTR(getScaledValue(wtChg.getAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));
				ccaDetail.setRevSrvTax(getScaledValue(serviceTax.getAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));
				ccaDetail.setRevTds(getScaledValue(tds.getRoundedAmount(),Integer.parseInt(detailsVO.getOverrideRounding())));//modified by a-7871 for ICRD-214766
				}else{
					ccaDetail.setRevWtChargeCTR(wtChg.getAmount());
					ccaDetail.setRevSrvTax(serviceTax.getAmount());
					ccaDetail.setRevTds(tds.getRoundedAmount());//modified by a-7871 for ICRD-214766	
				}
				
				}
			}



		} catch (FinderException e) {
			if(detailsVO.getRevContCurCode()!=null && detailsVO.getRevContCurCode().trim().length()>0){
				try {

					serviceTax=CurrencyHelper.getMoney(detailsVO.getRevContCurCode());
					tds=CurrencyHelper.getMoney(detailsVO.getRevContCurCode());

					serviceTax.setAmount(detailsVO.getRevTax());
					tds.setAmount(detailsVO.getRevTds());

				} catch (CurrencyException ex) {
					// TODO Auto-generated catch block

				}
			}
			if(!"N".equalsIgnoreCase(detailsVO.getOverrideRounding())){
				if(detailsVO.getRevChgGrossWeight()!=null && serviceTax!=null && tds!=null){
					netAmount=detailsVO.getRevChgGrossWeight().getAmount()+ serviceTax.getAmount()+detailsVO.getOtherRevChgGrossWgt().getAmount() - tds.getAmount();
				}
				if(detailsVO.getRevNetAmount()!=null){
					detailsVO.getRevNetAmount().setAmount(netAmount);
				}	
			}
			else{
			if(detailsVO.getRevChgGrossWeight()!=null && serviceTax!=null && tds!=null){
				netAmount=detailsVO.getRevChgGrossWeight().getRoundedAmount()+ serviceTax.getRoundedAmount()+detailsVO.getOtherRevChgGrossWgt().getRoundedAmount() - tds.getRoundedAmount();
			}
			if(detailsVO.getRevNetAmount()!=null){
				detailsVO.getRevNetAmount().setAmount(netAmount);
			}
			}
			ccaDetail=new CCADetail(detailsVO);

		}


	}

	/**
	 * Find tax detail for mca.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @return the hash map
	 * @throws SystemException the system exception
	 */
	private HashMap<String ,HashMap<String ,Collection<TaxVO>>>
	findTaxDetailForMCA(CCAdetailsVO ccAdetailsVO)throws SystemException{
		HashMap<String,TaxFilterVO> taxFilterMap = constructTaxFilterMapForMCA(ccAdetailsVO);
		HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails = null;
		// taxDetails - String Will be Key(billing basis), Second String will be type.. ie TAX,TDS, then Collection<TaxVO>
		try {
			taxDetails = new TariffTaxProxy().computeTax(taxFilterMap);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		return taxDetails;
	}

	/**
	 * Construct tax filter map for mca.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @return the hash map
	 */
	private HashMap<String, TaxFilterVO> constructTaxFilterMapForMCA(
			CCAdetailsVO ccAdetailsVO) {
		HashMap<String,TaxFilterVO> taxFilterMap = new HashMap<String, TaxFilterVO>();
		TaxFilterVO taxFilterVO = new TaxFilterVO();
		HashMap<String , Collection<String> > taxConfigurationDetails = new HashMap<String, Collection<String>>();
		HashMap<String ,Collection<String> >  parameterMap =  new HashMap<String, Collection<String>>();
		HashMap<String , ChargeDetailsVO> chargedetailsMap = new HashMap<String, ChargeDetailsVO>();
		ChargeDetailsVO chargeDetailsVO = null;
		//ChargeDetailsVO chargeDetailsVO1 = null;

		Collection<String> configurationCodes = new ArrayList<String>();
		Collection<String> configurationCodesTDS = new ArrayList<String>();
		Collection<String> agentPatameters = new ArrayList<String>();
		Money weightCharge = null;
		Collection<String> stationPatameters = new ArrayList<String>();
		/**
		 * TODO to check . Whether this is billing currency code itself
		 */
		//#1
		taxFilterVO.setCompanyCode(ccAdetailsVO.getCompanyCode());
		taxFilterVO.setCountryCode(ccAdetailsVO.getCountryCode());
		taxFilterVO.setOrigin(ccAdetailsVO.getOriginCode());
		taxFilterVO.setDestination(ccAdetailsVO.getDestnCode());
		taxFilterVO.setDateOfJourney(new LocalDate(LocalDate.NO_STATION, Location.NONE,false).setDate(ccAdetailsVO.getDsnDate()));
		taxFilterVO.setCurrencyCode(ccAdetailsVO.getRevContCurCode());
		taxFilterVO.setCargoType(TaxFilterVO.CARGOTYPE_MAIL);
		//On MCA Approval to be checked whether Exemption balance to be updated
		if(APPROVED.equals(ccAdetailsVO.getCcaStatus())){
			taxFilterVO.setExemptionBalanceToBeUpdated(true);
		}
		else{
			taxFilterVO.setExemptionBalanceToBeUpdated(false);
		}
		//#2
		configurationCodes.add(TaxFilterVO.CONFIGURATIONCODE_STWTCHG);
		taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TAX, configurationCodes);
		configurationCodesTDS.add(TaxFilterVO.CONFIGURATIONCODE_TDSDUEGPA);
		/*
		 * TODO check if this is only  TDS DUE GPA only
		 */
		//configurationCodesTDS.add(TaxFilterVO.CONFIGURATIONCODE_TDSDUECAR);
		taxConfigurationDetails.put(TaxFilterVO.CONFIGURATIONTYPE_TDS, configurationCodesTDS);
		//tds
		taxFilterVO.setTaxConfigurationDetails(taxConfigurationDetails);
		taxFilterVO.setBilledFrom(ccAdetailsVO.getSectFrom());
		taxFilterVO.setBilledTo(ccAdetailsVO.getSectTo());
		agentPatameters.add(ccAdetailsVO.getRevGpaCode());
		parameterMap.put(TaxFilterVO.PARAMETERCODE_GPA, agentPatameters);
		//stationPatameters.add();
		//parameterMap.put(TaxFilterVO.PARAMETERCODE_STNCOD, stationPatameters);
		taxFilterVO.setParameterMap(parameterMap);
		chargeDetailsVO =  new ChargeDetailsVO();
		chargeDetailsVO.setBasis("WTCHG");
		if(ccAdetailsVO.getValChgUpdAmount()!=null && ccAdetailsVO.getValChgUpdAmount().getAmount()>0)
			{
			chargeDetailsVO.setBasisTotalAmount(ccAdetailsVO.getValChgUpdAmount());
			}
		else
		{
		chargeDetailsVO.setBasisTotalAmount(ccAdetailsVO.getRevChgGrossWeight());
		}

		/**chargeDetailsVO1 =  new ChargeDetailsVO();
		chargeDetailsVO1.setBasis("STVALCHG");
		chargeDetailsVO1.setBasisTotalAmount(valuationchg);*/

		chargedetailsMap.put("WTCHG",chargeDetailsVO);
		taxFilterVO.setChargedetailsMap(chargedetailsMap);
		taxFilterMap.put(ccAdetailsVO.getBillingBasis(), taxFilterVO);
		return taxFilterMap;
	}


	/**
	 * Save cc adetails.
	 *
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3447
	 */
	public  String saveCCAdetails(CCAdetailsVO detailsVO) throws SystemException,MailTrackingMRABusinessException{
		CCADetail cCADetails = null;


		/*
		 * If cca is actual calculate netdue airline in usd ie RevdueArl - Duearl
		 * find exchange rate to convert from contract currency to usd
		 */

		if(detailsVO.getCcaType()!=null && "A".equals(detailsVO.getCcaType())){
			Money netDueArlUSD = null;
			Double usdConRate=0.0;
			Double netDueArlUSDTmp=0.0;
			try {
				netDueArlUSD = CurrencyHelper.getMoney("NZD");
				netDueArlUSDTmp =(detailsVO.getRevDueArl().getAmount()-detailsVO.getDueArl().getAmount());
				if(!MRAConstantsVO.CURRENCY_USD.equalsIgnoreCase(detailsVO.getContCurCode())){
					usdConRate=findExchangeRateForRateAuditDetails(detailsVO.getCompanyCode(),detailsVO.getContCurCode(),
							MRAConstantsVO.CURRENCY_USD,
							MRAConstantsVO.FIVE_DAY_RATE,//"USD",
							new LocalDate(LocalDate.NO_STATION,Location.NONE, false).addMonths(-1));
					netDueArlUSD.setAmount(netDueArlUSDTmp*usdConRate);
				}else{
					netDueArlUSD.setAmount(netDueArlUSDTmp);
				}

				detailsVO.setNetDueArlUSD(netDueArlUSD);
			}catch (CurrencyException e) {
				log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
				e.getErrorCode();
			}
		}


		//save,update or delete -->>
		if (OPERATION_FLAG_INSERT.equals(detailsVO.getOperationFlag())) {
			cCADetails = new CCADetail(detailsVO);
		}
		else{
			try {
				cCADetails = CCADetail.find(detailsVO);
			} catch (FinderException e) {
				// TODO Auto-generated catch block

			}
			if (OPERATION_FLAG_UPDATE.equals(detailsVO.getOperationFlag())){

				cCADetails.update(detailsVO);

			}else if (OPERATION_FLAG_DELETE.equals(detailsVO.getOperationFlag())){
				cCADetails.remove();
			}
		}


		/**
		 * added by A-2565 Meenu for triggering flown and interline provision accounting
		 */
		String sysparValue=null;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		//system parameter for accounting
		systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
		//the system parameter for audit  value Y for activate audit and N for deactivate audit
		String sysparCode = "mailtracking.mra.dsnauditrequired";
		systemParameterCodes.add (sysparCode);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		if(!FROM_RATEAUDIT.equals(detailsVO.getFromRateAudit())){
			if (systemParameters != null) {
				String accountingEnabled = (systemParameters.get(SYS_PARA_ACCOUNTING_ENABLED));
				log.log(Log.FINE, " accountingEnabled==================> ",
						accountingEnabled);
				sysparValue = systemParameters.get (sysparCode);
				//if CCA is actual and accounting enabled
				if("A".equals(detailsVO.getCcaType()) && "Y".equals(accountingEnabled)){
					MRABillingMaster mRABillingMaster = new MRABillingMaster() ;

					//mRABillingMaster =	MRABillingMaster.find(detailsVO.getCsgSequenceNumber(), detailsVO.getCsgDocumentNumber(), detailsVO.getCompanyCode(), detailsVO.getBillingBasis(), detailsVO.getGpaCode());
					RateAuditVO rateAuditVO =new RateAuditVO();
					rateAuditVO.setConDocNum(detailsVO.getCsgDocumentNumber());
					rateAuditVO.setConSerNum(detailsVO.getCsgSequenceNumber());
					rateAuditVO.setBillingBasis(detailsVO.getBillingBasis());
					rateAuditVO.setCompanyCode(detailsVO.getCompanyCode());
					rateAuditVO.setGpaCode(detailsVO.getPoaCode());
					//mRABillingMaster.triggerFlownAndInterlineProvAccounting(rateAuditVO);

				}
			}
		}

		String ccaRefNo = "INVALID";
		if(cCADetails!=null){
//			if(cCADetails.getUsrccanum()!=null){
//				ccaRefNo = cCADetails.getUsrccanum();
//			}
				
				ccaRefNo = cCADetails.getCCADetailsPK().getMcaRefNumber();
		
		}
		/*
		 * Trigerring WorFlow
		 */
		if ( cCADetails != null &&
				((OPERATION_FLAG_INSERT.equals(detailsVO.getOperationFlag())) ||
						(OPERATION_FLAG_UPDATE.equals(detailsVO.getOperationFlag())))){
			detailsVO.setCcaRefNumber(ccaRefNo);
			log.log(Log.FINE,"\n\nWorkFlow Trigerred ...........\n\n");
			if("P".equals(detailsVO.getCcaType())){
				//proforma work flow start--->>
				new WorkflowDefaultsProxy().startWorkflow(detailsVO);
			}
		}
		/*
		 * updated by meenu for Auditing AirNZ681
		 **/

		if (systemParameters != null) {
			log.log(Log.FINE, " sysparValue==================> ", sysparValue);
			//if value is Y then call the audit method in helper
			if ("Y".equals (sysparValue)){
				AuditHelper.auditDSNForCCAIssue(detailsVO);
			}
		}

		return ccaRefNo;

	}




	/**
	 * *.
	 *
	 * @param maintainCCAFilterVO return Collection<GPABillingDetailsVO>
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-3447
	 */
	public  Collection<CCAdetailsVO> findCCAdetails(MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException{

		return CCADetail.findCCAdetails(maintainCCAFilterVO);

	}

	/**
	 * List cc as.
	 *
	 * @param listCCAFilterVo the list cca filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-3429
	 */
	public Page<CCAdetailsVO> listCCAs(ListCCAFilterVO listCCAFilterVo) throws SystemException{

		return CCADetail.listCCAs(listCCAFilterVo);

	}

	/**
	 * List proration details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException the system exception
	 * @author A-2554
	 */
	public Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException {

		return MRABillingMaster.listProrationDetails(prorationFilterVO);

	}




	//	 Added by Deepthi for AirNZ173
	/**
	 * Prints the cc as report.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	public Map<String, Object> printCCAsReport(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {

		ListCCAFilterVO listCCAFilterVO = (ListCCAFilterVO) reportSpec
		.getFilterValues().iterator().next();

		Collection<CCAdetailsVO>  cCADetailsVOs= CCADetail.listCCAforPrint(listCCAFilterVO);

		log.log(Log.INFO, "data cCADetailsVOs1234----->", cCADetailsVOs);
		log.log(Log.INFO, "outside null loop---->");

		if (cCADetailsVOs == null || cCADetailsVOs.size() <= 0) {
			log.log(Log.INFO, "inside null loop---->");
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_DEFAULTS_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}
		Map<String,String> systemParameterMap =null; //Added by A-8164 for ICRD-272214 starts
		Collection<String> systemParCodes = new ArrayList<String>();   
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");        
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) { 
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();   
		}
		for(CCAdetailsVO cCAdetailsVOForIterate:cCADetailsVOs){
			cCAdetailsVOForIterate.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));     
		} //Added by A-8164 for ICRD-272214 ends 
		log.log(Log.INFO, "filter vo!@#&----->", listCCAFilterVO);
		reportSpec.addParameter(listCCAFilterVO);
		reportSpec.setData(cCADetailsVOs);
		return ReportAgent.generateReport(reportSpec);

	}

	// Added by Deepthi for AirNZ173 ends

	/**
	 * *.
	 *
	 * @param airlineBillingFilterVO return DocumentBillingDetailsVO
	 * @return the page
	 * @throws SystemException the system exception
	 * @author A-3434
	 */
	public  Page<DocumentBillingDetailsVO> findInterlineBillingEntries(AirlineBillingFilterVO airlineBillingFilterVO)
	throws SystemException{

		return MRABillingMaster.findInterlineBillingEntries(airlineBillingFilterVO);

	}

	/**
	 * *.
	 *
	 * @param documentBillingDetailsVOs the document billing details v os
	 * @throws SystemException the system exception
	 * @throws CreateException the create exception
	 * @author A-3434
	 */
	@Advice(name = "mail.mra.changeMailbagStatus" , phase=Phase.POST_INVOKE)
	public void changeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
	throws SystemException,CreateException{

		MRABillingMaster  mraBillingMaster=new MRABillingMaster();
		try{
			mraBillingMaster.updateBillingStatus(documentBillingDetailsVOs);
		}
		catch (FinderException e) {
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}
		catch (CreateException e) {
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}

		catch (ChangeStatusException e) {
			e.getMessage();
			throw new SystemException(e.getMessage());
		}

	}

	/**
	 * *.
	 *
	 * @param documentBillingDetailsVOs the document billing details v os
	 * @throws SystemException the system exception
	 * @author A-3434
	 */
	@Advice(name = "mailtracking.mra.changeReview" , phase=Phase.POST_INVOKE)
	public void changeReview(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
	throws SystemException{

		MRABillingDetails  mraBillingDetails = null;
		MRABillingMaster  mraBillingMaster = null;
		CCADetail  ccaDetail = null;
		RejectionMemo rejectionMemo = null;
		Collection<CCADetail> ccaDetailses = null;
		for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){
			long mailSequenceNumber=documentBillingDetailsVO.getMailSequenceNumber();
			Integer serNumber=documentBillingDetailsVO.getSerialNumber();
			Integer csgSeqNumber=documentBillingDetailsVO.getCsgSequenceNumber();
			String csgDocumentNumber=documentBillingDetailsVO.getCsgDocumentNumber();
			String billingBasis=documentBillingDetailsVO.getBillingBasis();
			String companyCode=documentBillingDetailsVO.getCompanyCode();
			String poacode=documentBillingDetailsVO.getPoaCode();
			String ccaRefnum=documentBillingDetailsVO.getCcaRefNumber();
			LocalDate lastUpdatedTime=documentBillingDetailsVO.getLastUpdatedTime();
			int airlineIdr=documentBillingDetailsVO.getAirlineIdr();
			/*
			 * updating REVFLG in MTKMRABLGDTL
			 */
			if(serNumber != 0){

				log.log(Log.INFO, "serNumber---->", serNumber);
				try{
					mraBillingMaster=MRABillingMaster.find( companyCode,mailSequenceNumber) ;
				}
				catch (SystemException e) {
					e.getMessage();
					throw new SystemException(e.getMessage());
				}
				catch (FinderException finderException) {
					throw new SystemException(finderException.getMessage(),
							finderException);
				}
				try{
					mraBillingDetails=MRABillingDetails.find(companyCode, mailSequenceNumber,serNumber) ;
				}
				catch (SystemException e) {
					e.getMessage();
					throw new SystemException(e.getMessage());
				}
				catch (FinderException finderException) {
					throw new SystemException(finderException.getMessage(),
							finderException);
				}


				mraBillingDetails.setRevFlag("Y");

				/* Finding ccadetails of the despatch
				 * if it contains actual and billable cca update REVFLG
				 */

				ccaDetailses = mraBillingMaster.getCCADetail();
				log.log(Log.INFO, "ccaDetailses---->", ccaDetailses);
				if(ccaDetailses != null && ccaDetailses.size() > 0){
					for(CCADetail ccaDetails: ccaDetailses ){
						if("A".equals(ccaDetails.getMcaStatus()) && "OB".equals(ccaDetails.getMcaStatus())){
							if(lastUpdatedTime!=null){
								ccaDetails.setLastUpdatedTime(lastUpdatedTime);
							}
							//ccaDetails.setReviewFlag("Y");
						}
					}
				}
			

			/*
			 * updating REVFLG in MTKARLMEM
			 */

			else if("Y".equals(documentBillingDetailsVO.getMemoFlag()))  {
				try{
					rejectionMemo=RejectionMemo.find(companyCode, airlineIdr, ccaRefnum);

				}
				catch (SystemException e) {
					e.getMessage();
					throw new SystemException(e.getMessage());
				}
				catch (FinderException finderException) {
					throw new SystemException(finderException.getMessage(),
							finderException);
				}


				rejectionMemo.setRevFlag("Y");

				if(lastUpdatedTime!=null){
					rejectionMemo.setLastUpdatedTime(lastUpdatedTime);
				}
			}
				
			}//Modified as part of ICRD-228632
			/*
			 * updating REVFLG in MTKMRACCADTL
			 */

			else{
				CCAdetailsVO cCAdetailsVO=new CCAdetailsVO();
				cCAdetailsVO.setCompanyCode(companyCode);
				cCAdetailsVO.setCcaRefNumber(ccaRefnum);
				cCAdetailsVO.setBillingBasis(billingBasis);
				cCAdetailsVO.setCsgSequenceNumber(csgSeqNumber);
				cCAdetailsVO.setCsgDocumentNumber(csgDocumentNumber);
				cCAdetailsVO.setPoaCode(poacode);

				try{
					try {
						ccaDetail=CCADetail.find(cCAdetailsVO);
					} catch (FinderException e) {
						// TODO Auto-generated catch block

					}
				}
				catch (SystemException e) {
					e.getMessage();
					throw new SystemException(e.getMessage());
				}


				if(lastUpdatedTime!=null){
					ccaDetail.setLastUpdatedTime(lastUpdatedTime);
				}


				//ccaDetail.setReviewFlag("Y");

			}
		}
		
	}

	/**
	 * Save proration details.
	 *
	 * @param prorationDetailsVOs the proration details v os
	 * @throws SystemException the system exception
	 * @throws FinderException the finder exception
	 * @author A-3229
	 */
	public  void saveProrationDetails(Collection<ProrationDetailsVO> prorationDetailsVOs) throws SystemException,FinderException{

		log.entering("MRA Defaults controller", "saveProrationDetails");
		MRABillingMaster mraBillingMaster=null;
		MRABillingDetails mraBillingDetails =null;
		boolean count=false;
		if(prorationDetailsVOs!=null && prorationDetailsVOs.size()>0){

			for(ProrationDetailsVO prorationDetailsVo:prorationDetailsVOs){

				if ("I".equals(prorationDetailsVo.getOperationFlag())) {
					mraBillingMaster.saveProrationDetails(prorationDetailsVo);

				}

				if (OPERATION_FLAG_UPDATE.equals(prorationDetailsVo.getOperationFlag())) {
					MRABillingMasterPK mraBillingMasterPK =new MRABillingMasterPK();
					mraBillingMasterPK.setCompanyCode(prorationDetailsVo.getCompanyCode());
					mraBillingMaster.setMailIdentifier(prorationDetailsVo.getBillingBasis());
					mraBillingMaster.setConsignmentDocNumber(prorationDetailsVo.getConsigneeDocumentNumber());
					mraBillingMaster.setConsignmentSeqNumber(Integer.parseInt(prorationDetailsVo.getConsigneeSequenceNumber()));

					/*mraBillingMaster = MRABillingMaster.find(Integer.parseInt(csgSeqNo),Integer.parseInt(csgDocNo),
						prorationDetailsVo.getCompanyCode(),prorationDetailsVo.getBillingBasis(),prorationDetailsVo.getPostalAuthorityCode());
				if(mraBillingMaster!=null){*/

					mraBillingDetails =MRABillingDetails.find(prorationDetailsVo.getCompanyCode(), prorationDetailsVo.getMailSequenceNumber(),prorationDetailsVo.getSerialNumber());

					if(mraBillingDetails!=null){
						log.log(Log.INFO, "UPDATEEEEEEEEEEEEEEEEEe",
								mraBillingDetails);
						mraBillingDetails.setFlightCarrierCode(prorationDetailsVo.getCarrierCode());
						mraBillingDetails.setSegFrom(prorationDetailsVo.getSectorFrom());
						mraBillingDetails.setSegTo(prorationDetailsVo.getSectorTo());
						//mraBillingDetails.setUpdPieces(prorationDetailsVo.getNumberOfPieces());
						mraBillingDetails.setProrationType(prorationDetailsVo.getProrationType());
						mraBillingDetails.setProratedValue(prorationDetailsVo.getProrationFactor());
						mraBillingDetails.setProrationPercentage(prorationDetailsVo.getProrationPercentage());
						mraBillingDetails.setPaymentFlag(prorationDetailsVo.getPayableFlag());


						mraBillingDetails.setUpdatedGrossWeight(prorationDetailsVo.getWeight());
						mraBillingDetails.setWgtChargeBas(prorationDetailsVo.getProrationAmtInBaseCurr().getAmount());
						mraBillingDetails.setWeightChargeXDR(prorationDetailsVo.getProrationAmtInSdr().getAmount());
						mraBillingDetails.setWgtChargeUsd(prorationDetailsVo.getProrationAmtInUsd().getAmount());
						mraBillingDetails.setWgtChargeBas(prorationDetailsVo.getProratedAmtInCtrCur().getAmount());
						mraBillingDetails.setWgtChargeBas(prorationDetailsVo.getProratedAmtInCtrCur().getAmount());
						//mraBillingDetails.setUpdWgtCharge(prorationDetailsVo.getProratedAmtInCtrCur().getAmount());

					}


				}
				if (OPERATION_FLAG_DELETE.equals(prorationDetailsVo.getOperationFlag())) {
					mraBillingDetails =MRABillingDetails.find(prorationDetailsVo.getCompanyCode(),
							prorationDetailsVo.getMailSequenceNumber(),prorationDetailsVo.getSerialNumber());

					mraBillingDetails.remove();
				}
				/*done by indu for log history*/
				MRABillingDetailsHistory mRABillingDetailsHistory = new MRABillingDetailsHistory(prorationDetailsVo);
				log.log(Log.FINE, "Inserted into History table in MANUAL PRORATION update");
			}

			/*
			 * Trigering Accounting
			 */
			ProrationDetailsVO prorationDtlsVO = ((ArrayList<ProrationDetailsVO>)prorationDetailsVOs).get(0);
			RateAuditVO newRateAuditVO = new RateAuditVO();
			newRateAuditVO.setCompanyCode(prorationDtlsVO.getCompanyCode());
			newRateAuditVO.setConDocNum(prorationDtlsVO.getConsigneeDocumentNumber());
			newRateAuditVO.setConSerNum(Integer.parseInt(prorationDtlsVO.getConsigneeSequenceNumber()));
			newRateAuditVO.setBillingBasis(prorationDtlsVO.getBillingBasis());
			newRateAuditVO.setGpaCode(prorationDtlsVO.getPostalAuthorityCode());

			new MRABillingMaster().triggerFlownAndInterlineProvAccounting(newRateAuditVO);

			log.exiting("MRA Defaults controller", "saveProrationDetails");

		}
	}



	/**
	 * Populate initial data in temp tables.
	 *
	 * @param oldRateAuditVO the old rate audit vo
	 * @throws SystemException the system exception
	 * @author A-3251
	 */
	public  void populateInitialDataInTempTables(RateAuditVO oldRateAuditVO) throws SystemException{

		log.entering("MRA Defaults controller", "populateInitialDataInTempTables");

		//---FLOW 1 :: Save data to Temp Tables
		MRABillingMasterTemp mRABillingMasterTemp =	new MRABillingMasterTemp(oldRateAuditVO);


		log.exiting("MRA Defaults controller", "populateInitialDataInTempTables");
	}


	/**
	 * Compute total for rate audit details.
	 *
	 * @param oldRateAuditVO the old rate audit vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @throws ProrationFactorNotFoundException the proration factor not found exception
	 * @throws MRARateAuditDetailsException the mRA rate audit details exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3251
	 */
	public  RateAuditVO computeTotalForRateAuditDetails(RateAuditVO oldRateAuditVO)
	throws SystemException, ProrationFactorNotFoundException, MRARateAuditDetailsException,MailTrackingMRABusinessException{

		log.entering("MRA Defaults controller", "computeTotalForRateAuditDetails");

		log.log(Log.FINE, "newRateAuditVO#-->>", oldRateAuditVO);
		if(oldRateAuditVO!=null){
			RateAuditVO rateAuditVOAfterParChange = null;

			/*
			 *  1 :: ---IF Billing parameters changed --------------------->>
			 */

			if(oldRateAuditVO.getParChangeFlag().contains("P")){

				/*
				 *---FLOW 1 :: Save data to Temp Tables Done in populateInitialDataInTempTables()
				 *---Flow 2 :: Call procedure update and insert new rows in temp tables
				 */
				String status =  MRABillingMaster.populateDataInTempTables(oldRateAuditVO);
				if( status==null || !"ok".equalsIgnoreCase(status)){
					throw new MRARateAuditDetailsException("mailtracking.mra.defaults.computetotprocfailed");
				}
				//--Flow 3 :: List the new Values R,P1,P2,T1,T2.. from Temp tables
				RateAuditFilterVO rateAuditFilterVO = new RateAuditFilterVO();
				rateAuditFilterVO.setDsnDate(oldRateAuditVO.getDsnDate());
				rateAuditFilterVO.setCompanyCode(oldRateAuditVO.getCompanyCode());
				rateAuditFilterVO.setBillingBasis(oldRateAuditVO.getBillingBasis());
				rateAuditFilterVO.setCsgDocNum(oldRateAuditVO.getConDocNum());
				rateAuditFilterVO.setCsgSeqNum(oldRateAuditVO.getConSerNum());
				rateAuditFilterVO.setGpaCode(oldRateAuditVO.getGpaCode());
				rateAuditVOAfterParChange = findListRateAuditDetailsFromTemp(rateAuditFilterVO);
				log.log(Log.FINE, "rateAuditVOAfterParChange####-->>",
						rateAuditVOAfterParChange);
				//--updating the vo --->>
				if(rateAuditVOAfterParChange!=null){
					oldRateAuditVO.setApplyAutd(rateAuditVOAfterParChange.getApplyAutd());
					oldRateAuditVO.setBillingBasis(rateAuditVOAfterParChange.getBillingBasis());
					oldRateAuditVO.setBillTo(rateAuditVOAfterParChange.getBillTo());
					oldRateAuditVO.setCarrierCode(rateAuditVOAfterParChange.getCarrierCode());
					oldRateAuditVO.setCategory(rateAuditVOAfterParChange.getCategory());
					oldRateAuditVO.setCompanyCode(rateAuditVOAfterParChange.getCompanyCode());
					oldRateAuditVO.setConDocNum(rateAuditVOAfterParChange.getConDocNum());
					oldRateAuditVO.setConSerNum(rateAuditVOAfterParChange.getConSerNum());
					oldRateAuditVO.setDestination(rateAuditVOAfterParChange.getDestination());
					oldRateAuditVO.setDiscrepancy(rateAuditVOAfterParChange.getDiscrepancy());
					oldRateAuditVO.setDsn(rateAuditVOAfterParChange.getDsn());
					oldRateAuditVO.setDsnDate(rateAuditVOAfterParChange.getDsnDate());
					oldRateAuditVO.setDsnStatus(rateAuditVOAfterParChange.getDsnStatus());
					oldRateAuditVO.setFlightCarCod(rateAuditVOAfterParChange.getFlightCarCod());
					oldRateAuditVO.setFlightDate(rateAuditVOAfterParChange.getFlightDate());
					oldRateAuditVO.setFlightNumber(rateAuditVOAfterParChange.getFlightNumber());
					oldRateAuditVO.setGpaCode(rateAuditVOAfterParChange.getGpaCode());
					oldRateAuditVO.setGrossWt(rateAuditVOAfterParChange.getGrossWt());
					oldRateAuditVO.setMalClass(rateAuditVOAfterParChange.getMalClass());
					oldRateAuditVO.setOrigin(rateAuditVOAfterParChange.getOrigin());
					oldRateAuditVO.setPcs(rateAuditVOAfterParChange.getPcs());
					oldRateAuditVO.setPresentWtCharge(rateAuditVOAfterParChange.getPresentWtCharge());
					oldRateAuditVO.setRate(rateAuditVOAfterParChange.getRate());
					oldRateAuditVO.setRateAuditDetails(rateAuditVOAfterParChange.getRateAuditDetails());
					oldRateAuditVO.setRoute(rateAuditVOAfterParChange.getRoute());
					oldRateAuditVO.setSerialNumber(rateAuditVOAfterParChange.getSerialNumber());
					oldRateAuditVO.setSubClass(rateAuditVOAfterParChange.getSubClass());
					oldRateAuditVO.setUld(rateAuditVOAfterParChange.getUld());
					if(!oldRateAuditVO.getParChangeFlag().contains("W")){
						oldRateAuditVO.setUpdWt(rateAuditVOAfterParChange.getUpdWt());
					}
					if(!(oldRateAuditVO.getParChangeFlag().contains("W")&& oldRateAuditVO.getParChangeFlag().contains("C"))){
						oldRateAuditVO.setAuditedWtCharge(rateAuditVOAfterParChange.getAuditedWtCharge());
					}
				}
			}

			/*
			 *  2 :: ----Calculations if gross weight is changed --------------------------->>
			 */
			double newCharge =0.0;
			double discp = 0.0;
			Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
			rateAuditDetailsVOs = oldRateAuditVO.getRateAuditDetails();
			if(oldRateAuditVO.getParChangeFlag().contains("W") && !(oldRateAuditVO.getParChangeFlag().contains("C"))){
				for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
					/*
					 * New manually changed weight will be set to all records in the command classes validate for rate audit details
					 * Autorate command for Maintain cca
					 * So only weight charge need to be updated
					 */
					if(!"T".equals(rateAuditDetailsVO.getPayFlag())){
						newCharge =Double.parseDouble(oldRateAuditVO.getUpdWt())*oldRateAuditVO.getRate();
						discp = newCharge - rateAuditDetailsVO.getPrsntWgtCharge().getAmount();
						oldRateAuditVO.getAuditedWtCharge().setAmount(newCharge);
						oldRateAuditVO.setDiscrepancy(String.valueOf(discp));
						rateAuditDetailsVO.setDiscrepancy(String.valueOf(discp));
					}else{
						//----if only one T record is there then no need to go for computeProrateFactors
						if(oldRateAuditVO.getTRecordCount()==1){
							rateAuditDetailsVO.getAudtdWgtCharge().setAmount(newCharge);
						}
					}
				}
				//----if more than  one T record go for computeProrateFactors
				if(oldRateAuditVO.getTRecordCount()>1)
				{
					oldRateAuditVO = computeProrateFactors(oldRateAuditVO);
				}

				log
						.log(
								Log.FINE,
								"\n\nRateAuditVO After editing gross weight----->>>\n\n",
								oldRateAuditVO);
			}

			/*
			 *  3 :: ----Calculations if Audited weight charge is changed --------------------------------->>
			 */

			if(oldRateAuditVO.getParChangeFlag().contains("C")){
				/*
				 * Take the updated charge from jsp in validate command or Autorate command
				 * For R records and P records new weight charge is updated in validate command
				 * if only one Trecord it is also done in validate command
				 * if more than  one T record go for computeProrateFactors
				 */
				//Added By Deepthi for correcting retention Amount
				double totalPayable = 0.0;
				double totalPayableContract = 0.0;
				double retention=0.0;
				double receivable=0.0;
				double exchgRate = 0.0D;
				String contractCurofReceivable="";
				LocalDate fiveDayRateDte =  null;
				for(RateAuditDetailsVO rateAuditDetailsVO:oldRateAuditVO.getRateAuditDetails()){
					if(PAYABLE.equals(rateAuditDetailsVO.getPayFlag())){
						totalPayable = totalPayable + rateAuditDetailsVO.getWgtChargeUSD();
					}
					if(RECEIVABLE.equals(rateAuditDetailsVO.getPayFlag())){
						receivable=rateAuditDetailsVO.getAudtdWgtCharge().getAmount();
						contractCurofReceivable=rateAuditDetailsVO.getContCurCode();
						fiveDayRateDte = new LocalDate(rateAuditDetailsVO.getRecVDate(), true);
					}
				}
				fiveDayRateDte = fiveDayRateDte.addMonths(-1);
				exchgRate=findExchangeRateForRateAuditDetails(oldRateAuditVO.getCompanyCode(),MRAConstantsVO.CURRENCY_USD,
						contractCurofReceivable,
						MRAConstantsVO.FIVE_DAY_RATE,
						fiveDayRateDte);
				totalPayableContract=totalPayable*exchgRate;
				if(oldRateAuditVO.getTRecordCount()==1){
					retention=receivable-totalPayableContract;
					for(RateAuditDetailsVO rateAuditDetailsVO:oldRateAuditVO.getRateAuditDetails()){
						if(RETENSION.equals(rateAuditDetailsVO.getPayFlag())){
							rateAuditDetailsVO.getAudtdWgtCharge(). setAmount(retention);
						}
					}
				}
				if(oldRateAuditVO.getTRecordCount()>1)
				{
					oldRateAuditVO = computeProrateFactors(oldRateAuditVO);
				}
			}

			rateAuditDetailsVOs = oldRateAuditVO.getRateAuditDetails();
			/*
			 * for converting the changed weight charge into USD,XDR,NSD ------------------------------>>
			 */
			for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){

				String blgcurcod = rateAuditDetailsVO.getContCurCode();
				String companycode = rateAuditDetailsVO.getCompanyCode();
				LocalDate recVDate = rateAuditDetailsVO.getRecVDate();
				Double usdwgtCharge=0.0;
				Double sdrwgtCharge=0.0;
				Double baswgtCharge=0.0;
				Double wgtCharge = rateAuditDetailsVO.getAudtdWgtCharge().getAmount();
				Double amtInUSD  = 0.0;
				String exgRateBasis ;
				LocalDate iHRDate = null;
				LocalDate fDRDate =  null;
				/*
				 * modified by Sandeep (A-2270)..05-Mar-2009
				 */
				iHRDate = rateAuditDetailsVO.getRecVDate();

				fDRDate = new LocalDate(rateAuditDetailsVO.getRecVDate(), true);

				fDRDate = fDRDate.addMonths(-1);
				/*
				 * modified By Sandeep (A-2270) for solving exchange rate issue
				 */

				if(blgcurcod!=null && blgcurcod.trim().length()>0){
					if(!MRAConstantsVO.CURRENCY_USD.equalsIgnoreCase(rateAuditDetailsVO.getContCurCode())){
						usdwgtCharge = findExchangeRateForRateAuditDetails(companycode,blgcurcod,
								MRAConstantsVO.CURRENCY_USD,
								MRAConstantsVO.FIVE_DAY_RATE,
								fDRDate);
						rateAuditDetailsVO.setWgtChargeUSD((usdwgtCharge*wgtCharge));
						amtInUSD = usdwgtCharge*wgtCharge;
					}else{
						rateAuditDetailsVO.setWgtChargeUSD((wgtCharge));
						amtInUSD = wgtCharge;
					}
					//if(!MRAConstantsVO.CURRENCY_XDR.equalsIgnoreCase(rateAuditDetailsVO.getContCurCode())){

					/*sdrwgtCharge = findExchangeRateForRateAuditDetails(companycode,blgcurcod,
									MRAConstantsVO.CURRENCY_XDR,
									MRAConstantsVO.INHOUSE_RATE,
									iHRDate);*/
					sdrwgtCharge = findExchangeRateForRateAuditDetails(companycode,MRAConstantsVO.CURRENCY_USD,
							MRAConstantsVO.CURRENCY_XDR,
							MRAConstantsVO.FIVE_DAY_RATE,
							fDRDate);
					//rateAuditDetailsVO.setWgtChargeSDR((sdrwgtCharge*wgtCharge));
					rateAuditDetailsVO.setWgtChargeSDR((sdrwgtCharge*amtInUSD));
					//}else{
					//rateAuditDetailsVO.setWgtChargeSDR((wgtCharge));
					//}
					//for airNZ BAS is NSD
					//baswgtCharge = findExchangeRateForRateAuditDetails(companycode,blgcurcod,MRAConstantsVO.CURRENCY_NZD,recVDate);
					//changed by Sandeep.T(A-2270) on Feb14th2009
					if(GPAFLG.equals(rateAuditDetailsVO.getGpaarlBillingFlag())
							&&RECEIVABLE.equals(rateAuditDetailsVO.getPayFlag())){
						baswgtCharge = findExchangeRateForRateAuditDetails(companycode,
								blgcurcod,
								MRAConstantsVO.CURRENCY_NZD,
								MRAConstantsVO.INHOUSE_RATE,
								iHRDate);
						rateAuditDetailsVO.setWgtChargeBAS((baswgtCharge*wgtCharge));
					}else if(AIRLINEFLG.equals(rateAuditDetailsVO.getGpaarlBillingFlag())
							&& (PAYABLE.equals(rateAuditDetailsVO.getPayFlag()))||RECEIVABLE.equals(rateAuditDetailsVO.getPayFlag())){
						baswgtCharge = findExchangeRateForRateAuditDetails(companycode,MRAConstantsVO.CURRENCY_USD,
								MRAConstantsVO.CURRENCY_NZD,
								MRAConstantsVO.INHOUSE_RATE,
								iHRDate);

						rateAuditDetailsVO.setWgtChargeBAS((baswgtCharge*amtInUSD));


					}else if(RETENSION.equals(rateAuditDetailsVO.getPayFlag())){
						baswgtCharge = findExchangeRateForRateAuditDetails(companycode,
								blgcurcod,
								MRAConstantsVO.CURRENCY_NZD,
								MRAConstantsVO.INHOUSE_RATE,
								iHRDate);
						rateAuditDetailsVO.setWgtChargeBAS((baswgtCharge*wgtCharge));
					}

				}
			}
			log.log(Log.FINE, "\n\nupdatedRateAuditVOTodisplay###------>>",
					oldRateAuditVO);

		}
		log.exiting("MRA Defaults controller", "computeTotalForRateAuditDetails");
		return oldRateAuditVO;

	}

	/**
	 * Compute prorate factors.
	 *
	 * @param rateAuditVO the rate audit vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @throws ProrationFactorNotFoundException the proration factor not found exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3251
	 */
	public  RateAuditVO computeProrateFactors(RateAuditVO rateAuditVO) throws SystemException , ProrationFactorNotFoundException,MailTrackingMRABusinessException{

		log.entering("MRA Defaults controller", "computeProrateFactors");
		/*
		 * Method to distribute the new charge among the T records if more than one T records are there
		 * Proration factors need to be found
		 */
		Integer proRatFactor = 0;
		double rFactor = 0.0;
		double pFactor = 0.0;
		double rpFactor = 0.0;
		double updAudcharge = 0.0;
		double discp = 0.0;
		Integer divFactor = 0;
		LocalDate fiveDayRateDte =  null;
		double exchgeRate = 0.0D;
		String contractCurofReceivable="";
		double totalPayableContract = 0.0;

		Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = null;
		ArrayList<RateAuditDetailsVO>  tPayFlagVOs = new ArrayList<RateAuditDetailsVO>();
		rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
		for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
			Integer proRatFactors = 0;
			if("R".equals(rateAuditDetailsVO.getPayFlag())){
				rFactor = rateAuditDetailsVO.getAudtdWgtCharge().getAmount();
				fiveDayRateDte = new LocalDate(rateAuditDetailsVO.getRecVDate(), true);
				contractCurofReceivable=rateAuditDetailsVO.getContCurCode();
			}else if("P".equals(rateAuditDetailsVO.getPayFlag())){
				pFactor = pFactor + rateAuditDetailsVO.getWgtChargeUSD();
			}else if("T".equals(rateAuditDetailsVO.getPayFlag())){
				tPayFlagVOs.add(rateAuditDetailsVO);
				RateAuditDetailsVO tOriginDestVo = new RateAuditDetailsVO();
				tOriginDestVo.setCompanyCode(rateAuditDetailsVO.getCompanyCode());
				tOriginDestVo.setSecFrom(rateAuditDetailsVO.getSecFrom());
				tOriginDestVo.setSecTo(rateAuditDetailsVO.getSecTo());
				tOriginDestVo.setRecVDate(rateAuditDetailsVO.getRecVDate());
				proRatFactors = MRABillingMaster.findProrateFactor(tOriginDestVo);
				divFactor=divFactor+proRatFactors;
			}

		}
		//Added By Deepthi
		fiveDayRateDte = fiveDayRateDte.addMonths(-1);
		exchgeRate=findExchangeRateForRateAuditDetails(rateAuditVO.getCompanyCode(),MRAConstantsVO.CURRENCY_USD,
				contractCurofReceivable,
				MRAConstantsVO.FIVE_DAY_RATE,
				fiveDayRateDte);
		totalPayableContract=pFactor*exchgeRate;
		//--to find the division factor ie pf of T orign -->> T dest

		/*int leng = tPayFlagVOs.size()-1;

			RateAuditDetailsVO tOriginDestVo = new RateAuditDetailsVO();
				tOriginDestVo.setCompanyCode(tPayFlagVOs.get(0).getCompanyCode());
				tOriginDestVo.setSecFrom(tPayFlagVOs.get(0).getSecFrom());
				tOriginDestVo.setSecTo(tPayFlagVOs.get(leng).getSecTo());
				tOriginDestVo.setRecVDate(tPayFlagVOs.get(0).getRecVDate());
				divFactor = MRABillingMaster.findProrateFactor(tOriginDestVo);*/

		rpFactor = rFactor - totalPayableContract;

		for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
			if("T".equals(rateAuditDetailsVO.getPayFlag())){
				proRatFactor = MRABillingMaster.findProrateFactor(rateAuditDetailsVO);
				updAudcharge = 	(proRatFactor * rpFactor)/divFactor;
				rateAuditDetailsVO.getAudtdWgtCharge().setAmount(updAudcharge);
				discp = updAudcharge - rateAuditDetailsVO.getPrsntWgtCharge().getAmount();
				rateAuditDetailsVO.setDiscrepancy(String.valueOf(discp));
			}
		}
		log.exiting("MRA Defaults controller", "computeProrateFactors");

		return rateAuditVO;

	}


	


	/**
	 * Find exchange rate for rate audit details.
	 *
	 * @param companyCode the company code
	 * @param blgCurrCod the blg curr cod
	 * @param toCurrCode the to curr code
	 * @param exgRateBasis the exg rate basis
	 * @param recVDate the rec v date
	 * @return Double
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3251
	 */
	private Double findExchangeRateForRateAuditDetails(String companyCode,String blgCurrCod,
			String toCurrCode,String exgRateBasis,LocalDate recVDate)
	throws SystemException,MailTrackingMRABusinessException
	{
		log.entering("MRA Defaults controller", "findExchangeRateForRateAuditDetails");
		/*server call to find exchange rate and billing currency code*/
		Double rate = 0.0;
		Double correctRate = 0.0;
		if(blgCurrCod.equals(toCurrCode)){
			correctRate=1.0;
		}else{
			try {
				CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();
				currencyConvertorVO.setCompanyCode(companyCode);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				currencyConvertorVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
				currencyConvertorVO.setFromCurrencyCode(blgCurrCod);
				currencyConvertorVO.setToCurrencyCode(toCurrCode);
				/*
				 * modified by Sandeep (A-2270)
				 */
				//							if(MRAConstantsVO.CURRENCY_NZD.equalsIgnoreCase(toCurrCode)){
				//								currencyConvertorVO.setRatingBasisType(MRAConstantsVO.INHOUSE_RATE);
				//							}else{
				//								currencyConvertorVO.setRatingBasisType(MRAConstantsVO.FIVE_DAY_RATE);
				//							}

				currencyConvertorVO.setRatingBasisType(exgRateBasis);
				currencyConvertorVO.setValidityStartDate(recVDate);
				currencyConvertorVO.setValidityEndDate(recVDate);
				currencyConvertorVO.setRatePickUpDate(recVDate);
				log.log(Log.INFO, "currencyConvertorVO --->",
						currencyConvertorVO);
				rate = new SharedCurrencyProxy().findConversionRate(currencyConvertorVO);
				if(rate!=0){
					correctRate=(1/rate);
				}

			}
			catch (ProxyException e) {
				log.log(Log.SEVERE,"\n\n\n\n\n\nfindExchangeRate-->>!!!!!! ExchangeRate not found check DB !!!\n\n\n\n\n");
				log.log(Log.SEVERE,
						"\n\n\n\n\n\nfindExchangeRate errorMSG-->>", e.getMessage());
				ErrorVO errorVo = new ErrorVO(
						MailTrackingMRABusinessException.MTK_MRA_DEFAULTS_NO_EXCHANGE_RATE);
				errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
				MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
				mailTrackingMRABusinessException.addError(errorVo);
				throw mailTrackingMRABusinessException;
			}
		}
		log.exiting("MRA Defaults controller", "findExchangeRateForRateAuditDetails");
		log.log(Log.FINE, "EXCHANGED RATE--->>", rate);
		return correctRate;

	}

	/**
	 * Validate mail sub class.
	 *
	 * @param companyCode the company code
	 * @param subclass the subclass
	 * @return the string
	 * @throws SystemException the system exception
	 * @author A-3251
	 */
	public String validateMailSubClass(String companyCode,String subclass) throws SystemException{

		log.entering("MRA Defaults controller", "validateMailSubClass");
		String flag=null;
		MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
		try {
			flag =  mailTrackingDefaultsProxy.validateMailSubClass(companyCode,subclass);
		} catch (ProxyException e) {
			log.log(Log.SEVERE,"ProxyException Occured!!!!");
		}
		log.exiting("MRA Defaults controller", "validateMailSubClass");
		return flag;

	}

	/**
	 * Find sector details.
	 *
	 * @param flightSectorRevenueFilterVO the flight sector revenue filter vo
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException the system exception
	 * @author a-3429
	 */
	public Collection<SectorRevenueDetailsVO> findSectorDetails(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)
			throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "findSectorDetails");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "validateReportingPeriod");
			return mraDefaultsDao
			.findSectorDetails(flightSectorRevenueFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Find flight revenue for sector.
	 *
	 * @param flightSectorRevenueFilterVO the flight sector revenue filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-3429
	 */
	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) throws SystemException{

		return MRABillingDetails.findFlightRevenueForSector(flightSectorRevenueFilterVO);

	}


	/**
	 * Find list rate audit details from temp.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @author a-3251
	 */
	public RateAuditVO findListRateAuditDetailsFromTemp(RateAuditFilterVO rateAuditFilterVO)throws SystemException{
		return MRABillingMasterTemp.findListRateAuditDetailsFromTemp(rateAuditFilterVO);
	}

	/**
	 * Removes the rate audit details from temp.
	 *
	 * @param rateAuditVO the rate audit vo
	 * @throws SystemException the system exception
	 * @author a-3251
	 */
	public void removeRateAuditDetailsFromTemp( RateAuditVO rateAuditVO)throws SystemException{


		MRABillingMasterTempPK mRABillingMasterTempPK = new MRABillingMasterTempPK();
		mRABillingMasterTempPK.setCsgSequenceNumber(rateAuditVO.getConSerNum());
		mRABillingMasterTempPK.setCsgDocumentNumber(rateAuditVO.getConDocNum());
		mRABillingMasterTempPK.setCompanyCode(rateAuditVO.getCompanyCode());
		mRABillingMasterTempPK.setBillingBasis(rateAuditVO.getBillingBasis());
		mRABillingMasterTempPK.setPostalAuthCode(rateAuditVO.getGpaCode());

		MRABillingMasterTemp mRABillingMasterTemp = new MRABillingMasterTemp();
		try {
			mRABillingMasterTemp = MRABillingMasterTemp.find(mRABillingMasterTempPK);
			mRABillingMasterTemp.remove();
		} catch (FinderException e) {
			throw new SystemException(e.getMessage(), e);
		}

	}





	/**
	 * This method generates invoices for outward billing.
	 *
	 * @param invoiceFilterVO the invoice filter vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author a-2521
	 */
	/////added for AirnZ
	public void generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
	throws SystemException, MailTrackingMRABusinessException {



		String outParameter=MRABillingMaster.generateOutwardBillingInvoice(invoiceFilterVO);


		String result = "OK";
		if ("EXP_INV_ALR_GEN".equalsIgnoreCase(outParameter)) {
			MailTrackingMRABusinessException exception = new MailTrackingMRABusinessException();
			exception
			.addError(new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_INVALRGEN));
			throw exception;

		}else if ("EXP_NO_BLBREC".equalsIgnoreCase(outParameter)) {
			MailTrackingMRABusinessException exception = new MailTrackingMRABusinessException();
			exception
			.addError(new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENINV_NOBLBREC));
			throw exception;

		}else if ("EXP_INV_EXGRATBAS".equalsIgnoreCase(outParameter)) {
			MailTrackingMRABusinessException exception = new MailTrackingMRABusinessException();
			exception
			.addError(new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENINV_NOEXGRATBAS));
			throw exception;
		}
		else if (!result.equalsIgnoreCase(outParameter)) {

			MailTrackingMRABusinessException exception = new MailTrackingMRABusinessException();
			exception
			.addError(new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENINV_FAILED));
			throw exception;
		}
	}




	


	/**
	 * Save rate audit details.
	 *
	 * @param newRateAuditVO the new rate audit vo
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3251
	 */
	public  void saveRateAuditDetails(RateAuditVO newRateAuditVO) throws SystemException,MailTrackingMRABusinessException{

		log.entering("MRA Defaults controller", "saveRateAuditDetails");

		MRABillingMaster mRABillingMaster=null;
		Map<String, String> systemParameters = null;
		try {
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);

			//SAVE TO HISTORY IF PRORATION TRIGERED
			if(newRateAuditVO.getSaveToHistoryFlg()!=null && "Y".equals(newRateAuditVO.getSaveToHistoryFlg())){

				for(RateAuditDetailsVO rateAuditDetailsVO : newRateAuditVO.getRateAuditDetails()){

					LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
					rateAuditDetailsVO.setLastUpdateTime(fromDate);
					rateAuditDetailsVO.setLastUpdateUser(newRateAuditVO.getLastUpdateUser());
					rateAuditDetailsVO.setRemark("");
					rateAuditDetailsVO.setCompTotTrigPt(newRateAuditVO.getCompTotTrigPt());
					MRABillingDetailsHistory mRABillingDetailsHistory = new MRABillingDetailsHistory(rateAuditDetailsVO);
					log.log(Log.FINE, "Inserted into History table");
				}
			}


			mRABillingMaster =	MRABillingMaster.find(newRateAuditVO.getCompanyCode(),newRateAuditVO.getMailSequenceNumber());
			mRABillingMaster.remove();

			MRABillingMaster mRABillingMasterNew = new MRABillingMaster(newRateAuditVO);

			//To raise an actual CCA
			if(newRateAuditVO.getRaiseCCAFlag()!=null && "Y".equals(newRateAuditVO.getRaiseCCAFlag())){
				raiseActualCCA(newRateAuditVO);
			}


			/**
			 * added by A-2565 Meenu for triggering flown and interline provision accounting
			 */
			SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
			Collection<String> systemParameterCodes = new ArrayList<String>();
			systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
			//the system parameter for audit  value Y for activate audit and N for deactivate audit
			String sysparCode = "mailtracking.mra.dsnauditrequired";
			systemParameterCodes.add (sysparCode);
			try {
				systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			String accountingEnabled = (systemParameters.get(SYS_PARA_ACCOUNTING_ENABLED));
			//if rate audit finalised and accounting enabled
			if("F".equals(newRateAuditVO.getDsnStatus()) && "Y".equals(accountingEnabled)){
				log.log(Log.FINE, "Inside PROCCC");
				//mRABillingMaster =	MRABillingMaster.find(newRateAuditVO.getConSerNum(), newRateAuditVO.getConDocNum(), newRateAuditVO.getCompanyCode(), newRateAuditVO.getBillingBasis(), newRateAuditVO.getGpaCode());
				mRABillingMasterNew.triggerFlownAndInterlineProvAccounting(newRateAuditVO);
			}

			/**
			 * updated by meenu for Auditing AirNZ681
			 **/
			if (systemParameters != null) {
				String sysparValue = systemParameters.get (sysparCode);
				log.log(Log.FINE, " sysparValue==================> ",
						sysparValue);
				//if value is Y then call the audit method in helper for auditing
				if ("Y".equals (sysparValue)){
					AuditHelper.auditDSNForRateAudit(newRateAuditVO);
				}
			}
		} catch (FinderException finderException) {
			log.log(Log.SEVERE,"Finder Exception Occured!!!!");
			throw new SystemException(finderException.getMessage(),
					finderException);
		}


		log.exiting("MRA Defaults controller", "saveRateAuditDetails");
	}





	/**
	 * Find dsn routing details.
	 *
	 * @param dsnRoutingFilterVO the dsn routing filter vo
	 * @return Collection<DSNRoutingVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public  Collection<DSNRoutingVO>  findDSNRoutingDetails(DSNRoutingFilterVO dsnRoutingFilterVO)  throws SystemException {

		return DSNRouting.findDSNRoutingDetails(dsnRoutingFilterVO);
	}


	/**
	 * Save dsn routing details.
	 *
	 * @param dsnRoutingVOs the dsn routing v os
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public  void saveDSNRoutingDetails(Collection<DSNRoutingVO> dsnRoutingVOs) throws SystemException{

		log.entering("MRA Defaults controller", "saveDSNRoutingDetails");
		log.log(Log.INFO, "dsnRoutingVOs in cntrlr-->", dsnRoutingVOs);
		int count=1;
		String transferPA=null;
		String transferAirline=null;
		DSNRoutingVO dSNRoutingVO=null;
		
		for(DSNRoutingVO dsnRoutingVO:dsnRoutingVOs){
			DSNRouting dsnRouting = null;
			if (OPERATION_FLAG_UPDATE.equals(dsnRoutingVO
					.getOperationFlag())) {
				try {
					dsnRouting = DSNRouting.find(dsnRoutingVO.getCompanyCode(),dsnRoutingVO.getMailSequenceNumber(),dsnRoutingVO.getRoutingSerialNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING DSNRouting Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (dsnRouting != null) {
					//Modified by A-7794 as part of ICRD-308518
					if(dsnRouting.getBsaReference()!= null){
						updateExistingDSNVOs(dsnRouting);
					}
					dsnRouting.remove();
				}
			}
			else if (OPERATION_FLAG_DELETE.equals(dsnRoutingVO
					.getOperationFlag())) {
				try {
					dsnRouting = DSNRouting.find(dsnRoutingVO.getCompanyCode(),dsnRoutingVO.getMailSequenceNumber(),dsnRoutingVO.getRoutingSerialNumber());
				} catch (FinderException finderException) {
					log
					.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING DSNRouting Entity");
					throw new SystemException(finderException.getErrorCode());
				}
				if (dsnRouting != null) {
					dsnRouting.remove();
				}
			}
		}
		for(DSNRoutingVO dsnRoutingVO:dsnRoutingVOs){

			if (OPERATION_FLAG_INSERT.equals(dsnRoutingVO
					.getOperationFlag())||OPERATION_FLAG_UPDATE.equals(dsnRoutingVO
							.getOperationFlag())) {

				dsnRoutingVO.setRoutingSerialNumber(count);
				//Code added by Manish for IASCB-33315 start
				if(dsnRoutingVO.getAcctualweight() > 0)
					dsnRoutingVO.setWeight(dsnRoutingVO.getAcctualweight());
				//Code added by Manish for IASCB-33315 end
				new DSNRouting(dsnRoutingVO);

			}
			count++;
		}
		
		if(dsnRoutingVOs!=null && !dsnRoutingVOs.isEmpty()){
			MRABillingMaster mRABillingMaster=null;
			dSNRoutingVO=dsnRoutingVOs.iterator().next();
			transferAirline=dSNRoutingVO.getTransferAirline();
			transferPA=dSNRoutingVO.getTransferPA();
				try {
					 mRABillingMaster =  MRABillingMaster.find(dSNRoutingVO.getCompanyCode(), dSNRoutingVO.getMailSequenceNumber()) ;
				} catch (FinderException finderException) {
					log.log(Log.SEVERE,finderException);
				}
				if (mRABillingMaster!=null){
					mRABillingMaster.setTransfercarcode(transferAirline);
					mRABillingMaster.setTransferPACode(transferPA);
				}
		}
		
		log.exiting(CLASS_NAME, "saveDSNRoutingDetails");
	}


	/**
	 * List unaccounted dispatches.
	 *
	 * @param unaccountedDispatchesFilterVO the unaccounted dispatches filter vo
	 * @return UnaccountedDispatchesVO
	 * @throws SystemException the system exception
	 * @author A-2107
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(
			UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
			throws SystemException{
		log.entering("MRADefaultController", "listUnaccountedDispatches");

		Page<UnaccountedDispatchesDetailsVO> unaccountedDispatches =
			MRABillingMaster.listUnaccountedDispatches(unaccountedDispatchesFilterVO);
		Money proratedAmt = null;
		Money dummyProratedAmt = null;
		Money dummyProratedAmtInCntrCurr = null;
		if(!"R1".equals(unaccountedDispatchesFilterVO.getReasonCode())){
			if(unaccountedDispatches != null){
				for(UnaccountedDispatchesDetailsVO unaccountedDispatchesDtlsVO : unaccountedDispatches){
					LocalDate recVDate=null;
					String receivDate=null;
					Money proratedAmtInCtrCur = null;
					double amountInCtrCur=0.0;
					double amount = 0.0D;
					if("R2".equals(unaccountedDispatchesDtlsVO.getReason())){
						if(unaccountedDispatchesDtlsVO.getCurrency()!=null &&
								unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
							if(MRAConstantsVO.CURRENCY_NZD.equals(unaccountedDispatchesDtlsVO.getCurrency())){


								try{
									proratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									dummyProratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									amount = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight();
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmt(dummyProratedAmt);
									unaccountedDispatchesDtlsVO.setProratedAmtinCtrcur(dummyProratedAmt);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
							}else if(unaccountedDispatchesDtlsVO.getCurrency() != null &&
									unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
								if(unaccountedDispatchesDtlsVO.getAcceptedDate()!=null){

									receivDate=unaccountedDispatchesDtlsVO.getAcceptedDate().toDisplayDateOnlyFormat();

									recVDate=(new LocalDate
											(LocalDate.NO_STATION,Location.NONE,false).setDate( receivDate ));
								}

								else{
									recVDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
								}


								try{
									proratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									dummyProratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									double exgRate = 0.0D;
									exgRate = findExchangeRateForRateAuditDetails(
											unaccountedDispatchesDtlsVO.getCompanyCode(),
											unaccountedDispatchesDtlsVO.getCurrency(),
											MRAConstantsVO.CURRENCY_NZD,
											MRAConstantsVO.INHOUSE_RATE,
											recVDate);
									amount = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight() * exgRate;
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmt(dummyProratedAmt);

								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
								catch (MailTrackingMRABusinessException ex) {
									ex.getMessage();
								}
								try{

									proratedAmtInCtrCur = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									dummyProratedAmtInCntrCurr = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									amountInCtrCur = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight();
									proratedAmtInCtrCur.setAmount(amountInCtrCur);
									dummyProratedAmtInCntrCurr.setAmount(proratedAmtInCtrCur.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmtinCtrcur(dummyProratedAmtInCntrCurr);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
							}
						}
					}
				}
			}
		}
		log.exiting(CLASS_NAME, "listUnaccountedDispatches");
		return unaccountedDispatches;


	}


	/**
	 * THIS METHOD WILL RETURN THE Unaccounted Dispatches BASED ON THE FILTER
	 * CRITERIA.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @author A-2107
	 */
	public Map<String, Object> generateUnaccountedDispachedReport(
			ReportSpec reportSpec) throws SystemException,
			RemoteException {
		log.entering("MRADefaultController", "generateUnaccountedDispachedReport");

		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO = UnaccountedDispatchesFilterVO.class
		.cast(reportSpec.getFilterValues().get(0));

		Collection<UnaccountedDispatchesDetailsVO> unaccountedDispatchesDetailsVO = MRABillingMaster
		.findUnaccountedDispatchesReport(unaccountedDispatchesFilterVO);
		Money proratedAmt = null;
		Money dummyProratedAmt = null;
		Money dummyProratedAmtInCntrCurr = null;
		if(!"R1".equals(unaccountedDispatchesFilterVO.getReasonCode())){
			if(unaccountedDispatchesDetailsVO != null){
				for(UnaccountedDispatchesDetailsVO unaccountedDispatchesDtlsVO : unaccountedDispatchesDetailsVO){
					LocalDate recVDate=null;
					String receivDate=null;
					Money proratedAmtInCtrCur = null;
					double amountInCtrCur=0.0;
					double amount = 0.0D;
					if("R2".equals(unaccountedDispatchesDtlsVO.getReason())){
						if(unaccountedDispatchesDtlsVO.getCurrency()!=null &&
								unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
							if(MRAConstantsVO.CURRENCY_NZD.equals(unaccountedDispatchesDtlsVO.getCurrency())){


								try{
									proratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									dummyProratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									amount = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight();
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmt(dummyProratedAmt);
									unaccountedDispatchesDtlsVO.setProratedAmtinCtrcur(dummyProratedAmt);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
							}else if(unaccountedDispatchesDtlsVO.getCurrency() != null &&
									unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
								if(unaccountedDispatchesDtlsVO.getAcceptedDate()!=null){

									receivDate=unaccountedDispatchesDtlsVO.getAcceptedDate().toDisplayDateOnlyFormat();

									recVDate=(new LocalDate
											(LocalDate.NO_STATION,Location.NONE,false).setDate( receivDate ));
								}

								else{
									recVDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
								}


								try{
									proratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									dummyProratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									double exgRate = 0.0D;
									exgRate = findExchangeRateForRateAuditDetails(
											unaccountedDispatchesDtlsVO.getCompanyCode(),
											unaccountedDispatchesDtlsVO.getCurrency(),
											MRAConstantsVO.CURRENCY_NZD,
											MRAConstantsVO.INHOUSE_RATE,
											recVDate);
									amount = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight() * exgRate;
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmt(dummyProratedAmt);

								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
								catch (MailTrackingMRABusinessException ex) {
									ex.getMessage();
								}
								try{

									proratedAmtInCtrCur = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									dummyProratedAmtInCntrCurr = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									amountInCtrCur = unaccountedDispatchesDtlsVO.getRates()* unaccountedDispatchesDtlsVO.getWeight();
									proratedAmtInCtrCur.setAmount(amountInCtrCur);
									dummyProratedAmtInCntrCurr.setAmount(proratedAmtInCtrCur.getRoundedAmount());

									unaccountedDispatchesDtlsVO.setProratedAmtinCtrcur(dummyProratedAmtInCntrCurr);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
							}
						}
					}
				}
			}
		}
		UnaccountedDispatchesVO unaccountedDispatchesVO=null;

		unaccountedDispatchesVO=getTotalOfDispatches(unaccountedDispatchesFilterVO);
		log.log(Log.INFO, "unaccountedDispatchesVO in ctrlr",
				unaccountedDispatchesVO);
		if(unaccountedDispatchesVO!=null){
			unaccountedDispatchesFilterVO.setNoOfDispatches(unaccountedDispatchesVO.getNoOfDispatches());
			if(unaccountedDispatchesVO.getPropratedAmt()!=null){
				unaccountedDispatchesFilterVO.setProRatedAmt(String.valueOf(unaccountedDispatchesVO.getPropratedAmt().getAmount()));
			}
			unaccountedDispatchesFilterVO.setCurrency(MRAConstantsVO.CURRENCY_NZD);
			log.log(Log.INFO, "unaccountedDispatchesFilterVO in ctrlr",
					unaccountedDispatchesFilterVO);
		}
		reportSpec.addParameter(unaccountedDispatchesFilterVO);
		reportSpec.setData(unaccountedDispatchesDetailsVO);

		log.exiting("MailController", "generateUnaccountedDispachedReport");
		return ReportAgent.generateReport(reportSpec);

	}




	/**
	 * Find inter line billing details.
	 *
	 * @param airlineBillingDetailVO the airline billing detail vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			AirlineBillingDetailVO  airlineBillingDetailVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findInterLineBillingDetails");

		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "findInterLineBillingDetails");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findInterLineBillingDetails");
			return mraDefaultsDao.findInterLineBillingDetails(airlineBillingDetailVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}


	}

	/**
	 * Method for Printing CCA Details.
	 *
	 * @param reportSpec the report spec
	 * @return Map
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3447
	 */

	public Map<String, Object> printCCAReport(ReportSpec reportSpec) throws SystemException, RemoteException,MailTrackingMRABusinessException {

		log.entering("MRADefaultController", "printCCAReport");

		MaintainCCAFilterVO maintainCCAFilterVO = (MaintainCCAFilterVO) reportSpec.getFilterValues().iterator().next();
		Collection<CCAdetailsVO> cCAdetailsVOs = CCADetail.findCCAdetails(maintainCCAFilterVO);
		log.log(Log.INFO, " Report Data From Server ->", cCAdetailsVOs);
		if (cCAdetailsVOs == null || cCAdetailsVOs.size() <= 0) {
			log.log(Log.INFO, "inside null loop---->");
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_DEFAULTS_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		log.log(Log.INFO, "filter vo!**", maintainCCAFilterVO);
		reportSpec.addParameter(maintainCCAFilterVO);
		/**
		 * Calling Update Routing Method
		 */
		CCAdetailsVO cCAdetailsVO=updateRouting(cCAdetailsVOs);
		reportSpec.addParameter(cCAdetailsVO);
		Map<String,String> systemParameterMap =null; //Added by A-8164 for ICRD-267530 starts
		Collection<String> systemParCodes = new ArrayList<String>();   
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");     
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) { 
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();   
		}
		//Added as part of IASCB-860 starts
		String[] reasncode=null;
		String actualrsncode=null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		Collection<CRAParameterVO> cRAParameterVOs=null;
		try
		{
			cRAParameterVOs =new CRADefaultsProxy().findCRAParameterDetails(logonAttributes.getCompanyCode(),"MCA");
		}
		catch(ProxyException proxyException)
		{
			proxyException.printStackTrace();
		}
		//Added by A-8164 for ICRD-267530 ends
		for(CCAdetailsVO cCAdetailsVOForIterate:cCAdetailsVOs){
			cCAdetailsVOForIterate.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));     
		
		
		} 
		
		
		
		//Added as part of 	IASCB-860 				
		reportSpec.addExtraInfo(cRAParameterVOs);
				
		reportSpec.setData(cCAdetailsVOs);    
		Collection<DSNRoutingVO> dSNRoutingVOs =new ArrayList<DSNRoutingVO>();
		dSNRoutingVOs=cCAdetailsVO.getDsnRoutingVOs();
		log.log(Log.INFO, "dSNRoutingVOs----", dSNRoutingVOs);
		ReportMetaData subReportMetaData = new ReportMetaData();
		
		subReportMetaData.setFieldNames(new String[] {
				"departureDate","flightCarrierCode","flightNumber","pol",
		"pou"});
		subReportMetaData.setColumnNames(new String[] {"FLTDAT",
				"FLTCARCOD","FLTNUM","POL","POU"});
		reportSpec.addSubReportMetaData(subReportMetaData);
		reportSpec.addSubReportData(dSNRoutingVOs);
		
		return ReportAgent.generateReport(reportSpec);

	}



	/**
	 * Update routing.
	 *
	 * @param cCAdetailsVOs the c c adetails v os
	 * @return the cC adetails vo
	 * @author A-3447
	 */
	public CCAdetailsVO updateRouting(Collection<CCAdetailsVO> cCAdetailsVOs){
		/*
		 * This method determines the Origin and Destination of the DSN
		 * based on Flight Routings.
		 */
		CCAdetailsVO ccaDetailsVO=null;
		if(cCAdetailsVOs!=null && cCAdetailsVOs.size()>0){
			ccaDetailsVO=((ArrayList<CCAdetailsVO>)cCAdetailsVOs).get(0);
			Collection<DSNRoutingVO> dsnRoutingVOs = ccaDetailsVO.getDsnRoutingVOs();
			Collection<DSNRoutingVO> dsnRoutings = new ArrayList<DSNRoutingVO>();
			if(dsnRoutingVOs!=null && dsnRoutingVOs.size()>0){
				Set<Integer> routeSerNum =new  TreeSet<Integer>();
				for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
					if(dsnRoutingVO.getRoutingSerialNumber()!=0){
						routeSerNum.add(dsnRoutingVO.getRoutingSerialNumber());
					}
				}
				if(routeSerNum.size()>0){

					Collection<Integer> routeSerNumList =new ArrayList<Integer>();
					for(Integer serialNumber : routeSerNum){
						routeSerNumList.add(serialNumber);
						boolean firstTime=true;
						for(DSNRoutingVO dsnRouting : dsnRoutingVOs){
							if(serialNumber==dsnRouting.getRoutingSerialNumber() &&
									firstTime){
								dsnRoutings.add(dsnRouting);
								firstTime=false;
							}
						}
					}
					int minSerNum = ((ArrayList<Integer>) routeSerNumList).get(0);
					int maxSerNum = ((ArrayList<Integer>) routeSerNumList).get(routeSerNumList.size()-1);
					String firstPol= null;
					String lastPou= null;
					log.log(Log.FINE, " Min Routing Serial Number----->",
							minSerNum);
					log.log(Log.FINE, " Max Routing Serial Number----->",
							maxSerNum);
					for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
						if(dsnRoutingVO.getRoutingSerialNumber()!=0 ){
							if(dsnRoutingVO.getRoutingSerialNumber()==minSerNum){
								firstPol=dsnRoutingVO.getPol();
							}
							if(dsnRoutingVO.getRoutingSerialNumber()==maxSerNum){
								lastPou=dsnRoutingVO.getPou();
							}
						}
					}
					if(firstPol!=null && firstPol.length()>0){
						ccaDetailsVO.setOriginCode(firstPol);
						ccaDetailsVO.setRevOrgCode(firstPol);
						log.log(Log.FINE, " Origin Code----->", firstPol);
					}
					if(lastPou!=null && lastPou.length()>0){
						ccaDetailsVO.setDestnCode(lastPou);
						if(ccaDetailsVO.getRevDStCode()==null){
							ccaDetailsVO.setRevDStCode(lastPou);
						}
						log.log(Log.FINE, " Destination Code ----->", lastPou);
					}
					ccaDetailsVO.setDsnRoutingVOs(dsnRoutings);
				}

			}
		}
		return ccaDetailsVO;

	}

	/**
	 * Find proration exceptions.
	 *
	 * @param prorationExceptionsFilterVO the proration exceptions filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<ProrationExceptionVO> findProrationExceptions(ProrationExceptionsFilterVO prorationExceptionsFilterVO)
	throws SystemException
	{
		log.entering("MRADefaultsController", "findProrationExceptions");
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		log.log(Log.INFO, "logon.getOwnAirlineIdentifier()=========", logon.getOwnAirlineIdentifier());
		prorationExceptionsFilterVO.setFlightCarrierId(logon.getOwnAirlineIdentifier());
		return MRAProrationExceptions.findProrationExceptions(prorationExceptionsFilterVO);
	}

	/**
	 * Save proration exceptions.
	 *
	 * @param prorationExceptionVOs the proration exception v os
	 * @throws SystemException the system exception
	 */
	public void saveProrationExceptions(Collection<ProrationExceptionVO> prorationExceptionVOs)
	throws SystemException
	{
		log.entering("MRADefaultsController", (new StringBuilder("saveProrationExceptions")).append(prorationExceptionVOs).toString());

		for(ProrationExceptionVO prorationExceptionVO:prorationExceptionVOs){
			new MRAProrationExceptions().saveProrationExceptions(prorationExceptionVO);
		}


		log.exiting("MRADefaultsController", "saveProrationExceptions");
	}

	/**
	 * Prints the proration exception report.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	public  Map<String, Object>  printProrationExceptionReport(ReportSpec reportSpec)
	throws SystemException, MailTrackingMRABusinessException
	{
		String KEY_EXCEPTION = "mra.proration.exceptions";
		String KEY_STATUS = "mra.proration.exceptionstatus";
		String KEY_TRIGGERPOINT = "mailtracking.mra.proration.triggerpoint";
		String KEY_ASSIGNED_STATUS = "mra.proration.assignedstatus";
		log.entering("ProrationController", "```````````printForReport");
		ProrationExceptionsFilterVO filterVO = (ProrationExceptionsFilterVO)(ProrationExceptionsFilterVO)reportSpec.getFilterValues().get(0);
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		log.log(Log.INFO, "logon.getOwnAirlineIdentifier()=========", logon.getOwnAirlineIdentifier());
		filterVO.setFlightCarrierId(logon.getOwnAirlineIdentifier());
		log.log(3, (new StringBuilder("FilterVO. in Controller...........")).append(filterVO).toString());
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		ArrayList<String> parameterTypes = new ArrayList<String>();
		log.log(Log.FINE, "KEY_EXCEPTION-------->", KEY_EXCEPTION);
		log.log(Log.FINE, "KEY_STATUS-------->", KEY_STATUS);
		log.log(Log.FINE, "KEY_TRIGGERPOINT-------->", KEY_TRIGGERPOINT);
		parameterTypes.add(KEY_EXCEPTION);
		parameterTypes.add(KEY_STATUS);
		parameterTypes.add(KEY_TRIGGERPOINT);
		parameterTypes.add(KEY_ASSIGNED_STATUS);

		String companyCode = logon.getCompanyCode();
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		try
		{
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode, parameterTypes);
			log.log(3, (new StringBuilder("One time values-------->")).append(oneTimes).toString());
		}
		catch(ProxyException proxyException)
		{
			throw new SystemException(proxyException.getMessage());
		}
		Collection<ProrationExceptionVO> prorationExceptionVOs = MRAProrationExceptions.printProrationExceptionReport(filterVO);
		log.log(3, (new StringBuilder("ProrationExceptionVOsssssssss...")).append(prorationExceptionVOs).toString());
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] {
				"DISPATCHNUM", "EXPCOD", "SEGORG", "SEGDST", "EXPDAT", "TRIGERPNT", "PROFCT", "FLTNUM", "FLTDAT", "ROUTE",
				"CSGDOCNUM", "PCS", "ASDUSR", "LSTUPDTIM", "RSDDAT", "EXPSTA"
		});
		reportMetaData.setFieldNames(new String[] {
				"dispatchNo", "exceptionCode", "segmentOrigin", "segmentDestination", "date", "triggerPoint", "flightNumber", "flightDate", "route", "consDocNo",
				"noOfBags", "assignedUser", "assignedTime", "resolvedTime", "status"
		});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.addParameter(filterVO);
		reportSpec.setData(prorationExceptionVOs);

		reportSpec.addExtraInfo(oneTimes);
		log.exiting("ProrationController", "````````````````printForReport");
		return ReportAgent.generateReport(reportSpec);
	}

	





	/**
	 * Find proration log details.
	 *
	 * @param dsnFilterVO the dsn filter vo
	 * @return Collection<MailProrationLogVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public  Collection<MailProrationLogVO>  findProrationLogDetails(DSNFilterVO dsnFilterVO)  throws SystemException {

		return MRABillingMaster.findProrationLogDetails(dsnFilterVO);
	}

	/**
	 * View proration log details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO)throws SystemException {

		return MRABillingMaster.viewProrationLogDetails(prorationFilterVO);

	}

	/**
	 * Find flown details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO popUpVO)throws SystemException{
		return MRABillingMaster.findFlownDetails(popUpVO);
	}

	/**
	 * Prints the document statistics report.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3429
	 */
	public Map<String, Object> printDocumentStatisticsReport(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {

		DocumentStatisticsFilterVO statisticsFilterVO = (DocumentStatisticsFilterVO) reportSpec
		.getFilterValues().iterator().next();

		Collection<DocumentStatisticsDetailsVO>  statisticsDetailsVO=
			MRABillingMaster.printDocumentStatisticsReport(statisticsFilterVO);

		log.log(Log.INFO, "data cCADetailsVOs1234----->", statisticsDetailsVO);
		log.log(Log.INFO, "outside null loop---->");

		if (statisticsDetailsVO == null || statisticsDetailsVO.size() <= 0) {
			log.log(Log.INFO, "inside null loop---->");
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_DEFAULTS_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		log.log(Log.INFO, "filter vo!@#&----->", statisticsFilterVO);
		reportSpec.addParameter(statisticsFilterVO);
		reportSpec.setData(statisticsDetailsVO);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * Finds tand returns the GPA Billing entries available
	 * This includes billed, billable and on hold despatches.
	 *
	 * @param gpaBillingEntriesFilterVO the gpa billing entries filter vo
	 * @return Collection<GPABillingDetailsVO>
	 * @throws SystemException the system exception
	 */

	public Page<DocumentBillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException{

		log.entering(CLASS_NAME, "listGPABillingDetails");
		Page<DocumentBillingDetailsVO> gpaBillingDetailsVOs=
			MRABillingMaster.findGPABillingEntries(gpaBillingEntriesFilterVO);
		log.exiting(CLASS_NAME, "listGPABillingDetails");
		return gpaBillingDetailsVOs;

	}

	/**
	 * Changes the staus of GPA billing entries to Billable/On Hold.
	 *
	 * @param postalAdministrationDetailsVO the postal administration details vo
	 * @return the postal administration details vo
	 * @throws SystemException the system exception
	 */

	/* public void changeBillingStatus(
            Collection<GPABillingStatusVO> gpaBillingStatusVO)
    throws SystemException{
    	log.entering(CLASS_NAME, "changeBillingStatus");
    	ArrayList<GPABillingStatusVO> gpaBillingStatusVOs=new ArrayList<GPABillingStatusVO>(gpaBillingStatusVO);
    	log.log(log.INFO,"GPABILLING STATUS VOS "+gpaBillingStatusVOs);
    	MRABillingMaster mraBillingMaster=new MRABillingMaster();
    	try
    	{


    		//mraBillingMaster=MRABillingMaster.find(gpaBillingStatusVOs.get(0).getCsgSequenceNumber(),
    		//gpaBillingStatusVOs.get(0).getCsgDocumentNumber(),gpaBillingStatusVOs.get(0).getCompanyCode(),
    		//gpaBillingStatusVOs.get(0).getBillingBasis(),gpaBillingStatusVOs.get(0).getPoaCode());
    		mraBillingMaster.updateBillingStatus(gpaBillingStatusVO);
    	}
    	catch (FinderException e) {
//printStackTraccee()();
				throw new SystemException(e.getMessage());
			}
    	catch (CreateException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}
    	catch (RemoveException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}
    	catch (ChangeStatusException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}

    	catch (ContainsActualCCAException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}

    }
	 */

	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public PostalAdministrationDetailsVO validatePoaDetailsForBilling(PostalAdministrationDetailsVO postalAdministrationDetailsVO) throws SystemException {

		PostalAdministrationDetailsVO paDetailsResultVO =null;
		try {
			paDetailsResultVO = new MailTrackingDefaultsProxy().validatePoaDetailsForBilling(postalAdministrationDetailsVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE," @@@ Proxy Exception thrown from MailTracking : Defaults Module ");
			throw new SystemException(e.getMessage(),e);
		}
		log.exiting(CLASS_NAME, "findPostalAdminDetails");
		return paDetailsResultVO;
	}


	


	/**
	 * Gets the total of dispatches.
	 *
	 * @param unaccountedDispatchesFilterVO the unaccounted dispatches filter vo
	 * @return the total of dispatches
	 * @throws SystemException the system exception
	 * @throws RemoteException  *
	 * @author A-2107
	 */
	public UnaccountedDispatchesVO getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
	throws SystemException , RemoteException {
		Collection<UnaccountedDispatchesVO> unaccountedDispatchesVOs = MRABillingMaster.getTotalOfDispatches(unaccountedDispatchesFilterVO);

		UnaccountedDispatchesVO unaccountedDispatchesVO=new UnaccountedDispatchesVO();
		Integer totaldespatches=0;
		Money proratedamt=null;
		Money dummyProratedAmt = null;
		try{
			proratedamt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
		}
		catch (CurrencyException e) {
			e.getErrorCode();
		}

		if(!"R1".equals(unaccountedDispatchesFilterVO.getReasonCode())){
			if(unaccountedDispatchesVOs != null){
				for(UnaccountedDispatchesVO unaccountedDispatchesDtlsVO : unaccountedDispatchesVOs){
					LocalDate recVDate=null;
					String receivDate=null;
					Money proratedAmt = null;
					double amount = 0.0D;
					if("R2".equals(unaccountedDispatchesDtlsVO.getReasonCode())){
						if(unaccountedDispatchesDtlsVO.getCurrency()!=null &&
								unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
							if(MRAConstantsVO.CURRENCY_NZD.equals(unaccountedDispatchesDtlsVO.getCurrency())){
								try{
									proratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									dummyProratedAmt = CurrencyHelper.getMoney(unaccountedDispatchesDtlsVO.getCurrency());
									amount = unaccountedDispatchesDtlsVO.getRate()
									* unaccountedDispatchesDtlsVO.getWeight();
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount()
											* Double.parseDouble(unaccountedDispatchesDtlsVO.getNoOfDispatches()));

									unaccountedDispatchesDtlsVO.setPropratedAmt(dummyProratedAmt);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
							}else if(unaccountedDispatchesDtlsVO.getCurrency() != null &&
									unaccountedDispatchesDtlsVO.getCurrency().trim().length() > 0 ){
								if(unaccountedDispatchesDtlsVO.getAcceptedDate()!=null){
									receivDate=unaccountedDispatchesDtlsVO.getAcceptedDate().toDisplayDateOnlyFormat();
									recVDate=(new LocalDate
											(LocalDate.NO_STATION,Location.NONE,false).setDate( receivDate ));
								}else{
									recVDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
								}
								try{
									proratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									dummyProratedAmt = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
									double exgRate = 0.0D;
									exgRate = findExchangeRateForRateAuditDetails(
											unaccountedDispatchesDtlsVO.getCompanyCode(),
											unaccountedDispatchesDtlsVO.getCurrency(),
											MRAConstantsVO.CURRENCY_NZD,
											MRAConstantsVO.INHOUSE_RATE,
											recVDate);
									amount = unaccountedDispatchesDtlsVO.getRate()
									* unaccountedDispatchesDtlsVO.getWeight()
									* exgRate;
									proratedAmt.setAmount(amount);
									dummyProratedAmt.setAmount(proratedAmt.getRoundedAmount()
											* Double.parseDouble(unaccountedDispatchesDtlsVO.getNoOfDispatches()));

									unaccountedDispatchesDtlsVO.setPropratedAmt(dummyProratedAmt);
								}
								catch (CurrencyException e) {
									e.getErrorCode();
								}
								catch (MailTrackingMRABusinessException ex) {
									ex.getMessage();
								}
							}
						}
					}
				}
			}
		}
		for(UnaccountedDispatchesVO unaccountedVO :unaccountedDispatchesVOs ){
			totaldespatches += Integer.parseInt(unaccountedVO.getNoOfDispatches());
			if(unaccountedVO.getCurrency()!=null && unaccountedVO.getCurrency().trim().length()>0){
				proratedamt.plusEquals(unaccountedVO.getPropratedAmt());
			}
		}
		unaccountedDispatchesVO.setNoOfDispatches(String.valueOf(totaldespatches));
		unaccountedDispatchesVO.setPropratedAmt(proratedamt);
		log.exiting(CLASS_NAME, "PropratedAmt..."+unaccountedDispatchesVO.getPropratedAmt());

		return unaccountedDispatchesVO;
	}





	/**
	 * View irregularity details.
	 *
	 * @param filterVO the filter vo
	 * @return Collection<IrregularityVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)throws SystemException{
		log.entering("MRADefaultsController", "viewIrregularityDetails");

		return MRAIrregularityDetails.viewIrregularityDetails(filterVO);
	}

	/**
	 * Prints the irregularity report.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3229
	 */

	public Map<String, Object> printIrregularityReport(ReportSpec reportSpec)

	throws SystemException, MailTrackingMRABusinessException {

		log.entering("MRA Defaults Controller", "```````````printIrregularityReport");

		MRAIrregularityFilterVO filterVO = (MRAIrregularityFilterVO) reportSpec.getFilterValues().get(0);

		Map<String, Collection<OneTimeVO>> oneTimes = null;
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(IRPSTATUS);


		LogonAttributes logon = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String companyCode = logon.getCompanyCode();

		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		try {
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode,
					parameterTypes);

		} catch (ProxyException proxyException) {

			throw new SystemException(proxyException.getMessage());
		}


		Collection<MRAIrregularityVO> irregularityCollection = MRAIrregularityDetails.printIrregularityReport(filterVO);

		log.log(Log.FINE, "irregularityCollection...", irregularityCollection);
		if (irregularityCollection == null || irregularityCollection.size() == 0) {

			ErrorVO error = new ErrorVO(ERROR_SEARCH);

			log.log(Log.FINE, "Errors are returned............");

			error.setErrorDisplayType(ErrorDisplayType.ERROR);

			MailTrackingMRABusinessException mraBusinessException = new MailTrackingMRABusinessException();

			mraBusinessException.addError(error);

			throw mraBusinessException;

		}

		ReportMetaData parameterMetaData = new ReportMetaData();

		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);
		reportSpec.addExtraInfo(oneTimes);

		//main report
		ReportMetaData reportMetaData = new ReportMetaData();

		reportSpec.setReportMetaData(reportMetaData);
		if(irregularityCollection != null && irregularityCollection.size()>0){
			log.log(Log.FINE, "irregularityCollection not null",
					irregularityCollection);
			reportSpec.setData(irregularityCollection);
			log.log(Log.FINE, "report data", reportSpec.getData());
		}

		// subreport 1
		ReportMetaData subReportMetaData1=new ReportMetaData();


		reportSpec.addSubReportMetaData(subReportMetaData1);

		// subreport 2
		ReportMetaData subReportMetaData2=new ReportMetaData();


		reportSpec.addSubReportMetaData(subReportMetaData2);


		//ADDING SUB REPORTS TO MAIN REPORT

		reportSpec.addSubReportData(irregularityCollection);


		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * Find accounting details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<MRAAcountingVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO popUpVO)throws SystemException{
		log.entering("MRA Defaults Controller", "findAccountingDetails");
		return MRABillingMaster.findAccountingDetails(popUpVO);
	}

	/**
	 * Find usps reporting details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<USPSReportingVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 */
	public Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO popUpVO)throws SystemException{
		log.entering("MRA Defaults Controller", "findUSPSReportingDetails");
		return MRABillingMaster.findUSPSReportingDetails(popUpVO);
	}



	/**
	 * Re prorate dsn.
	 *
	 * @param dsnRoutingVOs the dsn routing v os
	 * @throws SystemException the system exception
	 * @author A-3251
	 */
	@Advice(name = "mail.operations.reProrateDSN" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException{
		log.entering(CLASS_NAME, "reProrateDSN");
		MRABillingMaster.reProrateDSN(dsnRoutingVOs);
		if(isTaxRequired()){
		Collection<MRABillingDetailsVO> mraBillingDetails = null;
		MRADefaultsDAO mraDefaultsDao = null;
		String billingBasis = dsnRoutingVOs.iterator().next().getMailbagId();//A-8164 for TK 4.8 
		FlightValidationVO flightValidationVO=new FlightValidationVO();
		flightValidationVO.setCompanyCode(dsnRoutingVOs.iterator().next().getCompanyCode());
		flightValidationVO.setFlightCarrierId(dsnRoutingVOs.iterator().next().getFlightCarrierId());
		flightValidationVO.setFlightNumber(dsnRoutingVOs.iterator().next().getFlightNumber());
		flightValidationVO.setFlightSequenceNumber(dsnRoutingVOs.iterator().next().getFlightSeqnum());
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			mraBillingDetails = mraDefaultsDao.findBillingEntriesForFlight(flightValidationVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		if(mraBillingDetails!=null && !mraBillingDetails.isEmpty()){
			HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails
			= findTaxDetails(mraBillingDetails);
			for(MRABillingDetailsVO billingDetailsVO :mraBillingDetails ){
				if(billingBasis.equals(billingDetailsVO.getBillingBasis()))
					{
					updateBillingEntryWithTax(billingDetailsVO,taxDetails);
					}
			}
		}
		//Changed as part of BUG ICRD-106470 by A-5526 starts
		//findAndUpdateBilligDetails(flightValidationVO,billingBasis);
		//Cnhanged as part of BUG ICRD-106470 by A-5526 ends
		}

		//Added For ICRD-265471 BEGIN
		try {
				LocalDate date = null;
				LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
				date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			
				
				
				CRAFlightFinaliseVO cRAFlightFinaliseVO=null;
			 	for(DSNRoutingVO dSNRoutingVO:dsnRoutingVOs )
				if("B".equals(dSNRoutingVO.getBlockSpaceType())|| dSNRoutingVO.getBsaReference()!=null){
					cRAFlightFinaliseVO = new CRAFlightFinaliseVO();
					
					cRAFlightFinaliseVO.setCompanyCode(dSNRoutingVO.getCompanyCode());
					cRAFlightFinaliseVO.setFlightCarrierIdr(dSNRoutingVO.getFlightCarrierId());
					cRAFlightFinaliseVO.setFlightNumber(dSNRoutingVO.getFlightNumber());
					cRAFlightFinaliseVO.setFlightSeqNumber(dSNRoutingVO.getFlightSeqnum());
					cRAFlightFinaliseVO.setSegSerialNumber(dSNRoutingVO.getSegmentSerialNumber());
					cRAFlightFinaliseVO.setLastUpdatedUser(logonAttributes.getUserId());
					cRAFlightFinaliseVO.setTriggerPoint("MRA");
					
					
				new CRAMiscbillingProxy().saveBlockSpaceAgreementDetails(cRAFlightFinaliseVO);
				
				}
				
			} 
		 catch (ProxyException e) {
				log.log(Log.INFO,"Exception ");
		 }
		
		catch (SystemException e) {
				log.log(Log.INFO,"Exception ");
		 }
		
		
		//Added For ICRD-265471 END
		
		log.exiting(CLASS_NAME, "reProrateDSN");
	}
	//Added as part of BUG ICRD-106470 by A-5526 starts
	private void findAndUpdateBilligDetails(
			FlightValidationVO flightValidationVO, String billingBasis) throws SystemException {
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<MRABillingDetailsVO> mraBillingDetails = null;

		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			mraBillingDetails = mraDefaultsDao.findBillingEntriesForFlight(flightValidationVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		if(mraBillingDetails!=null && !mraBillingDetails.isEmpty()){
			HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails
			= findTaxDetails(mraBillingDetails);
			for(MRABillingDetailsVO billingDetailsVO :mraBillingDetails ){
				if(billingBasis.equals(billingDetailsVO.getBillingBasis()))
					{
					updateBillingEntryWithTax(billingDetailsVO,taxDetails);
					}
			}
		}

	}
	//Added as part of BUG ICRD-106470 by A-5526 ends


	
	/**
	 * Find outstanding balances.
	 *
	 * @param outstandingBalanceVO the outstanding balance vo
	 * @return Collection<IrregularityVO>
	 * @throws SystemException the system exception
	 * @author A-3434
	 */
	public Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)throws SystemException{
		log.entering("MRADefaultsController", "viewIrregularityDetails");

		return MRABillingMaster.findOutstandingBalances(outstandingBalanceVO);
	}

	/**
	 * Perform accounting for ds ns.
	 *
	 * @param unAccountedDSNVOs the un accounted dsnv os
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-3429
	 */
	public void performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs)throws SystemException,MailTrackingMRABusinessException {
		MRABillingMaster.performAccountingForDSNs(unAccountedDSNVOs);
	}

	/**
	 * Display fuel surcharge details.
	 *
	 * @param gpaCode the gpa code
	 * @param cmpCod the cmp cod
	 * @return Collection<FuelSurchargeVO>
	 * @throws BusinessDelegateException the business delegate exception
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 * @author A-2391
	 */
	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCod)
	throws BusinessDelegateException,RemoteException, SystemException{
		log.entering(CLASS_NAME, "displayFuelSurchargeDetails");
		return MRAFuelChargeMaster
		.displayFuelSurchargeDetails(gpaCode,cmpCod);
	}

	/**
	 * Save fuel surcharge details.
	 *
	 * @param fuelSurchargeVOs the fuel surcharge v os
	 * @throws BusinessDelegateException the business delegate exception
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 * @author A-2391
	 */
	public void saveFuelSurchargeDetails(Collection<FuelSurchargeVO> fuelSurchargeVOs)
	throws BusinessDelegateException,RemoteException, SystemException{
		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");
		int exist=0;
		int errors=0;
		try{
			for(FuelSurchargeVO surchargeVO:fuelSurchargeVOs){
				if (("I").equals(surchargeVO.getOperationFlag())){
					log.log(Log.INFO, "vo in controller ", surchargeVO);
					exist=MRAFuelChargeMaster.findExistingFuelSurchargeVOs(surchargeVO);
					log.log(Log.INFO, "exist FRM CONTROLLER INSERT ", exist);
					if(exist>0) {
						errors++;
					}else{
						new MRAFuelChargeMaster(surchargeVO);
					}
				}
				if (("U").equals(surchargeVO.getOperationFlag())){
					MRAFuelChargeMaster mst=MRAFuelChargeMaster.find(surchargeVO.getCompanyCode(),surchargeVO.getGpaCode(),
							surchargeVO.getSeqNum());
					exist=MRAFuelChargeMaster.findExistingFuelSurchargeVOs(surchargeVO);
					log.log(Log.INFO, "exist FRM CONTROLLER UPDATE", exist);
					if(exist>1) {
						errors++;
					}else{
						mst.update(surchargeVO);
					}
				}
				if (("D").equals(surchargeVO.getOperationFlag())){
					MRAFuelChargeMaster mst=MRAFuelChargeMaster.find(surchargeVO.getCompanyCode(),surchargeVO.getGpaCode(),
							surchargeVO.getSeqNum());
					mst.remove();
				}
			}
			if(errors>0)	{
				log.log(Log.INFO, "errors FRM CONTROLLER ", errors);
				throw new SystemException(
						DuplicateParameterException.DUPLICATE_PARAMETER_EXIST,
						new DuplicateParameterException());

			}
		}
		catch(FinderException e){
			log.log(Log.INFO,"FINDER ");
		}

	}

	/**
	 * Save history details.
	 *
	 * @param ccadetailsVO the ccadetails vo
	 * @throws BusinessDelegateException the business delegate exception
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 * @author A-2391
	 */
	@Advice(name = "mail.operations.saveHistoryDetails" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void saveHistoryDetails(
			CCAdetailsVO ccadetailsVO)
	throws BusinessDelegateException ,RemoteException, SystemException{
		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");
		
		CCADetail cCADetails=null;
		
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		Double exchangeRate=0.0;
		boolean isReverseAccounting = false;
		Money UpdValAmt = null;
		MRABillingMaster mRABillingMaster = new MRABillingMaster() ;
		RateAuditFilterVO filterVO =new RateAuditFilterVO();
		CCAdetailsVO revisedCCADetailsVO= null;
		MRABillingMaster mraBillingMaster = null;
		MRAGPABillingDetails mraGPABillingDetails = null;
		Collection<MRABillingDetails> mraBillingDetailses=new ArrayList<MRABillingDetails>();
		

		
		try{
			mraBillingMaster = MRABillingMaster.find(ccadetailsVO.getCompanyCode(),ccadetailsVO.getMailSequenceNumber());

		} catch (FinderException exception) {
			log.log(Log.FINE,"Inside finder exception.. ");

		}
		if(mraBillingMaster != null){
			mraBillingDetailses = mraBillingMaster.getBillingDetails();
			if(mraBillingDetailses!=null && !mraBillingDetailses.isEmpty()){
				if(mraBillingDetailses.iterator().next().getValueInBlgCurrency()>0){
					try{
					UpdValAmt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
					UpdValAmt.setAmount(mraBillingDetailses.iterator().next().getValueInBlgCurrency()+ccadetailsVO.getRevChgGrossWeight().getAmount());
					ccadetailsVO.setValChgUpdAmount(UpdValAmt);
					}catch(CurrencyException exception){
						log.log(Log.FINE,"Inside CurrencyException.. ");
					}
				}
			}
		}
		double netAmount = 0.0;
		Money netAmt;
		try {
			netAmt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
			netAmount=ccadetailsVO.getRevChgGrossWeight().getAmount()+ccadetailsVO.getOtherRevChgGrossWgt().getAmount()+ ccadetailsVO.getRevTax() - ccadetailsVO.getRevTds();
			netAmt.setAmount(netAmount);
			ccadetailsVO.setRevNetAmount(netAmt);
		} catch (CurrencyException excep) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		filterVO.setCompanyCode(ccadetailsVO.getCompanyCode());
		filterVO.setBillingBasis(ccadetailsVO.getBillingBasis());
		filterVO.setCsgDocNum(ccadetailsVO.getCsgDocumentNumber());
		filterVO.setCsgSeqNum(ccadetailsVO.getCsgSequenceNumber());
		filterVO.setGpaCode(ccadetailsVO.getGpaCode());
		LocalDate dsnDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if(ccadetailsVO.getDsnDate()!=null){	//added for ICRD-282931
		dsnDate.setDate(ccadetailsVO.getDsnDate());
		}
		filterVO.setDsnDate(dsnDate);
		RateAuditVO rateAuditVO=mRABillingMaster.findListRateAuditDetails(filterVO);
		ArrayList<RateAuditDetailsVO> rateAuditDetailsVOs=new ArrayList<RateAuditDetailsVO>();
		if(rateAuditVO != null){
			rateAuditDetailsVOs=new  ArrayList<RateAuditDetailsVO>(rateAuditVO.getRateAuditDetails());
		}
		if(rateAuditDetailsVOs!=null && rateAuditDetailsVOs.size()>0){
			for(RateAuditDetailsVO rateAuditDetailsVO:rateAuditDetailsVOs ){
				LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
				rateAuditDetailsVO.setLastUpdateTime(fromDate);
				rateAuditDetailsVO.setLastUpdateUser(logon.getUserId());
				rateAuditDetailsVO.setRemark("From Maintain MCA");
				rateAuditDetailsVO.setCompTotTrigPt("CA");
				MRABillingDetailsHistory mRABillingDetailsHistory = new MRABillingDetailsHistory(rateAuditDetailsVO);
				log.log(Log.FINE, "Inserted into History table from saveHistoryDetails.....>>");
			}
		}
		/*Added By A-3434 for bug 46427
		 * UpdBillto in MTKMRABLGDTL updates here
		 * if reverse gpacode in maintain cca changes.
		 */



		//			if(mraBillingDetailses != null && mraBillingDetailses.size()>0){
		//				for(MRABillingDetails mraBillingDetails:mraBillingDetailses ){
		//					if("R".equals(mraBillingDetails.getPaymentFlag())){
		//
		//						//mraBillingDetails.setUpdBillTo(ccadetailsVO.getRevGpaCode());
		//						log.log(Log.FINE, "after updating UpdBillTo......>>"+mraBillingDetails.getUpdBillTo());
		//
		//					}
		//				}
		//			}
		if(APPROVED.equals(ccadetailsVO.getCcaStatus())){
			if(!ccadetailsVO.getGpaCode().equals(ccadetailsVO.getRevGpaCode())){

				/**
				 * 	if mca is in approved status and rev gpa is different,
				 *  create another mca for the rev gpa and
				 *  update existing mca with negative entries
				 *  1. Existing entry for the old GPA will have amount negated( set as 0 for as it is considered in invoice generation )
				 *  2. New entry for the revised GPA with revised amounts of the original
				 *
				 */
				//LocalDate date=null;
				
				
				/**
				 * UPDBILLTOOPOA of MALMRAGPABLGDTL will be updated
				 * **/
				try {
					mraGPABillingDetails = MRAGPABillingDetails.find(ccadetailsVO.getCompanyCode(),ccadetailsVO.getMailSequenceNumber(),ccadetailsVO.getBlgDtlSeqNum());
				} catch (FinderException e) {
					log.log(Log.SEVERE, "Finder exception caught on MRAGPABillingDetails");
				}
				if (mraGPABillingDetails!=null){
					mraGPABillingDetails.setUpdatedBillTooPOA(ccadetailsVO.getRevGpaCode());
					mraGPABillingDetails.setUpdatedGrossWeight(ccadetailsVO.getRevGrossWeight());
					mraGPABillingDetails.setUpdatedContractCurrency(ccadetailsVO.getRevContCurCode());
					mraGPABillingDetails.setUpdatedWtChargeInContractCurrency(ccadetailsVO.getRevChgGrossWeight().getAmount());
					mraGPABillingDetails.setUpdatedOtherChargeInContractCurrency(ccadetailsVO.getOtherRevChgGrossWgt().getAmount());
				}
				/**
				 * new mca created for the revgpa will have maximum issuedate
				 */
/*				if(ccadetailsVO.getIssueDat()!=null){
					date =  new LocalDate(ccadetailsVO.getIssueDat(),true);
					//date=(LocalDate) cCADetails.getIssueDate();
				}
				else{
					date=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
				}
				cCADetails.setIssueDate(date.addSeconds(5));*/



				//negating existing mca
				//Modified by A-7794 as part of ICRD-271248 -Start
				CCADetail cCADetail=null;
				boolean checkLatestBilled = true;
				double totBillablechg = 0.0;
				double totBillablenetAmt = 0.0;
				Money autoRevnetAmount = null;
				Money autorevchgGrossWt = null;
				Money chargGrssWt = null;
				Money othrChargGrssWt = null;
				Money netAmountWt = null;
				MaintainCCAFilterVO ccaFilterVO = new MaintainCCAFilterVO();
				ccaFilterVO.setCompanyCode(ccadetailsVO.getCompanyCode());
				ccaFilterVO.setBillingBasis(ccadetailsVO.getBillingBasis());
				ccaFilterVO.setConsignmentDocNum(ccadetailsVO.getCsgDocumentNumber());
				ccaFilterVO.setConsignmentSeqNum(ccadetailsVO.getCsgSequenceNumber());
				ccaFilterVO.setPOACode(ccadetailsVO.getPoaCode());
				//Modified by A-7794 as part of ICRD-289946
				Collection<CCAdetailsVO> ccaVos = new ArrayList<CCAdetailsVO>();
				Collection<CCAdetailsVO> actualVOs =  CCADetail.findCCA(ccaFilterVO);
				for(CCAdetailsVO manipulateVO : actualVOs){
					if(!manipulateVO.getCcaStatus().equals("N")){
						ccaVos.add(manipulateVO);
					}
				}
				if(null != ccaVos && !ccaVos.isEmpty()){
					for(CCAdetailsVO tempVO : ccaVos){
						if(BILLABLE.equals(tempVO.getBillingStatus())){
							tempVO.setCompanyCode(ccadetailsVO.getCompanyCode());
							try {
								cCADetail = CCADetail.find(tempVO);
							} catch (FinderException e) {
							}
						totBillablechg = totBillablechg + (cCADetail.getRevWtChargeCTR() - cCADetail.getWeightChargeCTR());
						totBillablenetAmt = totBillablenetAmt + (cCADetail.getRevNetAmount() - cCADetail.getNetAmount());
						}
					}
					
					for(CCAdetailsVO vo : ccaVos){
						vo.setCompanyCode(ccadetailsVO.getCompanyCode());//Since return VO doesnt have cmpcod and  for finding cCADetail we need cmpcod; setting the same
						try {
							cCADetail = CCADetail.find(vo);
						} catch (FinderException e) {
						}
						if(BILLABLE.equals(vo.getBillingStatus())){
							//setting deatil VO with updated revGpaCode
							vo.setRevGpaCode(ccadetailsVO.getRevGpaCode());
							vo.setRevGpaName(ccadetailsVO.getRevGpaName());
							cCADetail.updateMaster(constructDetailVO(cCADetail,vo));
						}else if(cCADetail.getRevGpaCode().equals(ccadetailsVO.getGpaCode())){
							while (checkLatestBilled){
								//creating auto mca for old gpacode
				revisedCCADetailsVO=constructRevCCADetailVO(ccadetailsVO);
							try {
								if(revisedCCADetailsVO.getNetAmount() != null){
								autoRevnetAmount = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
								autoRevnetAmount.setAmount(revisedCCADetailsVO.getNetAmount().getAmount()-totBillablenetAmt);
								revisedCCADetailsVO.setNetAmount(autoRevnetAmount);
								}
								if(revisedCCADetailsVO.getChgGrossWeight() != null){
								autorevchgGrossWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
								autorevchgGrossWt.setAmount(revisedCCADetailsVO.getChgGrossWeight().getAmount()-totBillablechg);
								revisedCCADetailsVO.setChgGrossWeight(autorevchgGrossWt);
								}
							} catch (CurrencyException e1) {
							}
				try {
					cCADetail = CCADetail.find(ccadetailsVO);
					//Modified by A-7794 as part of ICRD-289946
					if(null != cCADetail && cCADetail.getMcaStatus().equals("N")){
						cCADetail=new CCADetail(revisedCCADetailsVO);
					}else{
					cCADetail.updateMaster(revisedCCADetailsVO);
					}

				} catch (FinderException e) {
					cCADetail=new CCADetail(revisedCCADetailsVO);
					//Added by A-7929 for ICRD-261091 starts
					if(cCADetail!=null){
									ccadetailsVO.setCcaRefNumber("");//CCA refnum is erased to cause finder exception creating second entry with revisedCCADetailsVO,Added as part of ICRD-140971
					}
					//Added by A-7929 for ICRD-261091 ends
							}
							//setting actual values to agentchange mca of new gpacode
							if(autorevchgGrossWt != null){
							ccadetailsVO.setRevChgGrossWeight(autorevchgGrossWt);
							}
							if(autoRevnetAmount != null){
							ccadetailsVO.setRevNetAmount(autoRevnetAmount);
							}
							try {
								chargGrssWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
								chargGrssWt.setAmount(0);
								ccadetailsVO.setChgGrossWeight(chargGrssWt);
								othrChargGrssWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
								othrChargGrssWt.setAmount(0);
								ccadetailsVO.setOtherChgGrossWgt(othrChargGrssWt);
								netAmountWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
								netAmountWt.setAmount(0);
								ccadetailsVO.setNetAmount(netAmountWt);
							} catch (CurrencyException e) {
							}
							//Modified by A-8527 for ICRD-344449 moved the flown accounting method above 
							triggerMCAFlownAccounting(revisedCCADetailsVO);
							if("I".equals(ccadetailsVO.getCcaType())){
								triggerMCAInternalAccounting(revisedCCADetailsVO);
								}
							
							isReverseAccounting = true;
							checkLatestBilled = false;
							}
							
							}
						}
					//for new mca of billed mailbags for gpachange 
					}else if(!BILLABLE.equals(mraGPABillingDetails.getBillingStatus())){
						revisedCCADetailsVO=constructRevCCADetailVO(ccadetailsVO);
						try {
							cCADetail = CCADetail.find(ccadetailsVO);
							//Modified by A-7794 as part of ICRD-289946
							
							if(null != cCADetail && (cCADetail.getMcaStatus().equals("N")|| cCADetail.getMcaStatus().equals("A")||"Y".equals(ccadetailsVO.getAutoMca()))){
								cCADetail=new CCADetail(revisedCCADetailsVO);
							}else{
							cCADetail.updateMaster(revisedCCADetailsVO);
							}
						} catch (FinderException e) {
							cCADetail=new CCADetail(revisedCCADetailsVO);
							if(cCADetail!=null){
								ccadetailsVO.setCcaRefNumber("");//CCA refnum is erased to cause finder exception creating second entry with revisedCCADetailsVO,Added as part of ICRD-140971
							}
						}
						try {
							chargGrssWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
							chargGrssWt.setAmount(0);
							ccadetailsVO.setChgGrossWeight(chargGrssWt);
							othrChargGrssWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
							othrChargGrssWt.setAmount(0);
							ccadetailsVO.setOtherChgGrossWgt(othrChargGrssWt);
							netAmountWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
							netAmountWt.setAmount(0);
							ccadetailsVO.setNetAmount(netAmountWt);
						} catch (CurrencyException e) {
						}
						//Modified by A-8527 for ICRD-344449 moved the flown accounting method above 
						triggerMCAFlownAccounting(revisedCCADetailsVO);
						if("I".equals(ccadetailsVO.getCcaType())){
							triggerMCAInternalAccounting(revisedCCADetailsVO);
							}
						
						isReverseAccounting = true;
					}
				//Modified by A-7794 as part of ICRD-271248 -End
				
				
				
				//A-8061 Added for ICRD-289296 begin	
				if(APPROVED.equals(ccadetailsVO.getCcaStatus()) ){ 
						if(ccadetailsVO.getCcaRefNumber()!=null){
							ccadetailsVO.setCurrChangeFlag("N");
							prorateMCA(ccadetailsVO.getCcaRefNumber(),ccadetailsVO.getCurrChangeFlag());
						}
				 }
				//A-8061 Added for ICRD-289296 end	
				
              //Added as part of ICRD-342944 starts
				
				String assignee = null;
				String isMcaWorkFlowEnabled =null;
				SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
				Collection<String> systemParameterCodes = new ArrayList<String>();
				//system parameter for accounting
				systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
				systemParameterCodes.add(SYSPAR_BASECURRENCY);
				Map<String, String> systemParameters = null;
				try {
					systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
				} catch (ProxyException e) {
					
					e.getMessage();
				}
				if (systemParameters != null) {
					 isMcaWorkFlowEnabled = (systemParameters.get(SYS_PARAM_WRKFLOWENABLED));
				}
				if ("R".equals(isMcaWorkFlowEnabled)) {
					if ("ACPMCA".equals(ccadetailsVO.getAcceptRejectIdentifier())) {
						
						String baseCurrency = systemParameters.get(SYSPAR_BASECURRENCY);	
					
						MailMRAHelper.populateNetValueBase(ccadetailsVO);
					
						LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
						if (logonAttributes.getDefaultRoleGroupAtStation() != null && 
								logonAttributes.getDefaultRoleGroupAtStation().trim().length() > 0) {
							ccadetailsVO.setInitiatorRoleGroup(logonAttributes.getDefaultRoleGroupAtStation());
						}else{
						ccadetailsVO.setInitiatorRoleGroup(logonAttributes.getRoleGroupCode());
						}
						assignee = MailMRAHelper
								.evaluateCCAWorkflowAssignee(ccadetailsVO);
						if (assignee != null && assignee.length() > 0 
								&&(!assignee.contains(ccadetailsVO.getInitiatorRoleGroup()))) {
							
							ccadetailsVO.setAssignee(assignee);
							ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_ACCEPTED);//C 
						} else {
							ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_APPROVED);//A
						}	
					} else {
						ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_REJECTED);//R
					}

				}
				//Added as part of ICRD-342944 ends
							
				
				try {
					cCADetails = CCADetail.find(ccadetailsVO);
					cCADetails.updateMaster(ccadetailsVO);
				} catch (FinderException ex) {
					//When WorkFlow Y,no entry will be there so  create new entry else update present value with 0
					
					cCADetails = new CCADetail(ccadetailsVO);
				}
				if(cCADetails!=null){
				cCADetails.setMcaStatus(ccadetailsVO.getCcaStatus());
				
				ccadetailsVO.setCcaRefNumber(cCADetails.getCCADetailsPK().getMcaRefNumber());//added by A-6991 for ICRD-252771	
			}

		
				
			}
			else{
				
				//Added for IASCB-2374 starts
				
				String assignee = null;
				String isMcaWorkFlowEnabled =null;
				SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
				Collection<String> systemParameterCodes = new ArrayList<String>();
				//system parameter for accounting
				systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
				systemParameterCodes.add(SYSPAR_BASECURRENCY);
				Map<String, String> systemParameters = null;
				try {
					systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
				} catch (ProxyException e) {
					
					e.getMessage();
				}
				if (systemParameters != null) {
					 isMcaWorkFlowEnabled = (systemParameters.get(SYS_PARAM_WRKFLOWENABLED));
				}
				if ("R".equals(isMcaWorkFlowEnabled)) {
					if ("ACPMCA".equals(ccadetailsVO.getAcceptRejectIdentifier())) {
						
						String baseCurrency = systemParameters.get(SYSPAR_BASECURRENCY);	
						//MailMRAHelper.populateAirportDetails(ccadetailsVO);
						
						//MailMRAHelper.populateStationCurrency(ccadetailsVO);
						MailMRAHelper.populateNetValueBase(ccadetailsVO);
							
					//Commented as part of ICRD-340886
						//MailMRAHelper.populateNetValue(ccadetailsVO);

						LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
						//ccadetailsVO.setInitiatorRoleGroup(logonAttributes.getRoleGroupCode());
						if (logonAttributes.getDefaultRoleGroupAtStation() != null && 
								logonAttributes.getDefaultRoleGroupAtStation().trim().length() > 0) {
							ccadetailsVO.setInitiatorRoleGroup(logonAttributes.getDefaultRoleGroupAtStation());
						}else{
						ccadetailsVO.setInitiatorRoleGroup(logonAttributes.getRoleGroupCode());
						}
						assignee = MailMRAHelper
								.evaluateCCAWorkflowAssignee(ccadetailsVO);
						//Modified as part of ICRD-342197
						if (assignee != null && assignee.length() > 0 
								&&(!assignee.contains(ccadetailsVO.getInitiatorRoleGroup()))) {
							
							ccadetailsVO.setAssignee(assignee);
							ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_ACCEPTED);//C 
						} else {
							ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_APPROVED);//A
						}	
					} else {
						ccadetailsVO.setCcaStatus(ccadetailsVO.MCA_STATUS_REJECTED);//R
					}

				}
				//Added for IASCB-2374 ends
				
				try {
					cCADetails = CCADetail.find(ccadetailsVO);
					cCADetails.updateMaster(ccadetailsVO);
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					cCADetails=new CCADetail(ccadetailsVO);
					
				}
				ccadetailsVO.setCcaRefNumber(cCADetails.getCCADetailsPK().getMcaRefNumber());//Commeted and added by A-6991 for ICRD-252771	
			}
			//ccadetailsVO.setCcaRefNumber(cCADetails.getCCADetailsPK().getMcaRefNumber());
			if(cCADetails!=null){ 
			}
			
			if(APPROVED.equals(ccadetailsVO.getCcaStatus())){
			//find and update MTKMRABLGMST
			try {
				mraBillingMaster=MRABillingMaster.find(ccadetailsVO.getCompanyCode(),ccadetailsVO.getMailSequenceNumber());
				mraBillingMaster.setGrsWgt(ccadetailsVO.getRevGrossWeight());
				

			} catch (FinderException e) {

				log.log(Log.FINE,  "FinderException");
			}
			/**
			 * UPDBILLTOOPOA of MALMRAGPABLGDTL will be updated
			 * **/
			try {
				mraGPABillingDetails = MRAGPABillingDetails.find(ccadetailsVO.getCompanyCode(),ccadetailsVO.getMailSequenceNumber(),ccadetailsVO.getBlgDtlSeqNum());//modified by A-7371 as part of ICRD-257661
			} catch (FinderException e) {
				log.log(Log.SEVERE, "Finder exception caught on MRAGPABillingDetails");
			}
			if (mraGPABillingDetails!=null && !"I".equals(ccadetailsVO.getCcaType())){
				mraGPABillingDetails.setUpdatedBillTooPOA(ccadetailsVO.getRevGpaCode());
				
				mraGPABillingDetails.setUpdatedGrossWeight(ccadetailsVO.getRevGrossWeight());
				mraGPABillingDetails.setUpdatedContractCurrency(ccadetailsVO.getRevContCurCode());
							  //modified by A-7938
				if(ccadetailsVO.getCcaType() != null && STATUS_ACTIVE.equals(ccadetailsVO.getCcaType())){
				mraGPABillingDetails.setUpdatedWtChargeInContractCurrency(ccadetailsVO.getRevChgGrossWeight().getAmount());
				convertAndPopulateUpdatedAmount(ccadetailsVO,mraGPABillingDetails);//ICRD-342347
				 }
				mraGPABillingDetails.setUpdatedOtherChargeInContractCurrency(ccadetailsVO.getOtherRevChgGrossWgt().getAmount());
				//Commented by A-7794 as part of ICRD-290334
				//mraGPABillingDetails.setNetAmount(ccadetailsVO.getRevNetAmount().getAmount());//Added by A-7929 for ICRD-253627
				mraGPABillingDetails.setAppliedRate(ccadetailsVO.getRevisedRate());   //Added by A-7929 as part of ICRD-278016
				mraGPABillingDetails.setUpdatedWtChargeInContractCurrency(ccadetailsVO.getRevChgGrossWeight().getAmount()); //Added by A-7929 as part of ICRD-278016
			}
			  
			}
			//Added by a-7929 as part of ICRD-237091 starts..
			if(APPROVED.equals(ccadetailsVO.getCcaStatus()) && (ccadetailsVO.getGpaCode().equals(ccadetailsVO.getRevGpaCode())) &&( ccadetailsVO.getNetAmount().getAmount() != (ccadetailsVO.getRevNetAmount().getAmount())) &&(ccadetailsVO.getContCurCode().equals(ccadetailsVO.getRevContCurCode())) ){ //modified for ICRD-282931
				if(ccadetailsVO.getCcaRefNumber()!=null){
					ccadetailsVO.setCurrChangeFlag("N");
					prorateMCA(ccadetailsVO.getCcaRefNumber(),ccadetailsVO.getCurrChangeFlag());
				}
			}
			else if(APPROVED.equals(ccadetailsVO.getCcaStatus()) && (!ccadetailsVO.getContCurCode().equals(ccadetailsVO.getRevContCurCode()))&& ( ccadetailsVO.getNetAmount().getAmount() == (ccadetailsVO.getRevNetAmount().getAmount()))){//modified for ICRD-282931
                  if(ccadetailsVO.getCcaRefNumber()!=null){
                	  ccadetailsVO.setCurrChangeFlag("Y");
					prorateMCA(ccadetailsVO.getCcaRefNumber(),ccadetailsVO.getCurrChangeFlag());
				}
			
			}else if(APPROVED.equals(ccadetailsVO.getCcaStatus())){//call prorate mca to update values in MALMRABLGDTLHIS for interfacing 
				
				if(ccadetailsVO.getCcaRefNumber()!=null){
					ccadetailsVO.setCurrChangeFlag("N");
					prorateMCA(ccadetailsVO.getCcaRefNumber(),ccadetailsVO.getCurrChangeFlag());
				}
			}
			
			
	        //Added by a-7929 as part of ICRD-237091 ends...
			//computeTax called on approval also to implement
			//for calculating TDS exemption by setting  isExemptionBalanceTobeupdated as true in TaxFilterVO
			if(ccadetailsVO.getGpaCode().equals(ccadetailsVO.getRevGpaCode())){
			computeTax(ccadetailsVO);
			}
			//			if(ccadetailsVO.getGrossWeight()!=(ccadetailsVO.getRevGrossWeight())){
			//				//trigerring proration for mra on MCA Save
			//				triggerProration(ccadetailsVO);
			//				//mra proration ends
			//
			//			}
			//Added as part of ICRD-106057
			if("Y".equals(ccadetailsVO.getGpaChangeInd()) && !isReverseAccounting){
				
			}else{
				//Modified by A-8527 for ICRD-344449 moved the flown accounting method above 
				triggerMCAFlownAccounting(ccadetailsVO);
				//Modified as part of ICRD-341710
			if("I".equals(ccadetailsVO.getCcaType()) && "A".equals(ccadetailsVO.getCcaStatus())){
				triggerMCAInternalAccounting(ccadetailsVO);
				}
			//Added as part of ICRD-341710
			if("A".equals(ccadetailsVO.getCcaStatus())){
			//Modified by A-7794 as part of ICRD-221742
			
			}
			}
			MCAAuditVO mcaAuditVO = new MCAAuditVO(MCAAuditVO.AUDIT_MODULENAME,
					MCAAuditVO.AUDIT_SUBMODULENAME,MCAAuditVO.AUDIT_ENTITY);
			StringBuilder addInfo=new StringBuilder("");
			String mcaType = "A".equals(ccadetailsVO.getCcaType())?ACT:"Internal";
			String mailType=ccadetailsVO.getBillingBasis().length()>=29?"Mail bag":"Despatch";
			mcaAuditVO.setCompanyCode(ccadetailsVO.getCompanyCode());
			mcaAuditVO.setCcaRefNumber(ccadetailsVO.getCcaRefNumber());
			mcaAuditVO.setBillingBasis(ccadetailsVO.getBillingBasis());
			if(ACT.equals(mcaType))
				{
				//Modified the below code by A-8527 for ICRD-350435 starts
				if("A".equals(ccadetailsVO.getCcaStatus())){
				mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_APPROVED);
				}else{
					mcaAuditVO.setActionCode(MCAAuditVO.ACTUAL_MCA_ACCEPTED);	
				}
				//Modified the below code by A-8527 for ICRD-350435 ends
				}
			else
				{
				//Modified the below code by A-8527 for ICRD-350435 starts
				if("A".equals(ccadetailsVO.getCcaStatus())){
				mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_APPROVED);
				}else{
				 mcaAuditVO.setActionCode(MCAAuditVO.INTERNAL_MCA_ACCEPTED);	
				}
				}
			//Modified the below code by A-8527 for ICRD-350435
			mcaAuditVO.setAuditRemarks("MCA Approved");
			//Modified the below code by A-8527 for ICRD-350435 starts
			if("A".equals(ccadetailsVO.getCcaStatus())){
			addInfo.append(mcaType).append(M).append(ccadetailsVO.getCcaRefNumber()).append(" Approved for the ").
			append(mailType).append(" ").append(ccadetailsVO.getBillingBasis());
			}else{
			addInfo.append(mcaType).append(M).append(ccadetailsVO.getCcaRefNumber()).append(" Accepted for the ").
				append(mailType).append(" ").append(ccadetailsVO.getBillingBasis());	
			}
			//Modified the below code by A-8527 for ICRD-350435 Ends
			mcaAuditVO.setAdditionalInformation(addInfo.toString());
			mcaAuditVO.setMailSequenceNumber(ccadetailsVO.getMailSequenceNumber());
			AuditUtils.performAudit(mcaAuditVO);
		auditMCAAtMailbagLevel(ccadetailsVO);
		}
	}
	
	
	private void auditMCAAtMailbagLevel(CCAdetailsVO detailsVO) throws SystemException {
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		 Calendar time = null;
		 String mailType=detailsVO.getBillingBasis().length()>=29?"Mail Bag":"Despatch";
		 StringBuilder addInfo=new StringBuilder("");
		 String mcaType = "A".equals(detailsVO.getCcaType())?ACT:"Internal";
		 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
	
			
			MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
			
			mailbagAuditVO.setCompanyCode(detailsVO.getCompanyCode());
			mailbagAuditVO.setMailbagId(detailsVO.getBillingBasis());     
			mailbagAuditVO.setMailSequenceNumber(detailsVO.getMailSequenceNumber());
			if(ACT.equals(mcaType))
			{
			mailbagAuditVO.setActionCode("MTKMRACCACRT_ACTUAL");
			}
		else
			{
			mailbagAuditVO.setActionCode("MTKMRACCACRT_INTERNAL");
			}
					
					
		addInfo.append(mcaType).append(M).append(detailsVO.getCcaRefNumber()).append(" Accepted for the ").
		append(mailType).append(" ").append(detailsVO.getBillingBasis());
		    mailbagAuditVO.setAdditionalInformation(addInfo.toString());
			mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId()); 
			mailbagAuditVO.setAuditRemarks("MCA Accepted");
				
			mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
			mailbagAuditVO.setTxnLocalTime(time);
			mailbagAuditVO.setTxnTime(time);
			mailbagAuditVO.setAuditTriggerPoint("MRA072");
			
			
			AuditUtils.performAudit(mailbagAuditVO);
		 
    }



	/**
	 * Construct rev cca detail vo.
	 *
	 * @param ccadetailsVO the ccadetails vo
	 * @return the cC adetails vo
	 * @throws SystemException the system exception
	 */
	private CCAdetailsVO constructRevCCADetailVO(CCAdetailsVO ccadetailsVO) throws SystemException {
		CCAdetailsVO revisedCCADetailVO=new CCAdetailsVO();
		//BeanHelper.copyProperties(revisedCCADetailVO,ccadetailsVO);
		revisedCCADetailVO.setBillingBasis(ccadetailsVO.getBillingBasis());
		revisedCCADetailVO.setBillingPeriodFrom(ccadetailsVO.getBillingPeriodFrom());
		revisedCCADetailVO.setBillingPeriodTo(ccadetailsVO.getBillingPeriodTo());
		revisedCCADetailVO.setBillingStatus(ccadetailsVO.getBillingStatus());
		revisedCCADetailVO.setCategory(ccadetailsVO.getCategory());
		revisedCCADetailVO.setCategoryCode(ccadetailsVO.getCategoryCode());
		revisedCCADetailVO.setMailSequenceNumber(ccadetailsVO.getMailSequenceNumber());//Added by A-6991 for ICRD-
		revisedCCADetailVO.setCcaReason(ccadetailsVO.getCcaReason());
		revisedCCADetailVO.setCcaRefNumber(ccadetailsVO.getCcaRefNumber());
		revisedCCADetailVO.setCcaRemark(ccadetailsVO.getCcaRemark());
		revisedCCADetailVO.setCcaStatus(ccadetailsVO.getCcaStatus());
		revisedCCADetailVO.setCcaType(ccadetailsVO.getCcaType());
		revisedCCADetailVO.setCompanyCode(ccadetailsVO.getCompanyCode());
		revisedCCADetailVO.setContCurCode(ccadetailsVO.getContCurCode());
		revisedCCADetailVO.setCountryCode(ccadetailsVO.getCountryCode());
		revisedCCADetailVO.setCsgDocumentNumber(ccadetailsVO.getCsgDocumentNumber());
		revisedCCADetailVO.setCsgSequenceNumber(ccadetailsVO.getCsgSequenceNumber());
		revisedCCADetailVO.setCurCode(ccadetailsVO.getCurCode());
		revisedCCADetailVO.setCurrChangeInd(ccadetailsVO.getCurrChangeInd());
		revisedCCADetailVO.setDestination(ccadetailsVO.getDestination());
		revisedCCADetailVO.setDestnCode(ccadetailsVO.getDestnCode());
		revisedCCADetailVO.setDsDate(ccadetailsVO.getDsDate());
		revisedCCADetailVO.setDsnDate(ccadetailsVO.getDsnDate());
		revisedCCADetailVO.setDsnNo(ccadetailsVO.getDsnNo());
		revisedCCADetailVO.setGpaChangeInd(ccadetailsVO.getGpaChangeInd());
		revisedCCADetailVO.setGpaCode(ccadetailsVO.getGpaCode());
		revisedCCADetailVO.setGpaName(ccadetailsVO.getGpaName());
		revisedCCADetailVO.setGrossWeight(ccadetailsVO.getGrossWeight());
		revisedCCADetailVO.setGrossWeightChangeInd(ccadetailsVO.getGrossWeightChangeInd());
		revisedCCADetailVO.setIssueDat(ccadetailsVO.getIssueDat());
		revisedCCADetailVO.setIssueDate(ccadetailsVO.getIssueDate());
		revisedCCADetailVO.setLastUpdateTime(ccadetailsVO.getLastUpdateTime());
		revisedCCADetailVO.setLastUpdateUser(ccadetailsVO.getLastUpdateUser());
		revisedCCADetailVO.setOrigin(ccadetailsVO.getOrigin());
		revisedCCADetailVO.setOriginCode(ccadetailsVO.getOriginCode());
		revisedCCADetailVO.setPoaCode(ccadetailsVO.getPoaCode());
		revisedCCADetailVO.setRevContCurCode(ccadetailsVO.getRevContCurCode());
		revisedCCADetailVO.setRevGpaCode(ccadetailsVO.getRevGpaCode());
		revisedCCADetailVO.setRevGpaName(ccadetailsVO.getRevGpaName());
		revisedCCADetailVO.setRevGrossWeight(ccadetailsVO.getRevGrossWeight());
		revisedCCADetailVO.setSectFrom(ccadetailsVO.getSectFrom());
		revisedCCADetailVO.setSectTo(ccadetailsVO.getSectTo());
		revisedCCADetailVO.setSerialNumber(ccadetailsVO.getSerialNumber());
		revisedCCADetailVO.setSubClass(ccadetailsVO.getSubClass());
		revisedCCADetailVO.setUpdBillTo(ccadetailsVO.getUpdBillTo());
		revisedCCADetailVO.setUpdBillToIdr(ccadetailsVO.getUpdBillToIdr());
		revisedCCADetailVO.setUsrccanum(ccadetailsVO.getUsrccanum());
		revisedCCADetailVO.setYear(ccadetailsVO.getYear());
		revisedCCADetailVO.setGpaArlIndicator(ccadetailsVO.getGpaArlIndicator());
		revisedCCADetailVO.setPayFlag(ccadetailsVO.getPayFlag());
		revisedCCADetailVO.setAutorateFlag(ccadetailsVO.getAutorateFlag());
		revisedCCADetailVO.setSurchargeCCAdetailsVOs(ccadetailsVO.getSurchargeCCAdetailsVOs());
		revisedCCADetailVO.setMailSequenceNumber(ccadetailsVO.getMailSequenceNumber());//Added by A-7929 for ICRD-261091
		revisedCCADetailVO.setRate(ccadetailsVO.getRate());//Added by A-7929 as part of iCRD-132548
		revisedCCADetailVO.setRevisedRate(ccadetailsVO.getRevisedRate()); //Added by A-7929 as part of iCRD-132548
		revisedCCADetailVO.setAutoMca(ccadetailsVO.getAutoMca());  //Added by A-7540 as part of ICRD-132548
		Money chgGrosWt=null;
		Money revChgGrosWt=null;
		Money netAmt=null;
		Money revNetAmt=null;
		Money surCharge = null;
		Money revSurCharge = null;
		if(ccadetailsVO.getContCurCode()!=null){
			try {
				if(ccadetailsVO.getChgGrossWeight()!=null){
					chgGrosWt = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
					chgGrosWt.setAmount((ccadetailsVO.getRevChgGrossWeight().getAmount()));
					revisedCCADetailVO.setChgGrossWeight(chgGrosWt);
					if(ccadetailsVO.getNetAmount()!=null){
						netAmt=CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
						netAmt.setAmount((ccadetailsVO.getRevNetAmount().getAmount()));
						revisedCCADetailVO.setNetAmount(netAmt);
						revisedCCADetailVO.setTds(ccadetailsVO.getRevTds());
						revisedCCADetailVO.setTax(ccadetailsVO.getRevTax());
					}
					if(ccadetailsVO.getOtherChgGrossWgt()!=null){
						surCharge = CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
						surCharge.setAmount((ccadetailsVO.getOtherRevChgGrossWgt().getAmount()));
						revisedCCADetailVO.setOtherChgGrossWgt(surCharge);
					}


				}
				//Modified by A-7794 as part of ICRD-289946
				//if(ccadetailsVO.getGpaCode().equals(ccadetailsVO.getRevGpaCode())){//a-8061  added for ICRD-253127
				if(ccadetailsVO.getRevChgGrossWeight()!=null){
					revChgGrosWt=CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
					revChgGrosWt.setAmount(0);
					revisedCCADetailVO.setRevChgGrossWeight(revChgGrosWt);
					if(ccadetailsVO.getRevNetAmount()!=null){
						revNetAmt=CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
						revNetAmt.setAmount(0);
						revisedCCADetailVO.setRevNetAmount(revNetAmt);
						revisedCCADetailVO.setRevTax(0);
						revisedCCADetailVO.setRevTds(0);
					}
					if(ccadetailsVO.getOtherRevChgGrossWgt()!=null){
						revSurCharge=CurrencyHelper.getMoney(ccadetailsVO.getContCurCode());
						revSurCharge.setAmount(0);
						revisedCCADetailVO.setOtherRevChgGrossWgt(revSurCharge);
					}
				}
				//}
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block

			}
		}
		//revisedCCADetailVO.setPoaCode(ccadetailsVO.getGpaCode());
		if(ccadetailsVO.getIssueDat()!=null){
			revisedCCADetailVO.setIssueDat(ccadetailsVO.getIssueDat());

		}
		else{
			revisedCCADetailVO.setIssueDat(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		}
		//Uncommented by A-7794 as part of ICRD-271248
		revisedCCADetailVO.setRevGpaCode(ccadetailsVO.getGpaCode()); //Commented by A-7929 for ICRD-261091
		revisedCCADetailVO.setRevGpaName(ccadetailsVO.getGpaName()); //Commented by A-7929 for ICRD-261091
		return revisedCCADetailVO;
	}

	/**
	 * Save history for log.
	 *
	 * @param rateauditVO the rateaudit vo
	 * @throws SystemException the system exception
	 * @author A-2391
	 */
	private void saveHistoryForLog(
			RateAuditVO rateauditVO)
	throws SystemException{
		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");

		MRABillingMaster mRABillingMaster = new MRABillingMaster() ;
		RateAuditFilterVO filterVO =new RateAuditFilterVO();
		filterVO.setCompanyCode(rateauditVO.getCompanyCode());
		filterVO.setBillingBasis(rateauditVO.getBillingBasis());
		filterVO.setCsgDocNum(rateauditVO.getConDocNum());
		filterVO.setCsgSeqNum(rateauditVO.getConSerNum());
		filterVO.setGpaCode(rateauditVO.getGpaCode());
		filterVO.setDsnDate(rateauditVO.getDsnDate());
		RateAuditVO rateAuditVO=mRABillingMaster.findListRateAuditDetails(filterVO);
		ArrayList<RateAuditDetailsVO> rateAuditDetailsVOs=new ArrayList<RateAuditDetailsVO>();
		rateAuditDetailsVOs=new  ArrayList<RateAuditDetailsVO>(rateAuditVO.getRateAuditDetails());
		if(rateAuditDetailsVOs!=null && rateAuditDetailsVOs.size()>0){
			for(RateAuditDetailsVO rateAuditDetailsVO:rateAuditDetailsVOs ){
				LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
				rateAuditDetailsVO.setLastUpdateTime(fromDate);
				rateAuditDetailsVO.setLastUpdateUser(rateauditVO.getLastUpdateUser());
				rateAuditDetailsVO.setRemark("");
				rateAuditDetailsVO.setCompTotTrigPt("RA");
				MRABillingDetailsHistory mRABillingDetailsHistory = new MRABillingDetailsHistory(rateAuditDetailsVO);
				log.log(Log.FINE, "Inserted into History table from saveHistoryForLog");
			}
		}
	}


	/**
	 * Find rate audit details print.
	 *
	 * @param reportSpec the report spec
	 * @return the map
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */

	public Map<String, Object> findRateAuditDetailsPrint(

			ReportSpec reportSpec) throws SystemException,MailTrackingMRABusinessException {

		Iterator<Object> filterValues = reportSpec.getFilterValues().iterator();

		RateAuditFilterVO rateAuditFilterVO = (RateAuditFilterVO)filterValues.next();

		Collection<RateAuditVO> rateauditvos =

			MRABillingMaster.findRateAuditDetailsCol(rateAuditFilterVO);
		log.log(Log.INFO, "rateauditvos for print---->", rateauditvos);
		log.log(Log.INFO, "outside null loop---->");

		if (rateauditvos == null || rateauditvos.size() <= 0) {
			log.log(Log.INFO, "inside null loop---->");
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_DEFAULTS_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		reportSpec.setData(rateauditvos);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * Find pa code.
	 *
	 * @param companyCode the company code
	 * @param paCode the pa code
	 * @return the postal administration vo
	 * @throws SystemException the system exception
	 * @author A-3429
	 */
	public PostalAdministrationVO findPACode(String companyCode, String paCode) throws SystemException{

		log.entering("MRA Defaults controller", "findPACode");
		PostalAdministrationVO postalAdministrationVO=null;
		MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
		try {
			postalAdministrationVO =  mailTrackingDefaultsProxy.findPACode(companyCode,paCode);
		} catch (ProxyException e) {
			log.log(Log.SEVERE,"ProxyException Occured!!!!");
		}
		log.exiting("MRA Defaults controller", "findPACode");
		return postalAdministrationVO;

	}

	/**
	 * Generate mail exception report.
	 *
	 * @param mailExceptionReportsFilterVo the mail exception reports filter vo
	 * @return String
	 * @throws SystemException the system exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-2414
	 */
	public String generateMailExceptionReport(MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
	throws SystemException, MailTrackingMRABusinessException {

		log.entering("MRADefaultsController", "generateMailExceptionReport");

		String output = MRAProrationExceptions
		.generateMailExceptionReport(mailExceptionReportsFilterVo);

		if (MRA_DEFAULTS_RETURN_ERROR.equals(output)) {
			throw new MailTrackingMRABusinessException(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENCSVRPT);
		}

		log.exiting("MRADefaultsController", "generateMailExceptionReport");

		return output;

	}

	/**
	 * Find mca lov.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @param displayPage the display page
	 * @return the page
	 * @throws SystemException the system exception
	 * @author a-4823
	 */
	public Page<CCAdetailsVO> findMCALov(CCAdetailsVO ccAdetailsVO,
			int displayPage) throws SystemException{
		return CCADetail.findMCALov(ccAdetailsVO,displayPage);
	}

	/**
	 * Find dsn lov.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @param displayPage the display page
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<CCAdetailsVO> findDSNLov(CCAdetailsVO ccAdetailsVO,
			int displayPage)  throws SystemException{
		return CCADetail.findDSNLov(ccAdetailsVO,displayPage);
	}

	/**
	 * Find approved mca.
	 *
	 * @param maintainCCAFilterVO the maintain cca filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<CCAdetailsVO> findApprovedMCA(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException {
		return CCADetail.findApprovedMCA(maintainCCAFilterVO);
	}

	/**
	 * Find rate line detail.
	 *
	 * @param rateLineFilterVO the rate line filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-5166
	 * Added for ICRD-17262 on 07-Feb-2013
	 */
	public Collection<RateLineVO> findRateLineDetail(RateLineFilterVO rateLineFilterVO)
	 throws SystemException{
		log.entering("MRADefaultsController", "findRateLineDetail");
		 return RateCard.findRateLineDetail(rateLineFilterVO);
	 }

	/**
	 * Gets the scaled value.
	 *
	 * @param value the value
	 * @param precision the precision
	 * @return the scaled value
	 * @author A-4809
	 * to round values to required decimal places
	 */
	private double getScaledValue(double value, int precision) {
		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * Trigger mca flown accounting.
	 *
	 * @param ccaDetailsVo the cca details vo
	 * @throws SystemException the system exception
	 */
	public void triggerMCAFlownAccounting(CCAdetailsVO ccaDetailsVo)
	throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "triggerMCAFlownAccounting");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "importFlownMails");
			mraDefaultsDao.triggerMCAFlownAccounting(ccaDetailsVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Find routing carrier details.
	 *
	 * @param carrierFilterVO the carrier filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-5166
	 */
	public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findRoutingCarrierDetails");
		return DispatchRoutingCarrier.findRoutingCarrierDetails(carrierFilterVO);
	}

	/**
	 * Find overlaping rate line details.
	 *
	 * @param filterVO the filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @author A-5166
	 * Added for ICRD-17262 on 07-Feb-2013
	 * to find the overlapping ratelines
	 * while copying the ratecard
	 */
	private Collection<RateLineVO> findOverlapingRateLineDetails(
			RateLineFilterVO filterVO) throws SystemException{
		log.entering("MRADefaultsController", "findOverlapingRateLineDetails");
		return RateCard.findOverlapingRateLineDetails(filterVO);
	}

	/**
	 * Save routing carrier details.
	 *
	 * @param routingCarrierVOs the routing carrier v os
	 * @throws SystemException the system exception
	 * @author A-5166
	 */
	public void saveRoutingCarrierDetails(Collection<RoutingCarrierVO> routingCarrierVOs) throws SystemException {

		log.entering("MRA Defaults controller", "saveRoutingCarrierDetails");
		log.log(Log.INFO, "routingCarrierVOs in cntrlr-->", routingCarrierVOs);
		int isDuplicateFlag = 0;
		//int count=1;

		for(RoutingCarrierVO routingCarrierVO:routingCarrierVOs){
			if(OPERATION_FLAG_UPDATE.equals(routingCarrierVO
					.getOperationFlag())||OPERATION_FLAG_INSERT.equals(routingCarrierVO
							.getOperationFlag())){
				Collection<RoutingCarrierVO> routingCarrierVOss = new ArrayList<RoutingCarrierVO>();
				RoutingCarrierFilterVO carrierFilterVO=new RoutingCarrierFilterVO();
				carrierFilterVO.setCompanyCode(routingCarrierVO.getCompanyCode());
				carrierFilterVO.setOriginCity(routingCarrierVO.getOriginCity());
				carrierFilterVO.setDestCity(routingCarrierVO.getDestCity());
				carrierFilterVO.setOwnSectorFrm(routingCarrierVO.getOwnSectorFrm());
				carrierFilterVO.setOwnSectorTo(routingCarrierVO.getOwnSectorTo());
				carrierFilterVO.setOalSectorFrm(routingCarrierVO.getOalSectorFrm());
				carrierFilterVO.setOalSectorTo(routingCarrierVO.getOalSectorTo());
				routingCarrierVOss=DispatchRoutingCarrier.findRoutingCarrierDetails(carrierFilterVO);
				//Modified by A-7794 as part of ICRD-285543
				DispatchRoutingCarrier.findCarrierDetails(routingCarrierVO);
				carrierFilterVO.setValidFromDate(routingCarrierVO.getValidFrom());
				carrierFilterVO.setValidFromTo(routingCarrierVO.getValidTo());
				carrierFilterVO.setSequenceNumber(routingCarrierVO.getSequenceNumber());
				if(routingCarrierVOss!=null){
					for (RoutingCarrierVO linevo : routingCarrierVOss) {
						log.log(Log.INFO, "overlapping lines from query ",
								linevo);
						if(carrierFilterVO.getSequenceNumber()!=routingCarrierVO.getSequenceNumber()){
							if ((carrierFilterVO.getValidFromDate().isGreaterThan(
									linevo.getValidFrom()) || carrierFilterVO
									.getValidFromDate().equals(
											linevo.getValidFrom())
											|| (linevo.getValidFrom().isGreaterThan(carrierFilterVO.getValidFromDate())
											&& linevo.getValidFrom().isLesserThan(carrierFilterVO.getValidFromTo())))
									&& (carrierFilterVO.getValidFromTo().isLesserThan(
											linevo.getValidTo()) || carrierFilterVO
											.getValidFromTo().equals(
													linevo.getValidTo())||
													(linevo.getValidTo().isGreaterThan(carrierFilterVO.getValidFromDate())
													&& linevo.getValidTo().isLesserThan(carrierFilterVO.getValidFromTo())))) {
								isDuplicateFlag++;
							}
						}
					}
					log.log(Log.INFO, "value of flag after date range check",
							isDuplicateFlag);
				}
			}

			if(isDuplicateFlag==0){
					DispatchRoutingCarrier dispatchRoutingCarrier = null;
					if (OPERATION_FLAG_INSERT.equals(routingCarrierVO.getOperationFlag())) {
						new DispatchRoutingCarrier(routingCarrierVO);
					}else if (OPERATION_FLAG_UPDATE.equals(routingCarrierVO.getOperationFlag())) {
						try {
							dispatchRoutingCarrier = DispatchRoutingCarrier.find(routingCarrierVO.getCompanyCode(),routingCarrierVO.getOriginCity(),
									routingCarrierVO.getDestCity(),routingCarrierVO.getOwnSectorFrm(),
									routingCarrierVO.getOwnSectorTo(),routingCarrierVO.getOalSectorFrm(),routingCarrierVO.getOalSectorTo(),routingCarrierVO.getSequenceNumber());
						} catch (FinderException finderException) {
							log.log(Log.SEVERE, "FINDER EXCEPTION OCCURED IN FINDING DispatchRoutingCarrier Entity");
							throw new SystemException(finderException.getErrorCode());
						}
						if (dispatchRoutingCarrier != null) {
							dispatchRoutingCarrier.update(routingCarrierVO);
						}
					} else if (OPERATION_FLAG_DELETE.equals(routingCarrierVO.getOperationFlag())) {
						try {
							dispatchRoutingCarrier = DispatchRoutingCarrier.find(routingCarrierVO.getCompanyCode(),routingCarrierVO.getOriginCity(),
									routingCarrierVO.getDestCity(),routingCarrierVO.getOwnSectorFrm(),
									routingCarrierVO.getOwnSectorTo(),routingCarrierVO.getOalSectorFrm(),routingCarrierVO.getOalSectorTo(),routingCarrierVO.getSequenceNumber());
						} catch (FinderException finderException) {
							log.log(Log.SEVERE,"FINDER EXCEPTION OCCURED IN FINDING DispatchRoutingCarrier Entity");
							throw new SystemException(finderException.getErrorCode());
						}
						if (dispatchRoutingCarrier != null) {
							dispatchRoutingCarrier.remove();
						}
					}
					log.exiting(CLASS_NAME, "saveDSNRoutingDetails");
				}else{
					throw new SystemException(
							DuplicateBillingLineException.DUPLICATE_BILLING_LINE_EXIST,
							new DuplicateBillingLineException());
				}
			}
		}

	/**
	 * Save copy rate card.
	 *
	 * @param rateCardVO the rate card vo
	 * @throws SystemException the system exception
	 * @throws RateCardException the rate card exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-5166
	 * Added for ICRD-17262 on 07-Feb-2013
	 * method to save a copied ratecard
	 */
	public void saveCopyRateCard(RateCardVO rateCardVO) throws SystemException, RateCardException, MailTrackingMRABusinessException{

		log.entering("MRADefaultsController", "saveCopyRateCardd");
		String overlapCheck ="false";
		RateLineFilterVO rateLineFilterVO = new RateLineFilterVO();

		rateLineFilterVO.setCompanyCode(rateCardVO.getCompanyCode());
		rateLineFilterVO.setRateCardID(rateCardVO.getRateCardID());
		rateLineFilterVO.setStartDate(rateCardVO.getNewValidStartDate());
		rateLineFilterVO.setEndDate(rateCardVO.getNewValidEndDate());
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode = logon.getCompanyCode();
		String rateCardID = rateCardVO.getNewRateCardID();
		try{
			RateCard ratecard = RateCard.find(companyCode, rateCardID);
			if(ratecard != null){
				throw new MailTrackingMRABusinessException(RateCardException.RATECARD_EXIST);
			}
		}catch(SystemException systemException){
			//throw new SystemException(systemException.getMessage());
		}catch(FinderException finderException){
			log.log(Log.FINE,  "FinderException");
		}

		Collection<RateLineVO> rateLineVOs =findRateLineDetail(rateLineFilterVO);
		StringBuilder rateline =new StringBuilder();
		RateLineFilterVO filterVO = new RateLineFilterVO();
		Collection<RateLineVO> rateLineVos = new ArrayList<RateLineVO> ();
		for(RateLineVO rateLineVO:rateLineVOs){
			filterVO.setCompanyCode(logon.getCompanyCode());
			filterVO.setRateCardID(rateCardVO.getRateCardID());
			filterVO.setOrigin(rateLineVO.getOrigin());
			filterVO.setDestination(rateLineVO.getDestination());
			filterVO.setStartDate(rateCardVO.getNewValidStartDate());
			filterVO.setEndDate(rateCardVO.getNewValidEndDate());

			 rateLineVos = findOverlapingRateLineDetails(filterVO);
			log.log(Log.INFO, "RateLines Overlaping with new RateCard-->",
					rateLineVos);
		   }
			for(RateLineVO rateLineV0:rateLineVos){
			rateline.append(rateLineV0.getRateCardID());
			rateline.append(" ");
			}

		RateCardVO ratecardVO = new RateCardVO();
		ratecardVO.setRateCardID(rateCardVO.getNewRateCardID());
		ratecardVO.setNewMailDistFactor(rateCardVO.getNewMailDistFactor());
		ratecardVO.setNewCategoryTonKMRefOne(rateCardVO.getNewCategoryTonKMRefOne());
		ratecardVO.setNewCategoryTonKMRefTwo(rateCardVO.getNewCategoryTonKMRefTwo());
		ratecardVO.setNewCategoryTonKMRefThree(rateCardVO.getNewCategoryTonKMRefThree());
		ratecardVO.setValidityStartDate(rateCardVO.getNewValidStartDate());
		ratecardVO.setValidityEndDate(rateCardVO.getNewValidEndDate());
		ratecardVO.setOperationFlag(RateCardVO.OPERATION_FLAG_INSERT);
		ratecardVO.setCompanyCode(rateCardVO.getCompanyCode());
		ratecardVO.setRateCardDescription(rateCardVO.getRateCardDescription());
		ratecardVO.setMailDistanceFactor(rateCardVO.getNewMailDistFactor());
		ratecardVO.setCategoryTonKMRefOne(rateCardVO.getNewCategoryTonKMRefOne());
		ratecardVO.setCategoryTonKMRefTwo(rateCardVO.getNewCategoryTonKMRefTwo());
		ratecardVO.setCategoryTonKMRefThree(rateCardVO.getNewCategoryTonKMRefThree());

      	Collection<RateLineVO> removeRateLineVOs = new ArrayList<RateLineVO>();
		for(RateLineVO rateLineVO:rateLineVOs){
			Collection<String> cityPair = new ArrayList<String>();
			Collection <CityPairVO> citypairVOs =new ArrayList<CityPairVO>();
			StringBuilder sb=new StringBuilder(rateLineVO.getOrigin());
			sb.append("-");
			sb.append(rateLineVO.getDestination());
			cityPair.add(sb.toString());
			SharedCitypairProxy sharedCitypairProxy = new SharedCitypairProxy();
			try{
				citypairVOs = sharedCitypairProxy.findCityPair(companyCode, cityPair);
				ArrayList<CityPairVO> citypairvos = new ArrayList<CityPairVO>();
				citypairvos.addAll(citypairVOs);

				//	double iataKM = citypairvos.get(0).getCityPairDistance();
				double iataKM = citypairvos.get(0).getCityPairDistance().getSystemValue();//added by A-7371
					double mailkms = iataKM*rateCardVO.getNewMailDistFactor();

					rateLineVO.setRateCardID(rateCardVO.getNewRateCardID());
					rateLineVO.setIataKilometre(iataKM);
					rateLineVO.setMailKilometre(iataKM*rateCardVO.getNewMailDistFactor());
					rateLineVO.setRateInSDRForCategoryRefOne(getScaledValue((mailkms*rateCardVO.getNewCategoryTonKMRefOne())/1000,4));
					rateLineVO.setRateInSDRForCategoryRefTwo(getScaledValue((mailkms*rateCardVO.getNewCategoryTonKMRefTwo())/1000,4));
					rateLineVO.setRateInSDRForCategoryRefThree(getScaledValue((mailkms*rateCardVO.getNewCategoryTonKMRefThree())/1000,4));
					rateLineVO.setValidityStartDate(ratecardVO.getValidityStartDate());
					rateLineVO.setValidityEndDate(ratecardVO.getValidityEndDate());

			}catch(CityPairBusinessException cityPairBusinessException){
				removeRateLineVOs.add(rateLineVO);
				//throw new SystemException(cityPairBusinessException.getMessage());
			}catch(SharedProxyException sharedProxyException){
				throw new SystemException(sharedProxyException.getMessage());
			}

		}
		rateLineVOs.removeAll(removeRateLineVOs);
		ratecardVO.setRateLineVOs(rateLineVOs);
		log.log(Log.INFO, "rateCardVO after removing ratelines-->", ratecardVO);
		//Collection<RateLineVO> rateLineVOstoremove = new ArrayList<RateLineVO>();
		//Collection<RateLineVO> rateLineVOstosave =new ArrayList<RateLineVO>();
		for (RateLineVO rateLineVoforCompare : rateLineVOs) {
			//if("N".equalsIgnoreCase(rateLineVoforCompare.getOverlapflag())){
			HashMap<String, RateLineVO> rateLineVoforChk = new HashMap<String, RateLineVO>();
			//String rateID = rateLineVoforCompare.getRateCardID();
			String origin = rateLineVoforCompare.getOrigin();
			String destination = rateLineVoforCompare.getDestination();
			LocalDate fromDate = rateCardVO.getValidityStartDate();
			LocalDate toDate = rateCardVO.getValidityEndDate();
			String keyForCompare = origin.concat(destination);
			rateLineVoforChk.put(keyForCompare, rateLineVoforCompare);
			boolean isPresent = rateLineVoforChk.containsKey(keyForCompare);
			if (isPresent) {
			log.log(Log.INFO, "-->>INSIDE PRESENT->>>>>>>>>>>>>>>>>>. ");
			RateLineVO rateLineVoInMap = rateLineVoforChk.get(keyForCompare);

			if(origin.equals(rateLineVoInMap.getOrigin())
				&& destination.equals(rateLineVoInMap.getDestination())
				&&((((rateLineVoInMap.getValidityStartDate().compareTo(fromDate))>=0)
							&&((rateLineVoInMap.getValidityStartDate().compareTo(toDate))<=0))
					 ||(((rateLineVoInMap.getValidityEndDate().compareTo(fromDate))>=0)
							&&((rateLineVoInMap.getValidityEndDate().compareTo(toDate))<=0))
					 ||(((fromDate.compareTo(rateLineVoInMap.getValidityStartDate()))>=0)
							&&((toDate.compareTo(rateLineVoInMap.getValidityEndDate()))<=0))
					 ||(((fromDate.compareTo(rateLineVoInMap.getValidityStartDate()))<=0)
							&&((toDate.compareTo(rateLineVoInMap.getValidityEndDate()))>=0))))

			{
					log.log(Log.INFO,"INSIDE IF BEFORE THROWING EXCEPTION><<<>>>>>>>>>>>>>>>. ");

			throw new MailTrackingMRABusinessException("mailtracking.mra.defaults.ratecardoverlap");
			}
			}
		 if("Y".equalsIgnoreCase(rateLineVoforCompare.getOverlapflag())){
				overlapCheck ="true";
			}
		}
		if(("true").equalsIgnoreCase(overlapCheck)){
			throw new SystemException("mailtracking.mra.defaults.ratelinesexistforcopied",new String []{rateline.toString()});
		}
		try{

			saveRateCard(ratecardVO);

			}catch(RateCardException rateCardException){
				throw new SystemException(rateCardException.getMessage());
			}catch(RateLineException rateLineException){
				throw new SystemException(rateLineException.getMessage());
			}
		log.exiting("MRADefaultsController", "saveCopyRateCard");
	}

	/**
	 * Initiate proration.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 * @author 5166
	 * Added for ICRD-36146 on 06-Mar-2013
	 */
	public void initiateProration(String companyCode)
	throws SystemException{
	log.entering(CLASS_NAME, "initiateProration");
	new MRAProrationFactor().initiateProration(companyCode);
	}


	/**
	 * Save billing site details.
	 *
	 * @param billingSiteVO the billing site vo
	 * @return the billing site vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @throws RemoteException the remote exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 */
	@Advice(name = "mail.mra.saveBillingSiteDetails" , phase=Phase.POST_INVOKE)
	public BillingSiteVO saveBillingSiteDetails(BillingSiteVO billingSiteVO)
	throws SystemException, PersistenceException,RemoteException, MailTrackingMRABusinessException{
		log.entering(CLASS_NAME, "saveBillingSiteDetails");
		BillingSiteMaster billingSiteMaster=new BillingSiteMaster();
		BillingSiteDetailsAuditVO auditVO = new BillingSiteDetailsAuditVO(BillingSiteDetailsAuditVO.AUDIT_MODULENAME,
				BillingSiteDetailsAuditVO.AUDIT_SUBMODULENAME,BillingSiteDetailsAuditVO.AUDIT_ENTITY);
		try{
			billingSiteMaster = BillingSiteMaster.find(billingSiteVO.getCompanyCode(),billingSiteVO.getBillingSiteCode());
			if(billingSiteMaster!=null && OPERATION_FLAG_INSERT.equals(billingSiteVO.getOperationFlag())){
				billingSiteVO.setDuplicate(true);
				return billingSiteVO;
			}
			if(!OPERATION_FLAG_DELETE.equals(billingSiteVO.getOperationFlag())){
				BillingSiteVO billingSiteUpdateVO=findCountryOverlapping(billingSiteVO);
				if(billingSiteUpdateVO.isOverlapping()){
					return billingSiteVO;
				}
			}
			if(OPERATION_FLAG_DELETE.equals(billingSiteVO.getOperationFlag())){
				if(billingSiteMaster.getBillingSiteCountryDetails()!=null && billingSiteMaster.getBillingSiteCountryDetails().size()>0){
					for(BillingSiteCountryDetails billingSiteCountryDetails :billingSiteMaster.getBillingSiteCountryDetails()){
						billingSiteCountryDetails.remove();
					}
				}
				if(billingSiteMaster.getBillingSiteBankDetails()!=null && billingSiteMaster.getBillingSiteBankDetails().size()>0){
					for(BillingSiteBankDetails billingSiteBankDetails : billingSiteMaster.getBillingSiteBankDetails()){
						billingSiteBankDetails.remove();
					}
				}
				billingSiteMaster.remove();
			}
			else{
				auditVO = (BillingSiteDetailsAuditVO)AuditUtils.populateAuditDetails(auditVO, billingSiteMaster, false);
				//billingSiteMaster=BillingSiteMaster.find(billingSiteVO.getCompanyCode(), billingSiteVO.getBillingSiteCode());
				billingSiteMaster.update(billingSiteVO);
				auditVO = (BillingSiteDetailsAuditVO)AuditUtils.populateAuditDetails(auditVO, billingSiteMaster, false);
				findHistoryValues(auditVO,billingSiteVO);
				auditVO.setCompanyCode(billingSiteVO.getCompanyCode());
				auditVO.setBillingSiteCode(billingSiteVO.getBillingSiteCode());
				auditVO.setActionCode(BillingSiteDetailsAuditVO.BILLINGSITE_MODIFIED);
				auditVO.setAuditRemarks("Billing Site Updated");
				StringBuilder additionalinfo=new StringBuilder().append(billingSiteVO.getNewFieldValue());
				auditVO.setAdditionalInformation(additionalinfo.toString());
				AuditUtils.performAudit(auditVO);
			}
		}
		catch(Exception exception){
			BillingSiteVO billingSiteUpdateVO=findCountryOverlapping(billingSiteVO);
			if(billingSiteUpdateVO.isOverlapping()){
				return billingSiteVO;
			}
			log.log(Log.INFO, "ERRORS>>>>>>>", exception.getMessage());
			if(OPERATION_FLAG_INSERT.equals(billingSiteVO.getOperationFlag())){
				genereateBillingSiteCode(billingSiteVO);
				billingSiteMaster=new BillingSiteMaster(billingSiteVO);
			}
		}
		return billingSiteVO;
	}



	/**
	 * Find billing site details.
	 *
	 * @param billingSiteFilterVO the billing site filter vo
	 * @return the collection
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	throws RemoteException,SystemException{
		log.entering(CLASS_NAME, "findBillingSiteDetails");
		BillingSiteMaster billingSiteMaster = new BillingSiteMaster();
		return billingSiteMaster.findBillingSiteDetails(billingSiteFilterVO);
	}

	/**
	 * List parameter lov.
	 *
	 * @param bsLovFilterVo the bs lov filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<BillingSiteLOVVO> listParameterLov(

			BillingSiteLOVFilterVO bsLovFilterVo) throws SystemException {

				return BillingSiteMaster.listParameterLov(bsLovFilterVo);

			}


	/**
	 * Find country overlapping.
	 *
	 * @param billingSiteVO the billing site vo
	 * @return the billing site vo
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	public BillingSiteVO findCountryOverlapping(BillingSiteVO billingSiteVO)
	throws RemoteException,SystemException{
		BillingSiteMaster billingSiteMaster = new BillingSiteMaster();
		Collection<BillingSiteVO> billingSiteVOFromServer=null;
		BillingSiteVO billingSiteUpdateVO= billingSiteVO;
		StringBuffer countries=new StringBuffer("");
		if(billingSiteVO.getBillingSiteGPACountriesVO()!=null && billingSiteVO.getBillingSiteGPACountriesVO().size()>0){
			for(BillingSiteGPACountriesVO billingSiteCountriesVO:billingSiteVO.getBillingSiteGPACountriesVO()){
				try{
					if(billingSiteCountriesVO.getOperationalFlag()!=null && !OPERATION_FLAG_DELETE.equals(billingSiteCountriesVO.getOperationalFlag()) && !"N".equals(billingSiteCountriesVO.getOperationalFlag())){
						String str[]=billingSiteCountriesVO.getGpaCountry().split(",");
						for(String country:str){
							billingSiteVO.setGpaCountry(country);
							billingSiteVOFromServer=billingSiteMaster.findCountryOverLapping(billingSiteVO);
							if(billingSiteVOFromServer!=null && billingSiteVOFromServer.size()>0){
								for(BillingSiteVO returnedVO:billingSiteVOFromServer){
									if(billingSiteVO.isAutoGenerate())
										{
										billingSiteVO.setBillingSiteCode("");
										}
									if((billingSiteVO.getGpaCountry().equals(billingSiteUpdateVO.getGpaCountry())&& !billingSiteVO.getBillingSiteCode().equals(returnedVO.getBillingSiteCode()))){
										billingSiteUpdateVO.setOverlapping(true);
										countries.append(country.concat(", "));
										break;
									}
								}
							}
						}
					}
					else if(OPERATION_FLAG_DELETE.equals(billingSiteCountriesVO.getOperationalFlag()) && billingSiteVO.getBillingSiteGPACountriesVO().size()==1){
						billingSiteVOFromServer=billingSiteMaster.findCountryOverLapping(billingSiteVO);
						if(billingSiteVOFromServer!=null && billingSiteVOFromServer.size()>0){
							for(BillingSiteVO siteVO:billingSiteVOFromServer){
								if(siteVO.getBillingSiteGPACountriesVO().isEmpty()){
									String code=siteVO.getBillingSiteCode();
									if(!code.equals(billingSiteVO.getBillingSiteCode())){
										if(billingSiteVO.isAutoGenerate())
											{
											billingSiteVO.setBillingSiteCode("");
											}
										billingSiteUpdateVO.setOverlapping(true);
										return billingSiteUpdateVO;
									}
								}
								else{
									for(BillingSiteGPACountriesVO countryVO:siteVO.getBillingSiteGPACountriesVO()){
										if(!siteVO.getBillingSiteCode().equals(billingSiteVO.getBillingSiteCode()) && countryVO.getGpaCountry()==null){
											if(billingSiteVO.isAutoGenerate())
												{
												billingSiteVO.setBillingSiteCode("");
												}
											billingSiteUpdateVO.setOverlapping(true);
											return billingSiteUpdateVO;
										}
									}
								}
							}
						}
					}
					else
						{
						continue;
						}
				}
				catch(Exception exception){
					log.log(Log.INFO, "ERRORS>>>>>>", exception.getMessage());
					continue;
				}
				if(countries!=null && countries.length()>0)
					{
					billingSiteUpdateVO.setGpaCountry(countries.substring(0, countries.length()-2).toString());
					}
				return billingSiteUpdateVO;
				}
			}
		else{
			try{
				billingSiteVOFromServer=billingSiteMaster.findCountryOverLapping(billingSiteVO);
				if(billingSiteVOFromServer!=null && billingSiteVOFromServer.size()>0){
					for(BillingSiteVO siteVO:billingSiteVOFromServer){
						if(!siteVO.getBillingSiteGPACountriesVO().isEmpty()){
							for(BillingSiteGPACountriesVO countryVO:siteVO.getBillingSiteGPACountriesVO()){
								String code=siteVO.getBillingSiteCode();
								if(!code.equals(billingSiteVO.getBillingSiteCode()) && countryVO.getGpaCountry()==null){
									if(billingSiteVO.isAutoGenerate())
										{
										billingSiteVO.setBillingSiteCode("");
										}
									billingSiteUpdateVO.setOverlapping(true);
									return billingSiteUpdateVO;
								}
							}
						}
						else{
							String code=siteVO.getBillingSiteCode();
							if(!code.equals(billingSiteVO.getBillingSiteCode())){
								if(billingSiteVO.isAutoGenerate())
									{
									billingSiteVO.setBillingSiteCode("");
									}
								billingSiteUpdateVO.setOverlapping(true);
								return billingSiteUpdateVO;
							}
				}
			}
				}
			}
			catch(Exception exception){
				log.log(Log.INFO, "ERRORS>>>>>>>", exception.getMessage());
			}
		}
		if(countries!=null && countries.length()>0)
			{
			billingSiteUpdateVO.setGpaCountry(countries.substring(0, countries.length()-2).toString());
			}
		return billingSiteUpdateVO;
	}



	/**
	 * Find history values.
	 *
	 * @param auditVO the audit vo
	 * @param billingSitevo the billing sitevo
	 */
	private void findHistoryValues(BillingSiteDetailsAuditVO auditVO,BillingSiteVO billingSitevo){
		StringBuffer newFieldInfo = new StringBuffer();
		StringBuffer addInfo=new StringBuffer();
		if ((auditVO.getAuditFields() != null) && (auditVO.getAuditFields().size() > 0)){
			for (AuditFieldVO auditField : auditVO.getAuditFields())
		    {
				if (auditField != null)
			    {
					if(!auditField.getOldValue().equals(auditField.getNewValue())){
						addInfo.append(auditField.getDescription()).append(",");
						StringBuffer oldValue=new StringBuffer();
						StringBuffer newValue=new StringBuffer();
						if("Billing Site Country Details".equals(auditField.getDescription())){
							oldValue.append(auditField.getOldValue());
							newValue.append(auditField.getNewValue());

						String s1=oldValue.toString().replace("Country Code :", "");
						String s2=newValue.toString().replace("Country Code :", "");
						auditField.setOldValue(s1);
						auditField.setNewValue(s2);
						}
						if("Billing Site Bank Details".equals(auditField.getDescription())){
							oldValue.append(auditField.getOldValue());
							newValue.append(auditField.getNewValue());
						}
						newFieldInfo.append(auditField.getDescription()).append(":").append(" Old Value:< ").append(auditField.getOldValue()).append(" > ");
			          if (auditField.getNewValue() != null) {
			              newFieldInfo.append("New Value:< ").append(auditField.getNewValue()).append(" > ");
			            }
			          }
			    }
		    }
		}
		String info=addInfo.substring(0, addInfo.length()-1).concat(" has been modified. The old value and new values associated are- ");
		billingSitevo.setNewFieldValue(info.concat((newFieldInfo.toString()).toString()));
	}

	/**
	 * Genereate billing site code.
	 *
	 * @param billingSiteVO the billing site vo
	 * @throws SystemException the system exception
	 */
	public void genereateBillingSiteCode(BillingSiteVO billingSiteVO)throws SystemException{
		boolean isGeneretedCorrect=true;
		String siteCode="";
		String key="MRA";
		BillingSiteMaster billingSiteMaster=new BillingSiteMaster();
		try{
			while(isGeneretedCorrect){
				siteCode = BillingSiteMaster.generateSerialNumber(billingSiteVO.getCompanyCode(),key);
			billingSiteVO.setGeneratedBillingSiteCode(siteCode);
			billingSiteMaster = BillingSiteMaster.find(billingSiteVO.getCompanyCode(), billingSiteVO.getGeneratedBillingSiteCode());
			if(!siteCode.equals(billingSiteMaster.getBillingSiteMasterPK().getBillingSiteCode()));
				isGeneretedCorrect=false;
			}

		}
		catch(FinderException exception){
			if(billingSiteVO.isAutoGenerate())
				{
				billingSiteVO.setBillingSiteCode(siteCode);
				}
			return;
		}
	}
	/**
	 * Process billing matrix upload details.
	 *
	 * @param fileUploadFilterVO the file upload filter vo
	 * @return the string
	 * @throws SystemException the system exception
	 */
	public String processBillingMatrixUploadDetails(FileUploadFilterVO fileUploadFilterVO) throws SystemException {
	    return BillingMatrix.processBillingMatrixUploadDetails(fileUploadFilterVO);
	  }
	/**
	 * Trigger mca accounting for Internal MCA
	 *
	 * @param ccaDetailsVo the cca details vo
	 * @throws SystemException the system exception
	 */
	public void triggerMCAInternalAccounting(CCAdetailsVO ccaDetailsVo)
	throws SystemException {
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "triggerMCAInternalAccounting");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "importInternalAccountingMails");
			mraDefaultsDao.triggerMCAInternalAccounting(ccaDetailsVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	  }

	/**
	 * 	Method		:	MRADefaultsController.prorateExceptionFlights
	 *	Added by 	:	A-6245 on 17-06-2015
	 * @param flightValidationVOs
	 * @throws SystemException
	 */
	@Advice(name = "mail.operations.prorateExceptionFlights" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
	throws SystemException{
		log.entering("MRADefaultsController", "prorateExceptionFlights");
		MRAProrationExceptions.prorateExceptionFlights(flightValidationVOs);
		if(isTaxRequired()){
		for(FlightValidationVO flightValidationVO:flightValidationVOs){
			updateTax(flightValidationVO);
		}
		}

	}

	/**
	 * @throws SystemException
	 * Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 *
	 */
	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO )
			throws SystemException{

		try {
			return new CRADefaultsProxy().initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}

}

	/**
	 * @throws SystemException
	 * Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 *
	 */
	public boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws SystemException{

		MRADefaultsDAO mraDefaultsDao = null;

		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "validateReportingPeriod");
			return  mraDefaultsDao.validateGpaBillingPeriod(generateInvoiceFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

    }

	/**
	 *
	 * @author A-5255
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException {
		log.entering("MRADefaultsController", "viewSurchargeProrationDetails");
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = null;

		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			surchargeProrationDetailsVOs = mraDefaultsDao
					.viewSurchargeProrationDetails(prorationFilterVO);
			log.exiting("MRADefaultsController", "viewSurchargeProrationDetails");
			return surchargeProrationDetailsVOs;

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @author A-5255
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeCCAdetailsVO> getSurchargeCCADetails(
			MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException {
		log.entering("MRADefaultsController", "getSurchargeCCADetails");
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs = null;

		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			surchargeCCAdetailsVOs = mraDefaultsDao
					.getSurchargeCCADetails(maintainCCAFilterVO);
			log.exiting("MRADefaultsController", "getSurchargeCCADetails");
			return surchargeCCAdetailsVOs;

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(
			CN51CN66FilterVO cn51CN66FilterVO)throws SystemException{
		log.entering("MRADefaultsController", "getSurchargeCCADetails");
		MRADefaultsDAO mraDefaultsDao = null;
		 Collection<SurchargeBillingDetailVO> surchargeBillingDetailVOs=null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			return mraDefaultsDao.findSurchargeBillingDetails(cn51CN66FilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @author A-5255
	 * @param blgMatrixFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(
			BillingMatrixFilterVO blgMatrixFilterVO) throws SystemException {
		log.entering("MRADefaultsController", "getSurchargeCCADetails");
		MRADefaultsDAO mraDefaultsDao = null;
		 Collection<AuditDetailsVO> auditDetailsVO=null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			return mraDefaultsDao.findBillingMatrixAuditDetails(blgMatrixFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	  }
	/**
	 * @author A-6245
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(
			CN51CN66FilterVO cn51CN66FilterVO)throws SystemException{
		log.entering("MRADefaultsController", "findSurchargeBillableDetails");
		MRADefaultsDAO mraDefaultsDao = null;
		 Collection<SurchargeBillingDetailVO> surchargeBillingDetailVOs=null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			return mraDefaultsDao.findSurchargeBillableDetails(cn51CN66FilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	private boolean checkWorkFlowEnabled()throws SystemException{
		Boolean isWorkFlowEnabled=false;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		//system parameter for accounting
		systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		if (systemParameters != null) {
			String workFlowEnabled = (systemParameters.get(SYS_PARAM_WRKFLOWENABLED));
			if( "Y".equals(workFlowEnabled) || "R".equals(workFlowEnabled)){
				isWorkFlowEnabled=true;
			}
		}
		return isWorkFlowEnabled;
	}

	/**
	 * @author A-6245
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(
			ProrationFilterVO prorationFilterVO) throws SystemException {
		log.entering("MRADefaultsController", "viewSurchargeProrationDetailsForMCA");
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			surchargeProrationDetailsVOs = mraDefaultsDao
					.viewSurchargeProrationDetailsForMCA(prorationFilterVO);
			log.exiting("MRADefaultsController", "viewSurchargeProrationDetailsForMCA");
			return surchargeProrationDetailsVOs;
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-6245
	 * Generate Interface file from shared proxy
	 * @param uploadTime
	 * @param uploadTimeStr
	 * @throws SystemException
	 */
	@Raise(module="mail", submodule="mra", event="FILE_GEN_EVENT", methodId="mail.mra.generateInterfaceFile")
	public void generateInterfaceFile(LocalDate uploadTime,String uploadTimeStr)
	throws SystemException{
	log.entering(CLASS_NAME, "generateInterfaceFile");
	SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
	String companyCode=logonAttributes.getCompanyCode();
	FileGenerateVO fileGenerateVO=new FileGenerateVO();
	FileGenerateConfigVO fileGenerateConfigVO=getFileGenerateConfigVO(companyCode);
	//LocalDate uploadTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
	fileGenerateVO.setStatus(FileGenerateVO.GENERATE_STATUS_IN_PROGRESS);
	fileGenerateVO.setAppliedConfiguration(fileGenerateConfigVO);
	fileGenerateVO.setFileType(fileGenerateConfigVO.getFileType());
	fileGenerateVO.setCompanyCode(companyCode);
	fileGenerateVO.setStationCode(logonAttributes.getStationCode());
	fileGenerateVO.setReconstructConfig(true);
	fileGenerateVO.setUploadStartTime(uploadTime);
	fileGenerateVO.setUploadEndTime(uploadTime);
	fileGenerateVO.setLastUpdatedTime(uploadTime);
	fileGenerateVO.setLastUpdatedUser(logonAttributes.getUserId());

	sharedDefaultsProxy.doGenerate(fileGenerateVO);

	try{
		updateInterfacedMails(companyCode);
	} catch (PersistenceException e) {
		throw new SystemException(e.getErrorCode());
	}
	log.exiting(CLASS_NAME, "generateInterfaceFile");
	}
	/**
	 * @author A-6245
	 * Build FileGenerateConfigVO for generateInterfaceFile
	 * @param companyCode
	 * @return fileGenerateConfigVO
	 */
	public FileGenerateConfigVO getFileGenerateConfigVO(String companyCode){
		FileGenerateConfigVO fileGenerateConfigVO=new FileGenerateConfigVO();
		Collection<FileGenerateFilterVO> screenFilters=getScreenFilters(companyCode);
		fileGenerateConfigVO.setScreenFilters(screenFilters);
		fileGenerateConfigVO.setCompanyCode(companyCode);
		fileGenerateConfigVO.setFileType(MRA_INTERFACE_FILTYP);
		return fileGenerateConfigVO;
	}
	/**
	 * Get Screen filters for FileGenerateConfigVO
	 * @author A-6245
	 * @param companyCode
	 * @return
	 */
	public Collection<FileGenerateFilterVO> getScreenFilters(String companyCode){
		Collection<FileGenerateFilterVO>screenFilters=new ArrayList<FileGenerateFilterVO>();
		FileGenerateFilterVO fromDateFilter=getFileGenerateFilterVO(MRA_INTERFACE_FROMDATE_FILTER_CODE);
		FileGenerateFilterVO toDateFilter=getFileGenerateFilterVO(MRA_INTERFACE_TODATE_FILTER_CODE);
		FileGenerateFilterVO interfacedFilter=getFileGenerateFilterVO(INTERFACED_FILTER_CODE);
		screenFilters.add(fromDateFilter);
		screenFilters.add(toDateFilter);
		screenFilters.add(interfacedFilter);
		return screenFilters;
	}
	/**
	 * Build FileGenerateFilterVO for Screen Filters
	 * @author A-6245
	 * @param format
	 * @param fieldType
	 * @param filterCode
	 * @return
	 */
	public FileGenerateFilterVO getFileGenerateFilterVO(String filterCode){
		FileGenerateFilterVO fileGenerateFilterVO=new FileGenerateFilterVO();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		systemParameterCodes.add(SYS_PARAM_DAYS_FOR_SAFT);
		systemParameterCodes.add(SYS_PARAM_FRMINVDAT_FOR_SAFT);
		Map<String, String> systemParameters = null;
		String period="";
		if(INTERFACED_FILTER_CODE.equals(filterCode)){
			fileGenerateFilterVO.setFilterCode(filterCode);
			fileGenerateFilterVO.setFilterValue(INTERFACED_FILTER_VALUE);
		}else{
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		fileGenerateFilterVO.setFilterCode(filterCode);
			try {
				systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
			} catch (ProxyException e) {
				log.log(Log.SEVERE, "PROXY EXCEPTION OCCURED IN FINDING SYSTEM PARAMETER:" +
						SYS_PARAM_DAYS_FOR_SAFT);
			}catch(SystemException e){
				log.log(Log.SEVERE, "SYSTEM EXCEPTION OCCURED IN FINDING SYSTEM PARAMETER:" +
						SYS_PARAM_DAYS_FOR_SAFT);
				}
			if (systemParameters!=null){
				 period = (systemParameters.get(SYS_PARAM_DAYS_FOR_SAFT));
			}
			if(MRA_INTERFACE_FROMDATE_FILTER_CODE.equals(filterCode)){
				if(period!=null&&period.trim().length()>0){
				if("0".equals(period)){
					period = (systemParameters.get(SYS_PARAM_FRMINVDAT_FOR_SAFT));//Date Period if syspar value is 0
				fileGenerateFilterVO.setFilterValue(currentDate.addDays(-1*(Integer.parseInt(period)-1))
						.toDisplayDateOnlyFormat());
				}else{
				fileGenerateFilterVO.setFilterValue(currentDate.addDays(-1*(Integer.parseInt(period)))
						.toDisplayDateOnlyFormat());
					}
				}
		}else{
			if("0".equals(period)){
				fileGenerateFilterVO.setFilterValue(currentDate.
						toDisplayDateOnlyFormat());//Date range=sysdt-pd to sysdt if syspar=0
			}else{
				fileGenerateFilterVO.setFilterValue(currentDate.addDays(-1).
						toDisplayDateOnlyFormat());//Date range=sysdt-pd to sysdt-1 if syspar!=0
			}
		}
	}
		return fileGenerateFilterVO;
	}
	/**
	 * @author A-6245
	 * @param fileGenerateVO
	 * @return outParameter
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String updateInterfacedMails(String companyCode)
			throws SystemException,PersistenceException{
		log.entering(CLASS_NAME, "updateInterfacedMails");
		MRADefaultsDAO mraDefaultsDao = null;
		String outParameter="";
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			outParameter=mraDefaultsDao.updateInterfacedMails(
					companyCode,
					getFileGenerateFilterVO(MRA_INTERFACE_FROMDATE_FILTER_CODE).
					getFilterValue(),
					getFileGenerateFilterVO(MRA_INTERFACE_TODATE_FILTER_CODE).
					getFilterValue()
					);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "updateInterfacedMails");
		return outParameter;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isTaxRequired()throws SystemException{
		log.entering(CLASS_NAME, "isTaxRequired");
		boolean taxFlag = false;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		GeneralParameterConfigurationVO configVO = new GeneralParameterConfigurationVO();
		configVO.setCompanyCode(logonAttributes.getCompanyCode());
		configVO.setMasterType(MRA_CONFIGURATION);
		configVO.setConfigurationReferenceOne(TAX_GROUP);
		configVO.setParmeterCode(TAX_PARAMETER);
		SharedDefaultsProxy proxy = new SharedDefaultsProxy();
		Map<String, HashMap<String, String>> configParams = null;
		try{
			configParams = proxy.findGeneralParameterConfigurationDetails(configVO);
		}catch(Exception exception){
			log.log(Log.FINE,exception.getMessage());
		}
		if(configParams!=null && !configParams.isEmpty() && configParams.containsKey(TAX_GROUP)){
			Map<String, String> map = configParams.get(TAX_GROUP);
			if(map!=null && !map.isEmpty() && map.containsKey(TAX_PARAMETER)){
				String value = map.get(TAX_PARAMETER);
				if("Y".equals(value))
					{
					taxFlag = true;
			}
		}
		}
		log.exiting(CLASS_NAME, "isTaxRequired");
		return taxFlag;
	}
	/**
	 * Find despatch details.
	 *
	 * @param popUpVO the pop up vo
	 * @return DespatchEnquiryVO
	 * @throws SystemException the system exception
	 */
	public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException{
		log.entering(CLASS_NAME, "findDespatchDetails");
		return MRABillingMaster.findDespatchDetails(popUpVO);
	}

	/**
	 * 
	 * 	Method		:	MRADefaultsController.reRateMailbags
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@param txnlogInfo
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Advice(name = "mail.operations.reRateMailbags" , phase=Phase.POST_INVOKE)
	public void reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)
		throws SystemException{
		MaintainCCAFilterVO maintainCCAFilterVO = null;
		StringBuilder newCCABlgbas = new StringBuilder("");
		String status = "";
		int count = 0;
		boolean flag=false;//Added by a-7531 for icrd 253020
		Collection<DocumentBillingDetailsVO> billingDetailsVOs=new ArrayList<DocumentBillingDetailsVO>();
		for(DocumentBillingDetailsVO blgVO : documentBillingDetailsVOs){
			count++;
			maintainCCAFilterVO = new MaintainCCAFilterVO();
			maintainCCAFilterVO.setCompanyCode(blgVO.getCompanyCode());
			maintainCCAFilterVO.setBillingBasis(blgVO.getBillingBasis());
			maintainCCAFilterVO.setConsignmentDocNum(blgVO.getCsgDocumentNumber());
			maintainCCAFilterVO.setConsignmentSeqNum(blgVO.getCsgSequenceNumber());
			maintainCCAFilterVO.setPOACode(blgVO.getPoaCode());
			
			String ccaStatus ="";
			try{
				ccaStatus = CCADetail.findCCAStatus(maintainCCAFilterVO);
			}catch(SystemException exception){
				log.log(Log.FINE,exception.getMessage());
			}
			if(ccaStatus!=null && (STATUS_NEW.equals(ccaStatus)||STATUS_APPROVE.equals(ccaStatus))){
				status = FAILED;
				flag=true;//Added by a-7531 for icrd 253020
				newCCABlgbas.append(blgVO.getBillingBasis()).append(",");
				billingDetailsVOs.add(blgVO);
				//documentBillingDetailsVOs.remove(blgVO);
				if(count >= documentBillingDetailsVOs.size())
					{
					break;
			}
			}
		}
		if(billingDetailsVOs!=null && billingDetailsVOs.size()>0){
		documentBillingDetailsVOs.removeAll(billingDetailsVOs);
		}
		
		String output[] = new String[2];
		if(!documentBillingDetailsVOs.isEmpty()){
			 output = new MRABillingMaster().reRateMailbags(documentBillingDetailsVOs).split("-");
		}
		if(newCCABlgbas.length() > 0){
			if(billingDetailsVOs.size()>10){//added as part of ICRD-277615
				count=billingDetailsVOs.size()+Integer.parseInt(output[1]);
			}else{
			newCCABlgbas.substring(0, newCCABlgbas.length()-1);
		}
		}
		String processStatus = output[0];
		if(processStatus == null)
			{
			processStatus = "";
			}
		if(status.length() >0){
			processStatus = processStatus.concat(",").concat(status);
		}
		String notRatedBlgBasis = output[1];
		if(notRatedBlgBasis == null)
			{
			notRatedBlgBasis="";
			}
		notRatedBlgBasis = notRatedBlgBasis.concat(newCCABlgbas.toString());
		String txnInfo[] = txnlogInfo.split("-");
		String txnCod = txnInfo[0];
		int serNum = Integer.parseInt(txnInfo[1]);
		StringBuilder remarks = new StringBuilder("");
		if(documentBillingDetailsVOs.isEmpty()){//Added for icrd-254820
			processStatus = FAILED;
			remarks=new StringBuilder("").append("No Eligible Records were Identified"); 
		}
		else if(processStatus.contains(FAILED) && processStatus.contains(COMPLETED)){
			processStatus =PARTIALLY_COMPLETED;
			if(count>10){//added as part of ICRD-277615
				remarks = new StringBuilder("").append(+count+"Mails not re-rated ");
			}
			else{
			remarks = new StringBuilder("").append("Mails not re-rated ").append(notRatedBlgBasis.substring(0,notRatedBlgBasis.length()-1));
			}
		}else if(!processStatus.contains(COMPLETED)){
			processStatus = FAILED;
			if (flag){//Added by a-7531 for icrd 253020
				remarks = new StringBuilder("").append("Mails not re-rated since MCA exists");
			}
			else{
			remarks = new StringBuilder("").append("Mails not re-rated ").append(notRatedBlgBasis.substring(0,notRatedBlgBasis.length()-1));
			}
		}else{
			processStatus = COMPLETED;
			remarks = new StringBuilder("").append("Mails re-rating success");
		}
		
		 InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
		    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBERERATED);
			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	   		invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
	   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
			invoiceTransactionLogVO.setRemarks(remarks.toString());
			invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		    invoiceTransactionLogVO.setSerialNumber(serNum);
		    invoiceTransactionLogVO.setTransactionCode(txnCod);
		
		    
		
		try {
		     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * 	Method		:	MRADefaultsController.findRerateBillableMails
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@param documentBillingVO
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	
	public Collection<DocumentBillingDetailsVO> findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,String companyCode)
			throws SystemException{
		log.entering(CLASS_NAME, "findRerateBillableMails");
		return MRABillingMaster.findRerateBillableMails(documentBillingVO,companyCode);
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.findRerateInterlineBillableMails
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@param documentBillingVO
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	public Collection<DocumentBillingDetailsVO> findRerateInterlineBillableMails(
			DocumentBillingDetailsVO documentBillingVO,String companyCode) throws SystemException {
		log.entering(CLASS_NAME, "findRerateInterlineBillableMails");
		return MRABillingMaster.findRerateInterlineBillableMails(documentBillingVO,companyCode) ;
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.importMRAData
	 *	Added by 	:	A-3429 
	 *	Parameters	:	@param rateAuditVos
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	
	 */
	@Advice(name = "mail.operations.importMRAData" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void importMRAData(Collection<RateAuditVO> rateAuditVos) throws SystemException {
		log.entering(CLASS_NAME, "importMRAData");
		Map<String, String> systemParameters = null;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		String isValidAgent = "N";
		systemParameterCodes.add(SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		systemParameterCodes.add(SYS_PARA_AGENTTYPE_RESTRICT_IMP);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		String triggerForMRAImport = (systemParameters.get(SYS_PARA_TRIGGER_FOR_MRAIMPORT));//need to check 
		//TODO check whether call is from configured trigger points
		String agentType = (systemParameters.get(SYS_PARA_AGENTTYPE_RESTRICT_IMP));
		for(RateAuditVO rateAuditVO : rateAuditVos){
		//for(RateAuditVO rateAuditVO : rateAuditVos){
			/*RateAuditVO rateAuditvoForInsert= new RateAuditVO();
			try{
				BeanUtils.copyProperties(rateAuditvoForInsert, rateAuditVO);
			} catch (IllegalAccessException ex) {
				throw new SystemException(ex.getMessage());
			} catch (InvocationTargetException ex) {
				throw new SystemException(ex.getMessage());
			}
			rateAuditvoForInsert.setRateAuditDetails(null);*/
			if (agentType!=null && "NA".equals(agentType) ) {
				isValidAgent="Y";
			}else{
			isValidAgent = validateAgent(rateAuditVO,agentType);
			}
			if(isValidAgent != null && "Y".equalsIgnoreCase(isValidAgent)){
			/*try{
				MRABillingMaster.find(rateAuditVO.getCompanyCode(),rateAuditVO.getMailSequenceNumber());
			} catch (FinderException e) {			
				rateAuditvoForInsert.setMailStatus("N");
				new MRABillingMaster(rateAuditvoForInsert);
			}*/
			try{
				saveMRADataForRatingJob(rateAuditVO);
			}catch(SystemException e){
				e.printStackTrace();
			}
			}
		}
			
		/*if(isTaxRequired()){ 
				
				
				updateTaxForMRA(rateAuditVO.getCompanyCode());
					
				
			}*/
						
		//}		 
		
	}
	
	/**
	 * 
	 * 	Method		:	MRADefaultsController.updateTaxForMRA
	 *	Added by 	:	A-7531 on 13-May-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void updateTaxForMRA(String companyCode) throws SystemException {
		// TODO Auto-generated method stub
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<MRABillingDetailsVO> mraBillingDetails = null;
		Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
		log.entering(CLASS_NAME, "updateTaxForMRA");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "updateTaxForMRA");
			mraBillingDetails = mraDefaultsDao.findBillingEntriesAtMailbagLevel(companyCode);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		
		
		
		if(mraBillingDetails!=null && !mraBillingDetails.isEmpty()){ 
			log.log(Log.FINE, "MRA billing details obtained",mraBillingDetails);
			String taxValues= taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
			if((null!=taxValues) &&(!taxValues.equals("")))
			{
				double tax=Double.parseDouble(taxValues);
			taxMraIntUpdate(mraBillingDetails, tax);
			}
			else
			{
			HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails
			= findTaxDetails(mraBillingDetails);
			log.log(Log.FINE, "Tax details obtained",taxDetails);
			for(MRABillingDetailsVO billingDetailsVO :mraBillingDetails ){
				/*
				 * update billing details vo with taxvo. Use key from the taxDetails and billing basis +payment flag from billingDetailsVO
				 *
				 */

				/*
				 * Find the tax corresponding to the despatch. Check with Raiz whether tax needs to be calculated for
				 * each billing basis.
				 * iterate tax details and find and update MRABillingDetailsEntity with tax
				 */
				updateBillingEntryWithTax(billingDetailsVO,taxDetails);
				MailDetailsTemp mailDetailsTemp=null;

				try {
					 mailDetailsTemp=MailDetailsTemp.find(billingDetailsVO);
					 mailDetailsTemp.setTaxUpdationFlag("Y");
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*if(mailDetailsTemp!=null&&"P".equals(mailDetailsTemp.getProcessStatus())&&"N".equals(mailDetailsTemp.getTaxUpdationFlag())){
					//mailDetailsTemp.setTaxUpdationFlag("Y");
				}*/

			}
		}
		}
		
		
		
		
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.importMRAData
	 *	Added by 	:	A-3429 
	 *	Parameters	:	@param rateAuditVos
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	
	 */
	private String validateAgent(RateAuditVO rateAuditVO, String agentType) throws SystemException {
		return MRABillingMaster.validateAgent(rateAuditVO,agentType);
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.importMRAData
	 *	Added by 	:	A-3429 
	 *	Parameters	:	@param rateAuditVos
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	
	 */
	public void saveMRADataForRatingJob(RateAuditVO rateAuditVO) throws SystemException {
		MRABillingMaster.saveMRADataForRatingJob(rateAuditVO);
	}
	
	public CCAdetailsVO constructDetailVO(CCADetail detail,CCAdetailsVO vo){
		
		vo.setGrossWeight(detail.getGrossWeight());
		vo.setRevGrossWeight(detail.getRevGrossWeight());
		vo.setGpaCode(detail.getGpaCode());
		vo.setGpaName(detail.getGpaName());
		vo.setBillingStatus(detail.getBillingStatus());
		vo.setContCurCode(detail.getContCurCode());
		vo.setCurrChangeInd(detail.getCurChgInd());
		//vo.setDueArl(detail.getDueArl());
		//vo.setDuePostDbt(detail.getDuePostDbt());
		vo.setGpaChangeInd(detail.getGpaChangeInd());
		vo.setGrossWeightChangeInd(detail.getGrossWeightChangeInd());
		vo.setLastUpdateUser(detail.getLastUpdatedUser());
		vo.setCcaRemark(detail.getMcaRemark());
		vo.setCcaStatus(detail.getMcaStatus());
		vo.setCcaType(detail.getMcaType());
		//vo.setNetAmount(detail.getNetAmount());
		vo.setRevContCurCode(detail.getRevContCurCod());
		//vo.setRevDueArl(detail.getRevDueArl();
		vo.setRevDuePostDbt(detail.getRevDuePostDbt());
		//vo.setRevNetAmount(detail.getRevNetAmount());
		vo.setRevTax(detail.getRevSrvTax());
		//vo.setRevSurChg(detail.getRevSurChargeCTR());
		vo.setRevTds(detail.getRevTds());
		vo.setAutoMca(detail.getAutoMca());
		vo.setSectFrom(detail.getSectFrom());
		vo.setSectTo(detail.getSectTo());
		vo.setTax(detail.getServiceTax());
		//vo.setSurChg(detail.getSurChargeCTR();
		vo.setTds(detail.getTds());
		vo.setWeightChargeChangeInd(detail.getWeightChargeChangeInd());
		vo.setWtChgAmtinCTR(detail.getWeightChargeCTR());
		return vo;
		
	}
	/**
	 * 	Method		:	MRADefaultsController.findReproarteMails
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingVO
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	public Collection<DocumentBillingDetailsVO> findReproarteMails(
			DocumentBillingDetailsVO documentBillingVO)  throws SystemException{
		log.entering(CLASS_NAME, "findReproarteMails");
		return MRABillingMaster.findReproarteMails(documentBillingVO);
	}

	/**
	 * 	Method		:	MRADefaultsController.reProrateExceptionMails
	 *	Added by 	:	A-7531 on 09-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@param txnlogInfo 
	 *	Return type	: 	void
	 */
	public void reProrateExceptionMails(
			Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs,
			String txnlogInfo) throws SystemException{
		
		String txnInfo[] = txnlogInfo.split("-");
		String txnCod = txnInfo[0];
		int serNum = Integer.parseInt(txnInfo[1]);
		String output[]=new String[3];
		
		if(!documentBillingDetailsVOs.isEmpty()){
			output= new MRABillingMaster().reProrateExceptionMails(documentBillingDetailsVOs).split("-");
		
		}
		String processStatus=output[0];  
		String count=output[1];
		StringBuilder remarks = null;
		if(processStatus!=null && (processStatus.contains(COMPLETED)&&!processStatus.contains(FAILED)))
		{
			processStatus =COMPLETED;
			remarks=new StringBuilder("").append(count).append(" ").append("mails are prorated by the system");
		}else if(processStatus!=null && (processStatus.contains(COMPLETED)&& processStatus.contains(FAILED)))
		{
			processStatus =PARTIALLY_COMPLETED;
			remarks=new StringBuilder("").append(count).append(" ").append("mails are prorated by the system");
		}else if(processStatus!=null && processStatus.contains(FAILED))
		{
			processStatus =FAILED;
			remarks=new StringBuilder("").append("Mails are not prorated");
		}else if(documentBillingDetailsVOs.isEmpty()){    
			processStatus =FAILED;    
			remarks=new StringBuilder("").append("No Eligible Records were Identified");       
		}
		
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
	    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBEREPORATED);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
   		if(remarks!=null)    
		{
		invoiceTransactionLogVO.setRemarks(remarks.toString());
		}
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
	    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
	    invoiceTransactionLogVO.setSerialNumber(serNum);
	    invoiceTransactionLogVO.setTransactionCode(txnCod);
	
		 
	
	try {
	     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
	} catch (ProxyException e) {
		throw new SystemException(e.getMessage());
	}
	}
/**
 * @author A-7371
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 */
	public Collection<AWMProrationDetailsVO>  viewAWMProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException{
		MRADefaultsDAO mraDefaultsDao = null;
		Collection<AWMProrationDetailsVO> awmProrationDetailsVOs = null;
		try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em
				.getQueryDAO("mail.mra.defaults"));
		awmProrationDetailsVOs = mraDefaultsDao
				.viewAWMProrationDetails(prorationFilterVO);
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
		log.exiting("MRADefaultsController", "viewAWMProrationDetails");
		return awmProrationDetailsVOs;
	}	

   
	/**
	 * 
	 * 	Method		:	MRADefaultsController.createJobforFlightRevenueInterface
	 *	Added by 	:	a-8061 on 22-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void createJobforFlightRevenueInterface() throws SystemException{
		
		MailBillingInterfaceFileJobScheduleVO mailBillingInterfaceFileJobScheduleVO=new MailBillingInterfaceFileJobScheduleVO();
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		JobVO jobVO=null;
		try {
			jobVO= SchedulerAgent.getInstance().findJob("MAL_BILLINGINTERFACE_JOB");
		} catch (JobSchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Map<String,String> att= new HashMap<String,String>();
		att.put("JOB_EVENT", "MAL_FLIGHTREVINTERFACE");
		Collection<ScheduleVO>  scheduleVOs= new ArrayList<ScheduleVO>();
		ScheduleVO scheduleVO= new ScheduleVO();
		scheduleVO.setOwnerId(jobVO.getJobId());
		scheduleVO.setCompanyCode(logonAttributes.getCompanyCode());
		scheduleVO.setAttributes(att);
		try {
			scheduleVOs= 	SchedulerAgent.getInstance().findExistingSchedulesForJob(scheduleVO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//added for ICRD-314111 ,if  one time schedule exist then no need to create one time job

		if(scheduleVOs!=null  && scheduleVOs.size() < 1){
		mailBillingInterfaceFileJobScheduleVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailBillingInterfaceFileJobScheduleVO.setJobName("MAL_BILLINGINTERFACE_JOB");
		LocalDate currentTime = new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true);		
		mailBillingInterfaceFileJobScheduleVO.setStartTime(currentTime);
		currentTime = new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true);
		currentTime = currentTime.addMinutes(30);
		mailBillingInterfaceFileJobScheduleVO.setEndTime(currentTime);
		mailBillingInterfaceFileJobScheduleVO.setValue(3, "MAL_FLIGHTREVINTERFACE");
		try {
			SchedulerAgent.getInstance().createScheduleForJob(mailBillingInterfaceFileJobScheduleVO);
		} catch (JobSchedulerException | SystemException e) {
			log.log(Log.SEVERE, "JobSchedulerException Caught job not found !");
			throw new SystemException(e.getMessage());
		}

		}

		
	}
	
	/**
	 * 
	 * 	Method		:	MRADefaultsController.doInterfaceFlightRevenueDtls
	 *	Added by 	:	a-8061 on 12-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param companyCode 
	 *	Return type	: 	void
	 */
	public void doInterfaceFlightRevenueDtls(String companyCode){
		log.entering(CLASS_NAME, "doInterfaceFlightRevenueDtls");
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.updateTruckCost
	 *	Added by 	:	a-8061 on 17-Jul-2018
	 * 	Used for 	:	ICRD-237070
	 *	Parameters	:	@param truckOrderMailVO 
	 *	Return type	: 	void
	 */
	@Advice(name = "mail.operations.updateTruckCost" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void updateTruckCost(TruckOrderMailVO truckOrderMailVO)throws SystemException {
		MRADefaultsDAO mraDefaultsDao = null;
		
		
		Map<String, String> systemParameters = null;
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		String truckcostFlag =null;
		systemParameterCodes.add(SYS_PARA_TRUCKCOSTFORWEIGHTCHARGE);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}
		if (systemParameters != null) {
		 truckcostFlag = (systemParameters.get(SYS_PARA_TRUCKCOSTFORWEIGHTCHARGE));
		}
		
		EntityManager em = PersistenceController.getEntityManager();
		try {
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		
	
		if(FLAG_YES.equals(truckcostFlag)&&truckOrderMailVO.getTruckOrderMailAWBVO()!=null && ! truckOrderMailVO.getTruckOrderMailAWBVO().isEmpty()){
			
			for(TruckOrderMailAWBVO truckOrderMailAWBVO : truckOrderMailVO.getTruckOrderMailAWBVO()){

				try {
					
					mraDefaultsDao.updateTruckCost(truckOrderMailAWBVO,truckOrderMailVO);
					
				} catch (PersistenceException persistenceException) {
					throw new SystemException(persistenceException.getErrorCode());
				}
			}
		}

		if(truckOrderMailVO.getTruckOrderMailBagVO()!=null && ! truckOrderMailVO.getTruckOrderMailBagVO().isEmpty()){
			
			// call AA method.
			//AA method to be written only after AA CR is in jira and 
			//requirements are in place
			
		}
	}
	
	/**
	 * @author a-8061
	 * @param dSNRoutingVO
	 * @return
	 */
	public String validateBSA(DSNRoutingVO dSNRoutingVO) throws SystemException {

		 return DSNRouting.validateBSA(dSNRoutingVO);
			 

		}
   /**
	 * 
	 * 	Method		:	MRADefaultsController.constructDAO
	 *	Added by 	:	A-7929 on 25-Jun-2018
	 * 	Used for 	:   ICRD-237091
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MRADefaultsDAO
	 */
	 private static MRADefaultsDAO constructDAO()throws SystemException {
	    	
			try {
				EntityManager entityManager = PersistenceController.getEntityManager();
				return MRADefaultsDAO.class.cast(entityManager.getQueryDAO(MODULE_NAME));
			}
			catch(PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.prorateMCA
	 *	Added by 	:	A-7929 on 25-Jun-2018
	 * 	Used for 	:   ICRD-237091 
	 *	Parameters	:	@param ccaRefNo
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void prorateMCA(String ccaRefNo,String currChangeFlag) throws SystemException {//modified for ICRD-282931
		log.entering(CLASS_NAME, "prorateMCA");
		constructDAO().prorateMCA(ccaRefNo,currChangeFlag);
		log.exiting(CLASS_NAME, "prorateMCA");
		
	}

/**
 * 
 * 	Method		:	MRADefaultsController.saveAutoMCAdetails
 *	Added by 	:	A-7929 on 26-Jul-2018
 * 	Used for 	:
 *	Parameters	:	@param cCAdetailsVOs  
*	Parameters	:	@return 
 *	Return type	: 	String
 * @param documentBillingDetailsVO 
 * @throws SystemException 
 * @throws RemoteException 
 * @throws BusinessDelegateException 
 * @throws MailTrackingMRABusinessException 
 */
	
@Advice(name = "mail.mra.saveAutoMCAdetails" , phase=Phase.POST_INVOKE)
public void saveAutoMCAdetails(Collection<CCAdetailsVO> cCAdetailsVOs,GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws MailTrackingMRABusinessException, BusinessDelegateException, RemoteException, SystemException {
	
	MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
	Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = new ArrayList<DocumentBillingDetailsVO>();
	Collection<CCAdetailsVO> ccaDetailVOs = new ArrayList<CCAdetailsVO>();
	MRADefaultsDAO mraDefaultsDao = null;
	if(gpaBillingEntriesFilterVO.getCompanyCode()!=null){
	try {
	EntityManager em = PersistenceController.getEntityManager();
	mraDefaultsDao = MRADefaultsDAO.class.cast(em
			.getQueryDAO("mail.mra.defaults"));
	documentBillingDetailsVOs = mraDefaultsDao
			.findGPABillingEntriesForAutoMCA(gpaBillingEntriesFilterVO);
} catch (PersistenceException persistenceException) {
	throw new SystemException(persistenceException.getErrorCode());
}
	
	}		
	
	double revRate = 0;
	String revGpaCode = null;
	String revCurr = null;
	String ccaStatus = null;
	double surRevRate = 0;
	double surAmount = 0;
	Money surChgAmt=null;
	boolean listMCAFlag = false;
	boolean isVoidMailbag = false;
	Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVO=null;
	//to find rate    
	
	String ccaRemark = null;
		
		CCAdetailsVO ccaDetailsVO = ((ArrayList<CCAdetailsVO>)cCAdetailsVOs).get(0);
		revRate = ccaDetailsVO.getRevisedRate();	
		revGpaCode = ccaDetailsVO.getRevGpaCode();
		revCurr = ccaDetailsVO.getRevContCurCode();
		ccaStatus = ccaDetailsVO.getCcaStatus();
		isVoidMailbag = ccaDetailsVO.isVoidMailbag();
		 surchargeCCAdetailsVO = ccaDetailsVO.getSurchargeCCAdetailsVOs();
		 ccaRemark = ccaDetailsVO.getCcaRemark();
		 
		 if(surchargeCCAdetailsVO!=null && surchargeCCAdetailsVO.size()>0){
		 for(SurchargeCCAdetailsVO surChgVO:surchargeCCAdetailsVO){
			 surRevRate=surChgVO.getSurchargeRevRate();
	}
 }
		 if(documentBillingDetailsVOs != null && documentBillingDetailsVOs.size() > 0){

			 maintainCCAFilterVO.setCompanyCode( ((ArrayList<DocumentBillingDetailsVO>)documentBillingDetailsVOs).get(0).getCompanyCode());
			 maintainCCAFilterVO.setDsnNumber( ((ArrayList<DocumentBillingDetailsVO>)documentBillingDetailsVOs).get(0).getDsn());
			 maintainCCAFilterVO.setGpaCode( ((ArrayList<DocumentBillingDetailsVO>)documentBillingDetailsVOs).get(0).getGpaCode());//Modified by a-7871 for ICRD-293403
			 if(isVoidMailbag){
				 maintainCCAFilterVO.setBillingBasis(((ArrayList<DocumentBillingDetailsVO>)documentBillingDetailsVOs).get(0).getBillingBasis());
			 }

			try{
				EntityManager em = PersistenceController.getEntityManager();
				mraDefaultsDao = MRADefaultsDAO.class.cast(em
						.getQueryDAO("mail.mra.defaults"));
				ccaDetailVOs = mraDefaultsDao
						.findCCAdetails(maintainCCAFilterVO);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}  
		 }
	Collection<CCAdetailsVO> detailsVOs = new ArrayList<CCAdetailsVO>(); 
	if(documentBillingDetailsVOs != null && documentBillingDetailsVOs.size() > 0){
	listMCAFlag=true;
	for(DocumentBillingDetailsVO documentBillingDetailsVO : documentBillingDetailsVOs){
		CCAdetailsVO ccadetails = new CCAdetailsVO();          
		for(CCAdetailsVO ccAdetailsVO:ccaDetailVOs){
			if(ccAdetailsVO.getCompanyCode().equals(documentBillingDetailsVO.getCompanyCode())&& ccAdetailsVO.getMailSequenceNumber()==documentBillingDetailsVO.getMailSequenceNumber()&&
					ccAdetailsVO.getBlgDtlSeqNum()==documentBillingDetailsVO.getSerialNumber()){
				//Added by A-7540 as part of ICRD-288846
				if(!STATUS_NEW.equals(ccAdetailsVO.getCcaStatus())){
		//ccastatus
		ccadetails.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
		ccadetails.setMailSequenceNumber(documentBillingDetailsVO.getMailSequenceNumber());
		//ccadetails.setCcaType(documentBillingDetailsVOs.getCcaType());
		ccadetails.setCcaType(CCA_TYPE_ACTUAL); //added by A-8464 for ICRD-280176
		// ccaref number
		ccadetails.setGrossWeight(documentBillingDetailsVO.getWeight());
		ccadetails.setVoidMailbag(isVoidMailbag);
		ccadetails.setRevGrossWeight(documentBillingDetailsVO.getWeight());
		if(surRevRate!=0){
				  if(documentBillingDetailsVO.getWeight()!=null){
					  surAmount=surRevRate* documentBillingDetailsVO.getWeight();
					  try {
						  surChgAmt=CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					  surChgAmt.setAmount(surAmount);
					  } catch (CurrencyException e) {
							log.log(Log.FINE,"Inside CurrencyException.. ");
						}
				  }
		}
		
		ccadetails.setGpaCode(documentBillingDetailsVO.getGpaCode());
		ccadetails.setCcaRemark(documentBillingDetailsVO.getRemarks());
		ccadetails.setGrossWeightChangeInd("N");
		ccadetails.setSectFrom(documentBillingDetailsVO.getSectorFrom());
		ccadetails.setSectTo(documentBillingDetailsVO.getSectorTo());
		ccadetails.setContCurCode(ccAdetailsVO.getContCurCode());
		ccadetails.setTds(documentBillingDetailsVO.getTds());
		//ccadetails.setTax(documentBillingDetailsVOs.getServiceTax());  tax
		if(!isVoidMailbag)
		ccadetails.setRevChgGrossWeight(ccAdetailsVO.getRevChgGrossWeight());
	    ccadetails.setWeightChargeChangeInd("N");
	    ccadetails.setRate(documentBillingDetailsVO.getApplicableRate());
	    
	    if(ccadetails.getRate() != revRate){ 
	    ccadetails.setRevisedRate(revRate);
	    ccadetails.setRateChangeInd("Y");
	    ccadetails.setWeightChargeChangeInd("Y");
	    }
	    else{
	    ccadetails.setRevisedRate(ccAdetailsVO.getRevisedRate());	
	    ccadetails.setRateChangeInd("N");
	    }
	    if(!ccadetails.getGpaCode().equals(revGpaCode)){
	    ccadetails.setRevGpaCode(revGpaCode);
	    ccadetails.setGpaChangeInd("Y");
	    }
	    else{
	    ccadetails.setRevGpaCode(documentBillingDetailsVO.getGpaCode());
	    ccadetails.setGpaChangeInd("N");
	    }
	    if(!ccadetails.getContCurCode().equals(revCurr)){
	    ccadetails.setRevContCurCode(revCurr);
	    ccadetails.setCurrChangeInd("Y");
	    }
	    else{
	    ccadetails.setRevContCurCode(ccAdetailsVO.getContCurCode());
	    ccadetails.setCurrChangeInd("N");
	    }
	    ccadetails.setRevTds(documentBillingDetailsVO.getTds());
	    
	    
	    ccadetails.setRevTax(documentBillingDetailsVO.getServiceTax().getAmount());
	    if(!isVoidMailbag){
	    ccadetails.setNetAmount(ccAdetailsVO.getNetAmount());
	    }else{
	    	ccadetails.setNetAmount(documentBillingDetailsVO.getNetAmount());
	    }
	    ccadetails.setBillingPeriodFrom(documentBillingDetailsVO.getFromDate());
	    ccadetails.setBillingPeriodTo(documentBillingDetailsVO.getToDate());
	    ccadetails.setCcaRemark(documentBillingDetailsVO.getRemarks());
	    ccadetails.setBillingStatus(BILLABLE);
	    ccadetails.setLastUpdateTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    ccadetails.setCcaRefNumber(documentBillingDetailsVO.getCcaRefNumber());
	    ccadetails.setAutoMca("Y");
	    ccadetails.setCcaStatus(ccaStatus);
	    ccadetails.setChgGrossWeight(ccAdetailsVO.getChgGrossWeight());
	    ccadetails.setOtherChgGrossWgt(ccAdetailsVO.getOtherChgGrossWgt());
	    if(surRevRate!=0){
	    	 ccadetails.setOtherRevChgGrossWgt(surChgAmt);
	    }else{
	    ccadetails.setOtherRevChgGrossWgt(ccAdetailsVO.getOtherRevChgGrossWgt());
	    }
	    ccadetails.setContCurCode(ccAdetailsVO.getContCurCode());
	    ccadetails.setRevGpaName(documentBillingDetailsVO.getPoaCode());
	    if(isVoidMailbag){
	    	if(documentBillingDetailsVO.getPoaCode() == null)
	    		ccadetails.setPoaCode(documentBillingDetailsVO.getGpaCode());
	    }else{
	    ccadetails.setPoaCode(documentBillingDetailsVO.getPoaCode());
	    }
	    ccadetails.setMailbagId(documentBillingDetailsVO.getBillingBasis());
	    ccadetails.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
	    ccadetails.setDsnDate(documentBillingDetailsVO.getDsnDate().toDisplayFormat());
	    ccadetails.setBlgDtlSeqNum(ccAdetailsVO.getBlgDtlSeqNum());
	    
		if(revRate != 0 ){
		double netAmountValue = 0.0;
		Money netAmt;
		try {
			netAmt = CurrencyHelper.getMoney(ccAdetailsVO.getContCurCode());
			if(!isVoidMailbag)
		    netAmountValue = (documentBillingDetailsVO.getWeight()*revRate ); 
		    netAmt.setAmount(netAmountValue);
		    
		    ccadetails.setRevChgGrossWeight(netAmt);
		    //ccadetails.setRevNetAmount(netAmt); //Rev net amount will be updated below after calculations,hence not needed here: ICRD-289070
		   // ccadetails.setWeightChargeChangeInd("Y");
		} catch (CurrencyException e) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		
		}
		 double netAmount = 0.0;
		 Money totNetAmt = null;
		 if(!isVoidMailbag)
		 netAmount=ccadetails.getRevChgGrossWeight().getAmount()+ccadetails.getOtherRevChgGrossWgt().getAmount()+ ccadetails.getRevTax() - ccadetails.getRevTds();
		 try {
			 totNetAmt = CurrencyHelper.getMoney(ccAdetailsVO.getContCurCode());
			 totNetAmt.setAmount(netAmount);
			 ccadetails.setRevNetAmount(totNetAmt);//totnetamt is the revised net amount, not org net amount: ICRD-289070
			//Modified by A-7794 as part of ICRD-299050
			 ccadetails.setRevDueArl(totNetAmt);
		 }catch (CurrencyException e) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
		 
		 
		 
		ccadetails.setOperationFlag(OPERATION_FLAG_INSERT);
		/*if((documentBillingDetailsVOs.getCcaRefNumber()!=null) && (documentBillingDetailsVOs.getCcaRefNumber().trim().length() > 0)){
			ccadetails.setOperationFlag(OPERATION_FLAG_UPDATE);
		}*/
		
		ccadetails.setCcaRemark(ccaRemark);
		
		detailsVOs.add(ccadetails);
		}
		}
		}
	}
 }
	//Added by A-7540
	else if(!listMCAFlag)
	{ 
		detailsVOs.addAll(cCAdetailsVOs);
		updateMcaStatus(detailsVOs);
	}
	
	//MaintainCCAFilterVO maintainCCAFilterVO = null;
	for(CCAdetailsVO detailsVO : detailsVOs){
		
		CCADetail cCADetail = null;
		String ccaRefNo = "INVALID";
		if (OPERATION_FLAG_INSERT.equals(detailsVO.getOperationFlag())) {
					if(APPROVED.equals(detailsVO.getCcaStatus()) && !isVoidMailbag){
						if(!"OH".equals(detailsVO.getBillingStatus()))
						{
							detailsVO.setBillingStatus(BILLABLE);
						}
						saveHistoryDetails(detailsVO);  // needed again change wen workflow is disabled.
						
						//tax calculation
						//computeTax(detailsVO);
						//update CCADetail entity
						
						ccaRefNo = detailsVO.getCcaRefNumber();
						}
		            else{
		            	cCADetail = new CCADetail(detailsVO);
		            	ccaRefNo=cCADetail.getCCADetailsPK().getMcaRefNumber();
		             }				
		}

		else{
		    try {
						cCADetail = CCADetail.find(detailsVO);
					} catch (FinderException e) {
						cCADetail = new CCADetail(detailsVO);
			    }
		    
		    ccaRefNo=cCADetail.getCCADetailsPK().getMcaRefNumber();
		    
		    if (OPERATION_FLAG_UPDATE.equals(detailsVO.getOperationFlag())){
		    	if(cCADetail.getMcaStatus().equals(NEW)){
				//computeTax(detailsVO);
		    	if(detailsVO.getRevisedRate() != detailsVO.getRate() ){
		    	cCADetail.setRevisedRate(detailsVO.getRevisedRate());
		    	cCADetail.setRevNetAmount(detailsVO.getRevNetAmount().getAmount());
		    	cCADetail.setWeightChargeChangeInd("Y");
		    	
		    	}
		    	if(!detailsVO.getRevContCurCode().equals(detailsVO.getContCurCode())){
		    		cCADetail.setRevContCurCod(detailsVO.getRevContCurCode());
		    		cCADetail.setCurChgInd("Y");
		    	}
                if(!detailsVO.getRevGpaCode().equals(detailsVO.getGpaCode())){
                	cCADetail.setRevGpaCode(detailsVO.getRevGpaCode());
                	cCADetail.setGpaChangeInd("Y");
		    	}
                
               
		    	
		    }
		    }
		}
		detailsVO.setCcaRefNumber(ccaRefNo);
		
	
	}
	cCAdetailsVOs.removeAll(cCAdetailsVOs);
	cCAdetailsVOs.addAll(detailsVOs);
	updateInvoiceTransferLogForAutoMca(gpaBillingEntriesFilterVO);
	releaseLocks(constructAutoMraMcaLockVOs());
	
}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.updateMailBSAInterfacedDetails
	 *	Added by 	:	a-8061 on 27-Aug-2018
	 * 	Used for 	:
	 *	Parameters	:	@param flightPk 
	 *	Return type	: 	void
	 */
	public void updateMailBSAInterfacedDetails(BlockSpaceFlightSegmentVO flightPk) throws SystemException {
		// TODO Auto-generated method stub
		MRADefaultsDAO mraDefaultsDao = null;
		try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em
				.getQueryDAO("mail.mra.defaults"));
		  mraDefaultsDao
				.updateMailBSAInterfacedDetails(flightPk);
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
		
		
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.findBlockSpaceFlights
	 *	Added by 	:	a-8061 on 11-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CRAFlightFinaliseVO>
	 */
	public Collection<CRAFlightFinaliseVO>  findBlockSpaceFlights(String companyCode) throws SystemException{
		
		MRADefaultsDAO mraDefaultsDao = null;
		Collection <CRAFlightFinaliseVO> blockspaceFlights = null;
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em
				.getQueryDAO("mail.mra.defaults"));

		blockspaceFlights =  mraDefaultsDao
				.findBlockSpaceFlights(companyCode);
		
		if(blockspaceFlights != null && !blockspaceFlights.isEmpty()){
			for(CRAFlightFinaliseVO cRAFlightFinaliseVO :blockspaceFlights ){
				cRAFlightFinaliseVO.setLastUpdatedUser(logonAttributes.getUserId());
				cRAFlightFinaliseVO.setTriggerPoint("MRA");
				cRAFlightFinaliseVO.setCompanyCode(companyCode);
			}
		}

	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}		
		return blockspaceFlights;
		
	}
	
	
	


//Added by A-7540
private void updateMcaStatus(Collection<CCAdetailsVO> cCAdetailsVOs) throws SystemException, BusinessDelegateException, RemoteException {
       for(CCAdetailsVO detailsVO : cCAdetailsVOs){
		
		CCADetail cCADetail = null;
	   try {
			cCADetail = CCADetail.find(detailsVO);
		} catch (FinderException e) {
			//cCADetail = new CCADetail(detailsVO);
       }
	   String mailBag=detailsVO.getBillingBasis();	  //added for ICRD-282931
	   String cgsDocnum=detailsVO.getCsgDocumentNumber();
	   int csgSeqnum=detailsVO.getCsgSequenceNumber();
	   String dsnDate=detailsVO.getDsnDate();
	   String opFlag=detailsVO.getOperationFlag();
	   String mcaStatus=detailsVO.getCcaStatus();
	   Double netval=detailsVO.getNetValue();//Added as part of ICRD-340886
	   int blgDtlSeqnum=detailsVO.getBlgDtlSeqNum();
	   //LocalDate issueDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(detailsVO.getIssueDate());
	   LocalDate issueDate=new LocalDate(LocalDate.NO_STATION, Location.NONE,true);
	  // issueDate.setDate(detailsVO.getIssueDate(), "yyyy-MM-dd");
	   detailsVO=cCADetail.retrieveVO();
	   detailsVO.setBillingBasis(mailBag);
	   detailsVO.setCsgDocumentNumber(cgsDocnum);
	   detailsVO.setCsgSequenceNumber(csgSeqnum);
	   detailsVO.setDsnDate(dsnDate);
	   detailsVO.setCcaStatus(mcaStatus);
	   detailsVO.setOperationFlag(opFlag);
	   detailsVO.setIssueDat(issueDate);
	   detailsVO.setBlgDtlSeqNum(blgDtlSeqnum);
	   detailsVO.setNetValue(netval);//Added as part of ICRD-340886
	   if(cCADetail !=null){
	   detailsVO.setMcaReasonCodes(cCADetail.getMcaReasonCodes());
	   }   
	   Map<String, String> systemParameterValues = null;
	   try {
			/** getting collections of OneTimeVOs */
			systemParameterValues=new SharedDefaultsProxy().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (ProxyException e) {
    		e.getMessage();
		}
	   if(systemParameterValues!=null && systemParameterValues.size()>0
      		  && systemParameterValues.containsValue(BASED_ON_RULES)){//Added For IASCB-2373
		   detailsVO.setAcceptRejectIdentifier(ACCEPTMCA);
        }
       if (OPERATION_FLAG_UPDATE.equals(detailsVO.getOperationFlag())){
	    	if(detailsVO.getCcaStatus().equals(APPROVED) && NEW.equals(cCADetail.getMcaStatus())){
	    	//	Commented as part of ICRD-337574
	    		//cCADetail.setMcaStatus(detailsVO.getCcaStatus());
	    		 saveHistoryDetails(detailsVO);	 //added for ICRD-282931
	    	  }
	    	else{
	    		cCADetail.setMcaStatus(detailsVO.getCcaStatus());
	    	  }
	    	}
       else{
    	   cCADetail.setMcaStatus(REJECTED);
       }

       }
    }
/*
 * Added as part of ICRD-308518 
 */
private void updateExistingDSNVOs(DSNRouting routing) throws SystemException{
	LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
			.getSecurityContext().getLogonAttributesVO();
	if("B".equals(routing.getBlockSpaceType())|| routing.getBsaReference()!=null){
		CRAFlightFinaliseVO cRAFlightFinaliseVO = new CRAFlightFinaliseVO();
		cRAFlightFinaliseVO.setCompanyCode(routing.getDSNRoutingPK().getCompanyCode());
		cRAFlightFinaliseVO.setFlightCarrierIdr(routing.getFlightCarrierID());
		cRAFlightFinaliseVO.setFlightNumber(routing.getFlightNumber());
		cRAFlightFinaliseVO.setFlightSeqNumber(routing.getFlightSequenceNumber());
		cRAFlightFinaliseVO.setSegSerialNumber(routing.getSegmentSerialNum());
		cRAFlightFinaliseVO.setLastUpdatedUser(logonAttributes.getUserId());
		cRAFlightFinaliseVO.setTriggerPoint("MRA");
		try {
			new CRAMiscbillingProxy().saveBlockSpaceAgreementDetails(cRAFlightFinaliseVO);
		} catch (ProxyException e) {
		}

	}
    }

public String findSystemParameterValue(String syspar)
		throws SystemException {
	String sysparValue = null;
	ArrayList<String> systemParameters = new ArrayList<String>();
	systemParameters.add(syspar);
	Map<String, String> systemParameterMap=null;
	try {
		systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
	} catch (ProxyException e) {
		e.printStackTrace();
	}
	log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
	if (systemParameterMap != null) {
		sysparValue = systemParameterMap.get(syspar);
	}
	return sysparValue;
}


/**
 * 
 * 	Method		:	MRADefaultsController.convertAndPopulateUpdatedAmount
 *	Added by 	:	A-8061 on 30-Aug-2019
 * 	Used for 	:	ICRD-342347
 *	Parameters	:	@param ccadetailsVO
 *	Parameters	:	@param mRAGPABillingDetails
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void convertAndPopulateUpdatedAmount(CCAdetailsVO ccadetailsVO,MRAGPABillingDetails mRAGPABillingDetails) throws SystemException{
	
	Collection<CurrencyConvertorVO> currencyConvertorVOs = new ArrayList<CurrencyConvertorVO>();
	CurrencyConvertorVO currencyConvertorVO1 = null;
	
	LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
			.getSecurityContext().getLogonAttributesVO();
	
	ArrayList<CurrencyConvertorVO> convertedVOs=null; 
	
	String baseCurr=findSystemParameterValue("shared.airline.basecurrency");
	String rounding = findSystemParameterValue("mailtracking.mra.overrideroundingvalue");
	currencyConvertorVO1 = new CurrencyConvertorVO();
	currencyConvertorVO1.setCompanyCode(ccadetailsVO.getCompanyCode());
	currencyConvertorVO1.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
	currencyConvertorVO1.setFromCurrencyCode(ccadetailsVO.getRevContCurCode());
	currencyConvertorVO1.setRatingBasisType("F");
	currencyConvertorVO1.setToCurrencyCode("USD");
	LocalDate date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
	currencyConvertorVO1.setRatePickUpDate(date);
	currencyConvertorVOs.add(currencyConvertorVO1);
	
	currencyConvertorVO1 = new CurrencyConvertorVO();
	currencyConvertorVO1.setCompanyCode(ccadetailsVO.getCompanyCode());
	currencyConvertorVO1.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
	currencyConvertorVO1.setFromCurrencyCode(ccadetailsVO.getRevContCurCode());
	currencyConvertorVO1.setRatingBasisType("F");
	currencyConvertorVO1.setToCurrencyCode("XDR");
	date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
	currencyConvertorVO1.setRatePickUpDate(date);
	currencyConvertorVOs.add(currencyConvertorVO1);
	
	currencyConvertorVO1 = new CurrencyConvertorVO();
	currencyConvertorVO1.setCompanyCode(ccadetailsVO.getCompanyCode());
	currencyConvertorVO1.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
	currencyConvertorVO1.setFromCurrencyCode(ccadetailsVO.getRevContCurCode());
	currencyConvertorVO1.setRatingBasisType("F");
	currencyConvertorVO1.setToCurrencyCode(baseCurr);
	date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
	currencyConvertorVO1.setRatePickUpDate(date);
	currencyConvertorVOs.add(currencyConvertorVO1);
	
	try {
	 convertedVOs =	(ArrayList)new SharedCurrencyProxy().convertCurrency(currencyConvertorVOs);
	} catch (ProxyException e) {
		e.printStackTrace();
	}
	
	if(convertedVOs!=null && !convertedVOs.isEmpty()){
		
		
		Money chgUSD=null;
		Money chgXDR=null;
		Money chgBase=null;
		
		try {
			chgUSD=CurrencyHelper.getMoney("USD");
			chgXDR=CurrencyHelper.getMoney("XDR");
			chgBase=CurrencyHelper.getMoney(baseCurr);
		} catch (CurrencyException e) {
			e.printStackTrace();
		}
		
		if("N".equals(rounding)){
			chgUSD.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(0).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInUSD(chgUSD.getRoundedAmount());
			
			chgXDR.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(1).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInXDR(chgXDR.getRoundedAmount());
			
			chgBase.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(2).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInBaseCurrency(chgBase.getRoundedAmount());
		}else{
			int roundingIntVal=Integer.valueOf(rounding);
			
			chgUSD.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(0).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInUSD(getScaledValue(chgUSD.getAmount(),roundingIntVal));
			
			chgXDR.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(1).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInXDR(getScaledValue(chgXDR.getAmount(),roundingIntVal));
			
			chgBase.setAmount(ccadetailsVO.getRevChgGrossWeight().getAmount()*convertedVOs.get(2).getConversionFactor());
			mRAGPABillingDetails.setUpdatedWeightChargeInBaseCurrency(getScaledValue(chgBase.getAmount(),roundingIntVal));
		}	
	}
}

	
	/**
	 * 
	 * @param VOs
	 * @throws SystemException
	 */
	@Advice(name = "mail.mra.voidMailbags" , phase=Phase.POST_INVOKE)
	public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws SystemException{
	
		log.entering(CLASS_NAME, "voidMailbags");
		try{
			raiseAutoMCAForVoidmailbags(VOs);
		new MRABillingMaster().voidMailbags(VOs);
		}catch(Exception exception){
			log.log(Log.SEVERE, exception.getMessage());
		}
		
		log.exiting(CLASS_NAME, "voidMailbags");
	}
	/**
	 *
	 * 	Method		:	MRADefaultsController.raiseAutoMCAForVoidmailbags
	 *	Added by 	:	A-5219 on 16-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingDetailsVO
	 *	Return type	: 	void
	 */
	public void  raiseAutoMCAForVoidmailbags(
			Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs){
		log.entering(CLASS_NAME, "populateAttributesForAutoMCA");
		Collection<CCAdetailsVO> ccaDetailsVOs = null;
		GPABillingEntriesFilterVO filterVO = null;
		try{
			for(DocumentBillingDetailsVO vo : documentBillingDetailsVOs){
				if("BD".equals(vo.getBillingStatus())){
					ccaDetailsVOs = new ArrayList<CCAdetailsVO>();
					LocalDate fromDate = new LocalDate(NO_STATION, NONE, false);
					fromDate.setDate(vo.getFlightDate().toDisplayDateOnlyFormat());
					fromDate.addDays(-5);
					LocalDate toDate = new LocalDate(NO_STATION, NONE, false);
					toDate.setDate(vo.getFlightDate().toDisplayDateOnlyFormat());
					toDate.addDays(5);
					filterVO = new GPABillingEntriesFilterVO();
					filterVO.setCompanyCode(vo.getCompanyCode());
					filterVO.setMailbagId(vo.getBillingBasis());
					filterVO.setFromDate(fromDate);
					filterVO.setToDate(toDate);
					CCAdetailsVO detailsVO = new CCAdetailsVO() ;
					detailsVO.setCompanyCode(vo.getCompanyCode());
					detailsVO.setBillingBasis(vo.getBillingBasis());
					detailsVO.setMailSequenceNumber(vo.getMailSequenceNumber());
					detailsVO.setOperationFlag(DocumentBillingDetailsVO.OPERATION_FLAG_INSERT);
					detailsVO.setCcaStatus(APPROVED);
					detailsVO.setCcaType(CCA_TYPE_ACTUAL);
					detailsVO.setPoaCode(vo.getGpaCode());
					detailsVO.setRevisedRate(0);
					detailsVO.setGpaCode(vo.getGpaCode());
					detailsVO.setRevGpaCode(vo.getGpaCode());
					detailsVO.setRevContCurCode(vo.getCurrency());
					detailsVO.setVoidMailbag(true);
					ccaDetailsVOs.add(detailsVO);
					try{
						MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
						 mRADefaultsController.saveAutoMCAdetails(ccaDetailsVOs,filterVO);
					}catch(Exception exception){
						log.log(Log.SEVERE, exception.getMessage());
					}
				}
			}
		}catch(Exception exception){
			log.log(Log.SEVERE, exception.getMessage());
		}
		log.exiting(CLASS_NAME, "populateAttributesForAutoMCA");
	}

	/**
	 * 
	 * 	Method		:	MRADefaultsController.findMailbagBillingStatus
	 *	Added by 	:	a-8331 on 25-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */

	public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException {
		return new MRABillingMaster().findMailbagBillingStatus(mailbagvo);
	}
/**
 * 
 * 	Method		:	MRADefaultsController.validateCurrConversion
 *	Added by 	:	A-8061 on 30-Oct-2019
 * 	Used for 	:	ICRD-346925
 *	Parameters	:	@param currCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	String
 */
	public String validateCurrConversion(String currCode) throws SystemException {
		
		Collection<CurrencyConvertorVO> currencyConvertorVOs = new ArrayList<CurrencyConvertorVO>();
		CurrencyConvertorVO currencyConvertorVO = null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		ArrayList<CurrencyConvertorVO> convertedVOs=null;
		String toCurrCode=null;

		String baseCurr=findSystemParameterValue("shared.airline.basecurrency");
		String rounding = findSystemParameterValue("mailtracking.mra.overrideroundingvalue");
		currencyConvertorVO = new CurrencyConvertorVO();
		currencyConvertorVO.setCompanyCode(logonAttributes.getCompanyCode());
		currencyConvertorVO.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
		currencyConvertorVO.setFromCurrencyCode(currCode);
		currencyConvertorVO.setRatingBasisType("F");
		currencyConvertorVO.setToCurrencyCode("USD");
		LocalDate date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		currencyConvertorVO.setRatePickUpDate(date);
		currencyConvertorVOs.add(currencyConvertorVO);

		currencyConvertorVO = new CurrencyConvertorVO();
		currencyConvertorVO.setCompanyCode(logonAttributes.getCompanyCode());
		currencyConvertorVO.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
		currencyConvertorVO.setFromCurrencyCode(currCode);
		currencyConvertorVO.setRatingBasisType("F");
		currencyConvertorVO.setToCurrencyCode("XDR");
		date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		currencyConvertorVO.setRatePickUpDate(date);
		currencyConvertorVOs.add(currencyConvertorVO);

		currencyConvertorVO = new CurrencyConvertorVO();
		currencyConvertorVO.setCompanyCode(logonAttributes.getCompanyCode());
		currencyConvertorVO.setAirlineIdentifier(logonAttributes.getAirlineIdentifier());
		currencyConvertorVO.setFromCurrencyCode(currCode);
		currencyConvertorVO.setRatingBasisType("F");
		currencyConvertorVO.setToCurrencyCode(baseCurr);
		date =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		currencyConvertorVO.setRatePickUpDate(date);
		currencyConvertorVOs.add(currencyConvertorVO);

		try {
			convertedVOs = (ArrayList) new SharedCurrencyProxy().convertCurrency(currencyConvertorVOs);
		} catch (ProxyException exception) {
			if (exception != null && exception.getErrors() != null && !exception.getErrors().isEmpty()
					&& exception.getErrors().iterator().next().getErrorData() != null
					&& exception.getErrors().iterator().next().getErrorData().length > 1) {
				toCurrCode = exception.getErrors().iterator().next().getErrorData()[1].toString();
				return toCurrCode;
			}
		}

		if(convertedVOs==null || convertedVOs.isEmpty()){
			toCurrCode="USD";
		}
		
		return toCurrCode;
	}

	




/**
 * 	Method		:	MRADefaultsController.importConsignmentDataToMra
 *	Added by 	:	A-4809 on Nov 20, 2018
 * 	Used for 	:
 *	Parameters	:	@param consignmentDocumentVO 
 *	Return type	: 	void
 */
public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO)
		throws SystemException{
	Log log = LogFactory.getLogger("MAILTRACKING_MRA_DEFAULTS");
	log.entering(CLASS_NAME, "importConsignmentDataToMra");
	if(consignmentDocumentVO.getMailInConsignmentVOs()!=null &&
			!consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()){
		for(MailInConsignmentVO mailVO: consignmentDocumentVO.getMailInConsignmentVOs()){
			try {
				new MailTrackingMRAProxy().importConsignmentDataToMRA(mailVO);
			} catch (ProxyException e) {
			log.log(Log.SEVERE, "Proxy exception caught");
			}
		}
	}
	log.exiting(CLASS_NAME, "importConsignmentDataToMra");
}
/**
 * 	Method		:	MRADefaultsController.importConsignmentDataToMRA
 *	Added by 	:	A-4809 on Nov 20, 2018
 * 	Used for 	:    
 *	Parameters	:	@param mailInConsignmentVO 
 *	Return type	: 	void
 */
public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO) 
		throws SystemException{
	log.entering(CLASS_NAME, "importConsignmentDataToMRA");
	MRADefaultsDAO mraDefaultsDao = null;
	try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em
				.getQueryDAO(MODULE_NAME));
		mraDefaultsDao.importConsignmentDataToMRA(mailInConsignmentVO);
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
	log.exiting(CLASS_NAME, "importConsignmentDataToMRA");
}
/**
 * 	Method		:	MRADefaultsController.calculateUSPSIncentive
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void calculateUSPSIncentive(USPSIncentiveVO uspsIncentiveVO)throws SystemException{
	log.entering(CLASS_NAME, "calculateUSPSIncentive");
	Collection<USPSPostalCalendarVO> uspsPostalCalendarVOs = null;
	uspsPostalCalendarVOs = findUSPSInternationalIncentiveJobDetails(uspsIncentiveVO.getCompanyCode());
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	if(uspsPostalCalendarVOs!=null && !uspsPostalCalendarVOs.isEmpty()){
	for(USPSPostalCalendarVO uspsPostalCalendarVO:uspsPostalCalendarVOs){
	if(uspsPostalCalendarVO.getIncCalcDate()!=null){
		if (currentDate.toDisplayDateOnlyFormat().equals(uspsPostalCalendarVO.getIncCalcDate().toDisplayDateOnlyFormat())){
			try {
				new MailTrackingMRAProxy().calculateUSPSIncentiveAmount(uspsPostalCalendarVO,uspsIncentiveVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			} 
			}
		}
	}
	}
	log.exiting(CLASS_NAME, "calculateUSPSIncentive");
}
/**
 * 	Method		:	MRADefaultsController.findUSPSInternationalIncentiveJobDetails
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return 
 *	Return type	: 	USPSPostalCalendarVO
 */
public Collection<USPSPostalCalendarVO> findUSPSInternationalIncentiveJobDetails(String companyCode)
throws SystemException{
	log.entering(CLASS_NAME, "findUSPSInternationalIncentiveJobDetails");
	MRADefaultsDAO mraDefaultsDao = null;
	Collection<USPSPostalCalendarVO> uspsPostalCalendarVOs = null;
	try {
		EntityManager em = PersistenceController.getEntityManager();
		mraDefaultsDao = MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		uspsPostalCalendarVOs=mraDefaultsDao.findUSPSInternationalIncentiveJobDetails(companyCode);
	} catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
	log.exiting(CLASS_NAME, "findUSPSInternationalIncentiveJobDetails");
	return uspsPostalCalendarVOs; 
}
/**
 * 	Method		:	MRADefaultsController.importMailsFromCarditData
 *	Added by 	:	A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void importMailsFromCarditData(DocumentBillingDetailsVO documentBillingDetailsVO, String txnlogInfo)
throws SystemException{
	log.entering(CLASS_NAME, "importMailsFromCarditData");
	String txnInfo[] = txnlogInfo.split("-");
	String txnCod = txnInfo[0];
	int serNum = Integer.parseInt(txnInfo[1]);
	String output[]=new String[3];
	Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = null;
	MRADefaultsDAO mraDefaultsDao = null;
	documentBillingDetailsVOs=findMailsFromCarditForImport(documentBillingDetailsVO);
	if(!documentBillingDetailsVOs.isEmpty()){
		try{
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			output=mraDefaultsDao.importMailsFromCarditData(documentBillingDetailsVOs).split("-");
			}catch(PersistenceException persistenceException){
				throw new SystemException(persistenceException.getErrorCode());	
			}
	}
	String processStatus=output[0];  
	String count=output[1]; 
	StringBuilder remarks = null;
	if(processStatus!=null && (processStatus.contains(COMPLETED)&&!processStatus.contains(FAILED)))
	{
		processStatus =COMPLETED;
		remarks=new StringBuilder("Mails are prorated by the system");
	}else if(processStatus!=null && (processStatus.contains(COMPLETED)&& processStatus.contains(FAILED)))
	{
		processStatus =PARTIALLY_COMPLETED;
		remarks=new StringBuilder("").append(count).append(" ").append("mails are prorated by the system");
	}else if(processStatus!=null && processStatus.contains(FAILED))
	{
		processStatus =FAILED;
		remarks=new StringBuilder("").append("Mails are not prorated");
	}else if(documentBillingDetailsVOs.isEmpty()){    
		processStatus =FAILED;    
		remarks=new StringBuilder("").append("No Eligible Records were Identified");       
	}
	InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
	LogonAttributes logonAttributes = (LogonAttributes) ContextUtils.getSecurityContext().getLogonAttributesVO();
    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
	invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.IMPORT_DATA_TO_MRA_CARDIT);
	invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
	invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());	
	if(remarks!=null)  {
	invoiceTransactionLogVO.setRemarks(remarks.toString());	
	}
	invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
	invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
    invoiceTransactionLogVO.setSerialNumber(serNum);
    invoiceTransactionLogVO.setTransactionCode(txnCod);	
	try {
	     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
	} catch (ProxyException e) {
		throw new SystemException(e.getMessage());
	}
	log.exiting(CLASS_NAME, "importMailsFromCarditData");
}
/**
 * 	Method		:	MRADefaultsController.findMailsFromCarditForImport
 *	Added by 	:	A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
private Collection<DocumentBillingDetailsVO> findMailsFromCarditForImport(DocumentBillingDetailsVO documentBillingDetailsVO)
throws SystemException{
	log.entering(CLASS_NAME, "findMailsFromCarditForImport");
	MRADefaultsDAO mraDefaultsDao = null;
	Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = null;
	try{
	EntityManager em = PersistenceController.getEntityManager();
	mraDefaultsDao = MRADefaultsDAO.class.cast(em
			.getQueryDAO(MODULE_NAME));
	documentBillingDetailsVOs=mraDefaultsDao.findMailsFromCarditForImport(documentBillingDetailsVO);
	}catch(PersistenceException persistenceException){
		throw new SystemException(persistenceException.getErrorCode());	
	}
	return documentBillingDetailsVOs;
}

/**
 * 	Method		:	MRADefaultsController.findMailbagExistInMRA
 *	Added by 	:	A-4809 on Jan 2, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	String
 */
public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO) throws SystemException{
	log.entering(CLASS_NAME, "findMailbagExistInMRA");
	return  new MRABillingMaster().findMailbagExistInMRA(documentBillingDetailsVO);
    }

/***
 * @author A-7794
 * @param fileUploadFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException 
 */
public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws SystemException, RemoteException{
	String processStatus = null;
	MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
	processStatus = mailTrackingDefaultsProxy.processMailDataFromExcel(fileUploadFilterVO);
	return processStatus;
}
/**
 * 	Method		:	MRADefaultsController.listGPABillingEntries
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Page<DocumentBillingDetailsVO>
 */
public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException{
	log.entering(CLASS_NAME, "listGPABillingEntries");
	Page<DocumentBillingDetailsVO> gpaBillingDetailsVOs=MRABillingMaster.listGPABillingEntries(gpaBillingEntriesFilterVO);
	log.exiting(CLASS_NAME, "listGPABillingDetails");
	return gpaBillingDetailsVOs;
}
/**
 * 	Method		:	MRADefaultsController.listConsignmentDetails
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Page<ConsignmentDocumentVO>
 */
public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException{
	log.entering(CLASS_NAME, "listConsignmentDetails");
	String companyCode = gpaBillingEntriesFilterVO.getCompanyCode();
	String parameterType = CRA_PAR_INVGRP;
	Collection<CRAParameterVO> craParameterVOs = null;
	try {
		 craParameterVOs=new CRADefaultsProxy().findCRAParameterDetails(companyCode, parameterType);
	} catch (ProxyException e) {
		log.log(Log.SEVERE, e.getMessage());
	}
	if(Objects.nonNull(craParameterVOs)){
	craParameterVOs = craParameterVOs.stream().filter(craParameterVO->StringUtils.equalsIgnoreCase(craParameterVO.getFunctionPoint(), MBG))
	.collect(Collectors.toList());
	}
	Page<ConsignmentDocumentVO> consignmentDocumentVOs=MRABillingMaster.listConsignmentDetails(gpaBillingEntriesFilterVO,craParameterVOs);
	log.exiting(CLASS_NAME, "listConsignmentDetails");
	return consignmentDocumentVOs;
}

/**
 * 	Method		:	MRADefaultsController.findCRAParameterDetails
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<CRAParameterVO>
 */
public Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String craParInvgrp) throws SystemException{
	log.entering(CLASS_NAME, "findCRAParameterDetails");
	Collection<CRAParameterVO> craParameterVOs = null;
	try {
		 craParameterVOs=new CRADefaultsProxy().findCRAParameterDetails(companyCode, craParInvgrp);
	} catch (ProxyException e) {
		log.log(Log.SEVERE, e.getMessage());
	}
	return craParameterVOs;
}

/**
 * 	Method		:	MRADefaultsController.findReasonCodes
 *	Added by 	:	A-7531 on 05-Feb-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param systemParCodes
 *	Parameters	:	@return 
 *	Return type	: 	Collection<CRAParameterVO>
 * @throws SystemException 
 * @throws ProxyException 
 */
public Collection<CRAParameterVO> findReasonCodes(String companyCode, String systemParCodes) throws ProxyException, SystemException {

	Collection<CRAParameterVO> craParameterVOs=null;
	craParameterVOs=new CRADefaultsProxy().findCRAParameterDetails(companyCode, systemParCodes);
	return craParameterVOs;
}

public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO) throws SystemException{
	MRABillingMaster.importResditDataToMRA(mailScanDetailVO);
}
private Collection<String> getSystemParameterTypes(){
	log.entering(CLASS_NAME, "getSystemParameterTypes");
	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	systemparameterTypes.add(SYS_PARAM_WRKFLOWENABLED);
	log.exiting(CLASS_NAME, "getSystemParameterTypes");
	return systemparameterTypes;
}
//Added as part of ICRD-329873
public String getmcastatus(
		CCAdetailsVO ccadetailsVO)
throws BusinessDelegateException ,RemoteException, SystemException{
	CCADetail ccadetail=null;
	try {
		 ccadetail= CCADetail.find(ccadetailsVO);
	
}
	
	catch (FinderException e) {

	
}
	return ccadetail.getMcaStatus();
}
/**
 * 	Method		:	MRADefaultsController.findReasonCodes
 *	Added by 	:	A-5526 on 24-Jan-2020
 * 	Used for 	:
 *	Parameters	:	@param USPSPostalCalendarVO
 *	Parameters	:	@return 
 *	Return type	: 	void
 * @throws SystemException 
 */
public void calculateUSPSIncentiveAmount(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO) throws SystemException {
	MRADefaultsDAO mraDefaultsDao = null;
	try {
	EntityManager em = PersistenceController.getEntityManager();
	mraDefaultsDao = MRADefaultsDAO.class.cast(em
			.getQueryDAO(MODULE_NAME));
	mraDefaultsDao.calculateUSPSIncentive(uspsPostalCalendarVO,uspsIncentiveVO);
} catch (PersistenceException persistenceException) {
	throw new SystemException(persistenceException.getErrorCode());
}
}

/**
 * 
 * 	Method		:	MRADefaultsController.isMailbagInMRA
 *	Added by 	:	A-5219 on 07-May-2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param mailSeq
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	boolean
 */
public boolean isMailbagInMRA(String companyCode, long mailSeq)
	throws SystemException {
	
	boolean isMailbagPresent = false;
	try{
		MRABillingMaster mraMaster = MRABillingMaster.find(companyCode, mailSeq);
		if(mraMaster != null && mraMaster.getBillingMasterPK().getMailSeqNumber() > 0){
			isMailbagPresent = true;
		}
	}catch(FinderException exception){
		isMailbagPresent = false;
	}catch(SystemException exception){
		isMailbagPresent = false;
	}catch(Exception exception){
		isMailbagPresent = false;
	}
	return isMailbagPresent;
}
/**
 * 
 * 	Method		:	MRADefaultsController.recalculateDisincentiveData
 *	Added by 	:	A-8176 on 12-Jun-2020
 * 	Used for 	:
 *	Parameters	:	@param mailbagVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	boolean
 */
public void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVOs)
	throws SystemException {
	Map<String, String> systemParameters = null;
	SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
	Collection<String> systemParameterCodes = new ArrayList<String>();
	systemParameterCodes.add(SYS_PARA_TRIGGER_FOR_MRAIMPORT);
	try {
		systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
	} catch (ProxyException e) {
		e.getMessage();
	}
	String triggerForMRAImport = (systemParameters.get(SYS_PARA_TRIGGER_FOR_MRAIMPORT));
	if(triggerForMRAImport!=null && triggerForMRAImport.contains("D")){
	   for(RateAuditDetailsVO rateAuditVO:rateAuditVOs){
		 new MailDetailsTemp(rateAuditVO);
	  }
	}
}
/**
 * 
 * 	Method		:	MRADefaultsController.updateRouteAndReprorate
 *	Added by 	:	A-8061 on 15-Dec-2020
 * 	Used for 	:
 *	Parameters	:	@param gPABillingEntriesFilterVO
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO) throws SystemException{

	try {
		if (gPABillingEntriesFilterVO.getMailbagId() != null && !gPABillingEntriesFilterVO.getMailbagId().isEmpty()) {
			gPABillingEntriesFilterVO
					.setMailSequenceNumber(new MailTrackingDefaultsProxy().findMailBagSequenceNumberFromMailIdr(
							gPABillingEntriesFilterVO.getMailbagId(), gPABillingEntriesFilterVO.getCompanyCode()));
		}
		
	constructDAO().updateRouteAndReprorate(gPABillingEntriesFilterVO);
	} catch (PersistenceException |ProxyException e) {
		throw new SystemException(e.getMessage());
	}
	
}

	/**
	 * 
	 * 	Method		:	MRADefaultsController.reImportPABuiltMailbagsToMRA
	 *	Added by 	:	A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO) throws SystemException {
		constructDAO().reImportPABuiltMailbagsToMRA(mailbagVO);
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.findMailbagsForPABuiltUpdate
	 *	Added by 	:	A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<MailbagVO>
	 */
	public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)
			throws SystemException {
		return constructDAO().findMailbagsForPABuiltUpdate(mailbagVO);
	}
/**
 * 
 * 	Method		:	MRADefaultsController.findBillingType
 *	Added by 	:	A-9498 on 26-April-2021
 * 	Used for 	:
 *	Parameters	:	@param BillingScheduleFilterVO
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Page
 */
	public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO,
			int pageNumber) throws SystemException {
		log.entering("MRADefaultsController", "findBillingType");
		try {
			MRADefaultsDAO mraDefaultsDao = null;
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "triggerProration");
			log.exiting("MRADefaultsController", "findBillingType");
			return mraDefaultsDao.findBillingType(billingScheduleFilterVO, pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),
					persistenceException);
		}
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.saveBillingSchedulemaster
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param BillingScheduleDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)
			throws SystemException, FinderException {
		log.entering(CLASS_NAME, "saveBillingSchedulemaster"); 
		BillingScheduleMasterPK billingScheduleMasterPK = new BillingScheduleMasterPK();
		BillingScheduleMaster billingScheduleMaster = null;
		
		if (billingScheduleDetailsVO.getOpearationFlag() != null
				&& OPERATION_FLAG_DELETE.equals(billingScheduleDetailsVO.getOpearationFlag())) {			
			billingScheduleMasterPK.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
			billingScheduleMasterPK.setBillingType(billingScheduleDetailsVO.getBillingType());
			billingScheduleMasterPK.setPeriodNumber(billingScheduleDetailsVO.getPeriodNumber());
			billingScheduleMasterPK.setSerialNumber(billingScheduleDetailsVO.getSerialNumber());
			billingScheduleMaster = BillingScheduleMaster.find(billingScheduleMasterPK);
			billingScheduleMaster.remove(billingScheduleDetailsVO);			
		} else {
			new BillingScheduleMaster(billingScheduleDetailsVO);
		}
		log.exiting(CLASS_NAME, "saveBillingSchedulemaster");
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.validateBillingSchedulemaster
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param BillingScheduleDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	boolean
	 */
	public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)
			throws SystemException {
		try {
			MRADefaultsDAO mraDefaultsDao = null;
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "triggerProration");
			log.exiting(CLASS_NAME, "validateBillingSchedulemaster");
			return mraDefaultsDao.validateBillingSchedulemaster(billingScheduleDetailsVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),
					persistenceException);
		}
}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.importMailProvisionalRateData
	 *	Added by 	:	A-10647 on 23-Nov-2022
	 * 	Used for 	:
	 *	Parameters	:	@param rateAuditVos
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	public void importMailProvisionalRateData(Collection<RateAuditVO> rateAuditVos) throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "importMailProvisionalRateData");
		for(RateAuditVO rateAuditVO : rateAuditVos){
				constructDAO().saveMRADataForProvisionalRate(rateAuditVO);
			}
		}
	
	/**
	 * 	Method		:	MRADefaultsController.changeEnddate
	 *	Added by 	:	204569 on 12-Dec-2022
	 * 	Used for 	:
	 *	Parameters	:	@param BillingLineVO
	 **	Parameters	:	@param changeEndDate
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Advice(name = "mail.mra.changeEnddate" , phase=Phase.POST_INVOKE) 
	public void changeEnddate(Collection<BillingLineVO> billingLineVos, String changedate) throws SystemException {
		log.entering(CLASS_NAME, "changeEnddate");
		log.log(Log.INFO, "changedate", changedate);
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils.getSecurityContext().getLogonAttributesVO();
		for (BillingLineVO billingLineVo : billingLineVos) {
			LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(changedate);
			log.log(Log.INFO, "dateChange", endDate);
			log.log(Log.INFO, "billingLineVo.getValidityEndDate() before setting ", billingLineVo.getValidityEndDate());
			billingLineVo.setValidityEndDate(endDate);
			log.log(Log.INFO, "billingLineVo.getValidityEndDate() after setting  ", billingLineVo.getValidityEndDate());
			billingLineVo.setLastUpdatedUser(logonAttributes.getUserId());
			billingLineVo.setOperationFlag(CHANGE_DATE_FLAG);
			validateBillingLines(findOverlappingBillingLines(billingLineVo, "A"));
			updateBillingLine(billingLineVo);
		}
		updateBillingMatrixStatus(billingLineVos);
		log.exiting(CLASS_NAME, "changeEnddate");
	}
	public void calculateProvisionalRate(Long noOfRecords) throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "CalculateUpfrontRateWorker");
		
				constructDAO().calculateProvisionalRate(noOfRecords);

		}

	/**
	 * 	Method		:	MRADefaultsController.changeStatus
	 *	Added by 	:	204569 on 12-Dec-2022
	 * 	Used for 	:
	 *	Parameters	:	@param BillingLineVO
	 **	Parameters	:	@param changeEndDate
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	
	@Advice(name = "mail.mra.changeBillingMatrixStatusUpdate" , phase=Phase.POST_INVOKE) 
	public void changeBillingMatrixStatusUpdate(BillingMatrixFilterVO billingMatrixFilterVO, String statusandTranx) throws ProxyException, SystemException {
		log.entering(CLASS_NAME, "changeBillingMatrixStatusUpdate");
		log.log(Log.INFO, "status", statusandTranx);
		String processStatus=null;
		StringBuilder remarks = new StringBuilder("");
		Collection<BillingLineVO> billingLineVos = new ArrayList<>();
		String[] txnInfo = statusandTranx.split("-");
		String status=txnInfo[0];
		String txnCod = txnInfo[1];
		int serNum = Integer.parseInt(txnInfo[2]);
		String matrixId=billingMatrixFilterVO.getBillingMatrixId();
	    String convertedStatus = statusConvertion(status);
		try{	
			log.log(Log.INFO, "billingLineVo.getstatus", status);
			BillingLineFilterVO blgLineFilterVO = new BillingLineFilterVO();
			
			blgLineFilterVO.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
			blgLineFilterVO.setAirlineCode(billingMatrixFilterVO.getAirlineCode());
			blgLineFilterVO.setCompanyCode(billingMatrixFilterVO.getCompanyCode());
			blgLineFilterVO.setAbsoluteIndex(1);
			blgLineFilterVO.setPageNumber(1);
			String remarkAndProcessStatus = findbillinglineDetailsforStatuChage(blgLineFilterVO,status,billingLineVos,checkOverlapBillinglinetotal,billingMatrixFilterVO);		
			 remarks =new StringBuilder(remarkAndProcessStatus.split(":")[0]);
			 processStatus=(remarkAndProcessStatus).split(":")[1];
		}
		catch(Exception e){
			processStatus=FAILED;
			remarks=new StringBuilder(REMARKS).append(matrixId).append(REMARKS_STATUS).append(convertedStatus).append(STATUS_FAILED);
			remarks=new StringBuilder(remarks).append("Failure reason :").append(e.getMessage());
			log.log(Log.INFO, "e", e);
		}   

		String remark;
		if(remarks.length()>500){	
		 remark = remarks.toString().substring(0, 499);
		} else{
			remark = remarks.toString();
		}
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
	    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType("BM");
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());	
		invoiceTransactionLogVO.setRemarks(remark);	
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
	    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
	    invoiceTransactionLogVO.setClearancePeriod("test");
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
	    invoiceTransactionLogVO.setSerialNumber(serNum);
	    invoiceTransactionLogVO.setTransactionCode(txnCod);	
	

	
	    Proxy.getInstance().get(CRADefaultsProxy.class).updateTransactionandRemarks(invoiceTransactionLogVO);
	    
	    log.exiting(CLASS_NAME, "changeBillingMatrixStatusUpdate");

}

public String findbillinglineDetailsforStatuChage(BillingLineFilterVO blgLineFilterVO, String status, Collection<BillingLineVO> billingLineVos, StringBuilder checkOverlapBillinglinetotal, BillingMatrixFilterVO billingMatrixFilterVO) throws SystemException {
	Collection<BillingLineVO> billingLines = BillingMatrix
			.findBillingLines(blgLineFilterVO);
	for (BillingLineVO billingLine : billingLines)	{
		String checkOverlapBillingline = null;
		if (status.equals("A") && !billingLine.getBillingLineStatus().equalsIgnoreCase("A")) {
			checkOverlapBillingline = checkOverlapBillingline(billingLine);
		}
		if (checkOverlapBillingline == null) {
			billingLine.setBillingLineStatus(status);
			updateBillingLine(billingLine);
			billingLineVos.add(billingLine);
		} else {
			checkOverlapBillinglinetotal = new StringBuilder(checkOverlapBillingline).append(checkOverlapBillinglinetotal);
		}
	}
	return updateStatusBiilingMatrix(billingLineVos,billingMatrixFilterVO,status,checkOverlapBillinglinetotal);
		
	}

public String updateStatusBiilingMatrix(Collection<BillingLineVO> billingLineVos,BillingMatrixFilterVO billingMatrixFilterVO, String status, StringBuilder checkOverlapBillinglinetotal) throws SystemException {

	MRADefaultsController mRADefaultsController=new MRADefaultsController();
	BillingMatrixVO billingMatrixDetails = mRADefaultsController.findBillingMatrixDetails(billingMatrixFilterVO);
	billingMatrixDetails.setBillingMatrixStatus(status);
	billingMatrixDetails.setLastUpdatedTime(billingMatrixDetails
			.getLastUpdatedTime());
	billingMatrixDetails.setLastUpdatedUser(billingMatrixDetails
			.getLastUpdatedUser());
	
	 String processStatus="";
	 StringBuilder remarks;
	if (checkOverlapBillinglinetotal.toString().equalsIgnoreCase("")) {
		BillingMatrix billingMatrix = BillingMatrix.find(billingMatrixFilterVO
				.getCompanyCode(), billingMatrixFilterVO.getBillingMatrixId());
		if (billingMatrix != null) {
			billingMatrix.setBillingMatrixStatus(status);
		}
		processStatus = COMPLETED;
		remarks = new StringBuilder(REMARKS).append(billingMatrixDetails.getBillingMatrixId()).append(REMARKS_STATUS).append(statusConvertion(status))
				.append(STATUS_SUCSESS);

	} else {
		processStatus = PARTIALLY_COMPLETED;
		remarks = new StringBuilder(PARTIALLY_COMPLETED_REMARKS).append(checkOverlapBillinglinetotal);
	}
		
	return remarks.append(":").append(processStatus).toString();
	}

public String checkOverlapBillingline(BillingLineVO billingLine) {
		
		String message=null;
		String[] messageRateline = null;
		try{			
			validateBillingLines(findOverlappingBillingLines(billingLine, "A"));
				}
				catch(SystemException sys){		
				log.log(Log.INFO, "SystemException", sys);
				 message = sys.getMessage();
				 messageRateline= message.split(":");
				 message= messageRateline[1];
				}		
		return message;		
	}


	private String statusConvertion(String status) {
		if(status.equalsIgnoreCase(STATUS_ACTIVE)){
			return STA_ACT;
		}
		else if (status.equalsIgnoreCase(STATUS_CANCELLED)){
		return STA_CAN;
		}
		else if (status.equalsIgnoreCase(STATUS_INACTIVE)){
			return STA_INA;
			}
		else if (status.equalsIgnoreCase(STATUS_EXPIRED)){
			return STA_EXP;
			}
		else{
			return status;
		}
		
		
	}
	/**
	 * 
	 * 	Method		:	MRADefaultsController.findMRAGLAccountingEntries
	 *	Added by 	:	A-10164 on 15-Feb-2023
	 *	User Story	:	IASCB-162079
	 *	Parameters	:	@param glInterfaceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	List<GLInterfaceDetailVO>
	 */
	public List<GLInterfaceDetailVO> findMRAGLAccountingEntries(GLInterfaceFilterVO glInterfaceFilterVO)
			throws SystemException {
		LOGGER.entering(CLASS_NAME, "findMRAGLAccountingEntries");
		return constructDAO().findMRAGLAccountingEntries(glInterfaceFilterVO);
	}	 
public String taxValuesMRA(String masterType,String parameterCode)throws SystemException{
		String taxValue ="";
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode(logonAttributes.getCompanyCode());
		configVOTaxValue.setMasterType(masterType);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(parameterCode);
		Map<String, HashMap<String, String>> configParamsTaxValue = null;
		try{
			configParamsTaxValue = Proxy.getInstance().get(SharedDefaultsProxy.class).findGeneralParameterConfigurationDetails(configVOTaxValue);
		}catch(Exception exception){
			log.log(Log.INFO,exception );
		}
		if(configParamsTaxValue!=null && !configParamsTaxValue.isEmpty() && configParamsTaxValue.containsKey(TAX_GROUP)){
			Map<String, String> map = configParamsTaxValue.get(TAX_GROUP);
			if(map!=null && !map.isEmpty() && map.containsKey(parameterCode)){
				String value = map.get(parameterCode);
				if(null!= value)
					{
					taxValue = value;
			}
		}
		}
		return taxValue;
	}

 void updateBillingDetail(MRABillingDetailsVO billingDetailsVO) throws FinderException, SystemException {
	MRAGPABillingDetails mraGPABillingDetails = MRAGPABillingDetails.
	find(billingDetailsVO.getCompanyCode(),billingDetailsVO.getMailSequenceNumber(),billingDetailsVO.getSequenceNumber());
	log.log(Log.FINE, "Billing details find",mraGPABillingDetails);
	String currencyCode = mraGPABillingDetails.getContractCurrencyCode();
		Money netAmnt = null;
		Money serviceTax = null;
		Money wtChgAndSurChg=null;
		double weightSurChgIncl=mraGPABillingDetails.getOtherChargeInContractCurrency();
		double weightMailChgIncl=mraGPABillingDetails.getWieghtChargeInContractCurrency();
		Money chgSurIncl = null;
		Money chgMailIncl = null;
		try {
		chgMailIncl =CurrencyHelper.getMoney(currencyCode);
		chgMailIncl.setAmount(weightMailChgIncl);
		chgSurIncl =CurrencyHelper.getMoney(currencyCode);
		chgSurIncl.setAmount(weightSurChgIncl);
		wtChgAndSurChg=CurrencyHelper.getMoney(currencyCode);
		wtChgAndSurChg.setAmount(chgMailIncl.getAmount()+chgSurIncl.getAmount());
		double actualServiceTax=(billingDetailsVO.getTaxPercentage()/100)*(wtChgAndSurChg.getRoundedAmount());
		serviceTax = CurrencyHelper.getMoney(currencyCode);
		serviceTax.setAmount(actualServiceTax);
		billingDetailsVO.setServiceTax(serviceTax.getRoundedAmount());
		double netAmount=(wtChgAndSurChg.getRoundedAmount())+ serviceTax.getRoundedAmount();
		netAmnt = CurrencyHelper.getMoney(currencyCode);
		netAmnt.setAmount(netAmount);
		mraGPABillingDetails.setServiceTax(billingDetailsVO.getServiceTax());
		mraGPABillingDetails.setNetAmount(netAmnt.getRoundedAmount());
}
		 catch (CurrencyException e) {
				log.log(Log.INFO,e );
			}
}
	public Collection<LockVO> addAutoMraMcaLocks() throws SystemException, ObjectAlreadyLockedException {
		Collection<LockVO> lockvos = constructAutoMraMcaLockVOs();
		return addFrameworkLocks(lockvos);
	}
	protected void releaseLocks(Collection<LockVO> lockVOs) throws ObjectNotLockedException, SystemException {
		try {
			Proxy.getInstance().get(FrameworkLockProxy.class).releaseLocks(lockVOs);
		} catch (ProxyException ex) {
			log.log(Log.SEVERE, ex);
			throw new SystemException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			log.log(Log.SEVERE, "System Exception");
			boolean isFound = false;
			if (ex.getErrors() != null && ex.getErrors().size() > 0) {
				for (ErrorVO errvo : ex.getErrors()) {
					if (ObjectNotLockedException.OBJECT_NOT_LOCKED.equals(errvo.getErrorCode())) {
						isFound = true;
						break;
					}
				}
			}
			if (isFound) {
				throw new ObjectNotLockedException(ex);
			}
			throw new SystemException(ex.getErrors());
		}
	}
	private Collection<LockVO> constructAutoMraMcaLockVOs() throws SystemException {
		Collection<LockVO> lockvos = new ArrayList<>();
		TransactionLockVO autoMraMcaLock = new TransactionLockVO("MRAAUTOMCA");
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils.getSecurityContext().getLogonAttributesVO();
		autoMraMcaLock.setAction("AUTOMCA");
		autoMraMcaLock.setClientType(ClientType.APPLICATION);
		autoMraMcaLock.setCompanyCode(logonAttributes.getCompanyCode());
		autoMraMcaLock.setDescription("MRA MCA ACCEPT LOCK");
		autoMraMcaLock.setRemarks("MANUAL LOCK");
		autoMraMcaLock.setStationCode(logonAttributes.getStationCode());
		autoMraMcaLock.setScreenId("MRA073");
		lockvos.add(autoMraMcaLock);
		return lockvos;
	}
	protected void updateInvoiceTransferLogForAutoMca(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException {
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(ACCEPTMCA);
		invoiceTransactionLogVO.setTransactionCode(gpaBillingEntriesFilterVO.getTxnCode());
		invoiceTransactionLogVO.setSerialNumber(gpaBillingEntriesFilterVO.getTxnSerialNum());
		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.COMPLETED);
		invoiceTransactionLogVO.setRemarks("MCA approval completed");
		try {
			 Proxy.getInstance().get(CRADefaultsProxy.class).updateTransactionandRemarks(invoiceTransactionLogVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, e);
			throw new SystemException(e.getMessage());
		}
	}
	private Collection<LockVO> addFrameworkLocks(Collection<LockVO> lockvos)
			throws ObjectAlreadyLockedException, SystemException {
		log.entering(CLASS_NAME,"addFrameworkLocks");
		Collection<LockVO> acquiredLockVOs = new ArrayList<>();
		try {
			acquiredLockVOs = Proxy.getInstance().get(FrameworkLockProxy.class).addLocks(lockvos);
		} catch (ProxyException exception) {
			this.log.log(7, new Object[] { "Proxy ExceptionCaught", exception });
		} catch (SystemException ex) {
			this.log.log(7, " SystemException......." + ex);
			boolean isFound = false;
			if ((ex.getErrors() != null) && (ex.getErrors().size() > 0)) {
				for (ErrorVO errvo : ex.getErrors()) {
					if ("persistence.lock.objectalreadylocked".equals(errvo.getErrorCode())) {
						isFound = true;
						break;
					}
				}
			}
			if (isFound) {
				throw new ObjectAlreadyLockedException(ex.getErrors());
			}
			throw new SystemException(ex.getErrors());
		}
		this.log.exiting("Processor", "addLocks");
		return acquiredLockVOs;
	}
  }
