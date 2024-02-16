/*
 * MailTrackingMRABI.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFileLogVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.RebillRemarksDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.cra.defaults.vo.tk.AOInvoiceReportDetailsVO;
import com.ibsplc.icargo.business.cra.miscbilling.blockspace.flight.utilization.vo.BlockSpaceFlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
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
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MiscFileFilterVO;
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
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
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


import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.mail.mra.flown.FlownException;
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
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
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
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.sprout.multitenant.flow.FlowDescriptor;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interfaces.BusinessInterface;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 * Revision History
 *
 * Version Date Author Description
 *
 * 0.1 Jan 8, 2007 Philip Initial draft 0.2 Jan 18,2007 Kiran Added the method
 * findAllRateCards
 *
 *
 */
public interface MailTrackingMRABI extends BusinessInterface{

	/**
	 * @author A-3434 Finds and returns the GpaBillingInvoicedetails
	 *
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
			throws RemoteException, SystemException;

	/**
	 * @author A-3434 .save the GpaBillingInvoicedetails
	 *
	 * @param cN66DetailsVO
	 *
	 * @throws RemoteException
	 * @throws SystemException
	 */
	@AvoidForcedStaleDataChecks
	public void saveBillingStatus(CN66DetailsVO cN66DetailsVO)
			throws RemoteException, SystemException,MailTrackingMRABusinessException;

	/**
	 * Saves the rate card and assosciated rate lines Used for the
	 * creation,updation of rate cards and creation,updation and deletion of
	 * rate lines based on the operation flag set in argument
	 *
	 * @param rateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @throws RateCardException
	 * @throws RateLineException
	 */
	public void saveRateCard(RateCardVO rateCardVO) throws SystemException,
			RemoteException, MailTrackingMRABusinessException;



	/**
	 * Finds the rate card and assosciated rate lines
	 *
	 * @param companyCode
	 * @param rateCardID
	 * @return RateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public RateCardVO findRateCardDetails(String companyCode, String rateCardID,int pagenum)
			throws SystemException, RemoteException;

	/**
	 * Returns the ratelines based on the filter criteria
	 *
	 * @param rateLineFilterVO
	 * @return Page<RateLineVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<RateLineVO> findRateLineDetails(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			RemoteException;

	/**
	 * Saves the rateLine Status
	 *
	 * @param rateLineVOs
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	// TODO Collection<RateLineVO>
	public void saveRatelineStatus(Collection<RateLineVO> rateLineVOs)
			throws MailTrackingMRABusinessException, SystemException,
			RemoteException;

	/**
	 * Finds the rate cards based on the filter
	 *
	 * @author a-2049
	 * @param rateCardFilterVO
	 * @return Page<RateCardVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<RateCardVO> findAllRateCards(RateCardFilterVO rateCardFilterVO)
			throws SystemException, RemoteException;

	/**
	 * method for changing the rateCard status from ViewRateCard Screen
	 *
	 * @author a-2049
	 * @param rateCardVOs
	 *            the collection of rateCardVOs with status being set with the
	 *            new status selected by the user
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void changeRateCardStatus(Collection<RateCardVO> rateCardVOs)
			throws MailTrackingMRABusinessException, SystemException,
			RemoteException;

	/**
	 * @author A-2408
	 * @param companyCode
	 * @param rateCardId
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<RateCardLovVO> findRateCardLov(String companyCode,
			String rateCardId, int pageNumber) throws SystemException,
			RemoteException;

	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<CN51SummaryVO> findAllInvoices(
			CN51SummaryFilterVO cn51SummaryFilterVO) throws RemoteException,
			SystemException;

	/**
	 * @author A-2280 Finds and returns the CN51 and CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return CN51CN66VO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public CN51CN66VO findCN51CN66Details(CN51CN66FilterVO cn51CN66FilterVO)
			throws RemoteException, SystemException;

	/* Added by A-2391 */
	/**
	 * Finds tand returns the GPA Billing entries available This includes
	 * billed, billable and on hold despatches
	 *
	 * @param gpaBillingEntriesFilterVO
	 * @return Collection<GPABillingDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public Page<DocumentBillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws RemoteException, SystemException;

	/**
	 * Changes the staus of GPA billing entries to Billable/On Hold
	 *
	 * @param gpaBillingStatusVO
	 * @throws RemoteException
	 * @throws SystemException
	 */

	/*@AvoidForcedStaleDataChecks
	public void changeBillingStatus(
			Collection<GPABillingStatusVO> gpaBillingStatusVO)
			throws RemoteException, SystemException;
*/
	/* Added by A-2391 ends */

