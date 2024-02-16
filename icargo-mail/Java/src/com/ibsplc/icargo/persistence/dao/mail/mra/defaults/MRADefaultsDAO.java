/*
 * MRADefaultsDAO.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAFlightFinaliseVO;
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
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicMessageVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
;

/**
 * The Interface MRADefaultsDAO.
 *
 * @author A-1556
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Jan 8, 2007 Philip Initial draft 0.2 Jan 28,2007 Kiran Added the method
 * findAllRateCards FEB 28, 2007 Prem Added the method findAllBillingMatrix
 */

public interface MRADefaultsDAO {

	/**
	 * Finds the rate card and assosciated rate lines.
	 *
	 * @param companyCode the company code
	 * @param rateCardID the rate card id
	 * @return RateCardVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public RateCardVO findRateCardDetails(String companyCode, String rateCardID)
	throws SystemException, PersistenceException;

	/**
	 * Returns the ratelines based on the filter criteria.
	 *
	 * @param rateLineFilterVO the rate line filter vo
	 * @return Page<RateLineVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<RateLineVO> findRateLineDetails(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Finds the rate cards based on the filter.
	 *
	 * @param rateCardFilterVO the rate card filter vo
	 * @return Page<RateCardVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-2049
	 */
	public Page<RateCardVO> findAllRateCards(RateCardFilterVO rateCardFilterVO)
	throws SystemException, PersistenceException;

	/**
	 * Find over lapping rate lines.
	 *
	 * @param rateLineVOs the rate line v os
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<RateLineErrorVO> findOverLappingRateLines(
			Collection<RateLineVO> rateLineVOs) throws SystemException,
			PersistenceException;

	/**
	 * Find all rate lines.
	 *
	 * @param rateLineFilterVO the rate line filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<RateLineVO> findAllRateLines(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find rate card lov.
	 *
	 * @param companyCode the company code
	 * @param rateCardID the rate card id
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<RateCardLovVO> findRateCardLov(String companyCode,
			String rateCardID, int pageNumber) throws SystemException,
			PersistenceException;

	/**
	 * Find all billing matrix.
	 *
	 * @param billingMatrixFilterVO the billing matrix filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2280
	 */
	public Page<BillingMatrixVO> findAllBillingMatrix(
			BillingMatrixFilterVO billingMatrixFilterVO)
			throws SystemException, PersistenceException;

