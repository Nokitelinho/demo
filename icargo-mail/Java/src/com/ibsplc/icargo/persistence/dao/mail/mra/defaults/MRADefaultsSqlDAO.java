/*
 * MRADefaultsSqlDAO.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAFlightFinaliseVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterConfigRuleVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.miscbilling.blockspace.flight.utilization.vo.BlockSpaceFlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteFilterVO;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicKeyLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVersionLOVVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailExceptionReportsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicDupRecepVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicMasterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
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
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicMessageVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsFilterQuery;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsMapper;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPABillingDetailsFilterQuery;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPABillingEntriesFilterQuery;
//import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.GPABillingFilterQuery;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.PageableQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1556
 * Revision History
 *
 * Version Date Author Description
 *
 * 0.1 Jan 8, 2007 Philip Initial draft 0.2 Jan 28,2007 Kiran Added the method
 * findAllRateCards
 *
 */

public class MRADefaultsSqlDAO extends AbstractQueryDAO implements
		MRADefaultsDAO {

	/**
	 *
	 * Log Variable
	 */
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String MRA_DEFAULTS_LISTBILLINGENTRIES="mail.mra.defaults.findRerateBillableMails";
	
	private static final String MRA_DEFAULTS_LISTINTERLINE="mail.mra.defaults.findRerateInterlineBillableMails";

	private static final String CLASS_NAME = "MRADefaultsSqlDAO";

	private static final String FIND_OVERLAPPING_RATELINES = "mail.mra.defaults.findoverlappingratelines";

	private static final String MRA_DEFAULTS_FINDRATELINEDETAILS = "mail.mra.defaults.findratelinedetails";

	private static final String MRA_DEFAULTS_FINDRATECARDDETAILS = "mail.mra.defaults.findratecarddetails";

	private static final String MRA_DEFAULTS_FIND_ALL_RATECARDS = "mail.mra.defaults.findAllRateCards";

	private static final String MRA_DEFAULTS_FINDRATECARDLOV = "mail.mra.defaults.findratecardlov";

	private static final String MTK_MRA_DEFAULTS_BLGLINDTL = "mail.mra.defaults.findBillingLineDetails";

	private static final String MTK_MRA_DEFAULTS_BLGLINCRGDTL = "mail.mra.defaults.findBillingLineChargeDetails";

	private static final String MRA_DEFAULTS_FINDBILLINGMATRIX = "mail.mra.defaults.findallbillingmarix";
	
	private static final String MRA_DEFAULTS_FINDBILLINGMATRIX_FORMAINTAINBLGMTX = "mail.mra.defaults.findallbillingmarixformaintainblgmtx";

	private static final String MTK_MRA_DEFAULTS_FINDBLGMTXLOV = "mail.mra.defaults.findbillingmatrixlov";

	private static final String MTK_MRA_DEFAULTS_FINDBLGLIN_STATUS = "mail.mra.defaults.findBillingLines.status";

	private static final String MTK_MRA_DEFAULTS_DISPLAYPRORATION = "mail.mra.defaults.displayprorationdetails";

	private static final String MTK_MRA_DEFAULTS_PARMETERS = "mail.mra.defaults.findparametersforbillinlines";

	private static final String MTK_MRA_DEFAULTS_BLGLIN_PARMETERS = "mail.mra.defaults.findbillinglinesbyparameters";

	private static final String MTK_MRA_DEFAULTS_FINDPRORATIONFACTORS = "mail.mra.defaults.findprorationfactors";

	private static final String MTK_MRA_DEFAULTS_FINDMAILSLA = "mail.mra.defaults.findmailsla";

	private static final String BLANK = "";

	private static final String VERSION_LATEST = "LATEST";

	private static final String MTK_MRA_DEFAULTS_VIEWMAILCONTRACT = "mail.mra.defaults.viewmailcontract";

	private static final String MTK_MRA_DEFAULTS_VIEWMAILCONTRACT_HISTORY = "mail.mra.defaults.viewmailcontracthistory";

	private static final String MTK_MRA_DEFAULTS_FINDMAXVERSIONNUMBER = "mail.mra.defaults.findmaximumversionnumber";

	private static final String MTK_MRA_DEFAULTS_FINDVERSIONNUMBERLOV = "mail.mra.defaults.findversionnumberlov";

	private static final String MTK_MRA_DEFAULTS_DSPCTRDTLS = "mail.mra.defaults.displayctrdetails";

	private static final String MTK_MRA_DEFAULTS_DSPSLADTLS = "mail.mra.defaults.displaysladetails";

	private static final String MTK_MRA_DEFAULTS_VIEWDUPLICATEMAILCONTRACT = "mail.mra.defaults.viewduplicatemailcontract";

	private static final String MTK_MRA_DEFAULTS_FINDMAILCONTRACTS = "mail.mra.defaults.findmailcontracts";

	private static final String MTK_MRA_DEFAULTS_FINDMAILCONTRACTS_VERSION_VAL = "mail.mra.defaults.findmailcontractswithvalue";

	private static final String MTK_MRA_DEFAULTS_FINDMAILCONTRACTS_VERSION_NO_VAL = "mail.mra.defaults.findmailcontractswithnovalue";

	private static final String CRA_DEFAULTS_FINDMAILSLAMAXSERIALNUMBER = "mail.mra.defaults.findmailslamaxserialnumber";

	private static final String MTK_MRA_DEFAULTS_FINDBILLINGMATRIXCODES = "mail.mra.defaults.findbillingmatrixcodes";

	private static final String MTK_MRA_DEFAULTS_FINDSLACODES = "mail.mra.defaults.findslacodes";

	private static final String MTK_MRA_DEFAULTS_FINDDESPATCHLOV = "mail.mra.defaults.finddespatchlov";

	private static final String MTK_MRA_DEFAULTS_VALIDATEREPORTINGPERIOD = "mail.defaults.validatereportingperiod";

	private static final String MRA_DEFAULTS_IMPORT_FLOWN_MAILS = "mail.mra.defaults.importflownmails";

	private static final String MRA_DEFAULTS_IMPORT_RECONCILE = "mail.mra.defaults.importtoreconcile";

	private static final String MRA_DEFAULTS_RECONCILE = "mail.mra.defaults.reconcile";

	private static final String MRA_DEFAULTS_FINDINVOICENQUIRYDETAILS = "mail.mra.defaults.findinvoicdetails";

	private static final String MRA_DEFAULTS_FINDINVOICCLAIMSDETAILS = "mail.mra.defaults.findinvoicclaimdetails";

	private static final String MRA_DEFAULTS_FINDDOTRATEDETAILS = "mail.mra.defaults.finddotratedetails";

	private static final String MRA_DEFAULTS_GENERATECLAIMFILE = "mail.mra.defaults.generateclaimfile";

	private static final String MRA_DEFAULTS_FINDINVOICKEYLOV = "mail.mra.defaults.findinvoickeylov";

	private static final String MRA_DEFAULTS_FINDDUPLICATE_RECEP="mail.mra.defaults.findduplicaterecepticles";

	private static final String MRA_DEFAULTS_FINDRECONSECTORIDR="mail.mra.defaults.findreconcilesectoridr";
	
	private static final String MRA_DEFAULTS_FINDROUTINGCARRIERDTL="mail.mra.defaults.findRoutingCarrierDetails";


	private static final String GENERATE_INVOICE="mail.mra.defaults.generateinvoice";

	private static final String MRA_DEFAULTS_FINDBILLINPERIODS="mail.mra.defaults.findbillingperiods";

private static final String MRA_DEFAULTS_FINDDESPATCHDTLS="mail.mra.defaults.finddespatchdls";

	private static final String MRA_DEFAULTS_FINDGPABLGDTLS="mail.mra.defaults.findgpablgdls";

	private static final String MRA_DEFAULTS_FINDDSNSELECTLOV="mail.mra.defaults.finddsnselectlov";
	private static final String MRA_DEFAULTS_COMPUTETOTFORRATEAUDITDETAILS="mail.mra.defaults.computetotforrateauditdetails";
	private static final String MAILTRACKING_MRA_DEFAULTS_REPRORATEDSN="mail.mra.defaults.prorateDsn";

	private static final String MRA_DEFAULTS_FINDPRORATEFACTOR="mail.mra.defaults.findproratefactor";
	private static final String MRA_DEFAULTS_FINDRATEAUDITDETAILS="mail.mra.defaults.findrateauditdetails";
	private static final String MRA_DEFAULTS_FINDLISTRATEAUDITDETAILS="mail.mra.defaults.findlistrateauditdtls";
	private static final String MRA_DEFAULTS_FINDLISTRATEAUDITDETAILSFROMTEMP="mail.mra.defaults.findlistrateauditdtlsfromtemp";
	private static final String MRA_DEFAULTS_FINDMAXSEQNUMFROMMRABILLINGDETAILSHISTORY="mail.mra.defaults.findmaxseqnumfrommrabillingdetailshistory";
	private static final String COMPLETED="C";
	private static final String FAILED="F";
	
	
	private static final String DATE ="yyyyMMdd";
			
	
	private static final String DSN_ROUTING_SCREEN="MRA061"; //Added as a part of ICRD-219936 
	/**
	 * for fetching cca details
	 */
	private static final String MRA_DEFAULTS_FINDCCAREFDTLS="mail.mra.defaults.findccarefdtls";

	private static final String MRA_DEFAULTS_FINDCCADTLS="mail.mra.defaults.findccadtls";
	/**
	 * for fetching cca details from Dsn no
	 */
	private static final String MRA_DEFAULTS_FINDCCA="mail.mra.defaults.findcca";
	private static final String MRA_DEFAULTS_LISTPRORATION_DETAILS="mail.mra.defaults.listProrationDetails";
	/**
	 *
	 *
	 */

	private static final String MRA_DEFAULTS_LISTCCA="mail.mra.defaults.listcca";
	
	private static final String MRA_DEFAULTS_DENSE_RANK_QUERY="SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
	private static final String MRA_DEFAULTS_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	private static final String MRA_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
	
	/**
	 *
	 */
	private static final String MRA_DEFAULTS_FINDINTRLINBLGENTRIESINBLGDTL="mail.mra.defaults.findintrlinblgentriesinblgdtl";
	private static final String MRA_DEFAULTS_FINDINTRLINBLGENTRIESINC66DTL="mail.mra.defaults.findintrlinblgentriesinc66dtl";
	private static final String MRA_DEFAULTS_FINDINTRLINBLGENTRIESINCCADTL="mail.mra.defaults.findintrlinblgentriesinccadtl";
	private static final String MRA_DEFAULTS_FINDINTRLINBLGENTRIESINREJMEMO="mail.mra.defaults.findintrlinblgentriesinrejmemo";
	private static final String FIND_FLIGHTDETAILS = "mail.mra.defaults.findSectorDetails";
	/**
	 * for fetching revenue Details
	 */
	private static final String MRA_DEFAULTS_FLIGHTSECTORREVENUE="mail.mra.defaults.viewflightsectorrevenue";

	/**
	 * for generate invoice integration added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_GENINV_PROC = "mail.mra.defaults.outward.generateinvoice";

	/**
	 * for fetching routing details added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_FINDDSNROUTINGDETAILS = "mail.mra.defaults.dsnroutingdetails";


	private static final String MRA_DEFAULTS_FIND_INTERLINEBILLING_QRY1="mail.mra.defaults.findinterlinebillingdetails_qryone";
	private static final String MRA_DEFAULTS_FIND_INTERLINEBILLING_QRY2="mail.mra.defaults.findinterlinebillingdetails_qrytwo";

	/**
	 * for fetching mail proration log details added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_FINDPRORATIONLOGDETAILS = "mail.mra.defaults.prorationlogdetails";

	/**
	 * for view proration log added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_VIEWPRORATIONLOG="mail.mra.defaults.viewprorationlogdetails";
	/**
	 * for flown details added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_FINDFLOWNDTLS="mail.mra.defaults.findflowndetails";
	/**
	 * for Document Statistics Report added by A-3429
	 *
	 */
	private static final String MRA_DEFAULTS_DOCUMENTSTATISTICS="mail.mra.defaults.documentstatistics";
	/**
	 * query strings used in the function findGPABillingEntries added by A-3434
	 */
	private static final String Find_GPABILLINGDETAILS = "mail.mra.gpabilling.findGPABillingEntries";
	private static final String Find_C66GPABILLINGDETAILS="mail.mra.gpabilling.findc66GPABillingEntries";
	private static final String Find_CCAGPABILLINGDETAILS="mail.mra.gpabilling.findccaGPABillingEntries";

	private static final String MRA_DEFAULTS_UNACCNTED_FROMMRA_R1="mail.mra.defaults.listunaccounteddespatchesfrommraforR1";
	
	private static final String MRA_DEFAULTS_UNACCNTED_FROMMRA_EXP="mail.mra.defaults.listunaccounteddespatchesfrommrawithexception";

	private static final String MRA_DEFAULTS_UNACCNTED_FROMMTK_R2="mail.mra.defaults.listunaccounteddespatchesfrommtkforR2";

	/**
	 * for irregularity report added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_VIEWIRREGULARITYDETAILS="mail.mra.defaults.viewirregularitydetails";
	/**
	 * for accounting Details added by A-3229
	 *
	 */
	private static final String MRA_DEFAULTS_FINDACCDETAILS="mail.mra.defaults.findAccountingDetails";
	/**
	 * for USPS Reporting Details added by A-3229
	 */
	private static final String MRA_DEFAULTS_FINDUSPSDETAILS="mail.mra.defaults.findUSPSReportingDetails";

	private static final String MRA_DEFAULTS_UNACCNTED_FROMMTKCON="mail.mra.defaults.listfrommtkcon";

	private static final String MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA = "mail.mra.defaults.findunaccounteddipatchtotalforR1";
	
	private static final String MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA_WITHEXP = "mail.mra.defaults.findunaccounteddipatchtotalforR1withexp";

	private static final String MRA_DEFAULTS_UNACCNTED_SUMTOTALULD = "mail.mra.defaults.findunaccounteddipatchtotalforuldR2";

	private static final String  MRA_DEFAULTS_UNACCNTED_SUMTOTALCON = "mail.mra.defaults.findunaccounteddipatchtotalforconR2";

	private static final String MRA_DEFAULTS_FINDBLGDTLS="mail.mra.defaults.findbillingdetails";
	
	private static final String MRA_DEFAULTS_FINDOUTSTANDINGBALANCES="mail.mra.defaults.findoutstandingbalances";
	
	private static final String MRA_DEFAULTS_UNACCOUNTEDDESPATCHES_PROCEDURE="mail.mra.defaults.unaccounteddespatchesprocedure";
	
	private static final String MRA_DEFAULTS_OUTPARAMETER_OK="OK";
	
	private static final String MRA_DEFAULTS_REASONCODE="R1";
	
	private static final String MRA_DEFAULTS_FUELSURCHARGEDETAILS="mail.mra.defaults.fuelsurchargedetails";
	
	private static final String MRA_DEFAULTS_FUELSURCHARGEDETAILS_COUNT="mail.mra.defaults.fuelsurchargedetails.count";
	
	private static final String MRA_DEFAULTS_FINDGPABLGDTLSINBLGDTL="mail.mra.defaults.findgpablgdls";
	
	private static final String MRA_DEFAULTS_FINDGPABLGDTLSINC66DTL="mail.mra.defaults.findgpablgdlsinc66";
	
	private static final String MRA_DEFAULTS_FINDGPABLGDTLSINMCA="mail.mra.defaults.findgpablgdlsinmca";
	private static final String FIND_GPABILLING_PERIODS = "mail.mra.defaults.findgpabillingperiods";
	
	private static final String MRA_DEFAULTS_GENERATE_CSV_REPORT = "mail.mra.defaults.generatecsvreport";
	
	private static final String MRA_DEFAULTS_GET_CSV_REPORT_DATA = "mail.mra.defaults.getcsvreportdata";
	
	private static final String MRA_DEFAULTS_RETURN_ERROR = "ERROR";
	
	private static final String MRA_DEFAULTS_FINDMCALOV="mail.mra.defaults.findmcalov";
	
	private static final String MRA_DEFAULTS_FINDDSNLOV="mail.mra.defaults.finddsnlov";
	
	private static final String MRA_DEFAULTS_FIND_BILLINGENTRIES_FLIGHT = "mail.mra.defaults.findbillingentriesforflight";
	
	private static final String MRA_DEFAULTS_TRIGGERPRORATION = "mail.mra.defaults.triggerproration";
	private static final String MRA_DEFAULTS_TRIGMCAACC_PROCEDURE = "mail.mra.defaults.triggermcaacc";

	private static final String MRA_DEFAULTS_TRIGINTMCAACC_PROCEDURE = "mail.mra.defaults.triggerintmcaacc";
	private static final String MRA_DEFAULTS_FINDRATELINEDETAILTOCHECKOVERLAP="mail.mra.defaults.findratelinestocheckoverlap";
	
	private static final String MRA_DEFAULTS_FINDOVERLAPRATELINEDETAILS="mail.mra.defaults.findoverlapratelinedetails";
	/***Added by A-5166 for ICRD-36146 ****/
	private static final String MRA_DEFAULTS_INITIATEPRORATION="mail.mra.defaults.initiateproration";
	
	private static final String MRA_DEFAULTS_BILLINGSITEMASTER="mail.mra.defaults.billingsitemaster";
	
	private static final String MRA_DEFAULTS_FIND_BILLINGSITELOVS = "mail.mra.defaults.listbillinglovs";
	private static final String MRA_DEFAULTS_FIND_COUNTRYDATEOVERLAP_COUNTRY = "mail.mra.defaults.findcountrydateoverlap";
	
	private static final String MRA_DEFAULTS_PRORATE="mail.mra.defaults.prorateDsn";
	private static final String MRA_DEFAULTS_CCASTATUS="mail.mra.defaults.latestCCAStatus";

	private static final String MRA_DEFAULTS_PRORATEEXCEPTIONFLIGHT = "mail.mra.defaults.prorateexceptionflights"; 
	private static final String MRA_DEFAULTS_UPDATEFLIGHTSEG="mail.mra.defaults.updateflightsegment";
	private static final String MRA_DEFAULTS_BILLINGMATRIXUPLOAD="mail.mra.defaults.processBillingMatrixUploadDetails";
	private static final String MRA_DEFAULTS_LISTSURCHARGEPRORATIONDETAILS="mail.mra.defaults.listSurchargeProrationDetails";
	private static final String MRA_DEFAULTS_LISTSURCCAPRORATIONDETAILS="mail.mra.defaults.listSurchargeCCADetails";
	private static final String MRA_GPABILLING_LISTSURCCAPRORATIONDETAILS="mail.mra.gpabilling.listSurchargeDetails";
	private static final String MRA_GPABILLING_LISTSURCHGBILLABLEDETAILS="mail.mra.gpabilling.listSurchargeBillableDetails";
	private static final String MRA_GPABILLING_LISTMCASURCHGBILLABLEDETAILS="mail.mra.gpabilling.listSurchargeMcaillableDetails";
	private static final String MRA_DEFAULTS_LISTSURCHARGEPRORATIONDETAILSFORMCA="mail.mra.defaults.listSurchargeProrationDetailsformca";
	private static final String MRA_DEFAULTS_INTERFACED_MAILS="mail.mra.defaults.updateinterfacedmails";
	public static final String BILLINGSITE_ROWNUM_QUERY =
		"SELECT RESULT_TABLE.* , ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM ( ";
	public static final String MRA_DEFAULTS_BILLINGMATRIXAUDIT="mail.mra.defaults.billingmatrixaudit";
	
	private static final String MRA_DEFAULTS_SAVE_MRADATA_FORRATING="mail.mra.defaults.saveMraDataForRating";
	private static final String MRA_DEFAULTS_VALIDATE_AGENT="mail.mra.defaults.validateAgent";
	
	public static final String BILLINGSITE_SUFFIX_QUERY = " )RESULT_TABLE";
	private static final String MRA_DEFUALTS_BULKACTIVATEUPURATES="mail.mra.defaults.bulkactivationupurates";//ADDED BY A-7871 FOR ICRD-264604
	public static final String MRA_DEFAULTS_PROCESS_BULKPRORATION="mail.mra.defaults.findReproarteMails";//Added by a-7531 for icrd-132487
	
	private static final String MRA_DEFAULTS_FINDBILLINGLINEPARAMETERS="mail.mra.defaults.findbillingLineParmeters";
	private static final String MRA_DEFAULTS_LISTAWMPRORATIONDETAILS="mail.mra.defaults.listAWMProrationDetails";//added by A-7371 for ICRD-234334
	private static  final String MRA_MAILBILLING_INTERFACE = "mail.mra.defaults.mailbillingInterface";//Added by A-7929 for ICRD-245605
	private static final String MAL_MRA_PRORATE_MCA="mail.mra.defaults.proratemca";//Added by A-7929  as part of ICRD-237091

	private static final String MRA_DEFAULTS_ISBSAEXIST="mail.mra.defaults.isBSAExist";
	private static final String MRA_DEFAULTS_FINDBILLINGSTATUS="mail.mra.defaults.findbillingstatus";
	private static final String MRA_DEFAULTS_FINDFLIGHTREVENUEDETAILS = "mail.mra.defaults.findFlightrevenueDetails";
	
	private static final String  MRA_DEFAULTS_UPDATEBSA_INTERFACEFLAG ="mail.mra.defaults.updateBSAInterfaceFlag";
	
	private static final String  MRA_DEFAULTS_FINDBLOCKSPACEFLIGHTS="mail.mra.defaults.findBlockspaceFlights";
	
	private static final String  MRA_UPDATE_TRUCKCOST ="mail.mra.defaults.updatetruckcost";
	
	//Added by A-7794 as part of ICRD-285543
	private static final String  MRA_DEFAULTS_FINDCARRIERDETAILS="mail.mra.defaults.findCarrierDetails";
	
	
	private static final String  MRA_DEFAULTS_FINDMRABILLINGDETAILS="mail.mra.defaults.findMRABillingDetails";
	private static final String  MRA_DEFAULTS_FINDINTERFACEETAILS="mail.mra.defaults.findInterfacedDetails";
	
	private static final String STATUS_FAILED ="F";
	private static final String STATUS_NEW ="N";
	private static final String Find_GPABILLINENTRIES = "mail.mra.defaults.findGPABillingEntries";
	private static final String MRA_VOID_MAILBAGS="mail.mra.defaults.voidmailbags";
	private static final String  MRA_DEFAULTS_FINDVOIDEDINTERFACEETAILS="mail.mra.defaults.findVoidedInterfacedDetails";
	private static final String  MRA_DEFAULTS_FINDVOIDEDINTERFACEETAILSOLDDATA="mail.mra.defaults.findVoidedInterfacedDetailsOldData";
	
	private static final String MRA_DEFAULTS_IMPORTCONSIGNMENTTOMRA="mail.mra.defaults.importConsignmentToMRA";
	private static final String MRA_DEFAULTS_USPS_INTERNATIONAL_INCENTIVE="mail.mra.defaults.finduspsinternationalincentivejobdetails";
	private static final String MRA_DEFAULTS_USPS_CALCULATE_INCENTIVE="mail.mra.defaults.calculateincentive";
	private static final String MRA_DEFAULTS_FINDMAILFROMCARDITSFORIMPORT="mail.mra.defaults.findmailsfromcarditforimport";
	private static final String MRA_DEFAULTS_FINDMAILBAGINMRA="mail.mra.defaults.findmailbagexistsinmra";
	private static final String MRA_DEFAULTS_IMPORT_RESDIT_DATA="mail.mra.defaults.importResditData";
	private static final String Find_CONSIGNMENTDETAILS = "mail.mra.gpabilling.listconsignmentdetails";
	private static final String Find_C66CONSIGNMENTDETAILS="mail.mra.gpabilling.listc66consignmentdetails";
	private static final String Find_CCACONSIGNMENTDETAILS="mail.mra.gpabilling.listccaconsignmentdetails";
	private static final String MRA_DEFAULTS_FINDMALDETAILS="mail.mra.defaults.findMailDetails";
	
	
	private static final String GENERATE_INTERFACE_FILE_SAPFI="mail.mra.defaults.generateSAPFIFile";
	private static final String EMPTY_STRING = "";
	private static final String COLUMN_SEP = "#";
	private static final String MRA_DEFAULTS_UPDATEROUTE_AND_REPRORATE = "mail.mra.defaults.updaterouteandreprorate";
	private static final String MRA_DEFAULTS_REIMPORT_PABUILT_MAILBAGS_TO_MRA = "mail.mra.defaults.reimportpabuiltmailbagstomra";
	private static final String MRA_DEFAULTS_FIND_MAILBAG_FOR_PABUILT_UPDATE = "mail.mra.defaults.findmailbagsforpabuiltupdate";
	private static final String FIND_MAIL_BILLING_LIST = "mail.mra.defaults.billingdetails";
	private static final String FIND_BILLING_PERIODS = "mail.mra.defaults.findbillingscheduleperiods";
	private static final String MRA_DEFAULTS_IMPORT_PROVISIONAL_RATE_DETAILS ="mail.mra.defaults.importprovisionalRateDetails";
	private static final String CALCULATE_PROVISIONAL_RATE ="mail.mra.defaults.calculateUpfrontRate";
	private static final String MRA_DEFAULTS_GLINTERFACEDATA ="mail.mra.defaults.glInterfaceData";
	
	public MRADefaultsSqlDAO() {

	}

	/**
	 * Finds the rate card and assosciated rate lines
	 *
	 * @param companyCode
	 * @param rateCardID
	 * @return RateCardVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public RateCardVO findRateCardDetails(String companyCode, String rateCardID)
			throws SystemException, PersistenceException {
		log.entering("MRADefaultsSQLDAO", "findRateCardDetails");
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDRATECARDDETAILS);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, rateCardID);
		/*
		 * System.out.println("inside card dao"+query.getSingleResult(new
		 * RateCardDetailsMapper()));
		 */
		return query.getSingleResult(new RateCardDetailsMapper());
	}

	/**
	 * Returns the ratelines based on the filter criteria
	 *
	 * @param rateLineFilterVO
	 * @return Page<RateLineVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<RateLineVO> findRateLineDetails(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException {
		log.entering("MRADefaultsSQLDAO", "findRateLineDetails");
		
		int pageNumber = rateLineFilterVO.getPageNumber();
		String baseQuery = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_FINDRATELINEDETAILS);
		
		StringBuilder rankQuery = new StringBuilder(MRA_DEFAULTS_ROWNUM_RANK_QUERY);		
		String baseQuery1=rankQuery.append(baseQuery).toString();
		
		PageableNativeQuery<RateLineVO> pgqry=new RateLineFilterQuery(rateLineFilterVO.getTotalRecordCount(),new RateLineDetailsMapper(), baseQuery1, rateLineFilterVO);
		pgqry.append(BILLINGSITE_SUFFIX_QUERY);
		
		return pgqry.getPage(rateLineFilterVO.getPageNumber());
		
	}
	/**
	 * Returns the ratelines based on the filter criteria
	 *@author A-5166
	 * Added for ICRD-17262 on 07-Feb-2013
	 * @param rateLineFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	
	public Collection<RateLineVO> findRateLineDetail(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException {
		log.entering("MRADefaultsSQLDAO", "findRateLineDetail");
		Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
		int index =0;
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDRATELINEDETAILTOCHECKOVERLAP);					
	
		query.setParameter(++index, rateLineFilterVO.getCompanyCode());
		query.setParameter(++index, rateLineFilterVO.getRateCardID());
		query.setParameter(++index, rateLineFilterVO.getStartDate());
		query.setParameter(++index, rateLineFilterVO.getEndDate());
		query.setParameter(++index, rateLineFilterVO.getCompanyCode());
		query.setParameter(++index, rateLineFilterVO.getRateCardID());
		query.setParameter(++index, rateLineFilterVO.getStartDate());
		query.setParameter(++index, rateLineFilterVO.getEndDate());
		return query.getResultList(new RateLineDetailsMapper());
	}

	/**
	 * Finds the rate cards based on the filter
	 *
	 * @author a-2049
	 * @param rateCardFilterVO
	 * @return Page<RateCardVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<RateCardVO> findAllRateCards(RateCardFilterVO rateCardFilterVO)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findAllRateCards");
		
		StringBuilder rankQuery=new StringBuilder();
		rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
		String nativeQuery = this.getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FIND_ALL_RATECARDS);
		String baseQuery=rankQuery.append(nativeQuery).toString();
		log.log(Log.INFO, " <-- the baseQuery for findAllRateCards --> \n\n",
				baseQuery);
		//Modified by : A-5175 on 16-Oct-2012 starts
		//Query fetchQuery = new RateCardFilterQuery(baseQuery, rateCardFilterVO);
		//PageableQuery<RateCardVO> pageQuery = new PageableQuery<RateCardVO>(
		//		fetchQuery, new RateCardDetailsMapper());
		PageableNativeQuery<RateCardVO> pageableNativeQuery=new RateCardFilterQuery(rateCardFilterVO.getTotalRecordCount(), new RateCardDetailsMapper(), baseQuery, rateCardFilterVO);
        pageableNativeQuery.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
        log.log(Log.INFO, "Final Query>>>>", pageableNativeQuery);
		//return pageQuery.getPage(rateCardFilterVO.getDisplayPage());
		return pageableNativeQuery.getPage(rateCardFilterVO.getDisplayPage());
 		//ends
	}

	/**
	 * This method finds if there exists any overlapping ratelines with the same
	 * origin destination pair.. returns a collection of rateline error vos if
	 * ther exists overlapping ratelines else returns a null.
	 *
	 * @author a-2270
	 * @param rateLineVOs
	 *            Collection<RateLineVO>
	 * @return Collection<RateLineErrorVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RateLineErrorVO> findOverLappingRateLines(
			Collection<RateLineVO> rateLineVOs) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "saveRatelineStatus");

		String baseQry = getQueryManager().getNamedNativeQueryString(
				FIND_OVERLAPPING_RATELINES);
		Query query = new RateLineErrorFilterQuery(baseQry, rateLineVOs);

		query.setSensitivity(true);

		Collection<RateLineErrorVO> newRateLineErrorVOs = new ArrayList<RateLineErrorVO>();

		Collection<RateLineErrorVO> rateLineErrorVOs = query
				.getResultList(new RateLineErrorMapper());

		log.log(Log.FINE, "rateLineErrorVOs  ", rateLineErrorVOs);
		if (rateLineErrorVOs == null) {
			newRateLineErrorVOs = null;
			log.log(Log.FINE, "rateLineErrorVOs  is null");
		} else {

			log.log(Log.FINE,
					"INSIDE FOR FOR UPDATING THE ERROR VO FOR CURRENT VALUES");
			for (RateLineErrorVO rateLineErrorVO : rateLineErrorVOs) {
				String origin = rateLineErrorVO.getOrigin();
				String desination = rateLineErrorVO.getDestination();
				LocalDate fromDate = rateLineErrorVO
						.getCurrentValidityStartDate();
				LocalDate toDate = rateLineErrorVO.getCurrentValidityEndDate();
				for (RateLineVO rateLineVO : rateLineVOs) {
					log.log(Log.FINE, "rateLineVO  ", rateLineVO);
					if (origin.equals(rateLineVO.getOrigin())
							&& desination.equals(rateLineVO.getDestination())
							&& ((rateLineVO.getValidityEndDate().isGreaterThan(
									fromDate) || (rateLineVO
									.getValidityEndDate().compareTo(fromDate)) == 0))
							&& ((rateLineVO.getValidityStartDate()
									.isLesserThan(toDate) || (rateLineVO
									.getValidityStartDate().compareTo(toDate)) == 0))) {
						rateLineErrorVO.setNewValidityEndDate(rateLineVO
								.getValidityEndDate());
						rateLineErrorVO.setNewRateCardID(rateLineVO
								.getRateCardID());
						rateLineErrorVO.setNewValidityStartDate(rateLineVO
								.getValidityStartDate());
						newRateLineErrorVOs.add(rateLineErrorVO);
						break;

					}
					//Added as part of ICRD-117884 starts
					else if(origin.equals(rateLineVO.getDestination())
							&& desination.equals(rateLineVO.getOrigin())
							&& ((rateLineVO.getValidityEndDate().isGreaterThan(
									fromDate) || (rateLineVO
											.getValidityEndDate().compareTo(fromDate)) == 0))
									&& ((rateLineVO.getValidityStartDate()
											.isLesserThan(toDate) || (rateLineVO
											.getValidityStartDate().compareTo(toDate)) == 0))) {
								rateLineErrorVO.setNewValidityEndDate(rateLineVO
										.getValidityEndDate());
								rateLineErrorVO.setNewRateCardID(rateLineVO
										.getRateCardID());
								rateLineErrorVO.setNewValidityStartDate(rateLineVO
										.getValidityStartDate());
								newRateLineErrorVOs.add(rateLineErrorVO);
								break;

							}
					//Added as part of ICRD-117884 ends		
					
				}
			}
		}
		log
				.log(Log.FINE, "errorVos returned from server ",
						newRateLineErrorVOs);
		return newRateLineErrorVOs;
	}

	/**
	 * @param rateLineFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<RateLineVO> findAllRateLines(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException {
		// int pageNumber=rateLineFilterVO.getPageNumber();
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDRATELINEDETAILS);
		Query query = new RateLineFilterQuery(rateLineFilterVO.getTotalRecordCount(),new RateLineDetailsMapper(), baseQuery, rateLineFilterVO);
		// PageableQuery<RateLineVO> pgqry = null;
		// pgqry = new PageableQuery<RateLineVO>(query,new
		// RateLineDetailsMapper());
		// System.out.println("inside rate line dao"+pgqry.getPage(pageNumber));
		// return pgqry.getPage(pageNumber);
		query.setSensitivity(true);
		return query.getResultList(new RateLineDetailsMapper());

	}

	/**
	 * @param companyCode
	 * @param rateCardID
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<RateCardLovVO> findRateCardLov(String companyCode, String rateCardID, int pageNumber) 
	throws SystemException, PersistenceException {
		// Modified by A-5220 for ICRD-32647 starts
		String qry = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_FINDRATECARDLOV);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(qry);
		PageableNativeQuery<RateCardLovVO> pgqry = null;
		pgqry = new PageableNativeQuery<RateCardLovVO>(0, rankQuery.toString(), new RateCardLovMapper());
		// Modified by A-5220 for ICRD-32647 ends
		int index = 0;
		pgqry.setParameter(++index, companyCode);

		if (rateCardID != null && rateCardID.trim().length() > 0) {
			StringBuilder sbul = new StringBuilder();
			
			if (rateCardID.contains(",")) {
				String[] rateCardIDs = rateCardID.split(",");
				sbul.append("AND  MST.RATCRDCOD  IN ");
				String groupNameQuery = getWhereClause(rateCardIDs);
				sbul.append(groupNameQuery).append(")");
			} else {
				sbul.append(" AND MST.RATCRDCOD LIKE '").append(rateCardID.trim().replace('*', '%'))
				.append('%').append("'").toString();
			}
			pgqry.append(sbul.toString());
		}
		log.log(Log.INFO, "query", qry);
		// Modified by A-5220 for ICRD-32647 starts
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		// Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(pageNumber);
	}

	/**
	 * 
	 * 	Method		:	MRADefaultsSqlDAO.getWhereClause
	 *	Added by 	:	A-4803 on 14-May-2015
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

	/**
	 * @author A-2280 This method finds Billing matrix depending on the filter
	 *         values
	 * @param billingMatrixFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findAllBillingMatrix");
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDBILLINGMATRIX);
		StringBuilder rankQuery = new StringBuilder().append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
		rankQuery.append(baseQuery);
		PageableNativeQuery<BillingMatrixVO> pgqry = new 
		BillingMatrixFilterQuery(new BillingMatrixMapper(),
				billingMatrixFilterVO, rankQuery.toString());
		pgqry.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);//added by T-1927 for the bug ICRD-24962
		log.exiting(CLASS_NAME, "findAllBillingMatrix");
        return pgqry.getPage(billingMatrixFilterVO.getPageNumber());
	}

	/**
	 * @author A-2398
	 * @param blgMtxFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public BillingMatrixVO findBillingMatrixDetails(
			BillingMatrixFilterVO blgMtxFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findBillingMatrixDetails");
		String baseQry = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDBILLINGMATRIX_FORMAINTAINBLGMTX);
		
		PageableNativeQuery<BillingMatrixVO> pgqry = new BillingMatrixFilterQuery(new BillingMatrixMapper(),blgMtxFilterVO,baseQry);
		
		log.exiting(CLASS_NAME, "findBillingMatrixDetails");
		return pgqry.getSingleResult(new BillingMatrixMapper());
	}

	/**
	 * @author A-2398
	 * @param billingLineFilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<BillingLineVO> findBillingLineDetails(
			BillingLineFilterVO billingLineFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findBillingLineDetails");
		int index = 0;
		
		//Added by A-5497 as part of ICRD-67884
		StringBuilder prefixQry = new StringBuilder(MRA_DEFAULTS_DENSE_RANK_QUERY);
		prefixQry.append(" BLGMTXCOD,BLGLINSEQNUM, CMPCOD  ASC ) AS RANK FROM ( ");
		String query = getQueryManager().getNamedNativeQueryString(MTK_MRA_DEFAULTS_BLGLINDTL);
		//String qry=prefixQry.append(qry).toString();
		prefixQry.append(query);
		 PageableNativeQuery<BillingLineVO> qry = new PageableNativeQuery<BillingLineVO>(billingLineFilterVO.getTotalRecordsCount(), 
				 prefixQry.toString(), new BillingLineMultiMapper());
		//Query qry = getQueryManager().createNamedNativeQuery(
		//MTK_MRA_DEFAULTS_BLGLINDTL);

		qry.setParameter(++index, billingLineFilterVO.getCompanyCode());
		if (billingLineFilterVO.getBillingMatrixId() != null) {
			qry.append(" AND LIN.BLGMTXCOD = ? ");

			qry.setParameter(++index, billingLineFilterVO.getBillingMatrixId());
		}
		if ((billingLineFilterVO.getPoaCode() != null
				&& billingLineFilterVO.getPoaCode().trim().length() > 0)
				&&(billingLineFilterVO.getAirlineCode() != null
				&& billingLineFilterVO.getAirlineCode().trim().length() > 0)){
						log.log(Log.FINE, "inside both");
			qry.append("AND(LIN.BILTOOPTYCOD = ?OR LIN.BILTOOPTYCOD = ?)");

			qry.setParameter(++index, billingLineFilterVO.getPoaCode());
			qry.setParameter(++index, billingLineFilterVO.getAirlineCode());
			}
		else{
			if (billingLineFilterVO.getPoaCode() != null

				&& billingLineFilterVO.getPoaCode().trim().length() > 0) {
			qry.append(" AND LIN.BILTOOPTYCOD = ? ");
			qry.setParameter(++index, billingLineFilterVO.getPoaCode());

		}
		if (billingLineFilterVO.getAirlineCode() != null
				&& billingLineFilterVO.getAirlineCode().trim().length() > 0) {
			qry.append(" AND LIN.BILTOOPTYCOD = ? ");
			qry.setParameter(++index, billingLineFilterVO.getAirlineCode());
		}
	}
		if (billingLineFilterVO.getBillingSector() != null
				&& billingLineFilterVO.getBillingSector().trim().length() > 0) {
			qry.append(" AND LIN.BILSEC = ? ");
			qry.setParameter(++index, billingLineFilterVO.getBillingSector());
		}
		if (billingLineFilterVO.getUnitCode() != null
				&& billingLineFilterVO.getUnitCode().trim().length() > 0) {
			qry.append(" AND LIN.UNTCOD = ? ");
			qry.setParameter(++index, billingLineFilterVO.getUnitCode());
		}
		if (billingLineFilterVO.getValidityStartDate() != null) {
			qry.append(" AND LIN.VLDSTRDAT = ? ");
			qry.setParameter(++index, billingLineFilterVO
					.getValidityStartDate());
		}
		if (billingLineFilterVO.getValidityEndDate() != null) {
			qry.append(" AND LIN.VLDENDDAT = ? ");
			qry.setParameter(++index, billingLineFilterVO.getValidityEndDate());
		}
		if (billingLineFilterVO.getBillingLineStatus() != null
				&& billingLineFilterVO.getBillingLineStatus().trim().length() > 0) {

			if ("E".equals(billingLineFilterVO.getBillingLineStatus())) {
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				qry.append(" AND LIN.VLDENDDAT <? ");
				qry.setParameter(++index, currentDate.toSqlDate());
			} else {
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				qry.append(" AND LIN.VLDENDDAT >=? ");
				qry.append(" AND LIN.BLGLINSTA = ? ");
				qry.setParameter(++index, currentDate.toSqlDate());
				qry.setParameter(++index, billingLineFilterVO
						.getBillingLineStatus());
			}

			/*
			 * qry.append(" AND LIN.BLGLINSTA = ? ");
			 * qry.setParameter(++index,billingLineFilterVO.getBillingLineStatus());
			 */
		}
		
		
		
		
/*		qry.append(" ORDER BY BLGMTXCOD,BLGLINSEQNUM, CMPCOD ASC");
		log.log(Log.FINE, "B4 pageable query");*/
		qry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		//PageableQuery<BillingLineVO> pgqry = new PageableQuery<BillingLineVO>(
			//	qry, new BillingLineMultiMapper());
		log.log(Log.FINE, "After pageable query");

		log.exiting(CLASS_NAME, "findBillingLineDetails");
		return qry.getPage(billingLineFilterVO.getPageNumber());

	}

	/**
	 * @param blgLineFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<BillingLineVO> findBillingLineValues(
			BillingLineFilterVO blgLineFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findBillingLineValues");
		Page<BillingLineVO> returnPage = null;
		StringBuilder rankQuery = new StringBuilder().append(MRA_DEFAULTS_DENSE_RANK_QUERY);
		rankQuery.append("BLGMTXCODLIN,BLGLINSEQNUMLIN,CMPCODLIN) AS RANK FROM (");
		String baseQry = getQueryManager().getNamedNativeQueryString(
				MTK_MRA_DEFAULTS_BLGLIN_PARMETERS);
		
			
	
        String dynamicQryOne = null;
        
        StringBuilder string = new StringBuilder();
        if(isOracleDataSource()) {
            dynamicQryOne = string.append("(SELECT SUBSTR(str, 1, instr(str, ':')-1) KEYCOD,").append("SUBSTR(str, instr(str, ':')         +1) KEYVAL from")
            		.append("(SELECT LEVEL ,SUBSTR ( TOKEN , DECODE(LEVEL, 1, 1, INSTR(TOKEN, DELIMITER, 1, LEVEL-1)+1) , INSTR(TOKEN, DELIMITER, 1, LEVEL) - DECODE(LEVEL, 1, 1, INSTR(TOKEN, DELIMITER, 1, LEVEL-1)+1) ) str")
            		.append(" FROM  ( SELECT ? AS TOKEN, ',' AS DELIMITER FROM DUAL )").append(" CONNECT BY INSTR(TOKEN, DELIMITER, 1, LEVEL)>0))").toString(); 
									
							 
								  
          
        }else {
            dynamicQryOne = string.append("(SELECT split_part(STR, ':', 1)KEYCOD, ").append("  split_part(STR, ':', 2)KEYVAL ").append(" FROM (SELECT unnest(string_to_array(TOKEN,DELIMITER)) STR ")
            		.append(" FROM (SELECT ? AS TOKEN, ',' AS DELIMITER FROM DUAL) MST  )MST )").toString();
            
        }   
        baseQry = String.format(baseQry, dynamicQryOne);
        
		
		
		StringBuilder sufixQuery= new StringBuilder();
		StringBuilder parQuery = new StringBuilder();
		boolean parExist = false;
		sufixQuery.append(")").append(" SELECT DISTINCT INC.CMPCODLIN,").append(" INC.BLGMTXCODLIN,")
		.append(" INC.BLGLINSEQNUMLIN," ).append(" INC.BLGLINSTALIN,").append(" INC.BILSEC,").append(" INC.UNTCOD,").append(" INC.BLGMTXPOACOD,")
		.append(" INC.BILPTYARLCOD,").append(" INC.VLDSTRDATLIN,").append(" INC.VLDENDDATLIN,").append(" INC.REVEXPFLGLIN,")
		.append(" INC.RATE,").append(" INC.BLGBAS,").append(" INC.CURRENCY,").append(" INC.PARCOD,").append(" INC.PARVAL")
		.append(" FROM MTX INC" ).append(" WHERE ? = 'N'");
		if("A".equals(blgLineFilterVO.getBillingLineStatus()) || ("E".equals(blgLineFilterVO.getBillingLineStatus()))){
			parExist = true;
			parQuery.append(" AND INC.VLDENDDATLIN ");
			if("A".equals(blgLineFilterVO.getBillingLineStatus()))
				{
				parQuery.append(" >= ? ");
				}
			else if ("E".equals(blgLineFilterVO.getBillingLineStatus()))
				{
				parQuery.append(" <= ? ");
				}
		}else if("C".equals(blgLineFilterVO.getBillingLineStatus()) || "I".equals(blgLineFilterVO.getBillingLineStatus()) || "N".equals(blgLineFilterVO.getBillingLineStatus())){
			parExist = true;
			parQuery.append(" AND INC.BLGLINSTALIN = ? ");
		}
		sufixQuery.append(parQuery.toString());
		//this.setParameter(++index, parFlt); 
		sufixQuery.append(" UNION ALL ").append(" SELECT DISTINCT INC.CMPCODLIN,").append(" INC.BLGMTXCODLIN,")
		.append(" INC.BLGLINSEQNUMLIN,").append(" INC.BLGLINSTALIN,").append(" INC.BILSEC,").append(" INC.UNTCOD,").append(" INC.BLGMTXPOACOD,")
		.append(" INC.BILPTYARLCOD,").append(" INC.VLDSTRDATLIN,").append(" INC.VLDENDDATLIN,").append(" INC.REVEXPFLGLIN,")
		.append(" INC.RATE,").append(" INC.BLGBAS,").append(" INC.CURRENCY,").append(" INC.PARCOD,").append(" INC.PARVAL")
		.append(" FROM MTX INC ").append(" INNER JOIN BILPAR ON BILPAR.KEYCOD = INC.PARCOD AND INSTR(',' ||INC.PARVAL ||',', ',' ||BILPAR.KEYVAL ||',')  >0"+
				" WHERE ? = 'Y'"
				); 
		if(parExist)
			{
			sufixQuery.append(parQuery.toString());
			}
		//this.setParameter(++index, parFlt);
		sufixQuery.append(" AND NOT EXISTS").append(" (SELECT 1").append(" FROM BILPAR").append(" WHERE KEYCOD NOT IN")
		.append(" (SELECT PARCOD").append(" FROM MTX EXC").append(" WHERE EXC.CMPCODLIN  = INC.CMPCODLIN").append(" AND EXC.BLGMTXCODLIN  = INC.BLGMTXCODLIN").append(
				" AND EXC.BLGLINSEQNUMLIN = INC.BLGLINSEQNUMLIN ) )").append(
				" AND NOT EXISTS ").append(
				" (SELECT 1 ").append(
				" FROM MTX EXC ").append(
				" INNER JOIN BILPAR ").append(
				" ON BILPAR.KEYCOD = EXC.PARCOD ").append(
				" AND INSTR(',' ||EXC.PARVAL ||',', ',' ||BILPAR.KEYVAL||',') =0").append(
				" WHERE EXC.CMPCODLIN     = INC.CMPCODLIN").append(
				" AND EXC.BLGMTXCODLIN    = INC.BLGMTXCODLIN").append(
				" AND EXC.BLGLINSEQNUMLIN = INC.BLGLINSEQNUMLIN").append(
				" AND EXC.EXCFLG          = 'N')");
		//return filterQuery.toString();
		//Query qry = new BlgLineByParmeterFilterQuery(baseQry, blgLineFilterVO); 
		PageableNativeQuery<BillingLineVO> pgqry = new BlgLineByParmeterFilterQuery(blgLineFilterVO, baseQry, sufixQuery.toString(), rankQuery.toString(), new BillingLineDetailsMultiMapper(),isOracleDataSource());
		log.log(Log.INFO, "Qry is--->>>", pgqry.toString());
/*		PageableQuery<BillingLineVO> pgqry = new PageableQuery<BillingLineVO>(
				qry, new BillingLineMapper());*/
/*		 PageableNativeQuery<BillingLineVO> pgqry = new PageableNativeQuery<BillingLineVO>(blgLineFilterVO.getTotalRecordsCount(), 
				 qry.toString(), new BillingLineDetailsMultiMapper()); */
		returnPage = pgqry.getPage(blgLineFilterVO.getPageNumber());

		if (returnPage != null && returnPage.size() > 0) {
		log.log(Log.INFO, "Returned Page of Billing Line-size is--->",
				returnPage.size());			
			String baseQryPar = getQueryManager().getNamedNativeQueryString(
					MTK_MRA_DEFAULTS_PARMETERS);
			Query qryPar = new BlgLineParameterFilterQuery(baseQryPar,
					returnPage);
			log.log(Log.INFO, "Qry is--->>>", qryPar.toString());
			ArrayList<BillingLineVO> blgLineParameters = (ArrayList<BillingLineVO>) qryPar
					.getResultList(new BillingLineParMultiMapper());
			log.log(Log.INFO,
					"Returned collection of Billing Line Parameters is--->",
					blgLineParameters);
			for (BillingLineVO vo : blgLineParameters) {
				for (BillingLineVO lineVO : returnPage) {
					if (vo.getCompanyCode().equals(lineVO.getCompanyCode())
							&& vo.getBillingLineSequenceNumber() == lineVO
									.getBillingLineSequenceNumber()
							&& vo.getBillingMatrixId().equals(
									lineVO.getBillingMatrixId())) {
						lineVO.setBillingLineParameters(vo
								.getBillingLineParameters());
						break;
					}
				}
			}
			
			/**
			 * getting the charge details
			 */
			String qryChargeString = getQueryManager().getNamedNativeQueryString(
					MTK_MRA_DEFAULTS_BLGLINCRGDTL);
			Query qryCharge = new BlgLineByChargeFilterQuery(qryChargeString,
					returnPage);
			ArrayList<BillingLineVO> blgLineCharges = (ArrayList<BillingLineVO>) qryCharge
					.getResultList(new BillingLineChargeMultiMapper());
			if(blgLineCharges!=null){
				for(BillingLineVO billingLineVO :blgLineCharges){
					for (BillingLineVO lineVO : returnPage) {
						if (billingLineVO.getCompanyCode().equals(lineVO.getCompanyCode())
								&& billingLineVO.getBillingLineSequenceNumber() == lineVO
										.getBillingLineSequenceNumber()
								&& billingLineVO.getBillingMatrixId().equals(
										lineVO.getBillingMatrixId())) {
							lineVO.setBillingLineDetails(billingLineVO.getBillingLineDetails());
							lineVO.setSurchargeIndicator(billingLineVO.getSurchargeIndicator());
							break;
						}
				}
			}
			}
			
		}

		log.exiting(CLASS_NAME, "findBillingLineValues");
		return returnPage;
	}

	/**
	 * Method for finding all the billing lines with the parameters
	 *
	 * @author A-1872 Mar 5, 2007
	 * @param billingLineVO
	 * @param billingLineStatus
	 * @return Collection<BillingLineVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<BillingLineVO> findOverlappingBillingLines(
			BillingLineVO billingLineVO, String billingLineStatus)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findOverlappingBillingLines");
		Collection<BillingLineVO> billingLines = null;
		Query constructedQuery = null;
		String baseQuery = getQueryManager().getNamedNativeQueryString(
				MTK_MRA_DEFAULTS_FINDBLGLIN_STATUS);
		constructedQuery = new OverlappingBillingFilterQuery(baseQuery,
				billingLineVO, billingLineStatus);
		billingLines = constructedQuery.getResultList(new BillingLineMapper());
		log.exiting(CLASS_NAME, "findOverlappingBillingLines");
		return billingLines;
	}

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param billingMatrixCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode, String billingMatrixCode, 
			int pageNumber) throws SystemException, PersistenceException {
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<BillingMatrixLovVO> pgqry = null;
		String basequery = getQueryManager().getNamedNativeQueryString(MTK_MRA_DEFAULTS_FINDBLGMTXLOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(basequery);
		// Added by A-5280 for ICRD-32647 ends
		int index = 0;
		pgqry = new PageableNativeQuery<BillingMatrixLovVO>(0, rankQuery.toString(), 
				new BillingMatrixLovMapper());
		pgqry.setParameter(++index, companyCode);
		
		if (billingMatrixCode != null && billingMatrixCode.trim().length() > 0) {
			StringBuilder sbul = new StringBuilder();
			
			if (billingMatrixCode.contains(",")) {
				String[] billingMatrixCodes = billingMatrixCode.split(",");
				sbul.append("AND  BLGMTX.BLGMTXCOD  IN ");
				String groupNameQuery = getWhereClause(billingMatrixCodes);
				sbul.append(groupNameQuery).append(")");
			} else {
				sbul.append(" AND BLGMTX.BLGMTXCOD LIKE '").append(billingMatrixCode.trim()
						.replace('*', '%')).append('%').append("'").toString();
			}
			pgqry.append(sbul.toString());
		}
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		return pgqry.getPage(pageNumber);
	}

	/**
	 * Method for finding the billing lines with the provided status
	 *
	 * TODO Purpose
	 *
	 * @author A-1872 Mar 6, 2007
	 * @param billingLineFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findBillingLines(com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO)
	 */
	public Collection<BillingLineVO> findBillingLines(
			BillingLineFilterVO billingLineFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findBillingLines");
		Collection<BillingLineVO> billingLines = null;
		Query baseQuery = null;
		int index = 0;
		baseQuery = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDBLGLIN_STATUS);
		baseQuery.setParameter(++index, billingLineFilterVO.getCompanyCode());
		if (billingLineFilterVO.getBillingMatrixId() != null) {
			baseQuery.append(" AND LIN.BLGMTXCOD = ? ");
			baseQuery.setParameter(++index, billingLineFilterVO
					.getBillingMatrixId());
		}
		if (billingLineFilterVO.getBillingLineStatus() != null
				&& billingLineFilterVO.getBillingLineStatus().trim().length() > 0) {
			baseQuery.append(" AND LIN.BLGLINSTA IN ( ");
			int statusLength = billingLineFilterVO.getBillingLineStatus()
					.split(",").length;
			// log.log(Log.INFO, "<:statusLenght:>" + statusLength);
			if (statusLength > 0) {
				int count = 0;
				for (String status : billingLineFilterVO.getBillingLineStatus()
						.split(",")) {
					count++;
					baseQuery.append("?");
					baseQuery.setParameter(++index, status);
					log.log(Log.INFO, "<:count:>", count);
					log.log(Log.INFO, "<:statusLength:>", statusLength);
					if (count < statusLength) {
						baseQuery.append(" ,");
					}
				}
				baseQuery.append(" )");
			}
		}
		baseQuery.setSensitivity(true);
		billingLines = baseQuery.getResultList(new BillingLineMapper());

		if (billingLines != null && billingLines.size() > 0) {
			String baseQryPar = getQueryManager().getNamedNativeQueryString(
					MTK_MRA_DEFAULTS_PARMETERS);
			ArrayList<BillingLineVO> billingLineVOs = new ArrayList<BillingLineVO>(
					billingLines);
			Page<BillingLineVO> newPage = new Page<BillingLineVO>(
					billingLineVOs, 1, 25, billingLineVOs.size(), 0, 0, false);
			Query qryPar = new BlgLineParameterFilterQuery(baseQryPar, newPage);
			log.log(Log.INFO, "Qry is--->>>", qryPar.toString());
			ArrayList<BillingLineVO> blgLineParameters = (ArrayList<BillingLineVO>) qryPar
					.getResultList(new BillingLineParMultiMapper());
			// log.log(1,"Returned collection of Billing Line Parameters
			// is--->"+blgLineParameters);
			// int size = billingLines.size();
			// int c =0;
			for (BillingLineVO vo : blgLineParameters) {
				for (BillingLineVO lineVO : billingLines) {
					if (vo.getCompanyCode().equals(lineVO.getCompanyCode())
							&& vo.getBillingLineSequenceNumber() == lineVO
									.getBillingLineSequenceNumber()
							&& vo.getBillingMatrixId().equals(
									lineVO.getBillingMatrixId())) {
						lineVO.setBillingLineParameters(vo
								.getBillingLineParameters());
						break;
					}
				}
				/*
				 * if(c != size){ log.log(1,"The billingLineVO
				 * are---->"+((ArrayList<BillingLineVO>)billingLines).get(c++)); }
				 */
			}
		}
		log.exiting(CLASS_NAME, "enclosing_method");
		return billingLines;
	}

	/**
	 * This method displays Mail Proration Details
	 *
	 * @author a-2518
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "displayProrationDetails");
		Collection<ProrationDetailsVO> prorationDetailsVos = null;
		Query query = null;
		int index = 0;
		query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_DISPLAYPRORATION);
		query.setParameter(++index, prorationFilterVO.getCompanyCode());
		query
				.setParameter(++index, prorationFilterVO
						.getDespatchSerialNumber());
		if (prorationFilterVO.getConsigneeDocumentNumber() != null
				&& prorationFilterVO.getConsigneeDocumentNumber().trim()
						.length() > 0) {
			query.append(" AND PRODTL.CSGDOCNUM = ?");
			query.setParameter(++index, prorationFilterVO
					.getConsigneeDocumentNumber().trim());
		}
		if (prorationFilterVO.getFlightCarrierIdentifier() != 0) {
			query.append(" AND PRODTL.FLTCARIDR = ?");
			query.setParameter(++index, prorationFilterVO
					.getFlightCarrierIdentifier());
		}
		if (prorationFilterVO.getFlightNumber() != null
				&& prorationFilterVO.getFlightNumber().trim().length() > 0) {
			query.append(" AND PRODTL.FLTNUM = ?");
			query.setParameter(++index, prorationFilterVO.getFlightNumber()
					.trim());
		}
		if (prorationFilterVO.getFlightDate() != null) {
			query.append(" AND PRODTL.FLTDAT = ?");
			query.setParameter(++index, prorationFilterVO.getFlightDate());
		}
		query.append(" ORDER BY CMPCOD, SERNUM, BLGBASNUM");
		log.log(Log.FINE, "<--------------- QUERY --------------->", query);
		return query.getResultList(new ProrationDetailsMultiMapper());
	}

	/**
	 * lists the Proration factors
	 *
	 * @param prorationFactorFilterVo
	 * @return Collection<ProrationFactorVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author a-2518
	 */
	public Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findProrationFactors");
		Collection<ProrationFactorVO> prorationFactorVos = null;
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDPRORATIONFACTORS);
		int index = 0;
		query.setParameter(++index, prorationFactorFilterVo.getCompanyCode());
		query
				.setParameter(++index, prorationFactorFilterVo
						.getOriginCityCode());
		if (prorationFactorFilterVo.getDestinationCityCode() != null
				&& !BLANK.equals(prorationFactorFilterVo
						.getDestinationCityCode())) {
			query.append("AND TOOCTY = ?");
			query.setParameter(++index, prorationFactorFilterVo
					.getDestinationCityCode());
		}
		if (prorationFactorFilterVo.getProrationFactorSource() != null
				&& !BLANK.equals(prorationFactorFilterVo
						.getProrationFactorSource())) {
			query.append("AND FCTSRC = ?");
			query.setParameter(++index, prorationFactorFilterVo
					.getProrationFactorSource());
		}
		if (prorationFactorFilterVo.getProrationFactorStatus() != null
				&& !BLANK.equals(prorationFactorFilterVo
						.getProrationFactorStatus())) {
			query.append("AND FCTSTA = ?");
			query.setParameter(++index, prorationFactorFilterVo
					.getProrationFactorStatus());
		}
		if (prorationFactorFilterVo.getFromDate() != null) {
			query.append("AND VLDFRM = ?");
			query.setParameter(++index, prorationFactorFilterVo.getFromDate()
					.toSqlDate());
		}
		if (prorationFactorFilterVo.getToDate() != null) {
			query.append("AND VLDTOO = ?");
			query.setParameter(++index, prorationFactorFilterVo.getToDate()
					.toSqlDate());
		}
		query.append("ORDER BY TOOCTY, SEQNUM");
		log.log(Log.FINE, "<-------- QUERY ---------->");
		log.log(Log.FINE, query.toString());
		prorationFactorVos = query.getResultList(new ProrationFactorMapper());
		log.exiting(CLASS_NAME, "findProrationFactors");
		return prorationFactorVos;
	}

	/**
	 * @author a-2524
	 * @param companyCode
	 * @param slaId
	 * @return
	 * @throws SystemException
	 */
	public MailSLAVO findMailSla(String companyCode, String slaId)
			throws SystemException {
		log.entering("CraDefaultsSqlDAO", "finMailSla");
		MailSLAVO mailSLAVo = null;
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDMAILSLA);

		int index = 0;
		query.setParameter(++index, companyCode);

		if (slaId != null && slaId.trim().length() > 0) {
			query.append(" AND MST.SLAIDR = ? ");
			query.setParameter(++index, slaId);
		}

		List<MailSLAVO> colMailSLAVo = query
				.getResultList(new MailSLAMultiMapper());
		for (MailSLAVO firstMailSLAVo : colMailSLAVo) {
			mailSLAVo = firstMailSLAVo;
		}
		log.exiting("CraDefaultsSqlDAO", "finfMailSla");
		return mailSLAVo;
	}

	/**
	 * Finds mail contract details
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return MailContractVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2518
	 */
	public MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "viewMailContract");
		MailContractVO mailContractVo = null;
		Query query = null;
		if (VERSION_LATEST.equals(versionNumber)) {
			query = getQueryManager().createNamedNativeQuery(
					MTK_MRA_DEFAULTS_VIEWMAILCONTRACT);
		} else {
			query = getQueryManager().createNamedNativeQuery(
					MTK_MRA_DEFAULTS_VIEWMAILCONTRACT_HISTORY);
			query.setParameter(3, versionNumber);
		}
		query.setParameter(1, companyCode);
		query.setParameter(2, contractReferenceNumber);
		query.append(" ORDER BY CTRREFNUM, BLGMTXCOD, SERNUM, ORGCOD, DSTCOD");
		List<MailContractVO> mailContractVos = query
				.getResultList(new MailContractMultiMapper());
		for (MailContractVO mailContractVoFromMultiMapper : mailContractVos) {
			mailContractVo = mailContractVoFromMultiMapper;
		}
		log.exiting(CLASS_NAME, "viewMailContract");
		return mailContractVo;
	}

	/**
	 * Finds maximum version number
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2518
	 */
	public int findMaximumVersionNumber(String companyCode,
			String contractReferenceNumber) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findMaximumVersionNumber");
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDMAXVERSIONNUMBER);
		query.setParameter(1, companyCode);
		query.setParameter(2, contractReferenceNumber);
		Mapper<Integer> intMapper = getIntMapper("VERNUM");
		log.exiting(CLASS_NAME, "findMaximumVersionNumber");
		return query.getSingleResult(intMapper).intValue();
	}

	/**
	 * Displays Version numbers for Version LOV
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2518
	 */
	public Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "displayVersionLov");
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDVERSIONNUMBERLOV);
		Collection<MailContractVersionLOVVO> mailContractVersionLovVos = null;
		int index = 0;
		if (query != null) {
			query.setParameter(++index, companyCode);
			if (contractReferenceNumber != null
					&& contractReferenceNumber.trim().length() > 0) {

				String queryString = new StringBuffer(
						" AND MSTHIS.CTRREFNUM LIKE '").append(
						contractReferenceNumber.trim().replace('*', '%'))
						.append("'").toString();
				query.append(queryString);
			}
			if (versionNumber != null && versionNumber.trim().length() > 0) {

				String queryString = new StringBuffer(
						" AND MSTHIS.VERNUM LIKE '").append(
						versionNumber.trim().replace('*', '%')).append("'")
						.toString();
				query.append(queryString);
			}

			query.append(" ORDER BY MSTHIS.CTRREFNUM, MSTHIS.VERNUM");
			mailContractVersionLovVos = query
					.getResultList(new VersionLovMapper());
		}
		log.exiting(CLASS_NAME, "displayVersionLov");
		return mailContractVersionLovVos;
	}

	/**
	 *
	 * @author A-2518 Mapper class for Version number LOV
	 *
	 */
	private static class VersionLovMapper implements
			Mapper<MailContractVersionLOVVO> {

		private Log log = LogFactory.getLogger("MRA_DEFAULTS");

		/**
		 *
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public MailContractVersionLOVVO map(ResultSet rs) throws SQLException {
			log.entering("MRADefaultsSqlDAO", "map");
			MailContractVersionLOVVO nailContractVersionLovVo = new MailContractVersionLOVVO();
			nailContractVersionLovVo.setCompanyCode(rs.getString("CMPCOD"));
			nailContractVersionLovVo.setContractReferenceNumber(rs
					.getString("CTRREFNUM"));
			nailContractVersionLovVo.setVersionNumber(rs.getString("VERNUM"));
			return nailContractVersionLovVo;
		}
	}

	/**
	 * Added by A-2521 for ContractRefNo Lov
	 *
	 * @param contractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO) throws SystemException {

		log.entering(CLASS_NAME, "displayContractDetails");

		Query qry = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_DSPCTRDTLS);

		int index = 0;
		String ctrNo = contractFilterVO.getContractRefNo();
		qry.setParameter(++index, contractFilterVO.getCompanyCode());
		log.log(Log.FINE, "displayContractDetails", ctrNo);
		if (ctrNo != null && !BLANK.equals(ctrNo)) {

			StringBuilder builder = new StringBuilder();
			builder.append("AND CTR.CTRREFNUM LIKE '").append(
					ctrNo.trim().replace('*', '%')).append("'");

			qry.append(builder.toString());
		}

		log.exiting(CLASS_NAME, "displayContractDetails");
		return qry.getResultList(new ContractLovMapper());
	}

	/**
	 * Added by A-2521 for SLAId Lov
	 *
	 * @param slaFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SLADetailsVO> displaySLADetails(SLAFilterVO slaFilterVO)
			throws SystemException {

		log.entering(CLASS_NAME, "displaySLADetails");

		Query qry = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_DSPSLADTLS);

		int index = 0;
		String slaId = slaFilterVO.getSlaID();
		qry.setParameter(++index, slaFilterVO.getCompanyCode());

		if (slaId != null && !BLANK.equals(slaId)) {

			StringBuilder builder = new StringBuilder();
			builder.append("AND SLA.SLAIDR LIKE '").append(
					slaId.replace('*', '%')).append("'");
			qry.append(builder.toString());
		}

		log.exiting(CLASS_NAME, "displaySLADetails");
		return qry.getResultList(new SLALovMapper());
	}

	/**
	 * Finds mail contract details for duplicate contract check
	 *
	 * @param companyCode
	 * @param gpaCode
	 * @param airlineCode
	 * @return Collection<MailContractVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2518
	 */
	public Collection<MailContractVO> viewDuplicateMailContract(
			String companyCode, String gpaCode, String airlineCode)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "viewDuplicateMailContract");
		Query query = null;
		int index = 0;
		query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_VIEWDUPLICATEMAILCONTRACT);
		query.setParameter(++index, companyCode);
		if (gpaCode != null) {
			query.append(" AND MST.GPACOD = ?");
			query.setParameter(++index, gpaCode);
		} else if (airlineCode != null) {
			query.append(" AND MST.ARLCOD = ?");
			query.setParameter(++index, airlineCode);
		}
		query.append(" ORDER BY CTRREFNUM, ORGCOD, DSTCOD, SERNUM");
		List<MailContractVO> mailContractVos = query
				.getResultList(new MailContractMultiMapper());
		log.exiting(CLASS_NAME, "viewDuplicateMailContract");
		return mailContractVos;
	}

	/**
	 * Added by A-1946 for findMailContract
	 *
	 * @param mailContractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO) throws SystemException {

		log.entering(CLASS_NAME, "findMailContracts");
		Query query = null;
		Query mstQuery = null;
		String hisQuery = null;
		// Query hisQuery =null;
		/**
		 * If VERSION value is LATEST PICK from MTKCTRMST
		 */
		if (mailContractFilterVO.getVersionNumber() != null
				&& mailContractFilterVO.getVersionNumber().trim().length() > 0) {
			log.log(Log.FINE, "getVersionNumber()-->not null");
			if (("LATEST").equals(mailContractFilterVO.getVersionNumber())) {
				log.log(Log.FINE, "getVersionNumber()-->LATEST");
				query = getQueryManager().createNamedNativeQuery(
						MTK_MRA_DEFAULTS_FINDMAILCONTRACTS);
			}
			/**
			 * If VERSION value is NOT equal to null PICK from MTKCTRMSTHIS
			 */
			else {
				log.log(Log.FINE, "getVersionNumber()-->VALUE");
				query = getQueryManager().createNamedNativeQuery(
						MTK_MRA_DEFAULTS_FINDMAILCONTRACTS_VERSION_VAL);
			}
		}
		/**
		 * If VERSION value is NULL PICK from MTKCTRMST && MTKCTRMSTHIS
		 */
		else {
			log.log(Log.FINE, "getVersionNumber()-->NULL");
			mstQuery = getQueryManager().createNamedNativeQuery(
					MTK_MRA_DEFAULTS_FINDMAILCONTRACTS);
			hisQuery = getQueryManager().getNamedNativeQueryString(
					MTK_MRA_DEFAULTS_FINDMAILCONTRACTS_VERSION_NO_VAL);
		}
		Collection<MailContractVO> mailContractVOs = null;
		int index = 0;
		if (mstQuery != null) {
			log.log(Log.FINE, "getVersionNumber()-->NULL QUERY hisIndex ");
			mstQuery.setParameter(++index, mailContractFilterVO
					.getCompanyCode());
			hisQuery = new StringBuilder().append(hisQuery).append("'").append(
					mailContractFilterVO.getCompanyCode()).append("' ")
					.toString();

			if (mailContractFilterVO.getContractValidityFrom() != null) {
				mstQuery.append(" AND MST.VLDFRMDAT >= ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getContractValidityFrom().toSqlDate());
				hisQuery = new StringBuilder().append(hisQuery).append(
						"AND MST.VLDFRMDAT >= '").append(
						mailContractFilterVO.getContractValidityFrom()
								.toSqlDate()).append("' ").toString();

			}
			if (mailContractFilterVO.getContractValidityTo() != null) {
				mstQuery.append(" AND MST.VLDTOODAT <= ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getContractValidityTo().toSqlDate());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.VLDTOODAT <=  '").append(
						mailContractFilterVO.getContractValidityTo()
								.toSqlDate()).append("' ").toString();
			}
			if (mailContractFilterVO.getContractReferenceNumber() != null
					&& mailContractFilterVO.getContractReferenceNumber().trim()
							.length() > 0) {
				mstQuery.append(" AND MST.CTRREFNUM = ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getContractReferenceNumber().trim());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.CTRREFNUM =  '").append(
						mailContractFilterVO.getContractReferenceNumber()
								.trim()).append("' ").toString();

			}
			if (mailContractFilterVO.getContractDate() != null) {
				mstQuery.append("AND MST.CREDAT = ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getContractDate().toSqlDate());
				hisQuery = new StringBuilder().append(hisQuery).append(
						"AND MST.CREDAT =  '").append(
						mailContractFilterVO.getContractDate().toSqlDate())
						.append("' ").toString();

			}
			if (mailContractFilterVO.getPaCode() != null
					&& mailContractFilterVO.getPaCode().trim().length() > 0) {
				mstQuery.append(" AND MST.GPACOD = ?");
				mstQuery.setParameter(++index, mailContractFilterVO.getPaCode()
						.trim());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.GPACOD =  '").append(
						mailContractFilterVO.getPaCode().trim()).append("' ")
						.toString();

			}
			if (mailContractFilterVO.getAirlineCode() != null
					&& mailContractFilterVO.getAirlineCode().trim().length() > 0) {
				mstQuery.append(" AND MST.ARLCOD = ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getAirlineCode().trim());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.ARLCOD =  '").append(
						mailContractFilterVO.getAirlineCode().trim()).append(
						"' ").toString();

			}
			if (mailContractFilterVO.getAgreementType() != null
					&& mailContractFilterVO.getAgreementType().trim().length() > 0) {
				mstQuery.append(" AND MST.AGRTYP = ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getAgreementType().trim());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.AGRTYP =  '").append(
						mailContractFilterVO.getAgreementType().trim()).append(
						"' ").toString();

			}
			if (mailContractFilterVO.getAgreementStatus() != null
					&& mailContractFilterVO.getAgreementStatus().trim()
							.length() > 0) {
				mstQuery.append(" AND MST.AGRSTA = ?");
				mstQuery.setParameter(++index, mailContractFilterVO
						.getAgreementStatus().trim());
				hisQuery = new StringBuilder().append(hisQuery).append(
						" AND MST.AGRSTA =  '").append(
						mailContractFilterVO.getAgreementStatus().trim())
						.append("'").toString();

			}
			mstQuery.append(" UNION ALL ");
			log.log(Log.FINE, "The Master Query", mstQuery);
			log.log(Log.FINE, "The hisQuery Query", hisQuery);
			mstQuery.append(hisQuery);
			log.log(Log.FINE, "The COMBINED query", mstQuery);
			mailContractVOs = mstQuery.getResultList(new MailContractMapper());
		} else if (query != null) {
			query.setParameter(++index, mailContractFilterVO.getCompanyCode());
			if (mailContractFilterVO.getContractValidityFrom() != null) {
				query.append(" AND MST.VLDFRMDAT >= ?");
				query.setParameter(++index, mailContractFilterVO
						.getContractValidityFrom().toSqlDate());
			}
			if (mailContractFilterVO.getContractValidityTo() != null) {
				query.append(" AND MST.VLDTOODAT <= ?");
				query.setParameter(++index, mailContractFilterVO
						.getContractValidityTo().toSqlDate());
			}
			if (mailContractFilterVO.getContractReferenceNumber() != null
					&& mailContractFilterVO.getContractReferenceNumber().trim()
							.length() > 0) {
				query.append(" AND MST.CTRREFNUM = ?");
				query.setParameter(++index, mailContractFilterVO
						.getContractReferenceNumber().trim());
			}
			if (mailContractFilterVO.getContractDate() != null) {
				query.append("AND MST.CREDAT = ?");
				query.setParameter(++index, mailContractFilterVO
						.getContractDate().toSqlDate());
			}
			if (mailContractFilterVO.getPaCode() != null
					&& mailContractFilterVO.getPaCode().trim().length() > 0) {
				query.append(" AND MST.GPACOD = ?");
				query.setParameter(++index, mailContractFilterVO.getPaCode()
						.trim());
			}
			if (mailContractFilterVO.getAirlineCode() != null
					&& mailContractFilterVO.getAirlineCode().trim().length() > 0) {
				query.append(" AND MST.ARLCOD = ?");
				query.setParameter(++index, mailContractFilterVO
						.getAirlineCode().trim());
			}
			if (mailContractFilterVO.getAgreementType() != null
					&& mailContractFilterVO.getAgreementType().trim().length() > 0) {
				query.append(" AND MST.AGRTYP = ?");
				query.setParameter(++index, mailContractFilterVO
						.getAgreementType().trim());
			}
			if (mailContractFilterVO.getAgreementStatus() != null
					&& mailContractFilterVO.getAgreementStatus().trim()
							.length() > 0) {
				query.append(" AND MST.AGRSTA = ?");
				query.setParameter(++index, mailContractFilterVO
						.getAgreementStatus().trim());
			}
			if (mailContractFilterVO.getVersionNumber() != null
					&& mailContractFilterVO.getVersionNumber().trim().length() > 0) {
				if (!("LATEST").equals(mailContractFilterVO.getVersionNumber())) {
					query.append(" AND MST.VERNUM = ?");
					query.setParameter(++index, mailContractFilterVO
							.getVersionNumber().trim());
				}
			}
			mailContractVOs = query.getResultList(new MailContractMapper());
		}
		log.exiting(CLASS_NAME, "findMailContracts");
		return mailContractVOs;
	}

	/**
	 * @author A-2524
	 * @param companyCode
	 * @param slaId
	 * @return
	 * @throws SystemException
	 */
	public int findMailSLAMaxSerialNumber(String companyCode, String slaId)
			throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				CRA_DEFAULTS_FINDMAILSLAMAXSERIALNUMBER);
		int index = 0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, slaId);
		log.log(Log.FINE, "query---------->", query);
		Mapper<Integer> intMapper = getIntMapper("MAXSERNUM");
		int seqNum = query.getSingleResult(intMapper).intValue();
		log.log(Log.FINE, "serialNumber---------->", seqNum);
		return seqNum;

	}

	/**
	 * This method validates billing matrix codes
	 *
	 * @author A-2518
	 * @param companyCode
	 * @param billingMatrixCodes
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> validateBillingMatrixCodes(String companyCode,
			Collection<String> billingMatrixCodes) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "validateBillingMatrixCodes");
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDBILLINGMATRIXCODES);
		query.setParameter(1, companyCode);
		if (billingMatrixCodes != null && billingMatrixCodes.size() > 0) {
			query.append(" AND BLGMTXCOD IN (");
			// String billingMatrixCodesKey = null;
			StringBuilder billingMatrixCodesKey = new StringBuilder();
			for (String billingMatrixCode : billingMatrixCodes) {
				billingMatrixCodesKey.append("'").append(billingMatrixCode)
						.append("'").append(",");
			}
			billingMatrixCodesKey = new StringBuilder().replace(0,
					billingMatrixCodesKey.toString().length(),
					billingMatrixCodesKey.substring(0, billingMatrixCodesKey
							.toString().length() - 1));
			query.append(billingMatrixCodesKey.toString());
			query.append(")");
		}
		log.log(Log.INFO, "Query ---->", query);
		Mapper<String> stringMapper = getStringMapper("BLGMTXCOD");
		Collection<String> billingMatrixCodesToReturn = query
				.getResultList(stringMapper);
		log.exiting(CLASS_NAME, "validateBillingMatrixCodes");
		return billingMatrixCodesToReturn;
	}

	/**
	 * This method validates Service Level Activity(SLA) codes
	 *
	 * @author A-2518
	 * @param companyCode
	 * @param slaCodes
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> validateSLACodes(String companyCode,
			Collection<String> slaCodes) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "validateSLACodes");
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDSLACODES);
		query.setParameter(1, companyCode);
		if (slaCodes != null && slaCodes.size() > 0) {
			query.append(" AND SLAIDR IN (");
			// String billingMatrixCodesKey = null;
			StringBuilder slaCodesKey = new StringBuilder();
			for (String slaCode : slaCodes) {
				slaCodesKey.append("'").append(slaCode).append("'").append(",");
			}
			slaCodesKey = new StringBuilder().replace(0, slaCodesKey.toString()
					.length(), slaCodesKey.substring(0, slaCodesKey.toString()
					.length() - 1));
			query.append(slaCodesKey.toString());
			query.append(")");
		}
		log.log(Log.INFO, "Query ---->", query);
		Mapper<String> stringMapper = getStringMapper("SLAIDR");
		Collection<String> slaCodesToReturn = query.getResultList(stringMapper);
		log.exiting(CLASS_NAME, "validateSLACodes");
		return slaCodesToReturn;
	}

	/**
	 * @param companyCode
	 * @param dsn
	 * @param despatch
	 * @param gpaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode, String dsn,
			String despatch, String gpaCode, int pageNumber)
			throws SystemException, PersistenceException {
		Query qry = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_FINDDESPATCHLOV);

		int index = 0;
		qry.setParameter(++index, companyCode);
		// StringBuilder sbul=new StringBuilder(qry);
		if (dsn != null && dsn.trim().length() > 0) {
			String sbul = new StringBuilder(" AND PRO.DSN LIKE '").append(
					dsn.trim().replace('*', '%')).append("'").toString();

			qry.append(sbul);

		}
		if (despatch != null && despatch.trim().length() > 0) {
			String sbul = new StringBuilder(" AND PRO.BLGBASNUM LIKE '")
					.append(despatch.trim().replace('*', '%')).append("'")
					.toString();

			qry.append(sbul);

		}
		if (gpaCode != null && gpaCode.trim().length() > 0) {
			String sbull = new StringBuilder(" AND PRO.POACOD LIKE '").append(
					gpaCode.trim().replace('*', '%')).append("'").toString();

			qry.append(sbull);

		}
		// System.out.println("query"+qry);
		PageableQuery<DespatchLovVO> pgqry = null;
		pgqry = new PageableQuery<DespatchLovVO>(qry, new DespatchLovMapper());

		return pgqry.getPage(pageNumber);
	}

	/**
	 * @author a-2518
	 * @param reportingPeriodFilterVo
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 * CHANGED BY A-3447 FOR AIRNZ CR-175
	 */

	public Collection<ReportingPeriodVO> validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "validateReportingPeriod");
		log
				.log(Log.INFO, "reportingPeriodFilterVo---",
						reportingPeriodFilterVo);
		Query query = getQueryManager().createNamedNativeQuery(
				MTK_MRA_DEFAULTS_VALIDATEREPORTINGPERIOD);
		int index = 0;
		query.setParameter(++index, reportingPeriodFilterVo.getCompanyCode());
		query.setParameter(++index, reportingPeriodFilterVo.getGpaCode());
		query.setParameter(++index, ReportingPeriodVO.BILLING_INFO);
		query.setParameter(++index, reportingPeriodFilterVo.getToDate());
		query.setParameter(++index, ReportingPeriodVO.REPORTING);
		query.setParameter(++index, reportingPeriodFilterVo.getFromDate());
		query.setParameter(++index, reportingPeriodFilterVo.getToDate());
		log.log(Log.INFO, "query***---", query);
		Collection<ReportingPeriodVO> reportingPeriodVos = query.getResultList(new ReportingPeriodMapper(reportingPeriodFilterVo));
		log.exiting(CLASS_NAME, "validateReportingPeriod");
		return reportingPeriodVos;
	}


	public int setImportFlownMailDateRangeDetails(Procedure importProcedure,DocumentBillingDetailsVO documentBillingVO) throws SystemException{
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String userId = logonAttributes.getUserId();
		int index=0;
		LocalDate fromDate=null;
	        LocalDate toDate=null;
	     if(documentBillingVO.getFromDate()!=null && !documentBillingVO.getFromDate().isEmpty() ){
	        	fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	        	fromDate.setDate(documentBillingVO.getFromDate());
	      }
	     
	     if(documentBillingVO.getToDate()!=null && !documentBillingVO.getToDate().isEmpty()){
	        	toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	        	toDate.setDate(documentBillingVO.getToDate());
	      }
	     
			importProcedure.setSensitivity(true);
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
		
		importProcedure.setParameter(++index, documentBillingVO
					.getCompanyCode());
		importProcedure.setParameter(++index, documentBillingVO.getMailSequenceNumber());
			importProcedure.setParameter(++index, 0);
		importProcedure.setParameter(++index, "");
		importProcedure.setParameter(++index, "-1");
			importProcedure.setParameter(++index, 0);
			importProcedure.setParameter(++index, 0);
		if(isOracleDataSource()){
		importProcedure.setParameter(++index, "");
		}
		else
		{
	   importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE); 
		}
		importProcedure.setParameter(++index, "SYNC");
		importProcedure.setParameter(++index,documentBillingVO.getTriggerPoint());
		importProcedure.setParameter(++index,documentBillingVO.getFilterMode()!=null ? documentBillingVO.getFilterMode():"");
		if(isOracleDataSource()){
		importProcedure.setParameter(++index,fromDate!=null?fromDate.toSqlDate():"");
		importProcedure.setParameter(++index,toDate!=null ?toDate.toSqlDate() :"");
		}else if (fromDate!=null && toDate != null){ 
			importProcedure.setParameter(++index,fromDate);
			importProcedure.setParameter(++index,toDate);
		}else{
			importProcedure.setNullParameter(++index,Types.TIMESTAMP_WITH_TIMEZONE);
			importProcedure.setNullParameter(++index,Types.TIMESTAMP_WITH_TIMEZONE);
		}
			importProcedure.setParameter(++index, userId);
			importProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
			importProcedure.setOutParameter(++index, SqlType.STRING);
		return index;

	}
	
	public int setImportFlownMailFlightDetails(FlightValidationVO flightValidationVO,Procedure importProcedure,DocumentBillingDetailsVO documentBillingVO,FlownMailSegmentVO flownMailSegmentVo) throws SystemException{
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		int index=0;
		LocalDate fromDate=null;
        LocalDate toDate=null;
        if(isNotNullAndNotEmpty(documentBillingVO.getFromDate()) 
        		&& isNotNullAndNotEmpty(documentBillingVO.getToDate()) ){
        fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	fromDate.setDate(documentBillingVO.getFromDate());
    	toDate.setDate(documentBillingVO.getToDate());
      }
		String userId = logonAttributes.getUserId();
				int segmentSerialNumber = flownMailSegmentVo
						.getSegmentSerialNumber();
				int flightSequenceNumber = (int) (flightValidationVO
						.getFlightSequenceNumber());
				importProcedure.setSensitivity(true);
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, true);
				importProcedure.setParameter(++index, flightValidationVO
						.getCompanyCode());
		importProcedure.setParameter(++index, documentBillingVO.getMailSequenceNumber()>0?documentBillingVO.getMailSequenceNumber():0);
				importProcedure.setParameter(++index, flightValidationVO
						.getFlightCarrierId());
				importProcedure.setParameter(++index, flightValidationVO
						.getCarrierCode());
				importProcedure.setParameter(++index, flightValidationVO
						.getFlightNumber());
				importProcedure.setParameter(++index, flightSequenceNumber);
				importProcedure.setParameter(++index, segmentSerialNumber);
				importProcedure.setParameter(++index, flightValidationVO.getFlightDate());
				if(flownMailSegmentVo.getTriggerPoint()!=null 
						&& "P".equalsIgnoreCase(flownMailSegmentVo.getTriggerPoint())){
					importProcedure.setParameter(++index, "SYNC");
				}else{
					importProcedure.setParameter(++index, "ASYNC");
				}
				//Modified by A-7794 as part of ICRD-232299
				if(flownMailSegmentVo.getTriggerPoint()!=null && ("TRA".equalsIgnoreCase(flownMailSegmentVo.getTriggerPoint()) || flownMailSegmentVo.getTriggerPoint().equals("TRA_MAL") || flownMailSegmentVo.getTriggerPoint().equals("TRA_CON"))){
					importProcedure.setParameter(++index, "TRA");
				}else{
					importProcedure.setParameter(++index,flownMailSegmentVo.getTriggerPoint());
				}
		importProcedure.setParameter(++index,"");
		index = setDateParameter(index,importProcedure,fromDate,toDate);
				importProcedure.setParameter(++index, userId);
				importProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
				importProcedure.setOutParameter(++index, SqlType.STRING);
		return index;
	}
	private int setDateParameter(int index,Procedure importProcedure,LocalDate fromDate,LocalDate toDate){
	if(fromDate!=null)
	{
  		importProcedure.setParameter(++index,fromDate.toSqlDate()); 
	}else{
		if(isOracleDataSource()){
			 importProcedure.setParameter(++index,"");
		}else{
			importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE);
		}
	}
  	 
	if(toDate!=null)
	{
  		  importProcedure.setParameter(++index,toDate.toSqlDate()); 
	}else{
		if(isOracleDataSource()){
			 importProcedure.setParameter(++index,"");
		}else{
			importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE);
		}
	}
		
		
		return index;
	}
    private boolean isNotNullAndNotEmpty(String s) {
        return !Objects.isNull(s)&& !s.trim().isEmpty();
    }
	/**
	 * @param flightValidationVO
	 * @param flownMailSegmentVOs
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String importFlownMails(FlightValidationVO flightValidationVO,
			Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "importFlownMails");
		Boolean flag = true;
		Collection<String> outParameters = new ArrayList<>();
		String returnString = null;

		if(flownMailSegmentVOs!=null&&!flownMailSegmentVOs.isEmpty()) {
			for (FlownMailSegmentVO flownMailSegmentVo : flownMailSegmentVOs) {
				int index=0;
				Procedure importProcedure = getQueryManager().createNamedNativeProcedure(
								MRA_DEFAULTS_IMPORT_FLOWN_MAILS);
				index = setImportFlownMailFlightDetails(flightValidationVO, importProcedure, documentBillingVO, flownMailSegmentVo);
				importProcedure.execute();
				String outParameter = (String) importProcedure.getParameter(index);
				outParameters.add(outParameter);
			}
		}else if (documentBillingVO!=null){
			int index = 0;
			Procedure importProcedure = getQueryManager().createNamedNativeProcedure(
							MRA_DEFAULTS_IMPORT_FLOWN_MAILS);
			index = setImportFlownMailDateRangeDetails(importProcedure,documentBillingVO);
			importProcedure.execute();
			String outParameter = (String) importProcedure.getParameter(index);
			outParameters.add(outParameter);
			
		}
			for (String parameter : outParameters) {
				if (!"OK".equals(parameter)) {
					flag = false;
					break;
				}
			}

			if (flag) {
				returnString = "OK";
			} else {
				returnString = "NOTOK";
			}
		return returnString;
	}

	/**
	 * Returns the InvoicEnquiryDetails based on the filter criteria
	 *
	 * @author a-2270
	 * @param companyCode
	 * @param invoiceNumber
	 * @param pageNumber
	 * @return Page<MailInvoicEnquiryDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo)
			throws SystemException, PersistenceException {
        String queryString = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDINVOICENQUIRYDETAILS);
        StringBuilder rankQuery = new StringBuilder().append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
		rankQuery.append(queryString);
		PageableNativeQuery<MailInvoicEnquiryDetailsVO> pgNativeQuery = 
			new PageableNativeQuery<MailInvoicEnquiryDetailsVO>(invoiceEnquiryFilterVo.getTotalRecordCount(),
					rankQuery.toString(),new InvoicEnquiryMapper());
		int index = 0;
		pgNativeQuery.setParameter(++index, invoiceEnquiryFilterVo.getCompanyCode());
		pgNativeQuery.setParameter(++index, invoiceEnquiryFilterVo.getInvoiceKey());
		pgNativeQuery.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
		log.log(Log.FINEST, "query", pgNativeQuery);
		return pgNativeQuery.getPage(invoiceEnquiryFilterVo.getDisplayPage());
	}

	/**
	 * @param filterVO
	 * @return Page<MailInvoicClaimsEnquiryVO>
	 * @throws SystemException
	 */
	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails(
			MailInvoicClaimsFilterVO filterVO) throws SystemException {
			
		//Query qry = getQueryManager().createNamedNativeQuery(
				//MRA_DEFAULTS_FINDINVOICCLAIMSDETAILS);	
				
		//Added By A-5183 For ICRD-21098 Starts			
		
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
		String query = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDINVOICCLAIMSDETAILS);
		String baseQry = rankQuery.append(query).toString();			
		PageableNativeQuery<MailInvoicClaimsEnquiryVO> pgqry = new PageableNativeQuery<MailInvoicClaimsEnquiryVO>(filterVO.getTotalRecordsCount(),baseQry,new InvoicClaimsEnquiryMapper());
		
		//Added By A-5183 For ICRD-21098 Ends
		
		int index = 0;
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getPoaCode());
		pgqry.setParameter(++index, filterVO.getFromDate().toSqlDate());
		pgqry.setParameter(++index, filterVO.getToDate().toSqlDate());
		if (filterVO.getClaimStatus() != null && filterVO.getClaimStatus().length() > 0) {
			pgqry.append(" AND DTL.CLMSTA = ? ");
			pgqry.setParameter(++index,filterVO.getClaimStatus());

		}
		if (filterVO.getClaimType() != null && filterVO.getClaimType().length() > 0) {
			if("RVX".equals(filterVO.getClaimType())){
				pgqry.append(" AND DTL.CLMCOD IN ( 'RVLD' , 'RVHD' ) ");

			}else{
				pgqry.append(" AND DTL.CLMCOD = ? ");
				pgqry.setParameter(++index,filterVO.getClaimType());
			}

		}
		      
		pgqry.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
	log
			.log(
					Log.FINEST,
					"query for findInvoicClaimsEnquiryDetails >>>>>>>>>>@@@@@@@@@@@@@@@",
					pgqry);
		return pgqry.getPage(filterVO.getPageNumber());

	}

	/**
	 * @param filterVO
	 * @throws SystemException
	 * @return Collection<MailDOTRateVO>
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(
			MailDOTRateFilterVO filterVO) throws SystemException {
		log.entering(CLASS_NAME, "findDOTRateDetails");
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDDOTRATEDETAILS);

		int index = 0;

		qry.setParameter(++index, filterVO.getCompanyCode());

		if (filterVO.getSectorOriginCode() != null
				&& filterVO.getSectorOriginCode().trim().length() > 0) {
			qry.append("AND DOT.SEGORGCOD = ?");
			qry.setParameter(++index, filterVO.getSectorOriginCode());
		}
		if (filterVO.getSectorDestinationCode() != null
				&& filterVO.getSectorDestinationCode().trim().length() > 0) {
			qry.append("AND DOT.SEGDSTCOD = ?");
			qry.setParameter(++index, filterVO.getSectorDestinationCode());
		}
		if (filterVO.getGcm() != 0) {
			qry.append("AND DOT.GCM = ?");
			qry.setParameter(++index, filterVO.getGcm());
		}
		if (filterVO.getRateCode() != null
				&& filterVO.getRateCode().trim().length() > 0) {
			qry.append("AND DOT.RATCOD = ?");
			qry.setParameter(++index, filterVO.getRateCode());
		}
		log.exiting(CLASS_NAME, "findDOTRateDetails");
		return qry.getResultList(new MailDOTRateMapper());

	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String importToReconcile(String companyCode) throws SystemException,
			PersistenceException {
		Procedure importProcedure = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_IMPORT_RECONCILE);
		importProcedure.setSensitivity(true);
		int index = 0;
		importProcedure.setParameter(++index, companyCode);
		importProcedure.setOutParameter(++index, SqlType.STRING);
		importProcedure.execute();
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) importProcedure.getParameter(2);
		log.log(Log.FINE, "outParameter is ", outParameter);
		return outParameter;
	}

	/**
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String reconcileProcess(String companyCode) throws SystemException,
			PersistenceException {
		Procedure importProcedure = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_RECONCILE);
		importProcedure.setSensitivity(true);
		int index = 0;
		importProcedure.setParameter(++index, companyCode);
		importProcedure.setOutParameter(++index, SqlType.STRING);
		importProcedure.execute();
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) importProcedure.getParameter(2);
		log.log(Log.FINE, "outParameter is ", outParameter);
		return outParameter;
	}

	/**
	 * This method generates INVOIC Claim file
	 *
	 * @author A-2518
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<InvoicMessageVO> generateInvoicClaimFile(
			String companyCode) throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "generateInvoicClaimFile");
		Collection<InvoicMessageVO> invoicMessageVos = null;
		Query query = null;
		int index = 0;
		query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_GENERATECLAIMFILE);
		query.setParameter(++index, companyCode);
		log.log(Log.FINE, "Query -->", query.toString());
		invoicMessageVos = query.getResultList(new InvoicClaimMultiMapper());
		log.exiting(CLASS_NAME, "generateInvoicClaimFile");
		return invoicMessageVos;
	}

	/**
	 * @param companyCode
	 * @param invoicKey
	 * @param poaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey, String poaCode, int pageNumber)
			throws SystemException, PersistenceException {
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<InvoicKeyLovVO> pgqry = null;
		String basequery = null;
   		basequery = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_FINDINVOICKEYLOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(basequery);
		// Added by A-5280 for ICRD-32647 ends
		pgqry = new PageableNativeQuery<InvoicKeyLovVO>(0,rankQuery.toString(), new InvoicKeyLovMapper());

		int index = 0;
		pgqry.setParameter(++index, companyCode);

		if (invoicKey != null && invoicKey.trim().length() > 0) {
			String sbul = new StringBuilder(" AND MST.INVKEY LIKE '").append(
					invoicKey.trim().replace('*', '%')).append('%').append("'")
					.toString();
			pgqry.append(sbul);

		}
		if (poaCode != null && poaCode.trim().length() > 0) {
			String sbul = new StringBuilder(" AND MST.POACOD LIKE '").append(
					poaCode.trim().replace('*', '%')).append('%').append("'")
					.toString();
			pgqry.append(sbul);

		}
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		return pgqry.getPage(pageNumber);
	}
	/**
	 * @param companyCode
	 * @param sectororigin
	 * @param sectorDest
	 * @param payType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findDuplicateRecticles(String companyCode,
			String sectororigin,String sectorDest,String payType,String recpIdr) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findDuplicateRecticles");
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDDUPLICATE_RECEP);
		int index=0;

		query.setParameter(++index, companyCode);
		if(sectororigin!=null && sectororigin.trim().length()>0){
			query.append("AND PAC.SECORG= ? ");
			query.setParameter(++index,sectororigin.trim());
		}
		if(sectorDest!=null && sectorDest.trim().length()>0){
			query.append("AND PAC.SECDST= ? ");
			query.setParameter(++index,sectorDest.trim());
		}
		if(payType!=null && payType.trim().length()>0){
			query.append("AND MST.PAYTYP = ? ");
			query.setParameter(++index,payType.trim());
		}
		if(recpIdr!=null && recpIdr.trim().length()>0){
			query.append("AND PAC.RCPIDR= ? ");
			query.setParameter(++index,recpIdr.trim());
		}
		Mapper<String> strMapper = getStringMapper("INVKEY");
		log.exiting(CLASS_NAME, "findDuplicateRecticles");
		return query.getSingleResult(strMapper);
	}
	/**
	 * @param dupVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<String> findReconcileSectorIdrs(MailInvoicDupRecepVO dupVO)
	throws SystemException,	PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDRECONSECTORIDR);
		int index=0;
		query.setParameter(++index, dupVO.getCompanyCode());
		if(dupVO.getRecepticleIdentifier()!=null && dupVO.getRecepticleIdentifier().trim().length()>0){
			query.append("AND RCPIDR= ? ");
			query.setParameter(++index,dupVO.getRecepticleIdentifier().trim());
		}
		if(dupVO.getSectorOrigin()!=null && dupVO.getSectorOrigin().trim().length()>0){
			query.append("AND SECORG= ?");
			query.setParameter(++index,dupVO.getSectorOrigin().trim());
		}
		if(dupVO.getSectorDestination()!=null && dupVO.getSectorDestination().trim().length()>0){
			query.append("AND SECDST= ? ");
			query.setParameter(++index,dupVO.getSectorDestination().trim());
		}
		Mapper<String> strMapper=getStringMapper("SECIDR");
		return query.getResultList(strMapper);
	}
	/**
	    * Method to find the billingperiod of the gpacode/billing periods of all
		* the gpacodes of the country specified
		* Method is called on clickig the suggestbutton
		* @author A-3108
		* @param generateInvoiceFilterVO
		* @return Collection<String>
		* @throws SystemException
		* @throws PersistenceException
	    */

		public Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO)
		throws SystemException,PersistenceException {
			log.log(Log.INFO,"into sqldao");
			log.log(Log.INFO, "gpacode is", generateInvoiceFilterVO.getGpaCode());
			int index=0;

			Query query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDBILLINPERIODS);
			generateInvoiceFilterVO.setCurrentDate(new LocalDate(LocalDate.NO_STATION,	Location.NONE, false));
			 Calendar currDate = new LocalDate(LocalDate.NO_STATION,	Location.NONE, false);


			 log.log(Log.INFO, "currDate.get(Calendar.MONTH)+1--->>>>", currDate.get(Calendar.MONTH));
			log.log(Log.INFO, "currDate.get(Calendar.MONTH)--->>>>", currDate.get(Calendar.MONTH));
			log.log(Log.INFO, "currDate--->>>>", currDate);
			query.setParameter(++index,currDate.get(Calendar.MONTH)+1);
			 query.setParameter(++index,generateInvoiceFilterVO.getCurrentDate());
			 query.setParameter(++index,currDate.get(Calendar.MONTH)+1);
			 query.setParameter(++index,currDate.get(Calendar.MONTH)+1);
			 query.setParameter(++index,generateInvoiceFilterVO.getCurrentDate());

			 query.setParameter(++index,generateInvoiceFilterVO.getGpaCode());
			 query.setParameter(++index,currDate.get(Calendar.MONTH));
			 query.setParameter(++index,generateInvoiceFilterVO.getCurrentDate());
			 query.setParameter(++index,currDate.get(Calendar.MONTH));
			 query.setParameter(++index,currDate.get(Calendar.MONTH));
			 query.setParameter(++index,generateInvoiceFilterVO.getCurrentDate());
			 query.setParameter(++index,generateInvoiceFilterVO.getGpaCode());

			return query.getResultList(new GenerateInvoiceMapper());
		}
		/**
	     * generateInvoice
	     * @author A-3108
	     * @param generateInvoiceFilterVO
	     * @return String
	     * @throws SystemException
	     */
	    public String[] generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
				throws SystemException {
			log.entering("CLASS_NAME", "generateInvoice");
			
			String outParameters[] = new String[3];

			// commented by A-3434 for CR ICRD-114599 on 29SEP2015.Validation added in client side for TK
			
			//boolean isCorrectPeriod = validateGpaBillingPeriod(generateInvoiceFilterVO);

			//if(!isCorrectPeriod){
				//outParameters[2] = "invalid billing period";
			//}
			//else{
			Procedure burstProcedure = getQueryManager()
					.createNamedNativeProcedure(GENERATE_INVOICE);
			int index = 0;

			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
			log.log(Log.FINE, "Current Date:in defaultsDao ", dateString);
			log.log(Log.FINE, "generateInvoiceFilterVO ",
					generateInvoiceFilterVO);
			// currentDate.setDate(dateString, "dd-MMM-yyyy HH:mm");
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			burstProcedure.setParameter(++index,generateInvoiceFilterVO.getCompanyCode());
			burstProcedure.setParameter(++index,generateInvoiceFilterVO.getGpaCode());
			burstProcedure.setParameter(++index,generateInvoiceFilterVO.getCountryCode());
			if(generateInvoiceFilterVO.getBillingPeriodFrom()!=null &&generateInvoiceFilterVO.getBillingPeriodTo()!=null){
			burstProcedure.setParameter(++index,generateInvoiceFilterVO.getBillingPeriodFrom().toSqlTimeStamp());
			burstProcedure.setParameter(++index,generateInvoiceFilterVO.getBillingPeriodTo().toSqlTimeStamp());
			}
			burstProcedure.setParameter(++index, generateInvoiceFilterVO.getInvoiceType());    
			burstProcedure.setParameter(++index, generateInvoiceFilterVO.isAddNew() == false ? "N" : "Y");
			burstProcedure.setParameter(++index,logonAttributes.getUserId());
			burstProcedure.setParameter(++index,logonAttributes.getAirportCode());
			burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			
			
			burstProcedure.execute();
			log.log(Log.FINE, "executed Procedure");
			// outParameter = (String) burstProcedure.getParameter(index);
			 
			 outParameters[0] = (String)burstProcedure.getParameter(index--);
			 outParameters[1] = (String)burstProcedure.getParameter(index--);
			 outParameters[2] = (String)burstProcedure.getParameter(index--);
			log.log(Log.FINE, "outParameter is ", outParameters);
			log.exiting("CLASS_NAME", "generateInvoice");
			//}
			return outParameters;
			}
	    
	    
	    /**
	     * Added by A-3434
		 * @param generateInvoiceFilterVO
		 * 
		 * @return boolean
		 *
		 */


	    public boolean validateGpaBillingPeriod(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	    throws SystemException{

			log.entering("GenerateInvoiceCommand", "validateGpaBillingPeriod");

			boolean isCorrect = true;
			int index=0;
			LocalDate billingPeriodFrom = generateInvoiceFilterVO.getBillingPeriodFrom();
			LocalDate billingPeriodTo = generateInvoiceFilterVO.getBillingPeriodTo();
			String billingPeriodFromStr = billingPeriodFrom.toDisplayDateOnlyFormat();
			String billingPeriodToStr = billingPeriodTo.toDisplayDateOnlyFormat();
			Query query=getQueryManager().createNamedNativeQuery(FIND_GPABILLING_PERIODS);

			query.setParameter(++index,generateInvoiceFilterVO.getCompanyCode());
	    	query.setParameter(++index,billingPeriodFrom);
	    	query.setParameter(++index,billingPeriodTo);
	    	query.setParameter(++index,generateInvoiceFilterVO.getBillingFrequency());

	    	String from=billingPeriodFromStr.substring(0,2);
	    	query.setParameter(++index,"D".concat(billingPeriodFromStr.substring(0,2)));
	    	LocalDate nextday = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true).setDate( billingPeriodToStr );
	    	nextday.addDays(1);
	    	String nextdayStr = nextday.toDisplayDateOnlyFormat();
	    	if(("01").equals(nextdayStr.substring(0,2))){
	    		query.setParameter(++index,"EOM");
	    	}
	    	else{
	    		query.setParameter(++index,"D".concat(billingPeriodToStr.substring(0,2)));
	    	}
	    	log.log(Log.INFO, "\n\n ***Final query--->>>>", query);
			Mapper<Integer> integerMapper = getIntMapper("VAL");

	    	int count = query.getSingleResult(integerMapper).intValue();

	    	if(count == 0){

	    		isCorrect = false;
	    	}

	    	log.exiting(CLASS_NAME,"validateGpaBillingPeriod");


			return  isCorrect;
	    }

		public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException{
			Query query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDDESPATCHDTLS);
			/**
			 * GPA code commented,from popup the GPA obtained will be PA mentioned against the OOE
			 * Mapping the GPA code with MTKMRABLGDTL will not be correct since GPA CODE in the table
			 * will the PA to which billing need to be done.
			 * Also this is wrong in cases airline is billed hence removing GPA code
			 * Done by A-4809
			 */
			LocalDate date=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   	       
			date.setDate(popUpVO.getDsnDate()) ;
			int index=0;
			query.setParameter(++index, popUpVO.getCompanyCode());
			query.setParameter(++index, popUpVO.getBlgBasis());
			query.setParameter(++index, Integer.parseInt(date.toStringFormat(DATE).substring(0, 8)));
			//Commented by A-7794 as part of MRA revamp
			//query.setParameter(++index, popUpVO.getCsgdocnum());
			//query.setParameter(++index, popUpVO.getCsgseqnum());
			//query.setParameter(++index, popUpVO.getGpaCode());
			return query.getSingleResult(new DespatchEnqMapper());

		}
		/**
		 *
		 */
		public  Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException{
			Query query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDGPABLGDTLSINBLGDTL);
			Query c66Query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDGPABLGDTLSINC66DTL);
			Query mcaQuery = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDGPABLGDTLSINMCA);
			int index=0;
			query.setParameter(++index, popUpVO.getCompanyCode());
			query.setParameter(++index, popUpVO.getBlgBasis());
			query.setParameter(++index, popUpVO.getDsnDate());
			//MOdified by A-7794 as part of ICRD-252665
			//query.setParameter(++index, popUpVO.getCsgdocnum());
			//query.setParameter(++index, popUpVO.getCsgseqnum());
			//GPA code commented by A-4809
			//query.setParameter(++index, popUpVO.getGpaCode());
			StringBuilder newString=new StringBuilder();
			query.append(" Union All ");
			mcaQuery.append(" Union All ");
			query.append(mcaQuery.toString()); 
			query.setParameter(++index, popUpVO.getCompanyCode());
			query.setParameter(++index, popUpVO.getBlgBasis());
			query.setParameter(++index, popUpVO.getDsnDate());
			//MOdified by A-7794 as part of ICRD-252665
			//query.setParameter(++index, popUpVO.getCsgdocnum());
			//query.setParameter(++index, popUpVO.getCsgseqnum()); 
			newString.append(" '" ); 
			newString.append(popUpVO.getCompanyCode()); 
			newString.append("' " ); 
			newString.append("AND MST.MALIDR ='" ); 
			newString.append(popUpVO.getBlgBasis()); 
			newString.append("' " ); 
			c66Query.append(newString.toString());
			query.append(c66Query.toString());
			return query.getResultList(new GPABillingDtlsMapper());
	
	}
		/**
		 *
		 * @param companyCode
		 * @param dsnNum
		 * @param dsnDate
		 * @param pageNumber
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
				String dsnNum,String dsnDate,int pageNumber) throws SystemException,
				PersistenceException {
			StringBuilder rankQuery = new StringBuilder().append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
			
			
			String pageQry = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_FINDDSNSELECTLOV);
			rankQuery.append(pageQry);
			PageableNativeQuery<DSNPopUpVO> qry = new PageableNativeQuery<DSNPopUpVO>(0,
					rankQuery.toString(),new DSNSelectLovMapper());
			int index = 0;
			qry.setParameter(++index, companyCode);

			if (dsnNum != null && dsnNum.trim().length() > 0) {
				if(dsnNum.contains("'")){
					dsnNum=dsnNum.replace("'", "''");
				}
				if(dsnNum.length()>4)
				{
					
					String sbul = new StringBuilder("AND MST.MALIDR LIKE '").append(
							dsnNum.trim().replace('*', '%')).append('%').append("'")
							.toString();
					qry.append(sbul);
				}
				else  
				{
				String sbul = new StringBuilder("AND MST.DSN LIKE '").append(
						dsnNum.trim().replace('*', '%')).append('%').append("'")
						.toString();
				qry.append(sbul);
				}
			}
			if(isOracleDataSource()) {
			if (dsnDate != null && dsnDate.trim().length() > 0) {
				qry.append(" AND TRUNC(MST.RCVDAT) =?");
				qry.setParameter(++index, dsnDate);
			}
			}
			else {
				if (dsnDate != null && dsnDate.trim().length() > 0) {
					qry.append(" AND TRUNC(MST.RCVDAT) =? :: TIMESTAMP");
					qry.setParameter(++index, dsnDate);
				}
			}
			log.log(Log.INFO, "query", qry);
			
			qry.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
			//PageableQuery<DSNPopUpVO> pgqry = null;
			//pgqry = new PageableQuery<DSNPopUpVO>(qry, new DSNSelectLovMapper());

			return qry.getPage(pageNumber);
		}
		/**
		 * @author a-3108
		* @return Collection<RateAuditVO>
		 * @throws SystemException
		*/
		public Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException,PersistenceException{
			Query qry = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDRATEAUDITDETAILS);
			int pageNumber=rateAuditFilterVO.getPageNumber();
			int index = 0;
			if(rateAuditFilterVO.getCarrierId()>0){
				qry.append("AND DTL.FLTCARIDR=?");
				qry.setParameter(++index,rateAuditFilterVO.getCarrierId());
			}
			StringBuilder sbd=new StringBuilder();
			sbd.append("where MST.CMPCOD ='");
			sbd.append(rateAuditFilterVO.getCompanyCode());
			sbd.append("'");
			
			qry.append(sbd.toString());
			
			if(rateAuditFilterVO.getDsn()!=null){
				qry.append("AND MST.DSN=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsn());

			}
			if(rateAuditFilterVO.getDsnDate()!=null){
				qry.append("AND MST.RCVDAT=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsnDate());
			}
			if(rateAuditFilterVO.getGpaCode()!=null){
				qry.append("AND MST.POACOD=?");
				qry.setParameter(++index,rateAuditFilterVO.getGpaCode());
			}
			if(rateAuditFilterVO.getDsnStatus()!=null){
				qry.append("AND MST.RATSTA=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsnStatus());
			}
			
			if(rateAuditFilterVO.getFlightNumber()!=null){
				qry.append("AND DTL.FLTNUM=?");
				qry.setParameter(++index,rateAuditFilterVO.getFlightNumber());
			}
			if(rateAuditFilterVO.getFlightDate()!=null){
				qry.append("AND TRUNC(DTL.FLTDAT)=?");
				qry.setParameter(++index,rateAuditFilterVO.getFlightDate());
			}
			if(rateAuditFilterVO.getSubClass()!=null){
				qry.append("AND MST.MALSUBCLS=?");
				qry.setParameter(++index,rateAuditFilterVO.getSubClass());
			}
			if(rateAuditFilterVO.getFromDate()!=null){
				qry.append("AND MST.RCVDAT>=?");
				qry.setParameter(++index,rateAuditFilterVO.getFromDate());
			}
			if(rateAuditFilterVO.getToDate()!=null){
				qry.append("AND MST.RCVDAT<=?");
				qry.setParameter(++index,rateAuditFilterVO.getToDate());
			}

			PageableQuery<RateAuditVO> pgqry = null;
			pgqry = new PageableQuery<RateAuditVO>(qry, new RateAuditMapper());

			log.log(Log.FINE, "rateAuditVOs  ", pgqry.getPage(pageNumber));
			return pgqry.getPage(pageNumber);


		}
		
		/**
		 * @author a-3434
		* @return Collection<RateAuditVO>
		 * @throws SystemException
		*/
		public Collection<RateAuditVO> findRateAuditDetailsCol(RateAuditFilterVO rateAuditFilterVO) throws SystemException,PersistenceException{
			Query qry = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDRATEAUDITDETAILS);
			
			int index = 0;
			if(rateAuditFilterVO.getCarrierId()>0){
				qry.append("AND DTL.FLTCARIDR=?");
				qry.setParameter(++index,rateAuditFilterVO.getCarrierId());
			}
			StringBuilder sbd=new StringBuilder();
			sbd.append("where MST.CMPCOD ='");
			sbd.append(rateAuditFilterVO.getCompanyCode());
			sbd.append("'");
			
			qry.append(sbd.toString());
			
			if(rateAuditFilterVO.getDsn()!=null){
				qry.append("AND MST.DSN=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsn());

			}
			if(rateAuditFilterVO.getDsnDate()!=null){
				qry.append("AND MST.RCVDAT=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsnDate());
			}
			if(rateAuditFilterVO.getGpaCode()!=null){
				qry.append("AND MST.POACOD=?");
				qry.setParameter(++index,rateAuditFilterVO.getGpaCode());
			}
			if(rateAuditFilterVO.getDsnStatus()!=null){
				qry.append("AND MST.RATSTA=?");
				qry.setParameter(++index,rateAuditFilterVO.getDsnStatus());
			}
			
			if(rateAuditFilterVO.getFlightNumber()!=null){
				qry.append("AND DTL.FLTNUM=?");
				qry.setParameter(++index,rateAuditFilterVO.getFlightNumber());
			}
			if(rateAuditFilterVO.getFlightDate()!=null){
				qry.append("AND TRUNC(DTL.FLTDAT)=?");
				qry.setParameter(++index,rateAuditFilterVO.getFlightDate());
			}
			if(rateAuditFilterVO.getSubClass()!=null){
				qry.append("AND MST.MALSUBCLS=?");
				qry.setParameter(++index,rateAuditFilterVO.getSubClass());
			}
			if(rateAuditFilterVO.getFromDate()!=null){
				qry.append("AND MST.RCVDAT>=?");
				qry.setParameter(++index,rateAuditFilterVO.getFromDate());
			}
			if(rateAuditFilterVO.getToDate()!=null){
				qry.append("AND MST.RCVDAT<=?");
				qry.setParameter(++index,rateAuditFilterVO.getToDate());
			}

			return qry.getResultList(new RateAuditMapper());


		}

		/**
		 * @author A-2391
		 */
		public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws SystemException,PersistenceException{
			Query query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDLISTRATEAUDITDETAILS);
			int index=0;
			query.setParameter(++index, rateAuditFilterVO.getCompanyCode());
			query.setParameter(++index, rateAuditFilterVO.getBillingBasis());
			//Removed by A-7794 as part of MRA revamp
			//query.setParameter(++index, rateAuditFilterVO.getCsgDocNum());
			//query.setParameter(++index, rateAuditFilterVO.getCsgSeqNum());
			//query.setParameter(++index, rateAuditFilterVO.getGpaCode());
			query.setParameter(++index, rateAuditFilterVO.getDsnDate());
			return query.getResultList(new ListRateAuditDtlsMultiMapper()).get(0);


		}

		/**
		 * @author A-3227
		 * @param maintainCCAFilterVO
		 * @return Collection<CCAdetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public Collection<CCAdetailsVO> findCCAdetails(MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException, PersistenceException {
			Query query =null;
			if((maintainCCAFilterVO.getCcaReferenceNumber()!=null &&
					maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0) &&
					(maintainCCAFilterVO.getUsrCCANumFlg()!=null &&
							"N".equals(maintainCCAFilterVO.getUsrCCANumFlg()))){
				/*
				 * CCA number exist in filter which is not manually created
				 */
				query = getQueryManager().createNamedNativeQuery(
						MRA_DEFAULTS_FINDCCAREFDTLS);
				int index=0;
				query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());

				if(maintainCCAFilterVO.getCcaReferenceNumber()!=null
						&& maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0){
					query.append(" AND (CCADTL.MCAREFNUM = ? OR CCADTL.MCAREFNUM = ?)");
					query.setParameter(++index, maintainCCAFilterVO.getCcaReferenceNumber());
					query.setParameter(++index, maintainCCAFilterVO.getCcaReferenceNumber());
				}
			if(maintainCCAFilterVO.getBillingBasis()!=null
					&& maintainCCAFilterVO.getBillingBasis().trim().length()>0){
					query.append(" AND BLGMST.MALIDR = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getBillingBasis());
				}
				if(maintainCCAFilterVO.getConsignmentDocNum()!=null
						&& maintainCCAFilterVO.getConsignmentDocNum().trim().length()>0){
					query.append(" AND BLGMST.CSGDOCNUM = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum());
				}
				if(maintainCCAFilterVO.getConsignmentSeqNum()!= 0){
					query.append(" AND BLGMST.CSGSEQNUM = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());
				}
				if(maintainCCAFilterVO.getPOACode()!=null
						&& maintainCCAFilterVO.getPOACode().trim().length()>0){
					query.append(" AND BLGMST.POACOD = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getPOACode());
				}
				if(maintainCCAFilterVO.getGpaCode()!=null
						&& maintainCCAFilterVO.getGpaCode().trim().length()>0){//Added by a-7871 for ICRD-293403
					query.append(" AND BLGDTL.UPDBILTOOPOA = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getGpaCode());
				}
				if(maintainCCAFilterVO.getDsnDate()!=null){   
					//query.append(" AND TRUNC(CCADTL.DSPDATE) = ? ");
					query.append(" AND TRUNC(BLGMST.RCVDAT) <= ? ");  
					query.setParameter(++index, maintainCCAFilterVO.getDsnDate());  
				}   
				if(maintainCCAFilterVO.getPartyCode()!=null
						&& maintainCCAFilterVO.getPartyCode().trim().length()>0){
					/*
					 * IssuingParty From MaintainCCAFilterVO
					 * 1. "G" - GPA
					 * 2. "A" - Own Airline
					 * 3. "OAL" - Other AirLine
					 
					if(maintainCCAFilterVO.getIssuingParty()!=null
							&& maintainCCAFilterVO.getIssuingParty().trim().length()>0){
						query.append(" AND CCADTL.ARLGPAIND = ? ");
						if("G".equals(maintainCCAFilterVO.getIssuingParty())){
							query.setParameter(++index, "G");
						}else{
							query.setParameter(++index, "A");
						}
					}*/
					if(maintainCCAFilterVO.getAirlineGpaIndicator()!=null
							&& maintainCCAFilterVO.getAirlineGpaIndicator().trim().length()>0){
						query.append(" AND CCADTL.GPACHGIND = ? ");
						query.setParameter(++index, maintainCCAFilterVO.getAirlineGpaIndicator());
					}
					//Commented by A-7794 as part of MRA revamp
					//query.append(" AND CCADTL.ISSPARTY = ? ");
					//query.setParameter(++index, maintainCCAFilterVO.getPartyCode());
				}
				//query.append(" AND BLGDTL.PAYFLG = 'R' ");
			}else{
				query = getQueryManager().createNamedNativeQuery(
						MRA_DEFAULTS_FINDCCADTLS);

				int index=0;
				query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());
				//Modified as part of ICRD-341973
				if(maintainCCAFilterVO.getDsnNumber()!=null
						&& maintainCCAFilterVO.getDsnNumber().trim().length()>0 && maintainCCAFilterVO.getDsnNumber().trim().length()!=12){
					
				query.append(" AND BLGMST.DSN= ? ");
						if(maintainCCAFilterVO.getDsnNumber().trim().length()>4)
						{
						query.setParameter(++index, maintainCCAFilterVO.getDsnNumber().substring(16, 20));	
						}
						else
						{
					query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
						}
					
				}    
				if(maintainCCAFilterVO.getBillingBasis()!=null
						&& maintainCCAFilterVO.getBillingBasis().trim().length()>0){
					query.append(" AND BLGMST.MALIDR= ? ");
					query.setParameter(++index, maintainCCAFilterVO.getBillingBasis());	
				
				}
				if(maintainCCAFilterVO.getConsignmentDocNum()!=null
						&& maintainCCAFilterVO.getConsignmentDocNum().trim().length()>0){
					query.append(" AND BLGMST.CSGDOCNUM = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum());
				}
				if(maintainCCAFilterVO.getConsignmentSeqNum()!= 0){
					query.append(" AND BLGMST.CSGSEQNUM = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());
				}
				if(maintainCCAFilterVO.getPOACode()!=null
						&& maintainCCAFilterVO.getPOACode().trim().length()>0){
					query.append(" AND BLGMST.POACOD = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getPOACode());
				}
				if(maintainCCAFilterVO.getPartyCode()!=null
						&& maintainCCAFilterVO.getPartyCode().trim().length()>0){
					/*
					 * IssuingParty From MaintainCCAFilterVO
					 * 1. "G" - GPA
					 * 2. "A" - OWN AIRLINE
					 * 3. "OAL" - OTHER AIRLINE
					 */
					if(maintainCCAFilterVO.getIssuingParty()!=null
							&& maintainCCAFilterVO.getIssuingParty().trim().length()>0){
						if("A".equals(maintainCCAFilterVO.getIssuingParty())){
							//OWN AIRLINE
							//query.append(" AND BLGDTL.GPAARLBLGFLG = 'A' ");
							//Commented by A-7794 as part of MRA revamp
							//query.append(" AND BLGDTL.PAYFLG = 'R' ");
						}else if("G".equals(maintainCCAFilterVO.getIssuingParty())){
							//GPA
							//Commented by A-7794 as part of MRA revamp
							//query.append(" AND BLGDTL.GPAARLBLGFLG = 'G' ");
							//query.append(" AND BLGDTL.PAYFLG = 'R' ");
							query.append(" AND BLGDTL.UPDBILTOOPOA = ? ");
							query.setParameter(++index, maintainCCAFilterVO.getPartyCode());
						}else if("OAL".equals(maintainCCAFilterVO.getIssuingParty())){
							//OTHER AIRLINE
							/*
							 * commented to fetch details if issuing party is other airline(OAL)
							 * it can come in two scenario
							 * 1: GPA -->> NZ(A) -->> QF(OAL)  here P record must be fetched
							 * 2: GPA -->> QF(OAL) -->> NZ(A)  here R record must be fetched
							 */
							//query.append(" AND BLGDTL.PAYFLG = 'P'");
							query.append(" AND BLGDTL.UPDBILTOOPOA = ? ");
							query.setParameter(++index, maintainCCAFilterVO.getPartyCode());
							
						}
					}

			}
				//Commented by A-7794 as part of MRA revamp
			//query.append(" AND BLGDTL.PAYFLG = 'R' ");
			}			
			query.append(" ORDER BY RTG.RTGSERNUM ");

			log.log(Log.INFO, "query-->>", query);
			return query.getResultList(new CCADetailsMapper());
		}

		/** @param maintainCCAFilterVO
		 * @return  Collection<CCAdetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public  Collection<CCAdetailsVO> findCCA(MaintainCCAFilterVO maintainCCAFilterVO)
		throws  SystemException,PersistenceException{
			Query query = getQueryManager().createNamedNativeQuery(
					MRA_DEFAULTS_FINDCCA);
			int index=0;
			query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());

			if(maintainCCAFilterVO.getCcaReferenceNumber()!=null
					&& maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0){
				query.append(" AND (CCADTL.MCAREFNUM = ? OR CCADTL.MCAREFNUM = ? )");
				query.setParameter(++index, maintainCCAFilterVO.getCcaReferenceNumber());
				query.setParameter(++index, maintainCCAFilterVO.getCcaReferenceNumber());
			}
				
			//Modified as part of ICRD-341973
			if(maintainCCAFilterVO.getDsnNumber()!=null && maintainCCAFilterVO.getDsnNumber().trim().length()>0 && maintainCCAFilterVO.getDsnNumber().trim().length()!=12){
				
				if(maintainCCAFilterVO.getDsnNumber().trim().length()>4)
				{
					
				query.append(" AND BLGMST.DSN = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getDsnNumber().substring(16, 20));
				}
				else
				{   query.append(" AND BLGMST.DSN = ? ");    
				query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
				}
			}
			if(maintainCCAFilterVO.getBillingBasis()!=null
				&& maintainCCAFilterVO.getBillingBasis().trim().length()>0){
				query.append(" AND BLGMST.MALIDR = ? ");
			query.setParameter(++index, maintainCCAFilterVO.getBillingBasis());
			}
			if(maintainCCAFilterVO.getConsignmentDocNum()!=null
					&& maintainCCAFilterVO.getConsignmentDocNum().trim().length()>0){
				query.append(" AND BLGMST.CSGDOCNUM = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum());
			}
			if(maintainCCAFilterVO.getConsignmentSeqNum()!= 0){
				query.append(" AND BLGMST.CSGSEQNUM = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());
			}
			if(maintainCCAFilterVO.getPOACode()!=null
					&& maintainCCAFilterVO.getPOACode().trim().length()>0){
				query.append(" AND BLGMST.POACOD = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getPOACode());
			}
			//Commented by A-7794 as part of MRA revamp
			/*if(maintainCCAFilterVO.getDsnDate()!=null){
				query.append(" AND TRUNC(CCADTL.DSPDATE) = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getDsnDate());
			}*/
			if(maintainCCAFilterVO.getPartyCode()!=null
					&& maintainCCAFilterVO.getPartyCode().trim().length()>0){

				/*
				 * IssuingParty From MaintainCCAFilterVO
				 * 1. "G" - GPA
				 * 2. "A" - Own Airline
				 * 3. "OAL" - Other AirLine
				 */
				/*if(maintainCCAFilterVO.getIssuingParty()!=null
						&& maintainCCAFilterVO.getIssuingParty().trim().length()>0){
					query.append(" AND CCADTL.ARLGPAIND = ? ");
					if("G".equals(maintainCCAFilterVO.getIssuingParty())){
						//GPA
						query.setParameter(++index, "G");
					}else{
						// Own Airline OR Other Airline
						query.setParameter(++index, "A");
					}
				}*/
				if(maintainCCAFilterVO.getAirlineGpaIndicator()!=null
						&& maintainCCAFilterVO.getAirlineGpaIndicator().trim().length()>0){
					query.append(" AND CCADTL.GPACHGIND = ? ");
					query.setParameter(++index, maintainCCAFilterVO.getAirlineGpaIndicator());
				}
				//Commented by A-7794 as part of MRA revamp
				//query.append(" AND CCADTL.ISSPARTY = ? ");
				//query.setParameter(++index, maintainCCAFilterVO.getPartyCode());
			}
			return query.getResultList(new FindCCAMapper());


		}
		/**
		 * @author A-3429
		 *
		 * @param listCCAFilterVo
		 * @return Page<CCAdetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public Page<CCAdetailsVO> listCCAs(ListCCAFilterVO listCCAFilterVo)
		throws SystemException, PersistenceException {
			
			//Added by A-5201 as part for the ICRD-21098 starts
			String pgqry = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_LISTCCA);		
			
			StringBuilder rankQuery = new StringBuilder().append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
			rankQuery.append(pgqry);
			
			PageableNativeQuery<CCAdetailsVO> qry = 
				new PageableNativeQuery<CCAdetailsVO>(listCCAFilterVo.getTotalRecords(),rankQuery.toString(),new ListCCAMapper());
			
			//Added by A-5201 as part for the ICRD-21098 end

	int index = 0;
	qry.setParameter(++index, listCCAFilterVo.getCompanyCode());
	if(listCCAFilterVo.getCcaRefNumber()!=null&& listCCAFilterVo.getCcaRefNumber().trim().length() > 0){
		qry.append(" AND MALMRAMCA.MCAREFNUM=?");
		qry.setParameter(++index, listCCAFilterVo.getCcaRefNumber());
	}
	if(listCCAFilterVo.getCcaType()!=null&& listCCAFilterVo.getCcaType().trim().length() > 0){
		qry.append(" AND MALMRAMCA.MCATYP=?");
		qry.setParameter(++index, listCCAFilterVo.getCcaType());
	}
	if(listCCAFilterVo.getDsn()!=null&& listCCAFilterVo.getDsn().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.DSN=?");
		qry.setParameter(++index, listCCAFilterVo.getDsn());
	}
//	if(listCCAFilterVo.getDsnDate()!=null){
//		qry.append(" AND TRUNC(MTKMRACCADTL.DSPDATE)= ? ");
//		qry.setParameter(++index, listCCAFilterVo.getDsnDate());
//	}
	if(listCCAFilterVo.getOriginOE()!=null&& listCCAFilterVo.getOriginOE().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.ORGEXGOFC=?");
		qry.setParameter(++index, listCCAFilterVo.getOriginOE());
	}
	if(listCCAFilterVo.getDestinationOE()!=null&& listCCAFilterVo.getDestinationOE().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.DSTEXGOFC=?");
		qry.setParameter(++index, listCCAFilterVo.getDestinationOE());
	}
	if(listCCAFilterVo.getCategoryCode()!=null&& listCCAFilterVo.getCategoryCode().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.MALCTGCOD=?");
		qry.setParameter(++index, listCCAFilterVo.getCategoryCode());
	}
	if(listCCAFilterVo.getSubClass()!=null&& listCCAFilterVo.getSubClass().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.MALSUBCLS=?");
		qry.setParameter(++index, listCCAFilterVo.getSubClass());
	}
	if(listCCAFilterVo.getRsn()!=null&& listCCAFilterVo.getRsn().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.RSN=?");
		qry.setParameter(++index, listCCAFilterVo.getRsn());
	}
	if(listCCAFilterVo.getHni()!=null&& listCCAFilterVo.getHni().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.HSN=?");
		qry.setParameter(++index, listCCAFilterVo.getHni());
	}
	if(listCCAFilterVo.getRegInd()!=null&& listCCAFilterVo.getRegInd().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.REGIND=?");
		qry.setParameter(++index, listCCAFilterVo.getRegInd());
	}
	if(listCCAFilterVo.getYear()!=null&& listCCAFilterVo.getYear().trim().length() > 0){
		qry.append(" AND MALMRABLGMST.YER=?");
		qry.setParameter(++index, Integer.parseInt(listCCAFilterVo.getYear()));
	}
	if(listCCAFilterVo.getCcaStatus()!=null&& listCCAFilterVo.getCcaStatus().trim().length() > 0){
		qry.append(" AND MALMRAMCA.MCASTA=?");
		qry.setParameter(++index, listCCAFilterVo.getCcaStatus());
	}
	log.log(Log.INFO, "listCCAFilterVO in sql dao====>", listCCAFilterVo);
	//if(listCCAFilterVo.getIssueParty()!=null&& listCCAFilterVo.getIssueParty().trim().length() > 0){
		//qry.append(" AND MTKMRACCADTL.ARLGPAIND=?");
		///qry.setParameter(++index, listCCAFilterVo.getArlGpaIndicator());
	//}
	log.log(Log.INFO, "listCCAFilterVO in sql dao====>", listCCAFilterVo.getAirlineCode());
	//Commented by A-7794 as part of MRA revamp
	/*if(listCCAFilterVo.getAirlineCode()!=null&& listCCAFilterVo.getAirlineCode().trim().length() > 0){
		qry.append(" AND MALMRAMCA.ISSPARTY=?");
		qry.setParameter(++index, listCCAFilterVo.getAirlineCode());
	}*/
	if(listCCAFilterVo.getGpaCode()!=null&& listCCAFilterVo.getGpaCode().trim().length() > 0){
		qry.append(" AND MALMRAMCA.REVGPACOD=?");
		qry.setParameter(++index, listCCAFilterVo.getGpaCode());
	}
	if(listCCAFilterVo.getFromDate()!=null){
		qry.append(" AND TO_NUMBER(TO_CHAR(MALMRAMCA.ISSDAT,'YYYYMMDD'))>= ? ");
		qry.setParameter(++index,Integer.parseInt(listCCAFilterVo.getFromDate().toStringFormat(DATE).substring(0, 8))  );
	}
	log.log(Log.INFO, "inside sqldao", listCCAFilterVo.getToDate());
	if(listCCAFilterVo.getToDate()!=null){
	qry.append(" AND TO_NUMBER(TO_CHAR(MALMRAMCA.ISSDAT,'YYYYMMDD'))<= ? ");
	qry.setParameter(++index,Integer.parseInt(listCCAFilterVo.getToDate().toStringFormat(DATE).substring(0, 8)) );
	}
	
		if(listCCAFilterVo.getOrigin()!=null&& listCCAFilterVo.getOrigin().trim().length() > 0){
			qry.append(" AND MALMRABLGMST.ORGARPCOD=?");
			qry.setParameter(++index, listCCAFilterVo.getOrigin());
		}
		if(listCCAFilterVo.getDestination()!=null&& listCCAFilterVo.getDestination().trim().length() > 0){
			qry.append(" AND MALMRABLGMST.DSTARPCOD=?");
			qry.setParameter(++index, listCCAFilterVo.getDestination());
		}
			
		if(listCCAFilterVo.getBillingStatus() != null && listCCAFilterVo.getBillingStatus().trim().length() > 0){
			qry.append(" AND MALMRAMCA.BLGSTA=?");
			qry.setParameter(++index, listCCAFilterVo.getBillingStatus());
		}
			
		//Added by A-7540
		if(listCCAFilterVo.getMcacreationtype() != null && listCCAFilterVo.getMcacreationtype().trim().length() > 0){
			qry.append(" AND MALMRAMCA.AUTMCA=?");
			qry.setParameter(++index, listCCAFilterVo.getMcacreationtype());
		}
			qry.append(MRA_DEFAULTS_SUFFIX_QUERY);
			log.log(Log.INFO, "query", qry);
			return qry.getPage(listCCAFilterVo.getPageNumber());//Added by A-5201 as part for the ICRD-21098
}





		/**
		 * @author A-2554
		 * @param ProrationFilterVO
		 * @return Collection<ProrationDetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public Collection<ProrationDetailsVO> listProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_LISTPRORATION_DETAILS);

		log.log(Log.INFO, "filter VO", prorationFilterVO);
		log.log(Log.INFO, "BASE QUERY------->>>>>>>>>>>>>>", query);
		StringBuilder sb = new StringBuilder();

		sb.append("AND  DTL.CMPCOD ='".trim()).append(
				prorationFilterVO.getCompanyCode().trim()).append(
				"' AND MST.MALIDR ='".trim()).append(
				prorationFilterVO.getBillingBasis().trim()).append("'");

		query.append(sb.toString());
		sb = new StringBuilder();

		if (prorationFilterVO.getConsigneeDocumentNumber() != null) {
			sb.append("AND MST.CSGDOCNUM ='").append(
					prorationFilterVO.getConsigneeDocumentNumber()).append("'");
		}

		/*
		 * Commented For ANZ BUG 34178
		 * Flight details are not needed for listing Proration Details.
		 * START
		 *
		if (prorationFilterVO.getFlightNumber() != null && prorationFilterVO.getFlightNumber().trim().length()>0
				&& prorationFilterVO.getFlightDate() != null) {
			sb.append("AND DTL.FLTNUM ='").append(
					prorationFilterVO.getFlightNumber().trim()).append("'")
					.append(" AND DTL.FLTCARIDR =").append(
							prorationFilterVO.getFlightCarrierIdentifier())
					.append(" AND trunc(DTL.FLTDAT) ='").append(
							prorationFilterVO.getFlightDate().toDisplayDateOnlyFormat()).append("'");

		}
		*END
		*/
		sb.append("ORDER BY DTL.SERNUM");
		query.append(sb.toString());

		log.log(Log.INFO, "FINAL LIST PRORATION QUERY IS----------->>", query.toString());
		return query.getResultList(new ProrationDetailsMapper(prorationFilterVO));

	}


		/**
		 * @author A-3429
		 *
		 * @param listCCAFilterVo
		 * @return Collection<CCAdetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
	public Collection<CCAdetailsVO> listCCAforPrint(
			ListCCAFilterVO listCCAFilterVo) throws SystemException,
			PersistenceException {
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_LISTCCA);

		int index = 0;
		qry.setParameter(++index, listCCAFilterVo.getCompanyCode());
		if (listCCAFilterVo.getCcaRefNumber() != null
				&& listCCAFilterVo.getCcaRefNumber().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.MCAREFNUM=?");
			qry.setParameter(++index, listCCAFilterVo.getCcaRefNumber());
		}
		if (listCCAFilterVo.getCcaType() != null
				&& listCCAFilterVo.getCcaType().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.MCATYP=?");
			qry.setParameter(++index, listCCAFilterVo.getCcaType());
		}
		if (listCCAFilterVo.getDsn() != null
				&& listCCAFilterVo.getDsn().trim().length() > 0) {
			qry.append(" AND MALMRABLGMST.DSN=?");
			qry.setParameter(++index, listCCAFilterVo.getDsn());
		}
		if (listCCAFilterVo.getDsnDate() != null) {
			qry.append(" AND TRUNC(MALMRAMCA.DSPDATE)= ? ");
			qry.setParameter(++index, listCCAFilterVo.getDsnDate());
		}
		if (listCCAFilterVo.getCcaStatus() != null
				&& listCCAFilterVo.getCcaStatus().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.MCASTA=?");
			qry.setParameter(++index, listCCAFilterVo.getCcaStatus());
		}
		/*if (listCCAFilterVo.getIssueParty() != null
				&& listCCAFilterVo.getIssueParty().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.ARLGPAIND=?");
			qry.setParameter(++index, listCCAFilterVo.getArlGpaIndicator());
		}*/
		if (listCCAFilterVo.getAirlineCode() != null
				&& listCCAFilterVo.getAirlineCode().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.ISSPARTY=?");
			qry.setParameter(++index, listCCAFilterVo.getAirlineCode());
		}
		if (listCCAFilterVo.getGpaCode() != null
				&& listCCAFilterVo.getGpaCode().trim().length() > 0) {
			qry.append(" AND MALMRAMCA.REVGPACOD=?");
			qry.setParameter(++index, listCCAFilterVo.getGpaCode());
		}

		if (listCCAFilterVo.getFromDate() != null) {
			qry.append(" AND TRUNC(MALMRAMCA.ISSDAT)>= ? ");
			qry.setParameter(++index, listCCAFilterVo.getFromDate());
		}
		log.log(Log.INFO, "inside sqldao", listCCAFilterVo.getToDate());
		qry.append(" AND TRUNC(MALMRAMCA.ISSDAT)<= ? ");
		qry.setParameter(++index, listCCAFilterVo.getToDate());
		log.log(Log.INFO, "query", qry);
		Collection<CCAdetailsVO> cCAdetailsVOs = qry
				.getResultList(new ListCCAMapper());
		log.log(Log.INFO, "cCAdetailsVOs inside sqldao", cCAdetailsVOs);
		return cCAdetailsVOs;
	}

	/** @param airlineBillingFilterVO
	 * @return  Collection<DocumentBillingDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Page<DocumentBillingDetailsVO> findInterlineBillingEntries (AirlineBillingFilterVO airlineBillingFilterVO)
	throws  SystemException,PersistenceException{


		//added by A-5223 for ICRD-21098 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
		//added by A-5223 for ICRD-21098 ends
		String queryString = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDINTRLINBLGENTRIESINBLGDTL);
		Query c66Dtlquery = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDINTRLINBLGENTRIESINC66DTL);
		Query ccaDtlquery = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDINTRLINBLGENTRIESINCCADTL);
		Query rejectionMemoquery = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDINTRLINBLGENTRIESINREJMEMO);

		LogonAttributes logonAttributes =
			(LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		int ownAirlineidentifier = logonAttributes.getOwnAirlineIdentifier();
		//added by A-5223 for ICRD-21098 starts
		rankQuery.append(queryString);
		PageableNativeQuery<DocumentBillingDetailsVO> blgDtlquery = 
			new PageableNativeQuery<DocumentBillingDetailsVO>(airlineBillingFilterVO.getTotalRecords(),
					rankQuery.toString(),new InterLineBillingEntriesMultiMapper());
		
		//added by A-5223 for ICRD-21098 ends
		if(blgDtlquery!=null){

			StringBuilder sbd=new StringBuilder();

			sbd.append("'");
			sbd.append(airlineBillingFilterVO.getCompanyCode());
			sbd.append("'");

			if(airlineBillingFilterVO.getFromDate() != null ){

				sbd.append( "AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) >=");
				sbd.append("'");
				sbd.append(Integer.parseInt(airlineBillingFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
				sbd.append("'");

			}

			if(airlineBillingFilterVO.getToDate() != null){

				sbd.append( "AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) <=");
				sbd.append("'");
				sbd.append(Integer.parseInt(airlineBillingFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getAirlineCode()!=null){
				sbd.append( "AND DTL.BILTOOPTYCOD =");//modified as part of ICRD-297476
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getAirlineCode());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getBillingStatus()!= null){

				sbd.append( "AND  DTL.BLGSTA =");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getBillingStatus());
				sbd.append("'");
			}
			if("I".equals(airlineBillingFilterVO.getBillingType())){

				sbd.append( "AND DTL.BLGSTA='IU'");

			}
			if("O".equals(airlineBillingFilterVO.getBillingType())){

				sbd.append( "AND (DTL.BLGSTA ='OB' OR DTL.BLGSTA ='OH')");

			}
			if(airlineBillingFilterVO.getSectorFrom()!= null){

				sbd.append( "AND DTL.SECFRM =");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getSectorFrom());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getSectorTo()!= null){

				sbd.append( "AND DTL.SECTOO=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getSectorTo());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getOriginOfficeOfExchange()!= null){

				sbd.append( "AND MST.ORGEXGOFC=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getOriginOfficeOfExchange());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getDestinationOfficeOfExchange()!= null){

				sbd.append( "AND MST.DSTEXGOFC=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getDestinationOfficeOfExchange());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getMailCategory()!= null){

				sbd.append( "AND MST.MALCTGCOD=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getMailCategory());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getSubClass()!= null){

				sbd.append( "AND MST.MALSUBCLS=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getSubClass());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getYear()!= null){

				sbd.append( "AND MST.YER=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getYear());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getDsn()!= null){

				sbd.append( "AND MST.DSN=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getDsn());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getReceptacleSerialNumber()!= null){

				sbd.append( "AND MST.RSN=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getReceptacleSerialNumber());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getHighestNumberIndicator()!= null){

				sbd.append( "AND MST.HSN=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getHighestNumberIndicator());
				sbd.append("'");

			}
			if(airlineBillingFilterVO.getRegisteredIndicator()!= null){

				sbd.append( "AND MST.REGIND=");
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getRegisteredIndicator());
				sbd.append("'");

			}
			blgDtlquery.append(sbd.toString());

		}

		StringBuilder sb=new StringBuilder();
		if(c66Dtlquery!=null){

			sb.append("'");
			sb.append(airlineBillingFilterVO.getCompanyCode());
			sb.append("'");


			if(airlineBillingFilterVO.getFromDate() != null ){
				sb.append(" AND TO_NUMBER(TO_CHAR(C66DTL.DSNDAT,'YYYYMMDD')) >= ");
				sb.append("'");
				sb.append(Integer.parseInt(airlineBillingFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)) );
				sb.append("'");
			}

			if(airlineBillingFilterVO.getToDate() != null){
				sb.append(" AND TO_NUMBER(TO_CHAR(C66DTL.DSNDAT,'YYYYMMDD')) <=");
				sb.append("'");
				sb.append(Integer.parseInt(airlineBillingFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)) );
				sb.append("'");
			}

			if(airlineBillingFilterVO.getAirlineCode()!= null){
				sb.append(" AND C66DTL.CARCOD=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getAirlineCode());
				sb.append("'");
			}
			if("I".equals(airlineBillingFilterVO.getBillingType())){
				sb.append(" AND INTBLGTYP='I' ");

			}
			if("O".equals(airlineBillingFilterVO.getBillingType())){
				sb.append("AND INTBLGTYP ='O' ");

			}
			if(airlineBillingFilterVO.getSectorFrom()!= null){
				sb.append(" AND C66DTL.CARFRM=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getSectorFrom());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getSectorTo()!= null){
				sb.append(" AND C66DTL.CARTOO=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getSectorTo());
				sb.append("'");
			}
			
			
			
			if(airlineBillingFilterVO.getBillingStatus()!= null){
				if("IB".equals(airlineBillingFilterVO.getBillingStatus())){

					sb.append( "AND C66DTL.INTBLGTYP='I'");

				}
				else if("OD".equals(airlineBillingFilterVO.getBillingStatus())){

					sb.append( "AND C66DTL.INTBLGTYP ='O'");

				}
				
				else if("WD".equals(airlineBillingFilterVO.getBillingStatus())){

					sb.append( "AND C66DTL.MALSTA ='D'");

				}
				
				else{

					sb.append( "AND C66DTL.INTBLGTYP =''");

				}
			}
			//Added by A-4809 for CR ICRD-64861 ...Starts
			if(airlineBillingFilterVO.getOriginOfficeOfExchange()!= null){
				sb.append( "AND MST.ORGEXGOFC=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getOriginOfficeOfExchange());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getDestinationOfficeOfExchange()!= null){
				sb.append( "AND MST.DSTEXGOFC=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getDestinationOfficeOfExchange());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getMailCategory()!= null){
				sb.append( "AND C66DTL.MALCTGCOD=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getMailCategory());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getSubClass()!= null){
				sb.append( "AND C66DTL.SUBCLSGRP=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getSubClass());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getYear()!= null){
				sb.append( "AND MST.YER=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getYear());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getDsn()!= null){
				sb.append( "AND C66DTL.DSN=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getDsn());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getReceptacleSerialNumber()!= null){
				sb.append( "AND MST.RSN=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getReceptacleSerialNumber());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getHighestNumberIndicator()!= null){
				sb.append( "AND MST.HSN=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getHighestNumberIndicator());
				sb.append("'");
			}
			if(airlineBillingFilterVO.getRegisteredIndicator()!= null){
				sb.append( "AND MST.REGIND=");
				sb.append("'");
				sb.append(airlineBillingFilterVO.getRegisteredIndicator());
				sb.append("'");
			}
			//Added by A-4809 for CR ICRD-64861 ...Ends
			c66Dtlquery.append(sb.toString());

			blgDtlquery.append(" UNION ALL ");

			blgDtlquery.append(c66Dtlquery.toString());

			StringBuilder sbd=new StringBuilder();
			if(ccaDtlquery!=null){
				sbd.append("'");
				sbd.append(airlineBillingFilterVO.getCompanyCode());
				sbd.append("'");

				if(airlineBillingFilterVO.getFromDate() != null ){
					sbd.append(" AND TO_NUMBER(TO_CHAR(CCADTL.ISSDAT,'YYYYMMDD')) >= ");
					sbd.append("'");
					sbd.append(Integer.parseInt(airlineBillingFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
					sbd.append("'");
				}

				if(airlineBillingFilterVO.getToDate() != null){
					sbd.append(" AND  TO_NUMBER(TO_CHAR(CCADTL.ISSDAT,'YYYYMMDD')) <=");
					sbd.append("'");
					sbd.append(Integer.parseInt(airlineBillingFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
					sbd.append("'");
				}
				/*	commented as part of ICRD-297476

				if(airlineBillingFilterVO.getAirlineCode()!= null){
					sbd.append(" AND CCADTL.ARLCOD =");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getAirlineCode());
					sbd.append("'");
				}  */
				if("I".equals(airlineBillingFilterVO.getBillingType())){

					sbd.append( "AND CCADTL.BLGSTA='IU'");//Modified as part of ICRD-297476

				}
				if("O".equals(airlineBillingFilterVO.getBillingType())){

					sbd.append( "AND CCADTL.BLGSTA ='OB'");//Modified as part of ICRD-297476

				}
				if(airlineBillingFilterVO.getBillingStatus()!= null){

					sbd.append( "AND  CCADTL.BLGSTA =");//Modified as part of ICRD-297476
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getBillingStatus());
					sbd.append("'");
				}
				if(airlineBillingFilterVO.getSectorFrom()!= null){
					sbd.append(" AND CCADTL.SECFRM=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getSectorFrom());
					sbd.append("'");
				}
				if(airlineBillingFilterVO.getSectorTo()!= null){
					sbd.append(" AND CCADTL.SECTOO=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getSectorTo());
					sbd.append("'");
				}

				//Added by A-4809 for CR ICRD-64861 ...Starts
				
				if(airlineBillingFilterVO.getOriginOfficeOfExchange()!= null){

					sbd.append( "AND MST.ORGEXGOFC=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getOriginOfficeOfExchange());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getDestinationOfficeOfExchange()!= null){

					sbd.append( "AND MST.DSTEXGOFC=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getDestinationOfficeOfExchange());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getMailCategory()!= null){

					sbd.append( "AND MST.MALCTGCOD=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getMailCategory());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getSubClass()!= null){

					sbd.append( "AND MST.MALSUBCLS=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getSubClass());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getYear()!= null){

					sbd.append( "AND MST.YER=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getYear());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getDsn()!= null){

					sbd.append( "AND MST.DSN="); // Modified by A-8527 for ICRD-294644
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getDsn());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getReceptacleSerialNumber()!= null){

					sbd.append( "AND MST.RSN=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getReceptacleSerialNumber());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getHighestNumberIndicator()!= null){

					sbd.append( "AND MST.HSN=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getHighestNumberIndicator());
					sbd.append("'");

				}
				if(airlineBillingFilterVO.getRegisteredIndicator()!= null){

					sbd.append( "AND MST.REGIND=");
					sbd.append("'");
					sbd.append(airlineBillingFilterVO.getRegisteredIndicator());
					sbd.append("'");

				}
				//Added by A-4809 for CR ICRD-64861 ...Ends

			}
			ccaDtlquery.append(sbd.toString());
			blgDtlquery.append(" UNION ALL ");
			blgDtlquery.append(ccaDtlquery.toString());
			StringBuilder sbdr=new StringBuilder();
			if(rejectionMemoquery != null){
				sbdr.append("'");
				sbdr.append(airlineBillingFilterVO.getCompanyCode());
				sbdr.append("'");
				
					
				if(airlineBillingFilterVO.getFromDate() != null ){
					sbdr.append(" AND TO_NUMBER(TO_CHAR(MEM.MEMDAT,'YYYYMMDD')) >= ");
					sbdr.append("'");
					sbdr.append(Integer.parseInt(airlineBillingFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)) );
					sbdr.append("'");
				}

				if(airlineBillingFilterVO.getToDate() != null){
					sbdr.append(" AND TO_NUMBER(TO_CHAR(MEM.MEMDAT,'YYYYMMDD'))<=");
					sbdr.append("'");
					sbdr.append(Integer.parseInt(airlineBillingFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
					sbdr.append("'");
				}



				if(airlineBillingFilterVO.getAirlineCode()!= null){
					sbdr.append(" AND DTL.ARLCOD =");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getAirlineCode());
					sbdr.append("'");
				}
				if("I".equals(airlineBillingFilterVO.getBillingType())){

					sbdr.append( "AND DTL.INTBLGTYP=''");

				}
				if("O".equals(airlineBillingFilterVO.getBillingType())){

					sbdr.append( "AND DTL.INTBLGTYP='I'");

				}
				if(airlineBillingFilterVO.getBillingStatus()!= null){

					sbdr.append( "AND  MEM.MEMSTA =");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getBillingStatus());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getSectorFrom()!= null){
					sbdr.append(" AND DTL.SECFRM=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getSectorFrom());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getSectorTo()!= null){
					sbdr.append(" AND DTL.SECTOO=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getSectorTo());
					sbdr.append("'");
				}
				//Added by A-4809 for CR ICRD-64861 ...Starts
				if(airlineBillingFilterVO.getOriginOfficeOfExchange()!= null){
					sbdr.append( "AND MST.ORGEXGOFC=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getOriginOfficeOfExchange());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getDestinationOfficeOfExchange()!= null){
					sbdr.append( "AND MST.DSTEXGOFC=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getDestinationOfficeOfExchange());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getMailCategory()!= null){
					sbdr.append( "AND MST.MALCTGCOD=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getMailCategory());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getSubClass()!= null){
					sbdr.append( "AND MST.MALSUBCLS=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getSubClass());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getYear()!= null){
					sbdr.append( "AND MST.YER=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getYear());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getDsn()!= null){
					sbdr.append( "AND MEM.DSN=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getDsn());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getReceptacleSerialNumber()!= null){
					sbdr.append( "AND MST.RSN=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getReceptacleSerialNumber());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getHighestNumberIndicator()!= null){
					sbdr.append( "AND MST.HSN=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getHighestNumberIndicator());
					sbdr.append("'");
				}
				if(airlineBillingFilterVO.getRegisteredIndicator()!= null){
					sbdr.append( "AND MST.REGIND=");
					sbdr.append("'");
					sbdr.append(airlineBillingFilterVO.getRegisteredIndicator());
					sbdr.append("'");
				}
				//Added by A-4809 for CR ICRD-64861 ...Ends

			}
			blgDtlquery.append(" UNION ALL ");
			rejectionMemoquery.append(sbdr.toString());
			blgDtlquery.append(rejectionMemoquery.toString());
			StringBuilder orderingString = new StringBuilder();
			orderingString.append(" ) ORDER BY CMPCOD,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,INVNUM,MEMCOD,ARLIDR ");
			blgDtlquery.append(orderingString.toString());
			log.log(Log.INFO, "finalquery-->>", blgDtlquery);

		}
		
		//added by A-5223 for ICRD-21098 starts
		blgDtlquery.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
		//added by A-5223 for ICRD-21098 ends
		
		// added For Pagination
//		PageableQuery<DocumentBillingDetailsVO> pgqry = null;
		int pageNumber=airlineBillingFilterVO.getPageNumber();
log.log(Log.INFO, "pageNumber..", pageNumber);
		//		log.log(Log.INFO, "absoluteIndex.." + absoluteIndex);
//		pgqry = new PageableQuery<DocumentBillingDetailsVO>(blgDtlquery, new InterLineBillingEntriesMultiMapper());
		return blgDtlquery.getPage(pageNumber);
	}
   /**
     * @author A-3429
     * This method is for findFlightDetails
	 * @param flownMailFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException
	 */
	public Collection<SectorRevenueDetailsVO> findSectorDetails(FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)throws SystemException {
		// TODO Auto-generated method stub
		Query query = getQueryManager().createNamedNativeQuery(FIND_FLIGHTDETAILS);

		query.setParameter(1, flightSectorRevenueFilterVO.getFlightNumber());
		query.setParameter(2, flightSectorRevenueFilterVO.getFlightCarrierId());
		query.setParameter(3, flightSectorRevenueFilterVO.getFlightSequenceNumber());
		query.setParameter(4, flightSectorRevenueFilterVO.getCompanyCode());

		return query.getResultList(new SectorDetailsMapper());
	}
	/**
     * @author A-3429
     * This method is for findFlightDetails
	 * @param flownMailFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException
	 */
	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)throws SystemException {
		// TODO Auto-generated method stub
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FLIGHTSECTORREVENUE);

		int index = 0;
		qry.setParameter(++index, flightSectorRevenueFilterVO.getCompanyCode());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getPayFlag());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightCarrierCode());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightNumber());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightDate());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getSegmentOrigin());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getSegmentDestination());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getCompanyCode());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightCarrierCode());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightNumber());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getFlightDate());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getSegmentOrigin());
		qry.setParameter(++index, flightSectorRevenueFilterVO.getSegmentDestination());
		return qry.getResultList(new ViewFlightSectorRevenueMapper());
	}


	/**
	 * @author A-2391
	 */
	public RateAuditVO findListRateAuditDetailsFromTemp(RateAuditFilterVO rateAuditFilterVO)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDLISTRATEAUDITDETAILSFROMTEMP);
		int index=0;
		query.setParameter(++index, rateAuditFilterVO.getCompanyCode());
		query.setParameter(++index, rateAuditFilterVO.getBillingBasis());
		query.setParameter(++index, rateAuditFilterVO.getCsgDocNum());
		query.setParameter(++index, rateAuditFilterVO.getCsgSeqNum());
		query.setParameter(++index, rateAuditFilterVO.getGpaCode());
		query.setParameter(++index, rateAuditFilterVO.getDsnDate());
		return query.getResultList(new ListRateAuditDtlsMultiMapper()).get(0);


	}


	/**
	 * @author A-2521 method to generate invoice for outward airline billing
	 *
	 * @param invoiceFilterVO
	 * @throws SystemException
	 * @return outPut
	 */
	public String generateOutwardBillingInvoice(
			InvoiceLovFilterVO invoiceFilterVO) throws SystemException {

		log.entering("MRADefaultsSqlDAO", "generateInvoice");

		String outPut = null;
		String temp = null;
		int index = 0;
		Procedure generateInvoice = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_GENINV_PROC);
		generateInvoice.setSensitivity(true);

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		generateInvoice.setParameter(++index, invoiceFilterVO.getCompanycode());
		generateInvoice.setParameter(++index,invoiceFilterVO.getClearingHouse());
		generateInvoice.setParameter(++index, invoiceFilterVO
				.getClearanceperiod());
		generateInvoice.setParameter(++index, invoiceFilterVO.getAirlineidr());
		if(!"".equals(invoiceFilterVO.getAirlineCode()) && (invoiceFilterVO.getAirlineCode()!=null)){
			generateInvoice.setParameter(++index, invoiceFilterVO.getAirlineCode());
		}else{
			generateInvoice.setParameter(++index,"");
		}
		generateInvoice.setParameter(++index, logonAttributes.getUserId());
		generateInvoice.setParameter(++index, currentDate.toSqlTimeStamp());
		generateInvoice.setParameter(++index, logonAttributes.getAirportCode());
		generateInvoice.setOutParameter(++index, SqlType.STRING);

		generateInvoice.execute();
		outPut = (String) generateInvoice.getParameter(index);

		log.log(Log.INFO, "outPut", outPut);
		log.exiting("MRADefaultsSqlDAO", "generateInvoice");
		return outPut;
	}


	 /**
	 *
	 * @param rateAuditVO
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String populateDataInTempTables(RateAuditVO rateAuditVO)throws SystemException,PersistenceException {

		Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_COMPUTETOTFORRATEAUDITDETAILS);
		procedure.setSensitivity(true);
		int index = 0;
		String prtflag = "Y";
		procedure.setParameter(++index, rateAuditVO.getCompanyCode());
		procedure.setParameter(++index, rateAuditVO.getBillingBasis());
		procedure.setParameter(++index, rateAuditVO.getConDocNum());
		procedure.setParameter(++index, rateAuditVO.getConSerNum());
		procedure.setParameter(++index, rateAuditVO.getGpaCode());
		procedure.setParameter(++index, prtflag);
		procedure.setParameter(++index, rateAuditVO.getCompTotTrigPt());
		procedure.setParameter(++index, rateAuditVO.getLastUpdateUser());
		procedure.setParameter(++index, rateAuditVO.getLastUpdateTime());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE, "executed Procedure populateDataInTempTables");
		String outParameter = (String) procedure.getParameter(9);
		log.log(Log.FINE, "outParameter is ", outParameter);
		return outParameter;
		}






	 /**
	 *
	 * @param rateAuditVO
	 * @return Integer
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Integer findProrateFactor(RateAuditDetailsVO rateAuditDetailsVO)throws SystemException,PersistenceException {

		Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_FINDPRORATEFACTOR);
		procedure.setSensitivity(true);
		int index = 0;
		Integer proval = 0;
		procedure.setParameter(++index, rateAuditDetailsVO.getSecFrom());
		procedure.setParameter(++index, rateAuditDetailsVO.getSecTo());
		procedure.setParameter(++index, rateAuditDetailsVO.getCompanyCode());
		procedure.setParameter(++index, rateAuditDetailsVO.getRecVDate());
		procedure.setOutParameter(++index, SqlType.INTEGER);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE, "executed Procedure findProrateFactor");
		String outParameter = (String) procedure.getParameter(6);
		log.log(Log.FINE, "outParameter is ", outParameter);
		log.log(Log.FINE, "prorate factor is ", procedure.getParameter(5));
		if("ok".equalsIgnoreCase(outParameter)){
			proval=	(Integer)procedure.getParameter(5);
		}else{
			//-1 is a flag to show proration failed
			proval=-1;
		}

		return proval;

		}


	/**
	 * @author A-3229
	 * @param dsnRoutingFilterVO
	 * @return  Collection<DSNRoutingVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<DSNRoutingVO> findDSNRoutingDetails(DSNRoutingFilterVO dsnRoutingFilterVO) throws  SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDDSNROUTINGDETAILS);

		//Modified by A-7794 as part of MRA revamp
		int index=0;
		query.setParameter(++index, dsnRoutingFilterVO.getCompanyCode());
		query.setParameter(++index, dsnRoutingFilterVO.getBillingBasis());
		LocalDate dsnDate = dsnRoutingFilterVO.getDsnDate();
		//	query.setParameter(++index, dsnRoutingFilterVO.getCsgDocumentNumber());
	//	query.setParameter(++index, dsnRoutingFilterVO.getCsgSequenceNumber());
	//	query.setParameter(++index, dsnRoutingFilterVO.getPoaCode());
		if (dsnDate  != null) {
			query.append(" AND TRUNC(MST.RCVDAT) =to_date(?, 'dd-MON-yyyy') ");
			query.setParameter(++index, dsnDate.toDisplayDateOnlyFormat());
		}
		query.append(" ORDER BY MST.MALSEQNUM,RTG.RTGSERNUM ");
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new DSNRoutingMapper());

	}

	/**
	 * @author A-2107
	 * @param listUnaccountedDispatches
	 * @return UnaccountedDispatchesFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
			throws SystemException, PersistenceException {
		log.log(Log.INFO, "unaccountedDispatchesFilterVO",
				unaccountedDispatchesFilterVO);
		int pageNumber=unaccountedDispatchesFilterVO.getPageNumber();
		Query qry = createUnaccountedDespatchQuery(unaccountedDispatchesFilterVO);
		if(qry != null){
			PageableQuery<UnaccountedDispatchesDetailsVO> pgqry = null;
			pgqry = new PageableQuery<UnaccountedDispatchesDetailsVO>(qry, new ListUnaccountedDispachesMapper());
			return pgqry.getPage(pageNumber);
		}else{
			return null;
		}

	}


	private Query createUnaccountedDespatchQuery(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws SystemException {
		Query qry = null;
		String baseQueryForR1 = null;
		String baseQueryForR1WithExp = null;
		String baseQueryForR2 = null;
		if("R1".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
			baseQueryForR1 = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMRA_R1);
			baseQueryForR1WithExp = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMRA_EXP);			
			qry = new UnaccountedDespatchFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2, unaccountedDispatchesFilterVO);
		}else if("R2".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
			baseQueryForR2 = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMTK_R2);
			qry = new UnaccountedDespatchFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2, unaccountedDispatchesFilterVO);
		}else if("".equals(unaccountedDispatchesFilterVO.getReasonCode())){
			baseQueryForR1 = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMRA_R1);
			baseQueryForR1WithExp = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMRA_EXP);
			baseQueryForR2 = getQueryManager().getNamedNativeQueryString(
					MRA_DEFAULTS_UNACCNTED_FROMMTK_R2);
			qry = new UnaccountedDespatchFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2, unaccountedDispatchesFilterVO);
		}
		return qry;
	}



	/**
	 * @author A-2107
	 * @param damageMailFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<UnaccountedDispatchesDetailsVO> findUnaccountedDispatchesReport(UnaccountedDispatchesFilterVO
			unaccountedDispatchesFilterVO) throws SystemException, PersistenceException {
		Query qry = createUnaccountedDespatchQuery(unaccountedDispatchesFilterVO);
		return qry.getResultList(new ListUnaccountedDispachesMapper());
	}


	/**
	 * @author A-2554
	 * @param airlineBillingDetailVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			   AirlineBillingDetailVO  airlineBillingDetailVO)
				throws  SystemException ,PersistenceException{

		Query query_partone = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FIND_INTERLINEBILLING_QRY1);

		Query query_parttwo = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FIND_INTERLINEBILLING_QRY2);

		StringBuilder sb=new StringBuilder();


		sb.append("'");
		sb.append(airlineBillingDetailVO.getCompanycode());
		sb.append("' AND BLGDTL.BLGBAS ='");
		sb.append(airlineBillingDetailVO.getBillingBasis());
		sb.append("' AND BLGDTL.POACOD = '");
		sb.append(airlineBillingDetailVO.getPoaCode());
		sb.append("' AND BLGDTL.CSGDOCNUM = '");
		sb.append(airlineBillingDetailVO.getConsignmentDocumentNumber());
		sb.append("' AND BLGDTL.CSGSEQNUM ='");
		sb.append(airlineBillingDetailVO.getConsignmentSequenceNumber());
		sb.append("' UNION ALL ");

		query_partone.append(sb.toString());

		sb=new StringBuilder();
		sb.append("'");
		sb.append(airlineBillingDetailVO.getBillingBasis());
		sb.append("' AND C66DTL.CMPCOD='");
		sb.append(airlineBillingDetailVO.getCompanycode());
		sb.append("'");

		query_parttwo.append(sb.toString());

		query_partone.append(query_parttwo.toString());

		return query_partone.getResultList(new InterLineBillingMapper());

	}
	  public Page<ProrationExceptionVO>  findProrationExceptions(ProrationExceptionsFilterVO prorationExceptionsFilterVO)
      throws SystemException
  {

	 log.entering("MRADefaultsSqlDAO", "findProrationExceptions");
      Page<ProrationExceptionVO> detailsVOs =null;
      log.log(Log.INFO, "prorationExceptionsFilterVO in sql dao===>>>",
			prorationExceptionsFilterVO);
	String route = null;
      int pageNumber=prorationExceptionsFilterVO.getDisplayPage();
    //added by A-5223 for ICRD-21098 starts
      StringBuilder rankQuery = new StringBuilder();
      rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_ROWNUM_QUERY);
	//added by A-5223 for ICRD-21098 ends
      String baseQuery = getQueryManager().getNamedNativeQueryString("mail.mra.defaults.findProrationExceptionsDetailsfirst");
      //added by A-5223 for ICRD-21098 starts
      rankQuery.append(baseQuery);
      if (prorationExceptionsFilterVO.getFromDate() != null){
    		rankQuery.append(" AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) >= TO_NUMBER(TO_CHAR((?)::DATE,'YYYYMMDD')) "); 
    		
    		}
    		if (prorationExceptionsFilterVO.getToDate() != null) {
    			rankQuery.append(" AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) <= TO_NUMBER(TO_CHAR((?)::DATE,'YYYYMMDD')) ");          
    		}
  		String baseQuery2 = getQueryManager().getNamedNativeQueryString("mail.mra.defaults.findProrationExceptionsDetailssecond");
        rankQuery.append(baseQuery2);
      PageableNativeQuery<ProrationExceptionVO> pgqry = 
			new MRAProrationExceptionsFilterQuery(new MRAProrationExceptionMapper(),prorationExceptionsFilterVO,rankQuery.toString());
      
    //added by A-5223 for ICRD-21098 ends
     /* Query qry = new MRAProrationExceptionsFilterQuery(prorationExceptionsFilterVO, baseQuery);
      PageableQuery<ProrationExceptionVO> pgqry = null;
      pgqry=new  PageableQuery<ProrationExceptionVO>(qry,new MRAProrationExceptionMapper());
      log.log(Log.INFO, "query-->> from sqldao1234" + qry);*/

      //log.log(Log.INFO, "detailsVOs in sql dao===>>>" + detailsVOs);
     /* if(detailsVOs!=null){
      for(ProrationExceptionVO prorationExceptionVO:detailsVOs){
    	  log.log(Log.INFO, "inside for loop sql dao===>>>" );
    	  Query query = getQueryManager().createNamedNativeQuery("mailtracking.mra.defaults.dsnroute");
          int index = 0;
          query.setParameter(++index, prorationExceptionVO.getCompanyCode());
          query.setParameter(++index, prorationExceptionVO.getDispatchNo());
          query.setParameter(++index, prorationExceptionVO.getConsDocNo());
          query.setParameter(++index, Integer.valueOf(prorationExceptionVO.getConsSeqNo()));
          query.setParameter(++index, prorationExceptionVO.getPoaCode());
          query.append("order by RTG.BLGBAS,RTG.CMPCOD,RTG.CSGDOCNUM,RTG.CSGSEQNUM,RTG.POACOD,RTG.RTGSERNUM");

          Collection<ProrationExceptionVO> prorationExceptionVOs =new ArrayList<ProrationExceptionVO>();
          prorationExceptionVOs = query.getResultList(new DSNRouteMapper());

          for(ProrationExceptionVO proExcepVO:prorationExceptionVOs){
        	  route = proExcepVO.getRoute();
          }
          prorationExceptionVO.setRoute(route);
          //log.log(Log.INFO, "prorationExceptionVO in sql dao===>>>" + prorationExceptionVO);
      }
      }*/

      return pgqry.getPage(pageNumber);
  }

 
  public Collection<ProrationExceptionVO>  printProrationExceptionReport(ProrationExceptionsFilterVO prorationExceptionsFilterVO)
      throws PersistenceException, SystemException
  {
			    StringBuilder rankQuery = new StringBuilder();
			    rankQuery.append("SELECT RESULT_TABLE.*, ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM (");

			    String baseQuery = getQueryManager().getNamedNativeQueryString("mail.mra.defaults.findProrationExceptionsDetailsfirst");
			
      rankQuery.append(baseQuery);
			      if (prorationExceptionsFilterVO.getFromDate() != null){
			    		rankQuery.append(" AND TRUNC(MST.RCVDAT) >= to_date(?, 'dd-MON-yyyy') "); 

          }
			    		if (prorationExceptionsFilterVO.getToDate() != null) {
			    			rankQuery.append("AND TRUNC(MST.RCVDAT) <= to_date(?, 'dd-MON-yyyy') ");
      }
			  		String baseQuery2 = getQueryManager().getNamedNativeQueryString("mail.mra.defaults.findProrationExceptionsDetailssecond");
			        rankQuery.append(baseQuery2);
			      
			    Query qry = new MRAProrationExceptionsFilterQuery(new MRAProrationExceptionMapper(), prorationExceptionsFilterVO, rankQuery.toString());
			    
      return qry.getResultList(new MRAProrationExceptionMapper());
  }

  public String prorateDSN(ProrationExceptionVO prorationExceptionVO)
      throws SystemException
  {
      log.entering("CLASS_NAME", "prorateDSN");
      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure("MRA_DEFAULTS_PRORATE");
      int index = 0;
      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
      String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");

      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
      burstProcedure.setParameter(++index, prorationExceptionVO.getCompanyCode());
      burstProcedure.setParameter(++index, prorationExceptionVO.getMailbagId());
      burstProcedure.setParameter(++index, prorationExceptionVO.getConsDocNo());
      burstProcedure.setParameter(++index, Integer.valueOf(prorationExceptionVO.getConsSeqNo()));
      burstProcedure.setParameter(++index, prorationExceptionVO.getPoaCode());
      burstProcedure.setParameter(++index, "Y");
      burstProcedure.setParameter(++index,prorationExceptionVO.getTriggerPoint());
      burstProcedure.setParameter(++index, logonAttributes.getUserId());
      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
      burstProcedure.setParameter(++index, prorationExceptionVO.getExceptionCode());
      burstProcedure.setParameter(++index, null);
      burstProcedure.setOutParameter(++index, SqlType.STRING);
      burstProcedure.execute();

      String outParameter = (String)burstProcedure.getParameter(index);

      log.exiting("CLASS_NAME", "prorateDSN");
      return outParameter;
  }

  /**
	 * @author A-3229
	 * @param dsnRoutingFilterVO
	 * @return  Collection<MailProrationLogVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<MailProrationLogVO> findProrationLogDetails(DSNFilterVO dsnFilterVO) throws  SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDPRORATIONLOGDETAILS);


		int index=0;
		query.setParameter(++index, dsnFilterVO.getCompanyCode());
		query.setParameter(++index, dsnFilterVO.getBlgBasis());
		//Removed by A-7794 as part of MRA revamp
		//query.setParameter(++index, dsnFilterVO.getPoaCode());
		//query.setParameter(++index, dsnFilterVO.getCsgDocumentNumber());
		//query.setParameter(++index, dsnFilterVO.getCsgSequenceNumber());

		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new ProrationLogMapper(dsnFilterVO));

	}

	/**
	 * @author A-3229
	 * @param ProrationFilterVO
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ProrationDetailsVO> viewProrationLogDetails(
		ProrationFilterVO prorationFilterVO) throws SystemException,
		PersistenceException {
	Query query = getQueryManager().createNamedNativeQuery(
			MRA_DEFAULTS_VIEWPRORATIONLOG);

	log.log(Log.INFO, "filter VO", prorationFilterVO);
	int index=0;
	query.setParameter(++index, prorationFilterVO.getCompanyCode());
	query.setParameter(++index, prorationFilterVO.getBillingBasis());
	query.setParameter(++index, prorationFilterVO.getPoaCode());
	//query.setParameter(++index, prorationFilterVO.getConsigneeDocumentNumber());
	query.setParameter(++index, prorationFilterVO.getSerialNumber());
	//query.setParameter(++index, prorationFilterVO.getConsigneeSequenceNumber()); //commented by A-7371 as part of ICRD-257532
	query.setParameter(++index,prorationFilterVO.getMailSquenceNumber());

	log.log(Log.INFO, "query-->>", query);
	log.log(Log.INFO, "FINAL VIEW PRORATION LOG QUERY IS----------->>", query.toString());
	return query.getResultList(new ProrationDetailsMapper(prorationFilterVO));

}
	/**
	 * @author A-3229
	 * @param ProrationFilterVO
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDFLOWNDTLS);
		int index=0;
		query.setParameter(++index, popUpVO.getCompanyCode());
		query.setParameter(++index, popUpVO.getBlgBasis());
		//query.setParameter(++index, popUpVO.getCsgdocnum());Commented by A-7929 for ICRD-257173 
		//query.setParameter(++index, popUpVO.getCsgseqnum());Commented by A-7929 for ICRD-257173 
		query.setParameter(++index, popUpVO.getPoaCode());
		//Added by A-7929 for ICRD-257173 starts
		if (popUpVO.getCsgdocnum() != null) {
			query.append("AND MST.CSGDOCNUM=?");
			query.setParameter(++index, popUpVO.getCsgdocnum());
		}
		
		if (popUpVO.getCsgseqnum() != 0) {
			query.append("AND MST.CSGSEQNUM=?");
			query.setParameter(++index, popUpVO.getCsgseqnum());
		}
		//Added by A-7929 for ICRD-257173 ends
		return query.getResultList(new ViewFlightSectorRevenueMapper());
	}

	/**
	 * to trigger flown and interline Provision accounting
	 * @author A-2565 Meenu Harry
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void triggerFlownAndInterlineProvAccounting(RateAuditVO newRateAuditVO) throws SystemException, PersistenceException {
		 log.entering("CLASS_NAME", "triggerFlownAndInterlineProvAccounting");
	      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure("mail.mra.defaults.triggerFlownAndInterlineProvAccounting");
	      int index = 0;
	      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	      burstProcedure.setSensitivity(true);
	      burstProcedure.setParameter(++index, newRateAuditVO.getCompanyCode());
	      burstProcedure.setParameter(++index, newRateAuditVO.getConDocNum());
	      burstProcedure.setParameter(++index, Integer.valueOf(newRateAuditVO.getConSerNum()));
	      burstProcedure.setParameter(++index, newRateAuditVO.getBillingBasis());
	      burstProcedure.setParameter(++index, newRateAuditVO.getGpaCode());
	      burstProcedure.setParameter(++index, logonAttributes.getUserId());
	      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
	      burstProcedure.setOutParameter(++index, SqlType.STRING);
	      burstProcedure.execute();

	      String outParameter = (String)burstProcedure.getParameter(index);
	      log
				.log(
						Log.INFO,
						"FINAL triggerFlownAndInterlineProvAccounting  outParameter----------->>",
						outParameter);
		log.exiting("CLASS_NAME", "triggerFlownAndInterlineProvAccounting");
	      //return outParameter;
	}

	/**
	 * @author A-3429
	 *
	 * @param listCCAFilterVo
	 * @return Collection<CCAdetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
public Collection<DocumentStatisticsDetailsVO> printDocumentStatisticsReport(
		DocumentStatisticsFilterVO statisticsFilterVO) throws SystemException,
		PersistenceException {
	Query qry = getQueryManager().createNamedNativeQuery(
			MRA_DEFAULTS_DOCUMENTSTATISTICS);

	int index = 0;
	qry.setParameter(++index, statisticsFilterVO.getCompanyCode());
	if (statisticsFilterVO.getCarrierIdentifier() > 0) {
		qry.append(" AND FLT.FLTCARIDR=?");
		qry.setParameter(++index, statisticsFilterVO.getCarrierIdentifier());
	}
	if (statisticsFilterVO.getFlightNo() != null
			&& statisticsFilterVO.getFlightNo().trim().length() > 0) {
		qry.append(" AND FLT.FLTNUM=?");
		qry.setParameter(++index, statisticsFilterVO.getFlightNo());
	}
	if (statisticsFilterVO.getFromDate() != null && statisticsFilterVO.getToDate()!=null) {
		qry.append(" AND TRUNC(FLT.FLTDAT) BETWEEN ? ");
		qry.setParameter(++index, statisticsFilterVO.getFromDate());
		qry.append(" AND  ? ");
		qry.setParameter(++index, statisticsFilterVO.getToDate());
	}else if(statisticsFilterVO.getFromDate() != null && statisticsFilterVO.getToDate()==null){
		qry.append(" AND TRUNC(FLT.FLTDAT) >= ? ");
		qry.setParameter(++index, statisticsFilterVO.getFromDate());
	}else if(statisticsFilterVO.getFromDate() == null && statisticsFilterVO.getToDate()!=null){
		qry.append(" AND TRUNC(FLT.FLTDAT) <= ? ");
		qry.setParameter(++index, statisticsFilterVO.getToDate());
	}
	qry.append(" GROUP BY FLT.FLTNUM,FLT.FLTDAT,R.RATAUD,T.TOORATAUD,A.ACCNTD,FLT.IMPCLSFLG,FLT.FLTCARCOD");
	log.log(Log.INFO, "query", qry);
	return qry.getResultList(new DocumentStatisticsReportMapper());
}


/**
 * Finds tand returns the GPA Billing entries available
 * This includes billed, billable and on hold despatches
 *
 * @param gpaBillingEntriesFilterVO
 * @return Collection<GPABillingDetailsVO>
 * @throws PersistenceException
 * @throws SystemException
 */
//TODO Collection<DocumentBillingDetailsVO>
public Page<DocumentBillingDetailsVO> findGPABillingEntriesOld(
        GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
throws PersistenceException,SystemException{
	StringBuilder rankQuery=new StringBuilder();
//	rankQuery.append("SELECT RESULT_TABLE.*,");
//	rankQuery.append("DENSE_RANK() OVER ( ORDER BY ");
	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
	rankQuery.append("RESULT_TABLE.CMPCOD, ");
	rankQuery.append("RESULT_TABLE.BLGBAS, ");
	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
	rankQuery.append("RESULT_TABLE.POACOD, ");
	rankQuery.append("RESULT_TABLE.INVNUM, ");
	rankQuery.append("RESULT_TABLE.SEQNUM, ");
	rankQuery.append("RESULT_TABLE.CCAREFNUM");
	rankQuery.append(") RANK ");
	rankQuery.append("FROM ( ");
	
	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINGDETAILS);
	String baseQuery=rankQuery.append(blgdtlQuery).toString();
	//modified by A-5175 for QF CR icrd-21098 starts
    //	Query query = new GPABillingDetailsFilterQuery(
    //	blgdtlQuery,  gpaBillingEntriesFilterVO );
	
	PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingDetailsFilterQuery(gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), baseQuery, gpaBillingEntriesFilterVO);
	
	// Added by A-8464 for ICRD-232381 begins
    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
    		  StringBuilder sbldr = new StringBuilder();
    		  sbldr.append(" AND MST.MALPERFLG = ");
    		  sbldr.append("'");
    		  sbldr.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
    	      sbldr.append("'");
    	      query.append(sbldr.toString());
    	    }
	// Added by A-8464 for ICRD-232381 ends
	
	//modified  by A-5175 for QF CR icrd-21098 ends
	Query c66dtlQuery = getQueryManager().createNamedNativeQuery(Find_C66GPABILLINGDETAILS);
	Query ccadtlQuery = getQueryManager().createNamedNativeQuery(Find_CCAGPABILLINGDETAILS);
	StringBuilder sbul = new StringBuilder();
	sbul.append("'");
	sbul.append( gpaBillingEntriesFilterVO.getCompanyCode());
	sbul.append("'");
	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null ) {
		sbul.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
		sbul.append(" AND ");
		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
		sbul.append(" ");
//		sbul.append("AND C66DTL.RCVDAT BETWEEN To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" AND To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" ");
//		sbul.append("AND MST.RCVDAT BETWEEN To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" AND To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" ");
	}
	/*if ( gpaBillingEntriesFilterVO.getToDate() != null  ) {
		
		sbul.append(" AND TRUNC(C66DTL.RCVDAT) <= '");
		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
		sbul.append("'");
	}*/
	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
		sbul.append( "AND C66DTL.BLGSTA =  ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getBillingStatus());
		sbul.append("'");
	}
	if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
			gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
		sbul.append( "AND C66DTL.GPACOD =  ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getGpaCode());
		sbul.append("'");
	}
	
	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null) {
		sbul.append(" AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
		sbul.append(" AND ");
		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
		sbul.append(" ");
		
	}
	
	
	//Added by A-4809 for Bug ICRD-17509 -->Starts
	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
		sbul.append("AND MST.CSGDOCNUM = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getConDocNumber());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
		sbul.append("AND MST.DSN = "); //fix for ICRD-282734
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getDsnNumber());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
		sbul.append("AND C66DTL.MALCTGCOD = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!= null &&
			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
		sbul.append("AND MST.ORGEXGOFC = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!= null &&
			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
		sbul.append("AND MST.DSTEXGOFC = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
		sbul.append("'");
	}
	//Added as part of ICRD-205027 starts
	if (gpaBillingEntriesFilterVO.getMailbagId()!= null &&
			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
		sbul.append("AND MST.MALIDR = ");    
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getMailbagId());
		sbul.append("'");
	}
	//Added as part of ICRD-205027 ends
	if (gpaBillingEntriesFilterVO.getMailSubclass()!= null &&
			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
		sbul.append("AND C66DTL.ACTSUBCLS = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getMailSubclass());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
		sbul.append("AND MST.RSN = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getRsn());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getHni()!= null &&
			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
		sbul.append("AND MST.HSN = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getHni());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
		sbul.append("AND MST.REGIND = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getRegInd());
		sbul.append("'");
	}
	if (gpaBillingEntriesFilterVO.getYear()!= null &&
			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
		sbul.append("AND MST.YER = ");
		sbul.append("'");
		sbul.append(gpaBillingEntriesFilterVO.getYear());
		sbul.append("'");
	}
	//Added by A-6991 for CR ICRD-137019 ...Starts
			if (gpaBillingEntriesFilterVO.getContractRate() != null
					&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
					&& gpaBillingEntriesFilterVO.getUPURate() == null) {
				sbul.append("AND C66DTL.RATTYP = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
				sbul.append("'");
			}
			if (gpaBillingEntriesFilterVO.getUPURate() != null
					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
					&& gpaBillingEntriesFilterVO.getContractRate() == null) {
				sbul.append("AND C66DTL.RATTYP = ");
				sbul.append("'");
				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
				sbul.append("'");
			}
			else if ((gpaBillingEntriesFilterVO.getUPURate() != null
					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
					&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
					.getContractRate().trim().length() > 0)) {
				sbul.append("AND C66DTL.RATTYP IN ");
				sbul.append("(").append("'");
				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
				sbul.append("'").append(",").append("'");
				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
				sbul.append("'").append(")");
			}
	//Added by A-6991 for CR ICRD-137019 ...Ends
	
		//Added by A-4809 for CR ICRD-258393....Starts
		if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
			sbul.append("AND MST.ORGCTYCOD = ");
			sbul.append("'");
			sbul.append(gpaBillingEntriesFilterVO.getOrigin());
			sbul.append("'");
		}
		if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
			sbul.append("AND MST.DSTCTYCOD = ");
			sbul.append("'");
			sbul.append(gpaBillingEntriesFilterVO.getDestination());
			sbul.append("'");	
		}
		//Added by A-4809 for CR ICRD-258393....Ends		
		
		// Added by A-8464 for ICRD-232381 begins
	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
	    	      sbul.append(" AND MST.MALPERFLG = ");
	    	      sbul.append("'");
	    	      sbul.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
	    	      sbul.append("'");
	    	    }
		// Added by A-8464 for ICRD-232381 ends
		
	log.log(Log.INFO, "sbul -->>", sbul.toString());
	c66dtlQuery.append(sbul.toString());
	log.log(Log.INFO, "c66dtlQuery", c66dtlQuery);
	query.append("UNION ALL");
	query.append(c66dtlQuery.toString());

	StringBuilder sb = new StringBuilder();
	sb.append("'");
	sb.append( gpaBillingEntriesFilterVO.getCompanyCode());
	sb.append("'");
	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null) {
		sb.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
		sb.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
		sb.append(" AND ");
		sb.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
		sb.append(" ");
		
	}
	/*if ( gpaBillingEntriesFilterVO.getToDate() != null  ) {
		sb.append(" AND TRUNC(MST.RCVDAT) <= '");
		sb.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
		sb.append("'");
	}*/

	if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
			gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
		sb.append( "AND CCADTL.REVGPACOD =  ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getGpaCode());
		sb.append("'");
	}
	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
		sb.append( "AND CCADTL.BLGSTA =  ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getBillingStatus());
		sb.append("'");
	}
	//Added by A-4809 for Bug ICRD-17509 -->Starts
	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){   
		//Modified by A-7794 as part of ICRD-251833
		sb.append("AND MST.CSGDOCNUM = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getConDocNumber());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
		//Modified by A-7794 as part of ICRD-251833
		sb.append("AND MST.DSN = ");  //fix for ICRD-282734
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getDsnNumber());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
		sb.append("AND MST.MALCTGCOD = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange() != null &&
			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
		sb.append("AND MST.ORGEXGOFC = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange() != null &&
			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
		sb.append("AND MST.DSTEXGOFC = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
		sb.append("'");
	}
	//Added as part of ICRD-205027 starts
	if (gpaBillingEntriesFilterVO.getMailbagId() != null &&
			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
		sb.append("AND MST.MALIDR = ");     
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getMailbagId());
		sb.append("'");
	}
	//Added as part of ICRD-205027 ends
	if (gpaBillingEntriesFilterVO.getMailSubclass() != null &&
			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
		//Modified by A-7794 as part of ICRD-251833
		sb.append("AND MST.MALSUBCLS = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getMailSubclass());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
		//Modified by A-7794 as part of ICRD-251833
		//Modified by A-8399 as part of ICRD-298070
		sb.append("AND MST.RSN = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getRsn());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getHni()!= null &&
			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
		sb.append("AND MST.HSN = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getHni());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
		sb.append("AND MST.REGIND = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getRegInd());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getYear()!= null &&
			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
		sb.append("AND MST.YER = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getYear());
		sb.append("'");
	}
	//Added by A-6991 for CR ICRD-137019 ...Starts
	if (gpaBillingEntriesFilterVO.getContractRate() != null
			&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
			&& gpaBillingEntriesFilterVO.getUPURate() == null) {
		sb.append("AND INNERCCADTL.RATTYP = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getContractRate());
		sb.append("'");
	}
	if (gpaBillingEntriesFilterVO.getUPURate() != null
			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
			&& gpaBillingEntriesFilterVO.getContractRate() == null) {
		sb.append("AND INNERCCADTL.RATTYP = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getUPURate());
		sb.append("'");
	}
	else if ((gpaBillingEntriesFilterVO.getUPURate() != null
			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
			&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
			.getContractRate().trim().length() > 0)) {
		sb.append("AND INNERCCADTL.RATTYP IN ");
		sb.append("(").append("'");
		sb.append(gpaBillingEntriesFilterVO.getUPURate());
		sb.append("'").append(",").append("'");
		sb.append(gpaBillingEntriesFilterVO.getContractRate());
		sb.append("'").append(")");
	}
	//Added by A-6991 for CR ICRD-137019 ...Ends
	//Added by A-4809 for Bug ICRD-17509 --->Ends
	//Added by A-4809 for CR ICRD-258393....Starts
	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
		sb.append("AND MST.ORGCTYCOD = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getOrigin());
		sb.append("'");
	}
	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
		sb.append("AND MST.DSTCTYCOD = ");
		sb.append("'");
		sb.append(gpaBillingEntriesFilterVO.getDestination());
		sb.append("'");	
	}   
	//Added by A-4809 for CR ICRD-258393....Ends
		// A-8164 For ICRD-256023
		if ((gpaBillingEntriesFilterVO.getContractRate() != null)
				&& (gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0)
				&& (gpaBillingEntriesFilterVO.getUPURate() == null)) {
			sb.append("AND INNERCCADTL.RATTYP = ");
			sb.append("'");
			sb.append(gpaBillingEntriesFilterVO.getContractRate());
			sb.append("'");
		}
		if ((gpaBillingEntriesFilterVO.getUPURate() != null)
				&& (gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0)
				&& (gpaBillingEntriesFilterVO.getContractRate() == null)) {
			sb.append("AND INNERCCADTL.RATTYP = ");
			sb.append("'");
			sb.append(gpaBillingEntriesFilterVO.getUPURate());
			sb.append("'");
		} else if ((gpaBillingEntriesFilterVO.getUPURate() != null)
				&& (gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0)
				&& (gpaBillingEntriesFilterVO.getContractRate() != null)
				&& (gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0)) {
			sb.append("AND INNERCCADTL.RATTYP IN ");
			sb.append("(").append("'");
			sb.append(gpaBillingEntriesFilterVO.getUPURate());
			sb.append("'").append(",").append("'");
			sb.append(gpaBillingEntriesFilterVO.getContractRate());
			sb.append("'").append(")");
		}
		
		// Added by A-8464 for ICRD-232381 begins
	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
	    	      sb.append(" AND MST.MALPERFLG = ");
	    	      sb.append("'");
	    	      sb.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
	    	      sb.append("'");
	    	    }
		// Added by A-8464 for ICRD-232381 ends
	    
	ccadtlQuery.append(sb.toString());

	query.append("UNION ALL");
	query.append(ccadtlQuery.toString());
	StringBuilder orderingString = new StringBuilder();
	//orderingString.append(" )GROUP BY CMPCOD,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,COD,INVNUM ");
	orderingString.append(" )GROUP BY CMPCOD,BLGBAS,COD,INVNUM )");
	//orderingString.append(" ) ORDER BY CMPCOD,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,INVNUM ");
	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);//added by A-5175 for cr icrd-21098
	query.append(orderingString.toString());
	log.log(Log.INFO, "final query -->>", query);
	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());
	//modified by A-5175 for QF CR icrd-21098 ends


}



/**
 * @author A-2391
 */
public int findMaxSeqnumFromMRABillingDetailsHistory(RateAuditDetailsVO rateAuditDetailsVO)throws SystemException,PersistenceException{
	Query query = getQueryManager().createNamedNativeQuery(
			MRA_DEFAULTS_FINDMAXSEQNUMFROMMRABILLINGDETAILSHISTORY);
	int index=0;
	query.setParameter(++index, rateAuditDetailsVO.getCompanyCode());
	query.setParameter(++index, rateAuditDetailsVO.getBillingBasis());
	//query.setParameter(++index, rateAuditDetailsVO.getMailSequenceNumber());
	//Modified by A-7794 as part of MRA revamp
	//query.setParameter(++index, rateAuditDetailsVO.getCsgSeqNum());
	//query.setParameter(++index, rateAuditDetailsVO.getGpaCode());

	Mapper<Integer> intMapper = getIntMapper("MAXNUM");
	log.exiting(CLASS_NAME, "findMaximumVersionNumber");
	return query.getSingleResult(intMapper).intValue();
}


/**
 * @author A-2107
 * @param unaccountedDispatchesFilterVO
 * @return UnaccountedDispatchesVO
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<UnaccountedDispatchesVO> getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
		throws SystemException, PersistenceException {
	Query qry = createUnaccountedDespatchTotalQuery(unaccountedDispatchesFilterVO);
	
	
	return qry.getResultList(new UnaccountedDispachesTotalMapper());
		
}



private Query createUnaccountedDespatchTotalQuery(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws SystemException {
	Query qry = null;
	String baseQueryForR1 = null;
	String baseQueryForR1WithExp = null;
	String baseQueryForR2 = null;
	if("R1".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
		baseQueryForR1 = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA);
		baseQueryForR1WithExp = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA_WITHEXP);
		qry = new UnaccountedDespatchCountFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2,unaccountedDispatchesFilterVO);
	}else if("R2".equalsIgnoreCase(unaccountedDispatchesFilterVO.getReasonCode())){
		baseQueryForR2 = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALULD);
		qry = new UnaccountedDespatchCountFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2,unaccountedDispatchesFilterVO);
	}else if("".equals(unaccountedDispatchesFilterVO.getReasonCode())){
		baseQueryForR1 = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA);
		baseQueryForR1WithExp = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALMRA_WITHEXP);
		baseQueryForR2 = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_UNACCNTED_SUMTOTALULD);
		qry = new UnaccountedDespatchCountFilterQuery(baseQueryForR1,baseQueryForR1WithExp,baseQueryForR2,unaccountedDispatchesFilterVO);
	}
	return qry;
}



/**
 * @author A-3434
 * @param cN66DetailsVO
 * @return MRABillingDetailsVO
 * @throws SystemException
 * @throws PersistenceException
 */
public MRABillingDetailsVO findGpaBillingDetails(CN66DetailsVO cN66DetailsVO)
throws SystemException,PersistenceException{
	Query query = getQueryManager().createNamedNativeQuery(
	MRA_DEFAULTS_FINDBLGDTLS);
	int index=0;
	query.setParameter(++index, cN66DetailsVO.getCompanyCode());
	query.setParameter(++index, cN66DetailsVO.getGpaCode());
	query.setParameter(++index, cN66DetailsVO.getSequenceNumber());
	query.setParameter(++index, cN66DetailsVO.getConsDocNo());
	query.setParameter(++index, cN66DetailsVO.getConsSeqNo());
	query.setParameter(++index, cN66DetailsVO.getBillingBasis());
	log.log(Log.INFO, "Query ---->>", query);
	log.exiting(CLASS_NAME, "findGpaBillingDetails");
	return query.getSingleResult(new BillingDetailsMapper());
}
/**
*
* @author A-3434
* Mapper class for BillingDetails
*
*/
private static class BillingDetailsMapper implements
		Mapper<MRABillingDetailsVO> {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 *
	 * @param rs
	 * @return MRABillingDetailsVO
	 * @throws SQLException
	 */
	public MRABillingDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("MRADefaultsSqlDAO", "map");
		MRABillingDetailsVO mraBillingDetailsVO = new MRABillingDetailsVO();
		mraBillingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		mraBillingDetailsVO.setPoaCode(rs.getString("POACOD"));
		log.log(Log.INFO, "mraBillingDetailsVO ---->>", mraBillingDetailsVO);
		return mraBillingDetailsVO;
	}
}





/**
 * @author A-3229
 * @param IrregularityFilterVO
 * @return Collection<IrregularityVO>
 * @throws SystemException
 * @throws PersistenceException
 */

public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)throws SystemException,PersistenceException{


	log.entering("MRADefaultsSqlDAO", "viewIrregularityDetails");


	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_VIEWIRREGULARITYDETAILS);
		int index=0;




		 query.setParameter(++index,filterVO.getToDate().toDisplayDateOnlyFormat());

		/*( if(filterVO.getSubSystem()!=null && filterVO.getSubSystem().trim().length() > 0){
				query.append(" AND IRP.SUBSYSTEM = ? ");
				query.setParameter(++index,filterVO.getSubSystem());
			}*/


		if(filterVO.getOffloadStation()!=null && filterVO.getOffloadStation().trim().length() > 0){
			query.append(" AND IRP.OFLSTN = ? ");
			query.setParameter(++index,filterVO.getOffloadStation());
		}
		if(filterVO.getIrpStatus()!=null && filterVO.getIrpStatus().trim().length() > 0){

			query.append(" AND IRP.IRPSTA = ? ");
			query.setParameter(++index,filterVO.getIrpStatus());
		}
		if(filterVO.getOrigin()!=null && filterVO.getOrigin().trim().length() > 0){
			query.append(" AND MST.ORGEXGOFC = ? ");
			query.setParameter(++index,filterVO.getOrigin());
		}
		if(filterVO.getDestination()!=null && filterVO.getDestination().trim().length() > 0){
			query.append(" AND MST.DSTEXGOFC = ? ");
			query.setParameter(++index,filterVO.getDestination());
		}
		if(filterVO.getFromDate()!=null  ){
				query.append(" AND IRP.ORGFLTDAT >= ? ");
				query.setParameter(++index,filterVO.getFromDate());
		}
		if(filterVO.getFromDate()!=null  && filterVO.getToDate()!=null ){
			query.append(" AND IRP.ORGFLTDAT <= ? ");
			query.setParameter(++index,filterVO.getToDate());

		}
		if(filterVO.getEffectiveDate()!=null ){
			query.append(" AND IRP.IRPDAT <= ? ");
			query.setParameter(++index,filterVO.getEffectiveDate());
		}
		if(filterVO.getBillingBasis()!=null && filterVO.getBillingBasis().trim().length()>0){
			query.append(" AND IRP.BLGBAS = ? ");
			query.setParameter(++index,filterVO.getBillingBasis());
		}
		if(filterVO.getCsgDocumentNumber()!=null && filterVO.getCsgDocumentNumber().trim().length()>0){
			query.append(" AND IRP.CSGDOCNUM = ? ");
			query.setParameter(++index,filterVO.getCsgDocumentNumber());
		}
		if( filterVO.getCsgSequenceNumber()>0){
			query.append(" AND IRP.CSGSEQNUM = ? ");
			query.setParameter(++index,filterVO.getCsgSequenceNumber());
		}
		if(filterVO.getPoaCode()!=null && filterVO.getPoaCode().trim().length()>0){
			query.append(" AND IRP.POACOD = ? ");
			query.setParameter(++index,filterVO.getPoaCode());
		}
		log.entering("MRADefaultsSqlDAO", "viewIrregularityDetails");
		return query.getResultList(new IrregularityDetailsMultiMapper(filterVO));


}

/**
 * @author A-3229
 * @param IrregularityFilterVO
 * @return Collection<IrregularityVO>
 * @throws SystemException
 * @throws PersistenceException
 */

public Collection<MRAIrregularityVO> printIrregularityReport(MRAIrregularityFilterVO filterVO)throws SystemException,PersistenceException{


	log.entering("MRADefaultsSqlDAO", "printIrregularityDetails");


	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_VIEWIRREGULARITYDETAILS);
		int index=0;




		 query.setParameter(++index,filterVO.getToDate().toDisplayDateOnlyFormat());

		/* if(filterVO.getSubSystem()!=null && filterVO.getSubSystem().trim().length() > 0){
				query.append(" AND IRP.SUBSYSTEM = ? ");
				query.setParameter(++index,filterVO.getSubSystem());
			}*/


		if(filterVO.getOffloadStation()!=null && filterVO.getOffloadStation().trim().length() > 0){
			query.append(" AND IRP.OFLSTN = ? ");
			query.setParameter(++index,filterVO.getOffloadStation());
		}
		if(filterVO.getIrpStatus()!=null && filterVO.getIrpStatus().trim().length() > 0){

			query.append(" AND IRP.IRPSTA = ? ");
			query.setParameter(++index,filterVO.getIrpStatus());
		}
		if(filterVO.getOrigin()!=null && filterVO.getOrigin().trim().length() > 0){
			query.append(" AND MST.ORGEXGOFC = ? ");
			query.setParameter(++index,filterVO.getOrigin());
		}
		if(filterVO.getDestination()!=null && filterVO.getDestination().trim().length() > 0){
			query.append(" AND MST.DSTEXGOFC = ? ");
			query.setParameter(++index,filterVO.getDestination());
		}
		if(filterVO.getFromDate()!=null  ){
				query.append(" AND IRP.ORGFLTDAT >= ? ");
				query.setParameter(++index,filterVO.getFromDate());
		}
		if(filterVO.getFromDate()!=null  && filterVO.getToDate()!=null ){
			query.append(" AND IRP.ORGFLTDAT <= ? ");
			query.setParameter(++index,filterVO.getToDate());

		}
		if(filterVO.getEffectiveDate()!=null ){
			query.append(" AND IRP.IRPDAT <= ? ");
			query.setParameter(++index,filterVO.getEffectiveDate());
		}
		if(filterVO.getBillingBasis()!=null && filterVO.getBillingBasis().trim().length()>0){
			query.append(" AND IRP.BLGBAS = ? ");
			query.setParameter(++index,filterVO.getBillingBasis());
		}
		if(filterVO.getCsgDocumentNumber()!=null && filterVO.getCsgDocumentNumber().trim().length()>0){
			query.append(" AND IRP.CSGDOCNUM = ? ");
			query.setParameter(++index,filterVO.getCsgDocumentNumber());
		}
		if( filterVO.getCsgSequenceNumber()>0){
			query.append(" AND IRP.CSGSEQNUM = ? ");
			query.setParameter(++index,filterVO.getCsgSequenceNumber());
		}
		if(filterVO.getPoaCode()!=null && filterVO.getPoaCode().trim().length()>0){
			query.append(" AND IRP.POACOD = ? ");
			query.setParameter(++index,filterVO.getPoaCode());
		}
		log.entering("MRADefaultsSqlDAO", "viewIrregularityDetails");
		return query.getResultList(new IrregularityDetailsMultiMapper(filterVO));


}


	/**
	 * @author A-3229
	 * @param popUpVO
	 * @return Collection<MRAAccountingVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException{
		log.entering("MRADefaultsSqlDAO", "findAccountingDetails--");
		/*StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDACCDETAILS);
		rankQuery.append(query);
		int index=0;
		query.setParameter(++index, popUpVO.getCompanyCode());
		query.setParameter(++index, popUpVO.getBlgBasis());
		query.setParameter(++index, popUpVO.getCsgdocnum());
		query.setParameter(++index, popUpVO.getCsgseqnum());
		query.setParameter(++index, popUpVO.getPoaCode());
		//PageableNativeQuery<MRAAccountingVO> pgqry = new PageableNativeQuery<MRAAccountingVO>(
				//rankQuery, new MRAAccountingDetailsMapper());
		log.log(Log.INFO, "query of findAccountingDetails ", query);
		
		PageableNativeQuery<MRAAccountingVO> pgqry = 
				new PageableNativeQuery<MRAAccountingVO>(popUpVO.getTotalRecordCount(),
						rankQuery.toString(),new MRAAccountingDetailsMapper());
		pgqry.append(BILLINGSITE_SUFFIX_QUERY);
		return pgqry.getPage(popUpVO.getPageNumber());*/
		
		String baseQuery = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_FINDACCDETAILS);
		StringBuilder rankQuery = new StringBuilder(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(baseQuery);
		PageableNativeQuery<MRAAccountingVO> pgqry = 
				new PageableNativeQuery<MRAAccountingVO>(popUpVO.getTotalRecordCount(),
						rankQuery.toString(),new MRAAccountingDetailsMapper());
		int index = 0;
		pgqry.setParameter(++index, popUpVO.getCompanyCode());
		pgqry.setParameter(++index, popUpVO.getBlgBasis());
		//pgqry.setParameter(++index, popUpVO.getCsgdocnum());Commented by A-7929 for ICRD-257173
		//pgqry.setParameter(++index, popUpVO.getCsgseqnum());Commented by A-7929 for ICRD-257173
		//Added by A-7929 for ICRD-257173 starts
		if (popUpVO.getCsgdocnum() != null) {
			pgqry.append("AND TXN.CSGDOCNUM=?");
		pgqry.setParameter(++index, popUpVO.getCsgdocnum());
		}
		
		if (popUpVO.getCsgseqnum() != 0) {
			pgqry.append("AND TXN.CSGSEQNUM=?");
		pgqry.setParameter(++index, popUpVO.getCsgseqnum());
		}
		//Added by A-7929 for ICRD-257173 ends
		//pgqry.setParameter(++index, popUpVO.getPoaCode());//Commented as part of ICRD-147818
		pgqry.append(BILLINGSITE_SUFFIX_QUERY);
		log.exiting("MRADefaultsSqlDAO", "findAccountingDetails");
		return pgqry.getPage(popUpVO.getPageNumber());


	}
	/**
	 * @author A-3229
	 * @param popUpVO
	 * @return Collection<USPSReportingVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDUSPSDETAILS);
		int index=0;
		query.setParameter(++index, popUpVO.getCompanyCode());
		query.setParameter(++index, popUpVO.getBlgBasis());
		query.setParameter(++index, popUpVO.getCsgdocnum());
		query.setParameter(++index, popUpVO.getCsgseqnum());
		query.setParameter(++index, popUpVO.getGpaCode());

		log.log(Log.INFO, "query of findUSPSReportingDetails ", query);
		return query.getResultList(new USPSReportingMapper());

	}

	/**
	 * added by Meenu for USPS accounting while processing Invoic ADV message
	 * @author A-2565
	 * @param masterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void triggerUSPSAccounting(MailInvoicMasterVO masterVO) throws SystemException, PersistenceException {
		 log.entering("CLASS_NAME", "triggerUSPSAccounting");
	      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure("mail.mra.defaults.triggerUSPSAccounting");
	      int index = 0;
	      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	      burstProcedure.setSensitivity(true);
	      burstProcedure.setParameter(++index, masterVO.getCompanyCode());
	      burstProcedure.setParameter(++index, masterVO.getPoaCode());
	      burstProcedure.setParameter(++index, masterVO.getInvoiceKey());
	      burstProcedure.setParameter(++index, logonAttributes.getUserId());
	      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
	      burstProcedure.setOutParameter(++index, SqlType.STRING);
	      burstProcedure.execute();

	      String outParameter = (String)burstProcedure.getParameter(index);
	      log.log(Log.INFO,
				"FINAL triggerUSPSAccounting  outParameter----------->>",
				outParameter);
		log.exiting("CLASS_NAME", "triggerUSPSAccounting");
	      //return outParameter;

	}


	/**
	 * @author A-3251
	 * @param dsnRoutingVOs
	 *
	 */
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException,PersistenceException {

		Procedure procedure = getQueryManager().createNamedNativeProcedure(MAILTRACKING_MRA_DEFAULTS_REPRORATEDSN);
		procedure.setSensitivity(true);
		int index = 0;
		String prtflag = "Y";
		DSNRoutingVO dSNRoutingVO = ((ArrayList<DSNRoutingVO>)dsnRoutingVOs).get(0);
		procedure.setParameter(++index, dSNRoutingVO.getCompanyCode());
		procedure.setParameter(++index, dSNRoutingVO.getMailbagId());
		procedure.setParameter(++index, dSNRoutingVO.getMailSequenceNumber());		
	//	procedure.setParameter(++index, dSNRoutingVO.getCsgSequenceNumber());
	//	procedure.setParameter(++index, dSNRoutingVO.getPoaCode());
		procedure.setParameter(++index, prtflag);
		procedure.setParameter(++index, dSNRoutingVO.getTriggerPoint());
		procedure.setParameter(++index, dSNRoutingVO.getLastUpdateUser());
		procedure.setParameter(++index, dSNRoutingVO.getLastUpdateTime());
		procedure.setParameter(++index,DSNRoutingVO.INVALID_PRORATEEXCEPTION);
		procedure.setParameter(++index,DSN_ROUTING_SCREEN);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE, "executed Procedure reProrateDSN --->>PKG_MRA_PRORAT.spr_prorat_dsn");
		String outParameter = (String) procedure.getParameter(index);
		log.log(Log.FINE, "reProrateDSN --- >>outParameter is ", outParameter);

		}

	/**
	 * @author A-3434
	 * @param outstandingBalanceVO
	 * @return Collection<OutstandingBalanceVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public  Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)
	throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDOUTSTANDINGBALANCES);
		int index=0;
		query.setParameter(++index, outstandingBalanceVO.getCompanycode());
		query.setParameter(++index, outstandingBalanceVO.getSubSystem());
		query.setParameter(++index, outstandingBalanceVO.getBillingBasis());
		query.setParameter(++index, outstandingBalanceVO.getConsignmentDocumentNumber());
		query.setParameter(++index, outstandingBalanceVO.getConsignmentSequenceNumber());
		query.setParameter(++index, outstandingBalanceVO.getPoaCode());

		log.log(Log.INFO, "query of findUSPSReportingDetails ", query);
		return query.getResultList(new OutstandingBalanceMapper());

	}
	
	/**
	 * 
	 * @author A-3429 calls accounting procedure
	 */
	public String performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs)
	throws SystemException,PersistenceException {
		
		//procedure.setSensitivity(true);
		int index = 0;
		/* pkg_mra_acc.SPR_MRA_UNACCOUNTED_DSN (
	      PI_CMPCOD      IN       CRAFLTSEG.CMPCOD%TYPE,
		  PI_BLGBAS      IN   MTKMRABLGMST.BLGBAS%TYPE,
		  PI_CSGDOCNUM   IN   MTKMRABLGMST.CSGDOCNUM%TYPE,
		  PI_CSGSEQNUM    IN   MTKMRABLGMST.CSGSEQNUM%TYPE,
		  PI_POACOD       IN   MTKMRABLGMST.POACOD%TYPE,
		  PI_SERNUM       IN   MTKMRABLGdtl.SERNUM%TYPE,
	      PI_SECORGCOD   IN       FLTOPRMST.FLTORG%TYPE,
	      PI_SECDSTCOD   IN       FLTOPRMST.FLTDST%TYPE,
	      PI_ORGCOD      IN       FLTOPRMST.FLTORG%TYPE,
		  PI_FLTNUM     IN       FLTOPRMST.FLTNUM%TYPE,
	      PI_FLTCARIDR   IN       FLTOPRMST.FLTCARIDR%TYPE,
	      PI_FLTDAT     IN       FLTOPRMST.FLTDAT%TYPE,   
	      PI_SEGSERNUM   IN MTKMRABLGDTL.SEGSERNUM%TYPE,
		  PI_FLTSEQNUM   IN MTKMRABLGDTL.FLTSEQNUM%TYPE,
		  PI_REASON      IN       SHRONETIM.FLDVAL%TYPE,
	      PI_LSTUPDDAT   IN       CRAACCTXN.LSTUPDTIM%TYPE,
	      PI_LSTUPDUSR   IN       CRAACCTXN.LSTUPDUSR%TYPE,
	      PO_STATUS      OUT      VARCHAR2
       )*/
	      String outParam="";
		for(UnaccountedDispatchesDetailsVO dispatchesDetailVO : unAccountedDSNVOs){
			Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_UNACCOUNTEDDESPATCHES_PROCEDURE);
			index = 0;
			log.log(Log.FINE, "index===>>>", index);
			procedure.setParameter(++index, dispatchesDetailVO.getCompanyCode());
			procedure.setParameter(++index, dispatchesDetailVO.getBilBase());
			procedure.setParameter(++index, dispatchesDetailVO.getCsgDocNum());
			procedure.setParameter(++index, Integer.parseInt(dispatchesDetailVO.getDsnSqnNo()));
			procedure.setParameter(++index, dispatchesDetailVO.getPostalCde());
			procedure.setParameter(++index, dispatchesDetailVO.getSerialNo());
			procedure.setParameter(++index, dispatchesDetailVO.getSectorFrom());
			procedure.setParameter(++index, dispatchesDetailVO.getSectorTo());
			procedure.setParameter(++index, dispatchesDetailVO.getOrigin());
			procedure.setParameter(++index, dispatchesDetailVO.getFlightNo());
			procedure.setParameter(++index, dispatchesDetailVO.getFlightCarrierId());
			procedure.setParameter(++index, dispatchesDetailVO.getFlightDate());
			procedure.setParameter(++index, dispatchesDetailVO.getSegmentSerialNo());
			procedure.setParameter(++index, dispatchesDetailVO.getFlightSeqNo());
			procedure.setParameter(++index, MRA_DEFAULTS_REASONCODE);
			LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			procedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());
			procedure.setParameter(++index, dispatchesDetailVO.getLastUpdateduser());
			procedure.setOutParameter(++index, SqlType.STRING);
			procedure.execute();
			log.log(Log.FINE, "executed Procedure UNACCOUNTED_DSN --->>SPR_MRA_UNACCOUNTED_DSN");
			String outParameter = (String) procedure.getParameter(index);
			log.log(Log.FINE, "UNACCOUNTED_DSN --- >>outParameter is ",
					outParameter);
			if(!MRA_DEFAULTS_OUTPARAMETER_OK.equals(outParameter)){
				outParam=outParameter;
				break;
			}else{
				outParam=outParameter;
			}
		}
		
		return outParam;
	}
	 /**
 	 * @author A-2391
 	 * @param gpaCode,cmpCode
 	 * @return Collection<FuelSurchargeVO>
 	 * @throws SystemException
 	 * @throws PersistenceException
 	 */
	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCode)
	 throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FUELSURCHARGEDETAILS);
		int index=0;
		query.setParameter(++index, cmpCode);
		query.setParameter(++index, gpaCode);
	
		log.log(Log.INFO, "query ", query);
		return query.getResultList(new FuelSurchargeMapper());

	}
	 /**
 	 * @author A-2391
 	 * @param fuelSurchargeVO
 	 * @return int
 	 * @throws SystemException
 	 * @throws PersistenceException
 	 */
 
 	public int findExistingFuelSurchargeVOs(FuelSurchargeVO fuelSurchargeVO)
 	throws SystemException,PersistenceException{
 		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FUELSURCHARGEDETAILS_COUNT);
		int index=0;
		query.setParameter(++index, fuelSurchargeVO.getCompanyCode());
		query.setParameter(++index, fuelSurchargeVO.getGpaCode());
		query.setParameter(++index, fuelSurchargeVO.getRateCharge());
		query.setParameter(++index, fuelSurchargeVO.getValidityStartDate());
		query.setParameter(++index, fuelSurchargeVO.getValidityEndDate());
	
		log.log(Log.INFO, "query ", query);
		fuelSurchargeVO= query.getSingleResult(new FuelSurchargeCountMapper());
		log.log(Log.INFO, "COUNT FRM DAO ", fuelSurchargeVO.getFuelCount());
		return fuelSurchargeVO.getFuelCount();
 	}
 	/**
 	*
 	* @author A-2391
 	* Mapper class for FuelSurchargeVO
 	*
 	*/
 	private static class FuelSurchargeCountMapper implements
 			Mapper<FuelSurchargeVO> {

 		private Log log = LogFactory.getLogger("MRA_DEFAULTS");

 		/**
 		 *
 		 * @param rs
 		 * @return FuelSurchargeVO
 		 * @throws SQLException
 		 */
 		public FuelSurchargeVO map(ResultSet rs) throws SQLException {
 			log.entering("MRADefaultsSqlDAO", "map");
 			FuelSurchargeVO vo=new FuelSurchargeVO();
 			 vo.setFuelCount(rs.getInt("CNT"));
 			 return vo;
 		}
 	}
 	
 	/**
 	 * @author A-2414
 	 * @param mailExceptionReportsFilterVo
 	 * @return String - file data
 	 * @throws SystemException
 	 * @throws PersistenceException
 	 * 
 	 * This is the method which:
 	 * 1. Calls the procedure to generate the report and store it as
 	 * 		a CLOB object in the DB.
 	 * 2. Executes the query to fetch the CLOB object from the DB.
 	 */
 	public String generateMailExceptionReport(MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
			throws SystemException, PersistenceException {

		log.entering("MRADefaultsSqlDAO", "generateMailExceptionReport");
		
		/*
		 * Going for the procedure call.
		 */
		Procedure generationProcedure = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_GENERATE_CSV_REPORT);
		generationProcedure.setSensitivity(true);

		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		log.log(Log.FINE, "Current Date: ", currentDate);
		generationProcedure.setParameter(++index, mailExceptionReportsFilterVo
				.getCompanyCode());
		generationProcedure.setParameter(++index, mailExceptionReportsFilterVo
				.getOwnerIdentifier());
		generationProcedure.setParameter(++index, mailExceptionReportsFilterVo
				.getReportType());
		generationProcedure.setParameter(++index, mailExceptionReportsFilterVo
				.getFromDate().toSqlDate());
		generationProcedure.setParameter(++index, mailExceptionReportsFilterVo
				.getToDate().toSqlDate());
		generationProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		generationProcedure.setOutParameter(++index, SqlType.STRING);
		generationProcedure.setOutParameter(++index, SqlType.INTEGER);
		generationProcedure.execute();

		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) generationProcedure.getParameter(7);
		log.log(Log.FINE, "outParameter is ", outParameter);
		if(!MRA_DEFAULTS_OUTPARAMETER_OK.equals(outParameter)){
			return MRA_DEFAULTS_RETURN_ERROR;
		}

		/*
		 * Retrieving the sequence number generated in the procedure
		 * for getting the report data by using the query.
		 */
		int seqnum = (Integer) generationProcedure.getParameter(index);
		
		/*
		 * Going for query execution.
		 */
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_GET_CSV_REPORT_DATA);
		index = 0;
		query.setParameter(++index, mailExceptionReportsFilterVo
				.getReportType());
		query.setParameter(++index, seqnum);
		query.setParameter(++index, mailExceptionReportsFilterVo
				.getCompanyCode());

		log.log(Log.INFO, "query ", query);
		String fileData = query.getSingleResult(new Mapper<String>() {

			private Log log = LogFactory.getLogger("MRA_DEFAULTS");

			/**
			 * 
			 * @param rs
			 * @return String
			 * @throws SQLException
			 */
			public String map(ResultSet rs) throws SQLException {
				log.entering("Mapper", "map");

				String csvReport = null;

				if (rs.getString("CSVRPT") != null) {
					csvReport = rs.getString("CSVRPT");
				}

				return csvReport;
			}
		});

		log.exiting("MRADefaultsSqlDAO", "generateMailExceptionReport");

		return fileData;

	}
 	
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @throws SystemException
	 * 
	 */
	public Page<CCAdetailsVO> findMCALov(CCAdetailsVO ccAdetailsVO,
			int displayPage) throws SystemException, PersistenceException {
		//Modified by A-5220 for ICRD-32647 starts
		String query = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FINDMCALOV);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(query);
		PageableNativeQuery<CCAdetailsVO> pageQuery = 
			new PageableNativeQuery<CCAdetailsVO>(0, rankQuery.toString(),
				new MCALovMapper());
		//Modified by A-5220 for ICRD-32647 ends
		int index=0;
		pageQuery.setParameter(++index, ccAdetailsVO.getCompanyCode());
		if(ccAdetailsVO.getCcaRefNumber()!=null && ccAdetailsVO.getCcaRefNumber().trim().length()>0){
			pageQuery.append(" AND CCADTL.MCAREFNUM=? ");//AND CCADTL.CCAREFNUM=?
			pageQuery.setParameter(++index, ccAdetailsVO.getCcaRefNumber());
		}
		if(ccAdetailsVO.getOriginCode()!=null && ccAdetailsVO.getOriginCode().trim().length()>0){
			pageQuery.append(" AND MST.ORGARPCOD=? ");//AND CCADTL.ORGCOD=?
			pageQuery.setParameter(++index, ccAdetailsVO.getOriginCode());
		}
		if(ccAdetailsVO.getDestnCode()!=null && ccAdetailsVO.getDestnCode().trim().length()>0){
			pageQuery.append(" AND MST.DSTARPCOD=? ");//AND CCADTL.DSTCOD
			pageQuery.setParameter(++index, ccAdetailsVO.getDestnCode());
		}
		if(ccAdetailsVO.getCategoryCode()!=null && ccAdetailsVO.getCategoryCode().trim().length()>0){
			pageQuery.append(" AND MST.MALCTGCOD=? ");//CCADTL.CATCOD
			pageQuery.setParameter(++index, ccAdetailsVO.getCategoryCode());
		}
		if(ccAdetailsVO.getSubClass()!=null && ccAdetailsVO.getSubClass().trim().length()>0){
			pageQuery.append(" AND MST.MALSUBCLS=? ");//CCADTL.SUBCLS
			pageQuery.setParameter(++index, ccAdetailsVO.getSubClass());
		}
		if(ccAdetailsVO.getYear()!=null && ccAdetailsVO.getYear().trim().length()>0){
			pageQuery.append(" AND MST.YER=? ");//SUBSTR(CCADTL.BLGBAS,16,1)
			pageQuery.setParameter(++index, Integer.parseInt(ccAdetailsVO.getYear()));
		}
		if(ccAdetailsVO.getDsnNo()!=null && ccAdetailsVO.getDsnNo().trim().length()>0){
			pageQuery.append(" AND MST.DSN=? ");//CCADTL.DSN
			pageQuery.setParameter(++index, ccAdetailsVO.getDsnNo());
		}
		pageQuery.append(" ORDER BY CCADTL.MCAREFNUM ");
		//Addded by A-5220 for ICRD-32647 starts
		pageQuery.append(MRA_DEFAULTS_SUFFIX_QUERY);
		//Addded by A-5220 for ICRD-32647 ends
		log.exiting(CLASS_NAME, "findMCALov");
		return pageQuery.getPage(displayPage);
	}
	/**
	 * 
	 */
	 
	public Page<CCAdetailsVO> findDSNLov(CCAdetailsVO ccAdetailsVO,
			int displayPage) throws SystemException, PersistenceException {
		int index=0;
		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<CCAdetailsVO> pgqry = null;
		String basequery = null;
   		basequery = getQueryManager().getNamedNativeQueryString(MRA_DEFAULTS_FINDDSNLOV);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(basequery);
		 String modifiedStr1 = null;
		    String modifiedStr2 = null;
		 if (isOracleDataSource()) {
		      modifiedStr1 = " AND TO_NUMBER(TO_CHAR(BLGMST.RCVDAT,'YYYYMMDD')) >= ? ";
		      modifiedStr2 = " AND TO_NUMBER(TO_CHAR(BLGMST.RCVDAT,'YYYYMMDD')) <= ? ";
		    } else {
		      modifiedStr1 = "AND TO_NUMBER(TO_CHAR(BLGMST.RCVDAT,'YYYYMMDD')) >= ?";
		      modifiedStr2 = "AND TO_NUMBER(TO_CHAR(BLGMST.RCVDAT,'YYYYMMDD')) <= ?";
		    }
		 
		// Added by A-5280 for ICRD-32647 ends
		pgqry = new PageableNativeQuery<CCAdetailsVO>(0,rankQuery.toString(),
				new DSNLovMapper());
		pgqry.setParameter(++index, ccAdetailsVO.getCompanyCode());
		if(ccAdetailsVO.getOriginCode()!=null && ccAdetailsVO.getOriginCode().trim().length()>0){
			/*Added By T-1925 Bug ICRD-16349*/
			if(ccAdetailsVO.getOriginCode().trim().length()>3){
				pgqry.append(" AND BLGMST.ORGEXGOFC =? ");
				pgqry.setParameter(++index, ccAdetailsVO.getOriginCode());
			}
			else{
				pgqry.append(" AND SUBSTR(BLGMST.ORGEXGOFC, 3, 3) =? ");
				pgqry.setParameter(++index, ccAdetailsVO.getOriginCode());	
			}
			//ICRD-16349 ENDS
		}
		if(ccAdetailsVO.getDestnCode()!=null && ccAdetailsVO.getDestnCode().trim().length()>0){
			/*Added By T-1925 Bug ICRD-16349*/
			if(ccAdetailsVO.getDestnCode().trim().length()>3){
				pgqry.append(" AND BLGMST.DSTEXGOFC =? ");
				pgqry.setParameter(++index, ccAdetailsVO.getDestnCode());
		}
			else{
				pgqry.append(" AND SUBSTR(BLGMST.DSTEXGOFC, 3, 3) =? ");
				pgqry.setParameter(++index, ccAdetailsVO.getDestnCode());	
			}
			//ICRD-16349 ENDS
		}
		if(ccAdetailsVO.getCategoryCode()!=null && ccAdetailsVO.getCategoryCode().trim().length()>0){
			pgqry.append(" AND BLGMST.MALCTGCOD=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getCategoryCode());
		}
		if(ccAdetailsVO.getSubClass()!=null && ccAdetailsVO.getSubClass().trim().length()>0){
			pgqry.append(" AND BLGMST.MALSUBCLS=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getSubClass());
		}
		if(ccAdetailsVO.getYear()!=null && ccAdetailsVO.getYear().trim().length()>0){
			pgqry.append(" AND  BLGMST.YER=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getYear());
		}
		if(ccAdetailsVO.getDsnNo()!=null && ccAdetailsVO.getDsnNo().trim().length()>0){
			pgqry.append(" AND BLGMST.DSN=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getDsnNo());
		}
		if(ccAdetailsVO.getRsn()!=null && ccAdetailsVO.getRsn().trim().length()>0){
			pgqry.append(" AND BLGMST.RSN=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getRsn());
		}
		if(ccAdetailsVO.getHni()!=null && ccAdetailsVO.getHni().trim().length()>0){
			pgqry.append(" AND BLGMST.HSN=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getHni());
		}
		if(ccAdetailsVO.getRegind()!=null && ccAdetailsVO.getRegind().trim().length()>0){
			pgqry.append(" AND BLGMST.REGIND=? ");
			pgqry.setParameter(++index, ccAdetailsVO.getRegind());
		}
		if(ccAdetailsVO.getBillingPeriodFrom()!=null &&ccAdetailsVO.getBillingPeriodFrom().trim().length()>0 )
		{
								
			 pgqry.append(modifiedStr1);
			LocalDate fromDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			fromDate.setDate(ccAdetailsVO.getBillingPeriodFrom());  
			pgqry.setParameter(++index, Integer.parseInt(fromDate.toStringFormat(DATE).substring(0, 8)));  
		}
		
		if(ccAdetailsVO.getBillingPeriodTo()!=null &&ccAdetailsVO.getBillingPeriodTo().trim().length()>0 )
		{
			pgqry.append(modifiedStr2);
			LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false); 
            toDate.setDate(ccAdetailsVO.getBillingPeriodTo());
           	pgqry.setParameter(++index, Integer.parseInt(toDate.toStringFormat(DATE).substring(0, 8)));                  
		}
		if(ccAdetailsVO.getCsgDocumentNumber()!=null && ccAdetailsVO.getCsgDocumentNumber().trim().length()>0){
			pgqry.append(" AND BLGMST.CSGDOCNUM =? ");
			pgqry.setParameter(++index, ccAdetailsVO.getCsgDocumentNumber());
		}
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends
		
		log.exiting(CLASS_NAME, "findDSNLov");
		return pgqry.getPage(displayPage);
	}
 	/**
 	 * 
 	 * @param flightValidationVO
 	 * @return Collection<MRABillingDetailsVO>
 	 * @throws SystemException
 	 * @throws PersistenceException
 	 */
 	public Collection<MRABillingDetailsVO> findBillingEntriesForFlight(FlightValidationVO flightValidationVO)
 	throws SystemException,PersistenceException{
 		//Collection<MRABillingDetailsVO> billingDetailsVO = null;
 		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FIND_BILLINGENTRIES_FLIGHT);
 		int index = 0;
 		query.setParameter(++index, flightValidationVO.getCompanyCode());
 		query.setParameter(++index,flightValidationVO.getFlightCarrierId());
 		query.setParameter(++index,flightValidationVO.getFlightNumber());
 		query.setParameter(++index,flightValidationVO.getFlightSequenceNumber());
 		return query.getResultList(new BillingEntryDetailsMapper());
 		
 	}
	/**
	 * 
	 */
	public Collection<CCAdetailsVO> findApprovedMCA (
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException,
			PersistenceException {
		Query query = null;
		query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDCCAREFDTLS);
		int index=0;
		query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());
		if(maintainCCAFilterVO.getDsnNumber()!=null
				&& maintainCCAFilterVO.getDsnNumber().trim().length()>0){
			int length = maintainCCAFilterVO.getDsnNumber().length();
			if(length == 4){
			query.append(" AND BLGMST.DSN= ? ");
			query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
		}
			else{
			query.append(" AND BLGMST.MALIDR= ? ");
			query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
			}
		}
		if(maintainCCAFilterVO.getConsignmentDocNum()!=null
				&& maintainCCAFilterVO.getConsignmentDocNum().trim().length()>0){
			query.append(" AND BLGMST.CSGDOCNUM = ? ");
			query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum());
		}
		if(maintainCCAFilterVO.getConsignmentSeqNum()!= 0){
			query.append(" AND BLGMST.CSGSEQNUM = ? ");
			query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());
		}
		//Modified by A-4809 for BUG ICRD-18489
		if(maintainCCAFilterVO.isApprovedMCAExists())
			{
			query.append("AND (CCADTL.MCASTA='A' )");
			
			}
		if(StringUtils.isBlank(maintainCCAFilterVO.getCcaReferenceNumber())){
			query.append("and CCADTL.revgpacod = BLGDTL.updbiltoopoa");
			}
		if(maintainCCAFilterVO.getDsnNumber()!=null
				&& maintainCCAFilterVO.getDsnNumber().trim().length()>0){
		//Modified by A-4809 for BUG ICRD-18489
			int length = maintainCCAFilterVO.getDsnNumber().length();
			if(length == 4){
				//Added by A-7794 as part of MRA revamp
     query.append("AND CCADTL.ISSDAT IN (SELECT MAX(ISSDAT) FROM MALMRAMCA,MALMRABLGMST BLGMST WHERE BLGMST.DSN = ? AND MALSEQNUM = BLGMST.MALSEQNUM AND CMPCOD = BLGMST.CMPCOD");
		query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
			}else{//Modified for ICRD-276942
                  query.append("AND CCADTL.ISSDAT IN (SELECT MAX(MCA.ISSDAT) FROM MALMRAMCA MCA,MALMRABLGMST BLGMST WHERE BLGMST.CMPCOD=MCA.CMPCOD AND BLGMST.MALSEQNUM=MCA.MALSEQNUM AND BLGMST.MALIDR = ? ");
				query.setParameter(++index, maintainCCAFilterVO.getDsnNumber());
			}
			if(maintainCCAFilterVO.isApprovedMCAExists())
				{
				query.append(" AND (MCASTA='A') )");
				}
			else
				query.append(")");
		}
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new CCACreateMapper());
 	}

	/**
	 * @author a-4823
	 * @param detailsVO
	 * @return String
	 * method to trigger proration on mca save
	 */
	public String triggerProration(CCAdetailsVO detailsVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "importFlownMails");
		Boolean flag = true;
		Collection<String> outParameters = new ArrayList<String>();

		String returnString = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String userId = logonAttributes.getUserId();
		String companyCode=logonAttributes.getCompanyCode();
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_DEFAULTS_TRIGGERPRORATION);
		procedure.setSensitivity(true);
		int index = 0;
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index,detailsVO.getMailbagId());
		procedure.setParameter(++index, detailsVO.getMailSequenceNumber());
		//procedure.setParameter(++index, detailsVO.getCsgSequenceNumber()); 
		//procedure.setParameter(++index, detailsVO.getPoaCode()); 
		procedure.setParameter(++index, "Y");
		procedure.setParameter(++index, "DR");
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setParameter(++index, detailsVO.getLastUpdateTime());
		procedure.setParameter(++index, "");		
		procedure.setParameter(++index, null);		
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
		log.log(Log.FINE, "----executed  Procedure----");
		returnString = (String) procedure.getParameter(index);
		log.log(Log.FINE, "---outParameter is -->", returnString);
		return returnString;
	}
	/**
	 * @author A-3429 calls accounting procedure
	 *
	 * @param detailsVO
	 * @return String
	 */
	public String triggerMCAFlownAccounting(CCAdetailsVO detailsVO)
	throws SystemException,PersistenceException {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_TRIGMCAACC_PROCEDURE);
			procedure.setSensitivity(true);
			index = 0;
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
			log.log(Log.FINE, "index===>>>", index);
			log.log(Log.FINE, "detailsVO==>>>", detailsVO);
			procedure.setParameter(++index, detailsVO.getCompanyCode());
			//Added by A-7794 as aprt of ICRD-221742
			if("I".equals(detailsVO.getCcaType())){
				procedure.setParameter(++index, "FMI");
			}else{
				procedure.setParameter(++index, "FMA");
			}
			if(detailsVO.getCsgDocumentNumber()!=null){
			procedure.setParameter(++index, detailsVO.getCsgDocumentNumber());
			}else{
				procedure.setParameter(++index, " ");  	
			}
			procedure.setParameter(++index,detailsVO.getCsgSequenceNumber());
			//Modified by A-7794 as part of MRA revamp
			procedure.setParameter(++index, detailsVO.getMailSequenceNumber());
			procedure.setParameter(++index, detailsVO.getRevGpaCode());
			procedure.setParameter(++index, detailsVO.getCcaRefNumber());
			procedure.setParameter(++index, logonAttributes.getUserId());
			LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			procedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());
			procedure.setOutParameter(++index, SqlType.STRING);
			procedure.execute();
		return (String) procedure.getParameter(index);
	}
	 /**
 	 * @author A-5166
     * calls accounting procedure
     * @param RoutingCarrierFilterVO
     * @throws SystemException
     */
	public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO)
 	throws SystemException,PersistenceException{
		Collection<RoutingCarrierVO> routingCarrierVOs = null;
 		Query query = getQueryManager().createNamedNativeQuery(
 				MRA_DEFAULTS_FINDROUTINGCARRIERDTL);
		int index=0;
		query.setParameter(++index, carrierFilterVO.getCompanyCode());
		if(carrierFilterVO.getOriginCity()!=null && carrierFilterVO.getOriginCity().trim().length()>0){
			query.append(" AND MALORGVAL = ? "); //Modified by A-7794 as part of ICRD-252154 -starts
			query.setParameter(++index, carrierFilterVO.getOriginCity());
		}	
		if(carrierFilterVO.getDestCity()!=null && carrierFilterVO.getDestCity().trim().length()>0){
			query.append(" AND MALDSTVAL = ? ");
			query.setParameter(++index, carrierFilterVO.getDestCity());
		}	
		if(carrierFilterVO.getCarrier()!=null && carrierFilterVO.getCarrier().trim().length()>0){
			query.append(" AND CARCOD = ? ");
			query.setParameter(++index, carrierFilterVO.getCarrier());
		}	
		if(carrierFilterVO.getValidFromDate()!=null){
			query.append(" AND VALFRM >= ? ");
			query.setParameter(++index, carrierFilterVO.getValidFromDate());
		}
		if(carrierFilterVO.getValidFromTo()!=null){
			query.append(" AND VALTOO <= ? ");
			query.setParameter(++index, carrierFilterVO.getValidFromTo());
		}
		if(carrierFilterVO.getOwnSectorFrm()!=null && carrierFilterVO.getOwnSectorFrm().trim().length()>0){
			query.append(" AND SECONEORGVAL = ? ");
			query.setParameter(++index, carrierFilterVO.getOwnSectorFrm());
		}
		if(carrierFilterVO.getOwnSectorTo()!=null && carrierFilterVO.getOwnSectorTo().trim().length()>0){
			query.append(" AND SECONEDSTVAL = ? ");
			query.setParameter(++index, carrierFilterVO.getOwnSectorTo());
		}
		if(carrierFilterVO.getOalSectorFrm()!=null && carrierFilterVO.getOalSectorFrm().trim().length()>0){
			query.append(" AND SECTWOORGVAL = ? ");
			query.setParameter(++index, carrierFilterVO.getOalSectorFrm());
		}
		if(carrierFilterVO.getOalSectorTo()!=null && carrierFilterVO.getOalSectorTo().trim().length()>0){
			query.append(" AND SECTWODSTVAL = ? "); //Modified by A-7794 as part of ICRD-252154 -end
			query.setParameter(++index, carrierFilterVO.getOalSectorTo());
		}
		log.log(Log.INFO, "query ", query);
		log.entering("MRADefaultsSqlDAO", "findRoutingCarrierDetails");      
		return query.getResultList(new RoutingCarrierDetailsMapper());
	}
	 /**
 	 * @author A-5166
     * Added for ICRD-17262 on 07-Feb-2013
     * @param RateLineFilterVO
     * @throws SystemException
     */
	public Collection<RateLineVO> findOverlapingRateLineDetails(RateLineFilterVO filterVO)
	throws SystemException,PersistenceException{
		log.entering("MRADefaultsSQLDAO", "findOverlapingRateLineDetails");
		int index=0;
		Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDOVERLAPRATELINEDETAILS);
		query.setParameter(++index, filterVO.getOrigin());
		query.setParameter(++index, filterVO.getDestination());
		query.setParameter(++index, filterVO.getCompanyCode());
		query.setParameter(++index, filterVO.getStartDate());
		query.setParameter(++index, filterVO.getEndDate());
		query.setParameter(++index, filterVO.getRateCardID());
		return query.getResultList(new RateLineDetailsMapper());
	}
	/**
	 * @author A-5166
	 *  Added for ICRD-36146 on 06-Mar-2013
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void initiateProration(String companyCode)   
	throws SystemException,PersistenceException{
		log.entering(CLASS_NAME, "initiateProration");
		String returnString = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String userId = logonAttributes.getUserId();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_DEFAULTS_INITIATEPRORATION);
		procedure.setSensitivity(true);
		int index = 0;
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index, userId);
		procedure.setParameter(++index, currentDate); 
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
		log.log(Log.FINE, "----executed  Procedure----");
		returnString = (String) procedure.getParameter(6);
		log.log(Log.FINE, "---outParameter is -->", returnString);		
	}
	
	  /**
  	 * Find billing site details.
  	 *
  	 * @param billingSiteFilterVO the billing site filter vo
  	 * @return the collection
  	 * @throws SystemException the system exception
  	 * @throws PersistenceException the persistence exception
  	 */
	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	  throws SystemException ,PersistenceException{
		Collection<BillingSiteVO> billingSiteVOs = null;
		int index=0;
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_BILLINGSITEMASTER);
		query.setParameter(++index,billingSiteFilterVO.getCompanyCode());
		query.setParameter(++index, billingSiteFilterVO.getBillingSiteCode());
		if(billingSiteFilterVO.getBillingSite()!=null &&
				billingSiteFilterVO.getBillingSite().trim().length()>0){
			query.append("AND BSTMST.BLGSITNAM=?");
			query.setParameter(++index, billingSiteFilterVO.getBillingSite());
		}
		
		return query.getResultList(new BillingSiteDetailsMultiMapper());
	}
	
	public Page<BillingSiteLOVVO> listParameterLov(
			BillingSiteLOVFilterVO filterVO) throws SystemException {
		log.entering("MRADefaultsSqlDAO", "ListParameterLov");
		
		int pageNumber = filterVO.getPageNumber();
		String query = null;
		PageableNativeQuery<BillingSiteLOVVO> pgqry = null;
		query = getQueryManager().getNamedNativeQueryString(
				MRA_DEFAULTS_FIND_BILLINGSITELOVS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(BILLINGSITE_ROWNUM_QUERY);
		rankQuery.append(query);
		
		pgqry = new PageableNativeQuery<BillingSiteLOVVO>(0,rankQuery.toString(),
				new BillingSiteDetailsMapper());
		pgqry.setParameter(1, filterVO.getCompanyCode().toUpperCase());
		if (filterVO.getBillingSiteCode() != null) {
			if (filterVO.getBillingSiteCode().trim().length() != 0) {

				StringBuffer holder=new StringBuffer("'").append(filterVO.getBillingSiteCode().trim().toUpperCase().trim()).append("%'");
				pgqry.append(" AND BLGSITCOD like").append(holder.toString().trim());

				}
		}
		if (filterVO.getBillingSite() != null && filterVO.getBillingSite().trim().length() > 0) {
			

				StringBuffer holder=new StringBuffer("'").append(filterVO.getBillingSite().trim().toUpperCase().trim()).append("%'");
				pgqry.append(" AND BLGSITNAM like").append(holder.toString().trim());

			
		}
		log.log(Log.INFO, "@@@@@@@@@@@@ the base query for LOV listing is ",
				pgqry.toString());
		 pgqry.append(BILLINGSITE_SUFFIX_QUERY);
		
		return pgqry.getPage(pageNumber);

	}
	
	public Collection<BillingSiteVO> findCountryOverLapping(BillingSiteVO billingSiteVO)
	throws SystemException,PersistenceException{
		int index=0;
		Query query =null;
		Collection<BillingSiteVO> billingSiteVOs = null;
		 query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FIND_COUNTRYDATEOVERLAP_COUNTRY);
		query.setParameter(++index,ContextUtils.getSecurityContext()
				.getLogonAttributesVO().getCompanyCode());
		if(billingSiteVO.getGpaCountry()!=null && billingSiteVO.getGpaCountry().trim().length()>0){
			 String temp=" AND BSTCNT.CNTCOD LIKE('%".concat(billingSiteVO.getGpaCountry().concat("%')"));
			query.append(temp).toString();
		}
		query.append(") BLGSIT").append("WHERE ('").append(billingSiteVO.getFromDate().toDisplayDateOnlyFormat().toString().trim())
		.append("' BETWEEN BLGSIT.FRMDAT AND BLGSIT.TOODAT) OR ('").append(billingSiteVO.getToDate().toDisplayDateOnlyFormat().toString().trim())
		.append("' BETWEEN BLGSIT.FRMDAT AND BLGSIT.TOODAT)");
		return query.getResultList(new BillingSiteOverlapMultiMapper());
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#updateFlightSegment(com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO)
	 *	Added by 			: a-4809 on Oct 22, 2014
	 * 	Used for 	:	ICRD-68924,TK specific
	 *  to update flight segment details for SAP CR
	 *	Parameters	:	@param dsnRoutingVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 */
	public void updateFlightSegment(DSNRoutingVO dsnRoutingVO)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "updateFlightSegment");
		String returnString =null; 
		int index=0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_DEFAULTS_UPDATEFLIGHTSEG);
		procedure.setSensitivity(true);
		procedure.setParameter(++index, dsnRoutingVO.getCompanyCode());
		procedure.setParameter(++index, dsnRoutingVO.getMailSequenceNumber());
		//procedure.setParameter(++index, dsnRoutingVO.getCsgSequenceNumber());
		//procedure.setParameter(++index, dsnRoutingVO.getPoaCode());
		//procedure.setParameter(++index, dsnRoutingVO.getBillingBasis());
		procedure.setParameter(++index, currentDate);
		procedure.setParameter(++index, dsnRoutingVO.getLastUpdateUser());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		returnString = (String) procedure.getParameter(6);
		log.log(Log.FINE, "---outParameter is -->", returnString);	
		log.exiting(CLASS_NAME, "updateFlightSegment");
	}
	/**
	 * Process billing matrix upload details.
	 *
	 * @param filterVO the filter vo
	 * @return the string
	 * @throws SystemException the system exception
	 */
	public String processBillingMatrixUploadDetails(FileUploadFilterVO filterVO)
    throws SystemException{
    log.entering(CLASS_NAME, "processBillingMatrixUploadDetails");
    String processStatus = null;
    if (filterVO != null) {
      int index = 0;
      Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_BILLINGMATRIXUPLOAD);
      index++; procedure.setParameter(index, filterVO.getCompanyCode());
      index++; procedure.setParameter(index, filterVO.getProcessIdentifier());
      index++; procedure.setParameter(index, filterVO.getFileName());
      index++; procedure.setOutParameter(index, SqlType.STRING);
      procedure.execute();
      processStatus = (String)procedure.getParameter(index);
    }
    log.log(Log.FINE, "ProcessStatus after executed Procedure -----> " + processStatus);
    this.log.exiting(CLASS_NAME, "processBillingMatrixUploadDetails");
    return processStatus;
	}
	/**
	 * @author A-6245 calls accounting procedure for internal MCA
	 *Added as part of ICRD-106057
	 * @param detailsVO
	 * @return String
	 */
	public String triggerMCAInternalAccounting(CCAdetailsVO detailsVO)
	throws SystemException,PersistenceException {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_TRIGINTMCAACC_PROCEDURE);
			procedure.setSensitivity(true);
			index = 0;
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
			log.log(Log.FINE, "index===>>>", index);
			log.log(Log.FINE, "detailsVO==>>>", detailsVO);
			procedure.setParameter(++index, detailsVO.getCompanyCode());
			procedure.setParameter(++index, "IA");
			//Modified by A-7794 as part of MRA revamp
			procedure.setParameter(++index, detailsVO.getMailSequenceNumber());
			if(detailsVO.getCsgDocumentNumber() != null){
			procedure.setParameter(++index, detailsVO.getCsgDocumentNumber());
			}else{
				procedure.setParameter(++index, " ");
			}
			procedure.setParameter(++index,detailsVO.getCsgSequenceNumber());
			procedure.setParameter(++index, detailsVO.getRevGpaCode());
			procedure.setParameter(++index, detailsVO.getCcaRefNumber());
			procedure.setParameter(++index, logonAttributes.getUserId());
			LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			procedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());
			procedure.setOutParameter(++index, SqlType.STRING);
			procedure.execute();
			String outParameter = (String) procedure.getParameter(index);
			log.log(Log.FINE, "OutParameter after executed Procedure -----> " + outParameter);
		return outParameter;
	}
	/**
	 * Added By A-6245 as part of ICRD-106032
	 * @param flightValidationVOs
	 */
	public void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
    throws SystemException{
    log.entering("MRADefaultsSqlDAO", "prorateExceptionFlights");
    LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
    LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
    for(FlightValidationVO flightValidationVO:flightValidationVOs){
    Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_PRORATEEXCEPTIONFLIGHT);
    	int index = 0;
    burstProcedure.setParameter(++index, flightValidationVO.getCompanyCode());
    burstProcedure.setParameter(++index, flightValidationVO.getFlightCarrierId());
    burstProcedure.setParameter(++index, flightValidationVO.getFlightNumber());
    burstProcedure.setParameter(++index, flightValidationVO.getFlightSequenceNumber());
    burstProcedure.setParameter(++index, logonAttributes.getUserId());
    burstProcedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());
    burstProcedure.setOutParameter(++index, SqlType.STRING);
    burstProcedure.execute();
    log.log(Log.FINE, "executed Procedure prorateExceptionFlights --->>PKG_MRA_PRORAT.SPR_MRA_PRORATEEXCEPTIONFLIGHT");
	String outParameter = (String) burstProcedure.getParameter(index);
	log.log(Log.FINE, "prorateExceptionFlights --- >>outParameter is-->> ", outParameter);
 }
    log.exiting("MRADefaultsSqlDAO", "prorateExceptionFlights");
  } 
	
	/**
	 * 
	 * @author A-5255
	 * @param prorationFilterVO
	 * @return
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(
			ProrationFilterVO prorationFilterVO)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_LISTSURCHARGEPRORATIONDETAILS);
		log.log(Log.INFO, "filter VO", prorationFilterVO);
		int index=0;
		query.setParameter(++index, prorationFilterVO.getCompanyCode());
		query.setParameter(++index, prorationFilterVO.getPoaCode());
		query.setParameter(++index, prorationFilterVO.getBillingBasis());	
		query.setParameter(++index, prorationFilterVO.getSerialNumber());	 
		if(prorationFilterVO.getConsigneeDocumentNumber()!=null){//modified by a-7531 for icrd 254289
			query.append(" AND MST.CSGDOCNUM = ? ");
		query.setParameter(++index, prorationFilterVO.getConsigneeDocumentNumber());
		}
		if(prorationFilterVO.getConsigneeSequenceNumber()>0)
		{
			query.append(" AND MST.CSGSEQNUM = ? ");
		query.setParameter(++index, prorationFilterVO.getConsigneeSequenceNumber());
		}
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new SurchargeProrationDetailsMapper(prorationFilterVO));
	//	return null;
	}
	/**
	 * 
	 * @author A-5255
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<SurchargeCCAdetailsVO> getSurchargeCCADetails(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException,
			PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_LISTSURCCAPRORATIONDETAILS);
		log.log(Log.INFO, "filter VO", maintainCCAFilterVO);
		int index = 0;
		query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());
		query.setParameter(++index, maintainCCAFilterVO.getBillingBasis());
		//query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum()); Commented by A-7929 for ICRD-ICRD-267635
		//query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());  Commented by A-7929 for ICRD-ICRD-267635
		//Commented by A-5945 for ICRD-146065
		//query.setParameter(++index, maintainCCAFilterVO.getPOACode());
		query.setParameter(++index, maintainCCAFilterVO.getCcaReferenceNumber());
		// query.setParameter(++index, prorationFilterVO.getSerialNumber());
		
		// Added by  A-7929 for ICRD-ICRD-267635 starts...
		if(maintainCCAFilterVO.getConsignmentDocNum()!= null){
			query.append("AND MST.CSGDOCNUM = ?");
			query.setParameter(++index,maintainCCAFilterVO.getConsignmentDocNum());
		}
		if(maintainCCAFilterVO.getConsignmentSeqNum()>0){
			query.append("AND MST.CSGSEQNUM = ?");
			query.setParameter(++index,maintainCCAFilterVO.getConsignmentSeqNum());	
		}
		// Added by  A-7929 for ICRD-ICRD-267635 ends...
		log.log(Log.INFO, "query-->>", query);
		 return query.getResultList(new SurchargeCCADetailsMapper(maintainCCAFilterVO));
		//return null;
	}
	/**
	 * 
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(
			CN51CN66FilterVO cn51CN66FilterVO) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_GPABILLING_LISTSURCCAPRORATIONDETAILS);
		log.log(Log.INFO, "filter VO", cn51CN66FilterVO);
		int index = 0;
		query.setParameter(++index, cn51CN66FilterVO.getCompanyCode());
		query.setParameter(++index, cn51CN66FilterVO.getInvoiceNumber());
		//query.setParameter(++index, cn51CN66FilterVO.getSequenceNumber());//comnmented for icrd-256997
		query.setParameter(++index, cn51CN66FilterVO.getMailSeqnum());//modified by a-7871 for ICRD-214766
		query.setParameter(++index, cn51CN66FilterVO.getBillingBasis());
		query.setParameter(++index, cn51CN66FilterVO.getGpaCode());
		if(cn51CN66FilterVO.getConsigneeSequenceNumber()>0){
			query.append(" AND MST.CSGSEQNUM = ? ");
		query.setParameter(++index,cn51CN66FilterVO.getConsigneeSequenceNumber()); 
		}
		if(cn51CN66FilterVO.getConsigneeDocumentNumber()!=null && !cn51CN66FilterVO.getConsigneeDocumentNumber().isEmpty()){
			query.append(" AND MST.CSGDOCNUM = ? ");
		query.setParameter(++index,cn51CN66FilterVO.getConsigneeDocumentNumber());
		}
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new SurchargeGPAC66MultiMapper(cn51CN66FilterVO));

	}
	/**
	 * 
	 * @author A-5255
	 * @param blgMatrixFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(
			BillingMatrixFilterVO blgMatrixFilterVO) throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_BILLINGMATRIXAUDIT);
		log.log(Log.INFO, "filter VO", blgMatrixFilterVO);
		int index = 0;
		query.setParameter(++index, blgMatrixFilterVO.getCompanyCode());
		query.setParameter(++index, blgMatrixFilterVO.getBillingMatrixId());
		 if (blgMatrixFilterVO.getTxnFromDate() != null) {
		      this.log.log(5, new Object[] { "FromDate", blgMatrixFilterVO.getTxnFromDate() });
		      query.append(" AND TRUNC(UPDTXNTIMUTC) >= ?");
		      query.setParameter(++index, blgMatrixFilterVO.getTxnFromDate().toCalendar());
		    }
		    if (blgMatrixFilterVO.getTxnToDate() != null) {
		      this.log.log(5, new Object[] { "Tooo", blgMatrixFilterVO.getTxnToDate() });
		      query.append(" AND TRUNC(UPDTXNTIMUTC) <= ?");
		      query.setParameter(++index, blgMatrixFilterVO.getTxnToDate().toCalendar());
		    }
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new BillingMatrixAuditMapper());
  } 
	/**
	 * 
	 * @author A-6245
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(
			CN51CN66FilterVO cn51CN66FilterVO) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_GPABILLING_LISTSURCHGBILLABLEDETAILS);
		Query mcaQuery = getQueryManager().createNamedNativeQuery(
				MRA_GPABILLING_LISTMCASURCHGBILLABLEDETAILS);
		log.log(Log.INFO, "filter VO", cn51CN66FilterVO);
		StringBuilder fullQuery=new StringBuilder();
		fullQuery.append(query);
		fullQuery.append(" UNION ").append(mcaQuery);
		if(cn51CN66FilterVO.getMcaNumber()!=null && !cn51CN66FilterVO.getMcaNumber().isEmpty()){
			fullQuery.append(" AND SURCHGCCA.MCAREFNUM = ? ");//mocified for icrd-256997
		}
		//fullQuery.append(" GROUP BY DTL.CMPCOD ,DTL.BLGBAS ,DTL.CSGDOCNUM ,DTL.CSGSEQNUM ,DTL.POACOD,BLG.APLRAT ");
		Query qry = getQueryManager().createNativeQuery(fullQuery.toString());
		int index = 0;
		qry.setParameter(++index, cn51CN66FilterVO.getCompanyCode());
		qry.setParameter(++index, cn51CN66FilterVO.getBillingBasis());
		qry.setParameter(++index, cn51CN66FilterVO.getGpaCode());
		/*qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeSequenceNumber());
		qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeDocumentNumber());*/
		qry.setParameter(++index, cn51CN66FilterVO.getCompanyCode());
		qry.setParameter(++index, cn51CN66FilterVO.getBillingBasis());
		//qry.setParameter(++index, cn51CN66FilterVO.getGpaCode());
		/*qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeSequenceNumber());
		qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeDocumentNumber());*/
		qry.setParameter(++index, cn51CN66FilterVO.getCompanyCode());
		qry.setParameter(++index, cn51CN66FilterVO.getBillingBasis());
		qry.setParameter(++index, cn51CN66FilterVO.getGpaCode());
		/*qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeSequenceNumber());
		qry.setParameter(++index,
				cn51CN66FilterVO.getConsigneeDocumentNumber());*/
		if(cn51CN66FilterVO.getMcaNumber()!=null && !cn51CN66FilterVO.getMcaNumber().isEmpty()){
			qry.setParameter(++index,
					cn51CN66FilterVO.getMcaNumber());
		}
		log.log(Log.INFO, "query-->>", qry);
		return qry.getResultList(new SurchargeBillableDetailsMapper(cn51CN66FilterVO));
  } 
	/**
	 * @author A-6245
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(
			ProrationFilterVO prorationFilterVO)throws SystemException,PersistenceException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_LISTSURCHARGEPRORATIONDETAILSFORMCA);
		log.log(Log.INFO, "filter VO", prorationFilterVO);
		int index=0;
		//Modified by A-7794 as part of MRA revamp
		query.setParameter(++index, prorationFilterVO.getCompanyCode());
		//query.setParameter(++index, prorationFilterVO.getMailSquenceNumber());
		query.setParameter(++index, prorationFilterVO.getBillingBasis());
		log.log(Log.INFO, "query-->>", query);
		return query.getResultList(new SurchargeProrationDetailsMapper(prorationFilterVO));
  } 
	/**
	 * @author A-6245
	 * @param companyCode
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws SystemException
	 */
	public String updateInterfacedMails(String companyCode,String fromDate,String toDate )
			throws SystemException,PersistenceException {
		Procedure procedure = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_INTERFACED_MAILS);
		int index = 0;
		LocalDate fromDateValue = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDateValue = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		fromDateValue.setDate(fromDate);
		toDateValue.setDate(toDate);
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index, fromDateValue);
		procedure.setParameter(++index, toDateValue);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) procedure.getParameter(3);
		log.log(Log.FINE, "outParameter is ", outParameter);
		return outParameter;
  } 

	/**
	 * @author A-7871
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public String activateBulkRatelines(RateLineVO ratelineVO) throws SystemException {
	
		log.log(Log.INFO, "filter VO", ratelineVO);
		int index=0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		 LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		 Procedure procedure = getQueryManager()
					.createNamedNativeProcedure(MRA_DEFUALTS_BULKACTIVATEUPURATES);
		 procedure.setParameter(++index, ratelineVO.getCompanyCode());
		 procedure.setParameter(++index, ratelineVO.getRateCardID());
		 procedure.setParameter(++index, logonAttributes.getUserId());
		 procedure.setParameter(++index, currentDate);
		 procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) procedure.getParameter(5);
		log.log(Log.FINE, "outParameter is ", outParameter);
		return outParameter;
  
}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#reRateMailbags(java.util.Collection)
	 *	Added by 			: A-7531 
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
		public String reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
				throws SystemException,PersistenceException {
			    StringBuilder processStatus= new StringBuilder("");
			    StringBuilder notRatedBlgBasis=new StringBuilder("");
				log.entering("MRADefaultsSQLDAO", "reRateMailbags");
				LocalDate currentDate = new LocalDate("***", Location.NONE, true);
				LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		    	  int count=0;
				for(DocumentBillingDetailsVO billingDetailVO : documentBillingDetailsVOs){
		    	  int index = 0;
		    	  Procedure prorateProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_PRORATE);
		    	  prorateProcedure.setParameter(++index, billingDetailVO.getCompanyCode());
		    	  prorateProcedure.setParameter(++index, billingDetailVO.getBillingBasis());
		    	  prorateProcedure.setParameter(++index, billingDetailVO.getMailSequenceNumber());		    	  
		    	  prorateProcedure.setParameter(++index, DocumentBillingDetailsVO.FLAG_YES);
		    	  prorateProcedure.setParameter(++index,MRAConstantsVO.TOBERERATED);
		    	  prorateProcedure.setParameter(++index, logonAttributes.getUserId());
		    	  prorateProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		    	  prorateProcedure.setParameter(++index, "");
		    	  prorateProcedure.setParameter(++index, billingDetailVO.getScreenID());
		    	  prorateProcedure.setOutParameter(++index, SqlType.STRING);
		    	  prorateProcedure.execute();
			      String outParameter = (String)prorateProcedure.getParameter(index);
			      log.log(Log.FINE, "outParameter after Re-rating "+billingDetailVO.getBillingBasis(), outParameter);
			      
			      
			      if(outParameter != null && (outParameter.contains(MRA_DEFAULTS_OUTPARAMETER_OK)
			    		  || !outParameter.contains("No UPU Rate")))
			      {
			    	  processStatus.append(COMPLETED).append(",");
			    	  //notRatedBlgBasis.append(" ");//commented as part of ICRD-277615
			    	 
			      }else
			      {    ++count;
			    	   processStatus.append(FAILED).append(",");
			    	   notRatedBlgBasis.append(billingDetailVO.getBillingBasis()).append(",");
			      }
		      }
				
				log.exiting("MRADefaultsSQLDAO", "reRateMailbags");
				if(count>10 && count!=0){//added as part of ICRD-277615
					notRatedBlgBasis=new StringBuilder().append(count);
				}else{
					notRatedBlgBasis=new StringBuilder().append(0);
				}
				return processStatus.append("-").append(notRatedBlgBasis).toString();
				
		}

/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findRerateBillableMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
		
	public Collection<DocumentBillingDetailsVO> findRerateBillableMails(
			DocumentBillingDetailsVO documentBillingVO, String companyCode)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_LISTBILLINGENTRIES);
			Collection<DocumentBillingDetailsVO> documentVO=new ArrayList<DocumentBillingDetailsVO>();
			log.log(Log.INFO, "filter VO",  documentBillingVO,companyCode);
			
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			
			int index=0;
			 LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
			query.setParameter(++index,companyCode);
		if ((!BLANK.equals(documentBillingVO.getFromDate())&&documentBillingVO.getFromDate()!=null && documentBillingVO.getFromDate().trim().length()>0)
				&& (!BLANK.equals(documentBillingVO.getToDate())&&documentBillingVO.getToDate()!=null && documentBillingVO.getToDate().trim().length()>0)) {//modified by A-7371 as part of ICRD-253017
			fromDate.setDate(documentBillingVO.getFromDate());
			toDate.setDate(documentBillingVO.getToDate());
			query.append("AND  TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD') ) BETWEEN ? AND ? ");
			query.setParameter(++index, Integer.valueOf(fromDate.toSqlDate().toString().replace("-", "")) );
			query.setParameter(++index, Integer.valueOf(toDate.toSqlDate().toString().replace("-", "")));	
	        }
		if (!BLANK.equals(documentBillingVO.getCategory())) {
				query.append("AND MST.MALCTGCOD = ?");
				query.setParameter(++index, documentBillingVO.getCategory());
			}
		if (!BLANK.equals(documentBillingVO.getSubClass())) {
				//query.append("AND MST.MALSUBCLS = ?");
				query.append("AND SUBCLS.SUBCLSGRP = ?");
				query.setParameter(++index, documentBillingVO.getSubClass());
			}
		if ("CNT".equals(documentBillingVO.getOrigin())
				&& "CNT".equals(documentBillingVO.getDestination())) {
				query.append("AND ORGOFC.CNTCOD = ?");
				query.append("AND DSTOFC.CNTCOD = ?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
		} else if ("CTY".equals(documentBillingVO.getOrigin())
				&& "CTY".equals(documentBillingVO.getDestination())) {
				query.append("AND ORGOFC.CTYCOD = ?");
				query.append("AND DSTOFC.CTYCOD = ?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
			}
		else if ("ARP".equals(documentBillingVO.getOrigin())
				&& "ARP".equals(documentBillingVO.getDestination())) {
				query.append("AND MST.ORGARPCOD = ?");
				query.append("AND MST.DSTARPCOD = ?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
			}
		
		
		if (!BLANK.equals(documentBillingVO.getGpaCode())) {//modified by A-7371 as part of ICRD-253017
				query.append("AND DTL.UPDBILTOOPOA = ?");
				query.setParameter(++index, documentBillingVO.getGpaCode());
			}
			
		if (documentBillingVO.getUpliftAirport() != null && !documentBillingVO.getUpliftAirport().isEmpty()
				&& documentBillingVO.getDischargeAirport() != null
				&& !documentBillingVO.getDischargeAirport().isEmpty()) {
			query.append("AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM  AND FLTCARIDR  = ? AND POL= ? )")
			.append(" AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM AND FLTCARIDR  = ?  AND POU = ? ) ");
			query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
			query.setParameter(++index,documentBillingVO.getUpliftAirport());
			query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
			query.setParameter(++index,documentBillingVO.getDischargeAirport());
		}
			
		if(documentBillingVO.getTransferAirline()!=null && !documentBillingVO.getTransferAirline().isEmpty()){
			query.append("AND MST.TRFCARCOD = ?");
			query.setParameter(++index, documentBillingVO.getTransferAirline());
		}
		if(documentBillingVO.getTransferPA()!=null && !documentBillingVO.getTransferPA().isEmpty()){
			query.append("AND MST.TRFPOACOD = ?");
			query.setParameter(++index, documentBillingVO.getTransferPA());
		}	
		
		if(documentBillingVO.getOriginOE()!=null && !documentBillingVO.getOriginOE().isEmpty()){
			query.append("AND MST.ORGEXGOFC = ?");
			query.setParameter(++index, documentBillingVO.getOriginOE());
		}
		
		if(documentBillingVO.getDestinationOE()!=null && !documentBillingVO.getDestinationOE().isEmpty()){
			query.append("AND MST.DSTEXGOFC = ?");
			query.setParameter(++index, documentBillingVO.getDestinationOE());
		}
			
			return query.getResultList(new GPABillingDetailsMultiMapper());
		}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findRerateInterlineBillableMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531 
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
	
public Collection<DocumentBillingDetailsVO> findRerateInterlineBillableMails(
				DocumentBillingDetailsVO documentBillingVO,String companyCode)
				throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_LISTINTERLINE);
			log.log(Log.INFO, "filter VO", documentBillingVO,companyCode);
			LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			
			int index=0;
			Collection<DocumentBillingDetailsVO> documentDetailsVOs=null;
			query.setParameter(++index,companyCode);
		if (((documentBillingVO.getFromDate()!=null))
				&& ((documentBillingVO.getToDate()!=null))) {
			fromDate.setDate(documentBillingVO.getFromDate());
			toDate.setDate(documentBillingVO.getToDate());
			query.append("AND  TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD') ) BETWEEN ? AND ? ");
			query.setParameter(++index, Integer.valueOf(fromDate.toSqlDate().toString().replace("-", "")) );
			query.setParameter(++index, Integer.valueOf(toDate.toSqlDate().toString().replace("-", "")));	

			}
		if (!BLANK.equals(documentBillingVO.getCategory())) {
				query.append("AND MST.MALCTGCOD = ?");
				query.setParameter(++index, documentBillingVO.getCategory());
			}
		if (!BLANK.equals(documentBillingVO.getSubClass())) {
				query.append("AND MST.MALSUBCLS = ?");
				query.setParameter(++index, documentBillingVO.getSubClass());
			}
		if ("CNT".equals(documentBillingVO.getOrigin())
				&& "CNT".equals(documentBillingVO.getDestination())) {
				query.append("AND ORGEXG.CNTCOD = ?");
				query.append("AND DSTEXG.CNTCOD=?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
		} else if ("CTY".equals(documentBillingVO.getOrigin())
				&& "CTY".equals(documentBillingVO.getDestination())) {
				query.append("AND ORGEXG.CTYCOD = ?");
				query.append("AND DSTEXG.CTYCOD = ?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
			}
		
		else if ("ARP".equals(documentBillingVO.getOrigin())
				&& "ARP".equals(documentBillingVO.getDestination())) {
				query.append("AND MST.ORGARPCOD = ?");
				query.append("AND MST.DSTARPCOD = ?");
			query.setParameter(++index,
					documentBillingVO.getOrgOfficeOfExchange());
			query.setParameter(++index,
					documentBillingVO.getDestOfficeOfExchange());
			}
		if (!BLANK.equals(documentBillingVO.getAirlineCode())) {
				query.append("AND DTL.UPDBILTOOPOA = ?");//modified by A-7371 as part of ICRD-253017
			    query.setParameter(++index, documentBillingVO.getAirlineCode());
			}
		
		if (documentBillingVO.getUpliftAirport() != null && !documentBillingVO.getUpliftAirport().isEmpty()
				&& documentBillingVO.getDischargeAirport() != null
				&& !documentBillingVO.getDischargeAirport().isEmpty()) {
			query.append("AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM  AND FLTCARIDR  = ? AND POL= ? )")
			.append(" AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM AND FLTCARIDR  = ?  AND POU = ? ) ");
			query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
			query.setParameter(++index,documentBillingVO.getUpliftAirport());
			query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
			query.setParameter(++index,documentBillingVO.getDischargeAirport());
		}
			
		if(documentBillingVO.getTransferAirline()!=null && !documentBillingVO.getTransferAirline().isEmpty()){
			query.append("AND MST.TRFCARCOD = ?");
			query.setParameter(++index, documentBillingVO.getTransferAirline());
		}
		if(documentBillingVO.getTransferPA()!=null && !documentBillingVO.getTransferPA().isEmpty()){
			query.append("AND MST.TRFPOACOD = ?");
			query.setParameter(++index, documentBillingVO.getTransferPA());
		}	
		
		if(documentBillingVO.getOriginOE()!=null && !documentBillingVO.getOriginOE().isEmpty()){
			query.append("AND MST.ORGEXGOFC = ?");
			query.setParameter(++index, documentBillingVO.getOriginOE());
		}
		
		if(documentBillingVO.getDestinationOE()!=null && !documentBillingVO.getDestinationOE().isEmpty()){
			query.append("AND MST.DSTEXGOFC = ?");
			query.setParameter(++index, documentBillingVO.getDestinationOE());
		}
			return query.getResultList(new InterLineBillingEntriesMultiMapper());
		}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findCCAStatus(com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO)
 *	Added by 			: A-7531 on 30-May-2017
 * 	Used for 	:
 *	Parameters	:	@param maintainCCAFilterVO
 *	Parameters	:	@return 
 */
@Override
	public String findCCAStatus(MaintainCCAFilterVO maintainCCAFilterVO)
			throws SystemException, PersistenceException {
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_CCASTATUS);
	int index=0;
	query.setParameter(++index, maintainCCAFilterVO.getCompanyCode());
	query.setParameter(++index, maintainCCAFilterVO.getBillingBasis());
	//query.setParameter(++index, maintainCCAFilterVO.getConsignmentDocNum());
	//query.setParameter(++index, maintainCCAFilterVO.getConsignmentSeqNum());
	query.setParameter(++index, maintainCCAFilterVO.getPOACode());
	Mapper<String> stringMapper = getStringMapper("MCASTA");//Modified by a-7531 for 253020
    return query.getSingleResult(stringMapper);
}

/**
 * 	Method		:	importMRAData
 *	Added by 	:	A-3429 on 04-Dec-2017
 * 	Used for 	:
 *	Parameters	:	@param rateAuditVO
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	public void saveMRADataForRatingJob(RateAuditVO rateAuditVO) throws SystemException, PersistenceException{
		  int index = 0;
		  RateAuditDetailsVO rateAuditDetailsVO = null;
		  if(rateAuditVO !=null && rateAuditVO.getRateAuditDetails()!=null){
		   rateAuditDetailsVO = ((ArrayList<RateAuditDetailsVO>) rateAuditVO.getRateAuditDetails()).get(0);
		  }
    	  Procedure importProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_SAVE_MRADATA_FORRATING);
    	  importProcedure.setSensitivity(true);
    	  LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
    	    LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
    	  importProcedure.setParameter(++index, rateAuditVO.getCompanyCode());
    	  //importProcedure.setParameter(++index, rateAuditVO.getMailbagId());
    	  importProcedure.setParameter(++index, rateAuditVO.getMailSequenceNumber());  
    	  
    	  if(rateAuditDetailsVO!=null && rateAuditDetailsVO.getCarrierid()!=0)
    			  {
    		  importProcedure.setParameter(++index, rateAuditDetailsVO.getCarrierid());
    			  }
    	  else{
    		  importProcedure.setParameter(++index, 0);  
    	  }
    	  
    	 	     	 
    	  if(rateAuditDetailsVO!=null&& rateAuditDetailsVO.getCarrierCode()!=null){
    		  importProcedure.setParameter(++index, rateAuditDetailsVO.getCarrierCode());
    	  }else{
    		  importProcedure.setParameter(++index, "");  
    	  }
    	  if(rateAuditDetailsVO!=null && rateAuditDetailsVO.getFlightno()!=null){
    		  importProcedure.setParameter(++index,rateAuditDetailsVO.getFlightno());
    	  }else{
    		  importProcedure.setParameter(++index, "");  
    	  }  
    	  if(rateAuditDetailsVO!=null && rateAuditDetailsVO.getFlightseqno()!=0){
    		  importProcedure.setParameter(++index,rateAuditDetailsVO.getFlightseqno()); 	
    	  }
    	  else{
    	  importProcedure.setParameter(++index,0); 	
    	  }
			if(rateAuditDetailsVO!=null && rateAuditDetailsVO.getSegSerNo()!=0){
				 importProcedure.setParameter(++index,rateAuditDetailsVO.getSegSerNo()); 
			}else{
		    	  importProcedure.setParameter(++index,0);
			}
    	  if(rateAuditDetailsVO!=null &&rateAuditDetailsVO.getFlightDate()!=null){
    		  if(rateAuditDetailsVO.getFlightDate().isTimePresent()){
    			  importProcedure.setParameter(++index,rateAuditDetailsVO.getFlightDate().toSqlTimeStamp());
    		  }else{
    		  importProcedure.setParameter(++index,rateAuditDetailsVO.getFlightDate().toSqlDate());//modified by a-7871 for ICRD-259926
    		  }
    	  }else{
    		  if(isOracleDataSource()) {
    		  importProcedure.setParameter(++index, "");  
    	      }
    	    else
    	    {
    	      importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE);  
    	    }
    	  }
    	  importProcedure.setParameter(++index,"ASYNC");
    	  //Added by A-7794 as part of ICRD-232299
    	  if(rateAuditVO.getTriggerPoint() != null && (rateAuditVO.getTriggerPoint().equals("TRA_MAL") || rateAuditVO.getTriggerPoint().equals("TRA_CON"))){
    	  importProcedure.setParameter(++index, "TRA");
    	  }else{
    	  importProcedure.setParameter(++index, "");  
    	  } 
    	  /*if(rateAuditDetailsVO.getUldno()!=null){
    		  importProcedure.setParameter(++index,rateAuditDetailsVO.getUldno());
    	  }else{
    		  importProcedure.setParameter(++index, "");  
    	  }  */   	  
    	  importProcedure.setParameter(++index,"");
    	  if(isOracleDataSource()) {
    	  importProcedure.setParameter(++index,"");
    	  }
    	  else
    	  {
    		  importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE);  
    	  }
    	 
    	  if(isOracleDataSource()) {
    	  importProcedure.setParameter(++index,"");
    	  }
    	  else
    	  {
    		  importProcedure.setNullParameter(++index, Types.TIMESTAMP_WITH_TIMEZONE); 
    	  }
    	  importProcedure.setParameter(++index, logonAttributes.getUserId());
    	  importProcedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());	    	  
    	  importProcedure.setOutParameter(++index, SqlType.STRING);
    	  importProcedure.execute();
	      String outParameter = (String)importProcedure.getParameter(index);
	      log.log(Log.FINE, "outParameter aFTER IMPORT CALL"+outParameter);
	
	}	
	/**
	 *	
	 *	Added by 			: A-3429 on 21-Dec-2017
	 * 	Used for 	:
	 *	Parameters	:	@param maintainCCAFilterVO
	 *	Parameters	:	@return 
	 */
	@Override
		public String validateAgent(RateAuditVO rateAuditVO, String agentType)
				throws SystemException, PersistenceException {
			Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_VALIDATE_AGENT);
		int index=0;
		if("GPA".equalsIgnoreCase(agentType)){
			query.setParameter(++index, "G");
		}else{
			query.setParameter(++index, "A");
		}
		query.setParameter(++index, rateAuditVO.getCompanyCode());
		query.setParameter(++index, rateAuditVO.getMailSequenceNumber());		
		Mapper<String> stringMapper = getStringMapper("ISVALID");
    return query.getSingleResult(stringMapper);
}
		
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findblgLinePars(java.lang.String)
	 *	Added by 			: A-7531 on 20-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param cmpcod
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 */
	@Override
	public Collection<BillingLineParameterVO> findblgLinePars(String cmpcod)
	throws SystemException, PersistenceException {
     Collection<BillingLineParameterVO> billingLineParVO = null;

    Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDBILLINGLINEPARAMETERS);
    int index = 0;
    index++; query.setParameter(index, cmpcod);
	return  query.getResultList(new Mapper<BillingLineParameterVO>() {

        public BillingLineParameterVO map(ResultSet rs) throws SQLException {
    		BillingLineParameterVO billingParameterVO = new BillingLineParameterVO();
    		billingParameterVO.setParameterCode(rs.getString("PARCOD"));
    		billingParameterVO.setParameterDesc(rs.getString("PARDES"));
			return billingParameterVO;
		}
    
    	
    });
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findReproarteMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531 on 09-Nov-2017
 * 	Used for 	:icrd-132487
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */

public Collection<DocumentBillingDetailsVO> findReproarteMails(
		DocumentBillingDetailsVO documentBillingVO)throws SystemException, PersistenceException
		{
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_PROCESS_BULKPRORATION);
	log.log(Log.INFO, "filter VO", documentBillingVO);  
	LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
	LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
	int index=0;
	Collection<DocumentBillingDetailsVO> documentDetailsVOs=null;
	query.setParameter(++index, documentBillingVO.getCompanyCode());
	
if ((!BLANK.equals(documentBillingVO.getFromDate()))
		&& (!BLANK.equals(documentBillingVO.getToDate()))) {

	fromDate.setDate(documentBillingVO.getFromDate());
	toDate.setDate(documentBillingVO.getToDate());
	query.setParameter(++index, Integer.valueOf(fromDate.toSqlDate().toString().replace("-", "")) );
	query.setParameter(++index, Integer.valueOf(toDate.toSqlDate().toString().replace("-", "")));	

	}

if(documentBillingVO.getProrateException()!= null &&!documentBillingVO.getProrateException().isEmpty()){
	query.append(" AND EXP.EXPCOD = ? ");
	query.setParameter(++index,documentBillingVO.getProrateException());

}

if(!BLANK.equals(documentBillingVO.getOrgOfficeOfExchange()))
{
	query.append("AND MST.ORGEXGOFC = ? ");
	query.setParameter(++index, documentBillingVO.getOrgOfficeOfExchange());
}
if(!BLANK.equals(documentBillingVO.getDestOfficeOfExchange()))
{
	query.append("AND MST.DSTEXGOFC = ? ");
	query.setParameter(++index, documentBillingVO.getDestOfficeOfExchange());
}
if (!BLANK.equals(documentBillingVO.getCategory())) {
	query.append("AND MST.MALCTGCOD = ?");
	query.setParameter(++index, documentBillingVO.getCategory());
}
if (!BLANK.equals(documentBillingVO.getSubClass())) {
	query.append("AND MST.MALSUBCLS = ?");
	query.setParameter(++index, documentBillingVO.getSubClass());
}


	if (documentBillingVO.getUpliftAirport() != null && !documentBillingVO.getUpliftAirport().isEmpty()
			&& documentBillingVO.getDischargeAirport() != null
			&& !documentBillingVO.getDischargeAirport().isEmpty()) {
		query.append("AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM  AND FLTCARIDR  = ? AND POL= ? )")
		.append(" AND EXISTS (SELECT 1 FROM MALMRARTG WHERE CMPCOD=MST.CMPCOD AND MALSEQNUM=MST.MALSEQNUM AND FLTCARIDR  = ?  AND POU = ? ) ");
		query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
		query.setParameter(++index,documentBillingVO.getUpliftAirport());
		query.setParameter(++index,logonAttributes.getOwnAirlineIdentifier());
		query.setParameter(++index,documentBillingVO.getDischargeAirport());
	}
	
	if(documentBillingVO.getTransferAirline()!=null && !documentBillingVO.getTransferAirline().isEmpty()){
		query.append("AND MST.TRFCARCOD = ?");
		query.setParameter(++index, documentBillingVO.getTransferAirline());
	}
	if(documentBillingVO.getTransferPA()!=null && !documentBillingVO.getTransferPA().isEmpty()){
		query.append("AND MST.TRFPOACOD = ?");
		query.setParameter(++index, documentBillingVO.getTransferPA());
	}

	if (documentBillingVO.getOriginAirport()!=null && !documentBillingVO.getOriginAirport().isEmpty()) {
	query.append("AND MST.ORGARPCOD = ?");
	query.setParameter(++index,documentBillingVO.getOriginAirport());
	}

	if (documentBillingVO.getDestinationAirport()!=null && !documentBillingVO.getDestinationAirport().isEmpty()) {
	query.append("AND MST.DSTARPCOD = ?");
	query.setParameter(++index,documentBillingVO.getDestinationAirport());
	}
	
	return query.getResultList(new BulkProrationMapper());
	
}
	


public String reProrateExceptionMails(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		throws SystemException,PersistenceException {
	    StringBuilder processStatus= new StringBuilder("");
	   
		log.entering("MRADefaultsSQLDAO", "reProrateExceptionMails");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		int countForCompleted=0;
		String outParameter=null;
		for(DocumentBillingDetailsVO billingDetailVO : documentBillingDetailsVOs){
			billingDetailVO.setScreenID("MRA040");
			
    	  int index = 0;
    	  Procedure prorateProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_PRORATE);
    	  prorateProcedure.setParameter(++index, billingDetailVO.getCompanyCode());
    	  prorateProcedure.setParameter(++index, billingDetailVO.getBillingBasis());
    	  prorateProcedure.setParameter(++index, billingDetailVO.getMailSequenceNumber());		    	  
    	  prorateProcedure.setParameter(++index, DocumentBillingDetailsVO.FLAG_YES);
    	  prorateProcedure.setParameter(++index,MRAConstantsVO.TOBEREPORATED);
    	  prorateProcedure.setParameter(++index, logonAttributes.getUserId());
    	  prorateProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
    	  prorateProcedure.setParameter(++index, billingDetailVO.getProrateException());
    	  prorateProcedure.setParameter(++index, billingDetailVO.getScreenID());
    	  prorateProcedure.setOutParameter(++index, SqlType.STRING);
    	  prorateProcedure.execute();
	       outParameter = (String)prorateProcedure.getParameter(index);
	      log.log(Log.FINE, "outParameter after Re-prorating "+billingDetailVO.getBillingBasis(), outParameter);
	      
	      
	      if(outParameter != null && outParameter.contains(MRA_DEFAULTS_OUTPARAMETER_OK))
	      
	      {
	    	  countForCompleted++;
	    	  processStatus.append(COMPLETED).append(",");
	    	  
	    	 
	      }else
	      {
	    	 
	    	   processStatus.append(FAILED).append(",");
	    	 
	      }
      }
		
		log.exiting("MRADefaultsSQLDAO", "reProrateExceptionMails");
		return processStatus.append("-").append(countForCompleted).toString();
		
}
/**
 * @author A-7371
 * Parameters	:	@throws SystemException
 * Parameters	:	@throws PersistenceException
 */
  public Collection<AWMProrationDetailsVO> viewAWMProrationDetails(
               ProrationFilterVO prorationFilterVO)throws SystemException,PersistenceException{  
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_LISTAWMPRORATIONDETAILS);
	int index=0;
	query.setParameter(++index, prorationFilterVO.getCompanyCode());
	query.setParameter(++index, prorationFilterVO.getPoaCode());
	query.setParameter(++index, prorationFilterVO.getMailSquenceNumber());	
	//Modified by A-7794 as part of ICRD-267369

        return query.getResultList(new AWMProrationDetailsMapper(prorationFilterVO));
}  
 /**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#generateMailBillingInterfaceFile()
 *	Added by 			:   A-7929 on 10-May-2018
 * 	Used for 	        :   ICRD-245605
 *	Parameters	        :	@throws SystemException
 */
public String generateMailBillingInterfaceFile(String regenerateFlag,String fileName,LocalDate fromDate, LocalDate toDate) throws SystemException {
	log.entering(CLASS_NAME, "generateMailBillingInterfaceFile");
	
	
	 Date currentDateFinal = null;
	 String currentDateInString = null;
	 Date fromDateFinal = null;
	 LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
	/*if(toDate == fromDate ){  
		toDate=fromDate.addDays(60);
	}*/
	 
	 /*if(toDate == null || toDate.equals("") ){
	  toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	 }*/
       //currentDateFinal = toDate.toSqlTimeStamp();
	  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
       currentDateInString = currentDate.toDisplayFormat("yyMMdd");  
       // currentDateInString = toDate.toDisplayFormat("yyMMdd");  
	 
	 
	 /*if(fromDate == null || fromDate.equals("")){
       fromDate = toDate.addDays(-60);
	 }*/
       fromDateFinal = fromDate.toSqlTimeStamp();
      
     
      
     
	  Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_MAILBILLING_INTERFACE);
	   int  index = 0;
	 
	   procedure.setParameter(++index, logonAttributes.getCompanyCode()); // company code
	   procedure.setParameter(++index, "MALBLG"); //fileType
	   procedure.setParameter(++index,fromDateFinal ); //from date
	   if(toDate == fromDate ){  
		toDate=fromDate.addDays(60);   
	    }
	   currentDateFinal = toDate.toSqlTimeStamp();
	   procedure.setParameter(++index,currentDateFinal ); // current date
	   procedure.setParameter(++index, logonAttributes.getUserName()); //last updated user
	   procedure.setParameter(++index,currentDateInString );
	   procedure.setParameter(++index,regenerateFlag );
		   procedure.setParameter(++index,fileName );
	   procedure.setOutParameter(++index, SqlType.STRING);  
	   
	   procedure.execute();
	 
	   log.log(Log.FINE, "executed Procedure");
	   String outParameter = (String) procedure.getParameter(index);// outParameter is filename
	   log.log(Log.FINE, "outParameter is ", outParameter);
	   log.exiting(CLASS_NAME, "generateMailBillingInterfaceFile");
	   return  outParameter;
	  
}

@Override
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findFlightrevenueDetails(java.lang.String, int)
 *	Added by 			: a-8061 on 29-Jun-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companycode
 *	Parameters	:	@param rowCount
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<FlightRevenueInterfaceVO> findFlightrevenueDetails(String companycode,boolean isFromRetrigger)
		throws SystemException, PersistenceException {
	
	
	log.entering(CLASS_NAME, "findFlightrevenueDetails");
	//Collection<ProrationDetailsVO> prorationDetailsVos = null;
	Query query = null;
	int index = 0;
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	
	query = getQueryManager().createNamedNativeQuery(
			MRA_DEFAULTS_FINDFLIGHTREVENUEDETAILS);
	query.setSensitivity(true);
	query.setParameter(++index, companycode);
	query.setParameter(++index, logonAttributes.getUserId());	
	query.setParameter(++index, companycode);
	if(isFromRetrigger){
	query.setParameter(++index, STATUS_FAILED);
	}else{
		query.setParameter(++index, STATUS_NEW);
	}
	query.setParameter(++index, companycode);
	//Added by A-7794 as part of ICRD-315107
	query.setParameter(++index, companycode);
	
	log.log(Log.FINE, "<--------------- QUERY --------------->", query);
	return query.getResultList(new FlightRevenueInterfaceMultimapper());
	  
}


		
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#updateTruckCost(com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailAWBVO)
 *	Added by 			: a-8061 on 17-Jul-2018
 * 	Used for 	:	ICRD-237070
 *	Parameters	:	@param truckOrderMailAWBVO
 *	Parameters	:	@throws SystemException
 */
public void updateTruckCost(TruckOrderMailAWBVO truckOrderMailAWBVO,TruckOrderMailVO truckOrderMailVO) throws SystemException {
		log.entering(CLASS_NAME, "updateTruckCost");
	
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	    Date currentDateFinal = currentDate.toSqlTimeStamp();
	      
		Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_UPDATE_TRUCKCOST);
		int  index = 0;
	 
		procedure.setParameter(++index,logonAttributes.getCompanyCode()); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getMasterDocumentNumber() ); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getDocumentOwnerId()); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getDuplicateNumber() ); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getSequenceNumber() ); 
		procedure.setParameter(++index,"");//mail sequence number 
		procedure.setParameter(++index,truckOrderMailVO.getFlightCarrierIdr()); 
		procedure.setParameter(++index,truckOrderMailVO.getFlightNumber()); 
		procedure.setParameter(++index,truckOrderMailVO.getFlightSequenceNumber()); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getSegmentSerialNumber()); 
		procedure.setParameter(++index,truckOrderMailVO.getTruckOrderNumber()); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getTotalTruckCharge().getAmount()); 
		procedure.setParameter(++index,0);//ICRD-290078 , always truck surcharge would be 0 . 
		procedure.setParameter(++index,truckOrderMailAWBVO.getTruckChargeVAT().getAmount()); 
		procedure.setParameter(++index,truckOrderMailAWBVO.getOtherChargeVAT().getAmount()); 
		procedure.setParameter(++index, truckOrderMailVO.getCurrencyCode()); 
		procedure.setParameter(++index, currentDateFinal); 
		procedure.setParameter(++index,logonAttributes.getUserId() ); 
		procedure.setParameter(++index,"TF"); 
		procedure.setParameter(++index,truckOrderMailVO.getTruckOrderStatus()); 
		procedure.setOutParameter(++index, SqlType.STRING); 
		procedure.execute();
	 
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) procedure.getParameter(index);
		log.log(Log.FINE, "outParameter is ", outParameter);
		log.exiting(CLASS_NAME, "updateTruckCost");
	 
	  
}
/**
 * @author A-8061
 * @param dsnRoutingVO
* Parameters	:	@throws SystemException
* Parameters	:	@throws PersistenceException
*/
public String validateBSA(DSNRoutingVO dsnRoutingVO)throws SystemException,PersistenceException {

	
	Query isBSAExistquery = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_ISBSAEXIST);
	int index=0;
	String bSACount="";
	boolean isBSAExist=false;
	String bsaValidationStatus="";
	String billingStatus="";

	isBSAExistquery.setParameter(++index, dsnRoutingVO.getCompanyCode());
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getFlightCarrierId());
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getFlightNumber() );	
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getPol() );	
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getPol() );	
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getPou() );
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getPou() );	
	isBSAExistquery.setParameter(++index,dsnRoutingVO.getDepartureDate().toSqlDate() );	
	isBSAExistquery.setParameter(++index, dsnRoutingVO.getBlockSpaceType());

	bSACount = isBSAExistquery.getSingleResult(getStringMapper("BSACNT"));
	
	if(bSACount!=null && ! bSACount.equals("") && ! bSACount.equals("0") ){
		isBSAExist= true ;
	}
	
	if(!isBSAExist){
		
		bsaValidationStatus="nobsaexist";
		return bsaValidationStatus;
		
	}

	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDBILLINGSTATUS);
	index=0;
	
	query.setParameter(++index, dsnRoutingVO.getCompanyCode());
	query.setParameter(++index,dsnRoutingVO.getFlightCarrierId());
	query.setParameter(++index,dsnRoutingVO.getMailSequenceNumber());
	query.setParameter(++index,dsnRoutingVO.getFlightNumber() );
	query.setParameter(++index,dsnRoutingVO.getFlightSeqnum() );
	//query.setParameter(++index,dsnRoutingVO.getPol() );	
	//query.setParameter(++index,dsnRoutingVO.getPou() );	
	if(dsnRoutingVO.getPol()!=null && dsnRoutingVO.getPol().trim().length()>0){
		query.append("AND DTL.SECFRM = ? ");
	query.setParameter(++index,dsnRoutingVO.getPol() );	
		
	}
	if(dsnRoutingVO.getPou()!=null && dsnRoutingVO.getPou().trim().length()>0){
		query.append(" AND DTL.SECTOO=? ");
	query.setParameter(++index,dsnRoutingVO.getPou() );	

	}
	billingStatus = query.getSingleResult(getStringMapper("BLGSTA"));
	
	if(billingStatus!=null && !"".equals(billingStatus) && "IB".equals(billingStatus)){
		bsaValidationStatus="inwardbilled";
	}
	else{
		bsaValidationStatus="validbsa";
	}
	return bsaValidationStatus;
}

@Override
public void updateMailBSAInterfacedDetails(BlockSpaceFlightSegmentVO blockSpaceFlightSegmentVO)
		throws SystemException, PersistenceException {
	Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_UPDATEBSA_INTERFACEFLAG);
	int  index = 0;
	
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	Date currentDateFinal = currentDate.toSqlTimeStamp();
	    
 
	procedure.setParameter(++index,blockSpaceFlightSegmentVO.getCompanyCode()); 
	procedure.setParameter(++index,blockSpaceFlightSegmentVO.getFlightCarrierId()); 
	procedure.setParameter(++index,blockSpaceFlightSegmentVO.getFlightNumber()); 
	procedure.setParameter(++index,blockSpaceFlightSegmentVO.getFlightSequenceNumber()); 
	procedure.setParameter(++index,logonAttributes.getUserId() ); 
	procedure.setParameter(++index,currentDateFinal); 
	procedure.setOutParameter(++index, SqlType.STRING); 
	
	procedure.execute();
 
	log.log(Log.FINE, "executed Procedure");
	String outParameter = (String) procedure.getParameter(index);
	
	
}
 


/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findBlockSpaceFlights(java.lang.String)
 *	Added by 			: a-8061 on 11-Sep-2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<CRAFlightFinaliseVO> findBlockSpaceFlights(String companyCode) throws SystemException, PersistenceException {

	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDBLOCKSPACEFLIGHTS);
	int index=0;
	
	query.setParameter(++index, companyCode);

	return  query.getResultList(new Mapper<CRAFlightFinaliseVO>() {
        public CRAFlightFinaliseVO map(ResultSet rs) throws SQLException {
        	CRAFlightFinaliseVO cRAFlightFinaliseVO = new CRAFlightFinaliseVO();
        	
        	cRAFlightFinaliseVO.setFlightNumber(rs.getString("FLTNUM"));
        	cRAFlightFinaliseVO.setFlightSeqNumber(rs.getInt("FLTSEQNUM"));
        	cRAFlightFinaliseVO.setFlightCarrierIdr(rs.getInt("FLTCARIDR"));
        	cRAFlightFinaliseVO.setSegSerialNumber(rs.getInt("SEGSERNUM"));
        	
			return cRAFlightFinaliseVO;
		}
    });
}
  /**
 * 
 * 	Method		:	MRADefaultsSqlDAO.prorateMCA
 *	Added by 	:	A-7929 on 25-Jun-2018
 * 	Used for 	:   ICRD-237091
 *	Parameters	:	@param ccaRefNo
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */

public void prorateMCA(String ccaRefNo,String currChangeFlag) throws SystemException {
	 log.entering(CLASS_NAME, "prorateMCA");
	 LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
	 
	 Procedure procedure = getQueryManager().createNamedNativeProcedure(MAL_MRA_PRORATE_MCA);
	 procedure.setSensitivity(true);// Added by A-8464 for ICRD-282762
	  int  index = 0;
	  procedure.setParameter(++index, logonAttributes.getCompanyCode()); // company code
	  procedure.setParameter(++index, ccaRefNo);   //mca number 
	  procedure.setParameter(++index, currChangeFlag);   //flag to identify the revised value ....added for ICRD-282931
	  procedure.setOutParameter(++index, SqlType.STRING);  
	  
	  procedure.execute();	 
	  log.log(Log.FINE, "executed Procedure");
	  String outParameter = (String) procedure.getParameter(index);
	  log.log(Log.FINE, "outParameter is ", outParameter);
	  log.exiting(CLASS_NAME, "prorateMCA");
}

 
  /**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findCarrierDetails(RoutingCarrierVO)
 *	Added by 			: A-7794 as part of ICRD-285543
 * 	Used for 	:
 *	Parameters	:	@param carrierVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 */
public RoutingCarrierVO findCarrierDetails(RoutingCarrierVO carrierVO) throws SystemException{
	int arlineIdr = 0;
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDCARRIERDETAILS);
	int index=0;
	query.setParameter(++index, carrierVO.getCompanyCode());
	query.setParameter(++index, carrierVO.getCarrier());
	arlineIdr = query.getSingleResult(getIntMapper("ARLIDR"));
	carrierVO.setCarrierIdr(arlineIdr);
	return carrierVO;
	
}

public Collection<DocumentBillingDetailsVO> findGPABillingEntriesForAutoMCA(
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
		throws PersistenceException,SystemException{

	StringBuilder rankQuery=new StringBuilder();
	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
	rankQuery.append("RESULT_TABLE.CMPCOD, ");
	rankQuery.append("RESULT_TABLE.BLGBAS, ");
	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
	rankQuery.append("RESULT_TABLE.POACOD, ");
	rankQuery.append("RESULT_TABLE.INVNUM, ");
	rankQuery.append("RESULT_TABLE.SEQNUM, ");
	rankQuery.append("RESULT_TABLE.CCAREFNUM");
	rankQuery.append(") RANK ");
	rankQuery.append("FROM ( ");
	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINENTRIES);
	String baseQuery=rankQuery.append(blgdtlQuery).toString();
	PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingEntriesFilterQuery(gpaBillingEntriesFilterVO.getDefaultPageSize(),gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), baseQuery, gpaBillingEntriesFilterVO);
	StringBuilder orderingString = new StringBuilder();
	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
	query.append(orderingString.toString());
	log.log(Log.INFO, "newquery -->>", query);
	return query.getResultList(new GPABillingDetailsMultiMapper());

		}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#importConsignmentDataToMRA(com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO)
 *	Added by 			: A-4809 on Nov 20, 2018
 * 	Used for 	:
 *	Parameters	:	@param mailInConsignmentVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO)
		throws SystemException, PersistenceException {
	log.entering("MRADefaultsSQLDAO", "importConsignmentDataToMra");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	String userId = logonAttributes.getUserId();
	Procedure importProcedure = getQueryManager().createNamedNativeProcedure(
			MRA_DEFAULTS_IMPORTCONSIGNMENTTOMRA);
	importProcedure.setSensitivity(true);
	int index = 0;
	importProcedure.setParameter(++index, mailInConsignmentVO.getCompanyCode());
	importProcedure.setParameter(++index, mailInConsignmentVO.getConsignmentNumber());
	importProcedure.setParameter(++index, mailInConsignmentVO.getConsignmentSequenceNumber());
	importProcedure.setParameter(++index, mailInConsignmentVO.getPaCode());
	importProcedure.setParameter(++index, mailInConsignmentVO.getMailSequenceNumber());
	importProcedure.setParameter(++index, mailInConsignmentVO.getMailSource());
	importProcedure.setParameter(++index, userId);
	importProcedure.setOutParameter(++index, SqlType.STRING);
	importProcedure.execute();
	String outParameter = (String) importProcedure.getParameter(index);
	log.log(Log.FINE, "outParameter is ", outParameter);
	log.exiting("MRADefaultsSQLDAO", "importConsignmentDataToMra");
}		
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findUSPSInternationalIncentiveJobDetails(java.lang.String)
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<USPSPostalCalendarVO> findUSPSInternationalIncentiveJobDetails(String companyCode)
		throws SystemException,PersistenceException{
	log.entering("MRADefaultsSQLDAO", "findUSPSInternationalIncentiveJobDetails");
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_USPS_INTERNATIONAL_INCENTIVE);
	int index=0;
	query.setParameter(++index, companyCode);
	query.setParameter(++index, companyCode);
	log.exiting("MRADefaultsSQLDAO", "findUSPSInternationalIncentiveJobDetails");
	return query.getResultList(new USPSIncentiveJobDetailsMapper());
}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#calculateUSPSIncentive(com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO)
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param uspsPostalCalendarVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public void calculateUSPSIncentive(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO)
		throws SystemException, PersistenceException {
	log.entering("MRADefaultsSQLDAO", "calculateUSPSIncentive");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	String userId = logonAttributes.getUserId();
	Procedure importProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_USPS_CALCULATE_INCENTIVE);
	importProcedure.setSensitivity(true);
	int index = 0;
	importProcedure.setParameter(++index, uspsPostalCalendarVO.getCompanyCode());
	importProcedure.setParameter(++index, uspsPostalCalendarVO.getGpacod());
	importProcedure.setParameter(++index, uspsPostalCalendarVO.getPeriodFrom());
	importProcedure.setParameter(++index, uspsPostalCalendarVO.getPeriodTo());
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	importProcedure.setParameter(++index, currentDate);
	importProcedure.setParameter(++index, userId);
	importProcedure.setParameter(++index, uspsIncentiveVO.getExcAmot());
	importProcedure.setParameter(++index, uspsIncentiveVO.getAccountingRequired());
	importProcedure.setOutParameter(++index, SqlType.STRING);
	importProcedure.execute();
	String outParameter = (String) importProcedure.getParameter(index);
	log.log(Log.FINE, "outParameter is ", outParameter);
	log.exiting("MRADefaultsSQLDAO", "calculateUSPSIncentive");
		}		
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findMailsFromCarditForImport(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO)
 *	Added by 			: A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<DocumentBillingDetailsVO> findMailsFromCarditForImport(DocumentBillingDetailsVO documentBillingDetailsVO)
throws SystemException, PersistenceException{
	log.entering("MRADefaultsSQLDAO", "findMailsFromCarditForImport");
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDMAILFROMCARDITSFORIMPORT);
	int index = 0;
	query.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
	if(documentBillingDetailsVO.getFlightNumber()!=null && documentBillingDetailsVO.getFlightNumber().trim().length()>0){
		query.append("AND RTG.FLTNUM LIKE (COALESCE (?, '%'))");
		query.setParameter(++index, documentBillingDetailsVO.getFlightNumber());
	}
	if(documentBillingDetailsVO.getFlightDate()!=null){
		query.append("AND TO_NUMBER(TO_CHAR((RTG.FLTDAT),'YYYYMMDD')) = COALESCE(?,TO_NUMBER(TO_CHAR((RTG.FLTDAT),'YYYYMMDD')))");
		query.setParameter(++index, documentBillingDetailsVO.getFlightDate().toSqlDate().toString().replace("-", ""));
	}
	if(documentBillingDetailsVO.getPoaCode()!=null && !documentBillingDetailsVO.getPoaCode().isEmpty()){
		query.append("AND RTG.POACOD LIKE (COALESCE (?, '%'))");
		query.setParameter(++index, documentBillingDetailsVO.getPoaCode());
	}
	if(documentBillingDetailsVO.getBillingBasis()!=null && !documentBillingDetailsVO.getBillingBasis().isEmpty()){
		query.append("AND MALMST.MALIDR LIKE (COALESCE (?, '%'))");  
		query.setParameter(++index, documentBillingDetailsVO.getBillingBasis());
	}
	if(documentBillingDetailsVO.getFromDate()!=null && !documentBillingDetailsVO.getFromDate().isEmpty()){
		query.append("AND TRUNC(MST.CSGDAT) >= COALESCE(to_date(?, 'dd-MON-yyyy') , TRUNC(MST.CSGDAT))");
		query.setParameter(++index, documentBillingDetailsVO.getFromDate());
	}
	if(documentBillingDetailsVO.getToDate()!=null && !documentBillingDetailsVO.getToDate().isEmpty()){
		query.append("AND TRUNC(MST.CSGDAT) <= COALESCE(to_date(?, 'dd-MON-yyyy'),TRUNC(MST.CSGDAT))");
		query.setParameter(++index, documentBillingDetailsVO.getToDate());
	}
	log.exiting("MRADefaultsSQLDAO", "findMailsFromCarditForImport");
	//return query.getResultList(new RateLineDetailsMapper());
	return query.getResultList(new MailFromCarditMapper());
}
		
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#importMailsFromCarditData(java.util.Collection)
 *	Added by 			: A-4809 on Dec 7, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public String importMailsFromCarditData(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		throws SystemException, PersistenceException{
	log.entering("MRADefaultsSQLDAO", "importMailsFromCarditData");
    StringBuilder processStatus= new StringBuilder("");
    StringBuilder notRatedBlgBasis=new StringBuilder("");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	String userId = logonAttributes.getUserId();
	  int count=0;
	  for(DocumentBillingDetailsVO documentBillingDetailsVO : documentBillingDetailsVOs){
	Procedure importProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_IMPORTCONSIGNMENTTOMRA);
	int index = 0;
			importProcedure.setSensitivity(true);
	importProcedure.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
	importProcedure.setParameter(++index, documentBillingDetailsVO.getConsignmentNumber());
	 if(isOracleDataSource()) {
	importProcedure.setParameter(++index, documentBillingDetailsVO.getConsignmentSeqNumber());
	 }
	 else
	 {
		 importProcedure.setParameter(++index, Integer.parseInt(documentBillingDetailsVO.getConsignmentSeqNumber()) );	 
	 }
	importProcedure.setParameter(++index, documentBillingDetailsVO.getPoaCode());
	importProcedure.setParameter(++index, documentBillingDetailsVO.getMailSequenceNumber());
	importProcedure.setParameter(++index, MailConstantsVO.PROCESS_MANAGER);
	importProcedure.setParameter(++index, userId);
	importProcedure.setOutParameter(++index, SqlType.STRING);
	importProcedure.execute();
			String outParameter = (String) importProcedure.getParameter(index);
			log.log(Log.FINE, "outParameter is ", outParameter);
			if(outParameter != null && outParameter.contains(MRA_DEFAULTS_OUTPARAMETER_OK)){
				processStatus.append(COMPLETED).append(",");
			}else{
				 ++count;
		    	  processStatus.append(FAILED).append(",");
		    	  notRatedBlgBasis.append(documentBillingDetailsVO.getBillingBasis()).append(",");
			}
	  }
	log.exiting("MRADefaultsSQLDAO", "importMailsFromCarditData");
		if(count>10 && count!=0){
			notRatedBlgBasis=new StringBuilder().append(count);
		}else{
			notRatedBlgBasis=new StringBuilder().append(0);
		}
		return processStatus.append("-").append(notRatedBlgBasis).toString();
}

/**
 * @author a-8061
 */
public Collection<MRABillingDetailsVO> findMRABillingDetails(MRABillingDetailsVO mRABillingDetailsVO)
		throws SystemException {
	
	log.entering(CLASS_NAME, "findMRABillingDetails");
	Query query = null;
	int index = 0;
	query = getQueryManager().createNamedNativeQuery(
			MRA_DEFAULTS_FINDMRABILLINGDETAILS);
	
	query.setParameter(++index, mRABillingDetailsVO.getCompanyCode());
	query.setParameter(++index, mRABillingDetailsVO.getMailSequenceNumber());	
	
	if(mRABillingDetailsVO.getBillTo()!=null&&mRABillingDetailsVO.getBillTo().trim().length()>0){
		query.append("AND BLGDTL.BILTOOPTYTYP = ? ");
		query.setParameter(++index,mRABillingDetailsVO.getBillTo());	
		
	}
	if(mRABillingDetailsVO.getPaymentFlag()!=null&&mRABillingDetailsVO.getPaymentFlag().trim().length()>0){
		query.append(" AND BLGDTL.PAYFLG=? ");
		query.setParameter(++index,mRABillingDetailsVO.getPaymentFlag());	
		
	}

	log.log(Log.FINE, "<--------------- QUERY --------------->", query);
	return query.getResultList(new BillingEntryDetailsMapper());
	
	
	
}


/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findFlightrevenueDetails(java.lang.String, int)
 *	Added by 			: a-8061 on 29-Jun-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companycode
 *	Parameters	:	@param rowCount
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<FlightRevenueInterfaceVO> findInterfaceDetails(String companycode,boolean isFromRetrigger)
			throws SystemException, PersistenceException {

		log.entering(CLASS_NAME, "findInterfaceDetails");

		Query query = null;
		int index = 0;

		query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDINTERFACEETAILS);

		query.setSensitivity(true);
		query.setParameter(++index, companycode);

		log.log(Log.FINE, "<--------------- QUERY --------------->", query);
		return query.getResultList(new InterfaceDetailsMultimapper());
	
}		

/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findGPABillingEntries(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
 *	Added by 			: A-8061 on 26-Sep-2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws PersistenceException
 *	Parameters	:	@throws SystemException
 */
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findMailbagExistInMRA(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO)
 *	Added by 			: A-4809 on Jan 2, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO)
		throws SystemException, PersistenceException {
	log.entering(CLASS_NAME, "findMailbagExistInMRA");
	Query query = null;
	int index = 0;
	query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDMAILBAGINMRA);
	query.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
	query.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
	query.setParameter(++index, documentBillingDetailsVO.getBillingBasis());
	return query.getSingleResult(getStringMapper("1"));
}		
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#listGPABillingEntries(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
 *	Added by 			: A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
		throws SystemException, PersistenceException {
	
	//Added for IASCB-21493 Begin
	
	StringBuilder rankQuery=new StringBuilder();
	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
	rankQuery.append("RESULT_TABLE.CMPCOD, ");
	rankQuery.append("RESULT_TABLE.BLGBAS, ");
	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
	rankQuery.append("RESULT_TABLE.POACOD, ");
	rankQuery.append("RESULT_TABLE.INVNUM, ");
	rankQuery.append("RESULT_TABLE.SEQNUM, ");
	rankQuery.append("RESULT_TABLE.CCAREFNUM");
	rankQuery.append(") RANK ");
	rankQuery.append("FROM ( ");
	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINENTRIES);
	String baseQuery=rankQuery.append(blgdtlQuery).toString();
	PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingEntriesFilterQuery(gpaBillingEntriesFilterVO.getDefaultPageSize(),gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), baseQuery, gpaBillingEntriesFilterVO);
	StringBuilder orderingString = new StringBuilder();
	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
	query.append(orderingString.toString());
	log.log(Log.INFO, "newquery -->>", query);
	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());

	//Added for IASCB-21493 End
	
	//Commented for IASCB-21493 Begin
	
//	StringBuilder rankQuery=new StringBuilder();
//	StringBuilder csgQueryPart = null;
//	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
//	rankQuery.append("RESULT_TABLE.CMPCOD, ");
//	rankQuery.append("RESULT_TABLE.BLGBAS, ");
//	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
//	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
//	rankQuery.append("RESULT_TABLE.POACOD, ");
//	rankQuery.append("RESULT_TABLE.INVNUM, ");
//	rankQuery.append("RESULT_TABLE.SEQNUM, ");
//	rankQuery.append("RESULT_TABLE.CCAREFNUM");
//	rankQuery.append(") RANK ");
//	rankQuery.append("FROM ( ");
//	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINGDETAILS);
//	String baseQuery=rankQuery.append(blgdtlQuery).toString();
//
//	PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingFilterQuery(gpaBillingEntriesFilterVO.getDefaultPageSize(),gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), baseQuery, gpaBillingEntriesFilterVO);
//	if(GPABillingEntriesFilterVO.FLAG_NO.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
//    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//    		  StringBuilder sbldr = new StringBuilder();
//    		  sbldr.append(" AND MST.MALPERFLG = ");
//    		  sbldr.append("'");
//    		  sbldr.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//    	      sbldr.append("'");
//    	      query.append(sbldr.toString());
//    	}
//    	    }
//	}
//	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
//			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
//		if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND MST.CSGDOCNUM IN ");
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getConDocNumber());
//			csgQueryPart.append(")");
//		}
//	}
//	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
//			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//		if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND MST.DSN IN "); //fix for ICRD-282734
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getDsnNumber());
//			csgQueryPart.append(")");
//		}else{
//			csgQueryPart.append("AND MST.DSN IN "); //fix for ICRD-282734
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getDsnNumber());
//			csgQueryPart.append(")");
//		}
//	}
//	if(gpaBillingEntriesFilterVO.getRateFilter()!=null 
//			&& gpaBillingEntriesFilterVO.getRateFilter().trim().length()>0){
//		if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND DTL.APLRAT IN "); 
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getRateFilter());
//			csgQueryPart.append(")");
//		}else{
//			csgQueryPart.append("AND DTL.APLRAT IN "); 
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getRateFilter());
//			csgQueryPart.append(")");			
//		}
//	}
//	if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
//		if(gpaBillingEntriesFilterVO.getGpaCode()!=null &&
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length()>0){
//		if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND DTL.UPDBILTOOPOA IN "); 
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
//			csgQueryPart.append(")");
//		}else{
//			csgQueryPart.append("AND DTL.UPDBILTOOPOA IN "); 
//			csgQueryPart.append("(");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
//			csgQueryPart.append(")");			
//		}			
//		}
//		if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
//				gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//		if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.ORGEXGOFC IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND MST.ORGEXGOFC IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//				csgQueryPart.append(")");			
//			}
//		}
//		if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
//				gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.DSTEXGOFC IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND MST.DSTEXGOFC IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//				csgQueryPart.append(")");			
//			}			
//		}
//		if(gpaBillingEntriesFilterVO.getMailCategoryCode()!=null &&
//				gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.MALCTGCOD IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND MST.MALCTGCOD IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//				csgQueryPart.append(")");
//			}				
//		}
//		if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
//				gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.MALSUBCLS IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND MST.MALSUBCLS IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
//				csgQueryPart.append(")");			
//			}				
//		}
//		if(gpaBillingEntriesFilterVO.getIsUSPSPerformed()!=null&&
//				gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length()>0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.MALPERFLG IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND MST.MALPERFLG IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//				csgQueryPart.append(")");			
//			}				
//		}
//	 if(gpaBillingEntriesFilterVO.getCurrencyCode()!=null &&
//			 gpaBillingEntriesFilterVO.getCurrencyCode().trim().length()>0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND DTL.CTRCURCOD IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getCurrencyCode());
//				csgQueryPart.append(")");
//			}else{
//				csgQueryPart.append("AND DTL.CTRCURCOD IN "); 
//				csgQueryPart.append("(");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getCurrencyCode());
//				csgQueryPart.append(")");			
//			}				
//		}
//	}else{
//		if(gpaBillingEntriesFilterVO.getGpaCode()!=null && 
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length()>0){
//			if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND DTL.UPDBILTOOPOA  = ");
//			csgQueryPart.append("'");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
//			csgQueryPart.append("'");			
//			}else{
//			csgQueryPart.append("AND DTL.UPDBILTOOPOA  = ");  
//			csgQueryPart.append("'");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getGpaCode());
//			csgQueryPart.append("'");
//			}			
//		}
//		if(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!=null &&
//				gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//			if(csgQueryPart==null){
//			csgQueryPart = new StringBuilder();
//			csgQueryPart.append("AND MST.ORGEXGOFC  = ");
//			csgQueryPart.append("'");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//			csgQueryPart.append("'");			
//			}else{
//			csgQueryPart.append("AND MST.ORGEXGOFC  = ");
//			csgQueryPart.append("'");
//			csgQueryPart.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//			csgQueryPart.append("'");
//			}
//		}
//		if(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null &&
//				gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.DSTEXGOFC  = ");
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//				csgQueryPart.append("'");				
//			}else{
//				csgQueryPart.append("AND MST.DSTEXGOFC  = ");
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//				csgQueryPart.append("'");
//				}
//		}
//		if(gpaBillingEntriesFilterVO.getMailCategoryCode()!=null &&
//				gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.MALCTGCOD   = ");
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//				csgQueryPart.append("'");				
//			}else{
//				csgQueryPart.append("AND MST.MALCTGCOD   = ");
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//				csgQueryPart.append("'");
//				}
//				}
//		}
//		if(gpaBillingEntriesFilterVO.getMailSubclass()!=null &&
//				gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//			if(csgQueryPart==null){
//				csgQueryPart = new StringBuilder();
//				csgQueryPart.append("AND MST.MALSUBCLS    = ");
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
//				csgQueryPart.append("'");				
//			}else{
//				csgQueryPart.append("AND MST.MALSUBCLS    = "); 
//				csgQueryPart.append("'");
//				csgQueryPart.append(gpaBillingEntriesFilterVO.getMailSubclass());
//				csgQueryPart.append("'");
//				}
//		}		
//	}
//	if(csgQueryPart!=null){
//		query.append(csgQueryPart.toString()); 
//	}
//	Query c66dtlQuery = getQueryManager().createNamedNativeQuery(Find_C66GPABILLINGDETAILS);
//	Query ccadtlQuery = getQueryManager().createNamedNativeQuery(Find_CCAGPABILLINGDETAILS);
//	StringBuilder sbul = new StringBuilder();
//	sbul.append("'");
//	sbul.append( gpaBillingEntriesFilterVO.getCompanyCode());
//	sbul.append("'");
//	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null ) {
//		sbul.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
//		sbul.append(" AND ");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
//		sbul.append(" ");
///*		sbul.append("AND C66DTL.RCVDAT BETWEEN To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" AND To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" ");
//		sbul.append("AND MST.RCVDAT BETWEEN To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" AND To_DATE(");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
//		sbul.append("', 'DD-MON-YYYY') ");
//		sbul.append(" ");*/
//	}
//
//	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
//			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
//		sbul.append( "AND C66DTL.BLGSTA =  ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getBillingStatus());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
//			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
//		sbul.append("AND MST.CSGDOCNUM IN ");
//		sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getConDocNumber());
//		sbul.append(")");
//	}
//	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
//			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//		sbul.append("AND MST.DSN IN "); //fix for ICRD-282734
//		sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getDsnNumber());
//		sbul.append(")");
//	}
//	if(gpaBillingEntriesFilterVO.getRateFilter()!=null 
//			&& gpaBillingEntriesFilterVO.getRateFilter().trim().length()>0){
//		sbul.append("AND C66DTL.APLRAT IN "); 
//		sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getRateFilter());
//		sbul.append(")");			
//	}	
//	if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
//		if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//			sbul.append( "AND C66DTL.GPACOD IN  ");
//			sbul.append("(");
//			sbul.append(gpaBillingEntriesFilterVO.getGpaCode());
//			sbul.append(")");
//		}		
//		if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!= null &&
//				gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//			sbul.append("AND MST.ORGEXGOFC IN ");
//			sbul.append("(");
//			sbul.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//			sbul.append(")");
//		}
//		if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!= null &&
//				gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//			sbul.append("AND MST.DSTEXGOFC IN ");
//			sbul.append("(");
//			sbul.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//			sbul.append(")");
//		}	
//	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			sbul.append("AND C66DTL.MALCTGCOD IN ");
//			sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//			sbul.append(")");
//		}	
//		if (gpaBillingEntriesFilterVO.getMailSubclass()!= null &&
//				gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//			sbul.append("AND C66DTL.ACTSUBCLS IN ");
//			sbul.append("(");
//			sbul.append(gpaBillingEntriesFilterVO.getMailSubclass());
//			sbul.append(")");
//		}	
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//	      sbul.append(" AND MST.MALPERFLG IN ");
//	      sbul.append("(");
//	      sbul.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//	      sbul.append(")");
//	   }
//	    if(gpaBillingEntriesFilterVO.getCurrencyCode()!=null && 
//	    		gpaBillingEntriesFilterVO.getCurrencyCode().trim().length()>0){
//			sbul.append("AND SMY.BLGCURCOD IN ");
//			sbul.append("(");
//			sbul.append(gpaBillingEntriesFilterVO.getCurrencyCode());
//			sbul.append(")");    	
//	    }	    
//	    if(GPABillingEntriesFilterVO.FLAG_NO.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
//	    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
//	    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
//				sbul.append("AND C66DTL.MCAREFNUM IN ");
//				sbul.append("(");
//				sbul.append(gpaBillingEntriesFilterVO.getMcaNumber());
//				sbul.append(")");   	    		
//	    	}
//	    }else if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
//	    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
//	    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
//				sbul.append("AND (C66DTL.MCAREFNUM is null OR C66DTL.MCAREFNUM IN ");
//				sbul.append("(");
//				sbul.append(gpaBillingEntriesFilterVO.getMcaNumber());
//				sbul.append("))"); 	    		
//	}else{
//	    		sbul.append("AND C66DTL.MCAREFNUM IS NULL");
//	    	}
//	    }
//	}else{
//		if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//			sbul.append( "AND C66DTL.GPACOD =  ");
//			sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getGpaCode());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!= null &&
//			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//		sbul.append("AND MST.ORGEXGOFC = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!= null &&
//			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//		sbul.append("AND MST.DSTEXGOFC = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//		sbul.append("'");
//	}
//		if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//				gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
//			sbul.append("AND C66DTL.MALCTGCOD = ");
//		sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//		sbul.append("'");
//			}
//	}
//	if (gpaBillingEntriesFilterVO.getMailSubclass()!= null &&
//			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//		sbul.append("AND C66DTL.ACTSUBCLS = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getMailSubclass());
//		sbul.append("'");
//	}
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//	    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//	    	      sbul.append(" AND MST.MALPERFLG = ");
//	    	      sbul.append("'");
//	    	      sbul.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//	    	      sbul.append("'");
//	    	}
//	   }
//	}
//	if (gpaBillingEntriesFilterVO.getMailbagId()!= null &&
//			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
//		sbul.append("AND MST.MALIDR = ");    
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getMailbagId());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
//			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
//		sbul.append("AND MST.RSN = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getRsn());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getHni()!= null &&
//			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
//		sbul.append("AND MST.HSN = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getHni());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
//			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
//		sbul.append("AND MST.REGIND = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getRegInd());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getYear()!= null &&
//			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
//		sbul.append("AND MST.YER = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getYear());
//		sbul.append("'");
//	}
//			if (gpaBillingEntriesFilterVO.getContractRate() != null
//					&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getUPURate() == null) {
//				sbul.append("AND C66DTL.RATTYP = ");
//				sbul.append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
//				sbul.append("'");
//			}
//			if (gpaBillingEntriesFilterVO.getUPURate() != null
//					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getContractRate() == null) {
//				sbul.append("AND C66DTL.RATTYP = ");
//				sbul.append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
//				sbul.append("'");
//			}
//			else if ((gpaBillingEntriesFilterVO.getUPURate() != null
//					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
//					.getContractRate().trim().length() > 0)) {
//				sbul.append("AND C66DTL.RATTYP IN ");
//				sbul.append("(").append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
//				sbul.append("'").append(",").append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
//				sbul.append("'").append(")");
//			}
//
//		if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
//			sbul.append("AND MST.ORGCTYCOD = ");
//			sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getOrigin());
//			sbul.append("'");
//		}
//		if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
//			sbul.append("AND MST.DSTCTYCOD = ");
//			sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getDestination());
//			sbul.append("'");	
//		}
//
//	log.log(Log.INFO, "sbul -->>", sbul.toString());
//	c66dtlQuery.append(sbul.toString());
//	log.log(Log.INFO, "c66dtlQuery", c66dtlQuery);
//	query.append("UNION ALL");
//	query.append(c66dtlQuery.toString());
//	StringBuilder sb = new StringBuilder();
//	sb.append("'");
//	sb.append( gpaBillingEntriesFilterVO.getCompanyCode());
//	sb.append("'");
//	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null) {
//		sb.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
//		sb.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
//		sb.append(" AND ");
//		sb.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
//		sb.append(" ");
//	/*	sb.append("AND MST.RCVDAT BETWEEN To_DATE(");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getFromDate().toDisplayDateOnlyFormat());
//		sb.append("', 'DD-MON-YYYY') ");
//		sb.append(" AND To_DATE(");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getToDate().toDisplayDateOnlyFormat());
//		sb.append("', 'DD-MON-YYYY') ");
//		sb.append(" ");*/
//	}
//
//	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
//			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
//		sb.append( "AND CCADTL.BLGSTA =  ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getBillingStatus());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
//			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){   
//		sb.append("AND MST.CSGDOCNUM IN ");
//		sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getConDocNumber());
//		sb.append(")");
//	}
//	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
//			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//		sb.append("AND MST.DSN IN "); 
//		sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getDsnNumber());
//		sb.append(")");
//	}
//	if(gpaBillingEntriesFilterVO.getRateFilter()!=null 
//			&& gpaBillingEntriesFilterVO.getRateFilter().trim().length()>0){
//		sb.append("AND INNERCCADTL.APLRAT IN "); 
//		sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getRateFilter());
//		sb.append(")");			
//	}
//	if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getFromCsgGroup())){
//		if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//			sb.append( "AND CCADTL.REVGPACOD IN  ");
//			sb.append("(");
//			sb.append(gpaBillingEntriesFilterVO.getGpaCode());
//			sb.append(")");
//		}
//		if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange() != null &&
//				gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//			sb.append("AND MST.ORGEXGOFC IN ");
//			sb.append("(");
//			sb.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//			sb.append(")");
//		}
//		if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange() != null &&
//				gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//			sb.append("AND MST.DSTEXGOFC IN ");
//			sb.append("(");
//			sb.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//			sb.append(")");
//		}
//	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			sb.append("AND MST.MALCTGCOD IN ");
//			sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//			sb.append(")");
//		}
//		if (gpaBillingEntriesFilterVO.getMailSubclass() != null &&
//				gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//			sb.append("AND MST.MALSUBCLS IN ");
//			sb.append("(");
//			sb.append(gpaBillingEntriesFilterVO.getMailSubclass());
//			sb.append(")");
//		}
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//		      sb.append(" AND MST.MALPERFLG IN ");
//		      sb.append("(");
//		      sb.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//		      sb.append(")");
//	   }
//	   if(gpaBillingEntriesFilterVO.getCurrencyCode()!=null &&
//			   gpaBillingEntriesFilterVO.getCurrencyCode().trim().length()>0){
//		      sb.append(" AND CCADTL.REVCTRCURCOD IN ");
//		      sb.append("(");
//		      sb.append(gpaBillingEntriesFilterVO.getCurrencyCode());
//		      sb.append(")");	   
//	   }
//	    if(GPABillingEntriesFilterVO.FLAG_NO.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
//	    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
//	    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
//	    		sb.append("AND INNERCCADTL.CCAREFNUM IN ");
//	    		sb.append("(");
//	    		sb.append(gpaBillingEntriesFilterVO.getMcaNumber());
//	    		sb.append(")");   	    		
//	    	}
//	    }else if(GPABillingEntriesFilterVO.FLAG_YES.equals(gpaBillingEntriesFilterVO.getMcaIndicator())){
//	    	if(gpaBillingEntriesFilterVO.getMcaNumber()!=null&& 
//	    			gpaBillingEntriesFilterVO.getMcaNumber().trim().length()>0){
//	    		sb.append("AND (INNERCCADTL.CCAREFNUM is null OR INNERCCADTL.CCAREFNUM IN ");
//	    		sb.append("(");
//	    		sb.append(gpaBillingEntriesFilterVO.getMcaNumber());
//	    		sb.append("))"); 	    		
//	    	}else{
//	    		sb.append("AND INNERCCADTL.CCAREFNUM IS NULL");
//	    	} 
//	   }
//	}else{
//		if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//				gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//			sb.append( "AND CCADTL.REVGPACOD =  ");
//			sb.append("'");
//			sb.append(gpaBillingEntriesFilterVO.getGpaCode());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange() != null &&
//			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//		sb.append("AND MST.ORGEXGOFC = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange() != null &&
//			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//		sb.append("AND MST.DSTEXGOFC = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//		sb.append("'");
//	}
//		if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//				gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//			if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
//			sb.append("AND MST.MALCTGCOD = ");
//		sb.append("'");
//			sb.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//		sb.append("'");
//			}
//	}
//	if (gpaBillingEntriesFilterVO.getMailSubclass() != null &&
//			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//		sb.append("AND MST.MALSUBCLS = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getMailSubclass());
//		sb.append("'");
//	}
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//	    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//		      sb.append(" AND MST.MALPERFLG = ");
//		      sb.append("'");
//		      sb.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//		      sb.append("'");
//	    	}
//	   }		
//	}
//	if (gpaBillingEntriesFilterVO.getMailbagId() != null &&
//			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
//		sb.append("AND MST.MALIDR = ");     
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getMailbagId());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
//			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
//		sb.append("AND MST.RSN = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getRsn());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getHni()!= null &&
//			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
//		sb.append("AND MST.HSN = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getHni());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
//			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
//		sb.append("AND MST.REGIND = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getRegInd());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getYear()!= null &&
//			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
//		sb.append("AND MST.YER = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getYear());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getContractRate() != null
//			&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getUPURate() == null) {
//		sb.append("AND INNERCCADTL.RATTYP = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getContractRate());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getUPURate() != null
//			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getContractRate() == null) {
//		sb.append("AND INNERCCADTL.RATTYP = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getUPURate());
//		sb.append("'");
//	}
//	else if ((gpaBillingEntriesFilterVO.getUPURate() != null
//			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
//			.getContractRate().trim().length() > 0)) {
//		sb.append("AND INNERCCADTL.RATTYP IN ");
//		sb.append("(").append("'");
//		sb.append(gpaBillingEntriesFilterVO.getUPURate());
//		sb.append("'").append(",").append("'");
//		sb.append(gpaBillingEntriesFilterVO.getContractRate());
//		sb.append("'").append(")");
//	}
//
//	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
//		sb.append("AND MST.ORGCTYCOD = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getOrigin());
//		sb.append("'");
//	}
//	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
//		sb.append("AND MST.DSTCTYCOD = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getDestination());
//		sb.append("'");	
//	}   
//
//		if ((gpaBillingEntriesFilterVO.getContractRate() != null)
//				&& (gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0)
//				&& (gpaBillingEntriesFilterVO.getUPURate() == null)) {
//			sb.append("AND INNERCCADTL.RATTYP = ");
//			sb.append("'");
//			sb.append(gpaBillingEntriesFilterVO.getContractRate());
//			sb.append("'");
//		}
//		if ((gpaBillingEntriesFilterVO.getUPURate() != null)
//				&& (gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0)
//				&& (gpaBillingEntriesFilterVO.getContractRate() == null)) {
//			sb.append("AND INNERCCADTL.RATTYP = ");
//			sb.append("'");
//			sb.append(gpaBillingEntriesFilterVO.getUPURate());
//			sb.append("'");
//		} else if ((gpaBillingEntriesFilterVO.getUPURate() != null)
//				&& (gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0)
//				&& (gpaBillingEntriesFilterVO.getContractRate() != null)
//				&& (gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0)) {
//			sb.append("AND INNERCCADTL.RATTYP IN ");
//			sb.append("(").append("'");
//			sb.append(gpaBillingEntriesFilterVO.getUPURate());
//			sb.append("'").append(",").append("'");
//			sb.append(gpaBillingEntriesFilterVO.getContractRate());
//			sb.append("'").append(")");
//	    	    }
//	ccadtlQuery.append(sb.toString());
//	query.append("UNION ALL");
//	query.append(ccadtlQuery.toString());
//	StringBuilder orderingString = new StringBuilder();
//	orderingString.append(" )GROUP BY CMPCOD,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,COD,INVNUM ");
//	orderingString.append(" ) ORDER BY CMPCOD,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,INVNUM ");
//	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);//added by A-5175 for cr icrd-21098
//	query.append(orderingString.toString());
//	log.log(Log.INFO, "final query -->>", query);
//	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());
	//Commented for IASCB-21493 End
}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#listConsignmentDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
 *	Added by 			: A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO,Collection<CRAParameterVO> craParameterVOs)
		throws SystemException, PersistenceException {
	String groupParm = getGroupParameter(craParameterVOs);
	
	//Added for IASCB-21493 Begin
	StringBuilder rankQuery=new StringBuilder();
	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
	rankQuery.append("RESULT_TABLE.CMPCOD, ");
	rankQuery.append("RESULT_TABLE.BLGBAS, ");
	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
	rankQuery.append("RESULT_TABLE.POACOD, ");
	rankQuery.append("RESULT_TABLE.INVNUM, ");
	rankQuery.append("RESULT_TABLE.SEQNUM, ");
	rankQuery.append("RESULT_TABLE.CCAREFNUM");
	rankQuery.append(") RANK ");
	rankQuery.append("FROM ( ");
	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_CONSIGNMENTDETAILS);
	
	String baseQuery=rankQuery.append(blgdtlQuery).toString();
	PageableNativeQuery<ConsignmentDocumentVO> query = new ConsignmentDetailsFilterQuery(gpaBillingEntriesFilterVO.getDefaultPageSize(),gpaBillingEntriesFilterVO.getTotalRecordCount(), new ConsignmentDetailsMapper(), baseQuery, gpaBillingEntriesFilterVO);
	StringBuilder orderingString = new StringBuilder();
	
	orderingString.append(" ) MST GROUP BY CMPCOD,CSGDOCNUM,");
	orderingString.append(groupParm).append(" ");
	orderingString.append(" ) ORDER BY CMPCOD,CSGDOCNUM,");
	orderingString.append(groupParm).append(" ");
	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
	query.append(orderingString.toString());
	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());
	//Added for IASCB-21493 End
	
////Commented for IASCB-21493 Begin
//	String groupParm = getGroupParameter(craParameterVOs);
//	log.log(Log.INFO, "Group by parameter -->>", groupParm);
//	StringBuilder rankQuery=new StringBuilder();
//	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
//	rankQuery.append("RESULT_TABLE.CMPCOD, ");
//	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
//	rankQuery.append("RESULT_TABLE.GPA, ");
//	rankQuery.append("RESULT_TABLE.OOE, ");
//	rankQuery.append("RESULT_TABLE.DOE, ");
//	rankQuery.append("RESULT_TABLE.RAT ");
//	rankQuery.append(") RANK ");
//	rankQuery.append("FROM ( ");
//	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_CONSIGNMENTDETAILS);
//	String baseQuery=rankQuery.append(blgdtlQuery).toString();
//	PageableNativeQuery<ConsignmentDocumentVO> query = new ConsignmentDetailsFilterQuery(gpaBillingEntriesFilterVO.getDefaultPageSize(),gpaBillingEntriesFilterVO.getTotalRecordCount(), new ConsignmentDetailsMapper(), baseQuery, gpaBillingEntriesFilterVO);
//    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//    		  StringBuilder sbldr = new StringBuilder();
//    		  sbldr.append(" AND MST.MALPERFLG = ");
//    		  sbldr.append("'");
//    		  sbldr.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//    	      sbldr.append("'");
//    	      query.append(sbldr.toString());
//    	}
//    	    }
//	Query c66dtlQuery = getQueryManager().createNamedNativeQuery(Find_C66CONSIGNMENTDETAILS);
//	Query ccadtlQuery = getQueryManager().createNamedNativeQuery(Find_CCACONSIGNMENTDETAILS);
//	StringBuilder sbul = new StringBuilder();
//	sbul.append("'");
//	sbul.append( gpaBillingEntriesFilterVO.getCompanyCode());
//	sbul.append("'");
//	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null ) {
//		sbul.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
//		sbul.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
//		sbul.append(" AND ");
//		sbul.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
//		sbul.append(" ");
//	}
//	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
//			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
//		sbul.append( "AND C66DTL.BLGSTA =  ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getBillingStatus());
//		sbul.append("'");
//	}
//	if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//			gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//		sbul.append( "AND C66DTL.GPACOD =  ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getGpaCode());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
//			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){
//		sbul.append("AND MST.CSGDOCNUM IN ");
//		sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getConDocNumber());
//		sbul.append(")");
//	}
//	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
//			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//		sbul.append("AND MST.DSN IN "); //fix for ICRD-282734
//		sbul.append("(");
//		sbul.append(gpaBillingEntriesFilterVO.getDsnNumber());
//		sbul.append(")");
//	}
//	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//		if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
//		sbul.append("AND C66DTL.MALCTGCOD = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//		sbul.append("'");
//		}
//	}
//	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange()!= null &&
//			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//		sbul.append("AND MST.ORGEXGOFC = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange()!= null &&
//			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//		sbul.append("AND MST.DSTEXGOFC = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getMailbagId()!= null &&
//			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
//		sbul.append("AND MST.MALIDR = ");    
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getMailbagId());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getMailSubclass()!= null &&
//			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//		sbul.append("AND C66DTL.ACTSUBCLS = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getMailSubclass());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
//			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
//		sbul.append("AND MST.RSN = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getRsn());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getHni()!= null &&
//			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
//		sbul.append("AND MST.HSN = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getHni());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
//			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
//		sbul.append("AND MST.REGIND = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getRegInd());
//		sbul.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getYear()!= null &&
//			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
//		sbul.append("AND MST.YER = ");
//		sbul.append("'");
//		sbul.append(gpaBillingEntriesFilterVO.getYear());
//		sbul.append("'");
//	}
//			if (gpaBillingEntriesFilterVO.getContractRate() != null
//					&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getUPURate() == null) {
//				sbul.append("AND C66DTL.RATTYP = ");
//				sbul.append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
//				sbul.append("'");
//			}
//			if (gpaBillingEntriesFilterVO.getUPURate() != null
//					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getContractRate() == null) {
//				sbul.append("AND C66DTL.RATTYP = ");
//				sbul.append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
//				sbul.append("'");
//			}
//			else if ((gpaBillingEntriesFilterVO.getUPURate() != null
//					&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//					&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
//					.getContractRate().trim().length() > 0)) {
//				sbul.append("AND C66DTL.RATTYP IN ");
//				sbul.append("(").append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getUPURate());
//				sbul.append("'").append(",").append("'");
//				sbul.append(gpaBillingEntriesFilterVO.getContractRate());
//				sbul.append("'").append(")");
//			}
//		if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
//			sbul.append("AND MST.ORGCTYCOD = ");
//			sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getOrigin());
//			sbul.append("'");
//		}
//		if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
//			sbul.append("AND MST.DSTCTYCOD = ");
//			sbul.append("'");
//			sbul.append(gpaBillingEntriesFilterVO.getDestination());
//			sbul.append("'");	
//		}
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//	    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//	    	      sbul.append(" AND MST.MALPERFLG = ");
//	    	      sbul.append("'");
//	    	      sbul.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//	    	      sbul.append("'");
//	    	}
//	    	    }
//	log.log(Log.INFO, "sbul -->>", sbul.toString());
//	c66dtlQuery.append(sbul.toString());
//	log.log(Log.INFO, "c66dtlQuery", c66dtlQuery);
//	query.append("UNION ALL");
//	query.append(c66dtlQuery.toString());
//	StringBuilder sb = new StringBuilder();
//	sb.append("'");
//	sb.append( gpaBillingEntriesFilterVO.getCompanyCode());
//	sb.append("'");
//	if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null) {
//		sb.append("AND TO_NUMBER(TO_CHAR(MST.RCVDAT,'YYYYMMDD')) BETWEEN ");
//		sb.append(gpaBillingEntriesFilterVO.getFromDate().toSqlDate().toString().replace("-", ""));
//		sb.append(" AND ");
//		sb.append(gpaBillingEntriesFilterVO.getToDate().toSqlDate().toString().replace("-", ""));
//		sb.append(" ");
//	}
//	if ( gpaBillingEntriesFilterVO.getGpaCode() != null &&
//			gpaBillingEntriesFilterVO.getGpaCode().trim().length() > 0  ) {
//		sb.append( "AND CCADTL.REVGPACOD =  ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getGpaCode());
//		sb.append("'");
//	}
//	if ( gpaBillingEntriesFilterVO.getBillingStatus() != null &&
//			gpaBillingEntriesFilterVO.getBillingStatus().trim().length() > 0) {
//		sb.append( "AND CCADTL.BLGSTA =  ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getBillingStatus());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getConDocNumber() != null &&
//			gpaBillingEntriesFilterVO.getConDocNumber().trim().length() >0){   
//		sb.append("AND MST.CSGDOCNUM IN ");
//		sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getConDocNumber());
//		sb.append(")");
//	}
//	if (gpaBillingEntriesFilterVO.getDsnNumber() != null &&
//			gpaBillingEntriesFilterVO.getDsnNumber().trim().length() >0){
//		sb.append("AND MST.DSN IN ");  //fix for ICRD-282734
//		sb.append("(");
//		sb.append(gpaBillingEntriesFilterVO.getDsnNumber());
//		sb.append(")"); 
//	}
//	if (gpaBillingEntriesFilterVO.getMailCategoryCode() != null &&
//			gpaBillingEntriesFilterVO.getMailCategoryCode().trim().length() >0){
//		if(!"ALL".equals(gpaBillingEntriesFilterVO.getMailCategoryCode())){
//		sb.append("AND MST.MALCTGCOD = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getMailCategoryCode());
//		sb.append("'");
//		}
//	}
//	if (gpaBillingEntriesFilterVO.getOriginOfficeOfExchange() != null &&
//			gpaBillingEntriesFilterVO.getOriginOfficeOfExchange().trim().length() >0){
//		sb.append("AND MST.ORGEXGOFC = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange() != null &&
//			gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange().trim().length() >0){
//		sb.append("AND MST.DSTEXGOFC = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getMailbagId() != null &&
//			gpaBillingEntriesFilterVO.getMailbagId().trim().length() >0){
//		sb.append("AND MST.MALIDR = ");     
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getMailbagId());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getMailSubclass() != null &&
//			gpaBillingEntriesFilterVO.getMailSubclass().trim().length() >0){
//		sb.append("AND MST.MALSUBCLS = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getMailSubclass());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRsn()!= null &&
//			gpaBillingEntriesFilterVO.getRsn().trim().length() >0){
//		sb.append("AND MST.RSN = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getRsn());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getHni()!= null &&
//			gpaBillingEntriesFilterVO.getHni().trim().length() >0){
//		sb.append("AND MST.HSN = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getHni());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getRegInd()!= null &&
//			gpaBillingEntriesFilterVO.getRegInd().trim().length() >0){
//		sb.append("AND MST.REGIND = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getRegInd());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getYear()!= null &&
//			gpaBillingEntriesFilterVO.getYear().trim().length() >0){
//		sb.append("AND MST.YER = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getYear());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getContractRate() != null
//			&& gpaBillingEntriesFilterVO.getContractRate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getUPURate() == null) {
//		sb.append("AND INNERCCADTL.RATTYP = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getContractRate());
//		sb.append("'");
//	}
//	if (gpaBillingEntriesFilterVO.getUPURate() != null
//			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getContractRate() == null) {
//		sb.append("AND INNERCCADTL.RATTYP = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getUPURate());
//		sb.append("'");
//	}
//	else if ((gpaBillingEntriesFilterVO.getUPURate() != null
//			&& gpaBillingEntriesFilterVO.getUPURate().trim().length() > 0
//			&& gpaBillingEntriesFilterVO.getContractRate() != null && gpaBillingEntriesFilterVO
//			.getContractRate().trim().length() > 0)) {
//		sb.append("AND INNERCCADTL.RATTYP IN ");
//		sb.append("(").append("'");
//		sb.append(gpaBillingEntriesFilterVO.getUPURate());
//		sb.append("'").append(",").append("'");
//		sb.append(gpaBillingEntriesFilterVO.getContractRate());
//		sb.append("'").append(")");
//	}
//	if(gpaBillingEntriesFilterVO.getOrigin()!=null && gpaBillingEntriesFilterVO.getOrigin().trim().length()>0){
//		sb.append("AND MST.ORGCTYCOD = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getOrigin());
//		sb.append("'");
//	}
//	if(gpaBillingEntriesFilterVO.getDestination()!=null && gpaBillingEntriesFilterVO.getDestination().trim().length()>0){
//		sb.append("AND MST.DSTCTYCOD = ");
//		sb.append("'");
//		sb.append(gpaBillingEntriesFilterVO.getDestination());
//		sb.append("'");	
//	}   
//	
//	    if ((gpaBillingEntriesFilterVO.getIsUSPSPerformed() != null) && 
//	    	      (gpaBillingEntriesFilterVO.getIsUSPSPerformed().trim().length() > 0)){
//	    	if(!"ALL".equals(gpaBillingEntriesFilterVO.getIsUSPSPerformed())){
//	    	      sb.append(" AND MST.MALPERFLG = ");
//	    	      sb.append("'");
//	    	      sb.append(gpaBillingEntriesFilterVO.getIsUSPSPerformed());
//	    	      sb.append("'");
//	    	}
//	    	    }
//	ccadtlQuery.append(sb.toString());
//	query.append("UNION ALL");
//	query.append(ccadtlQuery.toString());
//	StringBuilder orderingString = new StringBuilder();
//	orderingString.append(" )GROUP BY CMPCOD,CSGDOCNUM,");
//	orderingString.append(groupParm).append(" ");
//	orderingString.append(" ) ORDER BY CMPCOD,CSGDOCNUM,");
//	orderingString.append(groupParm).append(" ");
//	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);//added by A-5175 for cr icrd-21098
//	query.append(orderingString.toString());
//	log.log(Log.INFO, "final query -->>", query);
//	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());
	////Commented for IASCB-21493 End
}	
/**
 * 	Method		:	MRADefaultsSqlDAO.getGroupParameter
 *	Added by 	:	A-4809 on Feb 8, 2019
 * 	Used for 	:
 *	Parameters	:	@param craParameterVOs
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
private String getGroupParameter(Collection<CRAParameterVO> craParameterVOs) {
	StringBuilder groupString = null;
	if(craParameterVOs!=null && !craParameterVOs.isEmpty()){
		for(CRAParameterVO parameterVO :craParameterVOs){
		Collection<CRAParameterConfigRuleVO> cRAParameterConfigRuleVOs = parameterVO.getCRAParameterConfigRuleVOs();
		if(cRAParameterConfigRuleVOs!=null && !cRAParameterConfigRuleVOs.isEmpty()){
			for(CRAParameterConfigRuleVO ruleVO : cRAParameterConfigRuleVOs){
				if(MRAConstantsVO.CRA_PAR_FUNPNT_MBG.equals(ruleVO.getFunctionPoint())){
				if(CRAParameterConfigRuleVO.FLAG_YES.equals(ruleVO.getInclude())){
					if(groupString != null){
					groupString.append(",").append(ruleVO.getConditionCode());
					}else{
					groupString = new StringBuilder(ruleVO.getConditionCode());
					}
				}
			 }
			} 
		}
		}
	}
	//groupString.replace(start, end, str);
	return groupString.toString().replace(",CSGDOCNUM", "");
	 
	
}	
public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO) throws SystemException{
	int index = 0;
Procedure importProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_IMPORT_RESDIT_DATA);
importProcedure.setSensitivity(true);
LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
  LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
importProcedure.setParameter(++index, mailScanDetailVO.getCompanyCode());
importProcedure.setParameter(++index, mailScanDetailVO.getMailSequenceNumber());    	 
importProcedure.setParameter(++index, "RESDIT");	     	  	  
importProcedure.setParameter(++index, logonAttributes.getUserId());
importProcedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());	    	  
importProcedure.setOutParameter(++index, SqlType.STRING);
importProcedure.execute();
String outParameter = (String)importProcedure.getParameter(index);
log.log(Log.FINE, "Out Parameter after Import "+outParameter);
}		
		
/**
 * +
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findBillingEntriesAtMailbagLevel(java.lang.String)
 *	Added by 			: A-7531 on 13-May-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public Collection<MRABillingDetailsVO> findBillingEntriesAtMailbagLevel(String companyCode) throws SystemException ,PersistenceException{
	Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDMALDETAILS);
	int index=0;
	
	query.setParameter(++index,companyCode);
	query.setSensitivity(true);
	return  query.getResultList(new BillingEntryDetailsMapper() );
		
}

public String generateSAPFIFile(SAPInterfaceFilterVO interfaceFilterVO) throws SystemException{
	log.entering(CLASS_NAME, "generateSAPFIFile");	
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true);
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
	Procedure generateSapInterfaceFileProcedure = null;
	generateSapInterfaceFileProcedure = getQueryManager()
			.createNamedNativeProcedure(GENERATE_INTERFACE_FILE_SAPFI);	 
	int index = 0;
	generateSapInterfaceFileProcedure.setParameter(++index, interfaceFilterVO.getCompanyCode());
	if(interfaceFilterVO.getInterfaceFilename() != null && interfaceFilterVO.getInterfaceFilename().trim().length() > 0){
		generateSapInterfaceFileProcedure.setParameter(++index, interfaceFilterVO.getInterfaceFilename());
	} else {
		generateSapInterfaceFileProcedure.setParameter(++index , EMPTY_STRING);	
	}
	generateSapInterfaceFileProcedure.setParameter(++index,interfaceFilterVO.getFileType() );
	generateSapInterfaceFileProcedure.setParameter(++index,interfaceFilterVO.getSubsystem() );
	if(interfaceFilterVO.getAccountMonth() != null ) {
	generateSapInterfaceFileProcedure.setParameter(++index,interfaceFilterVO.getAccountMonth());
	}
	else {
		generateSapInterfaceFileProcedure.setParameter(++index, EMPTY_STRING);
	}
	if(interfaceFilterVO.getFromDate() != null ) {
		LocalDate fromDate = new LocalDate(interfaceFilterVO.getFromDate(),true);
		fromDate.set(Calendar.MILLISECOND, fromDate.getActualMinimum(Calendar.MILLISECOND));
        fromDate.set(Calendar.SECOND, fromDate.getActualMinimum(Calendar.SECOND));
        fromDate.set(Calendar.MINUTE, fromDate.getActualMinimum(Calendar.MINUTE));
        fromDate.set(Calendar.HOUR_OF_DAY, fromDate.getActualMinimum(Calendar.HOUR_OF_DAY));
        generateSapInterfaceFileProcedure.setParameter(++index, fromDate.toSqlTimeStamp());	
	} else {
		generateSapInterfaceFileProcedure.setNullParameter(++index, Types.TIMESTAMP);
	}
	if(interfaceFilterVO.getToDate() != null) {
		LocalDate toDate = new LocalDate(interfaceFilterVO.getToDate(),true);
        toDate.set(Calendar.MILLISECOND, toDate.getActualMaximum(Calendar.MILLISECOND));
        toDate.set(Calendar.SECOND, toDate.getActualMaximum(Calendar.SECOND));
        toDate.set(Calendar.MINUTE, toDate.getActualMaximum(Calendar.MINUTE));
        toDate.set(Calendar.HOUR_OF_DAY, toDate.getActualMaximum(Calendar.HOUR_OF_DAY));
        generateSapInterfaceFileProcedure.setParameter(++index,toDate.toSqlTimeStamp());
	} else {
		generateSapInterfaceFileProcedure.setNullParameter(++index, Types.TIMESTAMP);
	}
	generateSapInterfaceFileProcedure.setParameter(++index, interfaceFilterVO.getRegenerateFlag());
	if(interfaceFilterVO.getRegenFilename()!= null && interfaceFilterVO.getRegenFilename().trim().length()>0){
		generateSapInterfaceFileProcedure.setParameter(++index ,interfaceFilterVO.getRegenFilename());
	} else {
		generateSapInterfaceFileProcedure.setParameter(++index, EMPTY_STRING);	
	}
	generateSapInterfaceFileProcedure.setParameter(++index, currentDate);
	generateSapInterfaceFileProcedure.setParameter(++index, logonAttributes.getUserId());
	generateSapInterfaceFileProcedure.setOutParameter(++index, SqlType.STRING); 
	String status="OK";
	if(generateSapInterfaceFileProcedure!=null) {
		generateSapInterfaceFileProcedure.execute();
		status = (String) generateSapInterfaceFileProcedure.getParameter(index);
	log.log(Log.FINE, "status is ", status);
	}
	log.exiting(CLASS_NAME, "generateSAPFIFile");
	return status;
}

/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#updateRouteAndReprorate(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
 *	Added by 			: A-8061 on 15-Dec-2020
 * 	Used for 	:
 *	Parameters	:	@param gPABillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
public String updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO)
		throws SystemException, PersistenceException {
	
	log.entering(CLASS_NAME, "updateRouteAndReprorate");
	
	Boolean flag = true;
	Collection<String> outParameters = new ArrayList<>();
	String returnString = null;
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	String userId = logonAttributes.getUserId();
	StringBuilder flightFilter=new StringBuilder();
	StringBuilder despatchDetails=new StringBuilder();
	
	flightFilter.append(gPABillingEntriesFilterVO.getCarrierCode()!=null?gPABillingEntriesFilterVO.getCarrierCode():EMPTY_STRING).append(COLUMN_SEP).
	append(gPABillingEntriesFilterVO.getFlightNumber()!=null?gPABillingEntriesFilterVO.getFlightNumber():EMPTY_STRING).append(COLUMN_SEP).
	append(gPABillingEntriesFilterVO.getFlightDate()!=null?gPABillingEntriesFilterVO.getFlightDate().toDisplayDateOnlyFormat():EMPTY_STRING);
	
	despatchDetails.append(gPABillingEntriesFilterVO.getDsnNumber()!=null?gPABillingEntriesFilterVO.getDsnNumber():EMPTY_STRING).append(COLUMN_SEP).
	append(gPABillingEntriesFilterVO.getRsn()!=null?gPABillingEntriesFilterVO.getRsn():EMPTY_STRING).append(COLUMN_SEP).
	append(gPABillingEntriesFilterVO.getHni()!=null?gPABillingEntriesFilterVO.getHni():EMPTY_STRING).append(COLUMN_SEP).
	append(gPABillingEntriesFilterVO.getRegInd()!=null?gPABillingEntriesFilterVO.getRegInd():EMPTY_STRING);

	Procedure importProcedure = getQueryManager()
					.createNamedNativeProcedure(
							MRA_DEFAULTS_UPDATEROUTE_AND_REPRORATE);
	importProcedure.setSensitivity(true);
			
	int index = 0;
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			
		importProcedure.setParameter(++index,gPABillingEntriesFilterVO.getCompanyCode() );
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getExceptionCode()!=null?gPABillingEntriesFilterVO.getExceptionCode():EMPTY_STRING);	
		importProcedure.setParameter(++index, flightFilter.toString());
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getOrigin()!=null?gPABillingEntriesFilterVO.getOrigin():EMPTY_STRING);
		importProcedure.setParameter(++index,  gPABillingEntriesFilterVO.getDestination()!=null?gPABillingEntriesFilterVO.getDestination():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getOriginOfficeOfExchange()!=null?gPABillingEntriesFilterVO.getOriginOfficeOfExchange():EMPTY_STRING);
		importProcedure.setParameter(++index,  gPABillingEntriesFilterVO.getDestinationOfficeOfExchange()!=null?gPABillingEntriesFilterVO.getDestinationOfficeOfExchange():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getMailCategoryCode()!=null?gPABillingEntriesFilterVO.getMailCategoryCode():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getMailSubclass()!=null?gPABillingEntriesFilterVO.getMailSubclass():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getYear()!=null?gPABillingEntriesFilterVO.getYear():EMPTY_STRING);
		importProcedure.setParameter(++index, despatchDetails.toString());
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getFromDate()!=null?gPABillingEntriesFilterVO.getFromDate():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getToDate()!=null?gPABillingEntriesFilterVO.getToDate():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getConDocNumber()!=null?gPABillingEntriesFilterVO.getConDocNumber():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getBillingStatus()!=null?gPABillingEntriesFilterVO.getBillingStatus():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getGpaCode()!=null?gPABillingEntriesFilterVO.getGpaCode():EMPTY_STRING);	
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getIsUSPSPerformed()!=null?gPABillingEntriesFilterVO.getIsUSPSPerformed():EMPTY_STRING);	
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getPaBuilt()!=null?gPABillingEntriesFilterVO.getPaBuilt():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getRateFilter()!=null?gPABillingEntriesFilterVO.getRateFilter():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getAssignedStatus()!=null?gPABillingEntriesFilterVO.getAssignedStatus():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getAssignee()!=null?gPABillingEntriesFilterVO.getAssignee():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getMailSequenceNumber());
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getTransferPA()!=null?gPABillingEntriesFilterVO.getTransferPA():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getTransferAirline()!=null?gPABillingEntriesFilterVO.getTransferAirline():EMPTY_STRING);
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getFlightDetails());
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getFromScreen());
		importProcedure.setParameter(++index, logonAttributes.getStationCode());
		importProcedure.setParameter(++index, gPABillingEntriesFilterVO.getMailSeqNumbers());
		importProcedure.setParameter(++index, userId);
		importProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		importProcedure.setOutParameter(++index, SqlType.STRING);
		importProcedure.execute();
		
		String outParameter = (String) importProcedure.getParameter(index);
		outParameters.add(outParameter);

			for (String parameter : outParameters) {
				if (!"OK".equals(parameter)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				returnString = "OK";
			} else {
				returnString = "NOTOK";
			}
			
	log.exiting(CLASS_NAME, "updateRouteAndReprorate");
			
	return returnString;

}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#reImportPABuiltMailbagsToMRA(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 			: A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO) throws SystemException {
		log.entering(CLASS_NAME, "reImportPABuiltMailbagsToMRA");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		int index = 0;
		Procedure procedure = getQueryManager()
				.createNamedNativeProcedure(MRA_DEFAULTS_REIMPORT_PABUILT_MAILBAGS_TO_MRA);
		procedure.setParameter(++index, mailbagVO.getCompanyCode());
		procedure.setParameter(++index, mailbagVO.getMailSequenceNumber());
		procedure.setParameter(++index, Objects.nonNull(mailbagVO.getPreviousPostalContainerNumber())
				? mailbagVO.getPreviousPostalContainerNumber() : BLANK);
		procedure.setParameter(++index,
				Objects.nonNull(mailbagVO.getTriggerForReImport()) ? mailbagVO.getTriggerForReImport() : "ASYNC");
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setParameter(++index, currentDate.toSqlTimeStamp());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		String outParameter = (String) procedure.getParameter(index);
		log.log(Log.FINE, "Out Parameter:  ", outParameter);
		log.exiting(CLASS_NAME, "reImportPABuiltMailbagsToMRA");
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findMailbagsForPABuiltUpdate(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 			: A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findMailbagsForPABuiltUpdate");
		Query query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FIND_MAILBAG_FOR_PABUILT_UPDATE);
		int index = 0;
		query.setParameter(++index, mailbagVO.getCompanyCode());
		query.setParameter(++index, mailbagVO.getMailSequenceNumber());
		query.setParameter(++index, mailbagVO.getCompanyCode());
		String postalContainerNumber = BLANK;
		if (Objects.nonNull(mailbagVO.getAcceptancePostalContainerNumber())) {
			postalContainerNumber = mailbagVO.getAcceptancePostalContainerNumber();
		} else if (Objects.nonNull(mailbagVO.getPreviousPostalContainerNumber())) {
			postalContainerNumber = mailbagVO.getPreviousPostalContainerNumber();
		}
		query.setParameter(++index, postalContainerNumber);
		
		
		
		if(isOracleDataSource()){
			query.append(" AND TRUNC(ACPSCNDAT) =TRUNC(?) ");
		query.setParameter(++index, Objects.isNull(mailbagVO.getAcceptanceScanDate()) ? BLANK
				: mailbagVO.getAcceptanceScanDate().toSqlTimeStamp());
		}
		else{

			query.append("AND TRUNC(ACPSCNDAT)   =to_date(?, 'dd-MON-yyyy') ");
			query.setParameter(++index, mailbagVO.getAcceptanceScanDate().toDisplayDateOnlyFormat());               
			
		}  
	
		
		return query.getResultList(new Mapper<MailbagVO>() {
			@Override
			public MailbagVO map(ResultSet rs) throws SQLException {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
				mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				return mailbagVO;
			}
		});
	}

public Page<DocumentBillingDetailsVO> findGPABillingEntries(
        GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
throws PersistenceException,SystemException{
	StringBuilder rankQuery=new StringBuilder();

	rankQuery.append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
	rankQuery.append("RESULT_TABLE.CMPCOD, ");
	rankQuery.append("RESULT_TABLE.BLGBAS, ");
	rankQuery.append("RESULT_TABLE.CSGDOCNUM, ");
	rankQuery.append("RESULT_TABLE.CSGSEQNUM, ");
	rankQuery.append("RESULT_TABLE.POACOD, ");
	rankQuery.append("RESULT_TABLE.INVNUM, ");
	rankQuery.append("RESULT_TABLE.SEQNUM, ");
	rankQuery.append("RESULT_TABLE.CCAREFNUM");
	rankQuery.append(") RANK ");
	rankQuery.append("FROM ( ");
	String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINENTRIES);
	String baseQuery=rankQuery.append(blgdtlQuery).toString();
	PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingEntriesFilterQuery(25,gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), baseQuery, gpaBillingEntriesFilterVO);
	StringBuilder orderingString = new StringBuilder();
	orderingString.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
	query.append(orderingString.toString());
	log.log(Log.INFO, "final query -->>", query);
	return query.getPage(gpaBillingEntriesFilterVO.getPageNumber());

} 
		

	/**
	 * 
	 */
	public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)
	 	throws SystemException, PersistenceException{

		for(DocumentBillingDetailsVO vo : VOs){
	    	  int index = 0;
	    	  Procedure prorateProcedure = getQueryManager().createNamedNativeProcedure(MRA_VOID_MAILBAGS);
	    	  prorateProcedure.setParameter(++index, vo.getCompanyCode());
	    	  prorateProcedure.setParameter(++index, vo.getMailSequenceNumber());
	    	  prorateProcedure.setParameter(++index, vo.getScreenID());
	    	  prorateProcedure.setParameter(++index, vo.getLastUpdatedUser());
	    	  prorateProcedure.setParameter(++index, vo.getLastUpdatedTime());
	    	  prorateProcedure.setOutParameter(++index, SqlType.STRING);
	    	  prorateProcedure.execute();
		      String outParameter = (String)prorateProcedure.getParameter(index);
		      log.log(Log.FINE, "outParameter after Re-rating "+outParameter);
		}
	}
		
	
	
	
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findVoidedInterfaceDetails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO)
 *	Added by 			: A-8061 on 15-Oct-2019
 * 	Used for 	:	ICRD-336689
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
	public Collection<FlightRevenueInterfaceVO> findVoidedInterfaceDetails(DocumentBillingDetailsVO documentBillingDetailsVO)
			throws SystemException, PersistenceException {

		Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs=null;
		log.entering(CLASS_NAME, "findVoidedInterfaceDetails");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String userId = logonAttributes.getUserId();
		Query query = null;
		int index = 0;

		query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDVOIDEDINTERFACEETAILS);
		query.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
		query.setParameter(++index, userId);
		query.setParameter(++index,  documentBillingDetailsVO.getCompanyCode());
		query.setParameter(++index, documentBillingDetailsVO.getMailSequenceNumber());
		
		log.log(Log.FINE, "<--------------- QUERY --------------->", query);
		 flightRevenueInterfaceVOs=query.getResultList(new FlightRevenueInterfaceMultimapper());
		
		//MALMRAINTFCDDTL table crated on 04-09-2019 ,below code block is used to retrieve old data.
		//remove this find block after analysing one month production data .
		 
		 if(flightRevenueInterfaceVOs==null || flightRevenueInterfaceVOs.isEmpty()){
			index = 0;
			query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDVOIDEDINTERFACEETAILSOLDDATA);
			query.setParameter(++index, documentBillingDetailsVO.getCompanyCode());
			query.setParameter(++index, userId);
			query.setParameter(++index,  documentBillingDetailsVO.getCompanyCode());
			query.setParameter(++index, documentBillingDetailsVO.getMailSequenceNumber());
			flightRevenueInterfaceVOs=query.getResultList(new FlightRevenueInterfaceMultimapper());
		 }

		 return flightRevenueInterfaceVOs;

	}	
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findMailbagBillingStatus(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
 *	Added by 			: a-8331 on 25-Oct-2019
 * 	Used for 	:
 *	Parameters	:	@param mailbagvo
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
@Override
public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo)throws SystemException, PersistenceException {
	
	log.entering(CLASS_NAME, "findMailbagBillingStatus");
	Query query = null;
	int index = 0;
    DocumentBillingDetailsVO billingDetail = new DocumentBillingDetailsVO();
	  query = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_FINDBILLINGSTATUS);
		index=0;
		
		query.setParameter(++index, mailbagvo.getCompanyCode());
		query.setParameter(++index,mailbagvo.getCarrierId());
		query.setParameter(++index,mailbagvo.getMailSequenceNumber());
		query.setParameter(++index,mailbagvo.getFlightNumber() );
		query.setParameter(++index,mailbagvo.getFlightSequenceNumber() );
		
		/*
		query.setParameter(++index,mailbagvo.getOrigin() );	
		query.setParameter(++index,mailbagvo.getDestination() );*/	
		
		if(mailbagvo.getOrigin()!=null && mailbagvo.getOrigin().trim().length()>0){
			query.append("AND DTL.SECFRM = ? ");
			query.setParameter(++index,mailbagvo.getOrigin());	
			
		}
		if(mailbagvo.getDestination()!=null && mailbagvo.getDestination().trim().length()>0){
			query.append(" AND DTL.SECTOO=? ");
			query.setParameter(++index,mailbagvo.getDestination());	
			
		}
	//documentBillingDetailsVO=query.getResultList(new FlightRevenueInterfaceMultimapper());
	String	billingStatus = query.getSingleResult(getStringMapper("BLGSTA"));
	billingDetail.setBillingStatus(billingStatus);
	//documentBillingDetailsVO.add(billingDetail);
		return billingDetail;
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findBillingType(com.ibsplc.icargo.business.mail.mra.vo.BillingScheduleFilterVO)
 *	Added by 			: a-9498 on 26-April-2021
 * 	Used for 	:
 *	Parameters	:	@param BillingScheduleFilterVO
 *	Parameters	:	@return 
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException
 */
	@Override
	public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO,
			int pageNumber) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "findBillingType");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		Query query = getQueryManager().createNamedNativeQuery(FIND_MAIL_BILLING_LIST);
		StringBuilder rankQuery = new StringBuilder().append(MRAConstantsVO.MAILTRACKING_MRA_DENSE_RANK_QUERY);
		rankQuery.append("RESULT_TABLE.SERNUM, ");
		rankQuery.append("RESULT_TABLE.PRDNUM");
		rankQuery.append(") RANK ");
		rankQuery.append("FROM ( ");
		rankQuery.append(query);

		PageableNativeQuery<BillingScheduleDetailsVO> pgqry = new PageableNativeQuery<>(billingScheduleFilterVO.getTotalRecordCount(),
				billingScheduleFilterVO.getTotalRecordCount(), rankQuery.toString(), new BillingScheduleMasterMapper());
		int index = 0;
		pgqry.setParameter(++index, billingScheduleFilterVO.getCompanyCode());
		if (billingScheduleFilterVO.getBillingType() != null && billingScheduleFilterVO.getBillingType().length() > 0) {
			pgqry.append(" AND MST.BLGTYP = ?  ");
			pgqry.setParameter(++index, billingScheduleFilterVO.getBillingType());
		}
		if (billingScheduleFilterVO.getBillingPeriod() != null
				&& billingScheduleFilterVO.getBillingPeriod().length() > 0) {
			pgqry.append(" AND MST.BLGPRD = ?  ");
			pgqry.setParameter(++index, billingScheduleFilterVO.getBillingPeriod());
		}
		if (billingScheduleFilterVO.getYear() > 0) {
			pgqry.append(" AND SUBSTR(MST.PRDNUM,1,4) = ? ");
			pgqry.setParameter(++index, Integer.toString(billingScheduleFilterVO.getYear()));
		}
		if( MRAConstantsVO.SCHEDULER_JOB_GPABLG.equals(billingScheduleFilterVO.getSource())){
			pgqry.append(" AND trunc(MST.GPAINVGEN) = ?  ");
			pgqry.setParameter(++index,currentDate.toSqlDate());	
		}
		if(MRAConstantsVO.SCHEDULER_JOB_PASSBLG.equals(billingScheduleFilterVO.getSource())){
			pgqry.append(" AND trunc(MST.PASFILGEN) = ?  ");
			pgqry.setParameter(++index,currentDate.toSqlDate());	
		}
		
		if(billingScheduleFilterVO.getBillingPeriodFromDate()!=null){
			pgqry.append(" AND MST.BLGPRDFRM = ? ");
			pgqry.setParameter(++index, billingScheduleFilterVO.getBillingPeriodFromDate());
		}
		if(billingScheduleFilterVO.getBillingPeriodToDate()!=null){
			pgqry.append(" AND MST.BLGPRDTOO = ? ");
			pgqry.setParameter(++index, billingScheduleFilterVO.getBillingPeriodToDate());
		}
		if(billingScheduleFilterVO.getPeriodNumber()!=null && billingScheduleFilterVO.getPeriodNumber().length()>0){
			pgqry.append(" AND MST.PRDNUM = ? ");
			pgqry.setParameter(++index, billingScheduleFilterVO.getPeriodNumber());
		}
		pgqry.append(MRAConstantsVO.MAILTRACKING_MRA_SUFFIX_QUERY);
		log.log(Log.FINEST, "query", pgqry);
		
		if (MRAConstantsVO.SCHEDULER_JOB_GPABLG.equals(billingScheduleFilterVO.getSource())
				|| MRAConstantsVO.SCHEDULER_JOB_PASSBLG.equals(billingScheduleFilterVO.getSource())||MRAConstantsVO.PASS_BILLINGPERIOD_VALIDATION.equals(billingScheduleFilterVO.getSource())) {
			List<BillingScheduleDetailsVO> billingScheduleDetailsVOs = pgqry
					.getResultList(new BillingScheduleMasterMapper());
			return new Page<>(billingScheduleDetailsVOs, 0, 0, 0, 0, 0, false);
		} else {
			return pgqry.getPage(pageNumber);
		}
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#validateBillingSchedulemaster(com.ibsplc.icargo.business.mail.mra.vo.BillingScheduleDetailsVO)
	 *	Added by 			: a-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param BillingScheduleDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)
			throws SystemException {
		log.entering("validateBillingSchedulemaster", "validateGpaBillingPeriod");
		boolean flag = true;
		int index = 0;
		String year = "";
		
		Query query = getQueryManager().createNativeQuery("SELECT");
		if (!billingScheduleDetailsVO.getParamsList().isEmpty()) { 
			if(isOracleDataSource()) {
			 query.append(" CASE WHEN LISTAGG(PAR.PARVAL, ',')  WITHIN GROUP (ORDER BY PAR.PARVAL) = ?");
			}
			else {
			query.append(" CASE WHEN STRING_AGG( PAR.PARVAL, ',') = ?");
			}
			StringBuilder paramBldr = new StringBuilder();
			
			for (BillingParameterVO params : billingScheduleDetailsVO.getParamsList()) {
				paramBldr.append(params.getParameterValue());
				paramBldr.append(",");
			}
			paramBldr.deleteCharAt(paramBldr.length() - 1);
			
			query.setParameter(++index, paramBldr.toString());
			query.append(" THEN 1 ELSE 0 END AS VAL");
		} else {
			query.append(" COUNT(*) VAL");
		}
		query.append(getQueryManager().createNamedNativeQuery(FIND_BILLING_PERIODS).toString());
		query.setParameter(++index, billingScheduleDetailsVO.getCompanyCode());
		query.setParameter(++index, billingScheduleDetailsVO.getBillingType());
		query.setParameter(++index, billingScheduleDetailsVO.getBillingPeriod());
		year = billingScheduleDetailsVO.getPeriodNumber().substring(0, 4);
		query.setParameter(++index, year);
		query.setParameter(++index, billingScheduleDetailsVO.getPeriodNumber());
		if (!billingScheduleDetailsVO.getParamsList().isEmpty()) {
			query.append(" AND PAR.PARVAL IN (");
			StringBuilder paramBldr = new StringBuilder();
			for (BillingParameterVO params : billingScheduleDetailsVO.getParamsList()) {
				paramBldr.append(" ?,");
				query.setParameter(++index, params.getParameterValue());
			}
			paramBldr.deleteCharAt(paramBldr.length() - 1);
			paramBldr.append(" ) ");
			query.append(paramBldr.toString());
		}
		log.exiting(CLASS_NAME, "validateBillingSchedulemaster");
		log.log(Log.INFO, "\n\n ***Final query--->>>>", query);
		Mapper<Integer> integerMapper =getIntMapper("VAL");
		int count = query.getSingleResult(integerMapper).intValue();
		if (count==0) {
			flag = false;
		}
		return flag;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#saveMRADataForProvisionalRate(com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO)
	 *	Added by 			: A-10647 on 23-Nov-2022
	 * 	Used for 	:
	 *	Parameters	:	@param rateAuditVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	public String saveMRADataForProvisionalRate(RateAuditVO rateAuditVO) throws PersistenceException, SystemException {
  	  Procedure provisionalRateProcedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_IMPORT_PROVISIONAL_RATE_DETAILS);
  	int index=0;
  	Collection<RateAuditDetailsVO> rateAuditDetails=rateAuditVO.getRateAuditDetails();
  	RateAuditDetailsVO rateAuditDetailVO =rateAuditDetails.iterator().next();
  	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
  	String scanPort = logonAttributes.getAirportCode();
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	LocalDate scanDate = new LocalDate(scanPort,Location.ARP,rateAuditVO.getScannedDate().toSqlTimeStamp());
	Date currentDateFinal = currentDate.toSqlTimeStamp();
	provisionalRateProcedure.setParameter(++index, rateAuditVO.getCompanyCode());
	provisionalRateProcedure.setParameter(++index, rateAuditVO.getMailSequenceNumber());
	provisionalRateProcedure.setParameter(++index,rateAuditDetailVO.getSource());
	provisionalRateProcedure.setParameter(++index,scanDate.toSqlTimeStamp());
	provisionalRateProcedure.setParameter(++index,logonAttributes.getAirportCode());
	provisionalRateProcedure.setParameter(++index,logonAttributes.getUserId());
	provisionalRateProcedure.setParameter(++index,currentDateFinal);
	provisionalRateProcedure.setOutParameter(++index, SqlType.STRING); 
	provisionalRateProcedure.execute();
	return  (String) provisionalRateProcedure.getParameter(index);
	}
	
	public void calculateProvisionalRate(Long noOfRecords) throws PersistenceException, SystemException{
		Procedure calculateUpfrontRateProcedure = getQueryManager().createNamedNativeProcedure(CALCULATE_PROVISIONAL_RATE);
		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		Date currentDateFinal = currentDate.toSqlTimeStamp();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		calculateUpfrontRateProcedure.setParameter(++index, logonAttributes.getCompanyCode());
		calculateUpfrontRateProcedure.setParameter(++index, noOfRecords);
		calculateUpfrontRateProcedure.setParameter(++index, logonAttributes.getUserId());
		calculateUpfrontRateProcedure.setParameter(++index, currentDateFinal);
		calculateUpfrontRateProcedure.execute();
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#findMRAGLAccountingEntries(com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO)
	 *	Added by 			: A-10164 on 15-Feb-2023
	 * 	Used for 	: MRA GL interface
	 *	Parameters	:	@param glInterfaceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public List<GLInterfaceDetailVO> findMRAGLAccountingEntries(GLInterfaceFilterVO glInterfaceFilterVO)
			throws SystemException {
		int index = 0;
		Query accounitngQuery = getQueryManager().createNamedNativeQuery(MRA_DEFAULTS_GLINTERFACEDATA);
		accounitngQuery.setParameter(++index, glInterfaceFilterVO.getCompanyCode());
		if (("Y").equals(glInterfaceFilterVO.getRegenerateFlag())) {
			accounitngQuery.append(" AND UPPER(TXN.FILNAM) = UPPER(?) ");
			accounitngQuery.setParameter(++index, glInterfaceFilterVO.getRegenerateFileName());
		} else {
			accounitngQuery.append(" AND COALESCE(TXN.INTFCDFLG, 'N') = 'N' ");
		}
		if (Objects.nonNull(glInterfaceFilterVO.getFromDate()) && Objects.nonNull(glInterfaceFilterVO.getToDate())) {
			accounitngQuery.append(
					" AND TO_NUMBER(TO_CHAR(TXN.CREDAT, 'YYYYMMDD')) >= ? AND TO_NUMBER(TO_CHAR(TXN.CREDAT, 'YYYYMMDD')) <= ? ");
			accounitngQuery.setParameter(++index,
					Integer.parseInt(glInterfaceFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
			accounitngQuery.setParameter(++index,
					Integer.parseInt(glInterfaceFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
		} else if (Objects.nonNull(glInterfaceFilterVO.getAccountMonth())) {
			accounitngQuery.append(" AND TXN.ACCMON||TXN.FINYER = ? ");
			accounitngQuery.setParameter(++index, glInterfaceFilterVO.getAccountMonth());
		} else {
			accounitngQuery.append(" AND TO_NUMBER(TO_CHAR(TXN.CREDAT, 'YYYYMMDD')) < ? ");
			LocalDate toDate = new LocalDate("SEA", Location.STN, true);
			accounitngQuery.setParameter(++index, Integer.parseInt(toDate.toStringFormat(DATE).substring(0, 8)));
			accounitngQuery.append(" AND TXN.ACCMON||TXN.FINYER= ? ");
			String accMonth = new SimpleDateFormat("MMM").format(toDate.addDays(-(glInterfaceFilterVO.getDayOfMonth())).getTimeInMillis())
					.toUpperCase();
			int accountYear = toDate.get(Calendar.YEAR);
			accounitngQuery.setParameter(++index, accMonth + accountYear);
		}
		accounitngQuery.append(" ORDER BY TXN.CREDAT");
		return accounitngQuery.getResultList(new MRAGLInterfaceMultiMapper());
	}
}