	/**
	 * Saves the remarks against CN66 details
	 *
	 * @param cn66DetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	// TODO Collection<CN66DetailsVO>
	public void saveCN66Observations(Collection<CN66DetailsVO> cn66DetailsVO)
			throws RemoteException, SystemException;

	/**
	 * generates CN66 and CN51
	 *
	 * @param generateInvoiceFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	/* Commented the method as part of ICRD-153078
	@AvoidForcedStaleDataChecks
	public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;*/

	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO invoiceLovVO)
			throws SystemException, RemoteException;

	/**
	 * This method is for findFlownMails
	 *
	 * @param flownMailFilterVO
	 * @return
	 */
	public FlownMailSegmentVO findFlownMails(FlownMailFilterVO flownMailFilterVO)
			throws SystemException, RemoteException;

	/**
	 * This method is for closeFlight
	 *
	 * @param flownMailFilterVO
	 */
	/* Commented the method as part of ICRD-153078
	@AvoidForcedStaleDataChecks
	public void closeFlight(FlownMailFilterVO flownMailFilterVO)
			throws SystemException, RemoteException;*/

	/**
	 * This method is for validateFlight
	 *
	 * @param flightFilterVO
	 * @return
	 */

	@AvoidForcedStaleDataChecks
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException,
			RemoteException;

	/**
	 * This method is for findFlightDetails
	 *
	 * @param flownMailFilterVO
	 * @return
	 */
	public Collection<FlownMailSegmentVO> findFlightDetails(
			FlownMailFilterVO flownMailFilterVO) throws SystemException,
			RemoteException;

	/**
	 * added for upucalendar
	 *
	 * @param upuCalendarFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws RemoteException,
			SystemException;

	/**
	 *
	 * @param upuCalendarVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveUPUCalendarDetails(Collection<UPUCalendarVO> upuCalendarVOs)
			throws RemoteException, SystemException;

	/**
	 * Method to list CN66 details
	 *
	 * @param cn66FilterVo
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2518
	 */
	public Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws RemoteException,
			SystemException;

	/**
	 * Method to print CN66 details
	 *
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-2458
	 */
	public Map<String, Object> findCN66DetailsPrint(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * Method to save CN66 details
	 *
	 * @param cn66DetailsVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2518
	 */
	public void saveCN66Details(Collection<AirlineCN66DetailsVO> cn66DetailsVos)
			throws RemoteException, SystemException;

	/**
	 * Added by A-2401 Method to findFlownMailExceptions
	 *
	 * @param FlownMailFilterVO
	 * @throws RemoteException
	 */

	public Collection<FlownMailExceptionVO> findFlownMailExceptions(
			FlownMailFilterVO flownMailFilterVO) throws RemoteException,
			SystemException;

	/**
	 * Added by A-2401 Method to assignFlownMailExceptions
	 *
	 * @param flownMailExceptionVOs
	 * @throws RemoteException
	 */

	public void assignFlownMailExceptions(
			Collection<FlownMailExceptionVO> flownMailExceptionVOs)
			throws RemoteException, SystemException;

	/**
	 * Added by A-2401 Method to processFlight
	 *
	 * @param flownMailFilterVOs
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void processFlight(FlownMailFilterVO flownMailFilterVO)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * Added by A-2401 Method to validateUsers
	 *
	 * @param Collection
	 *            userCodes
	 * @param companyCode
	 */
	@AvoidForcedStaleDataChecks
	public Collection<ValidUsersVO> validateUsers(Collection<String> userCodes,
			String companyCode) throws RemoteException, SystemException;

	/**
	 *
	 * @param companyCode
	 * @param iataClearamcePrd
	 * @return UPUCalendarVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	@AvoidForcedStaleDataChecks
	public UPUCalendarVO validateIataClearancePeriod(String companyCode,
			String iataClearamcePrd) throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * added by A-2397
	 *
	 * @param Collection
	 *            <MemoInInvoiceVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	@AvoidForcedStaleDataChecks
	public void saveMemo(Collection<MemoInInvoiceVO> memoInInvoiceVos)
			throws RemoteException, SystemException;

	/**
	 * added by A-2397
	 *
	 * @param MemoFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
			throws RemoteException, SystemException;

	/**
	 *
	 * @param gpaReportingDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveGPAReportingDetails(
			Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public AirlineCN51SummaryVO findCN51Details(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 *
	 * @param gpaReportFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<GPAReportingDetailsVO> findGPAReportingDetails(
			GPAReportingFilterVO gpaReportFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @author A-2391
	 * @param filterVO
	 * @return Collection<ExceptionInInvoiceVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO filterVO) throws RemoteException,
			SystemException;

	/**
	 * @author A-2391
	 * @param exceptionInInvoiceVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public void acceptAirlineInvoices(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws RemoteException, SystemException;

	/**
	 * @author A-2391
	 * @param exceptionInInvoiceVO
	 * @return RejectionMemoVO
	 * @throws SystemException
	 * @throws RemoteException
	 */

	public RejectionMemoVO saveRejectionMemo(
			ExceptionInInvoiceVO exceptionInInvoiceVO) throws RemoteException,
			SystemException;

	/**
	 * Method to print Exception in invoice
	 *
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @author A-2399
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> printExceptionInInvoice(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2391
	 * @param exceptionInInvoiceVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */

	@AvoidForcedStaleDataChecks
	public void deleteRejectionMemo(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws RemoteException, SystemException;

	/**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<GPAReportingClaimDetailsVO> findClaimDetails(
			GPAReportingFilterVO gpaReportingFilterVO) throws RemoteException,
			SystemException;

	/**
	 *
	 * @param invoiceLovFilterVO
	 * @return Page<AirlineInvoiceLovVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws RemoteException,
			SystemException;

	/**
	 *
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return Page<MemoLovVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<MemoLovVO> displayMemoLOV(String companyCode, String memoCode,
			int pageNumber) throws RemoteException, SystemException;

	/**
	 * @author A-2280
	 * @param gpaReportingCliamDetailVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void assignClaims(
			Collection<GPAReportingClaimDetailsVO> gpaReportingCliamDetailVOs)
			throws RemoteException, SystemException;

	/**
	 * This method is used for GpaReportProcessing
	 *
	 * @author a-2270
	 * @param filterVo
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void processGpaReport(GPAReportingFilterVO filterVo)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 *
	 * @param airlineCN51SummaryVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveCN51(AirlineCN51SummaryVO airlineCN51SummaryVO)
			throws RemoteException, SystemException;

	/**
	 *
	 * @param reportSpec
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	@FlowDescriptor(flow="GenerateMailCN66Report_Flow", inputs={"reportSpec"}, output="cn66Details")
	public Map<String, Object> generateCN66Report(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN51Report(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2398
	 * @param blgMtxFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public BillingMatrixVO findBillingMatrixDetails(
			BillingMatrixFilterVO blgMtxFilterVO) throws RemoteException,
			SystemException, MailTrackingMRABusinessException;

	/**
	 * @author A-2398
	 * @param blgLineFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Page<BillingLineVO> findBillingLineDetails(
			BillingLineFilterVO blgLineFilterVO) throws RemoteException,
			SystemException, MailTrackingMRABusinessException;

	/**
	 * @author A-2398
	 * @param blgLineFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Page<BillingLineVO> findBillingLineValues(
			BillingLineFilterVO blgLineFilterVO) throws RemoteException,
			SystemException, MailTrackingMRABusinessException;

	/**
	 * @author A-2398
	 * @param billingMatrixVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public boolean saveBillingMatrix(BillingMatrixVO billingMatrixVO)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO)
			throws RemoteException, SystemException;

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceReport(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2408
	 * @param cn66FilterVo
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2391 This method is to printExceptionReportDetail
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionReportDetail(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2391 This method is to printRejectionMemo
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printRejectionMemo(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * Method for changing the billing matrix and billing line status.
	 *
	 * @author A-1872 Mar 5, 2007
	 * @param billingMatrixVOs
	 * @param billingLineVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void saveBillingLineStatus(
			Collection<BillingMatrixVO> billingMatrixVOs,
			Collection<BillingLineVO> billingLineVOs) throws SystemException,
			RemoteException, MailTrackingMRABusinessException;

	/**
	 * @param companyCode
	 * @param billingMatrixCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,
			String billingMatrixCode, int pageNumber) throws SystemException,
			RemoteException;

	/**
	 *
	 * @param airlineExceptionsFilterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @exception SystemException
	 * @exception RemoteException
	 */
	public Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws RemoteException, SystemException;

	/**
	 * Method to save CN66 details
	 *
	 * @param airlineExceptionsVOs
	 * @return void
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws FinderException
	 * @author A-2407
	 */
	public void saveAirlineExceptions(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws RemoteException, SystemException;

	/**
	 * This method displays Mail Proration Details
	 *
	 * @author a-2518
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException,
			RemoteException;

	/**
	 * This method is to print Exceptions Report by Assignee Details
	 *
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportAssigneeDetails(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * This method is to print Exceptions Report by Assignee Summary
	 *
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportAssigneeSummary(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * This method is to print Exceptions Report Details
	 *
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportDetails(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * This method is to print Exceptions Report Summary
	 *
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportSummary(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * This method is to generate outward billing invoice
	 *
	 * @author A-2521
	 * @param invoiceFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 *
	 * @param gprMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/* Commented the method as part of ICRD-153078
	void saveGPRMessageDetails(GPRMessageVO gprMessageVO)
			throws SystemException, RemoteException;*/

	/**
	 * @author A-2449
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findListOfFlightsForReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2449
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findListOfFlownMailsForReport(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 *
	 * @param gpaReportMessageVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void uploadGPAReport(GPAReportMessageVO gpaReportMessageVO)
			throws SystemException, RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findBlgSmyPeriodWiseDetailsForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findBlgSmyGpaWiseDetailsForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN51DetailsPeriodWiseForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN51DetailsGPAWiseForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN66DetailsPeriodWiseForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN66DetailsGPAWiseForPrint(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findInvoiceDetailsForReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2521
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceByClrPrd(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2521
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceByAirline(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2458
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */

	public Map<String, Object> findInvoicesCollectionByClrPrd(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2458
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findInvoicesCollectionByAirline(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * Method to view the CN51s for a period range
	 *
	 * @param filterVO
	 * @return Collection<AirlineCN51SummaryVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<AirlineCN51SummaryVO> findCN51s(
			AirlineCN51FilterVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillableReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillableReportTK(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException;

	/**
	 * lists the Proration factors
	 *
	 * @param prorationFactorFilterVo
	 * @return Collection<ProrationFactorVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @author a-2518
	 */
	public Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws SystemException, RemoteException;

	/**
	 * Saves the Proration factors
	 *
	 * @param prorationFactorVos
	 * @return void
	 * @throws SystemException
	 * @throws RemoteException
	 * @author a-2518
	 */
	public void saveProrationFactors(
			Collection<ProrationFactorVO> prorationFactorVos)
			throws SystemException, RemoteException;

	/**
	 * This method changes the status. Possible values of status are "New,
	 * Active, Inactive and Cancelled"
	 *
	 * @param prorationFactorVo
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @author a-2518
	 */
	public void changeProrationFactorStatus(ProrationFactorVO prorationFactorVo)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<GPASettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO,int displayPage)
			throws SystemException, RemoteException;

	/**
	 * @author A-2280
	 * @param invoiceSettlementVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void saveSettlementDetails(
			Collection<GPASettlementVO> gpaSettlementVOs)
			throws SystemException, RemoteException, MailTrackingMRABusinessException;

	/**
	 * @author A-2408
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	/*public Map<String, Object> printReconciliationReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;*/

	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)
			throws SystemException, RemoteException;

	/**
	 * a-2122
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findOutwardRejectionMemo(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * Finds mail contract details
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return MailContractVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @author A-2518
	 */
	public MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
			throws SystemException, RemoteException;

	/**
	 * @author a-2524
	 * @param mailSLAVo
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveMailSla(MailSLAVO mailSLAVo) throws SystemException,
			RemoteException;

	/**
	 * @author a-2524
	 * @param companyCode
	 * @param slaId
	 * @return MailSLAVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public MailSLAVO findMailSla(String companyCode, String slaId)
			throws SystemException, RemoteException;

	/**
	 * Saves mail contract details
	 *
	 * @author A-2518
	 * @param mailContractVo
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void saveMailContract(MailContractVO mailContractVo)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * Changes the agreement status - Possible values for status can be -
	 * <li>A - Active, C - Cancelled</li>
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param agreementStatus
	 * @throws SystemException
	 * @throws RemoteException
	 * @author A-2518
	 */
	public void changeMailContractStatus(
			Collection<MailContractVO> mailContractVOs) throws SystemException,
			RemoteException;

	/**
	 * Displays Version numbers for Version LOV
	 *
	 * @param companyCode
	 * @param contractReferenceNumber
	 * @param versionNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @author A-2518
	 */
	public Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws SystemException, RemoteException;

	/**
	 * Added by A-2521 for ContractRefNo Lov
	 *
	 * @param contractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO) throws SystemException,
			RemoteException;

	/**
	 * Added by A-2521 for SLAId Lov
	 *
	 * @param contractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SLADetailsVO> displaySLADetails(SLAFilterVO slaFilterVO)
			throws SystemException, RemoteException;

	/**
	 * Finds the rate card and assosciated rate lines
	 *
	 * @param companyCode
	 * @param rateCardID
	 * @return RateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @author a-2049
	 * @param companyCode
	 * @param gpaCode
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public PostalAdministrationVO findPostalAdminDetails(String companyCode,
			String gpaCode) throws SystemException, RemoteException;

	/**
	 * @author a-2407
	 * @param reportspec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> findCN51DetailsReport(ReportSpec reportspec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * This method validates billing matrix codes
	 *
	 * @author A-2518
	 * @param companyCode
	 * @param billingMatrixCodes
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void validateBillingMatrixCodes(String companyCode,
			Collection<String> billingMatrixCodes) throws SystemException,
			RemoteException, MailTrackingMRABusinessException;

	/**
	 * This method validates Service Level Activity(SLA) codes
	 *
	 * @author A-2518
	 * @param companyCode
	 * @param slaCodes
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void validateSLACodes(String companyCode, Collection<String> slaCodes)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @param companyCode
	 * @param despatch
	 * @param gpaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode, String dsn,
			String despatch, String gpaCode, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findFlownMailExceptionsforprint(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findFlownMailExceptionsforprintDetails(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @author a-2518
	 * @param reportingPeriodFilterVo
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Boolean validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws RemoteException, SystemException;

	/**
	 * @author a-2391
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public RejectionMemoVO findRejectionMemo(RejectionMemoFilterVO filterVO)
			throws RemoteException, SystemException;

	/**
	 * @author a-2391
	 * @param rejectionMemoVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	@AvoidForcedStaleDataChecks
	public void updateRejectionMemo(RejectionMemoVO rejectionMemoVO)
			throws RemoteException, SystemException;

	/**
	 *
	 * @param flightValidationVO
	 * @throws BusinessDelegateException
	 */
	public void importFlownMails(FlightValidationVO flightValidationVO,
			Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO,String txnlogInfo)
			throws RemoteException, SystemException;

	/**
	 * Inactivates the billing lines
	 *
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void inActivateBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException;

	/**
	 * Cancels the billing lines
	 *
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException;

	/**
	 * Activates the billing lines
	 *
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void activateBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;

	/**
	 * @param masterVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/* Commented the method as part of ICRD-153078
	public void saveMailInvoicAdv(MailInvoicMasterVO masterVO)
			throws SystemException, RemoteException;*/

	/**
	 *
	 * @param companyCode
	 * @param invoiceNumber
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails(
			MailInvoicClaimsFilterVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(
			MailDOTRateFilterVO filterVO) throws SystemException,
			RemoteException;

	/**
	 * @param mailDOTRateVOs
	 * @throws SystemException
	 */
	public void saveDOTRateDetails(Collection<MailDOTRateVO> mailDOTRateVOs)
			throws SystemException, RemoteException;

	/**
	 * Inactivates the rateLineVOs
	 *
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void inActivateRateLines(Collection<RateLineVO> rateLineVOs)
			throws RemoteException, SystemException;

	/**
	 * Cancels the rateLineVOs
	 *
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelRateLines(Collection<RateLineVO> rateLineVOs)
			throws RemoteException, SystemException;

	/**
	 * Activates the rateLineVOs
	 *
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void activateRateLines(Collection<RateLineVO> rateLineVOs ,boolean isBulkActivation)//Modified by a-7871 for ICRD-223130
			throws RemoteException, SystemException,MailTrackingMRABusinessException ;

	/**
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void importToReconcile(String companyCode) throws SystemException,
			RemoteException;

	/**
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void reconcileProcess(String companyCode) throws SystemException,
			RemoteException;

	/**
	 * This method generates INVOIC Claim file
	 *
	 * @author A-2518
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void generateInvoicClaimFile(String companyCode)
			throws SystemException, RemoteException;

	/**
	 * @author a-2391 This method is used to fetch audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<AuditDetailsVO> findArlAuditDetails(
			MRAArlAuditFilterVO mailAuditFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @param companyCode
	 * @param invoicKey
	 * @param poaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey, String poaCode, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * @author a-3108
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<String> findBillingPeriods(
			GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param companyCode
	 * @param blgbasis
	 * @param despatchDate
	 * @return DespatchEnquiryVO
	 * @throws BusinessDelegateException
	 */
	/* Commented the method as part of ICRD-153078
	public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO) throws SystemException,
			RemoteException;*/

	/**
	 *
	 * @param companyCode
	 * @param blgbasis
	 * @param despatchDate
	 * @return Collection<GPABillingDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<GPABillingDetailsVO> findGPABillingDetails(
			DSNPopUpVO popUpVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param companyCode
	 * @param dsnNum
	 * @param dsnDate
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
			String dsnNum, String dsnDate, int pageNumber)
			throws SystemException, RemoteException;

	/**
	 * @author A-3447
	 * @param summaryVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	void finalizeandSendEInvoice(Collection<CN51SummaryVO> summaryVOs,String eInvoiceMsg) throws SystemException,
			RemoteException;

	/**
	 * @author a-3108
	 * @return Collection<RateAuditVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<RateAuditVO> findRateAuditDetails(
			RateAuditFilterVO rateAuditFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @author A-2391
	 * @param rateAuditFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException,
			RemoteException;


	/**
	 * @author a-3108
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void changeRateAuditDsnStatus(Collection<RateAuditVO> rateAuditVOs,Collection<RateAuditVO> rateAuditVOsForaplyAudit)
			throws SystemException, RemoteException,MailTrackingMRABusinessException;

	/**
	 * @author A-3434
	 * @param formOneFilterVo
	 * @return Page<FormOneVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)
			throws RemoteException, SystemException;

	/**
	 * @author A-3447
	 * @param maintainCCAFilterVO
	 * @return Collection<CCAdetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<CCAdetailsVO> findCCAdetails(MaintainCCAFilterVO maintainCCAFilterVO)
			throws SystemException, RemoteException;

	/**
	 * @author A-3447
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<CCAdetailsVO> findCCA(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException,
			RemoteException;



	/**
	 * @author A-3434
	 * @param interlineFilterVo
	 * @return Collection<AirlineForBillingVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<AirlineForBillingVO> findFormTwoDetails(
			InterlineFilterVO interlineFilterVo) throws RemoteException,
			SystemException;
	/**
	 *
	 * @param formOneVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public FormOneVO listFormOneDetails(FormOneVO formOneVO)throws SystemException, RemoteException ;
	/**
	 * @author A-3456
	 * @param InterlineFilterVO
	 * @throws SystemException
	 * @throws RemoteException
	 */

	 public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)
		throws RemoteException, SystemException;
	 /**
		 *
		 * @param formOneVO
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void saveFormOneDetails(FormOneVO formOneVO)throws SystemException, RemoteException ;

		/**
		 * @author a-3108
		 * @param airlineForBillingVOs
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void saveFormThreeDetails(
				Collection<AirlineForBillingVO> airlineForBillingVOs)
				throws RemoteException, SystemException;
		/**
		 * @author a-3108
		 * @param airlineForBillingVOs
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<AirlineForBillingVO> findFormThreeDetails(
				InterlineFilterVO interlineFilterVO)
				throws RemoteException, SystemException ;
		/**
		 * @author A-3429
		 * @param CCAdetailsVO
		 * @return Collection<CCAdetailsVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */
		public Page<CCAdetailsVO> listCCAs(
				ListCCAFilterVO listCCAFilterVo) throws RemoteException,
				SystemException;


		 /**
		 * @author A-3456
		 * @param AirlineCN51FilterVO
		 * @throws SystemException
		 * @throws RemoteException
		 */

	 public AirlineCN51SummaryVO findCaptureInvoiceDetails(AirlineCN51FilterVO airlineCN51FilterVO)
		throws RemoteException, SystemException;

	 /**
	  * @author a-3447
	  * @param airlineCN51SummaryVO
	  * @throws SystemException
	  * @throws RemoteException
	  */
	 void updateBillingDetailCommand(AirlineCN51SummaryVO airlineCN51SummaryVO) throws SystemException,
		RemoteException;





	/**
	 * @author A-2554
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException, RemoteException;
	/**
	 * @author A-3429
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printCCAsReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;
	/**
	 * @author a-3434
    * this method is for listing the InterlineBillingEntries
    * @param airlineBillingFilterVO
    * @throws BusinessDelegateException
    */
	public Page<DocumentBillingDetailsVO> findInterlineBillingEntries (AirlineBillingFilterVO airlineBillingFilterVO)
	   throws SystemException, RemoteException;


	/**
	 * @author A-3434 . update status and remarks
	 *
	 * @param Collection<DocumentBillingDetailsVO>
	 *
	 * @throws BusinessDelegateException
	 */
	@AvoidForcedStaleDataChecks
	public void changeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException, RemoteException;

	/**
	 * @author A-3434 . update review flag
	 *
	 * @param Collection<DocumentBillingDetailsVO>
	 *
	 * @throws BusinessDelegateException
	 */
	@AvoidForcedStaleDataChecks
	public void changeReview(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException, RemoteException;
	/**
	 * @author a-3108
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABbillingInvoiceEnquiry(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author a-3229
	 * @param prorationDetailsVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveProrationDetails(
			Collection<ProrationDetailsVO> prorationDetailsVO)
			throws RemoteException, SystemException;
	/**
	 * @author A-2391
	 * @param filterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public String findInvoiceListingCurrency(AirlineCN51FilterVO filterVO)
			throws RemoteException, SystemException;


	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return RateAuditVO
	 */
	public  RateAuditVO computeTotalForRateAuditDetails(RateAuditVO newRateAuditVO)
			throws SystemException , RemoteException,MailTrackingMRABusinessException;

	/**
	 * This method is for printing the ProformaInvoiceDiffReport details
	 * @author A-3271
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	Map<String, Object> generateProformaInvoiceDiffReport(ReportSpec reportSpec)
	throws SystemException,RemoteException,MailTrackingMRABusinessException;




	/**Method for issuing rejection memo
	 * @author A-3447
	 * @param rejectionMemoVO
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public String saveRejectionMemos(RejectionMemoVO rejectionMemoVO)	throws RemoteException, SystemException;


	/**
	 * @author A-3251
	 * @param companyCode
	 * @param subclass
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String validateMailSubClass(String companyCode,String subclass)
			throws SystemException , RemoteException;
	/**
	 * A-3429
	 * This method is for finding SectorDetails
	 *
	 * @param flightSectorRevenueFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> findSectorDetails(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) throws SystemException,
			RemoteException;
	/**
	 * A-3429
	 * This method is for finding the revenue for a specified sector
	 *
	 * @param flightSectorRevenueFilterVO
	 * @return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) throws SystemException,
			RemoteException;



	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public  void saveRateAuditDetails(RateAuditVO newRateAuditVO)
			throws SystemException , RemoteException,MailTrackingMRABusinessException;





	/**
	 * A-3229
	 * @param dsnRoutingFilterVO
	 * @return DSNRoutingVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<DSNRoutingVO> findDSNRoutingDetails(
			DSNRoutingFilterVO dsnRoutingFilterVO) throws SystemException,
			RemoteException;


	/**
	 * @author a-3229
	 * @param dsnRoutingVOs
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@FlowDescriptor(flow="SaveMailDSNRoutingDetails_Flow", inputs={"dsnRoutingVOs"})
	public void saveDSNRoutingDetails(
			Collection<DSNRoutingVO> dsnRoutingVOs)
			throws RemoteException, SystemException;

	/**
	 * Added by A-2107 for listUnaccountedDispatches
	 * @param unaccountedDispatchesFilterVO
	 * @return Page<UnaccountedDispatchesDetailsVO>
	 * @throws SystemException
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
			throws SystemException, RemoteException;



	/**
	 * Added by A-2107 for listUnaccountedDispatches
	 * @param unaccountedDispatchesFilterVO
	 * @return Page<UnaccountedDispatchesDetailsVO>
	 * @throws SystemException
	 */
	public Map generateUnaccountedDispachedReport(ReportSpec reportSpec)
			throws RemoteException, SystemException;



	/**
	 * @author A-2554
	 * @param airlineBillingDetailVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			   AirlineBillingDetailVO  airlineBillingDetailVO)
			   throws SystemException , RemoteException;


	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public  void populateInitialDataInTempTables(RateAuditVO newRateAuditVO)
			throws SystemException , RemoteException;


	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public  void removeRateAuditDetailsFromTemp(RateAuditVO newRateAuditVO)
			throws SystemException , RemoteException;




	/**Method for Printing CCA Details
	 * @author A-3447
	 * @param reportSpec
	 * @return Map
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 * */
	public Map<String, Object> printCCAReport(ReportSpec reportSpec)
	throws SystemException, RemoteException,MailTrackingMRABusinessException;



	/**
 	 * @author A-3447
 	 * @param airlineCN51FilterVO
 	 * @return AirlineCN51SummaryVO
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * Method to pick all invoice flags form table
 	 *
 	 */
	public AirlineForBillingVO findAllInvoiceFlags(AirlineCN51FilterVO airlineCN51FilterVO)
		throws SystemException,RemoteException;

	/**
	 * @author A-3429
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPABillingInvoice(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;
/**
	 * @author A-8527
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printGPAInvoiceCoveringrpt(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;
	/**
	 * @param prorationExceptionsFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ProrationExceptionVO> findProrationExceptions (
			ProrationExceptionsFilterVO prorationExceptionsFilterVO)
			throws SystemException, RemoteException;

	/**
	 * @param prorationExceptionVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveProrationExceptions(
			Collection<ProrationExceptionVO> prorationExceptionVOs)
			throws SystemException, RemoteException;

/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 *
	 */
	public Map<String, Object> printProrationExceptionReport(ReportSpec reportSpec)
	throws SystemException, RemoteException;





	/**
	 * A-3229
	 * @param dsnFilterVO
	 * @return MailProrationLogVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MailProrationLogVO> findProrationLogDetails(
			DSNFilterVO dsnFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @author A-3229
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO)throws SystemException, RemoteException;

	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException;

	/**
	 * @author A-3429
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printDocumentStatisticsReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;



	/**
	 * @author A-3429
	 * @param RejectionMemoVO
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String saveRejectionMemoForDsn(RejectionMemoVO rejectionMemoVO)	throws RemoteException, SystemException;

	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public PostalAdministrationDetailsVO validatePoaDetailsForBilling(PostalAdministrationDetailsVO postalAdministrationDetailsVO) throws RemoteException, SystemException;

	/**
	 * @author A-3229
	 * @param irregularityFilterVO
	 * @return Collection<IrregularityVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO irregularityFilteVO)throws SystemException,RemoteException;

	/**
	 * This method is to print Irregularity Report Details
	 *
	 * @author A-3229
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printIrregularityReport(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			MailTrackingMRABusinessException;

	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<MRAAccountingVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException;
	/**
	 * @author A-3229
	 * @param dsnPopUpVO
	 * @return Collection<USPSReportingVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException;



  /**
   * Added by A-2107 for getTotalOfDispatches
   * @param unaccountedDispatchesFilterVO
   * @return UnaccountedDispatchesVO
   * @throws SystemException
   */
  public UnaccountedDispatchesVO getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
  		throws SystemException, RemoteException;

	/**
	 * @author A-3251
	 * @param dsnRoutingVOs
	 *
	 */
  public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws RemoteException,SystemException;






	/**
	 * @author A-3429
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> viewFormTwoPrint(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException;


	/**
	 * @author A-2554
	 * @param cN51SummaryVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public String generateEInvoiceMessage(CN51SummaryVO cN51SummaryVO)
	throws RemoteException, SystemException;

	 /**
	 * @author A-3434
	 * @param outstandingBalanceVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
  public Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)
  throws RemoteException,SystemException;

  /**
   * calls accounting procedure
   * @param filterVO
   * @throws RemoteException
   * @throws SystemException
   * @throws MailTrackingMRABusinessException
   */
	  public void performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs) throws RemoteException,
		SystemException,MailTrackingMRABusinessException;
  /**
	 * @author a-2391
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN66InvoiceReport(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;
	/**
	 * @author A-3429
	 * @param airlineExceptionsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	@AvoidForcedStaleDataChecks
	public void acceptAirlineDsns(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws RemoteException, SystemException;


	/**
 	 * @author A-2391
 	 * @param gpaCode,gpaName,country
 	 * @return Collection<FuelSurchargeVO>
 	 * @throws BusinessDelegateException
 	 */
 	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCod)
 	throws BusinessDelegateException,RemoteException, SystemException;
 	/**
	 * @author A-2391
	 * @param fuelSurchargeVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveFuelSurchargeDetails(Collection<FuelSurchargeVO> fuelSurchargeVOs)
			throws BusinessDelegateException,RemoteException, SystemException;
	/**
	 * @author A-2391
	 * @param ccadetailsVO
	 * @throws BusinessDelegateException
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public void saveHistoryDetails(
			CCAdetailsVO ccadetailsVO)
			throws BusinessDelegateException ,RemoteException, SystemException, MailTrackingMRABusinessException;
	/**
	 * @author A-3434
	 * @param reportSpec
	 * @throws RemoteException
	 * @throws ReportGenerationException
	 * @throws SystemException
	 */
	public Map<String, Object> findRateAuditDetailsPrint(ReportSpec reportSpec)
    throws SystemException, RemoteException, ReportGenerationException,MailTrackingMRABusinessException;

	// Added by A-3434 for Clearance period validation
	/**
	 * @param iataCalenderFilterVO
	 * @return IATACalendarVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public IATACalendarVO validateClearancePeriod(IATACalendarFilterVO iataCalenderFilterVO)
	throws SystemException, RemoteException;
	/**
	 * @author A-3429 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException, RemoteException;
	/**
	 * @author A-3429 Method for PALov containing PACode and PADescription
	 * @param companyCode
	 * @param paCode
	 * @param paName
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<PostalAdministrationVO> findPALov(String companyCode, String paCode,
			String paName, int pageNumber,int defaultSize) throws SystemException,
			RemoteException;

	/*
	 *
	 */
	public void printMailRevenueReport(FlownMailFilterVO flownMailFilterVO) throws SystemException,FlownException,
	RemoteException;

	/**
	 * @author A-2414
	 *
	 * @param mailExceptionReportsFilterVo
	 * @return String - file data
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public String generateMailExceptionReport(MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
	   throws SystemException, RemoteException, MailTrackingMRABusinessException;
	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO)
			throws SystemException, RemoteException;

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> printSettlementDetails(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 *
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(GPASettlementVO gpaSettlementVO) throws SystemException, RemoteException;
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> findPOMailSummaryDetails(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<CCAdetailsVO> findMCALov(CCAdetailsVO ccAdetailsVO, int displayPage) throws SystemException, RemoteException;
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<CCAdetailsVO> findDSNLov(CCAdetailsVO ccAdetailsVO, int displayPage) throws SystemException, RemoteException;
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN51ReportFromCN51Cn66(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 * @author a-4823
	 * @param cCADetailsVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @throws BusinessDelegateException
	 */
	String saveMCAdetails(CCAdetailsVO cCADetailsVO) throws SystemException,
	RemoteException,MailTrackingMRABusinessException, BusinessDelegateException;
	/**
	 *
	 * @param maintainCCAFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<CCAdetailsVO> findApprovedMCA(MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException, RemoteException;
	/**
	 * @param eventAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleEvents(com.ibsplc.xibase.server.framework.event.vo.EventAsyncHelperVO eventAsyncHelperVO) throws RemoteException;

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN66Report6E(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 * @author A-5166
	 * @param RoutingCarrierFilterVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO)
			throws RemoteException,SystemException;

	/**
	 * @author A-5166
	 * @param routingCarrierVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveRoutingCarrierDetails(Collection<RoutingCarrierVO> routingCarrierVOs)
		throws RemoteException,SystemException;
	/**
	 * @author A-5166
	 *
	 * @param rateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveCopyRateCard(RateCardVO rateCardVO)
	throws SystemException, RemoteException, MailTrackingMRABusinessException;

	/**
	 * @author A-5166
	 * Added for ICRD-36146 on 06-Mar-2013
	 * @param companyCode
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void initiateProration(String companyCode)
	 throws RemoteException,SystemException;
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
	public BillingSiteVO saveBillingSiteDetails(BillingSiteVO billingSiteVO)
	throws SystemException, PersistenceException,RemoteException, MailTrackingMRABusinessException;
	/**
	 * Find billing site details.
	 *
	 * @param billingSiteFilterVO the billing site filter vo
	 * @return the collection
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	 throws RemoteException,SystemException;
	/**
	 * List parameter lov.
	 *
	 * @param bsLovFilterVo the bs lov filter vo
	 * @return the page
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	Page<BillingSiteLOVVO> listParameterLov(BillingSiteLOVFilterVO bsLovFilterVo)
	throws RemoteException, SystemException;
	/**
	 * Handle advice.
	 *
	 * @param adviceAsyncHelperVO the advice async helper vo
	 * @throws RemoteException the remote exception
	 * @author a-5219
	 */
	public void handleAdvice(com.ibsplc.xibase.server.framework.interceptor.vo.AsyncAdviceHelperVO adviceAsyncHelperVO) throws RemoteException;
	/**
	 * Audit.
	 *
	 * @param auditVo the audit vo
	 * @throws AuditException the audit exception
	 * @throws RemoteException the remote exception
	 * @author a-5219
	 */
	void audit(Collection<AuditVO> auditVo) throws AuditException, RemoteException;

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN51ReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateInvoiceReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateGPAInvoiceReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;

	/**
	 * 	Method		:	MailTrackingMRABI.generateInvoiceTK
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Return type	: 	void
	 */
	@FlowDescriptor(flow="GenerateInvoice_Flow", inputs={"generateInvoiceFilterVO"})
	public void generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws RemoteException, SystemException,MailTrackingMRABusinessException;
	/**
	 * 	Method		:	MailTrackingMRABI.sendEmail
	 *	Added by 	:	A-4809 on 09-Jan-2014
	 * 	Used for 	:	ICRD-42160 EmailInvoice
	 *	Parameters	:	@param cN51CN66VO
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void sendEmailInvoice(CN51CN66VO cN51CN66VO)
	throws RemoteException,SystemException;
	/**
	 *
	 * 	Method		:	MailTrackingMRABI.generateCN51Report
	 *	Added by 	:	A-4809 on 10-Feb-2014
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<CN51DetailsVO>
	 */
	public Collection<CN51DetailsVO> generateCN51ReportPrint(CN51CN66FilterVO filterVO)
	throws RemoteException,SystemException;
	/**
	 *
	 * 	Method		:	MailTrackingMRABI.generateCN66Report
	 *	Added by 	:	A-4809 on 10-Feb-2014
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<CN66DetailsVO>
	 */
	public Collection<CN66DetailsVO> generateCN66ReportPrint(CN51CN66FilterVO filterVO)
	throws RemoteException,SystemException;

	public Collection<LockVO> generateInvoiceLock(String companyCode)
	throws SystemException,ObjectAlreadyLockedException,RemoteException;
	/**
	 *
	 * 	Method		:	MailTrackingMRABI.generateGPAInvoiceReport
	 *	Added by 	:	A-4809 on 25-Feb-2014
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Map<String,Object>
	 */
	public Map<String, Object> generateGPAInvoiceReport(ReportSpec reportSpec)
	throws MailTrackingMRABusinessException, SystemException, RemoteException ;
	/**
	 *
	 * 	Method		:	MailTrackingMRABI.generateGPABillingInvoiceReportTK
	 *	Added by 	:	A-4809 on 27-Feb-2014
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Map<String,Object>
	 */
	public Map<String, Object> generateGPABillingInvoiceReportTK(ReportSpec reportSpec)
	throws MailTrackingMRABusinessException, SystemException, RemoteException ;

	/**
	 *  Method for updating the updateInvoiceReference number for THY invoice printing
	 * 	Method		: MailTrackingMRABI.updateInvoiceReference
	 *	Added by 	: A-5273 on Mar 21, 2014
	 * 	@param aoInvoiceReportDetailsVO
	 * 	@throws RemoteException
	 * 	@throws SystemException
	 *  void
	 */
	public void updateInvoiceReference(AOInvoiceReportDetailsVO aoInvoiceReportDetailsVO)
			 throws RemoteException, SystemException;

	/**
	 * Process billing matrix upload details.
	 *
	 * @param filterVO the filter vo
	 * @return the string
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	public String processBillingMatrixUploadDetails(FileUploadFilterVO filterVO)
	throws RemoteException, SystemException;


public void sendEmailforPAs(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException,RemoteException;
/**
 * 	Method		:	MailTrackingMRAServicesEJB.prorateExceptionFlights
 *	Added by 	:	A-6245 on 17-06-2015
 * @param flightValidationVOs
 * @throws SystemException
 * @throws RemoteException
 */
	public  void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
	throws SystemException, RemoteException;
	/**
	 * 	Method		:	MailTrackingMRABI.validateFlightForAirport
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws SystemException,
			RemoteException;


/* Added by A-3434 for CR ICRD-114599 on 29SEP2015
 * @param invoiceTransactionLogVO
 * @throws SystemException
 * @throws RemoteException
 *
  */
	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO)
			throws SystemException, RemoteException;


/*Added by A-3434 for CR ICRD-114599 on 29SEP2015
 *
 * param generateInvoiceFilterVO
 * @return boolean
 * @throws SystemException
 * @throws RemoteException
 *
 * */
public Boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO)
		throws SystemException, RemoteException;

/**
	 * 	Method		:	MailTrackingMRABI.importArrivedMailstoMRA
	 *	Added by 	:	A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	void
	 */
	public void importArrivedMailstoMRA(String companyCode) throws SystemException,RemoteException;



 /**
	 *
	 * @author A-5255
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public  Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(ProrationFilterVO prorationFilterVO)
			throws SystemException, RemoteException;
/**
 *
 * @author A-5255
 * @param maintainCCAFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeCCAdetailsVO> getSurchargeCCADetails(
		MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException, RemoteException;
/**
 *
 * @author A-5255
 * @param cn51CN66FilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(
		CN51CN66FilterVO cn51CN66FilterVO)throws SystemException, RemoteException;
/**
 *
 * @author A-5255
 * @param blgMatrixFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(
		BillingMatrixFilterVO blgMatrixFilterVO)throws SystemException, RemoteException;
/***
 * @author A-6245
 * @param cn51CN66FilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(
		CN51CN66FilterVO cn51CN66FilterVO)throws SystemException, RemoteException;
/**
 * @author A-6245
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public  Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(ProrationFilterVO prorationFilterVO)
		throws SystemException, RemoteException;
/**
 * @author A-6245
 * @param companyCode
 * @throws RemoteException
 * @throws SystemException
 */
public void generateInterfaceFile(LocalDate uploadTime,String uploadTimeStr)
		 throws RemoteException,SystemException;
/**
*
* @param companyCode
* @param blgbasis
* @param despatchDate
* @return DespatchEnquiryVO
* @throws BusinessDelegateException
*/
public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO) throws SystemException,
		RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.withdrawMailbags
 *	Added by 	:	A-6991 on 08-Sep-2017
 * 	Used for 	:   ICRD-211662
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		 throws RemoteException,SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.finalizeProformaInvoice
 *	Added by 	:	A-6991 on 08-Sep-2017
 * 	Used for 	:   ICRD-211662
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
		 throws RemoteException,SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.withdrawInvoice
 *	Added by 	:	A-6991 on 18-Sep-2017
 * 	Used for 	:   ICRD-211662
 *	Parameters	:	@param summaryVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void withdrawInvoice(CN51SummaryVO summaryVO) throws SystemException,