	/**
	 * Find billing matrix details.
	 *
	 * @param blgMtxFilterVO the blg mtx filter vo
	 * @return the billing matrix vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2398
	 */
	public BillingMatrixVO findBillingMatrixDetails(
			BillingMatrixFilterVO blgMtxFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find billing line details.
	 *
	 * @param blgLineFilterVO the blg line filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2398
	 */
	public Page<BillingLineVO> findBillingLineDetails(
			BillingLineFilterVO blgLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find billing line values.
	 *
	 * @param blgLineFilterVO the blg line filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<BillingLineVO> findBillingLineValues(
			BillingLineFilterVO blgLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Method for finding all the billing lines with the parameters.
	 *
	 * @param billingLineVO the billing line vo
	 * @param billingLineStatus the billing line status
	 * @return Collection<BillingLineVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-1872 Mar 5, 2007
	 */
	Collection<BillingLineVO> findOverlappingBillingLines(
			BillingLineVO billingLineVO, String billingLineStatus)
			throws SystemException, PersistenceException;

	/**
	 * Method for finding the billing lines with the status TODO Purpose *.
	 *
	 * @param billingLineFilterVO * *
	 * @return Collection<BillingLineVO> * *
	 * @throws SystemException * *
	 * @throws PersistenceException the persistence exception
	 * @author A-1872 * Mar 6, 2007 * *
	 */
	Collection<BillingLineVO> findBillingLines(
			BillingLineFilterVO billingLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find billing matrix lov.
	 *
	 * @param companyCode the company code
	 * @param billingMatrixCode the billing matrix code
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<BillingMatrixLovVO> findBillingMatrixLov(String companyCode,
			String billingMatrixCode, int pageNumber) throws SystemException,
			PersistenceException;

	/**
	 * This method displays Mail Proration Details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-2518
	 */
	public Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * lists the Proration factors.
	 *
	 * @param prorationFactorFilterVo the proration factor filter vo
	 * @return Collection<ProrationFactorVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-2518
	 */
	public Collection<ProrationFactorVO> findProrationFactors(
			ProrationFactorFilterVO prorationFactorFilterVo)
			throws SystemException, PersistenceException;

	/**
	 * Find mail sla.
	 *
	 * @param companyCode the company code
	 * @param slaId the sla id
	 * @return the mail slavo
	 * @throws SystemException the system exception
	 * @author a-2524
	 */
	public MailSLAVO findMailSla(String companyCode, String slaId)
	throws SystemException;

	/**
	 * Finds mail contract details.
	 *
	 * @param companyCode the company code
	 * @param contractReferenceNumber the contract reference number
	 * @param versionNumber the version number
	 * @return MailContractVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public MailContractVO viewMailContract(String companyCode,
			String contractReferenceNumber, String versionNumber)
	throws SystemException, PersistenceException;

	/**
	 * Finds maximum version number.
	 *
	 * @param companyCode the company code
	 * @param contractReferenceNumber the contract reference number
	 * @return the int
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public int findMaximumVersionNumber(String companyCode,
			String contractReferenceNumber) throws SystemException,
			PersistenceException;

	/**
	 * Displays Version numbers for Version LOV.
	 *
	 * @param companyCode the company code
	 * @param contractReferenceNumber the contract reference number
	 * @param versionNumber the version number
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public Collection<MailContractVersionLOVVO> displayVersionLov(
			String companyCode, String contractReferenceNumber,
			String versionNumber) throws SystemException, PersistenceException;

	/**
	 * Added by A-2521 for ContractRefNo Lov.
	 *
	 * @param contractFilterVO the contract filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<ContractDetailsVO> displayContractDetails(
			ContractFilterVO contractFilterVO) throws SystemException;

	/**
	 * Added by A-2521 for SLAId Lov.
	 *
	 * @param slaFilterVO the sla filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<SLADetailsVO> displaySLADetails(SLAFilterVO slaFilterVO)
	throws SystemException;

	/**
	 * Finds mail contract details for duplicate contract check.
	 *
	 * @param companyCode the company code
	 * @param gpaCode the gpa code
	 * @param airlineCode the airline code
	 * @return Collection<MailContractVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public Collection<MailContractVO> viewDuplicateMailContract(
			String companyCode, String gpaCode, String airlineCode)
			throws SystemException, PersistenceException;

	/**
	 * Added by A-1946.
	 *
	 * @param mailContractFilterVO the mail contract filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 */
	public Collection<MailContractVO> findMailContracts(
			MailContractFilterVO mailContractFilterVO) throws SystemException;

	/**
	 * Find mail sla max serial number.
	 *
	 * @param companyCode the company code
	 * @param slaId the sla id
	 * @return the int
	 * @throws SystemException the system exception
	 * @author A-2524
	 */

	public int findMailSLAMaxSerialNumber(String companyCode, String slaId)
	throws SystemException;

	/**
	 * This method validates billing matrix codes.
	 *
	 * @param companyCode the company code
	 * @param billingMatrixCodes the billing matrix codes
	 * @return Collection<String>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public Collection<String> validateBillingMatrixCodes(String companyCode,
			Collection<String> billingMatrixCodes) throws SystemException,
			PersistenceException;

	/**
	 * This method validates Service Level Activity(SLA) codes.
	 *
	 * @param companyCode the company code
	 * @param slaCodes the sla codes
	 * @return Collection<String>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public Collection<String> validateSLACodes(String companyCode,
			Collection<String> slaCodes) throws SystemException,
			PersistenceException;

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
	 * @throws PersistenceException the persistence exception
	 */
	public Page<DespatchLovVO> findDespatchLov(String companyCode, String dsn,
			String despatch, String gpaCode, int pageNumber)
			throws SystemException, PersistenceException;

	/**
	 * Validate reporting period.
	 *
	 * @param reportingPeriodFilterVo the reporting period filter vo
	 * @return the collection
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 * @author a-2518
	 */
	public Collection<ReportingPeriodVO> validateReportingPeriod(
			ReportingPeriodFilterVO reportingPeriodFilterVo)
			throws PersistenceException, SystemException;

	/**
	 * Import flown mails.
	 *
	 * @param flightValidationVO the flight validation vo
	 * @param flownMailSegmentVOs the flown mail segment v os
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 * @author A-2270
	 */
	public String importFlownMails(FlightValidationVO flightValidationVO,
			Collection<FlownMailSegmentVO> flownMailSegmentVOs,DocumentBillingDetailsVO documentBillingVO)
	throws PersistenceException, SystemException;

	/**
	 * Find invoic enquiry details.
	 *
	 * @param invoiceEnquiryFilterVo the invoice enquiry filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-2270
	 */
	public Page<MailInvoicEnquiryDetailsVO> findInvoicEnquiryDetails(
			InvoicEnquiryFilterVO invoiceEnquiryFilterVo)
			throws SystemException, PersistenceException;

	/**
	 * Find invoic claims enquiry details.
	 *
	 * @param filterVO the filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<MailInvoicClaimsEnquiryVO> findInvoicClaimsEnquiryDetails(
			MailInvoicClaimsFilterVO filterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find dot rate details.
	 *
	 * @param filterVO the filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<MailDOTRateVO> findDOTRateDetails(
			MailDOTRateFilterVO filterVO) throws SystemException,
			PersistenceException;

	/**
	 * Import to reconcile.
	 *
	 * @param companyCode the company code
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public String importToReconcile(String companyCode) throws SystemException,
	PersistenceException;

	/**
	 * Reconcile process.
	 *
	 * @param companyCode the company code
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public String reconcileProcess(String companyCode) throws SystemException,
	PersistenceException;

	/**
	 * This method generates INVOIC Claim file.
	 *
	 * @param companyCode the company code
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2518
	 */
	public Collection<InvoicMessageVO> generateInvoicClaimFile(
			String companyCode) throws SystemException, PersistenceException;

	/**
	 * Find invoic key lov.
	 *
	 * @param companyCode the company code
	 * @param invoicKey the invoic key
	 * @param poaCode the poa code
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<InvoicKeyLovVO> findInvoicKeyLov(String companyCode,
			String invoicKey, String poaCode, int pageNumber)
			throws SystemException, PersistenceException;
	
	/**
	 * Find duplicate recticles.
	 *
	 * @param companyCode the company code
	 * @param sectororigin the sectororigin
	 * @param sectorDest the sector dest
	 * @param payType the pay type
	 * @param recpIdr the recp idr
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public String findDuplicateRecticles(String companyCode,
			String sectororigin,String sectorDest,String payType,String recpIdr) throws SystemException,
			PersistenceException;
	
	/**
	 * Find reconcile sector idrs.
	 *
	 * @param dupVO the dup vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<String> findReconcileSectorIdrs(MailInvoicDupRecepVO dupVO)
	throws SystemException,	PersistenceException;
	
	/**
	 * Method to find the billingperiod of the gpacode/billing periods of all
	 * the gpacodes of the country specified
	 * Method is called on clickig the suggestbutton.
	 *
	 * @param generateInvoiceFilterVO the generate invoice filter vo
	 * @return Collection<String>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3108
	 */
	public Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException,PersistenceException;

	/**
	 * Method to generate invoice for an agent.
	 *
	 * @param generateInvoiceFilterVO the generate invoice filter vo
	 * @return the string
	 * @throws SystemException the system exception
	 */
	public String[] generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException;
	
	/**
	 * Find despatch details.
	 *
	 * @param popUpVO the pop up vo
	 * @return the despatch enquiry vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException,PersistenceException;
	
	/**
	 * Find gpa billing details.
	 *
	 * @param popUpVO the pop up vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)
	throws SystemException,	PersistenceException;
	
	/**
	 * Find dsn select lov.
	 *
	 * @param companyCode the company code
	 * @param dsnNum the dsn num
	 * @param dsnDate the dsn date
	 * @param pageNumber the page number
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
			String dsnNum,String dsnDate,int pageNumber)
			throws SystemException,	PersistenceException;

	/**
	 * Find rate audit details.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return Collection<RateAuditVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3108
	 */
	public Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException,PersistenceException;

	/**
	 * Find rate audit details col.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return Collection<RateAuditVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3108
	 */
	public Collection<RateAuditVO> findRateAuditDetailsCol(RateAuditFilterVO rateAuditFilterVO) throws SystemException,PersistenceException;
	
	/**
	 * Find list rate audit details.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws SystemException,PersistenceException;


	/**
	 * Find cc adetails.
	 *
	 * @param maintainCCAFilterVO the maintain cca filter vo
	 * @return Collection<CCAdetailsVO> findCCAdetails
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3447
	 */
	public  Collection<CCAdetailsVO> findCCAdetails(
			MaintainCCAFilterVO maintainCCAFilterVO) throws SystemException ,
			PersistenceException;


	/**
	 * Find cca.
	 *
	 * @param maintainCCAFilterVO the maintain cca filter vo
	 * @return Collection<CCAdetailsVO> findCCA
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3447
	 */
	public Collection<CCAdetailsVO> findCCA(MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException,PersistenceException;

	/**
	 * List cc as.
	 *
	 * @param listCCAFilterVo the list cca filter vo
	 * @return Collection<CCAdetailsVO> findCCAdetails
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3429
	 */
	public  Page<CCAdetailsVO> listCCAs(
			ListCCAFilterVO listCCAFilterVo) throws SystemException ,
			PersistenceException;


	/**
	 * List proration details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2554
	 */
	public  Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO) throws SystemException,PersistenceException;
	
	/**
	 * List cc afor print.
	 *
	 * @param listCCAFilterVo the list cca filter vo
	 * @return Collection<CCAdetailsVO> findCCAdetails
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3429
	 */
	public  Collection<CCAdetailsVO> listCCAforPrint(
			ListCCAFilterVO listCCAFilterVo) throws SystemException ,
			PersistenceException;
	
	/**
	 * Find interline billing entries.
	 *
	 * @param airlineBillingFilterVO the airline billing filter vo
	 * @return documentBillingDetailsVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3434
	 */
	public Page<DocumentBillingDetailsVO> findInterlineBillingEntries (AirlineBillingFilterVO airlineBillingFilterVO)
	throws SystemException ,PersistenceException;
	
	/**
	 * /**.
	 *
	 * @param flightSectorRevenueFilterVO the flight sector revenue filter vo
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3429
	 * This method is for finding segmentdetails
	 */
	public Collection<SectorRevenueDetailsVO> findSectorDetails(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)throws SystemException ,PersistenceException;
	
	/**
	 * Find flight revenue for sector.
	 *
	 * @param flightSectorRevenueFilterVO the flight sector revenue filter vo
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3429
	 * This method is for finding the revenue for the specified sector
	 */
	public Collection<SectorRevenueDetailsVO> findFlightRevenueForSector(
			FlightSectorRevenueFilterVO flightSectorRevenueFilterVO)throws SystemException ,PersistenceException;

	/**
	 * Find list rate audit details from temp.
	 *
	 * @param rateAuditFilterVO the rate audit filter vo
	 * @return the rate audit vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public RateAuditVO findListRateAuditDetailsFromTemp(RateAuditFilterVO rateAuditFilterVO)throws SystemException,PersistenceException;

	/**
	 * Generate outward billing invoice.
	 *
	 * @param invoiceFilterVO the invoice filter vo
	 * @return the string
	 * @throws SystemException the system exception
	 * @author A-2521
	 * method to generate invoice for outward airline billing
	 */
	public String generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
	throws SystemException;

	/**
	 * Populate data in temp tables.
	 *
	 * @param rateAuditVO the rate audit vo
	 * @return String
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public String populateDataInTempTables(RateAuditVO rateAuditVO)throws SystemException,PersistenceException;

	/**
	 * Find prorate factor.
	 *
	 * @param rateAuditDetailsVO the rate audit details vo
	 * @return Integer
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Integer findProrateFactor(RateAuditDetailsVO rateAuditDetailsVO)throws SystemException,PersistenceException;




	/**
	 * Find dsn routing details.
	 *
	 * @param dsnRoutingFilterVO the dsn routing filter vo
	 * @return Collection<DSNRoutingVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 * method to get the routing details for a dsn
	 */
	public Collection<DSNRoutingVO> findDSNRoutingDetails(DSNRoutingFilterVO dsnRoutingFilterVO)
	throws SystemException;



	/**
	 * List unaccounted dispatches.
	 *
	 * @param unaccountedDispatchesFilterVO the unaccounted dispatches filter vo
	 * @return UnaccountedDispatchesVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2107
	 */
	public Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws SystemException ,
	PersistenceException;

	/**
	 * Find unaccounted dispatches report.
	 *
	 * @param unaccountedDispatchesFilterVO the unaccounted dispatches filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2107
	 */
	public Collection<UnaccountedDispatchesDetailsVO> findUnaccountedDispatchesReport(UnaccountedDispatchesFilterVO
			unaccountedDispatchesFilterVO) throws SystemException, PersistenceException;


	/**
	 * Find inter line billing details.
	 *
	 * @param airlineBillingDetailVO the airline billing detail vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2554
	 */
	public Collection<AirlineBillingDetailVO> findInterLineBillingDetails(
			AirlineBillingDetailVO  airlineBillingDetailVO)
			throws  SystemException ,PersistenceException;
	
	/**
	 * Find proration exceptions.
	 *
	 * @param prorationexceptionsfiltervo the prorationexceptionsfiltervo
	 * @return the page
	 * @throws SystemException the system exception
	 * @author a-3108
	 */
	public Page<ProrationExceptionVO> findProrationExceptions(ProrationExceptionsFilterVO prorationexceptionsfiltervo)
	throws SystemException;

	/**
	 * Prints the proration exception report.
	 *
	 * @param filterVO the filter vo
	 * @return the collection
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 * @author a-3108
	 */
	public Collection<ProrationExceptionVO> printProrationExceptionReport
	(ProrationExceptionsFilterVO filterVO)
	throws PersistenceException, SystemException;

	/**
	 * Prorate dsn.
	 *
	 * @param prorationexceptionvo the prorationexceptionvo
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-3108
	 */
	public String prorateDSN(ProrationExceptionVO prorationexceptionvo)
	throws SystemException, PersistenceException;

	/**
	 * Find proration log details.
	 *
	 * @param dsnFilterVO the dsn filter vo
	 * @return Collection<DSNFilterVO>
	 * @throws SystemException the system exception
	 * @author A-3229
	 * method to get the prorationlog details for a dsn
	 */
	public Collection<MailProrationLogVO> findProrationLogDetails(
			DSNFilterVO dsnFilterVO)
			throws SystemException;
	
	/**
	 * View proration log details.
	 *
	 * @param prorationFilterVO the proration filter vo
	 * @return Collection<ProrationDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public  Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO) throws SystemException,PersistenceException;
	
	/**
	 * Find flown details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<SectorRevenueDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO popUpVO)
	throws SystemException,	PersistenceException;




	/**
	 * Trigger flown and interline prov accounting.
	 *
	 * @param newRateAuditVO the new rate audit vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2565
	 */
	public void triggerFlownAndInterlineProvAccounting(RateAuditVO newRateAuditVO)throws SystemException,PersistenceException;
	
	/**
	 * Prints the document statistics report.
	 *
	 * @param statisticsFilterVO the statistics filter vo
	 * @return Collection<DocumentStatisticsDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3429
	 */
	public  Collection<DocumentStatisticsDetailsVO> printDocumentStatisticsReport(
			DocumentStatisticsFilterVO statisticsFilterVO) throws SystemException ,
			PersistenceException;

	/**
	 * Finds tand returns the GPA Billing entries available
	 * This includes billed, billable and on hold despatches.
	 *
	 * @param gpaBillingEntriesFilterVO the gpa billing entries filter vo
	 * @return Collection<DocumentBillingDetailsVO>
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	public Page<DocumentBillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws PersistenceException,SystemException ;
	
	/**
	 * Find max seqnum from mra billing details history.
	 *
	 * @param rateAuditDetailsVO the rate audit details vo
	 * @return the int
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2391
	 */
	public int findMaxSeqnumFromMRABillingDetailsHistory(RateAuditDetailsVO rateAuditDetailsVO)throws SystemException,PersistenceException;
	
	/**
	 * View irregularity details.
	 *
	 * @param filterVO the filter vo
	 * @return Collection<MRAIrregularityVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public Collection<MRAIrregularityVO> viewIrregularityDetails(MRAIrregularityFilterVO filterVO)
	throws SystemException,PersistenceException;
	
	/**
	 * Prints the irregularity report.
	 *
	 * @param filterVO the filter vo
	 * @return Collection<MRAIrregularityVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public Collection<MRAIrregularityVO> printIrregularityReport(MRAIrregularityFilterVO filterVO)
	throws SystemException,PersistenceException;

	/**
	 * Find accounting details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<MRAAccountingVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO popUpVO)
	throws SystemException,	PersistenceException;
	
	/**
	 * Find usps reporting details.
	 *
	 * @param popUpVO the pop up vo
	 * @return Collection<USPSReportingVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3229
	 */
	public Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO popUpVO)
	throws SystemException,	PersistenceException;

	/**
	 * added by Meenu for USPS accounting while processing Invoic ADV message.
	 *
	 * @param masterVO the master vo
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2565
	 */
	public void triggerUSPSAccounting(MailInvoicMasterVO masterVO)throws SystemException,	PersistenceException;

	/**
	 * Re prorate dsn.
	 *
	 * @param dsnRoutingVOs the dsn routing v os
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3251
	 */
	public void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException,	PersistenceException;



	/**
	 * Gets the total of dispatches.
	 *
	 * @param unaccountedDispatchesFilterVO the unaccounted dispatches filter vo
	 * @return UnaccountedDispatchesVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2107
	 */
	public Collection<UnaccountedDispatchesVO> getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO) throws SystemException ,
	PersistenceException;
	
	/**
	 * Find gpa billing details.
	 *
	 * @param cN66DetailsVO the c n66 details vo
	 * @return MRABillingDetailsVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3434
	 */
	public MRABillingDetailsVO findGpaBillingDetails(CN66DetailsVO cN66DetailsVO) throws SystemException ,
	PersistenceException;

	/**
	 * Find outstanding balances.
	 *
	 * @param outstandingBalanceVO the outstanding balance vo
	 * @return OutstandingBalanceVO
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-3434
	 */
	public Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO) 
	throws SystemException ,PersistenceException;
	
	/**
	 * calls accounting procedure.
	 *
	 * @param unAccountedDSNVOs the un accounted dsnv os
	 * @return the string
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public String performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs) throws SystemException,PersistenceException;
	
	/**
	 * Display fuel surcharge details.
	 *
	 * @param gpaCode the gpa code
	 * @param cmpCode the cmp code
	 * @return Collection<FuelSurchargeVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2391
	 */

	public Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCode)
	throws SystemException,PersistenceException;
	
