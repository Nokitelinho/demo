/*
 * MRAGPABillingDAO.java Created on Dec 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.RebillRemarksDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
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
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 *
 */
public interface MRAGPABillingDAO {

	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Page<CN51SummaryVO> findAllInvoices(CN51SummaryFilterVO cn51SummaryFilterVO)
	throws PersistenceException,SystemException ;

	public Collection<CN51SummaryVO> findAllInvoicesForPASSFileUpdate(CN51SummaryFilterVO cn51SummaryFilterVO)
			throws SystemException ;

	/**
	 * MEthod to print InvoiceDetails
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Collection<CN51SummaryVO> findAllInvoicesForReport(CN51SummaryFilterVO cn51SummaryFilterVO)
	throws PersistenceException,SystemException ;


	/**
	 * Finds and returns the CN51VO  based on the filter criteria
	 *
	 * @param gpaBillingInvoiceEnquiryFilterVO
	 * @return CN51SummaryVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
	throws PersistenceException,SystemException ;

	/**
	 * Finds and returns the CN51 details
	 *
	 * @param cn51CN66FilterVO
	 * @return Collection<CN51DetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Page<CN51DetailsVO> findCN51Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws PersistenceException,SystemException;
	/**
	 * Finds and returns the CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return Collection<CN66DetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Page<CN66DetailsVO> findCN66Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws PersistenceException,SystemException;

	/**
	 * Finds tand returns the GPA Billing entries available
	 * This includes billed, billable and on hold despatches
	 *
	 * @param gpaBillingEntriesFilterVO
	 * @return Collection<GPABillingDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<GPABillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws PersistenceException,SystemException ;
	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO  invoiceLovVO)
	throws PersistenceException,SystemException ;


	/**
	 * Method to generate invoice for an agent
	 * @param generateInvoiceFilterVO
	 * @throws SystemException
	 */
	public String generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.withdrawMailbags
	 *	Added by 	:	A-6991 on 05-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
    		throws SystemException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.finalizeProformaInvoice
	 *	Added by 	:	A-6991 on 07-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param documentBillingDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
    		throws SystemException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.withdrawInvoice
	 *	Added by 	:	A-6991 on 18-Sep-2017
	 * 	Used for 	:   ICRD-211662
	 *	Parameters	:	@param summaryVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	public void withdrawInvoice(CN51SummaryVO summaryVO)
	throws SystemException;
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  Collection<CN66DetailsVO> generateCN66Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException,SystemException;
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  Collection<CN51DetailsVO> generateCN51Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException,SystemException;

	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  InvoiceDetailsReportVO generateInvoiceReport(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException,SystemException;
	/**
	 * @author A-2408
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<BillingSummaryDetailsVO> findBillingSummaryDetailsForPrint(BillingSummaryDetailsFilterVO filterVO)
	throws PersistenceException, SystemException;
	/**
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<CN51DetailsVO> findCN51DetailsForPrint(CN51DetailsPrintFilterVO filterVO)
	throws PersistenceException, SystemException;
	/**
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<CN66DetailsPrintVO> findCN66DetailsForPrint(CN66DetailsPrintFilterVO filterVO)
	throws PersistenceException, SystemException;
	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<GPASettlementVO> findSettlementDetails(InvoiceSettlementFilterVO 
			invoiceSettlementFilterVO,int displayPage)throws PersistenceException, SystemException;
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO)throws PersistenceException,SystemException;

	/**
	 * @param cn51SummaryVO
	 * @return ProformaInvoiceDiffReportVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<ProformaInvoiceDiffReportVO> generateProformaInvoiceDiffReport(CN51SummaryVO cn51SummaryVO)
	throws PersistenceException,SystemException ;

	/**
	 * Added by A-2565 Meenu for GPA billing accounting
	 * @param summaryVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Object triggerGPABillingAccounting(CN51SummaryVO summaryVO)throws PersistenceException,SystemException ;
	/**
	 * @author a-3447
	 * @param companyCode
	 * @param airlineIdentifier
	 * @return
	 * For fetching Airline Details
	 */
	public AirlineVO findAirlineAddress(String companyCode, int airlineIdentifier) throws PersistenceException,SystemException;

	/**
	 * 
	 * @param gpaSettlementVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO)throws PersistenceException,SystemException;

	/**
	 * 
	 * @param settlementDetailsVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> findSettledInvoicesForGPA(
			SettlementDetailsVO settlementDetailsVO)throws PersistenceException,SystemException;

	/**
	 * 
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String findSettlementCurrency(String companyCode, String paCode)throws PersistenceException,SystemException;

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> generateSettlementDetails(
			InvoiceSettlementFilterVO filterVO)throws PersistenceException,SystemException;

	/**
	 * 
	 * @param gpaSettlementVO
	 * @return
	 */
	public Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO)throws PersistenceException,SystemException;
	public Collection<POMailSummaryDetailsVO> findPOMailSummaryDetails(
			BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO)throws PersistenceException,SystemException;

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public void triggerAccounting(GPASettlementVO gpaSettlementVO)throws PersistenceException,SystemException;


