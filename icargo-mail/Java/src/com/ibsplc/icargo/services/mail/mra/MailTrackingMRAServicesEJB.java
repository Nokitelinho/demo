/*
 * MailTrackingMRAServicesEJB.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.mra;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.util.List;
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
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.AirlineBillingController;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.CN51DetailsAlreadyCapturedException;
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
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MiscFileFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.ChangeStatusException;
import com.ibsplc.icargo.business.mail.mra.defaults.InvalidBillingMatrixCodeException;
import com.ibsplc.icargo.business.mail.mra.defaults.InvalidSLACodeException;
import com.ibsplc.icargo.business.mail.mra.defaults.MRADefaultsController;
import com.ibsplc.icargo.business.mail.mra.defaults.MRARateAuditDetailsException;
import com.ibsplc.icargo.business.mail.mra.defaults.ProrationFactorNotFoundException;
import com.ibsplc.icargo.business.mail.mra.defaults.RateCardException;
import com.ibsplc.icargo.business.mail.mra.defaults.RateLineException;
import com.ibsplc.icargo.business.mail.mra.defaults.aa.MRADefaultsAAController;
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
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
import com.ibsplc.icargo.business.mail.mra.flown.FlownController;
import com.ibsplc.icargo.business.mail.mra.flown.FlownException;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.GPABillingController;
import com.ibsplc.icargo.business.mail.mra.gpabilling.aa.GPABillingAAController;
import com.ibsplc.icargo.business.mail.mra.gpabilling.tk.GPABillingTKController;
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
import com.ibsplc.icargo.business.mail.mra.gpareporting.GPAReportingController;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
//import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;

/**
 * @author Philip Scaria
 * @ejb.bean description="MailTrackingMRAServices"
 *           display-name="MailTrackingMRAServices"
 *           jndi-name="com.ibsplc.icargo.services.mail.mra.MailTrackingMRAServicesHome"
 *           name="MailTrackingMRAServices" type="Stateless" view-type="remote"
 *           remote-business-interface="com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI"
 *
 * @ejb.transaction type="Supports"
 *
 *
 *
 * Bean implementation class for Enterprise Bean: MailTrackingMRAServicesEJB
 */