	/**
	 * Find existing fuel surcharge v os.
	 *
	 * @param fuelSurchargeVO the fuel surcharge vo
	 * @return int
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2391
	 */

	public int findExistingFuelSurchargeVOs(FuelSurchargeVO fuelSurchargeVO)
	throws SystemException,PersistenceException;

	/**
	 * Generate mail exception report.
	 *
	 * @param mailExceptionReportsFilterVo the mail exception reports filter vo
	 * @return String - file data
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-2414
	 */
	public String generateMailExceptionReport(MailExceptionReportsFilterVO mailExceptionReportsFilterVo)
	throws SystemException,PersistenceException;
	
	/**
	 * Find mca lov.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @param displayPage the display page
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-4823
	 */
	public Page<CCAdetailsVO> findMCALov(CCAdetailsVO ccAdetailsVO,
			int displayPage)throws SystemException,PersistenceException;
	
	/**
	 * Find dsn lov.
	 *
	 * @param ccAdetailsVO the cc adetails vo
	 * @param displayPage the display page
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author a-4823
	 */
	public Page<CCAdetailsVO> findDSNLov(CCAdetailsVO ccAdetailsVO,
			int displayPage)throws SystemException,PersistenceException;
	
	/**
	 * Find billing entries for flight.
	 *
	 * @param flightValidationVO the flight validation vo
	 * @return Collection<MRABillingDetailsVO>
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	Collection<MRABillingDetailsVO> findBillingEntriesForFlight(FlightValidationVO flightValidationVO)
	throws SystemException, PersistenceException;
	
	/**
	 * Find approved mca.
	 *
	 * @param maintainCCAFilterVO the maintain cca filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 */
	public Collection<CCAdetailsVO> findApprovedMCA(
			MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException ,
			PersistenceException;
	
	/**
	 * Trigger proration.
	 *
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	public String triggerProration(CCAdetailsVO detailsVO)throws PersistenceException, SystemException;
	
	/**
	 * Trigger mca flown accounting.
	 *
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	public String triggerMCAFlownAccounting(CCAdetailsVO detailsVO)throws PersistenceException, SystemException;

	 /**
 	 * Find routing carrier details.
 	 *
 	 * @param carrierFilterVO the carrier filter vo
 	 * @return the collection
 	 * @throws SystemException the system exception
 	 * @throws PersistenceException the persistence exception
 	 * @author A-5166
 	 */
 public Collection<RoutingCarrierVO> findRoutingCarrierDetails(RoutingCarrierFilterVO carrierFilterVO) 
      throws SystemException ,PersistenceException;
 

 /**
  * Returns the ratelines based on the filter criteria.
  *
  * @param rateLineFilterVO the rate line filter vo
  * @return the collection
  * @throws SystemException the system exception
  * @throws PersistenceException the persistence exception
  * @author A-5166
  * Added for ICRD-17262 on 07-Feb-2013
  */
	public Collection<RateLineVO> findRateLineDetail(
			RateLineFilterVO rateLineFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * Find overlaping rate line details.
	 *
	 * @param filterVO the filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws PersistenceException the persistence exception
	 * @author A-5166
	 * Added for ICRD-17262 on 07-Feb-2013
	 */
	public Collection<RateLineVO> findOverlapingRateLineDetails(
		RateLineFilterVO filterVO)throws SystemException ,PersistenceException;
	 
 	/**
 	 * Initiate proration.
 	 *
 	 * @param companyCode the company code
 	 * @throws SystemException the system exception
 	 * @throws PersistenceException the persistence exception
 	 * @author A-5166
 	 * Added for ICRD-36146 on 06-Mar-2013
 	 */
	   public void initiateProration(String companyCode)
	    	throws SystemException , PersistenceException;
	   
	   
	  /**
  	 * Find billing site details.
  	 *
  	 * @param billingSiteFilterVO the billing site filter vo
  	 * @return the collection
  	 * @throws SystemException the system exception
  	 * @throws PersistenceException the persistence exception
  	 */
  	public Collection<BillingSiteVO> findBillingSiteDetails(BillingSiteFilterVO billingSiteFilterVO)
	  throws SystemException ,PersistenceException;
  	
  	/**
	   * List parameter lov.
	   *
	   * @param bsLovFilterVo the bs lov filter vo
	   * @return the page
	   * @throws PersistenceException the persistence exception
	   * @throws SystemException the system exception
	   */
	  public Page<BillingSiteLOVVO> listParameterLov(
			BillingSiteLOVFilterVO bsLovFilterVo) throws PersistenceException,
			SystemException;
  	
  	/**
	   * Find country over lapping.
	   *
	   * @param billingSiteVO the billing site vo
	   * @return the collection
	   * @throws SystemException the system exception
	   * @throws PersistenceException the persistence exception
	   */
	  public Collection<BillingSiteVO> findCountryOverLapping(BillingSiteVO billingSiteVO)
  	throws SystemException,PersistenceException;
	/**
	 * 	Method		:	MRADefaultsDAO.updateFlightSegment
	 *	Added by 	:	a-4809 on Oct 22, 2014
	 * 	Used for 	:	ICRD-68924,TK specific 
	 *  to update flight segment details for SAP CR
	 *	Parameters	:	@param dsnRoutingVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	  public void updateFlightSegment(DSNRoutingVO dsnRoutingVO)
			  throws SystemException,PersistenceException;
	  /**
  	 * Process billing matrix upload details.
  	 *
  	 * @param filterVO the filter vo
  	 * @return the string
  	 * @throws SystemException the system exception
  	 * @throws PersistenceException the persistence exception
  	 */
  	public String processBillingMatrixUploadDetails(FileUploadFilterVO filterVO)
			  throws SystemException,PersistenceException;
	/**
	 * Trigger mca internal accounting.
	 *
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	public String triggerMCAInternalAccounting(CCAdetailsVO detailsVO)throws PersistenceException, SystemException;
	/**
	 * 
	 * @param prorationexceptionvo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void prorateExceptionFlights(Collection<FlightValidationVO>flightValidationVOs)
	throws SystemException, PersistenceException;
	
	
	/**Added by A-3434 for CR ICRD-114599 on 29SEP2015
	 * 
	 * @param generateInvoiceFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException, PersistenceException;
	/**
	 * 
	 * @author A-5255
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException, PersistenceException;
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
			PersistenceException;
	/**
	 * 
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillingDetails(
			CN51CN66FilterVO cn51CN66FilterVO) throws SystemException,PersistenceException; 
	/**
	 * 
	 * @author A-5255
	 * @param blgMatrixFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<AuditDetailsVO> findBillingMatrixAuditDetails(
			BillingMatrixFilterVO blgMatrixFilterVO) throws SystemException,PersistenceException;
	/**
	 * 
	 * @author A-6245
	 * @param cn51CN66FilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<SurchargeBillingDetailVO> findSurchargeBillableDetails(
			CN51CN66FilterVO cn51CN66FilterVO) throws SystemException,PersistenceException; 
	/**
	 * @author A-6245
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<SurchargeProrationDetailsVO> viewSurchargeProrationDetailsForMCA(
			ProrationFilterVO prorationFilterVO) throws SystemException, PersistenceException;
	/**
	 * @author A-6245	
	 * @param fromDate
	 * @param toDate
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String updateInterfacedMails(String companyCode,String fromDate,String toDate) 
			throws SystemException, PersistenceException;
	/**
	 * @author A-7871	
	 * @param ratelineVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String activateBulkRatelines(RateLineVO ratelineVO) 
			throws SystemException, PersistenceException;

	/**
	 * @author A-7531
	 * @param documentBillingDetailsVOs
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException, PersistenceException;
	
	/**
	 * 
	 * 	Method		:	MRADefaultsDAO.findRerateBillableMails
	 *	Added by 	:	A-7531 
	 *	Parameters	:	@param documentBillingVO
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
	public Collection<DocumentBillingDetailsVO>  findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,
			String companyCode) throws SystemException, PersistenceException;
   /**
    * 
    * 	Method		:	MRADefaultsDAO.findRerateInterlineBillableMails
    *	Added by 	:	A-7531
    *	Parameters	:	@param documentBillingVO
    *	Parameters	:	@param companyCode
    *	Parameters	:	@return
    *	Parameters	:	@throws SystemException
    *	Parameters	:	@throws PersistenceException 
    *	Return type	: 	Collection<DocumentBillingDetailsVO>
    */
	public Collection<DocumentBillingDetailsVO> findRerateInterlineBillableMails(
			DocumentBillingDetailsVO documentBillingVO,String companyCode)throws SystemException, PersistenceException;

/**
 * 	Method		:	MRADefaultsDAO.findCCAStatus
 *	Added by 	:	A-7531 on 30-May-2017
 * 	Used for 	:
 *	Parameters	:	@param maintainCCAFilterVO
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
public String findCCAStatus(MaintainCCAFilterVO maintainCCAFilterVO)throws SystemException, PersistenceException;

/**
 * 	Method		:	saveMRADataForRatingJob
 *	Added by 	:	A-3429 on 04-Dec-2017
 * 	Used for 	:
 *	Parameters	:	@param rateAuditVO
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
public void saveMRADataForRatingJob(RateAuditVO rateAuditVO) throws SystemException, PersistenceException;

/**
 * 	Method		:	MRADefaultsDAO.findCCAStatus
 *	Added by 	:	A-7531 on 30-May-2017
 * 	Used for 	:
 *	Parameters	:	@param maintainCCAFilterVO
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
public String validateAgent(RateAuditVO rateAuditVO, String agentType)throws SystemException, PersistenceException;

/**
 * 	Method		:	MRADefaultsDAO.findblgLinePars
 *	Added by 	:	A-7531 on 20-Feb-2018
 * 	Used for 	:
 *	Parameters	:	@param cmpcod
 *	Parameters	:	@return 
 *	Return type	: 	Collection<BillingLineParameterVO>
 */
public Collection<BillingLineParameterVO> findblgLinePars(String cmpcod)throws SystemException, PersistenceException;


/**
 * 	Method		:	MRADefaultsDAO.findReproarteMails
 *	Added by 	:	A-7531 on 09-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param documentBillingVO
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public Collection<DocumentBillingDetailsVO> findReproarteMails(
		DocumentBillingDetailsVO documentBillingVO)throws SystemException, PersistenceException;

/**
 * 	Method		:	MRADefaultsDAO.reProrateExceptionMails
 *	Added by 	:	A-7531 on 09-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
public String reProrateExceptionMails(
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)throws SystemException, PersistenceException;
/**
 * @author A-7371
 * @param prorationFilterVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<AWMProrationDetailsVO> viewAWMProrationDetails(
		ProrationFilterVO prorationFilterVO) throws SystemException, PersistenceException;
	
/**
 * 
 * 	Method		:	MRADefaultsDAO.generateMailBillingInterfaceFile
 *	Added by 	:	A-7929 on 10-May-2018
 * 	Used for 	:   ICRD-245605
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 * @param toDate 
 * @param fromDate 
 * @return 
 */
public String generateMailBillingInterfaceFile(String regenerateFlag,String fileName, LocalDate fromDate, LocalDate toDate) throws SystemException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.findFlightrevenueDetails
 *	Added by 	:	a-8061 on 29-Jun-2018
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companycode
 *	Parameters	:	@param rowCount
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<FlightRevenueInterfaceVO>
 */
public Collection<FlightRevenueInterfaceVO> findFlightrevenueDetails(String companycode, boolean isFromRetrigger) throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.updateTruckCost
 *	Added by 	:	a-8061 on 17-Jul-2018
 * 	Used for 	:	ICRD-237070
 *	Parameters	:	@param truckOrderMailAWBVO 
 *	Return type	: 	void
 */
public void updateTruckCost(TruckOrderMailAWBVO truckOrderMailAWBVO,TruckOrderMailVO truckOrderMailVO) throws SystemException, PersistenceException;

/**
 * 
 * @param dsnRoutingVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String validateBSA(DSNRoutingVO dsnRoutingVO) throws SystemException, PersistenceException;

/**
 * 
 * 	Method		:	MRADefaultsDAO.updateMailBSAInterfacedDetails
 *	Added by 	:	a-8061 on 27-Aug-2018
 * 	Used for 	:
 *	Parameters	:	@param flightPk
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	void
 */
public void updateMailBSAInterfacedDetails(BlockSpaceFlightSegmentVO flightPk)throws SystemException, PersistenceException;
	 
 /**
 *
 * 	Method		:	MRADefaultsDAO.prorateMCA
 *	Added by 	:	A-7929 on 25-Jun-2018
 * 	Used for 	:   ICRD-237091
 *	Parameters	:	@param ccaRefNo
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */

public void prorateMCA(String ccaRefNo,String currChangeFlag) throws SystemException ;//modified for ICRD-282931
/**
 * 
 * 	Method		:	MRADefaultsDAO.findBlockSpaceFlights
 *	Added by 	:	a-8061 on 11-Sep-2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<CRAFlightFinaliseVO>
 */
public Collection<CRAFlightFinaliseVO> findBlockSpaceFlights(String companyCode)throws SystemException, PersistenceException;

/**
 * Find carrier details.
 *
 * @param carrierFilterVO the carrier filter vo
 * @return the collection
 * @throws SystemException the system exception
 * @throws PersistenceException the persistence exception
 * @author A-7794 as part of ICRD-285543
 */
public RoutingCarrierVO findCarrierDetails(RoutingCarrierVO carrierVO) 
throws SystemException ,PersistenceException;


public Collection<DocumentBillingDetailsVO> findGPABillingEntriesForAutoMCA(
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
		throws PersistenceException,SystemException ;
/**
 * @author a-8061
 * @param mRABillingDetailsVO
 * @return
 * @throws SystemException
 */
public Collection<MRABillingDetailsVO> findMRABillingDetails(MRABillingDetailsVO mRABillingDetailsVO)throws SystemException;
/**
 * 	Method		:	MRADefaultsDAO.importConsignmentDataToMRA
 *	Added by 	:	A-4809 on Nov 20, 2018
 * 	Used for 	:
 *	Parameters	:	@param mailInConsignmentVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	void
 */
public void importConsignmentDataToMRA(MailInConsignmentVO mailInConsignmentVO)
		throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.findUSPSInternationalIncentiveJobDetails
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	USPSPostalCalendarVO
 */
public Collection<USPSPostalCalendarVO> findUSPSInternationalIncentiveJobDetails(String companyCode)
throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.calculateUSPSIncentive
 *	Added by 	:	A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param uspsPostalCalendarVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	void
 */
public void calculateUSPSIncentive(USPSPostalCalendarVO uspsPostalCalendarVO,USPSIncentiveVO uspsIncentiveVO)
		throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.findMailsFromCarditForImport
 *	Added by 	:	A-4809 on Nov 30, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public Collection<DocumentBillingDetailsVO> findMailsFromCarditForImport(DocumentBillingDetailsVO documentBillingDetailsVO)
throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.importMailsFromCarditData
 *	Added by 	:	A-4809 on Dec 4, 2018
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	String
 */
public String importMailsFromCarditData (Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
		throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.findMailbagExistInMRA
 *	Added by 	:	A-4809 on Jan 2, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	String
 */
public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO)throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.listGPABillingEntries
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Page<DocumentBillingDetailsVO>
 */
public Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException,PersistenceException;
/**
 * 	Method		:	MRADefaultsDAO.listConsignmentDetails
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Page<ConsignmentDocumentVO>
 */
public Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO,Collection<CRAParameterVO> craParameterVOs) throws SystemException,PersistenceException;

/***
 * @author A-7794
 * @param mailScanDetailVO
 * @throws SystemException
 * @throws PersistenceException
 */
public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO) throws  SystemException,PersistenceException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.findBillingEntriesAtMailbagLevel
 *	Added by 	:	A-7531 on 13-May-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<MRABillingDetailsVO>
 */
public Collection<MRABillingDetailsVO> findBillingEntriesAtMailbagLevel(String companyCode)throws SystemException,PersistenceException;

/**
 * 
 * @param interfaceFilterVO
 * @throws SystemException
 */
public String generateSAPFIFile(SAPInterfaceFilterVO interfaceFilterVO) throws SystemException;

/**
 * 
 * 	Method		:	MRADefaultsDAO.updateRouteAndReprorate
 *	Added by 	:	A-8061 on 15-Dec-2020
 * 	Used for 	:
 *	Parameters	:	@param gPABillingEntriesFilterVO
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	void
 */
public String updateRouteAndReprorate(GPABillingEntriesFilterVO gPABillingEntriesFilterVO)throws SystemException,PersistenceException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.reImportPABuiltMailbagsToMRA
 *	Added by 	:	A-6245 on 12-Mar-2021
 * 	Used for 	:	IASCB-96008
 *	Parameters	:	@param mailbagVO
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO)throws SystemException;

public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)throws SystemException;


