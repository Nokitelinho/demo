/*
 * MailTrackingMRADelegate.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.delegate.mail.mra;


import java.rmi.RemoteException;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicKeyLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ReportingPeriodFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingStatusVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;

/**
 * @author A-1556
 *
 */

/**
 * Revision History
 *
 * Version    Date          Author                     Description
 *
 * 0.1 Jan 8, 2007 Philip Initial draft
 *  0.2 Jan 18,2007 Kiran Added the method findAllInvoices
 * 0.3 Jan 18,2007 Prem Kumar.M Added the methods findCN51CN66Details,saveCN66Observations
 * 0.4 Feb 16,2007 Sarika Added methods for flown
 * 0.5 Feb 23 ,2007 Sandeep Added the method processGpaReport for GpaReporting
 * 0.6 Mar 26,2007  Prem Kumar.M added findSettlementDetails method
 * 0.7 Mar 27,2007	Prem Kumar.M	Added find SettlementHistory method
 *
 *
 *
 */
@Module("mail")
@SubModule("mra")
public class MailTrackingMRADelegate extends
com.ibsplc.xibase.client.framework.delegate.BusinessDelegate {

	private static final String MODULE_NAME = "MAILTRACKING MRA";

	private Log log = LogFactory.getLogger(MODULE_NAME);

	private static final String CLASS_NAME = "MailTrackingMRADelegate";
	private static final String CLEAR_BATCH_DETAILS="clearBatchDetails";

	/**
	 *
	 */
	public MailTrackingMRADelegate() {

	}

	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Page<CN51SummaryVO>
	 * @throws BusinessDelegateException
	 */
	public Page<CN51SummaryVO> findAllInvoices(
			CN51SummaryFilterVO cn51SummaryFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findAllInvoices");

		Page<CN51SummaryVO> cn51SummaryVOs = null;
		cn51SummaryVOs = this.despatchRequest("findAllInvoices",
				cn51SummaryFilterVO);
		log.log(Log.INFO, "@@ the results obtained @@ ", cn51SummaryVOs);
		log.exiting("MRAGPABillingDelegate", "findAllInvoices");
		return cn51SummaryVOs;
	}
	/**
	 * @author A-3434 Finds and returns  GpaBillingInvoice details
	 *
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws BusinessDelegateException
	 */
	public CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findGpaBillingInvoiceDetails");

		return despatchRequest("findGpaBillingInvoiceEnquiryDetails", gpaBillingInvoiceEnquiryFilterVO);
	}

	/**
	 * @author A-3434 .Save the updated status and remarks of invoices.
	 *
	 * @param cN66DetailsVO
	 *
	 * @throws BusinessDelegateException
	 */
	public void saveBillingStatus(CN66DetailsVO cN66DetailsVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeBillingStatus");

		despatchRequest("saveBillingStatus", cN66DetailsVO);
	}
	/**
	 * @author A-2280 Finds and returns the CN51 and CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return CN51CN66VO
	 * @throws BusinessDelegateException
	 */
	public CN51CN66VO findCN51CN66Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findCN51CN66Details");

		return despatchRequest("findCN51CN66Details", cn51CN66FilterVO);
	}

	/**
	 * @author A-2391 Finds tand returns the GPA Billing entries available This
	 *         includes billed, billable and on hold despatches
	 *
	 * @param gpaBillingEntriesFilterVO
	 * @return Page<DocumentBillingDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Page<DocumentBillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findGPABillingEntries");
		log.exiting("MRAGPABillingDelegate", "findGPABillingEntries");
		return despatchRequest("findGPABillingEntries",
				gpaBillingEntriesFilterVO);

	}

	/**
	 * @author A-2391 Changes the staus of GPA billing entries to Billable/On
	 *         Hold
	 *
	 * @param gpaBillingStatusVO
	 * @throws BusinessDelegateException
	 */
	public void changeBillingStatus(
			Collection<GPABillingStatusVO> gpaBillingStatusVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeBillingStatus");
		despatchRequest("changeBillingStatus", gpaBillingStatusVO);
		log.exiting("MRAGPABillingDelegate", "changeBillingStatus");

	}

	/**
	 * @author A-2280 Saves the remarks against CN66 details
	 *
	 * @param cn66DetailsVO
	 * @throws BusinessDelegateException
	 */
	public void saveCN66Observations(Collection<CN66DetailsVO> cn66DetailsVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveCN66Observations");
		despatchRequest("saveCN66Observations", cn66DetailsVOs);
		log.exiting("MRAGPABillingDelegate", "saveCN66Observations");

	}

	/**
	 * method for invoice generation
	 *
	 * @author a-2049
	 * @param generateInvoiceFilterVO
	 * @throws BusinessDelegateException
	 */
	/* Commented the method as part of ICRD-153078
	public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateInvoice");
		despatchRequest("generateInvoice", generateInvoiceFilterVO);
		log.exiting("MRAGPABillingDelegate", "generateInvoice");
	}*/

	/**
	 *
	 * @param invoiceLovVO
	 * @return Page<InvoiceLovVO>
	 * @throws BusinessDelegateException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO invoiceLovVO)
	throws BusinessDelegateException {

		return despatchRequest("findInvoiceLov", invoiceLovVO);
	}

	/**
	 * Saves the rate card and assosciated rate lines Used for the
	 * creation,updation of rate cards and creation,updation and deletion of
	 * rate lines based on the operation flag set in argument
	 *
	 * @author A-2408
	 * @param rateCardVO
	 * @throws BusinessDelegateException
	 */
	public void saveRateCard(RateCardVO rateCardVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveRateCard");
		despatchRequest("saveRateCard", rateCardVO);
	}


	/**
	 * Finds the rate card and assosciated rate lines
	 *
	 * @author A-2408
	 * @param companyCode
	 * @param rateCardID
	 * @return RateCardVO
	 * @throws BusinessDelegateException
	 *
	 */
	public RateCardVO findRateCardDetails(String companyCode, String rateCardID,int pagenum )
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findRateCardDetails");
		return despatchRequest("findRateCardDetails", companyCode, rateCardID,pagenum);
	}

	/**
	 * Returns the ratelines based on the filter criteria
	 *
	 * @author A-2408
	 * @param rateLineFilterVO
	 * @return Page<RateLineVO>
	 * @throws BusinessDelegateException
	 *
	 */
	public Page<RateLineVO> findRateLineDetails(
			RateLineFilterVO rateLineFilterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findRateLineDetails");
		return despatchRequest("findRateLineDetails", rateLineFilterVO);
	}

	/**
	 * Saves the rateLine Status
	 *
	 * @param rateLineVOs
	 * @throws BusinessDelegateException
	 *
	 */
	// TODO Collection<RateLineVO>
	public void saveRatelineStatus(Collection<RateLineVO> rateLineVOs)
	throws BusinessDelegateException {
		despatchRequest("saveRatelineStatus", rateLineVOs);
	}

	/**
	 * Finds the rate cards based on the filter
	 *
	 * @author a-2049
	 * @param rateCardFilterVO
	 * @return Page<RateCardVO>
	 * @throws BusinessDelegateException
	 *
	 */
	public Page<RateCardVO> findAllRateCards(RateCardFilterVO rateCardFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findAllRateCards");
		return this.despatchRequest("findAllRateCards", rateCardFilterVO);
	}

	/**
	 * method for changing the rateCard status from ViewRateCard Screen
	 *
	 * @author a-2049
	 * @param rateCardVOs
	 *            the collection of rateCardVOs with status being set with the
	 *            new status selected by the user
	 * @throws BusinessDelegateException
	 */
	public void changeRateCardStatus(Collection<RateCardVO> rateCardVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeRateCardStatus");
		this.despatchRequest("changeRateCardStatus", rateCardVOs);
	}

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param rateCardId
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<RateCardLovVO> findRateCardLov(String companyCode,
			String rateCardId, int pageNumber) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findRateCardLov");
		return this.despatchRequest("findRateCardLov", companyCode, rateCardId,
				pageNumber);
	}

	/**
	 * @author A-2259
	 * @param flownMailFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public FlownMailSegmentVO findFlownMails(FlownMailFilterVO flownMailFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findFlownMails");
		return this.despatchRequest("findFlownMails", flownMailFilterVO);
	}

	/**
	 * @author A-2259
	 * @param flownMailFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void closeFlight(FlownMailFilterVO flownMailFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "closeFlight");
		this.despatchRequest("closeFlight", flownMailFilterVO);
	}

	/**
	 * @author A-2259
	 * @param flownMailFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<FlownMailSegmentVO> findFlightDetails(
			FlownMailFilterVO flownMailFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findFlightDetails");
		return this.despatchRequest("findFlightDetails", flownMailFilterVO);
	}

	/**
	 * @author A-2259
	 * @param FlightFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateFlight");
		return this.despatchRequest("validateFlight", flightFilterVO);
	}

	/**
	 *
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "displayUPUCalendarDetails");
		return despatchRequest("displayUPUCalendarDetails", upuCalendarFilterVO);
	}

	/**
	 * method to save VOs for UPUCalendar screen
	 *
	 * @param upuCalendarVOs
	 * @throws BusinessDelegateException
	 */
	public void saveUPUCalendarDetails(Collection<UPUCalendarVO> upuCalendarVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveUPUCalendarDetails");
		despatchRequest("saveUPUCalendarDetails", upuCalendarVOs);
	}

	/**
	 * Method to list CN66 details
	 *
	 * @param cn66FilterVo
	 * @return
	 * @throws BusinessDelegateException
	 * @author A-2518
	 */
	public Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findCN66Details");
		return despatchRequest("findCN66Details", cn66FilterVo);
	}

	/**
	 * Method to save CN66 details
	 *
	 * @param cn66DetailsVos
	 * @throws BusinessDelegateException
	 * @author A-2518
	 */
	public void saveCN66Details(Collection<AirlineCN66DetailsVO> cn66DetailsVos)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveCN66Details");
		despatchRequest("saveCN66Details", cn66DetailsVos);
		log.exiting(CLASS_NAME, "saveCN66Details");
	}

	/**
	 * Method to list Airline Exception details
	 *
	 * @param airlineExceptionsFilterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @throws BusinessDelegateException
	 */
	public Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws BusinessDelegateException {

		log.entering(CLASS_NAME, "displayAirlineExceptions");
		return despatchRequest("displayAirlineExceptions",
				airlineExceptionsFilterVO);
	}

	/**
	 * Method to save Airline Exception details
	 *
	 * @param airlineExceptionsVOs
	 * @throws BusinessDelegateException
	 */
	public void saveAirlineExceptions(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveAirlineExceptions");
		despatchRequest("saveAirlineExceptions", airlineExceptionsVOs);
		log.exiting(CLASS_NAME, "saveAirlineExceptions");
	}

	/**
	 * A-2391 Method to list Airline Exception In Invoices
	 *
	 * @param exceptionInInvoiceFilterVO
	 * @return Page<ExceptionInInvoiceVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
			throws BusinessDelegateException {

		log.entering(CLASS_NAME, "findAirlineExceptionInInvoices");
		return despatchRequest("findAirlineExceptionInInvoices",
				exceptionInInvoiceFilterVO);
	}

	/**
	 * A-2391 Method to accept Airline Invoices
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws BusinessDelegateException
	 */
	public void acceptAirlineInvoices(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "acceptAirlineInvoices");
		despatchRequest("acceptAirlineInvoices", exceptionInInvoiceVOs);
		log.exiting(CLASS_NAME, "acceptAirlineInvoices");
	}

	/**
	 * A-2391 Method to saveRejectionMemo
	 * @return RejectionMemoVO
	 * @param exceptionInInvoiceVO
	 * @throws BusinessDelegateException
	 */
	public RejectionMemoVO saveRejectionMemo(
			ExceptionInInvoiceVO exceptionInInvoiceVO)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveRejectionMemo");
		return despatchRequest("saveRejectionMemo", exceptionInInvoiceVO);

	}

	/**
	 * A-2391 Method to deleteRejectionMemo
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws BusinessDelegateException
	 */
	public void deleteRejectionMemo(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "deleteRejectionMemo");
		despatchRequest("deleteRejectionMemo", exceptionInInvoiceVOs);
		log.exiting(CLASS_NAME, "deleteRejectionMemo");
	}

	/**
	 * A-2401 Method to findFlownMailExceptions
	 *
	 * @param FlownMailFilterVO
	 * @throws BusinessDelegateException
	 */
	public Collection<FlownMailExceptionVO> findFlownMailExceptions(
			FlownMailFilterVO flownMailFilterVO)
			throws BusinessDelegateException {

		log.entering(CLASS_NAME, "findFlownMailExceptions");
		return despatchRequest("findFlownMailExceptions", flownMailFilterVO);

	}

	/**
	 * A-2401 Method to assignFlownMailExceptions
	 *
	 * @param flownMailExceptionVOs
	 * @throws BusinessDelegateException
	 */
	public void assignFlownMailExceptions(
			Collection<FlownMailExceptionVO> flownMailExceptionVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "assignFlownMailExceptions");
		despatchRequest("assignFlownMailExceptions", flownMailExceptionVOs);

	}

	/**
	 * A-2401 Method to processFlight
	 *
	 * @param flownMailFilterVOs
	 * @throws BusinessDelegateException
	 */

	public void processFlight(FlownMailFilterVO flownMailFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "processFlight");
		despatchRequest("processFlight", flownMailFilterVO);
	}


	/**
	 * A-2401
	 * @param flightFilterVO
	 * @throws BusinessDelegateException
	 */
	public Collection<ValidUsersVO> validateUsers(
			Collection<String> userCodes, String companyCode)
			throws BusinessDelegateException  {
		return despatchRequest("validateUsers", userCodes, companyCode);
	}

	/**
	 * method for validating a clearance period using the table MTKIATCALMST--
	 * method returns a UPUCalendarVO to prove that it is a valid one-- the
	 * clearance period has to be validated from client for syntax mistakes--
	 *
	 * @param companyCode
	 * @param iataClearamcePrd
	 * @return UPUCalendarVO
	 * @throws BusinessDelegateException
	 */
	public UPUCalendarVO validateIataClearancePeriod(String companyCode,
			String iataClearamcePrd) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateIataClearancePeriod");
		return this.despatchRequest("validateIataClearancePeriod", companyCode,
				iataClearamcePrd);
	}

	/**
	 * method for finding inward/outward CN51Details captured for an airline for
	 * a particular clearance period -- the tables for fetching are MTKARLC51SMY
	 * and MTKARLC51DTL -- the return VO will be a single CN51SummaryVO --
	 *
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws BusinessDelegateException
	 */
	public AirlineCN51SummaryVO findCN51Details(AirlineCN51FilterVO cn51FilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findCN51Details");
		return this.despatchRequest("findCN51Details", cn51FilterVO);
	}

	/**
	 *
	 * @param gpaReportingDetailsVOs
	 * @throws BusinessDelegateException
	 */
	public void saveGPAReportingDetails(
			Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingMRADelegate", "saveGPAReportingDetails");
		despatchRequest("saveGPAReportingDetails", gpaReportingDetailsVOs);
		log.exiting("MailTrackingMRADelegate", "saveGPAReportingDetails");
	}

	/**
	 *
	 * @param invoiceLovFilterVO
	 * @return Page<AirlineInvoiceLovVO>
	 * @throws BusinessDelegateException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO)
			throws BusinessDelegateException {

		log.entering(CLASS_NAME, "displayInvoiceLOV");
		return despatchRequest("displayInvoiceLOV", invoiceLovFilterVO);

	}

	/**
	 *
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<MemoLovVO> displayMemoLOV(String companyCode, String memoCode,
			int pageNumber) throws BusinessDelegateException {

		log.entering(CLASS_NAME, "displayMemoLOV");
		return despatchRequest("displayMemoLOV", companyCode, memoCode,
				pageNumber);

	}

	/**
	 *
	 * @param gpaReportFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<GPAReportingDetailsVO> findGPAReportingDetails(
			GPAReportingFilterVO gpaReportFilterVO)
			throws BusinessDelegateException {
		log.entering("MailTrackingMRADelegate", "findGPAReportingDetails");
		Page<GPAReportingDetailsVO> gpaReportingDetailsVOs = despatchRequest(
				"findGPAReportingDetails", gpaReportFilterVO);
		log.exiting("MailTrackingMRADelegate", "findGPAReportingDetails");
		return gpaReportingDetailsVOs;
	}

	/**
	 * Added By A-2397 Method to saveMemo
	 *
	 * @return
	 * @param memoInInvoiceVos
	 * @throws BusinessDelegateException
	 */
	public void saveMemo(Collection<MemoInInvoiceVO> memoInInvoiceVos)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveMemo");
		despatchRequest("saveMemo", memoInInvoiceVos);

	}

	/**
	 * Added by A-2397 Method to list Rejection Memo
	 *
	 * @return Collection
	 * @param memoFilterVo
	 * @throws BusinessDelegateException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "findMemoDetails");
		return despatchRequest("findMemoDetails", memoFilterVo);

	}

	/**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<GPAReportingClaimDetailsVO> findClaimDetails(
			GPAReportingFilterVO gpaReportingFilterVO)
			throws BusinessDelegateException {

		log.entering(CLASS_NAME, "findClaimDetails");
		return despatchRequest("findClaimDetails", gpaReportingFilterVO);

	}

	/**
	 * @author A-2280
	 * @param gpaReportingClaimDetailVOs
	 * @throws BusinessDelegateException
	 */
	public void assignClaims(
			Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "assignClaims");
		despatchRequest("assignClaims", gpaReportingClaimDetailVOs);
		log.exiting(CLASS_NAME, "assignClaims");
	}

	/**
	 * Method to process sales batch
	 *
	 * @param invoiceFilterVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void processGpaReport(GPAReportingFilterVO filterVo)
	throws BusinessDelegateException {
		log.entering("CLASS_NAME", "processGpaReport");
		despatchRequest("processGpaReport", filterVo);
		log.exiting("CLASS_NAME", "processGpaReport");
	}

	/**
	 * method for capturing the CN51 details from the screen Capture Cn51 ---
	 * the method aids u to create a new CN51 record or either modify an
	 * existing one -- after creation method will call up the balancing method
	 * for balancing Cn51 & Cn66
	 *
	 * @param cn51SummaryVO
	 * @throws BusinessDelegateException
	 */
	public void saveCN51(AirlineCN51SummaryVO cn51SummaryVO)
	throws BusinessDelegateException {
		log.entering("CLASS_NAME", "saveCN51");
		despatchRequest("saveCN51", cn51SummaryVO);
		log.exiting("CLASS_NAME", "saveCN51");
	}

	/**
	 * @author A-2398
	 * @param blgMtxFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public BillingMatrixVO findBillingMatrixDetails(
			BillingMatrixFilterVO blgMtxFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findBillingMatrixDetails");
		log.exiting(CLASS_NAME, "findBillingMatrixDetails");
		return despatchRequest("findBillingMatrixDetails", blgMtxFilterVO);
	}

	/**
	 * @author A-2398
	 * @param blgMtxFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<BillingLineVO> findBillingLineDetails(
			BillingLineFilterVO blgLineFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findBillingLineDetails");
		log.exiting(CLASS_NAME, "findBillingLineDetails");
		return despatchRequest("findBillingLineDetails", blgLineFilterVO);
	}
	/**
	 * @author A-2398
	 * @param billingMatrixVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Boolean saveBillingMatrix(BillingMatrixVO billingMatrixVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME,"saveBillingMatrix");
		log.exiting(CLASS_NAME,"saveBillingMatrix");
		return despatchRequest("saveBillingMatrix",billingMatrixVO);
	}

	/**
	 * @param billingLineFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<BillingLineVO> findBillingLineValues(BillingLineFilterVO
			billingLineFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findBillingLineValues");
		Page<BillingLineVO> returnPage =
			despatchRequest("findBillingLineValues",billingLineFilterVO);
		log.exiting(CLASS_NAME,"findBillingLineValues");
		return returnPage;
	}
	/**
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findAllBillingMatrix");
		log.exiting(CLASS_NAME, "findAllBillingMatrix");
		return despatchRequest("findAllBillingMatrix", billingMatrixFilterVO);
	}

	/**
	 * @author A-2408
	 * @param cn66FilterVo
	 * @throws BusinessDelegateException
	 */
	public void processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "processMail");

		despatchRequest("processMail", cn66FilterVo);

		log.exiting(CLASS_NAME, "processMail");
	}

	/**
	 * Method for changing the billing matrix and billing line status.
	 *
	 * @author A-1872 Mar 5, 2007
	 * @param billingMatrixVOs
	 * @param billingLineVOs
	 * @throws BusinessDelegateException
	 */
	public void saveBillingLineStatus(
			Collection<BillingMatrixVO> billingMatrixVOs,
			Collection<BillingLineVO> billingLineVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveBillingLineStatus");
		despatchRequest("saveBillingLineStatus", billingMatrixVOs,
				billingLineVOs);
		log.exiting(CLASS_NAME, "saveBillingLineStatus");
	}

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param billingMatrixCode
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,
			String billingMatrixCode, int pageNumber)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findBillingMatrixLov");
		return despatchRequest("findBillingMatrixLov", companyCode,
				billingMatrixCode, pageNumber);
	}

	/**
	 * This method displays Mail Proration Details
	 *
	 * @author a-2518
	 * @param prorationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "displayProrationDetails");
		return despatchRequest("displayProrationDetails", prorationFilterVO);
	}

	/**
	 *
	 * @param invoiceFilterVO
	 * @throws BusinessDelegateException
	 */
	public void generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateOutwardBillingInvoice");
		despatchRequest("generateOutwardBillingInvoice", invoiceFilterVO);
		log.exiting("MailTrackingMRADelegate", "generateOutwardBillingInvoice");
	}

	/**
	 *
	 * @param gpaReportMessageVO
	 * @throws BusinessDelegateException
	 */
	public void uploadGPAReport(GPAReportMessageVO gpaReportMessageVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingMRADelegate", "uploadGPAReport");
		despatchRequest("uploadGPAReport", gpaReportMessageVO);
		log.exiting("MailTrackingMRADelegate", "uploadGPAReport");
	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<AirlineCN51SummaryVO> findCN51s(AirlineCN51FilterVO filterVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingMRADelegate","findCN51s");
		return despatchRequest("findCN51s",filterVO);
	}

	/**
	 * lists the Proration factors
	 *
	 * @param prorationFactorFilterVo
	 * @return Collection<ProrationFactorVO>
	 * @throws BusinessDelegateException
	 * @author a-2518
	 */
	public Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findProrationFactors");
		return despatchRequest("findProrationFactors", prorationFactorFilterVo);
	}

	/**
	 * Saves the Proration factors
	 *
	 * @param prorationFactorVos
	 * @return void
	 * @throws BusinessDelegateException
	 * @author a-2518
	 */
	public void saveProrationFactors(
			Collection<ProrationFactorVO> prorationFactorVos)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveProrationFactors");
		despatchRequest("saveProrationFactors", prorationFactorVos);
		log.exiting(CLASS_NAME, "saveProrationFactors");
	}

	/**
	 * This method changes the status. Possible values of status are "New,
	 * Active, Inactive and Cancelled"
	 *
	 * @param prorationFactorVo
	 * @throws BusinessDelegateException
	 * @author a-2518
	 */
	public void changeProrationFactorStatus(ProrationFactorVO prorationFactorVo)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeProrationFactorStatus");
		despatchRequest("changeProrationFactorStatus", prorationFactorVo);
		log.exiting(CLASS_NAME, "changeProrationFactorStatus");
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @param i
	 * @param
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<GPASettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO, int displayPage )throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findSettlementDetails");
		return despatchRequest("findSettlementDetails",invoiceSettlementFilterVO,displayPage);
	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementVOs
	 * @throws BusinessDelegateException
	 */
	public void saveSettlementDetails(
			Collection<GPASettlementVO> gpaSettlementVOs)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"saveSettlementDetails");
		despatchRequest("saveSettlementDetails",gpaSettlementVOs);
		log.exiting(CLASS_NAME,"saveSettlementDetails");
	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findSettlementHistory");
		return despatchRequest("findSettlementHistory",invoiceSettlementFilterVO);
	}

	/**
	 * @author a-2524
	 * @param mailSLAVo
	 * @throws BusinessDelegateException
	 */
	public void saveMailSla(MailSLAVO mailSLAVo) throws  BusinessDelegateException{
		log.entering(CLASS_NAME,"saveMailSla");
		despatchRequest("saveMailSla",mailSLAVo);
	}

	/**
	 * @author a-2524
	 * @param companyCode
	 * @param slaId
	 * @return MailSLAVO
	 * @throws BusinessDelegateException
	 */
	public MailSLAVO findMailSla(String companyCode, String slaId) throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findMailSla");
		return despatchRequest("findMailSla",companyCode,slaId);
	}
	/**
	 * Finds mail contract details
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return MailContractVO
	 * @throws BusinessDelegateException
	 * @author A-2518
	 */
	public MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "viewMailContract");
		return despatchRequest("viewMailContract", companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 * Saves mail contract details
	 *
	 * @author A-2518
	 * @param mailContractVo
	 * @throws BusinessDelegateException
	 */
	public void saveMailContract(MailContractVO mailContractVo)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "saveMailContract");
		despatchRequest("saveMailContract", mailContractVo);
		log.exiting("MailTrackingDefaultsDelegate", "saveMailContract");
	}

	/**
	 * Changes the agreement status - Possible values for status can be -
	 * <li>A - Active, C - Cancelled</li>
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param agreementStatus
	 * @throws BusinessDelegateException
	 * @author A-1946
	 */
	public void changeMailContractStatus(Collection<MailContractVO> mailContractVOs)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate",
		"changeMailContractStatus");
		despatchRequest("changeMailContractStatus",mailContractVOs );
		log.exiting("MailTrackingDefaultsDelegate", "changeMailContractStatus");
	}

	/**
	 * Displays Version numbers for Version LOV
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return
	 * @throws BusinessDelegateException
	 * @author A-2518
	 */
	public Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "displayVersionLov");
		return despatchRequest("displayVersionLov", companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 *  Added by A-2521
	 * @param contractFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO)throws BusinessDelegateException{

		log.entering(CLASS_NAME,"displayContractDetails");
		return despatchRequest("displayContractDetails",contractFilterVO);
	}

	/**
	 * Added by A-2521
	 * @param slaFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SLADetailsVO> displaySLADetails(
			SLAFilterVO slaFilterVO)throws BusinessDelegateException{

		log.entering(CLASS_NAME,"displaySLADetails");
		return despatchRequest("displaySLADetails", slaFilterVO);
	}
	/**
	 * @author
	 *  A-1946 The method is used to find the mail Contracts details.
	 * @param MailContractFilterVO
	 * @return Collection<MailContractVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findMailContracts");
		return despatchRequest("findMailContracts",
				mailContractFilterVO);
	}

	/**
	 * @author a-2049
	 *
	 */
	public PostalAdministrationVO findPostalAdminDetails( String companyCode , String gpaCode )
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findPostalAdminDetails");
		return this.despatchRequest("findPostalAdminDetails",companyCode,gpaCode);
	}

	/**
	 * This method validates billing matrix codes
	 * @author A-2518
	 * @param companyCode
	 * @param billingMatrixCodes
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void validateBillingMatrixCodes(String companyCode,
			Collection<String> billingMatrixCodes)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateBillingMatrixCodes");
		this.despatchRequest("validateBillingMatrixCodes", companyCode,
				billingMatrixCodes);
		log.exiting(CLASS_NAME, "validateBillingMatrixCodes");
	}

	/**
	 * This method validates Service Level Activity(SLA) codes
	 * @author A-2518
	 * @param companyCode
	 * @param slaCodes
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void validateSLACodes(String companyCode, Collection<String> slaCodes)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateSLACodes");
		this.despatchRequest("validateSLACodes", companyCode, slaCodes);
		log.exiting(CLASS_NAME, "validateSLACodes");
	}

	/**
	 * @param companyCode
	 * @param despatch
	 * @param gpaCode
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode,String dsn,
			String despatch,String gpaCode, int pageNumber) throws BusinessDelegateException{
		return despatchRequest("finddespatchlov", companyCode,dsn,despatch,gpaCode,pageNumber);
	}

	/**
	 * @author a-2518
	 * @param reportingPeriodFilterVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Boolean validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
	throws BusinessDelegateException {
		return despatchRequest("validateReportingPeriod",
				reportingPeriodFilterVo);
	}
	/**
	 * @author A-2391
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @throws BusinessDelegateException
	 */
	public RejectionMemoVO findRejectionMemo(RejectionMemoFilterVO filterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findRejectionMemo");
		log.exiting("MRAAirlineBillingDelegate", "findRejectionMemo");
		return despatchRequest("findRejectionMemo",filterVO);
	}
	/**
	 * @author A-2391
	 * @param rejectionMemoVO
	 * @return
	 *
	 *
	 * @throws BusinessDelegateException
	 */
	public void updateRejectionMemo(RejectionMemoVO rejectionMemoVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "updateRejectionMemo");
		log.exiting("MRAAirlineBillingDelegate", "updateRejectionMemo");
		despatchRequest("updateRejectionMemo",rejectionMemoVO);
	}


	public void importFlownMails(FlightValidationVO flightValidationVO,Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO,String txnlogInfo)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "importFlownMails");
		log.exiting("MRAAirlineBillingDelegate", "importFlownMails");
		dispatchAsyncRequest("importFlownMails",true,flightValidationVO,flownMailSegmentVOs,documentBillingVO,txnlogInfo);

	}

	/**
	 * Inactivates the billing lines
	 * @param billingLineVos
	 * @throws BusinessDelegateException
	 */
	public void inActivateBillingLines(Collection<BillingLineVO> billingLineVos)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "inActivateBillingLines");
		despatchRequest("inActivateBillingLines", billingLineVos);
	}

	/**
	 * Activates the billing lines
	 * @param billingLineVos
	 * @throws BusinessDelegateException
	 */
	public void activateBillingLines(Collection<BillingLineVO> billingLineVos)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "activateBillingLines");
		despatchRequest("activateBillingLines", billingLineVos);
	}

	/**
	 * Cancels the billing lines
	 * @param billingLineVos
	 * @throws BusinessDelegateException
	 */
	public void cancelBillingLines(Collection<BillingLineVO> billingLineVos)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "cancelBillingLines");
		despatchRequest("cancelBillingLines", billingLineVos);
	}

	/**
	 * @param masterVO
	 * @throws BusinessDelegateException
	 */
	/* Commented the method as part of ICRD-153078
	public void saveMailInvoicAdv(MailInvoicMasterVO masterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "saveMailInvoicAdv");
		despatchRequest("saveMailInvoicAdv", masterVO);
	}*/
	/**
	 * @author a-2270
	 * @param companyCode
	 * @param invoiceNumber
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findInvoicEnquiryDetails");
		return despatchRequest("findInvoicEnquiryDetails", invoiceEnquiryFilterVo);
	}

	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails
	(MailInvoicClaimsFilterVO filterVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findInvoicClaimsEnquiryDetails");
		return despatchRequest("findInvoicClaimsEnquiryDetails",filterVO);
	}
	/**
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(MailDOTRateFilterVO filterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findDOTRateDetails");
		return despatchRequest("findDOTRateDetails",filterVO);
	}

	/**
	 * @param mailDOTRateVOs
	 * @throws BusinessDelegateException
	 */
	public void saveDOTRateDetails(Collection<MailDOTRateVO> mailDOTRateVOs)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "saveDOTRateDetails");
		despatchRequest("saveDOTRateDetails",mailDOTRateVOs);
	}
	/**
	 * Inactivates the rateLineVOs
	 * @param rateLineVOs
	 * @throws BusinessDelegateException
	 */
	public void inActivateRateLines(Collection<RateLineVO> rateLineVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "inActivateRateLines");
		despatchRequest("inActivateRateLines", rateLineVOs);
	}

	/**
	 * Activates the rateLineVOs
	 * @param rateLineVOs
	 * @throws BusinessDelegateException
	 */
	public void activateRateLines(Collection<RateLineVO> rateLineVOs,boolean isBulkActivation)//Modified by a-7871 for ICRD-223130
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "activateRateLines");
		if(!isBulkActivation)
			{
			despatchRequest("activateRateLines", rateLineVOs ,isBulkActivation);
			}
		else
			{
			dispatchAsyncRequest("activateRateLines", true, rateLineVOs,isBulkActivation);
			}
	}

	/**
	 * Cancels the rateLineVOs
	 * @param rateLineVOs
	 * @throws BusinessDelegateException
	 */
	public void cancelRateLines(Collection<RateLineVO> rateLineVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "cancelRateLines");
		despatchRequest("cancelRateLines", rateLineVOs);
	}
	/**
	 * @param companyCode
	 * @throws BusinessDelegateException
	 */
	public void importToReconcile(String companyCode)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "importToReconcile");
		despatchRequest("importToReconcile", companyCode);
	}
	/**
	 * @param companyCode
	 * @throws BusinessDelegateException
	 */
	public void reconcileProcess(String companyCode)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "reconcileProcess");
		despatchRequest("reconcileProcess", companyCode);
	}

	/**
	 * This method generates INVOIC Claim file
	 *
	 * @author A-2518
	 * @param companyCode
	 * @throws BusinessDelegateException
	 */
	public void generateInvoicClaimFile(String companyCode)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateInvoicClaimFile");
		despatchRequest("generateInvoicClaimFile", companyCode);
	}
	/**
	 * @author A-2391 This method is used to fetch audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<AuditDetailsVO> findArlAuditDetails(MRAArlAuditFilterVO mailAuditFilterVO)
	throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate", "findArlAuditDetails");
		return despatchRequest("findArlAuditDetails",mailAuditFilterVO);
	}
	/**
	 * @param companyCode
	 * @param invoicKey
	 * @param poaCode
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey,String poaCode, int pageNumber)throws BusinessDelegateException {
		return despatchRequest("findInvoicKeyLov",companyCode,invoicKey,poaCode,pageNumber);
	}
	/**
	 * Method to find the billingperiod of the gpacode/billing periods of all
	 * the gpacode of the country specified
	 * Method is called on clickig the suggestbutton
	 *
	 * @param generateInvoiceFilterVO
	 * @return Collection<String>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws BusinessDelegateException{
		log.entering("MailTrackingDefaultsDelegate", "findBillingPeriods");
		return despatchRequest("findBillingPeriods",generateInvoiceFilterVO);


	}



	/**
	 * @author A-3447
	 * @param summaryVO
	 * @throws BusinessDelegateException
	 */

	public void finalizeandSendEInvoice(Collection<CN51SummaryVO> summaryVOs,String eInvoiceMsg) throws BusinessDelegateException {
		despatchRequest("finalizeandSendEInvoice", summaryVOs,eInvoiceMsg);

	}
	/**
	 * @param companyCode
	 * @param blgbasis
	 * @param despatchDate
	 * @return DespatchEnquiryVO
	 * @throws BusinessDelegateException
	 */
	public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws BusinessDelegateException {
		return despatchRequest("findDespatchDetails",popUpVO);
	}
	/**
	 * @param companyCode
	 * @param blgbasis
	 * @param despatchDate
	 * @return Collection<GPABillingDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)throws BusinessDelegateException {
		return despatchRequest("findGPABillingDetails",popUpVO);
	}
	/**
	 *
	 * @param companyCode
	 * @param dsnNum
	 * @param dsnDate
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
			String dsnNum,String dsnDate,int pageNumber) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findDsnSelectLov");
		return this.despatchRequest("findDsnSelectLov", companyCode, dsnNum,dsnDate,
				pageNumber);

	}
	/**
	 * @author A-3108
	 * @param rateAuditFilterVO
	 * @return Collection<RateAuditVO>
	 * @throws BusinessDelegateException
	 */
	public Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws BusinessDelegateException {
		return despatchRequest("findRateAuditDetails",rateAuditFilterVO);
	}

	/**
	 * @author A-2391
	 * @param rateAuditFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws BusinessDelegateException {
		return despatchRequest("findListRateAuditDetails",rateAuditFilterVO);
	}
	/**
	 * @author A-3108
	 * @param rateAuditVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void changeRateAuditDsnStatus(Collection<RateAuditVO> rateAuditVOs,Collection<RateAuditVO> rateAuditVOsForaplyAudit)throws BusinessDelegateException {
		despatchRequest("changeRateAuditDsnStatus",rateAuditVOs,rateAuditVOsForaplyAudit);
	}
	/**
	 * @author A-3434
	 * @param formOneFilterVo
	 * @return Page<FormOneVO>
	 * @throws BusinessDelegateException
	 */
	public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findFormOnes");
		return despatchRequest("findFormOnes",formOneFilterVo);

	}


	/**
	 * findCCAdetails
	 * @author A-3227
	 * @param maintainCCAFilterVO
	 * @return  Collection<CCAdetailsVO>
	 * @throws BusinessDelegateException
	 */

	public  Collection<CCAdetailsVO> findCCAdetails(MaintainCCAFilterVO maintainCCAFilterVO)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "----findCCAdetails---");
		return despatchRequest("findCCAdetails",maintainCCAFilterVO);

	}

	/**
	 * @author A-3447
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public  Collection<CCAdetailsVO>findCCA(MaintainCCAFilterVO maintainCCAFilterVO)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "----findCCA---");
		return despatchRequest("findCCA",maintainCCAFilterVO);

	}

	/**
	 *
	 * @author A-3434
	 * @param interlineFilterVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<AirlineForBillingVO> findFormTwoDetails(
			InterlineFilterVO interlineFilterVo)
			throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findFormTwoDetails");
		return despatchRequest("findFormTwoDetails",interlineFilterVo);

	}
	/**
	 *
	 * @param formOneVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public FormOneVO listFormOneDetails(FormOneVO formOneVO)throws BusinessDelegateException {

		return despatchRequest("listFormOneDetails",formOneVO);

	}
	/**
	 * @author A-3456
	 * @param interlineFilterVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findFormOneDetails");

		return despatchRequest("findFormOneDetails", interlineFilterVo);
	}
	/**
	 *
	 * @param formOneVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveFormOneDetails(FormOneVO formOneVO)throws BusinessDelegateException {

		despatchRequest("saveFormOneDetails",formOneVO);

	}
	/**
	 * @author a-3108
	 * this method is for saving the Form3 Details..
	 * @param airlineForBillingVOs
	 * @throws BusinessDelegateException
	 */
	public void saveFormThreeDetails (Collection<AirlineForBillingVO>airlineForBillingVOs)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME,"saveFormThreeDetails");
		despatchRequest("saveFormThreeDetails",airlineForBillingVOs);

	}
	/**
	 * @author a-3108
	 * this method is for listing the Form3 Details..
	 * @param airlineForBillingVOs
	 * @throws BusinessDelegateException
	 */
	public Collection<AirlineForBillingVO> findFormThreeDetails (InterlineFilterVO interlineFilterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findFormThreeDetails");
		return despatchRequest("findFormThreeDetails",interlineFilterVO);

	}
	/**
	 *
	 * @author A-3429
	 * @param listCCAFilterVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<CCAdetailsVO> listCCAs(ListCCAFilterVO listCCAFilterVo)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "listCCAs");
		return despatchRequest("listCCAs", listCCAFilterVo);

	}

	/**
	 * @author A-3456
	 * @param airlineCN51FilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public AirlineCN51SummaryVO findCaptureInvoiceDetails(AirlineCN51FilterVO airlineCN51FilterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findCaptureInvoiceDetails");
		return despatchRequest("findCaptureInvoiceDetails", airlineCN51FilterVO);
	}


	/**
	 * @author a-3447
	 * @param airlineCN51SummaryVO
	 * @throws BusinessDelegateException
	 */
	public void	updateBillingDetailCommand(AirlineCN51SummaryVO airlineCN51SummaryVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "updateBillingDetailCommand");
		despatchRequest("updateBillingDetailCommand", airlineCN51SummaryVO);
	}




	/**
	 * @author A-2554
	 * @param prorationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO)throws BusinessDelegateException {
		return despatchRequest("listProrationDetails",prorationFilterVO);
	}
	/**
	 * @author a-3434
	 * this method is for listing the InterlineBillingEntries
	 * @param airlineBillingFilterVO
	 * @throws BusinessDelegateException
	 */
	public Page<DocumentBillingDetailsVO> findInterlineBillingEntries (AirlineBillingFilterVO airlineBillingFilterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findInterlineBillingEntries");
		return despatchRequest("findInterlineBillingEntries",airlineBillingFilterVO);

	}
	/**
	 * @author A-3434 . update status and remarks
	 *
	 * @param documentBillingDetailsVO
	 *
	 * @throws BusinessDelegateException
	 */


	public void changeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeStatus");

		despatchRequest("changeStatus", documentBillingDetailsVOs);
	}
	/**
	 * @author A-3434 . update review flag
	 *
	 * @param documentBillingDetailsVO
	 *
	 * @throws BusinessDelegateException
	 */
	public void changeReview(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeReview");

		despatchRequest("changeReview", documentBillingDetailsVOs);
	}

	/**
	 * @author A-3229
	 * @param ProrationDetailsVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProrationDetails(Collection<ProrationDetailsVO> prorationDetailsVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "save manual proration");
		despatchRequest("saveProrationDetails",prorationDetailsVO);
	}
	/**
	 * @author A-2391
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String findInvoiceListingCurrency(AirlineCN51FilterVO filterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findInvoiceListingCurrency");
		return despatchRequest("findInvoiceListingCurrency",filterVO);

	}
	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @return RateAuditVO
	 * @throws BusinessDelegateException
	 */
	public RateAuditVO computeTotalForRateAuditDetails(RateAuditVO newRateAuditVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "computeTotalForRateAuditDetails");
		return despatchRequest("computeTotalForRateAuditDetails",newRateAuditVO);

	}


	/**
	 * @author A-3447
	 * @return String
	 * @throws BusinessDelegateException
	 */
	public String saveRejectionMemos(RejectionMemoVO rejectionMemoVO)throws BusinessDelegateException {
		return despatchRequest("saveRejectionMemos",rejectionMemoVO);

	}

	/**
	 * @author A-3251
	 * @param companyCode
	 * @param subclass
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String validateMailSubClass(String companyCode,String subclass) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateMailSubClass");
		return despatchRequest("validateMailSubClass",companyCode,subclass);

	}
	/**
	 * @author A-3429
	 * @param flightSectorRevenueFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws BusinessDelegateException
	 */

	public Collection<SectorRevenueDetailsVO> findSectorDetails(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findSectorDetails");
		return this.despatchRequest("findSectorDetails", flightSectorRevenueFilterVO);
	}
	/**
	 * @author A-3429
	 * @param flightSectorRevenueFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws BusinessDelegateException
	 */

	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findSectorDetails");
		return this.despatchRequest("findFlightRevenueForSector", flightSectorRevenueFilterVO);
	}




	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveRateAuditDetails(RateAuditVO newRateAuditVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "computeTotalForRateAuditDetails");
		despatchRequest("saveRateAuditDetails",newRateAuditVO);

	}


	/**
	 * @author A-3229
	 * @param dsnRoutingFilterVO
	 * @return Collection<DSNRoutingVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<DSNRoutingVO> findDSNRoutingDetails(
			DSNRoutingFilterVO  dsnRoutingFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findDSNRoutingDetails");
		return this.despatchRequest("findDSNRoutingDetails", dsnRoutingFilterVO);
	}

	/**
	 * @author A-3229
	 * @param dsnRoutingVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveDSNRoutingDetails(Collection<DSNRoutingVO> dsnRoutingVOs)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveDSNRoutingDetails");
		despatchRequest("saveDSNRoutingDetails",dsnRoutingVOs);
	}

	/**
	 * @author A-3251
	 * @param cCADetailsVO
	 * @throws BusinessDelegateException
	 */
	public void	deleteCCAdetails(CCAdetailsVO cCADetailsVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "----deleteCCAdetails---");
		despatchRequest("deleteCCAdetails", cCADetailsVO);
	}



	/**
	 * @author A-2107
	 * @param UnaccountedDispatchesFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)throws BusinessDelegateException {
		return despatchRequest("listUnaccountedDispatches",unaccountedDispatchesFilterVO);
	}

	/**
	 * @author A-2554
	 * @param airlineBillingDetailVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			AirlineBillingDetailVO  airlineBillingDetailVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findInterLineBillingDetails");
		return despatchRequest("findInterLineBillingDetails", airlineBillingDetailVO);
	}

	/**
	 * @author A-3251
	 * @param rateAuditVO		 *
	 * @throws BusinessDelegateException
	 */
	public void populateInitialDataInTempTables(RateAuditVO rateAuditVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "populateInitialDataInTempTables");
		despatchRequest("populateInitialDataInTempTables",rateAuditVO);

	}

	/**
	 * @author A-3251
	 * @param rateAuditVO		 *
	 * @throws BusinessDelegateException
	 */
	public void removeRateAuditDetailsFromTemp(RateAuditVO rateAuditVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "removeRateAuditDetailsFromTemp");
		despatchRequest("removeRateAuditDetailsFromTemp",rateAuditVO);

	}
	/**
	 * @author A-3108
	 * @param ProrationExceptionsFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<ProrationExceptionVO> findProrationExceptions(ProrationExceptionsFilterVO prorationExceptionsFilterVO)throws BusinessDelegateException{
		log.entering("MailTrackingDefaults Delegate", "findProrationExceptions");
		return despatchRequest("findProrationExceptions",prorationExceptionsFilterVO);

	}
	/**
	 * @author A-3108
	 * @param ProrationExceptionVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProrationExceptions(Collection<ProrationExceptionVO> prorationExceptionVOs)throws BusinessDelegateException{
		log.entering("MailTrackingDefaults Delegate", "saveProrationExceptions");
		despatchRequest("saveProrationExceptions",prorationExceptionVOs);

	}



	/**
	 * @author A-3229
	 * @param dsnFilterVO
	 * @return Collection<MailProrationLogVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<MailProrationLogVO> findProrationLogDetails(
			DSNFilterVO  dsnFilterVO)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findProrationLogDetails");
		return this.despatchRequest("findProrationLogDetails", dsnFilterVO);
	}

	/**
	 * @author A-3229
	 * @param prorationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO)throws BusinessDelegateException {
		return despatchRequest("viewProrationLogDetails",prorationFilterVO);
	}
	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO dsnPopUpVO)throws BusinessDelegateException {
		return despatchRequest("findFlownDetails",dsnPopUpVO);
	}




	/**
	 * @author A-3447
	 * @param airlineCN51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws BusinessDelegateException
	 * Method to pick all invoice flags form table
	 *
	 */
	public AirlineForBillingVO findAllInvoiceFlags(AirlineCN51FilterVO airlineCN51FilterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findAllInvoiceFlags");
		return despatchRequest("findAllInvoiceFlags", airlineCN51FilterVO);
	}




	/**
	 * A-3429
	 * Method to saveRejectionMemo
	 * @return RejectionMemoVO
	 * @param exceptionInInvoiceVO
	 * @throws BusinessDelegateException
	 */
	public String saveRejectionMemoForDsn(
			RejectionMemoVO rejectionMemoVO)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveRejectionMemoForDsn");
		return despatchRequest("saveRejectionMemoForDsn", rejectionMemoVO);

	}

	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO		 *
	 * @throws BusinessDelegateException
	 */
	public PostalAdministrationDetailsVO validatePoaDetailsForBilling(PostalAdministrationDetailsVO postalAdministrationDetailsVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "validatePoaDetailsForBilling");
		return despatchRequest("validatePoaDetailsForBilling",postalAdministrationDetailsVO);
	}
	/**
	 * @author A-3229
	 * @param  MRAIrregularityFilterVO
	 * @return Collection<MRAIrregularityVO>
	 * @throws BusinessDelegateException
	 */

	public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "viewIrregularityDetails");
		return despatchRequest("viewIrregularityDetails",filterVO);
	}

	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<MRAAccountingVO>
	 * @throws BusinessDelegateException
	 */
	public Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO dsnPopUpVO)throws BusinessDelegateException {
		return despatchRequest("findAccountingDetails",dsnPopUpVO);
	}
	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<USPSReportingVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO dsnPopUpVO)throws BusinessDelegateException {
		return despatchRequest("findUSPSReportingDetails",dsnPopUpVO);
	}




	/**
	 * @author A-2107
	 * @param unaccountedDispatchesFilterVO
	 * @throws BusinessDelegateException
	 */
	public UnaccountedDispatchesVO getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "getTotalOfDispatches");
		return despatchRequest("getTotalOfDispatches",unaccountedDispatchesFilterVO);

	}


	/**
	 * @author A-3251
	 * @param dsnRoutingVOs
	 *
	 */
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "reProrateDSN");
		despatchRequest("reProrateDSN",dsnRoutingVOs);

	}

	/**
	 * @author A-3251
	 * @param dsnRoutingVOs
	 *
	 */
	public void raiseActualCCA(RateAuditVO rateAuditVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "raiseActualCCA");
		despatchRequest("raiseActualCCA",rateAuditVO);

	}

	/**
	 * @author A-3434
	 * @param outstandingBalanceVO
	 * @return Collection<OutstandingBalanceVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)
	throws BusinessDelegateException {
		return despatchRequest("findOutstandingBalances",outstandingBalanceVO);
	}
	/**
	 * @author A-2554
	 * @param cN51SummaryVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String generateEInvoiceMessage(CN51SummaryVO cN51SummaryVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateEInvoiceMessage");
		return despatchRequest("generateEInvoiceMessage",cN51SummaryVO);
	}
	/**
	 * calls Accounting procedure
	 * @param filterVO
	 * @throws BusinessDelegateException
	 */
	public void performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs)
	throws BusinessDelegateException {
		despatchRequest("performAccountingForDSNs", unAccountedDSNVOs);
	}

	/**
	 * A-3429 Method to accept Airline Invoices
	 *
	 * @param airlineExceptionsVOs
	 * @throws BusinessDelegateException
	 */
	public void acceptAirlineDsns(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "acceptAirlineDsns");
		despatchRequest("acceptAirlineDsns", airlineExceptionsVOs);
		log.exiting(CLASS_NAME, "acceptAirlineDsns");
	}

	/**
	 * @author A-2391
	 * @param outstandingBalanceVO
	 * @return Collection<FuelSurchargeVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCode)
	throws BusinessDelegateException {
		return despatchRequest("displayFuelSurchargeDetails",gpaCode,cmpCode);
	}
	/**
	 * A-2391
	 * Method to fuelSurchargeVOs
	 * @return RejectionMemoVO
	 * @param fuelSurchargeVOs
	 * @throws BusinessDelegateException
	 */
	public void saveFuelSurchargeDetails(
			Collection<FuelSurchargeVO> fuelSurchargeVOs)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");
		despatchRequest("saveFuelSurchargeDetails", fuelSurchargeVOs);

	}
	/**
	 * A-2391
	 * Method to ccadetailsVO
	 * @param ccadetailsVO
	 * @throws BusinessDelegateException
	 */
	public void saveHistoryDetails(
			CCAdetailsVO ccadetailsVO)
	throws BusinessDelegateException {

		log.entering(CLASS_NAME, "saveHistoryDetails");
		despatchRequest("saveHistoryDetails", ccadetailsVO);

	}

	//Added by A-3434 for Clearance period validation
	/**
	 * @param iataCalenderFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public IATACalendarVO validateClearancePeriod(
			IATACalendarFilterVO iataCalenderFilterVO)	throws BusinessDelegateException{
		log.entering("MailTrackingMRADelegate","validateClearancePeriod");
		return despatchRequest("validateClearancePeriod", iataCalenderFilterVO);
	}
	/**
	 * @author A-3429 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws BusinessDelegateException
	 */
	public PostalAdministrationVO findPACode(String companyCode, String paCode)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findPACode1234");
		return despatchRequest("frmMRAfindPACode", companyCode, paCode);
	}
	/**
	 * @author A-3429 Method for PALov containing PACode and PADescription
	 * @param companyCode
	 * @param paCode
	 * @param paName
	 * @param pageNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<PostalAdministrationVO> findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultPgeSize)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findPALov1234");
		return despatchRequest("frmMRAfindPALov", companyCode, paCode, paName,
				pageNumber,defaultPgeSize);
	}

	public void printMailRevenueReport(FlownMailFilterVO flownFilterVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "printMailRevenueReport");
		despatchRequest("printMailRevenueReport",flownFilterVO);
	}

	/**
	 * @author A-2414
	 *
	 * @param mailExceptionReportsVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String generateMailExceptionReport(MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateMailExceptionReport");
		return despatchRequest("generateMailExceptionReport", mailExceptionReportsFilterVo);
	}

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findUnSettledInvoicesForGPA");
		return despatchRequest("findUnSettledInvoicesForGPA",gpaSettlementVO);
	}

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<GPASettlementVO>findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findUnSettledInvoicesForGPA");
		return despatchRequest("findUnSettledInvoicesForGPAForSettlementCapture",gpaSettlementVO);
	}
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<CCAdetailsVO>findMCALov(
			CCAdetailsVO ccAdetailsVO ,int displayPage)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findMCALov");
		return despatchRequest("findMCALov",ccAdetailsVO,displayPage);
	}
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<CCAdetailsVO>findDSNLov(
			CCAdetailsVO ccAdetailsVO ,int displayPage)throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findDSNLov");
		return despatchRequest("findDSNLov",ccAdetailsVO,displayPage);
	}
	/**
	 * @author a-4823
	 * @param cCADetailsVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String	saveMCAdetails(CCAdetailsVO cCADetailsVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "----saveCCAdetails---");
		return despatchRequest("saveMCAdetails", cCADetailsVO);
	}
	/**
	 *
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<CCAdetailsVO> findApprovedMCA(
			MaintainCCAFilterVO maintainCCAFilterVO) throws BusinessDelegateException {
		// TODO Auto-generated method stub
		return despatchRequest("findApprovedMCA", maintainCCAFilterVO);
	}
	/**
	 * @author A-5166
	 *
	 * @param RoutingCarrierFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findRoutingCarrierDetails");
		return despatchRequest("findRoutingCarrierDetails", carrierFilterVO);
	}

	/**
	 * @author A-5166
	 *
	 * @param RoutingCarrierVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public void saveRoutingCarrierDetails(Collection<RoutingCarrierVO> routingCarrierVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveRoutingCarrierDetails");
		despatchRequest("saveRoutingCarrierDetails", routingCarrierVOs);
	}
	/**
	 *  @author A-5166
	 *	Added by A-5166 for ICRD-17262 on 07-Feb-2013
	 * @param rateCardVO
	 * @throws BusinessDelegateException
	 */
	public void saveCopyRateCard(RateCardVO rateCardVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "saveCopyRateCard");
		despatchRequest("saveCopyRateCard",rateCardVO);

	}/**
	 * Save billing site details.
	 *
	 * @param billingSiteVO the billing site vo
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public BillingSiteVO saveBillingSiteDetails(BillingSiteVO billingSiteVO)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "saveBillingSiteDetails");
		return despatchRequest("saveBillingSiteDetails",billingSiteVO);
	}

	/**
	 * Find billing site details.
	 *
	 * @param billingSiteFilterVO the billing site filter vo
	 * @return the collection
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findBillingSiteDetails");
		return despatchRequest("findBillingSiteDetails", billingSiteFilterVO);
	}

	/**
	 * List parameter lov.
	 *
	 * @param bsLovFilterVo the bs lov filter vo
	 * @return the page
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public Page<BillingSiteLOVVO> listParameterLov(
			BillingSiteLOVFilterVO bsLovFilterVo)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "listParameterLov");
		return despatchRequest("listParameterLov", bsLovFilterVo);

	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.generateInvoiceTK
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:   ICRD-42160 turkish specific
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "generateInvoiceTK");
		dispatchAsyncRequest("generateInvoiceTK", false, generateInvoiceFilterVO);
		log.exiting(CLASS_NAME, "generateInvoiceTK ");
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.sendEmail
	 *	Added by 	:	A-4809 on 09-Jan-2014
	 * 	Used for 	:	ICRD-42160 emailInvoice
	 *	Parameters	:	@param cN51CN66VO
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void sendEmailInvoice(CN51CN66VO cN51CN66VO)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "sendEmailInvoice");
		despatchRequest("sendEmailInvoice", cN51CN66VO);
		log.exiting(CLASS_NAME, "sendEmailInvoice");
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.addLocks
	 *	Added by 	:	A-4809 on 10-Jan-2014
	 * 	Used for 	:	ICRD-42160 to lock while generating invoices
	 *	Parameters	:	@param addLocks
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	Collection<LockVO>
	 */
	public Collection<LockVO> generateInvoiceLock(String companycode)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "addLocks");
		return despatchRequest("generateInvoiceLock", companycode);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.findCN51DetailsForPrint
	 *	Added by 	:	A-4809 on 24-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	Collection<CN51DetailsVO>
	 */
	public Collection<CN51DetailsVO> generateCN51ReportPrint(CN51CN66FilterVO filterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "generateCN51ReportPrint");
		return despatchRequest("generateCN51ReportPrint", filterVO);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.findCN66DetailsForPrint
	 *	Added by 	:	A-4809 on 24-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	Collection<CN66DetailsPrintVO>
	 */
	public Collection<CN66DetailsVO> generateCN66ReportPrint(CN51CN66FilterVO filterVO)
	throws BusinessDelegateException{
		log.entering(CLASS_NAME, "generateCN66ReportPrint");
		return despatchRequest("generateCN66ReportPrint", filterVO);
	}