public class MailTrackingMRAServicesEJB extends AbstractFacadeEJB implements
		MailTrackingMRABI {

	private Log log = LogFactory.getLogger("MAILTRACKING:MRA");
	private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING:MRA");

	private static final String CLASS_NAME = "MailTrackingMRAServicesEJB";
	public static final String MRADEFAULTCONTROLLER_BEAN ="mRADefaultscontroller";

	/**
	 *
	 */
	public MailTrackingMRAServicesEJB() {
		super();

	}

	/**
	 * Saves the rate card and assosciated rate lines Used for the
	 * creation,updation of rate cards and creation,updation and deletion of
	 * rate lines based on the operation flag set in argument
	 *
	 * @param rateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws RateCardException
	 * @throws RateLineException
	 * @throws MailTrackingMRABusinessException
	 */
	public void saveRateCard(RateCardVO rateCardVO) throws SystemException,
			RemoteException, MailTrackingMRABusinessException {
		try {
			MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
			mRADefaultsController.saveRateCard(rateCardVO);
		} catch (RateLineException rateLineException) {
			throw new MailTrackingMRABusinessException(rateLineException);
		} catch (RateCardException rateCardException) {
			// throw new
			// MailTrackingMRABusinessException(rateCardException.getMessage(),rateCardException);
		}
	}

	/**
	 * @author A-3434 Finds and returns the GpaBillingInvoicedetails
	 *
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public  CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
			throws RemoteException, SystemException {
		log.entering("MailTrackingMRAServicesEJB", "findGpaBillingInvoice");
		return new GPABillingController().findGpaBillingInvoiceEnquiryDetails
		(gpaBillingInvoiceEnquiryFilterVO);
	}
	/**
	 * @author A-3434 .save the GpaBillingInvoicedetails
	 *
	 * @param cN66DetailsVO
	 *
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveBillingStatus(CN66DetailsVO cN66DetailsVO)throws
	RemoteException, SystemException,MailTrackingMRABusinessException {
		log.entering("MailTrackingMRAServicesEJB", "changeBillingStatus");
		try{ new GPABillingController().saveBillingStatus
					(cN66DetailsVO);
		}catch (ChangeStatusException changeStatusException) {
			throw new MailTrackingMRABusinessException(changeStatusException);
		}

	}


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
			throws SystemException, RemoteException {
		return new MRADefaultsController().findRateCardDetails(companyCode,
				rateCardID,pagenum);
	}

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
			RemoteException {

		return new MRADefaultsController()
				.findRateLineDetails(rateLineFilterVO);
	}

	/**
	 * Saves the rateLine Status
	 *
	 * @param rateLineVOs
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveRatelineStatus(Collection<RateLineVO> rateLineVOs)
			throws MailTrackingMRABusinessException, SystemException,
			RemoteException {

		try {
			new MRADefaultsController().saveRatelineStatus(rateLineVOs);
		} catch (RateLineException rateLineException) {
			throw new MailTrackingMRABusinessException(rateLineException);
		}

	}

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
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "findAllRateCards");
		return new MRADefaultsController().findAllRateCards(rateCardFilterVO);
	}

	/**
	 * @param rateCardVOs
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void changeRateCardStatus(Collection<RateCardVO> rateCardVOs)
			throws MailTrackingMRABusinessException, SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "changeRateCardStatus");
		try {
			new MRADefaultsController().changeRateCardStatus(rateCardVOs);
		} catch (RateLineException rateLineException) {
			log.log(Log.SEVERE, " Business Exception throw from Server");
			throw new MailTrackingMRABusinessException(rateLineException);
		}
	}

	/**
	 * @param companyCode
	 * @param rateCardId
	 * @param pageNumber
	 * @throws RemoteException
	 * @throws SystemException
	 * @return
	 */

	public Page<RateCardLovVO> findRateCardLov(String companyCode,
			String rateCardId, int pageNumber) throws SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "findRateCardLov");
		return new MRADefaultsController().findRateCardLov(companyCode,
				rateCardId, pageNumber);

	}

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
			SystemException {
		log.entering(CLASS_NAME, "findAllInvoices");
		return new GPABillingController().findAllInvoices(cn51SummaryFilterVO);
	}

	/**
	 * @author A-2280 Finds and returns the CN51 and CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return CN51CN66VO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public CN51CN66VO findCN51CN66Details(CN51CN66FilterVO cn51CN66FilterVO)
			throws RemoteException, SystemException {
		log.entering("CRASalesReportServicesEJB", "findCN51CN66Details");
		return new GPABillingController().findCN51CN66Details(cn51CN66FilterVO);
	}

	/**
	 * Finds and returns the GPA Billing entries available This includes
	 * billed, billable and on hold despatches
	 *
	 * @param gpaBillingEntriesFilterVO
	 * @return Collection<GPABillingDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public Page<DocumentBillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws RemoteException, SystemException {
		log.entering("CRASalesReportServicesEJB", "findMissingAWBs");
		return new MRADefaultsController()
				.findGPABillingEntries(gpaBillingEntriesFilterVO);

	}

	/**
	 * Changes the staus of GPA billing entries to Billable/On Hold
	 *
	 * @param gpaBillingStatusVO
	 * @throws RemoteException
	 * @throws SystemException
	 *//*

	public void changeBillingStatus(
			Collection<GPABillingStatusVO> gpaBillingStatusVO)
			throws RemoteException, SystemException {
		log.entering("CRASalesReportServicesEJB", "changeBillingStatus");
		new MRADefaultsController().changeBillingStatus(gpaBillingStatusVO);
		log.exiting("CRASalesReportServicesEJB", "changeBillingStatus");
	}*/

	/**
	 * Saves the remarks against CN66 details
	 *
	 * @param cn66DetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	// TODO Collection<CN66DetailsVO>
	public void saveCN66Observations(Collection<CN66DetailsVO> cn66DetailsVOs)
			throws RemoteException, SystemException {
		log.entering("CRASalesReportServicesEJB", "saveCN66Observations");
		new GPABillingController().saveCN66Observations(cn66DetailsVOs);
		log.exiting("CRASalesReportServicesEJB", "saveCN66Observations");

	}

	/**
	 * @param generateInvoiceFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	/* Commented the method as part of ICRD-153078
	public void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		new MRADefaultsController().generateInvoice(generateInvoiceFilterVO);

	}*/

	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO invoiceLovVO)
			throws SystemException, RemoteException {
		return new GPABillingController().findInvoiceLov(invoiceLovVO);
	}

	/**
	 * @author A-2259
	 * @param flownMailFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public FlownMailSegmentVO findFlownMails(FlownMailFilterVO flownMailFilterVO)
			throws SystemException, RemoteException {
		return new FlownController().findFlownMails(flownMailFilterVO);
	}

	/**
	 * @author A-2259
	 * @param flownMailFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/* Commented the method as part of ICRD-153078
	public void closeFlight(FlownMailFilterVO flownMailFilterVO)
			throws SystemException, RemoteException {
		new FlownController().closeFlight(flownMailFilterVO);

	}*/

	/**
	 * added for upu calendar
	 *
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws RemoteException,
			SystemException {

		log.entering(CLASS_NAME, "displayUPUCalendarDetails");
		return new AirlineBillingController()
				.displayUPUCalendarDetails(upuCalendarFilterVO);

	}

	/**
	 * saves upu calendar details
	 *
	 * @param upuCalendarVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveUPUCalendarDetails(Collection<UPUCalendarVO> upuCalendarVOs)
			throws RemoteException, SystemException {

		log.entering(CLASS_NAME, "saveUPUCalendarDetails");
		new AirlineBillingController().saveUPUCalendarDetails(upuCalendarVOs);

	}

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
			SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		return new AirlineBillingController().findCN66Details(cn66FilterVo);

	}

	/**
	 * Method to print CN66 details
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-2458
	 */
	public Map<String, Object> findCN66DetailsPrint(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findExportBillingEntries");
		return new AirlineBillingController().findCN66DetailsPrint(reportSpec);
	}

	/**
	 * Method to find flight segment details
	 *
	 * @param flownMailFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<FlownMailSegmentVO> findFlightDetails(
			FlownMailFilterVO flownMailFilterVO) throws SystemException,
			RemoteException {

		return new FlownController().findFlightDetails(flownMailFilterVO);
	}

	/**
	 * Method to save CN66 details
	 *
	 * @param cn66DetailsVos
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2518
	 */
	public void saveCN66Details(Collection<AirlineCN66DetailsVO> cn66DetailsVos)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "saveCN66Details");
		new AirlineBillingController().saveCN66Details(cn66DetailsVos);
		log.exiting(CLASS_NAME, "saveCN66Details");
	}

	/**
	 *
	 * @param airlineExceptionsFilterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @exception SystemException
	 * @exception RemoteException
	 */
	public Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "displayAirlineExceptions");
		return new AirlineBillingController()
				.displayAirlineExceptions(airlineExceptionsFilterVO);
	}


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
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "saveAirlineExceptions");
		AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
		airlineBillingController.saveAirlineExceptions(airlineExceptionsVOs);
	}

	/**
	 * Method to findAirlineExceptionInInvoices
	 *
	 * @param exceptionInInvoiceFilterVO
	 * @return Collection<ExceptionInInvoiceVO>
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2391
	 */
	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		return new AirlineBillingController()
				.findAirlineExceptionInInvoices(exceptionInInvoiceFilterVO);

	}

	/**
	 * Method to accept airlineinvoices
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2391
	 */
	public void acceptAirlineInvoices(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "acceptAirlineInvoices");
		new AirlineBillingController()
				.acceptAirlineInvoices(exceptionInInvoiceVOs);
	}

	/**
	 * Method to saveRejectionMemo
	 *
	 * @param exceptionInInvoiceVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @return RejectionMemoVO
	 * @author A-2391
	 */
	public RejectionMemoVO saveRejectionMemo(
			ExceptionInInvoiceVO exceptionInInvoiceVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "saveRejectionMemo");
		return new AirlineBillingController().saveRejectionMemo(exceptionInInvoiceVO);
	}

	/**
	 * Method to deleteRejectionMemo
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-2391
	 */
	public void deleteRejectionMemo(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "deleteRejectionMemo");
		new AirlineBillingController()
				.deleteRejectionMemo(exceptionInInvoiceVOs);
	}

	/**
	 * Added by A-2401 Method to findFlownMailExceptions
	 *
	 * @param FlownMailFilterVO
	 * @throws RemoteException
	 */
	public Collection<FlownMailExceptionVO> findFlownMailExceptions(
			FlownMailFilterVO flownMailFilterVO) throws RemoteException,
			SystemException {
		log.entering(CLASS_NAME, "findFlownMailExceptions");
		return new FlownController().findFlownMailExceptions(flownMailFilterVO);

	}

	/**
	 * Added by A-2401 Method to findFlownMailExceptions
	 *
	 * @param FlownMailFilterVO
	 * @throws RemoteException
	 */
	public Map<String, Object> findFlownMailExceptionsforprint(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findFlownMailExceptionsforprint");
		return new FlownController()
				.findFlownMailExceptionsforprint(reportSpec);

	}

	/**
	 * Added by A-2401 Method to findFlownMailExceptionsforprintDetails
	 *
	 * @param FlownMailFilterVO
	 * @throws RemoteException
	 */
	public Map<String, Object> findFlownMailExceptionsforprintDetails(
			ReportSpec reportSpec) throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findFlownMailExceptionsforprintDetails");
		return new FlownController()
				.findFlownMailExceptionsforprintDetails(reportSpec);

	}

	/**
	 * Added by A-2401 Method to assignFlownMailExceptions
	 *
	 * @param flownMailExceptionVOs
	 * @throws RemoteException
	 */
	public void assignFlownMailExceptions(
			Collection<FlownMailExceptionVO> flownMailExceptionVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "assignFlownMailExceptions");
		new FlownController().assignFlownMailExceptions(flownMailExceptionVOs);

	}

	/**
	 * Added by A-2401 Method to processFlight
	 *
	 * @param flownMailFilterVOs
	 * @throws RemoteException
	 */
	public void processFlight(FlownMailFilterVO flownMailFilterVO)
			throws RemoteException, SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "processFlight");
		new FlownController().processFlight(flownMailFilterVO);
	}

	/**
	 * Added by A-2401 Method to validateUsers
	 *
	 * @param Collection userCodes
	 * @param  companyCode
	 */
	public Collection<ValidUsersVO> validateUsers(Collection<String> userCodes, String companyCode)
			throws RemoteException, SystemException{
		log.entering(CLASS_NAME, "validateUsers");
		return new FlownController().validateUsers(userCodes,companyCode);
	}
	/**
	 *
	 * @param companyCode
	 * @param iataClearamcePrd
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public UPUCalendarVO validateIataClearancePeriod(String companyCode,
			String iataClearamcePrd) throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "validateIataClearancePeriod");
		return new AirlineBillingController().validateIataClearancePeriod(
				companyCode, iataClearamcePrd);
	}

	/**
	 * added by A-2397
	 *
	 * @param memoInInvoiceVos
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveMemo(Collection<MemoInInvoiceVO> memoInInvoiceVos)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "saveMemo");
		new AirlineBillingController().saveMemo(memoInInvoiceVos);
	}

	/**
	 * added by A-2397
	 *
	 * @param memoFilterVo
	 * @return Collection <MemoInInvoiceVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findMemoDetails");
		return new AirlineBillingController().findMemoDetails(memoFilterVo);
	}

	/**
	 *
	 * @param gpaReportingDetailsVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveGPAReportingDetails(
			Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs)
			throws SystemException, RemoteException {
		log.entering("MailTrackingMRAServicesEJB", "saveGPAReportingDetails");
		new GPAReportingController()
				.saveGPAReportingDetails(gpaReportingDetailsVOs);
		log.exiting("MailTrackingMRAServicesEJB", "saveGPAReportingDetails");
	}

	/**
	 *
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public AirlineCN51SummaryVO findCN51Details(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering("MailTrackingMRAServicesEJB", "findCN51Details");
		try {
			return new AirlineBillingController().findCN51Details(cn51FilterVO);
		} catch (CN51DetailsAlreadyCapturedException ex) {
			throw new MailTrackingMRABusinessException(ex);
		}
	}

	public Map<String, Object> findCN51DetailsReport(ReportSpec reportspec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException  {
		log.entering("MailTrackingMRAServicesEJB", "findCN51DetailsReport");
		try{
		return new AirlineBillingController().findCN51DetailsReport(reportspec);
		}catch (ReportGenerationException ex) {
			throw new MailTrackingMRABusinessException(ex);
		}

	}

	/**
	 *
	 * @param gpaReportFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<GPAReportingDetailsVO> findGPAReportingDetails(
			GPAReportingFilterVO gpaReportFilterVO) throws SystemException,
			RemoteException {
		log.entering("MailTrackingMRAServicesEJB", "findGPAReportingDetails");
		Page<GPAReportingDetailsVO> gpaReportingDetailsVOs = new GPAReportingController()
				.findGPAReportingDetails(gpaReportFilterVO);
		log.exiting("MailTrackingMRAServicesEJB", "findGPAReportingDetails");
		return gpaReportingDetailsVOs;
	}

	/**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 */
	public Page<GPAReportingClaimDetailsVO> findClaimDetails(
			GPAReportingFilterVO gpaReportingFilterVO) throws RemoteException,
			SystemException {
		log.entering(CLASS_NAME, "findClaimDetails");
		return new GPAReportingController()
				.findClaimDetails(gpaReportingFilterVO);
	}

	/**
	 * @author A-2521
	 *
	 * @param invoiceLovFilterVO
	 * @return Page<AirlineInvoiceLovVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws RemoteException,
			SystemException {

		log.entering(CLASS_NAME, "validateIataClearancePeriod");
		return new AirlineBillingController()
				.displayInvoiceLOV(invoiceLovFilterVO);
	}

	/**
	 *
	 * @author A-2521
	 *
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return Page<MemoLovVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<MemoLovVO> displayMemoLOV(String companyCode, String memoCode,
			int pageNumber) throws RemoteException, SystemException {

		log.entering(CLASS_NAME, "displayMemoLOV");
		return new AirlineBillingController().displayMemoLOV(companyCode,
				memoCode, pageNumber);
	}

	/**
	 * @author A-2280
	 * @param gpaReportingClaimDetailVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void assignClaims(
			Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "assignClaims");
		new GPAReportingController().assignClaims(gpaReportingClaimDetailVOs);

	}

	/**
	 * @author A-2259
	 * @param FlightFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "validateFlight");
		return new FlownController().validateFlight(flightFilterVO);
	}

	/**
	 * @author A-2270
	 * @param filterVo
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public void processGpaReport(GPAReportingFilterVO filterVo)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException{
		log.entering(CLASS_NAME, "processGpaReport");
		new GPAReportingController().processGpaReport(filterVo);

	}

	/**
	 * @param airlineCN51SummaryVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveCN51(AirlineCN51SummaryVO airlineCN51SummaryVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "saveCN51");
		new AirlineBillingController().saveCN51(airlineCN51SummaryVO);
		log.exiting(CLASS_NAME, "saveCN51");
	}

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> generateCN66Report(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateCN66Details");
		return ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).generateCN66Report(reportSpec);

	}

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> generateCN51Report(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findCN51CN66Details");
		return new GPABillingController().generateCN51Report(reportSpec);

	}

	 /**
	 * @author A-2398
	 * @param blgMtxFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public BillingMatrixVO findBillingMatrixDetails(BillingMatrixFilterVO blgMtxFilterVO)
	 throws RemoteException, SystemException,MailTrackingMRABusinessException{
		 log.entering(CLASS_NAME,"listBillingMatrixDetails");
		 return new MRADefaultsController().findBillingMatrixDetails(blgMtxFilterVO);
	 }
	/**
	 * @author A-2398
	 * @param blgLineFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Page<BillingLineVO> findBillingLineDetails(BillingLineFilterVO blgLineFilterVO)
     throws RemoteException, SystemException, MailTrackingMRABusinessException
     {
     log.entering("MailTrackingMRAServices", "findBillingLineDetails");
     log.exiting("MailTrackingMRAServices", "findBillingLineDetails");
     return (new MRADefaultsController()).findBillingLineDetails(blgLineFilterVO);
     }
	/**
	 * @param blgLineFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Page<BillingLineVO> findBillingLineValues(BillingLineFilterVO blgLineFilterVO)
    throws RemoteException, SystemException, MailTrackingMRABusinessException
    {
    log.entering("MailTrackingMRAServices", "findBillingLineValues");
    log.exiting("MailTrackingMRAServices", "findBillingLineValues");
    return (new MRADefaultsController()).findBillingLineValues(blgLineFilterVO);
    }



	/**
	 * @author A-2398
	 * @param billingMatrixVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public boolean saveBillingMatrix(BillingMatrixVO billingMatrixVO)
	 throws RemoteException, SystemException,MailTrackingMRABusinessException{
		 log.entering(CLASS_NAME,"saveBillingMatrix");

		 try{
			 log.exiting(CLASS_NAME,"saveBillingMatrix");
			 MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
			 return mRADefaultsController.saveBillingMatrix(billingMatrixVO);
		 }catch(CreateException createException){
			 throw new SystemException(createException.getErrorCode());
		 }
		 catch(FinderException finderException){
			 throw new SystemException(finderException.getErrorCode());
		 }

	}
	/**
	 * @author A-2280
	 * @param billingMatrixFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findAllBillingMatrix");

		return new MRADefaultsController()
				.findAllBillingMatrix(billingMatrixFilterVO);
	}

	/**
	 * @author a-2270
	 * @param reportSpec
	 * @return map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> generateInvoiceReport(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateInvoiceReport");
		return new GPABillingController().generateInvoiceReport(reportSpec);

	}

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
			MailTrackingMRABusinessException {

		log.entering(CLASS_NAME, "printExceptionInInvoice");
		return new AirlineBillingController()
				.printExceptionInInvoice(reportSpec);
	}

	/**
	 * Method to process Mail
	 *
	 * @param cn66FilterVo
	 * @return String
	 * @author A-2408
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public void processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "processMail");
		new AirlineBillingController().processMail(cn66FilterVo);
	}

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
			MailTrackingMRABusinessException {
		log.entering("MailTrackingMRAServicesEJB", "printExceptionReportDetail");
		return new AirlineBillingController()
				.printExceptionReportDetail(reportSpec);
	}

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
			MailTrackingMRABusinessException {
		log.entering("MailTrackingMRAServicesEJB", "printRejectionMemo");
		return new AirlineBillingController()
				.printRejectionMemo(reportSpec);
	}
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
			RemoteException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveBillingLineStatus");
		new MRADefaultsController().saveBillingLineStatus(billingMatrixVOs,
				billingLineVOs);
		log.exiting(CLASS_NAME, "saveBillingLineStatus");
	}
	/**
	 *
	 * @param companyCode
	 * @param billingMatrixCode
	 * @param pageNumber
	 * @return page
	 * @author A-2408
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,
			String billingMatrixCode, int pageNumber) throws SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "findBillingMatrixLov");
		return new MRADefaultsController().findBillingMatrixLov(companyCode,
				billingMatrixCode, pageNumber);

	}

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
			RemoteException {
		log.entering(CLASS_NAME, "displayProrationDetails");
		return new MRADefaultsController()
				.displayProrationDetails(prorationFilterVO);
	}

	/**
	 * This method is to print Exceptions Report by Assignee Details
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportAssigneeDetails(
			ReportSpec reportSpec) throws SystemException,
			RemoteException, MailTrackingMRABusinessException{
		log.entering("MailTrackingMRAServicesEJB", "printExceptionsReportAssigneeDetails");
		return new GPAReportingController()
				.printExceptionsReportAssigneeDetails(reportSpec);
	}

	/**
	 * This method is to print Exceptions Report by Assignee Summary
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportAssigneeSummary(
			ReportSpec reportSpec) throws SystemException,
			RemoteException, MailTrackingMRABusinessException{
		log.entering("MailTrackingMRAServicesEJB", "printExceptionsReportAssigneeSummary");
		return new GPAReportingController()
				.printExceptionsReportAssigneeSummary(reportSpec);
	}

	/**
	 * This method is to print Exceptions Report Details
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportDetails(
			ReportSpec reportSpec) throws SystemException,
			RemoteException, MailTrackingMRABusinessException{
		log.entering("MailTrackingMRAServicesEJB", "printExceptionsReportDetails");
		return new GPAReportingController()
				.printExceptionsReportDetails(reportSpec);
	}

	/**
	 * This method is to print Exceptions Report Summary
	 * @author A-2245
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printExceptionsReportSummary(
			ReportSpec reportSpec) throws SystemException,
			RemoteException, MailTrackingMRABusinessException{
		log.entering("MailTrackingMRAServicesEJB", "printExceptionsReportSummary");
		return new GPAReportingController()
				.printExceptionsReportSummary(reportSpec);
	}

	/**
	 * This method generates invoices for outward billing
	 *
	 * @author a-2521
	 * @param invoiceFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public void generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
	throws RemoteException, SystemException, MailTrackingMRABusinessException {

		log.entering(CLASS_NAME, "generateOutwardBillingInvoice");
		new MRADefaultsController().generateOutwardBillingInvoice(invoiceFilterVO);
		log.exiting(CLASS_NAME, "generateOutwardBillingInvoice");

	}

    /**
     *
     * @param gprMessageVO
     * @throws SystemException
     * @throws RemoteException
     */
	/* Commented the method as part of ICRD-153078
    public void saveGPRMessageDetails(GPRMessageVO gprMessageVO)
            throws SystemException, RemoteException {
        log.entering("MailTrackingMRAServicesEJB", "saveGPRMessageDetails");
        new GPAReportingController().saveGPRMessageDetails(gprMessageVO);
        log.exiting("MailTrackingMRAServicesEJB", "saveGPRMessageDetails");
    }*/


    /**
    * @author A-2449
    * @param reportSpec
    * @return Map<String, Object>
    * @throws SystemException
    * @throws RemoteException
    * @throws MailTrackingMRABusinessException
    */
    public Map<String, Object> findListOfFlightsForReport(ReportSpec reportSpec)
	throws SystemException,RemoteException,MailTrackingMRABusinessException {
		log.entering("CLASS_NAME","FindListOfFlightsForReport");
		return new FlownController().findListOfFlightsForReport(reportSpec);
	}


    /**
     * @author A-2449
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
	public Map<String, Object> findListOfFlownMailsForReport(ReportSpec reportSpec)
	throws SystemException,RemoteException,MailTrackingMRABusinessException {
		log.entering("CLASS_NAME","FindListOfFlownMailsForReport");
		return new FlownController().findListOfFlownMailsForReport(reportSpec);
	}

    /**
     *
     * @param gpaReportMessageVO
     * @throws SystemException
     * @throws RemoteException
     */
    public void uploadGPAReport(GPAReportMessageVO gpaReportMessageVO)
            throws SystemException, RemoteException {
        log.entering("MailTrackingMRAServicesEJB", "uploadGPAReport");
        new GPAReportingController().uploadGPAReport(gpaReportMessageVO);
        log.exiting("MailTrackingMRAServicesEJB", "uploadGPAReport");
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findBlgSmyPeriodWiseDetailsForPrint(ReportSpec reportSpec)
	throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	 log.entering("MailTrackingMRAServicesEJB", "findBillingSummaryDetailsPeriodWiseForPrint");
    	return new GPABillingController().findBlgSmyPeriodWiseDetailsForPrint(reportSpec);
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findBlgSmyGpaWiseDetailsForPrint(ReportSpec reportSpec)
	throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	 log.entering("MailTrackingMRAServicesEJB", "findBillingSummaryDetailsGpa WiseForPrint");
    	return new GPABillingController().findBlgSmyGpaWiseDetailsForPrint(reportSpec);
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findCN51DetailsPeriodWiseForPrint(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	 log.entering("MailTrackingMRAServicesEJB", "findCN51DetailsPeriodWise");
    	 return new GPABillingController().findCN51DetailsPeriodWiseForPrint(reportSpec);
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findCN51DetailsGPAWiseForPrint(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "findCN51DetailsGPAWise");
   	 return new GPABillingController().findCN51DetailsGPAWiseForPrint(reportSpec);
    }

    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findCN66DetailsPeriodWiseForPrint(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	 log.entering("MailTrackingMRAServicesEJB", "findCN66DetailsPeriodWise");
    	 return new GPABillingController().findCN66DetailsPeriodWiseForPrint(reportSpec);
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findCN66DetailsGPAWiseForPrint(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "findCN66DetailsGPAWise");
   	 return new GPABillingController().findCN66DetailsGPAWiseForPrint(reportSpec);
    }
    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findInvoiceDetailsForReport(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "findInvoiceDetailsForPrint");
   	 return new AirlineBillingController().findInvoiceDetailsForReport(reportSpec);
    }

    /**
     * @author A-2521
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> generateInvoiceByClrPrd(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "generateInvoiceByClrPrd");
   	 return new AirlineBillingController().generateInvoiceByClrPrd(reportSpec);
    }

    /**
     * @author A-2521
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> generateInvoiceByAirline(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "generateInvoiceByAirline");
   	 return new AirlineBillingController().generateInvoiceByAirline(reportSpec);
    }

    /**
     * @author A-2458
     * @param reportSpec
     * @return Map
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */

	public Map<String, Object> findInvoicesCollectionByClrPrd(ReportSpec reportSpec)
	throws RemoteException, SystemException,MailTrackingMRABusinessException
	{
		log.entering(CLASS_NAME, "findInvoicesCollectionByClrPrd");

		return new AirlineBillingController().findInvoicesCollectionByClrPrd(reportSpec);
	}
	 /**
     * @author A-2458
     * @param reportSpec
     * @return Map
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */

	public Map<String, Object> findInvoicesCollectionByAirline(ReportSpec reportSpec)
	throws RemoteException, SystemException,MailTrackingMRABusinessException
	{
		log.entering(CLASS_NAME, "findInvoicesCollectionByAirline");

		return new AirlineBillingController().findInvoicesCollectionByAirline(reportSpec);

	}

    /**
     * Method to view the CN51s for a period range
     * @param filterVO
     * @return
     * @throws SystemException
     */
    public Page<AirlineCN51SummaryVO>
						findCN51s(AirlineCN51FilterVO filterVO)
						throws SystemException,RemoteException {
    	log.entering("MailTrackingMRAServicesEJB","findCN51s");
    	return new AirlineBillingController().findCN51s(filterVO);
    }

    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> printGPABillableReport(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	return new GPABillingController().printGPABillableReport(reportSpec);
    }
    /**
     *
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> printGPABillableReportTK(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	return new GPABillingController().printGPABillableReportTK(reportSpec);
    }


    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String,Object> findOutwardRejectionMemo(ReportSpec reportSpec)
    throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	log.entering("MailTrackingMRAServicesEJB", "findOutwardRejectionMemo");
    	return new AirlineBillingController().findOutwardRejectionMemo(reportSpec);
    }

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
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "findProrationFactors");
		return new MRADefaultsController()
				.findProrationFactors(prorationFactorFilterVo);
	}

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
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "saveProrationFactors");
		new MRADefaultsController().saveProrationFactors(prorationFactorVos);
		log.exiting(CLASS_NAME, "saveProrationFactors");
	}

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
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "changeProrationFactorStatus");
		new MRADefaultsController()
				.changeProrationFactorStatus(prorationFactorVo);
		log.exiting(CLASS_NAME, "changeProrationFactorStatus");
	}
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<GPASettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO, int displayPage) throws SystemException,RemoteException{
		log.entering(CLASS_NAME,"findSettlementDetails");
		return new GPABillingController().findSettlementDetails(invoiceSettlementFilterVO,displayPage);
	}
	/**
	 * @author A-2280
	 * @param gpaSettlementVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	 public void saveSettlementDetails(
				Collection<GPASettlementVO> gpaSettlementVOs)throws SystemException,RemoteException, MailTrackingMRABusinessException{
		 log.entering(CLASS_NAME,"saveSettlementDetails");
		 new GPABillingController().saveSettlementDetails(gpaSettlementVOs);
		 log.exiting(CLASS_NAME,"saveSettlementDetails");


	 }

	 /**
	  * @author A-2408
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
   /* public Map<String,Object> printReconciliationReport(ReportSpec reportSpec)
     throws SystemException,RemoteException,MailTrackingMRABusinessException{
    	 log.entering(CLASS_NAME,"printReconciliationReport");
    	 return new GPABillingController().printReconciliationReport(reportSpec);
    }*/

    /**
     * @author A-2280
     * @param invoiceSettlementFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(InvoiceSettlementFilterVO invoiceSettlementFilterVO) throws SystemException, RemoteException {
		log.entering(CLASS_NAME,"findSettlementHistory");
		return new GPABillingController().findSettlementHistory(invoiceSettlementFilterVO);
	}

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
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "viewMailContract");
		return new MRADefaultsController().viewMailContract(companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 * @author a-2524
	 * @param mailSLAVo
	 * @throws SystemException
	 * @throws RemoteException
	 */
	 public void saveMailSla(MailSLAVO mailSLAVo) throws  SystemException, RemoteException{
		 log.entering(CLASS_NAME, "saveMailSla");
		  new MRADefaultsController().saveMailSla(mailSLAVo);
	 }

	 /**
	  * @author a-2524
	  * @param companyCode
	  * @param slaId
	  * @return MailSLAVO
	  * @throws SystemException
	  * @throws RemoteException
	  */
	 public MailSLAVO findMailSla(String companyCode, String slaId)
     throws SystemException, RemoteException {
		 log.entering(CLASS_NAME, "viewMailContract");
			return new MRADefaultsController().findMailSla(companyCode,slaId);
	 }

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
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "saveMailContract");
		new MRADefaultsController().saveMailContract(mailContractVo);
		log.exiting(CLASS_NAME, "saveMailContract");
	}

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
	public void changeMailContractStatus(Collection<MailContractVO> mailContractVOs)
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "changeMailContractStatus");
		new MRADefaultsController().changeMailContractStatus(mailContractVOs);
		log.exiting(CLASS_NAME, "changeMailContractStatus");
	}

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
			String versionNumber) throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "displayVersionLov");
		return new MRADefaultsController().displayVersionLov(companyCode,
				contractReferenceNumber, versionNumber);
	}

	/**
	 * Added by A-2521 for ContractRefNo Lov
	 * @param contractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO)throws SystemException, RemoteException{

		return new MRADefaultsController().displayContractDetails(contractFilterVO);
	}

	/**
	 * Added by A-2521 for SLAId Lov
	 * @param contractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<SLADetailsVO> displaySLADetails(
			SLAFilterVO slaFilterVO)throws SystemException, RemoteException{

		return new MRADefaultsController().displaySLADetails(slaFilterVO);
	}

	/**
	 * Added by A-1946 for
	 * @param mailContractFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO)
			throws SystemException, RemoteException{

		return new MRADefaultsController().findMailContracts(mailContractFilterVO);
	}

	/**
	 * @author a-2049
	 */
	public PostalAdministrationVO findPostalAdminDetails(String companyCode, String gpaCode)
	throws SystemException, RemoteException {
		return new GPABillingController().findPostalAdminDetails(companyCode,gpaCode);
	}

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
			RemoteException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "validateBillingMatrixCodes");
		try {
			new MRADefaultsController().validateBillingMatrixCodes(companyCode,
					billingMatrixCodes);
		} catch (InvalidBillingMatrixCodeException invalidBillingMatrixCodeException) {
			throw new MailTrackingMRABusinessException(
					invalidBillingMatrixCodeException);
		}
		log.exiting(CLASS_NAME, "validateBillingMatrixCodes");
	}

	/**
	 * This method validates Service Level Activity(SLA) codes
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
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "validateSLACodes");
		try {
			new MRADefaultsController().validateSLACodes(companyCode, slaCodes);
		} catch (InvalidSLACodeException invalidSlaCodeException) {
			throw new MailTrackingMRABusinessException(invalidSlaCodeException);
		}
		log.exiting(CLASS_NAME, "validateSLACodes");
	}
	/**
	 * @param companyCode
	 * @param despatch
	 * @param gpaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode,String dsn,
		String despatch,String gpaCode, int pageNumber) throws SystemException,RemoteException{

	return new MRADefaultsController().findDespatchLov(companyCode,dsn,despatch,gpaCode,pageNumber);
}

	/**
	 * @author a-2518
	 * @param reportingPeriodFilterVo
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Boolean validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "validateReportingPeriod");
		return new MRADefaultsController()
				.validateReportingPeriod(reportingPeriodFilterVo);
	}
	/**
	 * @author a-2391
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public RejectionMemoVO findRejectionMemo(
			RejectionMemoFilterVO filterVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findRejectionMemo");
		return new AirlineBillingController()
				.findRejectionMemo(filterVO);
	}

	/**
	 * @author a-2391
	 * @param rejectionMemoVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void updateRejectionMemo(
			RejectionMemoVO rejectionMemoVO)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findRejectionMemo");
		 new AirlineBillingController()
				.updateRejectionMemo(rejectionMemoVO);
	}
	/**
	 *
	 */
	public void importFlownMails(
			   FlightValidationVO flightValidationVO,

			   Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO,String txnlogInfo)
	 throws RemoteException, SystemException{
		log.entering(CLASS_NAME, "importFlownMails");
		new MRADefaultsController()
				.importFlownMails(flightValidationVO,flownMailSegmentVOs,documentBillingVO,txnlogInfo);

	}

	/**
	 * Inactivates the billing lines
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void inActivateBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "inActivateBillingLines");
		new MRADefaultsController().inActivateBillingLines(billingLineVos);
	}

	/**
	 * Cancels the billing lines
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "cancelBillingLines");
		new MRADefaultsController().cancelBillingLines(billingLineVos);
	}

	/**
	 * Activate the billing lines
	 * @param billingLineVos
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void activateBillingLines(Collection<BillingLineVO> billingLineVos)
			throws RemoteException, SystemException,MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "activateBillingLines");
		new MRADefaultsController().activateBillingLines(billingLineVos);
	}
	/**
	 * @param masterVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	/* Commented the method as part of ICRD-153078
	public void saveMailInvoicAdv(MailInvoicMasterVO masterVO)
	throws SystemException, RemoteException{
		log.entering(CLASS_NAME, "saveMailInvoicAdv");
		new MRADefaultsController().saveMailInvoicAdv(masterVO);
	}*/
	/**
	 *
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo) throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "findInvoicEnquiryDetails");
		return new MRADefaultsController().findInvoicEnquiryDetails(invoiceEnquiryFilterVo);
	}
	/**
	 *
	 */
	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails
	(MailInvoicClaimsFilterVO filterVO)throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "findInvoicClaimsEnquiryDetails");
		return new MRADefaultsController().findInvoicClaimsEnquiryDetails(filterVO);
	}
	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(MailDOTRateFilterVO filterVO)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "findDOTRateDetails");
		return new MRADefaultsController().findDOTRateDetails(filterVO);
	}
	/**
	 * @param mailDOTRateVOs
	 * @throws SystemException
	 */
	public void saveDOTRateDetails(Collection<MailDOTRateVO> mailDOTRateVOs)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "saveDOTRateDetails");
		new MRADefaultsController().saveDOTRateDetails(mailDOTRateVOs);
	}
	/**
	 * Inactivates the rateLineVOs
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void inActivateRateLines(Collection<RateLineVO> rateLineVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "inActivateRateLines");
		//modified by A-8527 for Bug IASCB-25921 starts
		//new MRADefaultsController().inActivateRateLines(rateLineVOs);
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
			try {
				mRADefaultsController.inActivateRateLines(rateLineVOs);
			} catch (MailTrackingMRABusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//modified by A-8527 for Bug IASCB-25921 Ends
	}

	/**
	 * Cancels the rateLineVOs
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelRateLines(Collection<RateLineVO> rateLineVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "cancelRateLines");
		//modified by A-8527 for Bug IASCB-25921 starts
		//new MRADefaultsController().cancelRateLines(rateLineVOs);
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		mRADefaultsController.cancelRateLines(rateLineVOs);
		//modified by A-8527 for Bug IASCB-25921 Ends
	}

	/**
	 * Activate the rateLineVOs
	 * @param rateLineVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void activateRateLines(Collection<RateLineVO> rateLineVOs , boolean isBulkActivation)//Modified by a-7871 ICRD-223130
			throws RemoteException, SystemException,MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "activateRateLines");
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		try{
			mRADefaultsController.activateRateLines(rateLineVOs, isBulkActivation);
		}catch(RateLineException  rateLineException){
			throw new MailTrackingMRABusinessException(rateLineException);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			throw new MailTrackingMRABusinessException(e);
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			throw new MailTrackingMRABusinessException(e);
		}
		}

	/**
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void importToReconcile(String companyCode)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "importToReconcile");
		new MRADefaultsController().importToReconcile(companyCode);
	}

	/**
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void reconcileProcess(String companyCode)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "reconcileProcess");
		new MRADefaultsController().reconcileProcess(companyCode);
	}

	/**
	 * This method generates INVOIC Claim file
	 *
	 * @author A-2518
	 * @param companyCode
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void generateInvoicClaimFile(String companyCode)
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "generateInvoicClaimFile");
		new MRADefaultsController().generateInvoicClaimFile(companyCode);
	}
	/**
	 * @author a-2391 This method is used to fetch Audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<AuditDetailsVO> findArlAuditDetails(MRAArlAuditFilterVO mailAuditFilterVO)
			throws SystemException, RemoteException {
		log.entering("MailTrackingDefaultsEJB", "findDSNAuditDetails");
		return new AirlineBillingController().findArlAuditDetails(mailAuditFilterVO);
	}
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
			String invoicKey,String poaCode, int pageNumber) throws SystemException,RemoteException{
		return new MRADefaultsController().findInvoicKeyLov(companyCode,invoicKey,poaCode,pageNumber);
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
		public Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException, RemoteException {
			log.log(Log.INFO,"into ejb call");

			return new MRADefaultsController().findBillingPeriods(generateInvoiceFilterVO);

	}
		/**
		 * @author A-3447
		 * @param summaryVO
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void finalizeandSendEInvoice(Collection<CN51SummaryVO> summaryVOs,String EInvoiceMsg) throws SystemException, RemoteException {


			 {
					log.log(Log.FINE,"<--Inside EJB SErvices:Save--->");
					log.log(Log.FINE, "**inside Ejb**", summaryVOs);
					GPABillingController gPABillingController=new GPABillingController();
					try{
						gPABillingController.finalizeandSendEInvoice(summaryVOs,EInvoiceMsg);
					}
					catch (FinderException e) {
					throw new SystemException(e.getErrorCode(), e);
					}

				 }

		}
		/**
		 *
		 * @param companyCode
		 * @param blgbasis
		 * @param despatchDate
		 * @return DespatchEnquiryVO
		 * @throws BusinessDelegateException
		 */
		/* Commented the method as part of ICRD-153078
		public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException,RemoteException {
			return new MRADefaultsController().findDespatchDetails(popUpVO);
		}*/
		/**
		 *
		 * @param companyCode
		 * @param blgbasis
		 * @param despatchDate
		 * @return Collection<GPABillingDetailsVO>
		 * @throws BusinessDelegateException
		 */
		public Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)throws SystemException,RemoteException {
			return new MRADefaultsController().findGPABillingDetails(popUpVO);
		}
		/**
		 *
		 */
		public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
				String dsnNum,String dsnDate,int pageNumber) throws SystemException,
				RemoteException {
			log.entering(CLASS_NAME, "findDsnSelectLov");
			return new MRADefaultsController().findDsnSelectLov(companyCode,
					dsnNum,dsnDate, pageNumber);

		}
		/**
		 * @author a-3108
		* @return Collection<RateAuditVO>
		 * @throws SystemException
		 * @throws RemoteException
		*/
		public Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException, RemoteException {
			return new MRADefaultsController().findRateAuditDetails(rateAuditFilterVO);
		}

		/**
		 *
		 * @param rateAuditFilterVO
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws SystemException,RemoteException {
			return new MRADefaultsController().findListRateAuditDetails(rateAuditFilterVO);
		}

		/**
		 * @author a-3108
		* @return
		 * @throws SystemException
		 * @throws RemoteException
		*/
		public void changeRateAuditDsnStatus(Collection<RateAuditVO> rateAuditVOs,Collection<RateAuditVO> rateAuditVOsForaplyAudit) throws SystemException, RemoteException,MailTrackingMRABusinessException {
			 new MRADefaultsController().changeRateAuditDsnStatus(rateAuditVOs,rateAuditVOsForaplyAudit);
		}
		/**
		 * @author A-3434
		 * @param formOneFilterVo
		 * @return Page<FormOneVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */
		public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)
				throws RemoteException, SystemException {
			log.entering(CLASS_NAME, "findFormOnes");
			return new AirlineBillingController().findFormOnes(formOneFilterVo);

		}
		/**
		 * @author A-3447
		 * @param maintainCCAFilterVO
		 * @throws SystemException
		 * @throws RemoteException
		 *
		 */

		public Collection<CCAdetailsVO> findCCAdetails(MaintainCCAFilterVO maintainCCAFilterVO)
				throws SystemException, RemoteException {

			return new MRADefaultsController().findCCAdetails(maintainCCAFilterVO);
		}

		/**
		 * @author A-3447
		 * @param maintainCCAFilterVO
		 * @throws SystemException
		 * @throws RemoteException
		 *
		 */

		public Collection<CCAdetailsVO> findCCA(
				MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException,
				RemoteException {
			return new MRADefaultsController().findCCA(maintainCCAFilterVO);
		}


		/**
		 * @author A-3434
		 * @param interlineFilterVo
		 * @return Collection<AirlineForBillingVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */

		public Collection<AirlineForBillingVO> findFormTwoDetails(
				InterlineFilterVO interlineFilterVo) throws RemoteException,
				SystemException {
			log.entering(CLASS_NAME, "findFormTwoDetails");
			return new AirlineBillingController()
					.findFormTwoDetails(interlineFilterVo);

		}
		/**
		 *
		 */
		public FormOneVO listFormOneDetails(FormOneVO formOneVO)throws SystemException, RemoteException {
			log.log(Log.INFO,"ejb done");
			return new AirlineBillingController().listFormOneDetails(formOneVO);
		}
		/**
		 * @author A-3456
		 * @param gpaBillingInvoiceEnquiryFilterVO
		 * @return CN51SummaryVO
		 * @throws RemoteException
		 * @throws SystemException
		 */
		public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)
		throws RemoteException, SystemException{
			log.entering("MailTrackingMRAServicesEJB", "findFormOneDetails");
			return new AirlineBillingController().findFormOneDetails
			(interlineFilterVo);
		}
		/**
		 *
		 */
		public void saveFormOneDetails(FormOneVO formOneVO)throws SystemException, RemoteException {
			log.log(Log.INFO,"ejb done");
			 new AirlineBillingController().saveFormOneDetails(formOneVO);
		}
		/**
		 * @author a-3108
		 * @param airlineForBillingVOs
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void saveFormThreeDetails(
				Collection<AirlineForBillingVO> airlineForBillingVOs)
				throws RemoteException, SystemException {
			log.entering(CLASS_NAME, "saveFormThreeDetails");
			new AirlineBillingController().saveFormThreeDetails(airlineForBillingVOs);
		}
		/**
		 * @author a-3108
		 * @param airlineForBillingVOs
		 * @return
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Collection<AirlineForBillingVO> findFormThreeDetails(
				InterlineFilterVO interlineFilterVO)
				throws RemoteException, SystemException {
			log.entering(CLASS_NAME, "findFormThreeDetails");
			return new AirlineBillingController().findFormThreeDetails(interlineFilterVO);
		}
		/**
		 * @author A-3429
		 * @param listCCAFilterVo
		 * @return Page<CCAdetailsVO>
		 * @throws RemoteException
		 * @throws SystemException
		 */

		public Page<CCAdetailsVO> listCCAs(
				ListCCAFilterVO listCCAFilterVo) throws RemoteException,
				SystemException {
			log.entering(CLASS_NAME, "findFormTwoDetails");
			return new MRADefaultsController()
					.listCCAs(listCCAFilterVo);

		}


		 /**
		 * @author A-3456
		 * @param AirlineCN51FilterVO
		 * @throws SystemException
		 * @throws RemoteException
		 */
	public AirlineCN51SummaryVO findCaptureInvoiceDetails(AirlineCN51FilterVO airlineCN51FilterVO) throws RemoteException, SystemException {

		log.entering(CLASS_NAME, "findCaptureInvoiceDetails");
		return new AirlineBillingController().findCaptureInvoiceDetails(airlineCN51FilterVO);


		}

	/**
	  * @author a-3447
	  * @param airlineCN51SummaryVO
	  * @throws SystemException
	  * @throws RemoteException
	 * @throws CreateException
	  */

		public void updateBillingDetailCommand(AirlineCN51SummaryVO airlineCN51SummaryVO) throws SystemException, RemoteException {

			log.entering(CLASS_NAME, "updateBillingDetailCommand");
			AirlineBillingController airlineBillingController=new AirlineBillingController();
			try{
				airlineBillingController.updateBillingDetailCommand(airlineCN51SummaryVO);

			}
			catch(CreateException createException){
				throw new SystemException(createException.getErrorCode(), createException);
			}
		 }






	/**
	 *@author A-2554
	 *@param prorationFilterVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException, RemoteException {
			return new MRADefaultsController().listProrationDetails(prorationFilterVO);
	}
	/**
	 * Method to print CCAs
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-3429
	 */
	public Map<String, Object> printCCAsReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findExportBillingEntries");
		return new MRADefaultsController().printCCAsReport(reportSpec);
	}

	/**
	 * @author a-3434
	 * @param airlineBillingFilterVO
	 * @return documentBillingDetailsVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<DocumentBillingDetailsVO> findInterlineBillingEntries (AirlineBillingFilterVO airlineBillingFilterVO)
	throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "findInterlineBillingEntries");
		return new MRADefaultsController().findInterlineBillingEntries(airlineBillingFilterVO);
	}
	/* @author A-3434 .save the billing status and remarks
	 *
	 * @param Collection<DocumentBillingDetailsVO>
	 *
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void changeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)throws
	RemoteException, SystemException {
		log.entering("MailTrackingMRAServicesEJB", "changeStatus");
		try{
			MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
			mRADefaultsController.changeStatus(documentBillingDetailsVOs);
		}

		catch (CreateException createException) {
		throw new SystemException(createException.getErrorCode(),createException);
		}
	}
	/* @author A-3434 .update the review flag
	 *
	 * @param cN66DetailsVO
	 *
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void changeReview(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)throws
	RemoteException, SystemException {
		log.entering("MailTrackingMRAServicesEJB", "changeReview");
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		mRADefaultsController.changeReview
		(documentBillingDetailsVOs);
	}
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
					MailTrackingMRABusinessException{
				return new GPABillingController().printGPABbillingInvoiceEnquiry(reportSpec);
		}

			/**
			 *@author A-3229
			 *@param prorationFilterVO
			 *@throws SystemException
			 *@throws RemoteException
			 *@return Collection<ProrationDetailsVO>
			 */
			public void saveProrationDetails(Collection<ProrationDetailsVO> prorationDetailsVO)throws SystemException, RemoteException {
			try{
				 new MRADefaultsController().saveProrationDetails(prorationDetailsVO);
			} catch (FinderException finderException) {
				throw new SystemException(finderException.getErrorCode(),
						finderException);
			}
		}

		/**
		 *@author A-3229
		 *@param prorationFilterVO
		 *@throws SystemException
		 *@throws RemoteException
		 *@return Collection<ProrationDetailsVO>
		 */
	public String findInvoiceListingCurrency(AirlineCN51FilterVO filterVO) throws SystemException, RemoteException {

			 return new AirlineBillingController().findInvoiceListingCurrency(filterVO);

	}

	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return RateAuditVO
	 */
	public  RateAuditVO computeTotalForRateAuditDetails(RateAuditVO newRateAuditVO) throws SystemException , RemoteException,MailTrackingMRABusinessException {
		try {
			return  new MRADefaultsController().computeTotalForRateAuditDetails(newRateAuditVO);
		} catch (ProrationFactorNotFoundException ex) {
			throw new MailTrackingMRABusinessException(ex);
		}catch (MRARateAuditDetailsException ex) {
				throw new MailTrackingMRABusinessException(ex);
			}

	}

	/**
	 * This method prints the Proforma-Invoice Diference details
	 * @author A-3271
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ReportGenerationException
	 *
	 */
	public Map<String, Object> generateProformaInvoiceDiffReport(ReportSpec reportSpec)
	throws RemoteException, SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME,"generateProformaInvoiceDiffReport");
		return new GPABillingController().generateProformaInvoiceDiffReport(reportSpec);
	}


	/**Method for issuing rejection memo
	 * @author A-3447
	 * @param rejectionMemoVO
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws FinderException
	 */
	public String saveRejectionMemos(RejectionMemoVO rejectionMemoVO)	throws RemoteException, SystemException{
		try{

		return new AirlineBillingController().saveRejectionMemos(rejectionMemoVO);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}
	}

	/**
	 * @author A-3251
	 * @param companyCode
	 * @param subclass
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String validateMailSubClass(String companyCode,String subclass)
			throws SystemException , RemoteException{

		return new MRADefaultsController().validateMailSubClass(companyCode,subclass);

	}
	/**
	 * @author a-3429
	 * Method to find flight segment details
	 *
	 * @param flightSectorRevenueFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<SectorRevenueDetailsVO> findSectorDetails(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) throws SystemException,
			RemoteException {

		return new MRADefaultsController().findSectorDetails(flightSectorRevenueFilterVO);
	}
	/**
	 * @author a-3429
	 * Method to find the flight revenue for a
	 * specified sector
	 * @param flightSectorRevenueFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO) throws SystemException,
			RemoteException {

		return new MRADefaultsController().findFlightRevenueForSector(flightSectorRevenueFilterVO);
	}






	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public  void saveRateAuditDetails(RateAuditVO newRateAuditVO) throws SystemException , RemoteException,MailTrackingMRABusinessException {
		  new MRADefaultsController().saveRateAuditDetails(newRateAuditVO);
	}




	/**
	 * @author a-3229
	 * Method to find the routing details for a
	 * specified dsn
	 * @param dsnRoutingFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<DSNRoutingVO> findDSNRoutingDetails(
			DSNRoutingFilterVO dsnRoutingFilterVO) throws SystemException,
			RemoteException {

		return new MRADefaultsController().findDSNRoutingDetails(dsnRoutingFilterVO);
	}

	/**
	 *@author A-3229
	 *@param dsnRoutingVOs
	 *@return
	 *@throws SystemException
	 *@throws RemoteException
	 */
	public void saveDSNRoutingDetails(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "saveDSNRoutingDetails");
		 //new MRADefaultsController().saveDSNRoutingDetails(dsnRoutingVOs);
		  ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).saveDSNRoutingDetails(dsnRoutingVOs);
			log.exiting(CLASS_NAME, "saveDSNRoutingDetails");
}


	/**
	 *@author A-2107
	 *@param unaccountedDispatchesFilterVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return UnaccountedDispatchesVO
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws SystemException,RemoteException{
		return new MRADefaultsController().listUnaccountedDispatches(unaccountedDispatchesFilterVO);
	}

	/**
 	 * @author A-2107
	 * @param reportSpec
	 * @throws SystemException
	 * @throws RemoteException
	 * @return reportSpec
	 */
	public Map generateUnaccountedDispachedReport(ReportSpec reportSpec)
	throws RemoteException, SystemException{

		log.entering("MailTracking Defaults Services EJB", "generateMailHandedOverReport");
		return new MRADefaultsController().generateUnaccountedDispachedReport(reportSpec);
	}


	/**
	 *@author A-2554
	 *@param airlineBillingDetailVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return AirlineBillingDetailVO
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			   AirlineBillingDetailVO  airlineBillingDetailVO)
				throws SystemException,RemoteException {
			log.entering(CLASS_NAME, "findInterLineBillingDetails");
			return new MRADefaultsController().findInterLineBillingDetails(airlineBillingDetailVO);
	}




	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public  void populateInitialDataInTempTables(RateAuditVO newRateAuditVO) throws SystemException , RemoteException {
		  new MRADefaultsController().populateInitialDataInTempTables(newRateAuditVO);
	}


	/**
	 * @author A-3251
	 * @param newRateAuditVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
	public  void removeRateAuditDetailsFromTemp(RateAuditVO newRateAuditVO) throws SystemException , RemoteException {
		  new MRADefaultsController().removeRateAuditDetailsFromTemp(newRateAuditVO);
	}


	/**Method for Printing CCA Details
	 * @author A-3447
	 * @param reportSpec
	 * @return Map
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> printCCAReport(ReportSpec reportSpec) throws SystemException, RemoteException,MailTrackingMRABusinessException{
		log.entering(CLASS_NAME, "printCCAReport");
		return new MRADefaultsController().printCCAReport(reportSpec);
	}


 	/**
 	 * @author A-3447
 	 * @param airlineCN51FilterVO
 	 * @return AirlineCN51SummaryVO
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * Method to pick all invoice flags form table
 	 *
 	 */
 	public AirlineForBillingVO findAllInvoiceFlags(AirlineCN51FilterVO airlineCN51FilterVO) throws RemoteException, SystemException {

		log.entering(CLASS_NAME, "findAllInvoiceFlags");
		return new AirlineBillingController().findAllInvoiceFlags(airlineCN51FilterVO);


		}

 	/**
	 * Method to printGPABillingInvoices
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-3429
	 */
	public Map<String, Object> printGPABillingInvoice(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findExportBillingEntries");
		return new GPABillingController().printGPABillingInvoice(reportSpec);
	}
	/**
	 * Method to printGPAInvoiceCoveringrpt
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-8527
	*/
	public Map<String, Object> printGPAInvoiceCoveringrpt(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findExportBillingEntries");
		return new GPABillingController().printGPAInvoiceCoveringrpt(reportSpec);
	}

	/**
	 * @param prorationExceptionsFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<ProrationExceptionVO> findProrationExceptions (
			ProrationExceptionsFilterVO prorationExceptionsFilterVO)
			throws SystemException, RemoteException{
		 log.entering("MailTrackingMRAServicesEJB", "findProrationExceptions");
	        return (new MRADefaultsController()).findProrationExceptions(prorationExceptionsFilterVO);

	}

	/**
	 * @param prorationExceptionVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveProrationExceptions(
			Collection<ProrationExceptionVO> prorationExceptionVOs)
			throws SystemException, RemoteException{
		log.entering("MailTrackingMRAServicesEJB", "saveProrationExceptions");
        (new MRADefaultsController()).saveProrationExceptions(prorationExceptionVOs);

	}

/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 *
	 */
	public Map<String, Object> printProrationExceptionReport(ReportSpec reportSpec)
	throws SystemException, RemoteException{
		 log.entering("MailTrackingMRAServicesEJB", "printProrationExceptionReport");
	        try
	        {
	            return (new MRADefaultsController()).printProrationExceptionReport(reportSpec);
	        }
	        catch(MailTrackingMRABusinessException e)
	        {
	            throw new SystemException(e.getErrors());
	        }


	}




	/**
	 * @author a-3229
	 * @param dsnFilterVO
	 * @return MailProrationLogVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<MailProrationLogVO> findProrationLogDetails(
			DSNFilterVO dsnFilterVO) throws SystemException,
			RemoteException {

		return new MRADefaultsController().findProrationLogDetails(dsnFilterVO);
	}

	/**
	 *@author A-3229
	 *@param prorationFilterVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO)throws SystemException, RemoteException {
			return new MRADefaultsController().viewProrationLogDetails(prorationFilterVO);
	}

	/**
	 *@author A-3229
	 *@param dsnPopUpVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException {
			return new MRADefaultsController().findFlownDetails(dsnPopUpVO);
	}
	/**
	 * Method to print Document Statistics
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-3429
	 */
	public Map<String, Object> printDocumentStatisticsReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "printDocumentStatisticsReport");
		return new MRADefaultsController().printDocumentStatisticsReport(reportSpec);
	}

	/**
	 * Method to saveRejectionMemo
	 *
	 * @param rejectionMemoVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @return String
	 * @author A-3429
	 */

	public String saveRejectionMemoForDsn(RejectionMemoVO rejectionMemoVO) throws RemoteException, SystemException{

		return new AirlineBillingController().saveRejectionMemoForDsn(rejectionMemoVO);

	}


	/**
	 * @author A-3251
	 * @param postalAdministrationDetailsVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
   public PostalAdministrationDetailsVO validatePoaDetailsForBilling(PostalAdministrationDetailsVO postalAdministrationDetailsVO) throws SystemException , RemoteException{

			return new MRADefaultsController().validatePoaDetailsForBilling(postalAdministrationDetailsVO);
	}


	/**
	 * @author A-3229
	 * @param MRAIrregularityFilterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return Collection<IrregularityVO>
	 */
	public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)throws SystemException,RemoteException{
		 log.entering("MailTrackingMRAServicesEJB", "viewIrregularityDetails");
		return new MRADefaultsController().viewIrregularityDetails(filterVO);
	}
	/**
	 * @author A-3229 This method is to print irregularitydetails
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> printIrregularityReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering("MailTrackingMRAServicesEJB", "printIrregularityReport");
		return new MRADefaultsController()
				.printIrregularityReport(reportSpec);
	}

	/**
	 *@author A-3229
	 *@param dsnPopUpVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return Collection<MRAAccountingVO>
	 */
	public Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException {
		log.entering("MailTrackingMRAServicesEJB", "findAccountingDetails");
			return new MRADefaultsController().findAccountingDetails(dsnPopUpVO);
	}
	/**
	 *@author A-3229
	 *@param dsnPopUpVO
	 *@throws SystemException
	 *@throws RemoteException
	 *@return Collection<USPSReportingVO>
	 */
	public Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO dsnPopUpVO)throws SystemException, RemoteException {
		log.entering("MailTrackingMRAServicesEJB", "findUSPSReportingDetails");
			return new MRADefaultsController().findUSPSReportingDetails(dsnPopUpVO);
	}



  /**
   * Added by A-2107 for getTotalOfDispatches
   * @param unaccountedDispatchesFilterVO
   * @return UnaccountedDispatchesVO
   * @throws SystemException
   */
  public UnaccountedDispatchesVO getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)throws SystemException , RemoteException {
		log.entering(CLASS_NAME, "getTotalOfDispatches");
		return new MRADefaultsController().getTotalOfDispatches(unaccountedDispatchesFilterVO);

	}


    /**
	 * @author A-3251
	 * @param dsnRoutingVOs
	 *
	 */
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException , RemoteException {

		// new MRADefaultsController().reProrateDSN(dsnRoutingVOs);
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		mRADefaultsController.reProrateDSN(dsnRoutingVOs);


	}



	/**
	 * Method to printFormTwoDetails
	 *
	 * @param reportSpec
	 * @return Map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @author A-3429
	 */
	public Map<String, Object> viewFormTwoPrint(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "viewFormTwoPrint");
		return new AirlineBillingController().viewFormTwoPrint(reportSpec);
	}

	/**
	 *@author A-2554
	 *@param cN51SummaryVO
	 *@throws RemoteException
	 *@throws SystemException
	 */
	public String generateEInvoiceMessage(CN51SummaryVO cN51SummaryVO) throws RemoteException, SystemException {

		log.entering(CLASS_NAME, "generateEInvoiceMessage");
		 return new GPABillingController().generateEInvoiceMessage(cN51SummaryVO);


	}
