/*
 * InvoiceSummaryFilterQuery.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2521
 *
 */
public class InvoiceSummaryFilterQuery extends NativeQuery{

	private String baseQuery;
	private AirlineCN51FilterVO filterVO = null ;
	
	private Log log = LogFactory.getLogger("MRA:AIRLINEBILLING");
	
	private static final String BLANK = "";
	
	/**
	 * 
	 * @param baseQuery
	 * @param filterVO
	 * @throws SystemException
	 */
	public InvoiceSummaryFilterQuery(String baseQuery,
			AirlineCN51FilterVO filterVO) throws SystemException{
		
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
	}

	/**
	 * 
	 */
	@Override
	public String getNativeQuery() {
		
		int index = 0;
		StringBuilder str = new StringBuilder(baseQuery);
		
		String tempFilter = null;		
		LocalDate tempDate = null;
		int temField = 0;
		
		this.setParameter(++index, filterVO.getCompanyCode());
		this.setParameter(++index, filterVO.getInterlineBillingType());
		
		tempFilter = filterVO.getIataClearancePeriod();			
		if(tempFilter != null && !BLANK.equals(tempFilter)){
			
			str.append(" AND SMY.CLRPRD = ? ");
			this.setParameter(++index,tempFilter);
		}
		
		temField = filterVO.getAirlineIdentifier();			
		if(temField != 0){
			
			str.append(" AND SMY.ARLIDR = ? ");
			this.setParameter(++index,temField);
		}
		if(filterVO.getInvoiceStatus() != null){
			str.append(" AND SMY.INVSTA = ? ");
			this.setParameter(++index,filterVO.getInvoiceStatus());
		}
		tempDate = filterVO.getFromDate();			
		if(tempDate != null){
			
			str.append(" AND CDRMST.FRMDAT >= ? ");
			this.setParameter(++index, tempDate);
		}
		
		tempDate = filterVO.getToDate();			
		if(tempDate != null){
			
			str.append(" AND CDRMST.TOODAT <= ? ");
			this.setParameter(++index, tempDate);
		}
			
		log.log(Log.INFO, " the query is now ", str.toString());
		return str.toString();
	}
	
	

}