	public Collection<SettlementDetailsVO> findOutStandingChequesForGPA(
			GPASettlementVO gpaSettlementVO)throws PersistenceException,SystemException;
	/**
	 * 
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  InvoiceDetailsReportVO generateInvoiceReportTK(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException,SystemException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.findMailDetailsforAudit
	 *	Added by 	:	a-4809 on Apr 2, 2014
	 * 	Used for 	:	to find maildetails for audit while
	 * invoice generation
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Collection<MailDetailVO>
	 */
	public Collection<MailDetailVO> findMailDetailsforAudit(String invoiceNumber,String companyCode)
	throws SystemException,PersistenceException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.findGPADtlsForSAPSettlemntMail
	 *	Added by 	:	A-7794 on 28-Nov-2017 as part of ICRD-194277
	 * 	Used for 	:	to find invoice details for settlement
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param billingPeriodTodate
	 *Parameters	:	@param accountNumber
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	CN51SummaryVO
	 */
	public CN51SummaryVO findGPADtlsForSAPSettlemntMail(String invoiceNumber,LocalDate billingPeriodTodate,String accountNumber)
	throws SystemException,PersistenceException;
	/**
	 * 
	 * 	Method		:	MRAGPABillingDAO.findGPADtlsForSAPSettlemntMail
	 *	Added by 	:	A-7794 on 28-Nov-2017 as part of ICRD-194277
	 * 	Used for 	:	to find invoice details for settlement
	 *	Parameters	:	@param comapnyCode
	 *	Parameters	:	@param invoice
	 *Parameters	:	@param gpaCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	CN51SummaryVO
	 */
	public InvoiceSettlementVO findLatestSettlementForInvoice(String comapnyCode, String invoice, String gpaCode) throws PersistenceException,
	SystemException ;
	