/**
	 * @author A-3434
	 * @param outstandingBalanceVO
	 * @throws SystemException
	 * @throws RemoteException	 *
	 */
  public Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)
  throws SystemException , RemoteException {

	  return new MRADefaultsController().findOutstandingBalances(outstandingBalanceVO);
	}

  /** * calls accounting procedure */
	public void performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		new MRADefaultsController().performAccountingForDSNs(unAccountedDSNVOs);
	}


	/**
	 * @author a-2391
	 * @param reportSpec
	 * @return map
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> generateCN66InvoiceReport(ReportSpec reportSpec)
			throws RemoteException, SystemException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateCN66InvoiceReport");
		return new AirlineBillingController().generateCN66InvoiceReport(reportSpec);

	}
	/**
	 * Method to accept airlineinvoices
	 *
	 * @param airlineExceptionsVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @author A-3429
	 */
	public void acceptAirlineDsns(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws RemoteException, SystemException {
		log.entering(CLASS_NAME, "acceptAirlineInvoices");
			AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
			airlineBillingController.acceptAirlineDsns(airlineExceptionsVOs);
	}
	/**
 	 * @author A-2391
 	 * @param gpaCode,gpaName,country
 	 * @return Collection<FuelSurchargeVO>
 	 * @throws BusinessDelegateException
 	 */
	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCod)
 	throws BusinessDelegateException,RemoteException, SystemException{
		log.entering(CLASS_NAME, "displayFuelSurchargeDetails");
		return new MRADefaultsController()
				.displayFuelSurchargeDetails(gpaCode,cmpCod);
	}
	/**
	 * @author A-2391
	 * @param fuelSurchargeVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveFuelSurchargeDetails(Collection<FuelSurchargeVO> fuelSurchargeVOs)
			throws BusinessDelegateException,RemoteException, SystemException{
		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");
		 new MRADefaultsController()
				.saveFuelSurchargeDetails(fuelSurchargeVOs);
	}
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
			throws BusinessDelegateException ,RemoteException, SystemException, MailTrackingMRABusinessException{
		log.entering(CLASS_NAME, "saveFuelSurchargeDetails");
		// new MRADefaultsController()
		//		.saveHistoryDetails(ccadetailsVO);
		 MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		    mRADefaultsController.saveHistoryDetails(ccadetailsVO);


	}

	/**
	 * @author A-3434
	 * @param reportSpec
	 * @throws RemoteException
	 * @throws ReportGenerationException
	 * @throws SystemException
	 */
	public Map<String, Object> findRateAuditDetailsPrint(ReportSpec reportSpec)
    throws SystemException, RemoteException, ReportGenerationException,MailTrackingMRABusinessException {
		log.entering("MailtrackingMRAServicesEJB","findRateAuditDetailsPrint");

		return new MRADefaultsController().findRateAuditDetailsPrint(reportSpec);
	}

	// Added by A-3434 for Clearance period validation
	/**
	 * @param iataCalenderFilterVO
	 * @return IATACalendarVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public IATACalendarVO validateClearancePeriod(
			IATACalendarFilterVO iataCalenderFilterVO) throws SystemException,
			RemoteException {
		log.entering("MailtrackingMRAServicesEJB", "validateClearancePeriod");
		log.exiting("MailtrackingMRAServicesEJB", "validateClearancePeriod");
		return new AirlineBillingController()
				.validateClearancePeriod(iataCalenderFilterVO);

	}
	/**
	 * @author A-3429 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException, RemoteException {
		log.entering("MailtrackingMRAServicesEJB", "findPACode");
		return new MRADefaultsController().findPACode(companyCode, paCode);
	}
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
	public Page<PostalAdministrationVO> findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultSize)
			throws SystemException, RemoteException {
		log.entering("MailtrackingMRAServicesEJB", "findPALov");
		return new GPABillingController().findPALov(companyCode, paCode, paName,
				pageNumber, defaultSize);
	}

	public void printMailRevenueReport (FlownMailFilterVO flownMailFilterVO ) throws SystemException,RemoteException,FlownException{
		 log.entering("MailtrackingMRAServicesEJB", "printMailRevenueReport");
		 new FlownController().printMailRevenueReport(flownMailFilterVO);

	}


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
			throws SystemException, RemoteException, MailTrackingMRABusinessException {
		log.entering("MailtrackingMRAServicesEJB",
				"generateMailExceptionReport");
		return new MRADefaultsController()
				.generateMailExceptionReport(mailExceptionReportsFilterVo);
	}
	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO) throws SystemException,RemoteException{
		log.entering(CLASS_NAME,"findSettlementDetails");
		return new GPABillingController().findUnSettledInvoicesForGPA(gpaSettlementVO);
	}

	/**
	 * @author a-4823
	 * @param reportSpec
	 * @throws SystemException
	 */
	public Map<String, Object> printSettlementDetails(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().printSettlementDetails(reportSpec);
	}
	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws SystemException
	 *
	 */
	public Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO) throws SystemException,RemoteException{
		log.entering(CLASS_NAME,"findSettlementDetails");
		return new GPABillingController().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementVO);
	}
	/**
	 * @author a-4823
	 * @param billingSummaryDetailsFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> findPOMailSummaryDetails(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().findPOMailSummaryDetails(reportSpec);
	}
	/**
	 * @author a-4823
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<CCAdetailsVO> findMCALov(
			CCAdetailsVO ccAdetailsVO,int displayPage) throws SystemException,RemoteException{
		log.entering(CLASS_NAME,"findMCALov");
		return new MRADefaultsController().findMCALov(ccAdetailsVO,displayPage);
	}
	/**
	 *
	 * @param ccAdetailsVO
	 * @param displayPage
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<CCAdetailsVO> findDSNLov(
			CCAdetailsVO ccAdetailsVO,int displayPage) throws SystemException,RemoteException{
		log.entering(CLASS_NAME,"findDSNLov");
		return new MRADefaultsController().findDSNLov(ccAdetailsVO,displayPage);
	}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN51ReportFromCN51Cn66(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().generateCN51ReportFromCN51Cn66(reportSpec);
	}
	/**
	 * @author a-4823
	 * @param cCADetailsVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @throws BusinessDelegateException
	 */
	public String saveMCAdetails(CCAdetailsVO cCADetailsVO)
	throws SystemException, RemoteException,MailTrackingMRABusinessException, BusinessDelegateException {
		log.log(Log.FINE, "<--Inside EJB SErvices:Save--->");
		log.log(Log.FINE, "inside Ejb", cCADetailsVO);
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		return mRADefaultsController.saveMCAdetails(cCADetailsVO);
	}
	/**
	 * @author a-4823
	 * @param maintainCCAFilterVO
	 */
	public Collection<CCAdetailsVO> findApprovedMCA(MaintainCCAFilterVO maintainCCAFilterVO)
	throws SystemException, RemoteException {
		return new MRADefaultsController().findApprovedMCA(maintainCCAFilterVO);
}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN66Report6E(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().generateCN66Report6E(reportSpec);
	}
	/**
	 * @author A-5166
	 * @param FlightSegmentFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO) throws SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "findRoutingCarrierDetails");
		return new MRADefaultsController().findRoutingCarrierDetails(carrierFilterVO);
	}
	/**
	 * @author A-5166
	 * @param FlightSegmentFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveRoutingCarrierDetails(Collection<RoutingCarrierVO> routingCarrierVOs) throws SystemException,
			RemoteException {
		log.entering(CLASS_NAME, "saveRoutingCarrierDetails");
		new MRADefaultsController().saveRoutingCarrierDetails(routingCarrierVOs);
	}
	/**
	 *  @author A-5166
	 *  Added for ICRD-17262 on 07-Feb-2013
	 * @param rateCardVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveCopyRateCard(RateCardVO rateCardVO)throws SystemException, RemoteException,
	MailTrackingMRABusinessException{
		log.entering(CLASS_NAME,"saveCopyRateCard");
		try{
		new MRADefaultsController().saveCopyRateCard(rateCardVO);
		} catch (RateCardException ratecardException) {
			throw new MailTrackingMRABusinessException(ratecardException);
		}
		log.exiting(CLASS_NAME, "saveCopyRateCard");
	}
	/**
	 * @author A-5166
	 * Added for ICRD-36146 on 06-Mar-2013
	 * @param companyCode
	 * @throws RemoteException
	 * @throws SystemException
	 */

	public void initiateProration(String companyCode)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "initiateProration");
		new MRADefaultsController().initiateProration(companyCode);
	}
	/**
	 * Save Billing Site Details.
	 *
	 * @param billingSiteVO the billing Site VO
	 * @return the billing site vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @throws RemoteException the remote exception
	 * @throws MailTrackingMRABusinessException the mail tracking mra business exception
	 * @author A-5219
	 */
	public BillingSiteVO saveBillingSiteDetails(BillingSiteVO billingSiteVO)
	throws SystemException, PersistenceException,RemoteException, MailTrackingMRABusinessException{
		log.entering(CLASS_NAME,"saveBillingSiteDetails");
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		return mRADefaultsController.saveBillingSiteDetails(billingSiteVO);
	}
	/**
	 * Save Find Billing Site Details.
	 *
	 * @param billingSiteFilterVO the billing Site filter VO
	 * @return the collection
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 * @author A-5219
	 */
	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	throws RemoteException,SystemException{
		log.entering(CLASS_NAME, "findBillingSiteDetails");
		return new MRADefaultsController().findBillingSiteDetails(billingSiteFilterVO);
	}
	/**
	 * Save List Parameter LOV.
	 *
	 * @param bsLovFilterVo the bs lov filter vo
	 * @return the page
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 * @author A-5219
	 */
	public Page<BillingSiteLOVVO> listParameterLov(
			BillingSiteLOVFilterVO bsLovFilterVo) throws RemoteException,
			SystemException {
		log.entering(CLASS_NAME, "displayBillingSiteLOVVO");
		return new MRADefaultsController().listParameterLov(bsLovFilterVo);
	}
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.generateInvoiceTK
 *	Added by 	:	A-4809 on 06-Jan-2014
 * 	Used for 	:	ICRD-42160
 *	Parameters	:	@param generateInvoiceFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Return type	: 	void
 */
	public void generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws RemoteException, SystemException,
	MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateInvoiceTK");
		//new GPABillingTKController().generateInvoiceTK(generateInvoiceFilterVO);
		 ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).generateInvoiceTK(generateInvoiceFilterVO);
		log.exiting(CLASS_NAME, "generateInvoiceTK");

}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#sendEmail(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO)
 *	Added by 			: A-4809 on 09-Jan-2014
 * 	Used for 	:	ICRD-42160 emailInvoice
 *	Parameters	:	@param cN51CN66VO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public void sendEmailInvoice(CN51CN66VO cN51CN66VO) throws RemoteException,
		SystemException {
	log.entering(CLASS_NAME, "sendEmailInvoice");
	new GPABillingController().sendEmailInvoice(cN51CN66VO);
	log.exiting(CLASS_NAME, "sendEmailInvoice");
}
/**
 * 	Method		:	MailTrackingMRAServicesEJB.addLocks
 *	Added by 	:	A-4809 on 10-Jan-2014
 * 	Used for 	:	ICRD-42160
 *	Parameters	:	@param addLocks
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Collection<LockVO>
 */
