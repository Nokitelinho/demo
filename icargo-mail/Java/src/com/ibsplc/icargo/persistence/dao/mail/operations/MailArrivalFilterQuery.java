/*
 * MailArrivalFilterQuery.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Jan 22, 2007			A-1739		Created
 */
public class MailArrivalFilterQuery extends NativeQuery {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private MailArrivalFilterVO mailArrivalFilterVO;
	
	private String baseQuery;
	
	/**
	 * @throws SystemException
	 */
	public MailArrivalFilterQuery(MailArrivalFilterVO mailArrivalFilterVO, String baseQuery) 
		throws SystemException {
		super();
		this.mailArrivalFilterVO = mailArrivalFilterVO;
		this.baseQuery = baseQuery;
	}

	/**
	 * TODO Purpose
	 * Jan 22, 2007, A-1739
	 * @return
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery#getNativeQuery()
	 */
	@Override
	public String getNativeQuery() {
		log.entering("MailArrivalFilterQuery", "getNativeQuery");
		
		String mailStatus = mailArrivalFilterVO.getMailStatus();
		String paCode = mailArrivalFilterVO.getPaCode();
		int nextCarrierId = mailArrivalFilterVO.getNextCarrierId();
		String nextCarrierCode=mailArrivalFilterVO.getNextCarrierCode();
		
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		
		if(!MailConstantsVO.MAIL_STATUS_ALL.equals(mailStatus)) {
			queryBuilder.append(" INNER JOIN SHRCTYMST CTY ON ").
			append(" CTY.CMPCOD = MALMST.CMPCOD ").
			append(" AND CTY.CTYCOD =  (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MALMST.CMPCOD AND EXGOFCCOD = MALMST.DSTEXGOFC) ");
		}
		
		if(MailConstantsVO.MAIL_TERMINATING.equals(mailStatus)) {
			if(paCode != null) {
				queryBuilder.append(" LEFT OUTER JOIN MALEXGOFCMST EXGOFC ON ").
				append(" EXGOFC.EXGOFCCOD = MALMST.DSTEXGOFC ");
			}
		} else  if(MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus)) {
			if(nextCarrierId > 0 || (nextCarrierCode!=null && 
					nextCarrierCode.trim().length()>0)) {
				queryBuilder.append(" LEFT OUTER JOIN MALTRFMFTDTL MFTDSN ").
				append(" ON MFTDSN.MALSEQNUM = MALMST.MALSEQNUM ").
				append(" AND MFTDSN.CMPCOD = MALMST.CMPCOD").
				append(" INNER JOIN MALTRFMFT TRFMFT ON ").
				append( " TRFMFT.TRFMFTIDR   = MFTDSN.TRFMFTIDR");
			}
		}
		
		queryBuilder.append(" WHERE FLT.CMPCOD = ? ").
        append(" AND FLT.FLTCARIDR = ? ").
        append(" AND FLT.FLTNUM = ? ").
        append(" AND FLT.FLTSEQNUM = ? ").
        append(" AND FLT.POU = ? ");
		

		int idx = 0;
		setParameter(++idx, MailConstantsVO.MAIL_STATUS_ARRIVED);
		setParameter(++idx, mailArrivalFilterVO.getPou()); 
		/*
		 * Added By Karthick V as the  part of the  Bug Fix in the Query
		 * 
		 */
		setParameter(++idx, mailArrivalFilterVO.getPou());
        setParameter(++idx, mailArrivalFilterVO.getCompanyCode());
        setParameter(++idx, mailArrivalFilterVO.getCarrierId());
        setParameter(++idx, mailArrivalFilterVO.getFlightNumber());
        setParameter(++idx, mailArrivalFilterVO.getFlightSequenceNumber());
        setParameter(++idx, mailArrivalFilterVO.getPou());
        
        
		if(MailConstantsVO.MAIL_TERMINATING.equals(mailStatus))  {
			queryBuilder.append(" AND CTY.SRVARPCOD = ? ");
			setParameter(++idx, mailArrivalFilterVO.getPou());
			
//			if(paCode != null && paCode.trim().length() > 0) {
//				queryBuilder.append(" AND EXGOFC.POACOD = ? ");
//				setParameter(++idx, mailArrivalFilterVO.getPaCode());
//			}
			
		} else if(MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus)) {
			queryBuilder.append(" AND CTY.SRVARPCOD <> ? ");			
			setParameter(++idx, mailArrivalFilterVO.getPou());
		}
		if(MailConstantsVO.MAIL_TERMINATING.equals(mailStatus)||MailConstantsVO.MAIL_STATUS_ALL.equals(mailStatus))  {
			if(paCode != null && paCode.trim().length() > 0) {
				queryBuilder.append(" AND EXGOFC.POACOD = ? ");
				setParameter(++idx, mailArrivalFilterVO.getPaCode());
			}
	     }
		 if(nextCarrierCode!=null && nextCarrierCode.trim().length()>0
					 &&(MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus))) {
					queryBuilder.append(" AND TRFMFT.ARPCOD = ? ").				
					append(" AND TRFMFT.ONWCARCOD = ? ");
				setParameter(++idx, mailArrivalFilterVO.getPou());
				 /*
				 * Note:-
				 * NCA Doesnt have the Schedules of the NH in their System where Querying with the Airline ID 
				 * will return no  results .so Use the Carrier Code as well ...
				 */
				setParameter(++idx, mailArrivalFilterVO.getNextCarrierCode());
			}
		// queryBuilder.append(" AND NOT (COALESCE(CON.DSTCOD,BULKCON.DSTCOD) IS NULL) "); 
		// queryBuilder.append(" AND ((NOT (COALESCE(CON.DSTCOD,BULKCON.DSTCOD) IS NULL)) or MALMST.CONNUM IS NOT NULL ) ");
		queryBuilder.append(" ORDER BY ULD.ULDNUM, ULD.SEGSERNUM,").
		append(" MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, ").
		append(" MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ,MALMST.MALIDR");
		
		log.exiting("MailArrivalFilterQuery", "getNativeQuery");
		return queryBuilder.toString();
	}
}
