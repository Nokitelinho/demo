/*
 * GPABillingController.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import com.ibm.icu.math.BigDecimal;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.RebillRemarksDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.AuditHelper;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.CCADetail;
import com.ibsplc.icargo.business.mail.mra.defaults.ChangeStatusException;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingDetails;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.GenerateInvoiceJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.POMailSummaryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.ProformaInvoiceDiffReportVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MRADefaultProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingMRAProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MessageBrokerConfigProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
//import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressDetailVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressVO;
import com.ibsplc.icargo.business.msgbroker.config.mode.vo.MessageModeParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateParameterMasterVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateConfigVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateFilterVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.jobscheduler.SchedulerAgent;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.message.vo.EmailVO;
import com.ibsplc.icargo.framework.message.vo.GenericMessageVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.agent.ReportAgentInstance;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.GenerationFailedException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateFilterVO;


/**
 * @author A-1556
 *
 * @generated
 *            "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 *            Revision History
 *
 *            Version Date Author Description
 *
 *            0.1 Jan 8, 2007 Philip Initial draft 0.2 Jan 18,2007 Kiran Added
 *            the method findAllInvoices 0.3 Jan 18,2007 Indu Added the method
 *            findGPABillingEntries,changeBillingStatus 0.4 Jan 18,2007 Prem
 *            Kumar.M Added the methods findCN51CN66Details,saveCN66Observations
 *            Mar 26,2007 Prem Kumar.M Added findSettlementDetails method Mar
 *            27,2007 Prem Kumar.M Added findSettlementHistory method
 *
 *
 */
@Module("mail")
@SubModule("mra")
public class GPABillingController {

	private static final String CLASS_NAME = "GPABillingController";

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String KEY_BILLING_STATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";

	private static final String INVOICE_STATUS = "mra.gpabilling.invoicestatus";
	private static final String BILLING_STATUS = "mailtracking.mra.gpabilling.gpabillingstatus";

	private static final String PROFORMA_INVOICEDIFF = "mra.defaults.despatchenqtype";

	private static final String SYS_PARA_ACCOUNTING_ENABLED = "cra.accounting.isaccountingenabled";

	private static final String WITHDRAWN_DIRECT = "WD";

	private static final String DATA_SEPARATOR = "+";
	private static final String RECORD_DELIMTER = "'";
	private static final String NEW_LINE = "\n";
	private static final String BLANK_DATA = "";
	private static final String FUEL_SURCHARGE_IND_RATE = "R";
	private static final String CHARGING_TYPE = "K";// Charging Per KG
	private static final String EINVOICE_HEADER = "HDR";
	private static final String EINVOICE_TRAILER = "TRL";
	private static final String SYS_PARAM_HONGKONGPOST = "mailtracking.mra.gpabilling.einvoicehongkongpost";
	private static final String SYS_PARAM_HONGKONGPOST_EMAILID = "mailtracking.mra.gpabilling.hongkongpostemail";
	private static final String BLGSTA_PROFORMA_ONHOLD = "PO";
	public static final String STRING_VALUE_HYPHEN = "-";
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String MAIL_SUBCLS_TYPE_SP = "SP";

	private static final String SETTLED = "S";
	private static final String DIFFERENCE = "D";
	private static final String STLREF_KEY ="STLREF_KEY";
	private static final String SYS_PARAM_EXGRATBAS="mailtracking.mra.gpabilling.exchangeratebasisforcontractcurrtobillingcurr";
	private static final String SYS_PARAM_EXGRATDATBAS="mailtracking.mra.gpabilling.conversiondatebasisforcontractcurrtobillingcurrr";
	private Map <String, Double>exchangeRateMap ;
	//Added by A-4809 for ICRD-42160 Starts
	private static final String OK="OK";
	private static final String MODULE_NAME = "mail.mra.gpabilling";
    private static final String PRODUCT_CODE="mail";
    private static final String SUB_PRODUCTCODE="mra";
    private static final String BUNDLE="geninvresources";
    private static final String REPORT_ID="RPRMTK088";
    private static final String REPORT_TITLE="Invoice Report";
    private static final String MSGBROKER_EMAIL_INTERFACE_PARAMETER = "msgbroker.message.emailinterfacesystem";
	private static final String MSGBROKER_EMAIL_INTERFACE = "EMAIL";
	private static final String GENINVMRA="GENINVMRA";
	private static final String MRA_STL_UPLOAD="MRASTLUPD";
	private static final String LOCKREMARK="MANUAL LOCK";
	private static final String LOCkDESC="GENERATE INVOICE LOCK";
	private static final String STL_LOCK_DESC="SETTLEMENT UPLOAD LOCK";
	private static final String GENINV="GENINV";
	private static final String STL_UPLOAD="STLUPD";
	private static final String LOCKSCREENID= "MRA009";
	private static final String STL_LOCKSCREENID= "SHR118";
	//Added by A-4809 for ICRD-42160 Ends
	//Added by A-4809 for ICRD-41320
	private static final String MINAMTCHK="MINAMNTCHK";
	//Added by A-4809 for ICRD-39860
	private static final String BILLINGRPTLEVEL="mailtracking.mra.gpabilling.levelforgpabillingreports";
	private static final String DSNLEVELIMPORT="mailtracking.defaults.DsnLevelImportToMRA";
	private static final String OVERRIDEROUNDING="mailtracking.mra.overrideroundingvalue";
	private static final String COMMA=",";
	private static final String BLANK="";
	private static String isSuccessFlag="N";
	private static final String MRA_INV_SETTLEMENT_MAIL_ERROR = "mailtracking.mra.gpabilling.msg.err.invoicenotexist";
	//Added by A-7794 as part of ICRD-194277
	private static final String UPDATAE = "U";
	private static final String INVOICE_SETTL = "I";
	private static final String SHARED_AIRLINE_BASECURRENCY  = "shared.airline.basecurrency";//Added by A-7929	for ICRD-257574
	private static final String MRAEXCELUPD  = "MRASTLUPD";
	private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit"; // added by A-9002
	private static final String GENPASMRA="GENPASMRA";
	private static final String GENPASS="GENPAS";
	private static final String LOCKDESCPAS="GENERATE MRA PASS LOCK";
	private static final String MRA_GPA_PASS_FILTYP="MAL_MRA_GPAPASS_FIL";
	private static final String MRA_GPA_PAS_CMPCOD="CMPCOD";
	private static final String MRA_GPA_PAS_PRDNUM="PRDNUM";
	private static final String MRA_GPA_PAS_GPACOD="GPACOD";
	private static final String MRA_GPA_PAS_BRHOFC="BRHOFC";
	private static final String MRA_GPA_PAS_SEQNUM="SEQNUM";
	private static final String MRA_GPA_PAS_FILFRM="FILFRM";
	private static final String MRA_GPA_PAS_FILNAM="FILNAM";
	private static final String MRA_GPA_PAS_ADDNEW="ADDNEW";
	private static final String MRA_GPA_PAS_LOGSTN="LOGSTN";
	private static final String MRA_GPA_PAS_TRGPNT="TRGPNT";
	private static final String MRA_GPA_PAS_UPDUSR="UPDUSR";
	private static final String COMPLETED="C";
	//Added by A-7794 as part of IASCB-102783
	private static final String INVDAT= "INVDAT";
	private static final String INVNUM= "INVNUM";
	private static final String SECTOR= "SECTOR";
	private static final String BLGPRDFRM= "BLGPRDFRM";
	private static final String BLGPRDTOO= "BLGPRDTOO";
	private static final String TOTAMTBLGCUR= "TOTAMTBLGCUR";
	private static final String BLGCURCOD= "BLGCURCOD";
	private static final String POANAM= "POANAM";
	private static final String POAADR= "POAADR";
	private static final String CITY= "CITY";
	private static final String STATE= "STATE";
	private static final String COUNTRY= "COUNTRY";
	private static final String PHONE1= "PHONE1";
	private static final String PHONE2= "PHONE2";
	private static final String FAX= "FAX";
	private static final String VATNUM= "VATNUM";
	private static final String BLDPRD= "BLDPRD";
	private static final String MALCTGCOD= "MALCTGCOD";
	private static final String OVRRND= "OVRRND";
	private static final String CORADR= "CORADR";
	private static final String SGNONE= "SGNONE";
	private static final String DSGONE= "DSGONE";
	private static final String SGNTWO= "SGNTWO";
	private static final String DSGTWO= "DSGTWO";
	private static final String CURCOD= "CURCOD";
	private static final String BNKNAM= "BNKNAM";
	private static final String BNKBRC= "BNKBRC";
	private static final String ACCNUM= "ACCNUM";
	private static final String CTYNAM= "CTYNAM";
	private static final String SWTCOD= "SWTCOD";
	private static final String IBNNUM= "IBNNUM";
	private static final String RMK= "RMK";
	private static final String GPA= "GPA";
	private static final String PERIOD= "PERIOD";
	private static final String CNTNAM= "CNTNAM";
	
	private static final String INVOICENUMBER= "invoiceNumber";
	private static final String SECTOR1= "sector";
	private static final String FROMDATESTRING= "fromDateString";
	private static final String TODATESTRING= "toDateString";
	private static final String TOTALAMTINBILLINGCURRSTRING= "totalAmtinBillingCurrString";
	private static final String BILLINGCURRENCYCODE= "billingCurrencyCode";
	private static final String PANAME= "paName";
	private static final String ADDRESS= "address";
	private static final String CITY1= "city";
	private static final String STATE1= "state";
	private static final String COUNTRY1= "country";
	private static final String PHONE11= "phone1";
	private static final String PHONE22= "phone2";
	private static final String FAX1= "fax";
	private static final String DUEDAYS= "duedays";
	private static final String BILLEDDATESTRING= "billedDateString";
	private static final String MAILCATEGORYCODE= "mailCategoryCode";
	private static final String OVERRIDEROUNDING1= "overrideRounding";
	private static final String CORRESPONDENCEADDRESS= "CorrespondenceAddress";
	private static final String SIGNATORONE= "signatorOne";
	private static final String DESIGNATORONE= "designatorOne";
	private static final String SIGNATORTWO= "signatorTwo";
	private static final String DESIGNATORTWO= "designatorTwo";
	private static final String CURRENCY= "currency";
	private static final String BANKNAME= "bankName";
	private static final String BRANCH= "branch";
	private static final String ACCNO= "accNo";
	private static final String BANKCITY= "bankCity";
	private static final String BANKCOUNTRY= "bankCountry";
	private static final String SWIFTCODE= "swiftCode";
	private static final String IBANNO= "ibanNo";
	private static final String BILLEDDATE= "invDateMMMformat";
	private static final String FREETEXT= "freeText";
	private static final String GPACODE= "gpaCode";
	
	private static final String AIRLINENAME= "airlineName";
	private static final String BILLINGADDRESS= "billingAddress";
	private static final String BILLINGPHONE1= "billingPhone1";
	private static final String BILLINGPHONE2= "billingPhone2";
	private static final String BILLINGFAX= "billingFax";
	private static final String MRABTHSTL="MRABTHSTL";

    private static final String TOTAMTCP="TOTAMTCP";

    private static final String ORGCOD= "ORGCOD";
    private static final String DSTCOD= "DSTCOD";
    private static final String DSN= "DSN";
    private static final String CNTIDR= "CNTIDR";
    private static final String FLTNUM= "FLTNUM";
    private static final String FLTDAT= "FLTDAT";
    private static final String MALSUBCLS= "MALSUBCLS";
    private static final String TOTWGT= "TOTWGT";
    private static final String BLDAMT= "BLDAMT";
    private static final String SRVTAX= "SRVTAX";
    private static final String NETAMT= "NETAMT";
    private static final String PONAM= "PONAM";
    private static final String RATE= "RATE";
    private static final String MCA= "MCA";
    private static final String TOTAL= "TOTAL";
    private static final String VATAMT="VATAMT";
    private static final String VALCHG="VALCHG";
    private static final String TOTALWEIGHT= "totalWeight";
    private static final String APPLICABLERATE= "applicableRate";
    private static final String APLRAT="APLRAT";
    private static final String CN66_REPORT="generateCN66Report";
    private static final String C51SMYCRTCURCOD="c51smycrtcurcod";
    private static final String AIRLINE_CODE="airlineCode";
    private static final String SERVICE_TAX="serviceTax";
    private static final String NET_AMOUNT="netAmount";
    private static final String MAILSUBCLASS="mailSubclass";
    private static final String AMOUNT="amount";
    private static final String STATUS_BILING="billingStatus1234";
    private static final String MONTH_FLAG="monthFlag";
    private static final String SURCHARGE="SURCHARGE";
    private static final String TOTALAMOUNTLC="totalAmountLC";
    private static final String TOTALAMOUNTCP="totalAmountCP";
    private static final String CTRCURCOD="CTRCURCOD";
    private static final String VALCHARGES="valCharges";
    private static final String TOTAMTLC="TOTAMTLC";
    private static final String FLAG_MONTH="MONTHFLAG";
    private static final String NETTOTAL="NETTOTAL";
    private static final String POACOD="POACOD";
    private static final String BILLFRM="BILLFRM";
    private static final String TOTAL_NET_AMOUNT="totalNetAmount";
    private static final String TOTAL_BILLED_AMOUNT="totalBilledAmount";
    private static final String SCALAR_TOTAL_BILLED_AMOUNT="scalarTotalBilledAmount";
    private static final String POA_ADDRESS="PoaAddress";
    private static final String C51SMYTOTBLDAMT="c51smytotbldamt";
    private static final String C51DTLINVDAT="c51dtlinvdat";
    private static final String SUR_CHARGE="surCharge";
    private static final String PPOANAM="poanam";
    private static final String VAT="vatNumber";
    private static final String INVDATEMMM="invDateMMMformat";
    private static final String INVNUMFIN="invNumberFinancial";
    private static final String VATNUM1="VATNUM1";
	/**
	 * Finds and returns the GpaBillingInvoicedetails based on the filter
	 * criteria
	 *
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws SystemException
	 */
	public CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
	throws SystemException {
		log.entering(CLASS_NAME, "findGpaBillingInvoice");

		return CN51Summary
		.findGpaBillingInvoiceEnquiryDetails(gpaBillingInvoiceEnquiryFilterVO);
	}

	/**
	 * @author A-3434 save the updated status of invoices.
	 *
	 * @param cN66DetailsVO
	 *
	 * @throws SystemException
	 */
	public void saveBillingStatus(CN66DetailsVO cN66DetailsVO)
	throws SystemException, ChangeStatusException {
		log.entering(CLASS_NAME, "changeBillingStatus");
		String companyCode = cN66DetailsVO.getCompanyCode();
		String invNo = cN66DetailsVO.getInvoiceNumber();
		String gpaCode = cN66DetailsVO.getGpaCode();
		Integer sequenceNo = cN66DetailsVO.getSequenceNumber();
		Integer invSerialNo = cN66DetailsVO.getInvSerialNumber();//Modified and added getInvSerialNumber() for ICRD-211662
		String billingBasis = cN66DetailsVO.getBillingBasis();
		String conDocNum = cN66DetailsVO.getConsDocNo();
		long mailSequenceNumber = cN66DetailsVO.getMailSequenceNumber();
		int conSeqNum = Integer.parseInt(cN66DetailsVO.getConsSeqNo());
		CN66Details cn66Details = null;
		CN51Summary cn51Summary = null;
		MRABillingDetails mraBillingDetails = null;
		MRABillingMaster mraBillingMaster = null;
		Double totalAmountInBillingCurrency = 0.0;
		Double amount = cN66DetailsVO.getActualAmount().getAmount();
		Collection<CN51Details> cN51Details = new ArrayList<CN51Details>();
		/*
		 * for updating billingStatus in MTKGPAC66DTL
		 */
		try {
			//Modified by A-7794 as part of MRA revamp
			cn66Details = CN66Details.find(companyCode, invNo, gpaCode,
					sequenceNo,invSerialNo);
			log.log(Log.INFO, "status.....", cN66DetailsVO.getBillingStatus());
			// deleting entries with status WD
			if (WITHDRAWN_DIRECT.equals(cN66DetailsVO.getBillingStatus())) {
				cn51Summary = CN51Summary.find(companyCode, invNo,invSerialNo, gpaCode);
				cN51Details = cn51Summary.getCn51details();
				totalAmountInBillingCurrency = cn51Summary
				.getTotalAmountInBillingCurr();
				// amount becomes present amount in billing currency -amount
				cn51Summary
				.setTotalAmountInBillingCurr(totalAmountInBillingCurrency
						- amount);
				if (cN51Details != null) {
					for (CN51Details cN51Detail : cN51Details) {
						if (cn66Details.getMailCategoryCode().equals(
								cN51Detail.getMailCategoryCode())
								&& cn66Details.getActualSubclass().equals(
										cN51Detail.getMailSubclass())
										&& cn66Details.getBillSectorOrigin().equals(
												cN51Detail.getOrigin())
												&& cn66Details.getBillSectorToo().equals(
														cN51Detail.getDestination())
														&& cn66Details.getApplicableRate() == cN51Detail
														.getApplicableRate()
														&& cn66Details.getContrctCurrencyCode().equals(
																cN51Detail.getBillingCurrencyCode())) {
							double totalCN51AmtInBillingCurrency = cN51Detail
							.getTotalAmountinBillingCurr()
							- cn66Details.getBilledAmountInBillingCurr();
							cN51Detail.setTotalAmountinBillingCurr(cN51Detail
									.getTotalAmountinBillingCurr()
									- cn66Details.getBilledAmountInBillingCurr());
							cN51Detail.setTotalWeight(cN51Detail
									.getTotalWeight()
									- cn66Details.getTotalWeight());
							log.log(Log.FINE,
									"totalCN51AmtInBillingCurrency===>>",
									totalCN51AmtInBillingCurrency);
							if (totalCN51AmtInBillingCurrency == 0.0) {
								log.log(Log.FINE,
								"inside if totalCN51AmtInBillingCur===>>");
								try {// removing the entity
									cN51Detail.remove();
								} catch (RemoveException e) {
									e.getErrorCode();
								}
							}
						}
					}
				}
				cn66Details.remove();
			} else {
				cn66Details.setLastUpdateTime(cN66DetailsVO
						.getLastupdatedTime());
				cn66Details.setBillingStatus(cN66DetailsVO.getBillingStatus());
				cn66Details.setRemarks(cN66DetailsVO.getRemarks());
			}
		} catch (FinderException e) {
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}

		catch (RemoveException e) {
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}
		/*
		 * for reflecting the billingStatus status change in MTKMRABLGDTL
		 */
		MRABillingDetailsVO mraBillingDetailsvo = new MRABillingDetailsVO();
		mraBillingDetailsvo.setIsFindFlag(true);

		try {
			mraBillingMaster = MRABillingMaster.find(companyCode, cn66Details.getCn66DetailsPK().getMailsequenceNumber());
		} catch (FinderException e) {
			// mraBillingMaster not found
			e.getErrorCode();
			/*
			 * new method findGpaBillingDetails is added for Bug 30352 At the
			 * time of rateAudit user can change the poaCode.It may not be the
			 * GpaCode in MTKGPAC66DTL.At that time,We have to take the PoaCode
			 * in which updBillTo in MTKMRABLGDTL is the gpaCode in
			 * MTKGPAC66DTL.
			 */
			log.log(log.INFO, "in catch..");

			mraBillingDetailsvo = MRABillingDetails
			.findGpaBillingDetails(cN66DetailsVO);
			mraBillingDetailsvo.setIsFindFlag(false);
			// setting gpaCode for auditDSNForBillingStatus
			cN66DetailsVO.setGpaCode(mraBillingDetailsvo.getPoaCode());
			// Finding mraBillingMaster with the PoaCode got from the above
			// method
			try {
				mraBillingMaster = MRABillingMaster.find(companyCode,cn66Details.getCn66DetailsPK().getMailsequenceNumber());
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
			}

			// Finding mraBillingDetails with the PoaCode got from the above
			// method
			try {
				//Modified by A-7794 as part of MRA revamp
				mraBillingDetails = MRABillingDetails.find(companyCode,mailSequenceNumber,sequenceNo);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
			}
		}
		log.log(Log.INFO, "IsFindFlag..", mraBillingDetailsvo.getIsFindFlag());
		if (mraBillingDetailsvo.getIsFindFlag()) {
			try {
				//Modified by A-7794 as part of MRA revamp
				mraBillingDetails = MRABillingDetails.find(companyCode,mailSequenceNumber,sequenceNo);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getMessage(),
						finderException);
			}
		}

		/*
		 * checks whether a despatch contains an actual CCA if it contains an
		 * actual CCA,it will throw the business exception.Otherwise
		 * billingstatus will change.
		 */
		ArrayList<CCADetail> ccadetails = null;
		ccadetails = new ArrayList<CCADetail>(mraBillingMaster.getCCADetail());

		Boolean isActual = mraBillingMaster.findActualCCA(ccadetails);

		log.log(Log.INFO, "isActual", isActual);
		if (isActual
				&& (!(mraBillingDetails.getBlgStatus().equals(cN66DetailsVO
						.getBillingStatus())))) {
			log.log(log.INFO, "Actual cca");
			ErrorVO error = new ErrorVO(
					ChangeStatusException.MRA_ACTUALCCA_EXISTS);
			ChangeStatusException changeStatusException = new ChangeStatusException();
			changeStatusException.addError(error);
			throw changeStatusException;
		}

		else if (!(isActual)
				&& (!(mraBillingDetails.getBlgStatus().equals(cN66DetailsVO
						.getBillingStatus())))) {
			log.log(log.INFO, "not Actual");

			mraBillingDetails.setBlgStatus(cN66DetailsVO.getBillingStatus());
			mraBillingDetails.setRemarks(cN66DetailsVO.getRemarks());
			log.log(Log.INFO, " updating MTKMRABLGDTL...BlgStatus ",
					mraBillingDetails.getBlgStatus());
		}

		/*
		 * updated by meenu for Auditing AirNZ681
		 */
		String sysparValue = null;
		// the system parameter for audit value Y for activate audit and N for
		// deactivate audit
		String sysparCode = "mailtracking.mra.dsnauditrequired";

		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(sysparCode);

		HashMap<String, String> systemParameterMap = null;
		try {
			systemParameterMap = (HashMap<String, String>) new SharedDefaultsProxy()
			.findSystemParameterByCodes(systemParameters);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(sysparCode);
			// if value is Y then call the audit method in helper
			if ("Y".equals(sysparValue)) {
				AuditHelper.auditDSNForBillingStatus(cN66DetailsVO);
			}
		}

	}

	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws SystemException
	 */
	public Page<CN51SummaryVO> findAllInvoices(
			CN51SummaryFilterVO cn51SummaryFilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findAllInvoices");
		log.log(Log.INFO, " the filter for getting Invoices ",
				cn51SummaryFilterVO);
		return CN51Summary.findAllInvoices(cn51SummaryFilterVO);
	}

	/**
	 * Finds and returns the CN51 and CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return CN51CN66VO
	 * @throws SystemException
	 */
	public CN51CN66VO findCN51CN66Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws SystemException {
		log.entering(CLASS_NAME, "findCN51CN66Details");
		CN51CN66VO cn51cn66VO = CN51Summary
		.findCN51CN66Details(cn51CN66FilterVO);
		log.exiting(CLASS_NAME, "findCN51CN66Details");
		return cn51cn66VO;
	}

	/**
	 * Finds tand returns the GPA Billing entries available This includes
	 * billed, billable and on hold despatches
	 *
	 * @param gpaBillingEntriesFilterVO
	 * @return Collection<GPABillingDetailsVO>
	 * @throws SystemException
	 */

	public Collection<GPABillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException {

		log.entering(CLASS_NAME, "listGPABillingDetails");
		Collection<GPABillingDetailsVO> gpaBillingDetailsVOs = GPABillingMaster
		.findGPABillingEntries(gpaBillingEntriesFilterVO);
		log.exiting(CLASS_NAME, "listGPABillingDetails");
		return gpaBillingDetailsVOs;

	}

	/**
	 * Changes the staus of GPA billing entries to Billable/On Hold
	 *
	 * @param gpaBillingStatusVO
	 * @throws SystemException
	 */

	/*
	 * public void changeBillingStatus( Collection<GPABillingStatusVO>
	 * gpaBillingStatusVO) throws SystemException{ log.entering(CLASS_NAME,
	 * "changeBillingStatus"); ArrayList<GPABillingStatusVO>
	 * gpaBillingStatusVOs=new
	 * ArrayList<GPABillingStatusVO>(gpaBillingStatusVO);
	 * log.log(log.INFO,"GPABILLING STATUS VOS "+gpaBillingStatusVOs);
	 * MRABillingMaster mraBillingMaster=new MRABillingMaster(); try {
	 *
	 *
	 * //mraBillingMaster=MRABillingMaster.find(gpaBillingStatusVOs.get(0).
	 * getCsgSequenceNumber(),
	 * //gpaBillingStatusVOs.get(0).getCsgDocumentNumber(
	 * ),gpaBillingStatusVOs.get(0).getCompanyCode(),
	 * //gpaBillingStatusVOs.get(0
	 * ).getBillingBasis(),gpaBillingStatusVOs.get(0).getPoaCode());
	 * mraBillingMaster.updateBillingStatus(gpaBillingStatusVO); } catch
	 * (FinderException e) { //printStackTraccee()(); throw new
	 * SystemException(e.getMessage()); } catch (CreateException e) {
	 * //printStackTraccee()(); throw new SystemException(e.getMessage()); }
	 * catch (RemoveException e) { //printStackTraccee()(); throw new
	 * SystemException(e.getMessage()); } catch (ChangeStatusException e) {
	 * //printStackTraccee()(); throw new SystemException(e.getMessage()); }
	 *
	 * catch (ContainsActualCCAException e) { //printStackTraccee()(); throw new
	 * SystemException(e.getMessage()); }
	 *
	 * }
	 */
	/**
	 * @author A-2280 Saves the remarks against CN66 details
	 *
	 * @param cn66DetailsVOs
	 * @throws SystemException
	 */

	public void saveCN66Observations(Collection<CN66DetailsVO> cn66DetailsVOs)
	throws SystemException {

		log.entering(CLASS_NAME, "saveCN66Observations");
		if (cn66DetailsVOs != null && cn66DetailsVOs.size() > 0) {
			for (CN66DetailsVO cn66DetailsVO : cn66DetailsVOs) {
				CN66Details cn66Details = null;
				try {
					//Modified by A-7794 as part of MRA revamp
					cn66Details = CN66Details.find(
							cn66DetailsVO.getCompanyCode(),
							cn66DetailsVO.getInvoiceNumber(),
							cn66DetailsVO.getGpaCode(),
							cn66DetailsVO.getSequenceNumber(),
							cn66DetailsVO.getInvSerialNumber());//Modified and added INVSERNUM for ICRD-211662
					if (cn66Details != null) {
						log.log(Log.INFO, "Updating Remarks for invoice--> ",
								cn66DetailsVO.getInvoiceNumber());
						cn66Details.setRemarks(cn66DetailsVO.getRemarks());
						cn66Details.setLastUpdateTime(cn66DetailsVO
								.getLastupdatedTime());
						cn66Details.setLastUpdatedUser(cn66DetailsVO
								.getLastUpdatedUser());
					}
				} catch (FinderException e) {
					e.getErrorCode();
					throw new SystemException(e.getMessage());
				}
			}
		}

		log.exiting(CLASS_NAME, "saveCN66Observations");

	}

	/**
	 * generateInvoice
	 *
	 * @param generateInvoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	/**public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException, MailTrackingMRABusinessException {
		GPABillingMaster.generateInvoice(generateInvoiceFilterVO);
	}**/
	/**
	 *
	 * 	Method		:	GPABillingController.withdrawMailbags
	 *	Added by 	:	A-6991 on 08-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException {
		new GPABillingMaster().withdrawMailbags(documentBillingDetailsVOs);
	}
	/**
	 *
	 * 	Method		:	GPABillingController.finalizeProformaInvoice
	 *	Added by 	:	A-6991 on 08-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param documentBillingDetailsVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
			throws SystemException {
		new GPABillingMaster().finalizeProformaInvoice(summaryVOs);
	}

	public void withdrawInvoice(CN51SummaryVO summaryVO) throws SystemException{
		new GPABillingMaster().withdrawInvoice(summaryVO);
	}

	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO invoiceLovVO)
	throws SystemException {
		return CN51Summary.findInvoiceLov(invoiceLovVO);
	}

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return map
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	//Modified the method as a part of ICRD-193493 by A-7540
	public Map<String, Object> generateCN66Report(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		log.entering("GPABillingController", "generateCN66Report");
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
				.getFilterValues().iterator().next();
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
				.generateCN66Report(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Map<String,String> systemParameterMap =null;
		String dsnLevelImport=null;
		String billingReportLevel=null;
		String curKey =null;
		String prevKey =null;
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add(DSNLEVELIMPORT);
		systemParCodes.add(BILLINGRPTLEVEL);
		systemParCodes.add(OVERRIDEROUNDING);
		/*added by A-8149 for ICRD-257237 --> */ systemParCodes.add(SHARED_AIRLINE_BASECURRENCY);
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);

			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}

		if(systemParameterMap !=null && systemParameterMap.size()>0){
			dsnLevelImport =systemParameterMap.get(DSNLEVELIMPORT);
			billingReportLevel = systemParameterMap.get(BILLINGRPTLEVEL);
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
		}
		ArrayList<CN66DetailsVO> cn66DetailsVOs = null;

		if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			CN66DetailsVO cN66DetailsVOforRpt = null;
			cn66DetailsVOs = new ArrayList<CN66DetailsVO>();
			for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				double amount=0.0;
				double serviceTax=0.0;

				double netAmount=0.0;
				double totalWeight=0.0;
				int count =0;

				curKey = new StringBuilder().append(cN66DetailsVO.getCompanyCode()).
						append(cN66DetailsVO.getInvoiceNumber()).append(cN66DetailsVO.getDsn()).toString();
				 if(cN66DetailsVO.getFlightDate() != null) {
					 String flightDateInString = TimeConvertor.toStringFormat(cN66DetailsVO.getFlightDate().toCalendar(),"dd-MMM-yy");
					 cN66DetailsVO.setFlightDateInString(flightDateInString);
				 }
				if(CN66DetailsVO.FLAG_YES.equals(dsnLevelImport)){
					cn66DetailsVOs.add(cN66DetailsVO);
				}else if(CN66DetailsVO.FLAG_NO.equals(dsnLevelImport)){
					if("M".equals(billingReportLevel)){
						cn66DetailsVOs.add(cN66DetailsVO);
					}else if("D".equalsIgnoreCase(billingReportLevel)){
						if(!curKey.equals(prevKey)){
							prevKey = curKey;
							cN66DetailsVO.setRsn("");
							cN66DetailsVO.setRegInd("");
							cN66DetailsVO.setHsn("");
							cn66DetailsVOs.add(cN66DetailsVO);
						}else{
							prevKey = curKey;
							count = cn66DetailsVOs.size();
							cN66DetailsVOforRpt = cn66DetailsVOs.get(count-1);
							totalWeight = cN66DetailsVOforRpt.getTotalWeight()+cN66DetailsVO.getTotalWeight();
							amount = cN66DetailsVOforRpt.getAmount()+cN66DetailsVO.getAmount();
							serviceTax = cN66DetailsVOforRpt.getServiceTax()+cN66DetailsVO.getServiceTax();

							netAmount = cN66DetailsVOforRpt.getNetAmount().getAmount()+cN66DetailsVO.getNetAmount().getAmount();
							cN66DetailsVOforRpt.setTotalWeight(totalWeight);
							cN66DetailsVOforRpt.setAmount(amount);
							cN66DetailsVOforRpt.setServiceTax(serviceTax);

							cN66DetailsVOforRpt.getNetAmount().setAmount(netAmount);
						}
					}
				}
				cN66DetailsVO.setOverrideRounding(overrideRounding);

			}
		}
		//Added by A-8149 for ICRD-257237 starts---

				if(cn66DetailsVos != null && !cn66DetailsVos.isEmpty() && overrideRounding!=null && overrideRounding.trim().length()>0
						&& CN51CN66FilterVO.FLAG_NO.equals(overrideRounding))
				{
					CurrencyValidationVO currencyValidationVO;
					String currency=null;

					for(CN66DetailsVO cN66DetailsVO : cn66DetailsVos){
						currency=cN66DetailsVO.getCurrencyCode();
						break;
					}
					try {
						String roundingUnit=null;
						currencyValidationVO = new SharedCurrencyProxy().validateCurrency(cn51CN66FilterVO.getCompanyCode(),currency);
						if(currencyValidationVO.getRoundingUnit() >= 1){
							int roundingUnitInt = (int) currencyValidationVO.getRoundingUnit();
							roundingUnit = String.valueOf(roundingUnitInt);
						}else{
							roundingUnit =String.valueOf(currencyValidationVO.getRoundingUnit());
						}
						int integerPlaces = roundingUnit.indexOf('.');
						if(integerPlaces != -1){
							int decimalPlaces = roundingUnit.length() - integerPlaces - 1;
							overrideRounding = String.valueOf(decimalPlaces);
						}else{
							overrideRounding = "0";
						}
						for(CN66DetailsVO cN66DetailsVO : cn66DetailsVos){
							cN66DetailsVO.setOverrideRounding(overrideRounding);
						}
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage());
					}

				}
				//Added by A-8149 as part of ICRD-257237 ends---
		if (cn66DetailsVos == null || cn66DetailsVos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO("mailtracking.mra.gpabilling.cn51cn66.Tabnoresultsfound");
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}


         if(cn66DetailsVos.size() >0 ){

        	for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {

		  	  if (cN66DetailsVO.getUnitcode() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cN66DetailsVO.getUnitcode()) ) {
						cN66DetailsVO.setUnitcode(oneTimeVO.getFieldDescription());
					}
				 }
			  }
		    }
         }

		log.log(Log.INFO, " <-- the cn66DetailsVos is --> \n\n ",
				cn66DetailsVos);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "airlineCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cn51CN66FilterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { MALCTGCOD, "DSN", "RSN", "CCAREFNUM", "ORGCOD", "DSTCOD",
				SECTOR, "FLTNUM", TOTWGT, APLRAT, "BLDAMT", "SRVTAX", NETAMT, "BLGCURCOD","BLDPRD",
				"TOTWGTCP","TOTWGTLC","TOTWGTSV","TOTWGTEMS","BLDAMT1","HNI","RI","FLTDAT","OVRRND",MALSUBCLS,"WEIGHT","UNTCOD","CNTIDR"
		});
		if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
		reportMetaData.setFieldNames(new String[] { MAILCATEGORYCODE, "dsn", "rsn", "ccaRefNo",
				"origin", "destination", SECTOR1,
				"flightNumber", TOTALWEIGHT, APPLICABLERATE, "actualAmount", "serviceTax", "netAmount", "currencyCode","billingPeriod",
				"weightCP","weightLC","weightSV","weightEMS","amount","hsn","regInd","flightDateInString","overrideRounding","mailSubclass","weight","unitcodeSV","containerNumber"
		});
		}else{
			reportMetaData.setFieldNames(new String[] { MAILCATEGORYCODE, "dsn", "rsn", "ccaRefNo",
					"origin", "destination", SECTOR1,
					"flightNumber", TOTALWEIGHT, APPLICABLERATE, "amount", "serviceTax", "scalarNetAmount", "currencyCode","billingPeriod",
					"weightCP","weightLC","weightSV","weightEMS","amount","hsn","regInd","flightDateInString","overrideRounding","mailSubclass","weight","unitcode","containerNumber"
			});
		}
		reportSpec.setReportMetaData(reportMetaData);

		reportSpec.setData(cn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return map
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN66Report6E(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
		.generateCN66Report(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		oneTimeActiveStatusList.add(BILLINGSTATUS_ONETIME);
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			billingStatus = oneTimeHashMap.get(BILLINGSTATUS_ONETIME);
			log.log(Log.INFO, "billingStatus1234++", billingStatus);
		}
		if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				if (cN66DetailsVO.getBillingStatus() != null) {
					for (OneTimeVO oneTimeVO : billingStatus) {
						if (cN66DetailsVO.getBillingStatus().equals(
								oneTimeVO.getFieldValue())) {
							cN66DetailsVO.setBillingStatus(oneTimeVO
									.getFieldDescription());
						}
					}
				}
				String frmStr = cN66DetailsVO.getBillingPeriod()
				.substring(0, 9);
				String toStr = cN66DetailsVO.getBillingPeriod().substring(14,
						23);
				log.log(Log.INFO, " <-- frmStr is --> \n\n ", frmStr);
				log.log(Log.INFO, " <-- toStr is --> \n\n ", toStr);
				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				fromDate.setDate(frmStr, "dd-MMM-yy");
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				toDate.setDate(toStr, "dd-MMM-yy");
				log.log(Log.INFO, " <-- fromDate is --> \n\n ", fromDate);
				log.log(Log.INFO, " <-- toDate is --> \n\n ", toDate);
				log.log(Log.INFO, " <-- ReceivedDate()--> \n\n ", cN66DetailsVO.getReceivedDate());
				if (cN66DetailsVO.getReceivedDate().isGreaterThan(fromDate)
						&& cN66DetailsVO.getReceivedDate().isLesserThan(toDate)) {
					cN66DetailsVO.setMonthFlag("A");
				} else {
					cN66DetailsVO.setMonthFlag("N");
				}
			}
		}
		if (cn66DetailsVos == null || cn66DetailsVos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		log.log(Log.INFO, " <--inside  generateCN66Report6E the cn66DetailsVos is --> \n\n ",
				cn66DetailsVos);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "airlineCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		// reportSpec.addParameter(cn66DetailsVos.iterator().next());
		reportSpec.addParameter(cn51CN66FilterVO);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { MALCTGCOD, "RCVDAT",
				"DSN", "ORGCOD", "DSTCOD", SECTOR, "FLTNUM", TOTWGT,
				"CCAREFNUM", "RMK", MALSUBCLS, "MONTHFLG" ,"FLTDAT","TOTPCS","BLDPRD"});
		reportMetaData.setFieldNames(new String[] { MAILCATEGORYCODE,
				"receivedDate", "dsn", "origin", "destination", SECTOR1,
				"flightNumber", TOTALWEIGHT, "ccaRefNo", "billingStatus",
				"mailSubclass", "monthFlag","flightDate","totalPieces","blgPrd" });
		
		
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(cn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);

	}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN51ReportFromCN51Cn66(ReportSpec reportSpec)throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		CN51Details cN51Details=new CN51Details();
		Collection<CN51DetailsVO> cn51DetailsVOs =cN51Details.findCN51Details(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		oneTimeActiveStatusList.add(BILLINGSTATUS_ONETIME);
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			billingStatus = oneTimeHashMap.get(BILLINGSTATUS_ONETIME);
			log.log(Log.INFO, "billingStatus1234++", billingStatus);
		}
		if (cn51DetailsVOs == null || cn51DetailsVOs.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		reportSpec.addParameter(cn51CN66FilterVO);
		reportSpec.setData(cn51DetailsVOs);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return Mao
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN51Report(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		CN51CN66VO cn51cn66VO = new CN51CN66VO();
		//Added by A-7929 as part of ICRD-257249 starts---
				String overrideRounding =null;
				Collection<String> systemParCodes = new ArrayList<String>();
				Map<String,String> systemParameterMap =null;
				systemParCodes.add(OVERRIDEROUNDING);
				systemParCodes.add(SHARED_AIRLINE_BASECURRENCY);

				try {
					systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
				} catch (ProxyException proxyException) {
					throw new SystemException(proxyException.getMessage());
				}
				if(systemParameterMap !=null && systemParameterMap.size()>0)
					{
					overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
					}

		//Added by A-7929 as part of ICRD-257249 ends---




				//added by A-7929 ends-----
		//commented by A-7540 as a part of ICRD-223463
		//LocalDate fromDate=null;
		//LocalDate toDate=null;

		/* COMMENTED BY INDU FOR BUG 36077 */
		/*
		 * cn51cn66VO=CN51Summary.findCN51CN66Details(cn51CN66FilterVO);
		 * log.log(Log.INFO,"CN51CN66VO"+cn51cn66VO); Collection<CN51DetailsVO>
		 * cn51DetailsVos = cn51cn66VO.getCn51DetailsVOs();
		 */

		/**/
		LocalDate currDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		Collection<CN66DetailsVO> currentCn66 = new ArrayList<CN66DetailsVO>();
		Collection<CN66DetailsVO> previousCn66 = new ArrayList<CN66DetailsVO>();
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
		.generateCN66Report(cn51CN66FilterVO);
		if (cn66DetailsVos == null || cn66DetailsVos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		log.log(Log.INFO, " <--inside generateCN51Report the cn66DetailsVos is --> \n\n ",
				cn66DetailsVos);
		for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
			String frmStr = cN66DetailsVO.getBillingPeriod().substring(0, 9);
			//String toStr = cN66DetailsVO.getBillingPeriod().substring(13, 22);
			String toStr = cN66DetailsVO.getBillingPeriod().substring(13, 22);
			log.log(Log.INFO, " <-- frmStr is --> \n\n ", frmStr);
			log.log(Log.INFO, " <-- toStr is --> \n\n ", toStr);

			java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yy");
            //Modified by A-7540 as a part of ICRD-223463
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			try {
				java.util.Date frmDate= formatter.parse(frmStr);

				fromDate.setTime(frmDate);
				java.util.Date toDte = formatter.parse(toStr);

				toDate.setTime(toDte);
				log.log(Log.INFO, " <-- fromDate is --> \n\n ", fromDate);
				log.log(Log.INFO, " <-- toDate is --> \n\n ", toDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.log(Log.INFO, " error msg \n\n ", e.getMessage());
			}

//			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
//					Location.NONE, false);
//			fromDate.setDate(frmStr, "dd-MMM-yy");
//			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
//					Location.NONE, false);
//
//			toDate.setDate(toStr, "dd-MMM-yy");


			log.log(Log.INFO, " <-- ReceivedDate()--> \n\n ", cN66DetailsVO.getReceivedDate());


		if (cN66DetailsVO.getReceivedDate().isGreaterThan(fromDate)
					&& cN66DetailsVO.getReceivedDate().isLesserThan(toDate)) {

				currentCn66.add(cN66DetailsVO);
		} else {
				previousCn66.add(cN66DetailsVO);
			}

		}
		log.log(Log.INFO, " <-- the currentCn66 is --> \n\n ", currentCn66);
		log.log(Log.INFO, " <-- the previousCn66 is --> \n\n ", previousCn66);
		Collection<CN51DetailsVO> cn51DetailsVosCur = performCN66ToCN51Grouping(currentCn66);
		Collection<CN51DetailsVO> cn51DetailsVosCurSum = cN66ToCN51GroupingForReport(currentCn66);
		Collection<CN51DetailsVO> cn51DetailsVosPre = performCN66ToCN51Grouping(previousCn66);
		Collection<CN51DetailsVO> cn51DetailsVosPreSum = cN66ToCN51GroupingForReport(previousCn66);
		log.log(Log.INFO, " <-- the cn51DetailsVosCur is --> \n\n ",
				cn51DetailsVosCur);
		log.log(Log.INFO, " <-- the cn51DetailsVosPre is --> \n\n ",
				cn51DetailsVosPre);
		log.log(Log.INFO, " <-- the cn51DetailsVosCurSum is --> \n\n ",
				cn51DetailsVosCurSum);
		log.log(Log.INFO, " <-- the cn51DetailsVosPreSum is --> \n\n ",
				cn51DetailsVosPreSum);

		//Added by A-7929 as part of ICRD-257249 starts---

		if(overrideRounding!=null && overrideRounding.trim().length()>0
				&& CN51CN66FilterVO.FLAG_NO.equals(overrideRounding))
		{
			CurrencyValidationVO currencyValidationVO;
			String currency=null;

			//Added by A-9855 as part of IASCB-135363 starts
			if(cn51DetailsVosCur != null && !cn51DetailsVosCur.isEmpty()){
				CN51DetailsVO cn51DetailsVO =  ((List<CN51DetailsVO>)cn51DetailsVosCur).get(0);
				currency = cn51DetailsVO.getBillingCurrencyCode();
			}
			if(cn51DetailsVosPre != null && !cn51DetailsVosPre.isEmpty()){
				CN51DetailsVO cn51DetailsVO = ((List<CN51DetailsVO>)cn51DetailsVosPre).get(0);
				currency = cn51DetailsVO.getBillingCurrencyCode();
				
			}
			//Added by A-9855 as part of IASCB-135363 ends
			try {
				//Added by A-7794 as part of ICRD-257249
				String roundingUnit;
				currencyValidationVO = new SharedCurrencyProxy().validateCurrency(cn51CN66FilterVO.getCompanyCode(),currency);
				if(currencyValidationVO.getRoundingUnit() >= 1){
					int roundingUnitInt = (int) currencyValidationVO.getRoundingUnit();
					roundingUnit = String.valueOf(roundingUnitInt);
				}else{
					roundingUnit =String.valueOf(currencyValidationVO.getRoundingUnit());
				}
				int integerPlaces = roundingUnit.indexOf('.');
				if(integerPlaces != -1){
					int decimalPlaces = roundingUnit.length() - integerPlaces - 1;
					overrideRounding = String.valueOf(decimalPlaces);
				}else{
					overrideRounding = "0";
				}
				//Added by A-7794 as part of ICRD-257249 ends
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			}

		}
		//Added by A-7929 as part of ICRD-257249 ends---


		if ((cn51DetailsVosCur == null || cn51DetailsVosCur.size() <= 0)
				&& (cn51DetailsVosPre == null || cn51DetailsVosPre.size() <= 0)) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND));
			throw mailTrackingMRABusinessException;
		}
		Collection<CN51DetailsVO> cn51DetailsVos = new ArrayList<CN51DetailsVO>();

		Collection<CN51DetailsVO> cn51DetailsVosSum = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVosCur != null || cn51DetailsVosCur.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVosCur) {
				cn51VO.setMonthFlag("C");
				cn51VO.setOverrideRounding(overrideRounding);////Added by A-7929 as part of ICRD-257249
				cn51DetailsVos.add(cn51VO);
			}
			for (CN51DetailsVO cn51VO : cn51DetailsVosCurSum) {
				cn51VO.setMonthFlag("C");
				cn51VO.setOverrideRounding(overrideRounding);//Added by A-7929 as part of ICRD-257249
				cn51DetailsVosSum.add(cn51VO);
			}
		}
		if (cn51DetailsVosPre != null || cn51DetailsVosPre.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVosPre) {
				cn51VO.setMonthFlag("P");
				cn51VO.setOverrideRounding(overrideRounding);//Added by A-7929 as part of ICRD-257249
				cn51DetailsVos.add(cn51VO);
			}
			for (CN51DetailsVO cn51VO : cn51DetailsVosPreSum) {
				cn51VO.setMonthFlag("P");
				cn51VO.setOverrideRounding(overrideRounding);//Added by A-7929 as part of ICRD-257249
				cn51DetailsVosSum.add(cn51VO);
			}
		}
		log.log(Log.INFO, " <-- the cn51DetailsVos is --> \n\n ",
				cn51DetailsVos);
		log.log(Log.INFO, " <-- the cn51DetailsVosSum is --> \n\n ",
				cn51DetailsVosSum);
		cn51cn66VO.setAirlineCode(cn51CN66FilterVO.getAirlineCode());// added by
		// indu
		cn51cn66VO.setOverrideRouding(overrideRounding);//added by a-7871 for ICRD-214766
		Page<CN51DetailsVO> cn51DetailsVoPage=new Page<CN51DetailsVO>((ArrayList<CN51DetailsVO>)cn51DetailsVos, 0, 0, 0, 0, 0, false);

		 		cn51cn66VO.setCn51DetailsVOs(cn51DetailsVoPage);// added by indu
		reportSpec.addExtraInfo(cn51cn66VO);

		ReportMetaData subReportMetaData = new ReportMetaData();
		reportSpec.addSubReportMetaData(subReportMetaData);
		ReportMetaData subReportMetaData2 = new ReportMetaData();
		reportSpec.addSubReportMetaData(subReportMetaData2);
		reportSpec.addSubReportData(cn51DetailsVosSum);

		return ReportAgent.generateReport(reportSpec);
	}
/**
 * @author A-10383
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 */
	
	public Map<String, Object> generateInvoiceReportSQ(ReportSpec reportSpec)throws SystemException
		{
				log.log(Log.INFO, " inside generateInvoiceReportSQ");
				LogonAttributes logon = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
				AirlineVO airlineVO = null;
				
				String overrideRounding =null;
				Collection<String> systemParCodes = new ArrayList();
				Map<String,String> systemParameterMap =null;
				systemParCodes.add(OVERRIDEROUNDING);
				systemParameterMap = findSystemParameterByCodes(systemParCodes);
				if(systemParameterMap !=null && systemParameterMap.size()>0)
				{
					overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
				}
		    	CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec.getFilterValues().iterator().next();	
		    	
				InvoiceDetailsReportVO invoiceDetailsReportVO = CN51Summary.generateInvoiceReportSQ(cn51CN66FilterVO);
				
				invoiceDetailsReportVO.setOverrideRounding(overrideRounding);
				Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<>();
				invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
				invoiceDetailsReportVO.setTotalAmtinBillingCurrString(setAmountAsString(invoiceDetailsReportVO.getTotalAmtinBillingCurr()));
				log.log(Log.INFO, "for vos returned ", invoiceDetailsReportVOs);
				log.log(Log.INFO, "for Airline Identifier--> ", logon.getOwnAirlineIdentifier());
				airlineVO = CN51Summary.findAirlineAddress(cn51CN66FilterVO.getCompanyCode(),logon.getOwnAirlineIdentifier());
				if (airlineVO != null) 
				{
					ReportMetaData parameterMetaDatas = new ReportMetaData();
					if(invoiceDetailsReportVO.getAirlineAddress()!= null)
					{
						airlineVO.setBillingAddress(invoiceDetailsReportVO.getAirlineAddress());
					}
					else if (airlineVO.getAirlineName() == null) 
					{
						airlineVO.setAirlineName("");
					}
					if (airlineVO.getBillingAddress() == null) 
					{
						airlineVO.setBillingAddress("");
					}

					if (airlineVO.getBillingPhone1() == null) 
					{
						airlineVO.setBillingPhone1("");
					}

					if (airlineVO.getBillingPhone2() == null) 
					{
						airlineVO.setBillingPhone2("");
					}

					if (airlineVO.getBillingFax() == null) 
					{
						airlineVO.setBillingFax("");
					}
					parameterMetaDatas.setFieldNames(new String[] { AIRLINENAME,BILLINGADDRESS, BILLINGPHONE1, BILLINGPHONE2,BILLINGFAX });
					reportSpec.addParameterMetaData(parameterMetaDatas);
					reportSpec.addParameter(airlineVO);
				}
	
		
				setReportMetaData(reportSpec,invoiceDetailsReportVO, invoiceDetailsReportVOs);
				return ReportAgent.generateReport(reportSpec);
	}
	private String setAmountAsString(Money input) {
		if (Objects.nonNull(input)) {
			return CurrencyHelper.getDisplayValue(input);
		}
		return StringUtils.EMPTY;
		}
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
private void setReportMetaData(ReportSpec reportSpec,
		InvoiceDetailsReportVO invoiceDetailsReportVO, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs) {
	ReportMetaData reportMetaData = new ReportMetaData();
	if(invoiceDetailsReportVO!=null)
	{

		reportMetaData.setColumnNames(new String[] { INVNUM, SECTOR,
		BLGPRDFRM,BLGPRDTOO, TOTAMTBLGCUR, BLGCURCOD,
		POANAM, POAADR, CITY, STATE, COUNTRY, PHONE1,
		PHONE2, FAX, VATNUM, BLDPRD, MALCTGCOD,OVRRND,CORADR,SGNONE,DSGONE,SGNTWO,DSGTWO,CURCOD,
			BNKNAM,BNKBRC,ACCNUM,CTYNAM,CNTNAM,SWTCOD,IBNNUM,INVDAT,RMK,GPA,PERIOD,"DATEPERIOD",VATNUM1});
		
			reportMetaData.setFieldNames(new String[] { INVNUMFIN, SECTOR1,
					FROMDATESTRING, TODATESTRING,
					TOTALAMTINBILLINGCURRSTRING, BILLINGCURRENCYCODE,
					PANAME, ADDRESS, CITY1, STATE1, COUNTRY1, PHONE11,
					PHONE22, FAX1, DUEDAYS, BILLEDDATESTRING,
					MAILCATEGORYCODE,OVERRIDEROUNDING1,CORRESPONDENCEADDRESS,SIGNATORONE,DESIGNATORONE,SIGNATORTWO,
						DESIGNATORTWO,CURRENCY,BANKNAME,BRANCH,"bnkdtlAccnum",BANKCITY,BANKCOUNTRY,SWIFTCODE,IBANNO,INVDATEMMM,FREETEXT,GPACODE,BILLEDDATESTRING,"dateperiod",VAT});
	}
	reportSpec.setReportMetaData(reportMetaData);
	reportSpec.setData(invoiceDetailsReportVOs);
}
/**
 * @author A-10383
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 */
private Map<String, String> findSystemParameterByCodes(Collection<String> systemParCodes) throws SystemException {
	Map<String, String> systemParameterMapSQ= null;
	try 
	{
		systemParameterMapSQ = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParCodes);
	} 
	catch (ProxyException proxyException) 
	{
		log.log(Log.INFO, proxyException);
		throw new SystemException(proxyException.getMessage());
	}
	return systemParameterMapSQ;
}

	
	

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceReport(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		log.log(Log.INFO, " inside generateInvoiceReport");
		LogonAttributes logon = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		AirlineVO airlineVO = null;
		//added by a-7871 for ICRD-214766 starts-----
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		Map<String,String> systemParameterMap =null;
		systemParCodes.add(OVERRIDEROUNDING);
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0)
			{
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
			}
		//added by a-7871 for ICRD-214766 ends-----
    	CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
    	//InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
		InvoiceDetailsReportVO invoiceDetailsReportVO1 = CN51Summary
		.generateInvoiceReport(cn51CN66FilterVO);
		//Added by A-8527 for ICRD-324399 Starts

		InvoiceDetailsReportVO	invoiceDetailsReportVO = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
		//Added by A-8527 for ICRD-324399 Ends
		if (invoiceDetailsReportVO1 == null) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		//Added by A-8527 for ICRD-324399 Starts
		if(invoiceDetailsReportVO!=null){
			invoiceDetailsReportVO1.setAddress(invoiceDetailsReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceDetailsReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setGpaCode(invoiceDetailsReportVO.getGpaCode());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceDetailsReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO1.setOverrideRounding(overrideRounding);
			invoiceDetailsReportVO1.setAccNo(invoiceDetailsReportVO.getAccountNumber());
		    invoiceDetailsReportVO1.setBankCity(invoiceDetailsReportVO.getCityOfBank());
			invoiceDetailsReportVO1.setBankCountry(invoiceDetailsReportVO.getCountryOfBank());
			invoiceDetailsReportVO1.setSwiftCode(invoiceDetailsReportVO.getSwiftCodeOfBank());
			invoiceDetailsReportVO1.setIbanNo(invoiceDetailsReportVO.getIbanNoOfBank());
			invoiceDetailsReportVO1.setBankName(invoiceDetailsReportVO.getNameOfBank());
			invoiceDetailsReportVO1.setBranch(invoiceDetailsReportVO.getBankBranch());
			invoiceDetailsReportVO1.setDesignatorOne(invoiceDetailsReportVO.getDesignatorOne());
			invoiceDetailsReportVO1.setDesignatorTwo(invoiceDetailsReportVO.getDesignatorTwo());
			invoiceDetailsReportVO1.setSignatorOne(invoiceDetailsReportVO.getSignatorOne());
			invoiceDetailsReportVO1.setSignatorTwo(invoiceDetailsReportVO.getSignatorTwo());
		    invoiceDetailsReportVO1.setToDateString(invoiceDetailsReportVO.getToDateString());
		    invoiceDetailsReportVO1.setCorrespondenceAddress(invoiceDetailsReportVO.getCorrespondenceAddress());
		    invoiceDetailsReportVO1.setFreeText(invoiceDetailsReportVO.getFreeText());
			}
		//Added by A-8527 for ICRD-324399 Ends
		invoiceDetailsReportVO1.setOverrideRounding(overrideRounding);//added by a-7871 for ICRD-214766
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
		//Added by A-7794 as part of ICRD-326702
		invoiceDetailsReportVO1.setTotalAmtinBillingCurrString(invoiceDetailsReportVO1.getTotalAmtinBillingCurr().toString());
		log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
		log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());
		airlineVO = CN51Summary.findAirlineAddress(
				cn51CN66FilterVO.getCompanyCode(),
				logon.getOwnAirlineIdentifier());
		if (airlineVO != null) {
			ReportMetaData parameterMetaDatas = new ReportMetaData();
			if(invoiceDetailsReportVO.getAirlineAddress()!= null){
				airlineVO.setBillingAddress(invoiceDetailsReportVO.getAirlineAddress());
			}else if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
			if (airlineVO.getBillingAddress() == null) {
				airlineVO.setBillingAddress("");
			}

			if (airlineVO.getBillingPhone1() == null) {
				airlineVO.setBillingPhone1("");
			}

			if (airlineVO.getBillingPhone2() == null) {
				airlineVO.setBillingPhone2("");
			}

			if (airlineVO.getBillingFax() == null) {
				airlineVO.setBillingFax("");
			}
			parameterMetaDatas.setFieldNames(new String[] { "airlineName",
					"billingAddress", "billingPhone1", "billingPhone2",
			"billingFax" });
			reportSpec.addParameterMetaData(parameterMetaDatas);
			reportSpec.addParameter(airlineVO);
		}

		/*
		 * PostalAdministrationVO adminDetailsVO = null;
		 * if(adminDetailsVO!=null){ ReportMetaData parameterMetaData = new
		 * ReportMetaData(); parameterMetaData.setFieldNames(new String[]
		 * {"paName", "address", "city", "state", "country","phone1"});
		 * reportSpec.addParameterMetaData(parameterMetaData);
		 * reportSpec.addParameter(adminDetailsVO); }
		 * airlineVO=CN51Summary.findAirlineAddress
		 * (cn51CN66FilterVO.getCompanyCode(),1086); /*if(airlineVO!=null){
		 * ReportMetaData parameterMetaDatas = new ReportMetaData();
		 * parameterMetaDatas.setFieldNames(new String[] {"airlineName",
		 * "billingAddress", "billingPhone1", "billingPhone2", "billingFax"});
		 * reportSpec.addParameterMetaData(parameterMetaDatas);
		 * reportSpec.addParameter(airlineVO); }
		 */

		// log.log(Log.INFO," invoiceDetailsReportVOs.iterator().next()"+invoiceDetailsReportVOs.iterator().next());

		// ReportMetaData reportMetaData = new ReportMetaData();
		// reportMetaData.setColumnNames(new String[]
		// {INVNUM,SECTOR});
		// reportMetaData.setFieldNames(new String[]
		// {"invoiceNumber",SECTOR1});
		// reportSpec.setReportMetaData(reportMetaData);
		// ReportMetaData parameterMetaData = new ReportMetaData();
		// parameterMetaData.setFieldNames(new String[] { SECTOR1,
		// "invoiceNumber", "billedDate", "billingCurrencyCode",
		// "totalAmountinBillingCurrency"});
		// reportSpec.addParameterMetaData(parameterMetaData);
		// reportSpec.addParameter(invoiceDetailsReportVOs.iterator().next());
		// reportSpec.addExtraInfo(invoiceDetailsReportVOs);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { INVNUM, SECTOR,
				BLGPRDFRM,BLGPRDTOO, TOTAMTBLGCUR, BLGCURCOD,
				POANAM, POAADR, CITY, STATE, COUNTRY, PHONE1,
				PHONE2, FAX, VATNUM, BLDPRD, MALCTGCOD,OVRRND,CORADR,SGNONE,DSGONE,SGNTWO,DSGTWO,CURCOD,
				BNKNAM,BNKBRC,ACCNUM,CTYNAM,CNTNAM,SWTCOD,IBNNUM,INVDAT,RMK,GPA});//modified by a-7871 for ICRD-214766
		//Modified by A-8527 for ICRD-324399
		//if condition added by a-7871 for ICRD-214766
		if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
			reportMetaData.setFieldNames(new String[] { INVOICENUMBER, SECTOR1,
					FROMDATESTRING, TODATESTRING,
					TOTALAMTINBILLINGCURRSTRING, BILLINGCURRENCYCODE,
					PANAME, ADDRESS, CITY1, STATE1, COUNTRY1, PHONE11,
					PHONE22, FAX1, DUEDAYS, BILLEDDATESTRING,
					MAILCATEGORYCODE,OVERRIDEROUNDING1,CORRESPONDENCEADDRESS,SIGNATORONE,DESIGNATORONE,SIGNATORTWO,
					DESIGNATORTWO,CURRENCY,BANKNAME,BRANCH,ACCNO,BANKCITY,BANKCOUNTRY,SWIFTCODE,IBANNO,BILLEDDATE,FREETEXT,GPACODE});//Modified by A-8527 for ICRD-324399
		}else{
		reportMetaData.setFieldNames(new String[] {INVOICENUMBER, SECTOR1,
				FROMDATESTRING, TODATESTRING,
				TOTALAMTINBILLINGCURRSTRING, BILLINGCURRENCYCODE,
				PANAME, ADDRESS, CITY1, STATE1, COUNTRY1, PHONE11,
				PHONE22, FAX1, DUEDAYS, BILLEDDATESTRING,
				MAILCATEGORYCODE,OVERRIDEROUNDING1,CORRESPONDENCEADDRESS,SIGNATORONE,DESIGNATORONE,SIGNATORTWO,
				DESIGNATORTWO,CURRENCY,BANKNAME,BRANCH,ACCNO,BANKCITY,BANKCOUNTRY,SWIFTCODE,IBANNO,BILLEDDATE,FREETEXT,GPACODE});//Modified by A-8527 for ICRD-324399
		}
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(invoiceDetailsReportVOs);
		return ReportAgent.generateReport(reportSpec);
		/**
		 * @author a-3447 for AirNZ Enhancemet:27970 ends
		 */
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<BillingSummaryDetailsVO> findBillingSummaryDetailsForPrint(
			BillingSummaryDetailsFilterVO filterVO) throws SystemException {

		return CN51Summary.findBillingSummaryDetailsForPrint(filterVO);

	}

	/**
	 * @author A-2408
	 * @param reportSpec
	 * @return Map
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findBlgSmyPeriodWiseDetailsForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		BillingSummaryDetailsFilterVO filterVO = (BillingSummaryDetailsFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<BillingSummaryDetailsVO> summaryVOs = findBillingSummaryDetailsForPrint(filterVO);
		if (summaryVOs == null || summaryVOs.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}

		//Added by A-7929 as part of ICRD-257574 starts...


	      SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
	        Collection<String> systemParameterCodes = new ArrayList<String>();
	        systemParameterCodes.add(SHARED_AIRLINE_BASECURRENCY);
	        systemParameterCodes.add("mailtracking.mra.overrideroundingvalue");
	        Map<String, String> systemParameters = null;

	        try {
	              systemParameters = sharedDefaultsProxy
	              .findSystemParameterByCodes(systemParameterCodes);
	        } catch (ProxyException proxyException) {
	              throw new SystemException(proxyException.getMessage(),
	                            proxyException);
	        }

	        Double sumGrandTotal = 0.00;
		    Money grandTotalMoney = null;
		    Money billedAmtMoney = null;//Added by A-7929 for ICRD-259231
			String roundedGrandTotal = null;
			String currencyCode = systemParameters.get(SHARED_AIRLINE_BASECURRENCY);
			try {
				grandTotalMoney = CurrencyHelper.getMoney(currencyCode);
			} catch (CurrencyException e) {
				e.getErrorCode();
			}

			if(summaryVOs!=null && summaryVOs.size()>=0)
			{
				for(BillingSummaryDetailsVO billingSummaryDetailsVO:summaryVOs)
				{
					 grandTotalMoney.setAmount(billingSummaryDetailsVO.getGrandTotal());
		             roundedGrandTotal = CurrencyHelper.getDisplayValueForReports(grandTotalMoney);
		             Double grandtotal=Double.parseDouble(roundedGrandTotal.replaceAll(",",""));
					 sumGrandTotal += grandtotal;
					billingSummaryDetailsVO.setOverrideRounding(systemParameters.get("mailtracking.mra.overrideroundingvalue"));

				}
				for(BillingSummaryDetailsVO billingSummaryDetailsVO:summaryVOs)
				{
					 billingSummaryDetailsVO.setSumGrandTotal(sumGrandTotal);
					//Added by A-7929 for ICRD-259231 starts---
						try {
							billedAmtMoney = CurrencyHelper.getMoney(billingSummaryDetailsVO.getCurrency());
						} catch (CurrencyException e) {
							e.getErrorCode();
						}
					 billedAmtMoney.setAmount(billingSummaryDetailsVO.getBilledValue());
					 billingSummaryDetailsVO.setBilledAmtForReport(billingSummaryDetailsVO.getBilledValue().toString());
					//Added by A-7929 for ICRD-259231 ends--
				}

			}

	        reportSpec.addExtraInfo(systemParameters);

	      //Added by A-7929 as part of ICRD-257574 ends...

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "fromDate", "toDate",
				COUNTRY1, GPACODE, "gpaName" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "BLDPRD", "GPACOD",
				POANAM, INVNUM, "BLDDAT", "CRTCURCOD", "TOTAMTCRTCUR","TOTAMTBASCUR","SUMTOTAMTBASCUR","OVRRND" });
		reportMetaData.setFieldNames(new String[] { "billingPeriod",  GPACODE,
				"gpaName", INVOICENUMBER, "invoiceDate", CURRENCY,
		"billedAmtForReport","grandTotal","sumGrandTotal",OVERRIDEROUNDING1 });//Modified for ICRD-105572
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(summaryVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findBlgSmyGpaWiseDetailsForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		BillingSummaryDetailsFilterVO filterVO = (BillingSummaryDetailsFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<BillingSummaryDetailsVO> summaryVOs = findBillingSummaryDetailsForPrint(filterVO);
		if (summaryVOs == null || summaryVOs.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		Map<String,String> systemParameterMap =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		for(BillingSummaryDetailsVO billingSummaryDetailsVO:summaryVOs){
			billingSummaryDetailsVO.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));
		}
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"fromDate", "toDate" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "BLDPRD", "CRTCURCOD",
		"TOTAMTCRTCUR","OVRRND" });
		reportMetaData.setFieldNames(new String[] { "billingPeriod",
				"currency", "billedValue" ,"overrideRounding"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(summaryVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<CN51DetailsVO> findCN51DetailsForPrint(
			CN51DetailsPrintFilterVO filterVO) throws SystemException {
		return CN51Summary.findCN51DetailsForPrint(filterVO);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN51DetailsPeriodWiseForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		CN51DetailsPrintFilterVO filterVO = (CN51DetailsPrintFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<CN51DetailsVO> detailvos = findCN51DetailsForPrint(filterVO);
		if (detailvos == null || detailvos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND));
			throw mailTrackingMRABusinessException;
		}
		Map<String,String> systemParameterMap =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		for(CN51DetailsVO cN51DetailsVO:detailvos){
			cN51DetailsVO.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));
		}
		//Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		//Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		//oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		//try {
		//	oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					//filterVO.getCompanyCode(), oneTimeActiveStatusList);
		//} catch (ProxyException proxyException) {
			//throw new SystemException(proxyException.getMessage());
		//}
		/*
		 * if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){
		 * categorycodes=oneTimeHashMap.get(KEY_CATEGORY_ONETIME); }
		 * log.log(log.INFO,"onetime categorycodes"+categorycodes);
		 */
		//reportSpec.addExtraInfo(oneTimeHashMap); Commented by A-7929 for ICRD-260958

		String companyCode = filterVO.getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		reportSpec.addExtraInfo(oneTimeHashMap);

        if(detailvos.size() >0 ){

        	for (CN51DetailsVO cn51DetailsVO : detailvos) {

		  	  if (cn51DetailsVO.getUnitCode() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn51DetailsVO.getUnitCode()) ) {
						cn51DetailsVO.setUnitCode(oneTimeVO.getFieldDescription());
					}
				 }
			  }
        	}
		}


		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "fromDate", "toDate",
				"gpaCode", "gpaName" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "GPACOD", "POANAM",
				"BLDPRD", "CRTCURCOD", "ORGCOD", "DSTCOD", MALCTGCOD,
				"LCTOTWGT","LCUNTCOD", "LCRPLRAT", "LCTOTAMT", "CPTOTWGT","CPUNTCOD", "CPAPLRAT",
				"CPTOTAMT","EMSTOTWGT","EMSUNTCOD","EMSRPLRAT","EMSTOTAMT","SVTOTWGT","SVUNTCOD","SVAPLRAT",
				"SVTOTAMT" ,"LCMAILCHG","LCSURCHG","CPMAILCHG","CPSURCHG" ,
				"SVMAILCHG","SVSURCHG","EMSMAILCHG","EMSSURCHG","OVRRND",MALSUBCLS,"WEIGHT","UNTCOD","RATE","AMOUNT","MAILCHARGE","SURCHARGE"});//Modified by A-7929 for ICRD-260958
		reportMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"billingPeriod", "billingCurrencyCode", "origin","destination", MAILCATEGORYCODE,
				"totalWeightLC","displayWgtUnitLC","applicableRateLC", "totalAmountLC", "totalWeightCP","displayWgtUnitCP","applicableRateCP",
				"totalAmountCP","totalWeightEMS","displayWgtUnitEMS","applicableRateEMS","totalAmountEMS","totalWeightSV","displayWgtUnitSV","applicableRateSV",
				"totalAmountSV","mailChgLC","surChgLC","mailChgCP","surChgCP",
				"mailChgSV","surChgSV","mailChgEMS","surChgEMS","overrideRounding","mailSubclass","weight","unitCode","rate","amount","mailChrg","surChrg"});

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(detailvos);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN51DetailsGPAWiseForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		CN51DetailsPrintFilterVO filterVO = (CN51DetailsPrintFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<CN51DetailsVO> detailvos = findCN51DetailsForPrint(filterVO);

		if (detailvos == null || detailvos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND));
			throw mailTrackingMRABusinessException;

		}
		Map<String,String> systemParameterMap =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		for(CN51DetailsVO cN51DetailsVO:detailvos){
			cN51DetailsVO.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));
		}
		//Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		//Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		//oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		//try {
			//oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
			//		filterVO.getCompanyCode(), oneTimeActiveStatusList);
		//} catch (ProxyException proxyException) {
			//throw new SystemException(proxyException.getMessage());
	//	}

		/*
		 * if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){
		 * categorycodes=oneTimeHashMap.get(KEY_CATEGORY_ONETIME); }
		 * log.log(log.INFO,"onetime categorycodes"+categorycodes);
		 */
		//reportSpec.addExtraInfo(oneTimeHashMap);// Commented by A-7929 for ICRD-260958

		String companyCode = filterVO.getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		reportSpec.addExtraInfo(oneTimeHashMap);

        if(detailvos.size() >0 ){

        	for (CN51DetailsVO cn51DetailsVO : detailvos) {

		  	  if (cn51DetailsVO.getUnitCode() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn51DetailsVO.getUnitCode()) ) {
						cn51DetailsVO.setUnitCode(oneTimeVO.getFieldDescription());
					}
				 }
			  }

        	}
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"fromDate", "toDate", });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();

		reportMetaData.setColumnNames(new String[] { "GPACOD", "POANAM",
				"BLDPRD", "CRTCURCOD", "ORGCOD", "DSTCOD", MALCTGCOD,
				"LCTOTWGT","LCUNTCOD", "LCRPLRAT", "LCTOTAMT", "CPTOTWGT","CPUNTCOD", "CPAPLRAT",
				"CPTOTAMT","EMSTOTWGT","EMSUNTCOD","EMSRPLRAT","EMSTOTAMT","SVTOTWGT","SVUNTCOD","SVAPLRAT",
				"SVTOTAMT" ,"LCMAILCHG","LCSURCHG","CPMAILCHG","CPSURCHG" ,
				"SVMAILCHG","SVSURCHG","EMSMAILCHG","EMSSURCHG","OVRRND",MALSUBCLS,"WEIGHT","UNTCOD","RATE","AMOUNT","MAILCHARGE","SURCHARGE"});//Modified by A-7929 for ICRD-260958
		reportMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"billingPeriod", "billingCurrencyCode", "origin","destination", MAILCATEGORYCODE,
				 "totalWeightLC","displayWgtUnitLC","applicableRateLC", "totalAmountLC", "totalWeightCP","displayWgtUnitCP","applicableRateCP",
				 "totalAmountCP","totalWeightEMS","displayWgtUnitEMS","applicableRateEMS","totalAmountEMS","totalWeightSV","displayWgtUnitSV","applicableRateSV",
				 "totalAmountSV","mailChgLC","surChgLC","mailChgCP","surChgCP",
				 "mailChgSV","surChgSV","mailChgEMS","surChgEMS","overrideRounding","mailSubclass","weight","unitCode","rate","amount","mailChrg","surChrg"});//Modified for ICRD-105572
		        //Modified by A-7929 for ICRD-260958
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(detailvos);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<CN66DetailsPrintVO> findCN66DetailsForPrint(
			CN66DetailsPrintFilterVO filterVO) throws SystemException {
		return CN51Summary.findCN66DetailsForPrint(filterVO);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN66DetailsPeriodWiseForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		CN66DetailsPrintFilterVO filterVO = (CN66DetailsPrintFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<CN66DetailsPrintVO> detailvos = findCN66DetailsForPrint(filterVO);
		if (detailvos == null || detailvos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND));
			throw mailTrackingMRABusinessException;
		}
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					filterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		/*
		 * if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){
		 * categorycodes=oneTimeHashMap.get(KEY_CATEGORY_ONETIME); }
		 * log.log(log.INFO,"onetime categorycodes"+categorycodes);
		 */
		reportSpec.addExtraInfo(oneTimeHashMap);


        if(detailvos.size() >0 ){

        	for (CN66DetailsPrintVO cn66DetailsPrintVO : detailvos) {

		  	  if (cn66DetailsPrintVO.getDisplayWgtUnitCP() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitCP()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitCP(oneTimeVO.getFieldDescription());
					}
				 }
			  }

		  	 if (cn66DetailsPrintVO.getDisplayWgtUnitEMS() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitEMS()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitEMS(oneTimeVO.getFieldDescription());
					}
				 }
			 }

		  	if (cn66DetailsPrintVO.getDisplayWgtUnitLC() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitLC()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitLC(oneTimeVO.getFieldDescription());
					}
				 }
			 }

		  	if (cn66DetailsPrintVO.getDisplayWgtUnitSV() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitSV()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitSV(oneTimeVO.getFieldDescription());
					}
				 }
			 }

          }
        }


		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "fromDate", "toDate",
				"gpaCode", "gpaName" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData
		.setColumnNames(new String[] { "BLDPRD", "GPACOD", "POANAM",
				MALCTGCOD, "RCVDAT", "DSN", "BILFRM", "BILTOO",
				"ORGCOD", "DSTCOD", "FLTCARCOD", "FLTNUM", "LCTOTWT","LCUNTCOD",
		"CPTOTWT","CPUNTCOD","EMSTOTWT","EMSUNTCOD","SVTOTWT","SVUNTCOD","UNTCOD" });
		reportMetaData.setFieldNames(new String[] { "billingPeriod", "gpaCode",
				"gpaName", MAILCATEGORYCODE, "receivedDate", "dsn",
				"billedFrom", "billingTo", "origin", "destination",
				"flightCarrierCode", "flightNumber", "totalLcWeight","displayWgtUnitLC",
		"totalCpWeight","displayWgtUnitCP","totalEmsWeight","displayWgtUnitEMS","totalSvWeight","displayWgtUnitSV"});//Modified for ICRD-105572
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(detailvos);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN66DetailsGPAWiseForPrint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		CN66DetailsPrintFilterVO filterVO = (CN66DetailsPrintFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<CN66DetailsPrintVO> detailvos = findCN66DetailsForPrint(filterVO);
		if (detailvos == null || detailvos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND));
			throw mailTrackingMRABusinessException;
		}
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					filterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		/*
		 * if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){
		 * categorycodes=oneTimeHashMap.get(KEY_CATEGORY_ONETIME); }
		 * log.log(log.INFO,"onetime categorycodes"+categorycodes);
		 */
		reportSpec.addExtraInfo(oneTimeHashMap);

        if(detailvos.size() >0 ){

        	for (CN66DetailsPrintVO cn66DetailsPrintVO : detailvos) {

		  	  if (cn66DetailsPrintVO.getDisplayWgtUnitCP() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitCP()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitCP(oneTimeVO.getFieldDescription());
					}
				 }
			  }

		  	 if (cn66DetailsPrintVO.getDisplayWgtUnitEMS() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitEMS()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitEMS(oneTimeVO.getFieldDescription());
					}
				 }
			 }

		  	if (cn66DetailsPrintVO.getDisplayWgtUnitLC() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitLC()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitLC(oneTimeVO.getFieldDescription());
					}
				 }
			 }

		  	if (cn66DetailsPrintVO.getDisplayWgtUnitSV() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(cn66DetailsPrintVO.getDisplayWgtUnitSV()) ) {
						cn66DetailsPrintVO.setDisplayWgtUnitSV(oneTimeVO.getFieldDescription());
					}
				 }
			 }

          }
        }

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"fromDate", "toDate" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData
		.setColumnNames(new String[] { "GPACOD", "POANAM", "BLDPRD",
				MALCTGCOD, "RCVDAT", "DSN", "BILFRM", "BILTOO",
				"ORGCOD", "DSTCOD", "FLTCARCOD", "FLTNUM", "LCTOTWT","LCUNTCOD",
		"CPTOTWT","CPUNTCOD","EMSTOTWT","EMSUNTCOD","SVTOTWT","SVUNTCOD"});
		reportMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
				"billingPeriod", MAILCATEGORYCODE, "receivedDate", "dsn",
				"billedFrom", "billingTo", "origin", "destination",
				"flightCarrierCode", "flightNumber", "totalLcWeight","displayWgtUnitLC",
		"totalCpWeight","displayWgtUnitCP","totalEmsWeight","displayWgtUnitEMS","totalSvWeight","displayWgtUnitSV"});//Modified for ICRD-105572
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(detailvos);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillableReport(ReportSpec reportSpec)
	throws SystemException, MailTrackingMRABusinessException {
		GPABillingEntriesFilterVO filterVO = (GPABillingEntriesFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<GPABillingDetailsVO> vos = findGPABillingEntries(filterVO);
		if (vos == null || vos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		String companyCode = filterVO.getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);

		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		reportSpec.addExtraInfo(oneTimeHashMap);

		if(vos.size() >0 ){

        	for (GPABillingDetailsVO gpaBillingdetailsVo : vos) {

		  	  if (gpaBillingdetailsVo.getDisplayWgtUnit() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
				 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
					if (oneTimeVO.getFieldValue().equals(gpaBillingdetailsVo.getDisplayWgtUnit()) ) {
						gpaBillingdetailsVo.setDisplayWgtUnit(oneTimeVO.getFieldDescription());
					}
				 }
			  }
        	}
		}


		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
		"billingStatus" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);
		ReportMetaData reportMetaData = new ReportMetaData();

		reportMetaData.setColumnNames(new String[] { INVNUM, "CSGDOCNUM",
				"ORGEXGOFC", "DSTEXGOFC", MALCTGCOD, MALSUBCLS, "YER",
				"DSN", "FLTNUM", "RCVDAT", "RCVPCS", "RCVWGT", "BLGSTA","CMPCOD","DSPWGTUNT"});
		reportMetaData.setFieldNames(new String[] { "invoiceNumber",
				"consignmentNumber", "originOfficeOfExchange",
				"destinationOfficeOfExchange", MAILCATEGORYCODE,
				"mailSubclass", "year", "dsn", "flightNumber", "receivedDate",
				"piecesReceived", "weightReceived", "billingStatus","companyCode","displayWgtUnit" });
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(vos);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * Added for ICRD-111958
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillableReportTK(ReportSpec reportSpec)
	throws SystemException, MailTrackingMRABusinessException {
		GPABillingEntriesFilterVO filterVO = (GPABillingEntriesFilterVO) reportSpec
		.getFilterValues().iterator().next();
		Collection<GPABillingDetailsVO> vos = null;
		try{
			vos = findGPABillingEntries(filterVO);
		}catch(SystemException e){
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENCSVRPT);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		if (vos == null || vos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		String key="";
		double grsAmt=0.0;
		double netAmt=0.0;
		double vatAmt=0.0;
		double valChg=0.0;
		double wgt = 0.0;
		for(GPABillingDetailsVO gPABillingDetailsVO : vos){
			if(key.equals(gPABillingDetailsVO.getDestinationOfficeOfExchange().substring(2, 5))){
				grsAmt = grsAmt+gPABillingDetailsVO.getTotalGrsAmount();
				netAmt = netAmt+gPABillingDetailsVO.getNetAmtBillingCurrency();
				vatAmt = vatAmt+gPABillingDetailsVO.getTotalVatAmount();
				valChg = valChg+gPABillingDetailsVO.getTotalValCharges();
				wgt = wgt+gPABillingDetailsVO.getWeightReceived();
			}else{
				grsAmt=0.0;
				netAmt=0.0;
				vatAmt=0.0;
				valChg=0.0;
				key = gPABillingDetailsVO.getDestinationOfficeOfExchange().substring(2, 5);
				grsAmt = grsAmt+gPABillingDetailsVO.getTotalGrsAmount();
				netAmt = netAmt+gPABillingDetailsVO.getNetAmtBillingCurrency();
				vatAmt = vatAmt+gPABillingDetailsVO.getTotalVatAmount();
				valChg = valChg+gPABillingDetailsVO.getTotalValCharges();
				wgt = wgt+gPABillingDetailsVO.getWeightReceived();
			}
			gPABillingDetailsVO.setOdnetAmtBillingCurrency(netAmt);
			gPABillingDetailsVO.setOdtotalGrsAmount(grsAmt);
			gPABillingDetailsVO.setOdtotalVatAmount(vatAmt);
			gPABillingDetailsVO.setOdtotalValCharges(valChg);
			gPABillingDetailsVO.setOdWeight(wgt);
			gPABillingDetailsVO.setOdString(key);
		}
		String companyCode = filterVO.getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		reportSpec.addExtraInfo(oneTimeHashMap);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "gpaCode", "gpaName",
		"billingStatus" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVO);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "ORGEXGOFC", "DSTEXGOFC", MALCTGCOD, MALSUBCLS, "YER",
				"DSN", "FLTNUM", "RCVDAT", "RCVWGT",APLRAT,"GRSAMT",NETAMT,VATAMT,"VALCHGS","DCLVAL","RSN","CTRCURCOD","NETAMTBLGCUR","BLGCURCOD","EXTWGT","TOTVAL","TOTVAT","TOTGRSAMT","ODKEY",
				"ODNETAMT","ODGRSAMT","ODVATAMT","ODVALAMT","ODWGT"});
		reportMetaData.setFieldNames(new String[] {"originOfficeOfExchange",
				"destinationOfficeOfExchange", MAILCATEGORYCODE,
				"mailSubclass", "year", "dsn", "flightNumber", "receivedDate", "weightReceived",APPLICABLERATE,"grossAmount","netAmount","vatAmount","valCharges","declaredValue","receptacleSerialNumber","currencyCode","netAmtBillingCurrency","billingCurrency","extraWeight",
				"totalValCharges","totalVatAmount","totalGrsAmount","odString","odnetAmtBillingCurrency",
				"odtotalGrsAmount","odtotalVatAmount","odtotalValCharges","odWeight"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(vos);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<GPASettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO, int displayPage)
			throws SystemException {
		log.entering(CLASS_NAME, "findSettlementDetails");
		return GPABillingSettlement
		.findSettlementDetails(invoiceSettlementFilterVO,displayPage);
	}

	/**
	 * @author A-2280
	 * modified by a-4823 for CR ICRD 7316
	 * @param invoiceSettlementVOs
	 * @throws SystemException
	 */
	public void saveSettlementDetails(
			Collection<GPASettlementVO> gpaSettlementVOs)
	throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveSettlementDetails");
		Collection<GPASettlementVO> unsettledGPAVOs = null;
		String prevSettKey = null;
		String currSetKey = null;
		Map<String, Double> isConvNeedMap=new HashMap<String,Double>();
		exchangeRateMap = new HashMap<String,Double>();
		if (gpaSettlementVOs != null && gpaSettlementVOs.size() > 0) {
			for (GPASettlementVO gpaSettlementVO : gpaSettlementVOs) {

				log.log(Log.INFO, gpaSettlementVO.getOperationFlag());
				Collection<SettlementDetailsVO> outStandingChequeDetails = null;
				Collection<SettlementDetailsVO> newSettlementCreated=new ArrayList<SettlementDetailsVO>();
				// generating settlement ref no:
				if ("".equals(gpaSettlementVO.getSettlementId()) || gpaSettlementVO.getSettlementId()==null) {
					gpaSettlementVO.setSettlementId(generateSettlementReferenceNumber(gpaSettlementVO));
				}



				/*
				 * Select the invoices to be settled. If there are any invoices selected from Screen
				 * Use the selected settlements otherwise find the unsettled invoices for the GPA from db.
				 */

				if (gpaSettlementVO.getInvoiceSettlementVOs() == null || gpaSettlementVO.getInvoiceSettlementVOs().size()==0 ) {
					/*
					 * If there is no selected invoices from screen.
					 */
					unsettledGPAVOs = GPABillingSettlement
					.findUnSettledInvoicesForGPA(gpaSettlementVO);
				} else {
					/*
					 * If there are selected invoices from screen.
					 */
					unsettledGPAVOs = gpaSettlementVOs;
				}
				/*
				 * Iterating settlement details for the settlement from Screen
				 */
				/*
				 * Populate GPABillingSettlement and  GPABillingSettlementDetails
				 * or update GPABillingSettlementDetails
				 */
				if(gpaSettlementVO.getSettlementDetailsVOs()!=null){
					for(SettlementDetailsVO settlementDetails : gpaSettlementVO
							.getSettlementDetailsVOs()){
						updateSettlement(gpaSettlementVO,settlementDetails);
					}
				}
				/*finding outstanding cheque details
				Iterate settlementdetails
				Outstandng cheque details should be first in the collection
				If any cheque is dishonoured then it should be first in the collection*/
				//Added if condition by A-7794 as part of ICRD-194277
				if(!INVOICE_SETTL.equals(gpaSettlementVO.getFrmScreen())){
				outStandingChequeDetails = findOutStandingChequesForGPA(gpaSettlementVO);
				if(outStandingChequeDetails!=null && outStandingChequeDetails.size()>0){
					for(SettlementDetailsVO deletedSettlmentVo:gpaSettlementVO.getSettlementDetailsVOs()){
						if("Y".equals(deletedSettlmentVo.getIsDeleted())){
							newSettlementCreated.add(deletedSettlmentVo);
						}
					}
					newSettlementCreated.addAll(outStandingChequeDetails);

					if(newSettlementCreated!=null && newSettlementCreated.size()>0){
						Collection<SettlementDetailsVO> settlementDetailsVOs=gpaSettlementVO.getSettlementDetailsVOs();
						for(SettlementDetailsVO newSettlementVO: gpaSettlementVO.getSettlementDetailsVOs()){
							if(!("Y".equals(newSettlementVO.getIsDeleted()))){
								newSettlementCreated.addAll(settlementDetailsVOs);
							}

						}

						gpaSettlementVO.setSettlementDetailsVOs(newSettlementCreated);


					}
				}
				}
				if(gpaSettlementVO.getSettlementDetailsVOs()!=null){
					for (SettlementDetailsVO settlementDetailsVO : gpaSettlementVO
							.getSettlementDetailsVOs()) {

						currSetKey=settlementDetailsVO.getSettlementId().concat(String.valueOf(settlementDetailsVO.getSettlementSequenceNumber())
								.concat(String.valueOf(settlementDetailsVO.getSerialNumber())));

						//added for handling exchane rate date basis
						settlementDetailsVO.setChequeDate(gpaSettlementVO.getSettlementDate());
						//Added or condition for handling the case where there are no unsettled invoices and cheque is dishonoured
						if ((unsettledGPAVOs != null && unsettledGPAVOs.size() > 0) || "Y".equals(settlementDetailsVO.getIsDeleted())) {


							/*if(gpaSettlementVO.getSettlementId()!=null && gpaSettlementVO.getSettlementId().trim().length()>0){
								settlementDetailsVO.setSettlementId(gpaSettlementVO.getSettlementId());
							}*/


							//cheque dishonour case as a seperate flow
							//find associated invoices and reverse the amount in MTKGPAC51SMY
							if("Y".equals(settlementDetailsVO.getIsDeleted())){
								updateChequeDishonour(settlementDetailsVO);
							}
							else  if(!("Y".equals(settlementDetailsVO.getIsDeleted()))) {
								/*
								 * Iterating unsettled invoices
								 */
								for (GPASettlementVO gpaVOforUnsettledInvoice : unsettledGPAVOs) {
									for (InvoiceSettlementVO unSettledInvoiceVO : gpaVOforUnsettledInvoice
											.getInvoiceSettlementVOs()) {


										if(unSettledInvoiceVO.getDueAmount()!=null && unSettledInvoiceVO.getDueAmount().getRoundedAmount() > 0 &&
												settlementDetailsVO.getChequeAmount().getRoundedAmount() >0){
											updateInvoiceDetails(settlementDetailsVO, unSettledInvoiceVO,isConvNeedMap);
										}
										//if cheque amount is fully consumed iterate next cheque details
										if(settlementDetailsVO.getChequeAmount().getRoundedAmount()==0){
											break;
										}
									}
									if(settlementDetailsVO.getChequeAmount().getRoundedAmount()==0){
										break;
									}

								}
							}
						}

						else {
							log.log(Log.INFO,
							"!!!!   ALL INVOICES SETTLED   !!!!!!!!!!!!!!!!!!!");
							if("Y".equals(settlementDetailsVO.getIsDeleted())){
								updateChequeDishonour(settlementDetailsVO);
							}


						}


					}

				}

				//If outstanding cheque amount is present then update the due amt of latest settled invoice as this extra amount
				//update GpaBIllingSettlmentdetails entity

				Money outStgAmtInSettCurr=null;
				Money outStgAmtInBillCurr=null;

				if(gpaSettlementVO.getSettlementDetailsVOs()!=null){
					//int size=gpaSettlementVO.getSettlementDetailsVOs().size();
					for(SettlementDetailsVO settlementDetailsVO : gpaSettlementVO.getSettlementDetailsVOs()){


						//settlementDetailsVO = detailsVoForOutStanding.get(size-1);
						if(settlementDetailsVO!=null){
							if(settlementDetailsVO.getChequeAmount()!=null && !("Y".equals(settlementDetailsVO.getIsDeleted()))){
								CN51Summary cn51SummaryEntity=null;
								if(settlementDetailsVO.getChequeAmount().getRoundedAmount()>0){

									if(unsettledGPAVOs!=null && unsettledGPAVOs.size()>0){
										Collection<InvoiceSettlementVO > invoicesTobeUpdated=unsettledGPAVOs.iterator().next().getInvoiceSettlementVOs();
										if(invoicesTobeUpdated!=null && invoicesTobeUpdated.size()>0){
											ArrayList<InvoiceSettlementVO> lastModifiedInvoice = new ArrayList<InvoiceSettlementVO>(invoicesTobeUpdated);
											double exchangeRate = 1;
											double outStgAmtInBillingCurr =settlementDetailsVO
											.getChequeAmount().getRoundedAmount();

											double outStandingAmtInSettCurr =settlementDetailsVO
											.getChequeAmount().getRoundedAmount();
											InvoiceSettlementVO invoiceSettlementVO = lastModifiedInvoice.get(invoicesTobeUpdated
													.size() - 1);
											/**
											 * If settlment has been associated to any invoice
											 * getChequeAmountInSettlementCurr will be set to the cheque amt in settlement currency else null
											 *
											 */
											if(settlementDetailsVO.getChequeAmountInSettlementCurr()!=null){
												if(!invoiceSettlementVO.getBillingCurrencyCode().equals(settlementDetailsVO.getChequeCurrency())){

													//populating system parameter
													//converting cheque amt from billing currency to settlement curr
													LocalDate date=new LocalDate
													(LocalDate.NO_STATION,Location.NONE,true);

													String exchangeRateBasis=null;
													String exchangeRateDateBasis=null;

													SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
													Collection<String> systemParameterCodes = new ArrayList<String>();
													systemParameterCodes.add(SYS_PARAM_EXGRATBAS);
													systemParameterCodes.add(SYS_PARAM_EXGRATDATBAS);
													Map<String, String> systemParameters = null;
													try {
														systemParameters = sharedDefaultsProxy
														.findSystemParameterByCodes(systemParameterCodes);
													} catch (ProxyException proxyException) {
														throw new SystemException(proxyException.getMessage(),
																proxyException);
													}
													if(systemParameters!=null &&systemParameters.size()>0 ){
														exchangeRateBasis=systemParameters.get(SYS_PARAM_EXGRATBAS);
														exchangeRateDateBasis=systemParameters.get(SYS_PARAM_EXGRATDATBAS);
														if("false".equals(settlementDetailsVO.getSettlementExists())) {
															date=invoiceSettlementVO.getBillingPeriodTo();

														}else{
															if("I".equals(exchangeRateDateBasis)){
																date= invoiceSettlementVO.getBillingPeriodTo();
															}else{
																date = settlementDetailsVO.getChequeDate();
															}
														}

													}
													//
													exchangeRate = GPABillingSettlement
													.findExchangeRate(
															settlementDetailsVO.getCompanyCode(),
															settlementDetailsVO.getChequeCurrency(),
															invoiceSettlementVO.getBillingCurrencyCode(),
															exchangeRateBasis,
															date);
													outStandingAmtInSettCurr = settlementDetailsVO
													.getChequeAmount().getRoundedAmount()* exchangeRate;


												}
												try {
													outStgAmtInSettCurr = CurrencyHelper
													.getMoney(settlementDetailsVO
															.getChequeCurrency());
												} catch (CurrencyException e) {
													log.log(Log.FINE,  "CurrencyException");
												}



												try {
													outStgAmtInBillCurr = CurrencyHelper
													.getMoney(invoiceSettlementVO
															.getBillingCurrencyCode());
												} catch (CurrencyException e) {
													log.log(Log.FINE,  "CurrencyException");
												}
												outStgAmtInSettCurr.setAmount(outStandingAmtInSettCurr);
												outStgAmtInBillCurr.setAmount(outStgAmtInBillingCurr);

											}
											//case where cheque is not associtaed to any invoice
											else{
												//populating system parameter
												LocalDate date=new LocalDate
												(LocalDate.NO_STATION,Location.NONE,true);

												String exchangeRateBasis=null;
												String exchangeRateDateBasis=null;

												SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
												Collection<String> systemParameterCodes = new ArrayList<String>();
												systemParameterCodes.add(SYS_PARAM_EXGRATBAS);
												systemParameterCodes.add(SYS_PARAM_EXGRATDATBAS);
												Map<String, String> systemParameters = null;
												try {
													systemParameters = sharedDefaultsProxy
													.findSystemParameterByCodes(systemParameterCodes);
												} catch (ProxyException proxyException) {
													throw new SystemException(proxyException.getMessage(),
															proxyException);
												}
												if(systemParameters!=null &&systemParameters.size()>0 ){
													exchangeRateBasis=systemParameters.get(SYS_PARAM_EXGRATBAS);
													exchangeRateDateBasis=systemParameters.get(SYS_PARAM_EXGRATDATBAS);
													if("false".equals(settlementDetailsVO.getSettlementExists())) {
														date=invoiceSettlementVO.getBillingPeriodTo();

													}else{
														if("I".equals(exchangeRateDateBasis)){
															date= invoiceSettlementVO.getBillingPeriodTo();
														}else{
															date = settlementDetailsVO.getChequeDate();
														}
													}

												}
												//
												exchangeRate = GPABillingSettlement
												.findExchangeRate(
														settlementDetailsVO.getCompanyCode(),
														invoiceSettlementVO.getBillingCurrencyCode(),
														settlementDetailsVO.getChequeCurrency(),
														exchangeRateBasis,
														date);
												outStgAmtInBillingCurr = settlementDetailsVO
												.getChequeAmount().getRoundedAmount()
												* exchangeRate;
												try {
													outStgAmtInBillCurr = CurrencyHelper
													.getMoney(invoiceSettlementVO
															.getBillingCurrencyCode());
												} catch (CurrencyException e) {
													log.log(Log.FINE,  "CurrencyException");
												}

												try {
													outStgAmtInSettCurr = CurrencyHelper
													.getMoney(settlementDetailsVO
															.getChequeCurrency());
												} catch (CurrencyException e) {
													log.log(Log.FINE,  "CurrencyException");
												}
												outStgAmtInBillCurr.setAmount(outStgAmtInBillingCurr);

												outStgAmtInSettCurr.setAmount(outStandingAmtInSettCurr);

											}

											if (SETTLED
													.equals(invoiceSettlementVO
															.getSettlementStatus())) {

												//double dueAmt=invoiceSettlementVO
												//.getDueAmount().getRoundedAmount();
												//double outStandingAmt=settlementDetailsVO.getChequeAmount().getRoundedAmount();
												//Modified by A-6991 for ICRD-211662
												try {
													cn51SummaryEntity = CN51Summary.find(
															invoiceSettlementVO
															.getCompanyCode(),
															invoiceSettlementVO
															.getInvoiceNumber(),
															invoiceSettlementVO.getInvSerialNumber(),
															invoiceSettlementVO
															.getGpaCode());
												} catch (FinderException ex) {
													throw new SystemException(
															ex.getMessage(), ex);
												}
												double dueAmt=cn51SummaryEntity.getDueAmount();
												cn51SummaryEntity
												.setDueAmount(dueAmt-outStgAmtInBillCurr.getRoundedAmount());

											}
										}
									}

									settlementDetailsVO.setOutStandingChqAmt(outStgAmtInSettCurr);
									updateSettlement(gpaSettlementVO, settlementDetailsVO);

								}


							}
						}
					}

				}

				else{
					log.log(Log.INFO,
					"!!!!  NO UPDATION   !!!!!!!!!!!!!!!!!!!");
				}
				//Accounting triggered on Settlement save

				triggerAccounting(gpaSettlementVO);
			}


		}
		log.exiting(CLASS_NAME, "saveSettlementDetails");
	}


	/**
	 * method for finding outstanding cheque amounts
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<SettlementDetailsVO> findOutStandingChequesForGPA(
			GPASettlementVO gpaSettlementVO) throws SystemException {

		return GPABillingSettlement.findOutStandingChequesForGPA(gpaSettlementVO);
	}

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws SystemException
	 */
	private void triggerAccounting(GPASettlementVO gpaSettlementVO) throws SystemException {
		MRAGPABillingDAO mragpaBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "triggerAccounting");
			mragpaBillingDAO
			.triggerAccounting(gpaSettlementVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @param gpaSettlementVO
	 * @param settlementDetailsVO
	 * @throws SystemException
	 */
	private void updateSettlement(GPASettlementVO gpaSettlementVO, SettlementDetailsVO settlementDetailsVO) throws SystemException {
		/*
		 * Updation for GPABillingSettlementDetails is necessary only if cheque is dishonoured
		 * else continue as a new insert
		 */
		Integer seqNum= null;
		GPABillingSettlementDetails gpaBillingSettlementDetailsEntity= null;
		GPABillingSettlement gpaBillingSettlement=null;
		try {
			/*
			 * Find GPABillingSettlement
			 */
			gpaBillingSettlement= GPABillingSettlement.find(gpaSettlementVO.getCompanyCode(),
					gpaSettlementVO.getGpaCode(), settlementDetailsVO.getSettlementId(),
					settlementDetailsVO.getSettlementSequenceNumber());
			//MALMRAGPASTLDTL
			gpaBillingSettlementDetailsEntity= GPABillingSettlementDetails.find(settlementDetailsVO.getCompanyCode(),
					settlementDetailsVO.getGpaCode(), settlementDetailsVO.getSettlementId(),
					settlementDetailsVO.getSerialNumber(),settlementDetailsVO.getSettlementSequenceNumber());
			if(gpaBillingSettlementDetailsEntity!=null){
				if(gpaSettlementVO.isFromBatchSettlmentJob())
				{
				gpaBillingSettlementDetailsEntity.setLastUpdatedUser("ADVPY");
				}
				settlementDetailsVO.setSettlementExists("true");
				if("Y".equals(settlementDetailsVO.getIsDeleted())){
					gpaBillingSettlementDetailsEntity.setIsDeleted("Y");
				}else if((settlementDetailsVO.getChequeAmount().getAmount()!=0 )&& ("0000".equals(settlementDetailsVO.getChequeNumber()))) {
					gpaBillingSettlementDetailsEntity.setSettlementAmount(settlementDetailsVO.getChequeAmount().getAmount());
				}
				gpaBillingSettlementDetailsEntity.setAccStatus(settlementDetailsVO.getIsAccounted());
				if(settlementDetailsVO.getOutStandingChqAmt()!=null){
					gpaBillingSettlementDetailsEntity.setOutStandingAmount(settlementDetailsVO.getOutStandingChqAmt().getAmount());

				}


			}
		} catch (FinderException e) {
			if(gpaBillingSettlement ==null){
				settlementDetailsVO.setSettlementExists("false");
				gpaBillingSettlement=new GPABillingSettlement(gpaSettlementVO);
				seqNum = Integer.valueOf(gpaBillingSettlement
						.getGpaBillingSettlementPK().getSettlementSequenceNumber());
				settlementDetailsVO
				.setSettlementSequenceNumber(seqNum.intValue());
			}
			else if(gpaBillingSettlementDetailsEntity==null){
				//settlementDetailsVO.setSettlementSequenceNumber(gpaSettlementVO.getSettlementSequenceNumber());
				gpaBillingSettlementDetailsEntity=new GPABillingSettlementDetails(settlementDetailsVO);
			}
		}
		/*
		 * Find and update GPABillingSettlementDetails
		 */
	}

	/**
	 *
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param isConvNeedMap
	 * @param prevSettKey
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	private void updateInvoiceDetails(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO,Map<String, Double> isConvNeedMap) throws MailTrackingMRABusinessException, SystemException {
		double amountToUpdateIncn51smy = 0;
		double amountToUpdateInSettlement = 0;
		Collection<InvoiceSettlementVO> associatedInvoices = null;
		Boolean isDeleted=false;
		if("Y".equals(settlementDetailsVO.getIsDeleted())){
			/*
			 * Finding Associated invoices for the settlement (Cheque) and
			 * then updating the deleted flag and updating the invoice Details accordingly
			 */
			associatedInvoices = findInvoiceVOForSettlement(settlementDetailsVO);
			if(associatedInvoices!=null && associatedInvoices.size() > 0){
				for(InvoiceSettlementVO invoiceSettlementVO:associatedInvoices ){
					isDeleted=true;
					amountToUpdateIncn51smy=updateInvoiceSettlementDetails(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn51smy);
					//amountToUpdateIncn51smy=updateInvoiceStatus(settlementDetailsVO,invoiceSettlementVO);
					if(invoiceSettlementVO.getDueAmount()!=null  ){
						amountToUpdateInSettlement = invoiceSettlementVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
						if(amountToUpdateInSettlement<0){
							amountToUpdateInSettlement=-(amountToUpdateInSettlement);
						}
						unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
						//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
						updateInvoiceStatusForChequeDishonour(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn51smy);
					}

				}
			}
			/*amountToUpdateIncn51smy=updateInvoiceSettlementDetails(settlementDetailsVO,unSettledInvoiceVO,amountToUpdateIncn51smy);
			amountToUpdateInSettlement = unSettledInvoiceVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
			unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
			settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
			updateInvoiceStatusForChequeDishonour(settlementDetailsVO,unSettledInvoiceVO,amountToUpdateIncn51smy);*/


		}else{
			amountToUpdateIncn51smy=updateInvoiceStatus(settlementDetailsVO,unSettledInvoiceVO,isConvNeedMap);
			//amountToUpdateInSettlement = unSettledInvoiceVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
			//unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
			//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateIncn51smy);
			updateInvoiceSettlementDetails(settlementDetailsVO,unSettledInvoiceVO, amountToUpdateIncn51smy);
		}
	}
	private void updateChequeDishonour(SettlementDetailsVO settlementDetailsVO) throws SystemException{
		double amountToUpdateIncn51smy = 0;
		double amountToUpdateInSettlement = 0;
		Collection<InvoiceSettlementVO> associatedInvoices = null;
		Boolean isDeleted=false;
		if("Y".equals(settlementDetailsVO.getIsDeleted()) ){
			/*
			 * Finding Associated invoices for the settlement (Cheque) and
			 * then updating the deleted flag and updating the invoice Details accordingly
			 */
			associatedInvoices = findInvoiceVOForSettlement(settlementDetailsVO);
			if(associatedInvoices!=null && associatedInvoices.size() > 0){
				for(InvoiceSettlementVO invoiceSettlementVO:associatedInvoices ){
					isDeleted=true;
					amountToUpdateIncn51smy=updateInvoiceSettlementDetails(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn51smy);
					//amountToUpdateIncn51smy=updateInvoiceStatus(settlementDetailsVO,invoiceSettlementVO);
					if(invoiceSettlementVO.getDueAmount()!=null  ){
						amountToUpdateInSettlement = invoiceSettlementVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
						if(amountToUpdateInSettlement<0){
							amountToUpdateInSettlement=-(amountToUpdateInSettlement);
						}
						//unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
						//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
						updateInvoiceStatusForChequeDishonour(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn51smy);
					}
				}
			}}
	}
	/**
	 *
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param amountToUpdateIncn51smy
	 * @throws SystemException
	 */
	protected void updateInvoiceStatusForChequeDishonour(
			SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO,
			double amountToUpdateIncn51smy) throws SystemException {
		String overrideRounding="";
		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		CN51Summary cn51SummaryEntity = null;
		double settledAmount=0.0;
		try {
			cn51SummaryEntity = CN51Summary.find(
					settlementDetailsVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getInvSerialNumber(),
					settlementDetailsVO
					.getGpaCode());
		} catch (FinderException ex) {
			throw new SystemException(
					ex.getMessage(), ex);
		}
		//Modified by A-6991 for ICRD-211662
		//delete amount updated in GpaBillingSettlementInvoiceDetails Entity from the settled amount and update status
		settledAmount = cn51SummaryEntity
		.getSettlementAmount()-amountToUpdateIncn51smy;
		settledAmount =getScaledValue(settledAmount,Integer.parseInt(overrideRounding));
		cn51SummaryEntity
		.updateSettlementStatus(settledAmount,unSettledInvoiceVO,Integer.parseInt(overrideRounding));

	}

	/**
	 *
	 * @param settlementDetailsVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<InvoiceSettlementVO> findInvoiceVOForSettlement(
			SettlementDetailsVO settlementDetailsVO) throws SystemException {

		Collection<GPASettlementVO> settledGpaSettlementVOs = null;
		settledGpaSettlementVOs = GPABillingSettlement
		.findSettledInvoicesForGPA(settlementDetailsVO);
		if(settledGpaSettlementVOs!=null && settledGpaSettlementVOs.size()>0){
			return settledGpaSettlementVOs.iterator().next().getInvoiceSettlementVOs();
		}
		else{
			return null;
		}

	}



	/**
	 *
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param amountToUpdateIncn51smy
	 * @return
	 * @throws SystemException
	 */
	private double updateInvoiceSettlementDetails(
			SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO, double amountToUpdateIncn51smy) throws SystemException {
		InvoiceSettlementVO invoiceSettlementVO = null;
		GPABillingSettlementInvoiceDetail settlementInvoiceEntity=null;

		try {

			invoiceSettlementVO = constructInvoiceSettlementVO(
					settlementDetailsVO,
					unSettledInvoiceVO,
					amountToUpdateIncn51smy);
			settlementInvoiceEntity = GPABillingSettlementInvoiceDetail
			.find(invoiceSettlementVO
					.getCompanyCode(),
					invoiceSettlementVO
					.getGpaCode(),
					settlementDetailsVO
					.getSettlementId(),
					settlementDetailsVO
					.getSettlementSequenceNumber(),
					invoiceSettlementVO
					.getInvoiceNumber(),
					settlementDetailsVO
							.getSerialNumber(),
							invoiceSettlementVO.getMailsequenceNum());//modified a-7871 ICRD-235799
			settlementInvoiceEntity.update(invoiceSettlementVO);
			if("Y".equals(settlementDetailsVO.getIsDeleted())){
				//settlementInvoiceEntity.setIsDeleted(true);
				amountToUpdateIncn51smy=settlementInvoiceEntity.getSettledAmount();
			}



		}

		catch (FinderException ex) {
			new GPABillingSettlementInvoiceDetail(
					invoiceSettlementVO);
		}
		return amountToUpdateIncn51smy;

	}


	// ****************************************************************************************


    private void updateInvoiceRemarks(
   GPASettlementVO unSettledInvoiceVO) throws SystemException {

    // InvoiceSettlementVO invoiceSettlementVO = null;
     GPABillingSettlementInvoiceDetail settlementInvoiceEntity=null;

 	for(InvoiceSettlementVO invoiceSettlementVO : unSettledInvoiceVO.getInvoiceSettlementVO())
 	{


 		//if(invoiceSettlementVO.getSettlementId()!=null) {
     try {

            settlementInvoiceEntity = GPABillingSettlementInvoiceDetail
            .find(invoiceSettlementVO
                         .getCompanyCode(),
                         invoiceSettlementVO
                         .getGpaCode(),
                         invoiceSettlementVO
                         .getSettlementId(),
                         invoiceSettlementVO
                         .getSettlementSequenceNumber(),
                         invoiceSettlementVO.getInvoiceNumber(),
                         invoiceSettlementVO.getSerialNumber(),
                         invoiceSettlementVO.getMailsequenceNum());
            settlementInvoiceEntity.setRemarks(invoiceSettlementVO.getRemarks());
     }

     catch (FinderException ex) {
            new GPABillingSettlementInvoiceDetail(
                         invoiceSettlementVO);
     }
 		} /*else {
 			//TODO

 		}*/
 	//}
    }


	/**
	 *
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param currSetKey
	 * @param prevSettKey
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	protected double updateInvoiceStatus(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO, Map<String, Double> isConvNeedMap) throws MailTrackingMRABusinessException, SystemException {
		// TODO Auto-generated method stub
		CN51Summary cn51SummaryEntity = null;
		double exchangeRate = 1;
		double chequeAmount = 0.0;
		double dueAmount=0.0;
		double settledAmount=0.0;
		double amountToUpdateIncn51smy = 0;
		Money chequeAmountInBillingCurrency = null;
		Money chequeAmountInSettlemntCurrency = null;
		GPASettlementVO gpaSettlementVO = new GPASettlementVO();
		String currentSetKey=settlementDetailsVO.getSettlementId().concat(String.valueOf(settlementDetailsVO.getSettlementSequenceNumber())
				.concat(String.valueOf(settlementDetailsVO.getSerialNumber())));
		String overrideRounding="";
		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		/**
		 * added check for avoiding multiple currency conversion for the same cheque.
		 */
		if(!isConvNeedMap.containsKey(currentSetKey)){
			exchangeRate = getExChangeRate( settlementDetailsVO,
					unSettledInvoiceVO);
			isConvNeedMap.put(currentSetKey, exchangeRate);
		}
		chequeAmount = settlementDetailsVO
		.getChequeAmount().getRoundedAmount()
		* exchangeRate;
		try {
			chequeAmountInBillingCurrency = CurrencyHelper
			.getMoney(unSettledInvoiceVO
					.getBillingCurrencyCode());
			chequeAmountInSettlemntCurrency = CurrencyHelper
			.getMoney(settlementDetailsVO
					.getChequeCurrency());
		} catch (CurrencyException e) {
			log.log(Log.FINE,  "CurrencyException");
		}
		if(chequeAmountInBillingCurrency!=null) {
		chequeAmountInBillingCurrency
		.setAmount(chequeAmount);
		}
//Modified by for ICRD-211662
		try {
			cn51SummaryEntity = CN51Summary.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getInvSerialNumber(),
					unSettledInvoiceVO
					.getGpaCode());
		} catch (FinderException ex) {
			throw new SystemException(
					ex.getMessage(), ex);
		}
		dueAmount = cn51SummaryEntity
		.getTotalAmountInBillingCurr()
		- cn51SummaryEntity.getSettlementAmount();
		dueAmount=getScaledValue(dueAmount,Integer.parseInt(overrideRounding));
		settledAmount = cn51SummaryEntity
		.getSettlementAmount();
		if (chequeAmountInBillingCurrency!=null&&dueAmount >=chequeAmountInBillingCurrency
				.getRoundedAmount()) {
			/*
			 * update due amount as dueamount - =
			 * netamount and set the settled amount
			 * as settledamount + = netamount
			 */
			dueAmount -= chequeAmountInBillingCurrency
			.getRoundedAmount();
			dueAmount=getScaledValue(dueAmount,Integer.parseInt(overrideRounding));
			settledAmount += chequeAmountInBillingCurrency
			.getRoundedAmount();
			settledAmount=getScaledValue(settledAmount,Integer.parseInt(overrideRounding));

			amountToUpdateIncn51smy = chequeAmountInBillingCurrency
			.getRoundedAmount();
			cn51SummaryEntity
			.updateSettlementStatus(settledAmount,unSettledInvoiceVO,Integer.parseInt(overrideRounding));
			chequeAmountInBillingCurrency
			.setAmount(0);
			settlementDetailsVO.getChequeAmount().setAmount(0);
			settlementDetailsVO.setOutStandingChqAmt(chequeAmountInBillingCurrency);
			if(chequeAmountInSettlemntCurrency!=null) {
			chequeAmountInSettlemntCurrency.setAmount(0);
			}
			settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);
			if(unSettledInvoiceVO.getDueAmount()!=null){
				unSettledInvoiceVO.getDueAmount().setAmount(dueAmount);
				if(unSettledInvoiceVO.getDueAmount().getRoundedAmount()==0){
					unSettledInvoiceVO.setSettlementStatus(SETTLED);
				}
				else{
					unSettledInvoiceVO.setSettlementStatus(DIFFERENCE);
				}
			}

		} else {
			if (chequeAmountInBillingCurrency!=null&&dueAmount < chequeAmountInBillingCurrency
				.getRoundedAmount()) {
			/*
			 * update due amount as dueamount = 0
			 * and set the settled amount as
			 * settledamount + = netamount
			 */

			settledAmount +=  chequeAmountInBillingCurrency
					.getRoundedAmount();
			settledAmount=getScaledValue(settledAmount,Integer.parseInt(overrideRounding));
			amountToUpdateIncn51smy = dueAmount;
			// cn51Summary.setSettledAmount(settledAmount)
			cn51SummaryEntity
			.updateSettlementStatus(settledAmount,unSettledInvoiceVO,Integer.parseInt(overrideRounding));
			Double amountAfterUdation = chequeAmountInBillingCurrency
			.getRoundedAmount() - dueAmount;

			amountAfterUdation=getScaledValue(amountAfterUdation,Integer.parseInt(overrideRounding));

			chequeAmountInBillingCurrency
			.setAmount(amountAfterUdation);
			dueAmount = 0;
			settlementDetailsVO.getChequeAmount().setAmount(amountAfterUdation);
			if(chequeAmountInSettlemntCurrency!=null) {
			chequeAmountInSettlemntCurrency.setAmount(amountAfterUdation);
			}
			settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);
			settlementDetailsVO.setOutStandingChqAmt(chequeAmountInBillingCurrency);
			if(unSettledInvoiceVO.getDueAmount()!=null){
				unSettledInvoiceVO.getDueAmount().setAmount(dueAmount);
			}
			/*//Converting cheque amount back to settlement currency
			Double amountAfterUdationInSettlCurr=0.0
			exchangeRate = getExChangeRate( settlementDetailsVO,
					 unSettledInvoiceVO)
			amountAfterUdationInSettlCurr = amountAfterUdation
			 * exchangeRate;
			settlementDetailsVO.getChequeAmount().setAmount(amountAfterUdationInSettlCurr)
			settlementDetailsVO.setOutStandingChqAmt(settlementDetailsVO.getChequeAmount())
			//ends
			 */
			unSettledInvoiceVO.setSettlementStatus(SETTLED);
			}
		}
		/**
		 * Update SettlementDetail Entity with the outstanding amount
		 */
		gpaSettlementVO.setCompanyCode(settlementDetailsVO.getCompanyCode());
		gpaSettlementVO.setGpaCode(settlementDetailsVO.getGpaCode());
		gpaSettlementVO.setSettlementSequenceNumber(settlementDetailsVO.getSettlementSequenceNumber());
		updateSettlement(gpaSettlementVO, settlementDetailsVO);
		//entity updation ends

		return amountToUpdateIncn51smy;

	}

	/**
	 * Method to find the exchange rate
	 * @param companyCode
	 * @param billingCurrencyCode
	 * @param settlementCurrency
	 * @return exchangeRate
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 */
	private double getExChangeRate(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO) throws MailTrackingMRABusinessException, SystemException {


		LocalDate date=new LocalDate
		(LocalDate.NO_STATION,Location.NONE,true);
		double exchangeRate = 0.0;
		String exchangeRateBasis=null;
		String exchangeRateDateBasis=null;

		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_EXGRATBAS);
		systemParameterCodes.add(SYS_PARAM_EXGRATDATBAS);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsProxy
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			exchangeRateBasis=systemParameters.get(SYS_PARAM_EXGRATBAS);
			exchangeRateDateBasis=systemParameters.get(SYS_PARAM_EXGRATDATBAS);
			if("false".equals(settlementDetailsVO.getSettlementExists())) {
				date=unSettledInvoiceVO.getBillingPeriodTo();

			}else{
				if("I".equals(exchangeRateDateBasis)){
					date= unSettledInvoiceVO.getBillingPeriodTo();
				}else{
					date = settlementDetailsVO.getChequeDate();
				}
			}

		}
		if(exchangeRateMap.get(unSettledInvoiceVO.getBillingCurrencyCode()+settlementDetailsVO.getChequeCurrency())!=null){

			exchangeRate = exchangeRateMap.get(unSettledInvoiceVO.getBillingCurrencyCode()+settlementDetailsVO.getChequeCurrency());
		}else{
			/**
			 * TODO to check if this returns correct exchange rate
			 */
			exchangeRate = GPABillingSettlement
			.findExchangeRate(
					settlementDetailsVO.getCompanyCode(),
					unSettledInvoiceVO.getBillingCurrencyCode(),
					settlementDetailsVO.getChequeCurrency(),exchangeRateBasis,date);
			exchangeRateMap.put(unSettledInvoiceVO.getBillingCurrencyCode()+settlementDetailsVO.getChequeCurrency(),exchangeRate);
		}
		return exchangeRate;
	}

	/**
	 *
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 * @throws GenerationFailedException
	 */
	private String generateSettlementReferenceNumber(
			GPASettlementVO gpaSettlementVO) throws GenerationFailedException, SystemException {
		LocalDate localDate = gpaSettlementVO.getSettlementDate();
		if(localDate==null){
			LogonAttributes logonVO = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			localDate=new LocalDate(logonVO.getAirportCode(),ARP,true);
		}
		if (localDate != null) {
			String dateString = localDate.toDisplayDateOnlyFormat();
			String[] dateArray = dateString.split("-");
			gpaSettlementVO.setSettlementDate(localDate);
			String year = "";
			String month = "";
			String date = "";
			String referenceNo = "";
			String mailbag="M";//added by a-7871 for ICRD-235799
			if (dateArray != null && dateArray.length >= 3) {
				year = dateArray[2];
				month = dateArray[1].toUpperCase();
				date = dateArray[0];
			}
			String key = new StringBuilder(year).append(month).toString();

			Criterion criterion = KeyUtils.getCriterion(
					gpaSettlementVO.getCompanyCode(), STLREF_KEY, key);
			String generatedKey;
			if(gpaSettlementVO.getInvoiceSettlementVO()!=null&&gpaSettlementVO.getInvoiceSettlementVO().iterator().next().getSettlementLevel()!=null && gpaSettlementVO.getInvoiceSettlementVO().iterator().next().getSettlementLevel().equals("M")){//modified by a-7871 for ICRD-235799
				generatedKey=mailbag.concat(String.valueOf(KeyUtils.getKey(criterion)));
			}else if(gpaSettlementVO.getInvoiceSettlementVOs()!=null&&"EXCELUPLOAD".equals(gpaSettlementVO.getFrmScreen())){//added for icrd-235819
				generatedKey=mailbag.concat(String.valueOf(KeyUtils.getKey(criterion)));
			}else{
				generatedKey= String.valueOf(KeyUtils.getKey(criterion));
			}
			//running serial number in the format 001,002 etc
			if (generatedKey.length() == 1) {
				referenceNo = new StringBuilder(year).append(month)
				.append(date).append("00").append(generatedKey)
				.toString();
			} else if (generatedKey.length() == 2) {
				referenceNo = new StringBuilder(year).append(month)
				.append(date).append("0").append(generatedKey)
				.toString();
			} else {
				referenceNo = new StringBuilder(year).append(month)
				.append(date).append(generatedKey).toString();
			}
			log.log(Log.FINE, "referenceNo Generated", referenceNo);
			return  referenceNo;

		}
		else {
			return null;
		}
	}

	/**
	 *
	 * @param settlementDetailsVO
	 * @param invoiceSettlementVO
	 * @param amountToUpdateIncn51smy
	 * @param chequeAmountInBillingCurrency
	 * @return
	 */
	private InvoiceSettlementVO constructInvoiceSettlementVO(
			SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO associatedInvoiceVO,
			double amountToUpdateIncn51smy) {
		InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
		double dueAmount = 0;
		Money dueAmountInBillingCurr = null;
		Money currentSettlingAmt = null;
		invoiceSettlementVO
		.setCompanyCode(settlementDetailsVO.getCompanyCode());
		invoiceSettlementVO.setGpaCode(settlementDetailsVO.getGpaCode());
		invoiceSettlementVO.setInvSerialNumber(settlementDetailsVO.getInvSerialNumber());////Modified and added INVSERNUM for ICRD-211662
		invoiceSettlementVO.setSettlementId(settlementDetailsVO
				.getSettlementId());
		invoiceSettlementVO.setSettlementSequenceNumber(settlementDetailsVO
				.getSettlementSequenceNumber());
		if("Y".equals(settlementDetailsVO.getIsDeleted())){
		invoiceSettlementVO.setIsDeleted(true);
		}else{
			invoiceSettlementVO.setIsDeleted(false);
		}
		invoiceSettlementVO.setSerialNumber(settlementDetailsVO
				.getSerialNumber());
		invoiceSettlementVO.setRemarks(associatedInvoiceVO.getRemarks());
		invoiceSettlementVO.setAmountAlreadySettled(associatedInvoiceVO
				.getAmountAlreadySettled());
		invoiceSettlementVO.setAmountInSettlementCurrency(associatedInvoiceVO
				.getAmountInSettlementCurrency());
		invoiceSettlementVO.setInvoiceNumber(associatedInvoiceVO
				.getInvoiceNumber());
		//invoiceSettlementVO.setIsDeleted(settlementDetailsVO.isDeleted());
		if(associatedInvoiceVO.getAmountInSettlementCurrency()!=null && associatedInvoiceVO.getAmountAlreadySettled()!=null){
			dueAmount = associatedInvoiceVO.getAmountInSettlementCurrency()
			.getRoundedAmount()
			- associatedInvoiceVO.getAmountAlreadySettled().getRoundedAmount();
		}

		try {
			dueAmountInBillingCurr = CurrencyHelper
			.getMoney(associatedInvoiceVO.getBillingCurrencyCode());
		} catch (CurrencyException e) {
			log.log(Log.FINE,  "CurrencyException");
		}

		dueAmountInBillingCurr.setAmount(dueAmount);
		try {
			currentSettlingAmt = CurrencyHelper.getMoney(associatedInvoiceVO
					.getBillingCurrencyCode());
		} catch (CurrencyException e) {
			log.log(Log.FINE,  "CurrencyException");
		}

		currentSettlingAmt.setAmount(amountToUpdateIncn51smy);
	
		invoiceSettlementVO.setCurrentSettlingAmount(currentSettlingAmt);
		invoiceSettlementVO.setMailsequenceNum(associatedInvoiceVO.getMailsequenceNum());// added by a-7871 for
																							// ICRD-235799
		return invoiceSettlementVO;

	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementVO
	 * @return
	 */
	private InvoiceSettlementHistoryVO populateInvoiceSettlementHistVO(
			InvoiceSettlementVO invoiceSettlementVO) {
		log.entering(CLASS_NAME, "populateInvoiceSettlementHistVO");
		InvoiceSettlementHistoryVO invoiceSettlementHistoryVO = new InvoiceSettlementHistoryVO();
		invoiceSettlementHistoryVO.setCompanyCode(invoiceSettlementVO
				.getCompanyCode());
		invoiceSettlementHistoryVO.setGpaCode(invoiceSettlementVO.getGpaCode());
		invoiceSettlementHistoryVO.setInvoiceNumber(invoiceSettlementVO
				.getInvoiceNumber());

		invoiceSettlementHistoryVO
		.setAmountInSettlementCurrency(invoiceSettlementVO
				.getCurrentSettlingAmount());
		invoiceSettlementHistoryVO.setSettlementDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, false));
		log.exiting(CLASS_NAME, "populateInvoiceSettlementHistVO");
		return invoiceSettlementHistoryVO;
	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementVO
	 * @return
	 * @throws SystemException
	 */
	//Modified for ICRD-211662
	private CN51Summary findCN51SummaryMatch(
			InvoiceSettlementVO invoiceSettlementVO) throws SystemException {
		log.entering(CLASS_NAME, "findCN51SummaryMatch");
		CN51Summary cn51SummaryMatch = null;
		try {
			cn51SummaryMatch = CN51Summary.find(
					invoiceSettlementVO.getCompanyCode(),
					invoiceSettlementVO.getInvoiceNumber(),
					invoiceSettlementVO.getInvSerialNumber(),
					invoiceSettlementVO.getGpaCode());
		} catch (FinderException finderException) {

			finderException.getErrorCode();
			throw new SystemException(finderException.getMessage());
		}

		log.exiting(CLASS_NAME, "findCN51SummaryMatch");
		return cn51SummaryMatch;

	}

	/**
	 * @author A-2408
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	/*
	 * public Map<String,Object> printReconciliationReport(ReportSpec
	 * reportSpec) throws SystemException,MailTrackingMRABusinessException{
	 * InvoiceSettlementFilterVO filterVO=(InvoiceSettlementFilterVO)reportSpec.
	 * getFilterValues().iterator().next(); Collection<InvoiceSettlementVO> vos=
	 * findSettlementDetails(filterVO); if(vos == null || vos.size()<=0){
	 * MailTrackingMRABusinessException mailTrackingMRABusinessException = new
	 * MailTrackingMRABusinessException(); ErrorVO reporterror = new
	 * ErrorVO(MailTrackingMRABusinessException.NO_DATA_FOUND);
	 * mailTrackingMRABusinessException.addError(reporterror); throw
	 * mailTrackingMRABusinessException; }
	 *
	 *
	 * ReportMetaData reportMetaData=new ReportMetaData();
	 *
	 * reportMetaData.setColumnNames(new String[]
	 * {"GPACOD",INVNUM,"TOTAMTCRTCUR","CRTCURCOD",
	 * "TOTAMTSTLCUR","STLAMT","STLCURCOD"}); reportMetaData.setFieldNames(new
	 * String[]
	 * {"gpaCode","invoiceNumber","amountInContractCurrency","contractCurrencyCode"
	 * ,
	 * "amountInSettlementCurrency","amountAlreadySettled","settlementCurrencyCode"
	 * }); reportSpec.setReportMetaData(reportMetaData);
	 * reportSpec.setData(vos); return ReportAgent.generateReport(reportSpec); }
	 */
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)
			throws SystemException {

		return SettlementHistory
		.findSettlementHistory(invoiceSettlementFilterVO);

	}

	/**
	 * @author a-2049
	 * @param companyCode
	 * @param gpaCode
	 * @return
	 * @throws SystemException
	 */
	public PostalAdministrationVO findPostalAdminDetails(String companyCode,
			String gpaCode) throws SystemException {
		log.entering(CLASS_NAME, "findPostalAdminDetails");
		PostalAdministrationVO adminDetailsVO = null;

		try {
			adminDetailsVO = new MailTrackingDefaultsProxy()
			.findPostalAdminDetails(companyCode, gpaCode);
		} catch (ProxyException proxyExp) {
			log.log(Log.SEVERE,
			" @@@ Proxy Exception thrown from MailTracking : Defaults Module ");
			throw new SystemException(proxyExp.getMessage(), proxyExp);
		}
		log.exiting(CLASS_NAME, "findPostalAdminDetails");
		return adminDetailsVO;
	}

	/**
	 * This method Finalizes the GPA billing Invoice
	 *
	 * Method Summary : ----------------- During the invoice finalization : case
	 * 1--> Invoice satus : PERFORMA-GENERATED ---> Finalization---> FINALIZED
	 * Despatch satus :PROFORMA-BILLABLE---> Finalization--->PROFORMA-BILLED
	 * Despatch satus :PROFORMA-ONHOLD ---->
	 * Finalization------>WITHDRAWN_PROFORMA<updation in billing detail table &
	 * cca tables > -Despatches get removed and amount deducted from summary
	 * table case 2--> Invoice satus : GENERATED--->
	 * Finalization--->DIRECTFINALISED Despatch satus :DIRECT-BILLABLE--->
	 * Finalization--->DIRECT-BILLED Despatch satus : ONHOLD ---->
	 * Finalization----> WITHDRAWN-DIRECT<updation in 66dtl,billing detail table
	 * cca tables > amount deducted from summary table When invoice status in
	 * finalized (in both cases)--Accounting gets triggered
	 */

	/**
	 * @author A-2554
	 * @param summaryVOs
	 * @param eInvoiceMsg
	 * @throws SystemException
	 * @throws FinderException
	 */
	public void finalizeandSendEInvoice(Collection<CN51SummaryVO> summaryVOs,
			String eInvoiceMsg) throws SystemException, FinderException {

		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_HONGKONGPOST);
		systemParameterCodes.add(SYS_PARAM_HONGKONGPOST_EMAILID);

		Map<String, String> systemParameters = null;
		String blgPRD = null;
		try {
			systemParameters = sharedDefaultsProxy
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}

		String hongKongPostPA = null;
		String hongKongPostEmailID = null;
		if (systemParameters != null && systemParameters.size() > 0) {
			hongKongPostPA = systemParameters.get(SYS_PARAM_HONGKONGPOST);
			hongKongPostEmailID = systemParameters
			.get(SYS_PARAM_HONGKONGPOST_EMAILID);
		}
		log.log(Log.INFO, "hongKongPostPA--->", hongKongPostPA);
		log.log(Log.INFO, "hongKongPostEmailID--->", hongKongPostEmailID);
		// comes from both listinvoice and invoiceenquiry where gpacode!=HKG
		if (("NOOP").equals(eInvoiceMsg)) {

			for (CN51SummaryVO summaryVO : summaryVOs) {
				finalizeInvoice(summaryVO);
			}
		} else // comes from list invoices where the selected invoice has
			// gpacode=HKG
			if (("GENMSG").equals(eInvoiceMsg)) {
				for (CN51SummaryVO summaryVO : summaryVOs) {
					// check if its gpacode=HKG,if yes then generatemessage and send
					// else simply finalize
					if (summaryVO.getGpaCode().equals(hongKongPostPA)) {
						String EInvoicemsg = generateEInvoiceMessage(summaryVO);
						if (summaryVO.getBillingPeriodFrom() != null
								&& summaryVO.getBillingPeriodFrom().trim().length() > 0) {
							blgPRD = new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false).setDate(
											summaryVO.getBillingPeriodFrom())
											.toDisplayFormat("yyyyMM");
						}
						sendEInvoiceAttachment(EInvoicemsg, hongKongPostEmailID,
								summaryVO.getInvoiceNumber(), blgPRD,
								summaryVO.getBillingPeriod(),
								summaryVO.getCompanyCode());
					}
					finalizeInvoice(summaryVO);
				}

			} else { // comes from invoiceenquiry with message generated in popup

				for (CN51SummaryVO summaryVO : summaryVOs) {
					log.log(Log.INFO, "EINVOICE MSG--->", eInvoiceMsg);
					if (summaryVO.getBillingPeriodFrom() != null
							&& summaryVO.getBillingPeriodFrom().trim().length() > 0) {
						blgPRD = new LocalDate(LocalDate.NO_STATION, Location.NONE,
								false).setDate(summaryVO.getBillingPeriodFrom())
								.toDisplayFormat("yyyyMM");
					}
					sendEInvoiceAttachment(eInvoiceMsg, hongKongPostEmailID,
							summaryVO.getInvoiceNumber(), blgPRD,
							summaryVO.getBillingPeriod(),
							summaryVO.getCompanyCode());
					finalizeInvoice(summaryVO);
				}

			}

	}

	private void sendEInvoiceAttachment(String Einvoicemsg, String address,
			String invoiceNumber, String blgPRD, String billingPeriod,
			String airlineCode) {
		log.log(Log.INFO, "SENDING  MSG--->", Einvoicemsg);
		String subject = invoiceNumber;
		subject = subject.concat("-").concat(billingPeriod);
		String filename = airlineCode;
		filename = filename.concat("_").concat(blgPRD).concat(".INV");
		GenericMessageVO genericMessageVO = new GenericMessageVO();
		genericMessageVO.setModeofCommunication(GenericMessageVO.MODE_EMAIL);
		HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put(GenericMessageVO.SUBJECT, (Object) subject);
		messageMap.put(GenericMessageVO.TO_ADDRESS, (Object) address);
		messageMap.put(GenericMessageVO.MESSAGE_TEXT, (String) Einvoicemsg);
		messageMap.put(GenericMessageVO.ATTACHEMENT_CONTENTS,
				(Object) Einvoicemsg.getBytes());
		messageMap.put(GenericMessageVO.ATTACHMENT_NAME, (Object) filename);
		genericMessageVO.setMessageDetails(messageMap);
		log.log(Log.INFO, "Sending EMAIL Message with genericMessageVO---->",
				genericMessageVO);
		try {
			new MsgBrokerMessageProxy().sendMessage(genericMessageVO);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}

	/**
	 * @author A-2554
	 * @param summaryVO
	 * @return
	 */
	public String generateEInvoiceMessage(CN51SummaryVO cN51SummaryVO) {

		log.log(Log.FINE,
		"\n\n\n\n<-------------generateEInvoiceMessage----------->");
		log.log(Log.FINE, "<-------------cN51SummaryVO----------->",
				cN51SummaryVO);
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		String billingPeriodTo = null;
		if (cN51SummaryVO != null && cN51SummaryVO.getBillingPeriodTo() != null
				&& cN51SummaryVO.getBillingPeriodTo().trim().length() > 0) {
			billingPeriodTo = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(
							cN51SummaryVO.getBillingPeriodTo()).toDisplayFormat(
							"yyyyMM");
		}
		/*
		 * HEADER CONSTRUCTOR
		 */
		StringBuffer headerConstructor = new StringBuffer()
		.append(EINVOICE_HEADER)// "HDR"
		.append(DATA_SEPARATOR)// "+"
		.append(cN51SummaryVO.getCompanyCode())// "NZ"
		.append(DATA_SEPARATOR)// "+"
		.append(billingPeriodTo)// "200904"
		.append(DATA_SEPARATOR)// "+"
		.append(currentDate.toDisplayFormat("yyyyMMddHHmmss"))// "20090429221418"
		.append(RECORD_DELIMTER);// "'"

		StringBuffer billingConstructor = new StringBuffer();
		for (CN66DetailsVO cn66DetailsVO : cN51SummaryVO.getCn66details()) {

			// if on proforma onhold then no need to send message for that
			// invoice
			if (cn66DetailsVO.getBillingStatus().equals(BLGSTA_PROFORMA_ONHOLD)) {
				continue;
			}
			String flightNumber = "";
			String flightCarrierCode = "";
			String flightDate = "";
			String subClass = "";
			if (cn66DetailsVO.getFlightNumber() != null
					&& cn66DetailsVO.getFlightNumber().trim().length() > 0) {
				flightNumber = cn66DetailsVO.getFlightNumber();
			}
			if (cn66DetailsVO.getFlightCarrierCode() != null
					&& cn66DetailsVO.getFlightCarrierCode().trim().length() > 0) {
				flightCarrierCode = cn66DetailsVO.getFlightCarrierCode();
			}
			if (cn66DetailsVO.getFlightDate() != null) {
				flightDate = cn66DetailsVO.getFlightDate().toDisplayFormat(
				"yyyyMMdd");
			}

			double fuelSurchargeRate = 0.0D;
			double fuelSurcharge = 0.0D;
			double totalWeight = 0.0D;
			try
			{
				totalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn66DetailsVO.getTotalWeight());

			}catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
			if (FUEL_SURCHARGE_IND_RATE.equalsIgnoreCase(cn66DetailsVO
					.getFuelSurchargeRateIndicator())) {
				fuelSurchargeRate = cn66DetailsVO.getFuelSurcharge();
				fuelSurcharge = cn66DetailsVO.getFuelSurcharge()
				* cn66DetailsVO.getTotalWeight();
			} else {
				fuelSurcharge = cn66DetailsVO.getFuelSurcharge();
				fuelSurchargeRate = cn66DetailsVO.getFuelSurcharge()
				/ cn66DetailsVO.getTotalWeight();
			}
			/*
			 * if the ACTSUBCLS is something starting with 'E' then u canmake
			 * the subclss in e-invoice as "SP" else u can take the SUBCLS frm
			 * MTKGPAC66DTL and show it in e-invoice
			 */
			if (cn66DetailsVO.getActualSubCls() != null
					&& cn66DetailsVO.getActualSubCls().startsWith("E")) {
				subClass = MAIL_SUBCLS_TYPE_SP;
			} else {
				subClass = cn66DetailsVO.getMailSubclass();
			}
			billingConstructor
			.append(NEW_LINE)
			.append("BIL")
			.append(DATA_SEPARATOR)

			// schedules departure date
			.append(flightDate)
			.append(DATA_SEPARATOR)

			// flight details
			.append(flightCarrierCode)
			.append(flightNumber)
			.append(DATA_SEPARATOR)

			// org office ex
			.append(cn66DetailsVO.getOrigin())
			.append(DATA_SEPARATOR)

			// dest office ex
			.append(cn66DetailsVO.getDestination())
			.append(DATA_SEPARATOR)

			// mail number
			.append(cn66DetailsVO.getDsn())
			.append(DATA_SEPARATOR)

			// uplift airport
			.append(cn66DetailsVO.getOrigin())
			.append(DATA_SEPARATOR)

			// offload airport
			.append(cn66DetailsVO.getDestination())
			.append(DATA_SEPARATOR)

			// type of mail subclass
			.append(subClass)
			.append(DATA_SEPARATOR)

			// charging type
			.append(BLANK_DATA)
			.append(CHARGING_TYPE)

			// total weight of airmail bags
			.append(DATA_SEPARATOR)

			.append(totalWeight)
			//.append(getScaledValue(cn66DetailsVO.getTotalWeight(), 1))

			// Billing Currency Code
			.append(DATA_SEPARATOR)
			.append(cN51SummaryVO.getBillingCurrencyCode())

			// Basic rate
			.append(DATA_SEPARATOR)
			.append(getScaledValue(cn66DetailsVO.getApplicableRate()
					- fuelSurchargeRate, 2))

					// Basic Total Amount
					.append(DATA_SEPARATOR)
					.append(getScaledValue((cn66DetailsVO.getActualAmount()
							.getRoundedAmount() - fuelSurcharge), 2))
							// Fuel Surcharge Rate
							.append(DATA_SEPARATOR)
							.append(getScaledValue(fuelSurchargeRate, 2))

							// Fuel Surcharge - Total Amount
							.append(DATA_SEPARATOR)
							.append(getScaledValue(fuelSurcharge, 2))

							// Total Amount
							.append(DATA_SEPARATOR)
							.append(getScaledValue(cn66DetailsVO.getActualAmount()
									.getRoundedAmount(), 2)).append(RECORD_DELIMTER);

		}
		/*
		 * Trailer Constructor
		 */
		StringBuffer trailerConstructor = new StringBuffer();
		trailerConstructor.append(NEW_LINE).append(EINVOICE_TRAILER)
		.append(DATA_SEPARATOR)
		.append(cN51SummaryVO.getCn66details().size())
		.append(RECORD_DELIMTER);

		/*
		 * Constructing E-Invoice
		 */
		StringBuffer einvoicebuilder = new StringBuffer();
		einvoicebuilder.append(headerConstructor);// HEADER
		einvoicebuilder.append(billingConstructor);// BILLING INFO
		einvoicebuilder.append(trailerConstructor);// TRAILER INFO

		log.log(Log.FINE, "\n\n\n\n<-------------EInvoiceMessage----------->",
				einvoicebuilder.toString());
		return einvoicebuilder.toString();
	}

	/**
	 * @author A-3227 RENO K ABRAHAM This method rounds the specified double
	 *         value to a precision specified
	 * @param value
	 * @param precision
	 * @return
	 */
	public double getScaledValue(double value, int precision) {

		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * @author A-3447
	 * @param summaryVO
	 * @throws FinderException
	 * @throws SystemException
	 */
	//modified for ICRD-211662
	private void finalizeInvoice(CN51SummaryVO summaryVO)
	throws SystemException, FinderException {

		log.entering("Finalize Invoice ", CLASS_NAME);
		String companyCode = summaryVO.getCompanyCode();
		String invoiceNumber = summaryVO.getInvoiceNumber();
		int invSerialNumber = summaryVO.getInvSerialNumber();
		String gpaCode = summaryVO.getGpaCode();
		String blgStatusOld = null;
		String blgStatusNew = null;
		String isMasterPresent = null;
		double totalAmountInBillingCurrency = 0.0;
		double totalWeight = 0.0;
		log.log(Log.FINE, "Summary vo in controller", summaryVO);
		try {

			/**
			 * finding the Entity using pKs.
			 */
			CN51Summary cN51Summary = CN51Summary.find(companyCode,
					invoiceNumber,invSerialNumber, gpaCode);
			if (cN51Summary != null) {
				Collection<CN66Details> cN66Details = new ArrayList<CN66Details>();
				Collection<CN51Details> cN51Details = new ArrayList<CN51Details>();
				Collection<CCADetail> cCADetails = new ArrayList<CCADetail>();
				Collection<MRABillingDetails> mRABillingDetails = new ArrayList<MRABillingDetails>();
				Collection<MRAGPABillingDetails> mRAGPABillingDetails = new ArrayList<MRAGPABillingDetails>();
				cN66Details = cN51Summary.getCN66Details();
				cN51Details = cN51Summary.getCn51details();
				totalAmountInBillingCurrency = cN51Summary
				.getTotalAmountInBillingCurr();
				log.log(Log.FINE, "totalAmountInBillingCurrency-->",
						totalAmountInBillingCurrency);
				if (cN66Details != null) {
					log.log(Log.FINE,
							"Not null--cN66Details.size****-------------",
							cN66Details.size());
					for (CN66Details cN66Detail : cN66Details) {
						try {
							log.log(Log.FINE, "inside loop---", cN66Detail.getBillingStatus());
							//Modified by A-7794 as part of MRA revamp
							MRABillingMaster mRABillingMaster = MRABillingMaster
							.find( cN66Detail
									.getCn66DetailsPK()
									.getCompanyCode(), cN66Detail
									.getCn66DetailsPK().getMailsequenceNumber());
							if (mRABillingMaster != null) {
								log.log(Log.FINE, "master not null");
								isMasterPresent = "true";
								if (mRABillingMaster.getCCADetail().size() > 0) {
									log.log(Log.FINE, "cca not null");
									cCADetails = mRABillingMaster
									.getCCADetail();
								}
								if (mRABillingMaster.getBillingDetails().size() > 0) {
									log.log(Log.FINE, "dtls null");
									mRABillingDetails = mRABillingMaster
									.getBillingDetails();
								}
								//Added by A-7794 as part of MRA revamp
								if(mRABillingMaster.getGpaBillingDetails().size() > 0){
									log.log(Log.FINE, "gpa billing dtls not null");
									mRAGPABillingDetails = mRABillingMaster.getGpaBillingDetails();
								}
							}
						} catch (FinderException finder) {
							isMasterPresent = "false";
							log.log(Log.SEVERE, "finder in mRABillingMaster***");
						}

						if (CN51SummaryVO.INVOICE_STATUS_PERFORMAGENERATED
								.equals(summaryVO.getInvoiceStatus())) {
							log.log(Log.FINE, "isMasterPresent",
									isMasterPresent);
							if (CN51SummaryVO.PROFORMA_BILLABLE
									.equals(cN66Detail.getBillingStatus())) {
								log.log(Log.FINE,
										"CN66Details.getBillingStatus()-",
										cN66Detail.getBillingStatus());
								cN66Detail
								.setBillingStatus(CN51SummaryVO.PROFORMA_BILLED);
								cN66Detail.setLastUpdateTime(cN66Detail
										.getLastUpdateTime());
								cN66Detail.setLastUpdatedUser(cN66Detail
										.getLastUpdatedUser());
								blgStatusOld = CN51SummaryVO.PROFORMA_BILLABLE;
								blgStatusNew = CN51SummaryVO.PROFORMA_BILLED;
								// amtTobeDeducted=totalAmountInBillingCurrency;///no
								// deduction needed in this case
								totalWeight += cN66Detail.getTotalWeight();
								//Added by A-7794 as part of MRA revamp
								if("true".equals(isMasterPresent) && mRAGPABillingDetails.size() > 0){
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRAGPABillingDetails, blgStatusOld,
											blgStatusNew);
								}
								/*if ("true".equals(isMasterPresent)) {
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRABillingDetails, blgStatusOld,
											blgStatusNew);
								}*/

							}

							if (CN51SummaryVO.PROFORMA_ONHOLD.equals(cN66Detail
									.getBillingStatus())) {
								log.log(Log.FINE,
										"CN66Details.getBillingStatus()-",
										cN66Detail.getBillingStatus());
								double amtDeducted = cN66Detail
								.getBilledAmountInBillingCurr();
								log.log(Log.FINE, "amtTobeDeducted-->>*",
										amtDeducted);
								totalAmountInBillingCurrency = totalAmountInBillingCurrency
								- amtDeducted;
								totalWeight -= cN66Detail.getTotalWeight();
								blgStatusOld = CN51SummaryVO.PROFORMA_ONHOLD;
								blgStatusNew = CN51SummaryVO.WITHDRAWN_PROFORMA;
								//Added by A-7794 as part of MRA revamp
								if("true".equals(isMasterPresent) && mRAGPABillingDetails.size() > 0){
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRAGPABillingDetails, blgStatusOld,
											blgStatusNew);
								}
								/*if ("true".equals(isMasterPresent)) {
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRABillingDetails, blgStatusOld,
											blgStatusNew);
								}*/
								// Added for correcting the amt and weight in
								// CN51DTL
								if (cN51Details != null) {
									for (CN51Details cN51Detail : cN51Details) {
										if (cN66Detail.getMailCategoryCode()
												.equals(cN51Detail
														.getMailCategoryCode())
														&& cN66Detail
														.getActualSubclass()
														.equals(cN51Detail
																.getMailSubclass())
																&& cN66Detail.getBillSectorOrigin()
																.equals(cN51Detail
																		.getOrigin())
																		&& cN66Detail
																		.getBillSectorToo()
																		.equals(cN51Detail
																				.getDestination())
																				&& cN66Detail.getApplicableRate() == cN51Detail
																				.getApplicableRate()
																				&& cN66Detail
																				.getContrctCurrencyCode()
																				.equals(cN51Detail
																						.getBillingCurrencyCode())) {
											double totalCN51AmtInBillingCurrency = cN51Detail
											.getTotalAmountinBillingCurr()
											- cN66Detail
											.getBilledAmountInBillingCurr();
											cN51Detail
											.setTotalAmountinBillingCurr(cN51Detail
													.getTotalAmountinBillingCurr()
													- cN66Detail
													.getBilledAmountInBillingCurr());
											cN51Detail
											.setTotalWeight(cN51Detail
													.getTotalWeight()
													- cN66Detail
													.getTotalWeight());
											log
													.log(
															Log.FINE,
															"totalCN51AmtInBillingCurrency===>>",
															totalCN51AmtInBillingCurrency);
											if (totalCN51AmtInBillingCurrency == 0.0) {
												log.log(Log.FINE,
												"inside if totalCN51AmtInBillingCur===>>");
												try {// removing the entity
													cN51Detail.remove();
												} catch (RemoveException e) {
													e.getErrorCode();
												}
											}
										}
									}
								}
								try {// removing the entity
									cN66Detail.remove();
								} catch (RemoveException e) {
									e.getErrorCode();
								}

							}
							log.log(Log.FINE, "-summaryVO-->>*", summaryVO.getInvoiceStatus());

						} else if (CN51SummaryVO.INVOICE_STATUS_GENERATED
								.equals(summaryVO.getInvoiceStatus())) {

							if (CN51SummaryVO.DIRECT_BILLABLE.equals(cN66Detail
									.getBillingStatus())) {
								log.log(Log.FINE,
										"-CN66Details.getBillingStatus()-s>>*",
										cN66Detail.getBillingStatus());
								cN66Detail
								.setBillingStatus(CN51SummaryVO.DIRECT_BILLED);
								cN66Detail.setLastUpdateTime(cN66Detail
										.getLastUpdateTime());
								cN66Detail.setLastUpdatedUser(cN66Detail
										.getLastUpdatedUser());
								// amtTobeDeducted=totalAmountInBillingCurrency;///no
								// deduction needed in this case
								totalWeight += cN66Detail.getTotalWeight();
								blgStatusOld = CN51SummaryVO.DIRECT_BILLABLE;
								blgStatusNew = CN51SummaryVO.DIRECT_BILLED;
								//Added by A-7794 as part of MRA revamp
								if("true".equals(isMasterPresent) && mRAGPABillingDetails.size() > 0){
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRAGPABillingDetails, blgStatusOld,
											blgStatusNew);
								}
								/*if ("true".equals(isMasterPresent)) {
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRABillingDetails, blgStatusOld,
											blgStatusNew);
								}*/
							}

							if (CN51SummaryVO.ONHOLD.equals(cN66Detail
									.getBillingStatus())) {
								log.log(Log.FINE,
										"-CN66Details.getBillingStatus()--",
										cN66Detail.getBillingStatus());
								double amtDeducted = cN66Detail
								.getBilledAmountInBillingCurr();

								totalAmountInBillingCurrency = totalAmountInBillingCurrency
								- amtDeducted;
								totalWeight -= cN66Detail.getTotalWeight();
								cN66Detail
								.setBillingStatus(CN51SummaryVO.WITHDRAWN_DIRECT);
								cN66Detail.setLastUpdateTime(cN66Detail
										.getLastUpdateTime());
								cN66Detail.setLastUpdatedUser(cN66Detail
										.getLastUpdatedUser());
								blgStatusOld = CN51SummaryVO.ONHOLD;
								blgStatusNew = CN51SummaryVO.WITHDRAWN_DIRECT;
								//Added by A-7794 as part of MRA revamp
								if("true".equals(isMasterPresent) && mRAGPABillingDetails.size() > 0){
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRAGPABillingDetails, blgStatusOld,
											blgStatusNew);
								}
								/*if ("true".equals(isMasterPresent)) {
									updateCCAandBlgDtls(cN66Detail, cCADetails,
											mRABillingDetails, blgStatusOld,
											blgStatusNew);
								}*/

							}

						}

					}
				}

			}

			SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
			Collection<String> systemParameterCodes = new ArrayList<String>();
			systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
			Map<String, String> systemParameters = null;
			try {
				systemParameters = sharedDefaultsProxy
				.findSystemParameterByCodes(systemParameterCodes);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),
						proxyException);
			}
			String accountingEnabled = (systemParameters
					.get(SYS_PARA_ACCOUNTING_ENABLED));

			if (CN51SummaryVO.INVOICE_STATUS_PERFORMAGENERATED.equals(summaryVO
					.getInvoiceStatus())) {

				cN51Summary
				.setInvoiceStatus(CN51SummaryVO.INVOICE_STATUS_FINALISED);
				cN51Summary.setLastUpdatedTime(summaryVO.getLastUpdatedTime());
				cN51Summary.setLastUpdatedUser(summaryVO.getLastUpdateduser());
				cN51Summary
				.setTotalAmountInBillingCurr(totalAmountInBillingCurrency);
				/*
				 * Added by meenu for GPA billing accounting
				 */
				if ("Y".equals(accountingEnabled)) { // if accoutning enabled
					cN51Summary.triggerGPABillingAccounting(summaryVO);
				}

			} else if (CN51SummaryVO.INVOICE_STATUS_GENERATED.equals(summaryVO
					.getInvoiceStatus())) {
				cN51Summary
				.setInvoiceStatus(CN51SummaryVO.INVOICE_STATUS_DIRECTFINALISED);
				cN51Summary.setLastUpdatedTime(summaryVO.getLastUpdatedTime());
				cN51Summary.setLastUpdatedUser(summaryVO.getLastUpdateduser());
				cN51Summary
				.setTotalAmountInBillingCurr(totalAmountInBillingCurrency);
				/*
				 * Added by meenu for GPA billing accounting
				 */
				if ("Y".equals(accountingEnabled)) { // if accoutning enabled
					cN51Summary.triggerGPABillingAccounting(summaryVO);
				}
			}

		} catch (FinderException finderException) {
			log.log(Log.FINE, "Exception in finding the table FinderException",
					finderException);
		}

	}

	/**
	 * @author a-3447
	 * @param detail
	 * @param ccaDetails
	 * @param billingDetails
	 * @param blgStatusOld
	 * @param blgStatusNew
	 */

	private void updateCCAandBlgDtls(CN66Details detail,
			Collection<CCADetail> ccaDetails,
			Collection<MRAGPABillingDetails> gpabillingDetails, String blgStatusOld,
			String blgStatusNew) {
		log.log(3, "updateCCAandBlgDtls");
		for (CCADetail cCADetail : ccaDetails) {
			if (cCADetail != null) {
				log.log(3, "inside cca updation");
				//Modified by A-7794 as part of MRA Revamp
				if (blgStatusOld.equals(cCADetail.getMcaStatus())
						//&& "G".equals(cCADetail.getGpaChangeInd())
						//&& "R".equals(cCADetail.getPayFlag())
						) {
					cCADetail.setMcaType(blgStatusNew);
					cCADetail
					.setLastUpdatedTime(cCADetail.getLastUpdatedTime());
					cCADetail
					.setLastUpdatedUser(cCADetail.getLastUpdatedUser());
				}
			}
		}
		for (MRAGPABillingDetails mRAgpaBillingDetails : gpabillingDetails) {

			if (gpabillingDetails != null) {
				log.log(3, "inside mRABillingDetails updation");
				//Modified by A-7794 as part of MRA Revamp
				if (blgStatusOld.equals(mRAgpaBillingDetails.getBillingStatus())
						//&& "G".equals(mRABillingDetails.getGpaArlBlgStatus())
						//&& "R".equals(mRABillingDetails.getPaymentFlag())
						) {
					mRAgpaBillingDetails.setBillingStatus(blgStatusNew);
				}
			}
		}

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABbillingInvoiceEnquiry(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {

		CN51SummaryVO cN51SummaryVO = null;

		GpaBillingInvoiceEnquiryFilterVO filterVO = (GpaBillingInvoiceEnquiryFilterVO) reportSpec
		.getFilterValues().iterator().next();

		cN51SummaryVO = CN51Summary
		.findGpaBillingInvoiceEnquiryDetails(filterVO);
		if (cN51SummaryVO != null) {
			if (cN51SummaryVO.getInvoiceNumber().trim().length() == 0) {
				cN51SummaryVO.setInvoiceNumber("");
			}
			if (cN51SummaryVO.getGpaCode().trim().length() == 0) {
				cN51SummaryVO.setGpaCode("");
			}
			if (cN51SummaryVO.getBillingPeriodFrom().trim().length() == 0) {
				cN51SummaryVO.setBillingPeriodFrom("");
			}
			if (cN51SummaryVO.getBillingPeriodTo().trim().length() == 0) {
				cN51SummaryVO.setBillingPeriodTo("");
			}
			if (cN51SummaryVO.getInvoiceStatus().trim().length() == 0) {
				cN51SummaryVO.setInvoiceStatus("");
			}
			if (cN51SummaryVO.getContractCurrencyCode() != null
					&& cN51SummaryVO.getContractCurrencyCode().trim().length() == 0) {
				cN51SummaryVO.setContractCurrencyCode("");
			}
		}
		log.log(Log.FINE, "cN51SummaryVO from Server:--> ", cN51SummaryVO);
		ReportMetaData parameterMetaData = new ReportMetaData();

		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "gpaName", "billingPeriodFrom", "billingPeriodTo",
				"billedDate", "invoiceStatus", "contractCurrencyCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cN51SummaryVO);
		reportSpec.addExtraInfo(cN51SummaryVO);

		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * This method prints the Proforma-Invoice Diff details
	 *
	 * @author A-3271
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> generateProformaInvoiceDiffReport(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		log.entering("GPABillingController",
		"generateProformaInvoiceDiffReport");

		CN51SummaryVO cn51SummaryVO = CN51SummaryVO.class.cast(reportSpec
				.getFilterValues().get(0));
		Collection<ProformaInvoiceDiffReportVO> proformaInvoiceDiffReportVOs = null;
		proformaInvoiceDiffReportVOs = CN51Summary
		.generateProformaInvoiceDiffReport(cn51SummaryVO);

		if (proformaInvoiceDiffReportVOs == null
				|| proformaInvoiceDiffReportVOs.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		// one time value
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(PROFORMA_INVOICEDIFF);
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		try {
			oneTimeMap = new SharedDefaultsProxy().findOneTimeValues(
					logonAttributes.getCompanyCode(), parameterTypes);
		} catch (ProxyException e) {
			log.log(Log.FINE, "ProxyException");
			throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR,
					e);
		}

		Collection<OneTimeVO> OneTimes = oneTimeMap.get(PROFORMA_INVOICEDIFF);

		if (OneTimes != null) {
			for (OneTimeVO oneTimeVo : OneTimes) {
				if (cn51SummaryVO.getFunctionPoint().equals(
						oneTimeVo.getFieldValue())) {
					cn51SummaryVO.setFunctionPoint(oneTimeVo
							.getFieldDescription());
				}
			}
		}

		reportSpec.addParameter(cn51SummaryVO);
		reportSpec.addExtraInfo(proformaInvoiceDiffReportVOs);
		reportSpec.addExtraInfo(cn51SummaryVO);
		log.exiting("GPABillingController", "generateProformaInvoiceDiffReport");
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillingInvoice(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {

		CN51SummaryFilterVO cn51SummaryFilterVO = (CN51SummaryFilterVO) reportSpec
		.getFilterValues().iterator().next();

		Collection<CN51SummaryVO> cN51SummaryVOs = CN51Summary
		.findAllInvoicesForReport(cn51SummaryFilterVO);
		cn51SummaryFilterVO.setInvoiceNumber(((ArrayList<CN51SummaryVO>) cN51SummaryVOs)
				.get(0).getInvoiceNumber());  //Added by A-8164 for ICRD-267499 starts
		log.log(Log.INFO, "data cN51SummaryVOs inside controller----->",
				cN51SummaryVOs);
		log.log(Log.INFO, "outside null loop---->");
		cn51SummaryFilterVO.setInvoiceStatus(((ArrayList<CN51SummaryVO>) cN51SummaryVOs)
				.get(0).getInvoiceStatus()); //Added by A-8164 for ICRD-267499
		Map<String,String> systemParameterMap =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			proxyException.printStackTrace();
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		for(CN51SummaryVO cN51SummaryVO:cN51SummaryVOs){
			cN51SummaryVO.setOverrideRounding(systemParameterMap.get("mailtracking.mra.overrideroundingvalue"));
		} //Added by A-8164 for ICRD-267499 ends
		if (cN51SummaryVOs == null || cN51SummaryVOs.size() <= 0) {
			log.log(Log.INFO, "inside null loop---->");
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_GPABILLING_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		log.log(Log.INFO, "filter vo!@#&----->", cn51SummaryFilterVO);
		reportSpec.addParameter(cn51SummaryFilterVO);
		reportSpec.setData(cN51SummaryVOs);
		return ReportAgent.generateReport(reportSpec);

	}
	//Added by A-8527
		public ReportSpec generateGPAinvoiceCoveringRpt(ReportSpec reportSpec,CN51CN66FilterVO cn51CN66FilterVO)
				throws SystemException{
					log.entering("GPABillingControlle", "generateGPAinvoiceRpt");
					AirlineVO airlineVO = null;
					Map<String,String> systemParameterMap =null;
					String overrideRounding =null;
					Collection<String> systemParCodes = new ArrayList<String>();
					systemParCodes.add(OVERRIDEROUNDING);
					try {
						systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
					} catch (ProxyException proxyException) {
						throw new SystemException(proxyException.getMessage());
					}
					if(systemParameterMap !=null && systemParameterMap.size()>0){
						overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
					}
					PostalAdministrationVO postalAdministrationVO = null;
					try{
						postalAdministrationVO = new MailTrackingDefaultsProxy().findPostalAdminDetails(cn51CN66FilterVO.getCompanyCode(),cn51CN66FilterVO.getGpaCode());
					}catch(ProxyException pe){
						log.log(Log.SEVERE, "Proxy exception caught in emailInvoice");
					}
					Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
					LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
					InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
					invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
					InvoiceDetailsReportVO invoiceReportVO = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
					if(invoiceReportVO!=null){
						invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
						invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
						invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
						invoiceDetailsReportVO1.setOverrideRounding(overrideRounding);
						//Added by A-8527 for ICRD-332631 starts
						invoiceDetailsReportVO1.setDuedays(invoiceReportVO.getDuedays());
						invoiceDetailsReportVO1.setPaName(invoiceReportVO.getPaName());
						invoiceDetailsReportVO1.setCountry(invoiceReportVO.getCountry());
						invoiceDetailsReportVO1.setState(invoiceReportVO.getState());
						invoiceDetailsReportVO1.setCity(invoiceReportVO.getCity());
						invoiceDetailsReportVO1.setCurrency(invoiceReportVO.getBillingCurrencyCode());
						invoiceDetailsReportVO1.setToDateString(invoiceReportVO.getBilledDate().toDisplayDateOnlyFormat());
						invoiceDetailsReportVO1.setInvoiceNumber(invoiceReportVO.getInvoiceNumber());
						invoiceDetailsReportVO1.setFreeText(invoiceReportVO.getFreeText());
						//Added by A-8527 for ICRD-332631 Ends
					}

					invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
					airlineVO = CN51Summary.findAirlineAddress(cn51CN66FilterVO.getCompanyCode(),logonAttributes.getOwnAirlineIdentifier());
					if (airlineVO != null) {
						if (airlineVO.getAirlineName() == null) {
							airlineVO.setAirlineName("");
						}
					}
					Collection<CN66DetailsVO> cn66DetailsVos= CN51Summary.generateCN66Report(cn51CN66FilterVO);
					 //added by A-8527 for ICRD-324283 starts
					for (CN66DetailsVO cn66DetailsVO:cn66DetailsVos){
						cn66DetailsVO.setSettlementCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
						cn66DetailsVO.setOverrideRounding(overrideRounding);
					}
					 //added by A-8527 for ICRD-324283 Ends
					Collection<CN51DetailsVO> cn51DetailsVos=null;
					if(cn51CN66FilterVO.getRebillInvNumber() == null){
						cn51DetailsVos= CN51Summary.generateCN51Report(cn51CN66FilterVO);
					}
					if(cn51DetailsVos!=null){
					for (CN51DetailsVO cn51DetailsVo:cn51DetailsVos){
						cn51DetailsVo.setOverrideRounding(overrideRounding);
						cn51DetailsVo.setAirlineCode(cn51DetailsVo.getCompanyCode());
					}
					}
			   		reportSpec.addExtraInfo(cn51CN66FilterVO);
			   		//reportSpec.addSubReportParameter(cn51CN66FilterVO);
					reportSpec.setData(invoiceDetailsReportVOs);
					reportSpec.setProductCode(PRODUCT_CODE);
					reportSpec.setSubProductCode(SUB_PRODUCTCODE);
			   		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
					oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
					Map<String, Collection<OneTimeVO>> oneTimeHashMap=null;
					try {
						oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
								cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
					} catch (ProxyException proxyException) {
						throw new SystemException(proxyException.getMessage());
					}
					Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
					if (oneTimeHashMap != null) {
						mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
					}
					Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
					if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
						for (CN51DetailsVO cn51VO : cn51DetailsVos) {
							for (OneTimeVO oneTimeVO : mailCategory) {
								if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
									cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
								}
							}
							cn51VO.setMonthFlag("C");
							cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
							cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
							cn51DetailsVoS.add(cn51VO);
						}
					}
			   		reportSpec.addSubReportData(cn51DetailsVoS);
			   		reportSpec.addParameter(cn51CN66FilterVO);
			   		reportSpec.addSubReportData(cn66DetailsVos);
					reportSpec.setPreview(true);
					//reportSpec.setReportId(REPORT_ID);
					reportSpec.setResourceBundle(BUNDLE);
					reportSpec.setReportTitle(REPORT_TITLE);
					reportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
					reportSpec.setShouldExport(false);
					//Added for email sending for rebill invoice by A-5526 as part of ICRD-234283 starts
					try {
						new MailTrackingMRAProxy().sendEmailRebillInvoice(cn51DetailsVoS,cn51CN66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage());
					}
					//Added for email sending for rebill invoice by A-5526 as part of ICRD-234283 ends
					log.exiting("GPABillingControlle", "generateGPAinvoiceRpt");
					return reportSpec;
				}
	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPAInvoiceCoveringrpt(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		CN51CN66FilterVO cn51CN66FilterVO=new CN51CN66FilterVO();
		if((ReminderDetailsFilterVO)
				reportSpec.getFilterValues().iterator().next() instanceof ReminderDetailsFilterVO){
			ReminderDetailsFilterVO reminderDetailsFilterVO = (ReminderDetailsFilterVO)
					reportSpec.getFilterValues().iterator().next();
			cn51CN66FilterVO.setCompanyCode(reminderDetailsFilterVO.getCompanyCode());
			cn51CN66FilterVO.setGpaCode(reminderDetailsFilterVO.getGpaCode());
			cn51CN66FilterVO.setInvoiceNumber(reminderDetailsFilterVO.getInvoiceNumber());
			cn51CN66FilterVO.setRebillInvNumber(new StringBuilder(reminderDetailsFilterVO.getInvoiceNumber())
			.append("-").append(reminderDetailsFilterVO.getGpaRebillRound()).toString());
		}else{
		CN51SummaryFilterVO cn51SummaryFilterVO = (CN51SummaryFilterVO) reportSpec
		.getFilterValues().iterator().next();
		cn51CN66FilterVO.setCompanyCode(cn51SummaryFilterVO.getCompanyCode());
		cn51CN66FilterVO.setGpaCode(cn51SummaryFilterVO.getGpaCode());
		cn51CN66FilterVO.setInvoiceNumber(cn51SummaryFilterVO.getInvoiceNumber());
		log.log(Log.INFO, "filter vo!@#&----->", cn51SummaryFilterVO);
		}
		reportSpec = generateGPAinvoiceCoveringRpt(reportSpec,cn51CN66FilterVO);
		//reportSpec.addParameter(cn51SummaryFilterVO);
		//reportSpec.setData(cN51SummaryVOs);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author A-2391
	 * @param airlineCN51DetailsVOs
	 * @return
	 */
	private Collection<CN51DetailsVO> performCN66ToCN51Grouping(
			Collection<CN66DetailsVO> gpaCN66DetailsVOs) {
		Collection<CN51DetailsVO> sortedCN51DtlVOs = new ArrayList<CN51DetailsVO>();
		String sectorKey = null;
		// Array<String> actSubcls;
		Collection<CN66DetailsVO> detailsForStorage = null;
		Map<String, Collection<CN66DetailsVO>> sectorMap = new HashMap<String, Collection<CN66DetailsVO>>();
		if (gpaCN66DetailsVOs != null && gpaCN66DetailsVOs.size() > 0) {
			for (CN66DetailsVO cn66detailVO : gpaCN66DetailsVOs) {
				sectorKey = new StringBuffer(cn66detailVO.getBillFrm())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getBillTo())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getMailCategoryCode())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getMailSubclass())//Actual Subclass not needed for grouping
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getApplicableRate()).toString();
				if (sectorMap.get(sectorKey) != null) {
					sectorMap.get(sectorKey).add(cn66detailVO);
				} else {
					detailsForStorage = new ArrayList<CN66DetailsVO>();
					detailsForStorage.add(cn66detailVO);
					sectorMap.put(sectorKey, detailsForStorage);
				}
			}
			Collection<CN66DetailsVO> storredDetailsInMap = null;
			CN51DetailsVO totalDetailsVO = null;
			int seqNum = 0;

			for (Entry<String, Collection<CN66DetailsVO>> entryInsideMap : sectorMap
					.entrySet()) {
				storredDetailsInMap = entryInsideMap.getValue();
				double totalWeight = 0.0;
				double totalSurChg = 0.0;
				double totalMailChg = 0.0;
				//Added for ICRD-150472 starts
				double grsAmt = 0.0;
				double srvTax = 0.0;
				//Added for ICRD-150472 ends
				Money totalAmt = null;
				Money vatAmt = null;
				Money grossAmt = null;
				Money netAmt = null;

				double netAmount=0.0;//added by a-7871 for ICRD-214766

				try {
					for (CN66DetailsVO storredDetail : storredDetailsInMap) {

						totalWeight = totalWeight
						+ storredDetail.getTotalWeight();
						if(storredDetail.getSurCharge()!=null&&storredDetail.getSurCharge().trim().length()>0){
							totalSurChg = totalSurChg+  Double.parseDouble(storredDetail.getSurCharge());
						}
						if(storredDetail.getMailCharge()!=null&&storredDetail.getMailCharge().trim().length()>0){
							totalMailChg = totalMailChg+ Double.parseDouble(storredDetail.getMailCharge());
						}
						//Added for ICRD-150472 starts
						grsAmt=grsAmt+storredDetail.getAmount();
						srvTax=srvTax+storredDetail.getServiceTax();
						//Added for ICRD-150472 ends

						netAmount=netAmount+storredDetail.getNetAmount().getAmount();//added by a-7871 for ICRD-214766

						totalAmt = CurrencyHelper.getMoney(storredDetail
								.getCurrencyCode());
						vatAmt = CurrencyHelper.getMoney(storredDetail
								.getCurrencyCode());
						grossAmt = CurrencyHelper.getMoney(storredDetail
								.getCurrencyCode());
						netAmt = CurrencyHelper.getMoney(storredDetail
								.getCurrencyCode());
					}

					vatAmt.setAmount(0.0D);
					totalAmt.setAmount(0.0D);
					grossAmt.setAmount(0.0D);
					netAmt.setAmount(0.0D);

					for (CN66DetailsVO storredDetail : storredDetailsInMap) {

						totalAmt.plusEquals(storredDetail.getAmount());
						vatAmt.plusEquals(storredDetail.getVatAmount());
						grossAmt.plusEquals(storredDetail.getAmount());
						netAmt.plusEquals(storredDetail.getNetAmount());
					}
				} catch (CurrencyException currencyException) {
					log.log(Log.INFO, "CurrencyException found");
				}

				// constrcuting a vo for the storing the total details
				StringTokenizer tokenizer = new StringTokenizer(
						entryInsideMap.getKey(), STRING_VALUE_HYPHEN, false);
				String[] keyArray = new String[tokenizer.countTokens() + 1];
				int index = 0;
				while (tokenizer.hasMoreTokens()) {
					keyArray[index] = tokenizer.nextToken();
					index++;
				}
				index = 0;
				totalDetailsVO = new CN51DetailsVO();
				totalDetailsVO.setOrigin(keyArray[index]);
				totalDetailsVO.setDestination(keyArray[++index]);
				totalDetailsVO.setMailCategoryCode(keyArray[++index]);
				totalDetailsVO.setMailSubclass(keyArray[++index]);
				totalDetailsVO.setApplicableRate(Double
						.parseDouble(keyArray[++index]));

				totalDetailsVO
				.setCompanyCode(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getCompanyCode());
				totalDetailsVO
				.setGpaCode(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getGpaCode());
				totalDetailsVO
				.setInvoiceNumber(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getInvoiceNumber());
				totalDetailsVO
				.setBillingCurrencyCode(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getCurrencyCode());
				totalDetailsVO.setMailCharge(String.valueOf(totalMailChg));
				totalDetailsVO.setSurCharge(String.valueOf(totalSurChg));
				//Added for ICRD-150472 starts
				totalDetailsVO.setGrossAmount(grsAmt);
				totalDetailsVO.setServiceTax(srvTax);
				//Added for ICRD-150472 ends
				totalDetailsVO.setSequenceNumber(++seqNum);
				// totalDetailsVO.setClearanceperiod(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getClearancePeriod());
				totalDetailsVO.setTotalWeight(totalWeight);
				totalDetailsVO.setTotalBilledAmount(totalAmt);
				totalDetailsVO
				.setTotalAmtinLC(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getTotalAmtinLC());
				totalDetailsVO
				.setTotalAmtinCP(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getTotalAmtinCP());
				totalDetailsVO.setVatAmount(vatAmt);
				totalDetailsVO.setTotalAmount(netAmt);
				totalDetailsVO.setTotalNetAmount(netAmount);
				sortedCN51DtlVOs.add(totalDetailsVO);
			}
		}
		return sortedCN51DtlVOs;
	}

	/**
	 * @author A-2391
	 * @param airlineCN51DetailsVOs
	 * @return
	 */
	private Collection<CN51DetailsVO> cN66ToCN51GroupingForReport(
			Collection<CN66DetailsVO> gpaCN66DetailsVOs) {
		Collection<CN51DetailsVO> sortedCN51DtlVOs = new ArrayList<CN51DetailsVO>();
		String sectorKey = null;
		// Array<String> actSubcls;
		Collection<CN66DetailsVO> detailsForStorage = null;
		Map<String, Collection<CN66DetailsVO>> sectorMap = new HashMap<String, Collection<CN66DetailsVO>>();
		if (gpaCN66DetailsVOs != null && gpaCN66DetailsVOs.size() > 0) {
			for (CN66DetailsVO cn66detailVO : gpaCN66DetailsVOs) {
				sectorKey = new StringBuffer(cn66detailVO.getBillFrm())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getBillTo())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getActualSubCls()).toString();
				if (sectorMap.get(sectorKey) != null) {
					sectorMap.get(sectorKey).add(cn66detailVO);
				} else {
					detailsForStorage = new ArrayList<CN66DetailsVO>();
					detailsForStorage.add(cn66detailVO);
					sectorMap.put(sectorKey, detailsForStorage);
				}
			}
			Collection<CN66DetailsVO> storredDetailsInMap = null;
			CN51DetailsVO totalDetailsVO = null;
			int seqNum = 0;

			for (Entry<String, Collection<CN66DetailsVO>> entryInsideMap : sectorMap
					.entrySet()) {
				storredDetailsInMap = entryInsideMap.getValue();
				double totalWeight = 0.0;
				Money totalAmt = null;
				double totalamount= 0.0;//added by a-7871
				try {
					for (CN66DetailsVO storredDetail : storredDetailsInMap) {

						totalWeight = totalWeight
						+ storredDetail.getTotalWeight();
						totalAmt = CurrencyHelper.getMoney(storredDetail
								.getCurrencyCode());
						totalamount=totalamount+storredDetail.getAmount();//added by a-7871 for ICRD-214766
					}

					totalAmt.setAmount(0.0D);
					for (CN66DetailsVO storredDetail : storredDetailsInMap) {

						totalAmt.plusEquals(storredDetail.getAmount());

					}
				} catch (CurrencyException currencyException) {
					log.log(Log.INFO, "CurrencyException found");
				}
				// constrcuting a vo for the storing the total details
				StringTokenizer tokenizer = new StringTokenizer(
						entryInsideMap.getKey(), STRING_VALUE_HYPHEN, false);
				String[] keyArray = new String[tokenizer.countTokens() + 1];
				int index = 0;
				while (tokenizer.hasMoreTokens()) {
					keyArray[index] = tokenizer.nextToken();
					index++;
				}
				index = 0;
				totalDetailsVO = new CN51DetailsVO();
				totalDetailsVO.setOrigin(keyArray[index]);
				totalDetailsVO.setDestination(keyArray[++index]);
				totalDetailsVO.setActualSubCls(keyArray[++index]);

				totalDetailsVO
				.setCompanyCode(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getCompanyCode());
				totalDetailsVO
				.setGpaCode(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getGpaCode());
				totalDetailsVO
				.setInvoiceNumber(((ArrayList<CN66DetailsVO>) storredDetailsInMap)
						.get(0).getInvoiceNumber());
				totalDetailsVO.setSequenceNumber(++seqNum);
				// totalDetailsVO.setClearanceperiod(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getClearancePeriod());
				totalDetailsVO.setTotalWeight(totalWeight);
				totalDetailsVO.setTotalBilledAmount(totalAmt);
				totalDetailsVO.setTotAmt(totalamount);//added by a-7871 for ICRD-214766
				sortedCN51DtlVOs.add(totalDetailsVO);
			}
		}
		return sortedCN51DtlVOs;
	}

	/**
	 * @author A-3429
	 * @param companyCode
	 * @param subclass
	 * @throws SystemException
	 */
	public Page<PostalAdministrationVO> findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultSize)
			throws SystemException {

		log.entering("MRA Defaults controller", "findPACode");
		Page<PostalAdministrationVO> postalAdministrationVOs = null;
		MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
		try {
			postalAdministrationVOs = mailTrackingDefaultsProxy.findPALov(
					companyCode, paCode, paName, pageNumber, defaultSize);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ProxyException Occured!!!!");
		}
		log.exiting("MRA Defaults controller", "findPACode");
		return postalAdministrationVOs;

	}

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO) throws SystemException {

		log.entering(CLASS_NAME, "findUnSettledInvoicesForGPA");
		return GPABillingSettlement
		.findUnSettledInvoicesForGPA(gpaSettlementVO);
	}


	/**
	 * @author a-4823
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printSettlementDetails(ReportSpec reportSpec) throws SystemException, MailTrackingMRABusinessException{
		InvoiceSettlementFilterVO filterVO=(InvoiceSettlementFilterVO)reportSpec.
		getFilterValues().iterator().next();
		Collection<GPASettlementVO> gpaSettlementVOs=generateSettlementDetails(filterVO);
		if(gpaSettlementVOs == null || gpaSettlementVOs.size()<=0){
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new
			MailTrackingMRABusinessException();
			ErrorVO reporterror = new
			ErrorVO(MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		reportSpec.addParameter(filterVO);
		//reportSpec.setData(gpaSettlementVOs);
		return ReportAgent.generateReport(reportSpec);

	}
	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<GPASettlementVO> generateSettlementDetails(
			InvoiceSettlementFilterVO filterVO) throws SystemException {

		return GPABillingSettlement.generateSettlementDetails(filterVO);
	}
	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 */
	public Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO) throws SystemException {
		// TODO Auto-generated method stub
		return GPABillingSettlement.findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementVO);
	}
	/**
	 * @author a-4823
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> findPOMailSummaryDetails(
			ReportSpec reportSpec) throws SystemException {
		BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO= BillingSummaryDetailsFilterVO.class
		.cast(reportSpec.getFilterValues().get(0));
		Collection<POMailSummaryDetailsVO> poMailSummaryDetailsVOs=null;
		poMailSummaryDetailsVOs=GPABillingSettlement.findPOMailSummaryDetails(billingSummaryDetailsFilterVO);
		reportSpec.addParameter(billingSummaryDetailsFilterVO);
		reportSpec.setData(poMailSummaryDetailsVOs);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * 	Method		:	GPABillingController.generateInvoiceTK
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160 generateInvoice specific for TK
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{
		log.entering("GPABillingControlle", "generateInvoiceTK");
		//LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
/*		TransactionProvider tm = PersistenceController.getTransactionProvider();
		Transaction tx = tm.getNewTransaction(true);
		boolean success = false;*/
		try{

			/*Collection<PostalAdministrationVO> postalAdministrationVOs = findAllPACodes(generateInvoiceFilterVO);

			sendEmailsForPA(postalAdministrationVOs,generateInvoiceFilterVO);*/
			LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
			GenerateInvoiceJobScheduleVO generateInvoiceJobScheduleVO = new GenerateInvoiceJobScheduleVO();
			
			generateInvoiceJobScheduleVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
			generateInvoiceJobScheduleVO.setJobName("MAL_GENERATEINVOICE_JOB");
			LocalDate currentTime = new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true);		
			generateInvoiceJobScheduleVO.setStartTime(currentTime);
			currentTime = new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true);
			currentTime = currentTime.addMinutes(30);
			generateInvoiceJobScheduleVO.setEndTime(currentTime);
			//generateInvoiceJobScheduleVO.setValue(1,generateInvoiceFilterVO.getCompanyCode());
			//generateInvoiceJobScheduleVO.setValue(2,"MAL_GENERATEINVOICE_JOB");
			generateInvoiceJobScheduleVO.setValue(1,generateInvoiceFilterVO.getGpaCode());
			generateInvoiceJobScheduleVO.setValue(2,generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayDateOnlyFormat());
			generateInvoiceJobScheduleVO.setValue(3,generateInvoiceFilterVO.getBillingPeriodTo().toDisplayDateOnlyFormat());
			generateInvoiceJobScheduleVO.setValue(4,generateInvoiceFilterVO.getBillingFrequency());
			generateInvoiceJobScheduleVO.setValue(5,generateInvoiceFilterVO.getInvoiceType());
			generateInvoiceJobScheduleVO.setValue(6,generateInvoiceFilterVO.isAddNew() ? "Y":"N");
			generateInvoiceJobScheduleVO.setValue(7, String.valueOf(generateInvoiceFilterVO.getInvoiceLogSerialNumber()));
			generateInvoiceJobScheduleVO.setValue(8, generateInvoiceFilterVO.getTransactionCode());
			generateInvoiceJobScheduleVO.setValue(9,generateInvoiceFilterVO.getCountryCode());
			
			try {
				SchedulerAgent.getInstance().createScheduleForJob(generateInvoiceJobScheduleVO);
			} catch (JobSchedulerException | SystemException e) {
				log.log(Log.SEVERE, "JobSchedulerException Caught job not found !");
				throw new SystemException(e.getMessage());
			}
			
			
			
			
			
			//sendEmailforPAs(postalAdministrationVOs,generateInvoiceFilterVO);
		}catch(SystemException se){
			log.log(Log.SEVERE, "Exception",se);
			//success = false;
		}//finally{
/*			if(success){
				tx.commit();
			}else{
				tx.rollback();
			}*/
			/*Collection<LockVO> lockvos=new ArrayList<LockVO>();
			TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
			generateInvoiceLockVO.setAction(GENINV);
			generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
			generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
			generateInvoiceLockVO.setDescription(LOCkDESC);
			generateInvoiceLockVO.setRemarks(LOCKREMARK);
			generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
			generateInvoiceLockVO.setScreenId(LOCKSCREENID);
			lockvos.add(generateInvoiceLockVO);
			releaseLocks(lockvos);
			isSuccessFlag="N";
			log.exiting("GPABillingControlle", "generateInvoiceTK");
		}*/
	}
	/**
	 * 	Method		:	GPABillingController.findAllPACodes
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160 finding All PAs
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<PostalAdministrationVO>
	 */
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException{
		log.entering("GPABillingControlle", "findAllPACodes");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			postalAdministrationVOs = new MailTrackingDefaultsProxy().findAllPACodes(generateInvoiceFilterVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ExceptionCaught",e);
			throw new SystemException(e.getErrors());
		}
		log.exiting("GPABillingControlle", "findAllPACodes");
		return postalAdministrationVOs;
	}
	/**
	 * 	Method		:	GPABillingController.sendEmail
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:	ICRD-42160 sendEmail to address configured
	 *	Parameters	:	@param cN51CN66VO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void sendEmail(Collection<CN66DetailsVO> cn66DetailsVos,Collection<CN51DetailsVO> cn51DetailsVos,InvoiceDetailsReportVO invoiceDetailsReportVO,CN51CN66FilterVO cn51CN66FilterVO,String email)
	throws SystemException{
		log.entering("GPABillingControlle", "sendEmail");

		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
		invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
		if(invoiceReportVO!=null){
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO1.setCurrency(invoiceReportVO.getBillingCurrencyCode());
			invoiceDetailsReportVO1.setPaName(invoiceReportVO.getPaName());
			invoiceDetailsReportVO1.setPhone1(invoiceReportVO.getPhone1());
			invoiceDetailsReportVO1.setPhone2(invoiceReportVO.getPhone2());
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setCity(invoiceReportVO.getCity());
			invoiceDetailsReportVO1.setState(invoiceReportVO.getState());
			invoiceDetailsReportVO1.setCountry(invoiceReportVO.getCountry());
			invoiceDetailsReportVO1.setFax(invoiceReportVO.getFax());
			invoiceDetailsReportVO1.setInvoiceNumber(invoiceReportVO.getInvoiceNumber());
			invoiceDetailsReportVO1.setFreeText(invoiceReportVO.getFreeText());
			invoiceDetailsReportVO1.setBillingCurrencyCode(invoiceReportVO.getBillingCurrencyCode());
		}
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);

   		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeHashMap=null;
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(cn51CN66FilterVO.getCompanyCode(),
					oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}

		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}
		// Added for CRQ ICRD-235779 by A-5526 starts
		String fromDate = "";
		String toDate = "";
		if (invoiceDetailsReportVO.getFromBillingPeriod() != null) {
			fromDate = TimeConvertor.toStringFormat(invoiceDetailsReportVO.getFromBillingPeriod().toCalendar(),
					"dd MMM yyyy");
		}
		if (invoiceDetailsReportVO.getToBillingPeriod() != null) {
			toDate = TimeConvertor.toStringFormat(invoiceDetailsReportVO.getToBillingPeriod().toCalendar(),
					"dd MMM yyyy");
		}
		if(fromDate!=null){
			fromDate=fromDate.substring(0, 2);
		}
		invoiceDetailsReportVO.setFromDateString(fromDate);
		invoiceDetailsReportVO.setToDateString(toDate);
		invoiceDetailsReportVO.setEmail(email);
		// Added for CRQ ICRD-235779 by A-5526 ends


		GPABillingController gpaBillingController = (GPABillingController) SpringAdapter.getInstance()
				.getBean("mRAGpaBillingcontroller");
		gpaBillingController.sendInvoiceEmail(cn51CN66FilterVO, invoiceDetailsReportVO, invoiceDetailsReportVOs,
				cn51DetailsVoS, cn66DetailsVos);


		log.exiting("GPABillingControlle", "sendEmail");
	}
	/**
	 * Method : GPABillingController.sendInvoiceEmail Added by : A-8527
	 * on 12-Feb-2019 Used for : ICRD-336722 sendInvoiceEmail Parameters
	 * : @param
	 * cn51CN66FilterVO,invoiceDetailsReportVO,invoiceDetailsReportVOs,cn51DetailsVoS,cn66DetailsVos,emailVO
	 * Parameters : @throws SystemException Return type : void
	 */
	@Raise(module = "mail", submodule = "mra", event = "MRA_INVOICEGENERATE_EVENT", methodId = "mail.mra.sendInvoiceEmail")
	public void sendInvoiceEmail(CN51CN66FilterVO cn51CN66FilterVO, InvoiceDetailsReportVO invoiceDetailsReportVO,
			Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs, Collection<CN51DetailsVO> cn51DetailsVoS,
			Collection<CN66DetailsVO> cn66DetailsVos) throws SystemException {
		log.entering("GPABillingController", "sendInvoiceEmail");
	}
	/**
	 * 	Method		:	GPABillingController.populateEmailVO
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:	ICRD-42160 to populateEmailVO
	 *	Parameters	:	@return
	 *	Return type	: 	EmailVO
	 *  Parameters  :   @throws SystemException
	 */
	private EmailVO populateEmailVO(String companyCode,String attachmentName, String attachmentType, String printerName, String messageType)
	throws SystemException {
		EmailVO emailVO = new EmailVO();
	try {
		emailVO.setAttachementName(attachmentName);
		emailVO.setAttachementType(attachmentType);
		emailVO.setEmailBody("");
		emailVO.setSubject(printerName);
		 ArrayList<MessageAddressDetailVO> addressDetails=null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			MessageAddressDetailVO addressDetailVO= new MessageAddressDetailVO();
			MessageAddressVO addressVO=new MessageAddressVO();
			ArrayList<MessageAddressVO> addresses=null;
			systemParameters.add(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
		 String emailInterface = null;
			HashMap<String, String> systemParameterMap = null;
			try{
				systemParameterMap = (HashMap<String, String>)new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
			}catch(ProxyException proxyException){
				throw new SystemException(proxyException.getMessage());
			}
			if (systemParameterMap != null){
				emailInterface = systemParameterMap.get(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
			}
			if(emailInterface==null){
				emailInterface=MSGBROKER_EMAIL_INTERFACE;
			}
			MessageAddressFilterVO filterVO=new MessageAddressFilterVO();
			filterVO.setCompanyCode(companyCode);
			//filterVO.setInterfaceSystem(emailInterface);
			//filterVO.setAirportCode(companyCode);
			filterVO.setMessageType(messageType);
			//Added as part of BUG ICRD-108425 by A-5526 starts
			filterVO.setProfileStatus(MessageAddressVO.STATUS_ACTIVE);
			//Added as part of BUG ICRD-108425 by A-5526 ends
			addresses=(ArrayList<MessageAddressVO>)	new MessageBrokerConfigProxy().findMessageAddressDetails(filterVO);
			log.log(Log.FINE, "Address obtained",addresses);
			if(addresses!=null){
				for(int j=0;j<addresses.size();j++){
					addressVO=addresses.get(j);
					addressDetails=(ArrayList<MessageAddressDetailVO>)addressVO.getMessageAddressDetails();
					for(int i=0;i<addressDetails.size();i++){
						addressDetailVO=addressDetails.get(i);
						MessageBrokerConfigProxy messageBrokerConfigProxy = new MessageBrokerConfigProxy();
						Collection<MessageModeParameterVO> addDtls = new ArrayList<MessageModeParameterVO>();
						addDtls = messageBrokerConfigProxy.getSplitedAddress(companyCode, CommunicationVO.MODE_EMAIL, addressDetailVO.getModeAddress());
							for(MessageModeParameterVO messageModeParameterVO : addDtls){
							if(MessageModeParameterVO.EMAIL_FROM_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
								emailVO.setFromAddress(messageModeParameterVO.getParameterValue());
							}
							if(MessageModeParameterVO.EMAIL_CC_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
								emailVO.setCcEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if(MessageModeParameterVO.EMAIL_BCC_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
								emailVO.setBccEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if(MessageModeParameterVO.EMAILADDRESS.equals(messageModeParameterVO.getParameterCode())){
								emailVO.setToEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if(emailVO.getSubject() == null && MessageModeParameterVO.EMAIL_SUBJECT.equals(messageModeParameterVO.getParameterCode())){
								emailVO.setSubject(messageModeParameterVO.getParameterValue());
							}
						}
					}
				}
				log.log(Log.FINE, "Email VO constructed",emailVO);
			}
		} catch(ProxyException pe){
			log.log(Log.SEVERE, "ProxyException",pe);
		}
		return emailVO;
	}
	/**
	 * 	Method		:	GPABillingController.addLocks
	 *	Added by 	:	A-4809 on 10-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param addLocks
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ObjectAlreadyLockedException
	 *	Return type	: 	Collection<LockVO>
	 */
	public Collection<LockVO> addLocks(String companyCode)
	throws SystemException,ObjectAlreadyLockedException{
		Collection<LockVO> lockvos=new ArrayList<LockVO>();
		TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
   		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		generateInvoiceLockVO.setAction(GENINV);
		generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
		generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceLockVO.setDescription(LOCkDESC);
		generateInvoiceLockVO.setRemarks(LOCKREMARK);
		generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
		generateInvoiceLockVO.setScreenId(LOCKSCREENID);
		lockvos.add(generateInvoiceLockVO);
		return addFrameworkLocks(lockvos);

				 
	}
	/**
	 *
	 * 	Method		:	GPABillingController.emailInvoice
	 *	Added by 	:	A-4809 on 13-Feb-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param cN51CN66VO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void sendEmailInvoice(CN51CN66VO cN51CN66VO)
	throws SystemException{
		log.entering("GPABillingController", "sendEmailInvoice");
		Collection<CN66DetailsVO> cn66DetailsVos = cN51CN66VO.getCn66DetailsVOsColln();
		Collection<CN51DetailsVO> cn51DetailsVos = cN51CN66VO.getCn51DetailsVOsColln();
		CN51CN66FilterVO cN51CN66FilterVO = new CN51CN66FilterVO();
		String cmpCode = cN51CN66VO.getCompanyCode();
		String gpaCode = cN51CN66VO.getGpaCode();
		cN51CN66FilterVO.setCompanyCode(cmpCode);
		cN51CN66FilterVO.setInvoiceNumber(cN51CN66VO.getInvoiceNumber());
		String billingCurrencyCode = cn51DetailsVos.iterator().next().getBillingCurrencyCode();
		cN51CN66FilterVO.setGpaCode(gpaCode);
		AirlineVO airlineVO=null;
		LogonAttributes logon = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		airlineVO = CN51Summary.findAirlineAddress(
				 cN51CN66VO.getCompanyCode(),
                 logon.getOwnAirlineIdentifier());
		if(airlineVO!=null && airlineVO.getAirlineName()!=null){
		cN51CN66FilterVO.setAirlineName(airlineVO.getAirlineName());
		}
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if(cn51DetailsVos!=null && cn51DetailsVos.size()>0){
			CN51DetailsVO cN51DetailsVO = cn51DetailsVos.iterator().next();
			String[] period = cN51DetailsVO.getBillingPeriod().split("to");
			java.text.DateFormat formatter =  new java.text.SimpleDateFormat("dd-MM-yy");
			java.util.Date frombillingdate =null;
			java.util.Date tobillingdate =null;
			cN51CN66FilterVO.setBillingPeriod(cN51DetailsVO.getBillingPeriod());
			try {
				frombillingdate = formatter.parse(period[0]);
				tobillingdate=formatter.parse(period[1]);
			} catch (ParseException e) {
				log.log(Log.SEVERE, "Date parse Exception");
			}
			if(frombillingdate!=null){
				fromDate.setTime(frombillingdate);
			}
			if(tobillingdate!=null){
				toDate.setTime(tobillingdate);
			}
		}
		PostalAdministrationVO postalAdministrationVO = null;
		String email =null;
		StringBuilder emailIds = new StringBuilder();
		try{
			postalAdministrationVO = new MailTrackingDefaultsProxy().findPostalAdminDetails(cmpCode,gpaCode);
		}catch(ProxyException pe){
			log.log(Log.SEVERE, "Proxy exception caught in emailInvoice");
		}
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		if(postalAdministrationVO!=null){
		invoiceDetailsReportVO.setPaName(postalAdministrationVO.getPaName());
		invoiceDetailsReportVO.setAddress(postalAdministrationVO.getAddress());
		invoiceDetailsReportVO.setCity(postalAdministrationVO.getCity());
		invoiceDetailsReportVO.setState(postalAdministrationVO.getState());
		invoiceDetailsReportVO.setCountry(postalAdministrationVO.getCountry());
		invoiceDetailsReportVO.setPhone1(postalAdministrationVO.getPhone1());
		invoiceDetailsReportVO.setPhone2(postalAdministrationVO.getPhone2());
		invoiceDetailsReportVO.setFax(postalAdministrationVO.getFax());
		invoiceDetailsReportVO.setFromBillingPeriod(fromDate);
		invoiceDetailsReportVO.setToBillingPeriod(toDate);
		invoiceDetailsReportVO.setBillingCurrencyCode(billingCurrencyCode);
		invoiceDetailsReportVO.setCurrency(billingCurrencyCode);
		if (postalAdministrationVO.getEmail() != null) {
			emailIds.append(postalAdministrationVO.getEmail());
			if (postalAdministrationVO.getSecondaryEmail1() != null
					&& !postalAdministrationVO.getSecondaryEmail1().isEmpty()) {
				emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail1());
			}
			if (postalAdministrationVO.getSecondaryEmail2() != null
					&& !postalAdministrationVO.getSecondaryEmail2().isEmpty()) {
				emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail2());
			}
		}
		 email = emailIds.toString();
		}
		//Added as part of ICRD-234283 by A-5526- rebill invoice flow
		if (cN51CN66VO.isRebillInvoice()) {
			cN51CN66FilterVO.setRebillInvNumber(cN51CN66VO.getInvoiceNumber());
			sendRebillInvoiceEmail(cN51CN66FilterVO, email);
		}else if(email!=null && email.trim().length()>0){
		sendEmail(cn66DetailsVos,cn51DetailsVos,invoiceDetailsReportVO,cN51CN66FilterVO,email);
		}else{
			throw new SystemException("mailtracking.mra.emailnotconfigured");
		}
		log.exiting("GPABillingController", "sendEmailInvoice");
	}
	/**
	 * 	Method		:	GPABillingController.releaseLocks
	 *	Added by 	:	A-4809 on 10-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param lockVOs
	 *	Parameters	:	@throws ObjectNotLockedException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	   private void  releaseLocks(Collection<LockVO> lockVOs)
	   throws ObjectNotLockedException,SystemException{
		   try {
			   Proxy.getInstance().get(FrameworkLockProxy.class).releaseLocks(lockVOs) ;
		   }  catch (ProxyException ex) {
			   throw new SystemException(ex.getMessage(),ex);
		   }
		   catch (SystemException ex) {
			   log.log(Log.SEVERE, "System Exception");
			   boolean isFound = false;
			   if (ex.getErrors() != null && ex.getErrors().size() > 0) {
				   for (ErrorVO errvo : ex.getErrors()) {
					   if(ObjectNotLockedException.OBJECT_NOT_LOCKED
							   .equals(errvo.getErrorCode())){
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
	/**
	 * @author a-5219
	 * @param reportSpec
	 * @return Mao
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN51ReportTK(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		Collection<CN51DetailsVO> cn51DetailsVos =null;
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		cn51DetailsVos = CN51Summary.generateCN51Report(cn51CN66FilterVO);
	String overrideRounding =null;
	Map<String,String> systemParameterMap =null;
	Collection<String> systemParCodes = new ArrayList<String>();
	systemParCodes.add(OVERRIDEROUNDING);
	Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
	Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
	oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
	try {
		oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
				cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
	} catch (ProxyException proxyException) {
		throw new SystemException(proxyException.getMessage());
	}
	Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
	if (oneTimeHashMap != null) {
		billingStatus = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		log.log(Log.INFO, "billingStatus1234++", billingStatus);
	}
	if(systemParameterMap !=null && systemParameterMap.size()>0){
		overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
	}
	LogonAttributes logon =ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	Collection<CN51DetailsVO> updatedCN51DetailsVO = new ArrayList<CN51DetailsVO>();
	if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
		for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : billingStatus) {
				if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
					cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
				}
			}
			cn51VO.setMonthFlag("C");
			cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
			cn51VO.setTotalNetAmount(cn51VO.getTotalAmount().getAmount());//Added for ICRD-107524
			cn51VO.setAirlineCode(logon.getOwnAirlineCode());
			updatedCN51DetailsVO.add(cn51VO);
		}
	}
	ReportMetaData parameterMetaDatas = new ReportMetaData();
	parameterMetaDatas.setFieldNames(new String[] { "airlineCode"});
	reportSpec.addParameterMetaData(parameterMetaDatas);
	reportSpec.addParameter(cn51CN66FilterVO);
	ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
	reportMetaDataForCN51.setColumnNames(new String[] {
			SECTOR,MALCTGCOD,TOTWGT,APLRAT,VATAMT,VALCHG,NETAMT,MALSUBCLS,
			"MONTHFLAG","TOTAMTLC",TOTAMTCP,INVNUM,"POACOD","BILLFRM","NETTOTAL"
	});
	if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
	reportMetaDataForCN51.setFieldNames(new String[] {
			SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,"serviceTax","valCharges",
			"totalNetAmount","mailSubclass","monthFlag","totalAmountLC","totalAmountCP","invoiceNumber","gpaCode",
			"airlineCode","totalBilledAmount"
	});//Modified for ICRD-107524
	}else{
		reportMetaDataForCN51.setFieldNames(new String[] {
				SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,"serviceTax","valCharges",
				"totalNetAmount","mailSubclass","monthFlag","totalAmountLC","totalAmountCP","invoiceNumber","gpaCode",
				"airlineCode","scalarTotalBilledAmount"
		});
	}
	reportSpec.setReportMetaData(reportMetaDataForCN51);
	reportSpec.setData(updatedCN51DetailsVO);
	return ReportAgent.generateReport(reportSpec);
	}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceReportTK(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		log.log(Log.INFO, " inside generateInvoiceReport");
		LogonAttributes logon = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		AirlineVO airlineVO = null;
		InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
          CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
          .getFilterValues().iterator().next();
          InvoiceDetailsReportVO invoiceDetailsReportVO = CN51Summary
          .generateInvoiceReportTK(cn51CN66FilterVO);
		if (invoiceDetailsReportVO.getInvoiceNumber() == null) {
                invoiceDetailsReportVO1 = CN51Summary
                .generateInvoiceReportTK(cn51CN66FilterVO);
			if(invoiceDetailsReportVO1==null){
				MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
				ErrorVO reporterror = new ErrorVO(
						MailTrackingMRABusinessException.NO_DATA_FOUND);
				mailTrackingMRABusinessException.addError(reporterror);
				throw mailTrackingMRABusinessException;
			}
		}
          Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		if(invoiceDetailsReportVO.getInvoiceNumber()==null)
			{
			invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
			}
		else
			{
			invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
			}
		log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
		log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());
          airlineVO = CN51Summary.findAirlineAddress(
                      cn51CN66FilterVO.getCompanyCode(),
                      logon.getOwnAirlineIdentifier());
		if (airlineVO != null) {
			ReportMetaData parameterMetaDatas = new ReportMetaData();
			if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
			if (airlineVO.getBillingAddress() == null) {
				airlineVO.setBillingAddress("");
			}
			parameterMetaDatas.setFieldNames(new String[] { "airlineName",
					"billingAddress", "billingPhone1", "billingPhone2",
			"billingFax" });
			reportSpec.addParameterMetaData(parameterMetaDatas);
			reportSpec.addParameter(airlineVO);
		}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] {"BLGSITCOD","BLGSITNAM","ARLADR","CORADR",SGNONE,DSGONE,SGNTWO,DSGTWO,
				 CURCOD,BNKNAM,BNKBRC,ACCNUM,CTYNAM,CNTNAM,SWTCOD,IBNNUM,
				 "CURCOD1","BNKNAM1","BNKBRC1","ACCNUM1","CTYNAM1","CNTNAM1","SWTCOD1","IBNNUM1",
				 TOTAMTBLGCUR, BLGCURCOD, POANAM, POAADR, CITY, STATE, COUNTRY, FAX, VATNUM, BLDPRD, MALCTGCOD,"CCOMID",INVDAT,"LSTUPDUSR" });
		
		reportMetaData.setFieldNames(new String[] {"billingSiteCode","billingSite","airlineAddress","correspondenceAddress",
				SIGNATORONE,DESIGNATORONE,SIGNATORTWO,
				DESIGNATORTWO,CURRENCY,BANKNAME,BRANCH,ACCNO,BANKCITY,BANKCOUNTRY,SWIFTCODE,IBANNO,
				"currencyOne","bankNameOne","branchOne","accNoOne","bankCityOne","bankCountryOne","swiftCodeOne","ibanNoOne",
				"totalAmountinBillingCurrency",BILLINGCURRENCYCODE,PANAME,ADDRESS,CITY1,STATE1,
				 COUNTRY1,FAX1,"vatNumber",BILLEDDATESTRING,MAILCATEGORYCODE,"clearComId","toDateString","lastUpdatedUser"});
		

		
		
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(invoiceDetailsReportVOs);
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateGPAInvoiceReportTK(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		log.log(Log.INFO, " inside generateInvoiceReport");
		LogonAttributes logon = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		AirlineVO airlineVO = null;
		InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		InvoiceDetailsReportVO invoiceDetailsReportVO = CN51Summary
		.generateInvoiceReportTK(cn51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary
		.generateInvoiceReport(cn51CN66FilterVO);
		if(invoiceReportVO!=null){
			invoiceDetailsReportVO.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO.setPaName(invoiceReportVO.getPaName());
			invoiceDetailsReportVO.setPhone1(invoiceReportVO.getPhone1());
			invoiceDetailsReportVO.setPhone2(invoiceReportVO.getPhone2());
			invoiceDetailsReportVO.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO.setCity(invoiceReportVO.getCity());
			invoiceDetailsReportVO.setState(invoiceReportVO.getState());
			invoiceDetailsReportVO.setCountry(invoiceReportVO.getCountry());
			invoiceDetailsReportVO.setFax(invoiceReportVO.getFax());
		}
		if (invoiceDetailsReportVO.getInvoiceNumber() == null) {
			invoiceDetailsReportVO1 = CN51Summary
			.generateInvoiceReportTK(cn51CN66FilterVO);
			if(invoiceDetailsReportVO1==null){
				MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
				ErrorVO reporterror = new ErrorVO(
						MailTrackingMRABusinessException.NO_DATA_FOUND);
				mailTrackingMRABusinessException.addError(reporterror);
				throw mailTrackingMRABusinessException;
			}
		}
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		if(invoiceDetailsReportVO.getInvoiceNumber()==null)
			{
			invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
			}
		else
			{
			invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
			}
		log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
		log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());
		airlineVO = CN51Summary.findAirlineAddress(
				cn51CN66FilterVO.getCompanyCode(),
				logon.getOwnAirlineIdentifier());
		if (airlineVO != null) {
			ReportMetaData parameterMetaDatas = new ReportMetaData();
			if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
			if (airlineVO.getBillingAddress() == null) {
				airlineVO.setBillingAddress("");
			}
			parameterMetaDatas.setFieldNames(new String[] { "airlineName",
					"billingAddress", "billingPhone1", "billingPhone2",
			"billingFax" });
			reportSpec.addParameterMetaData(parameterMetaDatas);
			reportSpec.addParameter(airlineVO);
		}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] {"BLGSITCOD","BLGSITNAM","ARLADR","CORADR","SGNONE","DSGONE","SGNTWO","DSGTWO",
				 "CURCOD","BNKNAM","BNKBRC","ACCNUM","CTYNAM","CNTNAM","SWTCOD","IBNNUM",
				 "CURCOD1","BNKNAM1","BNKBRC1","ACCNUM1","CTYNAM1","CNTNAM1","SWTCOD1","IBNNUM1",
				 "TOTAMTBLGCUR", "BLGCURCOD", "POANAM", "POAADR", "CITY", "STATE", "COUNTRY", "FAX", "VATNUM", "BLDPRD", MALCTGCOD,"CCOMID","INVDAT" });
		reportMetaData.setFieldNames(new String[] {"billingSiteCode","billingSite","airlineAddress","correspondenceAddress",
				"signatorOne","designatorOne","signatorTwo","designatorTwo",
				"currency","bankName","branch","accNo","bankCity","bankCountry","swiftCode","ibanNo",
				"currencyOne","bankNameOne","branchOne","accNoOne","bankCityOne","bankCountryOne","swiftCodeOne","ibanNoOne",
				"totalAmountinBillingCurrency","billingCurrencyCode","paName","address","city","state",
				 "country","fax","vatNumber","billedDateString",MAILCATEGORYCODE,"clearComId","toDateString"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(invoiceDetailsReportVOs);
		Collection<CN51DetailsVO> cn51DetailsVos = CN51Summary.generateCN51Report(cn51CN66FilterVO);
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary.generateCN66Report(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			billingStatus = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
			log.log(Log.INFO, "billingStatus1234++", billingStatus);
		}
		Collection<CN66DetailsVO> updatedCN66DetailsVO = new ArrayList<CN66DetailsVO>();
/*		if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				cN66DetailsVO.setBillFrm(logon.getOwnAirlineCode());

				if (cN66DetailsVO.getBillingStatus() != null) {
					for (OneTimeVO oneTimeVO : billingStatus) {
						if (cN66DetailsVO.getMailCategoryCode().equals(
								oneTimeVO.getFieldValue())) {
							cN66DetailsVO.setMailCategoryCode(oneTimeVO
									.getFieldDescription());
							updatedCN66DetailsVO.add(cN66DetailsVO);
						}
					}
				}
			}
		}*/
		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : billingStatus) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logon.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}
		reportSpec.addExtraInfo(cn51CN66FilterVO);
		reportSpec.addSubReportParameter(cn51CN66FilterVO);
		ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
		reportMetaDataForCN51.setColumnNames(new String[] {
				SECTOR,MALCTGCOD,TOTWGT,APLRAT,VATAMT,NETAMT,MALSUBCLS,
				"MONTHFLAG","TOTAMTLC",TOTAMTCP,INVNUM,"POACOD","BILLFRM"
		});
		reportMetaDataForCN51.setFieldNames(new String[] {
				SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,"valCharges",
				"totalBilledAmount","mailSubclass","monthFlag","totalAmountLC","totalAmountCP","invoiceNumber","gpaCode",
				"airlineCode"
		});
		reportSpec.addSubReportMetaData(reportMetaDataForCN51);
		reportSpec.addSubReportData(cn51DetailsVos);
		/*-------------------------------------------------------------------*/
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "airlineCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cn51CN66FilterVO);
		ReportMetaData reportMetaDataForCN61 = new ReportMetaData();
		reportMetaDataForCN61.setColumnNames(new String[] { MALCTGCOD, "DSN", "RSN", "CCAREFNUM", "ORGCOD", "DSTCOD",
				SECTOR, "FLTNUM", TOTWGT, "APLRAT", "BLDAMT", "SRVTAX", VATAMT, NETAMT, "BLGCURCOD","BLDPRD"
				 //"RMK", MALSUBCLS, "MONTHFLG", "BLDPRD" ,"TOTPCS"
				,"TOTWGTCP","TOTWGTLC","TOTWGTEMS","TOTWGTSV","FLTDAT"});
		reportMetaDataForCN61.setFieldNames(new String[] { MAILCATEGORYCODE, "dsn", "rsn", "ccaRefNo",
				"origin", "destination", SECTOR1,
				"flightNumber", TOTALWEIGHT, APPLICABLERATE, "amount", "serviceTax", "valCharges", "netAmount", "currencyCode","billingPeriod",
				"weightCP","weightLC","weightEMS","weightSV","flightDate"
				//"billingStatus","mailSubclass", "monthFlag", "billingPeriod","totalPieces"
				});//Modified as part of ICRD-107002

		reportSpec.addSubReportMetaData(reportMetaDataForCN61);
		reportSpec.addSubReportData(cn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);
	}
	   	/**
	   	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateCN51Report(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO)
	   	 *	Added by 			: A-4809 on 10-Feb-2014
	   	 * 	Used for 	:
	   	 *	Parameters	:	@param filterVO
	   	 *	Parameters	:	@return
	   	 *	Parameters	:	@throws RemoteException
	   	 *	Parameters	:	@throws SystemException
	   	 */
	   	public Collection<CN51DetailsVO> generateCN51ReportPrint(CN51CN66FilterVO filterVO)
	   			throws RemoteException, SystemException {
	   		// TODO Auto-generated method stub
	   		//added by A-8527 for ICRD-330535 starts
	   		Collection<CN51DetailsVO> cn51detailsvos=new ArrayList<CN51DetailsVO>();
	   		String overrideRounding =null;
			Collection<String> systemParCodes = new ArrayList<String>();
			Map<String,String> systemParameterMap =null;
			systemParCodes.add(OVERRIDEROUNDING);
			systemParCodes.add(SHARED_AIRLINE_BASECURRENCY);
			try {
				systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage());
			}
			if(systemParameterMap !=null && systemParameterMap.size()>0)
				{
				overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
				}
			cn51detailsvos=CN51Summary.generateCN51Report(filterVO);// modified
			for(CN51DetailsVO cn51vo:cn51detailsvos){
				cn51vo.setOverrideRounding(overrideRounding);
			}
	   		return cn51detailsvos;
	   	//added by A-8527 for ICRD-330535 starts
	   	}
	   	/**
	   	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateCN66Report(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO)
	   	 *	Added by 			: A-4809 on 10-Feb-2014
	   	 * 	Used for 	:
	   	 *	Parameters	:	@param filterVO
	   	 *	Parameters	:	@return
	   	 *	Parameters	:	@throws RemoteException
	   	 *	Parameters	:	@throws SystemException
	   	 */
	   	public Collection<CN66DetailsVO> generateCN66ReportPrint(CN51CN66FilterVO filterVO)
	   			throws RemoteException, SystemException {
	   		// TODO Auto-generated method stub
	   		return CN51Summary.generateCN66Report(filterVO);
	}
	 /**
	  * Method		:	GPABillingController.generateGPAInvoiceReport
	  *	Added by 	:	A-4809 on 21-Feb-2014
	  * Used for 	:
	  *	Parameters	:	@param reportSpec
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException
	  *	Parameters	:	@throws RemoteException
	  *	Parameters	:	@throws MailTrackingMRABusinessException
	  *	Return type	: 	Map<String,Object>
	  */
		public Map<String, Object> generateGPAInvoiceReport(ReportSpec reportSpec)
		throws SystemException, RemoteException,
		MailTrackingMRABusinessException {
			log.log(Log.INFO, " inside generateInvoiceReport");
			LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
			AirlineVO airlineVO = null;
			InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
			//CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec.getFilterValues().iterator().next();
			 ArrayList<CN51CN66FilterVO> cN51CN66FilterVOs = (ArrayList<CN51CN66FilterVO>)reportSpec.getFilterValues().get(0);
				 for(CN51CN66FilterVO cn51CN66FilterVO:cN51CN66FilterVOs){
				InvoiceDetailsReportVO invoiceDetailsReportVO = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
				if (invoiceDetailsReportVO.getInvoiceNumber() == null) {
					invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
					if(invoiceDetailsReportVO1==null){
						MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
						ErrorVO reporterror = new ErrorVO(MailTrackingMRABusinessException.NO_DATA_FOUND);
						mailTrackingMRABusinessException.addError(reporterror);
						throw mailTrackingMRABusinessException;
					}
				}

				Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();

				if(invoiceDetailsReportVO.getInvoiceNumber()==null)
					{
					invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
					}
				else
					{
					invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
					}
				log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
				log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());
				airlineVO = CN51Summary.findAirlineAddress(
						cn51CN66FilterVO.getCompanyCode(),
						logon.getOwnAirlineIdentifier());
				if (airlineVO != null) {
					ReportMetaData parameterMetaDatas = new ReportMetaData();
					if (airlineVO.getAirlineName() == null) {
						airlineVO.setAirlineName("");
					}
					if (airlineVO.getBillingAddress() == null) {
						airlineVO.setBillingAddress("");
					}
					parameterMetaDatas.setFieldNames(new String[] { "airlineName",
							"billingAddress", "billingPhone1", "billingPhone2",
					"billingFax" });
					reportSpec.addParameterMetaData(parameterMetaDatas);
					reportSpec.addParameter(airlineVO);
				}
				ReportMetaData reportMetaData = new ReportMetaData();
				reportMetaData.setColumnNames(new String[] {"BLGSITCOD","BLGSITNAM","ARLADR","CORADR","SGNONE","DSGONE","SGNTWO","DSGTWO",
						"CURCOD","BNKNAM","BNKBRC","ACCNUM","CTYNAM","CNTNAM","SWTCOD","IBNNUM",
						"CURCOD1","BNKNAM1","BNKBRC1","ACCNUM1","CTYNAM1","CNTNAM1","SWTCOD1","IBNNUM1",
						"TOTAMTBLGCUR", "BLGCURCOD", "POANAM", "POAADR", "CITY", "STATE", "COUNTRY", "FAX", "VATNUM", "BLDPRD", MALCTGCOD,"CCOMID","INVDAT","RMK" });
				reportMetaData.setFieldNames(new String[] {"billingSiteCode","billingSite","airlineAddress","correspondenceAddress",
						"signatorOne","designatorOne","signatorTwo","designatorTwo",
						"currency","bankName","branch","accNo","bankCity","bankCountry","swiftCode","ibanNo",
						"currencyOne","bankNameOne","branchOne","accNoOne","bankCityOne","bankCountryOne","swiftCodeOne","ibanNoOne",
						"totalAmountinBillingCurrency","billingCurrencyCode","paName","address","city","state",
						"country","fax","vatNumber","billedDateString",MAILCATEGORYCODE,"clearComId","toDateString",FREETEXT});
				reportSpec.setReportMetaData(reportMetaData);
				reportSpec.setData(invoiceDetailsReportVOs);
				Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
				.generateCN66Report(cn51CN66FilterVO);
				Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
				Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
				oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
				try {
					oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
							cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
				} catch (ProxyException proxyException) {
					throw new SystemException(proxyException.getMessage());
				}
				Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
				if (oneTimeHashMap != null) {
					billingStatus = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
					log.log(Log.INFO, "billingStatus1234++", billingStatus);
				}
				Collection<CN66DetailsVO> updatedCN66DetailsVO = new ArrayList<CN66DetailsVO>();

				if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
					for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
						//Modified as a part of ICRD-193493 by A-7540
						if(cN66DetailsVO.getFlightDate() != null) {
							 String flightDateInString = TimeConvertor.toStringFormat(cN66DetailsVO.getFlightDate().toCalendar(),"dd-MMM-yy");
							 cN66DetailsVO.setFlightDateInString(flightDateInString);
						 }
						if (cN66DetailsVO.getBillingStatus() != null) {
							//Modified as a part of ICRD-193493 by A-7540
							/*for (OneTimeVO oneTimeVO : billingStatus) {
								if (cN66DetailsVO.getMailCategoryCode().equals(
										oneTimeVO.getFieldValue())) {
									cN66DetailsVO.setMailCategoryCode(oneTimeVO
											.getFieldDescription());*/
									updatedCN66DetailsVO.add(cN66DetailsVO);
								}
							//}
						//}
					}
				}


				Collection<CN51DetailsVO> cn51DetailsVosCur = performCN66ToCN51Grouping(updatedCN66DetailsVO);
				Collection<CN51DetailsVO> cn51DetailsVos = new ArrayList<CN51DetailsVO>();
				if (cn51DetailsVosCur != null || cn51DetailsVosCur.size() > 0) {
					for (CN51DetailsVO cn51VO : cn51DetailsVosCur) {
						cn51VO.setMonthFlag("C");
						cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
						cn51VO.setAirlineCode(logon.getOwnAirlineCode());
						cn51DetailsVos.add(cn51VO);
					}
				}
				reportSpec.addExtraInfo(cn51CN66FilterVO);
				reportSpec.addSubReportParameter(cn51CN66FilterVO);
				ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
				reportMetaDataForCN51.setColumnNames(new String[] {
						SECTOR,MALCTGCOD,TOTWGT,APLRAT,NETAMT,MALSUBCLS,VATAMT,
						"MONTHFLAG","TOTAMTLC",TOTAMTCP,INVNUM,"POACOD","BILLFRM"
				});
				reportMetaDataForCN51.setFieldNames(new String[] {
						SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,
						"totalBilledAmount","actualSubCls","vatAmount","monthFlag","totalAmountLC","totalAmountCP","invoiceNumber","gpaCode",
						"airlineCode"
				});
				reportSpec.addSubReportMetaData(reportMetaDataForCN51);
				reportSpec.addSubReportData(cn51DetailsVos);
				/*-------------------------------------------------------------------*/
				ReportMetaData parameterMetaData = new ReportMetaData();

				parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
						"gpaCode", "airlineCode" });
				reportSpec.addParameterMetaData(parameterMetaData);
				reportSpec.addParameter(cn51CN66FilterVO);
				ReportMetaData reportMetaDataForCN61 = new ReportMetaData();

				//Modidfied as per ICRD-193493 by A-7540
				reportMetaDataForCN61.setColumnNames(new String[] { MALCTGCOD, "DSN", "RSN", "CCAREFNUM", "ORGCOD", "DSTCOD",
						SECTOR, "FLTNUM","FLTDAT","HNI","RI", TOTWGT, APLRAT, "BLDAMT", "SRVTAX", NETAMT, "BLGCURCOD","BLDPRD"
						//"RMK", MALSUBCLS, "MONTHFLG", "BLDPRD" ,"TOTPCS"
						,"TOTWGTCP","TOTWGTLC","TOTWGTSV","TOTWGTEMS"});
				reportMetaDataForCN61.setFieldNames(new String[] { MAILCATEGORYCODE, "dsn", "rsn", "ccaRefNo",
						"origin", "destination", SECTOR1,
						"flightNumber","flightDateInString","hsn","regInd", TOTALWEIGHT, APPLICABLERATE, "amount", "serviceTax", "netAmount", "currencyCode","billingPeriod",
						"weightCP","weightLC","weightSV","weightEMS"
						//"billingStatus","mailSubclass", "monthFlag", "billingPeriod","totalPieces"
				});
				reportSpec.addSubReportMetaData(reportMetaDataForCN61);
				reportSpec.addSubReportData(updatedCN66DetailsVO);
			}
			return ReportAgent.generateReport(reportSpec);
	}
		/**
		 * 	Method		:	GPABillingController.generateGPABillingInvoiceReportTK
		 *	Added by 	:	A-4809 on 27-Feb-2014
		 * 	Used for 	:
		 *	Parameters	:	@param reportSpec
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 *	Parameters	:	@throws MailTrackingMRABusinessException
		 *	Return type	: 	Map<String,Object>
		 */
		public Map<String, Object> generateGPABillingInvoiceReportTK(ReportSpec reportSpec)
		throws SystemException, RemoteException,MailTrackingMRABusinessException {
			log.log(Log.INFO, " inside generateGPABillingInvoiceReportTK");
			LogonAttributes logon = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
			AirlineVO airlineVO = null;
		   		InvoiceDetailsReportVO invoiceDetailsReportVO=null;
			InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
			//CN51CN66FilterVO cn51CN66FilterVO =null;
			//Modified by A-4809 for ICRD-42160 Starts
			Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
			   ArrayList<CN51CN66FilterVO> cN51CN66FilterVOs = (ArrayList<CN51CN66FilterVO>)reportSpec.getFilterValues().get(0);
			   for(CN51CN66FilterVO cn51CN66FilterVO:cN51CN66FilterVOs){
			   		 invoiceDetailsReportVO = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
			if (invoiceDetailsReportVO.getInvoiceNumber() == null) {
						invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
				if(invoiceDetailsReportVO1==null){
					MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
					ErrorVO reporterror = new ErrorVO(
							MailTrackingMRABusinessException.NO_DATA_FOUND);
					mailTrackingMRABusinessException.addError(reporterror);
					throw mailTrackingMRABusinessException;
				}
			}

			if(invoiceDetailsReportVO.getInvoiceNumber()==null)
				{
				invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
				}
			else
				{
				invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
				}
	   			airlineVO = CN51Summary.findAirlineAddress(cn51CN66FilterVO.getCompanyCode(),logon.getOwnAirlineIdentifier());
		   	}
			//Modified by A-4809 for ICRD-42160 Ends
			log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
			log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());

			if (airlineVO != null) {
				ReportMetaData parameterMetaDatas = new ReportMetaData();
				if (airlineVO.getAirlineName() == null) {
					airlineVO.setAirlineName("");
				}
				if (airlineVO.getBillingAddress() == null) {
					airlineVO.setBillingAddress("");
				}
				parameterMetaDatas.setFieldNames(new String[] { "airlineName",
						"billingAddress", "billingPhone1", "billingPhone2",
				"billingFax" });
				reportSpec.addParameterMetaData(parameterMetaDatas);
				reportSpec.addParameter(airlineVO);
			}
			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] {"BLGSITCOD","BLGSITNAM","ARLADR","CORADR","SGNONE","DSGONE","SGNTWO","DSGTWO",
					 "CURCOD","BNKNAM","BNKBRC","ACCNUM","CTYNAM","CNTNAM","SWTCOD","IBNNUM",
					 "CURCOD1","BNKNAM1","BNKBRC1","ACCNUM1","CTYNAM1","CNTNAM1","SWTCOD1","IBNNUM1",
					 "TOTAMTBLGCUR", "BLGCURCOD", "POANAM", "POAADR", "CITY", "STATE", "COUNTRY", "FAX", "VATNUM", "BLDPRD", MALCTGCOD,"CCOMID","INVDAT" });
			reportMetaData.setFieldNames(new String[] {"billingSiteCode","billingSite","airlineAddress","correspondenceAddress",
					"signatorOne","designatorOne","signatorTwo","designatorTwo",
					"currency","bankName","branch","accNo","bankCity","bankCountry","swiftCode","ibanNo",
					"currencyOne","bankNameOne","branchOne","accNoOne","bankCityOne","bankCountryOne","swiftCodeOne","ibanNoOne",
					"totalAmountinBillingCurrency","billingCurrencyCode","paName","address","city","state",
					 "country","fax","vatNumber","billedDateString",MAILCATEGORYCODE,"clearComId","toDateString"});
			reportSpec.setReportMetaData(reportMetaData);
			reportSpec.setData(invoiceDetailsReportVOs);
			return ReportAgent.generateReport(reportSpec);
		}
		/**
		 * 	Method		:	GPABillingController.sendEmailforPAs
		 *	Added by 	:	A-4809 on 10-Mar-2014
		 * 	Used for 	:
		 *	Parameters	:	@param postalAdministrationVOs,generateInvoiceFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 */
		public void sendEmailforPAs(Collection<PostalAdministrationVO> postalAdministrationVOs,
				GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{

			
			String outParameter[] = null;
			String invNumber = null;
			StringBuilder remarks = new StringBuilder();
			StringBuilder finalRemarks = new StringBuilder();
			String txnSta = BLANK;
			String failureFlag = null;
			String successFlag = null;
			String Syspar ="System Parameter Not found";
			LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			Collection<String> invoiceNumbers = new ArrayList<String>();
			InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			//Added by A-8527 for ICRD-324512 starts
			Map<String,String> systemParameterMap =null;
			String overrideRounding =null;
			Collection<String> systemParCodes = new ArrayList<String>();
			systemParCodes.add(OVERRIDEROUNDING);
			try {
				systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage());
			}
			if(systemParameterMap !=null && systemParameterMap.size()>0){
				overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
			}
			//Added by A-8527 for ICRD-324512 Ends
			if(postalAdministrationVOs !=null && postalAdministrationVOs.size() >0){
			for(PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs){

				boolean success = false;
				GenerateInvoiceFilterVO invoiceFilterVO = new GenerateInvoiceFilterVO();
				invoiceFilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
				invoiceFilterVO.setBillingFrequency(postalAdministrationVO.getBillingFrequency());
				invoiceFilterVO.setBillingPeriodFrom(generateInvoiceFilterVO.getBillingPeriodFrom());
				invoiceFilterVO.setBillingPeriodTo(generateInvoiceFilterVO.getBillingPeriodTo());
				invoiceFilterVO.setCountryCode(postalAdministrationVO.getCountryCode());
				invoiceFilterVO.setGpaCode(postalAdministrationVO.getPaCode());
				invoiceFilterVO.setGpaName(postalAdministrationVO.getPaName());
				invoiceFilterVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());//Added for ICRD-211662
				invoiceFilterVO.setAddNew(generateInvoiceFilterVO.isAddNew());
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
				invoiceFilterVO.setCurrentDate(currentDate);
				 log.log(Log.FINE, "trigger from GPABIllingCOntroller");
				outParameter = MRABillingMaster.generateInvoiceTK(invoiceFilterVO);
				if(outParameter != null){

					log.log(Log.FINE, "outParameter[0]",outParameter[0]);
					log.log(Log.FINE, "outParameter[1]",outParameter[1]);
					log.log(Log.FINE, "outParameter[2]",outParameter[2]);

				String[] parameter =  outParameter[0].split("#");

				 log.log(Log.FINE, "Parameters :>>>>>>>>>>>>>>>>",parameter);
				if(parameter!=null && parameter.length>0){
					if(OK.equals(parameter[0])){
						//if(PostalAdministrationVO.FLAG_YES.equals(postalAdministrationVO.getAutoEmailReqd())){
							CN51CN66FilterVO cN51CN66FilterVO =null;
							Collection<CN66DetailsVO> cn66DetailsVos =null;
							Collection<CN51DetailsVO> cn51DetailsVos =null;
							if(parameter.length > 1 && parameter[1]!=null && parameter[1].trim().length()>0){
								int len = parameter.length-1;
								for(int i=1;i<=len;i++){
								cN51CN66FilterVO = new CN51CN66FilterVO();
								cN51CN66FilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
								cN51CN66FilterVO.setInvoiceNumber(parameter[i]);
								cN51CN66FilterVO.setGpaCode(postalAdministrationVO.getPaCode());
						   		 cn66DetailsVos = CN51Summary.generateCN66Report(cN51CN66FilterVO);
								 cn51DetailsVos = CN51Summary.generateCN51Report(cN51CN66FilterVO);
								 invoiceNumbers.add(parameter[i]);
								 invNumber = parameter[i];
								 log.log(Log.FINE, "Invoice number",invNumber);
								//added by A-8527 for ICRD-324283 starts
									for(CN66DetailsVO cn66DetailsVo:cn66DetailsVos){
										cn66DetailsVo.setSettlementCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
										cn66DetailsVo.setOverrideRounding(overrideRounding);
									}
								//cN51CN66VO = findCN51CN66Details(cN51CN66FilterVO);
								/*Commented for TK
									String sysparValue = null;
									// the system parameter for audit value Y for activate audit and N for
									// deactivate audit
									String sysparCode = "mailtracking.mra.dsnauditrequired";

									ArrayList<String> systemParameters = new ArrayList<String>();
									systemParameters.add(sysparCode);

									HashMap<String, String> systemParameterMap = null;
									try {
										systemParameterMap = (HashMap<String, String>) new SharedDefaultsProxy()
										.findSystemParameterByCodes(systemParameters);
									} catch (ProxyException e) {
										throw new SystemException(e.getMessage());
									}
									log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
									if (systemParameterMap != null) {
										sysparValue = systemParameterMap.get(sysparCode);
										// if value is Y then call the audit method in helper
										if ("Y".equals(sysparValue)) {
											MRAAuditBuilder mRAAuditBuilder = new MRAAuditBuilder();
											mRAAuditBuilder.auditInvoiceGeneration(invNumber);
										}
									}*/
							if(PostalAdministrationVO.FLAG_YES.equals(postalAdministrationVO.getAutoEmailReqd())){
							if(cn66DetailsVos!=null && cn66DetailsVos.size()>0){
								//cN51CN66VO.setEmailToaddress(postalAdministrationVO.getEmail());
								InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
								invoiceDetailsReportVO.setPaName(postalAdministrationVO.getPaName());
								invoiceDetailsReportVO.setAddress(postalAdministrationVO.getAddress());
								invoiceDetailsReportVO.setCity(postalAdministrationVO.getCity());
								invoiceDetailsReportVO.setState(postalAdministrationVO.getState());
								invoiceDetailsReportVO.setCountry(postalAdministrationVO.getCountry());
								invoiceDetailsReportVO.setPhone1(postalAdministrationVO.getPhone1());
								invoiceDetailsReportVO.setPhone2(postalAdministrationVO.getPhone2());
								invoiceDetailsReportVO.setFax(postalAdministrationVO.getFax());
								invoiceDetailsReportVO.setFromBillingPeriod(generateInvoiceFilterVO.getBillingPeriodFrom());
								invoiceDetailsReportVO.setToBillingPeriod(generateInvoiceFilterVO.getBillingPeriodTo());
								log.log(Log.FINE, "Details send to sendEmail cn66DetailsVos:-",cn66DetailsVos);
								log.log(Log.FINE, "Details send to sendEmail cn51DetailsVos:-",cn51DetailsVos);
								log.log(Log.FINE, "Details send to sendEmail invoiceDetailsReportVO:-",invoiceDetailsReportVO);
								log.log(Log.FINE, "Details send to sendEmail cN51CN66FilterVO:-",cN51CN66FilterVO);
								log.log(Log.FINE, "Details send to sendEmail emailID:-",postalAdministrationVO.getEmail());
								StringBuilder emailIds = new StringBuilder();
								if (postalAdministrationVO.getEmail() != null) {
									emailIds.append(postalAdministrationVO.getEmail());
									if (postalAdministrationVO.getSecondaryEmail1() != null
											&& !postalAdministrationVO.getSecondaryEmail1().isEmpty()) {
										emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail1());
									}
									if (postalAdministrationVO.getSecondaryEmail2() != null
											&& !postalAdministrationVO.getSecondaryEmail2().isEmpty()) {
										emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail2());
									}
									invoiceDetailsReportVO.setEmail(emailIds.toString());
								}
								try{
								sendEmail(cn66DetailsVos,cn51DetailsVos,invoiceDetailsReportVO,cN51CN66FilterVO,emailIds.toString());
								}catch(SystemException exception){
									log.log(Log.FINE, "Issue in sending email invoice for the ",postalAdministrationVO.getPaCode());
								}
							}
							}
							}
						}

						success = true;
						successFlag ="Y";
						isSuccessFlag="Y";
					}else{
					success = false;
					failureFlag = "Y";
					// Added by A-3434 for CR ICRD-114599 on 29SEP2015 begins..For Invoice log screen

					 log.log(Log.FINE, "outParameter[1] before setting rmk111",outParameter[1] );

					// if ( outParameter[1].trim().length() > 0) {

						remarks.append(outParameter[1]);


							log.log(Log.FINE, "remarks inside",remarks.toString() );
					//}

					 if(Syspar.equals(outParameter[0])){

						 break;

					   }

					 if(postalAdministrationVOs.size() >1){
						    remarks.append(COMMA);
					   }

					}
				}



		}		//Release Locks for Each PA
				if(!MRAConstantsVO.SCHEDULER_JOB_GPABLG.equals(generateInvoiceFilterVO.getSource())){
				Collection<LockVO> lockvos=new ArrayList<LockVO>();
				TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
				generateInvoiceLockVO.setAction(GENINV);
				generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
				generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
				generateInvoiceLockVO.setDescription(LOCkDESC);
				generateInvoiceLockVO.setRemarks(LOCKREMARK);
				generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
				generateInvoiceLockVO.setScreenId(LOCKSCREENID);
				lockvos.add(generateInvoiceLockVO);
				releaseLocks(lockvos);
				}
				isSuccessFlag="N";
	}
	}

				if (generateInvoiceFilterVO.getCountryCode() != null
						&& generateInvoiceFilterVO.getCountryCode().trim().length() > 0) {
					finalRemarks.append("Country:")
							.append(generateInvoiceFilterVO.getCountryCode()).append(COMMA);
				}if (generateInvoiceFilterVO.getGpaCode()!= null
						&& generateInvoiceFilterVO.getGpaCode().trim().length() > 0) {
					finalRemarks.append("GpaCod:")
							.append(generateInvoiceFilterVO.getGpaCode()).append(COMMA);
				}


				finalRemarks.append("From Date:").append(generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayFormat("dd/MM/yyyy"))
						.append(COMMA).append("To Date:")
						.append(generateInvoiceFilterVO.getBillingPeriodTo().toDisplayFormat("dd/MM/yyyy"));



			   if("Y".equals(failureFlag) && "Y".equals(isSuccessFlag)){
				   txnSta = "P";
				   finalRemarks.append("\n").append("Failure Reason:");
			   }else if("Y".equals(failureFlag)){
				   txnSta = "F";
				   finalRemarks.append("\n").append("Failure Reason:");
			   }else if("Y".equals(successFlag)){
				   txnSta = "C";
				   remarks.append(COMMA);
				   if("P".equals(generateInvoiceFilterVO.getInvoiceType())){
					   if(outParameter[0] != null && outParameter[0].split("#").length >=2)
						   {
						   remarks.append("Proforma Invoice: ").append(outParameter[0].split("#")[1]).append(" generated Successfully");
						   }

				   }else{
				   remarks.append(outParameter[1]);
				   }


			   }

			   if(remarks != null){

					log.log(Log.FINE, "remarks last",remarks.toString() );
					//finalRemarks.append(COMMA);
					finalRemarks.append(remarks.toString());

               }
			   invoiceTransactionLogVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
			   //invoiceTransactionLogVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());
			   invoiceTransactionLogVO.setInvoiceType("GB");
			   invoiceTransactionLogVO.setTransactionCode(generateInvoiceFilterVO.getTransactionCode());
			   invoiceTransactionLogVO.setSerialNumber(generateInvoiceFilterVO.getInvoiceLogSerialNumber());

			   invoiceTransactionLogVO.setInvoiceGenerationStatus(txnSta);


			   if(finalRemarks != null && finalRemarks.length() >0){
			   invoiceTransactionLogVO.setRemarks(finalRemarks.toString());
			   }else{
				   invoiceTransactionLogVO.setRemarks(BLANK);
			   }

				try {
				     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage());
				}

				// Added by A-3434 for CR ICRD-114599 on 29SEP2015 ends




		}




		/**
		 * @author A-7794 on 28-Nov-2017 as part of <ICRD-194277>
		 *
		 * @param gpaSettlementVOs
		 *
		 * @throws SystemException
		 */
		public Collection<ErrorVO> saveSAPSettlementMail(Collection<GPASettlementVO> gpaSettlementVOs) throws SystemException{
			log.entering("GPA Billing Controller", "saveSAPSettlementMail");
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			Collection<GPASettlementVO> gPAsettlementVOs = new ArrayList<GPASettlementVO>();
			Collection<InvoiceSettlementVO> InvoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>();
			Collection<SettlementDetailsVO> settlementDetailsVOs = new ArrayList<SettlementDetailsVO>();
			GPASettlementVO gpaSettlementVo = new GPASettlementVO();
			InvoiceSettlementVO invoiceSettlementVo = new InvoiceSettlementVO();
			SettlementDetailsVO settlementDetailsVo = new SettlementDetailsVO();
			InvoiceSettlementVO invSettlVO = null;
			GPASettlementVO stlmnt = null;
			LocalDate currentTime = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();

			if(null != gpaSettlementVOs){
				for(GPASettlementVO settlementVOFromProcessor :gpaSettlementVOs){
					if(null != settlementVOFromProcessor.getSettlementDetailsVOs()){
						for(SettlementDetailsVO settlementdetailsVO : settlementVOFromProcessor.getSettlementDetailsVOs()){
							settlementDetailsVo.setSettlementId(settlementdetailsVO.getSettlementId());
							settlementDetailsVo.setCompanyCode(settlementVOFromProcessor.getCompanyCode());
							settlementDetailsVo.setOperationFlag(settlementdetailsVO.getOperationFlag());
							settlementDetailsVo.setChequeCurrency(settlementdetailsVO.getChequeCurrency());
							settlementDetailsVo.setChequeAmount(settlementdetailsVO.getChequeAmount());
							settlementDetailsVo.setChequeAmountInSettlementCurr(settlementdetailsVO.getChequeAmountInSettlementCurr());
							settlementDetailsVo.setIsDeleted(settlementdetailsVO.getIsDeleted());
							settlementDetailsVo.setChequeDate(settlementdetailsVO.getChequeDate());
							settlementDetailsVo.setChequeNumber("0000");
							settlementDetailsVo.setChequeBank("0000");
							settlementDetailsVo.setChequeBranch("0000");
							//Commented as part of IASCB-1300
							//settlementDetailsVo.setIsAccounted("Y");
						}
					}
					if(null != settlementVOFromProcessor.getInvoiceSettlementVOs()){
						for(InvoiceSettlementVO invoiceSettlementVOFromProcessor : settlementVOFromProcessor.getInvoiceSettlementVOs()){
							CN51SummaryVO CN51summaryVO = GPABillingSettlement.findGPADtlsForSAPSettlemnt(invoiceSettlementVOFromProcessor.getInvoiceNumber(),settlementVOFromProcessor.getToDate(),settlementVOFromProcessor.getAccountNumber());
							if(null != CN51summaryVO){
								gpaSettlementVo.setGpaCode(CN51summaryVO.getGpaCode());
								gpaSettlementVo.setSettlementDate(settlementVOFromProcessor.getSettlementDate());
								gpaSettlementVo.setSettlementSequenceNumber(settlementVOFromProcessor.getSettlementSequenceNumber());
								gpaSettlementVo.setSettlementCurrency(settlementVOFromProcessor.getSettlementCurrency());
								gpaSettlementVo.setAccountNumber(settlementVOFromProcessor.getAccountNumber());
								gpaSettlementVo.setSettlementId(settlementVOFromProcessor.getSettlementId());
								gpaSettlementVo.setCompanyCode(settlementVOFromProcessor.getCompanyCode());
								gpaSettlementVo.setOperationFlag(settlementVOFromProcessor.getOperationFlag());
								gpaSettlementVo.setFrmScreen(settlementVOFromProcessor.getFrmScreen());
								gpaSettlementVo.setLastUpdatedTime(currentTime);
								gpaSettlementVo.setLastUpdatedUser(logonAttributes.getUserId());

								invoiceSettlementVo.setAmountInContractCurrency(CN51summaryVO.getTotalAmountInContractCurrency());
								invoiceSettlementVo.setCompanyCode(invoiceSettlementVOFromProcessor.getCompanyCode());
								invoiceSettlementVo.setInvoiceNumber(invoiceSettlementVOFromProcessor.getInvoiceNumber());
								invoiceSettlementVo.setInvSerialNumber(CN51summaryVO.getInvSerialNumber());
								invoiceSettlementVo.setGpaCode(CN51summaryVO.getGpaCode());
								settlementDetailsVo.setGpaCode(CN51summaryVO.getGpaCode());
								invoiceSettlementVo.setCurrentSettlingAmount(invoiceSettlementVOFromProcessor.getCurrentSettlingAmount());
								invoiceSettlementVo.setAmountInSettlementCurrency(invoiceSettlementVOFromProcessor.getAmountInSettlementCurrency());
								invoiceSettlementVo.setOperationFlag(invoiceSettlementVOFromProcessor.getOperationFlag());
								invoiceSettlementVo.setIsDeleted(invoiceSettlementVOFromProcessor.isDeleted());
								invoiceSettlementVo.setContractCurrencyCode(CN51summaryVO.getContractCurrencyCode());
								invoiceSettlementVo.setBillingCurrencyCode(CN51summaryVO.getBillingCurrencyCode());
								invoiceSettlementVo.setBillingPeriodTo(invoiceSettlementVOFromProcessor.getBillingPeriodTo());
								invoiceSettlementVo.setSettlementStatus(CN51summaryVO.getBillingStatus());
								if(null != CN51summaryVO.getDueAirline() && settlementDetailsVo.getChequeCurrency() != null){
									Money dueAmount = null;
								try {
									 dueAmount = CurrencyHelper.getMoney(settlementDetailsVo.getChequeCurrency());
									 BigDecimal dueAirln = new BigDecimal(CN51summaryVO.getDueAirline());
									 dueAmount.setAmount(dueAirln.doubleValue());
									 invoiceSettlementVo.setDueAmount(dueAmount);
								} catch (CurrencyException e) {
									LogFactory.getLogger(
											GPABillingController.MODULE_NAME)
											.log(Log.SEVERE, e.toString());
								}
								}
								invoiceSettlementVo.setLastUpdatedTime(currentTime);
								invoiceSettlementVo.setLastUpdatedUser(logonAttributes.getUserId());
								if(INVOICE_SETTL.equals(settlementVOFromProcessor.getFrmScreen()) && UPDATAE.equals(settlementVOFromProcessor.getOperationFlag()) ){

								if(null == settlementVOFromProcessor.getSettlementId()){
									invSettlVO = GPABillingSettlement.findLatestSettlementForInvoice(invoiceSettlementVOFromProcessor.getCompanyCode(), invoiceSettlementVOFromProcessor.getInvoiceNumber(), invoiceSettlementVOFromProcessor.getGpaCode());
								}else{
									stlmnt = GPABillingSettlement.findSettlementSeqNum(gpaSettlementVo.getGpaCode(), settlementVOFromProcessor.getSettlementId());
								}
								}
								if(null != invSettlVO){
									invoiceSettlementVo.setSerialNumber(invSettlVO.getSerialNumber());
									invoiceSettlementVo.setSettlementSequenceNumber(invSettlVO.getSettlementSequenceNumber());
									invoiceSettlementVo.setSettlementId(invSettlVO.getSettlementId());
								}
								if (null != stlmnt){
									gpaSettlementVo.setSettlementSequenceNumber(stlmnt.getSettlementSequenceNumber());
								}
								InvoiceSettlementVOs.add(invoiceSettlementVo);
								settlementDetailsVOs.add(settlementDetailsVo);
								gpaSettlementVo.setInvoiceSettlementVOs(InvoiceSettlementVOs);
								gpaSettlementVo.setSettlementDetailsVOs(settlementDetailsVOs);
								gPAsettlementVOs.add(gpaSettlementVo);

								try {
									saveSettlementDetails(gPAsettlementVOs);
								} catch (MailTrackingMRABusinessException e) {
									LogFactory.getLogger(
											GPABillingController.MODULE_NAME)
											.log(Log.SEVERE, e.toString());
								}
								settlementDetailsVOs.removeAll(settlementDetailsVOs);
								InvoiceSettlementVOs.removeAll(InvoiceSettlementVOs);
								gPAsettlementVOs.removeAll(gPAsettlementVOs);
							}
							else {
								log.entering("GPA Billing Controller",
										"Exception at saveSAPSettlementMail");
								ErrorVO errorVo = new ErrorVO(
										MRA_INV_SETTLEMENT_MAIL_ERROR);
								String []mergedErrorCode = new String[10];
								mergedErrorCode[0] = invoiceSettlementVOFromProcessor.getInvoiceNumber();
								errorVo.setErrorData(mergedErrorCode);
								errorVOs.add(errorVo);
							}
						}
					}
				}
			}

			return errorVOs;
		}

		/**
		 * @author A-7794 as part of ICRD-234354
		 * @param reportSpec
		 * @return Map<String, Object>
		 * @throws RemoteException
		 * @throws SystemException
		 * @throws MailTrackingMRABusinessException
		 */
		public Map<String, Object> generateCN51ReportKE(ReportSpec reportSpec)
		throws  RemoteException,SystemException,
		MailTrackingMRABusinessException {
				CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
					.getFilterValues().iterator().next();
					Collection<CN51DetailsVO> cn51DetailsVOs =  generateCN51ReportPrint(cn51CN66FilterVO);
					reportSpec.addExtraInfo(cn51DetailsVOs);
					return ReportAgent.generateReport(reportSpec);

		}

		/**
		 * @author A-7794 as part of ICRD-234354
		 * @param reportSpec
		 * @return Map<String, Object>
		 * @throws RemoteException
		 * @throws SystemException
		 * @throws MailTrackingMRABusinessException
		 */
		public Map<String, Object> generateCN66ReportKE(ReportSpec reportSpec)
		throws  RemoteException,SystemException,
		MailTrackingMRABusinessException {
			log.entering("GPABillingController", "generateCN66ReportKE");

			CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
					.getFilterValues().iterator().next();
			Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary.generateCN66ReportForKE(cn51CN66FilterVO);

			Map<String,String> systemParameterMap =null;
			String dsnLevelImport=null;
			String billingReportLevel=null;
			String curKey =null;
			String prevKey =null;
			Collection<String> systemParCodes = new ArrayList<String>();
			systemParCodes.add(DSNLEVELIMPORT);
			systemParCodes.add(BILLINGRPTLEVEL);

			try {
				systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage());
			}
			if(systemParameterMap !=null && systemParameterMap.size()>0){
				dsnLevelImport =systemParameterMap.get(DSNLEVELIMPORT);
				billingReportLevel = systemParameterMap.get(BILLINGRPTLEVEL);
			}
			ArrayList<CN66DetailsVO> cn66DetailsVOs = null;
			if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
				CN66DetailsVO cN66DetailsVOforRpt = null;
				cn66DetailsVOs = new ArrayList<CN66DetailsVO>();
				for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
					double amount=0.0;
					double serviceTax=0.0;
					double netAmount=0.0;
					double totalWeight=0.0;
					int count =0;

					curKey = new StringBuilder().append(cN66DetailsVO.getCompanyCode()).
							append(cN66DetailsVO.getInvoiceNumber()).append(cN66DetailsVO.getDsn()).toString();
					if(cN66DetailsVO.getFlightDate() != null) {
						String flightDateInString = TimeConvertor.toStringFormat(cN66DetailsVO.getFlightDate().toCalendar(),"dd-MMM-yy");
						cN66DetailsVO.setFlightDateInString(flightDateInString);
					}
					if(CN66DetailsVO.FLAG_YES.equals(dsnLevelImport)){
						cn66DetailsVOs.add(cN66DetailsVO);
					}else if(CN66DetailsVO.FLAG_NO.equals(dsnLevelImport)){
						if("M".equals(billingReportLevel)){
							cn66DetailsVOs.add(cN66DetailsVO);
						}else if("D".equalsIgnoreCase(billingReportLevel)){
							if(!curKey.equals(prevKey)){
								prevKey = curKey;
								cN66DetailsVO.setRsn("");
								cN66DetailsVO.setRegInd("");
								cN66DetailsVO.setHsn("");
								cn66DetailsVOs.add(cN66DetailsVO);
							}else{
								prevKey = curKey;
								count = cn66DetailsVOs.size();
								cN66DetailsVOforRpt = cn66DetailsVOs.get(count-1);
								totalWeight = cN66DetailsVOforRpt.getTotalWeight()+cN66DetailsVO.getTotalWeight();
								amount = cN66DetailsVOforRpt.getAmount()+cN66DetailsVO.getAmount();
								serviceTax = cN66DetailsVOforRpt.getServiceTax()+cN66DetailsVO.getServiceTax();

								netAmount = cN66DetailsVOforRpt.getNetAmount().getAmount()+cN66DetailsVO.getNetAmount().getAmount();
								cN66DetailsVOforRpt.setTotalWeight(totalWeight);
								cN66DetailsVOforRpt.setAmount(amount);
								cN66DetailsVOforRpt.setServiceTax(serviceTax);

								cN66DetailsVOforRpt.getNetAmount().setAmount(netAmount);
							}
						}
					}
				}
			}
			if (cn66DetailsVos == null || cn66DetailsVos.size() <= 0) {
				MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
				ErrorVO reporterror = new ErrorVO(
						MailTrackingMRABusinessException.NO_DATA_FOUND);
				mailTrackingMRABusinessException.addError(reporterror);
				throw mailTrackingMRABusinessException;
			}
			ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
					"gpaCode", "airlineCode" });
			reportSpec.addParameterMetaData(parameterMetaData);
			reportSpec.addParameter(cn51CN66FilterVO);

			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] { "ORGCOD" , "DSTCOD", MALCTGCOD, "DSN", "CCAREFNUM", "FLTNUM",
					"FLTDAT" ,SECTOR, "TOTWGTLC" , "TOTWGTCP" , "TOTWGTSV" , "TOTWGTEMS" ,
					APLRAT, "BLDAMT", "SRVTAX", NETAMT,  "BLGCURCOD","BLDPRD",
					"BLDAMT1","OVRRND","FLTCARCOD", TOTWGT,VATAMT
			});
			reportMetaData.setFieldNames(new String[] { "origin", "destination",MAILCATEGORYCODE, "dsn", "ccaRefNo","flightNumber","flightDateInString", SECTOR1,
					"weightLC","weightCP","weightSV","weightEMS", APPLICABLERATE, "actualAmount","serviceTax","netAmount",
					"currencyCode","billingPeriod","amount","overrideRounding","flightCarrierCode",TOTALWEIGHT,"vatAmount"
			});

			reportSpec.setReportMetaData(reportMetaData);
			reportSpec.setData(cn66DetailsVOs);
			return ReportAgent.generateReport(reportSpec);

		}

		/**
		 * 	Method		:	GPABillingController.findSettledMailbags
		 *	Added by 	:	A-7531 on 26-Apr-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<GPASettlementVO>
		 */
		public Collection<GPASettlementVO> findSettledMailbags(
				InvoiceSettlementFilterVO filterVO)throws SystemException {
			log.entering(CLASS_NAME, "findSettledMailbags");
			GPASettlementVO gpasettlementvo=null;
			Collection<GPASettlementVO> gpasettlementVO=new ArrayList<GPASettlementVO>();
			 Page<InvoiceSettlementVO> invoiceVO =null;
			Collection<GPASettlementVO> gpavo=new ArrayList<GPASettlementVO>();
			gpasettlementVO=findSettlementInvoiceDetails(filterVO);
			if(gpasettlementVO!=null){
			gpasettlementvo=gpasettlementVO.iterator().next();
			invoiceVO=findSettledMailbagDetails(filterVO);
			if(invoiceVO!=null&&invoiceVO.size()>0){
			gpasettlementvo.setInvoiceSettlementVO(invoiceVO);
			}

			if(gpasettlementVO!=null)
			{
				gpavo.add(gpasettlementvo);
			}
			}
			return gpavo;


		}

		/**
		 *
		 * 	Method		:	GPABillingController.findSettlementInvoiceDetails
		 *	Added by 	:   A-7531 on 26-Apr-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Return type	: 	GPASettlementVO
		 */

	public Collection<GPASettlementVO> findSettlementInvoiceDetails(InvoiceSettlementFilterVO filterVO)
			throws SystemException{
		return GPABillingSettlementInvoiceDetail.findSettlementInvoiceDetails(filterVO);
	}
		/**
		 *
		 * 	Method		:	GPABillingController.findSettledMailbagDetails
		 *	Added by 	:	A-7531 on 26-Apr-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Page<InvoiceSettlementVO>
		 */
	public Page<InvoiceSettlementVO> findSettledMailbagDetails(InvoiceSettlementFilterVO filterVO)throws SystemException{
		return GPABillingSettlementInvoiceDetail.findSettledMailbagDetails(filterVO);
	}

		/**
		 * 	Method		:	GPABillingController.findUnsettledMailbags
		 *	Added by 	:	A-7531 on 03-May-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<GPASettlementVO>
		 * @throws SystemException
		 */
		public Collection<GPASettlementVO> findUnsettledMailbags(
				InvoiceSettlementFilterVO filterVO) throws SystemException {
			log.entering(CLASS_NAME, "findUnsettledMailbags");
			GPASettlementVO gpasettlementvo=null;
			Collection<GPASettlementVO> gpasettlementVO=new ArrayList<GPASettlementVO>();
			 Page<InvoiceSettlementVO> invoiceVO =null;
			Collection<GPASettlementVO> gpavo=new ArrayList<GPASettlementVO>();
			gpasettlementVO=findUnsettledInvoiceDetails(filterVO);
			if(gpasettlementVO!=null){
			gpasettlementvo=gpasettlementVO.iterator().next();
			invoiceVO=findUnsettledMailbagDetails(filterVO);
			if(invoiceVO!=null&&invoiceVO.size()>0){
				gpasettlementvo.setInvoiceSettlementVO(invoiceVO);
			}
			if(gpasettlementVO!=null)
			{
				gpavo.add(gpasettlementvo);
			}
			}
			return gpavo;
		}

		/**
		 * 	Method		:	GPABillingController.findUnsettledInvoiceDetails
		 *	Added by 	:	A-7531 on 26-Jun-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<GPASettlementVO>
		 */
		private Collection<GPASettlementVO> findUnsettledInvoiceDetails(
				InvoiceSettlementFilterVO filterVO) throws SystemException{
			// TODO Auto-generated method stub
			return GPABillingSettlementInvoiceDetail.findUnsettledInvoiceDetails(filterVO);
		}

		/**
		 * 	Method		:	GPABillingController.findUnsettledMailbagDetails
		 *	Added by 	:	A-7531 on 03-May-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Page<InvoiceSettlementVO>
		 * @throws SystemException
		 */
		private Page<InvoiceSettlementVO> findUnsettledMailbagDetails(
				InvoiceSettlementFilterVO filterVO) throws SystemException {

			return GPABillingSettlementInvoiceDetail.findUnsettledMailbagDetails(filterVO);
		}

		/**
		 * 	Method		:	GPABillingController.findMailbagSettlementHistory
		 *	Added by 	:	A-7531 on 11-May-2018
		 * 	Used for 	:
		 *	Parameters	:	@param filterVO
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<GPASettlementVO>
		 * @throws SystemException
		 */
		public Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(
				InvoiceSettlementFilterVO invoiceFiletrVO) throws SystemException {

			return GPABillingSettlementInvoiceDetail.findMailbagSettlementHistory(invoiceFiletrVO);
		}

	/**
	 * 	Method		:	GPABillingController.saveSettlementsAtMailbagLevel
	 *	Added by 	:	A-7871 on 24-May-2018
	 * 	Used for 	: Settelement at mailbag level
	 *	Parameters	:	@param gpaSettlementVOs
	 *	Parameters	:	@return
	 *	Return type	: 	Collection void
	 * @throws MailTrackingMRABusinessException ,SystemException
	 */
	@Advice(name = "mail.mra.saveSettlementsAtMailbagLevel" , phase=Phase.POST_INVOKE)
	public void saveSettlementsAtMailbagLevel(
			Collection<GPASettlementVO> gpaSettlementVOs)
					throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveSettlementsAtMailbagLevel");
		Collection<GPASettlementVO> unsettledGPAVOs = null;
		String chequeAmountUpdateFlag="N"; //Added by A-8399 as part of ICRD-305647
		String prevSettKey = null;
		String currSetKey = null;
		Map<String, Double> isConvNeedMap=new HashMap<String,Double>();
		exchangeRateMap = new HashMap<String,Double>();
		Collection<InvoiceSettlementVO> invoicesettlvos = new ArrayList<InvoiceSettlementVO>();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		String invnum=null;
		String currency=null;
		LocalDate stldate = null;
		String settlementId=null;
		int itrCount=0;
		//InvoiceSettlementFilterVO filterVO= new InvoiceSettlementFilterVO();

		if (gpaSettlementVOs != null && gpaSettlementVOs.size() > 0) {
			for (GPASettlementVO gpaSettlementVO : gpaSettlementVOs) {
				//	filterVO.setInvoiceNumber(gpaSettlementVO.getInvoiceSettlementVO().g)
				log.log(Log.INFO, gpaSettlementVO.getOperationFlag());
				if(!"EXCELUPLOAD".equals(gpaSettlementVO.getFrmScreen())){//added for icrd-235819
				invnum=gpaSettlementVO.getInvoiceSettlementVO().iterator().next().getInvoiceNumber();
				stldate=gpaSettlementVO.getSettlementDate();
				currency=gpaSettlementVO.getSettlementCurrency();
				}
				// generating settlement ref no: start with M generateSettlementReferenceNumber in case of mailbag.
				if ("".equals(gpaSettlementVO.getSettlementId()) || gpaSettlementVO.getSettlementId()==null) {
					gpaSettlementVO.setSettlementId(generateSettlementReferenceNumber(gpaSettlementVO));
					settlementId=gpaSettlementVO.getSettlementId();

				}
				if (gpaSettlementVO.getInvoiceSettlementVOs() == null || gpaSettlementVO.getInvoiceSettlementVOs().size()==0 ) {//no current settlement --dummy cheque


					if("Y".equals(gpaSettlementVO.getUpdateFlag())){
						updateSettlementDetails(gpaSettlementVO.getSettlementDetailsVOs(),invnum,stldate,currency,settlementId);
					}
				/*
				 * Iterating settlement details for the settlement from Screen
				 */
				/*
				 * Populate GPABillingSettlement and  GPABillingSettlementDetails
				 * or update GPABillingSettlementDetails
				 */

				}
				else{
					unsettledGPAVOs = gpaSettlementVOs;
				/*if(gpaSettlementVO.getSettlementDetailsVOs()!=null){
					for(SettlementDetailsVO settlementDetails : gpaSettlementVO
							.getSettlementDetailsVOs()){
						updateSettlement(gpaSettlementVO,settlementDetails);
					}
				}*/



				if(gpaSettlementVO.getSettlementDetailsVOs()!=null &&  gpaSettlementVO.getSettlementDetailsVOs().size()>0){
					if(!"Y".equals(gpaSettlementVO.getUpdateFlag())){
					updateSettlementC51(unsettledGPAVOs);
						}
					for (SettlementDetailsVO settlementDetailsVO : gpaSettlementVO
							.getSettlementDetailsVOs()) {

						if ("I".equals(settlementDetailsVO.getOperationFlag())||"U".equals(settlementDetailsVO.getOperationFlag())||"I".equals(gpaSettlementVO.getOperationFlag()))
								{

						//this can be the case when from screen we have settelements, or if settlemnt deleted.
						if ((unsettledGPAVOs != null && unsettledGPAVOs.size() > 0) || "Y".equals(settlementDetailsVO.getIsDeleted())) {


							//this handles the delete case alone. ie any one in prev or.
							if("Y".equals(settlementDetailsVO.getIsDeleted())){
								updateSettlement(gpaSettlementVO,settlementDetailsVO);
								updateChequeDishonourMailbaglevel(settlementDetailsVO,unsettledGPAVOs.iterator().next().getInvoiceSettlementVOs());
							}

							else  if(!("Y".equals(settlementDetailsVO.getIsDeleted()))) {
								/*
								/*
								 * Iterating unsettled invoices
								 * in case of current settlement given dummy populated and unsettledGPAVOs is from screen
								 * in case of cheque details given , unsettledGPAVOs returned from query orderby rcvdat
								 */
								for (GPASettlementVO gpaVOforUnsettledMailbag : unsettledGPAVOs) {

//									int voSize=gpaVOforUnsettledMailbag
//											.getInvoiceSettlementVOs().size();
									int curRow=0;
									boolean isLastMailbag=false;

									for (InvoiceSettlementVO unSettledMailbagVO : gpaVOforUnsettledMailbag
											.getInvoiceSettlementVOs()) {
										curRow++;
										if(gpaVOforUnsettledMailbag.getInvoiceSettlementVOs().size()==curRow){
											isLastMailbag=true;
										}
										unSettledMailbagVO.setSettlementReferenceId(gpaSettlementVO.getSettlementId());
										if(settlementDetailsVO.getChequeNumber().equals("0000"))
										{
										if(settlementDetailsVO.getIndex()==unSettledMailbagVO.getIndex())
										{
											if("EXCELUPLOAD".equals(gpaSettlementVO.getFrmScreen())){//added for icrd-235819
												settlementDetailsVO.setChequeNumber(settlementId);
												}
											if(unSettledMailbagVO.getDueAmount()!=null  &&
												(settlementDetailsVO.getChequeAmount().getAmount() >0 ||"EXCELUPLOAD".equals(gpaSettlementVO.getFrmScreen())))// || EXCELUPLOAD check added for ICRD-349950





											{
												updateSettlement(gpaSettlementVO,settlementDetailsVO);
											updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap,isLastMailbag);
											chequeAmountUpdateFlag="Y";
											gpaSettlementVO.setSettlementChequeNumber(settlementDetailsVO.getChequeNumber());
											triggerAccountingAtMailBagLevel(unSettledMailbagVO,gpaSettlementVO);
										}
										}
										}
										else
										{
										if(unSettledMailbagVO.getDueAmount()!=null && unSettledMailbagVO.getDueAmount().getAmount()>0 &&
													(settlementDetailsVO.getChequeAmount().getAmount() >0||"Y".equals(unSettledMailbagVO.getCaseClosed())) && !"S".equals(unSettledMailbagVO.getSettlementStatus())  ){// || EXCELUPLOAD check added for ICRD-349950
											updateSettlement(gpaSettlementVO,settlementDetailsVO);
												updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap,isLastMailbag);

												chequeAmountUpdateFlag="Y";
												gpaSettlementVO.setSettlementChequeNumber(settlementDetailsVO.getChequeNumber());
												triggerAccountingAtMailBagLevel(unSettledMailbagVO,gpaSettlementVO);
											}
										itrCount++;
										if(settlementDetailsVO.getChequeAmount().getAmount()>0 && itrCount==gpaVOforUnsettledMailbag
												.getInvoiceSettlementVOs().size()&& "Y".equals(unSettledMailbagVO.getCaseClosed()))
										{
											updateSettlement(gpaSettlementVO,settlementDetailsVO);
											updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap,isLastMailbag);

											chequeAmountUpdateFlag="Y";
											gpaSettlementVO.setSettlementChequeNumber(settlementDetailsVO.getChequeNumber());
											triggerAccountingAtMailBagLevel(unSettledMailbagVO,gpaSettlementVO);
										}

										}

										/*if(itrReqd==true)//excess cheque amt and one mailbag
										{
											updateInvoiceSettelementMailbag(gpaSettlementVO,settlementDetailsVO,unSettledMailbagVO,isConvNeedMap,itrReqd);
											//updateSettlement(gpaSettlementVO,settlementDetailsVO);
											//updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap);
										}*/

										//if cheque amount is fully consumed iterate next cheque details
										//Modified by A-8399 as part of ICRD-305647
										if(settlementDetailsVO.getChequeAmount().getAmount()==0 && "Y".equals(unSettledMailbagVO.getCaseClosed()) && chequeAmountUpdateFlag.equals("N")){
                                               updateSettlement(gpaSettlementVO,settlementDetailsVO);

											updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap,isLastMailbag);
											gpaSettlementVO.setSettlementChequeNumber(settlementDetailsVO.getChequeNumber());
											triggerAccountingAtMailBagLevel(unSettledMailbagVO,gpaSettlementVO);
										}
									}}


}
							else {
								log.log(Log.INFO,
										"!!!!   ALL MAILBAGS SETTLED   !!!!!!!!!!!!!!!!!!!");
								if("Y".equals(settlementDetailsVO.getIsDeleted())){//if any settlement deleted.
									updateChequeDishonourMailbaglevel(settlementDetailsVO,unsettledGPAVOs.iterator().next().getInvoiceSettlementVOs());//in this case unsettledGPAVOs is null ie. all setteled.
								}

							}
							}}

						else  {
							log.log(Log.INFO,
									"!!!!   ALL MAILBAGS SETTLED   !!!!!!!!!!!!!!!!!!!");


							for (GPASettlementVO gpaVOforUnsettledMailbag : unsettledGPAVOs)
							{
								for (InvoiceSettlementVO unSettledMailbagVO : gpaVOforUnsettledMailbag
										.getInvoiceSettlementVOs())
						   {
							if("Y".equals(unSettledMailbagVO.getCaseClosed() )&& "N".equals(chequeAmountUpdateFlag)&& "Y".equals(gpaSettlementVO.getUpdateFlag()) )
							{//if any settlement deleted.
								updateInvoiceDetailsMailbaglevelforCaseClose(unSettledMailbagVO,gpaSettlementVO);

							}

						   }

						   }

						   }}




								}

					}
				if(gpaSettlementVO.getInvoiceSettlementVO()!=null && "U".equals(gpaSettlementVO.getOperationFlag())&& unsettledGPAVOs!=null) {

					if(gpaSettlementVO.getSettlementDetailsVOs()==null || gpaSettlementVO.getSettlementDetailsVOs().isEmpty()){

					for (GPASettlementVO gpaVOforUnsettledMailbag : unsettledGPAVOs) //IASCB-26810
					{
						if(gpaVOforUnsettledMailbag
								.getInvoiceSettlementVOs()!=null){
						for (InvoiceSettlementVO unSettledMailbagVO : gpaVOforUnsettledMailbag
								.getInvoiceSettlementVOs())
						{
							if("Y".equals(unSettledMailbagVO.getCaseClosed() )&& "N".equals(chequeAmountUpdateFlag) )
							{
								updateInvoiceDetailsMailbaglevelforCaseClose(unSettledMailbagVO,gpaSettlementVO);

							}

						}
						}
				   }
					}


					updateInvoiceRemarks(gpaSettlementVO);
				}
				}

		log.exiting(CLASS_NAME, "saveSettlementDetails");

			}
			}
	
	/**
	 * 	Method		:	GPABillingController.saveSettlementsAtMailbagLevelExcelUpload
	 *	Added by 	:	A-8061 on 15-Jun-2020
	 * 	Used for 	:	IASCB-57810
	 *	Parameters	:	@param gpaSettlementVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Return type	: 	void
	 */
	public void saveSettlementsAtMailbagLevelExcelUpload(
			Collection<GPASettlementVO> gpaSettlementVOs)
					throws SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveSettlementsAtMailbagLevelExcelUpload");
		Map<String, Double> isConvNeedMap=new HashMap<String,Double>();
		exchangeRateMap = new HashMap<String,Double>();

		GPASettlementVO gpaSettlementVO= gpaSettlementVOs.iterator().next();
		if(!gpaSettlementVO.isFromBatchSettlmentJob()){
		gpaSettlementVO.setSettlementId(generateSettlementReferenceNumber(gpaSettlementVO));
		}
		gpaSettlementVO.setSettlementChequeNumber(gpaSettlementVO.getSettlementId());

			if(gpaSettlementVO.getSettlementDetailsVOs()!=null &&  !gpaSettlementVO.getSettlementDetailsVOs().isEmpty()){

				for (SettlementDetailsVO settlementDetailsVO : gpaSettlementVO.getSettlementDetailsVOs()) {

					InvoiceSettlementVO unSettledMailbagVO=gpaSettlementVO.getInvoiceSettlementVOs().stream().filter(invoiceSettlementVO->
					settlementDetailsVO.getIndex()==invoiceSettlementVO.getIndex()).findAny().orElse(null);
					unSettledMailbagVO.setSettlementReferenceId(gpaSettlementVO.getSettlementId());
					settlementDetailsVO.setChequeNumber(gpaSettlementVO.getSettlementId());
					updateSettlement(gpaSettlementVO,settlementDetailsVO);
					updateInvoiceDetailsMailbaglevel(settlementDetailsVO, unSettledMailbagVO,isConvNeedMap,false);
					triggerAccountingAtMailBagLevel(unSettledMailbagVO,gpaSettlementVO);
				}

			}
		log.exiting(CLASS_NAME, "saveSettlementDetails");

			}


	/**
	 * 	A-8331
	 * @param gpaSettlementVO
	 * @throws SystemException
	 *
	 */
	private void updateInvoiceDetailsMailbaglevelforCaseClose(InvoiceSettlementVO unSettledInvoiceVO ,GPASettlementVO gpaSettlementVO) throws SystemException {
		// TODO Auto-generated method stub
		CN66Details cn66DetailsEntity = null;
		boolean isAccounted=false;
		try {
			cn66DetailsEntity = CN66Details.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO.getInvoiceNumber(),
					unSettledInvoiceVO.getGpaCode(),
					unSettledInvoiceVO.getMailsequenceNum(),
					unSettledInvoiceVO.getInvSerialNumber()
					);
		} catch (FinderException ex) {
			// TODO Auto-generated catch block
			throw new SystemException(
					ex.getMessage(), ex);
		}
		if("Y".equals(cn66DetailsEntity.getCaseClose())){
			isAccounted=true;
		}
		if(unSettledInvoiceVO.getDueAmount()!=null && !"Y".equals(cn66DetailsEntity.getCaseClose())){
			cn66DetailsEntity.setSettelementStatus("C");
			cn66DetailsEntity.setCaseClose("Y");
		}
		if(!isAccounted){
			triggerAccountingAtMailBagLevel(unSettledInvoiceVO,gpaSettlementVO);
		}
	}

	/**
	 *A-7871 for ICRD-235799
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param isConvNeedMap
	 * @param prevSettKey
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	private void updateInvoiceDetailsMailbaglevel(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO,Map<String, Double> isConvNeedMap,boolean isLastMailbag) throws MailTrackingMRABusinessException, SystemException {
		double amountToUpdateIncn66 = 0;
		double amountToUpdateInSettlement = 0;
        String overrideRounding="";

		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		Collection<InvoiceSettlementVO> associatedMailbags = null;
		Boolean isDeleted=false;
		if("Y".equals(settlementDetailsVO.getIsDeleted())){//this is delete case..
			/*
			 * Finding Associated invoices for the settlement (Cheque) and
			 * then updating the deleted flag and updating the invoice Details accordingly
			 */
			associatedMailbags = findInvoiceVOForSettlementMailbaglevel(settlementDetailsVO); //Change to mailbag for settlement VO instead of invoci new query to be written
			if(associatedMailbags!=null && associatedMailbags.size() > 0){
				for(InvoiceSettlementVO invoiceSettlementVO:associatedMailbags ){
					isDeleted=true;
					amountToUpdateIncn66=updateInvoiceSettlementDetails(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn66);
					//amountToUpdateIncn51smy=updateInvoiceStatus(settlementDetailsVO,invoiceSettlementVO);
					if(invoiceSettlementVO.getDueAmount()!=null  ){
						amountToUpdateInSettlement = invoiceSettlementVO.getDueAmount().getAmount() - amountToUpdateIncn66;
						amountToUpdateInSettlement=getScaledValue(amountToUpdateInSettlement,Integer.parseInt(overrideRounding));
						if(amountToUpdateInSettlement<0){
							amountToUpdateInSettlement=-(amountToUpdateInSettlement);
							amountToUpdateInSettlement=getScaledValue(amountToUpdateInSettlement,Integer.parseInt(overrideRounding));
						}
						unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
						//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
						updateInvoiceStatusForChequeDishonourMailbaglevel(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn66);
					}

				}
			}
			/*amountToUpdateIncn51smy=updateInvoiceSettlementDetails(settlementDetailsVO,unSettledInvoiceVO,amountToUpdateIncn51smy);
				amountToUpdateInSettlement = unSettledInvoiceVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
				unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
				settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
				updateInvoiceStatusForChequeDishonour(settlementDetailsVO,unSettledInvoiceVO,amountToUpdateIncn51smy);*/


		}else{
			amountToUpdateIncn66=updateInvoiceStatusMailbaglevel(settlementDetailsVO,unSettledInvoiceVO,isConvNeedMap,isLastMailbag);
			//amountToUpdateInSettlement = unSettledInvoiceVO.getDueAmount().getRoundedAmount() - amountToUpdateIncn51smy;
			//unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
			//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateIncn51smy);
			updateInvoiceSettlementDetails(settlementDetailsVO,unSettledInvoiceVO, amountToUpdateIncn66);
		}
	}

	/**
	 * 	Method		:	GPABillingController.updateSettlementC51
	 *	Added by 	:	A-7531 on 28-Dec-2018
	 * 	Used for 	:
	 *	Parameters	:	@param settlementDetailsVO
	 *	Parameters	:	@param unSettledInvoiceVO
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	private void updateSettlementC51( Collection<GPASettlementVO> unsettledGPAVOs) throws SystemException {
		// TODO Auto-generated method stub
		CN51Summary cn51SummaryEntity = null;
		CN66Details cn66DetailsEntity = null;
		double settledAmountcn66=0.0;
		double amount=0.0;
		double oldSetllemnt=0.0;
		double newSettlmnt=0.0;
		String overrideRounding="";
		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }

		//Collection<InvoiceSettlementVO> unSettledInvoiceVOs=unsettledGPAVOs.iterator().next().getInvoiceSettlementVOs();
		for(GPASettlementVO gpaVO:unsettledGPAVOs){
			Collection<InvoiceSettlementVO> unSettledInvoiceVOs=gpaVO.getInvoiceSettlementVOs();
		for(InvoiceSettlementVO unSettledInvoiceVO: unSettledInvoiceVOs){
			oldSetllemnt=oldSetllemnt+unSettledInvoiceVO.getAmountAlreadySettled().getAmount();

		try{
		cn51SummaryEntity = CN51Summary.find(
				unSettledInvoiceVO
				.getCompanyCode(),
				unSettledInvoiceVO
				.getInvoiceNumber(),
				unSettledInvoiceVO.getInvSerialNumber(),
				unSettledInvoiceVO
				.getGpaCode());
	} catch (FinderException ex) {
		throw new SystemException(
				ex.getMessage(), ex);
	}


		}
		newSettlmnt=cn51SummaryEntity.getSettlementAmount()-oldSetllemnt;
		newSettlmnt=	getScaledValue(newSettlmnt,Integer.parseInt(overrideRounding));
		cn51SummaryEntity.setSettlementAmount(newSettlmnt);
		}


	}

	/**
	 *a-7871 for ICRD-235799
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param isConvNeedMap
	 * @return amountToUpdateIncn66
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	protected double updateInvoiceStatusMailbaglevel(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO, Map<String, Double> isConvNeedMap,boolean isLastMailbag) throws MailTrackingMRABusinessException, SystemException {
		// TODO Auto-generated method stub
		CN51Summary cn51SummaryEntity = null;
		CN66Details cn66DetailsEntity = null;
		double exchangeRate = 1;
		double chequeAmount = 0.0;
		double dueAmountcn66=0.0;
		double settledAmountcn66=0.0;
		double dueAmountcn66forMca=0.0;
		double diffAmount=0.0;
		double amountAfterUpdation=0.0;
		double amount=0.0;

		double dueAmountcn51=0.0;
		double settledAmountcn51=0.0;
		double amountToUpdateIncn66 = 0;
		Money chequeAmountInBillingCurrency = null;
		Money chequeAmountInSettlemntCurrency = null;
		GPASettlementVO gpaSettlementVO = new GPASettlementVO();
		String currentSetKey=settlementDetailsVO.getSettlementId().concat(String.valueOf(settlementDetailsVO.getSettlementSequenceNumber())
				.concat(String.valueOf(settlementDetailsVO.getSerialNumber())));

		String overrideRounding ="";
		Collection<String> systemParCodes = new ArrayList<String>();
		Map<String,String> systemParameterMap =null;
		systemParCodes.add(OVERRIDEROUNDING);
		try {
			systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0)            {
            overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
            if("N".equals(overrideRounding)){
                overrideRounding="2";
            }
            }
		gpaSettlementVO.setOverrideRounding(overrideRounding);



		/**
		 * added check for avoiding multiple currency conversion for the same cheque.
		 */
		if(!settlementDetailsVO.getChequeCurrency().equals(unSettledInvoiceVO.getBillingCurrencyCode())){
		if(!isConvNeedMap.containsKey(currentSetKey)){

			exchangeRate = getExChangeratebase( settlementDetailsVO,
					unSettledInvoiceVO);
			isConvNeedMap.put(currentSetKey, exchangeRate);
			}else{
				exchangeRate = isConvNeedMap.get(currentSetKey);
			}
		}
		chequeAmount = settlementDetailsVO
				.getChequeAmount().getAmount()
				* exchangeRate;
		chequeAmount=	getScaledValue(chequeAmount,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
		try {
			chequeAmountInBillingCurrency = CurrencyHelper
					.getMoney(unSettledInvoiceVO
							.getBillingCurrencyCode());
			chequeAmountInSettlemntCurrency = CurrencyHelper
					.getMoney(settlementDetailsVO
							.getChequeCurrency());
		} catch (CurrencyException e) {
			log.log(Log.FINE,  "CurrencyException");
		}
		chequeAmountInBillingCurrency
		.setAmount(chequeAmount);
		//Modified by for ICRD-211662
		try {
			cn51SummaryEntity = CN51Summary.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getInvSerialNumber(),
					unSettledInvoiceVO
					.getGpaCode());
		} catch (FinderException ex) {
			throw new SystemException(
					ex.getMessage(), ex);
		}
		dueAmountcn51 = cn51SummaryEntity
				.getTotalAmountInBillingCurr()
				- cn51SummaryEntity.getSettlementAmount();
		dueAmountcn51=getScaledValue(dueAmountcn51,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
		settledAmountcn51 = cn51SummaryEntity
				.getSettlementAmount();

		//below code to update c66 details table --mailbag level
		try {
			cn66DetailsEntity = CN66Details.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getGpaCode(),
					unSettledInvoiceVO.getMailsequenceNum(),
					unSettledInvoiceVO.getInvSerialNumber()
					);
		} catch (FinderException ex) {
			// TODO Auto-generated catch block
			throw new SystemException(
					ex.getMessage(), ex);
		}


		dueAmountcn66 = cn66DetailsEntity.getNetAmountInBillingCurr()
				- cn66DetailsEntity.getSettlementAmt();

		dueAmountcn66=getScaledValue(dueAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
	if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0){
	dueAmountcn66=dueAmountcn66+unSettledInvoiceVO.getMcaNumber().getAmount();	// added for  IASCB-55115
	log.log(Log.FINEST, "updated due amount to be ", dueAmountcn66);
		dueAmountcn66=unSettledInvoiceVO.getDueAmount().getAmount();
		dueAmountcn66=getScaledValue(dueAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
	}

		settledAmountcn66 = cn66DetailsEntity
				.getSettlementAmt();

		/*
		if (dueAmountcn51 >=chequeAmountInBillingCurrency
				.getRoundedAmount()) {

			 * update due amount as dueamount - =
			 * netamount and set the settled amount
			 * as settledamount + = netamount

			dueAmountcn51 -= chequeAmountInBillingCurrency
					.getRoundedAmount();
			settledAmountcn51 += chequeAmountInBillingCurrency
					.getRoundedAmount();
			cn51SummaryEntity
			.updateSettlementStatus(settledAmountcn51);
		}


		else if (dueAmountcn51 < chequeAmountInBillingCurrency
				.getRoundedAmount()) {

			 * update due amount as dueamount = 0
			 * and set the settled amount as
			 * settledamount + = netamount


			settledAmountcn51 += dueAmountcn51;
			cn51SummaryEntity
			.updateSettlementStatus(settledAmountcn51);


		}*/


		if (dueAmountcn66 >=chequeAmountInBillingCurrency
				.getAmount()) {
			dueAmountcn66forMca=cn66DetailsEntity.getNetAmountInBillingCurr()
					- cn66DetailsEntity.getSettlementAmt();
			dueAmountcn66forMca=getScaledValue(dueAmountcn66forMca,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
			if(settlementDetailsVO.getChequeNumber().equals("0000"))
			{

			dueAmountcn66 = cn66DetailsEntity.getNetAmountInBillingCurr()- chequeAmountInBillingCurrency
					.getAmount();
			dueAmountcn66=getScaledValue(dueAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
			settledAmountcn66 = chequeAmountInBillingCurrency
					.getAmount();
			}
			else{
				dueAmountcn66-=chequeAmountInBillingCurrency
						.getAmount();
				dueAmountcn66=getScaledValue(dueAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));

				settledAmountcn66 += chequeAmountInBillingCurrency
						.getAmount();
				settledAmountcn66=getScaledValue(settledAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
			}
			amount=chequeAmountInBillingCurrency
					.getAmount();


			amountToUpdateIncn66 = chequeAmountInBillingCurrency
					.getAmount();
			//update in c66 mailbag level
			cn66DetailsEntity
			.updateSettlementStatus(settledAmountcn66);
			// added for  IASCB-55115
			if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0 &&  MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType()))
			{
				double amountt =0.0, due=0.0;
				amountt= unSettledInvoiceVO.getNetAmount().getAmount();
				due=amountt-settledAmountcn66;
				due=getScaledValue(due,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
		 cn66DetailsEntity.setDueAmount(due);
		 if(cn66DetailsEntity.getDueAmount()==0.0){
			 cn66DetailsEntity.setSettelementStatus(SETTLED);
		 }
		 else if (cn66DetailsEntity.getDueAmount()>0.0){
			 cn66DetailsEntity.setSettelementStatus(DIFFERENCE);
		 }
		 else if (cn66DetailsEntity.getDueAmount()<0.0)
		 {
			 cn66DetailsEntity.setSettelementStatus("O");
		 }

		}else
		{
			if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0 ){
				double amountafterdeduction=0.0 , netammt =0.0;
				amountafterdeduction=unSettledInvoiceVO.getActualBilled().getAmount() - settledAmountcn66;
				amountafterdeduction=getScaledValue(amountafterdeduction,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
				 cn66DetailsEntity.setDueAmount(amountafterdeduction);
				 if(cn66DetailsEntity.getDueAmount()==0.0){
					 cn66DetailsEntity.setSettelementStatus(SETTLED);
				 }
				 else if (cn66DetailsEntity.getDueAmount()>0.0){
					 cn66DetailsEntity.setSettelementStatus(DIFFERENCE);
				 }
				 else if (cn66DetailsEntity.getDueAmount()<0.0)
				 {
					 cn66DetailsEntity.setSettelementStatus("O");
				 }
			}
		}

			 double settlementAmountofcn51 = settledAmountcn51+chequeAmountInBillingCurrency
						.getAmount();
		//	 settlementAmountofcn51 = Math.abs(settlementAmountofcn51);


			 settlementAmountofcn51=getScaledValue(settlementAmountofcn51,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));

			cn51SummaryEntity
			.updateSettlementStatus(settlementAmountofcn51,unSettledInvoiceVO,Integer.parseInt(overrideRounding));
			if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0)
			{
			if(unSettledInvoiceVO.getMcaNumber().getAmount()>0)
			{
			if(unSettledInvoiceVO.getMcaNumber().getAmount()+chequeAmountInBillingCurrency
					.getAmount()>=dueAmountcn66forMca && cn66DetailsEntity.getDueAmount()==0)
					cn66DetailsEntity.setSettelementStatus("S");
			}
				else if(unSettledInvoiceVO.getMcaNumber().getAmount()<0)
				{
					if(-(unSettledInvoiceVO.getMcaNumber().getAmount())+chequeAmountInBillingCurrency
							.getAmount()>=dueAmountcn66forMca &&  cn66DetailsEntity.getDueAmount()==0)
							cn66DetailsEntity.setSettelementStatus("S");
				}
			}
			if(unSettledInvoiceVO.getDueAmount()!=null){
				unSettledInvoiceVO.getDueAmount().setAmount(dueAmountcn66);
				if((unSettledInvoiceVO.getDueAmount().getAmount()==0) || (unSettledInvoiceVO.getMcaNumber().getAmount()>0 && (unSettledInvoiceVO.getMcaNumber().getAmount()+chequeAmountInBillingCurrency
					.getAmount()>=dueAmountcn66forMca))){
					unSettledInvoiceVO.setSettlementStatus(SETTLED);
				}else if((unSettledInvoiceVO.getDueAmount().getAmount()==0) || (unSettledInvoiceVO.getMcaNumber().getAmount()<0 && (-(unSettledInvoiceVO.getMcaNumber().getAmount())+chequeAmountInBillingCurrency
					.getAmount()>=dueAmountcn66forMca))){
					unSettledInvoiceVO.setSettlementStatus(SETTLED);
				}
				else{
					unSettledInvoiceVO.setSettlementStatus(DIFFERENCE);

				}
			}
			chequeAmountInBillingCurrency
			.setAmount(0);//set to 0 due amount fully consumed
			settlementDetailsVO.getChequeAmount().setAmount(0);
			settlementDetailsVO.setOutStandingChqAmt(chequeAmountInBillingCurrency);
			chequeAmountInSettlemntCurrency.setAmount(0);
			settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);

		}

		else if (dueAmountcn66 <chequeAmountInBillingCurrency
				.getAmount())
		{
		/*	Double amountAfterUdation = chequeAmountInBillingCurrency
					.getRoundedAmount() - dueAmountcn66;
			chequeAmountInBillingCurrency
			.setAmount(amountAfterUdation);
			dueAmountcn66 = 0;
			settlementDetailsVO.getChequeAmount().setAmount(amountAfterUdation);
			chequeAmountInSettlemntCurrency.setAmount(amountAfterUdation);
			settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);
			settlementDetailsVO.setOutStandingChqAmt(chequeAmountInBillingCurrency);*/

			if (settlementDetailsVO.getChequeNumber().equals("0000")
					|| MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType())
					|| MRABTHSTL.equals(unSettledInvoiceVO.getSettlementFileType()))// || MRASTLUPD check added for
																						// ICRD-349950
			{

				//IASCB-27842 begin
				//settledAmountcn66 = chequeAmountInBillingCurrency
				//		.getAmount();
				//amountToUpdateIncn66 =unSettledInvoiceVO.getActualBilled().getAmount()-settledAmountcn66;
				settledAmountcn66 += chequeAmountInBillingCurrency
						.getAmount();
				settledAmountcn66=getScaledValue(settledAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
				amountToUpdateIncn66 =chequeAmountInBillingCurrency
						.getAmount();
				cn66DetailsEntity.updateSettlementStatus(settledAmountcn66);
				// added for  IASCB-55115
				if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0 )
				{
					double amountt =0.0, due=0.0;
					amountt= unSettledInvoiceVO.getDueAmount().getAmount();
					due=amountt-settledAmountcn66;
			      cn66DetailsEntity.setDueAmount(due);
			       if(cn66DetailsEntity.getDueAmount()==0.0){
				   cn66DetailsEntity.setSettelementStatus(SETTLED);
			      }
			       else if (cn66DetailsEntity.getDueAmount()>0.0){
						 cn66DetailsEntity.setSettelementStatus(DIFFERENCE);
					 }
					 else if (cn66DetailsEntity.getDueAmount()<0.0)
					 {
						 cn66DetailsEntity.setSettelementStatus("O");
					 }


			      }

				//cn66DetailsEntity.updateSettlementStatus(chequeAmountInBillingCurrency.getAmount());
				//IASCB-27842 end

				chequeAmountInBillingCurrency
				.setAmount(0);//this is done as chequee amount is not carry fwded to next mailbag
				settlementDetailsVO.getChequeAmount().setAmount(0);
				settlementDetailsVO.setOutStandingChqAmt(chequeAmountInBillingCurrency);
				chequeAmountInSettlemntCurrency.setAmount(0);
				settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);

			}
			else
			{
				if(isLastMailbag){
//					settledAmountcn66 = chequeAmountInBillingCurrency.getAmount();
//					amountToUpdateIncn66 =unSettledInvoiceVO.getActualBilled().getAmount()-settledAmountcn66;
					settledAmountcn66 += chequeAmountInBillingCurrency.getAmount();
					settledAmountcn66=getScaledValue(settledAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
					amountToUpdateIncn66 =chequeAmountInBillingCurrency.getAmount();
				}else{
				if(!unSettledInvoiceVO.isFromBatchSettlementJob()){
				settledAmountcn66 = settledAmountcn66+dueAmountcn66;
				}else{
					settledAmountcn66 = unSettledInvoiceVO.getSettlemetAmt().getAmount();
				}
				settledAmountcn66=getScaledValue(settledAmountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
				amountToUpdateIncn66 =dueAmountcn66;
				}


				if(amountToUpdateIncn66>=0 && dueAmountcn66!=0)
				{
				cn66DetailsEntity
				.updateSettlementStatus(settledAmountcn66);
				// amountAfterUpdation = chequeAmountInBillingCurrency
					//	.getAmount()- dueAmountcn66;
				// added for  IASCB-55115
				if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0 &&  MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType()))
				{
					double amountt =0.0, due=0.0;
					amountt= unSettledInvoiceVO.getNetAmount().getAmount();
					due=amountt-settledAmountcn66;
			 cn66DetailsEntity.setDueAmount(due);
			 if(cn66DetailsEntity.getDueAmount()==0.0){
				 cn66DetailsEntity.setSettelementStatus(SETTLED);
			 }
			 else if (cn66DetailsEntity.getDueAmount()>0.0){
				 cn66DetailsEntity.setSettelementStatus(DIFFERENCE);
			 }
			 else if (cn66DetailsEntity.getDueAmount()<0.0)
			 {
				 cn66DetailsEntity.setSettelementStatus("O");
			 }


			}else
			{
				if(unSettledInvoiceVO.getMcaNumber().getAmount()!=0 ){
					double amountdeducted=0.0 ;
					amountdeducted=unSettledInvoiceVO.getActualBilled().getAmount() - settledAmountcn66;
					amountdeducted=getScaledValue(amountdeducted,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
					 cn66DetailsEntity.setDueAmount(amountdeducted);
					 if(cn66DetailsEntity.getDueAmount()==0.0){
						 cn66DetailsEntity.setSettelementStatus(SETTLED);
					 }
					 else if (cn66DetailsEntity.getDueAmount()>0.0){
						 cn66DetailsEntity.setSettelementStatus(DIFFERENCE);
					 }
					 else if (cn66DetailsEntity.getDueAmount()<0.0)
					 {
						 cn66DetailsEntity.setSettelementStatus("O");
					 }
				}
			}



				if ("Y".equals(unSettledInvoiceVO.getCaseClosed()))
				{
				amountAfterUpdation = unSettledInvoiceVO.getAmountInSettlementCurrency()
							.getAmount()- dueAmountcn66;
				amountAfterUpdation=getScaledValue(amountAfterUpdation,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
				}
				else
				{
				amountAfterUpdation = chequeAmountInBillingCurrency
						.getAmount()- dueAmountcn66;
				amountAfterUpdation=getScaledValue(amountAfterUpdation,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
                }
				}
				else if(dueAmountcn66==0 && chequeAmount!=0 ){
					double stlamountcn66 = settledAmountcn66+chequeAmount;

					cn66DetailsEntity
					.updateSettlementStatus(getScaledValue(stlamountcn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding())));
					cn66DetailsEntity.setDueAmount(-chequeAmount);
					cn66DetailsEntity.setSettelementStatus("O");
					amountToUpdateIncn66=settledAmountcn66+chequeAmount;
					amountToUpdateIncn66=getScaledValue(amountToUpdateIncn66,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));

				}
				else
				{
					cn66DetailsEntity
					.updateSettlementStatus(chequeAmountInBillingCurrency
						.getAmount());
					amountAfterUpdation=0;

				}
				chequeAmountInBillingCurrency
				.setAmount(amountAfterUpdation);
				settlementDetailsVO.getChequeAmount().setAmount(amountAfterUpdation);
				chequeAmountInSettlemntCurrency.setAmount(amountAfterUpdation);
				settlementDetailsVO.setChequeAmountInSettlementCurr(chequeAmountInSettlemntCurrency);

			}




			if(isLastMailbag&&dueAmountcn66!=0){

			cn51SummaryEntity
			.updateSettlementStatus(getScaledValue(settledAmountcn51+chequeAmount,Integer.parseInt(gpaSettlementVO.getOverrideRounding())),unSettledInvoiceVO,Integer.parseInt(overrideRounding));
			}else if(dueAmountcn66==0 && chequeAmount!=0){

				cn51SummaryEntity
				.updateSettlementStatus(getScaledValue(settledAmountcn51+chequeAmount,Integer.parseInt(gpaSettlementVO.getOverrideRounding())),unSettledInvoiceVO,Integer.parseInt(overrideRounding));
			}else{
				if (MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType())
						|| MRABTHSTL.equals(unSettledInvoiceVO.getSettlementFileType())) {

					cn51SummaryEntity.updateSettlementStatus(getScaledValue(settledAmountcn51 + chequeAmount,
							Integer.parseInt(gpaSettlementVO.getOverrideRounding())),unSettledInvoiceVO,Integer.parseInt(overrideRounding));
				} else {

					cn51SummaryEntity.updateSettlementStatus(getScaledValue(settledAmountcn51 + dueAmountcn66,
							Integer.parseInt(gpaSettlementVO.getOverrideRounding())),unSettledInvoiceVO,Integer.parseInt(overrideRounding));
				}
			}
			dueAmountcn66 = 0;
			if(unSettledInvoiceVO.getDueAmount()!=null){
				unSettledInvoiceVO.getDueAmount().setAmount(dueAmountcn66);
			}

			unSettledInvoiceVO.setSettlementStatus(SETTLED);

		}

		//settledAmountcn51 += cn51SummaryEntity.getSettlementAmount();


		/**
		 * Update SettlementDetail Entity with the outstanding amount
		 */
		gpaSettlementVO.setCompanyCode(settlementDetailsVO.getCompanyCode());
		gpaSettlementVO.setGpaCode(settlementDetailsVO.getGpaCode());
		gpaSettlementVO.setSettlementSequenceNumber(settlementDetailsVO.getSettlementSequenceNumber());
		updateSettlement(gpaSettlementVO, settlementDetailsVO);
		if(unSettledInvoiceVO.getCaseClosed()!=null)
		{
		if(unSettledInvoiceVO.getCaseClosed().equals("Y"))
		{
			cn66DetailsEntity.setCaseClose("Y");
			cn66DetailsEntity.setSettelementStatus("C");
		}
		}
		// added for  IASCB-55115
		if(unSettledInvoiceVO.getMcaNumber()==null)
		{

		diffAmount=cn66DetailsEntity.getNetAmountInBillingCurr()-cn66DetailsEntity.getSettlementAmt();

		/*diffAmount = Math.abs(diffAmount);


		diffAmount=Math.round(Double.valueOf(diffAmount) * 1 * scale) / scale;*/
		diffAmount = Math.abs(diffAmount);
		diffAmount=getScaledValue(diffAmount,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
		if(diffAmount>=0){
        if(unSettledInvoiceVO.getTolerancePercentage()!=0 && chequeAmount!=0  )//Modified by A-8527 for IASCB-29995
        {

                        if(diffAmount<=(unSettledInvoiceVO.getTolerancePercentage()*cn66DetailsEntity.getNetAmountInBillingCurr()/100) && unSettledInvoiceVO.getSettlementMaxValue() == 0)
                                                        {
                                        cn66DetailsEntity.setSettelementStatus("S");

                                                        }else if(diffAmount<=unSettledInvoiceVO.getSettlementMaxValue())
                                                        {
                                                        	cn66DetailsEntity.setSettelementStatus("S");
                                                        }
        }
        else if(unSettledInvoiceVO.getSettlementValue()!=0  && chequeAmount!=0)//Modified by A-8527 for IASCB-29995
        {
                        if(diffAmount<=unSettledInvoiceVO.getSettlementValue())
                                        cn66DetailsEntity.setSettelementStatus("S");
        }
		}
		} else {
			if (MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType())
					|| MRABTHSTL.equals(unSettledInvoiceVO.getSettlementFileType())) {
			        diffAmount=unSettledInvoiceVO.getNetAmount().getAmount() - settledAmountcn66;
			} else {
				  diffAmount=unSettledInvoiceVO.getActualBilled().getAmount() - settledAmountcn66;
			         }
		diffAmount=getScaledValue(diffAmount,Integer.parseInt(gpaSettlementVO.getOverrideRounding()));
		diffAmount=Math.abs(diffAmount);
			       if(diffAmount>=0){
				            if(unSettledInvoiceVO.getTolerancePercentage()!=0 && chequeAmount!=0  )
				              {

                                         if(diffAmount<=(unSettledInvoiceVO.getTolerancePercentage()*unSettledInvoiceVO.getActualBilled().getAmount()/100) && unSettledInvoiceVO.getSettlementMaxValue() == 0)
                                                     {
                                                     cn66DetailsEntity.setSettelementStatus("S");

                                                     }else if(diffAmount<=unSettledInvoiceVO.getSettlementMaxValue())
                                                     {
                                                     	cn66DetailsEntity.setSettelementStatus("S");
                                                     }
				 }
				 else if (unSettledInvoiceVO.getSettlementValue()!=0  && chequeAmount!=0){

						     if(diffAmount<=unSettledInvoiceVO.getSettlementValue())
						     {
                                 cn66DetailsEntity.setSettelementStatus("S");
					         }
						     }
			                 }
		}
		//entity updation ends

		GPABillingController gpaBillingController = (GPABillingController) SpringAdapter.getInstance()
				.getBean("mRAGpaBillingcontroller");

		unSettledInvoiceVO.setSettlementStatus(cn66DetailsEntity.getSettelementStatus());
		if ((MRAEXCELUPD.equals(unSettledInvoiceVO.getSettlementFileType()))) {
		unSettledInvoiceVO.setFromScreen("EXCELUPLOAD");
		} else if (MRABTHSTL.equals(unSettledInvoiceVO.getSettlementFileType())) {
			unSettledInvoiceVO.setFromScreen("BATCHUPLOAD");
		} else {
			unSettledInvoiceVO.setFromScreen("MRA076");
		}
		gpaBillingController.auditInvoiceStatusMailbaglevel(unSettledInvoiceVO);
		return amountToUpdateIncn66;

	}


	@Advice(name = "mail.operations.auditInvoiceStatusMailbaglevel", phase = Phase.POST_INVOKE)
	public void auditInvoiceStatusMailbaglevel(InvoiceSettlementVO unSettledInvoiceVO) throws SystemException{

	}

	/**
	 *A-7871 for ICRD-235799
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param amountToUpdateIncn51smy
	 * @throws SystemException
	 */
	protected void updateInvoiceStatusForChequeDishonourMailbaglevel(
			SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO,
			double amountToUpdateIncn51smy) throws SystemException {
         String overrideRounding="";

		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		CN51Summary cn51SummaryEntity = null;
		CN66Details cn66Details=null;
		double settledAmount=0.0;
		try {
			cn51SummaryEntity = CN51Summary.find(
					settlementDetailsVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getInvSerialNumber(),
					settlementDetailsVO
					.getGpaCode());
		} catch (FinderException ex) {
			throw new SystemException(
					ex.getMessage(), ex);
		}
		//Modified by A-6991 for ICRD-211662
		//delete amount updated in GpaBillingSettlementInvoiceDetails Entity from the settled amount and update status
		settledAmount = cn51SummaryEntity
				.getSettlementAmount()-amountToUpdateIncn51smy;
		settledAmount =	getScaledValue(settledAmount,Integer.parseInt(overrideRounding));
		cn51SummaryEntity
		.updateSettlementStatus(settledAmount,unSettledInvoiceVO,Integer.parseInt(overrideRounding));

		//to update mailbag setteled amount

		//below code to update c66 details table --mailbag level
		try {
			cn66Details = CN66Details.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getGpaCode(),
					unSettledInvoiceVO.getMailsequenceNum(),
					unSettledInvoiceVO.getInvSerialNumber()
					);
		} catch (FinderException ex) {
			// TODO Auto-generated catch block
			throw new SystemException(
					ex.getMessage(), ex);
		}



		settledAmount = cn66Details
				.getSettlementAmt()-amountToUpdateIncn51smy;// amountToUpdateIncn51smy to amountToUpdateIncn66 be changed to as mailbag level
		settledAmount =	getScaledValue(settledAmount,Integer.parseInt(overrideRounding));
		cn66Details
		.updateSettlementStatus(settledAmount);


	}



	/**updateChequeDishonourMailbaglevel
	 *A-7871 for ICRD-235799
	 * @param settlementDetailsVO
	 * @return
	 * @throws SystemException
	 */

	private void updateChequeDishonourMailbaglevel(SettlementDetailsVO settlementDetailsVO,Collection<InvoiceSettlementVO> unsettledGPAVOs) throws SystemException{
		double amountToUpdateIncn66 = 0;
		double amountToUpdateInSettlement = 0;
		Collection<InvoiceSettlementVO> associatedMailbags = null;
		String overrideRounding="";

		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		Boolean isDeleted=false;
		if("Y".equals(settlementDetailsVO.getIsDeleted()) ){
			/*
			 * Finding Associated mailbags for the settlement (Cheque) and
			 * then updating the deleted flag and updating the invoice Details accordingly
			 */
			associatedMailbags = unsettledGPAVOs;
			if(associatedMailbags!=null && associatedMailbags.size() > 0){
				for(InvoiceSettlementVO invoiceSettlementVO:associatedMailbags ){
					isDeleted=true;
					amountToUpdateIncn66=updateInvoiceSettlementDetails(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn66);//this gives amount to be updated.
					//amountToUpdateIncn51smy=updateInvoiceStatus(settlementDetailsVO,invoiceSettlementVO);
					if(invoiceSettlementVO.getDueAmount()!=null  ){

						amountToUpdateInSettlement = invoiceSettlementVO.getDueAmount().getAmount() - amountToUpdateIncn66;
						amountToUpdateInSettlement =getScaledValue(amountToUpdateInSettlement,Integer.parseInt(overrideRounding));
						if(amountToUpdateInSettlement<0){
							amountToUpdateInSettlement=-(amountToUpdateInSettlement);
							amountToUpdateInSettlement =getScaledValue(amountToUpdateInSettlement,Integer.parseInt(overrideRounding));
						}
						//unSettledInvoiceVO.getDueAmount().setAmount(amountToUpdateInSettlement);
						//settlementDetailsVO.getChequeAmount().setAmount(amountToUpdateInSettlement);
						updateInvoiceStatusForChequeDishonour(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn66);//this to update in cn51smy
						updateMailbagStatusForChequeDishonour(settlementDetailsVO,invoiceSettlementVO,amountToUpdateIncn66);//this to update in cn66 mailbag level
					}
				}
			}}
	}


	/**
	 *A-7871 for ICRD-235799
	 * @param settlementDetailsVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<InvoiceSettlementVO> findInvoiceVOForSettlementMailbaglevel(
			SettlementDetailsVO settlementDetailsVO) throws SystemException {

		Collection<InvoiceSettlementVO> settledInvoiceSettlementVos = null;
		InvoiceSettlementFilterVO filterVO= new InvoiceSettlementFilterVO();
		filterVO.setSettlementReferenceNumber(settlementDetailsVO.getSettlementId());
		settledInvoiceSettlementVos = GPABillingSettlement
				.findSettledmailbags(filterVO);
		if(settledInvoiceSettlementVos!=null && settledInvoiceSettlementVos.size()>0){
			return settledInvoiceSettlementVos;
		}
		else{
			return null;
		}

	}


	/**
	 *
	 * @param settlementDetailsVO
	 * @param unSettledInvoiceVO
	 * @param amountToUpdateIncn51smy
	 * @throws SystemException
	 */
	private void updateMailbagStatusForChequeDishonour(
			SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO,
			double amountToUpdateIncn51smy) throws SystemException {
	    String overrideRounding="";

		Map<String, String> systemParameters = null;
		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		systemParameterCodes.add(OVERRIDEROUNDING);
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			e.getMessage();
		}

		 if(systemParameters !=null && systemParameters.size()>0)            {
	            overrideRounding = systemParameters.get(OVERRIDEROUNDING);
	            if("N".equals(overrideRounding)){
	                overrideRounding="2";
	            }
	            }
		CN66Details cn66Details = null;
		double settledAmount=0.0;

		//below code to update c66 details table --mailbag level
		try {
			cn66Details = CN66Details.find(
					unSettledInvoiceVO
					.getCompanyCode(),
					unSettledInvoiceVO
					.getInvoiceNumber(),
					unSettledInvoiceVO.getGpaCode(),
					unSettledInvoiceVO.getMailsequenceNum(),
					unSettledInvoiceVO.getInvSerialNumber()
					);
		} catch (FinderException ex) {
			// TODO Auto-generated catch block
			throw new SystemException(
					ex.getMessage(), ex);
		}



		settledAmount = cn66Details
				.getSettlementAmt()-amountToUpdateIncn51smy;// amountToUpdateIncn51smy to amountToUpdateIncn66 be changed to as mailbag level
		settledAmount=getScaledValue(settledAmount,Integer.parseInt(overrideRounding));
		cn66Details
		.updateSettlementStatus(settledAmount);


	}

	/**
	 * 	Method		:	GPABillingController.updateSettlementDetails
	 *	Added by 	:	A-7531 on 05-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param settlementDetailsVOs
	 *	Return type	: 	void
	 */
	public void updateSettlementDetails(
			Collection<SettlementDetailsVO> settlementDetailsVOs,String invnum,LocalDate stldate,String currency,String settlementId) throws SystemException{

		MRAGPABillingDAO mragpaBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "updateSettlementDetails");
			mragpaBillingDAO
			.updateSettlementDetails(settlementDetailsVOs,invnum,stldate,currency,settlementId);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	/**
	 * Method to find the exchange rate from settlement curr to billing curr
	 * @param unSettledInvoiceVO
	 * @param settlementDetailsVO
	 * @return exchangeRate
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 */
	private double getExChangeratebase(SettlementDetailsVO settlementDetailsVO,
			InvoiceSettlementVO unSettledInvoiceVO) throws MailTrackingMRABusinessException, SystemException {

	    LocalDate date=new LocalDate
					(LocalDate.NO_STATION,Location.NONE,true);
		ExchangeRateFilterVO exchangeRateFilterVO = new ExchangeRateFilterVO();
		exchangeRateFilterVO.setCompanyCode(unSettledInvoiceVO.getCompanyCode());
		   exchangeRateFilterVO.setFunctionPoint("GS");
		    exchangeRateFilterVO.setSubSystem("M");
		String exchangeRatebasis = null;
		double exgrate=0.0;
		Collection<ExchangeRateParameterMasterVO> exchangeRateParameters=null;
		try {
			exchangeRateParameters = (Collection<ExchangeRateParameterMasterVO>) new SharedCurrencyProxy()
					.findExchangeRateParameters(exchangeRateFilterVO);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			log.log(Log.WARNING,  "CurrencyException");
		}

		// Only one ExchangeRateParameterMasterVO will be got even though
		// Collection<ExchangeRateParameterMasterVO> is returned
		if(exchangeRateParameters!=null && exchangeRateParameters.size()>=1){
		ExchangeRateParameterMasterVO exchangeRateParameterMasterVO = exchangeRateParameters
				.toArray(new ExchangeRateParameterMasterVO[exchangeRateParameters
						.size()])[0];
		exchangeRatebasis = exchangeRateParameterMasterVO
				.getExchangeRateBasis();
		}
		      if("false".equals(settlementDetailsVO.getSettlementExists())) {
					date=unSettledInvoiceVO.getBillingPeriodTo();

				}else{
					if("I".equals(exchangeRatebasis)){
						date= unSettledInvoiceVO.getBillingPeriodTo();
					}else{
						date = settlementDetailsVO.getChequeDate();
					}
				}
		      exgrate = GPABillingSettlement
						.findExchangeRate(
								settlementDetailsVO.getCompanyCode(),
								unSettledInvoiceVO.getBillingCurrencyCode(),
								settlementDetailsVO.getChequeCurrency(),exchangeRatebasis,date);
				exchangeRateMap.put(unSettledInvoiceVO.getBillingCurrencyCode()+settlementDetailsVO.getChequeCurrency(),exgrate);
				return exgrate;
	}

	/**
	 *
	 * @param rebillRemarksDetailVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void saveGpaRebillRemarks(
			Collection<RebillRemarksDetailVO> rebillRemarksDetailVOs) throws SystemException {
		for(RebillRemarksDetailVO rebillRemarksDetailVO : rebillRemarksDetailVOs){
			GPARebillRemarkDetails mraRebillRemark=new GPARebillRemarkDetails();
		try {
			if("N".equals(rebillRemarksDetailVO.getOperationFlag()) ||"U".equals(rebillRemarksDetailVO.getOperationFlag())){
				mraRebillRemark=GPARebillRemarkDetails.find(rebillRemarksDetailVO.getCompanyCode(),rebillRemarksDetailVO.getGpaCode(), rebillRemarksDetailVO.getInvoiceNumber(), rebillRemarksDetailVO.getInvoiceSerialNumber(),
						rebillRemarksDetailVO.getMailSeqNum(),rebillRemarksDetailVO.getRebillRound()  );
				mraRebillRemark.update(rebillRemarksDetailVO);
			}
			else if("D".equals(rebillRemarksDetailVO.getOperationFlag())){
				mraRebillRemark=GPARebillRemarkDetails.find(rebillRemarksDetailVO.getCompanyCode(),rebillRemarksDetailVO.getGpaCode(), rebillRemarksDetailVO.getInvoiceNumber(), rebillRemarksDetailVO.getInvoiceSerialNumber(),
						rebillRemarksDetailVO.getMailSeqNum(),rebillRemarksDetailVO.getRebillRound() );
				mraRebillRemark.remove();
			}else if("I".equals(rebillRemarksDetailVO.getOperationFlag())){
				new GPARebillRemarkDetails(rebillRemarksDetailVO);
				log.log(Log.FINE, "No operation");
			}

		} catch (FinderException e) {

			new GPARebillRemarkDetails(rebillRemarksDetailVO);
		}
	}
	}

	/**
	 * @author A-5991
	 * @param mailIdr
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private long findMailSequenceNumber(String mailIdr,String companyCode) throws SystemException{
		MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
		long mailsequenceNumber=0;
		try {
			mailsequenceNumber= mailTrackingDefaultsProxy.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ProxyException Occured!!!!");
		}

		return mailsequenceNumber;
	}

	public Page<ReminderDetailsVO> findReminderListForGpaBilling(ReminderDetailsFilterVO reminderDetailsFilterVO) throws SystemException{
		MRAGPABillingDAO mragpaBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findReminderListForGpaBilling");
		return mragpaBillingDAO.findReminderListForGpaBilling(reminderDetailsFilterVO);

	}
	 catch (PersistenceException persistenceException) {
		throw new SystemException(persistenceException.getErrorCode());
	}
	}

	/**
	 *
	 * @param reminderDetailsFilterVO
	 * @throws SystemException
	 */
	public void generateGpaRebill(ReminderDetailsFilterVO reminderDetailsFilterVO)
		throws SystemException {
		MRAGPABillingDAO mragpaBillingDAO = null;
		String outPar="";
		String txnRemarks = "";
		String txnStatus="";
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		 outPar = mragpaBillingDAO.generateGpaRebill(reminderDetailsFilterVO);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		InvoiceTransactionLogVO txnLogVO = new InvoiceTransactionLogVO();
		txnLogVO.setCompanyCode(reminderDetailsFilterVO.getCompanyCode());
		txnLogVO.setInvoiceType(MailConstantsVO.GPA_REBILL);
		txnLogVO.setTransactionCode(reminderDetailsFilterVO.getTransactionCode());
		txnLogVO.setSerialNumber(reminderDetailsFilterVO.getTxnSerialNumber());
		if(outPar != null && outPar.contains("-")){
			txnStatus = MailConstantsVO.COMPLETED;
			txnRemarks = new StringBuilder("GPA Rebill invoice ").append(outPar)
					.append(" generated").toString();
			String invoiceDetails[]=outPar.split("-");
			reminderDetailsFilterVO.setInvoiceNumber(invoiceDetails[0]);
			reminderDetailsFilterVO.setGpaRebillRound(invoiceDetails[1]);
			//Added as part of ICRD-234283 by A-5526 for rebill invoice email sending
			try {
				new MailTrackingMRAProxy().sendEmailRebillInvoice(reminderDetailsFilterVO);
			} catch (ProxyException e) {
				log.log(Log.SEVERE, e.getMessage());
			}
		}else{
			txnStatus = MailConstantsVO.FAILED;
			txnRemarks = "GPA Rebill not generated";
		}
		txnLogVO.setInvoiceGenerationStatus(txnStatus);
		txnLogVO.setRemarks(txnRemarks);
		try {
		     new CRADefaultsProxy().updateTransactionandRemarks(txnLogVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, e.getMessage());
		}
	}


   /**
	 * 	Method		:	GPABillingController.processMraSettlementFromExcel
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 */
	public String processMraSettlementFromExcel(FileUploadFilterVO filterVO)throws SystemException {
             log.entering("GPABillingController", "processMraSettlementFromExcel");

             try
     	    {
     	      MRAGPABillingDAO mragpaBillingDAO = (MRAGPABillingDAO)MRAGPABillingDAO.class.cast(PersistenceController.getEntityManager().getQueryDAO(MODULE_NAME));
     	      return mragpaBillingDAO.processMraSettlementFromExcel(filterVO);
     	      } catch (PersistenceException persistenceException) {
     	    	  throw new SystemException(persistenceException.getErrorCode());
     		}
	}

	/**
	 * 	Method		:	GPABillingController.validateFromFileUpload
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 * @throws SystemException
	 */
	public String validateFromFileUpload(FileUploadFilterVO fileUploadFilterVO) throws SystemException {
		String processStatus="";
		Collection<InvoiceSettlementVO> mailSettlementVOs = GPABillingSettlement
				.fetchDataForUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType());

		Collection<GPASettlementVO>gapsettlementVOs=new ArrayList<GPASettlementVO>();
		if(mailSettlementVOs!=null){
			gapsettlementVOs = constructInvoiceSettlemetVOForExcelupload(mailSettlementVOs);
			if(gapsettlementVOs!=null&&gapsettlementVOs.size()>0){
				processStatus="OK";
			}
		}
		if("OK".equals(processStatus)){
			try {
				saveSettlementsAtMailbagLevelExcelUpload(gapsettlementVOs);
			} catch (MailTrackingMRABusinessException e) {
				e.printStackTrace();
			}
			processStatus="OK";
		}
		return processStatus;
	}


	/**
	 * 	Method		:	GPABillingController.constructInvoiceSettlemetVOForExcelupload
	 *	Added by 	:	A-7531 on 24-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailSettlementVOs
	 *	Return type	: 	void
	 * @return
	 * @throws SystemException
	 */
	private Collection<GPASettlementVO> constructInvoiceSettlemetVOForExcelupload(Collection<InvoiceSettlementVO> invoiceVOs) throws SystemException {
		String invoiceNumber="NA";
		String processStatus="";
		int i=0;
		int count=0;
		Collection<GPASettlementVO> gpasettlementVOs=new HashSet<GPASettlementVO>();
		GPASettlementVO gapsettlementVO = new GPASettlementVO() ;
		Collection<InvoiceSettlementVO> invDetailsVOs=null;
		Collection<SettlementDetailsVO> settlementDetailsVOs=null;
		LocalDate currentTime = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		Collection<InvoiceSettlementVO> errorList= new ArrayList<>();
		

		for(InvoiceSettlementVO invoiceVO:invoiceVOs){
			if(count==0){
			count=GPABillingSettlement
					.filenameValidation(invoiceVO);
			}
			if(invoiceVO.getInvoiceNumber()==null){
				invoiceVO.setErrorCode("Invalid invoice number");
				processStatus = "NOTOK";
			}else if(invoiceVO.getGpaCode()==null){
				invoiceVO.setErrorCode("Invalid PA code");
				processStatus = "NOTOK";
			}else if(invoiceVO.getMailbagID()==null || invoiceVO.getMailsequenceNum() == 0){
				invoiceVO.setErrorCode("Invalid mailbagID");
				processStatus = "NOTOK";
			}else if(invoiceVO.getSettlementCurrencyCode()==null){
				invoiceVO.setErrorCode("Invalid settlementcurrency");
				processStatus = "NOTOK";
			}else if(invoiceVO.getSettlemetAmt()==null){
			String status=null;

			try {
				CurrencyHelper.getMoney(invoiceVO.getSettlementCurrencyCode());
			} catch (CurrencyException e) {
				processStatus = "NOTOK";
				status="N";
				e.getErrorCode();
			}

			if(status!=null&&"N".equals(status)){
				invoiceVO.setErrorCode("Invalid settlement currency");
			}else{
				invoiceVO.setErrorCode("Invalid settlement amount");
				processStatus = "NOTOK";
			}
			}else if(count>1){
				processStatus = "NOTOK";
				invoiceVO.setErrorCode("Duplicate file name");

			}else if(invoiceVO.getSummaryGpa()!=null&&!invoiceVO.getSummaryGpa().equals(invoiceVO.getGpaCode())&&(!invoiceVO.getSummaryInvoiceNumber().equals(invoiceVO.getInvoiceNumber()))){
				invoiceVO.setErrorCode("PA code and invoice number combination error");
				processStatus = "NOTOK";

			}else if(invoiceVO.getSummaryInvoiceNumber()==null||!invoiceVO.getSummaryInvoiceNumber().equals(invoiceVO.getInvoiceNumber())){

				invoiceVO.setErrorCode("Mailbag id and invoice number combination error");
				processStatus = "NOTOK";

			}else if(invoiceVO.getSummaryInvoiceNumber()==null||invoiceVO.getSummaryGpa()==null||invoiceVO.getMailsequenceNum()==0){

				invoiceVO.setErrorCode("Invalid Data");
				processStatus = "NOTOK";

			}else{
				processStatus = "OK";
			}
			if("NOTOK".equals(processStatus)){
				errorList.add(invoiceVO);
			}
			else if("OK".equals(processStatus)){
				if(!invoiceVO.getInvoiceNumber().equals(invoiceNumber)){
					//mailbaglevel info population
					InvoiceSettlementVO vo = new InvoiceSettlementVO();
					vo.setCompanyCode(invoiceVO.getCompanyCode());
					vo.setGpaCode(invoiceVO.getGpaCode());
					vo.setContractCurrencyCode(invoiceVO.getContractCurrencyCode());
					vo.setDestnCode(invoiceVO.getDestnCode());
					vo.setSettlementFileName(invoiceVO.getSettlementFileName());
					vo.setSettlementFileType(invoiceVO.getSettlementFileType());
					vo.setFlownSector(invoiceVO.getFlownSector());
					vo.setInvoiceNumber(invoiceVO.getInvoiceNumber());
					vo.setActualBilled(invoiceVO.getActualBilled());
					vo.setMailCharge(invoiceVO.getMailCharge());
					vo.setNetAmount(invoiceVO.getNetAmount());
					vo.setSettlemetAmt(invoiceVO.getSettlemetAmt());
					vo.setSurCharge(invoiceVO.getSurCharge());
					vo.setAmountAlreadySettled(invoiceVO.getAmountAlreadySettled());
					vo.setDueAmount(invoiceVO.getDueAmount());
					vo.setMcaNumber(invoiceVO.getMcaNumber());
					vo.setMailRate(invoiceVO.getMailRate());
					vo.setMailsequenceNum(invoiceVO.getMailsequenceNum());
					vo.setSettlementCurrencyCode(invoiceVO.getSettlementCurrencyCode());
					vo.setRemarks(invoiceVO.getRemarks());
					vo.setTax(invoiceVO.getTax());
					vo.setMailbagID(invoiceVO.getMailbagID());
					vo.setProcessIdentifier(invoiceVO.getProcessIdentifier());
					vo.setSummaryInvoiceNumber(invoiceVO.getSummaryInvoiceNumber());
					vo.setSummaryGpa(invoiceVO.getSummaryGpa());
					vo.setInvSerialNumber(invoiceVO.getInvSerialNumber());
					vo.setBillingCurrencyCode(invoiceVO.getBillingCurrencyCode());
					vo.setTolerancePercentage(invoiceVO.getTolerancePercentage());
					vo.setSettlementValue(invoiceVO.getSettlementValue());
					vo.setSettlementMaxValue(invoiceVO.getSettlementMaxValue());
					vo.setSettlementLevel(invoiceVO.getSettlementLevel());
					if(invoiceVO.isFromBatchSettlementJob()){
						vo.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
						vo.setFromBatchSettlementJob(true);
					}
					vo.setIndex(i);


					String txnRemarks=null;
					invDetailsVOs = new ArrayList<InvoiceSettlementVO>();
					//cheque details population
					SettlementDetailsVO settlementDetailsVO=new SettlementDetailsVO();
					settlementDetailsVO.setChequeAmount(invoiceVO.getSettlemetAmt());
					settlementDetailsVO.setChequeDate(currentTime);
					txnRemarks=new StringBuilder("Uploaded as Excel").append(invoiceVO.getSettlementFileName()).toString();
					settlementDetailsVO.setRemarks(txnRemarks);
					settlementDetailsVO.setChequeNumber("0000");
					settlementDetailsVO.setIsDeleted("N");
					settlementDetailsVO.setCompanyCode(invoiceVO.getCompanyCode());
					settlementDetailsVO.setGpaCode(invoiceVO.getGpaCode());
					settlementDetailsVO.setChequeCurrency(invoiceVO.getSettlementCurrencyCode());
					settlementDetailsVO.setIndex(i);
					if(invoiceVO.isFromBatchSettlementJob()){
						settlementDetailsVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
					}
					settlementDetailsVOs=new ArrayList<SettlementDetailsVO>();

					settlementDetailsVOs.add(settlementDetailsVO);
					invDetailsVOs.add(vo);
					gapsettlementVO = new GPASettlementVO();
					gapsettlementVO.setUpdateFlag("Y");   //added for  IASCB-55115
					gapsettlementVO.setCompanyCode(invoiceVO.getCompanyCode());
					gapsettlementVO.setGpaCode(invoiceVO.getGpaCode());
					gapsettlementVO.setOperationFlag("I");
					gapsettlementVO.setSettlementCurrency(invoiceVO.getSettlementCurrencyCode());
					gapsettlementVO.setFrmScreen("EXCELUPLOAD");
					gapsettlementVO.setSettlementChequeNumber("0000");
					if(invoiceVO.isFromBatchSettlementJob()){
						gapsettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
						gapsettlementVO.setSettlementId(invoiceVO.getSettlementId());
						gapsettlementVO.setFromBatchSettlmentJob(true);
					}
					gapsettlementVO.setInvoiceSettlementVOs(invDetailsVOs);
					gapsettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);

					invoiceNumber = invoiceVO.getInvoiceNumber();

				}else{
					InvoiceSettlementVO vo = new InvoiceSettlementVO();
					vo.setCompanyCode(invoiceVO.getCompanyCode());
					vo.setGpaCode(invoiceVO.getGpaCode());
					vo.setContractCurrencyCode(invoiceVO.getContractCurrencyCode());
					vo.setDestnCode(invoiceVO.getDestnCode());
					vo.setSettlementFileName(invoiceVO.getSettlementFileName());
					vo.setSettlementFileType(invoiceVO.getSettlementFileType());
					vo.setFlownSector(invoiceVO.getFlownSector());
					vo.setInvoiceNumber(invoiceVO.getInvoiceNumber());
					vo.setActualBilled(invoiceVO.getActualBilled());
					vo.setMailCharge(invoiceVO.getMailCharge());
					vo.setNetAmount(invoiceVO.getNetAmount());
					vo.setSettlemetAmt(invoiceVO.getSettlemetAmt());
					vo.setSurCharge(invoiceVO.getSurCharge());
					vo.setAmountAlreadySettled(invoiceVO.getAmountAlreadySettled());
					vo.setDueAmount(invoiceVO.getDueAmount());
					vo.setMcaNumber(invoiceVO.getMcaNumber());
					vo.setMailRate(invoiceVO.getMailRate());
					vo.setMailsequenceNum(invoiceVO.getMailsequenceNum());
					vo.setSettlementCurrencyCode(invoiceVO.getSettlementCurrencyCode());
					vo.setRemarks(invoiceVO.getRemarks());
					vo.setTax(invoiceVO.getTax());
					vo.setMailbagID(invoiceVO.getMailbagID());
					vo.setProcessIdentifier(invoiceVO.getProcessIdentifier());
					vo.setSummaryInvoiceNumber(invoiceVO.getSummaryInvoiceNumber());
					vo.setSummaryGpa(invoiceVO.getSummaryGpa());
					vo.setInvSerialNumber(invoiceVO.getInvSerialNumber());
					vo.setBillingCurrencyCode(invoiceVO.getBillingCurrencyCode());
					vo.setTolerancePercentage(invoiceVO.getTolerancePercentage());
					vo.setSettlementValue(invoiceVO.getSettlementValue());
					vo.setSettlementMaxValue(invoiceVO.getSettlementMaxValue());
					vo.setSettlementLevel(invoiceVO.getSettlementLevel());
					if(invoiceVO.isFromBatchSettlementJob()){
						vo.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
						vo.setFromBatchSettlementJob(true);
					}
					vo.setIndex(i);

					SettlementDetailsVO settlementDetailsVO=new SettlementDetailsVO();
					settlementDetailsVO.setChequeAmount(invoiceVO.getSettlemetAmt());
					settlementDetailsVO.setChequeDate(currentTime);
					settlementDetailsVO.setChequeNumber("0000");
					settlementDetailsVO.setIsDeleted("N");
					settlementDetailsVO.setCompanyCode(invoiceVO.getCompanyCode());
					settlementDetailsVO.setGpaCode(invoiceVO.getGpaCode());
					settlementDetailsVO.setChequeCurrency(invoiceVO.getSettlementCurrencyCode());
					if(invoiceVO.isFromBatchSettlementJob()){
						settlementDetailsVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
					}
					settlementDetailsVO.setIndex(i);

					if(invDetailsVOs != null){
						invDetailsVOs.add(vo);
					}
					if(settlementDetailsVOs!=null){
						settlementDetailsVOs.add(settlementDetailsVO);
					}
					gapsettlementVO.setUpdateFlag("Y");  //added for  IASCB-55115
					gapsettlementVO.setCompanyCode(invoiceVO.getCompanyCode());
					gapsettlementVO.setGpaCode(invoiceVO.getGpaCode());
					gapsettlementVO.setSettlementCurrency(invoiceVO.getSettlementCurrencyCode());
					gapsettlementVO.setFrmScreen("EXCELUPLOAD");
					gapsettlementVO.setSettlementChequeNumber("0000");
					if(invoiceVO.isFromBatchSettlementJob()){
						gapsettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false));
						gapsettlementVO.setSettlementId(invoiceVO.getSettlementId());
						gapsettlementVO.setFromBatchSettlmentJob(true);
					}
					gapsettlementVO.setInvoiceSettlementVOs(invDetailsVOs);
					gapsettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);
				}

				gpasettlementVOs.add(gapsettlementVO)	;
			}
			invoiceNumber=invoiceVO.getInvoiceNumber();
			invoiceVO.setIndex(i);
			i++;

		}
		
		if(!errorList.isEmpty()){
			saveFileUploadError(errorList);
			gpasettlementVOs = null;

		}

		return gpasettlementVOs;

	}

	/**
	 * 	Method		:	GPABillingController.removeDataFromTempTable
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Return type	: 	void
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)throws SystemException {
		GPABillingSettlement.removeDataFromTempTable(fileUploadFilterVO);

	}


	/**
	 * 	Method		:	GPABillingController.saveFileUploadError
	 *	Added by 	:	A-7531 on 21-Jan-2019
	 * 	Used for 	:  icrd-235819
	 *	Parameters	:	@param invoiceVO
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	private void saveFileUploadError(Collection<InvoiceSettlementVO> invoiceVOs) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		
		Collection<FileUploadErrorLogVO> errorLogVOs=new ArrayList<FileUploadErrorLogVO>();

		for(InvoiceSettlementVO invoiceVO : invoiceVOs ){
		
		FileUploadErrorLogVO errorLogVO = new FileUploadErrorLogVO();
		errorLogVO.setCompanyCode(invoiceVO.getCompanyCode());
		errorLogVO.setProcessIdentifier(invoiceVO.getProcessIdentifier());
		errorLogVO.setFileName(invoiceVO.getSettlementFileName());
		errorLogVO.setFileType(invoiceVO.getSettlementFileType());
		errorLogVO.setLineNumber(invoiceVO.getIndex()+2);
		errorLogVO.setErrorCode(invoiceVO.getErrorCode());
		errorLogVO.setUpdatedUser(logonAttributes.getUserId());
		LocalDate date = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		errorLogVO.setUpdatedTime(date);
		errorLogVOs.add(errorLogVO);
		
		}
		
		
		if (!errorLogVOs.isEmpty()) {
			new SharedDefaultsProxy().saveFileUploadExceptions(errorLogVOs);
		}

	}


	/**
	 *
	 * @param reminderListFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<RebillRemarksDetailVO> findGPARemarkDetails(ReminderDetailsFilterVO reminderListFilterVO)
		throws SystemException{
		Collection<RebillRemarksDetailVO> remarks = null;
		MRAGPABillingDAO mragpaBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			remarks = mragpaBillingDAO.findGPARemarkDetails(reminderListFilterVO);

		}
		catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return remarks;
	}

	/***
	 * @author A-7794
	 * @param gpaSettlementVO
	 * @throws SystemException
	 */
	private void triggerAccountingAtMailBagLevel(InvoiceSettlementVO invoiceStlmntVO,GPASettlementVO gpaSettlementVO) throws SystemException {
		MRAGPABillingDAO mragpaBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mragpaBillingDAO = MRAGPABillingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "triggerAccounting");
			mragpaBillingDAO
			.triggerGPASettlementAccounting(invoiceStlmntVO,gpaSettlementVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * 
	 * 	Method		:	GPABillingController.generateInvoiceJobScheduler
	 *	Added by 	:	A-8061 on 10-Feb-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 * @throws ProxyException 
	 * @throws RemoteException 
	 * @throws MailTrackingMRABusinessException 
	 */
	public void generateInvoiceJobScheduler(String companyCode)throws SystemException, ProxyException, MailTrackingMRABusinessException, RemoteException{
		
			log.entering("GPABillingControlle", "generateInvoiceJobScheduler");
		
			Collection<BillingScheduleDetailsVO> billingScheduleDetailVOs=null;
		
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
			 	billingScheduleFilterVO.setCompanyCode(companyCode);
				billingScheduleFilterVO.setSource(MRAConstantsVO.SCHEDULER_JOB_GPABLG);
				billingScheduleDetailVOs = Proxy.getInstance().get(MRADefaultProxy.class).findBillingScheduleDetails(billingScheduleFilterVO);

				if(billingScheduleDetailVOs!=null && !billingScheduleDetailVOs.isEmpty()){
					GenerateInvoiceFilterVO generateInvoiceFilterVO=null;
					addLocks(companyCode);
					for(BillingScheduleDetailsVO billingScheduleDetailVO:billingScheduleDetailVOs){
						
							generateInvoiceFilterVO= new GenerateInvoiceFilterVO();
							generateInvoiceFilterVO.setCompanyCode(companyCode);
							generateInvoiceFilterVO.setBillingPeriodFrom(new LocalDate(billingScheduleDetailVO.getBillingPeriodFromDate(), true));
							generateInvoiceFilterVO.setBillingPeriodTo(new LocalDate(billingScheduleDetailVO.getBillingPeriodToDate(), true));
							generateInvoiceFilterVO.setSource(MRAConstantsVO.SCHEDULER_JOB_GPABLG);
							generateInvoiceFilterVO.setParamsList(billingScheduleDetailVO.getParamsList());
							generateInvoiceFilterVO.setBillingFrequency(billingScheduleDetailVO.getBillingPeriod());
						 	InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
							invoiceTransactionLogVO.setCompanyCode(companyCode);
							invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.GPA_BILLING);
							invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					   		invoiceTransactionLogVO.setPeriodFrom(generateInvoiceFilterVO.getBillingPeriodFrom());
					   		invoiceTransactionLogVO.setPeriodTo(generateInvoiceFilterVO.getBillingPeriodTo());
					   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MRAConstantsVO.INITIATED);
					   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
							invoiceTransactionLogVO.setRemarks("Invoice Generation Initiated,Trigger point :MRA_GPABLG");
							invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
							invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
						    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
						    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
					 
							invoiceTransactionLogVO = Proxy.getInstance().get(MRADefaultProxy.class).initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);

							generateInvoiceFilterVO.setInvoiceLogSerialNumber(invoiceTransactionLogVO.getSerialNumber());
							generateInvoiceFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());

						 ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).generateInvoiceTK(generateInvoiceFilterVO);
					}
							Collection<LockVO> lockvos=new ArrayList<>();
							TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
							generateInvoiceLockVO.setAction(GENINV);
							generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
							generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
							generateInvoiceLockVO.setDescription(LOCkDESC);
							generateInvoiceLockVO.setRemarks(LOCKREMARK);
							generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
							generateInvoiceLockVO.setScreenId(LOCKSCREENID);
							lockvos.add(generateInvoiceLockVO);
							releaseLocks(lockvos);
				
				}

			 
			log.exiting("GPABillingControlle", "generateInvoiceJobScheduler");	
		
	}
	
	
	
	
	
/**
	 * 	Method		:	GPABillingController.generateInvoiceTK
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160 generateInvoice specific for TK
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{
		log.entering("GPABillingControlle", "generateInvoiceTK");
		try {
			Collection<PostalAdministrationVO> postalAdministrationVOs = findAllPACodes(generateInvoiceFilterVO);
			sendEmailsForPA(postalAdministrationVOs, generateInvoiceFilterVO);
		} catch (SystemException se) {
			log.log(Log.SEVERE, "Exception", se);

		}
	}
	private void sendEmailsForPA(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{

		for(PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs){
			try{
				Collection<PostalAdministrationVO> palist = new ArrayList<>(1);
				palist.add(postalAdministrationVO);
				if(MRAConstantsVO.SCHEDULER_JOB_GPABLG.equals(generateInvoiceFilterVO.getSource())){
					if(postalAdministrationVO.isPASSPA()){
						generateInvoiceFilterVO.setInvoiceType(MRAConstantsVO.INV_TYP_PASS);
					}else if (MRAConstantsVO.FLAG_YES.equals(postalAdministrationVO.getProformaInvoiceRequired())){
						generateInvoiceFilterVO.setInvoiceType(MRAConstantsVO.INV_TYP_PROFOMA);
					}else{
						generateInvoiceFilterVO.setInvoiceType(MRAConstantsVO.INV_TYP_FINAL);
					}
					generateInvoiceFilterVO.setGpaCode(postalAdministrationVO.getPaCode());
				}
				new MailTrackingMRAProxy().sendEmailforPAs(palist,generateInvoiceFilterVO);
			}catch (ProxyException e){
				throw new SystemException(e.getMessage());
			}
		}
		
		if((postalAdministrationVOs==null || postalAdministrationVOs.isEmpty()) && MRAConstantsVO.SCHEDULER_JOB_GPABLG.equals(generateInvoiceFilterVO.getSource())){
			InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			invoiceTransactionLogVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType("GB");
			invoiceTransactionLogVO.setTransactionCode(generateInvoiceFilterVO.getTransactionCode());
			invoiceTransactionLogVO.setSerialNumber(generateInvoiceFilterVO.getInvoiceLogSerialNumber());
			invoiceTransactionLogVO.setInvoiceGenerationStatus("F");
			invoiceTransactionLogVO.setRemarks(new StringBuilder().append("From Date:")
					.append(generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayDateOnlyFormat())
					.append(",To Date:").append(generateInvoiceFilterVO.getBillingPeriodTo().toDisplayDateOnlyFormat())
					.append(",Failure reason:No eligible PAs").toString());
			try {
				new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			}
		}

	}
	/**
	 * 	Method		:	GPABillingController.sendRebillIvoiceEmail
	 *	Added by 	:	A-5526 on 04-Mar-2019
	 * 	Used for 	:	ICRD-234283 sendRebillIvoiceEmail
	 *	Parameters	:	@param cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void sendRebillIvoiceEmail(Collection<CN51DetailsVO> cn51DetailsVoS, CN51CN66FilterVO cn51cn66FilterVO,
			Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs)
			throws SystemException {
		log.entering("GPABillingController", "sendRebillIvoiceEmail");
		InvoiceDetailsReportVO invoiceDetailsReportVO = null;
		if (invoiceDetailsReportVOs != null && !invoiceDetailsReportVOs.isEmpty()) {
			invoiceDetailsReportVO = invoiceDetailsReportVOs.iterator().next();
		}
		GPABillingController gpaBillingController = (GPABillingController) SpringAdapter.getInstance()
				.getBean("mRAGpaBillingcontroller");
		gpaBillingController.sendRebillInvoiceEmail(cn51DetailsVoS, cn51cn66FilterVO, cn66DetailsVos,
				invoiceDetailsReportVOs, invoiceDetailsReportVO);

	}
	/**
	 * Method : GPABillingController.sendInvoiceEmail Added by : A-5526
	 * on 04-Mar-2019 Used for : ICRD-234283 sendrebillInvoiceEmail Parameters
	 * : @param
	 * cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs,invoiceDetailsReportVO
	 * Parameters : @throws SystemException Return type : void
	 */
	@Raise(module = "mail", submodule = "mra", event = "MRA_INVOICEGENERATE_EVENT", methodId = "mail.mra.sendRebillInvoiceEmail")
	public void sendRebillInvoiceEmail(Collection<CN51DetailsVO> cn51DetailsVoS, CN51CN66FilterVO cn51cn66FilterVO,
			Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs,
			InvoiceDetailsReportVO invoiceDetailsReportVO) {

		log.entering("GPABillingController", "sendRebillInvoiceEmail");
	}
	/**
	 * 	Method		:	GPABillingController.sendEmailRebillInvoice
	 *	Added by 	:	A-5526 on 04-Mar-2019
	 * 	Used for 	:	ICRD-234283 sendEmailRebillInvoice
	 *	Parameters	:	@param reminderDetailsFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void sendEmailRebillInvoice(ReminderDetailsFilterVO reminderDetailsFilterVO) throws SystemException {
		CN51CN66FilterVO cn51CN66FilterVO = new CN51CN66FilterVO();
		cn51CN66FilterVO.setCompanyCode(reminderDetailsFilterVO.getCompanyCode());
		cn51CN66FilterVO.setGpaCode(reminderDetailsFilterVO.getGpaCode());
		cn51CN66FilterVO.setInvoiceNumber(reminderDetailsFilterVO.getInvoiceNumber());
		cn51CN66FilterVO.setRebillInvNumber(new StringBuilder(reminderDetailsFilterVO.getInvoiceNumber()).append("-")
				.append(reminderDetailsFilterVO.getGpaRebillRound()).toString());
		AirlineVO airlineVO = null;
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoiceDetailsReportVO invoiceDetailsReportVO1 = new InvoiceDetailsReportVO();
		invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
		if (invoiceReportVO != null) {
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO1.setBillingCurrencyCode(invoiceReportVO.getBillingCurrencyCode());
			invoiceDetailsReportVO1.setInvoiceNumber(invoiceReportVO.getInvoiceNumber());
		}

		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
		airlineVO = CN51Summary.findAirlineAddress(cn51CN66FilterVO.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier());
		if (airlineVO != null) {
			if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
		}
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary.generateCN66Report(cn51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos = null;
		if (cn51CN66FilterVO.getRebillInvNumber() == null) {
			cn51DetailsVos = CN51Summary.generateCN51Report(cn51CN66FilterVO);
		}
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(cn51CN66FilterVO.getCompanyCode(),
					oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}
		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}

		try {
			new MailTrackingMRAProxy().sendEmailRebillInvoice(cn51DetailsVoS, cn51CN66FilterVO, cn66DetailsVos,
					invoiceDetailsReportVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}

	}
	/**
	 * 	Method		:	GPABillingController.sendRebillInvoiceEmail
	 *	Added by 	:	A-5526 on 04-Mar-2019
	 * 	Used for 	:	ICRD-234283 sendRebillInvoiceEmail
	 *	Parameters	:	@param cN51CN66FilterVO,email
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	private void sendRebillInvoiceEmail(CN51CN66FilterVO cN51CN66FilterVO, String email) throws SystemException {
		AirlineVO airlineVO = null;
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoiceDetailsReportVO invoiceDetailsReportVO1 = new InvoiceDetailsReportVO();
		invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cN51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary.generateInvoiceReport(cN51CN66FilterVO);
		if (cN51CN66FilterVO.getBillingPeriod() != null && !cN51CN66FilterVO.getBillingPeriod().isEmpty()) {
			String[] billingPeriod = cN51CN66FilterVO.getBillingPeriod().split("to");
			if (billingPeriod != null) {
				invoiceDetailsReportVO1.setFromDateString(billingPeriod[0]);
				invoiceDetailsReportVO1.setToDateString(billingPeriod[1]);
			}
		}
		if (invoiceReportVO != null) {
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
            //Added as part of ICRD-336851
			invoiceDetailsReportVO1.setBillingCurrencyCode(invoiceReportVO.getBillingCurrencyCode());
		}
		invoiceDetailsReportVO1.setEmail(email);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
		airlineVO = CN51Summary.findAirlineAddress(cN51CN66FilterVO.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier());
		if (airlineVO != null) {
			if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
		}
		//Added by A-8527 for ICRD-326276 starts
		PostalAdministrationVO postalAdministrationVO = null;
		try{
			postalAdministrationVO = new MailTrackingDefaultsProxy().findPostalAdminDetails(cN51CN66FilterVO.getCompanyCode(),cN51CN66FilterVO.getGpaCode());
		}catch(ProxyException pe){
			log.log(Log.SEVERE, "Proxy exception caught in sendRebillInvoiceEmail");
		}
		//Added by A-8527 for ICRD-326276 Ends
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary.generateCN66Report(cN51CN66FilterVO);
		//Added by A-8527 for ICRD-326276 starts
		for (CN66DetailsVO cn66DetailsVO:cn66DetailsVos){
			cn66DetailsVO.setSettlementCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
			}
		//Added by A-8527 for ICRD-326276 Ends
		Collection<CN51DetailsVO> cn51DetailsVos = null;
		if (cN51CN66FilterVO.getRebillInvNumber() == null) {
			cn51DetailsVos = CN51Summary.generateCN51Report(cN51CN66FilterVO);
		}

		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(cN51CN66FilterVO.getCompanyCode(),
					oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}
		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}

		GPABillingController gpaBillingController = (GPABillingController) SpringAdapter.getInstance()
				.getBean("mRAGpaBillingcontroller");
		gpaBillingController.sendRebillInvoiceEmail(cn51DetailsVoS, cN51CN66FilterVO, cn66DetailsVos,
				invoiceDetailsReportVOs, invoiceDetailsReportVO1);

	}
	/**
	 * 	Method		:	GPABillingController.sendEmailRebillInvoice
	 *	Added by 	:	A-5526 on 04-Mar-2019
	 * 	Used for 	:	ICRD-234283 sendEmailRebillInvoice
	 *	Parameters	:	@param cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void sendEmailRebillInvoice(Collection<CN51DetailsVO> cn51DetailsVoS, CN51CN66FilterVO cn51cn66FilterVO,
			Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs) {
		CN66DetailsVO cN66DetailsVO = null;
		if (cn66DetailsVos != null && !cn66DetailsVos.isEmpty()) {
			cN66DetailsVO = cn66DetailsVos.iterator().next();
		}
		GenerateInvoiceFilterVO generateInvoiceFilterVO = new GenerateInvoiceFilterVO();
		generateInvoiceFilterVO.setCompanyCode(cN66DetailsVO.getCompanyCode());

		LocalDate fromBillingPeriod = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toBillingPeriod = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		if (cN66DetailsVO.getBillingPeriod() != null) {
			String[] billingPeriod = cN66DetailsVO.getBillingPeriod().split("to");
			java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yy");
			java.util.Date fromdate = null;
			java.util.Date toDate = null;

			try {
				fromdate = formatter.parse(billingPeriod[0]);
				toDate = formatter.parse(billingPeriod[1]);
				if (fromdate != null)
					fromBillingPeriod.setTime(fromdate);

				if (toDate != null)
					toBillingPeriod.setTime(toDate);

			} catch (ParseException e) {
				log.log(Log.SEVERE, "Date parse Exception");
			}

		}

		generateInvoiceFilterVO.setBillingPeriodFrom(fromBillingPeriod);
		generateInvoiceFilterVO.setBillingPeriodTo(toBillingPeriod);
		generateInvoiceFilterVO.setGpaCode(cN66DetailsVO.getGpaCode());

		try {
			Collection<PostalAdministrationVO> postalAdministrationVOs = findAllPACodes(generateInvoiceFilterVO);
			for (PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs) {
				if (PostalAdministrationVO.FLAG_YES.equals(postalAdministrationVO.getAutoEmailReqd())) {
					StringBuilder emailIds = new StringBuilder();
					if (postalAdministrationVO.getEmail() != null) {
						emailIds.append(postalAdministrationVO.getEmail());
						if (postalAdministrationVO.getSecondaryEmail1() != null
								&& !postalAdministrationVO.getSecondaryEmail1().isEmpty()) {
							emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail1());
						}
						if (postalAdministrationVO.getSecondaryEmail2() != null
								&& !postalAdministrationVO.getSecondaryEmail2().isEmpty()) {
							emailIds.append(",").append(postalAdministrationVO.getSecondaryEmail2());
						}
						if (invoiceDetailsReportVOs != null && !invoiceDetailsReportVOs.isEmpty()) {
							for (InvoiceDetailsReportVO invoiceDetailsReportVO : invoiceDetailsReportVOs) {
								invoiceDetailsReportVO.setEmail(emailIds.toString());
								invoiceDetailsReportVO.setFromDateString(fromBillingPeriod.toDisplayDateOnlyFormat());
								invoiceDetailsReportVO.setToDateString(toBillingPeriod.toDisplayDateOnlyFormat());
							}
						}
					}
				}
			}
			sendRebillIvoiceEmail(cn51DetailsVoS, cn51cn66FilterVO, cn66DetailsVos, invoiceDetailsReportVOs);
		} catch (SystemException se) {
			log.log(Log.SEVERE, "Exception", se);

		}

	}

	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 */
	public InvoiceLovVO findInvoiceNumber(InvoiceLovVO invoiceLovVO)
	throws SystemException {
		return CN51Summary.findInvoiceNumber(invoiceLovVO);
	}
	/**
	 *
	 * 	Method		:	GPABillingController.asyncProcessSettlementUpload
	 *	Added by 	:	A-5219 on 08-Jan-2021
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void asyncProcessSettlementUpload(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException {
		String uploadStatus = "";
		String processStatus = "";
		try{
			uploadStatus = processMraSettlementFromExcel(fileUploadFilterVO);
			uploadStatus = validateFromFileUpload(fileUploadFilterVO);
			removeDataFromTempTable(fileUploadFilterVO);
			if("OK".equals(uploadStatus)){
				processStatus = "PC";
			}else{
				processStatus = "PE";
			}
			fileUploadFilterVO.setStatus(processStatus);
			new SharedDefaultsProxy().updateSettlementFileUploadStatus(fileUploadFilterVO);
			try{
				delLockingForSettlementUpload(fileUploadFilterVO.getCompanyCode());
			}catch(Exception ex){
				log.log(Log.SEVERE, "Exception in delLockingForSettlementUpload", ex);
			}
		}catch(Exception exception){
			log.log(Log.SEVERE, "Exception in asyncProcessSettlementUpload", exception);
		}
	}
	/**
	 * 	Method		:	GPABillingController.addLocks
	 *	Added by 	:	A-5219 on 10-Jan-2021
	 * 	Used for 	:
	 *	Parameters	:	@param addLockingForSettlementUpload
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ObjectAlreadyLockedException
	 *	Return type	: 	Collection<LockVO>
	 */
	public Collection<LockVO> addLockingForSettlementUpload(String companyCode)
	throws SystemException,ObjectAlreadyLockedException{
		Collection<LockVO> lockvos=new ArrayList<LockVO>();
		TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(MRA_STL_UPLOAD);
   		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		generateInvoiceLockVO.setAction(STL_UPLOAD);
		generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
		generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceLockVO.setDescription(STL_LOCK_DESC);
		generateInvoiceLockVO.setRemarks(LOCKREMARK);
		generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
		generateInvoiceLockVO.setScreenId(STL_LOCKSCREENID);
		lockvos.add(generateInvoiceLockVO);
		   Collection<LockVO> acquiredLockVOs =addFrameworkLocks(lockvos);
				return acquiredLockVOs;
	}
	/**
	 *
	 * 	Method		:	GPABillingController.delLockingForSettlementUpload
	 *	Added by 	:	A-5219 on 11-Jan-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ObjectAlreadyLockedException
	 *	Return type	: 	void
	 */
	public void delLockingForSettlementUpload(String companyCode)
			throws SystemException,ObjectAlreadyLockedException{
		Collection<LockVO> lockvos=new ArrayList<LockVO>();
		TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(MRA_STL_UPLOAD);
   		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		generateInvoiceLockVO.setAction(STL_UPLOAD);
		generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
		generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceLockVO.setDescription(STL_LOCK_DESC);
		generateInvoiceLockVO.setRemarks(LOCKREMARK);
		generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
		generateInvoiceLockVO.setScreenId(STL_LOCKSCREENID);
		lockvos.add(generateInvoiceLockVO);
		try {
		   new FrameworkLockProxy().releaseLocks(lockvos);
		} catch(ProxyException exception){
			log.log(Log.SEVERE, "Proxy ExceptionCaught",exception);
		}catch (Exception ex) {
			log.log(Log.SEVERE, "Exception ExceptionCaught",ex);
		}
		log.exiting("Processor","addLocks");
	}
	/**
	 * 	Method		:	GPABillingController.addFrameworkLocks
	 *	Added by 	:	A-4809 on 17-Mar-2021
	 * 	Used for 	:
	 *	Parameters	:	@param lockvos
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ObjectAlreadyLockedException 
	 *	Return type	: 	Collection<LockVO>
	 */
	private Collection<LockVO> addFrameworkLocks (Collection<LockVO> lockvos)
			throws SystemException{
		log.entering(CLASS_NAME,"addLocks");
		  Collection<LockVO> acquiredLockVOs = new ArrayList<LockVO>();
		  try {
			  Proxy.getInstance().get(FrameworkLockProxy.class).addLocks(lockvos);
			  
			} catch(ProxyException exception){
				log.log(Log.SEVERE, "Proxy ExceptionCaught",exception);
		  }
		  catch (SystemException ex) {
				log.log(Log.SEVERE," SystemException......."+ex.getErrors());
				boolean isFound = false;
				if (ex.getErrors() != null && ex.getErrors().size() > 0) {
					for (ErrorVO errvo : ex.getErrors()) {
						if(ObjectAlreadyLockedException.OBJECT_ALREADY_LOCKED
						.equals(errvo.getErrorCode())){
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
			log.exiting(CLASS_NAME,"addLocks");
		  return acquiredLockVOs;
	}
	/**
	 * 	Method		:	GPABillingController.findInvoicesforPASS
	 *	Added by 	:	A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GPAInvoiceVO>
	 */
	private Collection<GPAInvoiceVO> findInvoicesforPASS(GeneratePASSFilterVO passFilterVO)throws SystemException{
		log.entering(CLASS_NAME, "findInvoicesforPASS");
		return CN51Summary.findInvoicesforPASS(passFilterVO);
	}
	/**
	 * 	Method		:	GPABillingController.getFileGenerateConfigVO
	 *	Added by 	:	A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileName
	 *	Parameters	:	@return 
	 *	Return type	: 	FileGenerateConfigVO
	 * @throws SystemException 
	 */
	public FileGenerateConfigVO getFileGenerateConfigVO(String companyCode,String fileName,GPAInvoiceVO invoiceVO,LogonAttributes logonAttributes,GeneratePASSFilterVO passFilterVO) throws SystemException{
		FileGenerateConfigVO fileGenerateConfigVO=new FileGenerateConfigVO();
		Collection<FileGenerateFilterVO> screenFilters=getScreenFilters(companyCode,invoiceVO, logonAttributes,passFilterVO);
		fileGenerateConfigVO.setScreenFilters(screenFilters);
		fileGenerateConfigVO.setCompanyCode(companyCode);
		fileGenerateConfigVO.setFileType(fileName);
		return fileGenerateConfigVO;
	}
	/**
	 * 	Method		:	GPABillingController.getScreenFilters
	 *	Added by 	:	A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<FileGenerateFilterVO>
	 * @throws SystemException 
	 */
	public Collection<FileGenerateFilterVO> getScreenFilters(String companyCode,GPAInvoiceVO invoiceVO,LogonAttributes logonAttributes,GeneratePASSFilterVO passFilterVO) throws SystemException{
		Collection<FileGenerateFilterVO>screenFilters=new ArrayList<FileGenerateFilterVO>();
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT"); 
		FileGenerateFilterVO cmpcodFilter=getFileGenerateFilterVO(MRA_GPA_PAS_CMPCOD,invoiceVO.getCompanyCode());
		FileGenerateFilterVO periodNumberFilter=getFileGenerateFilterVO(MRA_GPA_PAS_PRDNUM,invoiceVO.getPeriodNumber());
		FileGenerateFilterVO gpacodFilter=getFileGenerateFilterVO(MRA_GPA_PAS_GPACOD,passFilterVO.getGpaCode());
		FileGenerateFilterVO brhofcFilter=getFileGenerateFilterVO(MRA_GPA_PAS_BRHOFC,invoiceVO.getBranchOffice());
		FileGenerateFilterVO seqnumFilter=getFileGenerateFilterVO(MRA_GPA_PAS_SEQNUM,invoiceVO.getSequenceNumber());
		FileGenerateFilterVO fileFormat=getFileGenerateFilterVO(MRA_GPA_PAS_FILFRM,invoiceVO.getFileFormat());
		FileGenerateFilterVO fileName=getFileGenerateFilterVO(MRA_GPA_PAS_FILNAM,invoiceVO.getInterfacedFileName());
		FileGenerateFilterVO addNew=getFileGenerateFilterVO(MRA_GPA_PAS_ADDNEW,passFilterVO.isAddNew()?"Y":"N");
		FileGenerateFilterVO logStation=getFileGenerateFilterVO(MRA_GPA_PAS_LOGSTN,logonAttributes.getStationCode());
		FileGenerateFilterVO triggerPoint=getFileGenerateFilterVO(MRA_GPA_PAS_TRGPNT,triggeringPoint);
		FileGenerateFilterVO userCode=getFileGenerateFilterVO(MRA_GPA_PAS_UPDUSR,logonAttributes.getUserId());

		screenFilters.add(cmpcodFilter);
		screenFilters.add(periodNumberFilter);
		screenFilters.add(gpacodFilter);
		screenFilters.add(brhofcFilter);
		screenFilters.add(seqnumFilter);
		screenFilters.add(fileFormat);
		screenFilters.add(fileName);
		screenFilters.add(addNew);
		screenFilters.add(logStation);
		screenFilters.add(triggerPoint);
		screenFilters.add(userCode);
		
		return screenFilters;
	}
	/**
	 * 	Method		:	GPABillingController.getFileGenerateFilterVO
	 *	Added by 	:	A-4809 on 13-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterCode
	 *	Parameters	:	@param filterValue
	 *	Parameters	:	@return 
	 *	Return type	: 	FileGenerateFilterVO
	 */
	public FileGenerateFilterVO getFileGenerateFilterVO(String filterCode,String filterValue){
		FileGenerateFilterVO fileGenerateFilterVO=new FileGenerateFilterVO();
			fileGenerateFilterVO.setFilterCode(filterCode);
			fileGenerateFilterVO.setFilterValue(filterValue);
		return fileGenerateFilterVO;
	}
	/**
	 * 	Method		:	GPABillingController.generatePASSFile
	 *	Added by 	:	A-4809 on 12-Mar-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void generatePASSFile(GeneratePASSFilterVO passFilterVO)throws SystemException{
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		Collection<LockVO> lockvos=new ArrayList<>();
		TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENPASMRA);
   		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
   		boolean isPASSAlreadyGen=false;
		generateInvoiceLockVO.setAction(GENPASS);
		generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
		generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceLockVO.setDescription(LOCKDESCPAS);
		generateInvoiceLockVO.setRemarks(LOCKREMARK);
		generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
		generateInvoiceLockVO.setScreenId(LOCKSCREENID);
		lockvos.add(generateInvoiceLockVO);
		   Collection<LockVO> acquiredLockVOs = addFrameworkLocks(lockvos);
		   log.log(Log.FINE, " Lock VOs acquiredLocks-=-=->"+acquiredLockVOs);
			String txnSta = null;
			String finalRemarks = null;
		   Collection<GPAInvoiceVO>invoices = findInvoicesforPASS(passFilterVO);
		   if(invoices!=null && !invoices.isEmpty()){
			   isPASSAlreadyGen=isPASSAlreadyGenerated(passFilterVO);
		    for (GPAInvoiceVO invoiceVO :invoices ){
		    	StringBuilder fileName =  new StringBuilder().append(logonAttributes.getOwnAirlineNumericCode()).append('A').
		    			append(invoiceVO.getPeriodNumber());
		    	String file = fileName.toString();
		    	invoiceVO.setFileFormat(file);
		    	String seqNum = getSequenceNumberforPASSFile(invoiceVO,isPASSAlreadyGen);
		    	invoiceVO.setSequenceNumber(seqNum);
		    	FileGenerateVO fileGenerateVO=getFileGenerateVO(invoiceVO,logonAttributes,passFilterVO);
		    	Proxy.getInstance().get(SharedDefaultsProxy.class).doGenerate(fileGenerateVO);
		    	updatePASSFileName(fileGenerateVO,invoiceVO, passFilterVO);

		    }
			   txnSta = "C";
			   StringBuilder remarks = new StringBuilder().append("PASS File generated successfully for period").
					   append(passFilterVO.getBillingPeriodFrom().toDisplayDateOnlyFormat()).append("to").
					   append(passFilterVO.getBillingPeriodTo().toDisplayDateOnlyFormat()); 
			   finalRemarks = remarks.toString();
		   }else{
			   txnSta = "F";
			   finalRemarks = "No Eligible Records Found"; 
		   }
		   releaseLocks(lockvos);

		   invoiceTransactionLogVO.setCompanyCode(passFilterVO.getCompanyCode());
		   invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.INV_TYP_PASS);
		   invoiceTransactionLogVO.setTransactionCode(passFilterVO.getTransactionCode());
		   invoiceTransactionLogVO.setSerialNumber(passFilterVO.getInvoiceLogSerialNumber());
		   invoiceTransactionLogVO.setInvoiceGenerationStatus(txnSta);
		   invoiceTransactionLogVO.setRemarks(finalRemarks.toString());
			try {
			     Proxy.getInstance().get(CRADefaultsProxy.class).updateTransactionandRemarks(invoiceTransactionLogVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			}
	}
	
	
	public  GeneratePASSFilterVO getPASSFilterVO(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException, ProxyException{
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		GeneratePASSFilterVO passFilterVO =new GeneratePASSFilterVO();
		passFilterVO.setPeriodNumber(billingScheduleDetailsVO.getPeriodNumber());
		passFilterVO.setBillingPeriodFrom(billingScheduleDetailsVO.getBillingPeriodFromDate());
		passFilterVO.setBillingPeriodTo(billingScheduleDetailsVO.getBillingPeriodToDate());
		passFilterVO.setParamsList(billingScheduleDetailsVO.getParamsList());
		passFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.INV_TYP_PASS);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodFrom(passFilterVO.getBillingPeriodFrom());
   		invoiceTransactionLogVO.setPeriodTo(passFilterVO.getBillingPeriodTo());
   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MRAConstantsVO.INITIATED);
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks("PASS Generation Initiated");
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());

		invoiceTransactionLogVO = Proxy.getInstance().get(MRADefaultProxy.class).initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);

    	passFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
    	passFilterVO.setInvoiceLogSerialNumber(invoiceTransactionLogVO.getSerialNumber());

		return passFilterVO;
	}
	
	public void generatePASSFileJobScheduler(String companyCode) throws SystemException, ProxyException{
		Collection<BillingScheduleDetailsVO> billingScheduleDetailVOs=null;
		BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
		billingScheduleFilterVO.setSource(MRAConstantsVO.SCHEDULER_JOB_PASSBLG);
		billingScheduleFilterVO.setCompanyCode(companyCode);
	
		billingScheduleDetailVOs = Proxy.getInstance().get(MRADefaultProxy.class).findBillingScheduleDetails(billingScheduleFilterVO);
			if(billingScheduleDetailVOs!=null && !billingScheduleDetailVOs.isEmpty()){
				for(BillingScheduleDetailsVO billingScheduleDetailsVO:billingScheduleDetailVOs){
					GeneratePASSFilterVO passFilterVO =getPASSFilterVO(billingScheduleDetailsVO);
			    	generatePASSFile(passFilterVO);
				}
		}
		

	}
	
	public FileGenerateVO getFileGenerateVO(GPAInvoiceVO invoiceVO,LogonAttributes logonAttributes,GeneratePASSFilterVO passFilterVO) throws SystemException{

		FileGenerateVO fileGenerateVO=new FileGenerateVO();
    	FileGenerateConfigVO fileGenerateConfigVO=getFileGenerateConfigVO(invoiceVO.getCompanyCode(),MRA_GPA_PASS_FILTYP,invoiceVO, logonAttributes,passFilterVO);
    	LocalDate uploadTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
    	fileGenerateVO.setStatus(FileGenerateVO.GENERATE_STATUS_IN_PROGRESS);
    	fileGenerateVO.setAppliedConfiguration(fileGenerateConfigVO);
    	fileGenerateVO.setFileType(fileGenerateConfigVO.getFileType());
    	fileGenerateVO.setCompanyCode(invoiceVO.getCompanyCode());
    	fileGenerateVO.setStationCode(logonAttributes.getStationCode());
    	fileGenerateVO.setReconstructConfig(true);
    	fileGenerateVO.setUploadStartTime(uploadTime);
    	fileGenerateVO.setUploadEndTime(uploadTime);
    	fileGenerateVO.setLastUpdatedTime(uploadTime);
    	fileGenerateVO.setLastUpdatedUser(logonAttributes.getUserId());
    	return fileGenerateVO;
	}
	/**
	 * 
	 * 	Method		:	GPABillingController.updatePASSFileName
	 *	Added by 	:	A-8061 on 20-May-2021
	 * 	Used for 	:
	 *	Parameters	:	@param fileGenerateVO
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void updatePASSFileName(FileGenerateVO fileGenerateVO,GPAInvoiceVO invoiceVO,GeneratePASSFilterVO passFilterVO) throws SystemException {
		
		Collection<CN51SummaryVO> invoiceList=null;
				if(COMPLETED.equals(fileGenerateVO.getStatus())){


					CN51SummaryFilterVO summaryFilterVO = new CN51SummaryFilterVO();
					summaryFilterVO.setCompanyCode(invoiceVO.getCompanyCode());
					summaryFilterVO.setGpaCode(passFilterVO.getGpaCode());
					summaryFilterVO.setPeriodNumber(invoiceVO.getPeriodNumber());
					summaryFilterVO.setInvoiceStatus(MRAConstantsVO.INV_STA_NEW);
					summaryFilterVO.setPASS(true);
					summaryFilterVO.setAddNew(passFilterVO.isAddNew());
					summaryFilterVO.setBranchOffice(invoiceVO.getBranchOffice());
					summaryFilterVO.setPassFileName(passFilterVO.getFileName());

					invoiceList =CN51Summary.findAllInvoicesForPASSFileUpdate(summaryFilterVO);

					CN51Summary.updateInterfaceDetails(fileGenerateVO,invoiceList);

				}

		
	}

	private boolean isPASSAlreadyGenerated(GeneratePASSFilterVO passFilterVO) throws SystemException{
		
		boolean isPASSAlreadyGenerated=false;
		Collection<CN51SummaryVO> invoiceList=null;
		CN51SummaryFilterVO summaryFilterVO = new CN51SummaryFilterVO();
		summaryFilterVO.setCompanyCode(passFilterVO.getCompanyCode());
		summaryFilterVO.setPeriodNumber(passFilterVO.getPeriodNumber());
		summaryFilterVO.setInvoiceStatus(MRAConstantsVO.INV_STA_NEW);
		summaryFilterVO.setFetchAllResult(true);
		invoiceList =CN51Summary.findAllInvoices(summaryFilterVO);
		
		if(invoiceList!=null && !invoiceList.isEmpty()){
			for(CN51SummaryVO summaryVO : invoiceList){
				if(summaryVO.getPassFileName()!=null){
					return true;
				}
			}
		}

		return isPASSAlreadyGenerated;
	}
	
	
	/**
	 * 	Method		:	GPABillingController.getSequenceNumberforPASSFile
	 *	Added by 	:	A-4809 on 16-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 * @throws SystemException 
	 */
	private String getSequenceNumberforPASSFile(GPAInvoiceVO invoiceVO,boolean isPASSAlreadyGen) throws SystemException {
		log.entering(CLASS_NAME, "getSequenceNumberforPASSFile");	
		if(isPASSAlreadyGen && invoiceVO.getInterfacedFileName()==null){
			invoiceVO.setSequenceNumberIncrBy(2);
		}else{
			invoiceVO.setSequenceNumberIncrBy(1);
		}
		return CN51Summary.getSequenceNumberforPASSFile(invoiceVO);
	}

    public Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws SystemException {
		log.entering(CLASS_NAME, "findPASSFileNames");
		return CN51Summary.findPASSFileNames(fileNameLovVO);
	}
	
	
	/**
	 *
	 * 	Method		:	GPABillingController.processSettlementBatchDetails
	 *	Added by 	:	A-5219 on 08-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param gpaSettlementVOs
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	void
	 */
	public String processSettlementBatchDetails(Collection<InvoiceSettlementVO> gpaSettlementVOs)
		    throws MailTrackingMRABusinessException, SystemException{
		String processStatus="";
		if(gpaSettlementVOs!=null){
			Collection<GPASettlementVO> settlementVOs = constructInvoiceSettlementVOforBatch(gpaSettlementVOs);
			
				for (GPASettlementVO settlementVO : settlementVOs) {
					Collection<GPASettlementVO> gpaVOs = new ArrayList<>();
					gpaVOs.add(settlementVO);
					log.log(Log.INFO, "Settlement VO's", gpaVOs);
				try {
						  saveSettlementsAtMailbagLevelExcelUpload(gpaVOs); 
					processStatus = MailConstantsVO.OK_STATUS;
				} catch (SystemException | MailTrackingMRABusinessException e) {
					log.log(Log.SEVERE, e);
					  processStatus = "";
					  } 
				}
			
		}
		return processStatus;

	}

/**
 * 	Method		:	GPABillingController.constructInvoiceSettlementVOforBatch
 *	Added by 	:	A-4809 on 20-Dec-2021
 * 	Used for 	:
 *	Parameters	:	@param invoiceSettlementVOs
 *	Parameters	:	@return 
 *	Return type	: 	Collection<GPASettlementVO>
 */
	private Collection<GPASettlementVO> constructInvoiceSettlementVOforBatch(
			Collection<InvoiceSettlementVO> invoiceSettlementVOs) {
		Collection<GPASettlementVO> gpasettlementVOs = new HashSet<>();
		GPASettlementVO gpasettlementVO = null;
		Collection<InvoiceSettlementVO> invDetailsVOs = null;
		Collection<SettlementDetailsVO> settlementDetailsVOs = null;
		int i = 0;
		String currentKey;
		String prevKey = "";
		for (InvoiceSettlementVO invoiceVO : invoiceSettlementVOs) {
			currentKey = new StringBuilder(invoiceVO.getInvoiceNumber()).append(invoiceVO.getSettlementId()).toString();
			if (!currentKey.equals(prevKey)) {
				SettlementDetailsVO settlementDetailsVO = null; 
				settlementDetailsVOs = new ArrayList<>();
				InvoiceSettlementVO vo = null;
				invDetailsVOs = new ArrayList<>();
				gpasettlementVO = new GPASettlementVO();
				i = 0;
				settlementDetailsVO = populateSettlementDetailsVO(invoiceVO);
				settlementDetailsVO.setIndex(i);
				settlementDetailsVOs.add(settlementDetailsVO);
				vo = populateInvoiceSettlementVO(invoiceVO);
				vo.setIndex(i);
				invDetailsVOs.add(vo);
				gpasettlementVO.setUpdateFlag("Y");
				gpasettlementVO.setCompanyCode(invoiceVO.getCompanyCode());
				gpasettlementVO.setGpaCode(invoiceVO.getGpaCode());
				gpasettlementVO.setSettlementCurrency(invoiceVO.getSettlementCurrencyCode());
				gpasettlementVO.setFrmScreen("EXCELUPLOAD");
				gpasettlementVO.setSettlementChequeNumber("0000");
				gpasettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
				gpasettlementVO.setSettlementId(invoiceVO.getSettlementId());
				gpasettlementVO.setFromBatchSettlmentJob(true);
				gpasettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);
				gpasettlementVO.setInvoiceSettlementVOs(invDetailsVOs);
			} else {
				SettlementDetailsVO settlementDetailsVO = null;
				settlementDetailsVO = populateSettlementDetailsVO(invoiceVO);
				settlementDetailsVO.setIndex(i);
				if(settlementDetailsVOs!=null) {
				settlementDetailsVOs.add(settlementDetailsVO);
				}
				InvoiceSettlementVO vo = null;
				vo = populateInvoiceSettlementVO(invoiceVO);
				vo.setIndex(i);
				if(invDetailsVOs!=null) {
				invDetailsVOs.add(vo);
				}
				if(gpasettlementVO!=null) {
				gpasettlementVO.setSettlementDetailsVOs(settlementDetailsVOs);
				gpasettlementVO.setInvoiceSettlementVOs(invDetailsVOs);
				}
			}
			prevKey = currentKey;
			gpasettlementVOs.add(gpasettlementVO);
			i++;
		}
		return gpasettlementVOs;
	}
/**
 * 
 * 	Method		:	GPABillingController.populateSettlementDetailsVO
 *	Added by 	:	A-4809 on 21-Dec-2021
 * 	Used for 	:
 *	Parameters	:	@param invoiceVO
 *	Parameters	:	@return 
 *	Return type	: 	SettlementDetailsVO
 */
private SettlementDetailsVO populateSettlementDetailsVO(InvoiceSettlementVO invoiceVO) {
	SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();
	LocalDate currentTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
	settlementDetailsVO.setChequeAmount(invoiceVO.getSettlemetAmt());
	settlementDetailsVO.setChequeDate(currentTime);
	String txnRemarks = null;
	txnRemarks = new StringBuilder("Uploaded as Excel").append(invoiceVO.getSettlementFileName())
			.toString();
	settlementDetailsVO.setRemarks(txnRemarks);
	settlementDetailsVO.setChequeNumber("0000");
	settlementDetailsVO.setCompanyCode(invoiceVO.getCompanyCode());
	settlementDetailsVO.setGpaCode(invoiceVO.getGpaCode());
	settlementDetailsVO.setChequeCurrency(invoiceVO.getSettlementCurrencyCode());
	settlementDetailsVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
	return settlementDetailsVO;
}
	/**
	 * 	Method		:	GPABillingController.populateInvoiceSettlementVO
	 *	Added by 	:	A-4809 on 20-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return 
	 *	Return type	: 	InvoiceSettlementVO
	 */
	private InvoiceSettlementVO populateInvoiceSettlementVO(InvoiceSettlementVO invoiceVO) {
		InvoiceSettlementVO vo = new InvoiceSettlementVO();
		vo.setCompanyCode(invoiceVO.getCompanyCode());
		vo.setGpaCode(invoiceVO.getGpaCode());
		vo.setContractCurrencyCode(invoiceVO.getContractCurrencyCode());
		vo.setDestnCode(invoiceVO.getDestnCode());
		vo.setSettlementFileName(invoiceVO.getSettlementFileName());
		vo.setSettlementFileType(invoiceVO.getSettlementFileType());
		vo.setFlownSector(invoiceVO.getFlownSector());
		vo.setInvoiceNumber(invoiceVO.getInvoiceNumber());
		vo.setActualBilled(invoiceVO.getActualBilled());
		vo.setMailCharge(invoiceVO.getMailCharge());
		vo.setNetAmount(invoiceVO.getNetAmount());
		vo.setSettlemetAmt(invoiceVO.getSettlemetAmt());
		vo.setSurCharge(invoiceVO.getSurCharge());
		vo.setAmountAlreadySettled(invoiceVO.getAmountAlreadySettled());
		vo.setDueAmount(invoiceVO.getDueAmount());
		vo.setMcaNumber(invoiceVO.getMcaNumber());
		vo.setMailRate(invoiceVO.getMailRate());
		vo.setMailsequenceNum(invoiceVO.getMailsequenceNum());
		vo.setSettlementCurrencyCode(invoiceVO.getSettlementCurrencyCode());
		vo.setRemarks(invoiceVO.getRemarks());
		vo.setTax(invoiceVO.getTax());
		vo.setMailbagID(invoiceVO.getMailbagID());
		vo.setProcessIdentifier(invoiceVO.getProcessIdentifier());
		vo.setSummaryInvoiceNumber(invoiceVO.getSummaryInvoiceNumber());
		vo.setSummaryGpa(invoiceVO.getSummaryGpa());
		vo.setInvSerialNumber(invoiceVO.getInvSerialNumber());
		vo.setBillingCurrencyCode(invoiceVO.getBillingCurrencyCode());
		vo.setTolerancePercentage(invoiceVO.getTolerancePercentage());
		vo.setSettlementValue(invoiceVO.getSettlementValue());
		vo.setSettlementMaxValue(invoiceVO.getSettlementMaxValue());
		vo.setSettlementLevel(invoiceVO.getSettlementLevel());
		return vo;
	}  
  /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    
    
    
    public Map<String, Object> generateCN51ReportSQ(ReportSpec reportSpec)
    		throws SystemException
    {
    	log.log(Log.INFO, "generateCN51ReportSQ");
    	Collection<CN51DetailsVO> cn51DetailsVos =null;
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
		.getFilterValues().iterator().next();
		cn51DetailsVos = CN51Summary.generateCN51Report(cn51CN66FilterVO);
	String overrideRounding =null;
	Map<String,String> systemParameterMap =null;
	Collection<String> systemParCodes = new ArrayList<>();
	systemParCodes.add(OVERRIDEROUNDING);
	Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
	Collection<String> oneTimeActiveStatusList = new ArrayList<>();
	oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
	try {
		oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
				cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
	} catch (ProxyException proxyException) {
		log.log(Log.INFO, proxyException);
		throw new SystemException(proxyException.getMessage());
	}
	Collection<OneTimeVO> billingStatus = new ArrayList<>();
	if (oneTimeHashMap != null) {
		billingStatus = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		log.log(Log.INFO, "billingStatus1234++", billingStatus);
	}
	if(systemParameterMap !=null && systemParameterMap.size()>0){
		overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
	}
	
	LogonAttributes logon =ContextUtils.getSecurityContext()
	.getLogonAttributesVO();
	Collection<CN51DetailsVO> updatedCN51DetailsVO = new ArrayList<>();
	if (cn51DetailsVos != null && !(cn51DetailsVos.isEmpty())) {
		for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : billingStatus) {
				if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
					cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
				}
			}
			cn51VO.setMonthFlag("C");
			cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
			cn51VO.setTotalNetAmount(cn51VO.getTotalAmount().getAmount());//Added for ICRD-107524
			cn51VO.setAirlineCode(logon.getOwnAirlineCode());
			updatedCN51DetailsVO.add(cn51VO);
		}
	}
		reportmetadatacn51SQ(reportSpec, cn51CN66FilterVO, overrideRounding, updatedCN51DetailsVO);
		return ReportAgent.generateReport(reportSpec);
    }

	private void reportmetadatacn51SQ(ReportSpec reportSpec, CN51CN66FilterVO cn51CN66FilterVO, String overrideRounding,
			Collection<CN51DetailsVO> updatedCN51DetailsVO) {
		ReportMetaData parameterMetaDatas = new ReportMetaData();
		parameterMetaDatas.setFieldNames(new String[] { "airlineCode"});
		reportSpec.addParameterMetaData(parameterMetaDatas);
		reportSpec.addParameter(cn51CN66FilterVO);
		log.log(Log.INFO, "report meta data");
		ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
		reportMetaDataForCN51.setColumnNames(new String[] {
				SECTOR,MALCTGCOD,TOTWGT,APLRAT,VATAMT,VALCHG,NETAMT,MALSUBCLS,
				"MONTHFLAG","TOTAMTLC",TOTAMTCP,INVNUM,"POACOD","BILLFRM","NETTOTAL","RATE","POAADR","INVDAT","POANAM","MALCHGAMT","NET","SRVTAX",
				BLGCURCOD,CTRCURCOD,"TOTAMTCTRCURCOD","TOTAMTBLGCURCOD",SURCHARGE,VATNUM
		});
		if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
		reportMetaDataForCN51.setFieldNames(new String[] {
				SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,SERVICE_TAX,VALCHARGES,
				TOTAL_NET_AMOUNT,MAILSUBCLASS,MONTH_FLAG,TOTALAMOUNTLC,TOTALAMOUNTCP,INVNUMFIN,GPACODE,
				AIRLINE_CODE,TOTAL_BILLED_AMOUNT,APPLICABLERATE,POA_ADDRESS,INVDATEMMM,PPOANAM,AMOUNT,NET_AMOUNT,
				SERVICE_TAX,BILLINGCURRENCYCODE,C51SMYCRTCURCOD,SCALAR_TOTAL_BILLED_AMOUNT,C51SMYTOTBLDAMT,SUR_CHARGE,VAT,
		});
		}else{
			reportMetaDataForCN51.setFieldNames(new String[] {
					SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,SERVICE_TAX,VALCHARGES,
					TOTAL_NET_AMOUNT,MAILSUBCLASS,MONTH_FLAG,TOTALAMOUNTLC,TOTALAMOUNTCP,INVNUMFIN,GPACODE,
					AIRLINE_CODE,TOTAL_BILLED_AMOUNT,APPLICABLERATE,POA_ADDRESS,INVDATEMMM,PPOANAM,AMOUNT,NET_AMOUNT,
					SERVICE_TAX,BILLINGCURRENCYCODE,C51SMYCRTCURCOD,SCALAR_TOTAL_BILLED_AMOUNT,C51SMYTOTBLDAMT,SUR_CHARGE,VAT,
			});
		}
		reportSpec.setReportMetaData(reportMetaDataForCN51);
		reportSpec.setData(updatedCN51DetailsVO);
	} 
  /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String, Object> generateGPAInvoiceReportSQ(ReportSpec reportSpec)
    		throws SystemException, MailTrackingMRABusinessException {
    	
    	log.entering("GPABillingController", "generateCN66Report");
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
				.getFilterValues().iterator().next();
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
				.generateCN66Report(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Map<String,String> systemParameterMap =null;
		String dsnLevelImport=null;
		String billingReportLevel=null;
		String prevKey =null;
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<>();
		systemParCodes.add(DSNLEVELIMPORT);
		systemParCodes.add(BILLINGRPTLEVEL);
		systemParCodes.add(OVERRIDEROUNDING);
		systemParCodes.add(SHARED_AIRLINE_BASECURRENCY);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusList.add(WEIGHT_UNIT_ONETIME);
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);

			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			log.log(Log.INFO, proxyException);
			throw new SystemException(proxyException.getMessage());
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0){
			dsnLevelImport =systemParameterMap.get(DSNLEVELIMPORT);
			billingReportLevel = systemParameterMap.get(BILLINGRPTLEVEL);
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
		}
		vorateCalculationsSQ(cn66DetailsVos, dsnLevelImport, billingReportLevel, prevKey, overrideRounding);
				currencycalculationSQ(cn51CN66FilterVO, cn66DetailsVos, overrideRounding);
		voErrorAndWeight(cn66DetailsVos, oneTimeHashMap);

		log.log(Log.INFO, " <-- inside generateGPAInvoiceReportSQ the cn66DetailsVos is --> \n\n ",
				cn66DetailsVos);
		reportmetasq(reportSpec, cn51CN66FilterVO, cn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);
    	}
    /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
	private void voErrorAndWeight(Collection<CN66DetailsVO> cn66DetailsVos,
			Map<String, Collection<OneTimeVO>> oneTimeHashMap) throws MailTrackingMRABusinessException {
		if (cn66DetailsVos == null ||cn66DetailsVos.isEmpty()) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO("mailtracking.mra.gpabilling.cn51cn66.Tabnoresultsfound");
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
         if(cn66DetailsVos.isEmpty()){
        	for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
		  	  weightunitsq(oneTimeHashMap, cN66DetailsVO);
		    }
         }
	}

	private void weightunitsq(Map<String, Collection<OneTimeVO>> oneTimeHashMap, CN66DetailsVO cN66DetailsVO) {
		if (cN66DetailsVO.getUnitcode() != null  && oneTimeHashMap != null && !oneTimeHashMap.isEmpty() && oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeHashMap.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
			 for (OneTimeVO oneTimeVO : oneTimeHashMap.get(WEIGHT_UNIT_ONETIME)) {
				if (oneTimeVO.getFieldValue().equals(cN66DetailsVO.getUnitcode()) ) {
					cN66DetailsVO.setUnitcode(oneTimeVO.getFieldDescription());
				}
			 }
		  }
	}
	 /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
	private String currencycalculationSQ(CN51CN66FilterVO cn51CN66FilterVO, Collection<CN66DetailsVO> cn66DetailsVos,
			String overrideRounding) throws SystemException {
		if(cn66DetailsVos != null && !cn66DetailsVos.isEmpty() && overrideRounding!=null && overrideRounding.trim().length()>0
				&& CN51CN66FilterVO.FLAG_NO.equals(overrideRounding))
		{
			String currency=null;
			for(CN66DetailsVO cN66DetailsVO : cn66DetailsVos){
				if(cN66DetailsVO.getCurrencyCode()!=null)
				{
					currency=cN66DetailsVO.getCurrencyCode();
					break;
				}
			}
			try {
				overrideRounding = currencyvalidateSQ(cn51CN66FilterVO, cn66DetailsVos, currency);
			} catch (ProxyException e) {
				log.log(Log.INFO, e);
				throw new SystemException(e.getMessage());
			}
		}
		return overrideRounding;
	}

	private String currencyvalidateSQ(CN51CN66FilterVO cn51CN66FilterVO, Collection<CN66DetailsVO> cn66DetailsVos,
			String currency) throws ProxyException, SystemException {
		String overrideRounding;
		try{
		CurrencyValidationVO currencyValidationVO;
		String roundingUnit=null;
		currencyValidationVO = Proxy.getInstance().get(SharedCurrencyProxy.class).validateCurrency(cn51CN66FilterVO.getCompanyCode(),currency);
		if(currencyValidationVO.getRoundingUnit() >= 1){
			int roundingUnitInt = (int) currencyValidationVO.getRoundingUnit();
			roundingUnit = String.valueOf(roundingUnitInt);
		}else{
			roundingUnit =String.valueOf(currencyValidationVO.getRoundingUnit());
		}
		int integerPlaces = roundingUnit.indexOf('.');
		if(integerPlaces != -1){
			int decimalPlaces = roundingUnit.length() - integerPlaces - 1;
			overrideRounding = String.valueOf(decimalPlaces);
		}else{
			overrideRounding = "0";
		}
		for(CN66DetailsVO cN66DetailsVO : cn66DetailsVos){
			cN66DetailsVO.setOverrideRounding(overrideRounding);
		}}
		catch (ProxyException e) {
			log.log(Log.INFO, e);
			throw new SystemException(e.getMessage());
		}
		return overrideRounding;
	}
	 /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
	
	private void vorateCalculationsSQ(Collection<CN66DetailsVO> cn66DetailsVos, String dsnLevelImport,
			String billingReportLevel, String prevKey, String overrideRounding) {
		String curKey;
		ArrayList<CN66DetailsVO> cn66DetailsVOs = new ArrayList<>();
		if (cn66DetailsVos != null && !cn66DetailsVos.isEmpty()) {
			for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				curKey = new StringBuilder().append(cN66DetailsVO.getCompanyCode()).
						append(cN66DetailsVO.getInvoiceNumber()).append(cN66DetailsVO.getDsn()).toString();
				 if(cN66DetailsVO.getFlightDate() != null) {
					 String flightDateInString = TimeConvertor.toStringFormat(cN66DetailsVO.getFlightDate().toCalendar(),"dd-MMM-yy");
					 cN66DetailsVO.setFlightDateInString(flightDateInString);
				 }
				if(CN66DetailsVO.FLAG_YES.equals(dsnLevelImport)){
					cn66DetailsVOs.add(cN66DetailsVO);
				}
				else if(CN66DetailsVO.FLAG_NO.equals(dsnLevelImport))
				{
					prevKey = billingreportlevelsq(billingReportLevel, prevKey, curKey, cn66DetailsVOs, cN66DetailsVO);
				}
				cN66DetailsVO.setOverrideRounding(overrideRounding);
			}		}	}

	private String billingreportlevelsq(String billingReportLevel, String prevKey, String curKey,
			ArrayList<CN66DetailsVO> cn66DetailsVOs, CN66DetailsVO cN66DetailsVO) {
		if("M".equals(billingReportLevel)){
			cn66DetailsVOs.add(cN66DetailsVO);
		}else if("D".equalsIgnoreCase(billingReportLevel)){
			if(!curKey.equals(prevKey)){
				prevKey = curKey;
				cN66DetailsVO.setRsn("");
				cN66DetailsVO.setRegInd("");
				cN66DetailsVO.setHsn("");
				cn66DetailsVOs.add(cN66DetailsVO);
			}else{
				prevKey = weightcalculationsSQ(curKey, cn66DetailsVOs, cN66DetailsVO);
			}
		}
		return prevKey;
	}
	 /**
     * @author A-10383
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */

	private String weightcalculationsSQ(String curKey, ArrayList<CN66DetailsVO> cn66DetailsVOs,
			CN66DetailsVO cN66DetailsVO) {
		double amount=0;
		double serviceTax=0;

		double netAmount=0;
		double totalWeight=0;
		int count =0;
		String prevKey;
		CN66DetailsVO cN66DetailsVOforRpt;
		prevKey = curKey;
		count = cn66DetailsVOs.size();
		cN66DetailsVOforRpt = cn66DetailsVOs.get(count-1);
		totalWeight = cN66DetailsVOforRpt.getTotalWeight()+cN66DetailsVO.getTotalWeight();
		amount = cN66DetailsVOforRpt.getAmount()+cN66DetailsVO.getAmount();
		serviceTax = cN66DetailsVOforRpt.getServiceTax()+cN66DetailsVO.getServiceTax();
		netAmount = cN66DetailsVOforRpt.getNetAmount().getAmount()+cN66DetailsVO.getNetAmount().getAmount();
		cN66DetailsVOforRpt.setTotalWeight(totalWeight);
		cN66DetailsVOforRpt.setAmount(amount);
		cN66DetailsVOforRpt.setServiceTax(serviceTax);
		cN66DetailsVOforRpt.getNetAmount().setAmount(netAmount);
		return prevKey;
	}
/**
 * @author A-10383
 * @param reportSpec
 * @param cn51CN66FilterVO
 * @param cn66DetailsVos
 * @param overrideRounding
 */
	private void reportmetasq(ReportSpec reportSpec, CN51CN66FilterVO cn51CN66FilterVO,
			Collection<CN66DetailsVO> cn66DetailsVos) {
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "airlineCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cn51CN66FilterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] {ORGCOD,DSTCOD,MALCTGCOD,DSN,CNTIDR,FLTNUM,FLTDAT,SECTOR,MALSUBCLS,TOTWGT,BLDAMT,TOTAMTCP,
				SRVTAX,NETAMT,CURCOD,BLDPRD,PONAM,POAADR,INVDAT,GPA,INVNUM,RATE,MCA,TOTAL,"NETAMTDOUBLE","C51SMYCTRCURCOD","TOTNETBLGCUR","TOTNETCTRCUR",VATNUM});	
		
			reportMetaData.setFieldNames(new String[] {"cn66orgcod", "cn66dstcod", "cn66malctgcod", "cn66dsn", "consDocNo", "fltnum", "fltdat", SECTOR1, "cn66malsubcls", "cn66totwgt", "bldamt", "totamtcp", "srvtax", "netamt", "c51smyblgcurcod","bldprd", "ponam", "poaadr", INVDATEMMM, "cn66gpacod", INVNUMFIN,"rate","mca","total","netamtdouble","c51smyctrcurcod","totnetblgcur","totnetctrcur",VAT});
			
		
		reportSpec.setReportMetaData(reportMetaData);

		reportSpec.setData(cn66DetailsVos);
	}
	/**
	 * method for calling up the DAO for the submodule
	 * @author a-10383
	 * @return queryDAO
	 * @throws SystemException
	 */
	private static MRAGPABillingDAO constructDAO()
	throws SystemException {
		MRAGPABillingDAO queryDAO =null;
		try {
			queryDAO = (MRAGPABillingDAO)PersistenceController
			.getEntityManager()
			.getQueryDAO(MODULE_NAME);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}
		return queryDAO;
	}
	/**
	 * @author A-10383
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException 
	 */
	private InvoiceDetailsReportVO generateInvoiceReportSqlDaoSQ(CN51CN66FilterVO cN51CN66FilterVO)
			throws SystemException, PersistenceException{
					return constructDAO().generateInvoiceReportSQ(cN51CN66FilterVO);
			}
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<CN51DetailsVO> generateCN51Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateCN51Report(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<CN66DetailsVO> generateCN66Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws SystemException{
		try{
			return constructDAO().generateCN66Report(cN51CN66FilterVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}
	private  AirlineVO findAirlineAddress(String companyCode, int airlineIdentifier)  throws SystemException{
		try {
			return constructDAO().findAirlineAddress(companyCode,airlineIdentifier);	
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		} 
	}
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException 
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCoverPageSQ(ReportSpec reportSpec)throws SystemException, PersistenceException
	{
	log.log(Log.INFO, " inside generateCoverPageSQ");
	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
	String overrideRounding =null;
	Collection<String> systemParCodes = new ArrayList<>();
	Map<String,String> systemParameterMap =null;
	systemParCodes.add(OVERRIDEROUNDING);
	systemParameterMap = findSystemParameterByCodes(systemParCodes);
	if(systemParameterMap !=null && systemParameterMap.size()>0)
	{
		overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
	} 
	CN51CN66FilterVO cN51CN66Filtervo = (CN51CN66FilterVO) reportSpec.getFilterValues().iterator().next();	
	 InvoiceDetailsReportVO invoiceDetailsReportVO = generateInvoiceReportSqlDaoSQ(cN51CN66Filtervo);
	coverPageSetData(reportSpec, logon, overrideRounding, invoiceDetailsReportVO, cN51CN66Filtervo);
	return ReportAgentInstance.getInstance().generateReport(reportSpec);
	}
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @throws PersistenceException 
	 */
	public Map<String, Object> generateGPAInvoiceReportPrintAllSQ(ReportSpec reportSpec)throws SystemException, MailTrackingMRABusinessException, PersistenceException
	{
		log.log(Log.INFO, " inside generateGPAInvoiceReportPrintAllSQ");
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<>();
		Map<String,String> systemParameterMap =null;
		systemParCodes.add(OVERRIDEROUNDING);
		systemParameterMap = findSystemParameterByCodes(systemParCodes);
		if(systemParameterMap !=null && systemParameterMap.size()>0)
		{
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
		}
		CN51CN66FilterVO cN51CN66Filtervo = (CN51CN66FilterVO) reportSpec.getFilterValues().iterator().next();	
		
		InvoiceDetailsReportVO invoiceDetailsReportVO = generateInvoiceReportSqlDaoSQ(cN51CN66Filtervo);
		coverPageSetData(reportSpec, logon, overrideRounding, invoiceDetailsReportVO, cN51CN66Filtervo);
		
		log.entering(CLASS_NAME, "generateCN51Report");
		Collection<CN51DetailsVO> cn51DetailsVos =null;
		cn51DetailsVos = generateCN51Report(cN51CN66Filtervo);
	Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
	Collection<String> oneTimeActiveStatusList = new ArrayList<>();
	oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
	try {
		oneTimeHashMap =Proxy.getInstance().get(SharedDefaultsProxy.class).findOneTimeValues(
				cN51CN66Filtervo.getCompanyCode(), oneTimeActiveStatusList);
	} catch (ProxyException proxyException) {
		log.log(Log.INFO, proxyException);
		throw new SystemException(proxyException.getMessage());
	}
	Collection<OneTimeVO> billingStatus = new ArrayList<>();
	if (oneTimeHashMap != null) {
		billingStatus = oneTimeHashMap.get(KEY_CATEGORY_ONETIME);
		log.log(Log.INFO, STATUS_BILING, billingStatus);
	}
	Collection<CN51DetailsVO> updatedCN51DetailsVO = setUpdatedCN51DetailsVO(logon, cn51DetailsVos, billingStatus);
	reportSpec.addExtraInfo(cN51CN66Filtervo);
	reportSpec.addSubReportParameter(cN51CN66Filtervo);
	ReportMetaData reportMetaDataForCN51 = cn51SetmetaData(reportSpec, overrideRounding, cN51CN66Filtervo);
	reportSpec.addSubReportMetaData(reportMetaDataForCN51);
	reportSpec.addSubReportData(updatedCN51DetailsVO);
	
	log.entering(CLASS_NAME,CN66_REPORT);
	Collection<String> systemParCodesCN66 = new ArrayList<>();
	Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
	Collection<CN66DetailsVO> cn66DetailsVos = generateCN66Report(cN51CN66Filtervo);
	String dsnLevelImport=null;
	String billingReportLevel=null;
	String prevKey =null;
	systemParCodesCN66.add(DSNLEVELIMPORT);
	systemParCodesCN66.add(BILLINGRPTLEVEL);
	systemParCodesCN66.add(OVERRIDEROUNDING);
	systemParCodesCN66.add(SHARED_AIRLINE_BASECURRENCY);
	oneTimeActiveStatusListCn66.add(KEY_CATEGORY_ONETIME);
	oneTimeActiveStatusListCn66.add(WEIGHT_UNIT_ONETIME);
	Map<String,String> systemParameterMapCn66 =null;
	systemParameterMapCn66 = findSystemParameterByCodes(systemParCodesCN66);
	try {
		oneTimeHashMap =Proxy.getInstance().get(SharedDefaultsProxy.class).findOneTimeValues(
				cN51CN66Filtervo.getCompanyCode(), oneTimeActiveStatusListCn66);
	} catch (ProxyException proxyException) {
		log.log(Log.INFO, proxyException);
		throw new SystemException(proxyException.getMessage());
	}
	if(systemParameterMapCn66 !=null && systemParameterMapCn66.size()>0){
		dsnLevelImport =systemParameterMapCn66.get(DSNLEVELIMPORT);
		billingReportLevel = systemParameterMapCn66.get(BILLINGRPTLEVEL);
		overrideRounding = systemParameterMapCn66.get(OVERRIDEROUNDING);
	}
	vorateCalculationsSQ(cn66DetailsVos, dsnLevelImport, billingReportLevel, prevKey, overrideRounding);
	currencycalculationSQ(cN51CN66Filtervo, cn66DetailsVos, overrideRounding);
	voErrorAndWeight(cn66DetailsVos, oneTimeHashMap);
	cn66Setdata(reportSpec, cN51CN66Filtervo, cn66DetailsVos);
	return ReportAgentInstance.getInstance().generateReport(reportSpec);
	}
	private Collection<CN51DetailsVO> setUpdatedCN51DetailsVO(LogonAttributes logon,
			Collection<CN51DetailsVO> cn51DetailsVos, Collection<OneTimeVO> billingStatus) {
		Collection<CN51DetailsVO> updatedCN51DetailsVO = new ArrayList<>();
		if (cn51DetailsVos != null && !(cn51DetailsVos.isEmpty())) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
					for (OneTimeVO oneTimeVO : billingStatus) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setTotalNetAmount(cn51VO.getTotalAmount().getAmount());//Added for ICRD-107524
				cn51VO.setAirlineCode(logon.getOwnAirlineCode());
				updatedCN51DetailsVO.add(cn51VO);
			}
		}
		return updatedCN51DetailsVO;
	}
	private void cn66Setdata(ReportSpec reportSpec, CN51CN66FilterVO cN51CN66Filtervo,
			Collection<CN66DetailsVO> cn66DetailsVos) {
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { INVOICENUMBER,
				GPACODE, AIRLINE_CODE });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cN51CN66Filtervo);
		ReportMetaData reportMetaDataForCN66 = new ReportMetaData();
		reportMetaDataForCN66.setColumnNames(new String[] {ORGCOD,DSTCOD,MALCTGCOD,DSN,CNTIDR,FLTNUM,FLTDAT,SECTOR,MALSUBCLS,
				TOTWGT,BLDAMT,TOTAMTCP,	SRVTAX,NETAMT,CURCOD,BLDPRD,PONAM,POAADR,INVDAT,GPA,INVNUM,RATE,MCA,TOTAL,
				"NETAMTDOUBLE","C51SMYCTRCURCOD","TOTNETBLGCUR","TOTNETCTRCUR",VATNUM1});	
		reportMetaDataForCN66.setFieldNames(new String[] {"cn66orgcod", "cn66dstcod", "cn66malctgcod", "cn66dsn", "consDocNo", 
				"fltnum", "fltdat", SECTOR1, "cn66malsubcls", "cn66totwgt", "bldamt", "totamtcp", "srvtax", "netamt", 
				"c51smyblgcurcod","bldprd", "ponam", "poaadr", INVDATEMMM,"cn66gpacod", INVNUMFIN,"rate","mca",
				"total","netamtdouble","c51smyctrcurcod","totnetblgcur","totnetctrcur",VAT});
			reportSpec.addSubReportMetaData(reportMetaDataForCN66);
			reportSpec.addSubReportData(cn66DetailsVos);
	}
	private void coverPageSetData(ReportSpec reportSpec, LogonAttributes logon, String overrideRounding,
			InvoiceDetailsReportVO invoiceDetailsReportVO, CN51CN66FilterVO cN51CN66Filtervo) throws SystemException {
		AirlineVO airlineVO;
		invoiceDetailsReportVO.setOverrideRounding(overrideRounding);
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<>();
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		invoiceDetailsReportVO.setTotalAmtinBillingCurrString(setAmountAsString(invoiceDetailsReportVO.getTotalAmtinBillingCurr()));
		log.log(Log.INFO, "for vos returned ", invoiceDetailsReportVOs);
		log.log(Log.INFO, "for Airline Identifier--> ", logon.getOwnAirlineIdentifier());
		airlineVO = findAirlineAddress(cN51CN66Filtervo.getCompanyCode(),logon.getOwnAirlineIdentifier());
		if (airlineVO != null) 
		{
			ReportMetaData parameterMetaDatas = new ReportMetaData();
			if(invoiceDetailsReportVO.getAirlineAddress()!= null)
			{
				airlineVO.setBillingAddress(invoiceDetailsReportVO.getAirlineAddress());
			}
			if (airlineVO.getAirlineName() == null) 
			{
				airlineVO.setAirlineName("");
			}
			if (airlineVO.getBillingAddress() == null) 
			{
				airlineVO.setBillingAddress("");
			}
			if (airlineVO.getBillingPhone1() == null) 
			{
				airlineVO.setBillingPhone1("");
			}
			if (airlineVO.getBillingPhone2() == null) 
			{
				airlineVO.setBillingPhone2("");
			}
			if (airlineVO.getBillingFax() == null) 
			{
				airlineVO.setBillingFax("");
			}
			parameterMetaDatas.setFieldNames(new String[] { AIRLINENAME,BILLINGADDRESS, BILLINGPHONE1, BILLINGPHONE2,BILLINGFAX });
			reportSpec.addParameterMetaData(parameterMetaDatas);
			reportSpec.addParameter(airlineVO);
		}
		setReportMetaData(reportSpec,invoiceDetailsReportVO, invoiceDetailsReportVOs);
	}
	private ReportMetaData cn51SetmetaData(ReportSpec reportSpec, String overrideRounding,
			CN51CN66FilterVO cn51Cn66Filtervo) {
		ReportMetaData parameterMetaDatas = new ReportMetaData();
		parameterMetaDatas.setFieldNames(new String[] { AIRLINE_CODE});
		reportSpec.addParameterMetaData(parameterMetaDatas);
		reportSpec.addParameter(cn51Cn66Filtervo);
		log.log(Log.INFO, "report meta data");
		ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
		reportMetaDataForCN51.setColumnNames(new String[] {
				SECTOR,MALCTGCOD,TOTWGT,APLRAT,VATAMT,VALCHG,NETAMT,MALSUBCLS,
				FLAG_MONTH,TOTAMTLC,TOTAMTCP,INVNUM,POACOD,BILLFRM, NETTOTAL,"RATE",POAADR,INVDAT,POANAM,"MALCHGAMT","NET",SRVTAX,
						BLGCURCOD,CTRCURCOD,"TOTAMTCTRCURCOD","TOTAMTBLGCURCOD",SURCHARGE,VATNUM1
		});
		if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
		reportMetaDataForCN51.setFieldNames(new String[] {
				SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,SERVICE_TAX,VALCHARGES,
				TOTAL_NET_AMOUNT,MAILSUBCLASS,MONTH_FLAG,TOTALAMOUNTLC,TOTALAMOUNTCP,INVNUMFIN,GPACODE,
				AIRLINE_CODE,TOTAL_BILLED_AMOUNT,APPLICABLERATE,POA_ADDRESS,INVDATEMMM,PPOANAM,AMOUNT,NET_AMOUNT,
				SERVICE_TAX,BILLINGCURRENCYCODE,C51SMYCRTCURCOD,SCALAR_TOTAL_BILLED_AMOUNT,C51SMYTOTBLDAMT,SUR_CHARGE,VAT
		});
		}else{
			reportMetaDataForCN51.setFieldNames(new String[] {
					SECTOR1,MAILCATEGORYCODE,TOTALWEIGHT,APPLICABLERATE,SERVICE_TAX,VALCHARGES,
					TOTAL_NET_AMOUNT,MAILSUBCLASS,MONTH_FLAG,TOTALAMOUNTLC,TOTALAMOUNTCP,INVNUMFIN,GPACODE,
					AIRLINE_CODE,SCALAR_TOTAL_BILLED_AMOUNT,APPLICABLERATE,POA_ADDRESS,INVDATEMMM,PPOANAM,AMOUNT,NET_AMOUNT,
					SERVICE_TAX,BILLINGCURRENCYCODE,C51SMYCRTCURCOD,SCALAR_TOTAL_BILLED_AMOUNT,C51SMYTOTBLDAMT,SUR_CHARGE,VAT
			});
		}
		return reportMetaDataForCN51;  
 
	}
}
