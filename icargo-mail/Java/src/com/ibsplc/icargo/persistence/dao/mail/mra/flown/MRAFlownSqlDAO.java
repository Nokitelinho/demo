/*
 * MRAFlownSqlDAO.java Created on Dec 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;



import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailRevenueVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This Class is for MRAFlownSqlDAO
 * @author A-2338
 *
 */
public class MRAFlownSqlDAO extends AbstractQueryDAO implements MRAFlownDAO {
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA");
	
	private static final String CLASS_NAME = "MRAFlownSqlDAO";
	private static final String FIND_FLOWNMAILBAGS = "mailtracking.mra.flown.findmailbags";
	private static final String FIND_FLOWNDSN = "mailtracking.mra.flown.findDSN";
	private static final String FIND_FLIGHTDETAILS = "mailtracking.mra.flown.findFlightDetails";
	private static final String FIND_FLOWN_MAIL_EXCEPTIONS = "mailtracking.mra.flown.findFlownMailExceptions";
	private static final String FIND_FLOWN_MAIL_EXCEPTIONS_FORPRINT_DETAILS = "mailtracking.mra.flown.findFlownMailExceptionsForPrintdetails";
	private static final String FIND_LISTOFFLIGHTS = "mailtracking.mra.flown.findlistofflownflights";
	private static final String FIND_LISTOFFLOWNMAILBAGS = "mailtracking.mra.flown.findlistofflownmailbags";
	private static final String MTK_MRA_PROCESSFLIGHT = "mailtracking.mra.flown.processflight";
	private static final String MTK_MRA_FLOWN_FINDFLOWN_REVENUE_REPORT_DTLS = "mailtracking.mra.flown.findmailRevenueDetails";
	private static final String MTK_MRA_IMPORTARRIVEDMAILS="mailtracking.mra.flown.importarrivedmailstomra";
	private static final String MTK_MRA_IMPORTMISSEDMAILS_ON_DELIVERYSCAN="mailtracking.mra.flown.importmissedmailsondeliveryscantomra";
	
    
	/**
	 * This method is for findFlownMailMags
	 * @param flownMailFilterVO
	 * @return Collection <MailBagForFlownSegmentVO>
	 * @throws SystemException
	 */
   
    public Collection<MailBagForFlownSegmentVO> findFlownMailMags(
    		FlownMailFilterVO flownMailFilterVO)throws SystemException {
    	Query query = getQueryManager().createNamedNativeQuery(FIND_FLOWNMAILBAGS);
		query.setParameter(1, flownMailFilterVO.getCompanyCode());
		query.setParameter(2, flownMailFilterVO.getFlightNumber());
		query.setParameter(3, flownMailFilterVO.getFlightCarrierId());
		query.setParameter(4, flownMailFilterVO.getFlightSequenceNumber());
		query.setParameter(5, flownMailFilterVO.getSegmentSerialNumber());
		
		return query.getResultList(new FlownMailBagDetailsMapper());
		
       
    }

    /**
	 * This method is for findFlownDSNs
	 * @param flownMailFilterVO
	 * @return Collection <DSNForFlownSegmentVO>
	 * @throws SystemException
	 */
    public Collection<DSNForFlownSegmentVO> findFlownDSNs(
    		FlownMailFilterVO flownMailFilterVO)throws SystemException {
    	Query query = getQueryManager().createNamedNativeQuery(FIND_FLOWNDSN);
		query.setParameter(1, flownMailFilterVO.getCompanyCode());
		query.setParameter(2, flownMailFilterVO.getFlightNumber());
		query.setParameter(3, flownMailFilterVO.getFlightCarrierId());
		query.setParameter(4, flownMailFilterVO.getFlightSequenceNumber());
		query.setParameter(5, flownMailFilterVO.getSegmentSerialNumber());
		
		return query.getResultList(new FlownDSNDetailsMapper());
       
    }

