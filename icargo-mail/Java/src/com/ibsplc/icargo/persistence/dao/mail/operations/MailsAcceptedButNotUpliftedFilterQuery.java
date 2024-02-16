/*
 * MailsAcceptedButNotUpliftedFilterQuery.java Created on Nov 11, 2010
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3278
 *
 */
public class MailsAcceptedButNotUpliftedFilterQuery extends NativeQuery {

	private MailStatusFilterVO mailStatusFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MailTracking Defaults");

	public MailsAcceptedButNotUpliftedFilterQuery(
			MailStatusFilterVO mailStatusFilterVO, String baseQuery)
			throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	@Override
	public String getNativeQuery() {
		log.entering("MailsAcceptedButNotUpliftedFilterQuery", "getNativeQuery");
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		int index = 0;
		boolean isUnion = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
				.getFlightCarrierid() == 0);
		boolean isCarrierAlone = (mailStatusFilterVO.getCarrierid() > 0 && mailStatusFilterVO
				.getFlightCarrierid() == 0);
		boolean isFlightAlone = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
				.getFlightCarrierid() > 0);
		
		
		
		log.log(Log.FINE, "The Flag isUnion is ", isUnion);
		log.log(Log.FINE, "The isCarrierAlone is", isCarrierAlone);
		log.log(Log.FINE, "isFlightAlone is", isFlightAlone);
		log.log(Log.FINE, "The Mail status Filter Vo is ", mailStatusFilterVO);
		String paJoin = null;
		/*
		 * The Join conditions to be needed when the pacode is entered as a filter ..
		 * 
		 * 
		 */
		//Added by A-5945 for ICRD-103361 starts
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
		
		//Added by A-5945 for ICRD-103361 ends
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

			queryBuilder.append(" AND OPRMST.FLTSTA NOT IN ('TBA','TBC','CAN') ");

			if (fromScanDateString != null) {
				queryBuilder
						.append(" 	AND TRUNC ( MALULD.SCNDAT) >=TO_DATE (?,'yyyy-MM-dd')  ");
				this.setParameter(++index, fromScanDateString);
			}

			if (toScanDateString != null) {
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

			if (mailStatusFilterVO.getFlightDate() != null) {
				queryBuilder
						.append(" AND ASGFLT.FLTDAT = ?");
				this.setParameter(++index, mailStatusFilterVO.getFlightDate());
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
			
			/*if (mailStatusFilterVO.getMailCategoryCode() != null
					&& mailStatusFilterVO.getMailCategoryCode().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.MALCTGCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getMailCategoryCode());
			}*/

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
			String acqContainer = MailConstantsVO.CONST_BULK_ACQ_ARP.concat("%");
			String arrContainer = MailConstantsVO.CONST_BULK_ARR_ARP.concat("%");
			queryBuilder.append(" AND MALULD.CONNUM not like ? ");	
			this.setParameter(++index,acqContainer);			
			queryBuilder.append(" AND MALULD.CONNUM not like ? ");		
			this.setParameter(++index, arrContainer);		

		}

		if (isUnion || isCarrierAlone) {

			if (isUnion) {
				queryBuilder.append("  UNION ALL");
				queryBuilder
						.append(" SELECT MALULD.ARPCOD AS POL, MALMST.POU, '-1' FLTNUM, ");
				queryBuilder.append(" MALMST.MALIDR, MALMST.WGT, ");   
				queryBuilder
						.append(" TO_DATE (NULL) AS FLTDAT , (SELECT (CASE WHEN APHCODUSE = 2  THEN TWOAPHCOD ELSE THRAPHCOD END )  FROM SHRARLMST     WHERE CMPCOD = MALULD.CMPCOD     AND ARLIDR = MALULD.FLTCARIDR)    FLTCARCOD , ");
				queryBuilder.append(" MALULD.CONNUM, TO_DATE (NULL) AS STD, '' LEGSTA, '' FLTROU ");
				queryBuilder.append(" FROM MALARPULDDTL  MALULD "); 
				queryBuilder.append("INNER JOIN MALMST MALMST ON MALMST.CMPCOD            = MALULD.CMPCOD AND MALMST.FLTCARIDR        = MALULD.FLTCARIDR AND MALMST.MALSEQNUM        = MALULD.MALSEQNUM "); 

				if (mailStatusFilterVO.getPacode() != null
						&& mailStatusFilterVO.getPacode().trim().length() > 0) {  
					queryBuilder.append(paJoin);

				}

			}

			queryBuilder.append(" WHERE   MALULD.CMPCOD = ?  ");
			this.setParameter(++index, mailStatusFilterVO.getCompanyCode());

			if (fromScanDateString != null) {
				queryBuilder
						.append(" 	AND TRUNC ( MALULD.SCNDAT) >=TO_DATE (?,'yyyy-MM-dd')  ");
				this.setParameter(++index, fromScanDateString);
			}

			if (toScanDateString != null) {
				queryBuilder
						.append("  AND  TRUNC ( MALULD.SCNDAT) <=TO_DATE (?,'yyyy-MM-dd') ");
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
			/*if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.ARPCOD = ? ");
				this.setParameter(++index, "");
			}*/
			//END
			
			/*if (mailStatusFilterVO.getMailCategoryCode() != null
					&& mailStatusFilterVO.getMailCategoryCode().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.MALCTGCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getMailCategoryCode());
			}*/

			if (mailStatusFilterVO.getCarrierid() > 0) {
				queryBuilder.append(" AND MALULD.FLTCARIDR = ? ");
				this.setParameter(++index, mailStatusFilterVO.getCarrierid());
			}
			if (mailStatusFilterVO.getPacode() != null
					&& mailStatusFilterVO.getPacode().trim().length() > 0) {
				queryBuilder.append("    AND EXG.POACOD = ?  ");
				this.setParameter(++index, mailStatusFilterVO.getPacode());
			}
			
			//added by A-8149 for ICRD-261476 starts
			if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND MALMST.POU  = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPou());
			}
			//added by A-8149 for ICRD-261476 ends
			
			String acqContainer = MailConstantsVO.CONST_BULK_ACQ_ARP.concat("%");
			String arrContainer = MailConstantsVO.CONST_BULK_ARR_ARP.concat("%");
			queryBuilder.append(" AND MALULD.CONNUM not like ? ");	
			this.setParameter(++index,acqContainer);			
			queryBuilder.append(" AND MALULD.CONNUM not like ? ");		
			this.setParameter(++index, arrContainer);			

		}
		//queryBuilder.append(" order by MALULD.MALIDR ");
		return queryBuilder.toString();

	}

}