public Collection<LockVO> generateInvoiceLock(String companyCode)throws RemoteException,
SystemException,ObjectAlreadyLockedException{
	log.entering(CLASS_NAME, "addLocks");
	return new GPABillingController().addLocks(companyCode);
}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateCN51ReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().generateCN51ReportTK(reportSpec);
	}
	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateInvoiceReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().generateInvoiceReportTK(reportSpec);
	}

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws MailTrackingMRABusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, Object> generateGPAInvoiceReportTK(ReportSpec reportSpec) throws MailTrackingMRABusinessException, SystemException,RemoteException{
		return new GPABillingController().generateGPAInvoiceReportTK(reportSpec);
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
	return new GPABillingController().generateCN51ReportPrint(filterVO);
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
	return new GPABillingController().generateCN66ReportPrint(filterVO);
}
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateGPAInvoiceReportTK(com.ibsplc.icargo.framework.report.vo.ReportSpec)
 *	Added by 			: A-4809 on 25-Feb-2014
 * 	Used for 	:
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@return
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public Map<String, Object> generateGPAInvoiceReport(ReportSpec reportSpec)
throws MailTrackingMRABusinessException, SystemException,RemoteException{
	return new GPABillingController().generateGPAInvoiceReport(reportSpec);
}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateGPABillingInvoiceReportTK(com.ibsplc.icargo.framework.report.vo.ReportSpec)
 *	Added by 			: A-4809 on 27-Feb-2014
 * 	Used for 	:
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@return
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public Map<String, Object> generateGPABillingInvoiceReportTK(ReportSpec reportSpec)
throws MailTrackingMRABusinessException, SystemException,RemoteException{
	return new GPABillingController().generateGPABillingInvoiceReportTK(reportSpec);
}

