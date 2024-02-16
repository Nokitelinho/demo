/*
 * AirlineExceptionsFilterQuery.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2407
 *
 */
public class AirlineExceptionsFilterQuery extends NativeQuery{

	private String baseQuery;
	private AirlineExceptionsFilterVO airlineExceptionsFilterVO ;
	
	private Log log = LogFactory.getLogger("MRA:AIRLINEBILLING");
	
	private static final String BLANK = "";
	
	
	/**
	 * @param baseQuery
	 * @param airlineExceptionsFilterVO
	 * @throws SystemException
	 */
	public AirlineExceptionsFilterQuery(String baseQuery,
			AirlineExceptionsFilterVO airlineExceptionsFilterVO) throws SystemException{
		
		this.baseQuery = baseQuery;
		this.airlineExceptionsFilterVO = airlineExceptionsFilterVO;
	}

	/**
	 * 
	 */
	@Override
	public String getNativeQuery() {
		
		int index = 0;
		StringBuilder str = new StringBuilder(baseQuery);
		
		String companyCode = airlineExceptionsFilterVO.getCompanyCode();
		String airlineCod = airlineExceptionsFilterVO.getAirlineCode();
		
		
		
		this.setParameter(++index,companyCode);
		this.setParameter(++index,Integer.parseInt(airlineExceptionsFilterVO.getFromDate().toStringFormat("yyyyMMdd").substring(0, 8)));
		this.setParameter(++index,Integer.parseInt(airlineExceptionsFilterVO.getToDate().toStringFormat("yyyyMMdd").substring(0, 8)));
		this.setParameter(++index,airlineCod);
					
		
		if(airlineExceptionsFilterVO.getOriginOfficeOfExchange() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getOriginOfficeOfExchange())){
			
			str.append(" AND BLGMST.ORGEXGOFC = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getOriginOfficeOfExchange());
		}
		if(airlineExceptionsFilterVO.getDestinationOfficeOfExchange() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getDestinationOfficeOfExchange())){
			
			str.append(" AND BLGMST.DSTEXGOFC = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getDestinationOfficeOfExchange());
		}
		if(airlineExceptionsFilterVO.getMailCategory() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getMailCategory())){
			
			str.append("AND EXP.MALCTGCOD = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getMailCategory());
		}
		if(airlineExceptionsFilterVO.getSubClass() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getSubClass())){
			
			str.append(" AND EXP.MALSUBCLS = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getSubClass());
		}
		if(airlineExceptionsFilterVO.getYear() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getYear())){
			
			str.append(" AND EXP.YER = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getYear());
		}
		if(airlineExceptionsFilterVO.getReceptacleSerialNumber() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getReceptacleSerialNumber())){
			
			str.append(" AND BLGMST.RSN = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getReceptacleSerialNumber());
		}
		if(airlineExceptionsFilterVO.getHighestNumberIndicator() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getHighestNumberIndicator())){
			
			str.append(" AND BLGMST.HSN = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getHighestNumberIndicator());
		}
		if(airlineExceptionsFilterVO.getRegisteredIndicator() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getRegisteredIndicator())){
			
			str.append(" AND BLGMST.REGIND = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getRegisteredIndicator());
		}
		
		
		
		
		
		
		
		if(airlineExceptionsFilterVO.getDespatchSerNo() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getDespatchSerNo())){
			
			str.append(" AND EXP.DSN = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getDespatchSerNo());
		}
		
		if(airlineExceptionsFilterVO.getExceptionCode() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getExceptionCode())){
			
			str.append(" AND EXP.EXPCOD = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getExceptionCode());
		}
		
		if(airlineExceptionsFilterVO.getInvoiceRefNumber() != null &&
				!BLANK.equals(airlineExceptionsFilterVO.getInvoiceRefNumber())){
			
			str.append(" AND EXP.INVNUM = ? ");
			this.setParameter(++index,airlineExceptionsFilterVO.getInvoiceRefNumber());
		}
		
		log.log(Log.INFO, " the query is now ", str.toString());
		return str.toString();
	}
	
	

}