RemoteException;

/**
 * @author A-7531
 * @param mRABillingDetailsVOs
 * @throws SystemException
 * @throws RemoteException
 */
public void reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs,String txnlogInfo) throws SystemException,
RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.findRerateBillableMails
 *	Added by 	:	A-7531
 *	Parameters	:	@param gpafilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public Collection<DocumentBillingDetailsVO>findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,String companyCode)throws SystemException,
RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.findRerateInterlineBillableMails
 *	Added by 	:	A-7531
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public Collection<DocumentBillingDetailsVO>findRerateInterlineBillableMails(DocumentBillingDetailsVO documentBillingVO,String companyCode)throws SystemException,
RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.saveSAPSettlementMail
 *	Added by 	:	A-7794
 *	Parameters	:	@param Collection<GPASettlementVO> gpaSettlementVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<ErrorVO>
 */
public Collection<ErrorVO>saveSAPSettlementMail(Collection<GPASettlementVO> gpaSettlementVOs)throws SystemException,
RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.importMRAData
 *	Added by 	:	A-5526
 *	Parameters	:	@param rateAuditVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void importMRAData(Collection<RateAuditVO> rateAuditVOs) throws SystemException,
RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.findReproarteMails
 *	Added by 	:	A-7531 on 08-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public Collection<DocumentBillingDetailsVO>findReproarteMails(DocumentBillingDetailsVO documentBillingVO)throws SystemException,
RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.reProrateExceptionMails
 *	Added by 	:	A-7531 on 09-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void reProrateExceptionMails(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)
		throws SystemException, RemoteException;

/**
 * @author a-7794
 * @param reportSpec
 * @return
 * @throws RemoteException
 * @throws SystemException
 * @throws MailTrackingMRABusinessException
 */
public Map<String, Object> generateCN51ReportKE(ReportSpec reportSpec)
		throws RemoteException, SystemException,
		MailTrackingMRABusinessException;

/**
 * @author a-7794
 * @param reportSpec
 * @return
 * @throws MailTrackingMRABusinessException
 * @throws SystemException
 * @throws RemoteException
 */
public Map<String, Object> generateCN66ReportKE(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException, RemoteException ;

/**
 * @author A-7371
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<AWMProrationDetailsVO>  viewAWMProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException,
RemoteException ;

 /**
 *
 * 	Method		:	MailTrackingMRABI.generateMailBillingInterfaceFile
 *	Added by 	:	A-7929 on 09-May-2018
 * 	Used for 	:   ICRD-245605
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */

@FlowDescriptor(flow = "GenerateMailBillingInterfaceFile_Flow", inputs = { "companyCode","regenerateFlag","fileName","fromDate","toDate"}, output = "fileNameReturned")
public  String generateMailBillingInterfaceFile(String companyCode,String regenerateFlag,String fileName,LocalDate fromDate,LocalDate toDate) throws MailTrackingMRABusinessException, SystemException,RemoteException;


/**
 *
 * 	Method		:	MailTrackingMRABI.createJobforFlightRevenueInterface
 *	Added by 	:	a-8061 on 28-Jun-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void  createJobforFlightRevenueInterface(String companyCode)throws SystemException,
RemoteException ;

/**
 *
 * 	Method		:	MailTrackingMRABI.doInterfaceFlightRevenueDtls
 *	Added by 	:	a-8061 on 12-Jul-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
@FlowDescriptor(flow = "DoInterfaceFlightRevenueDtls_Flow", inputs = { "companyCode","maxRecord","isFromRetrigger"})
public  void doInterfaceFlightRevenueDtls(String companyCode, int maxRecord, boolean isFromRetrigger) throws MailTrackingMRABusinessException, SystemException,RemoteException;

/**
 *  @author A-7794
 * 	Method		:	MailTrackingMRABI.generateFile
 *	Added by 	:	a-7794 on 30-July-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public SISMessageVO  generateFile(MiscFileFilterVO filterVO)throws SystemException,
RemoteException ;
/**
 * @author A-8061
 * @param dSNRoutingVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public String  validateBSA(DSNRoutingVO dSNRoutingVO)throws SystemException,
RemoteException ;
/**
 *
 * 	Method		:	MailTrackingMRABI.generateBSAInterfaceDtls
 *	Added by 	:	a-7794 on 23-Aug-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param bsaInterfaceVOs
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
@FlowDescriptor(flow = "GenerateBSAInterfaceDtls_Flow", inputs = { "bsaInterfaceVOs"})
public  void generateBSAInterfaceDtls(Collection<FlightRevenueInterfaceVO> bsaInterfaceVOs) throws MailTrackingMRABusinessException, SystemException,RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.updateMailBSAInterfacedDetails
 *	Added by 	:	a-8061 on 27-Aug-2018
 * 	Used for 	:
 *	Parameters	:	@param flightPk
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void updateMailBSAInterfacedDetails(BlockSpaceFlightSegmentVO flightPk)throws SystemException,
RemoteException;


 /**
 *
 * 	Method		:	MailTrackingMRABI.saveMCAdetails
 *	Added by 	:	A-7929 on 26-Jul-2018
 * 	Used for 	:
 *	Parameters	:	@param cCADetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	String
 */

public void saveAutoMCAdetails(Collection<CCAdetailsVO> cCAdetailsVOs, GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException,
RemoteException,MailTrackingMRABusinessException, BusinessDelegateException;


public void updateTruckCost(TruckOrderMailVO truckOrderMailVO)throws SystemException, RemoteException ;
/**
 *
 * 	Method		:	MailTrackingMRABI.findSettledMailbags
 *	Added by 	:	A-7531 on 26-Apr-2018
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<GPASettlementVO>
 */
public Collection<GPASettlementVO>findSettledMailbags(InvoiceSettlementFilterVO filterVO)throws SystemException,
RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.findUnsettledMailbags
 *	Added by 	:	A-7531 on 03-May-2018
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<GPASettlementVO>
 */
public Collection<GPASettlementVO>findUnsettledMailbags(InvoiceSettlementFilterVO filterVO)throws SystemException,
RemoteException;


/**
 *
 * 	Method		:	MailTrackingMRABI.findMailbagSettlementHistory
 *	Added by 	:	A-7531 on 11-May-2018
 * 	Used for 	:
 *	Parameters	:	@param invoiceFiletrVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<InvoiceSettlementHistoryVO>
 */

public Collection<InvoiceSettlementHistoryVO>findMailbagSettlementHistory(InvoiceSettlementFilterVO invoiceFiletrVO)throws SystemException,
RemoteException;
/**
 * @author A-7871
 * @param gpaSettlementVOs
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 */
public void saveSettlementsAtMailbagLevel(
		Collection<GPASettlementVO> gpaSettlementVOs)
		throws SystemException, RemoteException, MailTrackingMRABusinessException;
/**
 * Method		:	MailTrackingMRABI.finalizeInvoice
 *	Added by 	:	A-7929 on 17-Aug-2018
 * 	Used for 	:
 * @param selectedairlineCN51SummaryVO
 * @throws SystemException
 * @throws RemoteException
 */
public void  finalizeInvoice(Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO, String txnlogInfo)throws SystemException,
RemoteException ;


/**
*
 * 	Method		:	withdrawMailBags
 *	Added by 	:	A-8061
 * 	Used for 	:
 *	Parameters	:	@return
 *	Return type	: 	void
 * @param airlineCN66DetailsVOs
 * @throws BusinessDelegateException
 */

public void  withdrawMailBags(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs)throws SystemException, RemoteException ;
/***
 * @author A-7794
 * @param filterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<AirlineCN51SummaryVO>  getAirlineSummaryDetails(AirlineCN51FilterVO filterVO)throws SystemException, RemoteException ;
/**
 * @author A-7371
 * @param companyCode
 * @param airlineId
 * @param flightNumber
 * @param flightSequenceNumber
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<FlightSegmentSummaryVO>  findFlightSegments(String companyCode, int airlineId, String flightNumber,
		long flightSequenceNumber) throws SystemException,RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.saveSupportingDocumentDetails
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param paramCollection
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
 */
public abstract Collection<SisSupportingDocumentDetailsVO> saveSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> paramCollection)
	    throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.downloadAttachment
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param paramSupportingDocumentFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	SisSupportingDocumentDetailsVO
 */
public abstract SisSupportingDocumentDetailsVO downloadAttachment(SupportingDocumentFilterVO paramSupportingDocumentFilterVO)
	    throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.removeSupportingDocumentDetails
 *	Added by 	:	a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param paramCollection
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
 */
public abstract Collection<SisSupportingDocumentDetailsVO> removeSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> paramCollection)
	    throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.listInvoicDetails
 *	Added by 	:	a-8464 on 20-Nov-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param paramCollection
 *	Parameters	:	@return invoicDetailsVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicDetailsVO>
 */