    /**
	 * This method is for findFlownMailExceptions
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
    public Collection<FlownMailExceptionVO> findFlownMailExceptions(
            FlownMailFilterVO flownMailFilterVO) throws SystemException {
       Query query =getQueryManager().createNamedNativeQuery(FIND_FLOWN_MAIL_EXCEPTIONS);
       int index = 0;
       query.setParameter(++index,flownMailFilterVO.getCompanyCode());
       if(flownMailFilterVO.getFlightCarrierCode()!=null && 
    		   flownMailFilterVO.getFlightCarrierCode().trim().length()>0){
    	   query.append(" AND MST.FLTCARIDR = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getFlightCarrierId());    	   
       }
       if(flownMailFilterVO.getFlightNumber()!=null &&
    		   flownMailFilterVO.getFlightNumber().trim().length() > 0){
    	   query.append(" AND MST.FLTNUM = ?");
    	   query.setParameter(++index,flownMailFilterVO.getFlightNumber());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null ){
    	   query.append(" AND MST.FLTDAT >= ?");
    	   query.setParameter(++index,flownMailFilterVO.getFromDate());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null && 
    		   flownMailFilterVO.getToDate()!=null){
    	   query.append(" AND MST.FLTDAT <= ?");    	   
    	   query.setParameter(++index,flownMailFilterVO.getToDate());
       }
       if(flownMailFilterVO.getExceptionCode()!=null && 
    		   flownMailFilterVO.getExceptionCode().trim().length() > 0){
    	   query.append(" AND EXP.EXPCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getExceptionCode());
       }
       if(flownMailFilterVO.getAssigneeCode()!=null && 
    		   flownMailFilterVO.getAssigneeCode().trim().length() > 0){
    	   query.append(" AND EXP.ASGCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssigneeCode());
       }
       if(flownMailFilterVO.getAssignedDate()!=null){
    	   query.append(" AND EXP.ASGDAT = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssignedDate());
       }
       if(flownMailFilterVO.getSegmentOrigin()!=null && 
    		   flownMailFilterVO.getSegmentOrigin().trim().length() > 0){
    	   query.append(" AND FLTSEG.POL = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentOrigin());
       }
       if(flownMailFilterVO.getSegmentDestination()!=null && 
    		   flownMailFilterVO.getSegmentDestination().trim().length() > 0){
    	   query.append(" AND FLTSEG.POU = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentDestination());
       }
       return query.getResultList(new FlownExceptionsMapper());
       
    }

    
    /**
     *  A-2401
	 * This method is for findFlownMailExceptionsforprint
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
    public Collection<FlownMailExceptionVO> findFlownMailExceptionsforprint(
            FlownMailFilterVO flownMailFilterVO) throws SystemException {
       Query query =getQueryManager().createNamedNativeQuery(FIND_FLOWN_MAIL_EXCEPTIONS);
       int index = 0;
       query.setParameter(++index,flownMailFilterVO.getCompanyCode());
       if(flownMailFilterVO.getFlightCarrierCode()!=null && 
    		   flownMailFilterVO.getFlightCarrierCode().trim().length()>0){
    	   query.append(" AND MST.FLTCARIDR = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getFlightCarrierId());    	   
       }
       if(flownMailFilterVO.getFlightNumber()!=null &&
    		   flownMailFilterVO.getFlightNumber().trim().length() > 0){
    	   query.append(" AND MST.FLTNUM = ?");
    	   query.setParameter(++index,flownMailFilterVO.getFlightNumber());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null ){
    	   query.append(" AND MST.FLTDAT >= ?");
    	   query.setParameter(++index,flownMailFilterVO.getFromDate());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null && 
    		   flownMailFilterVO.getToDate()!=null){
    	   query.append(" AND MST.FLTDAT <= ?");    	   
    	   query.setParameter(++index,flownMailFilterVO.getToDate());
       }
       if(flownMailFilterVO.getExceptionCode()!=null && 
    		   flownMailFilterVO.getExceptionCode().trim().length() > 0){
    	   query.append(" AND EXP.EXPCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getExceptionCode());
       }
       if(flownMailFilterVO.getAssigneeCode()!=null && 
    		   flownMailFilterVO.getAssigneeCode().trim().length() > 0){
    	   query.append(" AND EXP.ASGCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssigneeCode());
       }
       if(flownMailFilterVO.getAssignedDate()!=null){
    	   query.append(" AND EXP.ASGDAT = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssignedDate());
       }
       if(flownMailFilterVO.getSegmentOrigin()!=null && 
    		   flownMailFilterVO.getSegmentOrigin().trim().length() > 0){
    	   query.append(" AND FLTSEG.POL = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentOrigin());
       }
       if(flownMailFilterVO.getSegmentDestination()!=null && 
    		   flownMailFilterVO.getSegmentDestination().trim().length() > 0){
    	   query.append(" AND FLTSEG.POU = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentDestination());
       }
       return query.getResultList(new FlownExceptionsMapper());
       
    }
    
    /**
     *  A-2401
	 * This method is for findFlownMailExceptionsforprintDetails
	 * @param flownMailFilterVO
	 * @return Collection <FlownMailExceptionVO>
	 * @throws SystemException
	 */
    public Collection<FlownMailExceptionVO> findFlownMailExceptionsforprintDetails(
            FlownMailFilterVO flownMailFilterVO) throws SystemException {
       Query query =getQueryManager().createNamedNativeQuery(FIND_FLOWN_MAIL_EXCEPTIONS_FORPRINT_DETAILS);
       int index = 0;
       query.setParameter(++index,flownMailFilterVO.getCompanyCode());
       if(flownMailFilterVO.getFlightCarrierCode()!=null && 
    		   flownMailFilterVO.getFlightCarrierCode().trim().length()>0){
    	   query.append(" AND MST.FLTCARIDR = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getFlightCarrierId());    	   
       }
       if(flownMailFilterVO.getFlightNumber()!=null &&
    		   flownMailFilterVO.getFlightNumber().trim().length() > 0){
    	   query.append(" AND MST.FLTNUM = ?");
    	   query.setParameter(++index,flownMailFilterVO.getFlightNumber());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null ){
    	   query.append(" AND MST.FLTDAT >= ?");
    	   query.setParameter(++index,flownMailFilterVO.getFromDate());    	   
       }
       if(flownMailFilterVO.getFromDate()!=null && 
    		   flownMailFilterVO.getToDate()!=null){
    	   query.append(" AND MST.FLTDAT <= ?");    	   
    	   query.setParameter(++index,flownMailFilterVO.getToDate());
       }
       if(flownMailFilterVO.getExceptionCode()!=null && 
    		   flownMailFilterVO.getExceptionCode().trim().length() > 0){
    	   query.append(" AND EXP.EXPCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getExceptionCode());
       }
       if(flownMailFilterVO.getAssigneeCode()!=null && 
    		   flownMailFilterVO.getAssigneeCode().trim().length() > 0){
    	   query.append(" AND EXP.ASGCOD = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssigneeCode());
       }
       if(flownMailFilterVO.getAssignedDate()!=null){
    	   query.append(" AND EXP.ASGDAT = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getAssignedDate());
       }
       if(flownMailFilterVO.getSegmentOrigin()!=null && 
    		   flownMailFilterVO.getSegmentOrigin().trim().length() > 0){
    	   query.append(" AND FLTSEG.POL = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentOrigin());
       }
       if(flownMailFilterVO.getSegmentDestination()!=null && 
    		   flownMailFilterVO.getSegmentDestination().trim().length() > 0){
    	   query.append(" AND FLTSEG.POU = ? ");
    	   query.setParameter(++index,flownMailFilterVO.getSegmentDestination());
       }
       query.append(" ) GROUP BY(ARLCOD,FLTCARIDR,CMPCOD,FLTNUM,FLTDAT,POL,POU,EXPCOD,ASGCOD)");
       log.log(Log.FINE, "query", query.toString());
	return query.getResultList(new FlownExceptionsPrintMapper());
       
    }

    /**
  	 * This method is for closeFlight
	 * @param flownMailFilterVO
	 * @return 
	 */
    public void closeFlight(FlownMailFilterVO flownMailFilterVO) {

    }

    /**
     * This method is for Processing a Flight Segment
     * 
     * Mar 26, 2007, A-2401
     * @param flownMailFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     * @see com.ibsplc.icargo.persistence.dao.mail.mra.flown.MRAFlownDAO#processFlight(com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO)
     */
    public String processFlight(FlownMailFilterVO flownMailFilterVO)
    throws SystemException, PersistenceException{
    	
    	Procedure processProcedure = getQueryManager()
    									.createNamedNativeProcedure(
    												MTK_MRA_PROCESSFLIGHT);
    	
    	
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
    														.getLogonAttributesVO();
    	
    	LocalDate lastUpdTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
    	
    	int index = 0;
    	    	
    	processProcedure.setParameter(++index, flownMailFilterVO.getCompanyCode());
    	processProcedure.setParameter(++index, flownMailFilterVO.getFlightCarrierId());
    	processProcedure.setParameter(++index, flownMailFilterVO.getFlightNumber());
    	processProcedure.setParameter(++index, flownMailFilterVO.getFlightSequenceNumber());
    	processProcedure.setParameter(++index, flownMailFilterVO.getSegmentSerialNumber());
    	processProcedure.setParameter(++index, logonAttributes.getUserId());
    	processProcedure.setParameter(++index, lastUpdTime.toSqlTimeStamp());
    	processProcedure.setOutParameter(++index, SqlType.STRING);
    	
    	processProcedure.execute();
    	
    	   	
    	
    	
    	return (String) processProcedure.getParameter(index);
    }

    /**
    
	 * This method is for findFlightDetails
	 * @param flownMailFilterVO
	 * @return Collection<FlownMailSegmentVO>
	 * @throws SystemException
	 */
	public Collection<FlownMailSegmentVO> findFlightDetails(FlownMailFilterVO flownMailFilterVO)throws SystemException {
		// TODO Auto-generated method stub
		Query query = getQueryManager().createNamedNativeQuery(FIND_FLIGHTDETAILS);
		
		query.setParameter(1, flownMailFilterVO.getFlightNumber());
		query.setParameter(2, flownMailFilterVO.getFlightCarrierId());
		query.setParameter(3, flownMailFilterVO.getFlightSequenceNumber());
		query.setParameter(4, flownMailFilterVO.getCompanyCode());
			
		return query.getResultList(new FlownSegmentDetailsMapper());
	}
	
	
	/**
     * @author A-2449
     * @param flownMailFilterVO
     * @return Collection<FlownMailSegmentVO>
     * @throws SystemException
     * 
     */
	public Collection<FlownMailSegmentVO> findListOfFlightsForReport(
			FlownMailFilterVO flownMailFilterVO) throws SystemException{
		
		
		Query query = getQueryManager().createNamedNativeQuery(FIND_LISTOFFLIGHTS);
		log.log(Log.INFO, "Query from SQLDAO----->", query);
		int dataIndex = 0;
		query.setParameter(++dataIndex,flownMailFilterVO.getCompanyCode());
		
		if(flownMailFilterVO.getFlightCarrierCode() != null && 
				flownMailFilterVO.getFlightCarrierCode().length() > 0){
			if(flownMailFilterVO.getFlightCarrierCode().trim().length() == 2){
				query.append(" AND ARL.TWOAPHCOD = ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFlightCarrierCode());
			}
			else{
				query.append( "AND ARL.THRAPHCOD = ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFlightCarrierCode());
			}
			
		}		
		
		if(flownMailFilterVO.getFlightNumber() != null 
				&& flownMailFilterVO.getFlightNumber().length() > 0){
			query.append(" AND MST.FLTNUM = ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFlightNumber());
		}
		log.log(Log.INFO, "from date from flownMailFilterVO VO-------->",
				flownMailFilterVO.getFromDate());
		log.log(Log.INFO, "to date from flownMailFilterVO VO------->",
				flownMailFilterVO.getToDate());
		if(flownMailFilterVO.getFromDate() != null && flownMailFilterVO.getToDate() != null ){
			query.append(" AND MST.FLTDAT BETWEEN ? AND ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFromDate());
			query.setParameter(++dataIndex,flownMailFilterVO.getToDate());
		}
		else {			
			if(flownMailFilterVO.getFromDate() != null){
				query.append(" AND MST.FLTDAT >= ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFromDate());
			}
			if(flownMailFilterVO.getToDate() != null){
				query.append(" AND MST.FLTDAT <= ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getToDate());
			}			
		}
		
		if(flownMailFilterVO.getFlightStatus() != null &&
				flownMailFilterVO.getFlightStatus().trim().length() > 0){
			query.append(" AND MST.FLTSTA = ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFlightStatus());
		}
		return query.getResultList(new FlownSegmentDetailsMapper());
	}
	
	/**
     * @author A-2449
     * @param flownMailFilterVO
     * @return Collection<FlownMailSegmentVO>
     * @throws SystemException
     * 
     */
	public Collection<FlownMailSegmentVO> findListOfFlownMailsForReport(
    		FlownMailFilterVO flownMailFilterVO) throws SystemException{
		
		int dataIndex = 0;

		Query query = getQueryManager().createNamedNativeQuery(FIND_LISTOFFLOWNMAILBAGS);
			
		
		
		
		
		
		
		query.setParameter(++dataIndex,flownMailFilterVO.getCompanyCode());
		
		if(flownMailFilterVO.getFlightCarrierCode() != null && 
				flownMailFilterVO.getFlightCarrierCode().length() > 0){
			if(flownMailFilterVO.getFlightCarrierCode().trim().length() == 2){
				query.append(" AND ARL.CMPCOD = ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFlightCarrierCode());
			}
			else{
				query.append( "AND ARL.THRAPHCOD = ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFlightCarrierCode());
			}
			
		}		
		
		if(flownMailFilterVO.getFlightNumber() != null 
				&& flownMailFilterVO.getFlightNumber().length() > 0){
			query.append(" AND MST.FLTNUM = ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFlightNumber());
		}
		log.log(Log.INFO, "from date from flownMailFilterVO VO-------->",
				flownMailFilterVO.getFromDate());
		log.log(Log.INFO, "to date from flownMailFilterVO VO------->",
				flownMailFilterVO.getToDate());
		if(flownMailFilterVO.getFromDate() != null && flownMailFilterVO.getToDate() != null ){
			query.append(" AND MST.FLTDAT BETWEEN ? AND ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFromDate());
			query.setParameter(++dataIndex,flownMailFilterVO.getToDate());
		}
		else {			
			if(flownMailFilterVO.getFromDate() != null){
				query.append(" AND MST.FLTDAT >= ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getFromDate());
			}
			if(flownMailFilterVO.getToDate() != null){
				query.append(" AND MST.FLTDAT <= ? ");
				query.setParameter(++dataIndex,flownMailFilterVO.getToDate());
			}			
		}
		
		if(flownMailFilterVO.getMailOrigin() != null &&
				flownMailFilterVO.getMailOrigin().trim().length() > 0){
			query.append(" AND BLGMST.ORGEXGOFC LIKE ? ");
			StringBuilder strBld=new StringBuilder();
			strBld.append("%").append(flownMailFilterVO.getMailOrigin().toUpperCase()).append("%");
			query.setParameter(++dataIndex,strBld.toString());
		}
		
		if(flownMailFilterVO.getMailDestination() != null &&
				flownMailFilterVO.getMailDestination().trim().length() > 0){
			query.append(" AND BLGMST.DSTEXGOFC LIKE ? ");
			StringBuilder strBld=new StringBuilder();
			strBld.append("%").append(flownMailFilterVO.getMailDestination().toUpperCase()).append("%");
			query.setParameter(++dataIndex,strBld.toString());
		}
		
		if(flownMailFilterVO.getFlightOrigin() != null &&
				flownMailFilterVO.getFlightOrigin().trim().length() > 0){
			log.log(Log.INFO, "flownMailFilterVO.getFlightOrigin()----->",
					flownMailFilterVO.getFlightOrigin());
			query.append(" AND SEG.POL = ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFlightOrigin().toUpperCase());
		}
		
		if(flownMailFilterVO.getFlightDestination() != null &&
				flownMailFilterVO.getFlightDestination().trim().length() > 0){
			log.log(Log.INFO, "flownMailFilterVO.getFlightDestination()----->",
					flownMailFilterVO.getFlightDestination());
			query.append(" AND SEG.POU = ? ");
			query.setParameter(++dataIndex,flownMailFilterVO.getFlightDestination().toUpperCase());
		}
		
		
		// added for ICRD-69692 ends
		return query.getResultList(new FlownSegmentDetailsMapper());
		
	}
	/***
	 * @author A-2270
	 */
	public Collection<FlownMailRevenueVO> generateMailRevenueReportDetails(FlownMailFilterVO flownMailFilterVO)throws SystemException{
		  log.entering("MRAFlownSqlDao", "generateMailRevenueReportDetails");
		  Query query = getQueryManager().createNamedNativeQuery(MTK_MRA_FLOWN_FINDFLOWN_REVENUE_REPORT_DTLS);
		   // "800210"
		  int index = 0;
		  query.setParameter(++index, flownMailFilterVO.getCompanyCode());
		  
		  if(flownMailFilterVO.getAccountingMonth()!= null &&flownMailFilterVO.getAccountingMonth().trim().length()>0 ){
			  query.append(" AND  TXN.ACCMON = ? ");
			  query.setParameter(++index, flownMailFilterVO.getAccountingMonth());
		  }
		  if(flownMailFilterVO.getAccountingYear() != null && flownMailFilterVO.getAccountingYear().trim().length() >0){
				query.append(" AND TXN.FINYER  = ? ");
				query.setParameter(++index, Integer.parseInt(flownMailFilterVO.getAccountingYear()));
			}
		  if(flownMailFilterVO.getFlightNumber() != null && flownMailFilterVO.getFlightNumber().trim().length() >0){
				query.append(" AND TXN.FLTNUM  = ? ");
				query.setParameter(++index, flownMailFilterVO.getFlightNumber());
			}
		  
		  if(flownMailFilterVO.getFromDate() != null){
				query.append(" AND TXN.EXEDAT  >  ? ");
				query.setParameter(++index, flownMailFilterVO.getFromDate());
			}
		  if(flownMailFilterVO.getToDate() != null){
				query.append(" AND TXN.EXEDAT  <  ? ");
				query.setParameter(++index, flownMailFilterVO.getToDate());
			}
		  query.append( " GROUP BY TXN.CMPCOD,TXN.FLTNUM,TXN.EXEDAT,TXN.SEGORGCOD,TXN.SEGDSTCOD,MST.ACCCOD, ");
          query.append( " TXN.BLGBAS,txn.csgdocnum,txn.csgseqnum,txn.poacod,txn.mstdocnum  ");
	      query.append("  ORDER BY FUNPNT,EXEDAT,FLTNUM "); 
	      return query.getResultList(new MailRevenueReportDetailsMapper(flownMailFilterVO));
		
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.flown.MRAFlownDAO#importArrivedMailstoMRA(java.lang.String)
	 *	Added by 			: A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void importArrivedMailstoMRA(String companyCode)
			throws SystemException {
		log.entering(CLASS_NAME, "importArrivedMailstoMRA");
    	Procedure proc = getQueryManager().createNamedNativeProcedure(MTK_MRA_IMPORTARRIVEDMAILS);
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		log.log(Log.FINE, "Current Date: ", currentDate);
    	proc.setSensitivity(true);
    	int index = 0;
    	proc.setParameter(++index, companyCode);
    	proc.setParameter(++index, currentDate.toSqlTimeStamp());
    	proc.setParameter(++index, logonAttributes.getUserId());
    	proc.setOutParameter(++index, SqlType.STRING);
    	proc.execute(); 
    	String outParameter = (String)proc.getParameter(index);
    	log.log(Log.FINE, "Out parameter obtained",outParameter);
		log.exiting(CLASS_NAME, "importArrivedMailstoMRA");
	}
	
	/**
	 * @author A-7794 as part of ICRD-232299
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @throws SystemException 
	 */
	public void forceImportScannedMailbags(String companyCode,String startDate,String endDate) throws SystemException{
		log.entering(CLASS_NAME, "forceImportScannedMailbags");
		Procedure proc = getQueryManager().createNamedNativeProcedure(MTK_MRA_IMPORTMISSEDMAILS_ON_DELIVERYSCAN);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		int startDat=0;
		int endDat=0;
		try{
		startDat=Integer.parseInt(startDate);
		endDat=Integer.parseInt(endDate);
		}catch (NumberFormatException numberFormatException){
			log.log(Log.INFO, numberFormatException);
			}
    	proc.setSensitivity(true);
    	int index = 0;
    	proc.setParameter(++index, companyCode);
    	
    	 if(isOracleDataSource()) {
    	proc.setParameter(++index, startDat);
    	 }
    	 else
    	 {
    		 proc.setParameter(++index, startDate); 
    	 }
    	 if(isOracleDataSource()) {
    	proc.setParameter(++index, endDat);
    	 }
    	 else
    	 {
    		 proc.setParameter(++index, endDate); 	 
    	 }
    	proc.setParameter(++index, logonAttributes.getUserId());
    	proc.setParameter(++index, currentDate.toSqlTimeStamp());
    	proc.setOutParameter(++index, SqlType.STRING);
    	proc.execute(); 
    	String outParameter = (String)proc.getParameter(index);
    	log.log(Log.FINE, "Out parameter obtained",outParameter);
		log.exiting(CLASS_NAME, "forceImportScannedMailbags");
	}
}