/**
 * 	Method		:	MailTrackingMRADelegate.prorateExceptionFlights
 *	Added by 	:	A-6245 on 17-06-2015
 * @param flightValidationVOs
 * @throws BusinessDelegateException
 */
	public void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)throws BusinessDelegateException{
		log.entering("MailTrackingDefaults Delegate", "prorateExceptionFlights");
		despatchRequest("prorateExceptionFlights",flightValidationVOs);

	}
	/**
	 * Method		:	MailTrackingMRADelegate.validateFlightForAirport
	 * @param flightFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateFlightForAirport");
		return this.despatchRequest("validateFlightForAirport", flightFilterVO);
	}

	/*Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 *
	 * param invoiceTransactionLogVO
	 * @return
	 * @throws BusinessDelegateException
	 *
	 * */
	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO) throws BusinessDelegateException {
		return despatchRequest("initiateTransactionLogForInvoiceGeneration",invoiceTransactionLogVO);
	}

	/*Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 *
	 * param generateInvoiceFilterVO
	 * @return boolean
	 * @throws BusinessDelegateException
	 *
	 * */
	public Boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO) throws BusinessDelegateException {
		return despatchRequest("validateGpaBillingPeriod",generateInvoiceFilterVO);
	}








	  /**
	 *
	 * @author A-5255
	 * @param prorationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(ProrationFilterVO prorationFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "viewSurchargeProrationDetails");
		return despatchRequest("viewSurchargeProrationDetails",prorationFilterVO);
	}
	/**
	 *
	 * @author A-5255
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SurchargeCCAdetailsVO> getSurchargeCCADetails(MaintainCCAFilterVO maintainCCAFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "getSurchargeCCADetails");
		return despatchRequest("getSurchargeCCADetails",maintainCCAFilterVO);
	}
	/**
	 *
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(CN51CN66FilterVO cn51CN66FilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findSurchargeBillingDetails");
		return despatchRequest("findSurchargeBillingDetails",cn51CN66FilterVO);
	}
	/**
	 *
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(BillingMatrixFilterVO blgMatrixFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findBillingMatrixAuditDetails");
		return despatchRequest("findBillingMatrixAuditDetails",blgMatrixFilterVO);
	}
	/**
	 *
	 * @author A-6245
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(CN51CN66FilterVO cn51CN66FilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findSurchargeBillableDetails");
		return despatchRequest("findSurchargeBillableDetails",cn51CN66FilterVO);
	}
	/**
	 * @author A-6245
	 * @param prorationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(ProrationFilterVO prorationFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "viewSurchargeProrationDetailsForMCA");
		return despatchRequest("viewSurchargeProrationDetailsForMCA",prorationFilterVO);
	}
	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.withdrawMailbags
	 *	Added by 	:	A-6991 on 05-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws BusinessDelegateException {
				despatchRequest("withdrawMailbags", documentBillingDetailsVOs);
			}

	public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
			throws BusinessDelegateException {
				despatchRequest("finalizeProformaInvoice", summaryVOs);
			}
	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.withdrawInvoice
	 *	Added by 	:	A-6991 on 18-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param summaryVO
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void withdrawInvoice(CN51SummaryVO summaryVO)
			throws BusinessDelegateException {
				despatchRequest("withdrawInvoice", summaryVO);
			}

	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.reRateMailbags
	 *	Added by 	:	A-7531
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@param txnlogInfo
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void reRateMailbags(
			Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "reRateMailbags");
		dispatchAsyncRequest("reRateMailbags",true, documentBillingDetailsVOs,txnlogInfo);
	}

/**
 *
 * 	Method		:	MailTrackingMRADelegate.findRerateBillableMails
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
	public Collection<DocumentBillingDetailsVO> findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,String companyCode)
			throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findRerateBillableMails");
		return despatchRequest("findRerateBillableMails",documentBillingVO,companyCode);
	}

	 /**
	  *
	  * 	Method		:	MailTrackingMRADelegate.findRerateInterlineBillableMails
	  *	Added by 	:	A-7531
	  *	Parameters	:	@param documentBillingVO
	  *	Parameters	:	@param companyCode
	  *	Parameters	:	@return
	  *	Parameters	:	@throws BusinessDelegateException
	  *	Return type	: 	Collection<DocumentBillingDetailsVO>
	  */
	public Collection<DocumentBillingDetailsVO>findRerateInterlineBillableMails(
			DocumentBillingDetailsVO documentBillingVO,String companyCode) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findRerateInterlineBillableMails");
		return despatchRequest("findRerateInterlineBillableMails",documentBillingVO,companyCode);
	}

	/**
	 * 	Method		:	MailTrackingMRADelegate.findReproarteMails
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingVO
	 *	Return type	: 	void
	 */
	public Collection<DocumentBillingDetailsVO> findReproarteMails(DocumentBillingDetailsVO documentBillingVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findReproarteMails");
		return despatchRequest("findReproarteMails",documentBillingVO);

	}

	/**
	 * 	Method		:	MailTrackingMRADelegate.reProrateExceptionMails
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param selectedDocumentBillingDetailsVOs
	 *	Parameters	:	@param txnlogInfo
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	public void reProrateExceptionMails(
			Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs,
			String txnlogInfo) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "reProrateExceptionMails");
		dispatchAsyncRequest("reProrateExceptionMails",true, documentBillingDetailsVOs,txnlogInfo);
	}
/**
 * @author A-7371
 * @param prorationFilterVO
 * @return Collection<AWMProrationDetailsVO>
 * @throws BusinessDelegateException
 */
	public Collection<AWMProrationDetailsVO>  viewAWMProrationDetails(ProrationFilterVO prorationFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "viewAWMProrationDetails");
		return despatchRequest("viewAWMProrationDetails",prorationFilterVO);
	}
	   	/**
	 * @author A-8061
	 * @param FlightFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public String validateBSA(
			DSNRoutingVO dSNRoutingVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateBSA");
		return this.despatchRequest("validateBSA", dSNRoutingVO);
	}

	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.saveMCAdetailsForAutoMCA
	 *	Added by 	:	A-7929 on 26-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param cCAdetailsVOs
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 * @param gpaBillingEntriesFilterVO
	 * @throws BusinessDelegateException
	 */
	public void saveAutoMCAdetails(Collection<CCAdetailsVO> cCAdetailsVOs, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "----saveAutoMCAdetails---");
		dispatchAsyncRequest("saveAutoMCAdetails",true, cCAdetailsVOs,gpaBillingEntriesFilterVO);

		}
     /**
     *
	 * 	Method		:	MailTrackingMRADelegate.finalizeInvoice
	 *	Added by 	:	A-7929 on 17-Aug-2018
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 * @param selectedairlineCN51SummaryVO
	 * @throws BusinessDelegateException
	 */
	public void finalizeInvoice(Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO,String txnlogInfo) throws BusinessDelegateException {

		log.entering(CLASS_NAME, "----finalizeInvoice---");
		 despatchRequest("finalizeInvoice",selectedairlineCN51SummaryVO,txnlogInfo);
	}


    /**
    *
	 * 	Method		:	MailTrackingMRADelegate.withdrawMailBags
	 *	Added by 	:	A-8061
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Return type	: 	void
	 * @param airlineCN66DetailsVOs
	 * @throws BusinessDelegateException
	 */

	public void  withdrawMailBags(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs)throws BusinessDelegateException {
		  despatchRequest("withdrawMailBags",airlineCN66DetailsVOs);
	}
	/***
	 * @author A-7794
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<AirlineCN51SummaryVO>  getAirlineSummaryDetails(AirlineCN51FilterVO filterVO)throws BusinessDelegateException{
		return despatchRequest("getAirlineSummaryDetails",filterVO);
	}
	
	public Collection<FlightSegmentSummaryVO>  findFlightSegments(String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) throws BusinessDelegateException{
		return despatchRequest("findFlightSegments",companyCode,airlineId,flightNumber,flightSequenceNumber);
	}
	
/**
 *
 * 	Method		:	MailTrackingMRADelegate.saveSupportingDocumentDetails
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
 */
	  public Collection<SisSupportingDocumentDetailsVO> saveSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
			    throws BusinessDelegateException
			  {
			    return despatchRequest("saveSupportingDocumentDetails", sisSupportingDocumentDetailsVOs );
			  }
