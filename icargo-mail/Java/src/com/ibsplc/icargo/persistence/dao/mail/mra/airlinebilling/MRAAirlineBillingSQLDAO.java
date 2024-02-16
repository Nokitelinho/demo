/*
 * MRAAirlineBillingSQLDAO.java created on Feb 16, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;


import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ArlInvoiceDetailsReportVO;
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
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


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


/**
 * @author A-2524
 * 
 */
public class MRAAirlineBillingSQLDAO extends AbstractQueryDAO implements
		MRAAirlineBillingDAO {

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "MRAAirlineBillingSQLDAO";
	
	private static final String FIND_ARLAUDIT="mailtracking.mra.airlinebilling.findArlAudit";

	private static final String MRA_AIRLINEBILLING_ACCTTXNIDR_PROC = "mailtracking.mra.airlinebilling.findAcctTxnIdr";

	private static final String MRA_AIRLINEBILLING_REJECTIONFINDBLGCURCOD = "mailtracking.mra.airlinebilling.findBlgCurCode";

	private static final String MRA_AIRLINEBILLING_REJECTIONFINDEXGRAT = "mailtracking.mra.airlinebilling.findExgRate";

	private static final String MRA_AIRLINEBILLING_REJECTIONMEMODETAIL = "mailtracking.mra.airlinebilling.findRejectionMemoDetail";

	private static final String MRA_AIRLINEBILLING_REJECTIONMEMODETAILS = "mailtracking.mra.airlinebilling.findMemoDetails";

	private static final String MRA_AIRLINEBILLING_DISPLAY_UPUCALENDAR_DETAILS = "mra.airlinebilling.defaults.displayUPUCalendarDetails";

	private static final String MRA_AIRLINEBILLING_FIND_EXCEPTIONININVOICES_DETAILS = "mra.airlinebilling.findExceptionInInvoice";

	private static final String MRA_AIRLINEBILLING_FIND_AIRLINEEXCEPTION_DETAILS = "mailtracking.mra.airlinebilling.displayexceptiondetails";

	/**
	 * query string used in the method findCN51Details
	 */
	private static final String MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51DETAILS = "mailTracking.mra.airlineBilling.findCN51Details";

	private static final String MRA_AIRLINEBILLING_FIND_INVOICE_DETAILS = "mailtracking.mra.airlinebilling.defaults.findInvoiceDetails";

	private static final String MRA_AIRLINEBILLING_FIND_MEMO_DETAILS = "mailtracking.mra.airlinebilling.defaults.findMemoDetails";

	private static final String MRA_AIRLINEBILLING_PROCESS_MAIL_PROC = "mailtracking.mra.airlinebilling.defaults.processmail";

	private static final String MRA_AIRLINEBILLING_GENINV_PROC = "mailtracking.mra.airlinebilling.outward.generateinvoice";

	private static final String MRA_AIRLINEBILLING_FIND_AIRLINEEXCEPTION_DETAILS_EXP = "mailtracking.mra.airlinebilling.displayexceptiondetailsininvoice";

	private static final String MRA_AIRLINEBILLING_FIND_INVOICERPTDETAILS = "mailtracking.mra.airlinebilling.findoutwardinvoicereportdetails";

	private static final String MRA_AIRLINEBILLING_RPT_INV = "mailtracking.mra.airlinebilling.reports.invoicedetails";

	private static final String MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51S = "mailtracking.mra.airlinebilling.findCN51s";

	private static final String MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51DETAILSPRINT = "mailtracking.mra.airlinebilling.inward.findinvoicedetailsbyclearancePrd";
	private static final String MRA_AIRLINEBILLING_OUTWARD_FINDFORMONEDETAILS = "mra.airlinebilling.outward.findFormOneDetails";
	// Added for Outward Rejection Memo Report

	private static final String MRA_AIRLINEBILLING_OUTWARDREJECTIONMEMODETAILS = "mailtracking.mra.airlinebilling.findOutwardRejectionMemo";
	private static final String MRA_AIRLINEBILLING_FINDFORMONES = "mra.airlinebilling.findformones";
	private static final String MRA_AIRLINEBILLING_FINDAIRLINEDETAILS =

		"mra.airlinebilling.findairlinedetails";
	
	private static final String MRA_DEFAULTS_FINDFORMONEDTLS = "mailtracking.mra.airlinebilling.findFormOneDetails";
	
	private static final String MRA_AIRLINEBILLING_FINDFORMTHREEDETAILS="mailtracking.mra.airlinebilling.findFormThreeDetails";
	
	private static final String MRA_AIRLINEBILLING_FINDSERNUM="mailtracking.mra.airlinebilling.findSerialNumber";
	
	private static final String MRA_ARLBLG_SAVEFORMTHREEDETAILS ="mailtracking.mra.airlinebilling.saveformthreedetails";
	
	/**
	 * Capture Invoice Screen
	 */
	private static final String MRA_AIRLINEBILLING_INWARD_CAPTUREINVOICE="mra.airlinebilling.inward.captureinvoice";
	
	private static final String MAILTRACKING_MRA_AIRLINEBILLING_CAPTURECN51DETAILS = "mailTracking.mra.airlineBilling.captureCN51Details";
	
	private static final String FIND_INVOICEREPORT_DATA = "mailtracking.mra.airlinebilling.findinvoicereportdata";
	
	private static final String MRA_ARLBLG_FIND_AIRLINEDTLS = "mailtracking.mra.airlinebilling.findairlineaddress";
	
	private static final String ACCOUNTING_STATUS = "A";
	
	private static final String INTERLINE_BLILLINGTYPE = "I";
	
	private static final String MRA_ARLBLG_ACCEPT_ACCOUNTING = "mailtracking.mra.airlinebilling.triggeracceptbuttonAccounting";
	
	//Added by A-5280 for ICRD-32647  to enable Last Link in Pagination to START
    private static final String MAILTRACKING_MRA_AIRLINEBILLING_DENSE_RANK_QUERY=
    	"SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    private static final String MAILTRACKING_MRA_AIRLINEBILLING_SUFFIX_QUERY=") RESULT_TABLE";
    private static final String MAILTRACKING_MRA_AIRLINEBILLING_ROWNUM_QUERY=
    	"SELECT RESULT_TABLE.*,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM (";
    private static final String MRA_DEFAULTS_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	private static final String MRA_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
   //Added by A-5280 for ICRD-32647 to enable Last Link in Pagination to end
	private static final String MRA_ARLBLG_GENERATE_MRA_MISC_FILE = "mail.mra.airlinebilling.outwardfilegeneration";
  
	private static final String MRA_ARLBLG_WITHDRAWMAILBAG="mailtracking.mra.airlinebilling.withdrawMailBag";
	private static final String MRA_ARLBLG_FIND_CN66DTLS_FORSTATUSCHANGE="mailtracking.mra.airlinebilling.findCN66DetailsVOsForStatusChange";
	
	
	private static final String SIS_SUPPORTING_DOC_DOWNLOAD = "mail.mra.airlinebilling.downloadAttachment";
	  
	  
	private static final String FIND_SUPPORTING_DOCS = "mail.mra.airlinebilling.findSupportingDocs";
	private static final String MRA_SISBILLING_SUPPORTINGDOCUMENT_DOCSERIALNUMBER = "mail.mra.airlinebilling.docserialnumber";
	private static final String DATE = "yyyyMMdd" ;
	
	
	/**
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws SystemException
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws SystemException {

		int index = 0;
		log.entering("MRAAirlineBillingSQLDAO", "displayUPUCalendarDetails");
		List<UPUCalendarVO> upuCalendarVOs = null;
		Query basequery = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_DISPLAY_UPUCALENDAR_DETAILS);

		log.log(Log.INFO, "@@@@@@@@@@@@ the filter VO for listing is ",
				upuCalendarFilterVO.toString());
		log.log(Log.INFO, "@@@@@@@@@@@@ the base query for listing is ",
				basequery);
		basequery.setParameter(++index, upuCalendarFilterVO.getCompanyCode());

		if (upuCalendarFilterVO.getClearancePeriod() != null) {

			String queryString = new StringBuffer(" AND CLRPRD LIKE '").append(
					upuCalendarFilterVO.getClearancePeriod().trim().replace(
							'*', '%')).append("'").toString();
			basequery.append(queryString);
		}

		if (upuCalendarFilterVO.getFromDate() != null) {
			basequery.append(" AND FRMDAT >= ? ");
			basequery.setParameter(++index, upuCalendarFilterVO.getFromDate());
		}
		if (upuCalendarFilterVO.getToDate() != null) {
			basequery.append(" AND TOODAT <= ? ");
			basequery.setParameter(++index, upuCalendarFilterVO.getToDate());
		}

		upuCalendarVOs = basequery
				.getResultList(new UPUCalenderDetailsMapper());

		if (upuCalendarVOs != null && upuCalendarVOs.size() > 0) {
			log
					.log(
							Log.INFO,
							"@@@@@@@@@@@@@@@@@@@@@@ RESULT OBTAINED FROM SERVER @@@@@@@@@@@ ",
							upuCalendarVOs);
			return upuCalendarVOs;
		} else {
			log
					.log(
							Log.INFO,
							"@@@@@@@@@@@@@@@@@@@@@@ RESULT OBTAINED FROM SERVER NULL @@@@@@@@@@@ ",
							upuCalendarVOs);
		}
		log.exiting("MRAAirlineBillingSQLDAO", "displayUPUCalendarDetails");

		return null;

	}

	/**
	 * Method to list CN66 details
	 * 
	 * @param cn66FilterVo
	 * @return Collection<AirlineCN66DetailsVO>
	 * @throws SystemException
	 */
	public Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		Query query = null;
		String baseQuery = null;
		baseQuery = getQueryManager().getNamedNativeQueryString(
				MRAAirlineBillingPersistenceConstants.FIND_CN66DETAILS);
		query = new CN66DetailsFilterQuery(baseQuery, cn66FilterVo);
		/* For balancing CN51 and CN66 - sensitivity is set to true */
		if (query != null) {
			query.setSensitivity(true);
		}
		
		log.exiting(CLASS_NAME, "findCN66Details");
		
		PageableQuery<AirlineCN66DetailsVO> pgQuery = new PageableQuery<AirlineCN66DetailsVO>(
				query, new AirlineCN66DetailsMapper());

		return pgQuery.getPage(cn66FilterVo.getPageNumber());
	}
	/**
	 * Method to list CN66 details
	 * 
	 * @param cn66FilterVo
	 * @return Collection<AirlineCN66DetailsVO>
	 * @throws SystemException
	 */
	public Collection<AirlineCN66DetailsVO> findCN66DetailsVOCollection(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		Query query = null;
		String baseQuery = null;
		baseQuery = getQueryManager().getNamedNativeQueryString(
				MRAAirlineBillingPersistenceConstants.FIND_CN66DETAILS);
		query = new CN66DetailsFilterQuery(baseQuery, cn66FilterVo);
		/* For balancing CN51 and CN66 - sensitivity is set to true */
		if (query != null) {
			query.setSensitivity(true);
		}
		Collection<AirlineCN66DetailsVO> airlineCn66DetailsVos = query
				.getResultList(new AirlineCN66DetailsMapper());
		log.exiting(CLASS_NAME, "findCN66Details");
		return airlineCn66DetailsVos;
		
}


	/**
	 * Method to list CN66 details
	 * 
	 * @param exceptionInInvoiceFilterVO
	 * @return Collection<ExceptionInInvoiceVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
			throws SystemException, PersistenceException {
		
		
		Query query = null;
		String qry = getQueryManager().getNamedNativeQueryString(
				MRA_AIRLINEBILLING_FIND_EXCEPTIONININVOICES_DETAILS);
		query = new ExceptionInInvoiceFilterQuery(
				qry,exceptionInInvoiceFilterVO);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
	rankQuery.append(query);
	PageableNativeQuery<ExceptionInInvoiceVO> pgqry = null;
	pgqry = new PageableNativeQuery<ExceptionInInvoiceVO>(0, rankQuery.toString(),
				new ExceptionInInvoiceMapper());
	int index = 0;
	
	 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getCompanyCode());
	 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getCompanyCode());
	 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getAirlineCode());
	 if(exceptionInInvoiceFilterVO.getExceptionStatus()!=null)
	 {
	 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getExceptionStatus());
	 }
	 if ( exceptionInInvoiceFilterVO.getInvoiceNumber() != null && exceptionInInvoiceFilterVO.getInvoiceNumber().trim().length() > 0  ) {
		 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getInvoiceNumber()); 
	 }
	 if ( exceptionInInvoiceFilterVO.getClearancePeriod() != null && exceptionInInvoiceFilterVO.getClearancePeriod().trim().length() > 0  ) {
		 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getClearancePeriod()); 
	 }
	 if ( exceptionInInvoiceFilterVO.getMemoCode() != null && exceptionInInvoiceFilterVO.getMemoCode().trim().length() > 0  ) {
		 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getMemoCode());	 
	 }
	 if ( exceptionInInvoiceFilterVO.getMemoStatus() != null && exceptionInInvoiceFilterVO.getMemoStatus().trim().length() > 0  ) {
		 pgqry.setParameter(++index,exceptionInInvoiceFilterVO.getMemoStatus()); 
	 }
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		
		return pgqry.getPage(exceptionInInvoiceFilterVO.getPageNumber());	
		

	}
	/**
	 * Method to print AirlineExceptionInvoice details
	 * 
	 * @param exceptionInInvoiceFilterVO
	 * @return Collection<ExceptionInInvoiceVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ExceptionInInvoiceVO> findAirlineExceptionInInvoicesForReport(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findAirlineExceptions");
		Query query = null;
		// String baseQuery = null;
		query = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FIND_EXCEPTIONININVOICES_DETAILS);// MRAAirlineBillingPersistenceConstants.FIND_);
		// query = new AirlineExceptionsFilterQuery(baseQuery,
		// airlineExceptionsFilterVO);
		int queryIndex = 0;
		query.setParameter(++queryIndex, exceptionInInvoiceFilterVO
				.getCompanyCode());
		query.setParameter(++queryIndex, exceptionInInvoiceFilterVO
				.getCompanyCode());
		if(exceptionInInvoiceFilterVO.getAirlineCode()!=null)
		{ query.append(" AND EXP.ARLCOD >= ?");
			query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getAirlineCode());	
		}
		if(exceptionInInvoiceFilterVO.getExceptionStatus()!=null)
		 {query.append(" AND EXP.EXPSTA >= ?");
			query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getExceptionStatus());
		 }
		 if ( exceptionInInvoiceFilterVO.getInvoiceNumber() != null && exceptionInInvoiceFilterVO.getInvoiceNumber().trim().length() > 0  ) {
			 query.append(" AND EXP.INVNUM >= ?");
			 query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getInvoiceNumber()); 
		 }
		 if ( exceptionInInvoiceFilterVO.getClearancePeriod() != null && exceptionInInvoiceFilterVO.getClearancePeriod().trim().length() > 0  ) {
			 query.append(" AND EXP.CLRPRD >= ?");
			 query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getClearancePeriod()); 
		 }
		 if ( exceptionInInvoiceFilterVO.getMemoCode() != null && exceptionInInvoiceFilterVO.getMemoCode().trim().length() > 0  ) {
			 query.append(" AND EXP.MEMCOD >= ?");
			 query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getMemoCode());	 
		 }
		 if ( exceptionInInvoiceFilterVO.getMemoStatus() != null && exceptionInInvoiceFilterVO.getMemoStatus().trim().length() > 0  ) {
			 query.append(" AND EXP.MEMSTA >= ?");
			 query.setParameter(++queryIndex,exceptionInInvoiceFilterVO.getMemoStatus()); 
		 }
		
		log.log(Log.FINE, "", query);
		List<ExceptionInInvoiceVO> airlineExceptionsVOs = query
				.getResultList(new ExceptionInInvoiceMapper());
		log.exiting(CLASS_NAME, "findAirlineExceptions");
		if (airlineExceptionsVOs != null) {
			log.log(Log.FINE, "\nSize  of coll obtaiiend----->",
					airlineExceptionsVOs.size());
		}

		return airlineExceptionsVOs;
	}
	

	/**
	 * @param airlineExceptionsFilterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @throws SystemException
	 */
	public Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		
		
		
		  final String BLANK = "";
		Query query = null;
		String qry = getQueryManager().getNamedNativeQueryString(
				MRA_AIRLINEBILLING_FIND_AIRLINEEXCEPTION_DETAILS);
		query = new AirlineExceptionsFilterQuery(
				qry,airlineExceptionsFilterVO);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);
	rankQuery.append(query);
	PageableNativeQuery<AirlineExceptionsVO> pgqry = null;
	pgqry = new PageableNativeQuery<AirlineExceptionsVO>(0, rankQuery.toString(),
				new AirlineExceptionsMapper());
	int index = 0;
	
	
	log.log(Log.FINE, "airlineExceptionsFilterVO in sql dao before setting parameter",airlineExceptionsFilterVO); 
	
   pgqry.setParameter(++index,airlineExceptionsFilterVO.getCompanyCode());
   pgqry.setParameter(++index,Integer.parseInt(airlineExceptionsFilterVO.getFromDate().toStringFormat(DATE).substring(0, 8)));
   pgqry.setParameter(++index,Integer.parseInt(airlineExceptionsFilterVO.getToDate().toStringFormat(DATE).substring(0, 8)));
   pgqry.setParameter(++index,airlineExceptionsFilterVO.getAirlineCode());
   
   
   if(airlineExceptionsFilterVO.getOriginOfficeOfExchange() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getOriginOfficeOfExchange())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getOriginOfficeOfExchange());
	}
	if(airlineExceptionsFilterVO.getDestinationOfficeOfExchange() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getDestinationOfficeOfExchange())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getDestinationOfficeOfExchange());
	}
	if(airlineExceptionsFilterVO.getMailCategory() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getMailCategory())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getMailCategory());
	}
	if(airlineExceptionsFilterVO.getSubClass() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getSubClass())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getSubClass());
	}
	if(airlineExceptionsFilterVO.getYear() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getYear())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getYear());
	}
	if(airlineExceptionsFilterVO.getReceptacleSerialNumber() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getReceptacleSerialNumber())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getReceptacleSerialNumber());
	}
	if(airlineExceptionsFilterVO.getHighestNumberIndicator() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getHighestNumberIndicator())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getHighestNumberIndicator());
	}
	if(airlineExceptionsFilterVO.getRegisteredIndicator() != null &&
			!BLANK.equals(airlineExceptionsFilterVO.getRegisteredIndicator())){
		
		
		pgqry.setParameter(++index,airlineExceptionsFilterVO.getRegisteredIndicator());
	}
	
   
   
   
   
 if(airlineExceptionsFilterVO.getInvoiceRefNumber()!=null)
 {
	 pgqry.setParameter(++index,airlineExceptionsFilterVO.getInvoiceRefNumber()); 
 }
 if(airlineExceptionsFilterVO.getDespatchSerNo() != null){
	 pgqry.setParameter(++index,airlineExceptionsFilterVO.getDespatchSerNo());  
 }
 if(airlineExceptionsFilterVO.getExceptionCode() != null){
	 pgqry.setParameter(++index,airlineExceptionsFilterVO.getExceptionCode());   
 }
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);
		
		return pgqry.getPage(airlineExceptionsFilterVO.getPageNumber());
		
	}

	/**
	 * @param airlineExceptionsFilterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @throws SystemException
	 */
	public Collection<AirlineExceptionsVO> findAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findAirlineExceptions");
		Query query = null;
		// String baseQuery = null;
		query = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FIND_AIRLINEEXCEPTION_DETAILS_EXP);// MRAAirlineBillingPersistenceConstants.FIND_);
		// query = new AirlineExceptionsFilterQuery(baseQuery,
		// airlineExceptionsFilterVO);
		int queryIndex = 0;
		query.setParameter(++queryIndex, airlineExceptionsFilterVO
				.getCompanyCode());
		query.setParameter(++queryIndex, airlineExceptionsFilterVO
				.getAirlineIdentifier());
		query.setParameter(++queryIndex, airlineExceptionsFilterVO
				.getInvoiceRefNumber());
		query.setParameter(++queryIndex, airlineExceptionsFilterVO
				.getClearancePeriod());
		log.log(Log.FINE, "", query);
		List<AirlineExceptionsVO> airlineExceptionsVOs = query
				.getResultList(new AirlineExceptionsMapper());
		log.exiting(CLASS_NAME, "findAirlineExceptions");
		if (airlineExceptionsVOs != null) {
			log.log(Log.FINE, "\nSize  of coll obtaiiend----->",
					airlineExceptionsVOs.size());
		}

		return airlineExceptionsVOs;
	}

	/**
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * 
	 */
	public AirlineCN51SummaryVO findCN51Details(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findCN51Details");
		log.log(Log.INFO, "FilterVo from SQLDao", cn51FilterVO);
		AirlineCN51SummaryVO summaryVO = null;

		String invoiceRefNum = cn51FilterVO.getInvoiceReferenceNumber();
		String categoryCode = cn51FilterVO.getCategoryCode();
		String carriageFromStation = cn51FilterVO.getCarriageStationFrom();
		String carriageToStation = cn51FilterVO.getCarriageStationTo();
		String iataClearancePrd = cn51FilterVO.getIataClearancePeriod();

		Query query = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51DETAILS);
		int queryIndex = 0;
		query.setParameter(++queryIndex, cn51FilterVO.getCompanyCode());
		query.setParameter(++queryIndex, cn51FilterVO.getAirlineIdentifier());
		query
				.setParameter(++queryIndex, cn51FilterVO
						.getInterlineBillingType());
		query.setParameter(++queryIndex, invoiceRefNum);

		if (iataClearancePrd != null && iataClearancePrd.length() > 0) {
			query.append(" AND ARLCN51SMY.CLRPRD = ? ");
			query.setParameter(++queryIndex, iataClearancePrd);
		}
		if (categoryCode != null && categoryCode.length() > 0) {
			query.append(" AND ARLCN51DTL.MALCTGCOD = ? ");
			query.setParameter(++queryIndex, categoryCode);
		}
		if (carriageFromStation != null && carriageFromStation.length() > 0) {
			query.append(" AND ARLCN51DTL.CARFRM = ? ");
			query.setParameter(++queryIndex, carriageFromStation);
		}
		if (carriageToStation != null && carriageToStation.length() > 0) {
			query.append(" AND ARLCN51DTL.CARTOO = ? ");
			query.setParameter(++queryIndex, carriageToStation);
		}
		
		List<AirlineCN51SummaryVO> summaryVOsFromRSet = query
		.getResultList(new ListCN51DetailsMapper());
		
		
		if (summaryVOsFromRSet != null && summaryVOsFromRSet.size() > 0) {
			log.log(Log.INFO, " ##### No of Summary VOs ", summaryVOsFromRSet.size());
			summaryVO = summaryVOsFromRSet.get(0);
		}
		Query qry = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_CAPTURECN51DETAILS);
		int index = 0;
		qry.setParameter(++index, cn51FilterVO.getCompanyCode());
		qry.setParameter(++index, cn51FilterVO.getAirlineIdentifier());
		qry
				.setParameter(++index, cn51FilterVO
						.getInterlineBillingType());
		qry.setParameter(++index, invoiceRefNum);
		if (iataClearancePrd != null && iataClearancePrd.length() > 0) {
			qry.append(" AND ARLCN51DTL.CLRPRD = ? ");
			qry.setParameter(++index, iataClearancePrd);
		}
		if (categoryCode != null && categoryCode.length() > 0) {
			qry.append(" AND ARLCN51DTL.MALCTGCOD = ? ");
			qry.setParameter(++index, categoryCode);
		}
		if (carriageFromStation != null && carriageFromStation.length() > 0) {
			qry.append(" AND ARLCN51DTL.CARFRM = ? ");
			qry.setParameter(++index, carriageFromStation);
		}
		if (carriageToStation != null && carriageToStation.length() > 0) {
			qry.append(" AND ARLCN51DTL.CARTOO = ? ");
			qry.setParameter(++index, carriageToStation);
		}
		log.log(Log.INFO, " ###### qry for execution from sqldao===>>>>", qry.toString());
		PageableQuery<AirlineCN51DetailsVO> pgqry = null;
		pgqry = new PageableQuery<AirlineCN51DetailsVO>(qry, new CaptureCN51Mapper());
		Page<AirlineCN51DetailsVO> airlineCN51DetailsVO = pgqry.getPage(cn51FilterVO.getPageNumber());
		log.log(Log.INFO, " ###### Query for execution ", query.toString());
		if(summaryVO!=null){
		summaryVO.setCn51DetailsPageVOs(airlineCN51DetailsVO);
		}
		log.log(Log.INFO, " ###### qry for execution from sqldao43667===>>>>",
				qry.toString());
		log.log(Log.INFO, "Summary VO from SQL DAO====>>>", summaryVO);
		return summaryVO;

	}
	
	/**
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * 
	 */
	public AirlineCN51SummaryVO findCN51DetailColection(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, PersistenceException {
		log.entering(CLASS_NAME, "findCN51Details");
		log.log(Log.INFO, "FilterVo from SQLDao", cn51FilterVO);
		AirlineCN51SummaryVO summaryVO = null;

		String invoiceRefNum = cn51FilterVO.getInvoiceReferenceNumber();
		String categoryCode = cn51FilterVO.getCategoryCode();
		String carriageFromStation = cn51FilterVO.getCarriageStationFrom();
		String carriageToStation = cn51FilterVO.getCarriageStationTo();
		String iataClearancePrd = cn51FilterVO.getIataClearancePeriod();

		Query query = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51DETAILS);
		int queryIndex = 0;
		query.setParameter(++queryIndex, cn51FilterVO.getCompanyCode());
		query.setParameter(++queryIndex, cn51FilterVO.getAirlineIdentifier());
		query
				.setParameter(++queryIndex, cn51FilterVO
						.getInterlineBillingType());
		query.setParameter(++queryIndex, invoiceRefNum);

		if (iataClearancePrd != null && iataClearancePrd.length() > 0) {
			query.append(" AND ARLCN51SMY.CLRPRD = ? ");
			query.setParameter(++queryIndex, iataClearancePrd);
		}
		if (categoryCode != null && categoryCode.length() > 0) {
			query.append(" AND ARLCN51DTL.MALCTGCOD = ? ");
			query.setParameter(++queryIndex, categoryCode);
		}
		if (carriageFromStation != null && carriageFromStation.length() > 0) {
			query.append(" AND ARLCN51DTL.CARFRM = ? ");
			query.setParameter(++queryIndex, carriageFromStation);
		}
		if (carriageToStation != null && carriageToStation.length() > 0) {
			query.append(" AND ARLCN51DTL.CARTOO = ? ");
			query.setParameter(++queryIndex, carriageToStation);
		}
		
		List<AirlineCN51SummaryVO> summaryVOsFromRSet = query
		.getResultList(new ListCN51DetailsMapper());
		
		
		if (summaryVOsFromRSet != null && summaryVOsFromRSet.size() > 0) {
			log.log(Log.INFO, " ##### No of Summary VOs ", summaryVOsFromRSet.size());
			summaryVO = summaryVOsFromRSet.get(0);
		}
		Query qry = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_CAPTURECN51DETAILS);
		int index = 0;
		qry.setParameter(++index, cn51FilterVO.getCompanyCode());
		qry.setParameter(++index, cn51FilterVO.getAirlineIdentifier());
		qry
				.setParameter(++index, cn51FilterVO
						.getInterlineBillingType());
		qry.setParameter(++index, invoiceRefNum);
		if (iataClearancePrd != null && iataClearancePrd.length() > 0) {
			qry.append(" AND ARLCN51DTL.CLRPRD = ? ");
			qry.setParameter(++index, iataClearancePrd);
		}
		if (categoryCode != null && categoryCode.length() > 0) {
			qry.append(" AND ARLCN51DTL.MALCTGCOD = ? ");
			qry.setParameter(++index, categoryCode);
		}
		if (carriageFromStation != null && carriageFromStation.length() > 0) {
			qry.append(" AND ARLCN51DTL.CARFRM = ? ");
			qry.setParameter(++index, carriageFromStation);
		}
		if (carriageToStation != null && carriageToStation.length() > 0) {
			qry.append(" AND ARLCN51DTL.CARTOO = ? ");
			qry.setParameter(++index, carriageToStation);
		}
		log.log(Log.INFO, " ###### qry for execution from sqldao===>>>>", qry.toString());
		Collection<AirlineCN51DetailsVO> airlineCN51DtlsVOs = qry
		.getResultList(new CaptureCN51Mapper());
	    log.log(Log.FINE, "airlineCN51DtlsVOs from SQL Dao==>>>",
				airlineCN51DtlsVOs);
		if(summaryVO!=null){
		summaryVO.setCn51DetailsVOs(airlineCN51DtlsVOs);
	    }
		log.log(Log.INFO, " ##airlineCN51DtlsVOs from sqldao1234===>>>>",
				airlineCN51DtlsVOs);
		log.log(Log.INFO, "Summary VO from SQL DAO====>>>", summaryVO);
		return summaryVO;

	}
	
	/**
	 * @param invoiceLovFilterVO
	 * @return Page<AirlineInvoiceLovVO>
	 * @throws SystemException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws SystemException {

		int index = 0;
		log.entering("MRAAirlineBillingSQLDAO", "displayInvoiceLOV");

		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<AirlineInvoiceLovVO> pgqry = null;
		String basequery = null;
    	
		basequery = getQueryManager().getNamedNativeQueryString(MRA_AIRLINEBILLING_FIND_INVOICE_DETAILS);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_MRA_AIRLINEBILLING_ROWNUM_QUERY);
		rankQuery.append(basequery);
		// Added by A-5280 for ICRD-32647 ends
		pgqry = new PageableNativeQuery<AirlineInvoiceLovVO>(0,
				rankQuery.toString() , new InvoiceLOVMapper());
		
		pgqry.setParameter(++index, invoiceLovFilterVO.getCompanycode());

		if (invoiceLovFilterVO.getInvoicenumber() != null) {
			String queryString = new StringBuffer(" AND INV.INVNUM LIKE '")
					.append(
							invoiceLovFilterVO.getInvoicenumber().trim()
									.replace('*', '%')).append('%').append("'").toString();
			pgqry.append(queryString);
		}

		if (invoiceLovFilterVO.getInterlinebillingtype() != null) {

			String queryString = new StringBuffer(" AND INV.INTBLGTYP LIKE '")
					.append(
							invoiceLovFilterVO.getInterlinebillingtype().trim()
									.replace('*', '%')).append('%').append("'").toString();
			pgqry.append(queryString);
		}

		if (invoiceLovFilterVO.getClearanceperiod() != null) {

			String queryString = new StringBuffer(" AND INV.CLRPRD LIKE '")
					.append(
							invoiceLovFilterVO.getClearanceperiod().trim()
									.replace('*', '%')).append('%').append("'").toString();
			pgqry.append(queryString);
		}

		if (invoiceLovFilterVO.getAirlineCode() != null) {

			String queryString = new StringBuffer(" AND INV.ARLCOD LIKE '")
					.append(
							invoiceLovFilterVO.getAirlineCode().trim().replace(
									'*', '%')).append('%').append("'").toString();
			pgqry.append(queryString);
		}
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(MAILTRACKING_MRA_AIRLINEBILLING_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends

		return pgqry.getPage(invoiceLovFilterVO.getPageNumber());
	}

	/**
	 * 
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MemoLovVO> displayMemoLOV(String companyCode, String memoCode,
			int pageNumber) throws SystemException {

		int index = 0;
		log.entering("MRAAirlineBillingSQLDAO", "displayMemoLOV");

		// Modified by A-5280 for ICRD-32647
		PageableNativeQuery<MemoLovVO> pgqry = null;
		String basequery = null;
   		basequery = getQueryManager().getNamedNativeQueryString(MRA_AIRLINEBILLING_FIND_MEMO_DETAILS);
    	// Added by A-5280 for ICRD-32647 starts
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MAILTRACKING_MRA_AIRLINEBILLING_ROWNUM_QUERY);
		rankQuery.append(basequery);
		// Added by A-5280 for ICRD-32647 ends
		pgqry = new PageableNativeQuery<MemoLovVO>(0,
				rankQuery.toString(), new MemoLOVMapper());
		
		pgqry.setParameter(++index, companyCode);

		if (memoCode != null && memoCode.trim().length() > 0) {

			String queryString = new StringBuffer(" AND EXPDTL.MEMCOD LIKE '")
					.append(memoCode.trim().replace('*', '%')).append('%').append("'")
					.toString();

			pgqry.append(queryString);
		}
		// Added by A-5280 for ICRD-32647 starts
		pgqry.append(MAILTRACKING_MRA_AIRLINEBILLING_SUFFIX_QUERY);
		// Added by A-5280 for ICRD-32647 ends


		return pgqry.getPage(pageNumber);

	}

	/**
	 * Added By A-2397
	 * 
	 * @param memoFilterVo
	 * @return Collection<MemoInInvoiceVO>
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
			throws SystemException {
		log.entering(CLASS_NAME, "findMemoDetails");
		log.log(Log.INFO, "memoFilterVo", memoFilterVo);
		Collection<MemoInInvoiceVO> memoInInvoices = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_REJECTIONMEMODETAILS);
		qry.setParameter(++index, memoFilterVo.getCompanyCode());
		qry.setParameter(++index, memoFilterVo.getAirlineIdentifier());
		

		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		log.exiting(CLASS_NAME, "findMemoDetails");
		memoInInvoices = qry.getResultList(new MRAMemoDetailsMapper());
		log.log(Log.INFO, "return collection ", memoInInvoices);
		return memoInInvoices;

	}

	/**
	 * @author A-2408
	 * @param cn66FilterVo
	 * @throws SystemException
	 */
	public String processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
			throws SystemException {
		log.entering(CLASS_NAME, "processMail");
		Procedure processProcedure = getQueryManager()
				.createNamedNativeProcedure(
						MRA_AIRLINEBILLING_PROCESS_MAIL_PROC);
		processProcedure.setSensitivity(true);
		int index = 0;
		// String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy
		// HH:mm");
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		log.log(Log.FINE, "Current Date: ", currentDate);
		processProcedure.setParameter(++index, cn66FilterVo.getCompanyCode());
		processProcedure.setParameter(++index, cn66FilterVo.getAirlineId());
		processProcedure.setParameter(++index, cn66FilterVo
				.getClearancePeriod());
		processProcedure.setParameter(++index, cn66FilterVo
				.getInvoiceRefNumber() );
		
		processProcedure.setParameter(++index, cn66FilterVo
				.getLastUpdatedUser());
		processProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		processProcedure.setParameter(++index, logonAttributes.getAirportCode());
		processProcedure.setOutParameter(++index, SqlType.STRING);
		processProcedure.execute();
		log.log(Log.FINE, "executed Procedure");

		String outParameter = (String) processProcedure.getParameter(8);
		log.log(Log.FINE, "outParameter is ", outParameter);
		log.exiting(CLASS_NAME, "processMail");

		return outParameter;

	}

	/**
	 * This method printExceptionReportDetail for print
	 * 
	 * @author a-2391
	 * @param airlineExceptionsFilterVO
	 * @throws SystemException
	 * @return Collection<AirlineExceptionsVO>
	 */
	public Collection<AirlineExceptionsVO> printExceptionReportDetail(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		log.entering("MRAAIRLINEBILLINGSqlDAO", "printExceptionReportDetail");

		Query query = null;
		String baseQuery = null;
		baseQuery = getQueryManager().getNamedNativeQueryString(
				MRA_AIRLINEBILLING_FIND_AIRLINEEXCEPTION_DETAILS);// MRAAirlineBillingPersistenceConstants.FIND_);
		query = new AirlineExceptionsFilterQuery(baseQuery,
				airlineExceptionsFilterVO);
		log.log(Log.FINE, "", query);
		List<AirlineExceptionsVO> airlineExceptionsVOs = query
				.getResultList(new AirlineExceptionsMapper());

		log.exiting("MRAAIRLINEBILLINGSqlDAO", "printExceptionReportDetail");
		return airlineExceptionsVOs;
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

		log.entering("MRAAIRLINEBILLINGSqlDAO", "generateInvoice");

		String outPut = null;
		String temp = null;
		int index = 0;
		Procedure generateInvoice = getQueryManager()
				.createNamedNativeProcedure(MRA_AIRLINEBILLING_GENINV_PROC);
		generateInvoice.setSensitivity(true);

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		generateInvoice.setParameter(++index, invoiceFilterVO.getCompanycode());
		generateInvoice.setParameter(++index, invoiceFilterVO.getAirlineidr());
		generateInvoice.setParameter(++index, invoiceFilterVO
				.getClearanceperiod());
		generateInvoice.setParameter(++index, currentDate.toSqlTimeStamp());
		generateInvoice.setParameter(++index, logonAttributes.getUserId());
		generateInvoice.setParameter(++index, currentDate.toSqlTimeStamp());
		generateInvoice.setOutParameter(++index, SqlType.STRING);

		generateInvoice.execute();
		outPut = (String) generateInvoice.getParameter(index);

		log.exiting("MRAAIRLINEBILLINGSqlDAO", "generateInvoice");
		return outPut;
	}

	/**
	 * 
	 * @param airlineCN51FilterVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<AirlineInvoiceReportVO> findInvoiceDetailsForReport(
			AirlineCN51FilterVO airlineCN51FilterVO)
			throws PersistenceException, SystemException {
		log.entering(CLASS_NAME, "findInvoiceDetailsForReport");
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FIND_INVOICERPTDETAILS);
		int index = 0;
		query.setParameter(++index, airlineCN51FilterVO.getCompanyCode());
		query.setParameter(++index, airlineCN51FilterVO
				.getIataClearancePeriod());

		if (airlineCN51FilterVO.getInvoiceReferenceNumber() != null) {
			query.append(" AND SMY.INVNUM = ? ");
			query.setParameter(++index, airlineCN51FilterVO
					.getInvoiceReferenceNumber());
		}
		if (airlineCN51FilterVO.getInterlineBillingType() != null) {
			query.append(" AND   SMY.INTBLGTYP = ? ");
			query.setParameter(++index, airlineCN51FilterVO
					.getInterlineBillingType());
		}
		if (airlineCN51FilterVO.getAirlineCode() != null) {
			query.append(" AND   SMY.ARLIDR = ? ");
			query.setParameter(++index, airlineCN51FilterVO
					.getAirlineIdentifier());
		}
		log.log(Log.INFO, "\n\n** Final Query->", query);
		log.exiting(CLASS_NAME, "findInvoiceDetailsForReport");
		return query.getResultList(new InvoiceDetailsForReportMultiMapper());
		// return null;

	}

	/**
	 * @param filterVO
	 * @return Collection<AirlineExceptionsVO>
	 * @throws SystemException
	 */
	public Collection<AirlineCN51SummaryVO> generateInvoiceReports(
			AirlineCN51FilterVO filterVO) throws SystemException {

		log.entering(CLASS_NAME, "generateInvoiceReports");

		Query query = null;
		String baseQuery = null;

		baseQuery = getQueryManager().getNamedNativeQueryString(
				MRA_AIRLINEBILLING_RPT_INV);// MRAAirlineBillingPersistenceConstants.FIND_);
		query = new InvoiceSummaryFilterQuery(baseQuery, filterVO);

		Collection<AirlineCN51SummaryVO> airlineCN51SummaryVOs = query
				.getResultList(new InvoiceSummaryMapper());

		log.exiting(CLASS_NAME, "generateInvoiceReports");

		return airlineCN51SummaryVOs;
	}

	/**
	 * @author a-2049
	 * @param filterVO
	 * @return Collection<AirlineCN51SummaryVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<AirlineCN51SummaryVO> findCN51s(
		
			AirlineCN51FilterVO filterVO) throws PersistenceException,
			SystemException {
		
		
		
		Query query = null;

		query = this.getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51S);
		StringBuilder rankQuery = new StringBuilder();
		rankQuery.append(MRA_DEFAULTS_ROWNUM_RANK_QUERY);

		int index = 0;
		query.setParameter(++index, filterVO.getCompanyCode());
		query.setParameter(++index,
				String.valueOf(filterVO.getBilledDateFrom().toSqlDate()));
		query.setParameter(++index,
				String.valueOf(filterVO.getBilledDateTo().toSqlDate()));

		if (filterVO.getInterlineBillingType() != null
				&& filterVO.getInterlineBillingType().length() > 0) {
			query.append(" AND INTBLGTYP = ? ");
			query.setParameter(++index, filterVO.getInterlineBillingType());
		}
		if (filterVO.getAirlineCode() != null
				&& filterVO.getAirlineCode().length() > 0) {
			query.append(" AND ARLCOD = ? ");
			query.setParameter(++index, filterVO.getAirlineCode());
		}
		//added by A-7929 as part of icrd-265471
		if (filterVO.getBillingType() != null
				&& filterVO.getBillingType().length() > 0) {
			query.append(" AND INTINVTYP = ? ");
			query.setParameter(++index, filterVO.getBillingType());
		}
		if (filterVO.getInvoiceNo() != null
				&& filterVO.getInvoiceNo().length() > 0) {
			query.append(" AND INVNUM = ? ");
			query.setParameter(++index, filterVO.getInvoiceNo());
		}
		
		
		rankQuery.append(query);
		PageableNativeQuery<AirlineCN51SummaryVO> pgqry = null;
		pgqry = new PageableNativeQuery<AirlineCN51SummaryVO>(0,
				rankQuery.toString(), new Mapper<AirlineCN51SummaryVO>() {

					// Collection<AirlineCN51SummaryVO> summryVOs = null;
					// summryVOs = query.getResultList(new
					// Mapper<AirlineCN51SummaryVO>() {

					private AirlineCN51SummaryVO cn51SummaryVO = null;

					/**
					 * @param rs
					 * @throws SQLException
					 */
					public AirlineCN51SummaryVO map(ResultSet rs)
							throws SQLException {
						
						log.entering(CLASS_NAME, "AirlineCN51SummaryVOMapper");
						cn51SummaryVO = new AirlineCN51SummaryVO();
						cn51SummaryVO.setCompanycode(rs.getString("CMPCOD"));
						cn51SummaryVO.setAirlinecode(rs.getString("ARLCOD"));
						cn51SummaryVO.setAirlineidr(rs.getInt("ARLIDR"));
						cn51SummaryVO.setInvoicenumber(rs.getString("INVNUM"));
						cn51SummaryVO.setClearanceperiod(rs.getString("CLRPRD"));
						cn51SummaryVO.setInterlinebillingtype(rs
								.getString("INTBLGTYP"));

						cn51SummaryVO.setTotalAmountInContractCurrency(rs
								.getDouble("TOTAMTCRTCUR"));

						cn51SummaryVO.setContractCurrencycode(rs
								.getString("CRTCURCOD"));
						/**
						 * for optmimistic Locking
						 */
						cn51SummaryVO.setLastUpdatedUser(rs
								.getString("LSTUPDUSR"));
						if (rs.getTimestamp("LSTUPDTIM") != null) {
							cn51SummaryVO.setLastUpdatedTime(new LocalDate(
									LocalDate.NO_STATION, Location.NONE, rs
											.getTimestamp("LSTUPDTIM")));
						}
						//Added as part of ICRD-343336
						if(rs.getDate("INVRCPTDAT")!=null){
						//added by A-7929 as part of icrd-265471
						cn51SummaryVO.setInvoiceDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE,rs.getDate("INVRCPTDAT")));
						}
						cn51SummaryVO.setInvStatus(rs.getString("INVSTA"));
						cn51SummaryVO.setBillingType(rs.getString("INTINVTYP"));
						log.exiting(CLASS_NAME, "AirlineCN51SummaryVOMapper");
						return cn51SummaryVO;
					}// end of map
				});

		index = 0;

		pgqry.setParameter(++index, filterVO.getCompanyCode());
		pgqry.setParameter(++index,
				String.valueOf(filterVO.getBilledDateFrom().toSqlDate()));

		pgqry.setParameter(++index,
				String.valueOf(filterVO.getBilledDateTo().toSqlDate()));
		pgqry.setParameter(++index, filterVO.getInterlineBillingType());
		if(filterVO.getAirlineCode()!=null)
		{
		pgqry.setParameter(++index,filterVO.getAirlineCode());
		}
		//added by A-7929 as part of ICRD-265471
		if(filterVO.getBillingType()!=null && filterVO.getBillingType().trim().length() > 0)
		{
		pgqry.setParameter(++index,filterVO.getBillingType());
		}
		if(filterVO.getInvoiceNo()!=null && filterVO.getInvoiceNo().trim().length() > 0)
		{
		pgqry.setParameter(++index,filterVO.getInvoiceNo());
		}
		pgqry.append(MRA_DEFAULTS_SUFFIX_QUERY);

		return pgqry.getPage(filterVO.getPageNumber());
		
		
		
		
		
	}

	/**
	 * @author a-2458
	 * @param airlineCN51FilterVO
	 * @return Collection<AirlineCN51DetailsVO>
	 * @throws SystemException
	 */

	public Collection<AirlineCN51DetailsVO> findInwardInvoicesCollection(
			AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException {
		log.entering(CLASS_NAME, "findInwardInvoicesCollection");

		String clrPrd = airlineCN51FilterVO.getIataClearancePeriod();
		String airlineCode = airlineCN51FilterVO.getAirlineCode();
		String airlineNumber = airlineCN51FilterVO.getAirlineNum();
		String billingType = airlineCN51FilterVO.getInterlineBillingType();

		int airlineIdentifier = 0;
		LocalDate tempDate = null;

		Query query = getQueryManager().createNamedNativeQuery(
				MAILTRACKING_MRA_AIRLINEBILLING_FINDCN51DETAILSPRINT);
		int queryIndex = 0;
		query.setParameter(++queryIndex, airlineCN51FilterVO.getCompanyCode());
		query.setParameter(++queryIndex, billingType);

		if (clrPrd != null && clrPrd.length() > 0) {
			query.append(" AND C51.CLRPRD =  ? ");
			query.setParameter(++queryIndex, clrPrd);
		}
		if (airlineCode != null && airlineCode.length() > 0) {
			query.append(" AND SHR.TWOAPHCOD = ? ");
			query.setParameter(++queryIndex, airlineCode);
		}
		if (airlineNumber != null && airlineNumber.length() > 0) {
			query.append(" AND SHR.THRNUMCOD = ? ");
			query.setParameter(++queryIndex, airlineNumber);
		}
		airlineIdentifier = airlineCN51FilterVO.getAirlineIdentifier();

		if (airlineIdentifier != 0) {
			query.append(" AND C51.ARLIDR = ? ");
			query.setParameter(++queryIndex, airlineIdentifier);
		}

		tempDate = airlineCN51FilterVO.getFromDate();

		if (tempDate != null) {
			query.append(" AND CDRMST.FRMDAT >= ?  ");
			query.setParameter(++queryIndex, tempDate);
		}
		tempDate = airlineCN51FilterVO.getToDate();

		if (tempDate != null) {
			query.append(" AND CDRMST.TOODAT <= ? ");
			query.setParameter(++queryIndex, tempDate);
		}
		query
				.append(
						" GROUP BY  C51.CMPCOD,C51.CLRPRD,C51.INVNUM,SMY.LSTCURCOD,SHR.ARLIDR,")
				.append("C51.SUBCLSGRP,SHR.ARLNAM,SHR.TWOAPHCOD,SHR.THRNUMCOD ");
		Collection<AirlineCN51DetailsVO> airlineCN51DetailsVOs = query
				.getResultList(new AirlineCN51DetailsMapper());

		log.exiting(CLASS_NAME, "findInwardInvoicesCollection");
		return airlineCN51DetailsVOs;
	}

	/**
	 * Added By A-2122
	 * 
	 * @param memoFilterVo
	 * @return Collection<MemoInInvoiceVO>
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findOutwardRejectionMemo(
			MemoFilterVO memoFilterVo) throws SystemException {
		log.entering(CLASS_NAME, "findOutwardRejectionMemo");
		log.log(Log.FINE, "memoFilterVo", memoFilterVo);
		Collection<MemoInInvoiceVO> memoInInvoices = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_OUTWARDREJECTIONMEMODETAILS);
		qry.setParameter(++index, memoFilterVo.getCompanyCode());
		qry.setParameter(++index, memoFilterVo.getAirlineIdentifier());
		qry.setParameter(++index, memoFilterVo.getInterlineBillingType());
		if (memoFilterVo.getClearancePeriod() != null) {
			qry.append(" AND MEM.CLRPRD = ? ");
			qry.setParameter(++index, memoFilterVo.getClearancePeriod());
		}
		// qry.setParameter(++index, memoFilterVo.getClearancePeriod());

		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		log.exiting(CLASS_NAME, "findOutwardRejectionMemo");
		memoInInvoices = qry.getResultList(new MRAOutwardMemoDetailsMapper());
		log.log(Log.INFO, "return collection ", memoInInvoices);
		return memoInInvoices;

	}

	/**
	 * Added By A-2391
	 * 
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @throws SystemException
	 */
	public RejectionMemoVO findRejectionMemo(RejectionMemoFilterVO filterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findRejectionMemo");
		log.log(Log.FINE, "filterVo", filterVO);
		RejectionMemoVO rejectionMemoVO = null;
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_REJECTIONMEMODETAIL);
		qry.setParameter(++index, filterVO.getCompanyCode());
		if (filterVO.getMemoCode() != null
				&& filterVO.getMemoCode().trim().length() > 0) {
			qry.append(" AND MEM.MEMCOD = ? ");
			qry.setParameter(++index, filterVO.getMemoCode());
		}
		if (filterVO.getInvoiceNumber() != null
				&& filterVO.getInvoiceNumber().trim().length() > 0) {
			qry.append(" AND MEM.INWINVNUM = ? ");
			qry.setParameter(++index, filterVO.getInvoiceNumber());
		}
		/*if (filterVO.getBillingBasis()!= null                               //commented as part of ICRD_265471
				&& filterVO.getBillingBasis().trim().length() > 0) {
			qry.append(" AND MEM.BLGBAS = ? ");
			qry.setParameter(++index, filterVO.getBillingBasis());
		}
		if (filterVO.getCsgDocNum()!= null
				&& filterVO.getCsgDocNum().trim().length() > 0) {
			qry.append(" AND MEM.CSGDOCNUM = ? ");
			qry.setParameter(++index, filterVO.getCsgDocNum());
		}
		if (filterVO.getCsgSeqNum()>0) {
			qry.append(" AND MEM.CSGSEQNUM = ? ");
			qry.setParameter(++index, filterVO.getCsgSeqNum());
		}
		if (filterVO.getPoaCode()!= null
				&& filterVO.getPoaCode().trim().length() > 0) {
			qry.append(" AND MEM.POACOD = ? ");
			qry.setParameter(++index, filterVO.getPoaCode());
		}*/
		qry.setSensitivity(true);
		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		log.exiting(CLASS_NAME, "findRejectionMemo");
		List<RejectionMemoVO> rejectionMemoVOs = qry
				.getResultList(new MRARejectionMemoDetailsMapper());
		if (rejectionMemoVOs != null && rejectionMemoVOs.size() > 0) {
			log.log(Log.INFO, " ##### No of Summary VOs ", rejectionMemoVOs.size());
			rejectionMemoVO = rejectionMemoVOs.get(0);
		}

		if(rejectionMemoVO!=null){
		index = 0;
		Query findDocQry = getQueryManager().createNamedNativeQuery(
				FIND_SUPPORTING_DOCS);
		findDocQry.setParameter(++index, filterVO.getCompanyCode());
		
		
		if (filterVO.getMemoCode() != null
				&& filterVO.getMemoCode().trim().length() > 0) {
			findDocQry.append(" AND MEM.MEMCOD = ? ");
			findDocQry.setParameter(++index, filterVO.getMemoCode());
		}
		if (filterVO.getInvoiceNumber() != null
				&& filterVO.getInvoiceNumber().trim().length() > 0) {
			findDocQry.append(" AND MEM.INWINVNUM = ? ");
			findDocQry.setParameter(++index, filterVO.getInvoiceNumber());
		}
		
		List<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs = findDocQry
				.getResultList(new MRASupportingDocumentDetailMapper());
		
		if(sisSupportingDocumentDetailsVOs!=null && !sisSupportingDocumentDetailsVOs.isEmpty()){
			
			rejectionMemoVO.setSisSupportingDocumentDetailsVOs(sisSupportingDocumentDetailsVOs);
		}
		
		}

		log.log(Log.INFO, "return collection ", rejectionMemoVO);
		return rejectionMemoVO;


	}

	/**
	 * Added By A-2391
	 * 
	 * @param rejectionMemoVO
	 * @return String
	 * @throws SystemException
	 */
	public String findBlgCurCode(RejectionMemoVO rejectionMemoVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findBlgCurCode");
		log.log(Log.FINE, "rejectionMemoVO", rejectionMemoVO);
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_REJECTIONFINDBLGCURCOD);
		qry.setParameter(++index, rejectionMemoVO.getCompanycode());

		if (String.valueOf(rejectionMemoVO.getAirlineIdentifier()) != null) {
			qry.append("AND MST.ARLIDR=?");
			qry.setParameter(++index, rejectionMemoVO.getAirlineIdentifier());
		}

		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		log.exiting(CLASS_NAME, "findBlgCurCode");
		String curCode = null;
		if (qry.getSingleResult(getStringMapper("BASCURCOD")) != null) {
			curCode = qry.getSingleResult(getStringMapper("BASCURCOD"));
		}
		log.log(Log.INFO, "return string ", curCode);
		return curCode;

	}

	/**
	 * Added By A-2391
	 * 
	 * @param rejectionMemoVO
	 * @return double
	 * @throws SystemException
	 */
	public double findExgRate(RejectionMemoVO rejectionMemoVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findExgRate");
		log.log(Log.FINE, "rejectionMemoVO", rejectionMemoVO);
		int index = 0;
		Query qry = null;
		qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_REJECTIONFINDEXGRAT);
		qry.setParameter(++index, rejectionMemoVO.getCompanycode());
		if (rejectionMemoVO.getInwardClearancePeriod() != null) {
			qry.append("AND IAT.CLRPRD=?");
			qry.setParameter(++index, rejectionMemoVO
					.getInwardClearancePeriod());
		}
		if (String.valueOf(rejectionMemoVO.getAirlineIdentifier()) != null) {
			qry.append("AND RAT.ARLIDR=?");
			qry.setParameter(++index, rejectionMemoVO.getAirlineIdentifier());
		}
		if (rejectionMemoVO.getBillingCurrencyCode() != null) {
			qry.append("AND RAT.TOCURCOD=?");
			qry.setParameter(++index, rejectionMemoVO.getBillingCurrencyCode());
		}
		if (rejectionMemoVO.getContractCurrencyCode() != null) {
			qry.append("AND RAT.FRMCURCOD=?");
			qry
					.setParameter(++index, rejectionMemoVO
							.getContractCurrencyCode());
		}
		qry.setSensitivity(true);
		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		log.exiting(CLASS_NAME, "findExgRate");
		double rate = 0.0;
		String convertionFactor = qry
				.getSingleResult(getStringMapper("CNVFCT"));
		//System.out.println("DKLJJJJSFJSDKLJFKSDFKSDKLKSDKJSD->"+convertionFactor);		
		if (convertionFactor != null) {
			StringTokenizer tokens = new StringTokenizer(convertionFactor, ",");
			double roundingUnit = 0.01;
			int roundingBasis = 0;

			index = 0;
			while (tokens.hasMoreTokens()) {
				if (index == 0) {
					rate = Double.parseDouble(tokens.nextToken());
				} else if (index == 1) {
					roundingUnit = Double.parseDouble(tokens.nextToken());
				} else if (index == 2) {
					if ("D".equals(tokens.nextToken())) {
						roundingBasis = -1;
					} else if ("U".equals(tokens.nextToken())) {
						roundingBasis = 1;
					}
				}
				++index;
			}
		} else {
			index = 0;
			qry = getQueryManager().createNamedNativeQuery(
					MRA_AIRLINEBILLING_REJECTIONFINDEXGRAT);
			qry.setParameter(++index, rejectionMemoVO.getCompanycode());
			if (rejectionMemoVO.getInwardClearancePeriod() != null) {
				qry.append("AND IAT.CLRPRD=?");
				qry.setParameter(++index, rejectionMemoVO
						.getInwardClearancePeriod());
			}
			if (String.valueOf(rejectionMemoVO.getAirlineIdentifier()) != null) {
				qry.append("AND RAT.ARLIDR=?");
				qry.setParameter(++index, rejectionMemoVO
						.getAirlineIdentifier());
			}
			if (rejectionMemoVO.getBillingCurrencyCode() != null) {
				qry.append("AND RAT.FRMCURCOD=?");
				qry.setParameter(++index, rejectionMemoVO
						.getBillingCurrencyCode());
			}
			if (rejectionMemoVO.getContractCurrencyCode() != null) {
				qry.append("AND RAT.TOCURCOD=?");
				qry.setParameter(++index, rejectionMemoVO
						.getContractCurrencyCode());
			}
			qry.append(" AND CURMST.CURCOD=?");
			qry.setParameter(++index, rejectionMemoVO.getBillingCurrencyCode());
			qry.setSensitivity(true);
			log.log(Log.INFO, "123456789 Query ", qry);
			log.exiting(CLASS_NAME, "findExgRate");
			convertionFactor = qry
			.getSingleResult(getStringMapper("CNVFCT"));
			if (convertionFactor != null) {
				index = 0;
				double roundingUnit = 0.01;
				int roundingBasis = 0;
				StringTokenizer tokens = new StringTokenizer(convertionFactor, ",");
				while (tokens.hasMoreTokens()) {
					if (index == 0) {
						rate = Double.parseDouble(tokens.nextToken());
					} else if (index == 1) {
						roundingUnit = Double.parseDouble(tokens.nextToken());
					} else if (index == 2) {
						String currentToken = tokens.nextToken();
						if ("D".equals(currentToken)) {
							roundingBasis = -1;
						} else if ("U".equals(currentToken)) {
							roundingBasis = 1;
						}
					}
					++index;
				}
				rate = 1 / rate;
				//System.out.println("AMOUNT ---->" + rate);
				rate = getRoundedAmount(rate, roundingUnit, roundingBasis);
			}
		}
		log.log(Log.INFO, "return double--> ", rate);
		return rate;

	}

	/**
	 * @param rejectionMemoFilterVO
	 * @param user
	 * @throws SystemException
	 */
	public String findAcctTxnIdr(RejectionMemoFilterVO rejectionMemoFilterVO,
			String user) throws SystemException {
		log.entering(CLASS_NAME, "acctTxnIdrProcedure");
		Procedure acctTxnIdrProcedure = getQueryManager()
				.createNamedNativeProcedure(MRA_AIRLINEBILLING_ACCTTXNIDR_PROC);
		acctTxnIdrProcedure.setSensitivity(true);
		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		log.log(Log.FINE, "Current Date: ", currentDate);
		acctTxnIdrProcedure.setParameter(++index, rejectionMemoFilterVO
				.getCompanyCode());
		acctTxnIdrProcedure.setParameter(++index, rejectionMemoFilterVO
				.getAirlineIdentifier());
		acctTxnIdrProcedure.setParameter(++index, rejectionMemoFilterVO
				.getMemoCode());
		acctTxnIdrProcedure.setParameter(++index, user);
		acctTxnIdrProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
		acctTxnIdrProcedure.setOutParameter(++index, SqlType.STRING);
		acctTxnIdrProcedure.setOutParameter(++index, SqlType.STRING);
		acctTxnIdrProcedure.execute();
		log.log(Log.FINE, "executed Procedure");

		String acctTxnIdr = (String) acctTxnIdrProcedure.getParameter(6);
		String outParameter = (String) acctTxnIdrProcedure.getParameter(7);
		log.log(Log.FINE, "acctTxnIdr is ", acctTxnIdr);
		log.log(Log.FINE, "outParameter is ", outParameter);
		log.exiting(CLASS_NAME, "acctTxnIdrProcedure");

		return acctTxnIdr;

	}

	/**
	 * This method is used to round the specified amount to the rounding unit
	 * specified
	 * 
	 * Round basis is the parameter which decides where to round roundBasis=-1
	 * to round down roundBasis=0 to round according to Tact Rules roundBasis=1
	 * to round Up
	 * 
	 * @param amount
	 * @param roundingUnit
	 * @param roundBasis
	 * @return double
	 */
	private double getRoundedAmount(double amount, double roundingUnit,
			int roundBasis) {
		BigDecimal roundedResult = null;
		if (roundingUnit > 0) {
			BigDecimal bdRoundingUnit = new BigDecimal(String
					.valueOf(roundingUnit));
			//System.out.println("ROUNDING UNIT-->" + bdRoundingUnit);
			int scale = bdRoundingUnit.scale();
			//System.out.println("SCALE-->" + scale);
			BigDecimal unRoundedResult = new BigDecimal(amount).setScale(
					scale + 1, BigDecimal.ROUND_HALF_EVEN);
			//System.out.println("UNROUNDED RESULT-->" + unRoundedResult);
			double remainder = amount % roundingUnit;
			//System.out.println("REMAINDER-->" + remainder);
			if (remainder == 0) {
				return amount;
			}
			BigDecimal bdRemainder = new BigDecimal(remainder).setScale(
					scale + 1, BigDecimal.ROUND_HALF_EVEN);
			//System.out.println("BDREMAINDER-->" + bdRemainder);
			if (roundBasis == 0) {
				if (remainder < (roundingUnit / 2)) {
					//System.out.println("REMAINDER--->" + remainder);
					//System.out.println("ROUNDING UNIT BY 2-->" + roundingUnit	/ 2);
					roundedResult = unRoundedResult.subtract(bdRemainder);
					//System.out.println("ROUNDING UNIT/2-->" + roundedResult);
				} else {
					roundedResult = unRoundedResult.add(bdRoundingUnit
							.subtract(bdRemainder));
					//System.out.println("ROUNDING UNIT/3-->" + roundedResult);
				}
			} else if (roundBasis == -1) {
				roundedResult = unRoundedResult.subtract(bdRemainder);
				return roundedResult.doubleValue();
			} else if (roundBasis == 1) {
				roundedResult = unRoundedResult.add(bdRoundingUnit
						.subtract(bdRemainder));
				return roundedResult.doubleValue();
			}
		} else if (roundingUnit == 0) {
			roundedResult = new BigDecimal(amount);
		}
		double roundedValue=0.0;
		if(roundedResult!=null) {
			roundedValue=roundedResult.doubleValue();
		}
		return roundedValue;
	}
	/**
	 * @author a-2391 This method is used to find AuditDetails
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<AuditDetailsVO> findArlAuditDetails(
			MRAArlAuditFilterVO mailAuditFilterVO) 
			throws SystemException, PersistenceException {
		log.entering("audit", "findAuditDetails");
		int index = 0;
		Query qry = getQueryManager().createNamedNativeQuery(FIND_ARLAUDIT);
		qry.setParameter(++index, mailAuditFilterVO.getCompanyCode());
		qry.setParameter(++index, mailAuditFilterVO.getTxnFromDate().toGMTDate());
		qry.setParameter(++index, mailAuditFilterVO.getTxnToDate().toGMTDate());
		
		if (mailAuditFilterVO.getAirlineCode() != null
				&& mailAuditFilterVO.getAirlineCode().trim().length() > 0) {
			qry.append("AND ARLIDR = ?");
			qry.setParameter(++index, mailAuditFilterVO.getAirlineIdentifier());
		}
		if (mailAuditFilterVO.getClearancePeriod() != null
				&& mailAuditFilterVO.getClearancePeriod().trim().length() > 0) {
			qry.append("AND CLRPRD = ?");
			qry.setParameter(++index, mailAuditFilterVO.getClearancePeriod());
		}
		
			
		return qry.getResultList(new Mapper<AuditDetailsVO>() {






	public AuditDetailsVO map(ResultSet rs) throws SQLException {
				   AuditDetailsVO auditDetailsVO = new AuditDetailsVO();
				   auditDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				   auditDetailsVO.setAdditionalInformation(rs.getString("ADLINF"));
				   auditDetailsVO.setAction(rs.getString("ACTCOD"));
				   auditDetailsVO.setLastUpdateUser(rs.getString("UPDUSR"));
				   auditDetailsVO.setStationCode(rs.getString("STNCOD"));
				   //lastUpdateTime can be null
				   java.sql.Timestamp lastUpdateTime = rs.getTimestamp("UPDTXNTIM");
				   if(lastUpdateTime != null) {
					auditDetailsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, lastUpdateTime));
				   }
				   return auditDetailsVO;
				   
			   }
		});

	}
	
	/**

	 * @author A-3434

	 * @param formOneFilterVo

	 * @return Page<FormOneVO>

	 * @throws SystemException

	 */

	public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)

	throws SystemException {

		log.entering("MraAirlineBillingSqlDAO", "findFormOnes");

		int index = 0;

		Query qry = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FINDFORMONES);

		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
        int ownAirlineidentifier = logonAttributes.getOwnAirlineIdentifier();
       // qry.setParameter(++index, ownAirlineidentifier);
        //log.log(Log.INFO, "%%%%%%%%% ownAirlineidentifier " + ownAirlineidentifier);
        //qry.setParameter(++index, formOneFilterVo.getClearancePeriod());
        qry.setParameter(++index, formOneFilterVo.getCompanyCode());
        
        
        log.log(Log.INFO, "%%%%%%%%% ClearancePeriod ", formOneFilterVo.getClearancePeriod());
		qry.setParameter(++index, formOneFilterVo.getClearancePeriod());
		

		if (formOneFilterVo.getAirlineId() != 0) {

			qry.append(" AND FRM.ARLIDR = ? ");

			qry.setParameter(++index, formOneFilterVo.getAirlineId());

		}

		if(formOneFilterVo.getAirlineCode()!=null &&

		 formOneFilterVo.getAirlineCode().trim().length()>0 ){

		 qry.append("AND FRM.ARLCOD = ?");

		 qry.setParameter(++index,formOneFilterVo.getAirlineCode());

		 }

		 if(formOneFilterVo.getAirlineNumber()!=null &&

		 formOneFilterVo.getAirlineNumber().trim().length()>0 ){

		 qry.append(" AND FRM.ARLIDR = ? ");

		 qry.setParameter(++index,formOneFilterVo.getAirlineNumber());

		 }

		log.log(Log.INFO, "%%%%%%%%% Query ", qry);
		PageableQuery<FormOneVO> pqry = new PageableQuery<FormOneVO>(qry,
				new ListFormOneMapper());

		return pqry.getPage(formOneFilterVo.getPageNumber());

	}
	
	/**

	 * @author A-3434

	 * @param interlineFilterVo

	 * @return Collection<AirlineForBillingVO>

	 * @throws SystemException
 
	 * @throws PersistenceException

	 */

	public Collection<AirlineForBillingVO> findAirlineDetails(
			InterlineFilterVO interlineFilterVo)

	throws SystemException, PersistenceException {

		log.entering("MraAirlineBillingSQLDAO", "findAirlineDetails");

		Query query = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FINDAIRLINEDETAILS);

		int index = 0;
		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
        int ownAirlineidentifier = logonAttributes.getOwnAirlineIdentifier();
        //query.setParameter(++index, Integer.valueOf(ownAirlineidentifier));
        query.setParameter(++index, Integer.valueOf(ownAirlineidentifier));
        query.setParameter(++index, interlineFilterVo.getClearancePeriod());
        query.setParameter(++index, interlineFilterVo.getCompanyCode());

		if (interlineFilterVo.getIsFormTwo()) {

			log.log(Log.FINE, "isFormTwoFlag is True");

			//query.append(",MST.BLGCUR");
			query.append(",MST.ARLNAM");

		}

		if (interlineFilterVo.getIsFormTwo()) {

			query.append("FROM 	SHRARLMST MST,MALMRAARLBLG BLG ");

			query.append("WHERE BLG.CMPCOD = MST.CMPCOD AND");

			query.append("BLG.ARLIDR = MST.ARLIDR AND");

			query.append("BLG.GENFRMTWOFLG ='Y'AND ");

			query.append("BLG.CMPCOD = ?");

			query.setParameter(++index, interlineFilterVo.getCompanyCode());

			query.append("AND	BLG.CLRPRD = ?");

			query.setParameter(++index, interlineFilterVo.getClearancePeriod());

			query.append(" ORDER BY BLG.ZONIND");

		}

		
		Collection<AirlineForBillingVO> airlineForBillingVO = query
				.getResultList(new AirlineDetailsMapper());

		log.exiting("MraAirlineBillingSQLDAO", "findAirlineDetails");

		log.log(Log.FINE, "<----------- QUERY ------------->");

		log.log(Log.FINE, query.toString());

		log.log(Log.FINE, "VO's returned--->>", airlineForBillingVO);
		return airlineForBillingVO;

	}
	/**
	 * 
	 */
	public FormOneVO listFormOneDetails(FormOneVO formOneVO)
	throws SystemException{
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_DEFAULTS_FINDFORMONEDTLS);
		int index=0;
		query.setParameter(++index, formOneVO.getCompanyCode());
		query.setParameter(++index, formOneVO.getAirlineIdr());
		query.setParameter(++index, formOneVO.getClearancePeriod());
		if(formOneVO.getInvStatus()!=null) {
			if(!("").equals(formOneVO.getInvStatus())&& (formOneVO.getInvStatus().trim().length()>0)){
	
				query.append("AND SMY.INVSTA= ?");
				query.setParameter(++index,formOneVO.getInvStatus());
				}
		}
		Collection<FormOneVO> formOneVOs= query.getResultList(new FormOneDtlsMultiMapper());
		ArrayList<FormOneVO> formOneVOss=new ArrayList<FormOneVO>(formOneVOs);
		return formOneVOss.get(0);
		
	}
	/**
	 * @author a-3456 This method is used to list view form1
	 * @param InterlineFilterVO
	 * @return FormOneVO
	 * @throws SystemException
	 */
	
	public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)

	throws SystemException {

		log.entering("MraAirlineBillingSQLDAO", "findFormOneDetails");

		int index = 0;

		String EMPTY = "";

		Query query = null;
		interlineFilterVo.setInterlineBillingType("O");
	    LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();

	
			query = getQueryManager().createNamedNativeQuery(
					MRA_AIRLINEBILLING_OUTWARD_FINDFORMONEDETAILS);	
			query.setParameter(++index, interlineFilterVo.getCompanyCode());
			query.setParameter(++index, interlineFilterVo.getAirlineIdentifier());
			query.setParameter(++index, interlineFilterVo.getClearancePeriod());
			
		if (interlineFilterVo.getAirlineIdentifier() != 0) {
				query.append(" AND FRMONE.ARLIDR = ? ");
				query.setParameter(++index, interlineFilterVo
						.getAirlineIdentifier());
		}
		query.append(" AND FRMONE.INTBLGTYP = ? ");
		query.setParameter(++index, interlineFilterVo.getInterlineBillingType());
		log.log(Log.FINE, "<----------- QUERY ------------->");
		log.log(Log.FINE, query.toString());
		Collection<FormOneVO> formOneVos = query
				.getResultList(new ViewFormOneMultiMapper());

			
		FormOneVO formOneVo = null;
		if(formOneVos!=null){
		for (FormOneVO formOneVoFromMultiMapper : formOneVos) {

			formOneVo = formOneVoFromMultiMapper;

		}
		}

		
		return formOneVo;

	}
	/**
	 * @author A-3108
	 * @param  interlineFilterVO
	 * @return Collection<AirlineForBillingVO>
	 * @throws SystemException
	 */
	public Collection<AirlineForBillingVO> findFormThreeDetails(InterlineFilterVO interlineFilterVO)
		throws SystemException{
		log.entering("MraAirlineBillingSqlDAO", "findFormThreeDetails");
		int index = 0;
		Collection<AirlineForBillingVO> airlineForBillingVOs=null;
		Query query = getQueryManager().createNamedNativeQuery(
				MRA_AIRLINEBILLING_FINDFORMTHREEDETAILS);
		query.setParameter(++index, interlineFilterVO.getCompanyCode());
		query.setParameter(++index, interlineFilterVO.getClearancePeriod());
		query.append(" AND CAPFRMTHRFLG = ? ");
		query.setParameter(++index, "Y");
		log.log(Log.INFO, "%%%%%%%%% Query ", query);
		return query.getResultList(new FormThreeDetailsMapper());
	}
	/**

	 * @author A-3108

	 * @return int

	 * @throws SystemException

	 */

	public int findMaxSerialNumber() throws SystemException {

		Query query = getQueryManager().createNamedNativeQuery(

				MRA_AIRLINEBILLING_FINDSERNUM);

		Mapper<Integer> integerMapper = getIntMapper("SERNUM");

		int maxNum = query.getSingleResult(integerMapper).intValue();

		return (maxNum + 1);

	}
	/**

	 *@author a-2270

	 */

	/**
	 * @param lstupdusr
	 * @param lstupddat
	 * @param serialNumber
	 * @param companyCode
	 * @return String
	 * @throws SystemException
	 */
	public String saveFormThree(String lstupdusr, LocalDate lstupddat,

	int serialNumber, String companyCode) throws SystemException {

		log.entering("CraAirlineBillingSQLDAO", "saveFormThreeDetails");

		Procedure burstProcedure = getQueryManager()

		.createNamedNativeProcedure(MRA_ARLBLG_SAVEFORMTHREEDETAILS);

		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,

		Location.NONE, true);

		int index = 0;

		burstProcedure.setSensitivity(true);

		burstProcedure.setParameter(++index, companyCode);

		burstProcedure.setParameter(++index, serialNumber);

		burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());

		burstProcedure.setParameter(++index, lstupdusr);

		burstProcedure.setParameter(++index, lstupddat.toSqlTimeStamp());

		burstProcedure.setOutParameter(++index, SqlType.STRING);

		burstProcedure.execute();

		log.log(Log.FINE, "executed Procedure");

		String outParameter = (String) burstProcedure.getParameter(6);

		log.log(Log.FINE, "outParameter is ", outParameter);
		log.exiting("MraAirlineBillingSQLDAO", "saveFormThreeDetails");

		return outParameter;

	}

	/**
	 * @author a-3456 This method is used to get captureinvoice details
	 * @param InterlineFilterVO
	 * @return FormOneVO
	 * @throws SystemException
	 */
	
	public AirlineCN51SummaryVO findCaptureInvoiceDetails(AirlineCN51FilterVO airlineCN51FilterVO)
	throws SystemException{

		log.entering("MraAirlineBillingSQLDAO---->", "findCaptureInvoiceDetails");
		log
				.log(Log.FINE, "airlineCN51FilterVO---------->",
						airlineCN51FilterVO);
			int index = 0;
		Query query = null;
	    query = getQueryManager().createNamedNativeQuery(
	    		MRA_AIRLINEBILLING_INWARD_CAPTUREINVOICE);	    
	    query.setParameter(++index, airlineCN51FilterVO.getCompanyCode());
	    query.setParameter(++index, airlineCN51FilterVO.getIataClearancePeriod());
		query.setParameter(++index, airlineCN51FilterVO.getAirlineCode());
		query.setParameter(++index, airlineCN51FilterVO.getInvoiceReferenceNumber());
		query.setParameter(++index,Integer.parseInt(airlineCN51FilterVO.getInvDate().toStringFormat(DATE).substring(0, 8)  ) );
	
		
		if (airlineCN51FilterVO.getAirlineIdentifier() != 0) {
			query.append(" AND SMY.ARLIDR = ? ");
			query.setParameter(++index, airlineCN51FilterVO.getAirlineIdentifier());
		}
		log.log(Log.FINE, "QUERY --->", query);
		return query.getSingleResult(new CaptureInvoiceMapper());
		
	}
	
	/**
	 * to trigger Rejection Memo Accounting at despatch level
	 * @author Meenu A-2565
	 * @param rejectionVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void triggerRejectionMemoAccounting(RejectionMemoVO rejectionVO) throws SystemException, PersistenceException {
		log.entering("CLASS_NAME", "triggerRejectionMemoAccounting");
	      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure("mailtracking.mra.airlinebilling.triggerRejectionMemoAccounting");
	      int index = 0;
	      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	      burstProcedure.setSensitivity(true);
	      log.log(Log.INFO, "rejectionVO.getCompanycode()", rejectionVO.getCompanycode());
		log.log(Log.INFO, "rejectionVO.getAirlineIdentifier()", rejectionVO.getAirlineIdentifier());
		log.log(Log.INFO, "rejectionVO.getMemoCode()", rejectionVO.getMemoCode());
		log.log(Log.INFO, "rejectionVO.getCsgDocNum()", rejectionVO.getCsgDocNum());
		log.log(Log.INFO, "rejectionVO.getCsgSeqNum()", rejectionVO.getCsgSeqNum());
		log.log(Log.INFO, "rejectionVO.getBillingBasis()", rejectionVO.getBillingBasis());
		log.log(Log.INFO, "rejectionVO.getPoaCode()", rejectionVO.getPoaCode());
		log.log(Log.INFO, "logonAttributes.getUserId()", logonAttributes.getUserId());
		burstProcedure.setParameter(++index, rejectionVO.getCompanycode());
	      burstProcedure.setParameter(++index, rejectionVO.getAirlineIdentifier());
	      burstProcedure.setParameter(++index, rejectionVO.getMemoCode());
	      burstProcedure.setParameter(++index, rejectionVO.getCsgDocNum());
	      burstProcedure.setParameter(++index, rejectionVO.getCsgSeqNum());
	      burstProcedure.setParameter(++index, rejectionVO.getBillingBasis());
	      burstProcedure.setParameter(++index, rejectionVO.getPoaCode());
	      burstProcedure.setParameter(++index, logonAttributes.getUserId());
	      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
	      burstProcedure.setOutParameter(++index, SqlType.STRING);
	      burstProcedure.execute();

	      String outParameter = (String)burstProcedure.getParameter(index);
	      log
				.log(
						Log.INFO,
						"FINAL triggerRejectionMemoAccounting  outParameter----------->>",
						outParameter);
		log.exiting("CLASS_NAME", "triggerRejectionMemoAccounting");
	      
	}
	/**
	 * @author a-2391
	 * @param filterVO
	 * @return ArlInvoiceDetailsReportVO
	 * @throws PersistenceException
	 * @throws SystemException
	 * 
	 */
	public  ArlInvoiceDetailsReportVO generateCN66InvoiceReport(AirlineCN66DetailsFilterVO filterVO)	
	  throws PersistenceException, SystemException {
		ArlInvoiceDetailsReportVO invoiceDetailsReportVO=null;
		log.entering(CLASS_NAME,"generateCN66InvoiceReport");
  	Query query=getQueryManager().createNamedNativeQuery(FIND_INVOICEREPORT_DATA);
  	int index=0;
  	query.setParameter(++index,filterVO.getInvoiceRefNumber());
  	query.setParameter(++index,filterVO.getCompanyCode());
  	query.setParameter(++index,filterVO.getClearancePeriod());
  	query.setParameter(++index,filterVO.getInterlineBillingType());
  	log.log(Log.INFO, "\n\n** Final Query->", query);
	log.exiting(CLASS_NAME,"generateInvoiceReport");
  	List<ArlInvoiceDetailsReportVO> invoiceDetailsReportVOs = query.getResultList(new ArlInvoiceDetailsReportMapper());
	if(invoiceDetailsReportVOs!=null && !invoiceDetailsReportVOs.isEmpty()){
		invoiceDetailsReportVO= invoiceDetailsReportVOs.get(0);
		}
	log.log(Log.INFO, "\n\n*vos obatined >", invoiceDetailsReportVO);
		return invoiceDetailsReportVO;
	}
	/**
	 * @author a-2391
	 * @param String companyCode
	 *@param int airlineIdentifier
	 * @return AirlineVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	
	public AirlineVO findAirlineAddresss(String companyCode, int airlineIdentifier) throws PersistenceException, SystemException {
		 log.entering("CLASS_NAME", "findAirlineAddress");			 
		Query query = getQueryManager().createNamedNativeQuery(MRA_ARLBLG_FIND_AIRLINEDTLS);
				int index=0;
				query.setParameter(++index, companyCode);
				query.setParameter(++index, airlineIdentifier);
			 log.log(Log.INFO, "Query--", query);
			AirlineVO airlineVO=query.getSingleResult(new AirlineCN66AddressMapper());
			 log.log(Log.INFO, "Vos Returned ", airlineVO);
			return airlineVO;
		
	}
	/**
	 * to trigger Accounting After Accepting an Exception at despatch level
	 * @author Meenu A-3429
	 * @param rejectionVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void triggerAcceptDSNAccounting(AirlineExceptionsVO airlineExceptionsVO) throws SystemException, PersistenceException {
		log.entering("CLASS_NAME", "triggerAcceptDSNAccounting");
		/*PROCEDURE SPR_MRA_MALINOUTACC (
			PI_CMPCOD      IN       MTKARLC66DTL.CMPCOD%TYPE,
			PI_ARLIDR      IN       MTKARLC66DTL.ARLIDR%TYPE,
			PI_INVNUM      IN       MTKARLC66DTL.INVNUM%TYPE,
			PI_INTBLGTYP   IN       MTKARLC66DTL.INTBLGTYP%TYPE,   --- should be 'I' (Inward)
			PI_SEQNUM      IN       MTKARLC66DTL.SEQNUM%TYPE,      --seqnum in cn66dtl
			PI_CSGDOCNUM   IN       MTKARLC66DTL.CSGDOCNUM%TYPE,
			PI_BLGBAS      IN       MTKARLC66DTL.BLGBAS%TYPE,
			PI_POACOD      IN       MTKARLC66DTL.POACOD%TYPE,
			PI_CLRPRD      IN       MTKARLC66DTL.CLRPRD%TYPE,
			pi_excflg      in        craaccexpdtl.ACCSTA%type, ------  make it 'A'
			PI_LSTUPDUSR   IN       CRAACCTXN.LSTUPDUSR%TYPE,
			PI_LSTUPDTIM   IN       CRAACCTXN.LSTUPDTIM%TYPE,
			PO_STATUS      OUT      VARCHAR2
			)*/
		
	      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(MRA_ARLBLG_ACCEPT_ACCOUNTING);
	      int index = 0;
	      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	      burstProcedure.setSensitivity(true);
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getCompanyCode());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getAirlineIdentifier());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getInvoiceNumber());
	      burstProcedure.setParameter(++index, INTERLINE_BLILLINGTYPE);
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getSeqNumber());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getCsgDocNum());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getBillingBasis());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getPoaCode());
	      burstProcedure.setParameter(++index, airlineExceptionsVO.getClearancePeriod());
	      burstProcedure.setParameter(++index, ACCOUNTING_STATUS);
	      burstProcedure.setParameter(++index, logonAttributes.getUserId());
	      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
	      burstProcedure.setOutParameter(++index, SqlType.STRING);
	      burstProcedure.execute();

	      String outParameter = (String)burstProcedure.getParameter(index);
	      log.log(Log.INFO,
				"FINAL triggerAcceptDSNAccounting  outParameter----------->>",
				outParameter);
		log.exiting("CLASS_NAME", "triggerAcceptDSNAccounting");
	      
	}

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO#generateIsFile(ISFileFilterVO)
	 *	Added by 			: a-7794 on 24-July-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param fileFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 *	Return type	: 	Collection<SISMessageVO>
	 */
	@Override
	public Collection<SISMessageVO> generateIsFile(
			MiscFileFilterVO fileFilterVO) throws SystemException,
			PersistenceException {
		log.entering(CLASS_NAME, "generateIsFile");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(MRA_ARLBLG_GENERATE_MRA_MISC_FILE);
		query.setParameter(++index, logonAttributes.getCompanyCode());
		query.setParameter(++index, fileFilterVO.getClearancePeriod());
		//query.setParameter(++index, fileFilterVO.getAirlineIdr());
		if (fileFilterVO.getFileName() != null
				&& fileFilterVO.getFileName().length() > 0) {
			query.append("  AND SMY.INTFCDFILNAM = ? ");
			query.setParameter(++index, fileFilterVO.getFileName());
		}
		Collection<SISMessageVO> messageVOs = query
				.getResultList(new ISFileMultiMapper());
		log.exiting(CLASS_NAME, "generateIsFile");
		return messageVOs;
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
	public void withdrawMailBag(AirlineCN66DetailsVO airlineCN66DetailsVO) throws SystemException {
		// TODO Auto-generated method stub
		
	      Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(MRA_ARLBLG_WITHDRAWMAILBAG);
	      int index = 0;
	      LocalDate currentDate = new LocalDate("***", Location.NONE, true);
	      LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	      burstProcedure.setSensitivity(true);
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getCompanyCode());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getAirlineIdentifier());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getInvoiceNumber());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getClearancePeriod());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getMalSeqNum());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getDespatchSerialNo());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getInterlineBillingType());
	      burstProcedure.setParameter(++index, airlineCN66DetailsVO.getSequenceNumber());
	      burstProcedure.setParameter(++index, logonAttributes.getUserId());
	      burstProcedure.setParameter(++index, currentDate.toSqlTimeStamp());
	      burstProcedure.setOutParameter(++index, SqlType.STRING);
	      burstProcedure.execute();

	      String outParameter = (String)burstProcedure.getParameter(index);
	      log.log(Log.INFO,
				"FINAL triggerAcceptDSNAccounting  outParameter----------->>",
				outParameter);
	      
	      
	}

	/**
	 * 		 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO#downloadAttachment(com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO)
	 *	Added by 			: a-8061 on 29-Oct-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param supportingDocumentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	  public SisSupportingDocumentDetailsVO downloadAttachment(SupportingDocumentFilterVO supportingDocumentFilterVO)
			    throws SystemException, PersistenceException
			  {
			    this.log.entering(CLASS_NAME, "downloadAttachment");
			    Query query = getQueryManager().createNamedNativeQuery(SIS_SUPPORTING_DOC_DOWNLOAD);
			    int index = 0;
			    query.setParameter(++index, 
			      supportingDocumentFilterVO.getCompanyCode());
			    query.setParameter(++index, 
			      Integer.valueOf(supportingDocumentFilterVO.getBilledAirline()));
			    query.setParameter(++index, 
			      supportingDocumentFilterVO.getClearancePeriod());
			    query.setParameter(++index, 
			      supportingDocumentFilterVO.getInvoiceNumber());
			    query.setParameter(++index, 
			      Integer.valueOf(supportingDocumentFilterVO.getInvoiceSerialNumber()));
			    query.setParameter(++index, 
			      supportingDocumentFilterVO.getBillingType());
			    query.setParameter(++index, 
			      supportingDocumentFilterVO.getFileName());

			    if(supportingDocumentFilterVO.getDocumentSerialNumber()!=0){
			    	query.append("  AND DOCSERNUM = ? ");
					query.setParameter(++index, 
				    Integer.valueOf(supportingDocumentFilterVO.getDocumentSerialNumber()));
			    	
			    }

			    return ((SisSupportingDocumentDetailsVO)query.getSingleResult(new AttachmentDownladMapper()));
			  }
	  
	  /**
	   * 
	   *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO#findSupportingDocumentSerialNumber(com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO)
	   *	Added by 			: a-8061 on 29-Oct-2018
	   * 	Used for 	:	ICRD-265471
	   *	Parameters	:	@param sisSupportingDocumentDetailsVO
	   *	Parameters	:	@return
	   *	Parameters	:	@throws SystemException
	   *	Parameters	:	@throws PersistenceException
	   */
		public int findSupportingDocumentSerialNumber(
				SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
				throws SystemException, PersistenceException {
			log.entering(CLASS_NAME, "findSupportingDocumentSerialNumber");
			int index = 0;
			Query query = getQueryManager().createNamedNativeQuery(
					MRA_SISBILLING_SUPPORTINGDOCUMENT_DOCSERIALNUMBER);
			query.setParameter(++index,
					sisSupportingDocumentDetailsVO.getCompanyCode());
			query.setParameter(++index,
					sisSupportingDocumentDetailsVO.getBilledAirline());		
			query.setParameter(++index,
					sisSupportingDocumentDetailsVO.getClearancePeriod());
			query.setParameter(++index,
					sisSupportingDocumentDetailsVO.getInvoiceNumber());
			query.setParameter(++index,
					sisSupportingDocumentDetailsVO.getBillingType());
			Mapper<Integer> intMapper = getIntMapper("DOCSERNUM");
			return query.getSingleResult(intMapper);
		}
	/**
	 * Method to list CN66 details
	 * 
	 * @param cn66FilterVo
	 * @return Collection<AirlineCN66DetailsVO>
	 * @throws SystemException
	 */
	public Collection<AirlineCN66DetailsVO> findCN66DetailsVOsForStatusChange(
			AirlineCN66DetailsFilterVO cn66FilterVo,long mailSequenceNumber) throws SystemException {
		log.entering(CLASS_NAME, "findCN66DetailsVOsForStatusChange");
		  int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(MRA_ARLBLG_FIND_CN66DTLS_FORSTATUSCHANGE);
		query.setParameter(++index, cn66FilterVo.getCompanyCode());
		query.setParameter(++index, cn66FilterVo.getInvoiceRefNumber());
		query.setParameter(++index, cn66FilterVo.getInterlineBillingType());
		query.setParameter(++index, mailSequenceNumber);
		
		
		
		if (query != null) {
			query.setSensitivity(true);       
		}
		Collection<AirlineCN66DetailsVO> airlineCn66DetailsVos = query
				.getResultList(new AirlineCN66DetailsMapper());  
		log.exiting(CLASS_NAME, "findCN66DetailsVOsForStatusChange");
		return airlineCn66DetailsVos;  
	      
	}

			 
 }
		 
		
			
		
		
	
