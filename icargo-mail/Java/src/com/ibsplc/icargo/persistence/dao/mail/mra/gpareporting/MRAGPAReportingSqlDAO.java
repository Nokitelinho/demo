/*
 * MRAGPAReportingSqlDAO.java Created on Feb 22, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
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
 * @author A-1945
 */

/*
 *
 * Revision History
 * Version	 	Date      		    Author			Description
 * 0.1			Feb 22, 2007 	  	A-1945			First draft
 * 0.2          Feb 22, 2007        A-2280          Added method findClaimDetails
 * 0.3			Mar 6 , 2007		A-2245			Added method printExceptionsReportAssigneeDetails
 * 0.4			Mar 19 , 2007		A-2245			Added method printExceptionsReportAssigneeSummary
 * 0.5			Mar 20 , 2007		A-2245			Added method printExceptionsReportDetails
 * 0.6			Mar 20 , 2007		A-2245			Added method printExceptionsReportSummary
 * 0.7			Nov 20,2018			A-8464			Added method listInvoicDetails
 *
 */
public class MRAGPAReportingSqlDAO extends AbstractQueryDAO
        implements MRAGPAReportingDAO {

    private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING ");
	private static final String TRIGGERPOINT_JOB="JOB";
    private static final String FIND_GPA_REPORTING_DETAILS =
            "mailtracking.mra.gpareporting.findGPAReportingDetails";
    private static final String FIND_CLAIM_DETAILS="mailtracking.mra.gpareporting.findClaimDetails";

    private static final String MTKGPARPT_PROCESS_GPAREPORT = "mailtracking.mra.gpareporting.processgpareport";
    private static final String PRINT_EXCEPTIONSASSIGNEEDETAILS =
    		"mailtracking.mra.gpareporting.printexceptionsassigneedetails";
    private static final String PRINT_EXCEPTIONSASSIGNEESUMMARY =
			"mailtracking.mra.gpareporting.printexceptionsassigneesummary";
    private static final String PRINT_EXCEPTIONDETAILS =
			"mailtracking.mra.gpareporting.printexceptiondetails";
    private static final String PRINT_EXCEPTIONSUMMARY =
		"mailtracking.mra.gpareporting.printexceptionsummary";
    private static final String LIST_INVOICDETAILS_BASE = "mailtracking.mra.gpareporting.listInvoicDetailsBase";
    private static final String LIST_INVOICDETAILS = "mailtracking.mra.gpareporting.listInvoicDetails";
    private static final String LIST_INVOICDETAILS_JOIN1= "mailtracking.mra.gpareporting.listInvoicDetailsJoin1";
    private static final String LIST_INVOICDETAILS_JOIN2= "mailtracking.mra.gpareporting.listInvoicDetailsJoin2";
    private static final String SAVE_GROUP_REMARKS_PROCEDURE = "mail.mra.gpareporting.savegroupremarks";
    private static final String FIND_INVOIC_LOV = "mail.mra.gpareporting.findinvoiclov";
    private static final String LIST_INVOIC_BASE="mailtracking.mra.gpareporting.ux.listInvoic";
	private static final String MRA_GPAREPORTING_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	private static final String MRA_GPAREPORTING_SUFFIX_QUERY=") RESULT_TABLE";
	private static final String INVOIC_ENQUIRY_BUCKET_WISE_SUMS = "mail.mra.gpareporting.findbucketwisesums";
	private static final String INVOIC_ENQUIRY_BUCKET_WISE_SUMS_NAVIGATION = "mail.mra.gpareporting.findbucketwisesumsnavigation";
	private static final String LIST_GENCLAIM_BASE="mailtracking.mra.gpareporting.ux.listgenclaimdtls";
	private static final String LIST_CLAIMDTLS_BASE="mailtracking.mra.gpareporting.ux.listclaimdtls";
	private static final String FIND_SERIAL_NUMBER_FROM_INVOIC="mail.mra.gpareporting.findserialnumberfrominvoic";
private static final String INVOIC_PROCESS = "mailtracking.mra.gpareporting.processinvoic";
	private static final String INVOIC_REPROCESS = "mailtracking.mra.gpareporting.processinvoicformails";
	private static final String INVOIC_REPROCESS_INV = "mailtracking.mra.gpareporting.processinvoicforinvoice";
	private static final String INVOIC_COUNT = "mailtracking.mra.gpareporting.invoicprocesscount";
	private static final String LIST_INVOIC_UNION="mailtracking.mra.gpareporting.ux.listInvoicUnion";
	private static final String INVOIC_AUTO_PROCESSING = "mailtracking.mra.gpareporting.checkautoprocessing";
	private static final String UPDATE_BATCH = "mailtracking.mra.gpareporting.updatebatchnumber";
	private static final String FIND_BATCH = "mailtracking.mra.gpareporting.findbatchnumber";
	private static final String UPDATE_INVOIC_STATUS = "mailtracking.mra.gpareporting.updateinvoicstatus";
	private static final String INVOIC_AND_CLAIM_DETAILS = "mailtracking.mra.gpareporting.findInvoicAndClaimDetails";
private static final String SAVE_CLAIMS_DETAILS = "mailtracking.mra.gpareporting.saveclaimdetails";
	private static final String FIND_CLAIMS_DETAILS ="mailtracking.mra.gpareporting.findmailbagsforclaim";
	private static final String FIND_MAILS_FORCLAIMGEN = "mailtracking.mra.gpareporting.findmailbagsforclaimgeneration";
private static final String FIND_GENERATED_RESDIT_MSG = "mailtracking.mra.gpareporting.findgeneratedresditmessage";
private static final String MRA_DEFAULTS_INVOIC_ACC_PROCEDURE = "mailtracking.mra.gpareporting.triggerinvoicacc";
private static final String FIND_CLAIM_REFNUM = "mailtracking.mra.gpareporting.findClaimReferenceNumber";
private static final String FIND_CLAIM_MASTERDETAILS = "mailtracking.mra.gpareporting.findClaimMasterDetails";
private static final String LIST_CLAIMDTLS_FOR_EDI_GENERATION="mailtracking.mra.gpareporting.ux.findclaimdtlsforEDIgeneration";
private static final String LIST_INVOIC_OUTER_QUERY="mailtracking.mra.gpareporting.listinvoicouterquery";
private static final String LIST_INVOIC_GROUP_BY_QUERY="mailtracking.mra.gpareporting.listinvoicgroupbyquery";
private static final String FIND_INVOICS_BY_FILENAME="mailtracking.mra.gpareporting.findinvoicsbyfilename";
private static final String FIND_CLAIMS_DETAILS_INT="mailtracking.mra.gpareporting.findmailbagsforclaimforinternational";
private static final String FIND_REJECT_MAILBAGS_COUNT="mailtracking.mra.gpareporting.findmailbagsforrejection";
private static final String INVOIC_PROCESS_SCH = "mailtracking.mra.gpareporting.processinvoicjob";
private static final String INVOIC_UPDATE_STATUS = "mailtracking.mra.gpareporting.updateconcurrentstatus";
private static final String EMPTY = "";
private static final String MRA_REPORTING_SQLDAO = "MRAGPAReportingSqlDAO";
	/**
     * @param gpaReportFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Page<GPAReportingDetailsVO> findGPAReportingDetails(
            GPAReportingFilterVO gpaReportFilterVO)
            throws SystemException, PersistenceException {
        log.entering("MRAGPAReportingSqlDAO", "findGPAReportingDetails");
        Page<GPAReportingDetailsVO> gpaReportingDetailsVOs = null;
        String baseQuery = getQueryManager()
                .getNamedNativeQueryString(FIND_GPA_REPORTING_DETAILS);
        Query query = new GPAReportingDetailsFilterQuery(baseQuery,
                gpaReportFilterVO);
        PageableQuery<GPAReportingDetailsVO> pageableQuery =
                new PageableQuery<GPAReportingDetailsVO>(query,
                        new GPAReportingDetailsMultiMapper());
        gpaReportingDetailsVOs = pageableQuery.getPageAbsolute(
                gpaReportFilterVO.getPageNumber(),
                gpaReportFilterVO.getAbsoluteIndex());
        log.exiting("MRAGPAReportingSqlDAO---", "findGPAReportingDetails");
        return gpaReportingDetailsVOs;
    }
    /**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    public Page<GPAReportingClaimDetailsVO> findClaimDetails(
			GPAReportingFilterVO gpaReportingFilterVO)
			throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO","findClaimDetails");
		String baseQuery=getQueryManager().getNamedNativeQueryString(FIND_CLAIM_DETAILS);
		Page<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
		Query query=new GpaReportingClaimDetailsFilterQuery(baseQuery,gpaReportingFilterVO);
		log.log(Log.INFO, "Final Query-->", query);
		PageableQuery<GPAReportingClaimDetailsVO> pageableQuery=new
		 PageableQuery<GPAReportingClaimDetailsVO>(query,new GPAReportingClaimDetailsMapper());
		 gpaReportingClaimDetailsVOs=pageableQuery.getPage(gpaReportingFilterVO.getPageNumber());
		 log.log(Log.INFO, "result in SQl DAO-->", gpaReportingClaimDetailsVOs);
		log.exiting("MRAGPAReportingSqlDAO","findClaimDetails");

		return gpaReportingClaimDetailsVOs;
	}

    /**
     * @author a-2270
     * @param gpaReportingFilterVO
     * @throws SystemException
     * @throws PersistenceException
     *
     */

    public String processGpaReport(GPAReportingFilterVO gpaReportingFilterVO)
	throws SystemException,PersistenceException {
		log.entering("MRAGPAReportingSqlDAO","processGpaReport");
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
				MTKGPARPT_PROCESS_GPAREPORT);
		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
//		String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
//		log.log(Log.FINE, "Current Date: "+dateString);
//		//currentDate.setDate(dateString, "dd-MMM-yyyy HH:mm");
		burstProcedure.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		burstProcedure.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		burstProcedure.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom().toSqlDate());
		burstProcedure.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo().toSqlDate());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setParameter(++index,gpaReportingFilterVO.getLastUpdatedUser());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setOutParameter(++index,SqlType.STRING);
		burstProcedure.execute();
		log.log(Log.FINE,"executed Procedure-->");

		String outParameter = (String)burstProcedure.getParameter(8);
		log.log(Log.FINE, "outParameter is >>>>>>>>>>>", outParameter);
		log.exiting("MRAGPAReportingSqlDAO", "processGpaReport");
		return outParameter;
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeDetails(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","printExceptionsReportAssigneeDetails");
		Query query=getQueryManager().createNamedNativeQuery(PRINT_EXCEPTIONSASSIGNEEDETAILS);
		Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
		int index=0;
		query.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		query.setParameter(++index,gpaReportingFilterVO.getAssignee());
		if(gpaReportingFilterVO.getPoaCode()!=null
						&& gpaReportingFilterVO.getPoaCode().trim().length()>0){
			query.append(" AND CLM.POACOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		}
		if(gpaReportingFilterVO.getPoaName()!=null
				&& gpaReportingFilterVO.getPoaName().trim().length()>0){
			query.append(" AND MST.POANAM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaName());
		}
		if(gpaReportingFilterVO.getReportingPeriodFrom()!=null){
			query.append(" AND CLM.REPPRDFRM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom());
		}
		if(gpaReportingFilterVO.getReportingPeriodTo()!=null){
			query.append(" AND CLM.REPPRDTO = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo());
		}
		if(gpaReportingFilterVO.getCountry()!=null
				&& gpaReportingFilterVO.getCountry().trim().length()>0){
			query.append(" AND MST.CNTCOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getCountry());
		}
		if(gpaReportingFilterVO.getExceptionCode()!=null
				&& gpaReportingFilterVO.getExceptionCode().trim().length()>0){
			query.append(" AND CLM.EXPCODE = ?");
			query.setParameter(++index,gpaReportingFilterVO.getExceptionCode());
		}
		log.log(Log.INFO, "Final Query-->", query);
		gpaReportingClaimDetailsVOs=query.getResultList(new GPAReportsMapper());
		log.exiting("MRAGPAReportingSqlDAO","printExceptionsReportAssigneeDetails");
		return gpaReportingClaimDetailsVOs;
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeSummary(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","printExceptionsReportAssigneeSummary");
		Query query=getQueryManager().createNamedNativeQuery(PRINT_EXCEPTIONSASSIGNEESUMMARY);
		Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
		int index=0;
		query.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		query.setParameter(++index,gpaReportingFilterVO.getAssignee());
		if(gpaReportingFilterVO.getPoaCode()!=null
						&& gpaReportingFilterVO.getPoaCode().trim().length()>0){
			query.append(" AND CLM.POACOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		}
		if(gpaReportingFilterVO.getPoaName()!=null
				&& gpaReportingFilterVO.getPoaName().trim().length()>0){
			query.append(" AND MST.POANAM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaName());
		}
		if(gpaReportingFilterVO.getReportingPeriodFrom()!=null){
			query.append(" AND CLM.REPPRDFRM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom());
		}
		if(gpaReportingFilterVO.getReportingPeriodTo()!=null){
			query.append(" AND CLM.REPPRDTO = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo());
		}
		if(gpaReportingFilterVO.getCountry()!=null
				&& gpaReportingFilterVO.getCountry().trim().length()>0){
			query.append(" AND MST.CNTCOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getCountry());
		}
		if(gpaReportingFilterVO.getExceptionCode()!=null
				&& gpaReportingFilterVO.getExceptionCode().trim().length()>0){
			query.append(" AND CLM.EXPCODE = ?");
			query.setParameter(++index,gpaReportingFilterVO.getExceptionCode());
		}
		query.append(" GROUP BY (CLM.ASDUSR, CLM.EXPCODE, CLM.POACOD, MST.POANAM,")
			 .append(" MST.CNTCOD, CLM.REPPRDFRM, CLM.REPPRDTO, CLM.EXPCODE)");
		log.log(Log.INFO, "Final Query-->", query);
		gpaReportingClaimDetailsVOs=query.getResultList(new GPAReportsMapper());
		log.exiting("MRAGPAReportingSqlDAO","printExceptionsReportAssigneeSummary");
		return gpaReportingClaimDetailsVOs;
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportDetails(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","printExceptionsReportDetails");
		Query query=getQueryManager().createNamedNativeQuery(PRINT_EXCEPTIONDETAILS);
		Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
		int index=0;
		query.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		if(gpaReportingFilterVO.getPoaCode()!=null
						&& gpaReportingFilterVO.getPoaCode().trim().length()>0){
			query.append(" AND CLM.POACOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		}
		if(gpaReportingFilterVO.getPoaName()!=null
				&& gpaReportingFilterVO.getPoaName().trim().length()>0){
			query.append(" AND MST.POANAM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaName());
		}
		if(gpaReportingFilterVO.getReportingPeriodFrom()!=null){
			query.append(" AND CLM.REPPRDFRM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom());
		}
		if(gpaReportingFilterVO.getReportingPeriodTo()!=null){
			query.append(" AND CLM.REPPRDTO = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo());
		}
		if(gpaReportingFilterVO.getCountry()!=null
				&& gpaReportingFilterVO.getCountry().trim().length()>0){
			query.append(" AND MST.CNTCOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getCountry());
		}
		if(gpaReportingFilterVO.getExceptionCode()!=null
				&& gpaReportingFilterVO.getExceptionCode().trim().length()>0){
			query.append(" AND CLM.EXPCODE = ?");
			query.setParameter(++index,gpaReportingFilterVO.getExceptionCode());
		}
		if(gpaReportingFilterVO.getAssignee()!=null
				&& gpaReportingFilterVO.getAssignee().trim().length()>0){
			query.append(" AND CLM.ASDUSR = ?");
			query.setParameter(++index,gpaReportingFilterVO.getAssignee());
		}
		query.append(" GROUP BY (CLM.ASDUSR, CLM.POACOD, MST.POANAM,")
			 .append(" MST.CNTCOD, CLM.REPPRDFRM, CLM.REPPRDTO, CLM.EXPCODE, CLM.DSN)");
		log.log(Log.INFO, "Final Query-->", query);
		gpaReportingClaimDetailsVOs=query.getResultList(new GPAReportsMapper());
		log.exiting("MRAGPAReportingSqlDAO","printExceptionsReportDetails");
		return gpaReportingClaimDetailsVOs;
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportSummary(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","printExceptionsReportSummary");
		Query query=getQueryManager().createNamedNativeQuery(PRINT_EXCEPTIONSUMMARY);
		Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
		int index=0;
		query.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		if(gpaReportingFilterVO.getPoaCode()!=null
						&& gpaReportingFilterVO.getPoaCode().trim().length()>0){
			query.append(" AND CLM.POACOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		}
		if(gpaReportingFilterVO.getPoaName()!=null
				&& gpaReportingFilterVO.getPoaName().trim().length()>0){
			query.append(" AND MST.POANAM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getPoaName());
		}
		if(gpaReportingFilterVO.getReportingPeriodFrom()!=null){
			query.append(" AND CLM.REPPRDFRM = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom());
		}
		if(gpaReportingFilterVO.getReportingPeriodTo()!=null){
			query.append(" AND CLM.REPPRDTO = ?");
			query.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo());
		}
		if(gpaReportingFilterVO.getCountry()!=null
				&& gpaReportingFilterVO.getCountry().trim().length()>0){
			query.append(" AND MST.CNTCOD = ?");
			query.setParameter(++index,gpaReportingFilterVO.getCountry());
		}
		if(gpaReportingFilterVO.getExceptionCode()!=null
				&& gpaReportingFilterVO.getExceptionCode().trim().length()>0){
			query.append(" AND CLM.EXPCODE = ?");
			query.setParameter(++index,gpaReportingFilterVO.getExceptionCode());
		}
		query.append(" GROUP BY (CLM.POACOD, MST.POANAM, MST.CNTCOD, CLM.REPPRDFRM, CLM.REPPRDTO, CLM.EXPCODE) ");
		log.log(Log.INFO, "Final Query-->", query);
		gpaReportingClaimDetailsVOs=query.getResultList(new GPAReportsMapper());
		log.exiting("MRAGPAReportingSqlDAO","printExceptionsReportSummary");
		return gpaReportingClaimDetailsVOs;
	}

    /**
     * @author A-8464
     * @param InvoicFilterVO
     * @return Page<InvoicDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    public Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO)
			throws PersistenceException, SystemException {

		log.entering("MRAGPAReportingSqlDAO","listInvoicDetails");
		Page<InvoicDetailsVO> invoicDetailsVOs=null;
		int index=0;
		boolean queryRequired=false;
		boolean baseQueryRequired=false;
		boolean awaitingInvoicFilterRequired=false;
		boolean dummyOriginFilterRequired=false;
		boolean dummyDestinationFilterRequired=false;
		boolean baseQueryRequiredInitial=false;
		boolean baseQueryForClaimStatus=true;
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		StringBuilder qry = new StringBuilder().append(MRA_GPAREPORTING_ROWNUM_RANK_QUERY);
		PageableNativeQuery<InvoicDetailsVO> finalquery = null;
		//String invoicSplit[] = null;
		//if(invoicFilterVO.getInvoicId() != null && invoicFilterVO.getInvoicId().length() > 0){
		//	invoicSplit = invoicFilterVO.getInvoicId().split(" ");
		//}
		//String invoicID = null;
		//if(invoicSplit != null && invoicSplit.length >= 2){
		//	invoicID =  invoicSplit[1];
		//}else{
		//	invoicID = invoicFilterVO.getInvoicId();
		//}
		if (invoicFilterVO.getSelectedClaimRange() != null && invoicFilterVO.getSelectedClaimRange().size() > 0) {
				for(String claimRange : invoicFilterVO.getSelectedClaimRange()){
					String frmAmt = claimRange.split("-")[0];
					String toAmt =  claimRange.split("-")[1];
					if("0".equals(frmAmt) && "5".equals(toAmt)){
						queryRequired=true;
					}
				}
		}
		if(invoicFilterVO.getSelectedProcessStatus()!=null && invoicFilterVO.getSelectedProcessStatus().size()>0){
			if (invoicFilterVO.getSelectedProcessStatus().contains("SUB")
					|| invoicFilterVO.getSelectedProcessStatus().contains("GEN")) {
				baseQueryForClaimStatus=false;
			}
			for(String processStatus:invoicFilterVO.getSelectedProcessStatus()){
				if("AWTINC".equals(processStatus) || "DUMMYORG".equals(processStatus) || "DUMMYDST".equals(processStatus) || "DLVSCNWRG".equals(processStatus)){
					baseQueryRequired=true;
					if("AWTINC".equals(processStatus) ){
					awaitingInvoicFilterRequired=true;
					}
					if("DUMMYORG".equals(processStatus)){
					dummyOriginFilterRequired=true;
					}
					if("DUMMYDST".equals(processStatus)){
						dummyDestinationFilterRequired=true;
					}
				}

			}
		} else {
			baseQueryRequiredInitial=true;
		}

		if (invoicFilterVO.getInvoicId() == null
				&& ((invoicFilterVO.getSelectedClaimRange() == null
						|| invoicFilterVO.getSelectedClaimRange().size() == 0)
						|| (invoicFilterVO.getSelectedClaimRange() != null
								&& invoicFilterVO.getSelectedClaimRange().size() > 0 && queryRequired))
				&& (baseQueryRequired || baseQueryRequiredInitial) && baseQueryForClaimStatus){
			String outerQuery = getQueryManager().getNamedNativeQueryString(LIST_INVOIC_OUTER_QUERY);
		String basqry = getQueryManager().getNamedNativeQueryString(LIST_INVOICDETAILS_BASE);
			qry.append(outerQuery);
		qry.append(basqry);
		if(invoicFilterVO.getDefaultPageSize()==0){
			invoicFilterVO.setDefaultPageSize(10);
    	}
			finalquery = new PageableNativeQuery<InvoicDetailsVO>(invoicFilterVO.getDefaultPageSize(), -1,
					qry.toString(), new GPAReportingInvoicDetailsMapper());

			if (invoicFilterVO.getCmpcod() != null && invoicFilterVO.getCmpcod().trim().length() > 0) {
			finalquery.append(" AND GPABLGDTL.CMPCOD = ?");
			finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
		}

		//3 mandatory params - poa, from and to date

			if (invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length() > 0) {
			finalquery.append(" AND GPABLGDTL.UPDBILTOOPOA = ?");
			finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
		}
			if(!awaitingInvoicFilterRequired && dummyOriginFilterRequired && dummyDestinationFilterRequired){
				finalquery.append(" AND ( BLGMST.ORGARPCOD = 'XXX' OR  BLGMST.DSTARPCOD = 'XXX' )");
			}
			else if(!awaitingInvoicFilterRequired && dummyOriginFilterRequired){
				finalquery.append(" AND  BLGMST.ORGARPCOD = 'XXX' ");
			}
			else if(!awaitingInvoicFilterRequired && dummyDestinationFilterRequired){
				finalquery.append(" AND  BLGMST.DSTARPCOD = 'XXX' ");
			}else if(awaitingInvoicFilterRequired && dummyDestinationFilterRequired && !dummyOriginFilterRequired){
				finalquery.append(" AND  BLGMST.ORGARPCOD <> 'XXX' ");
			}
			else if(awaitingInvoicFilterRequired && dummyOriginFilterRequired && !dummyDestinationFilterRequired){
				finalquery.append(" AND  BLGMST.DSTARPCOD <> 'XXX' ");
		}
			else{
				//No filter condition is required
			}
			if (invoicFilterVO.getFromDate() != null && invoicFilterVO.getToDate() != null
					&& invoicFilterVO.getFromDate().trim().length() > 0
					&& invoicFilterVO.getToDate().trim().length() > 0) {
				fromDate.setDate(invoicFilterVO.getFromDate());
				toDate.setDate(invoicFilterVO.getToDate());
				finalquery.append(" AND TO_NUMBER(TO_CHAR(BLGMST.RCVDAT, 'YYYYMMDD'))  BETWEEN ? AND ? ");
				finalquery.setParameter(++index, Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
				finalquery.setParameter(++index, Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}

		//optional para
			if (invoicFilterVO.getMailbagId() != null && invoicFilterVO.getMailbagId().trim().length() > 0) {
			finalquery.append(" AND MALMST.MALIDR = ?");
			finalquery.setParameter(++index,invoicFilterVO.getMailbagId());
		}
		//...Origin
		if(invoicFilterVO.getOrg()!=null && invoicFilterVO.getOrg().size()>0){
			finalquery.append("AND MALMST.ORGCOD IN ( ");
			int count = 0;
			for(String origin : invoicFilterVO.getOrg()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, origin);
					if (count < invoicFilterVO.getOrg().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}

		//...Destination
		if(invoicFilterVO.getDest()!=null && invoicFilterVO.getDest().size()>0){
			finalquery.append("AND MALMST.DSTCOD IN ( ");
			int count = 0;
			for(String dest : invoicFilterVO.getDest()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, dest);
					if (count < invoicFilterVO.getDest().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}

		//..Mail Sub class
		if(invoicFilterVO.getMailSubClass()!=null && invoicFilterVO.getMailSubClass().size()>0){
			finalquery.append("AND MALMST.MALSUBCLS IN ( ");
			int count = 0;
			for(String subcls : invoicFilterVO.getMailSubClass()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, subcls);
					if (count < invoicFilterVO.getMailSubClass().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}
		}
		Query query=getQueryManager().createNamedNativeQuery(LIST_INVOICDETAILS);
		if(invoicFilterVO.getFromScreen() !=null && invoicFilterVO.getFromScreen().equals("MRA078")) {
		query.append("STLDTL.INVAMT TOTSTLAMT,STLDTL.UPDCLMAMT CLMAMT,STLDTL.PROSTA,STLDTL.INVPAYSTA,STLDTL.CLMSTA,STLDTL.RMK,STLDTL.VERNUM,INCDTL.SERNUM ");
		}
		else {
			query.append("INCDTL.INVAMT, INCDTL.UPDCLMAMT CLMAMT,INCDTL.PROSTA,INCDTL.INVPAYSTA,INCDTL.CLMSTA,INCDTL.RMK, INCDTL.VERNUM,INCDTL.SERNUM,INCDTL.TOTSTLAMT");
	    }
		query.append(getQueryManager().getNamedNativeQueryString(LIST_INVOICDETAILS_JOIN1));
		if(invoicFilterVO.getInvoicId()==null) {
			query.append("AND INCDTL.SERNUM =  STLDTL.SERNUM");
		}
		String queryJoin = getQueryManager().getNamedNativeQueryString(LIST_INVOICDETAILS_JOIN2);
		query.append(queryJoin);
		if(finalquery ==  null ){
			qry.append(query);
			finalquery = new PageableNativeQuery<InvoicDetailsVO>(invoicFilterVO.getDefaultPageSize(),
					invoicFilterVO.getTotalRecords(), qry.toString(), new GPAReportingInvoicDetailsMapper());
		}else{
			finalquery.append(" UNION ALL ");
			finalquery.append(query.toString());
		}
		if (invoicFilterVO.getCmpcod() != null && invoicFilterVO.getCmpcod().trim().length() > 0) {
			finalquery.append(" WHERE INCDTL.CMPCOD = ?");
			finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
		}

		//3 mandatory params - poa, from and to date

		if (invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length() > 0) {
			finalquery.append(" AND INCDTL.POACOD    = ?");
			finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
		}



		//optional
		if (invoicFilterVO.getMailbagId() != null && invoicFilterVO.getMailbagId().trim().length() > 0) {
			finalquery.append(" AND INCDTL.MALIDR = ?");
			finalquery.setParameter(++index,invoicFilterVO.getMailbagId());
		}
		
		
			finalquery.append(" AND INCDTL.PROSTA  NOT IN('AWTINC') ");
		

		if (invoicFilterVO.getInvoicId() != null && invoicFilterVO.getInvoicId().trim().length() > 0) {
			//if(invoicFilterVO.getFromScreen() !=null && invoicFilterVO.getFromScreen().equals("List_INVOIC_SCREEN")) {
			//finalquery.append(" AND STLDTL.INVREFNUM = ?");
			//finalquery.setParameter(++index,invoicFilterVO.getInvoicId());
			//}
			//else {
				finalquery.append(" AND STLDTL.INVREFNUM = ?");
				finalquery.setParameter(++index,invoicFilterVO.getInvoicId());
			//}
		} else {
			if(invoicFilterVO.getFromDate()!=null && invoicFilterVO.getToDate()!=null
					&& invoicFilterVO.getFromDate().trim().length() > 0 && invoicFilterVO.getToDate().trim().length() > 0) {
				fromDate.setDate(invoicFilterVO.getFromDate());
				toDate.setDate(invoicFilterVO.getToDate());
				finalquery.append(" AND TO_NUMBER(TO_CHAR(INCDTL.RCVDAT, 'YYYYMMDD'))  BETWEEN ? AND ? ");
				finalquery.setParameter(++index,Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
				finalquery.setParameter(++index,Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)));
			}
		}


		//Filter panel

		//.....Process status
		if(invoicFilterVO.getSelectedProcessStatus()!=null && invoicFilterVO.getSelectedProcessStatus().size()>0){

			Collection<String> clmstas = new ArrayList<String>();
			Collection<String> prostas = new ArrayList<String>();
			for(String status:invoicFilterVO.getSelectedProcessStatus()){
				if(status.equals("GEN") || status.equals("SUB")){
					clmstas.add(status);
				} else {
					prostas.add(status);
				}
			}

			if (prostas.size() > 0) {
				if(!clmstas.isEmpty())
					finalquery.append("AND ( ( ");
				else
					finalquery.append("AND ");
				finalquery.append(" INCDTL.PROSTA IN ( ");
			int count = 0;
			for(String prosta : prostas){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, prosta);
					if (count < prostas.size()) {
					finalquery.append(" ,");
				}
			}
			finalquery.append(" ) ");
			finalquery.append(" AND INCDTL.CLMSTA IS NULL ");
			if(!clmstas.isEmpty())
				finalquery.append(" ) ");
		}

			if (clmstas.size() > 0) {
				if(!prostas.isEmpty())
					finalquery.append(" OR ");
				else
					finalquery.append(" AND ");
				finalquery.append(" INCDTL.CLMSTA IN ( ");
			int count = 0;
			for(String clmsta : clmstas){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, clmsta);
					if (count < clmstas.size()) {
					finalquery.append(" ,");
				}
			}
			finalquery.append(")");
			if(!prostas.isEmpty())
				finalquery.append(")");

		}


	  }

	//.........Claim range
		if (invoicFilterVO.getSelectedClaimRange() != null && invoicFilterVO.getSelectedClaimRange().size() > 0) {
			finalquery.append("AND (");
			int count=0;
				for(String claimRange : invoicFilterVO.getSelectedClaimRange()){
					++count;
					String frmAmt = claimRange.split("-")[0];
					String toAmt =  claimRange.split("-")[1];
					if(!"501".equals(frmAmt)){
					finalquery.append(" INCDTL.CLMAMT >="+frmAmt+" AND INCDTL.CLMAMT <="+toAmt+"");
				} else {
						finalquery.append(" INCDTL.CLMAMT >="+frmAmt);
					}
				if (count < invoicFilterVO.getSelectedClaimRange().size()) {
						finalquery.append(" OR");
					}
				}
				finalquery.append(")");
		}


		//...Origin
		if(invoicFilterVO.getOrg()!=null && invoicFilterVO.getOrg().size()>0){
			finalquery.append("AND MST.ORGCOD IN ( ");
			int count = 0;
			for(String origin : invoicFilterVO.getOrg()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, origin);
				if (count < invoicFilterVO.getOrg().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}

		//...Destination
		if(invoicFilterVO.getDest()!=null && invoicFilterVO.getDest().size()>0){
			finalquery.append("AND MST.DSTCOD IN ( ");
			int count = 0;
			for(String dest : invoicFilterVO.getDest()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, dest);
				if (count < invoicFilterVO.getDest().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}

		//..Mail Sub class
		if(invoicFilterVO.getMailSubClass()!=null && invoicFilterVO.getMailSubClass().size()>0){
			finalquery.append("AND MST.MALSUBCLS IN ( ");
			int count = 0;
			for(String subcls : invoicFilterVO.getMailSubClass()){
				++count;
				finalquery.append("?");
				finalquery.setParameter(++index, subcls);
				if (count < invoicFilterVO.getMailSubClass().size()) {
					finalquery.append(" ,");
				}
			}

			finalquery.append(" )");
		}

		 log.log(Log.INFO, "Final Query-->", finalquery);

		if (invoicFilterVO.getInvoicId() == null
				&& ((invoicFilterVO.getSelectedClaimRange() == null
						|| invoicFilterVO.getSelectedClaimRange().size() == 0)
						|| (invoicFilterVO.getSelectedClaimRange() != null
								&& invoicFilterVO.getSelectedClaimRange().size() > 0 && queryRequired))
				&& (baseQueryRequired || baseQueryRequiredInitial) && baseQueryForClaimStatus) {
		finalquery.append(")MST");
		}
		 finalquery.append(MRA_GPAREPORTING_SUFFIX_QUERY);

		 invoicDetailsVOs=finalquery.getPage(invoicFilterVO.getPageNumber());

		// adding bucket wise sums
	 if(invoicDetailsVOs != null){
			if (!invoicDetailsVOs.isEmpty() && invoicDetailsVOs.get(0) != null) {
				Query claimRangeSumsQuery = getBucketWiseSumsQuery(invoicFilterVO);

				List<InvoicDetailsVO> claimSumDetails = claimRangeSumsQuery
						.getResultList(new Mapper<InvoicDetailsVO>() {
			        public InvoicDetailsVO map(ResultSet rs) throws SQLException {
			        	 InvoicDetailsVO claimSumDetail = new InvoicDetailsVO();
								// claimSumDetail.setCntawtinc(rs.getString("CNTOVRNOTMRA"));
								if(rs.getString("CNTOVRNOTMRA") == null){
									claimSumDetail.setCntovrnotmra("0");
								}else{
								claimSumDetail.setCntovrnotmra(rs.getString("CNTOVRNOTMRA"));
								}
								if(rs.getString("CLMZROPAY") == null){
									claimSumDetail.setClmzropay("0");
								}else{
								claimSumDetail.setClmzropay(rs.getString("CLMZROPAY"));
								}
								if(rs.getString("CLMNOINC") == null){
									claimSumDetail.setClmnoinc("0");
								}else{
								claimSumDetail.setClmnoinc(rs.getString("CLMNOINC"));
								}
								if(rs.getString("CLMRATDIF") == null){
									claimSumDetail.setClmratdif("0");
								}else{
								claimSumDetail.setClmratdif(rs.getString("CLMRATDIF"));
								}
								if(rs.getString("CLMWGTDIF") == null){
									claimSumDetail.setClmwgtdif("0");
								}else{
								claimSumDetail.setClmwgtdif(rs.getString("CLMWGTDIF"));
								}
								if(rs.getString("CLMMISSCN") == null){
									claimSumDetail.setClmmisscn("0");
								}else{
								claimSumDetail.setClmmisscn(rs.getString("CLMMISSCN"));
								}
								if(rs.getString("CLMLATDLV") == null){
									claimSumDetail.setClmlatdlv("0");
								}else{
								claimSumDetail.setClmlatdlv(rs.getString("CLMLATDLV"));
								}
								if(rs.getString("CLMSRVRSP") == null){
									claimSumDetail.setClmsrvrsp("0");
								}else{
								claimSumDetail.setClmsrvrsp(rs.getString("CLMSRVRSP"));
								}
								if(rs.getString("LATDLV") == null){
									claimSumDetail.setLatdlv("0");
								}else{
								claimSumDetail.setLatdlv(rs.getString("LATDLV"));
								}
								if(rs.getString("MISORGSCN") == null){
									claimSumDetail.setMisorgscn("0");
								}else{
								claimSumDetail.setMisorgscn(rs.getString("MISORGSCN"));
								}
								if(rs.getString("MISDSTSCN") == null){
									claimSumDetail.setMisdstscn("0");
								}else{
								claimSumDetail.setMisdstscn(rs.getString("MISDSTSCN"));
								}
								if(rs.getString("FULPAID") == null){
									claimSumDetail.setFulpaid("0");
								}else{
								claimSumDetail.setFulpaid(rs.getString("FULPAID"));
								}
								if(rs.getString("OVRRATDIF") == null){
									claimSumDetail.setOvrratdif("0");
								}else{
								claimSumDetail.setOvrratdif(rs.getString("OVRRATDIF"));
								}
								if(rs.getString("OVRWGTDIF") == null){
									claimSumDetail.setOvrwgtdif("0");
								}else{
								claimSumDetail.setOvrwgtdif(rs.getString("OVRWGTDIF"));
								}
								if(rs.getString("OVRCLSDIF") == null){
									claimSumDetail.setOvrclsdif("0");
								}else{
								claimSumDetail.setOvrclsdif(rs.getString("OVRCLSDIF"));
								}
								if(rs.getString("OVRSRVRSP") == null){
									claimSumDetail.setOvrsrvrsp("0");
								}else{
								claimSumDetail.setOvrsrvrsp(rs.getString("OVRSRVRSP"));
								}
								if(rs.getString("OVROTH") == null){
									claimSumDetail.setOvroth("0");
								}else{
								claimSumDetail.setOvroth(rs.getString("OVROTH"));
								}
								if(rs.getString("CLMOTH") == null){
									claimSumDetail.setClmoth("0");
								}else{
								claimSumDetail.setClmoth(rs.getString("CLMOTH"));
								}
								if(rs.getString("CLMNOTINV") == null){
									claimSumDetail.setClmnotinv("0");
								}else{
								claimSumDetail.setClmnotinv(rs.getString("CLMNOTINV"));
								}
								if(rs.getString("OVRPAYACP") == null){
									claimSumDetail.setOvrpayacp("0");
								}else{
								claimSumDetail.setOvrpayacp(rs.getString("OVRPAYACP"));
								}
								if(rs.getString("OVRPAYREJ") == null){
									claimSumDetail.setOvrpayrej("0");
								}else{
								claimSumDetail.setOvrpayrej(rs.getString("OVRPAYREJ"));
								}
								if(rs.getString("CLMFRCMJR") == null){
									claimSumDetail.setClmfrcmjr("0");
								}else{
								claimSumDetail.setClmfrcmjr(rs.getString("CLMFRCMJR"));
								}
								if(rs.getString("SHRPAYACP") == null){
									claimSumDetail.setShrpayacp("0");
								}else{
									claimSumDetail.setShrpayacp(rs.getString("SHRPAYACP"));
								}
								if(rs.getString("CLMSTAGEN") == null){
									claimSumDetail.setClmstagen("0");
								}else{
								claimSumDetail.setClmstagen(rs.getString("CLMSTAGEN"));
								}
								if(rs.getString("CLMSTASUB") == null){
									claimSumDetail.setClmstasub("0");
								}else{
								claimSumDetail.setClmstasub(rs.getString("CLMSTASUB"));
								
								}
								if(rs.getString("AMOTOBEACT") ==null) {
									claimSumDetail.setAmotobeact("0");
								}else {
								claimSumDetail.setAmotobeact(rs.getString("AMOTOBEACT"));
								}
							    if(rs.getString("AMOTACT") == null) {
								claimSumDetail.setAmotact("0");
							    }else {
							    claimSumDetail.setAmotact(rs.getString("AMOTACT"));
								}
						return claimSumDetail;

							}
						});

				invoicDetailsVOs.get(0).setCntovrnotmra(claimSumDetails.get(0).getCntovrnotmra());
				invoicDetailsVOs.get(0).setClmzropay(claimSumDetails.get(0).getClmzropay());
				invoicDetailsVOs.get(0).setClmnoinc(claimSumDetails.get(0).getClmnoinc());
				invoicDetailsVOs.get(0).setClmratdif(claimSumDetails.get(0).getClmratdif());
				invoicDetailsVOs.get(0).setClmwgtdif(claimSumDetails.get(0).getClmwgtdif());
				invoicDetailsVOs.get(0).setClmmisscn(claimSumDetails.get(0).getClmmisscn());
				invoicDetailsVOs.get(0).setClmlatdlv(claimSumDetails.get(0).getClmlatdlv());
				invoicDetailsVOs.get(0).setClmsrvrsp(claimSumDetails.get(0).getClmsrvrsp());
				invoicDetailsVOs.get(0).setLatdlv(claimSumDetails.get(0).getLatdlv());
				invoicDetailsVOs.get(0).setMisorgscn(claimSumDetails.get(0).getMisorgscn());
				invoicDetailsVOs.get(0).setMisdstscn(claimSumDetails.get(0).getMisdstscn());
				invoicDetailsVOs.get(0).setFulpaid(claimSumDetails.get(0).getFulpaid());
				invoicDetailsVOs.get(0).setOvrratdif(claimSumDetails.get(0).getOvrratdif());
				invoicDetailsVOs.get(0).setOvrwgtdif(claimSumDetails.get(0).getOvrwgtdif());
				invoicDetailsVOs.get(0).setOvrclsdif(claimSumDetails.get(0).getOvrclsdif());
				invoicDetailsVOs.get(0).setOvrsrvrsp(claimSumDetails.get(0).getOvrsrvrsp());
				invoicDetailsVOs.get(0).setOvroth(claimSumDetails.get(0).getOvroth());
				invoicDetailsVOs.get(0).setClmoth(claimSumDetails.get(0).getClmoth());
				invoicDetailsVOs.get(0).setClmnotinv(claimSumDetails.get(0).getClmnotinv());
				invoicDetailsVOs.get(0).setOvrpayacp(claimSumDetails.get(0).getOvrpayacp());
				invoicDetailsVOs.get(0).setOvrpayrej(claimSumDetails.get(0).getOvrpayrej());
				invoicDetailsVOs.get(0).setClmfrcmjr(claimSumDetails.get(0).getClmfrcmjr());
				invoicDetailsVOs.get(0).setShrpayacp(claimSumDetails.get(0).getShrpayacp());
				invoicDetailsVOs.get(0).setClmstagen(claimSumDetails.get(0).getClmstagen());
				invoicDetailsVOs.get(0).setClmstasub(claimSumDetails.get(0).getClmstasub());

				invoicDetailsVOs.get(0).setAmotobeact(claimSumDetails.get(0).getAmotobeact());
				invoicDetailsVOs.get(0).setAmotact(claimSumDetails.get(0).getAmotact());
		 }
		 }
		 log.log(Log.INFO, "result in SQl DAO-->", invoicDetailsVOs);

		 log.exiting("MRAGPAReportingSqlDAO","listInvoicDetails");

		return invoicDetailsVOs;
	}

    private Query getBucketWiseSumsQuery(InvoicFilterVO invoicFilterVO) throws SystemException{
    	Query query=null;
    	if(invoicFilterVO.getFromScreen() !=null && invoicFilterVO.getFromScreen().equals("MRA078")) {
    	 query=getQueryManager().createNamedNativeQuery(INVOIC_ENQUIRY_BUCKET_WISE_SUMS_NAVIGATION);
    	}
    	else {
    		 query=getQueryManager().createNamedNativeQuery(INVOIC_ENQUIRY_BUCKET_WISE_SUMS);
    		 if(invoicFilterVO.getInvoicId()==null) {
    				query.append("AND INCDTL.SERNUM =  STLDTL.SERNUM");
    		 }
    	}
    	int index = 0;
    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	String invoicSplit[] = null;
		if(invoicFilterVO.getInvoicId() != null && invoicFilterVO.getInvoicId().length() > 0){
			invoicSplit = invoicFilterVO.getInvoicId().split(" ");
		}
		String invoicID = null;
		if(invoicSplit != null && invoicSplit.length >= 3){
			invoicID =  invoicSplit[1];
		}else{
			invoicID = invoicFilterVO.getInvoicId();
		}
    	if(invoicFilterVO.getCmpcod() != null && invoicFilterVO.getCmpcod().trim().length()>0)
		{
    		query.append(" AND INCDTL.CMPCOD = ?");
    		query.setParameter(++index,invoicFilterVO.getCmpcod());
		}

		//3 mandatory params - poa, from and to date

		if(invoicFilterVO.getGpaCode() != null&& invoicFilterVO.getGpaCode().trim().length()>0)
		{
			query.append(" AND INCDTL.POACOD    = ?");
			query.setParameter(++index,invoicFilterVO.getGpaCode());
		}

		if(invoicFilterVO.getFromDate()!=null && invoicFilterVO.getToDate()!=null
				&& invoicFilterVO.getFromDate().trim().length()>0
				&& invoicFilterVO.getToDate().trim().length()>0)
		{
			fromDate.setDate(invoicFilterVO.getFromDate());
			toDate.setDate(invoicFilterVO.getToDate());
			query.append(" AND TO_NUMBER(TO_CHAR(INCDTL.RCVDAT, 'YYYYMMDD'))  BETWEEN ? AND ? ");
			query.setParameter(++index,Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
			query.setParameter(++index,Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		}

		//optional
		if(invoicFilterVO.getMailbagId() != null&& invoicFilterVO.getMailbagId().trim().length()>0)
		{
			query.append(" AND INCDTL.MALIDR = ?");
			query.setParameter(++index,invoicFilterVO.getMailbagId());
		}

		if(invoicFilterVO.getInvoicId() != null&& invoicFilterVO.getInvoicId().trim().length()>0)
		{
			query.append(" AND STLDTL.INVREFNUM = ?");
			query.setParameter(++index,invoicFilterVO.getInvoicId());
		}

		//....Process status
		/*if(invoicFilterVO.getSelectedProcessStatus()!=null && invoicFilterVO.getSelectedProcessStatus().size()>0){

			Collection<String> clmstas = new ArrayList<String>();
			Collection<String> prostas = new ArrayList<String>();
			for(String status:invoicFilterVO.getSelectedProcessStatus()){
				if(status.equals("GEN") || status.equals("SUB")){
					clmstas.add(status);
				}
				else{
					prostas.add(status);
				}


		}

		if(clmstas.size()>0)
		{	query.append("AND INCDTL.CLMSTA IN ( ");
			int count = 0;
			for(String clmsta : clmstas){
				++count;
				query.append("?");
				query.setParameter(++index, clmsta);
				if(count<invoicFilterVO.getSelectedProcessStatus().size())
				{
					query.append(" ,");
				}
			}

			query.append(" )");
		}


	  }*/


	  if(invoicFilterVO.getSelectedClaimRange()!=null && invoicFilterVO.getSelectedClaimRange().size()>0)
		{
			query.append("AND (");
			int count=0;
				for(String claimRange : invoicFilterVO.getSelectedClaimRange()){
					++count;
					String frmAmt = claimRange.split("-")[0];
					String toAmt =  claimRange.split("-")[1];
					query.append(" INCDTL.CLMAMT BETWEEN "+frmAmt+"AND "+toAmt+"");
					if(count<invoicFilterVO.getSelectedClaimRange().size())
					{
						query.append(" OR");
					}
				}
				query.append(")");
		}


		//.....Origin
		if(invoicFilterVO.getOrg()!=null && invoicFilterVO.getOrg().size()>0){
			query.append("AND MST.ORGARPCOD IN ( ");
			int count = 0;
			for(String origin : invoicFilterVO.getOrg()){
				++count;
				query.append("?");
				query.setParameter(++index, origin);
				if(count<invoicFilterVO.getOrg().size())
				{
					query.append(" ,");
				}
			}

			query.append(" )");
		}

		//....Destination
		if(invoicFilterVO.getDest()!=null && invoicFilterVO.getDest().size()>0){
			query.append("AND MST.DSTARPCOD IN ( ");
			int count = 0;
			for(String dest : invoicFilterVO.getDest()){
				++count;
				query.append("?");
				query.setParameter(++index, dest);
				if(count<invoicFilterVO.getDest().size())
				{
					query.append(" ,");
				}
			}

			query.append(" )");
		}

		//....Mail sub class
		if(invoicFilterVO.getMailSubClass()!=null && invoicFilterVO.getMailSubClass().size()>0){
			query.append("AND MST.MALSUBCLS IN ( ");
			int count = 0;
			for(String subcls : invoicFilterVO.getMailSubClass()){
				++count;
				query.append("?");
				query.setParameter(++index, subcls);
				if(count<invoicFilterVO.getMailSubClass().size())
				{
					query.append(" ,");
				}
			}

			query.append(" )");
		}

      return query;
    }

    private boolean isNotNullAndNotEmpty(String s) {
        return !Objects.isNull(s)&& !s.trim().isEmpty();
    }   
	/**
	 * @author A-8464
	 */
	public void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO, String groupRemarksToSave)
			throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","saveGroupRemarkDetails");
		 Procedure saveGrpRmksProcedure = getQueryManager().createNamedNativeProcedure(SAVE_GROUP_REMARKS_PROCEDURE);
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();			
			LocalDate fromDate=null;
	        LocalDate toDate=null;
	        if(isNotNullAndNotEmpty(invoicFilterVO.getFromDate()) 
	        		&& isNotNullAndNotEmpty(invoicFilterVO.getToDate()) )
	        {
	        fromDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	        toDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);	    	
	    	fromDate.setDate(invoicFilterVO.getFromDate());
	    	toDate.setDate(invoicFilterVO.getToDate()); 
	    	}
	    	int index = 0;

	    	//gpa code
	    	saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getGpaCode() == null ? "" : invoicFilterVO.getGpaCode());

	    	//invoic id
	    	String invoicSplit[] = null;
			if(invoicFilterVO.getInvoicId() != null && invoicFilterVO.getInvoicId().length() > 0){
				invoicSplit = invoicFilterVO.getInvoicId().split(" ");
			}
			String invoicID = null;
			if(invoicSplit != null && invoicSplit.length >= 3){
				invoicID =  invoicSplit[1];
			}else{
				invoicID = invoicFilterVO.getInvoicId();
			}
	    	if(invoicFilterVO.getInvoicId()!=null)
	    	{
	    		saveGrpRmksProcedure.setParameter(++index, invoicID);
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//from date
	    	saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getFromDate() == null ? "" :fromDate.toSqlDate());

	    	//to date
	    	saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getToDate() == null ? "" :toDate.toSqlDate());

	    	//mail bag id
	    	if(invoicFilterVO.getMailbagId()!=null)
	    	{
	    		saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getMailbagId());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//process status
	    	String clmstatus=null;
	    	String prosta = null;
			//for group remark, we will send only selected process status, not list of process status. Only if claim gen/sub status selected, that will be additionaly added
	    		if (invoicFilterVO.getProcessStatusFilter() != null && invoicFilterVO.getProcessStatusFilter().length() > 0) {
	    		if(invoicFilterVO.getProcessStatusFilter().equals("GEN") || invoicFilterVO.getProcessStatusFilter().equals("SUB"))
	    			{
	    				clmstatus=invoicFilterVO.getProcessStatusFilter();
	    			}  else {
						prosta=invoicFilterVO.getProcessStatusFilter();
					}	    			
	    		}
	    		if(prosta!=null &&prosta.length() > 0) {
	    				saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getProcessStatusFilter());
	    			} else {
	    					saveGrpRmksProcedure.setParameter(++index, "");
	    		}

	    		if(clmstatus!=null &&clmstatus.length() > 0) {
    				saveGrpRmksProcedure.setParameter(++index, clmstatus);
    			} else {
    					saveGrpRmksProcedure.setParameter(++index, "");
    		    }
	    	//claim status
	    	int count = 0;
			

			
			
			

			//origin
	    	StringBuilder orgList = new StringBuilder();
	    	Collection<String> origins = invoicFilterVO.getOrg();
	    	if(origins!=null && origins.size()>0)
	    	{count = 0;
	    	if (origins.size()==1)
	    	{
	    		orgList=orgList.append(origins.iterator().next());
	    	}
	    	else
	    	{   
	    	for(String origin:origins){
	    		++count;
	    	      
	    		orgList=orgList.append(origin);
	    	

	    		if(count<origins.size()){
	    			orgList=orgList.append(",");
	    		}
	    	}
	    	}
	    	saveGrpRmksProcedure.setParameter(++index,orgList.toString());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}


	    	//destination
	    	StringBuilder destList = new StringBuilder();
	    	Collection<String> dests = invoicFilterVO.getDest();
	    	if(dests!=null && dests.size()>0)
	    	{count = 0;
	    	if (dests.size()==1)
	    	{
	    		destList=destList.append(dests.iterator().next());
	    	}
	    	else
	    	{
	    	for(String dest:dests){
	    		++count;
	    	
	    		destList=destList.append(dest);
	    	

	    		if(count<dests.size()){
	    			destList=destList.append(",");
	    		}
	    	}
	    	}
	    	saveGrpRmksProcedure.setParameter(++index,destList.toString());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//claim range
	    	if(invoicFilterVO.getSelectedClaimRange()!=null && invoicFilterVO.getSelectedClaimRange().size()>0)
	    	{
	    		StringBuilder clmAmtRanges = new StringBuilder();
	    		count=0;
	    		for(String clmRange: invoicFilterVO.getSelectedClaimRange()){
	    			++count;
	    			clmAmtRanges.append(clmRange);
	    			if(count<invoicFilterVO.getSelectedClaimRange().size())
	    			{
	    				clmAmtRanges.append(",");
	    			}
	    		}
	    		saveGrpRmksProcedure.setParameter(++index,clmAmtRanges.toString());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//group remarks
	    	if(groupRemarksToSave==null)
	    		{
	    		groupRemarksToSave="";
	    		}
	    	saveGrpRmksProcedure.setParameter(++index, groupRemarksToSave);


	    	//mail sub class
	    	StringBuilder malsubclsList = new StringBuilder();
	    	Collection<String> malsubclases = invoicFilterVO.getMailSubClass();
	    	if(malsubclases!=null && malsubclases.size()>0)
	    	{count = 0;
	    	for(String malsubclas:malsubclases){
	    		++count;
	    	
	    		malsubclsList=malsubclsList.append(malsubclas);
	    	

	    		if(count<malsubclases.size()){
	    			malsubclsList=malsubclsList.append(",");
	    		}
	    	}
	    	saveGrpRmksProcedure.setParameter(++index,malsubclsList.toString());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//company code
	    	if(invoicFilterVO.getCmpcod()!=null){
	    	saveGrpRmksProcedure.setParameter(++index, invoicFilterVO.getCmpcod());
	    	}
	    	else{
	    		saveGrpRmksProcedure.setParameter(++index, "");
	    	}

	    	//out parameter
	    	saveGrpRmksProcedure.setOutParameter(++index, SqlType.STRING);
	    	saveGrpRmksProcedure.execute();
	    log.log(Log.FINE, "executed Procedure PKG_MAL_MRA_INVOIC_CALC.");
		String outParameter = (String) saveGrpRmksProcedure.getParameter(index);
		log.log(Log.FINE, "PKG_MAL_MRA_INVOIC_CALC.SPR_MRA_UPDATE_GRPRMK --- >>outParameter is-->> ", outParameter);
		log.exiting("MRAGPAReportingSqlDAO","saveGroupRemarkDetails");
	}

	/**
	 * @author A-8464
	 * @throws PersistenceException,SystemException
	 */
	// Commenting as part of ICRD-319850
	/*public Page<InvoicSummaryVO> findInvoicLov(InvoicFilterVO invoicFilterVO) throws PersistenceException,SystemException{


		log.entering("MRAGPAReportingSqlDAO","findInvoicLov");
		Page<InvoicSummaryVO> invoicSummaryVOs=null;

		String basqry = getQueryManager().getNamedNativeQueryString(FIND_INVOIC_LOV);
		StringBuilder qry = new StringBuilder().append(MRA_GPAREPORTING_ROWNUM_RANK_QUERY);
		qry.append(basqry);
		PageableNativeQuery<InvoicSummaryVO> finalquery=new PageableNativeQuery<InvoicSummaryVO>(invoicFilterVO.getDefaultPageSize(),invoicFilterVO.getTotalRecords(),qry.toString(),new GPAReportingInvoicLovDetailsMapper());

		int index=0;
		finalquery.setParameter(++index,invoicFilterVO.getCmpcod());

		if(invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length()>0)
		{
			finalquery.append(" AND gpaincmst.POACOD = ?");
			finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
		}

		if(invoicFilterVO.getFromDate() != null && invoicFilterVO.getFromDate().trim().length()>0)
		{
			finalquery.append(" AND gpaincmst.RPTPRDFRM = ?");
			finalquery.setParameter(++index,invoicFilterVO.getFromDate());
		}

		if(invoicFilterVO.getToDate() != null && invoicFilterVO.getToDate().trim().length()>0)
		{
			finalquery.append(" AND gpaincmst.RPTPRDTOO = ?");
			finalquery.setParameter(++index,invoicFilterVO.getToDate());
		}

		log.log(Log.INFO, "Final Query-->", finalquery);

		 finalquery.append(MRA_GPAREPORTING_SUFFIX_QUERY);

		 invoicSummaryVOs=finalquery.getPage(invoicFilterVO.getPageNumber());
		 log.log(Log.INFO, "result in SQl DAO-->", invoicSummaryVOs);

		 log.exiting("MRAGPAReportingSqlDAO","findInvoicLov");

		return invoicSummaryVOs;


	}*/
	/**
     * @author A-8527
     * @param InvoicFilterVO
     * @return Page<InvoicDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    public Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO,int pageNumber)
			throws PersistenceException, SystemException {
 		log.entering("MRAGPAReportingSqlDAO","listInvoic");
		Page<InvoicVO> invoicVOs=null;
		int pageSize = invoicFilterVO.getDefaultPageSize();
		int totalRecords=invoicFilterVO.getTotalRecords();
		int count =2;
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
	    LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		if(invoicFilterVO.getFromDate() != null && invoicFilterVO.getFromDate().trim().length() > 0
	    		&& invoicFilterVO.getToDate() != null && invoicFilterVO.getToDate().trim().length() > 0){
        fromDate.setDate(invoicFilterVO.getFromDate()) ;
        toDate.setDate(invoicFilterVO.getToDate());
		}
		String basqry = getQueryManager().getNamedNativeQueryString(LIST_INVOIC_UNION);
		StringBuilder qry = new StringBuilder().append(MRA_GPAREPORTING_ROWNUM_RANK_QUERY);
		String unionQuery = getQueryManager().getNamedNativeQueryString(LIST_INVOIC_BASE);
		if(invoicFilterVO.getInvoicfilterStatus() != null && invoicFilterVO.getInvoicfilterStatus().trim().length()>0
				&& (!"NW".equalsIgnoreCase(invoicFilterVO.getInvoicfilterStatus()) &&
						!"RJ".equals(invoicFilterVO.getInvoicfilterStatus()) &&
						!"IN".equals(invoicFilterVO.getInvoicfilterStatus()) &&
						!"All".equalsIgnoreCase(invoicFilterVO.getInvoicfilterStatus()))){
			qry.append(unionQuery);
		}else{
			qry.append(basqry);
		}
		 PageableNativeQuery<InvoicVO> finalquery=new PageableNativeQuery<InvoicVO>(pageSize,totalRecords,qry.toString(),new GPAReportingInvoicMapper());
		int index=0;
		finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
		if(invoicFilterVO.getInvoicfilterStatus() != null && !"All".equalsIgnoreCase(invoicFilterVO.getInvoicfilterStatus()) && invoicFilterVO.getInvoicfilterStatus().trim().length()>0)
		{
			if(invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length()>0)
			{
				finalquery.append(" AND LISTINV.POACOD = ?");
				finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
			}
			if(invoicFilterVO.getFileName() != null && invoicFilterVO.getFileName().trim().length()>0)
			{
				finalquery.append(" AND LISTINV.FILNAM = ?");
				finalquery.setParameter(++index,invoicFilterVO.getFileName());
			}
			if(invoicFilterVO.getInvoicRefId() != null && invoicFilterVO.getInvoicRefId().trim().length()>0)
			{
				finalquery.append(" AND LISTINV.INVREFNUM = ?");
				finalquery.setParameter(++index,invoicFilterVO.getInvoicRefId());
			}
			if(invoicFilterVO.getFromDate()!=null && invoicFilterVO.getFromDate().trim().length()>0 &&
					invoicFilterVO.getToDate()!=null && invoicFilterVO.getToDate().trim().length()>0)
			{
				finalquery.append(" AND TO_NUMBER(TO_CHAR(LISTINV.INVDAT,'YYYYMMDD')) BETWEEN ? AND ?");
				finalquery.setParameter(++index,Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8)) );
				finalquery.setParameter(++index,Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8))  );
			}
			if(invoicFilterVO.getInvoicfilterStatus() != null && (!"NW".equalsIgnoreCase(invoicFilterVO.getInvoicfilterStatus())
					&& !"RJ".equalsIgnoreCase(invoicFilterVO.getInvoicfilterStatus())
					&& !"IN".equals(invoicFilterVO.getInvoicfilterStatus()))
					&& invoicFilterVO.getInvoicfilterStatus().trim().length()>0)
			{
				finalquery.append(" AND LISTINV.INVSTA = ?");
				finalquery.setParameter(++index,invoicFilterVO.getInvoicfilterStatus());
				finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM,  TRUNC(RPTPRDFRM),TRUNC(RPTPRDTOO), POACOD ");
			}else{
				finalquery.append(" AND LISTINV.PRCSTA = ?");
				finalquery.setParameter(++index,invoicFilterVO.getInvoicfilterStatus());
				finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM, TRUNC(RPTPRDFRM),TRUNC(RPTPRDTOO), POACOD ");
			}
		}else{
		while (count-- > 0){

				//3 mandatory params - poa, from and to date
				if(invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length()>0)
				{
					finalquery.append(" AND LISTINV.POACOD = ?");
					finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
				}
				if(invoicFilterVO.getFileName() != null && invoicFilterVO.getFileName().trim().length()>0)
				{
					finalquery.append(" AND LISTINV.FILNAM = ?");
					finalquery.setParameter(++index,invoicFilterVO.getFileName());
				}
				if(invoicFilterVO.getInvoicRefId() != null && invoicFilterVO.getInvoicRefId().trim().length()>0)
				{
					finalquery.append(" AND LISTINV.INVREFNUM = ?");
					finalquery.setParameter(++index,invoicFilterVO.getInvoicRefId());
				}
				if(invoicFilterVO.getFromDate()!=null && invoicFilterVO.getFromDate().trim().length()>0 &&
						invoicFilterVO.getToDate()!=null && invoicFilterVO.getToDate().trim().length()>0)
				{
					finalquery.append(" AND TO_NUMBER(TO_CHAR(LISTINV.INVDAT,'YYYYMMDD')) BETWEEN ? AND ?");
					finalquery.setParameter(++index,Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8))  );
					finalquery.setParameter(++index,Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)) );
				}
				if( count == 1){
					finalquery.append(" AND LISTINV.PRCSTA IN ( 'NW','RJ','IN') ");
					finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM, TRUNC(RPTPRDFRM),TRUNC(RPTPRDTOO), POACOD ");
				}else if(count !=1){
					finalquery.append(" AND LISTINV.INVSTA NOT IN ('NW','RJ','IN','RV') ");
					finalquery.append(" AND NOT EXISTS( SELECT 1 FROM MALMRAGPAINCMSGMST WHERE CMPCOD = LISTINV.CMPCOD AND FILNAM = LISTINV.FILNAM ");
					finalquery.append(" AND INVREFNUM = LISTINV.INVREFNUM AND PAYTYP = LISTINV.PAYTYP AND POACOD = LISTINV.POACOD AND PRCSTA = 'IN' ) ");
					finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM, TRUNC(RPTPRDFRM),TRUNC(RPTPRDTOO), POACOD ");
				}else{
				}
				if(count == 1){
					//finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM, NUMMALBAG, CASE WHEN LISTINV.TOTINVAMT > 0 THEN LISTINV.TOTINVAMT ELSE LISTINV.TOTADJAMT END, POACOD ");
					finalquery.append(" UNION ").append(unionQuery);
			finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
				}/*else{
					finalquery.append(" GROUP BY CMPCOD, INVREFNUM, PAYTYP, FILNAM, NUMMALBAG, TOTINVAMT, POACOD ");
				}*/

			}
		}

		 finalquery.append(MRA_GPAREPORTING_SUFFIX_QUERY);

		// invoicVOs=finalquery.getPage(invoicFilterVO.getPageNumber());
		 log.log(Log.INFO, "result in SQl DAO-->", invoicVOs);

		 log.exiting("MRAGPAReportingSqlDAO","listInvoic");

		return finalquery.getPage(pageNumber);
	}

    /**
     * @author A-8527
     * @param InvoicFilterVO
     * @return Page<ClaimDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    public Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)
			throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO","listClaimDetails");
		int pageSize = invoicFilterVO.getDefaultPageSize();
		int totalRecords=invoicFilterVO.getTotalRecords();
		LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        toDate.setDate(invoicFilterVO.getToDate());
        LocalDate fromDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        fromDate.setDate(invoicFilterVO.getFromDate());
		String basqry = getQueryManager().getNamedNativeQueryString(LIST_CLAIMDTLS_BASE);
		StringBuilder qry = new StringBuilder().append(MRA_GPAREPORTING_ROWNUM_RANK_QUERY);
		qry.append(basqry);
		 PageableNativeQuery<ClaimDetailsVO> finalquery=new PageableNativeQuery<ClaimDetailsVO>(pageSize,totalRecords,qry.toString(),new ClaimDetailsMapper());
		int index=0;
			finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
		    finalquery.setParameter(++index,Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8))  );
			finalquery.setParameter(++index,Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)));

       if(invoicFilterVO.getGpaCode() != null && invoicFilterVO.getGpaCode().trim().length()>0)
      {
          finalquery.append(" AND LISTCLM.POACOD = ?");
          finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
       }
       if(invoicFilterVO.getMailbagId()!= null && invoicFilterVO.getMailbagId().trim().length()>0)
       {
    	   finalquery.append(" AND LISTCLM.MALIDR = ?");
           finalquery.setParameter(++index,invoicFilterVO.getMailbagId());
       }
      /* if(invoicFilterVO.getInvoicfilterStatus()!= null && !"ALL".equals(invoicFilterVO.getInvoicfilterStatus()))
       {
    	   finalquery.append(" AND LISTCLM.CLMSTA = ?");
           finalquery.setParameter(++index,invoicFilterVO.getInvoicfilterStatus());
       }*/   // Commented as part of ICRD-343866
       if(invoicFilterVO.getClaimFileName()!= null && invoicFilterVO.getClaimFileName().trim().length()>0)
       {
    	   finalquery.append("  AND LISTGECLM.CLMFILNAM=?");
           finalquery.setParameter(++index,invoicFilterVO.getClaimFileName());
       }
       if(invoicFilterVO.getClaimType()!= null && invoicFilterVO.getClaimType().trim().length()>0)
       {
    	   finalquery.append(" AND LISTCLM.CLMTYP IN( ");
    	   String[] claimTypes = invoicFilterVO.getClaimType().split(",");
    	      int configTypeIndex = 0;
    	      for (String claimType : claimTypes) {
    	    	  finalquery.append("?");
    	        index++; finalquery.setParameter(index, claimType);
    	        configTypeIndex++;
    	        if (configTypeIndex != claimTypes.length) {
    	        	finalquery.append(",");
    	        }
    	      }
    	      finalquery.append(") ");
       }
		log.log(Log.INFO, "Final Query-->", finalquery);
		 finalquery.append(MRA_GPAREPORTING_SUFFIX_QUERY);
		 log.exiting("MRAGPAReportingSqlDAO","listClaimDetails");
		return finalquery.getPage(pageNumber);
	}
    /**
     * @author A-8527
     * @param InvoicFilterVO
     * @return Page<ClaimDetailsVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    public Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)
			throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO","listGenerateClaimDetails");
		int pageSize = invoicFilterVO.getDefaultPageSize();
		int totalRecords=invoicFilterVO.getTotalRecords();
		String basqry = getQueryManager().getNamedNativeQueryString(LIST_GENCLAIM_BASE);
		StringBuilder qry = new StringBuilder().append(MRA_GPAREPORTING_ROWNUM_RANK_QUERY);
		qry.append(basqry);
		 PageableNativeQuery<ClaimDetailsVO> finalquery=new PageableNativeQuery<ClaimDetailsVO>(pageSize,totalRecords,qry.toString(),new GenerateAndListClaimMapper());
		int index=0;
			finalquery.setParameter(++index,invoicFilterVO.getCmpcod());
			finalquery.setParameter(++index,invoicFilterVO.getGpaCode());
			finalquery.setParameter(++index,invoicFilterVO.getFromDate());
			finalquery.setParameter(++index,invoicFilterVO.getToDate());
		log.log(Log.INFO, "Final Query-->", finalquery);
		 finalquery.append(MRA_GPAREPORTING_SUFFIX_QUERY);
		 log.exiting("MRAGPAReportingSqlDAO","listGenerateClaimDetails");
		return finalquery.getPage(pageNumber);
	}

    public long findSerialNumberfromInvoic(String invoicNumber, String companyCode)
			throws SystemException {

		log.entering("MRAGPAReportingSqlDAO", "findSerialNumberfromInvoic");
		long serialNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_SERIAL_NUMBER_FROM_INVOIC);
		int indx = 0;
		// String uldNumber = mailInConsignmentVO.getUldNumber();
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, invoicNumber);
		String seqNum = query.getSingleResult(getStringMapper("SERNUM"));
		if (seqNum != null) {
			serialNumber = Long.parseLong(seqNum);
		}
		log.exiting("MRAGPAReportingSqlDAO", "findMailSequenceNumber");
		return serialNumber;


	}
public String processInvoic(InvoicVO invoic)throws PersistenceException, SystemException {
    	String status = "";
    	String fileName = (invoic.getFileName()!= null && invoic.getFileName().trim().length() >0 )
    						? invoic.getFileName() : " ";
        String payType =  (invoic.getPayType()!= null && invoic.getPayType().trim().length() > 0 )
							? invoic.getPayType() : " ";
		String actionCode = ("PR".equals(invoic.getInvoicStatusCode()) || "PE".equals(invoic.getInvoicStatusCode())) ? "RJ" : "PR";
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		log.entering("MRAGPAReportingSqlDAO","processInvoic");
		String query = invoic.isNoDbJobRequired() == true ? INVOIC_REPROCESS_INV : INVOIC_PROCESS;
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
				query);
		int index = 0;
		String seqNum = invoic.getFileName() == null ? invoic.getSerNums() : " ";
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
//		String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
//		log.log(Log.FINE, "Current Date: "+dateString);
//		//currentDate.setDate(dateString, "dd-MMM-yyyy HH:mm");
		burstProcedure.setParameter(++index,invoic.getCompanyCode());
		burstProcedure.setParameter(++index,seqNum);
		if(invoic.isNoDbJobRequired()){
			burstProcedure.setParameter(++index,0);
		}
		burstProcedure.setParameter(++index,fileName);
		burstProcedure.setParameter(++index,invoic.getInvoicRefId());
		burstProcedure.setParameter(++index,payType);
		burstProcedure.setParameter(++index,invoic.getPoaCode());
		burstProcedure.setParameter(++index,invoic.getNumOfMailbags());
		burstProcedure.setParameter(++index,invoic.getTxnCode());
		burstProcedure.setParameter(++index,invoic.getTxnSerialNum());
		if(invoic.isNoDbJobRequired()){
			burstProcedure.setParameter(++index,1);
			burstProcedure.setParameter(++index,1);
			burstProcedure.setParameter(++index,1);
			burstProcedure.setParameter(++index,1);
		}
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setParameter(++index,logonAttributes.getUserId());
		burstProcedure.setParameter(++index,actionCode);
		burstProcedure.setParameter(++index,logonAttributes.getStationCode());
		burstProcedure.setOutParameter(++index,SqlType.STRING);
		burstProcedure.execute();
		log.log(Log.FINE,"executed Procedure-->");
		if(invoic.isNoDbJobRequired()){
			status = (String)burstProcedure.getParameter(18);
		}else{
			status = (String)burstProcedure.getParameter(14);
		}
		log.log(Log.FINE, "outParameter is >>>>>>>>>>>", status);
		log.exiting("MRAGPAReportingSqlDAO", "processInvoic");

    	return status;
    }

    /**
     *
     * @param invoic
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */
    public String processInvoicForMailbags(InvoicVO invoic)throws PersistenceException, SystemException {
    	String status = "";

		log.entering("MRAGPAReportingSqlDAO","processInvoic");
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
				INVOIC_PROCESS);
		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		burstProcedure.setParameter(++index,invoic.getCompanyCode());
		burstProcedure.setParameter(++index,invoic.getSeqNumber());
		burstProcedure.setParameter(++index,invoic.getInvoicRefId());
		burstProcedure.setParameter(++index,invoic.getPoaCode());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setParameter(++index,invoic.getLastupdatedUser());
		burstProcedure.setOutParameter(++index,SqlType.STRING);
		burstProcedure.execute();
		log.log(Log.FINE,"executed Procedure-->");

		 status = (String)burstProcedure.getParameter(7);
		log.log(Log.FINE, "outParameter is >>>>>>>>>>>", status);
		log.exiting("MRAGPAReportingSqlDAO", "processInvoic");

    	return status;
    }


    /**
     *
     */
    public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs)throws PersistenceException, SystemException {
    	String status = "";

		log.entering("MRAGPAReportingSqlDAO","reprocessInvoicMails");
		LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);

		for(InvoicDetailsVO vo : invoicDetailsVOs){
			Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
					INVOIC_REPROCESS);
			int index = 0;
			burstProcedure.setParameter(++index,logonAttributes.getCompanyCode());
			burstProcedure.setParameter(++index,vo.getSerialNumber());
			burstProcedure.setParameter(++index,vo.getMailSequenceNumber());
			burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
			burstProcedure.setParameter(++index,logonAttributes.getUserId());
			burstProcedure.setOutParameter(++index,SqlType.STRING);
			burstProcedure.execute();
			log.log(Log.FINE,"executed Procedure-->");

			 status = (String)burstProcedure.getParameter(6);
		}
		log.log(Log.FINE, "outParameter is >>>>>>>>>>>", status);
		log.exiting("MRAGPAReportingSqlDAO", "reprocessInvoicMails");

    	return status;
    }

    /**
     *
     */
    public int checkForProcessCount(String companyCode)throws PersistenceException, SystemException {
    	log.entering("MRAGPAReportingSqlDAO","checkForProcessCount");
    	int count= 0;
    	Query query = getQueryManager().createNamedNativeQuery(INVOIC_COUNT);
		query.setParameter(1, companyCode);
		String processCount = query.getSingleResult(getStringMapper("PRCCNT"));
		if(processCount != null && processCount.trim().length() > 0){
			count = Integer.parseInt(processCount);
		}else{
			count = 0;
		}
    	return count;
    }


    public String checkAutoProcessing(Collection<MailInvoicMessageVO> mailInvoicMessage) throws  SystemException {
    	log.entering("MRAGPAReportingSqlDAO","checkAutoProcessing");
    	String flag=null;
    	MailInvoicMessageVO mailInvoicMessageVO=mailInvoicMessage.iterator().next();
    	if(mailInvoicMessageVO!=null){
    	int index=0;
    	Query query = getQueryManager().createNamedNativeQuery(INVOIC_AUTO_PROCESSING);
		query.setParameter(++index,mailInvoicMessageVO.getCompanyCode());
		query.setParameter(++index, mailInvoicMessageVO.getFileName());
		flag = query.getSingleResult(getStringMapper("ISAUTOPROCESSING"));
    	}
    	return flag;
    }


    /**
     *
     */
    public String updateBatchNumForInvoic(InvoicVO invoic)throws PersistenceException, SystemException {
    	String status = "";
    	String appJobReq = "Y";
    	String fileName = (invoic.getFileName()!= null && invoic.getFileName().trim().length() >0 )
    						? invoic.getFileName() : "";
		String payType =  (invoic.getPayType()!= null && invoic.getPayType().trim().length() > 0 )
				? invoic.getPayType() : "";
		String actionCode = ("PR".equals(invoic.getInvoicStatusCode()) || "PE".equals(invoic.getInvoicStatusCode())) ? "RJ" : "PR";
		log.entering("MRAGPAReportingSqlDAO","updateBatchNumForInvoic");
		Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
					UPDATE_BATCH);
		int index = 0;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		String seqNum = invoic.getFileName() == null ? invoic.getSerNums() : "";
//		String dateString = DateUtilities.getCurrentDate("dd-MMM-yyyy HH:mm");
//		log.log(Log.FINE, "Current Date: "+dateString);
//		//currentDate.setDate(dateString, "dd-MMM-yyyy HH:mm");
		burstProcedure.setParameter(++index,invoic.getCompanyCode());
		burstProcedure.setParameter(++index,invoic.getProcessingType());
		burstProcedure.setParameter(++index,seqNum);
		burstProcedure.setParameter(++index,fileName);
		
		burstProcedure.setParameter(++index,invoic.getInvoicRefId());
		
		burstProcedure.setParameter(++index,payType);
		burstProcedure.setParameter(++index,invoic.getPoaCode());
		burstProcedure.setParameter(++index,invoic.getNumOfMailbags());
		burstProcedure.setParameter(++index,currentDate.toSqlTimeStamp());
		burstProcedure.setParameter(++index,invoic.getLastupdatedUser());
		burstProcedure.setParameter(++index,actionCode);
		burstProcedure.setOutParameter(++index,SqlType.STRING);
		burstProcedure.execute();
		log.log(Log.FINE,"executed Procedure-->");

		 status = (String)burstProcedure.getParameter(12);
		log.log(Log.FINE, "outParameter is >>>>>>>>>>>", status);
		if(isOracleDataSource()) {
			 appJobReq = "N" ;
		 }
		 status=appJobReq.concat("-").concat(status);
		log.exiting("MRAGPAReportingSqlDAO", "updateBatchNumForInvoic");

    	return status;
    }

    /**
     * @author A-7371
     */
	public String  updateInvoicStatus(Collection<MailInvoicMessageVO> mailInvoicMessage)throws SystemException{
		log.entering("MRAGPAReportingSqlDAO","updateInvoicStatus");
		String status = "";
		int i = 0;
		String invoicStatus="";
		if(mailInvoicMessage!=null){
			for(MailInvoicMessageVO mailInvoicMessageVO : mailInvoicMessage){
				if(i ==0){
					invoicStatus = mailInvoicMessageVO.getInvoiceStatus()!=null ?mailInvoicMessageVO.getInvoiceStatus():"NW";
					i++;
				}
				Procedure burstProcedure = getQueryManager().createNamedNativeProcedure(
						UPDATE_INVOIC_STATUS);
				int index = 0;
				burstProcedure.setParameter(++index,mailInvoicMessageVO.getCompanyCode());
				burstProcedure.setParameter(++index,mailInvoicMessageVO.getFileName());
				burstProcedure.setParameter(++index,invoicStatus);
				burstProcedure.setParameter(++index,mailInvoicMessageVO.getRemark()!=null?mailInvoicMessageVO.getRemark():"");
				burstProcedure.setOutParameter(++index,SqlType.STRING);
				burstProcedure.execute();
				log.log(Log.FINE,"executed Procedure-->");

				 status = (String)burstProcedure.getParameter(4);
			}

		}
		log.exiting("MRAGPAReportingSqlDAO", "updateInvoicStatus");
		return status;


	}

	/**
	 *
	 */
	public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum)
				throws PersistenceException,SystemException{
		log.entering("MRAGPAReportingSqlDAO","findInvoicAndClaimDetails");
		Query query=getQueryManager().createNamedNativeQuery(INVOIC_AND_CLAIM_DETAILS);
		Collection<InvoicDetailsVO> invoicDetailsVOS=null;
		int index=0;
		query.setParameter(++index,companyCode);
		query.setParameter(++index,mailSeqnum);
		query.setParameter(++index,companyCode);
		query.setParameter(++index,mailSeqnum);
		invoicDetailsVOS=query.getResultList(new InvoicAndClaimDetailsMapper());
		return invoicDetailsVOS;
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#saveClaimDetails(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
	 *	Added by 			: A-4809 on May 28, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public String saveClaimDetails(InvoicFilterVO filterVO) throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO", "saveClaimDetails");
		String status = "";
		String claimRefNum = "";
		StringBuilder output = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode=logonAttributes.getCompanyCode();
		LocalDate currentDate = new LocalDate("***", Location.NONE, false);
		Procedure procedure = getQueryManager().createNamedNativeProcedure(SAVE_CLAIMS_DETAILS);
		procedure.setSensitivity(true);
		int index = 0;
		procedure.setParameter(++index,companyCode);
		procedure.setParameter(++index,filterVO.getGpaCode());
		LocalDate fromDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		fromDate.setDate(filterVO.getFromDate());
		LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        toDate.setDate(filterVO.getToDate());
		procedure.setParameter(++index, fromDate);
		procedure.setParameter(++index, toDate);
		procedure.setParameter(++index, currentDate);
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setParameter(++index, filterVO.getTriggerPoint());
		procedure.setParameter(++index, claimRefNum);
		procedure.setOutParameter(index, SqlType.STRING);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		log.log(Log.FINE,"executed Procedure-->");
		claimRefNum = (String)procedure.getParameter(8);
		if( !isOracleDataSource() && "".equals(claimRefNum)  ){
			claimRefNum = null;
		}
		status = (String)procedure.getParameter(9);
		
		output = new StringBuilder(status).append("#").append(claimRefNum);
		log.log(Log.FINE, "Claim Reference Number",claimRefNum);
		log.log(Log.FINE, "Out Status",status);
		log.log(Log.FINE, "Output",output);
		log.exiting("MRAGPAReportingSqlDAO", "saveClaimDetails");

		return output.toString();
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#findMailbagsForClaim(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
	 *	Added by 			: A-4809 on Jun 3, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public ClaimVO findMailbagsForClaim(InvoicFilterVO filterVO) throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO", "findMailbagsForClaim");
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_CLAIMS_DETAILS);
		query.setParameter(++index, filterVO.getCmpcod());
		query.setParameter(++index, filterVO.getClaimRefNum());
		Collection<ClaimVO> claimVO = query
				.getResultList(new ClaimMailbagDetailsMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findMailbagsForClaim");
		return claimVO.iterator().next();
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#findMailbagsForClaim(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
	 *	Added by 			: A-4809 on Jun 3, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ClaimVO> findMailbagsForClaimForInternational(InvoicFilterVO filterVO) throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO", "findMailbagsForClaimForInternational");
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_CLAIMS_DETAILS_INT);
		query.setParameter(++index, filterVO.getCmpcod());
		//query.setParameter(++index, filterVO.getClaimRefNum());
		Collection<ClaimVO> claimVO = query
				.getResultList(new ClaimMailbagDetailsInternationalMapper());
		log.exiting(this.getClass().getSimpleName(),
				"findMailbagsForClaimForInternational");
		return claimVO;
	}
/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#findGeneratedResditMessages(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
	 *	Added by 			: A-4809 on Jun 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ResditEventVO> findGeneratedResditMessages(InvoicFilterVO filterVO)
			throws PersistenceException, SystemException {
		log.entering("MRAGPAReportingSqlDAO", "findGeneratedResditMessages");
		Collection<ResditEventVO> resditEventVOs = null;
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_GENERATED_RESDIT_MSG);

		if(TRIGGERPOINT_JOB.equals(filterVO.getTriggerPoint())){
			query.append(" AND  INC.CLMSTA= 'GEN' ");
		}else{
			query.append(" AND (INC.CLMSTA IS NULL OR INC.CLMSTA= 'GEN') ");
		}
		//query.append(" GROUP BY MSG.CMPCOD,INC.POACOD,MSG.MSGTYP,MSG.EVTPRT,MSG.MSGVERNUM,CDTEXT,MSG.MSGIDR ");
		query.append(") record where RWNUM='1'");
		query.setParameter(++index, filterVO.getCmpcod());
		LocalDate fromDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        fromDate.setDate(filterVO.getFromDate());
		query.setParameter(++index, fromDate);
		LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        toDate.setDate(filterVO.getToDate());
		query.setParameter(++index,toDate );
		query.setParameter(++index, filterVO.getGpaCode());
		log.log(Log.INFO, "Final Query-->", query);
		resditEventVOs=query.getResultList(new ResditMessageDetailsMapper());
		log.exiting("MRAGPAReportingSqlDAO", "findGeneratedResditMessages");
		return resditEventVOs;
	}

/**
	 * @author A-7371
	 */
	public Collection<ClaimDetailsVO> findMailBagsForMessageGeneration(String companyCode) throws SystemException{

		Collection<ClaimDetailsVO> claimDetailsVO=null;
		int index=0;
    	Query query = getQueryManager().createNamedNativeQuery(FIND_MAILS_FORCLAIMGEN);
		query.setParameter(++index,companyCode);

		claimDetailsVO=query.getResultList(new GPAClaimMessageMapper());
		return claimDetailsVO;

	}

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#isClaimGeneraetd(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
	 *	Added by 			: A-8061 on 20-Jun-2019
	 * 	Used for 	:	ICRD-262451
	 *	Parameters	:	@param invoicFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	public String findClaimMasterDetails(InvoicFilterVO invoicFilterVO) throws PersistenceException, SystemException {

		log.entering("MRAGPAReportingSqlDAO", "findClaimMasterDetails");
		int index = 0;
		LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        toDate.setDate(invoicFilterVO.getToDate());
        LocalDate fromDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        fromDate.setDate(invoicFilterVO.getFromDate());
		String claimDetails=null;
		Query query=getQueryManager().createNamedNativeQuery(FIND_CLAIM_MASTERDETAILS);
		query.setParameter(++index, invoicFilterVO.getCmpcod());
		query.setParameter(++index, invoicFilterVO.getGpaCode());
		query.setParameter(++index, Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		query.setParameter(++index, Integer.parseInt( toDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		log.log(Log.INFO, "Final Query-->", query);
		claimDetails = query.getSingleResult(getStringMapper("CLMDET"));

		log.exiting("MRAGPAReportingSqlDAO", "findClaimMasterDetails");
		return claimDetails;


	}
	/**
	 * Trigger Invoic accounting.
	 * @author A-7794
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	@Override
	public String triggerInvoicAccounting(InvoicDetailsVO detailVO)
			throws PersistenceException, SystemException {
		int index = 0;
		String funcPoint="";
		String outparameter= null;
		if(detailVO != null){
		Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_DEFAULTS_INVOIC_ACC_PROCEDURE);
		procedure.setSensitivity(true);
		index = 0;
		funcPoint = "OP".equals(detailVO.getInvoicPayStatus()) ? "IOA" : "IVS" ;
		funcPoint = "CAU".equals(detailVO.getInvoicPayStatus()) ? "IVC" : funcPoint ;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		procedure.setParameter(++index, detailVO.getCompanyCode());
		procedure.setParameter(++index, detailVO.getMailSequenceNumber());
		procedure.setParameter(++index, detailVO.getPoaCode());
		procedure.setParameter(++index,"IVPACP");
		procedure.setParameter(++index, detailVO.getSerialNumber());
		procedure.setParameter(++index, funcPoint);
		procedure.setParameter(++index, detailVO.getInvoicID());
		procedure.setParameter(++index, logonAttributes.getUserId());
		LocalDate lastUpdatedTim = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		procedure.setParameter(++index, lastUpdatedTim.toSqlTimeStamp());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		outparameter = (String) procedure.getParameter(index);
	}
	return outparameter;
	}
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#findClaimReferenceNumber(com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO)
 *	Added by 			: A-8061 on 27-Jun-2019
 * 	Used for 	:
 *	Parameters	:	@param invoicFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws PersistenceException
 *	Parameters	:	@throws SystemException
 */
	public String findClaimReferenceNumber(InvoicFilterVO invoicFilterVO) throws PersistenceException, SystemException {

		log.entering("MRAGPAReportingSqlDAO", "findClaimReferenceNumber");
		int index = 0;
		String claimRefNumber=null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Query query=getQueryManager().createNamedNativeQuery(FIND_CLAIM_REFNUM);
		query.setParameter(++index, invoicFilterVO.getCmpcod());
		query.setParameter(++index, invoicFilterVO.getFromDate());
		query.setParameter(++index, invoicFilterVO.getFromDate());
		query.setParameter(++index,logonAttributes.getUserId());
		log.log(Log.INFO, "Final Query-->", query);
		claimRefNumber = query.getSingleResult(getStringMapper("CLMREFNUM"));

		log.exiting("MRAGPAReportingSqlDAO", "findClaimReferenceNumber");
		return claimRefNumber;


	}



	public Collection<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO)
			throws PersistenceException, SystemException {



		Collection<ClaimDetailsVO> claimDetailsVOs=null;
		int index=0;
    	Query query = getQueryManager().createNamedNativeQuery(LIST_CLAIMDTLS_FOR_EDI_GENERATION);
		query.setParameter(++index,invoicFilterVO.getCmpcod());
    	query.setParameter(++index,invoicFilterVO.getGpaCode());


    if(invoicFilterVO.getToDate() != null )
   {
    	query.append(" AND TO_NUMBER(TO_CHAR(TRUNC(INCDTL.RCVDAT),'YYYYMMDD')) <= ? ");
    	LocalDate toDate =new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
        toDate.setDate(invoicFilterVO.getToDate());
        query.setParameter(++index, Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8)));


    }

    	//query.append(" AND (INCDTL.CLMSTA = 'GEN' OR COALESCE(INCDTL.CLMSTA,'C') =  'X' OR  COALESCE(INCDTL.CLMSTA,'C') ='C')");

		query.append(" AND  INCDTL.CLMSTA = 'GEN'");


		claimDetailsVOs=query.getResultList(new ClaimDetailsMapper());
		return claimDetailsVOs;


	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO#findInvoicsByFileName(java.lang.String, java.lang.String)
	 *	Added by 			: A-5219 on 17-Jun-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileName
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 */
	public List<Long> findInvoicsByFileName(String companyCode, String fileName)
			throws PersistenceException, SystemException {

		Query query = getQueryManager().createNamedNativeQuery(FIND_INVOICS_BY_FILENAME);
		int index=0;

		query.setParameter(++index, companyCode);
		query.setParameter(++index, fileName);

		return  query.getResultList(new Mapper<Long>() {
	        public Long map(ResultSet rs) throws SQLException {
				return rs.getLong("SERNUM");
			}
	    });
	}
	public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_REJECT_MAILBAGS_COUNT);
		int index=0;
		query.setParameter(++index, companyCode);
		query.setParameter(++index, invoicVO.getFileName());
		index= query.getSingleResult(getIntMapper("COUNT"));
		return index;
	}

	public long findBatchNo(InvoicVO invoicVO) throws SystemException {
		Query query = getQueryManager().createNamedNativeQuery(FIND_BATCH);
		int index=0;
		long bthnum;
		query.setParameter(++index, invoicVO.getCompanyCode());
		query.setParameter(++index, invoicVO.getFileName());
		String bnum= query.getSingleResult(getStringMapper("TOTMALCNT"));
		bthnum=Long.parseLong(bnum);
		return bthnum;
	}
	public String processInvoicFileFromJob(InvoicVO invoicVO) throws SystemException {
		log.entering(MRA_REPORTING_SQLDAO, "startInvrefJob");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Procedure procedure = getQueryManager().createNamedNativeProcedure(INVOIC_PROCESS_SCH);
		int index = 0; 
		String outparameter = null;
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		procedure.setParameter(++index, invoicVO.getCompanyCode());

		procedure.setParameter(++index, invoicVO.getFileName());

		procedure.setParameter(++index, invoicVO.getPoaCode() != null ? invoicVO.getPoaCode(): EMPTY);
		procedure.setParameter(++index, invoicVO.getTxnCode() != null ? invoicVO.getTxnCode(): EMPTY);
		procedure.setParameter(++index, invoicVO.getTxnSerialNum());
		procedure.setParameter(++index, invoicVO.getStartBatchnum());
		procedure.setParameter(++index, invoicVO.getEndBatchnum());
		procedure.setParameter(++index, invoicVO.getJobIdx());
		procedure.setParameter(++index, currentDate.toSqlTimeStamp());
		procedure.setParameter(++index, invoicVO.getActionCode() != null ? invoicVO.getActionCode(): EMPTY);
		procedure.setParameter(++index, logonAttributes.getStationCode());
		procedure.setParameter(++index, logonAttributes.getUserId());
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		outparameter = (String) procedure.getParameter(index);
		return outparameter;
	}
	@Override
	public String updateInvoicProcessingStatusFromJob(String companyCode) throws PersistenceException, SystemException {
		log.entering(MRA_REPORTING_SQLDAO, "concurrentFileStatusUpdate");
		Procedure procedure = getQueryManager().createNamedNativeProcedure(INVOIC_UPDATE_STATUS);
		int index = 0; 
		String outparameter = null;
		procedure.setParameter(++index, companyCode);
		procedure.setOutParameter(++index, SqlType.STRING);
		procedure.execute();
		outparameter = (String) procedure.getParameter(index);
		return outparameter;
		
	}
}