	/**
	 * Added by 	:	A-7794 on 23-Jan-2018 as part of ICRD-194277
	 * @param gpaCode
	 * @param setlmntRef
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public GPASettlementVO findSettlementSeqNum(String gpaCode, String setlmntRef) throws PersistenceException,
	SystemException ;
	/**
	 * @author A-7794 as part of ICRD-234354
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  Collection<CN66DetailsVO> generateCN66ReportForKE(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.findSettlementInvoiceDetails
	 *	Added by 	:	A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	GPASettlementVO
	 */
	public Collection<GPASettlementVO> findSettlementInvoiceDetails(
			InvoiceSettlementFilterVO filterVO)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.findSettledMailbagDetails
	 *	Added by 	:	A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Page<InvoiceSettlementVO>
	 */
	public Page<InvoiceSettlementVO> findSettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO)throws PersistenceException,SystemException;
	
	/**
	 * 	Method		:	MRAGPABillingDAO.findUnsettledMailbagDetails
	 *	Added by 	:	A-7531 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Page<InvoiceSettlementVO>
	 */
	public Page<InvoiceSettlementVO> findUnsettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.findMailbagSettlementHistory
	 *	Added by 	:	A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceFiletrVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GPASettlementVO>
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(
			InvoiceSettlementFilterVO invoiceFiletrVO) throws PersistenceException, SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.findUnsettledInvoiceDetails
	 *	Added by 	:	A-7531 on 26-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<GPASettlementVO>
	 */
	public Collection<GPASettlementVO> findUnsettledInvoiceDetails(
			InvoiceSettlementFilterVO filterVO)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.updateSettlementDetails
	 *	Added by 	:	A-7531 on 05-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param settlementDetailsVOs 
	 *	Return type	: 	void
	 */
	public void updateSettlementDetails(
			Collection<SettlementDetailsVO> settlementDetailsVOs,String invnum,LocalDate stldate,String currency,String settlementId)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.findReminderListForGpaBilling
	 *	Added by 	:	A-5526 on 23-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param reminderDetailsFilterVO 
	 *	Return type	: 	void
	 */
	public Page<ReminderDetailsVO> findReminderListForGpaBilling(ReminderDetailsFilterVO reminderDetailsFilterVO)throws PersistenceException,SystemException;
	
	/**
	 * 
	 * @param reminderDetailsFilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String generateGpaRebill(ReminderDetailsFilterVO reminderDetailsFilterVO)throws PersistenceException,SystemException;
/**
	 * 	Method		:	MRAGPABillingDAO.processMraSettlementFromExcel
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String processMraSettlementFromExcel(FileUploadFilterVO filterVO)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.fetchDataForUpload
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileType
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<InvoiceSettlementVO>
	 */
	public Collection<InvoiceSettlementVO> fetchDataForUpload(String companyCode, String fileType)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.removeDataFromTempTable
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO 
	 *	Return type	: 	void
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)throws PersistenceException,SystemException;
	/**
	 * 	Method		:	MRAGPABillingDAO.filenameValidation
	 *	Added by 	:	A-7531 on 21-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public int filenameValidation(InvoiceSettlementVO invoiceVO)throws PersistenceException,SystemException;
	
	/**
	 * 
	 * @param reminderListFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<RebillRemarksDetailVO> findGPARemarkDetails(ReminderDetailsFilterVO reminderListFilterVO)
			throws PersistenceException,SystemException;
	/***
	 * @author A-7794
	 * @param invoiceStlmntVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public void triggerGPASettlementAccounting(InvoiceSettlementVO invoiceStlmntVO,GPASettlementVO gpaSettlementVO)throws PersistenceException,SystemException;
	/**
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public InvoiceLovVO findInvoiceNumber(InvoiceLovVO  invoiceLovVO)
	throws PersistenceException,SystemException ;
	/**
	 * 	Method		:	MRAGPABillingDAO.findInvoicesforPASS
	 *	Added by 	:	A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<GPAInvoiceVO>
	 */
	public Collection<GPAInvoiceVO> findInvoicesforPASS(GeneratePASSFilterVO passFilterVO)
	throws PersistenceException,SystemException ;
/**
 * 	Method		:	MRAGPABillingDAO.getSequenceNumberforPASSFile
 *	Added by 	:	A-4809 on 16-Apr-2021
 * 	Used for 	:
 *	Parameters	:	@param invoiceVO
 *	Parameters	:	@return
 *	Parameters	:	@throws PersistenceException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	int
 */
	public String getSequenceNumberforPASSFile(GPAInvoiceVO invoiceVO)
	throws PersistenceException,SystemException ;

    Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws SystemException;

    /**
     * @author A-10383
     * @param cN51CN66FilterVO
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */
	public InvoiceDetailsReportVO generateInvoiceReportSQ(CN51CN66FilterVO cN51CN66FilterVO)throws PersistenceException, SystemException;
	public  Collection<CN51DetailsVO> generateCN51ReportSQ(CN51CN66FilterVO cN51CN66FilterVO)
			throws PersistenceException,SystemException;
}