/**
 *  Method for updating the updateInvoiceReference number for THY invoice printing
 * 	Method		: GPABillingController.updateInvoiceReference
 *	Added by 	: A-5273 on Mar 20, 2014
 * 	@param aoInvoiceReportDetailsVO
 * 	@throws SystemException
 *  void
 */
public void updateInvoiceReference(	AOInvoiceReportDetailsVO aoInvoiceReportDetailsVO)
			throws RemoteException, SystemException {
	 log.entering(CLASS_NAME, "updateInvoiceReference");
	 GPABillingTKController gpaBillingTKController = (GPABillingTKController)SpringAdapter.getInstance().getBean("mRAGpaBillingTKcontroller");
	 gpaBillingTKController.updateInvoiceReference(aoInvoiceReportDetailsVO);
	 log.exiting(CLASS_NAME, "updateInvoiceReference");

}


public String processBillingMatrixUploadDetails(FileUploadFilterVO filterVO)
throws RemoteException, SystemException {
	log.entering(CLASS_NAME, "processBillingMatrixUploadDetails");
	return new MRADefaultsController().processBillingMatrixUploadDetails(filterVO);
	}



public void sendEmailforPAs(Collection<PostalAdministrationVO> postalAdministrationVOs,
		GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException, RemoteException{

		 log.entering(CLASS_NAME, "sendEmailforPAs");
		 new GPABillingController().sendEmailforPAs(postalAdministrationVOs, generateInvoiceFilterVO);
		 log.exiting(CLASS_NAME, "sendEmailforPAs");
	}
/**
 * 	Method		:	MailTrackingMRAServicesEJB.prorateExceptionFlights
 *	Added by 	:	A-6245 on 17-06-2015
 * @param flightValidationVOs
 * @throws SystemException
 * @throws RemoteException
 */

public  void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
throws SystemException, RemoteException{
	 log.entering("MailTrackingMRAServicesEJB", "prorateExceptionFlights");
      // (new MRADefaultsController()).prorateExceptionFlights(flightValidationVOs);
	 MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
	    mRADefaultsController.prorateExceptionFlights(flightValidationVOs);

}

/**
 * Method	:MailTrackingMRAServicesEJB.validateFlightForAirport
 * @param flightFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<FlightValidationVO> validateFlightForAirport(
		FlightFilterVO flightFilterVO) throws SystemException,
		RemoteException {
	log.entering(CLASS_NAME, "validateFlightForAirport");
	return new FlownController().validateFlightForAirport(flightFilterVO);
}


/**
 *Added by A-3434 for CR ICRD-114599 on 29SEP2015
 *
 * @param invoiceTransactionLogVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
		InvoiceTransactionLogVO invoiceTransactionLogVO)
		throws SystemException, RemoteException{
	return new MRADefaultsController().initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
}

/*Added by A-3434 for CR ICRD-114599 on 29SEP2015
 *
 * param generateInvoiceFilterVO
 * @return boolean
 * @throws BusinessDelegateException
 *
 * */
public Boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO)
		throws SystemException, RemoteException {

	return new MRADefaultsController().validateGpaBillingPeriod(generateInvoiceFilterVO);
}

/**
 * 	Method		:	MailTrackingMRAServicesEJB.importArrivedMailstoMRA
 *	Added by 	:	A-4809 on Oct 12, 2015
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void importArrivedMailstoMRA(String companyCode)
		throws SystemException,RemoteException{
	log.entering(CLASS_NAME, "importArrivedMailstoMRA");
	//new FlownController().importArrivedMailstoMRA(companyCode);
	FlownController flownController = (FlownController)SpringAdapter.getInstance().getBean("fLowncontroller");
	flownController.importArrivedMailstoMRA(companyCode);
	log.exiting(CLASS_NAME, "importArrivedMailstoMRA");
}




  /**
 *
 * @author A-5255
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public  Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(ProrationFilterVO prorationFilterVO)
throws SystemException, RemoteException{
	 log.entering("MailTrackingMRAServicesEJB", "viewSurchargeProrationDetails");
       return new MRADefaultsController().viewSurchargeProrationDetails(prorationFilterVO);
}
/**
 *
 * @author A-5255
 * @param maintainCCAFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeCCAdetailsVO> getSurchargeCCADetails(
		MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException, RemoteException{
	 log.entering("MailTrackingMRAServicesEJB", "getSurchargeCCADetails");
     return new MRADefaultsController().getSurchargeCCADetails(maintainCCAFilterVO);
}
/**
 *
 * @author A-5255
 * @param cn51CN66FilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(
		CN51CN66FilterVO cn51CN66FilterVO)throws SystemException, RemoteException{
	log.entering("MailTrackingMRAServicesEJB", "findSurchargeBillingDetails");
    return new MRADefaultsController().findSurchargeBillingDetails(cn51CN66FilterVO);
}
/**
 *
 * @author A-5255
 * @param blgMatrixFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(
		BillingMatrixFilterVO blgMatrixFilterVO)throws SystemException, RemoteException{
	log.entering("MailTrackingMRAServicesEJB", "findBillingMatrixAuditDetails");
    return new MRADefaultsController().findBillingMatrixAuditDetails(blgMatrixFilterVO);
}

/**
 *
 * @author A-6245
 * @param cn51CN66FilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(
		CN51CN66FilterVO cn51CN66FilterVO)throws SystemException, RemoteException{
	log.entering("MailTrackingMRAServicesEJB", "findSurchargeBillableDetails");
    return new MRADefaultsController().findSurchargeBillableDetails(cn51CN66FilterVO);
}
/**
 *  @author A-6245
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public  Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(ProrationFilterVO prorationFilterVO)
throws SystemException, RemoteException{
	 log.entering("MailTrackingMRAServicesEJB", "viewSurchargeProrationDetailsForMCA");
       return new MRADefaultsController().viewSurchargeProrationDetailsForMCA(prorationFilterVO);
}

public void generateInterfaceFile(LocalDate uploadTime,String uploadTimeStr)
		throws RemoteException,SystemException{
	log.entering("MailTrackingMRAServicesEJB", "generateInterfaceFile");
	MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
	mRADefaultsController.generateInterfaceFile(uploadTime,uploadTimeStr);
	}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findDespatchDetails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO)
 *	Added by 			: A-4809 on Dec 23, 2016
 * 	Used for 	:
 *	Parameters	:	@param popUpVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)
		throws SystemException, RemoteException {
	log.entering(CLASS_NAME, "findDespatchDetails");
	return new MRADefaultsController().findDespatchDetails(popUpVO);
	}

public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		 throws RemoteException, SystemException {
	log.entering(CLASS_NAME, "withdrawMailbags");

	 new GPABillingController().withdrawMailbags(documentBillingDetailsVOs);
	 log.exiting(CLASS_NAME, "withdrawMailbags");
	}


public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
		throws RemoteException, SystemException {
	log.entering(CLASS_NAME, "withdrawMailbags");
	log.log(log.FINE, "method",new GPABillingController().getClass().getProtectionDomain().getCodeSource().getLocation());
	 new GPABillingController().finalizeProformaInvoice(summaryVOs);
	 log.exiting(CLASS_NAME, "finalizeProformaInvoice");
}

public void withdrawInvoice(CN51SummaryVO summaryVO)
		throws SystemException, RemoteException {
	log.entering(CLASS_NAME, "findDespatchDetails");
	log.log(log.FINE, "method",new GPABillingController().getClass().getProtectionDomain().getCodeSource().getLocation());
	 new GPABillingController().withdrawInvoice(summaryVO);
	}



/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#reRateMailbags(java.util.Collection)
 *	Added by 			: A-7531
 *	Parameters	:	@param mRABillingDetailsVOs
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */


public void reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)
		throws SystemException, RemoteException {
			log.entering(CLASS_NAME, "reratemailbags");
		 MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		 mRADefaultsController.reRateMailbags(documentBillingDetailsVOs,txnlogInfo);

	}

/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findRerateBillableMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */

public Collection<DocumentBillingDetailsVO> findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,
		String companyCode) throws SystemException,
		RemoteException {
	 log.entering(CLASS_NAME, "findRerateBillableMails");
	 return new MRADefaultsController().findRerateBillableMails(documentBillingVO,companyCode);

}
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findRerateInterlineBillableMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */

