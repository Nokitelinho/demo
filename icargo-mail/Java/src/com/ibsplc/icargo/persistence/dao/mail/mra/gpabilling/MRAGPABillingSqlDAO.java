/*
 * MRAGPABillingSqlDAO.java Created on Dec 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.RebillRemarksDetailVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ReminderDetailsGpaBillingMapper;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ReminderListFilterQuery;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.GPABillingDetailsMultiMapper;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;



/**
 * @author Philip
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jan 8, 2007   Philip 		            Initial draft
 *  0.2         Jan 17,2007   Kiran 					Implemented the method findAllInvoices
 *  0.3         Jan 17,2007	  Prem Kumar.M              Implemented methods  findCN66Details,findCN51Details
 *              Mar 26,2007	  Prem Kumar.M				Implemented methods findSettlementDetails,findSettlementhistory
 *
 */
public class MRAGPABillingSqlDAO extends AbstractQueryDAO implements
MRAGPABillingDAO {

	/**
	 * log variable
	 */
	private Log log = LogFactory.getLogger("MRA:GPABILLING");
	/**
	 * CLASS_NAME
	 */
	private static final String CLASS_NAME = "MRAGPABillingSqlDAO";
	/**
	 * query string used in the function findCN66Details
	 */
	private static final String FIND_CN66_DETAILS="mail.mra.gpabilling.findCN66Details";
	/**
	 * query string used in the function findAllInvoices
	 */
	private static final String FIND_ALL_INVOICES = "mail.mra.gpabilling.findAllInvoices";
	private static final String FIND_ALL_INVOICES_UNION = "mail.mra.gpabilling.findAllInvoices.union";
	/**
	 * query string used in the function findGPABillingEntries
	 */
	private static final String Find_GPABILLINGDETAILS = "mail.mra.gpabilling.findGPABillingEntries";
	private static final String Find_C66GPABILLINGDETAILS="mail.mra.gpabilling.findc66GPABillingEntries";
	private static final String Find_CCAGPABILLINGDETAILS="mail.mra.gpabilling.findccaGPABillingEntries";
	private static final String MAILBAG_LEVEL_SETTLEMENT="mail.mra.gpabilling.mailbagLevelSettlement";
	/**
	 * String used in findCN51Details to get the Native query
	 */
	private static final String FIND_CN51_DETAILS="mail.mra.gpabilling.findCN51Details";

	private static final String FIND_INVOICELOV="mail.mra.gpabilling.findInvoiceLov";

	private static final String GENERATE_INVOICE="mail.mra.gpabilling.generateinvoice";
	//Added by A-6991 for ICRD-211662 Starts
	private static final String FINALIZE_INVOICE="mail.mra.gpabilling.finalizeinvoice";

	
	private static final String WITHDRAW_MAILS="mail.mra.gpabilling.withdrawmails";
	
	private static final String WITHDRAW_INVOICE="mail.mra.gpabilling.withdrawinvoice";
	//Added by A-6991 for ICRD-211662 Ends
	private static final String FIND_INVOICEREPORT_DETAILS = "mail.mra.gpabilling.findinvoicereportdetails";
	private static final String MRA_DEFAULTS_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	private static final String MRA_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
	private static final String FIND_INVOICEREPORT_DATA = "mail.mra.gpabilling.findinvoicereportdata";


	private static final String FIND_BILLING_SMY_DETAILS="mail.mra.gpabilling.findbillingsummarydetails";

	private static final String FIND_CN51_DETAILS_PRINT="mail.mra.gpabilling.findcn51detailsforprint";

	private static final String FIND_CN66_DETAILS_PRINT="mail.mra.gpabilling.findcn66detailsforprint";
	/**
	 * 
	 */
	private static final String FIND_CN51_SUMMARY_SETTLEMENT_DETAILS="mail.mra.gpabilling.findsettlementdetails";
	private static final String FIND_SETTLEMENT_HISTORY = "mail.mra.gpabilling.findsettlementhistory";
	private static final String FIND_GPABILLING_INVOICES="mail.mra.gpabilling.findgpabillinginvoiceenquiry";

	private static final String FIND_GPABILLING_PROFORMAINVOICEDIFF_PRINT = "mail.mra.gpabilling.findproformainvoicediffreport";
	private static final String MRA_GPABLG__FIND_AIRLINEDTLS = "mail.mra.gpabilling.findairlineaddress";
	private static final String MRA_DEFAULTS_EXCFLG="G";
	private static final String FIND_UNSETTLED_INVOICES = "mail.mra.gpabilling.findUnsettledInvoicesForGPA";

	private static final String FIND_GPA_UNSETLLED_INVOICES ="mail.mra.gpabilling.findgpaforunsetteldinvoices";
	private static final String FIND_SETTLED_INVOICES = "mail.mra.gpabilling.findSettledInvoicesForGPA";
	private static final String FIND_SETTLEMENT_CURRENCY="mail.mra.gpabilling.findSettlementCurrency";
	private static final String FIND_POMAILSUMMARY="mail.mra.gpabilling.findpomailsummarydetails";
	private static final String MRA_GPABILLING_TRIGGERACCOUNTING="mail.mra.gpabilling.triggeraccounting";
	private static final String FIND_OUTSTANDING="mail.mra.gpabilling.findoutstandingchequeamounts";
	private static final String FIND_INVOICEREPORTTK_DATA = "mail.mra.gpabilling.findinvoicereporttkdata";
	private static final String FIND_INVOICEREPORTTK_DATA_REBILL= "mail.mra.gpabilling.findinvoicereporttkdataforRebill";
	private static final String FIND_MAILDETAILSFORAUDIT="mail.mra.gpabilling.findmaildetailsforaudit";
	private static final String ACC_INV_ROWNUM_RANK_QUERY=
    	"SELECT RESULT_TABLE.* ,ROWNUM AS RANK FROM(";
	//Added by A-5220 for ICRD-32647 starts
	private static final String MRA_GPABLG_DENSE_RANK_QUERY = 
		"SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY ";
	private static final String MRA_GPABLG_ROWNUM_QUERY =
		"SELECT RESULT_TABLE.* , ROWNUM AS RANK FROM ( ";
	private static final String MRA_GPABLG_SUFFIX_QUERY = " )RESULT_TABLE";
	private static final String PRINT_CN66DETAILS ="mail.mra.gpabilling.findCN66DetailsReport";
	private static final String PRINT_CN66DETAILS_REBILL ="mail.mra.gpabilling.findCN66Detailsrebillreport";
  //Added by A-5220 for ICRD-32647 ends
	//Added by A-7794 as part of ICRD-194277
		private static final String MRA_GPABILLING_GETINVOICE_FOR_GPA="mail.mra.gpabilling.findinvoicedetails";
		private static final String MRA_GPABILLING_GETLATESTINVOICE_FOR_GPA="mail.mra.gpabilling.findlatestinvoicedetails";
		private static final String MRA_GPABILLING_STL_SEQNUM = "mail.mra.gpabilling.findsettlementseqnum";
		private static final String MRA_GPA_SETTLEMENT="mail.mra.gpabilling.findsettlementforGPA";
		private static final String FIND_SETTLED_INVOICESLEVEL="mail.mra.gpabilling.invoicelevelsettlement";
	   private static final String MRA_FINDCOUNT_EXISTING_PERFORMAMAILBAG="mail.mra.gpabilling.checkforcountofmailbagsunderperforma";


		//Added by A-7794 as part of ICRD-234354
		private static final String PRINT_KE_CN66DETAILS ="mail.mra.gpabilling.findCN66DetailsReportForKE";

		private static final String FIND_SETTLED_MAILBAG_DETAILS="mail.mra.gpabilling.settledmailbagdetails";
		//added by A-9477 for IASCB-94550 starts
		private static final String FIND_SETTLED_MAILBAG_DETAILS_ORACLE="mail.mra.gpabilling.settledmailbagdetails.oracle";
		//added by A-9477 for IASCB-94550 starts
		
		private static final String FIND_UNSETTLED_MAILBAG_DETAILS="mail.mra.gpabilling.unsettledmailbagdetails";
		private static final String FIND_UNSETTLED_MAILBAG_DETAILS_ORACLE="mail.mra.gpabilling.unsettledmailbagdetails.oracle";
		private static final String FIND_SETTLED_MAILBAG_HISTORY_DETAILS="mail.mra.gpabilling.mailbagsettlementhistorydetails";
		private static final String FIND_SETTLEMENT_DETAILS="mail.mra.gpabilling.settlementdetails";
		
		  private static final String  FIND_REMINDERDETAILS_GPABILLING ="mail.mra.gpabilling.findreminderdetailsgpabilling";
		  private static final String GPA_REBILL="mail.mra.gpabilling.gparebill";
		  private static final String FIND_GPA_REBILL_DETAILS="mail.mra.gpabilling.findremainderlist";

        private static final String MRA_SETTLEMENT_FILEUPLOAD = "mail.mra.gpabilling.processSettlementUploadDetails";//added as part of icrd-235819
		private static final String FETCH_MRA_SETTLEMENT_DATAFORUPLOAD = "mail.mra.gpabilling.fetchDataForUpload";
		private static final String REMOVE_DATA_FROM_TEMPTABLE="mail.mra.gpabilling.removeDataFromTempTable";
		private static final String MRA_SETTLEMENT_FILENAMEVALIDATION="mail.mra.gpabilling.filenameValidation";
		private static final String REMARK_DETAILS = "mail.mra.gpabilling.findgparemaks";
		private static final String DATE ="yyyyMMdd";
		private static final String FIND_INVOICEPASS="mail.mra.gpabilling.findInvoicesforPASS";
		private static final String FIND_SEQNUMFORPASS="mail.mra.gpabilling.findSequenceNumberforPASS";
		private static final String MAIL_MRA_GPABILLING_FIND_PASS_FILE_NAMES = "mail.mra.gpabilling.findPassFileNames";
		private static final String MAIL_MRA_GPABILLING_FIND_PASS_FILE_NAMES_ORACLE = "mail.mra.gpabilling.findPassFileNamesOracle";

		private static final String FIND_ALL_INVOICES_FOR_PASSFILENAME_UPDATE = "mail.mra.gpabilling.findAllInvoicesForPassFileNameUpdate";

	/**
	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<CN51SummaryVO> findAllInvoices(CN51SummaryFilterVO cn51SummaryFilterVO)
	throws PersistenceException,SystemException{
		
	
		Query query = null;
		Query query2 = null;
		int pageNumber=cn51SummaryFilterVO.getPageNumber();
		log.log(Log.INFO,"Page"+pageNumber);
		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_ALL_INVOICES);
		query = new CN51SummaryFilterQuery(
				qry,cn51SummaryFilterVO);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);

		
		
		
		rankQuery.append(query);
	//Added By A-8527 for ICRD-234294 starts
	String qry2 = getQueryManager().getNamedNativeQueryString(
			FIND_ALL_INVOICES_UNION);
	query2 =new CN51SummaryFilterQuery(
			qry2,cn51SummaryFilterVO);
	query2.append(" ORDER BY INVNUM,INVSERNUM,GPACOD ");
	rankQuery.append(query2);
	//Added By A-8527 for ICRD-234294 Ends
	PageableNativeQuery<CN51SummaryVO> pgqry = null;
	pgqry = new PageableNativeQuery<CN51SummaryVO>(0, rankQuery.toString(),
				new CN51SummaryMapper());
	
	
	

	applyAdditionalFilters(cn51SummaryFilterVO, pgqry);
	
    pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		
    if(cn51SummaryFilterVO.isFetchAllResult()){
    	List<CN51SummaryVO>  invoices=pgqry.getResultList(new CN51SummaryMapper());
    	return new Page<>(
    			invoices, 0, 0, 0, 0, 0, false);
    }else{
		return pgqry.getPage(cn51SummaryFilterVO.getPageNumber());
    }
		
	}

	public Collection<CN51SummaryVO> findAllInvoicesForPASSFileUpdate(CN51SummaryFilterVO cn51SummaryFilterVO)
			throws SystemException{
		Query query = null;
		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_ALL_INVOICES_FOR_PASSFILENAME_UPDATE);
		query = new CN51SummaryFilterQuery(
				qry,cn51SummaryFilterVO);
		if(cn51SummaryFilterVO.getPassFileName()==null || cn51SummaryFilterVO.getPassFileName().isEmpty()) {
			query.append(" AND C51SMY.INTFCDFILNAM IS NULL ");
		}
		query.append(" GROUP BY C51SMY.CMPCOD,C51SMY.GPACOD,POAMST.POANAM,C51SMY.INVNUM,C51SMY.INVSERNUM,C51SMY.INTFCDFILNAM,C66.BRHOFC,C51SMY.BLGPRDFRM,C51SMY.BLGPRDTOO ");
		return query.getResultList(new CN51SummaryMapper());

	}

	private void applyAdditionalFilters(CN51SummaryFilterVO cn51SummaryFilterVO,
			PageableNativeQuery<CN51SummaryVO> pgqry) {
		int index = 0;
		pgqry.setParameter(++index, cn51SummaryFilterVO.getCompanyCode());
		if (cn51SummaryFilterVO.getFromDate() != null) {
			pgqry.setParameter(++index,
					Integer.parseInt(cn51SummaryFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
		}
		if (cn51SummaryFilterVO.getToDate() != null) {
			pgqry.setParameter(++index,
					Integer.parseInt(cn51SummaryFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
		}
		String gpaCodeFilter = cn51SummaryFilterVO.getGpaCode();
		if (gpaCodeFilter != null && gpaCodeFilter.length() > 0) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getGpaCode());
		}

		if (isNotNull(cn51SummaryFilterVO.getInvoiceNumber())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getInvoiceNumber());
		}
		if (isNotNull(cn51SummaryFilterVO.getInvoiceStatus())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getInvoiceStatus());
		}

		if (isNotNull(cn51SummaryFilterVO.getPeriodNumber())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getPeriodNumber());
		}
		if (cn51SummaryFilterVO.isPASS()) {
			pgqry.setParameter(++index, FileNameLovVO.PASS_PA_POATYP);
		}

		pgqry.setParameter(++index, cn51SummaryFilterVO.getCompanyCode());
		if (cn51SummaryFilterVO.getFromDate() != null) {
			pgqry.setParameter(++index,
					Integer.parseInt(cn51SummaryFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
		}
		if (cn51SummaryFilterVO.getToDate() != null) {
			pgqry.setParameter(++index,
					Integer.parseInt(cn51SummaryFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
		}
		if (isNotNull(gpaCodeFilter)) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getGpaCode());
		}
		if (isNotNull(cn51SummaryFilterVO.getInvoiceNumber())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getInvoiceNumber());
		}
		if (isNotNull(cn51SummaryFilterVO.getInvoiceStatus())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getInvoiceStatus());
		}
		if (isNotNull(cn51SummaryFilterVO.getPeriodNumber())) {
			pgqry.setParameter(++index, cn51SummaryFilterVO.getPeriodNumber());
		}
		if (cn51SummaryFilterVO.isPASS()) {
			pgqry.setParameter(++index, FileNameLovVO.PASS_PA_POATYP);
		}
	}

	private boolean isNotNull(String s){
		return (s!=null && s.length()>0);
	}
	
	/**
	 * Method to Print InvoiceDetails
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<CN51SummaryVO> findAllInvoicesForReport(
			CN51SummaryFilterVO cn51SummaryFilterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findAirlineExceptions");
		
		// String baseQuery = null;
		Query query = getQueryManager().createNamedNativeQuery(FIND_ALL_INVOICES);
		
		int index = 0;
		
		
		query.setParameter(++index,cn51SummaryFilterVO.getCompanyCode());
		if(cn51SummaryFilterVO.getFromDate() != null) {
			query.append(" AND C51SMY.BLGPRDFRM >= ?");
			query.setParameter(++index, cn51SummaryFilterVO.getFromDate());
			}
		if(cn51SummaryFilterVO.getToDate() != null) {
			query.append(" AND C51SMY.BLGPRDTOO <= ?");
			query.setParameter(++index, cn51SummaryFilterVO.getToDate());
			}
		String gpaCodeFilter = cn51SummaryFilterVO.getGpaCode();
		 if(gpaCodeFilter != null && gpaCodeFilter.length() > 0)
		{query.append(" AND C51SMY.GPACOD = ?");
			 query.setParameter(++index, cn51SummaryFilterVO.getGpaCode());
		}

		 if(cn51SummaryFilterVO.getInvoiceNumber() != null && cn51SummaryFilterVO.getInvoiceNumber().length() > 0){
			 query.append(" AND C51SMY.INVNUM = ?");
			 query.setParameter(++index, cn51SummaryFilterVO.getInvoiceNumber()); 
		 }
		 if(cn51SummaryFilterVO.getInvoiceStatus() != null && cn51SummaryFilterVO.getInvoiceStatus().length() > 0){
			 query.append(" AND C51SMY.INVSTA = ?");
			 query.setParameter(++index, cn51SummaryFilterVO.getInvoiceStatus());  
		 }
		 query.append(" ORDER BY C51SMY.INVNUM,C51SMY.GPACOD ");//Added for ICRD-201846
		log.log(Log.FINE, "", query);
		List<CN51SummaryVO> airlineExceptionsVOs = query
				.getResultList(new CN51SummaryMapper());
		log.exiting(CLASS_NAME, "findAirlineExceptions");
		if (airlineExceptionsVOs != null) {
			log.log(Log.FINE, "\nSize  of coll obtaiiend----->",
					airlineExceptionsVOs.size());
		}

		return airlineExceptionsVOs;
	}
	/**
	 * @author A-2280
	 * Finds and returns the CN51 details
	 *
	 * @param cn51CN66FilterVO
	 * @return Collection<CN51DetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Page<CN51DetailsVO> findCN51Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws PersistenceException,SystemException{
		log.entering(CLASS_NAME,"findCN51Details");
		
		
		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_CN51_DETAILS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(qry);
		PageableNativeQuery<CN51DetailsVO> pgqry = null;
		pgqry = new PageableNativeQuery<CN51DetailsVO>(0, rankQuery.toString(),
				new CN51DetailsMapper());
		//Modified by A-5220 for ICRD-32647 ends
		int index = 0;
		
		
		pgqry.setParameter(++index,cn51CN66FilterVO.getCompanyCode());
		pgqry.setParameter(++index,cn51CN66FilterVO.getGpaCode());
		pgqry.setParameter(++index,cn51CN66FilterVO.getInvoiceNumber());

		
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(cn51CN66FilterVO.getPageNumber());
		
		
	
		
	}
	
	
	

	/**
	 * @author A-2280
	 * Finds and returns the CN66 details
	 *
	 * @param cn51CN66FilterVO
	 * @return Collection<CN66DetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public Page<CN66DetailsVO> findCN66Details(CN51CN66FilterVO cn51CN66FilterVO)
	throws PersistenceException,SystemException{
		
		
log.entering(CLASS_NAME,"findCN66Details");
		
		
		String qry = getQueryManager().getNamedNativeQueryString(
				FIND_CN66_DETAILS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		rankQuery.append(qry);
		PageableNativeQuery<CN66DetailsVO> pgqry = null;
		pgqry = new PageableNativeQuery<CN66DetailsVO>(0, rankQuery.toString(),
				new CN66DetailsReportMapper());
		//Modified by A-5220 for ICRD-32647 ends
		int index = 0;
		
		
		pgqry.setParameter(++index,cn51CN66FilterVO.getCompanyCode());
		pgqry.setParameter(++index,cn51CN66FilterVO.getGpaCode());
		pgqry.setParameter(++index,cn51CN66FilterVO.getInvoiceNumber());
		if(cn51CN66FilterVO.getCategory() != null && cn51CN66FilterVO.getCategory().length() > 0){
			pgqry.append(" AND CN66.MALCTGCOD = ?");
			pgqry.setParameter(++index, cn51CN66FilterVO.getCategory()); 
		 }
		if(cn51CN66FilterVO.getOrgin() != null && cn51CN66FilterVO.getOrgin().length() > 0){
			pgqry.append(" AND CN66.ORGCOD = ?");
			pgqry.setParameter(++index, cn51CN66FilterVO.getOrgin()); 
		 }
		if(cn51CN66FilterVO.getDestination() != null && cn51CN66FilterVO.getDestination().length() > 0){
			pgqry.append(" AND CN66.DSTCOD = ?");
			pgqry.setParameter(++index, cn51CN66FilterVO.getDestination()); 
		 }
		if(cn51CN66FilterVO.getDsnNumber() != null && cn51CN66FilterVO.getDsnNumber().length() > 0){
			pgqry.append(" AND CN66.DSN = ?");
			pgqry.setParameter(++index, cn51CN66FilterVO.getDsnNumber()); 
		 }
		pgqry.append(" GROUP BY (CN66.ORGCOD || '-' || CN66.DSTCOD),CN66.SUBCLSGRP,MST.MALIDR,CN66.ORGCOD,CN66.DSTCOD,CN66.FLTDAT, CN66.MALSEQNUM");//modified by A-7371 as part of ICRD-259050 
		pgqry.append(" ORDER BY (CN66.ORGCOD || '-' || CN66.DSTCOD),CN66.SUBCLSGRP,CN66.FLTDAT");
		
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(cn51CN66FilterVO.getPageNumberCn66());
		
		
		
		
		
		
		
		
		
		
		
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
	//TODO Collection<GPABillingDetailsVO>
	public Collection<GPABillingDetailsVO> findGPABillingEntries(
			GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			throws PersistenceException,SystemException{
		String blgdtlQuery=getQueryManager().getNamedNativeQueryString(Find_GPABILLINGDETAILS);
		//Query query = new GPABillingDetailsFilterQuery(
		//		blgdtlQuery,  gpaBillingEntriesFilterVO );
		
		//modified by A-5175 for QF CR icrd-21098 starts
		
		PageableNativeQuery<DocumentBillingDetailsVO> query = new GPABillingDetailsFilterQuery(gpaBillingEntriesFilterVO.getTotalRecordCount(), new GPABillingDetailsMultiMapper(), blgdtlQuery, gpaBillingEntriesFilterVO);
		
		//modified  by A-5175 for QF CR icrd-21098 ends
		Query c66dtlQuery = getQueryManager().createNamedNativeQuery(Find_C66GPABILLINGDETAILS);
		Query ccadtlQuery = getQueryManager().createNamedNativeQuery(Find_CCAGPABILLINGDETAILS);
		StringBuilder sbul = new StringBuilder();
		sbul.append("'");
		sbul.append( gpaBillingEntriesFilterVO.getCompanyCode());
		sbul.append("'");
		if ( gpaBillingEntriesFilterVO.getFromDate() != null && gpaBillingEntriesFilterVO.getToDate() != null  ) {
			sbul.append( "AND TO_NUMBER(TO_CHAR(trunc(C66DTL.RCVDAT),'YYYYMMDD')) BETWEEN ");
			sbul.append(Integer.parseInt(gpaBillingEntriesFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
			sbul.append(" AND ");
			sbul.append(Integer.parseInt(gpaBillingEntriesFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
			sbul.append(" ");

		}
		/*if ( gpaBillingEntriesFilterVO.getToDate() != null  ) {
			sbul.append( " AND C66DTL.RCVDAT <= ");
			sbul.append("'");
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
			sb.append( "AND TO_NUMBER(TO_CHAR(trunc(MST.RCVDAT),'YYYYMMDD')) BETWEEN ");
			sb.append(Integer.parseInt(gpaBillingEntriesFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
			sb.append(" AND ");
			sb.append(Integer.parseInt(gpaBillingEntriesFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
			sb.append(" ");
			sb.append(" AND CCADTL.REVNETAMT<>0 ");//Added for ICRD-120502

		}
	/*	if ( gpaBillingEntriesFilterVO.getToDate() != null  ) {
			sb.append( " AND MST.RCVDAT <= ");
			sb.append("'");
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
		ccadtlQuery.append(sb.toString());
		log.log(Log.INFO, "ccadtlQuery -->>", ccadtlQuery);
		query.append("UNION ALL");
		query.append(ccadtlQuery.toString());
		query.append(")mst");
		query.append("GROUP BY ORG || DST,SUBCLSGRP,DAT,BLGBAS,CSGDOCNUM,CSGSEQNUM,POACOD,COD,INVNUM,DSN,RSN,CTGCOD )");
		query.append("ORDER BY ORG || DST,SUBCLSGRP,DAT,DSN,RSN,CTGCOD");
		
		log.log(Log.INFO, "final query -->>", query);
		//System.out.println("query"+query);
		return query.getResultList(new GPABillingDetailsMapper());

	}
	/**
	 * find Invoice Lov
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return Page<InvoiceLovVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<InvoiceLovVO> findInvoiceLov(InvoiceLovVO  invoiceLovVO)
	throws PersistenceException,SystemException {
		int pageNumber=invoiceLovVO.getPageNumber();
		int defaultPageSize=invoiceLovVO.getDefaultPageSize();
		defaultPageSize=10;
		if(invoiceLovVO.getDefaultPageSize()!=0){
		defaultPageSize=invoiceLovVO.getDefaultPageSize();
		}
		int totalRecords=invoiceLovVO.getTotalRecords();
		String baseQuery= getQueryManager().getNamedNativeQueryString(FIND_INVOICELOV);
		//Modified by A-5220 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_GPABLG_DENSE_RANK_QUERY);
		rankQuery.append("INVNUM ) AS RANK FROM ( ");
		rankQuery.append(baseQuery);
		PageableNativeQuery<InvoiceLovVO> pgqry=new InvoiceLovFilterQuery(defaultPageSize,totalRecords,
				new InvoiceLovMapper(),
				rankQuery.toString(),
				invoiceLovVO);
		pgqry.append(MRA_GPABLG_SUFFIX_QUERY);
		//Modified by A-5220 for ICRD-32647 ends
		return pgqry.getPage(pageNumber);
	}

	/**
	 * generateInvoice
	 * @author A-2270
	 * @param generateInvoiceFilterVO
	 * @return String
	 * @throws SystemException
	 */
	public String generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException {
		log.entering("CLASS_NAME", "generateInvoice");
		Procedure burstProcedure = getQueryManager()
		.createNamedNativeProcedure(GENERATE_INVOICE);
		int index = 0;

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
		log.log(Log.FINE, "Current Date: ", dateString);
		// currentDate.setDate(dateString, "dd-MMM-yyyy HH:mm");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		burstProcedure.setParameter(++index,generateInvoiceFilterVO.getCompanyCode());
		burstProcedure.setParameter(++index,generateInvoiceFilterVO.getGpaCode());
		burstProcedure.setParameter(++index,generateInvoiceFilterVO.getCountryCode());
		//burstProcedure.setParameter(++index,generateInvoiceFilterVO.getBillingPeriodFrom().toSqlDate());
		//burstProcedure.setParameter(++index,generateInvoiceFilterVO.getBillingPeriodTo().toSqlDate());
		burstProcedure.setParameter(++index,logonAttributes.getUserId());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		log.log(Log.FINE, "executed Procedure");
		String outParameter = (String) burstProcedure.getParameter(index);
		log.log(Log.FINE, "outParameter is ", outParameter);
		log.exiting("CLASS_NAME", "generateInvoice");
		return outParameter;
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.
	 *gpabilling.MRAGPABillingDAO#withdrawMailbags(java.util.Collection)
	 *	Added by 			:   A-6991 on 05-Sep-2017
	 * 	Used for 	  		:   ICRD-211662
	 *	Parameters			:	@param documentBillingDetailsVOs
	 *	Parameters			:	@throws SystemException
	 */
	public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
    		throws SystemException{
		log.entering("CLASS_NAME", "finalizeProformaInvoice");
		
		
      for(DocumentBillingDetailsVO documentBillingDetailsVO :documentBillingDetailsVOs){
		Procedure burstProcedure = getQueryManager()
		.createNamedNativeProcedure(WITHDRAW_MAILS);
    	  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
			log.log(Log.FINE, "Current Date:in defaultsDao ", dateString);
			log.log(Log.FINE, "cN51SummaryVO ",
					documentBillingDetailsVO);
			int index = 0;
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			burstProcedure.setParameter(++index,documentBillingDetailsVO.getCompanyCode());
			burstProcedure.setParameter(++index,documentBillingDetailsVO.getGpaCode());
			burstProcedure.setParameter(++index,documentBillingDetailsVO.getInvoiceNumber());
			burstProcedure.setParameter(++index,documentBillingDetailsVO.getMailSequenceNumber()); //modified by A-7371 as part of ICRD-259054
			burstProcedure.setParameter(++index,logonAttributes.getUserId());
			burstProcedure.setParameter(++index,logonAttributes.getAirportCode());
			burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);    
			
			
			burstProcedure.execute();
			String outParameter = (String) burstProcedure.getParameter(index);
			log.log(Log.FINE, "outParameter is ", outParameter);	
      	}
		checkforWithDrawInvoice(documentBillingDetailsVOs);

	}
	/**
	 * 
	 *	Overriding 
	 *  Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#finalizeProformaInvoice(java.util.Collection)
	 *	Added by 	:A-6991 on 19-Sep-2017
	 * 	Used for 	:ICRD-211662
	 *	Parameters	:@param summaryVOs
	 *	Parameters	:@throws SystemException
	 */
	public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
    		throws SystemException{
		log.entering("CLASS_NAME", "finalizeProformaInvoice");
    	  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
			log.log(Log.FINE, "Current Date:in defaultsDao ", dateString);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
      for(CN51SummaryVO cN51SummaryVO :summaryVOs){
    	  Procedure burstProcedure = getQueryManager()
    				.createNamedNativeProcedure(FINALIZE_INVOICE);
    	    int index = 0;
			burstProcedure.setParameter(++index,cN51SummaryVO.getCompanyCode());
			burstProcedure.setParameter(++index,cN51SummaryVO.getGpaCode());
			burstProcedure.setParameter(++index,cN51SummaryVO.getInvoiceNumber());   
			burstProcedure.setParameter(++index,logonAttributes.getUserId());
			burstProcedure.setParameter(++index,logonAttributes.getAirportCode());
			burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);    
			
			
			burstProcedure.execute();
			String outParameter = (String) burstProcedure.getParameter(index);
			log.log(Log.FINE, "outParameter is ", outParameter);	
      	}
     
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.
	 *MRAGPABillingDAO#withdrawInvoice(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO)
	 *	Added by 			: A-6991 on 19-Sep-2017
	 * 	Used for 	:       ICRD-211662
	 *	Parameters	:	@param summaryVO
	 *	Parameters	:	@throws SystemException
	 */
	public void withdrawInvoice(CN51SummaryVO summaryVO) throws SystemException{
		log.entering("CLASS_NAME", "finalizeProformaInvoice");
		Procedure burstProcedure = getQueryManager()
		.createNamedNativeProcedure(WITHDRAW_INVOICE);
		int index = 0;

    	  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
			String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
			log.log(Log.FINE, "Current Date:in defaultsDao ", dateString);
			log.log(Log.FINE, "cN51SummaryVO ",
					summaryVO);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			burstProcedure.setParameter(++index,summaryVO.getCompanyCode());
			burstProcedure.setParameter(++index,summaryVO.getGpaCode());
			burstProcedure.setParameter(++index,summaryVO.getInvoiceNumber());   
			burstProcedure.setParameter(++index,logonAttributes.getUserId());
			burstProcedure.setParameter(++index,logonAttributes.getAirportCode());
			burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			burstProcedure.setOutParameter(++index, SqlType.STRING);
			
			
			burstProcedure.execute();
			String outParameter = (String) burstProcedure.getParameter(index);
			log.log(Log.FINE, "outParameter is ", outParameter);	
      
     
	}
	/**    
	 * @author a-2270
	 * @param cN51CN66FilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  Collection<CN66DetailsVO> generateCN66Report(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException, SystemException {

		log.entering(CLASS_NAME,"generateCN66Report");
		Query query = null;
		if(cN51CN66FilterVO.getRebillInvNumber() == null)
			query = getQueryManager().createNamedNativeQuery(PRINT_CN66DETAILS);
		else
			query = getQueryManager().createNamedNativeQuery(PRINT_CN66DETAILS_REBILL);
		int index=0;
		
		query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
		
		if(cN51CN66FilterVO.getRebillInvNumber() == null){//ICRD-336231
		query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
		}

		if (cN51CN66FilterVO.getRebillInvNumber() == null) 
			query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
		else
			query.setParameter(++index,cN51CN66FilterVO.getRebillInvNumber());
		log.log(Log.INFO, "\n\n ***Final query-->", query);
		
		if(cN51CN66FilterVO.getRebillInvNumber()!=null && cN51CN66FilterVO.getGpaCode()!=null && cN51CN66FilterVO.getGpaCode().trim().length()>0){//ICRD-336231
			query.append(" AND RBL.GPACOD   = ? ");
			query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
		}

		List<CN66DetailsVO> cn66DetailVOs=query.getResultList(new CN66DetailsReportMapper());
		log.exiting(CLASS_NAME,"generateCN66Report");
		return cn66DetailVOs;

	}
	/**
	 * @author a-2270
	 * @param cN51CN66FilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 * 
	 */
	public  Collection<CN51DetailsVO> generateCN51Report(CN51CN66FilterVO cN51CN66FilterVO)	
	throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findCN51Details");
		Query query=getQueryManager().createNamedNativeQuery(FIND_CN51_DETAILS);
		int index=0;
		query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
		query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
		query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
		log.log(Log.INFO, "\n\n** Final Query->", query);
		List<CN51DetailsVO> cn51DetailVOs=query.getResultList(new CN51DetailsMapper());
		log.exiting(CLASS_NAME,"findCN51Details");
		return cn51DetailVOs;
	}
	/**
	 * @author a-2270
	 * @param cN51CN66FilterVO
	 * @return Collection
	 * @throws PersistenceException
	 * @throws SystemException
	 * 
	 */
	public  InvoiceDetailsReportVO generateInvoiceReport(CN51CN66FilterVO cN51CN66FilterVO)	
	throws PersistenceException, SystemException {
		InvoiceDetailsReportVO invoiceDetailsReportVO=null;
		log.entering(CLASS_NAME,"findCN51Details");
		Query query=getQueryManager().createNamedNativeQuery(FIND_INVOICEREPORT_DATA);
		int index=0;
		query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
		if(cN51CN66FilterVO.getRebillInvNumber()==null){
			query.append(" AND SMY.INVNUM = ? ");
			query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
		}else{
			query.append(" AND SMY.RBLINVNUM = ? ");
			query.setParameter(++index,cN51CN66FilterVO.getRebillInvNumber());
		}
		log.log(Log.INFO, "\n\n** Final Query->", query);
		//List<InvoiceDetailsReportVO> InvoiceDetailsReportVOs=query.getResultList(new CN51DetailsMapper());
		log.exiting(CLASS_NAME,"generateInvoiceReport");
		List<InvoiceDetailsReportVO> invoiceDetailsReportVOs = query.getResultList(new InvoiceDetailsReportMapper());
		if(invoiceDetailsReportVOs!=null && !invoiceDetailsReportVOs.isEmpty()){
			invoiceDetailsReportVO= invoiceDetailsReportVOs.get(0);
		}
		
		return invoiceDetailsReportVO;
	}
	/**
	 * @author A-2408
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<BillingSummaryDetailsVO> findBillingSummaryDetailsForPrint(BillingSummaryDetailsFilterVO filterVO)
	throws PersistenceException, SystemException{
		log.entering(CLASS_NAME,"findBillingSummaryDetails");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_BILLING_SMY_DETAILS);

		Query qry=new BillingSummaryDetailsFilterQuery(baseQuery,filterVO);

		log.exiting(CLASS_NAME,"findBillingSummaryDetails");

		return qry.getResultList(new BillingSummaryDetailsMapper());
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<CN51DetailsVO> findCN51DetailsForPrint(CN51DetailsPrintFilterVO filterVO)
	throws PersistenceException, SystemException{
		log.entering(CLASS_NAME,"findCN51DetailsForPrint");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_CN51_DETAILS_PRINT);

		Query qry=new CN51DetailsPrintFilterQuery(baseQuery,filterVO);
		log.exiting(CLASS_NAME,"findCN51DetailsForPrint");
		return qry.getResultList(new CN51DetailsPrintMapper());
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<CN66DetailsPrintVO> findCN66DetailsForPrint(CN66DetailsPrintFilterVO filterVO)
	throws PersistenceException, SystemException{
		log.entering(CLASS_NAME,"findCN66DetailsForPrint");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_CN66_DETAILS_PRINT);

		Query qry=new CN66DetailsPrintFilterQuery(baseQuery,filterVO);
		log.exiting(CLASS_NAME,"findCN66DetailsForPrint");
		return qry.getResultList(new CN66DetailsPrintMapper());
	}

	/**
	 * @author A-2280
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
/*	public Page<GPASettlementVO> findSettlementDetails(InvoiceSettlementFilterVO 
			invoiceSettlementFilterVO,int displayPage) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findSettlementDetails");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_CN51_SUMMARY_SETTLEMENT_DETAILS);
		Query qry=new CN51SettlementDetailsFilterQuery(baseQuery,invoiceSettlementFilterVO);   
		PageableQuery<GPASettlementVO> pgqry = new PageableQuery<GPASettlementVO>(
				qry, new GPABillingCN51SettlementDetailsMapper());

		log.exiting(CLASS_NAME,"findSettlementDetails");
		return pgqry.getPage(displayPage);

	}*/
	//added by A-7371
	public Page<GPASettlementVO> findSettlementDetails(InvoiceSettlementFilterVO 
			invoiceSettlementFilterVO,int displayPage) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findSettlementDetails");
		
		GPASettlementVO gpasettlementVO=null;
		 Page<InvoiceSettlementVO> invoiceVO =null;
		Page<GPASettlementVO> gpavo= new Page<GPASettlementVO>(new ArrayList<GPASettlementVO>(),0,0,0,0,0,false);
		Collection<GPASettlementVO> gpaVos= new ArrayList<GPASettlementVO>();

		Query qry = getQueryManager().createNamedNativeQuery(MRA_GPA_SETTLEMENT);   
		int index=0;
		qry.setParameter(++index, invoiceSettlementFilterVO.getCompanyCode());
		if(invoiceSettlementFilterVO.getGpaCode() != null) {
			qry.append(" AND C51.GPACOD  = ?");
		qry.setParameter(++index, invoiceSettlementFilterVO.getGpaCode());
		}
		if(invoiceSettlementFilterVO.getFromDate() != null) {
			qry.append(" AND C51.BLGPRDFRM >= ?");
			qry.setParameter(++index, invoiceSettlementFilterVO.getFromDate());
			}
		if(invoiceSettlementFilterVO.getToDate() != null) {
			qry.append(" AND C51.BLGPRDTOO <= ?");
			qry.setParameter(++index, invoiceSettlementFilterVO.getToDate());
			}
		
		if(invoiceSettlementFilterVO.getInvoiceNumber() !=null){
			qry.append("AND  c51.invnum = ?");
			qry.setParameter(++index, invoiceSettlementFilterVO.getInvoiceNumber());
		}
		if(invoiceSettlementFilterVO.getSettlementReferenceNumber()!=null){
			qry.append(" AND INV.STLREFNUM  = ?");
			qry.setParameter(++index, invoiceSettlementFilterVO.getSettlementReferenceNumber());
		}
		qry.append("ORDER BY C51.BLGPRDFRM");
		log.exiting(CLASS_NAME,"findUnSettledInvoicesForGPA");

		gpaVos= qry.getResultList(new GPASettlementMapper());
		gpavo.addAll(gpaVos);
		String query = getQueryManager().getNamedNativeQueryString(FIND_SETTLED_INVOICESLEVEL); 
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_GPABLG_DENSE_RANK_QUERY);
		rankQuery.append("STLREFNUM,INVNUM) AS RANK FROM(");
	    rankQuery.append(query);
	    PageableNativeQuery<InvoiceSettlementVO> pgqry = null;
	    pgqry = new PageableNativeQuery<InvoiceSettlementVO>(displayPage, rankQuery.toString(),
			new GPABillingCN51SettlementDetailsMapper());
		int index1=0;
		pgqry.setParameter(++index1, invoiceSettlementFilterVO.getCompanyCode());
		if(invoiceSettlementFilterVO.getGpaCode() != null) {
			pgqry.append(" AND INV.GPACOD  = ?");
			pgqry.setParameter(++index1, invoiceSettlementFilterVO.getGpaCode());
		}
		if(invoiceSettlementFilterVO.getSettlementReferenceNumber()!=null){
			pgqry.append(" AND INV.STLREFNUM  = ?");
			pgqry.setParameter(++index1, invoiceSettlementFilterVO.getSettlementReferenceNumber());
			
		}
		//pgqry.append("ORDER BY STLREFNUM,INVNUM");
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);	
		gpavo.iterator().next().setInvoiceSettlementVOsPage( pgqry.getPage(displayPage));
		return gpavo;

	}
	/**
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<InvoiceSettlementHistoryVO> findSettlementHistory(InvoiceSettlementFilterVO invoiceSettlementFilterVO) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findSettlementHistory");
		Query qry=getQueryManager().createNamedNativeQuery(FIND_SETTLEMENT_HISTORY);
		int index=0;
		qry.setParameter(++index,invoiceSettlementFilterVO.getCompanyCode());
		qry.setParameter(++index,invoiceSettlementFilterVO.getGpaCode());
		qry.setParameter(++index,invoiceSettlementFilterVO.getInvoiceNumber());
		log.log(Log.INFO, "\n\n Final query-->", qry);
		Collection<InvoiceSettlementHistoryVO> invoiceSettlementHistoryVOs= qry.getResultList(new GPABillingSettlementHistoryMapper());
		log.exiting(CLASS_NAME,"findSettlementHistory");
		return invoiceSettlementHistoryVOs;
	}
	/**
	 * Finds and returns the CN51s based on the filter criteria
	 *
	 * @param cn51SummaryFilterVO
	 * @return Collection<CN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public CN51SummaryVO findGpaBillingInvoiceEnquiryDetails(
			GpaBillingInvoiceEnquiryFilterVO gpaBillingInvoiceEnquiryFilterVO)
	throws PersistenceException,SystemException{
		log.entering(CLASS_NAME,"findGpaInvoices");

		Query query=getQueryManager().createNamedNativeQuery(FIND_GPABILLING_INVOICES);
		int index=0;
		CN51SummaryVO summaryVo= null;
		log.log(Log.INFO, "\n\n** gpaBillingInvoiceEnquiryFilterVO->",
				gpaBillingInvoiceEnquiryFilterVO);
		query.setParameter(++index,gpaBillingInvoiceEnquiryFilterVO.getCompanyCode());
		query.setParameter(++index,gpaBillingInvoiceEnquiryFilterVO.getInvoiceNumber());

		if(gpaBillingInvoiceEnquiryFilterVO.getGpaCode()!=null){
			query.append(" AND C51SMY.GPACOD = ? ");
			query.setParameter(++index,gpaBillingInvoiceEnquiryFilterVO.getGpaCode());
		}

		log.log(Log.INFO, "\n\n** Final Query->", query);
		List<CN51SummaryVO> cN51SummaryVOs = query.getResultList((new InvoiceEnquiryMapper()));
		if(cN51SummaryVOs!=null){
			summaryVo= cN51SummaryVOs.get(0);
		}
		log.log(Log.INFO, "\n\n** summaryVo123--->", summaryVo);
		log.exiting(CLASS_NAME,"findGpaInvoices");
		return summaryVo;
		//return query.getResultList((new InvoiceEnquiryMapper()));




	}
	/**
	 * @param proformaInvoiceDiffReportVO
	 * @return proformaInvoiceDiffReportVOs
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<ProformaInvoiceDiffReportVO> generateProformaInvoiceDiffReport(CN51SummaryVO cn51SummaryVO)
	throws PersistenceException, SystemException{
		log.entering(CLASS_NAME,"generateProformaInvoiceDiffReport");

		List<ProformaInvoiceDiffReportVO> proformaInvoiceDiffReportVOs = null;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_GPABILLING_PROFORMAINVOICEDIFF_PRINT);
		int index=0;
		qry.setParameter(++index,ProformaInvoiceDiffReportVO.FINALIZED_INVOICE);
		proformaInvoiceDiffReportVOs = qry.getResultList(new ProformaInvoiceDiffReportMultiMapper());
		log.log(Log.INFO, "proformaInvoiceDiffReportVOs &&&",
				proformaInvoiceDiffReportVOs);
		log.exiting(CLASS_NAME,"generateProformaInvoiceDiffReport");
		return proformaInvoiceDiffReportVOs;
	}


	/**
	 * Added by A-2565 Meenu for GPA billing accounting
	 * @param summaryVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Object triggerGPABillingAccounting(CN51SummaryVO summaryVO) throws PersistenceException, SystemException {
		log.entering("CLASS_NAME", "triggerGPABillingAccounting");
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure("mail.mra.gpabilling.triggerGPABillingAccounting");
		int index = 0;
		LocalDate currentDate = new LocalDate("***", Location.NONE, true);
		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		burstProcedure.setSensitivity(true);
		burstProcedure.setParameter(++index, summaryVO.getCompanyCode());
		burstProcedure.setParameter(++index, summaryVO.getGpaCode());
		burstProcedure.setParameter(++index, summaryVO.getInvoiceNumber());
		burstProcedure.setParameter(++index, MRA_DEFAULTS_EXCFLG);
		burstProcedure.setParameter(++index, logonAttributes.getUserId());
		burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();

		String outParameter = (String)burstProcedure.getParameter(index);
		log.log(Log.INFO,
				"FINAL triggerGPABillingAccounting  outParameter----------->>",
				outParameter);
		log.exiting("CLASS_NAME", "triggerGPABillingAccounting");
		return outParameter;
	}

	/**
	 * @author a-3447
	 * @param String companyCode
	 *@param int airlineIdentifier
	 * @return AirlineVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */

	public AirlineVO findAirlineAddress(String companyCode, int airlineIdentifier) throws PersistenceException, SystemException {
		log.entering("CLASS_NAME", "findAirlineAddress");			 
		Query query = getQueryManager().createNamedNativeQuery(MRA_GPABLG__FIND_AIRLINEDTLS);
		int index=0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, airlineIdentifier);
		log.log(Log.INFO, "Query--", query);
		AirlineVO airlineVO=query.getSingleResult(new AirlineAddressMapper());
		log.log(Log.INFO, "Vos Returned ", airlineVO);
		return airlineVO;

	}

	/**
	 * @author a-4823
	 * @param  gpaSettlementVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO) throws PersistenceException,
			SystemException {
		String invNumber=null;
		log.entering(CLASS_NAME,"findUnSettledInvoicesForGPA");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_UNSETTLED_INVOICES);   
		int index=0;
		if(gpaSettlementVO.getInvoiceSettlementVOs()!=null&& gpaSettlementVO.getInvoiceSettlementVOs().size()>0){
			invNumber = gpaSettlementVO.getInvoiceSettlementVOs().iterator().next().getInvoiceNumber();
		}

		qry.setParameter(++index, gpaSettlementVO.getCompanyCode());
		
		if(gpaSettlementVO.getGpaCode() != null) {
			qry.append(" AND C51.GPACOD  = ?");
		qry.setParameter(++index, gpaSettlementVO.getGpaCode());
		}
		if(gpaSettlementVO.getFromDate() != null) {
			qry.append(" AND C51.BLGPRDFRM >= ?");
			qry.setParameter(++index, gpaSettlementVO.getFromDate());
			}
		if(gpaSettlementVO.getToDate() != null) {
			qry.append(" AND C51.BLGPRDTOO <= ?");
			qry.setParameter(++index, gpaSettlementVO.getToDate());
			}
		
		if(invNumber !=null && invNumber.trim().length()>0){
			qry.append("AND  c51.invnum = ?");
			qry.setParameter(++index, invNumber);
		}
		qry.append("ORDER BY C51.BLGPRDFRM");
		log.exiting(CLASS_NAME,"findUnSettledInvoicesForGPA");

		return qry.getResultList(new GPABillingCN51InvoiceDetailsMapper());
	}
	/**
	 * @author a-4823
	 * @param settlementDetailsVO
	 * @return Collection<GPASettlementVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 * 
	 */
	public Collection<GPASettlementVO> findSettledInvoicesForGPA(
			SettlementDetailsVO settlementDetailsVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findSettledInvoicesForGPA");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_SETTLED_INVOICES);   
		int index=0;		
		qry.setParameter(++index, settlementDetailsVO.getCompanyCode());
		qry.setParameter(++index, settlementDetailsVO.getGpaCode());
		qry.setParameter(++index, settlementDetailsVO.getSettlementId());
		qry.setParameter(++index, settlementDetailsVO.getSettlementSequenceNumber());//Modified by A-8399 as part of ICRD-277609
		log.exiting(CLASS_NAME,"findSettledInvoicesForGPA");
		return qry.getResultList(new GPABillingCN51InvoiceDetailsMapper());
	}

	/**
	 * @author a-4823
	 * @param companyCode
	 * @param paCode
	 * @throws PersistenceException 
	 * @throws SystemException
	 */
	public String findSettlementCurrency(String companyCode, String paCode)
	throws PersistenceException, SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(FIND_SETTLEMENT_CURRENCY); 
		int index=0;
		qry.setParameter(++index, companyCode);
		qry.setParameter(++index, paCode);
		String settlementCurrencyCode="";
		return qry.getSingleResult(getStringMapper("STLCURCOD"));
	}

	/**
	 * @author a-4823
	 * @param filterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 * @return Collection<GPASettlementVO>
	 */
	public Collection<GPASettlementVO> generateSettlementDetails(
			InvoiceSettlementFilterVO filterVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"generateSettlementDetails");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_CN51_SUMMARY_SETTLEMENT_DETAILS);
		Query qry=new CN51SettlementDetailsFilterQuery(baseQuery,filterVO);  
		log.exiting(CLASS_NAME,"generateSettlementDetails");
		return qry.getResultList(new GPABillingCN51SettlementDetailsPrintMapper());


	}

	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws PersistenceException
	 *  
	 */
	public Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findSettlementDetails");
		//Added by A-4809 for BUG ICRD-18544..Starts
		Collection<InvoiceSettlementVO> invoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>();
		Collection<GPASettlementVO> gPASettlementVO = new ArrayList<GPASettlementVO>();
		Page<GPASettlementVO> gpaSettlemntVOsToList = new Page<GPASettlementVO>(new ArrayList<GPASettlementVO>(),0,0,0,0,0,false);
		Query query = getQueryManager().createNamedNativeQuery(FIND_GPA_UNSETLLED_INVOICES);
		//Added by A-4809 for BUG ICRD-18544..Ends
		int index=0;		
		query.setParameter(++index, gpaSettlementVO.getCompanyCode());
		query.setParameter(++index, gpaSettlementVO.getGpaCode());
		/*Query qry = getQueryManager().createNamedNativeQuery(FIND_UNSETTLED_INVOICES);   
		int i=0;		
		qry.setParameter(++i, gpaSettlementVO.getCompanyCode());
		qry.setParameter(++i, gpaSettlementVO.getGpaCode());*/
		//Added by A-4809 for BUG ICRD-18544..Starts
		gPASettlementVO =   query.getResultList(new GPASettlementMapper());
		int displayPage=gpaSettlementVO.getDisplayPage();
		String qry = getQueryManager().getNamedNativeQueryString(FIND_UNSETTLED_INVOICES); 
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_GPABLG_DENSE_RANK_QUERY);
		rankQuery.append("BLGPRDTOO,INVNUM) AS RANK FROM(");
	    rankQuery.append(qry);
	    PageableNativeQuery<InvoiceSettlementVO> pgqry = null;
	    pgqry = new PageableNativeQuery<InvoiceSettlementVO>(displayPage, rankQuery.toString(),
			new UnsettledInvoiceDetailsMapper());
		int i=0;
		pgqry.setParameter(++i, gpaSettlementVO.getCompanyCode());
		if(gpaSettlementVO.getGpaCode() != null) {
			pgqry.append(" AND C51.GPACOD  = ?");
		pgqry.setParameter(++i, gpaSettlementVO.getGpaCode());
		}
		
		//pgqry.append("ORDER BY BLGPRDTOO,INVNUM");
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);	
		gPASettlementVO.iterator().next().setInvoiceSettlementVOsPage( pgqry.getPage(displayPage));
		//invoiceSettlementVOs = qry.getResultList(new UnsettledInvoiceDetailsMapper());
		//gPASettlementVO.iterator().next().setInvoiceSettlementVOs(invoiceSettlementVOs);
		gpaSettlemntVOsToList.addAll(gPASettlementVO);
		/*PageableQuery<GPASettlementVO> pgqry = new PageableQuery<GPASettlementVO>(
				query, new GPASettlementMapper());*/
		//Added by A-4809 for BUG ICRD-18544..Ends
		log.exiting(CLASS_NAME,"findSettlementDetails"); 
		//return pgqry.getPage(gpaSettlementVO.getDisplayPage());
		return gpaSettlemntVOsToList; 
	}
	/**
	 * @author a-4823
	 * @param billingSummaryDetailsFilterVO
	 * @throws PersistenceException
	 */
	public Collection<POMailSummaryDetailsVO> findPOMailSummaryDetails(
			BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findPOMailSummaryDetails");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_POMAILSUMMARY);   
		Query qry=new POMailSummaryFilterQuery(baseQuery,billingSummaryDetailsFilterVO);
		log.exiting(CLASS_NAME, "findPOMailSummaryDetails");
		return qry.getResultList(new POMailSummaryMapper());
	}
	/**
	 * @author a-4823
	 * @param gpaSettlementVO
	 * @throws PersistenceException
	 * triggers settlement accounting
	 */
	public void triggerAccounting(GPASettlementVO gpaSettlementVO)
	throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "triggerAccounting");
		Boolean flag = true;
		String returnString = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String companyCode=logonAttributes.getCompanyCode();
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_GPABILLING_TRIGGERACCOUNTING);
		procedure.setSensitivity(true);
		int index = 0;		
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index, gpaSettlementVO.getGpaCode());
		procedure.setParameter(++index, gpaSettlementVO.getSettlementId());
		procedure.setParameter(++index, gpaSettlementVO.getSettlementSequenceNumber());
		procedure.setParameter(++index, "GS");
		procedure.setParameter(++index, "M");
		procedure.setParameter(++index, gpaSettlementVO.getInvoiceRefNumber());
		procedure.setParameter(++index, 0);
		procedure.setParameter(++index, gpaSettlementVO.getChequeNumber());
		procedure.setParameter(++index, gpaSettlementVO.getLastUpdatedTime());
		procedure.setParameter(++index, gpaSettlementVO.getLastUpdatedUser());
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
		log.log(Log.FINE, "----executed  Procedure----");
		returnString = (String) procedure.getParameter(index);
		log.log(Log.FINE, "---outParameter is -->", returnString);	
	}
	/**
	 * @author a-4823
	 * for finding outstanding cheque details for a GPA
	 */
	public Collection<SettlementDetailsVO> findOutStandingChequesForGPA(GPASettlementVO gpaSettlementVO) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME,"findOutStandingChequesForAgent");
		Query qry=getQueryManager().createNamedNativeQuery(FIND_OUTSTANDING);
		int index=0;
		qry.setParameter(++index,gpaSettlementVO.getCompanyCode());
		qry.setParameter(++index,gpaSettlementVO.getGpaCode());
		log.log(Log.INFO, "\n\n Final query-->", qry);
		Collection<SettlementDetailsVO> settlementDetailsVOs= qry.getResultList(new OutstandingDetailsMapper());
		log.exiting(CLASS_NAME,"findOutStandingChequesForAgent");
		return settlementDetailsVOs;
	}
	/**
	 * 
	 */ 
	public  InvoiceDetailsReportVO generateInvoiceReportTK(CN51CN66FilterVO cN51CN66FilterVO)	
	throws PersistenceException, SystemException {
		InvoiceDetailsReportVO invoiceDetailsReportVO=null; 
		log.entering(CLASS_NAME,"findCN51Details");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		Query query=null;
		if(cN51CN66FilterVO.getRebillInvNumber()==null){
		 query=getQueryManager().createNamedNativeQuery(FIND_INVOICEREPORTTK_DATA);
		}else{
		query=getQueryManager().createNamedNativeQuery(FIND_INVOICEREPORTTK_DATA_REBILL);	
		}
		int index=0;
		query.setParameter(++index,currentDate.toDisplayDateOnlyFormat());
		query.setParameter(++index,currentDate.toDisplayDateOnlyFormat());
		
		query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
		query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
		if(cN51CN66FilterVO.getRebillInvNumber()==null){
			query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
		}else{
			query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber().split("-")[0]);
		}
		log.log(Log.INFO, "\n\n** Final Query->", query);
		//List<InvoiceDetailsReportVO> InvoiceDetailsReportVOs=query.getResultList(new CN51DetailsMapper());
		log.exiting(CLASS_NAME,"generateInvoiceReport");
		List<InvoiceDetailsReportVO> invoiceDetailsReportVOs = query.getResultList(new GPAReportsMultiMapper());
		if(invoiceDetailsReportVOs!=null && !invoiceDetailsReportVOs.isEmpty()){
			invoiceDetailsReportVO= invoiceDetailsReportVOs.get(0);
		}
		log.log(Log.INFO, "\n\n*vos obatined >", invoiceDetailsReportVO);
		return invoiceDetailsReportVO;
	}
	/**
	 * @author A-10383
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	
	public  InvoiceDetailsReportVO generateInvoiceReportSQ(CN51CN66FilterVO cN51CN66FilterVO)throws PersistenceException, SystemException 
		{
			log.entering(CLASS_NAME,"SQfindCN51Details");
			InvoiceDetailsReportVO invoiceDetailsReportVO=null;
			Query query=getQueryManager().createNamedNativeQuery(FIND_INVOICEREPORT_DATA);
			int index=0;
			query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
			if(cN51CN66FilterVO.getRebillInvNumber()==null)
			{
				query.append(" AND SMY.INVNUM = ? ");
				query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
			}
			else
			{
				query.append(" AND SMY.RBLINVNUM = ? ");
				query.setParameter(++index,cN51CN66FilterVO.getRebillInvNumber());
			}
			log.log(Log.INFO, "\n\n** Final Query->", query);
			log.log(Log.INFO, "\n\n** sq FLOW->", query);
			List<InvoiceDetailsReportVO> invoiceDetailsReportVOs = query.getResultList(new InvoiceDetailsReportMapper());
			if(invoiceDetailsReportVOs!=null && !invoiceDetailsReportVOs.isEmpty())
			{
				invoiceDetailsReportVO= invoiceDetailsReportVOs.get(0);
			}
			log.exiting(CLASS_NAME,"generateInvoiceReport");
			log.log(Log.INFO, "\n\n*vos obatined >", invoiceDetailsReportVO);
			return invoiceDetailsReportVO;
		}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findMailDetailsforAudit(java.lang.String, java.lang.String)
	 *	Added by 			: a-4809 on Apr 2, 2014
	 * 	Used for 	:	to find mail details for audit 
	 * while invoice generation
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 */
	public Collection<MailDetailVO> findMailDetailsforAudit(
			String invoiceNumber, String companyCode) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "findMailDetailsforAudit");
		Collection<MailDetailVO> mailDetailVOs =null;
		Query query=getQueryManager().createNamedNativeQuery(FIND_MAILDETAILSFORAUDIT);
		int index=0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, invoiceNumber);
		mailDetailVOs = query.getResultList(new MailDetailsAuditMapper());
		log.log(Log.INFO, "\n\n*vos obatined >", mailDetailVOs);
		log.exiting(CLASS_NAME, "findMailDetailsforAudit");
		return mailDetailVOs;
	}

	/**
	 * Author : A-7794 on 28 Nov 2017
	 * 	Used for 	:	to find invoice details for settlement
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param billingToDate
	 *Parameters	:	@param accountNumber
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 * @throws SystemException, PersistenceException 
	 * 
	 */
	public CN51SummaryVO findGPADtlsForSAPSettlemntMail(String invoiceNumber,LocalDate billingPeriodTodate,String accountNumber) throws SystemException, PersistenceException{
		CN51SummaryVO summaryVO = new CN51SummaryVO();
		
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_GPABILLING_GETINVOICE_FOR_GPA);
		int index = 0;
		query.setParameter(++index, invoiceNumber);
		query.setParameter(++index, billingPeriodTodate);
		query.setParameter(++index, accountNumber);
		
		return query.getSingleResult(new Mapper<CN51SummaryVO>() {
					public CN51SummaryVO map(ResultSet result)throws SQLException{
						CN51SummaryVO summaryVo = new CN51SummaryVO();
						summaryVo.setInvoiceNumber(result.getString("INVNUM"));
						summaryVo.setGpaCode(result.getString("GPACOD"));
						summaryVo.setDueAirline(result.getString("DUEAMT"));
						Money totAmtInBillingCur = null;
						
						try {
							totAmtInBillingCur = CurrencyHelper.getMoney(result.getString("TOTAMTBLGCUR"));
						} catch (CurrencyException currencyException) {
					LogFactory.getLogger("MRA").log(Log.SEVERE, currencyException.toString());
						}
						summaryVo.setTotalAmountInBillingCurrency(totAmtInBillingCur);
						summaryVo.setBillingCurrencyCode(result.getString("BLGCURCOD"));
						summaryVo.setContractCurrencyCode(result.getString("CTRCURCOD"));
						summaryVo.setBillingStatus(result.getString("STLSTA"));
						summaryVo.setInvSerialNumber(result.getInt("INVSERNUM"));
						return summaryVo;
					}
				});
	}
	
	/**
	 * Author : A-7794 on 28 Nov 2017
	 * 	Used for 	:	to find latest invoice details for settlement
	 *	Parameters	:	@param invoiceNumber
	 *	Parameters	:	@param gpacode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 * @throws SystemException, PersistenceException 
	 * 
	 */
	public InvoiceSettlementVO findLatestSettlementForInvoice(String comapnyCode, String invoice, String gpaCode) throws PersistenceException,
	SystemException {
		InvoiceSettlementVO invoiceSettlVO = new InvoiceSettlementVO();
		log.entering(CLASS_NAME,"findLatestSettlementForInvoice");
		Query qry = getQueryManager().createNamedNativeQuery(MRA_GPABILLING_GETLATESTINVOICE_FOR_GPA);   
			int index=0;		
		
		qry.setParameter(++index, comapnyCode);
		qry.setParameter(++index, invoice);
		qry.setParameter(++index, gpaCode);
		log.exiting(CLASS_NAME,"findLatestSettlementtheInvoice");
		
		return qry.getSingleResult(new Mapper<InvoiceSettlementVO>() {
			public InvoiceSettlementVO map(ResultSet result)throws SQLException{
				InvoiceSettlementVO invoiceSettlVO = new InvoiceSettlementVO();
				invoiceSettlVO.setInvoiceNumber(result.getString("INVNUM"));
				invoiceSettlVO.setSettlementId(result.getString("STLREFNUM"));
				invoiceSettlVO.setSettlementSequenceNumber(Integer.parseInt(result.getString("SEQNUM")));
				invoiceSettlVO.setInvSerialNumber(Integer.parseInt(result.getString("SERNUM")));
				
				return invoiceSettlVO;
			}
		});
		
	}
	/**
	 * Author : A-7794 on 23 Jan 2018
	 * Parameters	:	@param gpaCode
	 * Parameters	:	@param setlmntRef
	 * @throws SystemException, PersistenceException 
	 * 
	 */
	@Override
	public GPASettlementVO findSettlementSeqNum(String gpaCode,
			String setlmntRef) throws PersistenceException, SystemException {
		
		GPASettlementVO settlement = new GPASettlementVO();
		log.entering(CLASS_NAME,"findSettlementSequenceNumber");
		
		Query qry = getQueryManager().createNamedNativeQuery(MRA_GPABILLING_STL_SEQNUM); 
		int index=0;		
		qry.setParameter(++index, gpaCode);
		qry.setParameter(++index, setlmntRef);
		log.exiting(CLASS_NAME,"findSettlementSequenceNumber");
		
		// TODO Auto-generated method stub
		return qry.getSingleResult(new Mapper<GPASettlementVO>() {
			public GPASettlementVO map(ResultSet result)throws SQLException{
				GPASettlementVO settlement = new GPASettlementVO();
				settlement.setSettlementSequenceNumber(result.getInt("SEQNUM"));
				return settlement;
			}
		});
	}
	
	/**    
	 * @author A-7794
	 * @param cN51CN66FilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public  Collection<CN66DetailsVO> generateCN66ReportForKE(CN51CN66FilterVO cN51CN66FilterVO)
	throws PersistenceException, SystemException {

		log.entering(CLASS_NAME,"generateCN66Report");
		Query query = getQueryManager().createNamedNativeQuery(PRINT_KE_CN66DETAILS);
		int index=0;
		query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
		query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
		query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
		log.log(Log.INFO, "\n\n ***Final query-->", query);
		List<CN66DetailsVO> cn66DetailVOs=query.getResultList(new CN66DetailsReportMapper());
		log.exiting(CLASS_NAME,"generateCN66Report");
		return cn66DetailVOs;

	}
	
	
	
	
	
	
	
	/**
	 * @author A-7371
	 * @param documentBillingDetailsVOs
	 * @throws SystemException 
	 * brought as part of ICRD-268803
	 */
	private void checkforWithDrawInvoice(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs) throws SystemException {
		
		if(documentBillingDetailsVOs!=null && documentBillingDetailsVOs.size()>0){
			String gpaCode=documentBillingDetailsVOs.iterator().next().getGpaCode();
			String companyCode=documentBillingDetailsVOs.iterator().next().getCompanyCode();
			String invoiceNumber=documentBillingDetailsVOs.iterator().next().getInvoiceNumber();
			
			Query qry = getQueryManager().createNamedNativeQuery(MRA_FINDCOUNT_EXISTING_PERFORMAMAILBAG); 
			int index=0;
			qry.setParameter(++index, companyCode);
			qry.setParameter(++index, gpaCode);
			qry.setParameter(++index, invoiceNumber);
			int numPcs = Integer.parseInt(qry.getSingleResult(getStringMapper("NUMPCS")));
			if(numPcs==0){
				CN51SummaryVO summaryVO=new CN51SummaryVO();
				        summaryVO.setCompanyCode(companyCode);
				        summaryVO.setGpaCode(gpaCode);
						summaryVO.setInvoiceNumber(invoiceNumber);
						withdrawInvoice(summaryVO);
			}
		
		}
		
		
		
	}
 /**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findSettlementInvoiceDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Collection<GPASettlementVO> findSettlementInvoiceDetails(
			InvoiceSettlementFilterVO filterVO) throws PersistenceException,
			SystemException {
		
		
		log.entering(CLASS_NAME,"findUnSettledInvoicesForGPA");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_SETTLEMENT_DETAILS);   
		int index=0;
		qry.setParameter(++index, filterVO.getCompanyCode());
		qry.setParameter(++index, filterVO.getInvoiceNumber());
		if(filterVO.getSettlementReferenceNumber()!=null){
			qry.append(" AND STL.STLREFNUM  = ?");
			qry.setParameter(++index, filterVO.getSettlementReferenceNumber());
		}
		log.exiting(CLASS_NAME,"findUnSettledInvoicesForGPA");
		List<GPASettlementVO> gpaSettlementVO=qry.getResultList(new GPASettlementMapper());
		return gpaSettlementVO;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findSettledMailbagDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Page<InvoiceSettlementVO> findSettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findSettledMailbagDetails");

		int pageNumber=filterVO.getPageNumber();
				int defaultPageSize=filterVO.getDefaultPageSize();
		defaultPageSize=10;
		if(filterVO.getDefaultPageSize()!=0){
		defaultPageSize=filterVO.getDefaultPageSize();
		}
		int totalRecords=filterVO.getTotalRecords();
		StringBuilder prefixQry = new StringBuilder(MRA_GPABLG_DENSE_RANK_QUERY);
		prefixQry.append("RCVDAT,MALSEQNUM,STLREFNUM ) AS RANK FROM ( ");
		String qry = null;
		
		//added by A-9477 for IASCB-94550 starts
		if (isOracleDataSource()) {
			qry = getQueryManager().getNamedNativeQueryString(FIND_SETTLED_MAILBAG_DETAILS_ORACLE);
		}else{
		 qry = getQueryManager().getNamedNativeQueryString(FIND_SETTLED_MAILBAG_DETAILS); 
		}
		
		//added by A-9477 for IASCB-94550 ends
		prefixQry.append(qry);
	PageableNativeQuery<InvoiceSettlementVO> pgqry = null;
	pgqry = new PageableNativeQuery<InvoiceSettlementVO>(defaultPageSize, totalRecords,prefixQry.toString(),
			new GPASettlementMailbagMapper());
		int index=0;
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getInvoiceNumber());
		if(filterVO.getGpaCode() != null) {
			pgqry.append(" AND INV.GPACOD  = ?");
			pgqry.setParameter(++index, filterVO.getGpaCode());
		}
		if(filterVO.getSettlementReferenceNumber() != null) {
			pgqry.append(" AND STL.STLREFNUM  = ?");
			pgqry.setParameter(++index, filterVO.getSettlementReferenceNumber());
		}
		if("SEARCH".equals(filterVO.getActionFlag())){
			if(filterVO.getMailbagID()!=null&&!"".equals(filterVO.getMailbagID())){
			pgqry.append("AND BLGMST.MALIDR=?");
			pgqry.setParameter(++index, filterVO.getMailbagID());
			}					
			if(filterVO.getSettlementStatus()!=null &&!"".equals(filterVO.getSettlementStatus()) ) {
			  pgqry.append("AND C66DTL.STLSTA=?");
			  pgqry.setParameter(++index, filterVO.getSettlementStatus());
			}
		}
		pgqry.append("ORDER BY STL.STLREFNUM,INVNUM,c66dtl.rcvdat");
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		log.exiting(CLASS_NAME,"findSettledMailbagDetails");

		 
		return pgqry.getPage(filterVO.getPageNumber());
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findUnsettledMailbagDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
	 *	Added by 			: A-7531 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Page<InvoiceSettlementVO> findUnsettledMailbagDetails(
			InvoiceSettlementFilterVO filterVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findUnsettledMailbagDetails");
		int pageNumber=filterVO.getPageNumber();
		int defaultPageSize;
		defaultPageSize=10;
		if(filterVO.getDefaultPageSize()!=0){
		defaultPageSize=filterVO.getDefaultPageSize();
		}
		int totalRecords=filterVO.getTotalRecords();
		StringBuilder prefixQry = new StringBuilder(MRA_GPABLG_DENSE_RANK_QUERY);
		prefixQry.append("RCVDAT,MALSEQNUM ) AS RANK FROM ( ");
		String qry = null;
		
if (isOracleDataSource()) {
			qry = getQueryManager().getNamedNativeQueryString(FIND_UNSETTLED_MAILBAG_DETAILS_ORACLE);
		}else{
		 qry = getQueryManager().getNamedNativeQueryString(FIND_UNSETTLED_MAILBAG_DETAILS); 
		}		
		prefixQry.append(qry);
	PageableNativeQuery<InvoiceSettlementVO> pgqry = null;
	pgqry = new PageableNativeQuery<InvoiceSettlementVO>(defaultPageSize,totalRecords, prefixQry.toString(),
			new GPASettlementMailbagMapper());
		int index=0;
		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index, filterVO.getInvoiceNumber());
		if(filterVO.getGpaCode() != null) {
			pgqry.append(" AND C51.GPACOD  = ?");
			pgqry.setParameter(++index, filterVO.getGpaCode());
		}
		if("SEARCH".equals(filterVO.getActionFlag())){
			if(filterVO.getMailbagID()!=null&&!"".equals(filterVO.getMailbagID())){
			pgqry.append("AND BLGMST.MALIDR=?");
			pgqry.setParameter(++index, filterVO.getMailbagID());
			}
			if(filterVO.getSettlementStatus()!=null && !"".equals(filterVO.getSettlementStatus())){
			pgqry.append("AND C66.STLSTA=?"); 
			pgqry.setParameter(++index, filterVO.getSettlementStatus());
			}
		}
		pgqry.append(" ORDER BY BLGMST.RCVDAT"); 
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		log.exiting(CLASS_NAME,"findUnsettledMailbagDetails");

		
		return pgqry.getPage(filterVO.getPageNumber());
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findMailbagSettlementHistory(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
	 *	Added by 			: A-7531 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceFiletrVO
	 *	Parameters	:	@return 
	 * @throws SystemException 
	 */
	@Override
	public Collection<InvoiceSettlementHistoryVO> findMailbagSettlementHistory(
			InvoiceSettlementFilterVO invoiceFiletrVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findMailbagSettlementHistory");
		Query query = getQueryManager().createNamedNativeQuery(FIND_SETTLED_MAILBAG_HISTORY_DETAILS);
		int index=0;
		query.setParameter(++index,invoiceFiletrVO.getCompanyCode());
		query.setParameter(++index,invoiceFiletrVO.getGpaCode());
		query.setParameter(++index,invoiceFiletrVO.getMailsequenceNum());
		log.log(Log.INFO, "\n\n ***Final query-->", query);
		List<InvoiceSettlementHistoryVO> invoiceSettlementHistoryVO=query.getResultList(new GPABillingSettlementHistoryMapper());
		log.exiting(CLASS_NAME,"findMailbagSettlementHistory");
		return invoiceSettlementHistoryVO;

	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findUnsettledInvoiceDetails(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO)
	 *	Added by 			: A-7531 on 26-Jun-2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Collection<GPASettlementVO> findUnsettledInvoiceDetails(
			InvoiceSettlementFilterVO filterVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME,"findUnsettledInvoiceDetails");
		Query qry = getQueryManager().createNamedNativeQuery(FIND_UNSETTLED_INVOICES);   
		int index=0;
		qry.setParameter(++index, filterVO.getCompanyCode());
		if(filterVO.getInvoiceNumber() != null) {
			qry.append(" AND C51.INVNUM  = ?");
			qry.setParameter(++index, filterVO.getInvoiceNumber());
		}
		
		if(filterVO.getGpaCode() != null) {
			qry.append(" AND C51.GPACOD  = ?");
		qry.setParameter(++index, filterVO.getGpaCode());
		}
		log.exiting(CLASS_NAME,"findUnsettledInvoiceDetails");
		List<GPASettlementVO> gpaSettlementVO=qry.getResultList(new GPASettlementMapper());
		return gpaSettlementVO;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#updateSettlementDetails(java.util.Collection)
	 *	Added by 			: A-7531 on 05-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param settlementDetailsVOs 
	 */
	@Override
	public void updateSettlementDetails( 
			Collection<SettlementDetailsVO> settlementDetailsVOs,String invnum,LocalDate stldate,String currency,String settlementId)throws PersistenceException,
			SystemException{
		  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, true);
		   for(SettlementDetailsVO settlementVO :settlementDetailsVOs){
		    	 
		    	  Procedure burstProcedure = getQueryManager()
		    				.createNamedNativeProcedure(MAILBAG_LEVEL_SETTLEMENT);
		    	  burstProcedure.setSensitivity(true);
		    	    int index = 0;
					String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
					log.log(Log.FINE, "Current Date:in defaultsDao ", dateString);
					log.log(Log.FINE, "cN51SummaryVO ",
							settlementDetailsVOs);
					LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
							.getLogonAttributesVO();
					burstProcedure.setParameter(++index,settlementVO.getCompanyCode());
					if(settlementVO.getSettlementId()!=null&& settlementVO.getSettlementId().trim().length()>0){  
					burstProcedure.setParameter(++index,settlementVO.getSettlementId());
					}
					else if(settlementId!=null){
						burstProcedure.setParameter(++index,settlementId);
					}
					burstProcedure.setParameter(++index,settlementVO.getGpaCode());   
					burstProcedure.setParameter(++index,settlementVO.getSettlementSequenceNumber());
					burstProcedure.setParameter(++index,settlementVO.getChequeAmount().getAmount());
					burstProcedure.setParameter(++index,settlementVO.getSerialNumber());
					if(settlementVO.getChequeBank()!=null) {
						  burstProcedure.setParameter(++index,settlementVO.getChequeBank());
						}else {
						        burstProcedure.setParameter(++index,"");
						      }
						if(settlementVO.getChequeBranch()!=null) {
						  burstProcedure.setParameter(++index,settlementVO.getChequeBranch());
						}else {
							    burstProcedure.setParameter(++index,"");
						      } 
					burstProcedure.setParameter(++index,settlementVO.getChequeNumber());
					burstProcedure.setParameter(++index,settlementVO.getIsDeleted());
					burstProcedure.setParameter(++index,settlementVO.getChequeDate());
					if(settlementVO.getRemarks()!=null){
					burstProcedure.setParameter(++index,settlementVO.getRemarks());
					}else
					{
						burstProcedure.setParameter(++index,"");
					}
					if(stldate!=null){
					burstProcedure.setParameter(++index,stldate);
					}else{
						burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
					}
					burstProcedure.setParameter(++index,currency);
					burstProcedure.setParameter(++index, settlementVO.getStlFlag());
					burstProcedure.setParameter(++index,invnum);
					burstProcedure.setParameter(++index,logonAttributes.getUserId());
					burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
					burstProcedure.setParameter(++index,logonAttributes.getAirportCode());
					burstProcedure.setParameter(++index,settlementVO.getFromScreen());
					burstProcedure.setOutParameter(++index, SqlType.STRING);    
					
					
					burstProcedure.execute();
					String outParameter = (String) burstProcedure.getParameter(index);
					log.log(Log.FINE, "outParameter is ", outParameter);	
		      	}
		
	}
	
	 /**
	 * Method to Getting Reminder List for GPA Billing. Added as a part of ICRD-234283
	 * @author A-5526
	 * @param reminderDetailsFilterVO
	 * @return 
	 * @throws SystemException
	 * @throws PersistenceException
	 */		
 public Page<ReminderDetailsVO> findReminderListForGpaBilling(
			ReminderDetailsFilterVO reminderDetailsFilterVO)
