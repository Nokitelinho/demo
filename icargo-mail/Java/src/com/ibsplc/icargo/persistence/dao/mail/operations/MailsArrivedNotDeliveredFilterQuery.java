/*
 * MailsArrivedNotDeliveredFilterQuery.java Created on Mar 12, 2008
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



import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2553
 *
 */
public class MailsArrivedNotDeliveredFilterQuery extends NativeQuery {

	private String baseQuery;

	private MailStatusFilterVO mailStatusFilterVO;

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	/**
	 * @throws SystemException
	 * @param baseQuery
	 * @param mailStatusFilterVO
	 */
	public MailsArrivedNotDeliveredFilterQuery(String baseQuery,
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
			queryBuilder.append(" LEFT OUTER JOIN MALEXGOFCMST EXG ");
			queryBuilder.append(" ON EXG.CMPCOD = MAL.CMPCOD")
			.append(" AND EXG.EXGOFCCOD = MAL.ORGEXGOFC")
			.append(" AND EXG.EXGOFCCOD = MAL.ORGEXGOFC");
		}
		if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0
				|| mailStatusFilterVO.getPou() !=null && mailStatusFilterVO.getPou().length()>0){
			queryBuilder.append(" LEFT OUTER JOIN MALFLTSEG ASG ");
			queryBuilder.append(" ON ASG.CMPCOD = ULD.CMPCOD")
			.append(" AND ASG.FLTCARIDR = ULD.FLTCARIDR")
			.append(" AND ASG.FLTNUM = ULD.FLTNUM")
			.append(" AND ASG.FLTSEQNUM = ULD.FLTSEQNUM")
			.append(" AND ASG.SEGSERNUM = ULD.SEGSERNUM");
		}

		queryBuilder.append(" WHERE MAL.CMPCOD =?");
		setParameter(++idx,mailStatusFilterVO.getCompanyCode());
	
		 queryBuilder.append(" AND ULD.ARRSTA = 'Y'")
		.append(" AND MAL.MALSTA = 'ARR'")
		.append(" AND ULD.DLVSTA <> 'Y'");
		
		if(mailStatusFilterVO.getPacode() != null && mailStatusFilterVO.getPacode().length()>0){
			queryBuilder.append(" AND EXG.POACOD = ?");
			setParameter(++idx,mailStatusFilterVO.getPacode());
		}
		if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0
				|| mailStatusFilterVO.getPou() !=null && mailStatusFilterVO.getPou().length()>0){

			if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0){
				queryBuilder.append(" AND ASG.POL =?");
				setParameter(++idx,mailStatusFilterVO.getPol());
			}
			if(mailStatusFilterVO.getPou() != null && mailStatusFilterVO.getPou().length()>0){
				queryBuilder.append(" AND ASG.POU = ?");
				setParameter(++idx,mailStatusFilterVO.getPou());
			}
		}
		if(mailStatusFilterVO.getFromDate()!=null){
			
			//queryBuilder.append(" AND TRUNC(MAL.SCNDAT) >= TO_DATE(?, 'yyyy-MM-dd')");
			queryBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.SCNDAT,'YYYYMMDD')) >= ");
			//LocalDate dt = mailStatusFilterVO.getFromDate(); 
			//Date d= dt.toSqlDate();
			String dat = mailStatusFilterVO.getFromDate().toSqlDate().toString().replace("-", "");
			//setParameter(++idx,dat);
			queryBuilder.append(dat);
		}
		if(mailStatusFilterVO.getToDate()!=null){
			//queryBuilder.append(" AND TRUNC(MAL.SCNDAT) <= TO_DATE(?, 'yyyy-MM-dd')");
			queryBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.SCNDAT,'YYYYMMDD')) <= ");
			/*LocalDate dt = mailStatusFilterVO.getToDate();
			Date d= dt.toSqlDate();
			String dat = String.valueOf(d);*/
			String dat = mailStatusFilterVO.getToDate().toSqlDate().toString().replace("-", "");      
			queryBuilder.append(dat);
			//setParameter(++idx,dat);
		}
		if(mailStatusFilterVO.getFlightCarrierid()>0){
			queryBuilder.append(" AND MAL.FLTCARIDR = ?");
			setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
		}
		if(mailStatusFilterVO.getCarrierid()>0){
			queryBuilder.append(" AND MAL.FLTCARIDR = ?");
			setParameter(++idx,mailStatusFilterVO.getCarrierid());
		}
		if(mailStatusFilterVO.getFlightNumber() !=null && mailStatusFilterVO.getFlightNumber().length()>0){
			queryBuilder.append(" AND MAL.FLTNUM = ?");
			setParameter(++idx,mailStatusFilterVO.getFlightNumber());
		}
		queryBuilder.append(" ) a ");
		
		log.exiting("MailsWithoutCarditFilterQuery", "getNativeQuery");
		return queryBuilder.toString();
	}

}