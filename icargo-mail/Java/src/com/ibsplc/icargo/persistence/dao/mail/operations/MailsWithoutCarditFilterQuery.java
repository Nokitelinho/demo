/*
 * MailsWithoutCarditFilterQuery.java Created on Mar 11, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
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
 * @author A-2553
 * 
 */
public class MailsWithoutCarditFilterQuery extends NativeQuery {

	private String baseQuery;
	
	private MailStatusFilterVO mailStatusFilterVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * @throws SystemException
	 * @param baseQuery
	 * @param mailStatusFilterVO
	 */
	public MailsWithoutCarditFilterQuery(String baseQuery, 
			MailStatusFilterVO mailStatusFilterVO) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.mailStatusFilterVO = mailStatusFilterVO;
	}

	/**
	 *
	 * Mar 11, 2008, A-2553
	 * @return
	 * */
	@Override
	public String getNativeQuery() {
		log.entering("MailsWithoutCarditFilterQuery", "getNativeQuery");
		
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		int idx = 0;
		if(mailStatusFilterVO.getPacode() != null && mailStatusFilterVO.getPacode().length()>0){
			queryBuilder.append(" INNER JOIN MALEXGOFCMST EXG ")
			.append(" ON EXG.CMPCOD = MST.CMPCOD ")
			.append(" AND EXG.EXGOFCCOD = MST.ORGEXGOFC ")
			.append(" AND EXG.POACOD =  ? ");
			setParameter(++idx,mailStatusFilterVO.getPacode()); 
		}
		queryBuilder.append(" WHERE MST.CMPCOD =?");
		setParameter(++idx,mailStatusFilterVO.getCompanyCode());	
		queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");
		if(mailStatusFilterVO.getFromDate()!=null){
			queryBuilder.append(" AND TRUNC(MST.SCNDAT) >= TO_DATE(?, 'yyyy-MM-dd')");
			LocalDate dt = mailStatusFilterVO.getFromDate();
			Date d= dt.toSqlDate();
			String dat = String.valueOf(d);
			setParameter(++idx,dat);
		}
		if(mailStatusFilterVO.getToDate()!=null){
			queryBuilder.append(" AND TRUNC(MST.SCNDAT) <= TO_DATE(?, 'yyyy-MM-dd')");
			LocalDate dt = mailStatusFilterVO.getToDate();
			Date d= dt.toSqlDate();
			String dat = String.valueOf(d);
			setParameter(++idx,dat);
		}
		if(mailStatusFilterVO.getFlightCarrierid()>0){
			queryBuilder.append(" AND MST.FLTCARIDR = ?");
			setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
		}
		if(mailStatusFilterVO.getCarrierid()>0){
			queryBuilder.append(" AND MST.FLTCARIDR = ?");
			setParameter(++idx,mailStatusFilterVO.getCarrierid());
		}
		if(mailStatusFilterVO.getFlightNumber() !=null && mailStatusFilterVO.getFlightNumber().length()>0){
			queryBuilder.append(" AND MST.FLTNUM = ?");
			setParameter(++idx,mailStatusFilterVO.getFlightNumber());
		}
		if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0){
			queryBuilder.append(" AND MST.SCNPRT = ?");
			setParameter(++idx,mailStatusFilterVO.getPol());
		}
		if(mailStatusFilterVO.getPou() != null && mailStatusFilterVO.getPou().length()>0){
			queryBuilder.append(" AND MST.POU = ?");
			setParameter(++idx,mailStatusFilterVO.getPou());
		}
		queryBuilder.append(" AND MST.MALSTA <> 'RTN' ");
		queryBuilder.append(" AND MST.MALSTA <> 'TRA' ");
		queryBuilder.append(" AND MST.FLTSEQNUM <> 0 ");		
		queryBuilder.append(" AND NOT EXISTS ")
					.append(" (SELECT 1 FROM MALCDTRCP ")
					.append(" WHERE MALCDTRCP.CMPCOD = MST.CMPCOD ")
					.append(" AND MALCDTRCP.RCPIDR = MST.MALIDR) ");
		queryBuilder.append(" ORDER BY HIS.FLTCARCOD,HIS.FLTNUM, HIS.FLTDAT, MST.MALIDR ");
		log.exiting("MailsWithoutCarditFilterQuery", "getNativeQuery");
		return queryBuilder.toString();
	}
		
}