/**
 * 
 * @param VOs
 * @throws SystemException
 * @throws PersistenceException
 */
public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.findVoidedInterfaceDetails
 *	Added by 	:	A-8061 on 15-Oct-2019
 * 	Used for 	:	ICRD-336689
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<FlightRevenueInterfaceVO>
 */
public Collection<FlightRevenueInterfaceVO> findVoidedInterfaceDetails(DocumentBillingDetailsVO documentBillingDetailsVO) throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	MRADefaultsDAO.findMailbagBillingStatus
 *	Added by 	:	a-8331 on 25-Oct-2019
 * 	Used for 	:
 *	Parameters	:	@param mailbagvo
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<DocumentBillingDetailsVO>
 */
public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException, PersistenceException;

/**
 * 
 * 	Method		:	MRADefaultsDAO.findInterfaceDetails
 *	Added by 	:	a-5526 on 02-Sep-2019
 * 	Used for 	:	ICRD-245594
 *	Parameters	:	@param companycode
 *	Parameters	:	@param rowCount
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<FlightRevenueInterfaceVO>
 */

public Collection<FlightRevenueInterfaceVO> findInterfaceDetails(String companycode, boolean isFromRetrigger) throws SystemException, PersistenceException;
public Page<BillingScheduleDetailsVO> findBillingType(BillingScheduleFilterVO billingScheduleFilterVO, int pageNumber)throws PersistenceException, SystemException;
public boolean validateBillingSchedulemaster(BillingScheduleDetailsVO billingScheduleDetailsVO)throws PersistenceException, SystemException;

public String saveMRADataForProvisionalRate(RateAuditVO rateAuditVO) throws PersistenceException, SystemException;

public void calculateProvisionalRate(Long noOfRecords) throws PersistenceException, SystemException;
/**
 * 	Method		:	MRADefaultsDAO.findMRAGLAccountingEntries
 *	Added by 	:	A-10164 on 15-Feb-2023
 *	User Story	:	IASCB-162079
 *	Parameters	:	@param glInterfaceFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	List<GLInterfaceDetailVO>
 */
public List<GLInterfaceDetailVO> findMRAGLAccountingEntries(GLInterfaceFilterVO glInterfaceFilterVO)
			throws SystemException;
}