/**
 *
 * 	Method		:	MailTrackingMRADelegate.downloadAttachment
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param documentFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	SisSupportingDocumentDetailsVO
 */
	public SisSupportingDocumentDetailsVO downloadAttachment(SupportingDocumentFilterVO documentFilterVO) throws BusinessDelegateException{
		// TODO Auto-generated method stub
		return  despatchRequest("downloadAttachment",documentFilterVO);
	}

/**
 *
 * 	Method		:	MailTrackingMRADelegate.removeSupportingDocumentDetails
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
 */
public Collection<SisSupportingDocumentDetailsVO> removeSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
			    throws BusinessDelegateException
 {
 return  despatchRequest("removeSupportingDocumentDetails",sisSupportingDocumentDetailsVOs);
}


	/**
	 * @author A-5526 . update status and remarks 
	 * 
	 * @param documentBillingDetailsVO
	 * 
	 * @throws BusinessDelegateException
	 */


	public void changeStatusForInterline(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME, "changeStatus");

		despatchRequest("changeStatusForInterline", documentBillingDetailsVOs);
	}
	
	/**
	 * 
	 * @param documentBillingDetailsVOs
	 * @throws BusinessDelegateException
	 */
	public void voidMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws BusinessDelegateException{
		log.entering(CLASS_NAME, "voidMailbags");
		despatchRequest("voidMailbags", documentBillingDetailsVOs);	
		log.exiting(CLASS_NAME, "voidMailbags");
	}

	public Collection<String> findMailbagBillingStatus(MaintainCCAFilterVO maintainCCAFilterVO) throws BusinessDelegateException {
		log.entering("MailTrackingDefaultsDelegate","findMailbagBillingStatus");
		return despatchRequest("findMailbagBillingStatus",maintainCCAFilterVO);
	}
	/**
	 * 
	 * 	Method		:	MailTrackingMRADelegate.validateCurrConversion
	 *	Added by 	:	A-8061 on 30-Oct-2019
	 * 	Used for 	:	ICRD-346925
	 *	Parameters	:	@param currCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	String
	 */
	public String validateCurrConversion(String currCode)throws BusinessDelegateException {
		log.entering("MailTrackingMRADelegate","validateCurrConversion");
		return despatchRequest("validateCurrConversion",currCode);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.findSettledMailbags
	 *	Added by 	:	A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Return type	: 	Page<GPASettlementVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<GPASettlementVO> findSettledMailbags(
			InvoiceSettlementFilterVO filterVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findSettledMailbags");
		return despatchRequest("findSettledMailbags",filterVO);
	}

	/**
	 * 	Method		:	MailTrackingMRADelegate.findUnsettledMailbags
	 *	Added by 	:	A-7531 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<GPASettlementVO>
	 */
	public Collection<GPASettlementVO> findUnsettledMailbags(
			InvoiceSettlementFilterVO filterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findUnsettledMailbags");
		return despatchRequest("findUnsettledMailbags",filterVO);
	}


	/**
	 * 	Method		:	MailTrackingMRADelegate.findMailbagSettlementHistory
	 *	Added by 	:	A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceFiletrVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<InvoiceSettlementHistoryVO>
	 */
	public Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(
			InvoiceSettlementFilterVO invoiceFiletrVO)throws BusinessDelegateException {

		return despatchRequest("findMailbagSettlementHistory",invoiceFiletrVO);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.saveSettlementsAtMailbagLevel
	 *	Added by 	:	A-7871 on 24-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param gpaSettlementVOs
	 *	Parameters	:	@return
	 *	Return type	: 	void
	 */
	public void saveSettlementsAtMailbagLevel
			(Collection<GPASettlementVO> gpaSettlementVOs)throws BusinessDelegateException {
		log.entering(CLASS_NAME,"saveSettlementsAtMailbagLevel");
		despatchRequest("saveSettlementsAtMailbagLevel",gpaSettlementVOs);
		log.exiting(CLASS_NAME,"saveSettlementsAtMailbagLevel");
	}

	/**
	 * 	Method		:	MailTrackingMRADelegate.listInvoicDetails
	 *	Added by 	:	A-8464 on 21-Nov-2018
	 * 	Used for 	:
	 */
	public Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"listInvoicDetails");
		 return despatchRequest("listInvoicDetails",invoicFilterVO);
	}

	/**
	 * 	Method		:	MailTrackingMRADelegate.listInvoic
	 *	Added by 	:	A-8527 on 18-Dec-2018
	 * 	Used for 	:
	 */
	public Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO,int pageNumber)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"listInvoic");
		 return despatchRequest("listInvoic",invoicFilterVO,pageNumber);
	}
	public void rejectProcessedInvoic(Collection<LockVO> locks, boolean saveflag,Collection <InvoicVO>rejectrecords)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"rejectProcessedInvoic");
		 dispatchAsyncRequest("updateInvoicReject",locks,saveflag,rejectrecords);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.listInvoic
	 *	Added by 	:	A-8527 on 18-Dec-2018
	 * 	Used for 	:
	 */
	public void updateInvoicReject(Collection <InvoicVO>rejectrecords)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"updateInvoicReject");
		 despatchRequest("updateInvoicReject",rejectrecords);
	}
	/**
	 * @author a-8464
	 * @throws BusinessDelegateException
	 */
	public void saveRemarkDetails(InvoicDetailsVO invoicDetailsVO)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME,"saveRemarkDetails");
		despatchRequest("saveRemarkDetails", invoicDetailsVO);
		log.exiting(CLASS_NAME,"saveRemarkDetails");
	}

	/**
	 * @author a-8464
	 * @throws BusinessDelegateException
	 */
	public void saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVOs)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME,"saveClaimDetails");
		despatchRequest("saveClaimDetails", invoicDetailsVOs);
		log.exiting(CLASS_NAME,"saveClaimDetails");
	}

	/**
	 * @author a-8464
	 * @throws BusinessDelegateException
	 */
	public void updateProcessStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String processStatus)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME,"updateProcessStatus");
		despatchRequest("updateProcessStatus", invoicDetailsVOs, processStatus);
		log.exiting(CLASS_NAME,"updateProcessStatus");
	}


	/**
	 * @author a-8464
	 * @throws BusinessDelegateException
	 */
	public void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave)
	throws BusinessDelegateException {
		log.entering(CLASS_NAME,"saveGroupRemarkDetails");
		despatchRequest("saveGroupRemarkDetails", invoicFilterVO, groupRemarksToSave);
		log.exiting(CLASS_NAME,"saveGroupRemarkDetails");
	}