public abstract Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO)
	    throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.listInvoic
 *	Added by 	:	a-8527 on 18-Dec-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param pagenumber
 *	Parameters	:	@return invoicFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicVO>
 */
public abstract Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO, int pageNumber)
	    throws RemoteException, SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.listInvoic
 *	Added by 	:	a-8527 on 18-Dec-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param pagenumber
 *	Parameters	:	@return invoicFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicVO>
 */
public abstract void updateInvoicReject(Collection <InvoicVO> rejectrecords)
	    throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.saveRemarkDetails
 *	Added by 	:	a-8464 on 20-Nov-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param InvoicDetailsVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public abstract void saveRemarkDetails(InvoicDetailsVO invoicDetailsVO) throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.saveClaimDetails
 *	Added by 	:	a-8464 on 20-Nov-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param Collection<InvoicDetailsVO>
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public abstract void saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVO) throws RemoteException, SystemException;

/**
 *
 * 	Method		:	MailTrackingMRABI.updateProcessStatus
 *	Added by 	:	a-8464 on 20-Nov-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param Collection<InvoicDetailsVO>
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public abstract void updateProcessStatus(Collection<InvoicDetailsVO> invoicDetailsVO, String processStatus) throws RemoteException, SystemException;


/**
 *
 * 	Method		:	MailTrackingMRABI.saveGroupRemarkDetails
 *	Added by 	:	a-8464 on 20-Nov-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param Collection<InvoicDetailsVO>
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public abstract void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave) throws RemoteException, SystemException;

/**
 * 	Method		:	MailTrackingMRABI.importConsignmentDataToMra
 *	Added by 	:	A-4809 on Nov 20, 2018
 * 	Used for 	:
 *	Parameters	:	@param consignmentDocumentVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO)
		throws RemoteException, SystemException;
/**
 * 	Method		:	MailTrackingMRABI.importConsignmentDataToMRA
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param mailInConsignmentVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO)
		throws RemoteException, SystemException;
/**
 * 	Method		:	MailTrackingMRABI.calculateUSPSIncentive
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void calculateUSPSIncentive(USPSIncentiveVO uspsIncentiveVO)
		 throws RemoteException,SystemException;
/**
 * 	Method		:	MailTrackingMRABI.importMailsFromCarditData
 *	Added by 	:	A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void importMailsFromCarditData(DocumentBillingDetailsVO documentBillingDetailsVO,String txnlogInfo) throws SystemException,
RemoteException;
/**
 * @author A-7371
 * @param mailInvoicMessage
 * @throws RemoteException
 * @throws SystemException
 * @throws ServiceNotAccessibleException
 */