public Collection<DocumentBillingDetailsVO> findRerateInterlineBillableMails(DocumentBillingDetailsVO documentBillingVO,String companyCode) throws SystemException,
		RemoteException {
	 log.entering(CLASS_NAME, "findRerateBillableMails");
	 return new MRADefaultsController().findRerateInterlineBillableMails(documentBillingVO,companyCode);
}

/**
 *
 * 	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#saveSAPSettlementMail(java.util.Collection)
 *	Added by 	:	A-7794
 *	Parameters	:	@param Collection<GPASettlementVO> gpaSettlementVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public Collection<ErrorVO> saveSAPSettlementMail(Collection<GPASettlementVO> gpaSettlementVOs)
		throws SystemException, RemoteException {
	Collection<ErrorVO> errorVOs = null;
			log.entering(CLASS_NAME, "saveSAPSettlementMail");
			return new GPABillingController().saveSAPSettlementMail(gpaSettlementVOs);
	  }
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.importMRAData
 *	Added by 	:	A-5526
 *	Parameters	:	@param rateAuditVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void importMRAData(Collection<RateAuditVO> rateAuditVOs)
	    throws RemoteException, SystemException
	  {
	    this.log.entering("MailTrackingMRAServicesEJB", "importMRAData");
	   /* new MRADefaultsController()
	      .importMRAData(rateAuditVOs);*/

	    MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
	    mRADefaultsController.importMRAData(rateAuditVOs);


	  }

/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findReproarteMails(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-7531 on 08-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public Collection<DocumentBillingDetailsVO> findReproarteMails(DocumentBillingDetailsVO documentBillingVO) throws SystemException,
RemoteException {
log.entering(CLASS_NAME, "findReproarteMails");
return new MRADefaultsController().findReproarteMails(documentBillingVO);
}
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.reProrateExceptionMails
 *	Added by 	:	A-7531 on 09-Nov-2017
 * 	Used for 	:    icrd-132487
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
public void reProrateExceptionMails(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs, String txnlogInfo)
		throws SystemException, RemoteException {
			log.entering(CLASS_NAME, "reProrateExceptionMails");
		  new MRADefaultsController().reProrateExceptionMails(documentBillingDetailsVOs,txnlogInfo);
	}

/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.generateCN51ReportKE
 *	Added by 	:	A-7794 on 02-Feb-2018
 * 	Used for 	:    ICRD-234354
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Map<String, Object>
 */
@Override
public Map<String, Object> generateCN51ReportKE(ReportSpec reportSpec)
		throws RemoteException, SystemException,
		MailTrackingMRABusinessException {
	return new GPABillingController().generateCN51ReportKE(reportSpec);
}

/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.generateCN66ReportKE
 *	Added by 	:	A-7794 on 02-Feb-2018
 * 	Used for 	:    ICRD-234354
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Map<String, Object>
 */
@Override
public Map<String, Object> generateCN66ReportKE(ReportSpec reportSpec)
		throws MailTrackingMRABusinessException, SystemException,
		RemoteException {
	return new GPABillingController().generateCN66ReportKE(reportSpec);
	}

public Collection<AWMProrationDetailsVO>  viewAWMProrationDetails(ProrationFilterVO prorationFilterVO)throws SystemException,
RemoteException{
	log.entering(CLASS_NAME, "prorationFilterVO");
	return new MRADefaultsController().viewAWMProrationDetails(prorationFilterVO);
}
 /**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateMailBillingInterfaceFile()
 *	Added by 			: A-7929 on 09-May-2018
 * 	Used for 	:ICRD-245605
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */

@Override
public  String generateMailBillingInterfaceFile(String companyCode,String regenerateFlag,String fileName,LocalDate fromDate,LocalDate toDate)
		throws MailTrackingMRABusinessException, SystemException, RemoteException {
	log.entering(CLASS_NAME, "generateMailBillingInterfaceFile");

	return ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).generateMailBillingInterfaceFile(companyCode,regenerateFlag,fileName,fromDate,toDate);


}


/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#createJobforFlightRevenueInterface()
 *	Added by 			: a-8061 on 28-Jun-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
@Override
public void createJobforFlightRevenueInterface(String companyCode) throws SystemException, RemoteException {
	new MRADefaultsController().createJobforFlightRevenueInterface();

}
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#doInterfaceFlightRevenueDtls(java.lang.String)
 *	Added by 			: a-8061 on 12-Jul-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
@Override
public  void doInterfaceFlightRevenueDtls(String companyCode, int maxRecord, boolean isFromRetrigger)
		throws MailTrackingMRABusinessException, SystemException, RemoteException {
	log.entering(CLASS_NAME, "doInterfaceFlightRevenueDtls");
	 ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).doInterfaceFlightRevenueDtls(companyCode,maxRecord,isFromRetrigger);
}

/**
 *  @author A-7794
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateFile(ISFileFilterVO filterVO)
 *	Added by 			: a-7794 on 30-Jul-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param filterVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 * @return
 */
@Override
public SISMessageVO generateFile(MiscFileFilterVO filterVO) throws SystemException,
		RemoteException {
		return new AirlineBillingController().generateFile(filterVO);
}



/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.validateBSA
 *	Added by 	:	A-8061
 *	Parameters	:	@param dSNRoutingVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	String
 */
public String validateBSA(DSNRoutingVO dSNRoutingVO)
	    throws RemoteException, SystemException
	  {
	    this.log.entering("MailTrackingMRAServicesEJB", "validateBSA");
	    return new MRADefaultsController()
	      .validateBSA(dSNRoutingVO);
	  }
  /**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#saveMCAdetailsForAutoMCA(java.util.Collection)
 *	Added by 			: A-7929 on 26-Jul-2018
 * 	Used for 	:
 *	Parameters	:	@param cCAdetailsVOs
*	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws BusinessDelegateException
 */
public void saveAutoMCAdetails(Collection<CCAdetailsVO> cCAdetailsVOs,GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
		throws SystemException, RemoteException, MailTrackingMRABusinessException, BusinessDelegateException {
	log.log(Log.FINE, "<--Inside EJB SErvices:Save--->");
	log.log(Log.FINE, "inside Ejb", cCAdetailsVOs);
	MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
	 mRADefaultsController.saveAutoMCAdetails(cCAdetailsVOs,gpaBillingEntriesFilterVO);
	//new MRADefaultsController().saveAutoMCAdetails(cCAdetailsVOs, documentBillingDetailsVO);

}
/**
 *  @author A-7794
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateBSAInterfaceDtls(Collection<FlightRevenueInterfaceVO> generateBSAInterfaceDtls)
 *	Added by 			: a-7794 on 27-Aug-2018
 * 	Used for 	:	ICRD-245574
 *	Parameters	:	@param generateBSAInterfaceDtls
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
@Override
public void generateBSAInterfaceDtls(Collection<FlightRevenueInterfaceVO> bsaInterfaceVOs)throws MailTrackingMRABusinessException, SystemException,
		RemoteException {
	log.entering(CLASS_NAME, "generateBSAInterfaceDtls");
	 ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).generateBSAInterfaceDtls(bsaInterfaceVOs);

}
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#updateMailBSAInterfacedDetails(com.ibsplc.icargo.business.cra.miscbilling.blockspace.flight.utilization.vo.BlockSpaceFlightSegmentVO)
 *	Added by 			: a-8061 on 27-Aug-2018
 * 	Used for 	:
 *	Parameters	:	@param flightPk
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public void updateMailBSAInterfacedDetails(BlockSpaceFlightSegmentVO flightPk) throws SystemException, RemoteException {

	 new MRADefaultsController().updateMailBSAInterfacedDetails(flightPk);

}

@Override
public void updateTruckCost(TruckOrderMailVO truckOrderMailVO) throws SystemException , RemoteException{

	MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");

	 mRADefaultsController.updateTruckCost(truckOrderMailVO);
}
/**
 * 	Method		:	MailTrackingMRAServicesEJB.finalizeInvoice
 *	Added by 	:	A-7929
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public void finalizeInvoice(Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO,String txnlogInfo)
		throws SystemException, RemoteException {
	try {
		new AirlineBillingController().finalizeInvoice(selectedairlineCN51SummaryVO,txnlogInfo);
	} catch (FinderException e) {

	}

}


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

public void  withdrawMailBags(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs)throws SystemException, RemoteException {

	new AirlineBillingController().withdrawMailBags(airlineCN66DetailsVOs);

}

@Override
public Collection<AirlineCN51SummaryVO> getAirlineSummaryDetails(
		AirlineCN51FilterVO filterVO) throws SystemException, RemoteException {
	return new AirlineBillingController().getAirlineSummaryDetails(filterVO);
}
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
		long flightSequenceNumber) throws SystemException,RemoteException{
			return new  FlownController().findFlightSegments(companyCode,airlineId,
					flightNumber,flightSequenceNumber); 
	
}
  /**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#saveSupportingDocumentDetails(java.util.Collection)
 *	Added by 			: a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public Collection<SisSupportingDocumentDetailsVO> saveSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
	    throws RemoteException, SystemException
	  {
	    return new AirlineBillingController().saveSupportingDocumentDetails(sisSupportingDocumentDetailsVOs);
	  }


/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#downloadAttachment(com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO)
 *	Added by 			: a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param supportingDocumentFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public SisSupportingDocumentDetailsVO downloadAttachment(SupportingDocumentFilterVO supportingDocumentFilterVO)
	    throws RemoteException, SystemException
	  {
		return new AirlineBillingController().downloadAttachment(supportingDocumentFilterVO);
	  }

/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#removeSupportingDocumentDetails(java.util.Collection)
 *	Added by 			: a-8061 on 29-Oct-2018
 * 	Used for 	:	ICRD-265471
 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public Collection<SisSupportingDocumentDetailsVO> removeSupportingDocumentDetails(Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
	    throws RemoteException, SystemException
	  {
	    return new AirlineBillingController().removeSupportingDocumentDetails(sisSupportingDocumentDetailsVOs);
	  }



/* @author A-5526 .save the billing status and remarks
*
* @param Collection<DocumentBillingDetailsVO>
*
* @throws RemoteException
* @throws SystemException
*/
public void changeStatusForInterline(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)throws
RemoteException, SystemException {
	log.entering("MailTrackingMRAServicesEJB", "changeStatus");
	
		AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
		airlineBillingController.changeStatus(documentBillingDetailsVOs);
	

	
}

	/**
	 * 
	 */
	public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws
		RemoteException, SystemException {
		log.entering("MailTrackingMRAServicesEJB", "voidMailbags");
		
		MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
		mRADefaultsController.voidMailbags(VOs);

		log.exiting("MailTrackingMRAServicesEJB", "voidMailbags");
	
	}
	
	
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#saveVoidedInterfaceDetails(java.util.Collection)
 *	Added by 			: A-8061 on 15-Oct-2019
 * 	Used for 	:	ICRD-336689
 *	Parameters	:	@param VOs
 *	Parameters	:	@throws MailTrackingMRABusinessException
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
	public void saveVoidedInterfaceDetails(Collection<DocumentBillingDetailsVO> VOs)throws MailTrackingMRABusinessException, SystemException, RemoteException{
		 ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).saveVoidedInterfaceDetails(VOs);
		
	}

/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMailbagBillingStatus(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
 *	Added by 			: a-8331 on 25-Oct-2019
 * 	Used for 	:
 *	Parameters	:	@param mailbagvo
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */

public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws RemoteException, SystemException {
	
	log.entering("MailTrackingMRAServicesEJB","findMailbagBillingStatus");
	return new MRADefaultsController().findMailbagBillingStatus(mailbagvo);
	
}

/*@Override
public Collection<DocumentBillingDetailsVO> findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException,RemoteException{	
	log.entering("MailTrackingMRAServicesEJB", "findMailbagBillingStatus");
	return new MRADefaultsController().findMailbagBillingStatus(mailbagvo);
	
	MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean("mRADefaultscontroller");
return	mRADefaultsController.findMailbagBillingStatus(mailbagvo);
	
}*/
/**
 * 	
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#validateCurrConversion(java.lang.String)
 *	Added by 			: A-8061 on 30-Oct-2019
 * 	Used for 	:	ICRD-346925
 *	Parameters	:	@param currCode
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public String validateCurrConversion(String currCode)throws  RemoteException, SystemException {
	
	return new MRADefaultsController().validateCurrConversion(currCode);
	
	
}

/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findSettledMailbags(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
 *	Added by 			: A-7531 on 26-Apr-2018
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public Collection<GPASettlementVO> findSettledMailbags(InvoiceSettlementFilterVO filterVO) throws SystemException,
RemoteException {
log.entering(CLASS_NAME, "findSettledMailbags");
return new GPABillingController().findSettledMailbags(filterVO);
}
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.findUnsettledMailbags
 *	Added by 	:	A-7531 on 03-May-2018
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<GPASettlementVO>
 */
public Collection<GPASettlementVO> findUnsettledMailbags(InvoiceSettlementFilterVO filterVO) throws SystemException,
RemoteException {
log.entering(CLASS_NAME, "findUnsettledMailbags");
return new GPABillingController().findUnsettledMailbags(filterVO);
}

/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.findMailbagSettlementHistory
 *	Added by 	:	A-7531 on 11-May-2018
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	Collection<GPASettlementVO>
 */
public Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(InvoiceSettlementFilterVO invoiceFiletrVO) throws SystemException,
RemoteException {
log.entering(CLASS_NAME, "findMailbagSettlementHistory");
return new GPABillingController().findMailbagSettlementHistory(invoiceFiletrVO);
}
/**
 * @author A-7871
 * @param gpaSettlementVOs
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 *
 */
 public void saveSettlementsAtMailbagLevel(
			Collection<GPASettlementVO> gpaSettlementVOs)throws SystemException,RemoteException, MailTrackingMRABusinessException{
	 log.entering(CLASS_NAME,"saveSettlementsAtMailbagLevel");
	 new GPABillingController().saveSettlementsAtMailbagLevel(gpaSettlementVOs);
	 log.exiting(CLASS_NAME,"saveSettlementsAtMailbagLevel");


}


 /**
  * @author A-8464
  * @param gpaSettlementVOs
  * @throws SystemException
  * @throws RemoteException
  * @throws MailTrackingMRABusinessException
  *
  */
  public Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO)throws SystemException,RemoteException{
 	 log.entering(CLASS_NAME,"listInvoicDetails");
 	 return new GPAReportingController().listInvoicDetails(invoicFilterVO);


 }

  /**
   * @author A-8464
   * @param invoicDetailsVO
   * @throws SystemException
   * @throws RemoteException
   * @throws MailTrackingMRABusinessException
   *
   */
   public void saveRemarkDetails(InvoicDetailsVO invoicDetailsVO)throws SystemException,RemoteException{
  	 log.entering(CLASS_NAME,"saveRemarkDetails");
  	 new GPAReportingController().saveRemarkDetails(invoicDetailsVO);
	 log.exiting(CLASS_NAME,"saveRemarkDetails");
	 }

   /**
    * @author A-8464
    */
   public void saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVOs)throws SystemException,RemoteException{
	  	 log.entering(CLASS_NAME,"saveClaimDetails");
	  	GPAReportingController gPAReportingController = (GPAReportingController)SpringAdapter.getInstance().getBean("gPAReportingController");
	  	gPAReportingController.saveClaimDetails(invoicDetailsVOs);
	  	//return ((MailTrackingMRABI)SpringAdapter.getInstance().getBean("mailMraFlowServices")).saveClaimDetails(reportSpec);
	  	// new GPAReportingController().saveClaimDetails(invoicDetailsVOs);
		 log.exiting(CLASS_NAME,"saveClaimDetails");
		 }

   /**
    * @author A-8464
    */
   public void updateProcessStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String processStatus)throws SystemException,RemoteException{
	  	 log.entering(CLASS_NAME,"updateProcessStatus");
	  	 new GPAReportingController().updateProcessStatus(invoicDetailsVOs, processStatus);
		 log.exiting(CLASS_NAME,"updateProcessStatus");
		 }

   /**
    * @author A-8464
    */
   public void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave)throws SystemException,RemoteException{
	  	 log.entering(CLASS_NAME,"saveGroupRemarkDetails");
	  	 new GPAReportingController().saveGroupRemarkDetails(invoicFilterVO, groupRemarksToSave);
		 log.exiting(CLASS_NAME,"saveGroupRemarkDetails");
		 }
 /**
    * @author A-8527
    * @param invoicFilterVO
    * @throws SystemException
    * @throws RemoteException
    * @throws MailTrackingMRABusinessException
    *
    */
    public Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO,int pageNumber)throws SystemException,RemoteException{
   	 log.entering(CLASS_NAME,"listInvoicDetails");
   	 return new GPAReportingController().listInvoic(invoicFilterVO,pageNumber);
   }
    /**
     * @author A-8527
     * @param Collection
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     *
     */
     public void updateInvoicReject(Collection <InvoicVO>rejectrecords)throws SystemException,RemoteException{
    	 log.entering(CLASS_NAME,"updateInvoicReject");
      new GPAReportingController().updateInvoicReject(rejectrecords);
    }
 /**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#importConsignmentDataToMra(com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO)
 *	Added by 			: A-4809 on Nov 20, 2018
 * 	Used for 	:
 *	Parameters	:	@param consignmentDocumentVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
	public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO)
	 throws RemoteException, SystemException{
		log.entering(CLASS_NAME, "importFlownMails");
		new MRADefaultsController().importConsignmentDataToMra(consignmentDocumentVO);
	}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#importConsignmentDataToMRA(com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO)
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param mailInConsignmentVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
	public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO)
			throws RemoteException, SystemException{
		new MRADefaultsController().importConsignmentDataToMRA(mailInConsignmentVO);
	}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#calculateUSPSIncentive(java.lang.String)
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
	public void calculateUSPSIncentive(USPSIncentiveVO uspsIncentiveVO) throws RemoteException, SystemException {
		new MRADefaultsController().calculateUSPSIncentive(uspsIncentiveVO);
	}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#importMailsFromCarditData(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO, java.lang.String)
 *	Added by 			: A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
	public void importMailsFromCarditData(DocumentBillingDetailsVO documentBillingDetailsVO, String txnlogInfo)
			throws SystemException, RemoteException {
				log.entering(CLASS_NAME, "importMailsFromCarditData");
			  new MRADefaultsController().importMailsFromCarditData(documentBillingDetailsVO,txnlogInfo);
		}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#saveMailInvoicDetails(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO)
	 *	Added by 			: A-7371 on Dec 13, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailInvoicMessage
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws ServiceNotAccessibleException
	 */