/**
 * 	Method		:	MailTrackingMRADelegate.importMailsFromCarditData
 *	Added by 	:	A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	void
 */
	public void importMailsFromCarditData(
			DocumentBillingDetailsVO documentBillingDetailsVO, String txnlogInfo)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "importMailsFromCarditData");
		dispatchAsyncRequest("importMailsFromCarditData",true, documentBillingDetailsVO,txnlogInfo);
	}

	/**
	 * @author a-8464
	 * @throws BusinessDelegateException
	 */
	// Commenting as part of ICRD-319850
/*	public Page<InvoicSummaryVO> findInvoicLov(InvoicFilterVO invoicFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME,"findInvoicLov");
		return despatchRequest("findInvoicLov", invoicFilterVO);

	}*/
	/**
	 * 	Method		:	MailTrackingMRADelegate.findMailbagExistInMRA
	 *	Added by 	:	A-4809 on Jan 2, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	String
	 */
	public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "findMailbagExistInMRA");
		return despatchRequest("findMailbagExistInMRA", documentBillingDetailsVO);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.updateMailStatus
	 *	Added by 	:	A-7929 on Jan 10, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoicDetailsVOs,RaiseClaimFlag
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	:
	 */
	public void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs,String RaiseClaimFlag) throws BusinessDelegateException{
		log.entering(CLASS_NAME,"updateMailStatus");
		despatchRequest("updateMailStatus", invoicDetailsVOs,RaiseClaimFlag);

	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.mailSubClass
	 *	Added by 	:	A-7929 on Jan 22, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,code
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	:
	 * @throws BusinessDelegateException
	 */
	public Collection<MailSubClassVO> findMailSubClass(String companyCode, String code) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"findMailSubClass");
		return despatchRequest("findMailSubClass", companyCode,code);
		}
	/**
	 * 	Method		:	MailTrackingMRADelegate.findMailbagHistories
	 *	Added by 	:	A-7929 on Jan 25, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	:
	 * @throws BusinessDelegateException
	 */
	public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailIdr, long mailSeqNum) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"findMailbagHistories");
		return despatchRequest("findMailbagHistories", companyCode,mailIdr,mailSeqNum);
		}


