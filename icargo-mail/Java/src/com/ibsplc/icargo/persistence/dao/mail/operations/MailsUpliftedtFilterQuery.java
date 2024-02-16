/*
 * MailsUpliftedtFilterQuery.java Created on Mar 12, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
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
/***
 * 
 * @author a-1936
 *
 */
public class MailsUpliftedtFilterQuery extends NativeQuery {

	private MailStatusFilterVO mailStatusFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MailTracking Defaults");

	public MailsUpliftedtFilterQuery(MailStatusFilterVO mailStatusFilterVO,
			String baseQuery) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	@Override
	public String getNativeQuery() {
		log.entering("MailsUpliftedtFilterQuery", "getNativeQuery");
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		int index = 0;
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

		log.log(Log.FINE, "The from Scan date String", fromScanDateString);
		log.log(Log.FINE, "The toScan date String", toScanDateString);
		String paJoin = null;
		/*
		 * The Join conditions to be needed when the pacode is entered as a
		 * filter ..
		 * 
		 * 
		 */
		if (mailStatusFilterVO.getPacode() != null
				&& mailStatusFilterVO.getPacode().trim().length() > 0) {
			paJoin = new StringBuilder("  INNER JOIN MALEXGOFCMST EXG ON  ")
					.append(" MALMST.CMPCOD = EXG.CMPCOD AND ").append(
							" MALMST.ORGEXGOFC = EXG.EXGOFCCOD ").toString();
			queryBuilder.append(paJoin);
		}

		queryBuilder.append(" WHERE  FLTSEG.CMPCOD = ?  ");
		this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
		queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");

		if (fromScandate != null) {
			queryBuilder
					.append("  AND  TRUNC ( MALULD.SCNDAT) >=TO_DATE (?,'yyyy-MM-dd') ");
			this.setParameter(++index, fromScanDateString);
		}

		if (toScandate != null) {
			queryBuilder
					.append("  AND TRUNC ( MALULD.SCNDAT) <=TO_DATE (?,'yyyy-MM-dd') ");
			this.setParameter(++index, toScanDateString);
		}

		if (mailStatusFilterVO.getFlightNumber() != null
				&& mailStatusFilterVO.getFlightNumber().length() > 0) {
			queryBuilder.append(" AND FLTSEG.FLTNUM = ? ");
			this.setParameter(++index, mailStatusFilterVO.getFlightNumber());
		}

		if (mailStatusFilterVO.getFlightCarrierid() > 0) {
			queryBuilder.append(" AND FLTSEG.FLTCARIDR = ? ");
			this.setParameter(++index, mailStatusFilterVO.getFlightCarrierid());
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
		 * To check if the Flight is Departed . Only Not Departed Flights need
		 * to be fetched.
		 */
		queryBuilder.append("    AND  OPRLEG.ATD IS NOT  NULL  ");

		/*
		 * If all mails that are without cardit has to be needed .
		 * 
		 * 
		 */

		if (MailConstantsVO.MAIL_UPLIFTED_WITHOUT_CARDIT
				.equals(mailStatusFilterVO.getCurrentStatus())) {
			queryBuilder.append("   AND NOT EXISTS ( ");
			queryBuilder.append("    SELECT 1 ");
			queryBuilder.append("        FROM MALCDTRCP ");
			queryBuilder.append("   WHERE CMPCOD = MALMST.CMPCOD");
			queryBuilder.append("  AND RCPIDR   = MALMST.MALIDR) ");     

		}

		/*
		 * If all the Mails that are not delivered is needed ..
		 * 
		 * 
		 */
		if (MailConstantsVO.MAIL_UPLIFTED_NOT_DELIVERED
				.equals(mailStatusFilterVO.getCurrentStatus())) {
			queryBuilder.append("    AND  MALULD.DLVSTA <> 'Y' AND MALULD.ACPSTA ='Y' ");

		}
		return queryBuilder.toString();

	}
}
