/*
 * ExpectedMailTranshipFilterQuery.java Created on Mar 17, 2008
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
 * @author a-2553
 *
 */
public class ExpectedMailTranshipFilterQuery extends NativeQuery {

	private MailStatusFilterVO mailStatusFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MailTracking Defaults");

	public ExpectedMailTranshipFilterQuery(MailStatusFilterVO mailStatusFilterVO,
			String baseQuery) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	@Override
	public String getNativeQuery() {
		log.entering("ExpectedMailTranshipFilterQuery", "getNativeQuery");
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
					.append(" CDTTRT.CMPCOD = EXG.CMPCOD AND ").append(
							" CDTRCP.ORGEXGOFF = EXG.EXGOFCCOD ").toString();
			queryBuilder.append(paJoin);
		}
			queryBuilder.append("  WHERE    CDTTRT.CMPCOD = ?  ");
			this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
			if (mailStatusFilterVO.getPol() != null
					&& mailStatusFilterVO.getPol().trim().length() > 0) {
				queryBuilder.append(" AND CDTTRT.ORGCOD  = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPol());
			}
			if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND CDTTRT.DSTCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getPou());
			}
			if (mailStatusFilterVO.getPacode() != null
					&& mailStatusFilterVO.getPacode().trim().length() > 0) {
				queryBuilder.append("    AND EXG.POACOD = ?  ");
				this.setParameter(++index, mailStatusFilterVO.getPacode());
			}
			if (mailStatusFilterVO.getFlightNumber() != null
					&& mailStatusFilterVO.getFlightNumber().length() > 0) {
				queryBuilder.append(" AND CDTTRT.FLTNUM = ? ");
				this.setParameter(++index, mailStatusFilterVO.getFlightNumber());
			}

			if (mailStatusFilterVO.getCarrierCode()!=null && mailStatusFilterVO.getCarrierCode().trim().length()>0) {
				queryBuilder.append(" AND CDTTRT.CARCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getCarrierCode());
			}
			if (fromScandate != null) {
				queryBuilder
						.append(" 	AND TRUNC ( CDTTRT.DEPTIM) >=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, fromScanDateString);
			}
			if (toScandate != null) {
				queryBuilder
						.append(" AND  TRUNC ( CDTTRT.DEPTIM) <=TO_DATE (?,'yyyy-MM-dd') ");
				this.setParameter(++index, toScanDateString);
			}
			queryBuilder
			.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN')");
		return queryBuilder.toString();

	}

}
