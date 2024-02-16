/*
 * GpaReportingClaimDetailsFilterQuery.java created on Mar 28, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. 
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class GpaReportingClaimDetailsFilterQuery extends NativeQuery{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");
	private String baseQuery;
	private GPAReportingFilterVO gpaReportingFilterVO;
	/**
	 * @author A-2280
	 * @param baseQuery
	 * @param gpaReportingFilterVO
	 * @throws SystemException
	 */
	public GpaReportingClaimDetailsFilterQuery(String baseQuery,
			GPAReportingFilterVO gpaReportingFilterVO)throws SystemException {
		this.baseQuery=baseQuery;
		this.gpaReportingFilterVO=gpaReportingFilterVO;
	}
	/**
	 * @return
	 */

	@Override
	public String getNativeQuery() {
		log.entering("GpaReportingClaimDetailsFilterQuery","getNativeQuery");
		StringBuilder querySb=new StringBuilder(baseQuery);
		
		int index=0;
		this.setParameter(++index,gpaReportingFilterVO.getCompanyCode());
		this.setParameter(++index,gpaReportingFilterVO.getPoaCode());
		this.setParameter(++index,gpaReportingFilterVO.getReportingPeriodFrom());
		this.setParameter(++index,gpaReportingFilterVO.getReportingPeriodTo());
		
		if(gpaReportingFilterVO.getExceptionCode()!=null && gpaReportingFilterVO.getExceptionCode().trim().length()>0){
			querySb.append(" AND CLAIM.EXPCODE = ?");
			this.setParameter(++index,gpaReportingFilterVO.getExceptionCode());
		}
		if(gpaReportingFilterVO.getAssignee()!=null && gpaReportingFilterVO.getAssignee().trim().length()>0){
			querySb.append(" AND CLAIM.ASDUSR = ?");
			this.setParameter(++index,gpaReportingFilterVO.getAssignee());
		}
		log.log(Log.INFO, "\n\n constructed query in filter class-->", querySb.toString());
		log.exiting("GpaReportingClaimDetailsFilterQuery","getNativeQuery");
		return querySb.toString();
	}

}