public void saveMailInvoicDetails(Collection<MailInvoicMessageVO> mailInvoicMessage)
		throws RemoteException, SystemException, ServiceNotAccessibleException{
	log.entering(CLASS_NAME, "saveMailInvoicDetails");
	GPAReportingController gPAReportingController = (GPAReportingController)SpringAdapter.getInstance().getBean("gPAReportingController");
	gPAReportingController.saveMailInvoicDetails(mailInvoicMessage);
}
  /**
	    * @author A-8464
	    */
	 /*  public Page<InvoicSummaryVO> findInvoicLov(InvoicFilterVO invoicFilterVO) throws SystemException,RemoteException{
		  	 log.entering(CLASS_NAME,"findInvoicLov");
		  	 return new GPAReportingController().findInvoicLov(invoicFilterVO);

			 }*/ // Commenting as part of ICRD-319850
   /**
    *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMailbagExistInMRA(com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO)
    *	Added by 			: A-4809 on Jan 2, 2019
    * 	Used for 	:
    *	Parameters	:	@param documentBillingDetailsVO
    *	Parameters	:	@return
    *	Parameters	:	@throws RemoteException
    *	Parameters	:	@throws SystemException
    */
   	public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO)
   			throws RemoteException, SystemException {
   		log.entering(CLASS_NAME, "findMailbagExistInMRA");
   		return new MRADefaultsController().findMailbagExistInMRA(documentBillingDetailsVO);
   	}



/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#updateMailStatus(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO)
 *	Added by 			: A-7929 on Jan 10, 2019
 * 	Used for 	:
 *	Parameters	:	@param invoicDetailsVO,RaiseClaimFlag
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 */
public void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String RaiseClaimFlag)
		throws RemoteException, SystemException {
	 log.entering(CLASS_NAME,"updateMailStatus");
		GPAReportingController gPAReportingController = (GPAReportingController)SpringAdapter.getInstance().getBean("gPAReportingController");
	  	gPAReportingController.updateMailStatus(invoicDetailsVOs,RaiseClaimFlag);
  	// new GPAReportingController().updateMailStatus(invoicDetailsVOs,RaiseClaimFlag);
	 log.exiting(CLASS_NAME,"updateMailStatus");
	 }

public void saveGpaRebillRemarks(
		Collection<RebillRemarksDetailVO> rebillRemarksDetailVOs)
		throws RemoteException, SystemException {
	new GPABillingController().saveGpaRebillRemarks(rebillRemarksDetailVOs);
}

public Page<ReminderDetailsVO> findReminderListForGpaBilling(ReminderDetailsFilterVO reminderDetailsFilterVO)
		throws RemoteException, SystemException {
	return new GPABillingController().findReminderListForGpaBilling(reminderDetailsFilterVO);
}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMailSubClass(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO)
 *	Added by 			: A-7929 on Jan 22, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode,code
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 * @return
 * @throws ServiceNotAccessibleException
 * @throws ProxyException
 */
public Collection<MailSubClassVO> findMailSubClass (String companyCode, String code) throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException {
	log.entering(CLASS_NAME,"findMailSubClass");
   return new GPAReportingController().findMailSubClass(companyCode,code);


}
/***
 * @author A-7794 as part of ICRD-232299
 * @param companyCode,startDate,endDate
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
@Override
public void forceImportScannedMailbags(String companyCode, String startDate,
		String endDate) throws SystemException, RemoteException {
	log.entering(CLASS_NAME,"forceImportScannedMailbags");
	FlownController flownController = (FlownController)SpringAdapter.getInstance().getBean("fLowncontroller");
	flownController.forceImportScannedMailbags(companyCode,startDate,endDate);
	log.exiting(CLASS_NAME,"forceImportScannedMailbags");

}

/**
 *
 */
public void generateGpaRebill(ReminderDetailsFilterVO reminderDetailsFilterVO)
	throws RemoteException,SystemException{
	new GPABillingController().generateGpaRebill(reminderDetailsFilterVO);
}



/**
     *
     *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#processSettlementUploadDetails(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
     *	Added by 			: A-7531 on 14-Jan-2019
     * 	Used for 	:
     *	Parameters	:	@param filterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException
     */
   	public String processSettlementUploadDetails(FileUploadFilterVO filterVO)throws RemoteException, SystemException {
   		log.entering(CLASS_NAME, "processSettlementUploadDetails");
   		return new GPABillingController().processMraSettlementFromExcel(filterVO);
   	}
   	/**
   	 *
   	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#validateFromFileUpload(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
   	 *	Added by 			: A-7531 on 14-Jan-2019
   	 * 	Used for 	:
   	 *	Parameters	:	@param fileUploadFilterVO
   	 *	Parameters	:	@return
   	 *	Parameters	:	@throws SystemException
   	 *	Parameters	:	@throws RemoteException
   	 */
   	public String validateFromFileUpload(FileUploadFilterVO fileUploadFilterVO)
   			throws SystemException, RemoteException{
   		log.entering(CLASS_NAME, "validateFromFileUpload");
   		return new GPABillingController().validateFromFileUpload(fileUploadFilterVO);
   	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#removeProcessedDataFromTempTable(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
	 *	Added by 			: A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public void removeProcessedDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)throws SystemException, RemoteException {
		new GPABillingController().removeDataFromTempTable(fileUploadFilterVO);
	}

/***
 * @author A-7794 as part of ICRD-232299
 * @param fileUploadFilterVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws PersistenceException
 */
@Override
public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO)
		throws SystemException, RemoteException, PersistenceException {
	log.entering("MailTrackingMRAServicesEJB", "processMailDataFromExcel");
	return new MRADefaultsController().processMailDataFromExcel(fileUploadFilterVO);
}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMailSubClass(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO)
 *	Added by 			: A-7929 on Jan 26, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 * @return
 * @throws ServiceNotAccessibleException
 * @throws ProxyException
 */
public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailIdr, long mailSeqNum)
		throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException {

	return new GPAReportingController().findMailbagHistories(companyCode,mailIdr,mailSeqNum);
}

/**
 *
 */
public Collection<RebillRemarksDetailVO> findGPARemarkDetails(ReminderDetailsFilterVO reminderListFilterVO)
	throws RemoteException,SystemException{
	return new GPABillingController().findGPARemarkDetails(reminderListFilterVO);
}


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#listGPABillingEntries(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
	 *	Added by 			: A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "listGPABillingEntries");
		return new MRADefaultsController().listGPABillingEntries(gpaBillingEntriesFilterVO);
	}


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#listConsignmentDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO)
	 *	Added by 			: A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntriesFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws SystemException, RemoteException {
		log.entering(CLASS_NAME, "listConsignmentDetails");
		return new MRADefaultsController().listConsignmentDetails(gpaBillingEntriesFilterVO);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findCRAParameterDetails(java.lang.String,java.lang.String)
	 *	Added by 			: A-4809 on Jan 29, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String craParInvgrp)
	throws SystemException,RemoteException{
		log.entering(CLASS_NAME, "findCRAParameterDetails");
		return new MRADefaultsController().findCRAParameterDetails(companyCode, craParInvgrp);
	}

/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findReasonCodes(java.lang.String, java.util.Collection)
 *	Added by 			: A-7531 on 05-Feb-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param systemParCodes
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 * @throws ProxyException
 */
@Override
public Collection<CRAParameterVO> findReasonCodes(String companyCode, String systemParCodes)
		throws RemoteException, SystemException, ProxyException {
	log.entering("MailTrackingMRAServicesEJB", "findReasonCodes");
	return new MRADefaultsController().findReasonCodes(companyCode,systemParCodes);
}


@Override
public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO)
		throws SystemException, RemoteException {
	log.entering("MailTrackingMRAServicesEJB", "importResditDataToMRA");
	 new MRADefaultsController().importResditDataToMRA(mailScanDetailVO);
}
/**
**
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
		GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException, RemoteException{

		 log.entering(CLASS_NAME, "sendEmailforPAs");
		 new GPABillingTKController().sendEmailforTK(postalAdministrationVOs, generateInvoiceFilterVO);
		 log.exiting(CLASS_NAME, "sendEmailforPAs");
	}

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
		GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException, RemoteException{

		 log.entering(CLASS_NAME, "sendEmailforPAs");
		 new GPABillingAAController().sendEmailforAA(postalAdministrationVOs, generateInvoiceFilterVO);
		 log.exiting(CLASS_NAME, "sendEmailforPAs");
	}
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
		Collection<CN66DetailsVO> cn66DetailsVos, Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs) throws SystemException, RemoteException{

		 log.entering(CLASS_NAME, "sendEmailforPAs");
		 new GPABillingController().sendEmailRebillInvoice(cn51DetailsVoS,cn51cn66FilterVO,cn66DetailsVos,invoiceDetailsReportVOs);
		 log.exiting(CLASS_NAME, "sendEmailforPAs");
	}
/**
 * 	Method		:	MailTrackingMRAProxy.sendEmailRebillInvoice
 *	Added by 	:	A-5526 on Mar 04, 2019
 * 	Used for 	:ICRD-234283-Rebill invoice email sending from CRA209-GPA rebill flow
 *	Parameters	:	@param reminderDetailsFilterVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void sendEmailRebillInvoice(ReminderDetailsFilterVO reminderDetailsFilterVO) throws SystemException, RemoteException{

	 log.entering(CLASS_NAME, "sendEmailforPAs");
	 new GPABillingController().sendEmailRebillInvoice(reminderDetailsFilterVO);
	 log.exiting(CLASS_NAME, "sendEmailforPAs");
}
/**
 * @author A-8527
 * @param invoicFilterVO
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 *
 */
 public Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)throws SystemException,RemoteException{
	 log.entering(CLASS_NAME,"listClaimDetails");
	 return new GPAReportingController().listClaimDetails(invoicFilterVO,pageNumber);
}
 /**
  * @author A-8527
  * @param invoicFilterVO
  * @throws SystemException
  * @throws RemoteException
  * @throws MailTrackingMRABusinessException
  *
  */
  public Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)throws SystemException,RemoteException{
 	 log.entering(CLASS_NAME,"listGenerateClaimDetails");
 	 return new GPAReportingController().listGenerateClaimDetails(invoicFilterVO,pageNumber);
 }
  /**
   * @author A-8527
   * @param invoicFilterVO
   * @throws SystemException
   * @throws RemoteException
 * @throws ServiceNotAccessibleException
   * @throws MailTrackingMRABusinessException
   *
   */
  public Collection <USPSPostalCalendarVO> validateFrmToDateRange(InvoicFilterVO invoicFilterVO)throws SystemException,RemoteException, ServiceNotAccessibleException{
	  log.entering(CLASS_NAME,"generateandClaimDetails");
	 	 return new GPAReportingController().validateFrmToDateRange(invoicFilterVO);
  }
  /**
   * @author A-8527
   * @param invoicFilterVO
   * @throws SystemException
   * @throws RemoteException
 * @throws ProxyException
   * @throws MailTrackingMRABusinessException
   *
   */
  public  Map <String,String> validateuspsPacode(Collection<String> systemParameters)throws SystemException,RemoteException, ProxyException{
	  log.entering(CLASS_NAME,"validateuspsPacode");
	 	 return new GPAReportingController().validateuspsPacode(systemParameters);
}
  /**
   *
   */
  public void processInvoic(InvoicVO invoicVO) throws SystemException,RemoteException{
	  new GPAReportingController().processInvoic(invoicVO);
  }
  /**
   *
   */
  public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws SystemException,RemoteException{
	  return new GPAReportingController().reprocessInvoicMails(invoicDetailsVOs);
  }
  /**
   *
   * @param companyCode
   * @return
   * @throws SystemException
   * @throws RemoteException
   */
  public int checkForProcessCount(String companyCode,InvoicVO invoicVO) throws SystemException,RemoteException{
	  return new GPAReportingController().checkForProcessCount(companyCode,invoicVO);
  }
  //Added as part of ICRD-329873
  public String getmcastatus(CCAdetailsVO ccaDetailsVO) throws SystemException,RemoteException, BusinessDelegateException{
	  return new MRADefaultsController().getmcastatus(ccaDetailsVO);
  }

  /**
   *
   */
  public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum)
		  	throws SystemException,RemoteException{
	  return new GPAReportingController().findInvoicAndClaimDetails(companyCode, mailSeqnum);
  }

/**
 * 	Method		:	MailTrackingMRAServicesEJB.generateClaimAndResdits
 *	Added by 	:	A-4809 on May 16, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@param txnlogInfo
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	void
 */
	public void generateClaimAndResdits(InvoicFilterVO filterVO, String txnlogInfo)
			throws SystemException, RemoteException {
				log.entering(CLASS_NAME, "generateClaimAndResdits");
			  new GPAReportingController().generateClaimAndResdits(filterVO,txnlogInfo);
		}
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.generateClaimAndResditsFromJob
 *	Added by 	:	A-4809 on May 31, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
	public void generateClaimAndResditsFromJob(String companyCode) throws RemoteException, SystemException {
		new GPAReportingController().generateClaimAndResditsFromJob(companyCode);
	}
	 /** @author A-7540
	  *  @param mailbagID
	  *  @throws SystemException
	  *  @throws RemoteException
	  */
	  public Collection<ResditReceiptVO> getResditInfofromUSPS(String mailbagID)
				 throws SystemException,WebServiceException,RemoteException, ProxyException, ServiceNotAccessibleException{
				return new GPAReportingController().getResditInfofromUSPS(mailbagID);
	  }

public void generateClaimMessageText(String companyCode) throws SystemException,RemoteException{

	 new GPAReportingController().generateClaimMessageText(companyCode);
  }
/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.isClaimGeneraetd
 *	Added by 	:	A-8061 on 20-Jun-2019
 * 	Used for 	:	ICRD-262451
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Boolean
 */
public Boolean isClaimGenerated(InvoicFilterVO invoicFilterVO)throws RemoteException, SystemException{

	return  new GPAReportingController().isClaimGenerated(invoicFilterVO);
}
/**
 * 	Method		:	MailTrackingMRAServicesEJB.calculateUSPSIncentiveAmount
 *	Added by 	:	A-5526 on Jan 23, 2020
 * 	Used for 	:IASCB-28259-calculateUSPSIncentiveAmount
 *	Parameters	:	@param USPSPostalCalendarVO
 *	Return type	: 	void
 * @throws SystemException
 * @throws ProxyException
 */
public void calculateUSPSIncentiveAmount(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO)
		throws SystemException, RemoteException {
	 new MRADefaultsController().calculateUSPSIncentiveAmount(uspsPostalCalendarVO,uspsIncentiveVO);
}

public void generateSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO) throws RemoteException, SystemException{
	new MRADefaultsAAController().generateSAPInterfaceFile(interfaceFilterVO);
}

public void sendSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO,SAPInterfaceFileLogVO sapInterfaceFileLogVO)
		throws RemoteException, SystemException, ProxyException {
	new MRADefaultsAAController().sendSAPInterfaceFile(interfaceFilterVO,sapInterfaceFileLogVO);
}

/**
 * @author A-2408
 * @param invoiceLovVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public InvoiceLovVO findInvoiceNumber(InvoiceLovVO invoiceLovVO)
		throws SystemException, RemoteException {
	return new GPABillingController().findInvoiceNumber(invoiceLovVO);
}

/**
 *
 * 	Method		:	MailTrackingMRAServicesEJB.isInitiatedInvoic
 *	Added by 	:	A-5219 on 20-Apr-2020
 * 	Used for 	:
 *	Parameters	:	@param invoicVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Return type	: 	boolean
 */