/**
	 * 	Method		:	MailTrackingMRADelegate.listGPABillingEntries
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	Page<DocumentBillingDetailsVO>
	 */
	public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "listGPABillingEntries");
		log.exiting(CLASS_NAME, "listGPABillingEntries");
		return despatchRequest("listGPABillingEntries",gpaBillingEntriesFilterVO);

	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.listConsignmentDetails
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	Page<ConsignmentDocumentVO>
	 */
	public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)throws BusinessDelegateException {
		log.entering(CLASS_NAME, "listConsignmentDetails");
		log.exiting(CLASS_NAME, "listConsignmentDetails");
		return despatchRequest("listConsignmentDetails",gpaBillingEntriesFilterVO);
	}



/**
	 * 	Method		:	MailTrackingMRADelegate.findReasonCodes
	 *	Added by 	:	A-7531 on 05-Feb-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param systemParCodes
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<CRAParameterVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<CRAParameterVO> findReasonCodes(String companyCode, String systemParCodes) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"findReasonCodes");
		return despatchRequest("findReasonCodes", companyCode,systemParCodes);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.listClaimDetails
	 *	Added by 	:	A-8527 on  15-March-2019
	 * 	Used for 	:
	 */
	public Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"listClaimDetails");
		 return despatchRequest("listClaimDetails",invoicFilterVO,pageNumber);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.generateandClaimDetails
	 *	Added by 	:	A-8527 on 15-March-2019
	 * 	Used for 	:
	 */
	public Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)throws BusinessDelegateException {
		 log.entering(CLASS_NAME,"listGenerateClaimDetails");
		 return despatchRequest("listGenerateClaimDetails",invoicFilterVO,pageNumber);
	}
     /**
	 * 	Method		:	MailTrackingMRADelegate.validateFrmToDateRange
	 *	Added by 	:	A-8527 on 15-March-2019
	 * 	Used for 	:
	 */
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange(InvoicFilterVO invoicFilterVO)throws BusinessDelegateException {

		log.entering(CLASS_NAME,"validateFrmToDateRange");
		 return despatchRequest("validateFrmToDateRange",invoicFilterVO);
	}
   /**
	 * 	Method		:	MailTrackingMRADelegate.validateuspsPacode
	 *	Added by 	:	A-8527 on 15-March-2019
	 * 	Used for 	:
	 */
	public Map<String,String>validateuspsPacode(Collection <String> systemParameters)throws BusinessDelegateException {
		log.entering(CLASS_NAME,"validateuspsPacode");
		 return despatchRequest("validateuspsPacode",systemParameters);
	}
	/**
	 *
	 * @param locks
	 * @param saveflag
	 * @param invoicVO
	 * @throws BusinessDelegateException
	 */
	public void processInvoic(Collection<LockVO> locks, boolean saveflag,InvoicVO invoicVO) throws BusinessDelegateException {
		dispatchAsyncRequest("processInvoic",locks, true, invoicVO);
	}

	/**
	 *
	 * @param invoicDetailsVOs
	 * @return
	 * @throws BusinessDelegateException
	 */
	public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws BusinessDelegateException {
		return despatchRequest("reprocessInvoicMails",invoicDetailsVOs);
	}

	/**
	 *
	 * @param companyCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	public int checkForProcessCount(String companyCode,InvoicVO invoicVO) throws BusinessDelegateException {
		return despatchRequest("checkForProcessCount",companyCode,invoicVO);
	}
	//Added as part of ICRD-329873
	public String getmcastatus(CCAdetailsVO ccaDetailsVO) throws BusinessDelegateException {
		return despatchRequest("getmcastatus",ccaDetailsVO);
	}

	public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum) throws BusinessDelegateException {
		return despatchRequest("findInvoicAndClaimDetails",companyCode,mailSeqnum);
	}
/**
	 * 	Method		:	MailTrackingMRADelegate.generateClaimAndResdits
	 *	Added by 	:	A-4809 on May 16, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@param txnlogInfo
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	void
	 */
	public void generateClaimAndResdits(
			InvoicFilterVO filterVO , String txnlogInfo)throws BusinessDelegateException{
		log.entering(CLASS_NAME, "generateClaimAndResdits");
		dispatchAsyncRequest("generateClaimAndResdits",true, filterVO,txnlogInfo);
	}

	/**
	 * @author A-7540
	 * @param mailbagID
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ResditReceiptVO> getResditInfofromUSPS(String mailbagID)throws BusinessDelegateException {
		log.entering(CLASS_NAME,"getResditInfofromUSPS");
		return despatchRequest("getResditInfofromUSPS", mailbagID);
	}

	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.isClaimGeneraetd
	 *	Added by 	:	A-8061 on 20-Jun-2019
	 * 	Used for 	:	ICRD-262451
	 *	Parameters	:	@param invoicFilterVO
	 *	Parameters	:	@return
	 *	Return type	: 	boolean
	 */
	public Boolean isClaimGenerated(InvoicFilterVO invoicFilterVO)throws BusinessDelegateException {
		return despatchRequest("isClaimGenerated", invoicFilterVO);
	}

	public Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String craParInvgrp)throws BusinessDelegateException{
		return despatchRequest("findCRAParameterDetails", companyCode,craParInvgrp);
	}

	/**
	 *
	 * @param invoiceLovVO
	 * @return Page<InvoiceLovVO>
	 * @throws BusinessDelegateException
	 */
	public InvoiceLovVO findInvoiceNumber(InvoiceLovVO invoiceLovVO)
	throws BusinessDelegateException {

		return despatchRequest("findInvoiceNumber", invoiceLovVO);
	}

	/**
	 *
	 * 	Method		:	MailTrackingMRADelegate.isInitiatedInvoic
	 *	Added by 	:	A-5219 on 20-Apr-2020
	 * 	Used for 	:
	 *	Parameters	:	@param invoicVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	: 	boolean
	 */
	public boolean isInitiatedInvoic(InvoicVO invoicVO)
			throws BusinessDelegateException{
		return despatchRequest("isInitiatedInvoic", invoicVO);
	}