throws PersistenceException, SystemException {
		PageableNativeQuery<ReminderDetailsVO> pgqry = null;
		String query = getQueryManager().getNamedNativeQueryString(FIND_REMINDERDETAILS_GPABILLING);    
		String rebillQuery = getQueryManager().getNamedNativeQueryString(FIND_GPA_REBILL_DETAILS);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append("SELECT RESULT_TABLE.* ,ROW_NUMBER() OVER ( ORDER BY ");
		rankQuery.append("NULL ) AS RANK FROM (");
		
		if((reminderDetailsFilterVO.getReminderStatus() != null && reminderDetailsFilterVO.getReminderStatus().trim().length() > 0) || 
				(reminderDetailsFilterVO.getGpaRebillRound()!=null && reminderDetailsFilterVO.getGpaRebillRound().trim().length() >0)) 
			rankQuery.append(rebillQuery);
		else
			rankQuery.append(query);
		
		pgqry = new ReminderListFilterQuery(reminderDetailsFilterVO,
				rankQuery.toString(), null, new ReminderDetailsGpaBillingMapper());    
		
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		
		log.exiting(CLASS_NAME, "findReminderListForGpaBilling");      
		return pgqry.getPage(reminderDetailsFilterVO.getPageNumber());    
		
	}
 
 /**
  * 
  */
 public String generateGpaRebill(ReminderDetailsFilterVO reminderDetailsFilterVO)
		 throws PersistenceException, SystemException {
	 String outPar="";
	 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
	 Procedure burstProcedure = getQueryManager()
				.createNamedNativeProcedure(GPA_REBILL);
	    LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		int index = 0;
		burstProcedure.setParameter(++index,reminderDetailsFilterVO.getCompanyCode());
		burstProcedure.setParameter(++index,reminderDetailsFilterVO.getInvoiceNumber());
		burstProcedure.setParameter(++index,logonAttributes.getUserId());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setParameter(++index,logonAttributes.getStationCode());
		burstProcedure.setOutParameter(++index, SqlType.STRING);
		burstProcedure.execute();
		outPar = (String) burstProcedure.getParameter(index);
		return outPar;
 }