public boolean isInitiatedInvoic(InvoicVO invoicVO)
		throws SystemException, RemoteException {
	return new GPAReportingController().isInitiatedInvoic(invoicVO);
}

/**
 * @author A-5526
 * @param companyCode
 * @return
 * @throws RemoteException
 * @throws SystemException
 * @throws ObjectAlreadyLockedException
 */

public Collection<LockVO> generateINVOICProcessingLock(String companyCode)
	    throws RemoteException, SystemException, ObjectAlreadyLockedException
	  {
	    this.log.entering("MailTrackingMRAServicesEJB", "addLocks");
	    return new GPAReportingController().addLocks(companyCode);
	  }


/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#isMailbagInMRA(java.lang.String, long)
 *	Added by 			: A-5219 on 07-May-2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param mailSeq
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
public boolean isMailbagInMRA(String companyCode, long mailSeq)
		throws SystemException, RemoteException {
	return new MRADefaultsController().isMailbagInMRA(companyCode,mailSeq);
}

/**
 * 	Method		:	MailTrackingMRADelegate.findApprovedForceMajeureDetails
 *	Added by 	:	A-5526 on Jun 15, 2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode,mailIdr,mailSeqNum
 *	Parameters	:	@return
 *	Parameters	:	@throws ServiceNotAccessibleException,RemoteException,SystemException
 *	Return type	:
 * @throws ServiceNotAccessibleException,RemoteException,SystemException
 */
public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailIdr, long mailSeqNum)
		throws RemoteException, SystemException, ProxyException, ServiceNotAccessibleException {

	return new GPAReportingController().findApprovedForceMajeureDetails(companyCode,mailIdr,mailSeqNum);
}
/**
*
*	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#recalculateDisincentiveData()
*	Added by 			: A-8176 on 11-Jun-2020
* 	Used for 	:	ICRD-245594
*	Parameters	:	@throws SystemException
*	Parameters	:	@throws RemoteException
*/
@Override
public void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVos) throws SystemException, RemoteException {
	new MRADefaultsController().recalculateDisincentiveData(rateAuditVos);
}
/**
 * 
 * 	Method		:	MailTrackingMRAServicesEJB.updateRouteAndReprorate
 *	Added by 	:	A-8061 on 15-Dec-2020
 * 	Used for 	:
 *	Parameters	:	@param gPABillingEntriesFilterVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 *	Return type	: 	void
 */
public void updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO) throws SystemException, RemoteException {
	new MRADefaultsController().updateRouteAndReprorate(gPABillingEntriesFilterVO);
}

/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#asyncProcessSettlementUpload(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
 *	Added by 			: A-5219 on 08-Jan-2021
 * 	Used for 	:
 *	Parameters	:	@param fileUploadFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws ProxyException
 *	Parameters	:	@throws SystemException
 */
 public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO,int pageNumber) throws SystemException, RemoteException {
	this.log.entering("MailTrackingMRAServicesEJB", "findBillingType");
	ArrayList<BillingScheduleDetailsVO> billingScheduleDetailsVO=null;
	return new MRADefaultsController().findBillingType(billingScheduleFilterVO,pageNumber);
}
public void saveBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException, RemoteException, FinderException {
	  log.entering("MailTrackingMRAServicesEJB", "saveBillingSchedulemaster");
	  new MRADefaultsController().saveBillingSchedulemaster(billingScheduleDetailsVO);
}
public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException, RemoteException, FinderException {
	  log.entering("MailTrackingMRAServicesEJB", "validateBillingSchedulemaster");
	 return new MRADefaultsController().validateBillingSchedulemaster(billingScheduleDetailsVO);
}

@Override
public void asyncProcessSettlementUpload(FileUploadFilterVO fileUploadFilterVO)
		throws RemoteException, ProxyException, SystemException {
	new GPABillingController().asyncProcessSettlementUpload(fileUploadFilterVO);

}


/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#addLockingForSettlementUpload(java.lang.String)
 *	Added by 			: A-5219 on 11-Jan-2021
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws ObjectAlreadyLockedException
 *	Parameters	:	@throws RemoteException
 */
public Collection<LockVO> addLockingForSettlementUpload(String companyCode)
		throws SystemException,ObjectAlreadyLockedException,RemoteException{
	return new GPABillingController().addLockingForSettlementUpload(companyCode);
}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#reImportPABuiltMailbagsToMRA(java.util.Collection)
	 *	Added by 			: A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO)
			throws SystemException, RemoteException {
		new MRADefaultsController().reImportPABuiltMailbagsToMRA(mailbagVO);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMailbagsForPABuiltUpdate(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 			: A-6245 on 12-Mar-2021
	 * 	Used for 	:	IASCB-96008
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)
			throws SystemException, RemoteException {
		return new MRADefaultsController().findMailbagsForPABuiltUpdate(mailbagVO);
	}
	public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws SystemException, RemoteException{
		  return new GPAReportingController().checkForRejectionMailbags(companyCode,invoicVO);
	  }	 
/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#listPaymentBatchDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO)
	 *	Added by 			: A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws PersistenceException 
	 */
	public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, RemoteException, PersistenceException{
		return new ReceivableManagementController().listPaymentBatchDetails(paymentBatchFilterVO);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#uploadSettlementAsyc(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRASettlementBatchHeaderVO, com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchSplitFilterVO, com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO)
	 *	Added by 			: A-5219 on 10-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param settlementBatchHeaderVO
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	public void uploadPaymentBatchDetail(PaymentBatchFilterVO batchFilterVO, InvoiceTransactionLogVO invoiceTransactionLogVO)
			throws SystemException, RemoteException{
		new ReceivableManagementController().uploadPaymentBatchDetail(batchFilterVO, invoiceTransactionLogVO);
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findSettlementBatches(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO)
	 *	Added by 			: A-3429 on 18-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return PaymentBatchDetailsVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws PersistenceException 
	 */
	public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, RemoteException, PersistenceException{
		return new ReceivableManagementController().findSettlementBatches(paymentBatchFilterVO);
	}
	
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
			throws SystemException, RemoteException, PersistenceException{
		return new ReceivableManagementController().findSettlementBatchDetails(mailSettlementBatchFilterVO);
	}

/**
 * 	Method		:	MailTrackingMRAServicesEJB.generatePASSFile
 *	Added by 	:	A-4809 on 12-Mar-2021
 * 	Used for 	:
 *	Parameters	:	@param passFilterVO
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void generatePASSFile(GeneratePASSFilterVO passFilterVO)
		throws RemoteException, SystemException {
	new GPABillingController().generatePASSFile(passFilterVO);
}

	public Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO)
	throws RemoteException, SystemException {
		return new GPABillingController().findPASSFileNames(fileNameLovVO);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generatePASSFileJobScheduler(java.lang.String)
	 *	Added by 			: A-8061 on 10-Jun-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 */
	public void generatePASSFileJobScheduler(String companyCode) throws SystemException, ProxyException, RemoteException{
		new GPABillingController().generatePASSFileJobScheduler(companyCode);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#generateInvoiceJobScheduler(java.lang.String)
	 *	Added by 			: A-8061 on 10-Jun-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Parameters	:	@throws RemoteException
	 */
	public void generateInvoiceJobScheduler(String companyCode)throws SystemException, ProxyException, MailTrackingMRABusinessException, RemoteException{
		new GPABillingController().generateInvoiceJobScheduler(companyCode);
		
	}
	
	
	/***
	 * @author A-8331
	 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 */
	 public void saveAdvancePaymentDetails(
			 PaymentBatchDetailsVO advancePaymentVO)throws SystemException,RemoteException, MailTrackingMRABusinessException, FinderException, PersistenceException{
		 log.entering(CLASS_NAME,"saveAdvancePaymentDetails");
			
		 ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean("receivableManagementController");
		 receivableManagementController.saveAdvancePaymentDetails(advancePaymentVO);
		 log.exiting(CLASS_NAME,"saveAdvancePaymentDetails");


}
 /***
		 * @author A-8331
		 	Used for 	:
		 *	Parameters	:	@param paymentBatchFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException 
		 */ 
	 
	
	 public void deletePaymentBatch(PaymentBatchDetailsVO advancePaymentVO)throws
		RemoteException, SystemException, CreateException, FinderException {
			log.entering("MailTrackingMRAServicesEJB", "changeStatus");
			ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean("receivableManagementController");
			 receivableManagementController.deletePaymentBatch(advancePaymentVO);
		}	 
	 public int findGPASettlementBatchUploadFileCount(PaymentBatchFilterVO paymentBatchFilterVO)
			 throws SystemException,RemoteException {
		 return new ReceivableManagementController().validateExcelFileName(paymentBatchFilterVO);
	 }
/**
 * 	Method		:	MailTrackingMRAServicesEJB.deletePaymentBatchAttachment
 *	Added by 	:	A-4809 on 02-Dec-2021
 * 	Used for 	:
 *	Parameters	:	@param paymentBatchDetailsVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	void
 */
	public void deletePaymentBatchAttachment(PaymentBatchDetailsVO paymentBatchDetailsVO)
	throws SystemException, RemoteException, PersistenceException{
		log.entering(CLASS_NAME,"deletePaymentBatchAttachment");
		new ReceivableManagementController().deletePaymentBatchAttachment(paymentBatchDetailsVO);
		log.exiting(CLASS_NAME,"deletePaymentBatchAttachment");
	}	
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
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateInvoiceReportSQ");
		return new GPABillingController().generateInvoiceReportSQ(reportSpec);

	}		
	/**
	 * Method		:	MailTrackingMRAEJB.generateGPAInvoiceReportSQ
	 * @author A-10383
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @throws ProxyException
	 */
	 public Map<String, Object> generateGPAInvoiceReportSQ(ReportSpec reportSpec)
			throws MailTrackingMRABusinessException, SystemException,RemoteException{
				return new GPABillingController().generateGPAInvoiceReportSQ(reportSpec);
			}  
/**
 * @author A-10383
 * Method		:	MailTrackingMRAEJB.generateCN51ReportSQ
 * @author A-10383
 * @throws RemoteException
* @throws SystemException
* @throws MailTrackingMRABusinessException
 */
	@Override
	public Map<String, Object> generateCN51ReportSQ(ReportSpec reportSpec)
			throws RemoteException, SystemException, MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "generateCN51ReportSQ");
		return new GPABillingController().generateCN51ReportSQ(reportSpec);
	}
	
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#mraBatchSettlementWorkerJob(java.lang.String)
	 *	Added by 			: A-5219 on 08-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 */
	public void mraBatchSettlementWorkerJob(String companyCode)
		    throws SystemException {
		new ReceivableManagementController().mraBatchSettlementJob(companyCode);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#updateBatchAmount(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO)
	 *	Added by 			: A-4809 on 11-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param batchVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws PersistenceException
	 */
	 public void updateBatchAmount(PaymentBatchDetailsVO batchVO)
			 throws SystemException, RemoteException, PersistenceException{
		 log.entering(CLASS_NAME,"updateBatchAmount");
		 ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean("receivableManagementController");
		 receivableManagementController.updateBatchAmount(batchVO);
		 log.exiting(CLASS_NAME,"updateBatchAmount");
	 }
	 /**
	  * 
	  * 	Method		:	MailTrackingMRAServicesEJB.clearBatchDetail
	  *	Added by 	:	A-10647 on 27-Jan-2022
	  * 	Used for 	:
	  *	Parameters	:	@param batchVO
	  *	Parameters	:	@throws SystemException
	  *	Parameters	:	@throws RemoteException
	  *	Parameters	:	@throws PersistenceException 
	  *	Return type	: 	void
	  */
	 public void clearBatchDetails(PaymentBatchDetailsVO batchVO)
			 throws SystemException, RemoteException, PersistenceException{
		 log.entering(CLASS_NAME,"clearBatchDetail");
		 new ReceivableManagementController().clearBatchDetails(batchVO);
		 log.exiting(CLASS_NAME,"clearBatchDetail");
	 }
	 
	 public void processInvoicFileFromJob(InvoicVO invoicVO) throws SystemException,RemoteException{
		 log.entering(CLASS_NAME,"processInvoicFileFromJob");
		  new GPAReportingController().processInvoicFileFromJob(invoicVO);
		  log.exiting(CLASS_NAME,"processInvoicFileFromJob");
	  }

	@Override
	public void updateInvoicProcessingStatusFromJob(String companyCode) throws SystemException, RemoteException {
		 log.entering(CLASS_NAME,"updateInvoicProcessingStatusFromJob");
		  new GPAReportingController().updateInvoicProcessingStatusFromJob(companyCode);
		  log.exiting(CLASS_NAME,"updateInvoicProcessingStatusFromJob");	
	}
	
	/**
	 * Method		:	MailTrackingMRAEJB.generateCoverPageSQ
	 * @author A-10383
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 * @throws PersistenceException 
	 * @throws ProxyException
	 */
	 public Map<String, Object> generateCoverPageSQ(ReportSpec reportSpec)
			throws MailTrackingMRABusinessException, SystemException,RemoteException, PersistenceException{
				return new GPABillingController().generateCoverPageSQ(reportSpec);
			}  
	 
	 /**
		 * Method		:	MailTrackingMRAEJB.generateGPAInvoiceReportPrintAll
		 * @author A-10383
		 * @throws RemoteException
		 * @throws SystemException
		 * @throws MailTrackingMRABusinessException
	 * @throws PersistenceException 
		 * @throws ProxyException
		 */
		 public Map<String, Object> generateGPAInvoiceReportPrintAllSQ(ReportSpec reportSpec)
				throws MailTrackingMRABusinessException, SystemException,RemoteException, PersistenceException{
					return new GPABillingController().generateGPAInvoiceReportPrintAllSQ(reportSpec);
				}  
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#importMRAProvisionalRateData(java.util.Collection)
 *	Added by 			: A-10647 on 23-Nov-2022
 * 	Used for 	:
 *	Parameters	:	@param rateAuditVOs
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException 
 * @throws PersistenceException 
 */
public void importMailProvisionalRateData(Collection<RateAuditVO> rateAuditVOs) throws SystemException, RemoteException, PersistenceException {
	 this.log.entering(CLASS_NAME, "importMRAProvsionalRateData");
	    MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean(MRADEFAULTCONTROLLER_BEAN);
		    mRADefaultsController.importMailProvisionalRateData(rateAuditVOs);
				}  

/**
 * 	Method		:	MailTrackingMRAEJB.changeEnddate
 *	Added by 	:	204569 on 12-Dec-2022
 * 	Used for 	:
 *	Parameters	:	@param BillingLineVO
 **	Parameters	:	@param changeEndDate
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
@Override
public void changeEnddate(Collection<BillingLineVO> billingLineVos, String date)
		throws RemoteException, SystemException {
	 log.exiting(CLASS_NAME,"changeEnddate");
			MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean(MRADEFAULTCONTROLLER_BEAN);
				mRADefaultsController.changeEnddate(billingLineVos, date);	
}

public void calculateProvisionalRate(Long noOfRecords) throws SystemException,
RemoteException,PersistenceException {
	 this.log.entering(CLASS_NAME, "CalculateUpfrontRateWorker");
	    MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean(MRADEFAULTCONTROLLER_BEAN);
		    mRADefaultsController.calculateProvisionalRate(noOfRecords);
}

/**
 * 	Method		:	MailTrackingMRAEJB.changeBillingMatrixStatusUpdate
 *	Added by 	:	204569 on 22-Dec-2022
 * 	Used for 	:
 *	Parameters	:	@param BillingLineVO
 **	Parameters	:	@param changeStatus
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 * @throws ProxyException 
 */
public void changeBillingMatrixStatusUpdate(BillingMatrixFilterVO billingLineVos, String status)
		throws RemoteException, SystemException, ProxyException {
	 log.exiting(CLASS_NAME,"changeBillingMatrixStatusUpdate");	
	 MRADefaultsController mRADefaultsController = (MRADefaultsController)SpringAdapter.getInstance().getBean(MRADEFAULTCONTROLLER_BEAN);
	 mRADefaultsController.changeBillingMatrixStatusUpdate(billingLineVos, status);
	
}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI#findMRAGLAccountingEntries(com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO)
 *	Added by 			: A-10164 on 15-Feb-2023
 * 	Used for 	: MRA GL interface
 *	Parameters	:	@param glInterfaceFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 */
	public List<GLInterfaceDetailVO> findMRAGLAccountingEntries(GLInterfaceFilterVO glInterfaceFilterVO)
			throws SystemException {
		LOGGER.exiting(CLASS_NAME, "findMRAGLAccountingEntries");
		return new MRADefaultsController().findMRAGLAccountingEntries(glInterfaceFilterVO);
}
	
	public Collection<LockVO> autoMCALock(String companyCode)throws RemoteException,
	SystemException,ObjectAlreadyLockedException{
		log.entering(CLASS_NAME, "autoMCALock "+companyCode);
		return new MRADefaultsController().addAutoMraMcaLocks();
	}
}
