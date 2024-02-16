/*
 * MailsNotUpliftedFilterQuery.java Created on Mar 12, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-1936
 *
 */
public class MailsNotUpliftedFilterQuery extends NativeQuery {

	private MailStatusFilterVO mailStatusFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MailTracking Defaults");

	public MailsNotUpliftedFilterQuery(MailStatusFilterVO mailStatusFilterVO,
			String baseQuery) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	@Override
	public String getNativeQuery() {
		log.entering("MailsNotUpliftedFilterQuery", "getNativeQuery");
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		int index = 0;
		boolean isUnion = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
				.getFlightCarrierid() == 0);
		boolean isCarrierAlone = (mailStatusFilterVO.getCarrierid() > 0 && mailStatusFilterVO
				.getFlightCarrierid()==0);
		boolean isFlightAlone = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
				.getFlightCarrierid()>0);
		LocalDate fromScandate = mailStatusFilterVO.getFromDate();
		Date fromSqlDate = null;
		if (fromScandate != null) {
			fromSqlDate = fromScandate.toSqlDate();
		}
		String fromScanDateString = String.valueOf(fromSqlDate);

		LocalDate toScandate = mailStatusFilterVO.getToDate();
		Date toSqlDate = null;
		if (toScandate != null) {
			toSqlDate = toScandate.toSqlDate();
		}
		String toScanDateString = String.valueOf(toSqlDate);
		log.log(Log.FINE, "The Flag isUnion is", isUnion);
		log.log(Log.FINE, "The isCarrierAlone is", isCarrierAlone);
		log.log(Log.FINE, "isFlightAlone is", isFlightAlone);
		log.log(Log.FINE, "The from Scan date String", fromScanDateString);
		log.log(Log.FINE, "The toScan date String", toScanDateString);
		log.log(Log.FINE, "The Mail status Filter Vo is ", mailStatusFilterVO);
		String paJoin = null;
        /*
         * The Join conditions to be needed when the pacode is entered as a filter ..
         * 
         * 
         */
		if (mailStatusFilterVO.getPacode() != null
				&& mailStatusFilterVO.getPacode().trim().length() > 0) {
			paJoin = new StringBuilder("  INNER JOIN MALEXGOFCMST EXG ON  ")
					.append(" MALULD.CMPCOD = EXG.CMPCOD AND ").append(
							" MALULD.ORGEXGOFC = EXG.EXGOFCCOD ").toString();
			queryBuilder.append(paJoin);
		}
        /*
         * The following Possibilites are possible :- 
         * 1. Flight Present ( Flag used - isFlightAlone):-
         *     The system should fetch all the mailbags in that flight that are not uplifted.
         * 2.Carrier Present(Flag used- isCarrierAlone)
         *    The  system should fetch all the mailbags that are 1.latest  carrier Assigned 
         *                                                       2.Offloaded Mails 
         *   Note:-
         *    As if now the system will also fetch all the mails that are in the Inventory at that port 
         *    when an arrival happens and the Delivery or Transfer for that Particular Mailbag has not  happened .
         *    Actually these are not actually a Pure  Carrier Acceptance at that Port..
         *    TODO  Later:-
         *    If these inventoy mails are not required Code to be changed Accordingly
         * 3.Both absent (Flag used- isUnion)
         */
		
		if (isUnion || isFlightAlone) {
			queryBuilder.append("  WHERE    FLTSEG.CMPCOD = ?  ");
			this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
			queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");
			if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
				queryBuilder.append(" AND MALULD.ARRSTA = 'N' ");
			}
			if (fromScandate != null) {
				queryBuilder
						.append(" 	AND TRUNC ( MALULD.SCNDAT) >=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, fromScanDateString);
			}

			if (toScandate != null) {
				queryBuilder
						.append(" AND  TRUNC ( MALULD.SCNDAT) <=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, toScanDateString);
			}

			if (mailStatusFilterVO.getFlightNumber() != null
					&& mailStatusFilterVO.getFlightNumber().length() > 0) {
				queryBuilder.append(" AND FLTSEG.FLTNUM = ? ");
				this
						.setParameter(++index, mailStatusFilterVO
								.getFlightNumber());
			}

			if (mailStatusFilterVO.getFlightCarrierid() > 0) {
				queryBuilder.append(" AND FLTSEG.FLTCARIDR = ? ");
				this.setParameter(++index, mailStatusFilterVO
						.getFlightCarrierid());
			}

			if (mailStatusFilterVO.getPol() != null
					&& mailStatusFilterVO.getPol().trim().length() > 0) {
				queryBuilder.append(" AND FLTSEG.POL = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPol());
			}

			if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND FLTSEG.POU = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPou());
			}

			if (mailStatusFilterVO.getPacode() != null
					&& mailStatusFilterVO.getPacode().trim().length() > 0) {
				queryBuilder.append("    AND EXG.POACOD = ?  ");
				this.setParameter(++index, mailStatusFilterVO.getPacode());
			}
			
			/*
			 * To check  if the Flight is Departed .
			 * Only Not Departed Flights need to be  fetched.
			 */
			queryBuilder.append("    AND  OPRLEG.ATD IS  NULL  ");
			

		}

		if (isUnion || isCarrierAlone) {

			if (isUnion) {
				queryBuilder.append("  UNION ALL");
				queryBuilder
						.append(" SELECT MALMST.MALIDR, MALULD.ARPCOD AS POL, '' POU, '-1' FLTNUM, ");
				queryBuilder.append(" MALMST.WGT, ");
				queryBuilder.append(" TO_DATE (NULL) AS FLTDAT , (SELECT ( CASE WHEN APHCODUSE = 2  THEN TWOAPHCOD ELSE THRAPHCOD END )  FROM SHRARLMST     WHERE CMPCOD = MALULD.CMPCOD     AND ARLIDR = MALULD.FLTCARIDR)    FLTCARCOD ,");
				queryBuilder.append(" CASE WHEN (CDTMST.CSGDOCNUM) IS NOT NULL THEN 'Y' ELSE 'N' END CDTAVL,MALMST.DSN ");
				queryBuilder.append(" FROM MALARPULDDTL  MALULD ");        
				
				if (mailStatusFilterVO.getPacode() != null
						&& mailStatusFilterVO.getPacode().trim().length() > 0) {
					queryBuilder.append(paJoin);

				}
				
			}
			
			if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
				queryBuilder.append(" INNER JOIN MALMST MALMST ON MALULD.CMPCOD  = MALMST.CMPCOD");
				queryBuilder.append(" AND MALULD.MALSEQNUM = MALMST.MALSEQNUM");
								queryBuilder.append(" AND MALULD.FLTCARIDR  = MALMST.FLTCARIDR");
				queryBuilder.append(" LEFT OUTER JOIN MALCDTMST CDTMST ON MALMST.CMPCOD = CDTMST.CMPCOD AND MALMST.CSGDOCNUM = CDTMST.CSGDOCNUM");				
			}

			queryBuilder.append(" WHERE   MALULD.CMPCOD = ?  ");
			this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
			if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
				queryBuilder.append(" AND MALMST.FLTNUM      =    '-1' ");
			}
			if (fromScandate != null) {
				queryBuilder
						.append(" AND  TRUNC ( MALULD.SCNDAT) >=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, fromScanDateString);
			}

			if (toScandate != null) {
				queryBuilder
						.append("  AND TRUNC ( MALULD.SCNDAT) <=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, toScanDateString);
			}

			if (mailStatusFilterVO.getPol() != null
					&& mailStatusFilterVO.getPol().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.ARPCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPol());
			}
			/*START
			 * If POU is given in the filter,
			 * 1. Carrier only case, the query should not pick any values. 
			 * 2. In no carrier - no flight case, 
			 * 	  the mail in the flight will be taken be fetched by the above written code,
			 * 	  So here in carrier related code we dont want to fetch any data,if POU is given.	
			 */
			if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.ARPCOD = ? ");
				this.setParameter(++index, "");
			}
			//END

			if (mailStatusFilterVO.getCarrierid() > 0) {
				queryBuilder.append(" AND MALULD.FLTCARIDR = ? ");
				this.setParameter(++index, mailStatusFilterVO.getCarrierid());
			}
			if (mailStatusFilterVO.getPacode() != null
					&& mailStatusFilterVO.getPacode().trim().length() > 0) {
				queryBuilder.append("    AND EXG.POACOD = ?  ");
				this.setParameter(++index, mailStatusFilterVO.getPacode());
			}

		}
		return queryBuilder.toString();

	}

}