public void saveMailInvoicDetails(Collection<MailInvoicMessageVO> mailInvoicMessage) throws RemoteException, SystemException, ServiceNotAccessibleException;


/**
 *
 * 	Method		:	MailTrackingMRABI.findInvoicLov
 *	Added by 	:	a-8464 on 17-Dec-2018
 * 	Used for 	:	ICRD-232401
 *	Parameters	:	@param Collection<InvoicSummaryVO>
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
//Commenting as part of ICRD-319850
//public Page<InvoicSummaryVO> findInvoicLov(InvoicFilterVO invoicFilterVO) throws RemoteException, SystemException;

/**
 * 	Method		:	MailTrackingMRABI.findMailbagExistInMRA
 *	Added by 	:	A-4809 on Jan 2, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	String
 */
public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO) throws RemoteException,SystemException;
/**
 * 	Method		:	MailTrackingMRABI.updateMailStatus
 *	Added by 	:	A-7929 on Jan 10, 2019
 * 	Used for 	:
 *	Parameters	:	@param invoicDetailsVO,RaiseClaimFlag
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	:
 */
public abstract void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs,String RaiseClaimFlag) throws RemoteException, SystemException;

public void saveGpaRebillRemarks(
		Collection<RebillRemarksDetailVO> rebillRemarksDetailVOs)
		throws RemoteException, SystemException;

public Page<ReminderDetailsVO> findReminderListForGpaBilling(ReminderDetailsFilterVO reminderDetailsFilterVO)
		throws RemoteException, SystemException;
/**
 * 	Method		:	MailTrackingMRABI.mailSubClass
 *	Added by 	:	A-7929 on Jan 22, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode,code
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<MailSubClassVO>
 * @throws ServiceNotAccessibleException
 * @throws ProxyException
 */
public abstract Collection<MailSubClassVO> findMailSubClass(String companyCode,String code) throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException;

/**
 * 	Method		:	MailTrackingMRABI.forceImportScannedMailbags
 *	Added by 	:	A-7794 as part of ICRD-232299
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param startDate
 *	Parameters	:	@param endDate
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void forceImportScannedMailbags(String companyCode,String startDate,String endDate) throws SystemException,RemoteException;

/**
 *
 * @param reminderDetailsFilterVO
 * @throws RemoteException
 * @throws SystemException
 */
public void generateGpaRebill(ReminderDetailsFilterVO reminderDetailsFilterVO)
		throws RemoteException, SystemException;




/**
 *
 * 	Method		:	MailTrackingMRABI.processSettlementUploadDetails
 *	Added by 	:	A-7531 on 14-Jan-2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	String
 */
public String processSettlementUploadDetails(FileUploadFilterVO filterVO)
		throws RemoteException, SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.removeProcessedDataFromTempTable
 *	Added by 	:	A-7531 on 14-Jan-2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	String
 */
	public void removeProcessedDataFromTempTable(FileUploadFilterVO filterVO)
		throws RemoteException, SystemException;
	/**
	 *
	 * 	Method		:	MailTrackingMRABI.validateFromFileUpload
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	String
	 */
	public String validateFromFileUpload(FileUploadFilterVO fileUploadFilterVO)
						throws SystemException,RemoteException;
   /***
 * @author A-7794 as part of ICRD-232299
 * @param fileUploadFilterVO
 * @throws SystemException
 * @throws RemoteException
 */
public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws SystemException,RemoteException,PersistenceException;
/**
 * 	Method		:	MailTrackingMRABI.findMailbagHistories
 *	Added by 	:	A-7929 on Jan 25, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<MailbagHistoryVO>
 * @throws ProxyException
 * @throws ServiceNotAccessibleException
 */
public abstract Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException;

/**
 *
 * @param reminderListFilterVO
 * @return
 * @throws RemoteException
 * @throws SystemException
 */
public Collection<RebillRemarksDetailVO> findGPARemarkDetails(ReminderDetailsFilterVO reminderListFilterVO)
		throws RemoteException, SystemException;
	/**
	 * 	Method		:	MailTrackingMRABI.listGPABillingEntries
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Page<DocumentBillingDetailsVO>
	 */
	public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException,RemoteException;
	/**
	 * 	Method		:	MailTrackingMRABI.listConsignmentDetails
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Page<ConsignmentDocumentVO>
	 */
	public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException,RemoteException;

	/**
	 * 	Method		:	MailTrackingMRABI.findCRAParameterDetails
	 *	Added by 	:	A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Collection<CRAParameterVO>
	 */
	public Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String craParInvgrp)
		throws SystemException,RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.findReasonCodes
 *	Added by 	:	A-7531 on 05-Feb-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param systemParCodes
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<CRAParameterVO>
 * @throws ProxyException
 */