/**
	 * @author A-5526
	 * @param companyCode
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<LockVO> generateINVOICProcessingLock(String companyCode)
		    throws BusinessDelegateException
		  {
		    return despatchRequest("generateINVOICProcessingLock",companyCode);
		  }

	/**
	 * 	Method		:	MailTrackingMRADelegate.findApprovedForceMajeureDetails
	 *	Added by 	:	A-5526 on Jun 15, 2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Return type	:
	 * @throws BusinessDelegateException
	 */
	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailIdr, long mailSeqNum) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"findApprovedForceMajeureDetails");
		return despatchRequest("findApprovedForceMajeureDetails", companyCode,mailIdr,mailSeqNum);
		  }


	public void updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO)throws BusinessDelegateException  {
		log.entering(CLASS_NAME,"updateRouteAndReprorate");
		dispatchAsyncRequest("updateRouteAndReprorate", true, gPABillingEntriesFilterVO);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.saveBillingSchedulemaster
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void saveBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "saveBillingSchedulemaster");
		despatchRequest("saveBillingSchedulemaster",billingScheduleDetailsVO);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.findBillingType
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	Page
	 */
	public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO, int pageNumber)throws BusinessDelegateException {
		 log.entering(CLASS_NAME, "findBillingType");
		return despatchRequest("findBillingType",billingScheduleFilterVO,pageNumber);
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.validateBillingSchedulemaster
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	boolean
	 */
	public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "validateBillingSchedulemaster");
		return despatchRequest("validateBillingSchedulemaster",billingScheduleDetailsVO);
		
	}
	/**
	 * 	Method		:	MailTrackingMRADelegate.generatePASSFile
	 *	Added by 	:	A-4809 on 12-Mar-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void generatePASSFile(GeneratePASSFilterVO passFilterVO) throws BusinessDelegateException{
		log.entering(CLASS_NAME, "generatePASSFile");
		dispatchAsyncRequest("generatePASSFile",true,passFilterVO);
	}
	
	public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws BusinessDelegateException {
		return despatchRequest("checkForRejectionMailbags",companyCode,invoicVO);
	}	
    /**
     * 	Method		:	MailTrackingMRADelegate.listPaymentBatchDetails
     *	Added by 	:	A-4809 on 12-Nov-2021
     * 	Used for 	:
     *	Parameters	:	@param paymentBatchFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws BusinessDelegateException 
     *	Return type	: 	Page<PaymentBatchDetailsVO>
     */
    public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO) throws BusinessDelegateException{
    	log.entering(CLASS_NAME, "listPaymentBatchDetails");
    return despatchRequest("listPaymentBatchDetails",paymentBatchFilterVO);
    }
	
	/**
	 * 
	 * 	Method		:	MailTrackingMRADelegate.uploadSettlementAsyc
	 *	Added by 	:	A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param settlementBatchHeaderVO
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void uploadPaymentBatchDetail(PaymentBatchFilterVO batchFilterVO, InvoiceTransactionLogVO invoiceTransactionLogVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"uploadPaymentBatchDetail");
		dispatchAsyncRequest("uploadPaymentBatchDetail",true, batchFilterVO,invoiceTransactionLogVO);
		
	}
	
	/**
     * 	Method		:	MailTrackingMRADelegate.findSettlementBatches
     *	Added by 	:	A-3429 on 18-Nov-2021
     * 	Used for 	:
     *	Parameters	:	@param paymentBatchFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws BusinessDelegateException 
     *	Return type	: 	Page<PaymentBatchDetailsVO>
     */
    public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO) throws BusinessDelegateException{
    	log.entering(CLASS_NAME, "findSettlementBatches");
    return despatchRequest("findSettlementBatches",paymentBatchFilterVO);
    }
    
    /**
     * 	Method		:	MailTrackingMRADelegate.findSettlementBatchDetails
     *	Added by 	:	A-3429 on 18-Nov-2021
     * 	Used for 	:
     *	Parameters	:	@param paymentBatchFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws BusinessDelegateException 
     *	Return type	: 	Page<PaymentBatchDetailsVO>
     */
    public Page<PaymentBatchSettlementDetailsVO> findSettlementBatchDetails(MailSettlementBatchFilterVO mailSettlementBatchFilterVO) throws BusinessDelegateException{
    	log.entering(CLASS_NAME, "findSettlementBatchDetails");
    return despatchRequest("findSettlementBatchDetails",mailSettlementBatchFilterVO);
    }