/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#processMraSettlementFromExcel(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
	 *	Added by 			: A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public String processMraSettlementFromExcel(FileUploadFilterVO filterVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "processMraSettlementFromExcel");
		String processStatus = null;
		if (filterVO != null) {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_SETTLEMENT_FILEUPLOAD); 
			procedure.setSensitivity(true);
			index++; procedure.setParameter(index, filterVO.getCompanyCode());
			index++; procedure.setParameter(index, filterVO.getFileType());
			index++; procedure.setParameter(index, "F");
			index++; procedure.setParameter(index, filterVO.getProcessIdentifier());  
			index++; procedure.setParameter(index, MRAConstantsVO.FLAG_NO);
			index++; procedure.setOutParameter(index, SqlType.STRING);
			index++; procedure.setOutParameter(index, SqlType.STRING);
			procedure.execute();
			processStatus = (String)procedure.getParameter(index);
		}
		log.log(Log.FINE, "ProcessStatus after executed Procedure -----> " + processStatus);
		this.log.exiting(CLASS_NAME, "processMraSettlementFromExcel");
		return processStatus;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#fetchDataForUpload(java.lang.String, java.lang.String)
	 *	Added by 			: A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Collection<InvoiceSettlementVO> fetchDataForUpload(String companyCode, String fileType)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "fetchDataForUpload");
		Collection<InvoiceSettlementVO > mailSettlementVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(
				FETCH_MRA_SETTLEMENT_DATAFORUPLOAD);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		query.setParameter(++idx, fileType);

		mailSettlementVOs = query
				.getResultList(new OfflineMailSettlementMapper());
		log.log(Log.INFO, "InvoiceSettlementVO  is from dao*****", mailSettlementVOs);
		return mailSettlementVOs;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#removeDataFromTempTable(com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO)
	 *	Added by 			: A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)
			throws PersistenceException, SystemException {
		String processStatus = null;
		if (fileUploadFilterVO != null)
		{int index = 0;
		Procedure procedure = getQueryManager().createNamedNativeProcedure(REMOVE_DATA_FROM_TEMPTABLE);
		index++; procedure.setParameter(index, fileUploadFilterVO.getCompanyCode());
		index++; procedure.setParameter(index, fileUploadFilterVO.getFileType());

		index++; procedure.setParameter(index,fileUploadFilterVO.getProcessIdentifier());
		index++; procedure.setParameter(index, MRAConstantsVO.FLAG_YES);
		index++; procedure.setOutParameter(index, SqlType.STRING);
		index++; procedure.setOutParameter(index, SqlType.STRING);
		procedure.execute();
		processStatus = (String)procedure.getParameter(index);
		}
		log.log(2, new Object[] {"ProcessStatus-->", processStatus });

	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#filenameValidation(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO)
	 *	Added by 			: A-7531 on 21-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public int filenameValidation(InvoiceSettlementVO invoiceVO) throws PersistenceException, SystemException {
		Query qry = getQueryManager().createNamedNativeQuery(MRA_SETTLEMENT_FILENAMEVALIDATION); 
		int index=0;
		qry.setParameter(++index, invoiceVO.getCompanyCode());
		qry.setParameter(++index, invoiceVO.getSettlementFileType());
		qry.setParameter(++index, invoiceVO.getSettlementFileName());
		int numPcs = qry.getSingleResult(getIntMapper("NUMPCS"));
		return numPcs;
	}
	
	/**
	 * 
	 */
	public Collection<RebillRemarksDetailVO> findGPARemarkDetails(ReminderDetailsFilterVO reminderListFilterVO)
			throws PersistenceException,SystemException{
		Query query = getQueryManager().createNamedNativeQuery(REMARK_DETAILS);
		 int index = 0;
		 query.setParameter(++index, reminderListFilterVO.getCompanyCode());
		 query.setParameter(++index, reminderListFilterVO.getMailbagID());
		 Collection<RebillRemarksDetailVO> remarkDetails =  query.getResultList(new MultiMapper<RebillRemarksDetailVO>() {

				public List<RebillRemarksDetailVO> map(ResultSet rs) throws SQLException {
					List<RebillRemarksDetailVO> rebillRemarksDetailVOs = new ArrayList<RebillRemarksDetailVO>();
					RebillRemarksDetailVO rebillRemarksDetailVO = null;
					while(rs.next()){
						rebillRemarksDetailVO=	new  RebillRemarksDetailVO();
						rebillRemarksDetailVO.setCompanyCode(rs.getString("CMPCOD"));
						rebillRemarksDetailVO.setMailSeqNum(rs.getLong("MALSEQNUM"));
						rebillRemarksDetailVO.setRemark(rs.getString("RMK"));
						rebillRemarksDetailVO.setRebillRound(rs.getInt("RBLRND"));
						rebillRemarksDetailVO.setRebillStatus("RBLSTA");
						rebillRemarksDetailVOs.add(rebillRemarksDetailVO) ;	
					}
					return rebillRemarksDetailVOs;
				}
			});
		 return remarkDetails;
	}
	/***
	 * @author A-7794
	 */
	@Override
	public void triggerGPASettlementAccounting(
			InvoiceSettlementVO invoiceStlmntVO,GPASettlementVO gpaSettlementVO) throws PersistenceException,
			SystemException {
		log.entering(CLASS_NAME, "triggerGPASettlementAccounting");
		String returnString = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode=logonAttributes.getCompanyCode();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_GPABILLING_TRIGGERACCOUNTING);
		procedure.setSensitivity(true);
		int index = 0;		
		procedure.setParameter(++index, companyCode);
		procedure.setParameter(++index, invoiceStlmntVO.getGpaCode());
		if(gpaSettlementVO.getSettlementId()!=null){
		procedure.setParameter(++index, gpaSettlementVO.getSettlementId());
		}
		else{
			procedure.setParameter(++index, " ");
		}
		procedure.setParameter(++index, gpaSettlementVO.getSettlementSequenceNumber());
		procedure.setParameter(++index, "GS");
		procedure.setParameter(++index, "M");
		procedure.setParameter(++index, invoiceStlmntVO.getInvoiceNumber());
		procedure.setParameter(++index, invoiceStlmntVO.getMailsequenceNum());
		if(gpaSettlementVO.getSettlementChequeNumber()!=null){
		procedure.setParameter(++index, gpaSettlementVO.getSettlementChequeNumber());
		}
		else{
			procedure.setParameter(++index, " ");
		}
		procedure.setParameter(++index, currentDate.toSqlTimeStamp());
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
		log.log(Log.FINE, "----executed  Procedure----");
		returnString = (String) procedure.getParameter(index);
		log.log(Log.FINE, "---outParameter is -->", returnString);	
	}

	/**
	 * find Invoice Lov
	 * @author A-2408
	 * @param invoiceLovVO
	 * @return Page<InvoiceLovVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public InvoiceLovVO findInvoiceNumber(InvoiceLovVO  invoiceLovVO)
	throws PersistenceException,SystemException {
		log.entering(CLASS_NAME, "findInvoiceNumber");
		Query query= getQueryManager().createNamedNativeQuery(FIND_INVOICELOV);
		int index = 0;
		query.setParameter(++index, invoiceLovVO.getCompanyCode());
		if(invoiceLovVO.getInvoiceNumber() != null && invoiceLovVO.getInvoiceNumber().length() >0) {
			query.append(" AND SMY.INVNUM = ?");
			query.setParameter(++index, invoiceLovVO.getInvoiceNumber());
		}
		return query.getSingleResult(new Mapper<InvoiceLovVO>() {
        	public InvoiceLovVO map(ResultSet rs) throws SQLException {
        		InvoiceLovVO invoiceLovVo=new InvoiceLovVO();
            	invoiceLovVo.setInvoiceNumber(rs.getString("INVNUM"));
            	invoiceLovVo.setGpaCode(rs.getString("GPACOD"));
            	invoiceLovVo.setBillingPeriod(rs.getString("BLDPRD"));
            	invoiceLovVo.setInvoiceStatus(rs.getString("INVSTA"));
        		return invoiceLovVo;
        	}
    });
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#findInvoicesforPASS(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO)
	 *	Added by 			: A-4809 on 10-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param passFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public Collection<GPAInvoiceVO> findInvoicesforPASS(GeneratePASSFilterVO passFilterVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "findInvoicesforPASS");
		String baseQuery= getQueryManager().getNamedNativeQueryString(FIND_INVOICEPASS);
		Query query=new FindPASSInvoicesFilterQuery(passFilterVO,baseQuery); 
		return query.getResultList(new Mapper<GPAInvoiceVO>() {
        	public GPAInvoiceVO map(ResultSet rs) throws SQLException {
        		GPAInvoiceVO gpaInvoiceVO=new GPAInvoiceVO();
        		gpaInvoiceVO.setCompanyCode(rs.getString("CMPCOD"));
        		gpaInvoiceVO.setPeriodNumber(rs.getString("PRDNUM"));
        		gpaInvoiceVO.setBranchOffice(rs.getString("BRHOFC"));
        		gpaInvoiceVO.setInterfacedFileName(rs.getString("INTFCDFILNAM"));
        		return gpaInvoiceVO;
        	}
	});
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO#getSequenceNumberforPASSFile(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO)
	 *	Added by 			: A-4809 on 16-Apr-2021
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 */
	public String getSequenceNumberforPASSFile(GPAInvoiceVO invoiceVO) throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "getSequenceNumberforPASSFile");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Query query= getQueryManager().createNamedNativeQuery(FIND_SEQNUMFORPASS);
		int index = 0;
		query.setParameter(++index, invoiceVO.getCompanyCode());
		query.setParameter(++index, invoiceVO.getPeriodNumber());
		query.setParameter(++index, "PASFIL");
		query.setParameter(++index, invoiceVO.getSequenceNumberIncrBy());
		query.setParameter(++index, logonAttributes.getUserId());
		query.setParameter(++index, currentDate.toSqlTimeStamp());
		
		String seqNum = query.getSingleResult(getStringMapper("SEQNUM"));
		if (seqNum.length() >= 4) {
			return seqNum;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < 4 - seqNum.length()) {
			sb.append('0');
		}
		sb.append(seqNum);
		return sb.toString();
		
	}

	@Override
	public Page<FileNameLovVO> findPASSFileNames(FileNameLovVO fileNameLovVO) throws SystemException {
		int pageSize= fileNameLovVO.getDefaultPageSize()!=0?fileNameLovVO.getDefaultPageSize():10;
		int recordCount = fileNameLovVO.getRecordCount();
		StringBuilder finalQuery = new StringBuilder(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
		String query =null;
		if (isOracleDataSource()) {
			query = getQueryManager().getNamedNativeQueryString(MAIL_MRA_GPABILLING_FIND_PASS_FILE_NAMES_ORACLE);
		} else {
			query = getQueryManager().getNamedNativeQueryString(MAIL_MRA_GPABILLING_FIND_PASS_FILE_NAMES);
		}
		finalQuery.append(query);
		PageableNativeQuery<FileNameLovVO> pageQuery = new PageableNativeQuery<>(pageSize,recordCount,finalQuery.toString(),new PASSFileNamesMapper());		
		int index = 0;
		pageQuery.setParameter(++index,fileNameLovVO.getCompanyCode());	
		pageQuery.append(" AND C51SMY.POATYP = ?");
		pageQuery.setParameter(++index, FileNameLovVO.PASS_PA_POATYP);
		if (Objects.nonNull(fileNameLovVO.getFromDate()) && !fileNameLovVO.getFromDate().trim().isEmpty()) {
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(C51SMY.BLGPRDFRM,'YYYYMMDD'))>=TO_NUMBER(TO_CHAR(TO_DATE(?),'YYYYMMDD')) ");
			pageQuery.setParameter(++index, fileNameLovVO.getFromDate());
		}
		if (Objects.nonNull(fileNameLovVO.getToDate()) && !fileNameLovVO.getToDate().trim().isEmpty()) {
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(C51SMY.BLGPRDTOO,'YYYYMMDD'))<=TO_NUMBER(TO_CHAR(TO_DATE(?),'YYYYMMDD'))");
			pageQuery.setParameter(++index, fileNameLovVO.getToDate());
		}
		if (Objects.nonNull(fileNameLovVO.getPeriodNumber()) && !fileNameLovVO.getPeriodNumber().trim().isEmpty()) {
			pageQuery.append(" AND C51SMY.PRDNUM = ? ");
			pageQuery.setParameter(++index, fileNameLovVO.getPeriodNumber());
		}
		if(fileNameLovVO.isDefaultList()){
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(C51SMY.BLGPRDFRM,'YYYYMMDD'))>=TO_NUMBER(TO_CHAR(CURRENT_DATE-90,'YYYYMMDD')) ");
			pageQuery.append(" AND TO_NUMBER(TO_CHAR(C51SMY.BLGPRDFRM,'YYYYMMDD'))<=TO_NUMBER(TO_CHAR(CURRENT_DATE,'YYYYMMDD'))");
		}
		if (isOracleDataSource()) {
			pageQuery.append(") SELECT DISTINCT BLGPRDFRM, BLGPRDTOO, PRDNUM, TRIM(REGEXP_SUBSTR(INTFCDFILNAM, '[^,]+', 1, LEVEL)) INTFCDFILNAM FROM TBL CONNECT BY LEVEL <= REGEXP_COUNT(INTFCDFILNAM, ',')+1");
		}
		pageQuery.append(" ORDER BY BLGPRDFRM DESC, BLGPRDTOO DESC");
		pageQuery.append(MRA_DEFAULTS_SUFFIX_QUERY);
		return pageQuery.getPage(fileNameLovVO.getPageNumber());
		 	}
	/**
	 * @author A-10383
	 * @param cN51CN66FilterVO
	 * @return
	 * @throws SystemException
	 */
	public  Collection<CN51DetailsVO> generateCN51ReportSQ(CN51CN66FilterVO cN51CN66FilterVO)	
			throws PersistenceException, SystemException {
		//for sq flow
				Query query=getQueryManager().createNamedNativeQuery(FIND_CN51_DETAILS);
				int index=0;
				query.setParameter(++index,cN51CN66FilterVO.getCompanyCode());
				query.setParameter(++index,cN51CN66FilterVO.getGpaCode());
				query.setParameter(++index,cN51CN66FilterVO.getInvoiceNumber());
				log.log(Log.INFO, "\n\n** Final Query->", query);
				List<CN51DetailsVO> cn51DetailVOs=query.getResultList(new CN51DetailsMapper());
				log.exiting(CLASS_NAME,"findCN51Details");
				return cn51DetailVOs;
			}
	
}