public Collection<CRAParameterVO> findReasonCodes(String companyCode, String systemParCodes)
		throws RemoteException, SystemException, ProxyException;

/***
 * @author A-7794
 * @param mailScanDetailVO
 * @throws SystemException
 * @throws RemoteException
 */
public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO)throws SystemException,RemoteException;


/**
 * 	Method		:	MailTrackingMRAServicesEJB.sendEmailforTK
 *	Added by 	:	A-5526 on Feb 12, 2019
 * 	Used for 	:
 *	Parameters	:	@param PostalAdministrationVOs
 *	Parameters	:	@throws generateInvoiceFilterVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void sendEmailforTK(Collection<PostalAdministrationVO> postalAdministrationVOs,
		GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException,RemoteException;
/**
 * 	Method		:	MailTrackingMRAServicesEJB.sendEmailforAA
 *	Added by 	:	A-5526 on Feb 12, 2019
 * 	Used for 	:
 *	Parameters	:	@param PostalAdministrationVOs
 *	Parameters	:	@throws generateInvoiceFilterVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void sendEmailforAA(Collection<PostalAdministrationVO> postalAdministrationVOs,
		GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException,RemoteException;
/**
 * 	Method		:	MailTrackingMRAProxy.sendEmailRebillInvoice
 *	Added by 	:	A-5526 on Mar 04, 2019
 * 	Used for 	:ICRD-234283-Rebill invoice email sending from CRA209-Reminder report flow
 *	Parameters	:	@param cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs
 *	Parameters	:	@throws ProxyException,SystemException
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void sendEmailRebillInvoice(Collection<CN51DetailsVO> cn51DetailsVoS, CN51CN66FilterVO cn51cn66FilterVO,
		Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs)throws SystemException,RemoteException;
/**
 * 	Method		:	MailTrackingMRAProxy.sendEmailRebillInvoice
 *	Added by 	:	A-5526 on Mar 04, 2019
 * 	Used for 	:ICRD-234283-Rebill invoice email sending from CRA209-GPA rebill flow
 *	Parameters	:	@param reminderDetailsFilterVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void sendEmailRebillInvoice(ReminderDetailsFilterVO reminderDetailsFilterVO) throws SystemException,RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.listClaimDetails
 *	Added by 	:	a-8527 on 07-March-2019
 * 	Used for 	:	ICRD-262471
 *	Parameters	:	@param pagenumber
 *	Parameters	:	@return invoicFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicVO>
 */
public abstract Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO, int pageNumber)
	    throws RemoteException, SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.listGenerateClaimDetails
 *	Added by 	:	a-8527 on 011-March-2019
 * 	Used for 	:	ICRD-262471
 *	Parameters	:	@param pagenumber
 *	Parameters	:	@return invoicFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicVO>
 */
public abstract Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO, int pageNumber)
	    throws RemoteException, SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.generateandClaimDetails
 *	Added by 	:	a-8527 on 011-March-2019
 * 	Used for 	:	ICRD-262471
 *	Parameters	:	@param pagenumber
 *	Parameters	:	@return invoicFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<InvoicVO>
 */
public abstract Collection <USPSPostalCalendarVO> validateFrmToDateRange(InvoicFilterVO invoicFilterVO)
	    throws RemoteException, SystemException,ServiceNotAccessibleException;
/**
*
* 	Method		:	MailTrackingMRABI.validateuspsPacode
*	Added by 	:	a-8527 on 18-March-2019
* 	Used for 	:	ICRD-262471
*	Parameters	:	@param pagenumber
*	Parameters	:	@return invoicFilterVO
*	Parameters	:	@throws RemoteException
*	Parameters	:	@throws SystemException
*	Return type	: 	Page<InvoicVO>
*/
public abstract Map <String,String> validateuspsPacode(Collection <String> systemParameters)
	    throws RemoteException, SystemException,ProxyException;
/**
 *
 * @param invoicVO
 * @throws SystemException
 * @throws RemoteException
 */
public void processInvoic(InvoicVO invoicVO) throws SystemException,RemoteException;
/**
 *
 * @param invoicDetailsVOs
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws SystemException,RemoteException;
/**
 *
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public int checkForProcessCount(String companyCode,InvoicVO invoicVO) throws SystemException,RemoteException;
//Added as part of ICRD-329873
public String getmcastatus(CCAdetailsVO ccaDetailsVO) throws SystemException,RemoteException,BusinessDelegateException;
/**
 *
 * @param companyCode
 * @param mailSeqnum
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum) throws SystemException,RemoteException;

/**
 * 	Method		:	MailTrackingMRABI.generateClaimAndResdits
 *	Added by 	:	A-4809 on May 22, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws BusinessDelegateException
 *	Return type	: 	void
 */
public void generateClaimAndResdits(InvoicFilterVO filterVO, String txnlogInfo) throws SystemException,RemoteException;

/**
 * 	Method		:	MailTrackingMRABI.generateClaimAndResditsFromJob
 *	Added by 	:	A-4809 on Jun 3, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void generateClaimAndResditsFromJob(String companyCode) throws SystemException,RemoteException;

/**
 * @author A-7540
 * @param mailbagID
 * @return
 * @throws SystemException
 * @throws WebServiceException
 * @throws RemoteException
 * @throws ServiceNotAccessibleException
 * @throws ProxyException
 */
public Collection<ResditReceiptVO> getResditInfofromUSPS(String mailbagID)
		 throws SystemException,RemoteException, ProxyException, ServiceNotAccessibleException, WebServiceException;

/**
 * @author A-7371
 * @param companyCode
 * @throws SystemException
 */
public void generateClaimMessageText(String companyCode) throws SystemException,RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.isClaimGenerated
 *	Added by 	:	A-8061 on 20-Jun-2019
 * 	Used for 	:	ICRD-262451
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Boolean
 */
public Boolean isClaimGenerated(InvoicFilterVO invoicFilterVO)throws SystemException,RemoteException;

/**
 * @author A-5526 . update status and remarks
 *
 * @param Collection<DocumentBillingDetailsVO>
 *
 * @throws BusinessDelegateException
 */
@AvoidForcedStaleDataChecks
public void changeStatusForInterline(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		throws SystemException, RemoteException;


/**
 * 
 * @param VOs
 * @throws SystemException
 * @throws RemoteException
 */
public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws SystemException, RemoteException;

/**
 * 
 * 	Method		:	MailTrackingMRABI.saveVoidedInterfaceDetails
 *	Added by 	:	A-8061 on 15-Oct-2019
 * 	Used for 	:	ICRD-336689
 *	Parameters	:	@param VOs
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 *	Return type	: 	void
 */
@FlowDescriptor(flow = "SaveVoidedInterfaceDetails_Flow", inputs = { "mailVOs"})
public  void saveVoidedInterfaceDetails(Collection<DocumentBillingDetailsVO> VOs) throws MailTrackingMRABusinessException, SystemException,RemoteException;

/**
 * 
 * 	Method		:	MailTrackingMRABI.findMailbagBillingStatus
 *	Added by 	:	a-8331 on 25-Oct-2019
 * 	Used for 	:
 *	Parameters	:	@param mailbagvo
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo)throws SystemException, RemoteException;


/**
 * 
 * 	Method		:	MailTrackingMRABI.validateCurrConversion
 *	Added by 	:	A-8061 on 30-Oct-2019
 * 	Used for 	:	ICRD-346925
 *	Parameters	:	@param currCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 *	Return type	: 	String
 */
public String validateCurrConversion(String currCode)throws SystemException, RemoteException;






/**
 * 	Method		:	MailTrackingMRABI.calculateUSPSIncentiveAmount
 *	Added by 	:	A-5526 on Jan 23, 2020
 * 	Used for 	:IASCB-28259-calculateUSPSIncentiveAmount
 *	Parameters	:	@param USPSPostalCalendarVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void calculateUSPSIncentiveAmount(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO)
		throws SystemException, RemoteException;
/**
 * Added by : U-1393
 * @param interfaceFilterVO
 * @throws RemoteException
 * @throws SystemException
 */
public void generateSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO) throws RemoteException, SystemException;

public void sendSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO,SAPInterfaceFileLogVO sapInterfaceFileLogVO) throws RemoteException, SystemException,ProxyException;

/**
 * @author A-2408
 * @param invoiceLovVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public InvoiceLovVO findInvoiceNumber(InvoiceLovVO invoiceLovVO)
		throws SystemException, RemoteException;
/**
 *
 * 	Method		:	MailTrackingMRABI.isInitiatedInvoic
 *	Added by 	:	A-5219 on 20-Apr-2020
 * 	Used for 	:
 *	Parameters	:	@param invoicVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	boolean
 */
public boolean isInitiatedInvoic(InvoicVO invoicVO)
		throws SystemException, RemoteException;
/**
 * @author A-5526
 * @param paramString
 * @return
 * @throws SystemException
 * @throws ObjectAlreadyLockedException
 * @throws RemoteException
 */

public  Collection<LockVO> generateINVOICProcessingLock(String paramString)
	    throws SystemException, ObjectAlreadyLockedException, RemoteException;


/**
 *
 * 	Method		:	MailTrackingMRABI.isMailbagInMRA
 *	Added by 	:	A-5219 on 07-May-2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param mailSeq
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	boolean
 */
public boolean isMailbagInMRA(String companyCode, long mailSeq)
		throws SystemException, RemoteException;

/**
 * 	Method		:	MailTrackingMRABI.findApprovedForceMeajureDetails
 *	Added by 	:	A-5526 on Jun 15, 2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<MailbagHistoryVO>
 * @throws ProxyException
 * @throws ServiceNotAccessibleException
 */
public abstract Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException;
/**
*
* 	Method		:	MailTrackingMRABI.recalculateDisincentiveData
*	Added by 	:	A-8176
*	Parameters	:	@param mailbagVos
*	Parameters	:	@return
*	Parameters	:	@throws SystemException
*	Parameters	:	@throws RemoteException
*	Return type	: 	void
*/
public void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVos) throws SystemException,
RemoteException;