/**
 * 	@author A-8331   
 * @param advancePaymentVO
 * @throws BusinessDelegateException
 */

public void saveAdvancePaymentDetails( PaymentBatchDetailsVO advancePaymentVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME,"saveAdvancePaymentDetails");
		 despatchRequest("saveAdvancePaymentDetails",advancePaymentVO);	
		 log.exiting(CLASS_NAME,"saveAdvancePaymentDetails");
	}

    public Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws BusinessDelegateException {
		log.entering(CLASS_NAME, "findPASSFileNames");
		return despatchRequest("findPASSFileNames",fileNameLovVO);
    }


	public int findGPASettlementBatchUploadFileCount(PaymentBatchFilterVO paymentBatchFilterVO)throws BusinessDelegateException{
    	log.entering(CLASS_NAME, "findSettlementBatches");
    	return despatchRequest("findGPASettlementBatchUploadFileCount",paymentBatchFilterVO);
    }

/**
 * 	Method		:	MailTrackingMRADelegate.deletePaymentBatchAttachment
 *	Added by 	:	A-4809 on 02-Dec-2021
 * 	Used for 	:
 *	Parameters	:	@param paymentBatchDetailsVO
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	void
 */
public void deletePaymentBatchAttachment(PaymentBatchDetailsVO paymentBatchDetailsVO) throws BusinessDelegateException{
	log.entering(CLASS_NAME, "deletePaymentBatchAttachment");
	 despatchRequest("deletePaymentBatchAttachment",paymentBatchDetailsVO);	
	log.exiting(CLASS_NAME, "deletePaymentBatchAttachment");
}
/**
 * @author A-8331
 * @param advancePaymentVO
 * @throws BusinessDelegateException
 */
