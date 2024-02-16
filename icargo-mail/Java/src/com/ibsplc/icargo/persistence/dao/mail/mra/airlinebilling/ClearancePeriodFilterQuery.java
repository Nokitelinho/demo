/*
 * ClearancePeriodFilterQuery.java Created on Nov 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class ClearancePeriodFilterQuery extends NativeQuery{

	private String baseQuery;
	private UPUCalendarFilterVO upuCalendarFilterVO ;
	
	private Log log = LogFactory.getLogger("CRA:AIRLINEBILLING");
	/**
	 * @param baseQuery
	 * @param upuCalendarFilterVO
	 * @throws SystemException
	 */
	public ClearancePeriodFilterQuery(String baseQuery,UPUCalendarFilterVO upuCalendarFilterVO) throws SystemException{
		this.baseQuery = baseQuery;
		this.upuCalendarFilterVO = upuCalendarFilterVO;
	}

	/**
	 * @param 
	 * @throws 
	 */
	@Override
	public String getNativeQuery() {
		int index = 0;
		StringBuilder str = new StringBuilder(baseQuery);
		
		this.setParameter(++index,upuCalendarFilterVO.getCompanyCode());
		this.setParameter(++index,upuCalendarFilterVO.getClearancePeriod());
					
		if(upuCalendarFilterVO.getFromDate() != null ){
			str.append(" AND FRMDAT = ? ");
			this.setParameter(++index,upuCalendarFilterVO.getFromDate());
		}
		if(upuCalendarFilterVO.getToDate() != null ){
			str.append(" AND TOODAT = ? ");
			this.setParameter(++index,upuCalendarFilterVO.getToDate());
		}	
		
		log.log(Log.INFO, " the query is now ", str.toString());
		return str.toString();
	}
	
	

}