/**
 *
 * 	Method		:	MailTrackingMRABI.updateRouteAndReprorate
 *	Added by 	:	A-8061 on 15-Dec-2020
 * 	Used for 	:
 *	Parameters	:	@param gPABillingEntriesFilterVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO) throws SystemException, RemoteException;


/**
 *
 * 	Method		:	MailTrackingMRABI.asyncProcessSettlementUpload
 *	Added by 	:	A-5219 on 08-Jan-2021
 * 	Used for 	:
 *	Parameters	:	@param fileUploadFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws ProxyException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void asyncProcessSettlementUpload(FileUploadFilterVO fileUploadFilterVO)
		throws RemoteException, ProxyException, SystemException;
/**
 *
 * 	Method		:	MailTrackingMRABI.addLockingForSettlementUpload
 *	Added by 	:	A-5219 on 11-Jan-2021
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws ObjectAlreadyLockedException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<LockVO>
 */
public Collection<LockVO> addLockingForSettlementUpload(String companyCode)
		throws SystemException,ObjectAlreadyLockedException,RemoteException;

public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO) throws SystemException, RemoteException;

public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)throws SystemException,RemoteException;

public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws SystemException,RemoteException;

/**
 * 	Method		:	MailTrackingMRABI.generatePASSFile
 *	Added by 	:	A-4809 on 20-Apr-2021
 * 	Used for 	:
 *	Parameters	:	@param passFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void generatePASSFile(GeneratePASSFilterVO passFilterVO)
		throws RemoteException, SystemException ;
/**
 * 	Method		:	MailTrackingMRABI.findBillingType
 *	Added by 	:	A-9498 on 26-Apr-2021
 * 	Used for 	:
 *	Parameters	:	@param passFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */

 public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO,
			int pageNumber) throws SystemException, RemoteException;
 /**
  * 	Method		:	MailTrackingMRABI.saveBillingSchedulemaster
  *	Added by 	:	A-9498 on 26-Apr-2021
  * 	Used for 	:
  *	Parameters	:	@param passFilterVO
  *	Parameters	:	@throws RemoteException
  *	Parameters	:	@throws SystemException 
  *	Return type	: 	void
  */
	public void saveBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)
			throws SystemException, RemoteException, FinderException;
	/**
	  * 	Method		:	MailTrackingMRABI.validateBillingSchedulemaster
	  *	Added by 	:	A-9498 on 26-Apr-2021
	  * 	Used for 	:
	  *	Parameters	:	@param passFilterVO
	  *	Parameters	:	@throws RemoteException
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	void
	  */
	public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)
			throws SystemException, RemoteException, FinderException;
	public Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws RemoteException,SystemException;
	
	/**
	 * 
	 * 	Method		:	MailTrackingMRABI.generatePASSFileJobScheduler
	 *	Added by 	:	A-8061 on 10-Jun-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void generatePASSFileJobScheduler(String companyCode) throws SystemException, ProxyException, RemoteException;
	/**
	 * 
	 * 	Method		:	MailTrackingMRABI.generateInvoiceJobScheduler
	 *	Added by 	:	A-8061 on 10-Jun-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void generateInvoiceJobScheduler(String companyCode)throws SystemException, ProxyException, MailTrackingMRABusinessException, RemoteException;
/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceReportSQ(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException;		 
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateCN51ReportSQ(ReportSpec reportSpec)
			throws RemoteException, SystemException,MailTrackingMRABusinessException;
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateGPAInvoiceReportSQ(ReportSpec reportSpec)
			throws MailTrackingMRABusinessException, SystemException, RemoteException ;

/**
	 * 	Method		:	MailTrackingMRABI.listPaymentBatchDetails
	 *	Added by 	:	A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Page<PaymentBatchDetailsVO>
	 * @throws PersistenceException
	 */
	public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, RemoteException, PersistenceException;


	/**
	 *
	 * 	Method		:	MailTrackingMRABI.uploadSettlementAsyc
	 *	Added by 	:	A-5219 on 10-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param settlementBatchHeaderVO
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	void
	 */
	public void uploadPaymentBatchDetail(PaymentBatchFilterVO batchFilterVO, InvoiceTransactionLogVO invoiceTransactionLogVO)
		throws SystemException, RemoteException;
		
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findSettlementBatchDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO)
	 *	Added by 			: A-3429 on 18-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws PersistenceException 
	 */
	public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, RemoteException, PersistenceException;
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findSettlementBatchDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO)
	 *	Added by 			: A-3429 on 18-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws PersistenceException 
	 */
	public Page<PaymentBatchSettlementDetailsVO> findSettlementBatchDetails(MailSettlementBatchFilterVO mailSettlementBatchFilterVO)
			throws SystemException, RemoteException, PersistenceException;

	
	/**
	 * @author A-8331 
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public void saveAdvancePaymentDetails(
			PaymentBatchDetailsVO advancePaymentVO)
			throws SystemException, RemoteException, MailTrackingMRABusinessException, FinderException, PersistenceException;

	
	
	public int findGPASettlementBatchUploadFileCount(PaymentBatchFilterVO paymentBatchFilterVO)
			 throws SystemException,RemoteException;
	/**
	 * 	Method		:	MailTrackingMRABI.deletePaymentBatchAttachment
	 *	Added by 	:	A-4809 on 02-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchDetailsVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	public void deletePaymentBatchAttachment(PaymentBatchDetailsVO paymentBatchDetailsVO)
			throws SystemException, RemoteException, PersistenceException;
	

     /**
	 * @author A-8331 
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	
	public void deletePaymentBatch(PaymentBatchDetailsVO advancePaymentVO)
			throws SystemException, RemoteException, CreateException, FinderException;

	/**
	   *
	   * 	Method		:	MailTrackingMRABI.mraBatchSettlementWorkerJob
	   *	Added by 	:	A-5219 on 08-Dec-2021
	   * 	Used for 	:
	   *	Parameters	:	@param paramString
	   *	Parameters	:	@throws SystemException
	   *	Parameters	:	@throws RemoteException
	   *	Parameters	:	@throws PersistenceException
	   *	Return type	: 	void
	   */
	  public void mraBatchSettlementWorkerJob(String paramString)
		    throws SystemException, RemoteException, PersistenceException;

	/**
	 * 	Method		:	MailTrackingMRABI.updateBatchAmount
	 *	Added by 	:	A-4809 on 11-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param batchVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	  public void updateBatchAmount(PaymentBatchDetailsVO batchVO)
			  throws SystemException, RemoteException, PersistenceException;
	  /**
	   * 
	   * 	Method		:	MailTrackingMRABI.clearBatchDetail
	   *	Added by 	:	A-10647 on 27-Jan-2022
	   * 	Used for 	:
	   *	Parameters	:	@param batchVO
	   *	Parameters	:	@throws SystemException
	   *	Parameters	:	@throws RemoteException
	   *	Parameters	:	@throws PersistenceException 
	   *	Return type	: 	void
	   */
	  public void clearBatchDetails(PaymentBatchDetailsVO batchVO)
			  throws SystemException, RemoteException, PersistenceException;
	  public  void processInvoicFileFromJob(InvoicVO invoicVO) throws SystemException,RemoteException;
	  
	  public  void updateInvoicProcessingStatusFromJob(String companyCode) throws SystemException,RemoteException;
	  /**
		 * @author A-10383
		 * @param reportSpec
		 * @return
		 * @throws RemoteException
		 * @throws SystemException
		 * @throws MailTrackingMRABusinessException
	 * @throws PersistenceException 
		 */
		public Map<String, Object> generateCoverPageSQ(ReportSpec reportSpec)
				throws RemoteException, SystemException,MailTrackingMRABusinessException, PersistenceException;
		/**
		 * @author A-10383
		 * @param reportSpec
		 * @return
		 * @throws RemoteException
		 * @throws SystemException
		 * @throws MailTrackingMRABusinessException
		 * @throws PersistenceException 
		 */
		public Map<String, Object> generateGPAInvoiceReportPrintAllSQ(ReportSpec reportSpec)
				throws RemoteException, SystemException,MailTrackingMRABusinessException, PersistenceException;
		/**
		 * 
		 * 	Method		:	MailTrackingMRABI.importMRAProvisionalRateData
		 *	Added by 	:	A-10647 on 23-Nov-2022
		 * 	Used for 	:
		 *	Parameters	:	@param rateAuditVOs
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException 
		 *	Return type	: 	void
		 */
		public void importMailProvisionalRateData(Collection<RateAuditVO> rateAuditVOs) throws SystemException,
		RemoteException,PersistenceException;
		
		/**
		 *	Added by 	:	204569 on 12-Dec-2022
		 * 	Used for 	:
		 *	Parameters	:	@param BillingLineVO
		 **	Parameters	:	@param changeEndDate
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	void
		 */
		public void changeEnddate(Collection<BillingLineVO> billingLineVos,String date)
				throws RemoteException, SystemException;
				
		public void calculateProvisionalRate(Long noOfRecords) throws SystemException,
		RemoteException,PersistenceException;


/**
 *	Added by 	:	204569 on 12-Dec-2022
 * 	Used for 	:
 *	Parameters	:	@param BillingLineVO
 **	Parameters	:	@param changeStatus
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 * @throws ProxyException 
 */
public void changeBillingMatrixStatusUpdate(BillingMatrixFilterVO billingLineVos,String status)
		throws RemoteException, SystemException, ProxyException;

		/**
		 * 
		 * 	Method		:	MailTrackingMRABI.findMRAGLAccountingEntries
		 *	Added by 	:	A-10164 on 15-Feb-2023
		 *	User Story	:   IASCB-162079
		 *	Parameters	:	@param glInterfaceFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 *	Parameters	:	@throws PersistenceException 
		 *	Return type	: 	List<GLInterfaceDetailVO>
		 */
		public List<GLInterfaceDetailVO> findMRAGLAccountingEntries(GLInterfaceFilterVO glInterfaceFilterVO) throws SystemException,
		RemoteException,PersistenceException;
		public Collection<LockVO> autoMCALock(String companyCode)throws RemoteException,
		SystemException,ObjectAlreadyLockedException;
}