public void deletePaymentBatch(PaymentBatchDetailsVO advancePaymentVO) throws BusinessDelegateException {
	despatchRequest("deletePaymentBatch", advancePaymentVO);
	
}

/**
 * 	Method		:	MailTrackingMRADelegate.updateBatchAmount
 *	Added by 	:	A-4809 on 11-Jan-2022
 * 	Used for 	:
 *	Parameters	:	@param advancePaymentVO
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	void
 */
public void updateBatchAmount( PaymentBatchDetailsVO batchVO) throws BusinessDelegateException {
	log.entering(CLASS_NAME,"updateBatchAmount");
	 despatchRequest("updateBatchAmount",batchVO);	
	 log.exiting(CLASS_NAME,"updateBatchAmount");
}
	
/**
 * 
 * 	Method		:	MailTrackingMRADelegate.clearBatch
 *	Added by 	:	A-10647 on 27-Jan-2022
 * 	Used for 	:
 *	Parameters	:	@param settlementDetailVO
 *	Parameters	:	@param batchDetailVO
 *	Parameters	:	@throws BusinessDelegateException 
 *	Return type	: 	void
 */
public void clearBatchDetails(PaymentBatchDetailsVO batchDetailVO) throws BusinessDelegateException{
	log.entering(CLASS_NAME,CLEAR_BATCH_DETAILS);
	 despatchRequest(CLEAR_BATCH_DETAILS,batchDetailVO);	
	 log.exiting(CLASS_NAME,CLEAR_BATCH_DETAILS);
}

/**
 * 	Method		:	MailTrackingMRADelegate.changeEnddate
 *	Added by 	:	204569 on 12-Dec-2022
 * 	Used for 	:
 *	Parameters	:	@param BillingLineVO
 **	Parameters	:	@param changeEndDate
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void changeEnddate(Collection<BillingLineVO> selectedBlgLineVOs,String changeEndDate) throws BusinessDelegateException {
	log.entering(CLASS_NAME, "changeEnddate");
	despatchRequest("changeEnddate", selectedBlgLineVOs,changeEndDate);
	
}

/**
 * 	Method		:	MailTrackingMRADelegate.changeBillingMatrixStatusUpdate
 *	Added by 	:	204569 on 12-Dec-2022
 * 	Used for 	:
 *	Parameters	:	@param BillingLineVO
 **	Parameters	:	@param changeStatus
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 * @throws BusinessDelegateException 
 */
public void changeBillingMatrixStatusUpdate(BillingMatrixFilterVO billingMatrix, String changedStatus) throws BusinessDelegateException {
	log.entering(CLASS_NAME, "changeBillingMatrixStatus");
	dispatchAsyncRequest("changeBillingMatrixStatusUpdate",true, billingMatrix,changedStatus);
	
}

public Collection<LockVO> autoMCALock(String companyCode) throws BusinessDelegateException {
	log.entering(CLASS_NAME, "autoMCALock");
	return despatchRequest("autoMCALock", companyCode);
}

	}





